package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: HamperKouseiSyohinUriageJisekiCreateDao.java クラス</p>
 * <p>説明　: ハンパー構成商品売上実績データ作成</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016/11/04) S_MDware-G_FIVIマート様開発 VINX J.Endo
 * @Version 1.01 (2016/11/15) 不具合対応(#2829) VINX J.Endo
 * @Version 1.02 (2016/11/18) 不具合対応(#2892) VINX J.Endo
 * @Version 1.03 (2017/01/12) FIVI対応(#3502) VINX J.Endo
 * @Version 1.04 (2017/01/23) FIVI対応(#3717) VINX J.Endo
 * @Version 1.05 (2017/01/27) FIVI対応(#3593) VINX J.Endo
 * @Version 1.06 (2017/02/22) FIVI対応(#4010) VINX J.Endo
 * @Version 1.07 (2017/03/23) FIVI対応(#4388) VINX N.Katou
 * @Version 1.08 (2017/05/18) FIVI対応(#5074) VINX J.Endo
 * @Version 1.09 (2017/06/14) FIVI対応(#5399) VINX X.Liu
 *
 */
public class HamperKouseiSyohinUriageJisekiCreateDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String DAO_NAME = "ハンパー構成商品売上実績データ作成";
    /** 登録先テーブル名称(ハンパー構成商品売上実績データ作成) */
    private static final String DT_HAMPER_KOUSEI_URIAGE_JISEKI_NAME = "ハンパー構成商品売上実績データ";

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
            // ハンパー構成商品売上実績データ作成の追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_HAMPER_KOUSEI_URIAGE_JISEKI_NAME + "の追加を開始します。");

            preparedStatementExSelect = invoker.getDataBase().prepareStatement(getDtPosAItemSelectSql());

            //2016.11.18 J.Endo FIVI対応(#2892) ADD(S)
            preparedStatementExSelect.setString(1, FiResorceUtility.getBatchDt());
            //2016.11.18 J.Endo FIVI対応(#2892) ADD(E)

            resultSet = preparedStatementExSelect.executeQuery();

            //2017.01.23 J.Endo FIVI対応(#3717) ADD(S)
            preparedStatementExInsert = invoker.getDataBase().prepareStatement(getHamperKouseiSyohinUriageJisekiInsertSql());
            //2017.01.23 J.Endo FIVI対応(#3717) ADD(E)

            // POSジャーナル（Hレコード）一時データのデータがなくなるまで繰り返す
            while (resultSet.next()) {
                int index = 1;
                //2017.01.23 J.Endo FIVI対応(#3717) DEL(S)
                //preparedStatementExInsert = invoker.getDataBase().prepareStatement(getHamperKouseiSyohinUriageJisekiInsertSql());
                //2017.01.23 J.Endo FIVI対応(#3717) DEL(E)

                // #4388  2017/3/23 N.katou(S)
                // ハンパー商品売単価（税抜き）の税加算
//                BigDecimal regularUnitSell_H = new BigDecimal(resultSet.getString("REGULAR_UNIT_SELL_H"));
//                BigDecimal taxRate_H = new BigDecimal(resultSet.getString("GST_TAX_H"));
//                CalculateTaxUtility calculateTaxUtility_H = new CalculateTaxUtility(regularUnitSell_H,
//                                                                "2",
//                                                                CalculateTaxUtility.INT_SOTOZEI,
//                                                                taxRate_H,
//                                                                CalculateTaxUtility.STR_MIMANSISYAGONYU);
//                // ハンパー構成商品売単価（税抜き）の税加算
//                BigDecimal regularUnitSell_A = new BigDecimal(resultSet.getString("REGULAR_UNIT_SELL_A"));
//                BigDecimal taxRate_A = new BigDecimal(resultSet.getString("GST_TAX_A"));
//                CalculateTaxUtility calculateTaxUtility_A = new CalculateTaxUtility(regularUnitSell_A,
//                                                                "2",
//                                                                CalculateTaxUtility.INT_SOTOZEI,
//                                                                taxRate_A,
//                                                                CalculateTaxUtility.STR_MIMANSISYAGONYU);
                // #4388  2017/3/23 N.katou(E)
                
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_SYOHIN_CD"));
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_KOSEI_SYOHIN_CD"));
                preparedStatementExInsert.setString(index++, resultSet.getString("URIAGE_DT"));
                preparedStatementExInsert.setString(index++, resultSet.getString("TENPO_CD"));
                // #4388  2017/3/23 N.katou(S)
//                preparedStatementExInsert.setString(index++, calculateTaxUtility_H.getTaxIn().toString());
                preparedStatementExInsert.setString(index++, resultSet.getString("REGULAR_UNIT_SELL_H"));
//                preparedStatementExInsert.setString(index++, calculateTaxUtility_A.getTaxIn().toString());
                preparedStatementExInsert.setString(index++, resultSet.getString("REGULAR_UNIT_SELL_A"));
                // #4388  2017/3/23 N.katou(E)
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_KOSEI_SYOHIN_NM"));
                preparedStatementExInsert.setString(index++, resultSet.getString("BUNRUI1_CD"));
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_KOSEI_HANBAI_TANI"));
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_KOSEI_URIAGE_QT"));
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_KOSEI_URIAGE_VL"));
                preparedStatementExInsert.setString(index++, userId);
                preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
                preparedStatementExInsert.setString(index++, userId);
                preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
                //2017.01.12 J.Endo FIVI対応(#3502) ADD(S)
                preparedStatementExInsert.setString(index++, resultSet.getString("TEIKAN_KB"));
                //2017.01.12 J.Endo FIVI対応(#3502) ADD(E)
                //2017.02.22 J.Endo FIVI対応(#4010) ADD(S)
                preparedStatementExInsert.setString(index++, resultSet.getString("HAMPER_KOSEI_NEBIKI_BAITANKA"));
                //2017.02.22 J.Endo FIVI対応(#4010) ADD(E)
                insertCount = insertCount + preparedStatementExInsert.executeUpdate();
            }

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_HAMPER_KOUSEI_URIAGE_JISEKI_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_HAMPER_KOUSEI_URIAGE_JISEKI_NAME + "の追加を終了します。");

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
     * Aレコード、Hレコードからハンパー構成商品売上実績データ取得用SQLを取得する
     *
     * @return Aレコード、Hレコードからハンパー構成商品売上実績データ取得用SQL
     */
    private String getDtPosAItemSelectSql() {
        StringBuilder sql = new StringBuilder();

        //2017.01.27 J.Endo FIVI対応(#3593) MOD(S)
        //sql.append("SELECT ");
        //sql.append("    DPAI1.HAMPER_ITEM_CD          AS HAMPER_SYOHIN_CD ");
        //sql.append("   ,DPAI1.SKU                     AS HAMPER_KOSEI_SYOHIN_CD ");
        //sql.append("   ,SUBSTR(DPAI1.EIGYO_DT,1,8)    AS URIAGE_DT ");
        //sql.append("   ,LPAD(DPAI1.STORE,6,'0')       AS TENPO_CD ");
        //sql.append("   ,TRIM(TPHH.REGULAR_UNIT_SELL)  AS REGULAR_UNIT_SELL_H ");
        //sql.append("   ,TRIM(TPHH.GST_TAX)            AS GST_TAX_H ");
        //sql.append("   ,TRIM(DPAI1.REGULAR_UNIT_SELL) AS REGULAR_UNIT_SELL_A ");
        //sql.append("   ,TRIM(DPAI1.GST_TAX)           AS GST_TAX_A ");
        //sql.append("   ,RS.HINMEI_KANJI_NA            AS HAMPER_KOSEI_SYOHIN_NM ");
        //sql.append("   ,SUBSTR(RS.BUNRUI1_CD,1,2)     AS BUNRUI1_CD ");
        ////2017.01.12 J.Endo FIVI対応(#3502) ADD(S)
        //sql.append("   ,RS.TEIKAN_KB                  AS TEIKAN_KB ");
        ////2017.01.12 J.Endo FIVI対応(#3502) ADD(E)
        //sql.append("   ,RN.KANJI_NA                   AS HAMPER_KOSEI_HANBAI_TANI ");
        //sql.append("   ,CASE WHEN RS.TEIKAN_KB='1' ");
        //sql.append("        THEN DPAI1.QTY ");
        //sql.append("        ELSE DPAI1.WEIGHT_SOLD "); // DT_POS_A_ITEMは単位が"g"(グラム)
        //sql.append("    END                           AS HAMPER_KOSEI_URIAGE_QT ");
        //sql.append("   ,DPAI1.ACTUAL_SELL             AS HAMPER_KOSEI_URIAGE_VL ");
        //sql.append("FROM ");
        //sql.append("    (SELECT ");
        //sql.append("        DPAI.HAMPER_ITEM_CD ");
        //sql.append("       ,DPAI.SKU ");
        //sql.append("       ,DPAI.EIGYO_DT ");
        //sql.append("       ,DPAI.STORE ");
        //sql.append("       ,DPAI.POS ");
        //sql.append("       ,DPAI.TRANS_NO ");
        //sql.append("       ,DPAI.REGULAR_UNIT_SELL ");
        //sql.append("       ,DPAI.GST_TAX ");
        //sql.append("       ,SUM(DPAI.QTY)         AS QTY ");
        //sql.append("       ,SUM(DPAI.WEIGHT_SOLD) AS WEIGHT_SOLD ");
        //sql.append("       ,SUM(DPAI.ACTUAL_SELL) AS ACTUAL_SELL ");
        //sql.append("    FROM DT_POS_A_ITEM DPAI ");
        ////2016.11.18 J.Endo FIVI対応(#2892) ADD(S)
        //sql.append("    WHERE DPAI.DATA_SAKUSEI_DT = ? ");
        ////2016.11.18 J.Endo FIVI対応(#2892) ADD(E)
        //sql.append("    GROUP BY ");
        //sql.append("        DPAI.HAMPER_ITEM_CD ");
        //sql.append("       ,DPAI.SKU ");
        //sql.append("       ,DPAI.EIGYO_DT ");
        //sql.append("       ,DPAI.STORE ");
        //sql.append("       ,DPAI.POS ");
        //sql.append("       ,DPAI.TRANS_NO ");
        //sql.append("       ,DPAI.REGULAR_UNIT_SELL ");
        //sql.append("       ,DPAI.GST_TAX) DPAI1 ");
        //sql.append("LEFT OUTER JOIN R_SYOHIN RS ");
        //sql.append("    ON EXISTS "); // この存在チェックは「商品マスタ.分類コードの最小値」のみを抽出するため（開始）
        //sql.append("    (SELECT 1 FROM R_SYOHIN RS2 ");
        //sql.append("    WHERE ");
        //sql.append("        RS.SYOHIN_CD = DPAI1.SKU ");
        //sql.append("    AND RS.YUKO_DT = ");
        //sql.append("        (SELECT MAX(YUKO_DT) FROM R_SYOHIN "); // この検索は「商品マスタ.有効日範囲内の最大」を抽出（開始）
        //sql.append("        WHERE ");
        //sql.append("            SYOHIN_CD = DPAI1.SKU ");
        //sql.append("        AND YUKO_DT <= DPAI1.EIGYO_DT ");
        //sql.append("        GROUP BY SYOHIN_CD ");
        //sql.append("        ) ");                                  // この検索は「商品マスタ.有効日範囲内の最大」を抽出（終了）
        //sql.append("    AND RS.BUNRUI1_CD = ");
        //sql.append("        (SELECT MIN(RS3.BUNRUI1_CD) FROM R_SYOHIN RS3 ");
        //sql.append("        WHERE ");
        //sql.append("            RS3.SYOHIN_CD = DPAI1.SKU ");
        //sql.append("        AND RS3.YUKO_DT = ");
        //sql.append("            (SELECT MAX(YUKO_DT) FROM R_SYOHIN "); // この検索は「商品マスタ.有効日範囲内の最大」を抽出（開始）
        //sql.append("            WHERE ");
        //sql.append("                SYOHIN_CD = DPAI1.SKU ");
        //sql.append("            AND YUKO_DT <= DPAI1.EIGYO_DT ");
        //sql.append("            GROUP BY SYOHIN_CD ");
        //sql.append("            ) ");                                  // この検索は「商品マスタ.有効日範囲内の最大」を抽出（終了）
        //sql.append("        GROUP BY RS3.SYOHIN_CD ");
        //sql.append("        ) ");
        //sql.append("    ) ");         // この存在チェックは「商品マスタ.分類コードの最小値」のみを抽出するため（終了）
        //sql.append("LEFT OUTER JOIN R_NAMECTF RN ");
        //sql.append("    ON  RN.CODE_CD = RS.HANBAI_TANI ");
        //sql.append("    AND RN.SYUBETU_NO_CD = '3040' ");
        ////2016.11.15 J.Endo FIVI対応(#2829) MOD(S)
        ////sql.append("LEFT OUTER JOIN TMP_POS_H_HAMPER TPHH ");
        //sql.append("INNER JOIN TMP_POS_H_HAMPER TPHH ");
        ////2016.11.15 J.Endo FIVI対応(#2829) MOD(E)
        //sql.append("    ON  TPHH.STORE = DPAI1.STORE ");
        //sql.append("    AND TPHH.POS = DPAI1.POS ");
        //sql.append("    AND TPHH.TRANS_NO = DPAI1.TRANS_NO ");
        //sql.append("    AND TPHH.EIGYO_DT = DPAI1.EIGYO_DT ");
        //sql.append("    AND TPHH.SKU = DPAI1.HAMPER_ITEM_CD ");
        sql.append("SELECT ");
        sql.append("    HAMPER_SYOHIN_CD ");
        sql.append("   ,HAMPER_KOSEI_SYOHIN_CD ");
        sql.append("   ,URIAGE_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,REGULAR_UNIT_SELL_H ");
        sql.append("   ,MIN(GST_TAX_H)                AS GST_TAX_H ");
        sql.append("   ,REGULAR_UNIT_SELL_A ");
        sql.append("   ,MIN(GST_TAX_A)                AS GST_TAX_A ");
        sql.append("   ,MIN(HAMPER_KOSEI_SYOHIN_NM)   AS HAMPER_KOSEI_SYOHIN_NM ");
        sql.append("   ,MIN(BUNRUI1_CD)               AS BUNRUI1_CD ");
        sql.append("   ,MIN(TEIKAN_KB)                AS TEIKAN_KB ");
        sql.append("   ,MIN(HAMPER_KOSEI_HANBAI_TANI) AS HAMPER_KOSEI_HANBAI_TANI ");
        sql.append("   ,SUM(HAMPER_KOSEI_URIAGE_QT)   AS HAMPER_KOSEI_URIAGE_QT ");
        sql.append("   ,SUM(HAMPER_KOSEI_URIAGE_VL)   AS HAMPER_KOSEI_URIAGE_VL ");
        //2017.02.22 J.Endo FIVI対応(#4010) ADD(S)
        sql.append("   ,CASE WHEN SUM(HAMPER_KOSEI_URIAGE_QT)=0 ");
        sql.append("        THEN 0 ");
        sql.append("        ELSE ");
        sql.append("            CASE WHEN MIN(TEIKAN_KB)='1' ");
        sql.append("                THEN ROUND(SUM(HAMPER_KOSEI_URIAGE_VL) / SUM(HAMPER_KOSEI_URIAGE_QT),2) ");
        sql.append("                ELSE ROUND(SUM(HAMPER_KOSEI_URIAGE_VL) / SUM(HAMPER_KOSEI_URIAGE_QT),2) ");
        sql.append("            END ");
        sql.append("    END AS HAMPER_KOSEI_NEBIKI_BAITANKA ");
        //2017.02.22 J.Endo FIVI対応(#4010) ADD(E)
        sql.append("FROM ( ");
        sql.append("    SELECT ");
        sql.append("        WPAI.HAMPER_ITEM_CD          AS HAMPER_SYOHIN_CD ");
        sql.append("       ,WPAI.SKU                     AS HAMPER_KOSEI_SYOHIN_CD ");
        sql.append("       ,SUBSTR(WPAI.EIGYO_DT,1,8)    AS URIAGE_DT ");
        sql.append("       ,LPAD(WPAI.STORE,6,'0')       AS TENPO_CD ");
        sql.append("       ,TRIM(TPHH.REGULAR_UNIT_SELL) AS REGULAR_UNIT_SELL_H ");
        sql.append("       ,TRIM(TPHH.GST_TAX)           AS GST_TAX_H ");
        sql.append("       ,TRIM(WPAI.REGULAR_UNIT_SELL) AS REGULAR_UNIT_SELL_A ");
        sql.append("       ,TRIM(WPAI.GST_TAX)           AS GST_TAX_A ");
        sql.append("       ,RS.HINMEI_KANJI_NA           AS HAMPER_KOSEI_SYOHIN_NM ");
        sql.append("       ,SUBSTR(RS.BUNRUI1_CD,1,2)    AS BUNRUI1_CD ");
        sql.append("       ,RS.TEIKAN_KB                 AS TEIKAN_KB ");
        sql.append("       ,RN.KANJI_NA                  AS HAMPER_KOSEI_HANBAI_TANI ");
        sql.append("       ,CASE WHEN RS.TEIKAN_KB='1' ");
        sql.append("            THEN WPAI.QTY ");
        sql.append("            ELSE WPAI.WEIGHT_SOLD ");
        sql.append("        END                          AS HAMPER_KOSEI_URIAGE_QT ");
        // #4388  2017/3/23 N.katou(S)
//        sql.append("       ,WPAI.ACTUAL_SELL             AS HAMPER_KOSEI_URIAGE_VL ");
        sql.append("       ,WPAI.ACTUAL_SELL_WOT             AS HAMPER_KOSEI_URIAGE_VL ");
        // #4388  2017/3/23 N.katou(E)
        sql.append("    FROM ( ");
        sql.append("        SELECT ");
        sql.append("            HAMPER_ITEM_CD ");
        sql.append("           ,SKU ");
        sql.append("           ,EIGYO_DT ");
        sql.append("           ,STORE ");
        sql.append("           ,POS ");
        sql.append("           ,TRANS_NO ");
        sql.append("           ,REGULAR_UNIT_SELL ");
        sql.append("           ,GST_TAX ");
        sql.append("           ,SUM(QTY)         AS QTY ");
        sql.append("           ,SUM(WEIGHT_SOLD) AS WEIGHT_SOLD ");
        // #4388  2017/3/23 N.katou(S)
//        sql.append("           ,SUM(ACTUAL_SELL) AS ACTUAL_SELL ");
        sql.append("           ,SUM(ACTUAL_SELL_WOT) AS ACTUAL_SELL_WOT ");
        // #4388  2017/3/23 N.katou(E)
        sql.append("        FROM WK_POS_A_ITEM ");
        sql.append("        WHERE ");
        sql.append("            ERR_KB = '0' AND ");
        sql.append("            DATA_SAKUSEI_DT = ? ");
        sql.append("        GROUP BY ");
        sql.append("            HAMPER_ITEM_CD ");
        sql.append("           ,SKU ");
        sql.append("           ,EIGYO_DT ");
        sql.append("           ,STORE ");
        sql.append("           ,POS ");
        sql.append("           ,TRANS_NO ");
        sql.append("           ,REGULAR_UNIT_SELL ");
        sql.append("           ,GST_TAX ");
        sql.append("        ) WPAI ");
        sql.append("        LEFT OUTER JOIN R_SYOHIN RS ");
        sql.append("        ON EXISTS ( "); // この存在チェックは「商品マスタ.分類コードの最小値」のみを抽出（開始）
        sql.append("            SELECT 1 FROM R_SYOHIN RS2 ");
        sql.append("            WHERE ");
        sql.append("                RS.SYOHIN_CD = WPAI.SKU AND ");
        sql.append("                RS.YUKO_DT = ( ");
        sql.append("                    SELECT MAX(YUKO_DT) FROM R_SYOHIN "); // この検索は「商品マスタ.有効日範囲内の最大」を抽出（開始）
        sql.append("                    WHERE ");
        sql.append("                        SYOHIN_CD = WPAI.SKU AND ");
        sql.append("                        YUKO_DT <= WPAI.EIGYO_DT ");
        sql.append("                    GROUP BY SYOHIN_CD) AND ");           // この検索は「商品マスタ.有効日範囲内の最大」を抽出（終了）
        sql.append("                RS.BUNRUI1_CD = ( ");
        sql.append("                    SELECT MIN(RS3.BUNRUI1_CD) FROM R_SYOHIN RS3 ");
        sql.append("                    WHERE ");
        sql.append("                        RS3.SYOHIN_CD = WPAI.SKU AND ");
        sql.append("                        RS3.YUKO_DT = ( ");
        sql.append("                            SELECT MAX(YUKO_DT) FROM R_SYOHIN "); // この検索は「商品マスタ.有効日範囲内の最大」を抽出（開始）
        sql.append("                            WHERE ");
        sql.append("                                SYOHIN_CD = WPAI.SKU AND ");
        sql.append("                                YUKO_DT <= WPAI.EIGYO_DT ");
        sql.append("                            GROUP BY SYOHIN_CD) ");               // この検索は「商品マスタ.有効日範囲内の最大」を抽出（終了）
        sql.append("                    GROUP BY RS3.SYOHIN_CD) ");
        sql.append("            ) ");       // この存在チェックは「商品マスタ.分類コードの最小値」のみを抽出（終了）
        sql.append("        LEFT OUTER JOIN R_NAMECTF RN ");
        sql.append("        ON  RN.CODE_CD = RS.HANBAI_TANI AND ");
        sql.append("            RN.SYUBETU_NO_CD = '3040' ");
        //2017/05/18 J.Endo FIVI対応(#5074) MOD(S)
        //sql.append("        INNER JOIN TMP_POS_H_HAMPER TPHH ");
        sql.append("        INNER JOIN WK_POS_H_HAMPER TPHH ");
        //2017/05/18 J.Endo FIVI対応(#5074) MOD(E)
        sql.append("        ON  TPHH.STORE = WPAI.STORE AND ");
        sql.append("            TPHH.POS = WPAI.POS AND ");
        sql.append("            TPHH.TRANS_NO = WPAI.TRANS_NO AND ");
        sql.append("            TPHH.EIGYO_DT = WPAI.EIGYO_DT AND ");
        sql.append("            TPHH.SKU = WPAI.HAMPER_ITEM_CD ");
        sql.append("    ) ");
        sql.append("GROUP BY ");
        sql.append("    HAMPER_SYOHIN_CD ");
        sql.append("   ,HAMPER_KOSEI_SYOHIN_CD ");
        sql.append("   ,URIAGE_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,REGULAR_UNIT_SELL_H ");
        sql.append("   ,REGULAR_UNIT_SELL_A ");
        //2017.01.27 J.Endo FIVI対応(#3593) MOD(E)

        return sql.toString();
    }

    /**
     * ハンパー構成商品売上実績データ作成用SQLを取得する
     *
     * @return ハンパー構成商品売上実績データ作成用SQL
     */
    private String getHamperKouseiSyohinUriageJisekiInsertSql() {
        StringBuilder sql = new StringBuilder();

        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_HAMPER_KOSEI_URI_JISEKI DHKUJ ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      ? AS HAMPER_SYOHIN_CD");
        sql.append("      , ? AS HAMPER_KOSEI_SYOHIN_CD");
        sql.append("      , ? AS URIAGE_DT");
        sql.append("      , ? AS TENPO_CD");
        sql.append("      , ? AS HAMPER_BAITANKA_VL");
        sql.append("      , ? AS HAMPER_KOSEI_BAITANKA_VL");
        sql.append("      , ? AS HAMPER_KOSEI_SYOHIN_NM");
        sql.append("      , ? AS HAMPER_KOSEI_DPT");
        sql.append("      , ? AS HAMPER_KOSEI_HANBAI_TANI");
        sql.append("      , ? AS HAMPER_KOSEI_URIAGE_QT");
        sql.append("      , ? AS HAMPER_KOSEI_URIAGE_VL");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS");
        sql.append("      , ? AS TEIKAN_KB");
        sql.append("      , ? AS HAMPER_KOSEI_NEBIKI_BAITANKA ");
        sql.append("    FROM");
        sql.append("      DUAL");
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
        sql.append("  HAMPER_KOSEI_URIAGE_QT = NVL(DHKUJ.HAMPER_KOSEI_URIAGE_QT,0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_QT,0)");
        sql.append("  , HAMPER_KOSEI_URIAGE_VL = NVL(DHKUJ.HAMPER_KOSEI_URIAGE_VL,0) + NVL(WPHH.HAMPER_KOSEI_URIAGE_VL,0)");
        sql.append("  , UPDATE_USER_ID = WPHH.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WPHH.UPDATE_TS WHEN NOT MATCHED THEN ");
//        sql.append("INSERT /*+ APPEND */ INTO DT_HAMPER_KOSEI_URI_JISEKI( ");
        sql.append("INSERT ( ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("    HAMPER_SYOHIN_CD ");
        sql.append("   ,HAMPER_KOSEI_SYOHIN_CD ");
        sql.append("   ,URIAGE_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,HAMPER_BAITANKA_VL ");
        sql.append("   ,HAMPER_KOSEI_BAITANKA_VL ");
        sql.append("   ,HAMPER_KOSEI_SYOHIN_NM ");
        sql.append("   ,HAMPER_KOSEI_DPT ");
        sql.append("   ,HAMPER_KOSEI_HANBAI_TANI ");
        sql.append("   ,HAMPER_KOSEI_URIAGE_QT ");
        sql.append("   ,HAMPER_KOSEI_URIAGE_VL ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        //2017.01.12 J.Endo FIVI対応(#3502) ADD(S)
        sql.append("   ,TEIKAN_KB ");
        //2017.01.12 J.Endo FIVI対応(#3502) ADD(E)
        //2017.02.22 J.Endo FIVI対応(#4010) ADD(S)
        sql.append("   ,HAMPER_KOSEI_NEBIKI_BAITANKA ");
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
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        //2017.01.12 J.Endo FIVI対応(#3502) ADD(S)
//        sql.append("   ,? ");
//        //2017.01.12 J.Endo FIVI対応(#3502) ADD(E)
//        //2017.02.22 J.Endo FIVI対応(#4010) ADD(S)
//        sql.append("   ,? ");
//        //2017.02.22 J.Endo FIVI対応(#4010) ADD(E)
        sql.append("    WPHH.HAMPER_SYOHIN_CD");
        sql.append("    , WPHH.HAMPER_KOSEI_SYOHIN_CD");
        sql.append("    , WPHH.URIAGE_DT");
        sql.append("    , WPHH.TENPO_CD");
        sql.append("    , WPHH.HAMPER_BAITANKA_VL");
        sql.append("    , WPHH.HAMPER_KOSEI_BAITANKA_VL");
        sql.append("    , WPHH.HAMPER_KOSEI_SYOHIN_NM");
        sql.append("    , WPHH.HAMPER_KOSEI_DPT");
        sql.append("    , WPHH.HAMPER_KOSEI_HANBAI_TANI");
        sql.append("    , WPHH.HAMPER_KOSEI_URIAGE_QT");
        sql.append("    , WPHH.HAMPER_KOSEI_URIAGE_VL");
        sql.append("    , WPHH.INSERT_USER_ID");
        sql.append("    , WPHH.INSERT_TS");
        sql.append("    , WPHH.UPDATE_USER_ID");
        sql.append("    , WPHH.UPDATE_TS");
        sql.append("    , WPHH.TEIKAN_KB");
        sql.append("    , WPHH.HAMPER_KOSEI_NEBIKI_BAITANKA           ");
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
