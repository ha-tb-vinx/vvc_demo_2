package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.vinculumjapan.mdware.common.util.CSVLine;
import jp.co.vinculumjapan.mdware.common.util.DateChanger;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiStringUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <p>タイトル: IfTrePaymentInfoCsvCreateDao クラス</p>
 *  <p>説明: 説明: TRE向けIFファイル作成を作成する。</p>
 *  <p>著作権: Copyright (c) 2020</p>
 *  <p>会社名: VVC</p>
 *  @author VINX
 *  @Version 1.00 (2021.10.12) KHOI.ND MKHK対応
 *  @Version 1.01 (2021.11.18) DUY.HK #6363 MKHK対応
 *  @Version 1.02 (2022.01.26) KHAI.NN #6497 MKH対応
 */
public class IfTrePaymentInfoCsvCreateDao implements DaoIf {
    
    /** バッチID */
    private static final String BATCH_ID = "URIB860";

    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    // #6363 Add 2021.11.18 DUY.HK (S)
    private static String PREVIOUS_DT = "";
    // #6363 Add 2021.11.18 DUY.HK (E)

    private static final String CHECK_RANGE = FiResorceUtility.getPropertie("TRE_PAYMENT_INFO_CHECK_RANGE");
    
    private static final String CHECK_DT = DateChanger.addDate(BATCH_DT, Integer.parseInt(CHECK_RANGE)*(-1));

    private static final String SYSTEM_DATE = FiResorceUtility.getDBServerTime();

    private static final String PATH = FiResorceUtility.getPropertie("IF_TRE_PATH");

    private static final String IF_TRE_PAYMENT_INFO_FILENAME = FiResorceUtility.getPropertie("IF_TRE_PAYMENT_INFO_FILENAME");

    private static final String OUTPUT_CHAR_SET = "UTF-8";

    private static final String LINE_FEED_CHAR = "\r\n";

    private static final int MODE_STR = 1;

    private static final String SEISAN_STATE_FG = "2";

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + " TRE向けIFファイル作成を作成する。";
        PreparedStatementEx ps = null;
        ResultSet rs = null;
        
        // #6363 Add 2021.11.18 DUY.HK (S)
        PREVIOUS_DT = DateChanger.addDate(BATCH_DT, -1);
        // #6363 Add 2021.11.18 DUY.HK (E)
        
        /** ログを出力*/
        invoker.infoLog(strUserId + "　：　WK_IF_TRE_PAYMENT_INFOの削除処理を開始します。");

        /** テーブルWK_IF_TRE_PAYMENT_INFOを切り捨てます */ 
        invoker.infoLog(strUserId + "　：　削除処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(getTrePaymentInfoTruncateStatement());
        ps.execute();
        invoker.infoLog(strUserId + "　：　TRUNCATEしました。");
        invoker.infoLog(strUserId + "　：　削除処理を終了します。");
        
        /** WK_IF_TRE_PAYMENT_INFOに挿入します */ 
        invoker.infoLog(strUserId + "　：　WK_IF_TRE_PAYMENT_INFOに挿入します。");
        int count = 0;
        ps = invoker.getDataBase().prepareStatement(getTrePaymentInfoInsertStatement());
        count = ps.executeUpdate();

        invoker.infoLog(strUserId + "　：　" + count + " データの行が挿入されました。");
        invoker.infoLog(strUserId + "　：　挿入が完了しました。");

        /** csvファイルを作成する */
        invoker.infoLog(strUserId + "　：　csvファイルの作成プロセスを開始します。");

        count = 0;
        ps = invoker.getDataBase().prepareStatement(getTrePaymentInfoQueryStatement());
        rs = ps.executeQuery();

        // #6497 Mod 2022.01.26 KHAI.NN (S)
        //count = createCSVFile(rs);
        if (rs.next()) {
            count = createCSVFile(rs);
        }
        // #6497 Mod 2022.01.26 KHAI.NN (E)
        invoker.infoLog(strUserId + "　：　" + count + " データの行がエクスポートされました。");
        invoker.infoLog(strUserId + "　：　ファイル作成プロセスが完了しました。");
        
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

        /** プロセスが完了しました */
        invoker.infoLog(strUserId + "　：　けIFファイル作成処理を終了します。");
    }

