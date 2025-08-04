package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: AddEigyoDtCDao.java クラス</p>
 * <p>説明　: 営業日付加</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00  (2016.06.06) k.Hyo FIVI対応
 * @Version 3.01  (2016.06.16) T.Kamei FIVI対応
 * @Version 3.02  (2016.06.17) T.Kamei FIVI対応 SQL性能改善
 * @Version 3.03  (2017.03.09) N.Katou #3760
 *
 */
public class AddEigyoDtCDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "営業日付加";
    /** 登録先テーブル名称 */

    //20160616 T.KAMEI ADD (S)
    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE TMP_POS_C_PAYMENT";
    //20160616 T.KAMEI ADD (E)

    /** 登録先テーブル名称(POSジャーナル一時データ) */
    private static final String TMP_POS_C_PAYMENT = "POSジャーナル（C_Payment）一時データ";
    
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

            // TMP_POS_C_PAYMENT
            invoker.infoLog(strUserId + "　：　" + TMP_POS_C_PAYMENT + "の出力を開始します。");
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getEigyoDtInsertSql());
            insertCount = preparedStatementExIns.executeUpdate();
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TMP_POS_C_PAYMENT + "を出力しました。");
            invoker.infoLog(strUserId + "　：　" + TMP_POS_C_PAYMENT + "の出力を終了します。");

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
    private String getEigyoDtInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO ");
        sql.append("TMP_POS_C_PAYMENT ");
        sql.append("( ");
        sql.append("COMMAND ");
        sql.append(",STORE ");
        sql.append(",POS ");
        sql.append(",TRANS_NO ");
        sql.append(",CASHIER_ID ");
        sql.append(",FORMAT ");
        sql.append(",CTYPE");
        sql.append(",PYMT_TYPE ");
        sql.append(",PYMT_TYPE2 ");
        sql.append(",DATE_EXPIRY ");
        sql.append(",PYMT_AMT ");
        sql.append(",CREDIT_CARD_NUMBER ");
        sql.append(",MERCHANT_CODE ");
        sql.append(",ACTUAL_AMT ");
        sql.append(",APP_CODE ");
        sql.append(",DEBIT_TYPE ");
        sql.append(",TERMINAL_ID ");
        sql.append(",TRACE_NO ");
        // #3760 URIB012590 2017/3/09 N.katou(S)
//        sql.append(",EIGYO_DT) ");
        sql.append(",EIGYO_DT ");
        sql.append(",ISSUE_DATE_OF_PRV) ");
        // #3760 URIB012590 2017/3/09 N.katou(E)
        sql.append("( ");
        // 20160617 T.KAMEI MOD (S)
        sql.append("SELECT ");
        sql.append("    A.COMMAND ");
        sql.append("    ,A.STORE ");
        sql.append("    ,A.POS ");
        sql.append("    ,A.TRANS_NO ");
        sql.append("    ,A.CASHIER_ID ");
        sql.append("    ,A.FORMAT ");
        sql.append("    ,A.CTYPE");
        sql.append("    ,A.PYMT_TYPE ");
        sql.append("    ,A.PYMT_TYPE2 ");
        sql.append("    ,A.DATE_EXPIRY ");
        sql.append("    ,A.PYMT_AMT ");
        sql.append("    ,A.CREDIT_CARD_NUMBER ");
        sql.append("    ,A.MERCHANT_CODE ");
        sql.append("    ,A.ACTUAL_AMT ");
        sql.append("    ,A.APP_CODE ");
        sql.append("    ,A.DEBIT_TYPE ");
        sql.append("    ,A.TERMINAL_ID ");
        sql.append("    ,A.TRACE_NO ");
        sql.append("    ,B.EIGYO_DT ");
        // #3760 URIB012590 2017/3/09 N.katou(S)
        sql.append("    ,A.ISSUE_DATE_OF_PRV ");
        // #3760 URIB012590 2017/3/09 N.katou(E)
        sql.append("FROM ");
        sql.append("    TMP_POS_C_PAYMENT_WK A ");
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
