/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品自動採番枠登録画面の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品自動採番枠登録画面の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品自動採番枠登録画面の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品自動採番枠登録画面の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/16)初版作成
 */
public class mst660201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int P_KANRI_KB_MAX_LENGTH			 = 1;//管理区分の長さ
	public static final int HANKU_CD_MAX_LENGTH				 = 4;//販区コードの長さ
	public static final int HINSYU_CD_MAX_LENGTH				 = 4;//品種コードの長さ
	public static final int START_TANPIN_CD_MAX_LENGTH		 = 3;//開始単品コードの長さ
	public static final int END_TANPIN_CD_MAX_LENGTH			 = 3;//終了単品コードの長さ
	public static final int HINSYU_CHECK_CD_MAX_LENGTH		 = 4;//品種コード（更新チェック用）コードの長さ
	//public static final int TENPO_CD_MAX_LENGTH				 = 4;//開始管理コードの長さ
	public static final int TENPO_CD_MAX_LENGTH				 = 6;//開始管理コードの長さ
	public static final int TENPO_NA_MAX_LENGTH				 = 20;//販区・品種名の長さ

	private String p_kanri_kb = null;//管理区分
	private String hanku_cd = null;//販区コード
	private String hinsyu_cd = null;//品種コード
	private String tenpo_cd = null;//開始管理コード
	private String tenpo_na = null;//販区・品種名
	private String retrieve_ts   = null;//データの検索（取得）日時
	private List chk_box_lst = new ArrayList();//チェックボックスのフラグリスト

	public static final int DB_HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	private String db_hanku_cd = null;//販区コード
	private String movepos = null;	//ページ遷移フラグ

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
	private List updateprocesslst = new ArrayList();


	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
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
	 * チェックボックスのフラグリストに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMeisai();　戻り値　更新処理内容配列<br>
	 * <br>
	 * @return List 更新処理内容配列
	 */
	public List getChkBoxLst() {
		return chk_box_lst;
	}

	/**
	 * チェックボックスのフラグリストに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setYukoChkLst(List);<br>
	 * <br>
	 * @param List 設定する更新処理内容配列
	 */
	public void setChkBoxLst(ArrayList list) {
		chk_box_lst = list;
	}



	//	moveposに対するセッターとゲッターの集合
	 public boolean setMovePos(String movepos)
	 {
		 this.movepos = movepos;
		 return true;
	 }
	 public String getMovePos()
	 {
		 return this.movepos;
	 }
	 public String getMovePosString()
	 {
		 return "'" + this.movepos + "'";
	 }
	 public String getMovePosHTMLString()
	 {
		 return HTMLStringUtil.convert(this.movepos);
	 }



	//	p_kanri_kbに対するセッターとゲッターの集合
	public boolean setPKanriKb(String kanri_kb)
	{
		this.p_kanri_kb = kanri_kb;
		return true;
	}
	public String getPKanriKb()
	{
		return cutString(this.p_kanri_kb, P_KANRI_KB_MAX_LENGTH);
	}
	public String getPKanriKbString()
	{
		return "'" + cutString(this.p_kanri_kb, P_KANRI_KB_MAX_LENGTH) + "'";
	}
	public String getPKanriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.p_kanri_kb, P_KANRI_KB_MAX_LENGTH));
	}

	// db_hanku_cdに対するセッターとゲッターの集合
	public boolean setDbHankuCd(String db_hanku_cd)
	{
		this.db_hanku_cd = db_hanku_cd;
		return true;
	}
	public String getDbHankuCd()
	{
		return cutString(this.db_hanku_cd, DB_HANKU_CD_MAX_LENGTH);
	}
	public String getDbHankuCdString()
	{
		return "'" + cutString(this.db_hanku_cd, DB_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getDbHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.db_hanku_cd, DB_HANKU_CD_MAX_LENGTH));
	}



	// hanku_cdに対するセッターとゲッターの集合
	public boolean setHankuCd(String hanku_cd)
	{
		this.hanku_cd = hanku_cd;
		return true;
	}
	public String getHankuCd()
	{
		return cutString(this.hanku_cd, HANKU_CD_MAX_LENGTH);
	}
	public String getHankuCdString()
	{
		return "'" + cutString(this.hanku_cd, HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_cd, HANKU_CD_MAX_LENGTH));
	}


	//	hinsyu_cdに対するセッターとゲッターの集合
	 public boolean setHinsyuCd(String hinsyu_cd)
	 {
		 this.hinsyu_cd = hinsyu_cd;
		 return true;
	 }
	 public String getHinsyuCd()
	 {
		 return cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH);
	 }
	 public String getHinsyuCdString()
	 {
		 return "'" + cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH) + "'";
	 }
	 public String getHinsyuCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH));
	 }


	//	tenpo_naに対するセッターとゲッターの集合
	 public boolean setTenpoNa(String tenpo_na)
	 {
		 this.tenpo_na = tenpo_na;
		 return true;
	 }
	 public String getTenpoNa()
	 {
		 return cutString(this.tenpo_na, TENPO_NA_MAX_LENGTH);
	 }
	 public String getTenpoNaString()
	 {
		 return "'" + cutString(this.tenpo_na, TENPO_NA_MAX_LENGTH) + "'";
	 }
	 public String getTenpoNaHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.tenpo_na, TENPO_NA_MAX_LENGTH));
	 }


	//	tenpo_naに対するセッターとゲッターの集合
	 public boolean setTenpoCd(String tenpo_cd)
	 {
		 this.tenpo_cd = tenpo_cd;
		 return true;
	 }
	 public String getTenpoCd()
	 {
		 return cutString(this.tenpo_cd, TENPO_CD_MAX_LENGTH);
	 }
	 public String getTenpoCdString()
	 {
		 return "'" + cutString(this.tenpo_cd, TENPO_CD_MAX_LENGTH) + "'";
	 }
	 public String getTenpoCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.tenpo_cd, TENPO_CD_MAX_LENGTH));
	 }

	/**
	 * 作成年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertTs("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setRetrieveTs(String retrieve_ts)
		{
			this.retrieve_ts = retrieve_ts;
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
		public String getRetrieveTs()
		{
			return this.retrieve_ts;
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
	 * 更新処理内容配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMeisai();　戻り値　更新処理内容配列<br>
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
	 * setYukoChkLst(List);<br>
	 * <br>
	 * @param List 設定する更新処理内容配列
	 */
	public void setUpdateProcessLst(List list) {
		updateprocesslst = list;
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

	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max )
	{
		if ( base == null ) return null;
		String wk = "";
		for ( int pos = 0, count = 0; pos < max && pos < base.length(); pos++ )
		{
			try
			{
				byte bt[] = base.substring(pos, pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if ( count > max )
					break;
				wk += base.substring(pos, pos+1);
			}
			catch(Exception e)
			{
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos, pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos, pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}

}
