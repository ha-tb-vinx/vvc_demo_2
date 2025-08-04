package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: KaikeiSeisanUriageDataTorikomiDao クラス</p>
 * <p>説明　　:「レジ別取引精算データ」より、「会計精算売上集計前ワーク」へ挿入する</p>
 * <p>著作権　: Copyright (c) 2009</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author   J.Lu
 * @version 1.00 (2009.02.19) 初版作成
 * @Version 3.00 (2013.10.08) Y.Tominaga [CUS00057] ランドローム様対応　SQLLoaderを使用した取込処理から、レジ別取引精算データより作成する処理へ変更対応
 * @Version 3.01 (2016/05/17) S_MDware-G_FIVIマート様開発 VINX k.Hyo
 * @Version 3.02 (2016/11/14) S_MDware-G_FIVIマート様開発 VINX Y.Itaki
 * @Version 3.03 (2017/04/27) S_MDware-G_FIVIマート様開発 VINX J.Endo #4770
 *
 */
public class KaikeiSeisanUriageDataTorikomiDao implements DaoIf {

    // 2016/05/17 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
    // 以下は既存ソース
    // バッチ名称
    //private static final String BATCH_NAME = "会計精算売上データ取込処理";

// 2013.10.08 Y.Tominaga [CUS00057] SQLLoaderを使用した取込処理から、レジ別取引精算データより作成する処理へ変更対応（S)
    // プロパティファイルより法人コード取得
    //private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // プロパティファイルよりバッチ日付取得
    //private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    // POSターミナル番号
    //private static final String POS_NB = "0000";

    //private static final String DAY_GROSS_VL = "DAY_GROSS_VL";
    //private static final String DAY_NET_VL = "DAY_NET_VL";
    //private static final String KEI_1_VL = "KEI_1_VL";
    //private static final String KEI_1_COUNT_QT = "KEI_1_COUNT_QT";
    //private static final String KEI_2_VL = "KEI_2_VL";
    //private static final String KEI_2_COUNT_QT = "KEI_2_COUNT_QT";
    //private static final String KEI_3_VL = "KEI_3_VL";
    //private static final String KEI_3_COUNT_QT = "KEI_3_COUNT_QT";
    //private static final String KEI_4_VL = "KEI_4_VL";
    //private static final String KEI_4_COUNT_QT = "KEI_4_COUNT_QT";
    //private static final String KEI_5_VL = "KEI_5_VL";
    //private static final String KEI_5_COUNT_QT = "KEI_5_COUNT_QT";
    //private static final String KEI_6_VL = "KEI_6_VL";
    //private static final String KEI_6_COUNT_QT = "KEI_6_COUNT_QT";
    //private static final String KEI_7_VL = "KEI_7_VL";
    //private static final String KEI_7_COUNT_QT = "KEI_7_COUNT_QT";
    //private static final String KEI_8_VL = "KEI_8_VL";
    //private static final String KEI_8_COUNT_QT = "KEI_8_COUNT_QT";
    //private static final String KEI_9_VL = "KEI_9_VL";
    //private static final String KEI_9_COUNT_QT = "KEI_9_COUNT_QT";
    //private static final String KEI_10_VL = "KEI_10_VL";
    //private static final String KEI_10_COUNT_QT = "KEI_10_COUNT_QT";
    //private static final String KYAKU_QT = "KYAKU_QT";
    //private static final String SOTOZEI_TAISYO_VL = "SOTOZEI_TAISYO_VL";
    //private static final String UCHIZEI_TAISYO_VL = "UCHIZEI_TAISYO_VL";
    //private static final String HIKAZEI_TAISYO_VL = "HIKAZEI_TAISYO_VL";
    //private static final String SOTOZEI_VL = "SOTOZEI_VL";
    //private static final String UCHIZEI_VL = "UCHIZEI_VL";

    /**
     * 会計精算売上データ取込処理を行う
     * @param DaoInvokerIf invoker
     */
    //public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        //String strUserId = invoker.getUserId() + " " + BATCH_NAME;

        // オブジェクトを初期化する
        //PreparedStatementEx psWkKaikeiSeisanTrun = null;
        //PreparedStatementEx psWkKaikeiSeisanTorikomi = null;

        // 開始ログを出力する
        //invoker.infoLog(strUserId + "　：　会計精算売上データ取込処理を開始します。");

        // 会計精算売上ワークをトランケート
        //psWkKaikeiSeisanTrun = invoker.getDataBase().prepareStatement(wkKaikeiSeisanTrun());
        //psWkKaikeiSeisanTrun.executeUpdate();

        // 会計精算売上ワーク追加ログ開始を出力する
        //invoker.infoLog(strUserId + "　：　レジ別取引精算データより、会計精算売上ワークへのデータ取込処理を開始します。");

        // 会計精算売上ワークを作成する。
        //int kensu = 0;
        //int index = 1;
        //psWkKaikeiSeisanTorikomi = invoker.getDataBase().prepareStatement(wkKaikeiSeisanTorikomi());

