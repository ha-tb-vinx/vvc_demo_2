/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店舗仮登録照会の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店舗仮登録照会の画面表示データ格納用クラス</p>
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
 * タイトル: 新ＭＤシステム（マスター管理）店舗仮登録照会の画面表示データ格納用クラス
 * </p>
 * <p>
 * 説明: 新ＭＤシステムで使用する店舗仮登録照会の画面表示データ格納用クラス
 * </p>
 * <p>
 * 著作権: Copyright (c) 2006
 * </p>
 * <p>
 * 会社名: Vinculum Japan Corp.
 * </p>
 * 
 * @author yaoyujun
 * @version 1.0(2006/07/20)初版作成
 */
public class mstB10201_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	
//	↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
	private boolean gyosyu_sel_flg	= false;			//業種選択フラグ
//	↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
	private String bumonCd = ""; // 部門コード
	private String bumonNm = ""; // 部門名称
	private String hinbanCd = ""; // 品番コード
	private String hinbanNm = ""; // 品番名称
	private String unitCd = ""; // ユニットコード
	private String unitNm = ""; // ユニット名称
	private String syohinCd = "" ; // 商品コード
	private String hinmeiKanjiNa = ""; // 商品名称
	private String tenpoCd = ""; // 店舗コード
	private String tenpoNm = ""; // 店舗名称
	private boolean inputflg = false; 
	
	private String errorFlg = null; // エラーフラグ
	private String errorMessage = null; // エラーメッセージ
	private String firstFocus = null; // フォーカスを最初に取得するオブジェクト名
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）
	private String systemKb = null; //システム区分 

	private static final String INIT_PAGE = "mstB10101_TenpoKaTorokuSyokaiInit"; // 初期画面JSPを取得
	private static final String VIEW_PAGE = "mstB10201_TenpoKaTorokuSyokaiView";	// 照会画面JSPを取得

	
//	↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * 業種選択フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGyosyuSelFlg(); 戻り値 true/false<br>
	 * <br>
	 * 
	 * @return boolean true/false
	 */
	public boolean getGyosyuSelFlg() {
		return gyosyu_sel_flg;
	}

	/**
	 *  業種選択フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGyosyuSelFlg(true/false);<br>
	 * <br>
	 * 
	 * @param boolean
	 *            設定するtrue/false
	 */
	public void setGyosyuSelFlg(boolean gyosyu_sel_flg) {
		this.gyosyu_sel_flg = gyosyu_sel_flg;
	}	
//	↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑

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
		int len 		= strNumber.length();
		
		String numberPart  =  strNumber.substring(0, len-2);
		String decimalPart =  strNumber.substring(len-2, len);
		ret = numberPart + "." + decimalPart;
		return ret;
	}

	public String getHinmeiKanjiNa() {
		return hinmeiKanjiNa;
	}

	public void setHinmeiKanjiNa(String hinmeiKanjiNa) {
		this.hinmeiKanjiNa = hinmeiKanjiNa;
	}

	public String getSyohinCd() {
		return syohinCd;
	}

	public void setSyohinCd(String syohinCd) {
		this.syohinCd = syohinCd;
	}

	public String getTenpoCd() {
		return tenpoCd;
	}

	public void setTenpoCd(String tenpoCd) {
		this.tenpoCd = tenpoCd;
	}

	public String getTenpoNm() {
		return tenpoNm;
	}

	public void setTenpoNm(String tenpoNm) {
		this.tenpoNm = tenpoNm;
	}

	public String getSystemKb() {
		return systemKb;
	}

	public void setSystemKb(String systemKb) {
		this.systemKb = systemKb;
	}

	
}
