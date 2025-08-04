package mdware.portal.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/6/7 11:10:9)
 */
public class MaAlarmDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public MaAlarmDM()
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
		MaAlarmBean bean = new MaAlarmBean();
		bean.setAlarmId( rest.getString("alarm_id") );
		bean.setAlarmTypeNa( rest.getString("alarm_type_na") );
		bean.setContentTx( rest.getString("content_tx") );
		bean.setUrlTx( rest.getString("url_tx") );
		bean.setPeriod( rest.getLong("period") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setColorTX( rest.getString("color_tx") );
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
		sb.append("alarm_id ");
		sb.append(", ");
		sb.append("alarm_type_na ");
		sb.append(", ");
		sb.append("content_tx ");
		sb.append(", ");
		sb.append("url_tx ");
		sb.append(", ");
		sb.append("period ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("color_tx ");
		sb.append("from ma_alarm ");


		// alarm_id に対するWHERE区
		if( map.get("alarm_id_bef") != null && ((String)map.get("alarm_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_id >= '" + conv.convertWhereString( (String)map.get("alarm_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_id_aft") != null && ((String)map.get("alarm_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_id <= '" + conv.convertWhereString( (String)map.get("alarm_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_id") != null && ((String)map.get("alarm_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_id = '" + conv.convertWhereString( (String)map.get("alarm_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_id_like") != null && ((String)map.get("alarm_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_id like '%" + conv.convertWhereString( (String)map.get("alarm_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("alarm_id_bef_like") != null && ((String)map.get("alarm_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_id like '%" + conv.convertWhereString( (String)map.get("alarm_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_id_aft_like") != null && ((String)map.get("alarm_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_id like '" + conv.convertWhereString( (String)map.get("alarm_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("alarm_id_in") != null && ((String)map.get("alarm_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("alarm_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("alarm_id_not_in") != null && ((String)map.get("alarm_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("alarm_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// alarm_type_na に対するWHERE区
		if( map.get("alarm_type_na_bef") != null && ((String)map.get("alarm_type_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_type_na >= '" + conv.convertWhereString( (String)map.get("alarm_type_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_type_na_aft") != null && ((String)map.get("alarm_type_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_type_na <= '" + conv.convertWhereString( (String)map.get("alarm_type_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_type_na") != null && ((String)map.get("alarm_type_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_type_na = '" + conv.convertWhereString( (String)map.get("alarm_type_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_type_na_like") != null && ((String)map.get("alarm_type_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_type_na like '%" + conv.convertWhereString( (String)map.get("alarm_type_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("alarm_type_na_bef_like") != null && ((String)map.get("alarm_type_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_type_na like '%" + conv.convertWhereString( (String)map.get("alarm_type_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_type_na_aft_like") != null && ((String)map.get("alarm_type_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_type_na like '" + conv.convertWhereString( (String)map.get("alarm_type_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("alarm_type_na_in") != null && ((String)map.get("alarm_type_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_type_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("alarm_type_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("alarm_type_na_not_in") != null && ((String)map.get("alarm_type_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_type_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("alarm_type_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// content_tx に対するWHERE区
		if( map.get("content_tx_bef") != null && ((String)map.get("content_tx_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("content_tx >= '" + conv.convertWhereString( (String)map.get("content_tx_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("content_tx_aft") != null && ((String)map.get("content_tx_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("content_tx <= '" + conv.convertWhereString( (String)map.get("content_tx_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("content_tx") != null && ((String)map.get("content_tx")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("content_tx = '" + conv.convertWhereString( (String)map.get("content_tx") ) + "'");
			whereStr = andStr;
		}
		if( map.get("content_tx_like") != null && ((String)map.get("content_tx_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("content_tx like '%" + conv.convertWhereString( (String)map.get("content_tx_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("content_tx_bef_like") != null && ((String)map.get("content_tx_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("content_tx like '%" + conv.convertWhereString( (String)map.get("content_tx_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("content_tx_aft_like") != null && ((String)map.get("content_tx_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("content_tx like '" + conv.convertWhereString( (String)map.get("content_tx_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("content_tx_in") != null && ((String)map.get("content_tx_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("content_tx in ( '" + replaceAll(conv.convertWhereString( (String)map.get("content_tx_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("content_tx_not_in") != null && ((String)map.get("content_tx_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("content_tx not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("content_tx_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// url_tx に対するWHERE区
		if( map.get("url_tx_bef") != null && ((String)map.get("url_tx_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("url_tx >= '" + conv.convertWhereString( (String)map.get("url_tx_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("url_tx_aft") != null && ((String)map.get("url_tx_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("url_tx <= '" + conv.convertWhereString( (String)map.get("url_tx_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("url_tx") != null && ((String)map.get("url_tx")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("url_tx = '" + conv.convertWhereString( (String)map.get("url_tx") ) + "'");
			whereStr = andStr;
		}
		if( map.get("url_tx_like") != null && ((String)map.get("url_tx_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("url_tx like '%" + conv.convertWhereString( (String)map.get("url_tx_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("url_tx_bef_like") != null && ((String)map.get("url_tx_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("url_tx like '%" + conv.convertWhereString( (String)map.get("url_tx_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("url_tx_aft_like") != null && ((String)map.get("url_tx_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("url_tx like '" + conv.convertWhereString( (String)map.get("url_tx_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("url_tx_in") != null && ((String)map.get("url_tx_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("url_tx in ( '" + replaceAll(conv.convertWhereString( (String)map.get("url_tx_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("url_tx_not_in") != null && ((String)map.get("url_tx_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("url_tx not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("url_tx_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// period に対するWHERE区
		if( map.get("period_bef") != null && ((String)map.get("period_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("period >= " + (String)map.get("period_bef") );
			whereStr = andStr;
		}
		if( map.get("period_aft") != null && ((String)map.get("period_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("period <= " + (String)map.get("period_aft") );
			whereStr = andStr;
		}
		if( map.get("period") != null && ((String)map.get("period")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("period = " + (String)map.get("period"));
			whereStr = andStr;
		}
		if( map.get("period_in") != null && ((String)map.get("period_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("period in ( " + conv.convertWhereString( (String)map.get("period_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("period_not_in") != null && ((String)map.get("period_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("period not in ( " + conv.convertWhereString( (String)map.get("period_not_in") ) + " )");
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
		sb.append(" order by ");
		sb.append("alarm_id");
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
		MaAlarmBean bean = (MaAlarmBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("ma_alarm (");
		if( bean.getAlarmId() != null && bean.getAlarmId().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" alarm_id");
		}
		if( bean.getAlarmTypeNa() != null && bean.getAlarmTypeNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" alarm_type_na");
		}
		if( bean.getContentTx() != null && bean.getContentTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" content_tx");
		}
		if( bean.getUrlTx() != null && bean.getUrlTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" url_tx");
		}
		if( befKanma ) sb.append(",");
		sb.append(" period");
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


		if( bean.getAlarmId() != null && bean.getAlarmId().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAlarmId() ) + "'");
		}
		if( bean.getAlarmTypeNa() != null && bean.getAlarmTypeNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAlarmTypeNa() ) + "'");
		}
		if( bean.getContentTx() != null && bean.getContentTx().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getContentTx() ) + "'");
		}
		if( bean.getUrlTx() != null && bean.getUrlTx().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUrlTx() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getPeriodString());
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
		MaAlarmBean bean = (MaAlarmBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("ma_alarm set ");
		if( bean.getAlarmTypeNa() != null && bean.getAlarmTypeNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" alarm_type_na = ");
			sb.append("'" + conv.convertString( bean.getAlarmTypeNa() ) + "'");
		}
		if( bean.getContentTx() != null && bean.getContentTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" content_tx = ");
			sb.append("'" + conv.convertString( bean.getContentTx() ) + "'");
		}
		if( bean.getUrlTx() != null && bean.getUrlTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" url_tx = ");
			sb.append("'" + conv.convertString( bean.getUrlTx() ) + "'");
		}
		if( befKanma ) sb.append(",");
		sb.append(" period = ");
		sb.append( bean.getPeriodString());
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


		if( bean.getAlarmId() != null && bean.getAlarmId().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" alarm_id = ");
			sb.append("'" + conv.convertWhereString( bean.getAlarmId() ) + "'");
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
		MaAlarmBean bean = (MaAlarmBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("ma_alarm where ");
		sb.append(" alarm_id = ");
		sb.append("'" + conv.convertWhereString( bean.getAlarmId() ) + "'");
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
