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
public class RHaisinsakiDM extends DataModule
{
	public RHaisinsakiDM()
	{
		super( "rbsite_ora");
	}
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		RHaisinsakiBean bean = new RHaisinsakiBean();
		bean.setHaisinsakiCd( rest.getString("haisinsaki_cd") );
		bean.setHaisinsakiNa( rest.getString("haisinsaki_na") );
// 20021219 add start A.Tashito for V2
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
// 20021219 add end
		bean.setDataDownloadKahiFg( rest.getString("data_download_kahi_fg") );
		bean.setDenpatuKahiFg( rest.getString("denpatu_kahi_fg") );
		bean.setFileKeisikiKb( rest.getString("file_keisiki_kb") );
		bean.setDataHojiKijunDt( rest.getLong("data_hoji_kijun_dt") );
		bean.setDataHojiKikanMn( rest.getLong("data_hoji_kikan_mn") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setDataMergeKahiKb( rest.getString("data_merge_kahi_kb") );
// 20021225 add start A.Tashito for V2
		bean.setZipKb( rest.getString("zip_kb") );
		bean.setPasswordKeyCd( rest.getString("password_key_cd") );
// 20021225 add end
		bean.setUpdateTs( rest.getString("update_ts") );
		return bean;
	}

	public String getSelectSql( Map map )
	{
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append("haisinsaki_cd,");
		sb.append("haisinsaki_na,");
        sb.append("torihikisaki_cd,");    // 20021219 add  A.Tashito for V2
		sb.append("data_download_kahi_fg,");
		sb.append("denpatu_kahi_fg,");
		sb.append("file_keisiki_kb,");
		sb.append("data_hoji_kijun_dt,");
		sb.append("data_hoji_kikan_mn,");
		sb.append("insert_ts,");
		sb.append("data_merge_kahi_kb,");
// 20021225 add start A.Tashito for V2
		sb.append("zip_kb,");
		sb.append("password_key_cd,");
// 20021225 add end
		sb.append("update_ts");
		sb.append(" from r_haisinsaki ");
//　コメント化　2002/08/08 by e.kato
//		if( map.get("haisinsaki_cd") != null && ((String)map.get("haisinsaki_cd")).trim().length() > 0 )		{
//			sb.append(whereStr);
//			sb.append("haisinsaki_cd = '" + (String)map.get("haisinsaki_cd") + "'");
//			whereStr = andStr;
//		}
		//--- ここから、追加 ---2002/08/08 by e.kato
		if( map.get("lowerequal.haisinsaki_cd") != null && ((String)map.get("lowerequal.haisinsaki_cd")).trim().length() > 0 ) {
			sb.append(whereStr);
			sb.append("haisinsaki_cd >= '" + (String)map.get("lowerequal.haisinsaki_cd") + "'");
			whereStr = andStr;
		}
		else if( map.get("haisinsaki_cd") != null && ((String)map.get("haisinsaki_cd")).trim().length() > 0 )		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd = '" + (String)map.get("haisinsaki_cd") + "'");
			whereStr = andStr;
		}
// 20021219 add start A.Tashiro for V2
		else if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
			whereStr = andStr;
		}
// 20021219 add end
		//--- ここまで、追加 ---2002/08/08 by e.kato

