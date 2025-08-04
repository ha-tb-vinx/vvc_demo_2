/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別販区マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別販区マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
//BUGNO-S052 2005.05.14 Y.Jozawa START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Jozawa END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別販区マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別販区マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/16)初版作成
 */
public class mst550201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KANRI_KANJI_RN_MAX_LENGTH 		 = 20;//管理名の長さ
	public static final int YUKO_DT_MAX_LENGTH 				 = 8;//有効日の長さ

	private String yuko_dt = null;	//有効日
	private String kanri_kb = null;	//管理区分
	private String kanri_cd = null;	//管理コード
	private String kanri_kanji_rn 			= "";		//管理名
	private String tenpo_cd = null;	//店舗コード (FK)
	private String tenhanku_cd = null;	//店販区コード
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	private String processingdivision = null;	//処理状況
	private String errorflg = null;	//エラーフラグ
	private String errormessage = null;	//エラーメッセージ
	private String[] menubar = null;	//メニューバーアイテム
	private Map mode = new HashMap();	//処理モード
	private String firstfocus = null;	//フォーカスを最初に取得するオブジェクト名
	private String insertflg = null;	//新規処理利用可能区分
	private String updateflg = null;	//更新処理利用可能区分
	private String deleteflg = null;	//削除処理利用可能区分
	private String referenceflg = null;	//照会処理利用可能区分
	private String csvflg = null;	//CSV処理利用可能区分
	private String printflg = null;	//印刷処理利用可能区分
	private String before_disp_id = null;	//前画面ID
	private String disp_url = null;	//現画面URL
	private String checkflg = null;	//チェック処理判断
	private String existflg = null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searcherrorflg = null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateprocessflg = null;	//更新処理内容
	private Map CtrlColor					= new HashMap();	// コントロール前景色
	private List yukochklst = new ArrayList();
	private List updateprocesslst = new ArrayList();
	private List yukochklstAll = new ArrayList();
	private List updateprocesslstAll = new ArrayList();
	private boolean seisen = false;//生鮮担当者判断フラグ
//	BUGNO-S005 2005.04.21 S.Takahashi START
	  private String changeflg	= null;	//項目変更フラグ
//	BUGNO-S005 2005.04.21 S.Takahashi END

//BUGNO-S052 2005.05.14 Y.Jozawa START
	private static final String INIT_PAGE = "mst550101_TenHankuInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst550201_TenHankuEdit";	// 編集画面JSPを取得
	private static final String VIEW_PAGE = "";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Jozawa END

	/**
	 * 生鮮担当者判断フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCopyKanriKanjiRn();　戻り値　生鮮担当者判断フラグ<br>
	 * <br>
	 * @return boolean 生鮮担当者判断フラグ
	 */
	public boolean isSeisen() {
		return seisen;
	}

	/**
	 * 生鮮担当者判断フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSeisen(boolean);<br>
	 * <br>
	 * @param String 設定する生鮮担当者判断フラグ
	 */
	public void setSeisen(boolean b) {
		seisen = b;
	}

