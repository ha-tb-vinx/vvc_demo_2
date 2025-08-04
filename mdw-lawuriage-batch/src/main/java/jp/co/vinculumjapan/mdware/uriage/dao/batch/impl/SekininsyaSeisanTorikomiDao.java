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
 * <p>
 * タイトル: SekininsyaSeisanTorikomiDao クラス
 * </p>
 * <p>
 * 説明　: POS実績取込処理(責任者精算)
 * </p>
 * <p>
 * 著作権: Copyright (c) 2013
 * </p>
 * <p>
 * 会社名: VINX
 * </p>
 *
 * @Version 3.00 (2013.09.17) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * @Version 3.01 (2016.05.10) monden    S03対応
 * @Version 3.03 (2016.05.10) VINX k.Hyo FIVI対応 店舗コード4桁→6桁修正
 * @Version 3.04 (2016.12.13) VINX J.Endo FIVI対応 キャッシャーＩＤ不具合対応 #3304
 * @Version 3.05 (2016.12.22) VINX J.Endo FIVI対応 在高の算出方法変更 #3273
 * @Version 3.06 (2017.01.17) VINX J.Endo FIVI対応 在高の算出方法変更 #3610
 * @Version 3.07 (2017.02.16) VINX J.Endo FIVI対応 サブ支払種別コード7桁化 #4013対応 
 * @Version 3.08 (2017.03.13) VINX J.Endo FIVI対応 EレコードのレジNO・レシートNO未設定化 #4322対応 
 * @Version 3.09 (2017.08.31) VINX N.Katou FIVI対応 #5840
 * @Version 3.10 (2020.11.12) THONG.VQ MKV対応 #6278
 */
