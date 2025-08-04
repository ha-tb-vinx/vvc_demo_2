/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/04)初版作成
 * @version 1.1(2005/05/11)新ER対応
 * 　　　　　追加→発注商品区分(hacyu_syohin_kb)、システム区分(system_kb)、
 * 　　　　　　　　マスタ使用不可区分(mst_siyofuka_kb)、税区分(zei_kb)、販売期間区分(hanbai_kikan_kb)
 * 　　　　　削除→店配送先管理コード(ten_haiso_kanri_cd)
 *           1.2(2006/01/24) D.Matsumoto 商品販区切り替え対応
 * @version 1.3(2006/02/15) D.Matsumoto 販区切り替え対応その２
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;
//↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
import mdware.common.util.StringUtility;
//↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/04)初版作成
 * @version 1.3(2006/02/15) D.Matsumoto 販区切り替え対応その２
 */
public class mst110101_SyohinDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst110101_SyohinDM()
	{
		super( mst000101_ConstDictionary.CONNECTION_STR );
	}
	/**
	 * 検索後にＢＥＡＮをインスタンス化する所。
	 * 検索した結果セットをＢＥＡＮとして持ち直す。
	 * DataModuleから呼び出され返したObjectをListに追加する。
	 * @param rest ResultSet
	 * @return Object インスタンス化されたＢＥＡＮ
	 */
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		mst110101_SyohinBean bean = new mst110101_SyohinBean();
		bean.setBumonCd( encodingString(rest.getString("bumon_cd")) );
		bean.setSyohinCd( encodingString(rest.getString("syohin_cd")) );
		bean.setYukoDt( encodingString(rest.getString("yuko_dt")) );
		bean.setHacyuSyohinKb( encodingString(rest.getString("hacyu_syohin_kb")) );
		bean.setSystemKb( encodingString(rest.getString("system_kb")) );
		bean.setGyosyuKb( encodingString(rest.getString("gyosyu_kb")) );
		bean.setUnitCd( encodingString(rest.getString("unit_cd")) );
		bean.setHinmokuCd( encodingString(rest.getString("hinmoku_cd")) );
		bean.setMarkGroupCd( encodingString(rest.getString("mark_group_cd")) );
		bean.setMarkCd( encodingString(rest.getString("mark_cd")) );
		bean.setSyohin2Cd( encodingString(rest.getString("syohin_2_cd")) );
		bean.setKetasuKb( encodingString(rest.getString("ketasu_kb")) );
		bean.setMakerCd( encodingString(rest.getString("maker_cd")) );
		bean.setHinmeiKanjiNa( encodingString(rest.getString("hinmei_kanji_na")) );
		bean.setKikakuKanjiNa( encodingString(rest.getString("kikaku_kanji_na")) );
		bean.setHinmeiKanaNa( encodingString(rest.getString("hinmei_kana_na")) );
		bean.setKikakuKanaNa( encodingString(rest.getString("kikaku_kana_na")) );
		bean.setSyohinWidthQt( rest.getLong("syohin_width_qt") );
		bean.setSyohinHeightQt( rest.getLong("syohin_height_qt") );
		bean.setSyohinDepthQt( rest.getLong("syohin_depth_qt") );
		bean.setSiireHinbanCd( encodingString(rest.getString("siire_hinban_cd")) );
		bean.setBlandCd( encodingString(rest.getString("bland_cd")) );
		bean.setYunyuhinKb( encodingString(rest.getString("yunyuhin_kb")) );
		bean.setSantiCd( encodingString(rest.getString("santi_cd")) );
		bean.setMakerKiboKakakuVl( rest.getLong("maker_kibo_kakaku_vl") );
		bean.setNohinOndoKb( encodingString(rest.getString("nohin_ondo_kb")) );
		bean.setCommentTx( encodingString(rest.getString("comment_tx")) );
		bean.setTenHachuStDt( encodingString(rest.getString("ten_hachu_st_dt")) );
		bean.setTenHachuEdDt( encodingString(rest.getString("ten_hachu_ed_dt")) );
		bean.setGentankaVl( rest.getDouble("gentanka_vl") );
		bean.setBaitankaVl( rest.getLong("baitanka_vl") );
//		↓↓2006.10.19 H.Yamamoto カスタマイズ修正↓↓
//		bean.setPreGentankaVl( rest.getDouble("gentanka_vl") );
//		bean.setPreBaitankaVl( rest.getLong("baitanka_vl") );
		bean.setPreGentankaVl( rest.getString("pre_gentanka_vl") );
		bean.setPreBaitankaVl( rest.getString("pre_baitanka_vl") );
