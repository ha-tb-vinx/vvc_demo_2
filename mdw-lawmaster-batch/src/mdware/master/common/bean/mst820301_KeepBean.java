/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst820301_ThemeItem用テーマ別アイテム一覧の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst820301_ThemeItem用テーマ別アイテム一覧の画面項目格納用クラス</P>
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
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
//BUGNO-S052 2005.05.14 S.Takahashi START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 S.Takahashi END

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst820301_ThemeItem用テーマ別アイテム一覧の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst820301_ThemeItem用テーマ別アイテム一覧の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 * 変更履歴：2006-03-29 M.Kawamoto
 * 　　　　　1.計量単位の取得のための、計量器のタイプコードを追加
 */
public class mst820301_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KEIRYO_HANKU_CD_MAX_LENGTH = 1;		// 計量販区コードの長さ
	public static final int SYOHIN_YOBIDASI_MAX_LENGTH = 4;		// 呼出NOの長さ
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;			// 小業種コード (FK)の長さ
	public static final int THEME_CD_MAX_LENGTH = 2;				// テーマコード (FK)の長さ
	public static final int HANEI_DT_MAX_LENGTH = 8;				// 反映日の長さ
	public static final int HANEI_DT_KB_MAX_LENGTH = 8;			// 反映日押下区分の長さ
	public static final int BLANK_KB_MAX_LENGTH = 1;				// 空白含む区分の長さ
	//2006-03-29 M.Kawamoto 計量タイプコード追加
	public static final int KEIRYO_TYPE_CD_MAX_LENGTH = 1;		// 計量器タイプの長さ

	private String StartNo				= "";				// 開始添加物No.
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
	private String changekeyflg		= "";				// キー部変更フラグ

	//2006-03-29 M.Kawamoto 計量タイプコード追加
	private String keiryo_type_cd		= "";				//計量器タイプ


	private String ProcessingDivision	= "0";				// 処理区分
	private String s_gyosyu_cd			= "";				//小業種コード (FK)
	private String theme_cd			= "";				//テーマコード (FK)
	private String keiryo_hanku_cd		= "";				//計量販区コード
	private String syohin_yobidasi		= "";				//呼出NO
	private String hanei_dt			= "";				//反映日
	private String hanei_dt_kb			= "";				//反映日押下区分
	private String blank_kb			= "";				//空白含む区分の長さ

//	BUGNO-S005 2005.04.21 T.Makuta START
	private String changeflg			= "0";				//変更フラグ
//	BUGNO-S005 2005.04.21 T.Makuta END
//BUGNO-S052 2005.05.14 S.Takahashi START
	private static final String INIT_PAGE   = "mst820101_ThemeItemInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE   = "mst820201_ThemeItemEdit";	// 更新画面JSPを取得
	private static final String VIEW_PAGE   = "mst820301_ThemeItemView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 S.Takahashi END

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
	// StartNoに対するセッターとゲッターの集合
	public boolean setStartNo(String StartNo)
	{
		this.StartNo = StartNo;
		return true;
	}
	public String getStartNo()
	{
		return this.StartNo;
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
	// keiryo_hanku_cdに対するセッターとゲッターの集合
	public boolean setKeiryoHankuCd(String keiryo_hanku_cd)
	{
		this.keiryo_hanku_cd = keiryo_hanku_cd;
		return true;
	}
	public String getKeiryoHankuCd()
	{
		return cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH);
	}
	public String getKeiryoHankuCdString()
	{
		return "'" + cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getKeiryoHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH));
	}


	// syohin_yobidasiに対するセッターとゲッターの集合
	public boolean setSyohinYobidasi(String syohin_yobidasi)
	{
		this.syohin_yobidasi = syohin_yobidasi;
		return true;
	}
	public String getSyohinYobidasi()
	{
		return cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH);
	}
	public String getSyohinYobidasiString()
	{
		return "'" + cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH) + "'";
	}
	public String getSyohinYobidasiHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH));
	}


	// s_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
		return true;
	}
	public String getSGyosyuCd()
	{
		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSGyosyuCdString()
	{
		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
	}


	// theme_cdに対するセッターとゲッターの集合
	public boolean setThemeCd(String theme_cd)
	{
		this.theme_cd = theme_cd;
		return true;
	}
	public String getThemeCd()
	{
		return cutString(this.theme_cd,THEME_CD_MAX_LENGTH);
	}
	public String getThemeCdString()
	{
		return "'" + cutString(this.theme_cd,THEME_CD_MAX_LENGTH) + "'";
	}
	public String getThemeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.theme_cd,THEME_CD_MAX_LENGTH));
	}


	// hanei_dtに対するセッターとゲッターの集合
	public boolean setHaneiDt(String hanei_dt)
	{
		this.hanei_dt = hanei_dt;
		return true;
	}
	public String getHaneiDt()
	{
		return cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH);
	}
	public String getHaneiDtString()
	{
		return "'" + cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH) + "'";
	}
	public String getHaneiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH));
	}


	// hanei_dtに対するセッターとゲッターの集合
	public boolean setHaneiDtKb(String hanei_dt_kb)
	{
		this.hanei_dt_kb = hanei_dt_kb;
		return true;
	}
	public String getHaneiDtKb()
	{
		return cutString(this.hanei_dt_kb,HANEI_DT_MAX_LENGTH);
	}
	public String getHaneiDtKbString()
	{
		return "'" + cutString(this.hanei_dt_kb,HANEI_DT_MAX_LENGTH) + "'";
	}
	public String getHaneiDtKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanei_dt_kb,HANEI_DT_MAX_LENGTH));
	}


	// blank_kbに対するセッターとゲッターの集合
	public boolean setBlankKb(String blank_kb)
	{
		this.blank_kb = blank_kb;
		return true;
	}
	public String getBlankKb()
	{
		return cutString(this.blank_kb,BLANK_KB_MAX_LENGTH);
	}
	public String getBlankKbString()
	{
		return "'" + cutString(this.blank_kb,BLANK_KB_MAX_LENGTH) + "'";
	}
	public String getBlankKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.blank_kb,BLANK_KB_MAX_LENGTH));
	}

	// changekeyflgに対するセッターとゲッターの集合
	public boolean setChangeKeyFlg(String changekeyflg)
	{
		this.changekeyflg = changekeyflg;
		return true;
	}
	public String getChangeKeyFlg()
	{
		return this.changekeyflg;
	}

//	BUGNO-S005 2005.04.21 T.Makuta START
	// changeflgに対するセッターとゲッターの集合
	public boolean setChangeflg(String changeflg)
	{
		this.changeflg = changeflg;
		return true;
	}
	public String getChangeflg()
	{
		return this.changeflg;
	}
//	BUGNO-S005 2005.04.21 T.Makuta END

//2006-03-29 M.Kawamoto 計量タイプコード追加
	// keiryo_type_cdに対するセッターとゲッターの集合
	public boolean setKeiryoTypeCd(String keiryo_type_cd)
	{
		this.keiryo_type_cd = keiryo_type_cd;
		return true;
	}
	public String getKeiryoTypeCd()
	{
		return cutString(this.keiryo_type_cd,KEIRYO_TYPE_CD_MAX_LENGTH);
	}
	public String getKeiryoTypeCdString()
	{
		return "'" + cutString(this.keiryo_type_cd,KEIRYO_TYPE_CD_MAX_LENGTH) + "'";
	}
	public String getKeiryoTypeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryo_type_cd,KEIRYO_TYPE_CD_MAX_LENGTH));
	}
//ここまで	
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
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}


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

