/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/04)初版作成
 *           1.1(2005/06/06)04コード対応
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
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.portal.bean.MdwareUserSessionBean;



/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst110301_CheckBean
{
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param		mst000101_DbmsBean 	db					: DBインスタンス作成
	 * @param		mst110201_KeepBean 	mst110201_KeepBean 	: 画面情報
	 * @param 		DataHolder 	d	ataHolder
	 * @param		String			kengenCd		: 権限コード
	 * @param		String 			GamenId 		: 画面ID
	 * @param		String 			FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		String[] 		CtrlName 		: コントロール名
	 * @param		String			tableNa			: 対象テーブル名称
	 * @param		String			columnNa		: 有効日カラム名称
	 * @param		Map				whereList		: 有効日を除くKEY部
	 * @param		String			addValue		: 暦日加算値
	 * @param		SessionManager  sessionManager	: SessionManager
	 */
	public void Check(
		mst000101_DbmsBean db,
		mst110201_KeepBean Keepb ,
		DataHolder dataHolder ,
//		String kengenCd ,
		String GamenId ,
		String FirstFocusCtl ,
		String[] CtrlName ,
		Map CtrlColor ,
		String tableNa ,
//		String columnNa ,
		List whereList ,
		SessionManager  sessionManager ) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		//マスタ日付
		String MSTDATE = RMSTDATEUtil.getMstDateDt();

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");
		Keepb.setSystemKb(String.valueOf(sessionManager.getSession().getAttribute("mdwareSystemKb")));

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){

			if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT) ){
				//■画面内容を保存
//				↓↓2006.06.26 zhujl カスタマイズ修正↓↓
				//販区コード
//				Keepb.setHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")));
//				//販区名称
//				Keepb.setHankuKanjiRn(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankuna"))));

				//部門コード
				Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")));
				//部門名称
				Keepb.setBumonKanjiRn(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumonna"))));
//		    	↑↑2006.06.26 zhujl カスタマイズ修正↑↑
				//商品コード
				Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")));
				//商品名称
				Keepb.setHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanjina")));			//漢字品名
				//有効日
				Keepb.setYukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yukodt")));
				//処理区分
				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim() );
				Keepb.setChangeFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_changeflg")).trim());

				//■画面内容を保存(BODY)
				//品種コード
//				Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyucd")));
				//品種名称
//				Keepb.setHinsyuNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyuna"))));
				//単品コード
//				Keepb.setTanpinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tanpincd")));
				//マークコード
//				Keepb.setMarkCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_markcd")));
				//マークコード名称
				Keepb.setMarkNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_markna"))));
//				↓↓2006.06.26 zhujl カスタマイズ修正↓↓
				//商品コード２
//				Keepb.setSyohin2Cd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin2cd")));
//				//商品コード２の値がない場合'21'を設定する
//				if (Keepb.getSyohin2Cd().equals("") && Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)) {
//					Keepb.setSyohin2Cd(mst000101_ConstDictionary.INSTORE_21);
//				}
//		    	↑↑2006.06.26 zhujl カスタマイズ修正↑↑
				//仕入先コード
				Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")));
				//仕入先名称
				Keepb.setSiiresakiNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakirn"))));
				//店別仕入先管理コード
				Keepb.setTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanricd")));
				//店別仕入先管理名称
				Keepb.setTenSiiresakiKanriNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanrina"))));
				//代表配送先コード
//				Keepb.setDaihyoHaisoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisocd")));
				//代表配送先名称
				Keepb.setDaihyoHaisoNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisona"))));

				Keepb.setHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanjina")));			//漢字品名
//				Keepb.setKikakuKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanjina")));			//漢字規格
				Keepb.setHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanana")));			//カナ品名
//				Keepb.setKikakuKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanana")));			//カナ規格
				Keepb.setColorCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_colorcd")));						//カラーコード
				Keepb.setSizeCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sizecd")));						//サイズコード
				Keepb.setSiireHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siirehinbancd")));			//仕入先品番

				Keepb.setGentankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankavl")));				//原価単価
//				Keepb.setGentankaSenVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankasenvl")));			//原価単価(少数部)
				Keepb.setBaitankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitankavl")));				//売価単価(税込)
//				Keepb.setMakerKiboKakakuVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makerkibokakakuvl")));	//メーカー希望小売り価格(税込み)
//				Keepb.setHenpinNb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_henpinnb")));					//返品契約書NO
//				Keepb.setHenpinGenkaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_henpingenkavl")));			//返品原価
//				Keepb.setRebateFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rebatefg")));					//リベート対象フラグ

				Keepb.setHanbaiStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaistdt")));				//販売開始日
				Keepb.setHanbaiEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaieddt")));				//販売終了日
				Keepb.setTenHachuStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachustdt")));			//店舗発注開始日
				Keepb.setTenHachuEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachueddt")));			//店舗発注終了日

//				Keepb.setHanbaiSeisakuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaiseisakukb")));		//販売政策区分
//				Keepb.setShinazoroeKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_shinazoroekb")));			//品揃え区分
				Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));							//EOS区分
