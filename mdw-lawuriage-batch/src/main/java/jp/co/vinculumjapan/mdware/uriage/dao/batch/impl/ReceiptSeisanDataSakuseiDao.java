package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.ErrorKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: ReceiptSeisanDataSakuseiDao.java クラス</p>
 * <p>説明　: 精算データ作成処理（レシート別取引精算）</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2016.05.20) H.Yaguma FIVI対応
 * @Version 3.01 (2016.09.29) k.Hyo FIVI対応
 * @Version 3.02 (2017.01.19) J.Endo FIVI対応 Cレコードが複数レコード発生する対応 #3468
 * @Version 3.03 (2017.03.09) N.Katou #3760
 * @Version 3.04 (2017.04.11) X.Liu #4585
 * @Version 3.05 (2017.06.21) X.Liu #5421
 * @Version 3.06 (2017.08.31) N.Katou #5840
 *
 */
public class ReceiptSeisanDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "精算データ作成処理（レシート別取引精算）";
    /** 登録先テーブル名称(店別レシート別精算データ) */
    private static final String DT_RECEIPT_TORIHIKI_SEISAN = "レシート別取引精算データ";
    /** 登録先テーブル名称(店別精算状況データ) */
    private static final String DT_ERR_RECEIPT_TORIHIKI_SEISAN = "レシート別取引精算エラーデータ";

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
//        PreparedStatementEx preparedStatementDelete = null;

        int insertCount = 0;
        String dbServerTime = "";

        try {
            // データ登録

            // レシート別取引精算データ
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_RECEIPT_TORIHIKI_SEISAN + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getBumonSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, BATCH_DT);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(8, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_RECEIPT_TORIHIKI_SEISAN + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_RECEIPT_TORIHIKI_SEISAN + "の追加を終了します。");


            // レシート別取引精算エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_RECEIPT_TORIHIKI_SEISAN + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrBumonSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, BATCH_DT);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_RECEIPT_TORIHIKI_SEISAN + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_RECEIPT_TORIHIKI_SEISAN + "の追加を終了します。");

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

        sql.append("INSERT /*+ APPEND */ INTO DT_RECEIPT_TORIHIKI_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TRAN_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,CREDIT_NO ");
        sql.append("    ,SYONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,MERCHANT_CODE ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        // #3760 URIB012570 2017/3/09 N.katou(S)
        // #3468 2017.01.19 J.Endo Add (S)
//        sql.append("    ,KINSYU_VL) ");
        sql.append("    ,KINSYU_VL ");
        // #3468 2017.01.19 J.Endo Add (E)
        // 2017/08/31 VINX N.Katou #5840対応 (S)
//        sql.append("    ,POINT_ISSUDE_DT) ");
        sql.append("    ,POINT_ISSUDE_DT ");
        sql.append("    ,SYUUKIN_VL ");
        sql.append("    ,CYOUKA_VL) ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        // #3760 URIB012570 2017/3/09 N.katou(E)
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TRAN_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,KINGAKU_VL ");
        // 2016.09.20 k.Hyo (S)
        //sql.append("    ,CREDIT_NO ");
        sql.append("    ,CASE ");
        sql.append("     WHEN CREDIT_NO IS NULL THEN ' ' ");
        sql.append("     ELSE CREDIT_NO ");
        sql.append("     END ");
        // 2016.09.20 k.Hyo (E)
        sql.append("    ,SYONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,MERCHANT_CODE ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // #3468 2017.01.19 J.Endo Add (S)
        sql.append("    ,KINSYU_VL ");
        // #3468 2017.01.19 J.Endo Add (E)
        // #4585 Mod X.Liu 2017.04.11 (S)
        // #3760 URIB012570 2017/3/09 N.katou(S)
//        sql.append("    ,POINT_ISSUDE_DT ");
        sql.append("    ,NVL(POINT_ISSUDE_DT,' ') ");
        // #3760 URIB012570 2017/3/09 N.katou(E)
        // #4585 Mod X.Liu 2017.04.11 (E)
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("    ,SYUUKIN_VL ");
        sql.append("    ,CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("FROM ");
        sql.append("    WK_YUKO_RECEIPT_TORI_SEISAN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ( ");
        sql.append("        ERR_KB = ? OR ");
        sql.append("        ERR_KB = ? ");
        sql.append("    ) ");

        return sql.toString();
    }

    /**
     * レシート別取引精算エラーデータ登録用SQLを取得する
     *
     * @return レシート別取引精算エラーデータ登録用SQL
     */
    private String getErrBumonSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_RECEIPT_TORIHIKI_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TRAN_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,CREDIT_NO ");
        sql.append("    ,SYONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,MERCHANT_CODE ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        // #3760 URIB012570 2017/3/09 N.katou(S)
        // #3468 2017.01.19 J.Endo Add (S)
//        sql.append("    ,KINSYU_VL) ");
        sql.append("    ,KINSYU_VL ");
        // #3468 2017.01.19 J.Endo Add (E)
        // 2017/08/31 VINX N.Katou #5840対応 (S)
//      sql.append("    ,POINT_ISSUDE_DT) ");
        sql.append("    ,POINT_ISSUDE_DT ");
        sql.append("    ,SYUUKIN_VL ");
        sql.append("    ,CYOUKA_VL) ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        // #3760 URIB012570 2017/3/09 N.katou(E)
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TRAN_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,KINGAKU_VL ");
        // 2016.09.20 k.Hyo (S)
        //sql.append("    ,CREDIT_NO ");
        sql.append("    ,CASE ");
        sql.append("     WHEN CREDIT_NO IS NULL THEN ' ' ");
        sql.append("     ELSE CREDIT_NO ");
        sql.append("     END ");
        // 2016.09.20 k.Hyo (E)
        sql.append("    ,SYONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,MERCHANT_CODE ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ,ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // #3468 2017.01.19 J.Endo Add (S)
        sql.append("    ,KINSYU_VL ");
        // #3468 2017.01.19 J.Endo Add (E)
        // #3760 URIB012570 2017/3/09 N.katou(S)
        sql.append("    ,POINT_ISSUDE_DT ");
        // #3760 URIB012570 2017/3/09 N.katou(E)        
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("    ,SYUUKIN_VL ");
        sql.append("    ,CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("FROM ");
        sql.append("    WK_YUKO_RECEIPT_TORI_SEISAN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ERR_KB != ? ");

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
