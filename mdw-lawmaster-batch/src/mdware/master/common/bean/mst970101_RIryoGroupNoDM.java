package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.common.util.StringUtility;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.MDWARE
 * @version 1.0 (Create time: 2006/10/2 14:59:21) K.Tanigawa
 */
public class mst970101_RIryoGroupNoDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public mst970101_RIryoGroupNoDM()
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
		mst970101_RIryoGroupNoBean bean = new mst970101_RIryoGroupNoBean();
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setGroupnoCd( rest.getString("groupno_cd") );
		bean.setNameNa( rest.getString("name_na") );
		bean.setGyotaiKb( rest.getString("gyotai_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setInsertUserNa( rest.getString("insert_user_na") );
		bean.setUpdateUserNa( rest.getString("update_user_na") );
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
		sb.append("t1.bumon_cd ");
		sb.append(", ");
		sb.append("t1.hinsyu_cd ");
		sb.append(", ");
		sb.append("t0.groupno_cd ");
		sb.append(", ");
		sb.append("t1.name_na ");
		sb.append(", ");
		sb.append("t1.gyotai_kb ");
		sb.append(", ");
		sb.append("t1.insert_ts ");
		sb.append(", ");
		sb.append("t1.insert_user_id ");
		sb.append(", ");
		sb.append("t1.update_ts ");
		sb.append(", ");
		sb.append("t1.update_user_id ");
		sb.append(", ");
		sb.append("t1.delete_fg ");
		sb.append(", ");
		sb.append("t2.RIYO_USER_NA as insert_user_na ");
		sb.append(", ");
		sb.append("t3.RIYO_USER_NA as update_user_na ");
		sb.append("from ");
		sb.append("R_GROUPNO_DEFAULT t0 left outer join r_iryogroupno t1 on t0.groupno_cd = t1.groupno_cd  ");

		whereStr = andStr;
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("t1.bumon_cd = '" + conv.convertWhereString( StringUtility.charFormat((String)map.get("bumon_cd"),4,"0",true) ) + "'");
			whereStr = andStr;
		}

		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("t1.hinsyu_cd = '" + StringUtility.charFormat((String)map.get("hinsyu_cd"),4,"0",true) + "'");
			whereStr = andStr;
		}
//		sb.append(" left outer join r_riyo_user  t2 on t2.riyo_user_id =  t1.insert_user_id ");
//		sb.append(" left outer join r_riyo_user  t3 on t3.riyo_user_id =  t1.update_user_id ");
		sb.append(" left outer join r_portal_user  t2 on t2.user_id =  TRIM(t1.insert_user_id) ");
		sb.append(" left outer join r_portal_user  t3 on t3.user_id =  TRIM(t1.update_user_id) ");

		// bumon_cd に対するWHERE区
//		if( map.get("bumon_cd_bef") != null && ((String)map.get("bumon_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.bumon_cd >= '" + conv.convertWhereString( (String)map.get("bumon_cd_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_aft") != null && ((String)map.get("bumon_cd_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.bumon_cd <= '" + conv.convertWhereString( (String)map.get("bumon_cd_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t2.bumon_cd = '" + conv.convertWhereString( (String)map.get("bumon_cd") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_like") != null && ((String)map.get("bumon_cd_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.bumon_cd like '%" + conv.convertWhereString( (String)map.get("bumon_cd_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_bef_like") != null && ((String)map.get("bumon_cd_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.bumon_cd like '%" + conv.convertWhereString( (String)map.get("bumon_cd_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_aft_like") != null && ((String)map.get("bumon_cd_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.bumon_cd like '" + conv.convertWhereString( (String)map.get("bumon_cd_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_in") != null && ((String)map.get("bumon_cd_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.bumon_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bumon_cd_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("bumon_cd_not_in") != null && ((String)map.get("bumon_cd_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.bumon_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bumon_cd_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}


		// hinsyu_cd に対するWHERE区
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
//		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.hinsyu_cd = '" + conv.convertWhereString( (String)map.get("hinsyu_cd") ) + "'");
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
//		if( map.get("groupno_cd") != null && ((String)map.get("groupno_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.groupno_cd = '" + conv.convertWhereString( (String)map.get("groupno_cd") ) + "'");
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


		// name_na に対するWHERE区
