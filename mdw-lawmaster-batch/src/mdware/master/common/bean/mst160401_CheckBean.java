package mdware.master.common.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
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
public class mst160401_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
//  ↓↓2006.07.10 lixy カスタマイズ修正↓↓
//  public void Check( mst000101_DbmsBean	db, mst160201_KeepBean Keepb, mst160301_KeepMeisaiBean KeepMeisaib, DataHolder dataHolder ,String kengenCd ,String GamenId
//	            ,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor ,SessionManager sessionManager )throws SQLException,Exception {

	private StcLog stcLog = StcLog.getInstance();

	public void Check( mst000101_DbmsBean	db, mst160201_KeepBean Keepb, mst160301_KeepMeisaiBean KeepMeisaib, DataHolder dataHolder ,String GamenId
				,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor ,SessionManager sessionManager )throws SQLException,Exception {
//	    ↑↑2006.07.10 lixy カスタマイズ修正↑↑

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

//		  ↓↓2006.07.10 lixy カスタマイズ修正↓↓
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
//			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
//			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");
//		    ↑↑2006.07.10 lixy カスタマイズ修正↑↑

			//操作権限設定
			if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT) ){
				//画面内容を保存
//				↓↓2006.07.10 lixy カスタマイズ修正↓↓
				Keepb.setBumonCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumoncd")).trim() );
//				Keepb.setBumonKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_nm")).trim() );
				Keepb.setHinbanCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinbancd")).trim() );
//				Keepb.setHinbanKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinban_nm")).trim() );
				Keepb.setRayinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_linecd")).trim() );
//				Keepb.setRanyinKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_line_nm")).trim() );
				Keepb.setUnitCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unitcd")).trim() );
//				Keepb.setUnitKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unit_nm")).trim() );
				Keepb.setSiiresakisyohinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakisyihincd")).trim() );
//				Keepb.setSiiresakisyohinKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakisyihin_nm")).trim() );
				Keepb.setHachuStDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hattyuustdt")).trim() );
				Keepb.setHachuEdDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hattyuueddt")).trim() );
				Keepb.setSabkurasCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sabukurasucd")).trim() );
				Keepb.setSabkurasKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sabukurasu_nm")).trim() );
				Keepb.setTananostNb( editIntegerValue(dataHolder.getParameter("tx_tanawaribangoufm")));
				Keepb.setTananoedNb( editIntegerValue(dataHolder.getParameter("tx_tanawaribangouto")));

				Keepb.setBaisttanka( editIntegerValue(dataHolder.getParameter("tx_baitankavlfm")));
				Keepb.setBaiedtanka( editIntegerValue(dataHolder.getParameter("tx_baitankavlto")));
				mst000301_SelectBean SelectBean = new mst000301_SelectBean();
				Keepb.setSyohinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syouhinkb")).trim());

				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syouhinkb")).trim().equals("")) {
					Keepb.setSyohinkanjiRn( SelectBean.subCtfVal2("1060", mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syouhinkb")), "0") );
				} else {
					Keepb.setSyohinkanjiRn("");
				}
				Keepb.setByNo( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_byno")).trim() );
//			    ↑↑2006.07.10 lixy カスタマイズ修正↑↑
				Keepb.setHinsyuCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyucd")).trim() );
//				Keepb.setHinsyuKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyu_nm")).trim() );
				Keepb.setSiiresakiCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakicd")).trim() );
//				Keepb.setSiiresakiKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_nm")).trim() );
				Keepb.setJanMarkCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_janmarkcd")).trim() );
//				Keepb.setJanMarkKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_janmark_nm")).trim() );
				Keepb.setHanbaiStDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanbaistdt")).trim() );
				Keepb.setHanbaiEdDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanbaieddt")).trim() );
				Keepb.setSeasonCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_seasoncd")).trim() );
//				Keepb.setSeasonKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_season_nm")).trim() );

// ↓↓仕様変更（2005.07.16）↓↓
//				Keepb.setTenGroupnoCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tengroupnocd")).trim() );
//				Keepb.setTenGroupnoNameNa( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tengroupno_nm")).trim() );
// ↑↑仕様変更（2005.07.16）↑↑

				stcLog.getLog().info("チェックボックスの件数確認処理 start ");
				ArrayList Meisai = new ArrayList();
				int cnt = 0;
				int iCountCheckNum = 0;
				for (int i = 0;i < KeepMeisaib.getMeisai().size(); i++) {
					mst160101_SyohinIkkatuMenteBean arrMeisai = (mst160101_SyohinIkkatuMenteBean)KeepMeisaib.getMeisai().get(i);
					if(i >= Integer.parseInt(KeepMeisaib.getStartRowInPage()) - 1
					 &&  i < Integer.parseInt(KeepMeisaib.getEndRowInPage())){
						arrMeisai.setSentakuFg( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sentaku"+Integer.toString(cnt))).trim() );

						cnt++;
					 }
//					 ADD by Tanigawa 2006/9/28 障害票№0086対応 チェック件数を画面情報保持Beanにセットする START
					if( mst000401_LogicBean.chkNullString(arrMeisai.getSentakuFg()).equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){
						iCountCheckNum = iCountCheckNum + 1;
					}
//					 ADD by Tanigawa 2006/9/28 障害票№0086対応 チェック件数を画面情報保持Beanにセットする  END
					Meisai.add( arrMeisai );
				}
				stcLog.getLog().info("チェックボックスの件数確認処理  end  ");
				KeepMeisaib.setMeisai(Meisai);
				KeepMeisaib.setPageMode( mst000401_LogicBean.chkNullString(dataHolder.getParameter("movePageMode")).trim() );

//				 ADD by Tanigawa 2006/11/14 障害票№0143対応 START
				Keepb.setSiiresakiSyohinCdCBChecked( (dataHolder.getParameterValues("cb_siiresakisyohincd_ambiguous").length == 1) );
//				 ADD by Tanigawa 2006/11/14 障害票№0143対応  END

//				 ADD by Tanigawa 2006/9/28 障害票№0086対応 チェック件数を画面情報保持Beanにセットする START
				Keepb.setProcNum( iCountCheckNum );	//チェック件数(処理対象件数)をセット
//				 ADD by Tanigawa 2006/9/28 障害票№0086対応   END

					//画面内容を保存
//				 ADD by Tanigawa 2006/11/10 障害票№0194対応 START
				Keepb.setYukoDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt")));

				Keepb.setUpSiiresakikanriChoice(mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_siiresakikanricd")));
				Keepb.setUpSiiresakikanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakikanricd")));
				Keepb.setUpSiiresakikanriKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakikanri_nm")));

				Keepb.setUpSiiresakiChoice( mst000401_LogicBean.chkNullString(dataHolder.getParameter("cb_siiresaki")) );
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
//				ADD by Tanigawa 2006/11/10 障害票№0194対応   END
//				ADD by Tanigawa 2006/11/16 障害票№XXXX対応  START
				Keepb.setScrollPos(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_scrollPos")));
//				ADD by Tanigawa 2006/11/16 障害票№XXXX対応   END
			}

			//チェック
			if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)
				&& !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT) ){
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
//		       ↓↓2006.07.10 lixy カスタマイズ修正↓↓
//				String name = "";				//戻り値格納
//				String INIT_PAGE ="";
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				//販区
//				if(!Keepb.getHankuCd().equals("")){
//					lst.add("SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");
//					lst.add(" and ");
//					lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.01.24 Y.Inaba Mod ↑
//					Keepb.setHankuKanjiRn(mstchk.getCdName());
//					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//管理コード存在エラー
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_kanricd");
//						Keepb.setFirstFocus("tx_kanricd");
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_hankucd");
//						Keepb.setFirstFocus("tx_hankucd");
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
//					} else {
//						if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
//								mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_kanricd");
//								mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_hankucd");
//								Keepb.setFirstFocus("tx_hankucd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40022").toString());
//						}
//					}
//				} else {
//					Keepb.setHankuKanjiRn("");
//				}
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
	  			//部門コード存在チェック
	  			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumoncd")).equals("")) {
	  				lst.clear();
	  				lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
	  				lst.add(" and "+ "code_cd =  '" + StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true).trim() + "' ");
	  				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
	  				Keepb.setBumonKanjiRn(mstchk.getCdName());
	  				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
	  					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_bumoncd");
	  					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
	  						Keepb.setFirstFocus("tx_bumoncd");
	  						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
	  						Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
	  					}
	  				}
	  			} else {
					Keepb.setBumonKanjiRn("");
				}

	  			//品番存在チェック
	  			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinbancd")).equals("")) {
	  				lst.clear();
	  				lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
	  				lst.add(" and "+ "code_cd = '" + StringUtility.charFormat(Keepb.getHinbanCd(),4,"0",true).trim() + "' ");
	  				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
	  				Keepb.setHinbanKanjiRn(mstchk.getCdName());
	  				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
	  					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_hinbancd");
	  					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
	  						Keepb.setFirstFocus("tx_hinbancd");
	  						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
	  						Keepb.setErrorMessage("指定された品番コード" + map.get("40007").toString());

	  					}
	  				}
	  			} else {
					Keepb.setHinbanKanjiRn("");
				}

				//品種
				//if(!Keepb.getHinsyuCd().equals("")){
	  			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyucd")).equals("")) {
					lst.clear();
					lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal)+ "' ");
					lst.add(" and ");
					//lst.add("CODE_CD = '" + Keepb.getHinsyuCd() + "' ");
					lst.add("code_cd = '" + StringUtility.charFormat(Keepb.getHinsyuCd(),4,"0",true).trim() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
					//mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.01.24 Y.Inaba Mod ↑
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
					Keepb.setHinsyuKanjiRn(mstchk.getCdName());
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_hinsyucd");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setFirstFocus("tx_hinsyucd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された品種コード"+map.get("40007").toString());
						}
					}
