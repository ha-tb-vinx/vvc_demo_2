package mdware.master.common.bean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステムPOS売価変更指示（基準売価）検索の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するPOS売価変更指示（基準売価）検索の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 
 * @version 1.0(2006/03/27)初版作成
 */
public class mstA00101_KeepBean
{

	private static final StcLog stcLog = StcLog.getInstance();
	
	public static final String SELECT_NO				=	"";			//無し 
	// ↓↓　2006/04/18 kim START MOD
	// public static final String SELECT_KIZYUNBAIKA	=	"1";		//1.基準売価
	// public static final String SELECT_TOKUBAIKA		=	"2";		//2.特売価 
	// public static final String SELECT_SAKUZYOKAN		=	"3";		//3.削除勧告
	// ↑↑　2006/04/18 kim END MOD
	public static final String SELECT_KIZYUNBAIKA	=	"01";		//01.基準売価
	public static final String SELECT_SAKUZYOKAN		=	"02";		//02.削除勧告
	public static final String SELECT_TOKUBAIKA		=	"03";		//03.特売価 
	public static final String SELECT_KEIRYOU		=	"11";		//11.計量器
	public static final String ASC_SORT				=	"1";		//昇順
	public static final String DESC_SORT				=	"2";		//降順
	
	
	private String selectSikibetuKb				= null;	//帳票種別
	private String sGyosyuCd						= null;	//小業種
	private String tenpoCd							= null;	//店舗コード
	private String sortFg							= null;	//ソートフラッグ

	private String selectSikibetuKbOnSearch       	= null;	//検索時-帳票種別
	private String sGyosyuCdOnSearch 				= null;	//検索時-小業種
	private String tenpoCdOnSearch       			= null;	//検索時-店舗コード

	private List meisai = new ArrayList(); //一覧の明細
	private String CurrentPageNo	= "";   // 現在表示ページ
	private String LastPageNo		= "";   // 最終ページ
	private String MaxRows			= "";   // 最大行数
	private String EndRowInPage	= "";   // 現在ページの終了行
	private String StartRowInPage	= "";   // 現在ページの開始行
   
	private String errorFlg = null;	//エラーフラグ
	private String errorMessage = null;	//エラーメッセージ
	private String[] menuBar = null;	//メニューバーアイテム
	private String mode = null;	//処理モード
	private String firstFocus = null;	//フォーカスを最初に取得するオブジェクト名
	private String insertFlg = null;	//新規処理利用可能区分
	private String updateFlg = null;	//更新処理利用可能区分
	private String deleteFlg = null;	//削除処理利用可能区分
	private String referenceFlg = null;	//照会処理利用可能区分
	private String csvFlg = null;	//CSV処理利用可能区分
	private String printFlg = null;	//印刷処理利用可能区分
	private String before_disp_id = null;	//前画面ID
	private String disp_url = null;	//現画面URL
	private String checkFlg = null;	//チェック処理判断
	private String existFlg = null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searchErrorFlg = null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateProcessFlg = null;	//更新処理内容
	private Map ctrlColor = new HashMap();	//コントロール背景色
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ

	private static final String INIT_PAGE = "mstA00203_FirstPageAlarmInit";	// 初期画面JSPを取得
	private static final String VIEW_PAGE = "mstA00204_FirstPageAlarmView";	// 照会画面JSPを取得

