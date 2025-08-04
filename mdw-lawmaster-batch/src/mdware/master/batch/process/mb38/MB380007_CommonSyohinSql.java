package mdware.master.batch.process.mb38;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Types;

import mdware.common.util.DateChanger;
import mdware.common.util.DateDiff;
//import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
//import mdware.master.common.bean.mst001401_CheckDegitBean;
//import mdware.master.common.dictionary.mst000101_ConstDictionary;
//import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.common.dictionary.mst006801_MstMainteFgDictionary;
import mdware.master.common.dictionary.mst910020_EmgFlagDictionary;
//import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:商品マスタ生成バッチ共通SQLクラス）</p>
 * <p>説明:データを登録するSQLを生成します</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/07/02<BR>
 * @         1.01 2006/01/17 D.Matsumoto 4桁販区対応<BR>
 * @         1.02 2006/07/31 D.Ryo PLU区分の追加対応<BR>
 * @Version 3.00 (2013.11.22) K.TO [CUS00048]  ランドローム様対応  マスタ未使用項目
 * @Version 3.01 (2014.01.15) K.TO [CUS00104]  計量器項目変更対応
 * @Version 3.02 (2014.01.23) K.TO [CUS00105]  売価変更フラグ対応（画面側）
 * @Version 3.03 (2015.07.13) DAI.BQ FIVImart様対応
 * @Version 3.04 (2015.08.17) THO.VT FIVImart様対応
 * @Version 3.05 (2015.12.15) TU.TD FIVImart様対応
 * @Version 3.06 (2016.01.11) Huy.NT FIVI様対応
 * @Version 3.07 (2016.10.20) T.Arimoto #2254 FIVI様対応 
 * @Version 3.08 (2016.10.25) Li.Sheng #2258 FIVI様対応 
 * @Version 3.09 (2017.04.03) M.Akagi #4509 
 * @Version 3.10 (2021.09.09) SIEU.D #6338 MKV対応 
 * @Version 3.11 (2021.09.27) SIEU.D #6355 MKV対応 
 * @author shimoyama
 * @         2.20 2009/05/08 MM対応<BR>
 */

public class MB380007_CommonSyohinSql {

	//マスタ日付
	private String MasterDT = "";

	private String BATCH_ID = "";

	private String GYOSYU_KB = "";

//	private final String deleteString = "*"; //削除を表す文字

