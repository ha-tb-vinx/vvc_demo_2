package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

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
 * <p>タイトル: TanpinSeisanMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理(単品精算)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.20) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2013.12.11) M.Ayukawa [ST000001] 販売マスタ 売単価取得不備対応
 * @Version 3.03 (2016.03.08) Vuong.LT 設計書No.604(#1161) FIVImart対応
 * @Version 3.04 (2016.04.11) T.Kamei  FIVImart対応 計上日の期間判定処理の修正
 * @Version 3.05 (2016.05.10) VINX S.Kashihara FIVI対応
 * @Version 3.06 (2016.08.12) VINX k.Hyo FIVI対応
 * @Version 3.07 (2016.09.20) VINX Y.Itaki FIVI対応(#2102)
 * @Version 3.08 (2016.12.13) VINX T.Kamei FIVI対応(#3306)
 * @Version 3.09 (2017.04.10) VINX X.Liu FIVI対応(#4553)
 * @Version 3.10 (2017.06.22) VINX J.Endo FIVI対応(#5040)
 * @Version 3.11 (2017.08.31) VINX N.Katou  FIVI対応(#5840)
 */
public class TanpinSeisanMstJohoSyutokuDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システムコントロールよりダミー部門コード取得
    private static final String DUMMY_DAIBUNRUI2_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_DAIBUNRUI2_CD);
    // システムコントロールよりダミーDPTコード取得
    private static final String DUMMY_BUNRUI1_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI1_CD);
    // システムコントロールよりダミーラインコード取得
    private static final String DUMMY_BUNRUI2_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI2_CD);
    // システムコントロールよりダミークラスコード取得
    private static final String DUMMY_BUNRUI5_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI5_CD);
    // システムコントロールよりダミー税区分取得
    private static final String DUMMY_ZEI_KB = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_ZEI_KB);
    // システムコントロールよりダミー税率区分取得
    private static final String DUMMY_TAX_RATE_KB = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_TAX_RATE_KB);
    // システムコントロールよりダミー税率取得
    private static final double DUMMY_TAX_RT = Double.parseDouble(FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_TAX_RT));

    /** 数量登録MAX値 */
    private static final long SU_MAXNUM = 999999L;
    // #5040 2017.06.22 J.Endo ADD (S)
    /** 金額MAX値 */
    private static final String KINGAKU_MAXNUM = "999999999999999.99";
    // #5040 2017.06.22 J.Endo ADD (E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "マスタ情報取得処理（単品精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "有効単品精算ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_TANPIN_SEISAN";

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
        PreparedStatementEx preparedStatementDelete = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;

        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            // No.604 URIB012210 Add 2016.03.08 Vuong.LT (S)
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
            // No.604 URIB012210 Add 2016.03.08 Vuong.LT (E)

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // 単品精算ワークから有効単品精算ワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getYukoWkTanpinSeisanInsertSql());

            // No.604 URIB012210 Mod 2016.03.08 Vuong.LT (S)
            int intI = 1;
            // preparedStatementExIns.setString(1, DUMMY_DAIBUNRUI2_CD);
            // preparedStatementExIns.setString(2, DUMMY_DAIBUNRUI2_CD);
            // preparedStatementExIns.setString(3, DUMMY_BUNRUI1_CD);
            // preparedStatementExIns.setString(4, DUMMY_BUNRUI1_CD);
            // preparedStatementExIns.setString(5, DUMMY_BUNRUI2_CD);
            // preparedStatementExIns.setString(6, DUMMY_BUNRUI2_CD);
            // preparedStatementExIns.setString(7, DUMMY_BUNRUI5_CD);
            // preparedStatementExIns.setString(8, DUMMY_BUNRUI5_CD);
            // preparedStatementExIns.setString(9, DUMMY_ZEI_KB);
            // preparedStatementExIns.setString(10, DUMMY_TAX_RATE_KB);
            // preparedStatementExIns.setDouble(11, DUMMY_TAX_RT);
            //
            // preparedStatementExIns.setString(12, ErrorKbDictionary.ERROR_01.getCode());
            // preparedStatementExIns.setString(13, BATCH_DT);
            // preparedStatementExIns.setString(14, ErrorKbDictionary.ERROR_04.getCode());
            // preparedStatementExIns.setLong(15, SU_MAXNUM);
            // preparedStatementExIns.setString(16, ErrorKbDictionary.ERROR_05.getCode());
            // preparedStatementExIns.setLong(17, SU_MAXNUM);
            // preparedStatementExIns.setString(18, ErrorKbDictionary.ERROR_05.getCode());
            // preparedStatementExIns.setString(19, ErrorKbDictionary.ERROR_02.getCode());
            // preparedStatementExIns.setString(20, ErrorKbDictionary.NORMAL_00.getCode());
            // preparedStatementExIns.setString(21, userId);
            // preparedStatementExIns.setString(22, dbServerTime);
            // preparedStatementExIns.setString(23, userId);
            // preparedStatementExIns.setString(24, dbServerTime);
            //
            // preparedStatementExIns.setString(25, COMP_CD);
            // preparedStatementExIns.setString(26, BATCH_DT);
            // preparedStatementExIns.setString(27, BATCH_DT);
            // preparedStatementExIns.setString(28, BATCH_DT);
            // preparedStatementExIns.setString(29, COMP_CD);
            // preparedStatementExIns.setString(30, BATCH_DT);
            // preparedStatementExIns.setString(31, COMP_CD);
            // preparedStatementExIns.setString(32, BATCH_DT);
            // preparedStatementExIns.setString(33, COMP_CD);
            // preparedStatementExIns.setString(34, BATCH_DT);
            //
            // preparedStatementExIns.setString(35, COMP_CD);

            preparedStatementExIns.setString(intI++, DUMMY_DAIBUNRUI2_CD);
            preparedStatementExIns.setString(intI++, DUMMY_DAIBUNRUI2_CD);
            preparedStatementExIns.setString(intI++, DUMMY_BUNRUI1_CD);
            preparedStatementExIns.setString(intI++, DUMMY_BUNRUI1_CD);
            preparedStatementExIns.setString(intI++, DUMMY_BUNRUI2_CD);
            preparedStatementExIns.setString(intI++, DUMMY_BUNRUI2_CD);
            preparedStatementExIns.setString(intI++, DUMMY_BUNRUI5_CD);
            preparedStatementExIns.setString(intI++, DUMMY_BUNRUI5_CD);
            preparedStatementExIns.setString(intI++, DUMMY_ZEI_KB);
            preparedStatementExIns.setString(intI++, DUMMY_TAX_RATE_KB);
            preparedStatementExIns.setDouble(intI++, DUMMY_TAX_RT);

            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_01.getCode());
            // preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_04.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            // #5040 2017.06.22 J.Endo ADD (S)
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
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());
            // #5040 2017.06.22 J.Endo ADD (E)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_02.getCode());
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
            // #3306 2016.12.13 T.Kamei DEL (S)
            //preparedStatementExIns.setString(intI++, COMP_CD);
            //preparedStatementExIns.setString(intI++, BATCH_DT);
            // #3306 2016.12.13 T.Kamei DEL (E)
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, BATCH_DT);

            preparedStatementExIns.setString(intI++, COMP_CD);
            // No.604 URIB012210 Mod 2016.03.08 Vuong.LT (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

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
     * 単品精算ワークから有効単品精算ワークへ登録するSQLを取得する
     *
     * @return 単品精算データ登録クエリー
     */
    private String getYukoWkTanpinSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_TANPIN_SEISAN( ");
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
        //sql.append("    ,HANBAI_WEIGHT_QT) ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016/08/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,URIAGE_NUKI_SOURI_VL");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_KOMI_VL ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB) ");
        // 2016/08/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("SELECT  ");
        sql.append("     COMP_CD  ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TANPIN_SHIKIBETSU_CD  ");
        sql.append("    ,TANPIN_CD  ");
        // 2016.09.20 VINX Y.Itaki FIVI対応 (S)
//        sql.append("    ,URIAGE_SOURI_QT  ");
//        sql.append("    ,URIAGE_SOURI_VL  ");
//        sql.append("    ,URIAGE_HITEIBAN_QT  ");
//        sql.append("    ,URIAGE_HITEIBAN_VL  ");
//        sql.append("    ,MM_NEBIKI_QT  ");
//        sql.append("    ,MM_NEBIKI_VL  ");
//        sql.append("    ,LOS_QT  ");
//        sql.append("    ,LOS_VL  ");
//        sql.append("    ,NEBIKI_REGI_QT  ");
//        sql.append("    ,NEBIKI_REGI_VL  ");
//        sql.append("    ,NEBIKI_SC_QT  ");
//        sql.append("    ,NEBIKI_SC_VL  ");
//        sql.append("    ,HAIKI_QT  ");
//        sql.append("    ,HAIKI_VL  ");
//        sql.append("    ,GAISAN_ARARI_SOURI_VL  ");
//        sql.append("    ,GAISAN_ARARI_HITEIBAN_VL  ");
//        sql.append("    ,TEBAN_TANKA_VL  ");
        sql.append("    ,NVL(URIAGE_SOURI_QT,0)  ");
        sql.append("    ,NVL(URIAGE_SOURI_VL,0)  ");
        sql.append("    ,NVL(URIAGE_HITEIBAN_QT,0)  ");
        sql.append("    ,NVL(URIAGE_HITEIBAN_VL,0)  ");
        sql.append("    ,NVL(MM_NEBIKI_QT,0)  ");
        sql.append("    ,NVL(MM_NEBIKI_VL,0)  ");
        sql.append("    ,NVL(LOS_QT,0)  ");
        sql.append("    ,NVL(LOS_VL,0)  ");
        sql.append("    ,NVL(NEBIKI_REGI_QT,0)  ");
        sql.append("    ,NVL(NEBIKI_REGI_VL,0)  ");
        sql.append("    ,NVL(NEBIKI_SC_QT,0)  ");
        sql.append("    ,NVL(NEBIKI_SC_VL,0)  ");
        sql.append("    ,NVL(HAIKI_QT,0)  ");
        sql.append("    ,NVL(HAIKI_VL,0)  ");
        sql.append("    ,NVL(GAISAN_ARARI_SOURI_VL,0)  ");
        sql.append("    ,NVL(GAISAN_ARARI_HITEIBAN_VL,0)  ");
        sql.append("    ,NVL(TEBAN_TANKA_VL,0)  ");
        // 2016.09.20 VINX Y.Itaki FIVI対応 (E)
        sql.append("    ,TOKUBAI_KIKAKU_NO  ");
        sql.append("    ,SAISYU_URIAGE_TM ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        sql.append("        ELSE COALESCE(RFST_DAIBUNRUI2_CD, ?) ");
        sql.append("     END AS DAIBUNRUI2_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        sql.append("        ELSE COALESCE(RFH_BUNRUI1_CD, RFS_BUNRUI1_CD, ?) ");
        sql.append("     END AS BUNRUI1_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        sql.append("        ELSE COALESCE(RFH_BUNRUI2_CD, RFS_BUNRUI2_CD, ?) ");
        sql.append("     END AS BUNRUI2_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        sql.append("        ELSE COALESCE(RFH_BUNRUI5_CD, RFS_BUNRUI5_CD, ?) ");
        sql.append("     END AS BUNRUI5_CD ");
        sql.append("    ,COALESCE(RFH_HINMEI_KANJI_NA, RFS_HINMEI_KANJI_NA) AS HINMEI_KANJI_NA ");
        sql.append("    ,COALESCE(RFH_KIKAKU_KANJI_NA, RFS_KIKAKU_KANJI_NA) AS KIKAKU_KANJI_NA ");
        sql.append("    ,COALESCE(RFH_KIKAKU_KANJI_2_NA, RFS_KIKAKU_KANJI_2_NA) AS KIKAKU_KANJI_2_NA ");
        sql.append("    ,COALESCE(RFH_HINMEI_KANA_NA, RFS_HINMEI_KANA_NA) AS HINMEI_KANA_NA ");
        sql.append("    ,COALESCE(RFH_KIKAKU_KANA_NA, RFS_KIKAKU_KANA_NA) AS KIKAKU_KANA_NA ");
        sql.append("    ,COALESCE(RFH_KIKAKU_KANA_2_NA, RFS_KIKAKU_KANA_2_NA) AS KIKAKU_KANA_2_NA ");
        sql.append("    ,COALESCE(RFH_REC_HINMEI_KANA_NA, RFS_REC_HINMEI_KANA_NA) AS REC_HINMEI_KANA_NA ");
        sql.append("    ,COALESCE(RFH_TEIKAN_KB, RFS_TEIKAN_KB) AS TEIKAN_KB ");
        sql.append("    ,COALESCE(RFH_ZEI_KB, RFS_ZEI_KB, ?) AS ZEI_KB ");
        sql.append("    ,COALESCE(RFH_TAX_RATE_KB, RFS_TAX_RATE_KB, ?) AS TAX_RATE_KB ");
        sql.append("    ,COALESCE(RFH_TAX_RT, RFS_TAX_RT, ?) AS TAX_RT ");
        sql.append("    ,COALESCE(RFH_BAITANKA_DAY_VL, RFS_BAITANKA_VL) AS BAITANKA_VL ");
        sql.append("    ,COALESCE(RFH_SHIIRE_HANBAI_KB, RFS_SHIIRE_HANBAI_KB)  AS SHIIRE_HANBAI_KB ");
        sql.append("    ,RFTB_TANAOROSHI_GENKA_KB ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RT_TENPO_CD IS NULL THEN ? ");
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.604 URIB012210 Mod 2016.03.08 Vuong.LT (S)
        // sql.append("        WHEN KEIJO_DT < ? THEN ? ");
//        sql.append("        WHEN KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT  THEN ? ");
        sql.append("        WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT)  THEN ? ");
        // No.604 URIB012210 Mod 2016.03.08 Vuong.LT (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("        WHEN URIAGE_SOURI_QT > ? THEN ? ");
        sql.append("        WHEN URIAGE_HITEIBAN_QT > ? THEN ? ");
        // #5040 2017.06.22 J.Endo ADD (S)
        sql.append("        WHEN NVL(TO_NUMBER(URIAGE_SOURI_VL), 0) > ? THEN ? ");
        sql.append("        WHEN NVL(TO_NUMBER(HANBAI_WEIGHT_QT), 0) > ? THEN ? ");
        sql.append("        WHEN NVL(TO_NUMBER(URIAGE_NUKI_SOURI_VL), 0) > ? THEN ? ");
        sql.append("        WHEN NVL(TO_NUMBER(URIAGE_ZEI_VL), 0) > ? THEN ? ");
        sql.append("        WHEN NVL(TO_NUMBER(HENPIN_QT), 0) > ? THEN ? ");
        sql.append("        WHEN NVL(TO_NUMBER(HENPIN_WEIGHT), 0) > ? THEN ? ");
        sql.append("        WHEN NVL(TO_NUMBER(HENPIN_KOMI_VL), 0) > ? THEN ? ");
        sql.append("        WHEN NVL(TO_NUMBER(HENPIN_NUKI_VL), 0) > ? THEN ? ");
        sql.append("        WHEN NVL(TO_NUMBER(HENPIN_ZEI_VL), 0) > ? THEN ? ");
        // #5040 2017.06.22 J.Endo ADD (E)
        sql.append("        WHEN RFTB_BUNRUI1_CD IS NULL THEN ? ");
        // No.604 URIB012210 Add 2016.03.08 Vuong.LT (S)
        // #3306 2016.12.13 T.Kamei Mod (S)
        //sql.append("        WHEN DTSS_SEISAN_STATE_FG = 1  THEN ? ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG <> 0  THEN ? ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG <> 0  THEN ? ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3306 2016.12.13 T.Kamei Mod (E)
        // No.604 URIB012210 Add 2016.03.08 Vuong.LT (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016.09.20 VINX Y.Itaki FIVI対応 (S)
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
//        sql.append("    ,HANBAI_WEIGHT_QT ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016/08/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
//        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
//        sql.append("    ,URIAGE_ZEI_VL ");
//        sql.append("    ,HENPIN_QT ");
//        sql.append("    ,HENPIN_WEIGHT ");
//        sql.append("    ,HENPIN_KOMI_VL ");
//        sql.append("    ,HENPIN_NUKI_VL ");
//        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,NVL(HANBAI_WEIGHT_QT,0) ");
        sql.append("    ,NVL(URIAGE_NUKI_SOURI_VL,0) ");
        sql.append("    ,NVL(URIAGE_ZEI_VL,0) ");
        sql.append("    ,NVL(HENPIN_QT,0) ");
        sql.append("    ,NVL(HENPIN_WEIGHT,0) ");
        sql.append("    ,NVL(HENPIN_KOMI_VL,0) ");
        sql.append("    ,NVL(HENPIN_NUKI_VL,0) ");
        sql.append("    ,NVL(HENPIN_ZEI_VL,0) ");
        // 2016.09.20 VINX Y.Itaki FIVI対応 (E)
        sql.append("    ,URIAGE_KB ");
        // 2016/08/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("FROM  ");
        sql.append("    ( ");
        sql.append("        SELECT   ");
        sql.append("             WTP.COMP_CD  ");
        sql.append("            ,SUBSTR(WTP.EIGYO_DT,1, 4) || SUBSTR(WTP.EIGYO_DT,6, 2) || SUBSTR(WTP.EIGYO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("            ,WTP.TENPO_CD AS TENPO_CD ");
        sql.append("            ,WTP.TANPIN_SHIKIBETSU_CD  ");
        sql.append("            ,WTP.TANPIN_CD  ");
        sql.append("            ,TO_NUMBER(WTP.URIAGE_SOURI_QT) AS URIAGE_SOURI_QT  ");
        sql.append("            ,TO_NUMBER(WTP.URIAGE_SOURI_VL) AS URIAGE_SOURI_VL  ");
        sql.append("            ,TO_NUMBER(WTP.URIAGE_HITEIBAN_QT) AS URIAGE_HITEIBAN_QT  ");
        sql.append("            ,TO_NUMBER(WTP.URIAGE_HITEIBAN_VL) AS URIAGE_HITEIBAN_VL  ");
        sql.append("            ,TO_NUMBER(WTP.MM_NEBIKI_QT) AS MM_NEBIKI_QT  ");
        sql.append("            ,TO_NUMBER(WTP.MM_NEBIKI_VL) AS MM_NEBIKI_VL  ");
        sql.append("            ,TO_NUMBER(WTP.LOS_QT) AS LOS_QT  ");
        sql.append("            ,TO_NUMBER(WTP.LOS_VL) AS LOS_VL  ");
        sql.append("            ,TO_NUMBER(WTP.NEBIKI_REGI_QT) AS NEBIKI_REGI_QT  ");
        sql.append("            ,TO_NUMBER(WTP.NEBIKI_REGI_VL) AS NEBIKI_REGI_VL  ");
        sql.append("            ,TO_NUMBER(WTP.NEBIKI_SC_QT) AS NEBIKI_SC_QT  ");
        sql.append("            ,TO_NUMBER(WTP.NEBIKI_SC_VL) AS NEBIKI_SC_VL  ");
        sql.append("            ,TO_NUMBER(WTP.HAIKI_QT) AS HAIKI_QT  ");
        sql.append("            ,TO_NUMBER(WTP.HAIKI_VL) AS HAIKI_VL  ");
        sql.append("            ,TO_NUMBER(WTP.GAISAN_ARARI_SOURI_VL) AS GAISAN_ARARI_SOURI_VL  ");
        sql.append("            ,TO_NUMBER(WTP.GAISAN_ARARI_HITEIBAN_VL) AS GAISAN_ARARI_HITEIBAN_VL  ");
        sql.append("            ,TO_NUMBER(WTP.TEBAN_TANKA_VL) AS TEBAN_TANKA_VL  ");
        sql.append("            ,WTP.TOKUBAI_KIKAKU_NO  ");
        sql.append("            ,SUBSTR(WTP.SAISYU_URIAGE_TM,1, 2) || SUBSTR(WTP.SAISYU_URIAGE_TM,4, 2) || '00'  AS SAISYU_URIAGE_TM ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
        sql.append("            ,WTP.HANBAI_WEIGHT_QT  ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        sql.append("            ,RT_TENPO_CD  ");
        sql.append("            ,RFS_SYOHIN_CD  ");
        sql.append("            ,RFS_BUNRUI1_CD  ");
        sql.append("            ,RFS_BUNRUI2_CD  ");
        sql.append("            ,RFS_BUNRUI5_CD  ");
        sql.append("            ,RFS_HINMEI_KANJI_NA  ");
        sql.append("            ,RFS_KIKAKU_KANJI_NA  ");
        sql.append("            ,RFS_KIKAKU_KANJI_2_NA  ");
        sql.append("            ,RFS_HINMEI_KANA_NA  ");
        sql.append("            ,RFS_KIKAKU_KANA_NA  ");
        sql.append("            ,RFS_KIKAKU_KANA_2_NA  ");
        sql.append("            ,RFS_REC_HINMEI_KANA_NA  ");
        sql.append("            ,RFS_TEIKAN_KB  ");
        sql.append("            ,RFS_ZEI_KB  ");
        sql.append("            ,RFS_TAX_RATE_KB  ");
        sql.append("            ,RFS_TAX_RT  ");
        sql.append("            ,RFS_BAITANKA_VL  ");
        sql.append("            ,RFS_SHIIRE_HANBAI_KB  ");
        sql.append("            ,RFH_COMP_CD  ");
        sql.append("            ,RFH_TENPO_CD  ");
        sql.append("            ,RFH_SYOHIN_CD  ");
        sql.append("            ,RFH_BUNRUI1_CD  ");
        sql.append("            ,RFH_BUNRUI2_CD  ");
        sql.append("            ,RFH_BUNRUI5_CD  ");
        sql.append("            ,RFH_HINMEI_KANJI_NA  ");
        sql.append("            ,RFH_KIKAKU_KANJI_NA  ");
        sql.append("            ,RFH_KIKAKU_KANJI_2_NA  ");
        sql.append("            ,RFH_HINMEI_KANA_NA  ");
        sql.append("            ,RFH_KIKAKU_KANA_NA  ");
        sql.append("            ,RFH_KIKAKU_KANA_2_NA  ");
        sql.append("            ,RFH_REC_HINMEI_KANA_NA  ");
        sql.append("            ,RFH_TEIKAN_KB  ");
        sql.append("            ,RFH_ZEI_KB  ");
        sql.append("            ,RFH_TAX_RATE_KB  ");
        sql.append("            ,RFH_TAX_RT  ");
        sql.append("            ,RFH_BAITANKA_DAY_VL  ");
        sql.append("            ,RFH_SHIIRE_HANBAI_KB  ");
        sql.append("            ,RFTB_COMP_CD  ");
        sql.append("            ,RFTB_TENPO_CD  ");
        sql.append("            ,RFTB_BUNRUI1_CD  ");
        sql.append("            ,RFTB_TANAOROSHI_GENKA_KB  ");
        sql.append("            ,RFST_COMP_CD  ");
        sql.append("            ,RFST_BUNRUI5_CD  ");
        sql.append("            ,RFST_DAIBUNRUI2_CD  ");
        // No.604 URIB012210 Add 2016.03.08 Vuong.LT (S)
        sql.append("            ,RT.SEISAN_ST_DT  ");
        sql.append("            ,RT.SEISAN_ED_DT  ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("            ,DTSS.DTSS_SEISAN_STATE_FG  ");
        sql.append("            ,DTSS.DTSS_ISAN_SEISAN_STATE_FG  ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.604 URIB012210 Add 2016.03.08 Vuong.LT (E)
        // 2016/08/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("            ,WTP.URIAGE_NUKI_SOURI_VL ");
        sql.append("            ,WTP.URIAGE_ZEI_VL ");
        sql.append("            ,WTP.HENPIN_QT ");
        sql.append("            ,WTP.HENPIN_WEIGHT ");
        sql.append("            ,WTP.HENPIN_KOMI_VL ");
        sql.append("            ,WTP.HENPIN_NUKI_VL ");
        sql.append("            ,WTP.HENPIN_ZEI_VL ");
        sql.append("            ,WTP.URIAGE_KB ");
        // 2016/08/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("        FROM   ");
        sql.append("            WK_TANPIN_SEISAN WTP   ");
        sql.append("            LEFT JOIN   ");
        // ＜SUB店舗マスタ＞
        sql.append("                (  ");
        sql.append("                    SELECT   ");
        sql.append("                        RT.TENPO_CD AS RT_TENPO_CD ");
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.604 URIB012210 Add 2016.03.08 Vuong.LT (S)
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
        // No.604 URIB012210 Add 2016.03.08 Vuong.LT (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("                    FROM   ");
        sql.append("                        R_TENPO RT  ");
        sql.append("                    WHERE  ");
        sql.append("                        RT.HOJIN_CD      = ?    AND   ");
        sql.append("                        RT.TENPO_KB      = '1'  AND   ");
        sql.append("                        RT.KAITEN_DT    <= ?    AND   ");
        sql.append("                        RT.ZAIMU_END_DT >= ?    AND   ");
        sql.append("                        RT.DELETE_FG     = '0'  ");
        sql.append("                ) RT  ");
        sql.append("            ON   ");
        sql.append("                WTP.TENPO_CD = RT.RT_TENPO_CD   ");
        // No.604 URIB012210 Add 2016.03.08 Vuong.LT (S)
        // ＜SUB店別精算状況データ＞
        sql.append("            LEFT JOIN   ");
        sql.append("                (  ");
        sql.append("                    SELECT  ");
        // #3306 2016.12.13 T.Kamei Mod (S)
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
        sql.append("                DTSS.DTSS_COMP_CD = WTP.COMP_CD AND ");
        sql.append("                DTSS.DTSS_KEIJO_DT = SUBSTR(WTP.EIGYO_DT,1, 4) || SUBSTR(WTP.EIGYO_DT,6, 2) || SUBSTR(WTP.EIGYO_DT,9, 2) AND ");
        sql.append("                DTSS.DTSS_TENPO_CD = WTP.TENPO_CD   ");
        //sql.append("                WTP.TENPO_CD = DTSS.DTSS_TENPO_CD   ");
        // #3306 2016.12.13 T.Kamei Mod (E)
        // No.604 URIB012210 Add 2016.03.08 Vuong.LT (E)
        sql.append("            LEFT JOIN   ");
        // ＜SUB商品マスタ＞
        sql.append("                (  ");
        sql.append("                SELECT   ");
        sql.append("                     RFS1.SYOHIN_CD             AS RFS_SYOHIN_CD  ");
        sql.append("                    ,RFS1.BUNRUI1_CD            AS RFS_BUNRUI1_CD  ");
        sql.append("                    ,RFS1.BUNRUI5_CD            AS RFS_BUNRUI5_CD  ");
        sql.append("                    ,RFS1.BUNRUI2_CD            AS RFS_BUNRUI2_CD  ");
        sql.append("                    ,RFS1.HINMEI_KANJI_NA       AS RFS_HINMEI_KANJI_NA  ");
        sql.append("                    ,RFS1.KIKAKU_KANJI_NA       AS RFS_KIKAKU_KANJI_NA  ");
        sql.append("                    ,RFS1.KIKAKU_KANJI_2_NA     AS RFS_KIKAKU_KANJI_2_NA  ");
        sql.append("                    ,RFS1.HINMEI_KANA_NA        AS RFS_HINMEI_KANA_NA  ");
        sql.append("                    ,RFS1.KIKAKU_KANA_NA        AS RFS_KIKAKU_KANA_NA  ");
        sql.append("                    ,RFS1.KIKAKU_KANA_2_NA      AS RFS_KIKAKU_KANA_2_NA  ");
        sql.append("                    ,RFS1.REC_HINMEI_KANA_NA    AS RFS_REC_HINMEI_KANA_NA  ");
        sql.append("                    ,RFS1.TEIKAN_KB             AS RFS_TEIKAN_KB  ");
        sql.append("                    ,RFS1.ZEI_KB                AS RFS_ZEI_KB  ");
        sql.append("                    ,RFS1.TAX_RATE_KB           AS RFS_TAX_RATE_KB  ");
        sql.append("                    ,RFS1.TAX_RT                AS RFS_TAX_RT  ");
        sql.append("                    ,RFS1.BAITANKA_VL           AS RFS_BAITANKA_VL  ");
        sql.append("                    ,RFS1.SHIIRE_HANBAI_KB      AS RFS_SHIIRE_HANBAI_KB  ");
        sql.append("                FROM   ");
        sql.append("                    R_FI_SYOHIN RFS1   ");
        sql.append("                    INNER JOIN   ");
        sql.append("                        (  ");
        sql.append("                        SELECT   ");
        sql.append("                             RFS.SYOHIN_CD  ");
        sql.append("                            ,MAX(RFS.YUKO_DT) AS YUKO_DT  ");
        sql.append("                        FROM   ");
        sql.append("                            R_FI_SYOHIN RFS  ");
        sql.append("                        WHERE   ");
        sql.append("                            RFS.YUKO_DT <= ?  ");
        sql.append("                        GROUP BY   ");
        sql.append("                            RFS.SYOHIN_CD  ");
        sql.append("                        ) RFS2  ");
        sql.append("                    ON   ");
        sql.append("                        RFS1.SYOHIN_CD  = RFS2.SYOHIN_CD    AND  ");
        sql.append("                        RFS1.YUKO_DT    = RFS2.YUKO_DT  ");
        sql.append("                ) RFS   ");
        sql.append("            ON   ");
        sql.append("                WTP.TANPIN_CD = RFS.RFS_SYOHIN_CD  ");
        sql.append("            LEFT JOIN   ");
        // ＜SUB販売マスタ＞
        sql.append("                (  ");
        sql.append("                SELECT   ");
        sql.append("                     RFH1.COMP_CD               AS RFH_COMP_CD  ");
        sql.append("                    ,RFH1.TENPO_CD              AS RFH_TENPO_CD  ");
        sql.append("                    ,RFH1.SYOHIN_CD             AS RFH_SYOHIN_CD  ");
        sql.append("                    ,RFH1.BUNRUI1_CD            AS RFH_BUNRUI1_CD  ");
        sql.append("                    ,RFH1.BUNRUI2_CD            AS RFH_BUNRUI2_CD  ");
        sql.append("                    ,RFH1.BUNRUI5_CD            AS RFH_BUNRUI5_CD  ");
        sql.append("                    ,RFH1.HINMEI_KANJI_NA       AS RFH_HINMEI_KANJI_NA  ");
        sql.append("                    ,RFH1.KIKAKU_KANJI_NA       AS RFH_KIKAKU_KANJI_NA  ");
        sql.append("                    ,RFH1.KIKAKU_KANJI_2_NA     AS RFH_KIKAKU_KANJI_2_NA  ");
        sql.append("                    ,RFH1.HINMEI_KANA_NA        AS RFH_HINMEI_KANA_NA  ");
        sql.append("                    ,RFH1.KIKAKU_KANA_NA        AS RFH_KIKAKU_KANA_NA  ");
        sql.append("                    ,RFH1.KIKAKU_KANA_2_NA      AS RFH_KIKAKU_KANA_2_NA  ");
        sql.append("                    ,RFH1.REC_HINMEI_KANA_NA    AS RFH_REC_HINMEI_KANA_NA  ");
        sql.append("                    ,RFH1.TEIKAN_KB             AS RFH_TEIKAN_KB  ");
        sql.append("                    ,RFH1.ZEI_KB                AS RFH_ZEI_KB  ");
        sql.append("                    ,RFH1.TAX_RATE_KB           AS RFH_TAX_RATE_KB  ");
        sql.append("                    ,RFH1.TAX_RT                AS RFH_TAX_RT  ");
        sql.append("                    ,RFH1.BAITANKA_DAY_VL       AS RFH_BAITANKA_DAY_VL  ");
        sql.append("                    ,RFH1.SHIIRE_HANBAI_KB      AS RFH_SHIIRE_HANBAI_KB  ");
        sql.append("                FROM   ");
        sql.append("                    R_FI_HANBAI RFH1   ");
        sql.append("                    INNER JOIN  ");
        sql.append("                        (  ");
        sql.append("                        SELECT   ");
        sql.append("                             RFH.COMP_CD  ");
        sql.append("                            ,RFH.TENPO_CD  ");
        sql.append("                            ,RFH.SYOHIN_CD  ");
        sql.append("                            ,MAX(RFH.TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("                        FROM  ");
        sql.append("                            R_FI_HANBAI RFH  ");
        sql.append("                        WHERE  ");
        sql.append("                            RFH.COMP_CD          = ?    AND  ");
        sql.append("                            RFH.TEKIYO_START_DT <= ?  ");
        sql.append("                        GROUP BY   ");
        sql.append("                             RFH.COMP_CD  ");
        sql.append("                            ,RFH.TENPO_CD  ");
        sql.append("                            ,RFH.SYOHIN_CD  ");
        sql.append("                        ) RFH2  ");
        sql.append("                    ON   ");
        sql.append("                        RFH1.COMP_CD            = RFH2.COMP_CD      AND  ");
        sql.append("                        RFH1.TENPO_CD           = RFH2.TENPO_CD     AND  ");
        sql.append("                        RFH1.SYOHIN_CD          = RFH2.SYOHIN_CD    AND   ");
        sql.append("                        RFH1.TEKIYO_START_DT    = RFH2.TEKIYO_START_DT  ");
        sql.append("                ) RFH  ");
        sql.append("                ON   ");
        sql.append("                    WTP.COMP_CD                         = RFH.RFH_COMP_CD  ");
        sql.append("                AND WTP.TENPO_CD = RFH.RFH_TENPO_CD  ");
        sql.append("                AND WTP.TANPIN_CD                       = RFH.RFH_SYOHIN_CD  ");
        sql.append("            LEFT JOIN   ");
        // ＜SUB店DPTマスタ＞
        sql.append("                (  ");
        sql.append("                SELECT   ");
        sql.append("                     RFTB1.COMP_CD              AS RFTB_COMP_CD  ");
        sql.append("                    ,RFTB1.TENPO_CD             AS RFTB_TENPO_CD  ");
        sql.append("                    ,RFTB1.BUNRUI1_CD           AS RFTB_BUNRUI1_CD  ");
        sql.append("                    ,RFTB1.TANAOROSHI_GENKA_KB  AS RFTB_TANAOROSHI_GENKA_KB  ");
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
        sql.append("                WTP.COMP_CD                         = RFTB.RFTB_COMP_CD     AND   ");
        sql.append("                WTP.TENPO_CD = RFTB.RFTB_TENPO_CD    AND   ");
        sql.append("                RFS.RFS_BUNRUI1_CD                  = RFTB.RFTB_BUNRUI1_CD   ");
        sql.append("            LEFT JOIN   ");
        // ＜SUB商品体系マスタ＞
        sql.append("                (  ");
        sql.append("                SELECT   ");
        sql.append("                     RFST1.COMP_CD          AS RFST_COMP_CD  ");
        sql.append("                    ,RFST1.BUNRUI5_CD       AS RFST_BUNRUI5_CD  ");
        sql.append("                    ,RFST1.DAIBUNRUI2_CD    AS RFST_DAIBUNRUI2_CD  ");
        sql.append("                FROM   ");
        sql.append("                    R_FI_SYOHIN_TAIKEI RFST1   ");
        sql.append("                    INNER JOIN   ");
        sql.append("                        (  ");
        sql.append("                        SELECT   ");
        sql.append("                             RFST.COMP_CD  ");
        sql.append("                            ,RFST.BUNRUI5_CD  ");
        sql.append("                            ,MAX(RFST.YUKO_DT) AS YUKO_DT  ");
        sql.append("                        FROM  ");
        sql.append("                            R_FI_SYOHIN_TAIKEI RFST  ");
        sql.append("                        WHERE  ");
        sql.append("                            RFST.COMP_CD  = ?   AND   ");
        sql.append("                            RFST.YUKO_DT <= ?  ");
        sql.append("                        GROUP BY   ");
        sql.append("                             RFST.COMP_CD  ");
        sql.append("                            ,RFST.BUNRUI5_CD  ");
        sql.append("                        ) RFST2  ");
        sql.append("                    ON  ");
        sql.append("                        RFST1.COMP_CD       = RFST2.COMP_CD     AND   ");
        sql.append("                        RFST1.BUNRUI5_CD    = RFST2.BUNRUI5_CD  AND   ");
        sql.append("                        RFST1.YUKO_DT       = RFST2.YUKO_DT  ");
        sql.append("                ) RFST   ");
        sql.append("            ON   ");
        sql.append("                WTP.COMP_CD         = RFST.RFST_COMP_CD     AND   ");
        sql.append("                RFS.RFS_BUNRUI5_CD  = RFST.RFST_BUNRUI5_CD  ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");
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
            DaoIf dao = new TanpinSeisanMstJohoSyutokuDao();
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
