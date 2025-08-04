package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール発行リクエスト(実用)の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール発行リクエスト(実用)の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author fanglh
 * @version 1.0(2006/08/11)初版作成
 */
public class mstB20101_KeepBean {
	
	private static final StcLog stcLog = StcLog.getInstance();

	private String firstFocus = null;		// フォーカスを最初に取得するオブジェクト名
	private String insertFlg = null;		// 新規処理利用可能区分
	private String updateFlg = null;		// 更新処理利用可能区分
	private String deleteFlg = null;		// 削除処理利用可能区分
	private String referenceFlg = null;	// 照会処理利用可能区分
	private String csvFlg = null;			// CSV処理利用可能区分
	private String printFlg = null;		// 印刷処理利用可能区分
	private String errorFlg = null;		// エラーフラグ
	private String errorMessage = null;	// エラーメッセージ
	private String checkFlg = null;		// チェック処理判断
	private int maxRows = 0;
	private int maxCnts = 0;
	private int startRowInPage = 0;
	private int endRowInPage = 0;
	private int maxRowInPage = 30;			// 1ページの最大表示件数
	private Map ctrlColor = new HashMap();	// コントロール背景色
	private String bumon_cd = null;		// 部門コード
	private String bumon_nm = null;		// 部門名
	private String hinban_cd = null;		// 品番コード
	private String hinban_nm = null;		// 品番名
	private String hinsyu_cd = null;		// 品種コード
	private String hinsyu_nm = null;		// 品種名
	private String line_cd = null;			// ラインコード
	private String line_nm = null;			// ライン名
	private String unit_cd = null;			// ユニットコード
	private String unit_nm = null;			// ユニット名
	private String tenpo_cd = null;		// 店舗コード
	private String tenpo_nm = null;		// 店舗名SEND_DT
	private String send_dt = null;			// 出力希望日
	private String by_no = null;			// BY NO.
	private String tanawari_bangou_fm = null;// 棚割番号（開始）
	private String tanawari_bangou_to = null;// 棚割番号（終了）
	private String system_kb = null;		// システム区分
	private String back_kb = null;			// 遷移区分
	private String processingDivision = null;	// 処理状況
	private String mstDate	= null;				//マスタ用日付
	private String insert_ts = null;	// 作成年月日
	private String insert_user_id = null;	// 作成者社員ID
	private String update_ts = null;	// 更新年月日
	private String update_user_id = null;	// 更新者社員ID
	private List meisai = new ArrayList();	
	private String changflg ="";
	private Map MeisaiColor = new HashMap(); // コントロール背景色（ヘッダ）
	
	
	private static final String INIT_PAGE = "mstB20101_PsRequestA07Init";		// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mstB20201_PsRequestA07Edit";			// 新規画面JSPを取得
	private static final String DELETE_PAGE = "mstB20301_PsRequestA07Delete";	// 削除画面JSPを取得
	private static final String VIEW_PAGE = "mstB20401_PsRequestA07View";		// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";			// 権限エラーJSPを取得


	/**
	 * 作成年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setInsertTs("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setInsertTs(String insert_ts)
	{
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
	public String getInsertTs()
	{
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
	public boolean setInsertUserId(String insert_user_id)
	{
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
	public String getInsertUserId()
	{
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
	public boolean setUpdateTs(String update_ts)
	{
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
	public String getUpdateTs()
	{
		return this.update_ts;
	}
//	 	******** ＤＢ Ver3.6修正箇所 *****************************************
	/**
	 * 更新者社員IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateUserTs("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateUserTs(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
		

	/**
	 * 更新者社員IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateUserTs();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdateUserTs()
	{
		return this.update_user_id;
	}

	/**
	 * 更新者社員IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateUserId("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateUserId(String update_user_id)
	{
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
	public String getUpdateUserId()
	{
		return this.update_user_id;
	}


	/**
	 * 明細配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMeisai();　戻り値　明細配列<br>
	 * <br>
	 * @return List 明細配列
	 */
	public List getMeisai() {
		return meisai;
	}

