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
public class RKouriDM extends DataModule
{
	public RKouriDM()
	{
		super( "rbsite_ora");
	}
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		RKouriBean bean = new RKouriBean();
		bean.setKouriCd( rest.getString("kouri_cd") );
		bean.setKouriNa( rest.getString("kouri_na") );
		bean.setHostIfKeisikiKb( rest.getString("host_if_keisiki_kb") );
		bean.setDataSaisyoriKb( rest.getString("data_saisyori_kb") );
		bean.setDataHojiKijunDt( rest.getLong("data_hoji_kijun_dt") );
		bean.setDataHojiKikanMn( rest.getLong("data_hoji_kikan_mn") );
// 20021212 @ADD yamanaka V2でDB変更により start
		bean.setDataInputLinesQt( rest.getLong("data_input_lines_qt") );
// 20021212 @ADD yamanaka V2でDB変更により end
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
// 20030201 @ADD tashiro pororoca対応でDB変更により start
		bean.setMasterDownloadKb( rest.getString("master_download_kb") );
// 200302021 @ADD tashiro pororoca対応でDB変更により end
		return bean;
	}

	public String getSelectSql( Map map )
	{
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append("kouri_cd,");
		sb.append("kouri_na,");
		sb.append("host_if_keisiki_kb,");
		sb.append("data_saisyori_kb,");
		sb.append("data_hoji_kijun_dt,");
		sb.append("data_hoji_kikan_mn,");
// 20021212 @ADD yamanaka V2でDB変更により start
		sb.append("data_input_lines_qt,");
// 20021212 @ADD yamanaka V2でDB変更により end
		sb.append("insert_ts,");
// 20030201 @ADD tashiro pororoca対応でDB変更により start
//		sb.append("update_ts");
		sb.append("update_ts,");
		sb.append("master_download_kb");
// 20030201 @ADD tashiro pororoca対応でDB変更により end
		sb.append(" from r_kouri ");
		if( map.get("kouri_cd") != null && ((String)map.get("kouri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kouri_cd = '" + (String)map.get("kouri_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("kouri_na") != null && ((String)map.get("kouri_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kouri_na = '" + (String)map.get("kouri_na") + "'");
			whereStr = andStr;
		}
		if( map.get("host_if_keisiki_kb") != null && ((String)map.get("host_if_keisiki_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("host_if_keisiki_kb = '" + (String)map.get("host_if_keisiki_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("data_saisyori_kb") != null && ((String)map.get("data_saisyori_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_saisyori_kb = '" + (String)map.get("data_saisyori_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("data_hoji_kijun_dt") != null && ((String)map.get("data_hoji_kijun_dt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("data_hoji_kijun_dt = " + (String)map.get("data_hoji_kijun_dt"));
			whereStr = andStr;
		}
		if( map.get("data_hoji_kikan_mn") != null && ((String)map.get("data_hoji_kikan_mn")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("data_hoji_kikan_mn = " + (String)map.get("data_hoji_kikan_mn"));
			whereStr = andStr;
		}
// 20021212 @ADD yamanaka V2でDB変更により start
		if( map.get("data_input_lines_qt") != null && ((String)map.get("data_input_lines_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("data_input_lines_qt = " + (String)map.get("data_input_lines_qt"));
			whereStr = andStr;
		}
// 20021212 @ADD yamanaka V2でDB変更により end
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
// 20030201 @ADD tashiro pororoca対応でDB変更により start
		if( map.get("master_download_kb") != null && ((String)map.get("master_download_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("master_download_kb = '" + (String)map.get("master_download_kb") + "'");
			whereStr = andStr;
		}
// 20030201 @ADD tashiro pororoca対応でDB変更により end
		sb.append(" order by ");
		sb.append("kouri_cd");
		sb.append(",");
		sb.append("kouri_na");
		sb.append(",");
		sb.append("host_if_keisiki_kb");
		sb.append(",");
		sb.append("data_saisyori_kb");
		sb.append(",");
		sb.append("data_hoji_kijun_dt");
		sb.append(",");
		sb.append("data_hoji_kikan_mn");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("update_ts");

		return sb.toString();
	}

	public String getInsertSql( Object beanMst )
	{
		RKouriBean bean = (RKouriBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_kouri (");
		sb.append(" kouri_cd");
		sb.append(",");
		sb.append(" kouri_na");
		sb.append(",");
		sb.append(" host_if_keisiki_kb");
		sb.append(",");
		sb.append(" data_saisyori_kb");
		sb.append(",");
		sb.append(" data_hoji_kijun_dt");
		sb.append(",");
		sb.append(" data_hoji_kikan_mn");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
// 20030201 @ADD tashiro pororoca対応でDB変更により start
		sb.append(",");
		sb.append(" master_download_kb");
// 20030201 @ADD end
		sb.append(")Values(");
		sb.append( bean.getKouriCdString());
		sb.append(",");
		sb.append( bean.getKouriNaString());
		sb.append(",");
		sb.append( bean.getHostIfKeisikiKbString());
		sb.append(",");
		sb.append( bean.getDataSaisyoriKbString());
		sb.append(",");
		sb.append( bean.getDataHojiKijunDtString());
		sb.append(",");
		sb.append( bean.getDataHojiKikanMnString());
		sb.append(",");
// 20021212 @ADD yamanaka V2でDB変更により start
		sb.append("data_input_lines_qt");
		sb.append(",");
// 20021212 @ADD yamanaka V2でDB変更により end
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append( bean.getUpdateTsString());
// 20030201 @ADD tashiro pororoca対応でDB変更により start
		sb.append(",");
		sb.append( bean.getMasterDownloadKbString());
// 20030201 @ADD end
		sb.append(")");
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		RKouriBean bean = (RKouriBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_kouri set ");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
		sb.append(",");
		sb.append(" kouri_na = ");
		sb.append( bean.getKouriNaString());
		sb.append(",");
		sb.append(" host_if_keisiki_kb = ");
		sb.append( bean.getHostIfKeisikiKbString());
		sb.append(",");
		sb.append(" data_saisyori_kb = ");
		sb.append( bean.getDataSaisyoriKbString());
		sb.append(",");
		sb.append(" data_hoji_kijun_dt = ");
		sb.append( bean.getDataHojiKijunDtString());
		sb.append(",");
		sb.append(" data_hoji_kikan_mn = ");
		sb.append( bean.getDataHojiKikanMnString());
		sb.append(",");
// 20021212 @ADD yamanaka V2でDB変更により start
		sb.append(" data_input_lines_qt = ");
		sb.append( bean.getDataInputLinesQtString());
		sb.append(",");
// 20021212 @ADD yamanaka V2でDB変更により end
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
// 20030201 @ADD tashiro pororoca対応でDB変更により start
		sb.append(",");
		sb.append(" master_download_kb = ");
		sb.append( bean.getMasterDownloadKbString());
// 20030201 @ADD end
		sb.append(" where");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
/*		sb.append(" AND");
		sb.append(" kouri_na = ");
		sb.append( bean.getKouriNaString());
		sb.append(" AND");
		sb.append(" host_if_keisiki_kb = ");
		sb.append( bean.getHostIfKeisikiKbString());
		sb.append(" AND");
		sb.append(" data_saisyori_kb = ");
		sb.append( bean.getDataSaisyoriKbString());
		sb.append(" AND");
		sb.append(" data_hoji_kijun_dt = ");
		sb.append( bean.getDataHojiKijunDtString());
		sb.append(" AND");
		sb.append(" data_hoji_kikan_mn = ");
		sb.append( bean.getDataHojiKikanMnString());
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
		RKouriBean bean = (RKouriBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_kouri where ");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
/*		sb.append(" AND");
		sb.append(" kouri_na = ");
		sb.append( bean.getKouriNaString());
		sb.append(" AND");
		sb.append(" host_if_keisiki_kb = ");
		sb.append( bean.getHostIfKeisikiKbString());
		sb.append(" AND");
		sb.append(" data_saisyori_kb = ");
		sb.append( bean.getDataSaisyoriKbString());
		sb.append(" AND");
		sb.append(" data_hoji_kijun_dt = ");
		sb.append( bean.getDataHojiKijunDtString());
		sb.append(" AND");
		sb.append(" data_hoji_kikan_mn = ");
		sb.append( bean.getDataHojiKikanMnString());
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
