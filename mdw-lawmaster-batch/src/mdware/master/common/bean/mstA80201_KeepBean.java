/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール・棚割表発行リクエストの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール・棚割表発行リクエストの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/08/11)初版作成
 */
package mdware.master.common.bean;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール・棚割表発行リクエストの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール・棚割表発行リクエストの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/08/11)初版作成
 */
public class mstA80201_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private String bumonCd = ""; // 部門コード
	private String bumonNm = ""; // 部門名称
	private String hinbanCdFrom = ""; // 品番コード
	private String hinbanNmFrom = ""; // 品番名称
	private String hinbanCdTo = ""; // 品番コード
	private String hinbanNmTo = ""; // 品番名称
	private String hinsyuCd = "";//品種コード	
	private String hinsyuNm = "";//品種名称
	private String lineCd = "";//ラインコード
	private String lineNm = "";//ライン名称
	private String unitCd = ""; // ユニットコード
	private String unitNm = ""; // ユニット名称
	private String tenpocd = ""; //店舗コード
	private String tenpoNm = ""; //店舗名称
	private String sendDT = "";//出力希望日
	private String byNo = "";//依頼BY No	
	private String tanadaiNb1From = "";//範囲1開始棚割番号
	private String tanadaiNb1To = "";//範囲1終了棚割番号
	private String tanadaiNb2From = "";//範囲2開始棚割番号
	private String tanadaiNb2To = "";//範囲2終了棚割番号
	private String tanadaiNb3From = "";//範囲3開始棚割番号
	private String tanadaiNb3To = "";//範囲3終了棚割番号
	private String trPsTanaRequest = "";//棚割外区分
	private String sofusakiKb = "";//送付
	private String kaiPageKb = "";//棚番改ページ
	private String request_kb = "";//リクエスト
	private String request_kb_tan = "";//リクエスト	
	private String tanaJyoken = "";//棚割表条件
	private String commentTx = "";//コメント
	private String insertflg 				= "";	//新規処理利用可能区分
	private String updateflg 				= "";	//更新処理利用可能区分
	private String deleteflg 				= "";	//削除処理利用可能区分
	private String referenceflg 			= "";	//照会処理利用可能区分
	private String processingDivision	= "0";				// 処理区分
	
	private String sysKb = ""; // システム区分
	private String changflg ="";
	
	private String insertUserId = null;
	private String insertTs = null;
	private String updateTs = null;	
	private String updateUserId = null;
	
	
	private String errorFlg = null; // エラーフラグ
	private String errorMessage = null; // エラーメッセージ
	private String firstFocus = null; // フォーカスを最初に取得するオブジェクト名
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）
	
	private Map MeisaiColor = new HashMap(); // コントロール背景色（ヘッダ）

//	↓↓2006.12.06 H.Yamamoto カスタマイズ修正↓↓
//	private static final String INIT_PAGE = "mstA80101_PsTanaHakkouRequestInit"; // 初期画面JSPを取得
//	private static final String INSERT_PAGE = "mstA80201_PsTanaHakkouRequestInsert";	// 登録画面JSPを取得
//	private static final String DELETE_PAGE = "mstA80301_PsTanaHakkouRequestDelete";	// 取消画面JSPを取得
//	private static final String VIEW_PAGE = "mstA80401_PsTanaHakkouRequestView";	// 照会画面JSPを取得
	private String entryKb =null; //依頼種別
	private String iraiNo = "";//依頼No.
	private String tanadaiNb = "";//棚割番号（個別）
	private boolean inputflg = false; 
	private static final String INIT_PAGE	= "mstA80501_PsTanaHakkouRequest";	// 初期画面JSPを取得
	private static final String INSERT_PAGE	= "mstA80501_PsTanaHakkouRequest";	// 登録画面JSPを取得
	private static final String DELETE_PAGE	= "mstA80501_PsTanaHakkouRequest";	// 取消画面JSPを取得
	private static final String VIEW_PAGE	= "mstA80501_PsTanaHakkouRequest";	// 照会画面JSPを取得
