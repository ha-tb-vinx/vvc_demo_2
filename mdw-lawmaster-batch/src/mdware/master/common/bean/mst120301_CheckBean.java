/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/04/04)初版作成
 * @version 1.1(2005/05/20)新ER対応 Y.Inaba
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
import mdware.master.util.RMSTDATEUtil;
//↑↑仕様変更（2005.07.28）↑↑

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst120301_CheckBean
{
// ↓↓2006.07.08 HuangJiugen カスタマイズ修正↓↓
	private DataHolder theDh;
	private String getInput(String item) {
		return mst000401_LogicBean.chkNullString(theDh.getParameter(item));
	}
	private String getInputNumWithComma(String item) {
		String s = theDh.getParameter(item);
		if (s == null || s.length() == 0) {
			return "";
		}
		return mst000401_LogicBean.removeComma(s);
	}
// ↑↑2006.07.08 HuangJiugen カスタマイズ修正↑↑

	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
	public void Check(
		mst000101_DbmsBean db,
		mst120201_KeepBean Keepb ,
		DataHolder dataHolder ,
		String kengenCd ,
		String GamenId ,
		String FirstFocusCtl ,
		String[] CtrlName ,
		Map CtrlColor ,
		String tableNa ,
		List whereList ,
		SessionManager  sessionManager ) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT) ){
// ↓↓2006.07.07 HuangJiugen カスタマイズ修正↓↓
				this.theDh = dataHolder;
				// 検索部分の画面内容を保存する
				Keepb.setBumonCd(getInput("ct_bumoncd"));						// 部門コード
				Keepb.setBumonKanjiRn(getInput("ct_bumonnm")); 					// 部門名称
				Keepb.setSyohinCd(getInput("ct_syohincd"));						// 商品コード
				Keepb.setHinmeiKanji(getInput("ct_syohinna"));					// 商品名
				Keepb.setYukoDt(getInput("ct_yukodt"));							// 有効日
				Keepb.setProcessingDivision(getInput("ct_syorikb")); 			// 処理状況
				Keepb.setChangeFlg(getInput("h_changeflg"));					// 変更フラグ

				// 新規と修正の場合、検索条件以外の画面内容を保存する
				if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)
						|| Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
				  	Keepb.setUnitCd(getInput("ct_unitcd"));						// ユニットコード
				  	Keepb.setUnitNm(getInput("ct_unitnm"));						// ユニット名称
				  	Keepb.setHaifuCd(getInput("ct_haifucd"));					// 配布
				  	Keepb.setSubclassNm(getInput("ct_subclassnm"));				// サブクラス名称
				  	Keepb.setSyokaiUserId(getInput("ct_userid"));				// バイヤーNo
				  	Keepb.setSyohin2Cd(getInput("ct_syohin2cd"));				// POSコード
				  	Keepb.setMakerCd(getInput("ct_makercd"));					// ＪＡＮメーカーコード
					Keepb.setHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanjina")));			//漢字品名
				  	Keepb.setKikakuKanjiNa(getInput("ct_kikakukanjina"));		// 漢字規格
				  	Keepb.setKikakuKanji2Na(getInput("ct_kikakukanji2na"));		// 漢字規格２
				  	Keepb.setHinmeiKanaNa(getInput("ct_hinmeikanana"));			// カナ品名
				  	Keepb.setKikakuKanaNa(getInput("ct_kikakukanana"));			// カナ規格
					Keepb.setKikakuKana2Na(getInput("ct_kikakukana2na"));		// カナ規格２
					Keepb.setRecHinmeiKanjiNa(getInput("ct_rechinmeikanjina"));	// 漢字POSレシート品名
					Keepb.setRecHinmeiKanaNa(getInput("ct_rechinmeikanana")); 	// カナPOSレシート品名
					Keepb.setSizeCd(getInput("ct_sizecd"));						// サイズコード
					Keepb.setColorCd(getInput("ct_colorcd")); 					// カラーコード
					Keepb.setGentankaVl(getInputNumWithComma("ct_gentankavl"));	// 原価単価(税込)
					Keepb.setBaitankaVl(getInputNumWithComma("ct_baitankavl"));	// 売上単価(税込)
					Keepb.setKeshiBaikaVl(getInputNumWithComma("ct_keshibaikavl"));				// 消札売価(税込)
					Keepb.setSyokaiKeiyakuQt(getInputNumWithComma("ct_syokaikeiyakuqt"));		// 契約数量
					Keepb.setKonkaituiKeiyakuQt(getInputNumWithComma("ct_konkaituikeiyakuqt"));	// 追加契約数量
					Keepb.setHachutaniIrisuQt(getInputNumWithComma("ct_hachutaniirisuqt"));		// 発注単位（入数）
					Keepb.setHachuTaniNa(getInput("ct_hachutanina"));			// 発注単位呼称
					Keepb.setPluSendDt(getInput("ct_plusenddt"));				// PLU送信日
					Keepb.setSiiresakiCd(getInput("ct_siiresakicd"));			// 仕入先コード
					Keepb.setSiiresakiNm(getInput("ct_siiresakinm"));			// 仕入先名称
					Keepb.setAreaCd(getInput("ct_areacd"));						// 地区コード
					Keepb.setAreacd_Na(getInput("ct_areacdna"));				// 地区名称
					Keepb.setSiireHinbanCd(getInput("ct_siirehinbancd"));		// 仕入先商品コード
					Keepb.setHanbaiStDt(getInput("ct_hanbaistdt"));				// 販売開始日
					Keepb.setHanbaiEdDt(getInput("ct_hanbaieddt"));				// 販売終了日
					Keepb.setTenHachuStDt(getInput("ct_tenhachustdt"));			// 発注開始日
					Keepb.setTenHachuEdDt(getInput("ct_tenhachueddt"));			// 発注終了日
					Keepb.setEosKb(getInput("ct_eoskb"));						// ＥＯＳ区分
					Keepb.setFujiSyohinKb(getInput("ct_fujisyohinkb"));			// 商品区分
					Keepb.setSeasonCd(getInput("ct_seasoncd"));					// シーズン
					Keepb.setBlandCd(getInput("ct_blandcd"));					// ブランドコード
					Keepb.setBlandNa(getInput("ct_blandnm"));					// ブランド名称
					Keepb.setPbKb(getInput("ct_pbkb"));							// ＰＢ区分
					Keepb.setYoridoriVl(getInputNumWithComma("ct_yoridorivl"));	// よリどり価格
					Keepb.setYoridoriQt(getInput("ct_yoridoriqt"));				// よりどり数量
					Keepb.setYoridoriKb(getInput("ct_yoridorikb"));				// よりどり単位
					Keepb.setTanaNoNb(getInputNumWithComma("ct_tananonb"));		// 棚番
					Keepb.setNefudaKb(getInput("ct_nefudakb"));					// POS値札
					Keepb.setNefudaUkewatasiDt(getInput("ct_nefudaukewatasidt"));	// POS値札受渡日
					Keepb.setNefudaUkewatasiKb(getInput("ct_nefudaukewatasikb"));	// POS値札受渡方法
					Keepb.setPcKb(getInput("ct_pckb"));							// プライスシール発行区分
				}
