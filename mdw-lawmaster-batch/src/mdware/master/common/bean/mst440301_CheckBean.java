package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst001001_ShihaiKbDictionary;
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;
import mdware.master.common.dictionary.mst009101_SiireSystemKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.portal.bean.MdwareUserSessionBean;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst440301_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
	public void Check(  mst000101_DbmsBean 	db
						,mst440201_KeepBean		Keepb
						,DataHolder				dataHolder
//						,String					kengenCd
						,String					GamenId
						,String					FirstFocusCtl
						,String[]				CtrlName
						,Map					CtrlColor
						,String					tableNa
						,String					columnNa
						,List					whereList
						,String					addValue
						,SessionManager sessionManager) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		//エラーチェック
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//画面内容を保存
//		   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//			Keepb.setKanriKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")));			//管理区分
//			Keepb.setKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")));			//管理コード
			Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")));		//部門コード
//			Keepb.setKanriNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrinm")));			//管理コード名
			Keepb.setTikuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")));			//地区コード
			Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")));	//仕入先コード
			Keepb.setSiiresakiNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakinm")));	//仕入先名
//			Keepb.setCopyKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copykanricd")));	//コピー管理コード
//			Keepb.setCopyKanriNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copykanrinm")));	//コピー管理コード名
			Keepb.setCopyBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copybumoncd")));	//コピー部門コード
			Keepb.setCopyBumonNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copykanrinm")));	//コピー部門コード名
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
			Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim() );

			//KeepBean設定処理
			Keepb.setKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanjina")));							//正式名称(漢字)
			Keepb.setKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanana")));							//正式名称(カナ)
			Keepb.setKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanjirn")));							//略式名称(漢字)
			Keepb.setKanaRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanarn")));							//略式名称(カナ)
			Keepb.setYubinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yubincd")));							//郵便番号
			Keepb.setAddressKanji1Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_addresskanji1na")));		//住所(漢字)1
			Keepb.setAddressKanji2Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_addresskanji2na")));		//住所(漢字)2
			Keepb.setAddressKana1Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_addresskana1na")));			//住所(カナ)1
			Keepb.setAddressKana2Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_addresskana2na")));			//住所(カナ)2
			Keepb.setMadoguchiTantoNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_madoguchitantona")));		//窓口担当者
			Keepb.setTel1Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tel1na")));							//通常連絡先TEL1
			Keepb.setTel2Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tel2na")));							//通常連絡先TEL2
			Keepb.setFax1Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_fax1na")));							//通常連絡先FAX1
			Keepb.setFax2Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_fax2na")));							//通常連絡先FAX2
			Keepb.setNightTelNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nighttelna")));					//夜間連絡先TEL(緊急)
			Keepb.setEmailNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_emailna")));							//E-MAIL
			Keepb.setJohosyoriSeikyuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_johosyoriseikyukb")));		//情報処理料金請求区分
			Keepb.setSyhincdPrintKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syhincdprintkb")));			//商品コード印字区分
			Keepb.setTaghakoGyosyaKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_taghakogyosyakb")));			//タグ発行代行業者
// ↓↓削除（2005.06.30）↓↓
//			Keepb.setHakoBasyoNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hakobasyona")));					//発行場所
// ↑↑削除（2005.06.30）↑↑
			if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinbanbetudenpyokb")).equals("")){
				Keepb.setHinbanbetuDenpyoKb("0");										//品番別伝票区分
			} else {
				Keepb.setHinbanbetuDenpyoKb(mst000101_ConstDictionary.SENTAKU_CHOICE);	//品番別伝票区分
			}

			Keepb.setMemoTx(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_memotx")));							//メモ欄

//			if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tosankb")).equals("")){
//				Keepb.setTosanKb("0");																//倒産区分
//			} else {
//				Keepb.setTosanKb(mst000101_ConstDictionary.SENTAKU_CHOICE);							//倒産区分
//			}
			if (!mst000101_ConstDictionary.CHECK_SEARCH.equals(Keepb.getFlg())){
				if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tosankb")).equals("")){							//倒産区分
					Keepb.setTosanKb(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode());
				} else {
					Keepb.setTosanKb(mst003701_TousanKbDictionary.TOUSAN.getCode());
				}
			}else{

				Keepb.setTosanKb(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode());
				Keepb.setFlg("");
			}


//		    ↓↓2006.08.09 jianglm カスタマイズ修正↓↓
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){

				Keepb.setTosanKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_tousan")));
			}
//			↑↑2006.08.09 jianglm カスタマイズ修正↑↑

//	       ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
			Keepb.setAsutexiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_asutexikb")));						//アスティ区分
			Keepb.setHanbaiKeiyakuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaikeiyakukb")));	//販売データ契約区分
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

			Keepb.setInsertTs(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_insertts")));						    //作成年月日
			Keepb.setInsertUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_insert_userid")));				//作成者社員ID
			Keepb.setUpdateTs(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_updatets")));						//更新年月日
			Keepb.setUpdateUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_updateuserid")));				//更新者社員ID
			Keepb.setDeleteFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_deletefg")));						//削除フラグ
// ↓↓仕様追加による変更（2005.05.26）
			Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));								//EOS区分
// ↑↑仕様追加による変更（2005.05.26）

//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//			Keepb.setKakinSyubetuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kakinsyubetucd")));			//課金種別コード
//↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//↑↑2006.06.22 wangzhg カスタマイズ修正↑↑


//			 ADD by Tanigawa 2006/9/25 障害票№0036対応 START
//			Keepb.setInsertUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameterValues("gyosyu_kb_j"+mst000611_SystemKbDictionary.T.getCode())));				//作成者社員ID
			Keepb.setBPos(dataHolder.getParameterValues(mst009101_SiireSystemKbDictionary.POS.toString()).length == 1 );		//チェックされている場合：true, されていない場合：false
			Keepb.setBTag(dataHolder.getParameterValues(mst009101_SiireSystemKbDictionary.TAG.toString()).length == 1 );		//チェックされている場合：true, されていない場合：false
//			 ADD by Tanigawa 2006/9/25 障害票№0036対応  END

//			 ADD by Tanigawa 2006/9/27 障害票№0057対応 コピー地区コード追加 START
			Keepb.setCopyChikuCd(dataHolder.getParameter("ct_copytikucd"));
//			 ADD by Tanigawa 2006/9/27 障害票№0057対応 コピー地区コード追加  END

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//メーニューバーアイコン取得
			// userSession取得
