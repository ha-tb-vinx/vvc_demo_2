/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタ登録(一覧)表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタ登録(一覧)表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/23)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタ登録(一覧)の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタ登録(一覧)の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/23)初版作成
 */
public class mst430201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String kanrikb_cd 				= null;	//管理区分
//	private String kanrikb_nm 				= null;	//管理区分名
//    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//	private String kanri_cd 				= null;	//管理コード
//	private String kanri_nm 				= null;	//商品コード名
	private String bumon_cd 				= null;	//部門コード
//	   ↑↑2006.06.15 guohy カスタマイズ修正↑↑	
	private String siiresaki_cd			= null;	//仕入先コード
	private String siiresaki_nm			= null;	//仕入先コード名
	private String insert_ts 				= null;	//作成年月日
	private String insert_ts_short			= null;	//表示用作成年月日
	private String insert_user_id 			= null;	//作成者社員ID
	private String update_ts 				= null;	//更新年月日
	private String update_ts_short			= null;	//表示用更新年月日
	private String update_user_id 			= null;	//更新者社員ID
	private String delete_fg 				= null;	//削除フラグ
	
	private String processingdivision 		= null;	//処理状況
	private String errorflg 				= null;	//エラーフラグ
	private String errormessage 			= null;	//エラーメッセージ
	private String[] menubar 				= null;	//メニューバーアイテム
	private Map mode 						= new HashMap();	//処理モード
	private String firstfocus 				= null;	//フォーカスを最初に取得するオブジェクト名
	private String insertflg 				= null;	//新規処理利用可能区分
	private String updateflg 				= null;	//更新処理利用可能区分
	private String deleteflg 				= null;	//削除処理利用可能区分
	private String referenceflg 			= null;	//照会処理利用可能区分
	private String csvflg 					= null;	//CSV処理利用可能区分
	private String printflg 				= null;	//印刷処理利用可能区分
	private String before_disp_id 			= null;	//前画面ID
	private String disp_url 				= null;	//現画面URL
	private String checkflg 				= null;	//チェック処理判断
	private String existflg 				= null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searcherrorflg 			= null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateprocessflg 		= null;	//更新処理内容
	private Map ctrlcolor					= new HashMap();
	private boolean seisen = false;//生鮮担当者判断フラグ	

//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst430101_SiiresakiItiranInit";	// 初期画面JSPを取得
	private static final String VIEW_PAGE = "mst430201_SiiresakiItiranView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END

	/**
	 * 管理区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setKanriKbCd(String kanrikb_cd)
	{
		this.kanrikb_cd = kanrikb_cd;
		return true;
	}
	/**
	 * 管理区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getKanriKbCd()
	{
		return this.kanrikb_cd;
	}


//    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
	/**
	 * 管理区分名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//		public boolean setKanriKbNm(String kanrikb_nm)
//		{
//			this.kanrikb_nm = kanrikb_nm;
//			return true;
//		}
	/**
	 * 管理区分名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
//		public String getKanriKbNm()
//		{
//			return this.kanrikb_nm ;
//		}
//	   ↑↑2006.06.15 guohy カスタマイズ修正↑↑	


//    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
	/**
	 * 管理コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//	public boolean setKanriCd(String kanri_cd)
//	{
//		this.kanri_cd = kanri_cd;
//		return true;
//	}
	/**
	 * 管理コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
//	public String getKanriCd()
//	{
//		return this.kanri_cd;
//	}


	/**
	 * 管理コード名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//		public boolean setKanriNm(String kanri_nm)
//		{
//			this.kanri_nm = kanri_nm;
//			return true;
//		}
	/**
	 * 管理コード名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
//		public String getKanriNm()
//		{
//			return this.kanri_nm ;
//		}

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	/**
	 * 部門コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBumonCd()
	{
		return this.bumon_cd;
	}

		
//		/**
//		 * 地区コードに対するセッター<br>
//		 * <br>
//		 * Ex)<br>
//		 * setAreaCd("文字列");<br>
//		 * <br>
//		 * @param String 設定する文字列
//		 */
//		public boolean setAreaCd(String area_cd)
//		{
//			this.area_cd = area_cd;
//			return true;
//		}
//		/**
//		 * 地区コードに対するセッター<br>
//		 * <br>
//		 * Ex)<br>
//		 * getBumonCd();　戻り値　文字列<br>
//		 * <br>
//		 * @return String 文字列
//		 */
//		public String getAreaCd()
//		{
//			return this.area_cd;
//		}
//
//
//		/**
//		 * 地区コード名に対するゲッター<br>
//		 * <br>
//		 * Ex)<br>
//		 * setAreaNm("文字列");<br>
//		 * <br>
//		 * @param String 設定する文字列
//		 */
//			public boolean setAreaNm(String area_nm)
//			{
//				this.area_nm = area_nm;
//				return true;
//			}
//		/**
//		 * 地区コード名に対するゲッター<br>
//		 * <br>
//		 * Ex)<br>
//		 * getAreaNm();　戻り値　文字列<br>
//		 * <br>
//		 * @return String 文字列
//		 */
//			public String getAreaNm()
//			{
//				return this.area_nm ;
//			}
//	   ↑↑2006.06.15 guohy カスタマイズ修正↑↑	


	/**
	 * 仕入先に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriNmuNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setSiiresakiCd(String siiresaki_cd)
		{
			this.siiresaki_cd = siiresaki_cd;
			return true;
		}
	/**
	 * 仕入先コード名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriNm();　戻り値　	<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getSiiresakiCd()
		{
			return this.siiresaki_cd;
		}


	/**
	 * 仕入先名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setSiiresakiNm(String siiresaki_nm)
		{
			this.siiresaki_nm = siiresaki_nm;
			return true;
		}
	/**
	 * 仕入先名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiNm();　戻り値　	<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getSiiresakiNm()
		{
			return this.siiresaki_nm;
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
	public boolean setExistFlg(String existflg)
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
	public String getExistFlg()
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
	public boolean setSearchErrorFlg(String searcherrorflg)
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
	public String getSearchErrorFlg()
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


	/**
	 * コントロールカラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor("Map");<br>
	 * <br>
	 * @param Map 設定するMap配列
	 */
		public boolean setCtrlColor(Map ctrlcolor)
		{
			try
			{
				this.ctrlcolor = ctrlcolor;
			}
			catch(Exception e)
			{
				return false;
			}
			return true;
		}
	/**
	 * コントロールカラーに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor();　戻り値　Map配列<br>
	 * <br>
	 * @return String[] Map配列
	 */
		public Map getCtrlColor()
		{
			return this.ctrlcolor;
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
