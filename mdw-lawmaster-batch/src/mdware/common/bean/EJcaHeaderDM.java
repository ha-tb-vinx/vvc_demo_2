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
 * @version pre1.0 20031218 取引先コードを追加
 */
public class EJcaHeaderDM extends DataModule
{
	private static int sequence = -1;
	private static Object o = new Object();
	/**
	 * 連番を使用しINSERTを行う時はこのメソッドを呼び出してください。
	 * @return 最大の連番＋１を返す。
	 */
	public synchronized int getNextSeq()
	{
		int retSeq = -1;
		synchronized(o)
		{
			if( sequence < 0 )
				sequence = Integer.parseInt(super.getNextSequence("jca_control_nb","e_jca_header"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	public EJcaHeaderDM()
	{
		super( "rbsite_ora");
	}
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		EJcaHeaderBean bean = new EJcaHeaderBean();
		bean.setJcaControlNb( rest.getLong("jca_control_nb") );
		bean.setIdCd( rest.getString("id_cd") );
		bean.setKouriCd( rest.getString("kouri_cd") );
		bean.setHaisinsakiCd( rest.getString("haisinsaki_cd") );
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
		bean.setYokyuKb( rest.getString("yokyu_kb") );
		bean.setDensoDt( rest.getString("denso_dt") );
		bean.setCenterCd( rest.getString("center_cd") );
		bean.setSikibetusiKb( rest.getString("sikibetusi_kb") );
		bean.setDataSyuruiKb( rest.getString("data_syurui_kb") );
		bean.setDataCount1Vl( rest.getLong("data_count_1_vl") );
		bean.setDataCount2Vl( rest.getLong("data_count_2_vl") );
		bean.setSyoriKb( rest.getString("syori_kb") );
		bean.setDataConvertDt( rest.getString("data_convert_dt") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setDatabaseInsertDt( rest.getString("database_insert_dt") );
// 20021211 @ADD yamanaka V2でDB変更により start
		bean.setDataSyubetuCd( rest.getString("data_syubetu_cd") );
// 20021211 @ADD yamanaka V2でDB変更により end
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setFileNa( rest.getString("file_na") );

		return bean;
	}

	public String getSelectSql( Map map )
	{
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append("jca_control_nb,");
		sb.append("id_cd,");
		sb.append("kouri_cd,");
		sb.append("haisinsaki_cd,");
		sb.append("torihikisaki_cd,");
		sb.append("yokyu_kb,");
		sb.append("denso_dt,");
		sb.append("center_cd,");
		sb.append("sikibetusi_kb,");
		sb.append("data_syurui_kb,");
		sb.append("data_count_1_vl,");
		sb.append("data_count_2_vl,");
		sb.append("syori_kb,");
		sb.append("data_convert_dt,");
		sb.append("insert_ts,");
		sb.append("database_insert_dt,");
// 20021211 @ADD yamanaka V2でDB変更により start
		sb.append("data_syubetu_cd,");
// 20021211 @ADD yamanaka V2でDB変更により end
		sb.append("update_ts,");
		sb.append("file_na");

		sb.append(" from e_jca_header ");
		if( map.get("jca_control_nb") != null && ((String)map.get("jca_control_nb")).trim().length() > 0	)
		{
			sb.append(whereStr);
			sb.append("jca_control_nb = " + (String)map.get("jca_control_nb"));
			whereStr = andStr;
		}
		if( map.get("id_cd") != null && ((String)map.get("id_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("id_cd = '" + (String)map.get("id_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("kouri_cd") != null && ((String)map.get("kouri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kouri_cd = '" + (String)map.get("kouri_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd") != null && ((String)map.get("haisinsaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd = '" + (String)map.get("haisinsaki_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("yokyu_kb") != null && ((String)map.get("yokyu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yokyu_kb = '" + (String)map.get("yokyu_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("denso_dt") != null && ((String)map.get("denso_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denso_dt = '" + (String)map.get("denso_dt") + "'");
			whereStr = andStr;
		}
		if( map.get("center_cd") != null && ((String)map.get("center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_cd = '" + (String)map.get("center_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("sikibetusi_kb") != null && ((String)map.get("sikibetusi_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sikibetusi_kb = '" + (String)map.get("sikibetusi_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("data_syurui_kb") != null && ((String)map.get("data_syurui_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_syurui_kb = '" + (String)map.get("data_syurui_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("data_count_1_vl") != null && ((String)map.get("data_count_1_vl")).trim().length() > 0	)
		{
			sb.append(whereStr);
			sb.append("data_count_1_vl = " + (String)map.get("data_count_1_vl"));
			whereStr = andStr;
		}
		if( map.get("data_count_2_vl") != null && ((String)map.get("data_count_2_vl")).trim().length() > 0	)
		{
			sb.append(whereStr);
			sb.append("data_count_2_vl = " + (String)map.get("data_count_2_vl"));
			whereStr = andStr;
		}
		if( map.get("syori_kb") != null && ((String)map.get("syori_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syori_kb = '" + (String)map.get("syori_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("data_convert_dt") != null && ((String)map.get("data_convert_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_convert_dt = '" + (String)map.get("data_convert_dt") + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + (String)map.get("insert_ts") + "'");
			whereStr = andStr;
		}
		if( map.get("database_insert_dt") != null && ((String)map.get("database_insert_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("database_insert_dt = '" + (String)map.get("database_insert_dt") + "'");
			whereStr = andStr;
		}
// 20021211 @ADD yamanaka V2でDB変更により start
		if( map.get("data_syubetu_cd") != null && ((String)map.get("data_syubetu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_syubetu_cd = '" + (String)map.get("data_syubetu_cd") + "'");
			whereStr = andStr;
		}
// 20021211 @ADD yamanaka V2でDB変更により end
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + (String)map.get("update_ts") + "'");
			whereStr = andStr;
		}
		if( map.get("file_na") != null && ((String)map.get("file_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_na = '" + (String)map.get("file_na") + "'");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("jca_control_nb");
		sb.append(",");
		sb.append("id_cd");
		sb.append(",");
		sb.append("kouri_cd");
		sb.append(",");
		sb.append("haisinsaki_cd");
		sb.append(",");
		sb.append("torihikisaki_cd");
		sb.append(",");
		sb.append("yokyu_kb");
		sb.append(",");
		sb.append("denso_dt");
		sb.append(",");
		sb.append("center_cd");
		sb.append(",");
		sb.append("sikibetusi_kb");
		sb.append(",");
		sb.append("data_syurui_kb");
		sb.append(",");
		sb.append("data_count_1_vl");
		sb.append(",");
		sb.append("data_count_2_vl");
		sb.append(",");
		sb.append("syori_kb");
		sb.append(",");
		sb.append("data_convert_dt");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("database_insert_dt");
// 20021211 @ADD yamanaka V2でDB変更により start
		sb.append(",");
		sb.append("data_syubetu_cd");
// 20021211 @ADD yamanaka V2でDB変更により end
		sb.append(",");
		sb.append("update_ts");
		sb.append(",");
		sb.append("file_na");
		return sb.toString();
	}

	public String getInsertSql( Object beanMst )
	{
		EJcaHeaderBean bean = (EJcaHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("e_jca_header (");
		sb.append(" jca_control_nb");
		sb.append(",");
		sb.append(" id_cd");
		sb.append(",");
		sb.append(" kouri_cd");
		sb.append(",");
		sb.append(" haisinsaki_cd");
		sb.append(",");
		sb.append(" torihikisaki_cd");
		sb.append(",");
		sb.append(" yokyu_kb");
		sb.append(",");
		sb.append(" denso_dt");
		sb.append(",");
		sb.append(" center_cd");
		sb.append(",");
		sb.append(" sikibetusi_kb");
		sb.append(",");
		sb.append(" data_syurui_kb");
		sb.append(",");
		sb.append(" data_count_1_vl");
		sb.append(",");
		sb.append(" data_count_2_vl");
		sb.append(",");
		sb.append(" data_convert_dt");
		sb.append(",");
		sb.append(" database_insert_dt");
		sb.append(",");
		sb.append(" syori_kb");
// 20021211 @ADD yamanaka V2でDB変更により start
		sb.append(",");
		sb.append(" data_syubetu_cd");
// 20021211 @ADD yamanaka V2でDB変更により end
		sb.append(",");
   		sb.append(" file_na");
	   	sb.append(",");
  		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getJcaControlNbString());
		sb.append(",");
		sb.append( bean.getIdCdString());
		sb.append(",");
		sb.append( bean.getKouriCdString());
		sb.append(",");
		sb.append( bean.getHaisinsakiCdString());
		sb.append(",");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(",");
		sb.append( bean.getYokyuKbString());
		sb.append(",");
		sb.append( bean.getDensoDtString());
		sb.append(",");
		sb.append( bean.getCenterCdString());
		sb.append(",");
		sb.append( bean.getSikibetusiKbString());
		sb.append(",");
		sb.append( bean.getDataSyuruiKbString());
		sb.append(",");
		sb.append( bean.getDataCount1VlString());
		sb.append(",");
		sb.append( bean.getDataCount2VlString());
		sb.append(",");
		sb.append( bean.getDataConvertDtString());
		sb.append(",");
		sb.append( "TO_CHAR(sysdate, 'YYYYMMDDHH24MISS')");
		sb.append(",");
		sb.append( bean.getSyoriKbString());
// 20021211 @ADD yamanaka V2でDB変更により start
		sb.append(",");
		sb.append( bean.getDataSyubetuCdString());
// 20021211 @ADD yamanaka V2でDB変更により end
   		sb.append(",");
	   	sb.append( bean.getFileNaString());
// sb.append( bean.getInsertTsString());
	   	sb.append(",");
	   	sb.append( "TO_CHAR(sysdate, 'YYYYMMDDHH24MISS')");
		sb.append(",");
//		sb.append( bean.getUpdateTsString());
		sb.append( "TO_CHAR(sysdate, 'YYYYMMDDHH24MISS')");
		sb.append(")");
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		EJcaHeaderBean bean = (EJcaHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("e_jca_header set ");
		/*sb.append(" jca_control_nb = ");
		sb.append( bean.getJcaControlNbString());
		sb.append(",");
		sb.append(" id_cd = ");
		sb.append( bean.getIdCdString());
		sb.append(",");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
		sb.append(",");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
		sb.append(",");
		sb.append(" yokyu_kb = ");
		sb.append( bean.getYokyuKbString());
		sb.append(",");
		sb.append(" denso_dt = ");
		sb.append( bean.getDensoDtString());
		sb.append(",");
		sb.append(" center_cd = ");
		sb.append( bean.getCenterCdString());
		sb.append(",");
		sb.append(" sikibetusi_kb = ");
		sb.append( bean.getSikibetusiKbString());
		sb.append(",");
		sb.append(" data_syurui_kb = ");
		sb.append( bean.getDataSyuruiKbString());
		sb.append(",");
		sb.append(" data_count_1_vl = ");
		sb.append( bean.getDataCount1VlString());
		sb.append(",");
		sb.append(" data_count_2_vl = ");
		sb.append( bean.getDataCount2VlString());
		sb.append(",");
		sb.append(" syori_kb = ");
		sb.append( bean.getSyoriKbString());
		sb.append(",");
		sb.append(" data_convert_dt = ");
		sb.append( bean.getDataConvertDtString());
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" database_insert_dt = ");
		sb.append( bean.getDatabaseInsertDtString());
		sb.append(",");*/
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		/*sb.append(",");
		sb.append(" file_na = ");
		sb.append( bean.getFileNaString());*/
		sb.append(" where");
		sb.append(" jca_control_nb = ");
		sb.append( bean.getJcaControlNbString());
		/*sb.append(" AND");
		sb.append(" id_cd = ");
		sb.append( bean.getIdCdString());
		sb.append(" AND");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
		sb.append(" AND");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
		sb.append(" AND");
		sb.append(" yokyu_kb = ");
		sb.append( bean.getYokyuKbString());
		sb.append(" AND");
		sb.append(" denso_dt = ");
		sb.append( bean.getDensoDtString());
		sb.append(" AND");
		sb.append(" center_cd = ");
		sb.append( bean.getCenterCdString());
		sb.append(" AND");
		sb.append(" sikibetusi_kb = ");
		sb.append( bean.getSikibetusiKbString());
		sb.append(" AND");
		sb.append(" data_syurui_kb = ");
		sb.append( bean.getDataSyuruiKbString());
		sb.append(" AND");
		sb.append(" data_count_1_vl = ");
		sb.append( bean.getDataCount1VlString());
		sb.append(" AND");
		sb.append(" data_count_2_vl = ");
		sb.append( bean.getDataCount2VlString());
		sb.append(" AND");
		sb.append(" syori_kb = ");
		sb.append( bean.getSyoriKbString());
		sb.append(" AND");
		sb.append(" data_convert_dt = ");
		sb.append( bean.getDataConvertDtString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" database_insert_dt = ");
		sb.append( bean.getDatabaseInsertDtString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" AND");
		sb.append(" file_na = ");
		sb.append( bean.getFileNaString());*/
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		EJcaHeaderBean bean = (EJcaHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("e_jca_header where ");
		sb.append(" jca_control_nb = ");
		sb.append( bean.getJcaControlNbString());
/*
		sb.append(" AND");
		sb.append(" id_cd = ");
		sb.append( bean.getIdCdString());
		sb.append(" AND");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
		sb.append(" AND");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
		sb.append(" AND");
		sb.append(" yokyu_kb = ");
		sb.append( bean.getYokyuKbString());
		sb.append(" AND");
		sb.append(" denso_dt = ");
		sb.append( bean.getDensoDtString());
		sb.append(" AND");
		sb.append(" center_cd = ");
		sb.append( bean.getCenterCdString());
		sb.append(" AND");
		sb.append(" sikibetusi_kb = ");
		sb.append( bean.getSikibetusiKbString());
		sb.append(" AND");
		sb.append(" data_syurui_kb = ");
		sb.append( bean.getDataSyuruiKbString());
		sb.append(" AND");
		sb.append(" data_count_1_vl = ");
		sb.append( bean.getDataCount1VlString());
		sb.append(" AND");
		sb.append(" data_count_2_vl = ");
		sb.append( bean.getDataCount2VlString());
		sb.append(" AND");
		sb.append(" syori_kb = ");
		sb.append( bean.getSyoriKbString());
		sb.append(" AND");
		sb.append(" data_convert_dt = ");
		sb.append( bean.getDataConvertDtString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" database_insert_dt = ");
		sb.append( bean.getDatabaseInsertDtString());
// 20021211 @ADD yamanaka V2でDB変更により start
		sb.append(" AND");
		sb.append(" data_syubetu_cd = ");
		sb.append( bean.getDataSyubetuCdString());
// 20021211 @ADD yamanaka V2でDB変更により end
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" AND");
		sb.append(" file_na = ");
		sb.append( bean.getFileNaString());
*/
		return sb.toString();
	}

}
