/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/15)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/15)初版作成
 */
public class mst570301_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private String kanri_kb = null; //管理区分
	private String kanri_cd = null; //管理コード
	private String kanri_kanji_rn = null; //管理名
//	BUGNO-006 2005.04.20 S.Murata START
	private String kanri_kanji_rn_mae = null; //管理名変換前
//	BUGNO-006 2005.04.20 S.Murata END
	private String sihai_kb = null; //仕配区分
	private String sihai_cd = null; //仕配コード
	private String sihai_kanji_rn = null; //仕入先名
//	BUGNO-006 2005.04.20 S.Murata START
	private String sihai_kanji_rn_mae = null; //仕入先名変換前
//	BUGNO-006 2005.04.20 S.Murata END
	private String syohin_cd = null; //商品コード
	private String syohin_kanji_rn = null; //商品名
//	BUGNO-006 2005.04.20 S.Murata START
	private String syohin_kanji_rn_mae = null; //商品名変換前
//	BUGNO-006 2005.04.20 S.Murata END
	private String groupno_cd = null; //グルーピングＮＯコード
	private String groupno_name_na = null; //グルーピング名
	private String tenpo_cd = null; //店舗コード
	private String yuko_dt = null; //有効日

//2005_05_20 新ER ver3.6 対応 *** 項目追加 start ***
	private String buturyu_kb = null;	//物流区分
//2005_05_20 新ER ver3.6 対応 *** 項目追加 end ***	

	private String nohin_center_cd = null; //納品センターコード
	private String keiyu_center_cd = null; //経由センターコード
	private String tenhai_center_cd = null; //店配センターコード
	private String center_nohin_read_time = null; //センター納品リードタイム
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
	private Map CtrlColor = new HashMap(); // コントロール前景色
	private ArrayList meisaiList = new ArrayList(); //明細行保持
	
	private String gyosyuid = null;//業種ID	

