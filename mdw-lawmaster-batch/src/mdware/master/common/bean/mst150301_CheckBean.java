/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/08)初版作成
 * @version 1.1 (2006/02/06) D.Matsumoto 新規の場合のみ削除フラグがあるデータを無視する
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
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
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
public class mst150301_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
	public void Check(
		mst000101_DbmsBean db,
		mst150201_KeepBean Keepb,
		DataHolder dataHolder,
//		<!--   ↓↓2006.07.11 wangjm カスタマイズ修正↓↓  -->
//		String kengenCd,
//		<!--   ↑↑2006.07.11 wangjm カスタマイズ修正↑↑  -->

		String GamenId,
		String FirstFocusCtl,
		String[] CtrlName,
		Map CtrlColor,
		String tableNa,
		List whereList,

//<!--   ↓↓2006.07.11 wangjm カスタマイズ修正↓↓  -->
//		String addValue,
//		<!--   ↑↑2006.07.11 wangjm カスタマイズ修正↑↑  -->
		SessionManager  sessionManager ) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT) ){
				//画面内容を保存
//				<!--   ↓↓2006.07.11 wangjm カスタマイズ追加↓↓  -->
				Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd").trim()));	//部門コード
				Keepb.setBumonKanjiRn(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumonna"))));				//部門名
//				<!--   ↑↑2006.07.11 wangjm カスタマイズ追加↑↑  -->
				Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")));							//商品コード
				Keepb.setHinmeiKanji(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohinna")));						//商品名
				Keepb.setYukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yukodt")));								//有効日

				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim() );	        //新規or更新(修正)or削除or参照
				Keepb.setChangeFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_changeflg")).trim());                   //状態フラグ(初期or検索or更新or照会orその他）
				//画面内容を保存(BODY)
//				<!--   ↓↓2006.07.11 wangjm カスタマイズ修正↓↓  -->
//				Keepb.setHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")));								//販区コード
//				Keepb.setHankuKanjiRn(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankuna"))));//販区名
//				Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyucd")));							//品種コード
//				Keepb.setHinsyuNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyuna"))));	//品種名
//				Keepb.setHinmokuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmokucd")));							//品目
//				Keepb.setHinmokuNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmokuna"))));	//品目
//				Keepb.setMakerCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makercd")));								//JANメーカーコード
//				Keepb.setMakerNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makerna"))));		//JANメーカー名
//				Keepb.setSantiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_santicd")));								//原産国/産地コード
//				Keepb.setSantiNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_santina"))));		//原産国/産地コード
//				Keepb.setHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanana")));					//カナ品名
//				Keepb.setKikakuKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanana")));					//カナ規格
//				Keepb.setHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanjina")));					//漢字品名
//				Keepb.setKikakuKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanjina")));					//漢字規格
//				Keepb.setTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanricd")));		//店別仕入先管理コード
//				Keepb.setTenSiiresakiKanriNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanrina"))));	//店別仕入先管理名
//				Keepb.setDaihyoHaisoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisocd")));					//代表配送先コード
//				Keepb.setDaihyoHaisoNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyo_haiso_na"))));			//代表配送先名
//				Keepb.setBlandCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandcd")));								//ブランドコード
//				Keepb.setBlandNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandna"))));		//ブランドコード
//				Keepb.setUnitPriceTaniKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricetanikb")));				//ユニットプライス-単位区分
//				Keepb.setUnitPriceNaiyoryoQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricenaiyoryoqt")));		//ユニットプライス-内容量
//				Keepb.setUnitPriceKijunTaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricekijuntaniqt")));	//ユニットプライス-基準単量
//				Keepb.setGentankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankavl")));						//原価単価
//				Keepb.setGentankaSenVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankasenvl")));					//原価単価(銭)
//				Keepb.setBaitankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitankavl")));						//売価単価(税込)
//				Keepb.setHachutaniIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutaniirisuqt")));			//発注単位(入数)
//				Keepb.setMaxHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_maxhachutaniqt")));				//最大発注単位
//				Keepb.setCaseIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_caseirisuqt")));						//ケース入り数
//				Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")));						//仕入先コード
//				Keepb.setSiiresakiNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakirn"))));//仕入先名
//				Keepb.setSiireHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siirehinbancd")));					//メーカー品番
//				Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));									//EOS区分
//				Keepb.setHachuTeisiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachuteisikb")));					//発注停止区分
//				Keepb.setTenZaikoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenzaikokb")));						//店在庫区分
//				Keepb.setTeikanKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_teikankb")));							//定貫区分
//				Keepb.setGotMujyokenFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gotmujyokenfg")));					//GOT無条件表示対象
//				Keepb.setKeypluFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_keyplufg")));							//キーPLU対象
//				Keepb.setButuryuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_buturyukb")));							//物流区分
//// ↓↓仕様変更（2005.08.12）↓↓
////				Keepb.setSobaSyohinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sobasyohinkb")));					//相場商品区分
//				Keepb.setSobaSyohinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_sobasyohinkb")));					//相場商品区分
//// ↑↑仕様変更（2005.08.12）↑↑
//				Keepb.setHachuPattern1Kb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachupattern1kb")));				//①発注パターン区分
//				Keepb.setBin1Kb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bin1kb")));								//①便区分
//				Keepb.setHachuPattern2Kb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachupattern2kb")));				//②発注パターン区分
//				Keepb.setBin2Kb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bin2kb")));								//②便区分
//				Keepb.setNohinOndoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohinondokb")));						//納品温度帯
//				Keepb.setNohinSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohinsyohincd")));					//納品商品コード
//				Keepb.setItfCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_itfcd")));									//ITFコード
//				Keepb.setRecHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rechinmeikanana")));				//POSレシート品名(カナ)
//				Keepb.setRecHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rechinmeikanjina")));			//POSレシート品名(漢字)
//				Keepb.setPcKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pckb")));									//PC発行区分
//				Keepb.setHanbaiLimitKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitkb")));					//販売期限区分
//				Keepb.setHanbaiLimitQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitqt")));					//販売期限
//				Keepb.setNohinKigenKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohinkigenkb")));					//納品期限区分
//				Keepb.setNohinKigenQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohinkigenqt")));					//納品期限
//				Keepb.setCommentTx(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_commenttx")));							//コメント
				Keepb.setGenyukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_genyukodt")));					//現在有効日
				Keepb.setTenReigai(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tenreigai")));				    //店舗例外登録

				Keepb.setUnitCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitcd")));				        //ユニットコード
				Keepb.setUnit_Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitna")));						//ユニット名
				Keepb.setHaiFuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_haifucd")));						//配布コード
				Keepb.setSubclassCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_subclasscd")));			    //サブクラスコード
				Keepb.setSubclass_Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_subclassna")));			    //サブクラス名
				Keepb.setUserId(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_userid")));			            //バイヤーNo

				Keepb.setMakerCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makercd")));			            //ＪＡＮメーカーコード
				Keepb.setMaker_Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makerna")));			        //ＪＡＮメーカー名
                Keepb.setHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanjina")));			//漢字品名
				Keepb.setKikakuKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanjina")));			//漢字規格
                Keepb.setKikakuKanji2Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanjina2")));		//漢字規格2
                Keepb.setHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanana")));			//カナ品名
				Keepb.setKikakuKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanana")));			//カナ規格
                Keepb.setKikakuKana2Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanana2")));			//カナ規格2
                Keepb.setRecHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rechinmeikanjina")));	//POSレシート品名(漢字)
				Keepb.setRecHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rechinmeikanana")));		//POSレシート品名(カナ)

				Keepb.setGentankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankavl")));				//原価単価(税込)
	            Keepb.setBaitankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitankavl")));				//売価単価(税込)
	            Keepb.setMakerKiboKakakuVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makerkibokakakuvl")));	//メーカー希望小売り価格(税込み)
//				if (mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_plusenddt")).equals("")) {
//					Keepb.setPluSendDt(RMSTDATEUtil.getMstDateDt());															//PLU送信日
//				} else {
					Keepb.setPluSendDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_plusenddt")));				//PLU送信日
//				}
				Keepb.setAreaCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_areacd")));	                    //地区コード
				Keepb.setAreacd_Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_areana")));	                //地区名
				Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")));	            //仕入先コード
				Keepb.setSiiresakiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakirn")));	            //仕入先名
				Keepb.setTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanricd")));	            //店別仕入先管理コード
				Keepb.setTenSiiresakiKanriNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanrina")));	            //店別仕入先管理名
				Keepb.setSiireHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siirehinbancd")));	                        //仕入先商品コード

				Keepb.setHanbaiStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaistdt")));	                        //販売開始日
				Keepb.setHanbaiEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaieddt")));	                        //販売終了日
				Keepb.setTenHachuStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachustdt")));	                    //発注開始日
				Keepb.setTenHachuEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachueddt")));	                    //発注終了日

				Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));	                                    //ＥＯＳ区分
				Keepb.setFujisyohinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_fujisyohinkb")));	                    //商品区分
				Keepb.setBlandCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandcd")));	                                //ブランドコード
				Keepb.setBlandNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandna")));	                                //ブランド名
				Keepb.setPbKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pbkb")));	                                    //ＰＢ区分

				Keepb.setHachuTaniNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutanina")));	                         //発注単位呼称
				Keepb.setHachutaniIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutaniirisuqt")));	             //発注単位（入数）
				Keepb.setMaxHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_maxhachutaniqt")));	                 //最大発注単位
				Keepb.setBinkuiti_ct(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_binkuiti")));	                         //便区分1
				Keepb.setBinkuni_ct(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_binkuni")));	                             //便区分2
				Keepb.setYusenbinkb_ct(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yusenbinkb")));	                    //優先便区分
				Keepb.setUnitPriceTaniKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricetanikb")));	                 //ユニットプライス単位
				Keepb.setUnitPriceNaiyoryoQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricenaiyoryoqt")));	        //ユニットプライス内容量
				Keepb.setUnitPriceKijunTaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricekijuntaniqt")));	    //ユニットプライス基準単位量
//				Keepb.setSyohikigen(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohikigen")));	                        //消費期限
//				 MOD by Tanigawa 2006/11/02 障害票№0238対応 START
				Keepb.setSyohikigen(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohikigen")).trim());                    //消費期限
//				 MOD by Tanigawa 2006/11/02 障害票№0238対応  END
//				 ADD by Tanigawa 2006/11/01 障害票№0238対応 START
				Keepb.setHanbaiLimitQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitqt")).trim());
				Keepb.setCenterKyoyoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_centerkyoyodt")).trim());
//				 ADD by Tanigawa 2006/11/01 障害票№0238対応  END

				Keepb.setDaichotenpoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daichotenpo")));	                    //商品台帳（店舗）
				Keepb.setPcKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pckb")));	                                    //プライスシール発行区分
				Keepb.setDaisiNokb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daisinokb")));	                            //プライスシール種類
//				<!--   ↑↑2006.07.11 wangjm カスタマイズ修正↑↑  -->

			}

			Keepb.setMode(mst000401_LogicBean.chkNullString(dataHolder.getParameter("mode"))	);
			Keepb.setExistFlg("");	//データ存在(検索[ｷｬﾝｾﾙ]時)
			Keepb.setErrorBackDisp("");

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//メーニューバーアイコン取得
			// userSession取得
//			<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);
//			<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->



//					<!--   ↓↓2006.07.11 wangjm カスタマイズ修正↓↓  -->
//			//エラーチェック
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				List lst = new ArrayList();	//マスタ存在チェック抽出条件
//				String name = "";				//戻り値格納
//				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
//				String CtlName = "";
//				String chkDeg = "";
//				//検索条件処理チェック　/////////////////////////////////////////////////////////////////////
//				// 有効日範囲チェック生鮮用(マスタ日付＋2日より1年後まで入力可能)
//				// 照会時はチェックを行わない
//				String[] strReturn = new String[3];
//				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
//					strReturn = mst000401_LogicBean.getYukoDtRangeCheckFre(db, Keepb.getYukoDt());
//					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setFirstFocus("ct_yukodt");
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_yukodt");
//						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
//					}
//				}
//
//				//商品コード
//				CtlName = "ct_syohincd";
//				Keepb.setHinmeiKanji("");
//				//処理状況：【新規】
//				if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)){
//					if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH)){
//						if( Keepb.getSyohinCd().length() >= 12 && Keepb.getSyohinCd().substring(0,4).equals("0400")){
//							//品種の存在チェック
//							chkHinsyu( db, Keepb, CtrlColor, CtlName, sessionManager, map, Keepb.getSyohinCd().substring(4,8) );
//						}
//					}
//				}
//				//商品の存在チェック
//				String chkCyohinCd = "";
//				chkCyohinCd = getCheckDegit( Keepb.getSyohinCd(), Keepb.getSyohinCd().length() );
//				//チェックデジット
//				if(chkCyohinCd.equals("")){
//					//チェックデジットエラー
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage(map.get("40201").toString());
//					}
//				} else {
//					//商品の存在チェック
//					mstchk = getSyohin( db, chkCyohinCd ,"" ,Keepb.getGyosyuKb() ,"gyosyu_kb" ,Keepb.getYukoDt(), Keepb.getProcessingDivision());
//					if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)){
//						if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//							// 2006/02/06 ↓IF文追加削除フラグが０の場合のみ商品コード存在エラー
//							if(mstchk.getDeleteFg().equals(mst000101_ConstDictionary.DELETE_FG_NOR)){
//					 	    	//商品コード存在エラー
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
//							// 2006/02/06 ↑IF文追加
//						}
//					} else {
//						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//							//商品コード未存在エラー
//							mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setFirstFocus( CtlName );
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("0005").toString());
//							}
//						} else {
//							if(!Keepb.getGyosyuKb().equals( mstchk.getCdName().trim())){
//								//商品コード存在エラー（業種違い）
//								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//									Keepb.setFirstFocus( CtlName );
//									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//									Keepb.setErrorMessage(map.get("40026").toString());
//								}
//							}
//						}
//					}
//					mstchk = getSyohin( db, chkCyohinCd ,"" ,Keepb.getGyosyuKb() ,"hinmei_kanji_na" ,Keepb.getYukoDt(), Keepb.getProcessingDivision());
//					Keepb.setHinmeiKanji( mstchk.getCdName() );
//				}
//				//処理状況：【修正・削除】
//				if( (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
//					|| Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE))
//						&& Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL) ){
////					whereList.clear();
////					whereList.add(" syohin_cd = '" + chkCyohinCd + "' ");
////					whereList.add(" AND gyosyu_kb = '" + Keepb.getGyosyuKb() + "' ");
//
//					// ↓↓　2006/03/30 kim START
//					//生鮮の場合、販区を''にして有効日のチェックをする。
//					//keepbeanの中で既存販区の値が書き込んでいるので有効日のチェックする時、エラー出る。
//					Keepb.setHankuCd("");
//					// ↑↑　2006/03/30 kim END
//
//
//					//有効日チェック
//// 2006.02.09 Y.Inaba Mod ↓
////					String chks = mst001001_EffectiveDayBean.getYukoDtCheck(tableNa,columnNa,whereList,addValue,Keepb.getYukoDt(),Keepb,db);
////					String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck(tableNa, Keepb.getYukoDt(), Keepb, db, addValue);
//					String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck(tableNa, Keepb.getYukoDt(), Keepb, db, addValue,false);
//// 2006.02.09 Y.Inaba Mod ↑


			// userSession取得
			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");

			//エラーチェック
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				String name = "";				//戻り値格納
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				String CtlName = "";
				String chkDeg = "";
				Keepb.setTempBuMonCd("");

				//検索条件処理チェック　/////////////////////////////////////////////////////////////////////
				//部門コード存在チェック
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).equals("")){
					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)  + "' ");
					lst.add(" and ");
					lst.add("CODE_CD = '"  + mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("ct_bumoncd"), 4, "0", true)) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
				}
	            //部門コード存在エラー
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_bumoncd");
					Keepb.setFirstFocus("ct_bumoncd");
					Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());//は存在しません
				} else {
					Keepb.setBumonKanjiRn( mstchk.getCdName() );
				}
				// 有効日範囲チェック(マスタ日付より1年後まで入力可能)
				// 照会時はチェックを行わない
				String[] strReturn = new String[3];
				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
