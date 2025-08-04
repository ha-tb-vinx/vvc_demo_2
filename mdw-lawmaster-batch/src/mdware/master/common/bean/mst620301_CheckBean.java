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
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @see なし
 */
public class mst620301_CheckBean{
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		mst620201_KeepBean 			Keepb
	 * @param 		DataHolder 					dataHolder
	 * @param		String						kengenCd		: 権限コード
 	 * @param		String 						GamenId 		: 画面ID
	 * @param		String 						FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		String[] 					CtrlName 		: コントロール名
	 * @param		Map 						CtrlColor 		: コントロールカラー
	 * @param		SessionManager 				sessionManager 	: セションマネージャー
	 */
	public void Check( mst620201_KeepBean	Keepb
						,DataHolder 		dataHolder
						,String 			kengenCd
						,String 			GamenId
						,String 			FirstFocusCtl
						,String[] 			CtrlName
						,Map 				CtrlColor
						,String				tableNa
						,String				columnNa
						,List				whereList
						,String				addValue
						,SessionManager 	sessionManager
						,mst000101_DbmsBean db
						) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");
		//エラーチェック
		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//操作権限設定
			//画面内容を保存
			Keepb.setGyosyuKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_gyosyucd")).trim() );
			Keepb.setHankuCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")).trim() );
			Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")).trim() );
			Keepb.setHankuNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanku_nm")).trim() );
			Keepb.setHinmeiKanjiNa( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanjina")).trim() );

			Keepb.setDZokusei1Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei1")).trim() );
			Keepb.setDZokusei2Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei2")).trim() );
			Keepb.setDZokusei3Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei3")).trim() );
			Keepb.setDZokusei4Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei4")).trim() );
			Keepb.setDZokusei5Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei5")).trim() );
			Keepb.setDZokusei6Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei6")).trim() );
			Keepb.setDZokusei7Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei7")).trim() );
			Keepb.setDZokusei8Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei8")).trim() );
			Keepb.setDZokusei9Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei9")).trim() );
			Keepb.setDZokusei10Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_ct_dzokusei10")).trim());
			Keepb.setSZokusei1Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei1")).trim() );
			Keepb.setSZokusei2Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei2")).trim() );
			Keepb.setSZokusei3Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei3")).trim() );
			Keepb.setSZokusei4Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei4")).trim() );
			Keepb.setSZokusei5Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei5")).trim() );
			Keepb.setSZokusei6Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei6")).trim() );
			Keepb.setSZokusei7Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei7")).trim() );
			Keepb.setSZokusei8Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei8")).trim() );
			Keepb.setSZokusei9Na( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei9")).trim() );
			Keepb.setSZokusei10Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_szokusei10")).trim() );
			Keepb.setBeforeDispId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("beforedispid")).trim() );

			if(!Keepb.getDZokusei1Na().equals("")){
				Keepb.setDZokusei1Na( Keepb.getDZokusei1Na().substring( Keepb.getDZokusei1Na().length()-3, Keepb.getDZokusei1Na().length()));
			}
			if(!Keepb.getDZokusei2Na().equals("")){
				Keepb.setDZokusei2Na( Keepb.getDZokusei2Na().substring( Keepb.getDZokusei2Na().length()-3, Keepb.getDZokusei2Na().length()));
			}
			if(!Keepb.getDZokusei3Na().equals("")){
				Keepb.setDZokusei3Na( Keepb.getDZokusei3Na().substring( Keepb.getDZokusei3Na().length()-3, Keepb.getDZokusei3Na().length()));
			}
			if(!Keepb.getDZokusei4Na().equals("")){
				Keepb.setDZokusei4Na( Keepb.getDZokusei4Na().substring( Keepb.getDZokusei4Na().length()-3, Keepb.getDZokusei4Na().length()));
			}
			if(!Keepb.getDZokusei5Na().equals("")){
				Keepb.setDZokusei5Na( Keepb.getDZokusei5Na().substring( Keepb.getDZokusei5Na().length()-3, Keepb.getDZokusei5Na().length()));
			}
			if(!Keepb.getDZokusei6Na().equals("")){
				Keepb.setDZokusei6Na( Keepb.getDZokusei6Na().substring( Keepb.getDZokusei6Na().length()-3, Keepb.getDZokusei6Na().length()));
			}
			if(!Keepb.getDZokusei7Na().equals("")){
				Keepb.setDZokusei7Na( Keepb.getDZokusei7Na().substring( Keepb.getDZokusei7Na().length()-3, Keepb.getDZokusei7Na().length()));
			}
			if(!Keepb.getDZokusei8Na().equals("")){
				Keepb.setDZokusei8Na( Keepb.getDZokusei8Na().substring( Keepb.getDZokusei8Na().length()-3, Keepb.getDZokusei8Na().length()));
			}
			if(!Keepb.getDZokusei9Na().equals("")){
				Keepb.setDZokusei9Na( Keepb.getDZokusei9Na().substring( Keepb.getDZokusei9Na().length()-3, Keepb.getDZokusei9Na().length()));
			}
			if(!Keepb.getDZokusei10Na().equals("")){
				Keepb.setDZokusei10Na(Keepb.getDZokusei10Na().substring(Keepb.getDZokusei10Na().length()-3,Keepb.getDZokusei10Na().length()));
			}

