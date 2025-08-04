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
 * <p>タイトル: SeisanDataSakuseiDao.java クラス</p>
 * <p>説明　: 精算データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00  (2013.09.25) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2013.10.28) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №055
 * @Version 3.03 (2016.05.10) VINX S.Kashihara FIVI対応
 * @Version 3.03 (2016.08.05) VINX Y.Itaki FIVI対応 #1879
 *
 */
public class SeisanDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // 2016.05.10 VINX S.Kashihara FIVI対応 (E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "精算データ作成処理";
    /** 登録先テーブル名称(単品精算テーブル) */
    private static final String DT_TANPIN_SEISAN_TABLE_NAME = "単品精算データ";
    /** 登録先テーブル名称(単品精算エラーテーブル) */
    private static final String DT_ERR_TANPIN_SEISAN_TABLE_NAME = "単品精算エラーデータ";
    // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
//    /** 登録先テーブル名称(部門精算テーブル) */
//    private static final String DT_BUMON_SEISAN_TABLE_NAME = "部門精算データ";
//    /** 登録先テーブル名称(レジ別取引精算テーブル) */
//    private static final String DT_REGI_TORIHIKI_SEISAN_TABLE_NAME = "レジ別取引精算データ";
//    /** 登録先テーブル名称(責任者精算テーブル) */
//    private static final String DT_SEKININSYA_SEISAN_TABLE_NAME = "責任者精算データ";
//    /** 登録先テーブル名称(部門精算エラーテーブル) */
//    private static final String DT_ERR_BUMON_SEISAN_TABLE_NAME = "部門精算エラーデータ";
//    /** 登録先テーブル名称(レジ別取引精算エラーテーブル) */
//    private static final String DT_ERR_REGI_TORIHIKI_SEISAN_TABLE_NAME = "レジ別取引精算エラーデータ";
//    /** 登録先テーブル名称(責任者精算エラーテーブル) */
//    private static final String DT_ERR_SEKININSYA_SEISAN_TABLE_NAME = "責任者精算エラーデータ";
    // 2016.05.10 VINX S.Kashihara FIVI対応 (E)

//    /** 削除SQL文(単品精算テーブル) */
//    private static final String DT_TANPIN_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_TANPIN_SEISAN";
//    /** 削除SQL文(部門精算テーブル) */
//    private static final String DT_BUMON_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_BUMON_SEISAN";
//    /** 削除SQL文(レジ別取引精算テーブル) */
//    private static final String DT_REGI_TORIHIKI_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_REGI_TORIHIKI_SEISAN";
//    /** 削除SQL文(責任者精算テーブル) */
//    private static final String DT_SEKININSYA_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_SEKININSYA_SEISAN";
//    /** 削除SQL文(単品精算エラーテーブル) */
//    private static final String DT_ERR_TANPIN_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_ERR_TANPIN_SEISAN";
//    /** 削除SQL文(部門精算エラーテーブル) */
//    private static final String DT_ERR_BUMON_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_ERR_BUMON_SEISAN";
//    /** 削除SQL文(レジ別取引精算エラーテーブル) */
//    private static final String DT_ERR_REGI_TORIHIKI_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_ERR_REGI_TORIHIKI_SEISAN";
//    /** 削除SQL文(責任者精算エラーテーブル) */
//    private static final String DT_ERR_SEKININSYA_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_ERR_SEKININSYA_SEISAN";

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