//					↓↓仕様変更（2005.08.19）↓↓
//					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
//					↑↑仕様変更（2005.08.19）↑↑
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("ct_yukodt");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_yukodt");
						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
					}
				}

				// 商品コード
				CtlName = "ct_syohincd";
				String syohinCd = null;
				String bumonCd = null;
				bumonCd = mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("ct_bumoncd"), 4, "0", true));
			   	Keepb.setHinmeiKanji( "" );
				if( !Keepb.getSyohinCd().equals("") ){
//					↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
					syohinCd = mst000401_LogicBean.formatZero(Keepb.getSyohinCd() ,13 );
					Keepb.setSyohinCd(syohinCd);
//					↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
					// 処理状況：【新規】
					if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)){
						if( Keepb.getSyohinCd().length() <= 3 ){
							syohinCd = mst000401_LogicBean.formatZero(Keepb.getSyohinCd() ,13 );
						} else if (Keepb.getSyohinCd().length() == 8 ){
							if("45".equals(Keepb.getSyohinCd().substring(0,2)) ||
									"49".equals(Keepb.getSyohinCd().substring(0,2))){
								// 前2桁が "45" "49" のとき前ゼロを5つでマスタ存在チェック
								syohinCd = mst000401_LogicBean.formatZero(Keepb.getSyohinCd() ,13 );
//							↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
//							} else if (Integer.parseInt(Keepb.getSyohinCd().substring(0,1)) > 3){
							} else if (Integer.parseInt(Keepb.getSyohinCd().substring(0,1)) >= 3){
//							↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
								// 前1桁が　"3" 以上のとき前ゼロ5つでマスタ存在チェック
								syohinCd = mst000401_LogicBean.formatZero(Keepb.getSyohinCd() ,13 );
//							↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
							} else {
								syohinCd = mst000401_LogicBean.formatZero(Keepb.getSyohinCd() ,13 );
//							↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
							}
						} else {
							syohinCd = Keepb.getSyohinCd();
						}
						// 商品マスタの存在チェック
						mstchk = getSyohin( db, syohinCd, bumonCd ,mst000611_SystemKbDictionary.F.getCode() ,"hinmei_kanji_na" ,Keepb.getYukoDt(), Keepb.getProcessingDivision());
						if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							//商品コード存在(有)エラー
							mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus( CtlName );
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("40001").toString());//指定されたデータは既に存在しています。
							}
						}
						if (Keepb.getSyohinCd().length() == 13 ){
							// 商品マスタの存在チェック
							syohinCd = Keepb.getSyohinCd();
						}
						mstchk = getSyohin(db, syohinCd, bumonCd, mst000611_SystemKbDictionary.F.getCode(), "hinmei_kanji_na", Keepb.getYukoDt(), Keepb.getProcessingDivision());
						Keepb.setHinmeiKanji( mstchk.getCdName() );
				    } else {
					// 処理状況：【新規以外】
					  if (Keepb.getSyohinCd().length() == 13 ){
							// 商品マスタの存在チェック
							syohinCd = Keepb.getSyohinCd();
							mstchk = getSyohin(db, syohinCd, bumonCd, mst000611_SystemKbDictionary.F.getCode(), "hinmei_kanji_na", Keepb.getYukoDt(), Keepb.getProcessingDivision());
							Keepb.setHinmeiKanji( mstchk.getCdName() );
							//商品コード存在(無)エラー
							if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
								//商品コード存在エラー
								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus( CtlName );
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("0005").toString());//検索データが存在しません。検索条件を変えて再度実行して下さい。
								}
							}
				       }
			        }
				// チェックデジットのチェック
