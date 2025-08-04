/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius T.Kiiuchi
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
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst001001_ShihaiKbDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.portal.bean.MdwareUserSessionBean;
/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst480301_CheckBean{
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
//	↓↓2006.06.20 maojm カスタマイズ修正↓↓
//	public void Check( mst000101_DbmsBean db, mst480201_KeepBean Keepb ,DataHolder dataHolder ,String kengenCd ,String GamenId ,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor
//										,SessionManager sessionManager ,String tableNa ,String columnNa ,List whereList, String addValue )
//				throws Exception,SQLException {
	public void Check( mst000101_DbmsBean db, mst480201_KeepBean Keepb ,DataHolder dataHolder ,String GamenId ,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor
			   ,SessionManager sessionManager ,String tableNa ,String columnNa ,List whereList, String addValue )
               throws Exception,SQLException {
	    //メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				//画面内容を保存
//				Keepb.setKanriKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kanrikb")).trim() );
//				Keepb.setKanriCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanricd")).trim() );
//				Keepb.setKanriKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kanri_nm")).trim() );
                Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumoncd")).trim());
				Keepb.setBumonKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_nm")).trim());
                Keepb.setTenpoCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tenpocd")).trim() );
				Keepb.setTenpoKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tenpo_nm")).trim() );
				Keepb.setShihaiKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_shihaiKb")).trim() );
				Keepb.setShihaiCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_shihaicd")).trim() );
				Keepb.setShihaiKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_shihai_nm")).trim() );
				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syorikb")).trim() );
				Keepb.setYukoDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")).trim() );
//				Keepb.setCopyKanriCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_copykanricd")).trim() );
//				Keepb.setCopyKanriKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_copykanri_nm")).trim() );
                Keepb.setCopyBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_copybumoncd")).trim() );
                Keepb.setCopyBumonKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_copybumon_nm")).trim() );
//  		　　↑↑2006.06.20 maojm カスタマイズ修正↑↑
				Keepb.setCopyBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_copybumoncd")).trim());
				Keepb.setCopyBumonKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_copybumon_nm")).trim());
                Keepb.setGenyukoDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_genyukodt")).trim() );
				Keepb.setWebHaisinsakiCd1( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_webhaisinsakicd1")).trim() );
				Keepb.setWebHaisinsakiCd2( mst000401_LogicBean.chkNullString( dataHolder.getParameter("tx_webhaisinsakicd2")).trim() );
				Keepb.setJcaHaisinsakiCd1( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_jcahaisinsakicd1")).trim());
				Keepb.setJcaHaisinsakiCd2( mst000401_LogicBean.chkNullString( dataHolder.getParameter("tx_jcahaisinsakicd2")).trim() );
//				↓↓2006.06.20 maojm カスタマイズ修正↓↓
//				Keepb.setHakouKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hakou_kb")).trim());				//発行区分
//				Keepb.setHakouBasyoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_hakou_basyo_cd")).trim());	//発行場所コード
//				↑↑2006.06.20 maojm カスタマイズ修正↑↑
				Keepb.setSyohinTorokuBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syohintorokukb")).trim());			//商品ﾏｽﾀ登録情報媒体区分
				Keepb.setSyohinSyokaiDonyuBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syohinsyokaidonyukb")).trim());	//初回導入提案媒体区分
				Keepb.setHachuBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachukb")).trim());							//発注媒体区分
				Keepb.setNohinAsnBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_nohinasnkb")).trim());					//納品(ASN)媒体区分
				Keepb.setKepinBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_kepinkb")).trim());              			//欠品媒体区分
				Keepb.setSiireKeijoBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_siirekeijokb")).trim());				//仕入計上媒体区分
				Keepb.setSeikyuBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_seikyukb")).trim());						//請求媒体区分
				Keepb.setSiharaiBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_siharaikb")).trim());						//支払媒体区分
				Keepb.setHanbaiBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hanbaikb")).trim());						//販売媒体区分
				Keepb.setHachuKankokuBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_hachukankokukb")).trim());			//発注勧告媒体区分
				Keepb.setZaikoBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_zaikokb")).trim());							//在庫媒体区分
				Keepb.setTagBataiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_tagkb")).trim());								//タグ媒体区分
				Keepb.setSyukanhachuyoteiKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syukanhachuyoteikb")).trim() );
				Keepb.setTenmeisaiKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_tenmeisaikb")).trim() );
			}

			Keepb.setMode( mst000401_LogicBean.chkNullString(dataHolder.getParameter("mode")).trim() );
			Keepb.setExistFlg( "" );
			Keepb.setDecisionFlg( mst000101_ConstDictionary.FUNCTION_TRUE );

			String INIT_PAGE = Keepb.getErrorBackDisp();
			Keepb.setErrorBackDisp( "" );

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

// 2006.01.20 Y.Inaba Add ↓
			String MSTDATE = RMSTDATEUtil.getMstDateDt();
			if(!mst000401_LogicBean.chkNullString(Keepb.getYukoDt()).equals("")){
				MSTDATE = Keepb.getYukoDt();
			}
// 2006.01.20 Y.Inaba Add ↑

			//メーニューバーアイコン取得
			// userSession取得
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");

			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");

//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);

			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			String name = "";				//戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
			//検索処理チェック-----------------------------------------------------------------------------
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH)){
			}
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)){
				//	検索条件チェック Start
//				↓↓2006.06.20 maojm カスタマイズ修正↓↓
//				// 有効日範囲チェック(マスタ日付より1年後まで入力可能)
//				// 照会時はチェックを行わない
//				String[] strReturn = new String[3];
//				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
//					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
//					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setFirstFocus("tx_yukodt");
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_yukodt");
//						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
//					}
//				}
//			//種別の取得
//				String Syubetu = new String();
//				if( Keepb.getKanriKb().equals(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()) ){
//					Syubetu = mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS;
//				} else if(  Keepb.getKanriKb().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//					Syubetu = mst000101_ConstDictionary.H_SALE;
//				}
//			   //管理コード存在チェック
//				lst.add( "SYUBETU_NO_CD = " + Syubetu );
//				lst.add( " and "+ " CODE_CD = '" + Keepb.getKanriCd() + "' ");
//// 2006.01.24 Y.Inaba Mod ↓
////				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
//// 2006.01.24 Y.Inaba Mod ↑
//				Keepb.setKanriKanjiRn(mstchk.getCdName());
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//管理コード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_kanricd");
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setErrorBackDisp( INIT_PAGE );
//						Keepb.setFirstFocus("tx_kanricd");
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された管理コード"+map.get("40007").toString());
//					}
//				} else {
//					if( Keepb.getKanriKb().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//						if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getKanriCd(),sessionManager)){
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_kanricd");
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setErrorBackDisp( INIT_PAGE );
//								Keepb.setFirstFocus("tx_kanricd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40022").toString());
//							}
//						}
//					}
//				}
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//				部門コード存在チェック
				lst.add( "SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' "  );
				lst.add("and  CODE_CD = '" + StringUtility.charFormat( Keepb.getBumonCd(),4,"0",true) + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
				Keepb.setBumonKanjiRn(mstchk.getCdName());
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//部門コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_bumoncd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setErrorBackDisp( INIT_PAGE );
						Keepb.setFirstFocus("tx_bumoncd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
					}

				}
				//店舗コード存在チェック
//				if( Keepb.getTenpoCd().equals(mst000101_ConstDictionary.ALL_TENPO_CD)){
//					Keepb.setTenpoKanjiRn("全店");
                if (Keepb.getTenpoCd().equals(StringUtility.cutString(mst000101_ConstDictionary.ALL_TENPO_CD,3,3))
                    || Keepb.getTenpoCd().equals("") ){
                	Keepb.setTenpoCd("000");
                	Keepb.setTenpoKanjiRn("全店");
			} else {
					lst.clear();
//					lst.add( "     TENPO_CD = '" + Keepb.getTenpoCd() + "' " );
					lst.add( "     tenpo_cd = '" + StringUtility.charFormat(  Keepb.getTenpoCd(),6,"000",true)  + "' " );
					lst.add( " and tenpo_kb = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' " );
//				   ↑↑2006.06.21 maojm カスタマイズ修正↑↑
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENPO","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENPO","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
// 2006.01.24 Y.Inaba Mod ↑
					Keepb.setTenpoKanjiRn(mstchk.getCdName());
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//店舗コード存在エラー
						Keepb.setErrorBackDisp( INIT_PAGE );
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_tenpocd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("tx_tenpocd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された店舗コード"+map.get("40007").toString());
						}
					}
				}

