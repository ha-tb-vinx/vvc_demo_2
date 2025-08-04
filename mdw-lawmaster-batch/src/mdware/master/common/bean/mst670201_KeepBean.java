/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計区分マスタ登録画面の画面情報格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計区分マスタ登録画面の画面情報格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
package mdware.master.common.bean;

import java.util.*;

import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計区分マスタ登録画面の画面情報格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計区分マスタ登録画面の画面情報格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
public class mst670201_KeepBean {
    private static final StcLog stcLog = StcLog.getInstance();

    private List meisai = new ArrayList(); // 検索結果（明細）
    
    private List meisaiAdd = new ArrayList(); // 検索結果（追加分明細）    

    private String StartNo = ""; // 開始集計種別コード

    private String ErrorFlg = ""; // エラーフラグ

    private String ErrorMessage = ""; // エラーメッセージ

    private String Mode = ""; // 処理モード

    private String[] MenuBar = null; // メニューバーアイテム

    private Map CtrlColor = new HashMap(); // コントロール前景色

    private String FirstFocus = ""; // フォーカスを最初に取得するオブジェクト名

    private String InsertFlg = ""; // 新規処理利用可能区分

    private String UpdateFlg = ""; // 更新処理利用可能区分

    private String DeleteFlg = ""; // 削除処理利用可能区分

    private String ReferenceFlg = ""; // 照会処理利用可能区分

    private String CsvFlg = ""; // CSV処理利用可能区分

    private String PrintFlg = ""; // 印刷処理利用可能区分

    private String DispUrl = ""; // 現画面URL

    private String BeforeDispUrl = ""; // 前画面URL

    private String BeforeDispKb = ""; // 前画面区分(Menu:0 Menu以外:1)

    private String insert_ts = null; //作成年月日

    private String insert_user_id = null; //作成者社員ID

    private String update_ts = null; //更新年月日

    private String update_user_ts = null; //更新者社員ID

    private String update_user_id = null; //更新者社員ID

    private String[] sentaku = null; // チェックボックス
    
    private String[] sentakuAdd = null; // 追加用チェックボックス

    /**
     * @return beforeDispKb を戻します。
     */
    public String getBeforeDispKb() {
        return BeforeDispKb;
    }

    /**
     * @param beforeDispKb
     *            beforeDispKb を設定。
     */
    public void setBeforeDispKb(String beforeDispKb) {
        BeforeDispKb = beforeDispKb;
    }

    /**
     * @return beforeDispUrl を戻します。
     */
    public String getBeforeDispUrl() {
        return BeforeDispUrl;
    }

    /**
     * @param beforeDispUrl
     *            beforeDispUrl を設定。
     */
    public void setBeforeDispUrl(String beforeDispUrl) {
        BeforeDispUrl = beforeDispUrl;
    }

    /**
     * @return csvFlg を戻します。
     */
    public String getCsvFlg() {
        return CsvFlg;
    }

    /**
     * @param csvFlg
     *            csvFlg を設定。
     */
    public void setCsvFlg(String csvFlg) {
        CsvFlg = csvFlg;
    }

    /**
     * @return ctrlColor を戻します。
     */
    public Map getCtrlColor() {
        return CtrlColor;
    }

    /**
     * @param ctrlColor
     *            ctrlColor を設定。
     */
    public void setCtrlColor(Map ctrlColor) {
        CtrlColor = ctrlColor;
    }

    /**
     * @return deleteFlg を戻します。
     */
    public String getDeleteFlg() {
        return DeleteFlg;
    }

    /**
     * @param deleteFlg
     *            deleteFlg を設定。
     */
    public void setDeleteFlg(String deleteFlg) {
        DeleteFlg = deleteFlg;
    }

    /**
     * @return dispUrl を戻します。
     */
    public String getDispUrl() {
        return DispUrl;
    }

    /**
     * @param dispUrl
     *            dispUrl を設定。
     */
    public void setDispUrl(String dispUrl) {
        DispUrl = dispUrl;
    }

