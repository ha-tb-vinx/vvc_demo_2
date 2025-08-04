package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;
import mdware.portal.bean.MdwareUserSessionBean;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品空番検索の画面表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品空番検索の画面表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Kim
 * @version 1.0(2006/01/31)初版作成
 * @version 1.1(2006/02/12) D.Matsumoto デグレ対応
 */
public class mst250301_CheckBean{
	/**
	 * 
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		mst450301_KeepBean 			Keepb
	 * @param 		DataHolder 					dataHolder
	 * @param		String						kengenCd		: 権限コード
 	 * @param		String 						GamenId 		: 画面ID
	 * @param		String 						FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ		
	 * @param		String[] 					CtrlName 		: コントロール名 
	 * @param		Map 						CtrlColor 		: コントロールカラー 
	 * @param		SessionManager 				sessionManager 	: セションマネージャー 
	 */	
	public void Check(mst000101_DbmsBean db, mst250201_KeepBean Keepb, DataHolder dataHolder, String kengenCd 
						, String GamenId, String FirstFocusCtl, String[] CtrlName, Map CtrlColor, SessionManager sessionManager)
				throws SQLException,Exception {
	
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");
	

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){

			//画面内容を保存
//			↓↓2006.07.20 xiongjun カスタマイズ修正↓↓
			Keepb.setSelect(		mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_select")).trim());
			Keepb.setStartSyohinCd(	mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_startSyohinCd")).trim());
			Keepb.setEndSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_endSyohinCd")).trim());
			Keepb.setByNo(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_byNo")).trim());
			Keepb.setSiiresakiSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakiSyohinCd")).trim());
			Keepb.setBlankKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blankKb")).trim());
			Keepb.setYoyakusumi(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yoyakusumi")).trim());
			Keepb.setTourokusumi(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tourokusumi")).trim());
//			Keepb.setSiiresakiCd(	mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")).trim());
//			Keepb.setSiiresakiNm(	mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakinm")).trim());
//			Keepb.setKanriCd(		mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")).trim());
//			Keepb.setKanriNm(		mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrinm")).trim());
//			↑↑2006.07.20 xiongjun カスタマイズ修正↑↑		
			
			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)
			
			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);			
			
			//メーニューバーアイコン取得
			// userSession取得
//			↓↓2006.07.20 xiongjun カスタマイズ修正↓↓
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean ();	// ログインユーザー情報
			SysUserBean = (MdwareUserSessionBean )sessionManager.getAttribute("userSession");

//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);
//			↑↑2006.07.20 xiongjun カスタマイズ修正↑↑
		
			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			String name = "";				//戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

			//検索処理チェック-----------------------------------------------------------------------------
//			↓↓2006.07.20 xiongjun カスタマイズ修正↓↓
//			//管理コード存在チェック
//			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")).equals("")){
//				lst = new ArrayList();
//				mstchk = new mst000701_MasterInfoBean();
//	
//				lst.add( " SYUBETU_NO_CD = " + mst000101_ConstDictionary.H_SALE );				
//				lst.add( " and "+ " CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//				// 2006/02/12 D.Matsumoto コンパイルできなかったため、有効日にNULLを入れた
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "" );
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//管理コード存在エラー
//					Keepb.setKanriNm("");
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");	
//					Keepb.setFirstFocus("ct_kanricd");
//					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
//					}
//				} else {
//					Keepb.setKanriNm(mstchk.getCdName());
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
//			//仕入先コード存在チェック
//			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")).equals("")){
//				lst = new ArrayList();
//				mstchk = new mst000701_MasterInfoBean();
//				
//				lst.add( " KANRI_KB      = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")).equals("")){
//					lst.add( " and KANRI_CD      = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
//				}
//				lst.add(" and TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//				lst.add(" and SIIRESAKI_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")) + "' ");
//				// 2006/02/12 D.Matsumoto コンパイルできなかったため、有効日にNULLを入れた
//				//mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, "0");
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, "0", "" );
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					Keepb.setSiiresakiNm("");
//				} else {
//					Keepb.setSiiresakiNm(mstchk.getCdName());
//				}
//			}
//			↑↑2006.07.20 xiongjun カスタマイズ修正↑↑			
		}
	
		Keepb.setCtrlColor(CtrlColor);
	}
}