//		    ↓↓2006.06.15 wangzhg カスタマイズ修正↓↓
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);
//			↑↑2006.06.15 wangzhg カスタマイズ修正↑↑

			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			List lst2 = new ArrayList();	//マスタ存在チェック抽出条件
			String name = "";				//戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

//		    ↓↓2006.06.21 wangzhg カスタマイズ修正↓↓
//			 //種別の取得
//			 String Syubetu = new String();
//			 if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()) ){
//				 Syubetu = mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS;
//		 	 } else if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//				 Syubetu = mst000101_ConstDictionary.H_SALE;
//			 }
//			↑↑2006.06.21 wangzhg カスタマイズ修正↑↑

//// ↓↓仕様変更（2005.09.01）↓↓
//			//業種による管理区分のコントロール名取得
//			String kanriKbCtl = "";
//			if (SysUserBean.getSelectedGyosyuCd().equals("04")) {
//				kanriKbCtl = "ct_kanrikb(0)";
//			} else {
//				kanriKbCtl = "ct_kanrikb(1)";
//			}
// ↑↑仕様変更（2005.09.01）↑↑

//		   ↓↓2006.06.21 wangzhg カスタマイズ修正↓↓
//			//管理コード存在チェック
//			lst = new ArrayList();
//			mstchk = new mst000701_MasterInfoBean();
//			lst.add( "SYUBETU_NO_CD = " + Syubetu );
//			lst.add( " and "+ " CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
//// 2006.01.24 Y.Inaba Mod ↑
//			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				//管理コード存在エラー
//				mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//				Keepb.setFirstFocus("ct_kanricd");
//				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					Keepb.setErrorMessage("指定された管理コード"+map.get("40007").toString());
//				}
//			} else {
//				Keepb.setKanriNm(mstchk.getCdName());
//				if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//					if(!mst000401_LogicBean.getHankuCdCheck(db,mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")),sessionManager)){
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//						Keepb.setFirstFocus("ct_kanricd");
//						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage(map.get("40022").toString());
//						}
//					}
//				}
//			}
//		   ↑↑2006.06.21 wangzhg カスタマイズ修正↑↑

			//コードチェック
//			↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//			//コピー販区コード存在チェック
//			if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//				if( !(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copykanricd")).trim().equals("")) ){
//					//コピー販区コード存在チェック
//					lst.clear();
//					lst.add( "SYUBETU_NO_CD = '" + mst000901_KanriKbDictionary.BUMON +"' " );
//					lst.add( " and " );
//					lst.add( "CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copykanricd")) + "' " );
//// 2006.01.24 Y.Inaba Mod ↓
////					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
//// 2006.01.24 Y.Inaba Mod ↑
//					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//						//管理コード存在エラー
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_copykanricd");
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus("ct_copykanricd");
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage("指定されたコピー販区"+map.get("40007").toString());
//						}
//					} else {
//						Keepb.setCopyKanriNm(mstchk.getCdName());
//						if(!mst000401_LogicBean.getHankuCdCheck(db,mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copykanricd")),sessionManager)){
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_copykanricd");
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setFirstFocus("ct_copykanricd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40022").toString());
//							}
//						}
//					}
//				}
//			}
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//		   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//			//仕入先コード存在チェック
//			lst = new ArrayList();
//			mstchk = new mst000701_MasterInfoBean();

