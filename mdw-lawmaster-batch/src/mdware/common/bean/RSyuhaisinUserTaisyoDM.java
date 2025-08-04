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
// 20020821 yoshi getUpdateSql, getDeleteSqlの条件文などを修正
public class RSyuhaisinUserTaisyoDM extends DataModule
{
	public RSyuhaisinUserTaisyoDM()
	{
		super( "rbsite_ora");
	}
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		RSyuhaisinUserTaisyoBean bean = new RSyuhaisinUserTaisyoBean();
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
// 20021206 @DEL yamanaka V2でDB変更により start
//		bean.setHaisinsakiNb( rest.getLong("haisinsaki_nb") );
// 20021206 @DEL yamanaka V2でDB変更により end
		bean.setHaisinsakiCd( rest.getString("haisinsaki_cd") );
// 20030210 @UPD tashiro V2でDB変更により start
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
// 20030210 @UPD end
		bean.setKouriCd( rest.getString("kouri_cd") );
		bean.setTantosyaKb( rest.getString("tantosya_kb") );
// 20030210 @UPD tashiro V2でDB変更により start
//		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
// 20030210 @UPD end
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
// 20021206 @DEL yamanaka V2でDB変更により start
//		sb.append("haisinsaki_nb,");
// 20021206 @DEL yamanaka V2でDB変更により end
		sb.append("haisinsaki_cd,");
// 20030210 @UPD tashiro V2でDB変更により start
		sb.append("torihikisaki_cd,");
// 20030210 @UPD end
		sb.append("kouri_cd,");
		sb.append("tantosya_kb,");
// 20030210 @UPD tashiro V2でDB変更により start
//		sb.append("torihikisaki_cd,");
// 20030210 @UPD end
		sb.append("insert_ts,");
		sb.append("update_ts");

		sb.append(" from r_syuhaisin_user_taisyo ");
		if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id = '" + (String)map.get("riyo_user_id") + "'");
			whereStr = andStr;
		}
// 20021206 @DEL yamanaka V2でDB変更により start
//		if( map.get("haisinsaki_nb") != null && ((String)map.get("haisinsaki_nb")).trim().length() > 0	)
//		{
//			sb.append(whereStr);
//			sb.append("haisinsaki_nb = " + (String)map.get("haisinsaki_nb"));
//			whereStr = andStr;
//		}
// 20021206 @DEL yamanaka V2でDB変更により end
		if( map.get("haisinsaki_cd") != null && ((String)map.get("haisinsaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd = '" + (String)map.get("haisinsaki_cd") + "'");
			whereStr = andStr;
		}
// 20030210 @UPD tashiro V2でDB変更により start
		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
			whereStr = andStr;
		}
