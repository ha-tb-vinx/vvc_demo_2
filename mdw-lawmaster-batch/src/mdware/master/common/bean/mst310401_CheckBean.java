/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.portal.bean.MdwareUserSessionBean;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst310401_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
	public void Check(
			mst000101_DbmsBean db,
			mst310201_KeepBean Keepb,
			mst310301_KeepMeisaiBean KeepMeisaib,
			DataHolder dataHolder,
//			↓↓2006.06.26 jiangyan カスタマイズ修正↓↓
//			String kengenCd,
//			↑↑2006.06.26 jiangyan カスタマイズ修正↑↑
			String GamenId,
			String FirstFocusCtl,
			String[] CtrlName,
			Map CtrlColor,
			SessionManager  sessionManager) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		//初期Focus
		Keepb.setFirstFocus(FirstFocusCtl);

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)){
				//画面内容を保存
//				↓↓2006.06.21 jiangyan カスタマイズ修正↓↓
//				Keepb.setHankuCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hankucd")).trim() );
				Keepb.setBumonCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).trim() );
				Keepb.setBumonNm( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumonnm")).trim() );
				Keepb.setYukoDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")).trim() );
//				↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
//				Keepb.setHankuKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hankunm")).trim() );
				Keepb.setSyohinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syohincd")).trim() );
				Keepb.setHinmeiKanjiNa( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syohinnm")).trim() );
				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syorikb")).trim() );
// ↓↓仕様変更（2005.07.29）↓↓
//				Keepb.setYukoDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")).trim() );
//				Keepb.setDonyuStDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_donyustdt")).trim() );
// ↑↑仕様変更（2005.07.29）↑↑
//				↑↑2006.06.21 jiangyan カスタマイズ修正↑↑

				//画面内容を保存(明細部)
				ArrayList meisai = KeepMeisaib.getMeisai();
				ArrayList meisaiList = new ArrayList();
				for (int i = 0; i < meisai.size(); i++) {
					mst310101_TanpinTenToriatukaiBean dbBean = (mst310101_TanpinTenToriatukaiBean)meisai.get(i);
					if( dbBean.getDisbale() == null || dbBean.getDisbale().equals("") ){
//						dbBean.setSentaku(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku"+Integer.toString(i))));
// ↓↓仕様変更（2005.07.29）↓↓
//						dbBean.setYukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yukodt"+Integer.toString(i))));
//						dbBean.setDonyuStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_donyustdt"+Integer.toString(i))));
//						dbBean.setDonyuEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_donyueddt"+Integer.toString(i))));
// ↑↑仕様変更（2005.07.29）↑↑

//						↓↓2006.06.21 jiangyan カスタマイズ修正↓↓
//						// 導入開始日範囲チェック生鮮用(マスタ日付より1年後まで入力可能)
//						String[] strReturn = new String[3];
//						if(!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)){
//							// 選択時のみ
//							if( dbBean.getSentaku().equals(mst000101_ConstDictionary.SENTAKU_CHOICE)
//								&&	!dbBean.getDonyuStDt().equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_donyustdt"+Integer.toString(i)))) ){
//								// 導入開始日
//								if(!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_donyustdt"+Integer.toString(i))).equals("")){
//									strReturn = mst000401_LogicBean.getYukoDtRangeCheckFre(db, mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_donyustdt"+Integer.toString(i))));
//									if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
//										mst000401_LogicBean.setErrorBackColor( CtrlColor, "ct_donyustdt"+Integer.toString(i) );
//										if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//											Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//											Keepb.setFirstFocus("ct_donyustdt"+Integer.toString(i));
//											mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_donyustdt"+Integer.toString(i));
//											Keepb.setErrorMessage("導入開始日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
//										}
//									}
//								}
//							}
//						}
						// 有効日範囲チェック生鮮用(マスタ日付より1年後まで入力可能)
						String[] strReturn = new String[3];
						if(!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)){
							// 選択時のみ
							if( dbBean.getSentaku().equals(mst000101_ConstDictionary.SENTAKU_CHOICE)
								&&!dbBean.getDonyuStDt().equals(dbBean.getDonyuStDtBefore()) ){
								// 導入開始日
								if(!mst000401_LogicBean.chkNullString(dbBean.getDonyuStDt()).equals("")){
									strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, mst000401_LogicBean.chkNullString(dbBean.getDonyuStDt()));
									if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
										mst000401_LogicBean.setErrorBackColor( CtrlColor, "ct_donyustdt"+Integer.toString(i) );
										if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
											Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
											Keepb.setFirstFocus("ct_donyustdt"+Integer.toString(i));
											Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
										}
									}
								}
							}
						}
