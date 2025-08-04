package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: HamperKouseiSyohinUriageJisekiCreateDao.java クラス</p>
 * <p>説明　: 返品レシート集計データ作成</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2017/01/25) S_MDware-G_FIVIマート様開発 VINX J.Endo #3184
 * @Version 1.01 (2017/06/14)  VINX X.Liu #5399
 *
 */
public class HenpinReceiptSummaryCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "返品レシート集計データ作成";
    /** 登録先テーブル名称(返品レシート集計データ) */
    private static final String DT_POS_A_CUT_HENPIN_NAME = "返品レシート集計データ";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExInsert = null;

        int insertCount = 0;

        try {
            // 返品レシート集計データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_POS_A_CUT_HENPIN_NAME + "の追加を開始します。");

            // 返品レシート集計データ(チェッカー別)作成
            preparedStatementExInsert = invoker.getDataBase().prepareStatement(getHenpinReceiptSummaryCheckerInsertSql());

            preparedStatementExInsert.setString(1, userId);
            preparedStatementExInsert.setString(2, FiResorceUtility.getDBServerTime());
            preparedStatementExInsert.setString(3, userId);
            preparedStatementExInsert.setString(4, FiResorceUtility.getDBServerTime());

            insertCount = preparedStatementExInsert.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の返品レシート集計データ(チェッカー別)を追加しました。");

            // 返品レシート集計データ(店舗・計上日別)作成
            preparedStatementExInsert = invoker.getDataBase().prepareStatement(getHenpinReceiptSummaryInsertSql());

            preparedStatementExInsert.setString(1, userId);
            preparedStatementExInsert.setString(2, FiResorceUtility.getDBServerTime());
            preparedStatementExInsert.setString(3, userId);
            preparedStatementExInsert.setString(4, FiResorceUtility.getDBServerTime());

            insertCount = preparedStatementExInsert.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の返品レシート集計データ(店舗・計上日別)を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_POS_A_CUT_HENPIN_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExInsert != null) {
                    preparedStatementExInsert.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 返品レシート集計データ(チェッカー別)作成用SQLを取得する
     *
     * @return 返品レシート集計データ作成用SQL
     */
    private String getHenpinReceiptSummaryCheckerInsertSql() {
        StringBuilder sql = new StringBuilder();

        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_HENPIN_RECEIPT_SUMMARY DHRS ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      EIGYO_DT AS KEIJO_DT");
        sql.append("      , LPAD(STORE, 6, '0') AS TENPO_CD");
        sql.append("      , CASHIER_ID AS CHECKER_CD");
        sql.append("      , COUNT(TRANS_NO) AS HENPIN_RECEIPT_QT");
        sql.append("      , SUM(ACTUAL_SELL) AS HENPIN_RECEIPT_VL");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS ");
        sql.append("    FROM");
        sql.append("      ( ");
        sql.append("        SELECT");
        sql.append("          SUBSTR(EIGYO_DT, 1, 8) AS EIGYO_DT");
        sql.append("          , STORE");
        sql.append("          , POS");
        sql.append("          , TRANS_NO");
        sql.append("          , CASHIER_ID");
        sql.append("          , SUM(NVL(ACTUAL_SELL, 0)) AS ACTUAL_SELL ");
        sql.append("        FROM");
        sql.append("          WK_POS_A_ITEM ");
        sql.append("        WHERE");
        sql.append("          ERR_KB = '0' ");
        sql.append("          AND COMMAND = '0044' ");
        sql.append("        GROUP BY");
        sql.append("          EIGYO_DT");
        sql.append("          , STORE");
        sql.append("          , POS");
        sql.append("          , TRANS_NO");
        sql.append("          , CASHIER_ID");
        sql.append("      ) ");
        sql.append("    GROUP BY");
        sql.append("      EIGYO_DT");
        sql.append("      , STORE");
        sql.append("      , CASHIER_ID");
        sql.append("  ) WPAI ");
        sql.append("    ON ( ");
        sql.append("      DHRS.KEIJO_DT = WPAI.KEIJO_DT ");
        sql.append("      AND DHRS.TENPO_CD = WPAI.TENPO_CD ");
        sql.append("      AND DHRS.CHECKER_CD = WPAI.CHECKER_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  HENPIN_RECEIPT_QT = NVL(DHRS.HENPIN_RECEIPT_QT,0) + NVL(WPAI.HENPIN_RECEIPT_QT,0)");
        sql.append("  , HENPIN_RECEIPT_VL = NVL(DHRS.HENPIN_RECEIPT_VL,0) + NVL(WPAI.HENPIN_RECEIPT_VL,0)");
        sql.append("  , UPDATE_USER_ID = WPAI.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WPAI.UPDATE_TS WHEN NOT MATCHED THEN ");
//        sql.append("INSERT INTO DT_HENPIN_RECEIPT_SUMMARY ( ");
        sql.append("INSERT  ( ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("    KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,HENPIN_RECEIPT_QT ");
        sql.append("   ,HENPIN_RECEIPT_VL ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("SELECT ");
//        sql.append("    EIGYO_DT ");
//        sql.append("   ,LPAD(STORE,6,'0') ");
//        sql.append("   ,CASHIER_ID ");
//        sql.append("   ,COUNT(TRANS_NO) ");
//        sql.append("   ,SUM(ACTUAL_SELL) ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("FROM ( ");
//        sql.append("    SELECT ");
//        sql.append("        SUBSTR(EIGYO_DT,1,8) AS EIGYO_DT ");
//        sql.append("       ,STORE ");
//        sql.append("       ,POS ");
//        sql.append("       ,TRANS_NO ");
//        sql.append("       ,CASHIER_ID ");
//        sql.append("       ,SUM(NVL(ACTUAL_SELL,0)) AS ACTUAL_SELL ");
//        sql.append("    FROM WK_POS_A_ITEM ");
//        sql.append("    WHERE ");
//        sql.append("        ERR_KB  = '0' AND ");
//        sql.append("        COMMAND = '0044' ");
//        sql.append("    GROUP BY ");
//        sql.append("        EIGYO_DT ");
//        sql.append("       ,STORE ");
//        sql.append("       ,POS ");
//        sql.append("       ,TRANS_NO ");
//        sql.append("       ,CASHIER_ID ");
//        sql.append("    ) ");
//        sql.append("GROUP BY ");
//        sql.append("    EIGYO_DT ");
//        sql.append("   ,STORE ");
//        sql.append("   ,CASHIER_ID ");
        sql.append(" VALUES ( ");
        sql.append("      WPAI.KEIJO_DT");
        sql.append("      , WPAI.TENPO_CD");
        sql.append("      , WPAI.CHECKER_CD");
        sql.append("      , WPAI.HENPIN_RECEIPT_QT");
        sql.append("      , WPAI.HENPIN_RECEIPT_VL");
        sql.append("      , WPAI.INSERT_USER_ID");
        sql.append("      , WPAI.INSERT_TS");
        sql.append("      , WPAI.UPDATE_USER_ID");
        sql.append("      , WPAI.UPDATE_TS");
        sql.append("  )  ");
        //#5399 Mod X.Liu 2017.06.14 (E)

        return sql.toString();
    }

    /**
     * 返品レシート集計データ(店舗・計上日別)作成用SQLを取得する
     *
     * @return 返品レシート集計データ作成用SQL
     */
    private String getHenpinReceiptSummaryInsertSql() {
        StringBuilder sql = new StringBuilder();
        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_HENPIN_RECEIPT_SUMMARY DHRS ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      EIGYO_DT AS KEIJO_DT");
        sql.append("      , LPAD(STORE, 6, '0') AS TENPO_CD");
        sql.append("      , '999999999' AS CHECKER_CD");
        sql.append("      , COUNT(TRANS_NO) AS HENPIN_RECEIPT_QT");
        sql.append("      , SUM(ACTUAL_SELL) AS HENPIN_RECEIPT_VL");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS ");
        sql.append("    FROM");
        sql.append("      ( ");
        sql.append("        SELECT");
        sql.append("          SUBSTR(EIGYO_DT, 1, 8) AS EIGYO_DT");
        sql.append("          , STORE");
        sql.append("          , POS");
        sql.append("          , TRANS_NO");
        sql.append("          , SUM(NVL(ACTUAL_SELL, 0)) AS ACTUAL_SELL ");
        sql.append("        FROM");
        sql.append("          WK_POS_A_ITEM ");
        sql.append("        WHERE");
        sql.append("          ERR_KB = '0' ");
        sql.append("          AND COMMAND = '0044' ");
        sql.append("        GROUP BY");
        sql.append("          EIGYO_DT");
        sql.append("          , STORE");
        sql.append("          , POS");
        sql.append("          , TRANS_NO");
        sql.append("      ) ");
        sql.append("    GROUP BY");
        sql.append("      EIGYO_DT");
        sql.append("      , STORE");
        sql.append("  ) WPAI ");
        sql.append("    ON ( ");
        sql.append("      DHRS.KEIJO_DT = WPAI.KEIJO_DT ");
        sql.append("      AND DHRS.TENPO_CD = WPAI.TENPO_CD ");
        sql.append("      AND DHRS.CHECKER_CD = WPAI.CHECKER_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  HENPIN_RECEIPT_QT = NVL(DHRS.HENPIN_RECEIPT_QT,0) + NVL(WPAI.HENPIN_RECEIPT_QT,0)");
        sql.append("  , HENPIN_RECEIPT_VL = NVL(DHRS.HENPIN_RECEIPT_VL,0) + NVL(WPAI.HENPIN_RECEIPT_VL,0)");
        sql.append("  , UPDATE_USER_ID = WPAI.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WPAI.UPDATE_TS WHEN NOT MATCHED THEN ");
//        sql.append("INSERT INTO DT_HENPIN_RECEIPT_SUMMARY ( ");
        sql.append("INSERT  ( ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("    KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,HENPIN_RECEIPT_QT ");
        sql.append("   ,HENPIN_RECEIPT_VL ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("SELECT ");
//        sql.append("    EIGYO_DT ");
//        sql.append("   ,LPAD(STORE,6,'0') ");
//        sql.append("   ,'999999999' ");
//        sql.append("   ,COUNT(TRANS_NO) ");
//        sql.append("   ,SUM(ACTUAL_SELL) ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("FROM ( ");
//        sql.append("    SELECT ");
//        sql.append("        SUBSTR(EIGYO_DT,1,8) AS EIGYO_DT ");
//        sql.append("       ,STORE ");
//        sql.append("       ,POS ");
//        sql.append("       ,TRANS_NO ");
//        sql.append("       ,SUM(NVL(ACTUAL_SELL,0)) AS ACTUAL_SELL ");
//        sql.append("    FROM WK_POS_A_ITEM ");
//        sql.append("    WHERE ");
//        sql.append("        ERR_KB  = '0' AND ");
//        sql.append("        COMMAND = '0044' ");
//        sql.append("    GROUP BY ");
//        sql.append("        EIGYO_DT ");
//        sql.append("       ,STORE ");
//        sql.append("       ,POS ");
//        sql.append("       ,TRANS_NO ");
//        sql.append("    ) ");
//        sql.append("GROUP BY ");
//        sql.append("    EIGYO_DT ");
//        sql.append("   ,STORE ");
        sql.append(" VALUES (   ");
        sql.append("        WPAI.KEIJO_DT");
        sql.append("      , WPAI.TENPO_CD");
        sql.append("      , WPAI.CHECKER_CD");
        sql.append("      , WPAI.HENPIN_RECEIPT_QT");
        sql.append("      , WPAI.HENPIN_RECEIPT_VL");
        sql.append("      , WPAI.INSERT_USER_ID");
        sql.append("      , WPAI.INSERT_TS");
        sql.append("      , WPAI.UPDATE_USER_ID");
        sql.append("      , WPAI.UPDATE_TS");
        sql.append("  )   ");
        //#5399 Mod X.Liu 2017.06.14 (E)

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    /**
     * アウトプットBeanを取得する
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }
}