	public MB380007_CommonSyohinSql(String MasterDT, String BATCH_ID, String GYOSYU_KB) {
		this.MasterDT = MasterDT;
		this.BATCH_ID = BATCH_ID;
		this.GYOSYU_KB = GYOSYU_KB;
	}

//	/**
//	 * 商品マスタ修正時の新規登録用PreparedStatement
//	 * @throws Exception
//	 */
//	public PreparedStatement getPreparedSyohinUpInsSQL(MasterDataBase dataBase) throws SQLException {
//		StringBuffer sql = new StringBuffer();
//		StringBuffer sql1 = new StringBuffer();
//		StringBuffer sql2 = new StringBuffer();
//
//		sql1.append("hanku_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("syohin_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("yuko_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("hacyu_syohin_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("system_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("gyosyu_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("hinsyu_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("hinmoku_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("mark_group_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("mark_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("syohin_2_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("ketasu_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("maker_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("hinmei_kanji_na,");
//		sql2.append(" ?,");
//
//		sql1.append("kikaku_kanji_na,");
//		sql2.append(" ?,");
//
//		sql1.append("hinmei_kana_na,");
//		sql2.append(" ?,");
//
//		sql1.append("kikaku_kana_na,");
//		sql2.append(" ?,");
//
//		sql1.append("syohin_width_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("syohin_height_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("syohin_depth_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("siire_hinban_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("bland_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("yunyuhin_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("santi_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("maker_kibo_kakaku_vl,");
//		sql2.append(" ?,");
//
//		sql1.append("nohin_ondo_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("comment_tx,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_hachu_st_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_hachu_ed_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("gentanka_vl,");
//		sql2.append(" ?,");
//
//		sql1.append("baitanka_vl,");
//		sql2.append(" ?,");
//
//		sql1.append("hachutani_irisu_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("max_hachutani_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("teikan_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("eos_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("nohin_kigen_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("nohin_kigen_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("mst_siyofuka_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("hachu_teisi_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("siiresaki_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("daihyo_haiso_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_siiresaki_kanri_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("soba_syohin_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("bin_1_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("hachu_pattern_1_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("sime_time_1_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("c_nohin_rtime_1_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_nohin_rtime_1_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_nohin_time_1_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("bin_2_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("hachu_pattern_2_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("sime_time_2_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("c_nohin_rtime_2_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_nohin_rtime_2_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_nohin_time_2_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("bin_3_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("hachu_pattern_3_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("sime_time_3_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("c_nohin_rtime_3_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_nohin_rtime_3_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_nohin_time_3_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("c_nohin_rtime_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("zei_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("hanbai_kikan_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("syohin_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("buturyu_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("yokomoti_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_groupno_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("ten_zaiko_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("hanbai_seisaku_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("henpin_nb,");
//		sql2.append(" ?,");
//
//		sql1.append("henpin_genka_vl,");
//		sql2.append(" ?,");
//
//		sql1.append("rebate_fg,");
//		sql2.append(" ?,");
//
//		sql1.append("hanbai_st_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("hanbai_ed_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("hanbai_limit_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("hanbai_limit_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("auto_del_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("got_mujyoken_fg,");
//		sql2.append(" ?,");
//
//		sql1.append("got_start_mm,");
//		sql2.append(" ?,");
//
//		sql1.append("got_end_mm,");
//		sql2.append(" ?,");
//
//		sql1.append("e_shop_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("plu_send_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("rec_hinmei_kanji_na,");
//		sql2.append(" ?,");
//
//		sql1.append("rec_hinmei_kana_na,");
//		sql2.append(" ?,");
//
//		sql1.append("keyplu_fg,");
//		sql2.append(" ?,");
//
//		sql1.append("pc_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("daisi_no_nb,");
//		sql2.append(" ?,");
//
//		sql1.append("unit_price_tani_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("unit_price_naiyoryo_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("unit_price_kijun_tani_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("shinazoroe_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("kumisu_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("nefuda_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("yoridori_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("yoridori_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("tag_hyoji_baika_vl,");
//		sql2.append(" ?,");
//
//		sql1.append("season_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("hukusyu_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("style_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("scene_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("sex_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("age_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("generation_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("sozai_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("pattern_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("oriami_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("huka_kino_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("size_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("color_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("syuzei_hokoku_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("tc_jyouho_na,");
//		sql2.append(" ?,");
//
//		sql1.append("nohin_syohin_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("itf_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("case_irisu_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("nyuka_syohin_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("pack_width_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("pack_heigth_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("pack_depth_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("pack_weight_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("center_zaiko_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("zaiko_hachu_saki,");
//		sql2.append(" ?,");
//
//		sql1.append("zaiko_center_cd,");
//		sql2.append(" ?,");
//
//		sql1.append("min_zaikosu_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("max_zaikosu_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("center_hachutani_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("center_hachutani_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("center_irisu_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("center_weight_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("tana_no_nb,");
//		sql2.append(" ?,");
//
//		sql1.append("dan_no_nb,");
//		sql2.append(" ?,");
//
//		sql1.append("keiyaku_su_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("keiyaku_pattern_kb,");
//		sql2.append(" ?,");
//
//		sql1.append("keiyaku_st_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("keiyaku_ed_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("kijun_zaikosu_qt,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_1_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_1_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_2_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_2_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_3_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_3_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_4_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_4_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_5_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_5_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_6_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_6_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_7_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_7_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_8_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_8_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_9_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_9_na,");
//		sql2.append(" ?,");
//
//		sql1.append("d_zokusei_10_na,");
//		sql2.append(" ?,");
//
//		sql1.append("s_zokusei_10_na,");
//		sql2.append(" ?,");
//
//		sql1.append("syokai_toroku_ts,");
//		sql2.append(" ?,");
//
//		sql1.append("syokai_user_id,");
//		sql2.append(" ?,");
//
//		sql1.append("sinki_toroku_dt,");
//		sql2.append(" ?,");
//
//		sql1.append("henko_dt,");
//		sql2.append("'").append(MasterDT).append("',");
//
//		sql1.append("insert_ts,");
////		↓↓移植(2006.05.22)↓↓
//		sql2.append("'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
////		↑↑移植(2006.05.22)↑↑
//
//		sql1.append("insert_user_id,");
//		sql2.append("'").append(BATCH_ID).append("',");
//
//		sql1.append("update_ts,");
////		↓↓移植(2006.05.22)↓↓
//		sql2.append("'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
////		↑↑移植(2006.05.22)↑↑
//
//		sql1.append("update_user_id,");
//		sql2.append("'").append(BATCH_ID).append("',");
//
//		sql1.append("delete_fg");
//		sql2.append(" '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
//
//		sql.append("insert into r_syohin ");
//		sql.append("( ");
//		sql.append(sql1.toString());
//		sql.append(") values ( ");
//		sql.append(sql2.toString());
//		sql.append(") ");
//
//		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
//		return pstmt;
//	}
//
//	/**
//	 * 商品マスタデータ更新SQL
//	 * @throws Exception
//	 */
////	↓↓2006.07.03 jianglm カスタマイズ修正↓↓
//	//public void setPrepareSyohinUpInsSQL(PreparedStatement pstmt, ResultSet rs, String hanku_cd) throws SQLException {
//	public void setPrepareSyohinUpInsSQL(PreparedStatement pstmt, ResultSet rs, String bumon_cd,String syohin_cd) throws SQLException {
//// ↑↑2006.07.03 jianglm カスタマイズ修正↑↑
//
//		int idx = 0;
//
//		String str = "";
//
//		idx++;
//		pstmt.setString(idx, rs.getString(bumon_cd));
//
//		idx++;
//		pstmt.setString(idx, syohin_cd);
//
//		idx++;
//		// ===BEGIN=== Modify by kou 2006/8/3
//		// 有効開始日が未入力の場合、管理日付の翌日でセットする
//		//pstmt.setString(idx, rs.getString("yuko_dt"));
//		if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
//			String startDt = DateChanger.addDate(MasterDT, 1);
//			pstmt.setString(idx,startDt);
//		}else{
//			pstmt.setString(idx, rs.getString("yuko_dt"));
//		}
//		// ===END=== Modify by kou 2006/8/3
//
//		idx++;
//		str = rs.getString("hacyu_syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("system_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
// 		str = rs.getString("gyosyu_kb");
//
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("unit_cd");
//
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hinmoku_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("mark_group_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("mark_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode()) ) {
//			idx++;
//			str = rs.getString("pos_cd");
//
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode()) ) {
//
//			idx++;
//			str = rs.getString("syohin_cd");
//
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}
//
//		idx++;
//		str = rs.getString("ketasu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("maker_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hinmei_kanji_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("kikaku_kanji_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hinmei_kana_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("kikaku_kana_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("syohin_width_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("syohin_height_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("syohin_depth_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("siire_hinban_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("bland_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("yunyuhin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("santi_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("maker_kibo_kakaku_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("nohin_ondo_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("comment_tx");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_hachu_st_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_hachu_ed_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("gentanka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//
//		idx++;
//		str = rs.getString("baitanka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("hachutani_irisu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//
//		idx++;
//		str = rs.getString("max_hachutani_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//
//		idx++;
//		str = rs.getString("teikan_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("eos_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("nohin_kigen_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("nohin_kigen_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("mst_siyofuka_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hachu_teisi_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("siiresaki_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("daihyo_haiso_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_siiresaki_kanri_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("soba_syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("bin_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hachu_pattern_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("sime_time_1_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("c_nohin_rtime_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_rtime_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_time_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("bin_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hachu_pattern_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("sime_time_2_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("c_nohin_rtime_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_rtime_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_time_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("bin_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hachu_pattern_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("sime_time_3_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("c_nohin_rtime_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_rtime_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_time_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("c_nohin_rtime_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("zei_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hanbai_kikan_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("buturyu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("yokomoti_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_groupno_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_zaiko_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hanbai_seisaku_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("henpin_nb");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("henpin_genka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("rebate_fg");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hanbai_st_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hanbai_ed_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode()) ) {
//			idx++;
//			str = rs.getString("hanbai_limit_qt");
//			if (isNotBlank(str)) {
//				pstmt.setLong(idx, Long.parseLong(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.BIGINT);
//			}
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode()) ) {
//			idx++;
//			str = rs.getString("syohi_kigen_dt");
//			if (isNotBlank(str)) {
//				int value = 0;
//				if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) == 1){
//
//					value = 0;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 2
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 8 ){
//
//					value = -1;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 9
//						&& Integer.parseInt (rs.getString("syohi_kigen_dt").trim()) <= 15 ){
//
//					value = -2;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 16
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 30 ){
//
//					value = -3;
//				}if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 31
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 90 ){
//
//					value = -5;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 91
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 180 ){
//
//					value = -7;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 181
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 360 ){
//
//					value = -20;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 361 ){
//
//					value = -30;
//				}
//				pstmt.setString(idx, String.valueOf(value));
//			} else {
//
//				pstmt.setNull(idx, Types.BIGINT);
//			}
//		}
//
//		idx++;
//		str = rs.getString("hanbai_limit_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("auto_del_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("got_mujyoken_fg");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("got_start_mm");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("got_end_mm");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("e_shop_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("plu_send_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("rec_hinmei_kanji_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("rec_hinmei_kana_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("keyplu_fg");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("pc_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("daisi_no_nb");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("unit_price_tani_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("unit_price_naiyoryo_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("unit_price_kijun_tani_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("shinazoroe_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("kumisu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("nefuda_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("yoridori_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("yoridori_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("tag_hyoji_baika_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("season_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hukusyu_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("style_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("scene_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("sex_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("age_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("generation_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("sozai_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("pattern_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("oriami_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("huka_kino_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("size_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("color_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("syuzei_hokoku_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("tc_jyouho_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode()))	{
//			idx++;
//			str = rs.getString("pos_cd");
//
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode()))	{
//			idx++;
//			str = rs.getString("syohin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}
//
//		idx++;
//		str = rs.getString("itf_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("case_irisu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("nyuka_syohin_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("pack_width_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("pack_heigth_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("pack_depth_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("pack_weight_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("center_zaiko_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("zaiko_hachu_saki");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("zaiko_center_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("min_zaikosu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("max_zaikosu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("center_hachutani_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("center_hachutani_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("center_irisu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("center_weight_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("tana_no_nb");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("dan_no_nb");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("keiyaku_su_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("keiyaku_pattern_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("keiyaku_st_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("keiyaku_ed_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("kijun_zaikosu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_1_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_1_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_2_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_2_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_3_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_3_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_4_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_4_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_5_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_5_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_6_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_6_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_7_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_7_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_8_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_8_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_9_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_9_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_10_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_10_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("by_no");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("by_no");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("by_no");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hinban_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("subclass_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("haifu_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode())){
//
//			idx++;
//			str = rs.getString("gtin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
//
//			//GTINコード
//			idx++;
//			str = rs.getString("syohin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, StringUtility.charFormat(str,str.length()+1,"0",true));
//			} else {
//				str = rs.getString("gtin_cd");
//				if (isNotBlank(str)) {
//					pstmt.setString(idx, str);
//				} else {
//					pstmt.setNull(idx, Types.VARCHAR);
//				}
//			}
//		}
////	   ↓↓2006.07.12 jianglm 暫定対応↓↓
////		idx++;
////		str = rs.getString("area_cd");
////		if (isNotBlank(str)) {
////			pstmt.setString(idx, str);
////		} else {
////			pstmt.setNull(idx, Types.VARCHAR);
////		}
////	   ↑↑2006.07.12 jianglm 暫定対応↑↑
//		idx++;
//		str = rs.getString("kikaku_kanji_2_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("kikaku_kana_2_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("pb_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("keshi_baika_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.LONGVARBINARY);
//		}
//
//		idx++;
//		str = rs.getString("tokubetu_genka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("pre_gentanka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("pre_baitanka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("hachu_tani_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode())){
//
//			idx++;
//			str = rs.getString("center_kyoyo_dt");
//			if (isNotBlank(str)) {
//				pstmt.setInt(idx, Integer.parseInt(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
//
//			idx++;
//			str = rs.getString("syohi_kigen_dt");
//			long val = 0;
//			if (isNotBlank(str)) {
//				if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 1
//						|| Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 2){
//
//					val = 1;
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) >= 3
//						&& Long.parseLong(rs.getString("syohi_kigen_dt").trim()) <= 8 ){
//
//					val = Long.parseLong(rs.getString("syohi_kigen_dt").trim())  - 1;
//
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 9 ){
//
//					val = 7;
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 10
//						|| Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 11){
//
//					val = 8;
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 12
//						|| Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 13){
//
//					val = 9;
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) >= 14 ){
//
//					val = Math.round(Double.parseDouble(rs.getString("syohi_kigen_dt").trim())*0.7);
//				}
//
//				pstmt.setLong(idx, val);
//			}else {
//
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}
//
//		idx++;
//		str = rs.getString("syohi_kigen_dt");
//		if (isNotBlank(str)) {
//			pstmt.setInt(idx, Integer.parseInt(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.INTEGER);
//		}
//
//		idx++;
//		str = rs.getString("nefuda_ukewatasi_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("nefuda_ukewatasi_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("yoridori_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.INTEGER);
//		}
//
//		idx++;
//		str = rs.getString("daicho_tenpo_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("daicho_honbu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("daicho_siiresaki_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("sode_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("honbu_zai_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("fuji_syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode())){
//
//			idx++;
//			str = rs.getString("hacyu_syohin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
//
//			idx++;
//			str = rs.getString("syohin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}
//
//		idx++;
//		str = rs.getString("yusen_bin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		// ===BEGIN === Add by kou 2006/8/9
//		// 実用のPLU区分は商品コードから判断ではなく、POSコードから判断
//		if(rs.getString("excel_file_syubetsu") != null
//			&& rs.getString("excel_file_syubetsu").equals("A07")){
//				//PLU区分
//				idx++;
//				str = rs.getString("POS_CD");
//				if (isNotBlank(str)) {
//					// 前4桁が"0400"である場合
//					if ("0400".equals(str.trim().substring(0,4))
//							) {
//						pstmt.setString(idx, "4");
//					} else if("0000000000".equals(str.trim().substring(0,10))){
//					//前9桁が"0000000000"である場合
//						pstmt.setString(idx, "3");
//					} else if ("45".equals(str.trim().substring(0,2))
//							|| "49".equals(str.trim().substring(0,2))) {
//					// 前2桁が"45","49"である場合
//						pstmt.setString(idx, "1");
//					}else{
//						pstmt.setString(idx, "2");
//					}
//				}else{
//					pstmt.setNull(idx, Types.CHAR);
//				}
//		}else{
//		// ===END=== Add by kou 2006/8/9
//
//	//		=== BEGIN === Add by ryo 2006/7/31
//			//PLU区分判定を行う
//			idx++;
//			//商品コードが前4桁が"0400"の場合
//			if ("0400".equals(syohin_cd.trim().substring(0,4))) {
//				str = "4";
//			} else if ("0000000000".equals(syohin_cd.trim().substring(0,10))) {
//				str = "3";
//			} else if ("45".equals(syohin_cd.trim().substring(0,2))
//			|| "49".equals(syohin_cd.trim().substring(0,2))) {
//				str = "1";
//			} else if ("45".equals(syohin_cd.trim().substring(5,7))
//			|| "49".equals(syohin_cd.trim().substring(5,7))) {
//				str = "1";
//			} else {
//				str = "2";
//			}
//			//PLU区分
//			pstmt.setString(idx, str);
//	//		=== END === Add by ryo 2006/7/31
//		}
//
//	}
//
////	 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//	/**
//	 * 商品マスタデータ更新SQL
//	 * @throws Exception
//	 */
////	↓↓2006.07.03 jianglm カスタマイズ修正↓↓
//	//public void setPrepareSyohinUpInsSQL(PreparedStatement pstmt, ResultSet rs, String hanku_cd) throws SQLException {
//	public void setPrepareSyohinUpInsSQL(PreparedStatement pstmt, ResultSet rs, String bumon_cd) throws SQLException {
//// ↑↑2006.07.03 jianglm カスタマイズ修正↑↑
//
//		int idx = 0;
//
//		String str = "";
//
//		idx++;
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
//		//pstmt.setString(idx, rs.getString(hanku_cd));
//		pstmt.setString(idx, rs.getString(bumon_cd));
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//		idx++;
//		pstmt.setString(idx, rs.getString("syohin_cd"));
//
//		idx++;
//		pstmt.setString(idx, rs.getString("yuko_dt"));
//
//		idx++;
//		str = rs.getString("hacyu_syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("system_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
//		//str = rs.getString("excel_file_syubetsu");
// 		str = rs.getString("gyosyu_kb");
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
//		//str = rs.getString("hinsyu_cd");
//		str = rs.getString("unit_cd");
////				 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hinmoku_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("mark_group_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("mark_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
//		//商品コード２
////		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.FRE.getCode()) || GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode()) ) {
//			idx++;
//			str = rs.getString("pos_cd");
//
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode()) ) {
//
//			idx++;
//			str = rs.getString("syohin_cd");
//
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
////			idx++;
////			str = rs.getString("syohin_2_cd");
////			if (isNotBlank(str)) {
////				pstmt.setString(idx, str);
////			} else {
////				pstmt.setNull(idx, Types.VARCHAR);
////			}
//		}
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//		idx++;
//		str = rs.getString("ketasu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
//		//メーカーコード
////		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.FRE.getCode()) || GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
////			idx++;
////			str = getMakerCd(rs);
////			if (isNotBlank(str)) {
////				pstmt.setString(idx, str);
////			} else {
////				pstmt.setNull(idx, Types.VARCHAR);
////			}
////		} else {
//			idx++;
//			str = rs.getString("maker_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//
////		}
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//		idx++;
//		str = rs.getString("hinmei_kanji_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("kikaku_kanji_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hinmei_kana_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("kikaku_kana_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("syohin_width_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("syohin_height_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("syohin_depth_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("siire_hinban_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("bland_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("yunyuhin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("santi_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("maker_kibo_kakaku_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("nohin_ondo_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("comment_tx");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_hachu_st_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_hachu_ed_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("gentanka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//
//		idx++;
//		str = rs.getString("baitanka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("hachutani_irisu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//
//		idx++;
//		str = rs.getString("max_hachutani_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//
//		idx++;
//		str = rs.getString("teikan_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("eos_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("nohin_kigen_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("nohin_kigen_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("mst_siyofuka_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hachu_teisi_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
//		idx++;
////		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
////			str = rs.getString("ten_siiresaki_kanri_ck"); //店別仕入先マスタの仕入先
////			if (isNotBlank(str)) {
////				pstmt.setString(idx, str);
////			} else {
////				str = rs.getString("siiresaki_cd");
////				if (isNotBlank(str)) {
////					pstmt.setString(idx, str);
////				} else {
////					pstmt.setNull(idx, Types.VARCHAR);
////				}
////			}
////		} else {
//			str = rs.getString("siiresaki_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//
////		}
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//		idx++;
//		str = rs.getString("daihyo_haiso_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_siiresaki_kanri_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("soba_syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("bin_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hachu_pattern_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("sime_time_1_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("c_nohin_rtime_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_rtime_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_time_1_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("bin_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hachu_pattern_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("sime_time_2_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("c_nohin_rtime_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_rtime_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_time_2_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("bin_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hachu_pattern_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("sime_time_3_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//
//		idx++;
//		str = rs.getString("c_nohin_rtime_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_rtime_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_nohin_time_3_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("c_nohin_rtime_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("zei_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hanbai_kikan_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("buturyu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("yokomoti_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_groupno_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("ten_zaiko_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("hanbai_seisaku_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//
//		idx++;
//		str = rs.getString("henpin_nb");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("henpin_genka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("rebate_fg");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hanbai_st_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hanbai_ed_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode()) ) {
//			idx++;
//			str = rs.getString("hanbai_limit_qt");
//			if (isNotBlank(str)) {
//				pstmt.setLong(idx, Long.parseLong(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.BIGINT);
//			}
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode()) ) {
//			idx++;
//			str = rs.getString("syohi_kigen_dt");
//			if (isNotBlank(str)) {
//				int value = 0;
//				if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) == 1){
//
//					value = 0;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 2
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 8 ){
//
//					value = -1;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 9
//						&& Integer.parseInt (rs.getString("syohi_kigen_dt").trim()) <= 15 ){
//
//					value = -2;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 16
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 30 ){
//
//					value = -3;
//				}if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 31
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 90 ){
//
//					value = -5;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 91
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 180 ){
//
//					value = -7;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 181
//						&& Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) <= 360 ){
//
//					value = -20;
//				}else if (Integer.parseInt(rs.getString("syohi_kigen_dt").trim()) >= 361 ){
//
//					value = -30;
//				}
//				pstmt.setString(idx, String.valueOf(value));
//			} else {
//
//				pstmt.setNull(idx, Types.BIGINT);
//			}
//		}
//
//		idx++;
//		str = rs.getString("hanbai_limit_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("auto_del_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("got_mujyoken_fg");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("got_start_mm");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("got_end_mm");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("e_shop_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("plu_send_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("rec_hinmei_kanji_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("rec_hinmei_kana_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("keyplu_fg");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("pc_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("daisi_no_nb");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("unit_price_tani_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("unit_price_naiyoryo_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("unit_price_kijun_tani_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("shinazoroe_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("kumisu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("nefuda_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("yoridori_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("yoridori_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("tag_hyoji_baika_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("season_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("hukusyu_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("style_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("scene_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("sex_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("age_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("generation_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("sozai_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("pattern_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("oriami_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("huka_kino_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("size_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("color_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("syuzei_hokoku_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("tc_jyouho_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
//		//納品商品コード
////		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.FRE.getCode()) || GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
//			//加工食品、生鮮の場合は納品商品コードがNULLの場合は、商品コードをセットする
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode()))	{
//			idx++;
//			str = rs.getString("pos_cd");
//
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode()))	{
//			idx++;
//			str = rs.getString("syohin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}
//
////		} else {
////			if (isNotBlank(str)) {
////				pstmt.setString(idx, str);
////			} else {
////				pstmt.setNull(idx, Types.VARCHAR);
////			}
////		}
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//		idx++;
//		str = rs.getString("itf_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("case_irisu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("nyuka_syohin_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("pack_width_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("pack_heigth_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("pack_depth_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("pack_weight_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("center_zaiko_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("zaiko_hachu_saki");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("zaiko_center_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("min_zaikosu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("max_zaikosu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("center_hachutani_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("center_hachutani_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("center_irisu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("center_weight_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("tana_no_nb");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("dan_no_nb");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("keiyaku_su_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("keiyaku_pattern_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("keiyaku_st_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("keiyaku_ed_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("kijun_zaikosu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_1_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_1_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_2_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_2_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_3_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_3_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_4_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_4_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_5_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_5_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_6_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_6_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_7_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_7_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_8_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_8_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_9_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_9_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("d_zokusei_10_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("s_zokusei_10_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
//		idx++;
//		str = rs.getString("by_no");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("by_no");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//		idx++;
//		str = rs.getString("by_no");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
////		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
////		idx++;
////		str = rs.getString("sinki_toroku_dt");
////		if (isNotBlank(str)) {
////			pstmt.setString(idx, str);
////		} else {
////			pstmt.setNull(idx, Types.VARCHAR);
////		}
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//		idx++;
//		str = rs.getString("hinban_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("subclass_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("haifu_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode())){
//
//			idx++;
//			str = rs.getString("gtin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
//
//			//GTINコード
//			idx++;
//			str = rs.getString("syohin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, StringUtility.charFormat(str,str.length()+1,"0",true));
//			} else {
//				str = rs.getString("gtin_cd");
//				if (isNotBlank(str)) {
//					pstmt.setString(idx, str);
//				} else {
//					pstmt.setNull(idx, Types.VARCHAR);
//				}
//			}
//		}
////	   ↓↓2006.07.12 jianglm 暫定対応↓↓
////		idx++;
////		str = rs.getString("area_cd");
////		if (isNotBlank(str)) {
////			pstmt.setString(idx, str);
////		} else {
////			pstmt.setNull(idx, Types.VARCHAR);
////		}
////	   ↑↑2006.07.12 jianglm 暫定対応↑↑
//		idx++;
//		str = rs.getString("kikaku_kanji_2_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("kikaku_kana_2_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("pb_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("keshi_baika_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.LONGVARBINARY);
//		}
//
//		idx++;
//		str = rs.getString("tokubetu_genka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("pre_gentanka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
//
//		idx++;
//		str = rs.getString("pre_baitanka_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}
//
//		idx++;
//		str = rs.getString("hachu_tani_na");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode())){
//
//			idx++;
//			str = rs.getString("center_kyoyo_dt");
//			if (isNotBlank(str)) {
//				pstmt.setInt(idx, Integer.parseInt(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
//
//			idx++;
//			str = rs.getString("syohi_kigen_dt");
//			long val = 0;
//			if (isNotBlank(str)) {
//				if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 1
//						|| Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 2){
//
//					val = 1;
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) >= 3
//						&& Long.parseLong(rs.getString("syohi_kigen_dt").trim()) <= 8 ){
//
//					val = Long.parseLong(rs.getString("syohi_kigen_dt").trim())  - 1;
//
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 9 ){
//
//					val = 7;
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 10
//						|| Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 11){
//
//					val = 8;
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 12
//						|| Long.parseLong(rs.getString("syohi_kigen_dt").trim()) == 13){
//
//					val = 9;
//				}else if (Long.parseLong(rs.getString("syohi_kigen_dt").trim()) >= 14 ){
//
//					val = Math.round(Double.parseDouble(rs.getString("syohi_kigen_dt").trim())*0.7);
//				}
//
//				pstmt.setLong(idx, val);
//			}else {
//
//				pstmt.setNull(idx, Types.INTEGER);
//			}
//		}
//
//		idx++;
//		str = rs.getString("syohi_kigen_dt");
//		if (isNotBlank(str)) {
//			pstmt.setInt(idx, Integer.parseInt(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.INTEGER);
//		}
//
//		idx++;
//		str = rs.getString("nefuda_ukewatasi_dt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("nefuda_ukewatasi_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("yoridori_vl");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.INTEGER);
//		}
//
//		idx++;
//		str = rs.getString("daicho_tenpo_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("daicho_honbu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("daicho_siiresaki_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("sode_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("honbu_zai_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		idx++;
//		str = rs.getString("fuji_syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.A07.getCode())){
//
//			idx++;
//			str = rs.getString("hacyu_syohin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//
//		} else if (GYOSYU_KB.equals(mst000601_GyoshuKbDictionary.GRO.getCode())) {
//
//			idx++;
//			str = rs.getString("syohin_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}
//
//		idx++;
//		str = rs.getString("yusen_bin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
////		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
//
//	}
//
//	/**
//	 * 商品マスタデータ削除用PreparedStatement
//	 * @throws Exception
//	 */
//	public PreparedStatement getPreparedSyohinDelInsSQL(MasterDataBase dataBase) throws SQLException {
//		StringBuffer sql = new StringBuffer();
//
//		sql.append("insert into ");
//		sql.append("r_syohin ");
//		sql.append("(");
//		sql.append("  hanku_cd,");
//		sql.append("  syohin_cd,");
//		sql.append("  yuko_dt,");
//		sql.append("  hacyu_syohin_kb,");
//		sql.append("  system_kb,");
//		sql.append("  gyosyu_kb,");
//		sql.append("  hinsyu_cd,");
//		sql.append("  hinmoku_cd,");
//		sql.append("  mark_group_cd,");
//		sql.append("  mark_cd,");
//		sql.append("  syohin_2_cd,");
//		sql.append("  ketasu_kb,");
//		sql.append("  maker_cd,");
//		sql.append("  hinmei_kanji_na,");
//		sql.append("  kikaku_kanji_na,");
//		sql.append("  hinmei_kana_na,");
//		sql.append("  kikaku_kana_na,");
//		sql.append("  syohin_width_qt,");
//		sql.append("  syohin_height_qt,");
//		sql.append("  syohin_depth_qt,");
//		sql.append("  siire_hinban_cd,");
//		sql.append("  bland_cd,");
//		sql.append("  yunyuhin_kb,");
//		sql.append("  santi_cd,");
//		sql.append("  maker_kibo_kakaku_vl,");
//		sql.append("  nohin_ondo_kb,");
//		sql.append("  comment_tx,");
//		sql.append("  ten_hachu_st_dt,");
//		sql.append("  ten_hachu_ed_dt,");
//		sql.append("  gentanka_vl,");
//		sql.append("  baitanka_vl,");
//		sql.append("  hachutani_irisu_qt,");
//		sql.append("  max_hachutani_qt,");
//		sql.append("  teikan_kb,");
//		sql.append("  eos_kb,");
//		sql.append("  nohin_kigen_qt,");
//		sql.append("  nohin_kigen_kb,");
//		sql.append("  mst_siyofuka_kb,");
//		sql.append("  hachu_teisi_kb,");
//		sql.append("  siiresaki_cd,");
//		sql.append("  daihyo_haiso_cd,");
//		sql.append("  ten_siiresaki_kanri_cd,");
//		sql.append("  soba_syohin_kb,");
//		sql.append("  bin_1_kb,");
//		sql.append("  hachu_pattern_1_kb,");
//		sql.append("  sime_time_1_qt,");
//		sql.append("  c_nohin_rtime_1_kb,");
//		sql.append("  ten_nohin_rtime_1_kb,");
//		sql.append("  ten_nohin_time_1_kb,");
//		sql.append("  bin_2_kb,");
//		sql.append("  hachu_pattern_2_kb,");
//		sql.append("  sime_time_2_qt,");
//		sql.append("  c_nohin_rtime_2_kb,");
//		sql.append("  ten_nohin_rtime_2_kb,");
//		sql.append("  ten_nohin_time_2_kb,");
//		sql.append("  bin_3_kb,");
//		sql.append("  hachu_pattern_3_kb,");
//		sql.append("  sime_time_3_qt,");
//		sql.append("  c_nohin_rtime_3_kb,");
//		sql.append("  ten_nohin_rtime_3_kb,");
//		sql.append("  ten_nohin_time_3_kb,");
//		sql.append("  c_nohin_rtime_kb,");
//		sql.append("  zei_kb,");
//		sql.append("  hanbai_kikan_kb,");
//		sql.append("  syohin_kb,");
//		sql.append("  buturyu_kb,");
//		sql.append("  yokomoti_kb,");
//		sql.append("  ten_groupno_cd,");
//		sql.append("  ten_zaiko_kb,");
//		sql.append("  hanbai_seisaku_kb,");
//		sql.append("  henpin_nb,");
//		sql.append("  henpin_genka_vl,");
//		sql.append("  rebate_fg,");
//		sql.append("  hanbai_st_dt,");
//		sql.append("  hanbai_ed_dt,");
//		sql.append("  hanbai_limit_qt,");
//		sql.append("  hanbai_limit_kb,");
//		sql.append("  auto_del_kb,");
//		sql.append("  got_mujyoken_fg,");
//		sql.append("  got_start_mm,");
//		sql.append("  got_end_mm,");
//		sql.append("  e_shop_kb,");
//		sql.append("  plu_send_dt,");
//		sql.append("  rec_hinmei_kanji_na,");
//		sql.append("  rec_hinmei_kana_na,");
//		sql.append("  keyplu_fg,");
//		sql.append("  pc_kb,");
//		sql.append("  daisi_no_nb,");
//		sql.append("  unit_price_tani_kb,");
//		sql.append("  unit_price_naiyoryo_qt,");
//		sql.append("  unit_price_kijun_tani_qt,");
//		sql.append("  shinazoroe_kb,");
//		sql.append("  kumisu_kb,");
//		sql.append("  nefuda_kb,");
//		sql.append("  yoridori_kb,");
//		sql.append("  yoridori_qt,");
//		sql.append("  tag_hyoji_baika_vl,");
//		sql.append("  season_cd,");
//		sql.append("  hukusyu_cd,");
//		sql.append("  style_cd,");
//		sql.append("  scene_cd,");
//		sql.append("  sex_cd,");
//		sql.append("  age_cd,");
//		sql.append("  generation_cd,");
//		sql.append("  sozai_cd,");
//		sql.append("  pattern_cd,");
//		sql.append("  oriami_cd,");
//		sql.append("  huka_kino_cd,");
//		sql.append("  size_cd,");
//		sql.append("  color_cd,");
//		sql.append("  syuzei_hokoku_kb,");
//		sql.append("  tc_jyouho_na,");
//		sql.append("  nohin_syohin_cd,");
//		sql.append("  itf_cd,");
//		sql.append("  case_irisu_qt,");
//		sql.append("  nyuka_syohin_cd,");
//		sql.append("  pack_width_qt,");
//		sql.append("  pack_heigth_qt,");
//		sql.append("  pack_depth_qt,");
//		sql.append("  pack_weight_qt,");
//		sql.append("  center_zaiko_kb,");
//		sql.append("  zaiko_hachu_saki,");
//		sql.append("  zaiko_center_cd,");
//		sql.append("  min_zaikosu_qt,");
//		sql.append("  max_zaikosu_qt,");
//		sql.append("  center_hachutani_kb,");
//		sql.append("  center_hachutani_qt,");
//		sql.append("  center_irisu_qt,");
//		sql.append("  center_weight_qt,");
//		sql.append("  tana_no_nb,");
//		sql.append("  dan_no_nb,");
//		sql.append("  keiyaku_su_qt,");
//		sql.append("  keiyaku_pattern_kb,");
//		sql.append("  keiyaku_st_dt,");
//		sql.append("  keiyaku_ed_dt,");
//		sql.append("  kijun_zaikosu_qt,");
//		sql.append("  d_zokusei_1_na,");
//		sql.append("  s_zokusei_1_na,");
//		sql.append("  d_zokusei_2_na,");
//		sql.append("  s_zokusei_2_na,");
//		sql.append("  d_zokusei_3_na,");
//		sql.append("  s_zokusei_3_na,");
//		sql.append("  d_zokusei_4_na,");
//		sql.append("  s_zokusei_4_na,");
//		sql.append("  d_zokusei_5_na,");
//		sql.append("  s_zokusei_5_na,");
//		sql.append("  d_zokusei_6_na,");
//		sql.append("  s_zokusei_6_na,");
//		sql.append("  d_zokusei_7_na,");
//		sql.append("  s_zokusei_7_na,");
//		sql.append("  d_zokusei_8_na,");
//		sql.append("  s_zokusei_8_na,");
//		sql.append("  d_zokusei_9_na,");
//		sql.append("  s_zokusei_9_na,");
//		sql.append("  d_zokusei_10_na,");
//		sql.append("  s_zokusei_10_na,");
//		sql.append("  syokai_toroku_ts,");
//		sql.append("  syokai_user_id,");
//		sql.append("  sinki_toroku_dt,");
//		sql.append("  henko_dt,");
//		sql.append("  insert_ts,");
//		sql.append("  insert_user_id,");
//		sql.append("  update_ts,");
//		sql.append("  update_user_id,");
//		//		sql.append("  update_ts,");
//		//		sql.append("  update_user_id,");
//		sql.append("  delete_fg ");
//		sql.append(") ");
//		sql.append("(");
//		sql.append("select");
//		sql.append("  hanku_cd,");
//		sql.append("  syohin_cd,");
////		↓↓移植(2006.05.22)↓↓
//		sql.append("  yuko_dt,");
////		↑↑移植(2006.05.22)↑↑
//		sql.append("  hacyu_syohin_kb,");
//		sql.append("  system_kb,");
//		sql.append("  gyosyu_kb,");
//		sql.append("  hinsyu_cd,");
//		sql.append("  hinmoku_cd,");
//		sql.append("  mark_group_cd,");
//		sql.append("  mark_cd,");
//		sql.append("  syohin_2_cd,");
//		sql.append("  ketasu_kb,");
//		sql.append("  maker_cd,");
//		sql.append("  hinmei_kanji_na,");
//		sql.append("  kikaku_kanji_na,");
//		sql.append("  hinmei_kana_na,");
//		sql.append("  kikaku_kana_na,");
//		sql.append("  syohin_width_qt,");
//		sql.append("  syohin_height_qt,");
//		sql.append("  syohin_depth_qt,");
//		sql.append("  siire_hinban_cd,");
//		sql.append("  bland_cd,");
//		sql.append("  yunyuhin_kb,");
//		sql.append("  santi_cd,");
//		sql.append("  maker_kibo_kakaku_vl,");
//		sql.append("  nohin_ondo_kb,");
//		sql.append("  comment_tx,");
//		sql.append("  ten_hachu_st_dt,");
//		sql.append("  ten_hachu_ed_dt,");
////		↓↓移植(2006.05.22)↓↓
//// ↓↓(2005.10.11)カンマ制御処理追加
//		sql.append("  gentanka_vl,");
//		sql.append("  baitanka_vl,");
//// ↑↑(2005.10.11)カンマ制御処理追加
////		↑↑移植(2006.05.22)↑↑
//		sql.append("  hachutani_irisu_qt,");
//		sql.append("  max_hachutani_qt,");
//		sql.append("  teikan_kb,");
//		sql.append("  eos_kb,");
//		sql.append("  nohin_kigen_qt,");
//		sql.append("  nohin_kigen_kb,");
//		sql.append("  mst_siyofuka_kb,");
//		sql.append("  hachu_teisi_kb,");
//		sql.append("  siiresaki_cd,");
//		sql.append("  daihyo_haiso_cd,");
//		sql.append("  ten_siiresaki_kanri_cd,");
//		sql.append("  soba_syohin_kb,");
//		sql.append("  bin_1_kb,");
//		sql.append("  hachu_pattern_1_kb,");
//		sql.append("  sime_time_1_qt,");
//		sql.append("  c_nohin_rtime_1_kb,");
//		sql.append("  ten_nohin_rtime_1_kb,");
//		sql.append("  ten_nohin_time_1_kb,");
//		sql.append("  bin_2_kb,");
//		sql.append("  hachu_pattern_2_kb,");
//		sql.append("  sime_time_2_qt,");
//		sql.append("  c_nohin_rtime_2_kb,");
//		sql.append("  ten_nohin_rtime_2_kb,");
//		sql.append("  ten_nohin_time_2_kb,");
//		sql.append("  bin_3_kb,");
//		sql.append("  hachu_pattern_3_kb,");
//		sql.append("  sime_time_3_qt,");
//		sql.append("  c_nohin_rtime_3_kb,");
//		sql.append("  ten_nohin_rtime_3_kb,");
//		sql.append("  ten_nohin_time_3_kb,");
//		sql.append("  c_nohin_rtime_kb,");
//		sql.append("  zei_kb,");
//		sql.append("  hanbai_kikan_kb,");
//		sql.append("  syohin_kb,");
//		sql.append("  buturyu_kb,");
//		sql.append("  yokomoti_kb,");
//		sql.append("  ten_groupno_cd,");
//		sql.append("  ten_zaiko_kb,");
//		sql.append("  hanbai_seisaku_kb,");
//		sql.append("  henpin_nb,");
//		sql.append("  henpin_genka_vl,");
//		sql.append("  rebate_fg,");
//		sql.append("  hanbai_st_dt,");
//		sql.append("  hanbai_ed_dt,");
//		sql.append("  hanbai_limit_qt,");
//		sql.append("  hanbai_limit_kb,");
//		sql.append("  auto_del_kb,");
//		sql.append("  got_mujyoken_fg,");
//		sql.append("  got_start_mm,");
//		sql.append("  got_end_mm,");
//		sql.append("  e_shop_kb,");
//		sql.append("  plu_send_dt,");
//		sql.append("  rec_hinmei_kanji_na,");
//		sql.append("  rec_hinmei_kana_na,");
//		sql.append("  keyplu_fg,");
//		sql.append("  pc_kb,");
//		sql.append("  daisi_no_nb,");
//		sql.append("  unit_price_tani_kb,");
//		sql.append("  unit_price_naiyoryo_qt,");
//		sql.append("  unit_price_kijun_tani_qt,");
//		sql.append("  shinazoroe_kb,");
//		sql.append("  kumisu_kb,");
//		sql.append("  nefuda_kb,");
//		sql.append("  yoridori_kb,");
//		sql.append("  yoridori_qt,");
//		sql.append("  tag_hyoji_baika_vl,");
//		sql.append("  season_cd,");
//		sql.append("  hukusyu_cd,");
//		sql.append("  style_cd,");
//		sql.append("  scene_cd,");
//		sql.append("  sex_cd,");
//		sql.append("  age_cd,");
//		sql.append("  generation_cd,");
//		sql.append("  sozai_cd,");
//		sql.append("  pattern_cd,");
//		sql.append("  oriami_cd,");
//		sql.append("  huka_kino_cd,");
//		sql.append("  size_cd,");
//		sql.append("  color_cd,");
//		sql.append("  syuzei_hokoku_kb,");
//		sql.append("  tc_jyouho_na,");
//		sql.append("  nohin_syohin_cd,");
//		sql.append("  itf_cd,");
//		sql.append("  case_irisu_qt,");
//		sql.append("  nyuka_syohin_cd,");
//		sql.append("  pack_width_qt,");
//		sql.append("  pack_heigth_qt,");
//		sql.append("  pack_depth_qt,");
//		sql.append("  pack_weight_qt,");
//		sql.append("  center_zaiko_kb,");
//		sql.append("  zaiko_hachu_saki,");
//		sql.append("  zaiko_center_cd,");
//		sql.append("  min_zaikosu_qt,");
//		sql.append("  max_zaikosu_qt,");
//		sql.append("  center_hachutani_kb,");
//		sql.append("  center_hachutani_qt,");
//		sql.append("  center_irisu_qt,");
//		sql.append("  center_weight_qt,");
//		sql.append("  tana_no_nb,");
//		sql.append("  dan_no_nb,");
//		sql.append("  keiyaku_su_qt,");
//		sql.append("  keiyaku_pattern_kb,");
//		sql.append("  keiyaku_st_dt,");
//		sql.append("  keiyaku_ed_dt,");
//		sql.append("  kijun_zaikosu_qt,");
//		sql.append("  d_zokusei_1_na,");
//		sql.append("  s_zokusei_1_na,");
//		sql.append("  d_zokusei_2_na,");
//		sql.append("  s_zokusei_2_na,");
//		sql.append("  d_zokusei_3_na,");
//		sql.append("  s_zokusei_3_na,");
//		sql.append("  d_zokusei_4_na,");
//		sql.append("  s_zokusei_4_na,");
//		sql.append("  d_zokusei_5_na,");
//		sql.append("  s_zokusei_5_na,");
//		sql.append("  d_zokusei_6_na,");
//		sql.append("  s_zokusei_6_na,");
//		sql.append("  d_zokusei_7_na,");
//		sql.append("  s_zokusei_7_na,");
//		sql.append("  d_zokusei_8_na,");
//		sql.append("  s_zokusei_8_na,");
//		sql.append("  d_zokusei_9_na,");
//		sql.append("  s_zokusei_9_na,");
//		sql.append("  d_zokusei_10_na,");
//		sql.append("  s_zokusei_10_na,");
//		sql.append("  syokai_toroku_ts,");
//		sql.append("  syokai_user_id,");
//		sql.append("  sinki_toroku_dt,");
//		sql.append("'").append(MasterDT).append("',");
////		↓↓移植(2006.05.22)↓↓
//		sql.append("'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
//		sql.append("'").append(BATCH_ID).append("',");
//		sql.append("'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
////		↑↑移植(2006.05.22)↑↑
//		sql.append("'").append(BATCH_ID).append("',");
//		sql.append("'").append(mst000801_DelFlagDictionary.IRU.getCode()).append("' ");
//		sql.append("from");
//		sql.append("  r_syohin ");
//		sql.append("where ");
//		sql.append("  hanku_cd = ? and");
//		sql.append("  syohin_cd = ? and");
//		sql.append("  yuko_dt = ? )");
//
//		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
//		return pstmt;
//	}
//
	/**
	 * 商品マスタデータ削除SQL
	 * @throws Exception
	 */
	public void setPrepareSyohinDelInsSQL(PreparedStatement pstmt, ResultSet rs, String delete_dt, String bumon_cd) throws SQLException {

		int idx = 0;

//		↓↓移植(2006.05.22)↓↓
//		//有効日
//		idx++;
//		pstmt.setString(idx, rs.getString("yuko_dt"));
//		↑↑移植(2006.05.22)↑↑

//		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
		//部門コード
		idx++;
//		pstmt.setString(idx, rs.getString(hanku_cd));
		pstmt.setString(idx, rs.getString(bumon_cd));
//		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		//有効日（削除対象の有効日）
		idx++;
		pstmt.setString(idx, delete_dt);
	}

//	/**
//	 * 商品マスタデータ削除用PreparedStatement
//	 * @throws Exception
//	 */
//	public PreparedStatement getPreparedSyohinDelUpSQL(MasterDataBase dataBase) throws SQLException {
//		StringBuffer sql = new StringBuffer();
//
//		sql.append("update ");
//		sql.append("  r_syohin ");
//		sql.append("set ");
//		sql.append("  henko_dt = '").append(MasterDT).append("',");
////		↓↓移植(2006.05.22)↓↓
//		sql.append("  update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
////		↑↑移植(2006.05.22)↑↑
//		sql.append("  update_user_id = '").append(BATCH_ID).append("',");
//		sql.append("  delete_fg = '").append(mst000801_DelFlagDictionary.IRU.getCode()).append("' ");
//		sql.append("where ");
//		sql.append("  hanku_cd = ? and");
//		sql.append("  syohin_cd = ? and");
//		sql.append("  yuko_dt = ? ");
//
//		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
//		return pstmt;
//	}

	/**
	 * 商品マスタデータ削除SQL
	 * @throws Exception
	 */
	public void setPrepareSyohinDelUpSQL(PreparedStatement pstmt, ResultSet rs, String bumon_cd) throws SQLException {

		int idx = 0;

//		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓

		//更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));
		//部門コード
		idx++;
		//pstmt.setString(idx, rs.getString(hanku_cd));
		pstmt.setString(idx, rs.getString(bumon_cd));
//		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		//有効日
		idx++;
		// ===BEGIN=== Modify by kou 2006/8/4
		// 有効開始日が未入力の場合、管理日付の翌日でセットする
		// 商品マスタに当日（管理日付の翌日）のレコードがある場合の配慮
		//pstmt.setString(idx, rs.getString("yuko_dt"));
		if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
			String startDt = DateChanger.addDate(MasterDT, 1);
			pstmt.setString(idx,startDt);
		}else{
			pstmt.setString(idx, rs.getString("yuko_dt"));
		}
		// ===END=== Modify by kou 2006/8/4
	}

