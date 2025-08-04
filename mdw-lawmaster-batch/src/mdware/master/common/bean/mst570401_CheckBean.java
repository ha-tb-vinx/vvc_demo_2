/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタの画面データ入力チェツククラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタ登録画面データの入力チェツククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/15)初版作成
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
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタの画面データ入力チェツククラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタ登録画面データの入力チェツククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst570401_CheckBean {
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param      mst570301_KeepBean Keepb    : キープビーン
	 * @param 		DataHolder 	dataHolder      : データホルダー
	 * @param		String		kengenCd		: 権限コード
	 * @param		String 		GamenId 		: 画面ID
	 * @param		String 		FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		String[] 	CtrlName 		: コントロール名
	 * @param      Map         map             : コントロールカラー
	 * @param      SessionManager sessionManager : セッションマネージャ
	 */
	public void Check(mst000101_DbmsBean db, mst570301_KeepBean Keepb ,DataHolder dataHolder ,String kengenCd ,String GamenId ,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor
						,SessionManager sessionManager) throws SQLException,Exception {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");
		//エラーチェック
		mst000501_SysSosasyaBean 		SysUserBean 	= new mst000501_SysSosasyaBean();	// ログインユーザー情報
		//sysUserSesson取得
		SysUserBean=(mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//画面内容を保存
			//検索条件
			Keepb.setKanriKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")));			//管理区分
			Keepb.setKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")));			//管理コード
			Keepb.setKanriKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrina")));	//管理名
			Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")));		//商品コード
			Keepb.setSyohinKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohinna")));	//商品名
			Keepb.setSihaiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_shihaicd")));		//仕入先コード
			Keepb.setSihaiKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_shihaina")));	//仕入先名
			Keepb.setYukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yukodt")));			//有効日
			Keepb.setButuryuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_buturyukb")));		//物流区分
			Keepb.setGroupnoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_groupnocd")));		//店グループＮＯ
			Keepb.setGroupnpNameNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_groupnona")));	//店グループ名
			Keepb.setProcessingDivision(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_processingdivision")));	//処理状況

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//メーニューバーアイコン取得
			Map menuMap = new HashMap();
			menuMap.put("gamen_id",GamenId);
			menuMap.put("kengen_cd",kengenCd);
			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
			String[] menu = mst000401_LogicBean.getCommonMenubar(db, menuMap);
			Keepb.setMenuBar(menu);

			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			String name = "";				//戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
			//Keepb.getCheckFlg()-- チェック処理判断
			//               チェック処理　初期--mst000101_ConstDictionary.CHECK_INIT = "0";
			//               チェック処理　検索--mst000101_ConstDictionary.CHECK_SEARCH = "1";
			//               チェック処理　更新（登録/修正/削除)--mst000101_ConstDictionary.CHECK_UPDATE = "2";
			//               チェック処理　照会--mst000101_ConstDictionary.CHECK_VIEW = "3";
			//               チェック処理　その他--mst000101_ConstDictionary.CHECK_OTHERS = "4";
			// 登録

			//検索処理チェック-----------------------------------------------------------------------------
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH) ||
				Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)) {
				// 有効日範囲チェック(マスタ日付より1年後まで入力可能)
				// 照会時はチェックを行わない
				String[] strReturn = new String[3];
				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("ct_yukodt");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_yukodt");
						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
					}
				}

				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				//管理コード存在チェック
				lst.add( "SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
				lst.add( " AND CODE_CD = '" + Keepb.getKanriCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//管理コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus("ct_kanricd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された管理コード"+map.get("40007").toString());
						Keepb.setKanriKanjiRn("");
					}
				} else {
					Keepb.setKanriKanjiRn(mstchk.getCdName());
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName2(db, "R_NAMECTF","KANJI_RN", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName2(db, "R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setKanri_kanji_rn_mae(mstchk.getCdName());
					if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//						↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//						if(!mst000401_LogicBean.getHankuCdCheck(db, mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")),sessionManager)){
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setFirstFocus("ct_kanricd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40022").toString());
//							}
//						}
//						↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
					}
				}

				//管理区分＝販区＋商品の場合
				if (Keepb.getKanriKb().equals(mst000901_KanriKbDictionary.HANKU_SYOUHINCD.getCode())) {
					//商品コード存在チェック
					lst.clear();
					lst.add( "HANKU_CD = '" + Keepb.getKanriCd() + "' ");
					lst.add( " AND SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN","HINMEI_KANJI_NA", lst, "1");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN","HINMEI_KANJI_NA", lst, "1", Keepb.getYukoDt());
// 2006.01.24 Y.Inaba Mod ↑
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//商品コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setFirstFocus("ct_syohincd");
							Keepb.setErrorMessage(map.get("40026").toString());
						}
						Keepb.setSyohinKanjiRn("");
					} else {
						Keepb.setSyohinKanjiRn(mstchk.getCdName());
// 2006.01.24 Y.Inaba Mod ↓
//						mstchk = mst000401_LogicBean.getMasterCdName2(db, "R_SYOHIN","HINMEI_KANJI_NA", lst, "1");
						mstchk = mst000401_LogicBean.getMasterCdName2(db, "R_SYOHIN","HINMEI_KANJI_NA", lst, "1", Keepb.getYukoDt());
// 2006.01.24 Y.Inaba Mod ↑
						Keepb.setSyohin_kanji_rn_mae(mstchk.getCdName());
					}
				}

				//仕入先コード存在チェック
				lst.clear();
				//業種コードが生鮮の場合
				if (SysUserBean.getSelectedGyosyuCd().equals("04")) {
					lst.add( "KANRI_KB = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");
					lst.add( " AND KANRI_CD = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "' ");
				} else {
					lst.add( "KANRI_KB = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
					lst.add( " AND KANRI_CD = '" + Keepb.getKanriCd() + "' ");
				}
				lst.add( " AND SIIRESAKI_CD = '" + Keepb.getSihaiCd() + "' ");
				lst.add( " AND TOSAN_KB = '"+ mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() +"'");
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SIIRESAKI","KANJI_RN", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SIIRESAKI","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//仕入先コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_shihaicd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus("ct_shihaicd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40024").toString());
					}
					Keepb.setSihaiKanjiRn("");
				} else {
					Keepb.setSihaiKanjiRn(mstchk.getCdName());
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName2(db, "R_SIIRESAKI","KANJI_RN", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName2(db, "R_SIIRESAKI","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setSihai_kanji_rn_mae(mstchk.getCdName());
				}

				ResultSet rst = null;
				String l_gyosyucd = "";
				//処理状況＝新規
				if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
					//大業種コード
					StringBuffer sb = new StringBuffer();

					sb.append(" select distinct ");
					sb.append("   d_gyosyu_cd ");
					sb.append(" from ");
					sb.append("   r_syohin_taikei ");
					sb.append(" where ");
					sb.append("   hanku_cd = '" + dataHolder.getParameter("ct_kanricd") + "' ");
					rst = db.executeQuery(sb.toString());
					if (rst.next()) {
						if (rst.getString("d_gyosyu_cd").equals("0011")
							|| rst.getString("d_gyosyu_cd").equals("0022")
							|| rst.getString("d_gyosyu_cd").equals("0033")) {
							l_gyosyucd = rst.getString("d_gyosyu_cd");
						} else if (rst.getString("d_gyosyu_cd").equals("0044")) {
							l_gyosyucd = "0011";
						} else {
							l_gyosyucd = "0022";
						}
						dataHolder.updateParameterValue("h_l_gyosyucd",l_gyosyucd);
					}
					//店配列コード存在チェック
					lst.clear();
					lst.add( "L_GYOSYU_CD = '" + dataHolder.getParameter("h_l_gyosyucd") + "' ");
					lst.add( " AND YOTO_KB = '" + mst003901_YotoKbDictionary.BUTURYU.getCode() + "' ");
					lst.add( " AND GROUPNO_CD = " + Keepb.getGroupnoCd() + " ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO","NAME_NA", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO","NAME_NA", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//店配列コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_groupnocd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_groupnocd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された店配列コード"+map.get("40007").toString());
						}
						Keepb.setGroupnpNameNa("");
					} else {
						Keepb.setGroupnpNameNa(mstchk.getCdName());
					}
					//グルーピングマスタ存在チェック
					lst.clear();
					lst.add( "L_GYOSYU_CD = '" + dataHolder.getParameter("h_l_gyosyucd") + "' ");
					lst.add( " AND YOTO_KB = '" + mst003901_YotoKbDictionary.BUTURYU.getCode() + "' ");
					lst.add( " AND GROUPNO_CD = " + Keepb.getGroupnoCd() + " ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUP","RANK_NB", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUP","RANK_NB", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//グルーピングマスタ存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_groupnocd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_groupnocd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40607").toString());
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * 有効日チェックを行う。
	 * @param      mst570301_KeepBean Keepb    : キープビーン
	 * @param		String[] 	CtrlName 		: コントロール名
	 * @param      Map         map             : コントロールカラー
	 * @param      SessionManager sessionManager : セッションマネージャ
	 * @param		String		tableNa			: 対象テーブル名称
	 * @param		String		columnNa		: 有効日カラム名称
	 * @param		List        whereList		: 有効日を除くKEY部
	 * @param		String		addValue		: 暦日加算値
	 */
