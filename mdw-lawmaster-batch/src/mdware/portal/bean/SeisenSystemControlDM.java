package mdware.portal.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/8/19 17:11:25)
 */
public class SeisenSystemControlDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public SeisenSystemControlDM()
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
		SeisenSystemControlBean bean = new SeisenSystemControlBean();
		bean.setOnlineDt( rest.getString("online_dt") );
		bean.setBatchDt( rest.getString("batch_dt") );
		bean.setHcyuStDtSoba( rest.getString("hcyu_st_dt_soba") );
		bean.setHcyuEdDtSoba( rest.getString("hcyu_ed_dt_soba") );
		bean.setHcyuStDtHi( rest.getString("hcyu_st_dt_hi") );
		bean.setHcyuEdDtHi( rest.getString("hcyu_ed_dt_hi") );
		bean.setHcyuStDtHan( rest.getString("hcyu_st_dt_han") );
		bean.setHcyuEdDtHan( rest.getString("hcyu_ed_dt_han") );
		bean.setSobaStDt( rest.getLong("soba_st_dt") );
		bean.setSyuseiMakeEndDt( rest.getString("syusei_make_end_dt") );
		bean.setDataInputLinesQt( rest.getLong("data_input_lines_qt") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
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
		sb.append("hcyu_st_dt_soba ");
		sb.append(", ");
		sb.append("hcyu_ed_dt_soba ");
		sb.append(", ");
		sb.append("hcyu_st_dt_hi ");
		sb.append(", ");
		sb.append("hcyu_ed_dt_hi ");
		sb.append(", ");
		sb.append("hcyu_st_dt_han ");
		sb.append(", ");
		sb.append("hcyu_ed_dt_han ");
		sb.append(", ");
		sb.append("soba_st_dt ");
		sb.append(", ");
		sb.append("syusei_make_end_dt ");
		sb.append(", ");
		sb.append("data_input_lines_qt ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append("from seisen_system_control ");


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


		// hcyu_st_dt_soba に対するWHERE区
		if( map.get("hcyu_st_dt_soba_bef") != null && ((String)map.get("hcyu_st_dt_soba_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_soba >= '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_soba_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_soba_aft") != null && ((String)map.get("hcyu_st_dt_soba_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_soba <= '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_soba_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_soba") != null && ((String)map.get("hcyu_st_dt_soba")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_soba = '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_soba") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_soba_like") != null && ((String)map.get("hcyu_st_dt_soba_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_soba like '%" + conv.convertWhereString( (String)map.get("hcyu_st_dt_soba_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_soba_bef_like") != null && ((String)map.get("hcyu_st_dt_soba_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_soba like '%" + conv.convertWhereString( (String)map.get("hcyu_st_dt_soba_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_soba_aft_like") != null && ((String)map.get("hcyu_st_dt_soba_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_soba like '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_soba_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_soba_in") != null && ((String)map.get("hcyu_st_dt_soba_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_soba in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_st_dt_soba_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_soba_not_in") != null && ((String)map.get("hcyu_st_dt_soba_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_soba not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_st_dt_soba_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hcyu_ed_dt_soba に対するWHERE区
		if( map.get("hcyu_ed_dt_soba_bef") != null && ((String)map.get("hcyu_ed_dt_soba_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_soba >= '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_soba_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_soba_aft") != null && ((String)map.get("hcyu_ed_dt_soba_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_soba <= '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_soba_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_soba") != null && ((String)map.get("hcyu_ed_dt_soba")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_soba = '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_soba") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_soba_like") != null && ((String)map.get("hcyu_ed_dt_soba_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_soba like '%" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_soba_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_soba_bef_like") != null && ((String)map.get("hcyu_ed_dt_soba_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_soba like '%" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_soba_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_soba_aft_like") != null && ((String)map.get("hcyu_ed_dt_soba_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_soba like '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_soba_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_soba_in") != null && ((String)map.get("hcyu_ed_dt_soba_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_soba in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_ed_dt_soba_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_soba_not_in") != null && ((String)map.get("hcyu_ed_dt_soba_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_soba not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_ed_dt_soba_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hcyu_st_dt_hi に対するWHERE区
		if( map.get("hcyu_st_dt_hi_bef") != null && ((String)map.get("hcyu_st_dt_hi_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_hi >= '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_hi_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_hi_aft") != null && ((String)map.get("hcyu_st_dt_hi_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_hi <= '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_hi_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_hi") != null && ((String)map.get("hcyu_st_dt_hi")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_hi = '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_hi") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_hi_like") != null && ((String)map.get("hcyu_st_dt_hi_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_hi like '%" + conv.convertWhereString( (String)map.get("hcyu_st_dt_hi_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_hi_bef_like") != null && ((String)map.get("hcyu_st_dt_hi_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_hi like '%" + conv.convertWhereString( (String)map.get("hcyu_st_dt_hi_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_hi_aft_like") != null && ((String)map.get("hcyu_st_dt_hi_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_hi like '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_hi_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_hi_in") != null && ((String)map.get("hcyu_st_dt_hi_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_hi in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_st_dt_hi_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_hi_not_in") != null && ((String)map.get("hcyu_st_dt_hi_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_hi not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_st_dt_hi_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hcyu_ed_dt_hi に対するWHERE区
		if( map.get("hcyu_ed_dt_hi_bef") != null && ((String)map.get("hcyu_ed_dt_hi_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_hi >= '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_hi_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_hi_aft") != null && ((String)map.get("hcyu_ed_dt_hi_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_hi <= '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_hi_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_hi") != null && ((String)map.get("hcyu_ed_dt_hi")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_hi = '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_hi") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_hi_like") != null && ((String)map.get("hcyu_ed_dt_hi_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_hi like '%" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_hi_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_hi_bef_like") != null && ((String)map.get("hcyu_ed_dt_hi_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_hi like '%" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_hi_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_hi_aft_like") != null && ((String)map.get("hcyu_ed_dt_hi_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_hi like '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_hi_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_hi_in") != null && ((String)map.get("hcyu_ed_dt_hi_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_hi in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_ed_dt_hi_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_hi_not_in") != null && ((String)map.get("hcyu_ed_dt_hi_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_hi not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_ed_dt_hi_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hcyu_st_dt_han に対するWHERE区
		if( map.get("hcyu_st_dt_han_bef") != null && ((String)map.get("hcyu_st_dt_han_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_han >= '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_han_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_han_aft") != null && ((String)map.get("hcyu_st_dt_han_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_han <= '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_han_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_han") != null && ((String)map.get("hcyu_st_dt_han")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_han = '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_han") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_han_like") != null && ((String)map.get("hcyu_st_dt_han_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_han like '%" + conv.convertWhereString( (String)map.get("hcyu_st_dt_han_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_han_bef_like") != null && ((String)map.get("hcyu_st_dt_han_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_han like '%" + conv.convertWhereString( (String)map.get("hcyu_st_dt_han_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_han_aft_like") != null && ((String)map.get("hcyu_st_dt_han_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_han like '" + conv.convertWhereString( (String)map.get("hcyu_st_dt_han_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_han_in") != null && ((String)map.get("hcyu_st_dt_han_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_han in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_st_dt_han_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hcyu_st_dt_han_not_in") != null && ((String)map.get("hcyu_st_dt_han_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_st_dt_han not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_st_dt_han_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hcyu_ed_dt_han に対するWHERE区
		if( map.get("hcyu_ed_dt_han_bef") != null && ((String)map.get("hcyu_ed_dt_han_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_han >= '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_han_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_han_aft") != null && ((String)map.get("hcyu_ed_dt_han_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_han <= '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_han_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_han") != null && ((String)map.get("hcyu_ed_dt_han")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_han = '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_han") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_han_like") != null && ((String)map.get("hcyu_ed_dt_han_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_han like '%" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_han_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_han_bef_like") != null && ((String)map.get("hcyu_ed_dt_han_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_han like '%" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_han_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_han_aft_like") != null && ((String)map.get("hcyu_ed_dt_han_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_han like '" + conv.convertWhereString( (String)map.get("hcyu_ed_dt_han_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_han_in") != null && ((String)map.get("hcyu_ed_dt_han_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_han in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_ed_dt_han_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hcyu_ed_dt_han_not_in") != null && ((String)map.get("hcyu_ed_dt_han_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hcyu_ed_dt_han not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hcyu_ed_dt_han_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// soba_st_dt に対するWHERE区
		if( map.get("soba_st_dt_bef") != null && ((String)map.get("soba_st_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("soba_st_dt >= " + (String)map.get("soba_st_dt_bef") );
			whereStr = andStr;
		}
		if( map.get("soba_st_dt_aft") != null && ((String)map.get("soba_st_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("soba_st_dt <= " + (String)map.get("soba_st_dt_aft") );
			whereStr = andStr;
		}
		if( map.get("soba_st_dt") != null && ((String)map.get("soba_st_dt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("soba_st_dt = " + (String)map.get("soba_st_dt"));
			whereStr = andStr;
		}
		if( map.get("soba_st_dt_in") != null && ((String)map.get("soba_st_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("soba_st_dt in ( " + conv.convertWhereString( (String)map.get("soba_st_dt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("soba_st_dt_not_in") != null && ((String)map.get("soba_st_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("soba_st_dt not in ( " + conv.convertWhereString( (String)map.get("soba_st_dt_not_in") ) + " )");
			whereStr = andStr;
		}


		// syusei_make_end_dt に対するWHERE区
		if( map.get("syusei_make_end_dt_bef") != null && ((String)map.get("syusei_make_end_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syusei_make_end_dt >= '" + conv.convertWhereString( (String)map.get("syusei_make_end_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syusei_make_end_dt_aft") != null && ((String)map.get("syusei_make_end_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syusei_make_end_dt <= '" + conv.convertWhereString( (String)map.get("syusei_make_end_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syusei_make_end_dt") != null && ((String)map.get("syusei_make_end_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syusei_make_end_dt = '" + conv.convertWhereString( (String)map.get("syusei_make_end_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syusei_make_end_dt_like") != null && ((String)map.get("syusei_make_end_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syusei_make_end_dt like '%" + conv.convertWhereString( (String)map.get("syusei_make_end_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syusei_make_end_dt_bef_like") != null && ((String)map.get("syusei_make_end_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syusei_make_end_dt like '%" + conv.convertWhereString( (String)map.get("syusei_make_end_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syusei_make_end_dt_aft_like") != null && ((String)map.get("syusei_make_end_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syusei_make_end_dt like '" + conv.convertWhereString( (String)map.get("syusei_make_end_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syusei_make_end_dt_in") != null && ((String)map.get("syusei_make_end_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syusei_make_end_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syusei_make_end_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syusei_make_end_dt_not_in") != null && ((String)map.get("syusei_make_end_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syusei_make_end_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syusei_make_end_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// data_input_lines_qt に対するWHERE区
		if( map.get("data_input_lines_qt_bef") != null && ((String)map.get("data_input_lines_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_input_lines_qt >= " + (String)map.get("data_input_lines_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("data_input_lines_qt_aft") != null && ((String)map.get("data_input_lines_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_input_lines_qt <= " + (String)map.get("data_input_lines_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("data_input_lines_qt") != null && ((String)map.get("data_input_lines_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("data_input_lines_qt = " + (String)map.get("data_input_lines_qt"));
			whereStr = andStr;
		}
		if( map.get("data_input_lines_qt_in") != null && ((String)map.get("data_input_lines_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_input_lines_qt in ( " + conv.convertWhereString( (String)map.get("data_input_lines_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("data_input_lines_qt_not_in") != null && ((String)map.get("data_input_lines_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_input_lines_qt not in ( " + conv.convertWhereString( (String)map.get("data_input_lines_qt_not_in") ) + " )");
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
		SeisenSystemControlBean bean = (SeisenSystemControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("seisen_system_control (");
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
		if( bean.getHcyuStDtSoba() != null && bean.getHcyuStDtSoba().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_st_dt_soba");
		}
		if( bean.getHcyuEdDtSoba() != null && bean.getHcyuEdDtSoba().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_ed_dt_soba");
		}
		if( bean.getHcyuStDtHi() != null && bean.getHcyuStDtHi().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_st_dt_hi");
		}
		if( bean.getHcyuEdDtHi() != null && bean.getHcyuEdDtHi().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_ed_dt_hi");
		}
		if( bean.getHcyuStDtHan() != null && bean.getHcyuStDtHan().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_st_dt_han");
		}
		if( bean.getHcyuEdDtHan() != null && bean.getHcyuEdDtHan().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_ed_dt_han");
		}
		if( befKanma ) sb.append(",");
		sb.append(" soba_st_dt");
		if( bean.getSyuseiMakeEndDt() != null && bean.getSyuseiMakeEndDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syusei_make_end_dt");
		}
		sb.append(",");
		sb.append(" data_input_lines_qt");
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
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts");
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
		if( bean.getHcyuStDtSoba() != null && bean.getHcyuStDtSoba().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHcyuStDtSoba() ) + "'");
		}
		if( bean.getHcyuEdDtSoba() != null && bean.getHcyuEdDtSoba().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHcyuEdDtSoba() ) + "'");
		}
		if( bean.getHcyuStDtHi() != null && bean.getHcyuStDtHi().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHcyuStDtHi() ) + "'");
		}
		if( bean.getHcyuEdDtHi() != null && bean.getHcyuEdDtHi().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHcyuEdDtHi() ) + "'");
		}
		if( bean.getHcyuStDtHan() != null && bean.getHcyuStDtHan().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHcyuStDtHan() ) + "'");
		}
		if( bean.getHcyuEdDtHan() != null && bean.getHcyuEdDtHan().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHcyuEdDtHan() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getSobaStDtString());
		if( bean.getSyuseiMakeEndDt() != null && bean.getSyuseiMakeEndDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSyuseiMakeEndDt() ) + "'");
		}
		sb.append(",");
		sb.append( bean.getDataInputLinesQtString());
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
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
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
		SeisenSystemControlBean bean = (SeisenSystemControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("seisen_system_control set ");
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
		if( bean.getHcyuStDtSoba() != null && bean.getHcyuStDtSoba().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_st_dt_soba = ");
			sb.append("'" + conv.convertString( bean.getHcyuStDtSoba() ) + "'");
		}
		if( bean.getHcyuEdDtSoba() != null && bean.getHcyuEdDtSoba().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_ed_dt_soba = ");
			sb.append("'" + conv.convertString( bean.getHcyuEdDtSoba() ) + "'");
		}
		if( bean.getHcyuStDtHi() != null && bean.getHcyuStDtHi().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_st_dt_hi = ");
			sb.append("'" + conv.convertString( bean.getHcyuStDtHi() ) + "'");
		}
		if( bean.getHcyuEdDtHi() != null && bean.getHcyuEdDtHi().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_ed_dt_hi = ");
			sb.append("'" + conv.convertString( bean.getHcyuEdDtHi() ) + "'");
		}
		if( bean.getHcyuStDtHan() != null && bean.getHcyuStDtHan().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_st_dt_han = ");
			sb.append("'" + conv.convertString( bean.getHcyuStDtHan() ) + "'");
		}
		if( bean.getHcyuEdDtHan() != null && bean.getHcyuEdDtHan().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hcyu_ed_dt_han = ");
			sb.append("'" + conv.convertString( bean.getHcyuEdDtHan() ) + "'");
		}
		if( befKanma ) sb.append(",");
		sb.append(" soba_st_dt = ");
		sb.append( bean.getSobaStDtString());
		if( bean.getSyuseiMakeEndDt() != null && bean.getSyuseiMakeEndDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syusei_make_end_dt = ");
			sb.append("'" + conv.convertString( bean.getSyuseiMakeEndDt() ) + "'");
		}
		sb.append(",");
		sb.append(" data_input_lines_qt = ");
		sb.append( bean.getDataInputLinesQtString());
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
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
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
		SeisenSystemControlBean bean = (SeisenSystemControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("seisen_system_control where ");
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
