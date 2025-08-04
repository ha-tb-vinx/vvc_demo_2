/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst550501用店別販区マスタの画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst550501用店別販区マスタの画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius Makuta
 * @version 1.0
 * @see なし
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.master.util.*;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst550501用店別販区マスタの画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst550501用店別販区マスタの画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius Makuta
 * @version 1.0
 * @see なし
 */
public class mst550501_SearchBean{
	/**
	 * 
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		DataHolder 	dataHolder
	 * @param		String		kengenCd		: 権限コード
 	 * @param		String 		GamenId 		: 画面ID
	 * @param		String 		FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		String[] 	CtrlName 		: コントロール名
	 * @param		String		tableNa			: 対象テーブル名称
	 * @param		String		columnNa		: 有効日カラム名称
	 * @param		Map			whereList		: 有効日を除くKEY部
	 * @param		String		yukoDt			: 入力有効日
	 * @param		String		addValue		: 暦日加算値
	 */
	public List Search(mst550201_KeepBean Keepb,
								DataHolder dataHolder,
								SessionManager sessionManager,
								mst550401_KeepMeisaiBean Meisai,
								List arrItiranAfter,
								boolean page,
								mst000101_DbmsBean db) throws SQLException {
		try {
			//メッセージ取得
			TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

			//データの取得
			//店別販区マスタ情報の取得用
			mst550101_TenHankuLDM TenHankuDM = new mst550101_TenHankuLDM();			//mst520101_TenpoListBean用店マスタのDMモジュール
			BeanHolder tenpoLbh = new BeanHolder(TenHankuDM);						//mst520101_TenpoListBean用店マスタのBeanHolder

			//Search
			dataHolder.updateParameterValue("mode",
				mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syorikb")).trim());
			dataHolder.updateParameterValue("kanri_kb",
				mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanrikb")).trim());
			dataHolder.updateParameterValue("kanri_cd",
				mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanricd")).trim());

			//データを全件取得する
			tenpoLbh.setRowsInPage(0);
			tenpoLbh.setDataHolder(dataHolder);

			//一覧を取得する
			List arrItiran = (ArrayList)tenpoLbh.getFirstPageBeanList();

			List YukoChkLst = new ArrayList();
			List UpdateProcessLst = new ArrayList();
			if(dataHolder.getParameter("rb_syorikb").equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
				//新規時
				arrItiranAfter = arrItiran;
				YukoChkLst.add(mst000101_ConstDictionary.ERR_CHK_NORMAL);
				UpdateProcessLst.add(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT);
			} else {
				//修正・削除・照会時の処理
				//件数が0件の場合エラー
				if(arrItiran.size() == 0) {
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("0005").toString());
					sessionManager.setAttribute("mst550201_Keep", Keepb);
					return null;
				} else {
					//取得したデータを元に有効日のエラー判定を行う
					for(int intCnt = 0; intCnt < arrItiran.size(); intCnt++) {
						mst550101_TenHankuLBean arrColumn = (mst550101_TenHankuLBean)arrItiran.get(intCnt);
						//有効日の妥当性チェック
						String	tableNa  	  = "r_tenhanku";//テーブル名称
						String	columnNa 	  = "yuko_dt";	 //カラム名称
						String	addValue 	  = "1";			 //暦日加算値
						List whereList = new ArrayList();
						String MSTDATE = "";
						whereList.clear();
						whereList.add( "     KANRI_KB = '" + mst000401_LogicBean.chkNullString(arrColumn.getKanriKb()).trim() + "' " );
						whereList.add( " AND KANRI_CD = '" + mst000401_LogicBean.chkNullString(arrColumn.getKanriCd()).trim() + "' " );
						whereList.add( " AND TENPO_CD = '" + mst000401_LogicBean.chkNullString(arrColumn.getTenpoCd()).trim() + "' " );

// 2006.02.22 Y.Inaba Add ↓
						//有効日未入力時はマスタ日付を使用する
						if(mst000401_LogicBean.chkNullString(Keepb.getYukoDt().trim()).equals("")){
							MSTDATE=RMSTDATEUtil.getMstDateDt();
						} else{
							MSTDATE=Keepb.getYukoDt();
						}
// 2006.02.22 Y.Inaba Add ↑
						//検索&更新処理チェック
						Keepb.setCheckFlg(mst000101_ConstDictionary.CHECK_SEARCH);
// 2006.01.30 Y.Inaba 修正 ↓
//						String chks = mst001001_EffectiveDayBean.getYukoDtCheck( tableNa, columnNa, whereList, addValue,
//														mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")).trim(), Keepb ,db);
//						String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck( tableNa,columnNa,Keepb,db,addValue,true );
						String chks = mst000206_TenhankuEffectiveDayCheckBean.getYukoDtCheck(tableNa,MSTDATE,mst000401_LogicBean.chkNullString(arrColumn.getKanriKb()).trim(),
													mst000401_LogicBean.chkNullString(arrColumn.getKanriCd()).trim(),mst000401_LogicBean.chkNullString(arrColumn.getTenpoCd()).trim(),Keepb,db,addValue);
// 2006.01.30 Y.Inaba 修正 ↑
						//現在有効日を取得する
						String YukoDt = new String();
// 2006.01.30 Y.Inaba 修正 ↓
//						YukoDt = mst001001_EffectiveDayBean.getGenYoyaku(tableNa, columnNa, whereList, addValue, db);
//						YukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db,tableNa,columnNa,whereList,
//									mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")).trim(),false);
						YukoDt = mst000206_TenhankuEffectiveDayCheckBean.getNowYukoDt(db,tableNa,columnNa,whereList,MSTDATE,false);
// 2006.01.30 Y.Inaba 修正 ↓
						//現在有効日を元に明細１件分のデータを取得する
						if (YukoDt.length() != 0 && YukoDt != null) {
							//有効日もキーに含めて検索する
//							dataHolder.updateParameterValue("yuko_dt", YukoDt);
							dataHolder.updateParameterValue("yuko_dt", Keepb.getYukoDt());
							dataHolder.updateParameterValue("kanri_kb",
								mst000401_LogicBean.chkNullString(arrColumn.getKanriKb()).trim());
							dataHolder.updateParameterValue("kanri_cd",
								mst000401_LogicBean.chkNullString(arrColumn.getKanriCd()).trim());
							dataHolder.updateParameterValue("tenpo_cd",
								mst000401_LogicBean.chkNullString(arrColumn.getTenpoCd()).trim());

							List wlst = mst001201_RetrieveBean.getRetrieve( tableNa,columnNa,dataHolder,TenHankuDM,Keepb
																,whereList,Keepb.getYukoDt(),addValue,db );
							//エラー情報を保持する
							YukoChkLst.add(chks);
							//更新処理内容を保持する
							UpdateProcessLst.add(Keepb.getUpdateProcessFlg());
							if (wlst != null) {
								//取得データをセットする
								BeanHolder tenpoLbhWrk = new BeanHolder(TenHankuDM);						//mst520101_TenpoListBean用店マスタのBeanHolder
								tenpoLbhWrk.setDataHolder(dataHolder);
								if (tenpoLbhWrk.getFirstPageBeanList().size() > 0) {
									mst550101_TenHankuLBean wrk = new mst550101_TenHankuLBean();
									wrk = (mst550101_TenHankuLBean)(tenpoLbhWrk.getFirstPageBeanList().get(0));
									wrk.setYukoDtReal(Keepb.getYukoDt());
//									arrItiranAfter.add(tenpoLbhWrk.getFirstPageBeanList().get(0));
									arrItiranAfter.add(wrk);
								}
							}
						}
					}

					if (arrItiranAfter.size() > 0) {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
						Keepb.setErrorMessage(arrItiranAfter.size() + map.get("0029").toString());
					} else {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("0005").toString());
						sessionManager.setAttribute("mst550201_Keep", Keepb);
						return null;
					}
				}
			}
			//明細表示用
			Meisai.setMeisai((ArrayList)arrItiranAfter);
			Meisai.setMeisaiAll((ArrayList)arrItiranAfter);
			Keepb.setYukoChkLstAll(YukoChkLst);
			Keepb.setUpdateProcessLstAll(UpdateProcessLst);

			//ページ手動移動
			//開始位置
			int pageRows = 30;		//表示行数
			int startNo = 0;		//検索開始位置
			int maxRows = arrItiranAfter.size();	//最大行数

			String h_CurrentPageNo = dataHolder.getParameter("h_CurrentPageNo");
			String h_LastPageNo = dataHolder.getParameter("h_LastPageNo");
			String h_MaxRows = dataHolder.getParameter("h_MaxRows");
			String h_EndRowInPage = dataHolder.getParameter("h_EndRowInPage");
			String h_StartRowInPage = dataHolder.getParameter("h_StartRowInPage");

			if (h_CurrentPageNo == null) {
				Meisai.setCurrentPageNo("1");
				Meisai.setStartRowInPage("1");
			} else {
				Meisai.setCurrentPageNo(h_CurrentPageNo);
				Meisai.setStartRowInPage(h_StartRowInPage);
			}
			startNo = Integer.parseInt(Meisai.getStartRowInPage());

			if(arrItiranAfter.size() < pageRows){
				//取得データが１ページ行数に満たない場合
				Meisai.setMaxRows(arrItiranAfter.size() + "");
				Meisai.setLastPageNo("1");
				Meisai.setEndRowInPage(arrItiranAfter.size() + "");
			} else {
				Meisai.setMaxRows(maxRows + "");
				Meisai.setLastPageNo((Integer.parseInt(Meisai.getMaxRows()) / pageRows) + "");
				if(Integer.parseInt(Meisai.getMaxRows()) % pageRows > 0){
					Meisai.setLastPageNo("" + (Integer.parseInt(Meisai.getLastPageNo())+1) );
				}
				if (Integer.parseInt(Meisai.getCurrentPageNo()) == Integer.parseInt(Meisai.getLastPageNo())) {
					Meisai.setEndRowInPage(Meisai.getMaxRows());
				} else {
					Meisai.setEndRowInPage("" + (Integer.parseInt(Meisai.getStartRowInPage()) + pageRows - 1));
				}
				//表示分の明細をセットする
				if (page) {
					List arrItiranTmp = new ArrayList();
					List YukoChkLstTmp = new ArrayList();
					List UpdateProcessLstTmp = new ArrayList();
					for (int i = Integer.parseInt(Meisai.getStartRowInPage()) - 1; i < Integer.parseInt(Meisai.getEndRowInPage()); i++) {
						arrItiranTmp.add(arrItiranAfter.get(i));
						YukoChkLstTmp.add(YukoChkLst.get(i));
						UpdateProcessLstTmp.add(UpdateProcessLst.get(i));
					}
					arrItiranAfter = arrItiranTmp;
					YukoChkLst = YukoChkLstTmp;
					UpdateProcessLst = UpdateProcessLstTmp;
				}
			}
			//明細表示用
			Meisai.setMeisai((ArrayList)arrItiranAfter);

			//初期フォーカス位置
			if (Keepb.isSeisen()) {
				Keepb.setFirstFocus("rb_kanrikb[0]");
			} else {
				Keepb.setFirstFocus("rb_kanrikb[1]");
			}

			//ページング
			BeanHolder tenpoLbhAfter = new BeanHolder(TenHankuDM);						//mst520101_TenpoListBean用店マスタのBeanHolder
			tenpoLbhAfter.setRowsInPage(30);
			tenpoLbhAfter.setCacheBeanList((ArrayList)arrItiranAfter);

			Keepb.setYukoChkLst(YukoChkLst);
			Keepb.setUpdateProcessLst(UpdateProcessLst);
		} catch(SQLException sqle) {
			throw sqle;
		} catch(Exception e) {
			throw e;
		} finally {
			return arrItiranAfter;
		}
	}
}