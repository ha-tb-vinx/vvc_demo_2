/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/04/08)初版作成
 * @version 1.1(2005/05/19)新ER対応 Y.Inaba
 * 　　　　　削除…店配送先管理コード(ten_haiso_kanri_cd)
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
public class mst130301_CheckBean
{
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param		mst000101_DbmsBean 	db					: DBインスタンス作成
	 * @param		mst310201_KeepBean 	mst310201_KeepBean 	: 画面情報
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
	public void Check(
		mst000101_DbmsBean db,
		mst130201_KeepBean Keepb ,
		DataHolder dataHolder ,
// ↓↓2006.06.27 xubq カスタマイズ修正↓↓
            // String kengenCd ,
// ↑↑2006.06.27 xubq カスタマイズ修正↑↑
 	    String GamenId ,
		String FirstFocusCtl ,
		String[] CtrlName ,
		Map CtrlColor ,
		String tableNa ,
//		String columnNa ,
		List whereList ,
//		String addValue,
		SessionManager  sessionManager ) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");
		Keepb.setSystemKb(String.valueOf(sessionManager.getSession().getAttribute("mdwareSystemKb")));
		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){

			if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT) ){
				//画面内容を保存
//              ↓↓2006.06.27 xubq カスタマイズ修正↓↓
//	            Keepb.setHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")));						//販区コード
//	            Keepb.setHankuKanjiRn(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankuna"))));				//販区名
				Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd").trim()));	//部門コード
				Keepb.setBuMonKanjiRn(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumonna"))));				//部門名
                Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")));					//商品コード
				Keepb.setHinmeiKanji(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohinna")));				//商品名
				Keepb.setYukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yukodt")));						//有効日
				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim() );
				Keepb.setChangeFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_changeflg")).trim());

				//画面内容を保存(BODY)
//              Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyucd")));					//品種コード
//		        Keepb.setHinsyuNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyuna"))));					//品種名
//		       	Keepb.setHinmokuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmokucd")));					//品目コード
//				Keepb.setHinmokuNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmokuna"))));					//品目名
//				Keepb.setTanpinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tanpincd")));					//単品
//				Keepb.setMarkGroupCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_markgroupcd")));				//マークグループ
//				Keepb.setMarkGroupNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_markgroupna")));				//マークグループ
//		       	Keepb.setMarkCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_markcd")));						//マークコード
//				Keepb.setMarkNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_markna")));						//マークコード
//	            Keepb.setSyohin2Cd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohin2cd")));					//商品コード２
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
//				Keepb.setGentankaSenVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankasenvl")));			//原価単価(少数部)
	            Keepb.setBaitankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitankavl")));				//売価単価(税込)
	            Keepb.setMakerKiboKakakuVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makerkibokakakuvl")));	//メーカー希望小売り価格(税込み)
				Keepb.setPluSendDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_plusenddt")));				//PLU送信日
				Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")));	            //仕入先コード
				Keepb.setSiiresakiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakirn")));	            //仕入先名
				Keepb.setAreaCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_areacd")));	            		//地区コード
				Keepb.setAreacd_Na(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_areacdna")));	            	//地区名
				Keepb.setTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanricd")));	            //店別仕入先管理コード
				Keepb.setTenSiiresakiKanriNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanrina")));	            //店別仕入先管理名
				Keepb.setSiireHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siirehinbancd")));	                        //仕入先商品コード

				Keepb.setHanbaiStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaistdt")));	                        //販売開始日
				Keepb.setHanbaiEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaieddt")));	                        //販売終了日
				Keepb.setTenHachuStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachustdt")));	                    //発注開始日
				Keepb.setTenHachuEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachueddt")));	                    //発注終了日

				Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));	                                    //ＥＯＳ区分
				Keepb.setHonbuzaiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_honbuzaikb")));	                        //本部在庫区分
				Keepb.setFujisyohinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_fujisyohinkb")));	                    //商品区分
				Keepb.setBlandCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandcd")));	                                //ブランドコード
				Keepb.setBlandNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandna")));	                                //ブランド名
				Keepb.setPbKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pbkb")));	                                    //ＰＢ区分
				Keepb.setSyuzeiHokokuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syuzeihokokukb")));	                //酒税報告分類

				Keepb.setHachuTaniNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutanina")));	                         //発注単位呼称
				Keepb.setHachutaniIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutaniirisuqt")));	             //発注単位（入数）
				Keepb.setMaxHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_maxhachutaniqt")));	                 //最大発注単位
				Keepb.setUnitPriceTaniKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricetanikb")));	                //ユニットプライス単位
				Keepb.setUnitPriceNaiyoryoQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricenaiyoryoqt")));	        //ユニットプライス内容量
				Keepb.setUnitPriceKijunTaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricekijuntaniqt")));	    //ユニットプライス基準単位量
				Keepb.setSyohikigen(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohikigen")));	                        //消費期限
//				↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
				Keepb.setHukaKinoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hukakinocd")));	                        //酒類度数
//				↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑

				Keepb.setDaichotenpoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daichotenpo")));	                    //商品台帳（店舗）
				Keepb.setDaichohonbuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daichohonbu")));	                    //商品台帳（本部）
				Keepb.setDaichosiiresakiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daichosiiresaki")));	            //商品台帳（仕入先）
				Keepb.setPcKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pckb")));	                                    //プライスシール発行区分
				Keepb.setDaisiNokb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daisinokb")));	                            //プライスシール種類


//	            Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")));				//仕入先コード
//				Keepb.setSiiresakiNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakirn"))));				//仕入先名
//				Keepb.setTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanricd")));	//店別仕入先管理コード
//				Keepb.setTenSiiresakiKanriNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanrina"))));	//店別仕入先管理名
//				Keepb.setDaihyoHaisoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisocd")));			//代表配送先コード
//				Keepb.setDaihyoHaisoNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisona"))));		//代表配送先名
//				Keepb.setSiireHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siirehinbancd")));			//仕入先品番
//
//				Keepb.setHanbaiStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaistdt")));				//販売開始日
//				Keepb.setHanbaiEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaieddt")));				//販売終了日
//				Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));							//EOS区分
//				Keepb.setTenHachuStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachustdt")));			//店舗発注開始日
//				Keepb.setTenHachuEdDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenhachueddt")));			//店舗発注終了日
//				Keepb.setHachutaniIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutaniirisuqt")));	//発注単位(入数)
//				Keepb.setMaxHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_maxhachutaniqt")));		//最大発注単位
//				Keepb.setHachuTeisiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachuteisikb")));			//発注停止区分
//
//				Keepb.setAutoDelKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_autodelkb")));					//自動削除対象区分
//				Keepb.setGotMujyokenFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gotmujyokenfg")));			//ＧＯＴ無条件表示対象
//				Keepb.setGotStartMm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gotstartmm")));				//ＧＯＴ表示期間(開始)
//				Keepb.setGotEndMm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gotendmm")));					//ＧＯＴ表示期間(終了)
//
//				Keepb.setHanbaiSeisakuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbaiseisakukb")));		//販売政策区分
//				Keepb.setShinazoroeKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_shinazoroekb")));			//品揃区分
//				Keepb.setSeasonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_seasoncd")));					//シーズンコード
//				Keepb.setBlandCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandcd")));						//ブランドコード
//				Keepb.setBlandNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandna"))));						//ブランド名
//				Keepb.setYunyuhinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yunyuhinkb")));				//輸入品区分
//				Keepb.setSantiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_santicd")));						//原産国/産地コード
//				Keepb.setMakerKiboKakakuVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makerkibokakakuvl")));	//メーカー希望小売り価格(税込み)
//				Keepb.setCommentTx(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_commenttx")));					//コメント
//
//				Keepb.setButuryuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_buturyukb")));					//物流区分
//				Keepb.setCNohinRtimeKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_cnohinrtimekb")));			//センター納品リードタイム
//				Keepb.setSyohinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohinkb")));					//商品区分
//				Keepb.setTenZaikoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenzaikokb")));				//店在庫区分
//				Keepb.setHenpinNb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_henpinnb")));					//返品契約書NO
//				Keepb.setHenpinGenkaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_henpingenkavl")));			//返品原価
//				Keepb.setRebateFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rebatefg")));					//リベート対象フラグ
//
//				Keepb.setPcKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pckb")));							//PC発行区分
//				Keepb.setUnitPriceTaniKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricetanikb")));		//ユニットプライス単位区分
//				Keepb.setUnitPriceNaiyoryoQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricenaiyoryoqt")));		//ユニットプライス内容量
//				Keepb.setUnitPriceKijunTaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricekijuntaniqt")));	//ユニットプライス基準単位
//				Keepb.setHanbaiLimitQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitqt")));			//販売期限
//				Keepb.setHanbaiLimitKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitkb")));			//販売期限区分
//
//		        Keepb.setItfCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_itfcd")));							//ITFコード
//				Keepb.setNyukaSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nyukasyohincd")));			//入荷時商品コード
//				Keepb.setPackWidthQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_packwidthqt")));				//外箱サイズ幅
//				Keepb.setPackHeigthQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_packheigthqt")));			//外箱サイズ高さ
//				Keepb.setPackDepthQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_packdepthqt")));				//外箱サイズ奥行き
//				Keepb.setPackWeightQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_packweightqt")));			//外箱重量
//
//				Keepb.setCenterZaikoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_centerzaikokb")));			//センター在庫区分
//				Keepb.setZaikoHachuSaki(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_zaikohachusaki")));		//在庫補充発注先
//				Keepb.setZaikoCenterCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_zaikocentercd")));			//在庫センターコード
//				Keepb.setZaikoCenterNa((mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_zaikocenterna"))));			//在庫センター名
//				Keepb.setMinZaikosuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_minzaikosuqt")));			//最低在庫数(発注点)
//				Keepb.setMaxZaikosuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_maxzaikosuqt")));			//最大在庫数
//				Keepb.setCenterHachutaniKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_centerhachutanikb")));	//センター発注単位区分
//				Keepb.setCenterHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_centerhachutaniqt")));	//センター発注単位数
//				Keepb.setCenterIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_centeririsuqt")));			//センター入り数
//				Keepb.setCenterWeightQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_centerweightqt")));		//センター重量
//				Keepb.setTanaNoNb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tananonb")));					//棚NO
//				Keepb.setDanNoNb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_dannonb")));						//段NO
//				Keepb.setKijunZaikosuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kijunzaikosuqt")));		//基準在庫日数
			}
// 			↑↑2006.06.27 xubq カスタマイズ修正↑↑
			Keepb.setMode(mst000401_LogicBean.chkNullString(dataHolder.getParameter("mode")));
			Keepb.setExistFlg("");	//データ存在(検索[ｷｬﾝｾﾙ]時)
			Keepb.setErrorBackDisp("");

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);
//          ↓↓2006.06.27 xubq カスタマイズ修正↓↓
			//メーニューバーアイコン取得
	        // SysSosasyaSesson取得
