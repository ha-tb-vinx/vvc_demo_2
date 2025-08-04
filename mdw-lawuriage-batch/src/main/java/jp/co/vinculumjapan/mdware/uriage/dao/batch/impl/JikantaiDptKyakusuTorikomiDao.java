package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.JikantaiDptKyakusuTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *
 * <p>タイトル: JikantaiDptKyakusuTorikomiDao.java クラス</p>
 * <p>説明　: 時間帯DPT客数取込処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.10.16) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @version 3.02 (2013.10.28) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №058
 * @version 3.03 (2013.10.30) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №068
 * @Version 3.04 (2014.01.31) Y.Tominaga 結合ﾃｽﾄNo.0112 POS時間帯取込処理改善 対応
 * @Version 3.05 (2016.11.17) VINX Y.Itaki FIVI対応 #2314対応
 * @Version 3.06 (2017.10.18) N.Katou FIVI対応 #6012対応
 * @Version 3.07 (2020.12.03) THONG.VQ #6284 MKV対応
 * @Version 3.08 (2021.09.14) SIEU.D #6339
 * @Version 3.09 (2023.10.17) TRI.NH #19142 MKV対応
 */
public class JikantaiDptKyakusuTorikomiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    // システムコントロールより店別客数取引コード取得
    private static final String TEN_KYAKU_TORIHIKI_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_KYAKU_TORIHIKI_CD);
    // システムコントロールより店別客数設定項目取得
    private static final String TEN_KYAKU_KOMOKU = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_KYAKU_KOMOKU);

    // システムコントロールより店集計DPTコード取得
    private static final String TEN_SUMMARY_DPT_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_SYUKEI_BUNRUI1_CD);

    /** バッチ処理名 */
    private static final String DAO_NAME = "時間帯DPT客数取込処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "時間帯DPT客数ワーク";
    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_JIKANTAI_DPT_KYKUSU";

    /** 事業部コード 00 */
    private static final String JIGYOBUCD_00 = "00";
    /** 事業部コード 01 */
    private static final String JIGYOBUCD_01 = "01";
    /** 業種コード 00 */
    private static final String GYOSYU_CD_00 = "00";
    /** 業種コード 01 */
    private static final String GYOSYU_CD_01 = "01";
    /** テナント区分 */
    private static final String TENANT_KB = "0";
    /** テナントコード */
    private static final String TENANT_CD = "000000";
    /** 売上区分 */
    private static final String URIAGE_KB = "00";

    /** 時間帯DPT客数取込処理InputBean*/
    private JikantaiDptKyakusuTorikomiDaoInputBean inputBean = null;

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

            // IF時間帯単品売上ワークを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 部門取込データからIF時間帯単品売上ワークを登録する
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkJikantaiDptKykusuInsertSql());

            int i = 1;
            preparedStatementExIns.setString(i++, TEN_SUMMARY_DPT_CD);
            preparedStatementExIns.setString(i++, TEN_SUMMARY_DPT_CD);
            preparedStatementExIns.setString(i++, TEN_SUMMARY_DPT_CD);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, COMP_CD);
//            preparedStatementExIns.setString(9, BATCH_DT);
            preparedStatementExIns.setString(i++, TEN_KYAKU_TORIHIKI_CD);
//            preparedStatementExIns.setString(11, inputBean.getTimeChgStr());
            preparedStatementExIns.setString(i++, COMP_CD);
