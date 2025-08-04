/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Jozawa START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Jozawa END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */
public class mst310201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	
	private String ProcessingDivision		= "0";				// 処理区分
	private String ErrorFlg				= "";				// エラーフラグ
	private String ErrorMessage			= "";				// エラーメッセージ
	private String Mode					= "";				// 処理モード
	private String[] MenuBar				= null;			// メニューバーアイテム
	private Map CtrlColor					= new HashMap();	// コントロール前景色
	private String FirstFocus				= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg				= "";				// 新規処理利用可能区分
	private String UpdateFlg				= "";				// 更新処理利用可能区分
	private String DeleteFlg				= "";				// 削除処理利用可能区分
	private String ReferenceFlg			= "";				// 照会処理利用可能区分
	private String CsvFlg					= "";				// CSV処理利用可能区分
	private String PrintFlg				= "";				// 印刷処理利用可能区分
	private String CheckFlg			    = "";				// チェック処理判断
	private String ChangeFlg			= "0";				// 明細部変更かどうか判断
	
	private String updateProcessFlg 		= "";				//更新処理内容
	private String insert_ts 				= "";				//作成年月日
	private String insert_user_id 			= "";				//作成者社員ID
	private String insert_user_nm 			= "";				//作成者社員名
	private String update_ts 				= "";				//更新年月日
	private String update_user_id 			= "";				//更新者社員ID
	private String update_user_nm 			= "";				//更新者社員名

//	↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
//	private String hanku_cd 				= "";				//販区コード
	private String bumon_cd 				= null;	            //部門コード
	private String bumon_nm 				= null;	            //部門コード名
	private String yuko_dt 					= null;				//有効日
//	↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
	private String hanku_kanji_rn 			= "";				//販区名
	private String syohin_cd				= "";				//商品コード
	private String hinmei_kanji_na			= "";				//商品名
//	↓↓仕様変更（2005.07.29）↓↓	
//	private String yuko_dt 					= null;				//有効日
//	private String donyu_st_dt				= null;			//導入開始日
//	↑↑仕様変更（2005.07.29）↑↑

