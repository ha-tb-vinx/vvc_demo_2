/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/02)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/02)初版作成
 */
public class mst520101_TenpoListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst520101_TenpoListDM()
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
		mst520101_TenpoListBean bean = new mst520101_TenpoListBean();
		bean.setTenpoCd( rest.getString("tenpo_cd") );
//		  ↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
//		bean.setKanjiRn( rest.getString("kanji_rn") );
		bean.setKanjiNa( rest.getString("kanji_na") );
		bean.setAddressKanjiNa( rest.getString("address_kanji_na") );
		bean.setYubinCd( rest.getString("yubin_cd") );
		bean.setTelCd( rest.getString("tel_cd") );
		bean.setInsertTs(rest.getString("insert_ts"));
		bean.setInsertUserName(rest.getString("insert_user_name"));
		bean.setUpdateTs(rest.getString("update_ts"));
		bean.setUpdateUserName(rest.getString("update_user_name"));
//		  ↑↑2006.06.19 zhouzt カスタマイズ修正↑↑
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
//		↓↓2006.08.02 baozy カスタマイズ修正↓↓
//		↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
		//店舗コード
		String tenpocd = conv.convertWhereString( (String)map.get("tenpo_cd_bef") );
//		↑↑2006.06.19 zhouzt カスタマイズ修正↑↑
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
//		  ↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
//		sb.append("tenpo_cd ");
		sb.append("substrb(ten.tenpo_cd, 4, 3) tenpo_cd");
		sb.append(", ");
//		sb.append("trim(kanji_rn) kanji_rn ");
		sb.append("trim(ten.kanji_na) kanji_na ");
//		  ↑↑2006.06.19 zhouzt カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("trim(ten.address_kanji_na) address_kanji_na ");
		sb.append(", ");
//     ↓↓移植(2006.05.30)↓↓		
        sb.append("concat(nvl(substr(ten.yubin_cd,1,3)||'-',''),");
        sb.append("nvl(substr(ten.yubin_cd, 4, 4),'')) as yubin_cd ");
//      ↑↑移植(2006.05.30)↑↑		
		sb.append(", ");
		sb.append("trim(ten.tel_cd) tel_cd ");
		sb.append(", ");
		sb.append("substr(ten.insert_ts, 1, 8) insert_ts , ");
		sb.append("user1.riyo_user_na insert_user_name , ");
		sb.append("substr(ten.update_ts, 1, 8) update_ts , ");
		sb.append("user2.riyo_user_na update_user_name ");
		sb.append("from R_TENPO ten ");
//		sb.append("  left join R_RIYO_USER user1 ");
//		sb.append("  on ten.insert_user_id = user1.riyo_user_id ");
//		sb.append("  left join r_riyo_user user2 ");
//		sb.append("  on ten.update_user_id = user2.riyo_user_id ");
		sb.append("  left join R_PORTAL_USER user1 ");
		sb.append("  on TRIM(ten.insert_user_id) = user1.user_id ");
		sb.append("  left join r_portal_user user2 ");
		sb.append("  on TRIM(ten.update_user_id) = user2.user_id ");

		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
//		↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
//			sb.append("tenpo_cd >= '" + conv.convertWhereString( (String)map.get("tenpo_cd_bef") ) + "'");
			sb.append("ten.tenpo_cd >= '" + StringUtility.charFormat(tenpocd, 6, "0", true) + "'");
			whereStr = andStr;
		} else {
			sb.append(whereStr);
			sb.append("ten.tenpo_cd >= '" + StringUtility.charFormat("000", 6, "0", true) + "'");
			whereStr = andStr;
		}
//		↑↑2006.06.19 zhouzt カスタマイズ修正↑↑

		// delete_fg に対するWHERE区
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}

		sb.append(" order by ");
		sb.append("ten.tenpo_cd");
//		↑↑2006.08.02 baozy カスタマイズ修正↑↑
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