//				↓↓2006.10.17 H.Yamamoto カスタマイズ修正↓↓
//				if (Keepb.getSyohinCd().length() == 13 && !("0000000000".equals(Keepb.getSyohinCd().substring(0,10)))){
				String syohinCheckCd = Keepb.getSyohinCd();
				boolean syohinCheckFg = false;
				if (Keepb.getSyohinCd().length() == 13){
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
//				↑↑2006.10.17 H.Yamamoto カスタマイズ修正↑↑
					chkDeg = mst001401_CheckDegitBean.getModulus10( Keepb.getSyohinCd().substring(0,12) ,12 );
					if( chkDeg == null || !chkDeg.equals( Keepb.getSyohinCd().substring(12,13) )){
						mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus( CtlName );
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40201").toString());
						}
					}
				}

				// ===BEGIN=== Add by kou 2006/10/27
				if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ) {
					// 商品コードの重複チェック
					StringBuffer sql = new StringBuffer();
					sql.append("select syohin_cd from r_syohin rs ");
					sql.append(" where syohin_cd = '")
						.append(Keepb.getSyohinCd()).append("' ");
					sql.append(" 	and bumon_cd <> '")
						.append(Keepb.getBumonCd()).append("' ");
					sql.append("   and yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, '")
						.append(Keepb.getYukoDt()).append("') ");
					sql.append(" fetch first 1 rows only ");
//					↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
					sql.append(" for read only ");
//					↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
					ResultSet rs = db.executeQuery(sql.toString());
					if(rs.next()){
						String ctlName = "ct_syohincd";
						mst000401_LogicBean.setErrorBackColor(CtrlColor, ctlName);
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus(ctlName);
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage( map.get("em000013").toString());
						}
					}
					rs.close();
				}
				// ===END=== Add by kou 2006/10/27

		   }

				//処理状況：【修正・削除】
				if( (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
					|| Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE))
						&& Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL) ){
					whereList.clear();
					whereList.add(" bumon_cd = '" + bumonCd + "' ");
					whereList.add(" AND syohin_cd = '" + Keepb.getSyohinCd() + "' ");
					whereList.add(" AND system_kb = '" + mst000611_SystemKbDictionary.F.getCode() + "' ");

					//有効日チェック
					String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck(tableNa,Keepb.getYukoDt(),Keepb,db,"0",false);
//					<!--   ↑↑2006.07.11 wangjm カスタマイズ修正↑↑  -->


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
			}
			//検索条件処理チェック   END ////////////////////////////////////////////////////////////////
			if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