//					} else {
//						if(!mst000401_LogicBean.getHinshuCdCheck(db,Keepb.getHinsyuCd(),sessionManager)){
//								mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_hinsyucd");
//								Keepb.setFirstFocus("tx_hinsyucd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40023").toString());
//						}
//					}
				} else {
					Keepb.setHinsyuKanjiRn("");
				}

	  			//ライン存在チェック
	  			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_linecd")).equals("")) {
	  				lst.clear();
	  				lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI4, userLocal) + "' ");
	  				lst.add(" and "+ "code_cd = '" + Keepb.getRayinbanCd() + "' ");
	  				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
	  				Keepb.setRanyinKanjiRn(mstchk.getCdName());
	  				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
	  					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_linecd");
	  					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
		  					Keepb.setFirstFocus("tx_linecd");
	  						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
	  						Keepb.setErrorMessage("指定されたラインコード" + map.get("40007").toString());
	  					}
	  				}
	  			} else {
					Keepb.setRanyinKanjiRn("");
				}

	  			//　ユニット存在チェック
	  			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unitcd")).equals("")) {
	  				lst.clear();
	  				lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
// ↓↓2006.08.03 HuangJiugen カスタマイズ修正↓↓
//	  				lst.add(" and "+ "code_cd = '" + Keepb.getUnitCd() + "' ");
	  				lst.add(" and "+ "code_cd = '" + Keepb.getSystemKb() + Keepb.getUnitCd() + "' ");
