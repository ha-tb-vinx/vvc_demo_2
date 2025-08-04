/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）配送先マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する配送先マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Sirius START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Sirius END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）配送先マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する配送先マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/16)初版作成
 */
public class mst460301_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String kanri_kb = null;	//管理区分
	private String kanri_cd = null;	//管理コード
	private String kanji_rn = null;	//略式名称(漢字)
	private String haisou_kanji_rn = null;	//配送先略式名称(漢字)

	private String shori_kb = null;	//処理状況
	private String haisosaki_cd4 = null;	//配送先コード4桁
	private String haisosaki_cd5 = null;	//配送先コード5桁
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
	private String searchErrorFlg = null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateProcessFlg = null;	//更新処理内容
	private Map ctrlColor = new HashMap();	//コントロール背景色
	private String copy_kanri_cd 			= "";//コピー販区コード
	private String copy_kanri_kanji_rn 	= "";//コピー販区名
	private boolean seisen = false;//生鮮担当者判断フラグ
	private List meisai = new ArrayList();
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	//BUGNO-S005 20050421 S.Murata START
	private String chgFlg = "false";	//画面変更フラグ
	//BUGNO-S005 20050421 S.Murata END
//BUGNO-S052 2005.05.14 Sirius START
	private static final String INIT_PAGE   = "mst460101_HaisouInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE   = "mst460201_HaisouEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE   = "mst460301_HaisouView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Sirius END

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
 * 配送先コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setHaisosakiCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHaisosakiCd4(String haisosaki_cd)
	{
		this.haisosaki_cd4 = haisosaki_cd;
		return true;
	}
/**
 * 配送先コードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHaisosakiCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getHaisosakiCd5()
	{
		return this.haisosaki_cd5;
	}

/**
 * 配送先コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setHaisosakiCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHaisosakiCd5(String haisosaki_cd)
	{
		this.haisosaki_cd5 = haisosaki_cd;
		return true;
	}
	
	
	/**
	 * 配送先コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHaisosakiCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHaisosakiCd4()
		{
			return this.haisosaki_cd4;
		}


	/**
	 * 配送先略式名称(漢字)に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHaisouKanjiRn(String haisou_kanji_rn)
		{
			this.haisou_kanji_rn = haisou_kanji_rn;
			return true;
		}
	/**
	 * 配送先略式名称(漢字)に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHaisouKanjiRn()
		{
			return this.haisou_kanji_rn;
		}



/**
 * 略式名称(漢字)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanjiRn("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanjiRn(String kanji_rn)
	{
		this.kanji_rn = kanji_rn;
		return true;
	}
/**
 * 略式名称(漢字)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanjiRn();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanjiRn()
	{
		return this.kanji_rn;
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
 * 処理状況に対するセッター<br>
 * <br>
 * Ex)<br>
 * setShoriKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setShoriKb(String shori_kb)
	{
		this.shori_kb = shori_kb;
		return true;
	}
/**
 * 処理状況に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getShoriKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getShoriKb()
	{
		return this.shori_kb;
	}

	/**
	 * コピー販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCopyKanriCd("コピー販区コード");<br>
	 * <br>
	 * @param String 設定するコピー販区コード
	 */
	public void setCopyKanriCd(String copy_kanri_cd)
	{
		this.copy_kanri_cd = copy_kanri_cd;
	}
	/**
	 * コピー販区コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCopyKanriCd();　戻り値　コピー販区コード<br>
	 * <br>
	 * @return String コピー販区コード
	 */
	public String getCopyKanriCd()
	{
		return this.copy_kanri_cd;
	}
	
	/**
	 * コピー販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCopyKanriKanjiRn("コピー販区名");<br>
	 * <br>
	 * @param String 設定するコピー販区名
	 */
	public void setCopyKanriKanjiRn(String copy_kanri_kanji_rn)
	{
		this.copy_kanri_kanji_rn = copy_kanri_kanji_rn;
	}
	/**
	 * コピー販区コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCopyKanriKanjiRn();　戻り値　コピー販区名<br>
	 * <br>
	 * @return String コピー販区名
	 */
	public String getCopyKanriKanjiRn()
	{
		return this.copy_kanri_kanji_rn;
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

	//BUGNO-S005 20050421 S.Murata START
	/**
	 * 画面変更フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getChgFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getChgFlg() {
		return chgFlg;
	}

	/**
	 * 画面変更フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setChgFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setChgFlg(String string) {
		chgFlg = string;
	}
	//BUGNO-S005 20050421 S.Murata END
//	BUGNO-S052 2005.05.14 SIRIUS START
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
//	BUGNO-S052 2005.05.14 SIRIUS START

}