//				Keepb.setErrorBackDisp(mst000101_ConstDictionary.ERR_CHK_ERROR);
			}

			if( Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) ){
				//更新処理処理チェック   //////////////////////
				UpdateCheck( db, Keepb, CtrlColor, sessionManager, map );
			}
		}

	/**
	 * 指定商品マスタの取得を行う。
	 * @param		mst000101_DbmsBean db		: DBインスタンス
	 * @param		String syohinCd				: 商品コード
	 * @param		String hankuCd				: 販区コード
	 * @param		String systemKb 			: システム区分
	 * @param		String columnNa				: 取得カラム名
	 * @return		mst000701_MasterInfoBean 	: 取得結果
	 */
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ修正↓↓  -->
	public mst000701_MasterInfoBean getSyohin( mst000101_DbmsBean db, String syohinCd, String bumonCd, String systemKb,
			String columnNa, String yukoDt, String syoriKb)
			 throws Exception,SQLException {
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ修正↑↑  -->


		mst110101_SyohinDM 	 	 dm  = new mst110101_SyohinDM();

		mst000701_MasterInfoBean ret = new mst000701_MasterInfoBean();

		ret.setCdName("");
		ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);
//		String MSTDATE = RMSTDATEUtil.getMstDateDt();
//		String yuko_Dt = "";


		Map selectMap  = new HashMap();
		selectMap.put("syohin_cd",syohinCd);
//		<!--   ↓↓2006.07.11 wangjm カスタマイズ修正↓↓  -->
//		if(!hankuCd.equals("")){
//			  selectMap.put("hanku_cd",hankuCd);
//			}
		selectMap.put("bumon_cd",bumonCd);
//		<!--   ↑↑2006.07.11 wangjm カスタマイズ修正↑↑  -->


		selectMap.put("system_kb",systemKb);
		selectMap.put("yuko_dt",yukoDt);

		String MSTDATE = null;
		ResultSet rset = null;    //抽出結果(ResultSet)
		rset = db.executeQuery(dm.getSelectSql(selectMap));

		if(rset.next()){
			ret.setCdName(mst000401_LogicBean.chkNullString(rset.getString(columnNa)).trim());
			ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
		} else {
			//===BEGIN=== Add by kou 2006/11/24
			if(!mst000401_LogicBean.chkNullString(yukoDt).equals("")){
				MSTDATE = yukoDt;
			}
			//===END=== Add by kou 2006/11/24
			//商品(現在有効)の存在チェック
			List whereList = new ArrayList();
			whereList.clear();
			whereList.add("  syohin_cd = '" + syohinCd + "' ");
//			if(!hankuCd.equals("")){
//				whereList.add(" AND hanku_cd = '" + hankuCd + "' ");
//			}
			whereList.add(" AND bumon_cd = '" + bumonCd + "' ");
			whereList.add(" AND system_kb = '" + systemKb + "' ");
//// 2005.11.30 Y.Inaba 処理区分により処理を分岐 START
//			//現在有効日を取得
			if(syoriKb.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)
				&& (yukoDt != null) && (!yukoDt.trim().equals(""))){
				MSTDATE = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, "r_syohin","yuko_dt",whereList, MSTDATE, false);
			} else{
				MSTDATE = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, "r_syohin","yuko_dt",whereList, MSTDATE, true);
			}
// 2005.11.30 Y.Inaba 処理区分により処理を分岐 END
			if(!MSTDATE.trim().equals("")){
				selectMap.put("yuko_dt",MSTDATE);
				ResultSet rset1 = null;    //抽出結果(ResultSet)
				rset1 = db.executeQuery(dm.getSelectSql(selectMap));
				if(rset1.next()){
					ret.setCdName(mst000401_LogicBean.chkNullString(rset1.getString(columnNa)).trim());
					ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
				}
			}else{
				ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);
			}

		}
		return ret;
	}