/**
 * 有効日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setYukoDt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
/**
 * 有効日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getYukoDt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getYukoDt()
	{
		return this.yuko_dt;
	}
	public String getYukoDtString()
	{
		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
	}


/**
 * 管理区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanriKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanriKb(String kanri_kb)
	{
		this.kanri_kb = kanri_kb;
		return true;
	}
/**
 * 管理区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanriKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanriKb()
	{
		return this.kanri_kb;
	}


/**
 * 管理コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanriCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanriCd(String kanri_cd)
	{
		this.kanri_cd = kanri_cd;
		return true;
	}
/**
 * 管理コードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanriCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanriCd()
	{
		return this.kanri_cd;
	}


/**
 * 店舗コード (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTenpoCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
/**
 * 店舗コード (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTenpoCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTenpoCd()
	{
		return this.tenpo_cd;
	}


/**
 * 店販区コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setTenhankuCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTenhankuCd(String tenhanku_cd)
	{
		this.tenhanku_cd = tenhanku_cd;
		return true;
	}
/**
 * 店販区コードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTenhankuCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTenhankuCd()
	{
		return this.tenhanku_cd;
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
 * 処理状況に対するセッター<br>
 * <br>
 * Ex)<br>
 * setProcessingdivision("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setProcessingDivision(String processingdivision)
	{
		this.processingdivision = processingdivision;
		return true;
	}
/**
 * 処理状況に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getProcessingdivision();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getProcessingDivision()
	{
		return this.processingdivision;
	}


/**
 * エラーフラグに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrorflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorFlg(String errorflg)
	{
		this.errorflg = errorflg;
		return true;
	}
/**
 * エラーフラグに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getErrorflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getErrorFlg()
	{
		return this.errorflg;
	}


/**
 * エラーメッセージに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrormessage("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorMessage(String errormessage)
	{
		this.errormessage = errormessage;
		return true;
	}
/**
 * エラーメッセージに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getErrormessage();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getErrorMessage()
	{
		return this.errormessage;
	}


/**
 * メニューバーアイテムに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMenubar("String[]");<br>
 * <br>
 * @param String[] 設定する文字配列
 */
	public boolean setMenuBar(String[] menubar)
	{
		try
		{
			this.menubar = menubar;
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
 * getMenubar();　戻り値　文字配列<br>
 * <br>
 * @return String[] 文字配列
 */
	public String[] getMenuBar()
	{
		return this.menubar;
	}


/**
 * 処理モードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMode("Map");<br>
 * <br>
 * @param Map 設定するMap配列
 */
	public boolean setMode(Map mode)
	{
		try
		{
			this.mode = mode;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 処理モードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMode();　戻り値　Map配列<br>
 * <br>
 * @return String[] Map配列
 */
	public Map getMode()
	{
		return this.mode;
	}


/**
 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFirstfocus("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setFirstFocus(String firstfocus)
	{
		this.firstfocus = firstfocus;
		return true;
	}
/**
 * フォーカスを最初に取得するオブジェクト名に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getFirstfocus();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getFirstFocus()
	{
		return this.firstfocus;
	}


/**
 * 新規処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertFlg(String insertflg)
	{
		this.insertflg = insertflg;
		return true;
	}
/**
 * 新規処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertFlg()
	{
		return this.insertflg;
	}


/**
 * 更新処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateFlg(String updateflg)
	{
		this.updateflg = updateflg;
		return true;
	}
/**
 * 更新処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateFlg()
	{
		return this.updateflg;
	}


/**
 * 削除処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDeleteflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDeleteFlg(String deleteflg)
	{
		this.deleteflg = deleteflg;
		return true;
	}
/**
 * 削除処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDeleteflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDeleteFlg()
	{
		return this.deleteflg;
	}


/**
 * 照会処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setReferenceflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setReferenceFlg(String referenceflg)
	{
		this.referenceflg = referenceflg;
		return true;
	}
/**
 * 照会処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getReferenceflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getReferenceFlg()
	{
		return this.referenceflg;
	}


/**
 * CSV処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCsvflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCsvFlg(String csvflg)
	{
		this.csvflg = csvflg;
		return true;
	}
/**
 * CSV処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCsvflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getCsvFlg()
	{
		return this.csvflg;
	}


/**
 * 印刷処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setPrintflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setPrintFlg(String printflg)
	{
		this.printflg = printflg;
		return true;
	}
/**
 * 印刷処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getPrintflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getPrintFlg()
	{
		return this.printflg;
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
 * setCheckflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCheckFlg(String checkflg)
	{
		this.checkflg = checkflg;
		return true;
	}
/**
 * チェック処理判断に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCheckflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getCheckFlg()
	{
		return this.checkflg;
	}


/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setExistflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setExistflg(String existflg)
	{
		this.existflg = existflg;
		return true;
	}
/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getExistflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getExistflg()
	{
		return this.existflg;
	}


/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSearcherrorflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSearcherrorflg(String searcherrorflg)
	{
		this.searcherrorflg = searcherrorflg;
		return true;
	}
/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSearcherrorflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSearcherrorflg()
	{
		return this.searcherrorflg;
	}

	/**
	 * 有効日情報配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getYukoChkLst();　戻り値　有効日情報配列<br>
	 * <br>
	 * @return List 有効日情報配列
	 */
	public List getYukoChkLst() {
		return yukochklst;
	}

	/**
	 * 有効日情報配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setYukoChkLst(List);<br>
	 * <br>
	 * @param List 設定する有効日情報配列
	 */
	public void setYukoChkLst(List list) {
		yukochklst = list;
	}

	/**
	 * 更新処理内容配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateProcessLst();　戻り値　更新処理内容配列<br>
	 * <br>
	 * @return List 更新処理内容配列
	 */
	public List getUpdateProcessLst() {
		return updateprocesslst;
	}

	/**
	 * 更新処理内容配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateProcessLst(List);<br>
	 * <br>
	 * @param List 設定する更新処理内容配列
	 */
	public void setUpdateProcessLst(List list) {
		updateprocesslst = list;
	}

	/**
	 * 有効日情報配列（全件）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getYukoChkLstAll();　戻り値　有効日情報配列（全件）<br>
	 * <br>
	 * @return List 有効日情報配列（全件）
	 */
	public List getYukoChkLstAll() {
		return yukochklstAll;
	}

	/**
	 * 有効日情報配列（全件）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setYukoChkLstAll(List);<br>
	 * <br>
	 * @param List 設定する有効日情報配列（全件）
	 */
	public void setYukoChkLstAll(List list) {
		yukochklstAll = list;
	}

	/**
	 * 更新処理内容配列（全件）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateProcessLstAll();　戻り値　更新処理内容配列（全件）<br>
	 * <br>
	 * @return List 更新処理内容配列（全件）
	 */
	public List getUpdateProcessLstAll() {
		return updateprocesslstAll;
	}

	/**
	 * 更新処理内容配列（全件）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateProcessLstAll(List);<br>
	 * <br>
	 * @param List 設定する更新処理内容配列（全件）
	 */
	public void setUpdateProcessLstAll(List list) {
		updateprocesslstAll = list;
	}

/**
 * 更新処理内容に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateprocessflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateProcessFlg(String updateprocessflg)
	{
		this.updateprocessflg = updateprocessflg;
		return true;
	}
/**
 * 更新処理内容に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateprocessflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateProcessFlg()
	{
		return this.updateprocessflg;
	}

	// CtrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map CtrlColor)
	{
		this.CtrlColor = CtrlColor;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.CtrlColor;
	}

	// kanri_kanji_rnに対するセッターとゲッターの集合
	public boolean setKanriKanjiRn(String kanri_kanji_rn)
	{
		this.kanri_kanji_rn = kanri_kanji_rn;
		return true;
	}
	public String getKanriKanjiRn()
	{
		return cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getKanriKanjiRnString()
	{
		return "'" + cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getKanriKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH));
	}

	// changeflgに対するセッターとゲッターの集合
	public boolean setChangeFlg(String changeflg)
	{
		this.changeflg = changeflg;
		return true;
	}
	public String getChangeFlg()
	{
		return this.changeflg;
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
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}

//BUGNO-S052 2005.05.14 SIRIUS START
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
		  if(this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorflg.equals("")){
			  //通常系
			  if(!this.errormessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errormessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errormessage);
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
	   * 登録画面URL取得(ログ出力有り)
	   * <br>
	   * Ex)<br>
	   * getEditUrl("logHeader","logMsg") -&gt; String<br>
	   * <br>
	   * @param String logHeader
	   * @param String logMsg
	   * @return		String
	   */
	  public String getEditUrl(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorflg.equals("")){
			  //通常系
			  if(!this.errormessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errormessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errormessage);
		  }
		
		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }
		
		  return	EDIT_PAGE;
	  }
	  /**
	   * 登録画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getEditUrl()
	  {
		  return	EDIT_PAGE;
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
		  if(this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorflg.equals("")){
			  //通常系
			  if(!this.errormessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errormessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errormessage);
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
//BUGNO-S052 2005.05.14 SIRIUS END
}
