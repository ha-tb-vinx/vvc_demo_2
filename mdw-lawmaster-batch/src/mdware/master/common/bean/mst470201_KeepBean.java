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
public class mst470201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String ProcessingDivision	= "0";	// 処理区分
	private String ErrorFlg			= "";	// エラーフラグ
	private String ErrorMessage		= "";	// エラーメッセージ
	private String Mode				= "";	// 処理モード
	private String[] MenuBar			= null;			// メニューバーアイテム
	private Map CtrlColor				= new HashMap();	// コントロール前景色
	private String FirstFocus			= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg			= "";				// 新規処理利用可能区分
	private String UpdateFlg			= "";				// 更新処理利用可能区分
	private String DeleteFlg			= "";				// 削除処理利用可能区分
	private String ReferenceFlg		= "";				// 照会処理利用可能区分
	private String CsvFlg				= "";				// CSV処理利用可能区分
	private String PrintFlg			= "";				// 印刷処理利用可能区分	
	private String kanri_kb 			= "";				//管理区分
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓
	private String back_kb                     = null;         //戻る区分
//	private String kanri_cd 			= "";				//管理コード
//	private String kanri_kanji_rn 		= "";				//管理名
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑	
	private String shihai_kb 			= "";				//仕配区分
	private String shihai_cd 			= "";				//仕配コード
	private String shihai_kanji_rn		= "";				//仕配名
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓	
	private String tenpo_cd         ="";				//店舗コード
	private String bumon_cd          ="";             //部門コード
	private String bumon_kanji_rn   ="";				//部門名	
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑		
//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE 		= "mst470101_HakoKanriInit";	// 初期画面JSPを取得
	private static final String VIEW_PAGE 		= "mst470201_HakoKanriView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE		= "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END

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
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓	
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
//	public boolean setKanriCd(String kanri_cd)
//	{
//		this.kanri_cd = kanri_cd;
//		return true;
//	}
//	public String getKanriCd()
//	{
//		return this.kanri_cd;
//	}
	
	// kanri_kanji_rnに対するセッターとゲッターの集合
//	public boolean setKanriKanjiRn(String kanri_kanji_rn)
//	{
//		this.kanri_kanji_rn = kanri_kanji_rn;
//		return true;
//	}
//	public String getKanriKanjiRn()
//	{
//		return this.kanri_kanji_rn;
//	}
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓	
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	public String getBumonCd()
	{
		return this.bumon_cd;
	}
	
	// bumon_kanji_rnに対するセッターとゲッターの集合
	public boolean setBumonKanjiRn(String bumon_kanji_rn)
	{
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}
	public String getBumonKanjiRn()
	{
		return this.bumon_kanji_rn;
	}
//	back_kbに対応するセッターとゲッターの集合
	public String  setBackKb(String back_kb)
	{
		return this.back_kb = back_kb;
	}

	public String getBackKb()
	{
	   return this.back_kb;
		
	}
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑
	
	// shihai_kbに対するセッターとゲッターの集合
	public boolean setShihaiKb(String shihai_kb)
	{
		this.shihai_kb = shihai_kb;
		return true;
	}
	public String getShihaiKb()
	{
		return this.shihai_kb;
	}
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd()
	{
		return this.tenpo_cd;
	}
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑	

	// shihai_cdに対するセッターとゲッターの集合
	public boolean setShihaiCd(String shihai_cd)
	{
		this.shihai_cd = shihai_cd;
		return true;
	}
	public String getShihaiCd()
	{
		return this.shihai_cd;
	}

	// shihai_kanji_rnに対するセッターとゲッターの集合
	public boolean setShihaiKanjiRn(String shihai_kanji_rn)
	{
		this.shihai_kanji_rn = shihai_kanji_rn;
		return true;
	}
	public String getShihaiKanjiRn()
	{
		return this.shihai_kanji_rn;
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
	 /*
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
	*/
	/**
	 * 登録画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
//	public String getEditUrl()
//	{
//		return	EDIT_PAGE;
//	}

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