//				Keepb.setHachutaniIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutaniirisuqt")));	//発注単位(入数)
//				↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
				Keepb.setHachutaniIrisuQt("1");	//発注単位(入数)
//				↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
//				Keepb.setMaxHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_maxhachutaniqt")));		//最大発注単位
//				Keepb.setHachuTeisiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachuteisikb")));			//発注停止区分
//				Keepb.setTenZaikoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenzaikokb")));				//店在庫区分
//				Keepb.setButuryuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_buturyukb")));					//物流区分
				Keepb.setSeasonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_seasoncd")));					//シーズンコード
//				Keepb.setBlandCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandcd")));						//ブランドコード
//				Keepb.setBlandNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandna"))));//ブランド名
//				Keepb.setSceneCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_scenecd")));						//シーンコード
//				Keepb.setSexCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sexcd")));							//性別コード
				Keepb.setAgeCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_agecd")));							//年代コード
//				Keepb.setHanbaiLimitQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitqt")));			//販売期限
//				Keepb.setHanbaiLimitKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitkb")));			//販売期限区分
//				Keepb.setKumisuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kumisukb")));					//組数区分
				Keepb.setNefudaKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nefudakb")));					//値札区分
//				Keepb.setTagHakoQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_taghakoqt")));					//タグ発行枚数
//				Keepb.setTagHyojiBaikaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_taghyojibaikavl")));		//タグ表示売価
//				Keepb.setPcKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pckb")));							//PC発行区分
//				Keepb.setPluSendDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_plusenddt")));
//				Keepb.setRecHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rechinmeikanjina")));	//POSレシート品名(漢字)
//				Keepb.setRecHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rechinmeikanana")));		//POSレシート品名(カナ)

				Keepb.setUnitCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitcd")));
				Keepb.setHaifuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_haifucd")));
				Keepb.setSubclassCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_subclasscd")));
//				if (!Keepb.getUpdateProcessFlg().equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)) {
					Keepb.setSyokaiUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_userid")));
//				}
				Keepb.setSodeCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sodecd")));
				Keepb.setKeshiBaikaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_keshibaikavl")));
//				Keepb.setTokubetuGenkaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tokubetugenkavl")));
				Keepb.setSyokaiKeiyakuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syokaikeiyakuqt")));
				Keepb.setKonkaiTuikeiyakuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_konkaituikeiyakuqt")));
				Keepb.setFujiSyohinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_fujisyohinkb")));
				Keepb.setPbKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pbkb")));
				Keepb.setYoridoriVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yoridorivl")));
				Keepb.setYoridoriQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yoridoriqt")));
				Keepb.setYoridoriKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yoridorikb")));
				Keepb.setNefudaUkewatasiDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nefudaukewatasidt")));
				Keepb.setNefudaUkewatasiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nefudaukewatasikb")));
				Keepb.setSozaiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sozaicd")));
				Keepb.setOriamiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_oriamicd")));
				Keepb.setPatternCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_patterncd")));
//				↑↑2006.06.26 zhujl カスタマイズ修正↑↑
			}

			Keepb.setMode(mst000401_LogicBean.chkNullString(dataHolder.getParameter("mode")));
			Keepb.setExistFlg("");	//データ存在(検索[ｷｬﾝｾﾙ]時)
			Keepb.setErrorBackDisp("");

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			// userSession取得
//			↓↓2006.06.26 zhujl カスタマイズ修正↓↓
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();
//			// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");

			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();
			// ログインユーザー情報
			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");

			//メーニューバーアイコン取得
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);

//			↑↑2006.06.26 zhujl カスタマイズ修正↑↑

			//エラーチェック
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				String name = "";				//戻り値格納
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				String CtlName = "";
				String chkDeg = "";
				Keepb.setTempHankuCd("");
				//検索条件処理チェック　/////////////////////////////////////////////////////////////////////

//				↓↓仕様変更（2005.08.19）↓↓
				//即時伝発対応
//				if ((Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
//					||	Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE))
//					&&	Keepb.getYukoDt().equals(MSTDATE)) {
//
//					List wlst = new ArrayList();
//					StringBuffer sb = new StringBuffer();
//					ResultSet rst = null;
//					String genYukoDt = "";
//
//					wlst.add("syohin_cd = '" + Keepb.getSyohinCd() + "' ");
////					↓↓2006.06.26 zhujl カスタマイズ修正↓↓
////					if (Keepb.getHankuCd() != null && Keepb.getHankuCd().length() > 0){
//					if (Keepb.getBumonCd() != null && Keepb.getBumonCd().length() > 0){
//						wlst.add(" AND ");
////						wlst.add("hanku_cd = '" + Keepb.getHankuCd() + "' ");
//						wlst.add("bumon_cd = '" + Keepb.getBumonCd() + "' ");
////						↑↑2006.06.26 zhujl カスタマイズ修正↑↑
//					}
//					//現在有効データの有効日を取得する
//					genYukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db,"r_syohin","yuko_dt",wlst,"0",true);
//
//					//新規登録日を取得する
//					sb.append(" SELECT ");
//					sb.append("	  sinki_toroku_dt ");
//					sb.append(" FROM ");
//					sb.append("	  r_syohin ");
//					sb.append(" WHERE ");
//					for (int i=0; i < wlst.size(); i++) {
//						sb.append(wlst.get(i));
//					}
//					sb.append(" AND ");
//					sb.append(" yuko_dt = '" + genYukoDt + "' ");
//
//					rst = db.executeQuery(sb.toString());
//					if (rst.next()) {
//						if (!genYukoDt.equals(mst000401_LogicBean.chkNullString(rst.getString("sinki_toroku_dt")))) {
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_yukodt");
//							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage("有効日が今日なので、即時伝発対象商品以外の修正・削除はできません。");
//								Keepb.setFirstFocus("ct_yukodt");
//							}
//						}
//					}
//					if (rst != null) {
//						rst.close();
//					}
//				}
//				↑↑仕様変更（2005.08.19）↑↑

