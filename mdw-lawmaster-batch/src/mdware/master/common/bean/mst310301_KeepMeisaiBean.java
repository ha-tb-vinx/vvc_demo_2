/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面表示明細データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタの画面表示明細データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */

package mdware.master.common.bean;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

import java.util.ArrayList;


/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面表示明細データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタの画面表示明細データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */

public class mst310301_KeepMeisaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private ArrayList Meisai	= new ArrayList();	// 一覧の明細
//	↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
	private String CurrentPageNo	= "";				//現在表示ページ
	private String DispRows		= "30";				//一画面表示明細数
	private String LastPageNo		= "";				//最終ページ
	private String MaxRows			= "";				//最大行数
	private String EndRowInPage	= "";				//現在ページの終了行
	private String StartRowInPage	= "";				//現在ページの開始行
	private String Sentaku			= null;				// 選択
	private String tenpo_kanji_rn	= null;				//店舗名
	private String donyu_st_dt		= null;				//導入開始日
	private String donyu_st_dt_before 	= null;			//導入開始日（チェック用）
	private String insert_ts 		  	= null;			//作成年月日
	private String insert_user_id 	  	= null;			//作成者社員ID
	private String update_ts 		  	= null;			//更新年月日
	//*********DB Ver3.6 修正箇所 **********************************
	private String update_user_id 	  	= null;	//更新者社員ID
//	↑↑2006.06.21 jiangyan カスタマイズ修正↑↑

	
	// Meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(ArrayList Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
	public ArrayList getMeisai() {
		return this.Meisai;
	}
	
	// DispRowsに対するセッターとゲッターの集合
	public boolean setDispRows(String DispRows) {
		this.DispRows = DispRows;
		return true;
	}
	
	public String getDispRows() {
		return this.DispRows;
	}
	
//	↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
	// CurrentPageNoに対するセッターとゲッターの集合
	public boolean setCurrentPageNo(String CurrentPageNo) {
		this.CurrentPageNo = CurrentPageNo;
		return true;
	}
	
	public String getCurrentPageNo() {
		return this.CurrentPageNo;
	}
	
	// LastPageNoに対するセッターとゲッターの集合
	public boolean setLastPageNo(String LastPageNo) {
		this.LastPageNo = LastPageNo;
		return true;
	}
	
	public String getLastPageNo() {
		return this.LastPageNo;
	}
	
	// MaxRowsに対するセッターとゲッターの集合
	public boolean setMaxRows(String MaxRows) {
		this.MaxRows = MaxRows;
		return true;
	}
	
	public String getMaxRows() {
		return this.MaxRows;
	}
	
	// EndRowInPageに対するセッターとゲッターの集合
	public boolean setEndRowInPage(String EndRowInPage) {
		this.EndRowInPage = EndRowInPage;
		return true;
	}
	
	public String getEndRowInPage() {
		return this.EndRowInPage;
	}

	// StartRowInPageに対するセッターとゲッターの集合
	public boolean setStartRowInPage(String StartRowInPage) {
		this.StartRowInPage = StartRowInPage;
		return true;
	}
	
	public String getStartRowInPage() {
		return this.StartRowInPage;
	}
	
	// Sentakuに対するセッターとゲッターの集合
	public String getSentaku() {
		return Sentaku;
	}

	public boolean setSentaku(String sentaku) {
		Sentaku = sentaku;
		return true;
	}
	
	// tenpo_kanji_rnに対するセッターとゲッターの集合
	public boolean setTenpoKanjiRn(String tenpo_kanji_rn)
	{
		this.tenpo_kanji_rn = tenpo_kanji_rn;
		return true;
	}
	public String getTenpoKanjiRn()
	{
		return this.tenpo_kanji_rn;
	}
	public boolean setDonyuStDt(String donyu_st_dt)
	{
		this.donyu_st_dt = donyu_st_dt;
		return true;
	}

	public String getDonyuStDt()
	{
		return this.donyu_st_dt;
	}
	
	public boolean setDonyuStDtBefore(String donyu_st_dt_before)
	{
		this.donyu_st_dt_before = donyu_st_dt_before;
		return true;
	}
	public String getDonyuStDtBefore()
	{
		return this.donyu_st_dt_before;
	}
	public String getDonyuStDtBeforeString()
	{
		return this.donyu_st_dt_before;
	}
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
	public String getInsertTs()
	{
		return this.insert_ts;
	}
	public String getInsertTsString()
	{
		return this.insert_ts;
	}
	public String getInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(this.insert_ts);
	}
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId()
	{
		return this.insert_user_id;
	}
	public String getInsertUserIdString()
	{
		return this.insert_user_id;
	}
	public String getInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(this.insert_user_id);
	}
	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdateTs()
	{
		return this.update_ts;
	}
	public String getUpdateTsString()
	{
		return this.update_ts;
	}
	public String getUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(this.update_ts);
	}

	//*********DB Ver3.6 修正箇所 **********************************
	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserTs(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserTs()
	{
		return this.update_user_id;
	}
	public String getUpdateUserTsString()
	{
		return this.update_user_id ;
	}
	public String getUpdateUserTsHTMLString()
	{
		return HTMLStringUtil.convert(this.update_user_id);
	}
	
//	↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
}
