package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/9/29 03:38:29)
 * @version 1.0 (Create time: 2006/9/29 03:38:29) 障害票№0086たおいう K.Tanigawa
 */
public class RSyohinIkkatuMenteNextDayDM extends DataModule
{
/* DM 生成時に使用した SQL 文です。
SELECT 
    TO_CHAR(TO_DATE(SUBSTR(max(T1.TOUROKU_TS), 1,8)) + 1 DAY, 'YYYYMMDD') AS NEXTDAY
FROM 
    R_SYOHIN_IKKATU_MENTE T1
*/
	/**
	 * コンストラクタ
	 */
	public RSyohinIkkatuMenteNextDayDM()
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
		RSyohinIkkatuMenteNextDayBean bean = new RSyohinIkkatuMenteNextDayBean();
		bean.setNextday( rest.getString("nextday") );
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
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	TO_CHAR ");
		sb.append("	( ");
		sb.append("			TO_DATE ");
		sb.append("			( ");
		sb.append("					SUBSTR ");
		sb.append("					( ");
		sb.append("							max(T1.TOUROKU_TS) ");
		sb.append("							, ");
		sb.append("							1 ");
		sb.append("							, ");
		sb.append("							8 ");
		sb.append("					) ");
		sb.append("			) ");
		sb.append("			+ 1 DAY ");
		sb.append("			, ");
		sb.append("			'YYYYMMDD' ");
		sb.append("	) ");
		sb.append("	AS NEXTDAY ");
		sb.append("FROM ");
		sb.append("	R_SYOHIN_IKKATU_MENTE T1 ");


		// ↓　ここに条件作成のサンプルを置きますので適切な場所に置いてください。

/*


		// nextday に対するWHERE区
		if( map.get("nextday_bef") != null && ((String)map.get("nextday_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("nextday >= '" + conv.convertWhereString( (String)map.get("nextday_bef") ) + "'");
		}
		if( map.get("nextday_aft") != null && ((String)map.get("nextday_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("nextday <= '" + conv.convertWhereString( (String)map.get("nextday_aft") ) + "'");
		}
		if( map.get("nextday") != null && ((String)map.get("nextday")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("nextday = '" + conv.convertWhereString( (String)map.get("nextday") ) + "'");
		}
		if( map.get("nextday_like") != null && ((String)map.get("nextday_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("nextday like '%" + conv.convertWhereString( (String)map.get("nextday_like") ) + "%'");
		}
		if( map.get("nextday_bef_like") != null && ((String)map.get("nextday_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("nextday like '%" + conv.convertWhereString( (String)map.get("nextday_bef_like") ) + "'");
		}
		if( map.get("nextday_aft_like") != null && ((String)map.get("nextday_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("nextday like '" + conv.convertWhereString( (String)map.get("nextday_aft_like") ) + "%'");
		}
		if( map.get("nextday_in") != null && ((String)map.get("nextday_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("nextday in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nextday_in") ),",","','") + "' )");
		}
		if( map.get("nextday_not_in") != null && ((String)map.get("nextday_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("nextday not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nextday_not_in") ),",","','") + "' )");
		}
*/

		// ↑　ここに条件作成のサンプルを置きますので適切な場所に置いてください。

//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		return null;
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
