/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタ登録(一覧)表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタ登録(一覧)表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/23)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.master.common.dictionary.*;
import mdware.portal.bean.MdwareUserSessionBean;
import mdware.common.util.StringUtility;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタ登録(一覧)の画面表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタ登録(一覧)の画面表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/23)初版作成
 */
public class mst430401_CheckBean{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */	
//    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//	public void Check(mst000101_DbmsBean db, mst430201_KeepBean Keepb ,mst430301_KeepMeisaiBean Meisaib ,DataHolder dataHolder ,String kengenCd 
//						,String GamenId ,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor ,SessionManager sessionManager)
//				throws SQLException,Exception {
    public void Check(mst000101_DbmsBean db, mst430201_KeepBean Keepb ,mst430301_KeepMeisaiBean Meisaib ,DataHolder dataHolder 
					,String GamenId ,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor ,SessionManager sessionManager)
			throws SQLException,Exception {
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
    	
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//画面内容を保存
//		    ↓↓2006.06.15 guohy カスタマイズ修正↓↓			
			//Keepb.setKanriKbCd(  mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim() );			
//			Keepb.setKanriCd(    mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")).trim() );
//			Keepb.setKanriNm(    mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrinm")).trim() );
			Keepb.setBumonCd(    mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).trim() );
//		    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
			Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")).trim() 
					                   + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_areacd")).trim());
			Keepb.setSiiresakiNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakinm")).trim() );
			Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_syorikb")).trim() );

			//画面明細内容を保存
			List mlst = Meisaib.getMeisai();
//			↓↓2006.08.03 guohy カスタマイズ修正↓↓
			int j = 0;
			if (mlst.size() > Integer.parseInt(Meisaib.getDispRows())){
				int pageRows = Integer.parseInt(Meisaib.getDispRows());				//表示行数
				int CurrentPageNo = Integer.parseInt(Meisaib.getCurrentPageNo());//現在表示ページ番号
				j = (((CurrentPageNo - 1) * pageRows) + 1);
			}
			mst430101_SiiresakiBean mb = new mst430101_SiiresakiBean();
			String[] index = dataHolder.getParameterValues("idx");
			for( int i = j ;i < index.length; i++){
//			↑↑2006.08.03 guohy カスタマイズ修正↑↑
				mb = (mst430101_SiiresakiBean)mlst.get(i);
//			    ↓↓2006.06.15 guohy カスタマイズ修正↓↓	
//				String ct_kanricd			= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_kanricd" + i));	
				String ct_bumoncd			= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_bumoncd" + i));	
//				String ct_bumonnm			= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_bumonnm" + i));			
//			    ↑↑2006.06.15 guohy カスタマイズ修正↑↑					
				String ct_siiresakicd		= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_siiresakicd" + i));
				String ct_kanjina			= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_kanjina" + i));
				String ct_yubincd			= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_yubincd" + i));
				String ct_addresskanji1na	= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_addresskanji1na" + i));
				String ct_tel1na			= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_tel1na" + i));
				String ct_insertts			= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_insertts" + i));
				String ct_inserttsshort		= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_inserttsshort" + i));
				String ct_insertuserid		= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_insertuserid" + i));
				String ct_insertusernm		= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_insertusernm" + i));
				String ct_updatets			= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updatets" + i));
				String ct_updatetsshort		= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updatetsshort" + i));
				String ct_updateuserid		= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updateuserid" + i));
				String ct_updateusernm		= mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updateusernm" + i));
			
//			    ↓↓2006.06.15 guohy カスタマイズ修正↓↓	
//				mb.setKanriCd(ct_kanricd);		
				mb.setBumonCd(ct_bumoncd);
//				mb.setBumonNm(ct_bumonnm);		
//			    ↑↑2006.06.15 guohy カスタマイズ修正↑↑	
				mb.setSiiresakiCd(ct_siiresakicd);
				mb.setKanjiNa(ct_kanjina);
				mb.setYubinCd(ct_yubincd);
				mb.setAddressKanji1Na(ct_addresskanji1na);
				mb.setTel1Na(ct_tel1na);
				mb.setInsertTs(ct_insertts);
				mb.setInsertTsShort(ct_inserttsshort);
				mb.setInsertUserId(ct_insertuserid);
				mb.setInsertUserNm(ct_insertusernm);
				mb.setUpdateTs(ct_updatets);
				mb.setUpdateTsShort(ct_inserttsshort);
				mb.setUpdateUserId(ct_updateuserid);
				mb.setUpdateUserNm(ct_updateusernm);

				String flgx = "";
			}
			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);			

