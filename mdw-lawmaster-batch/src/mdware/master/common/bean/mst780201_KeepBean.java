/**
 * <p>タイトル	： 新ＭＤシステム（マスタ管理）店別品種(店舗)マスタの画面表示データ格納用クラス</p>
 * <p>説明: 	： 新ＭＤシステムで使用する店別品種(店舗)マスタの画面表示データ格納用クラス</p>
 * <p>著作権: 	： Copyright (c) 2005</p>
 * <p>会社名: 	： Vinculum Japan Corp.</p>
 * @author 	： VJC A.Tozaki
 * @version 	： 1.0(2005/11/02)初版作成
 *              ： 1.1(2006/01/19) D.Matsumoto CSV出力対応
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
// 2006/01/19 D.Matsumoto CSV出力対応追加
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
//2006/01/19 D.Matsumoto CSV出力対応追加


public class mst780201_KeepBean
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
			
	private String updateProcessFlg 		= "";				// 更新処理内容
	private String insert_ts 				= "";				// 作成年月日
	private String insert_user_id 			= "";				// 作成者社員ID
	private String insert_user_nm 			= "";				// 作成者社員名
	private String update_ts 				= "";				// 更新年月日
	private String update_user_id 			= "";				// 更新者社員ID
	private String update_user_nm 			= "";				// 更新者社員名
	
	private String hanku_cd 				= "";				// 販区コード
	private String hanku_na 				= "";				// 販区名
	private String tenpo_cd				= "";				// 店舗コード
	private String tenpo_na				= "";				// 店舗名称
	private String tanpin_qt				= "";				// 単品数
	private String mst_date				= "";				// マスタ管理日付
	private String mst_date_next			= "";				// マスタ管理日付＋１日

	// 2006/01/19 D.Matsumoto CSV追加対応
	private static final String INIT_PAGE = "mst780101_TenbetsuHinsyuTenpoInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst780201_TenbetsuHinsyuTenpoEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst780301_TenbetsuHinsyuTenpoView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";			// 権限エラーJSPを取得
	// 2006/01/19 D.Matsumoto CSV追加対応
	
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
	
	// hanku_cdに対するセッターとゲッターの集合
	public boolean setHankuCd(String hanku_cd)
	{
		this.hanku_cd = hanku_cd;
		return true;
	}
	public String getHankuCd()
	{
		return this.hanku_cd;
	}

	// hanku_naに対するセッターとゲッターの集合
	public boolean setHankuNa(String hanku_na)
	{
		this.hanku_na = hanku_na;
		return true;
	}
	public String getHankuNa()
	{
		return this.hanku_na;
	}
	
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

	// tenpo_naに対するセッターとゲッターの集合
	public boolean setTenpoNa(String tenpo_na)	
	{
		this.tenpo_na = tenpo_na;
		return true;
	}
	public String getTenpoNa()
	{
		return this.tenpo_na;
	}
	// tanpin_qtに対するセッターとゲッターの集合
	public boolean setTanpinQt(String tanpin_qt)	
	{
		this.tanpin_qt = tanpin_qt;
		return true;
	}
	public String getTanpinQt()
	{
		return this.tanpin_qt;
	}


	// mst_dateに対するセッターとゲッターの集合
	public boolean setMstDate(String mst_date)	
	{
		this.mst_date = mst_date;
		return true;
	}
	public String getMstDate()
	{
		return this.mst_date;
	}

	
	// mst_date_nextに対するセッターとゲッターの集合
	public boolean setMstDateNext(String mst_date_next)	
	{
		this.mst_date_next = mst_date_next;
		return true;
	}
	public String getMstDateNext()
	{
		return this.mst_date_next;
	}

// 2006/01/19 D.Matsumoto CSV出力追加開始
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
//	2006/01/19 D.Matsumoto CSV出力追加終了

}