//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->.//	/**
//	 * 品種のチェックと販売区分の取得を行う。
//	 * @param		mst000101_DbmsBean db		: DBインスタンス
//	 * @param		String syohinCd				: 商品コード
//	 * @param		String hankuCd				: 販区コード
//	 * @param		String gyosyuKbr 			: 業種区分
//	 * @param		String columnNa				: 取得カラム名
//	 * @return		mst000701_MasterInfoBean 	: 取得結果
//	 */
//	public void chkHinsyu( mst000101_DbmsBean db, mst150201_KeepBean Keepb, Map CtrlColor, String CtlName,
//									 SessionManager  sessionManager, TreeMap map, String hinsyuCd) throws Exception,SQLException {
//
//		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
//		List lst = new ArrayList();
//		lst.clear();
//		lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' ");
//		lst.add(" and ");
//		lst.add("code_cd = '" + hinsyuCd + "' ");
////2006.02.09 Y.Inaba Mod ↓
////		mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
////2006.02.09 Y.Inaba Mod ↑
//		Keepb.setHinsyuNa( mstchk.getCdName() );
//		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				//品種コード存在エラー
//				mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					Keepb.setFirstFocus( CtlName );
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					Keepb.setErrorMessage(map.get("0315").toString());
//				}
//			} else {
//				//ログインユーザの売区とのチェック
//				if(!mst000401_LogicBean.getHinshuCdCheck(db,hinsyuCd,sessionManager)){
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage(map.get("40023").toString());
//					}
//				}
//			}
//		}
//		//販区の取得（品種入力時に商品体系マスタより取得）
//		Keepb.setHankuCd( getSyohinTaikei(db,hinsyuCd,"hanku_cd") );
//		//販区名称取得
//		lst.clear();
//		lst.add("SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");
//		lst.add(" and ");
//		lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
////2006.02.09 Y.Inaba Mod ↓
////		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt());
////2006.02.09 Y.Inaba Mod ↑
//		Keepb.setHankuKanjiRn(mstchk.getCdName());
//		//販区チェック
//		if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){
//			//処理状況：修正
//			if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
//				mstchk = getSyohin( db, getCheckDegit( Keepb.getSyohinCd(), Keepb.getSyohinCd().length()) ,"" ,Keepb.getGyosyuKb() ,"hanku_cd" ,Keepb.getYukoDt(), Keepb.getProcessingDivision());
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)
//						|| !Keepb.getHankuCd().equals(mstchk.getCdName().trim())){
//					//商品コード存在無しエラーor販区が変更されたエラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage(map.get("40023").toString());
//					}
//				}
//			}
//		}
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->




//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	/**
//	 * 商品体系マスタより指定カラムの値を取得を行う。
//	 * @param		mst000101_DbmsBean 		: dbインスタンス
//	 * @param		String hinsyuCd			: 品種コード
//	 * @param		String Column			: 指定カラム名
//	 * @return		String 					:値
//	 */
//	public String  getSyohinTaikei( mst000101_DbmsBean db, String hinsyuCd, String Column )  throws Exception,SQLException {
//		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
//		List lst = new ArrayList();
//
//		lst.clear();
//		lst.add("hinsyu_cd = '" + hinsyuCd + "' ");
//		mstchk = mst000401_LogicBean.getSyohinTaikei(db,Column, lst );
//		return  mstchk.getCdName();
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->



