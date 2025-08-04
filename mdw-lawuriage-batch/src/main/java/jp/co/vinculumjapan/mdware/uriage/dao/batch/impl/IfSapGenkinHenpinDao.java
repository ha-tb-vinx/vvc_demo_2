package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * タイトル：IfSapGenkinHenpinDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 現金返品 ワーク作成処理
 * </p>
 * <p>
 * 著作権：Copyright (c) 2016
 * </p>
 * <p>
 * 会社名：VINX
 * </p>
 * 
 * @author VINX
 * @version 1.00 (2016.7.4) A.Narita FIVIMART対応
 */
public class IfSapGenkinHenpinDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810071";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 現金返品 IFファイル作成処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** SAPファイルパス */
    private static final String SAP_SEND_PATH = FiResorceUtility.getPropertie("SAP_SEND_PATH");
    /** SAPファイル名 */
    private static final String SAP_SEND_FILE_URIAGE_GENKINHENPIN = FiResorceUtility.getPropertie("SAP_SEND_FILE_URIAGE_GENKINHENPIN");
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
        invoker.infoLog(strUserId + "　：　SAP IF 現金返品 IFファイル作成処理を開始します。");

        PreparedStatementEx ps_cnt = null;
        PreparedStatementEx ps_c = null;
        PreparedStatementEx ps_m = null;
        PreparedStatementEx ps_merge = null;
        ResultSet rs_cnt = null;
        ResultSet rs_c = null;
        ResultSet rs_m = null;

        /** 現金返品データ件数 */
        int count = 0;
        /** 現金返品データ件数取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　現金返品データ件数取得を開始します。");
        // 現金返品データ件数取得SQL
        ps_cnt = invoker.getDataBase().prepareStatement(selectCountWk());

        // SQLを実行する
        rs_cnt = ps_cnt.executeQuery();

        if (rs_cnt.next()) {
            count = rs_cnt.getInt("COUNT");
        }

        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　" + count + "件の現金返品データを取得しました。");
        invoker.infoLog(strUserId + "　：　現金返品データ件数取得を終了します。");

        /** 変更区分「C」分の抽出 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を開始します。");

        ps_c = invoker.getDataBase().prepareStatement(select_C());
        // SQLを実行する
        rs_c = ps_c.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を終了します。");

        /** 変更区分「M」分の抽出 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を開始します。");

        ps_m = invoker.getDataBase().prepareStatement(select_M());
        // SQLを実行する
        rs_m = ps_m.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を終了します。");

        /** 現金返品データ取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　累積データの作成を開始します。");

        ps_merge = invoker.getDataBase().prepareStatement(mergeRuiseki());
        // SQLを実行する
        ps_merge.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　累積データの作成を終了します。");

        /** ファイル作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ファイル作成を開始します。");
        // ファイル名の取得
        File dataFile = new File(SAP_SEND_PATH + "/" + SAP_SEND_FILE_URIAGE_GENKINHENPIN + "_" + BATCH_DT + "_" + selectFileNo(invoker) + ".txt");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
        // ファイル出力
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        count = 0;
        try {
            // 変更区分「C」
            count = count + createFile(rs_c, writer);
            // 変更区分「M」
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
        invoker.infoLog(strUserId + "　：　SAP IF 現金返品 IFファイル作成処理を終了します。");

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
            line.append(rs.getString("URIAGE_VL_NUKI"));
            line.append(rs.getString("TAX_VL"));
            line.append(rs.getString("TAX_RT"));
            line.append(rs.getString("TENPO_CD"));
            line.append(rs.getString("RECEIPT_NO"));
            line.append(rs.getString("SHIHARAI_SYUBETSU_CD"));
            line.append(LINE_FEED_CHAR);

            writer.write(line.toString());
            cnt++;
        }
        return cnt;

    }

    private String select_C() {
        StringBuffer sb = new StringBuffer();
        // 累積テーブルに無いデータを「C」として抽出
        sb.append(" SELECT /*+ ORDERED USE_NL(WSGH DSGHR) */ ");
        sb.append("   'C' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_GENKIN_HENPIN WSGH  ");
        sb.append("   LEFT OUTER JOIN DT_SAP_GENKIN_HENPIN_RUI DSGHR  ");
        sb.append("     ON WSGH.KEIJO_DT = DSGHR.KEIJO_DT  ");
        sb.append("     AND WSGH.TENPO_CD = DSGHR.TENPO_CD  ");
        sb.append("     AND WSGH.REGISTER_NO = DSGHR.REGISTER_NO  ");
        sb.append("     AND WSGH.TRANSACTION_NO = DSGHR.TRANSACTION_NO  ");
        sb.append("     AND WSGH.SHIHARAI_SYUBETSU_CD = DSGHR.SHIHARAI_SYUBETSU_CD  ");
        sb.append("     AND WSGH.TAX_RT = DSGHR.TAX_RT  ");
        sb.append(" WHERE ");
        sb.append("   DSGHR.KEIJO_DT IS NULL "); 
        sb.append("   AND NVL(WSGH.URIAGE_VL_NUKI, 0) <> 0 "); 

        return sb.toString();
    }

    private String select_M() {
        StringBuffer sb = new StringBuffer();
        // 累積テーブルにあり、変更あるデータを「M」として抽出
        sb.append(" SELECT /*+ ORDERED USE_NL(WSGH DSGHR) */ ");
        sb.append("   'M' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_GENKIN_HENPIN WSGH  ");
        sb.append("   INNER JOIN DT_SAP_GENKIN_HENPIN_RUI DSGHR  ");
        sb.append("     ON WSGH.KEIJO_DT = DSGHR.KEIJO_DT  ");
        sb.append("     AND WSGH.TENPO_CD = DSGHR.TENPO_CD  ");
        sb.append("     AND WSGH.REGISTER_NO = DSGHR.REGISTER_NO  ");
        sb.append("     AND WSGH.TRANSACTION_NO = DSGHR.TRANSACTION_NO  ");
        sb.append("     AND WSGH.SHIHARAI_SYUBETSU_CD = DSGHR.SHIHARAI_SYUBETSU_CD  ");
        sb.append("     AND WSGH.TAX_RT = DSGHR.TAX_RT  ");
        sb.append("     AND ( WSGH.URIAGE_VL_NUKI <> DSGHR.URIAGE_VL_NUKI  ");
        sb.append("           OR WSGH.TAX_VL <> DSGHR.TAX_VL ) ");

        return sb.toString();
    }
    
    private String selectColumn(){
        // 「C」と「M」の共通項目
        
        StringBuffer sb = new StringBuffer();
        sb.append("   , '" + BATCH_DT + "' AS SAKUSEI_DT ");
        sb.append("   , RPAD(WSGH.KEIJO_DT, 8, ' ') AS KEIJO_DT ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSGH.URIAGE_VL_NUKI, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS URIAGE_VL_NUKI ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSGH.TAX_VL, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS TAX_VL ");
        sb.append("   , RPAD(WSGH.TAX_RT, 2, ' ') AS TAX_RT");
        sb.append("   , RPAD(WSGH.TENPO_CD, 10, ' ') AS TENPO_CD  ");
        sb.append("   , RPAD(SUBSTR(WSGH.TENPO_CD,3,4) || WSGH.REGISTER_NO || WSGH.TRANSACTION_NO , 15, ' ') AS RECEIPT_NO  ");
        sb.append("   , RPAD(WSGH.SHIHARAI_SYUBETSU_CD, 3, ' ') AS SHIHARAI_SYUBETSU_CD  ");
        return sb.toString();
    }

    private String mergeRuiseki() {
        StringBuffer sb = new StringBuffer();
        sb.append(" MERGE /*+ ORDERED USE_NL(WSGH DSGHR) */ ");
        sb.append(" INTO DT_SAP_GENKIN_HENPIN_RUI DSGHR  ");
        sb.append("   USING WK_SAP_GENKIN_HENPIN WSGH  ");
        sb.append("     ON (  ");
        sb.append("       DSGHR.KEIJO_DT = WSGH.KEIJO_DT  ");
        sb.append("       AND DSGHR.TENPO_CD = WSGH.TENPO_CD  ");
        sb.append("       AND DSGHR.REGISTER_NO = WSGH.REGISTER_NO  ");
        sb.append("       AND DSGHR.TRANSACTION_NO = WSGH.TRANSACTION_NO  ");
        sb.append("       AND DSGHR.SHIHARAI_SYUBETSU_CD = WSGH.SHIHARAI_SYUBETSU_CD  ");
        sb.append("       AND DSGHR.TAX_RT = WSGH.TAX_RT ");
        sb.append("     ) WHEN MATCHED THEN UPDATE  ");
        sb.append(" SET ");
        sb.append("   DSGHR.URIAGE_VL_NUKI = WSGH.URIAGE_VL_NUKI ");
        sb.append("   , DSGHR.TAX_VL = WSGH.TAX_VL ");
        sb.append("   , DSGHR.UPDATE_USER_ID = '"+ BATCH_ID +"' ");
        sb.append("   , DSGHR.UPDATE_TS = '"+ SYS_DATE +"' WHEN NOT MATCHED THEN  ");
        sb.append(" INSERT (  ");
        sb.append("   DSGHR.KEIJO_DT ");
        sb.append("   , DSGHR.TENPO_CD ");
        sb.append("   , DSGHR.REGISTER_NO ");
        sb.append("   , DSGHR.TRANSACTION_NO ");
        sb.append("   , DSGHR.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , DSGHR.TAX_RT ");
        sb.append("   , DSGHR.URIAGE_VL_NUKI ");
        sb.append("   , DSGHR.TAX_VL ");
        sb.append("   , DSGHR.INSERT_USER_ID ");
        sb.append("   , DSGHR.INSERT_TS ");
        sb.append("   , DSGHR.UPDATE_USER_ID ");
        sb.append("   , DSGHR.UPDATE_TS ");
        sb.append(" )  ");
        sb.append(" VALUES (  ");
        sb.append("   WSGH.KEIJO_DT ");
        sb.append("   , WSGH.TENPO_CD ");
        sb.append("   , WSGH.REGISTER_NO ");
        sb.append("   , WSGH.TRANSACTION_NO ");
        sb.append("   , WSGH.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSGH.TAX_RT ");
        sb.append("   , WSGH.URIAGE_VL_NUKI ");
        sb.append("   , WSGH.TAX_VL ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append(" )  ");

        return sb.toString();
    }

    private String selectCountWk() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   COUNT(*) AS COUNT ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_GENKIN_HENPIN ");
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
        sb.append("   FILEID = '" + SAP_SEND_FILE_URIAGE_GENKINHENPIN + "' ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        rs = ps.executeQuery();
        if (rs.next()) {
            // FILENOを取得できたらその値を返却
            return rs.getString("FILENO");
        } else {
            // FILENOを取得できなくても001を返却（ファイル作成を優先）
            return "001";
        }
    }

    private void updateFileNo(DaoInvokerIf invoker) throws SQLException {
        PreparedStatementEx ps = null;
        // 結果0件でもファイルを空で作成するので、処理が流れたら+1しておく
        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE R_SAP_FILE_SEQ_URIAGE RSFS ");
        sb.append("   SET RSFS.FILENO = RSFS.FILENO + 1 ");
        sb.append("   , RSFS.UPDATE_USER_ID = '" + BATCH_ID + "' ");
        sb.append("   , RSFS.UPDATE_TS = '" + SYS_DATE + "' ");
        sb.append(" WHERE ");
        sb.append("   FILEID = '" + SAP_SEND_FILE_URIAGE_GENKINHENPIN + "' ");
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
            DaoIf dao = new IfSapGenkinHenpinDao();
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