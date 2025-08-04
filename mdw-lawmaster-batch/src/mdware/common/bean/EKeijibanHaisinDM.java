package mdware.common.bean;

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
public class EKeijibanHaisinDM extends DataModule
{
	public EKeijibanHaisinDM()
	{
		super( "rbsite_ora");
	}
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		EKeijibanHaisinBean bean = new EKeijibanHaisinBean();
		bean.setKeijiNb( rest.getLong("keiji_nb") );
		bean.setHaisinsakiCd( rest.getString("haisinsaki_cd") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		return bean;
	}

	public String getSelectSql( Map map )
	{
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append("keiji_nb,");
		sb.append("haisinsaki_cd,");
		sb.append("insert_ts,");
		sb.append("update_ts");

		sb.append(" from e_keijiban_haisin ");
		if( map.get("keiji_nb") != null && ((String)map.get("keiji_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("keiji_nb = " + singleQuotesfilter((String)map.get("keiji_nb")));
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd") != null && ((String)map.get("haisinsaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd = '" + singleQuotesfilter((String)map.get("haisinsaki_cd")) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + singleQuotesfilter((String)map.get("insert_ts")) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + singleQuotesfilter((String)map.get("update_ts")) + "'");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("keiji_nb");
		sb.append(",");
		sb.append("haisinsaki_cd");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("update_ts");
		return sb.toString();
	}

	public String getInsertSql( Object beanMst )
	{
		EKeijibanHaisinBean bean = (EKeijibanHaisinBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("e_keijiban_haisin (");
		sb.append(" keiji_nb");
		sb.append(",");
		sb.append(" haisinsaki_cd");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getKeijiNbString());
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getHaisinsakiCd()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
		sb.append(")");
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		EKeijibanHaisinBean bean = (EKeijibanHaisinBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("e_keijiban_haisin set ");
		sb.append(" keiji_nb = ");
		sb.append( bean.getKeijiNbString());
		sb.append(",");
		sb.append(" haisinsaki_cd = ");
		sb.append( "'" + singleQuotesfilter(bean.getHaisinsakiCd()) + "'");
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
		sb.append(" where");
		sb.append(" keiji_nb = ");
		sb.append( bean.getKeijiNbString());
		sb.append(" AND");
		sb.append(" haisinsaki_cd = ");
		sb.append( "'" + singleQuotesfilter(bean.getHaisinsakiCd()) + "'");
/*		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
*/		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		EKeijibanHaisinBean bean = (EKeijibanHaisinBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("e_keijiban_haisin where ");
		sb.append(" keiji_nb = ");
		sb.append( bean.getKeijiNbString());
		sb.append(" AND");
		sb.append(" haisinsaki_cd = ");
		sb.append( "'" + singleQuotesfilter(bean.getHaisinsakiCd()) + "'");
/*		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
*/		return sb.toString();
	}

}