//				↓↓2006.06.26 zhujl カスタマイズ修正↓↓
				//販区コードの存在チェック
//				if(!Keepb.getHankuCd().equals("")){
//					HankuCheck( db, Keepb, CtrlColor, sessionManager, map, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//				} else {
//					Keepb.setHankuKanjiRn("");
//				}

				// 検索の場合
				if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){
					//部門コードの存在チェック
					if(!Keepb.getBumonCd().equals("")){
						BumonCheck( db, Keepb, CtrlColor, sessionManager, map );
					} else {
						Keepb.setBumonKanjiRn("");
					}

					// 商品コードのチェック
					CtlName = "ct_syohincd";

					if (!Keepb.getSyohinCd().equals("")) {
//						↓↓ 新規の場合 別部門に商品コードが登録されていないかのチェック add by kema 06.11.03
						if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ){
							if(!SyohinBumonCheck( db, Keepb )){
								mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus(CtlName);
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("指定された商品コードは別部門に既に登録されています。");
								}
							}
						}
//						↑↑ by kema 06.11.03
						// チェックデジットエラー
						if (!Keepb.getSyohinCd().substring(7,8).equals(mst001401_CheckDegitBean.getModulus9(Keepb.getSyohinCd().substring(0,7),7))){
							mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus( CtlName );
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("40201").toString());
							}
						}
						mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getBumonCd(), "", "hinmei_kanji_na", Keepb.getYukoDt(),Keepb.getProcessingDivision());

						// 新規以外の場合
						if (!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
							if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
								//商品コード存在(有)エラー
								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus( CtlName );
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
								}
							}
						// 新規の場合
						} else {
							if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
								//商品コード存在(有)エラー
								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus( CtlName );
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("指定された商品コード" + map.get("40001").toString());
								}
							}
						}
					}
				// 登録の場合
				} else {

					// 商品コードのチェック
					if (!Keepb.getSyohinCd().equals("")) {

						// チェックデジットエラー
						if (!Keepb.getSyohinCd().substring(7,8).equals(mst001401_CheckDegitBean.getModulus9(Keepb.getSyohinCd().substring(0,7),7))) {
							mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
							Keepb.setFirstFocus( CtlName );
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40201").toString());
						}

						mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getBumonCd(), "", "hinmei_kanji_na", Keepb.getYukoDt(),Keepb.getProcessingDivision());

						// 新規以外の場合
						if (!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
							if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
								//商品コード存在(無)エラー
								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus( CtlName );
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
								}
							}
						// 新規の場合
						} else {
							if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
								//商品コード存在(有)エラー
								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus( CtlName );
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("指定された商品コード" + "40001").toString());
								}
							}
						}
					}
				}

				// 有効日範囲チェック(マスタ日付より1年後まで入力可能)
				// 照会時はチェックを行わない
				String[] strReturn = new String[3];
				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
//					↓↓仕様変更（2005.08.19）↓↓
//					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
//					strReturn = mst000401_LogicBean.getYukoDtRangeCheckSyohin(db, Keepb.getYukoDt());
//					↑↑仕様変更（2005.08.19）↑↑
					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("ct_yukodt");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_yukodt");
						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
					}
				}

				//処理状況：【修正・削除】
				if( (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
					|| Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE))
						&& Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL) ){
					whereList.clear();
//					whereList.add(" hanku_cd = '" + Keepb.getHankuCd() + "' ");
					whereList.add(" bumon_cd = '" + Keepb.getBumonCd() + "' ");
//					whereList.add(" AND syohin_cd Like '" + Keepb.getSyohinCd() + "%' ");
					whereList.add(" AND syohin_cd = '" + Keepb.getSyohinCd() + "' ");
//					whereList.add(" AND gyosyu_kb = '" + Keepb.getGyosyuKb() + "' ");

					//有効日チェック
					String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck(tableNa,Keepb.getYukoDt(),Keepb,db,"0",false);
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
							}
								else if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
											   Keepb.setErrorMessage( map.get("0011").toString() );
									   }
						}
					} else if ( chks.equals(mst000101_ConstDictionary.ERR_CHK_ERROR) ){
						for ( int i = 0;i<CtrlName.length;i++ ){
							mst000401_LogicBean.setErrorBackColor(CtrlColor,CtrlName[i]);
						}
					}
				}
				//検索条件処理チェック   END ////////////////////////////////////////////////////////////////
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
					Keepb.setErrorBackDisp(mst000101_ConstDictionary.ERR_CHK_ERROR);
				}

				if( Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) ){
					//更新処理処理チェック   //////////////////////
					UpdateCheck( db, Keepb, CtrlColor, sessionManager, map );
				}
			}
		}
	}


	/**
	 * 部門のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst310201_KeepBean mst310201_KeepBean 	: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */

	public void  BumonCheck( mst000101_DbmsBean db, mst110201_KeepBean Keepb, Map CtrlColor,
								 SessionManager  sessionManager, TreeMap map) throws Exception,SQLException {

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		String CtlName = "ct_bumoncd";

		List lst = new ArrayList();	//マスタ存在チェック抽出条件
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

		lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
		lst.add(" and ");
		lst.add("CODE_CD = '" + StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true)  + "' ");
		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst,mst000101_ConstDictionary.FUNCTION_PARAM_0,
														mst000401_LogicBean.chkNullString(Keepb.getYukoDt()));
		Keepb.setBumonKanjiRn(mstchk.getCdName());
		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				//部門コード存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);

				Keepb.setFirstFocus(CtlName);
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
			}
		}
	}
