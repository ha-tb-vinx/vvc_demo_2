package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: TorikomiMaeFDao.java クラス</p>
 * <p>説明　: Fレコード取込前処理</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00  (2016.09.27) Y.Itaki FIVI対応(#2190)
 * @Version 2.00  (2016.11.04) k.Hyo FIVI対応(#2263)
 * @Version 2.01  (2017.01.16) T.Kamei FIVI対応(#3627)
 * @Version 2.02  (2017.06.20) J.Endo FIVI対応(#5224)
 * @Version 2.03  (2017.07.07) J.Endo FIVI対応(#5579)
 *
 */
public class TorikomiMaeFDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "Fレコード取込前処理";

    /** 削除SQL */
    // 2017.06.20 J.Endo FIVI対応 #5224 MOD(S)
    //private static final String DEL_SQL = "TRUNCATE TABLE TMP_POS_F_INVOICE_WK";
    private static final String DEL_SQL = "TRUNCATE TABLE TMP_POS_F_INVOICE";
    // 2017.06.20 J.Endo FIVI対応 #5224 MOD(E)

    /** 登録先テーブル名称(POSジャーナル一時データ) */
    // 2017.06.20 J.Endo FIVI対応 #5224 MOD(S)
    //private static final String TMP_POS_F_INVOICE_WK = "POSジャーナル（F_Invoice）一時データ_WK";
    private static final String TMP_POS_F_INVOICE = "POSジャーナル（F_Invoice）一時データ";
    // 2017.06.20 J.Endo FIVI対応 #5224 MOD(E)


    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDel = null;

        int insertCount = 0;

        try {

            // 2017.06.20 J.Endo FIVI対応 #5224 MOD(S)
            //// TMP_POS_F_INVOICE_WKテーブルを削除する
            // TMP_POS_F_INVOICEテーブルを削除する
            // 2017.06.20 J.Endo FIVI対応 #5224 MOD(E)
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();


            // 2017.06.20 J.Endo FIVI対応 #5224 MOD(S)
            //// 開始
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_F_INVOICE_WK + "の出力を開始します。");
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtInsertSql());
            //insertCount = preparedStatementExIns.executeUpdate();
            //
            //// ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_F_INVOICE_WK + "を出力しました。");
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_F_INVOICE_WK + "の出力を終了します。");
            // 開始
            invoker.infoLog(strUserId + "　：　" + TMP_POS_F_INVOICE + "の出力を開始します。");
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtInsertSql());
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_F_INVOICE + "を出力しました。");
            invoker.infoLog(strUserId + "　：　" + TMP_POS_F_INVOICE + "の出力を終了します。");
            // 2017.06.20 J.Endo FIVI対応 #5224 MOD(E)

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 移送用SQLを取得する
     *
     * @return 移送用SQL
     */
    private String getEigyoDtInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO ");
        // 2017.06.20 J.Endo FIVI対応 #5224 MOD(S)
        //sql.append("  TMP_POS_F_INVOICE_WK ");
        sql.append("  TMP_POS_F_INVOICE ");
        // 2017.06.20 J.Endo FIVI対応 #5224 MOD(E)
        sql.append("( ");
        sql.append("  COMMAND ");
        sql.append("  ,STORE ");
        sql.append("  ,POS ");
        sql.append("  ,TRANS_NO ");
        sql.append("  ,CASHIER_ID ");
        sql.append("  ,FORMAT ");
        sql.append("  ,SNI_INV_FORM ");
        sql.append("  ,SIN_INV_SERIAL ");
        sql.append("  ,SIN_INV_NO ");
        sql.append("  ,SNI_REFUND_INV_FORM ");
        sql.append("  ,SIN_REFUND_INV_SERIAL ");
        sql.append("  ,SIN_REF_INV_NO ");
        sql.append("  ,SNI_CUST_NAME ");
        sql.append("  ,SNI_CUST_COMPANY ");
        sql.append("  ,SNI_CUST_ADDRS ");
        sql.append("  ,SNI_CUST_TAX_CODE ");
        // 2016/11/04 k.Hyo add (S)
        sql.append("  ,SNI_INV_FORM2 ");
        sql.append("  ,SIN_INV_SERIAL2 ");
        sql.append("  ,SIN_INV_NO2 ");
        sql.append("  ,SIN_INV_SKIP_KIND ");
        sql.append("  ,SIN_INV_SKIP_REASON ");
        // 2016/11/04 k.Hyo add (E)
        // 2017.06.20 J.Endo FIVI対応 #5224 ADD(S)
        // 2017.07.07 J.Endo FIVI対応 #5579 ADD(S)
        sql.append("  ,SNI_INV_ISSUED_CASHERID ");
        // 2017.07.07 J.Endo FIVI対応 #5579 ADD(E)
        sql.append("  ,EIGYO_DT ");
        // 2017.06.20 J.Endo FIVI対応 #5224 ADD(E)
        sql.append(") ");
        sql.append("SELECT ");
        sql.append("  COMMAND ");
        sql.append("  ,STORE ");
        sql.append("  ,POS ");
        sql.append("  ,TRANS_NO ");
        sql.append("  ,CASHIER_ID ");
        sql.append("  ,FORMAT ");
        sql.append("  ,SNI_INV_FORM ");
        sql.append("  ,SIN_INV_SERIAL ");
        sql.append("  ,SIN_INV_NO ");
        sql.append("  ,SNI_REFUND_INV_FORM ");
        sql.append("  ,SIN_REFUND_INV_SERIAL ");
        sql.append("  ,SIN_REF_INV_NO ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,  1,100) AS SNI_CUST_NAME ");
        // 2017.01.16 T.Kamei FIVI対応 #3627 Mod(S)
        //sql.append("  ,SUBSTR(MATOME_COLUMN,101,100) AS SNI_CUST_COMPANY ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,101,120) AS SNI_CUST_COMPANY ");
        //sql.append("  ,SUBSTR(MATOME_COLUMN,201,200) AS SNI_CUST_ADDRS ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,221,200) AS SNI_CUST_ADDRS ");
        //sql.append("  ,SUBSTR(MATOME_COLUMN,401, 50) AS SNI_CUST_TAX_CODE ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,421, 50) AS SNI_CUST_TAX_CODE ");
        // 2016/11/04 k.Hyo add (S)
        //sql.append("  ,SUBSTR(MATOME_COLUMN,451, 15) AS SNI_INV_FORM2 ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,471, 15) AS SNI_INV_FORM2 ");
        //sql.append("  ,SUBSTR(MATOME_COLUMN,466, 10) AS SIN_INV_SERIAL2 ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,486, 10) AS SIN_INV_SERIAL2 ");
        //sql.append("  ,SUBSTR(MATOME_COLUMN,476, 16) AS SIN_INV_NO2 ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,496, 16) AS SIN_INV_NO2 ");
        //sql.append("  ,SUBSTR(MATOME_COLUMN,492,  1) AS SIN_INV_SKIP_KIND ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,512,  1) AS SIN_INV_SKIP_KIND ");
        //sql.append("  ,SUBSTR(MATOME_COLUMN,493,100) AS SIN_INV_SKIP_REASON ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,513,100) AS SIN_INV_SKIP_REASON ");
        // 2016/11/04 k.Hyo add (E)
        // 2017.01.16 T.Kamei FIVI対応 #3627 Mod(E)
        // 2017.06.20 J.Endo FIVI対応 #5224 ADD(S)
        // 2017.07.07 J.Endo FIVI対応 #5579 MOD(S)
        //sql.append("  ,SUBSTR(MATOME_COLUMN,613,8) AS EIGYO_DT ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,616,6) AS SNI_INV_ISSUED_CASHERID "); // VAT発行者は９桁中４桁目から６桁
        sql.append("  ,SUBSTR(MATOME_COLUMN,622,8) AS EIGYO_DT ");
        // 2017.07.07 J.Endo FIVI対応 #5579 MOD(E)
        // 2017.06.20 J.Endo FIVI対応 #5224 ADD(E)
        sql.append("FROM ");
        sql.append("  TMP_POS_F_INVOICE_WK_BEFORE ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    /**
     * アウトプットBeanを取得する
     *
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

}
