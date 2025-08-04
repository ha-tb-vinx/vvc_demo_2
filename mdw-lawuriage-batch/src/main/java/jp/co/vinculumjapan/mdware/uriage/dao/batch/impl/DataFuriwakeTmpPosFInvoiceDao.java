package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル: DataFuriwakeTmpPosFInvoiceDao クラス</p>
 * <p>説明:データ振分（Fレコード）</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.10.14) k.Hyo FIVI対応
 * @Version 1.01 (2016.11.02) Y.Itaki FIVI対応(#2263)
 * @Version 1.02 (2016.11.29) J.Endo  FIVI対応(#2945)
 * @Version 1.03 (2017.02.03) J.Endo  FIVI対応(#3872)
 * @Version 1.04 (2017.07.10) X.Liu  FIVI対応(#5580)
 *
 * @see なし
 */
public class DataFuriwakeTmpPosFInvoiceDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "データ振分（Fレコード）";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // バッチID
    private static final String BATCH_ID = "URIB012720";

    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /**
     * データ振分（Fレコード）
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExInsOK = null;
        PreparedStatementEx preparedStatementExInsError = null;
        // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(S)
        PreparedStatementEx preparedStatementExInsSkip = null;
        // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(E)

        int insertCountOK = 0;
        int insertCountError = 0;
        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();
            //マスタチェック（Aレコード）

            preparedStatementExInsOK = invoker.getDataBase().prepareStatement(getOKDataFuriwakeTmpPosFInvoiceDaoSql(dbServerTime));
            insertCountOK = preparedStatementExInsOK.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のFレコードOKデータを追加しました。");

            preparedStatementExInsError = invoker.getDataBase().prepareStatement(getErrorDataFuriwakeTmpPosFInvoiceDaoSql(dbServerTime));
            insertCountError = preparedStatementExInsError.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountError + "件のFレコードエラーデータを追加しました。");

            // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(S)
            preparedStatementExInsSkip = invoker.getDataBase().prepareStatement(getDTPosInvoiceSkipInsertSql(dbServerTime));
            insertCountOK = preparedStatementExInsSkip.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のFレコードINVOICEスキップデータを追加しました。");
            // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(E)
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExInsOK != null) {
                    preparedStatementExInsOK.close();
                }
                if (preparedStatementExInsError != null) {
                    preparedStatementExInsError.close();
                }
                // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(S)
                if (preparedStatementExInsSkip != null) {
                    preparedStatementExInsSkip.close();
                }
                // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(E)
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    /**
     * FレコードOKデータSQLを取得する
     *
     * @return FレコードOKデータ
     */
    private String getOKDataFuriwakeTmpPosFInvoiceDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO DT_POS_F_INVOICE ( ");
        //sql.append("INSERT /*+ APPEND */ INTO TMP_POS_F_INVOICE_OK_DATA ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,SNI_INV_FORM ");
        sql.append("    ,SIN_INV_SERIAL ");
        sql.append("    ,SIN_INV_NO ");
        sql.append("    ,SNI_REFUND_INV_FORM ");
        sql.append("    ,SIN_REFUND_INV_SERIAL ");
        sql.append("    ,SIN_REF_INV_NO ");
        sql.append("    ,SNI_CUST_NAME ");
        sql.append("    ,SNI_CUST_COMPANY ");
        sql.append("    ,SNI_CUST_ADDRS ");
        sql.append("    ,SNI_CUST_TAX_CODE ");
        // 2016/11/02 VINX Y.Itaki FIVI(#2263) ADD(S)
        sql.append("    ,SNI_INV_FORM2 ");
        sql.append("    ,SIN_INV_SERIAL2 ");
        sql.append("    ,SIN_INV_NO2 ");
        sql.append("    ,SIN_INV_SKIP_KIND ");
        sql.append("    ,SIN_INV_SKIP_REASON ");
        // 2016/11/02 VINX Y.Itaki FIVI(#2263) ADD(E)
        sql.append("    ,EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("    ,SNI_INV_ISSUED_CASHERID ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");

        sql.append("SELECT ");
        sql.append("     WPFI.COMMAND ");
        sql.append("    ,WPFI.STORE ");
        sql.append("    ,WPFI.POS ");
        sql.append("    ,WPFI.TRANS_NO ");
        sql.append("    ,WPFI.CASHIER_ID ");
        sql.append("    ,WPFI.FORMAT ");
        sql.append("    ,WPFI.SNI_INV_FORM ");
        sql.append("    ,WPFI.SIN_INV_SERIAL ");
        sql.append("    ,WPFI.SIN_INV_NO ");
        sql.append("    ,WPFI.SNI_REFUND_INV_FORM ");
        sql.append("    ,WPFI.SIN_REFUND_INV_SERIAL ");
        sql.append("    ,WPFI.SIN_REF_INV_NO ");
        sql.append("    ,WPFI.SNI_CUST_NAME ");
        sql.append("    ,WPFI.SNI_CUST_COMPANY ");
        sql.append("    ,WPFI.SNI_CUST_ADDRS ");
        sql.append("    ,WPFI.SNI_CUST_TAX_CODE ");
        // 2016/11/02 VINX Y.Itaki FIVI(#2263) ADD(S)
        sql.append("    ,WPFI.SNI_INV_FORM2 ");
        sql.append("    ,WPFI.SIN_INV_SERIAL2 ");
        sql.append("    ,WPFI.SIN_INV_NO2 ");
        sql.append("    ,WPFI.SIN_INV_SKIP_KIND ");
        sql.append("    ,WPFI.SIN_INV_SKIP_REASON ");
        // 2016/11/02 VINX Y.Itaki FIVI(#2263) ADD(E)
        sql.append("    ,WPFI.EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("    ,WPFI.SNI_INV_ISSUED_CASHERID ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("    ,WPFI.DATA_SAKUSEI_DT ");
        sql.append("    ,WPFI.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_F_INVOICE WPFI ");
        //sql.append("    TMP_POS_F_INVOICE_CHECK_DATA TPFICD ");
        sql.append("WHERE ");
        // 2016/11/29 VINX J.Endo FIVI(#2945) MOD(S)
        //sql.append("    WPFI.ERR_KB = '0' ");
        sql.append("    WPFI.ERR_KB = '0' ");
        //#5580 Del X.Liu 2017.07.10 (S)
//        sql.append("AND(WPFI.SIN_INV_SKIP_KIND <> '1' ");
//        sql.append("AND WPFI.SIN_INV_SKIP_KIND <> '2') ");
        //#5580 Del X.Liu 2017.07.10 (E)
        // 2016/11/29 VINX J.Endo FIVI(#2945) MOD(E)
        //sql.append("    AND TPFICD.EIGYO_DT = '" + BATCH_DT + "' ");

        return sql.toString();
    }

    /**
     * FレコードエラーデータSQLを取得する
     *
     * @return Fレコードエラーデータ
     */
    private String getErrorDataFuriwakeTmpPosFInvoiceDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO DT_ERR_POS_F_INVOICE ( ");
        //sql.append("INSERT /*+ APPEND */ INTO TMP_POS_F_INVOICE_ERROR_DATA ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,SNI_INV_FORM ");
        sql.append("    ,SIN_INV_SERIAL ");
        sql.append("    ,SIN_INV_NO ");
        sql.append("    ,SNI_REFUND_INV_FORM ");
        sql.append("    ,SIN_REFUND_INV_SERIAL ");
        sql.append("    ,SIN_REF_INV_NO ");
        sql.append("    ,SNI_CUST_NAME ");
        sql.append("    ,SNI_CUST_COMPANY ");
        sql.append("    ,SNI_CUST_ADDRS ");
        sql.append("    ,SNI_CUST_TAX_CODE ");
        // 2016/11/02 VINX Y.Itaki FIVI(#2263) ADD(S)
        sql.append("    ,SNI_INV_FORM2 ");
        sql.append("    ,SIN_INV_SERIAL2 ");
        sql.append("    ,SIN_INV_NO2 ");
        sql.append("    ,SIN_INV_SKIP_KIND ");
        sql.append("    ,SIN_INV_SKIP_REASON ");
        // 2016/11/02 VINX Y.Itaki FIVI(#2263) ADD(E)
        sql.append("    ,EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("    ,SNI_INV_ISSUED_CASHERID ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");

        sql.append("SELECT ");
        sql.append("     WPFI.COMMAND ");
        sql.append("    ,WPFI.STORE ");
        sql.append("    ,WPFI.POS ");
        sql.append("    ,WPFI.TRANS_NO ");
        sql.append("    ,WPFI.CASHIER_ID ");
        sql.append("    ,WPFI.FORMAT ");
        sql.append("    ,WPFI.SNI_INV_FORM ");
        sql.append("    ,WPFI.SIN_INV_SERIAL ");
        sql.append("    ,WPFI.SIN_INV_NO ");
        sql.append("    ,WPFI.SNI_REFUND_INV_FORM ");
        sql.append("    ,WPFI.SIN_REFUND_INV_SERIAL ");
        sql.append("    ,WPFI.SIN_REF_INV_NO ");
        sql.append("    ,WPFI.SNI_CUST_NAME ");
        sql.append("    ,WPFI.SNI_CUST_COMPANY ");
        sql.append("    ,WPFI.SNI_CUST_ADDRS ");
        sql.append("    ,WPFI.SNI_CUST_TAX_CODE ");
        // 2016/11/02 VINX Y.Itaki FIVI(#2263) ADD(S)
        sql.append("    ,WPFI.SNI_INV_FORM2 ");
        sql.append("    ,WPFI.SIN_INV_SERIAL2 ");
        sql.append("    ,WPFI.SIN_INV_NO2 ");
        sql.append("    ,WPFI.SIN_INV_SKIP_KIND ");
        sql.append("    ,WPFI.SIN_INV_SKIP_REASON ");
        // 2016/11/02 VINX Y.Itaki FIVI(#2263) ADD(E)
        sql.append("    ,WPFI.EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("    ,WPFI.SNI_INV_ISSUED_CASHERID ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("    ,WPFI.DATA_SAKUSEI_DT ");
        sql.append("    ,WPFI.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_F_INVOICE WPFI ");
        //sql.append("    TMP_POS_F_INVOICE_CHECK_DATA TPFICD ");
        sql.append("WHERE ");
        // 2017/02/03 VINX J.Endo FIVI(#3872) MOD(S)
        //sql.append("    WPFI.ERR_KB = '1' ");
        sql.append("    WPFI.ERR_KB != '0' ");
        // 2017/02/03 VINX J.Endo FIVI(#3872) MOD(E)
        //sql.append("    AND TPFICD.EIGYO_DT = '" + BATCH_DT + "' ");

        return sql.toString();
    }

    // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(S)
    /**
     * Fレコードスキップデータ追加SQLを取得する
     *
     * @return Fレコードスキップデータ追加SQL
     */
    private String getDTPosInvoiceSkipInsertSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO DT_POS_INVOICE_SKIP ( ");
        sql.append("    COMMAND ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,CASHIER_ID ");
        sql.append("   ,FORMAT ");
        sql.append("   ,SNI_INV_FORM ");
        sql.append("   ,SIN_INV_SERIAL ");
        sql.append("   ,SIN_INV_NO ");
        sql.append("   ,SNI_REFUND_INV_FORM ");
        sql.append("   ,SIN_REFUND_INV_SERIAL ");
        sql.append("   ,SIN_REF_INV_NO ");
        sql.append("   ,SNI_CUST_NAME ");
        sql.append("   ,SNI_CUST_COMPANY ");
        sql.append("   ,SNI_CUST_ADDRS ");
        sql.append("   ,SNI_CUST_TAX_CODE ");
        sql.append("   ,SNI_INV_FORM2 ");
        sql.append("   ,SIN_INV_SERIAL2 ");
        sql.append("   ,SIN_INV_NO2 ");
        sql.append("   ,SIN_INV_SKIP_KIND ");
        sql.append("   ,SIN_INV_SKIP_REASON ");
        sql.append("   ,EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("   ,SNI_INV_ISSUED_CASHERID ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    WPFI.COMMAND ");
        sql.append("   ,WPFI.STORE ");
        sql.append("   ,WPFI.POS ");
        sql.append("   ,WPFI.TRANS_NO ");
        sql.append("   ,WPFI.CASHIER_ID ");
        sql.append("   ,WPFI.FORMAT ");
        sql.append("   ,WPFI.SNI_INV_FORM ");
        sql.append("   ,WPFI.SIN_INV_SERIAL ");
        sql.append("   ,WPFI.SIN_INV_NO ");
        sql.append("   ,WPFI.SNI_REFUND_INV_FORM ");
        sql.append("   ,WPFI.SIN_REFUND_INV_SERIAL ");
        sql.append("   ,WPFI.SIN_REF_INV_NO ");
        sql.append("   ,WPFI.SNI_CUST_NAME ");
        sql.append("   ,WPFI.SNI_CUST_COMPANY ");
        sql.append("   ,WPFI.SNI_CUST_ADDRS ");
        sql.append("   ,WPFI.SNI_CUST_TAX_CODE ");
        sql.append("   ,WPFI.SNI_INV_FORM2 ");
        sql.append("   ,WPFI.SIN_INV_SERIAL2 ");
        sql.append("   ,WPFI.SIN_INV_NO2 ");
        sql.append("   ,WPFI.SIN_INV_SKIP_KIND ");
        sql.append("   ,WPFI.SIN_INV_SKIP_REASON ");
        sql.append("   ,WPFI.EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("   ,WPFI.SNI_INV_ISSUED_CASHERID ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("   ,WPFI.DATA_SAKUSEI_DT ");
        sql.append("   ,WPFI.ERR_KB ");
        sql.append("   ,'" + BATCH_ID + "' ");
        sql.append("   ,'" + dbServerTime + "' ");
        sql.append("   ,'" + BATCH_ID + "' ");
        sql.append("   ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_F_INVOICE WPFI "); 
        sql.append("WHERE ");
        sql.append("    WPFI.ERR_KB = '0' ");
        sql.append("AND(WPFI.SIN_INV_SKIP_KIND = '1' ");
        //#5580 Mod X.Liu 2017.07.10 (S)
//        sql.append("OR  WPFI.SIN_INV_SKIP_KIND = '2') ");
        sql.append("OR  WPFI.SIN_INV_SKIP_KIND = '2' ");
        sql.append("OR  WPFI.SIN_INV_SKIP_KIND = '3') ");
        //#5580 Mod X.Liu 2017.07.10 (E)

        return sql.toString();
    }
    // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(E)

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {

        return null;
    }

    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {

    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new VatInvoiceRegistDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