//				//画面内容を保存
//				Keepb.setSubclassCd(getInput("ct_subclasscd"));				// サブクラスコード
//				Keepb.setDaisiNoNb(getInput("ct_daisinonb"));				// プライスシール種類
//				Keepb.setHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")));						//販区コード
//				Keepb.setHankuKanjiRn(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankuna"))));				//販区名
//				Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")));					//商品コード
//				Keepb.setHinmeiKanji(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohinna")));				//商品名
//				Keepb.setYukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yukodt")));						//有効日
//				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim() );
//			  	Keepb.setChangeFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_changeflg")).trim());
//
//				//画面内容を保存(BODY)
//				Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyucd")));					//品種コード
//				Keepb.setHinsyuNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyuna"))));					//品種名
//				Keepb.setTanpinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tanpincd")));					//単品
//
//				Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")));				//仕入先コード
//				Keepb.setSiiresakiNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakirn"))));				//仕入先コード
//				Keepb.setTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanricd")));	//店別仕入先管理コード
//				Keepb.setTenSiiresakiKanriNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanrina"))));	//店別仕入先管理名
//				Keepb.setDaihyoHaisoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisocd")));			//代表配送先コード
//				Keepb.setDaihyoHaisoNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisona"))));			//代表配送先名
//
//				Keepb.setHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanjina")));			//漢字品名
//				Keepb.setKikakuKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanjina")));			//漢字規格
//				Keepb.setHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanana")));			//カナ品名
//				Keepb.setKikakuKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanana")));			//カナ規格
//				Keepb.setColorCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_colorcd")));						//カラーコード
//				Keepb.setSizeCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sizecd")));						//サイズコード
//				Keepb.setSiireHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siirehinbancd")));			//仕入先品番
//
//				Keepb.setGentankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankavl")));				//原価単価
//				Keepb.setGentankaSenVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankasenvl")));			//原価単価(少数部)
//				Keepb.setBaitankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitankavl")));				//売価単価(税込)
//				Keepb.setMakerKiboKakakuVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makerkibokakakuvl")));	//メーカー希望小売り価格(税込み)
//				Keepb.setHenpinNb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_henpinnb")));					//返品契約書NO
//				Keepb.setHenpinGenkaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_henpingenkavl")));			//返品原価
//				Keepb.setRebateFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rebatefg")));					//リベート対象フラグ
//
//				Keepb.setHanbaiStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaistdt")));				//販売開始日
//				Keepb.setHanbaiEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaieddt")));				//販売終了日
//				Keepb.setTenHachuStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachustdt")));			//店舗発注開始日
//				Keepb.setTenHachuEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachueddt")));			//店舗発注終了日
//
//				Keepb.setHanbaiSeisakuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaiseisakukb")));		//販売政策区分
//				Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));							//EOS区分
//				Keepb.setHachutaniIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutaniirisuqt")));	//発注単位(入数)
//				Keepb.setMaxHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_maxhachutaniqt")));		//最大発注単位
//				Keepb.setHachuTeisiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachuteisikb")));			//発注停止区分
//				Keepb.setTenZaikoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenzaikokb")));				//店在庫区分
//				Keepb.setButuryuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_buturyukb")));					//物流区分
//				Keepb.setSeasonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_seasoncd")));					//シーズンコード
//				Keepb.setBlandCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandcd")));						//ブランドコード
//				Keepb.setBlandNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandna"))));						//ブランド名
//
//				Keepb.setSceneCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_scenecd")));						//シーンコード
//				Keepb.setSexCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sexcd")));							//性別コード
//				Keepb.setAgeCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_agecd")));							//年代コード
//
//				Keepb.setShinazoroeKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_shinazoroekb")));			//品揃え区分
//				Keepb.setKumisuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kumisukb")));					//組数区分
//				Keepb.setNefudaKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nefudakb")));					//値札区分
//				Keepb.setTagHyojiBaikaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_taghyojibaikavl")));		//タグ表示売価
//
//				Keepb.setPcKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pckb")));							//PC発行区分

// ↑↑2006.07.08 HuangJiugen カスタマイズ修正↑↑
			}
			Keepb.setMode(mst000401_LogicBean.chkNullString(dataHolder.getParameter("mode"))	);
			Keepb.setExistFlg("");	//データ存在(検索[ｷｬﾝｾﾙ]時)
			Keepb.setErrorBackDisp("");
// ↓↓2006.07.08 HuangJiugen カスタマイズ修正↓↓
//			Map param = new HashMap();		//抽出条件格納用
//			ResultSet rset = null;			//抽出結果(ResultSet)
// ↑↑2006.07.08 HuangJiugen カスタマイズ修正↑↑

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			// userSession取得
// ↓↓2006.07.07 HuangJiugen カスタマイズ修正↓↓
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//			// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
//			//メーニューバーアイコン取得
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);
// ↑↑2006.07.07 HuangJiugen カスタマイズ修正↑↑

			//エラーチェック
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
// ↓↓2006.07.08 HuangJiugen カスタマイズ修正↓↓
//				List lst = new ArrayList();	//マスタ存在チェック抽出条件
//				String name = "";				//戻り値格納
//				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
//				String CtlName = "";
//				String chkDeg = "";
//				Keepb.setTempHankuCd("");
// ↑↑2006.07.08 HuangJiugen カスタマイズ修正↑↑

// ↓↓2006.07.08 HuangJiugen カスタマイズ修正↓↓
				// 部門
				bumonCheck(db, Keepb, CtrlColor, sessionManager, map);

//				↓↓ 新規の場合 別部門に商品コードが登録されていないかのチェック add by kema 06.11.03
				if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
					if(!SyohinBumonCheck( db, Keepb )){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_syohincd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された商品コードは別部門に既に登録されています。");
						}
					}
				}
//				↑↑ by kema 06.11.03
				//商品コード
				syohinCheck(db, Keepb, CtrlColor, sessionManager, map);
// ↑↑2006.07.08 HuangJiugen カスタマイズ修正↑↑
				//検索条件処理チェック
				// 有効日範囲チェック(マスタ日付より1年後まで入力可能)
				// 照会時はチェックを行わない
				String[] strReturn = new String[3];
				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
// ↓↓仕様変更（2005.08.19）↓↓
//					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
					strReturn = mst000401_LogicBean.getYukoDtRangeCheckSyohin(db, Keepb.getYukoDt());
// ↑↑仕様変更（2005.08.19）↑↑
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("ct_yukodt");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_yukodt");
						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
					}
				}
// ↓↓2006.07.19 HuangJiugen カスタマイズ修正↓↓
//// ↓↓仕様変更（2005.08.19）↓↓
//				//即時伝発対応
//				if ((Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
//					||	Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE))
//					&&	Keepb.getYukoDt().equals(RMSTDATEUtil.getMstDateDt())) {
//					List wlst = new ArrayList();
//					StringBuffer sb = new StringBuffer();
//					ResultSet rst = null;
//					String genYukoDt = "";
//
//					wlst.add("syohin_cd = '" + Keepb.getSyohinCd() + "' ");
//					if (Keepb.getHankuCd() != null && Keepb.getHankuCd().length() > 0){
//						wlst.add(" AND ");
//						wlst.add("hanku_cd = '" + Keepb.getHankuCd() + "' ");
//					genYukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db,"r_syohin","yuko_dt",wlst,"0",true);
//
//					sb.append(" SELECT ");
//					sb.append("		sinki_toroku_dt ");
//					sb.append(" FROM ");
//					sb.append("		r_syohin ");
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
//// ↑↑仕様変更（2005.08.19）↑↑
// ↑↑2006.07.19 HuangJiugen カスタマイズ修正↑↑

