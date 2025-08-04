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
 * <p>タイトル: RegiSeisanErecDataSakuseiDao.java クラス</p>
 * <p>説明　: 精算データ作成処理（レジ別取引精算Erec）</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p> 
 * @Version 1.00 (2017.04.27) VINX J.Endo FIVI対応(#4770) 新規作成
 * @Version 1.01 (2017.06.14) VINX X.Liu FIVI対応(#5399)
 *
 */
public class RegiSeisanErecDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** バッチ処理名 */
    private static final String DAO_NAME = "精算データ作成処理（レジ別取引精算Erec）";
    /** 登録先テーブル名称(レジ別取引精算テーブルErec) */
    private static final String DT_REGI_TORIHIKI_SEISAN_E_TABLE_NAME = "レジ別取引ワーク精算データErec";
    /** 登録先テーブル名称(レジ別取引精算エラーテーブルErec) */
    private static final String DT_ERR_REGI_TORIHIKI_SEISAN_E_TABLE_NAME = "レジ別取引精算ワークエラーデータErec";
    // システムコントロールよりバッチ日付取得
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

        int insertCount = 0;
        String dbServerTime = "";

        try {

            // レジ別取引精算データErecの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_SEISAN_E_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getRegiTorihikiSeisanErecInsertSql());
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
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_REGI_TORIHIKI_SEISAN_E_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_SEISAN_E_TABLE_NAME + "の追加を終了します。");
     

            // レジ別取引精算エラーデータErecの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_REGI_TORIHIKI_SEISAN_E_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrRegiTorihikiSeisanErecInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, BATCH_DT);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_REGI_TORIHIKI_SEISAN_E_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_REGI_TORIHIKI_SEISAN_E_TABLE_NAME + "の追加を終了します。");
            
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
     * レジ別取引精算データErec登録用SQLを取得する
     * 
     * @return レジ別取引精算データErec登録用SQL
     */
    private String getRegiTorihikiSeisanErecInsertSql() {
        StringBuilder sql = new StringBuilder();

        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_REGI_TORIHIKI_SEISAN_E DRTSE ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      COMP_CD");
        sql.append("      , KEIJO_DT");
        sql.append("      , TENPO_CD");
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
        sql.append("      WK_YUKO_REGI_TORIHIKI_SEISAN_E ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ? ");
        sql.append("      AND (ERR_KB = ? OR ERR_KB = ? )");
        sql.append("  ) WYRTSE ");
        sql.append("    ON ( ");
        sql.append("      DRTSE.COMP_CD = WYRTSE.COMP_CD ");
        sql.append("      AND DRTSE.KEIJO_DT = WYRTSE.KEIJO_DT ");
        sql.append("      AND DRTSE.TENPO_CD = WYRTSE.TENPO_CD ");
        sql.append("      AND DRTSE.TORIHIKI_CD = WYRTSE.TORIHIKI_CD ");
        sql.append("      AND DRTSE.SHIHARAI_SYUBETSU_CD = WYRTSE.SHIHARAI_SYUBETSU_CD ");
        sql.append("      AND DRTSE.SHIHARAI_SYUBETSU_SUB_CD = WYRTSE.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  KYAKU_QT = NVL(DRTSE.KYAKU_QT, 0) + NVL(WYRTSE.KYAKU_QT, 0)");
        sql.append("  , TEN_KAISUU_QT = NVL(DRTSE.TEN_KAISUU_QT, 0) + NVL(WYRTSE.TEN_KAISUU_QT, 0)");
        sql.append("  , KINGAKU_VL = NVL(DRTSE.KINGAKU_VL, 0) + NVL(WYRTSE.KINGAKU_VL, 0)");
        sql.append("  , NEBIKI_VL = NVL(DRTSE.NEBIKI_VL, 0) + NVL(WYRTSE.NEBIKI_VL, 0)");
        sql.append("  , UPDATE_USER_ID = WYRTSE.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WYRTSE.UPDATE_TS");
        sql.append("  , DATA_SAKUSEI_DT = WYRTSE.DATA_SAKUSEI_DT WHEN NOT MATCHED THEN ");
//        sql.append("INSERT /*+ APPEND */ INTO DT_REGI_TORIHIKI_SEISAN_E ( ");
        sql.append("INSERT ( ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,TORIHIKI_CD ");
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
        sql.append("   ,DATA_SAKUSEI_DT) ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("SELECT ");
//        sql.append("    COMP_CD ");
//        sql.append("   ,KEIJO_DT ");
//        sql.append("   ,TENPO_CD ");
//        sql.append("   ,TORIHIKI_CD ");
//        sql.append("   ,KYAKU_QT ");
//        sql.append("   ,TEN_KAISUU_QT ");
//        sql.append("   ,KINGAKU_VL ");
//        sql.append("   ,NEBIKI_VL ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,? ");
//        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
//        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
//        sql.append("   ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_REGI_TORIHIKI_SEISAN_E ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ( ");
//        sql.append("        ERR_KB = ? OR ");
//        sql.append("        ERR_KB = ? ");
//        sql.append("    ) ");
        sql.append("  VALUES ( ");
        sql.append("    WYRTSE.COMP_CD");
        sql.append("    , WYRTSE.KEIJO_DT");
        sql.append("    , WYRTSE.TENPO_CD");
        sql.append("    , WYRTSE.TORIHIKI_CD");
        sql.append("    , WYRTSE.KYAKU_QT");
        sql.append("    , WYRTSE.TEN_KAISUU_QT");
        sql.append("    , WYRTSE.KINGAKU_VL");
        sql.append("    , WYRTSE.NEBIKI_VL");
        sql.append("    , WYRTSE.INSERT_USER_ID");
        sql.append("    , WYRTSE.INSERT_TS");
        sql.append("    , WYRTSE.UPDATE_USER_ID");
        sql.append("    , WYRTSE.UPDATE_TS");
        sql.append("    , WYRTSE.SHIHARAI_SYUBETSU_CD");
        sql.append("    , WYRTSE.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    , WYRTSE.DATA_SAKUSEI_DT");
        sql.append("  )  ");
        //#5399 Mod X.Liu 2017.06.14 (E)

        return sql.toString();
    }

    /**
     * レジ別取引精算エラーデータErec登録用SQLを取得する
     * 
     * @return レジ別取引精算エラーデータErec登録用SQL
     */
    private String getErrRegiTorihikiSeisanErecInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_REGI_TORIHIKI_SEISAN_E ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,TORIHIKI_CD ");
        sql.append("   ,KYAKU_QT ");
        sql.append("   ,TEN_KAISUU_QT ");
        sql.append("   ,KINGAKU_VL ");
        sql.append("   ,NEBIKI_VL ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,DATA_SAKUSEI_DT) ");
        sql.append("SELECT ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,TORIHIKI_CD ");
        sql.append("   ,KYAKU_QT ");
        sql.append("   ,TEN_KAISUU_QT ");
        sql.append("   ,KINGAKU_VL ");
        sql.append("   ,NEBIKI_VL ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,? ");
        sql.append("FROM ");
        sql.append("    WK_YUKO_REGI_TORIHIKI_SEISAN_E ");
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
            DaoIf dao = new SeisanDataSakuseiDao();
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
