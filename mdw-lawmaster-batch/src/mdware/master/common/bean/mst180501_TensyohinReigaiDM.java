/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/25)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;
import mdware.master.util.RMSTDATEUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/25)初版作成
 */
public class mst180501_TensyohinReigaiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst180501_TensyohinReigaiDM()
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
		mst180501_TensyohinReigaiBean bean = new mst180501_TensyohinReigaiBean();
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setBumonCd( rest.getString("bumon_cd") );
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		bean.setTenpoNa( rest.getString("tenpo_na") );
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setTenHachuStDt( rest.getString("ten_hachu_st_dt") );
		bean.setTenHachuEdDt( rest.getString("ten_hachu_ed_dt") );
		bean.setGentankaVl( rest.getDouble("gentanka_vl") );
		bean.setBaitankaVl( rest.getDouble("baitanka_vl") );
		bean.setMaxHachutaniQt( rest.getDouble("max_hachutani_qt") );
		bean.setEosKb( rest.getString("eos_kb") );
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		bean.setEosNa( rest.getString("eos_na") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
//		bean.setSiiresakiNa( rest.getString("siiresaki_na") );
//		bean.setAreaCd( rest.getString("area_cd") );
//		bean.setAreaNa( rest.getString("area_na") );
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		bean.setTenbetuHaisoCd( rest.getString("tenbetu_haiso_cd") );
		bean.setBin1Kb( rest.getString("bin_1_kb") );
//		bean.setHachuPattern1Kb( rest.getString("hachu_pattern_1_kb") );
//		bean.setSimeTime1Qt( rest.getDouble("sime_time_1_qt") );
//		bean.setCNohinRtime1Kb( rest.getString("c_nohin_rtime_1_kb") );
//		bean.setTenNohinRtime1Kb( rest.getString("ten_nohin_rtime_1_kb") );
//		bean.setTenNohinTime1Kb( rest.getString("ten_nohin_time_1_kb") );
		bean.setBin2Kb( rest.getString("bin_2_kb") );
		bean.setYusenBinKb( rest.getString("yusen_bin_kb") );
//		bean.setHachuPattern2Kb( rest.getString("hachu_pattern_2_kb") );
//		bean.setSimeTime2Qt( rest.getDouble("sime_time_2_qt") );
//		bean.setCNohinRtime2Kb( rest.getString("c_nohin_rtime_2_kb") );
//		bean.setTenNohinRtime2Kb( rest.getString("ten_nohin_rtime_2_kb") );
//		bean.setTenNohinTime2Kb( rest.getString("ten_nohin_time_2_kb") );
//		bean.setBin3Kb( rest.getString("bin_3_kb") );
//		bean.setHachuPattern3Kb( rest.getString("hachu_pattern_3_kb") );
//		bean.setSimeTime3Qt( rest.getDouble("sime_time_3_qt") );
//		bean.setCNohinRtime3Kb( rest.getString("c_nohin_rtime_3_kb") );
//		bean.setTenNohinRtime3Kb( rest.getString("ten_nohin_rtime_3_kb") );
//		bean.setTenNohinTime3Kb( rest.getString("ten_nohin_time_3_kb") );
//		bean.setCNohinRtimeKb( rest.getString("c_nohin_rtime_kb") );
//		bean.setSyohinKb( rest.getString("syohin_kb") );
//		bean.setButuryuKb( rest.getString("buturyu_kb") );
//		bean.setTenZaikoKb( rest.getString("ten_zaiko_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
//		bean.setDeleteFg( rest.getString("delete_fg") );
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		↓↓仕様追加による変更（2005.06.30）↓↓
//		bean.setSinkiTorokuDt( rest.getString("sinki_toroku_dt") );
//		bean.setHenkoDt( rest.getString("henko_dt") );
//		↑↑仕様追加による変更（2005.06.30）↑↑
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		String wk = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append("hanku_cd ");
		sb.append("bumon_cd ");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("yuko_dt ");
		sb.append(", ");
		sb.append("ten_hachu_st_dt ");
		sb.append(", ");
		sb.append("ten_hachu_ed_dt ");
		sb.append(", ");
		sb.append("gentanka_vl ");
		sb.append(", ");
		sb.append("baitanka_vl ");
		sb.append(", ");
		sb.append("max_hachutani_qt ");
		sb.append(", ");
		sb.append("eos_kb ");
		sb.append(", ");
		sb.append("siiresaki_cd ");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
		sb.append(", ");
//		sb.append("area_cd ");
//		sb.append(", ");
		sb.append("yusen_bin_kb ");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
//		sb.append(", ");
//		sb.append("tenbetu_haiso_cd ");
		sb.append(", ");
		sb.append("bin_1_kb ");
//		sb.append(", ");
//		sb.append("hachu_pattern_1_kb ");
//		sb.append(", ");
//		sb.append("sime_time_1_qt ");
//		sb.append(", ");
//		sb.append("c_nohin_rtime_1_kb ");
//		sb.append(", ");
//		sb.append("ten_nohin_rtime_1_kb ");
//		sb.append(", ");
//		sb.append("ten_nohin_time_1_kb ");
		sb.append(", ");
		sb.append("bin_2_kb ");
//		sb.append(", ");
//		sb.append("hachu_pattern_2_kb ");
//		sb.append(", ");
//		sb.append("sime_time_2_qt ");
//		sb.append(", ");
//		sb.append("c_nohin_rtime_2_kb ");
//		sb.append(", ");
//		sb.append("ten_nohin_rtime_2_kb ");
//		sb.append(", ");
//		sb.append("ten_nohin_time_2_kb ");
//		sb.append(", ");
//		sb.append("bin_3_kb ");
//		sb.append(", ");
//		sb.append("hachu_pattern_3_kb ");
//		sb.append(", ");
//		sb.append("sime_time_3_qt ");
//		sb.append(", ");
//		sb.append("c_nohin_rtime_3_kb ");
//		sb.append(", ");
//		sb.append("ten_nohin_rtime_3_kb ");
//		sb.append(", ");
//		sb.append("ten_nohin_time_3_kb ");
//		sb.append(", ");
//		sb.append("c_nohin_rtime_kb ");
//		sb.append(", ");
//		sb.append("syohin_kb ");
//		sb.append(", ");
//		sb.append("buturyu_kb ");
//		sb.append(", ");
//		sb.append("ten_zaiko_kb ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
//		sb.append(", ");
//		sb.append("delete_fg ");
// ↓↓仕様追加による変更（2005.06.30）↓↓
//		sb.append(", ");
//		sb.append("sinki_toroku_dt ");
//		sb.append(", ");
//		sb.append("henko_dt ");
// ↑↑仕様追加による変更（2005.06.30）↑↑
		sb.append("from r_tensyohin_reigai ");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		wk = getWhereSqlStr(map, "hanku_cd", whereStr);
		wk = getWhereSqlStr(map, "bumon_cd", whereStr);
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑

		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "syohin_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "tenpo_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "yuko_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_hachu_st_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_hachu_ed_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlNum(map, "gentanka_vl", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlNum(map, "baitanka_vl", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlNum(map, "max_hachutani_qt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "eos_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "siiresaki_cd", whereStr);
		sb.append(wk);
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
		whereStr = andStr;
		wk = getWhereSqlStr(map, "yusen_bin_kb", whereStr);
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "tenbetu_haiso_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "bin_1_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "hachu_pattern_1_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlNum(map, "sime_time_1_qt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "c_nohin_rtime_1_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_nohin_rtime_1_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_nohin_time_1_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "bin_2_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "hachu_pattern_2_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlNum(map, "sime_time_2_qt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "c_nohin_rtime_2_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_nohin_rtime_2_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_nohin_time_2_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "bin_3_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "hachu_pattern_3_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlNum(map, "sime_time_3_qt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "c_nohin_rtime_3_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_nohin_rtime_3_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_nohin_time_3_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "c_nohin_rtime_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "syohin_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "buturyu_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "ten_zaiko_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "delete_fg", whereStr);
		sb.append(wk);
		whereStr = andStr;
// ↓↓仕様追加による変更（2005.06.30）↓↓
		wk = getWhereSqlStr(map, "sinki_toroku_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "henko_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
// ↑↑仕様追加による変更（2005.06.30）↑↑
		sb.append(" order by ");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append("hanku_cd");
		sb.append("bumon_cd");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("yuko_dt");
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst180501_TensyohinReigaiBean bean = (mst180501_TensyohinReigaiBean)beanMst;
		StringBuffer sb = new StringBuffer();

		String value = "1"; 

		sb.append("insert into r_tensyohin_reigai (")
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				.append("hanku_cd,")
				.append("bumon_cd,")
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				.append("syohin_cd,")
				.append("tenpo_cd,")
				.append("yuko_dt,")
				.append("ten_hachu_st_dt,")
				.append("ten_hachu_ed_dt,")
				.append("gentanka_vl,")
				.append("baitanka_vl,")
				.append("max_hachutani_qt,")
				.append("eos_kb,")
				.append("siiresaki_cd,")
				
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
				.append("yusen_bin_kb,")
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				
				.append("tenbetu_haiso_cd,")
				.append("bin_1_kb,")
				.append("hachu_pattern_1_kb,")
				.append("sime_time_1_qt,")
				.append("c_nohin_rtime_1_kb,")
				.append("ten_nohin_rtime_1_kb,")
				.append("ten_nohin_time_1_kb,")
				.append("bin_2_kb,")
				.append("hachu_pattern_2_kb,")
				.append("sime_time_2_qt,")
				.append("c_nohin_rtime_2_kb,")
				.append("ten_nohin_rtime_2_kb,")
				.append("ten_nohin_time_2_kb,")
				.append("bin_3_kb,")
				.append("hachu_pattern_3_kb,")
				.append("sime_time_3_qt,")
				.append("c_nohin_rtime_3_kb,")
				.append("ten_nohin_rtime_3_kb,")
				.append("ten_nohin_time_3_kb,")
				.append("c_nohin_rtime_kb,")
				.append("syohin_kb,")
				.append("buturyu_kb,")
				.append("ten_zaiko_kb,")
				.append("henko_dt,")
				.append("insert_ts,")
				.append("insert_user_id,")
				.append("update_ts,")
				.append("update_user_id,")
				.append("delete_fg,")
				.append("sinki_toroku_dt")
				.append(") select ");

		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append(getColumnValue("hanku_cd", bean.getHankuCd(), true, conv, bean.getGamenFlg()));
		sb.append(getColumnValue("bumon_cd", bean.getBumonCd(), true, conv, bean.getGamenFlg()));
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append(",");
		sb.append(getColumnValue("syohin_cd", bean.getSyohinCd(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("tenpo_cd", bean.getTenpoCd(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("yuko_dt", bean.getYukoDt(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_hachu_st_dt", bean.getTenHachuStDt(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_hachu_ed_dt", bean.getTenHachuEdDt(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("gentanka_vl", bean.getGentankaVlString(),false,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("baitanka_vl", bean.getBaitankaVlString(),false,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("max_hachutani_qt", bean.getMaxHachutaniQtString(),false,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("eos_kb", bean.getEosKb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("siiresaki_cd", bean.getSiiresakiCd(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append(getColumnValue("area_cd", bean.getSiiresakiCd(),true,conv, bean.getGamenFlg()));
//		sb.append(",");
		sb.append(getColumnValue("yusen_bin_kb", bean.getYusenBinKb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append(getColumnValue("tenbetu_haiso_cd", bean.getTenbetuHaisoCd(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("bin_1_kb", bean.getBin1Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("hachu_pattern_1_kb", bean.getHachuPattern1Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
// ↓↓仕様変更（2005.08.24）↓↓
//		sb.append(getColumnValue("sime_time_1_qt", null,false,conv, bean.getGamenFlg()));
		sb.append(getColumnValue("sime_time_1_qt", bean.getSimeTime1QtString(),true,conv, bean.getGamenFlg()));
// ↑↑仕様変更（2005.08.24）↑↑
		sb.append(",");
		sb.append(getColumnValue("c_nohin_rtime_1_kb", bean.getCNohinRtime1Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_nohin_rtime_1_kb", bean.getTenNohinRtime1Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_nohin_time_1_kb", bean.getTenNohinTime1Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("bin_2_kb", bean.getBin2Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("hachu_pattern_2_kb", bean.getHachuPattern2Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
// ↓↓仕様変更（2005.08.24）↓↓
//		sb.append(getColumnValue("sime_time_2_qt", null,false,conv, bean.getGamenFlg()));
		sb.append(getColumnValue("sime_time_2_qt", bean.getSimeTime2QtString(),true,conv, bean.getGamenFlg()));
// ↑↑仕様変更（2005.08.24）↑↑
		sb.append(",");
		sb.append(getColumnValue("c_nohin_rtime_2_kb", bean.getCNohinRtime2Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_nohin_rtime_2_kb", bean.getTenNohinRtime2Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_nohin_time_2_kb", bean.getTenNohinTime2Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("bin_3_kb", bean.getBin3Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("hachu_pattern_3_kb", bean.getHachuPattern3Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("sime_time_3_qt", null,false,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("c_nohin_rtime_3_kb", bean.getCNohinRtime3Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_nohin_rtime_3_kb", bean.getTenNohinRtime3Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_nohin_time_3_kb", bean.getTenNohinTime3Kb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("c_nohin_rtime_kb", bean.getCNohinRtimeKb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("syohin_kb", bean.getSyohinKb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("buturyu_kb", bean.getButuryuKb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("ten_zaiko_kb", bean.getTenZaikoKb(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("henko_dt", bean.getHenkoDt(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
//	        ↓↓移植（2006.05.11）↓↓
			try {
				sb.append("( ");
				sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
				sb.append(")as insert_ts ");
			} catch (SQLException e){
				    e.printStackTrace();
				}
		} else {
			sb.append("max(insert_ts) as insert_ts ");
		}
		sb.append(",");
		sb.append(getColumnValue("insert_user_id", bean.getInsertUserId(),true,conv, bean.getGamenFlg()));
		sb.append(",");
//		↓↓2006.07.14 xiongjun カスタマイズ修正↓↓
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			try {
				sb.append("( ");
				sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
				sb.append(")as update_ts ");
			} catch (SQLException e){
				    e.printStackTrace();
				}
		} else {
			sb.append("max(update_ts) as update_ts ");
		}
		sb.append(",");
		sb.append(getColumnValue("update_user_id", bean.getUpdateUserId(),true,conv, bean.getGamenFlg()));
//		↑↑2006.07.14 xiongjun カスタマイズ修正↑↑
//	    ↑↑移植（2006.05.11）↑↑		
		sb.append(",");
		sb.append(getColumnValue("delete_fg", bean.getDeleteFg(),true,conv, bean.getGamenFlg()));
		sb.append(",");
		sb.append(getColumnValue("sinki_toroku_dt", bean.getSinkiTorokuDt(),true,conv, bean.getGamenFlg()));
		sb.append(" from r_tensyohin_reigai ");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append(" where hanku_cd = '").append(conv.convertWhereString( bean.getHankuCd() )).append("'");
		sb.append(" where bumon_cd = '").append(conv.convertWhereString( bean.getBumonCd() )).append("'");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append("   and syohin_cd = '").append(conv.convertWhereString( bean.getSyohinCd() )).append("'");
		sb.append("   and tenpo_cd = '").append(conv.convertWhereString( bean.getTenpoCd() )).append("'");
		sb.append("   and yuko_dt = (");
//	      ↓↓移植（2006.05.30）↓↓
		sb.append("	 select case");
		sb.append("	     when tbl11.yuko_dt > tbl12.yuko_dt then");
		sb.append("	       tbl12.yuko_dt");
		sb.append("	     else");
		sb.append("	       tbl11.yuko_dt");
		sb.append("	     end YUKO_DT");
//		↓↓2006.07.12 shijun 修正 ↓↓		
//		sb.append("   from (SELECT MAX(yuko_dt) YUKO_DT,hanku_cd,syohin_cd,tenpo_cd" );
		sb.append("   from (SELECT MAX(yuko_dt) YUKO_DT,bumon_cd,syohin_cd,tenpo_cd" );
//		↑↑2006.07.12 shijun 修正 ↑↑	
		
		sb.append("           FROM r_tensyohin_reigai" );
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append("           where hanku_cd = '" ).append(conv.convertWhereString( bean.getHankuCd() )).append("'");
		sb.append("           where bumon_cd = '" ).append(conv.convertWhereString( bean.getBumonCd() )).append("'");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append("              and syohin_cd = '").append(conv.convertWhereString( bean.getSyohinCd() )).append("'");
		sb.append("              and tenpo_cd = '").append(conv.convertWhereString( bean.getTenpoCd() )).append("'");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append(" 	and yuko_dt <= TO_CHAR(SYSDATE + " + Integer.toString(Integer.parseInt(value) - 1) + " ,'YYYYMMDD') ");
		if(mst000401_LogicBean.chkNullString(bean.getYukoDt()).equals("")){
			sb.append(" 	     and yuko_dt <= TO_CHAR("+ RMSTDATEUtil.getMstDateDt() +" + " + Integer.toString(Integer.parseInt(value) - 1) + ") ");
		} else{
			sb.append(" 	     and yuko_dt <= TO_CHAR("+ conv.convertWhereString(bean.getYukoDt())+" + " + Integer.toString(Integer.parseInt(value) - 1) + ") ");
		}
// 2006.01.23 Y.Inaba Mod ↑
		sb.append(" 	         and DELETE_FG = '"+mst000801_DelFlagDictionary.INAI.getCode()+"'");
		
//		sb.append("           group by hanku_cd,syohin_cd,tenpo_cd) TBL11, ");
		sb.append("           group by bumon_cd,syohin_cd,tenpo_cd) TBL11, ");
		//2006.07.10 chencl 修正 ↓
		sb.append("	 (SELECT MIN(yuko_dt) YUKO_DT,bumon_cd,syohin_cd,tenpo_cd ");
		sb.append("   FROM r_tensyohin_reigai " );
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
//		sb.append("   where hanku_cd = '").append(conv.convertWhereString( bean.getHankuCd() )).append("'");
		sb.append("   where bumon_cd = '").append(conv.convertWhereString( bean.getBumonCd() )).append("'");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append("     and syohin_cd = '").append(conv.convertWhereString( bean.getSyohinCd() )).append("'");
		sb.append("     and tenpo_cd = '").append(conv.convertWhereString( bean.getTenpoCd() )).append("'");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append(" 	 AND yuko_dt >= TO_CHAR(SYSDATE + " + value + " ,'YYYYMMDD') ");
		if(mst000401_LogicBean.chkNullString(bean.getYukoDt()).equals("")){
			sb.append(" 	and yuko_dt >= TO_CHAR("+ RMSTDATEUtil.getMstDateDt() +" + " + Integer.toString(Integer.parseInt(value) - 1) + ") ");
		} else{
			sb.append(" 	and yuko_dt >= TO_CHAR("+ conv.convertWhereString(bean.getYukoDt())+" + " + Integer.toString(Integer.parseInt(value) - 1) + ") ");
		}
// 2006.01.23 Y.Inaba Mod ↑
//		↓↓2006.07.12 shijun 修正 ↓↓	
//		sb.append(" 	group by hanku_cd,syohin_cd,tenpo_cd) TBL12");
//		sb.append("  where tbl11.hanku_cd = tbl12.hanku_cd");
		sb.append(" 	group by bumon_cd,syohin_cd,tenpo_cd) TBL12");
		sb.append("  where tbl11.bumon_cd = tbl12.bumon_cd");
//		↑↑2006.07.12 shijun 修正 ↑↑
		
		sb.append("    and tbl11.syohin_cd = tbl12.syohin_cd");
		sb.append("    and tbl11.tenpo_cd = tbl12.tenpo_cd)");
//	      ↑↑移植（2006.05.30）↑↑
		
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
//		      ↓↓移植（2006.05.30）↓↓
				sb.append(" cast(null as char) as ").append(colName);
//		      ↑↑移植（2006.05.30）↑↑
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
			if (needQt) {
				sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(value) ) + "'");
			} else {
				if (value != null && value.length() > 0) {
					sb.append(value);
				} else {
					sb.append(" null ");
				}
			}
		} else {
			sb.append(",");
			sb.append(colName).append(" = ");
			sb.append(" null ");
			
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst180501_TensyohinReigaiBean bean = (mst180501_TensyohinReigaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_tensyohin_reigai set ");
		
		StringBuffer cols = new StringBuffer();
		cols.append(getUpdColumnValue("ten_hachu_st_dt", bean.getTenHachuStDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("ten_hachu_ed_dt", bean.getTenHachuEdDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("gentanka_vl", bean.getGentankaVlString(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("baitanka_vl", bean.getBaitankaVlString(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("max_hachutani_qt", bean.getMaxHachutaniQtString(), false, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("eos_kb", bean.getEosKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("siiresaki_cd", bean.getSiiresakiCd(), true, conv, bean.getGamenFlg(), cols.length()));
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		cols.append(getUpdColumnValue("area_cd", bean.getAreaCd(), true, conv, bean.getGamenFlg(), cols.length()));

//		cols.append(getUpdColumnValue("tenbetu_haiso_cd", bean.getTenbetuHaisoCd(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("bin_1_kb", bean.getBin1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("hachu_pattern_1_kb", bean.getHachuPattern1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
// ↓↓仕様変更（2005.08.24）↓↓
//		cols.append(getUpdColumnValue("sime_time_1_qt", bean.getSimeTime1QtString(), false, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("sime_time_1_qt", bean.getSimeTime1QtString(), true, conv, bean.getGamenFlg(), cols.length()));
// ↑↑仕様変更（2005.08.24）↑↑
//		cols.append(getUpdColumnValue("c_nohin_rtime_1_kb", bean.getCNohinRtime1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("ten_nohin_rtime_1_kb", bean.getTenNohinRtime1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("ten_nohin_time_1_kb", bean.getTenNohinTime1Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("bin_2_kb", bean.getBin2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("yusen_bin_kb", bean.getYusenBinKb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("hachu_pattern_2_kb", bean.getHachuPattern2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
// ↓↓仕様変更（2005.08.24）↓↓
//		cols.append(getUpdColumnValue("sime_time_2_qt", bean.getSimeTime2QtString(), false, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("sime_time_2_qt", bean.getSimeTime2QtString(), true, conv, bean.getGamenFlg(), cols.length()));
// ↑↑仕様変更（2005.08.24）↑↑
//		cols.append(getUpdColumnValue("c_nohin_rtime_2_kb", bean.getCNohinRtime2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("ten_nohin_rtime_2_kb", bean.getTenNohinRtime2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("ten_nohin_time_2_kb", bean.getTenNohinTime2Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("bin_3_kb", bean.getBin3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("hachu_pattern_3_kb", bean.getHachuPattern3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("sime_time_3_qt", bean.getSimeTime3QtString(), false, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("c_nohin_rtime_3_kb", bean.getCNohinRtime3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("ten_nohin_rtime_3_kb", bean.getTenNohinRtime3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("ten_nohin_time_3_kb", bean.getTenNohinTime3Kb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("c_nohin_rtime_kb", bean.getCNohinRtimeKb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("syohin_kb", bean.getSyohinKb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("buturyu_kb", bean.getButuryuKb(), true, conv, bean.getGamenFlg(), cols.length()));
//		cols.append(getUpdColumnValue("ten_zaiko_kb", bean.getTenZaikoKb(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("insert_ts", bean.getInsertTs(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("insert_user_id", bean.getInsertUserId(), true, conv, bean.getGamenFlg(), cols.length()));
// ↓↓仕様変更（2005.08.04）↓↓
		cols.append(getUpdColumnValue("sinki_toroku_dt", bean.getSinkiTorokuDt(), true, conv, bean.getGamenFlg(), cols.length()));
		cols.append(getUpdColumnValue("henko_dt", bean.getHenkoDt(), true, conv, bean.getGamenFlg(), cols.length()));
		sb.append(cols.toString());
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts = ");
//			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
//		    ↓↓移植（2006.05.11）↓↓
			try {
				sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
			} catch (SQLException e){
			    e.printStackTrace();
			}
//		    ↑↑移植（2006.05.11）↑↑		
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}

		sb.append(" WHERE");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
//		{
//			whereAnd = true;
//			sb.append(" hanku_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" bumon_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		}
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst180501_TensyohinReigaiBean bean = (mst180501_TensyohinReigaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_tensyohin_reigai where ");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓

//		sb.append(" hanku_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
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
}