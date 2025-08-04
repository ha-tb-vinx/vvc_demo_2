package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.ErrorKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.TanpinTenkenMstJohoSyutokuDaoInputBean;
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
 * <p>タイトル: TanpinTenkenMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理(単品点検)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.01) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2014.01.31) Y.Tominaga 結合ﾃｽﾄNo.0112 POS時間帯取込処理改善 対応
 * @version 3.03 (2016.03.08) NGUYEN.NTM 設計書No.608 #1161 FIVIMART対応
 * @version 3.04 (2017.04.10) X.Liu  #4553 FIVIMART対応
 * @Version 3.05 (2017.08.31) VINX N.Katou #5840
 */
public class TanpinTenkenMstJohoSyutokuDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システムコントロールよりダミーDPTコード取得
    private static final String DUMMY_BUNRUI1_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI1_CD);
    // システムコントロールよりダミーラインコード取得
    private static final String DUMMY_BUNRUI2_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI2_CD);
    // システムコントロールよりダミークラスコード取得
    private static final String DUMMY_BUNRUI5_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI5_CD);

    /** 数量登録MAX値 */
    private static final long SU_MAXNUM = 999999L;

    /** バッチ処理名 */
    private static final String DAO_NAME = "マスタ情報取得処理（単品点検）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "有効単品点検ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_TANPIN_TENKEN";

    /** インプットBean */
    private TanpinTenkenMstJohoSyutokuDaoInputBean inputBean = null;

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
        PreparedStatementEx preparedStatementDelete = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;

        try {

            // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (S)
            TouKaikeiNengetsuSelectDaoInputBean  inBean = new TouKaikeiNengetsuSelectDaoInputBean();
            inBean.setCompCd(COMP_CD);
            TouKaikeiNengetsuSelectDao selectDAO = new TouKaikeiNengetsuSelectDao();
            selectDAO.setInputObject(inBean);
            invoker.InvokeDao(selectDAO);
            TouKaikeiNengetsuSelectDaoOutputBean outBean = (TouKaikeiNengetsuSelectDaoOutputBean) selectDAO.getOutputObject();
            String calculatorDt = outBean.getUserKaikeiYr() + outBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;
            // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (E)
            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // 単品点検ワークから有効単品点検ワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getYukoWkTanpinTenkenInsertSql());
            int i = 1;
            preparedStatementExIns.setString(i++, DUMMY_BUNRUI1_CD);
            preparedStatementExIns.setString(i++, DUMMY_BUNRUI1_CD);
            preparedStatementExIns.setString(i++, DUMMY_BUNRUI2_CD);
            preparedStatementExIns.setString(i++, DUMMY_BUNRUI2_CD);
            preparedStatementExIns.setString(i++, DUMMY_BUNRUI5_CD);
            preparedStatementExIns.setString(i++, DUMMY_BUNRUI5_CD);
            
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_01.getCode());
            // No.608 URIB013210 Del 2016.03.08 NGUYEN.NTM (S)
            // preparedStatementExIns.setString(i++, BATCH_DT);
            // No.608 URIB013210 Del 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_04.getCode());
//            preparedStatementExIns.setString(10, inputBean.getTimeChgStr());
//            preparedStatementExIns.setString(11, ErrorKbDictionary.ERROR_04.getCode());
            preparedStatementExIns.setLong(i++, SU_MAXNUM);
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_05.getCode());
//            preparedStatementExIns.setString(12, ErrorKbDictionary.ERROR_06.getCode());
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_02.getCode());
            // 2017/08/31 VINX N.Katou #5840 (S)
            // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (S)
//            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_09.getCode());
            // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (E)
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementExIns.setString(i++, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (S)
            preparedStatementExIns.setString(i++, calculatorDt);
            preparedStatementExIns.setString(i++, calculatorDt);
            // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (S)
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, COMP_CD);

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
     * 単品点検ワークから有効単品点検ワークへ登録するSQLを取得する
     * 
     * @return 単品点検データ登録クエリー
     */
    private String getYukoWkTanpinTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_TANPIN_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TANPIN_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,ARARI_VL ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TANPIN_CD ");
        sql.append("    ,TIME_NO ");
