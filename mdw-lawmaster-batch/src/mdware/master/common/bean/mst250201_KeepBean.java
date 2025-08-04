package mdware.master.common.bean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品空番検索の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品空番検索の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Y.Koyama
 * @version 1.0(2005/03/22)初版作成
 */
public class mst250201_KeepBean
{

	private static final StcLog stcLog = StcLog.getInstance();

//	↓↓2006.07.18 xiongjun カスタマイズ修正↓↓
// 2006/01/27 kim START POSコード追加
//  public static final String SELECT_POS     = "0";//選択-POSコード
//	public static final String SELECT_HINSYU     = "1";//選択-品種コード
// 	public static final String SELECT_HINBAN = "2";//選択-取引先品番コード
//  public static final String SELECT_SIIRE  = "4";//選択-仕入先コード
	public static final String SELECT_TAGU = "0"; 	//選択-タグ衣料
	public static final String SELECT_JITUYOU = "1"; //選択-実用衣料 
	public static final String SELECT_BLANKKB = "0"; //選択-空番 
	public static final String SELECT_YOYAKUSUMI = "1"; //選択-予約済
	public static final String SELECT_TOUROKUSUMI = "2"; //選択-登録済 
	public static final String SELECT_YOYAKU = "0"; 	//選択-予約
	public static final String SELECT_MIYOYAKU  = "1"; //選択-未予約
	public static final int GAMEN_KB_MAX_LENGTH  = 1; //画面区分の長さ
	
//	2006/01/27 kim END
//	↑↑2006.07.18 xiongjun カスタマイズ修正↑↑
	
	private String select = null;	//選択
//	↓↓2006.07.18 xiongjun カスタマイズ修正↓↓
	private String gamen_kb = null;			//画面区分
	private String startSyohinCd = null;		//開始商品コード
	private String endSyohinCd = null;			//終了商品コード
	private String byNo = null;				//By№
	private String siiresakiSyohinCd = null; 	//仕入先商品コード	
//	private String blankKb       = null;	//空番含む
	private String blankKb = null;				//空番
	private String yoyakusumi = null;			//予約済
	private String tourokusumi = null;			//登録済
//	↑↑2006.07.18 xiongjun カスタマイズ修正↑↑
	
	//===BEGIN=== Add by kou 2006/11/14
	private String sscdFlg = null;		//あいまい検索フラグ
	//===END=== Add by kou 2006/11/14
	
	private String selectOnSerch        = null;		//検索時-選択
//	↓↓2006.07.18 xiongjun カスタマイズ修正↓↓
	private String startSyohinCdOnSerch = null;		//検索時-開始商品コード
	private String endSyohinCdOnSerch = null;			//検索時-終了商品コード
	private String byNoOnSerch = null;					//検索時-By№
	private String siiresakiSyohinCdOnSerch = null;	//検索時-仕入先商品コード
//	private String blankKbOnSerch       = null;	//検索時-空番含む
	private String blankKbOnSerch = null;				//検索時-空番
	private String tourokusumiOnSerch = null;			//検索時-予約済
	private String yoyakusumiOnSerch = null;			//検索時-登録済
//	↑↑2006.07.18 xiongjun カスタマイズ修正↑↑