        //psWkKaikeiSeisanTorikomi.setString(index++, POS_NB);
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(DAY_GROSS_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(DAY_NET_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_1_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_1_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_2_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_2_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_3_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_3_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_4_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_4_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_5_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_5_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_6_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_6_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_7_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_7_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_8_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_8_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_9_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_9_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_10_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KEI_10_COUNT_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(KYAKU_QT));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(SOTOZEI_TAISYO_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(UCHIZEI_TAISYO_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(HIKAZEI_TAISYO_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(SOTOZEI_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, getToriMap(UCHIZEI_VL));
        //psWkKaikeiSeisanTorikomi.setString(index++, COMP_CD);
        //psWkKaikeiSeisanTorikomi.setString(index++, BATCH_DT);

        //kensu = psWkKaikeiSeisanTorikomi.executeUpdate();

        //invoker.infoLog(strUserId + "　：　会計精算売上ワークへのデータ取込処理を終了します。取込件数は" + kensu + "件です。");


        // 終了ログを出力
        //invoker.infoLog(strUserId + "　：　会計精算売上データ取込処理を終了します。");
    //}


    /**
     * 会計精算売上データの取込処理
     * レジ別取引精算データより取り込む。
     * @return
     */
    //private String wkKaikeiSeisanTorikomi() {

        //StringBuffer sb = new StringBuffer();

        //sb.append(" INSERT /*+ APPEND */ INTO");
        //sb.append("     WK_KAIKEI_SEISAN");
        //sb.append("     (");
        //sb.append("     COMP_CD");
        //sb.append("     ,KEIJO_DT");
        //sb.append("     ,TENPO_CD");
        //sb.append("     ,POS_NB");
        //sb.append("     ,DAY_GROSS_VL");
        //sb.append("     ,DAY_NET_VL");
        //sb.append("     ,KEI_1_VL");
        //sb.append("     ,KEI_1_COUNT_QT");
        //sb.append("     ,KEI_2_VL");
        //sb.append("     ,KEI_2_COUNT_QT");
        //sb.append("     ,KEI_3_VL");
        //sb.append("     ,KEI_3_COUNT_QT");
        //sb.append("     ,KEI_4_VL");
        //sb.append("     ,KEI_4_COUNT_QT");
        //sb.append("     ,KEI_5_VL");
        //sb.append("     ,KEI_5_COUNT_QT");
        //sb.append("     ,KEI_6_VL");
        //sb.append("     ,KEI_6_COUNT_QT");
        //sb.append("     ,KEI_7_VL");
        //sb.append("     ,KEI_7_COUNT_QT");
        //sb.append("     ,KEI_8_VL");
        //sb.append("     ,KEI_8_COUNT_QT");
        //sb.append("     ,KEI_9_VL");
        //sb.append("     ,KEI_9_COUNT_QT");
        //sb.append("     ,KEI_10_VL");
        //sb.append("     ,KEI_10_COUNT_QT");
        //sb.append("     ,KYAKU_QT");
        //sb.append("     ,SOTOZEI_TAISYO_VL");
        //sb.append("     ,UCHIZEI_TAISYO_VL");
        //sb.append("     ,HIKAZEI_TAISYO_VL");
        //sb.append("     ,SOTOZEI_VL");
        //sb.append("     ,UCHIZEI_VL");
        //sb.append("     )");
        //sb.append("     SELECT");
        //sb.append("         DRTS.COMP_CD");
        //sb.append("         ,DRTS.KEIJO_DT");
        //sb.append("         ,DRTS.TENPO_CD");
        //sb.append("         ,?");                                                  // POS_NB
        //sb.append("         ,SUM ( CASE");                                         // DAY_GROSS_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(DAY_GROSS_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // DAY_NET_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(DAY_NET_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_1_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_1_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_1_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_1_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_2_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_2_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_2_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_2_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_3_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_3_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_3_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_3_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_4_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_4_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_4_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_4_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_5_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_5_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_5_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_5_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_6_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_6_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_6_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_6_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_7_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_7_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_7_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_7_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_8_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_8_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_8_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_8_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_9_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_9_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_9_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_9_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_10_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_10_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KEI_10_COUNT_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KEI_10_COUNT_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // KYAKU_QT
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(KYAKU_QT));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // SOTOZEI_TAISYO_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(SOTOZEI_TAISYO_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // UCHIZEI_TAISYO_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(UCHIZEI_TAISYO_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // HIKAZEI_TAISYO_VL
        ///sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(HIKAZEI_TAISYO_VL));
        ///sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // SOTOZEI_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(SOTOZEI_VL));
        //sb.append("                 END )");
        //sb.append("         ,SUM ( CASE");                                         // UCHIZEI_VL
        //sb.append("                 WHEN DRTS.TORIHIKI_CD = ? THEN " + getSetteiMap(UCHIZEI_VL));
        //sb.append("                 END )");
        //sb.append("     FROM");
        //sb.append("         DT_REGI_TORIHIKI_SEISAN DRTS");
        //sb.append("     WHERE");
        //sb.append("         DRTS.COMP_CD  = ?    AND");
        //sb.append("         DRTS.KEIJO_DT = ?");
        //sb.append("     GROUP BY");
        //sb.append("         DRTS.COMP_CD");
        //sb.append("         ,DRTS.KEIJO_DT");
        //sb.append("         ,DRTS.TENPO_CD");

        //return sb.toString();
    //}

    /**
     * 会計精算売上データのトランケート処理
     * @return
     */
    //private String wkKaikeiSeisanTrun() {

        //StringBuffer sb = new StringBuffer();

        //sb.append(" TRUNCATE TABLE");
        //sb.append("     WK_KAIKEI_SEISAN");

        //return sb.toString();
    //}

    /**
     * ディクショナリから取得した会計精算取引コードMapの値を返す
     * @return
     */
    //private String getToriMap(String key) {

        // ディクショナリから取得した値がNULLかチェック
        //if (FiResorceUtility.getPropertieMap(UriResorceKeyConstant.KAIKEI_SEISAN_TORIHIKI_CD).get(key) == null
                //|| FiResorceUtility.getPropertieMap(UriResorceKeyConstant.KAIKEI_SEISAN_TORIHIKI_CD).get(key).toString().trim().length() == 0) {

            //return null;
        //} else {

            //return FiResorceUtility.getPropertieMap(UriResorceKeyConstant.KAIKEI_SEISAN_TORIHIKI_CD).get(key).toString();
        //}
    //}


    /**
     * ディクショナリから取得した[会計精算設定項目Mapの値を返す
     * @return
     */
    //private String getSetteiMap(String key) {

        // ディクショナリから取得した値がNULLかチェック
        //if (FiResorceUtility.getPropertieMap(UriResorceKeyConstant.KAIKEI_SEISAN_SETTEI_NAIYOU).get(key) == null
                //|| FiResorceUtility.getPropertieMap(UriResorceKeyConstant.KAIKEI_SEISAN_SETTEI_NAIYOU).get(key).toString().trim().length() == 0) {

            //return null;
        //} else {

            //return FiResorceUtility.getPropertieMap(UriResorceKeyConstant.KAIKEI_SEISAN_SETTEI_NAIYOU).get(key).toString();
        //}
    //}

// 2013.10.08 Y.Tominaga [CUS00057] SQLLoaderを使用した取込処理から、レジ別取引精算データより作成する処理へ変更対応（E)

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    //public Object getOutputObject() throws Exception {

        //return null;
    //}

    /**
     * インプットBeanを設定する
     * @param Object input
     */
    //public void setInputObject(Object input) throws Exception {

    //}

    //public static void main(String[] arg) {
        //try {
            //DaoIf dao = new KaikeiSeisanUriageDataTorikomiDao();
            //new StandAloneDaoInvoker("MM").InvokeDao(dao);
            //System.out.println(dao.getOutputObject());
        //} catch (DaoTimeOutException e) {
            //e.printStackTrace();
        //} catch (DaoException e) {
            //e.printStackTrace();
        //} catch (Exception e) {
            //e.printStackTrace();
        //}
    //}
    // ここまでは既存ソース

    /** バッチ処理名 */
    private static final String DAO_NAME = "会計精算売上データ取込処理";
    /** 登録先テーブル名称(レジ別取引精算テーブル) */
    private static final String TABLE_NAME = "会計精算売上集計前ワークテーブル";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_KAIKEI_SEISAN_SYUKEIMAE";

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
        PreparedStatementEx preparedStatementDelete = null;
        String dbServerTime = "";

        try {

            // レジ別取引精算データの追加
            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkKaikiSeisanSyukeimaeSql());
// 2017/04/27 VINX J.Endo FIVI(#4770) DEL(S)
//            preparedStatementExIns.setString(1, userId);
//            preparedStatementExIns.setString(2, dbServerTime);
//            preparedStatementExIns.setString(3, userId);
//            preparedStatementExIns.setString(4, dbServerTime);
//            preparedStatementExIns.setString(5, BATCH_DT);
//            preparedStatementExIns.setString(6, COMP_CD);
//            preparedStatementExIns.setString(7, BATCH_DT);
////            preparedStatementExIns.setString(8, userId);
////            preparedStatementExIns.setString(9, dbServerTime);
////            preparedStatementExIns.setString(10, userId);
////            preparedStatementExIns.setString(11, dbServerTime);
////            preparedStatementExIns.setString(12, COMP_CD);
////            preparedStatementExIns.setString(13, BATCH_DT);
// 2017/04/27 VINX J.Endo FIVI(#4770) DEL(E)
            // 2017/04/27 VINX J.Endo FIVI(#4770) ADD(S)
            int i = 1;
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);

            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);

            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            // 2017/04/27 VINX J.Endo FIVI(#4770) ADD(E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME + "の追加を終了します。");

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

// 2017/04/27 VINX J.Endo FIVI(#4770) DEL(S)
//    /**
//     * レジ別取引精算データ、売掛項目マスタから会計精算売上集計前ワークへ登録するSQLを取得する
//     *
//     * @return 会計精算売上集計前ワーク登録クエリー
//     */
//    private String getWkKaikiSeisanSyukeimaeSql() {
//        StringBuilder sql = new StringBuilder();
//
//        // 2016/11/14 VINX Y.Itaki FIVI(#2676) MOD(S)
//        //sql.append("INSERT /*+ APPEND */ INTO WK_KAIKEI_SEISAN( ");
//        sql.append("INSERT /*+ APPEND */ INTO WK_KAIKEI_SEISAN_SYUKEIMAE( ");
//        // 2016/11/14 VINX Y.Itaki FIVI(#2676) MOD(E)
//        sql.append("     COMP_CD ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,SEISANHYO_KOMOKU_CD ");
//        sql.append("    ,URIKAKE_KOMOKU_CD ");
//        sql.append("    ,KINGAKU_VL ");
//        sql.append("    ,KEN_QT ");
//        sql.append("    ,DATA_SAKUSEI_DT ");
//        sql.append("    ,INSERT_USER_ID ");
//        sql.append("    ,INSERT_TS ");
//        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS ");
//        sql.append("    ) ");
//        sql.append("SELECT  ");
//        sql.append("     DRTS.COMP_CD ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TORIHIKI_CD AS SEISANHYO_KOMOKU_CD ");
//        // 2016/11/14 VINX Y.Itaki FIVI(#2676) MOD(S)
//        //sql.append("    ,RUK.URIKAKE_KOMOKU_CD ");
//        sql.append("    ,CASE ");
//        sql.append("         WHEN TORIHIKI_CD <> '0002' THEN ' ' ");
//        sql.append("         ELSE NVL(RUK.URIKAKE_KOMOKU_CD,'99') ");
//        sql.append("     END URIKAKE_KOMOKU_CD ");
//        // 2016/11/14 VINX Y.Itaki FIVI(#2676) MOD(E)
//        sql.append("    ,KINGAKU_VL ");
//        sql.append("    ,TEN_KAISUU_QT AS KEN_QT ");
//        sql.append("    ,DATA_SAKUSEI_DT ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("FROM ");
//        sql.append("    DT_REGI_TORIHIKI_SEISAN DRTS ");
//
//        // 2016/11/14 VINX Y.Itaki FIVI(#2676) MOD(S)
////        sql.append("INNER JOIN ");
////        sql.append("    (");
////        sql.append("    SELECT ");
////        sql.append("        URIKAKE_KOMOKU_CD ");
////        sql.append("        ,COMP_CD ");
////        sql.append("        ,SHIHARAI_SYUBETSU_CD ");
////        sql.append("        ,SHIHARAI_SYUBETSU_SUB_CD ");
////        sql.append("    FROM ");
////        sql.append("        R_URIKAKE_KOMOKU RUK ");
////        sql.append("    WHERE ");
////        sql.append("        TEKIYO_START_DT ");
////        sql.append("        IN ");
////        sql.append("        ( ");
////        sql.append("        SELECT ");
////        sql.append("            MAX(TEKIYO_START_DT) ");
////        sql.append("        FROM ");
////        sql.append("            R_URIKAKE_KOMOKU ");
////        sql.append("        WHERE ");
////        sql.append("            TEKIYO_START_DT <= ? AND ");//「退避：バッチ日付」
////        sql.append("            DELETE_FG = '0' ");
////        sql.append("        ) ");
////        sql.append("    ) RUK ");
////        sql.append("ON ");
////        sql.append("    DRTS.COMP_CD = RUK.COMP_CD AND  ");
////        sql.append("    DRTS.SHIHARAI_SYUBETSU_CD = RUK.SHIHARAI_SYUBETSU_CD AND  ");
////        sql.append("    DRTS.SHIHARAI_SYUBETSU_SUB_CD = RUK.SHIHARAI_SYUBETSU_SUB_CD   ");
//        sql.append("LEFT JOIN ");
//        sql.append("    ( ");
//        sql.append("    SELECT ");
//        sql.append("        R1.COMP_CD ");
//        sql.append("        ,R1.SEISANHYO_KOMOKU_CD ");
//        sql.append("        ,R1.URIKAKE_KOMOKU_CD ");
//        sql.append("        ,R1.TEKIYO_START_DT ");
//        sql.append("        ,R1.SHIHARAI_SYUBETSU_CD ");
//        sql.append("        ,R1.SHIHARAI_SYUBETSU_SUB_CD ");
//        sql.append("    FROM ");
//        sql.append("        R_URIKAKE_KOMOKU R1 ");
//        sql.append("    INNER JOIN ");
//        sql.append("        ( ");
//        sql.append("        SELECT ");
//        sql.append("            COMP_CD ");
//        sql.append("            ,SEISANHYO_KOMOKU_CD ");
//        sql.append("            ,URIKAKE_KOMOKU_CD ");
//        sql.append("            ,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
//        sql.append("        FROM ");
//        sql.append("            R_URIKAKE_KOMOKU ");
//        sql.append("        WHERE ");
//        sql.append("            TEKIYO_START_DT <= ? AND ");
//        sql.append("            DELETE_FG = '0' ");
//        sql.append("        GROUP BY ");
//        sql.append("            COMP_CD ");
//        sql.append("            ,SEISANHYO_KOMOKU_CD ");
//        sql.append("            ,URIKAKE_KOMOKU_CD ");
//        sql.append("        ) R2 ");
//        sql.append("    ON ");
//        sql.append("        R1.COMP_CD = R2.COMP_CD AND ");
//        sql.append("        R1.SEISANHYO_KOMOKU_CD = R2.SEISANHYO_KOMOKU_CD AND ");
//        sql.append("        R1.URIKAKE_KOMOKU_CD = R2.URIKAKE_KOMOKU_CD AND ");
//        sql.append("        R1.TEKIYO_START_DT = R2.TEKIYO_START_DT ");
//        sql.append("    ) RUK ");
//        sql.append("ON ");
//        sql.append("    DRTS.COMP_CD = RUK.COMP_CD AND ");
//        sql.append("    DRTS.SHIHARAI_SYUBETSU_CD = RUK.SHIHARAI_SYUBETSU_CD AND ");
//        sql.append("    DRTS.SHIHARAI_SYUBETSU_SUB_CD = RUK.SHIHARAI_SYUBETSU_SUB_CD ");
//        // 2016/11/14 VINX Y.Itaki FIVI(#2676) MOD(E)
//
//        sql.append("WHERE ");
//        sql.append("    DRTS.COMP_CD = ? AND ");
//        sql.append("    DATA_SAKUSEI_DT = ? ");
//
//        //sql.append("INNER JOIN ");
//        //sql.append("    (");
//        //sql.append("    SELECT ");
//        //sql.append("        SEISANHYO_KOMOKU_CD ");
//        //sql.append("        ,COMP_CD ");
//        //sql.append("        ,SHIHARAI_SYUBETSU_CD ");
//        //sql.append("        ,SHIHARAI_SYUBETSU_SUB_CD ");
//        //sql.append("    FROM ");
//        //sql.append("        R_SEISANHYO_KOMOKU RSK ");
//        //sql.append("    WHERE ");
//        //sql.append("        TEKIYO_START_DT ");
//        //sql.append("        IN ");
//        //sql.append("        ( ");
//        //sql.append("        SELECT ");
//        //sql.append("            MAX(TEKIYO_START_DT) ");
//        //sql.append("        FROM ");
//        //sql.append("            R_SEISANHYO_KOMOKU ");
//        //sql.append("        WHERE ");
//        //sql.append("            SEISANHYO_KOMOKU_CD ");
//        //sql.append("            IN ");
//        //sql.append("            ( ");
//        //sql.append("                SELECT ");
//        //sql.append("                    TORIHIKI_CD ");
//        //sql.append("                FROM ");
//        //sql.append("                    DT_REGI_TORIHIKI_SEISAN ");
//        //sql.append("            ) AND ");
//        //sql.append("            TEKIYO_START_DT <= ? AND ");//「退避：バッチ日付」
//        //sql.append("            DELETE_FG = '0' ");
//        //sql.append("        ) ");
//        //sql.append("    ) RSK ");
//        //sql.append("ON ");
//        //sql.append("    DRTS.COMP_CD = RSK.COMP_CD AND  ");
//        //sql.append("    DRTS.SHIHARAI_SYUBETSU_CD = RSK.SHIHARAI_SYUBETSU_CD AND  ");
//        //sql.append("    DRTS.SHIHARAI_SYUBETSU_SUB_CD = RSK.SHIHARAI_SYUBETSU_SUB_CD   ");
//
//        // 2016/11/14 VINX Y.Itaki FIVI(#2676) DEL(S)
////        sql.append("UNION ALL ");
////        sql.append("SELECT  ");
////        sql.append("     COMP_CD ");
////        sql.append("    ,TENPO_CD ");
////        sql.append("    ,KEIJO_DT ");
////        sql.append("    ,TORIHIKI_CD ");
////        sql.append("    ,' ' ");
////        sql.append("    ,SUM(KINGAKU_VL) ");
////        sql.append("    ,SUM(TEN_KAISUU_QT) ");
////        sql.append("    ,DATA_SAKUSEI_DT ");
////        sql.append("    ,? ");
////        sql.append("    ,? ");
////        sql.append("    ,? ");
////        sql.append("    ,? ");
////        sql.append("FROM ");
////        sql.append("    DT_REGI_TORIHIKI_SEISAN ");
////        sql.append("WHERE ");
////        sql.append("    TORIHIKI_CD = '0002' AND ");
////        sql.append("    COMP_CD = ? AND ");
////        sql.append("    DATA_SAKUSEI_DT = ? ");
////        sql.append("GROUP BY ");
////        sql.append("    COMP_CD ");
////        sql.append("    ,TENPO_CD ");
////        sql.append("    ,KEIJO_DT ");
////        sql.append("    ,TORIHIKI_CD ");
////        sql.append("    ,DATA_SAKUSEI_DT ");
//        // 2016/11/14 VINX Y.Itaki FIVI(#2676) DEL(S)
//
//        return sql.toString();
//    }
// 2017/04/27 VINX J.Endo FIVI(#4770) DEL(E)
// 2017/04/27 VINX J.Endo FIVI(#4770) ADD(S)
    /**
     * レジ別取引精算データ、レジ別取引精算データErec、売掛項目マスタ、精算票項目マスタから
     * 会計精算売上集計前ワークへ登録するSQLを取得する
     *
     * @return 会計精算売上集計前ワーク登録クエリー
     */
    private String getWkKaikiSeisanSyukeimaeSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_KAIKEI_SEISAN_SYUKEIMAE ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,SEISANHYO_KOMOKU_CD ");
        sql.append("   ,URIKAKE_KOMOKU_CD ");
        sql.append("   ,KINGAKU_VL ");
        sql.append("   ,KEN_QT ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT "); // 取引コードが'0001'以外および'0002'以外のレコードはCレコードを出力
        sql.append("    DRTC.COMP_CD ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TORIHIKI_CD AS SEISANHYO_KOMOKU_CD ");
        sql.append("   ,' ' AS URIKAKE_KOMOKU_CD ");
        sql.append("   ,KINGAKU_VL ");
        sql.append("   ,TEN_KAISUU_QT AS KEN_QT ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ");
        sql.append("    DT_REGI_TORIHIKI_SEISAN DRTC ");
        sql.append("LEFT JOIN ( ");
        sql.append("    SELECT ");
        sql.append("        R1.COMP_CD ");
        sql.append("       ,R1.SEISANHYO_KOMOKU_CD ");
        sql.append("       ,R1.URIKAKE_KOMOKU_CD ");
        sql.append("       ,R1.TEKIYO_START_DT ");
        sql.append("       ,R1.SHIHARAI_SYUBETSU_CD ");
        sql.append("       ,R1.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    FROM ");
        sql.append("        R_URIKAKE_KOMOKU R1 ");
        sql.append("    INNER JOIN ( ");
        sql.append("        SELECT ");
        sql.append("            COMP_CD ");
        sql.append("           ,SEISANHYO_KOMOKU_CD ");
        sql.append("           ,URIKAKE_KOMOKU_CD ");
        sql.append("           ,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
        sql.append("        FROM ");
        sql.append("            R_URIKAKE_KOMOKU ");
        sql.append("        WHERE ");
        sql.append("            TEKIYO_START_DT <= ? AND ");
        sql.append("            DELETE_FG = '0' ");
        sql.append("        GROUP BY ");
        sql.append("            COMP_CD ");
        sql.append("           ,SEISANHYO_KOMOKU_CD ");
        sql.append("           ,URIKAKE_KOMOKU_CD ");
        sql.append("        ) R2 ");
        sql.append("    ON ");
        sql.append("        R1.COMP_CD = R2.COMP_CD AND ");
        sql.append("        R1.SEISANHYO_KOMOKU_CD = R2.SEISANHYO_KOMOKU_CD AND ");
        sql.append("        R1.URIKAKE_KOMOKU_CD = R2.URIKAKE_KOMOKU_CD AND ");
        sql.append("        R1.TEKIYO_START_DT = R2.TEKIYO_START_DT ");
        sql.append("    ) RUK ");
        sql.append("ON ");
        sql.append("    DRTC.COMP_CD = RUK.COMP_CD AND ");
        sql.append("    DRTC.SHIHARAI_SYUBETSU_CD = RUK.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("    DRTC.SHIHARAI_SYUBETSU_SUB_CD = RUK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("WHERE ");
        sql.append("    DRTC.COMP_CD = ? AND ");
        sql.append("    DATA_SAKUSEI_DT = ? AND ");
        sql.append("   (TORIHIKI_CD <> '0001' AND ");
        sql.append("    TORIHIKI_CD <> '0002') ");

        sql.append("UNION ALL "); // 取引コードが'0001'か'0002'のレコードはEレコードを出力 ※EレコードとCレコードの両方がある場合に出力
        sql.append("SELECT ");
        sql.append("    DRTE.COMP_CD ");
        sql.append("   ,DRTE.TENPO_CD ");
        sql.append("   ,DRTE.KEIJO_DT ");
        sql.append("   ,DRTE.TORIHIKI_CD AS SEISANHYO_KOMOKU_CD ");
        sql.append("   ,CASE WHEN DRTE.TORIHIKI_CD <> '0002' ");
        sql.append("        THEN ' ' ");
        sql.append("        ELSE NVL(RUK.URIKAKE_KOMOKU_CD,'99') ");
        sql.append("    END URIKAKE_KOMOKU_CD ");
        sql.append("   ,DRTE.KINGAKU_VL ");
        sql.append("   ,DRTE.TEN_KAISUU_QT AS KEN_QT ");
        sql.append("   ,DRTE.DATA_SAKUSEI_DT ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ");
        sql.append("    DT_REGI_TORIHIKI_SEISAN_E DRTE ");
        sql.append("INNER JOIN "); // EレコードとCレコードの両方がないレコードは除外
        sql.append("    DT_REGI_TORIHIKI_SEISAN DRTC ");
        sql.append("ON ");
        sql.append("    DRTC.COMP_CD = DRTE.COMP_CD AND ");
        sql.append("    DRTC.KEIJO_DT = DRTE.KEIJO_DT AND ");
        sql.append("    DRTC.TENPO_CD = DRTE.TENPO_CD AND ");
        sql.append("    DRTC.TORIHIKI_CD = DRTE.TORIHIKI_CD AND ");
        sql.append("    DRTC.SHIHARAI_SYUBETSU_CD = DRTE.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("    DRTC.SHIHARAI_SYUBETSU_SUB_CD = DRTE.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("LEFT JOIN ( ");
        sql.append("    SELECT ");
        sql.append("        R1.COMP_CD ");
        sql.append("       ,R1.SEISANHYO_KOMOKU_CD ");
        sql.append("       ,R1.URIKAKE_KOMOKU_CD ");
        sql.append("       ,R1.TEKIYO_START_DT ");
        sql.append("       ,R1.SHIHARAI_SYUBETSU_CD ");
        sql.append("       ,R1.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    FROM ");
        sql.append("        R_URIKAKE_KOMOKU R1 ");
        sql.append("    INNER JOIN ( ");
        sql.append("        SELECT ");
        sql.append("            COMP_CD ");
        sql.append("           ,SEISANHYO_KOMOKU_CD ");
        sql.append("           ,URIKAKE_KOMOKU_CD ");
        sql.append("           ,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
        sql.append("        FROM ");
        sql.append("            R_URIKAKE_KOMOKU ");
        sql.append("        WHERE ");
        sql.append("            TEKIYO_START_DT <= ? AND ");
        sql.append("            DELETE_FG = '0' ");
        sql.append("        GROUP BY ");
        sql.append("            COMP_CD ");
        sql.append("           ,SEISANHYO_KOMOKU_CD ");
        sql.append("           ,URIKAKE_KOMOKU_CD ");
        sql.append("        ) R2 ");
        sql.append("    ON ");
        sql.append("        R1.COMP_CD = R2.COMP_CD AND ");
        sql.append("        R1.SEISANHYO_KOMOKU_CD = R2.SEISANHYO_KOMOKU_CD AND ");
        sql.append("        R1.URIKAKE_KOMOKU_CD = R2.URIKAKE_KOMOKU_CD AND ");
        sql.append("        R1.TEKIYO_START_DT = R2.TEKIYO_START_DT ");
        sql.append("    ) RUK ");
        sql.append("ON ");
        sql.append("    DRTE.COMP_CD = RUK.COMP_CD AND ");
        sql.append("    DRTE.SHIHARAI_SYUBETSU_CD = RUK.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("    DRTE.SHIHARAI_SYUBETSU_SUB_CD = RUK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("WHERE ");
        sql.append("    DRTE.COMP_CD = ? AND ");
        sql.append("    DRTE.DATA_SAKUSEI_DT = ? AND ");
        sql.append("   (DRTE.TORIHIKI_CD = '0001' OR ");
        sql.append("    DRTE.TORIHIKI_CD = '0002') ");

        sql.append("UNION ALL "); // Eレコード.金額とCレコード.金額を比較し大きいほうのレコードの各項目を出力
        sql.append("SELECT ");
        sql.append("    DRTCE.COMP_CD ");
        sql.append("   ,DRTCE.TENPO_CD ");
        sql.append("   ,DRTCE.KEIJO_DT ");
        sql.append("   ,CASE WHEN DRTCE.KINGAKU_VL_E > DRTCE.KINGAKU_VL_C ");
        sql.append("        THEN NVL(RSK1.SEISANHYO_KOMOKU_CD, ' ') ");
        sql.append("        ELSE NVL(RSK0.SEISANHYO_KOMOKU_CD, ' ') ");
        sql.append("    END SEISANHYO_KOMOKU_CD ");
        sql.append("   ,' ' ");
        sql.append("   ,CASE WHEN DRTCE.KINGAKU_VL_E > DRTCE.KINGAKU_VL_C ");
        sql.append("        THEN DRTCE.KINGAKU_VL_E - DRTCE.KINGAKU_VL_C ");
        sql.append("        ELSE DRTCE.KINGAKU_VL_C - DRTCE.KINGAKU_VL_E ");
        sql.append("    END KINGAKU_VL ");
        sql.append("   ,DRTCE.KEN_QT ");
        sql.append("   ,DRTCE.DATA_SAKUSEI_DT ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ( ");
        sql.append("    SELECT ");
        sql.append("        COALESCE(DRTE.COMP_CD,                  DRTC.COMP_CD)                  AS COMP_CD ");
        sql.append("       ,COALESCE(DRTE.TENPO_CD,                 DRTC.TENPO_CD)                 AS TENPO_CD ");
        sql.append("       ,COALESCE(DRTE.KEIJO_DT,                 DRTC.KEIJO_DT)                 AS KEIJO_DT ");
        sql.append("       ,COALESCE(DRTE.TORIHIKI_CD,              DRTC.TORIHIKI_CD)              AS TORIHIKI_CD ");
        sql.append("       ,NVL(DRTE.KINGAKU_VL,0)                                                 AS KINGAKU_VL_E ");
        sql.append("       ,NVL(DRTC.KINGAKU_VL,0)                                                 AS KINGAKU_VL_C ");
        sql.append("       ,COALESCE(DRTE.TEN_KAISUU_QT,            DRTC.TEN_KAISUU_QT)            AS KEN_QT ");
        sql.append("       ,COALESCE(DRTE.DATA_SAKUSEI_DT,          DRTC.DATA_SAKUSEI_DT)          AS DATA_SAKUSEI_DT ");
        sql.append("       ,COALESCE(DRTE.SHIHARAI_SYUBETSU_CD,     DRTC.SHIHARAI_SYUBETSU_CD)     AS SHIHARAI_SYUBETSU_CD ");
        sql.append("       ,COALESCE(DRTE.SHIHARAI_SYUBETSU_SUB_CD, DRTC.SHIHARAI_SYUBETSU_SUB_CD) AS SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    FROM ( "); // CレコードとEレコードが何れかに存在すれば出力対象、ないレコードの金額は「0」を仮定して算出。
        sql.append("        SELECT "); // Cレコードを抽出
        sql.append("            COMP_CD ");
        sql.append("           ,TENPO_CD ");
        sql.append("           ,KEIJO_DT ");
        sql.append("           ,TORIHIKI_CD ");
        sql.append("           ,KINGAKU_VL ");
        sql.append("           ,TEN_KAISUU_QT ");
        sql.append("           ,DATA_SAKUSEI_DT ");
        sql.append("           ,SHIHARAI_SYUBETSU_CD ");
        sql.append("           ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("        FROM ");
        sql.append("            DT_REGI_TORIHIKI_SEISAN ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? AND ");
        sql.append("            DATA_SAKUSEI_DT = ? AND ");
        sql.append("           (TORIHIKI_CD = '0001' OR ");
        sql.append("            TORIHIKI_CD = '0002') ");
        sql.append("        ) DRTC ");
        sql.append("    FULL JOIN ( ");
        sql.append("        SELECT "); // Eレコードを抽出
        sql.append("            COMP_CD ");
        sql.append("           ,TENPO_CD ");
        sql.append("           ,KEIJO_DT ");
        sql.append("           ,TORIHIKI_CD ");
        sql.append("           ,KINGAKU_VL ");
        sql.append("           ,TEN_KAISUU_QT ");
        sql.append("           ,DATA_SAKUSEI_DT ");
        sql.append("           ,SHIHARAI_SYUBETSU_CD ");
        sql.append("           ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("        FROM ");
        sql.append("            DT_REGI_TORIHIKI_SEISAN_E ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? AND ");
        sql.append("            DATA_SAKUSEI_DT = ? AND ");
        sql.append("           (TORIHIKI_CD = '0001' OR ");
        sql.append("            TORIHIKI_CD = '0002') ");
        sql.append("        ) DRTE ");
        sql.append("    ON ");
        sql.append("        DRTC.COMP_CD = DRTE.COMP_CD AND ");
        sql.append("        DRTC.KEIJO_DT = DRTE.KEIJO_DT AND ");
        sql.append("        DRTC.TENPO_CD = DRTE.TENPO_CD AND ");
        sql.append("        DRTC.TORIHIKI_CD = DRTE.TORIHIKI_CD AND ");
        sql.append("        DRTC.SHIHARAI_SYUBETSU_CD = DRTE.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("        DRTC.SHIHARAI_SYUBETSU_SUB_CD = DRTE.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    WHERE ");
        sql.append("        NVL(DRTC.KINGAKU_VL,0) <> NVL(DRTE.KINGAKU_VL,0) "); // 金額が異なるもののみ出力対象
        sql.append("    ) DRTCE ");
        sql.append("    LEFT JOIN ( "); // 売掛項目マスタより売掛項目コードを取得
        sql.append("        SELECT ");
        sql.append("            R1.COMP_CD ");
        sql.append("           ,R1.SEISANHYO_KOMOKU_CD ");
        sql.append("           ,R1.URIKAKE_KOMOKU_CD ");
        sql.append("           ,R1.TEKIYO_START_DT ");
        sql.append("           ,R1.SHIHARAI_SYUBETSU_CD ");
        sql.append("           ,R1.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("        FROM ");
        sql.append("            R_URIKAKE_KOMOKU R1 ");
        sql.append("        INNER JOIN ( ");
        sql.append("            SELECT ");
        sql.append("                COMP_CD ");
        sql.append("               ,SEISANHYO_KOMOKU_CD ");
        sql.append("               ,URIKAKE_KOMOKU_CD ");
        sql.append("               ,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
        sql.append("            FROM ");
        sql.append("                R_URIKAKE_KOMOKU ");
        sql.append("            WHERE ");
        sql.append("                TEKIYO_START_DT <= ? AND ");
        sql.append("                DELETE_FG = '0' ");
        sql.append("            GROUP BY ");
        sql.append("                COMP_CD ");
        sql.append("               ,SEISANHYO_KOMOKU_CD ");
        sql.append("               ,URIKAKE_KOMOKU_CD ");
        sql.append("            ) R2 ");
        sql.append("        ON ");
        sql.append("            R1.COMP_CD = R2.COMP_CD AND ");
        sql.append("            R1.SEISANHYO_KOMOKU_CD = R2.SEISANHYO_KOMOKU_CD AND ");
        sql.append("            R1.URIKAKE_KOMOKU_CD = R2.URIKAKE_KOMOKU_CD AND ");
        sql.append("            R1.TEKIYO_START_DT = R2.TEKIYO_START_DT ");
        sql.append("        ) RUK ");
        sql.append("    ON ");
        sql.append("        DRTCE.COMP_CD = RUK.COMP_CD AND ");
        sql.append("        DRTCE.SHIHARAI_SYUBETSU_CD = RUK.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("        DRTCE.SHIHARAI_SYUBETSU_SUB_CD = RUK.SHIHARAI_SYUBETSU_SUB_CD ");
                        // 先に貸方・借方両方の精算票項目コードを結合しておく
        sql.append("    LEFT JOIN ( "); // 精算票項目マスタより貸借区分を0(借方)で結合し精算票項目コードを取得
        sql.append("        SELECT ");
        sql.append("            RSK.SHIHARAI_SYUBETSU_CD ");
        sql.append("           ,RSK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           ,RSK.SEISANHYO_KOMOKU_CD ");
        sql.append("           ,RSK.TAISYAKU_KB ");
        sql.append("        FROM ");
        sql.append("            R_SEISANHYO_KOMOKU RSK ");
        sql.append("            INNER JOIN ( ");
        sql.append("                SELECT ");
        sql.append("                    SHIHARAI_SYUBETSU_CD ");
        sql.append("                   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("                   ,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
        sql.append("                FROM ");
        sql.append("                    R_SEISANHYO_KOMOKU ");
        sql.append("                WHERE ");
        sql.append("                    TEKIYO_START_DT <= ? ");
        sql.append("                GROUP BY ");
        sql.append("                    SHIHARAI_SYUBETSU_CD ");
        sql.append("                   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("                ) RSK_MAX ");
        sql.append("            ON  RSK.SHIHARAI_SYUBETSU_CD = RSK_MAX.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("                RSK.SHIHARAI_SYUBETSU_SUB_CD = RSK_MAX.SHIHARAI_SYUBETSU_SUB_CD AND ");
        sql.append("                RSK.TEKIYO_START_DT = RSK_MAX.TEKIYO_START_DT ");
        sql.append("        WHERE ");
        sql.append("            RSK.COMP_CD = ? AND ");
        sql.append("            RSK.DELETE_FG = '0' AND ");
        sql.append("            RSK.TAISYAKU_KB = '0' ");
        sql.append("        ) RSK0 ");
        sql.append("    ON  DRTCE.SHIHARAI_SYUBETSU_CD = RSK0.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("        DRTCE.SHIHARAI_SYUBETSU_SUB_CD = RSK0.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    LEFT JOIN ( "); // 精算票項目マスタより貸借区分を1(貸方)で結合し精算票項目コードを取得
        sql.append("        SELECT ");
        sql.append("            RSK.SHIHARAI_SYUBETSU_CD ");
        sql.append("           ,RSK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           ,RSK.SEISANHYO_KOMOKU_CD ");
        sql.append("           ,RSK.TAISYAKU_KB ");
        sql.append("        FROM ");
        sql.append("            R_SEISANHYO_KOMOKU RSK ");
        sql.append("            INNER JOIN ( ");
        sql.append("                SELECT ");
        sql.append("                    SHIHARAI_SYUBETSU_CD ");
        sql.append("                   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("                   ,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
        sql.append("                FROM ");
        sql.append("                    R_SEISANHYO_KOMOKU ");
        sql.append("                WHERE ");
        sql.append("                    TEKIYO_START_DT <= ? ");
        sql.append("                GROUP BY ");
        sql.append("                    SHIHARAI_SYUBETSU_CD ");
        sql.append("                   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("                ) RSK_MAX ");
        sql.append("            ON  RSK.SHIHARAI_SYUBETSU_CD = RSK_MAX.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("                RSK.SHIHARAI_SYUBETSU_SUB_CD = RSK_MAX.SHIHARAI_SYUBETSU_SUB_CD AND ");
        sql.append("                RSK.TEKIYO_START_DT = RSK_MAX.TEKIYO_START_DT ");
        sql.append("        WHERE ");
        sql.append("            RSK.COMP_CD = ? AND ");
        sql.append("            RSK.DELETE_FG = '0' AND ");
        sql.append("            RSK.TAISYAKU_KB = '1' ");
        sql.append("        ) RSK1 ");
        sql.append("    ON  DRTCE.SHIHARAI_SYUBETSU_CD = RSK1.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("        DRTCE.SHIHARAI_SYUBETSU_SUB_CD = RSK1.SHIHARAI_SYUBETSU_SUB_CD ");

        return sql.toString();
}
// 2017/04/27 VINX J.Endo FIVI(#4770) ADD(E)

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
            DaoIf dao = new RegiTorihikiSeisanMstJohoSyutokuDao();
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
    // 2016/05/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

}
