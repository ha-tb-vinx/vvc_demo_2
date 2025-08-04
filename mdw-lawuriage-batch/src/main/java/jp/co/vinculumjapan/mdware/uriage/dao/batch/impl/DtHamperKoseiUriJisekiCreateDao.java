package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: DtHamperKoseiUriJisekiCreateDaoクラス</p>
 * <p>説明　: ハンパー構成商品売上実績データ作成（卸）</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2017.08.08)  VINX X.Liu #5752
 * @Version 1.01 (2017.09.19)  VINX X.Liu #5943
 */
public class DtHamperKoseiUriJisekiCreateDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String DAO_NAME = "ハンパー構成商品売上実績データ作成（卸）";
    private static final String DT_HAMPER_KOUSEI_URIAGE_JISEKI_NAME = "ハンパー構成商品売上実績データ";
    private static final String BATCH_ID = "URIB291020";
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExInsert = null;

        int insertCount = 0;

        try {
            // ハンパー構成商品売上実績データ作成の追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_HAMPER_KOUSEI_URIAGE_JISEKI_NAME + "の追加を開始します。");

            preparedStatementExInsert = invoker.getDataBase().prepareStatement(getHamperKouseiSyohinUriageJisekiInsertSql());
            int i = 1;
            preparedStatementExInsert.setString(i++, BATCH_ID);
            preparedStatementExInsert.setString(i++, DBSERVER_DT);
            preparedStatementExInsert.setString(i++, BATCH_ID);
            preparedStatementExInsert.setString(i++, DBSERVER_DT);
            insertCount = preparedStatementExInsert.executeUpdate();
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_HAMPER_KOUSEI_URIAGE_JISEKI_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_HAMPER_KOUSEI_URIAGE_JISEKI_NAME + "の追加を終了します。");

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
     * ハンパー構成商品売上実績データ作成用SQLを取得する
     *
     * @return ハンパー構成商品売上実績データ作成用SQL
     */
    private String getHamperKouseiSyohinUriageJisekiInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("MERGE ");
        sql.append("INTO DT_HAMPER_KOSEI_URI_JISEKI DHKUJ ");
        sql.append("  USING ( ");
        sql.append("    SELECT  ");
        sql.append("      HS.HAMPER_SYOHIN_CD AS HAMPER_SYOHIN_CD   ");
        sql.append("      , KS.HAMPER_KO_SYOHIN_CD AS HAMPER_KOSEI_SYOHIN_CD    ");
        sql.append("      , HS.KEIJO_DT AS URIAGE_DT    ");
        sql.append("      , HS.TENPO_CD AS TENPO_CD ");
        sql.append("      , HS.OROSHI_BAITANKA_ZEINUKI_VL AS HAMPER_BAITANKA_VL ");
        sql.append("      , KS.OROSHI_BAITANKA_ZEINUKI_VL AS HAMPER_KOSEI_BAITANKA_VL   ");
        sql.append("      , MIN(KS.HAMPER_KO_SYOHIN_NA) AS HAMPER_KOSEI_SYOHIN_NM   ");
        sql.append("      , MIN(KS.BUNRUI1_CD) AS HAMPER_KOSEI_DPT  ");
        sql.append("      , MIN(KS.HANBAI_TANI_NA) AS HAMPER_KOSEI_HANBAI_TANI  ");
        sql.append("      , CASE    ");
        sql.append("        WHEN MIN(KS.TEIKAN_KB) = '1'    ");
        sql.append("        THEN SUM(KS.NOUHIN_SURYO_QT)    ");
        sql.append("        ELSE SUM(KS.NOUHIN_JYURYO_QT * 1000)    ");
        sql.append("        END AS HAMPER_KOSEI_URIAGE_QT   ");
        //#5943 Mod X.Liu 2017.09.19 (S)
