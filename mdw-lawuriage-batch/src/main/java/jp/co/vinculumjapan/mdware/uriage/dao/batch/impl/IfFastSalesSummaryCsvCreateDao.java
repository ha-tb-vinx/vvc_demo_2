package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.common.util.CSVLine;
import jp.co.vinculumjapan.mdware.common.util.DateChanger;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiStringUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <p>タイトル: IfFastSalesSummaryCsvCreateDao クラス</p>
 *  <p>説明: 説明: FAST向けIFファイル（ペイメント毎の売上サマリー）を作成する。</p>
 *  <p>著作権: Copyright (c) 2020</p>
 *  <p>会社名: VVC</p>
 *  @author VINX
 *  @Version 1.00 (2020.08.27) THONG.VQ MKV対応
 */
public class IfFastSalesSummaryCsvCreateDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB840010";

    /** バッチ名 */
    private static final String BATCH_NAME = "FAST向けIFファイル作成（ペイメント毎の売上サマリー）処理";

    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    private static final String CHECK_RANGE = FiResorceUtility.getPropertie("FAST_SALES_SUMMARY_CHECK_RANGE");
    private static final String PREVIOUS_DT = DateChanger.addDate(BATCH_DT, -1);
    private static final String CHECK_DT = DateChanger.addDate(BATCH_DT, Integer.parseInt(CHECK_RANGE)*(-1));
    /** システム日時 */
    private static final String SYSTEM_DT = FiResorceUtility.getDBServerTime();
    /** FASTファイルパス */
    private static final String FAST_IF_PATH = FiResorceUtility.getPropertie("FAST_IF_PATH");

    /** FASTファイル名 */
    private static final String FAST_SEND_FILE_SALES_SUMMARY = FiResorceUtility.getPropertie("FAST_SEND_FILE_SALES_SUMMARY");

    /** 出力ファイル文字コード */
    private static final String OUTPUT_CHAR_SET = "UTF-8";

    /** 改行文字(CRLF */
    private static final String LINE_FEED_CHAR = "\r\n";

    /** 文字列変換用 */
    private static final int MODE_STR = 1;
    
    /** 精算状況フラグ２：精算済 */
    private static final String SEISAN_STATE_FG = "2";
    /** FAST送ったフラグ*/
    private static final String FAST_SENT_FG_OFF = "0";
    private static final String FAST_SENT_FG_ON = "1";

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        PreparedStatementEx ps = null;
        ResultSet rs = null;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　FAST向けIFファイル作成（ペイメント毎の売上サマリー）処理を開始します。");

        // WK_SALES_SUMMARYワーク削除処理
        invoker.infoLog(strUserId + "　：　削除処理を開始します。");
        // 検収確定データ（修正なし）削除
        ps = invoker.getDataBase().prepareStatement("TRUNCATE TABLE WK_SALES_SUMMARY");
        ps.executeUpdate();
        invoker.infoLog(strUserId + "　：　TRUNCATEしました。");
        invoker.infoLog(strUserId + "　：　削除処理を終了します。");


        /** ペイメント毎の売上サマリーデータ取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ペイメント毎の売上サマリー作成処理。");

        /** ペイメント毎の売上サマリーデータ件数 */
        int count = 0;
        // ペイメント毎の売上サマリーデータ取得処理SQL
        ps = invoker.getDataBase().prepareStatement(insertWkSalesSummary());
        // SQL文を実行する
        count = ps.executeUpdate();

        // 実行後処理
        // ログを出力する。
        invoker.infoLog(strUserId + "　：　" + count + "件のペイメント毎の売上サマリーデータを取得しました。");
        invoker.infoLog(strUserId + "　：　ペイメント毎の売上サマリー作成処理を終了します。");


        /** ペイメント毎の売上サマリーデータファイル作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ペイメント毎の売上サマリーデータファイル作成処理を開始します。");

        count = 0;
        // ペイメント毎の売上サマリーデータ取得処理SQL
        ps = invoker.getDataBase().prepareStatement(selectIfFastSalesSummary());
        // SQL文を実行する
        rs = ps.executeQuery();

        count = createCSVFile(rs);
        // ログを出力
        invoker.infoLog(strUserId + "　：　" + count + "件のペイメント毎の売上サマリーデータを出力しました。");
        invoker.infoLog(strUserId + "　：　ペイメント毎の売上サマリーデータファイル作成処理を終了します。");

        /** 全店精算状況データの更新 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　全店精算状況データの更新処理を開始します。");
        count = 0;
        ps = invoker.getDataBase().prepareStatement(updateDtZentenSeisanState());
        // SQL文を実行する
        count = ps.executeUpdate();
        // ログを出力
        invoker.infoLog(strUserId + "　：　" + count + "件のデータを更新しました。");
        invoker.infoLog(strUserId + "　：　全店精算状況データの更新処理を終了します。");
        if(rs!=null) {
            rs.close();
        }

        /** 終了処理 */
        invoker.infoLog(strUserId + "　：　FAST向けIFファイル作成（ペイメント毎の売上サマリー）処理を終了します。");

    }

    private int createCSVFile(ResultSet rs) throws Exception {
        int cnt = 0;

        // ファイル出力
        BufferedWriter writer = null;
        File dataFile = new File(FAST_IF_PATH + "/" + FAST_SEND_FILE_SALES_SUMMARY + "_" + PREVIOUS_DT + ".csv");

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
            writer = new BufferedWriter(outputStreamWriter);
            writer.write("\ufeff");

            if(rs == null) {
                return cnt;
            }
            while (rs.next()) {
                StringBuffer csvLine = new StringBuffer();

                CSVLine line = new CSVLine();

                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("KEIJO_DT"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("TENPO_CD"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("REGI_RB"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("SHIHARAI_SYUBETSU_GROUP_CD"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("SHIHARAI_SYUBETSU_GROUP_NA"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("SHIHARAI_SYUBETSUB_SUB_CD"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("SHIHARAI_SYUBETSU_SUB_NA"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("POS_VL"), MODE_STR)));

                csvLine.append(line.getLine());
                csvLine.append(LINE_FEED_CHAR); // 改行
                // ファイル書出し
                writer.write(csvLine.toString());

                cnt++;

            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return cnt;
    }


    private String selectIfFastSalesSummary() {

        // SQL文作成用
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT * ");
        sb.append(" FROM WK_SALES_SUMMARY ");
        sb.append(" ORDER BY ");
        sb.append("     KEIJO_DT ");
        sb.append("     ,TENPO_CD ");

        return sb.toString();
    }


    private String insertWkSalesSummary() {

        // SQL文作成用
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO  ");
        sb.append(" WK_SALES_SUMMARY ( ");
        sb.append("    KEIJO_DT ");
        sb.append("    ,TENPO_CD ");
        sb.append("    ,REGI_RB ");
        sb.append("    ,SHIHARAI_SYUBETSU_GROUP_CD ");
        sb.append("    ,SHIHARAI_SYUBETSU_GROUP_NA ");
        sb.append("    ,SHIHARAI_SYUBETSUB_SUB_CD ");
        sb.append("    ,SHIHARAI_SYUBETSU_SUB_NA ");
        sb.append("    ,POS_VL ");
        sb.append("    ,INSERT_USER_ID ");
        sb.append("    ,INSERT_TS ");
        sb.append("    ,UPDATE_USER_ID ");
        sb.append("    ,UPDATE_TS ");
        sb.append(" ) ");
        sb.append(" ( ");
        sb.append(" SELECT  ");
        sb.append("       TRS.KEIJO_DT ");
        sb.append("       ,TRS.TENPO_CD ");
        sb.append("       ,TRS.REGISTER_NO ");
        sb.append("       ,RP.SHIHARAI_SYUBETSU_GROUP_CD ");
        sb.append("       ,TRIM(RP.SHIHARAI_SYUBETSU_GROUP_NA) SHIHARAI_SYUBETSU_GROUP_NA ");
        sb.append("       ,RP.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("       ,TRIM(RP.SHIHARAI_SYUBETSU_SUB_NA) SHIHARAI_SYUBETSU_SUB_NA ");
        sb.append("       ,TRS.TEISEIGO_VL ");
        sb.append("       ,'" + BATCH_ID + "'");
        sb.append("       ,'" + SYSTEM_DT + "'");
        sb.append("       ,'" + BATCH_ID + "'");
        sb.append("       ,'" + SYSTEM_DT + "'");
        sb.append(" FROM ");
        sb.append("   ( ");
        sb.append("       SELECT ");
        sb.append("               (CASE ");
        sb.append("                   WHEN RDC.PARAMETER_ID IS NULL THEN RP.SHIHARAI_SYUBETSU_GROUP_NA ");
        sb.append("                   ELSE RP.SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("               END) AS SHIHARAI_SYUBETSU_GROUP_NA, ");
        sb.append("               RP.SHIHARAI_SYUBETSU_SUB_CD, ");
        sb.append("               RP.SHIHARAI_SYUBETSU_GROUP_CD, ");
        sb.append("               RP.SHIHARAI_SYUBETSU_CD, ");
        sb.append("               (CASE ");
        sb.append("                   WHEN RDC.PARAMETER_ID IS NULL THEN RP.SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("                   ELSE RP.SHIHARAI_SYUBETSU_SUB_NA ");
        sb.append("               END) AS SHIHARAI_SYUBETSU_SUB_NA ");
        sb.append("       FROM ");
        sb.append("           R_PAYMENT RP  ");
        sb.append("           LEFT JOIN R_DICTIONARY_CONTROL RDC ON ");
        sb.append("           ( ");
        sb.append("               RP.SHIHARAI_SYUBETSU_SUB_CD = RDC.DICTIONARY_ID ");
        sb.append("               AND RDC.SUBSYSTEM_ID = 'URIAGE' ");
        sb.append("               AND RDC.PARAMETER_ID = 'GAMEN_PAYMENT_vi_VN' ");
        sb.append("           ) ");
        sb.append("   ) RP ");
        sb.append(" INNER JOIN ");
        sb.append("   ( ");
        sb.append("       SELECT ");
        sb.append("           COMP_CD ");
        sb.append("           ,KEIJO_DT ");
        sb.append("           ,SHIHARAI_SYUBETSU_CD ");
        sb.append("           ,SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("           ,TENPO_CD ");
        sb.append("           ,REGISTER_NO ");
        sb.append("           ,TEISEIGO_VL ");
        sb.append("       FROM ");
        sb.append("       ( ");
        sb.append("           SELECT ");
        sb.append("               COMP_CD ");
        sb.append("               ,KEIJO_DT ");
        sb.append("               ,SHIHARAI_SYUBETSU_CD ");
        sb.append("               ,SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("               ,TENPO_CD ");
        sb.append("               ,REGISTER_NO ");
        sb.append("               ,SUM(TEISEIGO_VL) TEISEIGO_VL ");
        sb.append("           FROM DT_TEN_RECEIPT_SEISAN ");
        sb.append("           GROUP BY  ");
        sb.append("               COMP_CD ");
        sb.append("               ,KEIJO_DT ");
        sb.append("               ,TENPO_CD ");
        sb.append("               ,REGISTER_NO ");
        sb.append("               ,SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("               ,SHIHARAI_SYUBETSU_CD ");
        sb.append("       ) ");
        sb.append("   ) TRS ");
        sb.append("   ON ");
        sb.append("   ( ");
        sb.append("       RP.SHIHARAI_SYUBETSU_CD = TRS.SHIHARAI_SYUBETSU_CD ");
        sb.append("       AND RP.SHIHARAI_SYUBETSU_SUB_CD = TRS.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   ) ");
        sb.append(" INNER JOIN ");
        sb.append("     DT_ZENTEN_SEISAN_STATE DZSS ");
        sb.append(" ON ");
        sb.append("     DZSS.COMP_CD = TRS.COMP_CD ");
        sb.append(" AND DZSS.KEIJO_DT = TRS.KEIJO_DT ");
        sb.append(" WHERE ");
        sb.append("     TRS.KEIJO_DT >= '" + CHECK_DT + "' ");
        sb.append(" AND TRS.KEIJO_DT <= '" + PREVIOUS_DT + "' ");
        sb.append(" AND DZSS.SEISAN_STATE_FG = '" + SEISAN_STATE_FG + "' ");
        sb.append(" AND DZSS.FAST_SENT_FG = '" + FAST_SENT_FG_OFF + "' ");
        sb.append(" ) ");

        return sb.toString();
    }

    private String updateDtZentenSeisanState() {
     // SQL文作成用
        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE ");
        sb.append("     DT_ZENTEN_SEISAN_STATE  ");
        sb.append(" SET ");
        sb.append("     FAST_SENT_FG = '").append(FAST_SENT_FG_ON).append("'  ");
        sb.append("     ,UPDATE_TS = '").append(SYSTEM_DT).append("' ");
        sb.append("     ,UPDATE_USER_ID = '").append(BATCH_ID).append("' ");
        sb.append(" WHERE ");
        sb.append("     KEIJO_DT >= '").append(CHECK_DT).append("' ");
        sb.append(" AND KEIJO_DT <= '").append(PREVIOUS_DT).append("' ");
        sb.append(" AND SEISAN_STATE_FG = '").append(SEISAN_STATE_FG).append("'  ");
        sb.append(" AND FAST_SENT_FG = '").append(FAST_SENT_FG_OFF).append("'  ");
        return sb.toString();
    }

    private String addQuarter(String value){
        return '"' + value + '"';
    }    

    public void setInputObject(Object input) throws Exception {
        // TODO Auto-generated method stub

    }

    public Object getOutputObject() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
