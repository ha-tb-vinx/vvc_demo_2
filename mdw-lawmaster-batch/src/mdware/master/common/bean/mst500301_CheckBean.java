/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  発注納品基準日マスタ用の入力チェックメソッドを集めたクラス</P>
 * <P>説明 : 新ＭＤシステムで使用する発注納品基準日マスタ用のチェックメソッドを集めたクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius M.Yamada
 * @version 1.0
 * @see なし
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
import mdware.master.common.dictionary.mst008601_SentakuGyosyuCdDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  発注納品基準日マスタ用の入力チェックメソッドを集めたクラス</P>
 * <P>説明 : 新ＭＤシステムで使用する発注納品基準日マスタ用のチェックメソッドを集めたクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @see なし
 */
public class mst500301_CheckBean {
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		DataHolder 	Keepb           :
	 * @param 		DataHolder 	dataHolder      :
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
	public void check(
		mst000101_DbmsBean db,
		mst500201_KeepBean Keepb,
		DataHolder         dataHolder,
		String             kengenCd,
		String             GamenId,
		String             FirstFocusCtl,
		String []          CtrlName,
		Map                CtrlColor,
		SessionManager     sessionManager,
		String             tableNa,
		String             columnNa,
		List               whereList,
		String             addValue) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
			//画面内容を保存
			Keepb.setKanriKb	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanri_kb")).trim() );
			Keepb.setKanriCd	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanri_cd")).trim() );
			Keepb.setKanriNm	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanri_nm")).trim() );
//↓↓　仕様追加による変更（2005.06.27）　↓↓
			if (mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_cd")).trim().equals("000000")) {
				Keepb.setSiiresakiCd("");
			} else {
				Keepb.setSiiresakiCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_cd")).trim() );
			}
//↑↑　仕様追加による変更（2005.06.27）　↑↑
			Keepb.setSiiresakiNm( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_nm")).trim() );
			Keepb.setHachuHohoKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachu_hoho_kb")).trim() );
			Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syorikb")).trim() );
			Keepb.setYukoDt		( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yuko_dt")).trim() );
			Keepb.setHachuSimeTimeKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_hachu_sime_time_kb")).trim() );
			Keepb.setHachuMonFg 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachu_mon_fg")).trim() );
			Keepb.setHachuTueFg 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachu_tue_fg")).trim() );
			Keepb.setHachuWedFg 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachu_wed_fg")).trim() );
			Keepb.setHachuThuFg 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachu_thu_fg")).trim() );
			Keepb.setHachuFriFg 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachu_fri_fg")).trim() );
			Keepb.setHachuSatFg 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachu_sat_fg")).trim() );
			Keepb.setHachuSunFg 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachu_sun_fg")).trim() );
			Keepb.setRtimeMonKb 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_rtime_mon_kb")).trim() );
			Keepb.setRtimeTueKb 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_rtime_tue_kb")).trim() );
			Keepb.setRtimeWedKb 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_rtime_wed_kb")).trim() );
			Keepb.setRtimeThuKb 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_rtime_thu_kb")).trim() );
			Keepb.setRtimeFriKb 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_rtime_fri_kb")).trim() );
			Keepb.setRtimeSatKb 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_rtime_sat_kb")).trim() );
			Keepb.setRtimeSunKb 	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_rtime_sun_kb")).trim() );
			Keepb.setIncHachuMonFg ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_inc_hachu_mon_fg")).trim() );
			Keepb.setIncHachuTueFg ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_inc_hachu_tue_fg")).trim() );
			Keepb.setIncHachuWedFg ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_inc_hachu_wed_fg")).trim() );
			Keepb.setIncHachuThuFg ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_inc_hachu_thu_fg")).trim() );
			Keepb.setIncHachuFriFg ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_inc_hachu_fri_fg")).trim() );
			Keepb.setIncHachuSatFg ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_inc_hachu_sat_fg")).trim() );
			Keepb.setIncHachuSunFg ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_inc_hachu_sun_fg")).trim() );
			Keepb.setIncRtimeMonKb ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_inc_rtime_mon_kb")).trim() );
			Keepb.setIncRtimeTueKb ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_inc_rtime_tue_kb")).trim() );
			Keepb.setIncRtimeWedKb ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_inc_rtime_wed_kb")).trim() );
			Keepb.setIncRtimeThuKb ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_inc_rtime_thu_kb")).trim() );
			Keepb.setIncRtimeFriKb ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_inc_rtime_fri_kb")).trim() );
			Keepb.setIncRtimeSatKb ( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_inc_rtime_sat_kb")).trim() );
			Keepb.setIncRtimeSunKb	( mst000401_LogicBean.chkNullString(dataHolder.getParameter("se_inc_rtime_sun_kb")).trim() );

 			Keepb.setMode( mst000401_LogicBean.chkNullString(dataHolder.getParameter("mode")).trim() );
			Keepb.setExistFlg("");
			Keepb.setDecisionFlg(mst000101_ConstDictionary.FUNCTION_TRUE);

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			String INIT_PAGE = Keepb.getErrorBackDisp();
			Keepb.setErrorBackDisp( "" );

			//マスタ日付取得
			String MSTDATE = "";

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//メーニューバーアイコン取得
			// SysSosasyaSesson取得
			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
			Map menuMap = new HashMap();
			menuMap.put("gamen_id",GamenId);
			menuMap.put("kengen_cd",kengenCd);
			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
			Keepb.setMenuBar(menu);

			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			String name = "";				//戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

			if (! Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)) {
				//　　検索条件チェック START　　/////////////////
				// 有効日範囲チェック(マスタ日付より1年後まで入力可能)
				// 照会時はチェックを行わない
				String[] strReturn = new String[3];
				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						Keepb.setErrorBackDisp( INIT_PAGE );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("tx_yuko_dt");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_yuko_dt");
						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
					}
				}

				// 種別の取得
				String Syubetu = new String();
				if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanri_kb")).trim().equals(mst000901_KanriKbDictionary.HINSYU.getCode()) ){
					Syubetu = mst000101_ConstDictionary.BUNRUI3; // LARGE_TYPE_OF_BUSINESS;
				} else if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanri_kb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
					Syubetu = mst000101_ConstDictionary.BUNRUI3;
				}

				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				//管理コード存在チェック
				lst.add( "SYUBETU_NO_CD = " + MessageUtil.getMessage(Syubetu, userLocal) );
				lst.add( " and "+ " CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanri_cd")) + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
