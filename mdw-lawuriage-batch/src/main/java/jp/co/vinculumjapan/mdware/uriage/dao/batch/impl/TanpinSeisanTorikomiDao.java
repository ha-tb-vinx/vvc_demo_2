package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 * <p>
 * タイトル: TanpinSeisanTorikomiDao クラス
 * </p>
 * <p>
 * 説明　: POS実績取込処理(単品精算)
 * </p>
 * <p>
 * 著作権: Copyright (c) 2013
 * </p>
 * <p>
 * 会社名: VINX
 * </p>
 *
 * @Version 3.00 (2013.09.13) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * @Version 3.02 (2016.05.10) VINX S.Kashihara FIVI対応
 * @Version 3.03 (2016.05.10) VINX k.Hyo FIVI対応 店舗コード4桁→6桁修正
 * @Version 3.03 (2016.08.04) VINX k.Hyo FIVI対応 #1879対応
 * @Version 3.03 (2016.09.15) VINX Y.Itaki FIVI対応 #2009対応
 * @Version 3.03 (2016.10.04) VINX k.Hyo FIVI対応 #2196対応
 * @Version 3.03 (2016.12.01) VINX T.Kamei FIVI対応 #3088対応
 * @Version 3.04 (2017/01/16) J.Endo  FIVI対応(#3502)
 * @Version 3.05 (2017/02/21) N.Katou  FIVI対応(#4022)
 * @Version 3.06 (2017/04/05) X.Liu  FIVI対応(#4526)
 * @Version 3.07 (2017/08/31) N.Katou #5840
 * @Version 3.08 (2023/07/03) TUNG.LT #16388 MKH対応
 */
public class TanpinSeisanTorikomiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理(単品精算)";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMP単品精算データ";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE TMP_TANPIN_SEISAN";

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
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        int insertCount = 0;

        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkTanpinSeisanInsertSql());

            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, userId);
            preparedStatementExIns.setString(3, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(4, userId);
            preparedStatementExIns.setString(5, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(6, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }

            } catch (Exception e2) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

    }

    /**
     * 単品精算ワーク登録用SQLを取得する
     *
     * @return 単品精算ワーク登録用SQL
     */
    private String getWkTanpinSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO TMP_TANPIN_SEISAN( ");
        sql.append("    COMP_CD, ");
        sql.append("    EIGYO_DT, ");
        sql.append("    TENPO_CD, ");
        sql.append("    TANPIN_SHIKIBETSU_CD, ");
        sql.append("    TANPIN_CD, ");
        sql.append("    URIAGE_SOURI_QT, ");
        sql.append("    URIAGE_SOURI_VL, ");
        sql.append("    URIAGE_HITEIBAN_QT, ");
        sql.append("    URIAGE_HITEIBAN_VL, ");
        sql.append("    MM_NEBIKI_QT, ");
        sql.append("    MM_NEBIKI_VL, ");
        sql.append("    LOS_QT, ");
        sql.append("    LOS_VL, ");
        sql.append("    NEBIKI_REGI_QT, ");
        sql.append("    NEBIKI_REGI_VL, ");
        sql.append("    NEBIKI_SC_QT, ");
        sql.append("    NEBIKI_SC_VL, ");
        sql.append("    HAIKI_QT, ");
        sql.append("    HAIKI_VL, ");
        sql.append("    GAISAN_ARARI_SOURI_VL, ");
        sql.append("    GAISAN_ARARI_HITEIBAN_VL, ");
        sql.append("    TEBAN_TANKA_VL, ");
        sql.append("    TOKUBAI_KIKAKU_NO, ");
        sql.append("    SAISYU_URIAGE_TM, ");
        sql.append("    INSERT_USER_ID, ");
        sql.append("    INSERT_TS, ");
        sql.append("    UPDATE_USER_ID, ");
        sql.append("    UPDATE_TS, ");
        // 2016/08/08 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sql.append("    HANBAI_WEIGHT_QT) "); // 販売重量
        sql.append("    HANBAI_WEIGHT_QT, "); // 販売重量
        sql.append("    URIAGE_NUKI_SOURI_VL, ");
        sql.append("    URIAGE_ZEI_VL, ");
        sql.append("    URIAGE_KB, ");
        sql.append("    URIAGE_HENPIN_KB) ");
        // 2016/08/08 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("SELECT ");
        sql.append("    ?, ");
        sql.append("    SUBSTR(MAIN.EIGYO_DT,1,4) || '/' || SUBSTR(MAIN.EIGYO_DT,5,2) || '/' || SUBSTR(MAIN.EIGYO_DT,7,2), ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    LPAD(MAIN.STORE,6,'0'), ");
        //sql.append("    STORE, ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("    null, ");
        // 2016/10/05 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sql.append("    SKU, ");
        sql.append("    MAIN.SKU, ");
        // 2016/10/05 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("    SUM(MAIN.QTY), ");
        sql.append("    ROUND(SUM(REPLACE(MAIN.ACTUAL_SELL, ',', '.')), 2), ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    null, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        // 2016/08/08 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sql.append("    SUM(WEIGHT_SOLD) "); // 販売重量 (集計値)
        //2017.01.16 J.Endo FIVI対応(#3502) MOD(S)
        //sql.append("    SUM(MAIN.WEIGHT_SOLD), "); // 販売重量 (集計値)
        sql.append("    LPAD(TO_CHAR(SUM(NVL(MAIN.WEIGHT_SOLD,0)) * 1000,'FM00000000'),9,'0'), "); // 販売重量 (集計値)
        //2017.01.16 J.Endo FIVI対応(#3502) MOD(E)
        sql.append("    SUM(MAIN.ACTUAL_SELL_WOT), ");
        sql.append("    ROUND(SUM(REPLACE(MAIN.ACTUAL_SELL, ',', '.')), 2)-ROUND(SUM(REPLACE(MAIN.ACTUAL_SELL_WOT, ',', '.')), 2), ");
        // 2016/09/15 VINX Y.Itaki S_MDware-G_FIVIマート様開発（S)
        //sql.append("    CASE WHEN ATYPE = '0001' THEN '1' WHEN ATYPE = '0301' THEN '1' END, ");
        //sql.append("    CASE WHEN ATYPE = '0001' THEN '1' WHEN ATYPE = '0301' THEN '2' END ");
        sql.append("    CASE ");
        // #4022 URIB012010 2017/2/21 N.katou(S)
//        sql.append("      WHEN MAIN.ATYPE = '0001' THEN '1' ");
//        sql.append("      WHEN MAIN.ATYPE = '0301' THEN '1' ");
        // 2016/12/01 VINX T.Kamei S_MDware-G_FIVIマート様開発（S)
        //sql.append("      WHEN MAIN.ATYPE = '1001' THEN '2' ");
        //sql.append("      WHEN MAIN.ATYPE = '1301' THEN '2' ");
//        sql.append("      WHEN MAIN.ATYPE = '1011' THEN '2' ");
//        sql.append("      WHEN MAIN.ATYPE = '1311' THEN '2' ");
        sql.append("      WHEN MAIN.URIAGE_FRG = '1' THEN '1' ");
        sql.append("      WHEN MAIN.URIAGE_FRG = '2' THEN '1' ");
        sql.append("      WHEN MAIN.URIAGE_FRG = '3' THEN '2' ");
        sql.append("      WHEN MAIN.URIAGE_FRG = '4' THEN '2' ");
        // 2016/12/01 VINX T.Kamei S_MDware-G_FIVIマート様開発（E)
        // #4022 URIB012010 2017/2/21 N.katou(E)
        sql.append("    END, ");
        sql.append("    CASE ");
        // #4022 URIB012010 2017/2/21 N.katou(S)
//        sql.append("      WHEN MAIN.ATYPE = '0001' THEN '1' ");
//        sql.append("      WHEN MAIN.ATYPE = '0301' THEN '2' ");
        // 2016/12/01 VINX T.Kamei S_MDware-G_FIVIマート様開発（S)
        //sql.append("      WHEN MAIN.ATYPE = '1001' THEN '1' ");
        //sql.append("      WHEN MAIN.ATYPE = '1301' THEN '2' ");
//        sql.append("      WHEN MAIN.ATYPE = '1011' THEN '1' ");
//        sql.append("      WHEN MAIN.ATYPE = '1311' THEN '2' ");
        // 2016/12/01 VINX T.Kamei S_MDware-G_FIVIマート様開発（E)
        sql.append("      WHEN MAIN.URIAGE_FRG = '1' THEN '1' ");
        sql.append("      WHEN MAIN.URIAGE_FRG = '2' THEN '2' ");
        sql.append("      WHEN MAIN.URIAGE_FRG = '3' THEN '1' ");
        sql.append("      WHEN MAIN.URIAGE_FRG = '4' THEN '2' ");
        // #4022 URIB012010 2017/2/21 N.katou(E)
        sql.append("    END ");
        // 2016/09/15 VINX Y.Itaki S_MDware-G_FIVIマート様開発（E)
        // 2016/08/08 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        // #4022 URIB012010 2017/2/21 N.katou(S)
        sql.append("FROM ");
        sql.append("( "); 
        sql.append("SELECT ");
        sql.append("    SUB.COMMAND AS COMMAND ");
        sql.append("   ,SUB.STORE AS STORE ");
        sql.append("   ,SUB.POS AS POS ");
        sql.append("   ,SUB.TRANS_NO AS TRANS_NO ");
        sql.append("   ,SUB.EIGYO_DT AS EIGYO_DT ");
        sql.append("   ,SUB.ATYPE  AS ATYPE ");
        sql.append("   ,SUB.SKU AS SKU ");
        sql.append("   ,SUM(SUB.QTY) AS QTY ");
        sql.append("   ,SUM(SUB.ACTUAL_SELL) AS ACTUAL_SELL ");
        sql.append("   ,SUM(SUB.WEIGHT_SOLD)  AS WEIGHT_SOLD ");
        sql.append("   ,SUM(SUB.ACTUAL_SELL_WOT) AS ACTUAL_SELL_WOT ");
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("   ,CASE ");
        // sql.append("      WHEN ( ");
        // sql.append("        SELECT ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            COUNT(TPAI.ATYPE) ");
        // sql.append("            COUNT(WPAI.ATYPE) ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        FROM ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            TMP_POS_A_ITEM TPAI ");
        // sql.append("            WK_POS_A_ITEM WPAI ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        WHERE ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            TPAI.STORE = SUB.STORE AND ");
//      //   sql.append("            TPAI.POS = SUB.POS AND ");
//      //   sql.append("            TPAI.TRANS_NO = SUB.TRANS_NO AND ");
//      //   sql.append("            TPAI.EIGYO_DT = SUB.EIGYO_DT AND");
//      //   //#4526 Mod X.Liu 2017.04.05 (S)
////    //     sql.append("            TPAI.COMMAND = '0044' AND ");
//      //   sql.append("            (TPAI.COMMAND = '0044' OR TPAI.COMMAND = '0045') AND ");
//      //   //#4526 Mod X.Liu 2017.04.05 (E)
//      //   sql.append("            TPAI.ATYPE = '0301' ");
//      //   sql.append("        GROUP BY TPAI.ATYPE ");
        // sql.append("            WPAI.STORE = SUB.STORE AND ");
        // sql.append("            WPAI.POS = SUB.POS AND ");
        // sql.append("            WPAI.TRANS_NO = SUB.TRANS_NO AND ");
        // sql.append("            WPAI.EIGYO_DT = SUB.EIGYO_DT AND");
        // sql.append("            (WPAI.COMMAND = '0044' OR WPAI.COMMAND = '0045') AND ");
        // sql.append("            WPAI.ATYPE = '0301' AND ");
        // sql.append("            WPAI.ERR_KB = '0' ");
        // sql.append("        GROUP BY WPAI.ATYPE ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      ) > 0 ");
        // sql.append("      AND ( ");
        // sql.append("      SELECT ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("        COUNT(TPAI.ATYPE) ");
        // sql.append("        COUNT(WPAI.ATYPE) ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      FROM( ");
        // sql.append("        SELECT ");
        // sql.append("            ATYPE ");
        // sql.append("           ,STORE ");
        // sql.append("           ,POS ");
        // sql.append("           ,TRANS_NO ");
        // sql.append("           ,EIGYO_DT ");
      //// #4526 Add X.Liu 2017.04.05 (S)
        // sql.append("           ,COMMAND ");
      //// #4526 Add X.Liu 2017.04.05 (E)
        // sql.append("        FROM ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            TMP_POS_A_ITEM TPAI ");
        // sql.append("            WK_POS_A_ITEM WPAI ");
        // sql.append("        WHERE ");
        // sql.append("            ERR_KB = '0' ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        GROUP BY ");
        // sql.append("            STORE ");
        // sql.append("           ,POS ");
        // sql.append("           ,TRANS_NO ");
        // sql.append("           ,ATYPE ");
        // sql.append("           ,EIGYO_DT ");
        // sql.append("           ,COMMAND ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("      ) TPAI ");
        // sql.append("      ) WPAI ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      WHERE ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("          TPAI.STORE = SUB.STORE AND "); 
//      //   sql.append("          TPAI.POS = SUB.POS AND ");
//      //   sql.append("          TPAI.TRANS_NO = SUB.TRANS_NO AND "); 
//      //   sql.append("          TPAI.EIGYO_DT = SUB.EIGYO_DT ");
//      //   //#4526 Mod X.Liu 2017.04.05 (S)
//      //   sql.append("          AND (( TPAI.COMMAND<>'0044' AND TPAI.COMMAND<>'0045' )");
//      //   sql.append("          OR TPAI.ATYPE<>'0301') ");
        // sql.append("          WPAI.STORE = SUB.STORE AND "); 
        // sql.append("          WPAI.POS = SUB.POS AND ");
        // sql.append("          WPAI.TRANS_NO = SUB.TRANS_NO AND "); 
        // sql.append("          WPAI.EIGYO_DT = SUB.EIGYO_DT AND ");
        // sql.append("          (( WPAI.COMMAND<>'0044' AND WPAI.COMMAND<>'0045' ) OR ");
        // sql.append("          WPAI.ATYPE<>'0301') ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        ) =0 THEN '2' ");
//      //   sql.append("      ) = 1 THEN '2' ");
        // //#4526 Mod X.Liu 2017.04.05 (E)
        // sql.append("      WHEN ( ");
        // sql.append("        SELECT ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            COUNT(TPAI.ATYPE) ");
        // sql.append("            COUNT(WPAI.ATYPE) ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        FROM ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            TMP_POS_A_ITEM TPAI ");
        // sql.append("            WK_POS_A_ITEM WPAI ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        WHERE ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            TPAI.STORE = SUB.STORE AND ");
//      //   sql.append("            TPAI.POS = SUB.POS AND ");
//      //   sql.append("            TPAI.TRANS_NO = SUB.TRANS_NO AND ");
//      //   sql.append("            TPAI.EIGYO_DT = SUB.EIGYO_DT AND");
//      //   sql.append("            TPAI.COMMAND = '0043' AND ");
//      //   sql.append("            TPAI.ATYPE = '1011' ");
        // sql.append("            WPAI.STORE = SUB.STORE AND ");
        // sql.append("            WPAI.POS = SUB.POS AND ");
        // sql.append("            WPAI.TRANS_NO = SUB.TRANS_NO AND ");
        // sql.append("            WPAI.EIGYO_DT = SUB.EIGYO_DT AND");
        // sql.append("            WPAI.COMMAND = '0043' AND ");
        // sql.append("            WPAI.ATYPE = '1011' AND ");
        // sql.append("            WPAI.ERR_KB = '0' ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      ) > 0 THEN '3' ");
        // sql.append("      WHEN ( ");
        // sql.append("        SELECT ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            COUNT(TPAI.ATYPE) ");
        // sql.append("            COUNT(WPAI.ATYPE) ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        FROM ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            TMP_POS_A_ITEM TPAI ");
        // sql.append("            WK_POS_A_ITEM WPAI ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        WHERE ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            TPAI.STORE = SUB.STORE AND ");
//      //   sql.append("            TPAI.POS = SUB.POS AND ");
//      //   sql.append("            TPAI.TRANS_NO = SUB.TRANS_NO AND ");
//      //   sql.append("            TPAI.EIGYO_DT = SUB.EIGYO_DT AND ");
//      //   //#4526 Mod X.Liu 2017.04.05 (S)
////    //     sql.append("            TPAI.COMMAND = '0044' AND ");
//      //   sql.append("            (TPAI.COMMAND = '0044' OR TPAI.COMMAND = '0045') AND ");
//      //   //#4526 Mod X.Liu 2017.04.05 (E)
//      //   sql.append("            TPAI.ATYPE = '1311' ");
        // sql.append("            WPAI.STORE = SUB.STORE AND ");
        // sql.append("            WPAI.POS = SUB.POS AND ");
        // sql.append("            WPAI.TRANS_NO = SUB.TRANS_NO AND ");
        // sql.append("            WPAI.EIGYO_DT = SUB.EIGYO_DT AND ");
        // sql.append("            (WPAI.COMMAND = '0044' OR WPAI.COMMAND = '0045') AND ");
        // sql.append("            WPAI.ATYPE = '1311' AND ");
        // sql.append("            WPAI.ERR_KB = '0' ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("        GROUP BY ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("            TPAI.ATYPE ");
        // sql.append("            WPAI.ATYPE ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      ) > 0 ");
        // sql.append("      AND ");
        // sql.append("      ( ");
        // sql.append("      SELECT ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("          COUNT(TPAI.ATYPE) ");
        // sql.append("          COUNT(WPAI.ATYPE) ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      FROM( ");
        // sql.append("        SELECT ");
        // sql.append("            ATYPE ");
        // sql.append("           ,STORE ");
        // sql.append("           ,POS ");
        // sql.append("           ,TRANS_NO ");
        // sql.append("           ,EIGYO_DT ");
        // //#4526 Add X.Liu 2017.04.05 (S)
        // sql.append("           ,COMMAND ");
        // //#4526 Add X.Liu 2017.04.05 (E)
        // sql.append("      FROM ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("        TMP_POS_A_ITEM TPAI ");
        // sql.append("        WK_POS_A_ITEM WPAI ");
        // sql.append("      WHERE ");
        // sql.append("          WPAI.ERR_KB = '0' ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      GROUP BY ");
        // sql.append("          STORE ");
        // sql.append("          ,POS ");
        // sql.append("          ,TRANS_NO ");
        // sql.append("          ,ATYPE ");
        // sql.append("          ,EIGYO_DT ");
        // sql.append("          ,COMMAND ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("      ) TPAI ");
        // sql.append("      ) WPAI ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      WHERE ");
        // // 2017/08/31 VINX N.Katou #5840 (S)
//      //   sql.append("          TPAI.STORE = SUB.STORE AND "); 
//      //   sql.append("          TPAI.POS = SUB.POS AND ");
//      //   sql.append("          TPAI.TRANS_NO = SUB.TRANS_NO AND "); 
//      //   sql.append("          TPAI.EIGYO_DT = SUB.EIGYO_DT ");
//      //   //#4526 Mod X.Liu 2017.04.05 (S)
//      //   sql.append("          AND ((TPAI.COMMAND<>'0044' AND TPAI.COMMAND<>'0045') ");
//      //   sql.append("          OR TPAI.ATYPE<>'1311') ");
        // sql.append("          WPAI.STORE = SUB.STORE AND "); 
        // sql.append("          WPAI.POS = SUB.POS AND ");
        // sql.append("          WPAI.TRANS_NO = SUB.TRANS_NO AND "); 
        // sql.append("          WPAI.EIGYO_DT = SUB.EIGYO_DT AND ");
        // sql.append("          ((WPAI.COMMAND<>'0044' AND WPAI.COMMAND<>'0045') OR ");
        // sql.append("          WPAI.ATYPE<>'1311') ");
        // // 2017/08/31 VINX N.Katou #5840 (E)
        // sql.append("      ) = 0 THEN '4' ");
//        sql.append("      ) = 1 THEN '4' ");
        //#4526 Mod X.Liu 2017.04.05 (E)
        // sql.append("      ELSE '1' ");
        // sql.append("      END AS URIAGE_FRG ");
        sql.append("      ,TMP_URIAGE.URIAGE_FRG AS URIAGE_FRG ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        sql.append("FROM");
        // #4022 URIB012010 2017/2/21 N.katou(E)
        // 2016/10/05 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("( ");
        sql.append("SELECT  ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("    TPAI.EIGYO_DT ");
//        sql.append("    ,TPAI.STORE ");
//        sql.append("    ,NVL(OLD_RS.SYOHIN_CD,TPAI.SKU) AS SKU ");
//        sql.append("    ,TPAI.QTY ");
//        sql.append("    ,TPAI.ACTUAL_SELL ");
//        sql.append("    ,TPAI.WEIGHT_SOLD ");
//        sql.append("    ,TPAI.ACTUAL_SELL_WOT ");
//        sql.append("    ,TPAI.ATYPE ");
//        // #4022 URIB012010 2017/2/21 N.katou(S)
//        sql.append("    ,TPAI.COMMAND AS COMMAND ");
//        sql.append("    ,TPAI.POS AS POS ");
//        sql.append("    ,TPAI.TRANS_NO AS TRANS_NO ");
        sql.append("     WPAI.EIGYO_DT ");
        sql.append("    ,WPAI.STORE ");
        sql.append("    ,NVL(OLD_RS.SYOHIN_CD,WPAI.SKU) AS SKU ");
        sql.append("    ,WPAI.QTY ");
        sql.append("    ,WPAI.ACTUAL_SELL ");
        sql.append("    ,WPAI.WEIGHT_SOLD ");
        sql.append("    ,WPAI.ACTUAL_SELL_WOT ");
        sql.append("    ,WPAI.ATYPE ");
        sql.append("    ,WPAI.COMMAND AS COMMAND ");
        sql.append("    ,WPAI.POS AS POS ");
        sql.append("    ,WPAI.TRANS_NO AS TRANS_NO ");
         // #4022 URIB012010 2017/2/21 N.katou(E)
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("FROM ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("    TMP_POS_A_ITEM TPAI ");
        sql.append("    WK_POS_A_ITEM WPAI ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("LEFT OUTER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            OLD_SUB_RS_1.SYOHIN_CD AS SYOHIN_CD, ");
        sql.append("            OLD_SUB_RS_1.OLD_SYOHIN_CD AS OLD_SYOHIN_CD ");
        sql.append("        FROM ");
        sql.append("            R_SYOHIN OLD_SUB_RS_1 ");
        sql.append("        INNER JOIN ");
        sql.append("            ( ");
        sql.append("                SELECT ");
        sql.append("                    SYOHIN_CD, ");
        sql.append("                    MAX(YUKO_DT) YUKO_DT ");
        sql.append("                FROM ");
        sql.append("                    R_SYOHIN ");
        sql.append("                WHERE ");
        sql.append("                    YUKO_DT <= ? "); // バッチ日付
        sql.append("                GROUP BY ");
        sql.append("                    SYOHIN_CD ");
        sql.append("            ) OLD_SUB_RS_2 ");
        sql.append("        ON ");
        sql.append("            OLD_SUB_RS_1.SYOHIN_CD = OLD_SUB_RS_2.SYOHIN_CD AND ");
        sql.append("            OLD_SUB_RS_1.YUKO_DT = OLD_SUB_RS_2.YUKO_DT ");
        sql.append("        GROUP BY ");
        sql.append("            OLD_SUB_RS_1.SYOHIN_CD, ");
        sql.append("            OLD_SUB_RS_1.OLD_SYOHIN_CD ");
        sql.append("    ) OLD_RS ");
        sql.append("ON ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("    LPAD(TRIM(TPAI.SKU),13,'0') = LPAD(TRIM(OLD_RS.OLD_SYOHIN_CD),13,'0') ");
        sql.append("    LPAD(TRIM(WPAI.SKU),13,'0') = LPAD(TRIM(OLD_RS.OLD_SYOHIN_CD),13,'0') ");
        sql.append("WHERE ");
        sql.append("    WPAI.ERR_KB = '0' ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        // #4022 URIB012010 2017/2/21 N.katou(S)
//        sql.append("    ) MAIN ");
        sql.append("    ) SUB ");
        // #4022 URIB012010 2017/2/21 N.katou(E)
        // 2016/10/05 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("GROUP BY ");
        // sql.append("    EIGYO_DT, "); // 営業日
        // sql.append("    STORE, ");    // 店舗コード
        // //sql.append("    SKU ");       // 商品コード
        // // 2016/08/08 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        // //sql.append("    ATYPE ");       // 販売タイプ
        // // 2016/08/08 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        // // 2016/10/05 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        // sql.append("    ATYPE, ");
        // // #4022 URIB012010 2017/2/21 N.katou(S)
//      //   sql.append("    SKU ");
        // sql.append("    SKU, ");
        // // 2016/10/05 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        // sql.append("    COMMAND, ");
        // sql.append("    POS, ");
        // sql.append("    TRANS_NO ");
        sql.append("  LEFT JOIN ");
        sql.append("    (SELECT STORE, ");
        sql.append("      POS, ");
        sql.append("      TRANS_NO , ");
        sql.append("      EIGYO_DT , ");
        sql.append("      CASE ");
        sql.append("        WHEN SUM( DECODE(WPAI.ATYPE,'0301',1,0)) = COUNT(WPAI.ATYPE) ");
        sql.append("           AND (COMMAND                          = '0044' ");
        sql.append("           OR COMMAND                            = '0045') ");
        sql.append("        THEN '2' ");
        sql.append("        WHEN SUM( DECODE(WPAI.ATYPE,'1011',1,0)) > 0 ");
        sql.append("           AND COMMAND                           = '0043' ");
        sql.append("        THEN '3' ");
        sql.append("        WHEN SUM( DECODE(WPAI.ATYPE,'1311',1,0)) = COUNT(WPAI.ATYPE) ");
        sql.append("           AND (COMMAND                          = '0044' ");
        sql.append("           OR COMMAND                            = '0045') ");
        sql.append("        THEN '4' ");
        sql.append("        ELSE '1' ");
        sql.append("      END AS URIAGE_FRG ");
        sql.append("    FROM WK_POS_A_ITEM WPAI ");
        sql.append("    WHERE ERR_KB = '0' ");
        sql.append("    GROUP BY ");
        sql.append("      STORE ");
        sql.append("      ,POS ");
        sql.append("      ,TRANS_NO ");
        sql.append("      ,EIGYO_DT ");
        sql.append("      ,COMMAND ");
        sql.append("    ) TMP_URIAGE ");
        sql.append("  ON TMP_URIAGE.STORE     = SUB.STORE ");
        sql.append("  AND TMP_URIAGE.POS      = SUB.POS ");
        sql.append("  AND TMP_URIAGE.TRANS_NO = SUB.TRANS_NO ");
        sql.append("  AND TMP_URIAGE.EIGYO_DT = SUB.EIGYO_DT ");
        sql.append("  GROUP BY ");
        sql.append("    SUB.EIGYO_DT ");
        sql.append("    ,SUB.STORE ");
        sql.append("    ,SUB.ATYPE ");
        sql.append("    ,SUB.SKU ");
        sql.append("    ,SUB.COMMAND ");
        sql.append("    ,SUB.POS ");
        sql.append("    ,SUB.TRANS_NO ");
        sql.append("    ,TMP_URIAGE.URIAGE_FRG ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        sql.append("        )MAIN "); 
        sql.append("GROUP BY ");
        sql.append("    EIGYO_DT ");
        sql.append("    ,STORE ");
        sql.append("    ,SKU ");
        sql.append("    ,URIAGE_FRG ");
        // #4022 URIB012010 2017/2/21 N.katou(E)
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
