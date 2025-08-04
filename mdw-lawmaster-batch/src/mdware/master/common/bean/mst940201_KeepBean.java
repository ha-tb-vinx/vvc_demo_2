/**
 * <p>タイトル: mst940201_KeepBeanクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品体系検索の画面表示(検索条件)データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: mst940201_KeepBeanクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品体系検索の画面表示(検索条件)データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
public class mst940201_KeepBean {

	private static final StcLog stcLog = StcLog.getInstance();

	//遷移先URL
	private static final String INIT_PAGE = "mst940101_SyohinTaikeiInit";//初期画面JSP
	private static final String VIEW_PAGE = "mst940201_SyohinTaikeiView";//照会画面JSP
	private static final String KENGEN_PAGE = "mst000401_KengenError";//権限エラーJSP

	//画面項目
	private String kaisou_pattern_kb = null;//階層パターン
	private String code_cd = null;//コード
	private String code_nm = null;//コード名称
	private String yuko_dt = null;//検索対象日(有効日)

	//制御項目
	private String errorFlg = null;//エラーフラグ
	private String errorMessage = null;//エラーメッセージ
	private String[] menuBar = null;//メニューバーアイテム
	private Map mode = new HashMap();//処理モード
	private String firstFocus = null;//フォーカスを最初に取得するオブジェクト名
	private String referenceFlg = null;//照会処理利用可能区分
	private String csvFlg = null;//CSV処理利用可能区分
	private String checkFlg = null;//チェック処理判断
	private Map CtrlColor = new HashMap();//コントロール背景色（ヘッダ）
	private boolean codeErr = false;

	/******************************************************
	 *	画面出力項目(検索条件)
	 ******************************************************/

	/**
	 * 階層パターンを格納する<br>
	 * @param String 階層パターン
	 */
	public void setKaisouPatternKb(String kaisou_pattern_kb) {
		this.kaisou_pattern_kb = kaisou_pattern_kb;
	}

	/**
	 * 階層パターンを取得する<br>
	 * @return 階層パターン
	 */
	public String getKaisouPatternKb() {
		return this.kaisou_pattern_kb;
	}

	/**
	 * コードを格納する<br>
	 * @param String コード
	 */
	public void setCodeCd(String code_cd) {
		this.code_cd = code_cd;
	}

	/**
	 * コードを取得する<br>
	 * @return コード
	 */
	public String getCodeCd() {
		return this.code_cd;
	}

	/**
	 * コード名称を格納する<br>
	 * @param String コード名称
	 */
	public void setCodeNm(String code_nm) {
		this.code_nm = code_nm;
	}

	/**
	 * コード名称を取得する<br>
	 * @return コード名称
	 */
	public String getCodeNm() {
		return this.code_nm;
	}

	/**
	 * 検索対象日(有効日)を格納する<br>
	 * @param String 検索対象日(有効日)
	 */
	public void setYukoDt(String yuko_dt) {
		this.yuko_dt = yuko_dt;
	}

	/**
	 * 検索対象日(有効日)を取得する<br>
	 * @return 検索対象日(有効日)
	 */
	public String getYukoDt() {
		return this.yuko_dt;
	}

	/******************************************************
	 *	画面制御項目
	 ******************************************************/

	/**
	 * エラーフラグを格納する<br>
	 * @param String エラーフラグ
	 */
	public void setErrorFlg(String errorFlg) {
		this.errorFlg = errorFlg;
	}

	/**
	 * エラーフラグを取得する<br>
	 * @return エラーフラグ
	 */
	public String getErrorFlg() {
		return this.errorFlg;
	}

	/**
	 * エラーメッセージを格納する<br>
	 * @param String エラーメッセージ
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * エラーメッセージを取得する<br>
	 * @return エラーメッセージ
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * メニューバーアイテムを格納する<br>
	 * @param String[] メニューバーアイテム
	 */
	public boolean setMenuBar(String[] menuBar) {
		try {
			this.menuBar = menuBar;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * メニューバーアイテムを取得する<br>
	 * @return メニューバーアイテム
	 */
	public String[] getMenuBar() {
		return this.menuBar;
	}

	/**
	 * 処理モードを格納する<br>
	 * @param Map 処理モード
	 */
	public boolean setMode(Map mode) {
		try {
			this.mode = mode;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 処理モードを取得する<br>
	 * @return 処理モード
	 */
	public Map getMode() {
		return this.mode;
	}

	/**
	 * フォーカスを最初に取得するオブジェクト名を格納する<br>
	 * @param String フォーカスを最初に取得するオブジェクト名
	 */
	public void setFirstFocus(String firstFocus) {
		this.firstFocus = firstFocus;
	}

	/**
	 * フォーカスを最初に取得するオブジェクト名を取得する<br>
	 * @return フォーカスを最初に取得するオブジェクト名
	 */
	public String getFirstFocus() {
		return this.firstFocus;
	}

	/**
	 * 照会処理利用可能区分を格納する<br>
	 * @param String 照会処理利用可能区分
	 */
	public void setReferenceFlg(String referenceFlg) {
		this.referenceFlg = referenceFlg;
	}

	/**
	 * 照会処理利用可能区分を取得する<br>
	 * @return 照会処理利用可能区分
	 */
	public String getReferenceFlg() {
		return this.referenceFlg;
	}

	/**
	 * CSV処理利用可能区分を格納する<br>
	 * @param String CSV処理利用可能区分
	 */
	public void setCsvFlg(String csvFlg) {
		this.csvFlg = csvFlg;
	}

	/**
	 * CSV処理利用可能区分を取得する<br>
	 * @return CSV処理利用可能区分
	 */
	public String getCsvFlg() {
		return this.csvFlg;
	}

	/**
	 * チェック処理判断を格納する<br>
	 * @param String チェック処理判断フラグ
	 */
	public void setCheckFlg(String checkFlg) {
		this.checkFlg = checkFlg;
	}

	/**
	 * チェック処理判断を取得する<br>
	 * @return チェック処理判断フラグ
	 */
	public String getCheckFlg() {
		return this.checkFlg;
	}

	/**
	 * コントロールカラーを取得する<br>
	 * @return Map コントロールカラー
	 */
	public Map getCtrlColor() {
		return CtrlColor;
	}

	/**
	 * コントロールカラーを格納する<br>
	 * @param map コントロールカラー
	 */
	public void setCtrlColor(Map map) {
		CtrlColor = map;
	}

	/**
	 * 入力用コードエラーかどうかを判断<br>
	 * @return boolean true:明細コードエラー有り/false:明細コードエラー無し
	 */
	public boolean isCodeErr() {
		return codeErr;
	}

	/**
	 * 入力用コードエラー有無を格納する<br>
	 * @param boolean 入力用コードエラー有無 
	 */
	public void setCodeErr(boolean b) {
		codeErr = b;
	}

	/**
	 * 初期画面URLを取得する(ログ出力有り)
	 * @param String logHeader
	 * @param String logMsg
	 * @return 初期画面URL
	 */
	public String getInitUrl(String logHeader,String logMsg) {

		//画面メッセージと同様のログを出力
		if (this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL) || this.errorFlg.equals("")) {
			//通常系
			if (!this.errorMessage.equals("")) {
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		//処理終了ログ
		if (!logMsg.equals("")) {
			stcLog.getLog().info(logHeader + logMsg);
		}
		return	INIT_PAGE;
	}//getInitUrl

	/**
	 * 初期画面URLを取得する(ログ出力なし)
	 * @return 初期画面URL
	 */
	public String getInitUrl() {
		return	INIT_PAGE;
	}

	/**
	 * 照会画面URLを取得する(ログ出力有り)
	 * @param String logHeader
	 * @param String logMsg
	 * @return 照会画面URL
	 */
	public String getViewUrl(String logHeader,String logMsg) {

		//画面メッセージと同様のログを出力
		if (this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL) || this.errorFlg.equals("")) {
			//通常系
			if (!this.errorMessage.equals("")) {
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		//処理終了ログ
		if (!logMsg.equals("")) {
			stcLog.getLog().info(logHeader + logMsg);
		}
		return	VIEW_PAGE;
	}//getViewUrl

	/**
	 * 照会画面URLを取得する(ログ出力なし)
	 * @return 照会画面URL
	 */
	public String getViewUrl() {
		return	VIEW_PAGE;
	}

	/**
	 * 権限エラー画面URLを取得する(ログ出力有り)
	 * @param String logHeader
	 * @return 権限エラー画面URL
	 */
	public String getKengenErr(String logHeader) {
		stcLog.getLog().error(logHeader + InfoStrings.getInstance().getInfo("49999"));
		return KENGEN_PAGE;
	}
}