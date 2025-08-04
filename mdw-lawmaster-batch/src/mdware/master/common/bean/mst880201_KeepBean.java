/**
 * <p>タイトル	：新ＭＤシステム（マスタ管理）ギフトマスタの画面表示データ格納用クラス</p>
 * <p>説明: 	：新ＭＤシステムで使用するギフトマスタの画面表示データ格納用クラス</p>
 * <p>著作権: 	：Copyright (c) 2005</p>
 * <p>会社名: 	：Vinculum Japan Corp.</p>
 * @author 	：VJC A.Tozaki
 * @version 	：1.0(2005/11/10)初版作成
 * 更新履歴		：2006-04-13 M.Kawamoto 
 * 				  1.画面項目見直しによる全面改訂
 *   			　2006-04-24 M.Kawamoto
 * 				  2.登録者、更新者表示対応
 * 				  ユーザ名が取得できた場合はユーザ名を表示
 * 				  取得できなかった場合は、ログインＩＤを表示
 */
package mdware.master.common.bean;

import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.log.StcLog;


public class mst880201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	
	private List	meisai					= new ArrayList();	// 検索結果（明細）

	private String ProcessingDivision		= "0";				// 処理区分
	private String ErrorFlg				= "";				// エラーフラグ
	private String ErrorMessage			= "";				// エラーメッセージ
	private String Mode					= "";				// 処理モード 1:検索、2:確定 3:戻る 5:名称取得 6:全て選択　7:全て解除
	private String[] MenuBar				= null;			// メニューバーアイテム
	private Map CtrlColor					= new HashMap();	// コントロール前景色
	private String FirstFocus				= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg				= "";				// 新規処理利用可能区分
	private String UpdateFlg				= "";				// 更新処理利用可能区分
	private String DeleteFlg				= "";				// 削除処理利用可能区分
	private String ReferenceFlg			= "";				// 照会処理利用可能区分
	private String CsvFlg					= "";				// CSV処理利用可能区分
	private String PrintFlg				= "";				// 印刷処理利用可能区分
	private String CheckFlg			    = "";				// チェック処理判断
	private String DispUrl					= "";			// 現画面URL
			
	private String updateProcessFlg 		= "";				// 更新処理内容
	private String insert_ts 				= "";				// 作成年月日
	private String insert_user_id 			= "";			// 作成者社員ID
	private String insert_user_nm 			= "";			// 作成者社員名
	private String update_ts 				= "";				// 更新年月日
	private String update_user_id 			= "";			// 更新者社員ID
	private String update_user_nm 			= "";			// 更新者社員名
	
	private String hinsyu_cd_from			= "";				// 品種コード(from)
	private String hinsyu_cd_to			= "";				// 品種コード(to)
	private String hinsyu_na_from			= "";				// 品種名(from)
	private String hinsyu_na_to			= "";				// 品種名(to)
	private String gift_cd					= "";			// ギフトコード
	private String mst_date				= "";				// マスタ管理日付
	private String mst_date_next			= "";				// マスタ管理日付＋１日

	private String listChgFlg				= "";				// 詳細変更フラグ

	private int currentPageNum			= 0;				// 表示ページ番号
	private int lastPageNum 				= 0;				// 最終ページ番号
	private int maxRows 					= 0;				// 取得件数
	private int startRowInPage 			= 0;				// 表示開始位置
	private int endRowInPage 				= 0;				// 表示終了位置

	//2006-04-12 M.Kawamoto　追加
	private String select_kb				= "0";				// 選択コード区分（デフォルトは、0：選択なしとする）
	
	private String select_cd				= "";				// 選択コード(品種、販区)
	private String syohin_cd				= "";				// 商品コード(品種、販区)
	private String select_na				= "";				// 選択コード区分、選択コードに対応した名前
	
	private String hanei_date				= "";				// ＰＬＵ反映日

	private String syohin_na_size			= "";				// 商品名サイズ
	//
	
	// Processingdivisionに対するセッターとゲッターの集合
	public boolean setProcessingDivision(String ProcessingDivision)
	{
		this.ProcessingDivision = ProcessingDivision;
		return true;
	}
	public String getProcessingDivision()
	{
		return this.ProcessingDivision;
	}

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

	// Modeに対するセッターとゲッターの集合
	public boolean setMode(String Mode)
	{
		this.Mode = Mode;
		return true;
	}
	public String getMode()
	{
		return this.Mode;
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

	// CtrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map CtrlColor)
	{
		this.CtrlColor = CtrlColor;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.CtrlColor;
	}

	// FirstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String FirstFocus)
	{
		this.FirstFocus = FirstFocus;
		return true;
	}
	public String getFirstFocus()
	{
		return this.FirstFocus;
	}

	// InsertFlgに対するセッターとゲッターの集合
	public boolean setInsertFlg(String InsertFlg)
	{
		this.InsertFlg = InsertFlg;
		return true;
	}
	public String getInsertFlg()
	{
		return this.InsertFlg;
	}

	// UpdateFlgに対するセッターとゲッターの集合
	public boolean setUpdateFlg(String UpdateFlg)
	{
		this.UpdateFlg = UpdateFlg;
		return true;
	}
	public String getUpdateFlg()
	{
		return this.UpdateFlg;
	}

	// DeleteFlgに対するセッターとゲッターの集合
	public boolean setDeleteFlg(String DeleteFlg)
	{
		this.DeleteFlg = DeleteFlg;
		return true;
	}
	public String getDeleteFlg()
	{
		return this.DeleteFlg;
	}

	// ReferenceFlgに対するセッターとゲッターの集合
	public boolean setReferenceFlg(String ReferenceFlg)
	{
		this.ReferenceFlg = ReferenceFlg;
		return true;
	}
	public String getReferenceFlg()
	{
		return this.ReferenceFlg;
	}

	// CsvFlgに対するセッターとゲッターの集合
	public boolean setCsvFlg(String CsvFlg)
	{
		this.CsvFlg = CsvFlg;
		return true;
	}
	public String getCsvFlg()
	{
		return this.CsvFlg;
	}

	// PrintFlgに対するセッターとゲッターの集合
	public boolean setPrintFlg(String PrintFlg)
	{
		this.PrintFlg = PrintFlg;
		return true;
	}
	public String getPrintFlg()
	{
		return this.PrintFlg;
	}
	
	// CheckFlgに対するセッターとゲッターの集合
	public boolean setCheckFlg(String CheckFlg)
	{
		this.CheckFlg = CheckFlg;
		return true;
	}
	public String getCheckFlg()
	{
		return this.CheckFlg;
	}

	// hinsyu_cd_fromに対するセッターとゲッターの集合
	public boolean setHinsyuCdFrom(String hinsyu_cd_from)
	{
		this.hinsyu_cd_from = hinsyu_cd_from;
		return true;
	}
	public String getHinsyuCdFrom()
	{
		return this.hinsyu_cd_from;
	}

	// hinsyu_na_fromに対するセッターとゲッターの集合
	public boolean setHinsyuNaFrom(String hinsyu_na_from)
	{
		this.hinsyu_na_from = hinsyu_na_from;
		return true;
	}
	public String getHinsyuNaFrom()
	{
		return this.hinsyu_na_from;
	}

	// hinsyu_cd_toに対するセッターとゲッターの集合
	public boolean setHinsyuCdTo(String hinsyu_cd_to)
	{
		this.hinsyu_cd_to = hinsyu_cd_to;
		return true;
	}
	public String getHinsyuCdTo()
	{
		return this.hinsyu_cd_to;
	}

	// hinsyu_na_toに対するセッターとゲッターの集合
	public boolean setHinsyuNaTo(String hinsyu_na_to)
	{
		this.hinsyu_na_to = hinsyu_na_to;
		return true;
	}
	public String getHinsyuNaTo()
	{
		return this.hinsyu_na_to;
	}

	// gift_cdに対するセッターとゲッターの集合
	public boolean setGiftCd(String gift_cd)
	{
		this.gift_cd = gift_cd;
		return true;
	}
	public String getGiftCd()
	{
		return this.gift_cd;
	}

	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
	public String getInsertTs()
	{
		return this.insert_ts;
	}

	// insert_user_idに対するセッターとゲッターの集合
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId()
	{
		return this.insert_user_id;
	}

	// insert_user_nmに対するセッターとゲッターの集合
	public boolean setInsertUserNm(String insert_user_nm)
	{
		this.insert_user_nm = insert_user_nm;
		return true;
	}
	public String getInsertUserNm()
	{
		return this.insert_user_nm;
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

	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserId()
	{
		return this.update_user_id;
	}

	// update_user_nmに対するセッターとゲッターの集合
	public boolean setUpdateUserNm(String update_user_nm)
	{
		this.update_user_nm = update_user_nm;
		return true;
	}
	public String getUpdateUserNm()
	{
		return this.update_user_nm;
	}
	
	// updateProcessFlgに対するセッターとゲッターの集合
	public boolean setUpdateProcessFlg(String updateProcessFlg)	
	{
		this.updateProcessFlg = updateProcessFlg;
		return true;
	}
	public String getUpdateProcessFlg()
	{
		return this.updateProcessFlg;
	}
		
	// mst_dateに対するセッターとゲッターの集合
	public boolean setMstDate(String mst_date)	
	{
		this.mst_date = mst_date;
		return true;
	}
	public String getMstDate()
	{
		return this.mst_date;
	}

	// mst_date_nextに対するセッターとゲッターの集合
	public boolean setMstDateNext(String mst_date_next)	
	{
		this.mst_date_next = mst_date_next;
		return true;
	}
	public String getMstDateNext()
	{
		return this.mst_date_next;
	}

	// listChgFlgに対するセッターとゲッターの集合
	public boolean setListChgFlg(String listChgFlg)	
	{
		this.listChgFlg = listChgFlg;
		return true;
	}
	public String getListChgFlg()
	{
		return this.listChgFlg;
	}

	// meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(List meisai)	
	{
		this.meisai = meisai;
		return true;
	}
	public List getMeisai()
	{
		return this.meisai;
	}

	// currentPageNumに対するセッターとゲッターの集合
	public boolean setCurrentPageNum(int currentPageNum) 
	{
		this.currentPageNum = currentPageNum;
		return true;
	}
	public int getCurrentPageNum() 
	{
		return currentPageNum;
	}

	// lastPageNumに対するセッターとゲッターの集合
	public boolean setLastPageNum(int lastPageNum) 
	{
		this.lastPageNum = lastPageNum;
		return true;
	}
	public int getLastPageNum() 
	{
		return lastPageNum;
	}

	// maxRowsに対するセッターとゲッターの集合
	public boolean setMaxRows(int maxRows) 
	{
		this.maxRows = maxRows;
		return true;
	}
	public int getMaxRows() 
	{
		return maxRows;
	}

	// startRowInPageに対するセッターとゲッターの集合
	public boolean setStartRowInPage(int startRowInPage) 
	{
		this.startRowInPage = startRowInPage;
		return true;
	}
	public int getStartRowInPage() 
	{
		return startRowInPage;
	}

	// endRowInPageに対するセッターとゲッターの集合
	public boolean setEndRowInPage(int endRowInPage) 
	{
		this.endRowInPage = endRowInPage;
		return true;
	}
	public int getEndRowInPage() 
	{
		return endRowInPage;
	}

	// dispUrlに対するセッターとゲッターの集合
	public boolean setDispUrl(String dispUrl) 
	{
		DispUrl = dispUrl;
		return true;
	}
	public String getDispUrl() {
		return DispUrl;
	}

	/**
	 * 選択コード区分を取得します。
	 * 
	 * @return　選択コード区分
	 */
	public String getSelectkb() {
		return this.select_kb;
	}
	
	/**
	 * 選択コードをセットします。
	 * @param selectkb　セットする選択コード
	 */
	public void setSelectkb(String selectkb) {
		this.select_kb = selectkb;
	}

	/**
	 * 選択コード（品種、販区）を取得します。
	 * 
	 * @return　選択コード（品種、販区）
	 */
	public String getSelectcd() {
		return this.select_cd;
	}

	/**
	 * 選択コード（品種、販区）をセットします。
	 * 
	 * @param selectcd 選択コード
	 */
	public void setSelectcd(String selectcd) {
		this.select_cd = selectcd;
	}

	/**
	 * 選択区分、選択コードに対応する名前を取得します。
	 * 
	 * @return 選択区分、選択コードに対応した名前
	 */
	public String getSelectna() {
		return select_na;
	}

	/**
	 * 選択区分、選択コードに対応する名前をセットします。
	 * 
	 * @param selectna 選択区分、選択コードに対応した名前
	 */
	public void setSelectna(String selectna) {
		this.select_na = selectna;
	}
	
	/**
	 * 選択コード（商品）を取得します。
	 * 
	 * @return 選択コード（商品）
	 */
	public String getSyohincd() {
		return syohin_cd;
	}

	/**
	 * 選択コード（商品）をセットします。
	 * 
	 * @param syohincd 商品コード
	 */
	public void setSyohincd(String syohincd) {
		this.syohin_cd = syohincd;
	}

	/**
	 * PLU反映日を取得します。
	 * @return　PLU反映日
	 */
	public String getHaneidate() {
		return hanei_date;
	}

	/**
	 * PLU反映日をセットします。
	 * @param hanei_date
	 */
	public void setHaneidate(String hanei_date) {
		this.hanei_date = hanei_date;
	}

	/**
	 * 商品名サイズを取得します。
	 * 
	 * @return　商品名サイズ
	 */
	public String getSyohinNasize() {
		return syohin_na_size;
	}

	/**
	 * 商品名サイズをセットします。
	 * 
	 * @param syohin_na_size　商品名サイズ
	 */
	public void setSyohinNasize(String syohin_na_size) {
		this.syohin_na_size = syohin_na_size;
	}

	/**
	 * @return 現在表示中ページ以外の明細が1個以上選択されていればtrueを戻します。
	 */
	public boolean isBackgroundListSelected() {
		boolean isChecked = false;
		for (int i = 0; i < meisai.size(); i++) {
			mst880101_GiftBean bean = (mst880101_GiftBean)meisai.get(i);
			if (getStartRowInPage() - 1 <= i  && i <= getEndRowInPage() - 1) continue;
			
			if (bean.getSentaku() != null && bean.getSentaku().equals(mst000101_ConstDictionary.RECORD_EXISTENCE)){
				isChecked = true;
				break;
			}
		}
		return isChecked;
	}
	


}
