/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）自動発注制御マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する自動発注制御マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/31)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Jozawa START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Jozawa END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）自動発注制御マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する自動発注制御マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/31)初版作成
 */
public class mst490201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String kanri_kb = null;	//管理区分
	private String kanri_cd = null;	//管理コード
	private String kanri_kanji_rn 			= "";		//管理名
	private String tenpo_cd = null;	//店舗コード (FK)
	private String yuko_dt = null;	//有効日
	private String jido_hachu_kb = null;	//自動発注区分
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	private String processingDivision = null;	//処理状況
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
	private String DecisionFlg = null;	//
	private String updateProcessFlg = null;	//更新処理内容
	private Map ctrlColor = new HashMap();	//コントロール背景色
	private List yukochklst = new ArrayList();
	private List updateprocesslst = new ArrayList();
	private List yukochklstAll = new ArrayList();
	private List updateprocesslstAll = new ArrayList();
	private List tenpolst = new ArrayList();
	private List selectrow = new ArrayList();
	private List selectrowAll = new ArrayList();
	private boolean seisen = false;//生鮮担当者判断フラグ
	private boolean unyou = false;//運用担当者判断フラグ

//BUGNO-S052 2005.05.14 Y.Jozawa START
	private static final String INIT_PAGE = "mst490101_JidohachuseigyoInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst490201_JidohachuseigyoEdit";	// 編集画面JSPを取得
	private static final String VIEW_PAGE = "mst490301_JidohachuseigyoView";	// 削除・照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Jozawa END

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
 * 管理名に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanriKanjiRn("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanriKanjiRn(String kanri_kanji_rn)
	{
		this.kanri_kanji_rn = kanri_kanji_rn;
		return true;
	}

/**
 * 管理名に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanriKanjiRn();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanriKanjiRn()
	{
		return this.kanri_kanji_rn;
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


/**
 * 自動発注区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setJidoHachuKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setJidoHachuKb(String jido_hachu_kb)
	{
		this.jido_hachu_kb = jido_hachu_kb;
		return true;
	}
/**
 * 自動発注区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getJidoHachuKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getJidoHachuKb()
	{
		return this.jido_hachu_kb;
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
 * setProcessingDivision("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setProcessingDivision(String processingDivision)
	{
		this.processingDivision = processingDivision;
		return true;
	}
/**
 * 処理状況に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getProcessingDivision();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getProcessingDivision()
	{
		return this.processingDivision;
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
 * に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDecisionFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDecisionFlg(String DecisionFlg)
	{
		this.DecisionFlg = DecisionFlg;
		return true;
	}
/**
 * に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDecisionFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDecisionFlg()
	{
		return this.DecisionFlg;
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
	 * 店舗配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoLst();　戻り値　更新処理内容配列<br>
	 * <br>
	 * @return List 店舗内容配列
	 */
	public List getTenpoLst() {
		return tenpolst;
	}

	/**
	 * 店舗配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoLst(List);<br>
	 * <br>
	 * @param List 店舗内容配列
	 */
	public void setTenpoLst(List list) {
		tenpolst = list;
	}

	/**
	 * 行選択配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSelectRowLst();　戻り値　行選択内容配列<br>
	 * <br>
	 * @return List 行選択内容配列
	 */
	public List getSelectRowLst() {
		return selectrow;
	}

	/**
	 * 行選択配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSelectRowLst(List);<br>
	 * <br>
	 * @param List 設定する行選択内容配列
	 */
	public void setSelectRowLst(List list) {
		selectrow = list;
	}

	/**
	 * 行選択配列（全行）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSelectRowLstAll();　戻り値　行選択内容配列（全行）<br>
	 * <br>
	 * @return List 行選択内容配列（全行）
	 */
	public List getSelectRowLstAll() {
		return selectrowAll;
	}

	/**
	 * 行選択配列（全行）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSelectRowLstAll(List);<br>
	 * <br>
	 * @param List 設定する行選択内容配列（全行）
	 */
	public void setSelectRowLstAll(List list) {
		selectrowAll = list;
	}

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
	 * 運用担当者判断フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * isUnyou();　戻り値　運用担当者判断フラグ<br>
	 * <br>
	 * @return boolean 運用担当者判断フラグ
	 */
	public boolean isUnyou() {
		return unyou;
	}

	/**
	 * 運用担当者判断フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUnyou(boolean);<br>
	 * <br>
	 * @param String 設定する運用担当者判断フラグ
	 */
	public void setUnyou(boolean b) {
		unyou = b;
	}

//BUGNO-S052 2005.05.14 SIRIUS END
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
//BUGNO-S052 2005.05.14 SIRIUS END
}
