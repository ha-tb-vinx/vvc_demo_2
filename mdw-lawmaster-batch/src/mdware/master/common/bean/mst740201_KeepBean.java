package mdware.master.common.bean;

import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;

import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>
 * タイトル:
 * </p>
 * <p>
 * 説明:
 * </p>
 * <p>
 * 著作権: Copyright (c) 2002
 * </p>
 * <p>
 * 会社名:
 * </p>
 * 
 * @author 未入力
 * @version 1.0
 */
public class mst740201_KeepBean {
    private static final StcLog stcLog = StcLog.getInstance();

    private List meisai = new ArrayList(); // 検索結果（明細）

    private List meisaiAdd = new ArrayList(); // 検索結果（追加分明細）
    
//  ページ遷移用ここから
	private int currentPageNum			= 0;				//表示ページ番号
	private int lastPageNum 			= 0;				//最終ページ番号
	private int maxRows 				= 0;				//取得件数
	private int startRowInPage 		= 0;				//表示開始位置
	private int endRowInPage 			= 0;				//表示終了位置
	private String listChgFlg			= "";				//詳細変更フラグ
//ページ遷移用ここまで
	
	private String KikakuTokubaiNoCd = ""; //企画特売NO 
	
	private String NameNa = ""; // 漢字名称
	
	private String KikakuStDt = ""; // 期間（開始）
	
	private String KikakuEdDt = ""; // 期間（終了） 

	private String BMCode = ""; // BMコード
    
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
     * @return KikakuTokubaiNoCd を戻します。
     */
    public String getKikakuTokubaiNoCd() {
        return KikakuTokubaiNoCd;
    }

    /**
     * @param KikakuTokubaiNoCd
     *            KikakuTokubaiNoCd を設定。
     */
    public void setKikakuTokubaiNoCd(String kikakuTokubaiNoCd) {
        KikakuTokubaiNoCd = kikakuTokubaiNoCd;
    }

    /**
     * @return NameNa を戻します。
     */
    public String getNameNa() {
        return NameNa;
    }

    /**
     * @param NameNa
     *            NameNa を設定。
     */
    public void setNameNa(String nameNa) {
        NameNa = nameNa;
    }

    /**
     * @return KikakuStDt を戻します。
     */
    public String getKikakuStDt() {
        return KikakuStDt;
    }

    /**
     * @param KikakuStDt
     *            KikakuStDt を設定。
     */
    public void setKikakuStDt(String kikakuStDt) {
        KikakuStDt = kikakuStDt;
    }
    
    /**
     * @return KikakuEdDt を戻します。
     */
    public String getKikakuEdDt() {
        return KikakuEdDt;
    }

    /**
     * @param KikakuEdDt
     *            KikakuEdDt を設定。
     */
    public void setKikakuEdDt(String kikakuEdDt) {
        KikakuEdDt = kikakuEdDt;
    }    

    /**
     * @return BMCode を戻します。
     */
    public String getBMCode() {
        return BMCode;
    }

    /**
     * @param KikakuEdDt
     *            KikakuEdDt を設定。
     */
    public void setBMCode(String bmCode) {
        BMCode = bmCode;
    }      

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
     * @param String
     *            設定する文字列
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
     * @param String
     *            設定する文字列
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
     * @param String
     *            設定する文字列
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
     * @param String
     *            設定する文字列
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
     * @param String
     *            設定する文字列
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


	/**
	 * @return currentPageNumを戻します。
	 */
	public int getCurrentPageNum() {
		return currentPageNum;
	}

	/**
	 * @param currentPageNum currentPageNumを設定。
	 */
	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	/**
	 * @return lastPageNumを戻します。
	 */
	public int getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum lastPageNumを設定。
	 */
	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return maxRowsを戻します。
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * @param maxRows maxRowsを設定。
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * @return startRowInPageを戻します。
	 */
	public int getStartRowInPage() {
		return startRowInPage;
	}

	/**
	 * @param startRowInPage startRowInPageを設定。
	 */
	public void setStartRowInPage(int startRowInPage) {
		this.startRowInPage = startRowInPage;
	}

	/**
	 * @return endRowInPageを戻します。
	 */
	public int getEndRowInPage() {
		return endRowInPage;
	}

	/**
	 * @param endRowInPage endRowInPageを設定。
	 */
	public void setEndRowInPage(int endRowInPage) {
		this.endRowInPage = endRowInPage;
	}

	/**
	 * @param listChgFlg listChgFlgを設定。
	 */
	public boolean setListChgFlg(String listChgFlg)
	{
		this.listChgFlg = listChgFlg;
		return true;
	}
	/**
	 * @return listChgFlgを戻します。
	 */
	public String getListChgFlg()
	{
		return this.listChgFlg;
	}

	/**
	 * @return 現在表示中ページ以外の明細が1個以上選択されていればtrueを戻します。
	 */
	public boolean isBackgroundListSelected() {
		boolean isChecked = false;
		for (int i = 0; i < meisai.size(); i++) {
			mst740101_BMCodeBean bean = (mst740101_BMCodeBean)meisai.get(i);
			if (getStartRowInPage() - 1 <= i  && i <= getEndRowInPage() - 1) continue;
			
			if (bean.getSentaku() != null && bean.getSentaku().equals(mst000101_ConstDictionary.RECORD_EXISTENCE)){
				isChecked = true;
				break;
			}
		}
		return isChecked;
	}
	    
}