			if(!Keepb.getSZokusei1Na().equals("")){
				Keepb.setSZokusei1Na( Keepb.getSZokusei1Na().substring(Keepb.getSZokusei1Na().length()-3,Keepb.getSZokusei1Na().length()));
			}
			if(!Keepb.getSZokusei2Na().equals("")){
				Keepb.setSZokusei2Na( Keepb.getSZokusei2Na().substring(Keepb.getSZokusei2Na().length()-3,Keepb.getSZokusei2Na().length()));
			}
			if(!Keepb.getSZokusei3Na().equals("")){
				Keepb.setSZokusei3Na( Keepb.getSZokusei3Na().substring(Keepb.getSZokusei3Na().length()-3,Keepb.getSZokusei3Na().length()));
			}
			if(!Keepb.getSZokusei4Na().equals("")){
				Keepb.setSZokusei4Na( Keepb.getSZokusei4Na().substring(Keepb.getSZokusei4Na().length()-3,Keepb.getSZokusei4Na().length()));
			}
			if(!Keepb.getSZokusei5Na().equals("")){
				Keepb.setSZokusei5Na( Keepb.getSZokusei5Na().substring(Keepb.getSZokusei5Na().length()-3,Keepb.getSZokusei5Na().length()));
			}
			if(!Keepb.getSZokusei6Na().equals("")){
				Keepb.setSZokusei6Na( Keepb.getSZokusei6Na().substring(Keepb.getSZokusei6Na().length()-3,Keepb.getSZokusei6Na().length()));
			}
			if(!Keepb.getSZokusei7Na().equals("")){
				Keepb.setSZokusei7Na( Keepb.getSZokusei7Na().substring(Keepb.getSZokusei7Na().length()-3,Keepb.getSZokusei7Na().length()));
			}
			if(!Keepb.getSZokusei8Na().equals("")){
				Keepb.setSZokusei8Na( Keepb.getSZokusei8Na().substring(Keepb.getSZokusei8Na().length()-3,Keepb.getSZokusei8Na().length()));
			}
			if(!Keepb.getSZokusei9Na().equals("")){
				Keepb.setSZokusei9Na( Keepb.getSZokusei9Na().substring(Keepb.getSZokusei9Na().length()-3,Keepb.getSZokusei9Na().length()));
			}
			if(!Keepb.getSZokusei10Na().equals("")){
				Keepb.setSZokusei10Na(Keepb.getSZokusei10Na().substring(Keepb.getSZokusei10Na().length()-3,Keepb.getSZokusei10Na().length()));
			}