	/**
	 * エラーフラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setErrorFlg(String errorFlg)
	{
		this.errorFlg = errorFlg;
		return true;
	}
	/**
	 * エラーフラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getErrorFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getErrorFlg()
	{
		return this.errorFlg;
	}


	/**
	 * エラーメッセージに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorMessage("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
		return true;
	}
	/**
	 * エラーメッセージに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getErrorMessage();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getErrorMessage()
	{
		return this.errorMessage;
	}


	/**
	 * メニューバーアイテムに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMenuBar("String[]");<br>
	 * <br>
	 * @param String[] 設定する文字配列
	 */
	public boolean setMenuBar(String[] menuBar)
	{
		try
		{
			this.menuBar = menuBar;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	/**
	 * メニューバーアイテムに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMenuBar();　戻り値　文字配列<br>
	 * <br>
	 * @return String[] 文字配列
	 */
	public String[] getMenuBar()
	{
		return this.menuBar;
	}


	/**
	 * 処理モードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMode("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setMode(String mode)
	{
		this.mode = mode;
		return true;
	}
	/**
	 * 処理モードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMode();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getMode()
	{
		return this.mode;
	}


	/**
	 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setFirstFocus("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setFirstFocus(String firstFocus)
	{
		this.firstFocus = firstFocus;
		return true;
	}
	/**
	 * フォーカスを最初に取得するオブジェクト名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getFirstFocus();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getFirstFocus()
	{
		return this.firstFocus;
	}


	/**
	 * 新規処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setInsertFlg(String insertFlg)
	{
		this.insertFlg = insertFlg;
		return true;
	}
	/**
	 * 新規処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsertFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getInsertFlg()
	{
		return this.insertFlg;
	}


	/**
	 * 更新処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateFlg(String updateFlg)
	{
		this.updateFlg = updateFlg;
		return true;
	}
	/**
	 * 更新処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdateFlg()
	{
		return this.updateFlg;
	}


	/**
	 * 削除処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDeleteFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setDeleteFlg(String deleteFlg)
	{
		this.deleteFlg = deleteFlg;
		return true;
	}
	/**
	 * 削除処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getDeleteFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDeleteFlg()
	{
		return this.deleteFlg;
	}


	/**
	 * 照会処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setReferenceFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setReferenceFlg(String referenceFlg)
	{
		this.referenceFlg = referenceFlg;
		return true;
	}
	/**
	 * 照会処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getReferenceFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getReferenceFlg()
	{
		return this.referenceFlg;
	}


	/**
	 * CSV処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCsvFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCsvFlg(String csvFlg)
	{
		this.csvFlg = csvFlg;
		return true;
	}
	/**
	 * CSV処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCsvFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCsvFlg()
	{
		return this.csvFlg;
	}


	/**
	 * 印刷処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setPrintFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setPrintFlg(String printFlg)
	{
		this.printFlg = printFlg;
		return true;
	}
	/**
	 * 印刷処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getPrintFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getPrintFlg()
	{
		return this.printFlg;
	}


	/**
	 * 前画面IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBeforeDispId("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setBeforeDispId(String before_disp_id)
	{
		this.before_disp_id = before_disp_id;
		return true;
	}
	/**
	 * 前画面IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBeforeDispId();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBeforeDispId()
	{
		return this.before_disp_id;
	}


	/**
	 * 現画面URLに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDispUrl("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setDispUrl(String disp_url)
	{
		this.disp_url = disp_url;
		return true;
	}
	/**
	 * 現画面URLに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getDispUrl();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDispUrl()
	{
		return this.disp_url;
	}


	/**
	 * チェック処理判断に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCheckFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCheckFlg(String checkFlg)
	{
		this.checkFlg = checkFlg;
		return true;
	}
	/**
	 * チェック処理判断に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCheckFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCheckFlg()
	{
		return this.checkFlg;
	}


	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setExistFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setExistFlg(String existFlg)
	{
		this.existFlg = existFlg;
		return true;
	}
	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getExistFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getExistFlg()
	{
		return this.existFlg;
	}


	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSearchErrorFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSearchErrorFlg(String searchErrorFlg)
	{
		this.searchErrorFlg = searchErrorFlg;
		return true;
	}
	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSearchErrorFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSearchErrorFlg()
	{
		return this.searchErrorFlg;
	}

	
	/**
	 * 更新処理内容に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateProcessFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateProcessFlg(String updateProcessFlg)
	{
		this.updateProcessFlg = updateProcessFlg;
		return true;
	}
	/**
	 * 更新処理内容に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateProcessFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdateProcessFlg()
	{
		return this.updateProcessFlg;
	}


	/**
	 * コントロール背景色に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor("Map");<br>
	 * <br>
	 * @param Map 設定するMap配列
	 */
	public boolean setCtrlColor(Map ctrlColor)
	{
		try
		{
			this.ctrlColor = ctrlColor;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	/**
	 * コントロール背景色に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor();　戻り値　Map配列<br>
	 * <br>
	 * @return String[] Map配列
	 */
	public Map getCtrlColor()
	{
		return this.ctrlColor;
	}


	/**
	 * 明細配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMeisai();　戻り値　明細配列<br>
	 * <br>
	 * @return List 明細配列
	 */
	public List getMeisai() {
		return meisai;
	}

	/**
	 * 明細配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMeisai(List);<br>
	 * <br>
	 * @param List 設定する明細配列
	 */
	public void setMeisai(List list) {
		meisai = list;
	}
	/**
	 * 作成年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertTs("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setInsertTs(String insert_ts)
		{
			this.insert_ts = insert_ts;
			return true;
		}
	/**
	 * 作成年月日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsertTs();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getInsertTs()
		{
			return this.insert_ts;
		}


	/**
	 * 作成者社員IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertUserId("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setInsertUserId(String insert_user_id)
		{
			this.insert_user_id = insert_user_id;
			return true;
		}
	/**
	 * 作成者社員IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsertUserId();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getInsertUserId()
		{
			return this.insert_user_id;
		}


	/**
	 * 更新年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateTs("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setUpdateTs(String update_ts)
		{
			this.update_ts = update_ts;
			return true;
		}
	/**
	 * 更新年月日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateTs();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getUpdateTs()
		{
			return this.update_ts;
		}


	/**
	 * 更新者社員IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateUserId("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setUpdateUserId(String update_user_id)
		{
			this.update_user_id = update_user_id;
			return true;
		}
	/**
	 * 更新者社員IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateUserId();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getUpdateUserId()
		{
			return this.update_user_id;
		}


	/**
	 * 削除フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDeleteFg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setDeleteFg(String delete_fg)
		{
			this.delete_fg = delete_fg;
			return true;
		}
	/**
	 * 削除フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getDeleteFg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getDeleteFg()
		{
			return this.delete_fg;
		}



	/**
	 * 現在表示ページに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCurrentPageNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCurrentPageNo() {
		return CurrentPageNo;
	}

	/**
	 * 現在ページの終了行に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getEndRowInPage();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getEndRowInPage() {
		return EndRowInPage;
	}

	/**
	 * 最終ページに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLastPageNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getLastPageNo() {
		return LastPageNo;
	}

	/**
	 * MaxRowsに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLastPageNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getMaxRows() {
		return MaxRows;
	}

	/**
	 * 現在ページの開始行に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLastPageNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getStartRowInPage() {
		return StartRowInPage;
	}

	/**
	 * 現在表示ページに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCurrentPageNo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setCurrentPageNo(String string) {
		CurrentPageNo = string;
	}

	/**
	 * 現在ページの終了行に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setEndRowInPage("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setEndRowInPage(String string) {
		EndRowInPage = string;
	}

	/**
	 * 最終ページの終了行に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLastPageNo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setLastPageNo(String string) {
		LastPageNo = string;
	}

	/**
	 * 最大行数に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMaxRows("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setMaxRows(String string) {
		MaxRows = string;
	}

	/**
	 * 現在ページの開始行に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setStartRowInPage("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setStartRowInPage(String string) {
		StartRowInPage = string;
	}



	/** 検索時- 小業種対するゲッター
	 * @return
	 */
	public String getSGyosyuCdOnSearch() {
		return sGyosyuCdOnSearch;
	}

	/** 検索時-帳票種別に対するゲッター
	 * @return
	 */
	public String getSelectSikibetuKbOnSearch() {
		return selectSikibetuKbOnSearch;
	}

	/** 検索時-店舗コードに対するゲッター
	 * @return
	 */
	public String getTenpoCdOnSearch() {
		return tenpoCdOnSearch;
	}

	/**
	 * 検索時- 小業種対するセッター
	 * @param string 設定する文字列
	 */
	public void setSGyosyuCdOnSearch(String string) {
		sGyosyuCdOnSearch = string;
	}

	/**
	 * 検索時-帳票種別に対するセッター
	 * @param string 設定する文字列
	 */
	public void setSelectSikibetuKbOnSearch(String string) {
		selectSikibetuKbOnSearch = string;
	}

	/**
	* 検索時-店舗コードに対するセッター
	* @param string 設定する文字列
	*/
	public void setTenpoCdOnSearch(String string) {
		tenpoCdOnSearch = string;
	}
	


	/** 小業種に対するゲッターー
	 * @return
	 */
	public String getSGyosyuCd() {
		return sGyosyuCd;
	}

	/**帳票種別に対するゲッター
 	* @return
 	*/
	public String getSelectSikibetuKb() {
		return selectSikibetuKb;
	}

	/**店舗コードに対するゲッター
	 * @return
	 */
	public String getTenpoCd() {
		return tenpoCd;
	}

	/**選択に対するセッター
	 * @param string
	 */
	public void setSGyosyuCd(String string) {
		sGyosyuCd = string;
	}

	/**選択に対するセッター
 	* @param string
 	*/
	public void setSelectSikibetuKb(String string) {
		selectSikibetuKb = string;
	}

	/** 選択に対するセッター
	 * @param string
	 */
	public void setTenpoCd(String string) {
		tenpoCd = string;
	}

  /**
   * 初期画面URL取得(ログ出力有り)
   * <br>
   * Ex)<br>
   * getInitUrl("logHeader","logMsg") -&gt; String<br>
   * <br>
   * @param String logHeader
   * @param String logMsg
   * @return		String
   */
	public String getInitUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	INIT_PAGE;
	}
	/**
	 * 初期画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getInitUrl()
	{
		return	INIT_PAGE;
	}
	/**
	 * 照会画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getViewUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getViewUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	VIEW_PAGE;
	}
	/**
	 * 照会画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getViewUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getViewUrl()
	{
		return	VIEW_PAGE;
	}
	
	
	/**	ソートフラッグゲッタ
	 * @return
	 */
	public String getSortFg() {
		return sortFg;
	}

	/**	ソートフラッグセッタ
	 * @param string
	 */
	public void setSortFg(String string) {
		sortFg = string;
	}

}

