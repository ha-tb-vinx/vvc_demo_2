/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）価格チェックマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する価格チェックマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/18)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;
import mdware.common.util.date.AbstractMDWareDateGetter;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）価格チェックマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する価格チェックマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/18)初版作成
 */
public class mst280101_KakakuCheckDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst280101_KakakuCheckDM()
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
		mst280101_KakakuCheckBean bean = new mst280101_KakakuCheckBean();
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setKakakuKb( rest.getString("kakaku_kb") );
		bean.setMinKakakuVl( rest.getLong("min_kakaku_vl") );
		bean.setCheckStDt( rest.getString("check_st_dt") );
		bean.setCheckEdDt( rest.getString("check_ed_dt") );
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
		sb.append("hanku_cd ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("kakaku_kb ");
		sb.append(", ");
		sb.append("min_kakaku_vl ");
		sb.append(", ");
		sb.append("check_st_dt ");
		sb.append(", ");
		sb.append("check_ed_dt ");
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
		sb.append("from r_kakakucheck ");
		wk = getWhereSqlStr(map, "hanku_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "syohin_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kakaku_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlOther(map, "min_kakaku_vl", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "check_st_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "check_ed_dt", whereStr);
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
		sb.append("hanku_cd");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("kakaku_kb");
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
		mst280101_KakakuCheckBean bean = (mst280101_KakakuCheckBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_kakakucheck (");
		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" hanku_cd");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getKakakuKb() != null && bean.getKakakuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kakaku_kb");
		}
		if( befKanma ) sb.append(",");
		sb.append(" min_kakaku_vl");
		if( bean.getCheckStDt() != null && bean.getCheckStDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" check_st_dt");
		}
		if( bean.getCheckEdDt() != null && bean.getCheckEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" check_ed_dt");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHankuCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getKakakuKb() != null && bean.getKakakuKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKakakuKb() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getMinKakakuVlString());
		if( bean.getCheckStDt() != null && bean.getCheckStDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getCheckStDt() ) + "'");
		}
		if( bean.getCheckEdDt() != null && bean.getCheckEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getCheckEdDt() ) + "'");
		}

//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		}
		sb.append(",");
//		↓↓移植（2006.05.11）↓↓
		try {
			sb.append(" '"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"' " );
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		↑↑移植（2006.05.11）↑↑

		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
//			sb.append(",");
//			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
			sb.append(",");
//			↓↓移植（2006.05.11）↓↓
			try {
				sb.append(" '"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"' " );
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			↑↑移植（2006.05.11）↑↑
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
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
		mst280101_KakakuCheckBean bean = (mst280101_KakakuCheckBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_kakakucheck set ");
		befKanma = true;
		sb.append(" min_kakaku_vl = ");
		sb.append( bean.getMinKakakuVlString());
		if( bean.getCheckStDt() != null && bean.getCheckStDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" check_st_dt = ");
			sb.append("'" + conv.convertString( bean.getCheckStDt() ) + "'");
		}
		if( bean.getCheckEdDt() != null && bean.getCheckEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" check_ed_dt = ");
			sb.append("'" + conv.convertString( bean.getCheckEdDt() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}

//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append(" update_ts = ");
//			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
//		}
		sb.append(", update_ts = ");
//		↓↓移植（2006.05.11）↓↓
		try {
			sb.append(" '"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"' " );
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		↑↑移植（2006.05.11）↑↑
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" hanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getKakakuKb() != null && bean.getKakakuKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kakaku_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getKakakuKb() ) + "'");
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
		mst280101_KakakuCheckBean bean = (mst280101_KakakuCheckBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_kakakucheck where ");
		sb.append(" hanku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" kakaku_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKakakuKb() ) + "'");
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
