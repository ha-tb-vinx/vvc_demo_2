/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品階層マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius 
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
import mdware.master.common.dictionary.mst003401_KaisoPatternDictionary;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.util.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品階層マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius 
 * @version 1.0(2005/03/24)初版作成
 */
public class mst420101_SyohinKaisoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst420101_SyohinKaisoDM()
	{
		super( mst000101_ConstDictionary.CONNECTION_STR );
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
		mst420101_SyohinKaisoBean bean = new mst420101_SyohinKaisoBean();
		bean.setYukoDt( rest.getString("yuko_dt") );
//		↓↓2006.06.23  shiyue カスタマイズ修正↓↓		
		bean.setSystemKb( rest.getString("system_kb") );
//		↑↑2006.06.23  shiyue カスタマイズ修正↑↑		
		bean.setKaisouPatternKb( rest.getString("kaisou_pattern_kb") );
		bean.setCode1Cd( rest.getString("code1_cd") );
		bean.setCode2Cd( rest.getString("code2_cd") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setDeleteYoyakuFg( rest.getString("delete_yoyaku_fg") );
		bean.setGenYukoDt( rest.getString("yuko_dt") );
		bean.setLowCode( rest.getString("code1_cd") );
		bean.setHighCode( rest.getString("code2_cd") );
		bean.setSentaku(rest.getString("sentaku"));
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select wk.* ");
		sb.append(", ");
		sb.append(mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append(" ,(select count(*) from r_syohinkaiso where kaisou_pattern_kb = wk.kaisou_pattern_kb and code1_cd = wk.code1_cd and yuko_dt > to_char(sysdate,'yyyymmdd') and delete_fg = '1' and rownum = 1) delete_yoyaku_fg ");
//		↓↓移植（2006.05.16）↓↓
//		↓↓2006.06.26  shiyue カスタマイズ修正↓↓			
		sb.append(" ,(select case when count(*) = 0 then 0 when count(*) > 0 then 1 end count from r_syohinkaiso rs where rs.kaisou_pattern_kb = wk.kaisou_pattern_kb and rs.system_kb = wk.system_kb and  rs.code1_cd = wk.code1_cd and rs.yuko_dt > '"+ RMSTDATEUtil.getMstDateDt() +"' and rs.delete_fg = '1' ) delete_yoyaku_fg ");
//		↑↑2006.06.26  shiyue カスタマイズ修正↑↑
		//		↑↑移植（2006.05.16）↑↑ 
// 2006.01.23 Y.Inaba Mod ↑
		sb.append(" from ( ");
		sb.append("    select * ");
		sb.append("    from r_syohinkaiso sk");
		sb.append("    where yuko_dt = (select max(yuko_dt)");
		sb.append("                    from r_syohinkaiso");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append("                   where yuko_dt <= to_char(sysdate,'yyyymmdd')");
		sb.append("                    where yuko_dt <= '" + RMSTDATEUtil.getMstDateDt() + "'");
// 2006.01.23 Y.Inaba Mod ↑
//		↓↓2006.06.26  shiyue カスタマイズ修正↓↓	
		sb.append("                     and system_kb = sk.system_kb");
//		↑↑2006.06.26  shiyue カスタマイズ修正↑↑
		sb.append("                     and code1_cd = sk.code1_cd");
		sb.append("                     and kaisou_pattern_kb = sk.kaisou_pattern_kb)");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append("                     and yuko_dt <= to_char(sysdate,'yyyymmdd')");
		sb.append("    and yuko_dt <= '" + RMSTDATEUtil.getMstDateDt() + "'");
// 2006.01.23 Y.Inaba Mod ↑
		sb.append("    and kaisou_pattern_kb = '").append(conv.convertWhereString((String)map.get("kaisou_pattern_kb"))).append("'");
//		↓↓2006.06.26  shiyue カスタマイズ修正↓↓		
		sb.append("    and system_kb = '").append(conv.convertWhereString((String)map.get("system_kb"))).append("'");
//		↑↑2006.06.26  shiyue カスタマイズ修正↑↑			
		if( map.get("code1_cd") != null && ((String)map.get("code1_cd")).trim().length() > 0 )	{
			sb.append("and code1_cd = '").append(conv.convertWhereString((String)mst000401_LogicBean.formatZero((String)map.get("code1_cd"),4))).append("'");
			//sb.append("and code1_cd = '").append(conv.convertWhereString((String)map.get("code1_cd"))).append("'");
		}
		if( map.get("code2_cd") != null && ((String)map.get("code2_cd")).trim().length() > 0 )	{
			sb.append("and code2_cd = '").append(conv.convertWhereString((String)mst000401_LogicBean.formatZero((String)map.get("code2_cd"),4))).append("'");
		}
		sb.append("    and delete_fg = '0' ");
//		↓↓移植（2006.05.16）↓↓ 
		sb.append("  ) wk ");
		sb.append("    union");
		sb.append(" select wk2.* ");
		sb.append(", ");
		sb.append(mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
//		↓↓2006.06.26  shiyue カスタマイズ修正↓↓			
		sb.append(" ,(select case when count(*) = 0 then 0 when count(*) > 0 then 1 end count from r_syohinkaiso rs where rs.kaisou_pattern_kb = wk2.kaisou_pattern_kb and  rs.system_kb = wk2.system_kb and rs.code1_cd = wk2.code1_cd and rs.yuko_dt > '"+ RMSTDATEUtil.getMstDateDt() +"' and rs.delete_fg = '1' ) delete_yoyaku_fg ");
//		↑↑2006.06.26  shiyue カスタマイズ修正↑↑
		sb.append("  from ( ");
//		↑↑移植（2006.05.16）↑↑ 
		sb.append("    select * ");
		sb.append("    from r_syohinkaiso sk2");
		sb.append("    where yuko_dt = (select min(yuko_dt)");
		sb.append("                     from r_syohinkaiso");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append("                    where yuko_dt > to_char(sysdate,'yyyymmdd')");
		sb.append("                     where yuko_dt > '" + RMSTDATEUtil.getMstDateDt() + "'");
// 2006.01.23 Y.Inaba Mod ↑
//		↓↓2006.06.26  shiyue カスタマイズ修正↓↓		
		sb.append("                     and system_kb = sk2.system_kb");
//		↑↑2006.06.26  shiyue カスタマイズ修正↑↑			
		sb.append("                     and code1_cd = sk2.code1_cd");
		sb.append("                     and kaisou_pattern_kb = sk2.kaisou_pattern_kb)");
//		sb.append("                      and delete_fg = '0')");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append("    and yuko_dt > to_char(sysdate,'yyyymmdd')");
		sb.append("    and yuko_dt > '" + RMSTDATEUtil.getMstDateDt() + "'");
// 2006.01.23 Y.Inaba Mod ↑
//		↓↓2006.06.26  shiyue カスタマイズ修正↓↓		
		sb.append("    and system_kb = '").append(conv.convertWhereString((String)map.get("system_kb"))).append("'");
//		↑↑2006.06.26  shiyue カスタマイズ修正↑↑		
		sb.append("    and kaisou_pattern_kb = '").append(conv.convertWhereString((String)map.get("kaisou_pattern_kb"))).append("'");
		if( map.get("code1_cd") != null && ((String)map.get("code1_cd")).trim().length() > 0 )	{
			sb.append("and code1_cd = '").append(conv.convertWhereString((String)mst000401_LogicBean.formatZero((String)map.get("code1_cd"),4))).append("'");
		}
		if( map.get("code2_cd") != null && ((String)map.get("code2_cd")).trim().length() > 0 )	{
			sb.append("and code2_cd = '").append(conv.convertWhereString((String)mst000401_LogicBean.formatZero((String)map.get("code2_cd"),4))).append("'");
		}
//		sb.append("    and delete_fg = 0");
		sb.append("    and (CODE1_CD) not in (select CODE1_CD");
		sb.append("                           from r_syohinkaiso sk");
		sb.append("                           where yuko_dt = (select max(yuko_dt)");
		sb.append("                                               from r_syohinkaiso");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append("                                              where yuko_dt <= to_char(sysdate,'yyyymmdd')");
		sb.append("                                               where yuko_dt <= '" + RMSTDATEUtil.getMstDateDt() + "'");
// 2006.01.23 Y.Inaba Mod ↑
		sb.append("                                                and code1_cd = sk.code1_cd");
//		↓↓2006.06.26  shiyue カスタマイズ修正↓↓		
		sb.append("                                                and system_kb = sk.system_kb");
//		↑↑2006.06.26  shiyue カスタマイズ修正↑↑		
		sb.append("                                                and kaisou_pattern_kb = sk.kaisou_pattern_kb)");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append("                                                and yuko_dt <= to_char(sysdate,'yyyymmdd')");
		sb.append("                                                and yuko_dt <= '" + RMSTDATEUtil.getMstDateDt() + "'");
// 2006.01.23 Y.Inaba Mod ↑
		sb.append("                                                and kaisou_pattern_kb = '").append(conv.convertWhereString((String)map.get("kaisou_pattern_kb"))).append("'");
//		↓↓2006.06.26  shiyue カスタマイズ修正↓↓		
		sb.append("                                                and system_kb = '").append(conv.convertWhereString((String)map.get("system_kb"))).append("'");
//		↑↑2006.06.26  shiyue カスタマイズ修正↑↑		
		if( map.get("code1_cd") != null && ((String)map.get("code1_cd")).trim().length() > 0 )	{
			sb.append("                                            and code1_cd = '").append(conv.convertWhereString((String)mst000401_LogicBean.formatZero((String)map.get("code1_cd"),4))).append("'");
		}
		if( map.get("code2_cd") != null && ((String)map.get("code2_cd")).trim().length() > 0 )	{
			sb.append("                                            and code2_cd = '").append(conv.convertWhereString((String)mst000401_LogicBean.formatZero((String)map.get("code2_cd"),4))).append("'");
		}
		sb.append("                                                and delete_fg = '0')");
//		↓↓移植（2006.05.16）↓↓ 	
		sb.append("  ) wk2 ");
		sb.append("  order by code1_cd, code2_cd, yuko_dt ");
//		↑↑移植（2006.05.16）↑↑ 
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
		mst420101_SyohinKaisoBean bean = (mst420101_SyohinKaisoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_SYOHINKAISO (");
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" yuko_dt");
		}
		if( bean.getKaisouPatternKb() != null && bean.getKaisouPatternKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kaisou_pattern_kb");
		}
//		  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
		if( bean.getSystemKb() != null && bean.getSystemKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" system_kb");
		}
//		  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
		if( bean.getCode1Cd() != null && bean.getCode1Cd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" code1_cd");						
		}
		if( bean.getCode2Cd() != null && bean.getCode2Cd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" code2_cd");	
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		if( bean.getKaisouPatternKb() != null && bean.getKaisouPatternKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKaisouPatternKb() ) + "'");
		}
