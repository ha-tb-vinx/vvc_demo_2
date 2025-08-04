/**
 * <p>タイトル: 登録票BY承認の画面検索条件文表示データ格納用クラス</p>
 * <p>説明: 登録票BY承認の画面検索条件文表示データ格納用クラス</p>
 * <p> 著作権: Copyright (c) 2006 </p>
 * <p> 会社名: Vinculum Japan Corp.</p>
 * @author hujh
 * @version 1.0(2006/06/27)初版作成
 */
package mdware.master.common.bean;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/** 
 * <p>タイトル: 登録票BY承認の画面検索条件文表示データ格納用クラス</p>
 * <p>説明: 登録票BY承認の画面検索条件文表示データ格納用クラス</p>
 * <p> 著作権: Copyright (c) 2006 </p>
 * <p> 会社名: Vinculum Japan Corp.</p>
 * @author hujh
 * @version 1.0(2006/06/27)初版作成
 */
public class mstA70201_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

//	↓↓2006.08.04 H.Yamamoto カスタマイズ修正↓↓
	private String systemKb = ""; 			// システム区分
//	↑↑2006.08.04 H.Yamamoto カスタマイズ修正↑↑
//	↓↓2006.11.22 H.Yamamoto カスタマイズ修正↓↓
	private String systemKbS = ""; 		// システム区分（複数）
//	↑↑2006.11.22 H.Yamamoto カスタマイズ修正↑↑
	private String bumonCd = ""; 			// 部門コード
	private String bumonNm = ""; 		// 部門名称
	private String hinbanCd = ""; 			// 品番コード
	private String hinbanNm = "";		 	// 品番名称
	private String byNo = ""; 				// BY No.
	private String unitCd = ""; 				// ユニットコード
	private String unitNm = ""; 			// ユニット名称
	private String shiireCd = ""; 			// 仕入先コード
	private String shiireNm = ""; 			// 仕入先名称
	private String uploadDateFrom = ""; // アップロード日付（from）
	private String uploadDateTo = ""; 	// アップロード日付（to）
	private String jyotaiKb = ""; 			// 状態
	private String errorFlg = null; 			// エラーフラグ
	private String errorMessage = null; 	// エラーメッセージ
	private String firstFocus = null; 		// フォーカスを最初に取得するオブジェクト名
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）

	private static final String INIT_PAGE = "mstA70101_TorokuhyoBYSyoninInit"; // 初期画面JSPを取得
	private static final String EDIT_PAGE = "mstA70201_TorokuhyoBYSyoninEdit"; // 修正画面JSPを取得

//	↓↓2006.08.04 H.Yamamoto カスタマイズ修正↓↓
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
	 * システム区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSystemKb("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSystemKb(String systemKb) {
		this.systemKb = systemKb;
		return true;
	}
//	↑↑2006.08.04 H.Yamamoto カスタマイズ修正↑↑
//	↓↓2006.11.22 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * システム区分（複数）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSystemKbS(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSystemKbS() {
		return systemKbS;
	}

	/**
	 * システム区分（複数）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSystemKb("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSystemKbS(String systemKbS) {
		this.systemKbS = systemKbS;
		return true;
	}
//	↑↑2006.11.22 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * 状態に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getJyotaiKb(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getJyotaiKb() {
		return jyotaiKb;
	}

	/**
	 * 状態に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setJyotaiKb("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setJyotaiKb(String jyotaiKb) {
		this.jyotaiKb = jyotaiKb;
		return true;
	}

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
	 * BY　No.に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getByNo(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getByNo() {
		return byNo;
	}

	/**
	 * BY　No.に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setByNo("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setByNo(String byNo) {
		this.byNo = byNo;
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
	 * 仕入先コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getShiireCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getShiireCd() {
		return shiireCd;
	}

	/**
	 * 仕入先コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setShiireCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setShiireCd(String shiireCd) {
		this.shiireCd = shiireCd;
		return true;
	}

	/**
	 * 仕入先名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getShiireNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getShiireNm() {
		return shiireNm;
	}

	/**
	 * 仕入先名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setShiireNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setShiireNm(String shiireNm) {
		this.shiireNm = shiireNm;
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
	 * アップロード日付（from）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUploadDateFrom(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getUploadDateFrom() {
		return uploadDateFrom;
	}

	/**
	 * アップロード日付（from）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUploadDateFrom("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setUploadDateFrom(String uploadDateFrom) {
		this.uploadDateFrom = uploadDateFrom;
		return true;
	}

	/**
	 * アップロード日付（to）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUploadDateTo(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getUploadDateTo() {
		return uploadDateTo;
	}

	/**
	 * アップロード日付（to）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUploadDateTo("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setUploadDateTo(String uploadDateTo) {
		this.uploadDateTo = uploadDateTo;
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
