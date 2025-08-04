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
 * <p>タイトル: RegiTorihikiSeisanErecTorikomiDao クラス</p>
 * <p>説明　: POS実績取込処理（レジ別取引精算Erec）</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2017.04.27) VINX J.Endo FIVI対応(#4770) 新規作成
 * @Version 1.01 (2017.06.06) VINX J.Endo FIVI対応(#5271) 不具合対応
 * @Version 1.02 (2017.06.14) VINX X.Liu FIVI対応(#5399) 
 * @Version 1.03 (2017.07.26) VINX J.Endo FIVI対応(#5040) 不具合対応
 * 
 */
public class RegiTorihikiSeisanErecTorikomiDao implements DaoIf /*extends TorikomiDaoSuper*/ {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理（レジ別取引精算Erec）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMPレジ別取引精算データErec";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE TMP_REGI_TORIHIKI_SEISAN_E";

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

        int insertCount = 0;
        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // TMPレジ別取引精算データErecから有効レジ別取引精算ワークErecへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTmpRegiTorihikiSeisanErecSql());

            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, userId);
            preparedStatementExIns.setString(3, dbServerTime);
            preparedStatementExIns.setString(4, userId);
            preparedStatementExIns.setString(5, dbServerTime);
            //#5399 Add X.Liu 2017.06.14 (S)
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, userId);
            preparedStatementExIns.setString(8, dbServerTime);
            preparedStatementExIns.setString(9, userId);
            preparedStatementExIns.setString(10, dbServerTime);
            //#5399 Add X.Liu 2017.06.14 (E)

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
     * POSジャーナル（E_Tender）ワークからTMPレジ別取引精算データErecへ登録するSQLを取得する
     *
     * @return TMPレジ別取引精算データErec登録クエリー
     */
    private String getTmpRegiTorihikiSeisanErecSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO TMP_REGI_TORIHIKI_SEISAN_E ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,FLOAR_NO ");
        sql.append("   ,REGI_NO ");
        sql.append("   ,TORIHIKI_CD ");
        sql.append("   ,TYPE_KB ");
        sql.append("   ,KYAKU_QT ");
        sql.append("   ,TEN_KAISUU_QT ");
        sql.append("   ,KINGAKU_VL ");
        sql.append("   ,NEBIKI_VL ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ) ");
        sql.append("SELECT ");
        //#5399 Mod X.Liu 2017.06.14 (S) 
//        sql.append("    ? ");
//        sql.append("   ,SUBSTR(WPE.EIGYO_DT,1,4)||'/'||SUBSTR(WPE.EIGYO_DT,5,2)||'/'||SUBSTR(WPE.EIGYO_DT,7,2) ");
//        sql.append("   ,LPAD(WPE.STORE,6,'0') ");
//        sql.append("   ,NULL ");
//        sql.append("   ,NULL ");
//        sql.append("   ,CASE WHEN WPE.PYMT_TYPE = 'CSH' AND WPE.PYMT_TYPE2 = 'CSH0000' ");
//        sql.append("        THEN '0001' ");
//        sql.append("        ELSE '0002' ");
//        sql.append("    END AS TORIHIKI_CD ");
//        sql.append("   ,'0' ");
//        sql.append("   ,'0' ");
//        sql.append("   ,'0' ");
//        sql.append("   ,CASE WHEN WPE.PYMT_TYPE = 'CSH' AND WPE.PYMT_TYPE2 = 'CSH0000' ");
//                            // THEN 金額 ＝ Dレコードの釣銭準備金 － Dレコードの釣銭抜出金 ＋ Eレコードの交代時金額
//        // 2017.06.06 VINX J.Endo FIVI対応 #5271 MOD(S)
//        //sql.append("        THEN WPD.LOAN_AMOUNT - WPD.MIDDLE_COLLECTION_AMOUNT + WPE.PAYMENT_COUNT ");
//        sql.append("        THEN NVL(WPD.LOAN_AMOUNT, 0) - NVL(WPD.MIDDLE_COLLECTION_AMOUNT, 0) + WPE.PAYMENT_COUNT ");
//        // 2017.06.06 VINX J.Endo FIVI対応 #5271 MOD(E)
//                            // ELSE 金額 ＝ Eレコードの支払額合計
//        sql.append("        ELSE WPE.PAYMENT_SALES ");
//        sql.append("    END AS KINGAKU_VL ");
//        sql.append("   ,'0' ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,WPE.PYMT_TYPE ");
//        sql.append("   ,WPE.PYMT_TYPE2 ");
        sql.append("  ? AS COMP_CD");
        sql.append("  , SUBSTR(WPE.EIGYO_DT, 1, 4) || '/' || SUBSTR(WPE.EIGYO_DT, 5, 2) || '/' || SUBSTR(WPE.EIGYO_DT, 7, 2)");
        sql.append("   AS EIGYO_DT");
        sql.append("  , LPAD(WPE.STORE, 6, '0') AS TENPO_CD");
        sql.append("  , NULL AS FLOAR_NO");
        sql.append("  , NULL AS REGI_NO");
        sql.append("  , CASE ");
        sql.append("    WHEN WPE.PYMT_TYPE = 'CSH' ");
        sql.append("    AND WPE.PYMT_TYPE2 = 'CSH0000' ");
        sql.append("    THEN '0001' ");
        sql.append("    ELSE '0002' ");
        sql.append("    END AS TORIHIKI_CD");
        sql.append("  , '0' AS TYPE_KB");
        sql.append("  , '0' AS KYAKU_QT");
        sql.append("  , '0' AS TEN_KAISUU_QT");
        sql.append("  , CASE ");
        sql.append("    WHEN WPE.PYMT_TYPE = 'CSH' ");
        sql.append("    AND WPE.PYMT_TYPE2 = 'CSH0000'               ");
        // 2017.07.26 VINX J.Endo FIVI対応 #5040 MOD(S)
        //sql.append("    THEN NVL(WPD.LOAN_AMOUNT, 0) - NVL(WPD.MIDDLE_COLLECTION_AMOUNT, 0) + NVL(WPE.PAYMENT_COUNT, 0) ");
        sql.append("    THEN NVL(WPE.PAYMENT_COUNT, 0) + NVL(WPD.MIDDLE_COLLECTION_AMOUNT, 0) - NVL(WPD.LOAN_AMOUNT, 0) ");
        // 2017.07.26 VINX J.Endo FIVI対応 #5040 MOD(E)
        sql.append("    ELSE WPE.PAYMENT_SALES ");
        sql.append("    END AS KINGAKU_VL");
        sql.append("  , '0' AS NEBIKI_VL");
        sql.append("  , ? AS INSERT_USER_ID");
        sql.append("  , ? AS INSERT_TS");
        sql.append("  , ? AS UPDATE_USER_ID");
        sql.append("  , ? AS UPDATE_TS");
        sql.append("  , WPE.PYMT_TYPE AS SHIHARAI_SYUBETSU_CD");
        sql.append("  , WPE.PYMT_TYPE2 AS SHIHARAI_SYUBETSU_SUB_CD ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("FROM ( ");
        sql.append("    SELECT "); // TMP_POS_E_TENDERのキー情報を作成（メイン）
        sql.append("        EIGYO_DT ");
        sql.append("       ,STORE ");
        sql.append("       ,SUM(NVL(PAYMENT_SALES,0)) AS PAYMENT_SALES ");
        sql.append("       ,SUM(NVL(PAYMENT_COUNT,0)) AS PAYMENT_COUNT ");
        sql.append("       ,PYMT_TYPE ");
        sql.append("       ,PYMT_TYPE2 ");
        sql.append("    FROM WK_POS_E_TENDER ");
        sql.append("    GROUP BY ");
        sql.append("        EIGYO_DT ");
        sql.append("       ,STORE ");
        sql.append("       ,PYMT_TYPE ");
        sql.append("       ,PYMT_TYPE2 ");
        sql.append("    ) WPE ");
        sql.append("    LEFT OUTER JOIN ( "); // TMP_POS_D_CASHの集計を作成（外部結合）
        sql.append("        SELECT ");
        sql.append("            EIGYO_DT ");
        sql.append("           ,STORE ");
        sql.append("           ,SUM(NVL(LOAN_AMOUNT,0)) AS LOAN_AMOUNT ");
        sql.append("           ,SUM(NVL(MIDDLE_COLLECTION_AMOUNT,0)) AS MIDDLE_COLLECTION_AMOUNT ");
        sql.append("        FROM WK_POS_D_CASH ");
        sql.append("        GROUP BY ");
        sql.append("            EIGYO_DT ");
        sql.append("           ,STORE ");
        sql.append("        ) WPD ");
        sql.append("    ON  WPE.EIGYO_DT = WPD.EIGYO_DT AND ");
        sql.append("        WPE.STORE    = WPD.STORE ");
        //#5399 Add X.Liu 2017.06.14 (S)
        sql.append("    UNION ALL ");
        sql.append("SELECT");
        sql.append("  ? AS COMP_CD");
        sql.append("  , SUBSTR(WPD.EIGYO_DT, 1, 4) || '/' || SUBSTR(WPD.EIGYO_DT, 5, 2) || '/' || SUBSTR(WPD.EIGYO_DT, 7, 2)");
        sql.append("   AS EIGYO_DT");
        sql.append("  , LPAD(WPD.STORE, 6, '0') AS TENPO_CD");
        sql.append("  , NULL AS FLOAR_NO");
        sql.append("  , NULL AS REGI_NO");
        sql.append("  , '0001' AS TORIHIKI_CD");
        sql.append("  , '0' AS TYPE_KB");
        sql.append("  , '0' AS KYAKU_QT");
        sql.append("  , '0' AS TEN_KAISUU_QT  ");
        // 2017.07.26 VINX J.Endo FIVI対応 #5040 MOD(S)
        //sql.append("  , SUM(NVL(LOAN_AMOUNT, 0)) - SUM(NVL(MIDDLE_COLLECTION_AMOUNT, 0)) AS KINGAKU_VL");
        sql.append("  , SUM(NVL(MIDDLE_COLLECTION_AMOUNT, 0)) - SUM(NVL(LOAN_AMOUNT, 0)) AS KINGAKU_VL");
        // 2017.07.26 VINX J.Endo FIVI対応 #5040 MOD(E)
        sql.append("  , '0' AS NEBIKI_VL");
        sql.append("  , ? AS INSERT_USER_ID");
        sql.append("  , ? AS INSERT_TS");
        sql.append("  , ? AS UPDATE_USER_ID");
        sql.append("  , ? AS UPDATE_TS");
        sql.append("  , 'CSH' AS SHIHARAI_SYUBETSU_CD");
        sql.append("  , 'CSH0000' AS SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("FROM");
        sql.append("  WK_POS_D_CASH WPD ");
        sql.append("  LEFT OUTER JOIN WK_POS_E_TENDER WPET ");
        sql.append("    ON WPET.PYMT_TYPE = 'CSH' ");
        sql.append("    AND WPET.PYMT_TYPE2 = 'CSH0000' ");
        sql.append("    AND WPD.EIGYO_DT = WPET.EIGYO_DT ");
        sql.append("    AND WPD.STORE = WPET.STORE ");
        sql.append("WHERE");
        sql.append("  WPET.STORE IS NULL ");
        sql.append("GROUP BY");
        sql.append("  WPD.EIGYO_DT");
        sql.append("  , WPD.STORE");
        //#5399 Add X.Liu 2017.06.14 (E)

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
