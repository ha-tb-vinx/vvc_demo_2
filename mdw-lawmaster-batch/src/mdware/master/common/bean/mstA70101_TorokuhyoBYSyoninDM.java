/** * <p>タイトル: 登録票BY承認のDM</p>
 * <p>説明: 登録票BY承認のDMクラス</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author hujh
 * @version 1.0(2006/06/27)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/** * <p>タイトル: 登録票BY承認のDM</p> 
 * <p>説明: 登録票BY承認のDMクラス</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author hujh
 * @version 1.0(2006/06/27)初版作成
 */
public class mstA70101_TorokuhyoBYSyoninDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mstA70101_TorokuhyoBYSyoninDM()
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
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
		mstA70301_KeepMeisaiBean bean = new mstA70301_KeepMeisaiBean();
		bean.setTorikomiDt( rest.getString("torikomi_dt") );
		bean.setExcelFileSyubetu( rest.getString("excel_file_syubetsu") );
		bean.setUketsukeNo( rest.getString("uketsuke_no") );
		bean.setSiireskiKanjiNa( rest.getString("siiresaki_na") );
		bean.setSiireskiKanjiCd( rest.getString("siiresaki_cd") );
		bean.setUploadcommenttx( rest.getString("upload_comment_tx") );
		bean.setTorokuSyoninTs( rest.getString("toroku_syonin_ts") );
		bean.setByNo( rest.getString("by_no") );
		bean.setSyonincommenttx( rest.getString("syonin_comment_tx") );
		bean.setExcelSyohinKb( rest.getString("excel_syohin_kb") );
		bean.setExcelTanpinKb( rest.getString("excel_tanpin_kb") );
		bean.setExcelReigaiKb( rest.getString("excel_reigai_kb") );
		bean.setExcelSyokaiKb( rest.getString("excel_syokai_kb") );
		bean.setMinNeireRt( rest.getString("min_neire_rt") );
		bean.setMaxNeireRt( rest.getString("max_neire_rt") );
		bean.setMaxUritankaVl( rest.getString("max_uritanka_vl") );
		bean.setTorokuSyoninFg( rest.getString("toroku_syonin_fg") );
//		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
		bean.setInsertTs( rest.getString("insert_ts") );
//		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setKensu( rest.getString("seqmax") );
		bean.setSyuseiKb( rest.getString("syusei_kb") );
		// ===BEGIN=== Add by kou 2006/8/16
		bean.setTanpinSyuseiKb( rest.getString("TANPIN_SYUSEI_KB"));
		bean.setTanpinKensu( rest.getString("TANPIN_MAX"));
		bean.setReigaiSyuseiKb( rest.getString("REIGAI_SYUSEI_KB"));
		bean.setReigaiKensu(rest.getString("REIGAI_MAX"));
		bean.setSyokaiSyuseiKb(rest.getString("SYOKAI_SYUSEI_KB"));
		bean.setSyokaiKensu(rest.getString("SYOKAI_MAX"));
		// ===END=== Add by kou 2006/8/16
		bean.setTblNm( rest.getString("tblname") );
		bean.setSentaku( rest.getString("sentaku") );
		bean.setRadioflag( rest.getString("radioflag") );
		bean.setTorokuSyoninFgShoki( rest.getString("toroku_syonin_fg_shoki") );
//		↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
		bean.setSyoriJyotaiFg( rest.getString("syori_jyotai_fg") );