    private int createCSVFile(ResultSet rs) throws Exception {
        int cnt = 0;
        Date systemDate = new SimpleDateFormat("yyyyMMddhhmmss").parse(SYSTEM_DATE);
        String fileName = IF_TRE_PAYMENT_INFO_FILENAME.replaceAll("yymmddhhmmss", new SimpleDateFormat("yyMMddhhmmss").format(systemDate));
        File dataFile = new File(PATH + "/" + fileName);

        // ファイル出力
        BufferedWriter writer = null;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
            writer = new BufferedWriter(outputStreamWriter);
            writer.write("\ufeff");

            if(rs == null) {
                return cnt;
            }

            // #6497 Mod 2022.01.26 KHAI.NN (S)
            //while (rs.next()) {
            do {
            // #6497 Mod 2022.01.26 KHAI.NN (E)
                StringBuffer csvLine = new StringBuffer();

                CSVLine line = new CSVLine();

                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("KEIJO_DT"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("TENPO_CD"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("SHIHARAI_SYUBETSU_SEQ"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("POS_VL"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("UPDATE_TS"), MODE_STR)));

                csvLine.append(line.getLine());
                csvLine.append(LINE_FEED_CHAR); // 改行
                // ファイル書出し
                writer.write(csvLine.toString());

                cnt++;

            // #6497 Mod 2022.01.26 KHAI.NN (S)
            //}
            } while (rs.next());
            // #6497 Mod 2022.01.26 KHAI.NN (E)
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

    private String getTrePaymentInfoQueryStatement() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT * FROM WK_IF_TRE_PAYMENT_INFO ");
        return sb.toString();
    }

    private String getTrePaymentInfoTruncateStatement() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_IF_TRE_PAYMENT_INFO ");
        return sb.toString();
    }
    
