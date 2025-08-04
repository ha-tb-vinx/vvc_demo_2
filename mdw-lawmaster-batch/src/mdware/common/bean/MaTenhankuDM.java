package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2004/8/3 16:41:19)
 */
public class MaTenhankuDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public MaTenhankuDM()
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
		MaTenhankuBean bean = new MaTenhankuBean();
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenhankuCd( rest.getString("tenhanku_cd") );
		bean.setTenhankuNa( rest.getString("tenhanku_na") );
		bean.setTenhankuKa( rest.getString("tenhanku_ka") );
		bean.setUrikuCd( rest.getString("uriku_cd") );
		bean.setUrikuNa( rest.getString("uriku_na") );
		bean.setUrikuKa( rest.getString("uriku_ka") );
		bean.setInsertTs( rest.getString("insert_ts") );
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
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("tenhanku_cd ");
		sb.append(", ");
		sb.append("tenhanku_na ");
		sb.append(", ");
		sb.append("tenhanku_ka ");
		sb.append(", ");
		sb.append("uriku_cd ");
		sb.append(", ");
		sb.append("uriku_na ");
		sb.append(", ");
		sb.append("uriku_ka ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append("from ma_tenhanku ");


		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd >= '" + conv.convertWhereString( (String)map.get("tenpo_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft") != null && ((String)map.get("tenpo_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd <= '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_like") != null && ((String)map.get("tenpo_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_bef_like") != null && ((String)map.get("tenpo_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft_like") != null && ((String)map.get("tenpo_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_in") != null && ((String)map.get("tenpo_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_not_in") != null && ((String)map.get("tenpo_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenhanku_cd に対するWHERE区
		if( map.get("tenhanku_cd_bef") != null && ((String)map.get("tenhanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd >= '" + conv.convertWhereString( (String)map.get("tenhanku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_aft") != null && ((String)map.get("tenhanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd <= '" + conv.convertWhereString( (String)map.get("tenhanku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd") != null && ((String)map.get("tenhanku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd = '" + conv.convertWhereString( (String)map.get("tenhanku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_like") != null && ((String)map.get("tenhanku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd like '%" + conv.convertWhereString( (String)map.get("tenhanku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_bef_like") != null && ((String)map.get("tenhanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd like '%" + conv.convertWhereString( (String)map.get("tenhanku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_aft_like") != null && ((String)map.get("tenhanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd like '" + conv.convertWhereString( (String)map.get("tenhanku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_in") != null && ((String)map.get("tenhanku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_not_in") != null && ((String)map.get("tenhanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


//		// tenhanku_na に対するWHERE区
//		if( map.get("tenhanku_na_bef") != null && ((String)map.get("tenhanku_na_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_na >= '" + conv.convertWhereString( (String)map.get("tenhanku_na_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_na_aft") != null && ((String)map.get("tenhanku_na_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_na <= '" + conv.convertWhereString( (String)map.get("tenhanku_na_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_na") != null && ((String)map.get("tenhanku_na")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_na = '" + conv.convertWhereString( (String)map.get("tenhanku_na") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_na_like") != null && ((String)map.get("tenhanku_na_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_na like '%" + conv.convertWhereString( (String)map.get("tenhanku_na_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_na_bef_like") != null && ((String)map.get("tenhanku_na_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_na like '%" + conv.convertWhereString( (String)map.get("tenhanku_na_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_na_aft_like") != null && ((String)map.get("tenhanku_na_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_na like '" + conv.convertWhereString( (String)map.get("tenhanku_na_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_na_in") != null && ((String)map.get("tenhanku_na_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_na_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_na_not_in") != null && ((String)map.get("tenhanku_na_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_na_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// tenhanku_ka に対するWHERE区
//		if( map.get("tenhanku_ka_bef") != null && ((String)map.get("tenhanku_ka_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_ka >= '" + conv.convertWhereString( (String)map.get("tenhanku_ka_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_ka_aft") != null && ((String)map.get("tenhanku_ka_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_ka <= '" + conv.convertWhereString( (String)map.get("tenhanku_ka_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_ka") != null && ((String)map.get("tenhanku_ka")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_ka = '" + conv.convertWhereString( (String)map.get("tenhanku_ka") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_ka_like") != null && ((String)map.get("tenhanku_ka_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_ka like '%" + conv.convertWhereString( (String)map.get("tenhanku_ka_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_ka_bef_like") != null && ((String)map.get("tenhanku_ka_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_ka like '%" + conv.convertWhereString( (String)map.get("tenhanku_ka_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_ka_aft_like") != null && ((String)map.get("tenhanku_ka_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_ka like '" + conv.convertWhereString( (String)map.get("tenhanku_ka_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_ka_in") != null && ((String)map.get("tenhanku_ka_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_ka_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("tenhanku_ka_not_in") != null && ((String)map.get("tenhanku_ka_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tenhanku_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_ka_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// uriku_cd に対するWHERE区
//		if( map.get("uriku_cd_bef") != null && ((String)map.get("uriku_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_cd >= '" + conv.convertWhereString( (String)map.get("uriku_cd_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_cd_aft") != null && ((String)map.get("uriku_cd_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_cd <= '" + conv.convertWhereString( (String)map.get("uriku_cd_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_cd") != null && ((String)map.get("uriku_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_cd = '" + conv.convertWhereString( (String)map.get("uriku_cd") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_cd_like") != null && ((String)map.get("uriku_cd_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_cd like '%" + conv.convertWhereString( (String)map.get("uriku_cd_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_cd_bef_like") != null && ((String)map.get("uriku_cd_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_cd like '%" + conv.convertWhereString( (String)map.get("uriku_cd_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_cd_aft_like") != null && ((String)map.get("uriku_cd_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_cd like '" + conv.convertWhereString( (String)map.get("uriku_cd_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_cd_in") != null && ((String)map.get("uriku_cd_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku_cd_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_cd_not_in") != null && ((String)map.get("uriku_cd_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku_cd_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// uriku_na に対するWHERE区
//		if( map.get("uriku_na_bef") != null && ((String)map.get("uriku_na_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_na >= '" + conv.convertWhereString( (String)map.get("uriku_na_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_na_aft") != null && ((String)map.get("uriku_na_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_na <= '" + conv.convertWhereString( (String)map.get("uriku_na_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_na") != null && ((String)map.get("uriku_na")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_na = '" + conv.convertWhereString( (String)map.get("uriku_na") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_na_like") != null && ((String)map.get("uriku_na_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_na like '%" + conv.convertWhereString( (String)map.get("uriku_na_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_na_bef_like") != null && ((String)map.get("uriku_na_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_na like '%" + conv.convertWhereString( (String)map.get("uriku_na_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_na_aft_like") != null && ((String)map.get("uriku_na_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_na like '" + conv.convertWhereString( (String)map.get("uriku_na_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_na_in") != null && ((String)map.get("uriku_na_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku_na_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_na_not_in") != null && ((String)map.get("uriku_na_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku_na_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// uriku_ka に対するWHERE区
//		if( map.get("uriku_ka_bef") != null && ((String)map.get("uriku_ka_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_ka >= '" + conv.convertWhereString( (String)map.get("uriku_ka_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_ka_aft") != null && ((String)map.get("uriku_ka_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_ka <= '" + conv.convertWhereString( (String)map.get("uriku_ka_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_ka") != null && ((String)map.get("uriku_ka")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_ka = '" + conv.convertWhereString( (String)map.get("uriku_ka") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_ka_like") != null && ((String)map.get("uriku_ka_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_ka like '%" + conv.convertWhereString( (String)map.get("uriku_ka_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_ka_bef_like") != null && ((String)map.get("uriku_ka_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_ka like '%" + conv.convertWhereString( (String)map.get("uriku_ka_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_ka_aft_like") != null && ((String)map.get("uriku_ka_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_ka like '" + conv.convertWhereString( (String)map.get("uriku_ka_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_ka_in") != null && ((String)map.get("uriku_ka_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku_ka_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("uriku_ka_not_in") != null && ((String)map.get("uriku_ka_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("uriku_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku_ka_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//
//
//		// insert_ts に対するWHERE区
//		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
		sb.append(" order by ");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("tenhanku_cd");
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
		MaTenhankuBean bean = (MaTenhankuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("ma_tenhanku (");
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getTenhankuCd() != null && bean.getTenhankuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhanku_cd");
		}
		if( bean.getTenhankuNa() != null && bean.getTenhankuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhanku_na");
		}
		if( bean.getTenhankuKa() != null && bean.getTenhankuKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhanku_ka");
		}
		if( bean.getUrikuCd() != null && bean.getUrikuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku_cd");
		}
		if( bean.getUrikuNa() != null && bean.getUrikuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku_na");
		}
		if( bean.getUrikuKa() != null && bean.getUrikuKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku_ka");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}


		sb.append(")Values(");


		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getTenhankuCd() != null && bean.getTenhankuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenhankuCd() ) + "'");
		}
		if( bean.getTenhankuNa() != null && bean.getTenhankuNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenhankuNa() ) + "'");
		}
		if( bean.getTenhankuKa() != null && bean.getTenhankuKa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenhankuKa() ) + "'");
		}
		if( bean.getUrikuCd() != null && bean.getUrikuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUrikuCd() ) + "'");
		}
		if( bean.getUrikuNa() != null && bean.getUrikuNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUrikuNa() ) + "'");
		}
		if( bean.getUrikuKa() != null && bean.getUrikuKa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUrikuKa() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
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
		MaTenhankuBean bean = (MaTenhankuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("ma_tenhanku set ");
		if( bean.getTenhankuNa() != null && bean.getTenhankuNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" tenhanku_na = ");
			sb.append("'" + conv.convertString( bean.getTenhankuNa() ) + "'");
		}
		if( bean.getTenhankuKa() != null && bean.getTenhankuKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhanku_ka = ");
			sb.append("'" + conv.convertString( bean.getTenhankuKa() ) + "'");
		}
		if( bean.getUrikuCd() != null && bean.getUrikuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku_cd = ");
			sb.append("'" + conv.convertString( bean.getUrikuCd() ) + "'");
		}
		if( bean.getUrikuNa() != null && bean.getUrikuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku_na = ");
			sb.append("'" + conv.convertString( bean.getUrikuNa() ) + "'");
		}
		if( bean.getUrikuKa() != null && bean.getUrikuKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku_ka = ");
			sb.append("'" + conv.convertString( bean.getUrikuKa() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getTenhankuCd() != null && bean.getTenhankuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenhanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenhankuCd() ) + "'");
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
		MaTenhankuBean bean = (MaTenhankuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("ma_tenhanku where ");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append(" AND");
		sb.append(" tenhanku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenhankuCd() ) + "'");
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
