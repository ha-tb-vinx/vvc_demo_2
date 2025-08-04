package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.util.RMSTDATEUtil;
//↑↑2006.09.11 H.Yamamoto カスタマイズ修正↑↑

/**
 * <P>タイトル : mst380401_ExcelUploadDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用する登録票登録票アープロッドに使用(DmCreatorにより自動生成) </P>
 * <P>著作権: Copyright (c) 2005</P>
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @see なし
 */
public class mst380401_ExcelUploadDM extends DataModule
{
	//削除フラグ
	private static String DEL_FG = mst000801_DelFlagDictionary.INAI.getCode();

	// マスタ日付
	private String MasterDT = RMSTDATEUtil.getMstDateDt();

	/**
	 * コンストラクタ
	 */
	public mst380401_ExcelUploadDM()
	{
		super( "rbsite_ora");
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
		mst380401_ExcelUploadBean bean = new mst380401_ExcelUploadBean();
		bean.setTsTorikomiDt( encodingString(rest.getString("torikomi_dt")) );
		bean.setTsExcelFileSyubetsu( encodingString(rest.getString("excel_file_syubetsu")) );
		bean.setTsUketsukeNo( rest.getLong("uketsuke_no") );
		bean.setTsExcelFileNa( encodingString(rest.getString("excel_file_na")) );
//		↓↓2006.07.12 baozy 暫定対応↓↓
//		bean.setTsAreaCd( encodingString(rest.getString("area_cd")) );
//		↑↑2006.07.12 baozy 暫定対応↑↑
		bean.setTsSiiresakiCd( encodingString(rest.getString("siiresaki_cd")) );
		bean.setTsUploadCommentTx( encodingString(rest.getString("upload_comment_tx")) );
		bean.setTsTorokuSyoninFg( encodingString(rest.getString("toroku_syonin_fg")) );
		bean.setTsTorokuSyoninTs( encodingString(rest.getString("toroku_syonin_ts")) );
		bean.setTsByNo( encodingString(rest.getString("by_no")) );
		bean.setTsSyoninCommentTx( encodingString(rest.getString("syonin_comment_tx")) );
		bean.setTsExcelSyohinKb( encodingString(rest.getString("excel_syohin_kb")) );
		bean.setTsExcelTanpinKb( encodingString(rest.getString("excel_tanpin_kb")) );
		bean.setTsExcelReigaiKb( encodingString(rest.getString("excel_reigai_kb")) );
		bean.setTsExcelSyokaiKb( encodingString(rest.getString("excel_syokai_kb")) );
		bean.setTsMinNeireRt( rest.getDouble("min_neire_rt") );
		bean.setTsMaxNeireRt( rest.getDouble("max_neire_rt") );
		bean.setTsMaxUritankaVl( rest.getLong("max_uritanka_vl") );
		bean.setTsSyoriJyotaiFg( encodingString(rest.getString("syori_jyotai_fg")) );
		bean.setTsKakuninFg( encodingString(rest.getString("kakunin_fg")) );
		bean.setTsDeleteFg( encodingString(rest.getString("delete_fg")) );
		bean.setTsInsertTs( encodingString(rest.getString("insert_ts")) );
		bean.setTsInsertUserId( encodingString(rest.getString("insert_user_id")) );
		bean.setTsUpdateTs( encodingString(rest.getString("update_ts")) );
		bean.setTsUpdateUserId( encodingString(rest.getString("update_user_id")) );
		bean.setTsRiyoUserNa( encodingString(rest.getString("riyo_user_na")) );
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("select  ");
		sb.append("	  ts.torikomi_dt, ");
		sb.append("	  ts.excel_file_syubetsu, ");
		sb.append("	  ts.uketsuke_no, ");
		sb.append("	  ts.excel_file_na, ");
//		↓↓2006.07.12 baozy 暫定対応↓↓
//		sb.append("	  ts.area_cd, ");
//		↑↑2006.07.12 baozy 暫定対応↑↑
		sb.append("	  ts.siiresaki_cd, ");
		sb.append("	  ts.upload_comment_tx, ");
		sb.append("	  ts.toroku_syonin_fg, ");
		sb.append("	  ts.toroku_syonin_ts, ");
		sb.append("	  ts.by_no, ");
		sb.append("	  ts.syonin_comment_tx, ");
		sb.append("	  ts.excel_syohin_kb, ");
		sb.append("	  ts.excel_tanpin_kb, ");
		sb.append("	  ts.excel_reigai_kb, ");
		sb.append("	  ts.excel_syokai_kb, ");
		sb.append("	  ts.min_neire_rt, ");
		sb.append("	  ts.max_neire_rt, ");
		sb.append("	  ts.max_uritanka_vl, ");
		sb.append("	  ts.syori_jyotai_fg, ");
		sb.append("	  ts.kakunin_fg, ");
		sb.append("	  ts.delete_fg, ");
		sb.append("	  ts.insert_ts, ");
		sb.append("	  ts.insert_user_id, ");
		sb.append("	  ts.update_ts, ");
		sb.append("	  ts.update_user_id, ");
//		add by kema 06.09.08
		// ===BEGIN=== Modify by kou 2006/10/30
		//sb.append(" (select MAX(riyo_user_na) from r_riyo_user where ");
//		sb.append(" (select riyo_user_na from r_riyo_user where ");
////		 ===END=== Modify by kou 2006/10/30
//		sb.append("  riyo_user_id = ts.by_no ) as riyo_user_na ");
		sb.append(" (select user_na from r_portal_user where ");
		sb.append("  user_id = TRIM(ts.by_no) ) as riyo_user_na ");
		sb.append(" from ");
		sb.append("       tr_toroku_syonin ts ");
		sb.append("   where  ");
//		sb.append("       ( ts.by_no =  '"+ conv.convertWhereString( map.get("user_id").toString() ) +"'");
//		sb.append("       or ts.siiresaki_cd =  '"+ conv.convertWhereString( map.get("user_id").toString() ) +"') ");
		sb.append("   ts.delete_fg = '0' ");
		// ===BEGIN=== Add by kou 2006/8/27
		// 作成者IDとログインIDを一致する条件を追加
		sb.append("		and ts.insert_user_id = '")
			.append(conv.convertWhereString( map.get("user_id").toString()))
			.append("'");
		// ===END=== Add by kou 2006/8/27
//		add by kema 06.09.08
		if (!"".equals(map.get("buyer_no"))) {
			sb.append(" and ts.by_no = '0000")
			.append(conv.convertWhereString( map.get("buyer_no").toString()))
			.append("'");
		}
		int fg = Integer.parseInt(map.get("kakunin_flg").toString());
		switch (fg){
			case 0:
				sb.append(" and ts.syori_jyotai_fg = '0' and ts.toroku_syonin_fg = '0' ");
				break;
			case 1:
				sb.append(" and (ts.syori_jyotai_fg = '1' or ts.syori_jyotai_fg = '9') ");
				break;
			case 2:
				sb.append(" and ts.syori_jyotai_fg = '0' and ts.toroku_syonin_fg = '1' ");
				break;
			case 3:
				sb.append(" and ts.syori_jyotai_fg = '0' and ts.toroku_syonin_fg = '2' ");
				break;
			case 4:
				sb.append(" and ts.syori_jyotai_fg = '2' ");
				break;
		}
		if ("1".equals(map.get("chkflg"))) {
			sb.append("   and ts.torikomi_dt =  '"+ conv.convertWhereString( map.get("torikomi_dt").toString() ) +"'");
			sb.append("   and ts.excel_file_syubetsu =  '"+ conv.convertWhereString( map.get("excel_file_syubetsu").toString() ) +"'");
			sb.append("   and ts.uketsuke_no =  '"+ conv.convertWhereString( map.get("uketsuke_no").toString() ) +"'");
		}
		sb.append("   order by ts.torikomi_dt desc,  ");
		sb.append("     ts.uketsuke_no desc,  ");
		sb.append("     ts.syori_jyotai_fg desc  ");
		return sb.toString();
	}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getInsertSql( Object beanMst )
	{
		return null;
	}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getUpdateSql( Object beanMst )
	{
		mst380401_ExcelUploadBean bean = (mst380401_ExcelUploadBean)beanMst;
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName());

		StringBuffer sb = new StringBuffer();
		sb.append(" update ");
		sb.append("     tr_toroku_syonin ts ");
		sb.append(" set ");
		sb.append("     ts.kakunin_fg = '1', ");
		sb.append("     ts.update_ts = '" + conv.convertWhereString( bean.getTsUpdateTs()) +"', ");
		sb.append("     ts.update_user_id = '" + conv.convertWhereString(bean.getTsUpdateUserId()) +"' ");
		sb.append(" where ");
		sb.append("     ts.torikomi_dt = '" + conv.convertWhereString( bean.getTsTorikomiDt()) +"' ");
		sb.append("   and ");
		sb.append("     ts.excel_file_syubetsu = '" + conv.convertWhereString( bean.getTsExcelFileSyubetsu()) +"' ");
		sb.append("   and ");
		sb.append("     ts.uketsuke_no = '" + conv.convertWhereString( bean.getTsUketsukeNoString()) +"'");
		sb.append("   and ts.delete_fg = '0' ");

