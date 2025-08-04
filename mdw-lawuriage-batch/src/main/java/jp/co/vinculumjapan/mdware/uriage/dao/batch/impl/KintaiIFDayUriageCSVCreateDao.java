package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.CSVLine;
import jp.co.vinculumjapan.mdware.common.util.StringUtility;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.KintaiIFDayUriageCSVCreateDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoUtils;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;


/**
 *  <P>タイトル: KintaiIFDayUriageCSVCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author T.Morihiro
 *  @version 3.0  (2013.10.09) T.Morihiro [CUS00057] ランドローム様対応 売上管理―URIB131_日別売上集計処理
 *  @Version 3.01 (2013.10.23) S.Arakawa  [CUS00057] 固定値はあらかじめ宣言、SQLの見栄え修正
 *  @Version 3.02 (2013.10.27) T.Ooshiro  [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №048, 051
 *  @Version 3.03 (2014.04.22) Y.Tominaga [シス0201] ファイル名日時分秒追加
 */
public class KintaiIFDayUriageCSVCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "勤怠IF用CSV作成処理(外部IF)";
    /** バッチID */
    private static final String BATCH_ID = "URIB131070";
    /** DB日付取得 */
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    
    /** 「/」*/
    private static final String SOLIDUS = "/";
 // 2014.04.22 Y.Tominaga [シス0201] ファイル名日時分秒追加 (S)
    /** 「_」 */
    private static final String UNDER = "_";
    /** ファイル拡張子用「.csv」 */
    private static final String FILE_EXT = ".csv";
 // 2014.04.22 Y.Tominaga [シス0201] ファイル名日時分秒追加 (E)
    /** ファイル配置先ディレクトリ */
    private static final String PATH_SEND_KINTAI = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND_KINTAI);

    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    
    /** InputBean */
    private KintaiIFDayUriageCSVCreateDaoInputBean inputBean = null;
    /** 文字列モード(SPACE埋め用判定) */
    private static final int MODE_X = 1;
    /** 数値モード(符号付き0埋め用判定) */
    private static final int MODE_S9 = 2;
    /** 数値モード(符号なし0埋め用判定) */
    private static final int MODE_9 = 0;
    /** 付加文字(半角スペース) */
    private static final String ADD_SPACE = " ";
    /** 付加文字(0) */
    private static final String ADD_ZERO = "0";
    /** 符号＋ */
    private static final String SIGN_PLUS = "+";
    /** 符号－ */
    private static final String SIGN_MINUS = "-";
    /** 時間帯       文字列数  */
    private static final int TIME_NO_LENGTH  = 2;
    /** 部門コード   文字列数  */
    private static final int BUMON_CD_LENGTH = 3;
    /** 売上日       文字列数  */
    private static final int URIAGE_DT_LENGTH = 8;

    /**
     * 
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        
// 2014.04.22 Y.Tominaga [シス0201] ファイル名日時分秒追加 (S)
        // 外部IFファイル名を取得する
        String strFileName = inputBean.getOutsideFileName();
        
        // データファイルフルパスを日付付で設定する xxxx.csv → xxxx_YYYYMMDDHHMMSS.csv
        String strDataFilePath = new StringBuffer(PATH_SEND_KINTAI)
        .append(SOLIDUS).append(strFileName).append(UNDER).append(DBSERVER_DT).append(FILE_EXT).toString();
// 2014.04.22 Y.Tominaga [シス0201] ファイル名日時分秒追加 (E)
        
        // 改行コード
        String CRLF = "\r\n";
        
        // 日時間帯別売上データ出力件数
        int intCreateCount = 0;
        int intUpdateCount = 0;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;
        PreparedStatementEx preparedStatementUpd = null;
        
        BufferedWriter bwFile = null;
        ResultSet rsSelectFileOutData = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　勤怠Ｓ：勤怠IF用CSV作成処理(外部IF)を開始します。");
        invoker.infoLog(strUserID + "　：　勤怠IF用データ:勤怠IF用データ抽出処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        int IDX = 1;
        preparedStatementEx = invoker.getDataBase().prepareStatement(getSelKintaiIFDataSql());
        preparedStatementEx.setString(IDX++, COMP_CD);
        preparedStatementEx.setString(IDX++, UriageCommonConstants.ERR_KB_00);
        
        // SQLを実行し、データ取得
        rsSelectFileOutData = preparedStatementEx.executeQuery();

        invoker.infoLog(strUserID + "　：　勤怠IF用データ:勤怠IF用データ抽出処理を終了します。");
        
        invoker.infoLog(strUserID + "　：　時間帯別売上データ:ファイル作成処理を開始します。");
        
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
            // 売上日
            detailLine.addItem(getColumnEdit(mapFileOutData.get("URIAGE_DT").toString(), MODE_X, URIAGE_DT_LENGTH));
            // 店舗コード
            detailLine.addItem(mapFileOutData.get("TENPO_CD").toString().trim());
            // 部門コード
            detailLine.addItem(getColumnEdit(StringUtility.formatZero(mapFileOutData.get("BUMON_CD").toString(), BUMON_CD_LENGTH), MODE_X, BUMON_CD_LENGTH));
            // 時間帯
            detailLine.addItem(getColumnEdit(StringUtility.formatZero(mapFileOutData.get("TIME_NO").toString(), TIME_NO_LENGTH), MODE_X, TIME_NO_LENGTH));
            // 実績売上
            detailLine.addItem(mapFileOutData.get("JISEKI_URIAGE_VL").toString());
            // 実績販売点数
            detailLine.addItem(mapFileOutData.get("JISEKI_HANBAI_QT").toString());
            // 実績客数
            detailLine.addItem(mapFileOutData.get("JISEKI_KYAKU_QT").toString());
  
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
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件の日別売上データを出力しました。");
        invoker.infoLog(strUserID + "　：　時間帯別売上データ:ファイル作成処理を終了します。");        
        invoker.infoLog(strUserID + "　：　勤怠IF用累積データ:勤怠IF用累積データ更新処理を開始します。");

        // 勤怠IF用累積データ更新処理
        IDX = 1;
        preparedStatementUpd = invoker.getDataBase().prepareStatement(getUpdKintaiIFDataSql());
        preparedStatementUpd.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_ZUMI);
        preparedStatementUpd.setString(IDX++, BATCH_ID);
        preparedStatementUpd.setString(IDX++, DBSERVER_DT);
        preparedStatementUpd.setString(IDX++, COMP_CD);
        preparedStatementUpd.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_MI);
        
        // 実行
        intUpdateCount = preparedStatementUpd.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intUpdateCount + "件の勤怠IF用累積データを更新しました。");
        invoker.infoLog(strUserID + "　：　勤怠IF用累積データ:勤怠IF用累積データ更新処理を終了します。");

        invoker.infoLog(strUserID + "　：　勤怠Ｓ：勤怠IF用CSV作成処理(外部IF)を終了します。");
        
    }

    private String getSelKintaiIFDataSql() {
        StringBuffer sb = new StringBuffer("");
        
        // 勤怠IF用データ抽出用SelectSQL構築
        sb.append(" SELECT                  ");
        sb.append("      URIAGE_DT          ");
        sb.append("    , TENPO_CD           ");
        sb.append("    , BUMON_CD           ");
        sb.append("    , TIME_NO            ");
        sb.append("    , JISEKI_URIAGE_VL   ");
        sb.append("    , JISEKI_HANBAI_QT   ");
        sb.append("    , JISEKI_KYAKU_QT    ");
        sb.append(" FROM                    ");
        sb.append("    DT_KINTAI_IF         ");
        sb.append(" WHERE                   ");
        sb.append("    COMP_CD = ? AND      ");
        sb.append("    ERR_KB  = ?          ");
        
        return sb.toString();
    }

    private String getUpdKintaiIFDataSql() {
        StringBuffer sb = new StringBuffer("");
        
        // 勤怠IF用データ更新用UpdateSQL構築
        sb.append(" UPDATE                      ");
        sb.append("    DT_KINTAI_IF_RUISEKI     ");
        sb.append(" SET                         ");
        sb.append("      DATA_RENKEI_FG = ?     ");
        sb.append("    , UPDATE_USER_ID = ?     ");
        sb.append("    , UPDATE_TS      = ?     ");
        sb.append(" WHERE                       ");
        sb.append("    COMP_CD          = ? AND ");
        sb.append("    DATA_RENKEI_FG   = ?     ");
        
        return sb.toString();
    }
    
    /**
     * <p>文字列を編集する</p>
     * @param editStr 編集元文字
     * @param judgeMode 1：文字列として編集する 2：符号付き数値として編集する それ以外：符号無し数値として編集する
     * @param strLen 編集する文字列長
     * @return 編集結果を返却する
     */
    private String getColumnEdit(String editStr, int judgeMode, int strLen) {
        
        // 戻り値初期化
        String result = "";
        
        // 対応文字列の初期成型
        if (editStr == null) {
            editStr = "";
        }
        editStr = editStr.trim();
        
        boolean sighFg = false;
        switch (judgeMode) {
            case MODE_X:    // 文字列モード(スペース埋め)
                for (int i = editStr.length(); i < strLen; i++) {
                    editStr = new StringBuffer(editStr)
                        .append(ADD_SPACE)
                        .toString();
                }
                break;
                
            case MODE_S9: // 数値モード(符号有り0埋め)
                // 符号が付いているかを確認する
                if (editStr.indexOf(SIGN_PLUS) >= 0) {
                    sighFg = true;
                }
                if (!sighFg && editStr.indexOf(SIGN_MINUS) >= 0) {
                    sighFg = true;
                }
                for (int i = editStr.length(); i < strLen; i++) {
                    if (!sighFg) {
                       if (editStr.length() == 0) {
                           // 空文字なので、+符号を先頭付加
                           editStr = new StringBuffer("")
                               .append(SIGN_PLUS)
                               .toString();
                       } else {
                           // 指定値入力あり且つ、符号無し ⇒ ＋値となる
                           // 結果、＋符合を先頭に付加する。
                           editStr = new StringBuffer("")
                               .append(SIGN_PLUS)
                               .append(editStr)
                               .toString();
                       }
                       sighFg = true;
                    } else {
                        // 先頭から2文字目に0を付加
                        editStr = new StringBuffer(editStr.substring(0, 1))
                            .append(ADD_ZERO)
                            .append(editStr.substring(1, editStr.length()))
                            .toString();
                    }
                }
                break;
            default:    // 数値モード(符号無し0埋め)
                // 符号無し数値を想定
                for (int i = editStr.length(); i < strLen; i++) {
                    // 先頭に0を付加
                    editStr = new StringBuffer("")
                        .append(ADD_ZERO)
                        .append(editStr)
                        .toString();
                }
        }
        result = editStr;
        
        return result;
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

        inputBean = (KintaiIFDayUriageCSVCreateDaoInputBean) input;
        
    }
}