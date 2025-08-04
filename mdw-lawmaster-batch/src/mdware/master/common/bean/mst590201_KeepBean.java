/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングマスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングマスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/13)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Jozawa START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Jozawa END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングマスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングマスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/13)初版作成
 */
public class mst590201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
//	 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓
//	private String l_gyosyu_cd = null;	//大業種 (FK)
	private String bumon_cd = null; //部門
	private String back_kb 	= null;	// 遷移区分
	private String yoto_kb = null;	//用途区分 (FK)
//	private long groupno_cd = 0;	//グルーピングNO (FK)
	private String groupno_cd = null;	//グルーピングNO (FK)
//	 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑
	private String tenpo_cd = null;	//店舗コード (FK)
	private long rank_nb = 0;	//順位
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
	private String nameNa = null;//グループ名
	private List tenpo_cdL = new ArrayList();//選択前店舗配列
	private Map tenpo_nameL = new HashMap();//選択前店舗名配列
	private List tenpo2_cdL = new ArrayList();//選択店舗配列
	private Map tenpo2_nameL = new HashMap();//選択店舗名配列
	private String shori_kb = null;//処理状況
	private List data = null;//表示データ保存

//BUGNO-S052 2005.05.14 Y.Jozawa START
	private static final String INIT_PAGE = "mst590101_TenGroupInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst590201_TenGroupEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst590301_TenGroupView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Jozawa END

/**
 * 大業種 (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setLGyosyuCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
//	 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓
//	public boolean setLGyosyuCd(String l_gyosyu_cd)
//	{
//		this.l_gyosyu_cd = l_gyosyu_cd;
//		return true;
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
/**
 * 大業種 (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getLGyosyuCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
//	public String getLGyosyuCd()
//	{
//		return this.l_gyosyu_cd;
//	}
	public String getBumonCd()
	{
		return this.bumon_cd;
	}
	/**	
	 *戻る区分にに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public String  setBackKb(String back_kb)
		{
			return this.back_kb = back_kb;
		}

	/**	
	*戻る区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */	
		public String getBackKb()
		{
		   return this.back_kb;
			
		}
//	 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑

/**
 * 用途区分 (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setYotoKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setYotoKb(String yoto_kb)
	{
		this.yoto_kb = yoto_kb;
		return true;
	}
/**
 * 用途区分 (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getYotoKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getYotoKb()
	{
		return this.yoto_kb;
	}


/**
 * グルーピングNO (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setGroupnoCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setGroupnoCd(String groupno_cd)
	{
		try
		{
			this.groupno_cd =groupno_cd;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * グルーピングNO (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setGroupnoCd("long");<br>
 * <br>
 * @param long 設定する値
 */
