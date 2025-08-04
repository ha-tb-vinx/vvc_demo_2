/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/09)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/09)初版作成
 */
public class mst150101_ButuryukeiroDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst150101_ButuryukeiroDM()
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
		mst150101_ButuryukeiroBean bean = new mst150101_ButuryukeiroBean();
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setSihaiKb( rest.getString("sihai_kb") );
		bean.setSihaiCd( rest.getString("sihai_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setNohinCenterCd( rest.getString("nohin_center_cd") );
		bean.setKeiyuCenterCd( rest.getString("keiyu_center_cd") );
		bean.setTenhaiCenterCd( rest.getString("tenhai_center_cd") );
		bean.setCenterNohinReadTime( rest.getString("center_nohin_read_time") );
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
		String wk = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("kanri_kb ");
		sb.append(", ");
		sb.append("kanri_cd ");
		sb.append(", ");
		sb.append("sihai_kb ");
		sb.append(", ");
		sb.append("sihai_cd ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("yuko_dt ");
		sb.append(", ");
		sb.append("nohin_center_cd ");
		sb.append(", ");
		sb.append("keiyu_center_cd ");
		sb.append(", ");
		sb.append("tenhai_center_cd ");
		sb.append(", ");
		sb.append("center_nohin_read_time ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append("from r_buturyukeiro ");
		wk = getWhereSqlStr(map, "kanri_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kanri_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "sihai_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "sihai_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "syohin_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "tenpo_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "yuko_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "nohin_center_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "keiyu_center_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "tenhai_center_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "center_nohin_read_time", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "delete_fg", whereStr);
		sb.append(wk);
		whereStr = andStr;
		sb.append(" order by ");
		sb.append("kanri_kb");
		sb.append(",");
		sb.append("kanri_cd");
		sb.append(",");
		sb.append("sihai_kb");
		sb.append(",");
		sb.append("sihai_cd");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("yuko_dt");
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ文字型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlStr(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		// Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= '" + conv.convertWhereString( (String)map.get(Key + "_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= '" + conv.convertWhereString( (String)map.get(Key + "_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " = '" + conv.convertWhereString( (String)map.get(Key) ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_like") != null && ((String)map.get(Key + "_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '%" + conv.convertWhereString( (String)map.get(Key + "_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get(Key + "_bef_like") != null && ((String)map.get(Key + "_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '%" + conv.convertWhereString( (String)map.get(Key + "_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft_like") != null && ((String)map.get(Key + "_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '" + conv.convertWhereString( (String)map.get(Key + "_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ日付・時刻型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlDate(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= '" + conv.convertWhereString( (String)map.get(Key + "_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= '" + conv.convertWhereString( (String)map.get(Key + "_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " = '" + (String)map.get(Key) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ数値型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlNum(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append(Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプOTHER）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlOther(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append(Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
			whereStr = andStr;
		}
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
		mst150101_ButuryukeiroBean bean = (mst150101_ButuryukeiroBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_buturyukeiro (");
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" kanri_kb");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanri_cd");
		}
		if( bean.getSihaiKb() != null && bean.getSihaiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sihai_kb");
		}
		if( bean.getSihaiCd() != null && bean.getSihaiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sihai_cd");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_dt");
		}
		if( bean.getNohinCenterCd() != null && bean.getNohinCenterCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" nohin_center_cd");
		}
		if( bean.getKeiyuCenterCd() != null && bean.getKeiyuCenterCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiyu_center_cd");
		}
		if( bean.getTenhaiCenterCd() != null && bean.getTenhaiCenterCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhai_center_cd");
		}
		if( bean.getCenterNohinReadTime() != null && bean.getCenterNohinReadTime().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_nohin_read_time");
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


		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
		}
		if( bean.getSihaiKb() != null && bean.getSihaiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSihaiKb() ) + "'");
		}
		if( bean.getSihaiCd() != null && bean.getSihaiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSihaiCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		if( bean.getNohinCenterCd() != null && bean.getNohinCenterCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNohinCenterCd() ) + "'");
		}
		if( bean.getKeiyuCenterCd() != null && bean.getKeiyuCenterCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKeiyuCenterCd() ) + "'");
		}
		if( bean.getTenhaiCenterCd() != null && bean.getTenhaiCenterCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenhaiCenterCd() ) + "'");
		}
		if( bean.getCenterNohinReadTime() != null && bean.getCenterNohinReadTime().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCenterNohinReadTime() ) + "'");
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
		mst150101_ButuryukeiroBean bean = (mst150101_ButuryukeiroBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_buturyukeiro set ");
//		if( bean.getNohinCenterCd() != null && bean.getNohinCenterCd().trim().length() != 0 )
//		{
			befKanma = true;
			sb.append(" nohin_center_cd = ");
			sb.append("'" + conv.convertString( bean.getNohinCenterCd() ) + "'");
//		}
//		if( bean.getKeiyuCenterCd() != null && bean.getKeiyuCenterCd().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiyu_center_cd = ");
			sb.append("'" + conv.convertString( bean.getKeiyuCenterCd() ) + "'");
//		}
//		if( bean.getTenhaiCenterCd() != null && bean.getTenhaiCenterCd().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhai_center_cd = ");
			sb.append("'" + conv.convertString( bean.getTenhaiCenterCd() ) + "'");
//		}
//		if( bean.getCenterNohinReadTime() != null && bean.getCenterNohinReadTime().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_nohin_read_time = ");
			sb.append("'" + conv.convertString( bean.getCenterNohinReadTime() ) + "'");
//		}
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


		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" kanri_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		}
		if( bean.getSihaiKb() != null && bean.getSihaiKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" sihai_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getSihaiKb() ) + "'");
		}
		if( bean.getSihaiCd() != null && bean.getSihaiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" sihai_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSihaiCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" yuko_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
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
		mst150101_ButuryukeiroBean bean = (mst150101_ButuryukeiroBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_buturyukeiro where ");
		sb.append(" kanri_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		sb.append(" AND");
		sb.append(" kanri_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append(" AND");
		sb.append(" sihai_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getSihaiKb() ) + "'");
		sb.append(" AND");
		sb.append(" sihai_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSihaiCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append(" AND");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
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
