package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mdware.common.util.StringUtility;

import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;

/**
 * <p>タイトル:衣料グルーピングマスタ用DM </p>
 * <p>説明: 衣料グルーピングマスタをSELECT/INSERT/UPDATEするためのSQLを書いたDM</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.rbsite
 * @version 1.0 (Create time: 2006/10/3 10:23:5) K.Tanigawa 変更依頼票№0003対応
 */
public class mst970201_RIryoGroupDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public mst970201_RIryoGroupDM()
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
		mst970201_RIryoGroupBean bean = new mst970201_RIryoGroupBean();
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setGroupnoCd( rest.getString("groupno_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setRankNb( rest.getLong("rank_nb") );
		bean.setAnbunRt( rest.getLong("anbun_rt") );
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("bumon_cd ");
		sb.append(", ");
		sb.append("hinsyu_cd ");
		sb.append(", ");
		sb.append("groupno_cd ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("rank_nb ");
		sb.append(", ");
		sb.append("anbun_rt ");
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
		sb.append("from R_IRYOGROUP ");

		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bumon_cd = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("hinsyu_cd"),4,"0",true) ) + "'");
			whereStr = andStr;
		}
		if( map.get("groupno_cd") != null && ((String)map.get("groupno_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd = '" + StringUtility.charFormat( (String)map.get("groupno_cd"),2,"0",true) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rank_nb") != null && ((String)map.get("rank_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("rank_nb = " + (String)map.get("rank_nb"));
			whereStr = andStr;
		}
		if( map.get("anbun_rt") != null && ((String)map.get("anbun_rt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("anbun_rt = " + (String)map.get("anbun_rt"));
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		
		sb.append(" order by ");
		sb.append("bumon_cd");
		sb.append(",");
		sb.append("hinsyu_cd");
		sb.append(",");
		sb.append("groupno_cd");
		sb.append(",");
//		sb.append("tenpo_cd");
		sb.append(" rank_nb ");
		
		return sb.toString();


		// bumon_cd に対するWHERE区
//		if( map.get("bumon_cd_bef") != null && ((String)map.get("bumon_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("bumon_cd >= '" + conv.convertWhereString( (String)map.get("bumon_cd_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_aft") != null && ((String)map.get("bumon_cd_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("bumon_cd <= '" + conv.convertWhereString( (String)map.get("bumon_cd_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_like") != null && ((String)map.get("bumon_cd_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("bumon_cd like '%" + conv.convertWhereString( (String)map.get("bumon_cd_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_bef_like") != null && ((String)map.get("bumon_cd_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("bumon_cd like '%" + conv.convertWhereString( (String)map.get("bumon_cd_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_aft_like") != null && ((String)map.get("bumon_cd_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("bumon_cd like '" + conv.convertWhereString( (String)map.get("bumon_cd_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_in") != null && ((String)map.get("bumon_cd_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("bumon_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bumon_cd_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_not_in") != null && ((String)map.get("bumon_cd_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("bumon_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bumon_cd_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// hinsyu_cd に対するWHERE区
//		if( map.get("hinsyu_cd_bef") != null && ((String)map.get("hinsyu_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hinsyu_cd >= '" + conv.convertWhereString( (String)map.get("hinsyu_cd_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hinsyu_cd_aft") != null && ((String)map.get("hinsyu_cd_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hinsyu_cd <= '" + conv.convertWhereString( (String)map.get("hinsyu_cd_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hinsyu_cd_like") != null && ((String)map.get("hinsyu_cd_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hinsyu_cd like '%" + conv.convertWhereString( (String)map.get("hinsyu_cd_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hinsyu_cd_bef_like") != null && ((String)map.get("hinsyu_cd_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hinsyu_cd like '%" + conv.convertWhereString( (String)map.get("hinsyu_cd_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hinsyu_cd_aft_like") != null && ((String)map.get("hinsyu_cd_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hinsyu_cd like '" + conv.convertWhereString( (String)map.get("hinsyu_cd_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hinsyu_cd_in") != null && ((String)map.get("hinsyu_cd_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hinsyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinsyu_cd_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("hinsyu_cd_not_in") != null && ((String)map.get("hinsyu_cd_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hinsyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinsyu_cd_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// groupno_cd に対するWHERE区
//		if( map.get("groupno_cd_bef") != null && ((String)map.get("groupno_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("groupno_cd >= '" + conv.convertWhereString( (String)map.get("groupno_cd_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("groupno_cd_aft") != null && ((String)map.get("groupno_cd_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("groupno_cd <= '" + conv.convertWhereString( (String)map.get("groupno_cd_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("groupno_cd_like") != null && ((String)map.get("groupno_cd_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("groupno_cd like '%" + conv.convertWhereString( (String)map.get("groupno_cd_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("groupno_cd_bef_like") != null && ((String)map.get("groupno_cd_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("groupno_cd like '%" + conv.convertWhereString( (String)map.get("groupno_cd_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("groupno_cd_aft_like") != null && ((String)map.get("groupno_cd_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("groupno_cd like '" + conv.convertWhereString( (String)map.get("groupno_cd_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("groupno_cd_in") != null && ((String)map.get("groupno_cd_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("groupno_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("groupno_cd_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("groupno_cd_not_in") != null && ((String)map.get("groupno_cd_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("groupno_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("groupno_cd_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// tenpo_cd に対するWHERE区
//		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenpo_cd >= '" + conv.convertWhereString( (String)map.get("tenpo_cd_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenpo_cd_aft") != null && ((String)map.get("tenpo_cd_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenpo_cd <= '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenpo_cd_like") != null && ((String)map.get("tenpo_cd_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("tenpo_cd_bef_like") != null && ((String)map.get("tenpo_cd_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenpo_cd_aft_like") != null && ((String)map.get("tenpo_cd_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenpo_cd like '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("tenpo_cd_in") != null && ((String)map.get("tenpo_cd_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenpo_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("tenpo_cd_not_in") != null && ((String)map.get("tenpo_cd_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenpo_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// rank_nb に対するWHERE区
//		if( map.get("rank_nb_bef") != null && ((String)map.get("rank_nb_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("rank_nb >= " + (String)map.get("rank_nb_bef") );
//			whereStr = andStr;
//		}
//		if( map.get("rank_nb_aft") != null && ((String)map.get("rank_nb_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("rank_nb <= " + (String)map.get("rank_nb_aft") );
//			whereStr = andStr;
//		}
//		if( map.get("rank_nb_in") != null && ((String)map.get("rank_nb_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("rank_nb in ( " + conv.convertWhereString( (String)map.get("rank_nb_in") ) + " )");
//			whereStr = andStr;
//		}
//		if( map.get("rank_nb_not_in") != null && ((String)map.get("rank_nb_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("rank_nb not in ( " + conv.convertWhereString( (String)map.get("rank_nb_not_in") ) + " )");
//			whereStr = andStr;
//		}
//
//
//		// anbun_rt に対するWHERE区
//		if( map.get("anbun_rt_bef") != null && ((String)map.get("anbun_rt_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("anbun_rt >= " + (String)map.get("anbun_rt_bef") );
//			whereStr = andStr;
//		}
//		if( map.get("anbun_rt_aft") != null && ((String)map.get("anbun_rt_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("anbun_rt <= " + (String)map.get("anbun_rt_aft") );
//			whereStr = andStr;
//		}
//		if( map.get("anbun_rt_in") != null && ((String)map.get("anbun_rt_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("anbun_rt in ( " + conv.convertWhereString( (String)map.get("anbun_rt_in") ) + " )");
//			whereStr = andStr;
//		}
//		if( map.get("anbun_rt_not_in") != null && ((String)map.get("anbun_rt_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("anbun_rt not in ( " + conv.convertWhereString( (String)map.get("anbun_rt_not_in") ) + " )");
//			whereStr = andStr;
//		}
//
//
//		// insert_ts に対するWHERE区
//		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// insert_user_id に対するWHERE区
//		if( map.get("insert_user_id_bef") != null && ((String)map.get("insert_user_id_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_user_id >= '" + conv.convertWhereString( (String)map.get("insert_user_id_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_id_aft") != null && ((String)map.get("insert_user_id_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_user_id <= '" + conv.convertWhereString( (String)map.get("insert_user_id_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_id_like") != null && ((String)map.get("insert_user_id_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_id_bef_like") != null && ((String)map.get("insert_user_id_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_id_aft_like") != null && ((String)map.get("insert_user_id_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_user_id like '" + conv.convertWhereString( (String)map.get("insert_user_id_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_id_in") != null && ((String)map.get("insert_user_id_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_id_not_in") != null && ((String)map.get("insert_user_id_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// update_ts に対するWHERE区
//		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// update_user_id に対するWHERE区
//		if( map.get("update_user_id_bef") != null && ((String)map.get("update_user_id_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// delete_fg に対するWHERE区
//		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst970201_RIryoGroupBean bean = (mst970201_RIryoGroupBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_IRYOGROUP (");
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" bumon_cd");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinsyu_cd");
		}
		if( bean.getGroupnoCd() != null && bean.getGroupnoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" groupno_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( befKanma ) sb.append(",");
		sb.append(" rank_nb");
		sb.append(",");
		sb.append(" anbun_rt");
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append(" insert_ts");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append(" insert_user_id");
//		}
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append(" update_ts");
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append(" update_user_id");
//		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( StringUtility.charFormat( bean.getBumonCd(),4,"0",true) )  + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( StringUtility.charFormat( bean.getHinsyuCd(),4,"0",true) ) + "'");
		}
		if( bean.getGroupnoCd() != null && bean.getGroupnoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( StringUtility.charFormat( bean.getGroupnoCd(),2,"0",true) ) + "'");
			
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getRankNbString());
		sb.append(",");
		sb.append( bean.getAnbunRtString());
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		}
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");	//登録時は、INSERT_TSとUPDATE_TSに同じ値を挿入
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst970201_RIryoGroupBean bean = (mst970201_RIryoGroupBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_IRYOGROUP set ");
		befKanma = true;
		sb.append(" rank_nb = ");
		sb.append( bean.getRankNbString());
		sb.append(",");
		sb.append(" anbun_rt = ");
		sb.append( bean.getAnbunRtString());
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append(" insert_ts = ");
//			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append(" insert_user_id = ");
//			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		}
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTsForUpdate() ) + "'");
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
			sb.append(",");
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" bumon_cd = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat( bean.getBumonCd(),4,"0",true) ) + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" hinsyu_cd = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat( bean.getHinsyuCd(),4,"0",true) ) + "'");
		}
		if( bean.getGroupnoCd() != null && bean.getGroupnoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" groupno_cd = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat( bean.getGroupnoCd(),2,"0",true) ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst970201_RIryoGroupBean bean = (mst970201_RIryoGroupBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_IRYOGROUP where ");
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( StringUtility.charFormat( bean.getBumonCd(),4,"0",true) ) + "'");
		sb.append(" AND");
		sb.append(" hinsyu_cd = ");
		sb.append("'" + conv.convertWhereString( StringUtility.charFormat( bean.getHinsyuCd(),4,"0",true) ) + "'");
		sb.append(" AND");
		sb.append(" groupno_cd = ");
		sb.append("'" + conv.convertWhereString( StringUtility.charFormat( bean.getGroupnoCd(),2,"0",true) ) + "'");
//		sb.append(" AND");
//		sb.append(" tenpo_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
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
