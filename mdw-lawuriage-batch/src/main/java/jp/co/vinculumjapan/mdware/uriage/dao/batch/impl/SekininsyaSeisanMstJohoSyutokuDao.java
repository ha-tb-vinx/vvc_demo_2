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
 * <p>タイトル: SekininsyaSeisanMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理(責任者精算)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2014.12.03) chou グローバル化対応 通貨対応
 * @Version 1.01 (2016.03.09) TU.TD 設計書No.607(#1161) FIVImart対応
 * @Version 1.02 (2016.04.11) T.Kamei  FIVImart対応 計上日の期間判定処理の修正
 * @Version 1.03 (2016.05.10) monden  S03対応
 * @Version 1.04 (2016.12.13) VINX J.Endo FIVI対応 キャッシャーＩＤ不具合対応 #3304
 * @Version 1.05 (2016.12.14) VINX T.Kamei FIVI対応(#3306)
 * @Version 1.06 (2017.04.10) VINX X.Liu FIVI対応(#4553)
 * @Version 1.07 (2017.07.05) VINX J.Endo FIVI対応(#5040)
 * @Version 1.08 (2017.08.31) VINX N.Katou FIVI対応 (#5840)
 *
 */
public class SekininsyaSeisanMstJohoSyutokuDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システムコントロールより責任者精算取引コード取得
    private static final String SEKININSYA_TORIHIKI_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.SEKININSYA_TORIHIKI_CD);

    /** 客数MAX値 */
    private static final long KYAKU_QT_MAXNUM = 99999999999L;
    /** 点数/回数MAX値 */
    private static final long SU_MAXNUM = 999999L;
    /** 金額MAX値 */
    //#5040 MOD J.Endo 2017.07.05 (S)
    //private static final long KINGAKU_MAXNUM = 9999999999999L;
    private static final String KINGAKU_MAXNUM = "999999999999999.99";
    //#5040 MOD J.Endo 2017.07.05 (E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "マスタ情報取得処理（責任者精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "有効責任者精算ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_SEKININSYA_SEISAN";

    // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(S)
    ///** 責任者コード：開始位置 */
    //private static final String CHECKER_CD_START_COLUMN = "2";
    ///** 責任者コード：桁数 */
    //private static final String CHECKER_CD_LENGTH = "7";
    ///** 責任者コード：エラー値 */
    //private static final String CHECKER_CD_ERR_CHECK = "0";
    // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(E)

    // 2016/05/10 VINX #S03対応（S)
    /** ﾊﾞｯﾁID */
    private static final String BATCH_ID = "URIB012240";
    // 2016/05/10 VINX #S03対応（E)

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
        // 2016/05/10 VINX #S03対応（S)
        // ユーザID(DB登録用)
//        String userId = invoker.getUserId();
        // 2016/05/10 VINX #S03対応（E)

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        //
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        // 会計年月日
        String kaikeiDt = null;
        // 当月度会計年月取得DAO
        TouKaikeiNengetsuSelectDaoInputBean inputBean = new TouKaikeiNengetsuSelectDaoInputBean();
        // 法人コード
        inputBean.setCompCd(COMP_CD);

        // 当月度会計年月取得DAOの実行
        TouKaikeiNengetsuSelectDao touKaikeiNengetsu = new TouKaikeiNengetsuSelectDao();
        TouKaikeiNengetsuSelectDaoOutputBean outputBean = new TouKaikeiNengetsuSelectDaoOutputBean();

        // 入力ビーンをセット
        touKaikeiNengetsu.setInputObject(inputBean);

        invoker.InvokeDao(touKaikeiNengetsu);

        // 出力ビーンをセット
        outputBean = (TouKaikeiNengetsuSelectDaoOutputBean) touKaikeiNengetsu.getOutputObject();

        kaikeiDt = outputBean.getUserKaikeiYr() + outputBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)

        int insertCount = 0;
        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

           String dbServerTime = FiResorceUtility.getDBServerTime();
            // TMP責任者精算データから有効責任者取引精算データワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTmpSekininsyaSeisanInsertSql());

            // No.607 URIB012240 Mod 2016.03.09 TU.TD (S)
            /*preparedStatementExIns.setString(1, ErrorKbDictionary.ERROR_01.getCode());  // 01：店舗エラー
            preparedStatementExIns.setString(2, BATCH_DT);
            preparedStatementExIns.setString(3, ErrorKbDictionary.ERROR_04.getCode());  // 04：過去日エラー
            preparedStatementExIns.setLong(4, KYAKU_QT_MAXNUM);
            preparedStatementExIns.setString(5, ErrorKbDictionary.ERROR_05.getCode());  // 05：桁数エラー
            preparedStatementExIns.setLong(6, SU_MAXNUM);
            preparedStatementExIns.setString(7, ErrorKbDictionary.ERROR_05.getCode());  // 05：桁数エラー
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(8, KINGAKU_MAXNUM);
            preparedStatementExIns.setDouble(8, KINGAKU_MAXNUM);
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(9, ErrorKbDictionary.ERROR_05.getCode());  // 05：桁数エラー
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(10, KINGAKU_MAXNUM);
            preparedStatementExIns.setDouble(10, KINGAKU_MAXNUM);
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(11, ErrorKbDictionary.ERROR_05.getCode()); // 05：桁数エラー
            //2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応(S)
            preparedStatementExIns.setString(12, CHECKER_CD_ERR_CHECK);
            preparedStatementExIns.setString(13, ErrorKbDictionary.ERROR_07.getCode()); // 07：責任者コードエラー
            //2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応(E)
            preparedStatementExIns.setString(14, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(15, userId);
            preparedStatementExIns.setString(16, dbServerTime);
            preparedStatementExIns.setString(17, userId);
            preparedStatementExIns.setString(18, dbServerTime);

            preparedStatementExIns.setString(19, COMP_CD);
            preparedStatementExIns.setString(20, BATCH_DT);
            preparedStatementExIns.setString(21, BATCH_DT);

            preparedStatementExIns.setString(22, COMP_CD);
            preparedStatementExIns.setString(23, SEKININSYA_TORIHIKI_CD);*/

            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (S)
            int intI = 1;
            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (E)

            // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_01.getCode());  // 01：店舗エラー
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_04.getCode());  // 04：過去日エラー
            preparedStatementExIns.setLong(intI++, KYAKU_QT_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());  // 05：桁数エラー
            preparedStatementExIns.setLong(intI++, SU_MAXNUM);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());  // 05：桁数エラー
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(8, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.05 (S)
            //preparedStatementExIns.setDouble(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.05 (E)
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode());  // 05：桁数エラー
            //2014/12/03 chou グローバル化対応 通貨対応 MOD START
            //preparedStatementExIns.setLong(10, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.05 (S)
            //preparedStatementExIns.setDouble(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.05 (E)
            //2014/12/03 chou グローバル化対応 通貨対応 MOD END
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode()); // 05：桁数エラー
            // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(S)
            ////2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応(S)
            //preparedStatementExIns.setString(intI++, CHECKER_CD_ERR_CHECK);
            //preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_07.getCode()); // 07：責任者コードエラー
            ////2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応(E)
            // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(E)
            // 2017/08/31 VINX N.Katou #5840 (S)