//BUGNO-S052 2005.05.14 Y.Jozawa START
	private static final String INIT_PAGE = "mst310101_TanpinTenToriatukaiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst310201_TanpinTenToriatukaiEdit";	// 新規・削除画面JSPを取得
	private static final String VIEW_PAGE = "mst310301_TanpinTenToriatukaiView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Jozawa END

	// Processingdivisionに対するセッターとゲッターの集合
	public boolean setProcessingDivision(String ProcessingDivision)
	{
		this.ProcessingDivision = ProcessingDivision;
		return true;
	}
	public String getProcessingDivision()
	{
		return this.ProcessingDivision;
	}

	// ErrorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String ErrorFlg)
	{
		this.ErrorFlg = ErrorFlg;
		return true;
	}
	public String getErrorFlg()
	{
		return this.ErrorFlg;
	}

	// ErrorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
		return true;
	}
	public String getErrorMessage()
	{
		return this.ErrorMessage;
	}

	// Modeに対するセッターとゲッターの集合
	public boolean setMode(String Mode)
	{
		this.Mode = Mode;
		return true;
	}
	public String getMode()
	{
		return this.Mode;
	}

	// MenuBarに対するセッターとゲッターの集合
	public boolean setMenuBar(String[] MenuBar)
	{
		this.MenuBar = MenuBar;
		return true;
	}
	public String[] getMenuBar()
	{
		return this.MenuBar;
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

	// FirstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String FirstFocus)
	{
		this.FirstFocus = FirstFocus;
		return true;
	}
	public String getFirstFocus()
	{
		return this.FirstFocus;
	}
	// InsertFlgに対するセッターとゲッターの集合
	public boolean setInsertFlg(String InsertFlg)
	{
		this.InsertFlg = InsertFlg;
		return true;
	}
	public String getInsertFlg()
	{
		return this.InsertFlg;
	}
	// UpdateFlgに対するセッターとゲッターの集合
	public boolean setUpdateFlg(String UpdateFlg)
	{
		this.UpdateFlg = UpdateFlg;
		return true;
	}
	public String getUpdateFlg()
	{
		return this.UpdateFlg;
	}
	// DeleteFlgに対するセッターとゲッターの集合
	public boolean setDeleteFlg(String DeleteFlg)
	{
		this.DeleteFlg = DeleteFlg;
		return true;
	}
	public String getDeleteFlg()
	{
		return this.DeleteFlg;
	}
	// ReferenceFlgに対するセッターとゲッターの集合
	public boolean setReferenceFlg(String ReferenceFlg)
	{
		this.ReferenceFlg = ReferenceFlg;
		return true;
	}
	public String getReferenceFlg()
	{
		return this.ReferenceFlg;
	}
	// CsvFlgに対するセッターとゲッターの集合
	public boolean setCsvFlg(String CsvFlg)
	{
		this.CsvFlg = CsvFlg;
		return true;
	}
	public String getCsvFlg()
	{
		return this.CsvFlg;
	}
	// PrintFlgに対するセッターとゲッターの集合
	public boolean setPrintFlg(String PrintFlg)
	{
		this.PrintFlg = PrintFlg;
		return true;
	}
	public String getPrintFlg()
	{
		return this.PrintFlg;
	}
	
	// CheckFlgに対するセッターとゲッターの集合
	public boolean setCheckFlg(String CheckFlg)
	{
		this.CheckFlg = CheckFlg;
		return true;
	}
	public String getCheckFlg()
	{
		return this.CheckFlg;
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
	
	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
	public String getInsertTs()
	{
		return this.insert_ts;
	}

	// insert_user_idに対するセッターとゲッターの集合
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId()
	{
		return this.insert_user_id;
	}

	// insert_user_nmに対するセッターとゲッターの集合
	public boolean setInsertUserNm(String insert_user_nm)
	{
		this.insert_user_nm = insert_user_nm;
		return true;
	}
	public String getInsertUserNm()
	{
		return this.insert_user_nm;
	}

	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdateTs()
	{
		return this.update_ts;
	}

	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserId()
	{
		return this.update_user_id;
	}

	// update_user_nmに対するセッターとゲッターの集合
	public boolean setUpdateUserNm(String update_user_nm)
	{
		this.update_user_nm = update_user_nm;
		return true;
	}
	public String getUpdateUserNm()
	{
		return this.update_user_nm;
	}
//	↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
	// hanku_cdに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return this.hanku_cd;
//	}

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
	/**
	 * 部門コード名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setBumonNm(String bumon_nm)
		{
			this.bumon_nm = bumon_nm;
			return true;
		}
	/**
	 * 部門コード名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getBumonNm()
		{
			return this.bumon_nm ;
		}
//	↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
	
	// hanku_kanji_rnに対するセッターとゲッターの集合
	public boolean setHankuKanjiRn(String hanku_kanji_rn)
	{
		this.hanku_kanji_rn = hanku_kanji_rn;
		return true;
	}
	public String getHankuKanjiRn()
	{
		return this.hanku_kanji_rn;
	}
	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohinCd()
	{
		return this.syohin_cd;
	}
	
	// hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
		return true;
	}
	public String getHinmeiKanjiNa()
	{
		return this.hinmei_kanji_na;
	}

//	↓↓仕様変更（2005.07.29）↓↓
	// yuko_dtに対するセッターとゲッターの集合
//	public boolean setYukoDt(String yuko_dt)	
//	{
//		this.yuko_dt = yuko_dt;
//		return true;
//	}
//	public String getYukoDt()
//	{
//		return this.yuko_dt;
//	}
//	↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
//	yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)	
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYukoDt()
	{
		return this.yuko_dt;
	}

	// donyu_st_dtに対するセッターとゲッターの集合
//	public boolean setDonyuStDt(String donyu_st_dt)	
//	{
//		this.donyu_st_dt = donyu_st_dt;
//		return true;
//	}
//	public String getDonyuStDt()
//	{
//		return this.donyu_st_dt;
//	}
//	↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
//	↑↑仕様変更（2005.07.29）↑↑

	// updateProcessFlgに対するセッターとゲッターの集合
	public boolean setUpdateProcessFlg(String updateProcessFlg)	
	{
		this.updateProcessFlg = updateProcessFlg;
		return true;
	}
	public String getUpdateProcessFlg()
	{
		return this.updateProcessFlg;
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
//BUGNO-S052 2005.05.14 SIRIUS START
}