//				↓↓2006.06.21 maojm カスタマイズ修正↓↓
//				//コード存在チェック
//				lst.clear();
//
//				//業種コード取得
//				String gyosyuID = SysUserBean.getSelectedGyosyuCd();
//				//業種が生鮮の場合
//				if (gyosyuID.equals("04")) {
//					lst.add( "KANRI_KB = '" +  mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' "  );
//					lst.add( " and " );
//					lst.add( "KANRI_CD = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "' "  );
//					lst.add( " and " );
//				//業種が生鮮以外の場合
//				} else {
//					lst.add( "KANRI_KB = '" +  Keepb.getKanriKb() + "' "  );
//					lst.add( " and " );
//					lst.add( "KANRI_CD = '" + Keepb.getKanriCd() + "' "  );
//					lst.add( " and " );
//				}
//				lst.add(" TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//
//				//仕入先
//				if(Keepb.getShihaiKb().equals(mst001001_ShihaiKbDictionary.SIRESAKI.getCode())){
//					lst.add( " and SIIRESAKI_CD = '" + Keepb.getShihaiCd() + "' " );
//// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
//// 2006.01.24 Y.Inaba Mod ↑
//					Keepb.setShihaiKanjiRn(mstchk.getCdName());
//					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//						//コード存在エラー
//						Keepb.setErrorBackDisp( INIT_PAGE );
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_shihaicd");
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus("tx_shihaicd");
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage(map.get("40024").toString());
//						}
//					} else {
//// ↓↓仕様変更（2005.07.14）↓↓
////						boolean mstcheck = true;
////						lst.clear();
////						lst.add( " KANRI_KB = '" +  Keepb.getKanriKb() + "' "  );
////						lst.add( " and " );
////						lst.add( " KANRI_CD = '" + Keepb.getKanriCd() + "' "  );
////						lst.add( " and " );
////						lst.add( " TENPO_CD = '" + Keepb.getTenpoCd() + "' " );
////						lst.add( " and " );
////						lst.add( " SIIRESAKI_CD = '" + Keepb.getShihaiCd() + "' " );
////						mstcheck = mst000401_LogicBean.getMasterChk(db,"R_TENBETU_SIIRESAKI", lst  );
////						if(!mstcheck){
////							//コード存在エラー
////							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_shihaicd");
////							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
////								Keepb.setErrorBackDisp( INIT_PAGE );
////								Keepb.setFirstFocus("tx_shihaicd");
////								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////								Keepb.setErrorMessage(map.get("40024").toString());
////							}
////						}
//// ↑↑仕様変更（2005.07.14）↑↑
//					}
//				}
//			//配送先
//				if(Keepb.getShihaiKb().equals(mst001001_ShihaiKbDictionary.HAISOUSAKI.getCode())){
//// ↓↓仕様変更（2005.09.29）↓↓
//					Map valMap = new HashMap();
//					List colList = new ArrayList();
//					colList.add("KANJI_RN");
//					colList.add("HAISOSAKI_CD");
////					lst.add( " and HAISOSAKI_CD = '" + Keepb.getShihaiCd() + "' " );
//					if (gyosyuID.equals("04")) {
//						lst.add( " and HAISOSAKI_CD = '" + Keepb.getShihaiCd() + "' " );
//					} else {
//						lst.add( " and HAISOSAKI_CD LIKE '" + Keepb.getShihaiCd().substring(0,4) + "%' " );
//					}
////					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_HAISOU","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0  );
//					valMap = mst000401_LogicBean.getMasterItemValues(db, "R_HAISOU", colList, lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
////					Keepb.setShihaiKanjiRn(mstchk.getCdName());
////					2006/02/13 kim START 配送先名称表示対応
//					Keepb.setShihaiKanjiRn(mst000401_LogicBean.chkNullString((String)valMap.get("KANJI_RN")).trim());
////					Keepb.setShihaiKanjiRn(mst000401_LogicBean.chkNullString((String)map.get("KANJI_RN")).trim());
////					2006/02/13 kim END
////					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					if (valMap.size() == 0) {
//// ↑↑仕様変更（2005.09.29）↑↑
//						//コード存在エラー
//						Keepb.setErrorBackDisp( INIT_PAGE );
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_shihaicd");
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus("tx_shihaicd");
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage(map.get("40025").toString());
//						}
//					} else {
//						if(!Keepb.getTenpoCd().equals(mst000101_ConstDictionary.ALL_TENPO_CD)){
//// ↓↓仕様変更（2005.07.14）↓↓
////							boolean mstcheck = true;
////							lst.clear();
////							lst.add( "KANRI_KB = '" +  Keepb.getKanriKb() + "' "  );
////							lst.add( " and " );
////							lst.add( "KANRI_CD = '" + Keepb.getKanriCd() + "' "  );
////							lst.add( " and " );
////							lst.add( "TENPO_CD = '" + Keepb.getTenpoCd() + "' " );
////							lst.add( " and " );
////// ↓↓ＤＢバージョンアップによる変更（2005.05.19）
//////							lst.add( " HAISOUSAKI_CD = '" + Keepb.getShihaiCd() + "' " );
////							lst.add( " HAISOSAKI_CD = '" + Keepb.getShihaiCd() + "' " );
////// ↑↑ＤＢバージョンアップによる変更（2005.05.19）
////							mstcheck = mst000401_LogicBean.getMasterChk(db,"R_TENBETU_HAISOSAKI ", lst );
////							if(!mstcheck){
////								//コード存在エラー
////								mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_shihaicd");
////								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
////									Keepb.setErrorBackDisp( INIT_PAGE );
////									Keepb.setFirstFocus("tx_shihaicd");
////									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////									Keepb.setErrorMessage(map.get("40025").toString());
////								}
////							}
//// ↑↑仕様変更（2005.07.14）↑↑
//						}
//					}
//				}
//			//コピー販区コード存在チェック
//				if( Keepb.getKanriKb().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//					if( !(Keepb.getCopyKanriCd().equals("")) ){
//						//コピー販区コード存在チェック
//						lst.clear();
//						lst.add( "SYUBETU_NO_CD = '" + Syubetu +"' " );
//						lst.add( " and " );
//						lst.add( "CODE_CD = '" + Keepb.getCopyKanriCd() + "' " );
//// 2006.01.24 Y.Inaba Mod ↓
////						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
//// 2006.01.24 Y.Inaba Mod ↑
//						Keepb.setCopyKanriKanjiRn(mstchk.getCdName());
//						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//							//コピー販区存在エラー
//							Keepb.setErrorBackDisp( INIT_PAGE );
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_copykanricd");
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setFirstFocus("tx_copykanricd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage("指定されたコピー販区"+map.get("40007").toString());
//							}
//						} else {
//							if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getCopyKanriCd(),sessionManager)){
//								mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_copykanricd");
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//									Keepb.setErrorBackDisp( INIT_PAGE );
//									Keepb.setFirstFocus("tx_copykanricd");
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									Keepb.setErrorMessage(map.get("40022").toString());
//								}
//							}
//						}
//					}
//				}

//				コード存在チェック
				lst.clear();
			    lst.add( "kanri_kb = '" + mst000901_KanriKbDictionary.BUMON.getCode()+ "' "  );
				lst.add( " and " );
				lst.add( "kanri_cd = '0000' "  );
				lst.add( " and " );
				lst.add(" tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//				仕入先
				if(Keepb.getShihaiKb().equals(mst001001_ShihaiKbDictionary.SIRESAKI.getCode())){
					lst.add( " and siiresaki_cd = '" + Keepb.getShihaiCd() + "' " );

					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
					Keepb.setShihaiKanjiRn(mstchk.getCdName());
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//コード存在エラー
						Keepb.setErrorBackDisp( INIT_PAGE );
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_shihaicd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("tx_shihaicd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40024").toString());
						}
					}

				}
                //有効日範囲チェック(マスタ日付より1年後まで入力可能)
				// 照会時はチェックを行わない
				String[] strReturn = new String[3];
				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("tx_yukodt");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_yukodt");
						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
					}
				}

