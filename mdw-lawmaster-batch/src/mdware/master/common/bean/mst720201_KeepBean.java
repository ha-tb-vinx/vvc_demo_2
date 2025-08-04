/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）キーPLUパネル表示画面の画面情報格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するキーPLUパネル表示画面の画面情報格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/13)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）キーPLUパネル表示画面の画面情報格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するキーPLUパネル表示画面の画面情報格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/13)初版作成
 */
public class mst720201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private List	meisai				= new ArrayList();	// 検索結果（明細）
//共通項目ここから
	private String ErrorFlg			= "";				// エラーフラグ
	private String ErrorMessage		= "";				// エラーメッセージ
	private String Mode				= "";				// 処理モード
	private String[] MenuBar			= null;				// メニューバーアイテム
	private Map CtrlColor				= new HashMap();	// コントロール前景色
	private String FirstFocus			= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg			= "";				// 新規処理利用可能区分
	private String UpdateFlg			= "";				// 更新処理利用可能区分
	private String DeleteFlg			= "";				// 削除処理利用可能区分
	private String ReferenceFlg		= "";				// 照会処理利用可能区分
	private String CsvFlg				= "";				// CSV処理利用可能区分
	private String PrintFlg			= "";				// 印刷処理利用可能区分
	private String DispUrl				= "";				// 現画面URL
	private String BeforeDispUrl		= "";				// 前画面URL
	private String BeforeDispKb		= "";				// 前画面区分(Menu:0 Menu以外:1)
	private String ProcessingDivision	= "0";				// 処理区分
//共通項目ここまで
	
    /**
     * @return beforeDispKb を戻します。
     */
    public String getBeforeDispKb() {
        return BeforeDispKb;
    }
    /**
     * @param beforeDispKb beforeDispKb を設定。
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
     * @param beforeDispUrl beforeDispUrl を設定。
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
     * @param csvFlg csvFlg を設定。
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
     * @param ctrlColor ctrlColor を設定。
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
     * @param deleteFlg deleteFlg を設定。
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
     * @param dispUrl dispUrl を設定。
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
     * @param errorFlg errorFlg を設定。
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
     * @param errorMessage errorMessage を設定。
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
     * @param firstFocus firstFocus を設定。
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
     * @param insertFlg insertFlg を設定。
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
     * @param menuBar menuBar を設定。
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
     * @param mode mode を設定。
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
     * @param printFlg printFlg を設定。
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
     * @param processingDivision processingDivision を設定。
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
     * @param referenceFlg referenceFlg を設定。
     */
    public void setReferenceFlg(String referenceFlg) {
        ReferenceFlg = referenceFlg;
    }
     /**
     * @return updateFlg を戻します。
     */
    public String getUpdateFlg() {
        return UpdateFlg;
    }
    /**
     * @param updateFlg updateFlg を設定。
     */
    public void setUpdateFlg(String updateFlg) {
        UpdateFlg = updateFlg;
    }

	/**
	 * @return meisaiを戻します。
	 */
	public List getMeisai() {

		return meisai;
	}
	/**
	 * @param meisai meisaiを設定。
	 */
	public void setMeisai(List meisai) {

		this.meisai = meisai;
	}

}