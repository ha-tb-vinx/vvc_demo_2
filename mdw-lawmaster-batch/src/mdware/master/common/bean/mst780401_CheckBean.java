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

/**
 * <p>タイトル	：新ＭＤシステム（マスタ管理）店別品種(店舗)登録画面のデータ入力チェッククラス</p>
 * <p>説明: 	：新ＭＤシステムで使用する店別品種(店舗)マスタ登録画面のデータ入力チェッククラス</p>
 * <p>著作権: 	：Copyright (c) 2005</p>
 * <p>会社名: 	：Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst780401_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
	public void Check(
						mst000101_DbmsBean db,
						mst780201_KeepBean Keepb,
						mst780301_KeepMeisaiBean KeepMeisaib,
						DataHolder dataHolder,
						String kengenCd,
						String GamenId,
						String FirstFocusCtl,
						String[] CtrlName,
						Map CtrlColor,
						SessionManager  sessionManager) throws Exception,SQLException {

		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		// 初期Focus
		Keepb.setFirstFocus(FirstFocusCtl);

		// マスタ管理日付
		DataHolder dh = new DataHolder();
		mst870201_MstDateBean mstDateBean = mst870201_MstDateBean.getBean(dh);
		String strMstDate = mstDateBean.getMstDateDt();
		String strMstDateNext = mstDateBean.getMstDateDtNext();
		Keepb.setMstDate(strMstDate);
		Keepb.setMstDateNext(strMstDateNext);

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){

			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)){

				//画面内容を保存(ヘッダ部)
				// 店舗コード
				Keepb.setTenpoCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tenpo_cd")).trim() );
				// 販区コード
				Keepb.setHankuCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanku_cd")).trim() );
				// 処理区分
				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syorikb")).trim() );

				//画面内容を保存(明細部)
				List meisai = KeepMeisaib.getMeisai();
				ArrayList meisaiList = new ArrayList();
				for (int i = 0; i < meisai.size(); i++) {

					mst780101_TenbetsuHinsyuTenpoBean dbBean = (mst780101_TenbetsuHinsyuTenpoBean)meisai.get(i);

					if( dbBean.getDisbale() == null || dbBean.getDisbale().equals("") ){

						// 選択フラグ
						dbBean.setSentaku(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku"+Integer.toString(i))));

						// 品種
						dbBean.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyu_cd"+Integer.toString(i))));
						// 品種名
						dbBean.setHinsyuNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyu_na"+Integer.toString(i))));
						// 取扱開始日
						dbBean.setAtukaiStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_atukai_st_dt"+Integer.toString(i))));
						// 取扱終了日
						dbBean.setAtukaiEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_atukai_ed_dt"+Integer.toString(i))));
						// ＰＣ送信区分
						if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentakuPC"+Integer.toString(i))).equals("")){
							dbBean.setPcSendKb("0");
						}else{
							dbBean.setPcSendKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentakuPC"+Integer.toString(i))));
						}
						// 送信日
						dbBean.setSendDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_send_dt"+Integer.toString(i))));
						// 登録日
						dbBean.setInsertTs(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_insert_ts"+Integer.toString(i))));
						// 登録者
						dbBean.setInsertUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_insert_user_id"+Integer.toString(i))));
						// 更新日
						dbBean.setUpdateTs(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_update_ts"+Integer.toString(i))));
						// 更新者
						dbBean.setUpdateUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_update_user_id"+Integer.toString(i))));
						// 単品数
						dbBean.setTanpinQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanpin_qt"+Integer.toString(i))));

						// 取扱開始日チェック
						String[] strReturn = new String[3];
						if(!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)){

							// 選択時のみ
							if( dbBean.getSentaku().equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){

								// 新規時
								if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_insert_ts"+Integer.toString(i))).equals("")){
									if(Long.parseLong(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_atukai_st_dt"+Integer.toString(i)))) < Long.parseLong(strMstDateNext)){

										mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_atukai_st_dt"+Integer.toString(i));
										Keepb.setErrorMessage("翌日以降の日付を入力してください。");
										if( Keepb.getErrorFlg().equals( mst000101_ConstDictionary.ERR_CHK_NORMAL ) ){
											Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
											Keepb.setFirstFocus("tx_atukai_st_dt"+Integer.toString(i));
										}
									}
								}else{

									if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_send_dt"+Integer.toString(i))).equals("")){

										if(Long.parseLong(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_atukai_st_dt"+Integer.toString(i)))) < Long.parseLong(strMstDateNext)){
											mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_atukai_st_dt"+Integer.toString(i));
											Keepb.setErrorMessage("翌日以降の日付を入力してください。");

											if( Keepb.getErrorFlg().equals( mst000101_ConstDictionary.ERR_CHK_NORMAL ) ){
												Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
												Keepb.setFirstFocus("tx_atukai_st_dt"+Integer.toString(i));
											}
										}
									}

								}
							}
						}
					}
					meisaiList.add(dbBean);
				}
				KeepMeisaib.setMeisai(meisaiList);
			}

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			// メニューバーアイコン取得
			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();
			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
			Map menuMap = new HashMap();
			menuMap.put("gamen_id", GamenId);
			menuMap.put("kengen_cd", kengenCd);
			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
			Keepb.setMenuBar(menu);

			//エラーチェック
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

			//検索条件チェック-----------------------------------------------------------------------------
			// 店舗コード--------------------------------------
			if((!Keepb.getTenpoCd().equals(""))){
				lst.clear();
				lst.add( " TENPO_CD = '" + Keepb.getTenpoCd() + "' " );
// 2006.01.25 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENPO", "KANJI_RN", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENPO", "KANJI_RN", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
				Keepb.setTenpoNa(mstchk.getCdName());
			}else{
				Keepb.setTenpoNa("");
			}

			// 販区----------------------------------------
			TreeMap map2 = new TreeMap(mst000401_LogicBean.getMsg());
			if(!Keepb.getHankuCd().equals("")){
				HankuCheck( db, Keepb, CtrlColor, sessionManager, map2, 0 );
			} else {
				Keepb.setHankuNa("");
			}
		}
	}

	/**
	 *
	 * 販区のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst780201_KeepBean Keepb				: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */
	public void  HankuCheck( mst000101_DbmsBean db, mst780201_KeepBean Keepb, Map CtrlColor,
								 SessionManager  sessionManager, TreeMap map, int flg ) throws Exception,SQLException {
		String CtlName = "tx_hanku_cd";
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		List lst = new ArrayList();
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

		lst.add("	 SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "'");				// 種別コード
		lst.add("AND CODE_CD       = '" + Keepb.getHankuCd() + "'");							// 販区コード
// 2006.01.25 Y.Inaba Mod ↓
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
// 2006.01.25 Y.Inaba Mod ↑
		Keepb.setHankuNa(mstchk.getCdName());

		if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
			// 販区コード存在エラー
			mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
			if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
				Keepb.setFirstFocus(CtlName);
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				if(flg == 0){
					Keepb.setErrorMessage("指定された販区コード" + map.get("40007").toString());
				} else {
					Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
				}
			}
		} else {
			// 選択業種との関連チェック
//			↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//			if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
//				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					Keepb.setFirstFocus(CtlName);
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					if( flg == 0){
//						Keepb.setErrorMessage(map.get("40022").toString());
//					} else {
//						Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
//					}
//				}
//			}
//			↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
		}
	}
}
