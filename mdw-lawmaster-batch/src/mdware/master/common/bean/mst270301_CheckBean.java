/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

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
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst270301_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
//	public void Check(mst000101_DbmsBean	db, mst270201_KeepBean Keepb ,DataHolder dataHolder ,String kengenCd ,String GamenId ,
//	                   String FirstFocusCtl ,Map CtrlColor, SessionManager sessionManager ) throws SQLException, Exception {
	public void Check(mst000101_DbmsBean	db, mst270201_KeepBean Keepb ,DataHolder dataHolder ,String GamenId ,
            String FirstFocusCtl ,Map CtrlColor, SessionManager sessionManager ) throws SQLException, Exception {
		//----------------------------
		//初期処理
		//----------------------------
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
//			Keepb.setHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_cd")).trim());	//販区コード
//			Keepb.setHankuNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_nm")).trim());	//販区コード名称
			Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_cd")).trim());	//部門コード
			Keepb.setBumonNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_nm")).trim());	//部門コード名称
			Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin_cd")).trim());	//商品コード
			Keepb.setSyohinNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin_nm")).trim());	//商品コード名称

// ↓↓仕様追加（2005.07.06）↓↓
//			Keepb.setThemeCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_theme_cd")).trim());	//テーマコード
//			Keepb.setThemeNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_theme_nm")).trim());	//テーマコード名称
// ↑↑仕様追加（2005.07.06）↑↑

			//処理状況
			if (dataHolder.getParameter("ct_syorikb") == null) {
//				Keepb.setProcessingDivision(mst000101_ConstDictionary.PROCESS_REFERENCE);
				Keepb.setProcessingDivision(mst000101_ConstDictionary.PROCESS_INSERT);
			} else {
				Keepb.setProcessingDivision(dataHolder.getParameter("ct_syorikb"));
			}
			Keepb.setTenGroupNoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenGroupNo_cd")).trim());	//店配列コード
			Keepb.setTenGroupNoNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenGroupNo_nm")).trim());	//店配列コード名称

			String setFlg = dataHolder.getParameter("hid_setflag");

//			↓↓2006.09.15 H.Yamamoto カスタマイズ修正↓↓
			if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) && "1".equals(setFlg)) {
				mst270101_SyokaiDonyuJyukyoBean arrColumn = (mst270101_SyokaiDonyuJyukyoBean)Keepb.getMeisai().get(Keepb.getMeisai().size() - 1);
				arrColumn.setSuryoQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_suryo_qt" + (Keepb.getMeisai().size() - 1))).trim());
				arrColumn.setHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutani_qt" + (Keepb.getMeisai().size() - 1))).trim());
				arrColumn.setHatyuDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hatyu_dt" + (Keepb.getMeisai().size() - 1))).trim());
				arrColumn.setNohinDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohin_dt" + (Keepb.getMeisai().size() - 1))).trim());
				arrColumn.setHanbaiStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbai_st_dt" + (Keepb.getMeisai().size() - 1))).trim());
				arrColumn.setHanbaiEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbai_ed_dt" + (Keepb.getMeisai().size() - 1))).trim());
				arrColumn.setGentankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentanka_vl" + (Keepb.getMeisai().size() - 1))).trim());
				arrColumn.setBaitankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitanka_vl" + (Keepb.getMeisai().size() - 1))).trim());
				Keepb.getMeisai().set(Keepb.getMeisai().size() - 1,arrColumn);
			}