	/**
	 * 明細配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMeisai(List);<br>
	 * <br>
	 * @param List 設定する明細配列
	 */
	public void setMeisai(List list) {
		meisai = list;
	}

	/**
	 * マスタ日付に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * getMstDate("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public String getMstDate() {

		return mstDate;
	}
	
	/**
	 * マスタ日付に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMstDate("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setMstDate(String mstDate) {

		this.mstDate = mstDate;
	}
	
	/**
	 * 処理状況に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setProcessingDivision("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setProcessingDivision(String processingDivision)
	{
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
	public String getProcessingDivision()
	{
		return this.processingDivision;
	}

	/**
	 * 店舗コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSendDt("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSendDt(String send_dt)
	{
		this.send_dt = send_dt;
		return true;
	}
	/**
	 * 店舗コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSendDt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSendDt()
	{
		return this.send_dt;
	}
	/**
	 * 店舗コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setTenpoCd(String tenpo_cd)
	{
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
	public String getTenpoCd()
	{
		return this.tenpo_cd;
	}
	
	/**
	 * 店舗名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setTenpoNm(String tenpo_nm)
	{
		this.tenpo_nm = tenpo_nm;
		return true;
	}
	/**
	 * 店舗名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTenpoNm()
	{
		return this.tenpo_nm;
	}

	/**
	 * ユニットコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUnitCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUnitCd() {
		return unit_cd;
	}
	
	/**
	 * ユニットコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUnitCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setUnitCd(String unit_cd) {
		this.unit_cd = unit_cd;
	}

	/**
	 * ユニット名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUnitNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUnitNm() {
		return unit_nm;
	}
	
	/**
	 * ユニット名に対するセッター<br>システム区分
	 * <br>
	 * Ex)<br>
	 * setUnitNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setUnitNm(String unit_nm) {
		this.unit_nm = unit_nm;
	}

	/**
	 * // 品番コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinbanCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHinbanCd() {
		return hinban_cd;
	}
	
	/**
	 * // 品番コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHinbanCd(String hinban_cd) {
		this.hinban_cd = hinban_cd;
	}

	/**
	 * 品番名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHinbanNm() {
		return hinban_nm;
	}
	
	/**
	 * 品番名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHinbanNm(String hinban_nm) {
		this.hinban_nm = hinban_nm;
	}

	/**
	 * 品種コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinsyuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHinsyuCd() {
		return hinsyu_cd;
	}
	
	/**
	 * 品種コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinsyuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHinsyuCd(String hinsyu_cd) {
		this.hinsyu_cd = hinsyu_cd;
	}

	/**
	 * 品種名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinsyuNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHinsyuNm() {
		return hinsyu_nm;
	}
	
	/**
	 * 品種名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinsyuNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHinsyuNm(String hinsyu_nm) {
		this.hinsyu_nm = hinsyu_nm;
	}

	/**
	 * ラインコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLineCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getLineCd() {
		return line_cd;
	}
	
	/**
	 * ラインコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLineCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setLineCd(String line_cd) {
		this.line_cd = line_cd;
	}

	/**
	 * ライン名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLineNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getLineNm() {
		return line_nm;
	}
	
	/**
	 * ライン名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLineNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setLineNm(String line_nm) {
		this.line_nm = line_nm;
	}
	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	/**
	 * 部門コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	 public String getBumonCd()
	 {
	 	return this.bumon_cd;
	 }

	 /**
	  * 部門名に対するゲッター<br>
	  * <br>
	  * Ex)<br>
	  * getBumon_nm();　戻り値　文字列<br>
	  * <br>
	  * @return String 文字列
	  */
	 public String getBumon_nm() {
	 	return bumon_nm;
	 }

	 /**
	  * 部門名に対するセッター<br>
	  * <br>
	  * Ex)<br>
	  * setBumon_nm("文字列");<br>
	  * <br>
	  * @param String 設定する文字列
	  */
	 public void setBumon_nm(String string) {
	 	bumon_nm = string;
	 }
	 
