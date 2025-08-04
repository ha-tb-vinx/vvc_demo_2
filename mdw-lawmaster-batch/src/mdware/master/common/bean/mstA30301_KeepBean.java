/**
 * <P>タイトル : 新ＭＤシステム マスターメンテナンス 店別按分率登録 データ表示格納用クラス</P>
 * <P>説明 : 新ＭＤシステム マスターメンテナンス 店別按分率登録 データ表示格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>		
 * @author magp
 * @version 1.0 (2006.07.18)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;


/**
 * <P>タイトル : 新ＭＤシステム マスターメンテナンス 店別按分率登録 データ表示格納用クラス</P>
 * <P>説明 : 新ＭＤシステム マスターメンテナンス 店別按分率登録 データ表示格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>		
 * @author magp
 * @version 1.0 (2006.07.18)初版作成
 */
public class mstA30301_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();


	private String bumon_cd 				= "";	//部門コード
	private String bumon_nm 				= "";	//商品コード名
	private String group_cd 				= "";	//グループコード
	private String group_nm 				= "";	//グループコード名
	private String copy_group_cd 			= "";	//コピーグループコード
	private boolean user_kb_siire_flg     = false;//ユーザ区分（仕入先）フラグ

	private String errorflg 				= "";	//エラーフラグ
	private String errormessage 			= "";	//エラーメッセージ
	private String checkflg 				= "";	//チェック処理判断

	private String firstfocus 				= "";	//フォーカスを最初に取得するオブジェクト名
	private String insertflg 				= "";	//新規処理利用可能区分
	private String updateflg 				= "";	//更新処理利用可能区分
	private String deleteflg 				= "";	//削除処理利用可能区分
	private String referenceflg 			= "";	//照会処理利用可能区分
	private String csvflg 					= "";	//CSV処理利用可能区分
	private String printflg 				= "";	//印刷処理利用可能区分
	private String changflg 				= "";	

	private Map ctrlcolor					= new HashMap();

	private static final String INIT_PAGE = "mstA30101_TenbetuAnbunInit";	// 初期画面JSPを取得
	private static final String VIEW_PAGE = "mstA30301_TenbetuAnbunView";	// 照会画面JSPを取得
	private static final String EDIT_PAGE = "mstA30201_TenbetuAnbunEdit";	// 登録画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";// 権限エラーJSPを取得


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

	/**
	 * グループコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGroupCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setGroupCd(String group_cd)
	{
		this.group_cd = group_cd;
		return true;
	}
	/**
	 * グループコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGroupCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGroupCd()
	{
		return this.group_cd;
	}


	/**
	 * グループコード名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGroupNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setGroupNm(String group_nm)
	{
		this.group_nm = group_nm;
		return true;
	}
	
	/**
	 * グループコード名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGroupNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGroupNm()
	{
		return this.group_nm ;
	}
		
	/**
	 * コピーグループコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCopyGroupCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCopyGroupCd(String copy_group_cd)
	{
		this.copy_group_cd = copy_group_cd;
		return true;
	}
	
	/**
	 * コピーコピーグループコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCopyGroupCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCopyGroupCd()
	{
		return this.copy_group_cd;
	}		

	/**
	 * ユーザ区分（仕入先）フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUserKbSiireFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUserKbSiireFlg(boolean user_kb_siire_flg)
	{
		this.user_kb_siire_flg = user_kb_siire_flg;
		return true;
	}
	
	/**
	 * ユーザ区分（仕入先）フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUserKbSiireFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public boolean getUserKbSiireFlg()
	{
		return this.user_kb_siire_flg;
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
	 * 登録画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getViewUrl("logHeader","logMsg") -&gt; String<br>
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
	 * getViewUrl() -&gt; String<br>
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
	public String getChangflg() {
		return changflg;
	}
	public void setChangflg(String changflg) {
		this.changflg = changflg;
	}


}
