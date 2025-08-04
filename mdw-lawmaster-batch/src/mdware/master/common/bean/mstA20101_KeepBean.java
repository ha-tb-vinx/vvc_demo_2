/**
 * <P>タイトル : 新ＭＤシステム マスターメンテナンス サブクラス登録 データ表示格納用クラス</P>
 * <P>説明 : 新ＭＤシステム マスターメンテナンス サブクラス登録 データ表示格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>		
 * @author yaoyujun
 * @version 1.0 (2006.07.18)初版作成
 */
package mdware.master.common.bean;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
/**
 * <P>タイトル : 新ＭＤシステム マスターメンテナンス サブクラス登録 データ表示格納用クラス</P>
 * <P>説明 : 新ＭＤシステム マスターメンテナンス サブクラス登録 データ表示格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>		
 * @author yaoyujun
 * @version 1.0 (2006.07.18)初版作成
 */

public class mstA20101_KeepBean {

	private static final StcLog stcLog = StcLog.getInstance();
	
	//-------------------
	//画面検索条件
	//-------------------
	private String bumon_cd      = "";	// 部門コード
	private String bumon_nm      = ""; // 部門名称
	
	private mstA20301_SubClassBean[] meisai = new mstA20301_SubClassBean[100];//mstA20301_SubClassBean明細配列
	private String subclass_cd_from = "";			// 開始選択コード
	private String subclass_cd_to = ""; 			// 終了選択コード
	
	//-------------------
	//共通
	//-------------------
	private String errorFlg = null;			// エラーフラグ
	private String errorMessage = null;		// エラーメッセージ	
	
	private String firstFocus = null;			// フォーカスを最初に取得するオブジェクト名	
	
	private String checkFlg = null;			// チェック処理判断
	private String existFlg = "0";			// データ存在(検索[ｷｬﾝｾﾙ]時)
	private String changFlg = "0"; 
	
	private Map ctrlColor = new HashMap();		// コントロール背景色
	private String insertTs = null;			// 作成年月日
	private String insertUserId = null;		// 作成者社員ID
	private String updateTs = null;			// 更新年月日
	private String updateUserId = null;		// 更新者社員ID
	private String delete_fg = null;			// 削除フラグ	
	
	private static final String INIT_PAGE 		= "mstA20101_SubClassInit";		// 初期画面JSPを取得
	private static final String EDIT_PAGE 		= "mstA20201_SubClassEdit";		// 新規・修正画面JSPを取得	
	
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
	 * 販区コードに対するゲッター<br>
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
	 * 部門コード名称に対するセッター<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setBumonNm(String bumon_nm)
	{
		this.bumon_nm = bumon_nm;
		return true;
	}
	
	/**
	 * 部門コード名称に対するゲッター<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBumonNm()
	{
		return this.bumon_nm;
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
	 * 初期画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getInitUrl21("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	  public String getInitUrl21(String logHeader,String logMsg)
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
	  public String getInitUrl21()
	  {
		  return	INIT_PAGE;
	  }	  
		
		/**
		 * 明細配列に対するゲッター<br>
		 * <br>
		 * Ex)<br>
		 * getMeisai();　戻り値　mst580101_TenGroupNoBean[]<br>
		 * <br>
		 * @return mst580101_TenGroupNoBean[] 明細配列
		 */
		public mstA20301_SubClassBean[] getMeisai() {
			return meisai;
		}

		/**
		 * 明細配列に対するセッター<br>
		 * <br>
		 * Ex)<br>
		 * setMeisai(mst580101_TenGroupNoBean[]);　戻り値　なし<br>
		 * <br>
		 * @param mst580101_TenGroupNoBean[] 明細配列
		 */
		public void setMeisai(mstA20301_SubClassBean[] beans) {
			meisai = beans;
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
		 * 照会画面URL取得(ログ出力なし)
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
		

		public String getSubclass_cd_from() {
			return subclass_cd_from;
		}

		public void setSubclass_cd_from(String subclass_cd_from) {
			this.subclass_cd_from = subclass_cd_from;
		}

		public String getSubclass_cd_to() {
			return subclass_cd_to;
		}

		public void setSubclass_cd_to(String subclass_cd_to) {
			this.subclass_cd_to = subclass_cd_to;
		}

		public String getExistFlg() {
			return existFlg;
		}

		public void setExistFlg(String existFlg) {
			this.existFlg = existFlg;
		}

		public String getDelete_fg() {
			return delete_fg;
		}

		public void setDelete_fg(String delete_fg) {
			this.delete_fg = delete_fg;
		}

		public String getInsertTs() {
			return insertTs;
		}

		public void setInsertTs(String insertTs) {
			this.insertTs = insertTs;
		}

		public String getInsertUserId() {
			return insertUserId;
		}

		public void setInsertUserId(String insertUserId) {
			this.insertUserId = insertUserId;
		}

		public String getUpdateTs() {
			return updateTs;
		}

		public void setUpdateTs(String updateTs) {
			this.updateTs = updateTs;
		}

		public String getUpdateUserId() {
			return updateUserId;
		}

		public void setUpdateUserId(String updateUserId) {
			this.updateUserId = updateUserId;
		}

		public String getChangFlg() {
			return changFlg;
		}

		public void setChangFlg(String changFlg) {
			this.changFlg = changFlg;
		}

}
