package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.CSVLine;
import jp.co.vinculumjapan.mdware.common.util.StringUtility;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.DWHIFBumonSeisanCSVCreateDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoUtils;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;


/**
 *  <P>タイトル: DWHIFBumonSeisanCSVCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author T.Morihiro
 *  @version 1.0 2015/08/17 Sou 店舗コード桁数拡張
 */
public class DWHIFBumonSeisanCSVCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "部門精算データ作成処理(外部IF)";
    /** バッチID */
    private static final String BATCH_ID = "URIB131110";
    /** DB日付取得 */
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /** 「/」*/
    private static final String SOLIDUS = "/";
    /** ファイル配置先ディレクトリ */
    private static final String PATH_SET_DATA82 = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND_DWH);

    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** InputBean */
    private DWHIFBumonSeisanCSVCreateDaoInputBean inputBean = null;
//    /** 文字列モード(SPACE埋め用判定) */
//    private static final int MODE_X = 1;
//    /** 数値モード(符号付き0埋め用判定) */
//    private static final int MODE_S9 = 2;
//    /** 文字列モード(SPACE左埋め用判定) */
//    private static final int MODE_LX = 3;
//    /** 付加文字(半角スペース) */
//    private static final String ADD_SPACE = " ";
//    /** 付加文字(0) */
//    private static final String ADD_ZERO = "0";
//    /** 符号＋ */
//    private static final String SIGN_PLUS = "+";
//    /** 符号－ */
//    private static final String SIGN_MINUS = "-";
//
//    /** 日付 桁数 */
//    private static final int URIAGE_DT_LENGTH = 8;
    /** 時間(スペース埋め:5BYTE) */
    private static final String TIME_ADD_SPACE = "     ";
    /** 店舗コード 桁数 */
    //2015/08/17 Sou 店舗コード桁数拡張
    // private static final int TENPO_CD_LENGTH = 4;
    private static final int TENPO_CD_LENGTH = 6;
    /** DPTコード  桁数 */
    private static final int BUNRUI1_CD_LENGTH = 6;