// ↓↓2006.07.08 HuangJiugen カスタマイズ修正↓↓
//				//販区
//				if(!Keepb.getHankuCd().equals("")){
//					HankuCheck( db, Keepb, CtrlColor, sessionManager, map, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//				} else {
//					Keepb.setHankuKanjiRn("");
//				}
//				CtlName = "ct_syohincd";
//			   	Keepb.setHinmeiKanji( "" );
//				if( !Keepb.getSyohinCd().equals("") ){
//					//処理状況：【新規】
//					if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)){
//						if( Keepb.getSyohinCd().length() >= 4 ){
//							//品種の存在チェック
//							lst.clear();
//							lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' ");
//							lst.add(" and ");
//							lst.add("code_cd = '" + Keepb.getSyohinCd().substring(0,4) + "' ");
//// 2006.01.24 Y.Inaba Mod ↓
////							mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//							mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.01.24 Y.Inaba Mod ↑
//							if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//								//品種コード存在エラー
//								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//									Keepb.setFirstFocus( CtlName );
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									Keepb.setErrorMessage(map.get("0315").toString());
//								}
//							} else {
//								//ログインユーザの売区とのチェック
//								if(!mst000401_LogicBean.getHinshuCdCheck(db,Keepb.getSyohinCd().substring(0,4),sessionManager)){
//										mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//									if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//										Keepb.setFirstFocus(CtlName);
//										Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//										Keepb.setErrorMessage(map.get("40026").toString());
//									}
//								}
//							}
//						}
//						if( Keepb.getSyohinCd().length() >= 7 ){
//							//商品の存在チェック
////							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), Keepb.getGyosyuKb(), "hinmei_kanji_na", Keepb.getYukoDt());
////							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), "", "gyosyu_kb", Keepb.getYukoDt());
//							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), "", "gyosyu_kb", Keepb.getYukoDt(),Keepb.getProcessingDivision());
//
//							if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//								//商品コード存在(有)エラー
//								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//									Keepb.setFirstFocus( CtlName );
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									if(Keepb.getGyosyuKb().equals( mstchk.getCdName().trim())){
//										Keepb.setErrorMessage(map.get("40001").toString());
//									} else {
//										Keepb.setErrorMessage(map.get("40026").toString());
//									}
//								}
//							}
////							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), Keepb.getGyosyuKb(), "hinmei_kanji_na", Keepb.getYukoDt());
//							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), Keepb.getGyosyuKb(), "hinmei_kanji_na", Keepb.getYukoDt(), Keepb.getProcessingDivision());
//							Keepb.setHinmeiKanji( mstchk.getCdName() );
//						}
//						if( Keepb.getSyohinCd().length() >= 4 ){
//							//販区の存在チェック（商品体系マスタ）
//							lst.clear();
//							lst.add("hinsyu_cd = '" + Keepb.getSyohinCd().substring(0,4) + "' ");
//							if(!Keepb.getHankuCd().equals("")){
//								lst.add(" and ");
//								lst.add("hanku_cd = '" + Keepb.getHankuCd() + "' ");
//							}
//							mstchk = mst000401_LogicBean.getSyohinTaikei(db,"hanku_cd", lst );
//							//更新用販区の取得
//							Keepb.setTempHankuCd( mstchk.getCdName() );
//							if(!Keepb.getHankuCd().equals("") && !mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//								//販区存在エラー
//								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//								mst000401_LogicBean.setErrorBackColor( CtrlColor, "ct_hankucd" );
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//									Keepb.setFirstFocus( "ct_hankucd" );
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									Keepb.setErrorMessage("販区コード"+map.get("0200").toString());
//								}
//							}
//						}
//						if( Keepb.getSyohinCd().length() == 8 ){
//							//チェックデジットのチェック
//							chkDeg = mst001401_CheckDegitBean.getModulus11( Keepb.getSyohinCd().substring(0,7) ,7 );
//							if( chkDeg == null || !chkDeg.equals( Keepb.getSyohinCd().substring(7,8) )){
//								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//									Keepb.setFirstFocus( CtlName );
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									Keepb.setErrorMessage(map.get("40201").toString());
//								}
//							}
//						}
//					} else {
//						//処理状況：【新規以外】
//						if( Keepb.getSyohinCd().length() >= 7 && !Keepb.getHankuCd().equals("")){
//
////							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), Keepb.getGyosyuKb(), "hinmei_kanji_na", Keepb.getYukoDt());
////							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), "", "gyosyu_kb", Keepb.getYukoDt());
//							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), "", "gyosyu_kb", Keepb.getYukoDt(), Keepb.getProcessingDivision());
//
//							Keepb.setHinmeiKanji( mstchk.getCdName() );
//							if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//								//商品コード存在エラー
//								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//									Keepb.setFirstFocus( CtlName );
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									Keepb.setErrorMessage(map.get("0005").toString());
//								}
//							} else {
//								if(!Keepb.getGyosyuKb().equals( mstchk.getCdName().trim())){
//									//商品コード存在エラー
//									mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//									if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//										Keepb.setFirstFocus( CtlName );
//										Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//										Keepb.setErrorMessage(map.get("40026").toString());
//									}
//								}
//							}
////							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), Keepb.getGyosyuKb(), "hinmei_kanji_na", Keepb.getYukoDt());
//							mstchk = getSyohin(db, Keepb.getSyohinCd(), Keepb.getHankuCd(), Keepb.getGyosyuKb(), "hinmei_kanji_na", Keepb.getYukoDt(), Keepb.getProcessingDivision());
//							Keepb.setHinmeiKanji( mstchk.getCdName() );
//						}
//						if(Keepb.getHankuCd().equals("")){
//							//商品に対する販区のチェック
//							String syohin_hanku = "";
//							if(Keepb.getSyohinCd().length() < 8){
//								syohin_hanku = mst000401_LogicBean.getLikeSyohinCd2HankuCd(db,Keepb.getSyohinCd(),Keepb.getGyosyuKb());
//							} else {
//								syohin_hanku = mst000401_LogicBean.getSyohinCd2HankuCd(db,Keepb.getSyohinCd(),Keepb.getGyosyuKb());
//							}
//							if(syohin_hanku == null){
//								//複数の販区が存在するエラー
//								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//									Keepb.setFirstFocus( CtlName );
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									Keepb.setErrorMessage("販区コード"+map.get("0021").toString());
//								}
//							}else {
//								Keepb.setHankuCd(syohin_hanku);
//								HankuCheck( db, Keepb, CtrlColor, sessionManager, map, mst000101_ConstDictionary.FUNCTION_PARAM_1 );
//								//決定販区より商品名取得
////								mstchk = getSyohin( db, Keepb.getSyohinCd(), Keepb.getHankuCd(),Keepb.getGyosyuKb(),"hinmei_kanji_na",Keepb.getYukoDt());
////								mstchk = getSyohin( db, Keepb.getSyohinCd(), Keepb.getHankuCd(),Keepb.getGyosyuKb(),"hinmei_kanji_na",Keepb.getYukoDt());
//								mstchk = getSyohin( db, Keepb.getSyohinCd(), Keepb.getHankuCd(),Keepb.getGyosyuKb(),"hinmei_kanji_na",Keepb.getYukoDt(), Keepb.getProcessingDivision());
//								Keepb.setHinmeiKanji( mstchk.getCdName() );
//							}
//						}
//					}
//				}
// ↑↑2006.07.08 HuangJiugen カスタマイズ修正↑↑

				//処理状況：【修正・削除】
				if( (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
					|| Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE))
						&& Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL) ){
//					whereList.clear();
//					whereList.add(" hanku_cd = '" + Keepb.getHankuCd() + "' ");
//					whereList.add(" AND syohin_cd Like '" + Keepb.getSyohinCd() + "%' ");
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
// ↓↓2006.07.10 HuangJiugen カスタマイズ修正↓↓
//						for ( int i = 0;i<CtrlName.length;i++ ){
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,CtrlName[i]);
//						}
// ↑↑2006.07.10 HuangJiugen カスタマイズ修正↑↑
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
// ↓↓2006.07.08 HuangJiugen カスタマイズ修正↓↓
//	/**
//	 * 販区のチェックを行う。
//	 * @param		mst000101_DbmsBean db					: DBインスタンス
//	 * @param		mst120201_KeepBean mst120201_KeepBean 	: 画面情報
//	 * @param		Map 			   CtrlColor			: コントロール表示色
//	 * @param		SessionManager     sessionManager 		: SessionManager
//	 * @param		TreeMap            map					: メッセージ
//	 */
//
//	public void  HankuCheck( mst000101_DbmsBean db, mst120201_KeepBean Keepb, Map CtrlColor,
//								 SessionManager  sessionManager, TreeMap map, String flg ) throws Exception,SQLException {
//
//		String CtlName = "ct_hankucd";
//
//		List lst = new ArrayList();	//マスタ存在チェック抽出条件
//		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
//
//		lst.add("SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");
//		lst.add(" and ");
//		lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
//// 2006.01.24 Y.Inaba Mod ↓
////		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt());
//// 2006.01.24 Y.Inaba Mod ↑
//		Keepb.setHankuKanjiRn(mstchk.getCdName());
//		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				//販区コード存在エラー
//				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					Keepb.setFirstFocus(CtlName);
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					if(flg.equals(mst000101_ConstDictionary.FUNCTION_PARAM_0)){
//						Keepb.setErrorMessage("指定された販区コード"+map.get("40007").toString());
//					} else {
//						Keepb.setErrorMessage("指定された商品コード"+map.get("40007").toString());
//					}
//				}
//			} else {
//				//ログインユーザの売区とのチェック
//				if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						if(flg.equals(mst000101_ConstDictionary.FUNCTION_PARAM_0)){
//							Keepb.setErrorMessage(map.get("40022").toString());
//						} else {
//							Keepb.setErrorMessage("指定された商品コード"+map.get("40007").toString());
//						}
//					}
//				}
//			}
//		}
//	}

	private String getCodeName(mst000101_DbmsBean db, String yukoDt, String syubetuNoCd, String code)
	throws Exception {
		if (code == null) return null;
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		List lst = new ArrayList();	//マスタ存在チェック抽出条件
		lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(syubetuNoCd, userLocal) + "' ");
		lst.add(" and ");
//		↓↓2006.10.20 H.Yamamoto カスタマイズ修正↓↓
//		lst.add("trim(CODE_CD) = '" + code + "' ");
		lst.add("CODE_CD = '" + code + "' ");
//		↑↑2006.10.20 H.Yamamoto カスタマイズ修正↑↑

		mst000701_MasterInfoBean mib = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, yukoDt);

		if(!mst000101_ConstDictionary.FUNCTION_TRUE.equals(mib.getExistenceFlg())){
			return null;
		}
		return mib.getCdName() == null ? "" : mib.getCdName();
	}

	/**
	 * 部門のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst120201_KeepBean Keepb 				: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */

	public boolean  bumonCheck( mst000101_DbmsBean db, mst120201_KeepBean Keepb, Map CtrlColor,
								 SessionManager  sessionManager, TreeMap map) throws Exception,SQLException {
		String ctlName = "ct_bumoncd";
		String bumonCd = Keepb.getBumonCd();
		bumonCd = mst000401_LogicBean.formatZero(bumonCd, 4);
		String bumonNm = getCodeName(db, Keepb.getYukoDt(), mst000101_ConstDictionary.BUNRUI1, bumonCd);

		Keepb.setBumonKanjiRn(bumonNm);

		// 部門コードが存在しない場合
		if (bumonNm == null) {
			mst000401_LogicBean.setErrorBackColor(CtrlColor, ctlName);
			if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
				Keepb.setFirstFocus(ctlName);
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
				return false;
			}
		}
		return true;
	}
