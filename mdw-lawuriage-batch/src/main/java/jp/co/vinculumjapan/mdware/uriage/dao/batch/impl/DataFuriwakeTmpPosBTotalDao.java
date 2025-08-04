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
 * <p>タイトル: DataFuriwakeTmpPosBTotalDao クラス</p>
 * <p>説明:データ振分（Bレコード）</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.07.14) k.Hyo FIVI対応
 * @Version 1.01 (2017.02.03) J.Endo  FIVI対応(#3872)
 * @see なし
 */
public class DataFuriwakeTmpPosBTotalDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "データ振分（Bレコード）";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    
    // バッチID
    private static final String BATCH_ID = "URIB012710";
    
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    /**
     * データ振分（Bレコード）
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

            preparedStatementExInsOK = invoker.getDataBase().prepareStatement(getOKDataFuriwakeTmpPosBTotalDaoSql(dbServerTime));
            insertCountOK = preparedStatementExInsOK.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のBレコードOKデータを追加しました。");
            
            preparedStatementExInsError = invoker.getDataBase().prepareStatement(getErrorDataFuriwakeTmpPosBTotalDaoSql(dbServerTime));
            insertCountError = preparedStatementExInsError.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountError + "件のBレコードエラーデータを追加しました。");
            
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
     * BレコードOKデータSQLを取得する
     *
     * @return BレコードOKデータ
     */
    private String getOKDataFuriwakeTmpPosBTotalDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_POS_B_TOTAL ( ");
        //sql.append("INSERT /*+ APPEND */ INTO TMP_POS_B_TOTAL_OK_DATA ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,BTYPE ");
        sql.append("    ,REC_TOT ");
        sql.append("    ,NTAXABLE ");
        sql.append("    ,TAX_AMT ");
        sql.append("    ,TOT_AMT ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,TORI_TIME ");
        sql.append("    ,CHANGE ");
        sql.append("    ,MEM_CARD ");
        sql.append("    ,REFUND_ORI_TRANS# ");
        sql.append("    ,REFUND_ORI_STORE ");
        sql.append("    ,REFUND_ORI_SALES_DATE ");
        sql.append("    ,REFUND_REASON ");
        sql.append("    ,REFUND_DESCRIPTION ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     TPBTCD.COMMAND ");
        sql.append("    ,TPBTCD.STORE ");
        sql.append("    ,TPBTCD.POS ");
        sql.append("    ,TPBTCD.TRANS_NO ");
        sql.append("    ,TPBTCD.CASHIER_ID ");
        sql.append("    ,TPBTCD.FORMAT ");
        sql.append("    ,TPBTCD.BTYPE ");
        sql.append("    ,TPBTCD.REC_TOT ");
        sql.append("    ,TPBTCD.NTAXABLE ");
        sql.append("    ,TPBTCD.TAX_AMT ");
        sql.append("    ,TPBTCD.TOT_AMT ");
        sql.append("    ,TPBTCD.EIGYO_DT ");
        sql.append("    ,TPBTCD.TORI_TIME ");
        sql.append("    ,TPBTCD.CHANGE ");
        sql.append("    ,TPBTCD.MEM_CARD ");
        sql.append("    ,TPBTCD.REFUND_ORI_TRANS# ");
        sql.append("    ,TPBTCD.REFUND_ORI_STORE ");
        sql.append("    ,TPBTCD.REFUND_ORI_SALES_DATE ");
        sql.append("    ,TPBTCD.REFUND_REASON ");
        sql.append("    ,TPBTCD.REFUND_DESCRIPTION ");
        sql.append("    ,TPBTCD.DATA_SAKUSEI_DT ");
        sql.append("    ,TPBTCD.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_B_TOTAL TPBTCD ");
        //sql.append("    TMP_POS_B_TOTAL_CHECK_DATA TPBTCD "); 
        sql.append("WHERE ");
        sql.append("    TPBTCD.ERR_KB = '0' ");
        //sql.append("    AND TPBTCD.EIGYO_DT = '" + BATCH_DT + "' ");
        
        return sql.toString();
    }
    
    /**
     * BレコードエラーデータSQLを取得する
     *
     * @return Bレコードエラーデータ
     */
    private String getErrorDataFuriwakeTmpPosBTotalDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_ERR_POS_B_TOTAL ( ");
        //sql.append("INSERT /*+ APPEND */ INTO TMP_POS_B_TOTAL_ERROR_DATA ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,BTYPE ");
        sql.append("    ,REC_TOT ");
        sql.append("    ,NTAXABLE ");
        sql.append("    ,TAX_AMT ");
        sql.append("    ,TOT_AMT ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,TORI_TIME ");
        sql.append("    ,CHANGE ");
        sql.append("    ,MEM_CARD ");
        sql.append("    ,REFUND_ORI_TRANS# ");
        sql.append("    ,REFUND_ORI_STORE ");
        sql.append("    ,REFUND_ORI_SALES_DATE ");
        sql.append("    ,REFUND_REASON ");
        sql.append("    ,REFUND_DESCRIPTION ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     TPBTCD.COMMAND ");
        sql.append("    ,TPBTCD.STORE ");
        sql.append("    ,TPBTCD.POS ");
        sql.append("    ,TPBTCD.TRANS_NO ");
        sql.append("    ,TPBTCD.CASHIER_ID ");
        sql.append("    ,TPBTCD.FORMAT ");
        sql.append("    ,TPBTCD.BTYPE ");
        sql.append("    ,TPBTCD.REC_TOT ");
        sql.append("    ,TPBTCD.NTAXABLE ");
        sql.append("    ,TPBTCD.TAX_AMT ");
        sql.append("    ,TPBTCD.TOT_AMT ");
        sql.append("    ,TPBTCD.EIGYO_DT ");
        sql.append("    ,TPBTCD.TORI_TIME ");
        sql.append("    ,TPBTCD.CHANGE ");
        sql.append("    ,TPBTCD.MEM_CARD ");
        sql.append("    ,TPBTCD.REFUND_ORI_TRANS# ");
        sql.append("    ,TPBTCD.REFUND_ORI_STORE ");
        sql.append("    ,TPBTCD.REFUND_ORI_SALES_DATE ");
        sql.append("    ,TPBTCD.REFUND_REASON ");
        sql.append("    ,TPBTCD.REFUND_DESCRIPTION ");
        sql.append("    ,TPBTCD.DATA_SAKUSEI_DT ");
        sql.append("    ,TPBTCD.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");;
        sql.append("FROM ");
        sql.append("    WK_POS_B_TOTAL TPBTCD ");
        //sql.append("    TMP_POS_B_TOTAL_CHECK_DATA TPBTCD "); 
        sql.append("WHERE ");
        // 2017/02/03 VINX J.Endo FIVI(#3872) MOD(S)
        //sql.append("    TPBTCD.ERR_KB = '1' ");
        sql.append("    TPBTCD.ERR_KB != '0' ");
        // 2017/02/03 VINX J.Endo FIVI(#3872) MOD(E)
        //sql.append("    AND TPBTCD.EIGYO_DT = '" + BATCH_DT + "' ");
        
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
