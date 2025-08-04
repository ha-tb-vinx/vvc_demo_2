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
 * @Version 3.03 (2016.05.12) H.Yaguma FIVI対応
 * @Version 3.04 (2016.06.28) Y.Itaki FIVI対応
 * @Version 3.04 (2016.08.23) Y.Itaki FIVI(#1879対応)
 * @Version 3.06 (2017.02.06) J.Endo FIVI(#3847対応)
 * @Version 3.07 (2017.06.14) X.Liu FIVI(#5399対応)
 * @Version 3.08 (2017.07.03) J.Endo FIVI(#5040対応)
 *
 */
public class BumonSeisanDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "精算データ作成処理（部門精算）";
    /** 登録先テーブル名称(部門精算テーブル) */
    private static final String DT_BUMON_SEISAN_TABLE_NAME = "部門精算データ";
    /** 登録先テーブル名称(部門精算エラーテーブル) */
    private static final String DT_ERR_BUMON_SEISAN_TABLE_NAME = "部門精算エラーデータ";

    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();


//    /** 削除SQL文(部門精算テーブル) */
//    private static final String DT_BUMON_SEISAN_DEL_SQL = "TRUNCATE TABLE DT_BUMON_SEISAN";

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
//            // 部門精算データ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_BUMON_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();
//            // 部門精算エラーデータ削除
//            preparedStatementDelete = invoker.getDataBase().prepareStatement(DT_ERR_BUMON_SEISAN_DEL_SQL);
//            preparedStatementDelete.execute();

            // データ登録

            // 部門精算データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_BUMON_SEISAN_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getBumonSeisanInsertSql());
            // 2017/02/06 VINX J.Endo FIVI(#3847) MOD(S)
            //preparedStatementExIns.setString(1, userId);
            //preparedStatementExIns.setString(2, dbServerTime);
            //preparedStatementExIns.setString(3, userId);
            //preparedStatementExIns.setString(4, dbServerTime);
            //preparedStatementExIns.setString(5, BATCH_DT);
            //preparedStatementExIns.setString(6, COMP_CD);
            //preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());
            //preparedStatementExIns.setString(8, ErrorKbDictionary.ERROR_02.getCode());
            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(3, ErrorKbDictionary.ERROR_02.getCode());
            //#5399 Mod X.Liu 2017.06.14 (S)
//            preparedStatementExIns.setString(4, userId);
//            preparedStatementExIns.setString(5, dbServerTime);
//            preparedStatementExIns.setString(6, userId);
//            preparedStatementExIns.setString(7, dbServerTime);
//            preparedStatementExIns.setString(8, userId);
//            preparedStatementExIns.setString(9, dbServerTime);
//            preparedStatementExIns.setString(10, BATCH_DT);
            preparedStatementExIns.setString(4, BATCH_DT);
            preparedStatementExIns.setString(5, userId);
            preparedStatementExIns.setString(6, dbServerTime);
            preparedStatementExIns.setString(7, userId);
            preparedStatementExIns.setString(8, dbServerTime);
            preparedStatementExIns.setString(9, userId);
            preparedStatementExIns.setString(10, dbServerTime);
            preparedStatementExIns.setString(11, BATCH_DT);
            //#5399 Mod X.Liu 2017.06.14 (E)
            // 2017/02/06 VINX J.Endo FIVI(#3847) MOD(E)

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
            preparedStatementExIns.setString(5, BATCH_DT);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_ERR_BUMON_SEISAN_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_ERR_BUMON_SEISAN_TABLE_NAME + "の追加を終了します。");

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
     * 部門精算データ登録用SQLを取得する
     *
     * @return 部門精算データ登録用SQL
     */
    private String getBumonSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        // 2017/02/06 VINX J.Endo FIVI(#3847) MOD(S)
        //sql.append("INSERT /*+ APPEND */ INTO DT_BUMON_SEISAN( ");
        //sql.append("     COMP_CD ");
        //sql.append("    ,KEIJO_DT ");
        //sql.append("    ,TENPO_CD ");
        //sql.append("    ,BUNRUI1_CD ");
        //sql.append("    ,KYAKU_QT ");
        //sql.append("    ,URIAGE_SOURI_QT ");
        //sql.append("    ,URIAGE_SOURI_VL ");
        //sql.append("    ,URIAGE_HITEIBAN_QT ");
        //sql.append("    ,URIAGE_HITEIBAN_VL ");
        //sql.append("    ,LOS_QT ");
        //sql.append("    ,LOS_VL ");
        //sql.append("    ,NEBIKI_REGI_QT ");
        //sql.append("    ,NEBIKI_REGI_VL ");
        //sql.append("    ,NEBIKI_SC_QT ");
        //sql.append("    ,NEBIKI_SC_VL ");
        //sql.append("    ,HAIKI_QT ");
        //sql.append("    ,HAIKI_VL ");
        //sql.append("    ,HENPIN_QT ");
        //sql.append("    ,HENPIN_VL ");
        //sql.append("    ,DAIHYO_SYOHIN_CD ");
        //sql.append("    ,URIAGE_ZEI_KB ");
        //sql.append("    ,URIAGE_TAX_RATE_KB ");
        //sql.append("    ,URIAGE_TAX_RT ");
        //sql.append("    ,INSERT_USER_ID ");
        //sql.append("    ,INSERT_TS ");
        //sql.append("    ,UPDATE_USER_ID ");
        ////      sql.append("    ,UPDATE_TS) ");
        //sql.append("    ,UPDATE_TS ");
        //// 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        ////sql.append("    ,DATA_SAKUSEI_DT) ");
        //sql.append("    ,DATA_SAKUSEI_DT ");
        //sql.append("    ,HANBAI_WEIGHT_QT ");
        //sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        //sql.append("    ,URIAGE_ZEI_VL ");
        //sql.append("    ,HENPIN_WEIGHT ");
        //sql.append("    ,HENPIN_NUKI_VL ");
        //sql.append("    ,HENPIN_ZEI_VL ");
        //sql.append("    ,URIAGE_KB ");
        //sql.append(") ");
        //// 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        //sql.append("SELECT ");
        //sql.append("     COMP_CD ");
        //sql.append("    ,KEIJO_DT ");
        //sql.append("    ,TENPO_CD ");
        //sql.append("    ,BUNRUI1_CD ");
        //sql.append("    ,KYAKU_QT ");
        //sql.append("    ,URIAGE_SOURI_QT ");
        //sql.append("    ,URIAGE_SOURI_VL ");
        //sql.append("    ,URIAGE_HITEIBAN_QT ");
        //sql.append("    ,URIAGE_HITEIBAN_VL ");
        //sql.append("    ,LOS_QT ");
        //sql.append("    ,LOS_VL ");
        //sql.append("    ,NEBIKI_REGI_QT ");
        //sql.append("    ,NEBIKI_REGI_VL ");
        //sql.append("    ,NEBIKI_SC_QT ");
        //sql.append("    ,NEBIKI_SC_VL ");
        //sql.append("    ,HAIKI_QT ");
        //sql.append("    ,HAIKI_VL ");
        //sql.append("    ,HENPIN_QT ");
        //sql.append("    ,HENPIN_VL ");
        //sql.append("    ,DAIHYO_SYOHIN_CD ");
        //sql.append("    ,URIAGE_ZEI_KB ");
        //sql.append("    ,URIAGE_TAX_RATE_KB ");
        //sql.append("    ,URIAGE_TAX_RT ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //// 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        //sql.append("    ,HANBAI_WEIGHT_QT ");
        //sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        //sql.append("    ,URIAGE_ZEI_VL ");
        //sql.append("    ,HENPIN_WEIGHT ");
        //sql.append("    ,HENPIN_NUKI_VL ");
        //sql.append("    ,HENPIN_ZEI_VL ");
        //sql.append("    ,URIAGE_KB ");
        //// 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        //sql.append("FROM ");
        //// 20160628 Y.Itaki FIVI対応 MOD (S)
        ////sql.append("    WK_YUKO_BUMON_SEISAN ");
        //sql.append("    WK_YUKO_BUMON_SEISAN_KYAKUSU ");
        //// 20160628 Y.Itaki FIVI対応 MOD (E)
        //sql.append("WHERE ");
        //sql.append("    COMP_CD = ? AND ");
        //sql.append("    ( ");
        //sql.append("        ERR_KB = ? OR ");
        //sql.append("        ERR_KB = ? ");
        //sql.append("    ) ");
        sql.append("MERGE /*+ APPEND */ INTO DT_BUMON_SEISAN DBS ");
        sql.append("USING ( ");
        sql.append("    SELECT ");
        sql.append("        COMP_CD ");
        sql.append("       ,KEIJO_DT ");
        sql.append("       ,TENPO_CD ");
        sql.append("       ,BUNRUI1_CD ");
        sql.append("       ,KYAKU_QT ");
        sql.append("       ,URIAGE_SOURI_QT ");
        sql.append("       ,URIAGE_SOURI_VL ");
        sql.append("       ,URIAGE_HITEIBAN_QT ");
        sql.append("       ,URIAGE_HITEIBAN_VL ");
        sql.append("       ,LOS_QT ");
        sql.append("       ,LOS_VL ");
        sql.append("       ,NEBIKI_REGI_QT ");
        sql.append("       ,NEBIKI_REGI_VL ");
        sql.append("       ,NEBIKI_SC_QT ");
        sql.append("       ,NEBIKI_SC_VL ");
        sql.append("       ,HAIKI_QT ");
        sql.append("       ,HAIKI_VL ");
        sql.append("       ,HENPIN_QT ");
        sql.append("       ,HENPIN_VL ");
        sql.append("       ,DAIHYO_SYOHIN_CD ");
        sql.append("       ,URIAGE_ZEI_KB ");
        sql.append("       ,URIAGE_TAX_RATE_KB ");
        sql.append("       ,URIAGE_TAX_RT ");
        sql.append("       ,HANBAI_WEIGHT_QT ");
        sql.append("       ,URIAGE_NUKI_SOURI_VL ");
        sql.append("       ,URIAGE_ZEI_VL ");
        sql.append("       ,HENPIN_WEIGHT ");
        sql.append("       ,HENPIN_NUKI_VL ");
        sql.append("       ,HENPIN_ZEI_VL ");
        sql.append("       ,URIAGE_KB ");
        // 2017/07/03 VINX J.Endo FIVI(#5040) MOD(S)
        //sql.append("    FROM WK_YUKO_BUMON_SEISAN_KYAKUSU ");
        sql.append("    FROM WK_YUKO_BUMON_SEISAN ");
        // 2017/07/03 VINX J.Endo FIVI(#5040) MOD(E)
        sql.append("    WHERE ");
        sql.append("        COMP_CD = ? AND ");
        sql.append("       (ERR_KB  = ? OR ");
        sql.append("        ERR_KB  = ?) ");
        sql.append("    ) WYB ");
        sql.append("ON (DBS.KEIJO_DT   = WYB.KEIJO_DT   AND ");
        sql.append("    DBS.TENPO_CD   = WYB.TENPO_CD   AND ");
        sql.append("    DBS.BUNRUI1_CD = WYB.BUNRUI1_CD AND ");
        sql.append("    DBS.URIAGE_KB  = WYB.URIAGE_KB ");
        sql.append("    ) ");
        sql.append("WHEN MATCHED THEN ");
        sql.append("    UPDATE SET ");
        sql.append("        DBS.KYAKU_QT             = NVL(DBS.KYAKU_QT,0)             + NVL(WYB.KYAKU_QT,0) ");
        sql.append("       ,DBS.URIAGE_SOURI_QT      = NVL(DBS.URIAGE_SOURI_QT,0)      + NVL(WYB.URIAGE_SOURI_QT,0) ");
        sql.append("       ,DBS.URIAGE_SOURI_VL      = NVL(DBS.URIAGE_SOURI_VL,0)      + NVL(WYB.URIAGE_SOURI_VL,0) ");
        sql.append("       ,DBS.URIAGE_HITEIBAN_QT   = NVL(DBS.URIAGE_HITEIBAN_QT,0)   + NVL(WYB.URIAGE_HITEIBAN_QT,0) ");
        sql.append("       ,DBS.URIAGE_HITEIBAN_VL   = NVL(DBS.URIAGE_HITEIBAN_VL,0)   + NVL(WYB.URIAGE_HITEIBAN_VL,0) ");
        sql.append("       ,DBS.LOS_QT               = NVL(DBS.LOS_QT,0)               + NVL(WYB.LOS_QT,0) ");
        sql.append("       ,DBS.LOS_VL               = NVL(DBS.LOS_VL,0)               + NVL(WYB.LOS_VL,0) ");
        sql.append("       ,DBS.NEBIKI_REGI_QT       = NVL(DBS.NEBIKI_REGI_QT,0)       + NVL(WYB.NEBIKI_REGI_QT,0) ");
        sql.append("       ,DBS.NEBIKI_REGI_VL       = NVL(DBS.NEBIKI_REGI_VL,0)       + NVL(WYB.NEBIKI_REGI_VL,0) ");
        sql.append("       ,DBS.NEBIKI_SC_QT         = NVL(DBS.NEBIKI_SC_QT,0)         + NVL(WYB.NEBIKI_SC_QT,0) ");
        sql.append("       ,DBS.NEBIKI_SC_VL         = NVL(DBS.NEBIKI_SC_VL,0)         + NVL(WYB.NEBIKI_SC_VL,0) ");
        sql.append("       ,DBS.HAIKI_QT             = NVL(DBS.HAIKI_QT,0)             + NVL(WYB.HAIKI_QT,0) ");
        sql.append("       ,DBS.HAIKI_VL             = NVL(DBS.HAIKI_VL,0)             + NVL(WYB.HAIKI_VL,0) ");
        sql.append("       ,DBS.HENPIN_QT            = NVL(DBS.HENPIN_QT,0)            + NVL(WYB.HENPIN_QT,0) ");
        sql.append("       ,DBS.HENPIN_VL            = NVL(DBS.HENPIN_VL,0)            + NVL(WYB.HENPIN_VL,0) ");
        sql.append("       ,DBS.HANBAI_WEIGHT_QT     = NVL(DBS.HANBAI_WEIGHT_QT,0)     + NVL(WYB.HANBAI_WEIGHT_QT,0) ");
        sql.append("       ,DBS.URIAGE_NUKI_SOURI_VL = NVL(DBS.URIAGE_NUKI_SOURI_VL,0) + NVL(WYB.URIAGE_NUKI_SOURI_VL,0) ");
        sql.append("       ,DBS.URIAGE_ZEI_VL        = NVL(DBS.URIAGE_ZEI_VL,0)        + NVL(WYB.URIAGE_ZEI_VL,0) ");
        sql.append("       ,DBS.HENPIN_WEIGHT        = NVL(DBS.HENPIN_WEIGHT,0)        + NVL(WYB.HENPIN_WEIGHT,0) ");
        sql.append("       ,DBS.HENPIN_NUKI_VL       = NVL(DBS.HENPIN_NUKI_VL,0)       + NVL(WYB.HENPIN_NUKI_VL,0) ");
        sql.append("       ,DBS.HENPIN_ZEI_VL        = NVL(DBS.HENPIN_ZEI_VL,0)        + NVL(WYB.HENPIN_ZEI_VL,0) ");
        //#5399 Add X.Liu 2017.06.14 (S)
        sql.append("       ,DBS.DATA_SAKUSEI_DT      = ? ");
        //#5399 Add X.Liu 2017.06.14 (E)
        sql.append("       ,DBS.UPDATE_USER_ID       = ? ");
        sql.append("       ,DBS.UPDATE_TS            = ? ");
        sql.append("WHEN NOT MATCHED THEN ");
        sql.append("    INSERT ( ");
        sql.append("        DBS.COMP_CD ");
        sql.append("       ,DBS.KEIJO_DT ");
        sql.append("       ,DBS.TENPO_CD ");
        sql.append("       ,DBS.BUNRUI1_CD ");
        sql.append("       ,DBS.KYAKU_QT ");
        sql.append("       ,DBS.URIAGE_SOURI_QT ");
        sql.append("       ,DBS.URIAGE_SOURI_VL ");
        sql.append("       ,DBS.URIAGE_HITEIBAN_QT ");
        sql.append("       ,DBS.URIAGE_HITEIBAN_VL ");
        sql.append("       ,DBS.LOS_QT ");
        sql.append("       ,DBS.LOS_VL ");
        sql.append("       ,DBS.NEBIKI_REGI_QT ");
        sql.append("       ,DBS.NEBIKI_REGI_VL ");
        sql.append("       ,DBS.NEBIKI_SC_QT ");
        sql.append("       ,DBS.NEBIKI_SC_VL ");
        sql.append("       ,DBS.HAIKI_QT ");
        sql.append("       ,DBS.HAIKI_VL ");
        sql.append("       ,DBS.HENPIN_QT ");
        sql.append("       ,DBS.HENPIN_VL ");
        sql.append("       ,DBS.DAIHYO_SYOHIN_CD ");
        sql.append("       ,DBS.URIAGE_ZEI_KB ");
        sql.append("       ,DBS.URIAGE_TAX_RATE_KB ");
        sql.append("       ,DBS.URIAGE_TAX_RT ");
        sql.append("       ,DBS.INSERT_USER_ID ");
        sql.append("       ,DBS.INSERT_TS ");
        sql.append("       ,DBS.UPDATE_USER_ID ");
        sql.append("       ,DBS.UPDATE_TS ");
        sql.append("       ,DBS.DATA_SAKUSEI_DT ");
        sql.append("       ,DBS.HANBAI_WEIGHT_QT ");
        sql.append("       ,DBS.URIAGE_NUKI_SOURI_VL ");
        sql.append("       ,DBS.URIAGE_ZEI_VL ");
        sql.append("       ,DBS.HENPIN_WEIGHT ");
        sql.append("       ,DBS.HENPIN_NUKI_VL ");
        sql.append("       ,DBS.HENPIN_ZEI_VL ");
        sql.append("       ,DBS.URIAGE_KB ");
        sql.append("        ) ");
        sql.append("    VALUES ( ");
        sql.append("        WYB.COMP_CD ");
        sql.append("       ,WYB.KEIJO_DT ");
        sql.append("       ,WYB.TENPO_CD ");
        sql.append("       ,WYB.BUNRUI1_CD ");
        sql.append("       ,NVL(WYB.KYAKU_QT,0) ");
        sql.append("       ,NVL(WYB.URIAGE_SOURI_QT,0) ");
        sql.append("       ,NVL(WYB.URIAGE_SOURI_VL,0) ");
        sql.append("       ,NVL(WYB.URIAGE_HITEIBAN_QT,0) ");
        sql.append("       ,NVL(WYB.URIAGE_HITEIBAN_VL,0) ");
        sql.append("       ,NVL(WYB.LOS_QT,0) ");
        sql.append("       ,NVL(WYB.LOS_VL,0) ");
        sql.append("       ,NVL(WYB.NEBIKI_REGI_QT,0) ");
        sql.append("       ,NVL(WYB.NEBIKI_REGI_VL,0) ");
        sql.append("       ,NVL(WYB.NEBIKI_SC_QT,0) ");
        sql.append("       ,NVL(WYB.NEBIKI_SC_VL,0) ");
        sql.append("       ,NVL(WYB.HAIKI_QT,0) ");
        sql.append("       ,NVL(WYB.HAIKI_VL,0) ");
        sql.append("       ,NVL(WYB.HENPIN_QT,0) ");
        sql.append("       ,NVL(WYB.HENPIN_VL,0) ");
        sql.append("       ,WYB.DAIHYO_SYOHIN_CD ");
        sql.append("       ,WYB.URIAGE_ZEI_KB ");
        sql.append("       ,WYB.URIAGE_TAX_RATE_KB ");
        sql.append("       ,WYB.URIAGE_TAX_RT ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("       ,NVL(WYB.HANBAI_WEIGHT_QT,0) ");
        sql.append("       ,NVL(WYB.URIAGE_NUKI_SOURI_VL,0) ");
        sql.append("       ,NVL(WYB.URIAGE_ZEI_VL,0) ");
        sql.append("       ,NVL(WYB.HENPIN_WEIGHT,0) ");
        sql.append("       ,NVL(WYB.HENPIN_NUKI_VL,0) ");
        sql.append("       ,NVL(WYB.HENPIN_ZEI_VL,0) ");
        sql.append("       ,WYB.URIAGE_KB ");
        sql.append("        ) ");
        // 2017/02/06 VINX J.Endo FIVI(#3847) MOD(E)

        return sql.toString();
    }

    /**
     * 部門精算エラーデータ登録用SQLを取得する
     *
     * @return 部門精算エラーデータ登録用SQL
     */
    private String getErrBumonSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_ERR_BUMON_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_SOURI_QT ");
        sql.append("    ,URIAGE_SOURI_VL ");
        sql.append("    ,URIAGE_HITEIBAN_QT ");
        sql.append("    ,URIAGE_HITEIBAN_VL ");
        sql.append("    ,LOS_QT ");
        sql.append("    ,LOS_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,NEBIKI_SC_QT ");
        sql.append("    ,NEBIKI_SC_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,HENPIN_VL ");
        sql.append("    ,GAISAN_ARARI_SOURI_VL ");
        sql.append("    ,GAISAN_ARARI_HITEIBAN_VL ");
        sql.append("    ,DAIHYO_SYOHIN_CD ");
        sql.append("    ,URIAGE_ZEI_KB ");
        sql.append("    ,URIAGE_TAX_RATE_KB ");
        sql.append("    ,URIAGE_TAX_RT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        //sql.append("    ,DATA_SAKUSEI_DT) ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        sql.append(") ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_SOURI_QT ");
        sql.append("    ,URIAGE_SOURI_VL ");
        sql.append("    ,URIAGE_HITEIBAN_QT ");
        sql.append("    ,URIAGE_HITEIBAN_VL ");
        sql.append("    ,LOS_QT ");
        sql.append("    ,LOS_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,NEBIKI_SC_QT ");
        sql.append("    ,NEBIKI_SC_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,HENPIN_VL ");
        sql.append("    ,GAISAN_ARARI_SOURI_VL ");
        sql.append("    ,GAISAN_ARARI_HITEIBAN_VL ");
        sql.append("    ,DAIHYO_SYOHIN_CD ");
        sql.append("    ,URIAGE_ZEI_KB ");
        sql.append("    ,URIAGE_TAX_RATE_KB ");
        sql.append("    ,URIAGE_TAX_RT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        sql.append("FROM ");
        // 20160628 Y.Itaki FIVI対応 MOD (S)
        //sql.append("    WK_YUKO_BUMON_SEISAN ");
        // 2017/07/03 VINX J.Endo FIVI(#5040) MOD(S)
        //sql.append("    WK_YUKO_BUMON_SEISAN_KYAKUSU ");
        sql.append("    WK_YUKO_BUMON_SEISAN ");
        // 2017/07/03 VINX J.Endo FIVI(#5040) MOD(E)
        // 20160628 Y.Itaki FIVI対応 MOD (E)
        // 20160628 Y.Itaki FIVI対応 MOD (E)
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
            DaoIf dao = new BumonSeisanDataSakuseiDao();
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