//	↓↓別部門に商品コードが登録されていないかのチェック add by kema 06.11.03
	 public boolean SyohinBumonCheck( mst000101_DbmsBean db, mst120201_KeepBean Keepb) throws Exception,SQLException {
		 List lst = new ArrayList();	//マスタ存在チェック抽出条件

		 String setBumonCd = "";
		 setBumonCd = mst000401_LogicBean.formatZero(Keepb.getBumonCd(), 4);

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
//		 ↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		 sb.append(" for read only ");
//		 ↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		 rest = db.executeQuery(sb.toString());
		 if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			 if(rest.next()){
				 return false;
			 }
		 }
		 if(rest != null){
			 rest.close();
		 }
		 return true;
	 }
//	↑↑別部門に商品コードが登録されていないかのチェック add by kema 06.11.03

	/**
	 * 商品コードのチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst120201_KeepBean Keepb 				: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */

	public boolean  syohinCheck( mst000101_DbmsBean db, mst120201_KeepBean Keepb, Map CtrlColor,
								 SessionManager  sessionManager, TreeMap map) throws Exception,SQLException {
		String ctlName = "ct_syohincd";
		String syohinCd = Keepb.getSyohinCd();

		// 商品名称をクリアする
		Keepb.setHinmeiKanji("");

		//チェックデジットエラー
		if (syohinCd.length() == 7) {
			String ret = mst001401_CheckDegitBean.getModulus9(syohinCd.substring(0, 6), 6);
			if(!syohinCd.substring(6).equals(ret)){
				mst000401_LogicBean.setErrorBackColor( CtrlColor, ctlName );
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus( ctlName );
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("40201").toString());
					return false;
				}
			}
		}

		//新規の場合
		if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
			//入力された商品コードが商品マスタ.商品コードに存在する？
			if (syohinCd.length() == 7) {
				mst000701_MasterInfoBean mib = getSyohin(db, Keepb.getSyohinCd(), Keepb.getBumonCd(), "hinmei_kanji_na", Keepb.getYukoDt(),Keepb.getProcessingDivision());
				Keepb.setHinmeiKanji( mib.getCdName() );
				if(mib.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//商品コード存在(有)エラー
					mst000401_LogicBean.setErrorBackColor( CtrlColor, ctlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( ctlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40001").toString());
						return false;
					}
				}
			}
		} else {
			String bumonCd = mst000401_LogicBean.formatZero(Keepb.getBumonCd(), 4);
			mst000701_MasterInfoBean mib = getSyohin(db, Keepb.getSyohinCd(), bumonCd, "gyosyu_kb", Keepb.getYukoDt(),Keepb.getProcessingDivision());
			if(!mib.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				// 商品コード存在エラー
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("0005").toString());
					return false;
				}
			} else {
				if(!Keepb.getGyosyuKb().equals(mib.getCdName().trim())){
					// 指定された商品コードは利用できません
					mst000401_LogicBean.setErrorBackColor( CtrlColor, ctlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( ctlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40026").toString());
						return false;
					}
				}
			}
		}
		return true;
	}
// ↑↑2006.07.08 HuangJiugen カスタマイズ修正↑↑

// ↓↓2006.07.08 HuangJiugen カスタマイズ修正↓↓
	/**
	 * 指定商品マスタの取得を行う。
	 * @param		mst000101_DbmsBean db		: DBインスタンス
	 * @param		String syohinCd				: 商品コード
	 * @param		String bumonCd				: 部門コード
	 * @param		String columnNa				: 取得カラム名
	 * @param		String yukoDt				: 有効日
	 * @return		mst000701_MasterInfoBean 	: 取得結果
	 */
//	public mst000701_MasterInfoBean getSyohin( mst000101_DbmsBean db, String syohinCd, String hankuCd, String gyosyuKb,
	public mst000701_MasterInfoBean getSyohin( mst000101_DbmsBean db, String syohinCd, String bumonCd,
			String columnNa, String yukoDt,String syoriKb)
	 throws Exception,SQLException {

		mst110101_SyohinDM 	 	 dm  = new mst110101_SyohinDM();

		mst000701_MasterInfoBean ret = new mst000701_MasterInfoBean();
		ret.setCdName("");
		ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);

		//マスタ日付
		String MSTDATE = RMSTDATEUtil.getMstDateDt();
		String yuko_Dt = "";

		Map selectMap  = new HashMap();
//		↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//		selectMap.put("syohin_cd_aft_like",syohinCd);
		selectMap.put("syohin_cd",syohinCd);
//		↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
//		if(!hankuCd.equals("")){
//		  selectMap.put("hanku_cd",hankuCd);
//		}
		if (bumonCd != null && bumonCd.length() > 0) {
			if (bumonCd.length() == 3) bumonCd = "0" + bumonCd;
			selectMap.put("bumon_cd", bumonCd);
		}