// ↑↑2006.08.03 HuangJiugen カスタマイズ修正↑↑
	  				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
	  				Keepb.setUnitKanjiRn(mstchk.getCdName());
	  				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
	  					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_unitcd");
	  					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
		  					Keepb.setFirstFocus("tx_unitcd");
	  						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
	  						Keepb.setErrorMessage("指定されたユニットコード" + map.get("40007").toString());
	  					}
	  				}
	  			} else {
					Keepb.setUnitKanjiRn("");
				}

				//仕入先
//				if(!Keepb.getSiiresakiCd().equals("")){
//					lst.clear();
//					↓↓2006.07.10 lixy カスタマイズ修正↓↓
//					if(!Keepb.getHankuCd().equals("")){

						//業種コード取得
//						String gyosyuID = SysUserBean.getSelectedGyosyuCd();
						//業種が生鮮の場合
//						if (gyosyuID.equals("04")) {
//							lst.add("KANRI_KB = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");
//							lst.add(" and ");
//							lst.add("KANRI_CD = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "' ");
//							lst.add(" and ");
//
//						//業種が生鮮以外の場合
//						} else {
//							lst.add("KANRI_KB = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//							lst.add(" and ");
//							lst.add("KANRI_CD = '" + Keepb.getHankuCd()+ "' ");
//							lst.add(" and ");
//						}
//					}
//					lst.add(" KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
//					if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumoncd")).equals("")){
//			            lst.add( " and KANRI_CD      = '" + mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("tx_bumoncd"), 4, "0", true)) + "' ");
//					}
//					lst.add(" and ");
//
//					lst.add(" TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//					lst.add(" and ");
//					lst.add(" SIIRESAKI_CD = '" + Keepb.getSiiresakiCd() + "' " );
//// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
					//mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.01.24 Y.Inaba Mod ↑
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
//					Keepb.setSiiresakiKanjiRn(mstchk.getCdName());
//					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//						//仕入先コード存在エラー
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_siiresakicd");
//						Keepb.setFirstFocus("tx_siiresakicd");
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							//Keepb.setErrorBackDisp( INIT_PAGE );
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage("指定された仕入先コード"+map.get("40007").toString());
//						}
//					}
//				} else {
//					Keepb.setSiiresakiKanjiRn("");
//				}

	  			//仕入先コード存在チェック
	  			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakicd")).equals("")) {
	  				lst.clear();
	  				lst.add(" kanri_kb = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
	  				lst.add(" and ");
// ↓↓2006.08.03 HuangJiugen カスタマイズ修正↓↓
//	  				lst.add(" kanri_cd = '"+ StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true).trim() +"' ");
	  				lst.add(" kanri_cd = '0000' ");
// ↑↑2006.08.03 HuangJiugen カスタマイズ修正↑↑
	  				lst.add(" and ");
	  				lst.add(" siiresaki_cd = ");
	  				lst.add("(select max(siiresaki_cd) from R_Siiresaki where ");
	  				lst.add("kanri_kb = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' and ");
					lst.add(" kanri_cd = '0000' and ");
					lst.add(" siiresaki_cd like '"+ Keepb.getSiiresakiCd() +"%')");

	  				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
	  				Keepb.setSiiresakiKanjiRn(mstchk.getCdName());
	  				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
	  					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_siiresakicd");
	  					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
		  					Keepb.setFirstFocus("tx_siiresakicd");
	  						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
	  						Keepb.setErrorMessage("指定された仕入先コード" + map.get("40007").toString());
	  					}
	  				}
	  			} else {
					Keepb.setSiiresakiKanjiRn("");
				}