//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->.
//	/**
//	 * 指定商品のチェックデジットのチェック＆取得を行う。
//	 * @param		String syohinCd		: 商品コード
//	 * @param		int len   			: 商品コード入力桁数
//	 * @return		String 				: "":エラー,""以外:商品コード
//	 */
//	public String getCheckDegit( String syohinCd , int len) {
//
//		String ret = syohinCd;
//		String chkDeg = "";
//		//7桁は前0削除
//		if(len == 7 ){
//			syohinCd = syohinCd.substring(1).trim();
//		}
//		//04xx入力時の処理
//		if(len == 2 || len == 4){
//			if(syohinCd.substring(0,2).equals("04")){
//				if(syohinCd.length() > 4 && syohinCd.length() < 12){
//					chkDeg = mst001401_CheckDegitBean.getModulus11( syohinCd.substring(4,11) ,7 );
//					syohinCd = syohinCd.trim() + chkDeg.trim();
//				}
//			}
//		}
//		switch (syohinCd.length()) {
//			case 8:
//			chkDeg = mst001401_CheckDegitBean.getModulus10( "0000"+syohinCd.substring(0,7) ,12 );
//				if(!chkDeg.equals(syohinCd.substring(7))){
//					ret = "";
//				}
//				break;
//			case 10:
//				syohinCd = "00" + syohinCd.trim();
//				chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.trim() ,12 );
//				syohinCd = syohinCd.trim() + chkDeg.trim();
//				break;
//			case 11:
//				syohinCd = "0" + syohinCd.trim();
//				chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.trim() ,12 );
//				syohinCd = syohinCd.trim() + chkDeg.trim();
//				break;
//			case 12:
//				if( syohinCd.substring(0,4).equals("0400")){
//					chkDeg = mst001401_CheckDegitBean.getModulus11( syohinCd.substring(4,11) ,7 );
//					if(!chkDeg.equals(syohinCd.substring(11,12))){
//						ret = "";
//					}
//					chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
//					syohinCd = syohinCd.trim() + chkDeg.trim();
//				} else {
//					if(len > 2){
//						syohinCd = "0" + syohinCd.trim();
//						chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
//						if(!chkDeg.equals(syohinCd.substring(12,13))){
//							ret = "";
//						}
//					} else {
//						chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
//						syohinCd = syohinCd.trim() + chkDeg.trim();
//					}
//				}
//				break;
//			case 13:
//				if( syohinCd.substring(0,4).equals("0400")){
//					chkDeg = mst001401_CheckDegitBean.getModulus11( syohinCd.substring(4,11) ,7 );
//					if(!chkDeg.equals(syohinCd.substring(11,12))){
//						ret = "";
//					}
//				}
//				chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
//				if(!chkDeg.equals(syohinCd.substring(12))){
//					ret = "";
//				}
//				break;
//			default:break;
//		}
//		if(!ret.equals("")){
//			ret = syohinCd;
//		}
//
//		return ret;
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->


	/**
	 *
	 * 新規/修正時の入力項目のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst310201_KeepBean mst310201_KeepBean 	: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */
	public void  UpdateCheck( mst000101_DbmsBean db, mst150201_KeepBean Keepb, Map CtrlColor,
							 SessionManager  sessionManager, TreeMap map ) throws Exception,SQLException {
//		<!--   ↓↓2006.07.11 wangjm カスタマイズ修正↓↓  -->
//		List lst = new ArrayList();	//マスタ存在チェック抽出条件
//		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
//		String CtlName = "";
//		String chkDeg = "";
//		//〔品種コード〕
//		CtlName = "ct_hinsyucd";
//		chkHinsyu( db, Keepb, CtrlColor, CtlName, sessionManager, map, Keepb.getHinsyuCd());
//		//〔品目〕
//		CtlName = "ct_hinmokucd";
//		if(!Keepb.getHinmokuCd().equals("")){
//			//品目の存在チェック
//			lst.clear();
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.KIND_AND_ARTICLE + "' ");
//			lst.add(" and ");
//			lst.add("code_cd = '" + Keepb.getHinsyuCd() + Keepb.getHinmokuCd() + "' ");
//// 2006.02.09 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.02.09 Y.Inaba Mod ↑
//			Keepb.setHinmokuNa( mstchk.getCdName() );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//品目コード存在エラー
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された品目"+map.get("40007").toString());
//					}
//				}
//			}
//		} else {
//			Keepb.setHinmokuNa( "" );
//		}
//		//〔JANメーカーコード〕
//		CtlName = "ct_makercd";
//		if(!Keepb.getMakerCd().equals("")){
//			//JANメーカーコードの存在チェック
//			lst.clear();
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.JAN_MAKER_NAME + "' ");
//			lst.add(" and ");
//			lst.add("code_cd = '" + Keepb.getMakerCd() + "' ");
//// 2006.02.09 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.02.09 Y.Inaba Mod ↑
//			Keepb.setMakerNa( mstchk.getCdName() );
//// ↓↓仕様変更（2005.07.25）↓↓
////			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
////				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
////					//JANメーカーコード存在エラー
////					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
////					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
////						Keepb.setFirstFocus( CtlName );
////						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////						Keepb.setErrorMessage("指定されたJANメーカーコード" + map.get("40007").toString());
////					}
////				}
////			}
//// ↑↑仕様変更（2005.07.25）↑↑
//		} else {
//			Keepb.setMakerNa( "" );
//		}
//		//〔産地〕
//		CtlName = "ct_santicd";
//		if(!Keepb.getSantiCd().equals("")){
//			//産地の存在チェック
//			lst.clear();
////↓↓仕様変更(2005.09.19)
////			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.COUNTRY + "' ");
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.HOME_NUMBER + "' ");
////↑↑仕様変更(2005.09.19)
//			lst.add(" and ");
//			lst.add("code_cd = '" + Keepb.getSantiCd() + "' ");
//// 2006.02.09 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.02.09 Y.Inaba Mod ↑
//			Keepb.setSantiNa( mstchk.getCdName() );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//産地コード存在エラー
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された産地"+map.get("40007").toString());
//					}
//				}
//			}
//		} else {
//			Keepb.setSantiNa( "" );
//		}
//		//〔店別仕入先管理コード〕
//		CtlName = "ct_tensiiresakikanricd";
//		if(!Keepb.getTenSiiresakiKanriCd().equals("")){
//			lst.clear();
//			lst.add( " kanri_kb = '" +  mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' "  );
//			lst.add( " and " );
//			lst.add( " kanri_cd = '" + getSyohinTaikei(db,Keepb.getHinsyuCd(),"d_gyosyu_cd") + "' "  );
//			lst.add( " and " );
//			lst.add( " tenpo_cd = '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' " );
//			lst.add( " and " );
//// ↓↓仕様変更（2005.07.22）↓↓
////			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' " );
//// ↓↓仕様変更（2005.07.25）↓↓
////			lst.add( " ten_siiresaki_kanri_cd = '" + "0000" + Keepb.getTenSiiresakiKanriCd() + "' " );
//			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' " );
//// ↑↑仕様変更（2005.07.25）↑↑
//// ↑↑仕様変更（2005.07.22）↑↑
//// ↓↓仕様変更（2005.08.24）↓↓
//			lst.add( " and " );
//			lst.add( " tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' " );
//// ↑↑仕様変更（2005.08.24）↑↑
//// 2006.02.09 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db, "vw_r_tensiiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
//			mstchk = mst000401_LogicBean.getMasterCdName(db, "vw_r_tensiiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt());
//// 2006.02.09 Y.Inaba Mod ↑
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
//			lst.add("code_cd = '" + "0000" + Keepb.getTenSiiresakiKanriCd() + "' ");
//// ↑↑仕様変更（2005.07.13）↑↑
//// 2006.02.09 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db, "r_namectf", "kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt());
//// 2006.02.09 Y.Inaba Mod ↑
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
//// ↑↑仕様変更（2005.07.26）↑↑
//		} else {
//			Keepb.setTenSiiresakiKanriNa( "" );
//		}
///*
//		//〔配送先(店別配送先管理コード)〕
//		CtlName = "ct_tenhaisokanricd";
//		if(!Keepb.getTenHaisoKanriCd().equals("")){
//			lst.clear();
//			lst.add( " kanri_kb = '" +  mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' "  );
//			lst.add( " and " );
//			lst.add( " kanri_cd = '" + getSyohinTaikei(db,Keepb.getHinsyuCd(),"d_gyosyu_cd") + "' "  );
//			lst.add(" and ");
//			lst.add( "haisosaki_cd = '" + Keepb.getTenHaisoKanriCd()+"0" + "' " );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_haisou","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//コード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された配送先"+map.get("40007").toString());
//					}
//				}
//			}
//			//名称取得
//			lst.clear();
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP + "' ");
//			lst.add(" and ");
//			lst.add("code_cd = '" + Keepb.getTenHaisoKanriCd() + "' ");
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			Keepb.setTenHaisoKanriNa( mstchk.getCdName() );
//		} else {
//			Keepb.setTenHaisoKanriNa( "" );
//		}
//*/
////↓↓仕様変更（2005.08.24）↓↓
////		//処理状況：【修正】のみ
////		if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
////			&& !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS) ){
////			String syohin = getCheckDegit( Keepb.getSyohinCd(), Keepb.getSyohinCd().length() );
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
////			lst.add( " HANKU_CD = '" + Keepb.getHankuCd() + "' ");
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
////			lst.add( " HANKU_CD = '" + Keepb.getHankuCd() + "' ");
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
//		if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				boolean genka = false; //原価単価(税抜) ｾﾞﾛ判定
//				boolean baika = false; //売価単価（税込） ｾﾞﾛ判定
//				if(Keepb.getGentankaSenVl().trim().equals("") || Integer.parseInt(Keepb.getGentankaSenVl()) == 0){
//					if( Integer.parseInt(mst000401_LogicBean.removeComma(Keepb.getGentankaVl())) == 0){
//						genka = true;
//					}
//				}
//				if( Integer.parseInt(mst000401_LogicBean.removeComma(Keepb.getBaitankaVl())) == 0){
//					baika = true;
//				}
//				if((genka && baika) || (!genka && baika)){
//					lst.clear();
//					lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.SUB_KIND + "' ");
//					lst.add(" and ");
//					lst.add("code_cd = '" + Keepb.getHinsyuCd() + "' ");
//// 2006.02.09 Y.Inaba Mod ↓
////					mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.02.09 Y.Inaba Mod ↑
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					if(genka){
//						mst000401_LogicBean.setErrorBackColor( CtrlColor, "ct_gentankavl" );
//					} else {
//						mst000401_LogicBean.setErrorBackColor( CtrlColor, "ct_baitankavl" );
//					}
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						if(genka){
//							Keepb.setErrorMessage("原価単価(税抜)"+map.get("0203").toString());
//						} else {
//							Keepb.setErrorMessage("売価単価(税込)"+map.get("0203").toString());
//						}
//					}
//				}
//			}
//		}
//	}
//
//		//〔ブランド区分〕
//		CtlName = "ct_blandcd";
//		if(!Keepb.getBlandCd().equals("")){
//			//ブランド区分の存在チェック
//			lst.clear();
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.BRAND + "' ");
//			lst.add(" and ");
//// ↓↓ブランドコード仕様変更対応(2005.09.26)
////			↓↓仕様変更（2005.07.22）↓↓
////			lst.add("code_cd = '" + Keepb.getBlandCd() + "' ");
////			lst.add("code_cd = '" + "0000" + Keepb.getBlandCd() + "' ");
//			lst.add("trim(code_cd) = '" + mst000101_ConstDictionary.DUMMY_CD + Keepb.getBlandCd() + "' ");
////			↑↑仕様変更（2005.07.22）↑↑
//// ↑↑ブランドコード仕様変更対応(2005.09.26)
//// 2006.02.09 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.02.09 Y.Inaba Mod ↑
//			Keepb.setBlandNa( mstchk.getCdName() );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//ブランド区分存在エラー
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定されたブランド区分"+map.get("40007").toString());
//					}
//				}
//			}
//		} else {
//			Keepb.setBlandNa( "" );
//		}
//// 2005.05.30 Inaba 発注商品コード、システム区分追加 START
//		 //〔発注商品コード区分〕
//		 Keepb.setHacyuSyohinKb(mst006001_HacyuSyohinKbDictionary.JAN_CD.getCode());
//		 //〔システム区分〕
//		   lst.clear();
//		   lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' ");
//		   lst.add(" and ");
//		   lst.add("code_cd = '" + Keepb.getHankuCd() + "' ");
//// 2006.02.09 Y.Inaba Mod ↓
////		   mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","zokusei_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//		   mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","zokusei_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//// 2006.02.09 Y.Inaba Mod ↑
//		   Keepb.setSystemKb( mstchk.getCdName() );
//// 2005.05.30 Inaba 発注商品コード、システム区分追加 END
////		↓↓仕様変更（2005.09.28）↓↓
////		//〔代表配送先コード〕
////		CtlName = "ct_daihyohaisocd";
////		int cnt = 0; //不要ＳＱＬ文の削除用カウンター
////		if(!Keepb.getDaihyoHaisoCd().equals("")){
////			lst.clear();
//////		↓↓仕様追加による変更（2005.06.01）
//////			lst.add(" kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//////			lst.add(" and ");
//////			lst.add(" kanri_cd = '" + Keepb.getHankuCd() + "' ");
////			lst.add(" kanri_kb = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");
////			lst.add(" and ");
////			lst.add(" kanri_cd = '" + getSyohinTaikei(db,Keepb.getHinsyuCd(),"d_gyosyu_cd") + "' "  );
//////		↑↑仕様追加による変更（2005.06.01）
////			lst.add(" and ");
////			lst.add( "haisosaki_cd = '" + Keepb.getDaihyoHaisoCd()+"0" + "' " );
//////		↓↓仕様追加による変更（2005.06.01）
////			cnt = lst.size(); //これ以降のＳＱＬ文は削除対象になる。
////			lst.add(" and ");
////			lst.add(" tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//////		↑↑仕様追加による変更（2005.06.01）
////			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_haisou","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
////			Keepb.setDaihyoHaisoNa( mstchk.getCdName() );
////			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//////			↓↓仕様追加による変更（2005.06.01）
//////				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
////				for (int i = lst.size() - 1; i >= cnt; i--) {
////					//余分なＳＱＬ文を削除
////					lst.remove(i);
////				}
////				if(!mst000401_LogicBean.getMasterChk(db,"r_tenbetu_haisosaki", lst  )){
//////			↑↑仕様追加による変更（2005.06.01）
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
////		↑↑仕様変更（2005.09.28）↑↑
//		if(Keepb.getDaihyoHaisoCd().equals("")){
//			Keepb.setDaihyoHaisoNa("");
//		}
//
//		//ログインユーザー情報
//		mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();
//		SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
///*		//〔配列１〕
//		if(!Keepb.getHairetuCd1().equals("") && IsNum(Keepb.getHairetuCd1())){
//			CtlName = "ct_tengroupno1cd";
//			lst.clear();
//			lst.add("l_gyosyu_cd = '" + SysUserBean.getGyosyuCd() + "' ");
//			lst.add(" and ");
//			lst.add("yoto_kb = '" + mst003901_YotoKbDictionary.BUTURYU.getCode() + "' ");
//			lst.add(" and ");
//			lst.add( "groupno_cd = '" + Keepb.getHairetuCd1() + "' " );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_tengroupno","name_na", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			Keepb.setHairetuNm1( mstchk.getCdName() );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//配列１存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("配列１"+map.get("40007").toString());
//					}
//				}
//			}
//		} else {
//			Keepb.setHairetuNm1( "" );
//		}
//		//〔配列２〕
//		if(!Keepb.getHairetuCd2().equals("") && IsNum(Keepb.getHairetuCd2())){
//			CtlName = "ct_tengroupno2cd";
//			lst.clear();
//			lst.add("l_gyosyu_cd = '" + SysUserBean.getGyosyuCd() + "' ");
//			lst.add(" and ");
//			lst.add("yoto_kb = '" + mst003901_YotoKbDictionary.BUTURYU.getCode() + "' ");
//			lst.add(" and ");
//			lst.add( "groupno_cd = '" + Keepb.getHairetuCd2() + "' " );
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_tengroupno","name_na", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			Keepb.setHairetuNm2( mstchk.getCdName() );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//配列２存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("配列２"+map.get("40007").toString());
//					}
//				}
//			}
//		} else {
//			Keepb.setHairetuNm2( "" );
//		}
//*/
//		//〔納品商品コード〕
//		CtlName = "ct_nohinsyohincd";
//		if(!Keepb.getNohinSyohinCd().equals("") && !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//			if(Keepb.getNohinSyohinCd().length() == 8 || Keepb.getNohinSyohinCd().length() == 13){
//				if( getCheckDegit(Keepb.getNohinSyohinCd(), Keepb.getNohinSyohinCd().length()).equals("") ){
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("納品商品コード" + map.get("40219").toString());
//					}
//				}
//			}
//		}
//// ↓↓バグ修正（2005.08.10）↓↓
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
// ↑↑バグ修正（2005.08.10）↑↑
		List lst = new ArrayList();	//マスタ存在チェック抽出条件
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
		String CtlName = "";
		String chkDeg = "";

		String setBuMonCd = "";
		if(!"".equals(Keepb.getBumonCd())){
			setBuMonCd =mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true));	//部門コード
		}

		//〔部門・ユニットコード〕
		CtlName = "ct_unitcd";
		StringBuffer sb = new StringBuffer();
		ResultSet rst = null;
		sb.append(" select ");
		sb.append(" bumon_cd ");
		sb.append(" from ");
		sb.append("		r_syohin_taikei ");
		sb.append("		where ");
		sb.append("system_kb = '" + sessionManager.getSession().getAttribute("mdwareSystemKb") + "' ");
		sb.append(" and ");
		sb.append("unit_cd = '" + Keepb.getUnit() + "' ");
		sb.append(" and ");
		sb.append("bumon_cd = '" + setBuMonCd + "' ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
		rst = db.executeQuery(sb.toString());
		if (!rst.next()) {
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				//部門コード存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("商品体系マスタの部門・ユニットのコードが不正です。");
				}
			}
		}
		if (rst != null) {
			rst.close();
		}

		//〔ユニットコード〕
		CtlName = "ct_unitcd";
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		lst.clear();
		lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
		lst.add(" and ");
		lst.add("CODE_CD = '" + sessionManager.getSession().getAttribute("mdwareSystemKb") + Keepb.getUnit()+ "' ");
		mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
		Keepb.setUnit_Na( mstchk.getCdName() );
		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				//ユニットコード存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定されたユニットコード"+map.get("40007").toString());		//は存在しません。
				}
			}
		}

		//〔バイヤーNo〕
		CtlName = "ct_userid";
		sb = new StringBuffer();
