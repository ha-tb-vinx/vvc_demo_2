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
 *
 * <p>タイトル: SekininsyaSeisanDataSakuseiDao.java クラス</p>
 * <p>説明　: 精算データ作成処理(責任者精算)</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00  (2016.05.11) monden S03対応
 * @Version 1.01  (2016.06.02) vinx.to #1513対応
 * @Version 1.02  (2017.06.14) X.Liu #5399対応
 *
 */
public class SekininsyaSeisanDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "精算データ作成処理";
    /** 登録先テーブル名称(責任者精算テーブル) */
    private static final String DT_SEKININSYA_SEISAN_TABLE_NAME = "責任者精算データ";
    /** 登録先テーブル名称(責任者精算エラーテーブル) */
    private static final String DT_ERR_SEKININSYA_SEISAN_TABLE_NAME = "責任者精算エラーデータ";

    // 2016/05/10 VINX #S03対応（S)
    /** ﾊﾞｯﾁID */
    private static final String BATCH_ID = "URIB012350";
    // 2016/05/10 VINX #S03対応（E)
    
    // 2016/06/02 VINX #S03対応（S)
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // 2016/06/02 VINX #S03対応（E)

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
            // 責任者精算データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_SEKININSYA_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getSekininsyaSeisanInsertSql());
            preparedStatementExIns.setString(1, BATCH_ID);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, BATCH_ID);
            preparedStatementExIns.setString(4, dbServerTime);
            // 20160602 vinx.to #1513対応  (S)
//            preparedStatementExIns.setString(5, dbServerTime.substring(0, 8));
            preparedStatementExIns.setString(5, BATCH_DT);
            // 20160602 vinx.to #1513対応  (E)
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(8, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_SEKININSYA_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_SEKININSYA_SEISAN_TABLE_NAME + "の追加を終了します。");

            // 責任者精算エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_SEKININSYA_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrSekininsyaSeisanInsertSql());
            preparedStatementExIns.setString(1, BATCH_ID);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, BATCH_ID);
            preparedStatementExIns.setString(4, dbServerTime);
            // 20160602 vinx.to #1513対応  (S)
//            preparedStatementExIns.setString(5, dbServerTime.substring(0, 8));
            preparedStatementExIns.setString(5, BATCH_DT);
            // 20160602 vinx.to #1513対応  (E)
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
     * 責任者精算データ登録用SQLを取得する
     *
     * @return 責任者精算データ登録用SQL
     */
    private String getSekininsyaSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_SEKININSYA_SEISAN DSS ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      COMP_CD");
        sql.append("      , KEIJO_DT");
        sql.append("      , TENPO_CD");
        sql.append("      , CHECKER_CD");
        sql.append("      , TORIHIKI_CD");
        sql.append("      , KYAKU_QT");
        sql.append("      , TEN_KAISUU_QT");
        sql.append("      , KINGAKU_VL");
        sql.append("      , NEBIKI_VL");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS");
        sql.append("      , SHIHARAI_SYUBETSU_CD");
        sql.append("      , SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("      , ? AS DATA_SAKUSEI_DT ");
        sql.append("    FROM");
        sql.append("      WK_YUKO_SEKININSYA_SEISAN ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ? ");
        sql.append("      AND (ERR_KB = ? OR ERR_KB = ? )");
        sql.append("  ) WYSS ");
        sql.append("    ON ( ");
        sql.append("      DSS.COMP_CD = WYSS.COMP_CD ");
        sql.append("      AND DSS.KEIJO_DT = WYSS.KEIJO_DT ");
        sql.append("      AND DSS.TENPO_CD = WYSS.TENPO_CD ");
        sql.append("      AND DSS.CHECKER_CD = WYSS.CHECKER_CD ");
        sql.append("      AND DSS.TORIHIKI_CD = WYSS.TORIHIKI_CD ");
        sql.append("      AND DSS.SHIHARAI_SYUBETSU_CD = WYSS.SHIHARAI_SYUBETSU_CD ");
        sql.append("      AND DSS.SHIHARAI_SYUBETSU_SUB_CD = WYSS.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  KYAKU_QT = NVL(DSS.KYAKU_QT,0) + NVL(WYSS.KYAKU_QT,0)");
        sql.append("  , TEN_KAISUU_QT = NVL(DSS.TEN_KAISUU_QT,0) + NVL(WYSS.TEN_KAISUU_QT,0)");
        sql.append("  , KINGAKU_VL = NVL(DSS.KINGAKU_VL,0) + NVL(WYSS.KINGAKU_VL,0)");
        sql.append("  , NEBIKI_VL = NVL(DSS.NEBIKI_VL,0) + NVL(WYSS.NEBIKI_VL,0)");
        sql.append("  , UPDATE_USER_ID = WYSS.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WYSS.UPDATE_TS");
        sql.append("  , DATA_SAKUSEI_DT = WYSS.DATA_SAKUSEI_DT WHEN NOT MATCHED THEN ");
//        sql.append("INSERT /*+ APPEND */ INTO DT_SEKININSYA_SEISAN( ");
        sql.append("INSERT ( ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,DATA_SAKUSEI_DT) ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("SELECT ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,CHECKER_CD ");
//        sql.append("    ,TORIHIKI_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,TEN_KAISUU_QT ");
//        sql.append("    ,KINGAKU_VL ");
//        sql.append("    ,NEBIKI_VL ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
//        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_SEKININSYA_SEISAN ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ( ");
//        sql.append("        ERR_KB = ? OR ");
//        sql.append("        ERR_KB = ? ");
//        sql.append("    ) ");
        sql.append(" VALUES ( ");
        sql.append("    WYSS.COMP_CD");
        sql.append("    , WYSS.KEIJO_DT");
        sql.append("    , WYSS.TENPO_CD");
        sql.append("    , WYSS.CHECKER_CD");
        sql.append("    , WYSS.TORIHIKI_CD");
        sql.append("    , WYSS.KYAKU_QT");
        sql.append("    , WYSS.TEN_KAISUU_QT");
        sql.append("    , WYSS.KINGAKU_VL");
        sql.append("    , WYSS.NEBIKI_VL");
        sql.append("    , WYSS.INSERT_USER_ID");
        sql.append("    , WYSS.INSERT_TS");
        sql.append("    , WYSS.UPDATE_USER_ID");
        sql.append("    , WYSS.UPDATE_TS");
        sql.append("    , WYSS.SHIHARAI_SYUBETSU_CD");
        sql.append("    , WYSS.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    , WYSS.DATA_SAKUSEI_DT");
        sql.append("  ) ");
        //#5399 Mod X.Liu 2017.06.14 (E)

        return sql.toString();
    }

    /**
     * 責任者精算エラーデータ登録用SQLを取得する
     *
     * @return 責任者精算エラーデータ登録用SQL
     */
    private String getErrSekininsyaSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_SEKININSYA_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,DATA_SAKUSEI_DT) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_YUKO_SEKININSYA_SEISAN ");
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
            DaoIf dao = new SekininsyaSeisanDataSakuseiDao();
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