//				JANメーカ存在チェック
				//boolean janChk = false;
				if(!Keepb.getJanMarkCd().equals("")){
// ↓↓バグ修正（2005.08.17）↓↓
					//if (Keepb.getJanMarkCd().length() == 7 || Keepb.getJanMarkCd().length() == 6) {
					//	if ((Integer.parseInt(Keepb.getJanMarkCd().substring(0,3)) >= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_7_ST)
					//		&&	Integer.parseInt(Keepb.getJanMarkCd().substring(0,3)) <= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_7_ED))
					//		||	Keepb.getJanMarkCd().startsWith(mst000101_ConstDictionary.JAN_MAKER_49)) {
					//
					//		janChk = true;
					//	}
					//} else if (Keepb.getJanMarkCd().length() == 9) {
					//	if (Integer.parseInt(Keepb.getJanMarkCd().substring(0,3)) >= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_9_ST)
					//		&&	Integer.parseInt(Keepb.getJanMarkCd().substring(0,3)) <= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_9_ED)) {

					//		janChk = true;
					//	}
					//}
					lst.clear();
					lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.JAN_MAKER_NAME, userLocal)+ "' ");
					lst.add(" and ");
					lst.add("CODE_CD = '" + Keepb.getJanMarkCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
					//mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.01.24 Y.Inaba Mod ↑
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
					Keepb.setJanMarkKanjiRn(mstchk.getCdName());
// ↓↓バグ修正（2005.08.17）↓↓
					//if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//if(!janChk){
// ↑↑バグ修正（2005.08.17）↑↑
// 					↓↓2006.11.22 H.Yamamoto カスタマイズ修正↓↓
//					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_janmarkcd");
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus("tx_janmarkcd");
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage("指定されたＪＡＮメーカコード"+map.get("40007").toString());
//						}
//					}
//					↑↑2006.11.22 H.Yamamoto カスタマイズ修正↑↑
				} else {
					Keepb.setJanMarkKanjiRn("");
				}

				//サブクラスコード存在チェック
	  			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sabukurasucd")).equals("")) {
	  				lst.clear();
	  				lst.add( "BUMON_CD = '" + StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true).trim() + "' "   );
	  				lst.add("and  SUBCLASS_CD = '" + mst000401_LogicBean.chkNullString(Keepb.getSabkurasCd()) + "' ");
	  				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SUBCLASS","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
	  				Keepb.setSabkurasKanjiRn(mstchk.getCdName());
	  				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
	  					mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_sabukurasucd");
	  					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
		  					Keepb.setFirstFocus("tx_sabukurasucd");
	  						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
	  						Keepb.setErrorMessage("指定されたサブクラスコード"+  map.get("40007").toString());
	  					}
	  				}
	  			} else {
					Keepb.setSabkurasKanjiRn("");
				}

				//シーズン存在チェック
				//if(!Keepb.getSeasonCd().equals("")){
	  			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_seasoncd")).equals("")) {
					lst.clear();
					lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SEASON, userLocal)+ "' ");
					lst.add(" and ");
					lst.add("CODE_CD = '" + Keepb.getSeasonCd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
					//mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.01.24 Y.Inaba Mod ↑
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
					Keepb.setSeasonKanjiRn(mstchk.getCdName());
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_seasoncd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("tx_seasoncd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定されたシーズンコード"+map.get("40007").toString());
						}
					}
				} else {
					Keepb.setSeasonKanjiRn("");
				}
