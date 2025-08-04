/**
 * <p>タイトル: 新ＭＤシステム 契約残確認・修正画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する契約残確認・修正画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sunpt
 * @version 1.0(2006/07/10)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;


/**
 * <p>タイトル: 新ＭＤシステム 契約残確認・修正画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する契約残確認・修正画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sunpt
 * @version 1.0(2006/07/10)初版作成
 */
public class mstB00201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	//並び順
	public static final String SORT_ITEM = "0";//アイテム順 
	public static final String SORT_SYOKARI_ASC = "1";//消化率（昇順）
	public static final String SORT_SYOKARI_DESC = "2";//消化率（降順）
	
	private String syohin_cd = null;	//商品コード
	private String syohin_nm = null;	//商品名
	private String bumon_cd = null;	//部門コード
	private String bumon_nm = null;	//部門名
	private String hinban_cd = null;	//品番コード
	private String hinban_nm = null;	//品番名
	private String hinsyu_cd = null;	//品種コード
	private String hinsyu_nm = null;	//品種名
	private String line_cd = null;		//ラインコード
	private String line_nm = null;		//ライン名
	private String unit_cd = null;		//ユニットコード
	private String unit_nm = null;		//ユニット名
	private String siiresaki_cd = null;	//仕入先コード
	private String siiresaki_nm = null;	//仕入先名
	private String tanawari_bangou_fm = null;	//棚番（開始）
	private String tanawari_bangou_to = null;	//棚番（終了）
	private String siiresaki_syohin_cd = null;	//仕入先商品コード
	private String subClass_cd = null;		//サブクラス
	private String subClass_nm = null;		//サブクラス名
	private String system_kb = null;		//システム区分
	// ===BEGIN=== Add by kou 2006/10/17
	private String by_no = null;			//バイヤーNO
	private String eos_kb = null;			//EOS区分
	// ===END=== Add by kou 2006/10/17
	//===BEGIN=== Add by kou 2006/11/09
	private String maisuMax = null;		//枚数（上限）
	private String maisuMin = null;		//枚数（下限）
	private String percentFg = null;
	//===END=== Add by kou 2006/11/09
//	↓↓2006.11.27 H.Yamamoto カスタマイズ修正↓↓
	private String hachu_ed_dt = null;	//発注終了日
//	↑↑2006.11.27 H.Yamamoto カスタマイズ修正↑↑
	//===BEGIN=== Add by kou 2006/11/14
	private String sscdFlg = null;		//あいまい検索フラグ
	//===END=== Add by kou 2006/11/14
//	↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
	private boolean inputflg = false; 
