package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mst650101_SyuukeiSyubetuDetailDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst650101_SyuukeiSyubetuDetailDM()
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
		mst650101_SyuukeiSyubetuDetailBean bean = new mst650101_SyuukeiSyubetuDetailBean();

//		bean.setSyukeihinsyuHinsyuSyukeiHinsyuCd( rest.getString("syukei_hinsyu_cd") );
//		bean.setSyukeihinsyuHinsyuHinsyuCd( rest.getString("hinsyu_cd") );
//		bean.setInsertTs( rest.getString("insert_ts") );
//		bean.setInsertUserId( rest.getString("insert_user_id") );
//		bean.setUpdateTs( rest.getString("update_ts") );
//		bean.setUpdateUserId( rest.getString("update_user_id") );
//		bean.setDeleteFg( rest.getString("delete_fg") );
//
//		bean.setSyohinTaikeiHankuCd( rest.getString("syohin_taikei_hanku_cd") );
//
//		bean.setHankuKanjiRn( rest.getString("hanku_kanji_rn") );
//
//		bean.setHinsyuKanjiRn( rest.getString("hinsyu_kanji_rn") );
//
//		bean.setUserNaInsert( rest.getString("user_na_insert") );
//		bean.setUserNaUpdate( rest.getString("user_na_update") );
		bean.setSyukeiHinsyuCd( rest.getString("syukeihinsyu_cd") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setHinsyuNa( rest.getString("hinsyu_na") );
		bean.setSyukeiKbCd( rest.getString("syukei_kb_cd") );
		bean.setSyukeiKbNa( rest.getString("syukei_kb_na") );
		bean.setSyukeiKbACd( rest.getString("syukei_kb_a_cd") );
		bean.setSyukeiKbBCd( rest.getString("syukei_kb_b_cd") );
		bean.setSyukeiKbCCd( rest.getString("syukei_kb_c_cd") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setSyorisyaUserId( rest.getString("syorisya_user_id") );
		bean.setSyorisyaUserNa( rest.getString("syorisya_user_na") );

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
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
//		sb.append("select ");
//		sb.append("table1.syukei_hinsyu_cd as syukei_hinsyu_cd ");
//		sb.append(", ");
//		sb.append("table1.hinsyu_cd as hinsyu_cd ");
//		sb.append(", ");
//		sb.append("table1.insert_ts as insert_ts ");
//		sb.append(", ");
//		sb.append("table1.insert_user_id as insert_user_id ");
//		sb.append(", ");
//		sb.append("table1.update_ts as update_ts ");
//		sb.append(", ");
//		sb.append("table1.update_user_id as update_user_id ");
//		sb.append(", ");
//		sb.append("table1.delete_fg as delete_fg ");
//		sb.append(", ");
//		sb.append("table2.hanku_cd as syohin_taikei_hanku_cd ");
//		sb.append(", ");
//		sb.append("MSP650101_GETKANJIRNNAMECTF('" + mst000101_ConstDictionary.H_SALE  + "', table2.hanku_cd) hanku_kanji_rn");
//		sb.append(", ");
//		sb.append("MSP650101_GETKANJIRNNAMECTF('" + mst000101_ConstDictionary.KIND  + "', table1.hinsyu_cd) hinsyu_kanji_rn");
//		sb.append(", ");
//		sb.append("table5.user_na as user_na_insert ");
//		sb.append(", ");
//		sb.append("table6.user_na as user_na_update ");
//
//		sb.append(" from ");
//		sb.append(" R_SYUKEIHINSYU_HINSYU table1 ");
//		sb.append(" ,R_SYOHIN_TAIKEI table2 ");
//		sb.append(" ,SYS_SOSASYA table5 ");
//		sb.append(" ,SYS_SOSASYA table6 ");
//		sb.append(" WHERE ");
//		sb.append("table2.hinsyu_cd = table1.hinsyu_cd ");
//		sb.append("AND ");
//
//		// hanku_cd に対するWHERE区
//		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
//		{
//		    sb.append("table2.hanku_cd    = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
//			sb.append("AND ");
//		}
//		sb.append("table1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "' ");
//		sb.append("AND ");
//		sb.append("table5.user_id (+)= table1.insert_user_id ");
//		sb.append("AND ");
//		sb.append("table6.user_id (+)= table1.update_user_id ");
//
//		sb.append(" AND table5.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append(" AND table6.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");
//
//		// syukei_hinsyu_cd に対するWHERE区
//		if( map.get("syukei_hinsyu_cd") != null && ((String)map.get("syukei_hinsyu_cd")).trim().length() > 0 )
//		{
//			sb.append(andStr);
//			sb.append("table1.syukei_hinsyu_cd = '" + conv.convertWhereString( (String)map.get("syukei_hinsyu_cd") ) + "'");
//		}


		sb.append("select ");
		sb.append("	meisai.syukeihinsyu_cd as syukeihinsyu_cd ");
		sb.append(" , ");
		sb.append("	meisai.hinsyu_cd as hinsyu_cd ");
		sb.append(" , ");
		sb.append("	ctf.kanji_na as hinsyu_na ");
		sb.append(" , ");
		sb.append(" meisai.syukei_kb_cd as syukei_kb_cd ");
		sb.append(" , ");
		sb.append(" skb.syukei_kb_na as syukei_kb_na ");
		sb.append(" , ");
		sb.append(" meisai.syukei_kb_a_cd as syukei_kb_a_cd ");
		sb.append(" , ");
		sb.append(" meisai.syukei_kb_b_cd as syukei_kb_b_cd ");
		sb.append(" , ");
		sb.append(" meisai.syukei_kb_c_cd as syukei_kb_c_cd ");
		sb.append(" , ");
		sb.append("	meisai.insert_ts as insert_ts ");
		sb.append(" , ");
		sb.append(" meisai.update_ts as update_ts ");
		sb.append(" , ");
		sb.append(" meisai.syorisya_user_id as syorisya_user_id ");
		sb.append(" , ");
		sb.append(" sosasya.user_na as syorisya_user_na ");

		sb.append("from ");
		sb.append(" R_SYUKEIHINSYU_MEISAI meisai ");
		sb.append(" , ");
		sb.append(" SYS_SOSASYA sosasya ");
		sb.append(" , ");
		sb.append(" R_SYOHIN_TAIKEI taikei ");
		sb.append(" , ");
		sb.append(" R_NAMECTF ctf ");
		sb.append(" , ");
		sb.append(" R_SYUKEI_KB skb ");

		sb.append("where ");
		sb.append(" meisai.hinsyu_cd = taikei.hinsyu_cd ");
		sb.append("  and ");

		// hanku_cd に対するWHERE区
		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
		{
			sb.append("taikei.hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
			sb.append(" and ");
		}

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		sb.append(" ctf.code_cd (+)= meisai.hinsyu_cd ");
		sb.append("  and ");
		sb.append(" ctf.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
		sb.append("  and ");

		sb.append(" skb.syukei_kb_cd (+)= meisai.syukei_kb_cd ");
		sb.append("  and ");

		sb.append(" sosasya.user_id (+)= meisai.syorisya_user_id ");
		sb.append("  and ");
		sb.append(" sosasya.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");

		// syukeihinsyu_cd に対するWHERE区
		if( map.get("syukeihinsyu_cd") != null && ((String)map.get("syukeihinsyu_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append(" meisai.syukeihinsyu_cd = '" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd") ) + "' ");
		}

		sb.append(" order by ");
		sb.append(" meisai.hinsyu_cd");

//		sb.append("SELECT ");
//		sb.append(" meisai.syukeihinsyu_cd as syukeihinsyu_cd ");
//		sb.append(" , ");
//		sb.append(" taikei.hinsyu_cd as hinsyu_cd ");
//		sb.append(" , ");
//		sb.append(" ctf.kanji_na as hinsyu_na ");
//		sb.append(" , ");
//		sb.append(" meisai.syukei_kb_cd as syukei_kb_cd ");
//		sb.append(" , ");
//		sb.append(" skb.syukei_kb_na as syukei_kb_na ");
//		sb.append(" , ");
//		sb.append(" meisai.syukei_kb_a_cd as syukei_kb_a_cd ");
//		sb.append(" , ");
//		sb.append(" meisai.syukei_kb_b_cd as syukei_kb_b_cd ");
//		sb.append(" , ");
//		sb.append(" meisai.syukei_kb_c_cd as syukei_kb_c_cd ");
//		sb.append(" , ");
//		sb.append(" meisai.insert_ts as insert_ts ");
//		sb.append(" , ");
//		sb.append(" meisai.update_ts as update_ts ");
//		sb.append(" , ");
//		sb.append(" meisai.syorisya_user_id as syorisya_user_id ");
//		sb.append(" , ");
//		sb.append(" sosasya.user_na as syorisya_user_na ");
//
//		sb.append("FROM ");
//		// syukeihinsyu_cd に対するWHERE区
//		if( map.get("syukeihinsyu_cd") != null && ((String)map.get("syukeihinsyu_cd")).trim().length() > 0 )
//		{
//			sb.append(" ( SELECT ");
//			sb.append("    * ");
//			sb.append("   FROM ");
//			sb.append("    r_syukeihinsyu_meisai ");
//			sb.append("   WHERE ");
//			sb.append("    syukeihinsyu_cd = '" + conv.convertWhereString( (String)map.get("syukeihinsyu_cd") ) + "' ");
//			sb.append(" ) meisai ");
//		} else {
//			sb.append(" r_syukeihinsyu_meisai meisai ");
//		}
//		sb.append(" , ");
//		// hanku_cd に対するWHERE区
//		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
//		{
//			sb.append(" ( SELECT ");
//			sb.append("    hinsyu_cd ");
//			sb.append("   FROM ");
//			sb.append("    r_syohin_taikei ");
//			sb.append("   WHERE ");
//			sb.append("    hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "' ");
//			sb.append(" ) taikei ");
//		} else {
//			sb.append(" r_syohin_taikei taikei ");
//		}
//		sb.append(" , ");
//		sb.append(" ( SELECT ");
//		sb.append("    code_cd ");
//		sb.append("    , ");
//		sb.append("    kanji_na ");
//		sb.append("   FROM ");
//		sb.append("    r_namectf ");
//		sb.append("   WHERE ");
//		sb.append("    syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' ");
//		sb.append(" ) ctf ");
//		sb.append(" , ");
//		sb.append(" r_syukei_kb skb ");
//		sb.append(" , ");
//		sb.append(" sys_sosasya sosasya ");
//
//		sb.append("WHERE ");
//		sb.append(" meisai.hinsyu_cd (+)= taikei.hinsyu_cd ");
//		sb.append("  AND ");
//		sb.append(" ctf.code_cd (+)= meisai.hinsyu_cd ");
//		sb.append("  AND ");
//		sb.append(" sosasya.user_id (+)= meisai.syorisya_user_id ");
//		sb.append("  AND ");
//		sb.append(" skb.syukei_kb_cd (+)= meisai.syukei_kb_cd ");
//
//		sb.append("ORDER BY ");
//		sb.append(" taikei.hinsyu_cd ");

		return sb.toString();

	}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getInsertSql( Object beanMst )
	{
		boolean befKanma = false;
		boolean aftKanma = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst650101_SyuukeiSyubetuDetailBean bean = (mst650101_SyuukeiSyubetuDetailBean)beanMst;
		StringBuffer sb = new StringBuffer();
//		sb.append("insert into ");
//		sb.append("R_SYUKEIHINSYU_HINSYU (");
//		if( bean.getSyukeihinsyuHinsyuSyukeiHinsyuCd() != null && bean.getSyukeihinsyuHinsyuSyukeiHinsyuCd() .trim().length() != 0 )
//		{
//			befKanma = true;
//			sb.append(" SYUKEI_HINSYU_CD");
//		}
//		if( bean.getSyukeihinsyuHinsyuHinsyuCd() != null && bean.getSyukeihinsyuHinsyuHinsyuCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" HINSYU_CD");
//		}
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_ts");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_user_id");
//		}
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" update_ts");
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" update_user_id");
//		}
//		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" delete_fg");
//		}
		sb.append("insert into ");
		sb.append("R_SYUKEIHINSYU_MEISAI (");
		if( bean.getSyukeiHinsyuCd() != null && bean.getSyukeiHinsyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukeihinsyu_cd");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinsyu_cd");
		}
		if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukei_kb_cd");
		}
		if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukei_kb_a_cd");
		}
		if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukei_kb_b_cd");
		}
		if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukei_kb_c_cd");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
		if( bean.getSyorisyaUserId() != null && bean.getSyorisyaUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syorisya_user_id");
		}


		sb.append(")Values(");


