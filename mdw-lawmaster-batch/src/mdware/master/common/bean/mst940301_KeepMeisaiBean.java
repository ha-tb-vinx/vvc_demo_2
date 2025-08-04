/**
 * <p>タイトル: mst940301_KeepMeisaiBeanクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品体系検索画面表示データ(明細)格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>タイトル: mst940301_KeepMeisaiBeanクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品体系検索画面表示データ(明細)格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
public class mst940301_KeepMeisaiBean {

	private List Meisai = new ArrayList();//明細リスト
	private String DispRows = "30";//一画面表示明細数
	private String CurrentPageNo = "";//現在表示ページ
	private String LastPageNo = "";//最終ページ
	private String MaxRows = "";//最大行数
	private String EndRowInPage = "";//現在ページの終了行
	private String StartRowInPage = "";//現在ページの開始行
	private String PageMode = "";//ページ遷移の処理状況

	/**
	 * 明細リストを格納
	 * @param Meisai 明細リスト
	 * @return true
	 */
	public void setMeisai(List Meisai) {
		this.Meisai = Meisai;
	}
	
	/**
	 * 明細リストを取得
	 * @param なし
	 * @return 明細リスト
	 */
	public List getMeisai() {
		return this.Meisai;
	}
	
	/**
	 * 現在表示ページ番号を格納
	 * @param CurrentPageNo 現在表示ページ番号
	 * @return true
	 */
	public void setCurrentPageNo(String CurrentPageNo) {
		this.CurrentPageNo = CurrentPageNo;
	}
	
	/**
	 * 現在表示ページ番号を取得
	 * @param なし
	 * @return 現在表示ページ番号
	 */
	public String getCurrentPageNo() {
		return this.CurrentPageNo;
	}
	
	/**
	 * 最終ページ番号を格納
	 * @param LastPageNo 最終ページ番号
	 * @return true
	 */
	public void setLastPageNo(String LastPageNo) {
		this.LastPageNo = LastPageNo;
	}
	
	/**
	 * 最終ページ番号を取得
	 * @param なし
	 * @return 最終ページ番号
	 */
	public String getLastPageNo() {
		return this.LastPageNo;
	}
	
	/**
	 * 最大行番号を格納
	 * @param MaxRows 最大行番号
	 * @return true
	 */
	public void setMaxRows(String MaxRows) {
		this.MaxRows = MaxRows;
	}
	
	/**
	 * 最大行番号を設定
	 * @param なし
	 * @return 最大行番号
	 */
	public String getMaxRows() {
		return this.MaxRows;
	}
	
	/**
	 * 現在ページの終了行番号を格納
	 * @param EndRowInPage 現在ページの終了行番号
	 * @return true
	 */
	public void setEndRowInPage(String EndRowInPage) {
		this.EndRowInPage = EndRowInPage;
	}
	
	/**
	 * 現在ページの終了行番号を取得
	 * @param なし
	 * @return 現在ページの終了行番号
	 */
	public String getEndRowInPage() {
		return this.EndRowInPage;
	}

	/**
	 * 現在ページの開始行番号を格納
	 * @param StartRowInPage 現在ページの開始行番号
	 * @return true
	 */
	public void setStartRowInPage(String StartRowInPage) {
		this.StartRowInPage = StartRowInPage;
	}
	
	/**
	 * 現在ページの開始行番号を取得
	 * @param なし
	 * @return 現在ページの開始行番号
	 */
	public String getStartRowInPage() {
		return this.StartRowInPage;
	}

	/**
	 * 一画面表示明細行数を取得
	 * @return 一画面表示明細数
	 */
	public String getDispRows() {
		return DispRows;
	}

	/**
	 * ページ遷移の処理状況の設定
	 * @param PageMode ページ遷移の処理状況
	 */
	public void setPageMode(String PageMode) {
		this.PageMode = PageMode;
	}

	/**
	 * ページ遷移の処理状況の取得
	 * @return ページ遷移の処理状況
	 */
	public String getPageMode() {
		return PageMode;
	}
}