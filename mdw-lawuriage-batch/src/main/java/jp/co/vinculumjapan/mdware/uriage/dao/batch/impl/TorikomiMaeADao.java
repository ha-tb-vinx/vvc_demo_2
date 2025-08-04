package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: TorikomiMaeADao.java クラス</p>
 * <p>説明　: Aレコード取込前処理</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00  (2016.09.27) Y.Itaki FIVI対応(#2190)
 *
 */
public class TorikomiMaeADao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "Aレコード取込前処理";

    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE TMP_POS_A_ITEM_WK";

    /** 登録先テーブル名称(POSジャーナル一時データ) */
    private static final String TMP_POS_A_ITEM_WK = "POSジャーナル（A_Item）一時データ_WK";


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

            // TMP_POS_F_INVOICE_WKテーブルを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();


            // 開始
            invoker.infoLog(strUserId + "　：　" + TMP_POS_A_ITEM_WK + "の出力を開始します。");
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtInsertSql());
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_A_ITEM_WK + "を出力しました。");
            invoker.infoLog(strUserId + "　：　" + TMP_POS_A_ITEM_WK + "の出力を終了します。");

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
        sql.append("  TMP_POS_A_ITEM_WK ");
        sql.append("( ");
        sql.append("  COMMAND ");
        sql.append("  ,STORE ");
        sql.append("  ,POS ");
        sql.append("  ,TRANS_NO ");
        sql.append("  ,CASHIER_ID ");
        sql.append("  ,FORMAT ");
        sql.append("  ,ATYPE ");
        sql.append("  ,ODR_LINE_IDX ");
        sql.append("  ,SKU ");
        sql.append("  ,QTY ");
        sql.append("  ,WEIGHT_SOLD ");
        sql.append("  ,REG_SELL ");
        sql.append("  ,ACTUAL_SELL2 ");
        sql.append("  ,ACTUAL_SELL ");
        sql.append("  ,REG_SELL_WOT ");
        sql.append("  ,ACTUAL_SELL_WOT2 ");
        sql.append("  ,ACTUAL_SELL_WOT ");
        sql.append("  ,EMP_PURCH ");
        sql.append("  ,ITEM_WEIGH ");
        sql.append("  ,REGULAR_UNIT_SELL ");
        sql.append("  ,GST_TAX ");
        sql.append("  ,DISC4_AMT ");
        sql.append("  ,ITEM_NAME_RECEIPT ");
        sql.append("  ,ITEM_UOM_RECEIPT ");
        sql.append("  ,PRCCHG_NO ");
        sql.append("  ,PRCCHG1_NO ");
        sql.append("  ,PRCCHG2_NO ");
        sql.append("  ,PRCCHG3_NO ");
        sql.append("  ,PRIVILEGE_MEM ");
        sql.append("  ,OVER_WRITE_FLAG ");
        sql.append("  ,HAMPER_ITEM_CD ");
        sql.append("  ,SUPERVISOR_ID ");
        sql.append(") ");
        sql.append("SELECT ");
        sql.append("  COMMAND ");
        sql.append("  ,STORE ");
        sql.append("  ,POS ");
        sql.append("  ,TRANS_NO ");
        sql.append("  ,CASHIER_ID ");
        sql.append("  ,FORMAT ");
        sql.append("  ,ATYPE ");
        sql.append("  ,ODR_LINE_IDX ");
        sql.append("  ,SKU ");
        sql.append("  ,QTY ");
        sql.append("  ,WEIGHT_SOLD ");
        sql.append("  ,REG_SELL ");
        sql.append("  ,ACTUAL_SELL2 ");
        sql.append("  ,ACTUAL_SELL ");
        sql.append("  ,REG_SELL_WOT ");
        sql.append("  ,ACTUAL_SELL_WOT2 ");
        sql.append("  ,ACTUAL_SELL_WOT ");
        sql.append("  ,EMP_PURCH ");
        sql.append("  ,ITEM_WEIGH ");
        sql.append("  ,REGULAR_UNIT_SELL ");
        sql.append("  ,GST_TAX ");
        sql.append("  ,DISC4_AMT ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,  1, 40) AS ITEM_NAME_RECEIPT ");
        sql.append("  ,SUBSTR(MATOME_COLUMN, 41,  8) AS ITEM_UOM_RECEIPT ");
        sql.append("  ,SUBSTR(MATOME_COLUMN, 49, 10) AS PRCCHG_NO ");
        sql.append("  ,SUBSTR(MATOME_COLUMN, 59, 10) AS PRCCHG1_NO ");
        sql.append("  ,SUBSTR(MATOME_COLUMN, 69, 11) AS PRCCHG2_NO ");
        sql.append("  ,SUBSTR(MATOME_COLUMN, 80, 10) AS PRCCHG3_NO ");
        sql.append("  ,SUBSTR(MATOME_COLUMN, 90,  1) AS PRIVILEGE_MEM ");
        sql.append("  ,SUBSTR(MATOME_COLUMN, 91,  1) AS OVER_WRITE_FLAG ");
        sql.append("  ,SUBSTR(MATOME_COLUMN, 92, 13) AS HAMPER_ITEM_CD ");
        sql.append("  ,SUBSTR(MATOME_COLUMN,108,  6) AS SUPERVISOR_ID ");
        sql.append("FROM ");
        sql.append("  TMP_POS_A_ITEM_WK_BEFORE ");

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
