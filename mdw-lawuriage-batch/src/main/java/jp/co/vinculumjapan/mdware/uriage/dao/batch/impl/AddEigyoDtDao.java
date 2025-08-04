package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: AddEigyoDtDao.java クラス</p>
 * <p>説明　: 営業日付加</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00  (2016.05.18) H.Yaguma FIVI対応
 * @Version 3.01  (2016.06.06) k.Hyo FIVI対応
 * @Version 3.02  (2016.06.16) T.Kamei FIVI対応
 * @Version 3.03  (2016.06.17) T.Kamei FIVI対応 SQL性能改善
 * @Version 3.04  (2016.09.26) Y.Itaki FIVI対応(#2185)
 *
 */
public class AddEigyoDtDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "営業日付加";
    /** 登録先テーブル名称 */

    //20160616 T.KAMEI ADD (S)
    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE TMP_POS_A_ITEM";
    //20160616 T.KAMEI ADD (E)

    /** 登録先テーブル名称(POSジャーナル一時データ) */
    private static final String TMP_POS_A_ITEM = "POSジャーナル（A_Item）一時データ";
    //private static final String TMP_POS_C_PAYMENT = "POSジャーナル（C_Payment）一時データ";
    //private static final String TMP_POS_D_CASH = "POSジャーナル（D_Cash）一時データ";
    //private static final String TMP_POS_E_TENDER = "POSジャーナル（E_Tender）一時データ";
    //private static final String TMP_POS_F_INVOICE = "POSジャーナル（F_Invoice）一時データ";
    //private static final String TMP_POS_K_DISCOUNT = "POSジャーナル（K_Discount）一時データ";
    //private static final String TMP_POS_T_TRAILER = "POSジャーナル（T_Trailer）一時データ";

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
        //20160616 T.KAMEI ADD (S)
        PreparedStatementEx preparedStatementDel = null;
        //20160616 T.KAMEI ADD (E)

        int insertCount = 0;

        try {

            //20160616 T.KAMEI ADD (S)
            // TMP_POS_A_ITEMテーブルを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();
            //20160616 T.KAMEI ADD (E)

            // 営業日の付与
            // ログ出力
            // TMP_POS_A_ITEM
            invoker.infoLog(strUserId + "　：　" + TMP_POS_A_ITEM + "の出力を開始します。");
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtInsertSql(/*"TMP_POS_A_ITEM"*/));
            insertCount = preparedStatementExIns.executeUpdate();
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_A_ITEM + "を出力しました。");
            invoker.infoLog(strUserId + "　：　" + TMP_POS_A_ITEM + "の出力を終了します。");

            // 2016/06/06 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
            // TMP_POS_C_PAYMENT
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_C_PAYMENT + "の更新を開始します。");
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtUpdateSql("TMP_POS_C_PAYMENT"));
            //insertCount = preparedStatementExIns.executeUpdate();
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_C_PAYMENT + "を更新しました。");
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_C_PAYMENT + "の更新を終了します。");

            // TMP_POS_D_CASH
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_D_CASH + "の更新を開始します。");
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtUpdateSql("TMP_POS_D_CASH"));
            //insertCount = preparedStatementExIns.executeUpdate();
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_D_CASH + "を更新しました。");
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_D_CASH + "の更新を終了します。");

            // TMP_POS_E_TENDER
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_E_TENDER + "の更新を開始します。");
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtUpdateSql("TMP_POS_E_TENDER"));
            //insertCount = preparedStatementExIns.executeUpdate();
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_E_TENDER + "を更新しました。");
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_E_TENDER + "の更新を終了します。");

            // TMP_POS_F_INVOICE
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_F_INVOICE + "の更新を開始します。");
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtUpdateSql("TMP_POS_F_INVOICE"));
            //insertCount = preparedStatementExIns.executeUpdate();
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_F_INVOICE + "を更新しました。");
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_F_INVOICE + "の更新を終了します。");

            // TMP_POS_K_DISCOUNT
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_K_DISCOUNT + "の更新を開始します。");
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtUpdateSql("TMP_POS_K_DISCOUNT"));
            //insertCount = preparedStatementExIns.executeUpdate();
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_K_DISCOUNT + "を更新しました。");
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_K_DISCOUNT + "の更新を終了します。");

            // TMP_POS_T_TRAILER
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_T_TRAILER + "の更新を開始します。");
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtUpdateSql("TMP_POS_T_TRAILER"));
            //insertCount = preparedStatementExIns.executeUpdate();
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_T_TRAILER + "を更新しました。");
            //invoker.infoLog(strUserId + "　：　" + TMP_POS_T_TRAILER + "の更新を終了します。");
            // 2016/06/06 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

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
     * 営業日更新用SQLを取得する
     *
     * @return 営業日更新用SQL
     */
    private String getEigyoDtInsertSql(/*String tableName*/) {
        StringBuilder sql = new StringBuilder();

        //sql.append("UPDATE ");
        //sql.append(tableName);
        //sql.append(" X SET ");
        //sql.append("     X.EIGYO_DT = ");
        //sql.append("     (SELECT B.EIGYO_DT ");
        //sql.append("       FROM TMP_POS_B_TOTAL B ");
        //sql.append("       WHERE ");
        //sql.append("        X.STORE = B.STORE AND ");
        //sql.append("        X.POS = B.POS AND ");
        //sql.append("        X.TRANS_NO = B.TRANS_NO)");
        sql.append("INSERT INTO ");
        sql.append("TMP_POS_A_ITEM ");
        sql.append("( ");
        sql.append("COMMAND ");
        sql.append(",STORE ");
        sql.append(",POS ");
        sql.append(",TRANS_NO ");
        sql.append(",CASHIER_ID ");
        sql.append(",FORMAT ");
        sql.append(",ATYPE");
        sql.append(",ODR_LINE_IDX ");
        sql.append(",SKU ");
        sql.append(",QTY ");
        sql.append(",WEIGHT_SOLD ");
        sql.append(",REG_SELL ");
        sql.append(",ACTUAL_SELL2 ");
        sql.append(",ACTUAL_SELL ");
        sql.append(",REG_SELL_WOT ");
        sql.append(",ACTUAL_SELL_WOT2 ");
        sql.append(",ACTUAL_SELL_WOT ");
        sql.append(",EMP_PURCH ");
        sql.append(",ITEM_WEIGH ");
        sql.append(",REGULAR_UNIT_SELL ");
        sql.append(",GST_TAX");
        sql.append(",DISC4_AMT ");
        sql.append(",ITEM_NAME_RECEIPT ");
        sql.append(",ITEM_UOM_RECEIPT ");
        sql.append(",PRCCHG_NO ");
        sql.append(",PRCCHG1_NO ");
        sql.append(",PRCCHG2_NO ");
        sql.append(",PRCCHG3_NO ");
        sql.append(",PRIVILEGE_MEM ");
        sql.append(",OVER_WRITE_FLAG ");
        // 2016/09/26 Y.Itaki FIVI(#2185) ADD (S)
        sql.append(",HAMPER_ITEM_CD ");
        sql.append(",SUPERVISOR_ID ");
        // 2016/09/26 Y.Itaki FIVI(#2185) ADD (E)
        sql.append(",EIGYO_DT) ");
        sql.append("( ");
        // 20160617 T.KAMEI MOD (S)
        sql.append("SELECT ");
        sql.append("    A.COMMAND ");
        sql.append("    ,A.STORE ");
        sql.append("    ,A.POS ");
        sql.append("    ,A.TRANS_NO ");
        sql.append("    ,A.CASHIER_ID ");
        sql.append("    ,A.FORMAT ");
        sql.append("    ,A.ATYPE");
        sql.append("    ,A.ODR_LINE_IDX ");
        sql.append("    ,A.SKU ");
        sql.append("    ,A.QTY ");
        sql.append("    ,A.WEIGHT_SOLD ");
        sql.append("    ,A.REG_SELL ");
        sql.append("    ,A.ACTUAL_SELL2 ");
        sql.append("    ,A.ACTUAL_SELL ");
        sql.append("    ,A.REG_SELL_WOT ");
        sql.append("    ,A.ACTUAL_SELL_WOT2 ");
        sql.append("    ,A.ACTUAL_SELL_WOT ");
        sql.append("    ,A.EMP_PURCH ");
        sql.append("    ,A.ITEM_WEIGH ");
        sql.append("    ,A.REGULAR_UNIT_SELL ");
        sql.append("    ,A.GST_TAX");
        sql.append("    ,A.DISC4_AMT ");
        sql.append("    ,A.ITEM_NAME_RECEIPT ");
        sql.append("    ,A.ITEM_UOM_RECEIPT ");
        sql.append("    ,A.PRCCHG_NO ");
        sql.append("    ,A.PRCCHG1_NO ");
        sql.append("    ,A.PRCCHG2_NO ");
        sql.append("    ,A.PRCCHG3_NO ");
        sql.append("    ,A.PRIVILEGE_MEM ");
        sql.append("    ,A.OVER_WRITE_FLAG ");
        // 2016/09/26 Y.Itaki FIVI(#2185) ADD (S)
        sql.append("    ,A.HAMPER_ITEM_CD ");
        sql.append("    ,A.SUPERVISOR_ID ");
        // 2016/09/26 Y.Itaki FIVI(#2185) ADD (E)
        sql.append("    ,B.EIGYO_DT ");
        sql.append("FROM ");
        sql.append("    TMP_POS_A_ITEM_WK A ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("           ,POS ");
        sql.append("           ,TRANS_NO ");
        sql.append("           ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_B_TOTAL ");
        sql.append("    ) B ");
        sql.append("ON ");
        sql.append("    A.STORE    = B.STORE AND ");
        sql.append("    A.POS      = B.POS AND ");
        sql.append("    A.TRANS_NO = B.TRANS_NO ");
        sql.append(") ");
        // 20160617 T.KAMEI MOD (E)

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