//            // ワークテーブルのデータを削除する
//            // 単品精算データ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_TANPIN_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();
//            // 単品精算エラーデータ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_ERR_TANPIN_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();
//            // 部門精算データ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_BUMON_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();
//            // 部門精算エラーデータ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_ERR_BUMON_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();
//            // レジ別取引精算データ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_REGI_TORIHIKI_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();
//            // レジ別取引精算エラーデータ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_ERR_REGI_TORIHIKI_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();
//            // 責任者精算データ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_SEKININSYA_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();
//            // 責任者精算データ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_ERR_SEKININSYA_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();

            // データ登録

            // 単品精算データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_TANPIN_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTanpinSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
            preparedStatementExIns.setString(5, BATCH_DT);
            // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(8, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_TANPIN_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_TANPIN_SEISAN_TABLE_NAME + "の追加を終了します。");


            // 単品精算エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_TANPIN_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrTanpinSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
            preparedStatementExIns.setString(5, BATCH_DT);
            // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_TANPIN_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_TANPIN_SEISAN_TABLE_NAME + "の追加を終了します。");

            // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
            /*
            // 部門精算データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_BUMON_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getBumonSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_BUMON_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_BUMON_SEISAN_TABLE_NAME + "の追加を終了します。");


            // 部門精算エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_BUMON_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrBumonSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_BUMON_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_BUMON_SEISAN_TABLE_NAME + "の追加を終了します。");


            // レジ別取引精算データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_REGI_TORIHIKI_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getRegiTorihikiSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

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
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_REGI_TORIHIKI_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_REGI_TORIHIKI_SEISAN_TABLE_NAME + "の追加を終了します。");


            // 責任者精算データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_SEKININSYA_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getSekininsyaSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_02.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_SEKININSYA_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_SEKININSYA_SEISAN_TABLE_NAME + "の追加を終了します。");


            // 責任者精算エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_ERR_SEKININSYA_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getErrSekininsyaSeisanInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_SEKININSYA_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_SEKININSYA_SEISAN_TABLE_NAME + "の追加を終了します。");
            */
            // 2016.05.10 VINX S.Kashihara FIVI対応 (E)


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
//            try {
//                if (preparedStatementDelete != null) {
//                    preparedStatementDelete.close();
//                }
//
//            } catch (Exception ex) {
//                invoker.infoLog("preparedStatement Closeエラー");
//            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 単品精算データ登録用SQLを取得する
     *
     * @return 単品精算データ登録用SQL
     */
    private String getTanpinSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_TANPIN_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TANPIN_CD ");
        sql.append("    ,URIAGE_SOURI_QT ");
        sql.append("    ,URIAGE_SOURI_VL ");
        sql.append("    ,URIAGE_HITEIBAN_QT ");
        sql.append("    ,URIAGE_HITEIBAN_VL ");
        sql.append("    ,NEBIKI_MM_QT ");
        sql.append("    ,NEBIKI_MM_VL ");
        sql.append("    ,LOS_QT ");
        sql.append("    ,LOS_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,NEBIKI_SC_QT ");
        sql.append("    ,NEBIKI_SC_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,TEIBAN_TANKA_VL ");
        sql.append("    ,TOKUBAI_KIKAKU_NO ");
        sql.append("    ,END_HANBAI_TS ");
        sql.append("    ,DAIBUNRUI2_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,HINMEI_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_2_NA ");
        sql.append("    ,HINMEI_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_2_NA ");
        sql.append("    ,REC_HINMEI_KANA_NA ");
        sql.append("    ,TEIKAN_KB ");
        sql.append("    ,ZEI_KB ");
        sql.append("    ,TAX_RATE_KB ");
        sql.append("    ,TAX_RT ");
        sql.append("    ,MST_BAITANKA_VL ");
        sql.append("    ,SHIIRE_HANBAI_KB ");
        sql.append("    ,TANAOROSHI_GENKA_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016.08.05 VINX Y.itaki FIVI対応 #1879 (S)
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_KOMI_VL ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB) ");
        // 2016.08.05 VINX Y.itaki FIVI対応 #1879 (E)
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TANPIN_CD ");
        sql.append("    ,URIAGE_SOURI_QT ");
        sql.append("    ,URIAGE_SOURI_VL ");
        sql.append("    ,URIAGE_HITEIBAN_QT ");
        sql.append("    ,URIAGE_HITEIBAN_VL ");
        sql.append("    ,NEBIKI_MM_QT ");
        sql.append("    ,NEBIKI_MM_VL ");
        sql.append("    ,LOS_QT ");
        sql.append("    ,LOS_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,NEBIKI_SC_QT ");
        sql.append("    ,NEBIKI_SC_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,TEIBAN_TANKA_VL ");
        sql.append("    ,TOKUBAI_KIKAKU_NO ");
        sql.append("    ,END_HANBAI_TS ");
        sql.append("    ,DAIBUNRUI2_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,HINMEI_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_2_NA ");
        sql.append("    ,HINMEI_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_2_NA ");
        sql.append("    ,REC_HINMEI_KANA_NA ");
        sql.append("    ,TEIKAN_KB ");
        sql.append("    ,ZEI_KB ");
        sql.append("    ,TAX_RATE_KB ");
        sql.append("    ,TAX_RT ");
        sql.append("    ,MST_BAITANKA_VL ");
        sql.append("    ,SHIIRE_HANBAI_KB ");
        sql.append("    ,TANAOROSHI_GENKA_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,? "); // バッチ日付
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016.08.05 VINX Y.itaki FIVI対応 #1879 (S)
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_KOMI_VL ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        // 2016.08.05 VINX Y.itaki FIVI対応 #1879 (E)
        sql.append("FROM ");
        sql.append("    WK_YUKO_TANPIN_SEISAN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ( ");
        sql.append("        ERR_KB = ? OR ");
        sql.append("        ERR_KB = ? ");
        sql.append("    ) ");

        return sql.toString();
    }

    /**
     * 単品精算エラーデータ登録用SQLを取得する
     *
     * @return 単品精算エラーデータ登録用SQL
     */
    private String getErrTanpinSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_TANPIN_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TANPIN_SHIKIBETSU_CD ");
        sql.append("    ,TANPIN_CD ");
        sql.append("    ,URIAGE_SOURI_QT ");
        sql.append("    ,URIAGE_SOURI_VL ");
        sql.append("    ,URIAGE_HITEIBAN_QT ");
        sql.append("    ,URIAGE_HITEIBAN_VL ");
        sql.append("    ,NEBIKI_MM_QT ");
        sql.append("    ,NEBIKI_MM_VL ");
        sql.append("    ,LOS_QT ");
        sql.append("    ,LOS_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,NEBIKI_SC_QT ");
        sql.append("    ,NEBIKI_SC_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,GAISAN_ARARI_SOURI_VL ");
        sql.append("    ,GAISAN_ARARI_HITEIBAN_VL ");
        sql.append("    ,TEIBAN_TANKA_VL ");
        sql.append("    ,TOKUBAI_KIKAKU_NO ");
        sql.append("    ,END_HANBAI_TS ");
        sql.append("    ,DAIBUNRUI2_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,HINMEI_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_2_NA ");
        sql.append("    ,HINMEI_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_2_NA ");
        sql.append("    ,REC_HINMEI_KANA_NA ");
        sql.append("    ,TEIKAN_KB ");
        sql.append("    ,ZEI_KB ");
        sql.append("    ,TAX_RATE_KB ");
        sql.append("    ,TAX_RT ");
        sql.append("    ,MST_BAITANKA_VL ");
        sql.append("    ,SHIIRE_HANBAI_KB ");
        sql.append("    ,TANAOROSHI_GENKA_KB ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016.08.05 VINX Y.itaki FIVI対応 #1879 (S)
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_KOMI_VL ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB) ");
        // 2016.08.05 VINX Y.itaki FIVI対応 #1879 (E)
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TANPIN_SHIKIBETSU_CD ");
        sql.append("    ,TANPIN_CD ");
        sql.append("    ,URIAGE_SOURI_QT ");
        sql.append("    ,URIAGE_SOURI_VL ");
        sql.append("    ,URIAGE_HITEIBAN_QT ");
        sql.append("    ,URIAGE_HITEIBAN_VL ");
        sql.append("    ,NEBIKI_MM_QT ");
        sql.append("    ,NEBIKI_MM_VL ");
        sql.append("    ,LOS_QT ");
        sql.append("    ,LOS_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,NEBIKI_SC_QT ");
        sql.append("    ,NEBIKI_SC_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,GAISAN_ARARI_SOURI_VL ");
        sql.append("    ,GAISAN_ARARI_HITEIBAN_VL ");
        sql.append("    ,TEIBAN_TANKA_VL ");
        sql.append("    ,TOKUBAI_KIKAKU_NO ");
        sql.append("    ,END_HANBAI_TS ");
        sql.append("    ,DAIBUNRUI2_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,HINMEI_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_2_NA ");
        sql.append("    ,HINMEI_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_2_NA ");
        sql.append("    ,REC_HINMEI_KANA_NA ");
        sql.append("    ,TEIKAN_KB ");
        sql.append("    ,ZEI_KB ");
        sql.append("    ,TAX_RATE_KB ");
        sql.append("    ,TAX_RT ");
        sql.append("    ,MST_BAITANKA_VL ");
        sql.append("    ,SHIIRE_HANBAI_KB ");
        sql.append("    ,TANAOROSHI_GENKA_KB ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,? "); // バッチ日付
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016.08.05 VINX Y.itaki FIVI対応 #1879 (S)
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_KOMI_VL ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        // 2016.08.05 VINX Y.itaki FIVI対応 #1879 (E)
        sql.append("FROM ");
        sql.append("    WK_YUKO_TANPIN_SEISAN ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ERR_KB != ? ");

        return sql.toString();
    }

    // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
