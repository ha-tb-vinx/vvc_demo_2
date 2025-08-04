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
 * <p>タイトル: TenkenDataSakuseiDao.java クラス</p>
 * <p>説明　: 点検データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.27) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2013.10.28) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №054
 * @Version 3.03 (2014.01.31) Y.Tominaga 結合ﾃｽﾄNo.0112 POS時間帯取込処理改善 対応
 * @Version 3.04 (2016.12.27) T.Kamei FIVI対応 #3403 単品点検処理機能の廃止
 * @Version 3.05 (2021.09.14) SIEU.D #6339
 * @Version 3.06 (2023.10.18) TRI.NH #19142 MKV対応
 *
 */
public class TenkenDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "点検データ作成処理";
    // #3403 2016.12.27 T.Kamei DEL (S)
    ///** 登録先テーブル名称(単品点検テーブル) */
    //private static final String DT_TANPIN_TENKEN_TABLE_NAME = "単品点検データ";
    // #3403 2016.12.27 T.Kamei DEL (E)
    /** 登録先テーブル名称(部門点検テーブル) */
    private static final String DT_BUMON_TENKEN_TABLE_NAME = "部門点検データ";
    /** 登録先テーブル名称(レジ別取引点検テーブル) */
    private static final String DT_REGI_TORIHIKI_TENKEN_TABLE_NAME = "レジ別取引点検データ";
    // #3403 2016.12.27 T.Kamei DEL (S)
    ///** 登録先テーブル名称(単品点検エラーテーブル) */
    //private static final String DT_ERR_TANPIN_TENKEN_TABLE_NAME = "単品点検エラーデータ";
    // #3403 2016.12.27 T.Kamei DEL (E)
    /** 登録先テーブル名称(部門点検エラーテーブル) */
    private static final String DT_ERR_BUMON_TENKEN_TABLE_NAME = "部門点検エラーデータ";
    /** 登録先テーブル名称(レジ別取引点検エラーテーブル) */
    private static final String DT_ERR_REGI_TORIHIKI_TENKEN_TABLE_NAME = "レジ別取引点検エラーデータ";

    // #3403 2016.12.27 T.Kamei DEL (S)
    ///** 登録先テーブル名称(単品点検累積テーブル) */
    //private static final String DT_TANPIN_TENKEN_TABLE_NAME_RUI = "単品点検データ";
    // #3403 2016.12.27 T.Kamei DEL (E)
    /** 登録先テーブル名称(部門点検累積テーブル) */
    private static final String DT_BUMON_TENKEN_TABLE_NAME_RUI = "部門点検データ";
    /** 登録先テーブル名称(レジ別取引点検累積テーブル) */
    private static final String DT_REGI_TORIHIKI_TENKEN_TABLE_NAME_RUI = "レジ別取引点検データ";

    // #3403 2016.12.27 T.Kamei DEL (S)
    ///** 削除SQL文(単品点検テーブル) */
    //private static final String DT_TANPIN_TENKEN_DEL_SQL = "TRUNCATE TABLE DT_TANPIN_TENKEN";
    // #3403 2016.12.27 T.Kamei DEL (E)
    /** 削除SQL文(部門点検テーブル) */
    private static final String DT_BUMON_TENKEN_DEL_SQL = "TRUNCATE TABLE DT_BUMON_TENKEN";
    /** 削除SQL文(レジ別取引点検テーブル) */
    private static final String DT_REGI_TORIHIKI_TENKEN_DEL_SQL = "TRUNCATE TABLE DT_REGI_TORIHIKI_TENKEN";
