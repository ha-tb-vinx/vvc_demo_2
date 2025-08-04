package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mst000601_KoushinInfoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst000601_KoushinInfoDM()
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
		mst000601_KoushinInfoBean bean = new mst000601_KoushinInfoBean();
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setInsertUserName( rest.getString("insert_user_name") );
		bean.setUpdateUserName( rest.getString("update_user_name") );
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
		return null;	
	}
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql( String tbl, List whereList )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//		↓↓2006.06.29 xubq カスタマイズ修正↓↓
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	SUBSTR(TB1.INSERT_TS,1,8) AS INSERT_TS, ");
		sb.append("	SUBSTR(TB1.UPDATE_TS,1,8) AS UPDATE_TS, ");

		sb.append("	NVL(TB2.USER_NA ");
//		sb.append("	NVL(TB2.RIYO_USER_NA   ");
		sb.append("		, ");
//		BUGNO-006 2005.04.20 S.Murata START
//		sb.append("	' ') AS INSERT_USER_NAME ");
		sb.append("	'　') AS INSERT_USER_NAME ");
//		BUGNO-006 2005.04.20 S.Murata END
		sb.append("	, ");
		sb.append("	NVL(TB3.USER_NA ");
//		sb.append("	NVL(TB3.RIYO_USER_NA  ");
		sb.append("		, ");
//		BUGNO-006 2005.04.20 S.Murata START
//		sb.append("	' ') AS UPDATE_USER_NAME ");
		sb.append("	'　') AS UPDATE_USER_NAME ");
//		BUGNO-006 2005.04.20 S.Murata END
		sb.append("FROM ");
		sb.append("	" +  tbl + " TB1 ");
//      ↓↓移植（2006.05.29）↓↓
		sb.append("	left outer join  ");
//		sb.append("	SYS_SOSASYA  TB2 ");
//		sb.append("	r_riyo_user  TB2 ");
//		sb.append("	on TB1.INSERT_USER_ID = TB2.RIYO_USER_ID ");
		sb.append("	R_PORTAL_USER  TB2 ");
		sb.append("	on TRIM(TB1.INSERT_USER_ID) = TB2.USER_ID ");
//		sb.append("	AND ");
//		sb.append("	'" + mst000101_ConstDictionary.HOJIN_CD + "' =  TB2.HOJIN_CD ");		
		sb.append("	left outer join  ");
//		sb.append("	SYS_SOSASYA  TB3 ");
//		sb.append("	r_riyo_user  TB3 ");
//		sb.append("	on TB1.UPDATE_USER_ID = TB3.RIYO_USER_ID ");
		sb.append("	R_PORTAL_USER  TB3 ");
		sb.append("	on TRIM(TB1.UPDATE_USER_ID) = TB3.USER_ID ");
//		sb.append("	AND ");
//		sb.append("	'" + mst000101_ConstDictionary.HOJIN_CD + "' =  TB3.HOJIN_CD ");		
		sb.append("WHERE ");
//		↑↑2006.06.29 xubq カスタマイズ修正↑↑
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
//      ↑↑移植（2006.05.29）↑↑

		return sb.toString();
	}
	
	/**
	 * ユーザー名取得用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getNameSelectSql( String UserId )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
//		↓↓2006.06.29 xubq カスタマイズ修正↓↓		
//		sb.append("SELECT ");
//		sb.append("	NVL(USER_NA ,' ') AS USER_NA ");
//		sb.append("FROM ");
//		sb.append("	SYS_SOSASYA ");
//		sb.append("WHERE ");
//		sb.append(" USER_ID = '" + UserId + "' ");
//		sb.append("	AND ");
//		sb.append(" HOJIN_CD = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");	
//		↑↑2006.06.29 xubq カスタマイズ修正↑↑		
		  sb.append("SELECT ");
//		  sb.append(" NVL(RIYO_USER_NA ,' ') AS user_na ");
		  sb.append(" NVL(USER_NA ,' ') AS user_na ");
		  sb.append("FROM ");
//		  sb.append(" R_RIYO_USER ");
		  sb.append(" R_PORTAL_USER ");
		  sb.append("WHERE ");
//		  sb.append(" RIYO_USER_ID = '" + UserId + "' "); 
		  sb.append(" USER_ID = '" + UserId.trim() + "' "); 

		return sb.toString();
	}
	/**
	 * 対象レコードの状態取得用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getStatusSql( String tbl, List whereList )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append("		INSERT_TS, ");
		sb.append("		UPDATE_TS, ");
		sb.append("		INSERT_USER_ID, ");
		sb.append("		UPDATE_USER_ID, ");
		sb.append("		DELETE_FG ");
		sb.append(" FROM ");
		sb.append("		" +  tbl + " ");
		sb.append(" WHERE ");

		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}

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
