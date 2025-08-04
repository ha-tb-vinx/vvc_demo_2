package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jp.co.vinculumjapan.mdware.common.util.Zip;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.ShinTanpinSummaryFileCreateDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: ShinTanpinSummaryFileCreateDao クラス</p>
 *  <P>説明: IF新単品サマリファイル作成処理です。</p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author Y.Tominaga
 * @Version 1.00 (2014.12.04) chou グローバル化対応 通貨対応
 * @version 1.01 2015/06/18 Sou 英語化対応
 * @version 1.02 2015/12/04 T.Kamei グローバル化対応の漏れの修正
 * @Version 1.03 2016.05.20 VINX S.Kashihara FIVI対応
 * @Version 1.04 2016/09/22 VINX K.Hyo FIVI対応
 * @Version 1.05 2016/10/19 VINX k.Hyo FIVI対応
 */
public class ShinTanpinSummaryFileCreateDao implements DaoIf {

    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ処理名
    private static final String BATCH_NAME = "IF新単品サマリファイル作成";
    // 「/」
    private static final String SOLIDUS = "/";
    // 囲み文字(ダブルクォーテーション)
    private static final String ENCLOSE_CHAR = "\"";
    // 改行文字(CRLF)
    private static final String LINE_FEED_CHAR = "\r\n";
    // DB文字コード
//    private static final String DB_CHAR_SET = "Cp943C";
    // 出力ファイル文字コード
    private static final String OUTPUT_CHAR_SET = "UTF-8";
//    // IF新単品サマリファイル名
//    private static final String FILE_TANPINSAM = FiResorceUtility.getPropertie(UriResorceKeyConstant.FILE_TANPINSAM);
    // ファイル保存ディレクトリ
    private static final String PATH_SEND = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND);

    // 符号　"-"
    private static final String signMinus = "-";
    // 符号　"+"
    private static final String signPlus = "+";
    // インプットビーン
    private ShinTanpinSummaryFileCreateDaoInputBean input = null;

    // ゼロ　"0"
    private static final String ZERO_1 = "0";
    // 文字列　"00"
    private static final String ZERO_2 = "00";
    // 文字列　"0000"
    private static final String ZERO_4 = "0000";
    // 文字列　"00000"
    private static final String ZERO_5 = "00000";
    // 文字列　"000000"
    private static final String ZERO_6 = "000000";
    // 文字列　"000000"
// 2015.12.04 T.Kamei グローバル化対応 DEL(S)
//    private static final String ZERO_7 = "0000000";
// 2015.12.04 T.Kamei グローバル化対応 DEL(E)
    // 文字列　"000000000"
    private static final String ZERO_9 = "000000000";
    // 文字列　"0000000000"
// 2015.12.04 T.Kamei グローバル化対応 DEL(S)
//    private static final String ZERO_10 = "0000000000";
// 2015.12.04 T.Kamei グローバル化対応 DEL(E)
// 2015.12.04 T.Kamei グローバル化対応 ADD(S)
    // 文字列　"0000000000000000000"
    private static final String ZERO_18 = "000000000000000000";
    private static final String ZERO_19 = "0000000000000000000";
    private static final String ZERO_20 = "00000000000000000000";
// 2015.12.04 T.Kamei グローバル化対応 ADD(E)
    // TO_CHAR変換フォーマット
// 2015.12.04 T.Kamei グローバル化対応 DEL(S)
//    private static final String CHANGE_FORMAT = "0000000000.00";
// 2015.12.04 T.Kamei グローバル化対応 DEL(E)
// 2015.12.04 T.Kamei グローバル化対応 ADD(S)
    private static final String CHANGE_FORMAT11_2 = "000000000.00";
    private static final String CHANGE_FORMAT17_2 = "000000000000000.00";
    private static final String MM_POINT_TAISYO_KB_CUT = "1";
// 2015.12.04 T.Kamei グローバル化対応 ADD(E)
    // 数値　"6"
    private static final String END_HANBAI_TS_CUT = "6";
    // 数値　"36"
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
    private static final String RECEIPT_NA_CUT = "36";
//    private static final String RECEIPT_NA_CUT = "12";
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
    // 数値　"13"
    private static final String POS_CD_CUT = "13";
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
    // 数値　"54"
    private static final String MIX_MATCH_KANA_NA_CUT = "54";
