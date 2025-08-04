/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス 登録票アップロード画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する登録票アップロード画面の項目格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;


public class mst380101_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String ErrorFlg			= "";				// エラーフラグ
	private String ErrorMessage		= "";				// エラーメッセージ
	private String[] MenuBar			= null;			// メニューバーアイテム
//       ↓↓2006.06.23 baozy カスタマイズ修正↓↓
	private String upload_file = "";
	private String kakunin_flg = "";
	private String buyer_no = "";	// add by kema
	private String torikomi_dt = null;
	private String excel_file_syubetsu = null;
	private long uketsuke_no = 0;
	private String excel_file_na = null;
	private String upload_comment_tx = null;
	private String toroku_syonin_fg = null;
	private String toroku_syonin_ts = null;
	private String syori_jyotai_fg = null;
	private String kakunin_fg = null;
	private String firstFocus = null;	// フォーカスを最初に取得するオブジェクト名
	private String chkPage = "";
	private Map chkNo = new HashMap(); // 選択した行
	private Map chkKey = new HashMap();// 選択した行のキー
	private List meisai = new ArrayList();
	private int currentPageNum = 0;// 表示ページ番号
	private int lastPageNum = 0;// 表示ページ番号
	private int maxRows = 0;
	private int startRowInPage = 0;
	private int endRowInPage = 0;
//       ↑↑2006.06.23 baozy カスタマイズ修正↑↑

	// ErrorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String ErrorFlg)
	{
		this.ErrorFlg = ErrorFlg;
		return true;
	}
	public String getErrorFlg()
	{
		return this.ErrorFlg;
	}

	// ErrorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
		return true;
	}
	public String getErrorMessage()
	{
		return this.ErrorMessage;
	}
	// MenuBarに対するセッターとゲッターの集合
	public boolean setMenuBar(String[] MenuBar)
	{
		this.MenuBar = MenuBar;
		return true;
	}
	public String[] getMenuBar()
	{
		return this.MenuBar;
	}