//    /** 削除SQL文(単品点検エラーテーブル) */
//    private static final String DT_ERR_TANPIN_TENKEN_DEL_SQL = "TRUNCATE TABLE DT_ERR_TANPIN_TENKEN";
//    /** 削除SQL文(部門点検エラーテーブル) */
//    private static final String DT_ERR_BUMON_TENKEN_DEL_SQL = "TRUNCATE TABLE DT_ERR_BUMON_TENKEN";
//    /** 削除SQL文(レジ別取引点検エラーテーブル) */
//    private static final String DT_ERR_REGI_TORIHIKI_TENKEN_DEL_SQL = "TRUNCATE TABLE DT_ERR_REGI_TORIHIKI_TENKEN";

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
        String dbServerTime = "";

        try {

            // ワークテーブルのデータを削除する
            // #3403 2016.12.27 T.Kamei DEL (S)
            // 単品点検データ削除
            //preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_TANPIN_TENKEN_DEL_SQL);
            //preparedStatementDelete.execute();
            // #3403 2016.12.27 T.Kamei DEL (E)
//            // 単品点検エラーデータ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_ERR_TANPIN_TENKEN_DEL_SQL);
//            preparedStatementDelete.execute();
            // 部門点検データ削除
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_BUMON_TENKEN_DEL_SQL);
            preparedStatementDelete.execute();
//            // 部門点検エラーデータ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_ERR_BUMON_TENKEN_DEL_SQL);
//            preparedStatementDelete.execute();
            // レジ別取引点検データ削除
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_REGI_TORIHIKI_TENKEN_DEL_SQL);
            preparedStatementDelete.execute();