//			//業種コード取得
//			String gyosyuID = SysUserBean.getSelectedGyosyuCd();
//			//業種が生鮮の場合
//			if (gyosyuID.equals("04")) {
//				lst.add( "     KANRI_KB = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");
//				lst.add( " and KANRI_CD = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "' ");
//			//業種が生鮮以外の場合
//			} else {
//				lst.add( "     KANRI_KB = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")) + "' ");
//				lst.add( " and KANRI_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//			}
//			lst.add(" and TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//			lst.add(" and SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, "0");
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, "0", "");
//// 2006.01.24 Y.Inaba Mod ↓
//			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				Keepb.setSiiresakiNm("");
//			} else {
//				Keepb.setSiiresakiNm(mstchk.getCdName());
//			}
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//		   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
			//仕入先コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();

			//業種が生鮮以外の場合
			lst.add( "     KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
			lst.add( " and KANRI_CD = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),4) + "' ");
//			   ↓↓2006.08.08 jianglm カスタマイズ修正↓↓
//			lst.add(" and TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//			   ↑↑2006.08.08 jianglm カスタマイズ修正↑↑
			lst.add(" and SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))
					+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")) + "' ");

			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, "0", "");

			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				Keepb.setSiiresakiNm("");
			} else {
				Keepb.setSiiresakiNm(mstchk.getCdName());
			}
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//		   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
			//コピー部門コード存在チェック
			if( !(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copybumoncd")).trim().equals("")) ){
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				//コピー部門コード存在チェック
				lst.clear();
				lst.add( "SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) +"' " );
				lst.add( " and " );
				lst.add( "CODE_CD = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copybumoncd")),4) + "' " );

				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");

				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//部門コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_copybumoncd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus("ct_copybumoncd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたコピー部門コード"+map.get("40007").toString());
					}
				} else {
					Keepb.setCopyBumonNm(mstchk.getCdName());
				}
			}
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑


			//ADD by Tanigawa 2006/9/25 障害票№0036対応  START
			/********************************************************************
			 ********************************************************************
			 ****選択した仕入先システム区分の中に「タグ衣料」が含まれる場合、****
			 ****(画面から取得した仕入先コード、仕入先システム区分：2か3)の  ****
			 ****検索条件を元に、仕入先マスタを検索する。                    ****
			 ****仕入先マスタに重複するデータがある場合は、エラー。          ****
			 ********************************************************************
			 ****検索、登録、更新、削除のいずれの処理時にも、                ****
			 ****本メソッドが呼び出されているため、JobIDを参照して           ****
			 ****検索時"以外"の場合は、チェックロジックを通る様にしています。****
			 ********************************************************************
			 ********************************************************************/

			if( dataHolder.getParameter("JobID").equals("mst440301_SiiresakiInsert") || dataHolder.getParameter("JobID").equals("mst440401_SiiresakiUpdate") ){

				boolean bposTagCheck = (
				(dataHolder.getParameterValues(mst009101_SiireSystemKbDictionary.POS.toString()).length == 1) ||
				(dataHolder.getParameterValues(mst009101_SiireSystemKbDictionary.TAG.toString()).length == 1)
				);

				//POS/TAGのチェックボックスのいずれにもチェックが入っていない場合、エラー
				if( !bposTagCheck ){
					//エラー!!!
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(mst009101_SiireSystemKbDictionary.POS.toString());
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("業種"+map.get("0026").toString());	//業種を選択して下さい。
					}
				}

				mst009101_SiireSystemKbDictionary gmnSiireSystemKb = getSiireSystemKb(Keepb.isPosChecked(), Keepb.isTagChecked());
				//「タグ衣料」が選択されている場合、
				if( mst009101_SiireSystemKbDictionary.TAG.equals(gmnSiireSystemKb) || mst009101_SiireSystemKbDictionary.POS_TAG.equals(gmnSiireSystemKb) ){

//					boolean bSiiresakiExists = this.siiresakiCdCheckWhenTagIsSelected(Keepb.getSiiresakiCd());
					//ADD by Tanigawa 2006/10/08 障害票№0133対応 既存データをPOS→タグ衣料、POS→POSとタグ衣料 に修正した場合に、前6桁が重複したタグ衣料データが存在しているとエラー START
					boolean bSiiresakiExists = this.siiresakiCdCheckWhenTagIsSelected(dataHolder.getParameter("JobID"), Keepb.getSiiresakiCd(), Keepb.getTikuCd());
					//ADD by Tanigawa 2006/10/08 障害票№0133対応 既存データをPOS→タグ衣料、POS→POSとタグ衣料 に修正した場合に、前6桁が重複したタグ衣料データが存在しているとエラー  END

					//仕入先コードをチェックして、仕入先コードがマスタ上に存在し、登録しようとしている場合
					if( bSiiresakiExists ){

						//エラー!!!
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus(mst009101_SiireSystemKbDictionary.POS.toString());
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40001").toString());	//指定されたデータは既に存在しています。
						}
					}
				}
			}
			//ADD by Tanigawa 2006/9/25 障害票№0036対応   END


			//削除時、他テーブル存在チェック
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) ||
				Keepb.getTosanKb().equals(mst003701_TousanKbDictionary.TOUSAN.getCode())){
				String yukoDt = "";
				boolean checkflg = true;
				StringBuffer sb1 = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				List lstCol = new ArrayList();
				List lstRow = new ArrayList();

				//商品マスタ
				lst = new ArrayList();
				lst2 = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();

				sb1 = new StringBuffer();
				sb2 = new StringBuffer();

//			   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//				if(mst000901_KanriKbDictionary.BUMON.equals(mst000101_ConstDictionary.H_SALE)){
//
//					//販区の場合
//					sb1.append(" SELECT ");
//					sb1.append("		DISTINCT ");
//					sb1.append("		HANKU_CD, ");
//					sb1.append("		SYOHIN_CD ");
//					sb1.append("	FROM ");
//					sb1.append("		R_SYOHIN ");
//					sb1.append("	WHERE ");
//					sb1.append("		HANKU_CD     = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//					sb1.append("	and SIIRESAKI_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//					sb1.append("	and DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//					sb1.append("ORDER BY ");
//					sb1.append(" 	HANKU_CD, ");
//					sb1.append("	SYOHIN_CD ");
//			   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//				   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
					sb1.append(" SELECT ");
					sb1.append("		DISTINCT ");
					sb1.append("		A.BUMON_CD, ");
					sb1.append("		A.SYOHIN_CD ");
					sb1.append("	FROM ");
					sb1.append("		R_SYOHIN A ");
//					sb1.append("		R_SIIRESAKI B ");
					sb1.append("	WHERE ");
//					sb1.append( "     B.KANRI_KB      = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//					sb1.append( " and B.KANRI_CD      = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),4) + "' ");
//					sb1.append( " and B.SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))
//							+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")) + "' ");
					sb1.append( " A.BUMON_CD      = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),4) + "' ");
					sb1.append( " and A.SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))
							+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")) + "' ");
					sb1.append("	and A.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//					sb1.append("	and B.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
					sb1.append("ORDER BY ");
					sb1.append("		A.BUMON_CD, ");
					sb1.append("	A.SYOHIN_CD ");
//				} else {
//					//業種の場合
//					sb1.append(" SELECT ");
//					sb1.append("		DISTINCT ");
//					sb1.append("		S.HANKU_CD, ");
//					sb1.append("		S.SYOHIN_CD ");
//					sb1.append("	FROM ");
//					sb1.append("		R_SYOHIN S, ");
//					sb1.append("		R_SYOHIN_TAIKEI T ");
//					sb1.append("	WHERE ");
//					sb1.append("		S.HANKU_CD = T.HANKU_CD ");
//					sb1.append("	and T.D_GYOSYU_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//					sb1.append("	and S.SIIRESAKI_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//					sb1.append("	and S.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//					sb1.append("ORDER BY ");
//					sb1.append(" 	S.HANKU_CD, ");
//					sb1.append("	S.SYOHIN_CD ");
//				}
//			   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

				rset = db.executeQuery(sb1.toString());    //抽出結果(ResultSet)
// 2006.01.20 Y.Inaba Add ↓
				//マスタ日付取得
				String MSTDATE = RMSTDATEUtil.getMstDateDt();
// 2006.01.20 Y.Inaba Add ↑

				lstRow = new ArrayList();
				while(rset.next()){
					lstCol = new ArrayList();
//					↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("hanku_cd")).trim());
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("bumon_cd")).trim());
//					↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("syohin_cd")).trim());
					lstRow.add(lstCol);
				}
				for(int i=0;i<lstRow.size();i++){
					List lstRow2 = (List)lstRow.get(i);
					lst = new ArrayList();
					sb2 = new StringBuffer();

//					↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					String hankuCd  = (String)lstRow2.get(0);
//					String syohinCd = (String)lstRow2.get(1);
					String bumonCd  = (String)lstRow2.get(0);
					String syohinCd = (String)lstRow2.get(1);
//					lst.add( "     HANKU_CD  = '" + hankuCd  + "' ");
//					lst.add( " and SYOHIN_CD = '" + syohinCd + "' ");
					lst.add( "     BUMON_CD  = '" + bumonCd  + "' ");
					lst.add( " and SYOHIN_CD = '" + syohinCd + "' ");
//					↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
// 2006.01.20 Y.Inaba Mod ↓
//					yukoDt = mst001001_EffectiveDayBean.getGenYoyaku("R_SYOHIN","YUKO_DT",lst,"0",db);
					yukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db,"R_SYOHIN","YUKO_DT",lst,MSTDATE,true);
// 2006.01.20 Y.Inaba Mod ↑
					if(yukoDt.equals("")){
						//将来レコードチェック
						sb2.append(" SELECT ");
						sb2.append(" 		* ");
						sb2.append(" FROM ");
						sb2.append(" 		R_SYOHIN ");
						sb2.append(" WHERE ");
//						↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//						sb2.append("		HANKU_CD  = '" + hankuCd  + "' ");
//						sb2.append("	and SYOHIN_CD = '" + syohinCd + "' ");
						sb2.append( "  BUMON_CD      = '" + bumonCd + "' ");
						sb2.append(" and	SYOHIN_CD = '" + syohinCd + "' ");
//						↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
// 2006.01.20 Y.Inaba Mod ↓
//						sb2.append("	and YUKO_DT   > to_char(sysdate,'YYYYMMDD') ");
						sb2.append("	and YUKO_DT   > '" + MSTDATE + "' ");
// 2006.01.20 Y.Inaba Mod ↑
						ResultSet rset2 = db.executeQuery(sb2.toString());

						if(rset2.next()){
							//存在エラー
							checkflg = false;
							break;
						}
					} else {
						//SYSDATE以前の削除データよりも過去の日付は対象外
						sb2.append(" SELECT ");
						sb2.append(" 		*");
						sb2.append(" FROM ");
						sb2.append(" 		R_SYOHIN ");
						sb2.append(" WHERE ");
//						↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//						sb2.append("		HANKU_CD  = '" + hankuCd  + "' ");
//						sb2.append("	and SYOHIN_CD = '" + syohinCd + "' ");
						sb2.append( "     BUMON_CD      = '" + bumonCd + "' ");
						sb2.append("	and SYOHIN_CD = '" + syohinCd + "' ");
//						↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
						sb2.append("	and YUKO_DT   > '" + yukoDt + "' ");
// 2006.01.20 Y.Inaba Mod ↓
//						sb2.append("	and YUKO_DT   < to_char(sysdate,'YYYYMMDD') ");
						sb2.append("	and YUKO_DT   < '" + MSTDATE + "' ");
// 2006.01.20 Y.Inaba Mod ↑
						sb2.append("	and DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");

						ResultSet rset2 = db.executeQuery(sb2.toString());

						if(!rset2.next()){
							//存在エラー
							checkflg = false;
							break;
						}
					}
				}
				if(!checkflg){
					//データ存在エラー
//				   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_tikucd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
					Keepb.setFirstFocus("ct_tikucd");
//				   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
// ↓↓仕様変更（2005.09.01）↓↓
//						Keepb.setFirstFocus("ct_kanrikb(0)");
//						Keepb.setFirstFocus(kanriKbCtl);
// ↑↑仕様変更（2005.09.01）↑↑
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40006").toString());
					}
				}

				//店別商品例外マスタ
				lst = new ArrayList();
				lst2 = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();

				sb1 = new StringBuffer();
				sb2 = new StringBuffer();

