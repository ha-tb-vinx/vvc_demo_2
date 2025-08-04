package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

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
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル: MaseterCheckTmpPosFInvoiceDao クラス</p>
 * <p>説明:マスタチェック（Fレコード）</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.10.14) k.Hyo FIVI対応
 * @Version 1.01 (2016.11.02) Y.Itaki FIVI対応(#2263)
 * @Version 1.02 (2016.11.10) Y.Itaki FIVI対応(#2725)
 * @Version 1.03 (2016.11.29) J.Endo  FIVI対応(#2945)
 * @Version 1.04 (2016.12.16) T.Kamei FIVI対応(#3344)
 * @Version 1.05 (2017.04.10) X.Liu   FIVI対応(#4553)
 * @Version 1.06 (2017.06.14) X.Liu   FIVI対応(#5399)
 * @Version 1.07 (2017.07.10) X.Liu   FIVI対応(#5580)
 *
 * @see なし
 */
public class MaseterCheckTmpPosFInvoiceDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "マスタチェック（Fレコード）";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // バッチID
    private static final String BATCH_ID = "URIB012690";

    // #3344 2016.12.16 T.Kamei Add (S)
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // #3344 2016.12.16 T.Kamei Add (E)

   // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_POS_F_INVOICE";
    //private static final String DEL_SQL = "TRUNCATE TABLE TMP_POS_F_INVOICE_CHECK_DATA";

    /**
     * マスタチェック（Fレコード）
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // Fレコードチェックデータを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            // #3344 2016.12.16 T.Kamei Add (S)
            // 当月度会計年月取得DAOInputBean
            TouKaikeiNengetsuSelectDaoInputBean inputKaikeiBean = new TouKaikeiNengetsuSelectDaoInputBean();
            // 当月度会計年月取得DAOOutputBean
            TouKaikeiNengetsuSelectDaoOutputBean outputKaikeiBean = new TouKaikeiNengetsuSelectDaoOutputBean();
            // 当月度会計年月取得DAO
            TouKaikeiNengetsuSelectDao daoSelect = new TouKaikeiNengetsuSelectDao();

            // 法人コード
            inputKaikeiBean.setCompCd(COMP_CD);

            // 入力ビーンをセット
            daoSelect.setInputObject(inputKaikeiBean);

            invoker.InvokeDao(daoSelect);

            // 出力ビーンをセット
            outputKaikeiBean = (TouKaikeiNengetsuSelectDaoOutputBean) daoSelect.getOutputObject();

            // 会計年月日
            String kaikeiDt = outputKaikeiBean.getUserKaikeiYr() + outputKaikeiBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;
            // #3344 2016.12.16 T.Kamei Add (E)

            String dbServerTime = FiResorceUtility.getDBServerTime();
            //マスタチェック（Fレコード）

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getMaseterCheckTmpPosFInvoiceDaoSql(dbServerTime));

            int i=1;
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_ID);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, BATCH_ID);
            preparedStatementExIns.setString(i++, dbServerTime);

            // #3344 2016.12.16 T.Kamei Add (S)
            preparedStatementExIns.setString(i++, kaikeiDt);
            preparedStatementExIns.setString(i++, kaikeiDt);
            preparedStatementExIns.setString(i++, kaikeiDt);
            preparedStatementExIns.setString(i++, kaikeiDt);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            // #3344 2016.12.16 T.Kamei Add (E)
            //#5399 Add X.Liu 2017.06.14 (S)
            preparedStatementExIns.setString(i++, COMP_CD);
            //#5399 Add X.Liu 2017.06.14 (E)
            // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(S)
            //#5580 Del X.Liu 2017.07.10 (S)
//            preparedStatementExIns.setString(i++, BATCH_DT);
//            preparedStatementExIns.setString(i++, BATCH_ID);
//            preparedStatementExIns.setString(i++, dbServerTime);
//            preparedStatementExIns.setString(i++, BATCH_ID);
//            preparedStatementExIns.setString(i++, dbServerTime);
//            // 2016/11/29 VINX J.Endo FIVI(#2945) ADD(E)
//
//            // #3344 2016.12.16 T.Kamei Add (S)
//            preparedStatementExIns.setString(i++, kaikeiDt);
//            preparedStatementExIns.setString(i++, kaikeiDt);
//            preparedStatementExIns.setString(i++, kaikeiDt);
//            preparedStatementExIns.setString(i++, kaikeiDt);
//            preparedStatementExIns.setString(i++, BATCH_DT);
//            preparedStatementExIns.setString(i++, BATCH_DT);
//            preparedStatementExIns.setString(i++, BATCH_DT);
//            preparedStatementExIns.setString(i++, BATCH_DT);
//            preparedStatementExIns.setString(i++, COMP_CD);
//            preparedStatementExIns.setString(i++, BATCH_DT);
//            preparedStatementExIns.setString(i++, BATCH_DT);
//            preparedStatementExIns.setString(i++, COMP_CD);
//            // #3344 2016.12.16 T.Kamei Add (E)
//            //#5399 Add X.Liu 2017.06.14 (S)
//            preparedStatementExIns.setString(i++, COMP_CD);
            //#5580 Del X.Liu 2017.07.10 (E)
            //#5399 Add X.Liu 2017.06.14 (E)
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件のFレコードチェックデータを追加しました。");

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
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    /**
     * マスタチェック（Fレコード）SQLを取得する
     *
     * @return マスタチェック（Fレコード）
     */
    private String getMaseterCheckTmpPosFInvoiceDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        // 2016/11/10 VINX Y.Itaki FIVI(#2725) MOD(S)

        sql.append("INSERT /*+ APPEND */ INTO WK_POS_F_INVOICE ");
        sql.append("( ");
        sql.append("    COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,SNI_INV_FORM ");
        sql.append("    ,SIN_INV_SERIAL ");
        sql.append("    ,SIN_INV_NO ");
        sql.append("    ,SNI_REFUND_INV_FORM ");
        sql.append("    ,SIN_REFUND_INV_SERIAL ");
        sql.append("    ,SIN_REF_INV_NO ");
        sql.append("    ,SNI_CUST_NAME ");
        sql.append("    ,SNI_CUST_COMPANY ");
        sql.append("    ,SNI_CUST_ADDRS ");
        sql.append("    ,SNI_CUST_TAX_CODE ");
        sql.append("    ,SNI_INV_FORM2 ");
        sql.append("    ,SIN_INV_SERIAL2 ");
        sql.append("    ,SIN_INV_NO2 ");
        sql.append("    ,SIN_INV_SKIP_KIND ");
        sql.append("    ,SIN_INV_SKIP_REASON ");
        sql.append("    ,EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("    ,SNI_INV_ISSUED_CASHERID ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append(") ");
        sql.append("SELECT ");
        sql.append("    TPFI.COMMAND ");
        sql.append("    ,TPFI.STORE ");
        sql.append("    ,TPFI.POS ");
        sql.append("    ,TPFI.TRANS_NO ");
        sql.append("    ,TPFI.CASHIER_ID ");
        sql.append("    ,TPFI.FORMAT ");
        sql.append("    ,TPFI.SNI_INV_FORM ");
        sql.append("    ,TPFI.SIN_INV_SERIAL ");
        sql.append("    ,TPFI.SIN_INV_NO ");
        sql.append("    ,TPFI.SNI_REFUND_INV_FORM ");
        sql.append("    ,TPFI.SIN_REFUND_INV_SERIAL ");
        sql.append("    ,TPFI.SIN_REF_INV_NO ");
        sql.append("    ,TPFI.SNI_CUST_NAME ");
        sql.append("    ,TPFI.SNI_CUST_COMPANY ");
        sql.append("    ,TPFI.SNI_CUST_ADDRS ");
        sql.append("    ,TPFI.SNI_CUST_TAX_CODE ");
        sql.append("    ,TPFI.SNI_INV_FORM2 ");
        sql.append("    ,TPFI.SIN_INV_SERIAL2 ");
        sql.append("    ,TPFI.SIN_INV_NO2 ");
        sql.append("    ,TPFI.SIN_INV_SKIP_KIND ");
        sql.append("    ,TPFI.SIN_INV_SKIP_REASON ");
        sql.append("    ,TPFI.EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("    ,TPFI.SNI_INV_ISSUED_CASHERID ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("    ,? ");
        sql.append("    ,CASE ");
        // #3344 2016.12.16 T.Kamei Mod (S)
        //sql.append("         WHEN DPFI.STORE IS NULL THEN '0' ");
        //sql.append("         ELSE '1' ");
        sql.append("         WHEN DPFI.STORE IS NOT NULL THEN '1' ");
        sql.append("         WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (SUBSTR(TPFI.EIGYO_DT, 1, 8) < SEISAN_ST_DT OR SEISAN_ED_DT < SUBSTR(TPFI.EIGYO_DT, 1, 8)) THEN '4' ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("         WHEN SEISAN_STATE_FG <> '0' THEN '9' ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("         WHEN ISAN_SEISAN_STATE_FG <> '0' THEN '9' ");
        sql.append("  WHEN DTCSS.SEISAN_STATE_FG = '2' THEN '8'");
        sql.append("  WHEN RC.KARIZIMESYORI_KB <> '0' THEN '9'");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("         ELSE '0' ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3344 2016.12.16 T.Kamei Mod (E)
        sql.append("     END ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    TMP_POS_F_INVOICE TPFI ");
        sql.append("LEFT OUTER JOIN ");
        sql.append("    DT_POS_F_INVOICE DPFI ");
        sql.append("ON ");
        sql.append("    TPFI.STORE = DPFI.STORE AND ");
        sql.append("    TPFI.POS = DPFI.POS AND ");
        sql.append("    TPFI.TRANS_NO = DPFI.TRANS_NO AND ");
        sql.append("    TPFI.EIGYO_DT = DPFI.EIGYO_DT ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("    AND TPFI.SIN_INV_SKIP_KIND = DPFI.SIN_INV_SKIP_KIND ");
        sql.append("    AND TPFI.SNI_INV_FORM = DPFI.SNI_INV_FORM ");
        sql.append("    AND TPFI.SIN_INV_SERIAL = DPFI.SIN_INV_SERIAL ");
        sql.append("    AND TPFI.SIN_INV_NO = DPFI.SIN_INV_NO ");
        //#5580 Add X.Liu 2017.07.10 (E)

        // #3344 2016.12.16 T.Kamei Add (S)
        // ＜SUB店舗マスタ＞
        sql.append("LEFT JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            RT.TENPO_CD ");
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
        sql.append("                    CASE ");
        sql.append("                        WHEN RT.SEISAN_ED_DT <= ? THEN RT.SEISAN_ED_DT ");
        sql.append("                        ELSE ");
        sql.append("                            CASE ");
        sql.append("                                WHEN RT.SEISAN_ST_DT IS NULL OR RT.SEISAN_ST_DT <= ? THEN ? ");
                                                    // 上記以外の場合は精算可能開始日、精算可能終了日共に会計年月より未来となるためnullを設定する。
        sql.append("                                ELSE NULL ");
        sql.append("                            END ");
        sql.append("                    END ");
        sql.append("            END AS SEISAN_ED_DT ");
        sql.append("        FROM ");
        sql.append("            R_TENPO RT ");
        sql.append("                WHERE ");
        sql.append("                    RT.HOJIN_CD      = ?    AND ");
        sql.append("                    RT.TENPO_KB      = '1'  AND ");
        sql.append("                    RT.KAITEN_DT    <= ?    AND ");
        sql.append("                    RT.ZAIMU_END_DT >= ?    AND ");
        sql.append("                    RT.DELETE_FG     = '0' ");
        sql.append("    ) RT ");
        sql.append("ON ");
        sql.append("    RT.TENPO_CD = '00' || TPFI.STORE ");
        //#5399 Mod X.Liu 2017.06.14 (S)
        // ＜SUB店別精算状況データ＞
//        sql.append("LEFT JOIN ");
//        sql.append("    ( ");
//        sql.append("        SELECT ");
//        sql.append("            KEIJO_DT ");
//        sql.append("           ,TENPO_CD ");
//        //#4553 Mod X.Liu 2017.04.10 (S)
////        sql.append("           ,SEISAN_STATE_FG ");
//        sql.append("           ,ISAN_SEISAN_STATE_FG ");
//        //#4553 Mod X.Liu 2017.04.10 (E)
//        sql.append("        FROM ");
//        sql.append("            DT_TEN_SEISAN_STATE ");
//        sql.append("        WHERE ");
//        sql.append("            COMP_CD = ? ");
//        sql.append("    ) DTSS ");
//        sql.append("ON ");
//        sql.append("    DTSS.KEIJO_DT = SUBSTR(TPFI.EIGYO_DT, 1, 8) AND ");
//        sql.append("    DTSS.TENPO_CD = '00' || TPFI.STORE ");
        // #3344 2016.12.16 T.Kamei Add (E)
        sql.append("  LEFT JOIN ( ");
        sql.append("    SELECT");
        sql.append("      KEIJO_DT");
        sql.append("      , TENPO_CD");
        sql.append("      , CHECKER_CD");
        sql.append("      , SEISAN_STATE_FG ");
        sql.append("    FROM");
        sql.append("      DT_TEN_CHECKER_SEISAN_STATE ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ?");
        sql.append("  ) DTCSS ");
        sql.append("    ON DTCSS.KEIJO_DT = SUBSTR(TPFI.EIGYO_DT, 1, 8) ");
        sql.append("    AND DTCSS.TENPO_CD = '00' || TPFI.STORE ");
        sql.append("    AND DTCSS.CHECKER_CD = TPFI.CASHIER_ID ");
        sql.append("  LEFT JOIN ( ");
        sql.append("    SELECT");
        sql.append("      KARIZIMESYORI_KB");
        sql.append("      , START_DT");
        sql.append("      , END_DT ");
        sql.append("    FROM");
        sql.append("      R_CALENDAR ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ?");
        sql.append("  ) RC ");
        sql.append("    ON RC.START_DT <= SUBSTR(TPFI.EIGYO_DT, 1, 8) ");
        sql.append("    AND RC.END_DT >= SUBSTR(TPFI.EIGYO_DT, 1, 8)");
        //#5399 Mod X.Liu 2017.06.14 (E)
        // 2016/11/29 VINX J.Endo FIVI(#2945) MOD(S)
        //#5580 Del X.Liu 2017.07.10 (S)
//        sql.append("WHERE ");
//        sql.append("    TPFI.SIN_INV_SKIP_KIND <> '1' AND ");
//        sql.append("    TPFI.SIN_INV_SKIP_KIND <> '2' ");
//        sql.append("UNION ALL ");
//        sql.append("SELECT ");
//        sql.append("    TPFI.COMMAND ");
//        sql.append("    ,TPFI.STORE ");
//        sql.append("    ,TPFI.POS ");
//        sql.append("    ,TPFI.TRANS_NO ");
//        sql.append("    ,TPFI.CASHIER_ID ");
//        sql.append("    ,TPFI.FORMAT ");
//        sql.append("    ,TPFI.SNI_INV_FORM ");
//        sql.append("    ,TPFI.SIN_INV_SERIAL ");
//        sql.append("    ,TPFI.SIN_INV_NO ");
//        sql.append("    ,TPFI.SNI_REFUND_INV_FORM ");
//        sql.append("    ,TPFI.SIN_REFUND_INV_SERIAL ");
//        sql.append("    ,TPFI.SIN_REF_INV_NO ");
//        sql.append("    ,TPFI.SNI_CUST_NAME ");
//        sql.append("    ,TPFI.SNI_CUST_COMPANY ");
//        sql.append("    ,TPFI.SNI_CUST_ADDRS ");
//        sql.append("    ,TPFI.SNI_CUST_TAX_CODE ");
//        sql.append("    ,TPFI.SNI_INV_FORM2 ");
//        sql.append("    ,TPFI.SIN_INV_SERIAL2 ");
//        sql.append("    ,TPFI.SIN_INV_NO2 ");
//        sql.append("    ,TPFI.SIN_INV_SKIP_KIND ");
//        sql.append("    ,TPFI.SIN_INV_SKIP_REASON ");
//        sql.append("    ,TPFI.EIGYO_DT ");
//        sql.append("    ,? ");
//        sql.append("    ,CASE ");
//        // #3344 2016.12.16 T.Kamei Mod (S)
//        //sql.append("         WHEN DPIS.STORE IS NULL THEN '0' ");
//        //sql.append("         ELSE '1' ");
//        sql.append("         WHEN DPIS.STORE IS NOT NULL THEN '1' ");
//        sql.append("         WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (SUBSTR(TPFI.EIGYO_DT, 1, 8) < SEISAN_ST_DT OR SEISAN_ED_DT < SUBSTR(TPFI.EIGYO_DT, 1, 8)) THEN '4' ");
//        //#4553 Mod X.Liu 2017.04.10 (S)
////        sql.append("         WHEN SEISAN_STATE_FG <> '0' THEN '9' ");
//        //#5399 Mod X.Liu 2017.06.14 (S)
////        sql.append("         WHEN ISAN_SEISAN_STATE_FG <> '0' THEN '9' ");
//        sql.append("  WHEN DTCSS.SEISAN_STATE_FG = '2' THEN '8'");
//        sql.append("  WHEN RC.KARIZIMESYORI_KB <> '0' THEN '9'");
//        //#5399 Mod X.Liu 2017.06.14 (E)
//        //#4553 Mod X.Liu 2017.04.10 (E)
//        sql.append("         ELSE '0' ");
//        // #3344 2016.12.16 T.Kamei Mod (E)
//        sql.append("     END ERR_KB ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    TMP_POS_F_INVOICE TPFI ");
//        sql.append("LEFT OUTER JOIN ");
//        sql.append("    DT_POS_INVOICE_SKIP DPIS ");
//        sql.append("ON ");
//        sql.append("    TPFI.STORE = DPIS.STORE AND ");
//        sql.append("    TPFI.POS = DPIS.POS AND ");
//        sql.append("    TPFI.TRANS_NO = DPIS.TRANS_NO AND ");
//        sql.append("    TPFI.EIGYO_DT = DPIS.EIGYO_DT ");
//
//        // #3344 2016.12.16 T.Kamei Add (S)
//        // ＜SUB店舗マスタ＞
//        sql.append("LEFT JOIN ");
//        sql.append("    ( ");
//        sql.append("        SELECT ");
//        sql.append("            RT.TENPO_CD ");
//        sql.append("           ,CASE ");
//        sql.append("                WHEN RT.SEISAN_ST_DT IS NULL THEN ? ");
//        sql.append("                ELSE ");
//        sql.append("                    CASE ");
//        sql.append("                        WHEN RT.SEISAN_ST_DT >= ? THEN RT.SEISAN_ST_DT ");
//        sql.append("                        ELSE ");
//        sql.append("                            CASE ");
//        sql.append("                                WHEN RT.SEISAN_ED_DT IS NULL OR RT.SEISAN_ED_DT >= ? THEN ? ");
//                                                    // 上記以外の場合はSEISAN_ST_DT、SEISAN_ED_DT共に会計年月より過去となるためnullを設定する。
//        sql.append("                                ELSE NULL ");
//        sql.append("                            END ");
//        sql.append("                    END ");
//        sql.append("            END AS SEISAN_ST_DT ");
//        sql.append("           ,CASE ");
//        sql.append("                WHEN RT.SEISAN_ED_DT IS NULL THEN ? ");
//        sql.append("                ELSE ");
//        sql.append("                    CASE ");
//        sql.append("                        WHEN RT.SEISAN_ED_DT <= ? THEN RT.SEISAN_ED_DT ");
//        sql.append("                        ELSE ");
//        sql.append("                            CASE ");
//        sql.append("                                WHEN RT.SEISAN_ST_DT IS NULL OR RT.SEISAN_ST_DT <= ? THEN ? ");
//                                                    // 上記以外の場合は精算可能開始日、精算可能終了日共に会計年月より未来となるためnullを設定する。
//        sql.append("                                ELSE NULL ");
//        sql.append("                            END ");
//        sql.append("                    END ");
//        sql.append("            END AS SEISAN_ED_DT ");
//        sql.append("        FROM ");
//        sql.append("            R_TENPO RT ");
//        sql.append("                WHERE ");
//        sql.append("                    RT.HOJIN_CD      = ?    AND ");
//        sql.append("                    RT.TENPO_KB      = '1'  AND ");
//        sql.append("                    RT.KAITEN_DT    <= ?    AND ");
//        sql.append("                    RT.ZAIMU_END_DT >= ?    AND ");
//        sql.append("                    RT.DELETE_FG     = '0' ");
//        sql.append("    ) RT ");
//        sql.append("ON ");
//        sql.append("    RT.TENPO_CD = '00' || TPFI.STORE ");
//        // ＜SUB店別精算状況データ＞
//        //#5399 Mod X.Liu 2017.06.14 (S)
////        sql.append("LEFT JOIN ");
////        sql.append("    ( ");
////        sql.append("        SELECT ");
////        sql.append("            KEIJO_DT ");
////        sql.append("           ,TENPO_CD ");
////        //#4553 Mod X.Liu 2017.04.10 (S)
//////        sql.append("           ,SEISAN_STATE_FG ");
////        sql.append("           ,ISAN_SEISAN_STATE_FG ");
////        //#4553 Mod X.Liu 2017.04.10 (E)
////        sql.append("        FROM ");
////        sql.append("            DT_TEN_SEISAN_STATE ");
////        sql.append("        WHERE ");
////        sql.append("            COMP_CD = ? ");
////        sql.append("    ) DTSS ");
////        sql.append("ON ");
////        sql.append("    DTSS.KEIJO_DT = SUBSTR(TPFI.EIGYO_DT, 1, 8) AND ");
////        sql.append("    DTSS.TENPO_CD = '00' || TPFI.STORE ");
////        // #3344 2016.12.16 T.Kamei Add (E)
//        sql.append("  LEFT JOIN ( ");
//        sql.append("    SELECT");
//        sql.append("      KEIJO_DT");
//        sql.append("      , TENPO_CD");
//        sql.append("      , CHECKER_CD");
//        sql.append("      , SEISAN_STATE_FG ");
//        sql.append("    FROM");
//        sql.append("      DT_TEN_CHECKER_SEISAN_STATE ");
//        sql.append("    WHERE");
//        sql.append("      COMP_CD = ?");
//        sql.append("  ) DTCSS ");
//        sql.append("    ON DTCSS.KEIJO_DT = SUBSTR(TPFI.EIGYO_DT, 1, 8) ");
//        sql.append("    AND DTCSS.TENPO_CD = '00' || TPFI.STORE ");
//        sql.append("    AND DTCSS.CHECKER_CD = TPFI.CASHIER_ID ");
//        sql.append("  LEFT JOIN ( ");
//        sql.append("    SELECT");
//        sql.append("      KARIZIMESYORI_KB");
//        sql.append("      , START_DT");
//        sql.append("      , END_DT ");
//        sql.append("    FROM");
//        sql.append("      R_CALENDAR ");
//        sql.append("    WHERE");
//        sql.append("      COMP_CD = ?");
//        sql.append("  ) RC ");
//        sql.append("    ON RC.START_DT <= SUBSTR(TPFI.EIGYO_DT, 1, 8) ");
//        sql.append("    AND RC.END_DT >= SUBSTR(TPFI.EIGYO_DT, 1, 8)");
//        //#5399 Mod X.Liu 2017.06.14 (E)
//        sql.append("WHERE ");
//        sql.append("    TPFI.SIN_INV_SKIP_KIND = '1' OR ");
//        sql.append("    TPFI.SIN_INV_SKIP_KIND = '2' ");
        //#5580 Del X.Liu 2017.07.10 (E)
        // 2016/11/29 VINX J.Endo FIVI(#2945) MOD(E)

        // 2016/11/10 VINX Y.Itaki FIVI(#2725) MOD(E)

        return sql.toString();
    }

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {

        return null;
    }

    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {

    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new VatInvoiceRegistDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
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