//		↑↑2006.10.19 H.Yamamoto カスタマイズ修正↑↑
		bean.setHachutaniIrisuQt( rest.getDouble("hachutani_irisu_qt") );
		bean.setMaxHachutaniQt( rest.getDouble("max_hachutani_qt") );
		bean.setTeikanKb( encodingString(rest.getString("teikan_kb")) );
		bean.setEosKb( encodingString(rest.getString("eos_kb")) );
		bean.setNohinKigenQt( rest.getLong("nohin_kigen_qt") );
		bean.setNohinKigenKb( encodingString(rest.getString("nohin_kigen_kb")) );
		bean.setMstSiyofukaKb( encodingString(rest.getString("mst_siyofuka_kb")) );
		bean.setHachuTeisiKb( encodingString(rest.getString("hachu_teisi_kb")) );
		bean.setSiiresakiCd( encodingString(rest.getString("siiresaki_cd")) );
		bean.setDaihyoHaisoCd( encodingString(rest.getString("daihyo_haiso_cd")) );
		bean.setTenSiiresakiKanriCd( encodingString(rest.getString("ten_siiresaki_kanri_cd")) );
		bean.setSobaSyohinKb( encodingString(rest.getString("soba_syohin_kb")) );
		bean.setBin1Kb( encodingString(rest.getString("bin_1_kb")) );
		bean.setHachuPattern1Kb( encodingString(rest.getString("hachu_pattern_1_kb")) );
		bean.setSimeTime1Qt( rest.getLong("sime_time_1_qt") );
		bean.setCNohinRtime1Kb( encodingString(rest.getString("c_nohin_rtime_1_kb")) );
		bean.setTenNohinRtime1Kb( encodingString(rest.getString("ten_nohin_rtime_1_kb")) );
		bean.setTenNohinTime1Kb( encodingString(rest.getString("ten_nohin_time_1_kb")) );
		bean.setBin2Kb( encodingString(rest.getString("bin_2_kb")) );
		bean.setHachuPattern2Kb( encodingString(rest.getString("hachu_pattern_2_kb")) );
		bean.setSimeTime2Qt( rest.getLong("sime_time_2_qt") );
		bean.setCNohinRtime2Kb( encodingString(rest.getString("c_nohin_rtime_2_kb")) );
		bean.setTenNohinRtime2Kb( encodingString(rest.getString("ten_nohin_rtime_2_kb")) );
		bean.setTenNohinTime2Kb( encodingString(rest.getString("ten_nohin_time_2_kb")) );
		bean.setBin3Kb( encodingString(rest.getString("bin_3_kb")) );
		bean.setHachuPattern3Kb( encodingString(rest.getString("hachu_pattern_3_kb")) );
		bean.setSimeTime3Qt( rest.getLong("sime_time_3_qt") );
		bean.setCNohinRtime3Kb( encodingString(rest.getString("c_nohin_rtime_3_kb")) );
		bean.setTenNohinRtime3Kb( encodingString(rest.getString("ten_nohin_rtime_3_kb")) );
		bean.setTenNohinTime3Kb( encodingString(rest.getString("ten_nohin_time_3_kb")) );
		bean.setCNohinRtimeKb( encodingString(rest.getString("c_nohin_rtime_kb")) );
		bean.setZeiKb( encodingString(rest.getString("zei_kb")) );
		bean.setHanbaiKikanKb( encodingString(rest.getString("hanbai_kikan_kb")) );
		bean.setSyohinKb( encodingString(rest.getString("syohin_kb")) );
		bean.setButuryuKb( encodingString(rest.getString("buturyu_kb")) );
		bean.setYokomotiKb( encodingString(rest.getString("yokomoti_kb")) );
		bean.setTenGroupnoCd( encodingString(rest.getString("ten_groupno_cd")) );
		bean.setTenZaikoKb( encodingString(rest.getString("ten_zaiko_kb")) );
		bean.setHanbaiSeisakuKb( encodingString(rest.getString("hanbai_seisaku_kb")) );
		bean.setHenpinNb( encodingString(rest.getString("henpin_nb")) );
		bean.setHenpinGenkaVl( rest.getLong("henpin_genka_vl") );
		bean.setRebateFg( encodingString(rest.getString("rebate_fg")) );
		bean.setHanbaiStDt( encodingString(rest.getString("hanbai_st_dt")) );
		bean.setHanbaiEdDt( encodingString(rest.getString("hanbai_ed_dt")) );
		bean.setHanbaiLimitQt( rest.getLong("hanbai_limit_qt") );
		bean.setHanbaiLimitKb( encodingString(rest.getString("hanbai_limit_kb")) );
		bean.setAutoDelKb( encodingString(rest.getString("auto_del_kb")) );
		bean.setGotMujyokenFg( encodingString(rest.getString("got_mujyoken_fg")) );
		bean.setGotStartMm( encodingString(rest.getString("got_start_mm")) );
		bean.setGotEndMm( encodingString(rest.getString("got_end_mm")) );
		bean.setEShopKb( encodingString(rest.getString("e_shop_kb")) );
		bean.setPluSendDt( encodingString(rest.getString("plu_send_dt")) );
		bean.setRecHinmeiKanjiNa( encodingString(rest.getString("rec_hinmei_kanji_na")) );
		bean.setRecHinmeiKanaNa( encodingString(rest.getString("rec_hinmei_kana_na")) );
		bean.setKeypluFg( encodingString(rest.getString("keyplu_fg")) );
		bean.setPcKb( encodingString(rest.getString("pc_kb")) );
		bean.setDaisiNoNb( encodingString(rest.getString("daisi_no_nb")) );
		bean.setUnitPriceTaniKb( encodingString(rest.getString("unit_price_tani_kb")) );
		bean.setUnitPriceNaiyoryoQt( rest.getLong("unit_price_naiyoryo_qt") );
		bean.setUnitPriceKijunTaniQt( rest.getLong("unit_price_kijun_tani_qt") );
		bean.setShinazoroeKb( encodingString(rest.getString("shinazoroe_kb")) );
		bean.setKumisuKb( encodingString(rest.getString("kumisu_kb")) );
		bean.setNefudaKb( encodingString(rest.getString("nefuda_kb")) );
		bean.setYoridoriKb( encodingString(rest.getString("yoridori_kb")) );
		bean.setYoridoriQt( rest.getString("yoridori_qt") );
		bean.setTagHyojiBaikaVl( rest.getLong("tag_hyoji_baika_vl") );
		bean.setSeasonCd( encodingString(rest.getString("season_cd")) );
		bean.setHukusyuCd( encodingString(rest.getString("hukusyu_cd")) );
		bean.setStyleCd( encodingString(rest.getString("style_cd")) );
		bean.setSceneCd( encodingString(rest.getString("scene_cd")) );
		bean.setSexCd( encodingString(rest.getString("sex_cd")) );
		bean.setAgeCd( encodingString(rest.getString("age_cd")) );
		bean.setGenerationCd( encodingString(rest.getString("generation_cd")) );
		bean.setSozaiCd( encodingString(rest.getString("sozai_cd")) );
		bean.setPatternCd( encodingString(rest.getString("pattern_cd")) );
		bean.setOriamiCd( encodingString(rest.getString("oriami_cd")) );
		bean.setHukaKinoCd( encodingString(rest.getString("huka_kino_cd")) );
		bean.setSizeCd( encodingString(rest.getString("size_cd")) );
		bean.setColorCd( encodingString(rest.getString("color_cd")) );
		bean.setSyuzeiHokokuKb( encodingString(rest.getString("syuzei_hokoku_kb")) );
		bean.setTcJyouhoNa( encodingString(rest.getString("tc_jyouho_na")) );
		bean.setNohinSyohinCd( encodingString(rest.getString("nohin_syohin_cd")) );
		bean.setItfCd( encodingString(rest.getString("itf_cd")) );
		bean.setCaseIrisuQt( rest.getLong("case_irisu_qt") );
		bean.setNyukaSyohinCd( encodingString(rest.getString("nyuka_syohin_cd")) );
		bean.setPackWidthQt( rest.getLong("pack_width_qt") );
		bean.setPackHeigthQt( rest.getLong("pack_heigth_qt") );
		bean.setPackDepthQt( rest.getLong("pack_depth_qt") );
		bean.setPackWeightQt( rest.getLong("pack_weight_qt") );
		bean.setCenterZaikoKb( encodingString(rest.getString("center_zaiko_kb")) );
		bean.setZaikoHachuSaki( encodingString(rest.getString("zaiko_hachu_saki")) );
		bean.setZaikoCenterCd( encodingString(rest.getString("zaiko_center_cd")) );
		bean.setMinZaikosuQt( rest.getLong("min_zaikosu_qt") );
		bean.setMaxZaikosuQt( rest.getLong("max_zaikosu_qt") );
		bean.setCenterHachutaniKb( encodingString(rest.getString("center_hachutani_kb")) );
		bean.setCenterHachutaniQt( rest.getLong("center_hachutani_qt") );
		bean.setCenterIrisuQt( rest.getLong("center_irisu_qt") );
		bean.setCenterWeightQt( rest.getLong("center_weight_qt") );
		bean.setTanaNoNb( rest.getLong("tana_no_nb") );
		bean.setDanNoNb( rest.getLong("dan_no_nb") );
		bean.setKeiyakuSuQt( rest.getLong("keiyaku_su_qt") );
		bean.setKeiyakuPatternKb( encodingString(rest.getString("keiyaku_pattern_kb")) );
		bean.setKeiyakuStDt( encodingString(rest.getString("keiyaku_st_dt")) );
		bean.setKeiyakuEdDt( encodingString(rest.getString("keiyaku_ed_dt")) );
		bean.setKijunZaikosuQt( rest.getLong("kijun_zaikosu_qt") );
		bean.setDZokusei1Na( encodingString(rest.getString("d_zokusei_1_na")) );
		bean.setSZokusei1Na( encodingString(rest.getString("s_zokusei_1_na")) );
		bean.setDZokusei2Na( encodingString(rest.getString("d_zokusei_2_na")) );
		bean.setSZokusei2Na( encodingString(rest.getString("s_zokusei_2_na")) );
		bean.setDZokusei3Na( encodingString(rest.getString("d_zokusei_3_na")) );
		bean.setSZokusei3Na( encodingString(rest.getString("s_zokusei_3_na")) );
		bean.setDZokusei4Na( encodingString(rest.getString("d_zokusei_4_na")) );
		bean.setSZokusei4Na( encodingString(rest.getString("s_zokusei_4_na")) );
		bean.setDZokusei5Na( encodingString(rest.getString("d_zokusei_5_na")) );
		bean.setSZokusei5Na( encodingString(rest.getString("s_zokusei_5_na")) );
		bean.setDZokusei6Na( encodingString(rest.getString("d_zokusei_6_na")) );
		bean.setSZokusei6Na( encodingString(rest.getString("s_zokusei_6_na")) );
		bean.setDZokusei7Na( encodingString(rest.getString("d_zokusei_7_na")) );
		bean.setSZokusei7Na( encodingString(rest.getString("s_zokusei_7_na")) );
		bean.setDZokusei8Na( encodingString(rest.getString("d_zokusei_8_na")) );
		bean.setSZokusei8Na( encodingString(rest.getString("s_zokusei_8_na")) );
		bean.setDZokusei9Na( encodingString(rest.getString("d_zokusei_9_na")) );
		bean.setSZokusei9Na( encodingString(rest.getString("s_zokusei_9_na")) );
		bean.setDZokusei10Na( encodingString(rest.getString("d_zokusei_10_na")) );
		bean.setSZokusei10Na( encodingString(rest.getString("s_zokusei_10_na")) );
		bean.setHenkoDt( encodingString(rest.getString("henko_dt")) );
		bean.setInsertTs( encodingString(rest.getString("insert_ts")) );
		bean.setInsertUserId( encodingString(rest.getString("insert_user_id")) );
		bean.setUpdateTs( encodingString(rest.getString("update_ts")) );
		bean.setUpdateUserId( encodingString(rest.getString("update_user_id")) );
		bean.setDeleteFg( encodingString(rest.getString("delete_fg")) );
		bean.setSinkiTorokuDt( encodingString(rest.getString("sinki_toroku_dt")) );
		bean.setSyokaiTorokuTs( encodingString(rest.getString("syokai_toroku_ts")) );
		bean.setSyokaiUserId( encodingString(rest.getString("syokai_user_id")) );
		bean.setHinbanCd( encodingString(rest.getString("hinban_cd")) );
		bean.setSubclassCd( encodingString(rest.getString("subclass_cd")) );
		bean.setHaiFuCd( encodingString(rest.getString("haifu_cd")) );
		bean.setGtinCd( encodingString(rest.getString("gtin_cd")) );