//    private static final String MIX_MATCH_KANA_NA_CUT = "18";
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
    // FTPファイルバックアップディレクトリパス
    private static final String BACK_UP_DIR_PATH = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND_BACKUP);

    /**
     * IF新単品サマリファイル作成処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 新単品サマリ追加件数
        int intCreateCount = 0;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // オブジェクトを初期化する
        PreparedStatementEx psShinTanpinSumSelect = null;
        ResultSet resultSet = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　IF新単品サマリファイル作成処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        psShinTanpinSumSelect = invoker.getDataBase().prepareStatement(shinTanpinSumSelect());

        int index = 1;
        psShinTanpinSumSelect.setString(index++, COMP_CD);

        // SQL文を実行する
        resultSet = psShinTanpinSumSelect.executeQuery();

        // データ生成件数をセットする
        intCreateCount = csvExport(resultSet, input.getFileName());

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件のIF新単品サマリファイルを作成しました。");

        /** ファイルバックアップ処理 */

        // 開始ログ出力
        invoker.infoLog(strUserID + "　：　ファイルバックアップ処理を開始します。");

        // バックアップフォルダ名を指定
        String backupFileName = input.getFileName() + ".zip";

        // 圧縮後ファイルのフルパス
        String backupFilePath = new File(BACK_UP_DIR_PATH + "/" + backupFileName).getAbsolutePath();

        // 圧縮元のファイルのフルパス
        String toPath = PATH_SEND + "/" + input.getFileName();

        Zip zip = null;

        // バックアップ保管用ディレクトり存在確認
        if (!new File(BACK_UP_DIR_PATH).exists()) {
            throw new DaoException("バックアップ保管用ディレクトリが存在しません。");
        }

        zip = new Zip();
        zip.setSrcFilename(toPath);

        // ZIPファイル作成
        zip.encodeZip(backupFilePath);

        // 終了ログ出力
        invoker.infoLog(strUserID + "　：　ファイルバックアップ処理を終了します。");

        invoker.infoLog(strUserID + "　：　IF新単品サマリファイル作成処理を終了します。");

    }

    /**
     * 新単品サマリファイルからIFレイアウトの抽出を行うSQL
     * @return
     */
    private String shinTanpinSumSelect() {

        StringBuffer sb = new StringBuffer();

// 2013.10.17 Y.Tominaga [CUS00057] ライン・クラスコードの桁数変更　4ケタ→5ケタ　ファイル出力項目がNULLの時でも固定長出力する修正 (S)
        sb.append(" SELECT                                                                                                        ");
        sb.append("      KEIJO_DT                                                                          KEIJO_DT               ");
        //sb.append("     ,RTRIM(TENPO_CD)                                                                   TENPO_CD               ");
        sb.append("     ,LPAD(NVL(TENPO_CD,'"  + ZERO_1 + "')," + END_HANBAI_TS_CUT + ",'" +     ZERO_1  + "')    TENPO_CD        ");
        sb.append("     ,LPAD(NVL(POS_CD,'"    + ZERO_1 + "')," + POS_CD_CUT + ",'" +     ZERO_1  + "')    POS_CD                 ");
        sb.append("     ,LPAD(NVL(SYOHIN_CD,'" + ZERO_1 + "')," + POS_CD_CUT + ",'" +     ZERO_1  + "')    SYOHIN_CD              ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(NVL(BAITANKA_VL,'"       + ZERO_1 + "'),'"   +     CHANGE_FORMAT11_2  + "'))   BAITANKA_VL ");
//        sb.append("     ,LTRIM(TO_CHAR(NVL(BAITANKA_VL,'"       + ZERO_1 + "'),'"   +     ZERO_7  + "'))   BAITANKA_VL            ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        sb.append("     ,LTRIM(TO_CHAR(NVL(BAITANKA_VL,'"       + ZERO_1 + "'),'"   +     CHANGE_FORMAT11_2  + "'))   BAITANKA_VL ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(DAIBUNRUI2_CD,'" + ZERO_1 + "')),'"  +     ZERO_4  + "'))   DAIBUNRUI2_CD          ");
        //sb.append("     ,RPAD(NVL(RECEIPT_NA,' '),"             + RECEIPT_NA_CUT + ")                      RECEIPT_NA             ");
        sb.append("     ,RPAD(' '," + RECEIPT_NA_CUT + ",' ')                                                      RECEIPT_NA      "); // r0v6
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(DAIBUNRUI1_CD,'" + ZERO_1 + "')),'"  +     ZERO_4  + "'))   DAIBUNRUI1_CD          ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(KIKAKU_CD,'"     + ZERO_1 + "')),'"  +     ZERO_9  + "'))   KIKAKU_CD              ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(SYOHIN_KB,'"     + ZERO_1 + "')),'"  +     ZERO_2  + "'))   SYOHIN_KB              ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(MIX_MATCH_CD,'"  + ZERO_1 + "')),'"  +     ZERO_4  + "'))   MIX_MATCH_CD           ");
        sb.append("     ,'" + ZERO_4 + "'                                                                  MIX_MATCH_CD           "); // v0r6
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(MIX_MATCH_TYPE_TX,'" + ZERO_1 + "')),'" +  ZERO_6  + "'))   MIX_MATCH_TYPE_TX      ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(MIX_MATCH_TYPE_TX,'" + ZERO_1 + "')),'" +  ZERO_2  + "'))   MIX_MATCH_TYPE_TX      ");
        sb.append("     ,'" + ZERO_6 + "'                                                                  MIX_MATCH_TYPE_TX      "); // v0r6
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNDLE_1_QT,'"   + ZERO_1 + "')),'"  +     ZERO_2  + "'))   BUNDLE_1_QT            ");
        sb.append("     ,'" + ZERO_2 + "'                                                                  BUNDLE_1_QT            "); // v0r6
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNDLE_1_VL,'"   + ZERO_1 + "')),'"  +     ZERO_19 + "'))   BUNDLE_1_VL            ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNDLE_1_VL,'"   + ZERO_1 + "')),'"  +     ZERO_10 + "'))   BUNDLE_1_VL            ");
        sb.append("     ,'" + ZERO_19 + "'                                                                 BUNDLE_1_VL            "); // v0r6
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNDLE_2_QT,'"   + ZERO_1 + "')),'"  +     ZERO_2  + "'))   BUNDLE_2_QT            ");
        sb.append("     ,'" + ZERO_2 + "'                                                                  BUNDLE_2_QT            "); // v0r6
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNDLE_2_VL,'"   + ZERO_1 + "')),'"  +     ZERO_19 + "'))   UNDLE_2_VL             ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNDLE_2_VL,'"   + ZERO_1 + "')),'"  +     ZERO_10 + "'))   UNDLE_2_VL             ");
        sb.append("     ,'" + ZERO_19 + "'                                                                 UNDLE_2_VL             "); // v0r6
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        //sb.append("     ,RPAD(NVL(MIX_MATCH_KANA_NA,' '),"      + MIX_MATCH_KANA_NA_CUT + ")               MIX_MATCH_KANA_NA      ");
        sb.append("     ,RPAD('0'," + MIX_MATCH_KANA_NA_CUT + ",'0')                                        MIX_MATCH_KANA_NA      "); // r0v6
        sb.append("     ,CASE                                                                                                     ");
        sb.append("         WHEN URIAGE_VL < " + ZERO_1 + "                                                                       ");
        sb.append("             THEN '" + signMinus + "'                                                                          ");
        sb.append("         ELSE                                                                                                  ");
        sb.append("             '" + signPlus + "'                                                                                ");
        sb.append("     END                                                                                URIAGE_VL_KIGO         ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(URIAGE_VL,'"     + ZERO_1 + "')),'" +     CHANGE_FORMAT17_2 + "'))    URIAGE_VL    ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(URIAGE_VL,'"     + ZERO_1 + "')),'" +     ZERO_10 + "'))    URIAGE_VL              ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(URIAGE_VL,'"     + ZERO_1 + "')),'" +     CHANGE_FORMAT17_2 + "'))    URIAGE_VL    ");
        //sb.append("     ,CASE                                                                                                     ");
        //sb.append("         WHEN URIAGE_GENKA_VL < " + ZERO_1 + "                                                                 ");
        //sb.append("             THEN '" + signMinus + "'                                                                          ");
        //sb.append("         ELSE                                                                                                  ");
        //sb.append("             '" + signPlus + "'                                                                                ");
        //sb.append("     END                                                                                 URIAGE_GENKA_VL_KIGO  ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(URIAGE_GENKA_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "')) URIAGE_GENKA_VL   ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(URIAGE_GENKA_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT + "')) URIAGE_GENKA_VL       ");
        sb.append("     ,'" + ZERO_19 + "'                                                                 URIAGE_GENKA_VL        "); // v0r6
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(URIAGE_GENKA_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "')) URIAGE_GENKA_VL   ");
        sb.append("     ,CASE                                                                                                     ");
        sb.append("         WHEN URIAGE_QT < " + ZERO_1 + "                                                                       ");
        sb.append("             THEN '" + signMinus + "'                                                                          ");
        sb.append("         ELSE                                                                                                  ");
        sb.append("             '" + signPlus + "'                                                                                ");
        sb.append("     END                                                                                 URIAGE_QT_KIGO        ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(URIAGE_QT,'"  + ZERO_1 + "')),'" + ZERO_5 + "'))             URIAGE_QT             ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(URIAGE_QT,'"  + ZERO_1 + "')),'" + ZERO_19 + "'))            URIAGE_QT             ");
        //sb.append("     ,CASE                                                                                                     ");
        //sb.append("         WHEN NEBIKI_VL < " + ZERO_1 + "                                                                       ");
        //sb.append("             THEN '" + signMinus + "'                                                                          ");
        //sb.append("         ELSE                                                                                                  ");
        //sb.append("             '" + signPlus + "'                                                                                ");
        //sb.append("     END                                                                                 NEBIKI_VL_KIGO        ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(NEBIKI_VL,'"  + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "'))            NEBIKI_VL   ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(NEBIKI_VL,'"  + ZERO_1 + "')),'" + ZERO_10 + "'))            NEBIKI_VL             ");
        sb.append("     ,'" + ZERO_19 + "'                                                                  NEBIKI_VL             "); // v0r6
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(NEBIKI_VL,'"  + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "'))            NEBIKI_VL   ");
        //sb.append("     ,CASE                                                                                                     ");
        //sb.append("         WHEN NEBIKI_QT < " + ZERO_1 + "                                                                       ");
        //sb.append("             THEN '" + signMinus + "'                                                                          ");
        //sb.append("         ELSE                                                                                                  ");
        //sb.append("             '" + signPlus + "'                                                                                ");
        //sb.append("     END                                                                                 NEBIKI_QT_KIGO        ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(NEBIKI_QT,'" + ZERO_1 + "')),'" + ZERO_5 + "'))              NEBIKI_QT             ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(NEBIKI_QT,'" + ZERO_1 + "')),'" + ZERO_19 + "'))             NEBIKI_QT             ");
        sb.append("     ,'" + ZERO_20 + "'                                                                  NEBIKI_QT             "); // v0r6
        //sb.append("     ,CASE                                                                                                     ");
        //sb.append("         WHEN KAKO_VL < " + ZERO_1 + "                                                                         ");
        //sb.append("             THEN '" + signMinus + "'                                                                          ");
        //sb.append("         ELSE                                                                                                  ");
        //sb.append("             '" + signPlus + "'                                                                                ");
        //sb.append("     END                                                                                 KAKO_VL_KIGO          ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(KAKO_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "'))               KAKO_VL     ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(KAKO_VL,'" + ZERO_1 + "')),'" + ZERO_10 + "'))               KAKO_VL               ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(KAKO_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "'))               KAKO_VL     ");
        sb.append("     ,'" + ZERO_19 + "'                                                                  KAKO_VL               "); // v0r6
        //sb.append("     ,CASE                                                                                                     ");
        //sb.append("         WHEN KAKO_QT < " + ZERO_1 + "                                                                         ");
        //sb.append("             THEN '" + signMinus + "'                                                                          ");
        //sb.append("         ELSE                                                                                                  ");
        //sb.append("             '" + signPlus + "'                                                                                ");
        //sb.append("     END                                                                                 KAKO_QT_KIGO          ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(KAKO_QT,'" + ZERO_1 + "')),'" + ZERO_5 + "'))                KAKO_QT               ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(KAKO_QT,'" + ZERO_1 + "')),'" + ZERO_19 + "'))               KAKO_QT               ");
        sb.append("     ,'" + ZERO_20 + "'                                                                  KAKO_QT               "); // v0r6
        //sb.append("     ,CASE                                                                                                     ");
        //sb.append("         WHEN HAIKI_VL < " + ZERO_1 + "                                                                        ");
        //sb.append("             THEN '" + signMinus + "'                                                                          ");
        //sb.append("         ELSE                                                                                                  ");
        //sb.append("             '" + signPlus + "'                                                                                ");
        //sb.append("     END                                                                                 HAIKI_VL_KIGO         ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HAIKI_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "'))              HAIKI_VL    ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HAIKI_VL,'" + ZERO_1 + "')),'" + ZERO_10 + "'))              HAIKI_VL              ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HAIKI_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "'))              HAIKI_VL    ");
        sb.append("     ,'" + ZERO_19 + "'                                                                  HAIKI_VL              "); // v0r6
        //sb.append("     ,CASE                                                                                                     ");
        //sb.append("         WHEN HAIKI_QT < " + ZERO_1 + "                                                                        ");
        //sb.append("             THEN '" + signMinus + "'                                                                          ");
        //sb.append("         ELSE                                                                                                  ");
        //sb.append("             '" + signPlus + "'                                                                                ");
        //sb.append("     END                                                                                 HAIKI_QT_KIGO         ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HAIKI_QT,'" + ZERO_1 + "')),'" + ZERO_5 + "'))               HAIKI_QT              ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HAIKI_QT,'" + ZERO_1 + "')),'" + ZERO_19 + "'))              HAIKI_QT              ");
        sb.append("     ,'" + ZERO_20 + "'                                                                  HAIKI_QT              "); // v0r6
        sb.append("     ,CASE                                                                                                     ");
        sb.append("         WHEN HENPIN_VL < " + ZERO_1 + "                                                                       ");
        sb.append("             THEN '" + signMinus + "'                                                                          ");
        sb.append("         ELSE                                                                                                  ");
        sb.append("             '" + signPlus + "'                                                                                ");
        sb.append("     END                                                                                 HENPIN_VL_KIGO        ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(S)
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HENPIN_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "'))             HENPIN_VL   ");
//        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HENPIN_VL,'" + ZERO_1 + "')),'" + ZERO_10 + "'))             HENPIN_VL             ");
// 2015.12.04 T.Kamei グローバル化対応 MOD(E)
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HENPIN_VL,'" + ZERO_1 + "')),'" + CHANGE_FORMAT17_2 + "'))             HENPIN_VL   ");
        sb.append("     ,CASE                                                                                                     ");
        sb.append("         WHEN HENPIN_QT < " + ZERO_1 + "                                                                       ");
        sb.append("             THEN '" + signMinus + "'                                                                          ");
        sb.append("         ELSE                                                                                                  ");
        sb.append("             '" + signPlus + "'                                                                                ");
        sb.append("     END                                                                                 HENPIN_QT_KIGO        ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HENPIN_QT,'" + ZERO_1 + "')),'" + ZERO_5 + "'))              HENPIN_QT             ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(HENPIN_QT,'" + ZERO_1 + "')),'" + ZERO_19 + "'))             HENPIN_QT             ");
        sb.append("     ,CASE                                                                                                     ");
        sb.append("         WHEN KYAKU_QT < " + ZERO_1 + "                                                                        ");
        sb.append("             THEN '" + signMinus + "'                                                                          ");
        sb.append("         ELSE                                                                                                  ");
        sb.append("             '" + signPlus + "'                                                                                ");
        sb.append("     END                                                                                 KYAKU_QT_KIGO         ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(KYAKU_QT,'"   + ZERO_1 + "')),'" + ZERO_5 + "'))             KYAKU_QT              ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(KYAKU_QT,'"   + ZERO_1 + "')),'" + ZERO_19 + "'))            KYAKU_QT              ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNRUI1_CD,'" + ZERO_1 + "')),'" + ZERO_4 + "'))             BUNRUI1_CD            ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNRUI2_CD,'" + ZERO_1 + "')),'" + ZERO_5 + "'))             BUNRUI2_CD            ");
        //2014/12/04 chou グローバル化対応 通貨対応 MOD START
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNRUI5_CD,'" + ZERO_1 + "')),'" + ZERO_5 + "'))             BUNRUI5_CD            ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(BUNRUI5_CD,'" + ZERO_1 + "')),'" + ZERO_6 + "'))             BUNRUI5_CD            ");
        //2014/12/04 chou グローバル化対応 通貨対応 MOD END
        sb.append("     ,LPAD(NVL(END_HANBAI_TS,' ')," + END_HANBAI_TS_CUT + ")                             END_HANBAI_TS         ");
        //sb.append("     ,NVL(MM_POINT_TAISYO_KB,'"     + ZERO_1 + "')                                       MM_POINT_TAISYO_KB    ");
        sb.append("     ,RPAD(' '," + MM_POINT_TAISYO_KB_CUT + ",' ')                                       MM_POINT_TAISYO_KB    "); // v0r6
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(S)
        sb.append("     ,CASE                                                                                                     ");
        sb.append("         WHEN PI_URIAGE_QT < " + ZERO_1 + "                                                                    ");
        sb.append("             THEN '" + signMinus + "'                                                                          ");
        sb.append("         ELSE                                                                                                  ");
        sb.append("             '" + signPlus + "'                                                                                ");
        sb.append("     END                                                                                 PI_URIAGE_QT_KIGO     ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(PI_URIAGE_QT,'"   + ZERO_1 + "')),'" + ZERO_5 + "'))         PI_URIAGE_QT          ");
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(PI_URIAGE_QT,'"   + ZERO_1 + "')),'" + ZERO_19 + "'))        PI_URIAGE_QT          ");
        sb.append("     ,CASE                                                                                                     ");
        sb.append("         WHEN PI_HENPIN_QT < " + ZERO_1 + "                                                                    ");
        sb.append("             THEN '" + signMinus + "'                                                                          ");
        sb.append("         ELSE                                                                                                  ");
        sb.append("             '" + signPlus + "'                                                                                ");
        sb.append("     END                                                                                 PI_HENPIN_QT_KIGO     ");
        //sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(PI_HENPIN_QT,'"   + ZERO_1 + "')),'" + ZERO_5 + "'))         PI_HENPIN_QT          ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(E)
        sb.append("     ,LTRIM(TO_CHAR(ABS(NVL(PI_HENPIN_QT,'"   + ZERO_1 + "')),'" + ZERO_19 + "'))        PI_HENPIN_QT          ");
        // 2016.10.19 VINX k.Hyo FIVI対応 ADD(S)
        //sb.append("     ,URIAGE_KB                                                                          URIAGE_KB             ");
        sb.append("     ,CASE URIAGE_KB                                                                                           ");
        sb.append("         WHEN '1' THEN '0'                                                                                     ");
        sb.append("         WHEN '2' THEN '1'                                                                                     ");
        sb.append("         WHEN '3' THEN '2'                                                                                     ");
        sb.append("         ELSE URIAGE_KB                                                                                        ");
        sb.append("     END URIAGE_KB                                                                                             ");
        sb.append(" FROM                       ");
        sb.append("     WK_NEW_TANPIN_SUMMARY  ");
        sb.append(" WHERE                      ");
        sb.append("     COMP_CD = ?            ");
