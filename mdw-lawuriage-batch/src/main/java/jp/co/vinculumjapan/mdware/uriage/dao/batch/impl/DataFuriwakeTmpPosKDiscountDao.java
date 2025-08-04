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
 * <p>タイトル: DataFuriwakeTmpPosKDiscountDao クラス</p>
 * <p>説明  : データ振分（Kレコード）</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: VINX</p>
 *
 * @Version 1.00 (2017.01.31) J.Endo FIVI対応
 *
 */
public class DataFuriwakeTmpPosKDiscountDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "データ振分（Kレコード）";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    /** バッチID */
    private static final String BATCH_ID = "URIB012880";

    /**
     * データ振分（Kレコード）
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExInsOK = null;
        PreparedStatementEx preparedStatementExInsError = null;

        int insertCountOK = 0;
        int insertCountError = 0;
        try {

            //マスタチェック（Kレコード）
            preparedStatementExInsOK = invoker.getDataBase().prepareStatement(getOKDataFuriwakeTmpPosKDiscountDaoSql());

            preparedStatementExInsOK.setString(1, BATCH_ID);
            preparedStatementExInsOK.setString(2, FiResorceUtility.getDBServerTime());
            preparedStatementExInsOK.setString(3, BATCH_ID);
            preparedStatementExInsOK.setString(4, FiResorceUtility.getDBServerTime());

            insertCountOK = preparedStatementExInsOK.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のKレコードOKデータを追加しました。");

            preparedStatementExInsError = invoker.getDataBase().prepareStatement(getErrorDataFuriwakeTmpPosKDiscountDaoSql());

            preparedStatementExInsError.setString(1, BATCH_ID);
            preparedStatementExInsError.setString(2, FiResorceUtility.getDBServerTime());
            preparedStatementExInsError.setString(3, BATCH_ID);
            preparedStatementExInsError.setString(4, FiResorceUtility.getDBServerTime());

            insertCountError = preparedStatementExInsError.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountError + "件のKレコードエラーデータを追加しました。");

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
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    /**
     * KレコードOKデータSQLを取得する
     *
     * @return KレコードOKデータ
     */
    private String getOKDataFuriwakeTmpPosKDiscountDaoSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO DT_POS_K_DISCOUNT ( ");
        sql.append("    COMMAND ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,CASHIER_ID ");
        sql.append("   ,FORMAT ");
        sql.append("   ,ODR_LINE_IDX ");
        sql.append("   ,DISC_SUBCTGR ");
        sql.append("   ,DISC_AMT ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    WPKD.COMMAND ");
        sql.append("   ,WPKD.STORE ");
        sql.append("   ,WPKD.POS ");
        sql.append("   ,WPKD.TRANS_NO ");
        sql.append("   ,WPKD.CASHIER_ID ");
        sql.append("   ,WPKD.FORMAT ");
        sql.append("   ,WPKD.ODR_LINE_IDX ");
        sql.append("   ,WPKD.DISC_SUBCTGR ");
        sql.append("   ,WPKD.DISC_AMT ");
        sql.append("   ,WPKD.EIGYO_DT ");
        sql.append("   ,WPKD.DATA_SAKUSEI_DT ");
        sql.append("   ,WPKD.ERR_KB ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ");
        sql.append("    WK_POS_K_DISCOUNT WPKD ");
        sql.append("WHERE ");
        sql.append("    WPKD.ERR_KB = '0' ");

        return sql.toString();
    }

    /**
     * KレコードエラーデータSQLを取得する
     *
     * @return Kレコードエラーデータ
     */
    private String getErrorDataFuriwakeTmpPosKDiscountDaoSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO DT_ERR_POS_K_DISCOUNT ( ");
        sql.append("    COMMAND ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,CASHIER_ID ");
        sql.append("   ,FORMAT ");
        sql.append("   ,ODR_LINE_IDX ");
        sql.append("   ,DISC_SUBCTGR ");
        sql.append("   ,DISC_AMT ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    WPKD.COMMAND ");
        sql.append("   ,WPKD.STORE ");
        sql.append("   ,WPKD.POS ");
        sql.append("   ,WPKD.TRANS_NO ");
        sql.append("   ,WPKD.CASHIER_ID ");
        sql.append("   ,WPKD.FORMAT ");
        sql.append("   ,WPKD.ODR_LINE_IDX ");
        sql.append("   ,WPKD.DISC_SUBCTGR ");
        sql.append("   ,WPKD.DISC_AMT ");
        sql.append("   ,WPKD.EIGYO_DT ");
        sql.append("   ,WPKD.DATA_SAKUSEI_DT ");
        sql.append("   ,WPKD.ERR_KB ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ");
        sql.append("    WK_POS_K_DISCOUNT WPKD ");
        sql.append("WHERE ");
        sql.append("    WPKD.ERR_KB != '0' ");

        return sql.toString();
    }

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
