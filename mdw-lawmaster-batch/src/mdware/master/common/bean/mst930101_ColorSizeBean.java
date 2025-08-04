/**
 * <p>タイトル: 新ＭＤシステム カラーサイズマスタ検索画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するカラーサイズマスタ検索画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</P>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.0 (2006/03/17) D.Matsumoto 初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム カラーサイズマスタ検索画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するカラーサイズマスタ検索画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</P>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.0 (2006/03/17) D.Matsumoto 初版作成
 */
public class mst930101_ColorSizeBean
{

	private static final StcLog stcLog = StcLog.getInstance();
	
	//最大桁数設定
	public static final int DATE_MAX_LENGTH = 8;				//日付
	public static final int HINSYU_CD_MAX_LENGTH = 4;			//品種コード
	public static final int HINSYU_NM_MAX_LENGTH = 80;			//品種名称
	public static final int COLOR_CD_MAX_LENGTH = 2;			//カラーコード
	public static final int COLOR_NM_MAX_LENGTH = 20;			//カラー名称
	public static final int SIZE_CD_MAX_LENGTH = 2;				//サイズコード
	public static final int SIZE_NM_MAX_LENGTH = 30;			//サイズ名称
	public static final int COLOR_SIZE_CD_MAX_LENGTH = 3;		//カラーサイズコード
	public static final int INSERT_TS_MAX_LENGTH = 20;			//作成年月日
	public static final int UPDATE_TS_MAX_LENGTH = 20;			//更新年月日
	public static final int DELETE_FG_MAX_LENGTH = 1;			//削除フラグ
	public static final int KENSAKU_KB_MAX_LENGTH = 1;			//検索区分

	//画面表示項目設定
	private String hinsyu_cd = null;				//品種コード
	private String hinsyu_nm = null;				//品種名称
	private String color_cd = null;					//カラーコード
	private String color_nm = null;					//カラー名称
	private String size_cd = null;					//サイズコード
	private String size_nm = null;					//サイズ名称
	private String color_size_cd = null;			//カラーサイズコード
	private String insert_ts = null;				//作成年月日
	private String insert_user_id = null;			//作成者社員ID
	private String update_ts = null;				//更新年月日
	private String update_user_id = null;			//更新者社員ID
	private String delete_fg = null;				//削除フラグ
	private String kensaku_kb = null;				//検索区分

	private List Meisai = new ArrayList();//明細行取得
	
	//ページ遷移用
	private String CurrentPageNo	= "";   // 現在表示ページ
	private String LastPageNo		= "";   // 最終ページ
	private String MaxRows			= "";   // 最大行数
	private String EndRowInPage	= "";   // 現在ページの終了行
	private String StartRowInPage	= "";   // 現在ページの開始行


	private String errorFlg = null;		//エラーフラグ
	private String errorMessage = null;	//エラーメッセージ
	private String[] menuBar = null;		//メニューバーアイテム
	private String firstFocus = null;		//フォーカスを最初に取得するオブジェクト名
	private String insertFlg = null;		//新規処理利用可能区分
	private String updateFlg = null;		//更新処理利用可能区分
	private String deleteFlg = null;		//削除処理利用可能区分
	private String referenceFlg = null;	//照会処理利用可能区分
	private String csvFlg = null;			//CSV処理利用可能区分
	private String printFlg = null;		//印刷処理利用可能区分
	private String before_disp_id = null;	//前画面ID
	private String disp_url = null;		//現画面URL
	private String checkFlg = null;		//チェック処理判断
	private String existFlg = null;		//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searchErrorFlg = null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateProcessFlg = null;	//更新処理内容
	private Map ctrlColor = new HashMap();	//コントロール背景色

	private static final String INIT_PAGE = "mst930101_ColorSizeInit";	// 初期画面JSPを取得
	private static final String VIEW_PAGE = "mst930201_ColorSizeView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得

	/**
	 * DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	 */
	private boolean createDatabase = false;
	protected void setCreateDatabase()
	{
		createDatabase = true;
	}
	public boolean isCreateDatabase()
	{
		return createDatabase;
	}
	
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

	
	// Meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(List Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
	public List getMeisai() {
		return this.Meisai;
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
	
	//品種コードに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH);
	}
	
	//品種コードに対するセッターとゲッターの集合
	public boolean setHinsyuNm(String hinsyu_nm)
	{
		this.hinsyu_nm = hinsyu_nm;
		return true;
	}
	public String getHinsyuNm()
	{
		return cutString(this.hinsyu_nm, HINSYU_NM_MAX_LENGTH);
	}
	
	//カラーコードに対するセッターとゲッターの集合
	public boolean setColorCd(String color_cd)
	{
		this.color_cd = color_cd;
		return true;
	}
	public String getColorCd()
	{
		return cutString(this.color_cd, COLOR_CD_MAX_LENGTH);
	}
	