//		↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
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
		String sb = "";
		sb = getSelectAllSql( map );
		return sb;
	}
	
	/**
	 * CSV用検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectEachSql( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		
		String sb = "";
		if ("a08".equals(bean.getSelectFlg())) {
			sb = getSelectA08( bean );
		} else if ("a07".equals(bean.getSelectFlg())) {
			sb = getSelectA07( bean );
		} else if ("gro".equals(bean.getSelectFlg())) {
			sb = getSelectGro( bean );
		} else if ("fre".equals(bean.getSelectFlg())) {
			sb = getSelectFre( bean );
		} else if ("ttt".equals(bean.getSelectFlg())) {
			sb = getSelectTtt( bean );
		} else if ("ttr".equals(bean.getSelectFlg())) {
			sb = getSelectTtr( bean );
		} else {
			sb = getSelectTs( bean );
		}
		return sb;
	}
	
	/**
	 * CSV書出用ＳＱＬ
	 * 商品マスタTR（TAG）
	 * @param beanMst mstA70301_KeepMeisaiBean
	 * @return String 生成されたＳＱＬ
	 */
	private String getSelectA08( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append(" 		a08.bumon_cd a08_bumon_cd ");
		sb.append("		, ");
		sb.append(" 		a08.unit_cd a08_unit_cd ");
		sb.append("		, ");
		sb.append(" 		a08.haifu_cd a08_haifu_cd ");
		sb.append("		, ");
		sb.append(" 		a08.subclass_cd a08_subclass_cd ");
		sb.append("		, ");
		sb.append(" 		a08.syohin_cd a08_syohin_cd ");
		sb.append("		, ");
		sb.append(" 		a08.yuko_dt a08_yuko_dt ");
		sb.append("		, ");
		sb.append(" 		a08.hinmei_kanji_na a08_hinmei_kanji_na ");
		sb.append("		, ");
		sb.append(" 		a08.hinmei_kana_na a08_hinmei_kana_na ");
		sb.append("		, ");
		sb.append(" 		a08.size_cd a08_size_cd ");
		sb.append("		, ");
		sb.append(" 		a08.color_cd a08_color_cd ");
		sb.append("		, ");
		sb.append(" 		a08.gentanka_vl a08_gentanka_vl ");
		sb.append("		, ");
		sb.append(" 		a08.baitanka_vl a08_baitanka_vl ");
		sb.append("		, ");
		sb.append(" 		a08.keshi_baika_vl a08_keshi_baika_vl ");
		sb.append("		, ");
		sb.append(" 		a08.tokubetu_genka_vl a08_tokubetu_genka_vl ");
		sb.append("		, ");
		sb.append(" 		a08.keiyakusu_qt a08_keiyakusu_qt ");
		sb.append("		, ");
		sb.append(" 		a08.tuika_keiyakusu_qt a08_tuika_keiyakusu_qt ");
		sb.append("		, ");
		sb.append(" 		a08.siiresaki_cd a08_siiresaki_cd ");
		sb.append("		, ");
		sb.append(" 		a08.siire_hinban_cd a08_siire_hinban_cd ");
		sb.append("		, ");
		sb.append(" 		a08.hanbai_st_dt a08_hanbai_st_dt ");
		sb.append("		, ");
		sb.append(" 		a08.hanbai_ed_dt a08_hanbai_ed_dt ");
		sb.append("		, ");
		sb.append(" 		a08.ten_hachu_st_dt a08_ten_hachu_st_dt ");
		sb.append("		, ");
		sb.append(" 		a08.ten_hachu_ed_dt a08_ten_hachu_ed_dt ");
		sb.append("		, ");
		sb.append(" 		a08.eos_kb a08_eos_kb ");
		sb.append("		, ");
		sb.append(" 		a08.season_cd a08_season_cd ");
		sb.append("		, ");
		sb.append(" 		a08.bland_cd a08_bland_cd ");
		sb.append("		, ");
		sb.append(" 		a08.pb_kb a08_pb_kb ");
		sb.append("		, ");
		sb.append(" 		a08.yoridori_vl a08_yoridori_vl ");
		sb.append("		, ");
		sb.append(" 		a08.yoridori_qt a08_yoridori_qt ");
		sb.append("		, ");
		sb.append(" 		a08.yoridori_kb a08_yoridori_kb ");
		sb.append("		, ");
		sb.append(" 		a08.nefuda_kb a08_nefuda_kb ");
		sb.append("		, ");
		sb.append(" 		a08.nefuda_ukewatasi_dt a08_nefuda_ukewatasi_dt ");
		sb.append("		, ");
		sb.append(" 		a08.nefuda_ukewatasi_kb a08_nefuda_ukewatasi_kb ");
		sb.append("		, ");
		sb.append(" 		a08.fuji_syohin_kb a08_fuji_syohin_kb ");
		sb.append("		, ");
		sb.append(" 		a08.sozai_cd a08_sozai_cd ");
		sb.append("		, ");
		sb.append(" 		a08.oriami_cd a08_oriami_cd ");
		sb.append("		, ");
		sb.append(" 		a08.sode_cd a08_sode_cd ");
		sb.append("		, ");
		sb.append(" 		a08.age_cd a08_age_cd ");
		sb.append("		, ");
		sb.append(" 		a08.pattern_cd a08_pattern_cd ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 		a08.sakusei_gyo_no a08_sakusei_gyo_no ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 		a08.torikomi_dt torikomi_dt ");
		sb.append("		, ");
		sb.append(" 		a08.excel_file_syubetsu excel_file_syubetsu ");
		sb.append("		, ");
		sb.append(" 		a08.uketsuke_no uketsuke_no ");
		sb.append("		, ");
		sb.append(" 		'01' sheet_syubetsu ");
		sb.append("		, ");
		sb.append(" 		a08.uketsuke_seq uketsuke_seq ");
//		↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from  ");
		sb.append("		tr_syohin_a08 a08  ");
		sb.append("where  ");
		sb.append("		a08.torikomi_dt  ='" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append("and  ");
		sb.append("		a08.uketsuke_no  ='" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	order by");
		sb.append("		 a08.sakusei_gyo_no, ");
		sb.append("		 a08.uketsuke_seq ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
		return sb.toString();
	}
	
	/**
	 * CSV書出用ＳＱＬ
	 * 商品マスタTR（実用））
	 * @param beanMst mstA70301_KeepMeisaiBean
	 * @return String 生成されたＳＱＬ
	 */
	private String getSelectA07( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append("		 a07.bumon_cd a07_bumon_cd ");
		sb.append("		, ");
		sb.append("		 a07.unit_cd a07_unit_cd ");
		sb.append("		, ");
		sb.append("		 a07.haifu_cd a07_haifu_cd ");
		sb.append("		, ");
		sb.append("		 a07.subclass_cd a07_subclass_cd ");
		sb.append("		, ");
		sb.append("		 a07.syohin_cd a07_syohin_cd ");
		sb.append("		, ");
		sb.append("		 a07.yuko_dt a07_yuko_dt ");
		sb.append("		, ");
		sb.append("		 a07.maker_cd a07_maker_cd ");
		sb.append("		, ");
		sb.append("		 a07.pos_cd a07_pos_cd ");
		sb.append("		, ");
		sb.append("		 a07.hinmei_kanji_na a07_hinmei_kanji_na ");
		sb.append("		, ");
		sb.append("		 a07.kikaku_kanji_na a07_kikaku_kanji_na ");
		sb.append("		, ");
		sb.append("		 a07.kikaku_kanji_2_na a07_kikaku_kanji_2_na ");
		sb.append("		, ");
		sb.append("		 a07.hinmei_kana_na a07_hinmei_kana_na ");
		sb.append("		, ");
		sb.append("		 a07.kikaku_kana_na a07_kikaku_kana_na ");
		sb.append("		, ");
		sb.append("		 a07.kikaku_kana_2_na a07_kikaku_kana_2_na ");
		sb.append("		, ");
		sb.append("		 a07.rec_hinmei_kanji_na a07_rec_hinmei_kanji_na ");
		sb.append("		, ");
		sb.append("		 a07.rec_hinmei_kana_na a07_rec_hinmei_kana_na ");
		sb.append("		, ");
		sb.append("		 a07.size_cd a07_size_cd ");
		sb.append("		, ");
		sb.append("		 a07.color_cd a07_color_cd ");
		sb.append("		, ");
		sb.append("		 a07.gentanka_vl a07_gentanka_vl ");
		sb.append("		, ");
		sb.append("		 a07.baitanka_vl a07_baitanka_vl ");
		sb.append("		, ");
		sb.append("		 a07.keshi_baika_vl a07_keshi_baika_vl ");
		sb.append("		, ");
		sb.append("		 a07.keiyakusu_qt a07_keiyakusu_qt ");
		sb.append("		, ");
		sb.append("		 a07.tuika_keiyakusu_qt a07_tuika_keiyakusu_qt ");
		sb.append("		, ");
		sb.append("		 a07.hachutani_irisu_qt a07_hachutani_irisu_qt ");
		sb.append("		, ");
		sb.append("		 a07.hachu_tani_na a07_hachu_tani_na ");
		sb.append("		, ");
		sb.append("		 a07.plu_send_dt a07_plu_send_dt ");
		sb.append("		, ");
		sb.append("		 a07.siiresaki_cd a07_siiresaki_cd ");
		sb.append("		, ");
		sb.append("		 a07.siire_hinban_cd a07_siire_hinban_cd ");
		sb.append("		, ");
		sb.append("		 a07.hanbai_st_dt a07_hanbai_st_dt ");
		sb.append("		, ");
		sb.append("		 a07.hanbai_ed_dt a07_hanbai_ed_dt ");
		sb.append("		, ");
		sb.append("		 a07.ten_hachu_st_dt a07_ten_hachu_st_dt ");
		sb.append("		, ");
		sb.append("		 a07.ten_hachu_ed_dt a07_ten_hachu_ed_dt ");
		sb.append("		, ");
		sb.append("		 a07.eos_kb a07_eos_kb ");
		sb.append("		, ");
		sb.append("		 a07.season_cd a07_season_cd ");
		sb.append("		, ");
		sb.append("		 a07.bland_cd a07_bland_cd ");
		sb.append("		, ");
		sb.append("		 a07.pb_kb a07_pb_kb ");
		sb.append("		, ");
		sb.append("		 a07.yoridori_vl a07_yoridori_vl ");
		sb.append("		, ");
		sb.append("		 a07.yoridori_qt a07_yoridori_qt ");
		sb.append("		, ");
		sb.append("		 a07.yoridori_kb a07_yoridori_kb ");
		sb.append("		, ");
		sb.append("		 a07.tana_no_nb a07_tana_no_nb ");
		sb.append("		, ");
		sb.append("		 a07.nefuda_kb a07_nefuda_kb ");
		sb.append("		, ");
		sb.append("		 a07.nefuda_ukewatasi_dt a07_nefuda_ukewatasi_dt ");
		sb.append("		, ");
		sb.append("		 a07.nefuda_ukewatasi_kb a07_nefuda_ukewatasi_kb ");
		sb.append("		, ");
		sb.append("		 a07.fuji_syohin_kb a07_fuji_syohin_kb ");
		sb.append("		, ");
		sb.append("		 a07.pc_kb a07_pc_kb ");
		sb.append("		, ");
		sb.append("		 a07.daisi_no_nb a07_daisi_no_nb ");
		sb.append("		, ");
		sb.append("		 a07.syusei_kb a07_syusei_kb ");
		sb.append("		, ");
		sb.append("		 a07.sakusei_gyo_no a07_sakusei_gyo_no ");
		sb.append("		, ");
		sb.append("		 a07.mst_mainte_fg a07_mst_mainte_fg ");
		sb.append("		, ");
		sb.append("		 a07.alarm_make_fg a07_alarm_make_fg ");
		sb.append("		, ");
		sb.append("		 a07.insert_ts a07_insert_ts ");
		sb.append("		, ");
		sb.append("		 a07.insert_user_id a07_insert_user_id ");
		sb.append("		, ");
		sb.append("		 a07.update_ts a07_update_ts ");
		sb.append("		, ");
		sb.append("		 a07.update_user_id a07_update_user_id ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 	 a07.sakusei_gyo_no a07_sakusei_gyo_no ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 		a07.torikomi_dt torikomi_dt ");
		sb.append("		, ");
		sb.append(" 		a07.excel_file_syubetsu excel_file_syubetsu ");
		sb.append("		, ");
		sb.append(" 		a07.uketsuke_no uketsuke_no ");
		sb.append("		, ");
		sb.append(" 		'01' sheet_syubetsu ");
		sb.append("		, ");
		sb.append(" 		a07.uketsuke_seq uketsuke_seq ");
//		↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from  ");
		sb.append("		tr_syohin_a07 a07  ");
		sb.append("where  ");
		sb.append("		a07.torikomi_dt  ='" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append("and  ");
		sb.append("		a07.uketsuke_no  ='" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	order by");
		sb.append("		 a07.sakusei_gyo_no, ");
		sb.append("		 a07.uketsuke_seq ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
		
		return sb.toString();
	}
	
	/**
	 * CSV書出用ＳＱＬ
	 * 商品マスタTR（グロサリー/バラエティ）
	 * @param beanMst mstA70301_KeepMeisaiBean
	 * @return String 生成されたＳＱＬ
	 */
	private String getSelectGro( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append("		 gro.bumon_cd gro_bumon_cd ");
		sb.append("		, ");
		sb.append("		 gro.unit_cd gro_unit_cd ");
		sb.append("		, ");
		sb.append("		 gro.haifu_cd gro_haifu_cd ");
		sb.append("		, ");
		sb.append("		 gro.subclass_cd gro_subclass_cd ");
		sb.append("		, ");
		sb.append("		 gro.syohin_cd gro_syohin_cd ");
		sb.append("		, ");
		sb.append("		 gro.yuko_dt gro_yuko_dt ");
		sb.append("		, ");
		sb.append("		 gro.maker_cd gro_maker_cd ");
		sb.append("		, ");
		sb.append("		 gro.hinmei_kanji_na gro_hinmei_kanji_na ");
		sb.append("		, ");
		sb.append("		 gro.kikaku_kanji_na gro_kikaku_kanji_na ");
		sb.append("		, ");
		sb.append("		 gro.kikaku_kanji_2_na gro_kikaku_kanji_2_na ");
		sb.append("		, ");
		sb.append("		 gro.hinmei_kana_na gro_hinmei_kana_na ");
		sb.append("		, ");
		sb.append("		 gro.kikaku_kana_na gro_kikaku_kana_na ");
		sb.append("		, ");
		sb.append("		 gro.kikaku_kana_2_na gro_kikaku_kana_2_na ");
		sb.append("		, ");
		sb.append("		 gro.rec_hinmei_kanji_na gro_rec_hinmei_kanji_na ");
		sb.append("		, ");
		sb.append("		 gro.rec_hinmei_kana_na gro_rec_hinmei_kana_na ");
		sb.append("		, ");
		sb.append("		 gro.gentanka_vl gro_gentanka_vl ");
		sb.append("		, ");
		sb.append("		 gro.baitanka_vl gro_baitanka_vl ");
		sb.append("		, ");
		sb.append("		 gro.plu_send_dt gro_plu_send_dt ");
		sb.append("		, ");
		sb.append("		 gro.siiresaki_cd gro_siiresaki_cd ");
		sb.append("		, ");
		sb.append("		 gro.ten_siiresaki_kanri_cd gro_ten_siiresaki_kanri_cd ");
		sb.append("		, ");
		sb.append("		 gro.siire_hinban_cd gro_siire_hinban_cd ");
		sb.append("		, ");
		sb.append("		 gro.hanbai_st_dt gro_hanbai_st_dt ");
		sb.append("		, ");
		sb.append("		 gro.hanbai_ed_dt gro_hanbai_ed_dt ");
		sb.append("		, ");
		sb.append("		 gro.ten_hachu_st_dt gro_ten_hachu_st_dt ");
		sb.append("		, ");
		sb.append("		 gro.ten_hachu_ed_dt gro_ten_hachu_ed_dt ");
		sb.append("		, ");
		sb.append("		 gro.eos_kb gro_eos_kb ");
		sb.append("		, ");
		sb.append("		 gro.honbu_zai_kb gro_honbu_zai_kb ");
		sb.append("		, ");
		sb.append("		 gro.hachu_tani_na gro_hachu_tani_na ");
		sb.append("		, ");
		sb.append("		 gro.hachutani_irisu_qt gro_hachutani_irisu_qt ");
		sb.append("		, ");
		sb.append("		 gro.max_hachutani_qt gro_max_hachutani_qt ");
		sb.append("		, ");
		sb.append("		 gro.bland_cd gro_bland_cd ");
		sb.append("		, ");
		sb.append("		 gro.pb_kb gro_pb_kb ");
		sb.append("		, ");
		sb.append("		 gro.maker_kibo_kakaku_vl gro_maker_kibo_kakaku_vl ");
		sb.append("		, ");
		sb.append("		 gro.fuji_syohin_kb gro_fuji_syohin_kb ");
		sb.append("		, ");
		sb.append("		 gro.pc_kb gro_pc_kb ");
		sb.append("		, ");
		sb.append("		 gro.daisi_no_nb gro_daisi_no_nb ");
		sb.append("		, ");
		sb.append("		 gro.daicho_tenpo_kb gro_daicho_tenpo_kb ");
		sb.append("		, ");
		sb.append("		 gro.daicho_honbu_kb gro_daicho_honbu_kb ");
		sb.append("		, ");
		sb.append("		 gro.daicho_siiresaki_kb gro_daicho_siiresaki_kb ");
		sb.append("		, ");
		sb.append("		 gro.syuzei_hokoku_kb gro_syuzei_hokoku_kb ");
//		↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append("		 gro.syurui_dosuu_qt gro_syurui_dosuu_qt ");
//		↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
		sb.append("		, ");
		sb.append("		 gro.unit_price_tani_kb gro_unit_price_tani_kb ");
		sb.append("		, ");
		sb.append("		 gro.unit_price_naiyoryo_qt gro_unit_price_naiyoryo_qt ");
		sb.append("		, ");
		sb.append("		 gro.unit_price_kijun_tani_qt gro_unit_price_kijun_tani_qt ");
		sb.append("		, ");
		sb.append("		 gro.syohi_kigen_dt gro_syohi_kigen_dt ");
		sb.append("		, ");
		sb.append("		 gro.syusei_kb gro_syusei_kb ");
		sb.append("		, ");
		sb.append("		 gro.sakusei_gyo_no gro_sakusei_gyo_no ");
		sb.append("		, ");
		sb.append("		 gro.mst_mainte_fg gro_mst_mainte_fg ");
		sb.append("		, ");
		sb.append("		 gro.alarm_make_fg gro_alarm_make_fg ");
		sb.append("		, ");
		sb.append("		 gro.insert_ts gro_insert_ts ");
		sb.append("		, ");
		sb.append("		 gro.insert_user_id gro_insert_user_id ");
		sb.append("		, ");
		sb.append("		 gro.update_ts gro_update_ts ");
		sb.append("		, ");
		sb.append("		 gro.update_user_id gro_update_user_id ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 	 gro.sakusei_gyo_no gro_sakusei_gyo_no ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 		gro.torikomi_dt torikomi_dt ");
		sb.append("		, ");
		sb.append(" 		gro.excel_file_syubetsu excel_file_syubetsu ");
		sb.append("		, ");
		sb.append(" 		gro.uketsuke_no uketsuke_no ");
		sb.append("		, ");
		sb.append(" 		'01' sheet_syubetsu ");
		sb.append("		, ");
		sb.append(" 		gro.uketsuke_seq uketsuke_seq ");
//		↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from  ");
		sb.append("		tr_syohin_gro gro  ");
		sb.append("where  ");
		sb.append("		gro.torikomi_dt  ='" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append("and  ");
		sb.append("		gro.uketsuke_no  ='" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	order by");
		sb.append("		 gro.sakusei_gyo_no, ");
		sb.append("		 gro.uketsuke_seq ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
		return sb.toString();
	}
	
	/**
	 * CSV書出用ＳＱＬ
	 * 商品マスタTR（デイリー）
	 * @param beanMst mstA70301_KeepMeisaiBean
	 * @return String 生成されたＳＱＬ
	 */
	private String getSelectFre( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append("		 fre.bumon_cd fre_bumon_cd ");
		sb.append("		, ");
		sb.append("		 fre.unit_cd fre_unit_cd ");
		sb.append("		, ");
		sb.append("		 fre.haifu_cd fre_haifu_cd ");
		sb.append("		, ");
		sb.append("		 fre.subclass_cd fre_subclass_cd ");
		sb.append("		, ");
		sb.append("		 fre.syohin_cd fre_syohin_cd ");
		sb.append("		, ");
		sb.append("		 fre.yuko_dt fre_yuko_dt ");
		sb.append("		, ");
		sb.append("		 fre.maker_cd fre_maker_cd ");
		sb.append("		, ");
		sb.append("		 fre.hinmei_kanji_na fre_hinmei_kanji_na ");
		sb.append("		, ");
		sb.append("		 fre.kikaku_kanji_na fre_kikaku_kanji_na ");
		sb.append("		, ");
		sb.append("		 fre.kikaku_kanji_2_na fre_kikaku_kanji_2_na ");
		sb.append("		, ");
		sb.append("		 fre.hinmei_kana_na fre_hinmei_kana_na ");
		sb.append("		, ");
		sb.append("		 fre.kikaku_kana_na fre_kikaku_kana_na ");
		sb.append("		, ");
		sb.append("		 fre.kikaku_kana_2_na fre_kikaku_kana_2_na ");
		sb.append("		, ");
		sb.append("		 fre.rec_hinmei_kanji_na fre_rec_hinmei_kanji_na ");
		sb.append("		, ");
		sb.append("		 fre.rec_hinmei_kana_na fre_rec_hinmei_kana_na ");
		sb.append("		, ");
		sb.append("		 fre.gentanka_vl fre_gentanka_vl ");
		sb.append("		, ");
		sb.append("		 fre.baitanka_vl fre_baitanka_vl ");
		sb.append("		, ");
		sb.append("		 fre.plu_send_dt fre_plu_send_dt ");
		sb.append("		, ");
		sb.append("		 fre.siiresaki_cd fre_siiresaki_cd ");
		sb.append("		, ");
		sb.append("		 fre.ten_siiresaki_kanri_cd fre_ten_siiresaki_kanri_cd ");
		sb.append("		, ");
		sb.append("		 fre.siire_hinban_cd fre_siire_hinban_cd ");
		sb.append("		, ");
		sb.append("		 fre.hanbai_st_dt fre_hanbai_st_dt ");
		sb.append("		, ");
		sb.append("		 fre.hanbai_ed_dt fre_hanbai_ed_dt ");
		sb.append("		, ");
		sb.append("		 fre.ten_hachu_st_dt fre_ten_hachu_st_dt ");
		sb.append("		, ");
		sb.append("		 fre.ten_hachu_ed_dt fre_ten_hachu_ed_dt ");
		sb.append("		, ");
		sb.append("		 fre.eos_kb fre_eos_kb ");
		sb.append("		, ");
		sb.append("		 fre.honbu_zai_kb fre_honbu_zai_kb ");
		sb.append("		, ");
		sb.append("		 fre.hachu_tani_na fre_hachu_tani_na ");
		sb.append("		, ");
		sb.append("		 fre.hachutani_irisu_qt fre_hachutani_irisu_qt ");
		sb.append("		, ");
		sb.append("		 fre.max_hachutani_qt fre_max_hachutani_qt ");
		sb.append("		, ");
		sb.append("		 fre.bland_cd fre_bland_cd ");
		sb.append("		, ");
		sb.append("		 fre.pb_kb fre_pb_kb ");
		sb.append("		, ");
		sb.append("		 fre.maker_kibo_kakaku_vl fre_maker_kibo_kakaku_vl ");
		sb.append("		, ");
		sb.append("		 fre.bin_1_kb fre_bin_1_kb ");
		sb.append("		, ");
		sb.append("		 fre.bin_2_kb fre_bin_2_kb ");
		sb.append("		, ");
		sb.append("		 fre.yusen_bin_kb fre_yusen_bin_kb ");
		sb.append("		, ");
		sb.append("		 fre.fuji_syohin_kb fre_fuji_syohin_kb ");
		sb.append("		, ");
		sb.append("		 fre.pc_kb fre_pc_kb ");
		sb.append("		, ");
		sb.append("		 fre.daisi_no_nb fre_daisi_no_nb ");
		sb.append("		, ");
		sb.append("		 fre.daicho_tenpo_kb fre_daicho_tenpo_kb ");
		sb.append("		, ");
		sb.append("		 fre.daicho_honbu_kb fre_daicho_honbu_kb ");
		sb.append("		, ");
		sb.append("		 fre.daicho_siiresaki_kb fre_daicho_siiresaki_kb ");
		sb.append("		, ");
		sb.append("		 fre.unit_price_tani_kb fre_unit_price_tani_kb ");
		sb.append("		, ");
		sb.append("		 fre.unit_price_naiyoryo_qt fre_unit_price_naiyoryo_qt ");
		sb.append("		, ");
		sb.append("		 fre.unit_price_kijun_tani_qt fre_unit_price_kijun_tani_qt ");
		sb.append("		, ");
		sb.append("		 fre.syohi_kigen_dt fre_syohi_kigen_dt ");
//		↓↓2006.09.02 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append("		 fre.nyuka_kyoyo_qt fre_nyuka_kyoyo_qt ");
		sb.append("		, ");
		sb.append("		 fre.hanbai_gendo_qt fre_hanbai_gendo_qt ");
//		↑↑2006.09.02 H.Yamamoto カスタマイズ修正↑↑
		sb.append("		, ");
		sb.append("		 fre.syusei_kb fre_syusei_kb ");
		sb.append("		, ");
		sb.append("		 fre.sakusei_gyo_no fre_sakusei_gyo_no ");
		sb.append("		, ");
		sb.append("		 fre.mst_mainte_fg fre_mst_mainte_fg ");
		sb.append("		, ");
		sb.append("		 fre.alarm_make_fg fre_alarm_make_fg ");
		sb.append("		, ");
		sb.append("		 fre.insert_ts fre_insert_ts ");
		sb.append("		, ");
		sb.append("		 fre.insert_user_id fre_insert_user_id ");
		sb.append("		, ");
		sb.append("		 fre.update_ts fre_update_ts ");
		sb.append("		, ");
		sb.append("		 fre.update_user_id fre_update_user_id ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 	 fre.sakusei_gyo_no fre_sakusei_gyo_no ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 		fre.torikomi_dt torikomi_dt ");
		sb.append("		, ");
		sb.append(" 		fre.excel_file_syubetsu excel_file_syubetsu ");
		sb.append("		, ");
		sb.append(" 		fre.uketsuke_no uketsuke_no ");
		sb.append("		, ");
		sb.append(" 		'01' sheet_syubetsu ");
		sb.append("		, ");
		sb.append(" 		fre.uketsuke_seq uketsuke_seq ");
//		↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from  ");
		sb.append("		tr_syohin_fre fre  ");
		sb.append("where  ");
		sb.append("		fre.torikomi_dt  ='" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append("and  ");
		sb.append("		fre.uketsuke_no  ='" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	order by");
		sb.append("		 fre.sakusei_gyo_no, ");
		sb.append("		 fre.uketsuke_seq ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
		return sb.toString();
	}
	
	/**
	 * CSV書出用ＳＱＬ
	 * 単品店取扱マスタTR
	 * @param beanMst mstA70301_KeepMeisaiBean
	 * @return String 生成されたＳＱＬ
	 */
	private String getSelectTtt( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append("		 ttt.bumon_cd bumon_cd ");
		sb.append("		, ");
		sb.append("		 ttt.syohin_cd syohin_cd ");
		sb.append("		, ");
		sb.append("		 ttt.donyu_dt donyu_dt ");
		sb.append("		, ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓(基本情報取得の復活)
//		↓↓2006.09.06 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		 shohin.hinmei_kanji_na hinmei_kanji_na ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		if ("gro".equals(bean.getExcelFileSyubetu()) || "fre".equals(bean.getExcelFileSyubetu())) {
		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase()) || "fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
			sb.append("		, ");
			sb.append("		 shohin.kikaku_kanji_na kikaku_kanji_na ");
		} else {
			sb.append("		, ");
			sb.append("		 shohin.size_cd size_cd ");
			sb.append("		, ");
			sb.append("		 shohin.color_cd color_cd ");
		}
//		sb.append("		 '' hinmei_kanji_na ");
//		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase()) || "fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
//			sb.append("		, ");
//			sb.append("		 '' kikaku_kanji_na ");
//		} else {
//			sb.append("		, ");
//			sb.append("		 '' size_cd ");
//			sb.append("		, ");
//			sb.append("		 '' color_cd ");
//		}
//		↑↑2006.09.06 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑(基本情報取得の復活)
		sb.append("		, ");
		sb.append("		 ttt.tenpo_cd tenpo_cd ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append("		 ttt.sakusei_gyo_no sakusei_gyo_no ");
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append("		 ttt.syohin_gyo_no syohin_gyo_no ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 		ttt.torikomi_dt torikomi_dt ");
		sb.append("		, ");
		sb.append(" 		ttt.excel_file_syubetsu excel_file_syubetsu ");
		sb.append("		, ");
		sb.append(" 		ttt.uketsuke_no uketsuke_no ");
		sb.append("		, ");
		sb.append(" 		'02' sheet_syubetsu ");
		sb.append("		, ");
		sb.append(" 		ttt.uketsuke_seq uketsuke_seq ");
//		↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from  ");
		sb.append("		tr_tanpinten_toriatukai ttt  ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓(基本情報取得の復活)
//		↓↓2006.09.06 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	left outer join	  ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		if ("gro".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_gro shohin ");
//		} else if ("fre".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_fre shohin ");
//		} else if ("a07".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_a07 shohin ");
//		} else  {
//			sb.append("		 tr_syohin_a08 shohin ");
//		}
		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_gro shohin ");
		} else if ("fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_fre shohin ");
		} else if ("a07".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_a07 shohin ");
		} else  {
			sb.append("		 tr_syohin_a08 shohin ");
		}
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
		sb.append("on  ");
		sb.append("		ttt.uketsuke_no  = shohin.uketsuke_no ");
		sb.append("and  ");
		sb.append("		ttt.bumon_cd  = shohin.bumon_cd ");
		sb.append("and  ");
		sb.append("		ttt.syohin_gyo_no  = shohin.sakusei_gyo_no ");
//		↑↑2006.09.06 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑(基本情報取得の復活)
		sb.append("where  ");
		sb.append("		ttt.torikomi_dt  ='" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append("and  ");
		sb.append("		ttt.uketsuke_no  ='" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	order by");
		sb.append("		 ttt.sakusei_gyo_no, ");
		sb.append("		 ttt.uketsuke_seq ");
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}
	
	/**
	 * CSV書出用ＳＱＬ
	 * 店別商品例外マスタTR
	 * @param beanMst mstA70301_KeepMeisaiBean
	 * @return String 生成されたＳＱＬ
	 */
	private String getSelectTtr( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append("		 ttr.bumon_cd bumon_cd ");
		sb.append("		, ");
		sb.append("		 ttr.syohin_cd syohin_cd ");
		sb.append("		, ");
		sb.append("		 ttr.tenpo_cd tenpo_cd ");
		sb.append("		, ");
		sb.append("		 ttr.yuko_dt yuko_dt ");
		sb.append("		, ");
		sb.append("		 ttr.ten_hachu_st_dt ten_hachu_st_dt ");
		sb.append("		, ");
		sb.append("		 ttr.ten_hachu_ed_dt ten_hachu_ed_dt ");
		sb.append("		, ");
		sb.append("		 ttr.gentanka_vl gentanka_vl ");
		sb.append("		, ");
		sb.append("		 ttr.baitanka_vl baitanka_vl ");
		sb.append("		, ");
		sb.append("		 ttr.eos_kb eos_kb ");
		sb.append("		, ");
		sb.append("		 ttr.siiresaki_cd siiresaki_cd ");
		sb.append("		, ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓(基本情報取得の復活)
//		↓↓2006.09.06 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		 shohin.hinmei_kanji_na hinmei_kanji_na ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		if ("gro".equals(bean.getExcelFileSyubetu()) || "fre".equals(bean.getExcelFileSyubetu())) {
		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase()) || "fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
			sb.append("		, ");
			sb.append("		 shohin.kikaku_kanji_na kikaku_kanji_na ");
		} else {
			sb.append("		, ");
			sb.append("		 shohin.size_cd size_cd ");
			sb.append("		, ");
			sb.append("		 shohin.color_cd color_cd ");
		}
		sb.append("		, ");
		sb.append("		 shohin.ten_hachu_st_dt shohin_ten_hachu_st_dt ");
		sb.append("		, ");
		sb.append("		 shohin.ten_hachu_ed_dt shohin_ten_hachu_ed_dt ");
		sb.append("		, ");
		sb.append("		 shohin.gentanka_vl shohin_gentanka_vl ");
		sb.append("		, ");
		sb.append("		 shohin.baitanka_vl shohin_baitanka_vl ");
		sb.append("		, ");
		sb.append("		 shohin.eos_kb shohin_eos_kb ");
		sb.append("		, ");
		sb.append("		 shohin.siiresaki_cd shohin_siiresaki_cd ");
//		sb.append("		 '' hinmei_kanji_na ");
//		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase()) || "fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
//			sb.append("		, ");
//			sb.append("		 '' kikaku_kanji_na ");
//		} else {
//			sb.append("		, ");
//			sb.append("		 '' size_cd ");
//			sb.append("		, ");
//			sb.append("		 '' color_cd ");
//		}
//		sb.append("		, ");
//		sb.append("		 '' shohin_ten_hachu_st_dt ");
//		sb.append("		, ");
//		sb.append("		 '' shohin_ten_hachu_ed_dt ");
//		sb.append("		, ");
//		sb.append("		 '' shohin_gentanka_vl ");
//		sb.append("		, ");
//		sb.append("		 '' shohin_baitanka_vl ");
//		sb.append("		, ");
//		sb.append("		 '' shohin_eos_kb ");
//		sb.append("		, ");
//		sb.append("		 '' shohin_siiresaki_cd ");
//		↑↑2006.09.06 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑(基本情報取得の復活)
//		↓↓2006.07.30 H.Yamamoto カスタマイズ対応↓↓
		sb.append("		, ");
		sb.append("		 ttr.max_hachutani_qt max_hachutani_qt ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		if ("gro".equals(bean.getExcelFileSyubetu()) || "fre".equals(bean.getExcelFileSyubetu())) {
		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase()) || "fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
//			↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
			sb.append("		, ");
//			↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓(基本情報取得の復活)
//			↓↓2006.09.06 H.Yamamoto カスタマイズ修正↓↓
			sb.append("		 shohin.max_hachutani_qt shohin_max_hachutani_qt ");
//			sb.append("		 '' shohin_max_hachutani_qt ");
//			↑↑2006.09.06 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑(基本情報取得の復活)
		}
//		↑↑2006.07.30 H.Yamamoto カスタマイズ対応↑↑
//		↓↓2006.09.02 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append("		 ttr.bin_1_kb bin_1_kb ");
		sb.append("		, ");
		sb.append("		 ttr.bin_2_kb bin_2_kb ");
		sb.append("		, ");
		sb.append("		 ttr.yusen_bin_kb yusen_bin_kb ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append("		 ttr.sakusei_gyo_no sakusei_gyo_no ");
		sb.append("		, ");
		sb.append("		 ttr.syohin_gyo_no syohin_gyo_no ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.09.02 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 		ttr.torikomi_dt torikomi_dt ");
		sb.append("		, ");
		sb.append(" 		ttr.excel_file_syubetsu excel_file_syubetsu ");
		sb.append("		, ");
		sb.append(" 		ttr.uketsuke_no uketsuke_no ");
		sb.append("		, ");
		sb.append(" 		'03' sheet_syubetsu ");
		sb.append("		, ");
		sb.append(" 		ttr.uketsuke_seq uketsuke_seq ");
//		↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from  ");
		sb.append("		tr_tensyohin_reigai ttr  ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓(基本情報取得の復活)
//		↓↓2006.09.06 H.Yamamoto カスタマイズ修正↓↓
		sb.append("left outer join  ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		if ("gro".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_gro shohin ");
//		} else if ("fre".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_fre shohin ");
//		} else if ("a07".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_a07 shohin ");
//		} else  {
//			sb.append("		 tr_syohin_a08 shohin ");
//		}
		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_gro shohin ");
		} else if ("fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_fre shohin ");
		} else if ("a07".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_a07 shohin ");
		} else  {
			sb.append("		 tr_syohin_a08 shohin ");
		}
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
		sb.append("on  ");
		sb.append("		ttr.uketsuke_no  = shohin.uketsuke_no ");
		sb.append("and  ");
		sb.append("		ttr.bumon_cd  = shohin.bumon_cd ");
		sb.append("and  ");
		sb.append("		ttr.syohin_gyo_no  = shohin.sakusei_gyo_no ");
//		↑↑2006.09.06 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑(基本情報取得の復活)
		sb.append("where  ");
		sb.append("		ttr.torikomi_dt  ='" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append("and  ");
		sb.append("		ttr.uketsuke_no  ='" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	order by");
		sb.append("		 ttr.sakusei_gyo_no, ");
		sb.append("		 ttr.uketsuke_seq ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}
	
	/**
	 * CSV書出用ＳＱＬ
	 * 初回導入マスタTR
	 * @param beanMst mstA70301_KeepMeisaiBean
	 * @return String 生成されたＳＱＬ
	 */
	private String getSelectTs( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append("		 ts.bumon_cd bumon_cd ");
		sb.append("		, ");
		sb.append("		 ts.syohin_cd syohin_cd ");
		sb.append("		, ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓(基本情報取得の復活)
//		↓↓2006.09.06 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		 shohin.hinmei_kanji_na hinmei_kanji_na ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		if ("gro".equals(bean.getExcelFileSyubetu()) || "fre".equals(bean.getExcelFileSyubetu())) {
		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase()) || "fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
			sb.append("		, ");
			sb.append("		 shohin.kikaku_kanji_na kikaku_kanji_na ");
		} else {
			sb.append("		, ");
			sb.append("		 shohin.size_cd size_cd ");
			sb.append("		, ");
			sb.append("		 shohin.color_cd color_cd ");
		}
		sb.append("		, ");
		sb.append("		 shohin.gentanka_vl shohin_gentanka_vl ");
		sb.append("		, ");
		sb.append("		 shohin.baitanka_vl shohin_baitanka_vl ");
		
		//===BEGIN=== Add by kou 2006/8/15
		sb.append("		, ");
		sb.append("		 shohin.siiresaki_cd shohin_siiresaki_cd ");
		// ===END=== Add by kou 2006/8/15
//		sb.append("		 '' hinmei_kanji_na ");
//		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase()) || "fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
//			sb.append("		, ");
//			sb.append("		 '' kikaku_kanji_na ");
//		} else {
//			sb.append("		, ");
//			sb.append("		 '' size_cd ");
//			sb.append("		, ");
//			sb.append("		 '' color_cd ");
//		}
//		sb.append("		, ");
//		sb.append("		 '' shohin_gentanka_vl ");
//		sb.append("		, ");
//		sb.append("		 '' shohin_baitanka_vl ");
//		
//		sb.append("		, ");
//		sb.append("		 '' shohin_siiresaki_cd ");
//		↑↑2006.09.06 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑(基本情報取得の復活)
		
		sb.append("		, ");
		sb.append("		 ts.hatyu_dt hatyu_dt ");
		sb.append("		, ");
		sb.append("		 ts.nohin_dt nohin_dt ");
		sb.append("		, ");
		sb.append("		 ts.hanbai_st_dt hanbai_st_dt ");	
		sb.append("		, ");
		sb.append("		 ts.hanbai_ed_dt hanbai_ed_dt ");	
		sb.append("		, ");
		sb.append("		 ts.gentanka_vl gentanka_vl ");
		sb.append("		, ");
		sb.append("		 ts.baitanka_vl baitanka_vl ");
		sb.append("		, ");
		sb.append("		 ts.tenpo_cd tenpo_cd ");
		sb.append("		, ");
		sb.append("		 ts.suryo_qt suryo_qt ");		
//		↓↓2006.07.30 H.Yamamoto カスタマイズ対応↓↓
		sb.append("		, ");
		sb.append("		 ts.hachuno_cd hachuno_cd ");
		sb.append("		, ");
		sb.append("		 ts.hachutani_qt hachutani_qt ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		if ("a07".equals(bean.getExcelFileSyubetu())) {
		if ("a07".equals(bean.getExcelFileSyubetu().toLowerCase())) {
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
			sb.append("		, ");
//			↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓(基本情報取得の復活)
//			↓↓2006.09.06 H.Yamamoto カスタマイズ修正↓↓
			sb.append("		 shohin.hachutani_irisu_qt shohin_hachutani_irisu_qt ");
//			sb.append("		 '' shohin_hachutani_irisu_qt ");
//			↑↑2006.09.06 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑(基本情報取得の復活)
		}
//		↑↑2006.07.30 H.Yamamoto カスタマイズ対応↑↑
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append("		 ts.sakusei_gyo_no sakusei_gyo_no ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append("		 ts.syohin_gyo_no syohin_gyo_no ");
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.10 H.Yamamoto カスタマイズ修正↓↓
		sb.append("		, ");
		sb.append(" 		ts.torikomi_dt torikomi_dt ");
		sb.append("		, ");
		sb.append(" 		ts.excel_file_syubetsu excel_file_syubetsu ");
		sb.append("		, ");
		sb.append(" 		ts.uketsuke_no uketsuke_no ");
		sb.append("		, ");
		sb.append(" 		'04' sheet_syubetsu ");
		sb.append("		, ");
		sb.append(" 		ts.uketsuke_seq uketsuke_seq ");
//		↑↑2006.10.10 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from  ");
		sb.append("		tr_syokaidonyu ts  ");
//		↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓(基本情報取得の復活)
//		↓↓2006.09.06 H.Yamamoto カスタマイズ修正↓↓
		sb.append("left outer join  ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		if ("gro".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_gro shohin ");
//		} else if ("fre".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_fre shohin ");
//		} else if ("a07".equals(bean.getExcelFileSyubetu())) {
//			sb.append("		 tr_syohin_a07 shohin ");
//		} else  {
//			sb.append("		 tr_syohin_a08 shohin ");
//		}
		if ("gro".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_gro shohin ");
		} else if ("fre".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_fre shohin ");
		} else if ("a07".equals(bean.getExcelFileSyubetu().toLowerCase())) {
			sb.append("		 tr_syohin_a07 shohin ");
		} else  {
			sb.append("		 tr_syohin_a08 shohin ");
		}
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
		sb.append("on  ");
		sb.append("		ts.uketsuke_no  = shohin.uketsuke_no ");
		sb.append("and  ");
		sb.append("		ts.bumon_cd  = shohin.bumon_cd ");
		sb.append("and  ");
		sb.append("		ts.syohin_gyo_no  = shohin.sakusei_gyo_no ");
//		↑↑2006.09.06 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑(基本情報取得の復活)
		sb.append("where  ");
		sb.append("		ts.torikomi_dt  ='" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append("and  ");
		sb.append("		ts.uketsuke_no  ='" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	order by");
		sb.append("		 ts.sakusei_gyo_no, ");
		sb.append("		 ts.uketsuke_seq ");
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

// ===BEGIN=== Comment out by kou 2006/8/16	
//	/**
//	 * 検索用ＳＱＬの生成を行う。
//	 * 渡されたMapを元にWHERE区を作成する。
//	 * @param map Map
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getSelectAllSql( Map map )
//	{
//		StringBuffer sb = new StringBuffer();
//		sb.append("select  ");
//		sb.append("		tts.torikomi_dt         torikomi_dt ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_syubetsu excel_file_syubetsu ");
//		sb.append("		, ");
//		sb.append("		tts.uketsuke_no         uketsuke_no ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_na       excel_file_na ");
//		sb.append("		, ");
//		sb.append("		rs.kanji_rn        siiresaki_na ");
//		sb.append("		, ");
//		sb.append("		tts.siiresaki_cd        siiresaki_cd ");
//		sb.append("		, ");
//		sb.append("		tts.upload_comment_tx   upload_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_ts    toroku_syonin_ts ");
//		sb.append("		, ");
//		sb.append("		tts.by_no               by_no ");
//		sb.append("		, ");
//		sb.append("		tts.syonin_comment_tx   syonin_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syohin_kb     excel_syohin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_tanpin_kb     excel_tanpin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_reigai_kb     excel_reigai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syokai_kb     excel_syokai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.min_neire_rt        min_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_neire_rt        max_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_uritanka_vl     max_uritanka_vl ");
//		sb.append("		, ");
//		sb.append("		tts.syori_jyotai_fg     syori_jyotai_fg ");
//		sb.append("		, ");
//		sb.append("		tts.kakunin_fg          kakunin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.delete_fg           delete_fg ");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		, ");
//		sb.append("		tts.insert_ts           insert_ts ");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("		, ");
//		sb.append("		tts.update_ts           update_ts ");
//		sb.append("		, ");
//		sb.append("		tts.update_user_id      update_user_id ");
//		sb.append("		, ");
//		sb.append("		tbl1.seqmax             seqmax ");
//		sb.append("		, ");
//		sb.append("		fre.syusei_kb           syusei_kb ");
//		sb.append("		, ");
//		sb.append("		'1'           tblname ");
//		sb.append("		, ");
//		sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
//		sb.append("		, ");
//		sb.append("		'disabled'           radioflag ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg_shoki ");
//		sb.append("from  ");
//		sb.append("		(  ");
//		sb.append("		select  ");
//		sb.append("				tts.torikomi_dt torikomi_dt  ");
//		sb.append("				, ");
//		sb.append("				tts.uketsuke_no uketsuke_no  ");
//		sb.append("				, ");
//		sb.append("				max(fre.uketsuke_seq) as seqmax  ");
//		sb.append("		from ");
//		sb.append("				tr_toroku_syonin tts ");
//		// ===BEGIN=== Modify by kou 2006/8/14
////		sb.append("				left outer join  ");
//		sb.append("				right outer join  ");
//		// ===END=== Modify by kou 2006/8/14
//		sb.append("				tr_syohin_fre fre  ");
//		sb.append("		on   ");
//		sb.append("				tts.torikomi_dt = fre.torikomi_dt  ");
//		sb.append("		and ");
//		sb.append("				tts.uketsuke_no = fre.uketsuke_no  ");
//		sb.append("		and ");
//		sb.append("				fre.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		where ");
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
//		sb.append("		and ");
//		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
//		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
//		}
//		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
//		}
//		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
//		}
//		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
//			sb.append("	and ");
//			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
//		}
//		if (map.get("tx_hinban_cd") != null && map.get("tx_hinban_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			fre.unit_cd in ");
//			sb.append("			( ");
//			sb.append("			select  ");
//			sb.append("				rst.unit_cd  unit_cd  ");
//			sb.append("			from  ");
//			sb.append("				r_syohin_taikei rst  ");
//			sb.append("			where  ");
//			sb.append("				rst.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//			sb.append("			and  ");
//			sb.append("				rst.hinban_cd = '" + "0" + map.get("tx_hinban_cd") + "'");
//			sb.append("			) ");
//		} else if (map.get("tx_unit_cd") != null && map.get("tx_unit_cd").toString().trim().length() > 0) {
//			sb.append("	and ");
//			sb.append("			fre.unit_cd = '" + map.get("tx_unit_cd") + "'");
//		}
//		sb.append("		group by ");
//		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
//		sb.append("		) tbl1  ");
//		sb.append(",  ");
//		sb.append("		tr_toroku_syonin tts  ");
//		sb.append("left outer join ");
//		sb.append("		tr_syohin_fre fre  ");
//		sb.append("on  ");
//		sb.append("		tts.torikomi_dt = fre.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = fre.uketsuke_no ");		
//		sb.append("and  ");
//		sb.append("		tbl1.seqmax = fre.uketsuke_seq ");		
//		sb.append("left outer join ");
//		sb.append(" ( select t1.riyo_user_id siiresaki_cd ");
//		sb.append("	,  ");
//		sb.append(" t2.kanji_rn   kanji_rn  ");
//		sb.append(" from   ");
//		sb.append(" r_riyo_user t1   ");
//		sb.append("	,  ");
//		sb.append(" r_siiresaki  t2   ");
//		sb.append(" where  ");
//		sb.append("		t2.siiresaki_cd = t1.riyo_user_syozoku_cd ");
//		sb.append("and  ");
//		sb.append("		t2.kanri_kb = '1' ");
//		sb.append("and  ");
////		↓↓2006.07.20 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("		t2.kanri_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		t2.kanri_cd = '0000'");
////		↑↑2006.07.20 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("	) rs  ");
//		sb.append("on  ");
//		sb.append("		tts.siiresaki_cd = rs.siiresaki_cd ");
//		sb.append(" where  ");
//		sb.append("		tts.torikomi_dt = tbl1.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = tbl1.uketsuke_no ");
//		sb.append("union  ");
//		sb.append("select  ");
//		sb.append("		tts.torikomi_dt         torikomi_dt ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_syubetsu excel_file_syubetsu ");
//		sb.append("		, ");
//		sb.append("		tts.uketsuke_no         uketsuke_no ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_na       excel_file_na ");
//		sb.append("		, ");
//		sb.append("		rs.kanji_rn        siiresaki_na ");
//		sb.append("		, ");
//		sb.append("		tts.siiresaki_cd        siiresaki_cd ");
//		sb.append("		, ");
//		sb.append("		tts.upload_comment_tx   upload_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_ts    toroku_syonin_ts ");
//		sb.append("		, ");
//		sb.append("		tts.by_no               by_no ");
//		sb.append("		, ");
//		sb.append("		tts.syonin_comment_tx   syonin_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syohin_kb     excel_syohin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_tanpin_kb     excel_tanpin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_reigai_kb     excel_reigai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syokai_kb     excel_syokai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.min_neire_rt        min_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_neire_rt        max_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_uritanka_vl     max_uritanka_vl ");
//		sb.append("		, ");
//		sb.append("		tts.syori_jyotai_fg     syori_jyotai_fg ");
//		sb.append("		, ");
//		sb.append("		tts.kakunin_fg          kakunin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.delete_fg           delete_fg ");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		, ");
//		sb.append("		tts.insert_ts           insert_ts ");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("		, ");
//		sb.append("		tts.update_ts           update_ts ");
//		sb.append("		, ");
//		sb.append("		tts.update_user_id      update_user_id ");
//		sb.append("		, ");
//		sb.append("		tbl1.seqmax             seqmax ");
//		sb.append("		, ");
//		sb.append("		gro.syusei_kb           syusei_kb ");
//		sb.append("		, ");
//		sb.append("		'2'           tblname ");
//		sb.append("		, ");
//		sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
//		sb.append("		, ");
//		sb.append("		'disabled'           radioflag ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg_shoki ");
//		sb.append("from  ");
//		sb.append("		(  ");
//		sb.append("		select  ");
//		sb.append("				tts.torikomi_dt torikomi_dt  ");
//		sb.append("				, ");
//		sb.append("				tts.uketsuke_no uketsuke_no  ");
//		sb.append("				, ");
//		sb.append("				max(gro.uketsuke_seq) as seqmax  ");
//		sb.append("		from ");
//		sb.append("				tr_toroku_syonin tts ");
//		// ===BEGIN=== Modify by kou 2006/8/14
////		sb.append("				left outer join  ");
//		sb.append("				right outer join  ");
//		// ===END=== Modify by kou 2006/8/14
//		sb.append("				tr_syohin_gro gro  ");
//		sb.append("		on   ");
//		sb.append("				tts.torikomi_dt = gro.torikomi_dt  ");
//		sb.append("		and ");
//		sb.append("				tts.uketsuke_no = gro.uketsuke_no  ");
//		sb.append("		and ");
//		sb.append("				gro.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		where ");
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
//		sb.append("		and ");
//		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
//		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
//		}
//		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
//		}
//		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
//		}
//		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
//			sb.append("	and ");
//			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
//		}
//		if (map.get("tx_hinban_cd") != null && map.get("tx_hinban_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			gro.unit_cd in ");
//			sb.append("			( ");
//			sb.append("			select  ");
//			sb.append("				rst.unit_cd  unit_cd  ");
//			sb.append("			from  ");
//			sb.append("				r_syohin_taikei rst  ");
//			sb.append("			where  ");
//			sb.append("				rst.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//			sb.append("			and  ");
//			sb.append("				rst.hinban_cd = '" + "0" + map.get("tx_hinban_cd") + "'");
//			sb.append("			) ");
//		} else if (map.get("tx_unit_cd") != null && map.get("tx_unit_cd").toString().trim().length() > 0) {
//			sb.append("	and ");
//			sb.append("			gro.unit_cd = '" + map.get("tx_unit_cd") + "'");
//		}
//		sb.append("		group by ");
//		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
//		sb.append("		) tbl1  ");
//		sb.append(", ");
//		sb.append("		tr_toroku_syonin tts  ");
//		sb.append("left outer join ");
//		sb.append("		tr_syohin_gro gro  ");
//		sb.append("on  ");
//		sb.append("		tts.torikomi_dt = gro.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = gro.uketsuke_no ");	
//		sb.append("and  ");
//		sb.append("		tbl1.seqmax = gro.uketsuke_seq ");		
//		sb.append("left outer join ");
//		sb.append(" ( select t1.riyo_user_id siiresaki_cd ");
//		sb.append("	,  ");
//		sb.append(" t2.kanji_rn   kanji_rn  ");
//		sb.append(" from   ");
//		sb.append(" r_riyo_user t1   ");
//		sb.append("	,  ");
//		sb.append(" r_siiresaki  t2   ");
//		sb.append(" where  ");
//		sb.append("		t2.siiresaki_cd = t1.riyo_user_syozoku_cd ");
//		sb.append("and  ");
//		sb.append("		t2.kanri_kb = '1' ");
//		sb.append("and  ");
////		↓↓2006.07.20 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("		t2.kanri_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		t2.kanri_cd = '0000'");
////		↑↑2006.07.20 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("	) rs  ");
//		sb.append("on  ");
//		sb.append("		tts.siiresaki_cd = rs.siiresaki_cd ");
//		sb.append(" where  ");
//		sb.append("		tts.torikomi_dt = tbl1.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = tbl1.uketsuke_no ");
//		sb.append("union  ");
//		sb.append("select  ");
//		sb.append("		tts.torikomi_dt         torikomi_dt ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_syubetsu excel_file_syubetsu ");
//		sb.append("		, ");
//		sb.append("		tts.uketsuke_no         uketsuke_no ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_na       excel_file_na ");
//		sb.append("		, ");
//		sb.append("		rs.kanji_rn        siiresaki_na ");
//		sb.append("		, ");
//		sb.append("		tts.siiresaki_cd        siiresaki_cd ");
//		sb.append("		, ");
//		sb.append("		tts.upload_comment_tx   upload_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_ts    toroku_syonin_ts ");
//		sb.append("		, ");
//		sb.append("		tts.by_no               by_no ");
//		sb.append("		, ");
//		sb.append("		tts.syonin_comment_tx   syonin_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syohin_kb     excel_syohin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_tanpin_kb     excel_tanpin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_reigai_kb     excel_reigai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syokai_kb     excel_syokai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.min_neire_rt        min_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_neire_rt        max_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_uritanka_vl     max_uritanka_vl ");
//		sb.append("		, ");
//		sb.append("		tts.syori_jyotai_fg     syori_jyotai_fg ");
//		sb.append("		, ");
//		sb.append("		tts.kakunin_fg          kakunin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.delete_fg           delete_fg ");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		, ");
//		sb.append("		tts.insert_ts           insert_ts ");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("		, ");
//		sb.append("		tts.update_ts           update_ts ");
//		sb.append("		, ");
//		sb.append("		tts.update_user_id      update_user_id ");
//		sb.append("		, ");
//		sb.append("		tbl1.seqmax             seqmax ");
//		sb.append("		, ");
//		sb.append("		a07.syusei_kb           syusei_kb ");
//		sb.append("		, ");
//		sb.append("		'3'           tblname ");
//		sb.append("		, ");
//		sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
//		sb.append("		, ");
//		sb.append("		'disabled'           radioflag ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg_shoki ");
//		sb.append("from  ");
//		sb.append("		(  ");
//		sb.append("		select  ");
//		sb.append("				tts.torikomi_dt torikomi_dt  ");
//		sb.append("				, ");
//		sb.append("				tts.uketsuke_no uketsuke_no  ");
//		sb.append("				, ");
//		sb.append("				max(a07.uketsuke_seq) as seqmax  ");
//		sb.append("		from ");
//		sb.append("				tr_toroku_syonin tts ");
//		// ===BEGIN=== Modify by kou 2006/8/14
////		sb.append("				left outer join  ");
//		sb.append("				right outer join  ");
//		// ===END=== Modify by kou 2006/8/14
//		sb.append("				tr_syohin_a07 a07  ");
//		sb.append("		on   ");
//		sb.append("				tts.torikomi_dt = a07.torikomi_dt  ");
//		sb.append("		and ");
//		sb.append("				tts.uketsuke_no = a07.uketsuke_no  ");
//		sb.append("		and ");
//		sb.append("				a07.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		where ");
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
//		sb.append("		and ");
//		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
//		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
//		}
//		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
//		}
//		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
//		}
//		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
//			sb.append("	and ");
//			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
//		}
//		if (map.get("tx_hinban_cd") != null && map.get("tx_hinban_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			a07.unit_cd in ");
//			sb.append("			( ");
//			sb.append("			select  ");
//			sb.append("				rst.unit_cd  unit_cd  ");
//			sb.append("			from  ");
//			sb.append("				r_syohin_taikei rst  ");
//			sb.append("			where  ");
//			sb.append("				rst.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//			sb.append("			and  ");
//			sb.append("				rst.hinban_cd = '" + "0" + map.get("tx_hinban_cd") + "'");
//			sb.append("			) ");
//		} else if (map.get("tx_unit_cd") != null && map.get("tx_unit_cd").toString().trim().length() > 0) {
//			sb.append("	and ");
//			sb.append("			a07.unit_cd = '" + map.get("tx_unit_cd") + "'");
//		}
//		sb.append("		group by ");
//		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
//		sb.append("		) tbl1  ");
//		sb.append(", ");
//		sb.append("		tr_toroku_syonin tts  ");
//		sb.append("left outer join ");
//		sb.append("		tr_syohin_a07 a07  ");
//		sb.append("on  ");
//		sb.append("		tts.torikomi_dt = a07.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = a07.uketsuke_no ");	
//		sb.append("and  ");
//		sb.append("		tbl1.seqmax = a07.uketsuke_seq ");		
//		sb.append("left outer join ");
//		sb.append(" ( select t1.riyo_user_id siiresaki_cd ");
//		sb.append("	,  ");
//		sb.append(" t2.kanji_rn   kanji_rn  ");
//		sb.append(" from   ");
//		sb.append(" r_riyo_user t1   ");
//		sb.append("	,  ");
//		sb.append(" r_siiresaki  t2   ");
//		sb.append(" where  ");
//		sb.append("		t2.siiresaki_cd = t1.riyo_user_syozoku_cd ");
//		sb.append("and  ");
//		sb.append("		t2.kanri_kb = '1' ");
//		sb.append("and  ");
////		↓↓2006.07.20 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("		t2.kanri_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		t2.kanri_cd = '0000'");
////		↑↑2006.07.20 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("	) rs  ");
//		sb.append("on  ");
//		sb.append("		tts.siiresaki_cd = rs.siiresaki_cd ");
//		sb.append(" where  ");
//		sb.append("		tts.torikomi_dt = tbl1.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = tbl1.uketsuke_no ");
//		sb.append("union  ");
//		sb.append("select  ");
//		sb.append("		tts.torikomi_dt         torikomi_dt ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_syubetsu excel_file_syubetsu ");
//		sb.append("		, ");
//		sb.append("		tts.uketsuke_no         uketsuke_no ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_na       excel_file_na ");
//		sb.append("		, ");
//		sb.append("		rs.kanji_rn        siiresaki_na ");
//		sb.append("		, ");
//		sb.append("		tts.siiresaki_cd        siiresaki_cd ");
//		sb.append("		, ");
//		sb.append("		tts.upload_comment_tx   upload_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_ts    toroku_syonin_ts ");
//		sb.append("		, ");
//		sb.append("		tts.by_no               by_no ");
//		sb.append("		, ");
//		sb.append("		tts.syonin_comment_tx   syonin_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syohin_kb     excel_syohin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_tanpin_kb     excel_tanpin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_reigai_kb     excel_reigai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syokai_kb     excel_syokai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.min_neire_rt        min_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_neire_rt        max_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_uritanka_vl     max_uritanka_vl ");
//		sb.append("		, ");
//		sb.append("		tts.syori_jyotai_fg     syori_jyotai_fg ");
//		sb.append("		, ");
//		sb.append("		tts.kakunin_fg          kakunin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.delete_fg           delete_fg ");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		, ");
//		sb.append("		tts.insert_ts           insert_ts ");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("		, ");
//		sb.append("		tts.update_ts           update_ts ");
//		sb.append("		, ");
//		sb.append("		tts.update_user_id      update_user_id ");
//		sb.append("		, ");
//		sb.append("		tbl1.seqmax             seqmax ");
//		sb.append("		, ");
//		sb.append("		a08.syusei_kb           syusei_kb ");
//		sb.append("		, ");
//		sb.append("		'4'           tblname ");
//		sb.append("		, ");
//		sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
//		sb.append("		, ");
//		sb.append("		'disabled'           radioflag ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg_shoki ");
//		sb.append("from  ");
//		sb.append("		(  ");
//		sb.append("		select  ");
//		sb.append("				tts.torikomi_dt torikomi_dt  ");
//		sb.append("				, ");
//		sb.append("				tts.uketsuke_no uketsuke_no  ");
//		sb.append("				, ");
//		sb.append("				max(a08.uketsuke_seq) as seqmax  ");
//		sb.append("		from ");
//		sb.append("				tr_toroku_syonin tts ");
//		// ===BEGIN=== Modify by kou 2006/8/14
////		sb.append("				left outer join  ");
//		sb.append("				right outer join  ");
//		// ===END=== Modify by kou 2006/8/14
//		sb.append("				tr_syohin_a08 a08  ");
//		sb.append("		on   ");
//		sb.append("				tts.torikomi_dt = a08.torikomi_dt  ");
//		sb.append("		and ");
//		sb.append("				tts.uketsuke_no = a08.uketsuke_no  ");
//		sb.append("		and ");
//		sb.append("				a08.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		where ");
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
//		sb.append("		and ");
//		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
//		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
//		}
//		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
//		}
//		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
//		}
//		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
//			sb.append("	and ");
//			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
//		}
//		if (map.get("tx_hinban_cd") != null && map.get("tx_hinban_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			a08.unit_cd in ");
//			sb.append("			( ");
//			sb.append("			select  ");
//			sb.append("				rst.unit_cd  unit_cd  ");
//			sb.append("			from  ");
//			sb.append("				r_syohin_taikei rst  ");
//			sb.append("			where  ");
//			sb.append("				rst.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//			sb.append("			and  ");
//			sb.append("				rst.hinban_cd = '" + "0" + map.get("tx_hinban_cd") + "'");
//			sb.append("			) ");
//		} else if (map.get("tx_unit_cd") != null && map.get("tx_unit_cd").toString().trim().length() > 0) {
//			sb.append("	and ");
//			sb.append("			a08.unit_cd = '" + map.get("tx_unit_cd") + "'");
//		}
//		sb.append("		group by ");
//		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
//		sb.append("		) tbl1  ");
//		sb.append(", ");
//		sb.append("		tr_toroku_syonin tts  ");
//		sb.append("left outer join ");
//		sb.append("		tr_syohin_a08 a08  ");
//		sb.append("on  ");
//		sb.append("		tts.torikomi_dt = a08.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = a08.uketsuke_no ");	
//		sb.append("and  ");
//		sb.append("		tbl1.seqmax = a08.uketsuke_seq ");		
//		sb.append("left outer join ");
//		sb.append(" ( select t1.riyo_user_id siiresaki_cd ");
//		sb.append("	,  ");
//		sb.append(" t2.kanji_rn   kanji_rn  ");
//		sb.append(" from   ");
//		sb.append(" r_riyo_user t1   ");
//		sb.append("	,  ");
//		sb.append(" r_siiresaki  t2   ");
//		sb.append(" where  ");
//		sb.append("		t2.siiresaki_cd = t1.riyo_user_syozoku_cd ");
//		sb.append("and  ");
//		sb.append("		t2.kanri_kb = '1' ");
//		sb.append("and  ");
////		↓↓2006.07.20 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("		t2.kanri_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		t2.kanri_cd = '0000'");
////		↑↑2006.07.20 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("	) rs  ");
//		sb.append("on  ");
//		sb.append("		tts.siiresaki_cd = rs.siiresaki_cd ");
//		sb.append(" where  ");
//		sb.append("		tts.torikomi_dt = tbl1.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = tbl1.uketsuke_no ");
//		sb.append("union  ");
//		sb.append("select  ");
//		sb.append("		tts.torikomi_dt         torikomi_dt ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_syubetsu excel_file_syubetsu ");
//		sb.append("		, ");
//		sb.append("		tts.uketsuke_no         uketsuke_no ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_na       excel_file_na ");
//		sb.append("		, ");
//		sb.append("		rs.kanji_rn        siiresaki_na ");
//		sb.append("		, ");
//		sb.append("		tts.siiresaki_cd        siiresaki_cd ");
//		sb.append("		, ");
//		sb.append("		tts.upload_comment_tx   upload_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_ts    toroku_syonin_ts ");
//		sb.append("		, ");
//		sb.append("		tts.by_no               by_no ");
//		sb.append("		, ");
//		sb.append("		tts.syonin_comment_tx   syonin_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syohin_kb     excel_syohin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_tanpin_kb     excel_tanpin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_reigai_kb     excel_reigai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syokai_kb     excel_syokai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.min_neire_rt        min_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_neire_rt        max_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_uritanka_vl     max_uritanka_vl ");
//		sb.append("		, ");
//		sb.append("		tts.syori_jyotai_fg     syori_jyotai_fg ");
//		sb.append("		, ");
//		sb.append("		tts.kakunin_fg          kakunin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.delete_fg           delete_fg ");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		, ");
//		sb.append("		tts.insert_ts           insert_ts ");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("		, ");
//		sb.append("		tts.update_ts           update_ts ");
//		sb.append("		, ");
//		sb.append("		tts.update_user_id      update_user_id ");
//		sb.append("		, ");
//		sb.append("		tbl1.seqmax             seqmax ");
//		sb.append("		, ");
//		sb.append("		tbl1.syusei_kb           syusei_kb ");
//		sb.append("		, ");
//		sb.append("		'5'           tblname ");
//		sb.append("		, ");
//		sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
//		sb.append("		, ");
//		sb.append("		'disabled'           radioflag ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg_shoki ");
//		sb.append("from  ");
//		sb.append("		(  ");
//		sb.append("		select  ");
//		sb.append("				tts.torikomi_dt torikomi_dt  ");
//		sb.append("				, ");
//		sb.append("				tts.uketsuke_no uketsuke_no  ");
//		sb.append("				, ");
//		sb.append("				max('0') seqmax  ");
//		sb.append("				, ");
//		sb.append("				max(ts.syusei_kb) as syusei_kb  ");
//		sb.append("		from ");
//		sb.append("				tr_toroku_syonin tts ");
//		// ===BEGIN=== Modify by kou 2006/8/14
////		sb.append("				left outer join  ");
//		sb.append("				right outer join  ");
//		// ===END=== Modify by kou 2006/8/14
//		sb.append("				tr_syokaidonyu ts  ");
//		sb.append("		on   ");
//		sb.append("				tts.torikomi_dt = ts.torikomi_dt  ");
//		sb.append("		and ");
//		sb.append("				tts.uketsuke_no = ts.uketsuke_no  ");
//		sb.append("		and ");
//		sb.append("				ts.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		where ");
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
//		sb.append("		and ");
//		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
//		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
//		}
//		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
//		}
//		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
//		}
//		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
//			sb.append("	and ");
//			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
//		}
//		sb.append("		group by ");
//		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
//		sb.append("		) tbl1  ");
//		sb.append(",  ");
//		sb.append("		tr_toroku_syonin tts  ");
//		sb.append("left outer join ");
//		sb.append(" ( select t1.riyo_user_id siiresaki_cd ");
//		sb.append("	,  ");
//		sb.append(" t2.kanji_rn   kanji_rn  ");
//		sb.append(" from   ");
//		sb.append(" r_riyo_user t1   ");
//		sb.append("	,  ");
//		sb.append(" r_siiresaki  t2   ");
//		sb.append(" where  ");
//		sb.append("		t2.siiresaki_cd = t1.riyo_user_syozoku_cd ");
//		sb.append("and  ");
//		sb.append("		t2.kanri_kb = '1' ");
//		sb.append("and  ");
////		↓↓2006.07.20 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("		t2.kanri_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		t2.kanri_cd = '0000'");
////		↑↑2006.07.20 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("	) rs  ");
//		sb.append("on  ");
//		sb.append("		tts.siiresaki_cd = rs.siiresaki_cd ");
//		sb.append(" where  ");
//		sb.append("		tts.torikomi_dt = tbl1.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = tbl1.uketsuke_no ");
//		sb.append("union  ");
//		sb.append("select  ");
//		sb.append("		tts.torikomi_dt         torikomi_dt ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_syubetsu excel_file_syubetsu ");
//		sb.append("		, ");
//		sb.append("		tts.uketsuke_no         uketsuke_no ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_na       excel_file_na ");
//		sb.append("		, ");
//		sb.append("		rs.kanji_rn        siiresaki_na ");
//		sb.append("		, ");
//		sb.append("		tts.siiresaki_cd        siiresaki_cd ");
//		sb.append("		, ");
//		sb.append("		tts.upload_comment_tx   upload_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_ts    toroku_syonin_ts ");
//		sb.append("		, ");
//		sb.append("		tts.by_no               by_no ");
//		sb.append("		, ");
//		sb.append("		tts.syonin_comment_tx   syonin_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syohin_kb     excel_syohin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_tanpin_kb     excel_tanpin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_reigai_kb     excel_reigai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syokai_kb     excel_syokai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.min_neire_rt        min_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_neire_rt        max_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_uritanka_vl     max_uritanka_vl ");
//		sb.append("		, ");
//		sb.append("		tts.syori_jyotai_fg     syori_jyotai_fg ");
//		sb.append("		, ");
//		sb.append("		tts.kakunin_fg          kakunin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.delete_fg           delete_fg ");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		, ");
//		sb.append("		tts.insert_ts           insert_ts ");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("		, ");
//		sb.append("		tts.update_ts           update_ts ");
//		sb.append("		, ");
//		sb.append("		tts.update_user_id      update_user_id ");
//		sb.append("		, ");
//		sb.append("		tbl1.seqmax             seqmax ");
//		sb.append("		, ");
//		sb.append("		tbl1.syusei_kb           syusei_kb ");
//		sb.append("		, ");
//		sb.append("		'6'           tblname ");
//		sb.append("		, ");
//		sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
//		sb.append("		, ");
//		sb.append("		'disabled'           radioflag ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg_shoki ");
//		sb.append("from  ");
//		sb.append("		(  ");
//		sb.append("		select  ");
//		sb.append("				tts.torikomi_dt torikomi_dt  ");
//		sb.append("				, ");
//		sb.append("				tts.uketsuke_no uketsuke_no  ");
//		sb.append("				, ");
//		sb.append("				max('0') seqmax  ");
//		sb.append("				, ");
//		sb.append("				max(ttr.syusei_kb) as syusei_kb  ");
//		sb.append("		from ");
//		sb.append("				tr_toroku_syonin tts ");
//		// ===BEGIN=== Modify by kou 2006/8/14
////		sb.append("				left outer join  ");
//		sb.append("				right outer join  ");
//		// ===END=== Modify by kou 2006/8/14
//		sb.append("				tr_tensyohin_reigai ttr  ");
//		sb.append("		on   ");
//		sb.append("				tts.torikomi_dt = ttr.torikomi_dt  ");
//		sb.append("		and ");
//		sb.append("				tts.uketsuke_no = ttr.uketsuke_no  ");
//		sb.append("		and ");
//		sb.append("				ttr.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		where ");
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
//		sb.append("		and ");
//		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
//		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
//		}
//		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
//		}
//		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
//		}
//		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
//			sb.append("	and ");
//			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
//		}
//		sb.append("		group by ");
//		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
//		sb.append("		) tbl1  ");
//		sb.append(",  ");
//		sb.append("		tr_toroku_syonin tts  ");
//		sb.append("left outer join ");
//		sb.append(" ( select t1.riyo_user_id siiresaki_cd ");
//		sb.append("	,  ");
//		sb.append(" t2.kanji_rn   kanji_rn  ");
//		sb.append(" from   ");
//		sb.append(" r_riyo_user t1   ");
//		sb.append("	,  ");
//		sb.append(" r_siiresaki  t2   ");
//		sb.append(" where  ");
//		sb.append("		t2.siiresaki_cd = t1.riyo_user_syozoku_cd ");
//		sb.append("and  ");
//		sb.append("		t2.kanri_kb = '1' ");
//		sb.append("and  ");
////		↓↓2006.07.20 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("		t2.kanri_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		t2.kanri_cd = '0000'");
////		↑↑2006.07.20 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("	) rs  ");
//		sb.append("on  ");
//		sb.append("		tts.siiresaki_cd = rs.siiresaki_cd ");
//		sb.append(" where  ");
//		sb.append("		tts.torikomi_dt = tbl1.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = tbl1.uketsuke_no ");
//		sb.append("union  ");
//		sb.append("select  ");
//		sb.append("		tts.torikomi_dt         torikomi_dt ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_syubetsu excel_file_syubetsu ");
//		sb.append("		, ");
//		sb.append("		tts.uketsuke_no         uketsuke_no ");
//		sb.append("		, ");
//		sb.append("		tts.excel_file_na       excel_file_na ");
//		sb.append("		, ");
//		sb.append("		rs.kanji_rn        siiresaki_na ");
//		sb.append("		, ");
//		sb.append("		tts.siiresaki_cd        siiresaki_cd ");
//		sb.append("		, ");
//		sb.append("		tts.upload_comment_tx   upload_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_ts    toroku_syonin_ts ");
//		sb.append("		, ");
//		sb.append("		tts.by_no               by_no ");
//		sb.append("		, ");
//		sb.append("		tts.syonin_comment_tx   syonin_comment_tx ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syohin_kb     excel_syohin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_tanpin_kb     excel_tanpin_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_reigai_kb     excel_reigai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.excel_syokai_kb     excel_syokai_kb ");
//		sb.append("		, ");
//		sb.append("		tts.min_neire_rt        min_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_neire_rt        max_neire_rt ");
//		sb.append("		, ");
//		sb.append("		tts.max_uritanka_vl     max_uritanka_vl ");
//		sb.append("		, ");
//		sb.append("		tts.syori_jyotai_fg     syori_jyotai_fg ");
//		sb.append("		, ");
//		sb.append("		tts.kakunin_fg          kakunin_fg ");
//		sb.append("		, ");
//		sb.append("		tts.delete_fg           delete_fg ");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		, ");
//		sb.append("		tts.insert_ts           insert_ts ");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("		, ");
//		sb.append("		tts.update_ts           update_ts ");
//		sb.append("		, ");
//		sb.append("		tts.update_user_id      update_user_id ");
//		sb.append("		, ");
//		sb.append("		tbl1.seqmax             seqmax ");
//		sb.append("		, ");
//		sb.append("		tbl1.syusei_kb           syusei_kb ");
//		sb.append("		, ");
//		sb.append("		'7'           tblname ");
//		sb.append("		, ");
//		sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
//		sb.append("		, ");
//		sb.append("		'disabled'           radioflag ");
//		sb.append("		, ");
//		sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg_shoki ");
//		sb.append("from  ");
//		sb.append("		(  ");
//		sb.append("		select  ");
//		sb.append("				tts.torikomi_dt torikomi_dt  ");
//		sb.append("				, ");
//		sb.append("				tts.uketsuke_no uketsuke_no  ");
//		sb.append("				, ");
//		sb.append("				max('0') seqmax  ");
//		sb.append("				, ");
//		sb.append("				max(ttt.syusei_kb) as syusei_kb  ");
//		sb.append("		from ");
//		sb.append("				tr_toroku_syonin tts ");
//		// ===BEGIN=== Modify by kou 2006/8/14
////		sb.append("				left outer join  ");
//		sb.append("				right outer join  ");
//		// ===END=== Modify by kou 2006/8/14
//		sb.append("				tr_tanpinten_toriatukai ttt  ");
//		sb.append("		on   ");
//		sb.append("				tts.torikomi_dt = ttt.torikomi_dt  ");
//		sb.append("		and ");
//		sb.append("				tts.uketsuke_no = ttt.uketsuke_no  ");
//		sb.append("		and ");
//		sb.append("				ttt.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		where ");
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
//		sb.append("		and ");
//		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
//		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
//		}
//		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
//		}
//		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
//			sb.append("	and ");
//			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
//		}
//		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
//			sb.append("	and ");
//			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
//		}
//		sb.append("		group by ");
//		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
//		sb.append("		) tbl1  ");
//		sb.append(",  ");
//		sb.append("		tr_toroku_syonin tts  ");
//		sb.append("left outer join ");
//		sb.append(" ( select t1.riyo_user_id siiresaki_cd ");
//		sb.append("	,  ");
//		sb.append(" t2.kanji_rn   kanji_rn  ");
//		sb.append(" from   ");
//		sb.append(" r_riyo_user t1   ");
//		sb.append("	,  ");
//		sb.append(" r_siiresaki  t2   ");
//		sb.append(" where  ");
//		sb.append("		t2.siiresaki_cd = t1.riyo_user_syozoku_cd ");
//		sb.append("and  ");
//		sb.append("		t2.kanri_kb = '1' ");
//		sb.append("and  ");
////		↓↓2006.07.20 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("		t2.kanri_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
//		sb.append("		t2.kanri_cd = '0000'");
////		↑↑2006.07.20 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("	) rs  ");
//		sb.append("on  ");
//		sb.append("		tts.siiresaki_cd = rs.siiresaki_cd ");
//		sb.append(" where  ");
//		sb.append("		tts.torikomi_dt = tbl1.torikomi_dt ");
//		sb.append("and  ");
//		sb.append("		tts.uketsuke_no = tbl1.uketsuke_no ");
//		sb.append(" order by ");
//		sb.append("torikomi_dt desc,uketsuke_no desc,tblname");
//		return sb.toString();
//	}
// ===END=== Comment out by kou 2006/8/16

// ===BEGIN=== Add by kou 2006/8/16
   /**
	* 検索用ＳＱＬの生成を行う。
	* 渡されたMapを元にWHERE区を作成する。
	* @param map Map
	* @return String 生成されたＳＱＬ
	*/
   public String getSelectAllSql( Map map )
   {
	   StringBuffer sb = new StringBuffer();
	   sb.append("select  ");
	   sb.append("		tts.torikomi_dt         torikomi_dt ");
	   sb.append("		, ");
	   sb.append("		tts.excel_file_syubetsu excel_file_syubetsu ");
	   sb.append("		, ");
	   sb.append("		tts.uketsuke_no         uketsuke_no ");
	   sb.append("		, ");
	   sb.append("		tts.excel_file_na       excel_file_na ");
	   sb.append("		, ");
	   sb.append("		rs.kanji_rn        siiresaki_na ");
	   sb.append("		, ");
	   sb.append("		tts.siiresaki_cd        siiresaki_cd ");
	   sb.append("		, ");
	   sb.append("		tts.upload_comment_tx   upload_comment_tx ");
	   sb.append("		, ");
	   sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg ");
	   sb.append("		, ");
	   sb.append("		tts.toroku_syonin_ts    toroku_syonin_ts ");
	   sb.append("		, ");
	   sb.append("		tts.by_no               by_no ");
	   sb.append("		, ");
	   sb.append("		tts.syonin_comment_tx   syonin_comment_tx ");
	   sb.append("		, ");
	   sb.append("		tts.excel_syohin_kb     excel_syohin_kb ");
	   sb.append("		, ");
	   sb.append("		tts.excel_tanpin_kb     excel_tanpin_kb ");
	   sb.append("		, ");
	   sb.append("		tts.excel_reigai_kb     excel_reigai_kb ");
	   sb.append("		, ");
	   sb.append("		tts.excel_syokai_kb     excel_syokai_kb ");
//	↓↓2006.08.20 H.Yamamoto カスタマイズ修正↓↓
//	   sb.append("		, ");
//	   sb.append("		tts.min_neire_rt        min_neire_rt");
//	   sb.append("		, ");
//	   sb.append("		tts.max_neire_rt        max_neire_rt ");
	   sb.append("		, ");
	   sb.append("		tts.min_neire_rt * 100        min_neire_rt");
	   sb.append("		, ");
	   sb.append("		tts.max_neire_rt * 100        max_neire_rt ");
//	↑↑2006.08.20 H.Yamamoto カスタマイズ修正↑↑
	   sb.append("		, ");
	   sb.append("		tts.max_uritanka_vl     max_uritanka_vl ");
	   sb.append("		, ");
	   sb.append("		tts.syori_jyotai_fg     syori_jyotai_fg ");
	   sb.append("		, ");
	   sb.append("		tts.kakunin_fg          kakunin_fg ");
	   sb.append("		, ");
	   sb.append("		tts.delete_fg           delete_fg ");
	   sb.append("		, ");
	   sb.append("		tts.insert_ts           insert_ts ");
	   sb.append("		, ");
	   sb.append("		tts.update_ts           update_ts ");
	   sb.append("		, ");
	   sb.append("		tts.update_user_id      update_user_id ");
	   sb.append("		, ");
	   sb.append("		tbl1.seqmax             seqmax ");
	   sb.append("		, ");
	   sb.append("		tbl1.syusei_kb          syusei_kb ");
	   sb.append("		, ");
	   sb.append("		tbl1.tanpin_max             tanpin_max ");
	   sb.append("		, ");
	   sb.append("		tbl1.tanpin_syusei_kb       tanpin_syusei_kb ");
	   sb.append("		, ");
	   sb.append("		tbl1.reigai_max             reigai_max ");
	   sb.append("		, ");
	   sb.append("		tbl1.reigai_syusei_kb       reigai_syusei_kb ");
	   sb.append("		, ");
	   sb.append("		tbl1.syokai_max             syokai_max ");
	   sb.append("		, ");
	   sb.append("		tbl1.syokai_syusei_kb       syokai_syusei_kb ");
	   sb.append("		, ");
	   sb.append("		'1'           tblname ");
	   sb.append("		, ");
	   sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
	   sb.append("		, ");
	   sb.append("		'disabled'           radioflag ");
	   sb.append("		, ");
	   sb.append("		tts.toroku_syonin_fg    toroku_syonin_fg_shoki ");
//	   ↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
	   sb.append("		, ");
	   sb.append("		tts.syori_jyotai_fg    syori_jyotai_fg ");
//	   ↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
	   sb.append("from  ");
	   sb.append("		(  ");			//TBL BEGIN
	   sb.append(getTblSql(map));
	   sb.append("		) tbl1  ");			// TBL END
	   sb.append(",  ");
	   sb.append("		tr_toroku_syonin tts  ");
	   sb.append("left outer join ");
//	   sb.append(" ( select t1.riyo_user_id siiresaki_cd ");
	   sb.append(" ( select t1.user_id siiresaki_cd ");
	   sb.append("	,  ");
	   sb.append(" t2.kanji_rn   kanji_rn  ");
	   sb.append(" from   ");
//	   sb.append(" r_riyo_user t1   ");
	   sb.append(" r_portal_user t1   ");
	   sb.append("	,  ");
	   sb.append(" r_siiresaki  t2   ");
	   sb.append(" where  ");
//	   sb.append("		t2.siiresaki_cd = t1.riyo_user_syozoku_cd ");
	   sb.append("		TRIM(t2.siiresaki_cd) = t1.torihikisaki_cd ");
	   sb.append("and  ");
	   sb.append("		t2.kanri_kb = '1' ");
	   sb.append("and  ");
	   sb.append("		t2.kanri_cd = '0000'");
	   sb.append("	) rs  ");
	   sb.append("on  ");
	   sb.append("		tts.siiresaki_cd = rs.siiresaki_cd ");
	   sb.append(" where  ");
	   sb.append("		tts.torikomi_dt = tbl1.torikomi_dt ");
	   sb.append("and  ");
	   sb.append("		tts.uketsuke_no = tbl1.uketsuke_no ");
	   	   
	   sb.append(" order by ");
	   //sb.append("torikomi_dt desc,uketsuke_no desc,tblname");
	   sb.append("torikomi_dt desc,uketsuke_no desc");
	   
//	↓↓2006.10.28 H.Yamamoto カスタマイズ修正↓↓
//	※絶対にコメント化しない事！！！！！
//	※主処理で更新後コミットしていない為、本番環境では再表示用の検索でロックしてしまう。
//	※コメント化する場合、主処理で更新後コミットを行うように仕様変更を行う事が必要。
	   // ===BEGIN=== Add by kou 2006/8/24
	   sb.append(" WITH UR");
	   // ===END=== Add by kou 2006/8/24
//	↑↑2006.10.28 H.Yamamoto カスタマイズ修正↑↑
	   
	   return sb.toString();
   }
// ===END=== Add by kou 2006/8/16

	// ===BEGIN=== Add by kou 2006/8/17
	/**
	 * @param map
	 * @return
	 */
	private String getTblSql(Map map) {
		StringBuffer sb = new StringBuffer();
//		↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
		String systemKb = (String) map.get("SystemKB");
//		↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.11.22 H.Yamamoto カスタマイズ修正↓↓
		StringBuffer sbw = new StringBuffer();
		String systemKbS = (String) map.get("SystemKbS");
//		↑↑2006.11.22 H.Yamamoto カスタマイズ修正↑↑
		
		sb.append("	SELECT ");
		sb.append("		TORIKOMI_DT,");
		sb.append("		UKETSUKE_NO,");
		sb.append("		max(SEQMAX)       SEQMAX,");
		sb.append("		max(SYUSEI_KB)    SYUSEI_KB,");
		sb.append("		max(TANPIN_SEQMAX)      TANPIN_MAX,");
		sb.append("		max(TANPIN_SYUSEI_KB)   TANPIN_SYUSEI_KB,");
		sb.append("		max(REIGAI_SEQMAX)      REIGAI_MAX,");
		sb.append("		max(REIGAI_SYUSEI_KB)   REIGAI_SYUSEI_KB,");
		sb.append("		max(SYOKAI_SEQMAX)      SYOKAI_MAX,");
		sb.append("		max(SYOKAI_SYUSEI_KB)   SYOKAI_SYUSEI_KB");
		sb.append("	FROM (");	
		// ===BEGIN=== Modify by kou 2006/10/24
		//sb.append("			(").append(getSyohinSql(map,"A08")).append("	) ");
		//sb.append("			UNION ");	   
		//sb.append("			(").append(getSyohinSql(map,"A07")).append("	) ");
		//sb.append("			UNION ");	   
		//sb.append("			(").append(getSyohinSql(map,"GRO")).append("	) ");
		//sb.append("			UNION ");	   
		//sb.append("			(").append(getSyohinSql(map,"FRE")).append("	) ");
//		↓↓2006.11.22 H.Yamamoto カスタマイズ修正↓↓
////		↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("			(").append(getSyohinSql(map)).append("	) ");
//		if(systemKb != null && systemKb.trim().length() > 0){
//			sb.append("			(").append(getSyohinSql(map,getGyosyu(systemKb))).append("	) ");
//		} else {
//			sb.append("			(").append(getSyohinSql(map,"A08")).append("	) ");
//			sb.append("			UNION ");	   
//			sb.append("			(").append(getSyohinSql(map,"A07")).append("	) ");
//			sb.append("			UNION ");	   
//			sb.append("			(").append(getSyohinSql(map,"GRO")).append("	) ");
//			sb.append("			UNION ");	   
//			sb.append("			(").append(getSyohinSql(map,"FRE")).append("	) ");
//		}
////		↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
		if(systemKbS.equals("") || systemKbS.indexOf("T") > -1){
			if (sbw.length() > 0) {
				sbw.append("			UNION ");	   
			}
			sbw.append("			(").append(getSyohinSql(map,"A08")).append("	) ");
		}
		if(systemKbS.equals("") || systemKbS.indexOf("J") > -1){
			if (sbw.length() > 0) {
				sbw.append("			UNION ");	   
			}
			sbw.append("			(").append(getSyohinSql(map,"A07")).append("	) ");
		}
		if(systemKbS.equals("") || systemKbS.indexOf("G") > -1){
			if (sbw.length() > 0) {
				sbw.append("			UNION ");	   
			}
			sbw.append("			(").append(getSyohinSql(map,"GRO")).append("	) ");
		}
		if(systemKbS.equals("") || systemKbS.indexOf("F") > -1){
			if (sbw.length() > 0) {
				sbw.append("			UNION ");	   
			}
			sbw.append("			(").append(getSyohinSql(map,"FRE")).append("	) ");
		}
		sb.append(sbw.toString());	   
//		↑↑2006.11.22 H.Yamamoto カスタマイズ修正↑↑
		// ===END=== Modify by kou 2006/10/24
		sb.append("			UNION ");	   
		sb.append("			(  ").append(getTanpinSql(map)).append("		) ");
		sb.append("			UNION ");	   
		sb.append("			(  ").append(getReigaiSql(map)).append("		) ");
		sb.append("			UNION ");	   
		sb.append("			(  ").append(getSyokaiSql(map)).append("		) ");
		sb.append("		) TMP ");
		sb.append("	GROUP BY TORIKOMI_DT, UKETSUKE_NO ");
		sb.append("	ORDER BY TORIKOMI_DT, UKETSUKE_NO ");
		   
		return sb.toString();
	}
	//===END=== Add by kou 2006/8/17
	
	//===BEGIN=== Add by kou 2006/8/17
	/**
	 * @param map
	 * @return
	 */
//	↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
//	// ===BEGIN=== Modify by kou 2006/10/24
//	//private String getSyohinSql(Map map, String gyosyu) {
//	private String getSyohinSql(Map map) {
//		String gyosyu = getGyosyu((String) map.get("SystemKB"));
//	// ===END== Modify by kou 2006/10/24
	private String getSyohinSql(Map map, String gyosyu) {
//	↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
	
		StringBuffer sb = new StringBuffer();
		
		sb.append("		select  ");
		sb.append("				tts.torikomi_dt torikomi_dt  ");
		sb.append("				, ");
		sb.append("				tts.uketsuke_no uketsuke_no  ");
		sb.append("				, ");
		sb.append("				max(").append(gyosyu).append(".uketsuke_seq) as seqmax  ");
	    sb.append("				, ");
	    sb.append("				max(").append(gyosyu).append(".syusei_kb) as syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as tanpin_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as tanpin_syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as reigai_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as reigai_syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as syokai_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as syokai_syusei_kb  ");
		sb.append("		from ");
		sb.append("				tr_toroku_syonin tts ");
		// ===BEGIN=== Modify by kou 2006/10/24
		//sb.append("				right outer join  ");
		//sb.append("				tr_syohin_").append(gyosyu).append(" ").append(gyosyu).append("  ");
		//sb.append("		on   ");
		//sb.append("				tts.torikomi_dt = ").append(gyosyu).append(".torikomi_dt  ");
		//sb.append("		and ");
		//sb.append("				tts.uketsuke_no = ").append(gyosyu).append(".uketsuke_no  ");
		//sb.append("		and ");
		//sb.append("				").append(gyosyu).append(".bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
		sb.append("				, tr_syohin_").append(gyosyu).append(" ").append(gyosyu).append("  ");		
		// ===END=== Modify by kou 2006/10/24
		sb.append("		where ");
//		↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
		sb.append("				(tts.syori_jyotai_fg = '0' or tts.toroku_syonin_fg = '1')  ");
//		↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
		sb.append("		and ");
		sb.append("				tts.delete_fg = '0'  ");
		sb.append("		and ");
		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
			sb.append("	and ");
			// ===BEGIN=== Modify by kou 2006/8/18
			//sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
			sb.append("			tts.siiresaki_cd LIKE '" + map.get("tx_shiire_cd") + "%'");
			// ===END=== Modify by kou 2006/8/18
		}
		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
		}
		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
		}
		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
			sb.append("	and ");
			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
		}
		if (map.get("tx_hinban_cd") != null && map.get("tx_hinban_cd").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			").append(gyosyu).append(".unit_cd in ");
			sb.append("			( ");
			sb.append("			select  ");
			sb.append("				rst.unit_cd  unit_cd  ");
			sb.append("			from  ");
			sb.append("				r_syohin_taikei rst  ");
			sb.append("			where  ");
			sb.append("				rst.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
			sb.append("			and  ");
			sb.append("				rst.hinban_cd = '" + "0" + map.get("tx_hinban_cd") + "'");
			sb.append("			) ");
		} else if (map.get("tx_unit_cd") != null && map.get("tx_unit_cd").toString().trim().length() > 0) {
			sb.append("	and ");
			sb.append("			").append(gyosyu).append(".unit_cd = '" + map.get("tx_unit_cd") + "'");
		}
		// ===BEGIN=== Add by kou 2006/10/24
		sb.append("		and   ");
		sb.append("				tts.torikomi_dt = ").append(gyosyu).append(".torikomi_dt  ");
		sb.append("		and ");
		sb.append("				tts.uketsuke_no = ").append(gyosyu).append(".uketsuke_no  ");
		sb.append("		and ");
		sb.append("				").append(gyosyu).append(".bumon_cd = '0").append(map.get("tx_bumon_cd")).append("'");
		// ===END=== Add by kou 2006/10/24
		sb.append("		group by ");
		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
		
		return sb.toString();
	}
	// ===END=== Add by kou 2006/8/17
	
	/**
	 * @param string
	 * @return
	 */
	private String getGyosyu(String systemKB)
	{
		String gyosyu = null;
		
		if("T".equals(systemKB)) {
			gyosyu = "A08";
		} else if("J".equals(systemKB)) {
			gyosyu = "A07";
		} else if("G".equals(systemKB)) {
			gyosyu = "GRO";
		} else if("F".equals(systemKB)) {
			gyosyu = "FRE";
		} else {
			throw new RuntimeException("undefined SystemKB.");
		}
		
		return gyosyu;
	}
	// ===BEGIN=== Add by kou 2006/8/17
	/**
	 * @param map
	 * @return
	 */
	private String getSyokaiSql(Map map) {
		StringBuffer sb = new StringBuffer();
//		↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
		String systemKb = (String) map.get("SystemKB");
//		↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
		
		sb.append("		select  ");
		sb.append("				tts.torikomi_dt torikomi_dt  ");
		sb.append("				, ");
		sb.append("				tts.uketsuke_no uketsuke_no  ");
		sb.append("				, ");
		sb.append("				'0' as seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as tanpin_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as tanpin_syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as reigai_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as reigai_syusei_kb  ");
		sb.append("				, ");
		sb.append("				max(ts.uketsuke_seq) as syokai_seqmax  ");
		sb.append("				, ");
		sb.append("				max(ts.syusei_kb) as syokai_syusei_kb  ");
		sb.append("		from ");
		sb.append("				tr_toroku_syonin tts ");
		// ===BEGIN=== Modify by kou 2006/10/24
		//sb.append("				right outer join  ");
		//sb.append("				tr_syokaidonyu ts  ");
		//sb.append("		on   ");
		//sb.append("				tts.torikomi_dt = ts.torikomi_dt  ");
		//sb.append("		and ");
		//sb.append("				tts.uketsuke_no = ts.uketsuke_no  ");
		//sb.append("		and ");
		//sb.append("				ts.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
		sb.append("				, tr_syokaidonyu ts  ");
		// ===END=== Modify by kou 2006/10/24
		sb.append("		where ");
//		↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
		sb.append("				(tts.syori_jyotai_fg = '0' or tts.toroku_syonin_fg = '1')  ");
//		↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
		sb.append("		and ");
		sb.append("				tts.delete_fg = '0'  ");
		sb.append("		and ");
		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
			sb.append("	and ");
			// ===BEGIN=== Modify by kou 2006/8/18
			//sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
			sb.append("			tts.siiresaki_cd LIKE '" + map.get("tx_shiire_cd") + "%'");
			// ===END=== Modify by kou 2006/8/18
		}
		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
		}
		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
		}
		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
			sb.append("	and ");
			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
		}
		// ===BEGIN=== Add by kou 2006/10/24
		sb.append("		and  ");
		sb.append("				tts.torikomi_dt = ts.torikomi_dt  ");
		sb.append("		and ");
		sb.append("				tts.uketsuke_no = ts.uketsuke_no  ");
		sb.append("		and ");
		sb.append("				ts.bumon_cd = '0").append(map.get("tx_bumon_cd")).append("'");
//		↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		and ");
//		sb.append("				ts.EXCEL_FILE_SYUBETSU = '")
//			.append(getGyosyu((String) map.get("SystemKB"))).append("'");
		if(systemKb != null && systemKb.trim().length() > 0){
			sb.append("		and ");
			sb.append("				ts.EXCEL_FILE_SYUBETSU = '")
				.append(getGyosyu(systemKb)).append("'");
		}
//		↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
		// ===END=== Add by kou 2006/10/24
		sb.append("		group by ");
		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
		
		return sb.toString();
	}
	// ===END=== Add by kou 2006/8/17
	
	// ===BEGIN=== Add by kou 2006/8/17
	/**
	 * @param map
	 * @return
	 */
	private String getReigaiSql(Map map) {
		StringBuffer sb = new StringBuffer();
//		↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
		String systemKb = (String) map.get("SystemKB");
//		↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
		
		sb.append("		select  ");
		sb.append("				tts.torikomi_dt torikomi_dt  ");
		sb.append("				, ");
		sb.append("				tts.uketsuke_no uketsuke_no  ");
		sb.append("				, ");
		sb.append("				'0' as seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as tanpin_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as tanpin_syusei_kb  ");
		sb.append("				, ");
		sb.append("				max(ttr.uketsuke_seq) as reigai_seqmax  ");
		sb.append("				, ");
		sb.append("				max(ttr.syusei_kb) as reigai_syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as syokai_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as syokai_syusei_kb  ");
		sb.append("		from ");
		sb.append("				tr_toroku_syonin tts ");
		// ===BEGIN=== Modify by kou 2006/10/24
		//sb.append("				right outer join  ");
		//sb.append("				tr_tensyohin_reigai ttr  ");
		//sb.append("		on   ");
		//sb.append("				tts.torikomi_dt = ttr.torikomi_dt  ");
		//sb.append("		and ");
		//sb.append("				tts.uketsuke_no = ttr.uketsuke_no  ");
		//sb.append("		and ");
		//sb.append("				ttr.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
		sb.append("				, tr_tensyohin_reigai ttr  ");
		// ===END=== Modify by kou 2006/10/24
		sb.append("		where ");
//		↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
		sb.append("				(tts.syori_jyotai_fg = '0' or tts.toroku_syonin_fg = '1')  ");
//		↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
		sb.append("		and ");
		sb.append("				tts.delete_fg = '0'  ");
		sb.append("		and ");
		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
			sb.append("	and ");
			// ===BEGIN=== Modify by kou 2006/8/18
			//sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
			sb.append("			tts.siiresaki_cd LIKE '" + map.get("tx_shiire_cd") + "%'");
			// ===END=== Modify by kou 2006/8/18
		}
		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
		}
		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
		}
		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
			sb.append("	and ");
			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
		}
		// ===BEGIN=== Add by kou 2006/10/24
		sb.append("		and  ");
		sb.append("				tts.torikomi_dt = ttr.torikomi_dt  ");
		sb.append("		and ");
		sb.append("				tts.uketsuke_no = ttr.uketsuke_no  ");
		sb.append("		and ");
		sb.append("				ttr.bumon_cd = '0").append(map.get("tx_bumon_cd")).append("'");
//		↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		and ");
//		sb.append("				ttr.EXCEL_FILE_SYUBETSU = '")
//			.append(getGyosyu((String) map.get("SystemKB"))).append("'");
		if(systemKb != null && systemKb.trim().length() > 0){
			sb.append("		and ");
			sb.append("				ttr.EXCEL_FILE_SYUBETSU = '")
				.append(getGyosyu(systemKb)).append("'");
		}
//		↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
		// ===END=== Add by kou 2006/10/24
		sb.append("		group by ");
		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
		
		return sb.toString();
	}
	// ===END=== Add by kou 2006/8/17
	
	// ===BEGIN=== Add by kou 2006/8/17
	/**
	 * @param map
	 * @return
	 */
	private String getTanpinSql(Map map) {
		
		StringBuffer sb = new StringBuffer();
//		↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
		String systemKb = (String) map.get("SystemKB");
//		↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
		
		sb.append("		select  ");
		sb.append("				tts.torikomi_dt torikomi_dt  ");
		sb.append("				, ");
		sb.append("				tts.uketsuke_no uketsuke_no  ");
		sb.append("				, ");
		sb.append("				'0' as seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as syusei_kb  ");
		sb.append("				, ");
		sb.append("				max(ttt.uketsuke_seq) as tanpin_seqmax  ");
		sb.append("				, ");
		sb.append("				max(ttt.syusei_kb) as tanpin_syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as reigai_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as reigai_syusei_kb  ");
		sb.append("				, ");
		sb.append("				'0' as syokai_seqmax  ");
		sb.append("				, ");
		sb.append("				'0' as syokai_syusei_kb  ");
		sb.append("		from ");
		sb.append("				tr_toroku_syonin tts ");
		// ===BEGIN=== Modify by kou 2006/10/24
		//sb.append("				right outer join  ");
		//sb.append("				tr_tanpinten_toriatukai ttt  ");
		//sb.append("		on   ");
		//sb.append("				tts.torikomi_dt = ttt.torikomi_dt  ");
		//sb.append("		and ");
		//sb.append("				tts.uketsuke_no = ttt.uketsuke_no  ");
		//sb.append("		and ");
		//sb.append("				ttt.bumon_cd = '" + "0" + map.get("tx_bumon_cd") + "'");
		//// ===BEGIN=== Add by kou 2006/10/24
		//sb.append("		and ");
		//sb.append("				ttt.EXCEL_FILE_SYUBETSU = '")
		//	.append(getGyosyu((String) map.get("SystemKB"))).append("'");
		sb.append("				, tr_tanpinten_toriatukai ttt  ");
		// ===END=== Modify by kou 2006/10/24
		sb.append("		where ");
//		↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("				tts.syori_jyotai_fg = '0'  ");
		sb.append("				(tts.syori_jyotai_fg = '0' or tts.toroku_syonin_fg = '1')  ");
//		↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
		sb.append("		and ");
		sb.append("				tts.delete_fg = '0'  ");
		sb.append("		and ");
		sb.append("				tts.by_no = '" + map.get("tx_byNo") + "'");
		if (map.get("tx_shiire_cd") != null && map.get("tx_shiire_cd").toString().trim().length() > 0 ) {
			sb.append("	and ");
			// ===BEGIN=== Modify by kou 2006/8/18
			//sb.append("			tts.siiresaki_cd = '" + map.get("tx_shiire_cd") + "'");
			sb.append("			tts.siiresaki_cd LIKE '" + map.get("tx_shiire_cd") + "%'");
			// ===END=== Modify by kou 2006/8/18
		}
		if (map.get("tx_upload_date_from") != null && map.get("tx_upload_date_from").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			tts.torikomi_dt >= '" + map.get("tx_upload_date_from") + "'");
		}
		if (map.get("tx_upload_date_to") != null && map.get("tx_upload_date_to").toString().trim().length() > 0 ) {
			sb.append("	and ");
			sb.append("			tts.torikomi_dt <= '" + map.get("tx_upload_date_to") + "'");
		}
		if (map.get("rb_jyotai_kb") != null && !"X".equals(map.get("rb_jyotai_kb").toString())) {
			sb.append("	and ");
			sb.append("			tts.toroku_syonin_fg = '" + map.get("rb_jyotai_kb") + "'");
		}
		// ===BEGIN=== Add by kou 2006/10/24
		sb.append("		and  ");
		sb.append("				tts.torikomi_dt = ttt.torikomi_dt  ");
		sb.append("		and ");
		sb.append("				tts.uketsuke_no = ttt.uketsuke_no  ");
		sb.append("		and ");
		sb.append("				ttt.bumon_cd = '0").append(map.get("tx_bumon_cd")).append("'");
//		↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("		and ");
//		sb.append("				ttt.EXCEL_FILE_SYUBETSU = '")
//			.append(getGyosyu((String) map.get("SystemKB"))).append("'");
		if(systemKb != null && systemKb.trim().length() > 0){
			sb.append("		and ");
			sb.append("				ttt.EXCEL_FILE_SYUBETSU = '")
				.append(getGyosyu(systemKb)).append("'");
		}
//		↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑
		// ===END=== Add by kou 2006/10/24
		sb.append("		group by ");
		sb.append("				tts.torikomi_dt, tts.uketsuke_no  ");
		
		return sb.toString();
	}
	// ===END=== Add by kou 2006/8/17

/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst )
	{	
		return null;
	}

	/**
	 * 排他チェック用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSqlCheck(Object beanMst){
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("		update_ts update_ts ");
		sb.append("from ");
		sb.append("		tr_toroku_syonin  ");
		sb.append(" where");
		if( bean.getTorikomiDt() != null && bean.getTorikomiDt().trim().length() != 0 )
		{
			sb.append(" torikomi_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		}
		if( bean.getUketsukeNo() != null && bean.getUketsukeNo().trim().length() != 0 )
		{
			sb.append(" and ");
			sb.append(" uketsuke_no = ");
			sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
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
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("tr_toroku_syonin set ");
		if( bean.getTorokuSyoninFg() != null && bean.getTorokuSyoninFg().trim().length() != 0 )
		{
			sb.append(" toroku_syonin_fg = ");
			sb.append("'" + conv.convertString( bean.getTorokuSyoninFg() ) + "'");
		}
		if( bean.getSyonincommenttx() != null && bean.getSyonincommenttx().trim().length() != 0 )
		{
			sb.append(", ");
			sb.append(" syonin_comment_tx = ");
			sb.append("'" + conv.convertString( bean.getSyonincommenttx() ) + "'");
		}
		sb.append(", ");
		sb.append(" toroku_syonin_ts = ");
		try {
	    	sb.append("'"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+ "'");
	   	}catch (SQLException e){
	        e.printStackTrace();
	   	}  
		sb.append(", ");
		sb.append(" update_ts = ");
		try {
	    	sb.append("'"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+ "'");
	   	}catch (SQLException e){
	        e.printStackTrace();
	   	}  
		sb.append(", ");
		sb.append(" update_user_id = ");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		sb.append(" where");
		if( bean.getTorikomiDt() != null && bean.getTorikomiDt().trim().length() != 0 )
		{
			sb.append(" torikomi_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		}
		if( bean.getUketsukeNo() != null && bean.getUketsukeNo().trim().length() != 0 )
		{
			sb.append(" and ");
			sb.append(" uketsuke_no = ");
			sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		}
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをDBから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql( Object beanMst )
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを処理結果メッセージから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlRm( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_message where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを商品マスタTR（デイリー）から削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlFre( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_syohin_fre where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを商品マスタTR（グロサリー/バラエティ）から削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlGro( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_syohin_gro where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		return sb.toString();
	}
	
	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを商品マスタTR（実用）から削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlA07( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_syohin_a07 where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		return sb.toString();
	}
	
	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを商品マスタTR（TAG）から削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlA08( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_syohin_a08 where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		return sb.toString();
	}
	
	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを初回導入マスタTRから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlTs( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_syokaidonyu where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		return sb.toString();
	}
	
	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを店別商品例外マスタTRから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlTtr( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_tensyohin_reigai where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		return sb.toString();
	}
	
	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを単品店取扱マスタTRから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlTtt( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_tanpinten_toriatukai where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
		return sb.toString();
	}
	
	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを登録票承認TRから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSqlTts( Object beanMst )
	{
		mstA70301_KeepMeisaiBean bean = (mstA70301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_toroku_syonin where ");
		sb.append(" torikomi_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getTorikomiDt() ) + "'");
		sb.append(" and ");
		sb.append(" uketsuke_no = ");
		sb.append("'" + conv.convertWhereString( bean.getUketsukeNo() ) + "'");
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

	/**
	 * @param map
	 * @return
	 */
	public String getSystemKbSql(String bumonCd) {
	
		StringBuffer sb = new StringBuffer();
		
		sb.append("	select distinct ");
		sb.append("		rst.system_kb as system_kb");
		sb.append("	from ");
		sb.append("		r_syohin_taikei rst ");
		sb.append("	where ");
		sb.append("		rst.bumon_cd = '0").append(bumonCd).append("' ");
		
		return sb.toString();
	}
}
