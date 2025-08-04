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
 * <p>タイトル: DataFuriwakeTmpPosCPaymentDao クラス</p>
 * <p>説明:データ振分（Cレコード）</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.10.18) k.Hyo FIVI対応
 * @Version 1.01 (2017.02.03) J.Endo  FIVI対応(#3872)
 * @Version 1.02 (2017.03.09) N.Katou #3760
 * @see なし
 */
public class DataFuriwakeTmpPosCPaymentDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "データ振分（Cレコード）";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    
    // バッチID
    private static final String BATCH_ID = "URIB012770";
    
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    /**
     * データ振分（Aレコード）
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
            //マスタチェック（Aレコード）

            preparedStatementExInsOK = invoker.getDataBase().prepareStatement(getOKDataFuriwakeTmpPosAItemDaoSql(dbServerTime));
            insertCountOK = preparedStatementExInsOK.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のCレコードOKデータを追加しました。");
            
            preparedStatementExInsError = invoker.getDataBase().prepareStatement(getErrorDataFuriwakeTmpPosAItemDaoSql(dbServerTime));
            insertCountError = preparedStatementExInsError.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountError + "件のCレコードエラーデータを追加しました。");
            
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
     * AレコードOKデータSQLを取得する
     *
     * @return AレコードOKデータ
     */
    private String getOKDataFuriwakeTmpPosAItemDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_POS_C_PAYMENT ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,CTYPE ");
        sql.append("    ,PYMT_TYPE ");
        sql.append("    ,PYMT_TYPE2 ");
        sql.append("    ,DATE_EXPIRY ");
        sql.append("    ,PYMT_AMT ");
        sql.append("    ,CREDIT_CARD_NUMBER ");
        sql.append("    ,MERCHANT_CODE ");
        sql.append("    ,ACTUAL_AMT ");
        sql.append("    ,APP_CODE ");
        sql.append("    ,DEBIT_TYPE ");
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // #3760 URIB012770 2017/3/09 N.katou(S)
        sql.append("    ,ISSUE_DATE_OF_PRV ");
        // #3760 URIB012770 2017/3/09 N.katou(E)
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     WPCP.COMMAND ");
        sql.append("    ,WPCP.STORE ");
        sql.append("    ,WPCP.POS ");
        sql.append("    ,WPCP.TRANS_NO ");
        sql.append("    ,WPCP.CASHIER_ID ");
        sql.append("    ,WPCP.FORMAT ");
        sql.append("    ,WPCP.CTYPE ");
        sql.append("    ,WPCP.PYMT_TYPE ");
        sql.append("    ,WPCP.PYMT_TYPE2 ");
        sql.append("    ,WPCP.DATE_EXPIRY ");
        sql.append("    ,WPCP.PYMT_AMT ");
        sql.append("    ,WPCP.CREDIT_CARD_NUMBER ");
        sql.append("    ,WPCP.MERCHANT_CODE ");
        sql.append("    ,WPCP.ACTUAL_AMT ");
        sql.append("    ,WPCP.APP_CODE ");
        sql.append("    ,WPCP.DEBIT_TYPE ");
        sql.append("    ,WPCP.TERMINAL_ID ");
        sql.append("    ,WPCP.TRACE_NO ");
        sql.append("    ,WPCP.EIGYO_DT ");
        sql.append("    ,WPCP.DATA_SAKUSEI_DT ");
        sql.append("    ,WPCP.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        // #3760 URIB012770 2017/3/09 N.katou(S)
        sql.append("    ,WPCP.ISSUE_DATE_OF_PRV ");
        // #3760 URIB012770 2017/3/09 N.katou(E)
        sql.append("FROM ");
        sql.append("    WK_POS_C_PAYMENT WPCP "); 
        sql.append("WHERE ");
        sql.append("    WPCP.ERR_KB = '0' ");
        
        return sql.toString();
    }

    /**
     * AレコードエラーデータSQLを取得する
     *
     * @return Aレコードエラーデータ
     */
    private String getErrorDataFuriwakeTmpPosAItemDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_ERR_POS_C_PAYMENT ( ");
        //sql.append("INSERT /*+ APPEND */ INTO TMP_POS_A_ITEM_ERROR_DATA ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,CTYPE ");
        sql.append("    ,PYMT_TYPE ");
        sql.append("    ,PYMT_TYPE2 ");
        sql.append("    ,DATE_EXPIRY ");
        sql.append("    ,PYMT_AMT ");
        sql.append("    ,CREDIT_CARD_NUMBER ");
        sql.append("    ,MERCHANT_CODE ");
        sql.append("    ,ACTUAL_AMT ");
        sql.append("    ,APP_CODE ");
        sql.append("    ,DEBIT_TYPE ");
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // #3760 URIB012770 2017/3/09 N.katou(S)
        sql.append("    ,ISSUE_DATE_OF_PRV ");
        // #3760 URIB012770 2017/3/09 N.katou(E)
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     WPCP.COMMAND ");
        sql.append("    ,WPCP.STORE ");
        sql.append("    ,WPCP.POS ");
        sql.append("    ,WPCP.TRANS_NO ");
        sql.append("    ,WPCP.CASHIER_ID ");
        sql.append("    ,WPCP.FORMAT ");
        sql.append("    ,WPCP.CTYPE ");
        sql.append("    ,WPCP.PYMT_TYPE ");
        sql.append("    ,WPCP.PYMT_TYPE2 ");
        sql.append("    ,WPCP.DATE_EXPIRY ");
        sql.append("    ,WPCP.PYMT_AMT ");
        sql.append("    ,WPCP.CREDIT_CARD_NUMBER ");
        sql.append("    ,WPCP.MERCHANT_CODE ");
        sql.append("    ,WPCP.ACTUAL_AMT ");
        sql.append("    ,WPCP.APP_CODE ");
        sql.append("    ,WPCP.DEBIT_TYPE ");
        sql.append("    ,WPCP.TERMINAL_ID ");
        sql.append("    ,WPCP.TRACE_NO ");
        sql.append("    ,WPCP.EIGYO_DT ");
        sql.append("    ,WPCP.DATA_SAKUSEI_DT ");
        sql.append("    ,WPCP.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        // #3760 URIB012770 2017/3/09 N.katou(S)
        sql.append("    ,WPCP.ISSUE_DATE_OF_PRV ");
        // #3760 URIB012770 2017/3/09 N.katou(E)
        sql.append("FROM ");
        sql.append("    WK_POS_C_PAYMENT WPCP "); 
        sql.append("WHERE ");
        // 2017/02/03 VINX J.Endo FIVI(#3872) MOD(S)
        //sql.append("    WPCP.ERR_KB = '1' ");
        sql.append("    WPCP.ERR_KB != '0' ");
        // 2017/02/03 VINX J.Endo FIVI(#3872) MOD(E)
        
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
