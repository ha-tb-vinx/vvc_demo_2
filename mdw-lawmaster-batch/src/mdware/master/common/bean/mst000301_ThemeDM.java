/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用計量器テーママスタのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用計量器テーママスタのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用計量器テーママスタのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用計量器テーママスタのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst000301_ThemeDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst000301_ThemeDM()
	{
		super( mst000101_ConstDictionary.CONNECTION_STR);
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
		mst000301_ThemeBean bean = new mst000301_ThemeBean();
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setThemeCd( rest.getString("theme_cd") );
		bean.setHaneiDt( rest.getString("hanei_dt") );
		bean.setThemeNa( rest.getString("theme_na") );
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
		sb.append("SELECT ");
		sb.append("	S_GYOSYU_CD ");
		sb.append("	, ");
		sb.append("	THEME_CD ");
		sb.append("	, ");
		sb.append("	HANEI_DT ");
		sb.append("	, ");
		sb.append("	THEME_NA ");
		sb.append("	, ");
		sb.append("	INSERT_TS ");
		sb.append("	, ");
		sb.append("	INSERT_USER_ID ");
		sb.append("	, ");
		sb.append("	UPDATE_TS ");
		sb.append("	, ");
		sb.append("	UPDATE_USER_ID ");
		sb.append("	, ");
		sb.append("	DELETE_FG ");
		sb.append("FROM ");
		sb.append("	R_KEIRYOKI_THEME ");
		sb.append("WHERE ");
		sb.append("	S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "' ");
		sb.append("	AND ");
		
		if( !mst000401_LogicBean.chkNullString((String)map.get("theme_cd")).equals("") ){
			sb.append("	THEME_CD = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "' ");
			sb.append("	AND ");
		}
		sb.append("	DELETE_FG='0' ");
/*
		sb.append("	AND ");
		sb.append("	HANEI_DT <= TO_CHAR(SYSDATE ");
		sb.append("	, ");
		sb.append("	'YYYYMMDDHH24MISS') ");
		sb.append("	AND ");
		sb.append("	HANEI_DT = ");
		sb.append("	( ");
		sb.append("		SELECT ");
		sb.append("			MAX(HANEI_DT) HANEI_DT ");
		sb.append("		FROM ");
		sb.append("			R_KEIRYOKI_THEME ");
		sb.append("		WHERE ");
		sb.append("			S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "' ");
		sb.append("			AND ");
		sb.append("			HANEI_DT <= TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");
		sb.append("			AND ");
		sb.append("			DELETE_FG='0' ");
		if(conv.convertWhereString( (String)map.get("themecd") ) != null && !conv.convertWhereString( (String)map.get("themecd") ).equals("")){
			sb.append("	AND THEME_CD='" + conv.convertWhereString( (String)map.get("themecd") ) + "' ");
		}
		sb.append("	) ");
*/
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
