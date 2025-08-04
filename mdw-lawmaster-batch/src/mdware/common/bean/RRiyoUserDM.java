package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 * @version 1.1 (2004.09.02) hashimoto ver3.1用改修。
 * @version 1.2 (2005.01.27) morita
 */
public class RRiyoUserDM extends DataModule
{
	public RRiyoUserDM()
	{
		super( "rbsite_ora");
	}
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		RRiyoUserBean bean = new RRiyoUserBean();
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setCompanyNa( rest.getString("company_na") );
		bean.setRiyoUserNa( rest.getString("riyo_user_na") );
		bean.setDepartmentNa( rest.getString("department_na") );
		bean.setKouriCd( rest.getString("kouri_cd") );
		bean.setKengenCd( rest.getString("kengen_cd") );
		bean.setRiyoUserSyubetuKb( rest.getString("riyo_user_syubetu_kb") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
//2005/01/27 morita add start
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setTenhankuCd( rest.getString("tenhanku_cd") );		
//2005/01/27 morita add start
		bean.setPasswordKeyCd( rest.getString("password_key_cd") );
		bean.setYukoKaisiDt( rest.getString("yuko_kaisi_dt") );
		bean.setYukoSyuryoDt( rest.getString("yuko_syuryo_dt") );
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
		sb.append("riyo_user_id,");
		sb.append("company_na,");
		sb.append("riyo_user_na,");
		sb.append("department_na,");
		sb.append("kouri_cd,");
		sb.append("kengen_cd,");
		sb.append("riyo_user_syubetu_kb,");
    	sb.append("tenpo_cd, ");
//2005/01/27 morita add start
		sb.append("s_gyosyu_cd, ");
		sb.append("tenhanku_cd, ");
//2005/01/27 morita add end
		sb.append("password_key_cd,");
		sb.append("yuko_kaisi_dt,");
		sb.append("yuko_syuryo_dt,");
		sb.append("insert_ts,");
		sb.append("update_ts");

		sb.append(" from r_riyo_user ");
		if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id = '" + (String)map.get("riyo_user_id") + "'");
			whereStr = andStr;
		}
		if( map.get("company_na") != null && ((String)map.get("company_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("company_na = '" + singleQuotesfilter((String)map.get("company_na")) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_na") != null && ((String)map.get("riyo_user_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_na = '" + singleQuotesfilter((String)map.get("riyo_user_na")) + "'");
			whereStr = andStr;
		}
		if( map.get("department_na") != null && ((String)map.get("department_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("department_na = '" + singleQuotesfilter((String)map.get("department_na")) + "'");
			whereStr = andStr;
		}

		if( map.get("kouri_cd") != null && ((String)map.get("kouri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kouri_cd = '" + (String)map.get("kouri_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("kengen_cd") != null && ((String)map.get("kengen_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd = '" + (String)map.get("kengen_cd") + "'");
			whereStr = andStr;
		}

		if( map.get("riyo_user_syubetu_kb") != null && ((String)map.get("riyo_user_syubetu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_syubetu_kb = '" + (String)map.get("riyo_user_syubetu_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("password_key_cd") != null && ((String)map.get("password_key_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("password_key_cd = '" + singleQuotesfilter((String)map.get("password_key_cd")) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_kaisi_dt") != null && ((String)map.get("yuko_kaisi_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_kaisi_dt = '" + (String)map.get("yuko_kaisi_dt") + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_syuryo_dt") != null && ((String)map.get("yuko_syuryo_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_syuryo_dt = '" + (String)map.get("yuko_syuryo_dt") + "'");
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
		sb.append(" order by ");
		sb.append("riyo_user_id");
		sb.append(",");
		sb.append("company_na");
		sb.append(",");
		sb.append("riyo_user_na");
		sb.append(",");
		sb.append("department_na");
		sb.append(",");
		sb.append("kouri_cd");
		sb.append(",");
		sb.append("kengen_cd");
		sb.append(",");
		sb.append("riyo_user_syubetu_kb");
		sb.append(",");
		sb.append("password_key_cd");
		sb.append(",");
		sb.append("yuko_kaisi_dt");
		sb.append(",");
		sb.append("yuko_syuryo_dt");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("update_ts");
		return sb.toString();
	}

	public String getInsertSql( Object beanMst )
	{
		RRiyoUserBean bean = (RRiyoUserBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_riyo_user (");
		sb.append(" riyo_user_id");
		sb.append(",");
		sb.append(" company_na");
		sb.append(",");
		sb.append(" riyo_user_na");
		sb.append(",");
		sb.append(" department_na");
		sb.append(",");
		sb.append(" kouri_cd");
		sb.append(",");
		sb.append(" kengen_cd");
		sb.append(",");
		sb.append(" riyo_user_syubetu_kb");
		sb.append(",");
		sb.append(" tenpo_cd ");
		sb.append(",");
//2005/01/27 morita add start
		sb.append(" s_gyosyu_cd");
		sb.append(",");
		sb.append(" tenhanku_cd");
		sb.append(",");
//2005/01/27 morita add end
		sb.append(" password_key_cd");
		sb.append(",");
		sb.append(" yuko_kaisi_dt");
		sb.append(",");
		sb.append(" yuko_syuryo_dt");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getRiyoUserIdString());
		sb.append(",");
		sb.append("'" +  singleQuotesfilter(bean.getCompanyNa()) + "'");
		sb.append(",");
		sb.append( "'" +  singleQuotesfilter(bean.getRiyoUserNa()) + "'");
		sb.append(",");
		sb.append("'" +  singleQuotesfilter(bean.getDepartmentNa()) + "'");
		sb.append(",");
		sb.append( bean.getKouriCdString());
		sb.append(",");
		sb.append( bean.getKengenCdString());
		sb.append(",");
		sb.append( bean.getRiyoUserSyubetuKbString());
		sb.append(",");
		sb.append( bean.getTenpoCdString() );
		sb.append(",");
//2005/01/27 morita add start
		sb.append( bean.getSGyosyuCdString());
		sb.append(",");
		sb.append( bean.getTenhankuCdString());
		sb.append(",");
//2005/01/27 morita add end
		sb.append("'" +  singleQuotesfilter(bean.getPasswordKeyCd()) + "'");
		sb.append(",");
		sb.append( bean.getYukoKaisiDtString());
		sb.append(",");
		sb.append( bean.getYukoSyuryoDtString());
		sb.append(",");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append( bean.getUpdateTsString());
		sb.append(")");
		return sb.toString();
	}

	//  検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		RRiyoUserBean bean = (RRiyoUserBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_riyo_user set ");
		sb.append(" company_na = ");
		sb.append("'" +  singleQuotesfilter(bean.getCompanyNa()) + "'");
		sb.append(",");
		sb.append(" riyo_user_na = ");
		sb.append( "'" +  singleQuotesfilter(bean.getRiyoUserNa()) + "'");
		sb.append(",");
		sb.append(" department_na = ");
		sb.append("'" +  singleQuotesfilter(bean.getDepartmentNa()) + "'");
		sb.append(",");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
		sb.append(",");
		sb.append(" kengen_cd = ");
		sb.append( bean.getKengenCdString());
		sb.append(",");
		sb.append(" riyo_user_syubetu_kb = ");
		sb.append( bean.getRiyoUserSyubetuKbString());
		sb.append(",");
		sb.append(" tenpo_cd = ");
		sb.append( bean.getTenpoCdString() );
		sb.append(",");
//2005/01/27 morita add start
		sb.append(" s_gyosyu_cd = ");
		sb.append( bean.getSGyosyuCdString());
		sb.append(",");
		sb.append(" tenhanku_cd = ");
		sb.append( bean.getTenhankuCdString());
		sb.append(",");
//2005/01/27 morita add end
		sb.append(" password_key_cd = ");
		sb.append("'" + singleQuotesfilter(bean.getPasswordKeyCd()) + "'");
		sb.append(",");
		sb.append(" yuko_kaisi_dt = ");
		sb.append( bean.getYukoKaisiDtString());
		sb.append(",");
		sb.append(" yuko_syuryo_dt = ");
		sb.append( bean.getYukoSyuryoDtString());
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
System.out.println(sb.toString());
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		RRiyoUserBean bean = (RRiyoUserBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_riyo_user where ");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		return sb.toString();
	}

}
