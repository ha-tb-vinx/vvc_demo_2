package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 * <p>
 * タイトル: ReceiptTorihikiSeisanTorikomiDao クラス
 * </p>
 * <p>
 * 説明　: POS実績取込処理（レシート別取引精算）
 * </p>
 * <p>
 * 著作権: Copyright (c) 2013
 * </p>
 * <p>
 * 会社名: VINX
 * </p>
 * 
 * @Version 1.00 (2016.05.16) VINX S.Kashihara FIVI対応
 * @Version 3.03 (2016.05.10) VINX k.Hyo FIVI対応 店舗コード4桁→6桁修正
 * @Version 3.04 (2016.12.07) VINX J.Endo FIVI対応 重複データ集約対応 #3083
 * @Version 3.05 (2017.01.19) VINX J.Endo FIVI対応 Cレコードが複数レコード発生する対応 #3468
 * @Version 3.06 (2017.03.09) N.Katou #3760
 * @Version 3.07 (2017.04.05) X.Liu #4523
 * @Version 3.08 (2017.06.21) X.Liu #5421
 * @Version 3.09 (2017.08.31) N.Katou #5840
 */
public class ReceiptTorihikiSeisanTorikomiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理（レシート別取引精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMPレシート別取引精算データ";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE TMP_RECEIPT_TORIHIKI_SEISAN";

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

            // 出力先テーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getInsertSql());

            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, userId);
            preparedStatementExIns.setString(3, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(4, userId);
            preparedStatementExIns.setString(5, FiResorceUtility.getDBServerTime());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");
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

            } catch (Exception e2) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

    }

    /**
     * 出力データ登録用SQLを取得する
     * 
     * @return 出力データ登録用SQL
     */
    private String getInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO TMP_RECEIPT_TORIHIKI_SEISAN( ");
        sql.append("    COMP_CD, ");
        sql.append("    EIGYO_DT, ");
        sql.append("    TENPO_CD, ");
        sql.append("    REGI_NO, ");
        sql.append("    TRAN_CD, ");
        sql.append("    SHIHARAI_SYUBETSU_CD, ");
        sql.append("    SHIHARAI_SYUBETSU_SUB_CD, ");
        sql.append("    CHECKER_CD, ");
        sql.append("    KINGAKU_VL, ");
        sql.append("    CREDIT_NO, ");
        sql.append("    SYONIN_NO, ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    TERMINAL_ID,");
        sql.append("    TRACE_NO,");
        sql.append("    MERCHANT_CODE,");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    INSERT_USER_ID, ");
        sql.append("    INSERT_TS, ");
        sql.append("    UPDATE_USER_ID, ");
        sql.append("    UPDATE_TS, ");
        // #3760 URIB012550 2017/3/09 N.katou(S)
        // 2017/01/19 VINX J.Endo FIVI対応(#3468) ADD(S)
//        sql.append("    KINSYU_VL) ");
        sql.append("    KINSYU_VL, ");
        // 2017/01/19 VINX J.Endo FIVI対応(#3468) ADD(E)
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("    POINT_ISSUDE_DT) ");
         // #3760 URIB012550 2017/3/09 N.katou(E)
        sql.append("    POINT_ISSUDE_DT, ");
        sql.append("    SYUUKIN_VL, ");
        sql.append("    CYOUKA_VL) ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("SELECT ");
        sql.append("    ?, ");
        sql.append("    SUBSTR(EIGYO_DT,1,8), ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    LPAD(STORE,6,'0'), ");
        //sql.append("    STORE, ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("    POS, ");
        sql.append("    TRANS_NO, ");
        sql.append("    PYMT_TYPE, ");
        sql.append("    PYMT_TYPE2, ");
        sql.append("    CASHIER_ID, ");
        sql.append("    PYMT_AMT, ");
        sql.append("    CREDIT_CARD_NUMBER, ");
        sql.append("    APP_CODE, ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    TERMINAL_ID,");
        sql.append("    TRACE_NO,");
        sql.append("    MERCHANT_CODE,");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        // 2017/01/19 VINX J.Endo FIVI対応(#3468) ADD(S)
//        sql.append("    KINSYU_VL ");
//        // 2017/01/19 VINX J.Endo FIVI対応(#3468) ADD(E)
//        // #3760 URIB012550 2017/3/09 N.katou(S)
//        sql.append("   , ISSUE_DATE_OF_PRV ");
//        // #3760 URIB012550 2017/3/09 N.katou(E)
        sql.append("    KINSYU_VL, ");
        sql.append("    ISSUE_DATE_OF_PRV, ");
        sql.append("    SYUUKIN_VL, ");
        sql.append("    CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("FROM");
        // 2016/12/07 VINX J.Endo FIVI対応(#3083) MOD(S)
        //sql.append("    TMP_POS_C_PAYMENT ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        EIGYO_DT, ");
//        sql.append("        STORE, ");
//        sql.append("        POS, ");
//        sql.append("        TRANS_NO, ");
//        sql.append("        PYMT_TYPE, ");
//        sql.append("        PYMT_TYPE2, ");
//        sql.append("        MIN(CASHIER_ID) AS CASHIER_ID, ");
//        sql.append("        SUM(PYMT_AMT) AS PYMT_AMT, ");
//        sql.append("        MIN(CREDIT_CARD_NUMBER) AS CREDIT_CARD_NUMBER, ");
//        sql.append("        MIN(APP_CODE) AS APP_CODE, ");
//        //#5421 Add X.Liu 2017.06.21 (S)
//        sql.append("        MIN(TERMINAL_ID) AS TERMINAL_ID,");
//        sql.append("        MIN(TRACE_NO) AS TRACE_NO," );
//        sql.append("        MIN(MERCHANT_CODE) AS MERCHANT_CODE,");
//        //#5421 Add X.Liu 2017.06.21 (E)
//        // 2017/01/19 VINX J.Endo FIVI対応(#3468) ADD(S)
//        sql.append("        MAX(PYMT_AMT) AS KINSYU_VL ");
//        // 2017/01/19 VINX J.Endo FIVI対応(#3468) ADD(E)
//        // #3760 URIB012550 2017/3/09 N.katou(S)
//        // #4523 Mod X.Liu 2017.04.05 (S)
////        sql.append("       , MAX(ISSUE_DATE_OF_PRV) AS ISSUE_DATE_OF_PRV ");
//        sql.append("       ,ISSUE_DATE_OF_PRV ");
//        // #4523 Mod X.Liu 2017.04.05 (E)
//        // #3760 URIB012550 2017/3/09 N.katou(E)
        sql.append("        WPCP.EIGYO_DT, ");
        sql.append("        WPCP.STORE, ");
        sql.append("        WPCP.POS, ");
        sql.append("        WPCP.TRANS_NO, ");
        sql.append("        WPCP.PYMT_TYPE, ");
        sql.append("        WPCP.PYMT_TYPE2, ");
        sql.append("        MIN(WPCP.CASHIER_ID) AS CASHIER_ID, ");
        sql.append("        SUM(WPCP.PYMT_AMT) AS PYMT_AMT, ");
        sql.append("        MIN(WPCP.CREDIT_CARD_NUMBER) AS CREDIT_CARD_NUMBER, ");
        sql.append("        MIN(WPCP.APP_CODE) AS APP_CODE, ");
        sql.append("        MIN(WPCP.TERMINAL_ID) AS TERMINAL_ID,");
        sql.append("        MIN(WPCP.TRACE_NO) AS TRACE_NO," );
        sql.append("        MIN(WPCP.MERCHANT_CODE) AS MERCHANT_CODE,");
        sql.append("        MAX(WPCP.PYMT_AMT) AS KINSYU_VL ");
        sql.append("       ,WPCP.ISSUE_DATE_OF_PRV ");
        sql.append("       , SUM(  ");
        sql.append("         CASE  ");
        sql.append("           WHEN RP.OVER_TYPE = '2'  ");
        sql.append("           AND (  ");
        sql.append("             (CTYPE = '0012' AND PYMT_AMT > 0)  ");
        sql.append("             OR (CTYPE = '0312' AND PYMT_AMT < 0)  ");
        sql.append("             OR (CTYPE = '1011' AND PYMT_AMT > 0)  ");
        sql.append("             OR (CTYPE = '1311' AND PYMT_AMT < 0) ");
        sql.append("           )  ");
        sql.append("           THEN WPCP.PYMT_AMT  ");
        sql.append("           ELSE '0'  ");
        sql.append("           END ");
        sql.append("       ) SYUUKIN_VL ");
        sql.append("       , SUM(  ");
        sql.append("         CASE  ");
        sql.append("           WHEN RP.OVER_TYPE = '2'  ");
        sql.append("           AND (  ");
        sql.append("             (CTYPE = '0012' AND PYMT_AMT < 0)  ");
        sql.append("             OR (CTYPE = '0312' AND PYMT_AMT > 0)  ");
        sql.append("             OR (CTYPE = '1011' AND PYMT_AMT < 0)  ");
        sql.append("             OR (CTYPE = '1311' AND PYMT_AMT > 0) ");
        sql.append("           )  ");
        sql.append("           THEN WPCP.PYMT_AMT * - 1  ");
        sql.append("           ELSE 0  ");
        sql.append("           END ");
        sql.append("       ) CYOUKA_VL                                  ");
//        sql.append("    FROM TMP_POS_C_PAYMENT ");
        sql.append("    FROM WK_POS_C_PAYMENT WPCP ");
        sql.append(" LEFT JOIN R_PAYMENT RP  ");
        sql.append("         ON WPCP.PYMT_TYPE = RP.SHIHARAI_SYUBETSU_CD  ");
        sql.append("         AND WPCP.PYMT_TYPE2 = RP.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("     WHERE ");
        sql.append("       ERR_KB = '0'  ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("    GROUP BY ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        EIGYO_DT, ");
//        sql.append("        STORE, ");
//        sql.append("        POS, ");
//        sql.append("        TRANS_NO, ");
//        sql.append("        PYMT_TYPE, ");
//        sql.append("        PYMT_TYPE2, ");
//        // 2017/01/19 VINX J.Endo FIVI対応(#3468) ADD(S)
//        sql.append("        CREDIT_CARD_NUMBER ");
//        // 2017/01/19 VINX J.Endo FIVI対応(#3468) ADD(E)
//        // #4523 Add X.Liu 2017.04.05 (S)
//        sql.append("        ,ISSUE_DATE_OF_PRV ");
//        // #4523 Add X.Liu 2017.04.05 (E)
//        sql.append("    ) ");
//        // 2016/12/07 VINX J.Endo FIVI対応(#3083) MOD(E)
        sql.append("        WPCP.EIGYO_DT, ");
        sql.append("        WPCP.STORE, ");
        sql.append("        WPCP.POS, ");
        sql.append("        WPCP.TRANS_NO, ");
        sql.append("        WPCP.PYMT_TYPE, ");
        sql.append("        WPCP.PYMT_TYPE2, ");
        sql.append("        WPCP.CREDIT_CARD_NUMBER, ");
        sql.append("        WPCP.ISSUE_DATE_OF_PRV ");
        sql.append("    ) ");
        // 2017/08/31 VINX N.Katou #5840 (E)

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
}
