/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品階層マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius 
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品階層マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius H.Futagami
 * @version 1.0(2005/03/24)初版作成
 */
public class mst420201_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private String yuko_dt = null; //有効日
//	  ↓↓2006.06.23  shiyue カスタマイズ追加↓↓	
	private String system_kb = null;	//システム区分
//	  ↑↑2006.06.23  shiyue カスタマイズ追加↑↑	
	private String kaisou_pattern_kb = null; //階層パターン
	private String code1_cd = null; //コード１
	private String code1_rn = null; //コード１名称
	private String code2_cd = null; //コード２
	private String insert_ts = null; //作成年月日
	private String insert_user_id = null; //作成者社員ID
	private String update_ts = null; //更新年月日
	private String update_user_id = null; //更新者社員ID
	private String delete_fg = null; //削除フラグ
	private String processingDivision = null; //処理状況
	private String errorFlg = null; //エラーフラグ
	private String errorMessage = null; //エラーメッセージ
	private String[] menuBar = null; //メニューバーアイテム
	private Map mode = new HashMap(); //処理モード
	private String firstFocus = null; //フォーカスを最初に取得するオブジェクト名
	private String insertFlg = null; //新規処理利用可能区分
	private String updateFlg = null; //更新処理利用可能区分
	private String deleteFlg = null; //削除処理利用可能区分
	private String referenceFlg = null; //照会処理利用可能区分
	private String csvFlg = null; //CSV処理利用可能区分
	private String printFlg = null; //印刷処理利用可能区分
	private String before_disp_id = null; //前画面ID
	private String disp_url = null; //現画面URL
	private String checkFlg = null; //チェック処理判断
	private String existFlg = null; //データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searchErrorFlg = null; //エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateProcessFlg = null; //更新処理内容
	private boolean dataExists = false;
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）
	private String lowHeadName = null; //下位コード列名
	private String highHeadName = null; //上位コード列名
	private String entryHeadName = null; //入力コード列名
	private boolean codeErr = false;
	private boolean flgBatting = false;
//	BUGNO-S005 2005.04.22 T.kikuchi START	
	private String ChangeFlg 		     = "";				//データ変更
//	BUGNO-S005 2005.04.22 T.kikuchi END	

