package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.common.util.CSVLine;
import jp.co.vinculumjapan.mdware.common.util.DateChanger;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.SalesKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.constant.ZeiKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiStringUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <p>タイトル: IfFastIssueCsvCreateDao クラス</p>
 *  <p>説明: 説明: FAST向けIFファイル（レシート（小売））を作成する。</p>
 *  <p>著作権: Copyright (c) 2009</p>
 *  <p>会社名: VVC</p>
 *  @author VINX
 *  @Version 1.00 (2020.03.23) Man.NH MKV対応
 *  @Version 1.01 (2020.10.07) KHAI.NN #6246 MKV対応
 *  @Version 1.02 (2020.10.09) KHAI.NN #6249 MKV対応
 *  @Version 1.03 (2020.10.15) KHAI.NN #6252 MKV対応
 *  @Version 1.04 (2020.10.30) KHAI.NN #6271 MKV対応
 *  @Version 1.05 (2020.12.10) THONG.VQ #6285 MKV対応
 *  @Version 1.06 (2022.05.11) SIEU.D #6567 MKV対応
 *  @Version 1.07 (2022.12.19) SIEU.D #6712 MKV対応
 */
public class IfFastSalesInvoiceCsvCreateDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB830010";

    /** バッチ名 */
    private static final String BATCH_NAME = "FAST向けIFファイル作成（レシート（小売））処理";

    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    private static final String PREVIOUS_DT = DateChanger.addDate(BATCH_DT, -1);
    private static final String LAST_DT = DateChanger.addDate(BATCH_DT, -2);
    /** システム日時 */
    private static final String SYSTEM_DT = FiResorceUtility.getDBServerTime();
    /** FASTファイルパス */
    private static final String FAST_SEND_PATH = FiResorceUtility.getPropertie("FAST_IF_PATH");

    /** FASTファイル名 */
    private static final String FAST_SEND_FILE_SALES_INVOICE = FiResorceUtility.getPropertie("FAST_SEND_FILE_SALES_INVOICE");

    /** 出力ファイル文字コード */
    private static final String OUTPUT_CHAR_SET = "UTF-8";

    /** 改行文字(CRLF */
    private static final String LINE_FEED_CHAR = "\r\n";

    /** 文字列変換用 */
    private static final int MODE_STR = 1;

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        PreparedStatementEx ps = null;
        ResultSet rs = null;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　FAST向けIFファイル作成（レシート（小売））処理を開始します。");

        // WK_ISSUEワーク削除処理
        invoker.infoLog(strUserId + "　：　削除処理を開始します。");
        // 検収確定データ（修正なし）削除
        ps = invoker.getDataBase().prepareStatement("TRUNCATE TABLE WK_SALES_INVOICE");
        ps.executeUpdate();
        invoker.infoLog(strUserId + "　：　TRUNCATEしました。");
        invoker.infoLog(strUserId + "　：　削除処理を終了します。");


        /** レシート（小売）データ取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　レシート（小売）作成処理を開始します｡");

        /** レシート（小売）データ件数 */
        int count = 0;
        // レシート（小売）データ取得処理SQL
        ps = invoker.getDataBase().prepareStatement(insertWkSalesInvoice());
        // SQL文を実行する
        count = ps.executeUpdate();

        // 実行後処理
        // ログを出力する。
        invoker.infoLog(strUserId + "　：　" + count + "件のレシート（小売）データを取得しました。");
        invoker.infoLog(strUserId + "　：　レシート（小売）作成処理を終了します。");


        /** レシート（小売）データファイル作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　レシート（小売）データファイル作成処理を開始します。");

        count = 0;
        // レシート（小売）データ取得処理SQL
        ps = invoker.getDataBase().prepareStatement(selectIfFastSalesInvoice());
        // SQL文を実行する
        rs = ps.executeQuery();

        count = createCSVFile(rs);
        // ログを出力
        invoker.infoLog(strUserId + "　：　" + count + "件のレシート（小売）データを出力しました。");
        invoker.infoLog(strUserId + "　：　レシート（小売）データファイル作成処理を終了します。");

        if(rs!=null) {
            rs.close();
        }

        /** 終了処理 */
        invoker.infoLog(strUserId + "　：　FAST向けIFファイル作成（レシート（小売））処理を終了します。");

    }

    private int createCSVFile(ResultSet rs) throws Exception {
        int cnt = 0;

        // ファイル出力
        BufferedWriter writer = null;
        File dataFile = new File(FAST_SEND_PATH + "/" + FAST_SEND_FILE_SALES_INVOICE + "_" + PREVIOUS_DT + ".csv");

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

                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("EIGYO_DT"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("RECEIPT_NO"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("SALES_KB"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("ZAIKO_SYOHIN_CD"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("REC_HINMEI_KANJI_NA"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("HANBAI_TANI_NA"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("TENPO_CD"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("HANBAI_TANI_QT"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("BAITANKA_NUKI_VL"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("REG_SELL_WOT"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("TOTAL_DISCOUNT"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("ACTUAL_SELL_WOT"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("TAX_RT"), MODE_STR)));

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


    private String selectIfFastSalesInvoice(){

        // SQL文作成用
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT * ");
        sb.append(" FROM WK_SALES_INVOICE ");
        sb.append(" ORDER BY ");
        sb.append("     EIGYO_DT ");
        sb.append("     ,RECEIPT_NO ");

        return sb.toString();
    }


    private String insertWkSalesInvoice(){

        // SQL文作成用
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO  ");
        sb.append(" WK_SALES_INVOICE (");
        sb.append("    EIGYO_DT ");
        sb.append("    ,RECEIPT_NO ");
        sb.append("    ,SALES_KB ");
        sb.append("    ,ZAIKO_SYOHIN_CD ");
        sb.append("    ,REC_HINMEI_KANJI_NA ");
        sb.append("    ,HANBAI_TANI_NA ");
        sb.append("    ,TENPO_CD ");
        sb.append("    ,HANBAI_TANI_QT ");
        sb.append("    ,BAITANKA_NUKI_VL ");
        sb.append("    ,REG_SELL_WOT ");
        sb.append("    ,TOTAL_DISCOUNT ");
        sb.append("    ,ACTUAL_SELL_WOT ");
        sb.append("    ,TAX_RT ");
        sb.append("    ,INSERT_USER_ID ");
        sb.append("    ,INSERT_TS ");
        sb.append("    ,UPDATE_USER_ID ");
        sb.append("    ,UPDATE_TS ");
        sb.append(" ) ");
        // #6249 URIB830010 Add 2020.10.09 KHAI.NN (S)
        sb.append(" ( ");
        sb.append(" SELECT ");
        sb.append("     RECEIPT_INFO.EIGYO_DT ");
        sb.append("     ,RECEIPT_INFO.RECEIPT_NO ");
        sb.append("     ,RECEIPT_INFO.SALES_KB ");
        sb.append("     ,RECEIPT_INFO.ZAIKO_SYOHIN_CD ");
        sb.append("     ,RECEIPT_INFO.REC_HINMEI_KANJI_NA ");
        sb.append("     ,RECEIPT_INFO.HANBAI_TANI_NA ");
        sb.append("     ,RECEIPT_INFO.TENPO_CD ");
        sb.append("     ,SUM(RECEIPT_INFO.HANBAI_TANI_QT) AS HANBAI_TANI_QT ");
        // #6712 MOD 2022.12.19 SIEU.D (S)
        // sb.append("     ,RECEIPT_INFO.BAITANKA_NUKI_VL ");
        // sb.append("     ,ROUND(SUM(RECEIPT_INFO.HANBAI_TANI_QT * RECEIPT_INFO.BAITANKA_NUKI_VL)) AS REG_SELL_WOT  ");
        // sb.append("     ,ABS(ROUND(SUM((RECEIPT_INFO.HANBAI_TANI_QT * RECEIPT_INFO.BAITANKA_NUKI_VL) - RECEIPT_INFO.ACTUAL_SELL_WOT))) AS TOTAL_DISCOUNT ");
       sb.append("     ,CASE SUM(RECEIPT_INFO.HANBAI_TANI_QT) WHEN 0 THEN 0 ELSE SUM(RECEIPT_INFO.REG_SELL_WOT)/SUM(RECEIPT_INFO.HANBAI_TANI_QT) END AS BAITANKA_NUKI_VL ");
       sb.append("     ,ROUND(SUM(RECEIPT_INFO.REG_SELL_WOT)) AS REG_SELL_WOT  ");
       sb.append("     ,ABS(ROUND(SUM(RECEIPT_INFO.ACTUAL_SELL_WOT)) - ROUND(SUM(RECEIPT_INFO.REG_SELL_WOT))) AS TOTAL_DISCOUNT ");
        // #6712 MOD 2022.12.19 SIEU.D (E)
        sb.append("     ,ROUND(SUM(RECEIPT_INFO.ACTUAL_SELL_WOT)) AS ACTUAL_SELL_WOT ");
        // #6712 MOD 2022.12.19 SIEU.D (S)
        // sb.append("     ,RECEIPT_INFO.TAX_RT ");
        sb.append("     ,TRIM(TO_CHAR(RECEIPT_INFO.TAX_RT, '00')) TAX_RT ");
        // #6712 MOD 2022.12.19 SIEU.D (E)
        sb.append("     ,'" + BATCH_ID + "'");
        sb.append("     ,'" + SYSTEM_DT + "'");
        sb.append("     ,'" + BATCH_ID + "'");
        sb.append("     ,'" + SYSTEM_DT + "'");
        sb.append(" FROM ");
        // #6249 URIB830010 Add 2020.10.09 KHAI.NN (E)
        sb.append(" ( ");
        sb.append(" SELECT ");
        sb.append("     MAIN.EIGYO_DT ");
        // #6246 URIB830010 Mod 2020.10.07 KHAI.NN (S)
        //sb.append("     ,MAIN.RECEIPT_NO ");
        sb.append("     ,SUBSTR(MAIN.RECEIPT_NO, 3) AS RECEIPT_NO ");
        // #6246 URIB830010 Mod 2020.10.07 KHAI.NN (E)
        sb.append("     ,CASE  ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (S)
        //sb.append("         WHEN NVL(SUB.HENPIN_SU, '0') > 0 ");
        sb.append("         WHEN NVL(SUB.SALES_TIMES, '0') = 0 ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (E)
        sb.append("         THEN '"+ SalesKbDictionary.HENPIN.getCode() +"' ");
        sb.append("         ELSE '"+ SalesKbDictionary.HANBAI.getCode() +"' ");
        sb.append("     END AS SALES_KB  ");
        sb.append("     ,RS.ZAIKO_SYOHIN_CD ");
        sb.append("     ,RS.REC_HINMEI_KANJI_NA ");
        sb.append("     ,RN.KANJI_NA AS HANBAI_TANI_NA ");
        sb.append("     ,MAIN.TENPO_CD ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (S)
        //sb.append("     ,ROUND(MAIN.HANBAI_TANI_QT) ");
        sb.append("     ,ROUND(MAIN.HANBAI_TANI_QT) AS HANBAI_TANI_QT ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (E)
        sb.append("     ,CASE ");
        sb.append("         WHEN RS.ZEI_KB = '"+ ZeiKbDictionary.HIKAZEI.getCode() +"' ");
        sb.append("         THEN ROUND(NVL(RTSR.BAITANKA_VL, NVL(RS.BAITANKA_VL, '0')), 2) ");
        sb.append("         ELSE ROUND((NVL(RTSR.BAITANKA_VL, NVL(RS.BAITANKA_VL, '0')) * 100) / (100 + RTR.TAX_RT), 2) ");
        sb.append("     END AS BAITANKA_NUKI_VL ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (S)
        //sb.append("     ,ROUND(MAIN.REG_SELL_WOT, 2) ");
        //sb.append("     ,ABS(ROUND(MAIN.REG_SELL_WOT - MAIN.ACTUAL_SELL_WOT, 2)) AS TOTAL_DISCOUNT ");
        //sb.append("     ,ROUND(MAIN.ACTUAL_SELL_WOT, 2) ");
        // #6271 URIB830010 Mod 2020.10.30 KHAI.NN (S)
        //sb.append("     ,MAIN.ACTUAL_SELL / (1 + RTR.TAX_RT/100) AS ACTUAL_SELL_WOT ");
        sb.append("     ,MAIN.ACTUAL_SELL_WOT AS ACTUAL_SELL_WOT ");
        // #6271 URIB830010 Mod 2020.10.30 KHAI.NN (E)
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (E)
        // #6712 MOD 2022.12.19 SIEU.D (S)
//        sb.append("     ,CASE ");
//        sb.append("         WHEN RS.ZEI_KB = '"+ ZeiKbDictionary.HIKAZEI.getCode() +"' ");
//        sb.append("         THEN 'KT' ");
//        sb.append("         ELSE TRIM(TO_CHAR(RTR.TAX_RT, '00')) ");
//        sb.append("     END AS TAX_RT ");
        sb.append("     ,MAIN.REG_SELL_WOT AS REG_SELL_WOT ");
        sb.append("     ,MAIN.GST_TAX * 100 AS TAX_RT ");
        // #6712 MOD 2022.12.19 SIEU.D (E)
        // #6249 URIB830010 Del 2020.10.09 KHAI.NN (S)
//        sb.append("     ,'" + BATCH_ID + "'");
//        sb.append("     ,'" + SYSTEM_DT + "'");
//        sb.append("     ,'" + BATCH_ID + "'");
//        sb.append("     ,'" + SYSTEM_DT + "'");
        // #6249 URIB830010 Del 2020.10.09 KHAI.NN (E)
        sb.append(" FROM ");
        sb.append(" ( ");
        sb.append("     SELECT ");
        sb.append("            TEMP.* ");
        sb.append("            ,(   SELECT MAX(RS1.YUKO_DT) ");
        sb.append("                 FROM R_SYOHIN RS1 ");
        sb.append("                 WHERE RS1.SYOHIN_CD = TEMP.SYOHIN_CD ");
        sb.append("                 AND   RS1.DELETE_FG   = '").append(UriageCommonConstants.FG_OFF).append("' ");
        sb.append("                 AND   RS1.YUKO_DT    <= TEMP.EIGYO_DT ");
        // #6567 ADD 2022.05.11 SIEU.D (S)
        sb.append("                 AND   SUBSTR(RS1.INSERT_TS, 1, 12) <= TEMP.SALE_TS ");
        // #6567 ADD 2022.05.11 SIEU.D (E)
        
        sb.append("             ) R_YUKO_DT ");
        sb.append("            ,(   SELECT MAX(RTSR1.YUKO_DT) ");
        sb.append("                 FROM R_TENSYOHIN_REIGAI RTSR1 ");
        sb.append("                 WHERE RTSR1.SYOHIN_CD = TEMP.SYOHIN_CD ");
        sb.append("                 AND   RTSR1.DELETE_FG   = '").append(UriageCommonConstants.FG_OFF).append("' ");
        sb.append("                 AND   RTSR1.TENPO_CD   = TEMP.TENPO_CD");
        sb.append("                 AND   RTSR1.YUKO_DT    <= TEMP.EIGYO_DT ");
        sb.append("             ) R_REIGAI_DT ");
        sb.append("     FROM ");
        sb.append("     ( ");
        sb.append("         SELECT ");
        sb.append("             DTRH.EIGYO_DT ");
        sb.append("             ,DTRH.COMMAND_CD ");
        sb.append("             ,DTRH.TENPO_CD || DTRH.REGI_RB || DTRH.TERMINAL_RB AS RECEIPT_NO ");
        sb.append("             ,DTRH.CASHIER_CD ");
        sb.append("             ,DTRM.SEQ_RB ");
        sb.append("             ,DTRM.SYOHIN_CD ");
        sb.append("             ,DTRH.TENPO_CD ");
        sb.append("             ,CASE ");
        sb.append("                 WHEN DTRM.TEIKAN_FG = '2' ");
        sb.append("                 THEN NVL(DTRM.HANBAI_WEIGHT_QT, '0') ");
        sb.append("                 ELSE NVL(DTRM.SURYO_QT, '0') ");
        sb.append("             END HANBAI_TANI_QT ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (S)
        //sb.append("             ,NVL(DTRM.BAITANKA_VL, '0')                                  AS REG_SELL_WOT ");
        //sb.append("             ,NVL(TO_NUMBER(DPAI.ACTUAL_SELL_WOT), '0')                   AS ACTUAL_SELL_WOT ");
        // #6271 URIB830010 Mod 2020.10.30 KHAI.NN (S)
        //sb.append("             ,NVL(TO_NUMBER(DPAI.ACTUAL_SELL), '0')                   AS ACTUAL_SELL ");
        sb.append("             ,NVL(TO_NUMBER(DPAI.ACTUAL_SELL_WOT), '0')                   AS ACTUAL_SELL_WOT ");
        // #6271 URIB830010 Mod 2020.10.30 KHAI.NN (E)
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (E)
        // #6712 MOD 2022.12.19 SIEU.D (S)
        sb.append("             ,NVL(TO_NUMBER(DPAI.REG_SELL_WOT), '0')                   AS REG_SELL_WOT ");
        sb.append("             ,NVL(TO_NUMBER(DPAI.GST_TAX), '0')                   AS GST_TAX ");
        // #6712 MOD 2022.12.19 SIEU.D (E)
        // #6567 ADD 2022.05.11 SIEU.D (S)
        sb.append("             ,DTRH.EIGYO_DT || DTRH.SALES_TS AS SALE_TS ");
        // #6567 ADD 2022.05.11 SIEU.D (E)
        sb.append("         FROM ");
        sb.append("             DT_TEN_RECEIPT_H  DTRH ");
        sb.append("         INNER JOIN ");
        sb.append("             DT_TEN_RECEIPT_M DTRM ");
        sb.append("         ON  DTRH.EIGYO_DT = DTRM.EIGYO_DT ");
        sb.append("         AND DTRH.COMP_CD = DTRM.COMP_CD ");
        sb.append("         AND DTRH.COMMAND_CD = DTRM.COMMAND_CD ");
        sb.append("         AND DTRH.TENPO_CD = DTRM.TENPO_CD ");
        sb.append("         AND DTRH.REGI_RB = DTRM.REGI_RB ");
        sb.append("         AND DTRH.TERMINAL_RB = DTRM.TERMINAL_RB ");
        sb.append("         INNER JOIN ");
        sb.append("             DT_POS_A_ITEM DPAI ");
        sb.append("         ON  DPAI.EIGYO_DT = DTRM.EIGYO_DT ");
        sb.append("         AND DPAI.COMMAND = DTRM.COMMAND_CD ");
        sb.append("         AND DPAI.SKU = DTRM.SYOHIN_CD ");
        sb.append("         AND TO_NUMBER(DPAI.ODR_LINE_IDX) = DTRM.SEQ_RB ");
        sb.append("         AND '00' || DPAI.STORE = DTRM.TENPO_CD ");
        sb.append("         AND DPAI.POS = DTRM.REGI_RB ");
        sb.append("         AND DPAI.TRANS_NO = DTRM.TERMINAL_RB ");
        sb.append("     ) TEMP ");
        sb.append(" ) MAIN ");
        sb.append(" INNER JOIN ");
        sb.append(" ( ");
        sb.append("     SELECT ");
        sb.append("        RS1.* ");
        sb.append("        ,( SELECT ");
        sb.append("                 MAX(YUKO_DT) AS YUKO_DT ");
        sb.append("           FROM ");
        sb.append("                 R_TAX_RATE RTR1 ");
        sb.append("           WHERE ");
        sb.append("                 RTR1.TAX_RATE_KB = RS1.TAX_RATE_KB ");
        sb.append("           AND   RTR1.YUKO_DT <= RS1.YUKO_DT ");
        sb.append("           AND   RTR1.DELETE_FG = '" + UriageCommonConstants.FG_OFF + "' ");
        sb.append("        ) AS TAX_RATE_DT ");
        sb.append("     FROM ");
        sb.append("         R_SYOHIN RS1 ");
        sb.append(" ) RS ");
        sb.append(" ON  RS.SYOHIN_CD = MAIN.SYOHIN_CD");
        sb.append(" AND RS.YUKO_DT = MAIN.R_YUKO_DT ");
        // #6252 URIB830010 Add 2020.10.15 KHAI.NN (S)
        sb.append(" AND RS.DELETE_FG = '"+ UriageCommonConstants.FG_OFF +"' ");
        // #6252 URIB830010 Add 2020.10.15 KHAI.NN (E)
        sb.append(" INNER JOIN ");
        sb.append("     R_TAX_RATE RTR ");
        sb.append(" ON  RTR.TAX_RATE_KB = RS.TAX_RATE_KB");
        sb.append(" AND RTR.YUKO_DT = RS.TAX_RATE_DT ");
        sb.append(" AND RTR.DELETE_FG = '" + UriageCommonConstants.FG_OFF + "' ");
        sb.append(" LEFT JOIN ");
        sb.append("     R_NAMECTF RN ");
        sb.append(" ON  RN.SYUBETU_NO_CD = '3030_vi_VN' ");
        sb.append(" AND RN.CODE_CD = RS.HACHU_TANI_NA ");
        sb.append(" AND RN.DELETE_FG = '" + UriageCommonConstants.FG_OFF + "' ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (S)
        //sb.append(" LEFT JOIN ");
        sb.append(" INNER JOIN ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (E)
        sb.append("     R_TENSYOHIN_REIGAI RTSR ");
        sb.append(" ON  RTSR.SYOHIN_CD = MAIN.SYOHIN_CD ");
        sb.append(" AND RTSR.TENPO_CD = MAIN.TENPO_CD ");
        sb.append(" AND RTSR.YUKO_DT = MAIN.R_REIGAI_DT ");
        // #6252 URIB830010 Add 2020.10.15 KHAI.NN (S)
        sb.append(" AND RTSR.DELETE_FG = '" + UriageCommonConstants.FG_OFF + "' ");
        // #6252 URIB830010 Add 2020.10.15 KHAI.NN (E)
        sb.append(" LEFT JOIN ");
        sb.append(" ( ");
        sb.append("     SELECT ");
        sb.append("        TEMP.EIGYO_DT ");
        sb.append("        ,TEMP.COMMAND_CD ");
        sb.append("        ,TEMP.RECEIPT_NO ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (S)
        //sb.append("        ,COUNT(*) AS HENPIN_SU ");
        sb.append("        ,COUNT(*) AS SALES_TIMES ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (E)
        sb.append("     FROM ");
        sb.append("     (  ");
        sb.append("         SELECT ");
        sb.append("             DTRH.EIGYO_DT ");
        // #6285 URIB830010 Upd 2020.12.10 THONG.VQ (S)
        //sb.append("             ,DTRM.COMMAND_CD ");
        sb.append("             ,DTRH.COMMAND_CD ");
        // #6285 URIB830010 Upd 2020.12.10 THONG.VQ (E)
        sb.append("             ,DTRH.TENPO_CD || DTRH.REGI_RB || DTRH.TERMINAL_RB AS RECEIPT_NO ");
        // #6285 URIB830010 DEL 2020.12.10 THONG.VQ (S)
        //sb.append("             ,CASE ");
        //sb.append("                 WHEN DTRM.TEIKAN_FG = '2' ");
        //sb.append("                 THEN NVL(DTRM.HANBAI_WEIGHT_QT, '0') ");
        //sb.append("                 ELSE NVL(DTRM.SURYO_QT, '0') ");
        //sb.append("             END HANBAI_TANI_QT ");
        // #6285 URIB830010 DEL 2020.12.10 THONG.VQ (E)
        sb.append("         FROM ");
        sb.append("             DT_TEN_RECEIPT_H DTRH ");
        // #6285 URIB830010 DEL 2020.12.10 THONG.VQ (S)
        //sb.append("         INNER JOIN ");
        //sb.append("             DT_TEN_RECEIPT_M DTRM ");
        //sb.append("         ON  DTRH.EIGYO_DT = DTRM.EIGYO_DT ");
        //sb.append("         AND DTRH.COMP_CD = DTRM.COMP_CD ");
        //sb.append("         AND DTRH.COMMAND_CD = DTRM.COMMAND_CD ");
        //sb.append("         AND DTRH.TENPO_CD = DTRM.TENPO_CD ");
        //sb.append("         AND DTRH.REGI_RB = DTRM.REGI_RB ");
        //sb.append("         AND DTRH.TERMINAL_RB = DTRM.TERMINAL_RB ");
        // #6285 URIB830010 DEL 2020.12.10 THONG.VQ (E)
        sb.append("     ) TEMP ");
        sb.append("     WHERE ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (S)
        //sb.append("         TEMP.HANBAI_TANI_QT < 0 ");
        // #6285 URIB830010 Upd 2020.12.10 THONG.VQ (S)
        //sb.append("         TEMP.HANBAI_TANI_QT > 0 ");
        sb.append("         TEMP.COMMAND_CD = '0043' ");
        // #6285 URIB830010 Add 2020.12.10 THONG.VQ (E)
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (E)
        sb.append("     GROUP BY ");
        sb.append("        TEMP.EIGYO_DT ");
        sb.append("        ,TEMP.COMMAND_CD ");
        sb.append("        ,TEMP.RECEIPT_NO ");
        sb.append(" ) SUB ");
        sb.append(" ON  MAIN.EIGYO_DT = SUB.EIGYO_DT ");
        sb.append(" AND MAIN.COMMAND_CD = SUB.COMMAND_CD ");
        sb.append(" AND MAIN.RECEIPT_NO = SUB.RECEIPT_NO ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (S)
        //sb.append(" WHERE ");
        //sb.append("     MAIN.EIGYO_DT = '").append(LAST_DT).append("' ");
        sb.append(" ) RECEIPT_INFO ");
        sb.append(" WHERE ");
        sb.append("     RECEIPT_INFO.EIGYO_DT = '").append(LAST_DT).append("' ");
        sb.append(" GROUP BY ");
        sb.append("     RECEIPT_INFO.EIGYO_DT ");
        sb.append("     ,RECEIPT_INFO.RECEIPT_NO ");
        sb.append("     ,RECEIPT_INFO.SALES_KB ");
        sb.append("     ,RECEIPT_INFO.ZAIKO_SYOHIN_CD ");
        sb.append("     ,RECEIPT_INFO.REC_HINMEI_KANJI_NA ");
        sb.append("     ,RECEIPT_INFO.HANBAI_TANI_NA ");
        sb.append("     ,RECEIPT_INFO.TENPO_CD ");
        sb.append("     ,RECEIPT_INFO.BAITANKA_NUKI_VL ");
        sb.append("     ,RECEIPT_INFO.TAX_RT ");
        // #6249 URIB830010 Mod 2020.10.09 KHAI.NN (E)
        sb.append(" ) ");

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