			Keepb.setSentaku1(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku1")).trim() );
			Keepb.setSentaku2(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku2")).trim() );
			Keepb.setSentaku3(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku3")).trim() );
			Keepb.setSentaku4(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku4")).trim() );
			Keepb.setSentaku5(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku5")).trim() );
			Keepb.setSentaku6(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku6")).trim() );
			Keepb.setSentaku7(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku7")).trim() );
			Keepb.setSentaku8(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku8")).trim() );
			Keepb.setSentaku9(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku9")).trim() );
			Keepb.setSentaku10(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku10")).trim() );
			Keepb.setUpdateTs(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_updatets")).trim() );
			Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim() );

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

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
			//検索処理チェック-----------------------------------------------------------------------------
			//種別の取得
			String Syubetu = new String();
			String Hanku = new String();
			Syubetu = mst000101_ConstDictionary.BUNRUI3;

			//販区コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();

			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			if(!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")).equals("")){
				lst.add( "SYUBETU_NO_CD = " + MessageUtil.getMessage(Syubetu, userLocal) );
				lst.add( " and " + " CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")) + "' ");
// 2006.01.25 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//販区コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hankucd");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setFirstFocus("ct_hankucd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
					}
				} else {
					Keepb.setHankuNm(mstchk.getCdName());
//					if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
//					↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//						if(!mst000401_LogicBean.getHankuCdCheck(db,mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")),sessionManager)){
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hankucd");
//							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//								Keepb.setFirstFocus("ct_hankucd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40022").toString());
//							}
//						}
//					↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
//					}
				}
			}

			//商品コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			String mstTableNa = "R_SYOHIN";
			String MSTDATE = RMSTDATEUtil.getMstDateDt();
			int intCnt = 0;
			if(!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")).trim().equals("")){
				//販区入力済（商品コード存在チェック）
				lst.add( "HANKU_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")) + "' ");
				lst.add( " and " + " SYOHIN_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")) + "' ");
// 2006.01.25 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,mstTableNa,"HINMEI_KANJI_NA", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db,mstTableNa,"HINMEI_KANJI_NA", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setFirstFocus("ct_syohincd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40026").toString());
					}
				}
			} else {
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				//販区未入力（販区コード複数登録チェック）
				lst.add( " SYOHIN_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")) + "' ");
				lst.add( " and " + " DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
				lst.add( " GROUP BY ");
				lst.add( " 		SYOHIN_CD,HANKU_CD ");
				rset = db.executeQuery( mstDM.getCountSql(mstTableNa,lst) );    //抽出結果(ResultSet)
				while(rset.next()){
					intCnt = intCnt + 1;
				}

				if(intCnt > 1){
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setFirstFocus("ct_syohincd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("販区コード" + map.get("0021").toString());
					}
				}

				//販区未入力（取得販区に対する売区チェック）
				if(intCnt == 1){
					//販区未入力（販区コード複数登録チェック）
					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add( " SYOHIN_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")) + "' ");
					lst.add( " and " + " DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//					lst.add( " GROUP BY ");
//					lst.add( " 		SYOHIN_CD,HANKU_CD ");
// 2006.01.25 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HANKU_CD", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HANKU_CD", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
					if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						Keepb.setHankuCd(mst000401_LogicBean.chkNullString(mstchk.getCdName()));
					}

					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add( "SYUBETU_NO_CD = " + MessageUtil.getMessage(Syubetu, userLocal) );
					lst.add( " and " + " CODE_CD = '" + mst000401_LogicBean.chkNullString(Keepb.getHankuCd()) + "' ");