//		if (!gyosyuKb.equals("")) {
//			selectMap.put("gyosyu_kb",gyosyuKb);
//		}
		selectMap.put("yuko_dt",yukoDt);

		ResultSet rset = null;    //抽出結果(ResultSet)
//		↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//		rset = db.executeQuery(dm.getSelectSql(selectMap));
		rset = db.executeQuery(dm.getSyohinCheckSql(selectMap));
//		↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
		if(rset.next()){
			ret.setCdName(mst000401_LogicBean.chkNullString(rset.getString(columnNa)).trim());
			ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);

		} else {
			if(!mst000401_LogicBean.chkNullString(yukoDt).equals("")){
				MSTDATE = yukoDt;
			}
			//商品(現在有効)の存在チェック
			List whereList = new ArrayList();
			whereList.clear();
			whereList.add("  syohin_cd = '" + syohinCd + "' ");
//			if(!hankuCd.equals("")){
//				whereList.add(" AND hanku_cd = '" + hankuCd + "' ");
//			}
//			if (!gyosyuKb.equals("")) {
//				whereList.add(" AND gyosyu_kb = '" + gyosyuKb + "' ");
//			}
			if (bumonCd != null && bumonCd.length() > 0) {
				if (bumonCd.length() == 3) bumonCd = "0" + bumonCd;
				whereList.add(" and bumon_cd = '" + bumonCd + "'");
			}
// 2005.11.30 Y.Inaba 処理区分により処理を分岐 START
			//現在有効日を取得
			if(syoriKb.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)
				  && (yukoDt != null) && (!yukoDt.trim().equals(""))){
				yuko_Dt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, "r_syohin","yuko_dt",whereList, MSTDATE, false);
			} else{
				yuko_Dt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, "r_syohin","yuko_dt",whereList, MSTDATE, true);
			}
// 2005.11.30 Y.Inaba 処理区分により処理を分岐 START
			if(!yuko_Dt.trim().equals("")){
				selectMap.put("yuko_dt",yuko_Dt);
				ResultSet rset1 = null;    //抽出結果(ResultSet)
//				↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//				rset1 = db.executeQuery(dm.getSelectSql(selectMap));
				rset1 = db.executeQuery(dm.getSyohinCheckSql(selectMap));
//				↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
				if(rset1.next()){
					ret.setCdName(mst000401_LogicBean.chkNullString(rset1.getString(columnNa)).trim());
					ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
				}
			}
		}
		return ret;
	}
// ↑↑2006.07.08 HuangJiugen カスタマイズ修正↑↑

// ↓↓2006.07.10 HuangJiugen カスタマイズ修正↓↓
	private boolean isNotEmpty(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}
		return true;
	}
	private String getCodeName(mst000101_DbmsBean db, mst120201_KeepBean Keepb,
			Map ctrlColor, String ctlName, String errMsg,
			String yukoDt, String syubetuNoCd, String code, boolean isShowError) throws Exception,SQLException {
		String name = getCodeName(db, yukoDt, syubetuNoCd, code);

		if (name == null && isShowError) {
			mst000401_LogicBean.setErrorBackColor( ctrlColor, ctlName );
			if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
				Keepb.setFirstFocus(ctlName);
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage(errMsg);
			}
		}
		return name;
	}

	public void  initAllCodeName( mst000101_DbmsBean db, mst120201_KeepBean Keepb, Map CtrlColor,
			 SessionManager  sessionManager, TreeMap map ) throws Exception,SQLException {

		boolean isShowError = true;
		if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)
				|| Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)) {
			isShowError = false;
		}

		String errMsg = map.get("40007").toString();
		String yukoDt = Keepb.getYukoDt();
		String codeName;
		// ユニット
		if (isNotEmpty(Keepb.getUnitCd())) {
			codeName = getCodeName(db, Keepb, CtrlColor, "ct_unitcd", "指定されたユニット" + errMsg,
							yukoDt, mst000101_ConstDictionary.BUNRUI5, sessionManager.getSession().getAttribute("mdwareSystemKb") + Keepb.getUnitCd(), isShowError);
			Keepb.setUnitNm(codeName == null ? "" : codeName);
		} else {
			Keepb.setUnitNm("");
		}

		// POSコード
		if (Keepb.getSyohin2Cd().length() == 13) {
//			↓↓2006.10.26 H.Yamamoto カスタマイズ修正↓↓
			String syohinCheckCd = Keepb.getSyohin2Cd();
			boolean syohinCheckFg = false;
			if (Keepb.getSyohin2Cd().length() == 13){
				if("0400".equals(syohinCheckCd.substring(0,4))){
					// 自動採番
					syohinCheckFg = true;
				} else if("0000000000".equals(syohinCheckCd.substring(0,10))){
					// NLU
					syohinCheckFg = false;
				} else if("0000000".equals(syohinCheckCd.substring(0,7))){
					// UPC-E
					syohinCheckFg = false;
				} else if("45".equals(syohinCheckCd.substring(0,2)) || "49".equals(syohinCheckCd.substring(0,2))){
					// 49,45フラグ（13桁）
					syohinCheckFg = true;
				} else if("0000045".equals(syohinCheckCd.substring(0,7)) || "0000049".equals(syohinCheckCd.substring(0,7))){
					// 49,45フラグ（8桁）
					syohinCheckFg = true;
				} else if(Integer.parseInt(syohinCheckCd.substring(0,1)) >= 3){
					// EAN（13桁）
					syohinCheckFg = true;
				} else if("00000".equals(syohinCheckCd.substring(0,5)) && Integer.parseInt(syohinCheckCd.substring(5,6)) >= 3){
					// EAN（8桁）
					syohinCheckFg = true;
				} else if("00".equals(syohinCheckCd.substring(0,2)) || "01".equals(syohinCheckCd.substring(0,2)) || "03".equals(syohinCheckCd.substring(0,2)) ||
							"06".equals(syohinCheckCd.substring(0,2)) || "07".equals(syohinCheckCd.substring(0,2)) || "08".equals(syohinCheckCd.substring(0,2)) ||
							"09".equals(syohinCheckCd.substring(0,2))){
					// UPC-A
					syohinCheckFg = false;
				} else{
					// その他
					syohinCheckFg = false;
				}
			}
			if (syohinCheckFg){
//			↑↑2006.10.26 H.Yamamoto カスタマイズ修正↑↑
				String ret = mst001401_CheckDegitBean.getModulus10(Keepb.getSyohin2Cd().substring(0,12), 12);
				if(!Keepb.getSyohin2Cd().substring(12).equals(ret)){
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setFirstFocus("ct_syohin2cd");
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohin2cd");
					Keepb.setErrorMessage( map.get("em000010").toString());
				}
//			↓↓2006.10.26 H.Yamamoto カスタマイズ修正↓↓
			}
//			↑↑2006.10.26 H.Yamamoto カスタマイズ修正↑↑
		}
		// ＪＡＮメーカーコード
		if (isNotEmpty(Keepb.getMakerCd())) {
//			↓↓2006.11.22 H.Yamamoto カスタマイズ修正↓↓
//			codeName = getCodeName(db, Keepb, CtrlColor, "ct_makercd", "指定されたＪＡＮメーカーコード" + errMsg,
//							yukoDt, mst000101_ConstDictionary.JAN_MAKER_NAME, Keepb.getMakerCd(), isShowError);
			codeName = getCodeName(db, Keepb, CtrlColor, "ct_makercd", "指定されたＪＡＮメーカーコード" + errMsg,
							yukoDt, mst000101_ConstDictionary.JAN_MAKER_NAME, Keepb.getMakerCd(), false);
//			↑↑2006.11.22 H.Yamamoto カスタマイズ修正↑↑
			Keepb.setMakerNm(codeName == null ? "" : codeName);
		} else {
			Keepb.setMakerNm("");
		}

		// サイズ
		if (isNotEmpty(Keepb.getSizeCd())) {
			codeName = getCodeName(db, Keepb, CtrlColor, "ct_sizecd", "指定されたサイズ" + errMsg,
							yukoDt, mst000101_ConstDictionary.SIZE, Keepb.getSizeCd(), isShowError);
			Keepb.setSizeNm(codeName == null ? "" : codeName);
		} else {
			Keepb.setSizeNm("");
		}

		// カラー
		if (isNotEmpty(Keepb.getColorCd())) {
			codeName = getCodeName(db, Keepb, CtrlColor, "ct_colorcd", "指定されたカラー" + errMsg,
							yukoDt, mst000101_ConstDictionary.COLOR2, Keepb.getColorCd(), isShowError);
			Keepb.setColorNm(codeName == null ? "" : codeName);
		} else {
			Keepb.setColorNm("");
		}