//	/**
//	 * 自動採番マスタ（8桁商品）チェック用PreparedStatement
//	 * @throws
//	 */
//	public PreparedStatement getPrepared8KetaSQL(MasterDataBase dataBase) throws SQLException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("select ");
//		sql.append(" jyotai_kanri_kb ");
//		sql.append("from");
//		sql.append(" r_saiban_8keta ");
//		sql.append("where ");
//		sql.append(" hinsyu_cd = ? and");
//		sql.append(" tanpin_cd = ?");
//
//		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
//		return pstmt;
//	}
//
//	/**
//	 * 自動採番マスタ（8桁商品）チェックSQL
//	 * @throws Exception
//	 */
//	public void setPrepared8KetaSQL(PreparedStatement pstmt, String hinsyu_cd, String tanpin_cd) throws SQLException {
//		int idx = 0;
//		//品種コード
//		idx++;
//		pstmt.setString(idx, hinsyu_cd);
//
//		//単品コード(チェックデジットを外す)
//		idx++;
//		pstmt.setString(idx, tanpin_cd.substring(0, 3));
//	}
//
//	/**
//	 * 自動採番マスタ（インストア商品）チェック用PreparedStatement
//	 * @throws
//	 */
//	public PreparedStatement getPreparedInstoreSQL(MasterDataBase dataBase) throws SQLException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("select ");
//		sql.append(" jyotai_kanri_kb ");
//		sql.append("from");
//		sql.append(" r_saiban_instore ");
//		sql.append("where ");
//		sql.append(" tanpin_cd = ?");
//
//		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
//		return pstmt;
//	}
//
//	/**
//	 * 自動採番マスタ（インストア商品）チェックSQL
//	 * @throws Exception
//	 */
//	public void setPreparedSyohinInstoreSQL(PreparedStatement pstmt, String tanpin_cd) throws SQLException {
//		int idx = 0;
//		//単品コード
//		idx++;
//		pstmt.setString(idx, tanpin_cd);
//	}
//
//	/**
//	 * 自動採番マスタ（8桁商品）更新用PreparedStatement
//	 * @throws
//	 */
//	public PreparedStatement getPrepared8KetaUpSQL(MasterDataBase dataBase) throws SQLException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("update");
//		sql.append(" r_saiban_8keta ");
//		sql.append("set");
//		sql.append(" jyotai_kanri_kb = ?,");
////		↓↓移植(2006.05.22)↓↓
//		sql.append("  update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
////		↑↑移植(2006.05.22)↑↑
//		sql.append(" update_user_id = ? ");
//		sql.append("where ");
//		sql.append(" hinsyu_cd = ? and");
//		sql.append(" tanpin_cd = ?");
//
//		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
//		return pstmt;
//	}
//
//	/**
//	 * 自動採番マスタ（8桁商品）更新SQL
//	 * @throws Exception
//	 */
//	public void setPrepared8KetaUpSQL(PreparedStatement pstmt, String jyotai_kanri_kb, String userId, String hinsyu_cd, String tanpin_cd) throws SQLException {
//		int idx = 0;
//		//状態管理区分
//		idx++;
//		pstmt.setString(idx, jyotai_kanri_kb);
//		//ユーザID
//		idx++;
//		pstmt.setString(idx, userId);
//		//品種コード
//		idx++;
//		pstmt.setString(idx, hinsyu_cd);
//
//		//単品コード(チェックデジットを外す)
//		idx++;
//		pstmt.setString(idx, tanpin_cd.substring(0, 3));
//	}
//
//	/**
//	 * 自動採番マスタ（インストア商品）更新用PreparedStatement
//	 * @throws
//	 */
//	public PreparedStatement getPreparedInstoreUpSQL(MasterDataBase dataBase) throws SQLException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("update");
//		sql.append(" r_saiban_instore ");
//		sql.append("set");
//		sql.append(" jyotai_kanri_kb = ?,");
////		↓↓移植(2006.05.22)↓↓
//		sql.append(" update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
////		↑↑移植(2006.05.22)↑↑
//		sql.append(" update_user_id = ? ");
//		sql.append("where ");
//		sql.append(" tanpin_cd = ?");
//
//		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
//		return pstmt;
//	}
//
//	/**
//	 * 自動採番マスタ（インストア商品）更新SQL
//	 * @throws Exception
//	 */
//	public void setPreparedSyohinInstoreUpSQL(PreparedStatement pstmt, String jyotai_kanri_kb, String userId, String tanpin_cd) throws SQLException {
//		int idx = 0;
//		//状態管理区分
//		idx++;
//		pstmt.setString(idx, jyotai_kanri_kb);
//		//ユーザID
//		idx++;
//		pstmt.setString(idx, userId);
//		//単品コード
//		idx++;
//		pstmt.setString(idx, tanpin_cd);
//	}
//	/**
//	 * 権限チェックSQL
//	 * @throws Exception
//	 */
//	public String getKengenSQL() {
//		StringBuffer sql = new StringBuffer();
//
//		sql.append("   case hanku_cd2.d_gyosyu_cd");
//		sql.append("       when '0011' then '01'");
//		sql.append("       when '0044' then '01'");
//		// 2006/01/17 D.Matsumoto 新規追加開始 4桁販区対応
//		sql.append("       when '0057' then '01'");
//		// 2006/01/17 D.Matsumoto 新規追加終了 4桁販区対応
//		sql.append("       when '0061' then '01'");
//		sql.append("       when '0022' then '02'");
//		sql.append("       when '0033' then '03'");
//		sql.append("       else '02'");
//		sql.append("   end as d_gyosyu_cd,"); //大業種（商品体系マスタ）
//		sql.append("   hanku_cd2.s_gyosyu_cd as s_gyosyu_cd,"); //小業種（商品体系マスタ）
//
//		return sql.toString();
//	}
//
//	public String getMakerCd(ResultSet rs) throws SQLException {
//		String syohin_cd = rs.getString("syohin_cd").trim();
//		String nohin_syohin_cd = rs.getString("nohin_syohin_cd");
//		String maker_cd = rs.getString("maker_cd");
//
//		//商品コードよりメーカーコードを取得
//		if (syohin_cd.substring(0, 2).equals(mst000101_ConstDictionary.JAN_MAKER_45) || syohin_cd.substring(0, 2).equals(mst000101_ConstDictionary.JAN_MAKER_49)) {
//			//"49"始まりか"450"～"455"始まり
//			if (syohin_cd.substring(0, 2).equals(mst000101_ConstDictionary.JAN_MAKER_49) || (Integer.parseInt(syohin_cd.substring(0, 3)) >= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_7_ST) && Integer.parseInt(syohin_cd.substring(0, 3)) <= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_7_ED))) {
//				//短縮JANコード（8桁）の場合
//				if (syohin_cd.length() == 8) {
//					maker_cd = syohin_cd.substring(0, 6);
//				} else if (syohin_cd.length() == 13) {
//					//JANコード（13桁）の場合
//					maker_cd = syohin_cd.substring(0, 7);
//				}
//			} else if (Integer.parseInt(syohin_cd.substring(0, 3)) >= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_9_ST) && Integer.parseInt(syohin_cd.substring(0, 3)) <= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_9_ED)) {
//				//"456"～"459"始まり
//				maker_cd = syohin_cd.substring(0, 9);
//			}
//		} else if (nohin_syohin_cd != null && (nohin_syohin_cd.substring(0, 2).equals(mst000101_ConstDictionary.JAN_MAKER_45) || nohin_syohin_cd.substring(0, 2).equals(mst000101_ConstDictionary.JAN_MAKER_49))) {
//			nohin_syohin_cd = nohin_syohin_cd.trim();
//			//納品商品コードよりメーカーコードを取得
//			//"49"始まりか"450"～"455"始まり
//			if (nohin_syohin_cd.substring(0, 2).equals(mst000101_ConstDictionary.JAN_MAKER_49) || (Integer.parseInt(nohin_syohin_cd.substring(0, 3)) >= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_7_ST) && Integer.parseInt(nohin_syohin_cd.substring(0, 3)) <= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_7_ED))) {
//				//短縮JANコード（8桁）の場合
//				if (nohin_syohin_cd.length() == 8) {
//					maker_cd = nohin_syohin_cd.substring(0, 6);
//				} else if (nohin_syohin_cd.length() == 13) {
//					//JANコード（13桁）の場合
//					maker_cd = nohin_syohin_cd.substring(0, 7);
//				}
//			} else if (Integer.parseInt(nohin_syohin_cd.substring(0, 3)) >= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_9_ST) && Integer.parseInt(nohin_syohin_cd.substring(0, 3)) <= Integer.parseInt(mst000101_ConstDictionary.JAN_MAKER_9_ED)) {
//				//"456"～"459"（13桁）始まり
//				maker_cd = nohin_syohin_cd.substring(0, 9);
//			}
//		}
//
//		return maker_cd;
//	}
//
//	private boolean isNotBlank(String val) {
//		if (val != null && !val.trim().equals(deleteString) && val.trim().length() > 0) {
//			return true;
//		}
//		return false;
//	}



	public PreparedStatement getPreparedSyohinInsSQL(MasterDataBase dataBase, String strBatchId)  throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_SYOHIN ");
		sql.append("( ");
		sql.append("    BUNRUI1_CD, ");						// 分類１コード
		sql.append("    SYOHIN_CD, ");						// 商品コード
		sql.append("    YUKO_DT, ");						// 有効日
		sql.append("    SYSTEM_KB, ");						// システム区分
		sql.append("    GYOSYU_KB, ");						// 業種区分
		sql.append("    SYOHIN_KB, ");						// 商品区分
		sql.append("    KETASU_KB, ");						// 桁数区分
		sql.append("    BUNRUI2_CD, ");						// 分類２コード
		sql.append("    BUNRUI5_CD, ");						// 分類５コード
		sql.append("    HINMOKU_CD, ");						// 品目
		sql.append("    SYOHIN_2_CD, ");					// 商品コード２
		sql.append("    ZAIKO_SYOHIN_CD, ");				// 在庫用商品集計コード
		sql.append("    JYOHO_SYOHIN_CD, ");				// 情報系用商品集計コード
		sql.append("    MAKER_CD, ");						// ＪＡＮメーカーコード
		sql.append("    HINMEI_KANJI_NA, ");				// 漢字品名
		sql.append("    KIKAKU_KANJI_NA, ");				// 漢字規格
		sql.append("    KIKAKU_KANJI_2_NA, ");				// 漢字規格２
		sql.append("    REC_HINMEI_KANJI_NA, ");			// POSレシート品名(漢字)
		sql.append("    HINMEI_KANA_NA, ");					// カナ品名
		sql.append("    KIKAKU_KANA_NA, ");					// カナ規格
		sql.append("    KIKAKU_KANA_2_NA, ");				// カナ規格２
		sql.append("    REC_HINMEI_KANA_NA, ");				// POSレシート品名(カナ)
		sql.append("    SYOHIN_WIDTH_QT, ");				// 商品サイズ(幅)
		sql.append("    SYOHIN_HEIGHT_QT, ");				// 商品サイズ(高さ)
		sql.append("    SYOHIN_DEPTH_QT, ");				// 商品サイズ(奥行き)
		sql.append("    E_SHOP_KB, ");						// Eショップ区分
		sql.append("    PB_KB, ");							// PB区分
		sql.append("    SUBCLASS_CD, ");					// サブクラスコード
		sql.append("    HAIFU_CD, ");						// 配布コード
		sql.append("    ZEI_KB, ");							// 税区分
		sql.append("    TAX_RATE_KB, ");					// 税率区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		sql.append("    BAIKA_HAISHIN_FG, ");				// 売価配信区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)
		sql.append("    GENTANKA_VL, ");					// 原価単価
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    GENTANKA_NUKI_VL, ");				// 原価単価(税抜)
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    BAITANKA_VL, ");					// 売価単価
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    BAITANKA_NUKI_VL, ");				// 売価単価(税抜)
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    TOSYO_BAIKA_VL, ");					// 当初売価
		sql.append("    PRE_GENTANKA_VL, ");				// 前回原価単価
		sql.append("    PRE_BAITANKA_VL, ");				// 前回売価単価
		sql.append("    TOKUBETU_GENKA_VL, ");				// 特別原価
		sql.append("    MAKER_KIBO_KAKAKU_VL, ");			// メーカー希望小売り価格
		sql.append("    SIIRESAKI_CD, ");					// 仕入先コード
		sql.append("    DAIHYO_HAISO_CD, ");				// 代表配送先コード
		sql.append("    TEN_SIIRESAKI_KANRI_CD, ");			// 店別仕入先管理コード
		sql.append("    SIIRE_HINBAN_CD, ");				// 仕入先品番
		sql.append("    HACYU_SYOHIN_KB, ");				// 発注商品コード区分
		sql.append("    HACYU_SYOHIN_CD, ");				// 発注商品コード
		sql.append("    EOS_KB, ");							// EOS区分
		sql.append("    HACHU_TANI_NA, ");					// 発注単位呼称
		sql.append("    HANBAI_TANI, ");					// 販売単位呼称
		sql.append("    HACHUTANI_IRISU_QT, ");				// 発注単位(入数)
		sql.append("    MAX_HACHUTANI_QT, ");				// 最大発注単位数
		sql.append("    CASE_HACHU_KB, ");					// ケース発注区分
		sql.append("    BARA_IRISU_QT, ");					// バラ入数
		sql.append("    TEN_HACHU_ST_DT, ");				// 店舗発注開始日
		sql.append("    TEN_HACHU_ED_DT, ");				// 店舗発注終了日
		sql.append("    HANBAI_ST_DT, ");					// 販売開始日
		sql.append("    HANBAI_ED_DT, ");					// 販売終了日
		sql.append("    HANBAI_KIKAN_KB, ");				// 販売期間区分
		sql.append("    TEIKAN_KB, ");						// 定貫区分
		sql.append("    SOBA_SYOHIN_KB, ");					// 相場商品区分
		sql.append("    NOHIN_KIGEN_KB, ");					// 納品期限区分
		sql.append("    NOHIN_KIGEN_QT, ");					// 納品期限
		sql.append("    BIN_1_KB, ");						// ①便区分
		sql.append("    HACHU_PATTERN_1_KB, ");				// ①発注パターン区分
		sql.append("    SIME_TIME_1_QT, ");					// ①締め時間
		sql.append("    C_NOHIN_RTIME_1_KB, ");				// ①センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_1_KB, ");			// ①店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_1_KB, ");			// ①店納品時間帯
		sql.append("    BIN_2_KB, ");						// ②便区分
		sql.append("    HACHU_PATTERN_2_KB, ");				// ②発注パターン区分
		sql.append("    SIME_TIME_2_QT, ");					// ②締め時間
		sql.append("    C_NOHIN_RTIME_2_KB, ");				// ②センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_2_KB, ");			// ②店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_2_KB, ");			// ②店納品時間帯
		sql.append("    BIN_3_KB, ");						// ③便区分
		sql.append("    HACHU_PATTERN_3_KB, ");				// ③発注パターン区分
		sql.append("    SIME_TIME_3_QT, ");					// ③締め時間
		sql.append("    C_NOHIN_RTIME_3_KB, ");				// ③センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_3_KB, ");			// ③店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_3_KB, ");			// ③店納品時間帯
		sql.append("    C_NOHIN_RTIME_KB, ");				// センタ納品リードタイム
		sql.append("    YUSEN_BIN_KB, ");					// 優先便区分
		sql.append("    F_BIN_KB, ");						// F便区分
		sql.append("    BUTURYU_KB, ");						// 物流区分
		sql.append("    GOT_MUJYOKEN_FG, ");				// GOT無条件表示対象
		sql.append("    GOT_START_MM, ");					// 季節開始月日
		sql.append("    GOT_END_MM, ");						// 季節終了月日
		sql.append("    HACHU_TEISI_KB, ");					// 発注停止区分
		sql.append("    CENTER_ZAIKO_KB, ");				// センター在庫区分
		sql.append("    NOHIN_SYOHIN_CD, ");				// 納品商品コード
		sql.append("    NYUKA_SYOHIN_CD, ");				// 入荷時商品コード
		sql.append("    NYUKA_SYOHIN_2_CD, ");				// 入荷時商品コード２
		sql.append("    ITF_CD, ");							// ITFコード
		sql.append("    GTIN_CD, ");						// GTINコード
		sql.append("    VENDER_MAKER_CD, ");				// ベンダーメーカーコード
		sql.append("    ZAIKO_CENTER_CD, ");				// 在庫センターコード
		sql.append("    ZAIKO_HACHU_SAKI, ");				// 在庫補充発注先
		sql.append("    CENTER_WEIGHT_QT, ");				// センター重量
		sql.append("    PACK_WIDTH_QT, ");					// 外箱サイズ幅
		sql.append("    PACK_HEIGTH_QT, ");					// 外箱サイズ高さ
		sql.append("    PACK_DEPTH_QT, ");					// 外箱サイズ奥行き
		sql.append("    PACK_WEIGHT_QT, ");					// 外箱重量
		sql.append("    CENTER_HACHUTANI_KB, ");			// センター発注単位区分
		sql.append("    CENTER_HACHUTANI_QT, ");			// センター発注単位数
		sql.append("    CENTER_BARA_IRISU_QT, ");			// センターバラ入数
		sql.append("    CENTER_IRISU_QT, ");				// 最低入数
		sql.append("    CASE_IRISU_QT, ");					// ケース入り数
		sql.append("    CENTER_IRISU_2_QT, ");				// 梱り合せ数
		sql.append("    MIN_ZAIKOSU_QT, ");					// 最小在庫数
		sql.append("    MAX_ZAIKOSU_QT, ");					// 最大在庫数
		sql.append("    KIJUN_ZAIKOSU_QT, ");				// 基準在庫日数
		sql.append("    MIN_ZAIKONISSU_QT, ");				// 最小在庫日数
		sql.append("    MAX_ZAIKONISSU_QT, ");				// 最大在庫日数
		sql.append("    CENTER_KYOYO_KB, ");				// センター許容区分
		sql.append("    CENTER_KYOYO_DT, ");				// センター許容日
		sql.append("    CENTER_SYOMI_KIKAN_KB, ");			// センター賞味期間区分
		sql.append("    CENTER_SYOMI_KIKAN_DT, ");			// センター賞味期間
		sql.append("    TEN_GROUPNO_CD, ");					// 店グルーピングNO(物流）
		sql.append("    TC_JYOUHO_NA, ");					// TC情報
		sql.append("    NOHIN_ONDO_KB, ");					// 納品温度帯
		sql.append("    YOKOMOTI_KB, ");					// 横もち区分
		sql.append("    SHINAZOROE_KB, ");					// 品揃区分
		sql.append("    HONBU_ZAI_KB, ");					// 本部在庫区分
		sql.append("    TEN_ZAIKO_KB, ");					// 店在庫区分
		sql.append("    HANBAI_SEISAKU_KB, ");				// 販売政策区分
		sql.append("    HENPIN_NB, ");						// 返品契約書NO
		sql.append("    HENPIN_GENKA_VL, ");				// 返品原価
		sql.append("    CGC_HENPIN_KB, ");					// CGC返品区分
		sql.append("    HANBAI_LIMIT_KB, ");				// 販売期限区分
		sql.append("    HANBAI_LIMIT_QT, ");				// 販売期限
		sql.append("    PLU_SEND_DT, ");					// PLU送信日
		sql.append("    KEYPLU_FG, ");						// キーPLU対象
		sql.append("    PLU_KB, ");							// PLU区分
		sql.append("    SYUZEI_HOKOKU_KB, ");				// 酒税報告分類
		sql.append("    SAKE_NAIYORYO_QT, ");				// 酒内容量
		sql.append("    TAG_HYOJI_BAIKA_VL, ");				// タグ表示売価
		sql.append("    KESHI_BAIKA_VL, ");					// 消札売価
		sql.append("    YORIDORI_KB, ");					// よりどり種類
		sql.append("    YORIDORI_VL, ");					// よりどり価格
		sql.append("    YORIDORI_QT, ");					// よりどり数量
		sql.append("    BLAND_CD, ");						// ブランドコード
		sql.append("    SEASON_CD, ");						// シーズンコード
		sql.append("    HUKUSYU_CD, ");						// 服種コード
		sql.append("    STYLE_CD, ");						// スタイルコード
		sql.append("    SCENE_CD, ");						// シーンコード
		sql.append("    SEX_CD, ");							// 性別コード
		sql.append("    AGE_CD, ");							// 年代コード
		sql.append("    GENERATION_CD, ");					// 世代コード
		sql.append("    SOZAI_CD, ");						// 素材コード
		sql.append("    PATTERN_CD, ");						// パターンコード
		sql.append("    ORIAMI_CD, ");						// 織り編みコード
		sql.append("    HUKA_KINO_CD, ");					// 付加機能コード
		sql.append("    SODE_CD, ");						// 袖丈コード
		sql.append("    SIZE_CD, ");						// サイズコード
		sql.append("    COLOR_CD, ");						// カラーコード
		sql.append("    KEIYAKU_SU_QT, ");					// 契約数
		sql.append("    KEIYAKU_PATTERN_KB, ");				// 契約パターン
		sql.append("    KEIYAKU_ST_DT, ");					// 契約開始期間
		sql.append("    KEIYAKU_ED_DT, ");					// 契約終了期間
		sql.append("    KUMISU_KB, ");						// 組数区分
		sql.append("    NEFUDA_KB, ");						// 値札区分
		sql.append("    NEFUDA_UKEWATASI_DT, ");			// 値札受渡日
		sql.append("    NEFUDA_UKEWATASI_KB, ");			// 値札受渡方法
		sql.append("    PC_KB, ");							// PC発行区分
		sql.append("    DAISI_NO_NB, ");					// 台紙NO
		sql.append("    UNIT_PRICE_TANI_KB, ");				// ユニットプライス-単位区分
		sql.append("    UNIT_PRICE_NAIYORYO_QT, ");			// ユニットプライス-内容量
		sql.append("    UNIT_PRICE_KIJUN_TANI_QT, ");		// ユニットプライス-基準単位量
		sql.append("    SYOHI_KIGEN_KB, ");					// 消費期限区分
		sql.append("    SYOHI_KIGEN_DT, ");					// 消費期限
		sql.append("    DAICHO_TENPO_KB, ");				// 商品台帳(店舗)
		sql.append("    DAICHO_HONBU_KB, ");				// 商品台帳(本部)
		sql.append("    DAICHO_SIIRESAKI_KB, ");			// 商品台帳(仕入先)
		sql.append("    TANA_NO_NB, ");						// 棚NO
		sql.append("    DAN_NO_NB, ");						// 段NO
		sql.append("    REBATE_FG, ");						// リベート対象フラグ
		sql.append("    MARK_GROUP_CD, ");					// マークグループ
		sql.append("    MARK_CD, ");						// マークコード
		sql.append("    YUNYUHIN_KB, ");					// 輸入品区分
		sql.append("    SANTI_CD, ");						// 原産国/産地コード
		sql.append("    D_ZOKUSEI_1_NA, ");					// 大属性１
		sql.append("    S_ZOKUSEI_1_NA, ");					// 小属性１
		sql.append("    D_ZOKUSEI_2_NA, ");					// 大属性２
		sql.append("    S_ZOKUSEI_2_NA, ");					// 小属性２
		sql.append("    D_ZOKUSEI_3_NA, ");					// 大属性３
		sql.append("    S_ZOKUSEI_3_NA, ");					// 小属性３
		sql.append("    D_ZOKUSEI_4_NA, ");					// 大属性４
		sql.append("    S_ZOKUSEI_4_NA, ");					// 小属性４
		sql.append("    D_ZOKUSEI_5_NA, ");					// 大属性５
		sql.append("    S_ZOKUSEI_5_NA, ");					// 小属性５
		sql.append("    D_ZOKUSEI_6_NA, ");					// 大属性６
		sql.append("    S_ZOKUSEI_6_NA, ");					// 小属性６
		sql.append("    D_ZOKUSEI_7_NA, ");					// 大属性７
		sql.append("    S_ZOKUSEI_7_NA, ");					// 小属性７
		sql.append("    D_ZOKUSEI_8_NA, ");					// 大属性８
		sql.append("    S_ZOKUSEI_8_NA, ");					// 小属性８
		sql.append("    D_ZOKUSEI_9_NA, ");					// 大属性９
		sql.append("    S_ZOKUSEI_9_NA, ");					// 小属性９
		sql.append("    D_ZOKUSEI_10_NA, ");				// 大属性１０
		sql.append("    S_ZOKUSEI_10_NA, ");				// 小属性１０
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    FREE_1_KB, ");						// 任意区分１
		sql.append("    FREE_2_KB, ");						// 任意区分２
		sql.append("    FREE_3_KB, ");						// 任意区分３
		sql.append("    FREE_4_KB, ");						// 任意区分４
		sql.append("    FREE_5_KB, ");						// 任意区分５
		sql.append("    COMMENT_1_TX, ");					// コメント１
		sql.append("    COMMENT_2_TX, ");					// コメント２
		sql.append("    FREE_CD, ");						// 任意コード
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    FUJI_SYOHIN_KB, ");					// F商品区分
		sql.append("    COMMENT_TX, ");						// コメント
		sql.append("    AUTO_DEL_KB, ");					// 自動削除対象区分
		sql.append("    MST_SIYOFUKA_KB, ");				// マスタ使用不可区分
		sql.append("    HAIBAN_FG, ");						// 廃番実施フラグ
		sql.append("    SINKI_TOROKU_DT, ");				// 新規登録日
		sql.append("    HENKO_DT, ");						// 変更日
		// No.158 MSTB011 Add 2015.12.15 TU.TD (S)
		sql.append("    NENREI_SEIGEN_KB, ");				//年齢制限区分
		sql.append("    NENREI, ");							//年齢
		sql.append("    KAN_KB, ");							//瓶区分
		sql.append("    HOSHOUKIN, ");						//保証金
		// No.158 MSTB011 Add 2015.12.15 TU.TD (E)
		sql.append("    SYOKAI_TOROKU_TS, ");				// 初回登録日
		sql.append("    SYOKAI_USER_ID, ");					// 初回登録社員ID
		sql.append("    INSERT_TS, ");						// 作成年月日
		sql.append("    INSERT_USER_ID, ");					// 作成者ID
		sql.append("    UPDATE_TS, ");						// 更新年月日
		sql.append("    UPDATE_USER_ID, ");					// 更新者ID
		sql.append("    BATCH_UPDATE_TS, ");				// バッチ更新年月日
		sql.append("    BATCH_UPDATE_ID, ");				// バッチ更新者ID
		// 2016/10/20 T.Arimoto #2254対応（S) 
		//sql.append("    DELETE_FG ");						// 削除フラグ
		sql.append("    DELETE_FG, ");						// 削除フラグ
		sql.append("    CUR_GENERATION_YUKO_DT, ");			// 原価単価有効日(現世代)
		sql.append("    CUR_GENERATION_GENTANKA_VL, ");		// 原価単価(現世代)
		sql.append("    ONE_GENERATION_YUKO_DT, ");			// 原価単価有効日(1世代前)
		sql.append("    ONE_GENERATION_GENTANKA_VL, ");		// 原価単価(1世代前)
		sql.append("    TWO_GENERATION_YUKO_DT, ");			// 原価単価有効日(2世代前)
		sql.append("    TWO_GENERATION_GENTANKA_VL, ");		// 原価単価(2世代前)
		sql.append("    SIIRE_ZEI_KB, ");					// 仕入税区分
		sql.append("    SIIRE_TAX_RATE_KB, ");				// 仕入税率区分
		sql.append("    OROSI_ZEI_KB, ");					// 卸税区分
		sql.append("    OROSI_TAX_RATE_KB, ");				// 卸税率区分
		sql.append("    OROSI_BAITANKA_VL, ");				// 卸売単価
		sql.append("    OROSI_BAITANKA_NUKI_VL, ");			// 卸売価単価（税抜）
		sql.append("    MIN_HACHU_QT, ");					// 最低発注数
		sql.append("    HACHU_FUKA_FLG, ");					// 発注不可フラグ
		sql.append("    PER_TXT, ");						// 規格内容
		sql.append("    SYOHI_KIGEN_HYOJI_PATTER, ");		// 消費期限表示パターン
		sql.append("    PLU_HANEI_TIME ");					// PLU反映時間
		// 2016/10/20 T.Arimoto #2254対応（E) 
		// 2017.04.03 M.Akagi #4509 (S)
		sql.append("    ,EMG_FLAG ");					// 緊急配信フラグ
		// 2017.04.03 M.Akagi #4509 (E)
		// 2016/10/25 Li.Sheng #2258 対応（S) 
		sql.append("    ,OROSI_BAITANKA_VL_KOURI ");					// 卸売価単価(小売税)
		// 2016/10/25 Li.Sheng #2258対応（E) 
		// #6338 MST01003 Add 2021/09/10 SIEU.D (S)
		sql.append("    ,WARIBIKI_KB ");					// 割引区分
		sql.append("    ,PB_SYOHIN_KB ");					// PB区分
		sql.append("    ,IYAKUHIN_KB ");					// 医薬品分類
		// #6338 MST01003 Add 2021/09/10 SIEU.D (E)
		// #6355 MST01003 Add 2021/09/27 SIEU.D (S)
		sql.append("    ,SYOMI_KIGEN_NISU ");			// 賞味期限
		sql.append("    ,SHUKKA_KIGEN_NISU ");			// 出荷可能限度期日
		sql.append("    ,NYUKA_KIGEN_NISU ");			// 入荷可能限度期日
		// #6355 MST01003 Add 2021/09/27 SIEU.D (E)
		sql.append(") ");
		sql.append("SELECT  ");
		sql.append("    BUNRUI1_CD, ");						// 分類１コード
		sql.append("    SYOHIN_CD, ");						// 商品コード
		sql.append("    ?, ");								// 有効日
		sql.append("    SYSTEM_KB, ");						// システム区分
		sql.append("    GYOSYU_KB, ");						// 業種区分
		sql.append("    SYOHIN_KB, ");						// 商品区分
		sql.append("    KETASU_KB, ");						// 桁数区分
		sql.append("    BUNRUI2_CD, ");						// 分類２コード
		sql.append("    BUNRUI5_CD, ");						// 分類５コード
		sql.append("    HINMOKU_CD, ");						// 品目
		sql.append("    SYOHIN_2_CD, ");					// 商品コード２
		sql.append("    ZAIKO_SYOHIN_CD, ");				// 在庫用商品集計コード
		sql.append("    JYOHO_SYOHIN_CD, ");				// 情報系用商品集計コード
		sql.append("    MAKER_CD, ");						// ＪＡＮメーカーコード
		sql.append("    HINMEI_KANJI_NA, ");				// 漢字品名
		sql.append("    KIKAKU_KANJI_NA, ");				// 漢字規格
		sql.append("    KIKAKU_KANJI_2_NA, ");				// 漢字規格２
		// 2016/10/20 T.Arimoto #2254対応（S) POSレシート品名(漢字)<- 漢字品名
		//sql.append("    REC_HINMEI_KANJI_NA, ");			// POSレシート品名(漢字)
		sql.append("    HINMEI_KANJI_NA, ");			// POSレシート品名(漢字)<- 漢字品名
		// 2016/10/20 T.Arimoto #2254対応（E)
		sql.append("    HINMEI_KANA_NA, ");					// カナ品名
		sql.append("    KIKAKU_KANA_NA, ");					// カナ規格
		sql.append("    KIKAKU_KANA_2_NA, ");				// カナ規格２
		sql.append("    REC_HINMEI_KANA_NA, ");				// POSレシート品名(カナ)
		sql.append("    SYOHIN_WIDTH_QT, ");				// 商品サイズ(幅)
		sql.append("    SYOHIN_HEIGHT_QT, ");				// 商品サイズ(高さ)
		sql.append("    SYOHIN_DEPTH_QT, ");				// 商品サイズ(奥行き)
		sql.append("    E_SHOP_KB, ");						// Eショップ区分
		sql.append("    PB_KB, ");							// PB区分
		sql.append("    SUBCLASS_CD, ");					// サブクラスコード
		sql.append("    HAIFU_CD, ");						// 配布コード
		sql.append("    ZEI_KB, ");							// 税区分
		sql.append("    TAX_RATE_KB, ");					// 税率区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		sql.append("    BAIKA_HAISHIN_FG, ");				// 売価配信区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)
		sql.append("    GENTANKA_VL, ");					// 原価単価
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    GENTANKA_NUKI_VL, ");				// 原価単価(税抜)
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    BAITANKA_VL, ");					// 売価単価
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    BAITANKA_NUKI_VL, ");				// 売価単価(税抜)
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    TOSYO_BAIKA_VL, ");					// 当初売価
		sql.append("    PRE_GENTANKA_VL, ");				// 前回原価単価
		sql.append("    PRE_BAITANKA_VL, ");				// 前回売価単価
		sql.append("    TOKUBETU_GENKA_VL, ");				// 特別原価
		sql.append("    MAKER_KIBO_KAKAKU_VL, ");			// メーカー希望小売り価格
		sql.append("    SIIRESAKI_CD, ");					// 仕入先コード
		sql.append("    DAIHYO_HAISO_CD, ");				// 代表配送先コード
		sql.append("    TEN_SIIRESAKI_KANRI_CD, ");			// 店別仕入先管理コード
		sql.append("    SIIRE_HINBAN_CD, ");				// 仕入先品番
		sql.append("    HACYU_SYOHIN_KB, ");				// 発注商品コード区分
		sql.append("    HACYU_SYOHIN_CD, ");				// 発注商品コード
		sql.append("    EOS_KB, ");							// EOS区分
		sql.append("    HACHU_TANI_NA, ");					// 発注単位呼称
		sql.append("    HANBAI_TANI, ");					// 販売単位呼称
		sql.append("    HACHUTANI_IRISU_QT, ");				// 発注単位(入数)
		sql.append("    MAX_HACHUTANI_QT, ");				// 最大発注単位数
		sql.append("    CASE_HACHU_KB, ");					// ケース発注区分
		sql.append("    BARA_IRISU_QT, ");					// バラ入数
		sql.append("    TEN_HACHU_ST_DT, ");				// 店舗発注開始日
		sql.append("    TEN_HACHU_ED_DT, ");				// 店舗発注終了日
		sql.append("    HANBAI_ST_DT, ");					// 販売開始日
		sql.append("    HANBAI_ED_DT, ");					// 販売終了日
		sql.append("    HANBAI_KIKAN_KB, ");				// 販売期間区分
		sql.append("    TEIKAN_KB, ");						// 定貫区分
		sql.append("    SOBA_SYOHIN_KB, ");					// 相場商品区分
		sql.append("    NOHIN_KIGEN_KB, ");					// 納品期限区分
		sql.append("    NOHIN_KIGEN_QT, ");					// 納品期限
		sql.append("    BIN_1_KB, ");						// ①便区分
		sql.append("    HACHU_PATTERN_1_KB, ");				// ①発注パターン区分
		sql.append("    SIME_TIME_1_QT, ");					// ①締め時間
		sql.append("    C_NOHIN_RTIME_1_KB, ");				// ①センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_1_KB, ");			// ①店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_1_KB, ");			// ①店納品時間帯
		sql.append("    BIN_2_KB, ");						// ②便区分
		sql.append("    HACHU_PATTERN_2_KB, ");				// ②発注パターン区分
		sql.append("    SIME_TIME_2_QT, ");					// ②締め時間
		sql.append("    C_NOHIN_RTIME_2_KB, ");				// ②センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_2_KB, ");			// ②店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_2_KB, ");			// ②店納品時間帯
		sql.append("    BIN_3_KB, ");						// ③便区分
		sql.append("    HACHU_PATTERN_3_KB, ");				// ③発注パターン区分
		sql.append("    SIME_TIME_3_QT, ");					// ③締め時間
		sql.append("    C_NOHIN_RTIME_3_KB, ");				// ③センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_3_KB, ");			// ③店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_3_KB, ");			// ③店納品時間帯
		sql.append("    C_NOHIN_RTIME_KB, ");				// センタ納品リードタイム
		sql.append("    YUSEN_BIN_KB, ");					// 優先便区分
		sql.append("    F_BIN_KB, ");						// F便区分
		sql.append("    BUTURYU_KB, ");						// 物流区分
		sql.append("    GOT_MUJYOKEN_FG, ");				// GOT無条件表示対象
		sql.append("    GOT_START_MM, ");					// 季節開始月日
		sql.append("    GOT_END_MM, ");						// 季節終了月日
		sql.append("    HACHU_TEISI_KB, ");					// 発注停止区分
		sql.append("    CENTER_ZAIKO_KB, ");				// センター在庫区分
		sql.append("    NOHIN_SYOHIN_CD, ");				// 納品商品コード
		sql.append("    NYUKA_SYOHIN_CD, ");				// 入荷時商品コード
		sql.append("    NYUKA_SYOHIN_2_CD, ");				// 入荷時商品コード２
		sql.append("    ITF_CD, ");							// ITFコード
		sql.append("    GTIN_CD, ");						// GTINコード
		sql.append("    VENDER_MAKER_CD, ");				// ベンダーメーカーコード
		sql.append("    ZAIKO_CENTER_CD, ");				// 在庫センターコード
		sql.append("    ZAIKO_HACHU_SAKI, ");				// 在庫補充発注先
		sql.append("    CENTER_WEIGHT_QT, ");				// センター重量
		sql.append("    PACK_WIDTH_QT, ");					// 外箱サイズ幅
		sql.append("    PACK_HEIGTH_QT, ");					// 外箱サイズ高さ
		sql.append("    PACK_DEPTH_QT, ");					// 外箱サイズ奥行き
		sql.append("    PACK_WEIGHT_QT, ");					// 外箱重量
		sql.append("    CENTER_HACHUTANI_KB, ");			// センター発注単位区分
		sql.append("    CENTER_HACHUTANI_QT, ");			// センター発注単位数
		sql.append("    CENTER_BARA_IRISU_QT, ");			// センターバラ入数
		sql.append("    CENTER_IRISU_QT, ");				// 最低入数
		sql.append("    CASE_IRISU_QT, ");					// ケース入り数
		sql.append("    CENTER_IRISU_2_QT, ");				// 梱り合せ数
		sql.append("    MIN_ZAIKOSU_QT, ");					// 最小在庫数
		sql.append("    MAX_ZAIKOSU_QT, ");					// 最大在庫数
		sql.append("    KIJUN_ZAIKOSU_QT, ");				// 基準在庫日数
		sql.append("    MIN_ZAIKONISSU_QT, ");				// 最小在庫日数
		sql.append("    MAX_ZAIKONISSU_QT, ");				// 最大在庫日数
		sql.append("    CENTER_KYOYO_KB, ");				// センター許容区分
		sql.append("    CENTER_KYOYO_DT, ");				// センター許容日
		sql.append("    CENTER_SYOMI_KIKAN_KB, ");			// センター賞味期間区分
		sql.append("    CENTER_SYOMI_KIKAN_DT, ");			// センター賞味期間
		sql.append("    TEN_GROUPNO_CD, ");					// 店グルーピングNO(物流）
		sql.append("    TC_JYOUHO_NA, ");					// TC情報
		sql.append("    NOHIN_ONDO_KB, ");					// 納品温度帯
		sql.append("    YOKOMOTI_KB, ");					// 横もち区分
		sql.append("    SHINAZOROE_KB, ");					// 品揃区分
		sql.append("    HONBU_ZAI_KB, ");					// 本部在庫区分
		sql.append("    TEN_ZAIKO_KB, ");					// 店在庫区分
		sql.append("    HANBAI_SEISAKU_KB, ");				// 販売政策区分
		sql.append("    HENPIN_NB, ");						// 返品契約書NO
		sql.append("    HENPIN_GENKA_VL, ");				// 返品原価
		sql.append("    CGC_HENPIN_KB, ");					// CGC返品区分
		sql.append("    HANBAI_LIMIT_KB, ");				// 販売期限区分
		sql.append("    HANBAI_LIMIT_QT, ");				// 販売期限
		sql.append("    ?, ");								// PLU送信日
		sql.append("    KEYPLU_FG, ");						// キーPLU対象
		sql.append("    PLU_KB, ");							// PLU区分
		sql.append("    SYUZEI_HOKOKU_KB, ");				// 酒税報告分類
		sql.append("    SAKE_NAIYORYO_QT, ");				// 酒内容量
		sql.append("    TAG_HYOJI_BAIKA_VL, ");				// タグ表示売価
		sql.append("    KESHI_BAIKA_VL, ");					// 消札売価
		sql.append("    YORIDORI_KB, ");					// よりどり種類
		sql.append("    YORIDORI_VL, ");					// よりどり価格
		sql.append("    YORIDORI_QT, ");					// よりどり数量
		sql.append("    BLAND_CD, ");						// ブランドコード
		sql.append("    SEASON_CD, ");						// シーズンコード
		sql.append("    HUKUSYU_CD, ");						// 服種コード
		sql.append("    STYLE_CD, ");						// スタイルコード
		sql.append("    SCENE_CD, ");						// シーンコード
		sql.append("    SEX_CD, ");							// 性別コード
		sql.append("    AGE_CD, ");							// 年代コード
		sql.append("    GENERATION_CD, ");					// 世代コード
		sql.append("    SOZAI_CD, ");						// 素材コード
		sql.append("    PATTERN_CD, ");						// パターンコード
		sql.append("    ORIAMI_CD, ");						// 織り編みコード
		sql.append("    HUKA_KINO_CD, ");					// 付加機能コード
		sql.append("    SODE_CD, ");						// 袖丈コード
		sql.append("    SIZE_CD, ");						// サイズコード
		sql.append("    COLOR_CD, ");						// カラーコード
		sql.append("    KEIYAKU_SU_QT, ");					// 契約数
		sql.append("    KEIYAKU_PATTERN_KB, ");				// 契約パターン
		sql.append("    KEIYAKU_ST_DT, ");					// 契約開始期間
		sql.append("    KEIYAKU_ED_DT, ");					// 契約終了期間
		sql.append("    KUMISU_KB, ");						// 組数区分
		sql.append("    NEFUDA_KB, ");						// 値札区分
		sql.append("    NEFUDA_UKEWATASI_DT, ");			// 値札受渡日
		sql.append("    NEFUDA_UKEWATASI_KB, ");			// 値札受渡方法
		sql.append("    PC_KB, ");							// PC発行区分
		sql.append("    DAISI_NO_NB, ");					// 台紙NO
		sql.append("    UNIT_PRICE_TANI_KB, ");				// ユニットプライス-単位区分
		sql.append("    UNIT_PRICE_NAIYORYO_QT, ");			// ユニットプライス-内容量
		sql.append("    UNIT_PRICE_KIJUN_TANI_QT, ");		// ユニットプライス-基準単位量
		sql.append("    SYOHI_KIGEN_KB, ");					// 消費期限区分
		sql.append("    SYOHI_KIGEN_DT, ");					// 消費期限
		sql.append("    DAICHO_TENPO_KB, ");				// 商品台帳(店舗)
		sql.append("    DAICHO_HONBU_KB, ");				// 商品台帳(本部)
		sql.append("    DAICHO_SIIRESAKI_KB, ");			// 商品台帳(仕入先)
		sql.append("    TANA_NO_NB, ");						// 棚NO
		sql.append("    DAN_NO_NB, ");						// 段NO
		sql.append("    REBATE_FG, ");						// リベート対象フラグ
		sql.append("    MARK_GROUP_CD, ");					// マークグループ
		sql.append("    MARK_CD, ");						// マークコード
		sql.append("    YUNYUHIN_KB, ");					// 輸入品区分
		sql.append("    SANTI_CD, ");						// 原産国/産地コード
		sql.append("    D_ZOKUSEI_1_NA, ");					// 大属性１
		sql.append("    S_ZOKUSEI_1_NA, ");					// 小属性１
		sql.append("    D_ZOKUSEI_2_NA, ");					// 大属性２
		sql.append("    S_ZOKUSEI_2_NA, ");					// 小属性２
		sql.append("    D_ZOKUSEI_3_NA, ");					// 大属性３
		sql.append("    S_ZOKUSEI_3_NA, ");					// 小属性３
		sql.append("    D_ZOKUSEI_4_NA, ");					// 大属性４
		sql.append("    S_ZOKUSEI_4_NA, ");					// 小属性４
		sql.append("    D_ZOKUSEI_5_NA, ");					// 大属性５
		sql.append("    S_ZOKUSEI_5_NA, ");					// 小属性５
		sql.append("    D_ZOKUSEI_6_NA, ");					// 大属性６
		sql.append("    S_ZOKUSEI_6_NA, ");					// 小属性６
		sql.append("    D_ZOKUSEI_7_NA, ");					// 大属性７
		sql.append("    S_ZOKUSEI_7_NA, ");					// 小属性７
		sql.append("    D_ZOKUSEI_8_NA, ");					// 大属性８
		sql.append("    S_ZOKUSEI_8_NA, ");					// 小属性８
		sql.append("    D_ZOKUSEI_9_NA, ");					// 大属性９
		sql.append("    S_ZOKUSEI_9_NA, ");					// 小属性９
		sql.append("    D_ZOKUSEI_10_NA, ");				// 大属性１０
		sql.append("    S_ZOKUSEI_10_NA, ");				// 小属性１０
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    FREE_1_KB, ");						// 任意区分１
		sql.append("    FREE_2_KB, ");						// 任意区分２
		sql.append("    FREE_3_KB, ");						// 任意区分３
		sql.append("    FREE_4_KB, ");						// 任意区分４
		sql.append("    FREE_5_KB, ");						// 任意区分５
		sql.append("    COMMENT_1_TX, ");					// コメント１
		sql.append("    COMMENT_2_TX, ");					// コメント２
		sql.append("    FREE_CD, ");						// 任意コード
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    FUJI_SYOHIN_KB, ");					// F商品区分
		sql.append("    COMMENT_TX, ");						// コメント
		sql.append("    AUTO_DEL_KB, ");					// 自動削除対象区分
		sql.append("    MST_SIYOFUKA_KB, ");				// マスタ使用不可区分
		sql.append("    HAIBAN_FG, ");						// 廃番実施フラグ
		sql.append("    SINKI_TOROKU_DT, ");				// 新規登録日
		sql.append("    '" + MasterDT + "', ");				// 変更日
		// No.158 MSTB011 Add 2015.12.15 TU.TD (S)
		sql.append("    NENREI_SEIGEN_KB, ");				//年齢制限区分
		sql.append("    NENREI, ");							//年齢
		sql.append("    KAN_KB, ");							//瓶区分
		sql.append("    HOSHOUKIN, ");						//保証金
		// No.158 MSTB011 Add 2015.12.15 TU.TD (E)
		sql.append("    SYOKAI_TOROKU_TS, ");				// 初回登録日
		sql.append("    SYOKAI_USER_ID, ");					// 初回登録社員ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    ?, ");																				// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    ?,");																				// 更新者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// バッチ更新年月日
		sql.append("    '" + strBatchId + "', ");															// バッチ更新者ID
		// 2016/10/20 T.Arimoto #2254対応（S) 
		//sql.append("    ? ");																				// 削除フラグ
		sql.append("    ?, ");																				// 削除フラグ
		sql.append("    CUR_GENERATION_YUKO_DT, ");			// 原価単価有効日(現世代)
		sql.append("    CUR_GENERATION_GENTANKA_VL, ");		// 原価単価(現世代)
		sql.append("    ONE_GENERATION_YUKO_DT, ");			// 原価単価有効日(1世代前)
		sql.append("    ONE_GENERATION_GENTANKA_VL, ");		// 原価単価(1世代前)
		sql.append("    TWO_GENERATION_YUKO_DT, ");			// 原価単価有効日(2世代前)
		sql.append("    TWO_GENERATION_GENTANKA_VL, ");		// 原価単価(2世代前)
		sql.append("    SIIRE_ZEI_KB, ");					// 仕入税区分
		sql.append("    SIIRE_TAX_RATE_KB, ");				// 仕入税率区分
		sql.append("    OROSI_ZEI_KB, ");					// 卸税区分
		sql.append("    OROSI_TAX_RATE_KB, ");				// 卸税率区分
		sql.append("    OROSI_BAITANKA_VL, ");				// 卸売単価
		sql.append("    OROSI_BAITANKA_NUKI_VL, ");			// 卸売価単価（税抜）
		sql.append("    MIN_HACHU_QT, ");					// 最低発注数
		sql.append("    HACHU_FUKA_FLG, ");					// 発注不可フラグ
		sql.append("    PER_TXT, ");						// 規格内容
		sql.append("    SYOHI_KIGEN_HYOJI_PATTER, ");		// 消費期限表示パターン
		// 2017.04.03 M.Akagi #4509 (S)
		//sql.append("    PLU_HANEI_TIME ");					// PLU反映時間
		sql.append("    NULL, ");					// PLU反映時間
		sql.append("    '"+ mst910020_EmgFlagDictionary.OFF.getCode() + "' ");					// 緊急配信フラグ
		// 2017.04.03 M.Akagi #4509 (E)
		// 2016/10/25 Li.Sheng #2258 対応（S) 
		sql.append("    ,OROSI_BAITANKA_VL_KOURI ");					// 卸売価単価(小売税)
		// 2016/10/25 Li.Sheng #2258対応（E) 
		// 2016/10/20 T.Arimoto #2254対応（E) 
		// #6338 Add 2021/09/10 SIEU.D (S)
		sql.append("    ,WARIBIKI_KB ");					// 割引区分
		sql.append("    ,PB_SYOHIN_KB ");					// PB区分
		sql.append("    ,IYAKUHIN_KB ");					// 医薬品分類
		// #6338 Add 2021/09/10 SIEU.D (E)
		// #6355 Add 2021/09/27 SIEU.D (S)
		sql.append("    ,SYOMI_KIGEN_NISU ");			// 賞味期限
		sql.append("    ,SHUKKA_KIGEN_NISU ");			// 出荷可能限度期日
		sql.append("    ,NYUKA_KIGEN_NISU ");			// 入荷可能限度期日
		// #6355 Add 2021/09/27 SIEU.D (E)
		sql.append("  FROM R_SYOHIN ");
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");
		sql.append("   AND DELETE_FG  = '"+ mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());

		return pstmt;
	}

	//Add 2015.07.13 DAI.BQ (S)
	public PreparedStatement getPreparedASNSyohinInsSQL(MasterDataBase dataBase)  throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_SYOHIN_ASN ");
		sql.append("( ");
		sql.append("    BUNRUI1_CD, ");						// 分類１コード
		sql.append("    SYOHIN_CD, ");						// 商品コード
		sql.append("    YUKO_DT, ");						// 有効日
		sql.append("    SYOHIN_EIJI_NA, ");					// 商品名（英字）
		sql.append("    COUNTRY_CD, ");						// 国コード
		//ADD 2015/08/17 THO.VT (S)
		sql.append("    MAKER_CD, ");
		//ADD 2015/08/17 THO.VT (E)
		//Add 2016.01.11 Huy.NT (S)
		sql.append("	HANBAI_HOHO_KB, ");					// 販売方法
		//Add 2016.01.11 Huy.NT (E)
		sql.append("    MIN_ZAIKO_QT, ");					// 最低在庫数量
		sql.append("    SECURITY_TAG_FG, ");				// セキュリティタグフラグ
		sql.append("    MEMBER_DISCOUNT_FG, ");				// メンバーディ割引対象外商品フラグ
		sql.append("    HAMPER_SYOHIN_FG, ");				// ハンパー商品フラグ
		sql.append("    INSERT_TS, ");						// 作成年月日
		sql.append("    INSERT_USER_ID, ");					// 作成者ID
		sql.append("    UPDATE_TS, ");						// 更新年月日
		sql.append("    UPDATE_USER_ID, ");					// 更新者ID
		sql.append("    DELETE_FG ");						// 削除フラグ
		// 2016/10/20 T.Arimoto #2254対応（S) 
		sql.append("    , LABEL_SEIBUN");					// ラベル成分
		sql.append("    , LABEL_HOKAN_HOHO");				// ラベル保管方法
		sql.append("    , LABEL_TUKAIKATA");				// ラベル使い方
		// 2016/10/20 T.Arimoto #2254対応（E) 

		sql.append(") ");
		sql.append("SELECT  ");
		sql.append("    BUNRUI1_CD, ");																		// 分類１コード
		sql.append("    SYOHIN_CD, ");																		// 商品コード
		sql.append("    ?, ");																		// 有効日
		sql.append("    SYOHIN_EIJI_NA, ");																	// 商品名（英字）
		sql.append("    COUNTRY_CD, ");																		// 国コード
		//ADD 2015/08/17 THO.VT (S)
		sql.append("    MAKER_CD, ");
		//ADD 2015/08/17 THO.VT (E)
		//Add 2016.01.11 Huy.NT (S)
		sql.append("	HANBAI_HOHO_KB, ");
		//Add 2016.01.11 Huy.NT (E)
		sql.append("    MIN_ZAIKO_QT, ");																	// 最低在庫数量
		sql.append("    SECURITY_TAG_FG, ");																// セキュリティタグフラグ
		sql.append("    MEMBER_DISCOUNT_FG, ");																// メンバーディ割引対象外商品フラグ
		sql.append("    HAMPER_SYOHIN_FG, ");																// ハンパー商品フラグ
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    ?, ");																				// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    ?,");																				// 更新者ID
		sql.append("    ? ");																				// 削除フラグ
		// 2016/10/20 T.Arimoto #2254対応（S) 
		sql.append("    , LABEL_SEIBUN");																	// ラベル成分
		sql.append("    , LABEL_HOKAN_HOHO");																// ラベル保管方法
		sql.append("    , LABEL_TUKAIKATA");																// ラベル使い方
		// 2016/10/20 T.Arimoto #2254対応（E) 
		sql.append("  FROM R_SYOHIN_ASN ");
		sql.append("  WHERE BUNRUI1_CD = ? ");
		sql.append("    AND SYOHIN_CD  = ? ");
		// 2016/10/20 T.Arimoto #2258対応（S) 複数取得してしまう不具合を修正
		//sql.append("    AND YUKO_DT   <= ? ");
		sql.append("    AND YUKO_DT    = ? ");
		// 2016/10/20 T.Arimoto #2258対応（E) 
		sql.append("    AND DELETE_FG  = '"+ mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}
	//Add 2015.07.13 DAI.BQ (E)

	public void setPreparedSyohinInsSQL(PreparedStatement pstmt, ResultSet rs, String strPluSendDt, String strDelFg) throws SQLException {

		int idx = 0;
		String yukoDt = rs.getString("yuko_dt");

		// 有効日
		idx++;
		pstmt.setString(idx, yukoDt);

		// PLU送信日
		idx++;
		if (strDelFg.equals(mst000801_DelFlagDictionary.INAI.getCode())) {

			if (strPluSendDt != null && DateDiff.getDiffDays(yukoDt, strPluSendDt) >= 0) {
				pstmt.setString(idx, strPluSendDt);
			} else {
				pstmt.setString(idx, yukoDt);
			}
		}

		// 登録者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		// 更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, rs.getString("bunrui1_cd"));

		// 商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, rs.getString("s_yuko_dt"));
	}


	public void setPreparedSyohinInsSQL(PreparedStatement pstmt, String strSetYukoDt, String strUserId, String strDelFg, String strBunrui1Cd, String strSyohinCd, String strYukoDt) throws SQLException {

		int idx = 0;

		// 有効日
		idx++;
		pstmt.setString(idx, strSetYukoDt);

		// PLU反映日
		idx++;
		pstmt.setString(idx, strSetYukoDt);

		// 登録者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 更新者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, strBunrui1Cd);

		// 商品コード
		idx++;
		pstmt.setString(idx, strSyohinCd);

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, strYukoDt);

	}

	//Add 2015.07.13 DAI.BQ (S)
	public void setPreparedASNSyohinInsSQL(PreparedStatement pstmt, String strSetYukoDt, String strUserId, String strDelFg, String strBunrui1Cd, String strSyohinCd, String strYukoDt) throws SQLException {

		int idx = 0;

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, strSetYukoDt);

		// 登録者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 更新者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, strBunrui1Cd);

		// 商品コード
		idx++;
		pstmt.setString(idx, strSyohinCd);

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, strYukoDt);

	}
	//Add 2015.07.13 DAI.BQ (E)


	public PreparedStatement getPreparedGiftSyohinInsSQL(MasterDataBase dataBase, String strBatchId)  throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_GIFT_SYOHIN ");
		sql.append("( ");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    YUKO_DT, ");
		sql.append("    TSUBAN, ");
		sql.append("    GIFT_CD, ");
		sql.append("    WARIBIKI_QT, ");
		sql.append("    POINT_QT, ");
		sql.append("    SYOHIN_COMMENT_TX, ");
		sql.append("    MEISAI_TX, ");
		sql.append("    KAZEI_KB, ");
		sql.append("    KEIYU_KB, ");
		sql.append("    DENPYO_KB, ");
		sql.append("    SORYO_KB, ");
		sql.append("    GIFT_HASSOMOTO_CD, ");
		sql.append("    HAISO_KB, ");
		sql.append("    JUSHIN_START_DT, ");
		sql.append("    JUSHIN_END_DT, ");
		sql.append("    HAISO_START_DT, ");
		sql.append("    HAISO_END_DT, ");
		sql.append("    INSERT_TS, ");
		sql.append("    INSERT_USER_ID, ");
		sql.append("    UPDATE_TS, ");
		sql.append("    UPDATE_USER_ID, ");
		sql.append("    BATCH_UPDATE_TS, ");
		sql.append("    BATCH_UPDATE_ID, ");
		sql.append("    DELETE_FG ");
		sql.append(") ");
		sql.append("SELECT  ");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    ?, ");
		sql.append("    TSUBAN, ");
		sql.append("    GIFT_CD, ");
		sql.append("    WARIBIKI_QT, ");
		sql.append("    POINT_QT, ");
		sql.append("    SYOHIN_COMMENT_TX, ");
		sql.append("    MEISAI_TX, ");
		sql.append("    KAZEI_KB, ");
		sql.append("    KEIYU_KB, ");
		sql.append("    DENPYO_KB, ");
		sql.append("    SORYO_KB, ");
		sql.append("    GIFT_HASSOMOTO_CD, ");
		sql.append("    HAISO_KB, ");
		sql.append("    JUSHIN_START_DT, ");
		sql.append("    JUSHIN_END_DT, ");
		sql.append("    HAISO_START_DT, ");
		sql.append("    HAISO_END_DT, ");
		sql.append("       '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
	    sql.append("       ?, ");
	    sql.append("       '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
	    sql.append("       ?,");
	    sql.append("       '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       '" + strBatchId + "', ");
		sql.append("       ? ");
		sql.append("  FROM R_GIFT_SYOHIN ");
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");
		sql.append("   AND DELETE_FG  = '"+ mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());

		return pstmt;
	}


	public void setPreparedGiftSyohinInsSQL(PreparedStatement pstmt, ResultSet rs, String strDelFg) throws SQLException {

		int idx = 0;

		// 有効日
		idx++;
		pstmt.setString(idx, rs.getString("yuko_dt"));

		// 登録者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		// 更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, rs.getString("bunrui1_cd"));

		// 商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, rs.getString("s_yuko_dt"));

	}

	public void setPreparedGiftSyohinInsSQL(PreparedStatement pstmt, String strSetYukoDt, String strUserId, String strDelFg, String strBunrui1Cd, String strSyohinCd, String strYukoDt) throws SQLException {

		int idx = 0;

		// 有効日
		idx++;
		pstmt.setString(idx, strSetYukoDt);

		// 登録者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 更新者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, strBunrui1Cd);

		// 商品コード
		idx++;
		pstmt.setString(idx, strSyohinCd);

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, strYukoDt);

	}


	public PreparedStatement getPreparedKeiryokiInsSQL(MasterDataBase dataBase, String strBatchId)  throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_KEIRYOKI ");
		sql.append("( ");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    YUKO_DT, ");
		sql.append("    SYOHIN_YOBIDASI, ");
		sql.append("    S_GYOSYU_CD, ");
		sql.append("    THEME_CD, ");
		sql.append("    KEIRYO_HANKU_CD, ");
		sql.append("    HANEI_DT, ");
		sql.append("    SYOHIN_KBN, ");
		sql.append("    KEIRYOKI_NA, ");
		sql.append("    KEIRYOKI2_NA, ");
		sql.append("    KEIRYOKI3_NA, ");
		sql.append("    RECEIPT_HINMEI_NA, ");
		sql.append("    TEIGAKU_UP_KB, ");
		sql.append("    TANKA_VL, ");
		sql.append("    TEIGAKU_VL, ");
		sql.append("    TEIGAKUJI_TANI_KB, ");
		sql.append("    SYOMIKIKAN_KB, ");
		sql.append("    SYOMIKIKAN_VL, ");
		sql.append("    SANTI_KB, ");
		sql.append("    CHORIYO_KOKOKUBUN_KB, ");
		sql.append("    HOZON_ONDOTAI_KB, ");
		sql.append("    START_KB, ");
		sql.append("    BACK_LABEL_KB, ");
		sql.append("    EIYO_SEIBUN_NA, ");
		sql.append("    COMMENT_KB, ");
		sql.append("    BIKO_TX, ");
		sql.append("    GENZAIRYO_NA, ");
		sql.append("    TENKABUTU_NA, ");
		sql.append("    MIN_WEIGHT_QT, ");
		sql.append("    MAX_WEIGHT_QT, ");
		sql.append("    TEIKAN_WEIGHT_QT, ");
		sql.append("    FUTAI_WEIGHT_QT, ");
		sql.append("    EYE_CATCH_NO, ");
		sql.append("    EYE_CATCH_MODE, ");
		sql.append("    TEIGAKU_KB, ");
		sql.append("    TEIGAKU_BAIKA_VL, ");
		sql.append("    HOZON_ONDO_QT, ");
		sql.append("    CALORIE, ");
		sql.append("    TRAY_NA, ");
		sql.append("    NETSUKEKI_NA, ");
		sql.append("    NETSUKEKI_NA_2, ");
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    KAKOBI_PRINT_KB, ");
		sql.append("    KAKOJIKOKU_PRINT_KB, ");
		sql.append("    SENTAKU_COMMENT_CD, ");
		sql.append("    TRACEABILITY_FG, ");
		sql.append("    TEIKAN_TANI_KB, ");
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)
		sql.append("    INSERT_TS, ");
		sql.append("    INSERT_USER_ID, ");
		sql.append("    UPDATE_TS, ");
		sql.append("    UPDATE_USER_ID, ");
		sql.append("    BATCH_UPDATE_TS, ");
		sql.append("    BATCH_UPDATE_ID, ");
		sql.append("    DELETE_FG ");
		sql.append(") ");
		sql.append("SELECT ");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    ?, ");
		sql.append("    SYOHIN_YOBIDASI, ");
		sql.append("    S_GYOSYU_CD, ");
		sql.append("    THEME_CD, ");
		sql.append("    KEIRYO_HANKU_CD, ");
		sql.append("    HANEI_DT, ");
		sql.append("    SYOHIN_KBN, ");
		sql.append("    KEIRYOKI_NA, ");
		sql.append("    KEIRYOKI2_NA, ");
		sql.append("    KEIRYOKI3_NA, ");
		sql.append("    RECEIPT_HINMEI_NA, ");
		sql.append("    TEIGAKU_UP_KB, ");
		sql.append("    TANKA_VL, ");
		sql.append("    TEIGAKU_VL, ");
		sql.append("    TEIGAKUJI_TANI_KB, ");
		sql.append("    SYOMIKIKAN_KB, ");
		sql.append("    SYOMIKIKAN_VL, ");
		sql.append("    SANTI_KB, ");
		sql.append("    CHORIYO_KOKOKUBUN_KB, ");
		sql.append("    HOZON_ONDOTAI_KB, ");
		sql.append("    START_KB, ");
		sql.append("    BACK_LABEL_KB, ");
		sql.append("    EIYO_SEIBUN_NA, ");
		sql.append("    COMMENT_KB, ");
		sql.append("    BIKO_TX, ");
		sql.append("    GENZAIRYO_NA, ");
		sql.append("    TENKABUTU_NA, ");
		sql.append("    MIN_WEIGHT_QT, ");
		sql.append("    MAX_WEIGHT_QT, ");
		sql.append("    TEIKAN_WEIGHT_QT, ");
		sql.append("    FUTAI_WEIGHT_QT, ");
		sql.append("    EYE_CATCH_NO, ");
		sql.append("    EYE_CATCH_MODE, ");
		sql.append("    TEIGAKU_KB, ");
		sql.append("    TEIGAKU_BAIKA_VL, ");
		sql.append("    HOZON_ONDO_QT, ");
		sql.append("    CALORIE, ");
		sql.append("    TRAY_NA, ");
		sql.append("    NETSUKEKI_NA, ");
		sql.append("    NETSUKEKI_NA_2, ");
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    KAKOBI_PRINT_KB, ");
		sql.append("    KAKOJIKOKU_PRINT_KB, ");
		sql.append("    SENTAKU_COMMENT_CD, ");
		sql.append("    TRACEABILITY_FG, ");
		sql.append("    TEIKAN_TANI_KB, ");
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)
	    sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
	    sql.append("    ?,");
	    sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
	    sql.append("    ?,");
	    sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("    '" + strBatchId + "', ");
		sql.append("    ? ");
		sql.append("  FROM R_KEIRYOKI ");
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");
		sql.append("   AND DELETE_FG  = '"+ mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());

		return pstmt;
	}


	public void setPreparedKeiryokiInsSQL(PreparedStatement pstmt, ResultSet rs, String strDelFg) throws SQLException {

		int idx = 0;

		// 有効日
		idx++;
		pstmt.setString(idx, rs.getString("yuko_dt"));

		// 登録者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		// 更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, rs.getString("bunrui1_cd"));

		// 商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, rs.getString("rs_yuko_dt"));

	}

	public void setPreparedKeiryokiInsSQL(PreparedStatement pstmt, String strSetYukoDt, String strUserId, String strDelFg, String strBunrui1Cd, String strSyohinCd, String strYukoDt) throws SQLException {

		int idx = 0;

		// 有効日
		idx++;
		pstmt.setString(idx, strSetYukoDt);

		// 登録者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 更新者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, strBunrui1Cd);

		// 商品コード
		idx++;
		pstmt.setString(idx, strSyohinCd);

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, strYukoDt);

	}


	public PreparedStatement getPreparedSyohinHachuNohinkijunbiInsSQL(MasterDataBase dataBase, String strBatchId)  throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_SYOHIN_HACHUNOHINKIJUNBI ");
		sql.append("( ");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    TENPO_CD, ");
		sql.append("    YUKO_DT, ");
		sql.append("    HACHU_HOHO_KB, ");
		sql.append("    HACHU_MON_FG, ");
		sql.append("    HACHU_TUE_FG, ");
		sql.append("    HACHU_WED_FG, ");
		sql.append("    HACHU_THU_FG, ");
		sql.append("    HACHU_FRI_FG, ");
		sql.append("    HACHU_SAT_FG, ");
		sql.append("    HACHU_SUN_FG, ");
		sql.append("    RTIME_MON_KB, ");
		sql.append("    RTIME_TUE_KB, ");
		sql.append("    RTIME_WED_KB, ");
		sql.append("    RTIME_THU_KB, ");
		sql.append("    RTIME_FRI_KB, ");
		sql.append("    RTIME_SAT_KB, ");
		sql.append("    RTIME_SUN_KB, ");
		sql.append("    INSERT_TS, ");
		sql.append("    INSERT_USER_ID, ");
		sql.append("    UPDATE_TS, ");
		sql.append("    UPDATE_USER_ID, ");
		sql.append("    BATCH_UPDATE_TS, ");
		sql.append("    BATCH_UPDATE_ID, ");
		sql.append("    DELETE_FG ");
		sql.append(") ");
		sql.append("SELECT ");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    TENPO_CD, ");
		sql.append("    ?, ");
		sql.append("    HACHU_HOHO_KB, ");
		sql.append("    HACHU_MON_FG, ");
		sql.append("    HACHU_TUE_FG, ");
		sql.append("    HACHU_WED_FG, ");
		sql.append("    HACHU_THU_FG, ");
		sql.append("    HACHU_FRI_FG, ");
		sql.append("    HACHU_SAT_FG, ");
		sql.append("    HACHU_SUN_FG, ");
		sql.append("    RTIME_MON_KB, ");
		sql.append("    RTIME_TUE_KB, ");
		sql.append("    RTIME_WED_KB, ");
		sql.append("    RTIME_THU_KB, ");
		sql.append("    RTIME_FRI_KB, ");
		sql.append("    RTIME_SAT_KB, ");
		sql.append("    RTIME_SUN_KB, ");
	    sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
	    sql.append("    ?,");
	    sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
	    sql.append("    ?,");
	    sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("    '" + strBatchId + "', ");
		sql.append("    ? ");
		sql.append("  FROM R_SYOHIN_HACHUNOHINKIJUNBI ");
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");
		sql.append("   AND DELETE_FG  = '"+ mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());

		return pstmt;
	}


	public void setPreparedSyohinHachuNohinkijunbiInsSQL(PreparedStatement pstmt, ResultSet rs, String strDelFg) throws SQLException {

		int idx = 0;

		// 有効日
		idx++;
		pstmt.setString(idx, rs.getString("yuko_dt"));

		// 登録者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		// 更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, rs.getString("bunrui1_cd"));

		// 商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, rs.getString("s_yuko_dt"));

	}

	public void setPreparedSyohinHachuNohinkijunbiInsSQL(PreparedStatement pstmt, String strSetYukoDt, String strUserId, String strDelFg, String strBunrui1Cd, String strSyohinCd, String strYukoDt) throws SQLException {

		int idx = 0;

		// 有効日
		idx++;
		pstmt.setString(idx, strSetYukoDt);

		// 登録者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 更新者ID
		idx++;
		pstmt.setString(idx, strUserId);

		// 削除フラグ
		idx++;
		pstmt.setString(idx, strDelFg);

		// 分類１コード
		idx++;
		pstmt.setString(idx, strBunrui1Cd);

		// 商品コード
		idx++;
		pstmt.setString(idx, strSyohinCd);

		// 前世代の有効日
		idx++;
		pstmt.setString(idx, strYukoDt);

	}

	/**
	 * ギフト商品マスタ、計量器マスタの削除用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedSyohinSubDelUpSQL(MasterDataBase dataBase, String strTableName) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE " + strTableName);
		sql.append("   SET UPDATE_TS       = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       UPDATE_USER_ID  = ?,");
		sql.append("       BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       BATCH_UPDATE_ID = '").append(BATCH_ID).append("', ");
		sql.append("       DELETE_FG       = '").append(mst000801_DelFlagDictionary.IRU.getCode()).append("' ");
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");
		sql.append("   AND DELETE_FG   = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	public void setPreparedSyohinSubDelUpSQL(PreparedStatement pstmt, ResultSet rs) throws SQLException {

		int idx = 0;

		//更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("BY_NO"));

		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("SYOHIN_CD"));

		//有効日
		idx++;
		pstmt.setString(idx, rs.getString("YUKO_DT"));
	}


	public void setPreparedSyohinSubDelUpSQL(PreparedStatement pstmt, String strUseId, String strBunrui1Cd, String strSyohinCd, String strYukoDt) throws SQLException {

		int idx = 0;

		// 更新者ID
		idx++;
		pstmt.setString(idx, strUseId);

		// 分類１コード
		idx++;
		pstmt.setString(idx, strBunrui1Cd);

		// 商品コード
		idx++;
		pstmt.setString(idx, strSyohinCd);

		// 有効日
		idx++;
		pstmt.setString(idx, strYukoDt);
	}


	/**
	 * 商品マスタの変更日更新用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedSyohinHenkoDtUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE R_SYOHIN ");
		sql.append("   SET HENKO_DT  = '" + MasterDT + "', ");
		sql.append("       UPDATE_TS       = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       UPDATE_USER_ID  = ?, ");
		sql.append("       BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       BATCH_UPDATE_ID = '").append(BATCH_ID).append("' ");
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	public void setPreparedSyohinHenkoDtUpSQL(PreparedStatement pstmt, ResultSet rs) throws SQLException {

		int idx = 0;

		// 更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("BY_NO"));

		// 分類１コード
		idx++;
		pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

		// 商品コード
		idx++;
		pstmt.setString(idx, rs.getString("SYOHIN_CD"));

		// 有効日
		idx++;
		pstmt.setString(idx, rs.getString("YUKO_DT"));

	}

	/**
	 * ギフト商品マスタ、計量器マスタの商品コード更新用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedTrSyohinCdUpSQL(MasterDataBase dataBase, String strTableName) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE " + strTableName);
		sql.append("   SET TR_SYOHIN_CD = ? ");
		sql.append(" WHERE TORIKOMI_DT         = ? ");
		sql.append("   AND EXCEL_FILE_SYUBETSU = ? ");
		sql.append("   AND UKETSUKE_NO         = ? ");
		sql.append("   AND SYOHIN_GYO_NO       = ? ");
		sql.append("   AND MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("   AND SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("' ");
		sql.append("   AND LENGTH(TRIM(TR_SYOHIN_CD)) <= 3 ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	public void setPreparedTrSyohinCdUpSQL(PreparedStatement pstmt, ResultSet rs, String syohinCd) throws SQLException {

		int idx = 0;

		//商品コード
		idx++;
		pstmt.setString(idx, syohinCd);

		//取込日
		idx++;
		pstmt.setString(idx, rs.getString("TORIKOMI_DT"));

		//EXCELファイル種別
		idx++;
		pstmt.setString(idx, rs.getString("EXCEL_FILE_SYUBETSU"));

		//受付ファイル№
		idx++;
		pstmt.setString(idx, rs.getString("UKETSUKE_NO"));

		//商品マスタ登録行№
		idx++;
		pstmt.setString(idx, rs.getString("SAKUSEI_GYO_NO"));

	}

}