//			↑↑2006.09.15 H.Yamamoto カスタマイズ修正↑↑

			if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)
					&& "1".equals(setFlg)){
				String suryoAll = dataHolder.getParameter("hid_suryo_qt");
				String[] syryo = suryoAll.split(",");
				for (int i = 0; i < Keepb.getMeisai().size() - 1; i++) {
					mst270101_SyokaiDonyuJyukyoBean arrColumn = (mst270101_SyokaiDonyuJyukyoBean)Keepb.getMeisai().get(i);
					//数量
					if(!"".equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_suryo_qt" + (Keepb.getMeisai().size() - 1))).trim())){
						String suryo_qt = syryo[i];
						suryo_qt = mst000401_LogicBean.removeComma(suryo_qt);
						arrColumn.setSuryoQt(suryo_qt);
					}
					//発注単位
					if(!"".equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutani_qt" + (Keepb.getMeisai().size() - 1))).trim())){
						String hachutani_qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("hid_hachutani_qt")).trim();
						hachutani_qt = mst000401_LogicBean.removeComma(hachutani_qt);
						arrColumn.setHachutaniQt(hachutani_qt);
					}

					//発注日
					if(!"".equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hatyu_dt" + (Keepb.getMeisai().size() - 1))).trim())){
						String hatyu_dt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("hid_hatyu_dt")).trim();
						arrColumn.setHatyuDt(hatyu_dt);
					}

					//納品日
					if(!"".equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohin_dt" + (Keepb.getMeisai().size() - 1))).trim())){
						String nohin_dt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("hid_nohin_dt")).trim();
						arrColumn.setNohinDt(nohin_dt);
					}

					//販売開始日
					if(!"".equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbai_st_dt" + (Keepb.getMeisai().size() - 1))).trim())){
						String ct_hanbai_st_dt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("hid_hanbai_st_dt")).trim();
						arrColumn.setHanbaiStDt(ct_hanbai_st_dt);
					}

					//販売終了日
					if(!"".equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbai_ed_dt" + (Keepb.getMeisai().size() - 1))).trim())){
						String ct_hanbai_ed_dt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("hid_hanbai_ed_dt")).trim();
						arrColumn.setHanbaiEdDt(ct_hanbai_ed_dt);
					}

					//原価
					if(!"".equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentanka_vl" + (Keepb.getMeisai().size() - 1))).trim())){
						String gentanka_vl = mst000401_LogicBean.chkNullString(dataHolder.getParameter("hid_gentanka_vl")).trim();
						gentanka_vl = mst000401_LogicBean.removeComma(gentanka_vl);
						arrColumn.setGentankaVl(gentanka_vl);
					}

					//売価
					if(!"".equals(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitanka_vl" + (Keepb.getMeisai().size() - 1))).trim())){
						String baitanka_vl = mst000401_LogicBean.chkNullString(dataHolder.getParameter("hid_baitanka_vl" )).trim();
						baitanka_vl = mst000401_LogicBean.removeComma(baitanka_vl);
						arrColumn.setBaitankaVl(baitanka_vl);
					}
				}
			}

			//画面内容を保存（明細）
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){
				for (int i = (Integer.parseInt(Keepb.getStartRowInPage()) - 1) ; i < Integer.parseInt(Keepb.getEndRowInPage()) ; i ++) {

					mst270101_SyokaiDonyuJyukyoBean arrColumn = (mst270101_SyokaiDonyuJyukyoBean)Keepb.getMeisai().get(i);
					arrColumn.setChecked(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku" + i)));
				}
				//新規・修正の場合
				if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
				   Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
					for (int i = (Integer.parseInt(Keepb.getStartRowInPage()) - 1) ; i < Integer.parseInt(Keepb.getEndRowInPage()) ; i ++) {
						mst270101_SyokaiDonyuJyukyoBean arrColumn = (mst270101_SyokaiDonyuJyukyoBean)Keepb.getMeisai().get(i);

						//数量
						String suryo_qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_suryo_qt" + i)).trim();
						suryo_qt = mst000401_LogicBean.removeComma(suryo_qt);
						arrColumn.setSuryoQt(suryo_qt);

						//発注単位
						String hachutani_qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutani_qt" + i)).trim();
						hachutani_qt = mst000401_LogicBean.removeComma(hachutani_qt);
						arrColumn.setHachutaniQt(hachutani_qt);

						//発注日
						String hatyu_dt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hatyu_dt" + i)).trim();
						arrColumn.setHatyuDt(hatyu_dt);

						//納品日
						String nohin_dt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohin_dt" + i)).trim();
						arrColumn.setNohinDt(nohin_dt);

						//販売開始日
						String ct_hanbai_st_dt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbai_st_dt" + i)).trim();
						arrColumn.setHanbaiStDt(ct_hanbai_st_dt);

						//販売終了日
						String ct_hanbai_ed_dt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbai_ed_dt" + i)).trim();
						arrColumn.setHanbaiEdDt(ct_hanbai_ed_dt);

						//原価
						String gentanka_vl = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentanka_vl" + i)).trim();
						gentanka_vl = mst000401_LogicBean.removeComma(gentanka_vl);
						arrColumn.setGentankaVl(gentanka_vl);

						//売価
						String baitanka_vl = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitanka_vl" + i)).trim();
						baitanka_vl = mst000401_LogicBean.removeComma(baitanka_vl);
						arrColumn.setBaitankaVl(baitanka_vl);

						//  ↓↓2006/03/02 kim START
						// 自動発注一時/停止
//						String autoHachukb =mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_autoHachukb" + i)).trim();
//						arrColumn.setAutoHachuKb(autoHachukb);
						//　↑↑2006/03/02 kim END

// ↓↓仕様変更（2005.07.07）↓↓
						//テーマ
//						String theme_cd = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_theme_cd" + i)).trim();
//						arrColumn.setThemeCd(theme_cd);
// ↑↑仕様変更（2005.07.07）↑↑
					}
				}

				//削除の場合
//				if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)){
//					for (int i = 0; i < Keepb.getMeisai().size(); i++) {
//						mst270101_SyokaiDonyuJyukyoBean arrColumn = (mst270101_SyokaiDonyuJyukyoBean)Keepb.getMeisai().get(i);
//						//数量
//						String suryo_qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_suryo_qt" + i)).trim();
//						suryo_qt = mst000401_LogicBean.removeComma(suryo_qt);
//						arrColumn.setSuryoQt(suryo_qt);
//					}
//				}
			}

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

