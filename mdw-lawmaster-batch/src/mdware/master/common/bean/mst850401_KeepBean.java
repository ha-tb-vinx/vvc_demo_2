/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）呼出NO別店別反映日マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する呼出NO別店別反映日マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/14)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 S.Takahashi START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 S.Takahashi END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）呼出NO別店別反映日マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する呼出NO別店別反映日マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/14)初版作成
 */
public class mst850401_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String s_gyosyu_cd = null;		//小業種コード (FK)
	private String theme_cd = null;			//テーマコード (FK)
	private String keiryo_hanku_cd = null;	//計量販区コード (FK)
	private String syohin_yobidasi = null;	//呼出NO (FK)
	private String hanei_dt = null;			//反映日
	private String tenpo_cd = null;			//店舗コード
	private String ten_hanei_dt = null;		//店別反映日
	private String insert_ts = null;			//作成年月日
	private String insert_user_id = null;		//作成者社員ID
	private String update_ts = null;			//更新年月日
	private String update_user_id = null;		//更新者社員ID
	private String delete_fg = null;			//削除フラグ
	
	private String ProcessingDivision = null;	//処理状況
	private String ErrorFlg = null;			//エラーフラグ
	private String ErrorMessage = null;		//エラーメッセージ
	private String[] MenuBar = null;			//メニューバーアイテム
	private Map Mode = new HashMap();			//処理モード
	private String FirstFocus = null;			//フォーカスを最初に取得するオブジェクト名
	private String InsertFlg = null;			//新規処理利用可能区分
	private String UpdateFlg = null;			//更新処理利用可能区分
	private String DeleteFlg = null;			//削除処理利用可能区分
	private String ReferenceFlg = null;		//照会処理利用可能区分
	private String CsvFlg = null;				//CSV処理利用可能区分
	private String PrintFlg = null;			//印刷処理利用可能区分
	private String BeforeDispKb = null;		//前画面ID
	private String DispUrl = null;			//現画面URL
	private String CheckFlg = null;			//チェック処理判断
	private String ExistFlg = null;			//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String SearchErrorFlg = null;		//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String UpdateProcessFlg = null;	//更新処理内容
	private Map CtrlColor				= new HashMap();	// コントロール前景色
//BUGNO-S052 2005.05.14 S.Takahashi START
	private static final String EDIT_PAGE   = "mst850101_TenHaneibiEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE   = "mst850201_TenHaneibiView";	// 削除・照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 S.Takahashi END