//						↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
					}
					meisaiList.add(dbBean);
				}

				ArrayList meisaiListResult = new ArrayList();
				if (!Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_ERROR)) {
					for (int j = 0; j < meisaiList.size(); j++) {
						mst310101_TanpinTenToriatukaiBean dbChkBean = (mst310101_TanpinTenToriatukaiBean)meisaiList.get(j);
						dbChkBean.setDonyuStDtBefore(mst000401_LogicBean.chkNullString(dbChkBean.getDonyuStDt()));
						meisaiListResult.add(dbChkBean);
					}
				} else {
					meisaiListResult = meisaiList;
				}
//				KeepMeisaib.setMeisai(meisaiList);
				KeepMeisaib.setMeisai(meisaiListResult);
			}

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//メーニューバーアイコン取得
			// userSession取得
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);

			//エラーチェック
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

			//検索条件チェック-----------------------------------------------------------------------------
//			// 有効日範囲チェック(マスタ日付より1年後まで入力可能)
//			// 照会時はチェックを行わない
//			String[] strReturn = new String[3];
//			if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
//			↓↓2006.06.21 jiangyan カスタマイズ修正↓↓
//			if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && ((Keepb.getDonyuStDt() != null)) && (!Keepb.getDonyuStDt().trim().equals(""))){

			String CtlName = "tx_syohincd";
//		    ↓↓2006.06.20 jiangyan カスタマイズ修正↓↓
			//販区
//			if(!Keepb.getHankuCd().equals("")){
//				HankuCheck( db, Keepb, CtrlColor, sessionManager, map, 0 );
//			} else {
//				Keepb.setHankuKanjiRn("");
//			}

			//部門コード存在チェック
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).equals("")){
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)  + "' ");
				lst.add(" and ");
				lst.add("CODE_CD = '"  + mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("ct_bumoncd"), 4, "0", true)) + "' ");
//			    ↑↑2006.06.20 jiangyan カスタマイズ修正↑↑
				// 2006.01.24 Y.Inaba Mod ↓
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
			}
            //部門コード存在エラー
			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_bumoncd");
				Keepb.setBumonNm("");
				Keepb.setFirstFocus("ct_bumoncd");
				Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
			} else {
				Keepb.setBumonNm( mstchk.getCdName() );
			}
			//商品コード
			CtlName = "tx_syohincd";
//			if( Keepb.getHankuCd().equals("") ){
//				//商品コードのみの存在チェック
//				lst.clear();
//				lst.add("syohin_cd = '" + Keepb.getSyohinCd() + "' ");
//				lst.add(" and ");
//				lst.add("gyosyu_kb = '" + mst000601_GyoshuKbDictionary.FRE.getCode() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","hinmei_kanji_na", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1);
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","hinmei_kanji_na", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1, "");
// 2006.01.24 Y.Inaba Mod ↑
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//商品コード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"");
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された商品コード"+map.get("40007").toString());
//						Keepb.setErrorMessage(map.get("40026").toString());
//					}
//				} else {
//					↓↓2006.06.21 jiangyan カスタマイズ修正↓↓
					//商品に対する販区のチェック
//					String syohin_hanku = mst000401_LogicBean.getSyohinCd2HankuCd(db,Keepb.getSyohinCd(),mst000601_GyoshuKbDictionary.FRE.getCode());
//					if(syohin_hanku == null){
//						//複数の販区が存在するエラー
//						mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus( CtlName );
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage("販区コード"+map.get("0021").toString());
//						}
//					} else {
//							Keepb.setHankuCd(syohin_hanku);
//							HankuCheck( db, Keepb, CtrlColor, sessionManager, map, 1 );
//					}
//				}
//				↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
//			}
			//商品の存在チェック
			lst.clear();
//			↓↓2006.06.21 jiangyan カスタマイズ修正↓↓
//			lst.add("hanku_cd = '" + Keepb.getHankuCd() + "' ");
			lst.add("bumon_cd = '"  + mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("ct_bumoncd"), 4, "0", true)) + "' ");
//			↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
			lst.add(" and ");
			lst.add("syohin_cd = '" + Keepb.getSyohinCd() + "' ");
			lst.add(" and ");
			lst.add("gyosyu_kb = '" + mst000601_GyoshuKbDictionary.FRE.getCode() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","hinmei_kanji_na", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1 );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","hinmei_kanji_na", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1, "" );
