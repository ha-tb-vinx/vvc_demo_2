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
 * <p>タイトル: DataFuriwakeTmpPosAItemDao クラス</p>
 * <p>説明:データ振分（Aレコード）</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.07.14) k.Hyo FIVI対応
 * @Version 1.01 (2017.02.03) J.Endo  FIVI対応(#3872)
 * @Version 1.02 (2017.05.22) X.Liu  FIVI対応(#3770)
 * @Version 1.03 (2017.07.10) X.Liu  FIVI対応(#5580)
 * @Version 1.04 (2023/07/03) TUNG.LT #16388 MKH対応
 * @see なし
 */
public class DataFuriwakeTmpPosAItemDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "データ振分（Aレコード）";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    
    // バッチID
    private static final String BATCH_ID = "URIB012700";
    
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
        //#3770 Add X.Liu 2017.05.22 (S)
        PreparedStatementEx preparedStatementExInsCancel = null;
        //#3770 Add X.Liu 2017.05.22 (E)
        //#5580 Add X.Liu 2017.07.10 (S)
        PreparedStatementEx preparedStatementExInsInf = null;
        //#5580 Add X.Liu 2017.07.10 (E)

        int insertCountOK = 0;
        int insertCountError = 0;
        //#3770 Add X.Liu 2017.05.22 (S)
        int insertCountCancel = 0;
        //#3770 Add X.Liu 2017.05.22 (E)
        //#5580 Add X.Liu 2017.07.10 (S)
        int insertCountInf = 0;
        //#5580 Add X.Liu 2017.07.10 (E)
        try {
            
            String dbServerTime = FiResorceUtility.getDBServerTime();
            //マスタチェック（Aレコード）

            preparedStatementExInsOK = invoker.getDataBase().prepareStatement(getOKDataFuriwakeTmpPosAItemDaoSql(dbServerTime));
            insertCountOK = preparedStatementExInsOK.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountOK + "件のAレコードOKデータを追加しました。");
            
            preparedStatementExInsError = invoker.getDataBase().prepareStatement(getErrorDataFuriwakeTmpPosAItemDaoSql(dbServerTime));
            insertCountError = preparedStatementExInsError.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountError + "件のAレコードエラーデータを追加しました。");
            
            //#3770 Add X.Liu 2017.05.22 (S)
            preparedStatementExInsCancel = invoker.getDataBase().prepareStatement(getCancelDataFuriwakeTmpPosAItemDaoSql(dbServerTime));
            insertCountCancel = preparedStatementExInsCancel.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountCancel + "件のAレコードレシートキャンセルデータを追加しました。");
            //#3770 Add X.Liu 2017.05.22 (E)
            
            //#5580 Add X.Liu 2017.07.10 (S)
            preparedStatementExInsInf = invoker.getDataBase().prepareStatement(getInsertDtPosAVatInf(dbServerTime));
            insertCountInf = preparedStatementExInsInf.executeUpdate();
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCountInf + "件のデータ振分（Aレコード）データを追加しました。");
            //#5580 Add X.Liu 2017.07.10 (E)
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
                //#3770 Add X.Liu 2017.05.22 (S)
                if (preparedStatementExInsCancel != null) {
                    preparedStatementExInsCancel.close();
                }
                //#3770 Add X.Liu 2017.05.22 (E)
                //#5580 Add X.Liu 2017.07.10 (S)
                if (preparedStatementExInsInf != null) {
                    preparedStatementExInsInf.close();
                }
                //#5580 Add X.Liu 2017.07.10 (E)
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
   
        sql.append("INSERT INTO DT_POS_A_ITEM ( ");
        //sql.append("INSERT /*+ APPEND */ INTO TMP_POS_A_ITEM_OK_DATA ( ");
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
        sql.append("     TPAICD.COMMAND ");
        sql.append("    ,TPAICD.STORE ");
        sql.append("    ,TPAICD.POS ");
        sql.append("    ,TPAICD.TRANS_NO ");
        sql.append("    ,TPAICD.CASHIER_ID ");
        sql.append("    ,TPAICD.FORMAT ");
        sql.append("    ,TPAICD.ATYPE ");
        sql.append("    ,TPAICD.ODR_LINE_IDX ");
        sql.append("    ,TPAICD.SKU ");
        sql.append("    ,TPAICD.QTY ");
        sql.append("    ,TPAICD.WEIGHT_SOLD ");
        sql.append("    ,TPAICD.REG_SELL ");
        sql.append("    ,TPAICD.ACTUAL_SELL2 ");
        sql.append("    ,TPAICD.ACTUAL_SELL ");
        sql.append("    ,TPAICD.REG_SELL_WOT ");
        sql.append("    ,TPAICD.ACTUAL_SELL_WOT2 ");
        sql.append("    ,TPAICD.ACTUAL_SELL_WOT ");
        sql.append("    ,TPAICD.EMP_PURCH ");
        sql.append("    ,TPAICD.ITEM_WEIGH ");
        sql.append("    ,TPAICD.REGULAR_UNIT_SELL ");
        sql.append("    ,TPAICD.GST_TAX ");
        sql.append("    ,TPAICD.DISC4_AMT ");
        sql.append("    ,TPAICD.ITEM_NAME_RECEIPT ");
        sql.append("    ,TPAICD.ITEM_UOM_RECEIPT ");
        sql.append("    ,TPAICD.PRCCHG_NO ");
        sql.append("    ,TPAICD.PRCCHG1_NO ");
        sql.append("    ,TPAICD.PRCCHG2_NO ");
        sql.append("    ,TPAICD.PRCCHG3_NO ");
        sql.append("    ,TPAICD.PRIVILEGE_MEM ");
        sql.append("    ,TPAICD.OVER_WRITE_FLAG ");
        sql.append("    ,TPAICD.HAMPER_ITEM_CD ");
        sql.append("    ,TPAICD.SUPERVISOR_ID ");
        sql.append("    ,TPAICD.EIGYO_DT ");
        sql.append("    ,TPAICD.DATA_SAKUSEI_DT ");
        sql.append("    ,TPAICD.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_A_ITEM TPAICD "); 
        //sql.append("    TMP_POS_A_ITEM_CHECK_DATA TPAICD "); 
        sql.append("WHERE ");
        sql.append("    TPAICD.ERR_KB = '0' ");
        //sql.append("    AND TPAICD.EIGYO_DT = '" + BATCH_DT + "' ");
        
        return sql.toString();
    }

    /**
     * AレコードエラーデータSQLを取得する
     *
     * @return Aレコードエラーデータ
     */
    private String getErrorDataFuriwakeTmpPosAItemDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT INTO DT_ERR_POS_A_ITEM ( ");
        //sql.append("INSERT /*+ APPEND */ INTO TMP_POS_A_ITEM_ERROR_DATA ( ");
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
        sql.append("     TPAICD.COMMAND ");
        sql.append("    ,TPAICD.STORE ");
        sql.append("    ,TPAICD.POS ");
        sql.append("    ,TPAICD.TRANS_NO ");
        sql.append("    ,TPAICD.CASHIER_ID ");
        sql.append("    ,TPAICD.FORMAT ");
        sql.append("    ,TPAICD.ATYPE ");
        sql.append("    ,TPAICD.ODR_LINE_IDX ");
        sql.append("    ,TPAICD.SKU ");
        sql.append("    ,TPAICD.QTY ");
        sql.append("    ,TPAICD.WEIGHT_SOLD ");
        sql.append("    ,TPAICD.REG_SELL ");
        sql.append("    ,TPAICD.ACTUAL_SELL2 ");
        sql.append("    ,TPAICD.ACTUAL_SELL ");
        sql.append("    ,TPAICD.REG_SELL_WOT ");
        sql.append("    ,TPAICD.ACTUAL_SELL_WOT2 ");
        sql.append("    ,TPAICD.ACTUAL_SELL_WOT ");
        sql.append("    ,TPAICD.EMP_PURCH ");
        sql.append("    ,TPAICD.ITEM_WEIGH ");
        sql.append("    ,TPAICD.REGULAR_UNIT_SELL ");
        sql.append("    ,TPAICD.GST_TAX ");
        sql.append("    ,TPAICD.DISC4_AMT ");
        sql.append("    ,TPAICD.ITEM_NAME_RECEIPT ");
        sql.append("    ,TPAICD.ITEM_UOM_RECEIPT ");
        sql.append("    ,TPAICD.PRCCHG_NO ");
        sql.append("    ,TPAICD.PRCCHG1_NO ");
        sql.append("    ,TPAICD.PRCCHG2_NO ");
        sql.append("    ,TPAICD.PRCCHG3_NO ");
        sql.append("    ,TPAICD.PRIVILEGE_MEM ");
        sql.append("    ,TPAICD.OVER_WRITE_FLAG ");
        sql.append("    ,TPAICD.HAMPER_ITEM_CD ");
        sql.append("    ,TPAICD.SUPERVISOR_ID ");
        sql.append("    ,TPAICD.EIGYO_DT ");
        sql.append("    ,TPAICD.DATA_SAKUSEI_DT ");
        sql.append("    ,TPAICD.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_A_ITEM TPAICD "); 
        //sql.append("    TMP_POS_A_ITEM_CHECK_DATA TPAICD "); 
        sql.append("WHERE ");
        // 2017/02/03 VINX J.Endo FIVI(#3872) MOD(S)
        //sql.append("    TPAICD.ERR_KB = '1' ");
        sql.append("    TPAICD.ERR_KB != '0' ");
        // 2017/02/03 VINX J.Endo FIVI(#3872) MOD(E)
        //sql.append("    AND TPAICD.EIGYO_DT = '" + BATCH_DT + "' ");
        //#3770 Add X.Liu 2017.05.22 (S)
        sql.append("    AND TPAICD.ERR_KB != '7' ");
        //#3770 Add X.Liu 2017.05.22 (E)
        return sql.toString();
    }
    
    //#3770 Add X.Liu 2017.05.22 (S)
    /**
     * AレコードレシートキャンセルデータSQLを取得する
     *
     * @return Aレコードエラーデータ
     */
    private String getCancelDataFuriwakeTmpPosAItemDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO DT_POS_A_RECEIPT_CANCEL ( ");
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
        sql.append("     TPAICD.COMMAND ");
        sql.append("    ,TPAICD.STORE ");
        sql.append("    ,TPAICD.POS ");
        sql.append("    ,TPAICD.TRANS_NO ");
        sql.append("    ,TPAICD.CASHIER_ID ");
        sql.append("    ,TPAICD.FORMAT ");
        sql.append("    ,TPAICD.ATYPE ");
        sql.append("    ,TPAICD.ODR_LINE_IDX ");
        sql.append("    ,TPAICD.SKU ");
        sql.append("    ,TPAICD.QTY ");
        sql.append("    ,TPAICD.WEIGHT_SOLD ");
        sql.append("    ,TPAICD.REG_SELL ");
        sql.append("    ,TPAICD.ACTUAL_SELL2 ");
        sql.append("    ,TPAICD.ACTUAL_SELL ");
        sql.append("    ,TPAICD.REG_SELL_WOT ");
        sql.append("    ,TPAICD.ACTUAL_SELL_WOT2 ");
        sql.append("    ,TPAICD.ACTUAL_SELL_WOT ");
        sql.append("    ,TPAICD.EMP_PURCH ");
        sql.append("    ,TPAICD.ITEM_WEIGH ");
        sql.append("    ,TPAICD.REGULAR_UNIT_SELL ");
        sql.append("    ,TPAICD.GST_TAX ");
        sql.append("    ,TPAICD.DISC4_AMT ");
        sql.append("    ,TPAICD.ITEM_NAME_RECEIPT ");
        sql.append("    ,TPAICD.ITEM_UOM_RECEIPT ");
        sql.append("    ,TPAICD.PRCCHG_NO ");
        sql.append("    ,TPAICD.PRCCHG1_NO ");
        sql.append("    ,TPAICD.PRCCHG2_NO ");
        sql.append("    ,TPAICD.PRCCHG3_NO ");
        sql.append("    ,TPAICD.PRIVILEGE_MEM ");
        sql.append("    ,TPAICD.OVER_WRITE_FLAG ");
        sql.append("    ,TPAICD.HAMPER_ITEM_CD ");
        sql.append("    ,TPAICD.SUPERVISOR_ID ");
        sql.append("    ,TPAICD.EIGYO_DT ");
        sql.append("    ,TPAICD.DATA_SAKUSEI_DT ");
        sql.append("    ,TPAICD.ERR_KB ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("    ,'" + BATCH_ID + "' ");
        sql.append("    ,'" + dbServerTime + "' ");
        sql.append("FROM ");
        sql.append("    WK_POS_A_ITEM TPAICD "); 
        sql.append("WHERE ");
        sql.append("    TPAICD.ERR_KB = '7' ");
        return sql.toString();
    }
    
    //#3770 Add X.Liu 2017.05.22 (E)
    //#5580 Add X.Liu 2017.07.10 (S)
    private String getInsertDtPosAVatInf(String dbServerTime){
        StringBuilder sql  = new StringBuilder();
        sql.append("INSERT /*+ APPEND */");
        sql.append("INTO DT_POS_A_VAT_INF( ");
        sql.append("  COMMAND");
        sql.append("  , STORE");
        sql.append("  , POS");
        sql.append("  , TRANS_NO");
        sql.append("  , CASHIER_ID");
        sql.append("  , EIGYO_DT");
        sql.append("  , DATA_SAKUSEI_DT");
        sql.append("  , VAT_PRINT_KB");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS");
        sql.append(") ");
        sql.append("SELECT");
        sql.append("  MAX(SUB.COMMAND) AS COMMAND");
        sql.append("  , SUB.STORE");
        sql.append("  , SUB.POS");
        sql.append("  , SUB.TRANS_NO");
        sql.append("  , MAX(SUB.CASHIER_ID) AS CASHIER_ID");
        sql.append("  , SUB.EIGYO_DT");
        sql.append("  , MAX(SUB.DATA_SAKUSEI_DT) AS DATA_SAKUSEI_DT");
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("  , CASE ");
        // sql.append("    WHEN ( ");
        // sql.append("      SELECT");
        // sql.append("        COUNT(WPAI.ATYPE) ");
        // sql.append("      FROM");
        // sql.append("        WK_POS_A_ITEM WPAI ");
        // sql.append("      WHERE");
        // sql.append("        WPAI.STORE = SUB.STORE ");
        // sql.append("        AND WPAI.POS = SUB.POS ");
        // sql.append("        AND WPAI.TRANS_NO = SUB.TRANS_NO ");
        // sql.append("        AND WPAI.EIGYO_DT = SUB.EIGYO_DT ");
        // sql.append("        AND (WPAI.COMMAND = '0044' OR WPAI.COMMAND = '0045') ");
        // sql.append("        AND WPAI.ATYPE = '0301' ");
        // sql.append("        AND WPAI.ERR_KB = '0' ");
        // sql.append("      GROUP BY");
        // sql.append("        WPAI.ATYPE");
        // sql.append("    ) > 0 ");
        // sql.append("    AND ( ");
        // sql.append("      SELECT");
        // sql.append("        COUNT(WPAI.ATYPE) ");
        // sql.append("      FROM");
        // sql.append("        ( ");
        // sql.append("          SELECT");
        // sql.append("            ATYPE");
        // sql.append("            , STORE");
        // sql.append("            , POS");
        // sql.append("            , TRANS_NO");
        // sql.append("            , EIGYO_DT");
        // sql.append("            , COMMAND ");
        // sql.append("          FROM");
        // sql.append("            WK_POS_A_ITEM WPAI ");
        // sql.append("          GROUP BY");
        // sql.append("            STORE");
        // sql.append("            , POS");
        // sql.append("            , TRANS_NO");
        // sql.append("            , ATYPE");
        // sql.append("            , EIGYO_DT");
        // sql.append("            , COMMAND");
        // sql.append("        ) WPAI ");
        // sql.append("      WHERE");
        // sql.append("        WPAI.STORE = SUB.STORE ");
        // sql.append("        AND WPAI.POS = SUB.POS ");
        // sql.append("        AND WPAI.TRANS_NO = SUB.TRANS_NO ");
        // sql.append("        AND WPAI.EIGYO_DT = SUB.EIGYO_DT ");
        // sql.append("        AND ( ");
        // sql.append("          ( ");
        // sql.append("            WPAI.COMMAND <> '0044' ");
        // sql.append("            AND WPAI.COMMAND <> '0045'");
        // sql.append("          ) ");
        // sql.append("          OR WPAI.ATYPE <> '0301'");
        // sql.append("        )");
        // sql.append("    ) = 0 ");
        // sql.append("    THEN '5' ");
        // sql.append("    WHEN ( ");
        // sql.append("      SELECT");
        // sql.append("        COUNT(WPAI.ATYPE) ");
        // sql.append("      FROM");
        // sql.append("        WK_POS_A_ITEM WPAI ");
        // sql.append("      WHERE");
        // sql.append("        WPAI.STORE = SUB.STORE ");
        // sql.append("        AND WPAI.POS = SUB.POS ");
        // sql.append("        AND WPAI.TRANS_NO = SUB.TRANS_NO ");
        // sql.append("        AND WPAI.EIGYO_DT = SUB.EIGYO_DT ");
        // sql.append("        AND WPAI.COMMAND = '0043' ");
        // sql.append("        AND WPAI.ATYPE = '1011' ");
        // sql.append("        AND WPAI.ERR_KB = '0'");
        // sql.append("    ) > 0 ");
        // sql.append("    THEN '6' ");
        // sql.append("    WHEN ( ");
        // sql.append("      SELECT");
        // sql.append("        COUNT(WPAI.ATYPE) ");
        // sql.append("      FROM");
        // sql.append("        WK_POS_A_ITEM WPAI ");
        // sql.append("      WHERE");
        // sql.append("        WPAI.STORE = SUB.STORE ");
        // sql.append("        AND WPAI.POS = SUB.POS ");
        // sql.append("        AND WPAI.TRANS_NO = SUB.TRANS_NO ");
        // sql.append("        AND WPAI.EIGYO_DT = SUB.EIGYO_DT ");
        // sql.append("        AND (WPAI.COMMAND = '0044' OR WPAI.COMMAND = '0045') ");
        // sql.append("        AND WPAI.ATYPE = '1311' ");
        // sql.append("        AND WPAI.ERR_KB = '0' ");
        // sql.append("      GROUP BY");
        // sql.append("        WPAI.ATYPE");
        // sql.append("    ) > 0 ");
        // sql.append("    AND ( ");
        // sql.append("      SELECT");
        // sql.append("        COUNT(WPAI.ATYPE) ");
        // sql.append("      FROM");
        // sql.append("        ( ");
        // sql.append("          SELECT");
        // sql.append("            ATYPE");
        // sql.append("            , STORE");
        // sql.append("            , POS");
        // sql.append("            , TRANS_NO");
        // sql.append("            , EIGYO_DT");
        // sql.append("            , COMMAND ");
        // sql.append("          FROM");
        // sql.append("            WK_POS_A_ITEM WPAI ");
        // sql.append("          GROUP BY");
        // sql.append("            STORE");
        // sql.append("            , POS");
        // sql.append("            , TRANS_NO");
        // sql.append("            , ATYPE");
        // sql.append("            , EIGYO_DT");
        // sql.append("            , COMMAND");
        // sql.append("        ) WPAI ");
        // sql.append("      WHERE");
        // sql.append("        WPAI.STORE = SUB.STORE ");
        // sql.append("        AND WPAI.POS = SUB.POS ");
        // sql.append("        AND WPAI.TRANS_NO = SUB.TRANS_NO ");
        // sql.append("        AND WPAI.EIGYO_DT = SUB.EIGYO_DT ");
        // sql.append("        AND ( ");
        // sql.append("          ( ");
        // sql.append("            WPAI.COMMAND <> '0044' ");
        // sql.append("            AND WPAI.COMMAND <> '0045'");
        // sql.append("          ) ");
        // sql.append("          OR WPAI.ATYPE <> '1311'");
        // sql.append("        )");
        // sql.append("    ) = 0 ");
        // sql.append("    THEN '7' ");
        // sql.append("    ELSE '3' ");
        // sql.append("    END AS VAT_PRINT_KB");
        sql.append("    ,TMP_URIAGE.URIAGE_FRG AS VAT_PRINT_KB ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        sql.append("    ,'" + BATCH_ID + "' AS INSERT_USER_ID ");
        sql.append("    ,'" + dbServerTime + "' AS INSERT_TS ");
        sql.append("    ,'" + BATCH_ID + "' AS UPDATE_USER_ID");
        sql.append("    ,'" + dbServerTime + "' AS UPDATE_TS ");
        sql.append("FROM");
        sql.append("  WK_POS_A_ITEM SUB ");
        // #16388 ADD 2023.07.03 TUNG.LT (S)
        sql.append(" LEFT JOIN ");
        sql.append("  (SELECT ");
        sql.append("    STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO  ");
        sql.append("    ,EIGYO_DT  ");
        sql.append("    ,CASE ");
        sql.append("      WHEN SUM( DECODE(WPAI.ATYPE,'0301',1,0)) = COUNT(WPAI.ATYPE) ");
        sql.append("         AND (COMMAND                          = '0044' ");
        sql.append("         OR COMMAND                            = '0045') ");
        sql.append("      THEN '5' ");
        sql.append("      WHEN SUM( DECODE(WPAI.ATYPE,'1011',1,0)) > 0 ");
        sql.append("         AND COMMAND                           = '0043' ");
        sql.append("      THEN '6' ");
        sql.append("      WHEN SUM( DECODE(WPAI.ATYPE,'1311',1,0)) = COUNT(WPAI.ATYPE) ");
        sql.append("         AND (COMMAND                          = '0044' ");
        sql.append("         OR COMMAND                            = '0045') ");
        sql.append("      THEN '7' ");
        sql.append("      ELSE '3' ");
        sql.append("    END AS URIAGE_FRG ");
        sql.append("  FROM WK_POS_A_ITEM WPAI ");
        sql.append("  WHERE ERR_KB = '0' ");
        sql.append("  GROUP BY ");
        sql.append("    STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,COMMAND ");
        sql.append("  ) TMP_URIAGE ");
        sql.append(" ON TMP_URIAGE.STORE     = SUB.STORE ");
        sql.append(" AND TMP_URIAGE.POS      = SUB.POS ");
        sql.append(" AND TMP_URIAGE.TRANS_NO = SUB.TRANS_NO ");
        sql.append(" AND TMP_URIAGE.EIGYO_DT = SUB.EIGYO_DT ");
        // #16388 ADD 2023.07.03 TUNG.LT (E)
        sql.append("WHERE");
        sql.append("  SUB.ERR_KB = '0' ");
        sql.append("GROUP BY");
        sql.append("  SUB.STORE");
        sql.append("  , SUB.POS");
        sql.append("  , SUB.TRANS_NO");
        sql.append("  , SUB.EIGYO_DT");
        // #16388 ADD 2023.07.03 TUNG.LT (S)
        sql.append("  , TMP_URIAGE.URIAGE_FRG ");
        // #16388 ADD 2023.07.03 TUNG.LT (E)

        return sql.toString();
                
    }
    //#5580 Add X.Liu 2017.07.10 (E)
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
