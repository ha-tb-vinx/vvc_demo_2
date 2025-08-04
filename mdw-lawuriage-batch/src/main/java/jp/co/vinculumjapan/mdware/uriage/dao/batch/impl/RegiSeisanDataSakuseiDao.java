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
 * <p>タイトル: RegiSeisanDataSakuseiDao.java クラス</p>
 * <p>説明　: 精算データ作成処理（レジ別取引精算）</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p> 
 * @Version 1.00 (2016/05/13) S_MDware-G_FIVIマート様開発 VINX k.Hyo
 * @Version 1.01 (2017/06/15) FIVI対応 VINX X.Liu
 *
 */
public class RegiSeisanDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** バッチ処理名 */
    private static final String DAO_NAME = "精算データ作成処理（レジ別取引精算）";
    /** 登録先テーブル名称(レジ別取引精算テーブル) */
    private static final String DT_REGI_TORIHIKI_SEISAN_TABLE_NAME = "レジ別取引ワーク精算データ";
    /** 登録先テーブル名称(レジ別取引精算エラーテーブル) */
    private static final String DT_ERR_REGI_TORIHIKI_SEISAN_TABLE_NAME = "レジ別取引精算ワークエラーデータ";
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

            // レジ別取引精算データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getRegiTorihikiSeisanInsertSql());
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
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_REGI_TORIHIKI_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_SEISAN_TABLE_NAME + "の追加を終了します。");
     

            // レジ別取引精算エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_REGI_TORIHIKI_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrRegiTorihikiSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, BATCH_DT);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_REGI_TORIHIKI_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_REGI_TORIHIKI_SEISAN_TABLE_NAME + "の追加を終了します。");
            
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
     * レジ別取引精算データ登録用SQLを取得する
     * 
     * @return レジ別取引精算データ登録用SQL
     */
    private String getRegiTorihikiSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();
        
        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_REGI_TORIHIKI_SEISAN DRTS ");
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
        sql.append("      WK_YUKO_REGI_TORIHIKI_SEISAN ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ? ");
        sql.append("      AND (ERR_KB = ? OR ERR_KB = ? )");
        sql.append("  ) WYRTS ");
        sql.append("    ON ( ");
        sql.append("      DRTS.COMP_CD = WYRTS.COMP_CD ");
        sql.append("      AND DRTS.KEIJO_DT = WYRTS.KEIJO_DT ");
        sql.append("      AND DRTS.TENPO_CD = WYRTS.TENPO_CD ");
        sql.append("      AND DRTS.TORIHIKI_CD = WYRTS.TORIHIKI_CD ");
        sql.append("      AND DRTS.SHIHARAI_SYUBETSU_CD = WYRTS.SHIHARAI_SYUBETSU_CD ");
        sql.append("      AND DRTS.SHIHARAI_SYUBETSU_SUB_CD = WYRTS.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  KYAKU_QT = ( ");
        sql.append("    CASE ");
        sql.append("      WHEN DRTS.TORIHIKI_CD IN ('0001', '0002') ");
        sql.append("      THEN NVL(DRTS.KYAKU_QT,0) + NVL(WYRTS.KYAKU_QT,0) ");
        sql.append("      ELSE WYRTS.KYAKU_QT ");
        sql.append("      END");
        sql.append("  ) ");
        sql.append("  , TEN_KAISUU_QT = ( ");
        sql.append("    CASE ");
        sql.append("      WHEN DRTS.TORIHIKI_CD IN ('0001', '0002') ");
        sql.append("      THEN NVL(DRTS.TEN_KAISUU_QT,0) + NVL(WYRTS.TEN_KAISUU_QT,0) ");
        sql.append("      ELSE WYRTS.TEN_KAISUU_QT ");
        sql.append("      END");
        sql.append("  ) ");
        sql.append("  , KINGAKU_VL = ( ");
        sql.append("    CASE ");
        sql.append("      WHEN DRTS.TORIHIKI_CD IN ('0001', '0002') ");
        sql.append("      THEN NVL(DRTS.KINGAKU_VL,0) + NVL(WYRTS.KINGAKU_VL,0) ");
        sql.append("      ELSE WYRTS.KINGAKU_VL ");
        sql.append("      END");
        sql.append("  ) ");
        sql.append("  , NEBIKI_VL = ( ");
        sql.append("    CASE ");
        sql.append("      WHEN DRTS.TORIHIKI_CD IN ('0001', '0002') ");
        sql.append("      THEN NVL(DRTS.NEBIKI_VL,0) + NVL(WYRTS.NEBIKI_VL,0) ");
        sql.append("      ELSE WYRTS.NEBIKI_VL ");
        sql.append("      END");
        sql.append("  ) ");
        sql.append("  , UPDATE_USER_ID = WYRTS.UPDATE_USER_ID");
        sql.append("  , UPDATE_TS = WYRTS.UPDATE_TS");
        sql.append("  , DATA_SAKUSEI_DT = WYRTS.DATA_SAKUSEI_DT WHEN NOT MATCHED THEN ");
//        sql.append("INSERT /*+ APPEND */ INTO DT_REGI_TORIHIKI_SEISAN( ");
        sql.append("INSERT ( ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
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
//        sql.append("    WK_YUKO_REGI_TORIHIKI_SEISAN ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ( ");
//        sql.append("        ERR_KB = ? OR ");
//        sql.append("        ERR_KB = ? ");
//        sql.append("    ) ");
        sql.append("  VALUES ( ");
        sql.append("    WYRTS.COMP_CD");
        sql.append("    , WYRTS.KEIJO_DT");
        sql.append("    , WYRTS.TENPO_CD");
        sql.append("    , WYRTS.TORIHIKI_CD");
        sql.append("    , WYRTS.KYAKU_QT");
        sql.append("    , WYRTS.TEN_KAISUU_QT");
        sql.append("    , WYRTS.KINGAKU_VL");
        sql.append("    , WYRTS.NEBIKI_VL");
        sql.append("    , WYRTS.INSERT_USER_ID");
        sql.append("    , WYRTS.INSERT_TS");
        sql.append("    , WYRTS.UPDATE_USER_ID");
        sql.append("    , WYRTS.UPDATE_TS");
        sql.append("    , WYRTS.SHIHARAI_SYUBETSU_CD");
        sql.append("    , WYRTS.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    , WYRTS.DATA_SAKUSEI_DT");
        sql.append("  )   ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        return sql.toString();
    }

    /**
     * レジ別取引精算エラーデータ登録用SQLを取得する
     * 
     * @return レジ別取引精算エラーデータ登録用SQL
     */
    private String getErrRegiTorihikiSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_REGI_TORIHIKI_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
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
        sql.append("    WK_YUKO_REGI_TORIHIKI_SEISAN ");
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
