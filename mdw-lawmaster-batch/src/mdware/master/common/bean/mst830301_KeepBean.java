/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst830301_KeiryokiTheme用計量器テーマ登録の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst830301_KeiryokiTheme用計量器テーマ登録の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.*;
//BUGNO-S052 2005.05.14 S.Takahashi START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 S.Takahashi END

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst830301_KeiryokiTheme用計量器テーマ登録の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst830301_KeiryokiTheme用計量器テーマ登録の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst830301_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String SGyosyuCd			= "";				// 小業種コード
	private String ErrorFlg			= "";				// エラーフラグ
	private String ErrorMessage		= "";				// エラーメッセージ
	private String Mode				= "";				// 処理モード
	private String[] MenuBar			= null;			// メニューバーアイテム
	private Map CtrlColor				= new HashMap();	// コントロール前景色
	private String FirstFocus			= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg			= "";				// 新規処理利用可能区分
	private String UpdateFlg			= "";				// 更新処理利用可能区分
	private String DeleteFlg			= "";				// 削除処理利用可能区分
	private String ReferenceFlg		= "";				// 照会処理利用可能区分
	private String CsvFlg				= "";				// CSV処理利用可能区分
	private String PrintFlg			= "";				// 印刷処理利用可能区分
	private String DispUrl				= "";				// 現画面URL
	private String BeforeDispUrl		= "";				// 前画面URL
	private String BeforeDispKb		= "";				// 前画面区分(Menu:0 Menu以外:1)
	private String insert_ts			= "";				//作成年月日
	private String insert_user_id		= "";				//作成者社員ID
	private String update_ts			= "";				//更新年月日
	private String update_user_id		= "";				//更新者社員ID

//	BUGNO-S005 2005.04.21 T.Makuta START
	private String changeflg			= "0";				//変更フラグ
//	BUGNO-S005 2005.04.21 T.Makuta END
//BUGNO-S052 2005.05.14 S.Takahashi START
	private static final String INIT_PAGE   = "mst830101_KeiryokiThemeInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE   = "mst830201_KeiryokiThemeEdit";	// 登録画面JSPを取得
	private static final String VIEW_PAGE   = "mst830301_KeiryokiThemeView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 S.Takahashi END

	// SGyosyuCdに対するセッターとゲッターの集合
	public boolean setSGyosyuCd(String SGyosyuCd)
	{
		this.SGyosyuCd = SGyosyuCd;
		return true;
	}
	public String getSGyosyuCd()
	{
		return this.SGyosyuCd;
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
	// BeforeDispKbに対するセッターとゲッターの集合
	public boolean setBeforeDispKb(String BeforeDispKb)
	{
		this.BeforeDispKb = BeforeDispKb;
		return true;
	}
	public String getBeforeDispKb()
	{
		return this.BeforeDispKb;
	}
	// DispUrlに対するセッターとゲッターの集合
	public boolean setDispUrl(String DispUrl)
	{
		this.DispUrl = DispUrl;
		return true;
	}
	public String getDispUrl()
	{
		return this.DispUrl;
	}
	// BeforeDispUrlに対するセッターとゲッターの集合
	public boolean setBeforeDispUrl(String BeforeDispUrl)
	{
		this.BeforeDispUrl = BeforeDispUrl;
		return true;
	}
	public String getBeforeDispUrl()
	{
		return this.BeforeDispUrl;
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
	public String getInsertTsString()
	{
		return "'" + this.insert_ts + "'";
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
	public String getInsertUserIdString()
	{
		return "'" + this.insert_user_id + "'";
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
	public String getUpdateTsString()
	{
		return "'" + this.update_ts + "'";
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
	public String getUpdateUserIdString()
	{
		return "'" + this.update_user_id + "'";
	}

//	BUGNO-S005 2005.04.21 T.Makuta START
	// changeflgに対するセッターとゲッターの集合
	public String getChangeflg() {
		return changeflg;
	}

	public void setChangeflg(String string) {
		changeflg = string;
	}
//	BUGNO-S005 2005.04.21 T.Makuta END


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
//	BUGNO-S052 2005.05.14 SIRIUS START
  }