//		sb.append(" select ");
//		sb.append(" riyo_user_na as riyo_user_na ");
//		sb.append(" from ");
//		sb.append("		r_riyo_user ");
//		sb.append("		where ");
//		sb.append("riyo_user_id = '" + "0000" + Keepb.getUserId() + "' ");
		sb.append(" select ");
		sb.append(" user_na as riyo_user_na ");
		sb.append(" from ");
		sb.append("		r_portal_user ");
		sb.append("		where ");
		sb.append(" user_id = '" + "0000" + Keepb.getUserId() + "' ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
		rst = db.executeQuery(sb.toString());
//		<!--   ↓↓2006.07.17 wangjm カスタマイズ削除↓↓  -->
		if (!rst.next()) {
//		Keepb.setSiiresakiNa(mst000401_LogicBean.chkNullString(rst.getString("riyo_user_na")));
//	}else{
//		<!--   ↑↑2006.07.17 wangjm カスタマイズ削除↑↑  -->

			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				//バイヤーNo存在エラー
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定されたバイヤーNo"+map.get("40007").toString());
//				}
				}
			}
		}
		if (rst != null) {
			rst.close();
		}

		//〔JANメーカーコード〕
		CtlName = "ct_makercd";
		if(!Keepb.getMakerCd().equals("")&&Keepb.getMakerCd() != null){
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.JAN_MAKER_NAME, userLocal) + "' ");
			lst.add(" and ");
			lst.add("CODE_CD = '" + Keepb.getMakerCd()+ "' ");
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
			Keepb.setMaker_Na( mstchk.getCdName() );
