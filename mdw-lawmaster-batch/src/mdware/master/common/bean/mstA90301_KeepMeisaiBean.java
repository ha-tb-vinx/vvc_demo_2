/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール発行リクエスト(単品指定)の画面表示明細データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール発行リクエスト(単品指定)の画面表示明細データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005-2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author xubq
 * @version 1.0(2006.08.18)初版作成
 */

package mdware.master.common.bean;

import java.util.ArrayList;


/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール発行リクエスト(単品指定)の画面表示明細データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール発行リクエスト(単品指定)の画面表示明細データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005-2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author xubq
 * @version 1.0(2006.08.18)初版作成
 */

public class mstA90301_KeepMeisaiBean
{

	private ArrayList Meisai	= new ArrayList();	// 一覧の明細
	private String CurrentPageNo	= "";				//現在表示ページ
	private String DispRows		= "30";				//一画面表示明細数
	private String LastPageNo		= "";				//最終ページ
	private String MaxRows			= "";				//最大行数
	private String EndRowInPage	= "";				//現在ページの終了行
	private String StartRowInPage	= "";			//現在ページの開始行
	private String MaxNo = "";							//最大行数
	private String Sentaku			= null;				// 選択
	private int    ClearMeisai    =  0;			//照会モードから追加モードに切替時、明細行を残さないためのフラグ by kema 06.11.01
	//*********DB Ver3.6 修正箇所 **********************************
	
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
	
	public String getMaxNo() {
		return MaxNo;
	}

	public void setMaxNo(String maxNo) {
		MaxNo = maxNo;
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
	// ClearMeisaiに対するセッターとゲッターの集合 add by kema 06.11.01
	public int getClearMeisai() {
		return ClearMeisai;
	}

	public boolean setClearMeisai(int clearmeisai) {
		ClearMeisai = clearmeisai;
		return true;
	}
}
