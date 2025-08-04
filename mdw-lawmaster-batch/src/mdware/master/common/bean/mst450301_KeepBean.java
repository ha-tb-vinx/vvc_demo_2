/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別仕入先マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別仕入先マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別仕入先マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別仕入先マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
public class mst450301_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
//    ↓↓2006.06.30 maojm カスタマイズ修正↓↓
//	private String kanri_kb 				= null;			//管理区分 (FK)
//	private String kanri_cd 				= null;			//管理コード (FK)
//	private String copy_kanri_cd			= null;			//コピー管理コード (FK)
	private String bumon_cd          = null;        //部門コード
//	↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//	private String copy_bumon_cd    = null;      // コピー部門コード
	private String copy_bumon_cd    = "";      // コピー部門コード
//	↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
//    ↑↑2006.06.30 maojm カスタマイズ修正↑↑
	private String ten_siiresaki_kanri_cd 	= null;			//店別仕入先管理コード
	private String tenpo_cd 				= null;			//店舗コード (FK)
	private String siiresaki_cd 			= null;			//仕入先コード (FK)
	private String insert_ts 				= null;			//作成年月日
	private String insert_user_id 			= null;			//作成者社員ID
	private String update_ts 				= null;			//更新年月日
	private String update_user_id 			= null;			//更新者社員ID
	private String delete_fg 				= null;			//削除フラグ
//    ↓↓2006.06.30 maojm カスタマイズ修正↓↓
//	private String kanri_nm				= null;			//管理コード名称
	private String bumon_nm				= null;			//部門コード名称
	private String ten_siiresaki_kanri_nm	= null;			//店仕入先管理コード名称
//	private String copykanri_nm			= null;			//コピー管理コード名称
//	↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//	private String copybumon_nm			= null;			//コピー部門コード名称
	private String copybumon_nm			= "";			//コピー部門コード名称
//	↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
//  ↓↓ add by kema 06.11.10
//	↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//	private String copy_ten_siiresaki_kanri_cd = null;		//コピーメーカーコード
//	private String copy_ten_siiresaki_kanri_nm	= null;		//コピーメーカー名称
	private String copy_ten_siiresaki_kanri_cd = "";		//コピーメーカーコード
	private String copy_ten_siiresaki_kanri_nm	= "";		//コピーメーカー名称
//	↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
	private String groupno_cd               = null;			//店配列コード
	private String groupno_nm 		 		 = null;			//店配列名称 
//	↑↑ add by kema 06.11.10
	private String back_kb                     = null;         //戻る区分
//    ↑↑2006.06.30 maojm カスタマイズ修正↑↑
	private String processingdivision 		= null;			//処理状況
	private String errorflg 				= null;			//エラーフラグ
	private String ChangeFlg			= "0";				// 明細部変更かどうか判断
	private String errormessage 			= null;			//エラーメッセージ
	private String[] menubar 				= null;			//メニューバーアイテム
	private Map ctrlcolor					= new HashMap();	// コントロール前景色
	private Map mode 						= new HashMap();	//処理モード
	private String firstfocus 				= null;			//フォーカスを最初に取得するオブジェクト名
	private String insertflg 				= null;			//新規処理利用可能区分
	private String updateflg 				= null;			//更新処理利用可能区分
	private String deleteflg 				= null;			//削除処理利用可能区分
	private String referenceflg 			= null;			//照会処理利用可能区分
	private String csvflg 					= null;			//CSV処理利用可能区分
	private String printflg 				= null;			//印刷処理利用可能区分
	private String disp_id 				= null;			//画面id
	private String checkflg 				= null;			//チェック処理判断
	private String existflg 				= null;			//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searcherrorflg 			= null;			//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateprocessflg 		= null;			//更新処理内容
	//BUGNO-S005 20050421 S.Murata START
	private String chgFlg = "false";	//画面変更フラグ
	//BUGNO-S005 20050421 S.Murata END
	
	//生鮮かどうかの判断用
	private String gyosyuid = null;//業種ID

//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst450101_TenbetuSiiresakiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst450201_TenbetuSiiresakiEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst450301_TenbetuSiiresakiView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE		= "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END

//    ↓↓2006.06.30 maojm カスタマイズ修正↓↓
/**
 * 管理区分 (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanriKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
//	public boolean setKanriKb(String kanri_kb)
//	{
//		this.kanri_kb = kanri_kb;
//		return true;
//	}
/**
 * 管理区分 (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanriKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
//	public String getKanriKb()
//	{
//		return this.kanri_kb;
//	}


/**
 * 管理コード (FK)に対するセッター<br>
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
 * 管理コード (FK)に対するゲッター<br>
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
	 * コピー管理コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//		public boolean setCopyKanriCd(String copy_kanri_cd)
//		{
//			this.copy_kanri_cd = copy_kanri_cd;
//			return true;
//		}
	/**
	 * コピー管理コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
//		public String getCopyKanriCd()
//		{
//			return this.copy_kanri_cd;
//		}
	/**
	 * 部門コード (FK)に対するセッター<br>
	 * @param String 設定する文字列
	 */
public boolean setBumonCd (String bumon_cd){
	this.bumon_cd = bumon_cd;
	return true;
}
/**
 * 部門コードに対するゲッター<br>
 * @return String 文字列
 */
public String getBumonCd()
{
	return this.bumon_cd;
}
/**
 * コピー部門コードに対するセッター<br>
 * @param String 設定する文字列
 */
	public boolean setCopyBumonCd(String copy_bumon_cd)
	{
		this.copy_bumon_cd = copy_bumon_cd;
		return true;
	}
/**
 * コピー部門コードに対するゲッター<br>
 * @return String 文字列
 */
	public String getCopyBumonCd()
	{
		return this.copy_bumon_cd;
	}
	
