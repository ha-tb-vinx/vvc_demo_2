package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2004/8/17 10:29:52)
 */
public class ENohinFileHeaderDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public ENohinFileHeaderDM()
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
		ENohinFileHeaderBean bean = new ENohinFileHeaderBean();
		bean.setFileHeadNb( rest.getString("file_head_nb") );
		bean.setJcaControlNb( rest.getLong("jca_control_nb") );
		bean.setSyoriJyokyoFg( rest.getString("syori_jyokyo_fg") );
		bean.setServerFileNa( rest.getString("server_file_na") );
		bean.setClientFileNa( rest.getString("client_file_na") );
		bean.setFileLengthQt( rest.getString("file_length_qt") );
		bean.setOutputFg( rest.getString("output_fg") );
		bean.setFileRecvFg( rest.getString("file_recv_fg") );
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
		sb.append("file_head_nb ");
		sb.append(", ");
		sb.append("jca_control_nb ");
		sb.append(", ");
		sb.append("syori_jyokyo_fg ");
		sb.append(", ");
		sb.append("server_file_na ");
		sb.append(", ");
		sb.append("client_file_na ");
		sb.append(", ");
		sb.append("file_length_qt ");
		sb.append(", ");
		sb.append("output_fg ");
		sb.append(", ");
		sb.append("file_recv_fg ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append("from E_Nohin_File_Header ");


		// file_head_nb に対するWHERE区
		if( map.get("file_head_nb_bef") != null && ((String)map.get("file_head_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb >= '" + conv.convertWhereString( (String)map.get("file_head_nb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_aft") != null && ((String)map.get("file_head_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb <= '" + conv.convertWhereString( (String)map.get("file_head_nb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb") != null && ((String)map.get("file_head_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb = '" + conv.convertWhereString( (String)map.get("file_head_nb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_like") != null && ((String)map.get("file_head_nb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb like '%" + conv.convertWhereString( (String)map.get("file_head_nb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_bef_like") != null && ((String)map.get("file_head_nb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb like '%" + conv.convertWhereString( (String)map.get("file_head_nb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_aft_like") != null && ((String)map.get("file_head_nb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb like '" + conv.convertWhereString( (String)map.get("file_head_nb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_in") != null && ((String)map.get("file_head_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("file_head_nb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_not_in") != null && ((String)map.get("file_head_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("file_head_nb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// jca_control_nb に対するWHERE区
		if( map.get("jca_control_nb_bef") != null && ((String)map.get("jca_control_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jca_control_nb >= " + (String)map.get("jca_control_nb_bef") );
			whereStr = andStr;
		}
		if( map.get("jca_control_nb_aft") != null && ((String)map.get("jca_control_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jca_control_nb <= " + (String)map.get("jca_control_nb_aft") );
			whereStr = andStr;
		}
		if( map.get("jca_control_nb") != null && ((String)map.get("jca_control_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("jca_control_nb = " + (String)map.get("jca_control_nb"));
			whereStr = andStr;
		}
		if( map.get("jca_control_nb_in") != null && ((String)map.get("jca_control_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jca_control_nb in ( " + conv.convertWhereString( (String)map.get("jca_control_nb_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("jca_control_nb_not_in") != null && ((String)map.get("jca_control_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jca_control_nb not in ( " + conv.convertWhereString( (String)map.get("jca_control_nb_not_in") ) + " )");
			whereStr = andStr;
		}


		// syori_jyokyo_fg に対するWHERE区
		if( map.get("syori_jyokyo_fg_bef") != null && ((String)map.get("syori_jyokyo_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg >= '" + conv.convertWhereString( (String)map.get("syori_jyokyo_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syori_jyokyo_fg_aft") != null && ((String)map.get("syori_jyokyo_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg <= '" + conv.convertWhereString( (String)map.get("syori_jyokyo_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syori_jyokyo_fg") != null && ((String)map.get("syori_jyokyo_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg = '" + conv.convertWhereString( (String)map.get("syori_jyokyo_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syori_jyokyo_fg_like") != null && ((String)map.get("syori_jyokyo_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg like '%" + conv.convertWhereString( (String)map.get("syori_jyokyo_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syori_jyokyo_fg_bef_like") != null && ((String)map.get("syori_jyokyo_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg like '%" + conv.convertWhereString( (String)map.get("syori_jyokyo_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syori_jyokyo_fg_aft_like") != null && ((String)map.get("syori_jyokyo_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg like '" + conv.convertWhereString( (String)map.get("syori_jyokyo_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syori_jyokyo_fg_in") != null && ((String)map.get("syori_jyokyo_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syori_jyokyo_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syori_jyokyo_fg_not_in") != null && ((String)map.get("syori_jyokyo_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syori_jyokyo_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// server_file_na に対するWHERE区
		if( map.get("server_file_na_bef") != null && ((String)map.get("server_file_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("server_file_na >= '" + conv.convertWhereString( (String)map.get("server_file_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("server_file_na_aft") != null && ((String)map.get("server_file_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("server_file_na <= '" + conv.convertWhereString( (String)map.get("server_file_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("server_file_na") != null && ((String)map.get("server_file_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("server_file_na = '" + conv.convertWhereString( (String)map.get("server_file_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("server_file_na_like") != null && ((String)map.get("server_file_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("server_file_na like '%" + conv.convertWhereString( (String)map.get("server_file_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("server_file_na_bef_like") != null && ((String)map.get("server_file_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("server_file_na like '%" + conv.convertWhereString( (String)map.get("server_file_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("server_file_na_aft_like") != null && ((String)map.get("server_file_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("server_file_na like '" + conv.convertWhereString( (String)map.get("server_file_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("server_file_na_in") != null && ((String)map.get("server_file_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("server_file_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("server_file_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("server_file_na_not_in") != null && ((String)map.get("server_file_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("server_file_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("server_file_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// client_file_na に対するWHERE区
		if( map.get("client_file_na_bef") != null && ((String)map.get("client_file_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("client_file_na >= '" + conv.convertWhereString( (String)map.get("client_file_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("client_file_na_aft") != null && ((String)map.get("client_file_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("client_file_na <= '" + conv.convertWhereString( (String)map.get("client_file_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("client_file_na") != null && ((String)map.get("client_file_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("client_file_na = '" + conv.convertWhereString( (String)map.get("client_file_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("client_file_na_like") != null && ((String)map.get("client_file_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("client_file_na like '%" + conv.convertWhereString( (String)map.get("client_file_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("client_file_na_bef_like") != null && ((String)map.get("client_file_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("client_file_na like '%" + conv.convertWhereString( (String)map.get("client_file_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("client_file_na_aft_like") != null && ((String)map.get("client_file_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("client_file_na like '" + conv.convertWhereString( (String)map.get("client_file_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("client_file_na_in") != null && ((String)map.get("client_file_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("client_file_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("client_file_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("client_file_na_not_in") != null && ((String)map.get("client_file_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("client_file_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("client_file_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// file_length_qt に対するWHERE区
		if( map.get("file_length_qt_bef") != null && ((String)map.get("file_length_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt >= '" + conv.convertWhereString( (String)map.get("file_length_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_length_qt_aft") != null && ((String)map.get("file_length_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt <= '" + conv.convertWhereString( (String)map.get("file_length_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_length_qt") != null && ((String)map.get("file_length_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt = '" + conv.convertWhereString( (String)map.get("file_length_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_length_qt_like") != null && ((String)map.get("file_length_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt like '%" + conv.convertWhereString( (String)map.get("file_length_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("file_length_qt_bef_like") != null && ((String)map.get("file_length_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt like '%" + conv.convertWhereString( (String)map.get("file_length_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_length_qt_aft_like") != null && ((String)map.get("file_length_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt like '" + conv.convertWhereString( (String)map.get("file_length_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("file_length_qt_in") != null && ((String)map.get("file_length_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("file_length_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("file_length_qt_not_in") != null && ((String)map.get("file_length_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("file_length_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// output_fg に対するWHERE区
		if( map.get("output_fg_bef") != null && ((String)map.get("output_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("output_fg >= '" + conv.convertWhereString( (String)map.get("output_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("output_fg_aft") != null && ((String)map.get("output_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("output_fg <= '" + conv.convertWhereString( (String)map.get("output_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("output_fg") != null && ((String)map.get("output_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("output_fg = '" + conv.convertWhereString( (String)map.get("output_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("output_fg_like") != null && ((String)map.get("output_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("output_fg like '%" + conv.convertWhereString( (String)map.get("output_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("output_fg_bef_like") != null && ((String)map.get("output_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("output_fg like '%" + conv.convertWhereString( (String)map.get("output_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("output_fg_aft_like") != null && ((String)map.get("output_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("output_fg like '" + conv.convertWhereString( (String)map.get("output_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("output_fg_in") != null && ((String)map.get("output_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("output_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("output_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("output_fg_not_in") != null && ((String)map.get("output_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("output_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("output_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// file_recv_fg に対するWHERE区
		if( map.get("file_recv_fg_bef") != null && ((String)map.get("file_recv_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_recv_fg >= '" + conv.convertWhereString( (String)map.get("file_recv_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_recv_fg_aft") != null && ((String)map.get("file_recv_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_recv_fg <= '" + conv.convertWhereString( (String)map.get("file_recv_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_recv_fg") != null && ((String)map.get("file_recv_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_recv_fg = '" + conv.convertWhereString( (String)map.get("file_recv_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_recv_fg_like") != null && ((String)map.get("file_recv_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_recv_fg like '%" + conv.convertWhereString( (String)map.get("file_recv_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("file_recv_fg_bef_like") != null && ((String)map.get("file_recv_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_recv_fg like '%" + conv.convertWhereString( (String)map.get("file_recv_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_recv_fg_aft_like") != null && ((String)map.get("file_recv_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_recv_fg like '" + conv.convertWhereString( (String)map.get("file_recv_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("file_recv_fg_in") != null && ((String)map.get("file_recv_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_recv_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("file_recv_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("file_recv_fg_not_in") != null && ((String)map.get("file_recv_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_recv_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("file_recv_fg_not_in") ),",","','") + "' )");
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
		sb.append(" order by ");
		sb.append("file_head_nb");
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
		ENohinFileHeaderBean bean = (ENohinFileHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("E_Nohin_File_Header (");
		if( bean.getFileHeadNb() != null && bean.getFileHeadNb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" file_head_nb");
		}
		if( befKanma ) sb.append(",");
		sb.append(" jca_control_nb");
		if( bean.getSyoriJyokyoFg() != null && bean.getSyoriJyokyoFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syori_jyokyo_fg");
		}
		if( bean.getServerFileNa() != null && bean.getServerFileNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" server_file_na");
		}
		if( bean.getClientFileNa() != null && bean.getClientFileNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" client_file_na");
		}
		if( bean.getFileLengthQt() != null && bean.getFileLengthQt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" file_length_qt");
		}
		if( bean.getOutputFg() != null && bean.getOutputFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" output_fg");
		}
		if( bean.getFileRecvFg() != null && bean.getFileRecvFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" file_recv_fg");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" riyo_user_id");
		}
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");


		if( bean.getFileHeadNb() != null && bean.getFileHeadNb().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getFileHeadNb() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getJcaControlNbString());
		if( bean.getSyoriJyokyoFg() != null && bean.getSyoriJyokyoFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSyoriJyokyoFg() ) + "'");
		}
		if( bean.getServerFileNa() != null && bean.getServerFileNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getServerFileNa() ) + "'");
		}
		if( bean.getClientFileNa() != null && bean.getClientFileNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getClientFileNa() ) + "'");
		}
		if( bean.getFileLengthQt() != null && bean.getFileLengthQt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getFileLengthQt() ) + "'");
		}
		if( bean.getOutputFg() != null && bean.getOutputFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getOutputFg() ) + "'");
		}
		if( bean.getFileRecvFg() != null && bean.getFileRecvFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getFileRecvFg() ) + "'");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getRiyoUserId() ) + "'");
		}
		sb.append(",");
		sb.append("'TO_CHAR(sysdate, 'YYYYMMDDHH24MISS')'");
		sb.append(",");
		sb.append("'TO_CHAR(sysdate, 'YYYYMMDDHH24MISS')'");
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
		ENohinFileHeaderBean bean = (ENohinFileHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("E_Nohin_File_Header set ");
		befKanma = true;
		sb.append(" jca_control_nb = ");
		sb.append( bean.getJcaControlNbString());
		if( bean.getSyoriJyokyoFg() != null && bean.getSyoriJyokyoFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syori_jyokyo_fg = ");
			sb.append("'" + conv.convertString( bean.getSyoriJyokyoFg() ) + "'");
		}
		if( bean.getServerFileNa() != null && bean.getServerFileNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" server_file_na = ");
			sb.append("'" + conv.convertString( bean.getServerFileNa() ) + "'");
		}
		if( bean.getClientFileNa() != null && bean.getClientFileNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" client_file_na = ");
			sb.append("'" + conv.convertString( bean.getClientFileNa() ) + "'");
		}
		if( bean.getFileLengthQt() != null && bean.getFileLengthQt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" file_length_qt = ");
			sb.append("'" + conv.convertString( bean.getFileLengthQt() ) + "'");
		}
		if( bean.getOutputFg() != null && bean.getOutputFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" output_fg = ");
			sb.append("'" + conv.convertString( bean.getOutputFg() ) + "'");
		}
		if( bean.getFileRecvFg() != null && bean.getFileRecvFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" file_recv_fg = ");
			sb.append("'" + conv.convertString( bean.getFileRecvFg() ) + "'");
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
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getFileHeadNb() != null && bean.getFileHeadNb().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" file_head_nb = ");
			sb.append("'" + conv.convertWhereString( bean.getFileHeadNb() ) + "'");
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
		ENohinFileHeaderBean bean = (ENohinFileHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("E_Nohin_File_Header where ");
		sb.append(" file_head_nb = ");
		sb.append("'" + conv.convertWhereString( bean.getFileHeadNb() ) + "'");
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
