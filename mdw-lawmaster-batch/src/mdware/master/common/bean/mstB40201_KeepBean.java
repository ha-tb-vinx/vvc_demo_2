/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）値札タグ発行情報照会画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する値札タグ発行情報照会画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author H.Yamamoto
 * @version 1.0(2006/12/25)初版作成
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
 * @author H.Yamamoto
 * @version 1.0(2006/12/25)初版作成
 */
public class mstB40201_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private String systemKb = ""; // システム区分
	private String bumonCd = ""; // 部門コード
	private String bumonNm = ""; // 部門名称
	private String hinbanCd = ""; // 品番コード
	private String hinbanNm = ""; // 品番名称
	private String hinsyuCd = ""; //品種コード
	private String hinsyuNm = ""; //品種名称
	private String lineCd = ""; //ラインコード
	private String lineNm = ""; //ライン名称
	private String unitCd = ""; // ユニットコード
	private String unitNm = ""; // ユニット名称
	private String siiresakiCd = ""; // 仕入先コード
	private String siiresakiNm = ""; // 仕入先名称
	private String siiresakiSyohinCd = ""; // 仕入先商品コード
	private String sscdFlg = ""; //仕入先商品コードあいまい検索フラグ
	private String sinkiTorokuDt = ""; //新規登録日
	private String hachuStDt = ""; //発注開始日
	private String hachuEnDt = ""; //発注終了日
	private String nohinStDt = ""; //納品開始日
	private String nohinEnDt = ""; //納品終了日
	private String hanbaiStDt = ""; //販売開始日
	private String hanbaiEnDt = ""; //販売終了日
	private String syohinCd = ""; // 商品コード
	private String syohinNm = ""; // 商品名称
	private String byNo = ""; //バイヤーNo
	private boolean gyosyuFlg = false; 
	private boolean siiresakiFlg = false; 
	
	private int rowsInPage = 30; // ページ内行数
	private String errorFlg = null; // エラーフラグ
	private String errorMessage = null; // エラーメッセージ
	private String firstFocus = null; // フォーカスを最初に取得するオブジェクト名
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）

	private static final String INIT_PAGE = "mstB40101_NefudaTagHakkoInfo"; // 初期画面JSPを取得
	private static final String VIEW_PAGE = "mstB40101_NefudaTagHakkoInfo";	// 照会画面JSPを取得


	/**
	 * 業種フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGyosyuFlg(); 戻り値 true/false<br>
	 * <br>
	 * 
	 * @return boolean true/false
	 */
	public boolean getGyosyuFlg() {
		return gyosyuFlg;
	}

	/**
	 * 業種フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGyosyuFlg(true/false);<br>
	 * <br>
	 * 
	 * @param boolean
	 *            設定するtrue/false
	 */
	public boolean setGyosyuFlg(boolean gyosyuFlg) {
		this.gyosyuFlg = gyosyuFlg;
		return true;
	}

	/**
	 * 仕入先フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiFlg(); 戻り値 true/false<br>
	 * <br>
	 * 
	 * @return boolean true/false
	 */
	public boolean getSiiresakiFlg() {
		return siiresakiFlg;
	}

	/**
	 * 仕入先フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiFlg(true/false);<br>
	 * <br>
	 * 
	 * @param boolean
	 *            設定するtrue/false
	 */
	public boolean setSiiresakiFlg(boolean siiresakiFlg) {
		this.siiresakiFlg = siiresakiFlg;
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
	 * システム区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSystemKb(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public void setSystemKb(String systemKb) {
		this.systemKb = systemKb;
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
	 * 品種コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinsyuCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHinsyuCd() {
		return hinsyuCd;
	}

	/**
	 * 品種コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinsyuCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHinsyuCd(String hinsyuCd) {
		this.hinsyuCd = hinsyuCd;
		return true;
	}

	/**
	 * 品種名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinsyuNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHinsyuNm() {
		return hinsyuNm;
	}

	/**
	 * 品種名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinsyuNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHinsyuNm(String hinsyuNm) {
		this.hinsyuNm = hinsyuNm;
		return true;
	}	

	/**
	 * ラインコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLineCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getLineCd() {
		return lineCd;
	}

	/**
	 * ラインコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLineCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setLineCd(String lineCd) {
		this.lineCd = lineCd;
		return true;
	}

	/**
	 * ライン名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLineNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getLineNm() {
		return lineNm;
	}

	/**
	 * ライン名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLineNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setLineNm(String lineNm) {
		this.lineNm = lineNm;
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
	 * 仕入先コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSiiresakiCd() {
		return siiresakiCd;
	}

	/**
	 * 仕入先コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSiiresakiCd(String siiresakiCd) {
		this.siiresakiCd = siiresakiCd;
		return true;
	}

	/**
	 * 仕入先名称に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiNm(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSiiresakiNm() {
		return siiresakiNm;
	}

	/**
	 * 仕入先名称に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiNm("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSiiresakiNm(String siiresakiNm) {
		this.siiresakiNm = siiresakiNm;
		return true;
	}

	/**
	 * 仕入先商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiSyohinCd(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSiiresakiSyohinCd() {
		return siiresakiSyohinCd;
	}

	/**
	 * 仕入先商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiSyohinCd("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSiiresakiSyohinCd(String siiresakiSyohinCd) {
		this.siiresakiSyohinCd = siiresakiSyohinCd;
		return true;
	}

	/**
	 * 仕入先商品コードあいまい検索フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSscdFlg(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSscdFlg() {
		return sscdFlg;
	}

	/**
	 * 仕入先商品コードあいまい検索フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSscdFlg("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSscdFlg(String sscdFlg) {
		this.sscdFlg = sscdFlg;
		return true;
	}

	/**
	 * 新規登録日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSinkiTorokuDt(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getSinkiTorokuDt() {
		return sinkiTorokuDt;
	}

	/**
	 * 新規登録日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSinkiTorokuDt("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setSinkiTorokuDt(String sinkiTorokuDt) {
		this.sinkiTorokuDt = sinkiTorokuDt;
		return true;
	}

	/**
	 * 発注開始日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHachuStDt(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHachuStDt() {
		return hachuStDt;
	}

	/**
	 * 発注開始日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHachuStDt("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHachuStDt(String hachuStDt) {
		this.hachuStDt = hachuStDt;
		return true;
	}

	/**
	 * 発注終了日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHachuEnDt(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHachuEnDt() {
		return hachuEnDt;
	}

	/**
	 * 発注終了日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHachuEnDt("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHachuEnDt(String hachuEnDt) {
		this.hachuEnDt = hachuEnDt;
		return true;
	}

	/**
	 * 納品開始日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getNohinStDt(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getNohinStDt() {
		return nohinStDt;
	}

	/**
	 * 納品開始日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setNohinStDt("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setNohinStDt(String nohinStDt) {
		this.nohinStDt = nohinStDt;
		return true;
	}

	/**
	 * 納品終了日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getNohinEnDt(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getNohinEnDt() {
		return nohinEnDt;
	}

	/**
	 * 納品終了日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setNohinEnDt("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setNohinEnDt(String nohinEnDt) {
		this.nohinEnDt = nohinEnDt;
		return true;
	}

	/**
	 * 販売開始日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHanbaiStDt(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHanbaiStDt() {
		return hanbaiStDt;
	}

	/**
	 * 販売開始日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHanbaiStDt("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHanbaiStDt(String hanbaiStDt) {
		this.hanbaiStDt = hanbaiStDt;
		return true;
	}

	/**
	 * 販売終了日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHanbaiEnDt(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return String 文字列
	 */
	public String getHanbaiEnDt() {
		return hanbaiEnDt;
	}

	/**
	 * 販売終了日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHanbaiEnDt("文字列");<br>
	 * <br>
	 * 
	 * @param String
	 *            設定する文字列
	 */
	public boolean setHanbaiEnDt(String hanbaiEnDt) {
		this.hanbaiEnDt = hanbaiEnDt;
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
	 * バイヤーNoに対するゲッター<br>
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
	 * バイヤーNoに対するセッター<br>
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
	 * ページ内行数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getRowsInPage(); 戻り値 数値<br>
	 * <br>
	 * 
	 * @return int  数値
	 */
	public int getRowsInPage() {
		return this.rowsInPage;
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

}
