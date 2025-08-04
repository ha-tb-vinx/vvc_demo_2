/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst660401用自動採番枠品種別マスタの画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst660401用自動採番枠品種別マスタの画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author K.Inamoto
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst660401用自動採番枠品種別マスタの画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst660401用自動採番枠品種別マスタの画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author K.Inamoto
 * @version 1.0
 * @see なし								
 */
public class mst660401_SearchBean{
	/**
	 * 
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		DataHolder 	dataHolder
	 * @param		String		kengenCd		: 権限コード
 	 * @param		String 		GamenId 		: 画面ID
	 * @param		String 		FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ		
	 * @param		String[] 	CtrlName 		: コントロール名 
	 */	

	public String Search(	mst660201_KeepBean Keepb,
							DataHolder dataHolder,
							SessionManager sessionManager,
							mst660301_KeepMeisaiBean Meisai,
							List arrItiranAfter,
							boolean page,
							mst000101_DbmsBean db,
							String logHeader)
				throws SQLException, Exception{
			

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		
		//ログの登録
		StcLog stcLog = StcLog.getInstance();

		try {
			
			stcLog.getLog().info(logHeader + "呼び出されました。");
			
			//初期フォーカス位置
			Keepb.setFirstFocus("tx_tenpocd");	

			//データの取得
			//情報の取得用
			mst660101_TanpinJidoSaibanDM saibanDM = new mst660101_TanpinJidoSaibanDM();//mst660101_TanpinJidoSaibanBean用のDMモジュール
			BeanHolder saibanBH = new BeanHolder(saibanDM);//mst660101_TanpinJidoSaibanBean用のBeanHolder
			mst660101_TanpinJidoSaibanBean saibanBean = new mst660101_TanpinJidoSaibanBean(); 
					
			//データを全件取得する
			saibanBH.setRowsInPage(0);
			saibanBH.setDataHolder(dataHolder);
	
			//一覧を取得する
			List arrItiran = (ArrayList)saibanBH.getFirstPageBeanList();
			
			//一覧のリストをコピー
			arrItiranAfter = arrItiran;

			//件数が0件の場合エラー
			if (arrItiran.size() > 0) {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
				Keepb.setErrorMessage(arrItiranAfter.size() + map.get("0029").toString());
			} else {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage(map.get("0005").toString());
				sessionManager.setAttribute("mst660201_Keep", Keepb);
				stcLog.getLog().error(logHeader + "呼び出し元に戻ります（件数０エラー）");
				return Keepb.getErrorFlg();
			}

			//明細表示用
			Meisai.setMeisaiAll((ArrayList)arrItiran);
	
			//ページ手動移動
			//開始位置
			int pageRows = 30;		//表示行数
			int startNo = 0;		//検索開始位置
			int maxRows = arrItiran.size();	//最大行数

			String h_CurrentPageNo = dataHolder.getParameter("h_CurrentPageNo");//現在のページ
			String h_LastPageNo = dataHolder.getParameter("h_LastPageNo");		//最後のページ
			String h_MaxRows = dataHolder.getParameter("h_MaxRows");			//最大行数
			String h_EndRowInPage = dataHolder.getParameter("h_EndRowInPage");	//全行における現在の最後の行
			String h_StartRowInPage = dataHolder.getParameter("h_StartRowInPage");//全行における現在の最初の行

			//検索ボタン押下時に、ページングに使用するデータを初期化する
			if (dataHolder.getParameter("movepos").equals("") && dataHolder.getParameter("JobID").equals("mst660201_TanpinJidoSaibanRetrieve")) {
				h_CurrentPageNo = null;
				h_LastPageNo = null;
				h_MaxRows = null;
				h_EndRowInPage = null;
				h_StartRowInPage = null;
			}

			if (h_CurrentPageNo == null) {
				//検索ボタン初押下時の処理
				Meisai.setCurrentPageNo("1");
				Meisai.setStartRowInPage("1");
			} else {
				//ページ遷移時の処理
				Meisai.setCurrentPageNo(h_CurrentPageNo);
				Meisai.setStartRowInPage(h_StartRowInPage);
			}
	
			if (arrItiran.size() < pageRows) {
				//取得データが１ページ行数に満たない場合
				Meisai.setMaxRows(arrItiran.size() + "");
				Meisai.setLastPageNo("1");
				Meisai.setEndRowInPage(arrItiran.size() + "");
			} else {
				Meisai.setMaxRows(maxRows + "");
				Meisai.setLastPageNo((Integer.parseInt(Meisai.getMaxRows()) / pageRows) + "");
				if (Integer.parseInt(Meisai.getMaxRows()) % pageRows > 0) {
					Meisai.setLastPageNo("" + (Integer.parseInt(Meisai.getLastPageNo())+1) );
				}
				if (Integer.parseInt(Meisai.getCurrentPageNo()) == Integer.parseInt(Meisai.getLastPageNo())) {
					Meisai.setEndRowInPage(Meisai.getMaxRows());
				} else {
					Meisai.setEndRowInPage("" + (Integer.parseInt(Meisai.getStartRowInPage()) + pageRows - 1));
				}
				//表示分の明細をセットする
				if (page) {
					List arrItiranTmp = new ArrayList();//表示分の明細の一時保存用リスト
					for (int i = Integer.parseInt(Meisai.getStartRowInPage()) - 1; i < Integer.parseInt(Meisai.getEndRowInPage()); i++) {
						arrItiranTmp.add(arrItiran.get(i));//
					}
					arrItiranAfter = arrItiranTmp;
				}
			}
			
			//明細表示用
			Meisai.setMeisai((ArrayList)arrItiranAfter);
			
			//TenpoNaに出力するための値を取得
			for (int i = 0; i < arrItiran.size(); i++) {
				saibanBean = (mst660101_TanpinJidoSaibanBean)arrItiran.get(i);
				if (dataHolder.getParameter("h_kanri_kb").equals(mst000901_KanriKbDictionary.HANKU.getCode())) {
					if(saibanBean.getHankuCdHTMLString().equals(dataHolder.getParameter("h_hanku_cd"))) {
						Keepb.setTenpoNa(saibanBean.getHankuKanjiNa());
						break;
					} else {
						Keepb.setTenpoNa("");
					}
				} else if (dataHolder.getParameter("h_kanri_kb").equals(mst000901_KanriKbDictionary.HINSYU.getCode())) {
					if(saibanBean.getHinsyuCdHTMLString().equals(dataHolder.getParameter("h_hinsyu_cd"))) {
						Keepb.setTenpoNa(saibanBean.getHinsyuKanjiNa());
						break;
					} else {
						Keepb.setTenpoNa("");
					}
				}
			}
			
			//チェックボックスの状態を保存
			ArrayList checkBox = (ArrayList)Keepb.getChkBoxLst();
			if (dataHolder.getParameter("movepos").equals("")) {
				checkBox.clear();
				for (int j = 0; j < arrItiran.size(); j++) {
					checkBox.add("");
				}
			} else {
				for (int k = Integer.parseInt(dataHolder.getParameter("h_StartRowInPage_Prev")) - 1; k < Integer.parseInt(dataHolder.getParameter("h_EndRowInPage_Prev")); k++) {
					if (!dataHolder.getParameter("check_box_flg" + k).equals("")) {
						checkBox.set(k, "true");
					}
				}
			}
			Keepb.setChkBoxLst(checkBox);
			
			//ページング
			BeanHolder saibanBHAfter = new BeanHolder(saibanDM);//mst660101_TanpinJidoSaibanBean用のBeanHolder
			saibanBHAfter.setRowsInPage(30);
			saibanBHAfter.setCacheBeanList((ArrayList)arrItiranAfter);
			
			stcLog.getLog().info(logHeader + "呼び出し元に戻ります（検索終了）");
			return Keepb.getErrorFlg();
			
		} catch(SQLException sqle) {
			stcLog.getLog().fatal(logHeader + map.get("0007") + "呼び出し元に戻ります。");
			stcLog.getLog().fatal(logHeader + sqle.toString());				
			throw sqle;
		} catch(Exception e) {
			stcLog.getLog().error(logHeader + map.get("0007") + "呼び出し元に戻ります。");
			stcLog.getLog().error(logHeader + e.toString());				
			throw e;						
//		} finally {
//			return Keepb.getErrorFlg();
		}				
	}
}