// 20030210 @UPD end
		if( map.get("kouri_cd") != null && ((String)map.get("kouri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kouri_cd = '" + (String)map.get("kouri_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("tantosya_kb") != null && ((String)map.get("tantosya_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tantosya_kb = '" + (String)map.get("tantosya_kb") + "'");
			whereStr = andStr;
		}
// 20030210 @UPD tashiro V2でDB変更により start
//		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
//			whereStr = andStr;
//		}
// 20030210 @UPD end
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
// 20021206 @DEL yamanaka V2でDB変更により start
//		sb.append("haisinsaki_nb");
//		sb.append(",");
// 20021206 @DEL yamanaka V2でDB変更により end
		sb.append("haisinsaki_cd");
// 20030210 @UPD tashiro V2でDB変更により start
		sb.append(",");
		sb.append("torihikisaki_cd");
// 20030210 @UPD end
    	sb.append(",");
		sb.append("kouri_cd");
		sb.append(",");
		sb.append("tantosya_kb");
// 20030210 @UPD tashiro V2でDB変更により start
//		sb.append(",");
//		sb.append("torihikisaki_cd");
// 20030210 @UPD end
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("update_ts");
		return sb.toString();
	}

	public String getInsertSql( Object beanMst )
	{
		RSyuhaisinUserTaisyoBean bean = (RSyuhaisinUserTaisyoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_syuhaisin_user_taisyo (");
		sb.append(" riyo_user_id");
		sb.append(",");
// 20021206 @DEL yamanaka V2でDB変更により start
//		sb.append(" haisinsaki_nb");
//		sb.append(",");
// 20021206 @DEL yamanaka V2でDB変更により end
		sb.append(" haisinsaki_cd");
// 20030210 @UPD tashiro V2でDB変更により start
		sb.append(",");
		sb.append(" torihikisaki_cd");
// 20030210 @UPD tashiro end
		sb.append(",");
		sb.append(" kouri_cd");
		sb.append(",");
		sb.append(" tantosya_kb");
// 20030210 @UPD tashiro V2でDB変更により start
//		sb.append(",");
//		sb.append(" torihikisaki_cd");
// 20030210 @UPD tashiro end
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getRiyoUserIdString());
		sb.append(",");
// 20021206 @DEL yamanaka V2でDB変更により start
//		sb.append( bean.getHaisinsakiNbString());
//		sb.append(",");
// 20021206 @DEL yamanaka V2でDB変更により end
		sb.append( bean.getHaisinsakiCdString());
// 20030210 @UPD tashiro V2でDB変更により start
		sb.append(",");
		sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
		sb.append(",");
		sb.append( bean.getKouriCdString());
		sb.append(",");
		sb.append( bean.getTantosyaKbString());
// 20030210 @UPD tashiro V2でDB変更により start
//		sb.append(",");
//		sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
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
		RSyuhaisinUserTaisyoBean bean = (RSyuhaisinUserTaisyoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_syuhaisin_user_taisyo set ");
// 20020821 yoshi getUpdateSql, getDeleteSqlの条件文などを修正
/*
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(",");
		sb.append(" haisinsaki_nb = ");
		sb.append( bean.getHaisinsakiNbString());
		sb.append(",");
*/
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
// 20030210 @UPD tashiro V2でDB変更により start
		sb.append(",");
		sb.append(" torihikisaki_cd = ");
        sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
		sb.append(",");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
		sb.append(",");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
// 20030210 @UPD tashiro V2でDB変更により start
//		sb.append(",");
//		sb.append(" torihikisaki_cd = ");
//		sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
// 20021206 @DEL yamanaka V2でDB変更により start
//		sb.append(" AND");
//		sb.append(" haisinsaki_nb = ");
//		sb.append( bean.getHaisinsakiNbString());
// 20021206 @DEL yamanaka V2でDB変更により end

// 20020821 yoshi getUpdateSql, getDeleteSqlの条件文などを修正
/*
		sb.append(" AND");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
		sb.append(" AND");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
		sb.append(" AND");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
		sb.append(" AND");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
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
		RSyuhaisinUserTaisyoBean bean = (RSyuhaisinUserTaisyoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_syuhaisin_user_taisyo where ");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());

// 20021206 @UPD yamanaka V2でDB変更により start

// 20020821 yoshi getUpdateSql, getDeleteSqlの条件文などを修正
//		if( bean.getHaisinsakiNb() >= 0 )
//		{
//			sb.append(" AND");
//			sb.append(" haisinsaki_nb = ");
//			sb.append( bean.getHaisinsakiNbString());
//		}
		if( bean.getHaisinsakiCd().trim().length() > 0 )
		{
			sb.append(" AND");
			sb.append(" haisinsaki_cd = ");
			sb.append( bean.getHaisinsakiCdString());
		}
// 20020821 yoshi getUpdateSql, getDeleteSqlの条件文などを修正

// 20021206 @UPD yamanaka V2でDB変更により end
/*
		sb.append(" AND");
		sb.append(" haisinsaki_nb = ");
		sb.append( bean.getHaisinsakiNbString());
		sb.append(" AND");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
		sb.append(" AND");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
		sb.append(" AND");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
		sb.append(" AND");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
*/
		return sb.toString();
	}

// 20030116 @ADD A.Tashiro 担当者区分メンテナンス時の更新のため
	public String getDeleteSqlfromCharge( Object beanMst )
	{
		RSyuhaisinUserTaisyoBean bean = (RSyuhaisinUserTaisyoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_syuhaisin_user_taisyo where ");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
    	return sb.toString();
	}
// 20030116 @ADD END
}
