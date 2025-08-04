package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: TenbetsuReceiptbetsuSeisanDataSakuseiDao.java クラス</p>
 * <p>説明　: 店別レシート別精算データ作成</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2016.05.20) H.Yaguma FIVI対応
 * @Version 3.01 (2016.09.13) k.Hyo FIVI対応
 * @Version 3.02 (2017.01.20) J.Endo FIVI対応 Cレコードが複数レコード発生する対応 #3468
 * @Version 3.03 (2017.03.09) N.Katou #3760
 * @Version 3.04 (2017.06.21) X.Liu #5421
 * @Version 3.05 (2017.08.31) N.Katou #5840
 * @Version 3.06 (2022.12.23) SIEU.D #6715
 *
 */
public class TenbetsuReceiptbetsuSeisanDataSakuseiDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "店別レシート別精算データ作成";
    /** 登録先テーブル名称(店別レシート別精算データ) */
    private static final String DT_TEN_RECEIPT_SEISAN = "店別レシート別精算データ";
    /** 登録先テーブル名称(店別精算状況データ) */
    private static final String DT_TEN_SEISAN_STATE = "店別精算状況データ";

    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

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
//      PreparedStatementEx preparedStatementDelete = null;

        int insertCount = 0;
        String dbServerTime = "";

        try {
            // データ登録

            // 店別レシート別精算データ
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_TEN_RECEIPT_SEISAN + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getBumonSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_TEN_RECEIPT_SEISAN + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_TEN_RECEIPT_SEISAN + "の追加を終了します。");


            // 店別精算状況データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_TEN_SEISAN_STATE + "の更新を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrBumonSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_TEN_SEISAN_STATE + "を更新しました。");
            invoker.infoLog(strUserId + "　：　" + DT_TEN_SEISAN_STATE + "の更新を終了します。");

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

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 店別レシート別精算データ登録用SQLを取得する
     *
     * @return 店別レシート別精算データ登録用SQL
     */
    private String getBumonSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_TEN_RECEIPT_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGISTER_NO ");
        sql.append("    ,TRANSACTION_NO ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,CREDIT_NO ");
        sql.append("    ,SHONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TEISEIGO_TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,TEISEIGO_TRACE_NO ");
        sql.append("    ,MERCHANT_CODE ");
        sql.append("    ,TEISEIGO_MERCHANT_CODE ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ,POS_VL ");
        sql.append("    ,TEISEIGO_VL ");
        sql.append("    ,SAGAKU_VL ");
        sql.append("    ,TEISEIGO_CREDIT_NO ");
        sql.append("    ,TEISEIGO_SHONIN_NO ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // #3760 URIB012580 2017/3/09 N.katou(S)
        // 2017.01.20 J.Endo #3468 add (S)
//        sql.append("    ,KINSYU_VL) ");
        sql.append("    ,KINSYU_VL ");
        // 2017.01.20 J.Endo #3468 add (E)
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("    ,POINT_ISSUDE_DT) ");
        sql.append("    ,POINT_ISSUDE_DT ");
        sql.append("    ,SYUUKIN_VL ");
        sql.append("    ,CYOUKA_VL ");
        sql.append("    ,TEISEIGO_CYOUKA_VL ");
        sql.append("    ) ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        // #3760 URIB012580 2017/3/09 N.katou(E)
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TRAN_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,CREDIT_NO ");
        sql.append("    ,SYONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,NULL ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,NULL ");
        sql.append("    ,MERCHANT_CODE ");
        sql.append("    ,NULL ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ,KINGAKU_VL ");
        // 2016.09.13 k.Hyo add (S)
        //sql.append("    ,0 ");
        sql.append("    ,KINGAKU_VL ");
        // 2016.09.13 k.Hyo add (E)
        sql.append("    ,0 ");
        sql.append("    ,null ");
        sql.append("    ,null ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2017.01.20 J.Endo #3468 add (S)
        sql.append("    ,KINSYU_VL ");
        // 2017.01.20 J.Endo #3468 add (E)
        // #3760 URIB012580 2017/3/09 N.katou(S)
        sql.append("    ,POINT_ISSUDE_DT ");
        // #3760 URIB012580 2017/3/09 N.katou(E)
        // 2017/08/31 VINX N.Katou #5840 (S)
        sql.append("    ,SYUUKIN_VL ");
        sql.append("    ,CYOUKA_VL ");
        sql.append("    ,CYOUKA_VL TEISEIGO_CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("FROM ");
        sql.append("    DT_RECEIPT_TORIHIKI_SEISAN ");
        sql.append("WHERE ");
        sql.append("    DATA_SAKUSEI_DT = ? ");

        return sql.toString();
    }

    /**
     * 店別精算状況データ更新用SQLを取得する
     *
     * @return 店別精算状況データ更新用SQL
     */
    private String getErrBumonSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE DT_TEN_SEISAN_STATE seisanstate SET ");
        sql.append("     seisanstate.RECEIPT_SEISAN_STATE_FG = 1");
        sql.append("    , seisanstate.UPDATE_USER_ID = ? ");
        sql.append("    ,  seisanstate.UPDATE_TS = ? ");
        sql.append(" WHERE ");
        // #6715 MOD 2022.12.23 SIEU.D (S)
        // sql.append("( ");
        // sql.append("    seisanstate.TENPO_CD ");
        // sql.append("    ,seisanstate.KEIJO_DT ");
        // sql.append(") = ( ");
        // sql.append(" SELECT");
        // sql.append("    receipt.TENPO_CD ");
        // sql.append("    ,receipt.KEIJO_DT ");
        // sql.append(" FROM ");
        // sql.append(" DT_RECEIPT_TORIHIKI_SEISAN receipt ");
        // sql.append(" WHERE ");
        // sql.append("    seisanstate.TENPO_CD = receipt.TENPO_CD AND ");
        // sql.append("    seisanstate.KEIJO_DT = receipt.KEIJO_DT AND ");
        // sql.append("    receipt.DATA_SAKUSEI_DT = ?");
        // sql.append(" GROUP BY ");
        // sql.append("    receipt.TENPO_CD ");
        // sql.append("    ,receipt.KEIJO_DT ");
        // sql.append(" )");

         sql.append(" EXISTS ( ");
         sql.append("     SELECT '1' ");
         sql.append("     FROM DT_RECEIPT_TORIHIKI_SEISAN RECEIPT ");
         sql.append("     WHERE SEISANSTATE.TENPO_CD = RECEIPT.TENPO_CD ");
         sql.append("          AND SEISANSTATE.KEIJO_DT = RECEIPT.KEIJO_DT ");
         sql.append("          AND SEISANSTATE.COMP_CD = RECEIPT.COMP_CD ");
         sql.append("          AND RECEIPT.DATA_SAKUSEI_DT = ?");
         sql.append(" )");
        // #6715 MOD 2022.12.23 SIEU.D (E)

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
