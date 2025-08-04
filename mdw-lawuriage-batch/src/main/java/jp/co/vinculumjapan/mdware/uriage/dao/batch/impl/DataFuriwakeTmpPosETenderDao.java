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
 * <p>タイトル: DataFuriwakeTmpPosETenderDao クラス</p>
 * <p>説明:データ振分（Eレコード）</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2017.02.27) N.Katou FIVI対応
 * @see なし
 */
public class DataFuriwakeTmpPosETenderDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "データ振分（Eレコード）";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    
    // バッチID
    private static final String BATCH_ID = "URIB012930";
    
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    /**
     * データ振分（Eレコード）
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

            preparedStatementExInsOK = invoker.getDataBase().prepareStatement(getOKDataFuriwakeTmpPosETenderDaoSql(dbServerTime));
            insertCountOK = preparedStatementExInsOK.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のEレコードOKデータを追加しました。");
            
            preparedStatementExInsError = invoker.getDataBase().prepareStatement(getErrorDataFuriwakeTmpPosETenderDaoSql(dbServerTime));
            insertCountError = preparedStatementExInsError.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountError + "件のEレコードエラーデータを追加しました。");
            
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
     * EレコードOKデータSQLを取得する
     *
     * @return EレコードOKデータ
     */
    private String getOKDataFuriwakeTmpPosETenderDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_POS_E_TENDER ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,ETYPE ");
        sql.append("    ,PYMT_TYPE ");
        sql.append("    ,PYMT_TYPE2 ");
        sql.append("    ,PAYMENT_SALES ");
        sql.append("    ,PAYMENT_COUNT ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     WPET.COMMAND ");
        sql.append("    ,WPET.STORE ");
        sql.append("    ,WPET.POS ");
        sql.append("    ,WPET.TRANS_NO ");
        sql.append("    ,WPET.CASHIER_ID ");
        sql.append("    ,WPET.FORMAT ");
        sql.append("    ,WPET.ETYPE ");
        sql.append("    ,WPET.PYMT_TYPE ");
        sql.append("    ,WPET.PYMT_TYPE2 ");
        sql.append("    ,WPET.PAYMENT_SALES ");
        sql.append("    ,WPET.PAYMENT_COUNT ");
        sql.append("    ,WPET.EIGYO_DT ");
        sql.append("    ,WPET.DATA_SAKUSEI_DT ");
        sql.append("    ,WPET.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_E_TENDER WPET "); 
        sql.append("WHERE ");
        sql.append("    WPET.ERR_KB = '0' ");
        
        return sql.toString();
    }

    /**
     * EレコードエラーデータSQLを取得する
     *
     * @return Dレコードエラーデータ
     */
    private String getErrorDataFuriwakeTmpPosETenderDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_ERR_POS_E_TENDER ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,ETYPE ");
        sql.append("    ,PYMT_TYPE ");
        sql.append("    ,PYMT_TYPE2 ");
        sql.append("    ,PAYMENT_SALES ");
        sql.append("    ,PAYMENT_COUNT ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     WPET.COMMAND ");
        sql.append("    ,WPET.STORE ");
        sql.append("    ,WPET.POS ");
        sql.append("    ,WPET.TRANS_NO ");
        sql.append("    ,WPET.CASHIER_ID ");
        sql.append("    ,WPET.FORMAT ");
        sql.append("    ,WPET.ETYPE ");
        sql.append("    ,WPET.PYMT_TYPE ");
        sql.append("    ,WPET.PYMT_TYPE2 ");
        sql.append("    ,WPET.PAYMENT_SALES ");
        sql.append("    ,WPET.PAYMENT_COUNT ");
        sql.append("    ,WPET.EIGYO_DT ");
        sql.append("    ,WPET.DATA_SAKUSEI_DT ");
        sql.append("    ,WPET.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_E_TENDER WPET "); 
        sql.append("WHERE ");
        sql.append("    WPET.ERR_KB != '0' ");
        
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
