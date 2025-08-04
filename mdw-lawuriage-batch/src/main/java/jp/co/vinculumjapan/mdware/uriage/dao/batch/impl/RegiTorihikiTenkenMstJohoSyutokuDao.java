package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.ErrorKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.RegiTorihikiTenkenMstJohoSyutokuDaoInputBean;
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
 * <p>タイトル: RegiTorihikiTenkenMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理(レジ別取引点検)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2014.12.03) chou グローバル化対応 通貨対応
 * @version 1.01 (2016.03.08) NGUYEN.NTM 設計書No.610 #1161 FIVIMART対応
 * @version 1.02 (2017.04.10) X.Liu  #4553 FIVIMART対応
 * @Version 1.03 (2017.08.31) N.Katou #5840
 */
public class RegiTorihikiTenkenMstJohoSyutokuDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 客数MAX値 */
    private static final long KYAKU_QT_MAXNUM = 99999999999L;
    /** 点数/回数MAX値 */
    private static final long SU_MAXNUM = 999999L;
    /** 金額MAX値 */
    private static final long KINGAKU_MAXNUM = 9999999999999L;

    /** バッチ処理名 */
    private static final String DAO_NAME = "マスタ情報取得処理（レジ別取引点検）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "有効レジ別取引点検ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_REGI_TORIHIKI_TENKEN";
    /** レジ別取引点検取引コード */
    private static final String REGI_TENKEN_TORIHIKI_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.REGI_TENKEN_TORIHIKI_CD);

    /** インプットBean */
    private RegiTorihikiTenkenMstJohoSyutokuDaoInputBean inputBean = null;

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
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        int insertCount = 0;
        try {

            // No.610 URIB013230 Mod 2016.03.08 NGUYEN.NTM (S)
            TouKaikeiNengetsuSelectDaoInputBean  inBean = new TouKaikeiNengetsuSelectDaoInputBean();
            inBean.setCompCd(COMP_CD);
            TouKaikeiNengetsuSelectDao selectDAO = new TouKaikeiNengetsuSelectDao();
            selectDAO.setInputObject(inBean);
            invoker.InvokeDao(selectDAO);
            TouKaikeiNengetsuSelectDaoOutputBean outBean = (TouKaikeiNengetsuSelectDaoOutputBean) selectDAO.getOutputObject();
            String calculatorDt = outBean.getUserKaikeiYr() + outBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;
            // No.610 URIB013230 Mod 2016.03.08 NGUYEN.NTM (E)
            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // TMPレジ別取引点検データから有効レジ別取引点検ワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getYukoWkRegiTorihikiTenkenInsertSql());
            int i = 1;
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_01.getCode());
            // No.610 URIB013230 Del 2016.03.08 NGUYEN.NTM (S)
            // preparedStatementExIns.setString(i++, BATCH_DT);
            // No.610 URIB013230 Del 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_04.getCode());
//            preparedStatementExIns.setString(4, inputBean.getTimeChgStr());
//            preparedStatementExIns.setString(5, ErrorKbDictionary.ERROR_04.getCode());
            preparedStatementExIns.setLong(i++, KYAKU_QT_MAXNUM);
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(i++, SU_MAXNUM);
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_05.getCode());
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(8, KINGAKU_MAXNUM);
            preparedStatementExIns.setDouble(i++, KINGAKU_MAXNUM);
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_05.getCode());
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(10, KINGAKU_MAXNUM);
            preparedStatementExIns.setDouble(i++, KINGAKU_MAXNUM);
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_05.getCode());
//            preparedStatementExIns.setString(12, ErrorKbDictionary.ERROR_06.getCode());
            // 2017/08/31 VINX N.Katou #5840 (S)
            // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (S)