	/**
	 * 初期画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getInitUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return	String
	 */
	public String getInitUrl(String logHeader,String logMsg)
	{
	// 画面メッセージと同様のログを出力
	if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
	|| this.errorFlg.equals("")){
		// 通常系
		if(!this.errorMessage.equals("")){
		stcLog.getLog().info(logHeader + this.errorMessage);
		}
	} else {
		// エラー系
		stcLog.getLog().error(logHeader + this.errorMessage);
	}
	
	// 処理終了ログ
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
	 * @return	String
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
	 * @return	String
	 */
	public String getEditUrl(String logHeader,String logMsg)
	{
	// 画面メッセージと同様のログを出力
	if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
	|| this.errorFlg.equals("")){
		// 通常系
		if(!this.errorMessage.equals("")){
		stcLog.getLog().info(logHeader + this.errorMessage);
		}
	} else {
		// エラー系
		stcLog.getLog().error(logHeader + this.errorMessage);
	}
	
	// 処理終了ログ
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
	 * @return	String
	 */
	public String getEditUrl()
	{
	return	EDIT_PAGE;
	}

	/**
	 * 削除画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getDeleteUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return	String
	 */
	public String getDeleteUrl(String logHeader,String logMsg)
	{
	// 画面メッセージと同様のログを出力
	if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
	|| this.errorFlg.equals("")){
		// 通常系
		if(!this.errorMessage.equals("")){
		stcLog.getLog().info(logHeader + this.errorMessage);
		}
	} else {
		// エラー系
		stcLog.getLog().error(logHeader + this.errorMessage);
	}
	
	// 処理終了ログ
	if(!logMsg.equals("")){
		stcLog.getLog().info(logHeader + logMsg);
	}
	
