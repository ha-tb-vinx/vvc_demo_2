package mdware.master.common.bean;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>
 * タイトル: 衣料グルーピングNOマスタの画面表示データ格納用クラス
 * </p>
 * <p>
 * 説明: 新ＭＤシステムで使用する衣料グルーピングNOマスタの画面表示データ格納用クラス
 * </p>
 * <p>
 * 著作権: Copyright (c) 2006
 * </p>
 * <p>
 * 会社名: Vinculum Japan Corp.
 * </p>
 * 
 * @author K.Tanigawa
 * @version 1.0(2006.10.02)初版作成
 */
public class mst970101_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private String bumon_cd            = null;	// 部門コード
	private String bumon_kanji_rn      = null;	// 部門名
	private String hinban_cd           = "";   //品番
	private String hinban_kanji_rn     = "";   //品番名
	private String hinsyu_cd           = "";   //品種コード
	private String hinsyu_kanji_rn     = "";   //品種名
	private String groupno_cd          = null;	// グルーピングNO
	private String name_na             = null;	// 名称
	private String insert_ts           = null;	// 作成年月日
	private String insert_user_rn      = null;	// 作成者社員ID名前
	private String update_user_rn      = null;	// 更新者社員ID名前
	private String insert_user_id      = null;	// 作成者社員ID
	private String update_ts           = null;	// 更新年月日
	private String update_user_id      = null;	// 更新者社員ID
	private String delete_fg           = null;	// 削除フラグ
	private String processingdivision  = null;	// 処理状況
	private String errorflg            = null;	// エラーフラグ
	private String errormessage        = null;	// エラーメッセージ
	private String[] menubar           = null;	// メニューバーアイテム
	private Map mode                   = new HashMap(); // 処理モード
	private String firstfocus          = null;	// フォーカスを最初に取得するオブジェクト名
	private String insertflg           = null;	// 新規処理利用可能区分
	private String updateflg           = null;	// 更新処理利用可能区分
	private String deleteflg           = null;	// 削除処理利用可能区分
	private String referenceflg        = null;	// 照会処理利用可能区分
	private String csvflg              = null;	// CSV処理利用可能区分
	private String printflg            = null;	// 印刷処理利用可能区分
	private String before_disp_id      = null;	// 前画面ID
	private String disp_url            = null;	// 現画面URL
	private String checkflg            = null;	// チェック処理判断
	private String existflg            = null;	// データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searcherrorflg      = null;	// エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateprocessflg    = null;	// 更新処理内容
	private Map CtrlColor              = new HashMap(); // コントロール前景色
	private mst970101_RIryoGroupNoBean[] meisai = new mst970101_RIryoGroupNoBean[100];//mst580101_TenGroupNoBean明細配列
	private String changeflg           = null; // 項目変更フラグ
	private boolean shosaiFlg         = false;// 詳細リンク表示フラグ

	private static final String INIT_PAGE = "mst970101_IryoGroupNoInit"; // 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst970201_IryoGroupNoEdit"; // 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst970301_IryoGroupNoView"; // 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError"; // 権限エラーJSPを取得

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonCd"文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setBumonCd(String bumon_cd) {
		this.bumon_cd = bumon_cd;
		return true;
	}

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonCd"文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public String getBumonCd() {
		return ( this.bumon_cd == null) ? "" : this.bumon_cd.trim();
	}

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonKanjiRn"文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setBumonKanjiRn(String bumon_kanji_rn) {
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonKanjiRn"文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public String getBumonKanjiRn() {
		return bumon_kanji_rn;
	}

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGroupnoCd"文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setGroupnoCd(String groupno_cd) {
		this.groupno_cd = groupno_cd;
		return true;
	}

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * getGroupnoCd"文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public String getGroupnoCd() {
		return (this.groupno_cd == null) ? "" : this.groupno_cd.trim();
	}

	/**
	 * 名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setNameNa("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setNameNa(String name_na) {
		this.name_na = name_na;
		return true;
	}

	/**
	 * 名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getNameNa(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getNameNa() {
		return this.name_na;
	}

	/**
	 * 作成年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertTs("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setUpdateRn(String update_user_rn) {
		this.update_user_rn = update_user_rn;
		return true;
	}

	public String getUpdateRn() {
		return this.update_user_rn;
	}

	public boolean setInsertRn(String insert_user_rn) {
		this.insert_user_rn = insert_user_rn;
		return true;
	}

	public String getInsertRn() {
		return this.insert_user_rn;
	}

	public boolean setInsertTs(String insert_ts) {
		this.insert_ts = insert_ts;
		return true;
	}

	/**
	 * 作成年月日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsertTs(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getInsertTs() {
		return this.insert_ts;
	}

	/**
	 * 作成者社員IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertUserId("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setInsertUserId(String insert_user_id) {
		this.insert_user_id = insert_user_id;
		return true;
	}

	/**
	 * 作成者社員IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsertUserId(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getInsertUserId() {
		return this.insert_user_id;
	}

	/**
	 * 更新年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateTs("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setUpdateTs(String update_ts) {
		this.update_ts = update_ts;
		return true;
	}

	/**
	 * 更新年月日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateTs(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getUpdateTs() {
		return this.update_ts;
	}

	/**
	 * 更新者社員IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateUserId("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setUpdateUserId(String update_user_id) {
		this.update_user_id = update_user_id;
		return true;
	}

	/**
	 * 更新者社員IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateUserId(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getUpdateUserId() {
		return this.update_user_id;
	}

	/**
	 * 削除フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDeleteFg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setDeleteFg(String delete_fg) {
		this.delete_fg = delete_fg;
		return true;
	}

	/**
	 * 削除フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getDeleteFg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getDeleteFg() {
		return this.delete_fg;
	}

	/**
	 * 処理状況に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setProcessingdivision("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setProcessingdivision(String processingdivision) {
		this.processingdivision = processingdivision;
		return true;
	}

	/**
	 * 処理状況に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getProcessingdivision(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getProcessingdivision() {
		return this.processingdivision;
	}

	/**
	 * エラーフラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorFlg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setErrorFlg(String errorflg) {
		this.errorflg = errorflg;
		return true;
	}

	/**
	 * エラーフラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getErrorFlg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getErrorFlg() {
		return this.errorflg;
	}

	/**
	 * エラーメッセージに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorMessage("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setErrorMessage(String errormessage) {
		this.errormessage = errormessage;
		return true;
	}

	/**
	 * エラーメッセージに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getErrorMessage(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getErrorMessage() {
		return this.errormessage;
	}

	/**
	 * メニューバーアイテムに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMenuBar("String[]");<br>
	 * <br>
	 * 
	 * @param String[] 設定する文字配列
	 */
	public boolean setMenuBar(String[] menubar) {
		try {
			this.menubar = menubar;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * メニューバーアイテムに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMenubar(); 戻り値 文字配列<br>
	 * <br>
	 * 
	 * @return String[] 文字配列
	 */
	public String[] getMenuBar() {
		return this.menubar;
	}

	/**
	 * 処理モードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMode("Map");<br>
	 * <br>
	 * 
	 * @param Map 設定するMap配列
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
	 * 処理モードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMode(); 戻り値 Map配列<br>
	 * <br>
	 * 
	 * @return String[] Map配列
	 */
	public Map getMode() {
		return this.mode;
	}

	/**
	 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setFirstfocus("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setFirstFocus(String firstfocus) {
		this.firstfocus = firstfocus;
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
		return this.firstfocus;
	}

	/**
	 * 新規処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setInsertFlg(String insertflg) {
		this.insertflg = insertflg;
		return true;
	}

	/**
	 * 新規処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsertflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getInsertFlg() {
		return this.insertflg;
	}

	/**
	 * 更新処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setUpdateFlg(String updateflg) {
		this.updateflg = updateflg;
		return true;
	}

	/**
	 * 更新処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getUpdateFlg() {
		return this.updateflg;
	}

	/**
	 * 削除処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDeleteflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setDeleteFlg(String deleteflg) {
		this.deleteflg = deleteflg;
		return true;
	}

	/**
	 * 削除処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getDeleteflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getDeleteFlg() {
		return this.deleteflg;
	}

	/**
	 * 照会処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setReferenceflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setReferenceFlg(String referenceflg) {
		this.referenceflg = referenceflg;
		return true;
	}

	/**
	 * 照会処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getReferenceflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getReferenceFlg() {
		return this.referenceflg;
	}

	/**
	 * CSV処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCsvflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setCsvFlg(String csvflg) {
		this.csvflg = csvflg;
		return true;
	}

	/**
	 * CSV処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCsvflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getCsvFlg() {
		return this.csvflg;
	}

	/**
	 * 印刷処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setPrintflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setPrintFlg(String printflg) {
		this.printflg = printflg;
		return true;
	}

	/**
	 * 印刷処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getPrintflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getPrintFlg() {
		return this.printflg;
	}

	/**
	 * 前画面IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBeforeDispId("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setBeforeDispId(String before_disp_id) {
		this.before_disp_id = before_disp_id;
		return true;
	}

	/**
	 * 前画面IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBeforeDispId(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getBeforeDispId() {
		return this.before_disp_id;
	}

	/**
	 * 現画面URLに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDispUrl("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setDispUrl(String disp_url) {
		this.disp_url = disp_url;
		return true;
	}

	/**
	 * 現画面URLに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getDispUrl(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getDispUrl() {
		return this.disp_url;
	}

	/**
	 * チェック処理判断に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCheckflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setCheckflg(String checkflg) {
		this.checkflg = checkflg;
		return true;
	}

	/**
	 * チェック処理判断に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCheckflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getCheckflg() {
		return this.checkflg;
	}

	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setExistflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setExistflg(String existflg) {
		this.existflg = existflg;
		return true;
	}

	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getExistflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getExistflg() {
		return this.existflg;
	}

	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSearcherrorflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setSearcherrorflg(String searcherrorflg) {
		this.searcherrorflg = searcherrorflg;
		return true;
	}

	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSearcherrorflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSearcherrorflg() {
		return this.searcherrorflg;
	}

	/**
	 * 更新処理内容に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateprocessflg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setUpdateprocessflg(String updateprocessflg) {
		this.updateprocessflg = updateprocessflg;
		return true;
	}

	/**
	 * 更新処理内容に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateprocessflg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getUpdateprocessflg() {
		return this.updateprocessflg;
	}

	// CtrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map CtrlColor) {
		this.CtrlColor = CtrlColor;
		return true;
	}

	public Map getCtrlColor() {
		return this.CtrlColor;
	}

	/**
	 * 明細リストに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMeisai(); 戻り値 List<br>
	 * <br>
	 * 
	 * @return List
	 */
	public mst970101_RIryoGroupNoBean[] getMeisai() {
		return meisai;
	}

	/**
	 * 明細リストに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMeisai(new ArrayList()); 戻り値 なし<br>
	 * <br>
	 * 
	 * @param List 明細リスト
	 */
	public void setMeisai(mst970101_RIryoGroupNoBean[] beans) {
		meisai = beans;
	}

	/**
	 * 項目変更フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setChangeFlg("文字列");<br>
	 * <br>
	 * 
	 * @param String 設定する文字列
	 */
	public boolean setChangeFlg(String changeflg) {
		this.changeflg = changeflg;
		return true;
	}

	/**
	 * 項目変更フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getChangeFlg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getChangeFlg() {
		return this.changeflg;
	}

	/**
	 * 詳細表示用フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getShosaiFlg(); 戻り値 List<br>
	 * <br>
	 * 
	 * @return boolean
	 */
	public boolean getShosaiFlg() {
		return shosaiFlg;
	}

	/**
	 * 詳細表示用フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setShosaiFlg(List);<br>
	 * <br>
	 * 
	 * @param boolean
	 */
	public void setShosaiFlg(boolean shosaiFlg) {
		this.shosaiFlg = shosaiFlg;
	}

	/**
	 * hinsyu_cdに対するゲッター・セッター<br>
	 * <br>
	 * 
	 * @param boolean
	 */
	public boolean setHinsyuCd(String hinsyu_cd) {
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}

	public String getHinsyuCd() {
		return (this.hinsyu_cd == null) ? "" : this.hinsyu_cd.trim();
	}

	public boolean setHinsyuKanjiRn(String hinsyu_kanji_rn) {
		this.hinsyu_kanji_rn = hinsyu_kanji_rn;
		return true;
	}

	public String getHinsyuKanjiRn() {
		return this.hinsyu_kanji_rn;
	}

	/**
	 * hinban_cdに対するゲッター・セッター<br>
	 * <br>
	 * 
	 * @param boolean
	 */
	public boolean setHinbanCd(String hinban_cd) {
		this.hinban_cd = hinban_cd;
		return true;
	}

	public String getHinbanCd() {
		return ( this.hinban_cd == null) ? "" : this.hinban_cd.trim();
	}

	public boolean setHinbanKanjiRn(String hinban_kanji_rn) {
		this.hinban_kanji_rn = hinban_kanji_rn;
		return true;
	}

	public String getHinbanKanjiRn() {
		return this.hinban_kanji_rn;
	}

	/**
	 * 初期画面URL取得(ログ出力有り) <br>
	 * Ex)<br>
	 * getInitUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * 
	 * @param String logHeader
	 * @param String logMsg
	 * @return String
	 */
	public String getInitUrl(String logHeader, String logMsg) {
		// 画面メッセージと同様のログを出力
		if (this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
				|| this.errorflg.equals("")) {
			// 通常系
			if (!this.errormessage.equals("")) {
				stcLog.getLog().info(logHeader + this.errormessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errormessage);
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
	 * getEditUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * 
	 * @param String logHeader
	 * @param String logMsg
	 * @return String
	 */
	public String getEditUrl(String logHeader, String logMsg) {
		// 画面メッセージと同様のログを出力
		if (this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
				|| this.errorflg.equals("")) {
			// 通常系
			if (!this.errormessage.equals("")) {
				stcLog.getLog().info(logHeader + this.errormessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errormessage);
		}

		// 処理終了ログ
		if (!logMsg.equals("")) {
			stcLog.getLog().info(logHeader + logMsg);
		}

		return EDIT_PAGE;
	}

	/**
	 * 登録画面URL取得(ログ出力なし) <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * 
	 * @return String
	 */
	public String getEditUrl() {
		return EDIT_PAGE;
	}

	/**
	 * 照会画面URL取得(ログ出力有り) <br>
	 * Ex)<br>
	 * getViewUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * 
	 * @param String logHeader
	 * @param String logMsg
	 * @return String
	 */
	public String getViewUrl(String logHeader, String logMsg) {
		// 画面メッセージと同様のログを出力
		if (this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
				|| this.errorflg.equals("")) {
			// 通常系
			if (!this.errormessage.equals("")) {
				stcLog.getLog().info(logHeader + this.errormessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errormessage);
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
	 * 権限エラー画面URL取得(ログ出力有り) <br>
	 * Ex)<br>
	 * getKengenErr("logHeader") -&gt; String<br>
	 * <br>
	 * 
	 * @param String logHeader
	 * @return String
	 */
	public String getKengenErr(String logHeader) {
		stcLog.getLog().error(
				logHeader + InfoStrings.getInstance().getInfo("49999"));
		return KENGEN_PAGE;
	}

}