//		↓↓2006.08.28 kema カスタマイズ修正↓↓
		//〔PLU送信日〕新規変更時のみ
		if( (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
			Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE) ) &&
			isNotEmpty(Keepb.getPluSendDt()) ) {
			if(Long.parseLong(Keepb.getPluSendDt()) < Long.parseLong(RMSTDATEUtil.getMstDateDt())){
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setFirstFocus("ct_plusenddt");
				mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_plusenddt");
				Keepb.setErrorMessage("PLU送信日はマスタ管理日付以降の日を指定してください。");
			}
		}
		//〔仕入先コード〕　システム区分チェック追加による変更 by kema 06.09.28
		String ctlName = "ct_siiresakicd";
		if(!Keepb.getSiiresakiCd().equals("")){
			StringBuffer sb = new StringBuffer();
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
			sb.append( "SIIRESAKI_CD = '" + Keepb.getSiiresakiCd() + Keepb.getArea_Cd() + "' " );
			sb.append(" and ");
			sb.append(" DELETE_FG = '0' ");
//			↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
//			sb.append(" order by SIIRESAKI_CD ");
			sb.append(" and ");
			sb.append(" SIIRE_SYSTEM_KB in ('1','3') ");
			sb.append(" order by SIIRESAKI_CD desc ");
//			↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
			sb.append(" for read only ");
//			↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

			ResultSet rest = null;

			rest = db.executeQuery(sb.toString());

			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(rest.next()){
					Keepb.setSiiresakiNm(rest.getString("KANJI_RN"));
//					↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
//					if(rest.getString("SIIRE_SYSTEM_KB") != null && rest.getString("SIIRE_SYSTEM_KB").equals("2")){
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							//仕入先システム区分エラー
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,ctlName);
//							Keepb.setFirstFocus(ctlName);
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage("指定された仕入先が不正です。");
//						}
//					}
//					↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
				} else {
					mst000401_LogicBean.setErrorBackColor(CtrlColor,ctlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						//仕入先コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,ctlName);
						Keepb.setFirstFocus(ctlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された仕入先コード"+errMsg);
					}
				}
			} else {
//				↓↓2006.10.07 H.Yamamoto カスタマイズ修正↓↓
//				Keepb.setSiiresakiNm("");
				if(rest.next()){
					Keepb.setSiiresakiNm(rest.getString("KANJI_RN"));
				} else {
					Keepb.setSiiresakiNm("");
				}
//				↑↑2006.10.07 H.Yamamoto カスタマイズ修正↑↑
			}
			if(rest != null){
				rest.close();
			}
		}


	}
