package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.util.HashMap;

import jp.co.vinculumjapan.mdware.uriage.constant.ErrorKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.BumonTenkenMstJohoSyutokuDaoInputBean;
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
 * <p>タイトル: BumonTenkenMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理(部門点検)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.24) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.22) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №006
 * @Version 3.021 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.03 (2014.01.31) Y.Tominaga 結合ﾃｽﾄNo.0112 POS時間帯取込処理改善 対応
 * @version 3.04 (2016.03.08) NGUYEN.NTM 設計書No.609 #1161 FIVIMART対応
 * @version 3.05 (2016.07.22) Y.Itaki FIVI対応 #1484
 * @version 3.06 (2017.04.10) X.Liu  FIVI対応 #4553
 * @Version 3.07 (2017.08.31) VINX N.Katou #5840
 * @Version 3.08 (2021.09.14) SIEU.D #6339
 */
public class BumonTenkenMstJohoSyutokuDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // エラーコードとエラーDPT名
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラーコード
    private static final String ERROR_DPT_CODE = ERROR_DPT.keySet().toString().replace("[", "").replace("]", "");
    // システムコントロールより店集計DPTコード取得
    private static final String TEN_SUMMARY_DPT_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_SYUKEI_BUNRUI1_CD);

    /** 数量登録MAX値 */
    // #6569 MOD 2022.05.13 SIEU.D (S)
    // private static final long SU_MAXNUM = 999999L;
    private static final long SU_MAXNUM = 999999999L;
    // #6569 MOD 2022.05.13 SIEU.D (E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "マスタ情報取得処理（部門点検）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "有効部門点検ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_BUMON_Tenken";

    /** インプットBean */
    private BumonTenkenMstJohoSyutokuDaoInputBean inputBean = null;

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
        PreparedStatementEx preparedStatementDelete = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // No.609 URIB013220 Mod 2016.03.08 NGUYEN.NTM (S)
            TouKaikeiNengetsuSelectDaoInputBean  inBean = new TouKaikeiNengetsuSelectDaoInputBean();
            inBean.setCompCd(COMP_CD);
            TouKaikeiNengetsuSelectDao selectDAO = new TouKaikeiNengetsuSelectDao();
            selectDAO.setInputObject(inBean);
            invoker.InvokeDao(selectDAO);
            TouKaikeiNengetsuSelectDaoOutputBean outBean = (TouKaikeiNengetsuSelectDaoOutputBean) selectDAO.getOutputObject();
            String calculatorDt = outBean.getUserKaikeiYr() + outBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;
            // No.609 URIB013220 Mod 2016.03.08 NGUYEN.NTM (E)
            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // 部門点検ワークから有効部門点検ワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getYukoWkBumonTenkenInsertSql());
            int i = 1;
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_01.getCode());
            // No.609 URIB013220 Del 2016.03.08 NGUYEN.NTM (S)
            // preparedStatementExIns.setString(i++, BATCH_DT);
            // No.609 URIB013220 Del 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_04.getCode());
//            preparedStatementExIns.setString(4, inputBean.getTimeChgStr());
//            preparedStatementExIns.setString(5, ErrorKbDictionary.ERROR_04.getCode());
            preparedStatementExIns.setLong(i++, SU_MAXNUM);
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(i++, SU_MAXNUM);
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_05.getCode());
//            preparedStatementExIns.setString(8, ErrorKbDictionary.ERROR_06.getCode());
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_02.getCode());
            // #1484対応 2016/07/26 Y.Itaki (S)
            // preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_03.getCode());
            // #1484対応 2016/07/26 Y.Itaki (E)
            // 2017/08/31 VINX N.Katou #5840 (S)
            // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (S)
