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
 * <p>タイトル: DataFuriwakeTmpPosDCashDao クラス</p>
 * <p>説明:データ振分（Dレコード）</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2017.02.27) N.Katou FIVI対応
 * @see なし
 */
public class DataFuriwakeTmpPosDCashDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "データ振分（Dレコード）";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    
    // バッチID
    private static final String BATCH_ID = "URIB012910";
    
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    /**
     * データ振分（Dレコード）
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

        int insertCountOK = 0;
        int insertCountError = 0;
        try {
            
            String dbServerTime = FiResorceUtility.getDBServerTime();
            //マスタチェック（Dレコード）

            preparedStatementExInsOK = invoker.getDataBase().prepareStatement(getOKDataFuriwakeTmpPosDCashDaoSql(dbServerTime));
            insertCountOK = preparedStatementExInsOK.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のDレコードOKデータを追加しました。");
            
            preparedStatementExInsError = invoker.getDataBase().prepareStatement(getErrorDataFuriwakeTmpPosDCashDaoSql(dbServerTime));
            insertCountError = preparedStatementExInsError.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountError + "件のDレコードエラーデータを追加しました。");
            
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
     * DレコードOKデータSQLを取得する
     *
     * @return DレコードOKデータ
     */
    private String getOKDataFuriwakeTmpPosDCashDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_POS_D_CASH ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,LOAN_AMOUNT ");
        sql.append("    ,MIDDLE_COLLECTION_AMOUNT ");
        sql.append("    ,CASH_SALES ");
        sql.append("    ,CASH_COUNT ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     WPDC.COMMAND ");
        sql.append("    ,WPDC.STORE ");
        sql.append("    ,WPDC.POS ");
        sql.append("    ,WPDC.TRANS_NO ");
        sql.append("    ,WPDC.CASHIER_ID ");
        sql.append("    ,WPDC.FORMAT ");
        sql.append("    ,WPDC.LOAN_AMOUNT ");
        sql.append("    ,WPDC.MIDDLE_COLLECTION_AMOUNT ");
        sql.append("    ,WPDC.CASH_SALES ");
        sql.append("    ,WPDC.CASH_COUNT ");
        sql.append("    ,WPDC.EIGYO_DT ");
        sql.append("    ,WPDC.DATA_SAKUSEI_DT ");
        sql.append("    ,WPDC.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_D_CASH WPDC "); 
        sql.append("WHERE ");
        sql.append("    WPDC.ERR_KB = '0' ");
        
        return sql.toString();
    }

    /**
     * DレコードエラーデータSQLを取得する
     *
     * @return Dレコードエラーデータ
     */
    private String getErrorDataFuriwakeTmpPosDCashDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_ERR_POS_D_CASH ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,LOAN_AMOUNT ");
        sql.append("    ,MIDDLE_COLLECTION_AMOUNT ");
        sql.append("    ,CASH_SALES ");
        sql.append("    ,CASH_COUNT ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     WPDC.COMMAND ");
        sql.append("    ,WPDC.STORE ");
        sql.append("    ,WPDC.POS ");
        sql.append("    ,WPDC.TRANS_NO ");
        sql.append("    ,WPDC.CASHIER_ID ");
        sql.append("    ,WPDC.FORMAT ");
        sql.append("    ,WPDC.LOAN_AMOUNT ");
        sql.append("    ,WPDC.MIDDLE_COLLECTION_AMOUNT ");
        sql.append("    ,WPDC.CASH_SALES ");
        sql.append("    ,WPDC.CASH_COUNT ");
        sql.append("    ,WPDC.EIGYO_DT ");
        sql.append("    ,WPDC.DATA_SAKUSEI_DT ");
        sql.append("    ,WPDC.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_D_CASH WPDC "); 
        sql.append("WHERE ");
        sql.append("    WPDC.ERR_KB != '0' ");
        
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