    /**
     * @return errorFlg を戻します。
     */
    public String getErrorFlg() {
        return ErrorFlg;
    }

    /**
     * @param errorFlg
     *            errorFlg を設定。
     */
    public void setErrorFlg(String errorFlg) {
        ErrorFlg = errorFlg;
    }

    /**
     * @return errorMessage を戻します。
     */
    public String getErrorMessage() {
        return ErrorMessage;
    }

    /**
     * @param errorMessage
     *            errorMessage を設定。
     */
    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    /**
     * @return firstFocus を戻します。
     */
    public String getFirstFocus() {
        return FirstFocus;
    }

    /**
     * @param firstFocus
     *            firstFocus を設定。
     */
    public void setFirstFocus(String firstFocus) {
        FirstFocus = firstFocus;
    }

    /**
     * @return insertFlg を戻します。
     */
    public String getInsertFlg() {
        return InsertFlg;
    }

    /**
     * @param insertFlg
     *            insertFlg を設定。
     */
    public void setInsertFlg(String insertFlg) {
        InsertFlg = insertFlg;
    }

    /**
     * @return menuBar を戻します。
     */
    public String[] getMenuBar() {
        return MenuBar;
    }

    /**
     * @param menuBar
     *            menuBar を設定。
     */
    public void setMenuBar(String[] menuBar) {
        MenuBar = menuBar;
    }

    /**
     * @return mode を戻します。
     */
    public String getMode() {
        return Mode;
    }

    /**
     * @param mode
     *            mode を設定。
     */
    public void setMode(String mode) {
        Mode = mode;
    }

    /**
     * @return printFlg を戻します。
     */
    public String getPrintFlg() {
        return PrintFlg;
    }

    /**
     * @param printFlg
     *            printFlg を設定。
     */
    public void setPrintFlg(String printFlg) {
        PrintFlg = printFlg;
    }

    /**
     * @return processingDivision を戻します。
     */
    public String getProcessingDivision() {
        return ProcessingDivision;
    }

    /**
     * @param processingDivision
     *            processingDivision を設定。
     */
    public void setProcessingDivision(String processingDivision) {
        ProcessingDivision = processingDivision;
    }

    /**
     * @return referenceFlg を戻します。
     */
    public String getReferenceFlg() {
        return ReferenceFlg;
    }

    /**
     * @param referenceFlg
     *            referenceFlg を設定。
     */
    public void setReferenceFlg(String referenceFlg) {
        ReferenceFlg = referenceFlg;
    }

    /**
     * @return startNo を戻します。
     */
    public String getStartNo() {
        return StartNo;
    }

    /**
     * @param startNo
     *            startNo を設定。
     */
    public void setStartNo(String startNo) {
        StartNo = startNo;
    }

    /**
     * @return updateFlg を戻します。
     */
    public String getUpdateFlg() {
        return UpdateFlg;
    }

    /**
     * @param updateFlg
     *            updateFlg を設定。
     */
    public void setUpdateFlg(String updateFlg) {
        UpdateFlg = updateFlg;
    }

    private String ProcessingDivision = "0"; // 処理区分

    /**
     * meisaiを取得する。
     * 
     * @return meisaiを返す。
     */
    public List getMeisai() {

        return meisai;
    }

    /**
     * meisaiを設定する。
     * 
     * @param meisai
     *            meisaiを指定する。
     */
    public void setMeisai(List meisai) {

        this.meisai = meisai;
    }
    
    /**
     * meisaiAddを設定する。
     * 
     * @param meisaiAdd
     *            meisaiAddを指定する。
     */
    public void setMeisaiAdd(List meisaiAdd) {

        this.meisaiAdd = meisaiAdd;
    }

    /**
     * meisaiAddを取得する。
     * 
     * @return meisaiAddを返す。
     */
    public List getMeisaiAdd() {

        return meisaiAdd;
    }
    
