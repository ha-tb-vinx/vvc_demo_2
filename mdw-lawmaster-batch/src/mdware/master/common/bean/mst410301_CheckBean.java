/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）名称・CTFマスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する名称・CTFマスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/29)初版作成
 */
package mdware.master.common.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）名称・CTFマスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する名称・CTFマスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.0 2014/09/15 ha.ntt 海外LAWSON様通貨対
 */
public class mst410301_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
	public void Check(
					 mst000101_DbmsBean 		db
					,mst410201_KeepBean			Keepb
					,mst410501_KeepMeisaiBean	Meisaib2
					,DataHolder 				dataHolder
//					↓↓2006.06.24 shiyy カスタマイズ修正↓↓
//					,String 					kengenCd
//					↑↑2006.06.24 shiyy カスタマイズ修正↑↑
					,String 					GamenId
					,String 					FirstFocusCtl
					,String[] 					CtrlName
					,Map 						CtrlColor
					,SessionManager  			sessionManager) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		//エラーメッセージ初期化
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){

			//■■↓↓処理状況　保持↓↓■■
			Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim() );

			//画面内容を保存
			//リスト１
			Keepb.setSyubetu1Cd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu1cd")).trim() );	// 種別No1
			Keepb.setSyubetu1Nm( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu1nm")).trim() );	// 種別名称1
			Keepb.setCode1Cd(    mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_code1cd")).trim() );		// コード1

			//リスト２
			Keepb.setSyubetu2Cd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu2cd")).trim() );	// 種別No2
			Keepb.setSyubetu2Nm( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu2nm")).trim() );	// 種別名称2
			Keepb.setCode2Cd(    mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_code2cd")).trim() );		// コード2

			String[] syubetuNoCd = dataHolder.getParameterValues("ct_h_syubetunocd2");
			if(syubetuNoCd.length > 0){
				for (int i = (Integer.parseInt(Meisaib2.getStartRowInPage()) - 1) ; i < Integer.parseInt(Meisaib2.getEndRowInPage()) ; i ++) {
					mst410101_NameCtfBean arrColumn = (mst410101_NameCtfBean)Meisaib2.getMeisai().get(i);
					arrColumn.setSentaku(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_chk2" + i)));
					//新規・修正の場合
					if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
						// 名称（漢字）
						arrColumn.setKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanjina2" + i)).trim());
						// 略名（漢字）
						arrColumn.setKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanjirn2" + i)).trim());
						// 名称(カナ)
						arrColumn.setKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanana2" + i)).trim());
						// 略名(カナ)
						arrColumn.setKanaRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanarn2" + i)).trim());
					}
				}
			}
//			if(syubetuNoCd.length > 0){
//				List lst = new ArrayList();
//				for(int i = 0;i < syubetuNoCd.length; i++){
//					mst410101_NameCtfBean Bean = new mst410101_NameCtfBean();
//
//					Bean.setSentaku(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_chk2" + i)).trim());
//					Bean.setSyubetuNoCd(syubetuNoCd[i]);
//					Bean.setCodeCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_codecd2" + i)).trim());
//					Bean.setKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanjina2" + i)).trim());
//					Bean.setKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanjirn2" + i)).trim());
//					Bean.setKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanana2" + i)).trim());
//					Bean.setKanaRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanarn2" + i)).trim());
//					Bean.setZokuseiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_zokuseicd2" + i)).trim());
//					Bean.setInsertTs(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_insertts2" + i)).trim());
//					Bean.setInsertTsShort(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_inserttsshort2" + i)).trim());
//					Bean.setInsertUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_insertuserid2" + i)).trim());
//					Bean.setInsertUserNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_insertusernm2" + i)).trim());
//					Bean.setUpdateTs(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updatets2" + i)).trim());
//					Bean.setUpdateTsShort(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updatetsshort2" + i)).trim());
//					Bean.setUpdateUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updateuserid2" + i)).trim());
//					Bean.setUpdateUserNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updateusernm2" + i)).trim());
//					lst.add(Bean);
//				}
////				Meisaib2.setMeisai((ArrayList)lst);
//				Meisaib2.setCurrentPageNo(Meisaib2.getCurrentPageNo());
//				Meisaib2.setDispRows(Meisaib2.getDispRows());
//				Meisaib2.setEndRowInPage(Meisaib2.getEndRowInPage());
//				Meisaib2.setLastPageNo(Meisaib2.getLastPageNo());
//				Meisaib2.setMaxRows(Meisaib2.getMaxRows());
//				Meisaib2.setStartRowInPage(Meisaib2.getStartRowInPage());
//			}

			//リスト３
			Keepb.setSyubetu3Cd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu3cd")).trim() );	// 種別No3
			Keepb.setSyubetu3Nm( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu3nm")).trim() );	// 種別名称3
			Keepb.setCode3Cd(    mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_code3cd")).trim() );		// コード3

			Keepb.setMode(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_mode")));	//処理モード

//			Map param = new HashMap();		//抽出条件格納用
//			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//メーニューバーアイコン取得
			// userSession取得

//		    ↓↓2006.06.22 shiyy カスタマイズ修正↓↓
//			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//			SysUserBean = ( mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);
//		    ↑↑2006.06.22 shiyy カスタマイズ修正↑↑

			//エラーチェック
//			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
//			String name = "";				//戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

//			↓↓2006.08.15 wangluping カスタマイズ修正↓↓
// ↓↓　システム区分チェック追加（2005.06.22）　↓↓ //
//			if (dataHolder.getParameter("JobID").equals("mst410301_NameCtfInsert")
//				||	dataHolder.getParameter("JobID").equals("mst410401_NameCtfUpdate")) {
//				//新規・修正時の更新処理でチェックを行う
//				if (dataHolder.getParameter("ct_syubetu2cd").equals(mst000101_ConstDictionary.SECTION)) {
//					//種別№が販区
//					for (int i = 0; i < Integer.parseInt(Meisaib2.getMaxRows()); i++) { //明細部の件数分チェックを行う
//						if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_zokuseicd2" + i)).trim().equals("")){
//							String syskb_check = (String)dataHolder.getParameter("ct_zokuseicd2" + i).substring(0,1);
//							if (!syskb_check.equals(mst006101_SystemKbDictionary.LIVING.getCode())
//								&&	!syskb_check.equals(mst006101_SystemKbDictionary.APPAREL.getCode())
//								&&	!syskb_check.equals(mst006101_SystemKbDictionary.GROCERY.getCode())
//								&&	!syskb_check.equals(mst006101_SystemKbDictionary.FRESH.getCode())) {
//								//属性コードの１桁目がシステム区分の値でない場合はエラー
//								mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_zokuseicd2" + i);
//								if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									Keepb.setFirstFocus("ct_zokuseicd2" + i);
//									Keepb.setErrorMessage("属性コードの１桁目にはシステム区分を入力して下さい。");
//								}
//							}
//						}
//					}
//				}
//			}
// ↑↑　システム区分チェック追加（2005.06.22）　↑↑ //
//			↑↑2006.08.15 wangluping カスタマイズ修正↑↑

			//検索処理チェック-----------------------------------------------------------------------------
			// 2005.05.09 名称マスタ仕様変更に伴う修正(INABA)
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH)){
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				//種別No1コードチェック
				if(!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu1cd")).trim().equals("")){
					lst = new ArrayList();
//					lst.add( "     SYUBETU_NO_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu1cd")) + "' " );
//					lst.add( " AND CODE_CD       = '" + mst000101_ConstDictionary.NAMECTF_SYUBETU + "' " );
					lst.add( "     SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.NAMECTF_SYUBETU, userLocal) + "' " );
					lst.add( " AND CODE_CD       = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu1cd")) + "' " );
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//コード存在エラー
						Keepb.setSyubetu1Nm("");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syubetu1cd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_syubetu1cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							↓↓2006.08.01 wangluping カスタマイズ修正↓↓
// ↓↓修正（2005.07.01）↓↓
							Keepb.setErrorMessage("指定された種別No"+map.get("40007").toString());
//							Keepb.setErrorMessage("種別№0000でタイトル登録してください。");
// ↑↑修正（2005.07.01）↑↑
//							↑↑2006.08.01 wangluping カスタマイズ修正↑↑
						}
					} else {
						Keepb.setSyubetu1Nm(mstchk.getCdName());
					}
				} else {
					Keepb.setSyubetu1Nm("");
				}
				//種別No2コードチェック
				lst = new ArrayList();
//				lst.add( "     SYUBETU_NO_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu2cd")) + "' " );
//				lst.add( " AND CODE_CD       = '" + mst000101_ConstDictionary.NAMECTF_SYUBETU + "' " );
				lst.add( "     SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.NAMECTF_SYUBETU, userLocal) + "' " );
				lst.add( " AND CODE_CD       = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu2cd")) + "' " );
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//コード存在エラー
					Keepb.setSyubetu2Nm("");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syubetu2cd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus("ct_syubetu2cd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						↓↓2006.08.01 wangluping カスタマイズ修正↓↓
//						↓↓修正（2005.07.01）↓↓
						Keepb.setErrorMessage("指定された種別No"+map.get("40007").toString());
//						Keepb.setErrorMessage("種別№0000でタイトル登録してください。");
//						↑↑修正（2005.07.01）↑↑
//						↑↑2006.08.01 wangluping カスタマイズ修正↑↑
					}
				} else {
					Keepb.setSyubetu2Nm(mstchk.getCdName());
//					↓↓2006.08.15 wangluping カスタマイズ修正↓↓
// ↓↓追加（2005.07.01）↓↓
//					if (!dataHolder.getParameter("ct_syubetu2cd").equals(mst000101_ConstDictionary.NAMECTF_SYUBETU)){
//						//種別№0000の属性コードから、頭2桁を取得
//						lst = new ArrayList();
//						lst.add( " 	   SYUBETU_NO_CD = '"+ mst000101_ConstDictionary.NAMECTF_SYUBETU + "' " );
//						lst.add( " AND CODE_CD       = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu2cd")) + "' " );
//// 2006.01.24 Y.Inaba Mod ↓
////						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","TRIM(ZOKUSEI_CD)", lst, "0");
//						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","TRIM(ZOKUSEI_CD)", lst, "0", "");
//// 2006.01.24 Y.Inaba Mod ↑
//						if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)
//							&&	mstchk.getCdName().length() > 1){
//							//値があれば、頭2桁をセットする
//							Keepb.setCode2Len(mst000401_LogicBean.chkNullString(mstchk.getCdName()).substring(0,2));
//						} else {
//							Keepb.setCode2Len("10");
//						}
//					}
// ↑↑追加（2005.07.01）↑↑
				}

				//名称CTFから種別NoをKeyにしてコードの入力桁数を取得する
//				↓↓バグ修正（2005.10.13）↓↓
//				int codeLength = this.getCodeLength(dataHolder.getParameter("ct_code2cd"));
//				int codeLength = this.getCodeLength(dataHolder.getParameter("ct_syubetu2cd"));
//				↑↑バグ修正（2005.10.13）

				//コードの入力桁数チェック
//				if (codeLength != 0 && codeLength < Keepb.getCode2Cd().length() ) {
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_code2cd");
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus("ct_code2cd");
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage(map.get("0001").toString());//入力データが長すぎます。
//					}
//				}
//				↑↑2006.08.15 wangluping カスタマイズ修正↑↑
//				BUGNO-S014 2005.04.21 T.Makuta END
				//種別No3コードチェック
				if(!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu3cd")).trim().equals("")){
					lst = new ArrayList();
					lst.add( "     SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.NAMECTF_SYUBETU, userLocal) + "' " );
					lst.add( " AND CODE_CD       = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syubetu3cd")) + "' " );
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syubetu3cd");
						Keepb.setSyubetu3Nm("");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_syubetu3cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							↓↓2006.08.01 wangluping カスタマイズ修正↓↓
// ↓↓修正（2005.07.01）↓↓
							Keepb.setErrorMessage("指定された種別No"+map.get("40007").toString());
//							Keepb.setErrorMessage("種別№0000でタイトル登録してください。");
// ↑↑修正（2005.07.01）↑↑
//							↑↑2006.08.01 wangluping カスタマイズ修正↑↑
						}
					} else {
						Keepb.setSyubetu3Nm(mstchk.getCdName());
					}
				} else {
					Keepb.setSyubetu3Nm("");
				}
			}
		}
	}