//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
//			menuMap.put("kengen_cd",kengenCd);
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
//			Keepb.setMenuBar(menu);

            //エラーチェック
			//if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				//List lst = new ArrayList();	//マスタ存在チェック抽出条件
				//String name = "";				//戻り値格納
				//mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				//String CtlName = "";
				//String chkDeg = "";
				//Keepb.setTempHankuCd("");
			// userSession取得
			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id",GamenId);
			//エラーチェック
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				String name = "";				//戻り値格納
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				String CtlName = "";
				String chkDeg = "";
				Keepb.setTempBuMonCd("");
// 				↑↑2006.06.27 xubq カスタマイズ修正↑↑
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				//検索条件処理チェック　/////////////////////////////////////////////////////////////////////
				//部門コード存在チェック
				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).equals("")){
					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1,userLocal)  + "' ");
					lst.add(" and ");
					lst.add("CODE_CD = '"  + mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("ct_bumoncd"), 4, "0", true)) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
				}
	            //部門コード存在エラー
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_bumoncd");
					Keepb.setFirstFocus("ct_bumoncd");
					Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
				} else {
					Keepb.setBuMonKanjiRn( mstchk.getCdName() );
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
							}
						} else {
							syohinCd = Keepb.getSyohinCd();
						}
						// 商品マスタの存在チェック
						mstchk = getSyohin( db, syohinCd, bumonCd ,mst000611_SystemKbDictionary.G.getCode() ,"hinmei_kanji_na" ,Keepb.getYukoDt(), Keepb.getProcessingDivision());
						if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							//商品コード存在(有)エラー
							mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus( CtlName );
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("40001").toString());
							}
						}
						if (Keepb.getSyohinCd().length() == 13 ){
							// 商品マスタの存在チェック
							syohinCd = Keepb.getSyohinCd();
						}
						mstchk = getSyohin(db, syohinCd, bumonCd, mst000611_SystemKbDictionary.G.getCode(), "hinmei_kanji_na", Keepb.getYukoDt(), Keepb.getProcessingDivision());
						Keepb.setHinmeiKanji( mstchk.getCdName() );
					} else {
						// 処理状況：【新規以外】
						if (Keepb.getSyohinCd().length() == 13 ){
							// 商品マスタの存在チェック
							syohinCd = Keepb.getSyohinCd();
							mstchk = getSyohin(db, syohinCd, bumonCd, mst000611_SystemKbDictionary.G.getCode(), "hinmei_kanji_na", Keepb.getYukoDt(), Keepb.getProcessingDivision());
							Keepb.setHinmeiKanji( mstchk.getCdName() );
							//商品コード存在(有)エラー
							if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
								//商品コード存在エラー
								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus( CtlName );
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("0005").toString());
								}
							}
						}
					}
					// チェックデジットのチェック