//			    ↑↑2006.07.10 lixy カスタマイズ修正↑↑

// ↓↓仕様変更（2005.07.16）↓↓
				//物流用店配列
//				if(!Keepb.getTenGroupnoCd().equals("")){
//					mst000501_SysSosasyaBean SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
//					lst.clear();
//					lst.add("L_GYOSYU_CD = '" + SysUserBean.getGyosyuCd()+"' ");
//					lst.add(" and ");
//					lst.add("YOTO_KB	 = '" + mst003901_YotoKbDictionary.BUTURYU.getCode() + "' ");
//					lst.add(" and ");
//					lst.add("GROUPNO_CD  = '" + Keepb.getTenGroupnoCd() + "' ");
//
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUPNO","NAME_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//					Keepb.setTenGroupnoNameNa(mstchk.getCdName());
//					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_tengroupnocd");
//						Keepb.setFirstFocus("tx_tengroupnocd");
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された物流用店配列コード"+map.get("40007").toString());
//					}
//				} else {
//					Keepb.setTenGroupnoNameNa("");
//				}
//				↑↑仕様変更（2005.07.16）↑↑
			}
		}
	}
//	↓↓2006.07.20 lixy カスタマイズ修正↓↓
	/**
	 * JDK1.4からは使用できるようになったString.replaceAllをJDK1.3以前用に作成する。
	 * @param base
	 * @param before
	 * @param after
	 * @return
	*/
	protected String replaceAll( String base, String before, String after )
	{
		if( base == null )
			return base;
		int pos = base.lastIndexOf(before);
		if( pos < 0 )
			return base;
		int befLen = before.length();
		StringBuffer sb = new StringBuffer( base );
		while( pos >= 0 && (pos = base.lastIndexOf(before, pos)) >= 0 )
		{
			sb.delete(pos,pos + befLen);
			sb.insert(pos, after);
			pos--;
		}
		return sb.toString();
	}