//            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_09.getCode()); // 09：取込済エラー
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());
            // 2016/05/10 VINX #S03対応（S)
//            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, BATCH_ID);
            // 2016/05/10 VINX #S03対応（E)
            preparedStatementExIns.setString(intI++, dbServerTime);
            // 2016/05/10 VINX #S03対応（S)
//            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, BATCH_ID);
            // 2016/05/10 VINX #S03対応（E)
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
            preparedStatementExIns.setString(intI++, SEKININSYA_TORIHIKI_CD);
            // No.607 URIB012240 Mod 2016.03.09 TU.TD (E)
            // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // APPEND INSERTした内容確定する必要があるのでcommitを行う
            invoker.getDataBase().commit();

            // 責任者精算取引なしレコードを、有効責任者精算ワークに登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getSekininsyaNoneTorihikiInsertSql());

            // No.607 URIB012240 Mod 2016.03.09 TU.TD (S)
            /*preparedStatementExIns.setString(1, SEKININSYA_TORIHIKI_CD);
            preparedStatementExIns.setString(2, ErrorKbDictionary.ERROR_01.getCode()); // 01：店舗エラー
            preparedStatementExIns.setString(3, BATCH_DT);
            preparedStatementExIns.setString(4, ErrorKbDictionary.ERROR_04.getCode()); // 04：過去日エラー
            //2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応(S)
            preparedStatementExIns.setString(5, CHECKER_CD_ERR_CHECK);
            preparedStatementExIns.setString(6, ErrorKbDictionary.ERROR_07.getCode()); // 07：責任者コードエラー
            //2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応(E)
            preparedStatementExIns.setString(7, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(8, userId);
            preparedStatementExIns.setString(9, dbServerTime);
            preparedStatementExIns.setString(10, userId);
            preparedStatementExIns.setString(11, dbServerTime);

            preparedStatementExIns.setString(12, COMP_CD);
            preparedStatementExIns.setString(13, BATCH_DT);
            preparedStatementExIns.setString(14, BATCH_DT);
            preparedStatementExIns.setString(15, COMP_CD);
            preparedStatementExIns.setString(16, SEKININSYA_TORIHIKI_CD);
            preparedStatementExIns.setString(17, COMP_CD);*/

            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (S)
            intI = 1;
            // S35_催事店舗用レジ対応 URIB012210 ADD 2016.04.11 T.Kamei (E)

            // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
            preparedStatementExIns.setString(intI++, SEKININSYA_TORIHIKI_CD);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_01.getCode()); // 01：店舗エラー
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_04.getCode()); // 04：過去日エラー
            // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(S)
            ////2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応(S)
            //preparedStatementExIns.setString(intI++, CHECKER_CD_ERR_CHECK);
            //preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_07.getCode()); // 07：責任者コードエラー
            ////2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応(E)
            // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(E)
            // 2017/08/31 VINX N.Katou #5840 (S)