//			Map param = new HashMap();		//抽出条件格納用
//			ResultSet rset = null;			//抽出結果(ResultSet)

			//メーニューバーアイコン取得
			// userSession取得
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
			Map menuMap = new HashMap();
			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db, menuMap);
//			Keepb.setMenuBar(menu);

			//----------------------------
			//検索時エラーチェック
			//----------------------------
			//Keepb.getCheckFlg()-- チェック処理判断
			//               チェック処理　初期--mst000101_ConstDictionary.CHECK_INIT = "0";
			//               チェック処理　検索--mst000101_ConstDictionary.CHECK_SEARCH = "1";
			//               チェック処理　更新（登録/修正/削除)--mst000101_ConstDictionary.CHECK_UPDATE = "2";
			//               チェック処理　照会--mst000101_ConstDictionary.CHECK_VIEW = "3";
			//               チェック処理　その他--mst000101_ConstDictionary.CHECK_OTHERS = "4";

			//検索処理チェック-----------------------------------------------------------------------------
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)){
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				//----------------------
				//データ取得用変数
				//----------------------
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				boolean isErr = false;
				// 検索条件に部門コードが指定されている場合、名称を取得
				if (Keepb.getBumonCd() != null && !Keepb.getBumonCd().equals("")) {
					List whereList = new ArrayList();
					whereList.clear();
					whereList.add(" SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
					whereList.add(" AND ");
					whereList.add(" CODE_CD = '" + mst000401_LogicBean.chkNullString("0" + Keepb.getBumonCd()) + "' ");
					mst000701_MasterInfoBean mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", whereList, "0", "");
					// 部門名称が取得できない場合はエラー
					if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
						// 部門名称の初期化
						Keepb.setBumonNm("");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_bumon_cd");
						Keepb.setFirstFocus("tx_bumon_cd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された部門コード" + map.get("40007").toString());
						isErr = true;
					} else {
						// 部門名称の設定
						Keepb.setBumonNm(mstchk.getCdName());
					}
				} else {
					// 部門名称の初期化
					Keepb.setBumonNm("");
				}
//				String name = "";				//戻り値格納
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
//				mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール

				//----------------------
				//商品コード存在チェック
				//----------------------
				//業種コード取得
				lst.clear();
				lst.add( " SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
				lst.add( " AND  ");
				lst.add( " BUMON_CD = '0" + Keepb.getBumonCd() + "' ");
//				if (Keepb.getHankuCd().trim().length() > 0) {
//					lst.add( " AND HANKU_CD = '" + Keepb.getHankuCd() + "' ");
//				}
//				lst.add( " AND GYOSYU_KB = '" + mst000601_GyoshuKbDictionary.LIV.getCode() + "' ");

// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN","HINMEI_KANJI_NA", lst, "1");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN","HINMEI_KANJI_NA", lst, "1", "");
// 2006.01.24 Y.Inaba Mod ↑
				Keepb.setSyohinNm(mstchk.getCdName());
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//商品コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin_cd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						if (!isErr) {
							Keepb.setFirstFocus("ct_syohin_cd");
							Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
						}
						isErr = true;
					}
				}

				//販区が入力されていない場合は商品コードより取得する
//				if (Keepb.getHankuCd().length() == 0) {
////					String HankuCd = mst000401_LogicBean.getSyohinCd2HankuCd(db, Keepb.getSyohinCd().trim(), mst000601_GyoshuKbDictionary.LIV.getCode());
//					//2件以上ある場合はエラー
////					if (HankuCd == null) {
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setFirstFocus("ct_hanku_cd");
//							Keepb.setErrorMessage("販区コード" + map.get("0021").toString());
//
//
//							//販区が未確定なのでこれ以上のチェックは意味がないので処理終了
//						}
//					//1件の場合、その販区を以下の処理で使用する
////					} else {
////						Keepb.setHankuCd(HankuCd);
////					}
//				}

				//販区・商品コードが確定した場合、商品マスタからデータを取得する
				List collst = new ArrayList();//カラム名リスト
				collst.add("GENTANKA_VL");
				collst.add("BAITANKA_VL");
				collst.add("SIIRE_HINBAN_CD");
//				↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
				collst.add("HACHUTANI_IRISU_QT");
