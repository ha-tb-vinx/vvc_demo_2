package mdware.master.util.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>タイトル: マスタ制御情報</p>
 * <p>説明: マスタ制御情報用ＤＭクラス</p>
 * <p>著作権: Copyright (c) 2007</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author H.Yamamoto
 * @version 1.0
 */
public class RMasterControlDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public RMasterControlDM()
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
		RMasterControlBean bean = new RMasterControlBean();
		bean.setTaikeiKirikaeKijunDtT( encodingString(rest.getString("taikei_kirikae_kijun_dt_t")) );
		bean.setTaikeiKirikaeKijunDtJ( encodingString(rest.getString("taikei_kirikae_kijun_dt_j")) );
		bean.setTaikeiKirikaeKijunDtG( encodingString(rest.getString("taikei_kirikae_kijun_dt_g")) );
		bean.setTaikeiKirikaeKijunDtF( encodingString(rest.getString("taikei_kirikae_kijun_dt_f")) );
		bean.setSonotaKijunDt1( encodingString(rest.getString("sonota_kijun_dt_1")) );
		bean.setSonotaKijunDt2( encodingString(rest.getString("sonota_kijun_dt_2")) );
		bean.setSonotaKijunDt3( encodingString(rest.getString("sonota_kijun_dt_3")) );
		bean.setInsertTs( encodingString(rest.getString("insert_ts")) );
		bean.setInsertUserId( encodingString(rest.getString("insert_user_id")) );
		bean.setUpdateTs( encodingString(rest.getString("update_ts")) );
		bean.setUpdateUserId( encodingString(rest.getString("update_user_id")) );
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
		sb.append("taikei_kirikae_kijun_dt_t ");
		sb.append(", ");
		sb.append("taikei_kirikae_kijun_dt_j ");
		sb.append(", ");
		sb.append("taikei_kirikae_kijun_dt_g ");
		sb.append(", ");
		sb.append("taikei_kirikae_kijun_dt_f ");
		sb.append(", ");
		sb.append("sonota_kijun_dt_1 ");
		sb.append(", ");
		sb.append("sonota_kijun_dt_2 ");
		sb.append(", ");
		sb.append("sonota_kijun_dt_3 ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append("from r_master_control ");


		// taikei_kirikae_kijun_dt_t に対するWHERE区
		if( map.get("taikei_kirikae_kijun_dt_t_bef") != null && ((String)map.get("taikei_kirikae_kijun_dt_t_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_t >= '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_t_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_t_aft") != null && ((String)map.get("taikei_kirikae_kijun_dt_t_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_t <= '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_t_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_t") != null && ((String)map.get("taikei_kirikae_kijun_dt_t")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_t = '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_t") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_t_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_t_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_t like '%" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_t_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_t_bef_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_t_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_t like '%" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_t_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_t_aft_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_t_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_t like '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_t_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_t_in") != null && ((String)map.get("taikei_kirikae_kijun_dt_t_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_t in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_t_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_t_not_in") != null && ((String)map.get("taikei_kirikae_kijun_dt_t_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_t not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_t_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// taikei_kirikae_kijun_dt_j に対するWHERE区
		if( map.get("taikei_kirikae_kijun_dt_j_bef") != null && ((String)map.get("taikei_kirikae_kijun_dt_j_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_j >= '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_j_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_j_aft") != null && ((String)map.get("taikei_kirikae_kijun_dt_j_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_j <= '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_j_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_j") != null && ((String)map.get("taikei_kirikae_kijun_dt_j")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_j = '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_j") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_j_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_j_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_j like '%" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_j_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_j_bef_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_j_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_j like '%" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_j_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_j_aft_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_j_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_j like '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_j_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_j_in") != null && ((String)map.get("taikei_kirikae_kijun_dt_j_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_j in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_j_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_j_not_in") != null && ((String)map.get("taikei_kirikae_kijun_dt_j_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_j not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_j_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// taikei_kirikae_kijun_dt_g に対するWHERE区
		if( map.get("taikei_kirikae_kijun_dt_g_bef") != null && ((String)map.get("taikei_kirikae_kijun_dt_g_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_g >= '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_g_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_g_aft") != null && ((String)map.get("taikei_kirikae_kijun_dt_g_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_g <= '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_g_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_g") != null && ((String)map.get("taikei_kirikae_kijun_dt_g")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_g = '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_g") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_g_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_g_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_g like '%" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_g_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_g_bef_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_g_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_g like '%" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_g_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_g_aft_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_g_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_g like '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_g_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_g_in") != null && ((String)map.get("taikei_kirikae_kijun_dt_g_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_g in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_g_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_g_not_in") != null && ((String)map.get("taikei_kirikae_kijun_dt_g_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_g not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_g_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// taikei_kirikae_kijun_dt_f に対するWHERE区
		if( map.get("taikei_kirikae_kijun_dt_f_bef") != null && ((String)map.get("taikei_kirikae_kijun_dt_f_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_f >= '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_f_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_f_aft") != null && ((String)map.get("taikei_kirikae_kijun_dt_f_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_f <= '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_f_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_f") != null && ((String)map.get("taikei_kirikae_kijun_dt_f")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_f = '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_f") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_f_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_f_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_f like '%" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_f_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_f_bef_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_f_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_f like '%" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_f_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_f_aft_like") != null && ((String)map.get("taikei_kirikae_kijun_dt_f_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_f like '" + conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_f_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_f_in") != null && ((String)map.get("taikei_kirikae_kijun_dt_f_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_f in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_f_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("taikei_kirikae_kijun_dt_f_not_in") != null && ((String)map.get("taikei_kirikae_kijun_dt_f_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taikei_kirikae_kijun_dt_f not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taikei_kirikae_kijun_dt_f_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sonota_kijun_dt_1 に対するWHERE区
		if( map.get("sonota_kijun_dt_1_bef") != null && ((String)map.get("sonota_kijun_dt_1_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_1 >= '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_1_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_1_aft") != null && ((String)map.get("sonota_kijun_dt_1_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_1 <= '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_1_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_1") != null && ((String)map.get("sonota_kijun_dt_1")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_1 = '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_1") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_1_like") != null && ((String)map.get("sonota_kijun_dt_1_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_1 like '%" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_1_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_1_bef_like") != null && ((String)map.get("sonota_kijun_dt_1_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_1 like '%" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_1_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_1_aft_like") != null && ((String)map.get("sonota_kijun_dt_1_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_1 like '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_1_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_1_in") != null && ((String)map.get("sonota_kijun_dt_1_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_1 in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sonota_kijun_dt_1_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_1_not_in") != null && ((String)map.get("sonota_kijun_dt_1_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_1 not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sonota_kijun_dt_1_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sonota_kijun_dt_2 に対するWHERE区
		if( map.get("sonota_kijun_dt_2_bef") != null && ((String)map.get("sonota_kijun_dt_2_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_2 >= '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_2_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_2_aft") != null && ((String)map.get("sonota_kijun_dt_2_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_2 <= '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_2_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_2") != null && ((String)map.get("sonota_kijun_dt_2")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_2 = '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_2") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_2_like") != null && ((String)map.get("sonota_kijun_dt_2_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_2 like '%" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_2_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_2_bef_like") != null && ((String)map.get("sonota_kijun_dt_2_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_2 like '%" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_2_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_2_aft_like") != null && ((String)map.get("sonota_kijun_dt_2_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_2 like '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_2_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_2_in") != null && ((String)map.get("sonota_kijun_dt_2_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_2 in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sonota_kijun_dt_2_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_2_not_in") != null && ((String)map.get("sonota_kijun_dt_2_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_2 not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sonota_kijun_dt_2_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sonota_kijun_dt_3 に対するWHERE区
		if( map.get("sonota_kijun_dt_3_bef") != null && ((String)map.get("sonota_kijun_dt_3_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_3 >= '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_3_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_3_aft") != null && ((String)map.get("sonota_kijun_dt_3_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_3 <= '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_3_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_3") != null && ((String)map.get("sonota_kijun_dt_3")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_3 = '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_3") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_3_like") != null && ((String)map.get("sonota_kijun_dt_3_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_3 like '%" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_3_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_3_bef_like") != null && ((String)map.get("sonota_kijun_dt_3_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_3 like '%" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_3_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_3_aft_like") != null && ((String)map.get("sonota_kijun_dt_3_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_3 like '" + conv.convertWhereString( (String)map.get("sonota_kijun_dt_3_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_3_in") != null && ((String)map.get("sonota_kijun_dt_3_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_3 in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sonota_kijun_dt_3_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sonota_kijun_dt_3_not_in") != null && ((String)map.get("sonota_kijun_dt_3_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sonota_kijun_dt_3 not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sonota_kijun_dt_3_not_in") ),",","','") + "' )");
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
		sb.append(" for read only ");
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
		RMasterControlBean bean = (RMasterControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_master_control (");
		if( bean.getTaikeiKirikaeKijunDtT() != null && bean.getTaikeiKirikaeKijunDtT().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" taikei_kirikae_kijun_dt_t");
		}
		if( bean.getTaikeiKirikaeKijunDtJ() != null && bean.getTaikeiKirikaeKijunDtJ().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taikei_kirikae_kijun_dt_j");
		}
		if( bean.getTaikeiKirikaeKijunDtG() != null && bean.getTaikeiKirikaeKijunDtG().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taikei_kirikae_kijun_dt_g");
		}
		if( bean.getTaikeiKirikaeKijunDtF() != null && bean.getTaikeiKirikaeKijunDtF().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taikei_kirikae_kijun_dt_f");
		}
		if( bean.getSonotaKijunDt1() != null && bean.getSonotaKijunDt1().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sonota_kijun_dt_1");
		}
		if( bean.getSonotaKijunDt2() != null && bean.getSonotaKijunDt2().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sonota_kijun_dt_2");
		}
		if( bean.getSonotaKijunDt3() != null && bean.getSonotaKijunDt3().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sonota_kijun_dt_3");
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


		sb.append(")Values(");


		if( bean.getTaikeiKirikaeKijunDtT() != null && bean.getTaikeiKirikaeKijunDtT().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTaikeiKirikaeKijunDtT() ) + "'");
		}
		if( bean.getTaikeiKirikaeKijunDtJ() != null && bean.getTaikeiKirikaeKijunDtJ().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTaikeiKirikaeKijunDtJ() ) + "'");
		}
		if( bean.getTaikeiKirikaeKijunDtG() != null && bean.getTaikeiKirikaeKijunDtG().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTaikeiKirikaeKijunDtG() ) + "'");
		}
		if( bean.getTaikeiKirikaeKijunDtF() != null && bean.getTaikeiKirikaeKijunDtF().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTaikeiKirikaeKijunDtF() ) + "'");
		}
		if( bean.getSonotaKijunDt1() != null && bean.getSonotaKijunDt1().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSonotaKijunDt1() ) + "'");
		}
		if( bean.getSonotaKijunDt2() != null && bean.getSonotaKijunDt2().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSonotaKijunDt2() ) + "'");
		}
		if( bean.getSonotaKijunDt3() != null && bean.getSonotaKijunDt3().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSonotaKijunDt3() ) + "'");
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
		RMasterControlBean bean = (RMasterControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_master_control set ");
		if( bean.getTaikeiKirikaeKijunDtT() != null && bean.getTaikeiKirikaeKijunDtT().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" taikei_kirikae_kijun_dt_t = ");
			sb.append("'" + conv.convertString( bean.getTaikeiKirikaeKijunDtT() ) + "'");
		}
		if( bean.getTaikeiKirikaeKijunDtJ() != null && bean.getTaikeiKirikaeKijunDtJ().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taikei_kirikae_kijun_dt_j = ");
			sb.append("'" + conv.convertString( bean.getTaikeiKirikaeKijunDtJ() ) + "'");
		}
		if( bean.getTaikeiKirikaeKijunDtG() != null && bean.getTaikeiKirikaeKijunDtG().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taikei_kirikae_kijun_dt_g = ");
			sb.append("'" + conv.convertString( bean.getTaikeiKirikaeKijunDtG() ) + "'");
		}
		if( bean.getTaikeiKirikaeKijunDtF() != null && bean.getTaikeiKirikaeKijunDtF().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taikei_kirikae_kijun_dt_f = ");
			sb.append("'" + conv.convertString( bean.getTaikeiKirikaeKijunDtF() ) + "'");
		}
		if( bean.getSonotaKijunDt1() != null && bean.getSonotaKijunDt1().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sonota_kijun_dt_1 = ");
			sb.append("'" + conv.convertString( bean.getSonotaKijunDt1() ) + "'");
		}
		if( bean.getSonotaKijunDt2() != null && bean.getSonotaKijunDt2().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sonota_kijun_dt_2 = ");
			sb.append("'" + conv.convertString( bean.getSonotaKijunDt2() ) + "'");
		}
		if( bean.getSonotaKijunDt3() != null && bean.getSonotaKijunDt3().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sonota_kijun_dt_3 = ");
			sb.append("'" + conv.convertString( bean.getSonotaKijunDt3() ) + "'");
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
		RMasterControlBean bean = (RMasterControlBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_master_control where ");
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
