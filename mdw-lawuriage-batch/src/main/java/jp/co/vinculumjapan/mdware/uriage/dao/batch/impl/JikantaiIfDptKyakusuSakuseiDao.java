package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *
 * <p>タイトル: JikantaiIfDptKyakusuSakuseiDao.java クラス</p>
 * <p>説明　: IF時間帯DPT客数ワーク作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.16) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.01 (2013.11.07) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №023
 * @Version 3.02 (2016.12.28) T.Kamei FIVI対応 #3403
 * @Version 3.03 (2017.03.15) J.Endo  FIVI対応 #3331
 * @Version 3.04 (2017.08.07) J.Endo  FIVI対応 #5781
 * @Version 3.05 (2021.09.14) SIEU.D #6339
 *
 */
public class JikantaiIfDptKyakusuSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "IF時間帯DPT客数ワーク作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "IF時間帯DPT客数ワーク";
    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_IF_JIKANTAI_DPT_KYKUSU";

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // IF時間帯DPT客数ワークを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 部門精算データからIF時間帯DPT客数ワークを登録する
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkIfJikantaiTanpinUriageInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, BATCH_DT);
            // #3331 2017.03.15 J.Endo ADD (S)
            preparedStatementExIns.setString(7, COMP_CD);
            // #5781 2017.08.07 J.Endo  MOD (S)
            //preparedStatementExIns.setString(8, BATCH_DT);
            preparedStatementExIns.setString(8, COMP_CD);
            preparedStatementExIns.setString(9, BATCH_DT);
            preparedStatementExIns.setString(10, COMP_CD);
            // #5781 2017.08.07 J.Endo  MOD (E)
            // #3331 2017.03.15 J.Endo ADD (E)
            // #3403 2016.12.28 T.Kamei DEL (S)
            //preparedStatementExIns.setString(7, COMP_CD);
            //preparedStatementExIns.setString(8, BATCH_DT);
            // #3403 2016.12.28 T.Kamei DEL (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementDel != null) {
                    preparedStatementDel.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 部門精算データからIF時間帯DPT客数ワークを登録するSQLを取得する
     *
     * @return IF時間帯DPT客数ワーク登録SQL
     */
    private String getWkIfJikantaiTanpinUriageInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_IF_JIKANTAI_DPT_KYKUSU( ");
        sql.append("     COMP_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,YR ");
        sql.append("    ,MN ");
        sql.append("    ,DD ");
        sql.append("    ,JIGYOBU_CD ");
        sql.append("    ,GYOSYU_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME ");
        sql.append("    ,TENANT_KB ");
        sql.append("    ,TENANT_CD ");
        sql.append("    ,URIAGE_KB ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,URIAGE_GENKA_VL ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,NEBIKI_QT ");
        sql.append("    ,KAKO_VL ");
        sql.append("    ,KAKO_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HENPIN_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("    ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        // #3331 2017.03.15 J.Endo MOD (S)
        //sql.append("SELECT ");
        //sql.append("     WJDK.COMP_CD ");
        //sql.append("    ,WJDK.TENPO_CD ");
        //sql.append("    ,WJDK.YR ");
        //sql.append("    ,WJDK.MN ");
        //sql.append("    ,WJDK.DD ");
        //sql.append("    ,WJDK.JIGYOBU_CD ");
        //sql.append("    ,WJDK.GYOSYU_CD ");
        //sql.append("    ,WJDK.BUNRUI1_CD ");
        //sql.append("    ,WJDK.TIME_TM ");
        //sql.append("    ,WJDK.TENANT_KB ");
        //sql.append("    ,WJDK.TENANT_CD ");
        //sql.append("    ,WJDK.URIAGE_KB ");
        //sql.append("    ,WJDK.URIAGE_VL - COALESCE(ZJDK.URIAGE_VL , 0) ");
        //sql.append("    ,WJDK.URIAGE_GENKA_VL - COALESCE(ZJDK.URIAGE_GENKA_VL , 0) ");
        //sql.append("    ,WJDK.URIAGE_QT - COALESCE(ZJDK.URIAGE_QT , 0) ");
        //sql.append("    ,WJDK.NEBIKI_VL - COALESCE(ZJDK.NEBIKI_VL , 0) ");
        //sql.append("    ,WJDK.NEBIKI_QT - COALESCE(ZJDK.NEBIKI_QT , 0) ");
        //sql.append("    ,WJDK.KAKO_VL - COALESCE(ZJDK.KAKO_VL , 0) ");
        //sql.append("    ,WJDK.KAKO_QT - COALESCE(ZJDK.KAKO_QT , 0) ");
        //sql.append("    ,WJDK.HAIKI_VL - COALESCE(ZJDK.HAIKI_VL , 0) ");
        //sql.append("    ,WJDK.HAIKI_QT - COALESCE(ZJDK.HAIKI_QT , 0) ");
        //sql.append("    ,WJDK.HENPIN_VL - COALESCE(ZJDK.HENPIN_VL , 0) ");
        //sql.append("    ,WJDK.HENPIN_QT - COALESCE(ZJDK.HENPIN_QT , 0) ");
        //sql.append("    ,WJDK.BUNRUI1_KYAKU_QT - COALESCE(ZJDK.BUNRUI1_KYAKU_QT , 0) ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("FROM ");
        //sql.append("    WK_JIKANTAI_DPT_KYKUSU WJDK ");
        //sql.append("    LEFT JOIN ");
        //sql.append("        ( ");
        //sql.append("        SELECT ");
        //// #3403 2016.12.28 T.Kamei MOD (S)
        ////sql.append("             ZJDK.COMP_CD ");
        ////sql.append("            ,ZJDK.TENPO_CD ");
        ////sql.append("            ,ZJDK.YR ");
        ////sql.append("            ,ZJDK.MN ");
        ////sql.append("            ,ZJDK.DD ");
        ////sql.append("            ,ZJDK.BUNRUI1_CD ");
        ////sql.append("            ,ZJDK.URIAGE_VL ");
        ////sql.append("            ,ZJDK.URIAGE_GENKA_VL ");
        ////sql.append("            ,ZJDK.URIAGE_QT ");
        ////sql.append("            ,ZJDK.NEBIKI_VL ");
        ////sql.append("            ,ZJDK.NEBIKI_QT ");
        ////sql.append("            ,ZJDK.KAKO_VL ");
        ////sql.append("            ,ZJDK.KAKO_QT ");
        ////sql.append("            ,ZJDK.HAIKI_VL ");
        ////sql.append("            ,ZJDK.HAIKI_QT ");
        ////sql.append("            ,ZJDK.HENPIN_VL ");
        ////sql.append("            ,ZJDK.HENPIN_QT ");
        ////sql.append("            ,ZJDK.BUNRUI1_KYAKU_QT ");
        ////sql.append("        FROM ");
        ////sql.append("            ZEN_JIKANTAI_DPT_KYKUSU ZJDK ");
        ////sql.append("        WHERE ");
        ////sql.append("            ZJDK.COMP_CD                    = ? AND ");
        ////sql.append("            ZJDK.YR || ZJDK.MN || ZJDK.DD   = ? ");
        //sql.append("             ZJDK1.COMP_CD ");
        //sql.append("            ,ZJDK1.TENPO_CD ");
        //sql.append("            ,ZJDK1.YR ");
        //sql.append("            ,ZJDK1.MN ");
        //sql.append("            ,ZJDK1.DD ");
        //sql.append("            ,ZJDK1.BUNRUI1_CD ");
        //sql.append("            ,ZJDK1.URIAGE_VL ");
        //sql.append("            ,ZJDK1.URIAGE_GENKA_VL ");
        //sql.append("            ,ZJDK1.URIAGE_QT ");
        //sql.append("            ,ZJDK1.NEBIKI_VL ");
        //sql.append("            ,ZJDK1.NEBIKI_QT ");
        //sql.append("            ,ZJDK1.KAKO_VL ");
        //sql.append("            ,ZJDK1.KAKO_QT ");
        //sql.append("            ,ZJDK1.HAIKI_VL ");
        //sql.append("            ,ZJDK1.HAIKI_QT ");
        //sql.append("            ,ZJDK1.HENPIN_VL ");
        //sql.append("            ,ZJDK1.HENPIN_QT ");
        //sql.append("            ,ZJDK1.BUNRUI1_KYAKU_QT ");
        //sql.append("            ,ZJDK2.TIME_TM ");
        //sql.append("        FROM ");
        //sql.append("            ZEN_JIKANTAI_DPT_KYKUSU ZJDK1 ");
        //sql.append("        INNER JOIN ");
        //sql.append("            ( ");
        //sql.append("            SELECT ");
        //sql.append("                 ZJDK.COMP_CD ");
        //sql.append("                ,ZJDK.TENPO_CD ");
        //sql.append("                ,ZJDK.YR ");
        //sql.append("                ,ZJDK.MN ");
        //sql.append("                ,ZJDK.DD ");
        //sql.append("                ,ZJDK.BUNRUI1_CD ");
        //sql.append("                ,MAX(ZJDK.TIME_TM) AS MAX_TIME_TM ");
        //sql.append("                ,WJDK.TIME_TM ");
        //sql.append("            FROM ");
        //sql.append("                ZEN_JIKANTAI_DPT_KYKUSU ZJDK ");
        //sql.append("            INNER JOIN ");
        //sql.append("                WK_JIKANTAI_DPT_KYKUSU WJDK ");
        //sql.append("            ON ");
        //sql.append("                ZJDK.COMP_CD    = WJDK.COMP_CD    AND ");
        //sql.append("                ZJDK.TENPO_CD   = WJDK.TENPO_CD   AND ");
        //sql.append("                ZJDK.YR         = WJDK.YR         AND ");
        //sql.append("                ZJDK.MN         = WJDK.MN         AND ");
        //sql.append("                ZJDK.DD         = WJDK.DD         AND ");
        //sql.append("                ZJDK.BUNRUI1_CD = WJDK.BUNRUI1_CD ");
        //sql.append("            WHERE ");
        //sql.append("                ZJDK.TIME_TM < WJDK.TIME_TM ");
        //sql.append("            GROUP BY ");
        //sql.append("                 ZJDK.COMP_CD ");
        //sql.append("                ,ZJDK.TENPO_CD ");
        //sql.append("                ,ZJDK.YR ");
        //sql.append("                ,ZJDK.MN ");
        //sql.append("                ,ZJDK.DD ");
        //sql.append("                ,ZJDK.BUNRUI1_CD ");
        //sql.append("                ,WJDK.TIME_TM ");
        //sql.append("            ) ZJDK2 ");
        //sql.append("        ON ");
        //sql.append("            ZJDK1.COMP_CD    = ZJDK2.COMP_CD     AND ");
        //sql.append("            ZJDK1.TENPO_CD   = ZJDK2.TENPO_CD    AND ");
        //sql.append("            ZJDK1.YR         = ZJDK2.YR          AND ");
        //sql.append("            ZJDK1.MN         = ZJDK2.MN          AND ");
        //sql.append("            ZJDK1.DD         = ZJDK2.DD          AND ");
        //sql.append("            ZJDK1.BUNRUI1_CD = ZJDK2.BUNRUI1_CD  AND ");
        //sql.append("            ZJDK1.TIME_TM    = ZJDK2.MAX_TIME_TM ");
        //// #3403 2016.12.28 T.Kamei MOD (E)
        //sql.append("        ) ZJDK ");
        //sql.append("    ON ");
        //sql.append("        WJDK.COMP_CD    = ZJDK.COMP_CD      AND ");
        //sql.append("        WJDK.YR         = ZJDK.YR           AND ");
        //sql.append("        WJDK.MN         = ZJDK.MN           AND ");
        //sql.append("        WJDK.DD         = ZJDK.DD           AND ");
        //sql.append("        WJDK.TENPO_CD   = ZJDK.TENPO_CD     AND ");
        //// #3403 2016.12.28 T.Kamei MOD (S)
        //sql.append("        WJDK.BUNRUI1_CD = ZJDK.BUNRUI1_CD   AND ");
        //sql.append("        WJDK.TIME_TM    = ZJDK.TIME_TM ");
        //// #3403 2016.12.28 T.Kamei MOD (E)
        //sql.append("WHERE ");
        //sql.append("    WJDK.COMP_CD                    = ? AND ");
        //sql.append("    WJDK.YR || WJDK.MN || WJDK.DD   = ? ");
        sql.append("SELECT ");
        sql.append("    COMP_CD, ");
        sql.append("    TENPO_CD, ");
        sql.append("    YR, ");
        sql.append("    MN, ");
        sql.append("    DD, ");
        sql.append("    JIGYOBU_CD, ");
        sql.append("    GYOSYU_CD, ");
        sql.append("    BUNRUI1_CD, ");
        sql.append("    TMP_TIME_TAI AS TIME_TM, "); // 営業速報は１時間単位なので時間帯を設定
        sql.append("    TENANT_KB, ");
        sql.append("    TENANT_CD, ");
        sql.append("    URIAGE_KB, ");
        sql.append("    PRE_URIAGE_VL, ");
        sql.append("    PRE_URIAGE_GENKA_VL, ");
        sql.append("    PRE_URIAGE_QT, ");
        sql.append("    PRE_NEBIKI_VL, ");
        sql.append("    PRE_NEBIKI_QT, ");
        sql.append("    PRE_KAKO_VL, ");
        sql.append("    PRE_KAKO_QT, ");
        sql.append("    PRE_HAIKI_VL, ");
        sql.append("    PRE_HAIKI_QT, ");
        sql.append("    PRE_HENPIN_VL, ");
        sql.append("    PRE_HENPIN_QT, ");
        sql.append("    PRE_BUNRUI1_KYAKU_QT, ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("    PRE_ARARI_VL, ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ? ");
        sql.append("FROM ( ");
        sql.append("    SELECT ");
        sql.append("        CASE WHEN LAG(TMP_OUTPUT_FG) OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM) = '1' ");
        sql.append("            THEN '1' "); // 直前のレコードが出力対象の場合は出力フラグを出力対象(1)にする
        sql.append("            ELSE TMP_OUTPUT_FG ");
        sql.append("        END TMP_OUTPUT_FG, ");
        sql.append("        TMP_TIME_TAI, ");
        sql.append("        COMP_CD, ");
        sql.append("        TENPO_CD, ");
        sql.append("        YR, ");
        sql.append("        MN, ");
        sql.append("        DD, ");
        sql.append("        JIGYOBU_CD, ");
        sql.append("        GYOSYU_CD, ");
        sql.append("        BUNRUI1_CD, ");
        sql.append("        TIME_TM, ");
        sql.append("        TENANT_KB, ");
        sql.append("        TENANT_CD, ");
        sql.append("        URIAGE_KB, "); // ↓有効データで時系列に並んでいるため直前との差を算出
        sql.append("        URIAGE_VL        - NVL(LAG(URIAGE_VL)        OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_URIAGE_VL, ");
        sql.append("        URIAGE_GENKA_VL  - NVL(LAG(URIAGE_GENKA_VL)  OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_URIAGE_GENKA_VL, ");
        sql.append("        URIAGE_QT        - NVL(LAG(URIAGE_QT)        OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_URIAGE_QT, ");
        sql.append("        NEBIKI_VL        - NVL(LAG(NEBIKI_VL)        OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_NEBIKI_VL, ");
        sql.append("        NEBIKI_QT        - NVL(LAG(NEBIKI_QT)        OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_NEBIKI_QT, ");
        sql.append("        KAKO_VL          - NVL(LAG(KAKO_VL)          OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_KAKO_VL, ");
        sql.append("        KAKO_QT          - NVL(LAG(KAKO_QT)          OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_KAKO_QT, ");
        sql.append("        HAIKI_VL         - NVL(LAG(HAIKI_VL)         OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_HAIKI_VL, ");
        sql.append("        HAIKI_QT         - NVL(LAG(HAIKI_QT)         OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_HAIKI_QT, ");
        sql.append("        HENPIN_VL        - NVL(LAG(HENPIN_VL)        OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_HENPIN_VL, ");
        sql.append("        HENPIN_QT        - NVL(LAG(HENPIN_QT)        OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_HENPIN_QT, ");
        sql.append("        BUNRUI1_KYAKU_QT - NVL(LAG(BUNRUI1_KYAKU_QT) OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("       ,ARARI_VL          - NVL(LAG(ARARI_VL) OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD ORDER BY TIME_TM),0) AS PRE_ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    FROM ( ");
        sql.append("        SELECT ");
        sql.append("            TMP_OUTPUT_FG, ");
        sql.append("            TMP_TIME_TAI, ");
        sql.append("            ROW_NUMBER() OVER(PARTITION BY COMP_CD, TENPO_CD, YR, MN, DD, BUNRUI1_CD, TMP_TIME_TAI ORDER BY TIME_TM DESC) AS TMP_TARGET_NM, "); // 時間帯単位の時系列順で先頭を抽出するため連番を付加(9:30=2,10:00=1[1:抽出対象])
        sql.append("            COMP_CD, ");
        sql.append("            TENPO_CD, ");
        sql.append("            YR, ");
        sql.append("            MN, ");
        sql.append("            DD, ");
        sql.append("            JIGYOBU_CD, ");
        sql.append("            GYOSYU_CD, ");
        sql.append("            BUNRUI1_CD, ");
        sql.append("            TIME_TM, ");
        sql.append("            TENANT_KB, ");
        sql.append("            TENANT_CD, ");
        sql.append("            URIAGE_KB, ");
        sql.append("            URIAGE_VL, ");
        sql.append("            URIAGE_GENKA_VL, ");
        sql.append("            URIAGE_QT, ");
        sql.append("            NEBIKI_VL, ");
        sql.append("            NEBIKI_QT, ");
        sql.append("            KAKO_VL, ");
        sql.append("            KAKO_QT, ");
        sql.append("            HAIKI_VL, ");
        sql.append("            HAIKI_QT, ");
        sql.append("            HENPIN_VL, ");
        sql.append("            HENPIN_QT, ");
        sql.append("            BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("            ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("        FROM ( ");
        sql.append("            SELECT ");
        sql.append("                '0' AS TMP_OUTPUT_FG, "); // 0:前回のデータは時系列順で直前が出力対象の場合のみ出力対象
        // #3331 2017.04.07 J.Endo MOD (S)
        //sql.append("                SUBSTR(LPAD(TIME_TM-1,4,'0'),1,2) AS TMP_TIME_TAI, "); // 時間帯から処理時間帯を取得(09:01～10:00→0900「9時の時間帯のデータ」)
        sql.append("                SUBSTR(TIME_TM,1,2) AS TMP_TIME_TAI, "); // 時間帯から処理時間帯を取得(09:01～10:00→0900「9時の時間帯のデータ」)
        // #3331 2017.04.07 J.Endo MOD (E)
        sql.append("                COMP_CD, ");
        sql.append("                TENPO_CD, ");
        sql.append("                YR, ");
        sql.append("                MN, ");
        sql.append("                DD, ");
        sql.append("                JIGYOBU_CD, ");
        sql.append("                GYOSYU_CD, ");
        sql.append("                BUNRUI1_CD, ");
        // #3331 2017.04.07 J.Endo MOD (S)
        //sql.append("                TIME_TM, ");
        sql.append("                SUBSTR(TIME_TM,1,2)||'31' AS TIME_TM, "); // ZENの時間帯が10時の場合、WKが00分はWKから取得(WK[11:00 > ZEN[10:31]])、WKが30分はWKを破棄(WK[10:30] < ZEN[10:31])
        // #3331 2017.04.07 J.Endo MOD (E)
        sql.append("                TENANT_KB, ");
        sql.append("                TENANT_CD, ");
        sql.append("                URIAGE_KB, ");
        sql.append("                URIAGE_VL, ");
        sql.append("                URIAGE_GENKA_VL, ");
        sql.append("                URIAGE_QT, ");
        sql.append("                NEBIKI_VL, ");
        sql.append("                NEBIKI_QT, ");
        sql.append("                KAKO_VL, ");
        sql.append("                KAKO_QT, ");
        sql.append("                HAIKI_VL, ");
        sql.append("                HAIKI_QT, ");
        sql.append("                HENPIN_VL, ");
        sql.append("                HENPIN_QT, ");
        sql.append("                BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("                ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("            FROM ZEN_JIKANTAI_DPT_KYKUSU ");
        sql.append("            WHERE COMP_CD = ? AND ");
        // #5781 2017.08.07 J.Endo  MOD (S)
        //sql.append("                YR || MN || DD = ? ");
        sql.append("                YR || MN || DD <= ? AND ");
        sql.append("                YR || MN || DD >= ( ");
        sql.append("                    SELECT ");
        sql.append("                        MIN(START_DT) ");
        sql.append("                    FROM ");
        sql.append("                        R_CALENDAR ");
        sql.append("                    WHERE ");
        sql.append("                        COMP_CD = ? AND ");
        sql.append("                        KARIZIMESYORI_KB = '0') ");
        // #5781 2017.08.07 J.Endo  MOD (E)
        sql.append("            UNION ALL ");
        sql.append("            SELECT ");
        sql.append("                '1' AS TMP_OUTPUT_FG, "); // 1:ワークのデータはすべて出力対象
        sql.append("                SUBSTR(LPAD(TIME_TM-1,4,'0'),1,2) AS TMP_TIME_TAI, "); // 時間帯から処理時間帯を取得(09:01～10:00→0900「9時の時間帯のデータ」)
        sql.append("                COMP_CD, ");
        sql.append("                TENPO_CD, ");
        sql.append("                YR, ");
        sql.append("                MN, ");
        sql.append("                DD, ");
        sql.append("                JIGYOBU_CD, ");
        sql.append("                GYOSYU_CD, ");
        sql.append("                BUNRUI1_CD, ");
        sql.append("                TIME_TM, ");
        sql.append("                TENANT_KB, ");
        sql.append("                TENANT_CD, ");
        sql.append("                URIAGE_KB, ");
        sql.append("                URIAGE_VL, ");
        sql.append("                URIAGE_GENKA_VL, ");
        sql.append("                URIAGE_QT, ");
        sql.append("                NEBIKI_VL, ");
        sql.append("                NEBIKI_QT, ");
        sql.append("                KAKO_VL, ");
        sql.append("                KAKO_QT, ");
        sql.append("                HAIKI_VL, ");
        sql.append("                HAIKI_QT, ");
        sql.append("                HENPIN_VL, ");
        sql.append("                HENPIN_QT, ");
        sql.append("                BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("                ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("            FROM WK_JIKANTAI_DPT_KYKUSU ");
        sql.append("            WHERE COMP_CD = ? AND ");
        // #5781 2017.08.07 J.Endo  MOD (S)
        //sql.append("                YR || MN || DD = ? ");
        sql.append("                YR || MN || DD <= ? AND ");
        sql.append("                YR || MN || DD >= ( ");
        sql.append("                    SELECT ");
        sql.append("                        MIN(START_DT) ");
        sql.append("                    FROM ");
        sql.append("                        R_CALENDAR ");
        sql.append("                    WHERE ");
        sql.append("                        COMP_CD = ? AND ");
        sql.append("                        KARIZIMESYORI_KB = '0') ");
        // #5781 2017.08.07 J.Endo  MOD (E)
        sql.append("        ) ");
        sql.append("    ) ");
        sql.append("    WHERE TMP_TARGET_NM=1 "); // 30分単位のレコードは、00分単位のレコードがない場合(1)のみ出力し00分がある場合(0)は除外する。
        sql.append(") ");
        sql.append("WHERE TMP_OUTPUT_FG <> '0' "); // 出力対象フラグが0以外のもののみ出力する。
        // #3331 2017.03.15 J.Endo MOD (E)

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

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new JikantaiIfDptKyakusuSakuseiDao();
            new StandAloneDaoInvoker("mm").InvokeDao(dao);
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