//			   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//				if(mst000901_KanriKbDictionary.BUMON.equals(mst000101_ConstDictionary.H_SALE)){

//					//販区の場合
//					sb1.append(" SELECT ");
//					sb1.append("		DISTINCT ");
//					sb1.append("		HANKU_CD, ");
//					sb1.append("		SYOHIN_CD, ");
//					sb1.append("		TENPO_CD ");
//					sb1.append("	FROM ");
//					sb1.append("		R_TENSYOHIN_REIGAI ");
//					sb1.append("	WHERE ");
//					sb1.append("		HANKU_CD     = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//					sb1.append("	and SIIRESAKI_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//					sb1.append("	and DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//					sb1.append("ORDER BY ");
//					sb1.append(" 		HANKU_CD, ");
//					sb1.append("		SYOHIN_CD, ");
//					sb1.append("		TENPO_CD ");

				sb1.append(" SELECT ");
				sb1.append("		DISTINCT ");
				sb1.append("		A.BUMON_CD, ");
				sb1.append("		A.SYOHIN_CD, ");
				sb1.append("		A.TENPO_CD ");
				sb1.append("	FROM ");
				sb1.append("		R_TENSYOHIN_REIGAI A ");
//				sb1.append("		R_SIIRESAKI B ");
				sb1.append("	WHERE ");
//				sb1.append( "     B.KANRI_KB      = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//				sb1.append( " and B.KANRI_CD      = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),4) + "' ");
//				sb1.append(" and B.SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))
//						+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")) + "' ");
				sb1.append( " A.BUMON_CD      = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),4) + "' ");
				sb1.append(" and A.SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))
						+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")) + "' ");
				sb1.append("	and A.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//				sb1.append("	and B.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
				sb1.append("ORDER BY ");
				sb1.append("		A.BUMON_CD, ");
				sb1.append("		A.SYOHIN_CD, ");
				sb1.append("		A.TENPO_CD ");
//			   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑


//			   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//				} else {
//					//業種の場合
//					sb1.append(" SELECT ");
//					sb1.append("		DISTINCT ");
//					sb1.append("		S.HANKU_CD, ");
//					sb1.append("		S.SYOHIN_CD, ");
//					sb1.append("		S.TENPO_CD ");
//					sb1.append("	FROM ");
//					sb1.append("		R_TENSYOHIN_REIGAI S, ");
//					sb1.append("		R_SYOHIN_TAIKEI T ");
//					sb1.append("	WHERE ");
//					sb1.append("		S.HANKU_CD = T.HANKU_CD ");
//					sb1.append("	and T.D_GYOSYU_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//					sb1.append("	and S.SIIRESAKI_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//					sb1.append("	and S.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//					sb1.append("ORDER BY ");
//					sb1.append(" 	S.HANKU_CD, ");
//					sb1.append("	S.SYOHIN_CD ");
//				}
//			   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

				rset = db.executeQuery(sb1.toString());    //抽出結果(ResultSet)

				lstRow = new ArrayList();
				while(rset.next()){
					lstCol = new ArrayList();
//					↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("hanku_cd")).trim());
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("bumon_cd")).trim());
//					↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("syohin_cd")).trim());
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("tenpo_cd")).trim());
					lstRow.add(lstCol);
				}

				for(int i=0;i<lstRow.size();i++){
					List lstRow2 = (List)lstRow.get(i);
					lst = new ArrayList();
					sb2 = new StringBuffer();
//					↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					lst.add( "     HANKU_CD  = '" + lstRow2.get(0) + "' ");
//					lst.add( " and SYOHIN_CD = '" + lstRow2.get(1) + "' ");
//					lst.add( " and TENPO_CD  = '" + lstRow2.get(2) + "' ");
					lst.add( "     BUMON_CD  = '" + lstRow2.get(0) + "' ");
					lst.add( " and SYOHIN_CD = '" + lstRow2.get(1) + "' ");
					lst.add( " and TENPO_CD  = '" + lstRow2.get(2) + "' ");
//					↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
// 2006.01.20 Y.Inaba Mod ↓
//					yukoDt = mst001001_EffectiveDayBean.getGenYoyaku("R_TENSYOHIN_REIGAI","YUKO_DT",lst,"0",db);
					yukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db,"R_TENSYOHIN_REIGAI","YUKO_DT",lst,MSTDATE,true);