//            preparedStatementExIns.setString(12, BATCH_DT);
//            preparedStatementExIns.setString(14, inputBean.getTimeChgStr());

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
     * 部門精算データから時間帯DPT客数ワークを登録するSQLを取得する
     *
     * @return 時間帯DPT客数ワーク登録SQL
     */
    private String getWkJikantaiDptKykusuInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_JIKANTAI_DPT_KYKUSU( ");
        sql.append("     COMP_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,YR ");
        sql.append("    ,MN ");
        sql.append("    ,DD ");
        sql.append("    ,JIGYOBU_CD ");
        sql.append("    ,GYOSYU_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_TM ");
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
        sql.append("SELECT ");
        sql.append("     DBT.COMP_CD ");
        sql.append("    ,TRIM(DBT.TENPO_CD) AS TENPO_CD ");
        sql.append("    ,SUBSTR(DBT.KEIJO_DT, 1, 4) AS YR ");
        sql.append("    ,SUBSTR(DBT.KEIJO_DT, 5, 2) AS MN ");
        sql.append("    ,SUBSTR(DBT.KEIJO_DT, 7, 2) AS DD ");
        sql.append("    ,CASE DBT.BUNRUI1_CD ");
        sql.append("        WHEN ? THEN '" + JIGYOBUCD_00 + "' ");
        sql.append("        ELSE '" + JIGYOBUCD_01 + "' ");
        sql.append("     END AS JIGYOBUCD ");
        sql.append("    ,CASE DBT.BUNRUI1_CD ");
        sql.append("        WHEN ? THEN '" + GYOSYU_CD_00 + "' ");
        sql.append("        ELSE '" + GYOSYU_CD_01 + "' ");
        sql.append("     END AS GYOSYU_CD ");
        sql.append("    ,DBT.BUNRUI1_CD ");
        // #6012 2017.10.18 N.Katou  (S)
//        sql.append("    ,DBT.TIME_NO ");
        // #6284 URIB014040 UPD 2020.12.03 THONG.VQ (S)
        //sql.append("    ,CASE DBT.TIME_NO ");
        //sql.append("     WHEN '0000' THEN '2400' ");
        //sql.append("     ELSE DBT.TIME_NO ");
        //sql.append("     END AS TIME_NO ");
        // #19142 2020.12.03 TRI.NH MOD (S)
        // sql.append("    ,CASE SUBSTR(DBT.TIME_NO,1,2) ");
        // sql.append("     WHEN '00' THEN '24'||SUBSTR(DBT.TIME_NO,3,2) ");
        sql.append("    ,CASE ");
        sql.append("     WHEN SUBSTR(DBT.TIME_NO,1,2) < '07' THEN (CAST(SUBSTR(DBT.TIME_NO,1,2) AS INTEGER) + 24 ) ||SUBSTR(DBT.TIME_NO,3,2) ");
        // #19142 2020.12.03 TRI.NH MOD (S)
        sql.append("     ELSE DBT.TIME_NO ");
        sql.append("     END AS TIME_NO ");
        // #6284 URIB014040 UPD 2020.12.03 THONG.VQ (E)
        // #6012 2017.10.18 N.Katou  (E)
        sql.append("    ,'" + TENANT_KB + "' AS TENANT_KB ");
        sql.append("    ,'" + TENANT_CD + "' AS TENANT_CD ");
        sql.append("    ,'" + URIAGE_KB + "' AS URIAGE_KB ");
        sql.append("    ,DBT.URIAGE_VL ");
        sql.append("    ,0 AS URIAGE_GENKA_VL ");
        sql.append("    ,DBT.URIAGE_QT ");
        sql.append("    ,DBT.NEBIKI_REGI_VL ");
        sql.append("    ,DBT.NEBIKI_REGI_QT ");
        sql.append("    ,0 AS KAKO_VL ");
        sql.append("    ,0 AS KAKO_QT ");
        sql.append("    ,0 AS HAIKI_VL ");
        sql.append("    ,0 AS HAIKI_QT ");
        sql.append("    ,0 AS HENPIN_VL ");
        sql.append("    ,0 AS HENPIN_QT ");
        sql.append("    ,CASE DBT.BUNRUI1_CD ");
        sql.append("        WHEN ? THEN COALESCE(DRTT.TENBETSU_KYAKU_QT, 0) ");
        sql.append("        ELSE DBT.KYAKU_QT ");
        sql.append("     END AS BUNRUI1_KYAKU_QT ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("    ,DBT.ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    DT_BUMON_TENKEN DBT ");
        sql.append("    LEFT JOIN ");
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("             DRTT.COMP_CD ");
        sql.append("            ,DRTT.KEIJO_DT ");
        sql.append("            ,DRTT.TENPO_CD ");
        // #6012 2017.10.18 N.Katou  (S)
//        sql.append("            ,DRTT.TIME_NO ");
        // #6284 URIB014040 UPD 2020.12.03 THONG.VQ (S)
        //sql.append("    ,CASE DRTT.TIME_NO ");
        //sql.append("     WHEN '0000' THEN '2400' ");
        //sql.append("     ELSE DRTT.TIME_NO ");
        //sql.append("     END AS TIME_NO ");
        sql.append("            ,DRTT.TIME_NO ");
        // #6284 URIB014040 UPD 2020.12.03 THONG.VQ (E)
        // #6012 2017.10.18 N.Katou  (E)
        sql.append("            ,SUM(DRTT." + TEN_KYAKU_KOMOKU + ") AS TENBETSU_KYAKU_QT ");
        sql.append("        FROM ");
        sql.append("            DT_REGI_TORIHIKI_TENKEN DRTT ");
        sql.append("        WHERE ");
        sql.append("            DRTT.COMP_CD        = ?     AND ");
//        sql.append("            DRTT.KEIJO_DT       = ?     AND ");
        sql.append("            DRTT.TORIHIKI_CD    = ?     ");
//        sql.append("            DRTT.TIME_NO        = ? ");
        sql.append("        GROUP BY ");
        sql.append("             DRTT.COMP_CD ");
        sql.append("            ,DRTT.KEIJO_DT ");
        sql.append("            ,DRTT.TENPO_CD ");
        sql.append("            ,DRTT.TIME_NO ");
        sql.append("        ) DRTT ");
        sql.append("    ON ");
        sql.append("        DBT.COMP_CD     = DRTT.COMP_CD  AND ");
        sql.append("        DBT.KEIJO_DT    = DRTT.KEIJO_DT AND ");
        sql.append("        DBT.TENPO_CD    = DRTT.TENPO_CD AND ");
        sql.append("        DBT.TIME_NO     = DRTT.TIME_NO ");
        sql.append("WHERE ");
        sql.append("    DBT.COMP_CD     = ? ");
//        sql.append("    DBT.KEIJO_DT    = ? ");
//        sql.append("    DBT.TIME_NO     = ?  ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input JikantaiDptKyakusuTorikomiDaoInputBean
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
            DaoIf dao = new JikantaiDptKyakusuTorikomiDao();
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
