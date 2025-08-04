/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングNOマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングNOマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/11)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;
import mdware.common.util.StringUtility;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングNOマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングNOマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/11)初版作成
 */
public class mst580201_TenGroupNoLDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst580201_TenGroupNoLDM()
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
		mst580201_TenGroupNoLBean bean = new mst580201_TenGroupNoLBean();
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		bean.setLGyosyuCd( rest.getString("l_gyosyu_cd") );
//		bean.setYotoKb( rest.getString("yoto_kb") );
		bean.setBumonCd(rest.getString("bumon_cd"));
		bean.setInsertRn(rest.getString("insert_user_rn"));
		bean.setUpdateRn(rest.getString("update_user_rn"));
//		bean.setGroupnoCd( rest.getLong("groupno_cd") );
		bean.setGroupnoCd( rest.getString("groupno_cd") );
//      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
		bean.setNameNa( rest.getString("name_na") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
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
		StringBuffer sb = new StringBuffer();
//      ↓↓移植（2006.05.16）↓↓ 
//      ↑↑移植（2006.05.16）↑↑ 
		sb.append("select \n");
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		sb.append("	l_gyosyu_cd \n");
//		sb.append("	, \n");
		sb.append("	yoto_kb \n");
		sb.append("	, \n");
		sb.append("bumon_cd \n");
		sb.append("	, \n");
		sb.append("tb2.insert_user_rn insert_user_rn ");
		sb.append("	, \n");
		sb.append("tb2.update_user_rn update_user_rn");
		sb.append("	, \n");
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		sb.append("	tb1.groupno_cd as groupno_cd\n");
		sb.append("	, \n");
		sb.append("	name_na \n");
		sb.append("	, \n");
		sb.append("	insert_ts\n");
		sb.append("	, \n");
		sb.append("	insert_user_id \n");
		sb.append("	, \n");
		sb.append("	update_ts\n");
		sb.append("	, \n");
		sb.append("	update_user_id \n");
		sb.append("	, \n");
		sb.append("	nvl(delete_fg ");
		sb.append("	, ");
		sb.append("	'0') as delete_fg ");
		sb.append("from ");
//      ↓↓移植（2006.05.16）↓↓ 		
	    sb.append("	(SELECT " );
	    sb.append("		groupno_cd" );
	    sb.append("	  FROM " );
	    sb.append("		R_GROUPNO_DEFAULT  " );
//      ↑↑移植（2006.05.16）↑↑    
	    sb.append("  ) tb1 \n");
//      ↓↓移植（2006.05.11）↓↓ 
		sb.append(" left outer join ");
		sb.append(" \n");
		sb.append("(select\n");
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		sb.append("	l_gyosyu_cd, \n");
		sb.append("	yoto_kb, \n");
		sb.append(" bumon_cd,\n");
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		sb.append("	groupno_cd,\n");
		sb.append("	name_na,\n");
//      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		sb.append("	insert_ts,\n");
		sb.append("	tp.insert_user_id,\n");
//		sb.append("	update_ts,\n");
		sb.append("	tp.update_user_id,\n");
		sb.append("	tp.insert_ts,\n");
		sb.append("	user1.riyo_user_na insert_user_rn,\n");
		sb.append("	tp.update_ts,\n");
		sb.append("	user2.riyo_user_na update_user_rn,\n");
//      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑		
		sb.append("	delete_fg from \n");
//      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		sb.append("r_tengroupno  where\n");
		sb.append("r_tengroupno tp \n");
//		sb.append("left outer join r_riyo_user  user1 on user1.riyo_user_id =  tp.insert_user_id \n");  
//		sb.append("left outer join r_riyo_user user2 on user2.riyo_user_id =  tp.update_user_id \n");
		sb.append("left outer join r_portal_user user1 on user1.user_id =  TRIM(tp.insert_user_id) \n");  
		sb.append("left outer join r_portal_user user2 on user2.user_id =  TRIM(tp.update_user_id) \n");
		sb.append("where \n");
//      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		sb.append("r_tengroupno where delete_fg = '0'\n");
//		sb.append(" and\n");
//		sb.append("l_gyosyu_cd = '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd") ) + "'\n");
//		sb.append(" and\n");
//		sb.append("yoto_kb = '" + conv.convertWhereString( (String)map.get("yoto_kb") ) + "') tb2\n");
		sb.append("bumon_cd='"+StringUtility.charFormat(conv.convertWhereString( (String)map.get("tx_bumoncd") ),4,"0",true) + "'\n");
		sb.append(" and\n");
		sb.append("yoto_kb='" + mst003901_YotoKbDictionary.HACHU.getCode()+ "') tb2\n");
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		sb.append(" on  tb1.groupno_cd = tb2.groupno_cd \n");
//      ↑↑移植（2006.05.11）↑↑ 
		sb.append("where \n");
/*
		sb.append("											(tb2.l_gyosyu_cd = '0000' ");
		sb.append("											or ");
		sb.append("											tb2.l_gyosyu_cd is null) ");
		sb.append("											and ");
		sb.append("											(tb2.yoto_kb = '0' ");
		sb.append("											or ");
		sb.append("											tb2.yoto_kb is null) ");
		sb.append("											and ");
*/
//      ↓↓移植（2006.05.11）↓↓ 
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓	
//		if( map.get("l_gyosyu_cd") != null && ((String)map.get("l_gyosyu_cd")).trim().length() > 0 )
//		{
		
//		if( map.get("l_gyosyu_cd") != null && ((String)map.get("l_gyosyu_cd")).trim().length() > 0 )
//		{
//			sb.append("(l_gyosyu_cd = '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd") ) + "' or l_gyosyu_cd is null)\n");
//		} else {
//			sb.append("(l_gyosyu_cd is null)\n");
//		}
//      ↑↑移植（2006.05.11）↑↑ 	
//		}
//		if( map.get("yoto_kb") != null && ((String)map.get("yoto_kb")).trim().length() > 0 )
//		{
//			sb.append(" AND \n");
//			sb.append("(yoto_kb = '" + conv.convertWhereString( (String)map.get("yoto_kb") ) + "' or yoto_kb is null)\n");
//		}
		if( map.get("tx_bumoncd") != null && ((String)map.get("tx_bumoncd")).trim().length() > 0 )
		{
			sb.append("(bumon_cd = '"+StringUtility.charFormat(conv.convertWhereString( (String)map.get("tx_bumoncd") ),4,"0",true) + "' or bumon_cd is null )\n");
		}

		sb.append(" AND \n");
		sb.append("(yoto_kb = '"  + mst003901_YotoKbDictionary.HACHU.getCode()+"'or yoto_kb is null )\n");
		sb.append(" ORDER BY GROUPNO_CD");
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
//		sb.append(" ORDER BY GROUPNO_CD");
//      ↓↓移植（2006.05.16）↓↓ 

//      ↑↑移植（2006.05.16）↑↑ 
		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		return null;
	}
	
	public String getWorkTbl()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" DECLARE GLOBAL TEMPORARY TABLE TMP_TR_GROUP_CD ");	
		sb.append(" (groupno_cd                   NUMERIC(5,0) NOT NULL) ON COMMIT DELETE ROWS ");
		return sb.toString();
	
	}
	public String getSelectTmp(){
		StringBuffer sb = new StringBuffer();
		sb.append(" select * ");	
		sb.append( "from ");
		sb.append(" R_GROUPNO_DEFAULT ");
		return sb.toString();
	}
	public String getInsertTmp(int groupcd){
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");	
		sb.append( "R_GROUPNO_DEFAULT ");
		sb.append(" values ");
		sb.append(" ("+groupcd+") ");
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