//	↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑
	private String shoukaritu_max = null;//消化率(上限）
	private String shoukaritu_min = null;//消化率(下限）
	private String sort = null;//並び順
	private int narabijyun = 0;
	private String yuko_dt = null;		//有効日
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	private String processingDivision = null;	//処理状況
	private String errorFlg = null;	//エラーフラグ
	private String errorMessage = null;	//エラーメッセージ
	private String firstFocus = null;	//フォーカスを最初に取得するオブジェクト名
	private String updateFlg = null;	//更新処理利用可能区分
	private String csvFlg = null;	//CSV処理利用可能区分
	private String printFlg = null;	//印刷処理利用可能区分
	private String before_disp_id = null;	//前画面ID
	private String disp_url = null;	//現画面URL
	private String updateProcessFlg = null;	//更新処理内容
	private Map ctrlColor = new HashMap();	//コントロール背景色

	private static final String INIT_PAGE = "mstB00101_KeiyakuzanShuseiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mstB00201_KeiyakuzanShuseiEdit";	// 契約残確認・修正画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";			// 権限エラーJSPを取得

	/**
	 * 商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyohinCd(String syohin_cd)
	{
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
	public String getSyohinCd()
	{
		return this.syohin_cd;
	}

	/**
	 * 商品名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohin_nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohin_nm() {
		return syohin_nm;
	}

	/**
	 * 商品名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohin_nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSyohin_nm(String string) {
		syohin_nm = string;
	}


	/**
	 * 有効日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setYukoDt("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setYukoDt(String yuko_dt)
	{
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
	public String getYukoDt()
	{
		return this.yuko_dt;
	}


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
	 * 削除フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setDeleteFg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setDeleteFg(String delete_fg)
	{
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
	public String getDeleteFg()
	{
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
	 * 前画面IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBeforeDispId("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setBeforeDispId(String before_disp_id)
	{
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
	public String getBeforeDispId()
	{
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
	public boolean setDispUrl(String disp_url)
	{
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
	public String getDispUrl()
	{
		return this.disp_url;
	}


	/**
	 * 更新処理内容に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateProcessFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateProcessFlg(String updateProcessFlg)
	{
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
	public String getUpdateProcessFlg()
	{
		return this.updateProcessFlg;
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


	/**
	 * 品番コードに対するゲッター<br>
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
	 * 品番コードに対するセッター<br>
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
	 * 仕入先コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSiiresakiCd() {
		return siiresaki_cd;
	}
	
	/**
	 * 仕入先コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSiiresakiCd(String siiresaki_cd) {
		this.siiresaki_cd = siiresaki_cd;
	}

	/**
	 * 仕入先名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSiiresakiNm() {
		return siiresaki_nm;
	}
	
	/**
	 * 仕入先名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSiiresakiNm(String siiresaki_nm) {
		this.siiresaki_nm = siiresaki_nm;
	}

	/**
	 * 仕入先商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSiiresakiSyohinCd() {
		return siiresaki_syohin_cd;
	}
	
	/**
	 * 仕入先商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSiiresakiSyohinCd(String siiresaki_syohin_cd) {
		this.siiresaki_syohin_cd = siiresaki_syohin_cd;
	}

	/**
	 * 棚番（開始）に対するゲッター<br>
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
	 * 棚番（開始）に対するセッター<br>
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
	 * 棚番（終了）に対するゲッター<br>
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
	 * 棚番（終了）に対するセッター<br>
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
	 * サブクラスコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * subClass_cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSubClass_cd() {
		return subClass_cd;
	}
	/**
	 * サブクラスコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * subClass_cd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSubClass_cd(String string) {
		this.subClass_cd = string;
	}
	
	
	/**
	 * サブクラス名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * subClass_nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSubClass_nm() {
		return subClass_nm;
	}
	
	/**
	 * サブクラス名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * subClass_nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSubClass_nm(String string) {
		this.subClass_nm = string;
	}
	
	
	/**
	 * 消化率(上限）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * shoukaritu_max();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getShoukaritu_max() {
		return shoukaritu_max;
	}
	
	/**
	 * 消化率(上限）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * shoukaritu_max("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setShoukaritu_max(String shoukaritu_max) {
		this.shoukaritu_max = shoukaritu_max;
	}
	
	
	/**
	 * 消化率(下限）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * shoukaritu_min();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getShoukaritu_min() {
		return shoukaritu_min;
	}
	
	/**
	 * 消化率(下限）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * shoukaritu_min("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setShoukaritu_min(String shoukaritu_min) {
		this.shoukaritu_min = shoukaritu_min;
	}
	public int getNarabijyun() {
		return narabijyun;
	}
	public void setNarabijyun(int narabijyun) {
		this.narabijyun = narabijyun;
	}
	
	/**
	 * 並び順に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * sort();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * 並び順に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * sort("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	/** 明細変更フラグ */
	private String changeFg;

	public String getChangeFg() {
		if (changeFg == null)
			return "";
		return changeFg;
	}
	public void setChangeFg(String changeFg) {
		this.changeFg = changeFg;
	}

	/**
	 * @return
	 */
	public String getBy_no()
	{
		return by_no;
	}

	/**
	 * @return
	 */
	public String getEos_kb()
	{
		return eos_kb;
	}

	/**
	 * @param string
	 */
	public void setBy_no(String string)
	{
		by_no = string;
	}

	/**
	 * @param string
	 */
	public void setEos_kb(String string)
	{
		eos_kb = string;
	}

	/**
	 * @return
	 */
	public String getMaisuMax()
	{
		return maisuMax;
	}

	/**
	 * @return
	 */
	public String getMaisuMin()
	{
		return maisuMin;
	}

	/**
	 * @param string
	 */
	public void setMaisuMax(String string)
	{
		maisuMax = string;
	}

	/**
	 * @param string
	 */
	public void setMaisuMin(String string)
	{
		maisuMin = string;
	}

	/**
	 * @return
	 */
	public String getPercentFg()
	{
		return percentFg;
	}

	/**
	 * @param string
	 */
	public void setPercentFg(String string)
	{
		percentFg = string;
	}

	/**
	 * @return
	 */
	public String getSscdFlg()
	{
		return sscdFlg;
	}

	/**
	 * @param string
	 */
	public void setSscdFlg(String string)
	{
		sscdFlg = string;
	}

//	↓↓2006.11.27 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * @return
	 */
	public String getHachuEdDt()
	{
		return hachu_ed_dt;
	}

	/**
	 * @param string
	 */
	public void setHachuEdDt(String string)
	{
		hachu_ed_dt = string;
	}
//	↑↑2006.11.27 H.Yamamoto カスタマイズ修正↑↑

//	↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
	public boolean getInputflg() {
		return inputflg;
	}

	public void setInputflg(boolean inputflg) {
		this.inputflg = inputflg;
	}	
//	↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑

}