// 2006.01.20 Y.Inaba Mod ↑
					if(yukoDt.equals("")){
						//将来レコードチェック
						sb2.append(" SELECT ");
						sb2.append(" 		* ");
						sb2.append(" FROM ");
						sb2.append(" 		R_TENSYOHIN_REIGAI ");
						sb2.append(" WHERE ");
//						↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//						sb2.append("		HANKU_CD  = '" + lstRow2.get(0) + "' ");
//						sb2.append("	and SYOHIN_CD = '" + lstRow2.get(1) + "' ");
//						sb2.append("	and TENPO_CD  = '" + lstRow2.get(2) + "' ");
						sb2.append("		BUMON_CD  = '" + lstRow2.get(0) + "' ");
						sb2.append("	and SYOHIN_CD = '" + lstRow2.get(1) + "' ");
						sb2.append("	and TENPO_CD  = '" + lstRow2.get(2) + "' ");
//						↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
// 2006.01.20 Y.Inaba Mod ↓
//						sb2.append("	and YUKO_DT   > to_char(sysdate,'YYYYMMDD') ");
						sb2.append("	and YUKO_DT   > '" + MSTDATE + "' ");
// 2006.01.20 Y.Inaba Mod ↑

						ResultSet rset2 = db.executeQuery(sb2.toString());

						if(rset2.next()){
							//存在エラー
							checkflg = false;
							break;
						}
					} else {
						//SYSDATE以前の削除データよりも過去の日付は対象外
						sb2.append(" SELECT ");
						sb2.append(" 		*");
						sb2.append(" FROM ");
						sb2.append(" 		R_TENSYOHIN_REIGAI ");
						sb2.append(" WHERE ");
//						↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//						sb2.append("		HANKU_CD  = '" + lstRow2.get(0) + "' ");
//						sb2.append("	and SYOHIN_CD = '" + lstRow2.get(1) + "' ");
//						sb2.append("	and TENPO_CD  = '" + lstRow2.get(2) + "' ");
						sb2.append("	BUMON_CD  = '" + lstRow2.get(0) + "' ");
						sb2.append("	and SYOHIN_CD = '" + lstRow2.get(1) + "' ");
						sb2.append("	and TENPO_CD  = '" + lstRow2.get(2) + "' ");
//						↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
						sb2.append("	and YUKO_DT   > '" + yukoDt + "' ");
// 2006.01.20 Y.Inaba Mod ↓
//						sb2.append("	and YUKO_DT   < to_char(sysdate,'YYYYMMDD') ");
						sb2.append("	and YUKO_DT   < '"+ MSTDATE + "' ");
// 2006.01.20 Y.Inaba Mod ↑
						sb2.append("	and DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");

						ResultSet rset2 = db.executeQuery(sb2.toString());

						if(!rset2.next()){
							//存在エラー
							checkflg = false;
							break;
						}
					}
				}
				if(!checkflg){
					//データ存在エラー
//				   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_tikucd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
					Keepb.setFirstFocus("ct_tikucd");
//				   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
// ↓↓仕様変更（2005.09.01）↓↓
//						Keepb.setFirstFocus("ct_kanrikb(0)");
//						Keepb.setFirstFocus(kanriKbCtl);
// ↑↑仕様変更（2005.09.01）↑↑
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40006").toString());
					}
				}

				//発行管理マスタ
				lst = new ArrayList();
				lst2 = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();

				sb1 = new StringBuffer();
				sb2 = new StringBuffer();

//			   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//				sb1.append(" SELECT ");
//				sb1.append("		DISTINCT ");
//				sb1.append("		KANRI_KB, ");
//				sb1.append("		KANRI_CD, ");
//				sb1.append("		SHIHAI_CD, ");
//				sb1.append("		SHIHAI_KB, ");
//				sb1.append("		TENPO_CD ");
//				sb1.append("	FROM ");
//				sb1.append("		R_HAKOKANRI ");
//				sb1.append("	WHERE ");
//				sb1.append("		KANRI_KB  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")) + "' ");
//				sb1.append("	and KANRI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//				sb1.append("	and SHIHAI_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//				sb1.append("    and SHIHAI_KB = '" + mst001001_ShihaiKbDictionary.SIRESAKI.getCode() + "' ");
//				sb1.append("	and DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//				sb1.append("ORDER BY ");
//				sb1.append("		KANRI_KB, ");
//				sb1.append("		KANRI_CD, ");
//				sb1.append("		SHIHAI_CD, ");
//				sb1.append("		SHIHAI_KB, ");
//				sb1.append("		TENPO_CD ");


				sb1.append(" SELECT ");
				sb1.append("		DISTINCT ");
				sb1.append("		A.KANRI_KB, ");
				sb1.append("		A.KANRI_CD, ");
				sb1.append("		A.SHIHAI_CD, ");
				sb1.append("		A.SHIHAI_KB, ");
				sb1.append("		A.TENPO_CD ");
				sb1.append("	FROM ");
				sb1.append("		R_HAKOKANRI A ");
//				sb1.append("		R_SIIRESAKI B ");
				sb1.append("	WHERE ");