//		if( bean.getSyukeihinsyuHinsyuSyukeiHinsyuCd() != null && bean.getSyukeihinsyuHinsyuSyukeiHinsyuCd().trim().length() != 0 )
//		{
//			aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getSyukeihinsyuHinsyuSyukeiHinsyuCd() ) + "'");
//		}
//		if( bean.getSyukeihinsyuHinsyuHinsyuCd() != null && bean.getSyukeihinsyuHinsyuHinsyuCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getSyukeihinsyuHinsyuHinsyuCd() ) + "'");
//		}
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		}
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//		}
//		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
//		}

		if( bean.getSyukeiHinsyuCd() != null && bean.getSyukeiHinsyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiHinsyuCd() ) + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHinsyuCd() ) + "'");
		}
		if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiKbCd() ) + "'");
		}
		if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiKbCd().substring(0,2) ) + "'");
		}
		if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiKbCd().substring(2,4) ) + "'");
		}
		if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiKbCd().substring(4,6) ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getSyorisyaUserId() != null && bean.getSyorisyaUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyorisyaUserId() ) + "'");
		}

		sb.append(")");
		return sb.toString();
	}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getUpdateSql( Object beanMst )
	{
	boolean befKanma = false;
	boolean whereAnd = false;
//	DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	mst650101_SyuukeiSyubetuDetailBean bean = (mst650101_SyuukeiSyubetuDetailBean)beanMst;
	StringBuffer sb = new StringBuffer();
//	sb.append("update ");
//	sb.append("R_SYUKEIHINSYU_HINSYU set ");

//	if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//	{
//		if( befKanma ) sb.append(","); else befKanma = true;
//		sb.append(" insert_ts = ");
//		sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//	}
//	if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//	{
//		if( befKanma ) sb.append(","); else befKanma = true;
//		sb.append(" insert_user_id = ");
//		sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//	}
//	if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//	{
//		if( befKanma ) sb.append(","); else befKanma = true;
//		sb.append(" update_ts = ");
//		sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
//	}
//	if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//	{
//		if( befKanma ) sb.append(","); else befKanma = true;
//		sb.append(" update_user_id = ");
//		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//	}
//	if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
//	{
//		if( befKanma ) sb.append(","); else befKanma = true;
//		sb.append(" delete_fg = ");
//		sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
//	}

	sb.append("update ");
	sb.append("R_SYUKEIHINSYU_MEISAI set ");

	if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
	{
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" syukei_kb_cd = ");
		sb.append("'" + conv.convertString( bean.getSyukeiKbCd() ) + "'");
	}
	if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
	{
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" syukei_kb_a_cd = ");
		sb.append("'" + conv.convertString( bean.getSyukeiKbCd().substring(0,2) ) + "'");
	}
	if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
	{
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" syukei_kb_b_cd = ");
		sb.append("'" + conv.convertString( bean.getSyukeiKbCd().substring(2,4) ) + "'");
	}
	if( bean.getSyukeiKbCd() != null && bean.getSyukeiKbCd().trim().length() != 0 )
	{
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" syukei_kb_c_cd = ");
		sb.append("'" + conv.convertString( bean.getSyukeiKbCd().substring(4,6) ) + "'");
	}
	if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
	{
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" insert_ts = ");
		sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
	}
	if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
	{
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" update_ts = ");
		sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
	}
	if( bean.getSyorisyaUserId() != null && bean.getSyorisyaUserId().trim().length() != 0 )
	{
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" syorisya_user_id = ");
		sb.append("'" + conv.convertString( bean.getSyorisyaUserId() ) + "'");
	}


	sb.append(" WHERE");


