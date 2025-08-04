/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst002601_AutoHachuKbDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
public class mst270201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	//-------------------
	//画面検索条件
	//-------------------
//	private String hanku_cd      = "";	//販区コード
//	private String hanku_nm      = ""; //販区コード名称
	
//	****** DB Var3.6 修正箇所 *****仕入先コード削除**********************	
//	private String siiresaki_cd  = "";	//仕入先コード
//	private String siiresaki_nm  = ""; //仕入先コード名称

//	****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
	private String hachuno_cd 	= "";	//発注コード

	private String syohin_cd     = "";	//商品コード
	private String syohin_nm     = "";	//商品コード名称
	
	private String bumon_cd     = "";	//部門コード
	private String bumon_nm     = "";	//部門コード名称
	
//	↓↓仕様追加（2005.07.07）↓↓	
//	private String theme_cd      = "";	//テーマコード
//	private String theme_nm      = "";	//テーマコード名称
//	↑↑仕様追加（2005.07.07）↑↑

	private String processingDivision = ""; //処理状況
	
	private String tenGroupNo_cd = "";	//店配列コード
	private String tenGroupNo_nm = "";	//店配列コード名称
	private String backFlg = "";

	//-------------------
	//画面データ部
	//-------------------
	private List meisai         = new ArrayList(); //一覧の明細
	private String gentanka_vl  = "";  //商品マスタ.原価
	private String baitanka_vl  = "";  //商品マスタ.売価
//	↓↓仕様追加（2005.07.07）↓↓	
	private String theme_cd      = "";	//テーマコード
	private String theme_nm      = "";	//テーマコード名称
	private String DispRows		= "30";				// 一画面表示明細数
	private String CurrentPageNo	= "";				// 現在表示ページ
	private String LastPageNo		= "";				// 最終ページ
	private String MaxRows			= "0";			// 最大行数
	private String EndRowInPage	= "";				// 現在ページの終了行
	private String StartRowInPage	= "";				// 現在ページの開始行
	private String PageMode		= "";				// ページ遷移の処理状況
//	↑↑仕様追加（2005.07.07）↑↑
//	↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
	private String hachutani_irisu_qt  = "";  //商品マスタ.発注単位（入数）
//	↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑

	//-------------------
	//共通
	//-------------------
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
	private static final String INIT_PAGE 		= "mst270101_SyokaiDonyuJyukyoInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE 		= "mst270201_SyokaiDonyuJyukyoEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE 		= "mst270301_SyokaiDonyuJyukyoView";	// 削除・照会画面JSPを取得
	private static final String KENGEN_PAGE		= "mst000401_KengenError";				// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END

//↓↓仕様追加（2005.07.12）↓↓
  private List chkLst = new ArrayList();	//選択チェック状態
//↑↑仕様追加（2005.07.12）↑↑

  
//2006/03/02 kim START
  private List autoHachuList=null;         //自動発注一時停止リスト変数

  /**
	   * 自動発注一時停止リストに対するセッター<br>
	   * <br>
	   * Ex)<br>
	   * setAutoHachuList();<br>
	   * <br>
	   * @param なし
	   */
	  public void setAutoHachuList() {
		  List list = new ArrayList();		
			list.add( mst002601_AutoHachuKbDictionary.TAISYOGAI.getCode() );	//"0","対象外"
		  	list.add( mst002601_AutoHachuKbDictionary.TAISYO.getCode() );		//"1", "対象"
		//  list.add( mst002601_AutoHachuKbDictionary.UNKNOWN.getCode() );	//"X", "UNKNOWN"
		 
		  this.autoHachuList = list;
	  }

	  /**
	   * 自動発注一時停止リストに対するゲッター<br>
	   * <br>
	   * Ex)<br>
	   * getYukoChkLst();　戻り値　自動発注一時停止リスト<br>
	   * <br>
	   * @return List 自動発注一時/停止リスト
	   */
	  public List getAutoHachuList() {
		  if( this.autoHachuList == null){
			  this.setAutoHachuList();
		  }
		  return this.autoHachuList;
	  }

	  /**
	   * 自動発注一時停止リストに対するゲッター<br>
	   * <br>
	   * Ex)<br>
	   * getYukoChkLst( 1 );　戻り値　自動発注一時停止コード<br>
	   * <br>
	   * @return String 自動発注一時/停止コード
	   */
	  public String getAutoHachuList( int i ) {
		  if( this.autoHachuList == null){
			  this.setAutoHachuList();
		  }
		  return (String)this.autoHachuList.get(i); 
	  }
	
	  /**
	   * 商品マスターにある値札区分に対するセッター<br>
	   * <br>
	   * Ex)<br>
	   * setNefudaKBMst("文字列");<br>
	   * <br>
	   * @param String 設定する文字列
	   */
	  private String nefudaKBMst=null;
	
	  public boolean setNefudaKBMst(String nefudaKBMst)
	  {
		  this.nefudaKBMst=nefudaKBMst;
		  return true;
	  }
	
	  /**
	   * 商品マスターにある値札区分に対するゲッター<br>
	   * <br>
	   * Ex)<br>
	   * getNefudaKBMst();　戻り値　文字列<br>
	   * <br>
	   * @return String 文字列
	   */
	  public String getNefudaKBMst()
	  {
		  return this.nefudaKBMst;
	  }
