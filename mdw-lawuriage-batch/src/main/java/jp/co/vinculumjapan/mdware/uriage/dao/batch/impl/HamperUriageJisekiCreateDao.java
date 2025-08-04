package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: HamperUriageJisekiCreateDao.java クラス</p>
 * <p>説明　: ハンパー売上実績データ作成</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016/11/04) S_MDware-G_FIVIマート様開発 VINX J.Endo
 * @Version 1.01 (2017/01/12) FIVI対応(#3502) VINX J.Endo
 * @Version 1.02 (2017/01/23) FIVI対応(#3717) VINX J.Endo
 * @Version 1.03 (2017/01/27) FIVI対応(#3593) VINX J.Endo
 * @Version 1.04 (2017/02/22) FIVI対応(#4010) VINX J.Endo
 * @Version 1.05 (2017/03/23) FIVI対応(#4388) VINX N.Katou
 * @Version 1.06 (2017/04/05) FIVI対応(#4524) VINX X.Liu
 * @Version 1.07 (2017/05/18) FIVI対応(#5074) VINX J.Endo
 * @Version 1.08 (2017/06/14) FIVI対応(#5399) VINX X.Liu
 *
 */
public class HamperUriageJisekiCreateDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String DAO_NAME = "ハンパー売上実績データ作成";
    /** 登録先テーブル名称(ハンパー売上実績データ) */
    private static final String DT_HAMPER_URIAGE_JISEKI_NAME = "ハンパー売上実績データ";

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
        PreparedStatementEx preparedStatementExSelect = null;
        PreparedStatementEx preparedStatementExInsert = null;

        int insertCount = 0;
        ResultSet resultSet = null;

        try {
            // ハンパー売上実績データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_HAMPER_URIAGE_JISEKI_NAME + "の追加を開始します。");

            preparedStatementExSelect = invoker.getDataBase().prepareStatement(getTmpPosHHamperSelectSql());
            resultSet = preparedStatementExSelect.executeQuery();

            //2017.01.23 J.Endo FIVI対応(#3717) ADD(S)
            preparedStatementExInsert = invoker.getDataBase().prepareStatement(getHamperUriageJisekiInsertSql());
            //2017.01.23 J.Endo FIVI対応(#3717) ADD(E)

            // POSジャーナル（Hレコード）一時データのデータがなくなるまで繰り返す
            while (resultSet.next()) {
                int index = 1;
                //2017.01.23 J.Endo FIVI対応(#3717) DEL(S)
                //preparedStatementExInsert = invoker.getDataBase().prepareStatement(getHamperUriageJisekiInsertSql());
                //2017.01.23 J.Endo FIVI対応(#3717) DEL(E)

                // ハンパー商品売単価（税抜き）の税加算
                // #4524 Del X.Liu 2017.04.05 (S)
//                BigDecimal regularUnitSell = new BigDecimal(resultSet.getString("REGULAR_UNIT_SELL"));
//                BigDecimal taxRate = new BigDecimal(resultSet.getString("GST_TAX"));
//                CalculateTaxUtility calculateTaxUtility = new CalculateTaxUtility(regularUnitSell,
//                                                              "2",
//                                                              CalculateTaxUtility.INT_SOTOZEI,
//                                                              taxRate,
//                                                              CalculateTaxUtility.STR_MIMANSISYAGONYU);
                // #4524 Del X.Liu 2017.04.05 (E)
                preparedStatementExInsert.setString(index++, resultSet.getString("URIAGE_DT"));
                preparedStatementExInsert.setString(index++, resultSet.getString("TENPO_CD"));
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_SYOHIN_CD"));
                preparedStatementExInsert.setString(index++, resultSet.getString("TENPO_NA"));
                preparedStatementExInsert.setString(index++, resultSet.getString("SYOHIN_NA"));
                preparedStatementExInsert.setString(index++, resultSet.getString("URIAGE_QT"));
                preparedStatementExInsert.setString(index++, resultSet.getString("URIAGE_VL"));
                preparedStatementExInsert.setString(index++, userId);
                preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
                preparedStatementExInsert.setString(index++, userId);
                preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
                //# 4524 Mod X.Liu 2017.04.05 (S)
//                preparedStatementExInsert.setString(index++, calculateTaxUtility.getTaxIn().toString());
                preparedStatementExInsert.setString(index++, resultSet.getString("REGULAR_UNIT_SELL"));
                //# 4524 Mod X.Liu 2017.04.05 (E)
                //2017.01.12 J.Endo FIVI対応(#3502) ADD(S)
                preparedStatementExInsert.setString(index++, resultSet.getString("TEIKAN_KB"));
                //2017.01.12 J.Endo FIVI対応(#3502) ADD(E)
                //2017.02.22 J.Endo FIVI対応(#4010) ADD(S)
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_NEBIKIGO_BAITANKA"));
                //2017.02.22 J.Endo FIVI対応(#4010) ADD(E)

                insertCount = insertCount + preparedStatementExInsert.executeUpdate();
            }

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_HAMPER_URIAGE_JISEKI_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_HAMPER_URIAGE_JISEKI_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExSelect != null) {
                    preparedStatementExSelect.close();
                }
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
     * POSジャーナル（Hレコード）一時データ取得用SQLを取得する
     *
     * @return POSジャーナル（Hレコード）一時データ取得用SQL
     */
    private String getTmpPosHHamperSelectSql() {
        StringBuilder sql = new StringBuilder();

        //2017.01.27 J.Endo FIVI対応(#3593) MOD(S)
        //sql.append("SELECT ");
        //sql.append("    TPHH1.EIGYO_DT                AS URIAGE_DT ");
        //sql.append("   ,LPAD(TPHH1.STORE,6,'0')       AS TENPO_CD ");
        //sql.append("   ,TPHH1.SKU                     AS HAMPER_SYOHIN_CD ");
        //sql.append("   ,RT.KANJI_RN                   AS TENPO_NA ");
        //sql.append("   ,RS.HINMEI_KANJI_NA            AS SYOHIN_NA ");
        ////2017.01.12 J.Endo FIVI対応(#3502) ADD(S)
        //sql.append("   ,RS.TEIKAN_KB                  AS TEIKAN_KB ");
        ////2017.01.12 J.Endo FIVI対応(#3502) ADD(E)
        //sql.append("   ,CASE WHEN RS.TEIKAN_KB='1' ");
        //sql.append("        THEN TPHH1.QTY ");
        ////2017.01.12 J.Endo FIVI対応(#3502) MOD(S)
        ////sql.append("        ELSE TPHH1.WEIGHT_SOLD ");
        //sql.append("        ELSE NVL(TPHH1.WEIGHT_SOLD,0) * 1000 ");
        ////2017.01.12 J.Endo FIVI対応(#3502) MOD(E)
        //sql.append("    END                           AS URIAGE_QT ");
        //sql.append("   ,TPHH1.ACTUAL_SELL             AS URIAGE_VL ");
        //sql.append("   ,TRIM(TPHH1.REGULAR_UNIT_SELL) AS REGULAR_UNIT_SELL ");
        //sql.append("   ,TRIM(TPHH1.GST_TAX)           AS GST_TAX ");
        //sql.append("FROM ");
        //sql.append("    (SELECT ");
        //sql.append("        TPHH.EIGYO_DT ");
        //sql.append("       ,TPHH.STORE ");
        //sql.append("       ,TPHH.SKU ");
        //sql.append("       ,TPHH.REGULAR_UNIT_SELL ");
        //sql.append("       ,TPHH.GST_TAX ");
        //sql.append("       ,SUM(TPHH.QTY)         AS QTY ");
        //sql.append("       ,SUM(TPHH.WEIGHT_SOLD) AS WEIGHT_SOLD ");
        //sql.append("       ,SUM(TPHH.ACTUAL_SELL) AS ACTUAL_SELL ");
        //sql.append("    FROM TMP_POS_H_HAMPER TPHH ");
        //sql.append("    GROUP BY ");
        //sql.append("        TPHH.EIGYO_DT ");
        //sql.append("       ,TPHH.STORE ");
        //sql.append("       ,TPHH.SKU ");
        //sql.append("       ,TPHH.REGULAR_UNIT_SELL ");
        //sql.append("       ,TPHH.GST_TAX) TPHH1 ");
        //sql.append("LEFT OUTER JOIN R_SYOHIN RS ");
        //sql.append("    ON  EXISTS "); // この存在チェックは「商品マスタ.分類コードの最小値」のみを抽出するため（開始）
        //sql.append("    (SELECT 1 FROM R_SYOHIN RS2 ");
        //sql.append("    WHERE ");
        //sql.append("        RS.SYOHIN_CD = TPHH1.SKU ");
        //sql.append("    AND RS.YUKO_DT = ");
        //sql.append("        (SELECT MAX(YUKO_DT) FROM R_SYOHIN "); // この検索は「商品マスタ.有効日範囲内の最大」を抽出（開始）
        //sql.append("        WHERE ");
        //sql.append("            SYOHIN_CD = TPHH1.SKU ");
        //sql.append("        AND YUKO_DT <= TPHH1.EIGYO_DT ");
        //sql.append("        GROUP BY SYOHIN_CD ");
        //sql.append("        ) ");                                  // この検索は「商品マスタ.有効日範囲内の最大」を抽出（終了）
        //sql.append("    AND RS.BUNRUI1_CD = ");
        //sql.append("        (SELECT MIN(RS3.BUNRUI1_CD) FROM R_SYOHIN RS3 ");
        //sql.append("        WHERE ");
        //sql.append("            RS3.SYOHIN_CD = TPHH1.SKU ");
        //sql.append("        AND RS3.YUKO_DT = ");
        //sql.append("            (SELECT MAX(YUKO_DT) FROM R_SYOHIN "); // この検索は「商品マスタ.有効日範囲内の最大」を抽出（開始）
        //sql.append("            WHERE ");
        //sql.append("                SYOHIN_CD = TPHH1.SKU ");
        //sql.append("            AND YUKO_DT <= TPHH1.EIGYO_DT ");
        //sql.append("            GROUP BY SYOHIN_CD ");
        //sql.append("            ) ");                                  // この検索は「商品マスタ.有効日範囲内の最大」を抽出（終了）
        //sql.append("        GROUP BY RS3.SYOHIN_CD ");
        //sql.append("        ) ");
        //sql.append("    ) ");          // この存在チェックは「商品マスタ.分類コードの最小値」のみを抽出するため（終了）
        //sql.append("LEFT OUTER JOIN R_TENPO RT ");
        //sql.append("    ON  RT.TENPO_CD = LPAD(TPHH1.STORE,6,'0') ");
        sql.append("SELECT ");
        sql.append("    URIAGE_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,HAMPER_SYOHIN_CD ");
        sql.append("   ,MIN(TENPO_NA)          AS TENPO_NA ");
        sql.append("   ,MIN(SYOHIN_NA)         AS SYOHIN_NA ");
        sql.append("   ,MIN(TEIKAN_KB)         AS TEIKAN_KB ");
        sql.append("   ,SUM(URIAGE_QT)         AS URIAGE_QT ");
        sql.append("   ,SUM(URIAGE_VL)         AS URIAGE_VL ");
        sql.append("   ,MIN(REGULAR_UNIT_SELL) AS REGULAR_UNIT_SELL ");
        sql.append("   ,MIN(GST_TAX)           AS GST_TAX ");
        //2017.02.22 J.Endo FIVI対応(#4010) ADD(S)
        sql.append("   ,CASE WHEN SUM(URIAGE_QT)=0 ");
        sql.append("        THEN 0 ");
        sql.append("        ELSE ");
        sql.append("            CASE WHEN MIN(TEIKAN_KB)='1' ");
        sql.append("                THEN ROUND(SUM(URIAGE_VL) / SUM(URIAGE_QT),2) ");
        sql.append("                ELSE ROUND(SUM(URIAGE_VL) / (SUM(URIAGE_QT) / 1000),2) ");
        sql.append("            END ");
        sql.append("    END AS HAMPER_NEBIKIGO_BAITANKA ");
        //2017.02.22 J.Endo FIVI対応(#4010) ADD(E)
        sql.append("FROM ( ");
        sql.append("    SELECT ");
        sql.append("        TPHH1.EIGYO_DT                AS URIAGE_DT ");
        sql.append("       ,LPAD(TPHH1.STORE,6,'0')       AS TENPO_CD ");
        sql.append("       ,TPHH1.SKU                     AS HAMPER_SYOHIN_CD ");
        sql.append("       ,RT.KANJI_RN                   AS TENPO_NA ");
        sql.append("       ,RS.HINMEI_KANJI_NA            AS SYOHIN_NA ");
        sql.append("       ,RS.TEIKAN_KB                  AS TEIKAN_KB ");
        sql.append("       ,CASE WHEN RS.TEIKAN_KB='1' ");
        sql.append("            THEN TPHH1.QTY ");
        sql.append("            ELSE NVL(TPHH1.WEIGHT_SOLD,0) * 1000 ");
        sql.append("        END                           AS URIAGE_QT ");
        // #4388  2017/3/23 N.katou(S)
//        sql.append("       ,TPHH1.ACTUAL_SELL             AS URIAGE_VL ");
        sql.append("       ,TPHH1.ACTUAL_SELL_WOT             AS URIAGE_VL ");
        // #4388  2017/3/23 N.katou(E)
        sql.append("       ,TRIM(TPHH1.REGULAR_UNIT_SELL) AS REGULAR_UNIT_SELL ");
        sql.append("       ,TRIM(TPHH1.GST_TAX)           AS GST_TAX ");
        sql.append("    FROM ( ");
        sql.append("        SELECT ");
        sql.append("            TPHH.EIGYO_DT ");
        sql.append("           ,TPHH.STORE ");
        sql.append("           ,TPHH.SKU ");
        sql.append("           ,TPHH.REGULAR_UNIT_SELL ");
        sql.append("           ,TPHH.GST_TAX ");
        sql.append("           ,SUM(TPHH.QTY)         AS QTY ");
        sql.append("           ,SUM(TPHH.WEIGHT_SOLD) AS WEIGHT_SOLD ");
        // #4388  2017/3/23 N.katou(S)
//        sql.append("           ,SUM(TPHH.ACTUAL_SELL) AS ACTUAL_SELL ");
        sql.append("           ,SUM(TPHH.ACTUAL_SELL_WOT) AS ACTUAL_SELL_WOT ");
        // #4388  2017/3/23 N.katou(E)
        //2017/05/18 J.Endo FIVI対応(#5074) MOD(S)
        //sql.append("        FROM TMP_POS_H_HAMPER TPHH ");
        sql.append("        FROM WK_POS_H_HAMPER TPHH ");
        sql.append("        WHERE ");
        sql.append("            TPHH.ERR_KB = '0' ");
        //2017/05/18 J.Endo FIVI対応(#5074) MOD(E)
        sql.append("        GROUP BY ");
        sql.append("            TPHH.EIGYO_DT ");
        sql.append("           ,TPHH.STORE ");
        sql.append("           ,TPHH.SKU ");
        sql.append("           ,TPHH.REGULAR_UNIT_SELL ");
        sql.append("           ,TPHH.GST_TAX ");
        sql.append("        ) TPHH1 ");
        sql.append("        LEFT OUTER JOIN R_SYOHIN RS ");
        sql.append("            ON EXISTS ( "); // この存在チェックは「商品マスタ.分類コードの最小値」のみを抽出（開始）
        sql.append("            SELECT 1 FROM R_SYOHIN RS2 ");
        sql.append("            WHERE ");
        sql.append("                RS.SYOHIN_CD = TPHH1.SKU AND ");
        sql.append("                RS.YUKO_DT = ( ");
        sql.append("                    SELECT MAX(YUKO_DT) FROM R_SYOHIN "); // この検索は「商品マスタ.有効日範囲内の最大」を抽出（開始）
        sql.append("                    WHERE ");
        sql.append("                        SYOHIN_CD = TPHH1.SKU AND ");
        sql.append("                        YUKO_DT <= TPHH1.EIGYO_DT ");
        sql.append("                    GROUP BY SYOHIN_CD) AND ");           // この検索は「商品マスタ.有効日範囲内の最大」を抽出（終了）
        sql.append("                RS.BUNRUI1_CD = ( ");
        sql.append("                    SELECT MIN(RS3.BUNRUI1_CD) FROM R_SYOHIN RS3 ");
        sql.append("                    WHERE ");
        sql.append("                        RS3.SYOHIN_CD = TPHH1.SKU AND ");
        sql.append("                        RS3.YUKO_DT = ( ");
        sql.append("                        SELECT MAX(YUKO_DT) FROM R_SYOHIN "); // この検索は「商品マスタ.有効日範囲内の最大」を抽出（開始）
        sql.append("                        WHERE ");
        sql.append("                            SYOHIN_CD = TPHH1.SKU AND ");
        sql.append("                            YUKO_DT <= TPHH1.EIGYO_DT ");
        sql.append("                        GROUP BY SYOHIN_CD) ");               // この検索は「商品マスタ.有効日範囲内の最大」を抽出（終了）
        sql.append("                    GROUP BY RS3.SYOHIN_CD) ");
        sql.append("            ) ");           // この存在チェックは「商品マスタ.分類コードの最小値」のみを抽出（終了）
        sql.append("    LEFT OUTER JOIN R_TENPO RT ");
        sql.append("        ON  RT.TENPO_CD = LPAD(TPHH1.STORE,6,'0') ");
        sql.append("    ) ");
        sql.append("GROUP BY ");
        sql.append("    URIAGE_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,HAMPER_SYOHIN_CD ");
        //2017.01.27 J.Endo FIVI対応(#3593) MOD(E)

        return sql.toString();
    }

    /**
     * ハンパー売上実績データ作成用SQLを取得する
     *
     * @return ハンパー売上実績データ作成用SQL
     */
    private String getHamperUriageJisekiInsertSql() {
        StringBuilder sql = new StringBuilder();

        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_HAMPER_URIAGE_JISEKI DHUJ ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      ? AS URIAGE_DT");
        sql.append("      , ? AS TENPO_CD");
        sql.append("      , ? AS HAMPER_SYOHIN_CD");
        sql.append("      , ? AS TENPO_NA");
        sql.append("      , ? AS SYOHIN_NA");
        sql.append("      , ? AS URIAGE_QT");
        sql.append("      , ? AS URIAGE_VL");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS");
        sql.append("      , '0' AS DELETE_FG");
        sql.append("      , ? AS HAMPER_BAITANKA_VL");
        sql.append("      , ? AS TEIKAN_KB");
        sql.append("      , ? AS HAMPER_NEBIKIGO_BAITANKA ");
        sql.append("    FROM");
        sql.append("      DUAL");
        sql.append("  ) WPHH ");
        sql.append("    ON ( ");
        sql.append("      DHUJ.URIAGE_DT = WPHH.URIAGE_DT ");
        sql.append("      AND DHUJ.TENPO_CD = WPHH.TENPO_CD ");
        sql.append("      AND DHUJ.HAMPER_SYOHIN_CD = WPHH.HAMPER_SYOHIN_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  URIAGE_QT = NVL(DHUJ.URIAGE_QT,0) + NVL(WPHH.URIAGE_QT,0)");
        sql.append("  , URIAGE_VL = NVL(DHUJ.URIAGE_VL,0) + NVL(WPHH.URIAGE_VL,0)");
        sql.append("  , UPDATE_USER_ID = WPHH.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WPHH.UPDATE_TS WHEN NOT MATCHED THEN ");
//        sql.append("INSERT /*+ APPEND */ INTO DT_HAMPER_URIAGE_JISEKI( ");
        sql.append("INSERT ( ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("    URIAGE_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,HAMPER_SYOHIN_CD ");
        sql.append("   ,TENPO_NA ");
        sql.append("   ,SYOHIN_NA ");
        sql.append("   ,URIAGE_QT ");
        sql.append("   ,URIAGE_VL ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("   ,DELETE_FG ");
        sql.append("   ,HAMPER_BAITANKA_VL ");
        //2017.01.12 J.Endo FIVI対応(#3502) ADD(S)
        sql.append("   ,TEIKAN_KB ");
        //2017.01.12 J.Endo FIVI対応(#3502) ADD(E)
        //2017.02.22 J.Endo FIVI対応(#4010) ADD(S)
        sql.append("   ,HAMPER_NEBIKIGO_BAITANKA ");
        //2017.02.22 J.Endo FIVI対応(#4010) ADD(E)
        sql.append("   ) ");
        sql.append("VALUES ( ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("    ? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,'0' ");
//        sql.append("   ,? ");
//        //2017.01.12 J.Endo FIVI対応(#3502) ADD(S)
//        sql.append("   ,? ");
//        //2017.01.12 J.Endo FIVI対応(#3502) ADD(E)
//        //2017.02.22 J.Endo FIVI対応(#4010) ADD(S)
//        sql.append("   ,? ");
//        //2017.02.22 J.Endo FIVI対応(#4010) ADD(E)
        sql.append("    WPHH.URIAGE_DT");
        sql.append("    , WPHH.TENPO_CD");
        sql.append("    , WPHH.HAMPER_SYOHIN_CD");
        sql.append("    , WPHH.TENPO_NA");
        sql.append("    , WPHH.SYOHIN_NA");
        sql.append("    , WPHH.URIAGE_QT");
        sql.append("    , WPHH.URIAGE_VL");
        sql.append("    , WPHH.INSERT_USER_ID");
        sql.append("    , WPHH.INSERT_TS");
        sql.append("    , WPHH.UPDATE_USER_ID");
        sql.append("    , WPHH.UPDATE_TS");
        sql.append("    , WPHH.DELETE_FG");
        sql.append("    , WPHH.HAMPER_BAITANKA_VL");
        sql.append("    , WPHH.TEIKAN_KB");
        sql.append("    , WPHH.HAMPER_NEBIKIGO_BAITANKA   ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("   ) ");

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
