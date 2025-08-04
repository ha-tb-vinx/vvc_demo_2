package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>
 * タイトル：IfSapTenpoUriageVatinvoiceDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 店舗売上VATINVOICE情報 IFファイル作成処理
 * </p>
 * <p>
 * 著作権：Copyright (c) 2016
 * </p>
 * <p>
 * 会社名：VINX
 * </p>
 * 
 * @author VINX
 * @version 1.00  (2016.7.4) A.Narita FIVIMART対応
 */
public class IfSapTenpoUriageVatinvoiceDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810051";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 店舗売上VATINVOICE情報 IFファイル作成処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** SAPファイルパス */
    private static final String SAP_SEND_PATH = FiResorceUtility.getPropertie("SAP_SEND_PATH");
    /** SAPファイル名 */
    private static final String SAP_SEND_FILE_URIAGE_TENPOURIAGEVATINVOICE = FiResorceUtility.getPropertie("SAP_SEND_FILE_URIAGE_TENPOURIAGEVATINVOICE");
    /** 出力ファイル文字コード */
    private static final String OUTPUT_CHAR_SET = "UTF-8";
    /** 改行文字(CRLF) */
    private static final String LINE_FEED_CHAR = "\r\n";
    /** システム日付 */
    private static final String SYS_DATE = FiResorceUtility.getDBServerTime();

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 店舗売上VATINVOICE情報 IFファイル出力処理を開始します。");

        PreparedStatementEx ps_c = null;
        PreparedStatementEx ps_m = null;
        PreparedStatementEx ps_merge = null;
        ResultSet rs_c = null;
        ResultSet rs_m = null;
        int count = 0;

        /** 累積テーブルに無いデータの抽出処理 */
        invoker.infoLog(strUserId + "　：　抽出を開始します。");
        
        
        ps_c = invoker.getDataBase().prepareStatement(select_C());
        rs_c = ps_c.executeQuery();
        
        ps_m = invoker.getDataBase().prepareStatement(select_M());
        rs_m = ps_m.executeQuery();
        
        invoker.infoLog(strUserId + "　：　抽出を終了します。");
        
        /** 累積データの作成 */
        invoker.infoLog(strUserId + "　：　累積データの作成を開始します。");
        ps_merge = invoker.getDataBase().prepareStatement(mergeRuiseki());
        ps_merge.executeQuery();
        invoker.infoLog(strUserId + "　：　累積データの作成を終了します。");

        /** ファイル作成 */
        invoker.infoLog(strUserId + "　：　ファイル作成を開始します。");
        File dataFile = new File(SAP_SEND_PATH + "/" + SAP_SEND_FILE_URIAGE_TENPOURIAGEVATINVOICE + "_" + BATCH_DT + "_" + selectFileNo(invoker) + ".txt");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
        // ファイル出力
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        try {
            count = count + createFile(rs_c, writer);
            count = count + createFile(rs_m, writer);
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

        // ログを出力
        invoker.infoLog(strUserId + "　：　" + count + "件を作成しました。");
        invoker.infoLog(strUserId + "　：　ファイル作成を終了します。");
        
        // ファイル名連番を+1
        updateFileNo(invoker);

        /** 終了処理 */
        invoker.infoLog(strUserId + "　：　SAP IF 店舗売上VATINVOICE情報 IFファイル出力処理を終了します。");
    }

    private int createFile(ResultSet rs, BufferedWriter writer) throws Exception {

        int cnt = 0;

        if (rs == null) {
            return cnt;
        }
        while (rs.next()) {
            StringBuffer line = new StringBuffer();
            line.append(rs.getString("HENKOU_KB"));
            line.append(rs.getString("SAKUSEI_DT"));
            line.append(rs.getString("KEIJO_DT"));
            line.append(rs.getString("TENPO_CD"));
            line.append(rs.getString("INVOICE_AUTO_NB_KB"));
            line.append(rs.getString("INVOICE_AUTO_RB"));
            line.append(rs.getString("TAX_RT"));
            line.append(rs.getString("URIAGE_VL_NUKI"));
            line.append(rs.getString("TAX_VL"));
            line.append(rs.getString("URIAGE_VL"));
            line.append(rs.getString("INVOICE_KEYIN_NB_KB"));
            line.append(rs.getString("INVOICE_KEYIN_RB"));
            line.append(rs.getString("DATA_KB"));
            line.append(rs.getString("REASON_KB"));
            line.append(LINE_FEED_CHAR);

            writer.write(line.toString());
            cnt++;
        }

        return cnt;

    }
    