// 2006.02.21 Y.Inaba 引数追加 ↓
//   public void YukoDtCheck( mst570301_KeepBean Keepb ,String[] CtrlName ,Map CtrlColor
//					   ,SessionManager sessionManager ,String tableNa ,String columnNa
//					   ,List whereList, String addValue, mst000101_DbmsBean db ) throws Exception, SQLException {
	public void YukoDtCheck( mst570301_KeepBean Keepb ,String[] CtrlName ,Map CtrlColor
						,SessionManager sessionManager ,String tableNa ,String columnNa
						,List whereList, String addValue, mst000101_DbmsBean db ,String sihaiKb, String syohinCd, String tenpoCd ) throws Exception, SQLException {
// 2006.02.21 Y.Inaba 引数追加 ↑
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		String MSTDATE = "";

		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)){
// 2006.01.20 Y.Inaba Add ↓
			if(mst000401_LogicBean.chkNullString(Keepb.getYukoDt()).equals("")){
				MSTDATE = RMSTDATEUtil.getMstDateDt();
			} else{
				MSTDATE = Keepb.getYukoDt();
			}
// 2006.01.20 Y.Inaba Add ↑
			//検索&更新処理チェック
// 2006.01.30 Y.Inaba Mod ↓
//			String chks = mst001001_EffectiveDayBean.getYukoDtCheck( tableNa,columnNa,whereList,addValue,Keepb.getYukoDt(), Keepb, db );
//			String chks = mst000207_ButuryukeiroEffectiveDayCheckBean.getYukoDtCheck(tableNa,Keepb.getYukoDt(),Keepb.getKanriKb(),Keepb.getKanriCd(),Keepb.getSihaiKb(),Keepb.getSihaiCd(),
//																						Keepb.getSyohinCd(),Keepb.getButuryuKb(),Keepb.getTenpoCd(),Keepb,db,addValue);
			String chks = mst000207_ButuryukeiroEffectiveDayCheckBean.getYukoDtCheck(tableNa,Keepb.getYukoDt(),Keepb.getKanriKb(),Keepb.getKanriCd(),sihaiKb,Keepb.getSihaiCd(),
																						syohinCd,Keepb.getButuryuKb(),tenpoCd,Keepb,db,addValue);
// 2006.01.30 Y.Inaba Mod ↑
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
			} else if ( chks.equals(mst000101_ConstDictionary.ERR_CHK_ERROR) ){
/*
				for ( int i = 0;i < CtrlName.length;i++ ){
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtrlName[i]);
				}
*/
			}
		}
	}
}