	return	DELETE_PAGE;
	}
	/**
	 * 削除画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getDeleteUrl() -&gt; String<br>
	 * <br>
	 * @return	String
	 */
	public String getDeleteUrl()
	{
	return	DELETE_PAGE;
	}

	/**
	 * 照会画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getViewUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return	String
	 */
	public String getViewUrl(String logHeader,String logMsg)
	{
	// 画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			// 通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		// 処理終了ログ
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
	 * @return	String
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
	 * @return	String
	 */
	public String getKengenErr(String logHeader)
	{
		stcLog.getLog().error(logHeader + InfoStrings.getInstance().getInfo("49999"));
		return KENGEN_PAGE;
	}

	/**
	 * 取得件数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMaxRows();　戻り値　取得件数<br>
	 * <br>
	 * @return int 取得件数
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * 取得件数に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMaxRows(int);<br>
	 * <br>
	 * @param int 取得件数
	 */
	public void setMaxRows(int i) {
		maxRows = i;
	}

	/**
	 * 表示開始位置に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getStartRowInPage();　戻り値　表示開始位置<br>
	 * <br>
	 * @return int 表示開始位置
	 */
	public int getStartRowInPage() {
		return startRowInPage;
	}

	/**
	 * 表示開始位置に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setStartRowInPage(int);<br>
	 * <br>
	 * @param int 表示開始位置
	 */
	public void setStartRowInPage(int i) {
		startRowInPage = i;
	}

	/**
	 * 表示終了位置に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getEndRowInPage();　戻り値　表示終了位置<br>
	 * <br>
	 * @return int 表示終了位置
	 */
	public int getEndRowInPage() {
		return endRowInPage;
	}

	/**
	 * 表示終了位置に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setEndRowInPage(int);<br>
	 * <br>
	 * @param int 表示終了位置
	 */
	public void setEndRowInPage(int i) {
		endRowInPage = i;
	}

	/**
	* 1ページの最大表示件数<br>
	* <br>
	* Ex)<br>
	* getMaxRowInPage();　<br>
	* <br>
	* @return int 
	*/
	public int getMaxRowInPage() {
		return maxRowInPage;
	}

	/**
	* 1ページの最大表示件数<br>
	* <br>
	* Ex)<br>
	* setMaxRowInPage(int);<br>
	* <br>
	* @param int 
	*/
	public void setMaxRowInPage(int i) {
		maxRowInPage = i;
	}
	/**
	 * チェック処理判断に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCheckFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setCheckFlg(String checkFlg)
	{
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
	public String getCheckFlg()
	{
		return this.checkFlg;
	}

	/**
	 * エラーメッセージに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorMessage("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setErrorMessage(String errorMessage)
	{
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
	public String getErrorMessage()
	{
		return this.errorMessage;
	}

	/**
	 * エラーフラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setErrorFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setErrorFlg(String errorFlg)
	{
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
	public String getErrorFlg()
	{
		return this.errorFlg;
	}
	
	/**
	 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setFirstFocus("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setFirstFocus(String firstFocus)
	{
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
	public String getFirstFocus()
	{
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
	public boolean setInsertFlg(String insertFlg)
	{
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
	public String getInsertFlg()
	{
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
	public boolean setUpdateFlg(String updateFlg)
	{
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
	public String getUpdateFlg()
	{
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
	public boolean setDeleteFlg(String deleteFlg)
	{
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
	public String getDeleteFlg()
	{
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
	public boolean setReferenceFlg(String referenceFlg)
	{
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
	public String getReferenceFlg()
	{
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
	public boolean setCsvFlg(String csvFlg)
	{
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
	public String getCsvFlg()
	{
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
	public boolean setPrintFlg(String printFlg)
	{
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
	public String getPrintFlg()
	{
		return this.printFlg;
	}

	/**
	 * コントロール背景色に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor("Map");<br>
	 * <br>
	 * @param Map 設定するMap配列
	 */
	public boolean setCtrlColor(Map ctrlColor)
	{
		try
		{
		this.ctrlColor = ctrlColor;
		}
		catch(Exception e)
		{
		return false;
		}
		return true;
	}
	/**
	 * コントロール背景色に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor();　戻り値　Map配列<br>
	 * <br>
	 * @return String[] Map配列
	 */
	public Map getCtrlColor()
	{
		return this.ctrlColor;
	}

	/**
	 * BY NO.に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getByNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getByNo() {
		return by_no;
	}
	
	/**
	 * BY NO.に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setByNo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setByNo(String by_no) {
		this.by_no = by_no;
	}

	/**
	 * 棚割番号（開始）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTanawariBangouFm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTanawariBangouFm() {
		return tanawari_bangou_fm;
	}
	
	/**
	 * 棚割番号（開始）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTanawariBangouFm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setTanawariBangouFm(String tanawari_bangou_fm) {
		this.tanawari_bangou_fm = tanawari_bangou_fm;
	}

	/**
	 * 棚割番号（終了）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTanawariBangouTo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTanawariBangouTo() {
		return tanawari_bangou_to;
	}
	
	/**
	 * 棚割番号（終了）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTanawariBangouTo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setTanawariBangouTo(String tanawari_bangou_to) {
		this.tanawari_bangou_to = tanawari_bangou_to;
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
		return system_kb;
	}
	
	/**
	 * システム区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSystemKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSystemKb(String system_kb) {
		this.system_kb = system_kb;
	}

	 /**
	  * 遷移区分に対するゲッター<br>
	  * <br>
	  * Ex)<br>
	  * getBackKb();　戻り値　文字列<br>
	  * <br>
	  * @return String 文字列
	  */
	 public String getBackKb() {
	 	return back_kb;
	 }

	 /**
	  * 遷移区分に対するセッター<br>
	  * <br>
	  * Ex)<br>
	  * setBackKb("文字列");<br>
	  * <br>
	  * @param String 設定する文字列
	  */
	 public void setBackKb(String string) {
		 back_kb = string;
	 }

	 public Map getMeisaiColor() {
		 return MeisaiColor;
	 }

	 public void setMeisaiColor(Map meisaiColor) {
		 MeisaiColor = meisaiColor;
	 }
	 public String getChangflg() {
		 return changflg;
	 }

	 public void setChangflg(String changflg) {
		 this.changflg = changflg;
	 }	
	/**
	 * 最大件数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMaxCnts();　戻り値　取得件数<br>
	 * <br>
	 * @return int 取得件数
	 */
	public int getMaxCnts() {
		return maxCnts;
	}

	/**
	 * 最大件数に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMaxCnts(int);<br>
	 * <br>
	 * @param int 取得件数
	 */
	public void setMaxCnts(int i) {
		maxCnts = i;
	}
	 
}
