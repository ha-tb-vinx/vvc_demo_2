package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.util.HashMap;

import jp.co.vinculumjapan.mdware.uriage.constant.ErrorKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.impl.TouKaikeiNengetsuSelectDao;
import jp.co.vinculumjapan.mdware.uriage.dao.input.TouKaikeiNengetsuSelectDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.output.TouKaikeiNengetsuSelectDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: BumonSeisanMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理(部門精算)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.24) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.22) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №006
 * @Version 3.02 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.03 (2014.05.14) Y.Tominaga    [シス0243] 店集計レコード作成の条件変更
 * @Version 3.04 (2016.03.08) Vuong.LT 設計書No.605(#1161) FIVImart対応
 * @Version 3.05 (2016.04.11) T.Kamei  FIVImart対応 計上日の期間判定処理の修正
 * @Version 3.06 (2016.07.25) k.Hyo  FIVI対応 #1484
 * @Version 3.07 (2016.08.29) k.Hyo  FIVI対応 #1879
 * @Version 3.08 (2016.12.14) T.Kamei  FIVI対応 #3306
 * @Version 3.09 (2017.04.10) X.Liu  FIVI対応 #4553
 * @Version 3.10 (2017.06.23) J.Endo  FIVI対応 #5040
 * @Version 3.11 (2017.08.31) N.Katou  FIVI対応 #5840
 * @Version 3.12 (2020.11.13) KHAI.NN (#6258) MKV対応
 * @Version 3.13 (2021.01.18) THONG.VQ (#6291) MKV対応
 */
public class BumonSeisanMstJohoSyutokuDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // エラーコードとエラーDPT名
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラーコード
    private static final String ERROR_DPT_CODE = ERROR_DPT.keySet().toString().replace("[", "").replace("]", "");
    // システムコントロールよりエラー表示用DPT売上税区分取得
    private static final String ERR_DPT_URI_ZEI_KB = FiResorceUtility.getPropertie(UriResorceKeyConstant.ERR_DPT_URI_ZEI_KB);
    // システムコントロールよりエラー表示用DPT売上税率区分取得
    private static final String ERR_DPT_URI_TAX_RATE_KB = FiResorceUtility.getPropertie(UriResorceKeyConstant.ERR_DPT_URI_TAX_RATE_KB);
    // システムコントロールよりエラー表示用DPT売上税率取得
    private static final double ERR_DPT_URI_TAX_RT = Double.parseDouble(FiResorceUtility.getPropertie(UriResorceKeyConstant.ERR_DPT_URI_TAX_RT));
    // システムコントロールより店集計DPTコード取得
    private static final String TEN_SUMMARY_DPT_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_SYUKEI_BUNRUI1_CD);

    /** 数量登録MAX値 */
    private static final long SU_MAXNUM = 999999L;
    //#5040 ADD J.Endo 2017.06.23 (S)
    /** 金額MAX値 */
    private static final String KINGAKU_MAXNUM = "999999999999999.99";
    //#5040 ADD J.Endo 2017.06.23 (E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "マスタ情報取得処理（部門精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "有効部門精算ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_BUMON_SEISAN";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        //
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDelete = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            // No.605 URIB012220 Add 2016.03.08 Vuong.LT (S)
            // 当月度会計年月取得DAOInputBean
            TouKaikeiNengetsuSelectDaoInputBean inputKaikeiBean = new TouKaikeiNengetsuSelectDaoInputBean();
            // 当月度会計年月取得DAOOutputBean
            TouKaikeiNengetsuSelectDaoOutputBean outputKaikeiBean = new TouKaikeiNengetsuSelectDaoOutputBean();
            // 当月度会計年月取得DAO
            TouKaikeiNengetsuSelectDao daoSelect = new TouKaikeiNengetsuSelectDao();

            // 法人コード
            inputKaikeiBean.setCompCd(COMP_CD);

            // 入力ビーンをセット
            daoSelect.setInputObject(inputKaikeiBean);

            invoker.InvokeDao(daoSelect);

            // 出力ビーンをセット
            outputKaikeiBean = (TouKaikeiNengetsuSelectDaoOutputBean) daoSelect.getOutputObject();

            // 会計年月日
            String kaikeiDt = outputKaikeiBean.getUserKaikeiYr() + outputKaikeiBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;
            // No.605 URIB012220 Add 2016.03.08 Vuong.LT (E)

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // 部門精算ワークから有効部門精算ワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getYukoWkBumonSeisanInsertSql());

            // No.605 URIB012220 Mod 2016.03.08 Vuong.LT (S)
            int intI = 1;
            // preparedStatementExIns.setString(1, ERR_DPT_URI_ZEI_KB);
            // preparedStatementExIns.setString(2, ERR_DPT_URI_TAX_RATE_KB);
            // preparedStatementExIns.setDouble(3, ERR_DPT_URI_TAX_RT);
            //
            // preparedStatementExIns.setString(4, ErrorKbDictionary.ERROR_01.getCode());
            // preparedStatementExIns.setString(5, BATCH_DT);
            // preparedStatementExIns.setString(6, ErrorKbDictionary.ERROR_04.getCode());
            // preparedStatementExIns.setLong(7, SU_MAXNUM);
            // preparedStatementExIns.setString(8, ErrorKbDictionary.ERROR_05.getCode());
            // preparedStatementExIns.setLong(9, SU_MAXNUM);
            // preparedStatementExIns.setString(10, ErrorKbDictionary.ERROR_05.getCode());
            // preparedStatementExIns.setLong(11, SU_MAXNUM);
            // preparedStatementExIns.setString(12, ErrorKbDictionary.ERROR_05.getCode());
            // preparedStatementExIns.setString(13, ErrorKbDictionary.ERROR_02.getCode());
            // preparedStatementExIns.setString(14, ErrorKbDictionary.ERROR_03.getCode());
            // preparedStatementExIns.setString(15, ErrorKbDictionary.NORMAL_00.getCode());
            // preparedStatementExIns.setString(16, userId);
            // preparedStatementExIns.setString(17, dbServerTime);
            // preparedStatementExIns.setString(18, userId);
            // preparedStatementExIns.setString(19, dbServerTime);
            //
            // preparedStatementExIns.setString(20, COMP_CD);
            // preparedStatementExIns.setString(21, BATCH_DT);
            // preparedStatementExIns.setString(22, BATCH_DT);
            // preparedStatementExIns.setString(23, COMP_CD);
            // preparedStatementExIns.setString(24, BATCH_DT);
            // preparedStatementExIns.setString(25, COMP_CD);
            // preparedStatementExIns.setString(26, BATCH_DT);
            // preparedStatementExIns.setString(27, COMP_CD);

            preparedStatementExIns.setString(intI++, ERR_DPT_URI_ZEI_KB);
            preparedStatementExIns.setString(intI++, ERR_DPT_URI_TAX_RATE_KB);
            preparedStatementExIns.setDouble(intI++, ERR_DPT_URI_TAX_RT);

            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_01.getCode());
            // preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_04.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            //#5040 MOD J.Endo 2017.06.23 (S)
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            //#5040 MOD J.Endo 2017.06.23 (E)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_02.getCode());
            //preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_03.getCode());
            // 2017/08/31 VINX N.Katou #5840 (S)
//            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_09.getCode());
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, dbServerTime);
            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, dbServerTime);

            preparedStatementExIns.setString(intI++, kaikeiDt);
            preparedStatementExIns.setString(intI++, kaikeiDt);
            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (S)
            preparedStatementExIns.setString(intI++, kaikeiDt);
            preparedStatementExIns.setString(intI++, kaikeiDt);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (E)

            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);

            // #3306 2016.12.14 T.Kamei DEL (S)
            //preparedStatementExIns.setString(intI++, COMP_CD);
            //preparedStatementExIns.setString(intI++, BATCH_DT);
            // #3306 2016.12.14 T.Kamei DEL (E)

            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, COMP_CD);
            // No.605 URIB012220 Mod 2016.03.08 Vuong.LT (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // APPEND INSERTした内容確定する必要があるのでcommitを行う
            invoker.getDataBase().commit();

            // 店集計レコード作成
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getYukoWkBumonSeisanSummaryDataInsertSql());
            //#5040 MOD J.Endo 2017.06.23 (S)
            //preparedStatementExIns.setString(1, TEN_SUMMARY_DPT_CD);
            //preparedStatementExIns.setLong(2, SU_MAXNUM);
            //preparedStatementExIns.setString(3, ErrorKbDictionary.ERROR_05.getCode());
            //preparedStatementExIns.setLong(4, SU_MAXNUM);
            //preparedStatementExIns.setString(5, ErrorKbDictionary.ERROR_05.getCode());
            //preparedStatementExIns.setLong(6, SU_MAXNUM);
            //preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_05.getCode());
            //preparedStatementExIns.setString(8, ErrorKbDictionary.NORMAL_00.getCode());
            //
            //preparedStatementExIns.setString(9, userId);
            //preparedStatementExIns.setString(10, dbServerTime);
            //preparedStatementExIns.setString(11, userId);
            //preparedStatementExIns.setString(12, dbServerTime);
            //
            //preparedStatementExIns.setString(13, COMP_CD);
            //preparedStatementExIns.setString(14, ErrorKbDictionary.NORMAL_00.getCode());
            intI = 1;
            preparedStatementExIns.setString(intI++, TEN_SUMMARY_DPT_CD);
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());

            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, dbServerTime);
            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, dbServerTime);

            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());
            //#5040 MOD J.Endo 2017.06.23 (E)

            // ログ出力
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "店集計レコードを追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

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
    }

    /**
     * 部門精算ワークから有効部門精算ワークへ登録するSQLを取得する
     *
     * @return 有効部門精算ワーク登録クエリー
     */
    private String getYukoWkBumonSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        // 【ERROR_DPT_CODE】は集約条件に使用するため、SQLに直接埋め込む
        // ※パラメタで渡すと、SQL解析時点で不特定のため、GROUP BYの要素とみなされない

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_BUMON_SEISAN( ");
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
        // 2016/08/26 k.Hyo #1879対応 (S)
        //sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB) ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN '" + ERROR_DPT_CODE + "' ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        //sql.append("        ELSE BUMON_CD ");
        sql.append("        ELSE BUNRUI1_CD ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("     END AS BUNRUI1_CD ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        //sql.append("    ,SUM(URIAGE_KYAKU_QT) AS KYAKU_QT ");
        sql.append("    ,SUM(KYAKU_QT) AS KYAKU_QT ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("    ,SUM(URIAGE_SOURI_QT) AS URIAGE_SOURI_QT ");
        sql.append("    ,SUM(URIAGE_SOURI_VL) AS URIAGE_SOURI_VL ");
        sql.append("    ,SUM(URIAGE_HITEIBAN_QT) AS URIAGE_HITEIBAN_QT ");
        sql.append("    ,SUM(URIAGE_HITEIBAN_VL) AS URIAGE_HITEIBAN_VL ");
        sql.append("    ,SUM(LOS_QT) AS LOS_QT ");
        sql.append("    ,SUM(LOS_VL) AS LOS_VL ");
        sql.append("    ,SUM(NEBIKI_REGI_QT) AS NEBIKI_REGI_QT ");
        sql.append("    ,SUM(NEBIKI_REGI_VL) AS NEBIKI_REGI_VL ");
        sql.append("    ,SUM(NEBIKI_SC_QT) AS NEBIKI_SC_QT ");
        sql.append("    ,SUM(NEBIKI_SC_VL) AS NEBIKI_SC_VL ");
        sql.append("    ,SUM(HAIKI_QT) AS HAIKI_QT ");
        sql.append("    ,SUM(HAIKI_VL) AS HAIKI_VL ");
        sql.append("    ,SUM(HENPIN_QT) AS HENPIN_QT ");
        sql.append("    ,SUM(HENPIN_VL) AS HENPIN_VL ");
        sql.append("    ,SUM(GAISAN_ARARI_SOURI_VL) AS GAISAN_ARARI_SOURI_VL ");
        sql.append("    ,SUM(GAISAN_ARARI_HITEIBAN_VL) AS GAISAN_ARARI_HITEIBAN_VL ");
        // #1484対応 2016/07/25 k.Hyo (S)
        //sql.append("    ,MAX(RDS_SYOHIN_CD) AS DAIHYO_SYOHIN_CD ");
        //sql.append("    ,COALESCE(MAX(RDS_URIAGE_ZEI_KB), ?) AS URIAGE_ZEI_KB ");
        //sql.append("    ,COALESCE(MAX(RDS_URIAGE_TAX_RATE_KB), ?) AS URIAGE_TAX_RATE_KB ");
        //sql.append("    ,COALESCE(MAX(RDS_URIAGE_TAX_RT), ?) AS URIAGE_TAX_RT ");
        sql.append("    ,'' AS DAIHYO_SYOHIN_CD ");
        sql.append("    ,? AS URIAGE_ZEI_KB ");
        sql.append("    ,? AS URIAGE_TAX_RATE_KB ");
        sql.append("    ,? AS URIAGE_TAX_RT ");
        sql.append("    ,CASE ");
        sql.append("        WHEN MAX(RT_TENPO_CD) IS NULL THEN ? ");
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.605 URIB012220 Mod 2016.03.08 Vuong.LT (S)
        // sql.append("        WHEN KEIJO_DT < ? THEN ? ");
//        sql.append("        WHEN KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT  THEN ? ");
        sql.append("        WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT)  THEN ? ");
        // No.605 URIB012220 Mod 2016.03.08 Vuong.LT (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("        WHEN SUM(URIAGE_SOURI_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_HITEIBAN_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_QT) > ? THEN ? ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        sql.append("        WHEN SUM(URIAGE_SOURI_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_SOURI_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(HANBAI_WEIGHT_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_NUKI_SOURI_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_ZEI_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_WEIGHT) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_NUKI_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_ZEI_VL) > ? THEN ? ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("        WHEN MAX(RFTB_BUNRUI1_CD) IS NULL THEN ? ");
        //sql.append("        WHEN MAX(RDS_SYOHIN_CD) IS NULL THEN ? ");
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (S)
        // #3306 2016.12.14 T.Kamei Mod (S)
        //sql.append("        WHEN DTSS_SEISAN_STATE_FG = 1  THEN ? ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG <> 0  THEN ? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG <> 0  THEN ? ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3306 2016.12.14 T.Kamei Mod (E)
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        // #1484対応 2016/07/25 k.Hyo (E)
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016/08/26 k.Hyo #1879対応 (S)
        sql.append("    ,SUM(HANBAI_WEIGHT_QT) AS HANBAI_WEIGHT_QT ");
        sql.append("    ,SUM(URIAGE_NUKI_SOURI_VL) AS URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,SUM(URIAGE_ZEI_VL) AS URIAGE_ZEI_VL ");
        sql.append("    ,SUM(HENPIN_WEIGHT) AS HENPIN_WEIGHT ");
        sql.append("    ,SUM(HENPIN_NUKI_VL) AS HENPIN_NUKI_VL ");
        sql.append("    ,SUM(HENPIN_ZEI_VL) AS HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT   ");
        sql.append("             COMP_CD ");
        //#5040 MOD J.Endo 2017.06.23 (S)
        //sql.append("            ,SUBSTR(WBS.EIGYO_DT,1, 4) || SUBSTR(WBS.EIGYO_DT,6, 2) || SUBSTR(WBS.EIGYO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("            ,WBS.KEIJO_DT ");
        //#5040 MOD J.Endo 2017.06.23 (E)
        sql.append("            ,WBS.TENPO_CD AS TENPO_CD ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        //sql.append("            ,WBS.BUMON_CD AS BUMON_CD ");
        //sql.append("            ,TO_NUMBER(URIAGE_KYAKU_QT) AS URIAGE_KYAKU_QT ");
        sql.append("            ,WBS.BUNRUI1_CD AS BUNRUI1_CD ");
        sql.append("            ,TO_NUMBER(KYAKU_QT) AS KYAKU_QT ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("            ,TO_NUMBER(URIAGE_SOURI_QT) AS URIAGE_SOURI_QT ");
        sql.append("            ,TO_NUMBER(URIAGE_SOURI_VL) AS URIAGE_SOURI_VL ");
        sql.append("            ,TO_NUMBER(URIAGE_HITEIBAN_QT) AS URIAGE_HITEIBAN_QT ");
        sql.append("            ,TO_NUMBER(URIAGE_HITEIBAN_VL) AS URIAGE_HITEIBAN_VL ");
        sql.append("            ,TO_NUMBER(LOS_QT) AS LOS_QT ");
        sql.append("            ,TO_NUMBER(LOS_VL) AS LOS_VL ");
        sql.append("            ,TO_NUMBER(NEBIKI_REGI_QT) AS NEBIKI_REGI_QT ");
        sql.append("            ,TO_NUMBER(NEBIKI_REGI_VL) AS NEBIKI_REGI_VL ");
        sql.append("            ,TO_NUMBER(NEBIKI_SC_QT) AS NEBIKI_SC_QT ");
        sql.append("            ,TO_NUMBER(NEBIKI_SC_VL) AS NEBIKI_SC_VL ");
        sql.append("            ,TO_NUMBER(HAIKI_QT) AS HAIKI_QT ");
        sql.append("            ,TO_NUMBER(HAIKI_VL) AS HAIKI_VL ");
        sql.append("            ,TO_NUMBER(HENPIN_QT) AS HENPIN_QT ");
        sql.append("            ,TO_NUMBER(HENPIN_VL) AS HENPIN_VL ");
        sql.append("            ,TO_NUMBER(GAISAN_ARARI_SOURI_VL) AS GAISAN_ARARI_SOURI_VL ");
        sql.append("            ,TO_NUMBER(GAISAN_ARARI_HITEIBAN_VL) AS GAISAN_ARARI_HITEIBAN_VL ");
        sql.append("            ,RT.RT_TENPO_CD ");
        sql.append("            ,RFTB.RFTB_COMP_CD ");
        sql.append("            ,RFTB.RFTB_TENPO_CD ");
        sql.append("            ,RFTB.RFTB_BUNRUI1_CD ");
        sql.append("            ,RDS.RDS_COMP_CD ");
        sql.append("            ,RDS.RDS_BUNRUI1_CD ");
        sql.append("            ,RDS.RDS_SYOHIN_CD ");
        sql.append("            ,RDS.RDS_URIAGE_ZEI_KB ");
        sql.append("            ,RDS.RDS_URIAGE_TAX_RATE_KB ");
        sql.append("            ,TO_NUMBER(RDS.RDS_URIAGE_TAX_RT) AS RDS_URIAGE_TAX_RT ");
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (S)
        sql.append("            ,RT.SEISAN_ST_DT  ");
        sql.append("            ,RT.SEISAN_ED_DT  ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("            ,DTSS.DTSS_SEISAN_STATE_FG  ");
        sql.append("            ,DTSS.DTSS_ISAN_SEISAN_STATE_FG  ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (E)
        // 2016/08/26 k.Hyo #1879対応 (S)
        sql.append("            ,TO_NUMBER(WBS.HANBAI_WEIGHT_QT) AS HANBAI_WEIGHT_QT ");
        sql.append("            ,TO_NUMBER(WBS.URIAGE_NUKI_SOURI_VL) AS URIAGE_NUKI_SOURI_VL ");
        sql.append("            ,TO_NUMBER(WBS.URIAGE_ZEI_VL) AS URIAGE_ZEI_VL ");
        sql.append("            ,TO_NUMBER(WBS.HENPIN_WEIGHT) AS HENPIN_WEIGHT ");
        sql.append("            ,TO_NUMBER(WBS.HENPIN_NUKI_VL) AS HENPIN_NUKI_VL ");
        sql.append("            ,TO_NUMBER(WBS.HENPIN_ZEI_VL) AS HENPIN_ZEI_VL ");
        sql.append("            ,WBS.URIAGE_KB AS URIAGE_KB ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        sql.append("    FROM ");
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("             COMP_CD ");
        //#5040 MOD J.Endo 2017.06.23 (S)
        //sql.append("            ,EIGYO_DT ");
        sql.append("            ,KEIJO_DT ");
        //#5040 MOD J.Endo 2017.06.23 (E)
        sql.append("            ,TENPO_CD ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        //sql.append("            ,COALESCE(RPB.RPB_BUNRUI1_CD, RPAD(SUBSTR(WBS.BUMON_CD,1),4)) AS BUMON_CD ");
        //sql.append("            ,URIAGE_KYAKU_QT ");
        sql.append("            ,COALESCE(RPB.RPB_BUNRUI1_CD, RPAD(SUBSTR(WBS.BUNRUI1_CD,1),4)) AS BUNRUI1_CD ");
        sql.append("            ,KYAKU_QT ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("            ,URIAGE_SOURI_QT ");
        sql.append("            ,URIAGE_SOURI_VL ");
        sql.append("            ,URIAGE_HITEIBAN_QT ");
        sql.append("            ,URIAGE_HITEIBAN_VL ");
        sql.append("            ,LOS_QT ");
        sql.append("            ,LOS_VL ");
        sql.append("            ,NEBIKI_REGI_QT ");
        sql.append("            ,NEBIKI_REGI_VL ");
        sql.append("            ,NEBIKI_SC_QT ");
        sql.append("            ,NEBIKI_SC_VL ");
        sql.append("            ,HAIKI_QT ");
        sql.append("            ,HAIKI_VL ");
        sql.append("            ,HENPIN_QT ");
        sql.append("            ,HENPIN_VL ");
        sql.append("            ,GAISAN_ARARI_SOURI_VL ");
        sql.append("            ,GAISAN_ARARI_HITEIBAN_VL ");
        // 2016/08/26 k.Hyo #1879対応 (S)
        sql.append("            ,HANBAI_WEIGHT_QT ");
        sql.append("            ,URIAGE_NUKI_SOURI_VL ");
        sql.append("            ,URIAGE_ZEI_VL ");
        sql.append("            ,HENPIN_WEIGHT ");
        sql.append("            ,HENPIN_NUKI_VL ");
        sql.append("            ,HENPIN_ZEI_VL ");
        sql.append("            ,URIAGE_KB ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        sql.append("        FROM ");
        //#5040 MOD J.Endo 2017.06.23 (S)
        //sql.append("            WK_BUMON_SEISAN WBS ");
        sql.append("            WK_YUKO_BUMON_SEISAN_KYAKUSU WBS ");
        //#5040 MOD J.Endo 2017.06.23 (E)
        sql.append("            LEFT JOIN ");
        // ＜SUBPOS用部門変換マスタ＞
        sql.append("                ( ");
        sql.append("                SELECT ");
        sql.append("                     RPB1.COMP_CD           AS RPB_COMP_CD ");
        sql.append("                    ,RPB1.BUNRUI1_MAE_CD    AS RPB_BUNRUI1_MAE_CD ");
        sql.append("                    ,RPB1.BUNRUI1_CD        AS RPB_BUNRUI1_CD ");
        sql.append("                FROM ");
        sql.append("                    R_POS_BUMON_HENKAN RPB1 ");
        sql.append("                ) RPB ");
        sql.append("            ON ");
        sql.append("                WBS.COMP_CD                     = RPB.RPB_COMP_CD       AND ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        //sql.append("                RPAD(SUBSTR(WBS.BUMON_CD,1),4)  = RPB.RPB_BUNRUI1_MAE_CD ");
        sql.append("                RPAD(SUBSTR(WBS.BUNRUI1_CD,1),4)  = RPB.RPB_BUNRUI1_MAE_CD ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("        ) WBS ");
        sql.append("        LEFT JOIN ");
        // ＜SUB店舗マスタ＞
        sql.append("                (  ");
        sql.append("                    SELECT   ");
        sql.append("                        RT.TENPO_CD AS RT_TENPO_CD ");
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (S)
//        sql.append("                        , COALESCE(RT.SEISAN_ST_DT, CASE WHEN RT.KAITEN_DT > ? THEN RT.KAITEN_DT ELSE ? END ) AS SEISAN_ST_DT ");
//        sql.append("                        , COALESCE(RT.SEISAN_ED_DT, ZAIMU_END_DT ) AS SEISAN_ED_DT ");
        sql.append("                        , CASE ");
        sql.append("                              WHEN RT.SEISAN_ST_DT IS NULL THEN ? ");
        sql.append("                              ELSE ");
        sql.append("                                  CASE ");
        sql.append("                                      WHEN RT.SEISAN_ST_DT >= ? THEN RT.SEISAN_ST_DT ");
        sql.append("                                      ELSE ");
        sql.append("                                          CASE ");
        sql.append("                                              WHEN RT.SEISAN_ED_DT IS NULL OR RT.SEISAN_ED_DT >= ? THEN ? ");
                                                                  // 上記以外の場合はSEISAN_ST_DT、SEISAN_ED_DT共に会計年月より過去となるためnullを設定する。
        sql.append("                                              ELSE NULL ");
        sql.append("                                          END ");
        sql.append("                                  END ");
        sql.append("                          END AS SEISAN_ST_DT ");
        sql.append("                        , CASE ");
        sql.append("                              WHEN RT.SEISAN_ED_DT IS NULL THEN ? ");
        sql.append("                              ELSE ");
        sql.append("                                  CASE ");
        sql.append("                                      WHEN RT.SEISAN_ED_DT <= ? THEN RT.SEISAN_ED_DT ");
        sql.append("                                      ELSE ");
        sql.append("                                          CASE ");
        sql.append("                                              WHEN RT.SEISAN_ST_DT IS NULL OR RT.SEISAN_ST_DT <= ? THEN ? ");
                                                                  // 上記以外の場合は精算可能開始日、精算可能終了日共に会計年月より未来となるためnullを設定する。
        sql.append("                                              ELSE NULL ");
        sql.append("                                          END ");
        sql.append("                                  END ");
        sql.append("                          END AS SEISAN_ED_DT ");
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("                    FROM   ");
        sql.append("                        R_TENPO RT  ");
        sql.append("                    WHERE  ");
        sql.append("                        RT.HOJIN_CD      = ?        AND   ");
        sql.append("                        RT.TENPO_KB      = '1'      AND   ");
        sql.append("                        RT.KAITEN_DT    <= ?        AND   ");
        sql.append("                        RT.ZAIMU_END_DT >= ?        AND   ");
        sql.append("                        RT.DELETE_FG     = '0'  ");
        sql.append("                ) RT  ");
        sql.append("            ON   ");
        sql.append("                WBS.TENPO_CD = RT.RT_TENPO_CD   ");
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (S)
        // ＜SUB店別精算状況データ＞
        sql.append("            LEFT JOIN   ");
        sql.append("                (  ");
        sql.append("                    SELECT  ");
        // #3306 2016.12.14 T.Kamei Mod (S)
        sql.append("                        COMP_CD               AS DTSS_COMP_CD  ");
        sql.append("                        ,KEIJO_DT               AS DTSS_KEIJO_DT  ");
        sql.append("                        ,TENPO_CD               AS DTSS_TENPO_CD  ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("                        , SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG   ");
        sql.append("                        , ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG   ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("                    FROM   ");
        sql.append("                        DT_TEN_SEISAN_STATE   ");
        //sql.append("                    WHERE   ");
        //sql.append("                        COMP_CD  = ? AND   ");
        //sql.append("                        KEIJO_DT = ?   ");
        sql.append("                ) DTSS  ");
        sql.append("            ON   ");
        sql.append("                DTSS.DTSS_COMP_CD = WBS.COMP_CD AND ");
        //#5040 MOD J.Endo 2017.06.23 (S)
        //sql.append("                DTSS.DTSS_KEIJO_DT = SUBSTR(WBS.EIGYO_DT,1, 4) || SUBSTR(WBS.EIGYO_DT,6, 2) || SUBSTR(WBS.EIGYO_DT,9, 2) AND ");
        sql.append("                DTSS.DTSS_KEIJO_DT = WBS.KEIJO_DT AND ");
        //#5040 MOD J.Endo 2017.06.23 (E)
        sql.append("                DTSS.DTSS_TENPO_CD = WBS.TENPO_CD   ");
        //sql.append("                WBS.TENPO_CD = DTSS.DTSS_TENPO_CD   ");
        // #3306 2016.12.14 T.Kamei Mod (E)
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (E)
        sql.append("            LEFT JOIN   ");
        // ＜SUB店DPTマスタ＞
        sql.append("                (  ");
        sql.append("                SELECT   ");
        sql.append("                     RFTB1.COMP_CD              AS RFTB_COMP_CD  ");
        sql.append("                    ,RFTB1.TENPO_CD             AS RFTB_TENPO_CD  ");
        sql.append("                    ,RFTB1.BUNRUI1_CD           AS RFTB_BUNRUI1_CD  ");
        sql.append("                FROM  ");
        sql.append("                    R_FI_TENPO_BUNRUI1 RFTB1   ");
        sql.append("                    INNER JOIN   ");
        sql.append("                        (  ");
        sql.append("                        SELECT   ");
        sql.append("                             RFTB.COMP_CD  ");
        sql.append("                            ,RFTB.TENPO_CD  ");
        sql.append("                            ,RFTB.BUNRUI1_CD  ");
        sql.append("                            ,MAX(RFTB.YUKO_DT) AS YUKO_DT  ");
        sql.append("                        FROM  ");
        sql.append("                            R_FI_TENPO_BUNRUI1 RFTB  ");
        sql.append("                        WHERE  ");
        sql.append("                            RFTB.COMP_CD     = ?    AND   ");
        sql.append("                            RFTB.YUKO_DT    <= ?  ");
        sql.append("                        GROUP BY   ");
        sql.append("                             RFTB.COMP_CD  ");
        sql.append("                            ,RFTB.TENPO_CD  ");
        sql.append("                            ,RFTB.BUNRUI1_CD  ");
        sql.append("                        ) RFTB2  ");
        sql.append("                    ON  ");
        sql.append("                        RFTB1.COMP_CD           = RFTB2.COMP_CD     AND   ");
        sql.append("                        RFTB1.TENPO_CD          = RFTB2.TENPO_CD    AND   ");
        sql.append("                        RFTB1.BUNRUI1_CD        = RFTB2.BUNRUI1_CD  AND   ");
        sql.append("                        RFTB1.YUKO_DT           = RFTB2.YUKO_DT     AND   ");
        sql.append("                        RFTB1.TORIATSUKAI_KB    = '1'  ");
        sql.append("                ) RFTB   ");
        sql.append("            ON   ");
        sql.append("                WBS.COMP_CD                         = RFTB.RFTB_COMP_CD     AND   ");
        sql.append("                WBS.TENPO_CD = RFTB.RFTB_TENPO_CD    AND   ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        //sql.append("                WBS.BUMON_CD                        = RFTB.RFTB_BUNRUI1_CD ");
        sql.append("                WBS.BUNRUI1_CD                        = RFTB.RFTB_BUNRUI1_CD ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("            LEFT JOIN   ");
        // ＜SUB代表商品マスタ＞
        sql.append("                (  ");
        sql.append("                SELECT   ");
        sql.append("                     RDS1.COMP_CD               AS RDS_COMP_CD  ");
        sql.append("                    ,RDS1.BUNRUI1_CD            AS RDS_BUNRUI1_CD  ");
        sql.append("                    ,RDS1.SYOHIN_CD             AS RDS_SYOHIN_CD ");
        sql.append("                    ,RDS1.URIAGE_ZEI_KB         AS RDS_URIAGE_ZEI_KB ");
        sql.append("                    ,RDS1.URIAGE_TAX_RATE_KB    AS RDS_URIAGE_TAX_RATE_KB ");
        sql.append("                    ,RDS1.URIAGE_TAX_RT         AS RDS_URIAGE_TAX_RT ");
        sql.append("                FROM   ");
        sql.append("                    R_DAIHYO_SYOHIN RDS1   ");
        sql.append("                    INNER JOIN   ");
        sql.append("                        (  ");
        sql.append("                        SELECT   ");
        sql.append("                             RDS.COMP_CD  ");
        sql.append("                            ,RDS.BUNRUI1_CD  ");
        sql.append("                            ,MAX(RDS.YUKO_DT) AS YUKO_DT  ");
        sql.append("                        FROM  ");
        sql.append("                            R_DAIHYO_SYOHIN RDS  ");
        sql.append("                        WHERE  ");
        sql.append("                            RDS.COMP_CD  = ?    AND   ");
        sql.append("                            RDS.YUKO_DT <= ?  ");
        sql.append("                        GROUP BY   ");
        sql.append("                             RDS.COMP_CD  ");
        sql.append("                            ,RDS.BUNRUI1_CD  ");
        sql.append("                        ) RDS2  ");
        sql.append("                    ON  ");
        sql.append("                        RDS1.COMP_CD    = RDS2.COMP_CD      AND   ");
        sql.append("                        RDS1.BUNRUI1_CD = RDS2.BUNRUI1_CD   AND   ");
        sql.append("                        RDS1.YUKO_DT    = RDS2.YUKO_DT  ");
        sql.append("                ) RDS   ");
        sql.append("            ON   ");
        sql.append("                WBS.COMP_CD             = RDS.RDS_COMP_CD       AND   ");
        sql.append("                RFTB.RFTB_BUNRUI1_CD    = RDS.RDS_BUNRUI1_CD  ");
        sql.append("    ) ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");
        sql.append("GROUP BY  ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (S)
        sql.append("    ,SEISAN_ST_DT ");
        sql.append("    ,SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("    ,DTSS_SEISAN_STATE_FG ");
        sql.append("    ,DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.605 URIB012220 Add 2016.03.08 Vuong.LT (E)
        sql.append("    ,TENPO_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN '" + ERROR_DPT_CODE + "' ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        //sql.append("        ELSE BUMON_CD ");
        sql.append("        ELSE BUNRUI1_CD ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("     END ");
        // 2016/08/26 k.Hyo #1879対応 (S)
        sql.append("    ,URIAGE_KB ");
        // 2016/08/26 k.Hyo #1879対応 (S)

        return sql.toString();
    }


    /**
     * 有効部門精算ワーク店集計レコードを登録するSQLを取得する
     *
     * @return 有効部門精算ワーク店集計レコード登録クエリー
     */
    private String getYukoWkBumonSeisanSummaryDataInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_BUMON_SEISAN( ");
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
        // 2016/08/26 k.Hyo #1879対応 (S)
        //sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB) ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        sql.append("SELECT ");
        // #6291 URIB012220 Mod 2021.01.18 THONG.VQ (S)
        //sql.append("     COMP_CD ");
        //sql.append("    ,KEIJO_DT ");
        //sql.append("    ,TENPO_CD ");
        sql.append("     WYBS.COMP_CD ");
        sql.append("    ,WYBS.KEIJO_DT ");
        sql.append("    ,WYBS.TENPO_CD ");
        // #6291 URIB012220 Mod 2021.01.18 THONG.VQ (E)
        sql.append("    ,? ");
        // #6258 URIB012220 Mod 2020.11.13 KHAI.NN (S)
        //sql.append("    ,0 ");
        // #6291 URIB012220 Mod 2021.01.18 THONG.VQ (S)
        //sql.append("    ,SUM(KYAKU_QT) AS KYAKU_QT ");
        sql.append("    ,DJKS.KYAKU_QT AS KYAKU_QT ");
        // #6291 URIB012220 Mod 2021.01.18 THONG.VQ (E)
        // #6258 URIB012220 Mod 2020.11.13 KHAI.NN (E)
        sql.append("    ,SUM(URIAGE_SOURI_QT) AS URIAGE_SOURI_QT ");
        sql.append("    ,SUM(URIAGE_SOURI_VL) AS URIAGE_SOURI_VL ");
        sql.append("    ,SUM(URIAGE_HITEIBAN_QT) AS URIAGE_HITEIBAN_QT ");
        sql.append("    ,SUM(URIAGE_HITEIBAN_VL) AS URIAGE_HITEIBAN_VL ");
        sql.append("    ,SUM(LOS_QT) AS LOS_QT ");
        sql.append("    ,SUM(LOS_VL) AS LOS_VL ");
        sql.append("    ,SUM(NEBIKI_REGI_QT) AS NEBIKI_REGI_QT ");
        sql.append("    ,SUM(NEBIKI_REGI_VL) AS NEBIKI_REGI_VL ");
        sql.append("    ,SUM(NEBIKI_SC_QT) AS NEBIKI_SC_QT ");
        sql.append("    ,SUM(NEBIKI_SC_VL) AS NEBIKI_SC_VL ");
        sql.append("    ,SUM(HAIKI_QT) AS HAIKI_QT ");
        sql.append("    ,SUM(HAIKI_VL) AS HAIKI_VL ");
        sql.append("    ,SUM(HENPIN_QT) AS HENPIN_QT ");
        sql.append("    ,SUM(HENPIN_VL) AS HENPIN_VL ");
        sql.append("    ,SUM(GAISAN_ARARI_SOURI_VL) AS GAISAN_ARARI_SOURI_VL ");
        sql.append("    ,SUM(GAISAN_ARARI_HITEIBAN_VL) AS GAISAN_ARARI_HITEIBAN_VL ");
        sql.append("    ,MAX(DAIHYO_SYOHIN_CD) AS DAIHYO_SYOHIN_CD ");
        sql.append("    ,MAX(URIAGE_ZEI_KB) AS URIAGE_ZEI_KB ");
        sql.append("    ,MAX(URIAGE_TAX_RATE_KB) AS URIAGE_TAX_RATE_KB ");
        sql.append("    ,MAX(URIAGE_TAX_RT) AS URIAGE_TAX_RT ");
        sql.append("    ,CASE ");
        sql.append("        WHEN SUM(URIAGE_SOURI_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_HITEIBAN_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_QT) > ? THEN ? ");
        //#5040 ADD J.Endo 2017.06.23 (S)
        sql.append("        WHEN SUM(URIAGE_SOURI_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_SOURI_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(HANBAI_WEIGHT_QT) > ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_NUKI_SOURI_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(URIAGE_ZEI_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_WEIGHT) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_NUKI_VL) > ? THEN ? ");
        sql.append("        WHEN SUM(HENPIN_ZEI_VL) > ? THEN ? ");
        //#5040 ADD J.Endo 2017.06.23 (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016/08/26 k.Hyo #1879対応 (S)
        sql.append("    ,SUM(HANBAI_WEIGHT_QT) AS HANBAI_WEIGHT_QT ");
        sql.append("    ,SUM(URIAGE_NUKI_SOURI_VL) AS URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,SUM(URIAGE_ZEI_VL) AS URIAGE_ZEI_VL ");
        sql.append("    ,SUM(HENPIN_WEIGHT) AS HENPIN_WEIGHT ");
        sql.append("    ,SUM(HENPIN_NUKI_VL) AS HENPIN_NUKI_VL ");
        sql.append("    ,SUM(HENPIN_ZEI_VL) AS HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB AS URIAGE_KB ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        sql.append("FROM  ");
        // #6291 URIB012220 Mod 2021.01.18 THONG.VQ (S)
        //sql.append("    WK_YUKO_BUMON_SEISAN ");
        sql.append("    WK_YUKO_BUMON_SEISAN WYBS ");
        sql.append("INNER JOIN  ");
        sql.append("    DT_HIBETU_KYAKUSU_A DJKS ON ");
        sql.append("    DJKS.TENPO_CD = WYBS.TENPO_CD ");
        sql.append("    AND DJKS.KEIJYO_DT = WYBS.KEIJO_DT ");
        sql.append("    AND DJKS.KYAKU_SYUKEI_TANI = 'STR' ");
        sql.append("    AND SUBSTR(DJKS.KYAKU_SYUKEI_CD,-6) = WYBS.TENPO_CD ");
        // #6291 URIB012220 Mod 2021.01.18 THONG.VQ (E)
        sql.append("WHERE  ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    ( ");
        sql.append("        ERR_KB = ?       ");
        sql.append("    ) ");
        sql.append("GROUP BY ");
        // #6291 URIB012220 Mod 2021.01.18 THONG.VQ (S)
        //sql.append("     COMP_CD ");
        //sql.append("    ,KEIJO_DT ");
        //sql.append("    ,TENPO_CD ");
        sql.append("     WYBS.COMP_CD ");
        sql.append("    ,WYBS.KEIJO_DT ");
        sql.append("    ,WYBS.TENPO_CD ");
        sql.append("    ,DJKS.KYAKU_QT ");
        // #6291 URIB012220 Mod 2021.01.18 THONG.VQ (E)
        // 2016/08/26 k.Hyo #1879対応 (S)
        sql.append("    ,URIAGE_KB ");
        // 2016/08/26 k.Hyo #1879対応 (E)

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
            DaoIf dao = new BumonSeisanMstJohoSyutokuDao();
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
