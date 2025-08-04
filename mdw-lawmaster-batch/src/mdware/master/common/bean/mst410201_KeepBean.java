/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）名称・CTFマスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する名称・CTFマスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/29)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
//import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
//import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
//import jp.co.vinculumjapan.stc.util.calendar.DateChanger;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）名称・CTFマスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する名称・CTFマスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/29)初版作成
 */
public class mst410201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	//種別１
	private String syubetu1_cd	 		= null;	//種別NO
	private String code1_cd 			= null;	//コード
	private String syubetu1_nm			= null;	//種別名称
	private String delete1_fg			= null;	//削除フラグ
	//種別２
	private String syubetu2_cd	 		= null;	//種別NO
	private String code2_cd 			= null;	//コード
	private String syubetu2_nm			= null;	//種別名称
	private String delete2_fg			= null;	//削除フラグ
	//種別３
	private String syubetu3_cd	 		= null;	//種別NO
	private String code3_cd 			= null;	//コード
	private String syubetu3_nm			= null;	//種別名称
	private String delete3_fg			= null;	//削除フラグ	
	
	private String processingDivision 	= null;	//処理状況
	private String errorFlg 			= null;	//エラーフラグ
	private String errorMessage 		= null;	//エラーメッセージ
	private String[] menuBar 			= null;	//メニューバーアイテム
	private String mode 				= null;	//処理モード
	private String firstFocus 			= null;	//フォーカスを最初に取得するオブジェクト名
	private String insertFlg 			= null;	//新規処理利用可能区分
	private String updateFlg 			= null;	//更新処理利用可能区分
	private String deleteFlg 			= null;	//削除処理利用可能区分
	private String referenceFlg 		= null;	//照会処理利用可能区分
	private String csvFlg 				= null;	//CSV処理利用可能区分
	private String printFlg 			= null;	//印刷処理利用可能区分
	private String checkFlg 			= null;	//チェック処理判断
	private Map ctrlColor 				= new HashMap();	//コントロール背景色

	private String insert_ts 				= null;	//作成年月日
//	private String insert_ts_short			= null;	//表示用作成年月日
	private String insert_user_id 			= null;	//作成者社員ID
	private String update_ts 				= null;	//更新年月日
//	private String update_ts_short			= null;	//表示用更新年月日
	private String update_user_id 			= null;	//更新者社員ID
	private String delete_fg 				= null;	//削除フラグ
	
//	↓↓2006.08.15 wangluping カスタマイズ修正↓↓	
//	↓↓追加（2005.07.01）↓↓
//	private String code2_len				= null;	//属性コード頭2桁（文字数制御用）
//	↑↑追加（2005.07.01）↑↑
//	↑↑2006.08.15 wangluping カスタマイズ修正↑↑
	
//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst410101_NameCtfInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst410201_NameCtfEdit";	// 照会画面JSPを取得
	private static final String VIEW_PAGE = "mst410301_NameCtfView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END

	/**
	 * 種別NO1に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu1Cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyubetu1Cd(String syubetu1_cd)
	{
		this.syubetu1_cd = syubetu1_cd;
		return true;
	}
	/**
	 * 種別NO1に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu1Cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyubetu1Cd()
	{
		return this.syubetu1_cd;
	}


	/**
	 * コード1に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCode1Cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setCode1Cd(String code1_cd)
		{
			this.code1_cd = code1_cd;
			return true;
		}
	/**
	 * コード1に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCode1Cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getCode1Cd()
		{
			return this.code1_cd;
		}


	/**
	 * 種別名称1に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu1Nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyubetu1Nm(String syubetu1_nm)
	{
		this.syubetu1_nm = syubetu1_nm;
		return true;
	}
	/**
	 * 種別名称1に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu1Nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyubetu1Nm()
	{
		return this.syubetu1_nm;
	}


	/**
	 * 削除フラグ1に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu1Nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setDelete1Fg(String delete1_fg)
	{
		this.delete1_fg = delete1_fg;
		return true;
	}
	/**
	 * 削除フラグ1に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu1Nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDelete1Fg()
	{
		return this.delete1_fg;
	}


	/**
	 * 種別NO2に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu1Cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyubetu2Cd(String syubetu2_cd)
	{
		this.syubetu2_cd = syubetu2_cd;
		return true;
	}
	/**
	 * 種別NO2に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu1Cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyubetu2Cd()
	{
		return this.syubetu2_cd;
	}


	/**
	 * コード2に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCode1Cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setCode2Cd(String code2_cd)
		{
			this.code2_cd = code2_cd;
			return true;
		}
	/**
	 * コード2に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCode1Cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getCode2Cd()
		{
			return this.code2_cd;
		}


	/**
	 * 種別名称2に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu1Nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyubetu2Nm(String syubetu2_nm)
	{
		this.syubetu2_nm = syubetu2_nm;
		return true;
	}
	/**
	 * 種別名称2に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu2Nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyubetu2Nm()
	{
		return this.syubetu2_nm;
	}


	/**
	 * 削除フラグ2に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu1Nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setDelete2Fg(String delete2_fg)
	{
		this.delete2_fg = delete2_fg;
		return true;
	}
	/**
	 * 削除フラグ2に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu1Nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDelete2Fg()
	{
		return this.delete2_fg;
	}


	/**
	 * 種別NO3に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu3Cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyubetu3Cd(String syubetu3_cd)
	{
		this.syubetu3_cd = syubetu3_cd;
		return true;
	}
	/**
	 * 種別NO3に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu3Cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyubetu3Cd()
	{
		return this.syubetu3_cd;
	}


	/**
	 * コード3に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCode3Cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setCode3Cd(String code3_cd)
		{
			this.code3_cd = code3_cd;
			return true;
		}
	/**
	 * コード3に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCode3Cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getCode3Cd()
		{
			return this.code3_cd;
		}


	/**
	 * 種別名称3に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu3Nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyubetu3Nm(String syubetu3_nm)
	{
		this.syubetu3_nm = syubetu3_nm;
		return true;
	}
	/**
	 * 種別名称3に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu3Nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyubetu3Nm()
	{
		return this.syubetu3_nm;
	}


	/**
	 * 削除フラグ3に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyubetu1Nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setDelete3Fg(String delete3_fg)
	{
		this.delete3_fg = delete3_fg;
		return true;
	}
	/**
	 * 削除フラグ3に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyubetu1Nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDelete3Fg()
	{
		return this.delete3_fg;
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
//		↓↓2006.08.15 wangluping カスタマイズ修正↓↓	
//		↓↓追加（2005.07.01）↓↓
	/**
	 * 更新者社員IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCode2Len("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//		public boolean setCode2Len(String code2_len)
//		{
//			this.code2_len = code2_len;
//			return true;
//		}
	/**
	 * 更新者社員IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCode2Len();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
//		public String getCode2Len()
//		{
//			return this.code2_len;
//		}
//		↑↑追加（2005.07.01）↑↑
//		↑↑2006.08.15 wangluping カスタマイズ修正↑↑
		
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
//BUGNO-S052 2005.05.14 SIRIUS START

}