//				sb1.append("		B.KANRI_KB  = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//				sb1.append("	and B.KANRI_CD  = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),4) + "' ");
//				sb1.append(" and B.SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))
//						+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")) + "' ");
				sb1.append("		A.KANRI_KB  = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
				sb1.append("	and A.KANRI_CD  = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),4) + "' ");
				sb1.append(" and A.SHIHAI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))
						+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")) + "' ");
				sb1.append("    and A.SHIHAI_KB = '" + mst001001_ShihaiKbDictionary.SIRESAKI.getCode() + "' ");
				sb1.append("	and A.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//				sb1.append("	and B.DELETE_FG   = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
				sb1.append("ORDER BY ");
				sb1.append("		A.KANRI_KB, ");
				sb1.append("		A.KANRI_CD, ");
				sb1.append("		A.SHIHAI_CD, ");
				sb1.append("		A.SHIHAI_KB, ");
				sb1.append("		A.TENPO_CD ");
//			   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

				rset = db.executeQuery(sb1.toString());    //抽出結果(ResultSet)

				lstRow = new ArrayList();
				while(rset.next()){
					lstCol = new ArrayList();
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("kanri_kb")).trim());
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("kanri_cd")).trim());
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("shihai_cd")).trim());
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("shihai_kb")).trim());
					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("tenpo_cd")).trim());
					lstRow.add(lstCol);
				}
				for(int i=0;i<lstRow.size();i++){
					List lstRow2 = (List)lstRow.get(i);
// ↓↓追加（2005.06.30）↓↓
					lst.clear();
// ↑↑追加（2005.06.30）↑↑
					lst.add( "     KANRI_KB   = '" + lstRow2.get(0) + "' ");
//					   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					lst.add( " and KANRI_CD   = '" + lstRow2.get(1) + "' ");
					lst.add( " and KANRI_CD   = '" + lstRow2.get(1) + "' ");
//					   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
					lst.add( " and SHIHAI_CD  = '" + lstRow2.get(2) + "' ");
					lst.add( " and SHIHAI_KB  = '" + lstRow2.get(3) + "' ");
					lst.add( " and TENPO_CD   = '" + lstRow2.get(4) + "' ");
//					yukoDt = mst001001_EffectiveDayBean.getGenYoyaku("R_HAKOKANRI","YUKO_DT",lst,"0",db);
					yukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db,"R_HAKOKANRI","YUKO_DT",lst,MSTDATE,true);
					if(yukoDt.equals("")){
						//将来レコードチェック
// ↓↓追加（2005.06.30）↓↓
						sb2 = new StringBuffer();
// ↑↑追加（2005.06.30）↑↑
						sb2.append(" SELECT ");
						sb2.append(" 		* ");
						sb2.append(" FROM ");
						sb2.append(" 		R_HAKOKANRI ");
						sb2.append(" WHERE ");
						sb2.append("     KANRI_KB  = '" + lstRow2.get(0) + "' ");
//						↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//						sb2.append(" and KANRI_CD  = '" + lstRow2.get(1) + "' ");
						sb2.append(" and KANRI_CD  = '" + lstRow2.get(1) + "' ");
//						↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
						sb2.append(" and SHIHAI_CD = '" + lstRow2.get(2) + "' ");
						sb2.append(" and SHIHAI_KB = '" + lstRow2.get(3) + "' ");
						sb2.append(" and TENPO_CD  = '" + lstRow2.get(4) + "' ");
						sb2.append(" and DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");

// 2006.01.20 Y.Inaba Mod ↓
//						sb2.append("	and YUKO_DT   > to_char(sysdate,'YYYYMMDD') ");
						sb2.append("	and YUKO_DT   > '" + MSTDATE + "' ");
// 2006.01.20 Y.Inaba Mod ↑

						ResultSet rset2 = db.executeQuery(sb2.toString());

						if(rset2.next()){
							//存在エラー
							checkflg = false;
							break;
						}
					} else {
						//SYSDATE以前の削除データよりも過去の日付は対象外
// ↓↓追加（2005.06.30）↓↓
						sb2 = new StringBuffer();
// ↑↑追加（2005.06.30）↑↑
						sb2.append(" SELECT ");
						sb2.append(" 		*");
						sb2.append(" FROM ");
						sb2.append(" 		R_HAKOKANRI ");
						sb2.append(" WHERE ");
						sb2.append("     KANRI_KB  = '" + lstRow2.get(0) + "' ");
//						↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//						sb2.append(" and KANRI_CD  = '" + lstRow2.get(1) + "' ");
						sb2.append(" and KANRI_CD  = '" + lstRow2.get(1) + "' ");
//						↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
						sb2.append(" and SHIHAI_CD = '" + lstRow2.get(2) + "' ");
						sb2.append(" and SHIHAI_KB = '" + lstRow2.get(3) + "' ");
						sb2.append(" and TENPO_CD  = '" + lstRow2.get(4) + "' ");
						sb2.append(" and DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
// 2006.01.20 Y.Inaba Mod ↓
//						sb2.append("	and YUKO_DT   < to_char(sysdate,'YYYYMMDD') ");
						sb2.append("	and YUKO_DT   < '"+ MSTDATE +"' ");
// 2006.01.20 Y.Inaba Mod ↑
						ResultSet rset2 = db.executeQuery(sb2.toString());

						if(!rset2.next()){
							//存在エラー
							checkflg = false;
							break;
						}
					}
				}
				if(!checkflg){
					//データ存在エラー
//					   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_tikucd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
					Keepb.setFirstFocus("ct_tikucd");
//				   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
// ↓↓仕様変更（2005.09.01）↓↓
//						Keepb.setFirstFocus("ct_kanrikb(0)");
//						Keepb.setFirstFocus(kanriKbCtl);
// ↑↑仕様変更（2005.09.01）↑↑
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40006").toString());
					}
				}

