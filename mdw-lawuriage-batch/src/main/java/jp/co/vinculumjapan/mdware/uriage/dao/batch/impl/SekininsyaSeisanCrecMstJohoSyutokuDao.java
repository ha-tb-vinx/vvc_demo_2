package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.ErrorKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.impl.TouKaikeiNengetsuSelectDao;
import jp.co.vinculumjapan.mdware.uriage.dao.input.TouKaikeiNengetsuSelectDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.output.TouKaikeiNengetsuSelectDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: SekininsyaSeisanCrecMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理(責任者精算Crec)</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.11.14) VINX J.Endo FIVI対応 新規作成
 * @Version 1.01 (2016.12.13) VINX J.Endo FIVI対応 キャッシャーＩＤ不具合対応 #3304
 * @Version 1.02 (2016.12.14) VINX T.Kamei FIVI対応(#3306)
 * @Version 1.03 (2017.04.10) VINX X.Liu FIVI対応(#4553)
 * @Version 1.04 (2017.07.05) VINX J.Endo FIVI対応(#5040)
 * @Version 1.05 (2017.08.29) VINX N.Katou FIVI対応(#5840)
 */
public class SekininsyaSeisanCrecMstJohoSyutokuDao implements DaoIf {

    // バッチ処理名
    private static final String DAO_NAME = "マスタ情報取得処理（責任者精算Crec）";
    // 登録先テーブル名称
    private static final String INS_TABLE_NAME = "有効責任者精算Crecワーク";
    // ﾊﾞｯﾁID
    private static final String BATCH_ID = "URIB012800";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(S)
    //// 責任者コード：開始位置
    //private static final String CHECKER_CD_START_COLUMN = "2";
    //// 責任者コード：桁数
    //private static final String CHECKER_CD_LENGTH = "7";
    //// 責任者コード：エラー値
    //private static final String CHECKER_CD_ERR_CHECK = "0";
    // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(E)
    // 金額MAX値
    //#5040 MOD J.Endo 2017.07.05 (S)
    //private static final long KINGAKU_MAXNUM = 9999999999999L;
    private static final String KINGAKU_MAXNUM = "999999999999999.99";
    //#5040 MOD J.Endo 2017.07.05 (E)

    // 削除SQL文
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_SEKININSYA_SEISAN_C";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementInsert = null;
        PreparedStatementEx preparedStatementDelete = null;

        // 会計年月日
        String kaikeiDt = null;
        // 当月度会計年月取得DAO
        TouKaikeiNengetsuSelectDaoInputBean inputBean = new TouKaikeiNengetsuSelectDaoInputBean();
        // 法人コード
        inputBean.setCompCd(COMP_CD);

        // 当月度会計年月取得DAOの実行
        TouKaikeiNengetsuSelectDao touKaikeiNengetsu = new TouKaikeiNengetsuSelectDao();
        TouKaikeiNengetsuSelectDaoOutputBean outputBean = new TouKaikeiNengetsuSelectDaoOutputBean();

        // 入力ビーンをセット
        touKaikeiNengetsu.setInputObject(inputBean);

        invoker.InvokeDao(touKaikeiNengetsu);

        // 出力ビーンをセット
        outputBean = (TouKaikeiNengetsuSelectDaoOutputBean) touKaikeiNengetsu.getOutputObject();

        kaikeiDt = outputBean.getUserKaikeiYr() + outputBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;

        int insertCount = 0;
        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            // TMP責任者精算Crecデータから有効責任者取引精算Crecデータワークへ登録する
            preparedStatementInsert = invoker.getDataBase().prepareStatement(getTmpSekininsyaSeisanInsertSql());

            int intI = 1;

            preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_01.getCode()); // 01：店舗エラー
            preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_04.getCode()); // 04：過去日エラー
            //#5040 MOD J.Endo 2017.07.05 (S)
            //preparedStatementInsert.setDouble(intI++, KINGAKU_MAXNUM);
            preparedStatementInsert.setString(intI++, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.05 (E)
            preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_05.getCode()); // 05：桁数エラー
            // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(S)
            //preparedStatementInsert.setString(intI++, CHECKER_CD_ERR_CHECK);
            //preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_07.getCode()); // 07：責任者コードエラー
            // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(E)
            // 2017/08/31 VINX N.Katou #5840 (S)
//            preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_09.getCode()); // 09：取込済エラー
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementInsert.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());// ELSE
            preparedStatementInsert.setString(intI++, BATCH_ID);
            preparedStatementInsert.setString(intI++, FiResorceUtility.getDBServerTime());
            preparedStatementInsert.setString(intI++, BATCH_ID);
            preparedStatementInsert.setString(intI++, FiResorceUtility.getDBServerTime());
            preparedStatementInsert.setString(intI++, kaikeiDt);
            preparedStatementInsert.setString(intI++, kaikeiDt);
            preparedStatementInsert.setString(intI++, kaikeiDt);
            preparedStatementInsert.setString(intI++, kaikeiDt);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, COMP_CD);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            // #3306 2016.12.14 T.Kamei DEL (S)
            //preparedStatementInsert.setString(intI++, COMP_CD);
            //preparedStatementInsert.setString(intI++, BATCH_DT);
            // #3306 2016.12.14 T.Kamei DEL (E)
            preparedStatementInsert.setString(intI++, COMP_CD);

            insertCount = preparedStatementInsert.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // APPEND INSERTした内容確定する必要があるのでcommitを行う
            invoker.getDataBase().commit();

            // 責任者精算Crec取引なしレコードを、有効責任者精算Crecワークに登録する
            preparedStatementInsert = invoker.getDataBase().prepareStatement(getSekininsyaNoneTorihikiInsertSql());

            intI = 1;

            preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_01.getCode()); // 01：店舗エラー
            preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_04.getCode()); // 04：過去日エラー
            // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(S)
            //preparedStatementInsert.setString(intI++, CHECKER_CD_ERR_CHECK);
            //preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_07.getCode()); // 07：責任者コードエラー
            // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(E)
            // 2017/08/31 VINX N.Katou #5840 (S)
