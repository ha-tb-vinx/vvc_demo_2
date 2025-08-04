/**
 * <p>タイトル: 新ＭＤシステム 契約残確認・修正画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する契約残確認・修正画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sunpt
 * @version 1.0(2006/07/10)初版作成
 */
package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.List;

import mdware.master.common.dictionary.mst000101_ConstDictionary;


/**
 * <p>タイトル: 新ＭＤシステム 契約残確認・修正画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する契約残確認・修正画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sunpt
 * @version 1.0(2006/07/10)初版作成
 */
public class mstB00301_KeepMeisaiBean
{
	private List meisai = new ArrayList();//明細リスト
	private int dispRows = 30;			//一画面表示明細数
	private int currentPageNum = 0;	//表示ページ番号
	private int lastPageNum = 0; 		//表示ページ番号
	private int startRowInPage = 0;	
	private int endRowInPage = 0;
	private boolean selectedExcludeCurPage;
	
	public mstB00301_KeepMeisaiBean() {
		this.dispRows = 30;
	}
	
	public mstB00301_KeepMeisaiBean(int dispRows) {
		this.dispRows = dispRows;
	}

	/**
	 * 明細リストを格納
	 * @param Meisai 明細リスト
	 * @return true
	 */
	public void setMeisai(List meisai) {
		this.meisai = meisai;
	}
	
	/**
	 * 明細リストを取得
	 * @param なし
	 * @return 明細リスト
	 */
	public List getMeisai() {
		return this.meisai;
	}
	
	public List getDispMeisai() {
		ArrayList dispMeisai = new ArrayList();
		for (int i = this.startRowInPage - 1; i < this.endRowInPage; i++) {
			dispMeisai.add(this.meisai.get(i));
		}
		return dispMeisai;
	}

	/**
	 * 一画面表示明細行数を取得
	 * @return 一画面表示明細数
	 */
	public int getDispRows() {
		return dispRows;
	}
	
	public int getMaxRows() {
		return this.meisai.size();
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
	
	public void recalculateNum() {
		if (this.meisai == null) {
			this.startRowInPage = 0;
			this.endRowInPage = 0;
			this.currentPageNum = 0;
			this.lastPageNum = 0;
			return;
		}
		this.startRowInPage = this.dispRows * (this.currentPageNum - 1) + 1;
		this.endRowInPage = this.startRowInPage + this.dispRows - 1;
		if (this.meisai.size() < this.endRowInPage) {
			this.endRowInPage = this.meisai.size();
		}
		this.lastPageNum = (this.meisai.size() + this.dispRows - 1) / this.dispRows;
		
		this.selectedExcludeCurPage = false;
		for (int i = 0; i < this.startRowInPage - 2; i++) {
			mstB00101_KeiyakuzanShuseiBean meisaiBean = (mstB00101_KeiyakuzanShuseiBean) this.meisai.get(i);
			if(mst000101_ConstDictionary.RECORD_EXISTENCE.equals(meisaiBean.getSentaku())) {
				this.selectedExcludeCurPage = true;
				break;
			}
		}
		if (!this.selectedExcludeCurPage) {
			for (int i = this.endRowInPage; i < this.meisai.size(); i++) {
				mstB00101_KeiyakuzanShuseiBean meisaiBean = (mstB00101_KeiyakuzanShuseiBean) this.meisai.get(i);
				if(mst000101_ConstDictionary.RECORD_EXISTENCE.equals(meisaiBean.getSentaku())) {
					this.selectedExcludeCurPage = true;
					break;
				}
			}	
		}
	}
	
	public boolean hasSelectedExcludeCurPage() {
		return this.selectedExcludeCurPage;
	}
	
	public void editForDisplay() {
		if (this.meisai == null) return;
		mstB00101_KeiyakuzanShuseiBean lastBean = null;
		mstB00101_KeiyakuzanShuseiBean firstBean = null;
		for(int i = this.startRowInPage - 1; i < this.endRowInPage; i++){
			mstB00101_KeiyakuzanShuseiBean meisaiBean = (mstB00101_KeiyakuzanShuseiBean) meisai.get(i);
			if (lastBean == null) {				
				lastBean = meisaiBean;
				lastBean.setRowSpanCnt(1);
				lastBean.setSpaceCell(false);
				firstBean = lastBean;
			} else {
//仕入先商品コードが同じなら１つのRowSpanにする処理を削除 by kema 06.09.06
//				if (lastBean.getSiirehinban_cd().equals(meisaiBean.getSiirehinban_cd())) {
//					if (i % 10 == 0) {
//						lastBean = meisaiBean;
//						lastBean.setRowSpanCnt(1);
//						lastBean.setSpaceCell(true);
//					} else {
						lastBean = meisaiBean;
						lastBean.setRowSpanCnt(1);
						lastBean.setSpaceCell(false);
						firstBean = lastBean;
//					}
//					} else {
//						lastBean.setRowSpanCnt(lastBean.getRowSpanCnt() + 1);
//						meisaiBean.setRowSpanCnt(1);
//						meisaiBean.setSpaceCell(true);
//					}
					firstBean.setHachu_start_dt(getValidDt(firstBean.getHachu_start_dt(), meisaiBean.getHachu_start_dt(), false));
					firstBean.setHachu_end_dt(getValidDt(firstBean.getHachu_end_dt(), meisaiBean.getHachu_end_dt(), true));
					firstBean.setHanbai_start_dt(getValidDt(firstBean.getHanbai_start_dt(), meisaiBean.getHanbai_start_dt(), false));
					firstBean.setHanbai_end_dt(getValidDt(firstBean.getHanbai_end_dt(), meisaiBean.getHanbai_end_dt(), true));
			}
		}
	}
	
	private String getValidDt(String dtOld, String dtNew, boolean isGetMax) {
		if (dtNew == null || dtNew.trim().length() == 0) {
			return dtOld;
		}
		if (dtOld == null || dtOld.trim().length() == 0) {
			return dtNew;
		}
		if (dtNew.compareTo(dtOld) > 0) {
			return isGetMax ? dtNew : dtOld;
		} else {
			return isGetMax ? dtOld : dtNew;
		}
	}

}
