/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）サブクラスのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するサブクラスのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/19)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）サブクラスのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するサブクラスのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/19)初版作成
 */
public class mstA20301_SubClassDM extends DataModule {

	public mstA20301_SubClassDM()
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
	throws SQLException{
		
	mstA20301_SubClassBean bean = new mstA20301_SubClassBean();
	bean.setBumonCd(rest.getString("bumon_cd") );
	bean.setSubclassCd( rest.getString("subclass_cd"));
	bean.setKanjiNa(rest.getString("kanji_na") );
	bean.setKanaNa(rest.getString("kana_na") );
	bean.setKanjiRn( rest.getString("kanji_rn") );
	bean.setKanaRn( rest.getString("kana_rn") );
	bean.setDeleteFg( rest.getString("delete_fg") );
	bean.setInsertTs( rest.getString("insert_ts") );
	bean.setInsertUserId( rest.getString("insert_user_id") );
	bean.setUpdateTs( rest.getString("update_ts") );
	bean.setUpdateUserId( rest.getString("update_user_id") );
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
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("	   bumon_cd ");
		sb.append(" , ");
		sb.append("	   subclass_cd ");
		sb.append(" , ");
		sb.append("	   kanji_na ");
		sb.append(" , ");
		sb.append("    kana_na ");
		sb.append(" , ");
		sb.append("	   kana_rn ");
		sb.append(" , ");
		sb.append("	   delete_fg ");
		sb.append(" , ");
		sb.append("	   insert_ts ");
		sb.append(" , ");
		sb.append("    insert_user_id ");
		sb.append(" , ");
		sb.append("    update_ts ");
		sb.append(" , ");
		sb.append("    update_user_id ");
		sb.append(" from ");
		sb.append("	   r_subclass ");
		sb.append(" where ");
		sb.append("	   bumon_cd = '"+ StringUtility.charFormat(map.get("bumon_cd").toString().trim(),4,"0",true) +"' ");
		
		if( map.get("subclass_cd") != null && ((String)map.get("subclass_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("	   subclass_cd = '"+ map.get("subclass_cd").toString().trim() +"' ");
		}
		
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("	   delete_fg = '"+ map.get("delete_fg").toString().trim() +"' ");
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mstA20301_SubClassBean bean = (mstA20301_SubClassBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("	   r_subclass ");
		sb.append(" ( ");
		sb.append("	   bumon_cd ");
		sb.append(" , ");		
		sb.append("	   subclass_cd ");
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	   kanji_na ");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	   kana_na ");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	   kanji_rn ");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	   kana_rn ");
		}
		
		sb.append(",");
		sb.append(" insert_ts");
		
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id");
		}
		
		sb.append(",");
		sb.append(" update_ts");
		
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
		sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getBumonCd(),4,"0",true)) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSubclassCd() ) + "'");
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
		}		
		sb.append(",");
        try {		
        	sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}

		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		
		sb.append(",");
        try {		
		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mstA20301_SubClassBean bean = (mstA20301_SubClassBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_subclass set ");
		
		sb.append(" kanji_rn = ");
		sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		
		sb.append(","); 
		sb.append(" update_ts = ");

        try {		
		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}
        
        sb.append(","); 
		sb.append(" update_user_id = ");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		
		sb.append(","); 
		sb.append(" delete_fg = ");
		sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");

		sb.append(" WHERE ");
		
		sb.append(" bumon_cd=");
		sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		sb.append(" AND");
		sb.append(" subclass_cd = ");
		sb.append("'" + bean.getSubclassCd()+ "'");
		
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
		mstA20301_SubClassBean bean = (mstA20301_SubClassBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("	   r_subclass where ");
		sb.append("bumon_cd=");
		sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		sb.append(" AND");
		sb.append(" subclass_cd = ");
		sb.append("'" + bean.getSubclassCd()+ "'");
		return sb.toString();
	}
	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql1( Map map )
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("	   A.* ");		
		sb.append(" from ");
		sb.append("	   r_syohin A , r_subclass B  ");
		sb.append(" where ");
		sb.append("	   B.bumon_cd = '"+ StringUtility.charFormat(map.get("bumon_cd").toString().trim(),4,"0",true) +"' ");		
		sb.append(" and ");
		sb.append("	   B.subclass_cd = '"+ map.get("subclass_cd").toString().trim() +"' ");
		sb.append(" and ");
		sb.append("	   B.subclass_cd = A.subclass_cd");
		sb.append(" and ");
		sb.append("	   B.bumon_cd = A.bumon_cd");
		sb.append(" and ");
		sb.append("	   A.delete_fg = '0'");
		sb.append(" and ");
		sb.append("    A.YUKO_DT = MSP710101_GETSYOHINYUKODT(A.BUMON_CD, A.SYOHIN_CD,cast(null as char)) ");
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