//				↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑

				lst.clear();
				lst.add( " SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
				lst.add( " AND  ");
				lst.add( " BUMON_CD = '0" + Keepb.getBumonCd() + "' ");
//				lst.add( " AND HANKU_CD = '" + Keepb.getHankuCd() + "' ");

				Map values = mst000401_LogicBean.getMasterItemValues(db, "R_SYOHIN",collst,lst, "1");

				if(values.size() == 0){

					//商品コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin_cd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						if (!isErr) {
							Keepb.setFirstFocus("ct_syohin_cd");
							Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
						}
						isErr = true;
					}
					Keepb.setSyohinNm("");

				} else {
					Keepb.setGentanka_vl((String)values.get("GENTANKA_VL"));
					Keepb.setBaitanka_vl((String)values.get("BAITANKA_VL"));
//					↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
					Keepb.setHachutaniIrisuQt((String)values.get("HACHUTANI_IRISU_QT"));
//					↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑
				}

				//----------------------
				//販区
				//----------------------
//				lst.clear();
////				lst.add("SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");
//				lst.add(" and ");
////				lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
//// 2006.01.24 Y.Inaba Mod ↓
////				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
//// 2006.01.24 Y.Inaba Mod ↑
////				Keepb.setHankuNm(mstchk.getCdName());
//
//				//データが存在しない場合
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus("ct_hanku_cd");
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
//					}
//				} else {
////					if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus("ct_hanku_cd");
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage(map.get("40022").toString());
//						}
////					}
//				}
				//----------------------
				//店配列
				//----------------------
				if(!Keepb.getTenGroupNoCd().equals("")){
					lst.clear();

// ↓↓仕様変更（2005.07.16）↓↓
//					lst.add(" L_GYOSYU_CD = '0120' ");
//					lst.add(" L_GYOSYU_CD = '" + dataHolder.getParameter("h_l_gyosyucd") + "' ");
// ↑↑仕様変更（2005.07.16）↑↑
//					lst.add("  and ");
					lst.add(" YOTO_KB = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
					lst.add("  and ");
					lst.add(" GROUPNO_CD = '" + Keepb.getTenGroupNoCd() + "' ");
					lst.add( " AND  ");
					lst.add( " BUMON_CD = '0" + Keepb.getBumonCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUPNO","NAME_NA", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUPNO","NAME_NA", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setTenGroupNoNm(mstchk.getCdName());

					//データが存在しない場合
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_tenGroupNo_cd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							if (!isErr) {
								Keepb.setFirstFocus("ct_tenGroupNo_cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定された店配列"+map.get("40007").toString());
							}
							isErr = true;
						}
					//データが存在する場合、店グルーピングマスタもチェック
					} else {
// 2006.01.24 Y.Inaba Mod ↓
//						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUP","TENPO_CD", lst, "0");
						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUP","TENPO_CD", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
						//データが存在しない場合
						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_tenGroupNo_cd");
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								if (!isErr) {
									Keepb.setFirstFocus("ct_tenGroupNo_cd");
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("指定された店配列"+map.get("40007").toString());
								}
								isErr = true;
							}
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * コントロール名取得処理
	 * @param 		int meisaiCnt       : 明細数
	 * @return		String[]
	 */
	public String[] getCtrlName(int meisaiCnt){

//		String[] CtrlName 	  = new String[meisaiCnt * 6 + 5];
		String[] CtrlName 	  = new String[meisaiCnt * 8 + 5];
		int j=0;
		for (int i=0; i < meisaiCnt; i++) {
			CtrlName[j] 	  = "ct_suryo_qt" + i;
			j++;
			CtrlName[j] 	  = "ct_hachutani_qt" + i;
			j++;
			CtrlName[j] 	  = "ct_hatyu_dt" + i;
			j++;
			CtrlName[j] 	  = "ct_nohin_dt" + i;
			j++;
			CtrlName[j] 	  = "ct_hanbai_st_dt" + i;
			j++;
			CtrlName[j] 	  = "ct_hanbai_ed_dt" + i;
			j++;
			CtrlName[j] 	  = "ct_gentanka_vl" + i;
			j++;
			CtrlName[j] 	  = "ct_baitanka_vl" + i;
			j++;
		}
//		CtrlName[j] 	  = "ct_hanku_cd";
//		CtrlName[j+1] 	  = "ct_hachuno_cd";
		CtrlName[j+1] 	  = "ct_syohin_cd";
// ↓↓仕様追加（2005.07.06）↓↓
//		CtrlName[j+1] 	  = "ct_theme_cd";
// ↑↑仕様追加（2005.07.06）↑↑
		CtrlName[j+2] 	  = "ct_tenGroupNo_cd";
		CtrlName[j+3] 	  = "tx_bumon_cd";
		return CtrlName;
	}
}