//            // レジ別取引点検エラーデータ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_ERR_REGI_TORIHIKI_TENKEN_DEL_SQL);
//            preparedStatementDelete.execute();

            // データ登録

            // #3403 2016.12.27 T.Kamei DEL (S)
            // 単品点検データの追加
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + DT_TANPIN_TENKEN_TABLE_NAME + "の追加を開始します。");

            //dbServerTime = FiResorceUtility.getDBServerTime();
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getTanpinTenkenInsertSql());
            //preparedStatementExIns.setString(1, userId);
            //preparedStatementExIns.setString(2, dbServerTime);
            //preparedStatementExIns.setString(3, userId);
            //preparedStatementExIns.setString(4, dbServerTime);
            //preparedStatementExIns.setString(5, COMP_CD);
            //preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            //preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

            //insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_TANPIN_TENKEN_TABLE_NAME + "を追加しました。");
            //invoker.infoLog(strUserId + "　：　" + DT_TANPIN_TENKEN_TABLE_NAME + "の追加を終了します。");

            // 単品点検エラーデータの追加
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + DT_ERR_TANPIN_TENKEN_TABLE_NAME + "の追加を開始します。");

            //dbServerTime = FiResorceUtility.getDBServerTime();
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrTanpinTenkenInsertSql());
            //preparedStatementExIns.setString(1, userId);
            //preparedStatementExIns.setString(2, dbServerTime);
            //preparedStatementExIns.setString(3, userId);
            //preparedStatementExIns.setString(4, dbServerTime);
            //preparedStatementExIns.setString(5, COMP_CD);
            //preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());

            //insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_TANPIN_TENKEN_TABLE_NAME + "を追加しました。");
            //invoker.infoLog(strUserId + "　：　" + DT_ERR_TANPIN_TENKEN_TABLE_NAME + "の追加を終了します。");
            // #3403 2016.12.27 T.Kamei DEL (E)

            // 部門点検データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_BUMON_TENKEN_TABLE_NAME + "の追加を開始します。");
            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getBumonTenkenInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_BUMON_TENKEN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_BUMON_TENKEN_TABLE_NAME + "の追加を終了します。");

            // 部門点検エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_BUMON_TENKEN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrBumonTenkenInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_BUMON_TENKEN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_BUMON_TENKEN_TABLE_NAME + "の追加を終了します。");

            // レジ別取引点検データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_TENKEN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getRegiTorihikiTenkenInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_REGI_TORIHIKI_TENKEN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_TENKEN_TABLE_NAME + "の追加を終了します。");

            // レジ別取引点検エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_REGI_TORIHIKI_TENKEN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrRegiTorihikiTenkenInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_REGI_TORIHIKI_TENKEN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_REGI_TORIHIKI_TENKEN_TABLE_NAME + "の追加を終了します。");


            // #3403 2016.12.27 T.Kamei DEL (S)
            // 単品点検累積データの追加
            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + DT_TANPIN_TENKEN_TABLE_NAME_RUI + "の追加を開始します。");

            //dbServerTime = FiResorceUtility.getDBServerTime();
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(getTanpinTenkenRuiInsertSql());
            //preparedStatementExIns.setString(1, userId);
            //preparedStatementExIns.setString(2, dbServerTime);
            //preparedStatementExIns.setString(3, userId);
            //preparedStatementExIns.setString(4, dbServerTime);
            //preparedStatementExIns.setString(5, COMP_CD);
            //preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            //preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

            //insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            //invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_TANPIN_TENKEN_TABLE_NAME_RUI + "を追加しました。");
            //invoker.infoLog(strUserId + "　：　" + DT_TANPIN_TENKEN_TABLE_NAME_RUI + "の追加を終了します。");
            // #3403 2016.12.27 T.Kamei DEL (E)

            // 部門点検累積データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_BUMON_TENKEN_TABLE_NAME_RUI + "の追加を開始します。");
            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getBumonTenkenRuiInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_BUMON_TENKEN_TABLE_NAME_RUI + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_BUMON_TENKEN_TABLE_NAME_RUI + "の追加を終了します。");

            // レジ別取引点検累積データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_TENKEN_TABLE_NAME_RUI + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getRegiTorihikiTenkenRuiInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_REGI_TORIHIKI_TENKEN_TABLE_NAME_RUI + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_TENKEN_TABLE_NAME_RUI + "の追加を終了します。");

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

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    // #3403 2016.12.27 T.Kamei DEL (S)
//    /**
//     * 単品点検データ登録用SQLを取得する
//     *
//     * @return 単品点検データ登録用SQL
//     */
//    private String getTanpinTenkenInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_TANPIN_TENKEN( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TANPIN_CD ");
//        sql.append("    ,TIME_NO ");
//        sql.append("    ,URIAGE_QT ");
//        sql.append("    ,URIAGE_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,ARARI_VL ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,BUNRUI2_CD ");
//        sql.append("    ,BUNRUI5_CD ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
//        sql.append("SELECT ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TANPIN_CD ");
//        sql.append("    ,TIME_NO ");
//        sql.append("    ,URIAGE_QT ");
//        sql.append("    ,URIAGE_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,ARARI_VL ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,BUNRUI2_CD ");
//        sql.append("    ,BUNRUI5_CD ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_TANPIN_TENKEN ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ( ");
//        sql.append("        ERR_KB = ? OR ");
//        sql.append("        ERR_KB = ? ");
//        sql.append("    ) ");
//
//        return sql.toString();
//    }
//
//    /**
//     * 単品点検エラーデータ登録用SQLを取得する
//     *
//     * @return 単品点検エラーデータ登録用SQL
//     */
//    private String getErrTanpinTenkenInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_TANPIN_TENKEN( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TANPIN_CD ");
//        sql.append("    ,TIME_NO ");
//        sql.append("    ,URIAGE_QT ");
//        sql.append("    ,URIAGE_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,ARARI_VL ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,BUNRUI2_CD ");
//        sql.append("    ,BUNRUI5_CD ");
//        sql.append("    ,ERR_KB ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
//        sql.append("SELECT ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TANPIN_CD ");
//        sql.append("    ,TIME_NO ");
//        sql.append("    ,URIAGE_QT ");
//        sql.append("    ,URIAGE_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,ARARI_VL ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,BUNRUI2_CD ");
//        sql.append("    ,BUNRUI5_CD ");
//        sql.append("    ,ERR_KB ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_TANPIN_TENKEN ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ERR_KB != ? ");
//
//        return sql.toString();
//    }
    // #3403 2016.12.27 T.Kamei DEL (E)

    /**
     * 部門点検データ登録用SQLを取得する
     *
     * @return 部門点検データ登録用SQL
     */
    private String getBumonTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_BUMON_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("    ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("    ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_YUKO_BUMON_TENKEN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ( ");
        sql.append("        ERR_KB = ? OR ");
        sql.append("        ERR_KB = ? ");
        sql.append("    ) ");

        return sql.toString();
    }

    /**
     * 部門点検エラーデータ登録用SQLを取得する
     *
     * @return 部門点検エラーデータ登録用SQL
     */
    private String getErrBumonTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_BUMON_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_YUKO_BUMON_TENKEN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ERR_KB != ? ");

        return sql.toString();
    }

    /**
     * レジ別取引点検データ登録用SQLを取得する
     *
     * @return レジ別取引点検データ登録用SQL
     */
    private String getRegiTorihikiTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_REGI_TORIHIKI_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_YUKO_REGI_TORIHIKI_TENKEN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ( ");
        sql.append("        ERR_KB = ? OR ");
        sql.append("        ERR_KB = ? ");
        sql.append("    ) ");

        return sql.toString();
    }

    /**
     * レジ別取引点検エラーデータ登録用SQLを取得する
     *
     * @return レジ別取引点検エラーデータ登録用SQL
     */
    private String getErrRegiTorihikiTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_REGI_TORIHIKI_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
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
        sql.append("FROM ");
        sql.append("    WK_YUKO_REGI_TORIHIKI_TENKEN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ERR_KB != ? ");

        return sql.toString();
    }

    // #3403 2016.12.27 T.Kamei DEL (S)
