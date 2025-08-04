/**
 * <p>タイトル: 新ＭＤシステム（マスター管理） 商品マスタ一括修正（更新）の画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
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
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst170201_CheckBean
{
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		DataHolder 	dataHolder
	 * @param		String		kengenCd		: 権限コード
	 * @param		String 		GamenId 		: 画面ID
	 * @param		String 		FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		String[] 	CtrlName 		: コントロール名
	 */
//        ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
//	public void Check(  mst000101_DbmsBean db, mst160201_KeepBean Keepb ,DataHolder dataHolder
//				,String kengenCd ,String GamenId ,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor, SessionManager  sessionManager )  throws SQLException,Exception {
		/**
		 * @param db
		 * @param Keepb
		 * @param dataHolder
		 * @param GamenId
		 * @param FirstFocusCtl
		 * @param CtrlName
		 * @param CtrlColor
		 * @param sessionManager
		 * @throws SQLException
		 * @throws Exception
		 */
	public void Check(  mst000101_DbmsBean db, mst160201_KeepBean Keepb ,DataHolder dataHolder
				,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor, SessionManager  sessionManager )  throws SQLException,Exception {
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		//個別に権限取得
		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//操作権限設定
			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//メーニューバーアイコン取得
			// userSession取得
			if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT) ){
				//画面内容を保存
//			      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
				Keepb.setYukoDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")));

				Keepb.setUpSiiresakikanriChoice(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_siiresakikanricd")));
				Keepb.setUpSiiresakikanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakikanricd")));
				Keepb.setUpSiiresakikanriKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakikanri_nm")));

				Keepb.setUpSiiresakiChoice( mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_siiresaki")) );
//				Keepb.setUpSiiresakiCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakicd")) );
//				Keepb.setUpSiiresakiKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_nm")) );
				Keepb.setUpSiiresakiCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakicd_for_edit")) );
				Keepb.setUpSiiresakiKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_nm_for_edit")) );

				Keepb.setUpGenkaChgChoice( mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_genkachg")) );
				Keepb.setUpGenkaChgVl( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_genkachgvl")) );
				Keepb.setUpGenkaChgKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_genkachgkb")) );

				Keepb.setUpBaikaChgChoice( mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_baikachg")) );
				Keepb.setUpBaikaChgVl( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_baikachgvl")) );
				Keepb.setUpBaikaChgKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_baikachgkb")) );

				Keepb.setUpNeireChoice(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_neirert")));
				Keepb.setUpNeireVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_neirert")));
				Keepb.setUpNeireKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_neirekb")));

				Keepb.setUpHanbaiStChoice( mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_hanbaistdt")) );
				Keepb.setUpHanbaiStDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanbaistdt_for_edit")) );
				Keepb.setUpHanbaiEdChoice( mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_hanbaieddt")) );
				Keepb.setUpHanbaiEdDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanbaieddt_for_edit")) );
				Keepb.setUpHachuStChoice(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_hachustdt")));
				Keepb.setUpHachuStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hachustdt")));
				Keepb.setUpHachuEdChoice(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_hachueddt")));
				Keepb.setUpHachuEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hachueddt")));

				Keepb.setUpEoskbChgChoice(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_eoskb")));
				Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));

				Keepb.setUpUnitChoice( mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_unitcd")));
				Keepb.setUpUnitCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unitcd_for_edit")));
				Keepb.setUpunitKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unit_nm_for_edit")));

				Keepb.setUpTananoChoice(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_tananonb")));
				Keepb.setUpTananoNb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tananonb")));

				Keepb.setUpBayiaChoice(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_bayiacd")));
				Keepb.setUpBayiaCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bayiacd")));
//				ADD by Tanigawa 2006/11/16 障害票№XXXX対応  START
				Keepb.setScrollPos(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_scrollPos")));
//				ADD by Tanigawa 2006/11/16 障害票№XXXX対応   END

				// 無条件チェック
				String[] strReturn = new String[3];
				if(!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")).equals("")){
					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")));
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						mst000401_LogicBean.setErrorBackColor( CtrlColor, "tx_yukodt" );
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setFirstFocus("tx_yukodt");
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_yukodt");
							Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
						}
					}
				}
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				//店別仕入先管理コード存在チェック
				if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS) &&
					Keepb.getUpSiiresakikanriChoice().equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){
					lst.clear();
//					↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
//					lst.add(" SYUBETU_NO_CD = '" + mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP+ "'");
//					lst.add(" and ");
////					↓↓2006.08.25 H.Yamamoto カスタマイズ修正↓↓
////					lst.add("CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakikanricd")) + "' ");
//					lst.add("CODE_CD = '" + mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("tx_bumoncd"), 4, "0",true))
//							+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakikanricd")) + "' ");
////					↑↑2006.08.25 H.Yamamoto カスタマイズ修正↑↑
					lst.add(" SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.MAKER_NAME, userLocal) + "'");
					lst.add(" and ");
					lst.add("CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakikanricd")) + "' ");
//					↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst,mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
					Keepb.setUpSiiresakikanriKanjiRn(mstchk.getCdName());
					if( Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) ){
						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							//店別仕入先管理コード存在エラー
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_siiresakikanricd");
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus("tx_siiresakikanricd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定されたメーカーコード"+map.get("40007").toString());
							}
						}
					}
				}
//				↑↑2006.07.06 zhangxia カスタマイズ修正↑↑

				//〔仕入先コード〕　システム区分チェック追加による変更 by kema 06.09.28
//				String CtlName = "tx_siiresakicd";
//				 MOD by Tanigawa 2006/11/08 障害票№0194対応 START
				String CtlName = "tx_siiresakicd_for_edit";
//				 MOD by Tanigawa 2006/11/08 障害票№0194対応  END
				if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS) &&
					Keepb.getUpSiiresakiChoice().equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){
					StringBuffer sb = new StringBuffer();
					sb.append(" select ");
					// ===BEGIN=== Add by kou 2006/10/4
					sb.append(" SIIRESAKI_CD, ");
					// ===END=== Add by kou 2006/10/4
					sb.append(" KANJI_RN ");
					sb.append(" ,SIIRE_SYSTEM_KB ");
					sb.append(" from ");
					sb.append(" R_SIIRESAKI ");
					sb.append(" where ");
					sb.append("KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' " );
					sb.append(" and ");
					sb.append("KANRI_CD = '" + "0000" + "' ");
					sb.append(" and ");
//					↓↓仕様追加による変更（2006.09.14） by kema
					if(Keepb.getUpSiiresakiCd().length() < 9 ){
						sb.append( "SUBSTRING(SIIRESAKI_CD,1,6) = '" + Keepb.getUpSiiresakiCd() + "' " );
					}else{
						sb.append( "SIIRESAKI_CD = '" + Keepb.getUpSiiresakiCd() + "' " );
					}
					sb.append(" and ");
					sb.append(" DELETE_FG = '0' ");
					sb.append(" order by SIIRESAKI_CD ");
					// ===BEGIN=== Add by kou 2006/10/4
					sb.append(" DESC ");
					// ===END=== Add by kou 2006/10/4
//					↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
					sb.append(" for read only ");
//					↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

					ResultSet rest = null;
					rest = db.executeQuery(sb.toString());

					if( Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) ){
						if(rest.next()){
							// ===BEGIN=== Add by kou 2006/10/4
							String systemKB = (String) sessionManager.getAttribute("mdwareSystemKb");
							if(systemKB != null && "J".equals(systemKB.trim())) {
								Keepb.setUpSiiresakiCd(rest.getString("SIIRESAKI_CD"));
							}
							// ===END=== Add by kou 2006/10/4
							Keepb.setUpSiiresakiKanjiRn(rest.getString("KANJI_RN"));
							if(Keepb.getSystemKb() != null && rest.getString("SIIRE_SYSTEM_KB") != null ){
								if( (!Keepb.getSystemKb().equals("T") && rest.getString("SIIRE_SYSTEM_KB").equals("2")) ||
									(Keepb.getSystemKb().equals("T") && rest.getString("SIIRE_SYSTEM_KB").equals("1"))){
									if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
										//仕入先システム区分エラー
										mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
										Keepb.setFirstFocus(CtlName);
										Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
										Keepb.setErrorMessage("指定された仕入先が不正です。");
									}
								}
							}
						} else {
							mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								//仕入先コード存在エラー
								mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
								Keepb.setFirstFocus(CtlName);
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定された仕入先コード"+map.get("40007").toString());
							}
						}
					} else {
						Keepb.setUpSiiresakiKanjiRn("");
					}
					if(rest != null){
						rest.close();
					}
				}

//			      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
				//ユニット存在チェック
				if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS) &&
					Keepb.getUpUnitChoice().equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){
					lst.clear();
					lst.add(" SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)+ "'");
					lst.add(" and ");
//					lst.add(" CODE_CD = '" + Keepb.getSystemKb() + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unitcd")) + "' ");
//					 MOD by Tanigawa 2006/11/09 障害票№0194対応 START
					lst.add(" CODE_CD = '" + Keepb.getSystemKb() + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unitcd_for_edit")) + "' ");
//					 MOD by Tanigawa 2006/11/09 障害票№0194対応  END
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst,mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
					Keepb.setUpunitKanjiRn(mstchk.getCdName());
					if( Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) ){
						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							//ユニット存在エラー
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_unitcd_for_edit");
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setFirstFocus("tx_unitcd");
//								 MOD by Tanigawa 2006/11/08 障害票№0194対応 START
								Keepb.setFirstFocus("tx_unitcd_for_edit");
//								 MOD by Tanigawa 2006/11/08 障害票№0194対応  END
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定されたユニットコード"+map.get("40007").toString());
							}
						}
					}
				}
			}
		}
	}
//			      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
}
