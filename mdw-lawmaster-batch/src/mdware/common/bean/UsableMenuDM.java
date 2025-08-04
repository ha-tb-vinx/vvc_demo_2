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
public class UsableMenuDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public UsableMenuDM()
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
		UsableMenuBean bean = new UsableMenuBean();
		bean.setMenuCd( rest.getString("menu_cd") );
		bean.setKouriCd( rest.getString("kouri_cd") );
		bean.setMenuNa( rest.getString("menu_na") );
		bean.setKinoCd( rest.getString("kino_cd") );
		bean.setKinoNa( rest.getString("kino_na") );
		bean.setRiyoUserKb( rest.getString("riyo_user_kb") );
		bean.setDataSyubetuCd( rest.getString("data_syubetu_cd") );
		bean.setDataSyubetuNa( rest.getString("data_syubetu_na") );
		bean.setDataUpDownKb( rest.getString("data_up_down_kb") );
		bean.setHisuMenuCd( rest.getString("hisu_menu_cd") );
		bean.setKengenCd( rest.getString("kengen_cd") );
		bean.setKengenNa( rest.getString("kengen_na") );
		bean.setRiyoUserSyubetuKb( rest.getString("riyo_user_syubetu_kb") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setRiyoUserNa( rest.getString("riyo_user_na") );
		bean.setCompanyNa( rest.getString("company_na") );
		bean.setDepartmentNa( rest.getString("department_na") );
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
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct ");
		sb.append("table1.menu_cd as menu_cd ");
		sb.append(", ");
		sb.append("table1.kouri_cd as kouri_cd ");
		sb.append(", ");
		sb.append("table1.menu_na as menu_na ");
		sb.append(", ");
		sb.append("table1.kino_cd as kino_cd ");
		sb.append(", ");
		sb.append("table1.kino_na as kino_na ");
		sb.append(", ");
		sb.append("table1.riyo_user_kb as riyo_user_kb ");
		sb.append(", ");
		sb.append("table1.data_syubetu_cd as data_syubetu_cd ");
		sb.append(", ");
		sb.append("table1.data_syubetu_na as data_syubetu_na ");
		sb.append(", ");
		sb.append("table1.data_up_down_kb as data_up_down_kb ");
		sb.append(", ");
		sb.append("table1.hisu_menu_cd as hisu_menu_cd ");
		sb.append(", ");
		sb.append("table2.kengen_cd as kengen_cd ");
		sb.append(", ");
		sb.append("table2.kengen_na as kengen_na ");
		sb.append(", ");
		sb.append("table2.riyo_user_syubetu_kb as riyo_user_syubetu_kb ");
		sb.append(", ");
		sb.append("table4.riyo_user_id as riyo_user_id ");
		sb.append(", ");
		sb.append("table4.riyo_user_na as riyo_user_na ");
		sb.append(", ");
		sb.append("table4.company_na as company_na ");
		sb.append(", ");
		sb.append("table4.department_na as department_na ");
		sb.append(" from ");
		sb.append(" r_menu table1 ");
		sb.append(" ,r_kengen table2 ");
		sb.append(" ,r_role_menu_taio table3 ");
		sb.append(" ,r_riyo_user table4 ");
		sb.append(" WHERE ");
		sb.append("table4.kengen_cd = table2.kengen_cd ");
		sb.append("AND ");
		sb.append("table4.kouri_cd = table2.kouri_cd ");
		sb.append("AND ");
		sb.append("table3.kengen_cd = table2.kengen_cd ");
		sb.append("AND ");
		sb.append("table3.kouri_cd = table2.kouri_cd ");
		sb.append("AND ");
		sb.append("table3.menu_cd = table1.menu_cd ");
		sb.append("AND ");
		sb.append("table3.kouri_cd = table1.kouri_cd ");
		if( map.get("menu_cd") != null && ((String)map.get("menu_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.menu_cd = '" + (String)map.get("menu_cd") + "'");
		}
		if( map.get("kouri_cd") != null && ((String)map.get("kouri_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.kouri_cd = '" + (String)map.get("kouri_cd") + "'");
		}
//		if( map.get("menu_na") != null && ((String)map.get("menu_na")).trim().length() > 0 )
//		{
//			sb.append(andStr);
//			sb.append("table1.menu_na = '" + (String)map.get("menu_na") + "'");
//		}
		if( map.get("kino_cd") != null && ((String)map.get("kino_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.kino_cd = '" + (String)map.get("kino_cd") + "'");
		}
//		if( map.get("kino_na") != null && ((String)map.get("kino_na")).trim().length() > 0 )
//		{
//			sb.append(andStr);
//			sb.append("table1.kino_na = '" + (String)map.get("kino_na") + "'");
//		}
		if( map.get("riyo_user_kb") != null && ((String)map.get("riyo_user_kb")).trim().length() > 0 )
		{
			sb.append(andStr);
// 20030104 @rep start A.Tashiro 小売も取引先も使用可能なメニューも存在するので条件に追加
			sb.append("( table1.riyo_user_kb = '" + (String)map.get("riyo_user_kb") + "'");
			sb.append(" Or ");
			sb.append(" riyo_user_kb  = '4') ");
// 20030104 @rep end
         }
		if( map.get("data_syubetu_cd") != null && ((String)map.get("data_syubetu_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.data_syubetu_cd = '" + (String)map.get("data_syubetu_cd") + "'");
		}
//		if( map.get("data_syubetu_na") != null && ((String)map.get("data_syubetu_na")).trim().length() > 0 )
//		{
//			sb.append(andStr);
//			sb.append("table1.data_syubetu_na = '" + (String)map.get("data_syubetu_na") + "'");
//		}
		if( map.get("data_up_down_kb") != null && ((String)map.get("data_up_down_kb")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.data_up_down_kb = '" + (String)map.get("data_up_down_kb") + "'");
		}
		if( map.get("hisu_menu_cd") != null && ((String)map.get("hisu_menu_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table1.hisu_menu_cd = '" + (String)map.get("hisu_menu_cd") + "'");
		}
		if( map.get("kengen_cd") != null && ((String)map.get("kengen_cd")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table2.kengen_cd = '" + (String)map.get("kengen_cd") + "'");
		}
//		if( map.get("kengen_na") != null && ((String)map.get("kengen_na")).trim().length() > 0 )
//		{
//			sb.append(andStr);
//			sb.append("table2.kengen_na = '" + (String)map.get("kengen_na") + "'");
//		}
		if( map.get("riyo_user_syubetu_kb") != null && ((String)map.get("riyo_user_syubetu_kb")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table2.riyo_user_syubetu_kb = '" + (String)map.get("riyo_user_syubetu_kb") + "'");
		}
		if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(andStr);
			sb.append("table4.riyo_user_id = '" + (String)map.get("riyo_user_id") + "'");
		}
//		if( map.get("riyo_user_na") != null && ((String)map.get("riyo_user_na")).trim().length() > 0 )
//		{
//			sb.append(andStr);
//			sb.append("table4.riyo_user_na = '" + (String)map.get("riyo_user_na") + "'");
//		}
//		if( map.get("company_na") != null && ((String)map.get("company_na")).trim().length() > 0 )
//		{
//			sb.append(andStr);
//			sb.append("table4.company_na = '" + (String)map.get("company_na") + "'");
//		}
//		if( map.get("department_na") != null && ((String)map.get("department_na")).trim().length() > 0 )
//		{
//			sb.append(andStr);
//			sb.append("table4.department_na = '" + (String)map.get("department_na") + "'");
//		}
		return sb.toString();
	}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getInsertSql( Object beanMst )
	{
		return null;
	}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getUpdateSql( Object beanMst )
	{
		return null;
	}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getDeleteSql( Object beanMst )
	{
		return null;
	}

}