//		    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
			//メーニューバーアイコン取得
			// userSession取得
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);
//			 userSession取得
			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");
//		    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
			
			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			String name = "";				//戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
			//検索処理チェック-----------------------------------------------------------------------------
//		    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
			//種別の取得
//		    String Syubetu = new String();
//		    if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()) ){
//				Syubetu = mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS;				
//		    } else if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//					Syubetu = mst000101_ConstDictionary.H_SALE;
//			}		

			//管理コード存在チェック
//			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")).equals("")){
//				lst = new ArrayList();
//				mstchk = new mst000701_MasterInfoBean();
	
//				lst.add( "SYUBETU_NO_CD = " + Syubetu );				
//				lst.add( " and "+ " CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");

//			//部門コード存在チェック
//			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).equals("")){
//				lst = new ArrayList();
//				mstchk = new mst000701_MasterInfoBean();
//				lst.add( " CODE_CD = '"  + mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("ct_bumoncd"), 4, "0", true)) + "' ");
//				lst.add("  AND SYUBETU_NO_CD  = '"+ mst000101_ConstDictionary.SECTION +"' ");
////			    ↑↑2006.06.15 guohy カスタマイズ修正↑↑	
//				// 2006.01.24 Y.Inaba Mod ↓
////				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
//// 2006.01.24 Y.Inaba Mod ↑
////				    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//管理コード存在エラー
////					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");	
////					Keepb.setFirstFocus("ct_kanricd");
////					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
////						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////						Keepb.setErrorMessage("指定された管理コード"+map.get("40007").toString());
////					}
////				} else {
////					Keepb.setKanriNm(mstchk.getCdName());
////			}	
//                   //部門コード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_bumoncd");	
//					FirstFocusCtl = "ct_bumoncd";
//					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setBumonNm("");
//						Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
//					}
//				} else {
//					Keepb.setBumonNm(mstchk.getCdName());				
////						if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
////						if(!mst000401_LogicBean.getHankuCdCheck(db,mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")),sessionManager)){
////							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");	
////							Keepb.setFirstFocus("ct_kanricd");
////							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
////								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////								Keepb.setErrorMessage(map.get("40022").toString());											
////							}
////						}
////					}												
//				}
//			}
			
//			//地区コード存在チェック
//				lst = new ArrayList();
//				mstchk = new mst000701_MasterInfoBean();
//				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_areacd")).equals("")){	
//					lst.add("  CODE_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_areacd")) + "' ");
//					lst.add("  AND SYUBETU_NO_CD  =  '"+ mst000101_ConstDictionary.AREA_CODE +"' ");
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
//					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){							
//							FirstFocusCtl = "ct_areacd";							
//							Keepb.setAreaNm("");
//							Keepb.setErrorMessage("指定された地区コード"+map.get("40007").toString());
//						} 
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_areacd");
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);						
//					} else {
//						Keepb.setAreaNm(mstchk.getCdName());
//					}					
//				} else {
//					Keepb.setAreaNm("");
//				}
//				↑↑2006.06.15 guohy カスタマイズ修正↑↑			
			//仕入先コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			
//		    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
			//業種コード取得
//			String gyosyuID = SysUserBean.getSelectedGyosyuCd();
			//業種が生鮮の場合
//			if (gyosyuID.equals("04")) {		
//				lst.add( "     KANRI_KB      = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");		
//					if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")).equals("")){
//						lst.add( " and KANRI_CD      = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "' ");
//			}
			//業種が生鮮以外の場合
//			} else {
//				lst.add( "     KANRI_KB      = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")) + "' ");
//				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")).equals("")){
//					lst.add( " and KANRI_CD      = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//			}
	        lst.add( "     KANRI_KB      = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).equals("")){			
	            lst.add( " and KANRI_CD      = '0000' ");
			}			
			lst.add(" and TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
			lst.add(" and SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd"))  
					                                   + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_areacd")) + "' ");
//			 ↑↑2006.06.15 guohy カスタマイズ修正↑↑
// 2006.01.24 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, "0");
			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				Keepb.setSiiresakiNm("");
			} else {
				Keepb.setSiiresakiNm(mstchk.getCdName());
			}
		}
//	    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
		Keepb.setFirstFocus(FirstFocusCtl);
		Keepb.setCtrlColor(CtrlColor);
//		↑↑2006.06.15 guohy カスタマイズ修正↑↑
	}
}