/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス mst520201_TenpoList用店マスタ(一覧)の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst520301_TenpoList用店マスタ(一覧)の画面項目格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius M.Nakajima
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス mst520201_TenpoList用店マスタ(一覧)の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst520301_TenpoList用店マスタ(一覧)の画面項目格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius M.Nakajima
 * @version 1.0
 * @see なし								
 */
public class mst520201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String tenpo_cd			= "";				//店舗コード
//	  ↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
//	private String kanji_rn			= "";				//店舗名称
	private String kanji_na			= "";				//店舗名称
//	  ↑↑2006.06.19 zhouzt カスタマイズ修正↑↑	
	private String errorFlg			= "";				//エラーフラグ
	private String errorMessage		= "";				//エラーメッセージ
	private String[] menuBar			= null;			//メニューバーアイテム
	private String firstFocus			= "";				//フォーカスを最初に取得するオブジェクト名
	private Map ctrlColor				= new HashMap();	//コントロール背景色
	private String csvFlg				= "";				//CSV処理利用可能区分

//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst520101_TenpoListInit";	//初期画面JSPを取得
	private static final String VIEW_PAGE = "mst520201_TenpoListView";	//一覧画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END


	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd()
	{
		return this.tenpo_cd;
	}

//	  ↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
	// kanji_rnに対するセッターとゲッターの集合
//	public boolean setKanjiRn(String kanji_rn)
//	{
//		this.kanji_rn = kanji_rn;
//		return true;
//	}
//	public String getKanjiRn()
//	{
//		return this.kanji_rn;
//	}
	// kanji_naに対するセッターとゲッターの集合
	public boolean setKanjiNa(String kanji_na)
	{
		this.kanji_na = kanji_na;
		return true;
	}
	public String getKanjiNa()
	{
		return this.kanji_na;
	}
//	  ↑↑2006.06.19 zhouzt カスタマイズ修正↑↑	

	// errorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String errorFlg)
	{
		this.errorFlg = errorFlg;
		return true;
	}
	public String getErrorFlg()
	{
		return this.errorFlg;
	}

	// errorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
		return true;
	}
	public String getErrorMessage()
	{
		return this.errorMessage;
	}

	// menuBarに対するセッターとゲッターの集合
	public boolean setMenuBar(String[] menuBar)
	{
		this.menuBar = menuBar;
		return true;
	}
	public String[] getMenuBar()
	{
		return this.menuBar;
	}

	// ctrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map ctrlColor)
	{
		this.ctrlColor = ctrlColor;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.ctrlColor;
	}

	// firstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String firstFocus)
	{
		this.firstFocus = firstFocus;
		return true;
	}
	public String getFirstFocus()
	{
		return this.firstFocus;
	}

	// csvFlgに対するセッターとゲッターの集合
	public boolean setCsvFlg(String csvFlg)
	{
		this.csvFlg = csvFlg;
		return true;
	}
	public String getCsvFlg()
	{
		return this.csvFlg;
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

}