//	if( bean.getSyukeihinsyuHinsyuSyukeiHinsyuCd() != null && bean.getSyukeihinsyuHinsyuSyukeiHinsyuCd().trim().length() != 0 )
//	{
//		whereAnd = true;
//		sb.append(" syukei_hinsyu_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getSyukeihinsyuHinsyuSyukeiHinsyuCd() ) + "'");
//	}
//	if( bean.getSyukeihinsyuHinsyuHinsyuCd() != null && bean.getSyukeihinsyuHinsyuHinsyuCd().trim().length() != 0 )
//	{
//		whereAnd = true;
//		sb.append(" AND hinsyu_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getSyukeihinsyuHinsyuHinsyuCd() ) + "'");
//	}

	if( bean.getSyukeiHinsyuCd() != null && bean.getSyukeiHinsyuCd().trim().length() != 0 )
	{
		whereAnd = true;
		sb.append(" syukeihinsyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyukeiHinsyuCd() ) + "'");
	}
	if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
	{
		whereAnd = true;
		sb.append(" AND hinsyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getHinsyuCd() ) + "'");
	}

	return sb.toString();
}

/**
 * 結合を行ったＤＭでは挿入、更新、削除は行えません。
 * 無理やり例外を上げるためにnullを返す。
 */
	public String getDeleteSql( Object beanMst )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst650101_SyuukeiSyubetuDetailBean bean = (mst650101_SyuukeiSyubetuDetailBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_SYUKEIHINSYU_MEISAI where ");
		sb.append(" syukeihinsyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyukeiHinsyuCd() ) + "'");
		sb.append(" AND ");
		sb.append(" hinsyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getHinsyuCd() ) + "'");
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