//    ↑↑2006.07.20 lixy カスタマイズ修正↑↑

// ↓↓2006.08.04 HuangJiugen カスタマイズ修正↓↓
	private String editIntegerValue(String s) {
		if (s == null) return "";
		s = mst000401_LogicBean.removeComma(s);
		try {
			s = String.valueOf(Integer.parseInt(s));
		} catch (NumberFormatException e) {}
		return mst000401_LogicBean.addComma(s);
	}
// ↑↑2006.08.04 HuangJiugen カスタマイズ修正↑↑


	public void checkForTransitionToIkkatuUpdate( mst160201_KeepBean Keepb, mst160301_KeepMeisaiBean KeepMeisaib, DataHolder dataHolder, String FirstFocusCtl )throws SQLException,Exception {


//		初期Focus
		Keepb.setFirstFocus(FirstFocusCtl);

		//画面内容を保存
		Keepb.setBumonCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumoncd")).trim() );
		Keepb.setBumonKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_nm")).trim() );
		Keepb.setHinbanCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinbancd")).trim() );
		Keepb.setHinbanKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinban_nm")).trim() );
		Keepb.setRayinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_linecd")).trim() );
		Keepb.setRanyinKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_line_nm")).trim() );
		Keepb.setUnitCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unitcd")).trim() );
		Keepb.setUnitKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unit_nm")).trim() );
		Keepb.setSiiresakisyohinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakisyihincd")).trim() );
		Keepb.setSiiresakisyohinKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakisyihin_nm")).trim() );
		Keepb.setHachuStDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hattyuustdt")).trim() );
		Keepb.setHachuEdDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hattyuueddt")).trim() );
		Keepb.setSabkurasCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sabukurasucd")).trim() );
		Keepb.setSabkurasKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sabukurasu_nm")).trim() );
		Keepb.setTananostNb( editIntegerValue(dataHolder.getParameter("tx_tanawaribangoufm")));
		Keepb.setTananoedNb( editIntegerValue(dataHolder.getParameter("tx_tanawaribangouto")));

		Keepb.setBaisttanka( editIntegerValue(dataHolder.getParameter("tx_baitankavlfm")));
		Keepb.setBaiedtanka( editIntegerValue(dataHolder.getParameter("tx_baitankavlto")));
		mst000301_SelectBean SelectBean = new mst000301_SelectBean();
		Keepb.setSyohinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syouhinkb")).trim());

		if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syouhinkb")).trim().equals("")) {
			Keepb.setSyohinkanjiRn( SelectBean.subCtfVal2("1060", mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syouhinkb")), "0") );
		} else {
			Keepb.setSyohinkanjiRn("");
		}
		Keepb.setByNo( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_byno")).trim() );
		Keepb.setHinsyuCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyucd")).trim() );
		Keepb.setHinsyuKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyu_nm")).trim() );
		Keepb.setSiiresakiCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresakicd")).trim() );
		Keepb.setSiiresakiKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_nm")).trim() );
		Keepb.setJanMarkCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_janmarkcd")).trim() );
		Keepb.setJanMarkKanjiRn( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_janmark_nm")).trim() );
		Keepb.setHanbaiStDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanbaistdt")).trim() );
		Keepb.setHanbaiEdDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanbaieddt")).trim() );
		Keepb.setSeasonCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_seasoncd")).trim() );
//		ADD by Tanigawa 2006/11/16 障害票№XXXX対応  START
		Keepb.setScrollPos(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_scrollPos")));
//		ADD by Tanigawa 2006/11/16 障害票№XXXX対応   END

//		int cnt = 0;
//		int iCountCheckNum = 0;
//
//		for( int i = (Integer.parseInt(KeepMeisaib.getStartRowInPage()) - 1); i < Integer.parseInt(KeepMeisaib.getEndRowInPage()); i++ ){
//			mst160101_SyohinIkkatuMenteBean arrMeisai = (mst160101_SyohinIkkatuMenteBean)KeepMeisaib.getMeisai().get(i);
//			arrMeisai.setSentakuFg( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sentaku"+Integer.toString(i))).trim() );
//			if( mst000401_LogicBean.chkNullString(arrMeisai.getSentakuFg()).equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){
//				iCountCheckNum = iCountCheckNum + 1;
//			}
//		}
//		KeepMeisaib.setPageMode( mst000401_LogicBean.chkNullString(dataHolder.getParameter("movePageMode")).trim() );
//		Keepb.setProcNum(iCountCheckNum);	//チェック件数(処理対象件数)をセット

		stcLog.getLog().info("チェックボックスの件数確認処理 start ");
//		ArrayList Meisai = new ArrayList();
		int cnt = 0;
		int iCountCheckNum = 0;
		for (int i = 0;i < KeepMeisaib.getMeisai().size(); i++) {
			mst160101_SyohinIkkatuMenteBean arrMeisai = (mst160101_SyohinIkkatuMenteBean)KeepMeisaib.getMeisai().get(i);
			if(i >= Integer.parseInt(KeepMeisaib.getStartRowInPage()) - 1
			 &&  i < Integer.parseInt(KeepMeisaib.getEndRowInPage())){
				arrMeisai.setSentakuFg( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sentaku"+Integer.toString(cnt))).trim() );

				cnt++;
			 }
//			 ADD by Tanigawa 2006/9/28 障害票№0086対応 チェック件数を画面情報保持Beanにセットする START
			if( mst000401_LogicBean.chkNullString(arrMeisai.getSentakuFg()).equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){
				iCountCheckNum = iCountCheckNum + 1;
			}
//			 ADD by Tanigawa 2006/9/28 障害票№0086対応 チェック件数を画面情報保持Beanにセットする  END
//			Meisai.add( arrMeisai );
		}
		stcLog.getLog().info("チェックボックスの件数確認処理  end  ");
//		KeepMeisaib.setMeisai(Meisai);
		KeepMeisaib.setPageMode( mst000401_LogicBean.chkNullString(dataHolder.getParameter("movePageMode")).trim() );

//		 ADD by Tanigawa 2006/9/28 障害票№0086対応 チェック件数を画面情報保持Beanにセットする START
		Keepb.setProcNum(iCountCheckNum);	//チェック件数(処理対象件数)をセット
//		 ADD by Tanigawa 2006/9/28 障害票№0086対応   END
	}


	/**
	 * 一括修正データの登録に成功時/失敗時、改ページ時、画面上に"表示されている"チェックボックス数を総チェック件数からマイナスする。
	 * マイナスしなければ、画面側のJavaScriptでチェックボックスをダブルカウントすることになり、
	 * 本日の処理件数チェック処理が正常に動作しないため。
	 * チェックされている"状態"は保持します。
	 */
	public void rollbackCheckBoxSelectCount(mst160201_KeepBean Keepb, mst160301_KeepMeisaiBean KeepMeisaib){
		int iCountCheckNum = 0;
		for (int i = (Integer.parseInt(KeepMeisaib.getStartRowInPage()) - 1);i < Integer.parseInt(KeepMeisaib.getEndRowInPage()); i++) {
			mst160101_SyohinIkkatuMenteBean arrMeisai = (mst160101_SyohinIkkatuMenteBean)KeepMeisaib.getMeisai().get(i);
			if( mst000401_LogicBean.chkNullString(arrMeisai.getSentakuFg()).equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){
				iCountCheckNum++;
			}
		}
		Keepb.setProcNum( (Keepb.getProcNum() - iCountCheckNum) );	//チェック件数(処理対象件数)をセット
	}

}