//    /**
//     * 部門精算データ登録用SQLを取得する
//     *
//     * @return 部門精算データ登録用SQL
//     */
//    private String getBumonSeisanInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_BUMON_SEISAN( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,URIAGE_SOURI_QT ");
//        sql.append("    ,URIAGE_SOURI_VL ");
//        sql.append("    ,URIAGE_HITEIBAN_QT ");
//        sql.append("    ,URIAGE_HITEIBAN_VL ");
//        sql.append("    ,LOS_QT ");
//        sql.append("    ,LOS_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,NEBIKI_SC_QT ");
//        sql.append("    ,NEBIKI_SC_VL ");
//        sql.append("    ,HAIKI_QT ");
//        sql.append("    ,HAIKI_VL ");
//        sql.append("    ,HENPIN_QT ");
//        sql.append("    ,HENPIN_VL ");
//        sql.append("    ,DAIHYO_SYOHIN_CD ");
//        sql.append("    ,URIAGE_ZEI_KB ");
//        sql.append("    ,URIAGE_TAX_RATE_KB ");
//        sql.append("    ,URIAGE_TAX_RT ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
//        sql.append("SELECT ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,URIAGE_SOURI_QT ");
//        sql.append("    ,URIAGE_SOURI_VL ");
//        sql.append("    ,URIAGE_HITEIBAN_QT ");
//        sql.append("    ,URIAGE_HITEIBAN_VL ");
//        sql.append("    ,LOS_QT ");
//        sql.append("    ,LOS_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,NEBIKI_SC_QT ");
//        sql.append("    ,NEBIKI_SC_VL ");
//        sql.append("    ,HAIKI_QT ");
//        sql.append("    ,HAIKI_VL ");
//        sql.append("    ,HENPIN_QT ");
//        sql.append("    ,HENPIN_VL ");
//        sql.append("    ,DAIHYO_SYOHIN_CD ");
//        sql.append("    ,URIAGE_ZEI_KB ");
//        sql.append("    ,URIAGE_TAX_RATE_KB ");
//        sql.append("    ,URIAGE_TAX_RT ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_BUMON_SEISAN ");
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
//     * 部門精算エラーデータ登録用SQLを取得する
//     *
//     * @return 部門精算エラーデータ登録用SQL
//     */
//    private String getErrBumonSeisanInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_BUMON_SEISAN( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,URIAGE_SOURI_QT ");
//        sql.append("    ,URIAGE_SOURI_VL ");
//        sql.append("    ,URIAGE_HITEIBAN_QT ");
//        sql.append("    ,URIAGE_HITEIBAN_VL ");
//        sql.append("    ,LOS_QT ");
//        sql.append("    ,LOS_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,NEBIKI_SC_QT ");
//        sql.append("    ,NEBIKI_SC_VL ");
//        sql.append("    ,HAIKI_QT ");
//        sql.append("    ,HAIKI_VL ");
//        sql.append("    ,HENPIN_QT ");
//        sql.append("    ,HENPIN_VL ");
//        sql.append("    ,GAISAN_ARARI_SOURI_VL ");
//        sql.append("    ,GAISAN_ARARI_HITEIBAN_VL ");
//        sql.append("    ,DAIHYO_SYOHIN_CD ");
//        sql.append("    ,URIAGE_ZEI_KB ");
//        sql.append("    ,URIAGE_TAX_RATE_KB ");
//        sql.append("    ,URIAGE_TAX_RT ");
//        sql.append("    ,ERR_KB ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
//        sql.append("SELECT ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,BUNRUI1_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,URIAGE_SOURI_QT ");
//        sql.append("    ,URIAGE_SOURI_VL ");
//        sql.append("    ,URIAGE_HITEIBAN_QT ");
//        sql.append("    ,URIAGE_HITEIBAN_VL ");
//        sql.append("    ,LOS_QT ");
//        sql.append("    ,LOS_VL ");
//        sql.append("    ,NEBIKI_REGI_QT ");
//        sql.append("    ,NEBIKI_REGI_VL ");
//        sql.append("    ,NEBIKI_SC_QT ");
//        sql.append("    ,NEBIKI_SC_VL ");
//        sql.append("    ,HAIKI_QT ");
//        sql.append("    ,HAIKI_VL ");
//        sql.append("    ,HENPIN_QT ");
//        sql.append("    ,HENPIN_VL ");
//        sql.append("    ,GAISAN_ARARI_SOURI_VL ");
//        sql.append("    ,GAISAN_ARARI_HITEIBAN_VL ");
//        sql.append("    ,DAIHYO_SYOHIN_CD ");
//        sql.append("    ,URIAGE_ZEI_KB ");
//        sql.append("    ,URIAGE_TAX_RATE_KB ");
//        sql.append("    ,URIAGE_TAX_RT ");
//        sql.append("    ,ERR_KB ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_BUMON_SEISAN ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ERR_KB != ? ");
//
//        return sql.toString();
//    }
//
//    /**
//     * レジ別取引精算データ登録用SQLを取得する
//     *
//     * @return レジ別取引精算データ登録用SQL
//     */
//    private String getRegiTorihikiSeisanInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_REGI_TORIHIKI_SEISAN( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TORIHIKI_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,TEN_KAISUU_QT ");
//        sql.append("    ,KINGAKU_VL ");
//        sql.append("    ,NEBIKI_VL ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
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
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_REGI_TORIHIKI_SEISAN ");
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
//     * レジ別取引精算エラーデータ登録用SQLを取得する
//     *
//     * @return レジ別取引精算エラーデータ登録用SQL
//     */
//    private String getErrRegiTorihikiSeisanInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_REGI_TORIHIKI_SEISAN( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TORIHIKI_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,TEN_KAISUU_QT ");
//        sql.append("    ,KINGAKU_VL ");
//        sql.append("    ,NEBIKI_VL ");
//        sql.append("    ,ERR_KB ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
//        sql.append("SELECT ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,TORIHIKI_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,TEN_KAISUU_QT ");
//        sql.append("    ,KINGAKU_VL ");
//        sql.append("    ,NEBIKI_VL ");
//        sql.append("    ,ERR_KB ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_REGI_TORIHIKI_SEISAN ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ERR_KB != ? ");
//
//        return sql.toString();
//    }
//
//    /**
//     * 責任者精算データ登録用SQLを取得する
//     *
//     * @return 責任者精算データ登録用SQL
//     */
//    private String getSekininsyaSeisanInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_SEKININSYA_SEISAN( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,CHECKER_CD ");
//        sql.append("    ,TORIHIKI_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,TEN_KAISUU_QT ");
//        sql.append("    ,KINGAKU_VL ");
//        sql.append("    ,NEBIKI_VL ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
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
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_SEKININSYA_SEISAN ");
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
//     * 責任者精算エラーデータ登録用SQLを取得する
//     *
//     * @return 責任者精算エラーデータ登録用SQL
//     */
//    private String getErrSekininsyaSeisanInsertSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_SEKININSYA_SEISAN( ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,CHECKER_CD ");
//        sql.append("    ,TORIHIKI_CD ");
//        sql.append("    ,KYAKU_QT ");
//        sql.append("    ,TEN_KAISUU_QT ");
//        sql.append("    ,KINGAKU_VL ");
//        sql.append("    ,NEBIKI_VL ");
//        sql.append("    ,ERR_KB ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
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
//        sql.append("    ,ERR_KB ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    WK_YUKO_SEKININSYA_SEISAN ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD = ? AND ");
//        sql.append("    ERR_KB != ? ");
//
//        return sql.toString();
//    }
    // 2016.05.10 VINX S.Kashihara FIVI対応 (E)




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