//2016/05/10 VINX #S03対応（S)
 public class SekininsyaSeisanTorikomiDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理(責任者精算)";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMP責任者精算データ";

    /** ﾊﾞｯﾁID */
    private static final String BATCH_ID = "URIB012040";

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // 追加SQL文用定数
    private static final String INS_SQL = "insertTmpSekininshaSeisan";
    // 削除SQL文用定数
    private static final String DEL_SQL = "deleteTmpSekininshaSeisan";

    /**
     * アウトプットBeanを取得する
     *
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
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
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new SekininsyaSeisanTorikomiDao();
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
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        try {

            // SQLを取得し、TMPテーブルを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            // SQLを取得し、パラメータを条件にバインドする
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTMPSekininsyaSeisanSql());

            // バッチ日付を取得
            String batchDt = FiResorceUtility.getDBServerTime();

            // 2016/12/22 VINX J.Endo FIVI対応 #3273 MOD(S)
            //// 法人コード
            //preparedStatementExIns.setString(1, COMP_CD);
            //// 営業日
            //// 店舗コード
            //// キャッシャーID
            //// 時間
            //preparedStatementExIns.setString(2, "00000000");
            //// 取引コード
            //preparedStatementExIns.setString(3, "0001");
            //// タイプ
            //preparedStatementExIns.setString(4, "0");
            //// 客数
            //preparedStatementExIns.setString(5, "0");
            //// 点数
            //preparedStatementExIns.setString(6, "0");
            //// 金額
            //// 値引金額
            //preparedStatementExIns.setString(7, "0");
            //// 作成者ID
            //preparedStatementExIns.setString(8, BATCH_ID);
            //// 作成年月日
            //preparedStatementExIns.setString(9, batchDt);
            //// 更新者ID
            //preparedStatementExIns.setString(10, BATCH_ID);
            //// 更新年月日
            //preparedStatementExIns.setString(11, batchDt);
            //// 支払種別コード
            //// サブ支払種別コード
            preparedStatementExIns.setString(1, COMP_CD);   // 法人コード
            preparedStatementExIns.setString(2, BATCH_ID);  // 作成者ID
            preparedStatementExIns.setString(3, batchDt);   // 作成年月日
            preparedStatementExIns.setString(4, BATCH_ID);  // 更新者ID
            preparedStatementExIns.setString(5, batchDt);   // 更新年月日

            preparedStatementExIns.setString(6, COMP_CD);   // 法人コード
            preparedStatementExIns.setString(7, BATCH_ID);  // 作成者ID
            preparedStatementExIns.setString(8, batchDt);   // 作成年月日
            preparedStatementExIns.setString(9, BATCH_ID);  // 更新者ID
            preparedStatementExIns.setString(10, batchDt);  // 更新年月日

            preparedStatementExIns.setString(11, COMP_CD);  // 法人コード
            preparedStatementExIns.setString(12, BATCH_ID); // 作成者ID
            preparedStatementExIns.setString(13, batchDt);  // 作成年月日
            preparedStatementExIns.setString(14, BATCH_ID); // 更新者ID
            preparedStatementExIns.setString(15, batchDt);  // 更新年月日
            // 2016/12/22 VINX J.Endo FIVI対応 #3273 MOD(E)

            // 登録実行
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "へのデータ取込処理を終了します。取込件数は" + insertCount + "件です。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
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
     * 責任者精算一時テーブル登録用SQLを取得する
     *
     * @return 責任者精算一時テーブル登録用SQL
     */
    private String getTMPSekininsyaSeisanSql() {
        StringBuilder sql = new StringBuilder();

        // 2016/12/22 VINX J.Endo FIVI対応 #3273 MOD(S)
        //sql.append("INSERT /*+ APPEND */ INTO TMP_SEKININSYA_SEISAN( ");
        //sql.append(" COMP_CD, ");
        //sql.append(" EIGYO_DT, ");
        //sql.append(" TENPO_CD, ");
        //sql.append(" CHECKER_CD, ");
        //sql.append(" JIKAN_TM, ");
        //sql.append(" TORIHIKI_CD, ");
        //sql.append(" TYPE_KB, ");
        //sql.append(" KYAKU_QT, ");
        //sql.append(" TEN_KAISUU_QT, ");
        //sql.append(" KINGAKU_VL, ");
        //sql.append(" NEBIKI_VL, ");
        //sql.append(" INSERT_USER_ID, ");
        //sql.append(" INSERT_TS, ");
        //sql.append(" UPDATE_USER_ID, ");
        //sql.append(" UPDATE_TS, ");
        //sql.append(" SHIHARAI_SYUBETSU_CD, ");
        //sql.append(" SHIHARAI_SYUBETSU_SUB_CD) ");
        //sql.append("SELECT ");
        //sql.append(" ?, ");
        //sql.append(" CASE WHEN EIGYO_DT IS NULL THEN NULL ELSE SUBSTR(EIGYO_DT,1,4) || '/' || SUBSTR(EIGYO_DT,5,2) || '/' || SUBSTR(EIGYO_DT,7,2) END, ");
        //// 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sql.append(" LPAD(TPE.STORE,6,'0'), ");
        ////sql.append(" TPE.STORE, ");
        //// 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        //// 2016/12/13 VINX J.Endo FIVI対応 #3304 MOD(S)
        ////sql.append(" SUBSTR(TPE.CASHIER_ID,2,8), ");
        //sql.append(" SUBSTR(TPE.CASHIER_ID,1,8), ");
        //// 2016/12/13 VINX J.Endo FIVI対応 #3304 MOD(E)
        //sql.append(" ?, ");
        //sql.append(" ?, ");
        //sql.append(" ?, ");
        //sql.append(" ?, ");
        //sql.append(" ?, ");
        //sql.append(" SUM(NVL(PAYMENT_SALES, '0')), ");
        //sql.append(" ?, ");
        //sql.append(" ?, ");
        //sql.append(" ?, ");
        //sql.append(" ?, ");
        //sql.append(" ?, ");
        //sql.append(" TPE.PYMT_TYPE, ");
        //sql.append(" TPE.PYMT_TYPE2 ");
        //sql.append("FROM");
        //sql.append(" TMP_POS_E_TENDER TPE ");
        //sql.append("GROUP BY ");
        //sql.append(" TPE.EIGYO_DT, ");
        //sql.append(" TPE.STORE, ");
        //sql.append(" TPE.CASHIER_ID, ");
        //sql.append(" TPE.PYMT_TYPE, ");
        //sql.append(" TPE.PYMT_TYPE2 ");
        sql.append("INSERT /*+ APPEND */ INTO TMP_SEKININSYA_SEISAN ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,JIKAN_TM ");
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
        sql.append("    ? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("   ,CASE WHEN EIGYO_DT IS NULL ");
//        sql.append("        THEN NULL ");
//        sql.append("        ELSE SUBSTR(EIGYO_DT,1,4) || '/' || SUBSTR(EIGYO_DT,5,2) || '/' || SUBSTR(EIGYO_DT,7,2) ");
//        sql.append("    END ");
//        sql.append("   ,LPAD(TPE.STORE,6,'0') ");
//        sql.append("   ,SUBSTR(TPE.CASHIER_ID,1,8) ");
        sql.append("   ,CASE WHEN WPE.EIGYO_DT IS NULL ");
        sql.append("        THEN NULL ");
        sql.append("        ELSE SUBSTR(WPE.EIGYO_DT,1,4) || '/' || SUBSTR(WPE.EIGYO_DT,5,2) || '/' || SUBSTR(WPE.EIGYO_DT,7,2) ");
        sql.append("    END ");
        sql.append("   ,LPAD(WPE.STORE,6,'0') ");
        sql.append("   ,SUBSTR(WPE.CASHIER_ID,1,8) ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("   ,'00000000' ");
        sql.append("   ,'0001' ");
        sql.append("   ,'0' ");
        sql.append("   ,'0' ");
        sql.append("   ,'0' ");
        // 2017/08/31 VINX N.Katou #5840 (S)
        // 2017/03/13 VINX J.Endo FIVI対応 #4322 MOD(S)
        //sql.append("   ,SUM(NVL(PAYMENT_SALES, '0')) ");
//        sql.append("   ,TO_NUMBER(MAX(NVL(PAYMENT_SALES, '0'))) ");
        sql.append("   ,TO_NUMBER(MAX(NVL(WPE.PAYMENT_SALES, '0'))) ");
        // 2017/03/13 VINX J.Endo FIVI対応 #4322 MOD(E)
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("   ,'0' ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("   ,TPE.PYMT_TYPE ");
//        sql.append("   ,TPE.PYMT_TYPE2 ");
        sql.append("   ,WPE.PYMT_TYPE ");
        sql.append("   ,WPE.PYMT_TYPE2 ");
//        sql.append("FROM TMP_POS_E_TENDER TPE ");
        sql.append("FROM WK_POS_E_TENDER WPE ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("WHERE ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("    TPE.PYMT_TYPE <> 'CSH' OR ");
//        // 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(S)
//        //sql.append("    TPE.PYMT_TYPE2 <> 'CSH00' ");
//        sql.append("    TPE.PYMT_TYPE2 <> 'CSH0000' ");
//        // 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(E)
//        sql.append("GROUP BY ");
//        sql.append("    TPE.EIGYO_DT ");
//        sql.append("   ,TPE.STORE ");
//        sql.append("   ,TPE.CASHIER_ID ");
//        sql.append("   ,TPE.PYMT_TYPE ");
//        sql.append("   ,TPE.PYMT_TYPE2 ");
        sql.append("   (WPE.PYMT_TYPE <> 'CSH' OR ");
        sql.append("    WPE.PYMT_TYPE2 <> 'CSH0000') AND ");
        sql.append("    WPE.ERR_KB = '0' ");
        sql.append("GROUP BY ");
        sql.append("    WPE.EIGYO_DT ");
        sql.append("   ,WPE.STORE ");
        sql.append("   ,WPE.CASHIER_ID ");
        sql.append("   ,WPE.PYMT_TYPE ");
        sql.append("   ,WPE.PYMT_TYPE2 ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        
        // 2017/01/17 VINX J.Endo FIVI対応 #3610 MOD(S)
        //sql.append("UNION ");
        //sql.append("SELECT ");
        //sql.append("    ? ");
        //sql.append("   ,CASE WHEN TPE.EIGYO_DT IS NULL ");
        //sql.append("        THEN NULL ");
        //sql.append("        ELSE SUBSTR(TPE.EIGYO_DT,1,4) || '/' || SUBSTR(TPE.EIGYO_DT,5,2) || '/' || SUBSTR(TPE.EIGYO_DT,7,2) ");
        //sql.append("    END ");
        //sql.append("   ,LPAD(TPE.STORE,6,'0') ");
        //sql.append("   ,SUBSTR(TPE.CASHIER_ID,1,8) ");
        //sql.append("   ,'00000000' ");
        //sql.append("   ,'0001' ");
        //sql.append("   ,'0' ");
        //sql.append("   ,'0' ");
        //sql.append("   ,'0' ");
        //sql.append("   ,TPE.PAYMENT_COUNT + TPD.MIDDLE_COLLECTION_AMOUNT - TPD.LOAN_AMOUNT ");
        //sql.append("   ,'0' ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,TPE.PYMT_TYPE ");
        //sql.append("   ,TPE.PYMT_TYPE2 ");
        //sql.append("FROM TMP_POS_E_TENDER TPE ");
        //sql.append("LEFT OUTER JOIN ( ");
        //sql.append("    SELECT ");
        //sql.append("        MIN(COMMAND) AS COMMAND ");
        //sql.append("       ,STORE ");
        //sql.append("       ,MIN(POS) AS POS ");
        //sql.append("       ,MIN(TRANS_NO) AS TRANS_NO ");
        //sql.append("       ,CASHIER_ID ");
        //sql.append("       ,EIGYO_DT ");
        //sql.append("       ,SUM(NVL(MIDDLE_COLLECTION_AMOUNT,0)) AS MIDDLE_COLLECTION_AMOUNT ");
        //sql.append("       ,SUM(NVL(LOAN_AMOUNT,0)) AS LOAN_AMOUNT ");
        //sql.append("    FROM TMP_POS_D_CASH ");
        //sql.append("    GROUP BY ");
        //sql.append("        EIGYO_DT ");
        //sql.append("       ,STORE ");
        //sql.append("       ,CASHIER_ID ");
        //sql.append("    ) TPD ");
        //sql.append("ON  TPE.COMMAND    = TPD.COMMAND AND ");
        //sql.append("    TPE.STORE      = TPD.STORE AND ");
        //sql.append("    TPE.POS        = TPD.POS AND ");
        //sql.append("    TPE.TRANS_NO   = TPD.TRANS_NO AND ");
        //sql.append("    TPE.CASHIER_ID = TPD.CASHIER_ID AND ");
        //sql.append("    TPE.EIGYO_DT   = TPD.EIGYO_DT ");
        //sql.append("INNER JOIN ( ");
        //sql.append("    SELECT ");
        //sql.append("        MAX(TPB.TORI_TIME) AS MAX_TORI_TIME, ");
        //sql.append("        MAX(TPB.TORI_TIME ");
        //sql.append("     || TPE2.TRANS_NO ");
        //sql.append("     || TPE2.POS ");
        //sql.append("     || TPE2.CASHIER_ID ");
        //sql.append("     || TPE2.STORE ");
        //sql.append("     || TPE2.EIGYO_DT ");
        //sql.append("     || TPE2.COMMAND) AS PKBE ");
        //sql.append("    FROM TMP_POS_E_TENDER TPE2 ");
        //sql.append("    INNER JOIN TMP_POS_B_TOTAL TPB ");
        //sql.append("    ON  TPE2.COMMAND    = TPB.COMMAND AND ");
        //sql.append("        TPE2.STORE      = TPB.STORE AND ");
        //sql.append("        TPE2.POS        = TPB.POS AND ");
        //sql.append("        TPE2.TRANS_NO   = TPB.TRANS_NO AND ");
        //sql.append("        TPE2.CASHIER_ID = TPB.CASHIER_ID AND ");
        //sql.append("        TPE2.EIGYO_DT   = TPB.EIGYO_DT ");
        //sql.append("    WHERE ");
        //sql.append("        TPE2.PYMT_TYPE  = 'CSH' AND ");
        //sql.append("        TPE2.PYMT_TYPE2 = 'CSH00' AND ");
        //sql.append("        NVL(TPE2.PAYMENT_COUNT,0) > 0 ");
        //sql.append("    GROUP BY ");
        //sql.append("        TPE2.EIGYO_DT ");
        //sql.append("       ,TPE2.STORE ");
        //sql.append("       ,TPE2.CASHIER_ID ");
        //sql.append("    ) TPB_TPE ");
        //sql.append("ON (TPE.TRANS_NO ");
        //sql.append(" || TPE.POS ");
        //sql.append(" || TPE.CASHIER_ID ");
        //sql.append(" || TPE.STORE ");
        //sql.append(" || TPE.EIGYO_DT ");
        //sql.append(" || TPE.COMMAND) = SUBSTR(TPB_TPE.PKBE,LENGTH(TPB_TPE.MAX_TORI_TIME)+1) ");
        //sql.append("WHERE ");
        //sql.append("    TPE.PYMT_TYPE = 'CSH' AND ");
        //sql.append("    TPE.PYMT_TYPE2 = 'CSH00' ");
        sql.append("UNION ");
        sql.append("SELECT ");
        sql.append("    ? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("   ,CASE WHEN TPE.EIGYO_DT IS NULL ");
//        sql.append("        THEN NULL ");
//        sql.append("        ELSE SUBSTR(TPE.EIGYO_DT,1,4) || '/' || SUBSTR(TPE.EIGYO_DT,5,2) || '/' || SUBSTR(TPE.EIGYO_DT,7,2) ");
//        sql.append("    END ");
//        sql.append("   ,LPAD(TPE.STORE,6,'0') ");
//        sql.append("   ,SUBSTR(TPE.CASHIER_ID,1,8) ");
        sql.append("   ,CASE WHEN WPE.EIGYO_DT IS NULL ");
        sql.append("        THEN NULL ");
        sql.append("        ELSE SUBSTR(WPE.EIGYO_DT,1,4) || '/' || SUBSTR(WPE.EIGYO_DT,5,2) || '/' || SUBSTR(WPE.EIGYO_DT,7,2) ");
        sql.append("    END ");
        sql.append("   ,LPAD(WPE.STORE,6,'0') ");
        sql.append("   ,SUBSTR(WPE.CASHIER_ID,1,8) ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("   ,'00000000' ");
        sql.append("   ,'0001' ");
        sql.append("   ,'0' ");
        sql.append("   ,'0' ");
        sql.append("   ,'0' ");
        // 2017/08/31 VINX N.Katou #5840 (S)
        // 2017/03/13 VINX J.Endo FIVI対応 #4322 MOD(S)
        //sql.append("   ,NVL(TPETPB.PAYMENT_COUNT,0) + NVL(TPD.MIDDLE_COLLECTION_AMOUNT,0) - NVL(TPD.LOAN_AMOUNT,0) ");
//        sql.append("   ,NVL(TPD.MIDDLE_COLLECTION_AMOUNT,0) - NVL(TPD.LOAN_AMOUNT,0) ");
        // #6278 URI01001 UPD 2020.11.12 THONG.VQ (S)
        //sql.append("   ,NVL(WPD.MIDDLE_COLLECTION_AMOUNT,0) - NVL(WPD.LOAN_AMOUNT,0) ");
        sql.append("   ,NVL(WPE.PAYMENT_COUNT,0) - NVL(WPD.LOAN_AMOUNT,0) + NVL(WPD.MIDDLE_COLLECTION_AMOUNT,0) ");
        // #6278 URI01001 UPD 2020.11.12 THONG.VQ (E)
        // 2017/03/13 VINX J.Endo FIVI対応 #4322 MOD(E)
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("   ,'0' ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("   ,TPE.PYMT_TYPE ");
//        sql.append("   ,TPE.PYMT_TYPE2 ");
        sql.append("   ,WPE.PYMT_TYPE ");
        sql.append("   ,WPE.PYMT_TYPE2 ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("FROM ( ");
        sql.append("    SELECT "); // TMP_POS_E_TENDERのキー情報を作成（メイン）
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        TPE1.EIGYO_DT ");
//        sql.append("       ,TPE1.STORE ");
//        sql.append("       ,TPE1.CASHIER_ID ");
//        sql.append("       ,TPE1.PYMT_TYPE ");
//        sql.append("       ,TPE1.PYMT_TYPE2 ");
//        sql.append("    FROM TMP_POS_E_TENDER TPE1 ");
        sql.append("        WPE1.EIGYO_DT ");
        sql.append("       ,WPE1.STORE ");
        sql.append("       ,WPE1.CASHIER_ID ");
        sql.append("       ,WPE1.PYMT_TYPE ");
        sql.append("       ,WPE1.PYMT_TYPE2 ");
        // #6278 URI01001 UPD 2020.11.12 THONG.VQ (S)
        sql.append("       ,WPE1.PAYMENT_COUNT ");
        // #6278 URI01001 UPD 2020.11.12 THONG.VQ (E)
        sql.append("    FROM WK_POS_E_TENDER WPE1 ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("    WHERE ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        TPE1.PYMT_TYPE  = 'CSH' AND ");
//        // 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(S)
//        //sql.append("        TPE1.PYMT_TYPE2 = 'CSH00' ");
//        sql.append("        TPE1.PYMT_TYPE2 = 'CSH0000' ");
//        // 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(E)
//        sql.append("    GROUP BY ");
//        sql.append("        TPE1.EIGYO_DT ");
//        sql.append("       ,TPE1.STORE ");
//        sql.append("       ,TPE1.CASHIER_ID ");
//        sql.append("       ,TPE1.PYMT_TYPE ");
//        sql.append("       ,TPE1.PYMT_TYPE2 ");
//        sql.append("    ) TPE ");
        sql.append("        WPE1.PYMT_TYPE  = 'CSH' AND ");
        sql.append("        WPE1.PYMT_TYPE2 = 'CSH0000' AND ");
        sql.append("        WPE1.ERR_KB = '0' ");
        sql.append("    GROUP BY ");
        sql.append("        WPE1.EIGYO_DT ");
        sql.append("       ,WPE1.STORE ");
        sql.append("       ,WPE1.CASHIER_ID ");
        sql.append("       ,WPE1.PYMT_TYPE ");
        sql.append("       ,WPE1.PYMT_TYPE2 ");
        // #6278 URI01001 UPD 2020.11.12 THONG.VQ (S)
        sql.append("       ,WPE1.PAYMENT_COUNT ");
        // #6278 URI01001 UPD 2020.11.12 THONG.VQ (E)
        sql.append("    ) WPE ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("    LEFT OUTER JOIN ( "); // TMP_POS_D_CASHの集計を作成（外部結合）
        sql.append("        SELECT ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("            TPD1.EIGYO_DT ");
//        sql.append("           ,TPD1.STORE ");
//        sql.append("           ,TPD1.CASHIER_ID ");
//        sql.append("           ,SUM(NVL(TPD1.MIDDLE_COLLECTION_AMOUNT,0)) AS MIDDLE_COLLECTION_AMOUNT ");
//        sql.append("           ,SUM(NVL(TPD1.LOAN_AMOUNT,0)) AS LOAN_AMOUNT ");
//        sql.append("        FROM TMP_POS_D_CASH TPD1 ");
//        sql.append("        GROUP BY ");
//        sql.append("            TPD1.EIGYO_DT ");
//        sql.append("           ,TPD1.STORE ");
//        sql.append("           ,TPD1.CASHIER_ID ");
//        sql.append("        ) TPD ");
//        sql.append("    ON  TPE.EIGYO_DT   = TPD.EIGYO_DT AND ");
//        sql.append("        TPE.STORE      = TPD.STORE  AND ");
//        sql.append("        TPE.CASHIER_ID = TPD.CASHIER_ID ");
        sql.append("            WPD1.EIGYO_DT ");
        sql.append("           ,WPD1.STORE ");
        sql.append("           ,WPD1.CASHIER_ID ");
        sql.append("           ,SUM(NVL(WPD1.MIDDLE_COLLECTION_AMOUNT,0)) AS MIDDLE_COLLECTION_AMOUNT ");
        sql.append("           ,SUM(NVL(WPD1.LOAN_AMOUNT,0)) AS LOAN_AMOUNT ");
        sql.append("        FROM WK_POS_D_CASH WPD1 ");
        sql.append("        WHERE ");
        sql.append("            WPD1.ERR_KB = '0' ");
        sql.append("        GROUP BY ");
        sql.append("            WPD1.EIGYO_DT ");
        sql.append("           ,WPD1.STORE ");
        sql.append("           ,WPD1.CASHIER_ID ");
        sql.append("        ) WPD ");
        sql.append("    ON  WPE.EIGYO_DT   = WPD.EIGYO_DT AND ");
        sql.append("        WPE.STORE      = WPD.STORE  AND ");
        sql.append("        WPE.CASHIER_ID = WPD.CASHIER_ID ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        // 2017/03/13 VINX J.Endo FIVI対応 #4322 DEL(S)
        //sql.append("    LEFT OUTER JOIN ( "); // TMP_POS_E_TENDER、TMP_POS_B_TOTALより、（営業日、店舗コード、キャッシャー）単位で交代時金額を取得する。
        //sql.append("        SELECT ");
        //sql.append("            TPE2.EIGYO_DT ");
        //sql.append("           ,TPE2.STORE ");
        //sql.append("           ,TPE2.CASHIER_ID ");
        //sql.append("           ,TPE2.PAYMENT_COUNT ");
        //sql.append("        FROM TMP_POS_E_TENDER TPE2 ");
        //sql.append("        INNER JOIN ( ");
        //sql.append("            SELECT ");
        //sql.append("                MAX(BBB.TORI_TIME) AS MAX_TORI_TIME ");
        //sql.append("               ,MAX(BBB.TORI_TIME || EEE.TRANS_NO || EEE.POS || EEE.CASHIER_ID || EEE.STORE || EEE.EIGYO_DT || EEE.COMMAND) AS PKBE ");
        //sql.append("            FROM TMP_POS_E_TENDER EEE ");
        //sql.append("            INNER JOIN TMP_POS_B_TOTAL BBB ");
        //sql.append("            ON  EEE.COMMAND    = BBB.COMMAND AND ");
        //sql.append("                EEE.STORE      = BBB.STORE AND ");
        //sql.append("                EEE.POS        = BBB.POS AND ");
        //sql.append("                EEE.TRANS_NO   = BBB.TRANS_NO AND ");
        //sql.append("                EEE.CASHIER_ID = BBB.CASHIER_ID AND ");
        //sql.append("                EEE.EIGYO_DT   = BBB.EIGYO_DT ");
        //sql.append("            WHERE ");
        //sql.append("                EEE.PYMT_TYPE  = 'CSH' AND ");
        //// 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(S)
        ////sql.append("                EEE.PYMT_TYPE2 = 'CSH00' AND ");
        //sql.append("                EEE.PYMT_TYPE2 = 'CSH0000' AND ");
        //// 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(E)
        //sql.append("                NVL(EEE.PAYMENT_COUNT,0) > 0 ");
        //sql.append("            GROUP BY ");
        //sql.append("                EEE.EIGYO_DT ");
        //sql.append("               ,EEE.STORE ");
        //sql.append("               ,EEE.CASHIER_ID ");
        //sql.append("            ) BBBEEE ");
        //sql.append("        ON (TPE2.TRANS_NO || TPE2.POS || TPE2.CASHIER_ID || TPE2.STORE || TPE2.EIGYO_DT || TPE2.COMMAND) = SUBSTR(BBBEEE.PKBE,LENGTH(BBBEEE.MAX_TORI_TIME)+1) ");
        //sql.append("        WHERE ");
        //sql.append("            TPE2.PYMT_TYPE  = 'CSH' AND ");
        //// 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(S)
        ////sql.append("            TPE2.PYMT_TYPE2 = 'CSH00' ");
        //sql.append("            TPE2.PYMT_TYPE2 = 'CSH0000' ");
        //// 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(E)
        //sql.append("        ) TPETPB ");
        //sql.append("    ON  TPE.EIGYO_DT   = TPETPB.EIGYO_DT AND ");
        //sql.append("        TPE.STORE      = TPETPB.STORE AND ");
        //sql.append("        TPE.CASHIER_ID = TPETPB.CASHIER_ID ");
        //// 2017/01/17 VINX J.Endo FIVI対応 #3610 MOD(E)
        // 2017/03/13 VINX J.Endo FIVI対応 #4322 DEL(E)

        sql.append("UNION ");
        sql.append("SELECT ");
        sql.append("    ? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("   ,CASE WHEN TPD.EIGYO_DT IS NULL ");
//        sql.append("        THEN NULL ");
//        sql.append("        ELSE SUBSTR(TPD.EIGYO_DT,1,4) || '/' || SUBSTR(TPD.EIGYO_DT,5,2) || '/' || SUBSTR(TPD.EIGYO_DT,7,2) ");
//        sql.append("    END ");
//        sql.append("   ,LPAD(TPD.STORE,6,'0') ");
//        sql.append("   ,SUBSTR(TPD.CASHIER_ID,1,8) ");
        sql.append("   ,CASE WHEN WPD.EIGYO_DT IS NULL ");
        sql.append("        THEN NULL ");
        sql.append("        ELSE SUBSTR(WPD.EIGYO_DT,1,4) || '/' || SUBSTR(WPD.EIGYO_DT,5,2) || '/' || SUBSTR(WPD.EIGYO_DT,7,2) ");
        sql.append("    END ");
        sql.append("   ,LPAD(WPD.STORE,6,'0') ");
        sql.append("   ,SUBSTR(WPD.CASHIER_ID,1,8) ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("   ,'00000000' ");
        sql.append("   ,'0001' ");
        sql.append("   ,'0' ");
        sql.append("   ,'0' ");
        sql.append("   ,'0' ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("   ,SUM(NVL(TPD.MIDDLE_COLLECTION_AMOUNT,0)) - SUM(NVL(TPD.LOAN_AMOUNT,0)) ");
        // #6278 URI01001 UPD 2020.11.12 THONG.VQ (S)
        //sql.append("   ,SUM(NVL(WPD.MIDDLE_COLLECTION_AMOUNT,0)) - SUM(NVL(WPD.LOAN_AMOUNT,0)) ");
        sql.append("   ,SUM(NVL(WPE.PAYMENT_COUNT,0)) - SUM(NVL(WPD.LOAN_AMOUNT,0)) + SUM(NVL(WPD.MIDDLE_COLLECTION_AMOUNT,0)) ");
        // #6278 URI01001 UPD 2020.11.12 THONG.VQ (E)
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("   ,'0' ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,'CSH' ");
        // 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(S)
        //sql.append("   ,'CSH00' ");
        sql.append("   ,'CSH0000' ");
        // 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(E)
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("FROM TMP_POS_D_CASH TPD ");
//        sql.append("LEFT OUTER JOIN TMP_POS_E_TENDER TPE ");
//        sql.append("ON  TPE.PYMT_TYPE  = 'CSH' AND ");
//        // 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(S)
//        //sql.append("    TPE.PYMT_TYPE2 = 'CSH00' AND ");
//        sql.append("    TPE.PYMT_TYPE2 = 'CSH0000' AND ");
//        // 2017/02/16 VINX J.Endo FIVI対応 #4013 MOD(E)
//        sql.append("    TPD.COMMAND    = TPE.COMMAND AND ");
//        sql.append("    TPD.STORE      = TPE.STORE AND ");
//        // 2017/03/13 VINX J.Endo FIVI対応 #4322 DEL(S)
//        //sql.append("    TPD.POS        = TPE.POS AND ");
//        //sql.append("    TPD.TRANS_NO   = TPE.TRANS_NO AND ");
//        // 2017/03/13 VINX J.Endo FIVI対応 #4322 DEL(E)
//        sql.append("    TPD.CASHIER_ID = TPE.CASHIER_ID AND ");
//        sql.append("    TPD.EIGYO_DT   = TPE.EIGYO_DT ");
//        sql.append("WHERE TPE.CASHIER_ID IS NULL ");
//        sql.append("GROUP BY ");
//        sql.append("    TPD.EIGYO_DT ");
//        sql.append("   ,TPD.STORE ");
//        sql.append("   ,TPD.CASHIER_ID ");
//        // 2016/12/22 VINX J.Endo FIVI対応 #3273 MOD(E)
        sql.append("FROM WK_POS_D_CASH WPD ");
        sql.append("LEFT OUTER JOIN WK_POS_E_TENDER WPE ");
        sql.append("ON  WPE.PYMT_TYPE  = 'CSH' AND ");
        sql.append("    WPE.PYMT_TYPE2 = 'CSH0000' AND ");
        sql.append("    WPE.ERR_KB = '0' AND ");
        sql.append("    WPD.COMMAND    = WPE.COMMAND AND ");
        sql.append("    WPD.STORE      = WPE.STORE AND ");
        sql.append("    WPD.CASHIER_ID = WPE.CASHIER_ID AND ");
        sql.append("    WPD.EIGYO_DT   = WPE.EIGYO_DT ");
        sql.append("WHERE WPE.CASHIER_ID IS NULL AND ");
        sql.append("      WPD.ERR_KB = '0' ");
        sql.append("GROUP BY ");
        sql.append("    WPD.EIGYO_DT ");
        sql.append("   ,WPD.STORE ");
        sql.append("   ,WPD.CASHIER_ID ");
        // 2017/08/31 VINX N.Katou #5840 (E)

        return sql.toString();
    }

    private String createKeyString(String tenpoCd, String checkerCd) {
        return tenpoCd.concat(checkerCd);
    }

    private String getTenpoCd(String keyString) {
        return keyString.substring(0, 6);
    }

}

//public class SekininsyaSeisanTorikomiDao extends TorikomiDaoSuper {
//
//    /** バッチ処理名 */
//    private static final String DAO_NAME = "POS実績取込処理(責任者精算)";
//    /** 登録先テーブル名称 */
//    private static final String INS_TABLE_NAME = "TMP責任者精算データ";
//    /** 責任者精算取込ファイル明細固定項目数 */
//    private static final int SEKININSHA_DETAIL_STATIC_LIST_LENGTH = 2;
//    /** 責任者精算取込ファイル明細ループ項目数 */
//    private static final int SEKININSHA_DETAIL_LOOP_LIST_LENGTH = 6;
//
//    // 追加SQL文用定数
//    private static final String INS_SQL = "insertTmpSekininshaSeisan";
//    // 削除SQL文用定数
//    private static final String DEL_SQL = "deleteTmpSekininshaSeisan";
//
//    /** インプットBean */
//    private SekininsyaSeisanTorikomiDaoInputBean inputBean = null;
//
//    /**
//     * 明細レコードからデータを登録する
//     *
//     * @param preparedStatementExIns インサート用preparedStatement
//     * @param csvlist 明細レコード(コンマ分割後配列)
//     * @param tenpoCd 店コード
//     * @param eigyoDt 営業日
//     * @param dbServerTime DBサーバ時刻
//     * @return 登録データ件数
//     * @throws SQLException
//     */
//    protected int insertData(PreparedStatementEx preparedStatementExIns, String[] csvlist, String tenpoCd, String eigyoDt, String dbServerTime) throws SQLException {
//
//        int dataCnt = (csvlist.length - SEKININSHA_DETAIL_STATIC_LIST_LENGTH) / SEKININSHA_DETAIL_LOOP_LIST_LENGTH;
//
//        for (int i = 0; i < dataCnt; i++) {
//            preparedStatementExIns.setString(1, COMP_CD);
//            preparedStatementExIns.setString(2, eigyoDt);
//            // preparedStatementExIns.setString(3, tenpoCd);
//            // preparedStatementExIns.setString(4, csvlist[0]);
//            // preparedStatementExIns.setString(5, csvlist[1]);
//            // int baseIndex = i * SEKININSHA_DETAIL_LOOP_LIST_LENGTH +
//            // SEKININSHA_DETAIL_STATIC_LIST_LENGTH;
//            // preparedStatementExIns.setString(6, csvlist[baseIndex + 0]);
//            // preparedStatementExIns.setString(7, csvlist[baseIndex + 1]);
//            // preparedStatementExIns.setString(8, csvlist[baseIndex + 2]);
//            // preparedStatementExIns.setString(9, csvlist[baseIndex + 3]);
//            // preparedStatementExIns.setString(10, csvlist[baseIndex + 4]);
//            // preparedStatementExIns.setString(11, csvlist[baseIndex + 5]);
//            // preparedStatementExIns.setString(12, userId);
//            // preparedStatementExIns.setString(13, dbServerTime);
//            // preparedStatementExIns.setString(14, userId);
//            // preparedStatementExIns.setString(15, dbServerTime);
//
//            // 登録実行
//            preparedStatementExIns.executeUpdate();
//        }
//
//        return dataCnt;
//    }
//
//    /**
//     * 明細行判定
//     *
//     * @param recordColumnCount レコードカラム桁数
//     * @return true：明細行である、false：明細行ではない
//     */
//    protected boolean isDetailRecord(int recordColumnCount) {
//        return ((recordColumnCount - SEKININSHA_DETAIL_STATIC_LIST_LENGTH) % SEKININSHA_DETAIL_LOOP_LIST_LENGTH) == 0;
//    }
//
//    /**
//     * アウトプットBeanを取得する
//     *
//     * @return Object (アウトプットがないためnull)
//     */
//    public Object getOutputObject() throws Exception {
//        return null;
//    }
//
//    /**
//     * インプットBeanを設定する
//     *
//     * @param Object input SekininsyaSeisanTorikomiDaoInputBean型オブジェクト
//     */
//    public void setInputObject(Object input) throws Exception {
//        inputBean = (SekininsyaSeisanTorikomiDaoInputBean) input;
//
//    }
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        try {
//            DaoIf dao = new SekininsyaSeisanTorikomiDao();
//            new StandAloneDaoInvoker("mm").InvokeDao(dao);
//            System.out.println(dao.getOutputObject());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (DaoTimeOutException e) {
//            e.printStackTrace();
//        } catch (DaoException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * JOBIDを取得します。
//     *
//     * @return バッチ処理名
//     */
//    String getJobId() {
//        return inputBean.getJobId();
//    }
//
//    /**
//     * バッチ処理名を取得します。
//     *
//     * @return バッチ処理名
//     */
//    String getDaoName() {
//        return DAO_NAME;
//    }
//
//    /**
//     * 登録先テーブル名称を取得します。
//     *
//     * @return 登録先テーブル名称
//     */
//    String getInsTableName() {
//        return INS_TABLE_NAME;
//    }
//
//    /**
//     * 取込ファイルを取得します。
//     *
//     * @return 取込ファイル
//     */
//    String getFileName() {
//        return inputBean.getFileId();
//    }
//
//    /**
//     * 追加SQL文を取得します。
//     *
//     * @return 追加SQL文
//     */
//    String getInsSql() {
//        return INS_SQL;
//    }
//
//    /**
//     * 削除SQL文を取得します。
//     *
//     * @return 削除SQL文
//     */
//    String getDelSql() {
//        return DEL_SQL;
//    }
//
//    /**
//     * バックアップフォルダPIDを取得します。
//     *
//     * @return バックアップフォルダPID
//     */
//    String getBackUpDirPID() {
//        return inputBean.getBackUpDirPID();
//    }
//
//    /**
//     * SEITO社POSDataの取り込み処理
//     *
//     * @param invoker データベースアクセスオブジェクトの為のinvoker
//     * @param jobId ジョブID
//     * @param dirPath ディレクトリパス
//     * @param mainFileId ファイル名
//     * @throws Exception 例外
//     */
//    protected void seitoPosTorikomi(DaoInvokerIf invoker, String jobId, String dirPath, String mainFileId) throws Exception {
//
//        // ユーザID
//        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + daoName;
//
//        // ログ出力
//        invoker.infoLog(strUserId + "　：　" + daoName + "を開始します。");
//
//        // ディレクトリを取得する
//        File directory = new File(dirPath);
//        if (!directory.isDirectory()) {
//            invoker.errorLog("ディレクトリを正しく指定して下さい。");
//            throw new IllegalStateException("ディレクトリを正しく指定して下さい。");
//        }
//
//        // ファイルをリスト化
//        File readFile = new File(directory + "/" + fileName);
//        if (!readFile.exists()) {
//            invoker.errorLog(dirPath + "にファイルが存在しません");
//        } else {
//
//            BufferedReader file = null;
//
//            int insertCount = 0;
//
//            // オブジェクトを初期化する
//            PreparedStatementEx preparedStatementExIns = null;
//            PreparedStatementEx preparedStatementDelete = null;
//            PreparedStatementEx preparedStatementSyohinMasterSelect = null;
//
//            try {
//
//                // SQLを取得し、TMPテーブルを削除する
//                preparedStatementDelete = invoker.getDataBase().prepareStatement(delSql);
//                preparedStatementDelete.execute();
//
//                // SQLを取得し、パラメータを条件にバインドする
//                preparedStatementExIns = invoker.getDataBase().prepareStatement(insSql);
//
//                // ファイル読み込み
//                file = new BufferedReader(new FileReader(readFile));
//
//                String eigyoDt = null;
//
//                // バッチ日付を取得
//                String batchDt = FiResorceUtility.getDBServerTime();
//
//                // Item Recordリスト(Type=A)
//                List<String> itemRecordList = new ArrayList<String>();
//                // Total Recordリスト(Type=B)
//                List<String> totalRecordList = new ArrayList<String>();
//
//                // 店舗コードと責任者コードの結合文字列セット
//                Set<String> keyStringSet = new TreeSet<String>();
//
//                // ファイルの全行を読み込み
//                String record = null;
//                while ((record = file.readLine()) != null) {
//
//                    // Record Formatを取得
//                    char recordFormat = TorikomiUtility.getRecordFormat(record);
//
//                    // Item Recordのリスト、Total Recordのリスト、集計単位のセットを作成
//                    if (recordFormat == 'A') {
//
//                        // RecordをItem Recordリストへ追加
//                        itemRecordList.add(record);
//
//                        // 店舗コードと責任者コードを生成
//                        String tenpoCd = TorikomiUtility.createTenpoCd(record);
//                        String checkerCd = TorikomiUtility.createCheckerCd(record);
//
//                        // 店舗コードと責任者コードの結合文字列を生成
//                        String keyString = createKeyString(tenpoCd, checkerCd);
//
//                        // セットへ追加
//                        keyStringSet.add(keyString);
//                    } else if (recordFormat == 'B') {
//                        totalRecordList.add(record);
//                    }
//                }
//
//                // 店舗コード、責任者コード単位で集計して登録
//                for (String keyString : keyStringSet) {
//
//                    // 店舗コード、責任者コードを取得
//                    String tenpoCd = getTenpoCd(keyString);
//                    String checkerCd = getTenpoCd(keyString);
//
//                    // 客数集計用
//                    Set<String> transactionNoSet = new TreeSet<String>();
//                    // 点数/回数
//                    int tensu = 0;
//                    // 金額
//                    BigDecimal amountVl = new BigDecimal(0);
//                    // 値引金額
//                    BigDecimal amountDiscountVl = new BigDecimal(0);
//
//                    // 集計
//                    for (String itemRecord : itemRecordList) {
//
//                        // 店舗コードと責任者コードが一致する場合
//                        if (tenpoCd.equals(TorikomiUtility.createTenpoCd(itemRecord))
//                                && checkerCd.equals(TorikomiUtility.createCheckerCd(itemRecord))) {
//
//                            // 客数集計用
//                            transactionNoSet.add(TorikomiUtility.getTransactionNo(itemRecord));
//
//                            // 点数/回数
//                            tensu += TorikomiUtility.getActualQuantitySoldWithRoundHalfUp(itemRecord).intValue();
//
//                            // 値引前税込売価
//                            BigDecimal regularExtendedRetailPrice = new BigDecimal(TorikomiUtility.getRegularExtendedRetailPrice(itemRecord));
//
//                            // 値引後税込売価
//                            BigDecimal actualExtendedRetailPrice = new BigDecimal(TorikomiUtility.getActualExtendedRetailPrice(itemRecord));
//
//                            // 金額
//                            amountVl = amountVl.add(actualExtendedRetailPrice);
//
//                            // 値引金額
//                            amountDiscountVl = amountDiscountVl.add(regularExtendedRetailPrice.subtract(actualExtendedRetailPrice));
//                        }
//
//                        // 計上日を算出
//                        for (String totalRecord : totalRecordList) {
//                            // Recordの店舗コードとトランザクションNOが一致するか評価
//                            if (tenpoCd.equals(TorikomiUtility.createTenpoCd(totalRecord))
//                                    && TorikomiUtility.getTransactionNo(itemRecord).equals(TorikomiUtility.getTransactionNo(totalRecord))) {
//
//                                // 営業日を取得
//                                eigyoDt = TorikomiUtility.getEigyoDt(totalRecord);
//                            }
//                        }
//                    }
//
//                    // 法人コード
//                    preparedStatementExIns.setString(1, COMP_CD);
//                    // 営業日
//                    preparedStatementExIns.setString(2, TorikomiUtility.createEigyobi(eigyoDt));
//                    // 店舗コード
//                    preparedStatementExIns.setString(3, tenpoCd);
//                    // キャッシャーID
//                    preparedStatementExIns.setString(4, checkerCd);
//                    // 時間
//                    preparedStatementExIns.setString(5, null);
//                    // 取引コード
//                    preparedStatementExIns.setString(6, "0001");
//                    // タイプ
//                    preparedStatementExIns.setString(7, null);
//                    // 客数
//                    preparedStatementExIns.setString(8, TorikomiUtility.toSignedStringNumberValue(transactionNoSet.size(), 16));
//                    // 点数
//                    preparedStatementExIns.setString(9, TorikomiUtility.toSignedStringNumberValue(tensu, 16));
//                    // 金額
//                    preparedStatementExIns.setString(10, TorikomiUtility.toSignedStringNumberValue(amountVl, 19));
//                    // 値引金額
//                    preparedStatementExIns.setString(11, TorikomiUtility.toSignedStringNumberValue(amountDiscountVl, 19));
//                    preparedStatementExIns.setString(12, userId);
//                    preparedStatementExIns.setString(13, batchDt);
//                    preparedStatementExIns.setString(14, userId);
//                    preparedStatementExIns.setString(15, batchDt);
//
//                    // 登録実行
//                    preparedStatementExIns.executeUpdate();
//
//                    insertCount++;
//                }
//
//                file.close();
//
//                // ログ出力
//                invoker.infoLog(strUserId + "　：　" + insTableName + "へのデータ取込処理を終了します。取込件数は" + insertCount + "件です。");
//
//            } catch (Exception e) {
//
//                invoker.errorLog(e.toString());
//                throw e;
//
//            } finally {
//
//                try {
//                    if (file != null) {
//                        file.close();
//                    }
//
//                } catch (Exception e) {
//                    invoker.infoLog("FILE Closeエラー");
//                }
//
//                try {
//                    if (preparedStatementExIns != null) {
//                        preparedStatementExIns.close();
//                    }
//
//                    if (preparedStatementDelete != null) {
//                        preparedStatementDelete.close();
//                    }
//
//                    if (preparedStatementSyohinMasterSelect != null) {
//                        preparedStatementSyohinMasterSelect.close();
//                    }
//
//                } catch (Exception e) {
//                    invoker.infoLog("preparedStatement Closeエラー");
//                }
//            }
//        }
//
//        invoker.infoLog(strUserId + "　：　" + daoName + "を終了します。");
//    }
//
//    private String createKeyString(String tenpoCd, String checkerCd) {
//        return tenpoCd.concat(checkerCd);
//    }
//
//    private String getTenpoCd(String keyString) {
//        return keyString.substring(0, 6);
//    }
//
//}
//2016/05/10 VINX #S03対応（E)