//	↑↑2006.06.26 zhujl カスタマイズ修正↑↑
// ↓↓別部門に商品コードが登録されていないかのチェック add by kema 06.11.03
	public boolean SyohinBumonCheck( mst000101_DbmsBean db, mst110201_KeepBean Keepb) throws Exception,SQLException {
		List lst = new ArrayList();	//マスタ存在チェック抽出条件

		String setBumonCd = "";
		setBumonCd = StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true);

		StringBuffer sb = new StringBuffer();
		ResultSet rest = null;
		sb.append("select ");
		sb.append("	syohin_cd ");
		sb.append("from ");
		sb.append("R_SYOHIN ");
		sb.append("where ");
		sb.append("BUMON_CD != '" + setBumonCd  + "' ");
		sb.append(" and ");
		sb.append("SYOHIN_CD = '" + Keepb.getSyohinCd()  + "' ");
		sb.append(" and ");
		sb.append("DELETE_FG = '0' ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		rest = db.executeQuery(sb.toString());
		if(rest.next()){
			return false;
		}
		if(rest != null){
			rest.close();
		}
		return true;
	}
// ↑↑別部門に商品コードが登録されていないかのチェック add by kema 06.11.03

//	↓↓2006.06.26 zhujl カスタマイズ修正↓↓
	/**
	 * 指定商品マスタの取得を行う。
	 * @param		mst000101_DbmsBean db		: DBインスタンス
	 * @param		String syohinCd				: 商品コード
	 * @param		String bumonCd				: 部門コード
	 * @param		String gyosyuKbr 			: 業種区分
	 * @param		String columnNa				: 取得カラム名
	 * @param		String yukoDt				: 有効日
	 * @return		mst000701_MasterInfoBean 	: 取得結果
	 */
//	public mst000701_MasterInfoBean getSyohin( mst000101_DbmsBean db, String syohinCd, String hankuCd, String gyosyuKb,
	public mst000701_MasterInfoBean getSyohin( mst000101_DbmsBean db, String syohinCd, String bumonCd, String gyosyuKb,
	String columnNa, String yukoDt, String syoriKb)
	 throws Exception,SQLException {

		mst110101_SyohinDM 	 	 dm  = new mst110101_SyohinDM();

		mst000701_MasterInfoBean ret = new mst000701_MasterInfoBean();
		ret.setCdName("");
		ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);


		Map selectMap  = new HashMap();
		selectMap.put("syohin_cd", syohinCd);
		selectMap.put("bumon_cd", StringUtility.charFormat(bumonCd,4,"0",true));
		if (!gyosyuKb.equals("")) {
			selectMap.put("gyosyu_kb", gyosyuKb);
		}
		selectMap.put("yuko_dt", yukoDt);

		String MSTDATE = null;
		ResultSet rset = null;    //抽出結果(ResultSet)
//		↓↓2006.10.19 H.Yamamoto レスポンス対策修正↓↓
//		rset = db.executeQuery(dm.getSelectSql(selectMap));
		rset = db.executeQuery(dm.getSyohinCheckSql(selectMap));
//		↑↑2006.10.19 H.Yamamoto レスポンス対策修正↑↑
		if(rset.next()){
			ret.setCdName(mst000401_LogicBean.chkNullString(rset.getString(columnNa)).trim());
			ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
		}
		else {
			if(!mst000401_LogicBean.chkNullString(yukoDt).equals("")){
				MSTDATE = yukoDt;
			}
			//商品(現在有効)の存在チェック
			List whereList = new ArrayList();
			whereList.clear();
			whereList.add("  syohin_cd = '"+ syohinCd +"' ");
			whereList.add(" AND bumon_cd = '"+ StringUtility.charFormat(bumonCd,4,"0",true) +"' ");
			if (!gyosyuKb.equals("")) {
				whereList.add(" AND gyosyu_kb = '"+ gyosyuKb +"' ");
			}
//// 2005.11.29 Y.Inaba 処理区分により処理を分岐 START
			//現在有効日を取得
			if(syoriKb.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)
				  && (yukoDt != null) && (!yukoDt.trim().equals(""))){
				MSTDATE = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, "r_syohin","yuko_dt",whereList, MSTDATE, false);
			} else{
				MSTDATE = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, "r_syohin","yuko_dt",whereList, MSTDATE, true);
			}