//					↓↓2006.10.17 H.Yamamoto カスタマイズ修正↓↓
//					if (Keepb.getSyohinCd().length() == 13 && !("0000000000".equals(Keepb.getSyohinCd().substring(0,10)))){
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
//					↑↑2006.10.17 H.Yamamoto カスタマイズ修正↑↑
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
//						↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
						sql.append(" for read only ");
//						↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
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
					whereList.add(" AND system_kb = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");

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
//              ↑↑2006.06.27 xubq カスタマイズ修正↑↑
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
//	}


	/**
	 * 指定商品マスタの取得を行う。
	 * @param		mst000101_DbmsBean db		: DBインスタンス
	 * @param		String syohinCd				: 商品コード
	 * @param		String hankuCd				: 部門コード
	 * @param		String systemKb 			: システム区分
	 * @param		String columnNa				: 取得カラム名
	 * @param		String yukoDt				: 有効日
	 * @return		mst000701_MasterInfoBean 	: 取得結果
	 */
	public mst000701_MasterInfoBean getSyohin( mst000101_DbmsBean db, String syohinCd, String bumonCd, String systemKb,
	String columnNa, String yukoDt, String syoriKb)
	 throws Exception,SQLException {

		mst110101_SyohinDM 	 	 dm  = new mst110101_SyohinDM();

		mst000701_MasterInfoBean ret = new mst000701_MasterInfoBean();
		ret.setCdName("");
		ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);

		Map selectMap  = new HashMap();
		selectMap.put("syohin_cd",syohinCd);
		if(!bumonCd.equals("")){
		  selectMap.put("bumon_cd",bumonCd);
		}
		selectMap.put("system_kb",systemKb);
		selectMap.put("yuko_dt",yukoDt);

		String MSTDATE = null;
		ResultSet rset = null;    //抽出結果(ResultSet)
