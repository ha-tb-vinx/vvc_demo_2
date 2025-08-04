/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）旧・新商品コード変換マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する旧・新商品コード変換マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/29)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.master.common.dictionary.*;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）旧・新商品コード変換マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する旧・新商品コード変換マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/29)初版作成
 */
public class mst230401_CheckBean
{
	
	/**
	 * 
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		mst000101_DbmsBean	db               : DB
	 * @param 		mst230201_KeepBean Keepb             : KeepBeen
	 * @param 		mst230301_KeepMeisaiBean KeepbMeisai : KeepBeen
	 * @param 		DataHolder 	dataHolder               : DataHolder
	 * @param		String		kengenCd		         : 権限コード
	 * @param		String 		GamenId 		         : 画面ID
	 * @param		String 		FirstFocusCtl            : 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		Map CtrlColor           　           : コントロール背景色
	 * @param		SessionManager sessionManager        : SessionManager 
	 */	
/*
	public void Check(mst000101_DbmsBean	db, mst230201_KeepBean Keepb , mst230301_KeepMeisaiBean KeepbMeisai ,DataHolder dataHolder ,String kengenCd ,String GamenId ,
					   String FirstFocusCtl ,Map CtrlColor, SessionManager sessionManager ) throws Exception,SQLException{
		//----------------------------
		//初期処理
		//----------------------------
		//マスタ日付取得
		String MasterDT = RMSTDATEUtil.getMstDateDt();

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		//エラーコード・メッセージの初期化
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		//----------------------------
		//KeepBean値設定
		//----------------------------
		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//画面内容を保存
			Keepb.setHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_cd")).trim());	//販区コード
			Keepb.setHankuNm(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_nm")).trim()));	//販区名称
			Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin_cd")).trim());	//商品コード
			Keepb.setSyohinNm(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin_nm")).trim()));	//商品名称
			//処理状況
			if (dataHolder.getParameter("ct_syorikb") == null) {
				Keepb.setProcessingDivision(mst000101_ConstDictionary.PROCESS_REFERENCE);
			} else {
				Keepb.setProcessingDivision(dataHolder.getParameter("ct_syorikb"));//処理状況
			}
			Keepb.setChgFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("mst23_chgflg")).trim());//画面変更フラグ

			//画面内容を保存（明細）
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) || 
			   Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS) ){

				//新規・修正の場合				
				if(Keepb.getMode().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
				   Keepb.getMode().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
					for (int i = 0; i < KeepbMeisai.getMeisai().size(); i++) {
						mst230101_SyohinConvertBean arrColumn = (mst230101_SyohinConvertBean)KeepbMeisai.getMeisai().get(i);

						//選択
						String setnaku = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku" + i)).trim();
						arrColumn.setSentaku(setnaku);

						//販区コード（変換後）
						String hanku_after_cd = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_after_cd" + i)).trim();
						arrColumn.setHankuAfterCd(hanku_after_cd);

						//販区コード名称（変換後）
						String hanku_after_nm = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_after_nm" + i)).trim();
						arrColumn.setHankuAfterNm(hanku_after_nm);

						//商品コード（変換後）
						String syohin_after_cd = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin_after_cd" + i)).trim();
						arrColumn.setSyohinAfterCd(syohin_after_cd);

						//商品コード名称（変換後）
						String syohin_after_nm = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin_after_nm" + i)).trim();
						arrColumn.setSyohinAfterNm(syohin_after_nm);
					}
				}

				//削除の場合
				if(Keepb.getMode().equals(mst000101_ConstDictionary.PROCESS_DELETE)){
					//----------------------
					//データ取得用変数
					//----------------------
					List lst = new ArrayList();	//マスタ存在チェック抽出条件
					mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
					mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
					//----------------------
					//販区
					//----------------------
					if(!Keepb.getHankuCd().equals("")){
						lst.clear();
						lst.add("SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");				
						lst.add(" and ");
						lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
						Keepb.setHankuNm(mstchk.getCdName());			

						//データが存在しない場合
						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){

							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus("ct_hanku_cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
							}
						} else {
							if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
								mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus("ct_hanku_cd");
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("40022").toString());											
								}
							}			
						}			
					} else {
						Keepb.setHankuNm("");	
					}

					//----------------------
					//商品
					//----------------------
					if(Keepb.getSyohinCd().indexOf("%") == -1){
						List whereList = new ArrayList();
						whereList.add( " syohin_cd = '" + Keepb.getSyohinCd() + "' " );
						whereList.add( " and " );
// ↓↓仕様変更（2005.09.28）↓↓
//						whereList.add( " yuko_dt <= TO_CHAR(SYSDATE ,'YYYYMMDD') ");
						whereList.add( " yuko_dt <= '" + MasterDT + "' ");
// ↑↑仕様変更（2005.09.28）↑↑
						whereList.add( " and " );					
						whereList.add( " delete_fg = '"+mst000801_DelFlagDictionary.INAI.getCode()+"'");					
// 2006.01.19 Y.Inaba Mod ↓
//						if(!mst001001_EffectiveDayBean.getDateYukoDt(db,"r_syohin","yuko_dt",whereList,"")){
						if(!mst000201_EffectiveDayCheckBean.getDateYukoDt(db,"r_syohin","yuko_dt",whereList,"")){
// 2006.01.19 Y.Inaba Mod ↑
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin_cd");	
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus("ct_syohin_cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("0005").toString());											
							}						
						}
					}
					//商品（名称取得）
					lst.clear();
					lst.add("SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, "1");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setSyohinNm(mstchk.getCdName());			

					for (int i = 0; i < KeepbMeisai.getMeisai().size(); i++) {
						mst230101_SyohinConvertBean arrColumn = (mst230101_SyohinConvertBean)KeepbMeisai.getMeisai().get(i);
						//選択
						String setnaku = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku" + i)).trim();
						arrColumn.setSentaku(setnaku);
					}
				}
			}
			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//メーニューバーアイコン取得
			// SysSosasyaSesson取得
			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
			Map menuMap = new HashMap();
			menuMap.put("gamen_id",GamenId);
			menuMap.put("kengen_cd",kengenCd);
			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
			String[] menu = mst000401_LogicBean.getCommonMenubar(db, menuMap);
			Keepb.setMenuBar(menu);

			//----------------------------
			//検索時エラーチェック
			//----------------------------
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH)){
				//----------------------
				//データ取得用変数
				//----------------------
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール

				//----------------------
				//販区
				//----------------------
				if(!Keepb.getHankuCd().equals("")){
					lst.clear();
					lst.add("SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");				
					lst.add(" and ");
					lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setHankuNm(mstchk.getCdName());

					//データが存在しない場合
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_hanku_cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
						}
					} else {
						if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus("ct_hanku_cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("40022").toString());											
							}
						}			
					}			
				} else {
					Keepb.setHankuNm("");	
				}

				//----------------------
				//商品
				//----------------------
				if(Keepb.getSyohinCd().indexOf("%") == -1){
					List whereList = new ArrayList();
					whereList.add( " syohin_cd = '" + Keepb.getSyohinCd() + "' " );
					whereList.add( " and " );
// ↓↓仕様変更（2005.09.28）↓↓
//					whereList.add( " yuko_dt <= TO_CHAR(SYSDATE ,'YYYYMMDD') ");
					whereList.add( " yuko_dt <= '" + MasterDT + "' ");
// ↑↑仕様変更（2005.09.28）↑↑
					whereList.add( " and " );					
					whereList.add( " delete_fg = '"+mst000801_DelFlagDictionary.INAI.getCode()+"'");					
//					if(!mst001001_EffectiveDayBean.getDateYukoDt(db,"r_syohin","yuko_dt",whereList,"")){
					if(!mst000201_EffectiveDayCheckBean.getDateYukoDt(db,"r_syohin","yuko_dt",whereList,"")){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin_cd");	
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_syohin_cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("0005").toString());											
						}						
					}
				}
				//商品（名称取得）
				lst.clear();
				lst.add("SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, "1");
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, "1", "");
// 2006.01.24 Y.Inaba Mod ↑
				Keepb.setSyohinNm(mstchk.getCdName());			

			//----------------------------
			//更新時エラーチェック
			//----------------------------
			} else if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){
				//新規・修正の場合				
				if(Keepb.getMode().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
				   Keepb.getMode().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
					//----------------------
					//データ取得用変数
					//----------------------
					List lst = new ArrayList();	//マスタ存在チェック抽出条件
					mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
					mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール

					//----------------------
					//販区
					//----------------------
					if(!Keepb.getHankuCd().equals("")){
						lst.clear();
						lst.add("SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");				
						lst.add(" and ");
						lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
						Keepb.setHankuNm(mstchk.getCdName());			

						//データが存在しない場合
						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus("ct_hanku_cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
							}
						} else {
							if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
								mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus("ct_hanku_cd");
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("40022").toString());											
								}
							}			
						}			
					} else {
						Keepb.setHankuNm("");	
					}

					//----------------------
					//商品
					//----------------------
					if(Keepb.getSyohinCd().indexOf("%") == -1){
						List whereList = new ArrayList();
						whereList.add( " syohin_cd = '" + Keepb.getSyohinCd() + "' " );
						whereList.add( " and " );
// ↓↓仕様変更（2005.09.28）↓↓
//						whereList.add( " yuko_dt <= TO_CHAR(SYSDATE ,'YYYYMMDD') ");
						whereList.add( " yuko_dt <= '" + MasterDT + "' ");
// ↑↑仕様変更（2005.09.28）↑↑
						whereList.add( " and " );					
						whereList.add( " delete_fg = '"+mst000801_DelFlagDictionary.INAI.getCode()+"'");					
//						if(!mst001001_EffectiveDayBean.getDateYukoDt(db,"r_syohin","yuko_dt",whereList,"")){
						if(!mst000201_EffectiveDayCheckBean.getDateYukoDt(db,"r_syohin","yuko_dt",whereList,"")){
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");	
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin_cd");	
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus("ct_syohin_cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("0005").toString());											
							}						
						}
					}
					//商品（名称取得）
					lst.clear();
					lst.add("SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, "1");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, "1", "");
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setSyohinNm(mstchk.getCdName());
			
					for (int i = 0; i < KeepbMeisai.getMeisai().size(); i++) {
						mst230101_SyohinConvertBean arrColumn = (mst230101_SyohinConvertBean)KeepbMeisai.getMeisai().get(i);
						//選択されている明細のみチェック処理
						if(arrColumn.getSentaku().equals(mst000101_ConstDictionary.SENTAKU_CHOICE)){
							//販区（変換後）
							lst.clear();
							lst.add("SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");				
							lst.add(" and ");
							lst.add("CODE_CD = '" + arrColumn.getHankuAfterCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//							mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
							mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
							arrColumn.setHankuAfterNm(HTMLStringUtil.reverseToHTML(mstchk.getCdName()));			

							//データが存在しない場合、エラー
							if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
								mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_after_cd" + i);	
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus("ct_hanku_after_cd" + i);
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("指定された販区コード（変換後）"+map.get("40007").toString());
								}
							}			

							//商品（変換後）
							if(!arrColumn.getSyohinAfterCd().equals("")){
								lst.clear();
								lst.add(" HANKU_CD = '" + arrColumn.getHankuAfterCd() + "' ");
								lst.add(" AND SYOHIN_CD = '" + arrColumn.getSyohinAfterCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//								mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, "1");
								mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, "1", "");
// 2006.01.24 Y.Inaba Mod ↑
								arrColumn.setSyohinAfterNm(HTMLStringUtil.reverseToHTML(mstchk.getCdName()));			
							}		

							//データが存在しない場合、エラー
							if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
								mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin_after_cd" + i);	
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus("ct_syohin_after_cd" + i);
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("指定された商品コード（変換後）"+map.get("40007").toString());
								}
							}			
						}
					}
				}
			}
		}
	}
	*/
	
	/**
	 * 
	 * コントロール名取得処理
	 * @param 		int meisaiCnt       : 明細数
	 * @return		String[]  
	 */	
/*
	public String[] getCtrlName(int meisaiCnt){
	
		String[] CtrlName 	  = new String[meisaiCnt * 2 + 2];
		int j=0;
		for (int i=0; i < meisaiCnt; i++) {
			CtrlName[j] 	  = "ct_hanku_after_cd" + i;
			j++;
			CtrlName[j] 	  = "ct_syohin_after_cd" + i;
			j++;
		}
		CtrlName[j] 	  = "ct_hanku_cd";
		CtrlName[j+1] 	  = "ct_syohin_cd";
		return CtrlName;
	}
*/

}