//			   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//				//物流経路マスタ
//				lst = new ArrayList();
//				lst2 = new ArrayList();
//				mstchk = new mst000701_MasterInfoBean();
//
//				sb1 = new StringBuffer();
//				sb2 = new StringBuffer();
//
//				sb1.append(" SELECT ");
//				sb1.append("		DISTINCT ");
//				sb1.append("		KANRI_KB, ");
//				sb1.append("		KANRI_CD, ");
//				sb1.append("		SIHAI_CD, ");
//				sb1.append("		SIHAI_KB, ");
//				sb1.append("		SYOHIN_CD, ");
//				sb1.append("		TENPO_CD ");
//				sb1.append("	FROM ");
//				sb1.append("		R_BUTURYUKEIRO ");
//				sb1.append("	WHERE ");
//				sb1.append("		KANRI_KB  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")) + "' ");
//				sb1.append("	and KANRI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//				sb1.append("	and SIHAI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//				sb1.append("    and SIHAI_KB  = '"  + mst001001_ShihaiKbDictionary.SIRESAKI.getCode() + "' ");
//				sb1.append("	and DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//				sb1.append("ORDER BY ");
//				sb1.append("		KANRI_KB, ");
//				sb1.append("		KANRI_CD, ");
//				sb1.append("		SIHAI_CD, ");
//				sb1.append("		SIHAI_KB, ");
//				sb1.append("		TENPO_CD ");
//
//				rset = db.executeQuery(sb1.toString());    //抽出結果(ResultSet)
//
//				lstRow = new ArrayList();
//				while(rset.next()){
//					lstCol = new ArrayList();
//					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("kanri_kb")).trim());
//					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("kanri_cd")).trim());
//					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("sihai_cd")).trim());
//					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("sihai_kb")).trim());
//					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("syohin_cd")).trim());
//					lstCol.add(mst000401_LogicBean.chkNullString(rset.getString("tenpo_cd")).trim());
//					lstRow.add(lstCol);
//				}
//				for(int i=0;i<lstRow.size();i++){
//					List lstRow2 = (List)lstRow.get(i);
//// ↓↓追加（2005.06.30）↓↓
//					lst.clear();
//// ↑↑追加（2005.06.30）↑↑
//					lst.add( "     KANRI_KB  = '" + lstRow2.get(0) + "' ");
//					lst.add( " and KANRI_CD  = '" + lstRow2.get(1) + "' ");
//					lst.add( " and SIHAI_CD = '" + lstRow2.get(2) + "' ");
//					lst.add( " and SIHAI_KB = '" + lstRow2.get(3) + "' ");
//					lst.add( " and SYOHIN_CD = '" + lstRow2.get(4) + "' ");
//					lst.add( " and TENPO_CD  = '" + lstRow2.get(5) + "' ");
//					yukoDt = mst001001_EffectiveDayBean.getGenYoyaku("R_BUTURYUKEIRO","YUKO_DT",lst,"0",db);
//					if(yukoDt.equals("")){
//						//将来レコードチェック
//// ↓↓追加（2005.06.30）↓↓
//						sb2 = new StringBuffer();
//// ↑↑追加（2005.06.30）↑↑
//						sb2.append(" SELECT ");
//						sb2.append(" 		* ");
//						sb2.append(" FROM ");
//						sb2.append(" 		R_BUTURYUKEIRO ");
//						sb2.append(" WHERE ");
//						sb2.append("     KANRI_KB  = '" + lstRow2.get(0) + "' ");
//						sb2.append(" and KANRI_CD  = '" + lstRow2.get(1) + "' ");
//						sb2.append(" and SIHAI_CD = '" + lstRow2.get(2) + "' ");
//						sb2.append(" and SIHAI_KB = '" + lstRow2.get(3) + "' ");
//						sb2.append(" and SYOHIN_CD = '" + lstRow2.get(4) + "' ");
//						sb2.append(" and TENPO_CD  = '" + lstRow2.get(5) + "' ");
//						sb2.append(" and DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//// 2006.01.20 Y.Inaba Mod ↓
////						sb2.append("	and YUKO_DT   > to_char(sysdate,'YYYYMMDD') ");
//						sb2.append("	and YUKO_DT   > '" + MSTDATE + "' ");
//// 2006.01.20 Y.Inaba Mod ↑
//						ResultSet rset2 = db.executeQuery(sb2.toString());
//						if(rset2.next()){
//							//存在エラー
//							checkflg = false;
//							break;
//						}
//					} else {
//						//SYSDATE以前の削除データよりも過去の日付は対象外
//// ↓↓追加（2005.06.30）↓↓
//						sb2 = new StringBuffer();
//// ↑↑追加（2005.06.30）↑↑
//						sb2.append(" SELECT ");
//						sb2.append(" 		*");
//						sb2.append(" FROM ");
//						sb2.append(" 		R_BUTURYUKEIRO ");
//						sb2.append(" WHERE ");
//						sb2.append("     KANRI_KB  = '" + lstRow2.get(0) + "' ");
//						sb2.append(" and KANRI_CD  = '" + lstRow2.get(1) + "' ");
//						sb2.append(" and SIHAI_CD = '" + lstRow2.get(2) + "' ");
//						sb2.append(" and SIHAI_KB = '" + lstRow2.get(3) + "' ");
//						sb2.append(" and SYOHIN_CD = '" + lstRow2.get(4) + "' ");
//						sb2.append(" and TENPO_CD  = '" + lstRow2.get(5) + "' ");
//						sb2.append(" and DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//// 2006.01.20 Y.Inaba Mod ↓
////						sb2.append("	and YUKO_DT   < to_char(sysdate,'YYYYMMDD') ");
//						sb2.append("	and YUKO_DT   < '" + MSTDATE + "' ");
//// 2006.01.20 Y.Inaba Mod ↑
//						ResultSet rset2 = db.executeQuery(sb2.toString());
//						if(!rset2.next()){
//							//存在エラー
//							checkflg = false;
//							break;
//						}
//					}
//				}
//				if(!checkflg){
//					//データ存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//// ↓↓仕様変更（2005.09.01）↓↓
////						Keepb.setFirstFocus("ct_kanrikb(0)");
////						Keepb.setFirstFocus(kanriKbCtl);
//// ↑↑仕様変更（2005.09.01）↑↑
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage(map.get("40006").toString());
//					}
//				}
//				↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//				↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//				//発注納品基準日マスタ
//				lst = new ArrayList();
//				mstchk = new mst000701_MasterInfoBean();
//				lst.add( "     KANRI_KB      = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")) + "' ");
//				lst.add( " and KANRI_CD      = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//				lst.add( " and SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//// 2006.01.24 Y.Inaba Mod ↓
////				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_HACHUNOHINKIJUNBI","SIIRESAKI_CD", lst, "0");
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_HACHUNOHINKIJUNBI","SIIRESAKI_CD", lst, "0", "");
//// 2006.01.24 Y.Inaba Mod ↑
//				if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//データ存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//// ↓↓仕様変更（2005.09.01）↓↓
////						Keepb.setFirstFocus("ct_kanrikb(0)");
////						Keepb.setFirstFocus(kanriKbCtl);
//// ↑↑仕様変更（2005.09.01）↑↑
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage(map.get("40006").toString());
//					}
//				}
//				↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

				//店別仕入先マスタ
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
//			   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//				lst.add( "     KANRI_KB      = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")) + "' ");
//				lst.add( " and KANRI_CD      = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//				lst.add( " and SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
				lst.add( "     KANRI_KB      = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
				lst.add( " and KANRI_CD      = '" + mst000401_LogicBean.formatZero(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),4) + "' ");
				lst.add( " and SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))
						+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tikucd")) + "' ");
//			   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENBETU_SIIRESAKI","SIIRESAKI_CD", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENBETU_SIIRESAKI","SIIRESAKI_CD", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
				if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//データ存在エラー
//				   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_tikucd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_siiresakicd");
					Keepb.setFirstFocus("ct_tikucd");
//				   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
// ↓↓仕様変更（2005.09.01）↓↓
//						Keepb.setFirstFocus("ct_kanrikb(0)");
//						Keepb.setFirstFocus(kanriKbCtl);
// ↑↑仕様変更（2005.09.01）↑↑
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40006").toString());
					}
				}
				if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) &&
					Keepb.getTosanKb().equals(mst003701_TousanKbDictionary.TOUSAN.getCode())){
					if(Keepb.getErrorMessage().equals(map.get("40006").toString())){
// ↓↓仕様変更（2005.09.01）↓↓
//						Keepb.setFirstFocus("ct_kanrikb(0)");
//						Keepb.setFirstFocus(kanriKbCtl);
// ↑↑仕様変更（2005.09.01）↑↑
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40400").toString());
					}
				}
			}
		}
		Keepb.setCtrlColor(CtrlColor);
	}