//		↓↓2006.07.12 xubq カスタマイズ修正↓↓		
//		bean.setAreaCd( encodingString(rest.getString("area_cd")) );
//		↑↑2006.07.12 xubq カスタマイズ修正↑↑			
		bean.setKikakuKanji2Na( encodingString(rest.getString("kikaku_kanji_2_na")) );
		bean.setKikakuKana2Na( encodingString(rest.getString("kikaku_kana_2_na")) );
		bean.setPbKb( encodingString(rest.getString("pb_kb")) );
		bean.setKeshiBaikaVl( rest.getString("keshi_baika_vl") );
		bean.setTokubetuGenkaVl( rest.getString("tokubetu_genka_vl") );
		bean.setHachuTaniNa( encodingString(rest.getString("hachu_tani_na")) );
		bean.setCenterKyoyoDt( rest.getString("center_kyoyo_dt") );
		bean.setSyohiKigenDt( rest.getString("syohi_kigen_dt") );
		bean.setNefudaUkewatasiDt( encodingString(rest.getString("nefuda_ukewatasi_dt")) );
		bean.setNefudaUkewatasiKb( encodingString(rest.getString("nefuda_ukewatasi_kb")) );
		bean.setYoridoriVl( rest.getString("yoridori_vl") );
		bean.setDaichotenpoKb( encodingString(rest.getString("daicho_tenpo_kb")) );
		bean.setDaichohonbuKb( encodingString(rest.getString("daicho_honbu_kb")) );
		bean.setDaichosiiresakiKb( encodingString(rest.getString("daicho_siiresaki_kb")) );
		bean.setSodeCd( encodingString(rest.getString("sode_cd")) );
		bean.setHonbuzaiKb( encodingString(rest.getString("honbu_zai_kb")) );
		bean.setFujisyohinKb( encodingString(rest.getString("fuji_syohin_kb")) );
		bean.setHacyuSyohinCd( encodingString(rest.getString("hacyu_syohin_cd")) );
		bean.setYusenBinKb( encodingString(rest.getString("yusen_bin_kb")) );
		bean.setPluKb( encodingString(rest.getString("plu_kb")) );
		
		bean.setStSyohinWidthQt( rest.getString("syohin_width_qt") );
		bean.setStSyohinHeightQt( rest.getString("syohin_height_qt") );
		bean.setStSyohinDepthQt( rest.getString("syohin_depth_qt") );
		bean.setStMakerKiboKakakuVl( rest.getString("maker_kibo_kakaku_vl") );
		bean.setStGentankaVl( rest.getString("gentanka_vl") );
		bean.setStBaitankaVl( rest.getString("baitanka_vl") );
		bean.setStHachutaniIrisuQt( rest.getString("hachutani_irisu_qt") );
		bean.setStMaxHachutaniQt( rest.getString("max_hachutani_qt") );
		bean.setStNohinKigenQt( rest.getString("nohin_kigen_qt") );
		bean.setStSimeTime1Qt( rest.getString("sime_time_1_qt") );
		bean.setStSimeTime2Qt( rest.getString("sime_time_2_qt") );
		bean.setStSimeTime3Qt( rest.getString("sime_time_3_qt") );
		bean.setStHenpinNb( rest.getString("henpin_nb") );
		bean.setStHenpinGenkaVl( rest.getString("henpin_genka_vl") );
		bean.setStHanbaiLimitQt( rest.getString("hanbai_limit_qt") );
		bean.setStDaisiNoNb( rest.getString("daisi_no_nb") );
		bean.setStUnitPriceNaiyoryoQt( rest.getString("unit_price_naiyoryo_qt") );
		bean.setStUnitPriceKijunTaniQt( rest.getString("unit_price_kijun_tani_qt") );
		bean.setStYoridoriQt( rest.getString("yoridori_qt") );
		bean.setStTagHyojiBaikaVl( rest.getString("tag_hyoji_baika_vl") );		
		bean.setStCaseIrisuQt( rest.getString("case_irisu_qt") );
		bean.setStPackWidthQt( rest.getString("pack_width_qt") );
		bean.setStPackHeigthQt( rest.getString("pack_heigth_qt") );
		bean.setStPackDepthQt( rest.getString("pack_depth_qt") );
		bean.setStPackWeightQt( rest.getString("pack_weight_qt") );
		bean.setStMinZaikosuQt( rest.getString("min_zaikosu_qt") );
		bean.setStMaxZaikosuQt( rest.getString("max_zaikosu_qt") );
		bean.setStCenterHachutaniQt( rest.getString("center_hachutani_qt") );
		bean.setStCenterIrisuQt( rest.getString("center_irisu_qt") );
		bean.setStCenterWeightQt( rest.getString("center_weight_qt") );
		bean.setStTanaNoNb( rest.getString("tana_no_nb") );
		bean.setStDanNoNb( rest.getString("dan_no_nb") );
		bean.setStKeiyakuSuQt( rest.getString("keiyaku_su_qt") );
		bean.setStKijunZaikosuQt( rest.getString("kijun_zaikosu_qt") );	
		bean.setCreateDatabase();
		return bean;
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql( Map map )
	{

		String whereStr = "where ";
		String andStr = " and ";
		String wk = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("bumon_cd ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("yuko_dt ");
		sb.append(", ");
		sb.append("hacyu_syohin_kb ");
		sb.append(", ");
		sb.append("system_kb ");
		sb.append(", ");
		sb.append("gyosyu_kb ");
		sb.append(", ");
		sb.append("unit_cd ");
		sb.append(", ");
		sb.append("hinmoku_cd ");
		sb.append(", ");
		sb.append("mark_group_cd ");
		sb.append(", ");
		sb.append("mark_cd ");
		sb.append(", ");
		sb.append("syohin_2_cd ");
		sb.append(", ");
		sb.append("ketasu_kb ");
		sb.append(", ");
		sb.append("maker_cd ");
		sb.append(", ");
		sb.append("hinmei_kanji_na ");
		sb.append(", ");
		sb.append("kikaku_kanji_na ");
		sb.append(", ");
		sb.append("hinmei_kana_na ");
		sb.append(", ");
		sb.append("kikaku_kana_na ");
		sb.append(", ");
		sb.append("syohin_width_qt ");
		sb.append(", ");
		sb.append("syohin_height_qt ");
		sb.append(", ");
		sb.append("syohin_depth_qt ");
		sb.append(", ");
		sb.append("siire_hinban_cd ");
		sb.append(", ");
		sb.append("bland_cd ");
		sb.append(", ");
		sb.append("yunyuhin_kb ");
		sb.append(", ");
		sb.append("santi_cd ");
		sb.append(", ");
		sb.append("maker_kibo_kakaku_vl ");
		sb.append(", ");
		sb.append("nohin_ondo_kb ");
		sb.append(", ");
		sb.append("comment_tx ");
		sb.append(", ");
		sb.append("ten_hachu_st_dt ");
		sb.append(", ");
		sb.append("ten_hachu_ed_dt ");
		sb.append(", ");
		sb.append("gentanka_vl ");
		sb.append(", ");
		sb.append("baitanka_vl ");
		sb.append(", ");
		sb.append("hachutani_irisu_qt ");
		sb.append(", ");
		sb.append("max_hachutani_qt ");
		sb.append(", ");
		sb.append("teikan_kb ");
		sb.append(", ");
		sb.append("eos_kb ");
		sb.append(", ");
		sb.append("nohin_kigen_qt ");
		sb.append(", ");
		sb.append("nohin_kigen_kb ");
		sb.append(", ");
		sb.append("mst_siyofuka_kb ");
		sb.append(", ");
		sb.append("hachu_teisi_kb ");
		sb.append(", ");
		sb.append("siiresaki_cd ");
		sb.append(", ");
		sb.append("daihyo_haiso_cd ");
		sb.append(", ");
		sb.append("ten_siiresaki_kanri_cd ");
		sb.append(", ");
		sb.append("soba_syohin_kb ");
		sb.append(", ");
		sb.append("bin_1_kb ");
		sb.append(", ");
		sb.append("hachu_pattern_1_kb ");
		sb.append(", ");
		sb.append("sime_time_1_qt ");
		sb.append(", ");
		sb.append("c_nohin_rtime_1_kb ");
		sb.append(", ");
		sb.append("ten_nohin_rtime_1_kb ");
		sb.append(", ");
		sb.append("ten_nohin_time_1_kb ");
		sb.append(", ");
		sb.append("bin_2_kb ");
		sb.append(", ");
		sb.append("hachu_pattern_2_kb ");
		sb.append(", ");
		sb.append("sime_time_2_qt ");
		sb.append(", ");
		sb.append("c_nohin_rtime_2_kb ");
		sb.append(", ");
		sb.append("ten_nohin_rtime_2_kb ");
		sb.append(", ");
		sb.append("ten_nohin_time_2_kb ");
		sb.append(", ");
		sb.append("bin_3_kb ");
		sb.append(", ");
		sb.append("hachu_pattern_3_kb ");
		sb.append(", ");
		sb.append("sime_time_3_qt ");
		sb.append(", ");
		sb.append("c_nohin_rtime_3_kb ");
		sb.append(", ");
		sb.append("ten_nohin_rtime_3_kb ");
		sb.append(", ");
		sb.append("ten_nohin_time_3_kb ");
		sb.append(", ");
		sb.append("c_nohin_rtime_kb ");
		sb.append(", ");
		sb.append("zei_kb ");
		sb.append(", ");
		sb.append("hanbai_kikan_kb ");
		sb.append(", ");
		sb.append("syohin_kb ");
		sb.append(", ");
		sb.append("buturyu_kb ");
		sb.append(", ");
		sb.append("yokomoti_kb ");
		sb.append(", ");
		sb.append("ten_groupno_cd ");
		sb.append(", ");
		sb.append("ten_zaiko_kb ");
		sb.append(", ");
		sb.append("hanbai_seisaku_kb ");
		sb.append(", ");
		sb.append("henpin_nb ");
		sb.append(", ");
		sb.append("henpin_genka_vl ");
		sb.append(", ");
		sb.append("rebate_fg ");
		sb.append(", ");
		sb.append("hanbai_st_dt ");
		sb.append(", ");
		sb.append("hanbai_ed_dt ");
		sb.append(", ");
		sb.append("hanbai_limit_qt ");
		sb.append(", ");
		sb.append("hanbai_limit_kb ");
		sb.append(", ");
		sb.append("auto_del_kb ");
		sb.append(", ");
		sb.append("got_mujyoken_fg ");
		sb.append(", ");
		sb.append("got_start_mm ");
		sb.append(", ");
		sb.append("got_end_mm ");
		sb.append(", ");
		sb.append("e_shop_kb ");
		sb.append(", ");
		sb.append("plu_send_dt ");
		sb.append(", ");
		sb.append("rec_hinmei_kanji_na ");
		sb.append(", ");
		sb.append("rec_hinmei_kana_na ");
		sb.append(", ");
		sb.append("keyplu_fg ");
		sb.append(", ");
		sb.append("pc_kb ");
		sb.append(", ");
		sb.append("daisi_no_nb ");
		sb.append(", ");
		sb.append("unit_price_tani_kb ");
		sb.append(", ");
		sb.append("unit_price_naiyoryo_qt ");
		sb.append(", ");
		sb.append("unit_price_kijun_tani_qt ");
		sb.append(", ");
		sb.append("shinazoroe_kb ");
		sb.append(", ");
		sb.append("kumisu_kb ");
		sb.append(", ");
		sb.append("nefuda_kb ");
		sb.append(", ");
		sb.append("yoridori_kb ");
		sb.append(", ");
		sb.append("yoridori_qt ");
		sb.append(", ");
		sb.append("tag_hyoji_baika_vl ");
		sb.append(", ");
		sb.append("season_cd ");
		sb.append(", ");
		sb.append("hukusyu_cd ");
		sb.append(", ");
		sb.append("style_cd ");
		sb.append(", ");
		sb.append("scene_cd ");
		sb.append(", ");
		sb.append("sex_cd ");
		sb.append(", ");
		sb.append("age_cd ");
		sb.append(", ");
		sb.append("generation_cd ");
		sb.append(", ");
		sb.append("sozai_cd ");
		sb.append(", ");
		sb.append("pattern_cd ");
		sb.append(", ");
		sb.append("oriami_cd ");
		sb.append(", ");
//		↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("huka_kino_cd ");
		sb.append("rtrim(ltrim(huka_kino_cd,'0')) as huka_kino_cd ");
//		↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("size_cd ");
		sb.append(", ");
		sb.append("color_cd ");
		sb.append(", ");
		sb.append("syuzei_hokoku_kb ");
		sb.append(", ");
		sb.append("tc_jyouho_na ");
		sb.append(", ");
		sb.append("nohin_syohin_cd ");
		sb.append(", ");
		sb.append("itf_cd ");
		sb.append(", ");
		sb.append("case_irisu_qt ");
		sb.append(", ");
		sb.append("nyuka_syohin_cd ");
		sb.append(", ");
		sb.append("pack_width_qt ");
		sb.append(", ");
		sb.append("pack_heigth_qt ");
		sb.append(", ");
		sb.append("pack_depth_qt ");
		sb.append(", ");
		sb.append("pack_weight_qt ");
		sb.append(", ");
		sb.append("center_zaiko_kb ");
		sb.append(", ");
		sb.append("zaiko_hachu_saki ");
		sb.append(", ");
		sb.append("zaiko_center_cd ");
		sb.append(", ");
		sb.append("min_zaikosu_qt ");
		sb.append(", ");
		sb.append("max_zaikosu_qt ");
		sb.append(", ");
		sb.append("center_hachutani_kb ");
		sb.append(", ");
		sb.append("center_hachutani_qt ");
		sb.append(", ");
		sb.append("center_irisu_qt ");
		sb.append(", ");
		sb.append("center_weight_qt ");
		sb.append(", ");
		sb.append("tana_no_nb ");
		sb.append(", ");
		sb.append("dan_no_nb ");
		sb.append(", ");
		sb.append("keiyaku_su_qt ");
		sb.append(", ");
		sb.append("keiyaku_pattern_kb ");
		sb.append(", ");
		sb.append("keiyaku_st_dt ");
		sb.append(", ");
		sb.append("keiyaku_ed_dt ");
		sb.append(", ");
		sb.append("kijun_zaikosu_qt ");
		sb.append(", ");
		sb.append("d_zokusei_1_na ");
		sb.append(", ");
		sb.append("s_zokusei_1_na ");
		sb.append(", ");
		sb.append("d_zokusei_2_na ");
		sb.append(", ");
		sb.append("s_zokusei_2_na ");
		sb.append(", ");
		sb.append("d_zokusei_3_na ");
		sb.append(", ");
		sb.append("s_zokusei_3_na ");
		sb.append(", ");
		sb.append("d_zokusei_4_na ");
		sb.append(", ");
		sb.append("s_zokusei_4_na ");
		sb.append(", ");
		sb.append("d_zokusei_5_na ");
		sb.append(", ");
		sb.append("s_zokusei_5_na ");
		sb.append(", ");
		sb.append("d_zokusei_6_na ");
		sb.append(", ");
		sb.append("s_zokusei_6_na ");
		sb.append(", ");
		sb.append("d_zokusei_7_na ");
		sb.append(", ");
		sb.append("s_zokusei_7_na ");
		sb.append(", ");
		sb.append("d_zokusei_8_na ");
		sb.append(", ");
		sb.append("s_zokusei_8_na ");
		sb.append(", ");
		sb.append("d_zokusei_9_na ");
		sb.append(", ");
		sb.append("s_zokusei_9_na ");
		sb.append(", ");
		sb.append("d_zokusei_10_na ");
		sb.append(", ");
		sb.append("s_zokusei_10_na ");
		sb.append(", ");
		sb.append("henko_dt ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append(", ");
		sb.append("sinki_toroku_dt ");
		sb.append(", ");
		sb.append("syokai_toroku_ts ");
		sb.append(", ");
		sb.append("substring(syokai_user_id,5,6) as syokai_user_id");
		sb.append(", ");
		sb.append("hinban_cd ");
		sb.append(", ");
		sb.append("subclass_cd ");
		sb.append(", ");
		sb.append("haifu_cd ");
		sb.append(", ");
		sb.append("gtin_cd ");
//		↓↓2006.07.12 xubq カスタマイズ修正↓↓
//		sb.append(", ");
//		sb.append("area_cd ");
//		↑↑2006.07.12 xubq カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("kikaku_kanji_2_na ");
		sb.append(", ");
		sb.append("kikaku_kana_2_na ");
		sb.append(", ");
		sb.append("pb_kb ");
		sb.append(", ");
		sb.append("keshi_baika_vl ");
		sb.append(", ");
		sb.append("tokubetu_genka_vl ");
		sb.append(", ");
		sb.append("pre_gentanka_vl ");
		sb.append(", ");
		sb.append("pre_baitanka_vl ");
		sb.append(", ");
		sb.append("hachu_tani_na ");
		sb.append(", ");
		sb.append("center_kyoyo_dt ");
		sb.append(", ");
		sb.append("syohi_kigen_dt ");
		sb.append(", ");
		sb.append("nefuda_ukewatasi_dt ");
		sb.append(", ");
		sb.append("nefuda_ukewatasi_kb ");
		sb.append(", ");
		sb.append("yoridori_vl ");
		sb.append(", ");
		sb.append("daicho_tenpo_kb ");
		sb.append(", ");
		sb.append("daicho_honbu_kb ");
		sb.append(", ");
		sb.append("daicho_siiresaki_kb ");
		sb.append(", ");
		sb.append("sode_cd ");
		sb.append(", ");
		sb.append("honbu_zai_kb ");
		sb.append(", ");
		sb.append("fuji_syohin_kb ");
		sb.append(", ");
		sb.append("hacyu_syohin_cd ");
		sb.append(", ");
		sb.append("yusen_bin_kb ");
		sb.append(", ");
		sb.append("plu_kb ");
		sb.append("from R_SYOHIN ");
		
		wk = getWhereSqlStr(map, "bumon_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syohin_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "yuko_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hacyu_syohin_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "system_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "gyosyu_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hinsyu_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hinmoku_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "mark_group_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "mark_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syohin_2_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ketasu_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "maker_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hinmei_kanji_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "kikaku_kanji_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hinmei_kana_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "kikaku_kana_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "syohin_width_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "syohin_height_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "syohin_depth_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "siire_hinban_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "bland_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "yunyuhin_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "santi_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "maker_kibo_kakaku_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "nohin_ondo_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "comment_tx", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_hachu_st_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_hachu_ed_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "gentanka_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "baitanka_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "hachutani_irisu_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "max_hachutani_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "teikan_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "eos_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "nohin_kigen_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "nohin_kigen_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "mst_siyofuka_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hachu_teisi_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "siiresaki_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "daihyo_haiso_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_siiresaki_kanri_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "soba_syohin_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "bin_1_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hachu_pattern_1_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "sime_time_1_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "c_nohin_rtime_1_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_nohin_rtime_1_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_nohin_time_1_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "bin_2_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hachu_pattern_2_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "sime_time_2_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "c_nohin_rtime_2_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_nohin_rtime_2_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_nohin_time_2_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "bin_3_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hachu_pattern_3_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "sime_time_3_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "c_nohin_rtime_3_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_nohin_rtime_3_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_nohin_time_3_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "c_nohin_rtime_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "zei_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hanbai_kikan_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syohin_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "buturyu_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "yokomoti_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_groupno_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "ten_zaiko_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hanbai_seisaku_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "henpin_nb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "henpin_genka_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "rebate_fg", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hanbai_st_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hanbai_ed_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "hanbai_limit_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hanbai_limit_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "auto_del_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "got_mujyoken_fg", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "got_start_mm", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "got_end_mm", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "e_shop_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "plu_send_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "rec_hinmei_kanji_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "rec_hinmei_kana_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "keyplu_fg", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "pc_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "daisi_no_nb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "unit_price_tani_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "unit_price_naiyoryo_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "unit_price_kijun_tani_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "shinazoroe_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "kumisu_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "nefuda_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "yoridori_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "yoridori_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "tag_hyoji_baika_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "season_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hukusyu_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "style_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "scene_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "sex_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "age_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "generation_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "sozai_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "pattern_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "oriami_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "huka_kino_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "size_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "color_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syuzei_hokoku_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "tc_jyouho_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "nohin_syohin_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "itf_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "case_irisu_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "nyuka_syohin_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "pack_width_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "pack_heigth_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "pack_depth_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "pack_weight_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "center_zaiko_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "zaiko_hachu_saki", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "zaiko_center_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "min_zaikosu_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "max_zaikosu_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "center_hachutani_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "center_hachutani_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "center_irisu_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "center_weight_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "tana_no_nb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "dan_no_nb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "keiyaku_su_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "keiyaku_pattern_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "keiyaku_st_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "keiyaku_ed_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlOther(map, "kijun_zaikosu_qt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_1_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_1_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_2_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_2_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_3_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_3_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_4_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_4_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_5_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_5_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_6_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_6_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_7_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_7_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_8_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_8_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_9_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_9_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "d_zokusei_10_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "s_zokusei_10_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "insert_ts", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "insert_user_id", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "update_ts", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "update_user_id", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "delete_fg", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "sinki_toroku_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "henko_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syokai_toroku_ts", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syokai_user_id", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hinban_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "subclass_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "haifu_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "gtin_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "kikaku_kanji_2_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "kikaku_kana_2_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "pb_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "keshi_baika_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "tokubetu_genka_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "pre_gentanka_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "pre_baitanka_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hachu_tani_na", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "center_kyoyo_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syohi_kigen_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "nefuda_ukewatasi_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "nefuda_ukewatasi_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "yoridori_vl", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "daicho_tenpo_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "daicho_honbu_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "daicho_siiresaki_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "sode_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "honbu_zai_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "fuji_syohin_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "hacyu_syohin_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "yusen_bin_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}

		sb.append(" order by ");
		// 2006/01/24 D.Matsumoto 追加開始
		//商品マスタ検索時に有効日が同じで削除フラグが異なるデータが存在する場合
		//有効なデータを表示するように変更する
		sb.append("delete_fg");
		sb.append(",");
		// 2006/02/15 同一有効日で削除が２件以上存在する場合、登録された順に表示する
		sb.append("insert_ts");
		sb.append(" desc");
		sb.append(",");
		// 2006/02/15 ここまで
		// 2006/01/24 D.Matsumoto 追加ここまで
		//sb.append("hanku_cd");
		//sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("yuko_dt");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ文字型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlStr(Map map, String Key, String wstr)
	{
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();

		// Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= '" + conv.convertWhereString( (String)map.get(Key + "_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= '" + conv.convertWhereString( (String)map.get(Key + "_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " = '" + conv.convertWhereString( (String)map.get(Key) ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_like") != null && ((String)map.get(Key + "_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '%" + conv.convertWhereString( (String)map.get(Key + "_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get(Key + "_bef_like") != null && ((String)map.get(Key + "_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '%" + conv.convertWhereString( (String)map.get(Key + "_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft_like") != null && ((String)map.get(Key + "_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '" + conv.convertWhereString( (String)map.get(Key + "_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ日付・時刻型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlDate(Map map, String Key, String wstr)
	{
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();

		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= '" + conv.convertWhereString( (String)map.get(Key + "_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= '" + conv.convertWhereString( (String)map.get(Key + "_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " = '" + (String)map.get(Key) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ数値型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlNum(Map map, String Key, String wstr)
	{
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();

		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append(Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプOTHER）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlOther(Map map, String Key, String wstr)
	{
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();

		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append(Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst )
	{
		boolean befKanma = false;
		boolean aftKanma = false;

		mst110101_SyohinBean bean = (mst110101_SyohinBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("insert into r_syohin (");
		sb.append("bumon_cd ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("yuko_dt ");
		sb.append(", ");
		sb.append("hacyu_syohin_kb ");
		sb.append(", ");
		sb.append("system_kb ");
		sb.append(", ");
		sb.append("gyosyu_kb ");
		sb.append(", ");
		sb.append("unit_cd ");
		sb.append(", ");
		sb.append("hinmoku_cd ");
		sb.append(", ");
		sb.append("mark_group_cd ");
		sb.append(", ");
		sb.append("mark_cd ");
		sb.append(", ");
		sb.append("syohin_2_cd ");
		sb.append(", ");
		sb.append("ketasu_kb ");
		sb.append(", ");
		sb.append("maker_cd ");
		sb.append(", ");
		sb.append("hinmei_kanji_na ");
		sb.append(", ");
		sb.append("kikaku_kanji_na ");
		sb.append(", ");
		sb.append("hinmei_kana_na ");
		sb.append(", ");
		sb.append("kikaku_kana_na ");
		sb.append(", ");
		sb.append("syohin_width_qt ");
		sb.append(", ");
		sb.append("syohin_height_qt ");
		sb.append(", ");
		sb.append("syohin_depth_qt ");
		sb.append(", ");
		sb.append("siire_hinban_cd ");
		sb.append(", ");
		sb.append("bland_cd ");
		sb.append(", ");
		sb.append("yunyuhin_kb ");
		sb.append(", ");
		sb.append("santi_cd ");
		sb.append(", ");
		sb.append("maker_kibo_kakaku_vl ");
		sb.append(", ");
		sb.append("nohin_ondo_kb ");
		sb.append(", ");
		sb.append("comment_tx ");
		sb.append(", ");
		sb.append("ten_hachu_st_dt ");
		sb.append(", ");
		sb.append("ten_hachu_ed_dt ");
		sb.append(", ");
		sb.append("gentanka_vl ");
		sb.append(", ");
		sb.append("baitanka_vl ");
		sb.append(", ");
		sb.append("hachutani_irisu_qt ");
		sb.append(", ");
		sb.append("max_hachutani_qt ");
		sb.append(", ");
		sb.append("teikan_kb ");
		sb.append(", ");
		sb.append("eos_kb ");
		sb.append(", ");
		sb.append("nohin_kigen_qt ");
		sb.append(", ");
		sb.append("nohin_kigen_kb ");
		sb.append(", ");
		sb.append("mst_siyofuka_kb ");
		sb.append(", ");
		sb.append("hachu_teisi_kb ");
		sb.append(", ");
		sb.append("siiresaki_cd ");
		sb.append(", ");
		sb.append("daihyo_haiso_cd ");
		sb.append(", ");
		sb.append("ten_siiresaki_kanri_cd ");
		sb.append(", ");
		sb.append("soba_syohin_kb ");
		sb.append(", ");
		sb.append("bin_1_kb ");
		sb.append(", ");
		sb.append("hachu_pattern_1_kb ");
		sb.append(", ");
		sb.append("sime_time_1_qt ");
		sb.append(", ");
		sb.append("c_nohin_rtime_1_kb ");
		sb.append(", ");
		sb.append("ten_nohin_rtime_1_kb ");
		sb.append(", ");
		sb.append("ten_nohin_time_1_kb ");
		sb.append(", ");
		sb.append("bin_2_kb ");
		sb.append(", ");
		sb.append("hachu_pattern_2_kb ");
		sb.append(", ");
		sb.append("sime_time_2_qt ");
		sb.append(", ");
		sb.append("c_nohin_rtime_2_kb ");
		sb.append(", ");
		sb.append("ten_nohin_rtime_2_kb ");
		sb.append(", ");
		sb.append("ten_nohin_time_2_kb ");
		sb.append(", ");
		sb.append("bin_3_kb ");
		sb.append(", ");
		sb.append("hachu_pattern_3_kb ");
		sb.append(", ");
		sb.append("sime_time_3_qt ");
		sb.append(", ");
		sb.append("c_nohin_rtime_3_kb ");
		sb.append(", ");
		sb.append("ten_nohin_rtime_3_kb ");
		sb.append(", ");
		sb.append("ten_nohin_time_3_kb ");
		sb.append(", ");
		sb.append("c_nohin_rtime_kb ");
		sb.append(", ");
		sb.append("zei_kb ");
		sb.append(", ");
		sb.append("hanbai_kikan_kb ");
		sb.append(", ");
		sb.append("syohin_kb ");
		sb.append(", ");
		sb.append("buturyu_kb ");
		sb.append(", ");
		sb.append("yokomoti_kb ");
		sb.append(", ");
		sb.append("ten_groupno_cd ");
		sb.append(", ");
		sb.append("ten_zaiko_kb ");
		sb.append(", ");
		sb.append("hanbai_seisaku_kb ");
		sb.append(", ");
		sb.append("henpin_nb ");
		sb.append(", ");
		sb.append("henpin_genka_vl ");
		sb.append(", ");
		sb.append("rebate_fg ");
		sb.append(", ");
		sb.append("hanbai_st_dt ");
		sb.append(", ");
		sb.append("hanbai_ed_dt ");
		sb.append(", ");
		sb.append("hanbai_limit_qt ");
		sb.append(", ");
		sb.append("hanbai_limit_kb ");
		sb.append(", ");
		sb.append("auto_del_kb ");
		sb.append(", ");
		sb.append("got_mujyoken_fg ");
		sb.append(", ");
		sb.append("got_start_mm ");
		sb.append(", ");
		sb.append("got_end_mm ");
		sb.append(", ");
		sb.append("e_shop_kb ");
		sb.append(", ");
		sb.append("plu_send_dt ");
		sb.append(", ");
		sb.append("rec_hinmei_kanji_na ");
		sb.append(", ");
		sb.append("rec_hinmei_kana_na ");
		sb.append(", ");
		sb.append("keyplu_fg ");
		sb.append(", ");
		sb.append("pc_kb ");
		sb.append(", ");
		sb.append("daisi_no_nb ");
		sb.append(", ");
		sb.append("unit_price_tani_kb ");
		sb.append(", ");
		sb.append("unit_price_naiyoryo_qt ");
		sb.append(", ");
		sb.append("unit_price_kijun_tani_qt ");
		sb.append(", ");
		sb.append("shinazoroe_kb ");
		sb.append(", ");
		sb.append("kumisu_kb ");
		sb.append(", ");
		sb.append("nefuda_kb ");
		sb.append(", ");
		sb.append("yoridori_kb ");
		sb.append(", ");
		sb.append("yoridori_qt ");
		sb.append(", ");
		sb.append("tag_hyoji_baika_vl ");
		sb.append(", ");
		sb.append("season_cd ");
		sb.append(", ");
		sb.append("hukusyu_cd ");
		sb.append(", ");
		sb.append("style_cd ");
		sb.append(", ");
		sb.append("scene_cd ");
		sb.append(", ");
		sb.append("sex_cd ");
		sb.append(", ");
		sb.append("age_cd ");
		sb.append(", ");
		sb.append("generation_cd ");
		sb.append(", ");
		sb.append("sozai_cd ");
		sb.append(", ");
		sb.append("pattern_cd ");
		sb.append(", ");
		sb.append("oriami_cd ");
		sb.append(", ");
		sb.append("huka_kino_cd ");
		sb.append(", ");
		sb.append("size_cd ");
		sb.append(", ");
		sb.append("color_cd ");
		sb.append(", ");
		sb.append("syuzei_hokoku_kb ");
		sb.append(", ");
		sb.append("tc_jyouho_na ");
		sb.append(", ");
		sb.append("nohin_syohin_cd ");
		sb.append(", ");
		sb.append("itf_cd ");
		sb.append(", ");
		sb.append("case_irisu_qt ");
		sb.append(", ");
		sb.append("nyuka_syohin_cd ");
		sb.append(", ");
		sb.append("pack_width_qt ");
		sb.append(", ");
		sb.append("pack_heigth_qt ");
		sb.append(", ");
		sb.append("pack_depth_qt ");
		sb.append(", ");
		sb.append("pack_weight_qt ");
		sb.append(", ");
		sb.append("center_zaiko_kb ");
		sb.append(", ");
		sb.append("zaiko_hachu_saki ");
		sb.append(", ");
		sb.append("zaiko_center_cd ");
		sb.append(", ");
		sb.append("min_zaikosu_qt ");
		sb.append(", ");
		sb.append("max_zaikosu_qt ");
		sb.append(", ");
		sb.append("center_hachutani_kb ");
		sb.append(", ");
		sb.append("center_hachutani_qt ");
		sb.append(", ");
		sb.append("center_irisu_qt ");
		sb.append(", ");
		sb.append("center_weight_qt ");
		sb.append(", ");
		sb.append("tana_no_nb ");
		sb.append(", ");
		sb.append("dan_no_nb ");
		sb.append(", ");
		sb.append("keiyaku_su_qt ");
		sb.append(", ");
		sb.append("keiyaku_pattern_kb ");
		sb.append(", ");
		sb.append("keiyaku_st_dt ");
		sb.append(", ");
		sb.append("keiyaku_ed_dt ");
		sb.append(", ");
		sb.append("kijun_zaikosu_qt ");
		sb.append(", ");
		sb.append("d_zokusei_1_na ");
		sb.append(", ");
		sb.append("s_zokusei_1_na ");
		sb.append(", ");
		sb.append("d_zokusei_2_na ");
		sb.append(", ");
		sb.append("s_zokusei_2_na ");
		sb.append(", ");
		sb.append("d_zokusei_3_na ");
		sb.append(", ");
		sb.append("s_zokusei_3_na ");
		sb.append(", ");
		sb.append("d_zokusei_4_na ");
		sb.append(", ");
		sb.append("s_zokusei_4_na ");
		sb.append(", ");
		sb.append("d_zokusei_5_na ");
		sb.append(", ");
		sb.append("s_zokusei_5_na ");
		sb.append(", ");
		sb.append("d_zokusei_6_na ");
		sb.append(", ");
		sb.append("s_zokusei_6_na ");
		sb.append(", ");
		sb.append("d_zokusei_7_na ");
		sb.append(", ");
		sb.append("s_zokusei_7_na ");
		sb.append(", ");
		sb.append("d_zokusei_8_na ");
		sb.append(", ");
		sb.append("s_zokusei_8_na ");
		sb.append(", ");
		sb.append("d_zokusei_9_na ");
		sb.append(", ");
		sb.append("s_zokusei_9_na ");
		sb.append(", ");
		sb.append("d_zokusei_10_na ");
		sb.append(", ");
		sb.append("s_zokusei_10_na ");
		sb.append(", ");
		sb.append("henko_dt ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append(", ");
		sb.append("sinki_toroku_dt ");
		sb.append(", ");
		sb.append("syokai_toroku_ts ");
		sb.append(", ");
		sb.append("syokai_user_id ");
		sb.append(", ");
		sb.append("hinban_cd ");
		sb.append(", ");
		sb.append("subclass_cd ");
		sb.append(", ");
		sb.append("haifu_cd ");
		sb.append(", ");
		sb.append("gtin_cd ");
		sb.append(", ");
		sb.append("kikaku_kanji_2_na ");
		sb.append(", ");
		sb.append("kikaku_kana_2_na ");
		sb.append(", ");
		sb.append("pb_kb ");
		sb.append(", ");
		sb.append("keshi_baika_vl ");
		sb.append(", ");
		sb.append("tokubetu_genka_vl ");
		sb.append(", ");
		sb.append("pre_gentanka_vl ");
		sb.append(", ");
		sb.append("pre_baitanka_vl ");
		sb.append(", ");
		sb.append("hachu_tani_na ");
		sb.append(", ");
		sb.append("center_kyoyo_dt ");
		sb.append(", ");
		sb.append("syohi_kigen_dt ");
		sb.append(", ");
		sb.append("nefuda_ukewatasi_dt ");
		sb.append(", ");
		sb.append("nefuda_ukewatasi_kb ");
		sb.append(", ");
		sb.append("yoridori_vl ");
		sb.append(", ");
		sb.append("daicho_tenpo_kb ");
		sb.append(", ");
		sb.append("daicho_honbu_kb ");
		sb.append(", ");
		sb.append("daicho_siiresaki_kb ");
		sb.append(", ");
		sb.append("sode_cd ");
		sb.append(", ");
		sb.append("honbu_zai_kb ");
		sb.append(", ");
		sb.append("fuji_syohin_kb ");
		sb.append(", ");
		sb.append("hacyu_syohin_cd ");
		sb.append(", ");
		sb.append("yusen_bin_kb ");
		sb.append(",");
		sb.append(" plu_kb ");
		sb.append(",");
		sb.append(" haiban_fg");
		sb.append(")Values(");

		sb.append("'" + conv.convertString( bean.getBumonCd() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getHacyuSyohinKb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSystemKb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getGyosyuKb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getUnitCd() ) + "'");
		sb.append(",");
//		sb.append("'" + conv.convertString( bean.getHinmokuCd() ) + "'");
//		sb.append("'" + conv.convertString( bean.getMarkGroupCd() ) + "'");
//		sb.append("'" + conv.convertString( bean.getMarkCd() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		if( bean.getSyohin2Cd() != null && bean.getSyohin2Cd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getSyohin2Cd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		sb.append("'" + conv.convertString( bean.getKetasuKb() ) + "'");
		sb.append(",");
		if( bean.getMakerCd() != null && bean.getMakerCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getMakerCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		sb.append("'" + conv.convertString( bean.getHinmeiKanjiNa() ) + "'");
		sb.append(",");
		if( bean.getKikakuKanjiNa() != null && bean.getKikakuKanjiNa().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getKikakuKanjiNa() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		sb.append("'" + conv.convertString( bean.getHinmeiKanaNa() ) + "'");
		sb.append(",");
		if( bean.getKikakuKanaNa() != null && bean.getKikakuKanaNa().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getKikakuKanaNa() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append( bean.getSyohinWidthQtString());
		sb.append(" 0,");
//		sb.append( bean.getSyohinHeightQtString());
		sb.append(" 0,");
//		sb.append( bean.getSyohinDepthQtString());
		sb.append(" 0,");
		if( bean.getSiireHinbanCd() != null && bean.getSiireHinbanCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getSiireHinbanCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getBlandCd() ) + "'");
//		sb.append("'" + conv.convertString( bean.getYunyuhinKb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSantiCd() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		if( bean.getUpMakerKiboKakakuVl() != null && bean.getUpMakerKiboKakakuVl().trim().length() != 0 )
		{
			sb.append( bean.getUpMakerKiboKakakuVl());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}

//		sb.append("'" + conv.convertString( bean.getNohinOndoKb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getCommentTx() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getTenHachuStDt() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getTenHachuEdDt() ) + "'");
		sb.append(",");
		sb.append( bean.getUpGentankaVl());
		sb.append(",");
		sb.append( bean.getUpBaitankaVl());
		sb.append(",");
		if( bean.getUpHachutaniIrisuQt() != null && bean.getUpHachutaniIrisuQt().trim().length() != 0 )
		{
			sb.append( bean.getUpHachutaniIrisuQt());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getUpMaxHachutaniQt() != null && bean.getUpMaxHachutaniQt().trim().length() != 0 )
		{
			sb.append( bean.getStMaxHachutaniQt());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		↓↓2006.09.30 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("'" + conv.convertString( bean.getTeikanKb() ) + "'");
//		sb.append(",");
		if( bean.getTeikanKb() != null && bean.getTeikanKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getTeikanKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		↑↑2006.09.30 H.Yamamoto カスタマイズ修正↑↑
		sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
		sb.append(",");
//		sb.append( bean.getNohinKigenQtString());
//		sb.append("'" + conv.convertString( bean.getNohinKigenKb() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getMstSiyofukaKb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getHachuTeisiKb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
		sb.append(",");
//		sb.append("'" + conv.convertString( bean.getDaihyoHaisoCd() ) + "'");
		sb.append(" null ,");
		if( bean.getTenSiiresakiKanriCd() != null && bean.getTenSiiresakiKanriCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getTenSiiresakiKanriCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getSobaSyohinKb() ) + "'");
		sb.append(" null ,");
		if( bean.getBin1Kb() != null && bean.getBin1Kb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getBin1Kb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getHachuPattern1Kb() ) + "'");
//		sb.append( bean.getSimeTime1QtString());
//		sb.append("'" + conv.convertString( bean.getCNohinRtime1Kb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getTenNohinRtime1Kb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getTenNohinTime1Kb() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		if( bean.getBin2Kb() != null && bean.getBin2Kb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getBin2Kb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getHachuPattern2Kb() ) + "'");
//		sb.append( bean.getSimeTime2QtString());
//		sb.append("'" + conv.convertString( bean.getCNohinRtime2Kb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getTenNohinRtime2Kb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getTenNohinTime2Kb() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
//		sb.append("'" + conv.convertString( bean.getBin3Kb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getHachuPattern3Kb() ) + "'");
//		sb.append( bean.getSimeTime3QtString());
//		sb.append("'" + conv.convertString( bean.getCNohinRtime3Kb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getTenNohinRtime3Kb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getTenNohinTime3Kb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getCNohinRtimeKb() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getZeiKb() ) + "'");
		sb.append(",");
//		sb.append("'" + conv.convertString( bean.getHanbaiKikanKb() ) + "'");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getSyohinKb() ) + "'");
		sb.append(",");
//		sb.append("'" + conv.convertString( bean.getButuryuKb() ) + "'");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getYokomotiKb() ) + "'");
		sb.append(",");
//		sb.append("'" + conv.convertString( bean.getTenGroupnoCd() ) + "'");
//		sb.append("'" + conv.convertString( bean.getTenZaikoKb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getHanbaiSeisakuKb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getHenpinNb() ) + "'");
//		sb.append( bean.getHenpinGenkaVlString());
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getRebateFg() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getHanbaiStDt() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getHanbaiEdDt() ) + "'");
		sb.append(",");
		if( bean.getUpHanbaiLimitQt() != null && bean.getUpHanbaiLimitQt().trim().length() != 0 )
		{
			sb.append( bean.getUpHanbaiLimitQt());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		sb.append("'" + conv.convertString( bean.getHanbaiLimitKb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getAutoDelKb() ) + "'");
		sb.append(",");
//		sb.append("'" + conv.convertString( bean.getGotMujyokenFg() ) + "'");
//		sb.append("'" + conv.convertString( bean.getGotStartMm() ) + "'");
//		sb.append("'" + conv.convertString( bean.getGotEndMm() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getEShopKb() ) + "'");
		sb.append(",");

		if( bean.getPluSendDt() != null && bean.getPluSendDt().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getPluSendDt() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getRecHinmeiKanjiNa() != null && bean.getRecHinmeiKanjiNa().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getRecHinmeiKanjiNa() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getRecHinmeiKanaNa() != null && bean.getRecHinmeiKanaNa().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getRecHinmeiKanaNa() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getKeypluFg() != null && bean.getKeypluFg().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getKeypluFg() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getPcKb() != null && bean.getPcKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getPcKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getDaisiNoNb() != null && bean.getDaisiNoNb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getDaisiNoNb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getUnitPriceTaniKb() != null && bean.getUnitPriceTaniKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getUnitPriceTaniKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getUpUnitPriceNaiyoryoQt() != null && bean.getUpUnitPriceNaiyoryoQt().trim().length() != 0 )
		{
			sb.append( bean.getUpUnitPriceNaiyoryoQt());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getUpUnitPriceKijunTaniQt() != null && bean.getUpUnitPriceKijunTaniQt().trim().length() != 0 )
		{
			sb.append( bean.getUpUnitPriceKijunTaniQt());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getShinazoroeKb() ) + "'");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getKumisuKb() ) + "'");
		sb.append(",");
		if( bean.getNefudaKb() != null && bean.getNefudaKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getNefudaKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getYoridoriKb() != null && bean.getYoridoriKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getYoridoriKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getYoridoriQtString() != null && bean.getYoridoriQtString().trim().length() != 0 )
		{
			sb.append( bean.getYoridoriQtString());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append( bean.getTagHyojiBaikaVlString());
		sb.append(" null ,");
		if( bean.getSeasonCd() != null && bean.getSeasonCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getSeasonCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getHukusyuCd() ) + "'");
//		sb.append("'" + conv.convertString( bean.getStyleCd() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSceneCd() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSexCd() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		if( bean.getAgeCd() != null && bean.getAgeCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getAgeCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getGenerationCd() ) + "'");
		sb.append(" null ,");
		if( bean.getSozaiCd() != null && bean.getSozaiCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getSozaiCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getPatternCd() != null && bean.getPatternCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getPatternCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getOriamiCd() != null && bean.getOriamiCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getOriamiCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("'" + conv.convertString( bean.getHukaKinoCd() ) + "'");
//		sb.append(" null ,");
		if( bean.getHukaKinoCd() != null && bean.getHukaKinoCd().trim().length() != 0 )
		{
			sb.append("'" + StringUtility.charFormat(conv.convertString( bean.getHukaKinoCd().trim() ),3,"0",true) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
		if( bean.getSizeCd() != null && bean.getSizeCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getSizeCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getColorCd() != null && bean.getColorCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getColorCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getSyuzeiHokokuKb() != null && bean.getSyuzeiHokokuKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getSyuzeiHokokuKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getTcJyouhoNa() ) + "'");
		sb.append(" null ,");
		if( bean.getNohinSyohinCd() != null && bean.getNohinSyohinCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getNohinSyohinCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getItfCd() ) + "'");
		sb.append(" null ,");
//		sb.append( bean.getCaseIrisuQtString());
		sb.append(" null ,");
//		sb.append("'" + conv.convertString( bean.getNyukaSyohinCd() ) + "'");
		sb.append(" null ,");
//		sb.append( bean.getPackWidthQtString());
		sb.append(" 0,");
//		sb.append( bean.getPackHeigthQtString());
		sb.append(" 0,");
//		sb.append( bean.getPackDepthQtString());
		sb.append(" 0,");
//		sb.append( bean.getPackWeightQtString());
		sb.append(" 0,");
//		sb.append("'" + conv.convertString( bean.getCenterZaikoKb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getZaikoHachuSaki() ) + "'");
//		sb.append("'" + conv.convertString( bean.getZaikoCenterCd() ) + "'");
//		sb.append( bean.getMinZaikosuQtString());
//		sb.append( bean.getMaxZaikosuQtString());
//		sb.append("'" + conv.convertString( bean.getCenterHachutaniKb() ) + "'");
//		sb.append( bean.getCenterHachutaniQtString());
//		sb.append( bean.getCenterIrisuQtString());
//		sb.append( bean.getCenterWeightQtString());
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");

		if( bean.getTanaNoNbString() != null && bean.getTanaNoNbString().trim().length() != 0 )
		{
			sb.append( bean.getTanaNoNbString());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getDanNoNbString() != null && bean.getDanNoNbString().trim().length() != 0 )
		{
			sb.append( bean.getDanNoNbString());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append( bean.getKeiyakuSuQtString());
//		sb.append("'" + conv.convertString( bean.getKeiyakuPatternKb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getKeiyakuStDt() ) + "'");
//		sb.append("'" + conv.convertString( bean.getKeiyakuEdDt() ) + "'");
//		sb.append( bean.getKijunZaikosuQtString());
//		sb.append("'" + conv.convertString( bean.getDZokusei1Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei1Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei2Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei2Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei3Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei3Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei4Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei4Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei5Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei5Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei6Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei6Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei7Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei7Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei8Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei8Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei9Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei9Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDZokusei10Na() ) + "'");
//		sb.append("'" + conv.convertString( bean.getSZokusei10Na() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSinkiTorokuDt() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSyokaiTorokuTs() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( "0000" + bean.getSyokaiUserId() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getHinbanCd() ) + "'");
		sb.append(",");
		if( bean.getSubclassCd() != null && bean.getSubclassCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getSubclassCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getHaifuCd() != null && bean.getHaifuCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getHaifuCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getGtinCd() != null && bean.getGtinCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getGtinCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getKikaku_Kanji_Na2() != null && bean.getKikaku_Kanji_Na2().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getKikaku_Kanji_Na2() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getKikaku_Kana_Na2() != null && bean.getKikaku_Kana_Na2().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getKikaku_Kana_Na2() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getPbKb() != null && bean.getPbKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getPbKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getKeshiBaikaVlString() != null && bean.getKeshiBaikaVlString().trim().length() != 0 )
		{
			sb.append( bean.getKeshiBaikaVlString());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getTokubetuGenkaVlString() != null && bean.getTokubetuGenkaVlString().trim().length() != 0 )
		{
			sb.append( bean.getTokubetuGenkaVlString());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getPreGentankaVlString() != null && bean.getPreGentankaVlString().trim().length() != 0 )
		{
			sb.append( bean.getPreGentankaVl());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getPreBaitankaVlString() != null && bean.getPreBaitankaVlString().trim().length() != 0 )
		{
			sb.append( bean.getPreBaitankaVl());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getHachutani_Na() != null && bean.getHachutani_Na().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getHachutani_Na() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getCenterKyoyoDt() != null && bean.getCenterKyoyoDt().trim().length() != 0 )
		{
			sb.append( bean.getCenterKyoyoDt());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getSyohiKigenDt() != null && bean.getSyohiKigenDt().trim().length() != 0 )
		{
			sb.append( bean.getSyohiKigenDt());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getNefudaUkewatasiDt() != null && bean.getNefudaUkewatasiDt().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getNefudaUkewatasiDt() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getNefudaUkewatasiKb() != null && bean.getNefudaUkewatasiKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getNefudaUkewatasiKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getYoridoriVl() != null && bean.getYoridoriVl().trim().length() != 0 )
		{
			sb.append( bean.getYoridoriVl());
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getDaichotenpoKb() != null && bean.getDaichotenpoKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getDaichotenpoKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getDaichohonbuKb() ) + "'");
//		sb.append("'" + conv.convertString( bean.getDaichosiiresakiKb() ) + "'");
		sb.append(" null ,");
		sb.append(" null ,");
		if( bean.getSodeCd() != null && bean.getSodeCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getSodeCd() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
//		sb.append("'" + conv.convertString( bean.getHonbuzai_Kb() ) + "'");
		sb.append(" null ,");
		sb.append("'" + conv.convertString( bean.getFujiSyohinKb() ) + "'");
		sb.append(",");
		if( bean.getHacyuSyohinCd() != null && bean.getHacyuSyohinCd().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getHacyuSyohinCd() ) + "'");
			sb.append(",");
		}else{
//			↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
			if(!bean.getSystemKb().equals("F")){
				sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
				sb.append(",");
			}else{
				sb.append(" null ,");
			}
//			↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getYusenBinKb() != null && bean.getYusenBinKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getYusenBinKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		if( bean.getPluKb() != null && bean.getPluKb().trim().length() != 0 )
		{
			sb.append("'" + conv.convertString( bean.getPluKb() ) + "'");
			sb.append(",");
		}else{
			sb.append(" null ,");
		}
		sb.append(" '0' ");
		sb.append(")");
		return sb.toString();
	}


	/**
	 * 挿入用取得カラムＳＱＬの生成を行う。
	 *  
	 * @param colName  カラム名
	 * @param value    画面入力値
	 * @param needQt   クゥオート設定有無
	 * @param conv     DBStringConvert
	 * @param gamenFlg 画面使用項目フラグ
	 * @return String 生成された文字列
	 */
	private String getColumnValue(String colName, String value, boolean needQt, DBStringConvert conv, HashMap gamenFlg) {
		StringBuffer sb = new StringBuffer();
		if (gamenFlg.get(colName) != null) {
			if( value != null && value.trim().length() != 0 )
			{
				if (needQt) {
					sb.append("'" + conv.convertString( value ) + "'");
				} else {
					sb.append(value);
				}
				sb.append(" as ").append(colName);
			} else {
//              ↓↓移植（2006.06.02）↓↓
				sb.append(" cast(null as char) as ").append(colName);
//              ↑↑移植（2006.06.02）↑↑				
			}
		} else {
			sb.append(" max(").append(colName).append(") as ").append(colName);

		}
		return sb.toString();
	}
	/**
	 * 更新用設定カラムＳＱＬの生成を行う。
	 *  
	 * @param colName  カラム名
	 * @param value    画面入力値
	 * @param needQt   クゥオート設定有無
	 * @param conv     DBStringConvert
	 * @param gamenFlg 画面使用項目フラグ
	 * @return String 生成された文字列
	 */
	private String getUpdColumnValue(String colName, String value, boolean needQt, DBStringConvert conv, HashMap gamenFlg, int len) {
		StringBuffer sb = new StringBuffer();
		if (gamenFlg.get(colName) != null) {
			if (len > 0) {
				sb.append(",");
			}
			sb.append(colName).append(" = ");
//			↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
//			if (needQt) {
//				sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(value) ) + "'");
//			} else {
//				if (value != null && value.length() > 0) {
//					sb.append(value);
//				} else {
//					sb.append(" null ");
//				}
//			}
			if (value != null && value.length() > 0) {
				if (needQt) {
					sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(value) ) + "'");
				} else {
					sb.append(value);
				}
			} else {
					sb.append(" null ");
			}
//			↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
		}
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateSql( Object beanMst )
	{
		boolean befKanma = false;
		boolean whereAnd = false;

		mst110101_SyohinBean bean = (mst110101_SyohinBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_syohin set ");
		befKanma = true;

		StringBuffer cols = new StringBuffer();
		cols.append(getUpdColumnValue("unit_cd", bean.getUnitCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hinmei_kanji_na", bean.getHinmeiKanjiNa(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("kikaku_kanji_na", bean.getKikakuKanjiNa(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hinmei_kana_na", bean.getHinmeiKanaNa(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("kikaku_kana_na", bean.getKikakuKanaNa(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("syohin_2_cd", bean.getSyohin2Cd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("siire_hinban_cd", bean.getSiireHinbanCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("bland_cd", bean.getBlandCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("maker_kibo_kakaku_vl", bean.getUpMakerKiboKakakuVl(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_hachu_st_dt", bean.getTenHachuStDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_hachu_ed_dt", bean.getTenHachuEdDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("gentanka_vl", bean.getUpGentankaVl(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("baitanka_vl", bean.getUpBaitankaVl(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hachutani_irisu_qt", bean.getUpHachutaniIrisuQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("max_hachutani_qt", bean.getUpMaxHachutaniQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("eos_kb", bean.getEosKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("siiresaki_cd", bean.getSiiresakiCd(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("daihyo_haiso_cd", bean.getDaihyoHaisoCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_siiresaki_kanri_cd", bean.getTenSiiresakiKanriCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("bin_1_kb", bean.getBin1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hachu_pattern_1_kb", bean.getHachuPattern1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("sime_time_1_qt", bean.getUpSimeTime1Qt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("c_nohin_rtime_1_kb", bean.getCNohinRtime1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_nohin_rtime_1_kb", bean.getTenNohinRtime1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_nohin_time_1_kb", bean.getTenNohinRtime1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("bin_2_kb", bean.getBin2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hachu_pattern_2_kb", bean.getHachuPattern2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("sime_time_2_qt", bean.getUpSimeTime2Qt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("c_nohin_rtime_2_kb", bean.getCNohinRtime2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_nohin_rtime_2_kb", bean.getTenNohinRtime2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_nohin_time_2_kb", bean.getTenNohinRtime2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("bin_3_kb", bean.getBin3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hachu_pattern_3_kb", bean.getHachuPattern3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("sime_time_3_qt", bean.getUpSimeTime3Qt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("c_nohin_rtime_3_kb", bean.getCNohinRtime3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_nohin_rtime_3_kb", bean.getTenNohinRtime3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_nohin_time_3_kb", bean.getTenNohinRtime3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("c_nohin_rtime_kb", bean.getCNohinRtimeKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("buturyu_kb", bean.getButuryuKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_groupno_cd", bean.getTenGroupnoCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_zaiko_kb", bean.getTenZaikoKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hanbai_seisaku_kb", bean.getHanbaiSeisakuKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("henpin_nb", bean.getUpHenpinNb(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hanbai_st_dt", bean.getHanbaiStDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hanbai_ed_dt", bean.getHanbaiEdDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hanbai_limit_qt", bean.getUpHanbaiLimitQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hanbai_limit_kb", bean.getHanbaiLimitKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("got_mujyoken_fg", bean.getGotMujyokenFg(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("got_start_mm", bean.getGotStartMm(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("got_end_mm", bean.getGotEndMm(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("plu_send_dt", bean.getPluSendDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("rec_hinmei_kanji_na", bean.getRecHinmeiKanjiNa(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("rec_hinmei_kana_na", bean.getRecHinmeiKanaNa(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("pc_kb", bean.getPcKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("daisi_no_nb", bean.getDaisiNoNb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("unit_price_tani_kb", bean.getUnitPriceTaniKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("unit_price_naiyoryo_qt", bean.getUpUnitPriceNaiyoryoQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("unit_price_kijun_tani_qt",bean.getUpUnitPriceKijunTaniQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("shinazoroe_kb", bean.getShinazoroeKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("nefuda_kb", bean.getNefudaKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("yoridori_kb", bean.getYoridoriKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("yoridori_qt", bean.getYoridoriQtString(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("tag_hyoji_baika_vl", bean.getUpTagHyojiBaikaVl(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("season_cd", bean.getSeasonCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hukusyu_cd", bean.getHukusyuCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("style_cd", bean.getStyleCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("scene_cd", bean.getSceneCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("sex_cd", bean.getSexCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("age_cd", bean.getAgeCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("generation_cd", bean.getGenerationCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("sozai_cd", bean.getSozaiCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("pattern_cd", bean.getPatternCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("oriami_cd", bean.getOriamiCd(), true, conv, bean.getGamenFlg(), cols.length()));
//		↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
//		cols.append(getUpdColumnValue("huka_kino_cd", bean.getHukaKinoCd(), true, conv, bean.getGamenFlg(), cols.length()));
		if( bean.getHukaKinoCd() != null && bean.getHukaKinoCd().trim().length() != 0 ){
			cols.append(getUpdColumnValue("huka_kino_cd", StringUtility.charFormat(conv.convertString( bean.getHukaKinoCd().trim() ),3,"0",true), true, conv, bean.getGamenFlg(), cols.length()));
		} else {
			cols.append(getUpdColumnValue("huka_kino_cd", bean.getHukaKinoCd(), true, conv, bean.getGamenFlg(), cols.length()));
		}
//		↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
		cols.append(getUpdColumnValue("size_cd", bean.getSizeCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("color_cd", bean.getColorCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("syuzei_hokoku_kb", bean.getSyuzeiHokokuKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("tc_jyouho_na", bean.getTcJyouhoNa(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("nohin_syohin_cd", bean.getNohinSyohinCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("itf_cd", bean.getItfCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("case_irisu_qt", bean.getUpCaseIrisuQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("nyuka_syohin_cd", bean.getNyukaSyohinCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("pack_width_qt", bean.getUpPackWidthQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("pack_heigth_qt", bean.getUpPackHeigthQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("pack_depth_qt", bean.getUpPackDepthQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("pack_weight_qt", bean.getUpPackDepthQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("center_zaiko_kb", bean.getCenterZaikoKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("zaiko_hachu_saki", bean.getZaikoHachuSaki(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("zaiko_center_cd", bean.getZaikoCenterCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("min_zaikosu_qt", bean.getUpMinZaikosuQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("max_zaikosu_qt", bean.getUpMaxZaikosuQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("center_hachutani_kb", bean.getCenterHachutaniKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("center_hachutani_qt", bean.getUpCenterHachutaniQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("center_irisu_qt", bean.getUpCenterIrisuQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("center_weight_qt", bean.getUpCenterWeightQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("tana_no_nb", bean.getUpTanaNoNb(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("dan_no_nb", bean.getUpDanNoNb(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("keiyaku_su_qt", bean.getUpKeiyakuSuQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("keiyaku_pattern_kb", bean.getKeiyakuPatternKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("keiyaku_st_dt", bean.getKeiyakuStDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("keiyaku_ed_dt", bean.getKeiyakuEdDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("kijun_zaikosu_qt", bean.getUpKijunZaikosuQt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_1_na", bean.getDZokusei1Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_1_na", bean.getSZokusei1Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_2_na", bean.getDZokusei2Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_2_na", bean.getSZokusei2Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_3_na", bean.getDZokusei3Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_3_na", bean.getSZokusei3Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_4_na", bean.getDZokusei4Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_4_na", bean.getSZokusei4Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_5_na", bean.getDZokusei5Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_5_na", bean.getSZokusei5Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_6_na", bean.getDZokusei6Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_6_na", bean.getSZokusei6Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_7_na", bean.getDZokusei7Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_7_na", bean.getSZokusei7Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_8_na", bean.getDZokusei8Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_8_na", bean.getSZokusei8Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_9_na", bean.getDZokusei9Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_9_na", bean.getSZokusei9Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("d_zokusei_10_na", bean.getDZokusei10Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("s_zokusei_10_na", bean.getSZokusei10Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("henko_dt", bean.getHenkoDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("update_ts", bean.getUpdateTs(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("update_user_id", bean.getUpdateUserId(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("delete_fg", bean.getDeleteFg(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("sinki_toroku_dt", bean.getSinkiTorokuDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("syokai_toroku_ts", bean.getSyokaiTorokuTs(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("syokai_user_id", "0000" + bean.getSyokaiUserId(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hinban_cd", bean.getHinbanCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("subclass_cd", bean.getSubclassCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("haifu_cd", bean.getHaifuCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("gtin_cd", bean.getGtinCd(), true, conv, bean.getGamenFlg(), cols.length()));
//		↓↓2006.07.12 xubq カスタマイズ修正↓↓
//		cols.append(getUpdColumnValue("area_cd", bean.getAreaCd(), true, conv, bean.getGamenFlg(), cols.length()));
//		↑↑2006.07.12 xubq カスタマイズ修正↑↑		
		cols.append(getUpdColumnValue("kikaku_kanji_2_na", bean.getKikaku_Kanji_Na2(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("kikaku_kana_2_na", bean.getKikaku_Kana_Na2(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("pb_kb", bean.getPbKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("keshi_baika_vl", bean.getKeshiBaikaVl(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("tokubetu_genka_vl", bean.getTokubetuGenkaVl(), false, conv, bean.getGamenFlg(), cols.length()));
//		NULL にしたため変更　by kema 06.09.25
		cols.append(getUpdColumnValue("pre_gentanka_vl", bean.getPreGentankaVl(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("pre_baitanka_vl", bean.getPreBaitankaVl(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("hachu_tani_na", bean.getHachutani_Na(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("center_kyoyo_dt", bean.getCenterKyoyoDt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("syohi_kigen_dt", bean.getSyohiKigenDt(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("nefuda_ukewatasi_dt", bean.getNefudaUkewatasiDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("nefuda_ukewatasi_kb", bean.getNefudaUkewatasiKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("yoridori_vl", bean.getYoridoriVl(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("daicho_tenpo_kb", bean.getDaichotenpoKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("daicho_honbu_kb", bean.getDaichohonbuKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("daicho_siiresaki_kb", bean.getDaichosiiresakiKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("sode_cd", bean.getSodeCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("honbu_zai_kb", bean.getHonbuzai_Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("fuji_syohin_kb", bean.getFujiSyohinKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("yusen_bin_kb", bean.getYusenBinKb(), true, conv, bean.getGamenFlg(), cols.length()));
//		↓↓2006.10.21 H.Yamamoto カスタマイズ修正↓↓
		cols.append(getUpdColumnValue("plu_kb", bean.getPluKb(), true, conv, bean.getGamenFlg(), cols.length()));
//		↑↑2006.10.21 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.11.22 H.Yamamoto カスタマイズ修正↓↓
		cols.append(getUpdColumnValue("maker_cd", bean.getMakerCd(), true, conv, bean.getGamenFlg(), cols.length()));
//		↑↑2006.11.22 H.Yamamoto カスタマイズ修正↑↑
		
		
		sb.append(cols.toString());
		sb.append(" WHERE");

		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" bumon_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" yuko_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		}
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql( Object beanMst )
	{
		mst110101_SyohinBean bean = (mst110101_SyohinBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_syohin where ");
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		return sb.toString();
	}

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

//	↓↓2006.10.18 H.Yamamoto レスポンス対策修正↓↓
	/**
	 * 商品チェック用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSyohinCheckSql( Map map )
	{

		String whereStr = "where ";
		String andStr = " and ";
		String wk = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("bumon_cd ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("yuko_dt ");
		sb.append(", ");
		sb.append("system_kb ");
		sb.append(", ");
		sb.append("gyosyu_kb ");
		sb.append(", ");
		sb.append("syohin_2_cd ");
		sb.append(", ");
		sb.append("hinmei_kanji_na ");
		sb.append(", ");
		sb.append("henko_dt ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append(", ");
		sb.append("sinki_toroku_dt ");
		sb.append(", ");
		sb.append("syokai_toroku_ts ");
		sb.append(", ");
		sb.append("substring(syokai_user_id,5,6) as syokai_user_id ");
		sb.append("from R_SYOHIN ");
		
		wk = getWhereSqlStr(map, "bumon_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syohin_cd", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "yuko_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "system_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "gyosyu_kb", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "insert_ts", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "insert_user_id", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "update_ts", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "update_user_id", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "delete_fg", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "sinki_toroku_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "henko_dt", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syokai_toroku_ts", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}
		wk = getWhereSqlStr(map, "syokai_user_id", whereStr);
		if(!wk.equals("")){
			sb.append(wk);
			whereStr = andStr;
		}

		sb.append(" order by ");
		sb.append("delete_fg");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(" desc");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("yuko_dt");
		sb.append(" fetch first 1 rows only ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}
//	↑↑2006.10.18 H.Yamamoto レスポンス対策修正↑↑

}