//			↓↓2006.11.22 H.Yamamoto カスタマイズ修正↓↓
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//JANメーカーコード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定されたJANメーカーコード"+map.get("40007").toString());
//					}
//				}
//			}
//			↑↑2006.11.22 H.Yamamoto カスタマイズ修正↑↑
		} else {
			Keepb.setMaker_Na("");
		}
//		//〔地区コード〕
//		CtlName = "ct_areacd";
//		lst.clear();
//		lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.AREA_CODE + "' ");
//		lst.add(" and ");
//		lst.add("CODE_CD = '" + Keepb.getArea_Cd()+ "' ");
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
//		Keepb.setSiiresakiNa( mstchk.getCdName() );
//		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				//地区コード存在エラー
//				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					Keepb.setFirstFocus(CtlName);
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					Keepb.setErrorMessage("指定された地区コード"+map.get("40007").toString());
//				}
//			}
//		}
//
//		↓↓2006.08.28 kema カスタマイズ修正↓↓
		//〔PLU送信日〕新規変更時のみ
		if( (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
			Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE) ) &&
			!Keepb.getPluSendDt().equals("") ) {
			if(Long.parseLong(Keepb.getPluSendDt()) < Long.parseLong(RMSTDATEUtil.getMstDateDt())){
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setFirstFocus("ct_plusenddt");
				mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_plusenddt");
				Keepb.setErrorMessage("PLU送信日はマスタ管理日付以降の日を指定してください。");
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
//			↓↓仕様追加による変更（2006.09.14） by kema
			if(Keepb.getArea_Cd().length() == 0 ){
				sb.append( "SUBSTRING(SIIRESAKI_CD,1,6) = '" + Keepb.getSiiresakiCd() + "' " );
			}else{
				sb.append( "SIIRESAKI_CD = '" + Keepb.getSiiresakiCd() + Keepb.getArea_Cd() + "' " );
			}
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
					Keepb.setSiiresakiNa(rest.getString("KANJI_RN"));
//					↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
//					if(rest.getString("SIIRE_SYSTEM_KB") != null && rest.getString("SIIRE_SYSTEM_KB").equals("2")){
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
		//〔店別仕入先管理コード〕9999はフリー入力 by kema 06.09.13
		CtlName = "ct_tensiiresakikanricd";
		if(!Keepb.getTenSiiresakiKanriCd().equals("") && !Keepb.getTenSiiresakiKanriCd().equals("9999")){
			lst.clear();
			lst.add( " kanri_kb = '" +  mst000901_KanriKbDictionary.BUMON.getCode() + "' "  );
			lst.add( " and " );
			lst.add( " kanri_cd = '" + setBuMonCd + "' "  );
			lst.add( " and " );
			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd()+ "' " );
			// ===BEGIN=== Comment out by kou 2006/10/12
			//代表仕入先の考え方を無くなりました
			//lst.add( " and " );
			//lst.add( " TENPO_CD = '000000'" );
			// ===END=== Comment out by kou 2006/10/12
			mstchk = mst000401_LogicBean.getMasterCdName(db, "r_tenbetu_siiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt());

			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					//コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたメーカーコード"+map.get("40007").toString());
					}

				}

			}
			//名称取得　　〔店別仕入先コード〕
			lst.clear();
//			↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP + "' ");
//			lst.add(" and ");
//
//			lst.add("code_cd = '" +setBuMonCd + Keepb.getTenSiiresakiKanriCd() + "' ");
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.MAKER_NAME, userLocal) + "' ");
			lst.add(" and ");

			lst.add("code_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' ");
//			↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑

			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );

			Keepb.setTenSiiresakiKanriNa( mstchk.getCdName() );

			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//仕入先コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたメーカーコード"+map.get("40007").toString());
					}
				}
			}

		} else {
			Keepb.setTenSiiresakiKanriNa( "" );
		}

	}

//		<!--   ↑↑2006.07.11 wangjm カスタマイズ修正↑↑  -->

//
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	/**
//	 *  数値チェック
//	 *    項目が半角0-9か判断する
//	 *
//	 *  @param    strNum  入力パラメータ
//	 *  @return   boolean  有効 true 無効 false
//	 */
//	   public boolean IsNum(String strInString) {
//		//文字列の長さ分繰り返し
//		int intChk;
//		intChk = 0;
//		char c1 =  '0';
//		char c2 =  '9';
//		 for (int i = 0; i < strInString.length(); i++){
//			  char c  =  strInString.charAt(i);
//			  if (c < c1 || c > c2)
//				 {intChk = intChk + 1;}
//		 }
//		 if (intChk == 0){
//			return true;
//		 } else {
//			return false;
//		 }
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
//
//

}