//            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_09.getCode());
            // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (E)
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementExIns.setString(i++, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (S)
            preparedStatementExIns.setString(i++, calculatorDt);
            preparedStatementExIns.setString(i++, calculatorDt);
            // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (S)
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, COMP_CD);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // APPEND INSERTした内容確定する必要があるのでcommitを行う
            invoker.getDataBase().commit();

            // 店集計レコード作成
            preparedStatementExIns = null;
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getYukoWkBumonTenkenSummaryDataInsertSql());
            preparedStatementExIns.setString(1, TEN_SUMMARY_DPT_CD);
            preparedStatementExIns.setLong(2, SU_MAXNUM);
            preparedStatementExIns.setString(3, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(4, SU_MAXNUM);
            preparedStatementExIns.setString(5, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());

            preparedStatementExIns.setString(7, userId);
            preparedStatementExIns.setString(8, dbServerTime);
            preparedStatementExIns.setString(9, userId);
            preparedStatementExIns.setString(10, dbServerTime);

            preparedStatementExIns.setString(11, COMP_CD);
            preparedStatementExIns.setString(12, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(13, ErrorKbDictionary.ERROR_02.getCode());

            // ログ出力
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "店集計レコードを追加しました。");

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
     * 部門点検ワークから有効部門点検ワークへ登録するSQLを取得する
     *
     * @return 有効部門点検ワーク登録クエリー
     */
    private String getYukoWkBumonTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        // 【ERROR_DPT_CODE】は集約条件に使用するため、SQLに直接埋め込む
        // ※パラメタで渡すと、SQL解析時点で不特定のため、GROUP BYの要素とみなされない

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_BUMON_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,ERR_KB ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("    ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN '" + ERROR_DPT_CODE + "' ");
        sql.append("        ELSE BUNRUI1_CD ");
        sql.append("     END AS BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
//        sql.append("    ,RTTH_TIME_NO AS TIME_NO ");
        sql.append("    ,SUM(KYAKU_QT) ");
        sql.append("    ,SUM(URIAGE_QT) ");
        sql.append("    ,SUM(URIAGE_VL) ");
        sql.append("    ,SUM(NEBIKI_REGI_QT) ");
        sql.append("    ,SUM(NEBIKI_REGI_VL) ");
        sql.append("    ,CASE ");
        sql.append("        WHEN MAX(RT_TENPO_CD) IS NULL THEN ? ");
        // No.609 URIB013220 Mod 2016.03.08 NGUYEN.NTM (S)
        // sql.append("        WHEN KEIJO_DT < ? THEN ? ");
        sql.append("        WHEN KEIJO_DT < SEISAN_ST_DT OR KEIJO_DT > SEISAN_ED_DT THEN ? ");
        // No.609 URIB013220 Mod 2016.03.08 NGUYEN.NTM (E)
//        sql.append("        WHEN RTTH_TIME_NO <> ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(NEBIKI_REGI_QT) > ? THEN ? ");
//        sql.append("        WHEN MAX(RTTH_TIME_NO) IS NULL THEN ? ");
        sql.append("        WHEN MAX(RFTB_BUNRUI1_CD) IS NULL THEN ? ");
        // #1484対応 2016/07/26 Y.Itaki (S)
        //sql.append("        WHEN MAX(RDS_SYOHIN_CD) IS NULL THEN ? ");
        // #1484対応 2016/07/26 Y.Itaki (E)
        // 2017/08/31 VINX N.Katou #5840 (S)
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (S)
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG = '1' THEN ? ");
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG = '1' THEN ? ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (E)
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("    ,SUM(ARARI_VL) ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT   ");
        sql.append("             COMP_CD ");
        sql.append("            ,SUBSTR(WBT.KEIJO_DT,1, 4) || SUBSTR(WBT.KEIJO_DT,6, 2) || SUBSTR(WBT.KEIJO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("            ,WBT.TENPO_CD ");
        sql.append("            ,WBT.BUNRUI1_CD AS BUNRUI1_CD ");
        sql.append("            ,TO_NUMBER(KYAKU_QT) AS KYAKU_QT ");
        sql.append("            ,TO_NUMBER(URIAGE_QT) AS URIAGE_QT ");
        sql.append("            ,TO_NUMBER(URIAGE_VL) AS URIAGE_VL ");
        sql.append("            ,TO_NUMBER(NEBIKI_REGI_QT) AS NEBIKI_REGI_QT ");
        sql.append("            ,TO_NUMBER(NEBIKI_REGI_VL) AS NEBIKI_REGI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("            ,TO_NUMBER(ARARI_VL) AS ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("            ,RT.RT_TENPO_CD ");
        sql.append("            ,RFTB.RFTB_BUNRUI1_CD AS RFTB_BUNRUI1_CD ");
        sql.append("            ,RDS.RDS_SYOHIN_CD ");
        sql.append("            ,WBT.TIME_NO ");
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("            ,RT.SEISAN_ST_DT AS SEISAN_ST_DT ");
        sql.append("            ,RT.SEISAN_ED_DT AS SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("            ,DTSS.DTSS_SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("            ,DTSS.DTSS_ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (E)
//        sql.append("            ,RTTH.RTTH_TIME_NO ");
        sql.append("        FROM ");
        sql.append("            ( ");
        sql.append("            SELECT ");
        sql.append("                 COMP_CD ");
        sql.append("                ,KEIJO_DT ");
        sql.append("                ,TENPO_CD ");
        sql.append("                ,COALESCE(RPB.RPB_BUNRUI1_CD, RPAD(SUBSTR(WBT.BUNRUI1_CD,1),4)) AS BUNRUI1_CD ");
//        sql.append("                ,COALESCE(RPB.RPB_BUNRUI1_CD, RPAD(SUBSTR(WBT.BUNRUI1_CD,3),4)) AS BUNRUI1_CD ");
        sql.append("                ,KYAKU_QT ");
        sql.append("                ,URIAGE_QT ");
        sql.append("                ,URIAGE_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("                ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("                ,NEBIKI_REGI_QT ");
        sql.append("                ,NEBIKI_REGI_VL ");
        sql.append("                ,TIME_NO ");
        sql.append("            FROM ");
        sql.append("                WK_BUMON_TENKEN WBT ");
        sql.append("                LEFT JOIN ");
        // ＜SUBPOS用部門変換マスタ＞
        sql.append("                    ( ");
        sql.append("                    SELECT ");
        sql.append("                         RPB1.COMP_CD           AS RPB_COMP_CD ");
        sql.append("                        ,RPB1.BUNRUI1_MAE_CD    AS RPB_BUNRUI1_MAE_CD ");
        sql.append("                        ,RPB1.BUNRUI1_CD        AS RPB_BUNRUI1_CD ");
        sql.append("                    FROM ");
        sql.append("                        R_POS_BUMON_HENKAN RPB1 ");
        sql.append("                    ) RPB ");
        sql.append("                ON ");
        sql.append("                    WBT.COMP_CD                         = RPB.RPB_COMP_CD       AND ");
        sql.append("                    RPAD(SUBSTR(WBT.BUNRUI1_CD,1),4)    = RPB.RPB_BUNRUI1_MAE_CD ");
//        sql.append("                    RPAD(SUBSTR(WBT.BUNRUI1_CD,3),4)    = RPB.RPB_BUNRUI1_MAE_CD ");
        sql.append("            ) WBT ");
        sql.append("            LEFT JOIN ");
        // ＜SUB店舗マスタ＞
        sql.append("                (  ");
        sql.append("                    SELECT   ");
        sql.append("                        RT.TENPO_CD AS RT_TENPO_CD ");
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("                        ,COALESCE(RT.SEISAN_ST_DT, ");
        sql.append("                            CASE ");
        sql.append("                                WHEN RT.KAITEN_DT > ? THEN RT.KAITEN_DT ");
        sql.append("                                ELSE ? END ");
        sql.append("                        ) AS SEISAN_ST_DT ");
        sql.append("                        ,COALESCE(RT.SEISAN_ED_DT, RT.ZAIMU_END_DT) AS SEISAN_ED_DT ");
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (E)
        sql.append("                    FROM   ");
        sql.append("                        R_TENPO RT  ");
        sql.append("                    WHERE ");
        sql.append("                        RT.HOJIN_CD      = ?        AND ");
        sql.append("                        RT.TENPO_KB      = '1'      AND ");
        sql.append("                        RT.KAITEN_DT    <= ?        AND ");
        sql.append("                        RT.ZAIMU_END_DT >= ?        AND ");
        sql.append("                        RT.DELETE_FG     = '0'  ");
        sql.append("                ) RT  ");
        sql.append("            ON   ");
        sql.append("                WBT.TENPO_CD = RT.RT_TENPO_CD   ");
        sql.append("            LEFT JOIN   ");
        // ＜SUB店DPTマスタ＞
        sql.append("                (  ");
        sql.append("                SELECT   ");
        sql.append("                     RFTB1.COMP_CD              AS RFTB_COMP_CD  ");
        sql.append("                    ,RFTB1.TENPO_CD             AS RFTB_TENPO_CD  ");
        sql.append("                    ,RFTB1.BUNRUI1_CD           AS RFTB_BUNRUI1_CD  ");
        sql.append("                FROM  ");
        sql.append("                    R_FI_TENPO_BUNRUI1 RFTB1   ");
        sql.append("                    INNER JOIN   ");
        sql.append("                        (  ");
        sql.append("                        SELECT   ");
        sql.append("                             RFTB.COMP_CD  ");
        sql.append("                            ,RFTB.TENPO_CD  ");
        sql.append("                            ,RFTB.BUNRUI1_CD  ");
        sql.append("                            ,MAX(RFTB.YUKO_DT) AS YUKO_DT  ");
        sql.append("                        FROM  ");
        sql.append("                            R_FI_TENPO_BUNRUI1 RFTB  ");
        sql.append("                        WHERE  ");
        sql.append("                            RFTB.COMP_CD     = ?    AND   ");
        sql.append("                            RFTB.YUKO_DT    <= ?  ");
        sql.append("                        GROUP BY   ");
        sql.append("                             RFTB.COMP_CD  ");
        sql.append("                            ,RFTB.TENPO_CD  ");
        sql.append("                            ,RFTB.BUNRUI1_CD  ");
        sql.append("                        ) RFTB2  ");
        sql.append("                    ON  ");
        sql.append("                        RFTB1.COMP_CD           = RFTB2.COMP_CD     AND   ");
        sql.append("                        RFTB1.TENPO_CD          = RFTB2.TENPO_CD    AND   ");
        sql.append("                        RFTB1.BUNRUI1_CD        = RFTB2.BUNRUI1_CD  AND   ");
        sql.append("                        RFTB1.YUKO_DT           = RFTB2.YUKO_DT     AND   ");
        sql.append("                        RFTB1.TORIATSUKAI_KB    = '1'  ");
        sql.append("                ) RFTB   ");
        sql.append("            ON   ");
        sql.append("                WBT.COMP_CD                         = RFTB.RFTB_COMP_CD     AND   ");
        sql.append("                WBT.TENPO_CD    = RFTB.RFTB_TENPO_CD    AND   ");
        sql.append("                WBT.BUNRUI1_CD                      = RFTB.RFTB_BUNRUI1_CD ");
        sql.append("            LEFT JOIN   ");
        // ＜SUB代表商品マスタ＞
        sql.append("                (  ");
        sql.append("                SELECT   ");
        sql.append("                     RDS1.COMP_CD           AS RDS_COMP_CD  ");
        sql.append("                    ,RDS1.BUNRUI1_CD        AS RDS_BUNRUI1_CD  ");
        sql.append("                    ,RDS1.SYOHIN_CD         AS RDS_SYOHIN_CD ");
        sql.append("                    ,RDS1.ZEI_KB            AS RDS_ZEI_KB ");
        sql.append("                    ,RDS1.TAX_RATE_KB       AS RDS_TAX_RATE_KB ");
        sql.append("                    ,RDS1.TAX_RT            AS RDS_TAX_RT ");
        sql.append("                FROM   ");
        sql.append("                    R_DAIHYO_SYOHIN RDS1   ");
        sql.append("                    INNER JOIN   ");
        sql.append("                        (  ");
        sql.append("                        SELECT   ");
        sql.append("                             RDS.COMP_CD  ");
        sql.append("                            ,RDS.BUNRUI1_CD  ");
        sql.append("                            ,MAX(RDS.YUKO_DT) AS YUKO_DT  ");
        sql.append("                        FROM  ");
        sql.append("                            R_DAIHYO_SYOHIN RDS  ");
        sql.append("                        WHERE  ");
        sql.append("                            RDS.COMP_CD  = ?    AND   ");
        sql.append("                            RDS.YUKO_DT <= ?  ");
        sql.append("                        GROUP BY   ");
        sql.append("                             RDS.COMP_CD  ");
        sql.append("                            ,RDS.BUNRUI1_CD  ");
        sql.append("                        ) RDS2  ");
        sql.append("                    ON  ");
        sql.append("                        RDS1.COMP_CD    = RDS2.COMP_CD      AND   ");
        sql.append("                        RDS1.BUNRUI1_CD = RDS2.BUNRUI1_CD   AND   ");
        sql.append("                        RDS1.YUKO_DT    = RDS2.YUKO_DT  ");
        sql.append("                ) RDS   ");
        sql.append("            ON   ");
        sql.append("                WBT.COMP_CD             = RDS.RDS_COMP_CD       AND   ");
        sql.append("                RFTB.RFTB_BUNRUI1_CD    = RDS.RDS_BUNRUI1_CD  ");
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("            LEFT JOIN   ");
        sql.append("                (  ");
        sql.append("                    SELECT   ");
        sql.append("                        DTSS.TENPO_CD AS DTSS_TENPO_CD ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("                        ,DTSS.SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("                        ,DTSS.ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("                    FROM   ");
        sql.append("                        DT_TEN_SEISAN_STATE DTSS  ");
        sql.append("                    WHERE  ");
        sql.append("                        DTSS.COMP_CD    = ?     AND ");
        sql.append("                        DTSS.KEIJO_DT   = ?         ");
        sql.append("                ) DTSS  ");
        sql.append("            ON   ");
        sql.append("                WBT.TENPO_CD = DTSS.DTSS_TENPO_CD   ");
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (E)
//        sql.append("            LEFT JOIN  ");
        // ＜店別時間帯変換マスタ＞
//        sql.append("                ( ");
//        sql.append("                SELECT  ");
//        sql.append("                     RTTH.COMP_CD       AS RTTH_COMP_CD ");
//        sql.append("                    ,RTTH.TENPO_CD      AS RTTH_TENPO_CD ");
//        sql.append("                    ,RTTH.TIME_MAE_NO   AS RTTH_TIME_MAE_NO ");
//        sql.append("                    ,RTTH.TIME_NO       AS RTTH_TIME_NO ");
//        sql.append("                FROM ");
//        sql.append("                    R_TEN_TIME_HENKAN RTTH ");
//        sql.append("                ) RTTH ");
//        sql.append("                ON ");
//        sql.append("                    WBT.COMP_CD                         = RTTH.RTTH_COMP_CD     AND ");
//        sql.append("                    WBT.TENPO_CD    = RTTH.RTTH_TENPO_CD    AND ");
//        sql.append("                    WBT.TIME_NO                         = RTTH.RTTH_TIME_MAE_NO ");
        sql.append("    ) ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");
        sql.append("GROUP BY  ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN '" + ERROR_DPT_CODE + "' ");
        sql.append("        ELSE BUNRUI1_CD ");
        sql.append("     END ");
        sql.append("    ,TIME_NO ");
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("    ,SEISAN_ST_DT ");
        sql.append("    ,SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("    ,DTSS_SEISAN_STATE_FG ");
        sql.append("    ,DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.609 URIB013220 Add 2016.03.08 NGUYEN.NTM (E)
//        sql.append("    ,RTTH_TIME_NO ");


        return sql.toString();
    }


    /**
     * 有効部門点検ワーク店集計レコードを登録するSQLを取得する
     *
     * @return 有効部門点検ワーク店集計レコード登録クエリー
     */
    private String getYukoWkBumonTenkenSummaryDataInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_BUMON_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,? ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,0 ");
        sql.append("    ,SUM(URIAGE_QT) AS URIAGE_QT ");
        sql.append("    ,SUM(URIAGE_VL) AS URIAGE_VL ");
        sql.append("    ,SUM(NEBIKI_REGI_QT) AS NEBIKI_REGI_QT ");
        sql.append("    ,SUM(NEBIKI_REGI_VL) AS NEBIKI_REGI_VL ");
        sql.append("    ,CASE ");
        sql.append("        WHEN SUM(URIAGE_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(NEBIKI_REGI_QT) > ? THEN ? ");
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM  ");
        sql.append("    WK_YUKO_BUMON_TENKEN ");
        sql.append("WHERE  ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ( ");
        sql.append("        ERR_KB = ?      OR ");
        sql.append("        ERR_KB = ? ");
        sql.append("    ) ");
        sql.append("GROUP BY ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input BumonTenkenMstJohoSyutokuDaoInputBean
     */
    public void setInputObject(Object input) throws Exception {
        this.inputBean = null;
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
            DaoIf dao = new BumonTenkenMstJohoSyutokuDao();
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