/**
 * 小業種コード (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSGyosyuCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
		return true;
	}
/**
 * 小業種コード (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSGyosyuCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSGyosyuCd()
	{
		return this.s_gyosyu_cd;
	}


/**
 * テーマコード (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setThemeCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setThemeCd(String theme_cd)
	{
		this.theme_cd = theme_cd;
		return true;
	}
/**
 * テーマコード (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getThemeCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getThemeCd()
	{
		return this.theme_cd;
	}


/**
 * 計量販区コード (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKeiryoHankuCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKeiryoHankuCd(String keiryo_hanku_cd)
	{
		this.keiryo_hanku_cd = keiryo_hanku_cd;
		return true;
	}
/**
 * 計量販区コード (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKeiryoHankuCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKeiryoHankuCd()
	{
		return this.keiryo_hanku_cd;
	}


/**
 * 呼出NO (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSyohinYobidasi("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSyohinYobidasi(String syohin_yobidasi)
	{
		this.syohin_yobidasi = syohin_yobidasi;
		return true;
	}
/**
 * 呼出NO (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSyohinYobidasi();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSyohinYobidasi()
	{
		return this.syohin_yobidasi;
	}


/**
 * 反映日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setHaneiDt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHaneiDt(String hanei_dt)
	{
		this.hanei_dt = hanei_dt;
		return true;
	}
/**
 * 反映日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHaneiDt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getHaneiDt()
	{
		return this.hanei_dt;
	}


/**
 * 店舗コードに対するセッター<br>
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
 * 店舗コードに対するゲッター<br>
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
 * 店別反映日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTenHaneiDt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTenHaneiDt(String ten_hanei_dt)
	{
		this.ten_hanei_dt = ten_hanei_dt;
		return true;
	}
/**
 * 店別反映日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTenHaneiDt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTenHaneiDt()
	{
		return this.ten_hanei_dt;
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
	public boolean setProcessingDivision(String ProcessingDivision)
	{
		this.ProcessingDivision = ProcessingDivision;
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
		return this.ProcessingDivision;
	}


/**
 * エラーフラグに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrorFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorFlg(String ErrorFlg)
	{
		this.ErrorFlg = ErrorFlg;
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
		return this.ErrorFlg;
	}


/**
 * エラーメッセージに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrorMessage("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorMessage(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
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
		return this.ErrorMessage;
	}


/**
 * メニューバーアイテムに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMenuBar("String[]");<br>
 * <br>
 * @param String[] 設定する文字配列
 */
	public boolean setMenuBar(String[] MenuBar)
	{
		try
		{
			this.MenuBar = MenuBar;
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
		return this.MenuBar;
	}


/**
 * 処理モードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMode("Map");<br>
 * <br>
 * @param Map 設定するMap配列
 */
	public boolean setMode(Map Mode)
	{
		try
		{
			this.Mode = Mode;
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
		return this.Mode;
	}


/**
 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFirstFocus("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setFirstFocus(String FirstFocus)
	{
		this.FirstFocus = FirstFocus;
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
		return this.FirstFocus;
	}


/**
 * 新規処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertFlg(String InsertFlg)
	{
		this.InsertFlg = InsertFlg;
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
		return this.InsertFlg;
	}


/**
 * 更新処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateFlg(String UpdateFlg)
	{
		this.UpdateFlg = UpdateFlg;
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
		return this.UpdateFlg;
	}


/**
 * 削除処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDeleteFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDeleteFlg(String DeleteFlg)
	{
		this.DeleteFlg = DeleteFlg;
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
		return this.DeleteFlg;
	}


/**
 * 照会処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setReferenceFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setReferenceFlg(String ReferenceFlg)
	{
		this.ReferenceFlg = ReferenceFlg;
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
		return this.ReferenceFlg;
	}


/**
 * CSV処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCsvFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCsvFlg(String CsvFlg)
	{
		this.CsvFlg = CsvFlg;
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
		return this.CsvFlg;
	}


/**
 * 印刷処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setPrintFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setPrintFlg(String PrintFlg)
	{
		this.PrintFlg = PrintFlg;
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
		return this.PrintFlg;
	}


/**
 * 前画面IDに対するセッター<br>
 * <br>
 * Ex)<br>
 * setBeforeDispId("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setBeforeDispKb(String BeforeDispKb)
	{
		this.BeforeDispKb = BeforeDispKb;
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
	public String getBeforeDispKb()
	{
		return this.BeforeDispKb;
	}


/**
 * 現画面URLに対するセッター<br>
 * <br>
 * Ex)<br>
 * setDispUrl("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDispUrl(String DispUrl)
	{
		this.DispUrl = DispUrl;
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
		return this.DispUrl;
	}


/**
 * チェック処理判断に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCheckFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCheckFlg(String CheckFlg)
	{
		this.CheckFlg = CheckFlg;
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
		return this.CheckFlg;
	}


/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setExistFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setExistFlg(String ExistFlg)
	{
		this.ExistFlg = ExistFlg;
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
		return this.ExistFlg;
	}


/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSearchErrorFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSearchErrorFlg(String SearchErrorFlg)
	{
		this.SearchErrorFlg = SearchErrorFlg;
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
		return this.SearchErrorFlg;
	}


/**
 * 更新処理内容に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateProcessFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateProcessFlg(String UpdateProcessFlg)
	{
		this.UpdateProcessFlg = UpdateProcessFlg;
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
		return this.UpdateProcessFlg;
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


//	BUGNO-S052 2005.05.14 SIRIUS START

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
		  if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.ErrorFlg.equals("")){
			  //通常系
			  if(!this.ErrorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.ErrorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.ErrorMessage);
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
		  if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.ErrorFlg.equals("")){
			  //通常系
			  if(!this.ErrorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.ErrorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.ErrorMessage);
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

