package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.ErrorKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: SekininsyaSeisanCrecDataSakuseiDao クラス</p>
 * <p>説明　: 精算データ作成処理(責任者精算Crec)</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.11.14) VINX J.Endo FIVI対応 新規作成
 * @Version 1.01 (2017.06.14) VINX X.Liu #5399 FIVI対応
 * @Version 1.02 (2017.08.29) VINX N.Katou #5840 FIVI対応
 */
public class SekininsyaSeisanCrecDataSakuseiDao implements DaoIf {

    // バッチ処理名
    private static final String DAO_NAME = "精算Crecデータ作成処理";
    // 登録先テーブル名称(責任者精算テーブル)
    private static final String DT_SEKININSYA_SEISAN_TABLE_NAME = "責任者精算Crecデータ";
    // 登録先テーブル名称(責任者精算エラーテーブル)
    private static final String DT_ERR_SEKININSYA_SEISAN_TABLE_NAME = "責任者精算Crecエラーデータ";
    // ﾊﾞｯﾁID
    private static final String BATCH_ID = "URIB012810";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        String dbServerTime = "";

        try {
            // データ登録
            // 責任者精算Crecデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_SEKININSYA_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getSekininsyaSeisanInsertSql());
            preparedStatementExIns.setString(1, BATCH_DT);
            preparedStatementExIns.setString(2, BATCH_ID);
            preparedStatementExIns.setString(3, dbServerTime);
            preparedStatementExIns.setString(4, BATCH_ID);
            preparedStatementExIns.setString(5, dbServerTime);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(8, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_SEKININSYA_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_SEKININSYA_SEISAN_TABLE_NAME + "の追加を終了します。");

            // 責任者精算Crecエラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_SEKININSYA_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrSekininsyaSeisanInsertSql());
            preparedStatementExIns.setString(1, BATCH_DT);
            preparedStatementExIns.setString(2, BATCH_ID);
            preparedStatementExIns.setString(3, dbServerTime);
            preparedStatementExIns.setString(4, BATCH_ID);
            preparedStatementExIns.setString(5, dbServerTime);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_SEKININSYA_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_SEKININSYA_SEISAN_TABLE_NAME + "の追加を終了します。");
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
     * 責任者精算Crecデータ登録用SQLを取得する
     *
     * @return 責任者精算Crecデータ登録用SQL
     */
    private String getSekininsyaSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        //#5399 Add X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_SEKININSYA_SEISAN_C DSSC ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      COMP_CD");
        sql.append("      , KEIJO_DT");
        sql.append("      , TENPO_CD");
        sql.append("      , CHECKER_CD");
        sql.append("      , SHIHARAI_SYUBETSU_CD");
        sql.append("      , SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("      , SHIHARAI_VL");
        sql.append("      , ? AS DATA_SAKUSEI_DT ");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("      , RECEIPT_QT ");
        sql.append("      , SYUUKIN_VL ");
        sql.append("      , CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("    FROM");
        sql.append("      WK_YUKO_SEKININSYA_SEISAN_C ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ? ");
        sql.append("      AND (ERR_KB = ? OR ERR_KB = ? )");
        sql.append("  ) WYSSC ");
        sql.append("    ON ( ");
        sql.append("      DSSC.COMP_CD = WYSSC.COMP_CD ");
        sql.append("      AND DSSC.KEIJO_DT = WYSSC.KEIJO_DT ");
        sql.append("      AND DSSC.TENPO_CD = WYSSC.TENPO_CD ");
        sql.append("      AND DSSC.CHECKER_CD = WYSSC.CHECKER_CD ");
        sql.append("      AND DSSC.SHIHARAI_SYUBETSU_CD = WYSSC.SHIHARAI_SYUBETSU_CD ");
        sql.append("      AND DSSC.SHIHARAI_SYUBETSU_SUB_CD = WYSSC.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  SHIHARAI_VL = NVL(DSSC.SHIHARAI_VL,0) + NVL(WYSSC.SHIHARAI_VL,0)");
        sql.append("  , UPDATE_USER_ID = WYSSC.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WYSSC.UPDATE_TS");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
//        sql.append("  , DATA_SAKUSEI_DT = WYSSC.DATA_SAKUSEI_DT WHEN NOT MATCHED THEN ");
        sql.append("  , DATA_SAKUSEI_DT = WYSSC.DATA_SAKUSEI_DT ");
        sql.append("  , RECEIPT_QT =  NVL(DSSC.RECEIPT_QT,0) + NVL(WYSSC.RECEIPT_QT,0) ");
        sql.append("  , SYUUKIN_VL =  NVL(DSSC.SYUUKIN_VL,0) + NVL(WYSSC.SYUUKIN_VL,0) ");
        sql.append("  , CYOUKA_VL  =  NVL(DSSC.CYOUKA_VL,0) + NVL(WYSSC.CYOUKA_VL,0) ");
        sql.append("  WHEN NOT MATCHED THEN ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        //        sql.append("INSERT INTO DT_SEKININSYA_SEISAN_C ( ");
        sql.append("INSERT ( ");
        //#5399 Add X.Liu 2017.06.14 (E)
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,DATA_SAKUSEI_DT ");
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
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("SELECT ");
//        sql.append("    COMP_CD ");
//        sql.append("   ,KEIJO_DT ");
//        sql.append("   ,TENPO_CD ");
//        sql.append("   ,CHECKER_CD ");
//        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
//        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
//        sql.append("   ,SHIHARAI_VL ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_SEKININSYA_SEISAN_C ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ( ");
//        sql.append("        ERR_KB = ? OR ");
//        sql.append("        ERR_KB = ? ");
//        sql.append("    ) ");
        sql.append("  VALUES ( ");
        sql.append("    WYSSC.COMP_CD");
        sql.append("    , WYSSC.KEIJO_DT");
        sql.append("    , WYSSC.TENPO_CD");
        sql.append("    , WYSSC.CHECKER_CD");
        sql.append("    , WYSSC.SHIHARAI_SYUBETSU_CD");
        sql.append("    , WYSSC.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    , WYSSC.SHIHARAI_VL");
        sql.append("    , WYSSC.DATA_SAKUSEI_DT");
        sql.append("    , WYSSC.INSERT_USER_ID");
        sql.append("    , WYSSC.INSERT_TS");
        sql.append("    , WYSSC.UPDATE_USER_ID");
        sql.append("    , WYSSC.UPDATE_TS");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("    , WYSSC.RECEIPT_QT");
        sql.append("    , WYSSC.SYUUKIN_VL");
        sql.append("    , WYSSC.CYOUKA_VL");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("  )  ");
        //#5399 Mod X.Liu 2017.06.14 (E)

        return sql.toString();
    }

    /**
     * 責任者精算Crecエラーデータ登録用SQLを取得する
     *
     * @return 責任者精算Crecエラーデータ登録用SQL
     */
    private String getErrSekininsyaSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO DT_ERR_SEKININSYA_SEISAN_C ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,DATA_SAKUSEI_DT ");
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
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("   ,RECEIPT_QT ");
        sql.append("   ,SYUUKIN_VL ");
        sql.append("   ,CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("FROM ");
        sql.append("    WK_YUKO_SEKININSYA_SEISAN_C ");
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

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new SekininsyaSeisanCrecDataSakuseiDao();
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