//  ↓↓2006.06.23 baozy カスタマイズ修正↓↓

	/**
	 * 選択したページセッター<br>
	 * <br>
	 * Ex)<br>
	 * setChkPage("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setChkPage(String chkPage)
		{
			this.chkPage = chkPage;
			return true;
		}
	/**
	 * 選択したページゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getChkPage();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getChkPage()
		{
			return this.chkPage;
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
	 * 選択した行セッター<br>
	 * <br>
	 * Ex)<br>
	 * setChkNo("文字列");<br>
	 * <br>
	 * @param Map 設定する文字列
	 */
		public boolean setChkNo(Map chkNo)
		{
			this.chkNo = chkNo;
			return true;
		}
	/**
	 * 選択した行ゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getChkNo();　戻り値　文字列<br>
	 * <br>
	 * @return Map 文字列
	 */
		public Map getChkNo()
		{
			return this.chkNo;
		}

	/**
	 * 選択した行のキーセッター<br>
	 * <br>
	 * Ex)<br>
	 * setChkKey("文字列");<br>
	 * <br>
	 * @param Map 設定する文字列
	 */
		public boolean setChkKey(Map chkKey)
		{
			this.chkKey = chkKey;
			return true;
		}
	/**
	 * 選択した行のキーゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getChkKey();　戻り値　文字列<br>
	 * <br>
	 * @return Map 文字列
	 */
		public Map getChkKey()
		{
			return this.chkKey;
		}

	/**
	 * CSVファイル名<br>
	 * <br>
	 * Ex)<br>
	 * getUploadFile();　戻り値　CSVファイル名<br>
	 * <br>
	 * @return String CSVファイル名
	 */
	public String getUploadFile() {
		return upload_file;
	}

	/**
	 * CSVファイル名<br>
	 * <br>
	 * Ex)<br>
	 * setUploadFile(String);<br>
	 * <br>
	 * @param String CSVファイル名
	 */
	public void setUploadFile(String upload_file) {
		this.upload_file = upload_file;
	}

	/**
	 * @return String 画面のバイヤーNo
	 */
	public String getBuyerNo() {
		return buyer_no;
	}

	/**
	 * @param String 設定するバイヤーNo
	 */
	public void setBuyerNo(String buyer_no) {
		this.buyer_no = buyer_no;
	}

	/**
	 * 画面の検索区分<br>
	 * <br>
	 * Ex)<br>
	 * getKakuninFlg();　戻り値　画面の検索区分<br>
	 * <br>
	 * @return String 画面の検索区分
	 */
	public String getKakuninFlg() {
		return kakunin_flg;
	}

	/**
	 * 画面の検索区分<br>
	 * <br>
	 * Ex)<br>
	 * setKakuninFlg(String);<br>
	 * <br>
	 * @param String 設定する検索区分
	 */
	public void setKakuninFlg(String kakunin_flg) {
		this.kakunin_flg = kakunin_flg;
	}
	
	/**
	 * 取込日<br>
	 * <br>
	 * Ex)<br>
	 * getTorikomiDt();　戻り値　取込日<br>
	 * <br>
	 * @return String 取込日
	 */
	public String getTorikomiDt() {
		return torikomi_dt;
	}

	/**
	 * 取込日<br>
	 * <br>
	 * Ex)<br>
	 * setTorikomiDt(String);<br>
	 * <br>
	 * @param String 取込日
	 */
	public void setTorikomiDt(String torikomi_dt) {
		this.torikomi_dt = torikomi_dt;
	}
	
	/**
	 * EXCELファイル種別<br>
	 * <br>
	 * Ex)<br>
	 * getExcelFileSyubetsu();　戻り値　EXCELファイル種別<br>
	 * <br>
	 * @return String EXCELファイル種別
	 */
	public String getExcelFileSyubetsu() {
		return excel_file_syubetsu;
	}

	/**
	 * EXCELファイル種別<br>
	 * <br>
	 * Ex)<br>
	 * setExcelFileSyubetsu(String);<br>
	 * <br>
	 * @param String EXCELファイル種別
	 */
	public void setExcelFileSyubetsu(String excel_file_syubetsu) {
		this.excel_file_syubetsu = excel_file_syubetsu;
	}
	
	/**
	 * 受付ファイル№<br>
	 * <br>
	 * Ex)<br>
	 * getUketsukeNo();　戻り値　受付ファイル№<br>
	 * <br>
	 * @return long 受付ファイル№
	 */
	public long getUketsukeNo() {
		return uketsuke_no;
	}

	/**
	 * 受付ファイル№<br>
	 * <br>
	 * Ex)<br>
	 * setUketsukeNo(String);<br>
	 * <br>
	 * @param long 受付ファイル№
	 */
	public void setUketsukeNo(long uketsuke_no) {
		this.uketsuke_no = uketsuke_no;
	}
	
	/**
	 * EXCELファイル名<br>
	 * <br>
	 * Ex)<br>
	 * getExcelFileNa();　戻り値　EXCELファイル名<br>
	 * <br>
	 * @return String EXCELファイル名
	 */
	public String getExcelFileNa() {
		return excel_file_na;
	}

	/**
	 * EXCELファイル名<br>
	 * <br>
	 * Ex)<br>
	 * setExcelFileNa(String);<br>
	 * <br>
	 * @param String EXCELファイル名
	 */
	public void setExcelFileNa(String excel_file_na) {
		this.excel_file_na = excel_file_na;
	}
	
	/**
	 * アップロードコメント<br>
	 * <br>
	 * Ex)<br>
	 * getUploadCommentTx();　戻り値　アップロードコメント<br>
	 * <br>
	 * @return String アップロードコメント
	 */
	public String getUploadCommentTx() {
		return upload_comment_tx;
	}

	/**
	 * アップロードコメント<br>
	 * <br>
	 * Ex)<br>
	 * setUploadCommentTx(String);<br>
	 * <br>
	 * @param String アップロードコメント
	 */
	public void setUploadCommentTx(String upload_comment_tx) {
		this.upload_comment_tx = upload_comment_tx;
	}
	
	/**
	 * 登録承認フラグ<br>
	 * <br>
	 * Ex)<br>
	 * getTorokuSyoninFg();　戻り値　登録承認フラグ<br>
	 * <br>
	 * @return String 登録承認フラグ
	 */
	public String getTorokuSyoninFg() {
		return toroku_syonin_fg;
	}

	/**
	 * 登録承認フラグ<br>
	 * <br>
	 * Ex)<br>
	 * setTorokuSyoninFg(String);<br>
	 * <br>
	 * @param String 登録承認フラグ
	 */
	public void setTorokuSyoninFg(String toroku_syonin_fg) {
		this.toroku_syonin_fg = toroku_syonin_fg;
	}
	
	/**
	 * 登録承認日<br>
	 * <br>
	 * Ex)<br>
	 * getTorokuSyoninTs();　戻り値　登録承認日<br>
	 * <br>
	 * @return String 登録承認日
	 */
	public String getTorokuSyoninTs() {
		return toroku_syonin_ts;
	}

	/**
	 * 登録承認日<br>
	 * <br>
	 * Ex)<br>
	 * setTorokuSyoninTs(String);<br>
	 * <br>
	 * @param String 登録承認日
	 */
	public void setTorokuSyoninTs(String toroku_syonin_ts) {
		this.toroku_syonin_ts = toroku_syonin_ts;
	}
	
	/**
	 * 登録票処理状態フラグ<br>
	 * <br>
	 * Ex)<br>
	 * getSyoriJyotaiFg();　戻り値　登録票処理状態フラグ<br>
	 * <br>
	 * @return String 登録票処理状態フラグ
	 */
	public String getSyoriJyotaiFg() {
		return syori_jyotai_fg;
	}

	/**
	 * 登録票処理状態フラグ<br>
	 * <br>
	 * Ex)<br>
	 * setSyoriJyotaiFg(String);<br>
	 * <br>
	 * @param String 登録票処理状態フラグ
	 */
	public void setSyoriJyotaiFg(String syori_jyotai_fg) {
		this.syori_jyotai_fg = syori_jyotai_fg;
	}
	
	/**
	 * 登録票確認フラグ<br>
	 * <br>
	 * Ex)<br>
	 * getKakuninFg();　戻り値　登録票確認フラグ<br>
	 * <br>
	 * @return String 登録票処理状態フラグ
	 */
	public String getKakuninFg() {
		return kakunin_fg;
	}

	/**
	 * 登録票確認フラグ<br>
	 * <br>
	 * Ex)<br>
	 * setKakuninFg(String);<br>
	 * <br>
	 * @param String 登録票確認フラグ
	 */
	public void setKakuninFg(String kakunin_fg) {
		this.kakunin_fg = kakunin_fg;
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
	 * 表示ページ番号に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCurrentPageNum();　戻り値　ページ番号<br>
	 * <br>
	 * @return int ページ番号
	 */
	public int getCurrentPageNum() {
		return currentPageNum;
	}

	/**
	 * 表示ページ番号に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCurrentPageNum(int);<br>
	 * <br>
	 * @param int 設定するページ番号
	 */
	public void setCurrentPageNum(int i) {
		currentPageNum = i;
	}

	/**
	 * 最終ページ番号に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLastPageNum();　戻り値　最終ページ番号<br>
	 * <br>
	 * @return int ページ番号
	 */
	public int getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * 最終ページ番号に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLastPageNum(int);<br>
	 * <br>
	 * @param int 最終ページ番号
	 */
	public void setLastPageNum(int i) {
		lastPageNum = i;
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
//  ↑↑2006.06.23 baozy カスタマイズ修正↑↑

	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max )
	{
		if( base == null ) return null;
		String wk = "";
		for( int pos = 0,count = 0; pos < max && pos < base.length(); pos++ )
		{
			try
			{
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			}
			catch(Exception e)
			{
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}

}