//	2006/03/02 kim END


	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
	/**
	 * 販区コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
//	public String getHankuCd()
//	{
//		return this.hanku_cd;
//	}


	/**
	 * 販区コード名称に対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//	public boolean setHankuNm(String hanku_nm)
//	{
//		this.hanku_nm = hanku_nm;
//		return true;
//	}
	/**
	 * 販区コード名称に対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
//	public String getHankuNm()
//	{
//		return this.hanku_nm;
//	}

//	****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
	/**
	 * 発注NO.コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHachunoCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setHachunoCd(String hachuno_cd)
	{
		this.hachuno_cd = hachuno_cd;
		return true;
	}
	/**
	 * 発注NO.コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHachunoCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHachunoCd()
	{
		return this.hachuno_cd;
	}

//	****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//	/**
//	 * 仕入先コードに対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setSiiresakiCd("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//	public boolean setSiiresakiCd(String siiresaki_cd)
//	{
//		this.siiresaki_cd = siiresaki_cd;
//		return true;
//	}
//	/**
//	 * 仕入先コードに対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getSiiresakiCd();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//	public String getSiiresakiCd()
//	{
//		return this.siiresaki_cd;
//	}
//
//	/**
//	 * 仕入先コード名称に対するセッター<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//	public boolean setSiiresakiNm(String siiresaki_nm)
//	{
//		this.siiresaki_nm = siiresaki_nm;
//		return true;
//	}
//	/**
//	 * 仕入先コード名称に対するゲッター<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//	public String getSiiresakiNm()
//	{
//		return this.siiresaki_nm;
//	}

	/**
	 * 部門コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */	
	public String getBumonCd() {
		return bumon_cd;
	}

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumon_cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setBumonCd(String bumon_cd) {
		this.bumon_cd = bumon_cd;
	}

	/**
	 * 部門名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBumonNm() {
		return bumon_nm;
	}

	/**
	 * 部門名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumon_nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setBumonNm(String bumon_nm) {
		this.bumon_nm = bumon_nm;
	}
	
	/**
	 * 商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	/**
	 * 商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohinCd()
	{
		return this.syohin_cd;
	}


	/**
	 * テーマコードに対するセッター<br>
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
	 * テーマコードに対するゲッター<br>
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
	 * 商品コード名称に対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohinNm() {
		return syohin_nm;
	}
	/**
	 * 商品コード名称に対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSyohinNm(String string) {
		syohin_nm = string;
	}


	/**
	 * テーマコード名称に対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getThemeNm() {
		return theme_nm;
	}
	/**
	 * テーマコード名称に対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setThemeNm(String string) {
		theme_nm = string;
	}


	/**
	 * 店配列コードに対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTenGroupNoCd() {
		return tenGroupNo_cd;
	}
	/**
	 * 店配列コードに対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setTenGroupNoCd(String string) {
		tenGroupNo_cd = string;
	}

	/**
	 * 店配列コード名称に対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTenGroupNoNm() {
		return tenGroupNo_nm;
	}
	/**
	 * 店配列コード名称に対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setTenGroupNoNm(String string) {
		tenGroupNo_nm = string;
	}

	/**
	 * 一覧の明細に対するゲッター<br>
	 * <br>
	 * @return List リスト
	 */
	public List getMeisai() {
		return meisai;
	}

	/**
	 * 一覧の明細に対するセッター<br>
	 * <br>
	 * @param List 設定するリスト
	 */
	public void setMeisai(List list) {
		meisai = list;
	}

	/**
	 * 商品マスタ.原価に対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGentanka_vl() {
		return gentanka_vl;
	}
	/**
	 * 商品マスタ.原価に対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setGentanka_vl(String d) {
		gentanka_vl = d;
	}

	/**
	 * 商品マスタ.売価に対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBaitanka_vl() {
		return baitanka_vl;
	}
	/**
	 * 商品マスタ.売価に対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setBaitanka_vl(String l) {
		baitanka_vl = l;
	}

//	↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * 商品マスタ.発注単位（入数）に対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHachutaniIrisuQt() {
		return hachutani_irisu_qt;
	}
	/**
	 * 商品マスタ.発注単位（入数）に対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHachutaniIrisuQt(String d) {
		hachutani_irisu_qt = d;
	}
//	↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑

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

//↓↓仕様変更（2005.07.12）↓↓
  /**
   * 選択チェックボックスに対するゲッター<br>
   * <br>
   * @return List リスト
   */
  public List getCheckList() {
	  return chkLst;
  }
  /**
   * 選択チェックボックスに対するセッター<br>
   * <br>
   * @param List 設定するリスト
   */
  public void setCheckList(List list) {
	  chkLst = list;
  }
//↑↑仕様変更（2005.07.12）↑↑

public String getDispRows() {
	return DispRows;
}

public void setDispRows(String dispRows) {
	DispRows = dispRows;
}

public String getEndRowInPage() {
	return EndRowInPage;
}

public void setEndRowInPage(String endRowInPage) {
	EndRowInPage = endRowInPage;
}

public String getLastPageNo() {
	return LastPageNo;
}

public void setLastPageNo(String lastPageNo) {
	LastPageNo = lastPageNo;
}

public String getMaxRows() {
	return MaxRows;
}

public void setMaxRows(String maxRows) {
	MaxRows = maxRows;
}

public String getPageMode() {
	return PageMode;
}

public void setPageMode(String pageMode) {
	PageMode = pageMode;
}

public String getCurrentPageNo() {
	return CurrentPageNo;
}

public void setCurrentPageNo(String currentPageNo) {
	CurrentPageNo = currentPageNo;
}

public String getStartRowInPage() {
	return StartRowInPage;
}

public void setStartRowInPage(String startRowInPage) {
	StartRowInPage = startRowInPage;
}

public String getBackFlg() {
	return backFlg;
}

public void setBackFlg(String backFlg) {
	this.backFlg = backFlg;
}

}