//	↑↑2006.12.06 H.Yamamoto カスタマイズ修正↑↑
	

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
	 * 品番コードFromに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinbanCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHinbanCdFrom() {
		return hinbanCdFrom;
	}

	/**
	 * 品番コードFromに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHinbanCdFrom(String hinbanCdFrom) {
		this.hinbanCdFrom = hinbanCdFrom;
		return true;
	}

	/**
	 * 品番名称Fromに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinbanNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHinbanNmFrom() {
		return hinbanNmFrom;
	}

	/**
	 * 品番名称Fromに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHinbanNmFrom(String hinbanNmFrom) {
		this.hinbanNmFrom = hinbanNmFrom;
		return true;
	}
	
	
	/**
	 * 品番コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinbanCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHinbanCdTo() {
		return hinbanCdTo;
	}

	/**
	 * 品番コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHinbanCdTo(String hinbanCdTo) {
		this.hinbanCdTo = hinbanCdTo;
		return true;
	}

	/**
	 * 品番名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinbanNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHinbanNmTo() {
		return hinbanNmTo;
	}

	/**
	 * 品番名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHinbanNmTo(String hinbanNmTo) {
		this.hinbanNmTo = hinbanNmTo;
		return true;
	}	
	

	/**
	 * ユニットコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUnitCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getUnitCd() {
		return unitCd;
	}

	/**
	 * ユニットコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUnitCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setUnitCd(String unitCd) {
		this.unitCd = unitCd;
		return true;
	}

	/**
	 * ユニット名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUnitNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getUnitNm() {
		return unitNm;
	}

	/**
	 * ユニット名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUnitNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setUnitNm(String unitNm) {
		this.unitNm = unitNm;
		return true;
	}
		
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
	 * getErrorflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getErrorFlg() {
		return this.errorFlg;
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
	 * 登録画面URL取得(ログ出力有り) <br>
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
	public String getInsertUrl(String logHeader, String logMsg) {
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

		return INSERT_PAGE;
	}

	/**
	 * 登録画面URL取得(ログ出力なし) <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * 
	 * @return String
	 */
	public String getInsertUrl() {
		return INSERT_PAGE;
	}
	
	/**
	 * 取消画面URL取得(ログ出力有り) <br>
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
	public String getDeleteUrl(String logHeader, String logMsg) {
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

		return DELETE_PAGE;
	}

	/**
	 * 取消画面URL取得(ログ出力なし) <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * 
	 * @return String
	 */
	public String getDeleteUrl() {
		return DELETE_PAGE;
	}

	/**
	 * 照会画面URL取得(ログ出力有り) <br>
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
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * 
	 * @return String
	 */
	public String getViewUrl() {
		return VIEW_PAGE;
	}

	public String getChangflg() {
		return changflg;
	}

	public void setChangflg(String changflg) {
		this.changflg = changflg;
	}	
	/**
	 * 数字を編集 <br>
	 *  
	 * @param String strNumber
	 * <br>
	 * 
	 * @return String ret
	 */
	public String addPoint(String strNumber) {
		String ret = strNumber;
		if (!"0".equals(strNumber)){			
			int len 		= strNumber.length();
			
			String numberPart  =  strNumber.substring(0, len-2);
			String decimalPart =  strNumber.substring(len-2, len);
			ret = numberPart + "." + decimalPart;
		}
		
		return ret;
	}

	public String getSysKb() {
		return sysKb;
	}

	public void setSysKb(String sysKb) {
		this.sysKb = sysKb;
	}

	public String getByNo() {
		return byNo;
	}

	public void setByNo(String byNo) {
		this.byNo = byNo;
	}

	public String getHinsyuCd() {
		return hinsyuCd;
	}

	public void setHinsyuCd(String hinsyuCd) {
		this.hinsyuCd = hinsyuCd;
	}

	public String getHinsyuNm() {
		return hinsyuNm;
	}

	public void setHinsyuNm(String hinsyuNm) {
		this.hinsyuNm = hinsyuNm;
	}

	public String getLineCd() {
		return lineCd;
	}

	public void setLineCd(String lineCd) {
		this.lineCd = lineCd;
	}

	public String getLineNm() {
		return lineNm;
	}

	public void setLineNm(String lineNm) {
		this.lineNm = lineNm;
	}

	public String getSendDT() {
		return sendDT;
	}

	public void setSendDT(String sendDT) {
		this.sendDT = sendDT;
	}

	public String getTanadaiNb1From() {
		return tanadaiNb1From;
	}

	public void setTanadaiNb1From(String tanadaiNb1From) {
		this.tanadaiNb1From = tanadaiNb1From;
	}

	public String getTanadaiNb1To() {
		return tanadaiNb1To;
	}

	public void setTanadaiNb1To(String tanadaiNb1To) {
		this.tanadaiNb1To = tanadaiNb1To;
	}

	public String getTanadaiNb2From() {
		return tanadaiNb2From;
	}

	public void setTanadaiNb2From(String tanadaiNb2From) {
		this.tanadaiNb2From = tanadaiNb2From;
	}

	public String getTanadaiNb2To() {
		return tanadaiNb2To;
	}

	public void setTanadaiNb2To(String tanadaiNb2To) {
		this.tanadaiNb2To = tanadaiNb2To;
	}

	public String getTanadaiNb3From() {
		return tanadaiNb3From;
	}

	public void setTanadaiNb3From(String tanadaiNb3From) {
		this.tanadaiNb3From = tanadaiNb3From;
	}

	public String getTanadaiNb3To() {
		return tanadaiNb3To;
	}

	public void setTanadaiNb3To(String tanadaiNb3To) {
		this.tanadaiNb3To = tanadaiNb3To;
	}

	public String getTenpocd() {
		return tenpocd;
	}

	public void setTenpocd(String tenpocd) {
		this.tenpocd = tenpocd;
	}

	public String getTenpoNm() {
		return tenpoNm;
	}

	public void setTenpoNm(String tenpoNm) {
		this.tenpoNm = tenpoNm;
	}

	public String getCommentTx() {
		return commentTx;
	}

	public void setCommentTx(String commentTx) {
		this.commentTx = commentTx;
	}

	public String getDeleteflg() {
		return deleteflg;
	}

	public void setDeleteflg(String deleteflg) {
		this.deleteflg = deleteflg;
	}

	public String getInsertflg() {
		return insertflg;
	}

	public void setInsertflg(String insertflg) {
		this.insertflg = insertflg;
	}

	public String getProcessingDivision() {
		return processingDivision;
	}

	public void setProcessingDivision(String processingDivision) {
		this.processingDivision = processingDivision;
	}

	public String getReferenceflg() {
		return referenceflg;
	}

	public void setReferenceflg(String referenceflg) {
		this.referenceflg = referenceflg;
	}

	public String getRequest_kb() {
		return request_kb;
	}

	public void setRequest_kb(String request_kb) {
		this.request_kb = request_kb;
	}

	public String getSofusakiKb() {
		return sofusakiKb;
	}

	public void setSofusakiKb(String sofusakiKb) {
		this.sofusakiKb = sofusakiKb;
	}

	public String getTanaJyoken() {
		return tanaJyoken;
	}

	public void setTanaJyoken(String tanaJyoken) {
		this.tanaJyoken = tanaJyoken;
	}

	public String getTrPsTanaRequest() {
		return trPsTanaRequest;
	}

	public void setTrPsTanaRequest(String trPsTanaRequest) {
		this.trPsTanaRequest = trPsTanaRequest;
	}

	public String getUpdateflg() {
		return updateflg;
	}

	public void setUpdateflg(String updateflg) {
		this.updateflg = updateflg;
	}

	public String getKaiPageKb() {
		return kaiPageKb;
	}

	public void setKaiPageKb(String kaiPageKb) {
		this.kaiPageKb = kaiPageKb;
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

	public Map getMeisaiColor() {
		return MeisaiColor;
	}

	public void setMeisaiColor(Map meisaiColor) {
		MeisaiColor = meisaiColor;
	}

	public String getRequest_kb_tan() {
		return request_kb_tan;
	}

	public void setRequest_kb_tan(String request_kb_tan) {
		this.request_kb_tan = request_kb_tan;
	}	

//	↓↓2006.12.06 H.Yamamoto カスタマイズ修正↓↓
	public String getEntryKb() {
		return entryKb;
	}

	public void setEntryKb(String entryKb) {
		this.entryKb = entryKb;
	}	

	public String getTanadaiNb() {
		return tanadaiNb;
	}

	public void setTanadaiNb(String tanadaiNb) {
		this.tanadaiNb = tanadaiNb;
	}

	public String getIraiNo() {
		return iraiNo;
	}

	public void setIraiNo(String iraiNo) {
		this.iraiNo = iraiNo;
	}

	public boolean getInputflg() {
		return inputflg;
	}

	public void setInputflg(boolean inputflg) {
		this.inputflg = inputflg;
	}	
//	↑↑2006.12.06 H.Yamamoto カスタマイズ修正↑↑
	
	
}