//    /** 売上客数   桁数 */
//    private static final int URIAGE_KYAKU_QT_LENGTH = 10;
//    /** 売上点数   桁数 */
//    private static final int URIAGE_QT_LENGTH = 10;
//    /** 売上金額   桁数 */
//    private static final int URIAGE_VL_LENGTH = 11;
//    /** 売上点数(POS) 桁数 */
//    private static final int POS_QT_LENGTH = 10;
//    /** 売上金額(POS) 桁数 */
//    private static final int POS_VL_LENGTH = 11;
    /** 返品点数(0.00 固定値) */
    private static final String HENPIN_QT_ADD_ZERO = "0.00";
    /** 返品金額(0.00 固定値) */
    private static final String HENPIN_VL_ADD_ZERO = "0.00";

    /**
     *
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // 外部IFファイル名を取得する
        String strFileName = inputBean.getOutsideFileName();
        // データファイルフルパスを設定する
        String strDataFilePath = new StringBuffer(PATH_SET_DATA82)
        .append(SOLIDUS).append(strFileName).toString();

        // 改行コード
        String CRLF = "\r\n";

        // 部門精算データ出力件数
        int intCreateCount = 0;
        int intUpdateCount = 0;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;
        PreparedStatementEx preparedStatementUpd = null;

        BufferedWriter bwFile = null;
        ResultSet rsSelectFileOutData = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　ＤＷＨＳ：部門精算データ作成処理(外部IF)を開始します。");
        invoker.infoLog(strUserID + "　：　部門精算データ:部門精算データ抽出処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        int IDX = 1;
        preparedStatementEx = invoker.getDataBase().prepareStatement(getSelDWHIFDataSql());
        preparedStatementEx.setString(IDX++, COMP_CD);
        preparedStatementEx.setString(IDX++, UriageCommonConstants.ERR_KB_00);

        // SQLを実行し、データ取得
        rsSelectFileOutData = preparedStatementEx.executeQuery();

        invoker.infoLog(strUserID + "　：　部門精算データ:部門精算データ抽出処理を終了します。");

        invoker.infoLog(strUserID + "　：　部門精算データ:ファイル作成処理を開始します。");

        // データ取得結果と、他カラムの編集を実施し、外部IFファイルとしてファイル出力する
        bwFile = new BufferedWriter(new FileWriter(strDataFilePath));

        // *****************************************************
        // * 取得データ1件ずつファイル出力(固定長ファイル作成) *
        // *****************************************************
        while (rsSelectFileOutData.next()) {
            // 出力件数 + 1
            intCreateCount++;

            Map mapFileOutData = DaoUtils.getMapFromRs(rsSelectFileOutData);

            // 下記の明細項目の編集を行う
            CSVLine detailLine = new CSVLine();
            // 日付
            detailLine.addItem(mapFileOutData.get("URIAGE_DT").toString());
            // 時間(スペース埋め:5BYTE)
            detailLine.addItem(TIME_ADD_SPACE);
            // 企業コード１("0001"固定)
            detailLine.addItem(UriageCommonConstants.KIGYO_CD_0001);
            // 企業コード２("0001"固定)
            detailLine.addItem(UriageCommonConstants.KIGYO_CD_0001);
            // 店舗コード
            detailLine.addItem(StringUtility.formatZero(mapFileOutData.get("TENPO_CD").toString(), TENPO_CD_LENGTH));
            // DPTコード
            detailLine.addItem(StringUtility.formatZero(mapFileOutData.get("BUNRUI1_CD").toString(), BUNRUI1_CD_LENGTH));
            // 売上客数
            detailLine.addItem(mapFileOutData.get("URIAGE_KYAKU_QT").toString());
            // 売上点数
            detailLine.addItem(mapFileOutData.get("URIAGE_QT").toString());
            // 売上金額
            detailLine.addItem(mapFileOutData.get("URIAGE_VL").toString());
            // 売上点数(POS)
            detailLine.addItem(mapFileOutData.get("POS_QT").toString());
            // 売上金額(POS)
            detailLine.addItem(mapFileOutData.get("POS_VL").toString());
            // 返品点数(0.00固定)
            detailLine.addItem(HENPIN_QT_ADD_ZERO);
            // 返品金額(0.00固定)
            detailLine.addItem(HENPIN_VL_ADD_ZERO);

            // 《データファイルパス》で指定する外部IFファイルへの書き込みを行う
            bwFile.write(detailLine.getLine() + CRLF);
        }

        if (bwFile != null) {
            bwFile.flush();
            bwFile.close();
        }

        if (rsSelectFileOutData != null) {
            rsSelectFileOutData.close();
            rsSelectFileOutData = null;
        }

        if (preparedStatementEx != null) {
            preparedStatementEx.close();
            preparedStatementEx = null;
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件の部門精算データを出力しました。");
        invoker.infoLog(strUserID + "　：　部門精算データ:ファイル作成処理を終了します。");

        // ＤＷＨIF用累積データ更新処理
        IDX = 1;
        preparedStatementUpd = invoker.getDataBase().prepareStatement(updDWHIFDataSql());
        preparedStatementUpd.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_ZUMI);
        preparedStatementUpd.setString(IDX++, BATCH_ID);
        preparedStatementUpd.setString(IDX++, DBSERVER_DT);
        preparedStatementUpd.setString(IDX++, COMP_CD);
        preparedStatementUpd.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_MI);

        // 実行
        intUpdateCount = preparedStatementUpd.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intUpdateCount + "件のＤＷＨIF用累積データを更新しました。");
        invoker.infoLog(strUserID + "　：　ＤＷＨIF用累積データ:ＤＷＨIF用累積データ更新処理を終了します。");

        invoker.infoLog(strUserID + "　：　ＤＷＨＳ：部門精算データ作成処理(外部IF)を終了します。");

    }

    private String getSelDWHIFDataSql() {
        StringBuffer sb = new StringBuffer("");

        // 部門精算データ抽出用SelectSQL構築
        sb.append(" SELECT              ");
        sb.append("      TRIM(URIAGE_DT)                              URIAGE_DT         ");
        sb.append("    , TENPO_CD       ");
        sb.append("    , BUNRUI1_CD     ");
        sb.append("    , LTRIM(TO_CHAR(URIAGE_KYAKU_QT, '999990.99')) URIAGE_KYAKU_QT   ");
        sb.append("    , LTRIM(TO_CHAR(URIAGE_QT, '999990.99'))       URIAGE_QT         ");
        sb.append("    , LTRIM(TO_CHAR(URIAGE_VL, '9999990.99'))      URIAGE_VL         ");
        sb.append("    , LTRIM(TO_CHAR(POS_QT, '999990.99'))          POS_QT            ");
        sb.append("    , LTRIM(TO_CHAR(POS_VL, '9999990.99'))         POS_VL            ");
        sb.append(" FROM                ");
        sb.append("    DT_DWH_IF        ");
        sb.append(" WHERE               ");
        sb.append("    COMP_CD = ? AND  ");
        sb.append("    ERR_KB  = ?      ");

        return sb.toString();
    }

    private String updDWHIFDataSql() {
        StringBuffer sb = new StringBuffer("");

        // ＤＷＨIF用累積データ更新用UpdateSQL構築
        sb.append(" UPDATE                      ");
        sb.append("    DT_DWH_IF_RUISEKI        ");
        sb.append(" SET                         ");
        sb.append("      DATA_RENKEI_FG = ?     ");
        sb.append("    , UPDATE_USER_ID = ?     ");
        sb.append("    , UPDATE_TS      = ?     ");
        sb.append(" WHERE                       ");
        sb.append("    COMP_CD          = ? AND ");
        sb.append("    DATA_RENKEI_FG   = ?     ");


        return sb.toString();
    }

//    /**
//     * <p>文字列を編集する</p>
//     * @param editStr 編集元文字
//     * @param judgeMode 1：文字列として編集する 2：符号付き数値として編集する それ以外：符号無し数値として編集する
//     * @param strLen 編集する文字列長
//     * @return 編集結果を返却する
//     */
//    private String getColumnEdit(String editStr, int judgeMode, int strLen) {
//
//        // 戻り値初期化
//        String result = "";
//
//        // 対応文字列の初期成型
//        if (editStr == null) {
//            editStr = "";
//        }
//        editStr = editStr.trim();
//
//        boolean sighFg = false;
//        switch (judgeMode) {
//            case MODE_X:    // 文字列モード(スペース埋め)
//                for (int i = editStr.length(); i < strLen; i++) {
//                    editStr = new StringBuffer(editStr)
//                        .append(ADD_SPACE)
//                        .toString();
//                }
//                break;
//
//            case MODE_LX:   // 文字列モード(左スペース埋め)
//                for (int i = editStr.length(); i < strLen; i++) {
//                    editStr = new StringBuffer("")
//                        .append(ADD_SPACE)
//                        .append(editStr)
//                        .toString();
//                }
//                break;
//
//            case MODE_S9: // 数値モード(符号有り0埋め)
//                // 符号が付いているかを確認する
//                if (editStr.indexOf(SIGN_PLUS) >= 0) {
//                    sighFg = true;
//                }
//                if (!sighFg && editStr.indexOf(SIGN_MINUS) >= 0) {
//                    sighFg = true;
//                }
//                for (int i = editStr.length(); i < strLen; i++) {
//                    if (!sighFg) {
//                       if (editStr.length() == 0) {
//                           // 空文字なので、+符号を先頭付加
//                           editStr = new StringBuffer("")
//                               .append(SIGN_PLUS)
//                               .toString();
//                       } else {
//                           // 指定値入力あり且つ、符号無し ⇒ ＋値となる
//                           // 結果、＋符合を先頭に付加する。
//                           editStr = new StringBuffer("")
//                               .append(SIGN_PLUS)
//                               .append(editStr)
//                               .toString();
//                       }
//                       sighFg = true;
//                    } else {
//                        // 先頭から2文字目に0を付加
//                        editStr = new StringBuffer(editStr.substring(0, 1))
//                            .append(ADD_ZERO)
//                            .append(editStr.substring(1, editStr.length()))
//                            .toString();
//                    }
//                }
//                break;
//            default:    // 数値モード(符号無し0埋め)
//                // 符号無し数値を想定
//                for (int i = editStr.length(); i < strLen; i++) {
//                    // 先頭に0を付加
//                    editStr = new StringBuffer("")
//                        .append(ADD_ZERO)
//                        .append(editStr)
//                        .toString();
//                }
//        }
//        result = editStr;
//
//        return result;
//    }

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

        inputBean = (DWHIFBumonSeisanCSVCreateDaoInputBean) input;

    }
}