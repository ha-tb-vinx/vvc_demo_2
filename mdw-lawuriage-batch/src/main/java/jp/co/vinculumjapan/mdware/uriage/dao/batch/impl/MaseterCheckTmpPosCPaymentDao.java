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
 * <p>タイトル: MaseterCheckTmpPosCPaymentDao クラス</p>
 * <p>説明:マスタチェック（Cレコード）</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.10.18) k.Hyo FIVI対応
 * @Version 1.01 (2016.11.10) Y.Itaki FIVI対応(#2725)
 * @Version 1.02 (2016.12.16) T.Kamei FIVI対応(#3344)
 * @Version 1.03 (2017.03.09) N.Katou #3760
 * @Version 1.04 (2017.04.10) X.Liu  #4553
 * @Version 1.05 (2017.06.14) X.Liu  #5399
 * @Version 1.06 (2017.10.19) N.Katou #6015
 *
 * @see なし
 */
public class MaseterCheckTmpPosCPaymentDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "マスタチェック（Cレコード）";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // バッチID
    private static final String BATCH_ID = "URIB012760";

    // #3344 2016.12.16 T.Kamei Add (S)
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // #3344 2016.12.16 T.Kamei Add (E)

    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_POS_C_PAYMENT";

    /**
     * マスタチェック（Cレコード）
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

            // Cレコードチェックデータを削除する
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
            //マスタチェック（Cレコード）

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getMaseterCheckTmpPosAItemDaoSql(dbServerTime));

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
            insertCount = preparedStatementExIns.executeUpdate();


            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件のCレコードチェックデータを追加しました。");

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
     * マスタチェック（Cレコード）SQLを取得する
     *
     * @return マスタチェック（Cレコード）
     */
    private String getMaseterCheckTmpPosAItemDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        // 2016/11/10 VINX Y.Itaki FIVI(#2725) MOD(S)

        sql.append("INSERT /*+ APPEND */ INTO WK_POS_C_PAYMENT ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,CTYPE ");
        sql.append("    ,PYMT_TYPE ");
        sql.append("    ,PYMT_TYPE2 ");
        sql.append("    ,DATE_EXPIRY ");
        sql.append("    ,PYMT_AMT ");
        sql.append("    ,CREDIT_CARD_NUMBER ");
        sql.append("    ,MERCHANT_CODE ");
        sql.append("    ,ACTUAL_AMT ");
        sql.append("    ,APP_CODE ");
        sql.append("    ,DEBIT_TYPE ");
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // #3760 URIB012760 2017/3/09 N.katou(S)
        sql.append("    ,ISSUE_DATE_OF_PRV ");
        // #3760 URIB012760 2017/3/09 N.katou(E)
        sql.append("    ) ");

        sql.append("SELECT ");
        sql.append("     TPCP.COMMAND ");
        sql.append("    ,TPCP.STORE ");
        sql.append("    ,TPCP.POS ");
        sql.append("    ,TPCP.TRANS_NO ");
        sql.append("    ,TPCP.CASHIER_ID ");
        sql.append("    ,TPCP.FORMAT ");
        sql.append("    ,TPCP.CTYPE ");
        sql.append("    ,TPCP.PYMT_TYPE ");
        sql.append("    ,TPCP.PYMT_TYPE2 ");
        sql.append("    ,TPCP.DATE_EXPIRY ");
        sql.append("    ,TPCP.PYMT_AMT ");
        sql.append("    ,TPCP.CREDIT_CARD_NUMBER ");
        sql.append("    ,TPCP.MERCHANT_CODE ");
        sql.append("    ,TPCP.ACTUAL_AMT ");
        sql.append("    ,TPCP.APP_CODE ");
        sql.append("    ,TPCP.DEBIT_TYPE ");
        sql.append("    ,TPCP.TERMINAL_ID ");
        sql.append("    ,TPCP.TRACE_NO ");
        sql.append("    ,TPCP.EIGYO_DT ");
        sql.append("    ,? ");
        sql.append("    ,CASE ");
        // #3344 2016.12.16 T.Kamei Mod (S)
        //sql.append("         WHEN DPCP.STORE IS NULL THEN '0' ");
        //sql.append("         ELSE '1' ");
        sql.append("         WHEN DPCP.STORE IS NOT NULL THEN '1' ");
        sql.append("         WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (SUBSTR(TPCP.EIGYO_DT, 1, 8) < SEISAN_ST_DT OR SEISAN_ED_DT < SUBSTR(TPCP.EIGYO_DT, 1, 8)) THEN '4' ");
        // #6015  2017/10/19 N.katou(S)
        sql.append("         WHEN TPCP.PYMT_TYPE IS NULL OR TPCP.PYMT_TYPE2 IS NULL THEN '7' ");
        // #6015  2017/10/19 N.katou(E)
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("         WHEN SEISAN_STATE_FG <> '0' THEN '9' ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("         WHEN ISAN_SEISAN_STATE_FG <> '0' THEN '9' ");
        sql.append("  WHEN DTCSS.SEISAN_STATE_FG = '2' THEN '8'");
        sql.append("  WHEN RC.KARIZIMESYORI_KB <> '0' THEN '9'");
        //#5399 Mod X.Liu 2017.06.14 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("         ELSE '0' ");
        // #3344 2016.12.16 T.Kamei Mod (E)
        sql.append("     END ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // #3760 URIB012760 2017/3/09 N.katou(S)
        sql.append("    ,TPCP.ISSUE_DATE_OF_PRV ");
        // #3760 URIB012760 2017/3/09 N.katou(E)
        sql.append("FROM ");
        sql.append("    TMP_POS_C_PAYMENT TPCP ");
        sql.append("LEFT OUTER JOIN ");
        sql.append("    DT_POS_C_PAYMENT DPCP ");
        sql.append("ON ");
        sql.append("    TPCP.STORE = DPCP.STORE AND ");
        sql.append("    TPCP.POS = DPCP.POS AND ");
        sql.append("    TPCP.TRANS_NO = DPCP.TRANS_NO AND ");
        sql.append("    TPCP.EIGYO_DT = DPCP.EIGYO_DT ");

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
        sql.append("    RT.TENPO_CD = '00' || TPCP.STORE ");
        // ＜SUB店別精算状況データ＞
        //#5399 Mod X.Liu 2017.06.14 (S)
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
//        sql.append("    DTSS.KEIJO_DT = SUBSTR(TPCP.EIGYO_DT, 1, 8) AND ");
//        sql.append("    DTSS.TENPO_CD = '00' || TPCP.STORE ");
        // #3344 2016.12.16 T.Kamei Add (E)

        // 2016/11/10 VINX Y.Itaki FIVI(#2725) MOD(E)

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
        sql.append("    ON DTCSS.KEIJO_DT = SUBSTR(TPCP.EIGYO_DT, 1, 8) ");
        sql.append("    AND DTCSS.TENPO_CD = '00' || TPCP.STORE ");
        sql.append("    AND DTCSS.CHECKER_CD = TPCP.CASHIER_ID ");
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
        sql.append("    ON RC.START_DT <= SUBSTR(TPCP.EIGYO_DT, 1, 8) ");
        sql.append("    AND RC.END_DT >= SUBSTR(TPCP.EIGYO_DT, 1, 8)");
        //#5399 Mod X.Liu 2017.06.14 (E)
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