    private String getTrePaymentInfoInsertStatement() {
        StringBuffer sb = new StringBuffer();
        
        sb.append(" INSERT INTO WK_IF_TRE_PAYMENT_INFO ( ");
        sb.append("     KEIJO_DT, ");
        sb.append("     TENPO_CD, ");
        sb.append("     SHIHARAI_SYUBETSU_SEQ, ");
        sb.append("     POS_VL, ");
        sb.append("     UPDATE_TS ");
        sb.append(" ) ");
        sb.append(" WITH PAYMENT_INFO AS ( ");
        sb.append("     SELECT ");
        sb.append("         TRS.KEIJO_DT, ");
        sb.append("         TRS.TENPO_CD, ");
        sb.append("         TRS.SHIHARAI_SYUBETSU_CD, ");
        sb.append("         RP.SHIHARAI_SYUBETSU_SEQ, ");
        sb.append("         TRS.TEISEIGO_VL ");
        sb.append("     FROM ");
        sb.append("         R_PAYMENT RP ");
        sb.append("         INNER JOIN DT_TEN_RECEIPT_SEISAN TRS ON RP.SHIHARAI_SYUBETSU_CD = TRS.SHIHARAI_SYUBETSU_CD ");
        sb.append("         AND RP.SHIHARAI_SYUBETSU_SUB_CD = TRS.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("         INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS ON DZSS.COMP_CD = TRS.COMP_CD ");
        sb.append("         AND DZSS.KEIJO_DT = TRS.KEIJO_DT ");
        sb.append("     WHERE ");
        sb.append("         TRS.KEIJO_DT >= '" + CHECK_DT + "' ");
        // #6363 Mod 2021.11.18 DUY.HK (S)
        //sb.append("         AND TRS.KEIJO_DT <= '" + BATCH_DT + "' ");
        sb.append("         AND TRS.KEIJO_DT <= '" + PREVIOUS_DT + "' ");
        // #6363 Mod 2021.11.18 DUY.HK (E)
        sb.append("         AND DZSS.SEISAN_STATE_FG = '" + SEISAN_STATE_FG + "' ");
        sb.append("         AND DZSS.TRE_SEND_KB = '0' ");
        sb.append(" ) ");
        sb.append(" SELECT ");
        sb.append("     TO_CHAR(TO_DATE(PI.KEIJO_DT, 'yyyymmdd'),'yyyy/MM/dd') AS KEIJO_DT, ");
        sb.append("     PI.TENPO_CD, ");
        sb.append("     PI.SHIHARAI_SYUBETSU_SEQ, ");
        sb.append("     PI.TEISEIGO_VL, ");
        sb.append("     TO_CHAR( ");
        sb.append("         TO_DATE(MUT.UPDATE_TS, 'yyyymmddhh24miss'), ");
        sb.append("         'yyyy/MM/dd hh24:mm' ");
        sb.append("     ) AS UPDATE_TS ");
        sb.append(" FROM ");
        sb.append("     ( ");
        sb.append("         SELECT ");
        sb.append("             KEIJO_DT, ");
        sb.append("             TENPO_CD, ");
        sb.append("             SHIHARAI_SYUBETSU_SEQ, ");
        sb.append("             SUM(TEISEIGO_VL) AS TEISEIGO_VL ");
        sb.append("         FROM ");
        sb.append("             PAYMENT_INFO ");
        sb.append("         GROUP BY ");
        sb.append("             KEIJO_DT, ");
        sb.append("             TENPO_CD, ");
        sb.append("             SHIHARAI_SYUBETSU_SEQ ");
        sb.append("     ) PI ");
        sb.append("     INNER JOIN ( ");
        sb.append("         SELECT ");
        sb.append("             KEIJO_DT, ");
        sb.append("             TENPO_CD, ");
        sb.append("             MAX(UPDATE_TS) AS UPDATE_TS ");
        sb.append("         FROM ");
        sb.append("             DT_TEN_RECEIPT_SEISAN ");
        sb.append("         GROUP BY ");
        sb.append("             KEIJO_DT, ");
        sb.append("             TENPO_CD ");
        sb.append("     ) MUT ON MUT.TENPO_CD = PI.TENPO_CD ");
        sb.append("             AND MUT.KEIJO_DT = PI.KEIJO_DT ");
        sb.append(" ORDER BY ");
        sb.append("     PI.KEIJO_DT, ");
        sb.append("     PI.TENPO_CD ");
        return sb.toString();
    }
    
    private String updateDtZentenSeisanState() {
        // SQL文作成用
           StringBuffer sb = new StringBuffer();
           sb.append(" UPDATE ");
           sb.append("     DT_ZENTEN_SEISAN_STATE  ");
           sb.append(" SET ");
           sb.append("     TRE_SEND_KB = '1'  ");
           sb.append("     ,UPDATE_TS = '").append(SYSTEM_DATE).append("' ");
           sb.append("     ,UPDATE_USER_ID = '").append(BATCH_ID).append("' ");
           sb.append(" WHERE ");
           sb.append("     KEIJO_DT >= '").append(CHECK_DT).append("' ");
           // #6363 Mod 2021.11.18 DUY.HK (S)
           //sb.append(" AND KEIJO_DT <= '").append(BATCH_DT).append("' ");
           sb.append(" AND KEIJO_DT <= '").append(PREVIOUS_DT).append("' ");
           // #6363 Mod 2021.11.18 DUY.HK (E)
           sb.append(" AND SEISAN_STATE_FG = '").append(SEISAN_STATE_FG).append("'  ");
           sb.append(" AND TRE_SEND_KB = '0'  ");
           return sb.toString();
       }

    private String addQuarter(String value) {
        return '"' + value + '"';
    }    
    
    public void setInputObject(Object input) throws Exception {
    }

    public Object getOutputObject() throws Exception {
        return null;
    }
}
