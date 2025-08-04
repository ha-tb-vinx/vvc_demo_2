/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst840201_Tenkabutu用添加物マスタの表示用DMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst840201_Tenkabutu用添加物マスタマスタの表示用DMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst840201_Tenkabutu用添加物マスタマスタの表示用DMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst840201_Tenkabutu用添加物マスタマスタの表示用DMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst830201_KeiryokiThemeListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst830201_KeiryokiThemeListDM()
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
		mst830201_KeiryokiThemeListBean bean = new mst830201_KeiryokiThemeListBean();
		bean.setCheckFlg("0");
		bean.setOptFlg("0");
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setThemeCd( rest.getString("theme_cd") );
		bean.setThemeNa( rest.getString("theme_na") );
		bean.setHaneiDt( rest.getString("hanei_dt") );
		bean.setUpdateTs( rest.getString("hanei_dt") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateTsShort( rest.getString("update_ts_short") );
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
		long cnt =0;
		String blank = mst000401_LogicBean.chkNullString((String)map.get("h_blankkb")).trim();
		
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append(" DECODE(TB2.THEME_CD,NULL,"
					+ mst000801_DelFlagDictionary.IRU.getCode() 
					+ "," + mst000801_DelFlagDictionary.INAI.getCode() 
					+ ") AS DELETE_FG, ");
		sb.append("	TO_CHAR(TB2.S_GYOSYU_CD,'FM0000')	AS S_GYOSYU_CD, ");
		if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
			sb.append("	TO_CHAR(TB1.CD,'FM00')				AS THEME_CD, ");
		} else {
			sb.append("	TO_CHAR(TB2.THEME_CD,'FM00')				AS THEME_CD, ");
		}
		sb.append("	RTRIM(NVL(TB2.THEME_NA,''))			AS THEME_NA, ");
		sb.append("	RTRIM(NVL(TB2.HANEI_DT,''))			AS HANEI_DT, ");
		sb.append("	RTRIM(NVL(TB2.UPDATE_TS,''))		AS UPDATE_TS, ");
		sb.append("	SUBSTR(NVL(TB2.UPDATE_TS,''),0,8)	AS UPDATE_TS_SHORT ");
		sb.append("FROM ");
		if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
			sb.append("	( ");
			for(cnt=1; cnt<=99; cnt++ ){
				if(cnt != 1){
					sb.append("     UNION ALL ");
				}
				sb.append("		SELECT " + cnt + " AS CD FROM DUAL ");
			}
			sb.append("			) TB1 , ");
		}
		sb.append("			(SELECT ");
		sb.append("			   S_GYOSYU_CD, ");
		sb.append("			   THEME_CD, ");
		sb.append("			   THEME_NA, ");
		sb.append("			   HANEI_DT, ");
		sb.append("			   UPDATE_TS ");
		sb.append("			 FROM ");
		sb.append("			   R_KEIRYOKI_THEME TB2 ");
		sb.append("			 WHERE ");
		sb.append("			   DELETE_FG IS NULL OR DELETE_FG<>'" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");
		sb.append("			 AND S_GYOSYU_CD = '" + (String)map.get("s_gyosyu_cd") + "' ");
		sb.append("			) TB2 ");
		if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
			sb.append("		WHERE ");
			sb.append("			TB1.CD = TB2.THEME_CD(+) ");
		}
		sb.append(" ORDER BY THEME_CD ");
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