// この処理は不要。    
//    private String select_C_onlyCancel() {
//
//        StringBuffer sb = new StringBuffer();
//        // 理由区分 1:取消 WKにあり、累積になし の場合、0:通常 を「C」で作成
//        // → 結合条件に、REASON_KB は含めない。
//        //
//        // ※ 同日に通常発行と取消を行った場合、採番履歴テーブル には 1:取消しか残らないため、
//        //     1日目  C 0:通常 をこのメソッドでファイルレコード作成
//        //     1日目  M 1:取消 をselect_Cメソッドでファイルレコード作成
//        // ※ 一日おきに通常発行と取消を行った場合は、0:通常は作成しない
//        //     1日目  C 0:通常 をこのメソッドでファイルレコード作成しない
//        //     2日目  M 1:取消 をselect_Cメソッドでファイルレコード作成
//        
//        sb.append(" SELECT /*+ ORDERED USE_NL(WSTV DSTVR) */ ");
//        sb.append("   'C' AS HENKOU_KB ");
//        sb.append("   , '0' AS REASON_KB  ");
//        sb.append(selectColumn());
//        sb.append(" FROM  ");
//        sb.append("   WK_SAP_TEN_VAT WSTV  ");
//        sb.append("   LEFT OUTER JOIN DT_SAP_TEN_VAT_RUI DSTVR  ");
//        sb.append("     ON WSTV.NUMBERING_HISTORY_NO = DSTVR.NUMBERING_HISTORY_NO  ");
//        sb.append("     AND WSTV.TAX_RT = DSTVR.TAX_RT  ");
//        sb.append("     AND WSTV.KEIJO_DT = DSTVR.KEIJO_DT  ");
//        sb.append("     AND WSTV.TENPO_CD = DSTVR.TENPO_CD  ");
//        sb.append("     AND WSTV.INVOICE_AUTO_NB = DSTVR.INVOICE_AUTO_NB  ");
//        sb.append("     AND WSTV.INVOICE_AUTO_KB = DSTVR.INVOICE_AUTO_KB  ");
//        sb.append("     AND WSTV.INVOICE_AUTO_RB = DSTVR.INVOICE_AUTO_RB  ");
//        sb.append("     AND WSTV.INVOICE_KEYIN_RB = DSTVR.INVOICE_KEYIN_RB  ");
//        sb.append("     AND WSTV.DATA_KB = DSTVR.DATA_KB  ");
//        sb.append(" WHERE ");
//        sb.append("   DSTVR.NUMBERING_HISTORY_NO IS NULL ");
//        sb.append("   AND WSTV.REASON_KB = '1' ");
//        sb.append(" ORDER BY "); 
//        sb.append("   WSTV.NUMBERING_HISTORY_NO  ");
//        sb.append("   , WSTV.TAX_RT  ");
//
//        return sb.toString();
//    }
    
    private String select_C() {

        StringBuffer sb = new StringBuffer();
        //「C」

        sb.append(" SELECT /*+ ORDERED USE_NL(WSTV DSTVR) */ ");
        sb.append("   'C' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM  ");
        sb.append("   WK_SAP_TEN_VAT WSTV  ");
        sb.append("   LEFT OUTER JOIN DT_SAP_TEN_VAT_RUI DSTVR  ");
        sb.append("     ON WSTV.NUMBERING_HISTORY_NO = DSTVR.NUMBERING_HISTORY_NO  ");
        sb.append("     AND WSTV.TAX_RT = DSTVR.TAX_RT  ");
        sb.append("     AND WSTV.KEIJO_DT = DSTVR.KEIJO_DT  ");
        sb.append("     AND WSTV.TENPO_CD = DSTVR.TENPO_CD  ");
        sb.append("     AND WSTV.INVOICE_AUTO_NB = DSTVR.INVOICE_AUTO_NB  ");
        sb.append("     AND WSTV.INVOICE_AUTO_KB = DSTVR.INVOICE_AUTO_KB  ");
        sb.append("     AND WSTV.INVOICE_AUTO_RB = DSTVR.INVOICE_AUTO_RB  ");
        sb.append("     AND WSTV.INVOICE_KEYIN_NB = DSTVR.INVOICE_KEYIN_NB  ");
        sb.append("     AND WSTV.INVOICE_KEYIN_KB = DSTVR.INVOICE_KEYIN_KB  ");
        sb.append("     AND WSTV.INVOICE_KEYIN_RB = DSTVR.INVOICE_KEYIN_RB  ");
        sb.append("     AND WSTV.DATA_KB = DSTVR.DATA_KB  ");
        sb.append("     AND WSTV.REASON_KB = DSTVR.REASON_KB  ");
        sb.append(" WHERE ");
        sb.append("   DSTVR.NUMBERING_HISTORY_NO IS NULL ");
        sb.append(" ORDER BY "); 
        sb.append("   WSTV.NUMBERING_HISTORY_NO  ");
        sb.append("   , WSTV.TAX_RT  ");
        
        return sb.toString();
    }

    private String select_M() {

        StringBuffer sb = new StringBuffer();
        // 「M」※ 通常は発生しない
        
        sb.append(" SELECT /*+ ORDERED USE_NL(WSTV DSTVR) */ ");
        sb.append("   'M' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_TEN_VAT WSTV  ");
        sb.append("   INNER JOIN DT_SAP_TEN_VAT_RUI DSTVR  ");
        sb.append("     ON WSTV.NUMBERING_HISTORY_NO = DSTVR.NUMBERING_HISTORY_NO  ");
        sb.append("     AND WSTV.TAX_RT = DSTVR.TAX_RT  ");
        sb.append("     AND WSTV.KEIJO_DT = DSTVR.KEIJO_DT  ");
        sb.append("     AND WSTV.TENPO_CD = DSTVR.TENPO_CD  ");
        sb.append("     AND WSTV.INVOICE_AUTO_NB = DSTVR.INVOICE_AUTO_NB  ");
        sb.append("     AND WSTV.INVOICE_AUTO_KB = DSTVR.INVOICE_AUTO_KB  ");
        sb.append("     AND WSTV.INVOICE_AUTO_RB = DSTVR.INVOICE_AUTO_RB  ");
        sb.append("     AND WSTV.INVOICE_KEYIN_NB = DSTVR.INVOICE_KEYIN_NB  ");
        sb.append("     AND WSTV.INVOICE_KEYIN_KB = DSTVR.INVOICE_KEYIN_KB  ");
        sb.append("     AND WSTV.INVOICE_KEYIN_RB = DSTVR.INVOICE_KEYIN_RB  ");
        sb.append("     AND WSTV.DATA_KB = DSTVR.DATA_KB  ");
        sb.append("     AND WSTV.REASON_KB = DSTVR.REASON_KB  ");
        sb.append(" WHERE ");
        sb.append("   NVL(WSTV.URIAGE_VL_NUKI, 0) <> NVL(DSTVR.URIAGE_VL_NUKI, 0) ");
        sb.append("   OR NVL(WSTV.TAX_VL, 0) <> NVL(DSTVR.TAX_VL, 0) ");
        sb.append("   OR NVL(WSTV.URIAGE_VL, 0) <> NVL(DSTVR.URIAGE_VL, 0) ");
        sb.append(" ORDER BY "); 
        sb.append("   WSTV.NUMBERING_HISTORY_NO ");
        sb.append("   , WSTV.TAX_RT  ");

        return sb.toString();
    }

    private String selectColumn() {

        StringBuffer sb = new StringBuffer();
        // 「C」と「M」の共通項目
        sb.append("   , RPAD(NVL(TRIM('" + BATCH_DT  + "'), ' '), 8, ' ') AS SAKUSEI_DT  ");
        sb.append("   , RPAD(NVL(TRIM(WSTV.KEIJO_DT), ' '), 8, ' ') AS KEIJO_DT  ");
        sb.append("   , RPAD(NVL(TRIM(WSTV.TENPO_CD), ' '), 10, ' ') AS TENPO_CD  ");
        sb.append("   , RPAD(NVL(TRIM(WSTV.INVOICE_AUTO_NB) || TRIM(WSTV.INVOICE_AUTO_KB), ' ') , 25, ' ') AS INVOICE_AUTO_NB_KB  ");
        sb.append("   , RPAD(NVL(TRIM(WSTV.INVOICE_AUTO_RB), ' ') , 16, ' ') AS INVOICE_AUTO_RB  ");
        sb.append("   , RPAD(NVL(TRIM(WSTV.INVOICE_KEYIN_NB) || TRIM(WSTV.INVOICE_KEYIN_KB), ' ') , 25, ' ') AS INVOICE_KEYIN_NB_KB  ");
        sb.append("   , RPAD(NVL(TRIM(WSTV.INVOICE_KEYIN_RB), ' ') , 16, ' ') AS INVOICE_KEYIN_RB  ");
        sb.append("   , RPAD(NVL(TRIM(WSTV.TAX_RT), ' '), 2, ' ') AS TAX_RT  ");
        sb.append("   , TRIM(TO_CHAR(NVL(WSTV.URIAGE_VL_NUKI, 0) * 100, '000000000000000')) AS URIAGE_VL_NUKI  ");
        sb.append("   , TRIM(TO_CHAR(NVL(WSTV.TAX_VL, 0) * 100, '000000000000000')) AS TAX_VL  ");
        sb.append("   , TRIM(TO_CHAR(NVL(WSTV.URIAGE_VL, 0) * 100, '000000000000000')) AS URIAGE_VL  ");
        sb.append("   , RPAD(NVL(TRIM(WSTV.DATA_KB), ' '), 2, ' ') AS DATA_KB  "); 
        sb.append("   , RPAD(NVL(TRIM(WSTV.REASON_KB), ' '), 1, ' ') AS REASON_KB  ");

        return sb.toString();
    }
    
    private String mergeRuiseki() {

        StringBuffer sb = new StringBuffer();
        // 累積テーブルにMERGE
        sb.append(" MERGE /*+ ORDERED USE_NL(WSTV DSTVR) */");
        sb.append(" INTO DT_SAP_TEN_VAT_RUI DSTVR  ");
        sb.append("   USING WK_SAP_TEN_VAT WSTV  ");
        sb.append("     ON (  ");
        sb.append("       WSTV.NUMBERING_HISTORY_NO = DSTVR.NUMBERING_HISTORY_NO  ");
        sb.append("       AND WSTV.TAX_RT = DSTVR.TAX_RT  ");
        sb.append("       AND WSTV.KEIJO_DT = DSTVR.KEIJO_DT  ");
        sb.append("       AND WSTV.TENPO_CD = DSTVR.TENPO_CD  ");
        sb.append("       AND WSTV.INVOICE_AUTO_NB = DSTVR.INVOICE_AUTO_NB  ");
        sb.append("       AND WSTV.INVOICE_AUTO_KB = DSTVR.INVOICE_AUTO_KB  ");
        sb.append("       AND WSTV.INVOICE_AUTO_RB = DSTVR.INVOICE_AUTO_RB  ");
        sb.append("       AND WSTV.INVOICE_KEYIN_NB = DSTVR.INVOICE_KEYIN_NB  ");
        sb.append("       AND WSTV.INVOICE_KEYIN_KB = DSTVR.INVOICE_KEYIN_KB  ");
        sb.append("       AND WSTV.INVOICE_KEYIN_RB = DSTVR.INVOICE_KEYIN_RB  ");
        sb.append("       AND WSTV.DATA_KB = DSTVR.DATA_KB  ");
        sb.append("       AND WSTV.REASON_KB = DSTVR.REASON_KB  ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   DSTVR.URIAGE_VL_NUKI = WSTV.URIAGE_VL_NUKI ");
        sb.append("   , DSTVR.TAX_VL = WSTV.TAX_VL ");
        sb.append("   , DSTVR.URIAGE_VL = WSTV.URIAGE_VL ");
        sb.append("   , DSTVR.UPDATE_USER_ID = '" + BATCH_ID + "' ");
        sb.append("   , DSTVR.UPDATE_TS = '" + SYS_DATE + "' ");
        sb.append(" WHEN NOT MATCHED THEN INSERT (  ");
        sb.append("   DSTVR.NUMBERING_HISTORY_NO ");
        sb.append("   , DSTVR.TAX_RT ");
        sb.append("   , DSTVR.KEIJO_DT ");
        sb.append("   , DSTVR.TENPO_CD ");
        sb.append("   , DSTVR.INVOICE_AUTO_NB ");
        sb.append("   , DSTVR.INVOICE_AUTO_KB ");
        sb.append("   , DSTVR.INVOICE_AUTO_RB ");
        sb.append("   , DSTVR.INVOICE_KEYIN_NB ");
        sb.append("   , DSTVR.INVOICE_KEYIN_KB ");
        sb.append("   , DSTVR.INVOICE_KEYIN_RB ");
        sb.append("   , DSTVR.DATA_KB ");
        sb.append("   , DSTVR.REASON_KB ");
        sb.append("   , DSTVR.URIAGE_VL_NUKI ");
        sb.append("   , DSTVR.TAX_VL ");
        sb.append("   , DSTVR.URIAGE_VL ");
        sb.append("   , DSTVR.INSERT_USER_ID ");
        sb.append("   , DSTVR.INSERT_TS ");
        sb.append("   , DSTVR.UPDATE_USER_ID ");
        sb.append("   , DSTVR.UPDATE_TS ");
        sb.append(" )  ");
        sb.append(" VALUES (  ");
        sb.append("   WSTV.NUMBERING_HISTORY_NO ");
        sb.append("   , WSTV.TAX_RT ");
        sb.append("   , WSTV.KEIJO_DT ");
        sb.append("   , WSTV.TENPO_CD ");
        sb.append("   , WSTV.INVOICE_AUTO_NB ");
        sb.append("   , WSTV.INVOICE_AUTO_KB ");
        sb.append("   , WSTV.INVOICE_AUTO_RB ");
        sb.append("   , WSTV.INVOICE_KEYIN_NB ");
        sb.append("   , WSTV.INVOICE_KEYIN_KB ");
        sb.append("   , WSTV.INVOICE_KEYIN_RB ");
        sb.append("   , WSTV.DATA_KB ");
        sb.append("   , WSTV.REASON_KB ");
        sb.append("   , WSTV.URIAGE_VL_NUKI ");
        sb.append("   , WSTV.TAX_VL ");
        sb.append("   , WSTV.URIAGE_VL ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append(" )  "); 

        

        return sb.toString();
    }
    
    private String selectFileNo(DaoInvokerIf invoker) throws SQLException {
        PreparedStatementEx ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   LPAD(FILENO,3,'0') AS FILENO ");
        sb.append(" FROM ");
        sb.append("   R_SAP_FILE_SEQ_URIAGE ");
        sb.append(" WHERE ");
        sb.append("   FILEID = '"+ SAP_SEND_FILE_URIAGE_TENPOURIAGEVATINVOICE +"' ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        rs = ps.executeQuery();
        if (rs.next()) {
            //FILENOを取得できたらその値を返却
            return rs.getString("FILENO");
        }else{
            //FILENOを取得できなくても001を返却（ファイル作成を優先）
            return "001";
        }
    }
    
    private void updateFileNo(DaoInvokerIf invoker) throws SQLException {
        PreparedStatementEx ps = null;
        //結果0件でもファイルを空で作成するので、処理が流れたら+1しておく
        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE R_SAP_FILE_SEQ_URIAGE RSFS ");
        sb.append("   SET RSFS.FILENO = RSFS.FILENO + 1 ");
        sb.append("   , RSFS.UPDATE_USER_ID = '"+ BATCH_ID +"' ");
        sb.append("   , RSFS.UPDATE_TS = '"+ SYS_DATE +"' ");
        sb.append(" WHERE ");
        sb.append("   FILEID = '"+ SAP_SEND_FILE_URIAGE_TENPOURIAGEVATINVOICE +"' ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        ps.executeQuery();
    }


    @Override
    public Object getOutputObject() throws Exception {
        return null;
    }

    @Override
    public void setInputObject(Object arg0) throws Exception {

    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new IfSapTenpoUriageVatinvoiceDao();
            new StandAloneDaoInvoker("MD").InvokeDao(dao);
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}