	//サイズ名称に対するセッターとゲッターの集合
	public boolean setColorNm(String color_nm)
	{
		this.color_nm = color_nm;
		return true;
	}
	public String getColorNm()
	{
		return cutString(this.color_nm, COLOR_NM_MAX_LENGTH);
	}
	
	
	//サイズコードに対するセッターとゲッターの集合
	public boolean setSizeCd(String size_cd)
	{
		this.size_cd = size_cd;
		return true;
	}
	public String getSizeCd()
	{
		return cutString(this.size_cd, SIZE_CD_MAX_LENGTH);
	}

//	//販区コードに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return cutString(this.hanku_cd, HANKU_CD_MAX_LENGTH);
//	}
//	
//	//販区コードに対するセッターとゲッターの集合
//	public boolean setHankuNm(String hanku_nm)
//	{
//		this.hanku_nm = hanku_nm;
//		return true;
//	}
//	public String getHankuNm()
//	{
//		return cutString(this.hanku_nm, HANKU_NM_MAX_LENGTH);
//	}
	


	//サイズ名称に対するセッターとゲッターの集合
	public boolean setSizeNm(String size_nm)
	{
		this.size_nm = size_nm;
		return true;
	}
	public String getSizeNm()
	{
		return cutString(this.size_nm, SIZE_NM_MAX_LENGTH);
	}
	
	//カラーサイズコードに対するセッターとゲッターの集合
	public boolean setColorSizeCd(String color_size_cd)
	{
		this.color_size_cd = color_size_cd;
		return true;
	}
	public String getColorSizeCd()
	{
		return cutString(this.color_size_cd, COLOR_SIZE_CD_MAX_LENGTH);
	}
	//検索区分に対するセッターとゲッターの集合
	public boolean setKensakuKb(String kensaku_kb)
	{
		this.kensaku_kb = kensaku_kb;
		return true;
	}
	public String getKensakuKb()
	{
		return cutString(this.kensaku_kb, KENSAKU_KB_MAX_LENGTH);
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

	/**
	 * 権限エラー画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getKengenErr("logHeader") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @return		String
	 */
	public String getKengenErr(String logHeader)
	{
		stcLog.getLog().error(logHeader + InfoStrings.getInstance().getInfo("49999"));
		return KENGEN_PAGE;
	}
	
	/**
	 * コンストラクタ
	 */
	public mst930101_ColorSizeBean () {
		
		this.hinsyu_cd = "";
		this.hinsyu_nm = "";
		this.color_cd = "";
		this.color_nm = "";
		this.size_cd = "";
//		this.hanku_cd = "";
//		this.hanku_nm = "";
		this.size_nm = "";
		this.color_size_cd = "";
		this.insert_ts = "";
		this.insert_user_id = "";
		this.update_ts = "";
		this.update_user_id = "";
		this.delete_fg = "";
		this.kensaku_kb = "";
	
	}
	
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return 
		
		" hinsyu_cd = "+ getHinsyuCd()
		+" hinsyu_nm = "+ getHinsyuNm()
		+" color_cd = "+ getColorCd()
		+" color_nm = "+ getColorNm()
		+" size_cd = "+ getSizeCd()
		+" size_nm = "+ getSizeNm()
		+" color_size_cd = "+ getColorSizeCd()
		+" insert_ts = "+ getInsertTs()
		+" insert_user_id = "+ getInsertUserId()
		+" update_ts = "+ getUpdateTs()
		+" update_user_id = "+ getUpdateUserId()
		+" delete_fg = "+ getDeleteFg()
		+" createDatabase  = " + createDatabase ;
	}
	
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst250101_SyohinAkibanBean コピー後のクラス
	 */
	public mst930101_ColorSizeBean createClone()
	{
		mst930101_ColorSizeBean bean = new mst930101_ColorSizeBean();
		
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinsyuNm(this.hinsyu_nm);
		bean.setColorCd(this.color_cd);
		bean.setColorNm(this.color_nm);
		bean.setSizeCd(this.size_cd);
		bean.setSizeNm(this.size_nm);
		bean.setColorSizeCd(this.color_size_cd);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		if(this.isCreateDatabase()) bean.setCreateDatabase();
		return bean;
	}

	
	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o )
	{
		if( o == null ) return false;
		if( !( o instanceof mst930101_ColorSizeBean ) ) return false;
		return this.toString().equals( o.toString() );
	}
	
	
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max )
	{
		if( base == null ) return null;
		String wk = "";
		for( int pos = 0,count = 0; pos < max && pos < base.length(); pos++ )
		{
			try
			{
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			}
			catch(Exception e)
			{

				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");

			}
		}
		return wk;
	}
}
