/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）本部投入数量確認の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する本部投入数量確認の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/4)初版作成
 */
package mdware.master.common.bean;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>
 * タイトル: 新ＭＤシステム（マスター管理）本部投入数量確認の画面表示データ格納用クラス
 * </p>
 * <p>
 * 説明: 新ＭＤシステムで使用する本部投入数量確認の画面表示データ格納用クラス
 * </p>
 * <p>
 * 著作権: Copyright (c) 2006
 * </p>
 * <p>
 * 会社名: Vinculum Japan Corp.
 * </p>
 * 
 * @author yaoyujun
 * @version 1.0(2006/07/4)初版作成
 */
public class mstA60201_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private String bumonCd = ""; // 部門コード
	private String bumonNm = ""; // 部門名称
	private String hinbanCd = ""; // 品番コード
	private String hinbanNm = ""; // 品番名称
	private String hinsyuCd = "";//品種コード
	
	private String hinsyuNm = "";//品種名称
	private String lineCd = "";//ラインコード
	private String lineNm = "";//ライン名称
	private String tanadaiNbFrom = "";//範囲開始棚割番号
	private String tanadaiNbTo = "";//範囲終了棚割番号
	private String hachuStDt = "";//発注開始日
	private String hachuEnDt = "";//発注終了日
	private String nohinStDt = "";//納品開始日
	private String nohinEnDt = "";//納品終了日
	private String gyotaiKb = "";//業態区分
	private String hyouji = "";//表示単位	
	private String unitCd = ""; // ユニットコード
	private String unitNm = ""; // ユニット名称
	private boolean inputflg = false; 
	private String sysKb = ""; // システム区分
	//===BEGIN=== Add by kou 2006/11/13
	private String sort = "";			//ソート順
	private String tenpoCd = "";		//店舗コード
	private String tenpoNm = "";		//店舗名称
	private String tenGroupNo = "";	//店グループNo
	private String tenGroupNm = "";	//店グループ名称
	//===END=== Add by kou 2006/11/13
	
	private String errorFlg = null; // エラーフラグ
	private String errorMessage = null; // エラーメッセージ
	private String firstFocus = null; // フォーカスを最初に取得するオブジェクト名
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）

	private static final String INIT_PAGE = "mstA60101_HonbuTonyuInit"; // 初期画面JSPを取得
	private static final String VIEW_PAGE = "mstA60201_HonbuTonyuView";	// 照会画面JSPを取得

	

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
	 * 品番コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinbanCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHinbanCd() {
		return hinbanCd;
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
	public boolean setHinbanCd(String hinbanCd) {
		this.hinbanCd = hinbanCd;
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
	public String getHinbanNm() {
		return hinbanNm;
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
	public boolean setHinbanNm(String hinbanNm) {
		this.hinbanNm = hinbanNm;
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
	
	public String getGyotaiKb() {
		return gyotaiKb;
	}

	public void setGyotaiKb(String gyotaiKb) {
		this.gyotaiKb = gyotaiKb;
	}

	public String getHachuEnDt() {
		return hachuEnDt;
	}

	public void setHachuEnDt(String hachuEnDt) {
		this.hachuEnDt = hachuEnDt;
	}

	public String getHachuStDt() {
		return hachuStDt;
	}

	public void setHachuStDt(String hachuStDt) {
		this.hachuStDt = hachuStDt;
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

	public String getHyouji() {
		return hyouji;
	}

	public void setHyouji(String hyouji) {
		this.hyouji = hyouji;
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

	public String getNohinEnDt() {
		return nohinEnDt;
	}

	public void setNohinEnDt(String nohinEnDt) {
		this.nohinEnDt = nohinEnDt;
	}

	public String getNohinStDt() {
		return nohinStDt;
	}

	public void setNohinStDt(String nohinStDt) {
		this.nohinStDt = nohinStDt;
	}

	public String getTanadaiNbFrom() {
		return tanadaiNbFrom;
	}

	public void setTanadaiNbFrom(String tanadaiNbFrom) {
		this.tanadaiNbFrom = tanadaiNbFrom;
	}

	public String getTanadaiNbTo() {
		return tanadaiNbTo;
	}

	public void setTanadaiNbTo(String tanadaiNbTo) {
		this.tanadaiNbTo = tanadaiNbTo;
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

	public boolean getInputflg() {
		return inputflg;
	}

	public void setInputflg(boolean inputflg) {
		this.inputflg = inputflg;
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
	
	
	/**
	 * @return
	 */
	public String getSort()
	{
		return sort;
	}

	/**
	 * @param string
	 */
	public void setSort(String string)
	{
		sort = string;
	}

	/**
	 * @return
	 */
	public String getTenGroupNo()
	{
		return tenGroupNo;
	}

	/**
	 * @return
	 */
	public String getTenpoCd()
	{
		return tenpoCd;
	}

	/**
	 * @param string
	 */
	public void setTenGroupNo(String string)
	{
		tenGroupNo = string;
	}

	/**
	 * @param string
	 */
	public void setTenpoCd(String string)
	{
		tenpoCd = string;
	}

	/**
	 * @return
	 */
	public String getTenGroupNm()
	{
		return tenGroupNm;
	}

	/**
	 * @return
	 */
	public String getTenpoNm()
	{
		return tenpoNm;
	}

	/**
	 * @param string
	 */
	public void setTenGroupNm(String string)
	{
		tenGroupNm = string;
	}

	/**
	 * @param string
	 */
	public void setTenpoNm(String string)
	{
		tenpoNm = string;
	}

}