//	public boolean setGroupnoCd(long groupno_cd)
//	{
//		this.groupno_cd = groupno_cd;
//		return true;
//	}
//	public long getGroupnoCd()
//	{
//		return this.groupno_cd;
//	}
/**
 * グルーピングNO (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getGroupnoCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getGroupnoCd()
	{
		return this.groupno_cd;
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
 * 順位に対するセッター<br>
 * <br>
 * Ex)<br>
 * setRankNb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setRankNb(String rank_nb)
	{
		try
		{
			this.rank_nb = Long.parseLong(rank_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 順位に対するセッター<br>
 * <br>
 * Ex)<br>
 * setRankNb("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setRankNb(long rank_nb)
	{
		this.rank_nb = rank_nb;
		return true;
	}
	public long getRankNb()
	{
		return this.rank_nb;
	}
/**
 * 順位に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getRankNb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getRankNbString()
	{
		return Long.toString(this.rank_nb);
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
 * setErrorFlg("文字列");<br>
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
 * getErrorFlg();　戻り値　文字列<br>
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
 * setErrorMessage("文字列");<br>
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
 * getErrorMessage();　戻り値　文字列<br>
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
 * setFirstFocus("文字列");<br>
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
 * getFirstFocus();　戻り値　文字列<br>
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
 * setInsertFlg("文字列");<br>
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
 * getInsertFlg();　戻り値　文字列<br>
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
 * setUpdateFlg("文字列");<br>
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
 * getUpdateFlg();　戻り値　文字列<br>
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
 * setDeleteFlg("文字列");<br>
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
 * getDeleteFlg();　戻り値　文字列<br>
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
 * setReferenceFlg("文字列");<br>
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
 * getReferenceFlg();　戻り値　文字列<br>
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
 * setCsvFlg("文字列");<br>
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
 * getCsvFlg();　戻り値　文字列<br>
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
 * setPrintFlg("文字列");<br>
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
 * getPrintFlg();　戻り値　文字列<br>
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
	public boolean setCheckflg(String checkflg)
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
	public String getCheckflg()
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
 * 更新処理内容に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateprocessflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateprocessflg(String updateprocessflg)
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
	public String getUpdateprocessflg()
	{
		return this.updateprocessflg;
	}

	/**
	 * JSPコントロール配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor(JSPコントロール配列);<br>
	 * <br>
	 * @param String 設定するJSPコントロール配列
	 */
	public boolean setCtrlColor(Map CtrlColor)
	{
		this.CtrlColor = CtrlColor;
		return true;
	}
	/**
	 * JSPコントロール配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor();　戻り値　JSPコントロール配列<br>
	 * <br>
	 * @return String JSPコントロール配列
	 */
	public Map getCtrlColor()
	{
		return this.CtrlColor;
	}

	/**
	 * グループ名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setNameNa("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setNameNa(String string) {
		nameNa = string;
	}

	/**
	 * グループ名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getNameNa();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getNameNa() {
		return nameNa;
	}

	/**
	 * 選択前店舗配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoCdL();　戻り値　選択前店舗配列<br>
	 * <br>
	 * @return Map 選択前店舗配列
	 */
	public List getTenpoCdL() {
		return tenpo_cdL;
	}

	/**
	 * 選択前店舗名配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoNameL();　戻り値　選択前店舗名配列<br>
	 * <br>
	 * @return Map 選択前店舗配列
	 */
	public Map getTenpoNameL() {
		return tenpo_nameL;
	}

	/**
	 * 選択前店舗配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoCdL(Map);<br>
	 * <br>
	 * @param Map 選択前店舗配列
	 */
	public void setTenpoCdL(List map) {
		tenpo_cdL = map;
	}

	/**
	 * 選択店舗名配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoNameL(Map);<br>
	 * <br>
	 * @param Map 選択店舗名配列
	 */
	public void setTenpoNameL(Map map) {
		tenpo_nameL = map;
	}

	/**
	 * 選択店舗配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpo2CdL();　戻り値　選択店舗配列<br>
	 * <br>
	 * @return Map 選択前店舗配列
	 */
	public List getTenpo2CdL() {
		return tenpo2_cdL;
	}

	/**
	 * 選択店舗名配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpo2NameL();　戻り値　選択店舗名配列<br>
	 * <br>
	 * @return Map 選択店舗名配列
	 */
	public Map getTenpo2NameL() {
		return tenpo2_nameL;
	}

	/**
	 * 選択店舗配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpo2CdL(Map);<br>
	 * <br>
	 * @param Map 選択店舗配列
	 */
	public void setTenpo2CdL(List map) {
		tenpo2_cdL = map;
	}

	/**
	 * 選択店舗名配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpo2NameL(Map);<br>
	 * <br>
	 * @param Map 選択店舗名配列
	 */
	public void setTenpo2NameL(Map map) {
		tenpo2_nameL = map;
	}

	/**
	 * 処理状況に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getShori_kb();　戻り値　処理状況<br>
	 * <br>
	 * @return 処理状況
	 */
	public String getShori_kb() {
		return shori_kb;
	}

	/**
	 * 処理状況に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setShori_kb(String);<br>
	 * <br>
	 * @param string 処理状況
	 */
	public void setShori_kb(String string) {
		shori_kb = string;
	}

	/**
	 * 表示用保存データに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getData();　戻り値　List<br>
	 * <br>
	 * @return List
	 */
	public List getData() {
		return data;
	}

	/**
	 * 表示用保存データに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setData(List);<br>
	 * <br>
	 * @param List
	 */
	public void setData(List holder) {
		data = holder;
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
//BUGNO-S052 2005.05.14 SIRIUS START

}