//            preparedStatementExIns.setString(i++, ErrorKbDictionary.ERROR_09.getCode());
            // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (E)
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementExIns.setString(i++, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (S)
            preparedStatementExIns.setString(i++, calculatorDt);
            preparedStatementExIns.setString(i++, calculatorDt);
            // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (S)
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (E)
            preparedStatementExIns.setString(i++, REGI_TENKEN_TORIHIKI_CD);
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
     * TMPレジ別取引点検データから有効レジ別取引点検ワークへ登録するSQLを取得する
     *
     * @return 有効レジ別取引点検ワーク登録クエリー
     */
    private String getYukoWkRegiTorihikiTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_REGI_TORIHIKI_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,SUM(KYAKU_QT) AS KYAKU_QT ");
        sql.append("    ,SUM(TEN_KAISUU_QT) AS TEN_KAISUU_QT ");
        sql.append("    ,SUM(KINGAKU_VL) AS KINGAKU_VL ");
        sql.append("    ,SUM(NEBIKI_VL) AS NEBIKI_VL ");
        sql.append("    ,CASE ");
        sql.append("        WHEN MAX(RT_TENPO_CD) IS NULL THEN ? ");
        // No.610 URIB013230 Mod 2016.03.08 NGUYEN.NTM (S)
        // sql.append("        WHEN KEIJO_DT < ? THEN ? ");
        sql.append("        WHEN KEIJO_DT < SEISAN_ST_DT OR KEIJO_DT > SEISAN_ED_DT THEN ? ");
        // No.610 URIB013230 Mod 2016.03.08 NGUYEN.NTM (E)
//        sql.append("        WHEN RTTH_TIME_NO <> ? THEN ? ");
        sql.append("        WHEN SUM(KYAKU_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(TEN_KAISUU_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(KINGAKU_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(NEBIKI_VL) > ? THEN ? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (S)
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG = '1' THEN ? ");
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG = '1' THEN ? ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (E)
        // 2017/08/31 VINX N.Katou #5840 (E)
//        sql.append("        WHEN RTTH_TIME_NO IS NULL THEN ? ");
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT   ");
        sql.append("         COMP_CD ");
        sql.append("        ,SUBSTR(TRTT.EIGYO_DT,1, 4) || SUBSTR(TRTT.EIGYO_DT,6, 2) || SUBSTR(TRTT.EIGYO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("        ,TRTT.TENPO_CD ");
        sql.append("        ,FLOAR_NO ");
        sql.append("        ,REGI_NO ");
        sql.append("        ,TORIHIKI_CD ");
        sql.append("        ,TYPE_KB ");
        sql.append("        ,TO_NUMBER(KYAKU_QT) AS KYAKU_QT ");
        sql.append("        ,TO_NUMBER(TEN_KAISUU_QT) AS TEN_KAISUU_QT ");
        sql.append("        ,TO_NUMBER(KINGAKU_VL) AS KINGAKU_VL ");
        sql.append("        ,TO_NUMBER(NEBIKI_VL) AS NEBIKI_VL ");
        sql.append("        ,RT.RT_TENPO_CD ");
        sql.append("        ,TRTT.JIKANTAI_NO AS TIME_NO ");
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("        ,RT.SEISAN_ST_DT AS SEISAN_ST_DT ");
        sql.append("        ,RT.SEISAN_ED_DT AS SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        ,DTSS.DTSS_SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("        ,DTSS.DTSS_ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (E)
//        sql.append("        ,RTTH.RTTH_TIME_NO ");
        sql.append("    FROM   ");
        sql.append("        TMP_REGI_TORIHIKI_TENKEN TRTT   ");
        sql.append("        LEFT JOIN   ");
        // ＜SUB店舗マスタ＞
        sql.append("            (  ");
        sql.append("                SELECT   ");
        sql.append("                    RT.TENPO_CD AS RT_TENPO_CD ");
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("                    ,COALESCE(RT.SEISAN_ST_DT, ");
        sql.append("                        CASE ");
        sql.append("                            WHEN RT.KAITEN_DT > ? THEN RT.KAITEN_DT ");
        sql.append("                            ELSE ? END ");
        sql.append("                    ) AS SEISAN_ST_DT ");
        sql.append("                    ,COALESCE(RT.SEISAN_ED_DT, RT.ZAIMU_END_DT) AS SEISAN_ED_DT ");
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (E)
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
        sql.append("            TRTT.TENPO_CD = RT.RT_TENPO_CD   ");
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (S)
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
        sql.append("            TRTT.TENPO_CD = DTSS.DTSS_TENPO_CD   ");
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (E)
//        sql.append("                LEFT JOIN  ");
//        // ＜店別時間帯変換マスタ＞
//        sql.append("                    ( ");
//        sql.append("                    SELECT  ");
//        sql.append("                         RTTH.COMP_CD       AS RTTH_COMP_CD ");
//        sql.append("                        ,RTTH.TENPO_CD      AS RTTH_TENPO_CD ");
//        sql.append("                        ,RTTH.TIME_MAE_NO   AS RTTH_TIME_MAE_NO ");
//        sql.append("                        ,RTTH.TIME_NO       AS RTTH_TIME_NO ");
//        sql.append("                    FROM ");
//        sql.append("                        R_TEN_TIME_HENKAN RTTH ");
//        sql.append("                    ) RTTH ");
//        sql.append("                ON ");
//        sql.append("                    TRTT.COMP_CD                        = RTTH.RTTH_COMP_CD     AND ");
//        sql.append("                    TRTT.TENPO_CD   = RTTH.RTTH_TENPO_CD    AND ");
//        sql.append("                    TRTT.JIKANTAI_NO                    = RTTH.RTTH_TIME_MAE_NO ");
        sql.append("    WHERE ");
        sql.append("         TORIHIKI_CD = ? ");
        sql.append("    ) ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");
        sql.append("GROUP BY ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
//        sql.append("    ,RTTH_TIME_NO ");
        sql.append("    ,TORIHIKI_CD ");
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (S)
        sql.append("    ,SEISAN_ST_DT ");
        sql.append("    ,SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("    ,DTSS_SEISAN_STATE_FG ");
        sql.append("    ,DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.610 URIB013230 Add 2016.03.08 NGUYEN.NTM (E)
        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input RegiTorihikiTenkenMstJohoSyutokuDaoInputBean
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
            DaoIf dao = new RegiTorihikiTenkenMstJohoSyutokuDao();
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