// 2006.01.24 Y.Inaba Mod ↑
			Keepb.setHinmeiKanjiNa( mstchk.getCdName() );
			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				//商品コード存在エラー
				mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus( CtlName );
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("40026").toString());
				}
			}
			if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && ((Keepb.getYukoDt() != null)) && (!Keepb.getYukoDt().trim().equals(""))){
//				↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
					// 有効日範囲チェック生鮮用(マスタ日付より1年後まで入力可能)
					String[] strReturn = new String[3];
//					↓↓2006.06.21 jiangyan カスタマイズ修正↓↓
//					strReturn = mst000401_LogicBean.getYukoDtRangeCheckFre(db, Keepb.getDonyuStDt());
//					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
//						mst000401_LogicBean.setErrorBackColor( CtrlColor, "tx_donyustdt" );
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setFirstFocus("tx_donyustdt");
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_donyustdt");
//							Keepb.setErrorMessage("導入開始日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
//
//						}

//					String MasterDT = RMSTDATEUtil.getMstDateDt();
//					String MasterDT1 = DateChanger.addDate(MasterDT, 1);
//					String MasterYEAR = DateChanger.addYear(MasterDT, 1);
					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						mst000401_LogicBean.setErrorBackColor( CtrlColor, "tx_yukodt" );
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setFirstFocus("tx_yukodt");
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_yukodt");
							Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");

						}
//						↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
					}
				}
		}
	}
	/**
	 *
	 * 販区のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst310201_KeepBean mst310201_KeepBean 	: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */

/**	public void  HankuCheck( mst000101_DbmsBean db, mst310201_KeepBean Keepb, Map CtrlColor,
								 SessionManager  sessionManager, TreeMap map, int flg ) throws Exception,SQLException {
		String CtlName = "tx_hankucd";

		List lst = new ArrayList();	//マスタ存在チェック抽出条件
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

		lst.add("SYUBETU_NO_CD = '" + 0040 + "' ");
		lst.add(" and ");
		lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
// 2006.01.24 Y.Inaba Mod ↑
		Keepb.setHankuKanjiRn(mstchk.getCdName());
		if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
			//販区コード存在エラー
			mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
			if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
				Keepb.setFirstFocus(CtlName);
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				if(flg == 0){
					Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
				} else {
					Keepb.setErrorMessage("指定された商品コード"+map.get("40007").toString());
				}
			}
		} else {
			//ログインユーザの売区とのチェック
			if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					if( flg == 0){
						Keepb.setErrorMessage(map.get("40022").toString());
					} else {
						Keepb.setErrorMessage("指定された商品コード"+map.get("40007").toString());
					}
				}
			}
		}
	}
*/
	/**
	 *
	 * 有効日チェックを行う。
	 * @param      mst310201_KeepBean Keepb      : キープビーン
	 * @param		String[] 	CtrlName 		  : コントロール名
	 * @param      Map         map               : コントロールカラー
	 * @param      SessionManager sessionManager : セッションマネージャ
	 * @param		String		tableNa			  : 対象テーブル名称
	 * @param		String		columnNa		  : 有効日カラム名称
	 * @param		List        whereList		  : 有効日を除くKEY部
	 * @param		String		yuko_dt		  	  : 有効日
	 * @param		String		addValue		  : 暦日加算値
	 */
	public void YukoDtCheck( mst310201_KeepBean Keepb ,String[] CtrlName ,Map CtrlColor
						,SessionManager sessionManager ,String tableNa ,String columnNa
						,List whereList, String yuko_dt, String addValue, mst000101_DbmsBean db ) throws Exception, SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

//		String chks = mst001001_EffectiveDayBean.getYukoDtCheck( tableNa, columnNa, whereList, addValue, yuko_dt, Keepb, db );
		String chks = "";
		if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
			chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck( tableNa,yuko_dt,Keepb,db,addValue,true );
		} else{
			chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck( tableNa,yuko_dt,Keepb,db,addValue,false );
		}
		//エラー有り
		if( chks == null){
			Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH ) ){
				Keepb.setErrorMessage( map.get("0007").toString() );
			}
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE ) ){
				if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ){
					Keepb.setErrorMessage( map.get("0009").toString() );
				} else if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE) ){
					Keepb.setErrorMessage( map.get("0010").toString() );
				} else if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
					Keepb.setErrorMessage( map.get("0011").toString() );
				}
			}
		}
	}
}
