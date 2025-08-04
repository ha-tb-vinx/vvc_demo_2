package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2004/9/25 14:49:58)
 */
public class MaTokaikyuDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public MaTokaikyuDM()
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
		MaTokaikyuBean bean = new MaTokaikyuBean();
		bean.setTokaikyuCd( rest.getString("tokaikyu_cd") );
		bean.setTokaikyuNa( rest.getString("tokaikyu_na") );
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
		sb.append("tokaikyu_cd ");
		sb.append(", ");
		sb.append("tokaikyu_na ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append("from ma_tokaikyu ");


		// tokaikyu_cd に対するWHERE区
		if( map.get("tokaikyu_cd_bef") != null && ((String)map.get("tokaikyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd >= '" + conv.convertWhereString( (String)map.get("tokaikyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_aft") != null && ((String)map.get("tokaikyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd <= '" + conv.convertWhereString( (String)map.get("tokaikyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd") != null && ((String)map.get("tokaikyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd = '" + conv.convertWhereString( (String)map.get("tokaikyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_like") != null && ((String)map.get("tokaikyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd like '%" + conv.convertWhereString( (String)map.get("tokaikyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_bef_like") != null && ((String)map.get("tokaikyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd like '%" + conv.convertWhereString( (String)map.get("tokaikyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_aft_like") != null && ((String)map.get("tokaikyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd like '" + conv.convertWhereString( (String)map.get("tokaikyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_in") != null && ((String)map.get("tokaikyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tokaikyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_not_in") != null && ((String)map.get("tokaikyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tokaikyu_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tokaikyu_na に対するWHERE区
		if( map.get("tokaikyu_na_bef") != null && ((String)map.get("tokaikyu_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na >= '" + conv.convertWhereString( (String)map.get("tokaikyu_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_aft") != null && ((String)map.get("tokaikyu_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na <= '" + conv.convertWhereString( (String)map.get("tokaikyu_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na") != null && ((String)map.get("tokaikyu_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na = '" + conv.convertWhereString( (String)map.get("tokaikyu_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_like") != null && ((String)map.get("tokaikyu_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na like '%" + conv.convertWhereString( (String)map.get("tokaikyu_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_bef_like") != null && ((String)map.get("tokaikyu_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na like '%" + conv.convertWhereString( (String)map.get("tokaikyu_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_aft_like") != null && ((String)map.get("tokaikyu_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na like '" + conv.convertWhereString( (String)map.get("tokaikyu_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_in") != null && ((String)map.get("tokaikyu_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tokaikyu_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_not_in") != null && ((String)map.get("tokaikyu_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tokaikyu_na_not_in") ),",","','") + "' )");
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
		sb.append("tokaikyu_cd");
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
		MaTokaikyuBean bean = (MaTokaikyuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("ma_tokaikyu (");
		if( bean.getTokaikyuCd() != null && bean.getTokaikyuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" tokaikyu_cd");
		}
		if( bean.getTokaikyuNa() != null && bean.getTokaikyuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tokaikyu_na");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" riyo_user_id");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}


		sb.append(")Values(");


		if( bean.getTokaikyuCd() != null && bean.getTokaikyuCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTokaikyuCd() ) + "'");
		}
		if( bean.getTokaikyuNa() != null && bean.getTokaikyuNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTokaikyuNa() ) + "'");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getRiyoUserId() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
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
		MaTokaikyuBean bean = (MaTokaikyuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("ma_tokaikyu set ");
		if( bean.getTokaikyuNa() != null && bean.getTokaikyuNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" tokaikyu_na = ");
			sb.append("'" + conv.convertString( bean.getTokaikyuNa() ) + "'");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" riyo_user_id = ");
			sb.append("'" + conv.convertString( bean.getRiyoUserId() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getTokaikyuCd() != null && bean.getTokaikyuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" tokaikyu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTokaikyuCd() ) + "'");
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
		MaTokaikyuBean bean = (MaTokaikyuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("ma_tokaikyu where ");
		sb.append(" tokaikyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTokaikyuCd() ) + "'");
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
