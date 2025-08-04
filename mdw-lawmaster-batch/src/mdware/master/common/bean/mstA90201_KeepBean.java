/**
 * <p>タイトル: プライスシール発行リクエスト(単品指定)の画面検索条件文表示データ格納用クラス</p>
 * <p>説明: プライスシール発行リクエスト(単品指定)の画面検索条件文表示データ格納用クラス</p>
 * <p> 著作権: Copyright (c) 2005-2006 </p>
 * <p> 会社名: Vinculum Japan Corp.</p>
 * @author xubq
 * @version 1.0(2006.08.18)初版作成
 */
package mdware.master.common.bean;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/** 
 * <p>タイトル: プライスシール発行リクエスト(単品指定)の画面検索条件文表示データ格納用クラス</p>
 * <p>説明: プライスシール発行リクエスト(単品指定)の画面検索条件文表示データ格納用クラス</p>
 * <p> 著作権: Copyright (c) 2005-2006 </p>
 * <p> 会社名: Vinculum Japan Corp.</p>
 * @author xubq
 * @version 1.0(2006.08.18)初版作成
 */
public class mstA90201_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private String bumonCd = ""; 			// 部門コード
	private String bumonNm = ""; 		// 部門名称
	private String syohinCd = ""; 			// 商品コード
	private String syohinNm = "";		 	// 商品名称
	private String sendDt = ""; 			// 出力希望日
	private String tenpoCd = ""; 			// 店舗コード
	private String tenpoNm = ""; 			// 店舗名称
	private String psSofusakiKb = "";	// PS送付先区分
	private String commentTx = ""; 		// コメント
	private String systemKb = ""; 		// システム区分
	private String insert_ts = null;		//作成年月日
	private String insert_user_id = null;	//作成者ID
	private String update_ts = null;		//更新年月日
	private String update_user_id = null;	//更新者ID
	private String errorFlg = null; 		// エラーフラグ
	private String checkFlg= "";			// チェック処理判断
	private String changeFlg= "";			// 破棄チェック処理判断
	private String buttonMode= "";			// ボタンモード
	private String errorMessage = null; // エラーメッセージ
	private String firstFocus = null; 		// フォーカスを最初に取得するオブジェクト名
	private String processingdivision = null;	//処理状況
	private String file_nm                 = "";		//文件名
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）
	private Map CtrlMeisaiColor = new HashMap(); // 明細部コントロール背景色（ヘッダ）
	private static final String INIT_PAGE = "mstA90101_PsTanpinHakkouRequestInit"; // 初期画面JSPを取得
	private static final String VIEW_PAGE = "mstA90301_PsTanpinHakkouRequestView"; // 修正画面JSPを取得
	private static final String EDIT_PAGE = "mstA90201_PsTanpinHakkouRequestEdit"; // 修正画面JSPを取得
//	↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
	private boolean inputflg = false; 
