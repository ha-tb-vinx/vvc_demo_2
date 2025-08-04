package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.bean.MSTB907010_DtAlarmBeanForSyohinMasterHenkoList;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/6/6 17:21:51)
 */
public class MSTB907010_DtAlarmDMForSyohinMasterHenkoList extends mdware.portal.bean.DtAlarmDM
{
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
		MSTB907010_DtAlarmBeanForSyohinMasterHenkoList bean = new MSTB907010_DtAlarmBeanForSyohinMasterHenkoList();
		bean.setAlarmNb( rest.getLong("alarm_nb") );
		bean.setAlarmId( rest.getString("alarm_id") );
		bean.setAlarmTypeNa( rest.getString("alarm_type_na") );
		bean.setContentTx( rest.getString("content_tx") );
		bean.setYukoSyuryoDt( rest.getString("yuko_syuryo_dt") );
		bean.setAlarmKb( rest.getString("alarm_kb") );
		bean.setDestinationUserId( rest.getString("destination_user_id") );
		bean.setDestinationBumonCd( rest.getString("destination_bumon_cd") );
		bean.setUrlTx( rest.getString("url_tx") );
		bean.setParameterTx( rest.getString("parameter_tx") );
		bean.setDelKeyTx( rest.getString("del_key_tx") );
		bean.setDelFg( rest.getString("del_fg") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setColorTX( rest.getString("color_tx") );
		bean.setDestinationBusyoCd( rest.getString("destination_busyo_cd") );
		bean.setDestinationTenpoCd( rest.getString("destination_tenpo_cd") );
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
		sb.append("alarm_nb ");
		sb.append(", ");
		sb.append("alarm_id ");
		sb.append(", ");
		sb.append("alarm_type_na ");
		sb.append(", ");
		sb.append("content_tx ");
		sb.append(", ");
		sb.append("yuko_syuryo_dt ");
		sb.append(", ");
		sb.append("alarm_kb ");
		sb.append(", ");
		sb.append("destination_user_id ");
		sb.append(", ");
		sb.append("destination_bumon_cd ");
		sb.append(", ");
		sb.append("url_tx ");
		sb.append(", ");
		sb.append("parameter_tx ");
		sb.append(", ");
		sb.append("del_key_tx ");
		sb.append(", ");
		sb.append("del_fg ");
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
		sb.append(", ");
		sb.append("destination_busyo_cd ");
		sb.append(", ");
		sb.append("destination_tenpo_cd ");
		sb.append("from dt_alarm ");


		// alarm_nb に対するWHERE区
		if( map.get("alarm_nb_bef") != null && ((String)map.get("alarm_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_nb >= " + (String)map.get("alarm_nb_bef") );
			whereStr = andStr;
		}
		if( map.get("alarm_nb_aft") != null && ((String)map.get("alarm_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_nb <= " + (String)map.get("alarm_nb_aft") );
			whereStr = andStr;
		}
		if( map.get("alarm_nb") != null && ((String)map.get("alarm_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("alarm_nb = " + (String)map.get("alarm_nb"));
			whereStr = andStr;
		}
		if( map.get("alarm_nb_in") != null && ((String)map.get("alarm_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_nb in ( " + conv.convertWhereString( (String)map.get("alarm_nb_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("alarm_nb_not_in") != null && ((String)map.get("alarm_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_nb not in ( " + conv.convertWhereString( (String)map.get("alarm_nb_not_in") ) + " )");
			whereStr = andStr;
		}


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


		// yuko_syuryo_dt に対するWHERE区
		if( map.get("yuko_syuryo_dt_bef") != null && ((String)map.get("yuko_syuryo_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt >= '" + conv.convertWhereString( (String)map.get("yuko_syuryo_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_syuryo_dt_aft") != null && ((String)map.get("yuko_syuryo_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt <= '" + conv.convertWhereString( (String)map.get("yuko_syuryo_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_syuryo_dt") != null && ((String)map.get("yuko_syuryo_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt = '" + conv.convertWhereString( (String)map.get("yuko_syuryo_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_syuryo_dt_like") != null && ((String)map.get("yuko_syuryo_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt like '%" + conv.convertWhereString( (String)map.get("yuko_syuryo_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_syuryo_dt_bef_like") != null && ((String)map.get("yuko_syuryo_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt like '%" + conv.convertWhereString( (String)map.get("yuko_syuryo_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_syuryo_dt_aft_like") != null && ((String)map.get("yuko_syuryo_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt like '" + conv.convertWhereString( (String)map.get("yuko_syuryo_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_syuryo_dt_in") != null && ((String)map.get("yuko_syuryo_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_syuryo_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yuko_syuryo_dt_not_in") != null && ((String)map.get("yuko_syuryo_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_syuryo_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// alarm_kb に対するWHERE区
		if( map.get("alarm_kb_bef") != null && ((String)map.get("alarm_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_kb >= '" + conv.convertWhereString( (String)map.get("alarm_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_kb_aft") != null && ((String)map.get("alarm_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_kb <= '" + conv.convertWhereString( (String)map.get("alarm_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_kb") != null && ((String)map.get("alarm_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_kb = '" + conv.convertWhereString( (String)map.get("alarm_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_kb_like") != null && ((String)map.get("alarm_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_kb like '%" + conv.convertWhereString( (String)map.get("alarm_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("alarm_kb_bef_like") != null && ((String)map.get("alarm_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_kb like '%" + conv.convertWhereString( (String)map.get("alarm_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("alarm_kb_aft_like") != null && ((String)map.get("alarm_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_kb like '" + conv.convertWhereString( (String)map.get("alarm_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("alarm_kb_in") != null && ((String)map.get("alarm_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("alarm_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("alarm_kb_not_in") != null && ((String)map.get("alarm_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("alarm_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("alarm_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// destination_user_id に対するWHERE区
		if( map.get("destination_user_id_bef") != null && ((String)map.get("destination_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_user_id >= '" + conv.convertWhereString( (String)map.get("destination_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("destination_user_id_aft") != null && ((String)map.get("destination_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_user_id <= '" + conv.convertWhereString( (String)map.get("destination_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("destination_user_id") != null && ((String)map.get("destination_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_user_id = '" + conv.convertWhereString( (String)map.get("destination_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("destination_user_id_like") != null && ((String)map.get("destination_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_user_id like '%" + conv.convertWhereString( (String)map.get("destination_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("destination_user_id_bef_like") != null && ((String)map.get("destination_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_user_id like '%" + conv.convertWhereString( (String)map.get("destination_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("destination_user_id_aft_like") != null && ((String)map.get("destination_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_user_id like '" + conv.convertWhereString( (String)map.get("destination_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("destination_user_id_in") != null && ((String)map.get("destination_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("destination_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("destination_user_id_not_in") != null && ((String)map.get("destination_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("destination_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// destination_bumon_cd に対するWHERE区
		if( map.get("destination_bumon_cd_bef") != null && ((String)map.get("destination_bumon_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_bumon_cd >= '" + conv.convertWhereString( (String)map.get("destination_bumon_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("destination_bumon_cd_aft") != null && ((String)map.get("destination_bumon_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_bumon_cd <= '" + conv.convertWhereString( (String)map.get("destination_bumon_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("destination_bumon_cd") != null && ((String)map.get("destination_bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_bumon_cd = '" + conv.convertWhereString( (String)map.get("destination_bumon_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("destination_bumon_cd_like") != null && ((String)map.get("destination_bumon_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_bumon_cd like '%" + conv.convertWhereString( (String)map.get("destination_bumon_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("destination_bumon_cd_bef_like") != null && ((String)map.get("destination_bumon_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_bumon_cd like '%" + conv.convertWhereString( (String)map.get("destination_bumon_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("destination_bumon_cd_aft_like") != null && ((String)map.get("destination_bumon_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_bumon_cd like '" + conv.convertWhereString( (String)map.get("destination_bumon_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("destination_bumon_cd_in") != null && ((String)map.get("destination_bumon_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_bumon_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("destination_bumon_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("destination_bumon_cd_not_in") != null && ((String)map.get("destination_bumon_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("destination_bumon_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("destination_bumon_cd_not_in") ),",","','") + "' )");
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


		// parameter_tx に対するWHERE区
		if( map.get("parameter_tx_bef") != null && ((String)map.get("parameter_tx_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("parameter_tx >= '" + conv.convertWhereString( (String)map.get("parameter_tx_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("parameter_tx_aft") != null && ((String)map.get("parameter_tx_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("parameter_tx <= '" + conv.convertWhereString( (String)map.get("parameter_tx_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("parameter_tx") != null && ((String)map.get("parameter_tx")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("parameter_tx = '" + conv.convertWhereString( (String)map.get("parameter_tx") ) + "'");
			whereStr = andStr;
		}
		if( map.get("parameter_tx_like") != null && ((String)map.get("parameter_tx_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("parameter_tx like '%" + conv.convertWhereString( (String)map.get("parameter_tx_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("parameter_tx_bef_like") != null && ((String)map.get("parameter_tx_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("parameter_tx like '%" + conv.convertWhereString( (String)map.get("parameter_tx_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("parameter_tx_aft_like") != null && ((String)map.get("parameter_tx_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("parameter_tx like '" + conv.convertWhereString( (String)map.get("parameter_tx_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("parameter_tx_in") != null && ((String)map.get("parameter_tx_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("parameter_tx in ( '" + replaceAll(conv.convertWhereString( (String)map.get("parameter_tx_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("parameter_tx_not_in") != null && ((String)map.get("parameter_tx_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("parameter_tx not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("parameter_tx_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// del_key_tx に対するWHERE区
		if( map.get("del_key_tx_bef") != null && ((String)map.get("del_key_tx_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_key_tx >= '" + conv.convertWhereString( (String)map.get("del_key_tx_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("del_key_tx_aft") != null && ((String)map.get("del_key_tx_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_key_tx <= '" + conv.convertWhereString( (String)map.get("del_key_tx_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("del_key_tx") != null && ((String)map.get("del_key_tx")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_key_tx = '" + conv.convertWhereString( (String)map.get("del_key_tx") ) + "'");
			whereStr = andStr;
		}
		if( map.get("del_key_tx_like") != null && ((String)map.get("del_key_tx_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_key_tx like '%" + conv.convertWhereString( (String)map.get("del_key_tx_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("del_key_tx_bef_like") != null && ((String)map.get("del_key_tx_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_key_tx like '%" + conv.convertWhereString( (String)map.get("del_key_tx_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("del_key_tx_aft_like") != null && ((String)map.get("del_key_tx_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_key_tx like '" + conv.convertWhereString( (String)map.get("del_key_tx_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("del_key_tx_in") != null && ((String)map.get("del_key_tx_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_key_tx in ( '" + replaceAll(conv.convertWhereString( (String)map.get("del_key_tx_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("del_key_tx_not_in") != null && ((String)map.get("del_key_tx_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_key_tx not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("del_key_tx_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// del_fg に対するWHERE区
		if( map.get("del_fg_bef") != null && ((String)map.get("del_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_fg >= '" + conv.convertWhereString( (String)map.get("del_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("del_fg_aft") != null && ((String)map.get("del_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_fg <= '" + conv.convertWhereString( (String)map.get("del_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("del_fg") != null && ((String)map.get("del_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_fg = '" + conv.convertWhereString( (String)map.get("del_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("del_fg_like") != null && ((String)map.get("del_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_fg like '%" + conv.convertWhereString( (String)map.get("del_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("del_fg_bef_like") != null && ((String)map.get("del_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_fg like '%" + conv.convertWhereString( (String)map.get("del_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("del_fg_aft_like") != null && ((String)map.get("del_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_fg like '" + conv.convertWhereString( (String)map.get("del_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("del_fg_in") != null && ((String)map.get("del_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("del_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("del_fg_not_in") != null && ((String)map.get("del_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("del_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("del_fg_not_in") ),",","','") + "' )");
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
		sb.append("alarm_nb");
		//System.out.println(sb.toString());
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
		MSTB907010_DtAlarmBeanForSyohinMasterHenkoList bean = (MSTB907010_DtAlarmBeanForSyohinMasterHenkoList)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("dt_alarm (");
		befKanma = true;
		sb.append(" alarm_nb");
		if( bean.getAlarmId() != null && bean.getAlarmId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" alarm_id");
		}
		if( bean.getAlarmTypeNa() != null && bean.getAlarmTypeNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" alarm_type_na");
		}
		if( bean.getContentTx() != null && bean.getContentTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" content_tx");
		}
		if( bean.getYukoSyuryoDt() != null && bean.getYukoSyuryoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" yuko_syuryo_dt");
		}
		if( bean.getAlarmKb() != null && bean.getAlarmKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" alarm_kb");
		}
		if( bean.getDestinationUserId() != null && bean.getDestinationUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" destination_user_id");
		}
		if( bean.getDestinationBumonCd() != null && bean.getDestinationBumonCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" destination_bumon_cd");
		}
		if( bean.getUrlTx() != null && bean.getUrlTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" url_tx");
		}
		if( bean.getParameterTx() != null && bean.getParameterTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" parameter_tx");
		}
		if( bean.getDelKeyTx() != null && bean.getDelKeyTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" del_key_tx");
		}
		if( bean.getDelFg() != null && bean.getDelFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" del_fg");
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
		if( bean.getColorTX() != null && bean.getColorTX().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" color_tx");
		}
		if( bean.getDestinationBusyoCdTX() != null && bean.getDestinationBusyoCdTX().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" destination_busyo_cd");
		}
		if( bean.getDestinationTenpoCdTX() != null && bean.getDestinationTenpoCdTX().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" destination_tenpo_cd");
		}

		sb.append(")Values(");


		aftKanma = true;
		sb.append( bean.getAlarmNbString());
		if( bean.getAlarmId() != null && bean.getAlarmId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getAlarmId() ) + "'");
		}
		if( bean.getAlarmTypeNa() != null && bean.getAlarmTypeNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getAlarmTypeNa() ) + "'");
		}
		if( bean.getContentTx() != null && bean.getContentTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getContentTx() ) + "'");
		}
		if( bean.getYukoSyuryoDt() != null && bean.getYukoSyuryoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getYukoSyuryoDt() ) + "'");
		}
		if( bean.getAlarmKb() != null && bean.getAlarmKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getAlarmKb() ) + "'");
		}
		if( bean.getDestinationUserId() != null && bean.getDestinationUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDestinationUserId() ) + "'");
		}
		if( bean.getDestinationBumonCd() != null && bean.getDestinationBumonCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDestinationBumonCd() ) + "'");
		}
		if( bean.getUrlTx() != null && bean.getUrlTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUrlTx() ) + "'");
		}
		if( bean.getParameterTx() != null && bean.getParameterTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getParameterTx() ) + "'");
		}
		if( bean.getDelKeyTx() != null && bean.getDelKeyTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDelKeyTx() ) + "'");
		}
		if( bean.getDelFg() != null && bean.getDelFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDelFg() ) + "'");
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
		if( bean.getColorTX() != null && bean.getColorTX().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getColorTX() ) + "'");
		}
		if( bean.getDestinationBusyoCdTX() != null && bean.getDestinationBusyoCdTX().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDestinationBusyoCdTX() ) + "'");
		}
		if( bean.getDestinationTenpoCdTX() != null && bean.getDestinationTenpoCdTX().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDestinationTenpoCdTX() ) + "'");
		}
		sb.append(")");
		//System.out.println(sb.toString());
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
		MSTB907010_DtAlarmBeanForSyohinMasterHenkoList bean = (MSTB907010_DtAlarmBeanForSyohinMasterHenkoList)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("dt_alarm set ");
		if( bean.getAlarmId() != null && bean.getAlarmId().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" alarm_id = ");
			sb.append("'" + conv.convertString( bean.getAlarmId() ) + "'");
		}
		if( bean.getAlarmTypeNa() != null && bean.getAlarmTypeNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" alarm_type_na = ");
			sb.append("'" + conv.convertString( bean.getAlarmTypeNa() ) + "'");
		}
		if( bean.getContentTx() != null && bean.getContentTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" content_tx = ");
			sb.append("'" + conv.convertString( bean.getContentTx() ) + "'");
		}
		if( bean.getYukoSyuryoDt() != null && bean.getYukoSyuryoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_syuryo_dt = ");
			sb.append("'" + conv.convertString( bean.getYukoSyuryoDt() ) + "'");
		}
		if( bean.getAlarmKb() != null && bean.getAlarmKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" alarm_kb = ");
			sb.append("'" + conv.convertString( bean.getAlarmKb() ) + "'");
		}
		if( bean.getDestinationUserId() != null && bean.getDestinationUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" destination_user_id = ");
			sb.append("'" + conv.convertString( bean.getDestinationUserId() ) + "'");
		}
		if( bean.getDestinationBumonCd() != null && bean.getDestinationBumonCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" destination_bumon_cd = ");
			sb.append("'" + conv.convertString( bean.getDestinationBumonCd() ) + "'");
		}
		if( bean.getUrlTx() != null && bean.getUrlTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" url_tx = ");
			sb.append("'" + conv.convertString( bean.getUrlTx() ) + "'");
		}
		if( bean.getParameterTx() != null && bean.getParameterTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" parameter_tx = ");
			sb.append("'" + conv.convertString( bean.getParameterTx() ) + "'");
		}
		if( bean.getDelKeyTx() != null && bean.getDelKeyTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" del_key_tx = ");
			sb.append("'" + conv.convertString( bean.getDelKeyTx() ) + "'");
		}
		if( bean.getDelFg() != null && bean.getDelFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" del_fg = ");
			sb.append("'" + conv.convertString( bean.getDelFg() ) + "'");
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
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getColorTX() != null && bean.getColorTX().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getColorTX() ) + "'");
		}


		sb.append(" WHERE");


		whereAnd = true;
		sb.append(" del_key_tx = ");
		sb.append( bean.getDelKeyTxString());
		//sb.append(" alarm_nb = ");
		//sb.append( bean.getAlarmNbString());
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
		MSTB907010_DtAlarmBeanForSyohinMasterHenkoList bean = (MSTB907010_DtAlarmBeanForSyohinMasterHenkoList)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("dt_alarm where ");
		sb.append(" alarm_nb = ");
		sb.append( bean.getAlarmNbString());
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
