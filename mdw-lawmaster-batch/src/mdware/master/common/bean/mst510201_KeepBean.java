/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kiiuchi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.Map;
import java.util.HashMap;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kiiuchi
 * @version 1.0
 * @see なし								
 */
public class mst510201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String ProcessingDivision	= "0";	// 処理区分
	private String ErrorFlg			= "";	// エラーフラグ
	private String ErrorMessage		= "";	// エラーメッセージ
	private String Mode				= "";	// 処理モード
	private String[] MenuBar			= null;			// メニューバーアイテム
	private Map CtrlColor				= new HashMap();	// コントロール前景色
	private String FirstFocus			= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg			= "";				//
	private String UpdateFlg			= "";				//
	private String DeleteFlg			= "";				//
	private String ReferenceFlg		= "";				//
	private String CsvFlg				= "";				//
	private String PrintFlg			= "";				//
	
	private String kanri_kb 			= null;	//管理区分 (FK)
	private String kanri_cd 			= null;	//管理コード (FK)
	private String kanri_kanji_na 		= null;	//管理名称
	private String siiresaki_cd 		= null;	//仕入先コード (FK)
	private String siiresaki_kanji_rn 	= null;	//仕入先略名称
	private String date_dt 			= null;	//日付
	private String mstDate			= null;	//マスタ日付		Add By Kou 2006-04-06
	
	private int dispRows = 0;	//画面表示件数
	
//	BUGNO-S011 20050421 S.Murata START
	private String snm					= "";				//仕入先名
//	BUGNO-S011 20050421 S.Murata END
//	BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE 		= "mst510101_SiireHachuNohinNgInit";	// 初期画面JSPを取得	
	private static final String EDIT_PAGE 		= "mst510201_SiireHachuNohinNgEdit";	// 新規・修正画面JSPを取得
	private static final String KENGEN_PAGE      = "mst000401_KengenError";	// 権限エラーJSPを取得
//	BUGNO-S052 2005.05.14 Y.Gotoh END
	
	/**
	 * 画面表示件数のgetter
	 * @return
	 */
	public int getDispRows() {
		return this.dispRows;
	}

	/**
	 * 画面表示件数のsetter
	 * @param i
	 */
	public boolean setDispRows(int rows) {
		this.dispRows = rows;
		return true;
	}
	

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
	// kanri_kbに対するセッターとゲッターの集合
	public boolean setKanriKb(String kanri_kb)
	{
		this.kanri_kb = kanri_kb;
		return true;
	}
	public String getKanriKb()
	{
		return this.kanri_kb;
	}

	// kanri_cdに対するセッターとゲッターの集合
	public boolean setKanriCd(String kanri_cd)
	{
		this.kanri_cd = kanri_cd;
		return true;
	}
	public String getKanriCd()
	{
		return this.kanri_cd;
	}
	
	// kanri_kanji_naに対するセッターとゲッターの集合
	public boolean setKanriKanjiNa(String kanri_kanji_na)
	{
		this.kanri_kanji_na = kanri_kanji_na;
		return true;
	}
	public String getKanriKanjiNa()
	{
		return this.kanri_kanji_na;
	}

	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
	public String getSiiresakiCd()
	{
		return this.siiresaki_cd;
	}

	// siiresaki_kanji_rnに対するセッターとゲッターの集合
	public boolean setSiiresakiKanjiRn(String siiresaki_kanji_rn)
	{
		this.siiresaki_kanji_rn = siiresaki_kanji_rn;
		return true;
	}
	public String getSiiresakiKanjiRn()
	{
		return this.siiresaki_kanji_rn;
	}

	// date_dtに対するセッターとゲッターの集合
	public boolean setDateDt(String date_dt)
	{
		this.date_dt = date_dt;
		return true;
	}
	public String getDateDt()
	{
		return this.date_dt;
	}

	//	BUGNO-S011 20050421 S.Murata START
	/**
	 * @return
	 */
	public String getSnm() {
		return snm;
	}
	
	/**
	 * @param string
	 */
	public void setSnm(String string) {
		snm = string;
	}
	//BUGNO-S011 20050421 S.Murata END

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
	  public String getInitUrl(String logHeader,String logMsg, SessionManager sessionManager )
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
		  InitPocess(sessionManager);
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
	  public String getInitUrl( SessionManager sessionManager )
	  {
		  InitPocess(sessionManager);
		  return	INIT_PAGE;
	  }
	  
	/**
	 * 初期画面用処理
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public void InitPocess( SessionManager sessionManager )
	{
		mst510101_SiireHachuNohinNgBean sirengb 	= new mst510101_SiireHachuNohinNgBean();
		sirengb.setKanriKb(this.kanri_kb);
		sirengb.setKanriCd(this.kanri_cd);
		sirengb.setKanriKanjiNa(this.kanri_kanji_na);
		sirengb.setSiiresakiCd(this.siiresaki_cd);
		sirengb.setSiiresakiKanjiRn(this.siiresaki_kanji_rn);
		sirengb.setDateDt(this.date_dt);

		sessionManager.setAttribute("mst510101_SiireHachuNohinNg", sirengb);
			
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


	/**
	 * @return
	 */
	public String getMstDate() {
		return mstDate;
	}

	/**
	 * @param string
	 */
	public void setMstDate(String string) {
		mstDate = string;
	}

}
