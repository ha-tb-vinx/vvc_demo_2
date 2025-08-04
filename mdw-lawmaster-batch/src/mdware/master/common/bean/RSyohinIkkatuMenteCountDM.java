package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.rbsite
 * @version 1.0 K.Tanigawa  (Create time: 2006/9/28 20:37:53)
 */
public class RSyohinIkkatuMenteCountDM extends DataModule
{
/* DM 生成時に使用した SQL 文です。
SELECT
     SUBSTR(T1.TOUROKU_TS, 1,8) AS TOUROKU_TS,
     COUNT(TOUROKU_TS) AS REGISTERED_NUM
FROM
     R_SYOHIN_IKKATU_MENTE T1
    ,SYSTEM_CONTROL T2
WHERE 
     SUBSTR(T1.TOUROKU_TS, 1,8) >= T2.ONLINE_DT
GROUP BY 
     SUBSTR(T1.TOUROKU_TS, 1,8)
ORDER 
     BY TOUROKU_TS
*/
	/**
	 * コンストラクタ
	 */
	public RSyohinIkkatuMenteCountDM()
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
		RSyohinIkkatuMenteCountBean bean = new RSyohinIkkatuMenteCountBean();
		bean.setTourokuTs( rest.getString("touroku_ts") );
		bean.setRegisteredNum( rest.getLong("registered_num") );
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
		sb.append("	SUBSTR(T1.TOUROKU_TS ");
		sb.append("	, ");
		sb.append("	1 ");
		sb.append("	, ");
		sb.append("	8) AS TOUROKU_TS ");
		sb.append("	, ");
		sb.append("	COUNT(TOUROKU_TS) AS REGISTERED_NUM ");
		sb.append("FROM ");
		sb.append("	R_SYOHIN_IKKATU_MENTE T1 ");
		sb.append("	, ");
		sb.append("	SYSTEM_CONTROL T2 ");
		sb.append("WHERE ");
		sb.append("	SUBSTR(T1.TOUROKU_TS ");
		sb.append("	, ");
		sb.append("	1 ");
		sb.append("	, ");
		sb.append("	8) >= T2.MST_DATE_DT ");
		sb.append("GROUP BY ");
		sb.append("	SUBSTR(T1.TOUROKU_TS ");
		sb.append("	, ");
		sb.append("	1 ");
		sb.append("	, ");
		sb.append("	8)  ORDER        BY TOUROKU_TS ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑


		// ↓　ここに条件作成のサンプルを置きますので適切な場所に置いてください。

/*


		// touroku_ts に対するWHERE区
		if( map.get("touroku_ts_bef") != null && ((String)map.get("touroku_ts_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("touroku_ts >= '" + conv.convertWhereString( (String)map.get("touroku_ts_bef") ) + "'");
		}
		if( map.get("touroku_ts_aft") != null && ((String)map.get("touroku_ts_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("touroku_ts <= '" + conv.convertWhereString( (String)map.get("touroku_ts_aft") ) + "'");
		}
		if( map.get("touroku_ts") != null && ((String)map.get("touroku_ts")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("touroku_ts = '" + conv.convertWhereString( (String)map.get("touroku_ts") ) + "'");
		}
		if( map.get("touroku_ts_like") != null && ((String)map.get("touroku_ts_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("touroku_ts like '%" + conv.convertWhereString( (String)map.get("touroku_ts_like") ) + "%'");
		}
		if( map.get("touroku_ts_bef_like") != null && ((String)map.get("touroku_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("touroku_ts like '%" + conv.convertWhereString( (String)map.get("touroku_ts_bef_like") ) + "'");
		}
		if( map.get("touroku_ts_aft_like") != null && ((String)map.get("touroku_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("touroku_ts like '" + conv.convertWhereString( (String)map.get("touroku_ts_aft_like") ) + "%'");
		}
		if( map.get("touroku_ts_in") != null && ((String)map.get("touroku_ts_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("touroku_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("touroku_ts_in") ),",","','") + "' )");
		}
		if( map.get("touroku_ts_not_in") != null && ((String)map.get("touroku_ts_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("touroku_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("touroku_ts_not_in") ),",","','") + "' )");
		}


		// registered_num に対するWHERE区
		if( map.get("registered_num_bef") != null && ((String)map.get("registered_num_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("registered_num >= " + (String)map.get("registered_num_bef") );
		}
		if( map.get("registered_num_aft") != null && ((String)map.get("registered_num_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("registered_num <= " + (String)map.get("registered_num_aft") );
		}
		if( map.get("registered_num") != null && ((String)map.get("registered_num")).trim().length() > 0  )
		{
			sb.append(" AND ");
			sb.append("registered_num = " + (String)map.get("registered_num"));
		}
		if( map.get("registered_num_in") != null && ((String)map.get("registered_num_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("registered_num in ( " + conv.convertWhereString( (String)map.get("registered_num_in") ) + " )");
		}
		if( map.get("registered_num_not_in") != null && ((String)map.get("registered_num_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("registered_num not in ( " + conv.convertWhereString( (String)map.get("registered_num_not_in") ) + " )");
		}
*/

		// ↑　ここに条件作成のサンプルを置きますので適切な場所に置いてください。


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
