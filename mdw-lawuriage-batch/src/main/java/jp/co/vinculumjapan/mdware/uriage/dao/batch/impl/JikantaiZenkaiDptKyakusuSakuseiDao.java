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
 * <p>タイトル: JikantaiZenkaiDptKyakusuSakuseiDao.java クラス</p>
 * <p>説明　: 前回時間帯DPT客数データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.16) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2017.01.10) T.Kamei FIVI対応 #3403
 * @Version 3.03 (2017.03.17) J.Endo  FIVI対応 #3331
 * @Version 3.04 (2017.08.07) J.Endo  FIVI対応 #5781
 * @Version 3.05 (2021.09.14) SIEU.D #6339
 *
 */
public class JikantaiZenkaiDptKyakusuSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "前回時間帯DPT客数データ作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "前回時間帯DPT客数データ";

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

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 時間帯DPT客数ワークから前回時間帯DPT客数データを登録する
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加・更新を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getZenJikantaiDptKykusuMergeSql());
            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, BATCH_DT);
            // #5781 2017.08.07 J.Endo  MOD (S)
            //preparedStatementExIns.setString(3, userId);
            //preparedStatementExIns.setString(4, dbServerTime);
            //preparedStatementExIns.setString(5, userId);
            //preparedStatementExIns.setString(6, dbServerTime);
            //preparedStatementExIns.setString(7, userId);
            //preparedStatementExIns.setString(8, dbServerTime);
            preparedStatementExIns.setString(3, COMP_CD);
            preparedStatementExIns.setString(4, userId);
            preparedStatementExIns.setString(5, dbServerTime);
            preparedStatementExIns.setString(6, userId);
            preparedStatementExIns.setString(7, dbServerTime);
            preparedStatementExIns.setString(8, userId);
            preparedStatementExIns.setString(9, dbServerTime);
            // #5781 2017.08.07 J.Endo  MOD (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // #3331 2017.03.17 J.Endo  ADD (S)
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getZenJikantaiDptKykusuHalfMergeSql());
            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, BATCH_DT);
            // #5781 2017.08.07 J.Endo  MOD (S)
            //preparedStatementExIns.setString(3, userId);
            //preparedStatementExIns.setString(4, dbServerTime);
            //preparedStatementExIns.setString(5, userId);
            //preparedStatementExIns.setString(6, dbServerTime);
            preparedStatementExIns.setString(3, COMP_CD);
            preparedStatementExIns.setString(4, userId);
            preparedStatementExIns.setString(5, dbServerTime);
            preparedStatementExIns.setString(6, userId);
            preparedStatementExIns.setString(7, dbServerTime);
            // #5781 2017.08.07 J.Endo  MOD (E)

            insertCount = insertCount + preparedStatementExIns.executeUpdate();
            // #3331 2017.03.17 J.Endo  ADD (E)

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加・更新しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加・更新を終了します。");

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
     * 時間帯DPT客数ワークから前回時間帯DPT客数データを登録・更新するSQLを取得する
     *
     * @return 前回時間帯DPT客数データ登録・更新SQL
     */
    private String getZenJikantaiDptKykusuMergeSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("MERGE INTO ZEN_JIKANTAI_DPT_KYKUSU ZJDK ");
        sql.append("    USING ");
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("             WJDK.COMP_CD ");
        sql.append("            ,WJDK.TENPO_CD ");
        sql.append("            ,WJDK.YR ");
        sql.append("            ,WJDK.MN ");
        sql.append("            ,WJDK.DD ");
        sql.append("            ,WJDK.JIGYOBU_CD ");
        sql.append("            ,WJDK.GYOSYU_CD ");
        sql.append("            ,WJDK.BUNRUI1_CD ");
        sql.append("            ,WJDK.TIME_TM ");
        sql.append("            ,WJDK.TENANT_KB ");
        sql.append("            ,WJDK.TENANT_CD ");
        sql.append("            ,WJDK.URIAGE_KB ");
        sql.append("            ,WJDK.URIAGE_VL ");
        sql.append("            ,WJDK.URIAGE_GENKA_VL ");
        sql.append("            ,WJDK.URIAGE_QT ");
        sql.append("            ,WJDK.NEBIKI_VL ");
        sql.append("            ,WJDK.NEBIKI_QT ");
        sql.append("            ,WJDK.KAKO_VL ");
        sql.append("            ,WJDK.KAKO_QT ");
        sql.append("            ,WJDK.HAIKI_VL ");
        sql.append("            ,WJDK.HAIKI_QT ");
        sql.append("            ,WJDK.HENPIN_VL ");
        sql.append("            ,WJDK.HENPIN_QT ");
        sql.append("            ,WJDK.BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("            ,WJDK.ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("        FROM ");
        sql.append("            WK_JIKANTAI_DPT_KYKUSU WJDK ");
        sql.append("        WHERE ");
        sql.append("            WJDK.COMP_CD                    = ? AND ");
        // #3331 2017.03.17 J.Endo  MOD (S)
        //sql.append("            WJDK.YR || WJDK.MN || WJDK.DD   = ? ");
        // #5781 2017.08.07 J.Endo  MOD (S)
        //sql.append("            WJDK.YR || WJDK.MN || WJDK.DD   = ? AND ");
        sql.append("            WJDK.YR || WJDK.MN || WJDK.DD <= ? AND ");
        sql.append("            WJDK.YR || WJDK.MN || WJDK.DD >= ( ");
        sql.append("                    SELECT ");
        sql.append("                        MIN(START_DT) ");
        sql.append("                    FROM ");
        sql.append("                        R_CALENDAR ");
        sql.append("                    WHERE ");
        sql.append("                        COMP_CD = ? AND ");
        sql.append("                        KARIZIMESYORI_KB = '0') AND ");
        // #5781 2017.08.07 J.Endo  MOD (E)
        sql.append("            SUBSTR(WJDK.TIME_TM,3,2) = '00' "); // 00分は存在すれば更新
        // #3331 2017.03.17 J.Endo  MOD (E)
        sql.append("        ) WJDK ");
        sql.append("    ON ");
        sql.append("        ( ");
        sql.append("            ZJDK.COMP_CD    = WJDK.COMP_CD  AND ");
        sql.append("            ZJDK.YR         = WJDK.YR       AND ");
        sql.append("            ZJDK.MN         = WJDK.MN       AND ");
        sql.append("            ZJDK.DD         = WJDK.DD       AND ");
        sql.append("            ZJDK.TENPO_CD   = WJDK.TENPO_CD AND ");
        // #3403 2016.12.27 T.Kamei MOD (S)
        //sql.append("            ZJDK.BUNRUI1_CD = WJDK.BUNRUI1_CD ");
        sql.append("            ZJDK.BUNRUI1_CD = WJDK.BUNRUI1_CD AND ");
        // #3331 2017.03.17 J.Endo  MOD (S)
        //sql.append("            ZJDK.TIME_TM    = WJDK.TIME_TM ");
        sql.append("            SUBSTR(ZJDK.TIME_TM,1,2) = SUBSTR(LPAD(WJDK.TIME_TM-1,4,'0'),1,2) "); // 時間帯で比較（分は無視）
        // #3331 2017.03.17 J.Endo  MOD (E)
        // #3403 2016.12.27 T.Kamei MOD (E)
        sql.append("        ) ");
        sql.append("    WHEN MATCHED THEN ");
        sql.append("        UPDATE ");
        sql.append("            SET ");
        sql.append("                 ZJDK.JIGYOBU_CD        = WJDK.JIGYOBU_CD ");
        sql.append("                ,ZJDK.GYOSYU_CD         = WJDK.GYOSYU_CD ");
        // #3403 2017.01.10 T.Kamei DEL (S)
        //sql.append("                ,ZJDK.TIME_TM           = WJDK.TIME_TM ");
        // #3403 2017.01.10 T.Kamei DEL (E)
        sql.append("                ,ZJDK.TENANT_KB         = WJDK.TENANT_KB ");
        sql.append("                ,ZJDK.TENANT_CD         = WJDK.TENANT_CD ");
        sql.append("                ,ZJDK.URIAGE_KB         = WJDK.URIAGE_KB ");
        sql.append("                ,ZJDK.URIAGE_VL         = WJDK.URIAGE_VL ");
        sql.append("                ,ZJDK.URIAGE_GENKA_VL   = WJDK.URIAGE_GENKA_VL ");
        sql.append("                ,ZJDK.URIAGE_QT         = WJDK.URIAGE_QT ");
        sql.append("                ,ZJDK.NEBIKI_VL         = WJDK.NEBIKI_VL ");
        sql.append("                ,ZJDK.NEBIKI_QT         = WJDK.NEBIKI_QT ");
        sql.append("                ,ZJDK.KAKO_VL           = WJDK.KAKO_VL ");
        sql.append("                ,ZJDK.KAKO_QT           = WJDK.KAKO_QT ");
        sql.append("                ,ZJDK.HAIKI_VL          = WJDK.HAIKI_VL ");
        sql.append("                ,ZJDK.HAIKI_QT          = WJDK.HAIKI_QT ");
        sql.append("                ,ZJDK.HENPIN_VL         = WJDK.HENPIN_VL ");
        sql.append("                ,ZJDK.HENPIN_QT         = WJDK.HENPIN_QT ");
        sql.append("                ,ZJDK.BUNRUI1_KYAKU_QT  = WJDK.BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("                ,ZJDK.ARARI_VL            = WJDK.ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("                ,ZJDK.UPDATE_USER_ID    = ? ");
        sql.append("                ,ZJDK.UPDATE_TS         = ? ");
        sql.append("    WHEN NOT MATCHED THEN ");
        sql.append("        INSERT ( ");
        sql.append("             COMP_CD ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,YR ");
        sql.append("            ,MN ");
        sql.append("            ,DD ");
        sql.append("            ,JIGYOBU_CD ");
        sql.append("            ,GYOSYU_CD ");
        sql.append("            ,BUNRUI1_CD ");
        sql.append("            ,TIME_TM ");
        sql.append("            ,TENANT_KB ");
        sql.append("            ,TENANT_CD ");
        sql.append("            ,URIAGE_KB ");
        sql.append("            ,URIAGE_VL ");
        sql.append("            ,URIAGE_GENKA_VL ");
        sql.append("            ,URIAGE_QT ");
        sql.append("            ,NEBIKI_VL ");
        sql.append("            ,NEBIKI_QT ");
        sql.append("            ,KAKO_VL ");
        sql.append("            ,KAKO_QT ");
        sql.append("            ,HAIKI_VL ");
        sql.append("            ,HAIKI_QT ");
        sql.append("            ,HENPIN_VL ");
        sql.append("            ,HENPIN_QT ");
        sql.append("            ,BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("            ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("            ,INSERT_USER_ID ");
        sql.append("            ,INSERT_TS ");
        sql.append("            ,UPDATE_USER_ID ");
        sql.append("            ,UPDATE_TS) ");
        sql.append("        VALUES( ");
        sql.append("             WJDK.COMP_CD ");
        sql.append("            ,WJDK.TENPO_CD ");
        sql.append("            ,WJDK.YR ");
        sql.append("            ,WJDK.MN ");
        sql.append("            ,WJDK.DD ");
        sql.append("            ,WJDK.JIGYOBU_CD ");
        sql.append("            ,WJDK.GYOSYU_CD ");
        sql.append("            ,WJDK.BUNRUI1_CD ");
        // #3331 2017.03.17 J.Endo  MOD (S)
        //sql.append("            ,WJDK.TIME_TM ");
        sql.append("            ,SUBSTR(LPAD(WJDK.TIME_TM-1,4,'0'),1,2) "); // 時間帯のみ設定（分は無視）
        // #3331 2017.03.17 J.Endo  MOD (E)
        sql.append("            ,WJDK.TENANT_KB ");
        sql.append("            ,WJDK.TENANT_CD ");
        sql.append("            ,WJDK.URIAGE_KB ");
        sql.append("            ,WJDK.URIAGE_VL ");
        sql.append("            ,WJDK.URIAGE_GENKA_VL ");
        sql.append("            ,WJDK.URIAGE_QT ");
        sql.append("            ,WJDK.NEBIKI_VL ");
        sql.append("            ,WJDK.NEBIKI_QT ");
        sql.append("            ,WJDK.KAKO_VL ");
        sql.append("            ,WJDK.KAKO_QT ");
        sql.append("            ,WJDK.HAIKI_VL ");
        sql.append("            ,WJDK.HAIKI_QT ");
        sql.append("            ,WJDK.HENPIN_VL ");
        sql.append("            ,WJDK.HENPIN_QT ");
        sql.append("            ,WJDK.BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("            ,WJDK.ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("        ) ");

        return sql.toString();
    }

    // #3331 2017.03.17 J.Endo  ADD (S)
    /**
     * 時間帯DPT客数ワークから前回時間帯DPT客数データへ「なければ」登録するSQLを取得する
     *
     * @return 前回時間帯DPT客データ登録数SQL
     */
    private String getZenJikantaiDptKykusuHalfMergeSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("MERGE INTO ZEN_JIKANTAI_DPT_KYKUSU ZJDK ");
        sql.append("    USING ");
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("             WJDK.COMP_CD ");
        sql.append("            ,WJDK.TENPO_CD ");
        sql.append("            ,WJDK.YR ");
        sql.append("            ,WJDK.MN ");
        sql.append("            ,WJDK.DD ");
        sql.append("            ,WJDK.JIGYOBU_CD ");
        sql.append("            ,WJDK.GYOSYU_CD ");
        sql.append("            ,WJDK.BUNRUI1_CD ");
        sql.append("            ,WJDK.TIME_TM ");
        sql.append("            ,WJDK.TENANT_KB ");
        sql.append("            ,WJDK.TENANT_CD ");
        sql.append("            ,WJDK.URIAGE_KB ");
        sql.append("            ,WJDK.URIAGE_VL ");
        sql.append("            ,WJDK.URIAGE_GENKA_VL ");
        sql.append("            ,WJDK.URIAGE_QT ");
        sql.append("            ,WJDK.NEBIKI_VL ");
        sql.append("            ,WJDK.NEBIKI_QT ");
        sql.append("            ,WJDK.KAKO_VL ");
        sql.append("            ,WJDK.KAKO_QT ");
        sql.append("            ,WJDK.HAIKI_VL ");
        sql.append("            ,WJDK.HAIKI_QT ");
        sql.append("            ,WJDK.HENPIN_VL ");
        sql.append("            ,WJDK.HENPIN_QT ");
        sql.append("            ,WJDK.BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("            ,WJDK.ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("        FROM ");
        sql.append("            WK_JIKANTAI_DPT_KYKUSU WJDK ");
        sql.append("        WHERE ");
        sql.append("            WJDK.COMP_CD                    = ? AND ");
        // #5781 2017.08.07 J.Endo  MOD (S)
        //sql.append("            WJDK.YR || WJDK.MN || WJDK.DD   = ? AND ");
        sql.append("            WJDK.YR || WJDK.MN || WJDK.DD   <= ? AND ");
        sql.append("            WJDK.YR || WJDK.MN || WJDK.DD   >= ( ");
        sql.append("                    SELECT ");
        sql.append("                        MIN(START_DT) ");
        sql.append("                    FROM ");
        sql.append("                        R_CALENDAR ");
        sql.append("                    WHERE ");
        sql.append("                        COMP_CD = ? AND ");
        sql.append("                        KARIZIMESYORI_KB = '0') AND ");
        // #5781 2017.08.07 J.Endo  MOD (E)
        sql.append("            SUBSTR(WJDK.TIME_TM,3,2) = '30' "); // 30分は存在すれば未処理
        sql.append("        ) WJDK ");
        sql.append("    ON ");
        sql.append("        ( ");
        sql.append("            ZJDK.COMP_CD    = WJDK.COMP_CD  AND ");
        sql.append("            ZJDK.YR         = WJDK.YR       AND ");
        sql.append("            ZJDK.MN         = WJDK.MN       AND ");
        sql.append("            ZJDK.DD         = WJDK.DD       AND ");
        sql.append("            ZJDK.TENPO_CD   = WJDK.TENPO_CD AND ");
        sql.append("            ZJDK.BUNRUI1_CD = WJDK.BUNRUI1_CD AND ");
        sql.append("            SUBSTR(ZJDK.TIME_TM,1,2) = SUBSTR(LPAD(WJDK.TIME_TM-1,4,'0'),1,2) "); // 時間帯で比較（分は無視）
        sql.append("        ) ");
        sql.append("    WHEN NOT MATCHED THEN ");
        sql.append("        INSERT ( ");
        sql.append("             COMP_CD ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,YR ");
        sql.append("            ,MN ");
        sql.append("            ,DD ");
        sql.append("            ,JIGYOBU_CD ");
        sql.append("            ,GYOSYU_CD ");
        sql.append("            ,BUNRUI1_CD ");
        sql.append("            ,TIME_TM ");
        sql.append("            ,TENANT_KB ");
        sql.append("            ,TENANT_CD ");
        sql.append("            ,URIAGE_KB ");
        sql.append("            ,URIAGE_VL ");
        sql.append("            ,URIAGE_GENKA_VL ");
        sql.append("            ,URIAGE_QT ");
        sql.append("            ,NEBIKI_VL ");
        sql.append("            ,NEBIKI_QT ");
        sql.append("            ,KAKO_VL ");
        sql.append("            ,KAKO_QT ");
        sql.append("            ,HAIKI_VL ");
        sql.append("            ,HAIKI_QT ");
        sql.append("            ,HENPIN_VL ");
        sql.append("            ,HENPIN_QT ");
        sql.append("            ,BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("            ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("            ,INSERT_USER_ID ");
        sql.append("            ,INSERT_TS ");
        sql.append("            ,UPDATE_USER_ID ");
        sql.append("            ,UPDATE_TS) ");
        sql.append("        VALUES( ");
        sql.append("             WJDK.COMP_CD ");
        sql.append("            ,WJDK.TENPO_CD ");
        sql.append("            ,WJDK.YR ");
        sql.append("            ,WJDK.MN ");
        sql.append("            ,WJDK.DD ");
        sql.append("            ,WJDK.JIGYOBU_CD ");
        sql.append("            ,WJDK.GYOSYU_CD ");
        sql.append("            ,WJDK.BUNRUI1_CD ");
        sql.append("            ,SUBSTR(LPAD(WJDK.TIME_TM-1,4,'0'),1,2) "); // 時間帯のみ設定（分は無視）
        sql.append("            ,WJDK.TENANT_KB ");
        sql.append("            ,WJDK.TENANT_CD ");
        sql.append("            ,WJDK.URIAGE_KB ");
        sql.append("            ,WJDK.URIAGE_VL ");
        sql.append("            ,WJDK.URIAGE_GENKA_VL ");
        sql.append("            ,WJDK.URIAGE_QT ");
        sql.append("            ,WJDK.NEBIKI_VL ");
        sql.append("            ,WJDK.NEBIKI_QT ");
        sql.append("            ,WJDK.KAKO_VL ");
        sql.append("            ,WJDK.KAKO_QT ");
        sql.append("            ,WJDK.HAIKI_VL ");
        sql.append("            ,WJDK.HAIKI_QT ");
        sql.append("            ,WJDK.HENPIN_VL ");
        sql.append("            ,WJDK.HENPIN_QT ");
        sql.append("            ,WJDK.BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("            ,WJDK.ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("        ) ");

        return sql.toString();
    }
    // #3331 2017.03.17 J.Endo  ADD (E)

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
            DaoIf dao = new JikantaiZenkaiDptKyakusuSakuseiDao();
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
