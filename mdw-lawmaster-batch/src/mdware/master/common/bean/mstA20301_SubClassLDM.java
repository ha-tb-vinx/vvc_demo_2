/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）サブクラスのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するサブクラスのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/19)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）サブクラスのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するサブクラスのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/19)初版作成
 */
public class mstA20301_SubClassLDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public mstA20301_SubClassLDM()
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
		mstA20301_SubClassLBean bean = new mstA20301_SubClassLBean();
		bean.setSubclassCd( rest.getString("subclass_cd") );		
		bean.setKanjiRn( rest.getString("kanji_rn") );
		bean.setInsertTs(rest.getString("insert_ts") );
		bean.setInsertUserId(rest.getString("insert_user_id") );
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("select ");		
		sb.append("	   r_subclass.subclass_cd ");
		sb.append(", ");
		sb.append("	   r_subclass.kanji_rn ");
		sb.append(", ");
		sb.append("	   r_subclass.insert_ts ");
		sb.append(", ");
		sb.append("    user1.riyo_user_na as insert_user_id ");
		sb.append(", ");
		sb.append("	   r_subclass.update_ts ");
		sb.append(", ");
		sb.append("	   user2.riyo_user_na as update_user_id ");
		sb.append(", ");
		sb.append("	   r_subclass.delete_fg ");	
		
		sb.append("	from ");
		sb.append("	   r_subclass ");
//		sb.append("	left outer join ");
//		sb.append("	   r_riyo_user user1");
//		sb.append("	on ");
//		sb.append("    r_subclass.insert_user_id = user1.riyo_user_id ");
//		sb.append("	left outer join ");
//		sb.append("	   r_riyo_user user2");
//		sb.append("	on ");		
//		sb.append("	   r_subclass.update_user_id = user2.riyo_user_id ");
		sb.append("	left outer join ");
		sb.append("	   r_portal_user user1");
		sb.append("	on ");
		sb.append("    TRIM(r_subclass.insert_user_id) = user1.user_id ");
		sb.append("	left outer join ");
		sb.append("	   r_portal_user user2");
		sb.append("	on ");		
		sb.append("	   TRIM(r_subclass.update_user_id) = user2.user_id ");
		sb.append("	where ");
		sb.append("		r_subclass.bumon_cd = '"+ conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
		sb.append("	and ");
		sb.append("		r_subclass.delete_fg = '0' ");
		sb.append("	and ");
		sb.append("		r_subclass.subclass_cd between '" + conv.convertWhereString(StringUtility.charFormat( (String)map.get("subclass_cd_from") , 5, "0", true)) + "'");
		sb.append("	and ");
		sb.append("	'" + conv.convertWhereString(StringUtility.charFormat( (String)map.get("subclass_cd_to") , 5, "0", true)) + "'");
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
		return null;
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
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