// 2013.10.17 Y.Tominaga [CUS00057] ライン・クラスコードの桁数変更　4ケタ→5ケタ　ファイル出力項目がNULLの時でも固定長出力する修正 (E)

        return sb.toString();
    }

    /**
     * ファイル出力処理
     * @param rsSelectData
     * @param strFileName
     * @return 出力件数
     * @throws Exception
     */
    private int csvExport(ResultSet rsSelectData, String strFileName) throws Exception {

        // 出力データ数
        int intCount = 0;
        // ファイル出力データ
        ResultSetMetaData rsdCSVData = null;
        // ファイル出力
        BufferedWriter writer = null;
        File dataFile = new File(PATH_SEND + SOLIDUS + strFileName);

        // 出力データはResultSetMetaDataへ変換する
        rsdCSVData = rsSelectData.getMetaData();

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
            writer = new BufferedWriter(outputStreamWriter);

            while (rsSelectData.next()) {
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 1; i <= rsdCSVData.getColumnCount(); i++) {
                    if (rsSelectData.getString(i) != null) {
                        // 2013.10.15 [CUS00057] POSインターフェイス仕様変更対応（S)
                        String data = new String(rsSelectData.getString(i).getBytes(OUTPUT_CHAR_SET), OUTPUT_CHAR_SET);
                        // 2013.10.15 [CUS00057] POSインターフェイス仕様変更対応（E)
                        //囲み文字がデータに含まれる場合は削除
                        data = data.replaceAll(ENCLOSE_CHAR, "");
                        stringBuffer.append(data);
                    }
                }
                stringBuffer.append(LINE_FEED_CHAR);
                writer.write(stringBuffer.toString());

                intCount++;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return intCount;
    }

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {

        return null;
    }

    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {
        this.input = (ShinTanpinSummaryFileCreateDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new ShinTanpinSummaryFileCreateDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