    /**
     * 作成年月日に対するセッター <br>
     * <br>
     * Ex) <br>
     * setInsertTs("文字列"); <br>
     * <br>
     * 
     * @param insert_ts
     *            insert_ts
     * @return boolean
     */
    public boolean setInsertTs(String insert_ts) {
        this.insert_ts = insert_ts;
        return true;
    }

    /**
     * 作成年月日に対するゲッター <br>
     * <br>
     * Ex) <br>
     * getInsertTs(); 戻り値 文字列 <br>
     * <br>
     * 
     * @return String 文字列
     */
    public String getInsertTs() {
        return this.insert_ts;
    }

    /**
     * 作成者社員IDに対するセッター <br>
     * <br>
     * Ex) <br>
     * setInsertUserId("文字列"); <br>
     * <br>
     * 
     * @param insert_user_id
     *            insert_user_id
     * @return boolean
     */
    public boolean setInsertUserId(String insert_user_id) {
        this.insert_user_id = insert_user_id;
        return true;
    }

    /**
     * 作成者社員IDに対するゲッター <br>
     * <br>
     * Ex) <br>
     * getInsertUserId(); 戻り値 文字列 <br>
     * <br>
     * 
     * @return String 文字列
     */
    public String getInsertUserId() {
        return this.insert_user_id;
    }

    /**
     * 更新年月日に対するセッター <br>
     * <br>
     * Ex) <br>
     * setUpdateTs("文字列"); <br>
     * <br>
     * 
     * @param update_ts
     *            update_ts
     * @return boolean
     */
    public boolean setUpdateTs(String update_ts) {
        this.update_ts = update_ts;
        return true;
    }

    /**
     * 更新年月日に対するゲッター <br>
     * <br>
     * Ex) <br>
     * getUpdateTs(); 戻り値 文字列 <br>
     * <br>
     * 
     * @return String 文字列
     */
    public String getUpdateTs() {
        return this.update_ts;
    }

    /**
     * 更新者社員IDに対するセッター <br>
     * <br>
     * Ex) <br>
     * setUpdateUserTs("文字列"); <br>
     * <br>
     * 
     * @param update_user_ts
     *            update_user_ts
     * @return boolean
     */
    public boolean setUpdateUserTs(String update_user_ts) {
        this.update_user_ts = update_user_ts;
        return true;
    }

    /**
     * 更新者社員IDに対するゲッター <br>
     * <br>
     * Ex) <br>
     * getUpdateUserTs(); 戻り値 文字列 <br>
     * <br>
     * 
     * @return String 文字列
     */
    public String getUpdateUserTs() {
        return this.update_user_ts;
    }

    /**
     * 更新者社員IDに対するセッター <br>
     * <br>
     * Ex) <br>
     * setUpdateUserId("文字列"); <br>
     * <br>
     * 
     * @param update_user_id
     *            update_user_id
     * @return boolean
     */
    public boolean setUpdateUserId(String update_user_id) {
        this.update_user_id = update_user_id;
        return true;
    }

    /**
     * 更新者社員IDに対するゲッター <br>
     * <br>
     * Ex) <br>
     * getUpdateUserId(); 戻り値 文字列 <br>
     * <br>
     * 
     * @return String 文字列
     */
    public String getUpdateUserId() {
        return this.update_user_id;
    }

    /**
     * @return sentaku を戻します。
     */
    public String[] getSentaku() {
        return sentaku;
    }

    /**
     * @param sentaku
     *            sentaku を設定。
     */
    public void setSentaku(String[] sentaku) {
        this.sentaku = sentaku;
    }

    /**
     * @return sentaku を戻します。
     */
    public String[] getSentakuAdd() {
        return sentakuAdd;
    }

    /**
     * @param sentakuAdd
     *            sentakuAdd を設定。
     */
    public void setSentakuAdd(String[] sentakuAdd) {
        this.sentakuAdd = sentakuAdd;
    }
    
}