//            preparedStatementInsert.setString(intI++, ErrorKbDictionary.ERROR_09.getCode()); // 09：取込済エラー
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementInsert.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());// ELSE
            preparedStatementInsert.setString(intI++, BATCH_ID);
            preparedStatementInsert.setString(intI++, FiResorceUtility.getDBServerTime());
            preparedStatementInsert.setString(intI++, BATCH_ID);
            preparedStatementInsert.setString(intI++, FiResorceUtility.getDBServerTime());
            preparedStatementInsert.setString(intI++, kaikeiDt);
            preparedStatementInsert.setString(intI++, kaikeiDt);
            preparedStatementInsert.setString(intI++, kaikeiDt);
            preparedStatementInsert.setString(intI++, kaikeiDt);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, COMP_CD);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            preparedStatementInsert.setString(intI++, BATCH_DT);
            // #3306 2016.12.14 T.Kamei DEL (S)
            //preparedStatementInsert.setString(intI++, COMP_CD);
            //preparedStatementInsert.setString(intI++, BATCH_DT);
            // #3306 2016.12.14 T.Kamei DEL (E)
            preparedStatementInsert.setString(intI++, COMP_CD);
            preparedStatementInsert.setString(intI++, COMP_CD);

            insertCount = preparedStatementInsert.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "（責任者精算取引なしレコード）を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementInsert != null) {
                    preparedStatementInsert.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }
    }

    /**
     * TMP責任者精算Crecデータから有効責任者精算Crecワークへ登録するSQLを取得する
     *
     * @return 有効責任者精算Crecワーク登録クエリー
     */
    private String getTmpSekininsyaSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO WK_YUKO_SEKININSYA_SEISAN_C ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("   ,RECEIPT_QT ");
        sql.append("   ,SYUUKIN_VL ");
        sql.append("   ,CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("    ) ");
        sql.append("SELECT  ");
        sql.append("    COMP_CD  ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        // 2016.12.13 VINX J.Endo FIVI対応 #3304 MOD(S)
        //sql.append("   ,SUBSTR(CHECKER_CD, " + CHECKER_CD_START_COLUMN + ", " + CHECKER_CD_LENGTH + ") ");
        sql.append("   ,CHECKER_CD ");
        // 2016.12.13 VINX J.Endo FIVI対応 #3304 MOD(E)
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,CASE ");
        sql.append("        WHEN MAX(RT_TENPO_CD) IS NULL     THEN ? "); // 01：店舗エラー
        sql.append("        WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT)  THEN ? "); // 04：過去日エラー
        sql.append("        WHEN SHIHARAI_VL              > ? THEN ? "); // 05：桁数エラー
        // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(S)
        //sql.append("        WHEN SUBSTRB(CHECKER_CD,1,1) <> ? THEN ? "); // 07：責任者コードエラー
        // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(E)
        // #3306 2016.12.14 T.Kamei Mod (S)
        //sql.append("        WHEN DTSS_SEISAN_STATE_FG   = '1' THEN ? "); // 09：取込済エラー
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG   <> '0' THEN ? "); // 09：取込済エラー
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG   <> '0' THEN ? "); // 09：取込済エラー
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3306 2016.12.14 T.Kamei Mod (E)
        sql.append("        ELSE ? ");
        sql.append("    END AS ERR_KB ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("   ,RECEIPT_QT ");
        sql.append("   ,SYUUKIN_VL ");
        sql.append("   ,CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("        COMP_CD ");
        sql.append("       ,SUBSTR(TSS.EIGYO_DT,1, 4) || SUBSTR(TSS.EIGYO_DT,6, 2) || SUBSTR(TSS.EIGYO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("       ,TSS.TENPO_CD AS TENPO_CD ");
        sql.append("       ,CHECKER_CD ");
        sql.append("       ,TSS.SHIHARAI_SYUBETSU_CD ");
        sql.append("       ,TSS.SHIHARAI_SYUBETSU_SUB_CD ");
        //#5040 MOD J.Endo 2017.07.05 (S)
        //sql.append("       ,TSS.SHIHARAI_VL ");
        sql.append("       ,TO_NUMBER(TSS.SHIHARAI_VL) AS SHIHARAI_VL ");
        //#5040 MOD J.Endo 2017.07.05 (E)
        sql.append("       ,RT.RT_TENPO_CD ");
        sql.append("       ,RT.SEISAN_ST_DT ");
        sql.append("       ,RT.SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("       ,DTSS.DTSS_SEISAN_STATE_FG ");
        sql.append("       ,DTSS.DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("       ,TSS.RECEIPT_QT ");
        sql.append("       ,TSS.SYUUKIN_VL ");
        sql.append("       ,TSS.CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("    FROM   ");
        sql.append("        TMP_SEKININSYA_SEISAN_C TSS ");
        sql.append("        LEFT JOIN ");
        // ＜SUB店舗マスタ＞
        sql.append("            ( ");
        sql.append("            SELECT ");
        sql.append("                RT.TENPO_CD AS RT_TENPO_CD ");
        sql.append("               ,CASE ");
        sql.append("                    WHEN RT.SEISAN_ST_DT IS NULL THEN ? ");
        sql.append("                    ELSE ");
        sql.append("                        CASE ");
        sql.append("                            WHEN RT.SEISAN_ST_DT >= ? THEN RT.SEISAN_ST_DT ");
        sql.append("                            ELSE ");
        sql.append("                                CASE ");
        sql.append("                                    WHEN RT.SEISAN_ED_DT IS NULL OR RT.SEISAN_ED_DT >= ? THEN ? ");
                                                        // 上記以外の場合はSEISAN_ST_DT、SEISAN_ED_DT共に会計年月より過去となるためnullを設定する。
        sql.append("                                    ELSE NULL ");
        sql.append("                                END ");
        sql.append("                        END ");
        sql.append("                END AS SEISAN_ST_DT ");
        sql.append("               ,CASE ");
        sql.append("                    WHEN RT.SEISAN_ED_DT IS NULL THEN ? ");
        sql.append("                    ELSE ");
        sql.append("                        CASE ");
        sql.append("                            WHEN RT.SEISAN_ED_DT <= ? THEN RT.SEISAN_ED_DT ");
        sql.append("                            ELSE ");
        sql.append("                                CASE ");
        sql.append("                                    WHEN RT.SEISAN_ST_DT IS NULL OR RT.SEISAN_ST_DT <= ? THEN ? ");
                                                        // 上記以外の場合は精算可能開始日、精算可能終了日共に会計年月より未来となるためnullを設定する。
        sql.append("                                    ELSE NULL ");
        sql.append("                                END ");
        sql.append("                        END ");
        sql.append("                END AS SEISAN_ED_DT ");
        sql.append("            FROM ");
        sql.append("                R_TENPO RT ");
        sql.append("            WHERE  ");
        sql.append("                RT.HOJIN_CD      = ?   AND ");
        sql.append("                RT.TENPO_KB      = '1' AND ");
        sql.append("                RT.KAITEN_DT    <= ?   AND ");
        sql.append("                RT.ZAIMU_END_DT >= ?   AND ");
        sql.append("                RT.DELETE_FG     = '0' ");
        sql.append("            ) RT ");
        sql.append("        ON ");
        sql.append("            TSS.TENPO_CD = RT.RT_TENPO_CD ");
        sql.append("        LEFT JOIN ");
        sql.append("            ( ");
        sql.append("            SELECT ");
        // #3306 2016.12.14 T.Kamei Mod (S)
        sql.append("               COMP_CD               AS DTSS_COMP_CD  ");
        sql.append("               ,KEIJO_DT               AS DTSS_KEIJO_DT  ");
        sql.append("               ,TENPO_CD AS DTSS_TENPO_CD ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("               ,SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("               ,ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("            FROM ");
        sql.append("                DT_TEN_SEISAN_STATE ");
        //sql.append("            WHERE ");
        //sql.append("                COMP_CD = ? AND ");
        //sql.append("                KEIJO_DT = ? ");
        sql.append("            ) DTSS ");
        sql.append("        ON ");
        sql.append("            DTSS.DTSS_COMP_CD = TSS.COMP_CD AND ");
        sql.append("            DTSS.DTSS_KEIJO_DT = SUBSTR(TSS.EIGYO_DT,1, 4) || SUBSTR(TSS.EIGYO_DT,6, 2) || SUBSTR(TSS.EIGYO_DT,9, 2) AND ");
        sql.append("            DTSS.DTSS_TENPO_CD = TSS.TENPO_CD   ");
        //sql.append("            TSS.TENPO_CD = DTSS.DTSS_TENPO_CD ");
        // #3306 2016.12.14 T.Kamei Mod (E)
        sql.append("    ) ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");
        sql.append("GROUP BY ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,SEISAN_ST_DT ");
        sql.append("   ,SEISAN_ED_DT ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("   ,DTSS_SEISAN_STATE_FG ");
//        sql.append("   ,DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("   ,RECEIPT_QT ");
        sql.append("   ,SYUUKIN_VL ");
        sql.append("   ,CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        return sql.toString();
    }

    /**
     * 責任者精算Crec取引なしレコードを、有効責任者精算Crecワークに登録するSQLを取得する
     *
     * @return 有効責任者精算Crecワーク登録クエリー
     */
    private String getSekininsyaNoneTorihikiInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO WK_YUKO_SEKININSYA_SEISAN_C ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("   ,RECEIPT_QT ");
        sql.append("   ,SYUUKIN_VL ");
        sql.append("   ,CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        // 2016.12.13 VINX J.Endo FIVI対応 #3304 MOD(S)
        //sql.append("   ,SUBSTR(CHECKER_CD, " + CHECKER_CD_START_COLUMN + ", " + CHECKER_CD_LENGTH + ") ");
        sql.append("   ,CHECKER_CD ");
        // 2016.12.13 VINX J.Endo FIVI対応 #3304 MOD(E)
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,CASE ");
        sql.append("        WHEN RT_TENPO_CD IS NULL          THEN ? "); // 01：店舗エラー
        sql.append("        WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT)  THEN ? "); // 04：過去日エラー
        // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(S)
        //sql.append("        WHEN SUBSTRB(CHECKER_CD,1,1) <> ? THEN ? "); // 07：責任者コードエラー
        // 2016.12.13 VINX J.Endo FIVI対応 #3304 DEL(E)
        // #3306 2016.12.14 T.Kamei Mod (S)
        //sql.append("        WHEN DTSS_SEISAN_STATE_FG   = '1' THEN ? "); // 09：取込済エラー
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG   <> '0' THEN ? "); // 09：取込済エラー
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG   <> '0' THEN ? "); // 09：取込済エラー
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3306 2016.12.14 T.Kamei Mod (E)
        sql.append("        ELSE ? ");
        sql.append("    END AS ERR_KB ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("   ,RECEIPT_QT ");
        sql.append("   ,SYUUKIN_VL ");
        sql.append("   ,CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("        TSS.COMP_CD ");
        sql.append("       ,SUBSTR(TSS.EIGYO_DT,1, 4) || SUBSTR(TSS.EIGYO_DT,6, 2) || SUBSTR(TSS.EIGYO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("       ,TSS.TENPO_CD AS TENPO_CD ");
        sql.append("       ,TSS.CHECKER_CD ");
        sql.append("       ,TSS.SHIHARAI_SYUBETSU_CD ");
        sql.append("       ,TSS.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("       ,TSS.SHIHARAI_VL ");
        sql.append("       ,RT.RT_TENPO_CD ");
        sql.append("       ,RT.SEISAN_ST_DT ");
        sql.append("       ,RT.SEISAN_ED_DT ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("       ,DTSS.DTSS_SEISAN_STATE_FG ");
//        sql.append("       ,DTSS.DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("       ,TSS.RECEIPT_QT ");
        sql.append("       ,TSS.SYUUKIN_VL ");
        sql.append("       ,TSS.CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("    FROM ");
        sql.append("        TMP_SEKININSYA_SEISAN_C TSS ");
        sql.append("    LEFT JOIN ");
        // ＜SUB店舗マスタ＞
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("            RT.TENPO_CD AS RT_TENPO_CD ");
        sql.append("           ,CASE ");
        sql.append("                WHEN RT.SEISAN_ST_DT IS NULL THEN ? ");
        sql.append("                ELSE ");
        sql.append("                    CASE ");
        sql.append("                        WHEN RT.SEISAN_ST_DT >= ? THEN RT.SEISAN_ST_DT ");
        sql.append("                        ELSE ");
        sql.append("                            CASE ");
        sql.append("                                WHEN RT.SEISAN_ED_DT IS NULL OR RT.SEISAN_ED_DT >= ? THEN ? ");
                                                    // 上記以外の場合はSEISAN_ST_DT、SEISAN_ED_DT共に会計年月より過去となるためnullを設定する。
        sql.append("                                ELSE NULL ");
        sql.append("                            END ");
        sql.append("                    END ");
        sql.append("            END AS SEISAN_ST_DT ");
        sql.append("           ,CASE ");
        sql.append("                WHEN RT.SEISAN_ED_DT IS NULL THEN ? ");
        sql.append("                ELSE ");
        sql.append("                   CASE ");
        sql.append("                        WHEN RT.SEISAN_ED_DT <= ? THEN RT.SEISAN_ED_DT ");
        sql.append("                        ELSE ");
        sql.append("                            CASE ");
        sql.append("                                WHEN RT.SEISAN_ST_DT IS NULL OR RT.SEISAN_ST_DT <= ? THEN ? ");
                                                    // 上記以外の場合は精算可能開始日、精算可能終了日共に会計年月より未来となるためnullを設定する。
        sql.append("                                ELSE NULL ");
        sql.append("                            END ");
        sql.append("                   END ");
        sql.append("            END AS SEISAN_ED_DT ");
        sql.append("        FROM ");
        sql.append("            R_TENPO RT ");
        sql.append("        WHERE  ");
        sql.append("            RT.HOJIN_CD      = ?   AND ");
        sql.append("            RT.TENPO_KB      = '1' AND ");
        sql.append("            RT.KAITEN_DT    <= ?   AND ");
        sql.append("            RT.ZAIMU_END_DT >= ?   AND ");
        sql.append("            RT.DELETE_FG     = '0' ");
        sql.append("        ) RT ");
        sql.append("        ON ");
        sql.append("            TSS.TENPO_CD  = RT.RT_TENPO_CD   ");
        sql.append("        LEFT JOIN ");
        sql.append("            ( ");
        sql.append("            SELECT ");
        // #3306 2016.12.14 T.Kamei Mod (S)
        sql.append("                COMP_CD               AS DTSS_COMP_CD  ");
        sql.append("                ,KEIJO_DT               AS DTSS_KEIJO_DT  ");
        sql.append("                ,TENPO_CD AS DTSS_TENPO_CD, ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("                SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("                ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("            FROM ");
        sql.append("                DT_TEN_SEISAN_STATE ");
        //sql.append("            WHERE ");
        //sql.append("                COMP_CD = ? AND ");
        //sql.append("                KEIJO_DT = ? ");
        sql.append("            ) DTSS ");
        sql.append("        ON ");
        sql.append("            DTSS.DTSS_COMP_CD = TSS.COMP_CD AND ");
        sql.append("            DTSS.DTSS_KEIJO_DT = SUBSTR(TSS.EIGYO_DT,1, 4) || SUBSTR(TSS.EIGYO_DT,6, 2) || SUBSTR(TSS.EIGYO_DT,9, 2) AND ");
        sql.append("            DTSS.DTSS_TENPO_CD = TSS.TENPO_CD   ");
        //sql.append("            TSS.TENPO_CD = DTSS.DTSS_TENPO_CD ");
        // #3306 2016.12.14 T.Kamei Mod (E)
        sql.append("    WHERE ");
        sql.append("        TSS.COMP_CD = ? AND ");
        sql.append("        NOT EXISTS ");
        sql.append("            ( ");
        sql.append("            SELECT ");
        sql.append("                TSS_EX.COMP_CD ");
        sql.append("               ,TSS_EX.EIGYO_DT ");
        sql.append("               ,TSS_EX.TENPO_CD ");
        sql.append("               ,TSS_EX.CHECKER_CD ");
        sql.append("            FROM ");
        sql.append("                TMP_SEKININSYA_SEISAN_C TSS_EX ");
        sql.append("            WHERE ");
        sql.append("                TSS.COMP_CD         = TSS_EX.COMP_CD        AND ");
        sql.append("                TSS.EIGYO_DT        = TSS_EX.EIGYO_DT       AND ");
        sql.append("                TSS.TENPO_CD        = TSS_EX.TENPO_CD       AND ");
        sql.append("                TSS.CHECKER_CD      = TSS_EX.CHECKER_CD ");
        sql.append("            ) ");
        sql.append("    GROUP BY ");
        sql.append("        TSS.COMP_CD ");
        sql.append("       ,TSS.EIGYO_DT ");
        sql.append("       ,TSS.TENPO_CD ");
        sql.append("       ,TSS.CHECKER_CD ");
        sql.append("       ,TSS.SHIHARAI_SYUBETSU_CD ");
        sql.append("       ,TSS.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("       ,TSS.SHIHARAI_VL ");
        sql.append("       ,RT.RT_TENPO_CD ");
        sql.append("       ,SEISAN_ST_DT ");
        sql.append("       ,SEISAN_ED_DT ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("       ,DTSS_SEISAN_STATE_FG ");
//        sql.append("       ,DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("       ,TSS.RECEIPT_QT ");
        sql.append("       ,TSS.SYUUKIN_VL ");
        sql.append("       ,TSS.CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("    ) ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");

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
            DaoIf dao = new SekininsyaSeisanCrecMstJohoSyutokuDao();
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