//        sql.append("      , SUM(KS.BAIKA_ZEIKOMI_VL) AS HAMPER_KOSEI_URIAGE_VL  ");
        sql.append("      , SUM(KS.BAIKA_ZEINUKI_VL) AS HAMPER_KOSEI_URIAGE_VL  ");
        //#5943 Mod X.Liu 2017.09.19 (E)
        sql.append("      , ? AS INSERT_USER_ID ");
        sql.append("      , ? AS INSERT_TS  ");
        sql.append("      , ? AS UPDATE_USER_ID ");
        sql.append("      , ? AS UPDATE_TS  ");
        sql.append("      , MIN(KS.TEIKAN_KB) AS TEIKAN_KB  ");
        //#5943 Mod X.Liu 2017.09.19 (S)
//        sql.append("      , SUM(KS.BAIKA_ZEIKOMI_VL) / (    ");
//        sql.append("        CASE    ");
//        sql.append("          WHEN MIN(KS.TEIKAN_KB) = '1'  ");
//        sql.append("          THEN SUM(KS.NOUHIN_SURYO_QT)  ");
//        sql.append("          ELSE SUM(KS.NOUHIN_JYURYO_QT * 1000)  ");
//        sql.append("          END   ");
//        sql.append("      ) AS HAMPER_KOSEI_NEBIKI_BAITANKA     ");
        sql.append("      , CASE ");
        sql.append("        WHEN MIN(KS.TEIKAN_KB) = '1' ");
        sql.append("        AND SUM(KS.NOUHIN_SURYO_QT) = 0 ");
        sql.append("        THEN 0 ");
        sql.append("        WHEN (MIN(KS.TEIKAN_KB) != '1' OR MIN(KS.TEIKAN_KB) IS NULL)");
        sql.append("        AND SUM(KS.NOUHIN_JYURYO_QT * 1000) = 0 ");
        sql.append("        THEN 0 ");
        sql.append("        ELSE SUM(KS.BAIKA_ZEINUKI_VL) / ( ");
        sql.append("          CASE ");
        sql.append("            WHEN MIN(KS.TEIKAN_KB) = '1' ");
        sql.append("            THEN SUM(KS.NOUHIN_SURYO_QT) ");
        sql.append("            ELSE SUM(KS.NOUHIN_JYURYO_QT * 1000) ");
        sql.append("            END");
        sql.append("        ) ");
        sql.append("        END AS HAMPER_KOSEI_NEBIKI_BAITANKA ");
        //#5943 Mod X.Liu 2017.09.19 (E)
        sql.append("    FROM    ");
        sql.append("      WK_OROSHI_SYUKA_HAM_KO_SYO KS     ");
        sql.append("      INNER JOIN WK_OROSHI_SYUKA_HAM_SYO HS  ");
        sql.append("        ON KS.COMP_CD = HS.COMP_CD  ");
        sql.append("        AND KS.SEQ_NB = HS.SEQ_NB   ");
        sql.append("        AND KS.SEQ_EDA_RB = HS.SEQ_EDA_RB   ");
        sql.append("    GROUP BY    ");
        sql.append("      HS.HAMPER_SYOHIN_CD   ");
        sql.append("      , KS.HAMPER_KO_SYOHIN_CD  ");
        sql.append("      , HS.KEIJO_DT ");
        sql.append("      , HS.TENPO_CD ");
        sql.append("      , HS.OROSHI_BAITANKA_ZEINUKI_VL   ");
        sql.append("      , KS.OROSHI_BAITANKA_ZEINUKI_VL   ");
        sql.append("  ) WPHH ");
        sql.append("    ON ( ");
        sql.append("      DHKUJ.HAMPER_SYOHIN_CD = WPHH.HAMPER_SYOHIN_CD ");
        sql.append("      AND DHKUJ.HAMPER_KOSEI_SYOHIN_CD = WPHH.HAMPER_KOSEI_SYOHIN_CD ");
        sql.append("      AND DHKUJ.URIAGE_DT = WPHH.URIAGE_DT ");
        sql.append("      AND DHKUJ.TENPO_CD = WPHH.TENPO_CD ");
        sql.append("      AND DHKUJ.HAMPER_BAITANKA_VL = WPHH.HAMPER_BAITANKA_VL ");
        sql.append("      AND DHKUJ.HAMPER_KOSEI_BAITANKA_VL = WPHH.HAMPER_KOSEI_BAITANKA_VL");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  HAMPER_KOSEI_URIAGE_QT = NVL(DHKUJ.HAMPER_KOSEI_URIAGE_QT, 0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_QT, 0)");
        sql.append("  , HAMPER_KOSEI_URIAGE_VL = NVL(DHKUJ.HAMPER_KOSEI_URIAGE_VL, 0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_VL, 0)");
        //#5943 Mod X.Liu 2017.09.19 (S)