// ↑↑2006.07.10 HuangJiugen カスタマイズ修正↑↑

	/**
	 *
	 * 新規/修正時の入力項目のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst120201_KeepBean Keepb 	: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */

	public void  UpdateCheck( mst000101_DbmsBean db, mst120201_KeepBean Keepb, Map CtrlColor,
							 SessionManager  sessionManager, TreeMap map ) throws Exception,SQLException {
// ↓↓2006.07.10 HuangJiugen カスタマイズ修正↓↓
//		List lst = new ArrayList();	//マスタ存在チェック抽出条件
//
//		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
//		String CtlName = "";
//		String chkDeg = "";
//		String setHankuCd = "";
//		if(Keepb.getHankuCd().equals("")){
//			setHankuCd = Keepb.getTempHankuCd();
//		} else {
//			setHankuCd = Keepb.getHankuCd();
//		}
//		//〔品種コード〕
//		CtlName = "ct_hinsyucd";
//		lst.clear();
//		lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' ");
//		lst.add(" and ");
//		lst.add("code_cd = '" + Keepb.getHinsyuCd() + "' ");
//// 2006.01.24 Y.Inaba Mod ↓
////		mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.01.24 Y.Inaba Mod ↑
//		Keepb.setHinsyuNa( mstchk.getCdName() );
//
//		if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ){
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//品種コード存在エラー
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された品種コード"+map.get("40007").toString());
//					}
//				} else {
//					//ログインユーザの売区とのチェック
//					if(!mst000401_LogicBean.getHinshuCdCheck(db,Keepb.getHinsyuCd(),sessionManager)){
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus(CtlName);
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage(map.get("40023").toString());
//						}
//					}
//				}
//
//				if(!Keepb.getHankuCd().equals("") && Keepb.getSyohinCd().equals("")){
//					//販区の取得（品種入力時に商品体系マスタより取得）
//					lst.clear();
//					lst.add("hinsyu_cd = '" + Keepb.getHinsyuCd() + "' ");
//					lst.add(" and ");
//					lst.add("hanku_cd = '" + Keepb.getHankuCd() + "' ");
//					mstchk = mst000401_LogicBean.getSyohinTaikei(db,"hanku_cd", lst );
//					if(!Keepb.getHankuCd().equals(mstchk.getCdName())){
//						//品種コードエラー
//						mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus( CtlName );
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage(map.get("40023").toString());
//						}
//					}
//				}
//			}
//			if(Keepb.getHankuCd().equals("") && Keepb.getSyohinCd().equals("")){
//				//更新用販区の取得（品種入力時に商品体系マスタより取得）
//				lst.clear();
//				lst.add("hinsyu_cd = '" + Keepb.getHinsyuCd() + "' ");
//				if(!Keepb.getHankuCd().equals("")){
//					lst.add(" and ");
//					lst.add("hanku_cd = '" + Keepb.getHankuCd() + "' ");
//				}
//				mstchk = mst000401_LogicBean.getSyohinTaikei(db,"hanku_cd", lst );
//				Keepb.setTempHankuCd( mstchk.getCdName() );
//				if(Keepb.getHankuCd().equals("")){
//					setHankuCd = Keepb.getTempHankuCd();
//				} else {
//					setHankuCd = Keepb.getHankuCd();
//				}
//			}
//			//〔単品〕
//			CtlName = "ct_tanpincd";
//			if(!Keepb.getTanpinCd().equals("") && Keepb.getSyohinCd().length() <= 4){
//// ↓↓仕様変更（2005.09.02）↓↓
////				mstchk = getSyohin( db, Keepb.getHinsyuCd().trim() + Keepb.getTanpinCd().trim(), Keepb.getHankuCd(),Keepb.getGyosyuKb(),"hinmei_kanji_na",Keepb.getYukoDt());
////				mstchk = getSyohin( db, Keepb.getHinsyuCd().trim() + Keepb.getTanpinCd().trim(), setHankuCd, Keepb.getGyosyuKb(),"hinmei_kanji_na",Keepb.getYukoDt());
//				mstchk = getSyohin( db, Keepb.getHinsyuCd().trim() + Keepb.getTanpinCd().trim(), setHankuCd, Keepb.getGyosyuKb(),"hinmei_kanji_na",Keepb.getYukoDt(),Keepb.getProcessingDivision());
//// ↑↑仕様変更（2005.09.02）↑↑
//				if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//					if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//						//商品コード存在(有)エラー
//						mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//							Keepb.setFirstFocus( CtlName );
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage(map.get("40212").toString());
//						}
//					}
//				}
//			}
//		}
//		 //〔発注商品コード区分〕
//		 Keepb.setHacyuSyohinKb(mst006001_HacyuSyohinKbDictionary.JISYA_CD.getCode());
//		 //〔システム区分〕
//		   lst.clear();
//		   lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' ");
//		   lst.add(" and ");
//// ↓↓バグ修正（2005.08.25）↓↓
////		   lst.add("code_cd = '" + Keepb.getHankuCd() + "' ");
//		   lst.add("code_cd = '" + setHankuCd + "' ");
//// ↑↑バグ修正（2005.08.25）↑↑
//// 2006.01.24 Y.Inaba Mod ↓
////		   mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","zokusei_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//		   mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","zokusei_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.01.24 Y.Inaba Mod ↑
//		   Keepb.setSystemKb( mstchk.getCdName() );
//		//〔仕入先コード〕
//		CtlName = "ct_siiresakicd";
//		lst.clear();
//		lst.add("KANRI_KB = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//		lst.add(" and ");
//		lst.add("KANRI_CD = '" + setHankuCd+ "' ");
//		lst.add(" and ");
//		lst.add( "SIIRESAKI_CD = '" + Keepb.getSiiresakiCd() + "' " );
//// ↓↓仕様追加による変更（2005.06.01）
//		lst.add(" and ");
//		lst.add(" tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//// ↑↑仕様追加による変更（2005.06.01）
//// 2006.01.24 Y.Inaba Mod ↓
////		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.01.24 Y.Inaba Mod ↑
//		Keepb.setSiiresakiNa( mstchk.getCdName() );
//		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				//仕入先コード存在エラー
//				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					Keepb.setFirstFocus(CtlName);
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					Keepb.setErrorMessage("指定された仕入先コード"+map.get("40007").toString());
//				}
//			}
//		}
//		//〔店別仕入先管理コード〕
//		CtlName = "ct_tensiiresakikanricd";
//		if(!Keepb.getTenSiiresakiKanriCd().equals("")){
//			lst.clear();
//			lst.add( " kanri_kb = '" +  mst000901_KanriKbDictionary.HANKU.getCode() + "' "  );
//			lst.add( " and " );
//			lst.add( " kanri_cd = '" + setHankuCd + "' "  );
//			lst.add( " and " );
//			lst.add( " tenpo_cd = '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' " );
//			lst.add( " and " );
//// ↓↓仕様変更（2005.07.22）↓↓
////			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' " );
//// ↓↓仕様変更（2005.07.25）↓↓
////			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getHankuCd() + Keepb.getTenSiiresakiKanriCd() + "' " );
//			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' " );
//// ↑↑仕様変更（2005.07.25）↑↑
//// ↑↑仕様変更（2005.07.22）↑↑
//// ↓↓仕様変更（2005.08.24）↓↓
//			lst.add( " and " );
//			lst.add( " tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' " );
//// ↑↑仕様変更（2005.08.24）↑↑
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db, "vw_r_tensiiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
//			mstchk = mst000401_LogicBean.getMasterCdName(db, "vw_r_tensiiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt());
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
//			//名称取得
//			lst.clear();
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP + "' ");
//			lst.add(" and ");
//// ↓↓仕様変更（2005.07.13）↓↓
////			lst.add("code_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' ");
//// ↓↓仕様変更（2005.09.02）↓↓
////			lst.add("code_cd = '" + Keepb.getHankuCd() + Keepb.getTenSiiresakiKanriCd() + "' ");
//			lst.add("code_cd = '" + setHankuCd + Keepb.getTenSiiresakiKanriCd() + "' ");
//// ↑↑仕様変更（2005.09.02）↑↑
//// ↑↑仕様変更（2005.07.13）↑↑
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.01.24 Y.Inaba Mod ↑
//			Keepb.setTenSiiresakiKanriNa( mstchk.getCdName() );
//// ↓↓仕様変更（2005.07.26）↓↓
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
// ↑↑仕様変更（2005.07.26）↑↑
//		} else {
//			Keepb.setTenSiiresakiKanriNa( "" );
//		}
//		//〔代表配送先コード〕
//		int cnt = 0; //不要ＳＱＬ文の削除用カウンター
//		CtlName = "ct_daihyohaisocd";
//// ↓↓バグ修正（2005.07.28）↓↓
////		if(!Keepb.getDaihyoHaisoCd().equals("")){
////			lst.clear();
////			lst.add(" kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
////			lst.add(" and ");
////			lst.add(" kanri_cd = '" + Keepb.getHankuCd() + "' ");
////			lst.add(" and ");
////			lst.add( "haisosaki_cd = '" + Keepb.getDaihyoHaisoCd()+"0" + "' " );
//// ↓↓仕様追加による変更（2005.06.01）
////			cnt = lst.size(); //これ以降のＳＱＬ文は削除対象になる。
////			lst.add(" and ");
////			lst.add(" tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//// ↑↑仕様追加による変更（2005.06.01）
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_haisou","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
////			Keepb.setDaihyoHaisoNa( mstchk.getCdName() );
////			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//// ↓↓仕様追加による変更（2005.06.01）
//// if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
////				for (int i = lst.size() - 1; i >= cnt; i--) {
////					//余分なＳＱＬ文を削除
////					lst.remove(i);
////				}
////				if(!mst000401_LogicBean.getMasterChk(db,"r_tenbetu_haisosaki", lst  )){
//// ↑↑仕様追加による変更（2005.06.01）
////					//コード存在エラー
////					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
////					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
////						Keepb.setFirstFocus(CtlName);
////						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////						Keepb.setErrorMessage("指定された代表配送先コード"+map.get("40007").toString());
////					}
////				}
////			}
////		}
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
//// ↑↑バグ修正（2005.07.28）↑↑
//		} else{
//			Keepb.setDaihyoHaisoNa("");
//		}
//		if (rest != null) {
//			rest.close();
//		}
//// ↓↓仕様変更（2005.08.24）↓↓
////		//処理状況：【修正】のみ
////		if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
////			&& !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS) ){
////			String syohin = "";
////			if(Keepb.getSyohinCd().length() == 8){
////				syohin = Keepb.getSyohinCd();
////			} else {
////				mstchk = getSyohin( db, Keepb.getSyohinCd(), setHankuCd,Keepb.getGyosyuKb(),"syohin_cd",Keepb.getYukoDt());
////				syohin = mstchk.getCdName();
////			}
////			//〔原価単価(税抜)〕
////			CtlName = "ct_gentankavl";
////			String gentanka = mst000401_LogicBean.removeComma(Keepb.getGentankaVl()).trim();
////			if(!Keepb.getGentankaSenVl().trim().equals("")){
////				if(Keepb.getGentankaSenVl().length() < 2){
////					gentanka = gentanka + ".0" + Keepb.getGentankaSenVl().trim();
////				} else {
////					gentanka = gentanka + "." + Keepb.getGentankaSenVl().trim();
////				}
////			}
////			lst.clear();
////			lst.add( " HANKU_CD = '" + setHankuCd + "' ");
////			lst.add( " AND ");
////			lst.add( " SYOHIN_CD = '" + syohin + "' ");
////			lst.add( " AND ");
////			lst.add( " KAKAKU_KB = '" + mst004501_KakakuKbDictionary.GEN_TANKA.getCode() + "' ");
////			lst.add( " AND ");
////			lst.add( " MIN_KAKAKU_VL > '" + gentanka + "' ");
////			lst.add( " AND ");
////			lst.add( " CHECK_ST_DT <= '" + Keepb.getYukoDt() + "' ");
////			lst.add( " AND ");
////			lst.add( " CHECK_ED_DT >= '" + Keepb.getYukoDt() + "' ");
////			lst.add( " AND ");
////			lst.add( " DELETE_FG = '0' ");
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_KAKAKUCHECK","MIN_KAKAKU_VL", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
////			if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
////				//存在(有)エラー
////				mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
////				mst000401_LogicBean.setErrorBackColor( CtrlColor, "ct_gentankasenvl" );
////				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
////					Keepb.setFirstFocus( CtlName );
////					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////					Keepb.setErrorMessage("原価単価"+map.get("40202").toString());
////				}
////			}
////			//〔売価単価（税込）〕
////			CtlName = "ct_baitankavl";
////			lst.clear();
////			lst.add( " HANKU_CD = '" + setHankuCd + "' ");
////			lst.add( " AND ");
////			lst.add( " SYOHIN_CD = '" + syohin + "' ");
////			lst.add( " AND ");
////			lst.add( " KAKAKU_KB = '" + mst004501_KakakuKbDictionary.URI_TANKA.getCode() + "' ");
////			lst.add( " AND ");
////			lst.add( " MIN_KAKAKU_VL > '" + mst000401_LogicBean.removeComma(Keepb.getBaitankaVl()) + "' ");
////			lst.add( " AND ");
////			lst.add( " CHECK_ST_DT <= '" + Keepb.getYukoDt() + "' ");
////			lst.add( " AND ");
////			lst.add( " CHECK_ED_DT >= '" + Keepb.getYukoDt() + "' ");
////			lst.add( " AND ");
////			lst.add( " DELETE_FG = '0' ");
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_KAKAKUCHECK","MIN_KAKAKU_VL", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
////			if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
////				//存在(有)エラー
////				mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
////				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
////					Keepb.setFirstFocus( CtlName );
////					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////					Keepb.setErrorMessage("売価単価（税込）"+map.get("40202").toString());
////				}
////			}
////		}
//// ↑↑仕様変更（2005.08.24）↑↑
//
//		//〔ブランドコード〕
//		CtlName = "ct_blandcd";
//		if(!Keepb.getBlandCd().equals("")){
//			lst.clear();
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.BRAND + "' ");
//			lst.add(" and ");
//// ↓↓ブランドコード仕様変更対応(2005.09.26)
////			lst.add("code_cd = '" + Keepb.getBlandCd() + "' ");
////			lst.add("code_cd = '" + setHankuCd + Keepb.getBlandCd() + "' ");
//			lst.add("trim(code_cd) = '" + setHankuCd + Keepb.getBlandCd() + "' ");
//// ↑↑ブランドコード仕様変更対応(2005.09.26)
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.01.24 Y.Inaba Mod ↑
//			Keepb.setBlandNa( mstchk.getCdName() );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//コード存在エラー
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定されたブランドコード"+map.get("40007").toString());
//					}
//				}
//			}
//		} else {
//			Keepb.setBlandNa( "" );
//		}
//		//〔在庫センターコード〕
//		CtlName = "ct_zaikocentercd";
//		if(!Keepb.getZaikoCenterCd().equals("")){
//			lst.clear();
//			lst.add("tenpo_cd = '" + Keepb.getZaikoCenterCd()+ "' ");
//			lst.add(" and ");
//			lst.add( " tenpo_kb = '" + mst003601_TenpoKbDictionary.BUTURYU_CENTER.getCode() + "' " );
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_tenpo","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_tenpo","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
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
//			Keepb.setZaikoCenterNa( "" );
//		}
//// ↓↓バグ修正（2005.08.09）↓↓
//		// 〔税区分〕
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
// ↑↑バグ修正（2005.08.09）↑↑

		// すべてコード存在をチェックし、コード名称を取得する
		initAllCodeName(db, Keepb, CtrlColor, sessionManager, map);

		// ユニットコード存在チェックでエラーがない場合
		if (!mst000101_ConstDictionary.CONTROL_ERROR_BACKCOLOR.equals(map.get("ct_unitcd"))
				&& isNotEmpty(Keepb.getUnitCd())) {
			// ユニットコード 商品体系マスタとセッションのシステム区分＋ユニットコード＋部門コードで存在チェック
			String systemKb = (String) sessionManager.getAttribute("mdwareSystemKb");
			String bumonCd = mst000401_LogicBean.formatZero(Keepb.getBumonCd(), 4);
			String unitCd = Keepb.getUnitCd();

			StringBuffer sql = new StringBuffer();
			sql.append(" select HINBAN_CD, BUMON_CD from R_SYOHIN_TAIKEI ");
			sql.append("  where SYSTEM_KB = '").append(systemKb).append("' ");
			sql.append("    and UNIT_CD = '").append(unitCd).append("' ");
//			↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
			sql.append(" for read only ");
//			↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
			ResultSet rs = db.executeQuery(sql.toString());
			String dbBumonCd = null;
			String dbHinbanCd = null;
			if(rs.next()){
				dbBumonCd = (String) rs.getString("BUMON_CD");
				dbHinbanCd = (String) rs.getString("HINBAN_CD");
			}
			// 対応システム区分と部門の該当ユニットコードが存在しない場合
			if (!bumonCd.equals(dbBumonCd)) {
				String ctlName = "ct_unitcd";
				mst000401_LogicBean.setErrorBackColor(CtrlColor, ctlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(ctlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("商品体系の部門・ユニットコードの" + map.get("0002").toString());
				}
			} else { //存在場合
				// 取得した品番コードをKeepbへ格納する、新規の場合、商品マストへ登録用
				Keepb.setHinbanCd(dbHinbanCd);
			}
			if(rs != null){
				rs.close();
			}

		}

//		↓↓2006.10.20 H.Yamamoto カスタマイズ修正↓↓
		// ＪＡＮバッティングチェック
		if (!mst000101_ConstDictionary.CONTROL_ERROR_BACKCOLOR.equals(map.get("ct_syohin2cd"))
				&& isNotEmpty(Keepb.getSyohin2Cd())) {
			// 別商品で同一POSコードの存在チェック
			String syohinCd = Keepb.getSyohinCd();
			String syohin2Cd = Keepb.getSyohin2Cd();
			String yukoDt = Keepb.getYukoDt();

			StringBuffer sql = new StringBuffer();
			sql.append("select syohin_cd from r_syohin rs ");
			sql.append(" where syohin_cd <> '").append(syohinCd).append("' ");
			sql.append("   and syohin_2_cd = '").append(syohin2Cd).append("' ");
			sql.append("   and yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, '").append(yukoDt).append("') ");
			sql.append(" fetch first 1 rows only ");
//			↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
			sql.append(" for read only ");
//			↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
			ResultSet rs = db.executeQuery(sql.toString());
			if(rs.next()){
				String ctlName = "ct_syohin2cd";
				mst000401_LogicBean.setErrorBackColor(CtrlColor, ctlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(ctlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage( map.get("em000012").toString());
				}
			}
			rs.close();
		}
//		↑↑2006.10.20 H.Yamamoto カスタマイズ修正↑↑

		// バイヤーNo R利用ユーザーマスタと存在チェック
		if (isNotEmpty(Keepb.getSyokaiUserId())) {
			StringBuffer sql = new StringBuffer();
//			sql.append(" select RIYO_USER_ID");
//			sql.append("   from R_RIYO_USER ");
//			sql.append("  where RIYO_USER_ID = '" + "0000" + Keepb.getSyokaiUserId() + "' ");
			sql.append(" select USER_ID AS RIYO_USER_ID");
			sql.append("   from R_PORTAL_USER ");
			sql.append("  where USER_ID = '" + "0000" + Keepb.getSyokaiUserId() + "' ");
//			↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
			sql.append(" for read only ");
//			↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
			ResultSet rs = db.executeQuery(sql.toString());
			if(!rs.next()){
				String ctlName = "ct_userid";
				mst000401_LogicBean.setErrorBackColor(CtrlColor, ctlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(ctlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("バイヤーNoの" + map.get("0002").toString());
				}
			}
			if(rs != null){
				rs.close();
			}
		}

	}
}