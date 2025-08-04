/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius H.Futagami
 * @version 1.0(2005/03/17)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius H.Futagami
 * @version 1.0(2005/03/17)初版作成
 */
public class mst570201_ButuryuKeiroTempoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst570201_ButuryuKeiroTempoDM()
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
		mst570201_ButuryuKeiroTempoBean bean = new mst570201_ButuryuKeiroTempoBean();
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setKanjiRn( rest.getString("kanji_rn") );
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
		sb.append("	DISTINCT R_BUTURYUKEIRO.TENPO_CD ");
		sb.append("	, ");
		sb.append("	R_TENPO.KANJI_RN ");
		sb.append("FROM ");
		sb.append("	R_BUTURYUKEIRO ");
		sb.append("INNER JOIN ");
		sb.append("	R_TENPO ");
		sb.append("ON ");
		sb.append("	R_BUTURYUKEIRO.TENPO_CD = R_TENPO.TENPO_CD ");
		// kanri_kb に対するWHERE区
		if( map.get("kanri_kb") != null && ((String)map.get("kanri_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("R_BUTURYUKEIRO.kanri_kb = '" + conv.convertWhereString( (String)map.get("kanri_kb") ) + "'");
			whereStr = andStr;
		}
		// kanri_cd に対するWHERE区
		if( map.get("kanri_cd") != null && ((String)map.get("kanri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("R_BUTURYUKEIRO.kanri_cd = '" + conv.convertWhereString( (String)map.get("kanri_cd") ) + "'");
			whereStr = andStr;
		}
		// sihai_kb に対するWHERE区
		if( map.get("sihai_kb") != null && ((String)map.get("sihai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("R_BUTURYUKEIRO.sihai_kb = '" + conv.convertWhereString( (String)map.get("sihai_kb") ) + "'");
			whereStr = andStr;
		}
		// sihai_cd に対するWHERE区
		if( map.get("sihai_cd") != null && ((String)map.get("sihai_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("R_BUTURYUKEIRO.sihai_cd = '" + conv.convertWhereString( (String)map.get("sihai_cd") ) + "'");
			whereStr = andStr;
		}
		// syohin_cd に対するWHERE区
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("R_BUTURYUKEIRO.syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
			whereStr = andStr;
		}
		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("R_BUTURYUKEIRO.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			whereStr = andStr;
		}
		// yuko_dt に対するWHERE区
		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("R_BUTURYUKEIRO.yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
			whereStr = andStr;
		}
		//業種が指定されている場合（新規）
		if( map.get("l_gyosyu_cd") != null && ((String)map.get("l_gyosyu_cd")).trim().length() > 0 ) {
			whereStr = "where ";
			sb.append("	UNION ");
			sb.append("SELECT ");
			sb.append("	DISTINCT R_TENGROUP.TENPO_CD ");
			sb.append("	, ");
			sb.append("	R_TENPO.KANJI_RN ");
			sb.append("FROM ");
			sb.append("	R_TENGROUP ");
			sb.append("INNER JOIN ");
			sb.append("	R_TENPO ");
			sb.append("ON ");
			sb.append("	R_TENGROUP.TENPO_CD = R_TENPO.TENPO_CD ");
			// l_gyosyu_cd に対するWHERE区
			if( map.get("l_gyosyu_cd") != null && ((String)map.get("l_gyosyu_cd")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("R_TENGROUP.l_gyosyu_cd = '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd") ) + "'");
				whereStr = andStr;
			}
			// yoto_kb に対するWHERE区
			if( map.get("yoto_kb") != null && ((String)map.get("yoto_kb")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("R_TENGROUP.yoto_kb = '" + conv.convertWhereString( (String)map.get("yoto_kb") ) + "'");
				whereStr = andStr;
			}
			// groupno_cd に対するWHERE区
			if( map.get("groupno_cd") != null && ((String)map.get("groupno_cd")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("R_TENGROUP.groupno_cd = " + conv.convertWhereString( (String)map.get("groupno_cd") ) + "");
				whereStr = andStr;
			}
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
