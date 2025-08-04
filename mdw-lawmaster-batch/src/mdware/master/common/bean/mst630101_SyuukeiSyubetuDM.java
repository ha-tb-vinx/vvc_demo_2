/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計種別マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計種別マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計種別マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計種別マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
public class mst630101_SyuukeiSyubetuDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst630101_SyuukeiSyubetuDM()
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
	}
	/**
	 * 検索後にＢＥＡＮをインスタンス化する所。
	 * 検索した結果セットをＢＥＡＮとして持ち直す。
	 * DataModuleから呼び出され返したObjectをListに追加する。
	 * @param rest ResultSet
	 * @throws SQLException
	 * @return Object インスタンス化されたＢＥＡＮ
	 */
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		mst630101_SyuukeiSyubetuBean bean = new mst630101_SyuukeiSyubetuBean();
		bean.setSyukeiSyubetuCd( rest.getString("syukeisyubetu_cd") );
//		bean.setKanjiNa( rest.getString("kanji_na") );
//		bean.setKanaNa( rest.getString("kana_na") );
		bean.setInsertTs( rest.getString("insert_ts") );
//		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
//		bean.setUpdateUserId( rest.getString("update_user_id") );
//		bean.setDeleteFg( rest.getString("delete_fg") );
//		bean.setUserNaInsert( rest.getString("user_na_insert") );
//		bean.setUserNaUpdate( rest.getString("user_na_update") );
		bean.setSyukeiSyubetuNa( rest.getString("syukeisyubetu_na") );
		bean.setSyukeiSyubetuKa( rest.getString("syukeisyubetu_ka") );
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
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append("   trim(syukeisyubetu_cd) as syukeisyubetu_cd,");
		sb.append("   syukeisyubetu_na,");
		sb.append("   syukeisyubetu_ka,");
		sb.append("   insert_ts,");
		sb.append("   update_ts,");
		sb.append("   syorisya_user_id,");
		sb.append("   table1.user_na as syorisya_user_na");
		sb.append(" FROM ");
		sb.append("   R_SYUKEISYUBETU, ");
		sb.append("   SYS_SOSASYA table1 ");
		sb.append(" WHERE ");
		sb.append("   table1.user_id (+)= syorisya_user_id  ");
		sb.append(" AND table1.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");

		//集計種別コードが入力された場合
		if( map.get("syukeisyubetu_cd_bef") != null && ((String)map.get("syukeisyubetu_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("trim(syukeisyubetu_cd) >= '" + conv.convertWhereString( (String)map.get("syukeisyubetu_cd_bef") ) + "'");
		}
		
		sb.append(" ORDER BY");
		sb.append("   syukeisyubetu_cd ");
		
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
		mst630101_SyuukeiSyubetuBean bean = (mst630101_SyuukeiSyubetuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_SYUKEISYUBETU (");
		if( bean.getSyukeiSyubetuCd() != null && bean.getSyukeiSyubetuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" syukeisyubetu_cd");
		}
		if( bean.getSyukeiSyubetuNa() != null && bean.getSyukeiSyubetuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukeisyubetu_na");
		}
		if( bean.getSyukeiSyubetuKa() != null && bean.getSyukeiSyubetuKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukeisyubetu_ka");
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


		if( bean.getSyukeiSyubetuCd() != null && bean.getSyukeiSyubetuCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiSyubetuCd() ) + "'");
		}
		if( bean.getSyukeiSyubetuNa() != null && bean.getSyukeiSyubetuNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiSyubetuNa() ) + "'");
		}
		if( bean.getSyukeiSyubetuKa() != null && bean.getSyukeiSyubetuKa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukeiSyubetuKa() ) + "'");
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
		mst630101_SyuukeiSyubetuBean bean = (mst630101_SyuukeiSyubetuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_SYUKEISYUBETU set ");
		if( bean.getSyukeiSyubetuNa() != null && bean.getSyukeiSyubetuNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" syukeisyubetu_na = ");
			sb.append("'" + conv.convertString( bean.getSyukeiSyubetuNa() ) + "'");
		}
		

//		2006/01/25 kim START 名称（カナ）を入力しなくてもDBで更新できるように修正。
			 if( bean.getSyukeiSyubetuKa() != null)// && bean.getSyukeiSyubetuKa().trim().length() != 0 )
			 {
				 if( befKanma ) sb.append(","); else befKanma = true;
				 sb.append(" syukeisyubetu_ka = ");
				 sb.append("'" + conv.convertString( bean.getSyukeiSyubetuKa() ) + "'");
			 }
//		2006/01/25 kim END

		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		//if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		//{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append(" TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");
			//sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		//}
		if( bean.getSyorisyaUserId() != null && bean.getSyorisyaUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syorisya_user_id = ");
			sb.append("'" + conv.convertString( bean.getSyorisyaUserId() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getSyukeiSyubetuCd() != null && bean.getSyukeiSyubetuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" syukeisyubetu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyukeiSyubetuCd() ) + "'");
		}
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql( Object beanMst )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst630101_SyuukeiSyubetuBean bean = (mst630101_SyuukeiSyubetuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_SYUKEISYUBETU where ");
		sb.append(" syukeisyubetu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyukeiSyubetuCd() ) + "'");
		return sb.toString();
	}

	/**
	 * JDK1.4からは使用できるようになったString.replaceAllをJDK1.3以前用に作成する。
	 * @param base
	 * @param before
	 * @param after
	 * @return sb
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