//	↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * 部門コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getBumonCd() {
		return bumonCd;
	}

	/**
	 *  部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setBumonCd(String bumonCd) {
		this.bumonCd = bumonCd;
		return true;
	}
	
	/**
	 * 部門名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getBumonNm() {
		return bumonNm;
	}

	/**
	 * 部門名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setBumonNm(String bumonNm) {
		this.bumonNm = bumonNm;
		return true;
	}
	
	/**
	 * 商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSyohinCd() {
		return syohinCd;
	}

	/**
	 *  商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSyohinCd(String syohinCd) {
		this.syohinCd = syohinCd;
		return true;
	}
	
	/**
	 * 商品名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSyohinNm() {
		return syohinNm;
	}

	/**
	 * 商品名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSyohinNm(String syohinNm) {
		this.syohinNm = syohinNm;
		return true;
	}
	
	/**
	 * 店舗コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getTenpoCd() {
		return tenpoCd;
	}

	/**
	 *  店舗コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setTenpoCd(String tenpoCd) {
		this.tenpoCd = tenpoCd;
		return true;
	}

	/**
	 * 店舗名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getTenpoNm() {
		return tenpoNm;
	}

	/**
	 * 店舗名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setTenpoNm(String tenpoNm) {
		this.tenpoNm = tenpoNm;
		return true;
	}
	
	/**
	 * PS送付先区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getPsSofusakiKb() {
		return psSofusakiKb;
	}

	/**
	 * PS送付先区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setPsSofusakiKb(String psSofusakiKb) {
		this.psSofusakiKb = psSofusakiKb;
		return true;
	}

	/**
	 * 出力希望日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSendDt() {
		return sendDt;
	}

	/**
	 * 出力希望日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSendDt(String sendDt) {
		this.sendDt = sendDt;
		return true;
	}
	
	/**
	 * コメントに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCommentTx(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getCommentTx() {
		return commentTx;
	}

	/**
	 * コメントに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCommentTx("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setCommentTx(String commentTx) {
		this.commentTx = commentTx;
		return true;
	}
	
	/**
	 * システム区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSystemKb(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSystemKb() {
		return systemKb;
	}

	/**
	 *システム区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCommentTx("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSystemKb(String systemKb) {
		this.systemKb = systemKb;
		return true;
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

//	↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
	public boolean getInputflg() {
		return inputflg;
	}

	public void setInputflg(boolean inputflg) {
		this.inputflg = inputflg;
	}	
//	↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
	
	/**
	 * エラーフラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorflg("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setErrorFlg(String errorFlg) {
		this.errorFlg = errorFlg;
		return true;
	}

	/**
	 * エラーフラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCheckFlg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getCheckFlg() {
		return this.checkFlg;
	}
	
	/**
	 * 変更フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setChangeFlg("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setChangeFlg(String changeFlg) {
		this.changeFlg = changeFlg;
		return true;
	}
	
	/**
	 * 変更フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getChangeFlg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getChangeFlg() {
		return this.changeFlg;
	}
	
	/**
	 * エラーフラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCheck("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setCheckFlg(String checkFlg) {
		this.checkFlg = checkFlg;
		return true;
	}
	
	
	/**
	 * ボタンモードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getButtonMode(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getButtonMode() {
		return this.buttonMode;
	}
	
	/**
	 * ボタンモードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setButtonMode("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setButtonMode(String buttonMode) {
		this.buttonMode = buttonMode;
		return true;
	}
	
	/**
	 * エラーフラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getErrorflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getErrorFlg() {
		return this.errorFlg;
	}
	
	/**
	 * 処理状況に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorflg("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setProcessingDivision(String processingdivision) {
		this.processingdivision = processingdivision;
		return true;
	}

	/**
	 * 処理状況に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getErrorflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getProcessingDivision() {
		return this.processingdivision;
	}

	public boolean setFileNm(String file_nm)
	{
		this.file_nm = file_nm;
		return true;
	}
	public String getFileNm()
	{
		return this.file_nm;
	}
	
	/**
	 * エラーメッセージに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * seterrorMessage("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return true;
	}

	/**
	 * エラーメッセージに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * geterrorMessage(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setFirstFocus("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setFirstFocus(String firstFocus) {
		this.firstFocus = firstFocus;
		return true;
	}

	/**
	 * フォーカスを最初に取得するオブジェクト名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getFirstFocus(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getFirstFocus() {
		return this.firstFocus;
	}

	/**
	 * コントロールカラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor(Map);<br>
	 * <br>
	 * 
	 * @param コントロールカラーMap
	 */
	public boolean setCtrlColor(Map CtrlColor) {
		this.CtrlColor = CtrlColor;
		return true;
	}

	/**
	 * コントロールカラーに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return コントロールカラーMap
	 */
	public Map getCtrlColor() {
		return this.CtrlColor;
	}

	/**
	 * 明細部コントロールカラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlMeisaiColor(Map);<br>
	 * <br>
	 * 
	 * @param コントロールカラーMap
	 */
	public boolean setCtrlMeisaiColor(Map CtrlMeisaiColor) {
		this.CtrlMeisaiColor = CtrlMeisaiColor;
		return true;
	}

	/**
	 * 明細部コントロールカラーに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlMeisaiColor(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return コントロールカラーMap
	 */
	public Map getCtrlMeisaiColor() {
		return this.CtrlMeisaiColor;
	}
	
	/**
	 * 初期画面URL取得(ログ出力有り) <br>
	 * Ex)<br>
	 * getInitUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * 
	 * @param String
	 *            logHeader
	 * @param String
	 *            logMsg
	 * @return String
	 */
	public String getInitUrl(String logHeader, String logMsg) {
		// 画面メッセージと同様のログを出力
		if (this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
				|| this.errorFlg.equals("")) {
			// 通常系
			if (!this.errorMessage.equals("")) {
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}

		// 処理終了ログ
		if (!logMsg.equals("")) {
			stcLog.getLog().info(logHeader + logMsg);
		}

		return INIT_PAGE;
	}

	/**
	 * 初期画面URL取得(ログ出力なし) <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * 
	 * @return String
	 */
	public String getInitUrl() {
		return INIT_PAGE;
	}

	/**
	 * 照会画面URL取得(ログ出力有り) <br>
	 * Ex)<br>
	 * getViewUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * 
	 * @param String
	 *            logHeader
	 * @param String
	 *            logMsg
	 * @return String
	 */
	public String getViewUrl(String logHeader, String logMsg) {
		// 画面メッセージと同様のログを出力
		if (this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
				|| this.errorFlg.equals("")) {
			// 通常系
			if (!this.errorMessage.equals("")) {
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}

		// 処理終了ログ
		if (!logMsg.equals("")) {
			stcLog.getLog().info(logHeader + logMsg);
		}

		return VIEW_PAGE;
	}

	/**
	 * 照会画面URL取得(ログ出力なし) <br>
	 * Ex)<br>
	 * getViewUrl() -&gt; String<br>
	 * <br>
	 * 
	 * @return String
	 */
	public String getViewUrl() {
		return VIEW_PAGE;
	}
	
	/**
	 * 更新画面URL取得(ログ出力有り) <br>
	 * Ex)<br>
	 * getEditUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * 
	 * @param String
	 *            logHeader
	 * @param String
	 *            logMsg
	 * @return String
	 */
	public String getEditUrl(String logHeader, String logMsg) {
		// 画面メッセージと同様のログを出力
		if (this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
				|| this.errorFlg.equals("")) {
			// 通常系
			if (!this.errorMessage.equals("")) {
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}

		// 処理終了ログ
		if (!logMsg.equals("")) {
			stcLog.getLog().info(logHeader + logMsg);
		}

		return EDIT_PAGE;
	}

	/**
	 * 更新画面URL取得(ログ出力なし) <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * 
	 * @return String
	 */
	public String getEditUrl() {
		return EDIT_PAGE;
	}
}
