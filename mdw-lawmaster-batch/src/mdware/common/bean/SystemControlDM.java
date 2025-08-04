package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/6/28 10:20:15)
 */
public class SystemControlDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public SystemControlDM()
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
		SystemControlBean bean = new SystemControlBean();
		bean.setOnlineDt( rest.getString("online_dt") );
		bean.setBatchDt( rest.getString("batch_dt") );
		bean.setMstDateDt( rest.getString("mst_date_dt") );
		bean.setBatDateDt( rest.getString("bat_date_dt") );
		bean.setMstUseFg( rest.getString("mst_use_fg") );
		bean.setPreOrderBeginDt( rest.getString("pre_order_begin_dt") );
		bean.setPreOrderEndDt( rest.getString("pre_order_end_dt") );
		bean.setPreOrderSlideYobiKb( rest.getString("pre_order_slide_yobi_kb") );
		bean.setPreOrderSimeKbYobiKb( rest.getString("pre_order_sime_kb_yobi_kb") );
		bean.setMaxHachuDy( rest.getDouble("max_hachu_dy") );
		bean.setEobUseFg( rest.getString("eob_use_fg") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
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
		sb.append("online_dt ");
		sb.append(", ");
		sb.append("batch_dt ");
		sb.append(", ");
		sb.append("mst_date_dt ");
		sb.append(", ");
		sb.append("bat_date_dt ");
		sb.append(", ");
		sb.append("mst_use_fg ");
		sb.append(", ");
		sb.append("pre_order_begin_dt ");
		sb.append(", ");
		sb.append("pre_order_end_dt ");
		sb.append(", ");
		sb.append("pre_order_slide_yobi_kb ");
		sb.append(", ");
		sb.append("pre_order_sime_kb_yobi_kb ");
		sb.append(", ");
		sb.append("max_hachu_dy ");
		sb.append(", ");
		sb.append("eob_use_fg ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append("from system_control ");


		// online_dt に対するWHERE区
		if( map.get("online_dt_bef") != null && ((String)map.get("online_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("online_dt >= '" + conv.convertWhereString( (String)map.get("online_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("online_dt_aft") != null && ((String)map.get("online_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("online_dt <= '" + conv.convertWhereString( (String)map.get("online_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("online_dt") != null && ((String)map.get("online_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("online_dt = '" + conv.convertWhereString( (String)map.get("online_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("online_dt_like") != null && ((String)map.get("online_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("online_dt like '%" + conv.convertWhereString( (String)map.get("online_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("online_dt_bef_like") != null && ((String)map.get("online_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("online_dt like '%" + conv.convertWhereString( (String)map.get("online_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("online_dt_aft_like") != null && ((String)map.get("online_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("online_dt like '" + conv.convertWhereString( (String)map.get("online_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("online_dt_in") != null && ((String)map.get("online_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("online_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("online_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("online_dt_not_in") != null && ((String)map.get("online_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("online_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("online_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// batch_dt に対するWHERE区
		if( map.get("batch_dt_bef") != null && ((String)map.get("batch_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("batch_dt >= '" + conv.convertWhereString( (String)map.get("batch_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("batch_dt_aft") != null && ((String)map.get("batch_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("batch_dt <= '" + conv.convertWhereString( (String)map.get("batch_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("batch_dt") != null && ((String)map.get("batch_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("batch_dt = '" + conv.convertWhereString( (String)map.get("batch_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("batch_dt_like") != null && ((String)map.get("batch_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("batch_dt like '%" + conv.convertWhereString( (String)map.get("batch_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("batch_dt_bef_like") != null && ((String)map.get("batch_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("batch_dt like '%" + conv.convertWhereString( (String)map.get("batch_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("batch_dt_aft_like") != null && ((String)map.get("batch_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("batch_dt like '" + conv.convertWhereString( (String)map.get("batch_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("batch_dt_in") != null && ((String)map.get("batch_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("batch_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("batch_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("batch_dt_not_in") != null && ((String)map.get("batch_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("batch_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("batch_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// mst_date_dt に対するWHERE区
		if( map.get("mst_date_dt_bef") != null && ((String)map.get("mst_date_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_date_dt >= '" + conv.convertWhereString( (String)map.get("mst_date_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("mst_date_dt_aft") != null && ((String)map.get("mst_date_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_date_dt <= '" + conv.convertWhereString( (String)map.get("mst_date_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("mst_date_dt") != null && ((String)map.get("mst_date_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_date_dt = '" + conv.convertWhereString( (String)map.get("mst_date_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("mst_date_dt_like") != null && ((String)map.get("mst_date_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_date_dt like '%" + conv.convertWhereString( (String)map.get("mst_date_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("mst_date_dt_bef_like") != null && ((String)map.get("mst_date_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_date_dt like '%" + conv.convertWhereString( (String)map.get("mst_date_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("mst_date_dt_aft_like") != null && ((String)map.get("mst_date_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_date_dt like '" + conv.convertWhereString( (String)map.get("mst_date_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("mst_date_dt_in") != null && ((String)map.get("mst_date_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_date_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("mst_date_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("mst_date_dt_not_in") != null && ((String)map.get("mst_date_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_date_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("mst_date_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// bat_date_dt に対するWHERE区
		if( map.get("bat_date_dt_bef") != null && ((String)map.get("bat_date_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bat_date_dt >= '" + conv.convertWhereString( (String)map.get("bat_date_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bat_date_dt_aft") != null && ((String)map.get("bat_date_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bat_date_dt <= '" + conv.convertWhereString( (String)map.get("bat_date_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bat_date_dt") != null && ((String)map.get("bat_date_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bat_date_dt = '" + conv.convertWhereString( (String)map.get("bat_date_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bat_date_dt_like") != null && ((String)map.get("bat_date_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bat_date_dt like '%" + conv.convertWhereString( (String)map.get("bat_date_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bat_date_dt_bef_like") != null && ((String)map.get("bat_date_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bat_date_dt like '%" + conv.convertWhereString( (String)map.get("bat_date_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bat_date_dt_aft_like") != null && ((String)map.get("bat_date_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bat_date_dt like '" + conv.convertWhereString( (String)map.get("bat_date_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bat_date_dt_in") != null && ((String)map.get("bat_date_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bat_date_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bat_date_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("bat_date_dt_not_in") != null && ((String)map.get("bat_date_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bat_date_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bat_date_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// mst_use_fg に対するWHERE区
		if( map.get("mst_use_fg_bef") != null && ((String)map.get("mst_use_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_use_fg >= '" + conv.convertWhereString( (String)map.get("mst_use_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("mst_use_fg_aft") != null && ((String)map.get("mst_use_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_use_fg <= '" + conv.convertWhereString( (String)map.get("mst_use_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("mst_use_fg") != null && ((String)map.get("mst_use_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_use_fg = '" + conv.convertWhereString( (String)map.get("mst_use_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("mst_use_fg_like") != null && ((String)map.get("mst_use_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_use_fg like '%" + conv.convertWhereString( (String)map.get("mst_use_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("mst_use_fg_bef_like") != null && ((String)map.get("mst_use_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_use_fg like '%" + conv.convertWhereString( (String)map.get("mst_use_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("mst_use_fg_aft_like") != null && ((String)map.get("mst_use_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_use_fg like '" + conv.convertWhereString( (String)map.get("mst_use_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("mst_use_fg_in") != null && ((String)map.get("mst_use_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_use_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("mst_use_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("mst_use_fg_not_in") != null && ((String)map.get("mst_use_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("mst_use_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("mst_use_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pre_order_begin_dt に対するWHERE区
		if( map.get("pre_order_begin_dt_bef") != null && ((String)map.get("pre_order_begin_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_begin_dt >= '" + conv.convertWhereString( (String)map.get("pre_order_begin_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_begin_dt_aft") != null && ((String)map.get("pre_order_begin_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_begin_dt <= '" + conv.convertWhereString( (String)map.get("pre_order_begin_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_begin_dt") != null && ((String)map.get("pre_order_begin_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_begin_dt = '" + conv.convertWhereString( (String)map.get("pre_order_begin_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_begin_dt_like") != null && ((String)map.get("pre_order_begin_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_begin_dt like '%" + conv.convertWhereString( (String)map.get("pre_order_begin_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pre_order_begin_dt_bef_like") != null && ((String)map.get("pre_order_begin_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_begin_dt like '%" + conv.convertWhereString( (String)map.get("pre_order_begin_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_begin_dt_aft_like") != null && ((String)map.get("pre_order_begin_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_begin_dt like '" + conv.convertWhereString( (String)map.get("pre_order_begin_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pre_order_begin_dt_in") != null && ((String)map.get("pre_order_begin_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_begin_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pre_order_begin_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pre_order_begin_dt_not_in") != null && ((String)map.get("pre_order_begin_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_begin_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pre_order_begin_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pre_order_end_dt に対するWHERE区
		if( map.get("pre_order_end_dt_bef") != null && ((String)map.get("pre_order_end_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_end_dt >= '" + conv.convertWhereString( (String)map.get("pre_order_end_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_end_dt_aft") != null && ((String)map.get("pre_order_end_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_end_dt <= '" + conv.convertWhereString( (String)map.get("pre_order_end_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_end_dt") != null && ((String)map.get("pre_order_end_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_end_dt = '" + conv.convertWhereString( (String)map.get("pre_order_end_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_end_dt_like") != null && ((String)map.get("pre_order_end_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_end_dt like '%" + conv.convertWhereString( (String)map.get("pre_order_end_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pre_order_end_dt_bef_like") != null && ((String)map.get("pre_order_end_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_end_dt like '%" + conv.convertWhereString( (String)map.get("pre_order_end_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_end_dt_aft_like") != null && ((String)map.get("pre_order_end_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_end_dt like '" + conv.convertWhereString( (String)map.get("pre_order_end_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pre_order_end_dt_in") != null && ((String)map.get("pre_order_end_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_end_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pre_order_end_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pre_order_end_dt_not_in") != null && ((String)map.get("pre_order_end_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_end_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pre_order_end_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pre_order_slide_yobi_kb に対するWHERE区
		if( map.get("pre_order_slide_yobi_kb_bef") != null && ((String)map.get("pre_order_slide_yobi_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_slide_yobi_kb >= '" + conv.convertWhereString( (String)map.get("pre_order_slide_yobi_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_slide_yobi_kb_aft") != null && ((String)map.get("pre_order_slide_yobi_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_slide_yobi_kb <= '" + conv.convertWhereString( (String)map.get("pre_order_slide_yobi_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_slide_yobi_kb") != null && ((String)map.get("pre_order_slide_yobi_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_slide_yobi_kb = '" + conv.convertWhereString( (String)map.get("pre_order_slide_yobi_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_slide_yobi_kb_like") != null && ((String)map.get("pre_order_slide_yobi_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_slide_yobi_kb like '%" + conv.convertWhereString( (String)map.get("pre_order_slide_yobi_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pre_order_slide_yobi_kb_bef_like") != null && ((String)map.get("pre_order_slide_yobi_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_slide_yobi_kb like '%" + conv.convertWhereString( (String)map.get("pre_order_slide_yobi_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_slide_yobi_kb_aft_like") != null && ((String)map.get("pre_order_slide_yobi_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_slide_yobi_kb like '" + conv.convertWhereString( (String)map.get("pre_order_slide_yobi_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pre_order_slide_yobi_kb_in") != null && ((String)map.get("pre_order_slide_yobi_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_slide_yobi_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pre_order_slide_yobi_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pre_order_slide_yobi_kb_not_in") != null && ((String)map.get("pre_order_slide_yobi_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_slide_yobi_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pre_order_slide_yobi_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pre_order_sime_kb_yobi_kb に対するWHERE区
		if( map.get("pre_order_sime_kb_yobi_kb_bef") != null && ((String)map.get("pre_order_sime_kb_yobi_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_sime_kb_yobi_kb >= '" + conv.convertWhereString( (String)map.get("pre_order_sime_kb_yobi_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_sime_kb_yobi_kb_aft") != null && ((String)map.get("pre_order_sime_kb_yobi_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_sime_kb_yobi_kb <= '" + conv.convertWhereString( (String)map.get("pre_order_sime_kb_yobi_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_sime_kb_yobi_kb") != null && ((String)map.get("pre_order_sime_kb_yobi_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_sime_kb_yobi_kb = '" + conv.convertWhereString( (String)map.get("pre_order_sime_kb_yobi_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_sime_kb_yobi_kb_like") != null && ((String)map.get("pre_order_sime_kb_yobi_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_sime_kb_yobi_kb like '%" + conv.convertWhereString( (String)map.get("pre_order_sime_kb_yobi_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pre_order_sime_kb_yobi_kb_bef_like") != null && ((String)map.get("pre_order_sime_kb_yobi_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_sime_kb_yobi_kb like '%" + conv.convertWhereString( (String)map.get("pre_order_sime_kb_yobi_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pre_order_sime_kb_yobi_kb_aft_like") != null && ((String)map.get("pre_order_sime_kb_yobi_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_sime_kb_yobi_kb like '" + conv.convertWhereString( (String)map.get("pre_order_sime_kb_yobi_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pre_order_sime_kb_yobi_kb_in") != null && ((String)map.get("pre_order_sime_kb_yobi_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_sime_kb_yobi_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pre_order_sime_kb_yobi_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pre_order_sime_kb_yobi_kb_not_in") != null && ((String)map.get("pre_order_sime_kb_yobi_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pre_order_sime_kb_yobi_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pre_order_sime_kb_yobi_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// max_hachu_dy に対するWHERE区
		if( map.get("max_hachu_dy_bef") != null && ((String)map.get("max_hachu_dy_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachu_dy >= " + (String)map.get("max_hachu_dy_bef") );
			whereStr = andStr;
		}
		if( map.get("max_hachu_dy_aft") != null && ((String)map.get("max_hachu_dy_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachu_dy <= " + (String)map.get("max_hachu_dy_aft") );
			whereStr = andStr;
		}
		if( map.get("max_hachu_dy") != null && ((String)map.get("max_hachu_dy")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("max_hachu_dy = " + (String)map.get("max_hachu_dy"));
			whereStr = andStr;
		}
		if( map.get("max_hachu_dy_in") != null && ((String)map.get("max_hachu_dy_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachu_dy in ( " + conv.convertWhereString( (String)map.get("max_hachu_dy_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("max_hachu_dy_not_in") != null && ((String)map.get("max_hachu_dy_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_hachu_dy not in ( " + conv.convertWhereString( (String)map.get("max_hachu_dy_not_in") ) + " )");
			whereStr = andStr;
		}


		// eob_use_fg に対するWHERE区
		if( map.get("eob_use_fg_bef") != null && ((String)map.get("eob_use_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eob_use_fg >= '" + conv.convertWhereString( (String)map.get("eob_use_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eob_use_fg_aft") != null && ((String)map.get("eob_use_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eob_use_fg <= '" + conv.convertWhereString( (String)map.get("eob_use_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eob_use_fg") != null && ((String)map.get("eob_use_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eob_use_fg = '" + conv.convertWhereString( (String)map.get("eob_use_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eob_use_fg_like") != null && ((String)map.get("eob_use_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eob_use_fg like '%" + conv.convertWhereString( (String)map.get("eob_use_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eob_use_fg_bef_like") != null && ((String)map.get("eob_use_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eob_use_fg like '%" + conv.convertWhereString( (String)map.get("eob_use_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eob_use_fg_aft_like") != null && ((String)map.get("eob_use_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eob_use_fg like '" + conv.convertWhereString( (String)map.get("eob_use_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eob_use_fg_in") != null && ((String)map.get("eob_use_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eob_use_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eob_use_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("eob_use_fg_not_in") != null && ((String)map.get("eob_use_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eob_use_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eob_use_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// riyo_user_id に対するWHERE区
		if( map.get("riyo_user_id_bef") != null && ((String)map.get("riyo_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id >= '" + conv.convertWhereString( (String)map.get("riyo_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_aft") != null && ((String)map.get("riyo_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id <= '" + conv.convertWhereString( (String)map.get("riyo_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id = '" + conv.convertWhereString( (String)map.get("riyo_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_like") != null && ((String)map.get("riyo_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id like '%" + conv.convertWhereString( (String)map.get("riyo_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_bef_like") != null && ((String)map.get("riyo_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id like '%" + conv.convertWhereString( (String)map.get("riyo_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_aft_like") != null && ((String)map.get("riyo_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id like '" + conv.convertWhereString( (String)map.get("riyo_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_in") != null && ((String)map.get("riyo_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("riyo_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_not_in") != null && ((String)map.get("riyo_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("riyo_user_id_not_in") ),",","','") + "' )");
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
		//sb.append(" order by ");
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
		SystemControlBean bean = (SystemControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("system_control (");
		if( bean.getOnlineDt() != null && bean.getOnlineDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" online_dt");
		}
		if( bean.getBatchDt() != null && bean.getBatchDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" batch_dt");
		}
		if( bean.getMstDateDt() != null && bean.getMstDateDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" mst_date_dt");
		}
		if( bean.getBatDateDt() != null && bean.getBatDateDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bat_date_dt");
		}
		if( bean.getMstUseFg() != null && bean.getMstUseFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" mst_use_fg");
		}
		if( bean.getPreOrderBeginDt() != null && bean.getPreOrderBeginDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pre_order_begin_dt");
		}
		if( bean.getPreOrderEndDt() != null && bean.getPreOrderEndDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pre_order_end_dt");
		}
		if( bean.getPreOrderSlideYobiKb() != null && bean.getPreOrderSlideYobiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pre_order_slide_yobi_kb");
		}
		if( bean.getPreOrderSimeKbYobiKb() != null && bean.getPreOrderSimeKbYobiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pre_order_sime_kb_yobi_kb");
		}
		if( befKanma ) sb.append(",");
		sb.append(" max_hachu_dy");
		if( bean.getEobUseFg() != null && bean.getEobUseFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" eob_use_fg");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" riyo_user_id");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id");
		}


		sb.append(")Values(");


		if( bean.getOnlineDt() != null && bean.getOnlineDt().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getOnlineDt() ) + "'");
		}
		if( bean.getBatchDt() != null && bean.getBatchDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBatchDt() ) + "'");
		}
		if( bean.getMstDateDt() != null && bean.getMstDateDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getMstDateDt() ) + "'");
		}
		if( bean.getBatDateDt() != null && bean.getBatDateDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBatDateDt() ) + "'");
		}
		if( bean.getMstUseFg() != null && bean.getMstUseFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getMstUseFg() ) + "'");
		}
		if( bean.getPreOrderBeginDt() != null && bean.getPreOrderBeginDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPreOrderBeginDt() ) + "'");
		}
		if( bean.getPreOrderEndDt() != null && bean.getPreOrderEndDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPreOrderEndDt() ) + "'");
		}
		if( bean.getPreOrderSlideYobiKb() != null && bean.getPreOrderSlideYobiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPreOrderSlideYobiKb() ) + "'");
		}
		if( bean.getPreOrderSimeKbYobiKb() != null && bean.getPreOrderSimeKbYobiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPreOrderSimeKbYobiKb() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getMaxHachuDyString());
		if( bean.getEobUseFg() != null && bean.getEobUseFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getEobUseFg() ) + "'");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getRiyoUserId() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
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
		SystemControlBean bean = (SystemControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("system_control set ");
		if( bean.getOnlineDt() != null && bean.getOnlineDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" online_dt = ");
			sb.append("'" + conv.convertString( bean.getOnlineDt() ) + "'");
		}
		if( bean.getBatchDt() != null && bean.getBatchDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" batch_dt = ");
			sb.append("'" + conv.convertString( bean.getBatchDt() ) + "'");
		}
		if( bean.getMstDateDt() != null && bean.getMstDateDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" mst_date_dt = ");
			sb.append("'" + conv.convertString( bean.getMstDateDt() ) + "'");
		}
		if( bean.getBatDateDt() != null && bean.getBatDateDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bat_date_dt = ");
			sb.append("'" + conv.convertString( bean.getBatDateDt() ) + "'");
		}
		if( bean.getMstUseFg() != null && bean.getMstUseFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" mst_use_fg = ");
			sb.append("'" + conv.convertString( bean.getMstUseFg() ) + "'");
		}
		if( bean.getPreOrderBeginDt() != null && bean.getPreOrderBeginDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pre_order_begin_dt = ");
			sb.append("'" + conv.convertString( bean.getPreOrderBeginDt() ) + "'");
		}
		if( bean.getPreOrderEndDt() != null && bean.getPreOrderEndDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pre_order_end_dt = ");
			sb.append("'" + conv.convertString( bean.getPreOrderEndDt() ) + "'");
		}
		if( bean.getPreOrderSlideYobiKb() != null && bean.getPreOrderSlideYobiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pre_order_slide_yobi_kb = ");
			sb.append("'" + conv.convertString( bean.getPreOrderSlideYobiKb() ) + "'");
		}
		if( bean.getPreOrderSimeKbYobiKb() != null && bean.getPreOrderSimeKbYobiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pre_order_sime_kb_yobi_kb = ");
			sb.append("'" + conv.convertString( bean.getPreOrderSimeKbYobiKb() ) + "'");
		}
		if( befKanma ) sb.append(",");
		sb.append(" max_hachu_dy = ");
		sb.append( bean.getMaxHachuDyString());
		if( bean.getEobUseFg() != null && bean.getEobUseFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" eob_use_fg = ");
			sb.append("'" + conv.convertString( bean.getEobUseFg() ) + "'");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" riyo_user_id = ");
			sb.append("'" + conv.convertString( bean.getRiyoUserId() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}


		sb.append(" WHERE");


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
		SystemControlBean bean = (SystemControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("system_control where ");
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
