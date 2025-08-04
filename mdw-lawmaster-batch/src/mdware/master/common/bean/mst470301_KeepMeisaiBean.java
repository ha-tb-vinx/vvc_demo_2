/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目(一覧)格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kiiuchi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;


import java.util.ArrayList;


/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目(一覧)格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kiiuchi
 * @version 1.0
 * @see なし								
 */
public class mst470301_KeepMeisaiBean
{
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓
//	private static final StcLog stcLog = StcLog.getInstance();
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑
	private ArrayList Meisai	= new ArrayList();	// 一覧の明細
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓
	private String DispRows		= "30";				// 一画面表示明細数
	private String PageMode		= "";				// ページ遷移の処理状況
	private String CurrentPageNo	= "";				//現在表示ページ
	private String LastPageNo		= "";				//最終ページ
	private String MaxRows			= "";				//最大行数
	private String EndRowInPage	= "";				//現在ページの終了行
	private String StartRowInPage	= "";				//現在ページの開始行
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑	
	// Meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(ArrayList Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
	public ArrayList getMeisai() {
		return this.Meisai;
	}
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓
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
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑

	

}