//        sql.append("    ,RTTH_TIME_NO ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,ARARI_VL ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        sql.append("        ELSE COALESCE(RFH_BUNRUI1_CD, RFS_BUNRUI1_CD, ?) ");
        sql.append("     END AS BUNRUI1_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        sql.append("        ELSE COALESCE(RFH_BUNRUI2_CD, RFS_BUNRUI2_CD, ?) ");
        sql.append("     END AS BUNRUI2_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        sql.append("        ELSE COALESCE(RFH_BUNRUI5_CD, RFS_BUNRUI5_CD, ?) ");
        sql.append("     END AS BUNRUI5_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RT_TENPO_CD IS NULL THEN ? ");
        // No.608 URIB013210 Mod 2016.03.08 NGUYEN.NTM (S)
        // sql.append("        WHEN KEIJO_DT < ? THEN ? ");
        sql.append("        WHEN KEIJO_DT < SEISAN_ST_DT OR KEIJO_DT > SEISAN_ED_DT THEN ? ");
        // No.608 URIB013210 Mod 2016.03.08 NGUYEN.NTM (E)
//        sql.append("        WHEN RTTH_TIME_NO <> ? THEN ? ");
        sql.append("        WHEN URIAGE_QT > ? THEN ? ");
//        sql.append("        WHEN RTTH_TIME_NO IS NULL THEN ? ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
        // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (S)
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG = '1' THEN ? ");
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG = '1' THEN ? ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (E)
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT   ");
        sql.append("         WTT.COMP_CD ");
        sql.append("        ,SUBSTR(WTT.KEIJO_DT,1, 4) || SUBSTR(WTT.KEIJO_DT,6, 2) || SUBSTR(WTT.KEIJO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("        ,WTT.TENPO_CD ");
        sql.append("        ,WTT.TANPIN_CD ");
        sql.append("        ,TO_NUMBER(WTT.URIAGE_QT) AS URIAGE_QT ");
        sql.append("        ,TO_NUMBER(WTT.URIAGE_VL) AS URIAGE_VL ");
        sql.append("        ,TO_NUMBER(WTT.NEBIKI_REGI_QT) AS NEBIKI_REGI_QT ");
        sql.append("        ,TO_NUMBER(WTT.NEBIKI_REGI_VL) AS NEBIKI_REGI_VL ");
        sql.append("        ,TO_NUMBER(WTT.ARARI_VL) AS ARARI_VL ");
        sql.append("        ,RT.RT_TENPO_CD ");
        sql.append("        ,RFS.RFS_SYOHIN_CD ");
        sql.append("        ,RFS.RFS_BUNRUI1_CD ");
        sql.append("        ,RFS.RFS_BUNRUI2_CD ");
        sql.append("        ,RFS.RFS_BUNRUI5_CD ");
        sql.append("        ,RFH.RFH_COMP_CD ");
        sql.append("        ,RFH.RFH_TENPO_CD ");
        sql.append("        ,RFH.RFH_SYOHIN_CD ");
        sql.append("        ,RFH.RFH_BUNRUI1_CD ");
        sql.append("        ,RFH.RFH_BUNRUI2_CD ");
        sql.append("        ,RFH.RFH_BUNRUI5_CD ");
        sql.append("        ,RFTB.RFTB_COMP_CD ");
        sql.append("        ,RFTB.RFTB_TENPO_CD ");
        sql.append("        ,RFTB.RFTB_BUNRUI1_CD ");
        sql.append("        ,WTT.TIME_NO ");
        // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("        ,RT.SEISAN_ST_DT AS SEISAN_ST_DT ");
        sql.append("        ,RT.SEISAN_ED_DT AS SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        ,DTSS.DTSS_SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("        ,DTSS.DTSS_ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (E)
//        sql.append("        ,RTTH.RTTH_TIME_NO ");
        sql.append("    FROM   ");
        sql.append("        WK_TANPIN_TENKEN WTT   ");
        sql.append("        LEFT JOIN   ");
        // ＜SUB店舗マスタ＞
        sql.append("            (  ");
        sql.append("                SELECT   ");
        sql.append("                    RT.TENPO_CD AS RT_TENPO_CD ");
        // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("                    ,COALESCE(RT.SEISAN_ST_DT, ");
        sql.append("                        CASE ");
        sql.append("                            WHEN RT.KAITEN_DT > ? THEN RT.KAITEN_DT ");
        sql.append("                            ELSE ? END ");
        sql.append("                    ) AS SEISAN_ST_DT ");
        sql.append("                    ,COALESCE(RT.SEISAN_ED_DT, RT.ZAIMU_END_DT) AS SEISAN_ED_DT ");
        // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (E)
        sql.append("                FROM   ");
        sql.append("                    R_TENPO RT  ");
        sql.append("                WHERE  ");
        sql.append("                    RT.HOJIN_CD      = ?    AND   ");
        sql.append("                    RT.TENPO_KB      = '1'  AND   ");
        sql.append("                    RT.KAITEN_DT    <= ?    AND   ");
        sql.append("                    RT.ZAIMU_END_DT >= ?    AND   ");
        sql.append("                    RT.DELETE_FG     = '0'  ");
        sql.append("            ) RT  ");
        sql.append("        ON   ");
        sql.append("            WTT.TENPO_CD = RT.RT_TENPO_CD   ");
        sql.append("        LEFT JOIN   ");
        // ＜SUB商品マスタ＞
        sql.append("            (  ");
        sql.append("            SELECT   ");
        sql.append("                 RFS1.SYOHIN_CD             AS RFS_SYOHIN_CD  ");
        sql.append("                ,RFS1.BUNRUI1_CD            AS RFS_BUNRUI1_CD  ");
        sql.append("                ,RFS1.BUNRUI2_CD            AS RFS_BUNRUI2_CD  ");
        sql.append("                ,RFS1.BUNRUI5_CD            AS RFS_BUNRUI5_CD  ");
        sql.append("            FROM   ");
        sql.append("                R_FI_SYOHIN RFS1   ");
        sql.append("                INNER JOIN   ");
        sql.append("                    (  ");
        sql.append("                    SELECT   ");
        sql.append("                         RFS.SYOHIN_CD  ");
        sql.append("                        ,MAX(RFS.YUKO_DT) AS YUKO_DT  ");
        sql.append("                    FROM   ");
        sql.append("                        R_FI_SYOHIN RFS  ");
        sql.append("                    WHERE   ");
        sql.append("                        RFS.YUKO_DT <= ?  ");
        sql.append("                    GROUP BY   ");
        sql.append("                        RFS.SYOHIN_CD  ");
        sql.append("                    ) RFS2  ");
        sql.append("                ON   ");
        sql.append("                    RFS1.SYOHIN_CD  = RFS2.SYOHIN_CD    AND  ");
        sql.append("                    RFS1.YUKO_DT    = RFS2.YUKO_DT  ");
        sql.append("            ) RFS   ");
        sql.append("        ON   ");
        sql.append("            WTT.TANPIN_CD = RFS.RFS_SYOHIN_CD  ");
        sql.append("        LEFT JOIN   ");
        // ＜SUB販売マスタ＞
        sql.append("            (  ");
        sql.append("            SELECT   ");
        sql.append("                 RFH1.COMP_CD               AS RFH_COMP_CD  ");
        sql.append("                ,RFH1.TENPO_CD              AS RFH_TENPO_CD  ");
        sql.append("                ,RFH1.SYOHIN_CD             AS RFH_SYOHIN_CD  ");
        sql.append("                ,RFH1.BUNRUI1_CD            AS RFH_BUNRUI1_CD  ");
        sql.append("                ,RFH1.BUNRUI2_CD            AS RFH_BUNRUI2_CD  ");
        sql.append("                ,RFH1.BUNRUI5_CD            AS RFH_BUNRUI5_CD  ");
        sql.append("            FROM   ");
        sql.append("                R_FI_HANBAI RFH1   ");
        sql.append("                INNER JOIN  ");
        sql.append("                    (  ");
        sql.append("                    SELECT   ");
        sql.append("                         RFH.COMP_CD  ");
        sql.append("                        ,RFH.TENPO_CD  ");
        sql.append("                        ,RFH.SYOHIN_CD  ");
        sql.append("                        ,MAX(RFH.TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("                    FROM  ");
        sql.append("                        R_FI_HANBAI RFH  ");
        sql.append("                    WHERE  ");
        sql.append("                        RFH.COMP_CD          = ?    AND  ");
        sql.append("                        RFH.TEKIYO_START_DT <= ?  ");
        sql.append("                    GROUP BY   ");
        sql.append("                         RFH.COMP_CD  ");
        sql.append("                        ,RFH.TENPO_CD  ");
        sql.append("                        ,RFH.SYOHIN_CD  ");
        sql.append("                    ) RFH2  ");
        sql.append("                ON   ");
        sql.append("                    RFH1.COMP_CD            = RFH2.COMP_CD      AND  ");
        sql.append("                    RFH1.TENPO_CD           = RFH2.TENPO_CD     AND  ");
        sql.append("                    RFH1.SYOHIN_CD          = RFH2.SYOHIN_CD    AND   ");
        sql.append("                    RFH1.TEKIYO_START_DT    = RFH2.TEKIYO_START_DT  ");
        sql.append("            ) RFH  ");
        sql.append("            ON   ");
        sql.append("                WTT.COMP_CD                         = RFH.RFH_COMP_CD  ");
        sql.append("            AND WTT.TENPO_CD    = RFH.RFH_TENPO_CD  ");
        sql.append("            AND WTT.TANPIN_CD                       = RFH.RFH_SYOHIN_CD  ");
        sql.append("        LEFT JOIN   ");
        // ＜SUB店DPTマスタ＞
        sql.append("            (  ");
        sql.append("            SELECT   ");
        sql.append("                 RFTB1.COMP_CD              AS RFTB_COMP_CD  ");
        sql.append("                ,RFTB1.TENPO_CD             AS RFTB_TENPO_CD  ");
        sql.append("                ,RFTB1.BUNRUI1_CD           AS RFTB_BUNRUI1_CD  ");
        sql.append("            FROM  ");
        sql.append("                R_FI_TENPO_BUNRUI1 RFTB1   ");
        sql.append("                INNER JOIN   ");
        sql.append("                    (  ");
        sql.append("                    SELECT   ");
        sql.append("                         RFTB.COMP_CD  ");
        sql.append("                        ,RFTB.TENPO_CD  ");
        sql.append("                        ,RFTB.BUNRUI1_CD  ");
        sql.append("                        ,MAX(RFTB.YUKO_DT) AS YUKO_DT  ");
        sql.append("                    FROM  ");
        sql.append("                        R_FI_TENPO_BUNRUI1 RFTB  ");
        sql.append("                    WHERE  ");
        sql.append("                        RFTB.COMP_CD     = ?    AND   ");
        sql.append("                        RFTB.YUKO_DT    <= ?  ");
        sql.append("                    GROUP BY   ");
        sql.append("                         RFTB.COMP_CD  ");
        sql.append("                        ,RFTB.TENPO_CD  ");
        sql.append("                        ,RFTB.BUNRUI1_CD  ");
        sql.append("                    ) RFTB2  ");
        sql.append("                ON  ");
        sql.append("                    RFTB1.COMP_CD           = RFTB2.COMP_CD     AND   ");
        sql.append("                    RFTB1.TENPO_CD          = RFTB2.TENPO_CD    AND   ");
        sql.append("                    RFTB1.BUNRUI1_CD        = RFTB2.BUNRUI1_CD  AND   ");
        sql.append("                    RFTB1.YUKO_DT           = RFTB2.YUKO_DT ");
        sql.append("                WHERE  ");
        sql.append("                    RFTB1.TORIATSUKAI_KB    = '1'  ");
        sql.append("            ) RFTB   ");
        sql.append("        ON   ");
        sql.append("            WTT.COMP_CD                         = RFTB.RFTB_COMP_CD     AND   ");
        sql.append("            WTT.TENPO_CD    = RFTB.RFTB_TENPO_CD    AND   ");
        sql.append("            RFS.RFS_BUNRUI1_CD                  = RFTB.RFTB_BUNRUI1_CD   ");
        // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("        LEFT JOIN   ");
        sql.append("            (  ");
        sql.append("                SELECT   ");
        sql.append("                    DTSS.TENPO_CD AS DTSS_TENPO_CD ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("                    ,DTSS.SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("                    ,DTSS.ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("                FROM   ");
        sql.append("                    DT_TEN_SEISAN_STATE DTSS  ");
        sql.append("                WHERE  ");
        sql.append("                    DTSS.COMP_CD    = ?     AND ");
        sql.append("                    DTSS.KEIJO_DT   = ?         ");
        sql.append("            ) DTSS  ");
        sql.append("        ON   ");
        sql.append("            WTT.TENPO_CD = DTSS.DTSS_TENPO_CD   ");
        // No.608 URIB013210 Add 2016.03.08 NGUYEN.NTM (E)
//        sql.append("        LEFT JOIN ");
        // ＜店別時間帯変換マスタ＞
//        sql.append("            ( ");
//        sql.append("            SELECT  ");
//        sql.append("                 RTTH.COMP_CD       AS RTTH_COMP_CD ");
//        sql.append("                ,RTTH.TENPO_CD      AS RTTH_TENPO_CD ");
//        sql.append("                ,RTTH.TIME_MAE_NO   AS RTTH_TIME_MAE_NO ");
//        sql.append("                ,RTTH.TIME_NO       AS RTTH_TIME_NO ");
//        sql.append("            FROM ");
//        sql.append("                R_TEN_TIME_HENKAN RTTH ");
//        sql.append("            ) RTTH ");
//        sql.append("            ON ");
//        sql.append("                WTT.COMP_CD                         = RTTH.RTTH_COMP_CD     AND ");
//        sql.append("                WTT.TENPO_CD    = RTTH.RTTH_TENPO_CD    AND ");
//        sql.append("                WTT.TIME_NO                         = RTTH.RTTH_TIME_MAE_NO ");
        sql.append("    WHERE COMP_CD = ? ");
        sql.append("    ) ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object input TanpinTenkenMstJohoSyutokuDaoInputBean
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
            DaoIf dao = new TanpinTenkenMstJohoSyutokuDao();
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
