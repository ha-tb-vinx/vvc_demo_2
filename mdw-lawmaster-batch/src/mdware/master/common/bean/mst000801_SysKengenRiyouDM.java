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
public class mst000801_SysKengenRiyouDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst000801_SysKengenRiyouDM()
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
		mst000801_SysKengenRiyouBean bean = new mst000801_SysKengenRiyouBean();
		bean.setKengenCd( rest.getString("kengen_cd") );
		bean.setGamenId( rest.getString("gamen_id") );
		bean.setSubmenuId( rest.getString("submenu_id") );
		bean.setGyouNo( rest.getString("gyou_no") );
		bean.setRetuNo( rest.getString("retu_no") );
		bean.setInsertOkKb( rest.getString("insert_ok_kb") );
		bean.setUpdateOkKb( rest.getString("update_ok_kb") );
		bean.setDeleteOkKb( rest.getString("delete_ok_kb") );
		bean.setReferenceOkKb( rest.getString("reference_ok_kb") );
		bean.setCsvOkKb( rest.getString("csv_ok_kb") );
		bean.setPrintOkKb( rest.getString("print_ok_kb") );
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
		sb.append("	SELECT ");
		sb.append("		KENGEN_CD,");
		sb.append("		GAMEN_ID,");
		sb.append("		SUBMENU_ID,");
		sb.append("		GYOU_NO,");
		sb.append("		RETU_NO,");
		sb.append("		INSERT_OK_KB,");
		sb.append("		UPDATE_OK_KB,");
		sb.append("		DELETE_OK_KB,");
		sb.append("		REFERENCE_OK_KB,");
		sb.append("		CSV_OK_KB,");
		sb.append("		PRINT_OK_KB ");
		sb.append(" FROM ");
		sb.append("		SYS_KENGEN_RIYOU ");
		sb.append("	WHERE ");
		sb.append("		KENGEN_CD = '" + conv.convertWhereString((String)map.get("kengen_cd")) +  "' ");
		sb.append("	AND	GAMEN_ID  = '" + conv.convertWhereString((String)map.get("gamen_id")) +  "' ");
		sb.append("	ORDER BY ");
		sb.append("		KENGEN_CD, ");
		sb.append("		GAMEN_ID ");

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