	private List meisai = new ArrayList(); //一覧の明細
	private String CurrentPageNo	= "";   // 現在表示ページ
	private String LastPageNo		= "";   // 最終ページ
	private String MaxRows			= "";   // 最大行数
	private String EndRowInPage	= "";   // 現在ページの終了行
	private String StartRowInPage	= "";   // 現在ページの開始行

// ↓↓2006.07.18 xiongjun カスタマイズ修正↓↓    
// 2006/01/27 kim START
//   private String siiresaki_cd			= null;	//仕入先コード
//   private String siiresaki_nm			= null;	//仕入先コード名
//   private String kanri_cd 				= null;	//管理コード
//   private String kanri_nm 				= null;	//商品コード名 
// 2006/01/27 kim END
// ↑↑2006.07.18 xiongjun カスタマイズ修正↑↑
   
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
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ

//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst250101_SyohinAkibanInit";	// 初期画面JSPを取得
	private static final String VIEW_PAGE = "mst250201_SyohinAkibanView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END


// ↓↓2006.07.18 xiongjun カスタマイズ修正↓↓
// 2006/01/27 kim START 仕入先コードと管理コードのセッター、 ゲッター
		
//   /**
//	  * 管理コードに対するセッター<br>
//	  * <br>
//	  * Ex)<br>
//	  * setKanriCd("文字列");<br>
//	  * <br>
//	  * @param String 設定する文字列
//	  */
//	 public boolean setKanriCd(String kanri_cd)
//	 {
//		 this.kanri_cd = kanri_cd;
//		 return true;
//	 }
//	 /**
//	  * 管理コードに対するゲッター<br>
//	  * <br>
//	  * Ex)<br>
//	  * getKanriCd();　戻り値　文字列<br>
//	  * <br>
//	  * @return String 文字列
//	  */
//	 public String getKanriCd()
//	 {
//		 return this.kanri_cd;
//	 }
//
//
//	 /**
//	  * 管理コード名に対するセッター<br>
//	  * <br>
//	  * Ex)<br>
//	  * setKanriNm("文字列");<br>
//	  * <br>
//	  * @param String 設定する文字列
//	  */
//		 public boolean setKanriNm(String kanri_nm)
//		 {
//			 this.kanri_nm = kanri_nm;
//			 return true;
//		 }
//	 /**
//	  * 管理コード名に対するゲッター<br>
//	  * <br>
//	  * Ex)<br>
//	  * getKanriNm();　戻り値　文字列<br>
//	  * <br>
//	  * @return String 文字列
//	  */
//		 public String getKanriNm()
//		 {
//			 return this.kanri_nm ;
//		 }
//
//
//   
//   
//	 /**
//	  * 仕入先に対するセッター<br>
//	  * <br>
//	  * Ex)<br>
//	  * setKanriNmuNm("文字列");<br>
//	  * <br>
//	  * @param String 設定する文字列
//	  */
//		 public boolean setSiiresakiCd(String siiresaki_cd)
//		 {
//			 this.siiresaki_cd = siiresaki_cd;
//			 return true;
//		 }
//	 /**
//	  * 仕入先コード名に対するゲッター<br>
//	  * <br>
//	  * Ex)<br>
//	  * getKanriNm();　戻り値　	<br>
//	  * <br>
//	  * @return String 文字列
//	  */
//		 public String getSiiresakiCd()
//		 {
//			 return this.siiresaki_cd;
//		 }
//
//
//	 /**
//	  * 仕入先名に対するセッター<br>
//	  * <br>
//	  * Ex)<br>
//	  * setSiiresakiNm("文字列");<br>
//	  * <br>
//	  * @param String 設定する文字列
//	  */
//		 public boolean setSiiresakiNm(String siiresaki_nm)
//		 {
//			 this.siiresaki_nm = siiresaki_nm;
//			 return true;
//		 }
//	 /**
//	  * 仕入先名に対するゲッター<br>
//	  * <br>
//	  * Ex)<br>
//	  * getSiiresakiNm();　戻り値　	<br>
//	  * <br>
//	  * @return String 文字列
//	  */
//		 public String getSiiresakiNm()
//		 {
//			 return this.siiresaki_nm;
//		 }

// 2006/01/27 kim END
// ↑↑2006.07.18 xiongjun カスタマイズ修正↑↑

	/**
	 * 選択に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSelect("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSelect(String select)
	{
		this.select = select;
		return true;
	}
	/**
	 * 選択に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSelect();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSelect()
	{
		return this.select;
	}


	/**
	 * 開始商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setStartSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setStartSyohinCd(String string) {
		startSyohinCd = string;
	}

	/**
	 * 開始商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getStartSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getStartSyohinCd() {
		return startSyohinCd;
	}

//	 ↓↓2006.07.18 xiongjun カスタマイズ修正↓↓
//	/**
//	 * 空番含むに対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setBlankKb("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//	/**
//	 * @param string
//	 */
//	public void setBlankKb(String string) {
//		blankKb = string;
//	}
//
//	/**
//	 * 空番含むに対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getBlankKb();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//	public String getBlankKb() {
//		if(blankKb == null){
//			return "0";
//		} else{
//			return blankKb;
//		}
//	}
//	 ↑↑2006.07.18 xiongjun カスタマイズ修正↑↑

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

//	↓↓2006.07.18 xiongjun カスタマイズ修正↓↓
//	/**
//	 * 検索時-空番含むに対するゲッター
//	 * @return
//	 */
//	public String getBlankKbOnSerch() {
//		return blankKbOnSerch;
//	}

	/**
	 * 検索時-選択に対するゲッター
	 * @return
	 */
	public String getSelectOnSerch() {
		return selectOnSerch;
	}

