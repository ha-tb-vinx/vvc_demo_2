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
public class mst000901_MenuBarDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst000901_MenuBarDM()
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
		sb.append("		S_KEN_RI.KENGEN_CD,");
		sb.append("		S_KEN_RI.GAMEN_ID,");
		sb.append("		S_KEN_RI.SUBMENU_ID,");
		sb.append("		S_KEN_RI.GYOU_NO,");
		sb.append("		S_KEN_RI.RETU_NO,");
		sb.append("		S_KEN_RI.INSERT_OK_KB,");
		sb.append("		S_KEN_RI.UPDATE_OK_KB,");
		sb.append("		S_KEN_RI.DELETE_OK_KB,");
		sb.append("		S_KEN_RI.REFERENCE_OK_KB,");
		sb.append("		S_KEN_RI.CSV_OK_KB,");
		sb.append("		S_KEN_RI.PRINT_OK_KB ");


		sb.append("   FROM SYS_KENGEN_RIYOU	S_KEN_RI ");
		sb.append("  INNER JOIN SYS_SENTAKU_GYOSYU_RIYOU GYOSYU ");
		sb.append("		ON S_KEN_RI.GAMEN_ID = GYOSYU.GAMEN_ID ");

		sb.append("	WHERE ");
		sb.append("		S_KEN_RI.KENGEN_CD = '" + conv.convertWhereString((String)map.get("kengen_cd")) +  "' ");
		sb.append("	AND	SUBMENU_ID = ");
		sb.append("				( ");
		sb.append("				SELECT ");
		sb.append("					SUBMENU_ID ");
		sb.append("				FROM ");
		sb.append("					SYS_KENGEN_RIYOU ");
		sb.append("				WHERE ");
		sb.append("					KENGEN_CD = '" + conv.convertWhereString((String)map.get("kengen_cd")) +  "' ");
		sb.append("				AND	GAMEN_ID  = '" + conv.convertWhereString((String)map.get("gamen_id")) +  "' ");
		sb.append("				) ");
		sb.append("	AND ( ");
		sb.append("		S_KEN_RI.INSERT_OK_KB = '1' OR ");
		sb.append("		S_KEN_RI.UPDATE_OK_KB = '1' OR ");
		sb.append("		S_KEN_RI.DELETE_OK_KB = '1' OR ");
		sb.append("		S_KEN_RI.REFERENCE_OK_KB = '1' OR ");
		sb.append("		S_KEN_RI.CSV_OK_KB = '1' OR ");
		sb.append("		S_KEN_RI.PRINT_OK_KB = '1' ");
		sb.append("		) ");
		sb.append("	AND	S_KEN_RI.GAMEN_ID  <> '" + conv.convertWhereString((String)map.get("gamen_id")) +  "' ");
		sb.append("	AND	S_KEN_RI.GAMEN_ID  <> 'mst170101' ");
		sb.append("	AND	S_KEN_RI.GAMEN_ID  <> 'mst590101' ");
		sb.append("	AND	S_KEN_RI.GAMEN_ID  <> 'mst720101' ");
		sb.append("	AND	S_KEN_RI.GAMEN_ID  <> 'mst810101' ");
		sb.append("	AND	S_KEN_RI.GAMEN_ID  <> 'mst850101' ");
		
		// sentaku_gyosyu_cd に対するWHERE句
		if( map.get("sentaku_gyosyu_cd") != null && ((String)map.get("sentaku_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append("AND GYOSYU.SENTAKU_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("sentaku_gyosyu_cd") ) + "'");
		}
		
		
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