//	↓↓2006.08.15 wangluping カスタマイズ修正↓↓
	/**
	 * コードの入力桁数チェック
	 * 名称CTFから種別No=0000 とコードで
	 * そのコードの桁数を取得する
	 * @param String code2 コード２
	 * @return	int codeLength コードの桁数
	 */
//	public int getCodeLength (String code2) throws SQLException,Exception {
//
//		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();
//		StringBuffer sql = new StringBuffer();
//
//		//コードの桁数
//		int codeLength = 0;
//
//		//SQL開始
//		try {
//			sql.append(" SELECT ");
////      ↓↓移植（2006.05.26）↓↓
//			sql.append("   SUBSTR(ZOKUSEI_CD, 1, 2) as CODE_LEN ");
////      ↑↑移植（2006.05.26）↑↑
//			sql.append(" FROM ");
//			sql.append("   R_NAMECTF ");
//			sql.append(" WHERE ");
//			//コード参照用の種別NoALL0を設定
//			sql.append("   SYUBETU_NO_CD = '0000' ");
//			sql.append(" AND ");
//			sql.append("   CODE_CD = '"+ code2 +"' ");
//
//			ResultSet rset = db.executeQuery(sql.toString());    //抽出結果(ResultSet)
//
//			while (rset.next()) {
//				codeLength = Integer.parseInt(rset.getString("CODE_LEN").trim());
//			}
//
//		} catch(SQLException  sqle) {
//			throw sqle;
//		}
//		return codeLength;
//	}
//	↑↑2006.08.15 wangluping カスタマイズ修正↑↑
	/**
	 *
	 * コントロール名取得処理
	 * @param 		int meisaiCnt       : 明細数
	 * @return		String[]
	 */
	public String[] getCtrlName(int meisaiCnt){

		String[] CtrlName 	  = new String[meisaiCnt * 5 + 9];
		int j=0;
		for (int i=0; i < meisaiCnt; i++) {
			CtrlName[j] 	  = "ct_kanjina2" + i;
			j++;
			CtrlName[j] 	  = "ct_kanjirn2" + i;
			j++;
			CtrlName[j] 	  = "ct_kanana2" + i;
			j++;
			CtrlName[j] 	  = "ct_kanarn2" + i;
			j++;
			CtrlName[j] 	  = "ct_codecd2" + i;
			j++;
		}
		CtrlName[j+1] 	  = "ct_syubetu1cd";
		CtrlName[j+2] 	  = "ct_syubetu1na";
		CtrlName[j+3] 	  = "ct_code1cd";
		CtrlName[j+4] 	  = "ct_syubetu2cd";
		CtrlName[j+5] 	  = "ct_syubetu2na";
		CtrlName[j+6] 	  = "ct_code2cd";
		CtrlName[j+7] 	  = "ct_syubetu3cd";
		CtrlName[j+8] 	  = "ct_syubetu3na";
		CtrlName[j] 	  = "ct_code3cd";
		return CtrlName;
	}
}