/**	
 *戻る区分にに対するセッター<br>
 * @param String 設定する文字列
 */
	public String  setBackKb(String back_kb)
	{
		return this.back_kb = back_kb;
	}

/**	
*戻る区分に対するゲッター<br>
 * @return String 文字列
 */	
	public String getBackKb()
	{
	   return this.back_kb;
		
	}
	
//    ↑↑2006.06.30 maojm カスタマイズ修正↑↑
/**
 * 店別仕入先管理コードに対するセッター<br>
 * setTenSiiresakiKanriCd("文字列");<br>
 * @param String 設定する文字列
 */
	public boolean setTenSiiresakiKanriCd(String ten_siiresaki_kanri_cd)
	{
		this.ten_siiresaki_kanri_cd = ten_siiresaki_kanri_cd;
		return true;
	}
/**
 * 店別仕入先管理コードに対するゲッター<br>
 * getTenSiiresakiKanriCd();　戻り値　文字列<br>
 * @return String 文字列
 */
	public String getTenSiiresakiKanriCd()
	{
		return this.ten_siiresaki_kanri_cd;
	}


//	↓↓ add by kema 06.11.08
//	店配列に対するゲッターセッター
	public String getGroupNoCd() {
		return groupno_cd;
	}
	public void setGroupNoCd(String groupno_cd) {
		this.groupno_cd = groupno_cd;
	}
//	店配列名称に対するゲッターセッター
	public String getGroupNoNm() {
		return groupno_nm;
	}
	public void setGroupNoNm(String groupno_nm) {
		this.groupno_nm = groupno_nm;
	}
//	コピーメーカーコードに対するゲッターセッター
	public String getCopyTenSiiresakiKanriCd()
	{
		return this.copy_ten_siiresaki_kanri_cd;
	}
	public boolean setCopyTenSiiresakiKanriCd(String copy_ten_siiresaki_kanri_cd)
	{
		this.copy_ten_siiresaki_kanri_cd = copy_ten_siiresaki_kanri_cd;
		return true;
	}
//	コピーメーカー名称に対するゲッターセッター
	public void setCopyTenSiiresakiKanriNm(String copy_ten_siiresaki_kanri_nm) {
		this.copy_ten_siiresaki_kanri_nm = copy_ten_siiresaki_kanri_nm;
	}
	public String getCopyTenSiiresakiKanriNm() {
		return copy_ten_siiresaki_kanri_nm;
	}
//	↑↑ add by kema 06.11.08

/**
 * 仕入先コード (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSiiresakiCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
/**
 * 仕入先コード (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSiiresakiCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSiiresakiCd()
	{
		return this.siiresaki_cd;
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
 * seterrorflg("文字列");<br>
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
 * geterrorflg();　戻り値　文字列<br>
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
 * コントロールカラーに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMode("Map");<br>
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
 * getMode();　戻り値　Map配列<br>
 * <br>
 * @return String[] Map配列
 */
	public Map getCtrlColor()
	{
		return this.ctrlcolor;
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

	// ChangeFlgに対するセッターとゲッターの集合
	public boolean setChangeFlg(String ChangeFlg)
	{
		this.ChangeFlg = ChangeFlg;
		return true;
	}
	public String getChangeFlg()
	{
		return this.ChangeFlg;
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
	
//    ↓↓2006.06.30 maojm カスタマイズ修正↓↓
/**
 * 管理コード名称に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateprocessflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
//	public void setKanriNm(String string) {
//		kanri_nm = string;
//	}
/**
 * 管理コード名称に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateprocessflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
//	public String getKanriNm() {
//		return kanri_nm;
//	}
	/**
	 * 部門コード名称に対するセッター<br>
	 * @param String 設定する文字列
	 */
		public void setBumonNm(String string) {
			bumon_nm = string;
		}
	/**
	 * 部門コード名称に対するゲッター<br>
	 * @return String 文字列
	 */
		public String getBumonNm() {
			return bumon_nm;
		}	

//	    ↑↑2006.06.30 maojm カスタマイズ修正↑↑	
/**
 * 店仕入先管理コード名称に対するセッター<br>
 * @param String 設定する文字列
 */
	public void setTenSiiresakiKanriNm(String ten_siiresaki_kanri_nm) {
		this.ten_siiresaki_kanri_nm = ten_siiresaki_kanri_nm;
	}
/**
 * 店仕入先管理コード名称に対するゲッター<br>
 * @return String 文字列
 */
	public String getTenSiiresakiKanriNm() {
		return ten_siiresaki_kanri_nm;
	}

//    ↓↓2006.06.30 maojm カスタマイズ修正↓↓
/**
 * コピー管理コード名称に対するセッター<br>
 * @param String 設定する文字列
 */
//		public void setCopyKanriNm(String string) {
//			copykanri_nm = string;
//		}
/**
 * コピー管理コード名称に対するゲッター<br>
 * @return String 文字列
 */
//		public String getCopyKanriNm() {
//			return copykanri_nm;
//		}
 
/**
 * コピー部門コード名称に対するセッター<br>
* @param String 設定する文字列
*/
		public void setCopyBumonNm(String string) {
			copybumon_nm = string;
		}
/**
* コピー部門コード名称に対するゲッター<br>
* @return String 文字列
*/
   public String getCopyBumonNm() {
		return copybumon_nm;
		}		
//   ↑↑2006.06.30 maojm カスタマイズ修正↑↑	

	//BUGNO-S005 20050421 S.Murata START
	/**
	 * 画面変更フラグに対するゲッター<br>
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

  /**
   * 業種取得用のゲッターとセッター
   */
  public void setGyosyuId(String str) {
	  gyosyuid = str;
		
  }
	
  public String getGyosyuId() {
	  return this.gyosyuid;
  }

}
