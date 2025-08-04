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
 *  <p>タイトル: IfTreARInvoiceCsvCreateDao クラス</p>
 *  <p>説明: 説明: TRE向けIFファイル作成（A/R Invoice）を作成する。</p>
 *  <p>著作権: Copyright (c) 2020</p>
 *  <p>会社名: VVC</p>
 *  @author VINX
 *  @Version 1.00 (2021.10.12) KHOI.ND MKHK対応
 *  @Version 1.01 (2021.11.18) DUY.HK #6363 MKHK対応
 *  @Version 1.02 (2022.01.06) KHAI.NN #6363 MKHK対応
 *  @Version 1.03 (2022.01.26) KHAI.NN #6497 MKHK対応
 *  @Version 1.04 (2022.02.22) KHAI.NN #6363 MKHK対応
 *  @Version 1.05 (2024.03.23) DINH.TP #18254 MKHK対応
 *  @Version 1.06 (2024.11.22) SIEU.D #30457 MKHK対応
 */
public class IfTreARInvoiceCsvCreateDao implements DaoIf {

    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    private static final String SYSTEM_DATE = FiResorceUtility.getDBServerTime();

    private static final String PATH = FiResorceUtility.getPropertie("IF_TRE_PATH");

    private static final String IF_TRE_AR_INVOICE_FILENAME = FiResorceUtility.getPropertie("IF_TRE_AR_INVOICE_FILENAME");

    private static final String OUTPUT_CHAR_SET = "UTF-8";

    private static final String LINE_FEED_CHAR = "\r\n";

    // #6363 Mod 2022.02.22 KHAI.NN (S)
    //private static final String COMMAND_CD = "0043";
    /** コマンドコード：売上 */
    private static final String COMMAND_URIAGE_CD = "0043";
    
    /** コマンドコード：一部返品 */
    private static final String COMMAND_ICHIBU_HENPIN_CD = "0044";
    
    /** コマンドコード：全返品 */
    private static final String COMMAND_ZEN_HENPIN_CD = "0045";
    
    /** 販売タイプ：売上 */
    private static final String SALES_TYPE_URIAGE_CD = "0001";
    
    private static final String CHECK_RANGE = FiResorceUtility.getPropertie("TRE_PAYMENT_INFO_CHECK_RANGE");
    private static final String CHECK_DT = DateChanger.addDate(BATCH_DT, Integer.parseInt(CHECK_RANGE)*(-1));
    /** TRE送ったフラグ：未送信*/
    private static final String TRE_SEND_KB_UNSEND = "0";
    // #6363 Mod 2022.02.22 KHAI.NN (E)
    
    private static final String COMP_CD = "0000";

    private static final String SHIHARAI_SYUBETSU_SEQ = "0100";

    // #18254 Add 2024.03.23 DINH.TP (S)
    // Purchase contract
    private static final String REBATE_KEIYAKU_SYUBETU_01 = "01";
    // Sales contract
    private static final String REBATE_KEIYAKU_SYUBETU_02 = "02";
    // リベート契約種別03宣伝協賛金
    private static final String REBATE_KEIYAKU_SYUBETU_03 = "03";
    // 5:契約期間終了
    private static final String REBATE_KEIYAKU_STATUS_5 = "5";
    // #18254 Add 2024.03.23 DINH.TP (E)

    private static final String DELETE_FG = "0";

    private static final int MODE_STR = 1;

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + " TRE向けIFファイル作成（A/R Invoice）を作成する。";
        PreparedStatementEx ps = null;
        ResultSet rs = null;
        // #18254 ADD 2024.05.13 TUNG.LT (S)
        Date systemDate = new SimpleDateFormat("yyyyMMddhhmmss").parse(SYSTEM_DATE);
        String fileNameRebate = IF_TRE_AR_INVOICE_FILENAME.replaceAll("yymmddhhmmss", new SimpleDateFormat("yyMMddhhmmss").format(systemDate));
        String fileNameUriage = "URIAGE_" + IF_TRE_AR_INVOICE_FILENAME.replaceAll("yymmddhhmmss", new SimpleDateFormat("yyMMddhhmmss").format(systemDate));
        // #18254 ADD 2024.05.13 TUNG.LT (E)
        
        /** ログを出力*/
        invoker.infoLog(strUserId + "　：　WK_IF_TRE_AR_INVOICEの削除処理を開始します。");

        /** テーブルWK_IF_TRE_AR_INVOICEを切り捨てます */ 
        invoker.infoLog(strUserId + "　：　削除処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(getTreARInvoiceTruncateStatement());
        ps.execute();
        invoker.infoLog(strUserId + "　：　TRUNCATEしました。");
        invoker.infoLog(strUserId + "　：　削除処理を終了します。");
        
        /** WK_IF_TRE_AR_INVOICEに挿入します */ 
        invoker.infoLog(strUserId + "　：　WK_IF_TRE_AR_INVOICEに挿入します。");
        int count = 0;
        ps = invoker.getDataBase().prepareStatement(getTreARInvoiceInsertStatement());
        count = ps.executeUpdate();

        invoker.infoLog(strUserId + "　：　" + count + " データの行が挿入されました。");
        invoker.infoLog(strUserId + "　：　挿入が完了しました。");

        /** csvファイルを作成する */
        invoker.infoLog(strUserId + "　：　csvファイルの作成プロセスを開始します。");

        count = 0;
        // #18254 MOD 2024.05.13 TUNG.LT (S)
        // ps = invoker.getDataBase().prepareStatement(getTreARInvoiceQueryStatement());
        ps = invoker.getDataBase().prepareStatement(getUriageTreARInvoiceQueryStatement());
        // #18254 MOD 2024.05.13 TUNG.LT (E)
        rs = ps.executeQuery();

        // #18254 MOD 2024.05.13 TUNG.LT (S)
        // // #6497 Mod 2022.01.26 KHAI.NN (S)
        // //count = createCSVFile(rs);
        // if (rs.next()) {
        //     count = createCSVFile(rs);
        // }
        // // #6497 Mod 2022.01.26 KHAI.NN (E)
        //
        // if (rs.next()) {
        //     count = createCSVFile(rs);
        // }
        // // #6497 Mod 2022.01.26 KHAI.NN (E)
        if (rs.next()) {
            count = createCSVFile(rs, fileNameUriage);
        }
        invoker.infoLog(strUserId + "　：　" + count + " データの行がエクスポートされました。");

        count = 0;
        ps = invoker.getDataBase().prepareStatement(getRebateTreARInvoiceQueryStatement());
        rs = ps.executeQuery();
        if (rs.next()) {
            count = createCSVFile(rs, fileNameRebate);
        }
        invoker.infoLog(strUserId + "　：　" + count + " データの行がエクスポートされました。");
        // #18254 MOD 2024.05.13 TUNG.LT (E)
        invoker.infoLog(strUserId + "　：　ファイル作成プロセスが完了しました。");

        if(rs!=null) {
            rs.close();
        }

        /** プロセスが完了しました */
        invoker.infoLog(strUserId + "　：　けIFファイル作成処理を終了します。");
    }