//		if( map.get("name_na_bef") != null && ((String)map.get("name_na_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("name_na >= '" + conv.convertWhereString( (String)map.get("name_na_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("name_na_aft") != null && ((String)map.get("name_na_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("name_na <= '" + conv.convertWhereString( (String)map.get("name_na_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("name_na") != null && ((String)map.get("name_na")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.name_na = '" + conv.convertWhereString( (String)map.get("name_na") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("name_na_like") != null && ((String)map.get("name_na_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("name_na like '%" + conv.convertWhereString( (String)map.get("name_na_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("name_na_bef_like") != null && ((String)map.get("name_na_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("name_na like '%" + conv.convertWhereString( (String)map.get("name_na_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("name_na_aft_like") != null && ((String)map.get("name_na_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("name_na like '" + conv.convertWhereString( (String)map.get("name_na_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("name_na_in") != null && ((String)map.get("name_na_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("name_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("name_na_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("name_na_not_in") != null && ((String)map.get("name_na_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("name_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("name_na_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}


		// gyotai_kb に対するWHERE区
//		if( map.get("gyotai_kb_bef") != null && ((String)map.get("gyotai_kb_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("gyotai_kb >= '" + conv.convertWhereString( (String)map.get("gyotai_kb_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("gyotai_kb_aft") != null && ((String)map.get("gyotai_kb_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("gyotai_kb <= '" + conv.convertWhereString( (String)map.get("gyotai_kb_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("gyotai_kb") != null && ((String)map.get("gyotai_kb")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("t1.gyotai_kb = '" + conv.convertWhereString( (String)map.get("gyotai_kb") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("gyotai_kb_like") != null && ((String)map.get("gyotai_kb_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("gyotai_kb like '%" + conv.convertWhereString( (String)map.get("gyotai_kb_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("gyotai_kb_bef_like") != null && ((String)map.get("gyotai_kb_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("gyotai_kb like '%" + conv.convertWhereString( (String)map.get("gyotai_kb_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("gyotai_kb_aft_like") != null && ((String)map.get("gyotai_kb_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("gyotai_kb like '" + conv.convertWhereString( (String)map.get("gyotai_kb_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("gyotai_kb_in") != null && ((String)map.get("gyotai_kb_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("gyotai_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gyotai_kb_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("gyotai_kb_not_in") != null && ((String)map.get("gyotai_kb_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("gyotai_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gyotai_kb_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}


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
//		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
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
//		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
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
//		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
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
//		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
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
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("t1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
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
		sb.append(" order by ");
		sb.append("t0.groupno_cd ");
//		sb.append(",");
//		sb.append("t1.bumon_cd");
//		sb.append(",");
//		sb.append("t1.hinsyu_cd");
//		sb.append(",");
//		sb.append("t1.groupno_cd");
		return sb.toString();
	}

	/**
	 * 更新対象行の更新年月日フィールドが別のユーザにより更新されていないかを確認するために、
	 * 更新対象行を取得するSELECT SQLを返す。
	 * WHERE区は、渡されたMapを元に作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateTsCheckSelectSql( Map map )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
//		sb.append("t1.bumon_cd ");
//		sb.append(", ");
//		sb.append("t1.hinsyu_cd ");
//		sb.append(", ");
//		sb.append("t1.groupno_cd ");
//		sb.append(", ");
//		sb.append("t1.name_na ");
//		sb.append(", ");
//		sb.append("t1.gyotai_kb ");
//		sb.append(", ");
//		sb.append("t1.insert_ts ");
//		sb.append(", ");
//		sb.append("t1.insert_user_id ");
//		sb.append(", ");
		sb.append("t1.update_ts ");
//		sb.append(", ");
//		sb.append("t1.update_user_id ");
//		sb.append(", ");
//		sb.append("t1.delete_fg ");
		sb.append("from ");
		sb.append("r_iryogroupno t1 ");

		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("t1.bumon_cd = '" + conv.convertWhereString( StringUtility.charFormat((String)map.get("bumon_cd"),4,"0",true) ) + "'");
			whereStr = andStr;
		}

		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("t1.hinsyu_cd = '" + StringUtility.charFormat((String)map.get("hinsyu_cd"),4,"0",true) + "'");
			whereStr = andStr;
		}

		if( map.get("groupno_cd") != null && ((String)map.get("groupno_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("t1.groupno_cd = '" + StringUtility.charFormat((String)map.get("groupno_cd"),2,"0",true) + "'");
			whereStr = andStr;
		}

		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("t1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst970101_RIryoGroupNoBean bean = (mst970101_RIryoGroupNoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_iryogroupno (");
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
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" name_na");
		}
//		if( bean.getGyotaiKb() != null && bean.getGyotaiKb().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" gyotai_kb");
//		}
		
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id");
//		}
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id");
//		}
//		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg");
//		}


		sb.append(")Values(");


		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( StringUtility.charFormat(bean.getBumonCd(),4,"0",true) ) + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( StringUtility.charFormat(bean.getHinsyuCd(),4,"0",true) ) + "'");
		}
		if( bean.getGroupnoCd() != null && bean.getGroupnoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getGroupnoCd() ) + "'");
		}
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNameNa() ) + "'");
		}
//		if( bean.getGyotaiKb() != null && bean.getGyotaiKb().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getGyotaiKb() ) + "'");
//		}
		
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		}
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");	//挿入時は、INSERT_TSとUPDATE_TSの値を同じにする(異なる値をセットする意味はナーイ)
			bean.setUpdateTs(bean.getInsertTs());								//つじつまあわせ
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//		}
//		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
//		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
//		}
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
		mst970101_RIryoGroupNoBean bean = (mst970101_RIryoGroupNoBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("update ");
		sb.append("r_iryogroupno set ");
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" name_na = ");
			sb.append("'" + conv.convertString( bean.getNameNa() ) + "'");
		}
//		if( bean.getGyotaiKb() != null && bean.getGyotaiKb().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" gyotai_kb = ");
//			sb.append("'" + conv.convertString( bean.getGyotaiKb() ) + "'");
//		}

		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_ts = REPLACE(CHAR(CURRENT DATE),'/','') || REPLACE(CHAR(CURRENT TIME), ':', '') ");
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTsForUpdate() != null && bean.getUpdateTsForUpdate().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTsForUpdate() ) + "'");
//			sb.append(" update_ts = REPLACE(CHAR(CURRENT DATE),'-','') || REPLACE(CHAR(CURRENT TIME), ':', '') ");
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


		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" bumon_cd = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat(bean.getBumonCd(),4,"0",true) ) + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" hinsyu_cd = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat(bean.getHinsyuCd(),4,"0",true) ) + "'");
		}
		if( bean.getGroupnoCd() != null && bean.getGroupnoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" groupno_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getGroupnoCd() ) + "'");
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
		mst970101_RIryoGroupNoBean bean = (mst970101_RIryoGroupNoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_iryogroupno where ");
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( StringUtility.charFormat(bean.getBumonCd(),4,"0",true) ) + "'");
		sb.append(" AND");
		sb.append(" hinsyu_cd = ");
		sb.append("'" + conv.convertWhereString( StringUtility.charFormat(bean.getHinsyuCd(),4,"0",true) ) + "'");
		sb.append(" AND");
		sb.append(" groupno_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getGroupnoCd() ) + "'");
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