//// 2005.11.29 Y.Inaba 処理区分により処理を分岐 END
			if(!MSTDATE.trim().equals("")){
				selectMap.put("yuko_dt",MSTDATE);
				ResultSet rset1 = null;    //抽出結果(ResultSet)
//				↓↓2006.10.18 H.Yamamoto レスポンス対策修正↓↓
//				rset1 = db.executeQuery(dm.getSelectSql(selectMap));
				rset1 = db.executeQuery(dm.getSyohinCheckSql(selectMap));
//				↑↑2006.10.18 H.Yamamoto レスポンス対策修正↑↑
				if(rset1.next()){
					ret.setCdName(mst000401_LogicBean.chkNullString(rset1.getString(columnNa)).trim());
					ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
				}
			}
		}
		return ret;
	}
//	↑↑2006.06.26 zhujl カスタマイズ修正↑↑

	/**
	 *
	 * 新規/修正時の入力項目のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst310201_KeepBean mst310201_KeepBean 	: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */
	public void  UpdateCheck( mst000101_DbmsBean db, mst110201_KeepBean Keepb, Map CtrlColor,
							 SessionManager  sessionManager, TreeMap map ) throws Exception,SQLException {
		List lst = new ArrayList();	//マスタ存在チェック抽出条件

		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
		String CtlName = "";
//		String setHankuCd = "";
		String setBumonCd = "";
//		if(Keepb.getHankuCd().equals("")){
		setBumonCd = StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true);
//		}


		// ↓↓別部門に商品コードが登録されていないかのチェック add by kema 06.11.03
		StringBuffer sb = new StringBuffer();
		ResultSet rest = null;
		sb.append("select ");
		sb.append("	syohin_cd ");
		sb.append("from ");
		sb.append("R_SYOHIN ");
		sb.append("where ");
		sb.append("BUMON_CD != '" + setBumonCd  + "' ");
		sb.append(" and ");
		sb.append("SYOHIN_CD = '" + Keepb.getSyohinCd()  + "' ");
		sb.append(" and ");
		sb.append("DELETE_FG = '0' ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		rest = db.executeQuery(sb.toString());
		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			if(rest.next()){
				// 存在するエラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("商品コードは別部門に既に登録されています。");
				}
			}
		}
		if(rest != null){
			rest.close();
		}
		// ↑↑別部門に商品コードが登録されていないかのチェック add by kema 06.11.03

		// 部門・ユニットコード
		CtlName = "ct_unitcd";
		sb = new StringBuffer();
		rest = null;
		sb.append("select ");
		sb.append("	* ");
		sb.append("from ");
		sb.append("R_SYOHIN_TAIKEI ");
		sb.append("where ");
		sb.append("SYSTEM_KB = '" + sessionManager.getSession().getAttribute("mdwareSystemKb")  + "' ");
		sb.append("and ");
		sb.append("UNIT_CD = '" + Keepb.getUnitCd()  + "' ");
		sb.append(" and ");
		sb.append("BUMON_CD = '" + setBumonCd  + "' ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑


		rest = db.executeQuery(sb.toString());
		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			if(rest.next()){

			} else {
				// 部門コード存在しないエラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("商品体系マスタの部門・ユニットのコードが不正です。");
				}
			}
		}
		if(rest != null){
			rest.close();
		}
//		　ユニットコード
		sb = new StringBuffer();
		rest = null;
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		// 〔ユニット名称〕取得
		CtlName = "ct_unitcd";
		if(!Keepb.getUnitCd().equals("")){
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
			lst.add(" and ");
			lst.add("code_cd = '" + sessionManager.getSession().getAttribute("mdwareSystemKb") + Keepb.getUnitCd() + "' ");

			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0,
															mst000401_LogicBean.chkNullString(Keepb.getYukoDt()) );
			Keepb.setUnitNa( mstchk.getCdName() );
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//コード存在エラー
					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたユニットコード"+map.get("40007").toString());
					}
				}
			}
		} else {
			Keepb.setUnitNa("");
		}

		// 〔サブクラス名称〕チェック 削除 by kema 06.09.21
		CtlName = "ct_subclasscd";
		if(!Keepb.getSubclassCd().equals("")){
			lst.clear();
			lst.add("bumon_cd = '" + setBumonCd+ "' ");
			lst.add(" and ");
			lst.add("subclass_cd = '" + Keepb.getSubclassCd() + "' ");

			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_subclass","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0,
															mst000401_LogicBean.chkNullString(Keepb.getYukoDt()) );
			Keepb.setSubclassNa( mstchk.getCdName() );
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
/*					//コード存在エラー
					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたサブクラスコード"+map.get("40007").toString());
					} */
					Keepb.setSubclassNa("");
				}
			}
		} else {
			Keepb.setSubclassNa("");
		}
		// バイヤーNo
		CtlName = "ct_userid";

		sb = new StringBuffer();
		sb.append(" select ");
