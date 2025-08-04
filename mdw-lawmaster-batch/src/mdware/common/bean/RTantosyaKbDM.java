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
public class RTantosyaKbDM extends DataModule
{
	public RTantosyaKbDM()
	{
		super( "rbsite_ora");
	}
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		RTantosyaKbBean bean = new RTantosyaKbBean();
		bean.setTantosyaKb( rest.getString("tantosya_kb") );
		bean.setTantosyaKbNa( rest.getString("tantosya_kb_na") );
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

		sb.append("tantosya_kb,");
		sb.append("tantosya_kb_na,");
		sb.append("insert_ts,");
		sb.append("update_ts");

		sb.append(" from r_tantosya_kb ");
/****************************************
 * コメント化　2002/08/15 by e.kato
 * 　以降検索（＞＝）に対応
 ***************************************/
//		if( map.get("tantosya_kb") != null && ((String)map.get("tantosya_kb")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tantosya_kb = '" + (String)map.get("tantosya_kb") + "'");
//			whereStr = andStr;
//		}
	//--- ここから、追加 ---2002/08/15 added by e.kato
	if( map.get("lowerequal.tantosya_kb") != null && ((String)map.get("lowerequal.tantosya_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tantosya_kb >= '" + (String)map.get("lowerequal.tantosya_kb") + "'");
			whereStr = andStr;
		}
	else if( map.get("tantosya_kb") != null && ((String)map.get("tantosya_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tantosya_kb = '" + (String)map.get("tantosya_kb") + "'");
			whereStr = andStr;
		}
	//--- ここまで、追加 ---2002/08/15 added by e.kato

/*
		if( map.get("tantosya_kb_na") != null && ((String)map.get("tantosya_kb_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tantosya_kb_na = '" + (String)map.get("tantosya_kb_na") + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + (String)map.get("insert_ts") + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + (String)map.get("update_ts") + "'");
			whereStr = andStr;
		}
*/
		sb.append(" order by ");
		sb.append("tantosya_kb");
		sb.append(",");
		sb.append("tantosya_kb_na");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("update_ts");
		return sb.toString();
	}

	public String getInsertSql( Object beanMst )
	{
		RTantosyaKbBean bean = (RTantosyaKbBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_tantosya_kb (");
		sb.append(" tantosya_kb");
		sb.append(",");
		sb.append(" tantosya_kb_na");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getTantosyaKbString());
		sb.append(",");
// 20030125 @Rep SinguleQuotation A.Tashiro
//		sb.append( bean.getTantosyaKbNaString());
		sb.append("'" +  singleQuotesfilter(bean.getTantosyaKbNa()) + "'");
// 20030125 @Rep end
		sb.append(",");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append( bean.getUpdateTsString());
		sb.append(")");
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		RTantosyaKbBean bean = (RTantosyaKbBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_tantosya_kb set ");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
		sb.append(",");
		sb.append(" tantosya_kb_na = ");
// 20030125 @Rep SingleQuotation A.Tashiro
//		sb.append( bean.getTantosyaKbNaString());
		sb.append("'" + singleQuotesfilter(bean.getTantosyaKbNa()) + "'");
// 20030125 @Rep end
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
/*
		sb.append(" AND");
		sb.append(" tantosya_kb_na = ");
		sb.append( bean.getTantosyaKbNaString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
*/
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		RTantosyaKbBean bean = (RTantosyaKbBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_tantosya_kb where ");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
/*
		sb.append(" AND");
		sb.append(" tantosya_kb_na = ");
		sb.append( bean.getTantosyaKbNaString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
*/
		return sb.toString();
	}

}
