/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/18)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/18)初版作成
 */
public class mst531001_TensyohinReigaiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst531001_TensyohinReigaiDM()
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
		mst531001_TensyohinReigaiBean bean = new mst531001_TensyohinReigaiBean();
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setTenHachuStDt( rest.getString("ten_hachu_st_dt") );
		bean.setTenHachuEdDt( rest.getString("ten_hachu_ed_dt") );
		bean.setGentankaVl( rest.getString("gentanka_vl") );
		bean.setBaitankaVl( rest.getString("baitanka_vl") );
		bean.setMaxHachutaniQt( rest.getString("max_hachutani_qt") );
		bean.setEosKb( rest.getString("eos_kb") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setTenbetuHaisoCd( rest.getString("tenbetu_haiso_cd") );
		bean.setBin1Kb( rest.getString("bin_1_kb") );
		bean.setHachuPattern1Kb( rest.getString("hachu_pattern_1_kb") );
		bean.setSimeTime1Qt( rest.getString("sime_time_1_qt") );
		bean.setCNohinRtime1Kb( rest.getString("c_nohin_rtime_1_kb") );
		bean.setTenNohinRtime1Kb( rest.getString("ten_nohin_rtime_1_kb") );
		bean.setTenNohinTime1Kb( rest.getString("ten_nohin_time_1_kb") );
		bean.setBin2Kb( rest.getString("bin_2_kb") );
		bean.setHachuPattern2Kb( rest.getString("hachu_pattern_2_kb") );
		bean.setSimeTime2Qt( rest.getString("sime_time_2_qt") );
		bean.setCNohinRtime2Kb( rest.getString("c_nohin_rtime_2_kb") );
		bean.setTenNohinRtime2Kb( rest.getString("ten_nohin_rtime_2_kb") );
		bean.setTenNohinTime2Kb( rest.getString("ten_nohin_time_2_kb") );
		bean.setBin3Kb( rest.getString("bin_3_kb") );
		bean.setHachuPattern3Kb( rest.getString("hachu_pattern_3_kb") );
		bean.setSimeTime3Qt( rest.getString("sime_time_3_qt") );
		bean.setCNohinRtime3Kb( rest.getString("c_nohin_rtime_3_kb") );
		bean.setTenNohinRtime3Kb( rest.getString("ten_nohin_rtime_3_kb") );
		bean.setTenNohinTime3Kb( rest.getString("ten_nohin_time_3_kb") );
		bean.setCNohinRtimeKb( rest.getString("c_nohin_rtime_kb") );
		bean.setSyohinKb( rest.getString("syohin_kb") );
		bean.setButuryuKb( rest.getString("buturyu_kb") );
		bean.setTenZaikoKb( rest.getString("ten_zaiko_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
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
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("hanku_cd ");
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
		sb.append(", ");
		sb.append("tenbetu_haiso_cd ");
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
		sb.append("syohin_kb ");
		sb.append(", ");
		sb.append("buturyu_kb ");
		sb.append(", ");
		sb.append("ten_zaiko_kb ");
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
		sb.append("from R_TENSYOHIN_REIGAI ");


		// hanku_cd に対するWHERE区
		if( map.get("hanku_cd_bef") != null && ((String)map.get("hanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd >= '" + conv.convertWhereString( (String)map.get("hanku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_aft") != null && ((String)map.get("hanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd <= '" + conv.convertWhereString( (String)map.get("hanku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_like") != null && ((String)map.get("hanku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd like '%" + conv.convertWhereString( (String)map.get("hanku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_bef_like") != null && ((String)map.get("hanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd like '%" + conv.convertWhereString( (String)map.get("hanku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_aft_like") != null && ((String)map.get("hanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd like '" + conv.convertWhereString( (String)map.get("hanku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_in") != null && ((String)map.get("hanku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_not_in") != null && ((String)map.get("hanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_cd に対するWHERE区
		if( map.get("syohin_cd_bef") != null && ((String)map.get("syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd >= '" + conv.convertWhereString( (String)map.get("syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft") != null && ((String)map.get("syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd <= '" + conv.convertWhereString( (String)map.get("syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_like") != null && ((String)map.get("syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_bef_like") != null && ((String)map.get("syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft_like") != null && ((String)map.get("syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '" + conv.convertWhereString( (String)map.get("syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_in") != null && ((String)map.get("syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_not_in") != null && ((String)map.get("syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd >= '" + conv.convertWhereString( (String)map.get("tenpo_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft") != null && ((String)map.get("tenpo_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd <= '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_like") != null && ((String)map.get("tenpo_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_bef_like") != null && ((String)map.get("tenpo_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft_like") != null && ((String)map.get("tenpo_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_in") != null && ((String)map.get("tenpo_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_not_in") != null && ((String)map.get("tenpo_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// yuko_dt に対するWHERE区
		if( map.get("yuko_dt_bef") != null && ((String)map.get("yuko_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt >= '" + conv.convertWhereString( (String)map.get("yuko_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_aft") != null && ((String)map.get("yuko_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt <= '" + conv.convertWhereString( (String)map.get("yuko_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_like") != null && ((String)map.get("yuko_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '%" + conv.convertWhereString( (String)map.get("yuko_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_bef_like") != null && ((String)map.get("yuko_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '%" + conv.convertWhereString( (String)map.get("yuko_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_aft_like") != null && ((String)map.get("yuko_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '" + conv.convertWhereString( (String)map.get("yuko_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_in") != null && ((String)map.get("yuko_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_not_in") != null && ((String)map.get("yuko_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_hachu_st_dt に対するWHERE区
		if( map.get("ten_hachu_st_dt_bef") != null && ((String)map.get("ten_hachu_st_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_st_dt >= '" + conv.convertWhereString( (String)map.get("ten_hachu_st_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_st_dt_aft") != null && ((String)map.get("ten_hachu_st_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_st_dt <= '" + conv.convertWhereString( (String)map.get("ten_hachu_st_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_st_dt") != null && ((String)map.get("ten_hachu_st_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_st_dt = '" + conv.convertWhereString( (String)map.get("ten_hachu_st_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_st_dt_like") != null && ((String)map.get("ten_hachu_st_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_st_dt like '%" + conv.convertWhereString( (String)map.get("ten_hachu_st_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_st_dt_bef_like") != null && ((String)map.get("ten_hachu_st_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_st_dt like '%" + conv.convertWhereString( (String)map.get("ten_hachu_st_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_st_dt_aft_like") != null && ((String)map.get("ten_hachu_st_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_st_dt like '" + conv.convertWhereString( (String)map.get("ten_hachu_st_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_st_dt_in") != null && ((String)map.get("ten_hachu_st_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_st_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_hachu_st_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_st_dt_not_in") != null && ((String)map.get("ten_hachu_st_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_st_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_hachu_st_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_hachu_ed_dt に対するWHERE区
		if( map.get("ten_hachu_ed_dt_bef") != null && ((String)map.get("ten_hachu_ed_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_ed_dt >= '" + conv.convertWhereString( (String)map.get("ten_hachu_ed_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_ed_dt_aft") != null && ((String)map.get("ten_hachu_ed_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_ed_dt <= '" + conv.convertWhereString( (String)map.get("ten_hachu_ed_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_ed_dt") != null && ((String)map.get("ten_hachu_ed_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_ed_dt = '" + conv.convertWhereString( (String)map.get("ten_hachu_ed_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_ed_dt_like") != null && ((String)map.get("ten_hachu_ed_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_ed_dt like '%" + conv.convertWhereString( (String)map.get("ten_hachu_ed_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_ed_dt_bef_like") != null && ((String)map.get("ten_hachu_ed_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_ed_dt like '%" + conv.convertWhereString( (String)map.get("ten_hachu_ed_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_ed_dt_aft_like") != null && ((String)map.get("ten_hachu_ed_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_ed_dt like '" + conv.convertWhereString( (String)map.get("ten_hachu_ed_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_ed_dt_in") != null && ((String)map.get("ten_hachu_ed_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_ed_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_hachu_ed_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_hachu_ed_dt_not_in") != null && ((String)map.get("ten_hachu_ed_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hachu_ed_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_hachu_ed_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// gentanka_vl に対するWHERE区
		if( map.get("gentanka_vl_bef") != null && ((String)map.get("gentanka_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl >= '" + conv.convertWhereString( (String)map.get("gentanka_vl_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_aft") != null && ((String)map.get("gentanka_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl <= '" + conv.convertWhereString( (String)map.get("gentanka_vl_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl") != null && ((String)map.get("gentanka_vl")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl = '" + conv.convertWhereString( (String)map.get("gentanka_vl") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_like") != null && ((String)map.get("gentanka_vl_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl like '%" + conv.convertWhereString( (String)map.get("gentanka_vl_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_bef_like") != null && ((String)map.get("gentanka_vl_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl like '%" + conv.convertWhereString( (String)map.get("gentanka_vl_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_aft_like") != null && ((String)map.get("gentanka_vl_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl like '" + conv.convertWhereString( (String)map.get("gentanka_vl_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_in") != null && ((String)map.get("gentanka_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gentanka_vl_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_not_in") != null && ((String)map.get("gentanka_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gentanka_vl_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// baitanka_vl に対するWHERE区
		if( map.get("baitanka_vl_bef") != null && ((String)map.get("baitanka_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl >= '" + conv.convertWhereString( (String)map.get("baitanka_vl_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_aft") != null && ((String)map.get("baitanka_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl <= '" + conv.convertWhereString( (String)map.get("baitanka_vl_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("baitanka_vl") != null && ((String)map.get("baitanka_vl")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl = '" + conv.convertWhereString( (String)map.get("baitanka_vl") ) + "'");
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_like") != null && ((String)map.get("baitanka_vl_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl like '%" + conv.convertWhereString( (String)map.get("baitanka_vl_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_bef_like") != null && ((String)map.get("baitanka_vl_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl like '%" + conv.convertWhereString( (String)map.get("baitanka_vl_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_aft_like") != null && ((String)map.get("baitanka_vl_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl like '" + conv.convertWhereString( (String)map.get("baitanka_vl_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_in") != null && ((String)map.get("baitanka_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl in ( '" + replaceAll(conv.convertWhereString( (String)map.get("baitanka_vl_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_not_in") != null && ((String)map.get("baitanka_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("baitanka_vl_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// max_hachutani_qt に対するWHERE区
		if( map.get("max_hachutani_qt_bef") != null && ((String)map.get("max_hachutani_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachutani_qt >= '" + conv.convertWhereString( (String)map.get("max_hachutani_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("max_hachutani_qt_aft") != null && ((String)map.get("max_hachutani_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachutani_qt <= '" + conv.convertWhereString( (String)map.get("max_hachutani_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("max_hachutani_qt") != null && ((String)map.get("max_hachutani_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachutani_qt = '" + conv.convertWhereString( (String)map.get("max_hachutani_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("max_hachutani_qt_like") != null && ((String)map.get("max_hachutani_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachutani_qt like '%" + conv.convertWhereString( (String)map.get("max_hachutani_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("max_hachutani_qt_bef_like") != null && ((String)map.get("max_hachutani_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachutani_qt like '%" + conv.convertWhereString( (String)map.get("max_hachutani_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("max_hachutani_qt_aft_like") != null && ((String)map.get("max_hachutani_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachutani_qt like '" + conv.convertWhereString( (String)map.get("max_hachutani_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("max_hachutani_qt_in") != null && ((String)map.get("max_hachutani_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachutani_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("max_hachutani_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("max_hachutani_qt_not_in") != null && ((String)map.get("max_hachutani_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachutani_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("max_hachutani_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// eos_kb に対するWHERE区
		if( map.get("eos_kb_bef") != null && ((String)map.get("eos_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb >= '" + conv.convertWhereString( (String)map.get("eos_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_aft") != null && ((String)map.get("eos_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb <= '" + conv.convertWhereString( (String)map.get("eos_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eos_kb") != null && ((String)map.get("eos_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb = '" + conv.convertWhereString( (String)map.get("eos_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_like") != null && ((String)map.get("eos_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb like '%" + conv.convertWhereString( (String)map.get("eos_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_bef_like") != null && ((String)map.get("eos_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb like '%" + conv.convertWhereString( (String)map.get("eos_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_aft_like") != null && ((String)map.get("eos_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb like '" + conv.convertWhereString( (String)map.get("eos_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_in") != null && ((String)map.get("eos_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eos_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("eos_kb_not_in") != null && ((String)map.get("eos_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eos_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// siiresaki_cd に対するWHERE区
		if( map.get("siiresaki_cd_bef") != null && ((String)map.get("siiresaki_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd >= '" + conv.convertWhereString( (String)map.get("siiresaki_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_aft") != null && ((String)map.get("siiresaki_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd <= '" + conv.convertWhereString( (String)map.get("siiresaki_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd = '" + conv.convertWhereString( (String)map.get("siiresaki_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_like") != null && ((String)map.get("siiresaki_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd like '%" + conv.convertWhereString( (String)map.get("siiresaki_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_bef_like") != null && ((String)map.get("siiresaki_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd like '%" + conv.convertWhereString( (String)map.get("siiresaki_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_aft_like") != null && ((String)map.get("siiresaki_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd like '" + conv.convertWhereString( (String)map.get("siiresaki_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_in") != null && ((String)map.get("siiresaki_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siiresaki_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_not_in") != null && ((String)map.get("siiresaki_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siiresaki_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenbetu_haiso_cd に対するWHERE区
		if( map.get("tenbetu_haiso_cd_bef") != null && ((String)map.get("tenbetu_haiso_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenbetu_haiso_cd >= '" + conv.convertWhereString( (String)map.get("tenbetu_haiso_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenbetu_haiso_cd_aft") != null && ((String)map.get("tenbetu_haiso_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenbetu_haiso_cd <= '" + conv.convertWhereString( (String)map.get("tenbetu_haiso_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenbetu_haiso_cd") != null && ((String)map.get("tenbetu_haiso_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenbetu_haiso_cd = '" + conv.convertWhereString( (String)map.get("tenbetu_haiso_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenbetu_haiso_cd_like") != null && ((String)map.get("tenbetu_haiso_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenbetu_haiso_cd like '%" + conv.convertWhereString( (String)map.get("tenbetu_haiso_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenbetu_haiso_cd_bef_like") != null && ((String)map.get("tenbetu_haiso_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenbetu_haiso_cd like '%" + conv.convertWhereString( (String)map.get("tenbetu_haiso_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenbetu_haiso_cd_aft_like") != null && ((String)map.get("tenbetu_haiso_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenbetu_haiso_cd like '" + conv.convertWhereString( (String)map.get("tenbetu_haiso_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenbetu_haiso_cd_in") != null && ((String)map.get("tenbetu_haiso_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenbetu_haiso_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenbetu_haiso_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenbetu_haiso_cd_not_in") != null && ((String)map.get("tenbetu_haiso_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenbetu_haiso_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenbetu_haiso_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// bin_1_kb に対するWHERE区
		if( map.get("bin_1_kb_bef") != null && ((String)map.get("bin_1_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_1_kb >= '" + conv.convertWhereString( (String)map.get("bin_1_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_1_kb_aft") != null && ((String)map.get("bin_1_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_1_kb <= '" + conv.convertWhereString( (String)map.get("bin_1_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_1_kb") != null && ((String)map.get("bin_1_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_1_kb = '" + conv.convertWhereString( (String)map.get("bin_1_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_1_kb_like") != null && ((String)map.get("bin_1_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_1_kb like '%" + conv.convertWhereString( (String)map.get("bin_1_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bin_1_kb_bef_like") != null && ((String)map.get("bin_1_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_1_kb like '%" + conv.convertWhereString( (String)map.get("bin_1_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_1_kb_aft_like") != null && ((String)map.get("bin_1_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_1_kb like '" + conv.convertWhereString( (String)map.get("bin_1_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bin_1_kb_in") != null && ((String)map.get("bin_1_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_1_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bin_1_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("bin_1_kb_not_in") != null && ((String)map.get("bin_1_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_1_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bin_1_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_pattern_1_kb に対するWHERE区
		if( map.get("hachu_pattern_1_kb_bef") != null && ((String)map.get("hachu_pattern_1_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_1_kb >= '" + conv.convertWhereString( (String)map.get("hachu_pattern_1_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_1_kb_aft") != null && ((String)map.get("hachu_pattern_1_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_1_kb <= '" + conv.convertWhereString( (String)map.get("hachu_pattern_1_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_1_kb") != null && ((String)map.get("hachu_pattern_1_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_1_kb = '" + conv.convertWhereString( (String)map.get("hachu_pattern_1_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_1_kb_like") != null && ((String)map.get("hachu_pattern_1_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_1_kb like '%" + conv.convertWhereString( (String)map.get("hachu_pattern_1_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_1_kb_bef_like") != null && ((String)map.get("hachu_pattern_1_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_1_kb like '%" + conv.convertWhereString( (String)map.get("hachu_pattern_1_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_1_kb_aft_like") != null && ((String)map.get("hachu_pattern_1_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_1_kb like '" + conv.convertWhereString( (String)map.get("hachu_pattern_1_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_1_kb_in") != null && ((String)map.get("hachu_pattern_1_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_1_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_pattern_1_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_1_kb_not_in") != null && ((String)map.get("hachu_pattern_1_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_1_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_pattern_1_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sime_time_1_qt に対するWHERE区
		if( map.get("sime_time_1_qt_bef") != null && ((String)map.get("sime_time_1_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_1_qt >= '" + conv.convertWhereString( (String)map.get("sime_time_1_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_1_qt_aft") != null && ((String)map.get("sime_time_1_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_1_qt <= '" + conv.convertWhereString( (String)map.get("sime_time_1_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_1_qt") != null && ((String)map.get("sime_time_1_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_1_qt = '" + conv.convertWhereString( (String)map.get("sime_time_1_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_1_qt_like") != null && ((String)map.get("sime_time_1_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_1_qt like '%" + conv.convertWhereString( (String)map.get("sime_time_1_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sime_time_1_qt_bef_like") != null && ((String)map.get("sime_time_1_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_1_qt like '%" + conv.convertWhereString( (String)map.get("sime_time_1_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_1_qt_aft_like") != null && ((String)map.get("sime_time_1_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_1_qt like '" + conv.convertWhereString( (String)map.get("sime_time_1_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sime_time_1_qt_in") != null && ((String)map.get("sime_time_1_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_1_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sime_time_1_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sime_time_1_qt_not_in") != null && ((String)map.get("sime_time_1_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_1_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sime_time_1_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// c_nohin_rtime_1_kb に対するWHERE区
		if( map.get("c_nohin_rtime_1_kb_bef") != null && ((String)map.get("c_nohin_rtime_1_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_1_kb >= '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_1_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_1_kb_aft") != null && ((String)map.get("c_nohin_rtime_1_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_1_kb <= '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_1_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_1_kb") != null && ((String)map.get("c_nohin_rtime_1_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_1_kb = '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_1_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_1_kb_like") != null && ((String)map.get("c_nohin_rtime_1_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_1_kb like '%" + conv.convertWhereString( (String)map.get("c_nohin_rtime_1_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_1_kb_bef_like") != null && ((String)map.get("c_nohin_rtime_1_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_1_kb like '%" + conv.convertWhereString( (String)map.get("c_nohin_rtime_1_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_1_kb_aft_like") != null && ((String)map.get("c_nohin_rtime_1_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_1_kb like '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_1_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_1_kb_in") != null && ((String)map.get("c_nohin_rtime_1_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_1_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("c_nohin_rtime_1_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_1_kb_not_in") != null && ((String)map.get("c_nohin_rtime_1_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_1_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("c_nohin_rtime_1_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_nohin_rtime_1_kb に対するWHERE区
		if( map.get("ten_nohin_rtime_1_kb_bef") != null && ((String)map.get("ten_nohin_rtime_1_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_1_kb >= '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_1_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_1_kb_aft") != null && ((String)map.get("ten_nohin_rtime_1_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_1_kb <= '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_1_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_1_kb") != null && ((String)map.get("ten_nohin_rtime_1_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_1_kb = '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_1_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_1_kb_like") != null && ((String)map.get("ten_nohin_rtime_1_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_1_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_1_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_1_kb_bef_like") != null && ((String)map.get("ten_nohin_rtime_1_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_1_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_1_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_1_kb_aft_like") != null && ((String)map.get("ten_nohin_rtime_1_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_1_kb like '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_1_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_1_kb_in") != null && ((String)map.get("ten_nohin_rtime_1_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_1_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_rtime_1_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_1_kb_not_in") != null && ((String)map.get("ten_nohin_rtime_1_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_1_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_rtime_1_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_nohin_time_1_kb に対するWHERE区
		if( map.get("ten_nohin_time_1_kb_bef") != null && ((String)map.get("ten_nohin_time_1_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_1_kb >= '" + conv.convertWhereString( (String)map.get("ten_nohin_time_1_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_1_kb_aft") != null && ((String)map.get("ten_nohin_time_1_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_1_kb <= '" + conv.convertWhereString( (String)map.get("ten_nohin_time_1_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_1_kb") != null && ((String)map.get("ten_nohin_time_1_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_1_kb = '" + conv.convertWhereString( (String)map.get("ten_nohin_time_1_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_1_kb_like") != null && ((String)map.get("ten_nohin_time_1_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_1_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_time_1_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_1_kb_bef_like") != null && ((String)map.get("ten_nohin_time_1_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_1_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_time_1_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_1_kb_aft_like") != null && ((String)map.get("ten_nohin_time_1_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_1_kb like '" + conv.convertWhereString( (String)map.get("ten_nohin_time_1_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_1_kb_in") != null && ((String)map.get("ten_nohin_time_1_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_1_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_time_1_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_1_kb_not_in") != null && ((String)map.get("ten_nohin_time_1_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_1_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_time_1_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// bin_2_kb に対するWHERE区
		if( map.get("bin_2_kb_bef") != null && ((String)map.get("bin_2_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_2_kb >= '" + conv.convertWhereString( (String)map.get("bin_2_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_2_kb_aft") != null && ((String)map.get("bin_2_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_2_kb <= '" + conv.convertWhereString( (String)map.get("bin_2_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_2_kb") != null && ((String)map.get("bin_2_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_2_kb = '" + conv.convertWhereString( (String)map.get("bin_2_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_2_kb_like") != null && ((String)map.get("bin_2_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_2_kb like '%" + conv.convertWhereString( (String)map.get("bin_2_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bin_2_kb_bef_like") != null && ((String)map.get("bin_2_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_2_kb like '%" + conv.convertWhereString( (String)map.get("bin_2_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_2_kb_aft_like") != null && ((String)map.get("bin_2_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_2_kb like '" + conv.convertWhereString( (String)map.get("bin_2_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bin_2_kb_in") != null && ((String)map.get("bin_2_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_2_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bin_2_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("bin_2_kb_not_in") != null && ((String)map.get("bin_2_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_2_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bin_2_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_pattern_2_kb に対するWHERE区
		if( map.get("hachu_pattern_2_kb_bef") != null && ((String)map.get("hachu_pattern_2_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_2_kb >= '" + conv.convertWhereString( (String)map.get("hachu_pattern_2_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_2_kb_aft") != null && ((String)map.get("hachu_pattern_2_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_2_kb <= '" + conv.convertWhereString( (String)map.get("hachu_pattern_2_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_2_kb") != null && ((String)map.get("hachu_pattern_2_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_2_kb = '" + conv.convertWhereString( (String)map.get("hachu_pattern_2_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_2_kb_like") != null && ((String)map.get("hachu_pattern_2_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_2_kb like '%" + conv.convertWhereString( (String)map.get("hachu_pattern_2_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_2_kb_bef_like") != null && ((String)map.get("hachu_pattern_2_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_2_kb like '%" + conv.convertWhereString( (String)map.get("hachu_pattern_2_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_2_kb_aft_like") != null && ((String)map.get("hachu_pattern_2_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_2_kb like '" + conv.convertWhereString( (String)map.get("hachu_pattern_2_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_2_kb_in") != null && ((String)map.get("hachu_pattern_2_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_2_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_pattern_2_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_2_kb_not_in") != null && ((String)map.get("hachu_pattern_2_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_2_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_pattern_2_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sime_time_2_qt に対するWHERE区
		if( map.get("sime_time_2_qt_bef") != null && ((String)map.get("sime_time_2_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_2_qt >= '" + conv.convertWhereString( (String)map.get("sime_time_2_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_2_qt_aft") != null && ((String)map.get("sime_time_2_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_2_qt <= '" + conv.convertWhereString( (String)map.get("sime_time_2_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_2_qt") != null && ((String)map.get("sime_time_2_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_2_qt = '" + conv.convertWhereString( (String)map.get("sime_time_2_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_2_qt_like") != null && ((String)map.get("sime_time_2_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_2_qt like '%" + conv.convertWhereString( (String)map.get("sime_time_2_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sime_time_2_qt_bef_like") != null && ((String)map.get("sime_time_2_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_2_qt like '%" + conv.convertWhereString( (String)map.get("sime_time_2_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_2_qt_aft_like") != null && ((String)map.get("sime_time_2_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_2_qt like '" + conv.convertWhereString( (String)map.get("sime_time_2_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sime_time_2_qt_in") != null && ((String)map.get("sime_time_2_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_2_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sime_time_2_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sime_time_2_qt_not_in") != null && ((String)map.get("sime_time_2_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_2_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sime_time_2_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// c_nohin_rtime_2_kb に対するWHERE区
		if( map.get("c_nohin_rtime_2_kb_bef") != null && ((String)map.get("c_nohin_rtime_2_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_2_kb >= '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_2_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_2_kb_aft") != null && ((String)map.get("c_nohin_rtime_2_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_2_kb <= '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_2_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_2_kb") != null && ((String)map.get("c_nohin_rtime_2_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_2_kb = '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_2_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_2_kb_like") != null && ((String)map.get("c_nohin_rtime_2_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_2_kb like '%" + conv.convertWhereString( (String)map.get("c_nohin_rtime_2_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_2_kb_bef_like") != null && ((String)map.get("c_nohin_rtime_2_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_2_kb like '%" + conv.convertWhereString( (String)map.get("c_nohin_rtime_2_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_2_kb_aft_like") != null && ((String)map.get("c_nohin_rtime_2_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_2_kb like '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_2_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_2_kb_in") != null && ((String)map.get("c_nohin_rtime_2_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_2_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("c_nohin_rtime_2_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_2_kb_not_in") != null && ((String)map.get("c_nohin_rtime_2_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_2_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("c_nohin_rtime_2_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_nohin_rtime_2_kb に対するWHERE区
		if( map.get("ten_nohin_rtime_2_kb_bef") != null && ((String)map.get("ten_nohin_rtime_2_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_2_kb >= '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_2_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_2_kb_aft") != null && ((String)map.get("ten_nohin_rtime_2_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_2_kb <= '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_2_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_2_kb") != null && ((String)map.get("ten_nohin_rtime_2_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_2_kb = '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_2_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_2_kb_like") != null && ((String)map.get("ten_nohin_rtime_2_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_2_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_2_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_2_kb_bef_like") != null && ((String)map.get("ten_nohin_rtime_2_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_2_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_2_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_2_kb_aft_like") != null && ((String)map.get("ten_nohin_rtime_2_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_2_kb like '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_2_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_2_kb_in") != null && ((String)map.get("ten_nohin_rtime_2_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_2_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_rtime_2_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_2_kb_not_in") != null && ((String)map.get("ten_nohin_rtime_2_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_2_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_rtime_2_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_nohin_time_2_kb に対するWHERE区
		if( map.get("ten_nohin_time_2_kb_bef") != null && ((String)map.get("ten_nohin_time_2_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_2_kb >= '" + conv.convertWhereString( (String)map.get("ten_nohin_time_2_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_2_kb_aft") != null && ((String)map.get("ten_nohin_time_2_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_2_kb <= '" + conv.convertWhereString( (String)map.get("ten_nohin_time_2_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_2_kb") != null && ((String)map.get("ten_nohin_time_2_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_2_kb = '" + conv.convertWhereString( (String)map.get("ten_nohin_time_2_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_2_kb_like") != null && ((String)map.get("ten_nohin_time_2_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_2_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_time_2_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_2_kb_bef_like") != null && ((String)map.get("ten_nohin_time_2_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_2_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_time_2_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_2_kb_aft_like") != null && ((String)map.get("ten_nohin_time_2_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_2_kb like '" + conv.convertWhereString( (String)map.get("ten_nohin_time_2_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_2_kb_in") != null && ((String)map.get("ten_nohin_time_2_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_2_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_time_2_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_2_kb_not_in") != null && ((String)map.get("ten_nohin_time_2_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_2_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_time_2_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// bin_3_kb に対するWHERE区
		if( map.get("bin_3_kb_bef") != null && ((String)map.get("bin_3_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_3_kb >= '" + conv.convertWhereString( (String)map.get("bin_3_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_3_kb_aft") != null && ((String)map.get("bin_3_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_3_kb <= '" + conv.convertWhereString( (String)map.get("bin_3_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_3_kb") != null && ((String)map.get("bin_3_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_3_kb = '" + conv.convertWhereString( (String)map.get("bin_3_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_3_kb_like") != null && ((String)map.get("bin_3_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_3_kb like '%" + conv.convertWhereString( (String)map.get("bin_3_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bin_3_kb_bef_like") != null && ((String)map.get("bin_3_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_3_kb like '%" + conv.convertWhereString( (String)map.get("bin_3_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_3_kb_aft_like") != null && ((String)map.get("bin_3_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_3_kb like '" + conv.convertWhereString( (String)map.get("bin_3_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bin_3_kb_in") != null && ((String)map.get("bin_3_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_3_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bin_3_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("bin_3_kb_not_in") != null && ((String)map.get("bin_3_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_3_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bin_3_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_pattern_3_kb に対するWHERE区
		if( map.get("hachu_pattern_3_kb_bef") != null && ((String)map.get("hachu_pattern_3_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_3_kb >= '" + conv.convertWhereString( (String)map.get("hachu_pattern_3_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_3_kb_aft") != null && ((String)map.get("hachu_pattern_3_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_3_kb <= '" + conv.convertWhereString( (String)map.get("hachu_pattern_3_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_3_kb") != null && ((String)map.get("hachu_pattern_3_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_3_kb = '" + conv.convertWhereString( (String)map.get("hachu_pattern_3_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_3_kb_like") != null && ((String)map.get("hachu_pattern_3_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_3_kb like '%" + conv.convertWhereString( (String)map.get("hachu_pattern_3_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_3_kb_bef_like") != null && ((String)map.get("hachu_pattern_3_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_3_kb like '%" + conv.convertWhereString( (String)map.get("hachu_pattern_3_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_3_kb_aft_like") != null && ((String)map.get("hachu_pattern_3_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_3_kb like '" + conv.convertWhereString( (String)map.get("hachu_pattern_3_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_3_kb_in") != null && ((String)map.get("hachu_pattern_3_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_3_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_pattern_3_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_3_kb_not_in") != null && ((String)map.get("hachu_pattern_3_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_3_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_pattern_3_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sime_time_3_qt に対するWHERE区
		if( map.get("sime_time_3_qt_bef") != null && ((String)map.get("sime_time_3_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_3_qt >= '" + conv.convertWhereString( (String)map.get("sime_time_3_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_3_qt_aft") != null && ((String)map.get("sime_time_3_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_3_qt <= '" + conv.convertWhereString( (String)map.get("sime_time_3_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_3_qt") != null && ((String)map.get("sime_time_3_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_3_qt = '" + conv.convertWhereString( (String)map.get("sime_time_3_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_3_qt_like") != null && ((String)map.get("sime_time_3_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_3_qt like '%" + conv.convertWhereString( (String)map.get("sime_time_3_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sime_time_3_qt_bef_like") != null && ((String)map.get("sime_time_3_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_3_qt like '%" + conv.convertWhereString( (String)map.get("sime_time_3_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sime_time_3_qt_aft_like") != null && ((String)map.get("sime_time_3_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_3_qt like '" + conv.convertWhereString( (String)map.get("sime_time_3_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sime_time_3_qt_in") != null && ((String)map.get("sime_time_3_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_3_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sime_time_3_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sime_time_3_qt_not_in") != null && ((String)map.get("sime_time_3_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sime_time_3_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sime_time_3_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// c_nohin_rtime_3_kb に対するWHERE区
		if( map.get("c_nohin_rtime_3_kb_bef") != null && ((String)map.get("c_nohin_rtime_3_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_3_kb >= '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_3_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_3_kb_aft") != null && ((String)map.get("c_nohin_rtime_3_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_3_kb <= '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_3_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_3_kb") != null && ((String)map.get("c_nohin_rtime_3_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_3_kb = '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_3_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_3_kb_like") != null && ((String)map.get("c_nohin_rtime_3_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_3_kb like '%" + conv.convertWhereString( (String)map.get("c_nohin_rtime_3_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_3_kb_bef_like") != null && ((String)map.get("c_nohin_rtime_3_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_3_kb like '%" + conv.convertWhereString( (String)map.get("c_nohin_rtime_3_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_3_kb_aft_like") != null && ((String)map.get("c_nohin_rtime_3_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_3_kb like '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_3_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_3_kb_in") != null && ((String)map.get("c_nohin_rtime_3_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_3_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("c_nohin_rtime_3_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_3_kb_not_in") != null && ((String)map.get("c_nohin_rtime_3_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_3_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("c_nohin_rtime_3_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_nohin_rtime_3_kb に対するWHERE区
		if( map.get("ten_nohin_rtime_3_kb_bef") != null && ((String)map.get("ten_nohin_rtime_3_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_3_kb >= '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_3_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_3_kb_aft") != null && ((String)map.get("ten_nohin_rtime_3_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_3_kb <= '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_3_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_3_kb") != null && ((String)map.get("ten_nohin_rtime_3_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_3_kb = '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_3_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_3_kb_like") != null && ((String)map.get("ten_nohin_rtime_3_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_3_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_3_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_3_kb_bef_like") != null && ((String)map.get("ten_nohin_rtime_3_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_3_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_3_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_3_kb_aft_like") != null && ((String)map.get("ten_nohin_rtime_3_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_3_kb like '" + conv.convertWhereString( (String)map.get("ten_nohin_rtime_3_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_3_kb_in") != null && ((String)map.get("ten_nohin_rtime_3_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_3_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_rtime_3_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_rtime_3_kb_not_in") != null && ((String)map.get("ten_nohin_rtime_3_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_rtime_3_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_rtime_3_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_nohin_time_3_kb に対するWHERE区
		if( map.get("ten_nohin_time_3_kb_bef") != null && ((String)map.get("ten_nohin_time_3_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_3_kb >= '" + conv.convertWhereString( (String)map.get("ten_nohin_time_3_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_3_kb_aft") != null && ((String)map.get("ten_nohin_time_3_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_3_kb <= '" + conv.convertWhereString( (String)map.get("ten_nohin_time_3_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_3_kb") != null && ((String)map.get("ten_nohin_time_3_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_3_kb = '" + conv.convertWhereString( (String)map.get("ten_nohin_time_3_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_3_kb_like") != null && ((String)map.get("ten_nohin_time_3_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_3_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_time_3_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_3_kb_bef_like") != null && ((String)map.get("ten_nohin_time_3_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_3_kb like '%" + conv.convertWhereString( (String)map.get("ten_nohin_time_3_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_3_kb_aft_like") != null && ((String)map.get("ten_nohin_time_3_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_3_kb like '" + conv.convertWhereString( (String)map.get("ten_nohin_time_3_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_3_kb_in") != null && ((String)map.get("ten_nohin_time_3_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_3_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_time_3_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_nohin_time_3_kb_not_in") != null && ((String)map.get("ten_nohin_time_3_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_nohin_time_3_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_nohin_time_3_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// c_nohin_rtime_kb に対するWHERE区
		if( map.get("c_nohin_rtime_kb_bef") != null && ((String)map.get("c_nohin_rtime_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_kb >= '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_kb_aft") != null && ((String)map.get("c_nohin_rtime_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_kb <= '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_kb") != null && ((String)map.get("c_nohin_rtime_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_kb = '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_kb_like") != null && ((String)map.get("c_nohin_rtime_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_kb like '%" + conv.convertWhereString( (String)map.get("c_nohin_rtime_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_kb_bef_like") != null && ((String)map.get("c_nohin_rtime_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_kb like '%" + conv.convertWhereString( (String)map.get("c_nohin_rtime_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_kb_aft_like") != null && ((String)map.get("c_nohin_rtime_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_kb like '" + conv.convertWhereString( (String)map.get("c_nohin_rtime_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_kb_in") != null && ((String)map.get("c_nohin_rtime_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("c_nohin_rtime_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("c_nohin_rtime_kb_not_in") != null && ((String)map.get("c_nohin_rtime_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("c_nohin_rtime_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("c_nohin_rtime_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_kb に対するWHERE区
		if( map.get("syohin_kb_bef") != null && ((String)map.get("syohin_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kb >= '" + conv.convertWhereString( (String)map.get("syohin_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kb_aft") != null && ((String)map.get("syohin_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kb <= '" + conv.convertWhereString( (String)map.get("syohin_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kb") != null && ((String)map.get("syohin_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kb = '" + conv.convertWhereString( (String)map.get("syohin_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kb_like") != null && ((String)map.get("syohin_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kb like '%" + conv.convertWhereString( (String)map.get("syohin_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_kb_bef_like") != null && ((String)map.get("syohin_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kb like '%" + conv.convertWhereString( (String)map.get("syohin_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kb_aft_like") != null && ((String)map.get("syohin_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kb like '" + conv.convertWhereString( (String)map.get("syohin_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_kb_in") != null && ((String)map.get("syohin_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_kb_not_in") != null && ((String)map.get("syohin_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// buturyu_kb に対するWHERE区
		if( map.get("buturyu_kb_bef") != null && ((String)map.get("buturyu_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb >= '" + conv.convertWhereString( (String)map.get("buturyu_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_aft") != null && ((String)map.get("buturyu_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb <= '" + conv.convertWhereString( (String)map.get("buturyu_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb") != null && ((String)map.get("buturyu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb = '" + conv.convertWhereString( (String)map.get("buturyu_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_like") != null && ((String)map.get("buturyu_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb like '%" + conv.convertWhereString( (String)map.get("buturyu_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_bef_like") != null && ((String)map.get("buturyu_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb like '%" + conv.convertWhereString( (String)map.get("buturyu_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_aft_like") != null && ((String)map.get("buturyu_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb like '" + conv.convertWhereString( (String)map.get("buturyu_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_in") != null && ((String)map.get("buturyu_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("buturyu_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_not_in") != null && ((String)map.get("buturyu_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("buturyu_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ten_zaiko_kb に対するWHERE区
		if( map.get("ten_zaiko_kb_bef") != null && ((String)map.get("ten_zaiko_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_zaiko_kb >= '" + conv.convertWhereString( (String)map.get("ten_zaiko_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_zaiko_kb_aft") != null && ((String)map.get("ten_zaiko_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_zaiko_kb <= '" + conv.convertWhereString( (String)map.get("ten_zaiko_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_zaiko_kb") != null && ((String)map.get("ten_zaiko_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_zaiko_kb = '" + conv.convertWhereString( (String)map.get("ten_zaiko_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_zaiko_kb_like") != null && ((String)map.get("ten_zaiko_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_zaiko_kb like '%" + conv.convertWhereString( (String)map.get("ten_zaiko_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_zaiko_kb_bef_like") != null && ((String)map.get("ten_zaiko_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_zaiko_kb like '%" + conv.convertWhereString( (String)map.get("ten_zaiko_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_zaiko_kb_aft_like") != null && ((String)map.get("ten_zaiko_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_zaiko_kb like '" + conv.convertWhereString( (String)map.get("ten_zaiko_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_zaiko_kb_in") != null && ((String)map.get("ten_zaiko_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_zaiko_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_zaiko_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_zaiko_kb_not_in") != null && ((String)map.get("ten_zaiko_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_zaiko_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_zaiko_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_ts に対するWHERE区
		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_user_id に対するWHERE区
		if( map.get("insert_user_id_bef") != null && ((String)map.get("insert_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id >= '" + conv.convertWhereString( (String)map.get("insert_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft") != null && ((String)map.get("insert_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id <= '" + conv.convertWhereString( (String)map.get("insert_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_like") != null && ((String)map.get("insert_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_bef_like") != null && ((String)map.get("insert_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft_like") != null && ((String)map.get("insert_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '" + conv.convertWhereString( (String)map.get("insert_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_in") != null && ((String)map.get("insert_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_not_in") != null && ((String)map.get("insert_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_ts に対するWHERE区
		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_user_id に対するWHERE区
		if( map.get("update_user_id_bef") != null && ((String)map.get("update_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// delete_fg に対するWHERE区
		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("hanku_cd");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("yuko_dt");
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
		mst531001_TensyohinReigaiBean bean = (mst531001_TensyohinReigaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_TENSYOHIN_REIGAI (");
		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" hanku_cd");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_dt");
		}
		if( bean.getTenHachuStDt() != null && bean.getTenHachuStDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_hachu_st_dt");
		}
		if( bean.getTenHachuEdDt() != null && bean.getTenHachuEdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_hachu_ed_dt");
		}
		if( bean.getGentankaVl() != null && bean.getGentankaVl().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gentanka_vl");
		}
		if( bean.getBaitankaVl() != null && bean.getBaitankaVl().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" baitanka_vl");
		}
		if( bean.getMaxHachutaniQt() != null && bean.getMaxHachutaniQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" max_hachutani_qt");
		}
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" eos_kb");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siiresaki_cd");
		}
		if( bean.getTenbetuHaisoCd() != null && bean.getTenbetuHaisoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenbetu_haiso_cd");
		}
		if( bean.getBin1Kb() != null && bean.getBin1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bin_1_kb");
		}
		if( bean.getHachuPattern1Kb() != null && bean.getHachuPattern1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_pattern_1_kb");
		}
		if( bean.getSimeTime1Qt() != null && bean.getSimeTime1Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sime_time_1_qt");
		}
		if( bean.getCNohinRtime1Kb() != null && bean.getCNohinRtime1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" c_nohin_rtime_1_kb");
		}
		if( bean.getTenNohinRtime1Kb() != null && bean.getTenNohinRtime1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_rtime_1_kb");
		}
		if( bean.getTenNohinTime1Kb() != null && bean.getTenNohinTime1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_time_1_kb");
		}
		if( bean.getBin2Kb() != null && bean.getBin2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bin_2_kb");
		}
		if( bean.getHachuPattern2Kb() != null && bean.getHachuPattern2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_pattern_2_kb");
		}
		if( bean.getSimeTime2Qt() != null && bean.getSimeTime2Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sime_time_2_qt");
		}
		if( bean.getCNohinRtime2Kb() != null && bean.getCNohinRtime2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" c_nohin_rtime_2_kb");
		}
		if( bean.getTenNohinRtime2Kb() != null && bean.getTenNohinRtime2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_rtime_2_kb");
		}
		if( bean.getTenNohinTime2Kb() != null && bean.getTenNohinTime2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_time_2_kb");
		}
		if( bean.getBin3Kb() != null && bean.getBin3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bin_3_kb");
		}
		if( bean.getHachuPattern3Kb() != null && bean.getHachuPattern3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_pattern_3_kb");
		}
		if( bean.getSimeTime3Qt() != null && bean.getSimeTime3Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sime_time_3_qt");
		}
		if( bean.getCNohinRtime3Kb() != null && bean.getCNohinRtime3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" c_nohin_rtime_3_kb");
		}
		if( bean.getTenNohinRtime3Kb() != null && bean.getTenNohinRtime3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_rtime_3_kb");
		}
		if( bean.getTenNohinTime3Kb() != null && bean.getTenNohinTime3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_time_3_kb");
		}
		if( bean.getCNohinRtimeKb() != null && bean.getCNohinRtimeKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" c_nohin_rtime_kb");
		}
		if( bean.getSyohinKb() != null && bean.getSyohinKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_kb");
		}
		if( bean.getButuryuKb() != null && bean.getButuryuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" buturyu_kb");
		}
		if( bean.getTenZaikoKb() != null && bean.getTenZaikoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_zaiko_kb");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHankuCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		if( bean.getTenHachuStDt() != null && bean.getTenHachuStDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenHachuStDt() ) + "'");
		}
		if( bean.getTenHachuEdDt() != null && bean.getTenHachuEdDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenHachuEdDt() ) + "'");
		}
		if( bean.getGentankaVl() != null && bean.getGentankaVl().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getGentankaVl() ) + "'");
		}
		if( bean.getBaitankaVl() != null && bean.getBaitankaVl().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBaitankaVl() ) + "'");
		}
		if( bean.getMaxHachutaniQt() != null && bean.getMaxHachutaniQt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getMaxHachutaniQt() ) + "'");
		}
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getTenbetuHaisoCd() != null && bean.getTenbetuHaisoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenbetuHaisoCd() ) + "'");
		}
		if( bean.getBin1Kb() != null && bean.getBin1Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBin1Kb() ) + "'");
		}
		if( bean.getHachuPattern1Kb() != null && bean.getHachuPattern1Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuPattern1Kb() ) + "'");
		}
		if( bean.getSimeTime1Qt() != null && bean.getSimeTime1Qt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSimeTime1Qt() ) + "'");
		}
		if( bean.getCNohinRtime1Kb() != null && bean.getCNohinRtime1Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCNohinRtime1Kb() ) + "'");
		}
		if( bean.getTenNohinRtime1Kb() != null && bean.getTenNohinRtime1Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenNohinRtime1Kb() ) + "'");
		}
		if( bean.getTenNohinTime1Kb() != null && bean.getTenNohinTime1Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenNohinTime1Kb() ) + "'");
		}
		if( bean.getBin2Kb() != null && bean.getBin2Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBin2Kb() ) + "'");
		}
		if( bean.getHachuPattern2Kb() != null && bean.getHachuPattern2Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuPattern2Kb() ) + "'");
		}
		if( bean.getSimeTime2Qt() != null && bean.getSimeTime2Qt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSimeTime2Qt() ) + "'");
		}
		if( bean.getCNohinRtime2Kb() != null && bean.getCNohinRtime2Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCNohinRtime2Kb() ) + "'");
		}
		if( bean.getTenNohinRtime2Kb() != null && bean.getTenNohinRtime2Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenNohinRtime2Kb() ) + "'");
		}
		if( bean.getTenNohinTime2Kb() != null && bean.getTenNohinTime2Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenNohinTime2Kb() ) + "'");
		}
		if( bean.getBin3Kb() != null && bean.getBin3Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBin3Kb() ) + "'");
		}
		if( bean.getHachuPattern3Kb() != null && bean.getHachuPattern3Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuPattern3Kb() ) + "'");
		}
		if( bean.getSimeTime3Qt() != null && bean.getSimeTime3Qt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSimeTime3Qt() ) + "'");
		}
		if( bean.getCNohinRtime3Kb() != null && bean.getCNohinRtime3Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCNohinRtime3Kb() ) + "'");
		}
		if( bean.getTenNohinRtime3Kb() != null && bean.getTenNohinRtime3Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenNohinRtime3Kb() ) + "'");
		}
		if( bean.getTenNohinTime3Kb() != null && bean.getTenNohinTime3Kb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenNohinTime3Kb() ) + "'");
		}
		if( bean.getCNohinRtimeKb() != null && bean.getCNohinRtimeKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCNohinRtimeKb() ) + "'");
		}
		if( bean.getSyohinKb() != null && bean.getSyohinKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinKb() ) + "'");
		}
		if( bean.getButuryuKb() != null && bean.getButuryuKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getButuryuKb() ) + "'");
		}
		if( bean.getTenZaikoKb() != null && bean.getTenZaikoKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenZaikoKb() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}
		sb.append(")");
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
		mst531001_TensyohinReigaiBean bean = (mst531001_TensyohinReigaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_TENSYOHIN_REIGAI set ");
		if( bean.getTenHachuStDt() != null && bean.getTenHachuStDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" ten_hachu_st_dt = ");
			sb.append("'" + conv.convertString( bean.getTenHachuStDt() ) + "'");
		}
		if( bean.getTenHachuEdDt() != null && bean.getTenHachuEdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_hachu_ed_dt = ");
			sb.append("'" + conv.convertString( bean.getTenHachuEdDt() ) + "'");
		}
		if( bean.getGentankaVl() != null && bean.getGentankaVl().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gentanka_vl = ");
			sb.append("'" + conv.convertString( bean.getGentankaVl() ) + "'");
		}
		if( bean.getBaitankaVl() != null && bean.getBaitankaVl().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" baitanka_vl = ");
			sb.append("'" + conv.convertString( bean.getBaitankaVl() ) + "'");
		}
		if( bean.getMaxHachutaniQt() != null && bean.getMaxHachutaniQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" max_hachutani_qt = ");
			sb.append("'" + conv.convertString( bean.getMaxHachutaniQt() ) + "'");
		}
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" eos_kb = ");
			sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siiresaki_cd = ");
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getTenbetuHaisoCd() != null && bean.getTenbetuHaisoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenbetu_haiso_cd = ");
			sb.append("'" + conv.convertString( bean.getTenbetuHaisoCd() ) + "'");
		}
		if( bean.getBin1Kb() != null && bean.getBin1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bin_1_kb = ");
			sb.append("'" + conv.convertString( bean.getBin1Kb() ) + "'");
		}
		if( bean.getHachuPattern1Kb() != null && bean.getHachuPattern1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_pattern_1_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuPattern1Kb() ) + "'");
		}
		if( bean.getSimeTime1Qt() != null && bean.getSimeTime1Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sime_time_1_qt = ");
			sb.append("'" + conv.convertString( bean.getSimeTime1Qt() ) + "'");
		}
		if( bean.getCNohinRtime1Kb() != null && bean.getCNohinRtime1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" c_nohin_rtime_1_kb = ");
			sb.append("'" + conv.convertString( bean.getCNohinRtime1Kb() ) + "'");
		}
		if( bean.getTenNohinRtime1Kb() != null && bean.getTenNohinRtime1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_rtime_1_kb = ");
			sb.append("'" + conv.convertString( bean.getTenNohinRtime1Kb() ) + "'");
		}
		if( bean.getTenNohinTime1Kb() != null && bean.getTenNohinTime1Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_time_1_kb = ");
			sb.append("'" + conv.convertString( bean.getTenNohinTime1Kb() ) + "'");
		}
		if( bean.getBin2Kb() != null && bean.getBin2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bin_2_kb = ");
			sb.append("'" + conv.convertString( bean.getBin2Kb() ) + "'");
		}
		if( bean.getHachuPattern2Kb() != null && bean.getHachuPattern2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_pattern_2_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuPattern2Kb() ) + "'");
		}
		if( bean.getSimeTime2Qt() != null && bean.getSimeTime2Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sime_time_2_qt = ");
			sb.append("'" + conv.convertString( bean.getSimeTime2Qt() ) + "'");
		}
		if( bean.getCNohinRtime2Kb() != null && bean.getCNohinRtime2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" c_nohin_rtime_2_kb = ");
			sb.append("'" + conv.convertString( bean.getCNohinRtime2Kb() ) + "'");
		}
		if( bean.getTenNohinRtime2Kb() != null && bean.getTenNohinRtime2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_rtime_2_kb = ");
			sb.append("'" + conv.convertString( bean.getTenNohinRtime2Kb() ) + "'");
		}
		if( bean.getTenNohinTime2Kb() != null && bean.getTenNohinTime2Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_time_2_kb = ");
			sb.append("'" + conv.convertString( bean.getTenNohinTime2Kb() ) + "'");
		}
		if( bean.getBin3Kb() != null && bean.getBin3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bin_3_kb = ");
			sb.append("'" + conv.convertString( bean.getBin3Kb() ) + "'");
		}
		if( bean.getHachuPattern3Kb() != null && bean.getHachuPattern3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_pattern_3_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuPattern3Kb() ) + "'");
		}
		if( bean.getSimeTime3Qt() != null && bean.getSimeTime3Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sime_time_3_qt = ");
			sb.append("'" + conv.convertString( bean.getSimeTime3Qt() ) + "'");
		}
		if( bean.getCNohinRtime3Kb() != null && bean.getCNohinRtime3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" c_nohin_rtime_3_kb = ");
			sb.append("'" + conv.convertString( bean.getCNohinRtime3Kb() ) + "'");
		}
		if( bean.getTenNohinRtime3Kb() != null && bean.getTenNohinRtime3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_rtime_3_kb = ");
			sb.append("'" + conv.convertString( bean.getTenNohinRtime3Kb() ) + "'");
		}
		if( bean.getTenNohinTime3Kb() != null && bean.getTenNohinTime3Kb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_nohin_time_3_kb = ");
			sb.append("'" + conv.convertString( bean.getTenNohinTime3Kb() ) + "'");
		}
		if( bean.getCNohinRtimeKb() != null && bean.getCNohinRtimeKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" c_nohin_rtime_kb = ");
			sb.append("'" + conv.convertString( bean.getCNohinRtimeKb() ) + "'");
		}
		if( bean.getSyohinKb() != null && bean.getSyohinKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_kb = ");
			sb.append("'" + conv.convertString( bean.getSyohinKb() ) + "'");
		}
		if( bean.getButuryuKb() != null && bean.getButuryuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" buturyu_kb = ");
			sb.append("'" + conv.convertString( bean.getButuryuKb() ) + "'");
		}
		if( bean.getTenZaikoKb() != null && bean.getTenZaikoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_zaiko_kb = ");
			sb.append("'" + conv.convertString( bean.getTenZaikoKb() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append(" to_char(SYSDATE,'YYYYMMDDHH24MISS')");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" hanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		}
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
		mst531001_TensyohinReigaiBean bean = (mst531001_TensyohinReigaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_TENSYOHIN_REIGAI where ");
		sb.append(" hanku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
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