// 2006.01.24 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						//管理コード存在エラー
						Keepb.setErrorBackDisp( INIT_PAGE );
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_kanri_cd");
						Keepb.setFirstFocus("tx_kanri_cd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された管理コード"+map.get("40007").toString());
					}
				} else {
					Keepb.setKanriNm(mstchk.getCdName()); //Keepb.setKanriKanjiRn(mstchk.getCdName());
//					if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
					if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanri_kb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//						↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//						if(!mst000401_LogicBean.getHankuCdCheck(db,mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanri_cd")),sessionManager)){
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setErrorBackDisp( INIT_PAGE );
//								mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_kanri_cd");
//								Keepb.setFirstFocus("tx_kanri_cd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40022").toString());
//							}
//						}
//						↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
					} else {
						if (!mst000401_LogicBean.getHinshuCdCheck(db,mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanri_cd")),sessionManager)) {
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setErrorBackDisp( INIT_PAGE );
								mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_kanri_cd");
								Keepb.setFirstFocus("tx_kanri_cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("40023").toString());
							}
						}
					}
				}
//　↓↓　仕様追加＜if文のみ追加＞（2005.06.24）　↓↓
				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_cd")).trim().equals("000000")) {
					//仕入先コード存在チェック
					lst.clear();
					List whereList2  = new ArrayList();
					String hankuCode  = mst000901_KanriKbDictionary.HANKU.getCode();  // 管理区分:販区
					String hinsyuCode = mst000901_KanriKbDictionary.HINSYU.getCode(); // 管理区分:品種