//		sb.append(" RIYO_USER_ID ");
		sb.append(" USER_ID AS RIYO_USER_ID ");
		sb.append(" from ");
//		sb.append(" R_RIYO_USER ");
		sb.append(" R_PORTAL_USER ");
		sb.append(" where ");
//		sb.append(" RIYO_USER_ID = '" + "0000" + Keepb.getSyokaiUserId()  + "' ");
		sb.append(" USER_ID = '" + "0000" + Keepb.getSyokaiUserId()  + "' ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		rest = null;

		rest = db.executeQuery(sb.toString());

		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			if(rest.next()){
			} else {
				// バイヤーNo存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定されたバイヤーNo"+map.get("40007").toString());
				}
			}
		}
		if(rest != null){
			rest.close();
		}

		//〔サイズコード〕
		CtlName = "ct_sizecd";
		if(!Keepb.getSizeCd().equals("")){
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal) + "' ");
			lst.add(" and ");
			lst.add( " code_cd = '" + Keepb.getHinsyuCd() + Keepb.getSizeCd() + "' " );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0,
															mst000401_LogicBean.chkNullString(Keepb.getYukoDt()) );
			Keepb.setSizeNa( mstchk.getCdName() );
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//コード存在エラー
					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたサイズコード"+map.get("40007").toString());
					}
				}
			}
		} else {
			Keepb.setSizeNa("");
		}

		// カラー
		CtlName = "ct_colorcd";
		if (!Keepb.getColorCd().equals("")) {
			sb = new StringBuffer();
			sb.append(" select ");
			sb.append(" KANJI_RN ");
			sb.append(" from ");
			sb.append(" R_NAMECTF ");
			sb.append(" where ");
			sb.append("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal) + "' ");
			sb.append(" and ");
			sb.append("code_cd = '" + Keepb.getColorCd() + "' ");
//			↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
			sb.append(" for read only ");
//			↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

			rest = null;

			rest = db.executeQuery(sb.toString());

			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(rest.next()){
					Keepb.setColorNa( rest.getString("KANJI_RN") );
				}else{
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたカラーコード"+map.get("40007").toString());
					}
				}
			}
			if(rest != null){
				rest.close();
			}
		} else {
			Keepb.setColorNa("");
		}

		if( !Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)){
			// 契約数量と追加契約数量
			sb = new StringBuffer();
			sb.append(" select ");
			sb.append(" SYOKAI_KEIYAKU_QT, ");
			sb.append(" KONKAI_TUIKEIYAKU_QT ");
			sb.append(" from ");
			sb.append(" R_KEIYAKUSU ");
			sb.append(" where ");
			sb.append(" BUMON_CD = '" + StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true)  + "' ");
			sb.append(" and ");
			sb.append(" SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
//			↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
			sb.append(" for read only ");
//			↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

			rest = null;

			rest = db.executeQuery(sb.toString());

			if(rest.next()){
				Keepb.setSyokaiKeiyakuQt(rest.getString("SYOKAI_KEIYAKU_QT"));
				if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) {
					Keepb.setKonkaiTuikeiyakuQt(rest.getString("KONKAI_TUIKEIYAKU_QT"));
				}
			}

			if(rest != null){
				rest.close();
			}
		}

		//〔仕入先コード〕　システム区分チェック追加による変更 by kema 06.09.28
		CtlName = "ct_siiresakicd";
		if(!Keepb.getSiiresakiCd().equals("")){
			sb = new StringBuffer();
			sb.append(" select ");
			sb.append(" KANJI_RN ");
//			↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
//			sb.append(" ,SIIRE_SYSTEM_KB ");
//			↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
			sb.append(" from ");
			sb.append(" R_SIIRESAKI ");
			sb.append(" where ");
			sb.append("KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' " );
			sb.append(" and ");
			sb.append("KANRI_CD = '" + "0000" + "' ");
			sb.append(" and ");
			sb.append( "SUBSTRING(SIIRESAKI_CD,1,6) = '" + Keepb.getSiiresakiCd() + "' " );
			sb.append(" and ");
			sb.append(" DELETE_FG = '0' ");
//			↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
//			sb.append(" order by SIIRESAKI_CD ");
			sb.append(" and ");
			sb.append(" SIIRE_SYSTEM_KB in ('2','3') ");
			sb.append(" order by SIIRESAKI_CD desc ");
//			↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
			sb.append(" for read only ");
//			↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

			rest = null;

			rest = db.executeQuery(sb.toString());

			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(rest.next()){
					Keepb.setSiiresakiNa(rest.getString("KANJI_RN"));
//					↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
//					if(rest.getString("SIIRE_SYSTEM_KB") != null && rest.getString("SIIRE_SYSTEM_KB").equals("1")){
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							//仕入先システム区分エラー
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//							Keepb.setFirstFocus(CtlName);
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage("指定された仕入先が不正です。");
//						}
//					}
//					↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
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
//				↓↓2006.10.07 H.Yamamoto カスタマイズ修正↓↓
//				Keepb.setSiiresakiNa("");
				if(rest.next()){
					Keepb.setSiiresakiNa(rest.getString("KANJI_RN"));
				} else {
					Keepb.setSiiresakiNa("");
				}
//				↑↑2006.10.07 H.Yamamoto カスタマイズ修正↑↑
			}
			if(rest != null){
				rest.close();
			}
		}