		return sb.toString();
	}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getDeleteSql( Object beanMst )
	{
		return null;
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
	 * TRテーブルの検索用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectUnionSql(String torikomiDt, String uketsukeNo) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '01' a08_sheet_syubetsu,  ");
		sb.append("	a08.torikomi_dt a08_torikomi_dt, ");
		sb.append("	a08.excel_file_syubetsu a08_excel_file_syubetsu, ");
		sb.append("	a08.uketsuke_no a08_uketsuke_no, ");
		sb.append("	a08.uketsuke_seq a08_uketsuke_seq, ");
		sb.append("	a08.bumon_cd a08_bumon_cd, ");
		sb.append("	a08.unit_cd a08_unit_cd, ");
		sb.append("	a08.haifu_cd a08_haifu_cd, ");
		sb.append("	a08.subclass_cd a08_subclass_cd, ");
		sb.append("	a08.syohin_cd a08_syohin_cd, ");
		sb.append("	a08.yuko_dt a08_yuko_dt, ");
		sb.append("	a08.hinmei_kanji_na a08_hinmei_kanji_na, ");
		sb.append("	a08.hinmei_kana_na a08_hinmei_kana_na, ");
		sb.append("	a08.size_cd a08_size_cd, ");
		sb.append("	a08.color_cd a08_color_cd, ");
		sb.append("	a08.gentanka_vl a08_gentanka_vl, ");
		sb.append("	a08.baitanka_vl a08_baitanka_vl, ");
		sb.append("	a08.keshi_baika_vl a08_keshi_baika_vl, ");
		sb.append("	a08.tokubetu_genka_vl a08_tokubetu_genka_vl, ");
		sb.append("	a08.keiyakusu_qt a08_keiyakusu_qt, ");
		sb.append("	a08.tuika_keiyakusu_qt a08_tuika_keiyakusu_qt, ");
//		↓↓2006.07.12 baozy 暫定対応↓↓
//     sb.append("	a08.area_cd a08_area_cd, ");
//		↑↑2006.07.12 baozy 暫定対応↑↑
		sb.append("	a08.siiresaki_cd a08_siiresaki_cd, ");
		sb.append("	a08.siire_hinban_cd a08_siire_hinban_cd, ");
		sb.append("	a08.hanbai_st_dt a08_hanbai_st_dt, ");
		sb.append("	a08.hanbai_ed_dt a08_hanbai_ed_dt, ");
		sb.append("	a08.ten_hachu_st_dt a08_ten_hachu_st_dt, ");
		sb.append("	a08.ten_hachu_ed_dt a08_ten_hachu_ed_dt, ");
		sb.append("	a08.eos_kb a08_eos_kb, ");
		sb.append("	a08.season_cd a08_season_cd, ");
		sb.append("	a08.bland_cd a08_bland_cd, ");
		sb.append("	a08.pb_kb a08_pb_kb, ");
		sb.append("	a08.yoridori_vl a08_yoridori_vl, ");
		sb.append("	a08.yoridori_qt a08_yoridori_qt, ");
		sb.append("	a08.yoridori_kb a08_yoridori_kb, ");
		sb.append("	a08.nefuda_kb a08_nefuda_kb, ");
		sb.append("	a08.nefuda_ukewatasi_dt a08_nefuda_ukewatasi_dt, ");
		sb.append("	a08.nefuda_ukewatasi_kb a08_nefuda_ukewatasi_kb, ");
		sb.append("	a08.fuji_syohin_kb a08_fuji_syohin_kb, ");
		sb.append("	a08.sozai_cd a08_sozai_cd, ");
		sb.append("	a08.oriami_cd a08_oriami_cd, ");
		sb.append("	a08.sode_cd a08_sode_cd, ");
		sb.append("	a08.age_cd a08_age_cd, ");
		sb.append("	a08.pattern_cd a08_pattern_cd, ");
		sb.append("	'' a08_temp1, ");
		sb.append("	'' a08_temp2, ");
		sb.append("	'' a08_temp3, ");
		sb.append("	'' a08_temp4, ");
		sb.append("	'' a08_temp5, ");
		sb.append("	'' a08_temp6, ");
		sb.append("	'' a08_temp7, ");
		sb.append("	'' a08_temp8, ");
		sb.append("	'' a08_temp9, ");
//		↓↓2006.09.02 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	'' a08_temp10, ");
		sb.append("	'' a08_temp11, ");
//		↑↑2006.09.02 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	a08.syusei_kb a08_syusei_kb, ");
		sb.append("	0 a08_temp10, ");
		sb.append("	a08.sakusei_gyo_no a08_sakusei_gyo_no, ");
		sb.append("	a08.mst_mainte_fg a08_mst_mainte_fg, ");
		sb.append("	a08.alarm_make_fg a08_alarm_make_fg, ");
		sb.append("	a08.insert_ts a08_insert_ts, ");
		sb.append("	a08.insert_user_id a08_insert_user_id, ");
		sb.append("	a08.update_ts a08_update_ts, ");
		sb.append("	a08.update_user_id a08_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_a08 a08 ");
		sb.append("   where  ");
		sb.append("     a08.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     a08.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '01' a07_sheet_syubetsu,  ");
		sb.append("	a07.torikomi_dt a07_torikomi_dt, ");
		sb.append("	a07.excel_file_syubetsu a07_excel_file_syubetsu, ");
		sb.append("	a07.uketsuke_no a07_uketsuke_no, ");
		sb.append("	a07.uketsuke_seq a07_uketsuke_seq, ");
		sb.append("	a07.bumon_cd a07_bumon_cd, ");
		sb.append("	a07.unit_cd a07_unit_cd, ");
		sb.append("	a07.haifu_cd a07_haifu_cd, ");
		sb.append("	a07.subclass_cd a07_subclass_cd, ");
		sb.append("	a07.syohin_cd a07_syohin_cd, ");
		sb.append("	a07.yuko_dt a07_yuko_dt, ");
		sb.append("	a07.maker_cd a07_maker_cd, ");
		sb.append("	a07.pos_cd a07_pos_cd, ");
		sb.append("	a07.hinmei_kanji_na a07_hinmei_kanji_na, ");
		sb.append("	a07.kikaku_kanji_na a07_kikaku_kanji_na, ");
		sb.append("	a07.kikaku_kanji_2_na a07_kikaku_kanji_2_na, ");
		sb.append("	a07.hinmei_kana_na a07_hinmei_kana_na, ");
		sb.append("	a07.kikaku_kana_na a07_kikaku_kana_na, ");
		sb.append("	a07.kikaku_kana_2_na a07_kikaku_kana_2_na, ");
		sb.append("	a07.rec_hinmei_kanji_na a07_rec_hinmei_kanji_na, ");
		sb.append("	a07.rec_hinmei_kana_na a07_rec_hinmei_kana_na, ");
		sb.append("	a07.size_cd a07_size_cd, ");
		sb.append("	a07.color_cd a07_color_cd, ");
		sb.append("	a07.gentanka_vl a07_gentanka_vl, ");
		sb.append("	a07.baitanka_vl a07_baitanka_vl, ");
		sb.append("	a07.keshi_baika_vl a07_keshi_baika_vl, ");
		sb.append("	a07.keiyakusu_qt a07_keiyakusu_qt, ");
		sb.append("	a07.tuika_keiyakusu_qt a07_tuika_keiyakusu_qt, ");
		sb.append("	a07.hachutani_irisu_qt a07_hachutani_irisu_qt, ");
		sb.append("	a07.hachu_tani_na a07_hachu_tani_na, ");
		sb.append("	a07.plu_send_dt a07_plu_send_dt, ");
//		↓↓2006.07.12 baozy 暫定対応↓↓
//	     sb.append("	a07.area_cd a07_area_cd, ");
//		↑↑2006.07.12 baozy 暫定対応↑↑
		sb.append("	a07.siiresaki_cd a07_siiresaki_cd, ");
		sb.append("	a07.siire_hinban_cd a07_siire_hinban_cd, ");
		sb.append("	a07.hanbai_st_dt a07_hanbai_st_dt, ");
		sb.append("	a07.hanbai_ed_dt a07_hanbai_ed_dt, ");
		sb.append("	a07.ten_hachu_st_dt a07_ten_hachu_st_dt, ");
		sb.append("	a07.ten_hachu_ed_dt a07_ten_hachu_ed_dt, ");
		sb.append("	a07.eos_kb a07_eos_kb, ");
		sb.append("	a07.season_cd a07_season_cd, ");
		sb.append("	a07.bland_cd a07_bland_cd, ");
		sb.append("	a07.pb_kb a07_pb_kb, ");
		sb.append("	a07.yoridori_vl a07_yoridori_vl, ");
		sb.append("	a07.yoridori_qt a07_yoridori_qt, ");
		sb.append("	a07.yoridori_kb a07_yoridori_kb, ");
		sb.append("	a07.tana_no_nb a07_tana_no_nb, ");
		sb.append("	a07.nefuda_kb a07_nefuda_kb, ");
		sb.append("	a07.nefuda_ukewatasi_dt a07_nefuda_ukewatasi_dt, ");
		sb.append("	a07.nefuda_ukewatasi_kb a07_nefuda_ukewatasi_kb, ");
		sb.append("	a07.fuji_syohin_kb a07_fuji_syohin_kb, ");
		sb.append("	a07.pc_kb a07_pc_kb, ");
		sb.append("	a07.daisi_no_nb a07_daisi_no_nb, ");
		sb.append("	'' a07_temp1, ");
//		↓↓2006.09.02 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	'' a07_temp2, ");
		sb.append("	'' a07_temp3, ");
//		↑↑2006.09.02 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	a07.syusei_kb a07_syusei_kb, ");
		sb.append("	0 a07_temp2, ");
		sb.append("	a07.sakusei_gyo_no a07_sakusei_gyo_no, ");
		sb.append("	a07.mst_mainte_fg a07_mst_mainte_fg, ");
		sb.append("	a07.alarm_make_fg a07_alarm_make_fg, ");
		sb.append("	a07.insert_ts a07_insert_ts, ");
		sb.append("	a07.insert_user_id a07_insert_user_id, ");
		sb.append("	a07.update_ts a07_update_ts, ");
		sb.append("	a07.update_user_id a07_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_a07 a07 ");
		sb.append("   where  ");
		sb.append("     a07.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     a07.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '01' gro_sheet_syubetsu,  ");
		sb.append("	gro.torikomi_dt gro_torikomi_dt, ");
		sb.append("	gro.excel_file_syubetsu gro_excel_file_syubetsu, ");
		sb.append("	gro.uketsuke_no gro_uketsuke_no, ");
		sb.append("	gro.uketsuke_seq gro_uketsuke_seq, ");
		sb.append("	gro.bumon_cd gro_bumon_cd, ");
		sb.append("	gro.unit_cd gro_unit_cd, ");
		sb.append("	gro.haifu_cd gro_haifu_cd, ");
		sb.append("	gro.subclass_cd gro_subclass_cd, ");
		sb.append("	gro.syohin_cd gro_syohin_cd, ");
		sb.append("	gro.yuko_dt gro_yuko_dt, ");
		sb.append("	gro.maker_cd gro_maker_cd, ");
		sb.append("	'' gro_temp1, ");
		sb.append("	gro.hinmei_kanji_na gro_hinmei_kanji_na, ");
		sb.append("	gro.kikaku_kanji_na gro_kikaku_kanji_na, ");
		sb.append("	gro.kikaku_kanji_2_na gro_kikaku_kanji_2_na, ");
		sb.append("	gro.hinmei_kana_na gro_hinmei_kana_na, ");
		sb.append("	gro.kikaku_kana_na gro_kikaku_kana_na, ");
		sb.append("	gro.kikaku_kana_2_na gro_kikaku_kana_2_na, ");
		sb.append("	gro.rec_hinmei_kanji_na gro_rec_hinmei_kanji_na, ");
		sb.append("	gro.rec_hinmei_kana_na gro_rec_hinmei_kana_na, ");
		sb.append("	gro.gentanka_vl gro_gentanka_vl, ");
		sb.append("	gro.baitanka_vl gro_baitanka_vl, ");
		sb.append("	gro.plu_send_dt gro_plu_send_dt, ");
//		↓↓2006.07.12 baozy 暫定対応↓↓
//	     sb.append("	gro.area_cd gro_area_cd, ");
//		↑↑2006.07.12 baozy 暫定対応↑↑
		sb.append("	gro.siiresaki_cd gro_siiresaki_cd, ");
		sb.append("	gro.ten_siiresaki_kanri_cd gro_ten_siiresaki_kanri_cd, ");
		sb.append("	gro.siire_hinban_cd gro_siire_hinban_cd, ");
		sb.append("	gro.hanbai_st_dt gro_hanbai_st_dt, ");
		sb.append("	gro.hanbai_ed_dt gro_hanbai_ed_dt, ");
		sb.append("	gro.ten_hachu_st_dt gro_ten_hachu_st_dt, ");
		sb.append("	gro.ten_hachu_ed_dt gro_ten_hachu_ed_dt, ");
		sb.append("	gro.eos_kb gro_eos_kb, ");
		sb.append("	gro.honbu_zai_kb gro_honbu_zai_kb, ");
		sb.append("	gro.hachu_tani_na gro_hachu_tani_na, ");
		sb.append("	gro.hachutani_irisu_qt gro_hachutani_irisu_qt, ");
		sb.append("	gro.max_hachutani_qt gro_max_hachutani_qt, ");
		sb.append("	gro.bland_cd gro_bland_cd, ");
		sb.append("	gro.pb_kb gro_pb_kb, ");
		sb.append("	gro.maker_kibo_kakaku_vl gro_maker_kibo_kakaku_vl, ");
		sb.append("	gro.fuji_syohin_kb gro_fuji_syohin_kb, ");
		sb.append("	gro.pc_kb gro_pc_kb, ");
		sb.append("	gro.daisi_no_nb gro_daisi_no_nb, ");
		sb.append("	gro.daicho_tenpo_kb gro_daicho_tenpo_kb, ");
		sb.append("	gro.daicho_honbu_kb gro_daicho_honbu_kb, ");
		sb.append("	gro.daicho_siiresaki_kb gro_daicho_siiresaki_kb, ");
		sb.append("	gro.syuzei_hokoku_kb gro_syuzei_hokoku_kb, ");
		sb.append("	gro.unit_price_tani_kb gro_unit_price_tani_kb, ");
		sb.append("	gro.unit_price_naiyoryo_qt gro_unit_price_naiyoryo_qt, ");
		sb.append("	gro.unit_price_kijun_tani_qt gro_unit_price_kijun_tani_qt, ");
		sb.append("	gro.syohi_kigen_dt gro_syohi_kigen_dt, ");
		sb.append("	'' gro_temp2, ");
		sb.append("	'' gro_temp3, ");
//		↓↓2006.09.02 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	'' gro_temp4, ");
		sb.append("	'' gro_temp5, ");
//		↑↑2006.09.02 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	gro.syusei_kb gro_syusei_kb, ");
		sb.append("	0 gro_temp4, ");
		sb.append("	gro.sakusei_gyo_no gro_sakusei_gyo_no, ");
		sb.append("	gro.mst_mainte_fg gro_mst_mainte_fg, ");
		sb.append("	gro.alarm_make_fg gro_alarm_make_fg, ");
		sb.append("	gro.insert_ts gro_insert_ts, ");
		sb.append("	gro.insert_user_id gro_insert_user_id, ");
		sb.append("	gro.update_ts gro_update_ts, ");
		sb.append("	gro.update_user_id gro_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_gro gro ");
		sb.append("   where  ");
		sb.append("     gro.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     gro.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '01' fre_sheet_syubetsu,  ");
		sb.append("	fre.torikomi_dt fre_torikomi_dt, ");
		sb.append("	fre.excel_file_syubetsu fre_excel_file_syubetsu, ");
		sb.append("	fre.uketsuke_no fre_uketsuke_no, ");
		sb.append("	fre.uketsuke_seq fre_uketsuke_seq, ");
		sb.append("	fre.bumon_cd fre_bumon_cd, ");
		sb.append("	fre.unit_cd fre_unit_cd, ");
		sb.append("	fre.haifu_cd fre_haifu_cd, ");
		sb.append("	fre.subclass_cd fre_subclass_cd, ");
		sb.append("	fre.syohin_cd fre_syohin_cd, ");
		sb.append("	fre.yuko_dt fre_yuko_dt, ");
		sb.append("	fre.maker_cd fre_maker_cd, ");
		sb.append("	'' fre_temp1, ");
		sb.append("	fre.hinmei_kanji_na fre_hinmei_kanji_na, ");
		sb.append("	fre.kikaku_kanji_na fre_kikaku_kanji_na, ");
		sb.append("	fre.kikaku_kanji_2_na fre_kikaku_kanji_2_na, ");
		sb.append("	fre.hinmei_kana_na fre_hinmei_kana_na, ");
		sb.append("	fre.kikaku_kana_na fre_kikaku_kana_na, ");
		sb.append("	fre.kikaku_kana_2_na fre_kikaku_kana_2_na, ");
		sb.append("	fre.rec_hinmei_kanji_na fre_rec_hinmei_kanji_na, ");
		sb.append("	fre.rec_hinmei_kana_na fre_rec_hinmei_kana_na, ");
		sb.append("	fre.gentanka_vl fre_gentanka_vl, ");
		sb.append("	fre.baitanka_vl fre_baitanka_vl, ");
		sb.append("	fre.plu_send_dt fre_plu_send_dt, ");
//		↓↓2006.07.12 baozy 暫定対応↓↓
//	     sb.append("	fre.area_cd fre_area_cd, ");
//		↑↑2006.07.12 baozy 暫定対応↑↑
		sb.append("	fre.siiresaki_cd fre_siiresaki_cd, ");
		sb.append("	fre.ten_siiresaki_kanri_cd fre_ten_siiresaki_kanri_cd, ");
		sb.append("	fre.siire_hinban_cd fre_siire_hinban_cd, ");
		sb.append("	fre.hanbai_st_dt fre_hanbai_st_dt, ");
		sb.append("	fre.hanbai_ed_dt fre_hanbai_ed_dt, ");
		sb.append("	fre.ten_hachu_st_dt fre_ten_hachu_st_dt, ");
		sb.append("	fre.ten_hachu_ed_dt fre_ten_hachu_ed_dt, ");
		sb.append("	fre.eos_kb fre_eos_kb, ");
		sb.append("	fre.honbu_zai_kb fre_honbu_zai_kb, ");
		sb.append("	fre.hachu_tani_na fre_hachu_tani_na, ");
		sb.append("	fre.hachutani_irisu_qt fre_hachutani_irisu_qt, ");
		sb.append("	fre.max_hachutani_qt fre_max_hachutani_qt, ");
		sb.append("	fre.bland_cd fre_bland_cd, ");
		sb.append("	fre.pb_kb fre_pb_kb, ");
		sb.append("	fre.maker_kibo_kakaku_vl fre_maker_kibo_kakaku_vl, ");
		sb.append("	fre.bin_1_kb fre_bin_1_kb, ");
		sb.append("	fre.bin_2_kb fre_bin_2_kb, ");
		sb.append("	fre.yusen_bin_kb fre_yusen_bin_kb, ");
		sb.append("	fre.fuji_syohin_kb fre_fuji_syohin_kb, ");
		sb.append("	fre.pc_kb fre_pc_kb, ");
		sb.append("	fre.daisi_no_nb fre_daisi_no_nb, ");
		sb.append("	fre.daicho_tenpo_kb fre_daicho_tenpo_kb, ");
		sb.append("	fre.daicho_honbu_kb fre_daicho_honbu_kb, ");
		sb.append("	fre.daicho_siiresaki_kb fre_daicho_siiresaki_kb, ");
		sb.append("	fre.unit_price_tani_kb fre_unit_price_tani_kb, ");
		sb.append("	fre.unit_price_naiyoryo_qt fre_unit_price_naiyoryo_qt, ");
		sb.append("	fre.unit_price_kijun_tani_qt fre_unit_price_kijun_tani_qt, ");
		sb.append("	fre.syohi_kigen_dt fre_syohi_kigen_dt, ");
//		↓↓2006.09.02 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	fre.nyuka_kyoyo_qt fre_nyuka_kyoyo_qt, ");
		sb.append("	fre.hanbai_gendo_qt fre_hanbai_gendo_qt, ");
//		↑↑2006.09.02 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	fre.syusei_kb fre_syusei_kb, ");
		sb.append("	0 fre_temp1, ");
		sb.append("	fre.sakusei_gyo_no fre_sakusei_gyo_no, ");
		sb.append("	fre.mst_mainte_fg fre_mst_mainte_fg, ");
		sb.append("	fre.alarm_make_fg fre_alarm_make_fg, ");
		sb.append("	fre.insert_ts fre_insert_ts, ");
		sb.append("	fre.insert_user_id fre_insert_user_id, ");
		sb.append("	fre.update_ts fre_update_ts, ");
		sb.append("	fre.update_user_id fre_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_fre fre ");
		sb.append("   where  ");
		sb.append("     fre.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     fre.uketsuke_no = '" + uketsukeNo +"' ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" union ");
//		sb.append(" select  ");
//		sb.append(" '02' tanpin_sheet_syubetsu,  ");
//		sb.append("	tanpin.torikomi_dt tanpin_torikomi_dt, ");
//		sb.append("	tanpin.excel_file_syubetsu tanpin_excel_file_syubetsu, ");
//		sb.append("	tanpin.uketsuke_no tanpin_uketsuke_no, ");
//		sb.append("	tanpin.uketsuke_seq tanpin_uketsuke_seq, ");
//		sb.append("	tanpin.bumon_cd tanpin_bumon_cd, ");
//		sb.append("	tanpin.syohin_cd tanpin_syohin_cd, ");
//		sb.append("	tanpin.donyu_dt tanpin_donyu_dt, ");
//		sb.append("	tanpin.tenpo_cd tanpin_tenpo_cd, ");
//		sb.append("	'' tanpin_temp1, ");
//		sb.append("	'' tanpin_temp2, ");
//		sb.append("	'' tanpin_temp3, ");
//		sb.append("	'' tanpin_temp4, ");
//		sb.append("	'' tanpin_temp5, ");
//		sb.append("	'' tanpin_temp6, ");
//		sb.append("	'' tanpin_temp7, ");
//		sb.append("	'' tanpin_temp8, ");
//		sb.append("	'' tanpin_temp9, ");
//		sb.append("	'' tanpin_temp10, ");
//		sb.append("	'' tanpin_temp11, ");
//		sb.append("	'' tanpin_temp12, ");
//		sb.append("	'' tanpin_temp13, ");
//		sb.append("	'' tanpin_temp14, ");
//		sb.append("	'' tanpin_temp15, ");
//		sb.append("	'' tanpin_temp16, ");
//		sb.append("	'' tanpin_temp17, ");
//		sb.append("	'' tanpin_temp18, ");
//		sb.append("	'' tanpin_temp19, ");
//		sb.append("	'' tanpin_temp20, ");
//		sb.append("	'' tanpin_temp21, ");
//		sb.append("	'' tanpin_temp22, ");
//		sb.append("	'' tanpin_temp23, ");
//		sb.append("	'' tanpin_temp24, ");
//		sb.append("	'' tanpin_temp25, ");
//		sb.append("	'' tanpin_temp26, ");
//		sb.append("	'' tanpin_temp27, ");
//		sb.append("	'' tanpin_temp28, ");
//		sb.append("	'' tanpin_temp29, ");
//		sb.append("	'' tanpin_temp30, ");
//		sb.append("	'' tanpin_temp31, ");
//		sb.append("	'' tanpin_temp32, ");
//		sb.append("	'' tanpin_temp33, ");
//		sb.append("	'' tanpin_temp34, ");
//		sb.append("	'' tanpin_temp35, ");
//		sb.append("	'' tanpin_temp36, ");
//		sb.append("	'' tanpin_temp37, ");
//		sb.append("	'' tanpin_temp38, ");
//		sb.append("	'' tanpin_temp39, ");
//		sb.append("	'' tanpin_temp40, ");
//		sb.append("	'' tanpin_temp41, ");
//		sb.append("	'' tanpin_temp42, ");
//		sb.append("	'' tanpin_temp43, ");
////		sb.append("	'' tanpin_temp44, ");
//		sb.append("	tanpin.syusei_kb tanpin_syusei_kb, ");
//		sb.append("	tanpin.syohin_gyo_no tanpin_syohin_gyo_no, ");
//		sb.append("	tanpin.sakusei_gyo_no tanpin_sakusei_gyo_no, ");
//		sb.append("	tanpin.mst_mainte_fg tanpin_mst_mainte_fg, ");
//		sb.append("	tanpin.alarm_make_fg tanpin_alarm_make_fg, ");
//		sb.append("	tanpin.insert_ts tanpin_insert_ts, ");
//		sb.append("	tanpin.insert_user_id tanpin_insert_user_id, ");
//		sb.append("	tanpin.update_ts tanpin_update_ts, ");
//		sb.append("	tanpin.update_user_id tanpin_update_user_id ");
//		sb.append(" from ");
//		sb.append("     tr_tanpinten_toriatukai tanpin  ");
//		sb.append("   where  ");
//		sb.append("     tanpin.torikomi_dt = '"+ torikomiDt +"' ");
//		sb.append("     and  ");
//		sb.append("     tanpin.uketsuke_no = '"+ uketsukeNo +"' ");
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '03' reigai_sheet_syubetsu,  ");
		sb.append("	reigai.torikomi_dt reigai_torikomi_dt, ");
		sb.append("	reigai.excel_file_syubetsu reigai_excel_file_syubetsu, ");
		sb.append("	reigai.uketsuke_no reigai_uketsuke_no, ");
		sb.append("	reigai.uketsuke_seq reigai_uketsuke_seq, ");
		sb.append("	reigai.bumon_cd reigai_bumon_cd, ");
		sb.append("	reigai.syohin_cd reigai_syohin_cd, ");
		sb.append("	reigai.tenpo_cd reigai_tenpo_cd, ");
		sb.append("	reigai.yuko_dt reigai_yuko_dt, ");
		sb.append("	reigai.ten_hachu_st_dt reigai_ten_hachu_st_dt, ");
		sb.append("	reigai.ten_hachu_ed_dt reigai_ten_hachu_ed_dt, ");
		sb.append("	reigai.gentanka_vl reigai_gentanka_vl, ");
		sb.append("	reigai.baitanka_vl reigai_baitanka_vl, ");
		sb.append("	reigai.max_hachutani_qt reigai_max_hachutani_qt, ");
		sb.append("	reigai.eos_kb reigai_eos_kb, ");
//		↓↓2006.07.12 baozy 暫定対応↓↓
//	     sb.append("	reigai.area_cd reigai_area_cd, ");
//		↑↑2006.07.12 baozy 暫定対応↑↑
		sb.append("	reigai.siiresaki_cd reigai_siiresaki_cd, ");
		sb.append("	reigai.buturyu_kb reigai_buturyu_kb, ");
		sb.append("	reigai.bin_1_kb reigai_bin_1_kb, ");
		sb.append("	reigai.bin_2_kb reigai_bin_2_kb, ");
		sb.append("	reigai.yusen_bin_kb reigai_yusen_bin_kb, ");
		sb.append("	'' reigai_temp1, ");
		sb.append("	'' reigai_temp2, ");
		sb.append("	'' reigai_temp3, ");
		sb.append("	'' reigai_temp4, ");
		sb.append("	'' reigai_temp5, ");
		sb.append("	'' reigai_temp6, ");
		sb.append("	'' reigai_temp7, ");
		sb.append("	'' reigai_temp8, ");
		sb.append("	'' reigai_temp9, ");
		sb.append("	'' reigai_temp10, ");
		sb.append("	'' reigai_temp11, ");
		sb.append("	'' reigai_temp12, ");
		sb.append("	'' reigai_temp13, ");
		sb.append("	'' reigai_temp14, ");
		sb.append("	'' reigai_temp15, ");
		sb.append("	'' reigai_temp16, ");
		sb.append("	'' reigai_temp17, ");
		sb.append("	'' reigai_temp18, ");
		sb.append("	'' reigai_temp19, ");
		sb.append("	'' reigai_temp20, ");
		sb.append("	'' reigai_temp21, ");
		sb.append("	'' reigai_temp22, ");
		sb.append("	'' reigai_temp23, ");
		sb.append("	'' reigai_temp24, ");
		sb.append("	'' reigai_temp25, ");
		sb.append("	'' reigai_temp26, ");
		sb.append("	'' reigai_temp27, ");
		sb.append("	'' reigai_temp28, ");
		sb.append("	'' reigai_temp29, ");
		sb.append("	'' reigai_temp30, ");
		sb.append("	'' reigai_temp31, ");
		sb.append("	'' reigai_temp32, ");
//		↓↓2006.09.02 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	'' reigai_temp33, ");
		sb.append("	'' reigai_temp34, ");
//		↑↑2006.09.02 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	reigai.syusei_kb reigai_syusei_kb, ");
		sb.append("	reigai.syohin_gyo_no reigai_syohin_gyo_no, ");
		sb.append("	reigai.sakusei_gyo_no reigai_sakusei_gyo_no, ");
		sb.append("	reigai.mst_mainte_fg reigai_mst_mainte_fg, ");
		sb.append("	reigai.alarm_make_fg reigai_alarm_make_fg, ");
		sb.append("	reigai.insert_ts reigai_insert_ts, ");
		sb.append("	reigai.insert_user_id reigai_insert_user_id, ");
		sb.append("	reigai.update_ts reigai_update_ts, ");
		sb.append("	reigai.update_user_id reigai_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_tensyohin_reigai reigai  ");
		sb.append("   where  ");
		sb.append("     reigai.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     reigai.uketsuke_no = '"+ uketsukeNo +"' ");
//		↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" union ");
//		sb.append(" select  ");
//		sb.append(" '04' syo_sheet_syubetsu,  ");
//		sb.append("	syo.torikomi_dt syo_torikomi_dt, ");
//		sb.append("	syo.excel_file_syubetsu syo_excel_file_syubetsu, ");
//		sb.append("	syo.uketsuke_no syo_uketsuke_no, ");
//		sb.append("	syo.uketsuke_seq syo_uketsuke_seq, ");
//		sb.append("	syo.hachuno_cd syo_hachuno_cd, ");
//		sb.append("	syo.bumon_cd syo_bumon_cd, ");
//		sb.append("	syo.syohin_cd syo_syohin_cd, ");
//		sb.append("	syo.theme_cd syo_theme_cd, ");
//		sb.append("	syo.hatyu_dt syo_hatyu_dt, ");
//		sb.append("	syo.nohin_dt syo_nohin_dt, ");
//		sb.append("	syo.hanbai_st_dt syo_hanbai_st_dt, ");
//		sb.append("	syo.hanbai_ed_dt syo_hanbai_ed_dt, ");
//		sb.append("	syo.gentanka_vl syo_gentanka_vl, ");
//		sb.append("	syo.baitanka_vl syo_baitanka_vl, ");
//		sb.append("	syo.hachutani_qt syo_hachutani_qt, ");
//		sb.append("	syo.tenpo_cd syo_tenpo_cd, ");
//		sb.append("	syo.suryo_qt syo_suryo_qt, ");
//		sb.append("	'' syo_temp1, ");
//		sb.append("	'' syo_temp2, ");
//		sb.append("	'' syo_temp3, ");
//		sb.append("	'' syo_temp4, ");
//		sb.append("	'' syo_temp5, ");
//		sb.append("	'' syo_temp6, ");
//		sb.append("	'' syo_temp7, ");
//		sb.append("	'' syo_temp8, ");
//		sb.append("	'' syo_temp9, ");
//		sb.append("	'' syo_temp10, ");
//		sb.append("	'' syo_temp11, ");
//		sb.append("	'' syo_temp12, ");
//		sb.append("	'' syo_temp13, ");
//		sb.append("	'' syo_temp14, ");
//		sb.append("	'' syo_temp15, ");
//		sb.append("	'' syo_temp16, ");
//		sb.append("	'' syo_temp17, ");
//		sb.append("	'' syo_temp18, ");
//		sb.append("	'' syo_temp19, ");
//		sb.append("	'' syo_temp20, ");
//		sb.append("	'' syo_temp21, ");
//		sb.append("	'' syo_temp22, ");
//		sb.append("	'' syo_temp23, ");
//		sb.append("	'' syo_temp24, ");
//		sb.append("	'' syo_temp25, ");
//		sb.append("	'' syo_temp26, ");
//		sb.append("	'' syo_temp27, ");
//		sb.append("	'' syo_temp28, ");
//		sb.append("	'' syo_temp29, ");
//		sb.append("	'' syo_temp30, ");
//		sb.append("	'' syo_temp31, ");
//		sb.append("	'' syo_temp32, ");
//		sb.append("	'' syo_temp33, ");
//		sb.append("	'' syo_temp34, ");
////		sb.append("	'' syo_temp35, ");
//		sb.append("	syo.syusei_kb syo_syusei_kb, ");
//		sb.append("	syo.syohin_gyo_no syo_syohin_gyo_no, ");
//		sb.append("	syo.sakusei_gyo_no syo_sakusei_gyo_no, ");
//		sb.append("	syo.mst_mainte_fg syo_mst_mainte_fg, ");
//		sb.append("	syo.alarm_make_fg syo_alarm_make_fg, ");
//		sb.append("	syo.insert_ts syo_insert_ts, ");
//		sb.append("	syo.insert_user_id syo_insert_user_id, ");
//		sb.append("	syo.update_ts syo_update_ts, ");
//		sb.append("	syo.update_user_id syo_update_user_id ");
//		sb.append(" from ");
//		sb.append("     tr_syokaidonyu syo  ");
//		sb.append("   where  ");
//		sb.append("     syo.torikomi_dt = '"+ torikomiDt +"' ");
//		sb.append("     and  ");
//		sb.append("     syo.uketsuke_no = '"+ uketsukeNo +"' ");
//		↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

//	↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓(未使用の為コメント化)
//	/**
//	 * ４商品マスタTRの検索用ＳＱＬの生成を行う。
//	 *
//	 * @param  strTorikomiDt String
//	 * @param  strUketsukeNo String
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getSelectKekkaUnionSql(String strTorikomiDt, String strUketsukeNo)
//    {
//            DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//            StringBuffer sb = new StringBuffer();
//
//            // 商品マスタ(TAG)
//            sb.append("select");
////			↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    main.insert_user_id as insert_user_id,"); //登録者
////			↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
//            sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //excelファイル種別
//            sb.append("    main.uketsuke_no as uketsuke_no,"); //受付ファイル№
//            sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付seq №
//            sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
//            sb.append("    main.by_no as by_no,"); //バイヤーNO
//
//            sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
//            sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
//            sb.append("    main.unit_cd as unit_cd,"); //ユニットコード
//            sb.append("    main.haifu_cd as haifu_cd,"); //配布コード
//            sb.append("    main.subclass_cd as subclass_cd,"); //サブクラスコード
//            sb.append("    main.yuko_dt as yuko_dt,"); //有効日
//            sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原価単価
//            sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売価単価
//// ↓↓2006.07.12 Jiangb 暫定対応↓↓
////            sb.append("    main.area_cd as area_cd,"); //地区コード
//// ↑↑2006.07.12 Jiangb 暫定対応↑↑
//            sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
//            sb.append("    main.bland_cd as bland_cd,"); //ブランドコード
//            sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
//            // ===BEGIN=== Modify by kou 2006/7/28
//            // 販売終了日が空白の場合、99999999をセット
//            //sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
//			sb.append("    CASE WHEN main.hanbai_ed_dt is null THEN '99999999'"); //販売終了日
//			sb.append("         ELSE  main.hanbai_ed_dt END hanbai_ed_dt,"); //販売終了日
//            // ===END=== Modify by kou 2006/7/28
//            sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
//            sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
//            sb.append("    main.eos_kb as eos_kb,"); //eos区分
//            sb.append("    main.pb_kb as pb_kb,"); //pb区分
//            sb.append("    main.fuji_syohin_kb as fuji_syohin_kb,"); //fuji商品区分
//
//		    sb.append("    main.hinmei_kanji_na as hinmei_kanji_na,"); //漢字品名
//		    sb.append("    main.hinmei_kana_na as hinmei_kana_na,"); //カナ品名
//		    sb.append("    '' as maker_cd,"); //janメーカーコード
//		    sb.append("    '' as pos_cd,"); //posコード
//		    sb.append("    main.size_cd as size_cd,"); //サイズコード
//		    sb.append("    main.color_cd as color_cd,"); //カラーコード
//		    sb.append("    main.keshi_baika_vl as keshi_baika_vl,"); //消札売価
//		    sb.append("    main.tokubetu_genka_vl as tokubetu_genka_vl,"); //特別原価
//		    sb.append("    main.keiyakusu_qt as keiyakusu_qt,"); //契約数量
//		    sb.append("    main.tuika_keiyakusu_qt as tuika_keiyakusu_qt,"); //追加契約数量
//		    sb.append("    '' as plu_send_dt,"); //plu送信日
//		    sb.append("    '' as ten_siiresaki_kanri_cd,"); //店別仕入先管理コード
//		    sb.append("    '' as honbu_zai_kb,"); //本部在庫区分
//		    sb.append("    '' as hachu_tani_na,"); //発注単位呼称
//		    sb.append("    '' as hachutani_irisu_qt,"); //発注単位(入数)
//		    sb.append("    '' as max_hachutani_qt,"); //最大発注単位
//		    sb.append("    '' as maker_kibo_kakaku_vl,"); //メーカー希望小売価格（税込）
//		    sb.append("    main.siire_hinban_cd as siire_hinban_cd,"); //仕入先品番
//		    sb.append("    main.season_cd as season_cd,"); //シーズンコード
//		    sb.append("    main.yoridori_vl as yoridori_vl,"); //よりどり価格
//		    sb.append("    main.yoridori_qt as yoridori_qt,"); //よりどり販売数量
//		    sb.append("    main.yoridori_kb as yoridori_kb,"); //よりどり種類
//		    sb.append("    main.nefuda_kb as nefuda_kb,"); //tag値札区分
//		    sb.append("    main.nefuda_ukewatasi_dt as nefuda_ukewatasi_dt,"); //tag値札受渡日
//		    sb.append("    main.nefuda_ukewatasi_kb as nefuda_ukewatasi_kb,"); //tag値札受渡方法
//		    sb.append("    main.sozai_cd as sozai_cd,"); //素材コード
//		    sb.append("    main.oriami_cd as oriami_cd,"); //織り編みコード
//		    sb.append("    main.sode_cd as sode_cd,"); //袖丈コード
//		    sb.append("    main.age_cd as age_cd,"); //年代コード
//		    sb.append("    main.pattern_cd as pattern_cd,"); //パターンコード
//		    sb.append("    '' as bin_1_kb,"); //①便区分
//		    sb.append("    '' as bin_2_kb,"); //②便区分
//		    sb.append("    '' as yusen_bin_kb,"); //優先便区分
//		    sb.append("    '' as pc_kb,"); //pc発行区分
//		    sb.append("    '' as daisi_no_nb,"); //台紙no
//		    sb.append("    '' as daicho_tenpo_kb,");//商品台帳（店舗）
//		    sb.append("    '' as daicho_honbu_kb,");//商品台帳（本部）
//		    sb.append("    '' as daicho_siiresaki_kb,");//商品台帳（仕入先）
//		    sb.append("    '' as syuzei_hokoku_kb,");//酒税報告区分
//		    sb.append("    '' as syohi_kigen_dt,");//消費期限
//		    sb.append("    '' as unit_price_tani_kb,");//ユニットプライス-単位区分
//		    sb.append("    '' as unit_price_naiyoryo_qt,");//ユニットプライス-内容量
//		    sb.append("    '' as unit_price_kijun_tani_qt,");//ユニットプライス-基準単位量
//
//		    sb.append("    bumon_ck.code_cd as bumon_ck,"); //部門コード存在チェック
//		    sb.append("    main.s_syohin_cd as s_syohin_cd, "); //商品コード存在チェック
//		    sb.append("    unit_cd.code_cd as unit_ck,"); //ユニットコード存在チェック
//		    sb.append("    rst.unit_cd as rst_unit_ck,"); //商品マスタに部門コードの整合性用ユニットコード存在チェック
//		    sb.append("    subclass_cd.subclass_cd as sub_class_ck,"); //サブクラスコード存在チェック
////  ↓↓2006.07.12 Jiangb 暫定対応↓↓
////    		sb.append("    area_cd.code_cd as area_ck,"); //地区コード存在チェック
////  ↑↑2006.07.12 Jiangb 暫定対応↑↑
//		    sb.append("    siiresaki_cd.siiresaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
//		    sb.append("    bland_cd.code_cd as bland_ck,"); //ブランドコード存在チェック
//		    sb.append("    pb_kb.code_cd as pb_ck,"); //PB区分存在チェック
//		    sb.append("    fuji_syohin_kb.code_cd as fuji_syohin_ck,"); //FUJI商品マスタ存在チェック
//
//		    sb.append("    '' as maker_ck,"); //JANメーカーコード存在チェック
//		    sb.append("    '' as syohin_2_cd,"); // 商品コード２存在チェック
//		    sb.append("    size_cd.code_cd as size_ck,"); //サイズコード存在チェック
//		    sb.append("    color_cd.code_cd as color_ck,"); //カラーコード存在チェック
//		    sb.append("    season_cd.code_cd as season_ck,"); //シーズンコード存在チェック
//		    sb.append("    yoridori_kb.code_cd as yoridori_ck,"); //よりどり種類存在チェック
//		    sb.append("    nefuda_kb.code_cd as nefuda_ck,"); //TAG値札区分存在チェック
//		    sb.append("    nefuda_ukewatasi_kb.code_cd as nefuda_ukewatasi_ck,"); //TAG値札受渡方法存在チェック
//		    sb.append("    sozai_cd.code_cd as sozai_ck,"); //素材コード存在チェック
//		    sb.append("    oriami_cd.code_cd as oriami_ck,"); //織り編みコード存在チェック
//		    sb.append("    sode_cd.code_cd as sode_ck,"); //袖丈コード存在チェック
//		    sb.append("    age_cd.code_cd as age_ck,"); //年代コード存在チェック
//		    sb.append("    pattern_cd.code_cd as pattern_ck,"); //パターンコード存在チェック
//		    sb.append("    '' as pc_ck,"); //PC発行区分
//		    sb.append("    '' as daisi_no_nb_ck,"); //プライスシール種類(台紙NO)存在チェック
//			sb.append("    '' as honbu_zai_ck,");	// 本部在庫区分
//		    sb.append("    '' as syuzei_hokoku_ck, "); //酒税報告区分存在チェック
//		    sb.append("    '' as unit_price_tani_ck, "); //ユニットプライス-単位区分存在チェック
//		    sb.append("    '' as daicho_tenpo_ck, ");// 台帳店舗
//		    sb.append("    '' as daicho_honbu_ck, ");// 台帳本部
//		    sb.append("    '' as daicho_siiresaki_ck, ");// 台帳仕入先
//		    sb.append("    '' as ten_siiresaki_kanri_ck "); //店仕入先コード
//            sb.append(" from");
//            sb.append("   (select tsa.*, rs.syohin_cd as s_syohin_cd");
//            sb.append("    from");
//            sb.append("    (select");
//            sb.append("        tr.*,");
//            sb.append("        tts.by_no");
//            sb.append("    from");
//            sb.append("        tr_toroku_syonin tts left join tr_syohin_a08 tr ");
//            sb.append("        on ");
//            sb.append("        tts.torikomi_dt = tr.torikomi_dt and");
//            sb.append("        tts.excel_file_syubetsu = tr.excel_file_syubetsu and");
//            sb.append("        tts.uketsuke_no = tr.uketsuke_no and ");
//            sb.append("        tr.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"' and");
//            sb.append("        tr.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//            sb.append("        ) tsa left join ");
//            sb.append("        (select");
//            sb.append("            rs.*");
//            sb.append("         from");
//            sb.append("            r_syohin rs,");
//            sb.append("            (select");
//            sb.append("                tr.*");
//            sb.append("            from");
//            sb.append("              tr_toroku_syonin tts left join tr_syohin_a08 tr ");
//            sb.append("              on ");
//            sb.append("              tts.torikomi_dt = tr.torikomi_dt and");
//            sb.append("              tts.excel_file_syubetsu = tr.excel_file_syubetsu and");
//            sb.append("              tts.uketsuke_no = tr.uketsuke_no");
//            sb.append("            ) tsa");
//            sb.append("         where");
//            sb.append("            tsa.bumon_cd = rs.bumon_cd and");
//            sb.append("            tsa.syohin_cd = rs.syohin_cd and");
//            sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.yuko_dt)");
//            sb.append("        ) rs");
//            sb.append("    on");
//            sb.append("        tsa.bumon_cd = rs.bumon_cd and");
//            sb.append("        tsa.syohin_cd = rs.syohin_cd");
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_syohin_taikei rst on rst.system_kb = '2' and rst.unit_cd = tsa.unit_cd");
//            sb.append("    left join r_syohin_taikei rst on rst.system_kb = '" + mst000611_SystemKbDictionary.T.getCode() + "' and rst.unit_cd = tsa.unit_cd");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    ) main");
//            sb.append("    left join r_namectf bumon_ck on bumon_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SECTION).append("' and bumon_ck.code_cd = main.bumon_cd and bumon_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf size_cd on size_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.SIZE).append("' and size_cd.code_cd = main.size_cd and size_cd.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf color_cd on color_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.COLOR).append("' and color_cd.code_cd = main.color_cd and color_cd.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf season_cd on season_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.SEASON).append("' and season_cd.code_cd = main.season_cd and season_cd.delete_fg = '").append(DEL_FG).append("'");
////		↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf yoridori_kb on yoridori_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.VARIOUS_KINDS).append("' and yoridori_kb.code_cd = main.yoridori_kb and yoridori_kb.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf yoridori_kb on yoridori_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.VARIOUS_KINDS).append("' and yoridori_kb.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.yoridori_kb and yoridori_kb.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf nefuda_kb on nefuda_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.PRICE_TAG_DIVISION).append("' and nefuda_kb.code_cd = main.nefuda_kb and nefuda_kb.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf nefuda_kb on nefuda_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.PRICE_TAG_DIVISION).append("' and nefuda_kb.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.nefuda_kb and nefuda_kb.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf nefuda_ukewatasi_kb on nefuda_ukewatasi_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.RECEIVE_OF_TAG).append("' and nefuda_ukewatasi_kb.code_cd = main.nefuda_ukewatasi_kb and nefuda_ukewatasi_kb.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf nefuda_ukewatasi_kb on nefuda_ukewatasi_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.RECEIVE_OF_TAG).append("' and nefuda_ukewatasi_kb.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.nefuda_ukewatasi_kb and nefuda_ukewatasi_kb.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_namectf sozai_cd on sozai_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.MATERIAL).append("' and sozai_cd.code_cd = main.sozai_cd and sozai_cd.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf oriami_cd on oriami_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.WEAVING_KNITTING).append("' and oriami_cd.code_cd = main.oriami_cd and oriami_cd.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf sode_cd on sode_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.LENGTH_OF_SLEEVE).append("' and sode_cd.code_cd = main.sode_cd and sode_cd.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf age_cd on age_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.AGE).append("' and age_cd.code_cd = main.age_cd and age_cd.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf pattern_cd on pattern_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.HANDLE_PATTERN).append("' and pattern_cd.code_cd = main.pattern_cd and pattern_cd.delete_fg = '").append(DEL_FG).append("'");
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_syohin_taikei rst on rst.system_kb = '2' and rst.unit_cd = main.unit_cd ");
////			sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd  ");
//        	sb.append("    left join r_syohin_taikei rst on rst.system_kb = '" + mst000611_SystemKbDictionary.T.getCode() + "' and rst.unit_cd = main.unit_cd ");
//        	sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000611_SystemKbDictionary.T.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd  ");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
////          sb.append("    left join r_namectf unit_cd on unit_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_cd.code_cd = main.unit_cd and unit_cd.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf unit_cd on unit_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_cd.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.unit_cd and unit_cd.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_subclass subclass_cd on subclass_cd.bumon_cd = main.bumon_cd and subclass_cd.subclass_cd = main.subclass_cd and subclass_cd.delete_fg = '").append(DEL_FG).append("'");
//
//            // ↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////          sb.append("    left join r_namectf area_cd on area_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_cd.code_cd = main.area_cd and area_cd.delete_fg = '").append(DEL_FG).append("'");
////          sb.append("    left join r_siiresaki siiresaki_cd on siiresaki_cd.siiresaki_cd = main.siiresaki_cd and siiresaki_cd.kanri_cd = main.bumon_cd and siiresaki_cd.kanri_kb = '1' and siiresaki_cd.area_cd = main.area_cd ");
//            sb.append("    left join r_siiresaki siiresaki_cd on siiresaki_cd.siiresaki_cd = main.siiresaki_cd and siiresaki_cd.kanri_cd = '0000' and siiresaki_cd.kanri_kb = '1' ");
////          ↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//
//            sb.append("    left join r_namectf bland_cd on bland_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_cd.code_cd = main.bland_cd and bland_cd.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf pb_kb on pb_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.PB_DIVISION).append("' and pb_kb.code_cd = main.pb_kb and pb_kb.delete_fg = '").append(DEL_FG).append("'");
//// ↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf fuji_syohin_kb on fuji_syohin_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_kb.code_cd = main.fuji_syohin_kb and fuji_syohin_kb.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf fuji_syohin_kb on fuji_syohin_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_kb.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.fuji_syohin_kb and fuji_syohin_kb.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//            sb.append(" where ");
//            sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
//            sb.append("  and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//            sb.append(" union ");
//            // 商品マスタTR（実用）
//            sb.append("select");
////			↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    main.insert_user_id as insert_user_id,"); //登録者
////			↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
//            sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //excelファイル種別
//            sb.append("    main.uketsuke_no as uketsuke_no,"); //受付no
//            sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付seqno
//            sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
//            sb.append("    main.by_no as by_no,");//バイヤーno
//
//            sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
//            sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
//            sb.append("    main.unit_cd as unit_cd,"); //ユニットコード
//            sb.append("    main.haifu_cd as haifu_cd,"); //配布コード
//            sb.append("    main.subclass_cd as subclass_cd,"); //サブクラスコード
//            sb.append("    main.yuko_dt as yuko_dt,"); //有効日
//            sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原価単価
//            sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売価単価
//// ↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////            sb.append("    main.area_cd as area_cd,"); //地区コード
//// ↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//            sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
//            sb.append("    main.bland_cd as bland_cd,"); //ブランドコード
//            sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
//            sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
//            sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
//            sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
//            sb.append("    main.eos_kb as eos_kb,"); //eos区分
//            sb.append("    main.pb_kb as pb_kb,"); //pb区分
//            sb.append("    main.fuji_syohin_kb as fuji_syohin_kb,"); //fuji商品区分
//
//		    sb.append("    main.hinmei_kanji_na as hinmei_kanji_na,"); //漢字品名
//		    sb.append("    main.hinmei_kana_na as hinmei_kana_na,"); //カナ品名
//		    sb.append("    main.maker_cd as maker_cd,"); //janメーカーコード
//		    sb.append("    main.pos_cd as pos_cd,"); //posコード
//		    sb.append("    main.size_cd as size_cd,"); //サイズコード
//		    sb.append("    main.color_cd as color_cd,"); //カラーコード
//		    sb.append("    main.keshi_baika_vl as keshi_baika_vl,"); //消札売価
//		    sb.append("    '' as tokubetu_genka_vl,"); //特別原価
//		    sb.append("    main.keiyakusu_qt as keiyakusu_qt,"); //契約数量
//		    sb.append("    main.tuika_keiyakusu_qt as tuika_keiyakusu_qt,"); //追加契約数量
//		    sb.append("    main.plu_send_dt as plu_send_dt,"); //plu送信日
//		    sb.append("    '' as ten_siiresaki_kanri_cd,"); //店別仕入先管理コード
//		    sb.append("    '' as honbu_zai_kb,"); //本部在庫区分
//		    sb.append("    main.hachu_tani_na as hachu_tani_na,"); //発注単位呼称
//		    sb.append("    '' as hachutani_irisu_qt,"); //発注単位(入数)
//		    sb.append("    '' as max_hachutani_qt,"); //最大発注単位
//		    sb.append("    '' as maker_kibo_kakaku_vl,"); //メーカー希望小売価格（税込）
//		    sb.append("    main.siire_hinban_cd as siire_hinban_cd,"); //仕入先品番
//		    sb.append("    main.season_cd as season_cd,"); //シーズンコード
//		    sb.append("    main.yoridori_vl as yoridori_vl,"); //よりどり価格
//		    sb.append("    main.yoridori_qt as yoridori_qt,"); //よりどり販売数量
//		    sb.append("    main.yoridori_kb as yoridori_kb,"); //よりどり種類
//		    sb.append("    main.nefuda_kb as nefuda_kb,"); //pos値札区分
//		    sb.append("    main.nefuda_ukewatasi_dt as nefuda_ukewatasi_dt,"); //pos値札受渡日
//		    sb.append("    main.nefuda_ukewatasi_kb as nefuda_ukewatasi_kb,"); //pos値札受渡方法
//		    sb.append("    '' as sozai_cd,"); //素材コード
//		    sb.append("    '' as oriami_cd,"); //織り編みコード
//		    sb.append("    '' as sode_cd,"); //袖丈コード
//		    sb.append("    '' as age_cd,"); //年代コード
//		    sb.append("    '' as pattern_cd,"); //パターンコード
//		    sb.append("    '' as bin_1_kb,"); //①便区分
//		    sb.append("    '' as bin_2_kb,"); //②便区分
//		    sb.append("    '' as yusen_bin_kb,"); //優先便区分
//		    sb.append("    main.pc_kb as pc_kb,"); //pc発行区分
//		    sb.append("    main.daisi_no_nb as daisi_no_nb,"); //台紙no
//		    sb.append("    '' as daicho_tenpo_kb,");//商品台帳（店舗）
//		    sb.append("    '' as daicho_honbu_kb,");//商品台帳（本部）
//		    sb.append("    '' as daicho_siiresaki_kb,");//商品台帳（仕入先）
//		    sb.append("    '' as syuzei_hokoku_kb,");//酒税報告区分
//		    sb.append("    '' as syohi_kigen_dt,");//消費期限
//		    sb.append("    '' as unit_price_tani_kb,");//ユニットプライス-単位区分
//		    sb.append("    '' as unit_price_naiyoryo_qt,");//ユニットプライス-内容量
//		    sb.append("    '' as unit_price_kijun_tani_qt,");//ユニットプライス-基準単位量
//
//		    sb.append("    bumon_ck.code_cd as bumon_ck,"); //部門コード存在チェック
//		    sb.append("    main.s_syohin_cd  as s_syohin_cd,"); //商品コード存在チェック
//		    sb.append("    unit_ck.code_cd as unit_ck,"); //ユニットコード存在チェック
//		    sb.append("    rst1.unit_cd as rst_unit_ck,"); //商品マスタに部門コードの整合性用ユニットコード存在チェック
//		    sb.append("    sub_class_ck.subclass_cd as sub_class_ck,"); //サブクラスコード存在チェック
//
////		    sb.append("    area_ck.code_cd as area_ck,"); //地区コード存在チェック
//
//		    sb.append("    siiresaki_ck.siiresaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
//		    sb.append("    bland_ck.code_cd bland_ck,");// ブランドコード存在チェック
//		    sb.append("    pb_ck.code_cd as pb_ck,"); //PB区分存在チェック
//		    sb.append("    fuji_syohin_ck.code_cd as fuji_syohin_ck,");// FUJI商品マスタ存在チェック
//
//		    sb.append("    maker_ck.code_cd as maker_ck,"); //JANメーカーコード存在チェック
//		    sb.append("    main.syohin_2_cd as syohin_2_cd,"); // 商品コード２存在チェック
//		    sb.append("    size_ck.code_cd as size_ck,"); //サイズコード存在チェック
//		    sb.append("    color_ck.code_cd as color_ck,"); //カラーコード存在チェック
//		    sb.append("    season_ck.code_cd as season_ck,"); //シーズンコード存在チェック
//		    sb.append("    yoridori_ck.code_cd as yoridori_ck,"); //よりどり種類存在チェック
//		    sb.append("    nefuda_ck.code_cd as nefuda_ck,"); //POS値札区分存在チェック
//		    sb.append("    nefuda_ukewatasi_ck.code_cd as nefuda_ukewatasi_ck,"); //POS値札受渡方法存在チェック
//		    sb.append("    '' as sozai_ck,"); //素材コード存在チェック
//		    sb.append("    '' as oriami_ck,"); //織り編みコード存在チェック
//		    sb.append("    '' as sode_ck,"); //袖丈コード存在チェック
//		    sb.append("    '' as age_ck,"); //年代コード存在チェック
//		    sb.append("    '' as pattern_ck,"); //パターンコード存在チェック
//		    sb.append("    pc_kb_ck.code_cd as pc_kb,"); //PC発行区分
//		    sb.append("    daisi_no_nb_ck.code_cd as daisi_no_nb_ck,"); //プライスシール種類(台紙NO)存在チェック
//			sb.append("    '' as honbu_zai_ck,");	// 本部在庫区分
//		    sb.append("    '' as syuzei_hokoku_ck, "); //酒税報告区分存在チェック
//		    sb.append("    '' as unit_price_tani_ck, "); //ユニットプライス-単位区分存在チェック
//		    sb.append("    '' as daicho_tenpo_ck, ");// 台帳店舗
//		    sb.append("    '' as daicho_honbu_ck, ");// 台帳本部
//		    sb.append("    '' as daicho_siiresaki_ck, ");// 台帳仕入先
//		    sb.append("    '' as ten_siiresaki_kanri_ck "); //店仕入先コード
//            sb.append("   from ( select tsa.*,rs.syohin_cd as s_syohin_cd,rs.syohin_2_cd  ");
//            sb.append("    from");
//            sb.append("        (select");
//            sb.append("            tr.* ");
//            sb.append("           ");
//            sb.append("        from");
//            sb.append("            (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts left join tr_syohin_a07 tr1");
//            sb.append("   on tr1.torikomi_dt = tts.torikomi_dt ");
//            sb.append("   and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
//            sb.append("   and  tr1.uketsuke_no=tts.uketsuke_no ");
//            sb.append(" ) tr  ");
//            sb.append("    ");
//            sb.append("                ");
//            sb.append("        ) tsa left join ");
//            sb.append("        (select");
//            sb.append("            rs.*");
//            sb.append("         from");
//            sb.append("            r_syohin rs,");
//            sb.append("            (select");
//            sb.append("                tr.* ");
//            sb.append("                ");
//            sb.append("            from");
//            sb.append("                (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts left join tr_syohin_a07 tr1 ");
//            sb.append("   on tr1.torikomi_dt = tts.torikomi_dt ");
//            sb.append("   and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
//            sb.append("   and  tr1.uketsuke_no=tts.uketsuke_no ");
//            sb.append("   and  tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
//            sb.append("   and  tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//            sb.append("             ) tr ");
//            sb.append("            ) tsa");
//            sb.append("         where");
//            sb.append("            tsa.bumon_cd = rs.bumon_cd and");
//            sb.append("            tsa.syohin_cd = rs.syohin_cd and");
//            sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.yuko_dt and delete_fg='0')");
//            sb.append("        ) rs on tsa.bumon_cd = rs.bumon_cd and tsa.syohin_cd = rs.syohin_cd ");
//            sb.append("    ) main  ");
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000901_KanriKbDictionary.HINBAN.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//		sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.J.getCode() + "' and rst.unit_cd = main.unit_cd  ");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//			sb.append("    left join r_namectf maker_ck on maker_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.JAN_MAKER_NAME).append("' and maker_ck.code_cd = main.maker_cd and maker_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf size_ck on size_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SIZE).append("' and size_ck.code_cd = main.size_cd and size_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf color_ck on color_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.COLOR).append("' and color_ck.code_cd = main.color_cd and color_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf season_ck on season_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SEASON).append("' and season_ck.code_cd = main.season_cd and season_ck.delete_fg = '").append(DEL_FG).append("'");
////		↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf yoridori_ck on yoridori_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.VARIOUS_KINDS).append("' and yoridori_ck.code_cd = yoridori_kb and yoridori_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf yoridori_ck on yoridori_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.VARIOUS_KINDS).append("' and yoridori_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || yoridori_kb and yoridori_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf nefuda_ck on nefuda_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PRICE_TAG_DIVISION).append("' and nefuda_ck.code_cd = main.nefuda_kb and nefuda_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf nefuda_ck on nefuda_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PRICE_TAG_DIVISION).append("' and nefuda_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || main.nefuda_kb and nefuda_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
////          sb.append("    left join r_namectf nefuda_ukewatasi_ck on nefuda_ukewatasi_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.RECEIVE_OF_TAG).append("' and nefuda_ukewatasi_ck.code_cd = 'nefuda_ukewatasi_kb").append(DEL_FG).append("'");
////		↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf nefuda_ukewatasi_ck on nefuda_ukewatasi_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.RECEIVE_OF_TAG).append("' and nefuda_ukewatasi_ck.code_cd = main.nefuda_ukewatasi_kb and nefuda_ukewatasi_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf nefuda_ukewatasi_ck on nefuda_ukewatasi_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.RECEIVE_OF_TAG).append("' and nefuda_ukewatasi_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || main.nefuda_ukewatasi_kb and nefuda_ukewatasi_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
////		↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
////          sb.append("    left join r_namectf pc_kb_ck on pc_kb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PC_ISSUE_DIVISION).append("' and pc_kb_ck.code_cd = main.pc_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf pc_kb_ck on pc_kb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PC_ISSUE_DIVISION).append("' and pc_kb_ck.code_cd = '").append(mst000611_SystemKbDictionary.J.getCode()).append("'||main.pc_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_namectf daisi_no_nb_ck on daisi_no_nb_ck.syubetu_no_cd = '").append("1090").append("' and daisi_no_nb_ck.code_cd = main.daisi_no_nb and daisi_no_nb_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf bumon_ck on bumon_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SECTION).append("' and bumon_ck.code_cd = main.bumon_cd and bumon_ck.delete_fg = '").append(DEL_FG).append("'");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("    left join r_syohin_taikei rst1 on rst.system_kb =  '" + mst000901_KanriKbDictionary.UNIT.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//		sb.append("    left join r_syohin_taikei rst1 on rst1.system_kb =  '" + mst000611_SystemKbDictionary.J.getCode() + "' and rst1.unit_cd = main.unit_cd  ");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_subclass sub_class_ck on sub_class_ck.bumon_cd = main.bumon_cd and sub_class_ck.subclass_cd = main.subclass_cd and sub_class_ck.delete_fg = '").append(DEL_FG).append("'");
//
//// ↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////            sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = main.bumon_cd and siiresaki_ck.area_cd = main.area_cd and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = '0000'  and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
////          ↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//            sb.append("    left join r_namectf syouhin_ck on syouhin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and syouhin_ck.code_cd = main.fuji_syohin_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf pb_ck on pb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PB_DIVISION).append("' and pb_ck.code_cd = main.pb_kb and pb_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf bland_ck on bland_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_ck.code_cd = main.bland_cd and bland_ck.delete_fg = '").append(DEL_FG).append("'");
//
//// ↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////            sb.append("    left join r_namectf area_ck on area_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_ck.code_cd = main.area_cd and area_ck.delete_fg = '").append(DEL_FG).append("'");
//// ↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//
//            //		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd  ");
//		sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000611_SystemKbDictionary.J.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd  ");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("	   left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
//		sb.append("    left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//
//            sb.append(" where ");
//            sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
//            sb.append("  and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//
//            sb.append(" union ");
//            // 商品マスタ（グロ・バラ）
//            sb.append(" select");
////			↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    main.insert_user_id as insert_user_id,"); //登録者
////			↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
//            sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //excelファイル種別
//            sb.append("    main.uketsuke_no as uketsuke_no,"); //受付no
//            sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付seqno
//            sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
//            sb.append("    main.by_no as by_no, ");//バイヤーno
//
//            sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
//            sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
//            sb.append("    main.unit_cd as unit_cd,"); //ユニットコード
//            sb.append("    main.haifu_cd as haifu_cd,"); //配布コード
//            sb.append("    main.subclass_cd as subclass_cd,"); //サブクラスコード
//            sb.append("    main.yuko_dt as yuko_dt,"); //有効日
//            sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原価単価
//            sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売価単価
//
////            sb.append("    main.area_cd as area_cd,"); //地区コード
//
//            sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
//            sb.append("    main.bland_cd as bland_cd,"); //ブランドコード
//            sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
//            sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
//            sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
//            sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
//            sb.append("    main.eos_kb as eos_kb,"); //eos区分
//            sb.append("    main.pb_kb as pb_kb,"); //pb区分
//            sb.append("    main.fuji_syohin_kb as fuji_syohin_kb,"); //fuji商品区分
//
//            sb.append("    main.hinmei_kanji_na as hinmei_kanji_na,"); //漢字品名
//		    sb.append("    main.hinmei_kana_na as hinmei_kana_na,"); //カナ品名
//		    sb.append("    main.maker_cd as maker_cd,"); //janメーカーコード
//		    sb.append("    '' as pos_cd,"); //posコード
//		    sb.append("    '' as size_cd,"); //サイズコード
//		    sb.append("    '' as color_cd,"); //カラーコード
//		    sb.append("    '' as keshi_baika_vl,"); //消札売価
//		    sb.append("    '' as tokubetu_genka_vl,"); //特別原価
//		    sb.append("    '' as keiyakusu_qt,"); //契約数量
//		    sb.append("    '' as tuika_keiyakusu_qt,"); //追加契約数量
//		    sb.append("    main.plu_send_dt as plu_send_dt,"); //plu送信日
//		    sb.append("    main.ten_siiresaki_kanri_cd as ten_siiresaki_kanri_cd,"); //店別仕入先管理コード
//		    sb.append("    main.honbu_zai_kb as honbu_zai_kb,"); //本部在庫区分
//		    sb.append("    main.hachu_tani_na as hachu_tani_na,");  //発注単位呼称
//		    sb.append("    main.hachutani_irisu_qt as hachutani_irisu_qt,"); //発注単位(入数)
//		    sb.append("    main.max_hachutani_qt as max_hachutani_qt,"); //最大発注単位
//		    sb.append("    main.maker_kibo_kakaku_vl as maker_kibo_kakaku_vl,"); //メーカー希望小売価格（税込）
//		    sb.append("    main.siire_hinban_cd as siire_hinban_cd,"); //仕入先品番
//		    sb.append("    '' as season_cd,"); //シーズンコード
//		    sb.append("    '' as yoridori_vl,"); //よりどり価格
//		    sb.append("    '' as yoridori_qt,"); //よりどり販売数量
//		    sb.append("    '' as yoridori_kb,"); //よりどり種類
//		    sb.append("    '' as nefuda_kb,"); //tag値札区分
//		    sb.append("    '' as nefuda_ukewatasi_dt,"); //tag値札受渡日
//		    sb.append("    '' as nefuda_ukewatasi_kb,"); //tag値札受渡方法
//		    sb.append("    '' as sozai_cd,"); //素材コード
//		    sb.append("    '' as oriami_cd,"); //織り編みコード
//		    sb.append("    '' as sode_cd,"); //袖丈コード
//		    sb.append("    '' as age_cd,"); //年代コード
//		    sb.append("    '' as pattern_cd,"); //パターンコード
//		    sb.append("    '' as bin_1_kb,"); //①便区分
//		    sb.append("    '' as bin_2_kb,"); //②便区分
//		    sb.append("    '' as yusen_bin_kb,"); //優先便区分
//		    sb.append("    main.pc_kb as pc_kb,"); //pc発行区分
//		    sb.append("    main.daisi_no_nb as daisi_no_nb,"); //台紙no
//		    sb.append("    main.daicho_tenpo_kb,");//商品台帳（店舗）
//		    sb.append("    main.daicho_honbu_kb,");//商品台帳（本部）
//		    sb.append("    main.daicho_siiresaki_kb,");//商品台帳（仕入先）
//		    sb.append("    main.syuzei_hokoku_kb,");//酒税報告区分
//		    sb.append("    main.syohi_kigen_dt,");//消費期限
//		    sb.append("    main.unit_price_tani_kb,");//ユニットプライス-単位区分
//		    sb.append("    main.unit_price_naiyoryo_qt,");//ユニットプライス-内容量
//		    sb.append("    main.unit_price_kijun_tani_qt,");//ユニットプライス-基準単位量
//
//		    sb.append("    bumon_ck.code_cd as bumon_ck, ");// 部門コード存在チェック
//		    sb.append("    main.s_syohin_cd  as s_syohin_cd, "); // 商品コード存在チェック
//		    sb.append("    unit_ck.code_cd as unit_ck, "); // ユニットコード存在チェック
//		    sb.append("    rst.unit_cd as rst_unit_ck, "); // ユニットコード商品体系マスタ存在チェック
//		    sb.append("    sub_class_ck.subclass_cd as sub_class_ck, ");// サブクラスコード存在チェック
//
////		    sb.append("    area_ck.code_cd as area_ck, "); // 地区コード存在チェック
//
//		    sb.append("    siiresaki_ck.siiresaki_cd as siiresaki_ck, "); // 仕入先コード存在チェック
//		    sb.append("    bland_ck.code_cd as bland_ck, "); // ブラントコード存在チェック
//		    sb.append("    pb_kb_ck.code_cd as pb_ck, "); // PB区分存在チェック
//		    sb.append("    fuji_syohin_ck.code_cd fuji_syohin_ck, ");// FUJI商品マスタ(存在チェック用)
//
//		    sb.append("    maker_ck.code_cd as maker_ck, "); //JANメーカーコード存在チェック
//		    sb.append("    '' as syohin_2_cd,"); // 商品コード２存在チェック
//		    sb.append("    '' as size_ck,"); //サイズコード存在チェック
//		    sb.append("    '' as color_ck,"); //カラーコード存在チェック
//		    sb.append("    '' as season_ck,"); //シーズンコード存在チェック
//		    sb.append("    '' as yoridori_ck,"); //よりどり種類存在チェック
//		    sb.append("    '' as nefuda_ck,"); //TAG値札区分存在チェック
//		    sb.append("    '' as nefuda_ukewatasi_ck,"); //TAG値札受渡方法存在チェック
//		    sb.append("    '' as sozai_ck,"); //素材コード存在チェック
//		    sb.append("    '' as oriami_ck,"); //織り編みコード存在チェック
//		    sb.append("    '' as sode_ck,"); //袖丈コード存在チェック
//		    sb.append("    '' as age_ck,"); //年代コード存在チェック
//		    sb.append("    '' as pattern_ck,"); //パターンコード存在チェック
//		    sb.append("    pc_kb_ck.code_cd as pc_ck, "); //PC発行区分存在チェック
//		    sb.append("    daisi_no_nb_ck.code_cd as daisi_no_nb_ck, "); //プライスシール種類(台紙NO)存在チェック
//			sb.append("    honbu_zai_ck.code_cd as honbu_zai_ck,");	// 本部在庫区分
//		    sb.append("    syuzei_hokoku_ck.code_cd as syuzei_hokoku_ck, "); //酒税報告区分存在チェック
//		    sb.append("    unit_price_tani_ck.code_cd as unit_price_tani_ck, "); //ユニットプライス-単位区分存在チェック
//		    sb.append("    daicho_tenpo_ck.code_cd as daicho_tenpo_ck, ");// 台帳店舗
//		    sb.append("    daicho_honbu_ck.code_cd as daicho_honbu_ck, ");// 台帳本部
//		    sb.append("    daicho_siiresaki_ck.code_cd as daicho_siiresaki_ck, ");// 台帳仕入先
//		    sb.append("    (select rts.ten_siiresaki_kanri_cd from r_tenbetu_siiresaki rts where rts.kanri_kb =  '"
//                      + mst000901_KanriKbDictionary.BUMON.getCode() + "' and rts.kanri_cd = main.bumon_cd and rts.ten_siiresaki_kanri_cd =" +
//                            " main.ten_siiresaki_kanri_cd and rts.delete_fg = "+ DEL_FG + " group by rts.kanri_kb,rts.kanri_cd,rts.ten_siiresaki_kanri_cd) as  ten_siiresaki_kanri_ck  ");
//            sb.append("from ( select tsa.*,rs.syohin_cd as s_syohin_cd ");
//            sb.append("         from");
//            sb.append("            (select");
//            sb.append("              tr.*");
//            sb.append("             ");
//            sb.append("              from");
//            sb.append("                 (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts left join tr_syohin_gro tr1 ");
//            sb.append("   on tr1.torikomi_dt = tts.torikomi_dt ");
//            sb.append("   and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
//            sb.append("   and  tr1.uketsuke_no=tts.uketsuke_no ");
//            sb.append("            ) tr   ");
//            sb.append("    ");
//            sb.append("                ");
//            sb.append("        ) tsa left join ");
//            sb.append("        (select");
//            sb.append("            rs.*");
//            sb.append("         from");
//            sb.append("            r_syohin rs,");
//            sb.append("            (select");
//            sb.append("                tr.*");
//            sb.append("               ");
//            sb.append("            from");
//            sb.append("                (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts left join tr_syohin_gro tr1 ");
//            sb.append("   on tr1.torikomi_dt = tts.torikomi_dt ");
//            sb.append("   and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
//            sb.append("   and  tr1.uketsuke_no=tts.uketsuke_no ");
//            sb.append("   and  tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
//            sb.append("   and   tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//            sb.append("                ) tr   ");
//            sb.append("   ");
//            sb.append("             ");
//            sb.append("            ) tsa");
//            sb.append("         where");
//            sb.append("            tsa.bumon_cd = rs.bumon_cd and");
//            sb.append("            tsa.syohin_cd = rs.syohin_cd and");
//            sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.yuko_dt and delete_fg='0')");
//            sb.append("        ) rs on tsa.bumon_cd = rs.bumon_cd and tsa.syohin_cd = rs.syohin_cd ");
//            sb.append("    ) main  ");
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000901_KanriKbDictionary.HINBAN.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//		sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.G.getCode() + "' and rst.unit_cd = main.unit_cd  ");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//			sb.append("    left join r_namectf maker_ck on maker_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.JAN_MAKER_NAME).append("' and maker_ck.code_cd = main.maker_cd and maker_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf pc_kb_ck on pc_kb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PC_ISSUE_DIVISION).append("' and pc_kb_ck.code_cd = '").append(mst000611_SystemKbDictionary.G.getCode()).append("'||main.pc_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf daisi_no_nb_ck on daisi_no_nb_ck.syubetu_no_cd = '").append("1090").append("' and daisi_no_nb_ck.code_cd = main.daisi_no_nb and daisi_no_nb_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf syuzei_hokoku_ck on syuzei_hokoku_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.LIQUOR_TAX_REPORT_DIVIDE).append("' and syuzei_hokoku_ck.code_cd = main.syuzei_hokoku_kb and syuzei_hokoku_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf unit_price_tani_ck on unit_price_tani_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT_PRICE_UNIT_AMOUNT).append("' and unit_price_tani_ck.code_cd = main.unit_price_tani_kb and unit_price_tani_ck.delete_fg = '").append(DEL_FG).append("'");
//
//            sb.append("    left join r_namectf bumon_ck on bumon_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SECTION).append("' and bumon_ck.code_cd = main.bumon_cd and bumon_ck.delete_fg = '").append(DEL_FG).append("'");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = '" + mst000611_SystemKbDictionary.G.getCode() + "' || main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_subclass sub_class_ck on sub_class_ck.bumon_cd = main.bumon_cd and sub_class_ck.subclass_cd = main.subclass_cd and sub_class_ck.delete_fg = '").append(DEL_FG).append("'");
//
////    		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////            sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = main.bumon_cd and siiresaki_ck.area_cd = main.area_cd and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = '0000' and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
////    		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//
//            sb.append("    left join r_namectf syouhin_ck on syouhin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and syouhin_ck.code_cd = main.fuji_syohin_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf pb_kb_ck on pb_kb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PB_DIVISION).append("' and pb_kb_ck.code_cd = main.pb_kb and pb_kb_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf bland_ck on bland_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_ck.code_cd = main.bland_cd and bland_ck.delete_fg = '").append(DEL_FG).append("'");
////    		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////            sb.append("    left join r_namectf area_ck on area_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_ck.code_cd = main.area_cd and area_ck.delete_fg = '").append(DEL_FG).append("'");
////    		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd ");
//		sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000611_SystemKbDictionary.G.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd ");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("	   left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
//		sb.append("    left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = '" + mst000611_SystemKbDictionary.G.getCode() + "' || main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_namectf daicho_tenpo_ck on daicho_tenpo_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_LEDGER_STORE).append("' and daicho_tenpo_ck.code_cd = main.daicho_tenpo_kb and daicho_tenpo_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf daicho_honbu_ck on daicho_honbu_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_LEDGER_CENTER).append("' and daicho_honbu_ck.code_cd = main.daicho_honbu_kb and daicho_honbu_ck.delete_fg = '").append(DEL_FG).append("'");
//            sb.append("    left join r_namectf daicho_siiresaki_ck on daicho_siiresaki_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_LEDGER_VENDOR).append("' and daicho_siiresaki_ck.code_cd = main.daicho_siiresaki_kb and daicho_siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf honbu_zai_ck on honbu_zai_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.STOCK_DIVISION).append("' and honbu_zai_ck.code_cd = main.honbu_zai_kb and honbu_zai_ck.delete_fg = '").append(DEL_FG).append("'");
//
//            sb.append(" where ");
//            sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
//            sb.append("  and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//
//            sb.append(" union ");
//            // 商品マスタ（デイリー）
//            sb.append(" select");
////			↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    main.insert_user_id as insert_user_id,"); //登録者
////			↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
//            sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //EXCELファイル種別
//            sb.append("    main.uketsuke_no as uketsuke_no,"); //受付No
//            sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付SEQNo
//            sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
//            sb.append("    main.by_no as by_no,"); //バイヤーＮＯ
//
//            sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
//            sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
//            sb.append("    main.unit_cd as unit_cd,"); //ユニットコード
//            sb.append("    main.haifu_cd as haifu_cd,"); //配布コード
//            sb.append("    main.subclass_cd as subclass_cd,"); //サブクラスコード
//            sb.append("    main.yuko_dt as yuko_dt,"); //有効日
//            sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原価単価
//            sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売価単価
//
////    		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////            sb.append("    main.area_cd as area_cd,"); //地区コード
////    		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//
//            sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
//            sb.append("    main.bland_cd as bland_cd,"); //ブランドコード
//            sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
//            sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
//            sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
//            sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
//            sb.append("    main.eos_kb as eos_kb,"); //EOS区分
//            sb.append("    main.pb_kb as pb_kb,"); //ＰＢ区分
//            sb.append("    main.fuji_syohin_kb as fuji_syohin_kb,"); //fuji商品区分
//
//		    sb.append("    main.hinmei_kanji_na as hinmei_kanji_na,"); //漢字品名
//		    sb.append("    main.hinmei_kana_na as hinmei_kana_na,"); //カナ品名
//		    sb.append("    main.maker_cd as maker_cd,"); //JANメーカーコード
//		    sb.append("    '' as pos_cd,"); //posコード
//		    sb.append("    '' as size_cd,"); //サイズコード
//		    sb.append("    '' as color_cd,"); //カラーコード
//		    sb.append("    '' as keshi_baika_vl,"); //消札売価
//		    sb.append("    '' as tokubetu_genka_vl,"); //特別原価
//		    sb.append("    '' as keiyakusu_qt,"); //契約数量
//		    sb.append("    '' as tuika_keiyakusu_qt,"); //追加契約数量
//		    sb.append("    main.plu_send_dt as plu_send_dt,"); //PLU送信日
//		    sb.append("    main.ten_siiresaki_kanri_cd as ten_siiresaki_kanri_cd,"); //店別仕入先コード
//		    sb.append("    main.honbu_zai_kb as honbu_zai_kb,"); //本部在庫区分
//		    sb.append("    main.hachu_tani_na as hachu_tani_na,"); //発注単位呼称
//		    sb.append("    main.hachutani_irisu_qt as hachutani_irisu_qt,"); //発注単位
//		    sb.append("    main.max_hachutani_qt as max_hachutani_qt,"); //最大発注単位
//		    sb.append("    main.maker_kibo_kakaku_vl as maker_kibo_kakaku_vl,"); //メーカー希望小売価格（税込）
//		    sb.append("    main.siire_hinban_cd as siire_hinban_cd,"); //仕入先品番
//		    sb.append("    '' as season_cd,"); //シーズンコード
//		    sb.append("    '' as yoridori_vl,"); //よりどり価格
//		    sb.append("    '' as yoridori_qt,"); //よりどり販売数量
//		    sb.append("    '' as yoridori_kb,"); //よりどり種類
//		    sb.append("    '' as nefuda_kb,"); //tag値札区分
//		    sb.append("    '' as nefuda_ukewatasi_dt,"); //tag値札受渡日
//		    sb.append("    '' as nefuda_ukewatasi_kb,"); //tag値札受渡方法
//		    sb.append("    '' as sozai_cd,"); //素材コード
//		    sb.append("    '' as oriami_cd,"); //織り編みコード
//		    sb.append("    '' as sode_cd,"); //袖丈コード
//		    sb.append("    '' as age_cd,"); //年代コード
//		    sb.append("    '' as pattern_cd,"); //パターンコード
//		    sb.append("    main.bin_1_kb as bin_1_kb,"); //①便区分
//		    sb.append("    main.bin_2_kb as bin_2_kb,"); //②便区分
//		    sb.append("    main.yusen_bin_kb as yusen_bin_kb,"); //優先便区分
//		    sb.append("    main.pc_kb as pc_kb,"); //PC発行区分
//		    sb.append("    main.daisi_no_nb as daisi_no_nb,"); //台紙ＮＯ
//		    sb.append("    main.daicho_tenpo_kb as daicho_tenpo_kb,"); //商品台帳（店舗）
//		    sb.append("    main.daicho_honbu_kb as daicho_honbu_kb,"); //商品台帳（本部）
//		    sb.append("    main.daicho_siiresaki_kb as daicho_siiresaki_kb,"); //商品台帳（仕入先）
//		    sb.append("    '' as syuzei_hokoku_kb,");//酒税報告区分
//		    sb.append("    main.syohi_kigen_dt as syohi_kigen_dt,"); //消費期限
//		    sb.append("    main.unit_price_tani_kb as unit_price_tani_kb,"); //ユニットプライス・単位
//		    sb.append("    '' as unit_price_naiyoryo_qt,");//ユニットプライス-内容量
//		    sb.append("    '' as unit_price_kijun_tani_qt,");//ユニットプライス-基準単位量
//
//		    sb.append("    bumon_ck.code_cd as bumon_ck,"); //部門コード存在チェック
//		    sb.append("    main.s_syohin_cd as s_syohin_cd,"); //商品コード存在チェック
//		    sb.append("    unit_ck.code_cd as unit_ck,"); //ユニットコード存在チェック
//		    sb.append("    rst_unit_ck.unit_cd as rst_unit_ck,"); //ユニットコード存在チェック
//		    sb.append("    sub_class_ck.subclass_cd as sub_class_ck,"); //サブクラスコードチェック
//
////    		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////		    sb.append("    area_ck.code_cd as area_ck,"); //地区コード存在チェック
////    		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//
//		    sb.append("    siiresaki_ck.siiresaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
//		    sb.append("    bland_ck.code_cd as bland_ck,"); //ブランドコード存在チェック
//		    sb.append("    pb_ck.code_cd as pb_ck,"); //ＰＢ区分存在チェック
//		    sb.append("    fuji_syohin_ck.code_cd as fuji_syohin_ck,"); //fuji商品区分存在チェック
//
//		    sb.append("    maker_ck.code_cd as maker_ck,"); //JANメーカーコード存在チェック
//		    sb.append("    '' as syohin_2_cd,"); // 商品コード２存在チェック
//		    sb.append("    '' as size_ck,"); //サイズコード存在チェック
//		    sb.append("    '' as color_ck,"); //カラーコード存在チェック
//		    sb.append("    '' as season_ck,"); //シーズンコード存在チェック
//		    sb.append("    '' as yoridori_ck,"); //よりどり種類存在チェック
//		    sb.append("    '' as nefuda_ck,"); //TAG値札区分存在チェック
//		    sb.append("    '' as nefuda_ukewatasi_ck,"); //TAG値札受渡方法存在チェック
//		    sb.append("    '' as sozai_ck,"); //素材コード存在チェック
//		    sb.append("    '' as oriami_ck,"); //織り編みコード存在チェック
//		    sb.append("    '' as sode_ck,"); //袖丈コード存在チェック
//		    sb.append("    '' as age_ck,"); //年代コード存在チェック
//		    sb.append("    '' as pattern_ck,"); //パターンコード存在チェック
//		    sb.append("    pc_ck.code_cd as pc_ck,"); //PC発行区分存在チェック
//		    sb.append("    daisi_no_nb_ck.code_cd as daisi_no_nb_ck,"); //台紙ＮＯ存在チェック
//			sb.append("    honbu_zai_ck.code_cd as honbu_zai_ck,");	// 本部在庫区分
//		    sb.append("    '' as syuzei_hokoku_ck, "); //酒税報告区分存在チェック
//		    sb.append("    unit_price_tani_ck.code_cd as unit_price_tani_ck,"); //ユニットプライス・単位存在チェック
//		    sb.append("    '' as daicho_tenpo_ck, ");// 台帳店舗
//		    sb.append("    '' as daicho_honbu_ck, ");// 台帳本部
//		    sb.append("    '' as daicho_siiresaki_ck, ");// 台帳仕入先
////		↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
////		    sb.append("    (select");
////		    sb.append("        t.siiresaki_cd");
////		    sb.append("    from");
////		    sb.append("        r_tenbetu_siiresaki t,");
////		    sb.append("        r_siiresaki s");
////		    sb.append("    where  ");
////		    sb.append("        t.kanri_kb = '").append(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()).append("' and");
////		    sb.append("        t.kanri_cd = '").append(mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD).append("' and");
////		    sb.append("        t.ten_siiresaki_kanri_cd =main.ten_siiresaki_kanri_cd and");
////		    sb.append("        t.tenpo_cd = '000000' and");
////		    sb.append("        t.delete_fg = '").append(DEL_FG).append("' and");
////		    sb.append("        t.kanri_kb = s.kanri_kb and");
////		    sb.append("        t.kanri_cd = s.kanri_cd and");
////		    sb.append("        t.siiresaki_cd = s.siiresaki_cd and");
////		    sb.append("        s.tosan_kb = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("' and");
////		    sb.append("        s.delete_fg = '").append(DEL_FG).append("'");
////		    sb.append("    ) as ten_siiresaki_kanri_ck "); //店別仕入コード存在チェック
//			sb.append("    (select rts.ten_siiresaki_kanri_cd from r_tenbetu_siiresaki rts where rts.kanri_kb =  '"
//					  + mst000901_KanriKbDictionary.BUMON.getCode() + "' and rts.kanri_cd = main.bumon_cd and rts.ten_siiresaki_kanri_cd =" +
//							" main.ten_siiresaki_kanri_cd and rts.delete_fg = "+ DEL_FG + " group by rts.kanri_kb,rts.kanri_cd,rts.ten_siiresaki_kanri_cd) as  ten_siiresaki_kanri_ck  "); //店別仕入コード存在チェック
////		↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
//		    sb.append("from ( ");
//            sb.append("    select tsa.*,rs.syohin_cd as s_syohin_cd  ");
//            sb.append("    from");
//            sb.append("    (select");
//            sb.append("        tr.*,");
//            sb.append("        by_no");
//            sb.append("    from");
//            sb.append("        tr_toroku_syonin tts left join tr_syohin_fre tr  on ");
//            sb.append("        tr.torikomi_dt = tts.torikomi_dt  ");
//            sb.append("        AND tr.excel_file_syubetsu = tts.excel_file_syubetsu ");
//            sb.append("        AND tr.uketsuke_no =tts.uketsuke_no  ");
//            sb.append("    ) tsa left join ");
//            sb.append("         (select");
//            sb.append("             rs.*");
//            sb.append("          from");
//            sb.append("             r_syohin rs,");
//            sb.append("             (select");
//            sb.append("                 tr.* ");
//            sb.append("                 ");
//            sb.append("             from");
//            sb.append("                 (select ");
//            sb.append("                             tr1.*,");
//            sb.append("                             tts.by_no as by_no ");
//            sb.append("                       from tr_toroku_syonin tts left join TR_SYOHIN_FRE  tr1 ");
//            sb.append("                                                                             on tr1.torikomi_dt = tts.torikomi_dt ");
//            sb.append("                                                                             and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
//            sb.append("                                                                             and  tr1.uketsuke_no=tts.uketsuke_no ");
//            sb.append("                                         and  tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
//            sb.append("                                         and  tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//            sb.append(" ) tr  ");
//            sb.append("             ) tsa");
//            sb.append("       where");
//            sb.append("             tsa.bumon_cd = rs.bumon_cd and");
//            sb.append("             tsa.syohin_cd = rs.syohin_cd and");
//            sb.append("             rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.yuko_dt and delete_fg='0')");
//            sb.append("         ) rs on tsa.bumon_cd = rs.bumon_cd and tsa.syohin_cd = rs.syohin_cd ");
//            sb.append("     ) main  ");
//            sb.append("    left join r_namectf maker_ck on maker_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.JAN_MAKER_NAME).append("' and maker_ck.code_cd = rtrim(main.maker_cd) and maker_ck.delete_fg = '").append(DEL_FG).append("'");
//// ===BEGIN=== Modify by kou 2006/7/28
////            sb.append("    left join r_namectf bland_ck on bland_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_ck.code_cd = ('0000' || rtrim(main.bland_cd)) and bland_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf bland_ck on bland_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_ck.code_cd = (rtrim(main.bland_cd)) and bland_ck.delete_fg = '").append(DEL_FG).append("'");
//// ===END=== Modify by kou 2006/7/28
////    		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////            sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()).append("' and siiresaki_ck.kanri_cd = '").append(mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD).append("' and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.delete_fg = '").append(DEL_FG).append("' and siiresaki_ck.tosan_kb = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("'");
//            sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()).append("' and siiresaki_ck.kanri_cd = '0000' and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.delete_fg = '").append(DEL_FG).append("' and siiresaki_ck.tosan_kb = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("'");
////    		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
////		↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf pc_ck on pc_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PC_ISSUE_DIVISION).append("' and pc_ck.code_cd = main.pc_kb and pc_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf pc_ck on pc_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PC_ISSUE_DIVISION).append("' and pc_ck.code_cd = '").append(mst000611_SystemKbDictionary.F.getCode()).append("'||main.pc_kb and pc_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_namectf unit_price_tani_ck on unit_price_tani_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT_PRICE_UNIT_AMOUNT).append("' and unit_price_tani_ck.code_cd = rtrim(main.unit_price_tani_kb) and unit_price_tani_ck.delete_fg = '").append(DEL_FG).append("'");
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000901_KanriKbDictionary.HINBAN.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//		sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.F.getCode() + "' and rst.unit_cd = main.unit_cd  ");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_namectf bumon_ck on bumon_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SECTION).append("' and bumon_ck.code_cd = main.bumon_cd and bumon_ck.delete_fg = '").append(DEL_FG).append("'");
////		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = '" + mst000611_SystemKbDictionary.F.getCode() + "' || main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("    left join r_syohin_taikei rst_unit_ck on rst_unit_ck.system_kb =  '" + mst000901_KanriKbDictionary.UNIT.getCode() + "' and rst_unit_ck.unit_cd = main.unit_cd  ");
//		sb.append("    left join r_syohin_taikei rst_unit_ck on rst_unit_ck.system_kb =  '" + mst000611_SystemKbDictionary.F.getCode() + "' and rst_unit_ck.unit_cd = main.unit_cd  ");
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_subclass sub_class_ck on sub_class_ck.bumon_cd = main.bumon_cd and sub_class_ck.subclass_cd = main.subclass_cd and sub_class_ck.delete_fg = '").append(DEL_FG).append("'");
//
////    		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
////            sb.append("    left join r_namectf area_ck on area_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_ck.code_cd = main.area_cd and area_ck.delete_fg = '").append(DEL_FG).append("'");
//
////    		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
////		↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf pb_ck on pb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PB_DIVISION).append("' and pb_ck.code_cd = '").append(mst000611_SystemKbDictionary.F.getCode()).append("'||main.pb_kb and pb_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf pb_ck on pb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PB_DIVISION).append("' and pb_ck.code_cd = main.pb_kb and pb_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
////		↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
////			sb.append("    left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = '" + mst000611_SystemKbDictionary.F.getCode() + "' || main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
////		↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
//            sb.append("    left join r_namectf daisi_no_nb_ck on daisi_no_nb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PRICE_SEAL_KIND).append("' and daisi_no_nb_ck.code_cd = main.daisi_no_nb and daisi_no_nb_ck.delete_fg = '").append(DEL_FG).append("'");
//			sb.append("    left join r_namectf honbu_zai_ck on honbu_zai_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.STOCK_DIVISION).append("' and honbu_zai_ck.code_cd = main.honbu_zai_kb and honbu_zai_ck.delete_fg = '").append(DEL_FG).append("'");
//
//            sb.append(" where ");
//            sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
//            sb.append("   and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//
//		return sb.toString();
//	}
//	↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑(未使用の為コメント化)

	// ===BEGIN=== Add by kou 2006/8/4
	/**
	 * 業種別によりの商品マスタTRの検索用ＳＱＬの生成を行う。
	 *
	 * @param  strTorikomiDt String
	 * @param  strUketsukeNo String
	 * @param  strSyubetu String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSyohinSql(String strTorikomiDt, String strUketsukeNo, String strSyubetu)
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
			StringBuffer sb = new StringBuffer();

            if("A08".equals(strSyubetu)){
				// 商品マスタ(TAG)
				sb.append("select distinct ");
//				↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
				sb.append("    main.insert_user_id as insert_user_id,"); //登録者
//				↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
				sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //excelファイル種別
				sb.append("    main.uketsuke_no as uketsuke_no,"); //受付ファイル№
				sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付seq №
				sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
				sb.append("    main.by_no as by_no,"); //バイヤーNO

				sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
				sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
				sb.append("    main.unit_cd as unit_cd,"); //ユニットコード
				sb.append("    main.haifu_cd as haifu_cd,"); //配布コード
				sb.append("    main.subclass_cd as subclass_cd,"); //サブクラスコード
				sb.append("    main.yuko_dt as yuko_dt,"); //有効日
				sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原価単価
				sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売価単価
//	   ↓↓2006.07.12 Jiangb 暫定対応↓↓
//				  sb.append("    main.area_cd as area_cd,"); //地区コード
//	   ↑↑2006.07.12 Jiangb 暫定対応↑↑
				sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
				sb.append("    main.bland_cd as bland_cd,"); //ブランドコード
				sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
				// ===BEGIN=== Modify by kou 2006/7/28
				// 販売終了日が空白の場合、99999999をセット
				//sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
				sb.append("    CASE WHEN main.hanbai_ed_dt is null THEN '99999999'"); //販売終了日
				sb.append("         ELSE  main.hanbai_ed_dt END hanbai_ed_dt,"); //販売終了日
				// ===END=== Modify by kou 2006/7/28
				sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
				sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
				sb.append("    main.eos_kb as eos_kb,"); //eos区分
				sb.append("    main.pb_kb as pb_kb,"); //pb区分
				sb.append("    main.fuji_syohin_kb as fuji_syohin_kb,"); //fuji商品区分

				sb.append("    main.hinmei_kanji_na as hinmei_kanji_na,"); //漢字品名
				sb.append("    main.hinmei_kana_na as hinmei_kana_na,"); //カナ品名
				sb.append("    '' as maker_cd,"); //janメーカーコード
				sb.append("    '' as pos_cd,"); //posコード
				sb.append("    main.size_cd as size_cd,"); //サイズコード
				sb.append("    main.color_cd as color_cd,"); //カラーコード
				sb.append("    main.keshi_baika_vl as keshi_baika_vl,"); //消札売価
				sb.append("    main.tokubetu_genka_vl as tokubetu_genka_vl,"); //特別原価
				sb.append("    main.keiyakusu_qt as keiyakusu_qt,"); //契約数量
				sb.append("    main.tuika_keiyakusu_qt as tuika_keiyakusu_qt,"); //追加契約数量
				sb.append("    '' as plu_send_dt,"); //plu送信日
				sb.append("    '' as ten_siiresaki_kanri_cd,"); //店別仕入先管理コード
				sb.append("    '' as honbu_zai_kb,"); //本部在庫区分
				sb.append("    '' as hachu_tani_na,"); //発注単位呼称
				sb.append("    '' as hachutani_irisu_qt,"); //発注単位(入数)
				sb.append("    '' as max_hachutani_qt,"); //最大発注単位
				sb.append("    '' as maker_kibo_kakaku_vl,"); //メーカー希望小売価格（税込）
				sb.append("    main.siire_hinban_cd as siire_hinban_cd,"); //仕入先品番
				sb.append("    main.season_cd as season_cd,"); //シーズンコード
				sb.append("    main.yoridori_vl as yoridori_vl,"); //よりどり価格
				sb.append("    main.yoridori_qt as yoridori_qt,"); //よりどり販売数量
				sb.append("    main.yoridori_kb as yoridori_kb,"); //よりどり種類
				sb.append("    main.nefuda_kb as nefuda_kb,"); //tag値札区分
				sb.append("    main.nefuda_ukewatasi_dt as nefuda_ukewatasi_dt,"); //tag値札受渡日
				sb.append("    main.nefuda_ukewatasi_kb as nefuda_ukewatasi_kb,"); //tag値札受渡方法
				sb.append("    main.sozai_cd as sozai_cd,"); //素材コード
				sb.append("    main.oriami_cd as oriami_cd,"); //織り編みコード
				sb.append("    main.sode_cd as sode_cd,"); //袖丈コード
				sb.append("    main.age_cd as age_cd,"); //年代コード
				sb.append("    main.pattern_cd as pattern_cd,"); //パターンコード
				sb.append("    '' as bin_1_kb,"); //①便区分
				sb.append("    '' as bin_2_kb,"); //②便区分
				sb.append("    '' as yusen_bin_kb,"); //優先便区分
				sb.append("    '' as pc_kb,"); //pc発行区分
				sb.append("    '' as daisi_no_nb,"); //台紙no
				sb.append("    '' as daicho_tenpo_kb,");//商品台帳（店舗）
				sb.append("    '' as daicho_honbu_kb,");//商品台帳（本部）
				sb.append("    '' as daicho_siiresaki_kb,");//商品台帳（仕入先）
				sb.append("    '' as syuzei_hokoku_kb,");//酒税報告区分
				sb.append("    '' as syohi_kigen_dt,");//消費期限
				sb.append("    '' as unit_price_tani_kb,");//ユニットプライス-単位区分
				sb.append("    '' as unit_price_naiyoryo_qt,");//ユニットプライス-内容量
				sb.append("    '' as unit_price_kijun_tani_qt,");//ユニットプライス-基準単位量
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    bumon_ck.code_cd as bumon_ck,"); //部門コード存在チェック
				sb.append("    '' as bumon_ck,"); //部門コード存在チェック
				// ===END=== Modify by kou 2006/10/30
				sb.append("    main.s_syohin_cd as s_syohin_cd, "); //商品コード存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    unit_cd.code_cd as unit_ck,"); //ユニットコード存在チェック
				sb.append("    '' as unit_ck,"); //ユニットコード存在チェック
				// ===END=== Modify by kou 2006/10/30
				sb.append("    rst.unit_cd as rst_unit_ck,"); //商品マスタに部門コードの整合性用ユニットコード存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    subclass_cd.subclass_cd as sub_class_ck,"); //サブクラスコード存在チェック
				sb.append("    '' as sub_class_ck,"); //サブクラスコード存在チェック
				// ===END=== Modify by kou 2006/10/30
//		↓↓2006.07.12 Jiangb 暫定対応↓↓
//				sb.append("    area_cd.code_cd as area_ck,"); //地区コード存在チェック
//		↑↑2006.07.12 Jiangb 暫定対応↑↑
// ===BEGIN=== Modify by kou 2006/8/19
//				sb.append("    siiresaki_cd.siiresaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
				sb.append("    substr(siiresaki_ck.siiresaki_cd,1,6) as siiresaki_ck,"); //仕入先コード存在チェック
// ===END=== Modify by kou 2006/8/19
				// ===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    bland_cd.code_cd as bland_ck,"); //ブランドコード存在チェック
				sb.append("    '' as bland_ck,"); //ブランドコード存在チェック
				// ===END=== Modify by kou 2006/10/30
				sb.append("    pb_kb.code_cd as pb_ck,"); //PB区分存在チェック
				sb.append("    fuji_syohin_kb.code_cd as fuji_syohin_ck,"); //FUJI商品マスタ存在チェック

				sb.append("    '' as maker_ck,"); //JANメーカーコード存在チェック
				sb.append("    '' as syohin_2_cd,"); // 商品コード２存在チェック
				sb.append("    size_cd.code_cd as size_ck,"); //サイズコード存在チェック
				sb.append("    color_cd.code_cd as color_ck,"); //カラーコード存在チェック
				sb.append("    season_cd.code_cd as season_ck,"); //シーズンコード存在チェック
				sb.append("    yoridori_kb.code_cd as yoridori_ck,"); //よりどり種類存在チェック
				sb.append("    nefuda_kb.code_cd as nefuda_ck,"); //TAG値札区分存在チェック
				sb.append("    nefuda_ukewatasi_kb.code_cd as nefuda_ukewatasi_ck,"); //TAG値札受渡方法存在チェック
				sb.append("    sozai_cd.code_cd as sozai_ck,"); //素材コード存在チェック
				sb.append("    oriami_cd.code_cd as oriami_ck,"); //織り編みコード存在チェック
				sb.append("    sode_cd.code_cd as sode_ck,"); //袖丈コード存在チェック
				sb.append("    age_cd.code_cd as age_ck,"); //年代コード存在チェック
				sb.append("    pattern_cd.code_cd as pattern_ck,"); //パターンコード存在チェック
				sb.append("    '' as pc_ck,"); //PC発行区分
				sb.append("    '' as daisi_no_nb_ck,"); //プライスシール種類(台紙NO)存在チェック
				sb.append("    '' as honbu_zai_ck,");	// 本部在庫区分
				sb.append("    '' as syuzei_hokoku_ck, "); //酒税報告区分存在チェック
				sb.append("    '' as unit_price_tani_ck, "); //ユニットプライス-単位区分存在チェック
				sb.append("    '' as daicho_tenpo_ck, ");// 台帳店舗
				sb.append("    '' as daicho_honbu_ck, ");// 台帳本部
				sb.append("    '' as daicho_siiresaki_ck, ");// 台帳仕入先
				sb.append("    '' as ten_siiresaki_kanri_ck "); //店仕入先コード
				sb.append(" from");
				sb.append("   (select tsa.*, rs.syohin_cd as s_syohin_cd");
				sb.append("    from");
				sb.append("    (select");
				sb.append("        tr.*,");
				sb.append("        tts.by_no");
				sb.append("    from");
				// ===BEGIN=== Modify by kou 2006/8/18
				//sb.append("              tr_toroku_syonin tts left join tr_syohin_a08 tr ");
				//sb.append("              on ");
				sb.append("              tr_toroku_syonin tts , tr_syohin_a08 tr ");
				sb.append("              where ");
				// ===END=== Modify by kou 2006/8/18
				sb.append("        tts.torikomi_dt = tr.torikomi_dt and");
				sb.append("        tts.excel_file_syubetsu = tr.excel_file_syubetsu and");
				sb.append("        tts.uketsuke_no = tr.uketsuke_no and ");
				sb.append("        tr.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"' and");
				sb.append("        tr.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
				sb.append("        ) tsa left join ");
				sb.append("        (select");
				sb.append("            rs.*");
				// ===BEGIN=== Modify by kou 2006/8/18
				sb.append("				,TSA.TORIKOMI_DT TORIKOMI_DT");
				sb.append("				,TSA.UKETSUKE_NO UKETSUKE_NO");
				sb.append("				,TSA.UKETSUKE_SEQ UKETSUKE_SEQ");
				// ===END=== Add by kou 2006/8/18
				sb.append("         from");
				sb.append("            r_syohin rs,");
				sb.append("            (select");
				// ===BEGIN=== Add by kou 2006/8/21
				sb.append("					case");
				sb.append("						when tr.yuko_dt is null then '").append(MasterDT).append("'");
				sb.append("						else tr.yuko_dt ");
				sb.append("					end tmp_yuko_dt").append(" , ");
				// ===END=== Add by kou 2006/8/21
				sb.append("                tr.*");
				sb.append("            from");
				// ===BEGIN=== Modify by kou 2006/8/18
				//sb.append("              tr_toroku_syonin tts left join tr_syohin_a08 tr ");
				//sb.append("              on ");
				sb.append("              tr_toroku_syonin tts , tr_syohin_a08 tr ");
				sb.append("              where ");
				// ===END=== Modify by kou 2006/8/18
				sb.append("              tts.torikomi_dt = tr.torikomi_dt and");
				sb.append("              tts.excel_file_syubetsu = tr.excel_file_syubetsu and");
				sb.append("              tts.uketsuke_no = tr.uketsuke_no");
				// ===BEGIN=== Add by kou 2006/8/18
				sb.append("		and");
				sb.append("        tr.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"' and");
				sb.append("        tr.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
				// ===END=== Add by kou 2006/8/18
				sb.append("            ) tsa");
				sb.append("         where");
				sb.append("            tsa.bumon_cd = rs.bumon_cd and");
				sb.append("            tsa.syohin_cd = rs.syohin_cd and");
				// ===BEGIN=== Modify by kou 2006/8/21
				//sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.yuko_dt and delete_fg='0')");
//				↓↓2006.10.17 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.tmp_yuko_dt and delete_fg='0')");
				sb.append("            rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, tsa.tmp_yuko_dt) ");
//				↑↑2006.10.17 H.Yamamoto カスタマイズ修正↑↑
				// ===END=== Modify by kou 2006/8/21
				sb.append("        ) rs");
				sb.append("    on");
				sb.append("        tsa.bumon_cd = rs.bumon_cd and");
				sb.append("        tsa.syohin_cd = rs.syohin_cd");
				// ===BEGIN=== Add by kou 2006/8/18
				sb.append("		AND TSA.TORIKOMI_DT = RS.TORIKOMI_DT");
				sb.append("		AND TSA.UKETSUKE_NO = RS.UKETSUKE_NO");
				sb.append("		AND TSA.UKETSUKE_SEQ = RS.UKETSUKE_SEQ");
				// ===END=== Add by kou 2006/8/18
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_syohin_taikei rst on rst.system_kb = '2' and rst.unit_cd = tsa.unit_cd");
//				↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_syohin_taikei rst on rst.system_kb = '" + mst000611_SystemKbDictionary.T.getCode() + "' and rst.unit_cd = tsa.unit_cd");
//				↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    ) main");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf bumon_ck on bumon_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SECTION).append("' and bumon_ck.code_cd = main.bumon_cd and bumon_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
				sb.append("    left join r_namectf size_cd on size_cd.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)).append("' and size_cd.code_cd = main.size_cd and size_cd.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf color_cd on color_cd.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)).append("' and color_cd.code_cd = main.color_cd and color_cd.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf season_cd on season_cd.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SEASON, userLocal)).append("' and season_cd.code_cd = main.season_cd and season_cd.delete_fg = '").append(DEL_FG).append("'");
//			↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf yoridori_kb on yoridori_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.VARIOUS_KINDS).append("' and yoridori_kb.code_cd = main.yoridori_kb and yoridori_kb.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf yoridori_kb on yoridori_kb.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.VARIOUS_KINDS, userLocal)).append("' and yoridori_kb.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.yoridori_kb and yoridori_kb.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf nefuda_kb on nefuda_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.PRICE_TAG_DIVISION).append("' and nefuda_kb.code_cd = main.nefuda_kb and nefuda_kb.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf nefuda_kb on nefuda_kb.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PRICE_TAG_DIVISION, userLocal)).append("' and nefuda_kb.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.nefuda_kb and nefuda_kb.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf nefuda_ukewatasi_kb on nefuda_ukewatasi_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.RECEIVE_OF_TAG).append("' and nefuda_ukewatasi_kb.code_cd = main.nefuda_ukewatasi_kb and nefuda_ukewatasi_kb.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf nefuda_ukewatasi_kb on nefuda_ukewatasi_kb.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.RECEIVE_OF_TAG, userLocal)).append("' and nefuda_ukewatasi_kb.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.nefuda_ukewatasi_kb and nefuda_ukewatasi_kb.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    left join r_namectf sozai_cd on sozai_cd.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.MATERIAL, userLocal)).append("' and sozai_cd.code_cd = main.sozai_cd and sozai_cd.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf oriami_cd on oriami_cd.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.WEAVING_KNITTING, userLocal)).append("' and oriami_cd.code_cd = main.oriami_cd and oriami_cd.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf sode_cd on sode_cd.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.LENGTH_OF_SLEEVE, userLocal)).append("' and sode_cd.code_cd = main.sode_cd and sode_cd.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf age_cd on age_cd.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.AGE, userLocal)).append("' and age_cd.code_cd = main.age_cd and age_cd.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf pattern_cd on pattern_cd.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HANDLE_PATTERN, userLocal)).append("' and pattern_cd.code_cd = main.pattern_cd and pattern_cd.delete_fg = '").append(DEL_FG).append("'");
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_syohin_taikei rst on rst.system_kb = '2' and rst.unit_cd = main.unit_cd ");
//				sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd  ");
//				↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_syohin_taikei rst on rst.system_kb = '" + mst000611_SystemKbDictionary.T.getCode() + "' and rst.unit_cd = main.unit_cd ");
				sb.append("    left join r_syohin_taikei rst on rst.system_kb = '" + mst000611_SystemKbDictionary.T.getCode() + "' and rst.unit_cd = main.unit_cd ").append("and rst.bumon_cd = main.bumon_cd ");
//				↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000611_SystemKbDictionary.T.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd  ");
				//===END=== Comment out by kou 2006/10/30
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf unit_cd on unit_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_cd.code_cd = main.unit_cd and unit_cd.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf unit_cd on unit_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_cd.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.unit_cd and unit_cd.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//			↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    left join r_subclass subclass_cd on subclass_cd.bumon_cd = main.bumon_cd and subclass_cd.subclass_cd = main.subclass_cd and subclass_cd.delete_fg = '").append(DEL_FG).append("'");

				// ↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				sb.append("    left join r_namectf area_cd on area_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_cd.code_cd = main.area_cd and area_cd.delete_fg = '").append(DEL_FG).append("'");
//				sb.append("    left join r_siiresaki siiresaki_cd on siiresaki_cd.siiresaki_cd = main.siiresaki_cd and siiresaki_cd.kanri_cd = main.bumon_cd and siiresaki_cd.kanri_kb = '1' and siiresaki_cd.area_cd = main.area_cd ");
//				sb.append("    left join r_siiresaki siiresaki_cd on siiresaki_cd.siiresaki_cd = main.siiresaki_cd and siiresaki_cd.kanri_cd = '0000' and siiresaki_cd.kanri_kb = '1' ");
//				↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//				===BEGIN=== Modify by kou 2006/8/18
							 sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '")
								 .append(mst000901_KanriKbDictionary.BUMON.getCode())
								 .append("' and siiresaki_ck.kanri_cd = '0000' ")
//								 .append(" and siiresaki_ck.siiresaki_cd LIKE ")
//								 .append(" substr(main.siiresaki_cd,1,6)").append(" || '%'")
//								↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//								.append(" and substr(siiresaki_ck.siiresaki_cd,1,6) = ")
//								.append(" substr(main.siiresaki_cd,1,6)")
								 .append(" and (siiresaki_ck.siiresaki_cd = ").append(" main.siiresaki_cd ")
								 .append("  or  substr(siiresaki_ck.siiresaki_cd,1,6) = ").append(" rtrim(main.siiresaki_cd)) ")
//								↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
//								↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
								 .append(" and siiresaki_ck.siire_system_kb in ('2','3') ")
//								↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑
								 .append(" and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '")
								 .append(DEL_FG).append("'");
//				===END=== Modify by kou 2006/8/18
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf bland_cd on bland_cd.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_cd.code_cd = main.bland_cd and bland_cd.delete_fg = '").append(DEL_FG).append("'");
				// ===END=== Comment out by kou 2006/10/30
				sb.append("    left join r_namectf pb_kb on pb_kb.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PB_DIVISION, userLocal)).append("' and pb_kb.code_cd = main.pb_kb and pb_kb.delete_fg = '").append(DEL_FG).append("'");
//	   ↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf fuji_syohin_kb on fuji_syohin_kb.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_kb.code_cd = main.fuji_syohin_kb and fuji_syohin_kb.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf fuji_syohin_kb on fuji_syohin_kb.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.GOODS_DIVISION, userLocal)).append("' and fuji_syohin_kb.code_cd = '" + mst000611_SystemKbDictionary.T.getCode() + "' || main.fuji_syohin_kb and fuji_syohin_kb.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
				sb.append(" where ");
				sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
				sb.append("  and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
            }else if("A07".equals(strSyubetu)){
				// 商品マスタTR（実用）
				sb.append("select distinct ");
//				↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
				sb.append("    main.insert_user_id as insert_user_id,"); //登録者
//				↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
				sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //excelファイル種別
				sb.append("    main.uketsuke_no as uketsuke_no,"); //受付no
				sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付seqno
				sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
				sb.append("    main.by_no as by_no,");//バイヤーno

				sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
				sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
				sb.append("    main.unit_cd as unit_cd,"); //ユニットコード
				sb.append("    main.haifu_cd as haifu_cd,"); //配布コード
				sb.append("    main.subclass_cd as subclass_cd,"); //サブクラスコード
				sb.append("    main.yuko_dt as yuko_dt,"); //有効日
				sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原価単価
				sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売価単価
//	   ↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				  sb.append("    main.area_cd as area_cd,"); //地区コード
//	   ↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
				sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
				sb.append("    main.bland_cd as bland_cd,"); //ブランドコード
				sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
				sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
				sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
				sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
				sb.append("    main.eos_kb as eos_kb,"); //eos区分
				sb.append("    main.pb_kb as pb_kb,"); //pb区分
				sb.append("    main.fuji_syohin_kb as fuji_syohin_kb,"); //fuji商品区分

				sb.append("    main.hinmei_kanji_na as hinmei_kanji_na,"); //漢字品名
				sb.append("    main.hinmei_kana_na as hinmei_kana_na,"); //カナ品名
				sb.append("    main.maker_cd as maker_cd,"); //janメーカーコード
				sb.append("    main.pos_cd as pos_cd,"); //posコード
				sb.append("    main.size_cd as size_cd,"); //サイズコード
				sb.append("    main.color_cd as color_cd,"); //カラーコード
				sb.append("    main.keshi_baika_vl as keshi_baika_vl,"); //消札売価
				sb.append("    '' as tokubetu_genka_vl,"); //特別原価
				sb.append("    main.keiyakusu_qt as keiyakusu_qt,"); //契約数量
				sb.append("    main.tuika_keiyakusu_qt as tuika_keiyakusu_qt,"); //追加契約数量
				sb.append("    main.plu_send_dt as plu_send_dt,"); //plu送信日
				sb.append("    '' as ten_siiresaki_kanri_cd,"); //店別仕入先管理コード
				sb.append("    '' as honbu_zai_kb,"); //本部在庫区分
				sb.append("    main.hachu_tani_na as hachu_tani_na,"); //発注単位呼称
				sb.append("    '' as hachutani_irisu_qt,"); //発注単位(入数)
				sb.append("    '' as max_hachutani_qt,"); //最大発注単位
				sb.append("    '' as maker_kibo_kakaku_vl,"); //メーカー希望小売価格（税込）
				sb.append("    main.siire_hinban_cd as siire_hinban_cd,"); //仕入先品番
				sb.append("    main.season_cd as season_cd,"); //シーズンコード
				sb.append("    main.yoridori_vl as yoridori_vl,"); //よりどり価格
				sb.append("    main.yoridori_qt as yoridori_qt,"); //よりどり販売数量
				sb.append("    main.yoridori_kb as yoridori_kb,"); //よりどり種類
				sb.append("    main.nefuda_kb as nefuda_kb,"); //pos値札区分
				sb.append("    main.nefuda_ukewatasi_dt as nefuda_ukewatasi_dt,"); //pos値札受渡日
				sb.append("    main.nefuda_ukewatasi_kb as nefuda_ukewatasi_kb,"); //pos値札受渡方法
				sb.append("    '' as sozai_cd,"); //素材コード
				sb.append("    '' as oriami_cd,"); //織り編みコード
				sb.append("    '' as sode_cd,"); //袖丈コード
				sb.append("    '' as age_cd,"); //年代コード
				sb.append("    '' as pattern_cd,"); //パターンコード
				sb.append("    '' as bin_1_kb,"); //①便区分
				sb.append("    '' as bin_2_kb,"); //②便区分
				sb.append("    '' as yusen_bin_kb,"); //優先便区分
				sb.append("    main.pc_kb as pc_kb,"); //pc発行区分
				sb.append("    main.daisi_no_nb as daisi_no_nb,"); //台紙no
				sb.append("    '' as daicho_tenpo_kb,");//商品台帳（店舗）
				sb.append("    '' as daicho_honbu_kb,");//商品台帳（本部）
				sb.append("    '' as daicho_siiresaki_kb,");//商品台帳（仕入先）
				sb.append("    '' as syuzei_hokoku_kb,");//酒税報告区分
				sb.append("    '' as syohi_kigen_dt,");//消費期限
				sb.append("    '' as unit_price_tani_kb,");//ユニットプライス-単位区分
				sb.append("    '' as unit_price_naiyoryo_qt,");//ユニットプライス-内容量
				sb.append("    '' as unit_price_kijun_tani_qt,");//ユニットプライス-基準単位量
		    	//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    bumon_ck.code_cd as bumon_ck,"); //部門コード存在チェック
				sb.append("    '' as bumon_ck,"); //部門コード存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    main.s_syohin_cd  as s_syohin_cd,"); //商品コード存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    unit_ck.code_cd as unit_ck,"); //ユニットコード存在チェック
				sb.append("    '' as unit_ck,"); //ユニットコード存在チェック
				//===END=== Modify by kou 2006/10/30
//				↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    rst1.unit_cd as rst_unit_ck,"); //商品マスタに部門コードの整合性用ユニットコード存在チェック
				sb.append("    rst.unit_cd as rst_unit_ck,"); //商品マスタに部門コードの整合性用ユニットコード存在チェック
//				↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    sub_class_ck.subclass_cd as sub_class_ck,"); //サブクラスコード存在チェック
				sb.append("    '' as sub_class_ck,"); //サブクラスコード存在チェック
		    	//===END=== Modify by kou 2006/10/30
//				sb.append("    area_ck.code_cd as area_ck,"); //地区コード存在チェック

//				===BEGIN=== Modify by kou 2006/8/19
//				sb.append("    siiresaki_cd.siiresaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
				sb.append("    substr(siiresaki_ck.siiresaki_cd,1,6) as siiresaki_ck,"); //仕入先コード存在チェック
//				===END=== Modify by kou 2006/8/19
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    bland_ck.code_cd bland_ck,");// ブランドコード存在チェック
				sb.append("    '' bland_ck,");// ブランドコード存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    pb_ck.code_cd as pb_ck,"); //PB区分存在チェック
				sb.append("    fuji_syohin_ck.code_cd as fuji_syohin_ck,");// FUJI商品マスタ存在チェック

				sb.append("    maker_ck.code_cd as maker_ck,"); //JANメーカーコード存在チェック
				sb.append("    main.syohin_2_cd as syohin_2_cd,"); // 商品コード２存在チェック
				sb.append("    size_ck.code_cd as size_ck,"); //サイズコード存在チェック
				sb.append("    color_ck.code_cd as color_ck,"); //カラーコード存在チェック
				sb.append("    season_ck.code_cd as season_ck,"); //シーズンコード存在チェック
				sb.append("    yoridori_ck.code_cd as yoridori_ck,"); //よりどり種類存在チェック
				sb.append("    nefuda_ck.code_cd as nefuda_ck,"); //POS値札区分存在チェック
				sb.append("    nefuda_ukewatasi_ck.code_cd as nefuda_ukewatasi_ck,"); //POS値札受渡方法存在チェック
				sb.append("    '' as sozai_ck,"); //素材コード存在チェック
				sb.append("    '' as oriami_ck,"); //織り編みコード存在チェック
				sb.append("    '' as sode_ck,"); //袖丈コード存在チェック
				sb.append("    '' as age_ck,"); //年代コード存在チェック
				sb.append("    '' as pattern_ck,"); //パターンコード存在チェック

				// ===BEGIN=== Modify by kou 2006/8/8
				//sb.append("    pc_kb_ck.code_cd as pc_kb,"); //PC発行区分
				sb.append("    pc_kb_ck.code_cd as pc_ck,"); //PC発行区分
				// ===END=== Modify by kou 2006/8/8

				sb.append("    daisi_no_nb_ck.code_cd as daisi_no_nb_ck,"); //プライスシール種類(台紙NO)存在チェック
				sb.append("    '' as honbu_zai_ck,");	// 本部在庫区分
				sb.append("    '' as syuzei_hokoku_ck, "); //酒税報告区分存在チェック
				sb.append("    '' as unit_price_tani_ck, "); //ユニットプライス-単位区分存在チェック
				sb.append("    '' as daicho_tenpo_ck, ");// 台帳店舗
				sb.append("    '' as daicho_honbu_ck, ");// 台帳本部
				sb.append("    '' as daicho_siiresaki_ck, ");// 台帳仕入先
				sb.append("    '' as ten_siiresaki_kanri_ck "); //店仕入先コード
				sb.append("   from ( select tsa.*,rs.syohin_cd as s_syohin_cd,rs.syohin_2_cd  ");
				sb.append("    from");
				sb.append("        (select");
				sb.append("            tr.* ");
				sb.append("           ");
				sb.append("        from");
//				↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//				sb.append("            (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts left join tr_syohin_a07 tr1");
				sb.append("            (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts inner join tr_syohin_a07 tr1");
//				↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
				sb.append("   on tr1.torikomi_dt = tts.torikomi_dt ");
				sb.append("   and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
				sb.append("   and  tr1.uketsuke_no=tts.uketsuke_no ");
				sb.append(" ) tr  ");
				sb.append("    ");
				sb.append("                ");
				sb.append("        ) tsa left join ");
				sb.append("        (select");
				sb.append("            rs.*");
				sb.append("         from");
				sb.append("            r_syohin rs,");
				sb.append("            (select");
				// ===BEGIN=== Add by kou 2006/8/21
				sb.append("					case");
				sb.append("						when tr.yuko_dt is null then '").append(MasterDT).append("'");
				sb.append("						else tr.yuko_dt ");
				sb.append("					end tmp_yuko_dt").append(" , ");
				// ===END=== Add by kou 2006/8/21
				sb.append("                tr.* ");
				sb.append("                ");
				sb.append("            from");
//				↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//				sb.append("                (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts left join tr_syohin_a07 tr1 ");
				sb.append("                (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts inner join tr_syohin_a07 tr1 ");
//				↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
				sb.append("   on tr1.torikomi_dt = tts.torikomi_dt ");
				sb.append("   and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
				sb.append("   and  tr1.uketsuke_no=tts.uketsuke_no ");
				sb.append("   and  tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
				sb.append("   and  tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
				sb.append("             ) tr ");
				sb.append("            ) tsa");
				sb.append("         where");
				sb.append("            tsa.bumon_cd = rs.bumon_cd and");
				sb.append("            tsa.syohin_cd = rs.syohin_cd and");
				// ===BEGIN=== Modify by kou 2006/8/21
				//sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.yuko_dt and delete_fg='0')");
//				↓↓2006.10.17 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.tmp_yuko_dt and delete_fg='0')");
				sb.append("            rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, tsa.tmp_yuko_dt) ");
//				↑↑2006.10.17 H.Yamamoto カスタマイズ修正↑↑
				// ===END=== Modify by kou 2006/8/21
				sb.append("        ) rs on tsa.bumon_cd = rs.bumon_cd and tsa.syohin_cd = rs.syohin_cd ");
				sb.append("    ) main  ");
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000901_KanriKbDictionary.HINBAN.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.J.getCode() + "' and rst.unit_cd = main.unit_cd  ");
				sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.J.getCode() + "' and rst.unit_cd = main.unit_cd ").append("and rst.bumon_cd = main.bumon_cd ");
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    left join r_namectf maker_ck on maker_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.JAN_MAKER_NAME, userLocal)).append("' and maker_ck.code_cd = main.maker_cd and maker_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf size_ck on size_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)).append("' and size_ck.code_cd = main.size_cd and size_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf color_ck on color_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)).append("' and color_ck.code_cd = main.color_cd and color_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf season_ck on season_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SEASON, userLocal)).append("' and season_ck.code_cd = main.season_cd and season_ck.delete_fg = '").append(DEL_FG).append("'");
//			↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf yoridori_ck on yoridori_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.VARIOUS_KINDS).append("' and yoridori_ck.code_cd = yoridori_kb and yoridori_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf yoridori_ck on yoridori_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.VARIOUS_KINDS, userLocal)).append("' and yoridori_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || yoridori_kb and yoridori_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf nefuda_ck on nefuda_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PRICE_TAG_DIVISION).append("' and nefuda_ck.code_cd = main.nefuda_kb and nefuda_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf nefuda_ck on nefuda_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PRICE_TAG_DIVISION, userLocal)).append("' and nefuda_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || main.nefuda_kb and nefuda_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf nefuda_ukewatasi_ck on nefuda_ukewatasi_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.RECEIVE_OF_TAG).append("' and nefuda_ukewatasi_ck.code_cd = 'nefuda_ukewatasi_kb").append(DEL_FG).append("'");
//			↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf nefuda_ukewatasi_ck on nefuda_ukewatasi_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.RECEIVE_OF_TAG).append("' and nefuda_ukewatasi_ck.code_cd = main.nefuda_ukewatasi_kb and nefuda_ukewatasi_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf nefuda_ukewatasi_ck on nefuda_ukewatasi_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.RECEIVE_OF_TAG, userLocal)).append("' and nefuda_ukewatasi_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || main.nefuda_ukewatasi_kb and nefuda_ukewatasi_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf pc_kb_ck on pc_kb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PC_ISSUE_DIVISION).append("' and pc_kb_ck.code_cd = main.pc_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf pc_kb_ck on pc_kb_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PC_ISSUE_DIVISION, userLocal)).append("' and pc_kb_ck.code_cd = '").append(mst000611_SystemKbDictionary.J.getCode()).append("'||main.pc_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    left join r_namectf daisi_no_nb_ck on daisi_no_nb_ck.syubetu_no_cd = '").append(MessageUtil.getMessage("1090", userLocal)).append("' and daisi_no_nb_ck.code_cd = main.daisi_no_nb and daisi_no_nb_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf bumon_ck on bumon_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SECTION).append("' and bumon_ck.code_cd = main.bumon_cd and bumon_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//			↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//			↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst1 on rst.system_kb =  '" + mst000901_KanriKbDictionary.UNIT.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst1 on rst1.system_kb =  '" + mst000611_SystemKbDictionary.J.getCode() + "' and rst1.unit_cd = main.unit_cd  ");
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_subclass sub_class_ck on sub_class_ck.bumon_cd = main.bumon_cd and sub_class_ck.subclass_cd = main.subclass_cd and sub_class_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30

//	   ↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				  sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = main.bumon_cd and siiresaki_ck.area_cd = main.area_cd and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
// ===BEGIN=== Modify by kou 2006/8/18
				//sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = '0000'  and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '")
					.append(mst000901_KanriKbDictionary.BUMON.getCode())
					.append("' and siiresaki_ck.kanri_cd = '0000' ")
//					↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//					.append(" and siiresaki_ck.siiresaki_cd LIKE ")
//					.append(" substr(main.siiresaki_cd,1,6)").append(" || '%'")
					.append(" and (siiresaki_ck.siiresaki_cd = ").append(" main.siiresaki_cd ")
					.append("  or  substr(siiresaki_ck.siiresaki_cd,1,6) = ").append(" rtrim(main.siiresaki_cd)) ")
//					↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
//					↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
					.append(" and siiresaki_ck.siire_system_kb in ('1','3') ")
//					↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑
					.append(" and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '")
					.append(DEL_FG).append("'");
// ===END=== Modify by kou 2006/8/18
//				↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf syouhin_ck on syouhin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and syouhin_ck.code_cd = main.fuji_syohin_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
				sb.append("    left join r_namectf pb_ck on pb_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PB_DIVISION, userLocal)).append("' and pb_ck.code_cd = main.pb_kb and pb_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf bland_ck on bland_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_ck.code_cd = main.bland_cd and bland_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30

//	   ↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				  sb.append("    left join r_namectf area_ck on area_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_ck.code_cd = main.area_cd and area_ck.delete_fg = '").append(DEL_FG).append("'");
//	   ↑↑2006.07.12 Jiangb カスタマイズ修正↑↑

				//		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd  ");
			//===BEGIN=== Comment out by kou 2006/10/30
			//sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000611_SystemKbDictionary.J.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd  ");
			//===END=== Comment out by kou 2006/10/30
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("	   left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
			sb.append("    left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.GOODS_DIVISION, userLocal)).append("' and fuji_syohin_ck.code_cd = '" + mst000611_SystemKbDictionary.J.getCode() + "' || main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑

				sb.append(" where ");
				sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
				sb.append("  and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
            }else if("GRO".equals(strSyubetu)){
				// 商品マスタ（グロ・バラ）
				sb.append(" select distinct ");
//				↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
				sb.append("    main.insert_user_id as insert_user_id,"); //登録者
//				↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
				sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //excelファイル種別
				sb.append("    main.uketsuke_no as uketsuke_no,"); //受付no
				sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付seqno
				sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
				sb.append("    main.by_no as by_no, ");//バイヤーno

				sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
				sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
				sb.append("    main.unit_cd as unit_cd,"); //ユニットコード
				sb.append("    main.haifu_cd as haifu_cd,"); //配布コード
				sb.append("    main.subclass_cd as subclass_cd,"); //サブクラスコード
				sb.append("    main.yuko_dt as yuko_dt,"); //有効日
				sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原価単価
				sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売価単価

//				  sb.append("    main.area_cd as area_cd,"); //地区コード

				sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
				sb.append("    main.bland_cd as bland_cd,"); //ブランドコード
				sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
				sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
				sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
				sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
				sb.append("    main.eos_kb as eos_kb,"); //eos区分
				sb.append("    main.pb_kb as pb_kb,"); //pb区分
				sb.append("    main.fuji_syohin_kb as fuji_syohin_kb,"); //fuji商品区分

				sb.append("    main.hinmei_kanji_na as hinmei_kanji_na,"); //漢字品名
				sb.append("    main.hinmei_kana_na as hinmei_kana_na,"); //カナ品名
				sb.append("    main.maker_cd as maker_cd,"); //janメーカーコード
				sb.append("    '' as pos_cd,"); //posコード
				sb.append("    '' as size_cd,"); //サイズコード
				sb.append("    '' as color_cd,"); //カラーコード
				sb.append("    '' as keshi_baika_vl,"); //消札売価
				sb.append("    '' as tokubetu_genka_vl,"); //特別原価
				sb.append("    '' as keiyakusu_qt,"); //契約数量
				sb.append("    '' as tuika_keiyakusu_qt,"); //追加契約数量
				sb.append("    main.plu_send_dt as plu_send_dt,"); //plu送信日
				sb.append("    main.ten_siiresaki_kanri_cd as ten_siiresaki_kanri_cd,"); //店別仕入先管理コード
				sb.append("    main.honbu_zai_kb as honbu_zai_kb,"); //本部在庫区分
				sb.append("    main.hachu_tani_na as hachu_tani_na,");  //発注単位呼称
				sb.append("    main.hachutani_irisu_qt as hachutani_irisu_qt,"); //発注単位(入数)
				sb.append("    main.max_hachutani_qt as max_hachutani_qt,"); //最大発注単位
				sb.append("    main.maker_kibo_kakaku_vl as maker_kibo_kakaku_vl,"); //メーカー希望小売価格（税込）
				sb.append("    main.siire_hinban_cd as siire_hinban_cd,"); //仕入先品番
				sb.append("    '' as season_cd,"); //シーズンコード
				sb.append("    '' as yoridori_vl,"); //よりどり価格
				sb.append("    '' as yoridori_qt,"); //よりどり販売数量
				sb.append("    '' as yoridori_kb,"); //よりどり種類
				sb.append("    '' as nefuda_kb,"); //tag値札区分
				sb.append("    '' as nefuda_ukewatasi_dt,"); //tag値札受渡日
				sb.append("    '' as nefuda_ukewatasi_kb,"); //tag値札受渡方法
				sb.append("    '' as sozai_cd,"); //素材コード
				sb.append("    '' as oriami_cd,"); //織り編みコード
				sb.append("    '' as sode_cd,"); //袖丈コード
				sb.append("    '' as age_cd,"); //年代コード
				sb.append("    '' as pattern_cd,"); //パターンコード
				sb.append("    '' as bin_1_kb,"); //①便区分
				sb.append("    '' as bin_2_kb,"); //②便区分
				sb.append("    '' as yusen_bin_kb,"); //優先便区分
				sb.append("    main.pc_kb as pc_kb,"); //pc発行区分
				sb.append("    main.daisi_no_nb as daisi_no_nb,"); //台紙no
				sb.append("    main.daicho_tenpo_kb,");//商品台帳（店舗）
				sb.append("    main.daicho_honbu_kb,");//商品台帳（本部）
				sb.append("    main.daicho_siiresaki_kb,");//商品台帳（仕入先）
				sb.append("    main.syuzei_hokoku_kb,");//酒税報告区分
//				↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
				sb.append("    main.syurui_dosuu_qt,");//酒類度数
//				↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    main.syohi_kigen_dt,");//消費期限
				sb.append("    main.unit_price_tani_kb,");//ユニットプライス-単位区分
				sb.append("    main.unit_price_naiyoryo_qt,");//ユニットプライス-内容量
				sb.append("    main.unit_price_kijun_tani_qt,");//ユニットプライス-基準単位量

				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    bumon_ck.code_cd as bumon_ck, ");// 部門コード存在チェック
				sb.append("    '' as bumon_ck, ");// 部門コード存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    main.s_syohin_cd  as s_syohin_cd, "); // 商品コード存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    unit_ck.code_cd as unit_ck, "); // ユニットコード存在チェック
				sb.append("    '' as unit_ck, "); // ユニットコード存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    rst.unit_cd as rst_unit_ck, "); // ユニットコード商品体系マスタ存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    sub_class_ck.subclass_cd as sub_class_ck, ");// サブクラスコード存在チェック
				sb.append("    '' as sub_class_ck, ");// サブクラスコード存在チェック
		    	//===END=== Modify by kou 2006/10/30
//				sb.append("    area_ck.code_cd as area_ck, "); // 地区コード存在チェック

//				===BEGIN=== Modify by kou 2006/8/19
//				sb.append("    siiresaki_cd.siiresaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
				sb.append("    substr(siiresaki_ck.siiresaki_cd,1,6) as siiresaki_ck, "); // 仕入先コード存在チェック
//				===END=== Modify by kou 2006/8/19
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    bland_ck.code_cd as bland_ck, "); // ブラントコード存在チェック
				sb.append("    '' as bland_ck, "); // ブラントコード存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    pb_kb_ck.code_cd as pb_ck, "); // PB区分存在チェック
				sb.append("    fuji_syohin_ck.code_cd fuji_syohin_ck, ");// FUJI商品マスタ(存在チェック用)

				sb.append("    maker_ck.code_cd as maker_ck, "); //JANメーカーコード存在チェック
				sb.append("    '' as syohin_2_cd,"); // 商品コード２存在チェック
				sb.append("    '' as size_ck,"); //サイズコード存在チェック
				sb.append("    '' as color_ck,"); //カラーコード存在チェック
				sb.append("    '' as season_ck,"); //シーズンコード存在チェック
				sb.append("    '' as yoridori_ck,"); //よりどり種類存在チェック
				sb.append("    '' as nefuda_ck,"); //TAG値札区分存在チェック
				sb.append("    '' as nefuda_ukewatasi_ck,"); //TAG値札受渡方法存在チェック
				sb.append("    '' as sozai_ck,"); //素材コード存在チェック
				sb.append("    '' as oriami_ck,"); //織り編みコード存在チェック
				sb.append("    '' as sode_ck,"); //袖丈コード存在チェック
				sb.append("    '' as age_ck,"); //年代コード存在チェック
				sb.append("    '' as pattern_ck,"); //パターンコード存在チェック
				sb.append("    pc_kb_ck.code_cd as pc_ck, "); //PC発行区分存在チェック
				sb.append("    daisi_no_nb_ck.code_cd as daisi_no_nb_ck, "); //プライスシール種類(台紙NO)存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    honbu_zai_ck.code_cd as honbu_zai_ck,");	// 本部在庫区分
				sb.append("    '' as honbu_zai_ck,");	// 本部在庫区分
				//===END=== Modify by kou 2006/10/30
				sb.append("    syuzei_hokoku_ck.code_cd as syuzei_hokoku_ck, "); //酒税報告区分存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    unit_price_tani_ck.code_cd as unit_price_tani_ck, "); //ユニットプライス-単位区分存在チェック
				sb.append("    '' as unit_price_tani_ck, "); //ユニットプライス-単位区分存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    daicho_tenpo_ck.code_cd as daicho_tenpo_ck, ");// 台帳店舗
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    daicho_honbu_ck.code_cd as daicho_honbu_ck, ");// 台帳本部
				//sb.append("    daicho_siiresaki_ck.code_cd as daicho_siiresaki_ck, ");// 台帳仕入先
				sb.append("    '' as daicho_honbu_ck, ");// 台帳本部
				sb.append("    '' as daicho_siiresaki_ck, ");// 台帳仕入先
				//===END=== Modify by kou 2006/10/30
				sb.append("    (select rts.ten_siiresaki_kanri_cd from r_tenbetu_siiresaki rts where rts.kanri_kb =  '"
						  + mst000901_KanriKbDictionary.BUMON.getCode() + "' and rts.kanri_cd = main.bumon_cd and rts.ten_siiresaki_kanri_cd =" +
								" main.ten_siiresaki_kanri_cd and rts.delete_fg = "+ DEL_FG + " group by rts.kanri_kb,rts.kanri_cd,rts.ten_siiresaki_kanri_cd) as  ten_siiresaki_kanri_ck  ");
				sb.append("from ( select tsa.*,rs.syohin_cd as s_syohin_cd ");
				sb.append("         from");
				sb.append("            (select");
				sb.append("              tr.*");
				sb.append("             ");
				sb.append("              from");
//				↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//				sb.append("                 (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts left join tr_syohin_gro tr1 ");
				sb.append("                 (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts inner join tr_syohin_gro tr1 ");
//				↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
				sb.append("   on tr1.torikomi_dt = tts.torikomi_dt ");
				sb.append("   and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
				sb.append("   and  tr1.uketsuke_no=tts.uketsuke_no ");
				sb.append("            ) tr   ");
				sb.append("    ");
				sb.append("                ");
				sb.append("        ) tsa left join ");
				sb.append("        (select");
				sb.append("            rs.*");
				sb.append("         from");
				sb.append("            r_syohin rs,");
				sb.append("            (select");
				// ===BEGIN=== Add by kou 2006/8/21
				sb.append("					case");
				sb.append("						when tr.yuko_dt is null then '").append(MasterDT).append("'");
				sb.append("						else tr.yuko_dt ");
				sb.append("					end tmp_yuko_dt").append(" , ");
				// ===END=== Add by kou 2006/8/21
				sb.append("                tr.*");
				sb.append("               ");
				sb.append("            from");
//				↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//				sb.append("                (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts left join tr_syohin_gro tr1 ");
				sb.append("                (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts inner join tr_syohin_gro tr1 ");
//				↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
				sb.append("   on tr1.torikomi_dt = tts.torikomi_dt ");
				sb.append("   and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
				sb.append("   and  tr1.uketsuke_no=tts.uketsuke_no ");
				sb.append("   and  tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
				sb.append("   and   tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
				sb.append("                ) tr   ");
				sb.append("   ");
				sb.append("             ");
				sb.append("            ) tsa");
				sb.append("         where");
				sb.append("            tsa.bumon_cd = rs.bumon_cd and");
				sb.append("            tsa.syohin_cd = rs.syohin_cd and");
				// ===BEGIN=== Modify by kou 2006/8/21
				//sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.yuko_dt and delete_fg='0')");
//				↓↓2006.10.17 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.tmp_yuko_dt and delete_fg='0')");
				sb.append("            rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, tsa.tmp_yuko_dt) ");
//				↑↑2006.10.17 H.Yamamoto カスタマイズ修正↑↑
				// ===END=== Modify by kou 2006/8/21
				sb.append("        ) rs on tsa.bumon_cd = rs.bumon_cd and tsa.syohin_cd = rs.syohin_cd ");
				sb.append("    ) main  ");
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000901_KanriKbDictionary.HINBAN.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.G.getCode() + "' and rst.unit_cd = main.unit_cd  ");
				sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.G.getCode() + "' and rst.unit_cd = main.unit_cd ").append("and rst.bumon_cd = main.bumon_cd ");
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    left join r_namectf maker_ck on maker_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.JAN_MAKER_NAME, userLocal)).append("' and maker_ck.code_cd = main.maker_cd and maker_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf pc_kb_ck on pc_kb_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PC_ISSUE_DIVISION, userLocal)).append("' and pc_kb_ck.code_cd = '").append(mst000611_SystemKbDictionary.G.getCode()).append("'||main.pc_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf daisi_no_nb_ck on daisi_no_nb_ck.syubetu_no_cd = '").append(MessageUtil.getMessage("1090", userLocal)).append("' and daisi_no_nb_ck.code_cd = main.daisi_no_nb and daisi_no_nb_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf syuzei_hokoku_ck on syuzei_hokoku_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.LIQUOR_TAX_REPORT_DIVIDE, userLocal)).append("' and syuzei_hokoku_ck.code_cd = main.syuzei_hokoku_kb and syuzei_hokoku_ck.delete_fg = '").append(DEL_FG).append("'");

				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf unit_price_tani_ck on unit_price_tani_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT_PRICE_UNIT_AMOUNT).append("' and unit_price_tani_ck.code_cd = main.unit_price_tani_kb and unit_price_tani_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30

				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf bumon_ck on bumon_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SECTION).append("' and bumon_ck.code_cd = main.bumon_cd and bumon_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//			↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = '" + mst000611_SystemKbDictionary.G.getCode() + "' || main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//			↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_subclass sub_class_ck on sub_class_ck.bumon_cd = main.bumon_cd and sub_class_ck.subclass_cd = main.subclass_cd and sub_class_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30

//				↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				  sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = main.bumon_cd and siiresaki_ck.area_cd = main.area_cd and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
//				sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = '0000' and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
//				↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//				===BEGIN=== Modify by kou 2006/8/18
							 //sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = '0000'  and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
							 sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '")
								 .append(mst000901_KanriKbDictionary.BUMON.getCode())
								 .append("' and siiresaki_ck.kanri_cd = '0000' ")
//								↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//								 .append(" and siiresaki_ck.siiresaki_cd LIKE ")
//								 .append(" substr(main.siiresaki_cd,1,6)").append(" || '%'")
								 .append(" and (siiresaki_ck.siiresaki_cd = ").append(" main.siiresaki_cd ")
								 .append("  or  substr(siiresaki_ck.siiresaki_cd,1,6) = ").append(" rtrim(main.siiresaki_cd)) ")
//								↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
//								↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
								 .append(" and siiresaki_ck.siire_system_kb in ('1','3') ")
//								↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑
								 .append(" and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '")
								 .append(DEL_FG).append("'");
//				===END=== Modify by kou 2006/8/18

				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf syouhin_ck on syouhin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and syouhin_ck.code_cd = main.fuji_syohin_kb and pc_kb_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
				sb.append("    left join r_namectf pb_kb_ck on pb_kb_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PB_DIVISION, userLocal)).append("' and pb_kb_ck.code_cd = main.pb_kb and pb_kb_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf bland_ck on bland_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_ck.code_cd = main.bland_cd and bland_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//				↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				  sb.append("    left join r_namectf area_ck on area_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_ck.code_cd = main.area_cd and area_ck.delete_fg = '").append(DEL_FG).append("'");
//				↑↑2006.07.12 Jiangb カスタマイズ修正↑↑

//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd ");
			//===BEGIN=== Comment out by kou 2006/10/30
			//sb.append("    left join r_syohin_taikei bumon_cd2 on bumon_cd2.system_kb =  '" + mst000611_SystemKbDictionary.G.getCode() + "' and bumon_cd2.unit_cd = main.unit_cd ");
			//===END=== Comment out by kou 2006/10/30
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("	   left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
			sb.append("    left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.GOODS_DIVISION, userLocal)).append("' and fuji_syohin_ck.code_cd = '" + mst000611_SystemKbDictionary.G.getCode() + "' || main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    left join r_namectf daicho_tenpo_ck on daicho_tenpo_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.GOODS_LEDGER_STORE, userLocal)).append("' and daicho_tenpo_ck.code_cd = main.daicho_tenpo_kb and daicho_tenpo_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf daicho_honbu_ck on daicho_honbu_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_LEDGER_CENTER).append("' and daicho_honbu_ck.code_cd = main.daicho_honbu_kb and daicho_honbu_ck.delete_fg = '").append(DEL_FG).append("'");
				//sb.append("    left join r_namectf daicho_siiresaki_ck on daicho_siiresaki_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_LEDGER_VENDOR).append("' and daicho_siiresaki_ck.code_cd = main.daicho_siiresaki_kb and daicho_siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
				//sb.append("    left join r_namectf honbu_zai_ck on honbu_zai_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.STOCK_DIVISION).append("' and honbu_zai_ck.code_cd = main.honbu_zai_kb and honbu_zai_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30

				sb.append(" where ");
				sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
				sb.append("  and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
            }else if("FRE".equals(strSyubetu)){
				// 商品マスタ（デイリー）
				sb.append(" select distinct ");
//				↓↓2006.07.18 H.Yamamoto カスタマイズ修正↓↓
				sb.append("    main.insert_user_id as insert_user_id,"); //登録者
//				↑↑2006.07.18 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
				sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //EXCELファイル種別
				sb.append("    main.uketsuke_no as uketsuke_no,"); //受付No
				sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付SEQNo
				sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
				sb.append("    main.by_no as by_no,"); //バイヤーＮＯ

				sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
				sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
				sb.append("    main.unit_cd as unit_cd,"); //ユニットコード
				sb.append("    main.haifu_cd as haifu_cd,"); //配布コード
				sb.append("    main.subclass_cd as subclass_cd,"); //サブクラスコード
				sb.append("    main.yuko_dt as yuko_dt,"); //有効日
				sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原価単価
				sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売価単価

//				↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				  sb.append("    main.area_cd as area_cd,"); //地区コード
//				↑↑2006.07.12 Jiangb カスタマイズ修正↑↑

				sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
				sb.append("    main.bland_cd as bland_cd,"); //ブランドコード
				sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
				sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
				sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
				sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
				sb.append("    main.eos_kb as eos_kb,"); //EOS区分
				sb.append("    main.pb_kb as pb_kb,"); //ＰＢ区分
				sb.append("    main.fuji_syohin_kb as fuji_syohin_kb,"); //fuji商品区分

				sb.append("    main.hinmei_kanji_na as hinmei_kanji_na,"); //漢字品名
				sb.append("    main.hinmei_kana_na as hinmei_kana_na,"); //カナ品名
				sb.append("    main.maker_cd as maker_cd,"); //JANメーカーコード
				sb.append("    '' as pos_cd,"); //posコード
				sb.append("    '' as size_cd,"); //サイズコード
				sb.append("    '' as color_cd,"); //カラーコード
				sb.append("    '' as keshi_baika_vl,"); //消札売価
				sb.append("    '' as tokubetu_genka_vl,"); //特別原価
				sb.append("    '' as keiyakusu_qt,"); //契約数量
				sb.append("    '' as tuika_keiyakusu_qt,"); //追加契約数量
				sb.append("    main.plu_send_dt as plu_send_dt,"); //PLU送信日
				sb.append("    main.ten_siiresaki_kanri_cd as ten_siiresaki_kanri_cd,"); //店別仕入先コード
				sb.append("    main.honbu_zai_kb as honbu_zai_kb,"); //本部在庫区分
				sb.append("    main.hachu_tani_na as hachu_tani_na,"); //発注単位呼称
				sb.append("    main.hachutani_irisu_qt as hachutani_irisu_qt,"); //発注単位
				sb.append("    main.max_hachutani_qt as max_hachutani_qt,"); //最大発注単位
				sb.append("    main.maker_kibo_kakaku_vl as maker_kibo_kakaku_vl,"); //メーカー希望小売価格（税込）
				sb.append("    main.siire_hinban_cd as siire_hinban_cd,"); //仕入先品番
				sb.append("    '' as season_cd,"); //シーズンコード
				sb.append("    '' as yoridori_vl,"); //よりどり価格
				sb.append("    '' as yoridori_qt,"); //よりどり販売数量
				sb.append("    '' as yoridori_kb,"); //よりどり種類
				sb.append("    '' as nefuda_kb,"); //tag値札区分
				sb.append("    '' as nefuda_ukewatasi_dt,"); //tag値札受渡日
				sb.append("    '' as nefuda_ukewatasi_kb,"); //tag値札受渡方法
				sb.append("    '' as sozai_cd,"); //素材コード
				sb.append("    '' as oriami_cd,"); //織り編みコード
				sb.append("    '' as sode_cd,"); //袖丈コード
				sb.append("    '' as age_cd,"); //年代コード
				sb.append("    '' as pattern_cd,"); //パターンコード
				sb.append("    main.bin_1_kb as bin_1_kb,"); //①便区分
				sb.append("    main.bin_2_kb as bin_2_kb,"); //②便区分
				sb.append("    main.yusen_bin_kb as yusen_bin_kb,"); //優先便区分
				sb.append("    main.pc_kb as pc_kb,"); //PC発行区分
				sb.append("    main.daisi_no_nb as daisi_no_nb,"); //台紙ＮＯ
				sb.append("    main.daicho_tenpo_kb as daicho_tenpo_kb,"); //商品台帳（店舗）
				sb.append("    main.daicho_honbu_kb as daicho_honbu_kb,"); //商品台帳（本部）
				sb.append("    main.daicho_siiresaki_kb as daicho_siiresaki_kb,"); //商品台帳（仕入先）
				sb.append("    '' as syuzei_hokoku_kb,");//酒税報告区分
				sb.append("    main.syohi_kigen_dt as syohi_kigen_dt,"); //消費期限
				sb.append("    main.unit_price_tani_kb as unit_price_tani_kb,"); //ユニットプライス・単位
				sb.append("    '' as unit_price_naiyoryo_qt,");//ユニットプライス-内容量
				sb.append("    '' as unit_price_kijun_tani_qt,");//ユニットプライス-基準単位量

				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    bumon_ck.code_cd as bumon_ck,"); //部門コード存在チェック
				sb.append("    '' as bumon_ck,"); //部門コード存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    main.s_syohin_cd as s_syohin_cd,"); //商品コード存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    unit_ck.code_cd as unit_ck,"); //ユニットコード存在チェック
				sb.append("    '' as unit_ck,"); //ユニットコード存在チェック
				//===END=== Modify by kou 2006/10/30
//				↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    rst_unit_ck.unit_cd as rst_unit_ck,"); //ユニットコード存在チェック
				sb.append("    rst.unit_cd as rst_unit_ck,"); //ユニットコード存在チェック
//				↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    sub_class_ck.subclass_cd as sub_class_ck,"); //サブクラスコードチェック
				sb.append("    '' as sub_class_ck,"); //サブクラスコードチェック
				//===END=== Modify by kou 2006/10/30
//				↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				sb.append("    area_ck.code_cd as area_ck,"); //地区コード存在チェック
//				↑↑2006.07.12 Jiangb カスタマイズ修正↑↑

//				===BEGIN=== Modify by kou 2006/8/19
//				sb.append("    siiresaki_cd.siiresaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
				sb.append("    substr(siiresaki_ck.siiresaki_cd,1,6) as siiresaki_ck,"); //仕入先コード存在チェック
//				===END=== Modify by kou 2006/8/19
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    bland_ck.code_cd as bland_ck,"); //ブランドコード存在チェック
				sb.append("    '' as bland_ck,"); //ブランドコード存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    pb_ck.code_cd as pb_ck,"); //ＰＢ区分存在チェック
				sb.append("    fuji_syohin_ck.code_cd as fuji_syohin_ck,"); //fuji商品区分存在チェック

				sb.append("    maker_ck.code_cd as maker_ck,"); //JANメーカーコード存在チェック
				sb.append("    '' as syohin_2_cd,"); // 商品コード２存在チェック
				sb.append("    '' as size_ck,"); //サイズコード存在チェック
				sb.append("    '' as color_ck,"); //カラーコード存在チェック
				sb.append("    '' as season_ck,"); //シーズンコード存在チェック
				sb.append("    '' as yoridori_ck,"); //よりどり種類存在チェック
				sb.append("    '' as nefuda_ck,"); //TAG値札区分存在チェック
				sb.append("    '' as nefuda_ukewatasi_ck,"); //TAG値札受渡方法存在チェック
				sb.append("    '' as sozai_ck,"); //素材コード存在チェック
				sb.append("    '' as oriami_ck,"); //織り編みコード存在チェック
				sb.append("    '' as sode_ck,"); //袖丈コード存在チェック
				sb.append("    '' as age_ck,"); //年代コード存在チェック
				sb.append("    '' as pattern_ck,"); //パターンコード存在チェック
				sb.append("    pc_ck.code_cd as pc_ck,"); //PC発行区分存在チェック
				sb.append("    daisi_no_nb_ck.code_cd as daisi_no_nb_ck,"); //台紙ＮＯ存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    honbu_zai_ck.code_cd as honbu_zai_ck,");	// 本部在庫区分
				sb.append("    '' as honbu_zai_ck,");	// 本部在庫区分
				//===END=== Modify by kou 2006/10/30
				sb.append("    '' as syuzei_hokoku_ck, "); //酒税報告区分存在チェック
				//===BEGIN=== Modify by kou 2006/10/30
				//sb.append("    unit_price_tani_ck.code_cd as unit_price_tani_ck,"); //ユニットプライス・単位存在チェック
				sb.append("    '' as unit_price_tani_ck,"); //ユニットプライス・単位存在チェック
				//===END=== Modify by kou 2006/10/30
				sb.append("    '' as daicho_tenpo_ck, ");// 台帳店舗
				sb.append("    '' as daicho_honbu_ck, ");// 台帳本部
				sb.append("    '' as daicho_siiresaki_ck, ");// 台帳仕入先
//			↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    (select");
//				sb.append("        t.siiresaki_cd");
//				sb.append("    from");
//				sb.append("        r_tenbetu_siiresaki t,");
//				sb.append("        r_siiresaki s");
//				sb.append("    where  ");
//				sb.append("        t.kanri_kb = '").append(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()).append("' and");
//				sb.append("        t.kanri_cd = '").append(mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD).append("' and");
//				sb.append("        t.ten_siiresaki_kanri_cd =main.ten_siiresaki_kanri_cd and");
//				sb.append("        t.tenpo_cd = '000000' and");
//				sb.append("        t.delete_fg = '").append(DEL_FG).append("' and");
//				sb.append("        t.kanri_kb = s.kanri_kb and");
//				sb.append("        t.kanri_cd = s.kanri_cd and");
//				sb.append("        t.siiresaki_cd = s.siiresaki_cd and");
//				sb.append("        s.tosan_kb = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("' and");
//				sb.append("        s.delete_fg = '").append(DEL_FG).append("'");
//				sb.append("    ) as ten_siiresaki_kanri_ck "); //店別仕入コード存在チェック
				sb.append("    (select rts.ten_siiresaki_kanri_cd from r_tenbetu_siiresaki rts where rts.kanri_kb =  '"
						  + mst000901_KanriKbDictionary.BUMON.getCode() + "' and rts.kanri_cd = main.bumon_cd and rts.ten_siiresaki_kanri_cd =" +
								" main.ten_siiresaki_kanri_cd and rts.delete_fg = "+ DEL_FG + " group by rts.kanri_kb,rts.kanri_cd,rts.ten_siiresaki_kanri_cd) as  ten_siiresaki_kanri_ck  "); //店別仕入コード存在チェック
//			↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
				sb.append("from ( ");
				sb.append("    select tsa.*,rs.syohin_cd as s_syohin_cd  ");
				sb.append("    from");
				sb.append("    (select");
				sb.append("        tr.*,");
				sb.append("        by_no");
				sb.append("    from");
//				↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//				sb.append("        tr_toroku_syonin tts left join tr_syohin_fre tr  on ");
				sb.append("        tr_toroku_syonin tts inner join tr_syohin_fre tr  on ");
//				↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
				sb.append("        tr.torikomi_dt = tts.torikomi_dt  ");
				sb.append("        AND tr.excel_file_syubetsu = tts.excel_file_syubetsu ");
				sb.append("        AND tr.uketsuke_no =tts.uketsuke_no  ");
				sb.append("    ) tsa left join ");
				sb.append("         (select");
				sb.append("             rs.*");
				sb.append("          from");
				sb.append("             r_syohin rs,");
				sb.append("             (select");
				// ===BEGIN=== Add by kou 2006/8/21
				sb.append("					case");
				sb.append("						when tr.yuko_dt is null then '").append(MasterDT).append("'");
				sb.append("						else tr.yuko_dt ");
				sb.append("					end tmp_yuko_dt").append(" , ");
				// ===END=== Add by kou 2006/8/21
				sb.append("                 tr.* ");
				sb.append("                 ");
				sb.append("             from");
				sb.append("                 (select ");
				sb.append("                             tr1.*,");
				sb.append("                             tts.by_no as by_no ");
//				↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//				sb.append("                       from tr_toroku_syonin tts left join TR_SYOHIN_FRE  tr1 ");
				sb.append("                       from tr_toroku_syonin tts inner join TR_SYOHIN_FRE  tr1 ");
//				↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
				sb.append("                                                                             on tr1.torikomi_dt = tts.torikomi_dt ");
				sb.append("                                                                             and  tr1.excel_file_syubetsu = tts.excel_file_syubetsu ");
				sb.append("                                                                             and  tr1.uketsuke_no=tts.uketsuke_no ");
				sb.append("                                         and  tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
				sb.append("                                         and  tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
				sb.append(" ) tr  ");
				sb.append("             ) tsa");
				sb.append("       where");
				sb.append("             tsa.bumon_cd = rs.bumon_cd and");
				sb.append("             tsa.syohin_cd = rs.syohin_cd and");
				// ===BEGIN=== Modify by kou 2006/8/21
				//sb.append("             rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.yuko_dt and delete_fg='0')");
//				↓↓2006.10.17 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.tmp_yuko_dt and delete_fg='0')");
				sb.append("            rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, tsa.tmp_yuko_dt) ");
//				↑↑2006.10.17 H.Yamamoto カスタマイズ修正↑↑
				// ===END=== Modify by kou 2006/8/21
				sb.append("         ) rs on tsa.bumon_cd = rs.bumon_cd and tsa.syohin_cd = rs.syohin_cd ");
				sb.append("     ) main  ");
				sb.append("    left join r_namectf maker_ck on maker_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.JAN_MAKER_NAME, userLocal)).append("' and maker_ck.code_cd = rtrim(main.maker_cd) and maker_ck.delete_fg = '").append(DEL_FG).append("'");
//	   ===BEGIN=== Modify by kou 2006/7/28
//				  sb.append("    left join r_namectf bland_ck on bland_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_ck.code_cd = ('0000' || rtrim(main.bland_cd)) and bland_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf bland_ck on bland_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.BRAND).append("' and bland_ck.code_cd = (rtrim(main.bland_cd)) and bland_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//	   ===END=== Modify by kou 2006/7/28
//				↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				  sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()).append("' and siiresaki_ck.kanri_cd = '").append(mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD).append("' and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.delete_fg = '").append(DEL_FG).append("' and siiresaki_ck.tosan_kb = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("'");
//				sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()).append("' and siiresaki_ck.kanri_cd = '0000' and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.delete_fg = '").append(DEL_FG).append("' and siiresaki_ck.tosan_kb = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("'");
//				↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//				===BEGIN=== Modify by kou 2006/8/18
							 //sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = '0000'  and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
							 sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '")
								 .append(mst000901_KanriKbDictionary.BUMON.getCode())
								 .append("' and siiresaki_ck.kanri_cd = '0000' ")
//								↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//								 .append(" and siiresaki_ck.siiresaki_cd LIKE ")
//								 .append(" substr(main.siiresaki_cd,1,6)").append(" || '%'")
								 .append(" and (siiresaki_ck.siiresaki_cd = ").append(" main.siiresaki_cd ")
								 .append("  or  substr(siiresaki_ck.siiresaki_cd,1,6) = ").append(" rtrim(main.siiresaki_cd)) ")
//								↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
//								↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
								.append(" and siiresaki_ck.siire_system_kb in ('1','3') ")
//								↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑
								 .append(" and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '")
								 .append(DEL_FG).append("'");
//				===END=== Modify by kou 2006/8/18
//			↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf pc_ck on pc_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PC_ISSUE_DIVISION).append("' and pc_ck.code_cd = main.pc_kb and pc_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf pc_ck on pc_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PC_ISSUE_DIVISION, userLocal)).append("' and pc_ck.code_cd = '").append(mst000611_SystemKbDictionary.F.getCode()).append("'||main.pc_kb and pc_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf unit_price_tani_ck on unit_price_tani_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT_PRICE_UNIT_AMOUNT).append("' and unit_price_tani_ck.code_cd = rtrim(main.unit_price_tani_kb) and unit_price_tani_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000901_KanriKbDictionary.HINBAN.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.F.getCode() + "' and rst.unit_cd = main.unit_cd  ");
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf bumon_ck on bumon_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.SECTION).append("' and bumon_ck.code_cd = main.bumon_cd and bumon_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//			↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf unit_ck on unit_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.UNIT).append("' and unit_ck.code_cd = '" + mst000611_SystemKbDictionary.F.getCode() + "' || main.unit_cd and unit_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30
//			↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst_unit_ck on rst_unit_ck.system_kb =  '" + mst000901_KanriKbDictionary.UNIT.getCode() + "' and rst_unit_ck.unit_cd = main.unit_cd  ");
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    left join r_syohin_taikei rst_unit_ck on rst_unit_ck.system_kb =  '" + mst000611_SystemKbDictionary.F.getCode() + "' and rst_unit_ck.unit_cd = main.unit_cd  ");
				sb.append("    left join r_syohin_taikei rst on rst.system_kb =  '" + mst000611_SystemKbDictionary.F.getCode() + "' and rst.unit_cd = main.unit_cd ").append("and rst.bumon_cd = main.bumon_cd ");
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
//			↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_subclass sub_class_ck on sub_class_ck.bumon_cd = main.bumon_cd and sub_class_ck.subclass_cd = main.subclass_cd and sub_class_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30

//				↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//				  sb.append("    left join r_namectf area_ck on area_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_ck.code_cd = main.area_cd and area_ck.delete_fg = '").append(DEL_FG).append("'");

//				↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
//			↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf pb_ck on pb_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.PB_DIVISION).append("' and pb_ck.code_cd = '").append(mst000611_SystemKbDictionary.F.getCode()).append("'||main.pb_kb and pb_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf pb_ck on pb_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PB_DIVISION, userLocal)).append("' and pb_ck.code_cd = main.pb_kb and pb_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.07.27 H.Yamamoto カスタマイズ修正↓↓
//				sb.append("    left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.GOODS_DIVISION).append("' and fuji_syohin_ck.code_cd = main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
				sb.append("    left join r_namectf fuji_syohin_ck on fuji_syohin_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.GOODS_DIVISION, userLocal)).append("' and fuji_syohin_ck.code_cd = '" + mst000611_SystemKbDictionary.F.getCode() + "' || main.fuji_syohin_kb and fuji_syohin_ck.delete_fg = '").append(DEL_FG).append("'");
//			↑↑2006.07.27 H.Yamamoto カスタマイズ修正↑↑
				sb.append("    left join r_namectf daisi_no_nb_ck on daisi_no_nb_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PRICE_SEAL_KIND, userLocal)).append("' and daisi_no_nb_ck.code_cd = main.daisi_no_nb and daisi_no_nb_ck.delete_fg = '").append(DEL_FG).append("'");
				//===BEGIN=== Comment out by kou 2006/10/30
				//sb.append("    left join r_namectf honbu_zai_ck on honbu_zai_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.STOCK_DIVISION).append("' and honbu_zai_ck.code_cd = main.honbu_zai_kb and honbu_zai_ck.delete_fg = '").append(DEL_FG).append("'");
				//===END=== Comment out by kou 2006/10/30

				sb.append(" where ");
				sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
				sb.append("   and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
            }

		return sb.toString();
	}
	//===END=== Add by kou 2006/8/4

	/**
	 * 単品店マスタTRの検索用ＳＱＬの生成を行う。
	 *
	 * @param  strTorikomiDt String　　取込日
	 * @param  strUketsukeNo String　　受付ファイルNO
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectTanpinSql(String strTorikomiDt, String strUketsukeNo)
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sb = new StringBuffer();
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

		sb.append("select");
//		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
		sb.append("    main.insert_user_id as insert_user_id,"); //登録者
//		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
		sb.append("    main.torikomi_dt as torikomi_dt,"); // 取込日
		sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); // EXCELファイル種別
		sb.append("    main.uketsuke_no as uketsuke_no,"); // 受付No
		sb.append("    main.uketsuke_seq as uketsuke_seq,"); // 受付SEQNo
		sb.append("    main.syusei_kb as syusei_kb,"); // 修正区分
		sb.append("    main.bumon_cd as bumon_cd,"); //  部門コード
		sb.append("    bumon_ck.code_cd as bumon_ck,");// 部門コード存在チェック
		sb.append("    main.syohin_cd as syohin_cd,"); // 商品コード
		sb.append("    main.donyu_dt as donyu_dt,"); // 導入日
		sb.append("    main.tenpo_cd as tenpo_cd,"); // 店舗コード
		sb.append("    tenpo_ck.tenpo_cd as tenpo_ck,"); // 店舗コード存在チェック
		sb.append("    main.s_syohin_cd as s_syohin_cd,"); // 商品コード（商品マスタ）
		sb.append("    main.s_delete_fg as s_delete_fg, "); // 削除フラグ（商品マスタ）
		sb.append("    main.by_no as by_no "); // 登録票承認TR.バイヤーNo.
		sb.append("from");
		sb.append("    (select");
//		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
		sb.append("        tsa.insert_user_id as insert_user_id,");
//		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
		sb.append("        tsa.torikomi_dt as torikomi_dt,");
		sb.append("        tsa.excel_file_syubetsu as excel_file_syubetsu,");
		sb.append("        tsa.uketsuke_no as uketsuke_no,");
		sb.append("        tsa.uketsuke_seq as uketsuke_seq,");
		sb.append("        tsa.syusei_kb as syusei_kb,");
		sb.append("        tsa.bumon_cd as bumon_cd,");
		sb.append("        tsa.syohin_cd as syohin_cd,");
		sb.append("        tsa.donyu_dt as donyu_dt,");
		sb.append("        tsa.tenpo_cd as tenpo_cd,");
		sb.append("        rs.syohin_cd as s_syohin_cd,");
		sb.append("        rs.delete_fg as s_delete_fg,");
//		↓↓2006.09.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("        rs.by_no as by_no");
		sb.append("        tsa.by_no as by_no");
//		↑↑2006.09.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append("    from");
		sb.append("        (select");
		sb.append("            rs.*,");
		sb.append("            tsa.tenpo_cd as tenpo_cd1, ");
		sb.append("            tsa.by_no as by_no ");
		sb.append("         from");
		sb.append("            r_syohin rs,");
		sb.append("            (select");
		sb.append("                tr.*,");
		sb.append("                top.by_no as by_no");
		sb.append("            from  ");
//		↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("                tr_toroku_syonin top left join tr_tanpinten_toriatukai tr");
		sb.append("                tr_toroku_syonin top inner join tr_tanpinten_toriatukai tr");
//		↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
		sb.append("                on top.torikomi_dt = tr.torikomi_dt ");
		sb.append("                and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		sb.append("                and top.uketsuke_no = tr.uketsuke_no ");
		sb.append("                and tr.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
		sb.append("                and tr.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
		sb.append("            ) tsa");
		sb.append("         where");
		sb.append("            tsa.bumon_cd = rs.bumon_cd and");

//		===Begin=== Modify By Kou 2006-07-19
//			 sb.append("            tsa.syohin_cd = rs.syohin_cd and");
//			 sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.donyu_dt )");
//		TODO デイリーの場合は初回導入日と比較する必要があるが、このSQL文がおかしい。
//		取りあえず、7月21日のシナリオのために、コメントアウトした。
			 sb.append("            tsa.syohin_cd = rs.syohin_cd ");
//			 sb.append("            and rs.yuko_dt = ");
//			 sb.append("            (select max(yuko_dt) " +
//				 "from r_syohin where bumon_cd = tsa.bumon_cd" +
//				 " and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.donyu_dt )");
//		===End=== Modify By Kou 2006-07-19
//		↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
		sb.append("            and rs.yuko_dt = ");
		sb.append("            (select max(yuko_dt) " +
			"from r_syohin where bumon_cd = tsa.bumon_cd" +
			" and syohin_cd = tsa.syohin_cd)");
//		↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑

		sb.append("        ) rs right outer join ");
		sb.append("        (select");
		sb.append("            tr.*");
//		↓↓2006.09.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append("            ,top.by_no as by_no");
//		↑↑2006.09.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append("        from  ");
//		↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("            tr_toroku_syonin top left join tr_tanpinten_toriatukai tr");
		sb.append("            tr_toroku_syonin top inner join tr_tanpinten_toriatukai tr");
//		↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
		sb.append("            on top.torikomi_dt = tr.torikomi_dt ");
		sb.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		sb.append("            and top.uketsuke_no = tr.uketsuke_no ");
		sb.append("        ) tsa on ");
		sb.append("        tsa.tenpo_cd = rs.tenpo_cd1 and");
		sb.append("        tsa.bumon_cd = rs.bumon_cd and");
		sb.append("        tsa.syohin_cd = rs.syohin_cd");
		sb.append("    ) main");
		sb.append("    left join r_tenpo tenpo_ck on tenpo_ck.tenpo_cd = main.tenpo_cd and tenpo_kb = '").append(mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode()).append("' and heiten_dt >= '").append(MasterDT).append("' and tenpo_ck.delete_fg = '").append(DEL_FG).append("'");
		sb.append("    left join r_namectf bumon_ck on bumon_ck.code_cd = main.bumon_cd and SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' and bumon_ck.delete_fg = '").append(DEL_FG).append("' ");

		sb.append("where ");
		sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
		sb.append("  and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");


		return sb.toString();
	}

	/**
	 * 店別商品例外マスタTRの検索用ＳＱＬの生成を行う。
	 *
	 * @param  strTorikomiDt String　　取込日
	 * @param  strUketsukeNo String　　受付ファイルNO
	 * @return String 生成されたＳＱＬ
	 */
//	↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
//	public String getSelectReigaiSql(String strTorikomiDt, String strUketsukeNo)
	public String getSelectReigaiSql(String strTorikomiDt, String strUketsukeNo, String strSyubetu)
//	↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sb = new StringBuffer();
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

		sb.append("select distinct");
//		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
		sb.append("    main.insert_user_id as insert_user_id,"); //登録者
//		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
		sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
		sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //EXCELファイル種別
		sb.append("    main.uketsuke_no as uketsuke_no,"); //受付No
		sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付SEQNo
		sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
		sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
		sb.append("    bumon_ck.code_cd as bumon_ck,"); //部門コード2（品種、部門チェック）
		sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
		sb.append("    main.tenpo_cd as tenpo_cd,"); //店舗コード
		sb.append("    tenpo_ck.tenpo_cd as tenpo_ck,"); //店舗コード存在チェック
		sb.append("    main.yuko_dt as yuko_dt,"); //有効日
		sb.append("    main.ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
		sb.append("    main.ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
		sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原単価
		sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売単価
		sb.append("    main.max_hachutani_qt as max_hachutani_qt,"); //最大発注数
		sb.append("    main.eos_kb as eos_kb,"); //EOS区分
		sb.append("    eos_ck.code_cd as eos_ck,"); //EOS区分存在チェック
		sb.append("    main.siiresaki_cd as siiresaki_cd,"); //仕入先コード
		// ===BEGIN=== Modify by kou 2006/8/19
		//sb.append("    siiresaki_ck.siiresaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
		sb.append("    substr(siiresaki_ck.siiresaki_cd,1,6) as siiresaki_ck,"); //仕入先コード存在チェック
		// ===END=== Modify by kou 2006/8/19

//		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//		sb.append("    main.area_cd as area_cd,"); //地区コード
//		sb.append("    area_ck.code_cd as area_ck,"); //地区コード存在チェック
//		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
		sb.append("    main.bin_1_kb as bin_1_kb,"); //①便区分
		sb.append("    main.bin_2_kb as bin_2_kb,"); //②便区分
		sb.append("    main.yusen_bin_kb as yusen_bin_kb,"); //優先便区分
		sb.append("    main.s_syohin_cd as s_syohin_cd,"); //商品コード（商品マスタ）
		sb.append("    main.s_gyosyu_kb as s_gyosyu_kb,"); //業種区分（商品マスタ）
		sb.append("    main.s_ten_hachu_st_dt as s_ten_hachu_st_dt,"); //店舗発注開始日（商品マスタ）
		sb.append("    main.s_ten_hachu_ed_dt as s_ten_hachu_ed_dt,"); //店舗発注終了日（商品マスタ）
		sb.append("    replace(to_char(main.s_gentanka_vl),',') as s_gentanka_vl,"); //原単価（商品マスタ）
		sb.append("    replace(to_char(main.s_baitanka_vl),',') as s_baitanka_vl,"); //売単価（商品マスタ）
		sb.append("    main.by_no as by_no, "); //バイヤーNO
		sb.append("    main.s_delete_fg as s_delete_fg "); //削除フラグ（商品マスタ）
		sb.append("from");
		sb.append("    (select");
//		↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
		sb.append("        tsa.insert_user_id as insert_user_id,");
//		↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
		sb.append("        tsa.torikomi_dt as torikomi_dt,");
		sb.append("        tsa.excel_file_syubetsu as excel_file_syubetsu,");
		sb.append("        tsa.uketsuke_no as uketsuke_no,");
		sb.append("        tsa.uketsuke_seq as uketsuke_seq,");
		sb.append("        tsa.syusei_kb as syusei_kb,");
		sb.append("        coalesce(tsa.bumon_cd,rs.bumon_cd) as bumon_cd,");
		sb.append("        tsa.syohin_cd as syohin_cd,");
		sb.append("        tsa.tenpo_cd as tenpo_cd,");
		sb.append("        tsa.yuko_dt as yuko_dt,");
		sb.append("        tsa.ten_hachu_st_dt as ten_hachu_st_dt,");
		sb.append("        tsa.ten_hachu_ed_dt as ten_hachu_ed_dt,");
		sb.append("        tsa.gentanka_vl as gentanka_vl,");
		sb.append("        tsa.baitanka_vl as baitanka_vl,");
		sb.append("        tsa.max_hachutani_qt as max_hachutani_qt,");
		sb.append("        tsa.eos_kb as eos_kb,");
		sb.append("        tsa.siiresaki_cd as siiresaki_cd,");
//		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//		sb.append("        tsa.area_cd as area_cd,");
//		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑
		sb.append("        tsa.bin_1_kb as bin_1_kb,");
		sb.append("        tsa.bin_2_kb as bin_2_kb,");
		sb.append("        tsa.yusen_bin_kb as yusen_bin_kb,");
		sb.append("        tsa.by_no as by_no,");
		sb.append("        rs.syohin_cd as s_syohin_cd,");
		sb.append("        rs.gyosyu_kb as s_gyosyu_kb,");
		sb.append("        rs.ten_hachu_st_dt as s_ten_hachu_st_dt,");
		sb.append("        rs.ten_hachu_ed_dt as s_ten_hachu_ed_dt,");
		sb.append("        rs.gentanka_vl as s_gentanka_vl,");
		sb.append("        rs.baitanka_vl as s_baitanka_vl,");
		sb.append("        rs.delete_fg as s_delete_fg");
		sb.append("    from");
//		↓↓2006.09.11 H.Yamamoto カスタマイズ修正↓↓
		sb.append("        (select");
		sb.append("                tr1.*,ts.by_no");
		sb.append("         from");
		sb.append("               tr_toroku_syonin ts");
		sb.append("                inner join tr_tensyohin_reigai tr1 on ts.torikomi_dt = tr1.torikomi_dt and ts.excel_file_syubetsu = tr1.excel_file_syubetsu and ts.uketsuke_no = tr1.uketsuke_no ");
		sb.append("                     and tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
		sb.append("                     and tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
		sb.append("        ) tsa left join");
		sb.append("        (select");
		sb.append("            rs.*,");
		sb.append("            tsa.tenpo_cd as tenpo_cd");
		sb.append("         from");
		sb.append("            r_syohin rs,");
		sb.append("                (select");
		sb.append("                    tr1.*,ts.by_no,");
		sb.append("case  when tr1.yuko_dt is null then '")
		  .append(DateChanger.addDate(MasterDT, 1))
		  .append("'	  else tr1.yuko_dt end tmp_yuko_dt");
		sb.append("                from");
		sb.append("                   tr_toroku_syonin ts");
		sb.append("                    inner join tr_tensyohin_reigai tr1 on ts.torikomi_dt = tr1.torikomi_dt and ts.excel_file_syubetsu = tr1.excel_file_syubetsu and ts.uketsuke_no = tr1.uketsuke_no ");
		sb.append("                     and tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
		sb.append("                     and tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
		sb.append("            ) tsa");
		sb.append("         where");
		sb.append("       	   tsa.bumon_cd = rs.bumon_cd and");
		sb.append("            tsa.syohin_cd = rs.syohin_cd and");
//		sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = rs.bumon_cd and syohin_cd = rs.syohin_cd and yuko_dt <= tsa.tmp_yuko_dt )");
		sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = rs.bumon_cd and syohin_cd = rs.syohin_cd )");
		sb.append("        ) rs");
		sb.append("       on tsa.bumon_cd = rs.bumon_cd and");
		sb.append("           tsa.tenpo_cd = rs.tenpo_cd and");
		sb.append("           tsa.syohin_cd = rs.syohin_cd");
		sb.append("    ) main");
//		sb.append("        (select");
//		sb.append("            tr.*,");
//		sb.append("            rst.bumon_cd as bumon_ck");
//		sb.append("        from");
//		sb.append("            (select");
//		sb.append("            		tr1.*,ts.toroku_syonin_fg ,ts.by_no");
//		sb.append("            	from");
//		sb.append("           		tr_toroku_syonin ts");
//		sb.append("            		left join tr_tensyohin_reigai tr1 on ts.torikomi_dt = tr1.torikomi_dt and ts.excel_file_syubetsu = tr1.excel_file_syubetsu and ts.uketsuke_no = tr1.uketsuke_no and tr1.uketsuke_seq ="+ mst006501_BySyoninFgDictionary.SYONIN.getCode());
//		sb.append("            ) tr");
//		sb.append("            left join r_syohin_taikei rst on (rst.bumon_cd = tr.bumon_cd) and rst.system_kb = '1' ");
//		sb.append("        ) tsa left join");
//		sb.append("        (select");
//		sb.append("            rs.*,");
//		sb.append("            tsa.tenpo_cd as tenpo_cd");
//		sb.append("         from");
//		sb.append("            r_syohin rs,");
//		sb.append("            (select");
//		sb.append("                tr.*,");
//		sb.append("                rst.bumon_cd as bumon_ck");
//		sb.append("            from");
//		sb.append("            		(select");
//		sb.append("            			tr1.*,ts.toroku_syonin_fg ,ts.by_no");
//		sb.append("            		from");
//		sb.append("           			tr_toroku_syonin ts");
//		sb.append("            			left join tr_tensyohin_reigai tr1 on ts.torikomi_dt = tr1.torikomi_dt and ts.excel_file_syubetsu = tr1.excel_file_syubetsu and ts.uketsuke_no = tr1.uketsuke_no and tr1.uketsuke_seq ="+ mst006501_BySyoninFgDictionary.SYONIN.getCode());
//		sb.append("                     and tr1.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
//		sb.append("                     and tr1.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
//		sb.append("            ) tr");
//		sb.append("            left join r_syohin_taikei rst on (rst.bumon_cd = tr.bumon_cd) and rst.system_kb = '1' ");
//		sb.append("            ) tsa");
//		sb.append("         where");
//		sb.append("            tsa.bumon_ck = rs.bumon_cd and");
//		sb.append("            tsa.syohin_cd = rs.syohin_cd and");
//		sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = rs.bumon_cd and syohin_cd = rs.syohin_cd and yuko_dt <= tsa.yuko_dt )");
//		sb.append("        ) rs");
//		sb.append("       on tsa.tenpo_cd = rs.tenpo_cd and");
//		sb.append("           tsa.bumon_ck = rs.bumon_cd and");
//		sb.append("           tsa.syohin_cd = rs.syohin_cd");
//		sb.append("    ) main");
//		↑↑2006.09.11 H.Yamamoto カスタマイズ修正↑↑
		sb.append("    left join r_namectf bumon_ck on bumon_ck.code_cd = main.bumon_cd and bumon_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' and bumon_ck.delete_fg = '").append(DEL_FG).append("' ");
		sb.append("    left join r_tenpo tenpo_ck on tenpo_ck.tenpo_cd = main.tenpo_cd and tenpo_ck.tenpo_kb = '").append(mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode()).append("' and tenpo_ck.heiten_dt >= '").append(MasterDT).append("' and tenpo_ck.delete_fg = '").append(DEL_FG).append("'");
		sb.append("    left join r_namectf eos_ck on eos_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EOS_DIVISION, userLocal)).append("' and eos_ck.code_cd = main.eos_kb and eos_ck.delete_fg = '").append(DEL_FG).append("'");
//		↓↓2006.07.19 H.Yamamoto カスタマイズ・バグ修正↓↓
//		sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = main.bumon_cd and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.delete_fg = '").append(DEL_FG).append("' and siiresaki_ck.tosan_kb = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("'");
//		sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = '0000' and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.delete_fg = '").append(DEL_FG).append("' and siiresaki_ck.tosan_kb = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("'");
//		↑↑2006.07.19 H.Yamamoto カスタマイズ・バグ修正↑↑
//		↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
////		===BEGIN=== Modify by kou 2006/8/18
//					 //sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' and siiresaki_ck.kanri_cd = '0000'  and siiresaki_ck.siiresaki_cd = main.siiresaki_cd and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '").append(DEL_FG).append("'");
//					 sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '")
//						 .append(mst000901_KanriKbDictionary.BUMON.getCode())
//						 .append("' and siiresaki_ck.kanri_cd = '0000' ")
////						↓↓2006.09.14 H.Yamamoto カスタマイズ修正↓↓
////						 .append(" and siiresaki_ck.siiresaki_cd LIKE '")
////						 .append(" substr(main.siiresaki_cd,1,6)").append("%'")
//						 .append(" and siiresaki_ck.siiresaki_cd LIKE ")
//						 .append(" substr(main.siiresaki_cd,1,6)").append(" || '%'")
////						↑↑2006.09.14 H.Yamamoto カスタマイズ修正↑↑
//						 .append(" and siiresaki_ck.siire_system_kb in ('1','3') ")
//						 .append(" and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '")
//						 .append(DEL_FG).append("'");
////		===END=== Modify by kou 2006/8/18
		if("A08".equals(strSyubetu)){
			sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '")
				.append(mst000901_KanriKbDictionary.BUMON.getCode())
				.append("' and siiresaki_ck.kanri_cd = '0000' ")
//			↓↓2006.12.04 H.Yamamoto カスタマイズ修正↓↓
//				.append(" and siiresaki_ck.siiresaki_cd LIKE ")
//				.append(" substr(main.siiresaki_cd,1,6)").append(" || '%'")
				.append(" and (siiresaki_ck.siiresaki_cd = ").append(" main.siiresaki_cd ")
				.append("  or  substr(siiresaki_ck.siiresaki_cd,1,6) = ").append(" rtrim(main.siiresaki_cd)) ")
//			↑↑2006.12.04 H.Yamamoto カスタマイズ修正↑↑
				.append(" and siiresaki_ck.siire_system_kb in ('2','3') ")
				.append(" and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '")
				.append(DEL_FG).append("'");
		} else {
			sb.append("    left join r_siiresaki siiresaki_ck on siiresaki_ck.kanri_kb = '")
				.append(mst000901_KanriKbDictionary.BUMON.getCode())
				.append("' and siiresaki_ck.kanri_cd = '0000' ")
//			↓↓2006.12.04 H.Yamamoto カスタマイズ修正↓↓
//				.append(" and siiresaki_ck.siiresaki_cd LIKE ")
//				.append(" substr(main.siiresaki_cd,1,6)").append(" || '%'")
				.append(" and (siiresaki_ck.siiresaki_cd = ").append(" main.siiresaki_cd ")
				.append("  or  substr(siiresaki_ck.siiresaki_cd,1,6) = ").append(" rtrim(main.siiresaki_cd)) ")
//			↑↑2006.12.04 H.Yamamoto カスタマイズ修正↑↑
				.append(" and siiresaki_ck.siire_system_kb in ('1','3') ")
				.append(" and siiresaki_ck.tosan_kb <> '9'  and siiresaki_ck.delete_fg = '")
				.append(DEL_FG).append("'");
		}
//		↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑

//		↓↓2006.07.12 Jiangb カスタマイズ修正↓↓
//		sb.append("    left join r_namectf area_ck on area_ck.syubetu_no_cd = '").append(mst000101_ConstDictionary.AREA_CODE).append("' and area_ck.code_cd = main.area_cd and area_ck.delete_fg = '").append(DEL_FG).append("'");
//		↑↑2006.07.12 Jiangb カスタマイズ修正↑↑

//		↓↓2006.09.14 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("where ");
		sb.append(" where ");
//		↑↑2006.09.14 H.Yamamoto カスタマイズ修正↑↑
		sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
		sb.append("  and main.uketsuke_no = '"+ conv.convertWhereString(strUketsukeNo) +"'");

		return sb.toString();
	}

	/**
	 * 初回導入マスタTRの検索用ＳＱＬの生成を行う。
	 *
	 * @param  strTorikomiDt String　　取込日
	 * @param  strUketsukeNo String　　受付ファイルNO
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSyokaiSql(String strTorikomiDt, String strUketsukeNo)
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sb = new StringBuffer();
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

		sb.append("select");
//		↓↓2006.07.19 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	   main.by_no as by_no, "); //バイヤーNO
//		↑↑2006.07.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append("    main.torikomi_dt as torikomi_dt,"); //取込日
		sb.append("    main.excel_file_syubetsu as excel_file_syubetsu,"); //EXCELファイル種別
		sb.append("    main.uketsuke_no as uketsuke_no,"); //受付No
		sb.append("    main.uketsuke_seq as uketsuke_seq,"); //受付SEQNo
		sb.append("    main.syusei_kb as syusei_kb,"); //修正区分
		sb.append("    main.hachuno_cd as hachuno_cd,"); //発注No
		sb.append("    main.bumon_cd as bumon_cd,"); //部門コード
		sb.append("    bumon_ck.code_cd as bumon_ck,"); //部門コード存在チェック
		sb.append("    main.syohin_cd as syohin_cd,"); //商品コード
		sb.append("    main.tenpo_cd as tenpo_cd,"); //店舗コード
		sb.append("    tenpo_ck.tenpo_cd as tenpo_ck,"); //店舗コード存在チェック
		sb.append("    main.theme_cd as theme_cd,"); //テーマコード
		sb.append("    theme_ck.code_cd as theme_ck,"); //テーマコード存在チェック
		sb.append("    main.suryo_qt as suryo_qt,"); //数量
		sb.append("    main.hachutani_qt as hachutani_qt,"); //発注単位
		sb.append("    main.hatyu_dt as hatyu_dt,"); //発注日
		sb.append("    main.nohin_dt as nohin_dt,"); //納品日
		sb.append("    main.hanbai_st_dt as hanbai_st_dt,"); //販売開始日
		sb.append("    main.hanbai_ed_dt as hanbai_ed_dt,"); //販売終了日
		sb.append("    replace(main.gentanka_vl,',') as gentanka_vl,"); //原単価
		sb.append("    replace(main.baitanka_vl,',') as baitanka_vl,"); //売単価
		sb.append("    main.s_syohin_cd as s_syohin_cd,"); //商品コード（商品マスタ）
		sb.append("    main.s_gyosyu_kb as s_gyosyu_kb,"); //業種区分（商品マスタ）
		sb.append("    main.s_hachu_teisi_kb as s_hachu_teisi_kb,"); //発注停止区分（商品マスタ）
		sb.append("    main.s_mst_siyofuka_kb as s_mst_siyofuka_kb,"); //マスタ使用不可区分（商品マスタ）
		sb.append("    main.s_delete_fg as s_delete_fg, "); //削除フラグ（商品マスタ）
		sb.append("	main.t_insert_user_id as insert_user_id, "); //作成者ID
		sb.append("	main.t_update_user_id as update_user_id ");//更新者ID
		sb.append("from");
		sb.append("    (select");
//		↓↓2006.07.19 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	        tsa.by_no as by_no, ");
//		↑↑2006.07.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append("         tsa.torikomi_dt as torikomi_dt,");
		sb.append("         tsa.excel_file_syubetsu as excel_file_syubetsu,");
		sb.append("         tsa.uketsuke_no as uketsuke_no,");
		sb.append("         tsa.uketsuke_seq as uketsuke_seq,");
		sb.append("         tsa.syusei_kb as syusei_kb,");
		sb.append("         tsa.hachuno_cd as hachuno_cd,");
		sb.append("         tsa.bumon_cd as bumon_cd,");
		sb.append("         tsa.syohin_cd as syohin_cd,");
		sb.append("         tsa.tenpo_cd as tenpo_cd,");
		sb.append("         tsa.theme_cd as theme_cd,");
		sb.append("         tsa.suryo_qt as suryo_qt,");
		sb.append("         tsa.hachutani_qt as hachutani_qt,");
		sb.append("         tsa.gentanka_vl as gentanka_vl,");
		sb.append("         rs.gentanka_vl as s_gentanka_vl,");
		sb.append("         tsa.baitanka_vl as baitanka_vl,");
		sb.append("         rs.baitanka_vl as s_baitanka_vl,");
		sb.append("         tsa.hatyu_dt as hatyu_dt,");
		sb.append("         tsa.nohin_dt as nohin_dt,");
		sb.append("         tsa.hanbai_st_dt as hanbai_st_dt,");
		sb.append("         tsa.hanbai_ed_dt as hanbai_ed_dt,");
		sb.append("         rs.syohin_cd as s_syohin_cd,");
		sb.append("         rs.gyosyu_kb as s_gyosyu_kb,");
		sb.append("         rs.hachu_teisi_kb as s_hachu_teisi_kb,");
		sb.append("         rs.mst_siyofuka_kb as s_mst_siyofuka_kb,");
		sb.append("         rs.delete_fg as s_delete_fg,");
		sb.append("         tsa.t_insert_user_id as t_insert_user_id,");
		sb.append("         tsa.t_update_user_id as t_update_user_id");
		sb.append("    from");
		sb.append("        (select");
		sb.append("            tr.*");
		sb.append("        from");
		sb.append("            (select");
		sb.append("                trsk.*,");
//		↓↓2006.07.19 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	               trs.by_no as by_no, ");
//		↑↑2006.07.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append("                trs.by_no as t_insert_user_id,");
		sb.append("                trs.by_no as t_update_user_id");
		sb.append("             from");
//		↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//		sb.append("                tr_toroku_syonin trs left join tr_syokaidonyu trsk on");
		sb.append("                tr_toroku_syonin trs inner join tr_syokaidonyu trsk on");
//		↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
		sb.append("                    trs.torikomi_dt = trsk.torikomi_dt and");
		sb.append("                    trs.excel_file_syubetsu = trsk.excel_file_syubetsu and");
		sb.append("                    trs.uketsuke_no = trsk.uketsuke_no");
		sb.append("            ) tr");
		sb.append("        ) tsa left join");
		sb.append("        (select");
		sb.append("            rs.*,");
		sb.append("            tsa.torikomi_dt,");
		sb.append("            tsa.excel_file_syubetsu,");
		sb.append("            tsa.uketsuke_no,");
		sb.append("            tsa.uketsuke_seq");
		sb.append("        from");
		sb.append("            r_syohin rs,");
		sb.append("            (select");
		sb.append("                tr.*");
		sb.append("            from");
		sb.append("                (select");
		sb.append("                    trsk.*");
		sb.append("                 from");
//		↓↓2006.10.17 H.Yamamoto レスポンス対策修正↓↓
//		sb.append("                    tr_toroku_syonin trs left join tr_syokaidonyu trsk on");
		sb.append("                    tr_toroku_syonin trs inner join tr_syokaidonyu trsk on");
//		↑↑2006.10.17 H.Yamamoto レスポンス対策修正↑↑
		sb.append("                        trs.torikomi_dt = trsk.torikomi_dt and");
		sb.append("                        trs.excel_file_syubetsu = trsk.excel_file_syubetsu and");
		sb.append("                        trs.uketsuke_no = trsk.uketsuke_no");
		sb.append("                        and trsk.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
		sb.append("                        and trsk.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");
		sb.append("                ) tr ");
		sb.append("            ) tsa");
		sb.append("        where");
		sb.append("            tsa.bumon_cd = rs.bumon_cd and");
		sb.append("            tsa.syohin_cd = rs.syohin_cd and");
//		↓↓2006.09.11 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd and yuko_dt <= tsa.hatyu_dt)");
		sb.append("            rs.yuko_dt = (select max(yuko_dt) from r_syohin where bumon_cd = tsa.bumon_cd and syohin_cd = tsa.syohin_cd)");
//		↑↑2006.09.11 H.Yamamoto カスタマイズ修正↑↑
		sb.append("        ) rs");
		sb.append("    on(");
		sb.append("        tsa.bumon_cd = rs.bumon_cd and");
		sb.append("        tsa.syohin_cd = rs.syohin_cd and");
		sb.append("        tsa.torikomi_dt = rs.torikomi_dt and");
		sb.append("        tsa.excel_file_syubetsu = rs.excel_file_syubetsu and");
		sb.append("        tsa.uketsuke_no = rs.uketsuke_no and");
		sb.append("        tsa.uketsuke_seq = rs.uketsuke_seq)");
		sb.append("    ) main");
		sb.append("    left join r_namectf bumon_ck on bumon_ck.code_cd = main.bumon_cd and bumon_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' and bumon_ck.delete_fg = '").append(DEL_FG).append("' ");
		sb.append("    left join r_tenpo tenpo_ck on tenpo_ck.tenpo_cd = main.tenpo_cd and tenpo_ck.tenpo_kb = '").append(mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode()).append("' and tenpo_ck.heiten_dt >= '").append(MasterDT).append("' and tenpo_ck.delete_fg = '").append(DEL_FG).append("'");
		sb.append("    left join r_namectf theme_ck on theme_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.THEME_DIVISION, userLocal)).append("' and theme_ck.code_cd = '0022' || rtrim(main.theme_cd) and theme_ck.delete_fg = '").append(DEL_FG).append("'");

		sb.append("where ");
		sb.append("      main.torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
		sb.append("  and main.uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");

		return sb.toString();
	}

	/**
	 * 処理状態と最低最高値入率、最大売価単価の更新ＳＱＬの生成を行う。
	 *
	 * @param  strTorikomiDt String
	 * @param  strUketsukeNo String
	 * @param  blKeisanFlg   boolean
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateKekkaSql(String strTorikomiDt, String strUketsukeNo,boolean blKeisanFlg) {

		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append("update tr_toroku_syonin set");
		sb.append("    syori_jyotai_fg = ? ,");
		sb.append("    excel_syohin_kb = ? ,");
		sb.append("    excel_tanpin_kb = ? ,");
		sb.append("    excel_reigai_kb = ? ,");
		sb.append("    excel_syokai_kb = ? ,");
		sb.append("    toroku_syonin_ts = ? ");
		// 当該変数に値がセットされた時のみ下記の項目を更新する
		if (blKeisanFlg == true) {
			sb.append("    ,min_neire_rt = ? ,");
			sb.append("    max_neire_rt = ? ,");
			sb.append("    max_uritanka_vl = ? ");
		}
		sb.append("where ");
		sb.append("      torikomi_dt = '"+ conv.convertWhereString(strTorikomiDt) +"'");
		sb.append("  and uketsuke_no =  '"+ conv.convertWhereString(strUketsukeNo) +"'");

		return sb.toString();
	}

	/**
	 * TRテーブルの検索用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectAlmSql(String torikomiDt, String uketsukeNo) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '01' sheet_syubetsu,  ");
		sb.append("	a08.torikomi_dt torikomi_dt, ");
		sb.append("	a08.uketsuke_no uketsuke_no, ");
		sb.append("	a08.bumon_cd bumon_cd, ");
		sb.append("	a08.insert_user_id insert_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_a08 a08 ");
		sb.append("   where  ");
		sb.append("     a08.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     a08.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '01' sheet_syubetsu,  ");
		sb.append("	a07.torikomi_dt torikomi_dt, ");
		sb.append("	a07.uketsuke_no uketsuke_no, ");
		sb.append("	a07.bumon_cd bumon_cd, ");
		sb.append("	a07.insert_user_id insert_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_a07 a07 ");
		sb.append("   where  ");
		sb.append("     a07.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     a07.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '01' sheet_syubetsu,  ");
		sb.append("	gro.torikomi_dt torikomi_dt, ");
		sb.append("	gro.uketsuke_no uketsuke_no, ");
		sb.append("	gro.bumon_cd bumon_cd, ");
		sb.append("	gro.insert_user_id insert_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_gro gro ");
		sb.append("   where  ");
		sb.append("     gro.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     gro.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '01' sheet_syubetsu,  ");
		sb.append("	fre.torikomi_dt torikomi_dt, ");
		sb.append("	fre.uketsuke_no uketsuke_no, ");
		sb.append("	fre.bumon_cd bumon_cd, ");
		sb.append("	fre.insert_user_id insert_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_fre fre ");
		sb.append("   where  ");
		sb.append("     fre.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     fre.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '02' sheet_syubetsu,  ");
		sb.append("	tanpin.torikomi_dt torikomi_dt, ");
		sb.append("	tanpin.uketsuke_no uketsuke_no, ");
		sb.append("	tanpin.bumon_cd bumon_cd, ");
		sb.append("	tanpin.insert_user_id insert_user_id ");
		sb.append(" from ");
		sb.append("     tr_tanpinten_toriatukai tanpin  ");
		sb.append("   where  ");
		sb.append("     tanpin.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     tanpin.uketsuke_no = '"+ uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '03' sheet_syubetsu,  ");
		sb.append("	reigai.torikomi_dt torikomi_dt, ");
		sb.append("	reigai.uketsuke_no uketsuke_no, ");
		sb.append("	reigai.bumon_cd bumon_cd, ");
		sb.append("	reigai.insert_user_id insert_user_id ");
		sb.append(" from ");
		sb.append("     tr_tensyohin_reigai reigai  ");
		sb.append("   where  ");
		sb.append("     reigai.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     reigai.uketsuke_no = '"+ uketsukeNo +"' ");
		sb.append(" union ");
		sb.append(" select  ");
		sb.append(" '04' sheet_syubetsu,  ");
		sb.append("	syo.torikomi_dt torikomi_dt, ");
		sb.append("	syo.uketsuke_no uketsuke_no, ");
		sb.append("	syo.bumon_cd bumon_cd, ");
		sb.append("	syo.insert_user_id insert_user_id ");
		sb.append(" from ");
		sb.append("     tr_syokaidonyu syo  ");
		sb.append("   where  ");
		sb.append("     syo.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     syo.uketsuke_no = '"+ uketsukeNo +"' ");

		return sb.toString();
	}

//	↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//	/**
//	 * TRテーブル（店指示）のCSV用ＳＱＬの生成を行う。
//	 *
//	 * @param  torikomiDt String
//	 * @param  uketsukeNo String
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getSelectTanpinCsvSql(String torikomiDt, String uketsukeNo) {
//
//		StringBuffer sb = new StringBuffer();
//
//		sb.append(" select  ");
//		sb.append(" '02' sheet_syubetsu,  ");
//		sb.append("	tanpin.torikomi_dt torikomi_dt, ");
//		sb.append("	tanpin.excel_file_syubetsu excel_file_syubetsu, ");
//		sb.append("	tanpin.uketsuke_no uketsuke_no, ");
//		sb.append("	tanpin.uketsuke_seq uketsuke_seq, ");
//		sb.append("	tanpin.bumon_cd bumon_cd, ");
//		sb.append("	tanpin.syohin_cd syohin_cd, ");
//		sb.append("	tanpin.donyu_dt donyu_dt, ");
//		sb.append("	tanpin.tenpo_cd tenpo_cd, ");
//		sb.append("	tanpin.syusei_kb syusei_kb, ");
//		sb.append("	tanpin.syohin_gyo_no syohin_gyo_no, ");
//		sb.append("	tanpin.sakusei_gyo_no sakusei_gyo_no, ");
//		sb.append("	tanpin.mst_mainte_fg mst_mainte_fg, ");
//		sb.append("	tanpin.alarm_make_fg alarm_make_fg, ");
//		sb.append("	tanpin.insert_ts insert_ts, ");
//		sb.append("	tanpin.insert_user_id insert_user_id, ");
//		sb.append("	tanpin.update_ts update_ts, ");
//		sb.append("	tanpin.update_user_id update_user_id ");
//		sb.append(" from ");
//		sb.append("     tr_tanpinten_toriatukai tanpin  ");
//		sb.append("   where  ");
//		sb.append("     tanpin.torikomi_dt = '"+ torikomiDt +"' ");
//		sb.append("     and  ");
//		sb.append("     tanpin.uketsuke_no = '"+ uketsukeNo +"' ");
//		sb.append(" order by ");
//		sb.append("     tanpin.sakusei_gyo_no,  ");
//		sb.append("     tanpin.uketsuke_seq  ");
//
//		return sb.toString();
//	}
//
//	/**
//	 * TRテーブル（本部投入）のCSV用ＳＱＬの生成を行う。
//	 *
//	 * @param  torikomiDt String
//	 * @param  uketsukeNo String
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getSelectSyokaiCsvSql(String torikomiDt, String uketsukeNo) {
//
//		StringBuffer sb = new StringBuffer();
//
//		sb.append(" select  ");
//		sb.append(" '04' sheet_syubetsu,  ");
//		sb.append("	syo.torikomi_dt torikomi_dt, ");
//		sb.append("	syo.excel_file_syubetsu excel_file_syubetsu, ");
//		sb.append("	syo.uketsuke_no uketsuke_no, ");
//		sb.append("	syo.uketsuke_seq uketsuke_seq, ");
//		sb.append("	syo.hachuno_cd hachuno_cd, ");
//		sb.append("	syo.bumon_cd bumon_cd, ");
//		sb.append("	syo.syohin_cd syohin_cd, ");
//		sb.append("	syo.theme_cd theme_cd, ");
//		sb.append("	syo.hatyu_dt hatyu_dt, ");
//		sb.append("	syo.nohin_dt nohin_dt, ");
//		sb.append("	syo.hanbai_st_dt hanbai_st_dt, ");
//		sb.append("	syo.hanbai_ed_dt hanbai_ed_dt, ");
//		sb.append("	syo.gentanka_vl gentanka_vl, ");
//		sb.append("	syo.baitanka_vl baitanka_vl, ");
//		sb.append("	syo.hachutani_qt hachutani_qt, ");
//		sb.append("	syo.tenpo_cd tenpo_cd, ");
//		sb.append("	syo.suryo_qt suryo_qt, ");
//		sb.append("	syo.syusei_kb syusei_kb, ");
//		sb.append("	syo.syohin_gyo_no syohin_gyo_no, ");
//		sb.append("	syo.sakusei_gyo_no sakusei_gyo_no, ");
//		sb.append("	syo.mst_mainte_fg mst_mainte_fg, ");
//		sb.append("	syo.alarm_make_fg alarm_make_fg, ");
//		sb.append("	syo.insert_ts insert_ts, ");
//		sb.append("	syo.insert_user_id insert_user_id, ");
//		sb.append("	syo.update_ts update_ts, ");
//		sb.append("	syo.update_user_id update_user_id ");
//		sb.append(" from ");
//		sb.append("     tr_syokaidonyu syo  ");
//		sb.append("   where  ");
//		sb.append("     syo.torikomi_dt = '"+ torikomiDt +"' ");
//		sb.append("     and  ");
//		sb.append("     syo.uketsuke_no = '"+ uketsukeNo +"' ");
//		sb.append(" order by ");
//		sb.append("     syo.sakusei_gyo_no,  ");
//		sb.append("     syo.uketsuke_seq  ");
//
//		return sb.toString();
//	}
//	↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑

//	↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * TRテーブル（タグ）のCSV用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectA08CsvSql(String torikomiDt, String uketsukeNo) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '01' a08_sheet_syubetsu,  ");
		sb.append("	a08.torikomi_dt a08_torikomi_dt, ");
		sb.append("	a08.excel_file_syubetsu a08_excel_file_syubetsu, ");
		sb.append("	a08.uketsuke_no a08_uketsuke_no, ");
		sb.append("	a08.uketsuke_seq a08_uketsuke_seq, ");
		sb.append("	a08.bumon_cd a08_bumon_cd, ");
		sb.append("	a08.unit_cd a08_unit_cd, ");
		sb.append("	a08.haifu_cd a08_haifu_cd, ");
		sb.append("	a08.subclass_cd a08_subclass_cd, ");
		sb.append("	a08.syohin_cd a08_syohin_cd, ");
		sb.append("	a08.yuko_dt a08_yuko_dt, ");
		sb.append("	a08.hinmei_kanji_na a08_hinmei_kanji_na, ");
		sb.append("	a08.hinmei_kana_na a08_hinmei_kana_na, ");
		sb.append("	a08.size_cd a08_size_cd, ");
		sb.append("	a08.color_cd a08_color_cd, ");
		sb.append("	a08.gentanka_vl a08_gentanka_vl, ");
		sb.append("	a08.baitanka_vl a08_baitanka_vl, ");
		sb.append("	a08.keshi_baika_vl a08_keshi_baika_vl, ");
		sb.append("	a08.tokubetu_genka_vl a08_tokubetu_genka_vl, ");
		sb.append("	a08.keiyakusu_qt a08_keiyakusu_qt, ");
		sb.append("	a08.tuika_keiyakusu_qt a08_tuika_keiyakusu_qt, ");
		sb.append("	a08.siiresaki_cd a08_siiresaki_cd, ");
		sb.append("	a08.siire_hinban_cd a08_siire_hinban_cd, ");
		sb.append("	a08.hanbai_st_dt a08_hanbai_st_dt, ");
		sb.append("	a08.hanbai_ed_dt a08_hanbai_ed_dt, ");
		sb.append("	a08.ten_hachu_st_dt a08_ten_hachu_st_dt, ");
		sb.append("	a08.ten_hachu_ed_dt a08_ten_hachu_ed_dt, ");
		sb.append("	a08.eos_kb a08_eos_kb, ");
		sb.append("	a08.season_cd a08_season_cd, ");
		sb.append("	a08.bland_cd a08_bland_cd, ");
		sb.append("	a08.pb_kb a08_pb_kb, ");
		sb.append("	a08.yoridori_vl a08_yoridori_vl, ");
		sb.append("	a08.yoridori_qt a08_yoridori_qt, ");
		sb.append("	a08.yoridori_kb a08_yoridori_kb, ");
		sb.append("	a08.nefuda_kb a08_nefuda_kb, ");
		sb.append("	a08.nefuda_ukewatasi_dt a08_nefuda_ukewatasi_dt, ");
		sb.append("	a08.nefuda_ukewatasi_kb a08_nefuda_ukewatasi_kb, ");
		sb.append("	a08.fuji_syohin_kb a08_fuji_syohin_kb, ");
		sb.append("	a08.sozai_cd a08_sozai_cd, ");
		sb.append("	a08.oriami_cd a08_oriami_cd, ");
		sb.append("	a08.sode_cd a08_sode_cd, ");
		sb.append("	a08.age_cd a08_age_cd, ");
		sb.append("	a08.pattern_cd a08_pattern_cd, ");
		sb.append("	'' a08_temp1, ");
		sb.append("	'' a08_temp2, ");
		sb.append("	'' a08_temp3, ");
		sb.append("	'' a08_temp4, ");
		sb.append("	'' a08_temp5, ");
		sb.append("	'' a08_temp6, ");
		sb.append("	'' a08_temp7, ");
		sb.append("	'' a08_temp8, ");
		sb.append("	'' a08_temp9, ");
		sb.append("	'' a08_temp10, ");
		sb.append("	'' a08_temp11, ");
		sb.append("	a08.syusei_kb a08_syusei_kb, ");
		sb.append("	0 a08_temp10, ");
		sb.append("	a08.sakusei_gyo_no a08_sakusei_gyo_no, ");
		sb.append("	a08.mst_mainte_fg a08_mst_mainte_fg, ");
		sb.append("	a08.alarm_make_fg a08_alarm_make_fg, ");
		sb.append("	a08.insert_ts a08_insert_ts, ");
		sb.append("	a08.insert_user_id a08_insert_user_id, ");
		sb.append("	a08.update_ts a08_update_ts, ");
		sb.append("	a08.update_user_id a08_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_a08 a08 ");
		sb.append("   where  ");
		sb.append("     a08.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     a08.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" order by ");
		sb.append("     a08.sakusei_gyo_no,  ");
		sb.append("     a08.uketsuke_seq  ");

		return sb.toString();
	}

	/**
	 * TRテーブル（実用）のCSV用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectA07CsvSql(String torikomiDt, String uketsukeNo) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '01' a07_sheet_syubetsu,  ");
		sb.append("	a07.torikomi_dt a07_torikomi_dt, ");
		sb.append("	a07.excel_file_syubetsu a07_excel_file_syubetsu, ");
		sb.append("	a07.uketsuke_no a07_uketsuke_no, ");
		sb.append("	a07.uketsuke_seq a07_uketsuke_seq, ");
		sb.append("	a07.bumon_cd a07_bumon_cd, ");
		sb.append("	a07.unit_cd a07_unit_cd, ");
		sb.append("	a07.haifu_cd a07_haifu_cd, ");
		sb.append("	a07.subclass_cd a07_subclass_cd, ");
		sb.append("	a07.syohin_cd a07_syohin_cd, ");
		sb.append("	a07.yuko_dt a07_yuko_dt, ");
		sb.append("	a07.maker_cd a07_maker_cd, ");
		sb.append("	a07.pos_cd a07_pos_cd, ");
		sb.append("	a07.hinmei_kanji_na a07_hinmei_kanji_na, ");
		sb.append("	a07.kikaku_kanji_na a07_kikaku_kanji_na, ");
		sb.append("	a07.kikaku_kanji_2_na a07_kikaku_kanji_2_na, ");
		sb.append("	a07.hinmei_kana_na a07_hinmei_kana_na, ");
		sb.append("	a07.kikaku_kana_na a07_kikaku_kana_na, ");
		sb.append("	a07.kikaku_kana_2_na a07_kikaku_kana_2_na, ");
		sb.append("	a07.rec_hinmei_kanji_na a07_rec_hinmei_kanji_na, ");
		sb.append("	a07.rec_hinmei_kana_na a07_rec_hinmei_kana_na, ");
		sb.append("	a07.size_cd a07_size_cd, ");
		sb.append("	a07.color_cd a07_color_cd, ");
		sb.append("	a07.gentanka_vl a07_gentanka_vl, ");
		sb.append("	a07.baitanka_vl a07_baitanka_vl, ");
		sb.append("	a07.keshi_baika_vl a07_keshi_baika_vl, ");
		sb.append("	a07.keiyakusu_qt a07_keiyakusu_qt, ");
		sb.append("	a07.tuika_keiyakusu_qt a07_tuika_keiyakusu_qt, ");
		sb.append("	a07.hachutani_irisu_qt a07_hachutani_irisu_qt, ");
		sb.append("	a07.hachu_tani_na a07_hachu_tani_na, ");
		sb.append("	a07.plu_send_dt a07_plu_send_dt, ");
		sb.append("	a07.siiresaki_cd a07_siiresaki_cd, ");
		sb.append("	a07.siire_hinban_cd a07_siire_hinban_cd, ");
		sb.append("	a07.hanbai_st_dt a07_hanbai_st_dt, ");
		sb.append("	a07.hanbai_ed_dt a07_hanbai_ed_dt, ");
		sb.append("	a07.ten_hachu_st_dt a07_ten_hachu_st_dt, ");
		sb.append("	a07.ten_hachu_ed_dt a07_ten_hachu_ed_dt, ");
		sb.append("	a07.eos_kb a07_eos_kb, ");
		sb.append("	a07.season_cd a07_season_cd, ");
		sb.append("	a07.bland_cd a07_bland_cd, ");
		sb.append("	a07.pb_kb a07_pb_kb, ");
		sb.append("	a07.yoridori_vl a07_yoridori_vl, ");
		sb.append("	a07.yoridori_qt a07_yoridori_qt, ");
		sb.append("	a07.yoridori_kb a07_yoridori_kb, ");
		sb.append("	a07.tana_no_nb a07_tana_no_nb, ");
		sb.append("	a07.nefuda_kb a07_nefuda_kb, ");
		sb.append("	a07.nefuda_ukewatasi_dt a07_nefuda_ukewatasi_dt, ");
		sb.append("	a07.nefuda_ukewatasi_kb a07_nefuda_ukewatasi_kb, ");
		sb.append("	a07.fuji_syohin_kb a07_fuji_syohin_kb, ");
		sb.append("	a07.pc_kb a07_pc_kb, ");
		sb.append("	a07.daisi_no_nb a07_daisi_no_nb, ");
		sb.append("	'' a07_temp1, ");
		sb.append("	'' a07_temp2, ");
		sb.append("	'' a07_temp3, ");
		sb.append("	a07.syusei_kb a07_syusei_kb, ");
		sb.append("	0 a07_temp2, ");
		sb.append("	a07.sakusei_gyo_no a07_sakusei_gyo_no, ");
		sb.append("	a07.mst_mainte_fg a07_mst_mainte_fg, ");
		sb.append("	a07.alarm_make_fg a07_alarm_make_fg, ");
		sb.append("	a07.insert_ts a07_insert_ts, ");
		sb.append("	a07.insert_user_id a07_insert_user_id, ");
		sb.append("	a07.update_ts a07_update_ts, ");
		sb.append("	a07.update_user_id a07_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_a07 a07 ");
		sb.append("   where  ");
		sb.append("     a07.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     a07.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" order by ");
		sb.append("     a07.sakusei_gyo_no,  ");
		sb.append("     a07.uketsuke_seq  ");

		return sb.toString();
	}

	/**
	 * TRテーブル（グロサリー・バラエティ）のCSV用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectGroCsvSql(String torikomiDt, String uketsukeNo) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '01' gro_sheet_syubetsu,  ");
		sb.append("	gro.torikomi_dt gro_torikomi_dt, ");
		sb.append("	gro.excel_file_syubetsu gro_excel_file_syubetsu, ");
		sb.append("	gro.uketsuke_no gro_uketsuke_no, ");
		sb.append("	gro.uketsuke_seq gro_uketsuke_seq, ");
		sb.append("	gro.bumon_cd gro_bumon_cd, ");
		sb.append("	gro.unit_cd gro_unit_cd, ");
		sb.append("	gro.haifu_cd gro_haifu_cd, ");
		sb.append("	gro.subclass_cd gro_subclass_cd, ");
		sb.append("	gro.syohin_cd gro_syohin_cd, ");
		sb.append("	gro.yuko_dt gro_yuko_dt, ");
		sb.append("	gro.maker_cd gro_maker_cd, ");
		sb.append("	'' gro_temp1, ");
		sb.append("	gro.hinmei_kanji_na gro_hinmei_kanji_na, ");
		sb.append("	gro.kikaku_kanji_na gro_kikaku_kanji_na, ");
		sb.append("	gro.kikaku_kanji_2_na gro_kikaku_kanji_2_na, ");
		sb.append("	gro.hinmei_kana_na gro_hinmei_kana_na, ");
		sb.append("	gro.kikaku_kana_na gro_kikaku_kana_na, ");
		sb.append("	gro.kikaku_kana_2_na gro_kikaku_kana_2_na, ");
		sb.append("	gro.rec_hinmei_kanji_na gro_rec_hinmei_kanji_na, ");
		sb.append("	gro.rec_hinmei_kana_na gro_rec_hinmei_kana_na, ");
		sb.append("	gro.gentanka_vl gro_gentanka_vl, ");
		sb.append("	gro.baitanka_vl gro_baitanka_vl, ");
		sb.append("	gro.plu_send_dt gro_plu_send_dt, ");
		sb.append("	gro.siiresaki_cd gro_siiresaki_cd, ");
		sb.append("	gro.ten_siiresaki_kanri_cd gro_ten_siiresaki_kanri_cd, ");
		sb.append("	gro.siire_hinban_cd gro_siire_hinban_cd, ");
		sb.append("	gro.hanbai_st_dt gro_hanbai_st_dt, ");
		sb.append("	gro.hanbai_ed_dt gro_hanbai_ed_dt, ");
		sb.append("	gro.ten_hachu_st_dt gro_ten_hachu_st_dt, ");
		sb.append("	gro.ten_hachu_ed_dt gro_ten_hachu_ed_dt, ");
		sb.append("	gro.eos_kb gro_eos_kb, ");
		sb.append("	gro.honbu_zai_kb gro_honbu_zai_kb, ");
		sb.append("	gro.hachu_tani_na gro_hachu_tani_na, ");
		sb.append("	gro.hachutani_irisu_qt gro_hachutani_irisu_qt, ");
		sb.append("	gro.max_hachutani_qt gro_max_hachutani_qt, ");
		sb.append("	gro.bland_cd gro_bland_cd, ");
		sb.append("	gro.pb_kb gro_pb_kb, ");
		sb.append("	gro.maker_kibo_kakaku_vl gro_maker_kibo_kakaku_vl, ");
		sb.append("	gro.fuji_syohin_kb gro_fuji_syohin_kb, ");
		sb.append("	gro.pc_kb gro_pc_kb, ");
		sb.append("	gro.daisi_no_nb gro_daisi_no_nb, ");
		sb.append("	gro.daicho_tenpo_kb gro_daicho_tenpo_kb, ");
		sb.append("	gro.daicho_honbu_kb gro_daicho_honbu_kb, ");
		sb.append("	gro.daicho_siiresaki_kb gro_daicho_siiresaki_kb, ");
		sb.append("	gro.syuzei_hokoku_kb gro_syuzei_hokoku_kb, ");
//		↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	gro.syurui_dosuu_qt gro_syurui_dosuu_qt, ");
//		↑↑2006.10.18 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	gro.unit_price_tani_kb gro_unit_price_tani_kb, ");
		sb.append("	gro.unit_price_naiyoryo_qt gro_unit_price_naiyoryo_qt, ");
		sb.append("	gro.unit_price_kijun_tani_qt gro_unit_price_kijun_tani_qt, ");
		sb.append("	gro.syohi_kigen_dt gro_syohi_kigen_dt, ");
		sb.append("	'' gro_temp2, ");
		sb.append("	'' gro_temp3, ");
		sb.append("	'' gro_temp4, ");
		sb.append("	'' gro_temp5, ");
		sb.append("	gro.syusei_kb gro_syusei_kb, ");
		sb.append("	0 gro_temp4, ");
		sb.append("	gro.sakusei_gyo_no gro_sakusei_gyo_no, ");
		sb.append("	gro.mst_mainte_fg gro_mst_mainte_fg, ");
		sb.append("	gro.alarm_make_fg gro_alarm_make_fg, ");
		sb.append("	gro.insert_ts gro_insert_ts, ");
		sb.append("	gro.insert_user_id gro_insert_user_id, ");
		sb.append("	gro.update_ts gro_update_ts, ");
		sb.append("	gro.update_user_id gro_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_gro gro ");
		sb.append("   where  ");
		sb.append("     gro.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     gro.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" order by ");
		sb.append("     gro.sakusei_gyo_no,  ");
		sb.append("     gro.uketsuke_seq  ");

		return sb.toString();
	}

	/**
	 * TRテーブル（デイリー・パン）のCSV用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectFreCsvSql(String torikomiDt, String uketsukeNo) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '01' fre_sheet_syubetsu,  ");
		sb.append("	fre.torikomi_dt fre_torikomi_dt, ");
		sb.append("	fre.excel_file_syubetsu fre_excel_file_syubetsu, ");
		sb.append("	fre.uketsuke_no fre_uketsuke_no, ");
		sb.append("	fre.uketsuke_seq fre_uketsuke_seq, ");
		sb.append("	fre.bumon_cd fre_bumon_cd, ");
		sb.append("	fre.unit_cd fre_unit_cd, ");
		sb.append("	fre.haifu_cd fre_haifu_cd, ");
		sb.append("	fre.subclass_cd fre_subclass_cd, ");
		sb.append("	fre.syohin_cd fre_syohin_cd, ");
		sb.append("	fre.yuko_dt fre_yuko_dt, ");
		sb.append("	fre.maker_cd fre_maker_cd, ");
		sb.append("	'' fre_temp1, ");
		sb.append("	fre.hinmei_kanji_na fre_hinmei_kanji_na, ");
		sb.append("	fre.kikaku_kanji_na fre_kikaku_kanji_na, ");
		sb.append("	fre.kikaku_kanji_2_na fre_kikaku_kanji_2_na, ");
		sb.append("	fre.hinmei_kana_na fre_hinmei_kana_na, ");
		sb.append("	fre.kikaku_kana_na fre_kikaku_kana_na, ");
		sb.append("	fre.kikaku_kana_2_na fre_kikaku_kana_2_na, ");
		sb.append("	fre.rec_hinmei_kanji_na fre_rec_hinmei_kanji_na, ");
		sb.append("	fre.rec_hinmei_kana_na fre_rec_hinmei_kana_na, ");
		sb.append("	fre.gentanka_vl fre_gentanka_vl, ");
		sb.append("	fre.baitanka_vl fre_baitanka_vl, ");
		sb.append("	fre.plu_send_dt fre_plu_send_dt, ");
		sb.append("	fre.siiresaki_cd fre_siiresaki_cd, ");
		sb.append("	fre.ten_siiresaki_kanri_cd fre_ten_siiresaki_kanri_cd, ");
		sb.append("	fre.siire_hinban_cd fre_siire_hinban_cd, ");
		sb.append("	fre.hanbai_st_dt fre_hanbai_st_dt, ");
		sb.append("	fre.hanbai_ed_dt fre_hanbai_ed_dt, ");
		sb.append("	fre.ten_hachu_st_dt fre_ten_hachu_st_dt, ");
		sb.append("	fre.ten_hachu_ed_dt fre_ten_hachu_ed_dt, ");
		sb.append("	fre.eos_kb fre_eos_kb, ");
		sb.append("	fre.honbu_zai_kb fre_honbu_zai_kb, ");
		sb.append("	fre.hachu_tani_na fre_hachu_tani_na, ");
		sb.append("	fre.hachutani_irisu_qt fre_hachutani_irisu_qt, ");
		sb.append("	fre.max_hachutani_qt fre_max_hachutani_qt, ");
		sb.append("	fre.bland_cd fre_bland_cd, ");
		sb.append("	fre.pb_kb fre_pb_kb, ");
		sb.append("	fre.maker_kibo_kakaku_vl fre_maker_kibo_kakaku_vl, ");
		sb.append("	fre.bin_1_kb fre_bin_1_kb, ");
		sb.append("	fre.bin_2_kb fre_bin_2_kb, ");
		sb.append("	fre.yusen_bin_kb fre_yusen_bin_kb, ");
		sb.append("	fre.fuji_syohin_kb fre_fuji_syohin_kb, ");
		sb.append("	fre.pc_kb fre_pc_kb, ");
		sb.append("	fre.daisi_no_nb fre_daisi_no_nb, ");
		sb.append("	fre.daicho_tenpo_kb fre_daicho_tenpo_kb, ");
		sb.append("	fre.daicho_honbu_kb fre_daicho_honbu_kb, ");
		sb.append("	fre.daicho_siiresaki_kb fre_daicho_siiresaki_kb, ");
		sb.append("	fre.unit_price_tani_kb fre_unit_price_tani_kb, ");
		sb.append("	fre.unit_price_naiyoryo_qt fre_unit_price_naiyoryo_qt, ");
		sb.append("	fre.unit_price_kijun_tani_qt fre_unit_price_kijun_tani_qt, ");
		sb.append("	fre.syohi_kigen_dt fre_syohi_kigen_dt, ");
		sb.append("	fre.nyuka_kyoyo_qt fre_nyuka_kyoyo_qt, ");
		sb.append("	fre.hanbai_gendo_qt fre_hanbai_gendo_qt, ");
		sb.append("	fre.syusei_kb fre_syusei_kb, ");
		sb.append("	0 fre_temp1, ");
		sb.append("	fre.sakusei_gyo_no fre_sakusei_gyo_no, ");
		sb.append("	fre.mst_mainte_fg fre_mst_mainte_fg, ");
		sb.append("	fre.alarm_make_fg fre_alarm_make_fg, ");
		sb.append("	fre.insert_ts fre_insert_ts, ");
		sb.append("	fre.insert_user_id fre_insert_user_id, ");
		sb.append("	fre.update_ts fre_update_ts, ");
		sb.append("	fre.update_user_id fre_update_user_id ");
		sb.append(" from ");
		sb.append("     tr_syohin_fre fre ");
		sb.append("   where  ");
		sb.append("     fre.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     fre.uketsuke_no = '" + uketsukeNo +"' ");
		sb.append(" order by ");
		sb.append("     fre.sakusei_gyo_no,  ");
		sb.append("     fre.uketsuke_seq  ");

		return sb.toString();
	}

	/**
	 * TRテーブル（店別例外）のCSV用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectReigaiCsvSql(String torikomiDt, String uketsukeNo, String excelFileSyubetu) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '03' sheet_syubetsu,  ");
		sb.append("	reigai.torikomi_dt torikomi_dt, ");
		sb.append("	reigai.excel_file_syubetsu excel_file_syubetsu, ");
		sb.append("	reigai.uketsuke_no uketsuke_no, ");
		sb.append("	reigai.uketsuke_seq uketsuke_seq, ");
		sb.append("	reigai.bumon_cd bumon_cd, ");
		sb.append("	reigai.syohin_cd syohin_cd, ");
		sb.append("	reigai.tenpo_cd tenpo_cd, ");
		sb.append("	reigai.yuko_dt yuko_dt, ");
		sb.append("	reigai.ten_hachu_st_dt ten_hachu_st_dt, ");
		sb.append("	reigai.ten_hachu_ed_dt ten_hachu_ed_dt, ");
		sb.append("	reigai.gentanka_vl gentanka_vl, ");
		sb.append("	reigai.baitanka_vl baitanka_vl, ");
		sb.append("	reigai.max_hachutani_qt max_hachutani_qt, ");
		sb.append("	reigai.eos_kb eos_kb, ");
		sb.append("	reigai.siiresaki_cd siiresaki_cd, ");
		sb.append("	reigai.buturyu_kb buturyu_kb, ");
		sb.append("	reigai.bin_1_kb bin_1_kb, ");
		sb.append("	reigai.bin_2_kb bin_2_kb, ");
		sb.append("	reigai.yusen_bin_kb yusen_bin_kb, ");
		sb.append("	reigai.syusei_kb syusei_kb, ");
		sb.append("	reigai.syohin_gyo_no syohin_gyo_no, ");
		sb.append("	reigai.sakusei_gyo_no sakusei_gyo_no, ");
		sb.append("	reigai.mst_mainte_fg mst_mainte_fg, ");
		sb.append("	reigai.alarm_make_fg alarm_make_fg, ");
		sb.append("	reigai.insert_ts insert_ts, ");
		sb.append("	reigai.insert_user_id insert_user_id, ");
		sb.append("	reigai.update_ts update_ts, ");
		sb.append("	reigai.update_user_id update_user_id ");
		sb.append("	, ");
		sb.append("	 shohin.hinmei_kanji_na hinmei_kanji_na ");
		if ("gro".equals(excelFileSyubetu.toLowerCase()) || "fre".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	, ");
			sb.append("	 shohin.kikaku_kanji_na kikaku_kanji_na ");
		} else {
			sb.append("	, ");
			sb.append("	 shohin.size_cd size_cd ");
			sb.append("	, ");
			sb.append("	 shohin.color_cd color_cd ");
		}
		sb.append("	, ");
		sb.append("	 shohin.ten_hachu_st_dt shohin_ten_hachu_st_dt ");
		sb.append("	, ");
		sb.append("	 shohin.ten_hachu_ed_dt shohin_ten_hachu_ed_dt ");
		sb.append("	, ");
		sb.append("	 shohin.gentanka_vl shohin_gentanka_vl ");
		sb.append("	, ");
		sb.append("	 shohin.baitanka_vl shohin_baitanka_vl ");
		sb.append("	, ");
		sb.append("	 shohin.eos_kb shohin_eos_kb ");
		sb.append("	, ");
		sb.append("	 shohin.siiresaki_cd shohin_siiresaki_cd ");
		if ("gro".equals(excelFileSyubetu.toLowerCase()) || "fre".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("		, ");
			sb.append("		 shohin.max_hachutani_qt shohin_max_hachutani_qt ");
		}
		sb.append(" from ");
		sb.append("     tr_tensyohin_reigai reigai  ");
		sb.append("	left outer join	  ");
		if ("gro".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_gro shohin ");
		} else if ("fre".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_fre shohin ");
		} else if ("a07".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_a07 shohin ");
		} else  {
			sb.append("	 tr_syohin_a08 shohin ");
		}
		sb.append("on  ");
		sb.append("		reigai.uketsuke_no  = shohin.uketsuke_no ");
		sb.append("and  ");
		sb.append("		reigai.syohin_gyo_no  = shohin.sakusei_gyo_no ");
		sb.append("   where  ");
		sb.append("     reigai.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     reigai.uketsuke_no = '"+ uketsukeNo +"' ");
		sb.append(" order by ");
		sb.append("     reigai.sakusei_gyo_no,  ");
		sb.append("     reigai.uketsuke_seq  ");

		return sb.toString();
	}

	/**
	 * TRテーブル（店指示）のCSV用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectTanpinCsvSql(String torikomiDt, String uketsukeNo, String excelFileSyubetu) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '02' sheet_syubetsu,  ");
		sb.append("	tanpin.torikomi_dt torikomi_dt, ");
		sb.append("	tanpin.excel_file_syubetsu excel_file_syubetsu, ");
		sb.append("	tanpin.uketsuke_no uketsuke_no, ");
		sb.append("	tanpin.uketsuke_seq uketsuke_seq, ");
		sb.append("	tanpin.bumon_cd bumon_cd, ");
		sb.append("	tanpin.syohin_cd syohin_cd, ");
		sb.append("	tanpin.donyu_dt donyu_dt, ");
		sb.append("	tanpin.tenpo_cd tenpo_cd, ");
		sb.append("	tanpin.syusei_kb syusei_kb, ");
		sb.append("	tanpin.syohin_gyo_no syohin_gyo_no, ");
		sb.append("	tanpin.sakusei_gyo_no sakusei_gyo_no, ");
		sb.append("	tanpin.mst_mainte_fg mst_mainte_fg, ");
		sb.append("	tanpin.alarm_make_fg alarm_make_fg, ");
		sb.append("	tanpin.insert_ts insert_ts, ");
		sb.append("	tanpin.insert_user_id insert_user_id, ");
		sb.append("	tanpin.update_ts update_ts, ");
		sb.append("	tanpin.update_user_id update_user_id ");
		sb.append("	, ");
		sb.append("	 shohin.hinmei_kanji_na hinmei_kanji_na ");
		if ("gro".equals(excelFileSyubetu.toLowerCase()) || "fre".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	, ");
			sb.append("	 shohin.kikaku_kanji_na kikaku_kanji_na ");
		} else {
			sb.append("	, ");
			sb.append("	 shohin.size_cd size_cd ");
			sb.append("	, ");
			sb.append("	 shohin.color_cd color_cd ");
		}
		sb.append(" from ");
		sb.append("     tr_tanpinten_toriatukai tanpin  ");
		sb.append("	left outer join	  ");
		if ("gro".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_gro shohin ");
		} else if ("fre".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_fre shohin ");
		} else if ("a07".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_a07 shohin ");
		} else  {
			sb.append("	 tr_syohin_a08 shohin ");
		}
		sb.append("on  ");
		sb.append("		tanpin.uketsuke_no  = shohin.uketsuke_no ");
		sb.append("and  ");
		sb.append("		tanpin.syohin_gyo_no  = shohin.sakusei_gyo_no ");
		sb.append("   where  ");
		sb.append("     tanpin.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     tanpin.uketsuke_no = '"+ uketsukeNo +"' ");
		sb.append(" order by ");
		sb.append("     tanpin.sakusei_gyo_no,  ");
		sb.append("     tanpin.uketsuke_seq  ");

		return sb.toString();
	}

	/**
	 * TRテーブル（本部投入）のCSV用ＳＱＬの生成を行う。
	 *
	 * @param  torikomiDt String
	 * @param  uketsukeNo String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSyokaiCsvSql(String torikomiDt, String uketsukeNo, String excelFileSyubetu) {

		StringBuffer sb = new StringBuffer();

		sb.append(" select  ");
		sb.append(" '04' sheet_syubetsu,  ");
		sb.append("	syo.torikomi_dt torikomi_dt, ");
		sb.append("	syo.excel_file_syubetsu excel_file_syubetsu, ");
		sb.append("	syo.uketsuke_no uketsuke_no, ");
		sb.append("	syo.uketsuke_seq uketsuke_seq, ");
		sb.append("	syo.hachuno_cd hachuno_cd, ");
		sb.append("	syo.bumon_cd bumon_cd, ");
		sb.append("	syo.syohin_cd syohin_cd, ");
		sb.append("	syo.theme_cd theme_cd, ");
		sb.append("	syo.hatyu_dt hatyu_dt, ");
		sb.append("	syo.nohin_dt nohin_dt, ");
		sb.append("	syo.hanbai_st_dt hanbai_st_dt, ");
		sb.append("	syo.hanbai_ed_dt hanbai_ed_dt, ");
		sb.append("	syo.gentanka_vl gentanka_vl, ");
		sb.append("	syo.baitanka_vl baitanka_vl, ");
		sb.append("	syo.hachutani_qt hachutani_qt, ");
		sb.append("	syo.tenpo_cd tenpo_cd, ");
		sb.append("	syo.suryo_qt suryo_qt, ");
		sb.append("	syo.syusei_kb syusei_kb, ");
		sb.append("	syo.syohin_gyo_no syohin_gyo_no, ");
		sb.append("	syo.sakusei_gyo_no sakusei_gyo_no, ");
		sb.append("	syo.mst_mainte_fg mst_mainte_fg, ");
		sb.append("	syo.alarm_make_fg alarm_make_fg, ");
		sb.append("	syo.insert_ts insert_ts, ");
		sb.append("	syo.insert_user_id insert_user_id, ");
		sb.append("	syo.update_ts update_ts, ");
		sb.append("	syo.update_user_id update_user_id ");
		sb.append("	, ");
		sb.append("	 shohin.hinmei_kanji_na hinmei_kanji_na ");
		if ("gro".equals(excelFileSyubetu.toLowerCase()) || "fre".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	, ");
			sb.append("	 shohin.kikaku_kanji_na kikaku_kanji_na ");
		} else {
			sb.append("	, ");
			sb.append("	 shohin.size_cd size_cd ");
			sb.append("	, ");
			sb.append("	 shohin.color_cd color_cd ");
		}
		sb.append("	, ");
		sb.append("	 shohin.gentanka_vl shohin_gentanka_vl ");
		sb.append("	, ");
		sb.append("	 shohin.baitanka_vl shohin_baitanka_vl ");
		sb.append("	, ");
		sb.append("	 shohin.siiresaki_cd shohin_siiresaki_cd ");
		if ("a07".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	, ");
			sb.append("	 shohin.hachutani_irisu_qt shohin_hachutani_irisu_qt ");
		}
		sb.append(" from ");
		sb.append("     tr_syokaidonyu syo  ");
		sb.append("	left outer join	  ");
		if ("gro".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_gro shohin ");
		} else if ("fre".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_fre shohin ");
		} else if ("a07".equals(excelFileSyubetu.toLowerCase())) {
			sb.append("	 tr_syohin_a07 shohin ");
		} else  {
			sb.append("	 tr_syohin_a08 shohin ");
		}
		sb.append("on  ");
		sb.append("		syo.uketsuke_no  = shohin.uketsuke_no ");
		sb.append("and  ");
		sb.append("		syo.syohin_gyo_no  = shohin.sakusei_gyo_no ");
		sb.append("   where  ");
		sb.append("     syo.torikomi_dt = '"+ torikomiDt +"' ");
		sb.append("     and  ");
		sb.append("     syo.uketsuke_no = '"+ uketsukeNo +"' ");
		sb.append(" order by ");
		sb.append("     syo.sakusei_gyo_no,  ");
		sb.append("     syo.uketsuke_seq  ");

		return sb.toString();
	}
//	↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
}
