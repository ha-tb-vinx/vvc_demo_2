package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル： TenbetsuReceiptbetsuDataSakuseiDao クラス</p>
 * <p>説明　　：店別レシート別データ作成</p>
 * <p>著作権　： Copyright (c) 2017</p>
 * <p>会社名　： VINX</p>
 * @author   VINX
 * @Version 1.00 (2017.05.22) X.Liu #3770
 * @Version 1.01 (2017.06.22) N.Katou #5455
 * @Version 1.02 (2017.06.22) N.Katou #5456
 * @Version 1.03 (2017.08.01) N.Katou #5739
 * @Version 1.04 (2023/07/03) TUNG.LT #16388 MKH対応
 */
public class TenbetsuReceiptbetsuDataSakuseiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "店別レシート別データ作成";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチID
    private static final String BATCH_ID = "URIB012405";
    
    /**
     * 店別レシート別データ作成処理を行う
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

            ps = invoker.getDataBase().prepareStatement(getInsertDtTenReceiptHSQL());
            
            ps.setString(intI++, COMP_CD);
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, dbServerTime);
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, dbServerTime);
            intCnt = ps.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + intCnt + "件の店別レシート別データ(ヘッダ)を作成しました。");
            
            ps = invoker.getDataBase().prepareStatement(getInsertDtTenReceiptMSQL());
            intI=1;
            ps.setString(intI++, COMP_CD);
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, dbServerTime);
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, dbServerTime);
            intCnt = ps.executeUpdate();
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + intCnt + "件の店別レシート別データ(明細)を作成しました。");
            
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
     * 店別レシート別データ作成(ヘッダ)を取得SQL
     * @return SQL
     */
    private String getInsertDtTenReceiptHSQL(){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT ");
        sql.append("INTO DT_TEN_RECEIPT_H ");
        sql.append("SELECT");
        sql.append("  COMP_CD");
        sql.append("  , COMMAND_CD");
        sql.append("  , TENPO_CD");
        sql.append("  , REGI_RB");
        sql.append("  , TERMINAL_RB");
        sql.append("  , EIGYO_DT");
        sql.append("  , CASHIER_CD");
        sql.append("  , SALES_TS");
        sql.append("  , SALES_KB");
        sql.append("  , POS_TOT_VL");
        sql.append("  , CANCEL_TOT_VL");
        sql.append("  , MEISAI_KB");
        sql.append("  , TEISEI_KB");
        sql.append("  , SYOUKEI_VL");
        sql.append("  , RECEIPT_NEBIKI_VL");
        sql.append("  , URIAGE_VL");
        sql.append("  , ZEI_TOT_VL");
        sql.append("  , ZEINUKI_VL");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS ");
        sql.append("FROM");
        sql.append("  ( ");
        sql.append("    SELECT");
        sql.append("      ? AS COMP_CD");
        sql.append("      , COMMAND_CD");
        sql.append("      , ('00' || TENPO_CD) AS TENPO_CD");
        sql.append("      , REGI_RB");
        sql.append("      , TERMINAL_RB");
        sql.append("      , TRIM(EIGYO_DT) AS EIGYO_DT");
        sql.append("      , CASHIER_CD");
        sql.append("      , SALES_TS");
        sql.append("      , SALES_KB");
        sql.append("      , POS_TOT_VL");
        sql.append("      , CANCEL_TOT_VL");
        sql.append("      , MEISAI_KB");
        sql.append("      , TEISEI_KB");
        sql.append("      , SYOUKEI_VL");
        sql.append("      , RECEIPT_NEBIKI_VL");
        sql.append("      , URIAGE_VL");
        sql.append("      , ZEI_TOT_VL");
        sql.append("      , ZEINUKI_VL");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS ");
        sql.append("    FROM");
        sql.append("      ( ");
        sql.append("        SELECT");
        sql.append("          A.COMMAND AS COMMAND_CD");
        sql.append("          , A.STORE AS TENPO_CD");
        sql.append("          , A.POS AS REGI_RB");
        sql.append("          , A.TRANS_NO AS TERMINAL_RB");
        sql.append("          , A.EIGYO_DT");
        sql.append("          , CASE ");
        sql.append("            WHEN C.CASHIER_ID IS NULL ");
        sql.append("            THEN A.CASHIER_ID ");
        sql.append("            ELSE C.CASHIER_ID ");
        sql.append("            END AS CASHIER_CD");
        sql.append("          , B.TORI_TIME AS SALES_TS");
        sql.append("          , A.SALES_KB");
        sql.append("          , B.TOT_AMT AS POS_TOT_VL");
        // #5456 2017/6/22 N.katou(S)
//        sql.append("          , A.ACTUAL_SELL2 AS CANCEL_TOT_VL");
        sql.append("          , A.CAN_ACTUAL_SELL2 AS CANCEL_TOT_VL");
        // #5456 2017/6/22 N.katou(E)
        sql.append("          , '0' AS MEISAI_KB");
        sql.append("          , CASE ");
        sql.append("            WHEN C.STORE IS NULL ");
        sql.append("            THEN '9' ");
        sql.append("            ELSE '0' ");
        sql.append("            END AS TEISEI_KB");
        sql.append("          , A.ACTUAL_SELL2 AS SYOUKEI_VL   ");
        sql.append("          , NVL(A.ACTUAL_SELL2,0) - NVL(B.TOT_AMT,0) AS RECEIPT_NEBIKI_VL");
        sql.append("          , B.TOT_AMT AS URIAGE_VL");
        sql.append("          , B.TAX_AMT AS ZEI_TOT_VL");
        sql.append("          , B.ZEINUKI_VL ");
        sql.append("        FROM");
        sql.append("          ( ");
        sql.append("            SELECT");
        sql.append("              WPAI.COMMAND");
        sql.append("              , WPAI.STORE");
        sql.append("              , WPAI.POS");
        sql.append("              , WPAI.TRANS_NO");
        sql.append("              , WPAI.EIGYO_DT");
        sql.append("              , WPAI.CASHIER_ID");
        // #5456 2017/6/22 N.katou(S)
        sql.append("              , WPAI.ACTUAL_SELL2");
//        sql.append("              , CAN_A.ACTUAL_SELL2");
        sql.append("              , CAN_A.CAN_ACTUAL_SELL2");
        // #5456 2017/6/22 N.katou(E)
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("              , CASE ");
        // sql.append("                WHEN ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPAI.ATYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    WK_POS_A_ITEM SAL_WPAI ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPAI.STORE = WPAI.STORE ");
        // sql.append("                    AND SAL_WPAI.POS = WPAI.POS ");
        // sql.append("                    AND SAL_WPAI.TRANS_NO = WPAI.TRANS_NO ");
        // sql.append("                    AND SAL_WPAI.EIGYO_DT = WPAI.EIGYO_DT ");
        // sql.append("                    AND ( ");
        // sql.append("                      SAL_WPAI.COMMAND = '0044' ");
        // sql.append("                      OR SAL_WPAI.COMMAND = '0045'");
        // sql.append("                    ) ");
        // sql.append("                    AND SAL_WPAI.ATYPE = '0301' ");
        // sql.append("                  GROUP BY");
        // sql.append("                    SAL_WPAI.ATYPE");
        // sql.append("                ) > 0 ");
        // sql.append("                AND ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPAI.ATYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    ( ");
        // sql.append("                      SELECT");
        // sql.append("                        ATYPE");
        // sql.append("                        , STORE");
        // sql.append("                        , POS");
        // sql.append("                        , TRANS_NO");
        // sql.append("                        , EIGYO_DT");
        // sql.append("                        , COMMAND ");
        // sql.append("                      FROM");
        // sql.append("                        WK_POS_A_ITEM SAL_WPAI ");
        // sql.append("                      GROUP BY");
        // sql.append("                        STORE");
        // sql.append("                        , POS");
        // sql.append("                        , TRANS_NO");
        // sql.append("                        , ATYPE");
        // sql.append("                        , EIGYO_DT");
        // sql.append("                        , COMMAND");
        // sql.append("                    ) SAL_WPAI ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPAI.STORE = WPAI.STORE ");
        // sql.append("                    AND SAL_WPAI.POS = WPAI.POS ");
        // sql.append("                    AND SAL_WPAI.TRANS_NO = WPAI.TRANS_NO ");
        // sql.append("                    AND SAL_WPAI.EIGYO_DT = WPAI.EIGYO_DT ");
        // sql.append("                    AND ( ");
        // sql.append("                      ( ");
        // sql.append("                        SAL_WPAI.COMMAND <> '0044' ");
        // sql.append("                        AND SAL_WPAI.COMMAND <> '0045'");
        // sql.append("                      ) ");
        // sql.append("                      OR SAL_WPAI.ATYPE <> '0301'");
        // sql.append("                    )");
        // sql.append("                ) = 0 ");
        // sql.append("                THEN '2' ");
        // sql.append("                WHEN ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPAI.ATYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    WK_POS_A_ITEM SAL_WPAI ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPAI.STORE = WPAI.STORE ");
        // sql.append("                    AND SAL_WPAI.POS = WPAI.POS ");
        // sql.append("                    AND SAL_WPAI.TRANS_NO = WPAI.TRANS_NO ");
        // sql.append("                    AND SAL_WPAI.EIGYO_DT = WPAI.EIGYO_DT ");
        // sql.append("                    AND SAL_WPAI.COMMAND = '0043' ");
        // sql.append("                    AND SAL_WPAI.ATYPE = '1011'");
        // sql.append("                ) > 0 ");
        // sql.append("                THEN '3' ");
        // sql.append("                WHEN ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPAI.ATYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    WK_POS_A_ITEM SAL_WPAI ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPAI.STORE = WPAI.STORE ");
        // sql.append("                    AND SAL_WPAI.POS = WPAI.POS ");
        // sql.append("                    AND SAL_WPAI.TRANS_NO = WPAI.TRANS_NO ");
        // sql.append("                    AND SAL_WPAI.EIGYO_DT = WPAI.EIGYO_DT ");
        // sql.append("                    AND ( ");
        // sql.append("                      SAL_WPAI.COMMAND = '0044' ");
        // sql.append("                      OR SAL_WPAI.COMMAND = '0045'");
        // sql.append("                    ) ");
        // sql.append("                    AND SAL_WPAI.ATYPE = '1311' ");
        // sql.append("                  GROUP BY");
        // sql.append("                    SAL_WPAI.ATYPE");
        // sql.append("                ) > 0 ");
        // sql.append("                AND ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPAI.ATYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    ( ");
        // sql.append("                      SELECT");
        // sql.append("                        ATYPE");
        // sql.append("                        , STORE");
        // sql.append("                        , POS");
        // sql.append("                        , TRANS_NO");
        // sql.append("                        , EIGYO_DT");
        // sql.append("                        , COMMAND ");
        // sql.append("                      FROM");
        // sql.append("                        WK_POS_A_ITEM SAL_WPAI ");
        // sql.append("                      GROUP BY");
        // sql.append("                        STORE");
        // sql.append("                        , POS");
        // sql.append("                        , TRANS_NO");
        // sql.append("                        , ATYPE");
        // sql.append("                        , EIGYO_DT");
        // sql.append("                        , COMMAND");
        // sql.append("                    ) SAL_WPAI ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPAI.STORE = WPAI.STORE ");
        // sql.append("                    AND SAL_WPAI.POS = WPAI.POS ");
        // sql.append("                    AND SAL_WPAI.TRANS_NO = WPAI.TRANS_NO ");
        // sql.append("                    AND SAL_WPAI.EIGYO_DT = WPAI.EIGYO_DT ");
        // sql.append("                    AND ( ");
        // sql.append("                      ( ");
        // sql.append("                        SAL_WPAI.COMMAND <> '0044' ");
        // sql.append("                        AND SAL_WPAI.COMMAND <> '0045'");
        // sql.append("                      ) ");
        // sql.append("                      OR SAL_WPAI.ATYPE <> '1311'");
        // sql.append("                    )");
        // sql.append("                ) = 0 ");
        // sql.append("                THEN '4' ");
        // sql.append("                ELSE '1' ");
        // sql.append("                END AS SALES_KB ");
        sql.append("            , TMP_URIAGE.URIAGE_FRG AS SALES_KB ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        sql.append("            FROM");
        sql.append("              ( ");
        sql.append("                SELECT");
        sql.append("                  WPAI.COMMAND");
        sql.append("                  , WPAI.STORE");
        sql.append("                  , WPAI.POS");
        sql.append("                  , WPAI.TRANS_NO");
        sql.append("                  , WPAI.EIGYO_DT");
        sql.append("                  , MAX(WPAI.CASHIER_ID) AS CASHIER_ID ");
        // #5456 2017/6/22 N.katou(S)
        sql.append("                  , SUM(NVL(WPAI.ACTUAL_SELL2,0)) AS ACTUAL_SELL2 ");
        // #5456 2017/6/22 N.katou(E)
        sql.append("                FROM");
        sql.append("                  WK_POS_A_ITEM WPAI ");
        sql.append("                WHERE");
        sql.append("                  ERR_KB IN ('0', '7') ");
        sql.append("                GROUP BY");
        sql.append("                  COMMAND");
        sql.append("                  , STORE");
        sql.append("                  , POS");
        sql.append("                  , TRANS_NO");
        sql.append("                  , EIGYO_DT");
        sql.append("              ) WPAI ");
        sql.append("              LEFT OUTER JOIN ( ");
        sql.append("                SELECT");
        sql.append("                  COMMAND");
        sql.append("                  , STORE");
        sql.append("                  , POS");
        sql.append("                  , TRANS_NO");
        sql.append("                  , EIGYO_DT");
        // #5456 2017/6/22 N.katou(S)
//        sql.append("                  , SUM(ACTUAL_SELL2) AS ACTUAL_SELL2 ");
        sql.append("                  , SUM(NVL(ACTUAL_SELL2,0)) AS CAN_ACTUAL_SELL2 ");
        // #5456 2017/6/22 N.katou(E)
        sql.append("                FROM");
        sql.append("                  WK_POS_A_ITEM ");
        sql.append("                WHERE");
        sql.append("                  ERR_KB IN ('0', '7') ");
        sql.append("                  AND COMMAND = '0043' ");
        sql.append("                  AND ATYPE IN ('0301', '1311') ");
        sql.append("                GROUP BY");
        sql.append("                  COMMAND");
        sql.append("                  , STORE");
        sql.append("                  , POS");
        sql.append("                  , TRANS_NO");
        sql.append("                  , EIGYO_DT");
        sql.append("              ) CAN_A ");
        sql.append("                ON WPAI.COMMAND = CAN_A.COMMAND ");
        sql.append("                AND WPAI.STORE = CAN_A.STORE ");
        sql.append("                AND WPAI.POS = CAN_A.POS ");
        sql.append("                AND WPAI.TRANS_NO = CAN_A.TRANS_NO ");
        sql.append("                AND WPAI.EIGYO_DT = CAN_A.EIGYO_DT");
        // #16388 ADD 2023.07.03 TUNG.LT (S)
        sql.append("                LEFT JOIN ");
        sql.append("                  (SELECT STORE ");
        sql.append("                  , POS ");
        sql.append("                  , TRANS_NO  ");
        sql.append("                  , EIGYO_DT  ");
        sql.append("                  , CASE ");
        sql.append("                       WHEN SUM( DECODE(WPAI.ATYPE,'0301',1,0)) = COUNT(WPAI.ATYPE) ");
        sql.append("                         AND (COMMAND                             = '0044' ");
        sql.append("                         OR COMMAND                               = '0045') ");
        sql.append("                       THEN '2' ");
        sql.append("                       WHEN SUM( DECODE(WPAI.ATYPE,'1011',1,0)) > 0 ");
        sql.append("                         AND COMMAND                              = '0043' ");
        sql.append("                       THEN '3' ");
        sql.append("                       WHEN SUM( DECODE(WPAI.ATYPE,'1311',1,0)) = COUNT(WPAI.ATYPE) ");
        sql.append("                         AND (COMMAND                             = '0044' ");
        sql.append("                         OR COMMAND                               = '0045') ");
        sql.append("                       THEN '4' ");
        sql.append("                       ELSE '1' ");
        sql.append("                    END AS URIAGE_FRG ");
        sql.append("                    FROM WK_POS_A_ITEM WPAI ");
        sql.append("                    WHERE ERR_KB IN ('0', '7') ");
        sql.append("                    GROUP BY ");
        sql.append("                      STORE ");
        sql.append("                      ,POS ");
        sql.append("                      ,TRANS_NO ");
        sql.append("                      ,EIGYO_DT ");
        sql.append("                      ,COMMAND ");
        sql.append("                  ) TMP_URIAGE ");
        sql.append("                ON  TMP_URIAGE.STORE     = WPAI.STORE ");
        sql.append("                AND TMP_URIAGE.POS      = WPAI.POS ");
        sql.append("                AND TMP_URIAGE.TRANS_NO = WPAI.TRANS_NO ");
        sql.append("                AND TMP_URIAGE.EIGYO_DT = WPAI.EIGYO_DT ");
        // #16388 ADD 2023.07.03 TUNG.LT (E)
        sql.append("          ) A   ");
        sql.append("          LEFT OUTER JOIN ( ");
        sql.append("            SELECT");
        sql.append("              COMMAND");
        sql.append("              , STORE");
        sql.append("              , POS");
        sql.append("              , TRANS_NO");
        sql.append("              , EIGYO_DT");
        sql.append("              , TORI_TIME");
        sql.append("              , TOT_AMT");
        sql.append("              , TAX_AMT");
        sql.append("              , NVL(REC_TOT,0) + NVL(NTAXABLE,0) AS ZEINUKI_VL ");
        sql.append("            FROM");
        sql.append("              WK_POS_B_TOTAL");
        // #5739 2017/8/01 N.katou(S)
        sql.append("            WHERE");
        sql.append("              ERR_KB = '0' ");
        // #5739 2017/8/01 N.katou(E)
        sql.append("          ) B ");
        sql.append("            ON A.COMMAND = B.COMMAND ");
        sql.append("            AND A.STORE = B.STORE ");
        sql.append("            AND A.POS = B.POS ");
        sql.append("            AND A.TRANS_NO = B.TRANS_NO ");
        sql.append("            AND A.EIGYO_DT = B.EIGYO_DT ");
        sql.append("          LEFT OUTER JOIN ( ");
        sql.append("            SELECT");
        sql.append("              COMMAND");
        sql.append("              , STORE");
        sql.append("              , POS");
        sql.append("              , TRANS_NO");
        sql.append("              , EIGYO_DT");
        sql.append("              , MAX(CASHIER_ID) AS CASHIER_ID ");
        sql.append("            FROM");
        sql.append("              WK_POS_C_PAYMENT ");
        // #5739 2017/8/01 N.katou(S)
        sql.append("            WHERE");
        sql.append("              ERR_KB = '0' ");
        // #5739 2017/8/01 N.katou(E)
        sql.append("            GROUP BY");
        sql.append("              COMMAND");
        sql.append("              , STORE");
        sql.append("              , POS");
        sql.append("              , TRANS_NO");
        sql.append("              , EIGYO_DT");
        sql.append("          ) C ");
        sql.append("            ON A.COMMAND = C.COMMAND ");
        sql.append("            AND A.STORE = C.STORE ");
        sql.append("            AND A.POS = C.POS ");
        sql.append("            AND A.TRANS_NO = C.TRANS_NO ");
        sql.append("            AND A.EIGYO_DT = C.EIGYO_DT ");
        sql.append("            UNION ALL ");
        sql.append("        SELECT");
        sql.append("          C.COMMAND AS COMMAND_CD");
        sql.append("          , C.STORE AS TENPO_CD");
        sql.append("          , C.POS AS REGI_RB");
        sql.append("          , C.TRANS_NO AS TERMINAL_RB");
        sql.append("          , C.EIGYO_DT");
        sql.append("          , C.CASHIER_ID");
        sql.append("          , B.TORI_TIME AS SALES_TS");
        sql.append("          , C.SALES_KB");
        sql.append("          , B.TOT_AMT AS POS_TOT_VL");
        sql.append("          , NULL AS CANCEL_TOT_VL");
        sql.append("          , '9' AS MEISAI_KB");
        sql.append("          , '0' AS TEISEI_KB");
        sql.append("          , NULL AS SYOUKEI_VL");
        // #5456 2017/6/22 N.katou(S)
//        sql.append("          , NULL AS RECEIPT_NEBIKI_VL");
        sql.append("          , - B.TOT_AMT AS RECEIPT_NEBIKI_VL");
        // #5456 2017/6/22 N.katou(E)
        sql.append("          , B.TOT_AMT AS URIAGE_VL");
        sql.append("          , B.TAX_AMT AS ZEI_TOT_VL");
        sql.append("          , B.ZEINUKI_VL ");
        sql.append("        FROM");
        sql.append("          ( ");
        sql.append("            SELECT");
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("              COMMAND");
        // sql.append("              , STORE");
        // sql.append("              , POS");
        // sql.append("              , TRANS_NO");
        // sql.append("              , EIGYO_DT");
        // sql.append("              , MAX(CASHIER_ID) AS CASHIER_ID");
        // sql.append("              , CASE ");
        // sql.append("                WHEN ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPCP.CTYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    WK_POS_C_PAYMENT SAL_WPCP ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPCP.STORE = WPCP.STORE ");
        // sql.append("                    AND SAL_WPCP.POS = WPCP.POS ");
        // sql.append("                    AND SAL_WPCP.TRANS_NO = WPCP.TRANS_NO ");
        // sql.append("                    AND SAL_WPCP.EIGYO_DT = WPCP.EIGYO_DT ");
        // sql.append("                    AND ( ");
        // sql.append("                      SAL_WPCP.COMMAND = '0044' ");
        // sql.append("                      OR SAL_WPCP.COMMAND = '0045'");
        // sql.append("                    ) ");
        // sql.append("                    AND SAL_WPCP.CTYPE = '0312' ");
        // sql.append("                  GROUP BY");
        // sql.append("                    SAL_WPCP.CTYPE");
        // sql.append("                ) > 0 ");
        // sql.append("                AND ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPCP.CTYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    ( ");
        // sql.append("                      SELECT");
        // sql.append("                        CTYPE");
        // sql.append("                        , STORE");
        // sql.append("                        , POS");
        // sql.append("                        , TRANS_NO");
        // sql.append("                        , EIGYO_DT");
        // sql.append("                        , COMMAND ");
        // sql.append("                      FROM");
        // sql.append("                        WK_POS_C_PAYMENT SAL_WPCP ");
        // sql.append("                      GROUP BY");
        // sql.append("                        STORE");
        // sql.append("                        , POS");
        // sql.append("                        , TRANS_NO");
        // sql.append("                        , CTYPE");
        // sql.append("                        , EIGYO_DT");
        // sql.append("                        , COMMAND");
        // sql.append("                    ) SAL_WPCP ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPCP.STORE = WPCP.STORE ");
        // sql.append("                    AND SAL_WPCP.POS = WPCP.POS ");
        // sql.append("                    AND SAL_WPCP.TRANS_NO = WPCP.TRANS_NO ");
        // sql.append("                    AND SAL_WPCP.EIGYO_DT = WPCP.EIGYO_DT ");
        // sql.append("                    AND ( ");
        // sql.append("                      ( ");
        // sql.append("                        SAL_WPCP.COMMAND <> '0044' ");
        // sql.append("                        AND SAL_WPCP.COMMAND <> '0045'");
        // sql.append("                      ) ");
        // sql.append("                      OR SAL_WPCP.CTYPE <> '0312'");
        // sql.append("                    )");
        // sql.append("                ) = 0 ");
        // sql.append("                THEN '2' ");
        // sql.append("                WHEN ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPCP.CTYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    WK_POS_C_PAYMENT SAL_WPCP ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPCP.STORE = WPCP.STORE ");
        // sql.append("                    AND SAL_WPCP.POS = WPCP.POS ");
        // sql.append("                    AND SAL_WPCP.TRANS_NO = WPCP.TRANS_NO ");
        // sql.append("                    AND SAL_WPCP.EIGYO_DT = WPCP.EIGYO_DT ");
        // sql.append("                    AND SAL_WPCP.COMMAND = '0043' ");
        // sql.append("                    AND SAL_WPCP.CTYPE = '1011'");
        // sql.append("                ) > 0 ");
        // sql.append("                THEN '3' ");
        // sql.append("                WHEN ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPCP.CTYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    WK_POS_C_PAYMENT SAL_WPCP ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPCP.STORE = WPCP.STORE ");
        // sql.append("                    AND SAL_WPCP.POS = WPCP.POS ");
        // sql.append("                    AND SAL_WPCP.TRANS_NO = WPCP.TRANS_NO ");
        // sql.append("                    AND SAL_WPCP.EIGYO_DT = WPCP.EIGYO_DT ");
        // sql.append("                    AND ( ");
        // sql.append("                      SAL_WPCP.COMMAND = '0044' ");
        // sql.append("                      OR SAL_WPCP.COMMAND = '0045'");
        // sql.append("                    ) ");
        // sql.append("                    AND SAL_WPCP.CTYPE = '1311' ");
        // sql.append("                  GROUP BY");
        // sql.append("                    SAL_WPCP.CTYPE");
        // sql.append("                ) > 0 ");
        // sql.append("                AND ( ");
        // sql.append("                  SELECT");
        // sql.append("                    COUNT(SAL_WPCP.CTYPE) ");
        // sql.append("                  FROM");
        // sql.append("                    ( ");
        // sql.append("                      SELECT");
        // sql.append("                        CTYPE");
        // sql.append("                        , STORE");
        // sql.append("                        , POS");
        // sql.append("                        , TRANS_NO");
        // sql.append("                        , EIGYO_DT");
        // sql.append("                        , COMMAND ");
        // sql.append("                      FROM");
        // sql.append("                        WK_POS_C_PAYMENT SAL_WPCP ");
        // sql.append("                      GROUP BY");
        // sql.append("                        STORE");
        // sql.append("                        , POS");
        // sql.append("                        , TRANS_NO");
        // sql.append("                        , CTYPE");
        // sql.append("                        , EIGYO_DT");
        // sql.append("                        , COMMAND");
        // sql.append("                    ) SAL_WPCP ");
        // sql.append("                  WHERE");
        // sql.append("                    SAL_WPCP.STORE = WPCP.STORE ");
        // sql.append("                    AND SAL_WPCP.POS = WPCP.POS ");
        // sql.append("                    AND SAL_WPCP.TRANS_NO = WPCP.TRANS_NO ");
        // sql.append("                    AND SAL_WPCP.EIGYO_DT = WPCP.EIGYO_DT ");
        // sql.append("                    AND ( ");
        // sql.append("                      ( ");
        // sql.append("                        SAL_WPCP.COMMAND <> '0044' ");
        // sql.append("                        AND SAL_WPCP.COMMAND <> '0045'");
        // sql.append("                      ) ");
        // sql.append("                      OR SAL_WPCP.CTYPE <> '1311'");
        // sql.append("                    )");
        // sql.append("                ) = 0 ");
        // sql.append("                THEN '4' ");
        // sql.append("                ELSE '1' ");
        // sql.append("                END AS SALES_KB ");
        sql.append("              WPCP.COMMAND");
        sql.append("              , WPCP.STORE");
        sql.append("              , WPCP.POS");
        sql.append("              , WPCP.TRANS_NO");
        sql.append("              , WPCP.EIGYO_DT");
        sql.append("              , MAX(CASHIER_ID) AS CASHIER_ID ");
        sql.append("              , TMP_URIAGE.URIAGE_FRG AS SALES_KB ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        sql.append("            FROM");
        sql.append("              WK_POS_C_PAYMENT WPCP ");
        // #16388 ADD 2023.07.03 TUNG.LT (S)
        sql.append("            LEFT JOIN ");
        sql.append("              (SELECT STORE ");
        sql.append("                , POS ");
        sql.append("                , TRANS_NO ");
        sql.append("                , EIGYO_DT ");
        sql.append("                , CASE ");
        sql.append("                    WHEN SUM( DECODE(WPCI.CTYPE,'0312',1,0)) = COUNT(WPCI.CTYPE) ");
        sql.append("                       AND (COMMAND                             = '0044' ");
        sql.append("                       OR COMMAND                               = '0045') ");
        sql.append("                    THEN '2' ");
        sql.append("                    WHEN SUM( DECODE(WPCI.CTYPE,'1011',1,0)) > 0 ");
        sql.append("                       AND COMMAND                              = '0043' ");
        sql.append("                    THEN '3' ");
        sql.append("                    WHEN SUM( DECODE(WPCI.CTYPE,'1311',1,0)) = COUNT(WPCI.CTYPE) ");
        sql.append("                       AND (COMMAND                             = '0044' ");
        sql.append("                       OR COMMAND                               = '0045') ");
        sql.append("                    THEN '4' ");
        sql.append("                    ELSE '1' ");
        sql.append("                END AS URIAGE_FRG ");
        sql.append("              FROM WK_POS_C_PAYMENT WPCI ");
        sql.append("              GROUP BY STORE ");
        sql.append("                , POS ");
        sql.append("                , TRANS_NO ");
        sql.append("                , EIGYO_DT ");
        sql.append("                , COMMAND ");
        sql.append("              ) TMP_URIAGE ");
        sql.append("            ON  TMP_URIAGE.STORE     = WPCP.STORE ");
        sql.append("            AND TMP_URIAGE.POS      = WPCP.POS ");
        sql.append("            AND TMP_URIAGE.TRANS_NO = WPCP.TRANS_NO ");
        sql.append("            AND TMP_URIAGE.EIGYO_DT = WPCP.EIGYO_DT ");
        // #16388 ADD 2023.07.03 TUNG.LT (E)
        sql.append("            WHERE");
        // #5739 2017/8/01 N.katou(S)
//        sql.append("              NOT EXISTS ( ");
        sql.append("              WPCP.ERR_KB = '0' ");
        sql.append("              AND NOT EXISTS ( ");
        // #5739 2017/8/01 N.katou(E)
        sql.append("                SELECT");
        sql.append("                  STORE ");
        sql.append("                FROM");
        sql.append("                  WK_POS_A_ITEM WPAI ");
        sql.append("                WHERE");
        sql.append("                  ERR_KB IN ('0', '7') ");
        sql.append("                  AND WPAI.COMMAND = WPCP.COMMAND ");
        sql.append("                  AND WPAI.STORE = WPCP.STORE ");
        sql.append("                  AND WPAI.POS = WPCP.POS ");
        sql.append("                  AND WPAI.TRANS_NO = WPCP.TRANS_NO ");
        sql.append("                  AND WPAI.EIGYO_DT = WPCP.EIGYO_DT");
        sql.append("              ) ");
        sql.append("            GROUP BY");
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("              COMMAND");
        // sql.append("              , STORE");
        // sql.append("              , POS");
        // sql.append("              , TRANS_NO");
        // sql.append("              , EIGYO_DT");
        sql.append("                WPCP.COMMAND ");
        sql.append("              , WPCP.STORE ");
        sql.append("              , WPCP.POS ");
        sql.append("              , WPCP.TRANS_NO ");
        sql.append("              , WPCP.EIGYO_DT ");
        sql.append("              , TMP_URIAGE.URIAGE_FRG ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        sql.append("          ) C  ");
        sql.append("          LEFT OUTER JOIN ( ");
        sql.append("            SELECT");
        sql.append("              COMMAND");
        sql.append("              , STORE");
        sql.append("              , POS");
        sql.append("              , TRANS_NO");
        sql.append("              , EIGYO_DT");
        sql.append("              , TORI_TIME");
        sql.append("              , TOT_AMT");
        sql.append("              , TAX_AMT");
        sql.append("              , NVL(REC_TOT,0) + NVL(NTAXABLE,0) AS ZEINUKI_VL ");
        sql.append("            FROM");
        sql.append("              WK_POS_B_TOTAL");
        // #5739 2017/8/01 N.katou(S)
        sql.append("            WHERE");
        sql.append("              ERR_KB = '0' ");
        // #5739 2017/8/01 N.katou(E)
        sql.append("          ) B ");
        sql.append("            ON C.COMMAND = B.COMMAND ");
        sql.append("            AND C.STORE = B.STORE ");
        sql.append("            AND C.POS = B.POS ");
        sql.append("            AND C.TRANS_NO = B.TRANS_NO ");
        sql.append("            AND C.EIGYO_DT = B.EIGYO_DT");
        sql.append("      )");
        sql.append("  ) ");

        return sql.toString();
                
    }
    
    /**
     * 店別レシート別データ作成(明細)を取得SQL
     * @return SQL
     */
    private String getInsertDtTenReceiptMSQL(){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT  ");
        sql.append("    INTO DT_TEN_RECEIPT_M   ");
        sql.append("    (  ");
        sql.append("COMP_CD ");
        sql.append(", COMMAND_CD    ");
        sql.append(", TENPO_CD  ");
        sql.append(", REGI_RB   ");
        sql.append(", TERMINAL_RB   ");
        sql.append(", EIGYO_DT  ");
        sql.append(", SEQ_RB    ");
        sql.append(", SALES_TYPE    ");
        sql.append(", SYOHIN_CD ");
        sql.append(", SYOHIN_NA ");
        sql.append(", TAX_RT    ");
        sql.append(", TEIKAN_FG ");
        sql.append(", SURYO_QT  ");
        sql.append(", HANBAI_WEIGHT_QT  ");
        sql.append(", BAITANKA_VL   ");
        sql.append(", ZEINUKI_VL    ");
        sql.append(", ZEIKOMI_VL    ");
        sql.append(", INSERT_USER_ID    ");
        sql.append(", INSERT_TS ");
        sql.append(", UPDATE_USER_ID    ");
        sql.append(", UPDATE_TS  )   ");
        sql.append("SELECT  ");
        sql.append("? AS COMP_CD    ");
        sql.append(", COMMAND    ");
        sql.append(", '00'||STORE  ");
        sql.append(", POS   ");
        sql.append(", TRANS_NO   ");
        sql.append(", TRIM(EIGYO_DT)  ");
        sql.append(", ODR_LINE_IDX    ");
        sql.append(", ATYPE    ");
        sql.append(", SKU ");
        sql.append(", ITEM_NAME_RECEIPT ");
        // #5455 2017/6/22 N.katou(S)
//      sql.append(", GST_TAX    ");
        sql.append(", GST_TAX * 100 AS GST_TAX  ");
        // #5455 2017/6/22 N.katou(E)
        sql.append(", ITEM_WEIGH ");
        sql.append(", QTY  ");
        sql.append(", WEIGHT_SOLD  ");
        sql.append(", REG_SELL_WOT   ");
        sql.append(", ACTUAL_SELL_WOT2    ");
        sql.append(", ACTUAL_SELL2    ");
        sql.append(", ? AS INSERT_USER_ID   ");
        sql.append(", ? AS INSERT_TS    ");
        sql.append(", ? AS UPDATE_USER_ID   ");
        sql.append(", ? AS UPDATE_TS    ");
        sql.append("    FROM    ");
        sql.append("WK_POS_A_ITEM   ");
        sql.append("    WHERE   ");
        sql.append("ERR_KB IN ('0', '7')    ");

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
