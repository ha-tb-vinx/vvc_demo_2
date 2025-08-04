/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
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
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst260301_CheckBean
{
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		mst000101_DbmsBean	db        : DB
	 * @param 		mst260201_KeepBean Keepb      : KeepBeen
	 * @param 		DataHolder 	dataHolder        : DataHolder
	 * @param		String		kengenCd		  : 権限コード
	 * @param		String 		GamenId 		  : 画面ID
	 * @param		String 		FirstFocusCtl  	  : 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		Map CtrlColor           　    : コントロール背景色
	 * @param		SessionManager sessionManager : SessionManager
	 */
	public void Check(mst000101_DbmsBean	db, mst260201_KeepBean Keepb ,DataHolder dataHolder ,String kengenCd ,String GamenId ,
	                   String FirstFocusCtl ,Map CtrlColor, SessionManager sessionManager ) throws SQLException,Exception {


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
			Keepb.setHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_cd")).trim());	//販区コード
			Keepb.setHankuNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_nm")).trim());	//販区コード名称

			Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin_cd")).trim());	//商品コード
			Keepb.setSyohinNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin_nm")).trim());	//商品コード名称
			//処理状況
			if (dataHolder.getParameter("ct_syorikb") == null) {
//				Keepb.setProcessingDivision(mst000101_ConstDictionary.PROCESS_REFERENCE);
				Keepb.setProcessingDivision(mst000101_ConstDictionary.PROCESS_INSERT);
			} else {
				Keepb.setProcessingDivision(dataHolder.getParameter("ct_syorikb"));
			}
			Keepb.setTenGroupNoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenGroupNo_cd")).trim());	//店配列コード
			Keepb.setTenGroupNoNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenGroupNo_nm")).trim());	//店配列コード名称

			Keepb.setTrihikisakiHinban(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_trihikisakiHinban")).trim());	//取引先品番

			Keepb.setColorCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_color_cd")).trim());	//カラーコード
			Keepb.setColorNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_color_nm")).trim());	//カラーコード名称
			Keepb.setSizeCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_size_cd")).trim());	//サイズーコード
			Keepb.setSizeNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_size_nm")).trim());	//サイズコード名称
			Keepb.setAssortPtCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_asort_pattern_cd")).trim());	//アソートパターンコード

			//画面内容を保存（明細）
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){

				//新規・修正の場合
				if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
				   Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
				   ){
					for (int i = 0; i < Keepb.getMeisai().size(); i++) {

						mst260101_SyokaiDonyuIryouBean arrColumn = (mst260101_SyokaiDonyuIryouBean)Keepb.getMeisai().get(i);

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

						//原価
						String gentanka_vl = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentanka_vl" + i)).trim();
						gentanka_vl = mst000401_LogicBean.removeComma(gentanka_vl);
						arrColumn.setGentankaVl(gentanka_vl);

						//売価
						String baitanka_vl = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitanka_vl" + i)).trim();
						baitanka_vl = mst000401_LogicBean.removeComma(baitanka_vl);
						arrColumn.setBaitankaVl(baitanka_vl);

						//テーマ
						String theme_cd = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_theme_cd" + i)).trim();
						arrColumn.setThemeCd(theme_cd);
					}
				}

				//削除の場合
				if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)){
					for (int i = 0; i < Keepb.getMeisai().size(); i++) {
						mst260101_SyokaiDonyuIryouBean arrColumn = (mst260101_SyokaiDonyuIryouBean)Keepb.getMeisai().get(i);
						//数量
						String suryo_qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_suryo_qt" + i)).trim();
						suryo_qt = mst000401_LogicBean.removeComma(suryo_qt);
						arrColumn.setSuryoQt(suryo_qt);
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
			//検索処理チェック-----------------------------------------------------------------------------
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)){
				//----------------------
				//データ取得用変数
				//----------------------
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				String name = "";				//戻り値格納
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール

				//----------------------
				//商品コード存在チェック
				//----------------------
				//業種コード取得
				String gyosyuKb = "";
				lst.clear();
				lst.add( " SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
				if (Keepb.getHankuCd().trim().length() > 0) {
					lst.add( " AND HANKU_CD = '" + Keepb.getHankuCd() + "' ");
				}
				lst.add( " AND GYOSYU_KB IN ('" + mst000601_GyoshuKbDictionary.A08.getCode() + "' , '" + mst000601_GyoshuKbDictionary.A12.getCode() + "') ");
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN","GYOSYU_KB", lst, "1");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN","GYOSYU_KB", lst, "1", "");
// 2006.01.24 Y.Inaba Mod ↑
				Keepb.setGyosyu_kb(mstchk.getCdName());
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//商品コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin_cd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("ct_syohin_cd");
						Keepb.setErrorMessage(map.get("40026").toString());
					}
					Keepb.setSyohinNm("");
				} else {
					Keepb.setSyohinNm("");
					gyosyuKb = mstchk.getCdName();
				}

				//販区が入力されていない場合は商品コードより取得する
				if (Keepb.getHankuCd().length() == 0) {
					String HankuCd = mst000401_LogicBean.getSyohinCd2HankuCd(db, Keepb.getSyohinCd().trim(), gyosyuKb);
					//2件以上ある場合はエラー
					if (HankuCd == null) {
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setFirstFocus("ct_hanku_cd");
							Keepb.setErrorMessage("販区コード" + map.get("0021").toString());

						}
					//1件の場合、その販区を以下の処理で使用する
					} else {
						Keepb.setHankuCd(HankuCd);
					}
				}

				//販区・商品コードが確定した場合、商品マスタからデータを取得する
				List collst = new ArrayList();//カラム名リスト
				collst.add("HINMEI_KANJI_NA");
				collst.add("GENTANKA_VL");
				collst.add("BAITANKA_VL");
				collst.add("SIIRE_HINBAN_CD");

				lst.clear();
				lst.add( " SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
				lst.add( " AND HANKU_CD = '" + Keepb.getHankuCd() + "' ");

				Map values = mst000401_LogicBean.getMasterItemValues(db, "R_SYOHIN",collst,lst, "1");

				if(values.size() == 0){

					//商品コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin_cd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("ct_syohin_cd");
						Keepb.setErrorMessage(map.get("40026").toString());
					}
					Keepb.setSyohinNm("");
					Keepb.setTrihikisakiHinban("");

				} else {
					Keepb.setSyohinNm((String)values.get("HINMEI_KANJI_NA"));
					Keepb.setGentanka_vl((String)values.get("GENTANKA_VL"));
					Keepb.setBaitanka_vl((String)values.get("BAITANKA_VL"));
					Keepb.setTrihikisakiHinban((String)values.get("SIIRE_HINBAN_CD"));
				}

				//----------------------
				//販区
				//----------------------
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				lst.clear();
				lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
				lst.add(" and ");
				lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
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
//					↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//					if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hanku_cd");
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setFirstFocus("ct_hanku_cd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40022").toString());
//							}
//					}
//					↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
				}

				//----------------------
				//店配列
				//----------------------
				if(!Keepb.getTenGroupNoCd().equals("")){
					lst.clear();

					lst.add(" L_GYOSYU_CD = '" + dataHolder.getParameter("h_l_gyosyucd") + "' ");
					lst.add("  and ");
					lst.add(" YOTO_KB = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
					lst.add("  and ");
					lst.add(" GROUPNO_CD = '" + Keepb.getTenGroupNoCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUPNO","NAME_NA", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUPNO","NAME_NA", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setTenGroupNoNm(mstchk.getCdName());

					//データが存在しない場合
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){

						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_tenGroupNo_cd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_tenGroupNo_cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された店配列"+map.get("40007").toString());
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
								Keepb.setFirstFocus("ct_tenGroupNo_cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定された店配列"+map.get("40007").toString());
							}
						}
					}
				}

				//----------------------
				//カラー／サイズコード
				//----------------------
				//カラーコード名称取得
				if (!Keepb.getColorCd().equals("")) {
					lst.clear();
					lst.add(" SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal) + "' ");
					lst.add(" and ");
					lst.add(" CODE_CD = '" + Keepb.getColorCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
						mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_color_cd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_color_cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定されたカラーコード"+map.get("40007").toString());
						}
					} else {
						Keepb.setColorNm(mstchk.getCdName());
					}
				}
				//サイズコード名称取得
				if ((!Keepb.getSizeCd().equals(""))
					&& (!Keepb.getSyohinCd().equals(""))) {
					lst.clear();
					lst.add(" SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal) + "' ");
					lst.add(" and ");
					lst.add(" CODE_CD = '" + Keepb.getSyohinCd().substring(0,4) + Keepb.getSizeCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
						mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_size_cd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_size_cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定されたサイズコード"+map.get("40007").toString());
						}
					} else {
						Keepb.setSizeNm(mstchk.getCdName());
					}
				}
				//アソートメントマスタでの存在チェック
				if ((!Keepb.getColorCd().equals(""))
					&& (!Keepb.getSizeCd().equals(""))){
					lst.clear();
					lst.add(" SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
					lst.add("  and ");
					lst.add(" COLOR_CD = '" + Keepb.getColorCd() + "' ");
					lst.add("  and ");
					lst.add(" SIZE_CD = '" + Keepb.getSizeCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_ASSORTMENT","HACHU_TEISI_KB", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_ASSORTMENT","HACHU_TEISI_KB", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					//データが存在しない場合
					if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_color_cd");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_size_cd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_color_cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定されたカラー・サイズコード"+map.get("40007").toString());
						}
					//データが存在する場合、発注停止区分をチェック
//					} else if (mstchk.getCdName().equals(mst001301_HatyuTeishiKbDictionary.HATYU_TEISI.getCode())) {
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_color_cd");
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_size_cd");
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus("ct_color_cd");
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage("発注が停止されています。");
//						}
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
// ↓↓仕様変更（2005.10.14）↓↓
//		String[] CtrlName 	  = new String[meisaiCnt * 5 + 6];
		String[] CtrlName 	  = new String[meisaiCnt * 6 + 6];
// ↑↑仕様変更（2005.10.14）↑↑
		int j=0;
		for (int i=0; i < meisaiCnt; i++) {
			CtrlName[j] 	  = "ct_suryo_qt" + i;
			j++;
			CtrlName[j]		  = "ct_hachutani_qt" + i;
			j++;
			CtrlName[j] 	  = "ct_hatyu_dt" + i;
			j++;
			CtrlName[j] 	  = "ct_nohin_dt" + i;
			j++;
			CtrlName[j] 	  = "ct_gentanka_vl" + i;
			j++;
			CtrlName[j] 	  = "ct_baitanka_vl" + i;
			j++;
		}
		CtrlName[j] 	  = "ct_hanku_cd";
		CtrlName[j+1] 	  = "ct_hachuno_cd";
		CtrlName[j+2] 	  = "ct_syohin_cd";
		CtrlName[j+3] 	  = "ct_tenGroupNo_cd";
		CtrlName[j+4] 	  = "ct_color_cd";
		CtrlName[j+5] 	  = "ct_size_cd";
		return CtrlName;

	}

}