//				コピー部門コード存在チェック
//				if( Keepb.getKanriKb().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
					if( !(Keepb.getCopyBumonCd().equals("")) ){
						//コピー部門コード存在チェック
						lst.clear();
						lst.add( " syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' "  );
						lst.add( " and " );
						lst.add( "code_cd = '" + StringUtility.charFormat(Keepb.getCopyBumonCd(),4,"0",true) + "' " );
   					    mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
						Keepb.setCopyBumonKanjiRn(mstchk.getCdName());
						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							//コピー部門存在エラー
							Keepb.setErrorBackDisp( INIT_PAGE );
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_copybumoncd");
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus("tx_copybumoncd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定されたコピー部門"+map.get("40007").toString());
							}
						}
					}

//			    ↑↑2006.06.21 maojm カスタマイズ修正↑↑
				//	検索条件チェック END
				// 有効日関係のチェック　START
// 2006.01.30 Y.Inaba Mod ↓
//				String chks = mst001001_EffectiveDayBean.getYukoDtCheck( tableNa, columnNa, whereList, addValue,Keepb.getYukoDt().trim(), Keepb, db );
//				String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck(tableNa,columnNa,Keepb,db,addValue,false);
//  ↓↓2006.06.23 maojm カスタマイズ修正↓↓
//					String chks = mst000204_HakokanriEffectiveDayCheckBean.getYukoDtCheck
//								(tableNa,Keepb.getYukoDt(),Keepb.getKanriKb(),Keepb.getKanriCd(),Keepb.getShihaiKb(),
//				               Keepb.getShihaiCd(), Keepb.getTenpoCd(), Keepb,db,addValue);
					String chks = mst000204_HakokanriEffectiveDayCheckBean.getYukoDtCheck
	                          (tableNa,Keepb.getYukoDt(),mst000901_KanriKbDictionary.BUMON.getCode(),StringUtility.charFormat(Keepb.getBumonCd(), 4, "0", true),
	            		       Keepb.getShihaiKb(),Keepb.getShihaiCd(), StringUtility.charFormat(Keepb.getTenpoCd(), 6, "000", true),Keepb,db,addValue);

