/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/22)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 S.Takahashi START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 S.Takahashi END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/22)初版作成
 */
public class mst240201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String syohin_cd = null; //商品コード（検索条件）
	private String syohinmei_kanji_na = null; //漢字品名
	private String siire_hinban_cd = null; //仕入先品番
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
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）
	private int sizeCnt = 0; //横軸数
	private int colorCnt = 0; //横軸数
	private int recCnt = 0;   //レコード数
	private mst240101_AsortmentBean[][] meisaiArgs = null;		//画面表示用
	private mst240101_AsortmentBean[][] beforeMeisaiArgs = null;	//初期表示データ
	private ArrayList sizeList = new ArrayList();		//サイズリスト	
	private ArrayList colorList = new ArrayList();	//カラーリスト
	private String[] selSize = null;		//サイズ選択値
	private String[] selColors = null;	//カラー選択値
	private String[] disableSize = null;		//サイズ選択可否
	private String[] disableColors = null;	//カラー選択可否
	private boolean dataExists = false;

//BUGNO-S070 2005.07.11 Sirius START
	private static final int sizeMax = 4;
	private ArrayList registSizeList = new ArrayList();		//登録済みサイズリスト	
//BUGNO-S070 2005.07.11 Sirius End

//	BUGNO-S052 2005.05.14 S.Takahashi START
	  private static final String INIT_PAGE   = "mst240101_AsortmentInit";	// 初期画面JSPを取得
	  private static final String EDIT_PAGE   = "mst240201_AsortmentEdit";	// 登録画面JSPを取得
	  private static final String VIEW_PAGE   = "mst240301_AsortmentView";	// 照会画面JSPを取得
	  private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//	BUGNO-S052 2005.05.14 S.Takahashi END
	
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
	 * 明細レコード配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * mst240101_AsortmentBean[][];　戻り値　２次元配列レコード配列<br>
	 * <br>
	 * @return 明細レコード配列
	 */
	public mst240101_AsortmentBean[][] getMeisaiArgs() {
		return meisaiArgs;
	}

	/**
	 * 明細レコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * mst240101_AsortmentBean;　戻り値　レコード格納ビーン<br>
	 * <br>
	 * @return レコード格納ビーン
	 */
	public mst240101_AsortmentBean getMeisaiArgs(int x, int y) {
		return meisaiArgs[x][y];
	}

	/**
	 * 明細レコード配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMeisaiArgs(mst240101_AsortmentBean[][]);<br>
	 * <br>
	 * @param 明細レコード配列
	 */
	public void setMeisaiArgs(mst240101_AsortmentBean[][] beans) {
		meisaiArgs = beans;
	}

	/**
	 * 明細レコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMeisaiArgs(mst240101_AsortmentBean[][]);<br>
	 * <br>
	 * @param 明細レコード
	 * @param 横軸
	 * @param 縦軸
	 */
	public void setMeisaiArgs(mst240101_AsortmentBean bean, int x , int y) {
		meisaiArgs[x][y] = bean;
	}


	/**
	 * カラー数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getColoeCnt;　戻り値　カラー数<br>
	 * <br>
	 * @return カラー数
	 */
	public int getColorCnt() {
		return colorCnt;
	}

	/**
	 * サイズ数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getColoeCnt;　戻り値　サイズ数<br>
	 * <br>
	 * @return サイズ数
	 */
	public int getSizeCnt() {
		return sizeCnt;
	}

	/**
	 * カラー数に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setColoeCnt(int i);<br>
	 * <br>
	 * @param カラー数
	 */
	public void setColorCnt(int i) {
		colorCnt = i;
	}

	/**
	 * サイズ数に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSizeCnt(int i);<br>
	 * <br>
	 * @param サイズ数
	 */
	public void setSizeCnt(int i) {
		sizeCnt = i;
	}

	/**
	 * 仕入先品番に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiireHinbanCd;　戻り値　仕入先品番<br>
	 * <br>
	 * @return 仕入先品番
	 */
	public String getSiireHinbanCd() {
		return siire_hinban_cd;
	}

	/**
	 * 漢字品名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinmeiKanjiNa;　戻り値　仕入先品番<br>
	 * <br>
	 * @return 仕入先品番
	 */
	public String getSyohinmeiKanjiNa() {
		return syohinmei_kanji_na;
	}

	/**
	 * 仕入先品番に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiireHinbanCd(string);<br>
	 * <br>
	 * @param 仕入先品番
	 */
	public void setSiireHinbanCd(String string) {
		siire_hinban_cd = string;
	}

	/**
	 * 商品漢字品名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinmeiKanjiNa(string);<br>
	 * <br>
	 * @param 商品漢字品名
	 */
	public void setSyohinmeiKanjiNa(String string) {
		syohinmei_kanji_na = string;
	}

	/**
	 * 明細レコード配列（取得時）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * mst240101_AsortmentBean[][];　戻り値　２次元配列レコード配列<br>
	 * <br>
	 * @return 明細レコード配列（取得時）
	 */
	public mst240101_AsortmentBean[][] getBeforeMeisaiArgs() {
		return beforeMeisaiArgs;
	}

	/**
	 * 明細レコード配列（取得時）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * mst240101_AsortmentBean;　戻り値　レコード格納ビーン<br>
	 * <br>
	 * @return 明細レコード配列（取得時）
	 */
	public mst240101_AsortmentBean getBeforeMeisaiArgs(int x, int y) {
		return beforeMeisaiArgs[x][y];
	}


	/**
	 * 明細レコード配列（取得時）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBeforeMeisaiArgs(beans);<br>
	 * <br>
	 * @param 明細レコード配列（取得時）
	 */
	public void setBeforeMeisaiArgs(mst240101_AsortmentBean[][] beans) {
		beforeMeisaiArgs = beans;
	}

	/**
	 * カラーリストに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getColorList;　戻り値　カラーリスト<br>
	 * <br>
	 * @return カラーリスト
	 */
	public ArrayList getColorList() {
		return colorList;
	}

	/**
	 * サイズリストに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSizeList;　戻り値　サイズリスト<br>
	 * <br>
	 * @return サイズリスト
	 */
	public ArrayList getSizeList() {
		return sizeList;
	}

	/**
	 * カラーリストに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setColorList(list);<br>
	 * <br>
	 * @param カラーリスト
	 */
	public void setColorList(ArrayList list) {
		colorList = list;
	}

	/**
	 * サイズリストに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setColorList(list);<br>
	 * <br>
	 * @param サイズリスト
	 */
	public void setSizeList(ArrayList list) {
		sizeList = list;
	}

	/**
	 * カラー選択値に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSelColors;　戻り値　カラー選択値<br>
	 * <br>
	 * @return カラー選択値
	 */
	public String[] getSelColors() {
		return selColors;
	}

	/**
	 * サイズ選択値に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSelSize;　戻り値　サイズ選択値<br>
	 * <br>
	 * @return カラー選択値
	 */
	public String[] getSelSize() {
		return selSize;
	}

	/**
	 * カラー選択値に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSelColors(strings);<br>
	 * <br>
	 * @param カラー選択値
	 */
	public void setSelColors(String[] strings) {
		selColors = strings;
	}

	/**
	 * サイズ選択値に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSelSize(strings);<br>
	 * <br>
	 * @param サイズ選択値
	 */
	public void setSelSize(String[] strings) {
		selSize = strings;
	}

	/**
	 * カラー使用可否に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSelSize;　戻り値　カラー使用可否<br>
	 * <br>
	 * @return カラー使用可否
	 */
	public String[] getDisableColors() {
		return disableColors;
	}

	/**
	 * サイズ使用可否に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSelSize;　戻り値　サイズ使用可否<br>
	 * <br>
	 * @return サイズ使用可否
	 */
	public String[] getDisableSize() {
		return disableSize;
	}

	/**
	 * カラー使用可否に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSelSize(strings);<br>
	 * <br>
	 * @param サイズ使用可否
	 */
	public void setDisableColors(String[] strings) {
		disableColors = strings;
	}

	/**
	 * サイズ使用可否に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSelSize(strings);<br>
	 * <br>
	 * @param サイズ使用可否
	 */
	public void setDisableSize(String[] strings) {
		disableSize = strings;
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
	 * レコード件数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getRecCnt;　戻り値　レコード件数<br>
	 * <br>
	 * @return レコード件数
	 */
	public int getRecCnt() {
		return recCnt;
	}

	/**
	 * レコード件数に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setRecCnt(i);<br>
	 * <br>
	 * @param レコード件数
	 */
	public void setRecCnt(int i) {
		recCnt = i;
	}

//	BUGNO-S052 2005.05.14 SIRIUS START
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
//	BUGNO-S052 2005.05.14 SIRIUS START


//BUGNO-S070 2005.07.11 Sirius START
	/**
	 * 画面表示最大サイズ数を取得
	 * <br>
	 * Ex)<br>
	 * getSizeMax() -&gt; int<br>
	 * <br>
	 * @return 画面表示最大サイズ数 
	 */
	public int getSizeMax() {
		return sizeMax;
	}

	/**
	 * 登録済みサイズリストに対するゲッター
	 * <br>
	 * Ex)<br>
	 * getRegistSizeList() -&gt; ArrayList<br>
	 * <br>
	 * @return 登録済みサイズリスト 
	 */
	public ArrayList getRegistSizeList() {
		return registSizeList;
	}


	/**
	 * 登録済みサイズリストに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setRegistSizeList(list);<br>
	 * <br>
	 * @param 登録済みサイズリスト
	 */
	public void setRegistSizeList(ArrayList list) {
		registSizeList = list;
	}
//BUGNO-S070 2005.07.11 Sirius END
}