//		//〔店別仕入先管理コード〕
//		CtlName = "ct_tensiiresakikanricd";
//		if(!Keepb.getTenSiiresakiKanriCd().equals("")){
//			lst.clear();
////			↓↓2006.06.26 zhujl カスタマイズ修正↓↓
////			lst.add( " kanri_kb = '" +  mst000901_KanriKbDictionary.HANKU.getCode() + "' "  );
//			lst.add( " kanri_kb = '" +  mst000901_KanriKbDictionary.BUMON.getCode() + "' "  );
//			lst.add( " and " );
////			lst.add( " kanri_cd = '" + setHankuCd + "' "  );
//			lst.add( " kanri_cd = '" + setBumonCd+ "' "  );
////	    	↑↑2006.06.26 zhujl カスタマイズ修正↑↑
//			lst.add( " and " );
//			lst.add( " tenpo_cd = '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' " );
//			lst.add( " and " );
//			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' " );
//			lst.add( " and " );
//			lst.add( " tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' " );
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db, "vw_r_tensiiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
//			mstchk = mst000401_LogicBean.getMasterCdName(db, "vw_r_tensiiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0,
//															mst000401_LogicBean.chkNullString(Keepb.getYukoDt()));
//// 2006.01.24 Y.Inaba Mod ↑
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//// ↓↓仕様変更（2005.08.31）↓↓
////				if(!mst000401_LogicBean.getMasterChk(db,"vw_r_tensiiresaki", lst  )){
//				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
//// ↑↑仕様変更（2005.08.31）↑↑
//					//コード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された店別仕入先管理コード"+map.get("40007").toString());
//					}
//// ↓↓仕様変更（2005.08.31）↓↓
//				} else {
//					if (!mstchk.getCdName().equals(Keepb.getSiiresakiCd())) {
//						mst000401_LogicBean.setErrorBackColor(CtrlColor, CtlName);
//						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//							Keepb.setFirstFocus(CtlName);
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage("指定された仕入先コードと、店別仕入先管理コードにおける代表店舗の仕入先コードが違います。");
//						}
//					}
//// ↑↑仕様変更（2005.08.31）↑↑
//				}
//			}
			//名称取得
//			lst.clear();
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP + "' ");
//			lst.add(" and ");
//// ↓↓仕様変更（2005.09.02）↓↓
////			lst.add("code_cd = '" + Keepb.getHankuCd() + Keepb.getTenSiiresakiKanriCd() + "' ");
////			↓↓2006.06.26 zhujl カスタマイズ修正↓↓
////			lst.add("code_cd = '" + setHankuCd + Keepb.getTenSiiresakiKanriCd() + "' ");
//			lst.add("code_cd = '" + setBumonCd + Keepb.getTenSiiresakiKanriCd() + "' ");
////	    	↑↑2006.06.26 zhujl カスタマイズ修正↑↑
//
//// ↑↑仕様変更（2005.09.02）↑↑
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0,
//															mst000401_LogicBean.chkNullString(Keepb.getYukoDt()) );
//// 2006.01.24 Y.Inaba Mod ↑
//			Keepb.setTenSiiresakiKanriNa( mstchk.getCdName() );
//
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//仕入先コード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された店別仕入先コード"+map.get("40007").toString());
//					}
//				}
//			}
//
//		} else {
//			Keepb.setTenSiiresakiKanriNa("");
//		}

		//〔代表配送先コード〕
