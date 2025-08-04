package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;

import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mst640101_SyuukeiSyubetuInfoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst640101_SyuukeiSyubetuInfoDM()
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
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
		mst640101_SyuukeiSyubetuInfoBean bean = new mst640101_SyuukeiSyubetuInfoBean();
		bean.setSyukeiHinsyuCd( rest.getString("syukeihinsyu_cd") );
		bean.setSyukeiHinsyuNa( rest.getString("syukeihinsyu_na") );
		bean.setSyukeiHinsyuKa( rest.getString("syukeihinsyu_ka") );
		bean.setSyukeiSyubetuCd( rest.getString("syukeisyubetu_cd") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setSyorisyaUserId( rest.getString("syorisya_user_id") );
		bean.setSyorisyaUserNa( rest.getString("syorisya_user_na") );
		bean.setGyosyuCd( rest.getString("gyosyu_cd") );
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
		String SelectSql = "";
//		if (map.get("delete_fg") == null || map.get("delete_fg").equals("")) {
//		    SelectSql = getSelectSqlChkDt( map );		    
//		}
		if (map.get("syukei_syubetu_cd") == null || map.get("syukei_syubetu_cd").equals("")) {
		    SelectSql = getSelectSqlHinsyu( map );
		}
		else {
		    SelectSql = getSelectSqlSyubetu( map );
		}
		return SelectSql;
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSqlHinsyu( Map map )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("trim(syukeihinsyu_cd) as syukeihinsyu_cd ");
		sb.append(", ");
		sb.append("syukeihinsyu_na ");
		sb.append(", ");
		sb.append("trim(syukeihinsyu_ka) as syukeihinsyu_ka ");
		sb.append(", ");
		sb.append("trim(syukeisyubetu_cd) as syukeisyubetu_cd ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("syorisya_user_id ");
		sb.append(", ");
		sb.append("table1.user_na as syorisya_user_na ");
		sb.append(", ");
		sb.append("R_SYUKEI_HINSYU.gyosyu_cd as gyosyu_cd ");
		sb.append(" FROM ");
		sb.append(" R_SYUKEI_HINSYU ");
		sb.append(" ,SYS_SOSASYA table1 ");
		sb.append(" WHERE ");
		sb.append(" table1.user_id (+)= syorisya_user_id  ");
		sb.append(" AND table1.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");
		
		// syukeihinsyu_cd に対するWHERE区
		if( map.get("syukeihinsyu_cd_bef") != null && ((String)map.get("syukeihinsyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeihinsyu_cd >= '" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd_bef") ) + "'");
		}
		if( map.get("syukeihinsyu_cd_aft") != null && ((String)map.get("syukeihinsyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeihinsyu_cd <= '" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd_aft") ) + "'");
		}
		if( map.get("syukeihinsyu_cd") != null && ((String)map.get("syukeihinsyu_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeihinsyu_cd = '" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd") ) + "'");
		}
		if( map.get("syukeihinsyu_cd_like") != null && ((String)map.get("syukeihinsyu_cd_like")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeihinsyu_cd like '%" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd_like") ) + "%'");
		}
		if( map.get("syukeihinsyu_cd_bef_like") != null && ((String)map.get("syukeihinsyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeihinsyu_cd like '%" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd_bef_like") ) + "'");
		}
		if( map.get("syukeihinsyu_cd_aft_like") != null && ((String)map.get("syukeihinsyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeihinsyu_cd like '" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd_aft_like") ) + "%'");
		}
		if( map.get("syukeihinsyu_cd_in") != null && ((String)map.get("syukeihinsyu_cd_in")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeihinsyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syukeihinsyu_cd_in") ),",","','") + "' )");
		}
		if( map.get("syukeihinsyu_cd_not_in") != null && ((String)map.get("syukeihinsyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeihinsyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syukeihinsyu_cd_not_in") ),",","','") + "' )");
		}
		
		if( map.get("syukeisyubetu_cd") != null && ((String)map.get("syukeisyubetu_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("syukeisyubetu_cd = '" + conv.convertWhereString( (String)map.get("syukeisyubetu_cd") ) + "'");
		}
		
		if( map.get("gyosyu_cd") != null && ((String)map.get("gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("R_SYUKEI_HINSYU.gyosyu_cd = '" + conv.convertWhereString( (String)map.get("gyosyu_cd") ) + "'");
		}

		sb.append(" ORDER BY  syukeihinsyu_cd ");
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSqlSyubetu( Map map )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("table1.syukei_hinsyu_cd as syukei_hinsyu_cd ");
		sb.append(", ");
		sb.append("table1.kanji_na as kanji_na ");
		sb.append(", ");
		sb.append("table1.kana_na as kana_na ");
		sb.append(", ");
		sb.append("table1.insert_ts as insert_ts ");
		sb.append(", ");
		sb.append("table1.insert_user_id as insert_user_id ");
		sb.append(", ");
		sb.append("table1.update_ts as update_ts ");
		sb.append(", ");
		sb.append("table1.update_user_id as update_user_id ");
		sb.append(", ");
		sb.append("table1.delete_fg as delete_fg ");
		sb.append(", ");
		sb.append("table3.user_na as user_na_insert ");
		sb.append(", ");
		sb.append("table4.user_na as user_na_update ");
		sb.append(" FROM ");
		sb.append(" R_SYUKEIHINSYU table1 ");
		sb.append(" ,R_SYUKEISYUBETU_HINSYU_RENKAN table2 ");
		sb.append(" ,SYS_SOSASYA table3 ");
		sb.append(" ,SYS_SOSASYA table4 ");
		sb.append(" WHERE ");
		sb.append("table2.syukei_syubetu_cd = '" + conv.convertWhereString( (String)map.get("syukei_syubetu_cd") ) + "' ");
		sb.append("AND ");
		sb.append("table2.syukei_hinsyu_cd = table1.syukei_hinsyu_cd ");
		sb.append("AND ");
		sb.append("table1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "' ");
		sb.append("AND ");
		sb.append("table2.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "' ");
		sb.append("AND ");
		sb.append("table3.user_id (+)= table1.insert_user_id ");
		sb.append("AND ");
		sb.append("table4.user_id (+)= table1.update_user_id ");
		sb.append(" AND table3.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");
		sb.append(" AND table4.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");
		
		// syukei_hinsyu_cd に対するWHERE区
		if( map.get("syukei_hinsyu_cd_bef") != null && ((String)map.get("syukei_hinsyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.syukei_hinsyu_cd >= '" + conv.convertWhereString( (String)map.get("syukei_hinsyu_cd_bef") ) + "'");
		}
		if( map.get("syukei_hinsyu_cd_aft") != null && ((String)map.get("syukei_hinsyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.syukei_hinsyu_cd <= '" + conv.convertWhereString( (String)map.get("syukei_hinsyu_cd_aft") ) + "'");
		}
		if( map.get("syukei_hinsyu_cd") != null && ((String)map.get("syukei_hinsyu_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.syukei_hinsyu_cd = '" + conv.convertWhereString( (String)map.get("syukei_hinsyu_cd") ) + "'");
		}
		if( map.get("syukei_hinsyu_cd_like") != null && ((String)map.get("syukei_hinsyu_cd_like")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.syukei_hinsyu_cd like '%" + conv.convertWhereString( (String)map.get("syukei_hinsyu_cd_like") ) + "%'");
		}
		if( map.get("syukei_hinsyu_cd_bef_like") != null && ((String)map.get("syukei_hinsyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.syukei_hinsyu_cd like '%" + conv.convertWhereString( (String)map.get("syukei_hinsyu_cd_bef_like") ) + "'");
		}
		if( map.get("syukei_hinsyu_cd_aft_like") != null && ((String)map.get("syukei_hinsyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.syukei_hinsyu_cd like '" + conv.convertWhereString( (String)map.get("syukei_hinsyu_cd_aft_like") ) + "%'");
		}
		if( map.get("syukei_hinsyu_cd_in") != null && ((String)map.get("syukei_hinsyu_cd_in")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.syukei_hinsyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syukei_hinsyu_cd_in") ),",","','") + "' )");
		}
		if( map.get("syukei_hinsyu_cd_not_in") != null && ((String)map.get("syukei_hinsyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.syukei_hinsyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syukei_hinsyu_cd_not_in") ),",","','") + "' )");
		}

		
		sb.append(" ORDER BY  syukei_hinsyu_cd ");
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSqlChkDt( Map map )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("syukeihinsyu_cd as syukeihinsyu_cd ");
		sb.append(", ");
		sb.append("syukeihinsyu_na as syukeihinsyu_na ");
		sb.append(", ");
		sb.append("syukeihinsyu_ka as syukeihinsyu_ka ");
		sb.append(", ");
		sb.append("syukeisyubetu_cd as syukeisyubetu_cd ");
		sb.append(", ");
		sb.append("insert_ts as insert_ts ");
		sb.append(", ");
		sb.append("update_ts as update_ts ");
		sb.append(", ");
		sb.append("syorisya_user_id as syorisya_user_id ");
		sb.append(", ");
		sb.append("gyosyu_kb as gyosyu_kb ");
		sb.append(" from ");
		sb.append(" R_SYUKEI_HINSYU ");
		sb.append(" WHERE ");
		sb.append("syukeihinsyu_cd = '" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd") ) + "'");
		
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst640101_SyuukeiSyubetuInfoBean bean = (mst640101_SyuukeiSyubetuInfoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_SYUKEI_HINSYU (");
		if( bean.getSyukeiHinsyuCd() != null && bean.getSyukeiHinsyuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" syukeihinsyu_cd");
		}
		if( bean.getSyukeiHinsyuNa() != null && bean.getSyukeiHinsyuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukeihinsyu_na");
		}
		if( bean.getSyukeiHinsyuKa() != null && bean.getSyukeiHinsyuKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukeihinsyu_ka");
		}
		if( bean.getSyukeiSyubetuCd() != null && bean.getSyukeiSyubetuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukeisyubetu_cd");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
		if( bean.getSyorisyaUserId() != null && bean.getSyorisyaUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syorisya_user_id");
		}
		if( bean.getGyosyuCd() != null && bean.getGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gyosyu_cd");
		}


		sb.append(")Values(");


		if( bean.getSyukeiHinsyuCd() != null && bean.getSyukeiHinsyuCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiHinsyuCd() ) + "'");
		}
		if( bean.getSyukeiHinsyuNa() != null && bean.getSyukeiHinsyuNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiHinsyuNa() ) + "'");
		}
		if( bean.getSyukeiHinsyuKa() != null && bean.getSyukeiHinsyuKa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiHinsyuKa() ) + "'");
		}
		if( bean.getSyukeiSyubetuCd() != null && bean.getSyukeiSyubetuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiSyubetuCd() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getSyorisyaUserId() != null && bean.getSyorisyaUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyorisyaUserId() ) + "'");
		}
		if( bean.getGyosyuCd() != null && bean.getGyosyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getGyosyuCd() ) + "'");
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst640101_SyuukeiSyubetuInfoBean bean = (mst640101_SyuukeiSyubetuInfoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_SYUKEI_HINSYU set ");
		if( bean.getSyukeiHinsyuNa() != null && bean.getSyukeiHinsyuNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" syukeihinsyu_na = ");
			sb.append("'" + conv.convertString( bean.getSyukeiHinsyuNa() ) + "'");
		}

//		2006/01/25 kim START 名称（カナ）を入力しなくてもDBで更新できるように修正。
		if( bean.getSyukeiHinsyuKa() != null ) //&& bean.getSyukeiHinsyuKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukeihinsyu_ka = ");
			sb.append("'" + conv.convertString( bean.getSyukeiHinsyuKa() ) + "'");
		}
//		2006/01/25 kim END
		if( bean.getSyukeiSyubetuCd() != null && bean.getSyukeiSyubetuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukeisyubetu_cd = ");
			sb.append("'" + conv.convertString( bean.getSyukeiSyubetuCd() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getSyorisyaUserId() != null && bean.getSyorisyaUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syorisya_user_id = ");
			sb.append("'" + conv.convertString( bean.getSyorisyaUserId() ) + "'");
		}
		if( bean.getGyosyuCd() != null && bean.getGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gyosyu_cd = ");
			sb.append("'" + conv.convertString( bean.getGyosyuCd() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getSyukeiHinsyuCd() != null && bean.getSyukeiHinsyuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" syukeihinsyu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyukeiHinsyuCd() ) + "'");
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst640101_SyuukeiSyubetuInfoBean bean = (mst640101_SyuukeiSyubetuInfoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_SYUKEI_HINSYU where ");
		sb.append(" syukeihinsyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyukeiHinsyuCd() ) + "'");
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