//	 ADD by Tanigawa 2006/9/25 障害票№0036 チェック処理を追加 START
	//
	//
	/**
	 * 画面上でタグ衣料が選択された場合、仕入先コード(前から6桁)、仕入先システム区分(2 or 3)にて検索し、
	 * すでに登録されているデータが存在するかチェックする
	 * @author Tanigawa
	 * @param String JobID        JobID
	 * @param String siiresaki_cd 仕入先コード
	 * @param String tiku_cd      地区コード
	 *
	 * @return boolean true：同一仕入先コードは存在する、false：同一仕入先コードは存在しない
	 */
//	private boolean siiresakiCdCheckWhenTagIsSelected(String siiresaki_cd) throws SQLException{
	private boolean siiresakiCdCheckWhenTagIsSelected(String JobID, String siiresaki_cd, String tiku_cd) throws SQLException{

		DataHolder dh = new DataHolder();
		BeanHolder bh = new BeanHolder(new mst440401_RSiiresakiDM());

//		dh.updateParameterValue("siiresaki_cd_not_equals", siiresaki_tiku_cd.toString());	//仕入先コード(6桁) + 地区コード(3桁)の仕入先コードは、検索条件から省く(そうしないと、自分自身が検索条件に引っかかってしまうので…)
//		dh.updateParameterValue("siiresaki_cd_aft_like", siiresaki_cd);	//仕入先コードは、前方一致検索(6桁チェックは、事前に済)
//		dh.updateParameterValue("siire_system_kb_in", "2,3");			//仕入先システムコードは、2,3で検索

		//ADD by Tanigawa 2006/10/08 障害票№0133対応 既存データをPOS→タグ衣料、POS→POSとタグ衣料 に修正した場合に、前6桁が重複したタグ衣料データが存在しているとエラー START
		StringBuffer siiresaki_tiku_cd = new StringBuffer();
		siiresaki_tiku_cd.append(siiresaki_cd);
		siiresaki_tiku_cd.append(tiku_cd);
//		if( dataHolder.getParameter("JobID").equals("mst440301_SiiresakiInsert") || dataHolder.getParameter("JobID").equals("mst440401_SiiresakiUpdate") ){

		//INSERT時：仕入先コードを検索して、前6桁が重複したデータがあればエラー
		//UPDATE時：自分以外の仕入先コードを検索して、前6桁が重複したデータがあればエラー
		if( JobID.equals("mst440401_SiiresakiUpdate") ){
			dh.updateParameterValue("siiresaki_cd_not_equals", siiresaki_tiku_cd.toString());	//仕入先コード(6桁) + 地区コード(3桁)の仕入先コードは、チェック時の検索条件から省く(そうしないと、自分自身が検索条件に引っかかってしまうので…)
		}
		dh.updateParameterValue("siiresaki_cd_aft_like", siiresaki_cd);	//仕入先コードは、前方一致検索(6桁チェックは、事前に済)
		dh.updateParameterValue("siire_system_kb_in", "2,3");			//仕入先システムコードは、2,3で検索
		dh.updateParameterValue("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());	//未削除
		//ADD by Tanigawa 2006/10/08 障害票№0133対応 既存データをPOS→タグ衣料、POS→POSとタグ衣料 に修正した場合に、前6桁が重複したタグ衣料データが存在しているとエラー  END

		bh.setDataHolder(dh);

		bh.getFirstPageBeanList();

		if(bh.getBeanList().size() <= 0){
			return false;
		}

		return true;
	}


	/**
	 * 仕入先システム区分取得用メソッド
	 * 画面上で選択した、POS、タグ衣料に基づき、
	 * 適切な仕入先システム区分を返す
	 *
	 * ****DMなどで使用しているので、安易にprivateメソッドへと修正することを禁ず****
	 *
	 *
	 * @author Tanigawa
	 * @param boolean bPos
	 * @param boolean bTag
	 *
	 * @return String 1 POS
	 * @return String 2 タグ
	 * @return String 3 POS/タグ 共通
	 */
	public mst009101_SiireSystemKbDictionary getSiireSystemKb(boolean bPos, boolean bTag ){

		//POSとタグ衣料が選択されている場合、"3"(POS/タグ 共通)
		if( bPos && bTag ){
			return mst009101_SiireSystemKbDictionary.POS_TAG;
		}

		//POSがチェックされている場合、"1"(POS)
		if( bPos ){
			return mst009101_SiireSystemKbDictionary.POS;
		}

		//タグ衣料がチェックされている場合、"2"(タグ)
		if( bTag ){
			return mst009101_SiireSystemKbDictionary.TAG;
		}

		//いずれの選択肢にも当てはまらないことはないと思いますが…、もし当てはまらなければ「不明」を返す
		return mst009101_SiireSystemKbDictionary.NULL;
	}
//	 ADD by Tanigawa 2006/9/25 障害票№0036 仕入先システム区分に基づき、仕入先マスタ内をチェック  END

}
