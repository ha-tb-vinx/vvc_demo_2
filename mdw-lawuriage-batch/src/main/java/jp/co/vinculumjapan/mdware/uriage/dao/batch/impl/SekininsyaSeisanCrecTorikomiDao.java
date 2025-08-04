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
 * <p>タイトル: SekininsyaSeisanCrecTorikomiDao クラス</p>
 * <p>説明　: POS実績取込処理(責任者精算Crec)</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.11.18) VINX J.Endo FIVI対応 新規作成
 * @Version 1.01 (2016.12.13) VINX J.Endo FIVI対応 キャッシャーＩＤ不具合対応 #3304
 * @Version 1.02 (2017.08.29) VINX N.Katou #5840
 */
public class SekininsyaSeisanCrecTorikomiDao implements DaoIf {

    // バッチ処理名
    private static final String DAO_NAME = "POS実績取込処理(責任者精算Crec)";
    // 登録先テーブル名称
    private static final String INS_TABLE_NAME = "TMP責任者精算Crecデータ";
    // バッチID
    private static final String BATCH_ID = "URIB012790";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        int insertCount = 0;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementInsert = null;
        PreparedStatementEx preparedStatementDelete = null;

        try {
            // SQLを取得し、TMPテーブルを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(getDeleteTMPSekininsyaSeisanCrecSql());
            preparedStatementDelete.execute();

            // SQLを取得し、パラメータを条件にバインドする
            preparedStatementInsert = invoker.getDataBase().prepareStatement(getTMPSekininsyaSeisanCrecSql());

            // 法人コード
            preparedStatementInsert.setString(1, COMP_CD);
            // 作成者ID
            preparedStatementInsert.setString(2, BATCH_ID);
            // 作成年月日
            preparedStatementInsert.setString(3, FiResorceUtility.getDBServerTime());
            // 更新者ID
            preparedStatementInsert.setString(4, BATCH_ID);
            // 更新年月日
            preparedStatementInsert.setString(5, FiResorceUtility.getDBServerTime());

            // 登録実行
            insertCount = preparedStatementInsert.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "へのデータ取込処理を終了します。取込件数は" + insertCount + "件です。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementInsert != null) {
                    preparedStatementInsert.close();
                }
                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }
            } catch (Exception e) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 責任者精算Crec一時テーブルトランケート用SQLを取得する
     *
     * @return 責任者精算Crec一時テーブルトランケート用SQL
     */
    private String getDeleteTMPSekininsyaSeisanCrecSql(){
        StringBuffer sql = new StringBuffer("");

        sql.append("TRUNCATE TABLE ");
        sql.append("    TMP_SEKININSYA_SEISAN_C ");

        return sql.toString();
    }

    /**
     * 責任者精算Crec一時テーブル登録用SQLを取得する
     *
     * @return 責任者精算Crec一時テーブル登録用SQL
     */
    private String getTMPSekininsyaSeisanCrecSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO TMP_SEKININSYA_SEISAN_C( ");
        sql.append("    COMP_CD ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
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
        sql.append("    ? ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
//        sql.append("   ,CASE WHEN EIGYO_DT IS NULL THEN NULL ELSE SUBSTR(EIGYO_DT,1,4) || '/' || SUBSTR(EIGYO_DT,5,2) || '/' || SUBSTR(EIGYO_DT,7,2) END ");
//        sql.append("   ,LPAD(STORE,6,'0') ");
//        // 2016.12.13 VINX J.Endo FIVI対応 #3304 MOD(S)
//        //sql.append("   ,SUBSTR(CASHIER_ID,2,8) ");
//        sql.append("   ,SUBSTR(CASHIER_ID,1,8) ");
//        // 2016.12.13 VINX J.Endo FIVI対応 #3304 MOD(E)
//        sql.append("   ,PYMT_TYPE ");
//        sql.append("   ,PYMT_TYPE2 ");
//        sql.append("   ,SUM(PYMT_AMT) ");
        sql.append("   ,CASE WHEN WPCP.EIGYO_DT IS NULL THEN NULL ");
        sql.append("    ELSE SUBSTR(WPCP.EIGYO_DT,1,4) || '/' || SUBSTR(WPCP.EIGYO_DT,5,2) || '/' || SUBSTR(WPCP.EIGYO_DT,7,2) ");
        sql.append("    END EIGYO_DT ");
        sql.append("   ,LPAD(WPCP.STORE,6,'0') ");
        sql.append("   ,SUBSTR(WPCP.CASHIER_ID,1,8) ");
        sql.append("   ,WPCP.PYMT_TYPE ");
        sql.append("   ,WPCP.PYMT_TYPE2 ");
        sql.append("   ,WPCP.PYMT_AMT ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("   ,WPCP.RECEIPT_QT ");
        sql.append("   ,CASE WHEN RP.OVER_TYPE = '2' THEN WPCP.SYUUKIN_VL ");
        sql.append("    ELSE 0  ");
        sql.append("    END SYUUKIN_VL ");
        sql.append("   ,CASE WHEN RP.OVER_TYPE = '2' THEN WPCP.CYOUKA_VL ");
        sql.append("    ELSE 0  ");
        sql.append("    END CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("FROM");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
//        sql.append("    TMP_POS_C_PAYMENT ");
//        sql.append("GROUP BY ");
//        sql.append("    EIGYO_DT ");
//        sql.append("   ,STORE ");
//        sql.append("   ,CASHIER_ID ");
//        sql.append("   ,PYMT_TYPE ");
//        sql.append("   ,PYMT_TYPE2 ");
        sql.append("   (  ");
        sql.append("     SELECT ");
        sql.append("       EIGYO_DT ");
        sql.append("       , STORE ");
        sql.append("       , CASHIER_ID ");
        sql.append("       , PYMT_TYPE ");
        sql.append("       , PYMT_TYPE2 ");
        sql.append("       , SUM(PYMT_AMT) PYMT_AMT ");
        sql.append("       , SUM(RECEIPT_QT) RECEIPT_QT ");
        sql.append("       , SUM(SYUUKIN_VL) SYUUKIN_VL ");
        sql.append("       , SUM(CYOUKA_VL) CYOUKA_VL ");
        sql.append("     FROM ");
        sql.append("       (  ");
        sql.append("         SELECT ");
        sql.append("           EIGYO_DT ");
        sql.append("           , STORE ");
        sql.append("           , CASHIER_ID ");
        sql.append("           , PYMT_TYPE ");
        sql.append("           , PYMT_TYPE2 ");
        sql.append("           , 1 RECEIPT_QT ");
        sql.append("           , SUM(PYMT_AMT) PYMT_AMT ");
        sql.append("           , SUM(  ");
        sql.append("             CASE  ");
        sql.append("               WHEN (  ");
        sql.append("                 (CTYPE = '0012' AND PYMT_AMT > 0)  ");
        sql.append("                 OR (CTYPE = '0312' AND PYMT_AMT < 0)  ");
        sql.append("                 OR (CTYPE = '1011' AND PYMT_AMT > 0)  ");
        sql.append("                 OR (CTYPE = '1311' AND PYMT_AMT < 0) ");
        sql.append("               )  ");
        sql.append("               THEN PYMT_AMT  ");
        sql.append("               ELSE '0'  ");
        sql.append("               END ");
        sql.append("           ) SYUUKIN_VL ");
        sql.append("           , SUM(  ");
        sql.append("             CASE  ");
        sql.append("               WHEN (  ");
        sql.append("                 (CTYPE = '0012' AND PYMT_AMT < 0)  ");
        sql.append("                 OR (CTYPE = '0312' AND PYMT_AMT > 0)  ");
        sql.append("                 OR (CTYPE = '1011' AND PYMT_AMT < 0)  ");
        sql.append("                 OR (CTYPE = '1311' AND PYMT_AMT > 0) ");
        sql.append("               )  ");
        sql.append("               THEN PYMT_AMT * - 1  ");
        sql.append("               ELSE 0  ");
        sql.append("               END ");
        sql.append("           ) CYOUKA_VL  ");
        sql.append("         FROM ");
        sql.append("           WK_POS_C_PAYMENT WPCP  ");
        sql.append("         WHERE ");
        sql.append("           ERR_KB = '0'  ");
        sql.append("         GROUP BY ");
        sql.append("           EIGYO_DT ");
        sql.append("           , STORE ");
        sql.append("           , POS ");
        sql.append("           , TRANS_NO ");
        sql.append("           , CASHIER_ID ");
        sql.append("           , PYMT_TYPE ");
        sql.append("           , PYMT_TYPE2 ");
        sql.append("       )  ");
        sql.append("     GROUP BY ");
        sql.append("       EIGYO_DT ");
        sql.append("       , STORE ");
        sql.append("       , CASHIER_ID ");
        sql.append("       , PYMT_TYPE ");
        sql.append("       , PYMT_TYPE2 ");
        sql.append("   ) WPCP  ");
        sql.append("   LEFT JOIN R_PAYMENT RP  ");
        sql.append("     ON WPCP.PYMT_TYPE = RP.SHIHARAI_SYUBETSU_CD  ");
        sql.append("     AND WPCP.PYMT_TYPE2 = RP.SHIHARAI_SYUBETSU_SUB_CD ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input SekininsyaSeisanTorikomiDaoInputBean型オブジェクト
     */
    public void setInputObject(Object input) throws Exception {
        // 処理無し
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
            DaoIf dao = new SekininsyaSeisanCrecTorikomiDao();
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
