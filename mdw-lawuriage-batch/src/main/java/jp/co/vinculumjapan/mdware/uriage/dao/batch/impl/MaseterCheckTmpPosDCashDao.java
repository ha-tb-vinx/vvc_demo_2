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
 * <p>タイトル: MaseterCheckTmpPosDCashDao クラス</p>
 * <p>説明:マスタチェック（Dレコード）</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2017.02.27) N.Katou FIVI対応
 * @Version 1.01 (2017.04.10) X.Liu FIVI対応
 * @Version 1.02 (2017.04.28) J.Endo FIVI対応(#4875)
 * @Version 1.03 (2017.05.09) J.Endo FIVI対応(#5000)
 * @Version 1.04 (2017.06.14) X.Liu FIVI対応(#5399)
 * @see なし
 */
public class MaseterCheckTmpPosDCashDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "マスタチェック（Dレコード）";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // バッチID
    private static final String BATCH_ID = "URIB012900";

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_POS_D_CASH";

    /**
     * マスタチェック（Dレコード）
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

            // Dレコードチェックデータを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

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

            String dbServerTime = FiResorceUtility.getDBServerTime();

            //マスタチェック（Dレコード）
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getMaseterCheckTmpPosDCashDaoSql(dbServerTime));

            int i=1;
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_ID);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, BATCH_ID);
            preparedStatementExIns.setString(i++, dbServerTime);
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
            //#5399 Add X.Liu 2017.06.14 (S)
            preparedStatementExIns.setString(i++, COMP_CD);
            //#5399 Add X.Liu 2017.06.14 (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件のDレコードチェックデータを追加しました。");

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
     * マスタチェック（Dレコード）SQLを取得する
     *
     * @return マスタチェック（Dレコード）
     */
    private String getMaseterCheckTmpPosDCashDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_POS_D_CASH ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,LOAN_AMOUNT ");
        sql.append("    ,MIDDLE_COLLECTION_AMOUNT ");
        sql.append("    ,CASH_SALES ");
        sql.append("    ,CASH_COUNT ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");

        sql.append("SELECT ");
        sql.append("     TPDC.COMMAND ");
        sql.append("    ,TPDC.STORE ");
        sql.append("    ,TPDC.POS ");
        sql.append("    ,TPDC.TRANS_NO ");
        sql.append("    ,TPDC.CASHIER_ID ");
        sql.append("    ,TPDC.FORMAT ");
        sql.append("    ,TPDC.LOAN_AMOUNT ");
        sql.append("    ,TPDC.MIDDLE_COLLECTION_AMOUNT ");
        sql.append("    ,TPDC.CASH_SALES ");
        sql.append("    ,TPDC.CASH_COUNT ");
        sql.append("    ,TPDC.EIGYO_DT ");
        sql.append("    ,? ");
        sql.append("    ,CASE ");
        sql.append("         WHEN DPDC.STORE IS NOT NULL THEN '1' ");
        sql.append("         WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (SUBSTR(TPDC.EIGYO_DT, 1, 8) < SEISAN_ST_DT OR SEISAN_ED_DT < SUBSTR(TPDC.EIGYO_DT, 1, 8)) THEN '4' ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("         WHEN SEISAN_STATE_FG <> '0' OR SEISAN_STATE_FG IS NULL THEN '9' ");
        //#4875 Mod J.Endo 2017.04.28 (S)
//        sql.append("         WHEN ISAN_SEISAN_STATE_FG <> '0' OR ISAN_SEISAN_STATE_FG IS NULL THEN '9' ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("         WHEN ISAN_SEISAN_STATE_FG <> '0' THEN '9'  ");
        //#4875 Mod J.Endo 2017.04.28 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("  WHEN DTCSS.SEISAN_STATE_FG = '2' THEN '8'");
        sql.append("  WHEN RC.KARIZIMESYORI_KB <> '0' THEN '9'");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("         ELSE '0' ");
        sql.append("     END ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    TMP_POS_D_CASH TPDC ");
        sql.append("LEFT OUTER JOIN ");
        sql.append("    DT_POS_D_CASH DPDC ");
        sql.append("ON ");
        sql.append("    TPDC.STORE = DPDC.STORE AND ");
        sql.append("    TPDC.POS = DPDC.POS AND ");
        sql.append("    TPDC.TRANS_NO = DPDC.TRANS_NO AND ");
        sql.append("    TPDC.EIGYO_DT = DPDC.EIGYO_DT ");
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
        sql.append("    RT.TENPO_CD = '00' || TPDC.STORE ");
        // ＜SUB店別精算状況データ＞
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("LEFT JOIN ");
//        sql.append("    ( ");
//        sql.append("        SELECT ");
//        sql.append("            KEIJO_DT ");
//        sql.append("           ,TENPO_CD ");
////#5000 Mod J.Endo 2017.05.09 (S)
////        //#4553 Mod X.Liu 2017.04.10 (S)
//////        sql.append("           ,SEISAN_STATE_FG ");
////        sql.append("           ,ISAN_SEISAN_STATE_FG ");
////        //#4553 Mod X.Liu 2017.04.10 (E)
//        sql.append("           ,ISAN_SEISAN_STATE_FG  ");
////#5000 Mod J.Endo 2017.05.09 (E)
//        sql.append("        FROM ");
//        sql.append("            DT_TEN_SEISAN_STATE ");
//        sql.append("        WHERE ");
//        sql.append("            COMP_CD = ? ");
//        sql.append("    ) DTSS ");
//        sql.append("ON ");
//        sql.append("    DTSS.KEIJO_DT = SUBSTR(TPDC.EIGYO_DT, 1, 8) AND ");
//        sql.append("    DTSS.TENPO_CD = '00' || TPDC.STORE ");
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
        sql.append("    ON DTCSS.KEIJO_DT = SUBSTR(TPDC.EIGYO_DT, 1, 8) ");
        sql.append("    AND DTCSS.TENPO_CD = '00' || TPDC.STORE ");
        sql.append("    AND DTCSS.CHECKER_CD = TPDC.CASHIER_ID ");
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
        sql.append("    ON RC.START_DT <= SUBSTR(TPDC.EIGYO_DT, 1, 8) ");
        sql.append("    AND RC.END_DT >= SUBSTR(TPDC.EIGYO_DT, 1, 8)");
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