    // #18254 MOD 2024.05.13 TUNG.LT (S)
    // private int createCSVFile(ResultSet rs) throws Exception {
        
        // int cnt = 0;
        // Date systemDate = new SimpleDateFormat("yyyyMMddhhmmss").parse(SYSTEM_DATE);
        // String fileName = IF_TRE_AR_INVOICE_FILENAME.replaceAll("yymmddhhmmss", new SimpleDateFormat("yyMMddhhmmss").format(systemDate));
    private int createCSVFile(ResultSet rs, String fileName) throws Exception {
        
        int cnt = 0;
    // #18254 MOD 2024.05.13 TUNG.LT (E)
        File dataFile = new File(PATH + "/" + fileName);
        
        // ファイル出力
        BufferedWriter writer = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
            writer = new BufferedWriter(outputStreamWriter);
            writer.write("\ufeff");

            if(rs == null) {
                return cnt;
            }

            // #6363 Del 2022.01.06 KHAI.NN (S)
            //String formattedDocumentDate = new SimpleDateFormat("yyyy/MM/dd").format(systemDate);
            // #6363 Del 2022.01.06 KHAI.NN (E)
            // #6497 Mod 2022.01.26 KHAI.NN (S)
            //while (rs.next()) {
            do {
            // #6497 Mod 2022.01.26 KHAI.NN (E)
                StringBuffer csvLine = new StringBuffer();

                CSVLine line = new CSVLine();

                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("DOCUMENT_TYPE"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("DOCUMENT_NO"), MODE_STR)));
                // #6363 Mod 2022.01.06 KHAI.NN (S)
                //line.addItem(addQuarter(formattedDocumentDate));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("DOCUMENT_DATE"), MODE_STR)));
                // #6363 Mod 2022.01.06 KHAI.NN (E)
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("TENPO_CD"), MODE_STR)));
                line.addItem(addQuarter("HKD"));
                line.addItem(addQuarter("1"));
                line.addItem(addQuarter("COD"));
                // #6363 Mod 2022.01.06 KHAI.NN (S)
                //line.addItem(addQuarter(formattedDocumentDate));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("DOCUMENT_DATE"), MODE_STR)));
                // #6363 Mod 2022.01.06 KHAI.NN (E)
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("DIVISION_NO"), MODE_STR)));
                line.addItem(addQuarter("AR"));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("LINE_NO"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("ACCOUNT_NO"), MODE_STR)));
                // #18254 Add 2024.03.23 DINH.TP (S)
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("PA_NO"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("TERM_FROM"), MODE_STR)));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("TERM_TO"), MODE_STR)));
                line.addItem(addQuarter(convertAmount(rs.getString("PURCHASE_QTY"))));
                line.addItem(addQuarter(convertAmount(rs.getString("PURCHASE_AMT"))));
                line.addItem(addQuarter(convertAmount(rs.getString("SALES_QTY"))));
                line.addItem(addQuarter(convertAmount(rs.getString("SALES_AMT"))));
                line.addItem(addQuarter(convertAmount(rs.getString("FIXED_COST"))));
                // #18254 Add 2024.03.23 DINH.TP (E)
                line.addItem(addQuarter(convertAmount(rs.getString("AMOUNT"))));
                // #18254 Mod 2024.03.23 DINH.TP (S)
                // line.addItem(addQuarter(""));
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("REMARKS"), MODE_STR)));
                // #18254 Mod 2024.03.23 DINH.TP (E)
                line.addItem(addQuarter(FiStringUtility.convert(rs.getString("UPDATE_TS"), MODE_STR)));

                csvLine.append(line.getLine());
                csvLine.append(LINE_FEED_CHAR); // 改行
                // ファイル書出し
                writer.write(csvLine.toString());

                cnt++;

            // #6497 Mod 2022.01.26 KHAI.NN (S)
            //}
            } while(rs.next());
            // #6497 Mod 2022.01.26 KHAI.NN (E)
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return cnt;
    }

    // #18254 MOD 2024.05.13 TUNG.LT (S)
    // private String getTreARInvoiceQueryStatement() {
    //     StringBuffer sb = new StringBuffer();
    //     sb.append(" SELECT * FROM WK_IF_TRE_AR_INVOICE ");
    //     return sb.toString();
    // }
    
    private String getUriageTreARInvoiceQueryStatement() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT * FROM WK_IF_TRE_AR_INVOICE WHERE DOCUMENT_TYPE <> 'D' ");
        return sb.toString();
    }
    
    private String getRebateTreARInvoiceQueryStatement() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT * FROM WK_IF_TRE_AR_INVOICE WHERE DOCUMENT_TYPE = 'D' ");
        return sb.toString();
    }
    // #18254 MOD 2024.05.13 TUNG.LT (S)

    private String getTreARInvoiceTruncateStatement() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_IF_TRE_AR_INVOICE ");
        return sb.toString();
    }
    
    private String getTreARInvoiceInsertStatement() {
        String lastDt = DateChanger.addDate(BATCH_DT, -2);
        // #30457 ADD 2024.11.22 SIEU.D (S)
        String yesterdayDt = DateChanger.addDate(BATCH_DT, -1);
        // #30457 ADD 2024.11.22 SIEU.D (E)

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO WK_IF_TRE_AR_INVOICE ( ");
        sb.append("     DOCUMENT_TYPE, ");
        sb.append("     DOCUMENT_NO, ");
        // #6363 Add 2022.01.06 KHAI.NN (S)
        sb.append("     DOCUMENT_DATE, ");
        // #6363 Add 2022.01.06 KHAI.NN (E)
        sb.append("     TENPO_CD, ");
        sb.append("     DIVISION_NO, ");
        sb.append("     LINE_NO, ");
        sb.append("     ACCOUNT_NO, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("     PA_NO, ");
        sb.append("     TERM_FROM, ");
        sb.append("     TERM_TO, ");
        sb.append("     PURCHASE_QTY, ");
        sb.append("     PURCHASE_AMT, ");
        sb.append("     SALES_QTY, ");
        sb.append("     SALES_AMT, ");
        sb.append("     FIXED_COST, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("     AMOUNT, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("     REMARKS, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("     UPDATE_TS ");
        sb.append(" ) ");
        sb.append(" WITH COMMON_TABLE AS ( ");
        sb.append("     SELECT ");
        sb.append("         RECEIPT_INFO.EIGYO_DT, ");
        sb.append("         RECEIPT_INFO.TENPO_CD, ");
        sb.append("         RECEIPT_INFO.UPDATE_TS, ");
        sb.append("         RECEIPT_INFO.SALES_KB, ");
        sb.append("         RECEIPT_INFO.RECEIPT_NO, ");
        sb.append("         RECEIPT_INFO.ZAIKO_SYOHIN_CD, ");
        sb.append("         ROUND( ");
        sb.append("             SUM( ");
        sb.append("                 RECEIPT_INFO.HANBAI_TANI_QT * RECEIPT_INFO.BAITANKA_NUKI_VL ");
        sb.append("             ) ");
        // #6363 Mod 2022.02.22 KHAI.NN (S)
        //sb.append("         ) AS REG_SELL_WOT, ");
        sb.append("         , 2) AS REG_SELL_WOT, ");
        // #6363 Mod 2022.02.22 KHAI.NN (E)
        sb.append("         ROUND( ");
        sb.append("             SUM( ");
        sb.append("                 ( ");
        sb.append("                     RECEIPT_INFO.HANBAI_TANI_QT * RECEIPT_INFO.BAITANKA_NUKI_VL ");
        sb.append("                 ) - RECEIPT_INFO.ACTUAL_SELL_WOT ");
        sb.append("             ) ");
        // #6363 Mod 2022.02.22 KHAI.NN (S)
        //sb.append("         ) * (-1) AS TOTAL_DISCOUNT ");
        sb.append("         , 2) * (-1) AS TOTAL_DISCOUNT ");
        // #6363 Mod 2022.02.22 KHAI.NN (E)
        sb.append("     FROM ");
        sb.append("         ( ");
        sb.append("             SELECT ");
        sb.append("                 DTRH.EIGYO_DT, ");
        sb.append("                 DTRH.TENPO_CD, ");
        sb.append("                 DTRH.UPDATE_TS, ");
        sb.append("                 CASE ");
        // #6363 Mod 2022.02.22 KHAI.NN (S)
        //sb.append("                     WHEN NVL(SUB.SALES_TIMES, '0') = 0 THEN '2' ");
        //sb.append("                     ELSE '1' ");
        sb.append("                     WHEN NVL(SUB.SALES_TIMES, '0') > 0 THEN '1' ");
        sb.append("                     ELSE '2' ");
        // #6363 Mod 2022.02.22 KHAI.NN (E)
        sb.append("                 END AS SALES_KB, ");
        sb.append("                 ROUND( ");
        sb.append("                     CASE ");
        sb.append("                         WHEN DTRM.TEIKAN_FG = '2' THEN NVL(DTRM.HANBAI_WEIGHT_QT, '0') ");
        sb.append("                         ELSE NVL(DTRM.SURYO_QT, '0') ");
        sb.append("                     END ");
        sb.append("                 ) AS HANBAI_TANI_QT, ");
        sb.append("                 CASE ");
        sb.append("                     WHEN RS.ZEI_KB = '3' THEN ROUND( ");
        sb.append("                         NVL(RTSR.BAITANKA_VL, NVL(RS.BAITANKA_VL, '0')), ");
        sb.append("                         2 ");
        sb.append("                     ) ");
        sb.append("                     ELSE ROUND( ");
        sb.append("                         ( ");
        sb.append("                             NVL(RTSR.BAITANKA_VL, NVL(RS.BAITANKA_VL, '0')) * 100 ");
        sb.append("                         ) / (100 + RTR.TAX_RT), ");
        sb.append("                         2 ");
        sb.append("                     ) ");
        sb.append("                 END AS BAITANKA_NUKI_VL, ");
        sb.append("                 NVL(TO_NUMBER(DPAI.ACTUAL_SELL_WOT), '0') AS ACTUAL_SELL_WOT, ");
        sb.append("                 SUBSTR( ");
        sb.append("                     DTRH.TENPO_CD || DTRH.REGI_RB || DTRH.TERMINAL_RB, ");
        sb.append("                     3 ");
        sb.append("                 ) RECEIPT_NO, ");
        sb.append("                 RS.ZAIKO_SYOHIN_CD ");
        sb.append("             FROM ");
        sb.append("                 DT_TEN_RECEIPT_H DTRH ");
        sb.append("                 INNER JOIN DT_TEN_RECEIPT_M DTRM ON DTRH.EIGYO_DT = '" + lastDt + "' ");
        sb.append("                 AND DTRH.EIGYO_DT = DTRM.EIGYO_DT ");
        sb.append("                 AND DTRH.COMP_CD = DTRM.COMP_CD ");
        sb.append("                 AND DTRH.COMMAND_CD = DTRM.COMMAND_CD ");
        sb.append("                 AND DTRH.TENPO_CD = DTRM.TENPO_CD ");
        sb.append("                 AND DTRH.REGI_RB = DTRM.REGI_RB ");
        sb.append("                 AND DTRH.TERMINAL_RB = DTRM.TERMINAL_RB ");
        sb.append("                 INNER JOIN DT_POS_A_ITEM DPAI ON DPAI.EIGYO_DT = DTRM.EIGYO_DT ");
        sb.append("                 AND DPAI.COMMAND = DTRM.COMMAND_CD ");
        sb.append("                 AND DPAI.SKU = DTRM.SYOHIN_CD ");
        sb.append("                 AND TO_NUMBER(DPAI.ODR_LINE_IDX) = DTRM.SEQ_RB ");
        sb.append("                 AND '00' || DPAI.STORE = DTRM.TENPO_CD ");
        sb.append("                 AND DPAI.POS = DTRM.REGI_RB ");
        sb.append("                 AND DPAI.TRANS_NO = DTRM.TERMINAL_RB ");
        sb.append("                 LEFT JOIN ( ");
        sb.append("                     SELECT ");
        sb.append("                         SYOHIN_CD, ");
        sb.append("                         MAX(YUKO_DT) AS R_YUKO_DT ");
        sb.append("                     FROM ");
        sb.append("                         R_SYOHIN ");
        sb.append("                     WHERE ");
        sb.append("                         YUKO_DT <= '" + lastDt + "' ");
        sb.append("                         AND DELETE_FG = '" + DELETE_FG  + "' ");
        sb.append("                     GROUP BY ");
        sb.append("                         SYOHIN_CD ");
        sb.append("                 ) RS_MAX_YUKO_DT ON RS_MAX_YUKO_DT.SYOHIN_CD = DTRM.SYOHIN_CD ");
        sb.append("                 LEFT JOIN ( ");
        sb.append("                     SELECT ");
        sb.append("                         TENPO_CD, ");
        sb.append("                         SYOHIN_CD, ");
        sb.append("                         MAX(YUKO_DT) AS R_REIGAI_DT ");
        sb.append("                     FROM ");
        sb.append("                         R_TENSYOHIN_REIGAI ");
        sb.append("                     WHERE ");
        sb.append("                         YUKO_DT <= '" + lastDt + "' ");
        sb.append("                         AND DELETE_FG = '" + DELETE_FG  + "' ");
        sb.append("                     GROUP BY ");
        sb.append("                         TENPO_CD, ");
        sb.append("                         SYOHIN_CD ");
        sb.append("                 ) RTR_MAX_YUKO_DT ON RTR_MAX_YUKO_DT.TENPO_CD = DTRH.TENPO_CD ");
        sb.append("                 AND RTR_MAX_YUKO_DT.SYOHIN_CD = DTRM.SYOHIN_CD ");
        sb.append("                 INNER JOIN ( ");
        sb.append("                     SELECT ");
        sb.append("                         RS1.*, ");
        sb.append("                         ( ");
        sb.append("                             SELECT ");
        sb.append("                                 MAX(YUKO_DT) AS YUKO_DT ");
        sb.append("                             FROM ");
        sb.append("                                 R_TAX_RATE RTR1 ");
        sb.append("                             WHERE ");
        sb.append("                                 RTR1.TAX_RATE_KB = RS1.TAX_RATE_KB ");
        sb.append("                                 AND RTR1.YUKO_DT <= RS1.YUKO_DT ");
        sb.append("                                 AND RTR1.DELETE_FG = '" + DELETE_FG  + "' ");
        sb.append("                         ) AS TAX_RATE_DT ");
        sb.append("                     FROM ");
        sb.append("                         R_SYOHIN RS1 ");
        sb.append("                 ) RS ON RS.SYOHIN_CD = RS_MAX_YUKO_DT.SYOHIN_CD ");
        sb.append("                 AND RS.YUKO_DT = RS_MAX_YUKO_DT.R_YUKO_DT ");
        sb.append("                 INNER JOIN R_TAX_RATE RTR ON RTR.TAX_RATE_KB = RS.TAX_RATE_KB ");
        sb.append("                 AND RTR.YUKO_DT = RS.TAX_RATE_DT ");
        sb.append("                 AND RTR.DELETE_FG = '" + DELETE_FG  + "' ");
        sb.append("                 LEFT JOIN R_NAMECTF RN ON RN.SYUBETU_NO_CD = '3030_vi_VN' ");
        sb.append("                 AND RN.CODE_CD = RS.HACHU_TANI_NA ");
        sb.append("                 AND RN.DELETE_FG = '" + DELETE_FG  + "' ");
        sb.append("                 INNER JOIN R_TENSYOHIN_REIGAI RTSR ON RTSR.SYOHIN_CD = DTRM.SYOHIN_CD ");
        sb.append("                 AND RTSR.TENPO_CD = DTRH.TENPO_CD ");
        sb.append("                 AND RTSR.YUKO_DT = RTR_MAX_YUKO_DT.R_REIGAI_DT ");
        sb.append("                 AND RTSR.DELETE_FG = '" + DELETE_FG  + "' ");
        sb.append("                 LEFT JOIN ( ");
        sb.append("                     SELECT ");
        // #6363 Mod 2022.02.22 KHAI.NN (S)
        //sb.append("                         COMMAND_CD, ");
        //sb.append("                         TENPO_CD || REGI_RB || TERMINAL_RB AS RECEIPT_NO, ");
        sb.append("                         DTRH.COMMAND_CD, ");
        sb.append("                         DTRM.SALES_TYPE, ");
        sb.append("                         DTRH.TENPO_CD || DTRH.REGI_RB || DTRH.TERMINAL_RB AS RECEIPT_NO, ");
        // #6363 Mod 2022.02.22 KHAI.NN (E)
        sb.append("                         COUNT(1) AS SALES_TIMES ");
        sb.append("                     FROM ");
        // #6363 Mod 2022.02.22 KHAI.NN (S)
        //sb.append("                         DT_TEN_RECEIPT_H ");
        sb.append("                         DT_TEN_RECEIPT_H DTRH ");
        sb.append("                     INNER JOIN ");
        sb.append("                         DT_TEN_RECEIPT_M DTRM ");
        sb.append("                     ON DTRH.EIGYO_DT = DTRM.EIGYO_DT ");
        sb.append("                     AND DTRH.COMP_CD = DTRM.COMP_CD ");
        sb.append("                     AND DTRH.COMMAND_CD = DTRM.COMMAND_CD ");
        sb.append("                     AND DTRH.TENPO_CD = DTRM.TENPO_CD ");
        sb.append("                     AND DTRH.REGI_RB = DTRM.REGI_RB ");
        sb.append("                     AND DTRH.TERMINAL_RB = DTRM.TERMINAL_RB ");
        // #6363 Mod 2022.02.22 KHAI.NN (E)
        sb.append("                     WHERE ");
        // #6363 Mod 2022.02.22 KHAI.NN (S)
        //sb.append("                         COMMAND_CD = '" + COMMAND_CD + "' ");
        //sb.append("                         AND EIGYO_DT = '" + lastDt + "' ");
        sb.append("                         (( DTRH.COMMAND_CD IN ('" + COMMAND_URIAGE_CD + "','" + COMMAND_ICHIBU_HENPIN_CD + "') ");
        sb.append("                         AND DTRM.SALES_TYPE = '" + SALES_TYPE_URIAGE_CD + "') ");
        sb.append("                         OR DTRH.COMMAND_CD = '" + COMMAND_ZEN_HENPIN_CD + "') ");
        sb.append("                         AND DTRH.EIGYO_DT = '" + lastDt + "' ");
        // #6363 Mod 2022.02.22 KHAI.NN (E)
        sb.append("                     GROUP BY ");
        // #6363 Mod 2022.02.22 KHAI.NN (S)
        //sb.append("                         COMMAND_CD, ");
        //sb.append("                         TENPO_CD || REGI_RB || TERMINAL_RB ");
        sb.append("                         DTRH.COMMAND_CD, ");
        sb.append("                         DTRM.SALES_TYPE, ");
        sb.append("                         DTRH.TENPO_CD || DTRH.REGI_RB || DTRH.TERMINAL_RB ");
        // #6363 Mod 2022.02.22 KHAI.NN (E)
        sb.append("                 ) SUB ON DTRH.COMMAND_CD = SUB.COMMAND_CD ");
        // #6363 Add 2022.02.22 KHAI.NN (S)
        sb.append("                 AND DTRM.SALES_TYPE = SUB.SALES_TYPE ");
        // #6363 Add 2022.02.22 KHAI.NN (E)
        sb.append("                 AND DTRH.TENPO_CD || DTRH.REGI_RB || DTRH.TERMINAL_RB = SUB.RECEIPT_NO ");
        sb.append("         ) RECEIPT_INFO ");
        sb.append("     GROUP BY ");
        sb.append("         RECEIPT_INFO.EIGYO_DT, ");
        sb.append("         RECEIPT_INFO.SALES_KB, ");
        sb.append("         RECEIPT_INFO.TENPO_CD, ");
        sb.append("         RECEIPT_INFO.RECEIPT_NO, ");
        sb.append("         RECEIPT_INFO.UPDATE_TS, ");
        sb.append("         RECEIPT_INFO.ZAIKO_SYOHIN_CD ");
        sb.append(" ) ");
        sb.append(" SELECT ");
        sb.append("     * ");
        sb.append(" FROM ");
        sb.append("     ( ");
        // 店舗売上売価
        sb.append("         SELECT ");
        sb.append("             'I' AS DOCUMENT_TYPE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (S)
        //sb.append("             TENPO_CD || '-' || EIGYO_DT || '-' || '1' AS DOCUMENT_NO, ");
        sb.append("             TENPO_CD || EIGYO_DT || '1' AS DOCUMENT_NO, ");
        sb.append("             TO_CHAR(TO_DATE(EIGYO_DT, 'yyyymmdd'), 'yyyy/MM/dd') AS DOCUMENT_DATE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (E)
        sb.append("             TENPO_CD, ");
        sb.append("             TENPO_CD AS DIVISION_NO, ");
        sb.append("             '1' AS LINE_NO, ");
        sb.append("             'SALES' ACCOUNT_NO, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             NULL PA_NO, ");
        sb.append("             NULL AS TERM_FROM, ");
        sb.append("             NULL AS TERM_TO, ");
        sb.append("             0    AS PURCHASE_QTY, ");
        sb.append("             0    AS PURCHASE_AMT, ");
        sb.append("             0    AS SALES_QTY, ");
        sb.append("             0    AS SALES_AMT, ");
        sb.append("             0    AS FIXED_COST, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             SUM(REG_SELL_WOT) AS AMOUNT, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             '' AS REMARKS, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             TO_CHAR(TO_DATE(UPDATE_TS, 'yyyymmddhh24miss'),'yyyy/MM/dd hh24:mm') UPDATE_TS ");
        sb.append("         FROM ");
        sb.append("             COMMON_TABLE ");
        sb.append("         WHERE SALES_KB = '1' ");
        sb.append("         GROUP BY ");
        sb.append("             UPDATE_TS, ");
        sb.append("             TENPO_CD, ");
        sb.append("             EIGYO_DT ");
        // 値引き
        sb.append("         UNION ALL  ");
        sb.append("         SELECT ");
        sb.append("             'I' AS DOCUMENT_TYPE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (S)
        //sb.append("             TENPO_CD || '-' || EIGYO_DT || '-' || '1' AS DOCUMENT_NO, ");
        sb.append("             TENPO_CD || EIGYO_DT || '1' AS DOCUMENT_NO, ");
        sb.append("             TO_CHAR(TO_DATE(EIGYO_DT, 'yyyymmdd'), 'yyyy/MM/dd') AS DOCUMENT_DATE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (E)
        sb.append("             TENPO_CD, ");
        sb.append("             TENPO_CD AS DIVISION_NO, ");
        sb.append("             '2' AS LINE_NO, ");
        sb.append("             'DISCOUNT' ACCOUNT_NO, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             NULL PA_NO, ");
        sb.append("             NULL AS TERM_FROM, ");
        sb.append("             NULL AS TERM_TO, ");
        sb.append("             0    AS PURCHASE_QTY, ");
        sb.append("             0    AS PURCHASE_AMT, ");
        sb.append("             0    AS SALES_QTY, ");
        sb.append("             0    AS SALES_AMT, ");
        sb.append("             0    AS FIXED_COST, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             SUM(TOTAL_DISCOUNT) AS AMOUNT, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             '' AS REMARKS, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             TO_CHAR(TO_DATE(UPDATE_TS, 'yyyymmddhh24miss'),'yyyy/MM/dd hh24:mm') UPDATE_TS ");
        sb.append("         FROM ");
        sb.append("             COMMON_TABLE ");
        sb.append("         WHERE ");
        sb.append("             TOTAL_DISCOUNT <> 0 ");
        sb.append("             AND SALES_KB = '1' ");
        sb.append("         GROUP BY ");
        sb.append("             UPDATE_TS, ");
        sb.append("             TENPO_CD, ");
        sb.append("             EIGYO_DT ");
        // 店舗売上返品売価
        sb.append("         UNION ALL  ");
        sb.append("         SELECT ");
        sb.append("             'C' AS DOCUMENT_TYPE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (S)
        //sb.append("             TENPO_CD || '-' || EIGYO_DT || '-' || '2' AS DOCUMENT_NO, ");
        sb.append("             TENPO_CD || EIGYO_DT || '2' AS DOCUMENT_NO, ");
        sb.append("             TO_CHAR(TO_DATE(EIGYO_DT, 'yyyymmdd'), 'yyyy/MM/dd') AS DOCUMENT_DATE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (E)
        sb.append("             TENPO_CD, ");
        sb.append("             TENPO_CD AS DIVISION_NO, ");
        sb.append("             '1' AS LINE_NO, ");
        sb.append("             'SALES' ACCOUNT_NO, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             NULL PA_NO, ");
        sb.append("             NULL AS TERM_FROM, ");
        sb.append("             NULL AS TERM_TO, ");
        sb.append("             0    AS PURCHASE_QTY, ");
        sb.append("             0    AS PURCHASE_AMT, ");
        sb.append("             0    AS SALES_QTY, ");
        sb.append("             0    AS SALES_AMT, ");
        sb.append("             0    AS FIXED_COST, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             SUM(REG_SELL_WOT) AS AMOUNT, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             '' AS REMARKS, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             TO_CHAR(TO_DATE(UPDATE_TS, 'yyyymmddhh24miss'),'yyyy/MM/dd hh24:mm') UPDATE_TS ");
        sb.append("         FROM ");
        sb.append("             COMMON_TABLE ");
        sb.append("         WHERE SALES_KB = '2' ");
        sb.append("         GROUP BY ");
        sb.append("             UPDATE_TS, ");
        sb.append("             TENPO_CD, ");
        sb.append("             EIGYO_DT ");
        // 値引き返品
        sb.append("         UNION ALL  ");
        sb.append("         SELECT ");
        sb.append("             'C' AS DOCUMENT_TYPE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (S)
        //sb.append("             TENPO_CD || '-' || EIGYO_DT || '-' || '2' AS DOCUMENT_NO, ");
        sb.append("             TENPO_CD || EIGYO_DT || '2' AS DOCUMENT_NO, ");
        sb.append("             TO_CHAR(TO_DATE(EIGYO_DT, 'yyyymmdd'), 'yyyy/MM/dd') AS DOCUMENT_DATE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (E)
        sb.append("             TENPO_CD, ");
        sb.append("             TENPO_CD AS DIVISION_NO, ");
        sb.append("             '2' AS LINE_NO, ");
        sb.append("             'DISCOUNT' ACCOUNT_NO, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             NULL PA_NO, ");
        sb.append("             NULL AS TERM_FROM, ");
        sb.append("             NULL AS TERM_TO, ");
        sb.append("             0    AS PURCHASE_QTY, ");
        sb.append("             0    AS PURCHASE_AMT, ");
        sb.append("             0    AS SALES_QTY, ");
        sb.append("             0    AS SALES_AMT, ");
        sb.append("             0    AS FIXED_COST, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             SUM(TOTAL_DISCOUNT) AS AMOUNT, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             '' AS REMARKS, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             TO_CHAR(TO_DATE(UPDATE_TS, 'yyyymmddhh24miss'),'yyyy/MM/dd hh24:mm') UPDATE_TS ");
        sb.append("         FROM ");
        sb.append("             COMMON_TABLE ");
        sb.append("         WHERE ");
        sb.append("             TOTAL_DISCOUNT <> 0 ");
        sb.append("             AND SALES_KB = '2' ");
        sb.append("         GROUP BY ");
        sb.append("             UPDATE_TS, ");
        sb.append("             TENPO_CD, ");
        sb.append("             EIGYO_DT ");
        sb.append("         UNION ALL ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("         SELECT ");
        sb.append("             'D' AS DOCUMENT_TYPE, ");
        sb.append("             '" + BATCH_DT + "' || LPAD(ROWNUM, 7, '0') AS DOCUMENT_NO, ");
        sb.append("             TO_CHAR(TO_DATE(SUBSTR(REBATE_INFO.UPDATE_TS,0,8), 'yyyymmdd'), 'yyyy/MM/dd') AS DOCUMENT_DATE, ");
        sb.append("             REBATE_INFO.TORIHIKISAKI_CD AS TENPO_CD, ");
        sb.append("             REBATE_INFO.TENPO_CD AS DIVISION_NO, ");
        sb.append("             '1' AS LINE_NO, ");
        sb.append("             'REBATE' ACCOUNT_NO, ");
        sb.append("             REBATE_INFO.PA_NO, ");
        sb.append("             REBATE_INFO.CALC_START_DT AS TERM_FROM, ");
        sb.append("             REBATE_INFO.CALC_END_DT AS TERM_TO, ");
        sb.append("             CASE ");
        sb.append("                 WHEN REBATE_INFO.KEIYAKU_SBT_KB = '" + REBATE_KEIYAKU_SYUBETU_01 + "' THEN ");
        sb.append("                     REBATE_INFO.JISEKI_QT ");
        sb.append("                 ELSE ");
        sb.append("                     0 ");
        sb.append("             END AS PURCHASE_QTY, ");
        sb.append("             CASE ");
        sb.append("                 WHEN REBATE_INFO.KEIYAKU_SBT_KB = '" + REBATE_KEIYAKU_SYUBETU_01 + "' THEN ");
        sb.append("                     REBATE_INFO.JISEKI_VL ");
        sb.append("                 ELSE ");
        sb.append("                     0 ");
        sb.append("             END AS PURCHASE_AMT, ");
        sb.append("             CASE ");
        sb.append("                 WHEN REBATE_INFO.KEIYAKU_SBT_KB = '" + REBATE_KEIYAKU_SYUBETU_02 + "' THEN ");
        sb.append("                     REBATE_INFO.JISEKI_QT ");
        sb.append("                 ELSE ");
        sb.append("                     0 ");
        sb.append("             END AS SALES_QTY, ");
        sb.append("             CASE ");
        sb.append("                 WHEN REBATE_INFO.KEIYAKU_SBT_KB = '" + REBATE_KEIYAKU_SYUBETU_02 + "' THEN ");
        sb.append("                     REBATE_INFO.JISEKI_VL ");
        sb.append("                 ELSE ");
        sb.append("                     0 ");
        sb.append("             END AS SALES_AMT, ");
        sb.append("             CASE ");
        sb.append("                 WHEN REBATE_INFO.KEIYAKU_SBT_KB = '" + REBATE_KEIYAKU_SYUBETU_03 + "' THEN ");
        sb.append("                     SUM(REBATE_INFO.REBATE_VL) OVER (PARTITION BY REBATE_INFO.PA_NO) ");
        sb.append("                 ELSE ");
        sb.append("                     0 ");
        sb.append("             END AS FIXED_COST, ");
        sb.append("             REBATE_INFO.REBATE_VL AS AMOUNT, ");
        // #30457 MOD 2024.11.22 SIEU.D (S)
        // sb.append("             REBATE_INFO.REMARKS, ");
        sb.append("             SUBSTRB(REBATE_INFO.REMARKS, 0, 100 ) REMARKS, ");
        // #30457 MOD 2024.11.22 SIEU.D (E)
        sb.append("             TO_CHAR(TO_DATE(REBATE_INFO.UPDATE_TS, 'yyyymmddhh24miss'),'yyyy/MM/dd hh24:mm') UPDATE_TS ");
        sb.append("         FROM ( ");
        sb.append("                SELECT RKK.KEIYAKU_NO||'-'||RKK.KEIYAKU_EDA_RB||RKK.RIREKI_NB AS PA_NO ");
        sb.append("                      ,RKK.TORIHIKISAKI_CD ");
        sb.append("                      ,TO_CHAR(TO_DATE(RKK.CALC_START_DT, 'yyyymmdd'), 'yyyy/MM/dd') CALC_START_DT ");
        sb.append("                      ,TO_CHAR(TO_DATE(RKK.CALC_END_DT, 'yyyymmdd'), 'yyyy/MM/dd') CALC_END_DT ");
        sb.append("                      ,RKK.KEIYAKU_SBT_KB ");
        sb.append("                      ,RKK.REMARKS ");
        sb.append("                      ,RKK.UPDATE_TS ");
        sb.append("                      ,COALESCE(TRIM(RKC.REBATE_KB),DRH.TENPO_CD,DKTJ.TENPO_CD) AS TENPO_CD ");
        sb.append("                      ,SUM(DKTJ.JISEKI_QT) AS JISEKI_QT ");
        sb.append("                      ,SUM(DKTJ.JISEKI_VL) AS JISEKI_VL ");
        sb.append("                      ,NVL(RKC.SET_VL,DRH.HON_HAIFU_MONSUM_VL) AS REBATE_VL ");
        sb.append("                FROM R_KEI_KIHON RKK ");
        sb.append("                LEFT JOIN DT_KEI_TANPIN_JISEKI DKTJ ");
        sb.append("                ON DKTJ.COMP_CD = RKK.COMP_CD ");
        sb.append("                   AND DKTJ.KEIYAKU_NO = RKK.KEIYAKU_NO ");
        sb.append("                   AND DKTJ.KEIYAKU_EDA_RB = RKK.KEIYAKU_EDA_RB ");
        sb.append("                   AND DKTJ.CALC_YM BETWEEN SUBSTR(RKK.CALC_START_DT,0,6) AND SUBSTR(RKK.CALC_END_DT,0,6) ");
        sb.append("                LEFT JOIN DT_RBT_HAIFU DRH ");
        sb.append("                ON DRH.COMP_CD = RKK.COMP_CD ");
        sb.append("                   AND DRH.KEIYAKU_NO = RKK.KEIYAKU_NO ");
        sb.append("                   AND DRH.KEIYAKU_EDA_RB = RKK.KEIYAKU_EDA_RB ");
        sb.append("                   AND ( RKK.KEIYAKU_SBT_KB = '" + REBATE_KEIYAKU_SYUBETU_03 + "' ");
        sb.append("                        OR DRH.TENPO_CD = DKTJ.TENPO_CD ) ");
        sb.append("                LEFT JOIN R_KEI_CALC RKC ");
        sb.append("                ON RKC.COMP_CD = RKK.COMP_CD ");
        sb.append("                   AND RKC.KEIYAKU_NO = RKK.KEIYAKU_NO ");
        sb.append("                   AND RKC.KEIYAKU_EDA_RB = RKK.KEIYAKU_EDA_RB ");
        sb.append("                   AND RKC.RIREKI_NB = RKK.RIREKI_NB ");
        sb.append("                   AND RKK.KEIYAKU_SBT_KB = '" + REBATE_KEIYAKU_SYUBETU_03 + "' ");
        sb.append("                   AND RKK.TENPO_SHITEI_FG = '1' ");
        sb.append("                WHERE RKK.COMP_CD            = '" + COMP_CD + "' ");
        sb.append("                      AND RKK.STATUS_KB = '" + REBATE_KEIYAKU_STATUS_5 + "' ");
        sb.append("                      AND RKK.DELETE_FG = '" + DELETE_FG + "' ");
        // #30457 MOD 2024.11.22 SIEU.D (S)
        // sb.append("                      AND SUBSTR(RKK.UPDATE_TS,0,8) = '" + lastDt + "' ");
        sb.append("                      AND SUBSTR(RKK.UPDATE_TS,0,8) = '" + yesterdayDt + "' ");
        // #30457 MOD 2024.11.22 SIEU.D (E)
        sb.append("                      AND RKK.RIREKI_NB = ( ");
        sb.append("                                           SELECT MAX(RIREKI_NB) ");
        sb.append("                                           FROM R_KEI_KIHON RKK2 ");
        sb.append("                                           WHERE RKK2.COMP_CD = RKK.COMP_CD ");
        sb.append("                                                 AND RKK2.KEIYAKU_NO = RKK.KEIYAKU_NO ");
        sb.append("                                                 AND RKK2.KEIYAKU_EDA_RB = RKK.KEIYAKU_EDA_RB ");
        sb.append("                                          ) ");
        sb.append("                GROUP BY RKK.KEIYAKU_NO||'-'||RKK.KEIYAKU_EDA_RB||RKK.RIREKI_NB ");
        sb.append("                        ,RKK.TORIHIKISAKI_CD ");
        sb.append("                        ,RKK.CALC_START_DT ");
        sb.append("                        ,RKK.CALC_END_DT ");
        sb.append("                        ,RKK.KEIYAKU_SBT_KB ");
        sb.append("                        ,RKK.REMARKS ");
        sb.append("                        ,RKK.UPDATE_TS ");
        sb.append("                        ,COALESCE(TRIM(RKC.REBATE_KB),DRH.TENPO_CD,DKTJ.TENPO_CD) ");
        sb.append("                        ,NVL(RKC.SET_VL,DRH.HON_HAIFU_MONSUM_VL) ");
        sb.append("              ) REBATE_INFO ");
        sb.append("         UNION ALL  ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        // 違算（レジ誤差）
        sb.append("         SELECT ");
        sb.append("             CASE ");
        sb.append("                 WHEN CASH_LOSS.TEISEIGO_URIAGE_VL - CASH_LOSS.ARIDAKA_VL > 0 THEN 'D' ");
        sb.append("                 ELSE 'C' ");
        sb.append("             END DOCUMENT_TYPE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (S)
        //sb.append("             CASH_LOSS.TENPO_CD || '-' || CASH_LOSS.KEIJO_DT || '-' || '3' AS DOCUMENT_NO, ");
        sb.append("             CASH_LOSS.TENPO_CD || CASH_LOSS.KEIJO_DT || '3' AS DOCUMENT_NO, ");
        sb.append("             TO_CHAR(TO_DATE(CASH_LOSS.KEIJO_DT, 'yyyymmdd'), 'yyyy/MM/dd') AS DOCUMENT_DATE, ");
        // #6363 Mod 2022.01.06 KHAI.NN (E)
        sb.append("             CASH_LOSS.TENPO_CD, ");
        sb.append("             CASH_LOSS.TENPO_CD AS DIVISION_NO, ");
        sb.append("             '1' AS LINE_NO, ");
        sb.append("             'DISCREPANCY' AS ACCOUNT_NO, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             NULL PA_NO, ");
        sb.append("             NULL AS TERM_FROM, ");
        sb.append("             NULL AS TERM_TO, ");
        sb.append("             0    AS PURCHASE_QTY, ");
        sb.append("             0    AS PURCHASE_AMT, ");
        sb.append("             0    AS SALES_QTY, ");
        sb.append("             0    AS SALES_AMT, ");
        sb.append("             0    AS FIXED_COST, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             CASH_LOSS.TEISEIGO_URIAGE_VL - CASH_LOSS.ARIDAKA_VL AS AMOUNT, ");
        // #18254 Add 2024.03.23 DINH.TP (S)
        sb.append("             '' AS REMARKS, ");
        // #18254 Add 2024.03.23 DINH.TP (E)
        sb.append("             UPDATE_TS ");
        sb.append("         FROM ");
        sb.append("         ( ");
        sb.append("             SELECT ");
        sb.append("                 DTIS.TENPO_CD, ");
        sb.append("                 DTIS.KEIJO_DT, ");
        sb.append("                 RP.SHIHARAI_SYUBETSU_SEQ, ");
        sb.append("                 SUM(NVL(DTIS.TEISEIGO_URIAGE_VL, 0)) TEISEIGO_URIAGE_VL, ");
        sb.append("                 SUM(NVL(DTIS.ARIDAKA_VL, 0)) ARIDAKA_VL, ");
        sb.append("                 TO_CHAR(TO_DATE(DTIS.UPDATE_TS, 'yyyymmddhh24miss'),'yyyy/MM/dd hh24:mm') UPDATE_TS ");
        sb.append("             FROM ");
        sb.append("                 DT_TEN_ISAN_SEISAN DTIS ");
        sb.append("                 LEFT JOIN R_PAYMENT RP ON RP.SHIHARAI_SYUBETSU_CD = DTIS.SHIHARAI_SYUBETSU_CD ");
        sb.append("                 AND RP.SHIHARAI_SYUBETSU_SUB_CD = DTIS.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("                 INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS ON DZSS.COMP_CD = DTIS.COMP_CD ");
        sb.append("                 AND DZSS.KEIJO_DT = DTIS.KEIJO_DT ");
        sb.append("             WHERE ");
        sb.append("                 DTIS.COMP_CD = '" + COMP_CD + "' ");
        // #6363 Mod 2022.02.22 KHAI.NN (S)
        //sb.append("                 AND DTIS.KEIJO_DT = '" + lastDt + "' ");
        sb.append("                 AND DZSS.TRE_SEND_KB = '" + TRE_SEND_KB_UNSEND + "' ");
        // #6363 Mod 2022.02.22 KHAI.NN (E)
        sb.append("                 AND DZSS.SEISAN_STATE_FG = '2' ");
        sb.append("                 AND RP.SHIHARAI_SYUBETSU_SEQ = '" + SHIHARAI_SYUBETSU_SEQ +"' ");
        sb.append("             GROUP BY ");
        sb.append("                 DTIS.TENPO_CD, ");
        sb.append("                 DTIS.KEIJO_DT, ");
        sb.append("                 DTIS.UPDATE_TS, ");
        sb.append("                 RP.SHIHARAI_SYUBETSU_SEQ ");
        sb.append("         ) CASH_LOSS ");
        // #6363 Add 2022.02.22 KHAI.NN (S)
        sb.append("         WHERE ");
        sb.append("             CASH_LOSS.KEIJO_DT >= '" + CHECK_DT + "'");
        sb.append("         AND CASH_LOSS.KEIJO_DT <= '" + lastDt + "'");
        // #6363 Add 2022.02.22 KHAI.NN (E)
        sb.append("     ) MAIN ");
        sb.append(" WHERE MAIN.AMOUNT <> 0 ");
        sb.append(" ORDER BY ");
        sb.append("     MAIN.DOCUMENT_NO ");
        return sb.toString();
    }

    private String addQuarter(String value) {
        return '"' + value + '"';
    }  

    // #18254 Add 2024.03.23 DINH.TP (S)
    private String convertAmount(String amount) {
        if (null == amount || amount.trim().length() == 0) return "";
        if (amount.startsWith(".")) return "0" + amount;
        return amount;
    }   
    // #18254 Add 2024.03.23 DINH.TP (E)
    
    public void setInputObject(Object input) throws Exception {
    }

    public Object getOutputObject() throws Exception {
        return null;
    }
}
