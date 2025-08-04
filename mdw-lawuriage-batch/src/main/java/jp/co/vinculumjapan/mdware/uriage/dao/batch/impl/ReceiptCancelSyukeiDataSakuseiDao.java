package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル： ReceiptCancelSyukeiDataSakuseiDao クラス</p>
 * <p>説明　　：レシート取消集計データ作成</p>
 * <p>著作権　： Copyright (c) 2017</p>
 * <p>会社名　： VINX</p>
 * @author   VINX
 * @Version 1.00 (2017.05.22) X.Liu #3770
 * @Version 1.01 (2017.06.22) N.Katou #5454
 * @Version 1.02 (2022.10.19) TUNG.LT #6664
 */
public class ReceiptCancelSyukeiDataSakuseiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "レシート取消集計データ作成";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチID
    private static final String BATCH_ID = "URIB012404";
    
    /**
     * レシート取消集計データ作成処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
        
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx ps = null;
        int intCnt = 0;
        int intI = 1;
        try {
            
            String dbServerTime = FiResorceUtility.getDBServerTime();
            
            ps = invoker.getDataBase().prepareStatement(getInsertSQL());
            
            ps.setString(intI++, COMP_CD);
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, dbServerTime);
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, dbServerTime);
            
            intCnt = ps.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + intCnt + "件のレシート取消集計データを作成しました。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }           
        }
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    
    }

    /**
     * レシート取消集計データ作成SQLを取得
     * @return SQL
     */
    private String getInsertSQL(){
        StringBuilder sql = new StringBuilder();
// #6664 MOD 2022.10.19 TUNG.LT (S)
//        sql.append("INSERT  ");
//        sql.append("    INTO DT_RECEIPT_CANCEL_SUM  ");
//        sql.append("    SELECT  ");
//        sql.append("COMP_CD ");
//        sql.append(", TRIM(KEIJO_DT)    ");
//        sql.append(", TENPO_CD  ");
//        sql.append(", CHECKER_CD    ");
//        sql.append(", RECEIPT_TORIKESI_QT   ");
//        sql.append(", RECEIPT_TORIKESI_VL   ");
//        sql.append(", INSERT_USER_ID    ");
//        sql.append(", INSERT_TS ");
//        sql.append(", UPDATE_USER_ID    ");
//        sql.append(", UPDATE_TS     ");
//        sql.append("    FROM    ");
//        sql.append("(   ");
//        sql.append("  SELECT    ");
//        sql.append("    ? AS COMP_CD    ");
//        sql.append("    , EIGYO_DT AS KEIJO_DT  ");
//        sql.append("    , '00'|| STORE AS TENPO_CD ");
//        sql.append("    , CASHIER_ID AS CHECKER_CD  ");
//        // #5454 2017/6/22 N.katou(S)
////        sql.append("    , RECEIPT_TORIKESI_QT   ");
////        sql.append("    , RECEIPT_TORIKESI_VL   ");
//        sql.append("    , COUNT(EIGYO_DT) AS RECEIPT_TORIKESI_QT   ");
//        sql.append("    , SUM(RECEIPT_TORIKESI_VL) AS RECEIPT_TORIKESI_VL   ");
//        // #5454 2017/6/22 N.katou(E)
//        sql.append("    , ? AS INSERT_USER_ID   ");
//        sql.append("    , ? AS INSERT_TS    ");
//        sql.append("    , ? AS UPDATE_USER_ID   ");
//        sql.append("    , ? AS UPDATE_TS    ");
//        sql.append("  FROM  ");
//        sql.append("    (   ");
//        sql.append("      SELECT    ");
//        sql.append("        EIGYO_DT    ");
//        sql.append("        , STORE ");
//        // #5454 2017/6/22 N.katou(S)
//        sql.append("        , POS ");
//        sql.append("        , TRANS_NO ");
//        // #5454 2017/6/22 N.katou(E)
//        sql.append("        , CASHIER_ID    ");
//        // #5454 2017/6/22 N.katou(S)
////        sql.append("        , COUNT(EIGYO_DT) AS RECEIPT_TORIKESI_QT    ");
//        // #5454 2017/6/22 N.katou(E)
//        sql.append("        , SUM(ACTUAL_SELL) AS RECEIPT_TORIKESI_VL   ");
//        sql.append("      FROM  ");
//        sql.append("        WK_POS_A_ITEM   ");
//        sql.append("      WHERE ");
//        sql.append("        ERR_KB = '7'    ");
//        sql.append("        AND ACTUAL_SELL > 0     ");
//        sql.append("      GROUP BY  ");
//        sql.append("        EIGYO_DT    ");
//        sql.append("        , STORE ");
//        // #5454 2017/6/22 N.katou(S)
//        sql.append("        , POS ");
//        sql.append("        , TRANS_NO ");        
//        // #5454 2017/6/22 N.katou(E)
//        sql.append("        , CASHIER_ID    ");
//        sql.append("      UNION     ");
//        sql.append("      SELECT    ");
//        sql.append("        EIGYO_DT    ");
//        sql.append("        , STORE ");
//        // #5454 2017/6/22 N.katou(S)
//        sql.append("        , POS ");
//        sql.append("        , TRANS_NO ");
//        // #5454 2017/6/22 N.katou(E)
//        sql.append("        , '999999999' AS CASHIER_ID ");
//        // #5454 2017/6/22 N.katou(S)
////        sql.append("        , COUNT(EIGYO_DT) AS RECEIPT_TORIKESI_QT    ");
//        // #5454 2017/6/22 N.katou(E)
//        sql.append("        , SUM(ACTUAL_SELL) AS RECEIPT_TORIKESI_VL   ");
//        sql.append("      FROM  ");
//        sql.append("        WK_POS_A_ITEM   ");
//        sql.append("      WHERE ");
//        sql.append("        ERR_KB = '7'    ");
//        sql.append("        AND ACTUAL_SELL > 0     ");
//        sql.append("      GROUP BY  ");
//        sql.append("        EIGYO_DT    ");
//        sql.append("        , STORE ");
//        // #5454 2017/6/22 N.katou(S)
//        sql.append("        , POS ");
//        sql.append("        , TRANS_NO ");        
//        // #5454 2017/6/22 N.katou(E)
//        sql.append("    )   ");
//        // #5454 2017/6/22 N.katou(S)
//        sql.append("    GROUP BY ");
//        sql.append("        EIGYO_DT ");
//        sql.append("        , STORE ");
//        sql.append("        , CASHIER_ID ");
//        // #5454 2017/6/22 N.katou(E)
//        sql.append(")   ");
		
		sql.append("     MERGE INTO DT_RECEIPT_CANCEL_SUM MAIN ");
        sql.append("        USING ");
        sql.append("            (SELECT ");
        sql.append("                   COMP_CD ");
        sql.append("                   , TRIM(KEIJO_DT)    AS KEIJO_DT");
        sql.append("                   , TENPO_CD  ");
        sql.append("                   , CHECKER_CD    ");
        sql.append("                   , RECEIPT_TORIKESI_QT   ");
        sql.append("                   , RECEIPT_TORIKESI_VL   ");
        sql.append("                   , INSERT_USER_ID    ");
        sql.append("                   , INSERT_TS ");
        sql.append("                   , UPDATE_USER_ID    ");
        sql.append("                   , UPDATE_TS     ");
        sql.append("                       FROM    ");
        sql.append("                   (   ");
        sql.append("                     SELECT    ");
        sql.append("                       ? AS COMP_CD    ");
        sql.append("                       , EIGYO_DT AS KEIJO_DT  ");
        sql.append("                       , '00'|| STORE AS TENPO_CD ");
        sql.append("                       , CASHIER_ID AS CHECKER_CD  ");
        sql.append("                       , COUNT(EIGYO_DT) AS RECEIPT_TORIKESI_QT   ");
        sql.append("                       , SUM(RECEIPT_TORIKESI_VL) AS RECEIPT_TORIKESI_VL   ");
        sql.append("                       , ? AS INSERT_USER_ID   ");
        sql.append("                       , ? AS INSERT_TS    ");
        sql.append("                       , ? AS UPDATE_USER_ID   ");
        sql.append("                       , ? AS UPDATE_TS    ");
        sql.append("                     FROM  ");
        sql.append("                       (   ");
        sql.append("                         SELECT    ");
        sql.append("                           EIGYO_DT    ");
        sql.append("                           , STORE ");
        sql.append("                           , POS ");
        sql.append("                           , TRANS_NO ");
        sql.append("                           , CASHIER_ID    ");
        sql.append("                           , SUM(ACTUAL_SELL) AS RECEIPT_TORIKESI_VL   ");
        sql.append("                         FROM  ");
        sql.append("                           WK_POS_A_ITEM   ");
        sql.append("                         WHERE ");
        sql.append("                           ERR_KB = '7'    ");
        sql.append("                           AND ACTUAL_SELL > 0     ");
        sql.append("                         GROUP BY  ");
        sql.append("                           EIGYO_DT    ");
        sql.append("                           , STORE ");
        sql.append("                           , POS ");
        sql.append("                           , TRANS_NO ");        
        sql.append("                           , CASHIER_ID    ");
        sql.append("                         UNION     ");
        sql.append("                         SELECT    ");
        sql.append("                           EIGYO_DT    ");
        sql.append("                           , STORE ");
        sql.append("                           , POS ");
        sql.append("                           , TRANS_NO ");
        sql.append("                           , '999999999' AS CASHIER_ID ");
        sql.append("                           , SUM(ACTUAL_SELL) AS RECEIPT_TORIKESI_VL   ");
        sql.append("                         FROM  ");
        sql.append("                           WK_POS_A_ITEM   ");
        sql.append("                         WHERE ");
        sql.append("                           ERR_KB = '7'    ");
        sql.append("                           AND ACTUAL_SELL > 0     ");
        sql.append("                         GROUP BY  ");
        sql.append("                           EIGYO_DT    ");
        sql.append("                           , STORE ");
        sql.append("                           , POS ");
        sql.append("                           , TRANS_NO ");        
        sql.append("                       )   ");
        sql.append("                       GROUP BY ");
        sql.append("                           EIGYO_DT ");
        sql.append("                           , STORE ");
        sql.append("                           , CASHIER_ID ");
        sql.append("                   )   ");
        sql.append("            ) SUB   ");
        sql.append("     ON ( ");
        sql.append("        SUB.COMP_CD         = MAIN.COMP_CD ");
        sql.append("        AND SUB.KEIJO_DT    = MAIN.KEIJO_DT ");
        sql.append("        AND SUB.TENPO_CD    = MAIN.TENPO_CD ");
        sql.append("        AND SUB.CHECKER_CD  = MAIN.CHECKER_CD ");
        sql.append("        ) ");
        sql.append("     WHEN MATCHED THEN ");
        sql.append("            UPDATE SET ");
        sql.append("                MAIN.RECEIPT_TORIKESI_QT  = MAIN.RECEIPT_TORIKESI_QT + SUB.RECEIPT_TORIKESI_QT ");
        sql.append("                ,MAIN.RECEIPT_TORIKESI_VL = MAIN.RECEIPT_TORIKESI_VL + SUB.RECEIPT_TORIKESI_VL ");
        sql.append("                ,MAIN.UPDATE_TS           = SUB.UPDATE_TS ");
        sql.append("                ,MAIN.UPDATE_USER_ID      = SUB.UPDATE_USER_ID ");
        sql.append("     WHEN NOT MATCHED THEN ");
        sql.append("            INSERT ");
        sql.append("                ( ");
        sql.append("                 MAIN.COMP_CD ");
        sql.append("                ,MAIN.KEIJO_DT ");
        sql.append("                ,MAIN.TENPO_CD ");
        sql.append("                ,MAIN.CHECKER_CD ");
        sql.append("                ,MAIN.RECEIPT_TORIKESI_QT ");
        sql.append("                ,MAIN.RECEIPT_TORIKESI_VL ");
        sql.append("                ,MAIN.INSERT_USER_ID ");
        sql.append("                ,MAIN.INSERT_TS ");
        sql.append("                ,MAIN.UPDATE_USER_ID ");
        sql.append("                ,MAIN.UPDATE_TS ");
        sql.append("                ) ");
        sql.append("            VALUES ");
        sql.append("                ( ");
        sql.append("                 SUB.COMP_CD ");
        sql.append("                ,SUB.KEIJO_DT ");
        sql.append("                ,SUB.TENPO_CD ");
        sql.append("                ,SUB.CHECKER_CD ");
        sql.append("                ,SUB.RECEIPT_TORIKESI_QT ");
        sql.append("                ,SUB.RECEIPT_TORIKESI_VL ");
        sql.append("                ,SUB.INSERT_USER_ID ");
        sql.append("                ,SUB.INSERT_TS ");
        sql.append("                ,SUB.UPDATE_USER_ID ");
        sql.append("                ,SUB.UPDATE_TS ");
        sql.append("                ) ");
// #6664 MOD 2022.10.19 TUNG.LT (E)
        return sql.toString();
                
    }
    
    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {
        
    }

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

}
