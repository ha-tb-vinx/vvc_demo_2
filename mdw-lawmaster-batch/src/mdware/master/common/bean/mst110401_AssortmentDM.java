package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2005/5/31 21:25:22)
 */
public class mst110401_AssortmentDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst110401_AssortmentDM()
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
		mst110401_AssortmentBean bean = new mst110401_AssortmentBean();
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setColorCd( rest.getString("color_cd") );
		bean.setSizeCd( rest.getString("size_cd") );
		bean.setPattern1Qt( rest.getString("pattern_1_qt") );
		bean.setPattern2Qt( rest.getString("pattern_2_qt") );
		bean.setPattern3Qt( rest.getString("pattern_3_qt") );
		bean.setPattern4Qt( rest.getString("pattern_4_qt") );
		bean.setPattern5Qt( rest.getString("pattern_5_qt") );
		bean.setTagHakoQt( rest.getString("tag_hako_qt") );
		bean.setTotalTagHakoQt( rest.getString("total_tag_hako_qt") );
		bean.setHachuTeisiKb( rest.getString("hachu_teisi_kb") );
		bean.setSaihakoFg( rest.getString("saihako_fg") );
		bean.setTorihikisakiRendobiDt( rest.getString("torihikisaki_rendobi_dt") );
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
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("syohin_cd, ");
		sb.append("color_cd, ");
		sb.append("size_cd, ");
		sb.append("pattern_1_qt, ");
		sb.append("pattern_2_qt, ");
		sb.append("pattern_3_qt, ");
		sb.append("pattern_4_qt, ");
		sb.append("pattern_5_qt, ");
		sb.append("tag_hako_qt, ");
		sb.append("total_tag_hako_qt, ");
		sb.append("hachu_teisi_kb, ");
		sb.append("saihako_fg, ");
		sb.append("torihikisaki_rendobi_dt, ");
		sb.append("insert_ts, ");
		sb.append("insert_user_id, ");
		sb.append("update_ts, ");
		sb.append("update_user_id, ");
		sb.append("delete_fg ");
		sb.append("from r_assortment ");

		// syohin_cd に対するWHERE区
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 ){
			sb.append(whereStr);
			sb.append("syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
			whereStr = andStr;
		}

		// color_cd に対するWHERE区
		if( map.get("color_cd") != null && ((String)map.get("color_cd")).trim().length() > 0 ){
			sb.append(whereStr);
			sb.append("color_cd = '" + conv.convertWhereString( (String)map.get("color_cd") ) + "'");
			whereStr = andStr;
		}

		// size_cd に対するWHERE区
		if( map.get("size_cd") != null && ((String)map.get("size_cd")).trim().length() > 0 ){
			sb.append(whereStr);
			sb.append("size_cd = '" + conv.convertWhereString( (String)map.get("size_cd") ) + "'");
			whereStr = andStr;
		}

		// tag_hako_qt に対するWHERE区
		if( map.get("tag_hako_qt") != null && ((String)map.get("tag_hako_qt")).trim().length() > 0  ){
			sb.append(whereStr);
			sb.append("tag_hako_qt = " + (String)map.get("tag_hako_qt"));
			whereStr = andStr;
		}

		// delete_fg に対するWHERE区
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 ){
			sb.append(whereStr);
			sb.append("delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("syohin_cd,");
		sb.append("color_cd,");
		sb.append("size_cd");

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst ){
		boolean befKanma = false;
		boolean aftKanma = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst110401_AssortmentBean bean = (mst110401_AssortmentBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_assortment (");
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 ){
			befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getColorCd() != null && bean.getColorCd().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" color_cd");
		}
		if( bean.getSizeCd() != null && bean.getSizeCd().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" size_cd");
		}
		if( befKanma ) sb.append(",");
		sb.append(" pattern_1_qt,");
		sb.append(" pattern_2_qt,");
		sb.append(" pattern_3_qt,");
		sb.append(" pattern_4_qt,");
		sb.append(" pattern_5_qt,");
		sb.append(" tag_hako_qt,");
		sb.append(" total_tag_hako_qt");

		if( bean.getHachuTeisiKb() != null && bean.getHachuTeisiKb().trim().length() != 0 ){
			sb.append(", hachu_teisi_kb");
		}
		if( bean.getSaihakoFg() != null && bean.getSaihakoFg().trim().length() != 0 ){
			sb.append(", saihako_fg");
		}
		if( bean.getTorihikisakiRendobiDt() != null && bean.getTorihikisakiRendobiDt().trim().length() != 0 ){
			sb.append(", torihikisaki_rendobi_dt");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 ){
			sb.append(", insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 ){
			sb.append(", insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 ){
			sb.append(", update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 ){
			sb.append(", update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 ){
			sb.append(", delete_fg");
		}

		sb.append(")Values(");

		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 ){
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getColorCd() != null && bean.getColorCd().trim().length() != 0 ){
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getColorCd() ) + "'");
		}
		if( bean.getSizeCd() != null && bean.getSizeCd().trim().length() != 0 ){
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSizeCd() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getPattern1QtString());
		sb.append(",");
		sb.append( bean.getPattern2QtString());
		sb.append(",");
		sb.append( bean.getPattern3QtString());
		sb.append(",");
		sb.append( bean.getPattern4QtString());
		sb.append(",");
		sb.append( bean.getPattern5QtString());
		sb.append(",");
		sb.append( bean.getTagHakoQtString());
		sb.append(",");
		sb.append( bean.getTotalTagHakoQtString());
		if( bean.getHachuTeisiKb() != null && bean.getHachuTeisiKb().trim().length() != 0 ){
			sb.append(",'" + conv.convertString( bean.getHachuTeisiKb() ) + "'");
		}
		if( bean.getSaihakoFg() != null && bean.getSaihakoFg().trim().length() != 0 ){
			sb.append(",'" + conv.convertString( bean.getSaihakoFg() ) + "'");
		}
		if( bean.getTorihikisakiRendobiDt() != null && bean.getTorihikisakiRendobiDt().trim().length() != 0 ){
			sb.append(",'" + conv.convertString( bean.getTorihikisakiRendobiDt() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 ){
			sb.append(",'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 ){
			sb.append(",'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 ){
			sb.append(",'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 ){
			sb.append(",'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 ){
			sb.append(",'" + conv.convertString( bean.getDeleteFg() ) + "'");
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
	public String getUpdateSql( Object beanMst ){
		boolean befKanma = false;
		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst110401_AssortmentBean bean = (mst110401_AssortmentBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_assortment set ");
		befKanma = true;
//		sb.append(" pattern_1_qt = " + bean.getPattern1QtString());
//		sb.append(",");
//		sb.append(" pattern_2_qt = " + bean.getPattern2QtString());
//		sb.append(",");
//		sb.append(" pattern_3_qt = " + bean.getPattern3QtString());
//		sb.append(",");
//		sb.append(" pattern_4_qt = " + bean.getPattern4QtString());
//		sb.append(",");
//		sb.append(" pattern_5_qt = " + bean.getPattern5QtString());
//		sb.append(",");
		sb.append(" tag_hako_qt = " + bean.getTagHakoQtString());
//		sb.append(",");
//		sb.append(" total_tag_hako_qt = " + bean.getTotalTagHakoQtString());
/*
		if( bean.getHachuTeisiKb() != null && bean.getHachuTeisiKb().trim().length() != 0 ){
			sb.append(",");
			sb.append(" hachu_teisi_kb = '" + conv.convertString( bean.getHachuTeisiKb() ) + "'");
		}
		if( bean.getSaihakoFg() != null && bean.getSaihakoFg().trim().length() != 0 ){
			sb.append(",");
			sb.append(" saihako_fg = '" + conv.convertString( bean.getSaihakoFg() ) + "'");
		}
		if( bean.getTorihikisakiRendobiDt() != null && bean.getTorihikisakiRendobiDt().trim().length() != 0 ){
			sb.append(",");
			sb.append(" torihikisaki_rendobi_dt = '" + conv.convertString( bean.getTorihikisakiRendobiDt() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 ){
			sb.append(",");
			sb.append(" insert_ts = '" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 ){
			sb.append(",");
			sb.append(" insert_user_id = '" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
*/
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 ){
			sb.append(",");
			sb.append(" update_ts = '" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 ){
			sb.append(",");
			sb.append(" update_user_id = '" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 ){
			sb.append(",");
			sb.append(" delete_fg = '" + conv.convertString( bean.getDeleteFg() ) + "'");
		}

		sb.append(" WHERE");

		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 ){
			whereAnd = true;
			sb.append(" syohin_cd = '" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getColorCd() != null && bean.getColorCd().trim().length() != 0 ){
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" color_cd = '" + conv.convertWhereString( bean.getColorCd() ) + "'");
		}
		if( bean.getSizeCd() != null && bean.getSizeCd().trim().length() != 0 ){
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" size_cd = '" + conv.convertWhereString( bean.getSizeCd() ) + "'");
		}

		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql( Object beanMst ){
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst110401_AssortmentBean bean = (mst110401_AssortmentBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_assortment where ");
		sb.append(" syohin_cd = '" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" color_cd = '" + conv.convertWhereString( bean.getColorCd() ) + "'");
		sb.append(" AND");
		sb.append(" size_cd = '" + conv.convertWhereString( bean.getSizeCd() ) + "'");
		return sb.toString();
	}

	/**
	 * JDK1.4からは使用できるようになったString.replaceAllをJDK1.3以前用に作成する。
	 * @param base
	 * @param before
	 * @param after
	 * @return
	 */
	protected String replaceAll( String base, String before, String after ){
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