//		↓↓2006.10.18 H.Yamamoto レスポンス対策修正↓↓
//		rset = db.executeQuery(dm.getSelectSql(selectMap));
		rset = db.executeQuery(dm.getSyohinCheckSql(selectMap));
//		↑↑2006.10.18 H.Yamamoto レスポンス対策修正↑↑
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
//			↓↓2006.10.18 H.Yamamoto レスポンス対策修正↓↓
//			whereList.add("  syohin_cd Like '" + syohinCd + "%' ");
			whereList.add("  syohin_cd = '" + syohinCd + "' ");
//			↑↑2006.10.18 H.Yamamoto レスポンス対策修正↑↑
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
//// 2005.11.30 Y.Inaba 処理区分により処理を分岐 END
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
			}else{
				ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);
			}
		}
		return ret;
	}
//  ↑↑2006.06.27 xubq カスタマイズ修正↑↑

	/**
	 *
	 * 新規/修正時の入力項目のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst310201_KeepBean mst310201_KeepBean 	: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */

	public void  UpdateCheck( mst000101_DbmsBean db, mst130201_KeepBean Keepb, Map CtrlColor,
							 SessionManager  sessionManager, TreeMap map ) throws Exception,SQLException {
		List lst = new ArrayList();	//マスタ存在チェック抽出条件
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
		String CtlName = "";
		String chkDeg = "";
//		↓↓2006/06/29xubqカスタマイズ修正↓↓
//		String setHankuCd = "";
//		if(Keepb.getHankuCd().equals("")){
//			setHankuCd = Keepb.getTempHankuCd();
//		} else {
//			setHankuCd = Keepb.getHankuCd();
//		}
//		↑↑2006/06/29xubqカスタマイズ修正↑↑
		String setBuMonCd = "";
		if(!"".equals(Keepb.getBumonCd())){
			setBuMonCd =mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true));	//部門コード
		}
		//〔部門コード〕
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
					Keepb.setErrorMessage("商品体系の部門・ユニットコードの" + map.get("0002").toString());
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
					Keepb.setErrorMessage("指定されたユニットコード"+map.get("40007").toString());
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
//		sb.append("riyo_user_id = '" + "0000"+ Keepb.getUserId() + "' ");
		sb.append(" select ");
		sb.append(" user_na as riyo_user_na ");
		sb.append(" from ");
		sb.append("		r_portal_user ");
		sb.append("		where ");
		sb.append(" user_id = '" + "0000"+ Keepb.getUserId() + "' ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
		rst = db.executeQuery(sb.toString());
		if (rst.next()) {
//			↓↓2006.07.13 yangbo カスタマイズ修正↓↓
//			Keepb.setSiiresakiNa(mst000401_LogicBean.chkNullString(rst.getString("riyo_user_na")));
//			↑↑2006.07.13 yangbo カスタマイズ修正↑↑
		}else{
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				//バイヤーNo存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定されたバイヤーNo"+map.get("40007").toString());
				}
			}
		}
		if (rst != null) {
			rst.close();
		}

		//〔JANメーカーコード〕
		CtlName = "ct_makercd";
		if(!Keepb.getMakerCd().equals("")&& Keepb.getMakerCd()!=null){
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.JAN_MAKER_NAME,userLocal) + "' ");
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
		}else {Keepb.setMaker_Na("");}
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

		//〔店別仕入先管理コード〕9999 はフリー入力 by kema 06.09.13
		CtlName = "ct_tensiiresakikanricd";
		if(!Keepb.getTenSiiresakiKanriCd().equals("") && Keepb.getTenSiiresakiKanriCd()!=null
			&& !Keepb.getTenSiiresakiKanriCd().equals("9999")){
			lst.clear();
			lst.add( " kanri_kb = '" +  mst000901_KanriKbDictionary.BUMON.getCode() + "' "  );
			lst.add( " and " );
			lst.add( " kanri_cd = '" + setBuMonCd + "' "  );
			lst.add( " and " );
			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd()+ "' " );
			// ===BEGIN=== Comment out by kou 2006/10/12
