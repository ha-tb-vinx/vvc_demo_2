/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別仕入先マスタの画面表示明細データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別仕入先マスタの画面表示明細データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別仕入先マスタの画面表示明細データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別仕入先マスタの画面表示明細データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
public class mst450401_KeepMeisaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private List meisai = new ArrayList();	//明細
// ↓↓2006.07.04 maojm カスタマイズ修正↓↓
	private String CurrentPageNo	= "";				//現在表示ページ
	private String DispRows		= "30";				//一画面表示明細数
	private String LastPageNo		= "";				//最終ページ
	private String MaxRows			= "";				//最大行数
	private String EndRowInPage	= "";				//現在ページの終了行
	private String StartRowInPage	= "";				//現在ページの開始行
	private String Sentaku			= null;				// 選択
// ↑↑2006.07.04 maojm カスタマイズ修正↑↑	
// ↓↓2006.11.14 H.Yamamoto カスタマイズ修正↓↓
	private String new_tcd_l		= "";				//店舗リスト
	private String new_gno_l		= "";				//入力仕入先リスト
	private String new_flg_l		= "";				//更新仕入先リスト
	private String upd_ts_l		= "";				//更新日リスト
//	↑↑2006.11.14 H.Yamamoto カスタマイズ修正↑↑
	
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
	
	// Sentakuに対するセッターとゲッターの集合
	public String getSentaku() {
		return Sentaku;
	}

	public boolean setSentaku(String sentaku) {
		Sentaku = sentaku;
		return true;
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
	// ↑↑2006.07.04 maojm カスタマイズ修正↑↑

//	↓↓2006.11.14 H.Yamamoto カスタマイズ修正↓↓
//	店舗リストに対するゲッターセッター
	public String getNewTcdL()
	{
		return this.new_tcd_l;
	}
	public boolean setNewTcdL(String new_tcd_l)
	{
		this.new_tcd_l = new_tcd_l;
		return true;
	}
//	入力行No.リストに対するゲッターセッター
	public String getNewGnoL()
	{
		return this.new_gno_l;
	}
	public boolean setNewGnoL(String new_gno_l)
	{
		this.new_gno_l = new_gno_l;
		return true;
	}
//	更新フラグリストに対するゲッターセッター
	public String getNewFlgL()
	{
		return this.new_flg_l;
	}
	public boolean setNewFlgL(String new_flg_l)
	{
		this.new_flg_l = new_flg_l;
		return true;
	}
//	更新日リストに対するゲッターセッター
	public String getUpdTsL()
	{
		return this.upd_ts_l;
	}
	public boolean setUpdTsL(String upd_ts_l)
	{
		this.upd_ts_l = upd_ts_l;
		return true;
	}
//	↑↑2006.11.14 H.Yamamoto カスタマイズ修正↑↑
}
