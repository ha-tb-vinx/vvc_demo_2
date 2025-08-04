/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用名称CTFのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用名称CTFのDMクラス(DmCreatorにより自動生成)</P>
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
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst870201_MstDateDM用名称CTFのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用名称CTFのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst870201_MstDateDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst870201_MstDateDM()
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
		mst870201_MstDateBean bean = new mst870201_MstDateBean();
		bean.setMstDateDt( rest.getString("MST_DATE_DT") );
		bean.setBatDateDt( rest.getString("BAT_DATE_DT") );
		bean.setMstDateDtNext( rest.getString("MST_DATE_DT_NEXT") );
		bean.setBatDateDtNext( rest.getString("BAT_DATE_DT_NEXT") );
		bean.setMstDateDt2Next( rest.getString("MST_DATE_DT_2NEXT") );
		bean.setBatDateDt2Next( rest.getString("BAT_DATE_DT_2NEXT") );
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
		sb.append("SELECT ");
		sb.append("		R.MST_DATE_DT MST_DATE_DT, ");
		sb.append("		R.BAT_DATE_DT BAT_DATE_DT, ");
//		↓↓移植（2006.05.26）↓↓
		sb.append(" TO_CHAR(TO_DATE(R.MST_DATE_DT, 'YYYYMMDD')+1 days, 'YYYYMMDD')  MST_DATE_DT_NEXT, ");
		sb.append(" TO_CHAR(TO_DATE(R.BAT_DATE_DT, 'YYYYMMDD')+1 days, 'YYYYMMDD')  BAT_DATE_DT_NEXT, ");
		sb.append(" TO_CHAR(TO_DATE(R.MST_DATE_DT, 'YYYYMMDD')+2 days, 'YYYYMMDD')  MST_DATE_DT_2NEXT, ");
		sb.append(" TO_CHAR(TO_DATE(R.BAT_DATE_DT, 'YYYYMMDD')+2 days, 'YYYYMMDD')  BAT_DATE_DT_2NEXT ");
//		↑↑移植（2006.05.26）↑↑		
		sb.append("  FROM SYSTEM_CONTROL R ");
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
