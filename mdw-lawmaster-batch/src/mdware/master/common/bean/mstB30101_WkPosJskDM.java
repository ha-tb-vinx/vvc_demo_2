package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mstB30101_WkPosJskDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public mstB30101_WkPosJskDM()
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
		mstB30101_WkPosJskBean bean = new mstB30101_WkPosJskBean();
		bean.setSiiresakiCd( encodingString(rest.getString("siiresaki_cd")) );
		bean.setSyohinCd( encodingString(rest.getString("syohin_cd")) );
		bean.setTenpoCd( encodingString(rest.getString("tenpo_cd")) );
		bean.setUriageDt( encodingString(rest.getString("uriage_dt")) );
		bean.setUriageVl( rest.getLong("uriage_vl") );
		bean.setUriageQt( rest.getLong("uriage_qt") );
		bean.setSyutokuFg( encodingString(rest.getString("syutoku_fg")) );
		bean.setInsertTs( encodingString(rest.getString("insert_ts")) );
		bean.setUpdateTs( encodingString(rest.getString("update_ts")) );
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
		sb.append("siiresaki_cd ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("uriage_dt ");
		sb.append(", ");
		sb.append("uriage_vl ");
		sb.append(", ");
		sb.append("uriage_qt ");
		sb.append(", ");
		sb.append("syutoku_fg ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append("from WK_POSJSK ");


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


		// uriage_dt に対するWHERE区
		if( map.get("uriage_dt_bef") != null && ((String)map.get("uriage_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_dt >= '" + conv.convertWhereString( (String)map.get("uriage_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriage_dt_aft") != null && ((String)map.get("uriage_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_dt <= '" + conv.convertWhereString( (String)map.get("uriage_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriage_dt") != null && ((String)map.get("uriage_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_dt = '" + conv.convertWhereString( (String)map.get("uriage_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriage_dt_like") != null && ((String)map.get("uriage_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_dt like '%" + conv.convertWhereString( (String)map.get("uriage_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("uriage_dt_bef_like") != null && ((String)map.get("uriage_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_dt like '%" + conv.convertWhereString( (String)map.get("uriage_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriage_dt_aft_like") != null && ((String)map.get("uriage_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_dt like '" + conv.convertWhereString( (String)map.get("uriage_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("uriage_dt_in") != null && ((String)map.get("uriage_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriage_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("uriage_dt_not_in") != null && ((String)map.get("uriage_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriage_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// uriage_vl に対するWHERE区
		if( map.get("uriage_vl_bef") != null && ((String)map.get("uriage_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_vl >= " + (String)map.get("uriage_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("uriage_vl_aft") != null && ((String)map.get("uriage_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_vl <= " + (String)map.get("uriage_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("uriage_vl") != null && ((String)map.get("uriage_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("uriage_vl = " + (String)map.get("uriage_vl"));
			whereStr = andStr;
		}
		if( map.get("uriage_vl_in") != null && ((String)map.get("uriage_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_vl in ( " + conv.convertWhereString( (String)map.get("uriage_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("uriage_vl_not_in") != null && ((String)map.get("uriage_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_vl not in ( " + conv.convertWhereString( (String)map.get("uriage_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// uriage_qt に対するWHERE区
		if( map.get("uriage_qt_bef") != null && ((String)map.get("uriage_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_qt >= " + (String)map.get("uriage_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("uriage_qt_aft") != null && ((String)map.get("uriage_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_qt <= " + (String)map.get("uriage_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("uriage_qt") != null && ((String)map.get("uriage_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("uriage_qt = " + (String)map.get("uriage_qt"));
			whereStr = andStr;
		}
		if( map.get("uriage_qt_in") != null && ((String)map.get("uriage_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_qt in ( " + conv.convertWhereString( (String)map.get("uriage_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("uriage_qt_not_in") != null && ((String)map.get("uriage_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriage_qt not in ( " + conv.convertWhereString( (String)map.get("uriage_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// syutoku_fg に対するWHERE区
		if( map.get("syutoku_fg_bef") != null && ((String)map.get("syutoku_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syutoku_fg >= '" + conv.convertWhereString( (String)map.get("syutoku_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syutoku_fg_aft") != null && ((String)map.get("syutoku_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syutoku_fg <= '" + conv.convertWhereString( (String)map.get("syutoku_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syutoku_fg") != null && ((String)map.get("syutoku_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syutoku_fg = '" + conv.convertWhereString( (String)map.get("syutoku_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syutoku_fg_like") != null && ((String)map.get("syutoku_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syutoku_fg like '%" + conv.convertWhereString( (String)map.get("syutoku_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syutoku_fg_bef_like") != null && ((String)map.get("syutoku_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syutoku_fg like '%" + conv.convertWhereString( (String)map.get("syutoku_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syutoku_fg_aft_like") != null && ((String)map.get("syutoku_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syutoku_fg like '" + conv.convertWhereString( (String)map.get("syutoku_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syutoku_fg_in") != null && ((String)map.get("syutoku_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syutoku_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syutoku_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syutoku_fg_not_in") != null && ((String)map.get("syutoku_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syutoku_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syutoku_fg_not_in") ),",","','") + "' )");
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
		sb.append(" order by ");
		sb.append("siiresaki_cd");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("uriage_dt");
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
		mstB30101_WkPosJskBean bean = (mstB30101_WkPosJskBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("WK_POSJSK (");
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" siiresaki_cd");
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
		if( bean.getUriageDt() != null && bean.getUriageDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriage_dt");
		}
		if( befKanma ) sb.append(",");
		sb.append(" uriage_vl");
		sb.append(",");
		sb.append(" uriage_qt");
		if( bean.getSyutokuFg() != null && bean.getSyutokuFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syutoku_fg");
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


		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
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
		if( bean.getUriageDt() != null && bean.getUriageDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUriageDt() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getUriageVlString());
		sb.append(",");
		sb.append( bean.getUriageQtString());
		if( bean.getSyutokuFg() != null && bean.getSyutokuFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSyutokuFg() ) + "'");
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
		mstB30101_WkPosJskBean bean = (mstB30101_WkPosJskBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("WK_POSJSK set ");
		befKanma = true;
		sb.append(" uriage_vl = ");
		sb.append( bean.getUriageVlString());
		sb.append(",");
		sb.append(" uriage_qt = ");
		sb.append( bean.getUriageQtString());
		if( bean.getSyutokuFg() != null && bean.getSyutokuFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syutoku_fg = ");
			sb.append("'" + conv.convertString( bean.getSyutokuFg() ) + "'");
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


		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" siiresaki_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() ) + "'");
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
		if( bean.getUriageDt() != null && bean.getUriageDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" uriage_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getUriageDt() ) + "'");
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
		mstB30101_WkPosJskBean bean = (mstB30101_WkPosJskBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("WK_POSJSK where ");
		sb.append(" siiresaki_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append(" AND");
		sb.append(" uriage_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getUriageDt() ) + "'");
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