// 2005.09.08 Inaba 生鮮選択時の存在チェック追加 START
					//生鮮の時
					if(SysUserBean.getSelectedGyosyuCd().equals(mst008601_SentakuGyosyuCdDictionary.SEL_FRE.getCode())){
						lst.add(" KANRI_KB = '"
							+ mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");
						lst.add(" AND KANRI_CD = '"
							+ mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "' ");
					} else{
//	2005.09.08 Inaba 生鮮選択時の存在チェック追加 END
					//生鮮以外
						//販区
						if (mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanri_kb")).trim().equals(hankuCode)) {
							lst.add(" KANRI_KB = '"
								+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanri_kb")).trim() + "' ");
							lst.add(" AND KANRI_CD = '"
								+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanri_cd")).trim() + "' ");
						} else {
						//品種
							whereList2.add(" HINSYU_CD = '" +
								mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanri_cd")).trim() + "' ");
							mstchk = new mst000701_MasterInfoBean();
							StringBuffer sb = new StringBuffer();
							sb.append("(SELECT HINSYU_CD, HANKU_CD, 0 AS DELETE_FG FROM R_SYOHIN_TAIKEI)");
// 2006.01.24 Y.Inaba Mod ↓
//							mstchk = mst000401_LogicBean.getMasterCdName(db,sb.toString(),"HANKU_CD", whereList2, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
							mstchk = mst000401_LogicBean.getMasterCdName(db,sb.toString(),"HANKU_CD", whereList2, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
// 2006.01.24 Y.Inaba Mod ↑
							lst.add(" KANRI_KB = '" + hankuCode + "' ");
							lst.add(" AND KANRI_CD = '"
								+ mst000401_LogicBean.chkNullString(mstchk.getCdName()).trim() + "' ");
						}
					}
					lst.add( " and "  );
					lst.add(" TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
					lst.add( " and "  );
					lst.add( "SIIRESAKI_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_cd")) + "' " );
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setSiiresakiNm(mstchk.getCdName());
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//仕入先コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_siiresaki_cd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setErrorBackDisp( INIT_PAGE );
							Keepb.setFirstFocus("tx_siiresaki_cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40024").toString());
						}
					}
				}
// ↑↑　仕様追加（2005.06.24）　↑↑

				//　　検索条件チェック END　　/////////////////
				///////////////////////////////////////////////
				// 有効日のチェック
				///////////////////////////////////////////////
// 2006.01.20 Y.Inaba Mod ↓
				if(mst000401_LogicBean.chkNullString(Keepb.getYukoDt()).equals("")){
					MSTDATE = RMSTDATEUtil.getMstDateDt();
				} else{
					MSTDATE = Keepb.getYukoDt();
				}
//				String chks = mst001001_EffectiveDayBean.getYukoDtCheck( tableNa, columnNa,	whereList, addValue,
//						mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yuko_dt")).trim(), Keepb, db );
//				String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck(tableNa,MSTDATE,Keepb,db,addValue,true);
				String chks = mst000205_HachunohinkijunbiEffectiveDayCheckBean.getYukoDtCheck
										(tableNa,MSTDATE,Keepb.getKanriKb(),Keepb.getKanriCd(),dataHolder.getParameter("tx_siiresaki_cd"),
											Keepb.getHachuHohoKb(),Keepb,db,addValue);
// 2006.01.20 Y.Inaba Mod ↑
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
					for ( int i = 0;i<CtrlName.length;i++ ){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,CtrlName[i]);
					}
				}
				///////////////////////////////////////////////
				// end of 有効日のチェック
				///////////////////////////////////////////////
				//新規・修正時エラーチェック
				if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)
					|| Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
				}
				if ( Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)) {
					//削除時エラーチェック
					if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
					}
				}
			}
		}
	}
}