/* コメント化　2002/08/15 by e.kato
		if( map.get("haisinsaki_na") != null && ((String)map.get("haisinsaki_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_na = '" + (String)map.get("haisinsaki_na") + "'");
			whereStr = andStr;
		}
		if( map.get("data_download_kahi_fg") != null && ((String)map.get("data_download_kahi_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_download_kahi_fg = '" + (String)map.get("data_download_kahi_fg") + "'");
			whereStr = andStr;
		}
		if( map.get("denpatu_kahi_fg") != null && ((String)map.get("denpatu_kahi_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpatu_kahi_fg = '" + (String)map.get("denpatu_kahi_fg") + "'");
			whereStr = andStr;
		}
		if( map.get("file_keisiki_kb") != null && ((String)map.get("file_keisiki_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_keisiki_kb = '" + (String)map.get("file_keisiki_kb") + "'");
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
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + (String)map.get("insert_ts") + "'");
			whereStr = andStr;
		}
		if( map.get("data_merge_kahi_kb") != null && ((String)map.get("data_merge_kahi_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_merge_kahi_kb = '" + (String)map.get("data_merge_kahi_kb") + "'");
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
		sb.append("haisinsaki_cd");
		sb.append(",");
		sb.append("haisinsaki_na");
		sb.append(",");
// 20021219 add start A.Tashiro for V2
		sb.append("torihikisaki_cd");
		sb.append(",");
// 20021219 add end
		sb.append("data_download_kahi_fg");
		sb.append(",");
		sb.append("denpatu_kahi_fg");
		sb.append(",");
		sb.append("file_keisiki_kb");
		sb.append(",");
		sb.append("data_hoji_kijun_dt");
		sb.append(",");
		sb.append("data_hoji_kikan_mn");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("data_merge_kahi_kb");
		sb.append(",");
		sb.append("update_ts");
		return sb.toString();
	}

	public String getInsertSql( Object beanMst )
	{
		RHaisinsakiBean bean = (RHaisinsakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_haisinsaki (");
		sb.append(" haisinsaki_cd");
		sb.append(",");
		sb.append(" haisinsaki_na");
		sb.append(",");
// 20021219 add start A.Tashiro for V2
		sb.append("torihikisaki_cd");
		sb.append(",");
// 20021219 add end
		sb.append(" data_download_kahi_fg");
		sb.append(",");
		sb.append(" denpatu_kahi_fg");
		sb.append(",");
		sb.append(" file_keisiki_kb");
		sb.append(",");
		sb.append(" data_hoji_kijun_dt");
		sb.append(",");
		sb.append(" data_hoji_kikan_mn");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" data_merge_kahi_kb");
		sb.append(",");
// 20021225 add start A.Tashiro for V2
		sb.append(" zip_kb");
		sb.append(",");
        sb.append(" password_key_cd");
		sb.append(",");
// 20021225 add end
        sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getHaisinsakiCdString());
		sb.append(",");
		sb.append( bean.getHaisinsakiNaString());
		sb.append(",");
// 20021219 add start A.Tashiro for V2
		sb.append( bean.getTorihikisakiCdString());
		sb.append(",");
// 20021219 add end
		sb.append( bean.getDataDownloadKahiFgString());
		sb.append(",");
		sb.append( bean.getDenpatuKahiFgString());
		sb.append(",");
		sb.append( bean.getFileKeisikiKbString());
		sb.append(",");
		sb.append( bean.getDataHojiKijunDtString());
		sb.append(",");
		sb.append( bean.getDataHojiKikanMnString());
		sb.append(",");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append( bean.getDataMergeKahiKbString());
		sb.append(",");
// 20021225 add start A.Tashiro for V2
		sb.append( bean.getZipKbString());
		sb.append(",");
		sb.append( bean.getPasswordKeyCdString());
		sb.append(",");
// 20021225 add end
		sb.append( bean.getUpdateTsString());
		sb.append(")");
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		RHaisinsakiBean bean = (RHaisinsakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_haisinsaki set ");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
		sb.append(",");
		sb.append(" haisinsaki_na = ");
		sb.append( bean.getHaisinsakiNaString());
		sb.append(",");
		sb.append(" data_download_kahi_fg = ");
		sb.append( bean.getDataDownloadKahiFgString());
		sb.append(",");
		sb.append(" denpatu_kahi_fg = ");
		sb.append( bean.getDenpatuKahiFgString());
		sb.append(",");
		sb.append(" file_keisiki_kb = ");
		sb.append( bean.getFileKeisikiKbString());
		sb.append(",");
		sb.append(" data_hoji_kijun_dt = ");
		sb.append( bean.getDataHojiKijunDtString());
		sb.append(",");
		sb.append(" data_hoji_kikan_mn = ");
		sb.append( bean.getDataHojiKikanMnString());
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" data_merge_kahi_kb = ");
		sb.append( bean.getDataMergeKahiKbString());
// 20021225 add start A.Tashiro for V2
		sb.append(",");
		sb.append(" zip_kb = ");
		sb.append( bean.getZipKbString());
		sb.append(",");
		sb.append(" password_key_cd = ");
		sb.append( bean.getPasswordKeyCdString());
// 20021225 add end
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
/*		sb.append(" AND");
		sb.append(" haisinsaki_na = ");
		sb.append( bean.getHaisinsakiNaString());
		sb.append(" AND");
		sb.append(" data_download_kahi_fg = ");
		sb.append( bean.getDataDownloadKahiFgString());
		sb.append(" AND");
		sb.append(" denpatu_kahi_fg = ");
		sb.append( bean.getDenpatuKahiFgString());
		sb.append(" AND");
		sb.append(" file_keisiki_kb = ");
		sb.append( bean.getFileKeisikiKbString());
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
		sb.append(" data_merge_kahi_kb = ");
		sb.append( bean.getDataMergeKahiKbString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
*/
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		RHaisinsakiBean bean = (RHaisinsakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_haisinsaki where ");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
/*		sb.append(" AND");
		sb.append(" haisinsaki_na = ");
		sb.append( bean.getHaisinsakiNaString());
		sb.append(" AND");
		sb.append(" data_download_kahi_fg = ");
		sb.append( bean.getDataDownloadKahiFgString());
		sb.append(" AND");
		sb.append(" denpatu_kahi_fg = ");
		sb.append( bean.getDenpatuKahiFgString());
		sb.append(" AND");
		sb.append(" file_keisiki_kb = ");
		sb.append( bean.getFileKeisikiKbString());
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
		sb.append(" data_merge_kahi_kb = ");
		sb.append( bean.getDataMergeKahiKbString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
*/
		return sb.toString();
	}

}
