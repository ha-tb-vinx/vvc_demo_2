package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2004/12/29 19:15:8)
 */
public class MaCenterDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public MaCenterDM()
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
		MaCenterBean bean = new MaCenterBean();
		bean.setCenterCd( rest.getString("center_cd") );
		bean.setCenterNa( rest.getString("center_na") );
		bean.setCenterKa( rest.getString("center_ka") );
		bean.setCenterRn( rest.getString("center_rn") );
		bean.setCenterRk( rest.getString("center_rk") );
		bean.setHojinCd( rest.getString("hojin_cd") );
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
		sb.append("center_cd ");
		sb.append(", ");
		sb.append("center_na ");
		sb.append(", ");
		sb.append("center_ka ");
		sb.append(", ");
		sb.append("center_rn ");
		sb.append(", ");
		sb.append("center_rk ");
		sb.append(", ");
		sb.append("hojin_cd ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append("from ma_center ");

		// center_cd に対するWHERE区
		if( map.get("center_cd") != null && ((String)map.get("center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_cd = '" + conv.convertWhereString( (String)map.get("center_cd") ) + "'");
			whereStr = andStr;
		}

		sb.append(" order by ");
		sb.append("center_cd");
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
		MaCenterBean bean = (MaCenterBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("ma_center (");
		if( bean.getCenterCd() != null && bean.getCenterCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" center_cd");
		}
		if( bean.getCenterNa() != null && bean.getCenterNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_na");
		}
		if( bean.getCenterKa() != null && bean.getCenterKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_ka");
		}
		if( bean.getCenterRn() != null && bean.getCenterRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_rn");
		}
		if( bean.getCenterRk() != null && bean.getCenterRk().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_rk");
		}
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hojin_cd");
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


		if( bean.getCenterCd() != null && bean.getCenterCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCenterCd() ) + "'");
		}
		if( bean.getCenterNa() != null && bean.getCenterNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCenterNa() ) + "'");
		}
		if( bean.getCenterKa() != null && bean.getCenterKa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCenterKa() ) + "'");
		}
		if( bean.getCenterRn() != null && bean.getCenterRn().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCenterRn() ) + "'");
		}
		if( bean.getCenterRk() != null && bean.getCenterRk().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCenterRk() ) + "'");
		}
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
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
		MaCenterBean bean = (MaCenterBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("ma_center set ");
		if( bean.getCenterNa() != null && bean.getCenterNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" center_na = ");
			sb.append("'" + conv.convertString( bean.getCenterNa() ) + "'");
		}
		if( bean.getCenterKa() != null && bean.getCenterKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_ka = ");
			sb.append("'" + conv.convertString( bean.getCenterKa() ) + "'");
		}
		if( bean.getCenterRn() != null && bean.getCenterRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_rn = ");
			sb.append("'" + conv.convertString( bean.getCenterRn() ) + "'");
		}
		if( bean.getCenterRk() != null && bean.getCenterRk().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_rk = ");
			sb.append("'" + conv.convertString( bean.getCenterRk() ) + "'");
		}
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hojin_cd = ");
			sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
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


		if( bean.getCenterCd() != null && bean.getCenterCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" center_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getCenterCd() ) + "'");
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
		MaCenterBean bean = (MaCenterBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("ma_center where ");
		sb.append(" center_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getCenterCd() ) + "'");
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
