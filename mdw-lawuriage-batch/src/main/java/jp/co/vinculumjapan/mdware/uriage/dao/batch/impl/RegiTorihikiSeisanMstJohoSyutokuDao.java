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
 * <p>タイトル: RegiTorihikiSeisanMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理(レジ別取引精算)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2014.12.03) chou グローバル化対応 通貨対応
 * @Version 1.01 (2016.03.08) TU.TD 設計書No.606(#1161) FIVImart対応
 * @Version 1.02 (2016.04.11) T.Kamei  FIVImart対応 計上日の期間判定処理の修正
 * @Version 1.01 (2016/05/10) S_MDware-G_FIVIマート様開発 VINX k.Hyo
 * @Version 1.02 (2016/06/1) to #1476対応
 * @Version 1.03 (2016/06/29) S_MDware-G_FIVIマート様開発 VINX Y.Itaki
 * @Version 1.04 (2016/12/14) VINX T.Kamei FIVI対応(#3306)
 * @Version 1.05 (2017/04/10) VINX X.Liu FIVI対応(#4553)
 * @Version 1.06 (2017/07/04) VINX J.Endo FIVI対応(#5040)
 * @Version 1.07 (2017/08/31) VINX N.Katou  FIVI対応(#5840)
 *
 */
public class RegiTorihikiSeisanMstJohoSyutokuDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 客数MAX値 */
    private static final long KYAKU_QT_MAXNUM = 99999999999L;
    /** 点数/回数MAX値 */
    private static final long SU_MAXNUM = 999999L;
    /** 金額MAX値 */
    //#5040 MOD J.Endo 2017.07.04 (S)
    //private static final long KINGAKU_MAXNUM = 9999999999999L;
    private static final String KINGAKU_MAXNUM = "999999999999999.99";
    //#5040 MOD J.Endo 2017.07.04 (E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "マスタ情報取得処理（レジ別取引精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "有効レジ別取引精算ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_REGI_TORIHIKI_SEISAN";
    /** レジ別取引精算取引コード */
    // 2016/06/28 VINX Y.Itaki S_MDware-G_FIVIマート様開発（S)
    //private static final Map REGI_SEISAN_TORIHIKI_CD_MAP = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.REGI_SEISAN_TORIHIKI_CD);
    // 2016/06/28 VINX Y.Itaki S_MDware-G_FIVIマート様開発（E)
    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        //
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        // No.606 URIB012230 Add 2016.03.08 TU.TD (S)
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
        // No.606 URIB012230 Add 2016.03.08 TU.TD (E)

        int insertCount = 0;
        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // TMPレジ別取引精算データから有効レジ別取引精算ワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getYukoWkRegiTorihikiSeisanInsertSql());

            // No.606 URIB012230 Mod 2016.03.08 TU.TD (S)
            /*preparedStatementExIns.setString(1, ErrorKbDictionary.ERROR_01.getCode());
            preparedStatementExIns.setString(2, BATCH_DT);
            preparedStatementExIns.setString(3, ErrorKbDictionary.ERROR_04.getCode());
            preparedStatementExIns.setLong(4, KYAKU_QT_MAXNUM);
            preparedStatementExIns.setString(5, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(6, SU_MAXNUM);
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_05.getCode());
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(8, KINGAKU_MAXNUM);
            preparedStatementExIns.setDouble(8, KINGAKU_MAXNUM);
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(9, ErrorKbDictionary.ERROR_05.getCode());
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(10, KINGAKU_MAXNUM);
            preparedStatementExIns.setDouble(10, KINGAKU_MAXNUM);
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(11, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(12, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(13, userId);
            preparedStatementExIns.setString(14, dbServerTime);
            preparedStatementExIns.setString(15, userId);
            preparedStatementExIns.setString(16, dbServerTime);

            preparedStatementExIns.setString(17, COMP_CD);
            preparedStatementExIns.setString(18, BATCH_DT);
            preparedStatementExIns.setString(19, BATCH_DT);

            preparedStatementExIns.setString(20, COMP_CD);*/


            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (S)
            int intI = 1;
            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (E)

            // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_01.getCode());
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_04.getCode());
            preparedStatementExIns.setLong(intI++, KYAKU_QT_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(8, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.04 (S)
            //preparedStatementExIns.setDouble(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.04 (E)
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(10, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.04 (S)
            //preparedStatementExIns.setDouble(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.04 (E)
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            // 2017/08/31 VINX N.Katou #5840 (S)
//            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_09.getCode());
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, dbServerTime);
            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, dbServerTime);

            preparedStatementExIns.setString(intI++, kaikeiDt);
            preparedStatementExIns.setString(intI++, kaikeiDt);
            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (S)
            preparedStatementExIns.setString(intI++, kaikeiDt);
            preparedStatementExIns.setString(intI++, kaikeiDt);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (E)
            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);

            // #3306 2016.12.14 T.Kamei DEL (S)
            //preparedStatementExIns.setString(intI++, COMP_CD);
            //preparedStatementExIns.setString(intI++, BATCH_DT);
            // #3306 2016.12.14 T.Kamei DEL (E)

            preparedStatementExIns.setString(intI++, COMP_CD);
            // No.606 URIB012230 Mod 2016.03.08 TU.TD (E)
            // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
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
     * TMPレジ別取引精算データから有効レジ別取引精算ワークへ登録するSQLを取得する
     *
     * @return 有効レジ別取引精算ワーク登録クエリー
     */
    private String getYukoWkRegiTorihikiSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_REGI_TORIHIKI_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD) ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("SELECT  ");
        sql.append("     COMP_CD  ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,SUM(KYAKU_QT) ");
        sql.append("    ,SUM(TEN_KAISUU_QT) ");
        sql.append("    ,SUM(KINGAKU_VL) ");
        sql.append("    ,SUM(NEBIKI_VL) ");
        sql.append("    ,CASE ");
        sql.append("        WHEN MAX(RT_TENPO_CD) IS NULL THEN ? ");
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.606 URIB012230 Mod 2016.03.08 TU.TD (S)
        //sql.append("        WHEN KEIJO_DT < ? THEN ? ");
//        sql.append("        WHEN KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT THEN ? ");
        sql.append("        WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT)  THEN ? ");
        // No.606 URIB012230 Mod 2016.03.08 TU.TD (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("        WHEN SUM(KYAKU_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(TEN_KAISUU_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(KINGAKU_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(NEBIKI_VL) > ? THEN ? ");
        // No.606 URIB012230 Mod 2016.03.08 TU.TD (S)
        // #3306 2016.12.14 T.Kamei Mod (S)
        //sql.append("        WHEN DTSS_SEISAN_STATE_FG = '1' THEN ? ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG <> '0' THEN ? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG <> '0' THEN ? ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3306 2016.12.14 T.Kamei Mod (E)
        // No.606 URIB012230 Mod 2016.03.08 TU.TD (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT   ");
        sql.append("             COMP_CD ");
        sql.append("            ,SUBSTR(TRTS.EIGYO_DT,1, 4) || SUBSTR(TRTS.EIGYO_DT,6, 2) || SUBSTR(TRTS.EIGYO_DT,9, 2)  AS KEIJO_DT ");
        // 2016/06/01 VINX to #1476対応 （S)
//        sql.append("            ,SUBSTR(TRTS.TENPO_CD ,2,4) AS TENPO_CD ");
        sql.append("            ,TRTS.TENPO_CD AS TENPO_CD ");
        // 2016/06/01 VINX to #1476対応 （E)
        sql.append("            ,FLOAR_NO ");
        sql.append("            ,REGI_NO ");
        sql.append("            ,TORIHIKI_CD ");
        sql.append("            ,TYPE_KB ");
        sql.append("            ,TO_NUMBER(KYAKU_QT) AS KYAKU_QT ");
        sql.append("            ,TO_NUMBER(TEN_KAISUU_QT) AS TEN_KAISUU_QT ");
        sql.append("            ,TO_NUMBER(KINGAKU_VL) AS KINGAKU_VL ");
        sql.append("            ,TO_NUMBER(NEBIKI_VL) AS NEBIKI_VL ");
        sql.append("            ,RT.RT_TENPO_CD ");
        // No.606 URIB012230 Add 2016.03.08 TU.TD (S)
        sql.append("            ,RT.SEISAN_ST_DT ");
        sql.append("            ,RT.SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("            ,DTSS.DTSS_SEISAN_STATE_FG ");
        sql.append("            ,DTSS.DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.606 URIB012230 Add 2016.03.08 TU.TD (E)
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("            ,SHIHARAI_SYUBETSU_CD ");
        sql.append("            ,SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("        FROM   ");
        sql.append("            TMP_REGI_TORIHIKI_SEISAN TRTS   ");
        sql.append("            LEFT JOIN   ");
        // ＜SUB店舗マスタ＞
        sql.append("                (  ");
        sql.append("                    SELECT   ");
        sql.append("                        RT.TENPO_CD AS RT_TENPO_CD ");
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.606 URIB012230 Add 2016.03.08 TU.TD (S)
//        sql.append("                        , COALESCE( ");
//        sql.append("                            RT.SEISAN_ST_DT, ");
//        sql.append("                            CASE ");
//        sql.append("                                WHEN RT.KAITEN_DT > ? THEN RT.KAITEN_DT ");
//        sql.append("                                ELSE ? ");
//        sql.append("                            END) AS SEISAN_ST_DT ");
//        sql.append("                        , COALESCE(RT.SEISAN_ED_DT,RT.ZAIMU_END_DT) AS SEISAN_ED_DT ");
        sql.append("                        , CASE ");
        sql.append("                              WHEN RT.SEISAN_ST_DT IS NULL THEN ? ");
        sql.append("                              ELSE ");
        sql.append("                                  CASE ");
        sql.append("                                      WHEN RT.SEISAN_ST_DT >= ? THEN RT.SEISAN_ST_DT ");
        sql.append("                                      ELSE ");
        sql.append("                                          CASE ");
        sql.append("                                              WHEN RT.SEISAN_ED_DT IS NULL OR RT.SEISAN_ED_DT >= ? THEN ? ");
                                                                  // 上記以外の場合はSEISAN_ST_DT、SEISAN_ED_DT共に会計年月より過去となるためnullを設定する。
        sql.append("                                              ELSE NULL ");
        sql.append("                                          END ");
        sql.append("                                  END ");
        sql.append("                          END AS SEISAN_ST_DT ");
        sql.append("                        , CASE ");
        sql.append("                              WHEN RT.SEISAN_ED_DT IS NULL THEN ? ");
        sql.append("                              ELSE ");
        sql.append("                                  CASE ");
        sql.append("                                      WHEN RT.SEISAN_ED_DT <= ? THEN RT.SEISAN_ED_DT ");
        sql.append("                                      ELSE ");
        sql.append("                                          CASE ");
        sql.append("                                              WHEN RT.SEISAN_ST_DT IS NULL OR RT.SEISAN_ST_DT <= ? THEN ? ");
                                                                  // 上記以外の場合は精算可能開始日、精算可能終了日共に会計年月より未来となるためnullを設定する。
        sql.append("                                              ELSE NULL ");
        sql.append("                                          END ");
        sql.append("                                  END ");
        sql.append("                          END AS SEISAN_ED_DT ");
        // No.606 URIB012230 Add 2016.03.08 TU.TD (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("                    FROM   ");
        sql.append("                        R_TENPO RT  ");
        sql.append("                    WHERE  ");
        sql.append("                        RT.HOJIN_CD      = ?    AND   ");
        sql.append("                        RT.TENPO_KB      = '1'  AND   ");
        sql.append("                        RT.KAITEN_DT    <= ?    AND   ");
        sql.append("                        RT.ZAIMU_END_DT >= ?    AND   ");
        sql.append("                        RT.DELETE_FG     = '0'  ");
        sql.append("                ) RT  ");
        sql.append("            ON   ");
        sql.append("                TRTS.TENPO_CD = RT.RT_TENPO_CD   ");
        // No.606 URIB012230 Add 2016.03.08 TU.TD (S)
        sql.append("            LEFT JOIN ");
        sql.append("                ( ");
        sql.append("                    SELECT ");
        // #3306 2016.12.14 T.Kamei Mod (S)
        sql.append("                        COMP_CD               AS DTSS_COMP_CD  ");
        sql.append("                        ,KEIJO_DT               AS DTSS_KEIJO_DT  ");
        sql.append("                        ,TENPO_CD AS DTSS_TENPO_CD, ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("                        SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("                        ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("                    FROM ");
        sql.append("                        DT_TEN_SEISAN_STATE ");
        //sql.append("                    WHERE ");
        //sql.append("                        COMP_CD = ? AND ");
        //sql.append("                        KEIJO_DT = ? ");
        sql.append("                 ) DTSS");
        sql.append("            ON ");
        sql.append("                DTSS.DTSS_COMP_CD = TRTS.COMP_CD AND ");
        sql.append("                DTSS.DTSS_KEIJO_DT = SUBSTR(TRTS.EIGYO_DT,1, 4) || SUBSTR(TRTS.EIGYO_DT,6, 2) || SUBSTR(TRTS.EIGYO_DT,9, 2) AND ");
        sql.append("                DTSS.DTSS_TENPO_CD = TRTS.TENPO_CD   ");
        //sql.append("                TRTS.TENPO_CD = DTSS.DTSS_TENPO_CD ");
        // #3306 2016.12.14 T.Kamei Mod (E)
        // No.606 URIB012230 Add 2016.03.08 TU.TD (E)

        // 2016/06/28 VINX Y.Itaki S_MDware-G_FIVIマート様開発（S)
//        if (!REGI_SEISAN_TORIHIKI_CD_MAP.isEmpty()) {
//            StringBuilder buff = new StringBuilder();
//
//            for(Iterator it = REGI_SEISAN_TORIHIKI_CD_MAP.entrySet().iterator(); it.hasNext();){
//                Map.Entry uriageTanpinCd = (Map.Entry)it.next();
//                buff.append(",'" + uriageTanpinCd.getValue() + "'");
//            }
//
//
//            sql.append("        WHERE ");
//            sql.append("             TORIHIKI_CD IN (");
//            sql.append(buff.substring(1));
//            sql.append(")");
//        }
        // 2016/06/28 VINX Y.Itaki S_MDware-G_FIVIマート様開発（E)

        sql.append("    ) ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");
        sql.append("GROUP BY  ");
        sql.append("     COMP_CD  ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TORIHIKI_CD ");
        // No.606 URIB012230 Add 2016.03.08 TU.TD (S)
        sql.append("    ,SEISAN_ST_DT ");
        sql.append("    ,SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("    ,DTSS_SEISAN_STATE_FG ");
        sql.append("    ,DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.606 URIB012230 Add 2016.03.08 TU.TD (E)
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)


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
            DaoIf dao = new RegiTorihikiSeisanMstJohoSyutokuDao();
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