//            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_09.getCode()); // 09：取込済エラー
            // 2017/08/31 VINX N.Katou #5840 (E)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());
            // 2016/05/10 VINX #S03対応（S)
//          preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, BATCH_ID);
            // 2016/05/10 VINX #S03対応（E)
            preparedStatementExIns.setString(intI++, dbServerTime);
            // 2016/05/10 VINX #S03対応（S)
//          preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, BATCH_ID);
            // 2016/05/10 VINX #S03対応（E)
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
            preparedStatementExIns.setString(intI++, SEKININSYA_TORIHIKI_CD);
            preparedStatementExIns.setString(intI++, COMP_CD);
            // No.607 URIB012240 Mod 2016.03.09 TU.TD (E)
            // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "（責任者精算取引なしレコード）を追加しました。");

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
     * TMP責任者精算データから有効責任者精算ワークへ登録するSQLを取得する
     *
     * @return 有効レジ別取引精算データ登録クエリー
     */
    private String getTmpSekininsyaSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_SEKININSYA_SEISAN( ");
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
        // 2016/05/10 VINX #S03対応(S)
//        sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD) ");
        // 2016/05/10 VINX #S03対応(E)
        sql.append("SELECT  ");
        sql.append("     COMP_CD  ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        // 2016/12/13 VINX J.Endo FIVI対応 #3304 MOD(S)
        //sql.append("    ,SUBSTR(CHECKER_CD, " + CHECKER_CD_START_COLUMN + ", " + CHECKER_CD_LENGTH + ") ");
        sql.append("    ,CHECKER_CD ");
        // 2016/12/13 VINX J.Endo FIVI対応 #3304 MOD(E)
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,SUM(KYAKU_QT) ");
        sql.append("    ,SUM(TEN_KAISUU_QT) ");
        sql.append("    ,SUM(KINGAKU_VL) ");
        sql.append("    ,SUM(NEBIKI_VL) ");
        sql.append("    ,CASE ");
        sql.append("        WHEN MAX(RT_TENPO_CD) IS NULL     THEN ? "); // 01：店舗エラー
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.607 URIB012240 Mod 2016.03.09 TU.TD (S)
        //sql.append("        WHEN KEIJO_DT                 < ? THEN ? "); // 04：過去日エラー
//        sql.append("        WHEN KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT THEN ? "); // 04：過去日エラー
        sql.append("        WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT)  THEN ? "); // 04：過去日エラー
        // No.607 URIB012240 Mod 2016.03.09 TU.TD (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("        WHEN SUM(KYAKU_QT)            > ? THEN ? "); // 05：桁数エラー
        sql.append("        WHEN SUM(TEN_KAISUU_QT)       > ? THEN ? "); // 05：桁数エラー
        sql.append("        WHEN SUM(KINGAKU_VL)          > ? THEN ? "); // 05：桁数エラー
        sql.append("        WHEN SUM(NEBIKI_VL)           > ? THEN ? "); // 05：桁数エラー
        // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(S)
        //sql.append("        WHEN SUBSTRB(CHECKER_CD,1,1) <> ? THEN ? "); // 07：責任者コードエラー  2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応
        // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        // #3306 2016.12.14 T.Kamei Mod (S)
        //sql.append("        WHEN DTSS_SEISAN_STATE_FG = '1' THEN ? "); // 09：取込済エラー
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG <> '0' THEN ? "); // 09：取込済エラー
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG <> '0' THEN ? "); // 09：取込済エラー
        // 2017/08/31 VINX N.Katou #5840 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3306 2016.12.14 T.Kamei Mod (E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016/05/10 VINX #S03対応(S)
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX #S03対応(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT   ");
        sql.append("         COMP_CD ");
        sql.append("        ,SUBSTR(TSS.EIGYO_DT,1, 4) || SUBSTR(TSS.EIGYO_DT,6, 2) || SUBSTR(TSS.EIGYO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("        ,TSS.TENPO_CD AS TENPO_CD ");
        sql.append("        ,CHECKER_CD ");
        sql.append("        ,JIKAN_TM ");
        sql.append("        ,TORIHIKI_CD ");
        sql.append("        ,TYPE_KB ");
        sql.append("        ,TO_NUMBER(KYAKU_QT) AS KYAKU_QT ");
        sql.append("        ,TO_NUMBER(TEN_KAISUU_QT) AS TEN_KAISUU_QT ");
        sql.append("        ,TO_NUMBER(KINGAKU_VL) AS KINGAKU_VL ");
        sql.append("        ,TO_NUMBER(NEBIKI_VL) AS NEBIKI_VL ");
        sql.append("        ,RT.RT_TENPO_CD ");
        // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        sql.append("        ,RT.SEISAN_ST_DT ");
        sql.append("        ,RT.SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        ,DTSS.DTSS_SEISAN_STATE_FG ");
        sql.append("        ,DTSS.DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // 2016/05/10 VINX #S03対応(S)
        sql.append("        ,TSS.SHIHARAI_SYUBETSU_CD ");
        sql.append("        ,TSS.SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX #S03対応(E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        sql.append("    FROM   ");
        sql.append("        TMP_SEKININSYA_SEISAN TSS   ");
        sql.append("        LEFT JOIN   ");
        // ＜SUB店舗マスタ＞
        sql.append("            (  ");
        sql.append("                SELECT   ");
        sql.append("                    RT.TENPO_CD AS RT_TENPO_CD ");
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
//        sql.append("                    , COALESCE( ");
//        sql.append("                        RT.SEISAN_ST_DT, ");
//        sql.append("                        CASE ");
//        sql.append("                            WHEN RT.KAITEN_DT > ? THEN RT.KAITEN_DT ");
//        sql.append("                            ELSE ? ");
//        sql.append("                        END) AS SEISAN_ST_DT ");
//        sql.append("                    , COALESCE(RT.SEISAN_ED_DT,RT.ZAIMU_END_DT) AS SEISAN_ED_DT ");
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
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("                FROM   ");
        sql.append("                    R_TENPO RT  ");
        sql.append("                WHERE  ");
        sql.append("                    RT.HOJIN_CD      = ?      AND   ");
        sql.append("                    RT.TENPO_KB      = '1'    AND   ");
        sql.append("                    RT.KAITEN_DT    <= ?      AND   ");
        sql.append("                    RT.ZAIMU_END_DT >= ?      AND   ");
        sql.append("                    RT.DELETE_FG     = '0'  ");
        sql.append("            ) RT  ");
        sql.append("        ON   ");
        sql.append("            TSS.TENPO_CD = RT.RT_TENPO_CD   ");
        // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        sql.append("        LEFT JOIN ");
        sql.append("            ( ");
        sql.append("                SELECT ");
        // #3306 2016.12.14 T.Kamei Mod (S)
        sql.append("                    COMP_CD               AS DTSS_COMP_CD  ");
        sql.append("                    ,KEIJO_DT               AS DTSS_KEIJO_DT  ");
        sql.append("                    ,TENPO_CD AS DTSS_TENPO_CD, ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("                    SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("                    ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("                FROM ");
        sql.append("                    DT_TEN_SEISAN_STATE ");
        //sql.append("                WHERE ");
        //sql.append("                    COMP_CD = ? AND ");
        //sql.append("                    KEIJO_DT = ? ");
        sql.append("            ) DTSS ");
        sql.append("        ON ");
        sql.append("            DTSS.DTSS_COMP_CD = TSS.COMP_CD AND ");
        sql.append("            DTSS.DTSS_KEIJO_DT = SUBSTR(TSS.EIGYO_DT,1, 4) || SUBSTR(TSS.EIGYO_DT,6, 2) || SUBSTR(TSS.EIGYO_DT,9, 2) AND ");
        sql.append("            DTSS.DTSS_TENPO_CD = TSS.TENPO_CD   ");
        //sql.append("            TSS.TENPO_CD = DTSS.DTSS_TENPO_CD ");
        // #3306 2016.12.14 T.Kamei Mod (E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        sql.append("    ) ");
        sql.append("WHERE ");
        sql.append("    COMP_CD     = ?     AND ");
        sql.append("    TORIHIKI_CD = ? ");
        sql.append("GROUP BY  ");
        sql.append("     COMP_CD  ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,TORIHIKI_CD ");
        // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        sql.append("    ,SEISAN_ST_DT ");
        sql.append("    ,SEISAN_ED_DT ");
        //#4533 Mod X.Liu 2017.04.10 (S)
//        sql.append("    ,DTSS_SEISAN_STATE_FG ");
        sql.append("    ,DTSS_ISAN_SEISAN_STATE_FG ");
        //#4533 Mod X.Liu 2017.04.10 (E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        // 2016/05/10 VINX #S03対応(S)
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX #S03対応(E)

        return sql.toString();
    }

    /**
     * 責任者精算取引なしレコードを、有効責任者精算ワークに登録するSQLを取得する
     *
     * @return 有効レジ別取引精算データ登録クエリー
     */
    private String getSekininsyaNoneTorihikiInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_SEKININSYA_SEISAN( ");
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
        // 2016/05/10 VINX #S03対応(S)
//        sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD) ");
        // 2016/05/10 VINX #S03対応(E)
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        // 2016/12/13 VINX J.Endo FIVI対応 #3304 MOD(S)
        //sql.append("    ,SUBSTR(CHECKER_CD, " + CHECKER_CD_START_COLUMN + ", " + CHECKER_CD_LENGTH + ") ");
        sql.append("    ,CHECKER_CD ");
        // 2016/12/13 VINX J.Endo FIVI対応 #3304 MOD(E)
        sql.append("    ,? AS TORIHIKI_CD ");
        sql.append("    ,0 AS KYAKU_QT ");
        sql.append("    ,0 AS TEN_KAISUU_QT ");
        sql.append("    ,0 AS KINGAKU_VL ");
        sql.append("    ,0 AS NEBIKI_VL ");
        sql.append("    ,CASE ");
        sql.append("        WHEN RT_TENPO_CD IS NULL          THEN ? "); // 01：店舗エラー
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
        // No.607 URIB012240 Mod 2016.03.09 TU.TD (S)
        //sql.append("        WHEN KEIJO_DT                 < ? THEN ? "); // 04：過去日エラー
//        sql.append("        WHEN KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT THEN ? "); // 04：過去日エラー
        sql.append("        WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (KEIJO_DT < SEISAN_ST_DT OR SEISAN_ED_DT < KEIJO_DT)  THEN ? "); // 04：過去日エラー
        // No.607 URIB012240 Mod 2016.03.09 TU.TD (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(S)
        //sql.append("        WHEN SUBSTRB(CHECKER_CD,1,1) <> ? THEN ? "); // 07：責任者コードエラー  2014.04.15 M.Ayukawa [シス0196]責任者コード重複対応
        // 2016/12/13 VINX J.Endo FIVI対応 #3304 DEL(E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        // #3306 2016.12.14 T.Kamei Mod (S)
        //sql.append("        WHEN DTSS_SEISAN_STATE_FG = '1' THEN ? "); // 09：取込済エラー
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG <> '0' THEN ? "); // 09：取込済エラー
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG <> '0' THEN ? "); // 09：取込済エラー
        // 2017/08/31 VINX N.Katou #5840 (E)
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3306 2016.12.14 T.Kamei Mod (E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016/05/10 VINX #S03対応(S)
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX #S03対応(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("         TSS.COMP_CD ");
        sql.append("        ,SUBSTR(TSS.EIGYO_DT,1, 4) || SUBSTR(TSS.EIGYO_DT,6, 2) || SUBSTR(TSS.EIGYO_DT,9, 2)  AS KEIJO_DT ");
        sql.append("        ,TSS.TENPO_CD AS TENPO_CD ");
        sql.append("        ,TSS.CHECKER_CD ");
        sql.append("        ,RT.RT_TENPO_CD ");
     // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        sql.append("        ,RT.SEISAN_ST_DT ");
        sql.append("        ,RT.SEISAN_ED_DT ");
        //#4533 Mod X.Liu 2017.04.10 (S)
//        sql.append("        ,DTSS.DTSS_SEISAN_STATE_FG ");
        sql.append("        ,DTSS.DTSS_ISAN_SEISAN_STATE_FG ");
        //#4533 Mod X.Liu 2017.04.10 (E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        // 2016/05/10 VINX #S03対応(S)
        sql.append("    ,TSS.SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,TSS.SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX #S03対応(E)
        sql.append("    FROM ");
        sql.append("        TMP_SEKININSYA_SEISAN TSS ");
        sql.append("    LEFT JOIN   ");
        // ＜SUB店舗マスタ＞
        sql.append("        (  ");
        sql.append("            SELECT   ");
        sql.append("                RT.TENPO_CD AS RT_TENPO_CD ");
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (S)
     // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
//        sql.append("                , COALESCE( ");
//        sql.append("                    RT.SEISAN_ST_DT, ");
//        sql.append("                    CASE ");
//        sql.append("                        WHEN RT.KAITEN_DT > ? THEN RT.KAITEN_DT ");
//        sql.append("                        ELSE ? ");
//        sql.append("                    END) AS SEISAN_ST_DT ");
//        sql.append("                , COALESCE(RT.SEISAN_ED_DT,RT.ZAIMU_END_DT) AS SEISAN_ED_DT ");
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
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        // S35_催事店舗用レジ対応 URIB012210 MOD 2016.04.11 T.Kamei (E)
        sql.append("            FROM   ");
        sql.append("                R_TENPO RT  ");
        sql.append("            WHERE  ");
        sql.append("                RT.HOJIN_CD      = ?    AND   ");
        sql.append("                RT.TENPO_KB      = '1'  AND   ");
        sql.append("                RT.KAITEN_DT    <= ?    AND   ");
        sql.append("                RT.ZAIMU_END_DT >= ?    AND   ");
        sql.append("                RT.DELETE_FG     = '0'  ");
        sql.append("        ) RT  ");
        sql.append("        ON   ");
        sql.append("            TSS.TENPO_CD  = RT.RT_TENPO_CD   ");
     // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        sql.append("        LEFT JOIN ");
        sql.append("            ( ");
        sql.append("                SELECT ");
        // #3306 2016.12.14 T.Kamei Mod (S)
        sql.append("                    COMP_CD               AS DTSS_COMP_CD  ");
        sql.append("                    ,KEIJO_DT               AS DTSS_KEIJO_DT  ");
        sql.append("                    ,TENPO_CD AS DTSS_TENPO_CD, ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("                    SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("                    ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("                FROM ");
        sql.append("                    DT_TEN_SEISAN_STATE ");
        //sql.append("                WHERE ");
        //sql.append("                    COMP_CD = ? AND ");
        //sql.append("                    KEIJO_DT = ? ");
        sql.append("            ) DTSS ");
        sql.append("        ON ");
        sql.append("            DTSS.DTSS_COMP_CD = TSS.COMP_CD AND ");
        sql.append("            DTSS.DTSS_KEIJO_DT = SUBSTR(TSS.EIGYO_DT,1, 4) || SUBSTR(TSS.EIGYO_DT,6, 2) || SUBSTR(TSS.EIGYO_DT,9, 2) AND ");
        sql.append("            DTSS.DTSS_TENPO_CD = TSS.TENPO_CD   ");
        //sql.append("            TSS.TENPO_CD = DTSS.DTSS_TENPO_CD ");
        // #3306 2016.12.14 T.Kamei Mod (E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        sql.append("    WHERE ");
        sql.append("        TSS.COMP_CD = ? AND ");
        sql.append("        NOT EXISTS ");
        sql.append("            ( ");
        sql.append("            SELECT ");
        sql.append("                 TSS_EX.COMP_CD ");
        sql.append("                ,TSS_EX.EIGYO_DT ");
        sql.append("                ,TSS_EX.TENPO_CD ");
        sql.append("                ,TSS_EX.CHECKER_CD ");
        sql.append("            FROM ");
        sql.append("                TMP_SEKININSYA_SEISAN TSS_EX ");
        sql.append("            WHERE ");
        sql.append("                TSS_EX.TORIHIKI_CD  = ?                     AND ");
        sql.append("                TSS.COMP_CD         = TSS_EX.COMP_CD        AND ");
        sql.append("                TSS.EIGYO_DT        = TSS_EX.EIGYO_DT       AND ");
        sql.append("                TSS.TENPO_CD        = TSS_EX.TENPO_CD       AND ");
        sql.append("                TSS.CHECKER_CD      = TSS_EX.CHECKER_CD ");
        sql.append("            ) ");
        sql.append("    GROUP BY  ");
        sql.append("         TSS.COMP_CD ");
        sql.append("        ,TSS.EIGYO_DT ");
        sql.append("        ,TSS.TENPO_CD ");
        sql.append("        ,TSS.CHECKER_CD ");
        sql.append("        ,RT.RT_TENPO_CD ");
     // No.607 URIB012240 Add 2016.03.09 TU.TD (S)
        sql.append("        ,SEISAN_ST_DT ");
        sql.append("        ,SEISAN_ED_DT ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        ,DTSS_SEISAN_STATE_FG ");
        sql.append("        ,DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // No.607 URIB012240 Add 2016.03.09 TU.TD (E)
        // 2016/05/10 VINX #S03対応(S)
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        // 2016/05/10 VINX #S03対応(E)
        sql.append("    ) ");
        sql.append("WHERE  ");
        sql.append("    COMP_CD = ? ");

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
            DaoIf dao = new SekininsyaSeisanMstJohoSyutokuDao();
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