//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst420101_SyohinKaisoInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst420201_SyohinKaisoEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst420301_SyohinKaisoView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE		= "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END
	
	/**
	 * 有効日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setYukoDt("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setYukoDt(String yuko_dt) {
		this.yuko_dt = yuko_dt;
		return true;
	}
	/**
	 * 有効日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getYukoDt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getYukoDt() {
		return this.yuko_dt;
	}
//	  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓	
	/**
	 * システム区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSystemKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSystemKb(String system_kb) {
		this.system_kb = system_kb;
		return true;
	}
	/**
	 * システム区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSystemKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSystemKb() {
		return this.system_kb;
	}
//	  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
	/**
	 * 階層パターンに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKaisouPatternKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setKaisouPatternKb(String kaisou_pattern_kb) {
		this.kaisou_pattern_kb = kaisou_pattern_kb;
		return true;
	}
	/**
	 * 階層パターンに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKaisouPatternKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getKaisouPatternKb() {
		return this.kaisou_pattern_kb;
	}

	/**
	 * コード１に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCode1Cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCode1Cd(String code1_cd) {
		//this.code1_cd = mst000401_LogicBean.formatZero(code1_cd,4);
		this.code1_cd = code1_cd;
		return true;
	}
	/**
	 * コード１に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCode1Cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCode1Cd() {
		return this.code1_cd;
	}

	/**
	 * コード２に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCode2Cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCode2Cd(String code2_cd) {
		this.code2_cd = code2_cd;
		//this.code2_cd = mst000401_LogicBean.formatZero(code2_cd,4);
		return true;
	}
	/**
	 * コード２に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCode2Cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCode2Cd() {
		return this.code2_cd;
	}

	/**
	 * 作成年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertTs("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setInsertTs(String insert_ts) {
		this.insert_ts = insert_ts;
		return true;
	}
	/**
	 * 作成年月日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsertTs();　戻り値　文字列<br>
	 * <br>
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
	 * getInsertUserId();　戻り値　文字列<br>
	 * <br>
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
	 * getUpdateTs();　戻り値　文字列<br>
	 * <br>
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
	 * getUpdateUserId();　戻り値　文字列<br>
	 * <br>
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
	 * getDeleteFg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDeleteFg() {
		return this.delete_fg;
	}

	/**
	 * 処理状況に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setProcessingDivision("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setProcessingDivision(String processingDivision) {
		this.processingDivision = processingDivision;
		return true;
	}
	/**
	 * 処理状況に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getProcessingDivision();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getProcessingDivision() {
		return this.processingDivision;
	}

	/**
	 * エラーフラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setErrorFlg(String errorFlg) {
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
	public String getErrorFlg() {
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
	public boolean setErrorMessage(String errorMessage) {
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
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * メニューバーアイテムに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMenuBar("String[]");<br>
	 * <br>
	 * @param String[] 設定する文字配列
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
	 * メニューバーアイテムに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMenuBar();　戻り値　文字配列<br>
	 * <br>
	 * @return String[] 文字配列
	 */
	public String[] getMenuBar() {
		return this.menuBar;
	}

	/**
	 * 処理モードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMode("Map");<br>
	 * <br>
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
	 * getMode();　戻り値　Map配列<br>
	 * <br>
	 * @return String[] Map配列
	 */
	public Map getMode() {
		return this.mode;
	}

	/**
	 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setFirstFocus("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setFirstFocus(String firstFocus) {
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
	public String getFirstFocus() {
		return this.firstFocus;
	}

	/**
	 * 新規処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setInsertFlg(String insertFlg) {
		this.insertFlg = insertFlg;
		return true;
	}
	/**
	 * 新規処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsertFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getInsertFlg() {
		return this.insertFlg;
	}

	/**
	 * 更新処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateFlg(String updateFlg) {
		this.updateFlg = updateFlg;
		return true;
	}
	/**
	 * 更新処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdateFlg() {
		return this.updateFlg;
	}

	/**
	 * 削除処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDeleteFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
		return true;
	}
	/**
	 * 削除処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getDeleteFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDeleteFlg() {
		return this.deleteFlg;
	}

	/**
	 * 照会処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setReferenceFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setReferenceFlg(String referenceFlg) {
		this.referenceFlg = referenceFlg;
		return true;
	}
	/**
	 * 照会処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getReferenceFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getReferenceFlg() {
		return this.referenceFlg;
	}

	/**
	 * CSV処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCsvFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCsvFlg(String csvFlg) {
		this.csvFlg = csvFlg;
		return true;
	}
	/**
	 * CSV処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCsvFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCsvFlg() {
		return this.csvFlg;
	}

	/**
	 * 印刷処理利用可能区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setPrintFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setPrintFlg(String printFlg) {
		this.printFlg = printFlg;
		return true;
	}
	/**
	 * 印刷処理利用可能区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getPrintFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getPrintFlg() {
		return this.printFlg;
	}

	/**
	 * 前画面IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBeforeDispId("文字列");<br>
	 * <br>
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
	 * getBeforeDispId();　戻り値　文字列<br>
	 * <br>
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
	 * getDispUrl();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDispUrl() {
		return this.disp_url;
	}

	/**
	 * チェック処理判断に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCheckFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCheckFlg(String checkFlg) {
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
	public String getCheckFlg() {
		return this.checkFlg;
	}

	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setExistFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setExistFlg(String existFlg) {
		this.existFlg = existFlg;
		return true;
	}
	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getExistFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getExistFlg() {
		return this.existFlg;
	}

	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSearchErrorFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSearchErrorFlg(String searchErrorFlg) {
		this.searchErrorFlg = searchErrorFlg;
		return true;
	}
	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSearchErrorFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSearchErrorFlg() {
		return this.searchErrorFlg;
	}

	/**
	 * 更新処理内容に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateProcessFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateProcessFlg(String updateProcessFlg) {
		this.updateProcessFlg = updateProcessFlg;
		return true;
	}
	/**
	 * 更新処理内容に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateProcessFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdateProcessFlg() {
		return this.updateProcessFlg;
	}

	/**
	 * データの有無を判定<br>
	 * <br>
	 * Ex)<br>
	 * isDataExists;　戻り値　データ有無<br>
	 * <br>
	 * @return データ有無
	 */
	public boolean isDataExists() {
		return dataExists;
	}

	/**
	 * データ有無に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDataExists(b);<br>
	 * <br>
	 * @param データ有無
	 */
	public void setDataExists(boolean b) {
		dataExists = b;
	}

	/**
	 * コード１名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCode1Rn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCode1Rn() {
		return code1_rn;
	}

	/**
	 * コード１名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCode1Rn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setCode1Rn(String string) {
		code1_rn = string;
	}

	/**
	 * コントロールカラーに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor();　戻り値　コントロールカラー<br>
	 * <br>
	 * @return Map コントロールカラー
	 */
	public Map getCtrlColor() {
		return CtrlColor;
	}

	/**
	 * コントロールカラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor(Map)<br>
	 * <br>
	 * @param map 設定するコントロールカラー
	 */
	public void setCtrlColor(Map map) {
		CtrlColor = map;
	}

	/**
	 * 入力用コード列名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getEntryHeadName();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getEntryHeadName() {
		return entryHeadName;
	}

	/**
	 * 上位コード列名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHighHeadName();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHighHeadName() {
		return highHeadName;
	}

	/**
	 * 下位コード列名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHighHeadName();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getLowHeadName() {
		return lowHeadName;
	}

	/**
	 * 入力用コード列名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setEntryHeadName("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setEntryHeadName(String string) {
		entryHeadName = string;
	}

	/**
	 * 上位コード列名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHighHeadName("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHighHeadName(String string) {
		highHeadName = string;
	}

	/**
	 * 下位コード列名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLowHeadName("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setLowHeadName(String string) {
		lowHeadName = string;
	}

	/**
	 * 入力用コードエラーかどうかを判断<br>
	 * <br>
	 * Ex)<br>
	 * isCodeErr();　戻り値　boolean<br>
	 * <br>
	 * @return boolean true:明細コードエラー有り/false:明細コードエラー無し
	 */
	public boolean isCodeErr() {
		return codeErr;
	}

	/**
	 * 入力用コードエラー有無に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCodeErr(boolean);<br>
	 * <br>
	 * @param boolean 入力用コードエラー有無 
	 */
	public void setCodeErr(boolean b) {
		codeErr = b;
	}

	/**
	 * バッティングエラーかどうかを判断<br>
	 * <br>
	 * Ex)<br>
	 * isFlgBatting();　戻り値　boolean<br>
	 * <br>
	 * @return boolean true:バッティングエラー有り/false:バッティングエラー無し
	 */
	public boolean isFlgBatting() {
		return flgBatting;
	}

	/**
	 * バッティングエラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setFlgBatting(boolean);<br>
	 * <br>
	 * @param boolean バッティングエラー有無 
	 */
	public void setFlgBatting(boolean b) {
		flgBatting = b;
	}
//	BUGNO-S005 2005.04.21 T.kikuchi START
	//CenterPattern2Kbに対するセッターとゲッターの集合
	public boolean  setChangeFlg(String ChangeFlg)
	{
		this.ChangeFlg = ChangeFlg;
		return true;
	}
	public String getChangeFlg()
	{
		return this.ChangeFlg;
	}	
//	BUGNO-S005 2005.04.21 T.kikuchi START

//BUGNO-S052 2005.05.14 SIRIUS START
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
//BUGNO-S052 2005.05.14 SIRIUS START

}