//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst570101_ButuryuKeiroInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst570201_ButuryuKeiroEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst570301_ButuryuKeiroView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END

	/**
	 * 管理区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setKanriKb(String kanri_kb) {
		this.kanri_kb = kanri_kb;
		return true;
	}
	/**
	 * 管理区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getKanriKb() {
		return this.kanri_kb;
	}

	/**
	 * 管理コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setKanriCd(String kanri_cd) {
		this.kanri_cd = kanri_cd;
		return true;
	}
	/**
	 * 管理コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getKanriCd() {
		return this.kanri_cd;
	}

	/**
	 * 仕配区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSihaiKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSihaiKb(String sihai_kb) {
		this.sihai_kb = sihai_kb;
		return true;
	}
	/**
	 * 仕配区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSihaiKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSihaiKb() {
		return this.sihai_kb;
	}

	/**
	 * 仕配コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSihaiCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSihaiCd(String sihai_cd) {
		this.sihai_cd = sihai_cd;
		return true;
	}
	/**
	 * 仕配コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSihaiCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSihaiCd() {
		return this.sihai_cd;
	}

	/**
	 * 商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyohinCd(String syohin_cd) {
		this.syohin_cd = syohin_cd;
		return true;
	}
	/**
	 * 商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohinCd() {
		return this.syohin_cd;
	}

	/**
	 * 店舗コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setTenpoCd(String tenpo_cd) {
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	/**
	 * 店舗コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTenpoCd() {
		return this.tenpo_cd;
	}

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
	
/****************************************************
 *       2005_05_20 新ER ver3.6 対応 項目追加
 ****************************************************/
	/**
	 * 物流区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setYukoDt("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setButuryuKb(String buturyu_kb) {
		this.buturyu_kb = buturyu_kb;
		return true;
	}
	/**
	 * 物流区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getYukoDt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getButuryuKb() {
		return this.buturyu_kb;
	}	
	
/***************************************************/	

	/**
	 * 納品センターコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setNohinCenterCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setNohinCenterCd(String nohin_center_cd) {
		this.nohin_center_cd = nohin_center_cd;
		return true;
	}
	/**
	 * 納品センターコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getNohinCenterCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getNohinCenterCd() {
		return this.nohin_center_cd;
	}

	/**
	 * 経由センターコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKeiyuCenterCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setKeiyuCenterCd(String keiyu_center_cd) {
		this.keiyu_center_cd = keiyu_center_cd;
		return true;
	}
	/**
	 * 経由センターコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKeiyuCenterCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getKeiyuCenterCd() {
		return this.keiyu_center_cd;
	}

	/**
	 * 店配センターコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenhaiCenterCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setTenhaiCenterCd(String tenhai_center_cd) {
		this.tenhai_center_cd = tenhai_center_cd;
		return true;
	}
	/**
	 * 店配センターコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenhaiCenterCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTenhaiCenterCd() {
		return this.tenhai_center_cd;
	}

	/**
	 * センター納品リードタイムに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCenterNohinReadTime("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCenterNohinReadTime(String center_nohin_read_time) {
		this.center_nohin_read_time = center_nohin_read_time;
		return true;
	}
	/**
	 * センター納品リードタイムに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCenterNohinReadTime();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCenterNohinReadTime() {
		return this.center_nohin_read_time;
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
	 * setErrorflg("文字列");<br>
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
	 * getErrorflg();　戻り値　文字列<br>
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
	 * seterrorMessage("文字列");<br>
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
	 * geterrorMessage();　戻り値　文字列<br>
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
	 * getMenubar();　戻り値　文字配列<br>
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
	 * setInsertflg("文字列");<br>
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
	 * setSearcherrorFlg("文字列");<br>
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
	 * getSearcherrorFlg();　戻り値　文字列<br>
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
	 * setUpdateprocessFlg("文字列");<br>
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
	 * getUpdateprocessFlg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdateProcessFlg() {
		return this.updateProcessFlg;
	}

	/**
	 * コントロールカラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor(Map);<br>
	 * <br>
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
	 * getCtrlColor();　戻り値　文字列<br>
	 * <br>
	 * @return コントロールカラーMap
	 */
	public Map getCtrlColor() {
		return this.CtrlColor;
	}

	/**
	 * 管理名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanri_kanji_rn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getKanriKanjiRn() {
		return kanri_kanji_rn;
	}

	/**
	 * 管理名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanri_kanji_rn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setKanriKanjiRn(String string) {
		kanri_kanji_rn = string;
	}

	/**
	 * 商品名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohin_kanji_rn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohinKanjiRrn() {
		return syohin_kanji_rn;
	}

	/**
	 * 商品名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohin_kanji_rn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSyohinKanjiRn(String string) {
		syohin_kanji_rn = string;
	}

	/**
	 * 明細一覧に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMeisaiList();　戻り値　文字列<br>
	 * <br>
	 * @return ArrayList 明細一覧リスト
	 */
	public ArrayList getMeisaiList() {
		return meisaiList;
	}

	/**
	 * 明細一覧に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMeisaiList("文字列");<br>
	 * <br>
	 * @param list 明細一覧のリスト
	 */
	public void setMeisaiList(ArrayList list) {
		meisaiList = list;
	}

	/**
	 * 仕入先名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSihai_kanji_rn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSihaiKanjiRn() {
		return sihai_kanji_rn;
	}

	/**
	 * 仕入先名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSihai_kanji_rn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSihaiKanjiRn(String string) {
		sihai_kanji_rn = string;
	}

	/**
	 * グルーピングＮＯコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGroupnp_cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGroupnoCd() {
		return groupno_cd;
	}

	/**
	 * グルーピング名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGroupnp_cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGroupnpNameNa() {
		return groupno_name_na;
	}

	/**
	 * グルーピングＮＯコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGroupnp_cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setGroupnoCd(String string) {
		groupno_cd = string;
	}

	/**
	 * グルーピング名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGroupnp_name_na("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setGroupnpNameNa(String string) {
		groupno_name_na = string;
	}

//	BUGNO-006 2005.04.20 S.Murata START
	/**
	 * 管理名変換前に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanri_kanji_rn_mae();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getKanri_kanji_rn_mae() {
		return kanri_kanji_rn_mae;
	}
	
	/**
	 * 仕入先名変換前に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSihai_kanji_rn_mae();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSihai_kanji_rn_mae() {
		return sihai_kanji_rn_mae;
	}
	
	/**
	 * 商品名変換前に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohin_kanji_rn_mae();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohin_kanji_rn_mae() {
		return syohin_kanji_rn_mae;
	}
	
	/**
	 * 管理名変換前に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanri_kanji_rn_mae("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setKanri_kanji_rn_mae(String string) {
		kanri_kanji_rn_mae = string;
	}
	
	/**
	 * 仕入先名変換前に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSihai_kanji_rn_mae("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSihai_kanji_rn_mae(String string) {
		sihai_kanji_rn_mae = string;
	}
	
	/**
	 * 商品名変換前に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohin_kanji_rn_mae("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSyohin_kanji_rn_mae(String string) {
		syohin_kanji_rn_mae = string;
	}
//	BUGNO-006 2005.04.20 S.Murata END

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

  /**
   * 業種取得用のゲッターとセッター
   */
  public void setGyosyuId(String str) {
	  gyosyuid = str;
		
  }
	
  public String getGyosyuId() {
	  return this.gyosyuid;
  }

}