//		CtlName = "ct_daihyohaisocd";
//		StringBuffer sb = new StringBuffer();
//		ResultSet rest = null;
//		if(!Keepb.getDaihyoHaisoCd().equals("")){
//			lst.clear();
//// ↓↓バグ修正(2005.10.18)
////			lst.add(" kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
////			lst.add(" and ");
////			↓↓仕様変更（2005.09.02）↓↓
////			lst.add(" kanri_cd = '" + Keepb.getHankuCd() + "' ");
////			lst.add(" kanri_cd = '" + setHankuCd + "' ");
////			↑↑仕様変更（2005.09.02）↑↑
////			lst.add(" and ");
////			lst.add(" daihyo_haiso_cd = '" + Keepb.getDaihyoHaisoCd() + "' " );
////			lst.add(" and ");
////			lst.add(" haisosaki_cd = '" + Keepb.getDaihyoHaisoCd() + "0'" );
////			lst.add(" and ");
////			lst.add(" tenpo_cd = '" + mst000101_ConstDictionary.ALL_TENPO_CD + "'" );
////			lst.add(" and ");
////			lst.add(" tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"vw_r_tenhaisosaki","KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
////			Keepb.setDaihyoHaisoNa( mstchk.getCdName() );
//
//			sb.append(" select ");
//			sb.append("   rh.kanji_na ");
//			sb.append(" from ");
//			sb.append("   r_tenbetu_haisosaki rth inner join r_haisou rh ");
//			sb.append("     on rth.kanri_kb = rh.kanri_kb and ");
//			sb.append("     rth.kanri_cd = rh.kanri_cd and ");
//			sb.append("     rth.haisosaki_cd = rh.haisosaki_cd ");
//			sb.append(" where ");
//			sb.append("   rth.kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//			sb.append("     and ");
//// ↓↓仕様変更（2005.09.02）↓↓
////			sb.append("   rth.kanri_cd = '" + Keepb.getHankuCd() + "' ");
//			sb.append("   rth.kanri_cd = '" + setHankuCd + "' ");
//// ↑↑仕様変更（2005.09.02）↑↑
//			sb.append("     and ");
//			sb.append("   rth.daihyo_haiso_cd = '" + Keepb.getDaihyoHaisoCd() + "' ");
//			sb.append("     and ");
//			sb.append("   rth.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//			sb.append("     and ");
//			sb.append("   rh.tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' ");
//			sb.append("     and ");
//			sb.append("   rh.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////			↓↓移植（2006.05.15）↓↓
//			sb.append(" order by ");
//			sb.append("   rth.haisosaki_cd ");
//			sb.append("   fetch first 1 rows only");
////			↑↑移植（2006.05.15）↑↑
//// ↑↑バグ修正(2005.10.18)
//			rest = db.executeQuery(sb.toString());
//			if (!rest.next()) {
//				mst000401_LogicBean.setErrorBackColor(CtrlColor, CtlName);
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//					Keepb.setFirstFocus( CtlName );
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					Keepb.setErrorMessage("指定された代表配送先"+map.get("40007").toString());
//				}
//			} else {
//				Keepb.setDaihyoHaisoNa(mst000401_LogicBean.chkNullString(rest.getString("kanji_na")));
//			}
//		} else{
//			Keepb.setDaihyoHaisoNa("");
//		}
//		if (rest != null) {
//			rest.close();
//		}

		//〔ブランドコード〕
		CtlName = "ct_blandcd";
		if(!Keepb.getBlandCd().equals("")){
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BRAND, userLocal) + "' ");
			lst.add(" and ");
// ↓↓ブランドコード仕様変更対応(2005.09.26)
//			lst.add("code_cd = '" + setHankuCd + Keepb.getBlandCd() + "' ");
//			↓↓2006.06.26 zhujl カスタマイズ修正↓↓
//			lst.add("trim(code_cd) = '" + setHankuCd + Keepb.getBlandCd() + "' ");
			lst.add("trim(code_cd) = '" + Keepb.getBlandCd()  + "' ");
//	    	↑↑2006.06.26 zhujl カスタマイズ修正↑↑

// ↑↑ブランドコード仕様変更対応(2005.09.26)
// 2006.01.24 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0,
															mst000401_LogicBean.chkNullString(Keepb.getYukoDt()));
// 2006.01.24 Y.Inaba Mod ↑
			Keepb.setBlandNa( mstchk.getCdName() );
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//コード存在エラー
					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたブランドコード"+map.get("40007").toString());
					}
				}
			}
		} else {
			Keepb.setBlandNa("");
		}

		//〔在庫センターコード〕
//		CtlName = "ct_zaikocentercd";
//		if(!Keepb.getZaikoCenterCd().equals("")){
//			lst.clear();
//			lst.add("tenpo_cd = '" + Keepb.getZaikoCenterCd()+ "' ");
//			lst.add(" and ");
//			lst.add( " tenpo_kb = '" + mst003601_TenpoKbDictionary.BUTURYU_CENTER.getCode() + "' " );
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_tenpo","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_tenpo","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0,
//															mst000401_LogicBean.chkNullString(Keepb.getYukoDt()) );
//// 2006.01.24 Y.Inaba Mod ↑
//			Keepb.setZaikoCenterNa( mstchk.getCdName() );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//コード存在エラー
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された在庫センターコード"+map.get("40007").toString());
//					}
//				}
//			}
//		} else {
//			Keepb.setZaikoCenterNa("");
//		}

		//〔税区分〕
//		CtlName = "ct_hinsyucd";
//		if (!Keepb.getHinsyuCd().equals("")) {
//			lst.clear();
//			lst.add(" syubetu_no_cd = '" + mst000101_ConstDictionary.TAX_FREE + "' ");
//			lst.add(" and ");
//			lst.add(" code_cd = '" + Keepb.getHinsyuCd() + "' ");
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mst000401_LogicBean.getMasterChk(db,"r_namectf", lst  )){
//					Keepb.setZeiKb(mst007501_ZeiKbDictionary.PRETAX.getCode());
//				} else {
//					Keepb.setZeiKb(mst007501_ZeiKbDictionary.TAXFREE.getCode());
//				}
//			}
//		}
	}
}
