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
 * <p>タイトル: DataFuriwakeTmpPosHHamperDao クラス</p>
 * <p>説明:データ振分（Hレコード）</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2017.02.27) N.Katou FIVI対応
 * @see なし
 */
public class DataFuriwakeTmpPosHHamperDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "データ振分（Hレコード）";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    
    // バッチID
    private static final String BATCH_ID = "URIB012950";
    
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    /**
     * データ振分（Hレコード）
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
            //マスタチェック（Hレコード）

            preparedStatementExInsOK = invoker.getDataBase().prepareStatement(getOKDataFuriwakeTmpPosHHamperDaoSql(dbServerTime));
            insertCountOK = preparedStatementExInsOK.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のHレコードOKデータを追加しました。");
            
            preparedStatementExInsError = invoker.getDataBase().prepareStatement(getErrorDataFuriwakeTmpPosHHamperDaoSql(dbServerTime));
            insertCountError = preparedStatementExInsError.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountError + "件のHレコードエラーデータを追加しました。");
            
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
     * HレコードOKデータSQLを取得する
     *
     * @return HレコードOKデータ
     */
    private String getOKDataFuriwakeTmpPosHHamperDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_POS_H_HAMPER ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,ATYPE ");
        sql.append("    ,ODR_LINE_IDX ");
        sql.append("    ,SKU ");
        sql.append("    ,QTY ");
        sql.append("    ,WEIGHT_SOLD ");
        sql.append("    ,REG_SELL ");
        sql.append("    ,ACTUAL_SELL2 ");
        sql.append("    ,ACTUAL_SELL ");
        sql.append("    ,REG_SELL_WOT ");
        sql.append("    ,ACTUAL_SELL_WOT2 ");
        sql.append("    ,ACTUAL_SELL_WOT ");
        sql.append("    ,EMP_PURCH ");
        sql.append("    ,ITEM_WEIGH ");
        sql.append("    ,REGULAR_UNIT_SELL ");
        sql.append("    ,GST_TAX ");
        sql.append("    ,DISC4_AMT ");
        sql.append("    ,ITEM_NAME_RECEIPT ");
        sql.append("    ,ITEM_UOM_RECEIPT ");
        sql.append("    ,PRCCHG_NO ");
        sql.append("    ,PRCCHG1_NO ");
        sql.append("    ,PRCCHG2_NO ");
        sql.append("    ,PRCCHG3_NO ");
        sql.append("    ,PRIVILEGE_MEM ");
        sql.append("    ,OVER_WRITE_FLAG ");
        sql.append("    ,HAMPER_ITEM_CD ");
        sql.append("    ,SUPERVISOR_ID ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     WPHH.COMMAND ");
        sql.append("    ,WPHH.STORE ");
        sql.append("    ,WPHH.POS ");
        sql.append("    ,WPHH.TRANS_NO ");
        sql.append("    ,WPHH.CASHIER_ID ");
        sql.append("    ,WPHH.FORMAT ");
        sql.append("    ,WPHH.ATYPE ");
        sql.append("    ,WPHH.ODR_LINE_IDX ");
        sql.append("    ,WPHH.SKU ");
        sql.append("    ,WPHH.QTY ");
        sql.append("    ,WPHH.WEIGHT_SOLD ");
        sql.append("    ,WPHH.REG_SELL ");
        sql.append("    ,WPHH.ACTUAL_SELL2 ");
        sql.append("    ,WPHH.ACTUAL_SELL ");
        sql.append("    ,WPHH.REG_SELL_WOT ");
        sql.append("    ,WPHH.ACTUAL_SELL_WOT2 ");
        sql.append("    ,WPHH.ACTUAL_SELL_WOT ");
        sql.append("    ,WPHH.EMP_PURCH ");
        sql.append("    ,WPHH.ITEM_WEIGH ");
        sql.append("    ,WPHH.REGULAR_UNIT_SELL ");
        sql.append("    ,WPHH.GST_TAX ");
        sql.append("    ,WPHH.DISC4_AMT ");
        sql.append("    ,WPHH.ITEM_NAME_RECEIPT ");
        sql.append("    ,WPHH.ITEM_UOM_RECEIPT ");
        sql.append("    ,WPHH.PRCCHG_NO ");
        sql.append("    ,WPHH.PRCCHG1_NO ");
        sql.append("    ,WPHH.PRCCHG2_NO ");
        sql.append("    ,WPHH.PRCCHG3_NO ");
        sql.append("    ,WPHH.PRIVILEGE_MEM ");
        sql.append("    ,WPHH.OVER_WRITE_FLAG ");
        sql.append("    ,WPHH.HAMPER_ITEM_CD ");
        sql.append("    ,WPHH.SUPERVISOR_ID ");
        sql.append("    ,WPHH.EIGYO_DT ");
        sql.append("    ,WPHH.DATA_SAKUSEI_DT ");
        sql.append("    ,WPHH.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_H_HAMPER WPHH "); 
        sql.append("WHERE ");
        sql.append("    WPHH.ERR_KB = '0' ");
        
        return sql.toString();
    }

    /**
     * HレコードエラーデータSQLを取得する
     *
     * @return Hレコードエラーデータ
     */
    private String getErrorDataFuriwakeTmpPosHHamperDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_ERR_POS_H_HAMPER ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,ATYPE ");
        sql.append("    ,ODR_LINE_IDX ");
        sql.append("    ,SKU ");
        sql.append("    ,QTY ");
        sql.append("    ,WEIGHT_SOLD ");
        sql.append("    ,REG_SELL ");
        sql.append("    ,ACTUAL_SELL2 ");
        sql.append("    ,ACTUAL_SELL ");
        sql.append("    ,REG_SELL_WOT ");
        sql.append("    ,ACTUAL_SELL_WOT2 ");
        sql.append("    ,ACTUAL_SELL_WOT ");
        sql.append("    ,EMP_PURCH ");
        sql.append("    ,ITEM_WEIGH ");
        sql.append("    ,REGULAR_UNIT_SELL ");
        sql.append("    ,GST_TAX ");
        sql.append("    ,DISC4_AMT ");
        sql.append("    ,ITEM_NAME_RECEIPT ");
        sql.append("    ,ITEM_UOM_RECEIPT ");
        sql.append("    ,PRCCHG_NO ");
        sql.append("    ,PRCCHG1_NO ");
        sql.append("    ,PRCCHG2_NO ");
        sql.append("    ,PRCCHG3_NO ");
        sql.append("    ,PRIVILEGE_MEM ");
        sql.append("    ,OVER_WRITE_FLAG ");
        sql.append("    ,HAMPER_ITEM_CD ");
        sql.append("    ,SUPERVISOR_ID ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        
        sql.append("SELECT ");
        sql.append("     WPHH.COMMAND ");
        sql.append("    ,WPHH.STORE ");
        sql.append("    ,WPHH.POS ");
        sql.append("    ,WPHH.TRANS_NO ");
        sql.append("    ,WPHH.CASHIER_ID ");
        sql.append("    ,WPHH.FORMAT ");
        sql.append("    ,WPHH.ATYPE ");
        sql.append("    ,WPHH.ODR_LINE_IDX ");
        sql.append("    ,WPHH.SKU ");
        sql.append("    ,WPHH.QTY ");
        sql.append("    ,WPHH.WEIGHT_SOLD ");
        sql.append("    ,WPHH.REG_SELL ");
        sql.append("    ,WPHH.ACTUAL_SELL2 ");
        sql.append("    ,WPHH.ACTUAL_SELL ");
        sql.append("    ,WPHH.REG_SELL_WOT ");
        sql.append("    ,WPHH.ACTUAL_SELL_WOT2 ");
        sql.append("    ,WPHH.ACTUAL_SELL_WOT ");
        sql.append("    ,WPHH.EMP_PURCH ");
        sql.append("    ,WPHH.ITEM_WEIGH ");
        sql.append("    ,WPHH.REGULAR_UNIT_SELL ");
        sql.append("    ,WPHH.GST_TAX ");
        sql.append("    ,WPHH.DISC4_AMT ");
        sql.append("    ,WPHH.ITEM_NAME_RECEIPT ");
        sql.append("    ,WPHH.ITEM_UOM_RECEIPT ");
        sql.append("    ,WPHH.PRCCHG_NO ");
        sql.append("    ,WPHH.PRCCHG1_NO ");
        sql.append("    ,WPHH.PRCCHG2_NO ");
        sql.append("    ,WPHH.PRCCHG3_NO ");
        sql.append("    ,WPHH.PRIVILEGE_MEM ");
        sql.append("    ,WPHH.OVER_WRITE_FLAG ");
        sql.append("    ,WPHH.HAMPER_ITEM_CD ");
        sql.append("    ,WPHH.SUPERVISOR_ID ");
        sql.append("    ,WPHH.EIGYO_DT ");
        sql.append("    ,WPHH.DATA_SAKUSEI_DT ");
        sql.append("    ,WPHH.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_H_HAMPER WPHH "); 
        sql.append("WHERE ");
        sql.append("    WPHH.ERR_KB != '0' ");
        
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