//   /**
//     * 単品点検累積データ登録用SQLを取得する
//     *
//     * @return 単品点検累積データ登録用SQL
//     */
//    private String getTanpinTenkenRuiInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_TANPIN_TENKEN_RUI( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TANPIN_CD ");
//        sql.append("    ,TIME_NO ");
//        sql.append("    ,URIAGE_QT ");
//        sql.append("    ,URIAGE_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,ARARI_VL ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,BUNRUI2_CD ");
//        sql.append("    ,BUNRUI5_CD ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
//        sql.append("SELECT ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TANPIN_CD ");
//        sql.append("    ,TIME_NO ");
//        sql.append("    ,URIAGE_QT ");
//        sql.append("    ,URIAGE_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,ARARI_VL ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,BUNRUI2_CD ");
//        sql.append("    ,BUNRUI5_CD ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_TANPIN_TENKEN ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ( ");
//        sql.append("        ERR_KB = ? OR ");
//        sql.append("        ERR_KB = ? ");
//        sql.append("    ) ");
//
//        return sql.toString();
//    }
    // #3403 2016.12.27 T.Kamei DEL (E)

    /**
     * 部門点検累積データ登録用SQLを取得する
     *
     * @return 部門点検累積データ登録用SQL
     */
    private String getBumonTenkenRuiInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_BUMON_TENKEN_RUI( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        // #19142 ADD 2023/10/18 TRI.NH (S) 
        sql.append("    ,ARARI_VL ");
        // #19142 ADD 2023/10/18 TRI.NH (E) 
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        // #19142 ADD 2023/10/18 TRI.NH (S) 
        sql.append("    ,ARARI_VL ");
        // #19142 ADD 2023/10/18 TRI.NH (E) 
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_YUKO_BUMON_TENKEN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ( ");
        sql.append("        ERR_KB = ? OR ");
        sql.append("        ERR_KB = ? ");
        sql.append("    ) ");

        return sql.toString();
    }

    /**
     * レジ別取引点検累積データ登録用SQLを取得する
     *
     * @return レジ別取引点検累積データ登録用SQL
     */
    private String getRegiTorihikiTenkenRuiInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_REGI_TORIHIKI_TENKEN_RUI( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_YUKO_REGI_TORIHIKI_TENKEN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ( ");
        sql.append("        ERR_KB = ? OR ");
        sql.append("        ERR_KB = ? ");
        sql.append("    ) ");

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
            DaoIf dao = new TenkenDataSakuseiDao();
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