// ↑↑2006.06.23 maojm カスタマイズ修正↑↑
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
				}
//			↓↓2006.07.06 maojm カスタマイズ修正↓↓
//				else if ( chks.equals(mst000101_ConstDictionary.ERR_CHK_ERROR) ){
//					for ( int i = 0;i<CtrlName.length-1;i++ ){
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,CtrlName[i]);
//					}
//				}
				// 有効日関係のチェック　END

//				//新規・修正時エラーチェック
//				if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)||
//						Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE) ) {

				// 新規時エラーチェック
				if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
					whereList.add( " AND DELETE_FG = '" +  mst000101_ConstDictionary.DELETE_FG_NOR+ "' " );
					if(mst000204_HakokanriEffectiveDayCheckBean.getDateYukoDt(db,"r_hakokanri","yuko_dt",whereList,Keepb.getYukoDt())){
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage( map.get("40001").toString() );//エラーメッセージ
						}
						if ( !Keepb.getCopyBumonCd().trim().equals(""))  {
							Keepb.setFirstFocus("tx_copybumoncd");
							}
						else {
							Keepb.setFirstFocus("tx_bumoncd");
							}
						}
				}

				// 新規時||修正時エラーチェック
				if(	Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)||Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ) {
//					if( !Keepb.getTenpoCd().equals(mst000101_ConstDictionary.ALL_TENPO_CD)){
					if( !Keepb.getTenpoCd().equals(StringUtility.cutString(mst000101_ConstDictionary.ALL_TENPO_CD,3,3))){
// ↑↑2006.07.06 maojm カスタマイズ修正↑↑
						lst.clear();
						whereList.add( " AND DELETE_FG = '" +  mst000101_ConstDictionary.DELETE_FG_NOR+ "' " );
						for( int j = 0 ;j<whereList.size();j++ ){
							if(whereList.get(j).toString().indexOf("TENPO_CD") == -1 ){
								lst.add(whereList.get(j));
							} else {
								lst.add( " AND TENPO_CD  = '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' " );
							}
						}
// 2006.01.20 Y.Inaba Mod ↓
//						if(!mst001001_EffectiveDayBean.getDateYukoDt( db, tableNa, columnNa, lst ,"" )){
//						if(!mst000201_EffectiveDayCheckBean.getDateYukoDt(db,tableNa,columnNa,lst,MSTDATE)){
						if(!mst000204_HakokanriEffectiveDayCheckBean.getDateYukoDt(db,tableNa,columnNa,lst,"")){
// 2006.01.20 Y.Inaba Mod ↑
//							↓↓2006.06.20 maojm カスタマイズ修正↓↓
//							for ( int i = 0;i<CtrlName.length-1;i++ ){
//								mst000401_LogicBean.setErrorBackColor(CtrlColor,CtrlName[i]);
//							}
//							↑↑2006.06.20 maojm カスタマイズ修正↑↑
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage( map.get("40401").toString() );//エラーメッセージ
								if ( !Keepb.getCopyBumonCd().trim().equals(""))  {
									Keepb.setFirstFocus("tx_copybumoncd");
									}
								else {
									Keepb.setFirstFocus("tx_bumoncd");
									}
								}
							}
						}
					}

				//削除時(確定)エラーチェック
				if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){
					if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
						if( Keepb.getTenpoCd().equals("000")){
							lst.clear();
							whereList.add( " AND DELETE_FG = '" +  mst000101_ConstDictionary.DELETE_FG_NOR+ "' " );
							for( int j = 0 ;j<whereList.size();j++ ){
								if(whereList.get(j).toString().indexOf("TENPO_CD") == -1 ){
									lst.add(whereList.get(j));
								} else {
									lst.add( " AND TENPO_CD  <> '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' " );
								}
							}
// 2006.01.30 Y.Inaba Mod ↓
//							if(mst001001_EffectiveDayBean.getDateYukoDt( db, tableNa, columnNa, lst ,"" )){
							if(mst000204_HakokanriEffectiveDayCheckBean.getDateYukoDt(db,tableNa,columnNa,lst,"")){
// 2006.01.30 Y.Inaba Mod ↑
//								↓↓2006.06.20 maojm カスタマイズ修正↓↓
//								for ( int i = 0;i<CtrlName.length-1;i++ ){
//									mst000401_LogicBean.setErrorBackColor(CtrlColor,CtrlName[i]);
//								}
//								↑↑2006.06.20 maojm カスタマイズ修正↑↑
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage( map.get("40402").toString() );//エラーメッセージ
								}
							}
						}
					 }
				  }
			   }
		    }
		}
	}