	/**
	 * 検索時-開始商品コードに対するゲッター
	 * @return
	 */
	public String getStartSyohinCdOnSerch() {
		return startSyohinCdOnSerch;
	}

//	/**
//	 * 検索時-空番含むに対するセッター
//	 * @param string 設定する文字列
//	 */
//	public void setBlankKbOnSerch(String string) {
//		blankKbOnSerch = string;
//	}

	/**
	 * 検索時-選択に対するセッター
	 * @param string 設定する文字列
	 */
	public void setSelectOnSerch(String string) {
		selectOnSerch = string;
	}

	/**
	 * 検索時-開始商品コードに対するセッター
	 * @param string 設定する文字列
	 */
	public void setStartSyohinCdOnSerch(String string) {
		startSyohinCdOnSerch = string;
	}

//BUGNO-S052 2005.05.14 SIRIUS START
// ↑↑2006.07.18 xiongjun カスタマイズ修正↑↑
	
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
//BUGNO-S052 2005.05.14 SIRIUS START

//	↓↓2006.07.18 xiongjun カスタマイズ修正↓↓
	public boolean setGamenKb(String gamen_kb)
    {
        this.gamen_kb = gamen_kb;
        return true;
    }
    public String getGamenKb()
    {
        return cutString(this.gamen_kb,GAMEN_KB_MAX_LENGTH);
    }
    public String getGamenKbString()
    {
        return "'" + cutString(this.gamen_kb,GAMEN_KB_MAX_LENGTH) + "'";
    }
    public String getGamenKbHTMLString()
    {
        return HTMLStringUtil.convert(cutString(this.gamen_kb,GAMEN_KB_MAX_LENGTH));
    }
	
	public String getBlankKb() {
		return blankKb;
	}
	public void setBlankKb(String blankKb) {
		this.blankKb = blankKb;
	}
	public String getBlankKbOnSerch() {
		return blankKbOnSerch;
	}
	public void setBlankKbOnSerch(String blankKbOnSerch) {
		this.blankKbOnSerch = blankKbOnSerch;
	}
	public String getByNo() {
		return byNo;
	}
	public void setByNo(String byNo) {
		this.byNo = byNo;
	}
	public String getByNoOnSerch() {
		return byNoOnSerch;
	}
	public void setByNoOnSerch(String byNoOnSerch) {
		this.byNoOnSerch = byNoOnSerch;
	}
	public String getEndSyohinCd() {
		return endSyohinCd;
	}
	public void setEndSyohinCd(String endSyohinCd) {
		this.endSyohinCd = endSyohinCd;
	}
	public String getEndSyohinCdOnSerch() {
		return endSyohinCdOnSerch;
	}
	public void setEndSyohinCdOnSerch(String endSyohinCdOnSerch) {
		this.endSyohinCdOnSerch = endSyohinCdOnSerch;
	}
	public String getSiiresakiSyohinCd() {
		return siiresakiSyohinCd;
	}
	public void setSiiresakiSyohinCd(String siiresakiSyohinCd) {
		this.siiresakiSyohinCd = siiresakiSyohinCd;
	}
	public String getSiiresakiSyohinCdOnSerch() {
		return siiresakiSyohinCdOnSerch;
	}
	public void setSiiresakiSyohinCdOnSerch(String siiresakiSyohinCdOnSerch) {
		this.siiresakiSyohinCdOnSerch = siiresakiSyohinCdOnSerch;
	}
	public String getTourokusumi() {
		return tourokusumi;
	}
	public void setTourokusumi(String tourokusumi) {
		this.tourokusumi = tourokusumi;
	}
	public String getTourokusumiOnSerch() {
		return tourokusumiOnSerch;
	}
	public void setTourokusumiOnSerch(String tourokusumiOnSerch) {
		this.tourokusumiOnSerch = tourokusumiOnSerch;
	}
	public String getYoyakusumi() {
		return yoyakusumi;
	}
	public void setYoyakusumi(String yoyakusumi) {
		this.yoyakusumi = yoyakusumi;
	}
	public String getYoyakusumiOnSerch() {
		return yoyakusumiOnSerch;
	}
	public void setYoyakusumiOnSerch(String yoyakusumiOnSerch) {
		this.yoyakusumiOnSerch = yoyakusumiOnSerch;
	}
//  ↑↑2006.07.18 xiongjun カスタマイズ修正↑↑
//	↓↓2006.07.19 xiongjun カスタマイズ修正↓↓	
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
//	↑↑2006.07.19 xiongjun カスタマイズ修正↑↑
/**
 * @return
 */
public String getSscdFlg()
{
	return sscdFlg;
}

/**
 * @param string
 */
public void setSscdFlg(String string)
{
	sscdFlg = string;
}

}
