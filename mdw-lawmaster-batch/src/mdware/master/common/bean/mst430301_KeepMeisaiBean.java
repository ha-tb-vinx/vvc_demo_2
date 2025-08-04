/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタ登録(一覧)表示データ(明細)格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタ登録(一覧)表示データ(明細)格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/23)初版作成
 */
package mdware.master.common.bean;

import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.ArrayList;

import java.util.List;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタ登録(一覧)の画面表示データ(明細)格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタ登録(一覧)の画面表示データ(明細)格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/23)初版作成
 */
public class mst430301_KeepMeisaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	
// ↓↓2006.08.03 guohy カスタマイズ修正↓↓
//	private ArrayList Meisai		= new ArrayList();	// 一覧の明細
	private List Meisai		= new ArrayList();	// 一覧の明細 List
	private String CurrentPageNo	= "";				// 現在表示ページ
	private String LastPageNo		= "";				// 最終ページ
	private String MaxRows			= "";				// 最大行数
	private String EndRowInPage	= "";				// 現在ページの終了行
	private String StartRowInPage	= "";				// 現在ページの開始行
	private String DispRows		= "30";				// 一画面表示明細数
	private String PageMode		= "";				// ページ遷移の処理状況
//	 ↑↑2006.08.03 guohy カスタマイズ修正↑↑
	
//	 ↓↓2006.08.03 guohy カスタマイズ修正↓↓
	// Meisaiに対するセッターとゲッターの集合
//	public boolean setMeisai(ArrayList Meisai) {
	public boolean setMeisai(List Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
//	public ArrayList getMeisai() {
	public List getMeisai() {
		return this.Meisai;
	}
//	 ↑↑2006.08.03 guohy カスタマイズ修正↑↑
	
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
	
//	 ↓↓2006.08.03 guohy カスタマイズ修正↓↓
	// PageModeに対するセッターとゲッターの集合
	public boolean setPageMode(String PageMode) {
		this.PageMode = PageMode;
		return true;
	}
	
	public String getPageMode() {
		return this.PageMode;
	}
	
	// DispRowsに対するセッターとゲッターの集合
	public boolean setDispRows(String DispRows) {
		this.DispRows = DispRows;
		return true;
	}
	
	public String getDispRows() {
		return this.DispRows;
	}
//	 ↑↑2006.08.03 guohy カスタマイズ修正↑↑
}