// 2006.01.25 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
					if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						Keepb.setHankuNm(mstchk.getCdName());
					}

					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add( " SYOHIN_CD   = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")) + "' ");
					lst.add( " and " + " DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
// 2006.01.25 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,mstTableNa,"hanku_cd", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,mstTableNa,"hanku_cd", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
					if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//						↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//						if(!mst000401_LogicBean.getHankuCdCheck(db,mst000401_LogicBean.chkNullString(mstchk.getCdName()).trim(),sessionManager)){
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
//							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//								Keepb.setFirstFocus("ct_hankucd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40026").toString());
//							} else if(intCnt != 0){
//							}
//						}
//						↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
					}
				}
			}

			//有効日取得
			whereList.add( " AND HANKU_CD  = '" + mst000401_LogicBean.chkNullString(Keepb.getHankuCd()) + "' " );
			Keepb.setSyohinYukoDt(mst001001_EffectiveDayBean.getGenYoyaku(tableNa,columnNa,whereList,addValue,db));
			Keepb.setSyohinYukoDt(mst000201_EffectiveDayCheckBean.getNowYukoDt(db,tableNa,columnNa,whereList,MSTDATE,true));

			//現在有効日での存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			lst.add( "     SYOHIN_CD   = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")) + "' ");
			lst.add( " and DELETE_FG   = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			lst.add( " and HANKU_CD    = '" + Keepb.getHankuCd() + "' ");
			lst.add( " and YUKO_DT     = '" + Keepb.getSyohinYukoDt() + "' ");
			rset = db.executeQuery( mstDM.getCountSql(mstTableNa,lst) );    //抽出結果(ResultSet)
			if(rset.next()){
				//対象レコードあり
			} else {
				//対象レコードなし（予約レコードを検索）
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				lst.add( "     SYOHIN_CD   = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")) + "' ");
				lst.add( " and DELETE_FG   = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
				lst.add( " and HANKU_CD    = '" + Keepb.getHankuCd() + "' ");
				lst.add( " and YUKO_DT     = ( ");
				lst.add( "						SELECT MIN(YUKO_DT) YUKO_DT_MIN ");
				lst.add( "						FROM R_SYOHIN ");
				lst.add( "						WHERE         ");
				lst.add( "     							SYOHIN_CD   = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")) + "' ");
				lst.add( " 							and DELETE_FG   = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
				lst.add( " 							and HANKU_CD    = '" + Keepb.getHankuCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//				lst.add( " 							and TRIM(YUKO_DT) > TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");
				lst.add( " 							and TRIM(YUKO_DT) > '"+ RMSTDATEUtil.getMstDateDt() +"' ");
// 2006.01.24 Y.Inaba Mod ↑
				lst.add( " 					) ");
// 2006.01.25 Y.Inaba Mod ↓
//				rset = db.executeQuery( mstDM.getSelectSql(mstTableNa,"YUKO_DT",lst,"0") );    //抽出結果(ResultSet)
				rset = db.executeQuery( mstDM.getSelectSql(mstTableNa,"YUKO_DT",lst,"0", "") );    //抽出結果(ResultSet)
// 2006.01.25 Y.Inaba Mod ↑
				if(rset.next()){
					//対象あり
					Keepb.setSyohinYukoDt(mst000401_LogicBean.chkNullString(rset.getString("yuko_dt")).trim());
				} else {
					//対象なし
					Keepb.setSyohinYukoDt("");
				}
			}
			Keepb.setCtrlColor(CtrlColor);

			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			lst.add( "     SYOHIN_CD   = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")) + "' ");
			lst.add( " and DELETE_FG   = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			lst.add( " and HANKU_CD    = '" + Keepb.getHankuCd() + "' ");
			lst.add( " and YUKO_DT     = '" + Keepb.getSyohinYukoDt() + "' ");
// 2006.01.25 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,mstTableNa,"gyosyu_kb", lst, "0");
			mstchk = mst000401_LogicBean.getMasterCdName(db,mstTableNa,"gyosyu_kb", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
			if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				//対象レコードあり
				lst = new ArrayList();
				lst.add("     SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.LARGE_ATTRIBUTE, userLocal) + "' ");
				lst.add(" and CODE_CD LIKE '" + mst000401_LogicBean.chkNullString(mstchk.getCdName()) + "%' ");
				lst.add(" and DELETE_FG   = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
				rset = db.executeQuery( mstDM.getCountSql("R_NAMECTF",lst) );    //抽出結果(ResultSet)
				if(rset.next()){
					if( rset.getLong("cnt") > 0 ){
						//対象レコードあり
						lst = new ArrayList();
						lst.add("     SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SMALL_ATTRIBUTE, userLocal) + "' ");
						lst.add(" and CODE_CD LIKE '" + mst000401_LogicBean.chkNullString(mstchk.getCdName()) + "%' ");
						lst.add(" and DELETE_FG   = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
						rset = db.executeQuery( mstDM.getCountSql("R_NAMECTF",lst) );    //抽出結果(ResultSet)
						if(rset.next()){
							if( rset.getLong("cnt") > 0 ){
							} else {
								//対象レコードなし
								mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hankucd");
								mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
								if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
									Keepb.setFirstFocus("ct_hankucd");
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("40221").toString());
								}
							}
						}
					} else {
						//対象レコードなし
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_hankucd");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setFirstFocus("ct_hankucd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40221").toString());
						}
					}
				}
			} else {
			}
		}
	}
}