//		  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
		if( bean.getSystemKb() != null && bean.getSystemKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSystemKb() ) + "'");
		}
//		  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
		if( bean.getCode1Cd() != null && bean.getCode1Cd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			//sb.append("'" + conv.convertString( bean.getCode1Cd() ) + "'");
			sb.append("'" + conv.convertString( mst000401_LogicBean.formatZero(bean.getCode1Cd(),4) ) + "'");
		}
		if( bean.getCode2Cd() != null && bean.getCode2Cd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( mst000401_LogicBean.formatZero(bean.getCode2Cd(),4) ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
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
		mst420101_SyohinKaisoBean bean = (mst420101_SyohinKaisoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_SYOHINKAISO set ");
		if( bean.getCode2Cd() != null && bean.getCode2Cd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" code2_cd = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.formatZero(bean.getCode2Cd(),4) ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" yuko_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		}
		if( bean.getKaisouPatternKb() != null && bean.getKaisouPatternKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kaisou_pattern_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getKaisouPatternKb() ) + "'");
		}
//		  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
		if( bean.getSystemKb() != null && bean.getSystemKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" system_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getSystemKb() ) + "'");
		}
//		  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
		if( bean.getCode1Cd() != null && bean.getCode1Cd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" code1_cd = ");
			sb.append("'" + conv.convertWhereString( mst000401_LogicBean.formatZero(bean.getCode1Cd(),4) ) + "'");
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
		mst420101_SyohinKaisoBean bean = (mst420101_SyohinKaisoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_SYOHINKAISO where ");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		sb.append(" AND");
		sb.append(" kaisou_pattern_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKaisouPatternKb() ) + "'");
//		  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
		sb.append(" AND");
		sb.append(" system_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getSystemKb() ) + "'");		
//		  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
		sb.append(" AND");
		sb.append(" code1_cd = ");
		sb.append("'" + conv.convertWhereString( mst000401_LogicBean.formatZero(bean.getCode1Cd(),4) ) + "'");
		return sb.toString();
	}

	/**
	 * 予約レコード取得用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getYoyakuSelectSql( Map map )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select yuko_dt ");
		sb.append("       ,kaisou_pattern_kb ");
//		  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
		sb.append("       ,system_kb ");
//		  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
		sb.append("       ,code1_cd ");
		sb.append("       ,code2_cd ");
		sb.append("       ,insert_ts ");
		sb.append("       ,insert_user_id ");
		sb.append("       ,update_ts ");
		sb.append("       ,update_user_id ");
		sb.append("       ,delete_fg ");
		sb.append("  from r_syohinkaiso ");
// 2006.01.23 Y.Inaba Mod ↓
//		sb.append("  where yuko_dt > to_char(sysdate,'yyyymmdd') ");
		sb.append("  where yuko_dt > '" + RMSTDATEUtil.getMstDateDt() + "' ");
//		  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
		sb.append("    and system_kb = '").append(conv.convertWhereString((String)map.get("system_kb"))).append("'");
//		  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
// 2006.01.23 Y.Inaba Mod ↑
		sb.append("    and kaisou_pattern_kb = '").append(conv.convertWhereString((String)map.get("kaisou_pattern_kb"))).append("'");
		sb.append("    and code1_cd = '").append(conv.convertWhereString((String)mst000401_LogicBean.formatZero((String)map.get("code1_cd"),4))).append("'");
		
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