//        sql.append("  , HAMPER_KOSEI_NEBIKI_BAITANKA = ( ");
//        sql.append("    NVL(DHKUJ.HAMPER_KOSEI_URIAGE_VL, 0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_VL, 0)");
//        sql.append("  ) / ( ");
//        sql.append("    NVL(DHKUJ.HAMPER_KOSEI_URIAGE_QT, 0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_QT, 0)");
//        sql.append("  ) ");
        sql.append("  , HAMPER_KOSEI_NEBIKI_BAITANKA = CASE ");
        sql.append("    WHEN NVL(DHKUJ.HAMPER_KOSEI_URIAGE_QT, 0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_QT, 0) = 0 ");
        sql.append("    THEN 0 ");
        sql.append("    ELSE ( ");
        sql.append("      NVL(DHKUJ.HAMPER_KOSEI_URIAGE_VL, 0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_VL, 0)");
        sql.append("    ) / ( ");
        sql.append("      NVL(DHKUJ.HAMPER_KOSEI_URIAGE_QT, 0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_QT, 0)");
        sql.append("    ) ");
        sql.append("    END");
        //#5943 Mod X.Liu 2017.09.19 (E)
        sql.append("  , UPDATE_USER_ID = WPHH.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WPHH.UPDATE_TS WHEN NOT MATCHED THEN ");
        sql.append("INSERT ( ");
        sql.append("  HAMPER_SYOHIN_CD");
        sql.append("  , HAMPER_KOSEI_SYOHIN_CD");
        sql.append("  , URIAGE_DT");
        sql.append("  , TENPO_CD");
        sql.append("  , HAMPER_BAITANKA_VL");
        sql.append("  , HAMPER_KOSEI_BAITANKA_VL");
        sql.append("  , HAMPER_KOSEI_SYOHIN_NM");
        sql.append("  , HAMPER_KOSEI_DPT");
        sql.append("  , HAMPER_KOSEI_HANBAI_TANI");
        sql.append("  , HAMPER_KOSEI_URIAGE_QT");
        sql.append("  , HAMPER_KOSEI_URIAGE_VL");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS");
        sql.append("  , TEIKAN_KB");
        sql.append("  , HAMPER_KOSEI_NEBIKI_BAITANKA");
        sql.append(") ");
        sql.append("VALUES ( ");
        sql.append("  WPHH.HAMPER_SYOHIN_CD");
        sql.append("  , WPHH.HAMPER_KOSEI_SYOHIN_CD");
        sql.append("  , WPHH.URIAGE_DT");
        sql.append("  , WPHH.TENPO_CD");
        sql.append("  , WPHH.HAMPER_BAITANKA_VL");
        sql.append("  , WPHH.HAMPER_KOSEI_BAITANKA_VL");
        sql.append("  , WPHH.HAMPER_KOSEI_SYOHIN_NM");
        sql.append("  , WPHH.HAMPER_KOSEI_DPT");
        sql.append("  , WPHH.HAMPER_KOSEI_HANBAI_TANI");
        sql.append("  , WPHH.HAMPER_KOSEI_URIAGE_QT");
        sql.append("  , WPHH.HAMPER_KOSEI_URIAGE_VL");
        sql.append("  , WPHH.INSERT_USER_ID");
        sql.append("  , WPHH.INSERT_TS");
        sql.append("  , WPHH.UPDATE_USER_ID");
        sql.append("  , WPHH.UPDATE_TS");
        sql.append("  , WPHH.TEIKAN_KB");
        //#5943 Mod X.Liu 2017.09.19 (S)
//        sql.append("  , WPHH.HAMPER_KOSEI_URIAGE_VL / WPHH.HAMPER_KOSEI_URIAGE_QT");
        sql.append("  , WPHH.HAMPER_KOSEI_NEBIKI_BAITANKA");
        //#5943 Mod X.Liu 2017.09.19 (E)
        sql.append(") ");


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