////			代表仕入先の考え方を無くなりました
//			lst.add( " and " );
//// ↓↓仕様変更（2005.07.22）↓↓
////			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' " );
//// ↓↓仕様変更（2005.07.25）↓↓
////			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getHankuCd() + Keepb.getTenSiiresakiKanriCd() + "' " );
//			lst.add( " TENPO_CD = '000000'" );
			// ===END=== Comment out by kou 2006/10/12
// ↑↑仕様変更（2005.07.25）↑↑
// ↑↑仕様変更（2005.07.22）↑↑
// ↓↓仕様変更（2005.08.24）↓↓
//			lst.add( " and " );
//			lst.add( " tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' " );
// ↑↑仕様変更（2005.08.24）↑↑
// 2006.01.24 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db, "vw_r_tensiiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
			mstchk = mst000401_LogicBean.getMasterCdName(db, "r_tenbetu_siiresaki", "siiresaki_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt());
// 2006.01.24 Y.Inaba Mod ↑
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
// ↓↓仕様変更（2005.08.31）↓↓
//				if(!mst000401_LogicBean.getMasterChk(db,"vw_r_tensiiresaki", lst  )){
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
// ↑↑仕様変更（2005.08.31）↑↑
					//コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたメーカーコード"+map.get("40007").toString());
					}
// ↓↓仕様変更（2005.08.31）↓↓
				}
//				 ↓↓2006.06.27 xubq カスタマイズ修正↓↓
//				else {
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
//				 ↑↑2006.06.27 xubq カスタマイズ修正↑↑
			}
			//名称取得
			lst.clear();
//			↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
//			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP + "' ");
//			lst.add(" and ");
//// ↓↓仕様変更（2005.07.13）↓↓
//// lst.add("code_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' ");
//// ↓↓仕様変更（2005.09.02）↓↓
////			lst.add("code_cd = '" + Keepb.getHankuCd() + Keepb.getTenSiiresakiKanriCd() + "' ");
//			lst.add("code_cd = '" +setBuMonCd + Keepb.getTenSiiresakiKanriCd() + "' ");
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.MAKER_NAME, userLocal) + "' ");
			lst.add(" and ");
			lst.add("code_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' ");
//			↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
// ↑↑仕様変更（2005.09.02）↑↑
// ↑↑仕様変更（2005.07.13）↑↑
// 2006.01.24 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.01.24 Y.Inaba Mod ↑
			Keepb.setTenSiiresakiKanriNa( mstchk.getCdName() );
// ↓↓仕様変更（2005.07.26）↓↓
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
// ↑↑仕様変更（2005.07.26）↑↑
		} else {
			Keepb.setTenSiiresakiKanriNa( "" );
		}

	}

}
