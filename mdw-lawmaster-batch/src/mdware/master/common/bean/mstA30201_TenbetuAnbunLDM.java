/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author magp
 * @version 1.0(2006/07/19)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author magp
 * @version 1.0(2006/07/19)初版作成
 */
public class mstA30201_TenbetuAnbunLDM extends DataModule  {
	
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mstA30201_TenbetuAnbunLDM()
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
	protected Object instanceBean( ResultSet rest ) throws SQLException {
		mstA30201_TenbetuAnbunLBean bean = new mstA30201_TenbetuAnbunLBean();
		bean.setTenpoCd(encodingString(rest.getString("tenpo_cd")));
		bean.setTenpoNa(encodingString(rest.getString("tenpo_na")));
		bean.setAnbunRt(encodingString(rest.getString("anbun_rt")));
		bean.setInsertTs(encodingString(rest.getString("insert_ts")));
		bean.setInsertUserId(encodingString(rest.getString("insert_user_id")));
		bean.setUpdateTs(encodingString(rest.getString("update_ts")));
		bean.setUpdateUserId(encodingString(rest.getString("update_user_id")));
		bean.setDeleteFg(encodingString(rest.getString("delete_fg")));	
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
		sb.append(" select ");
		sb.append(" r_tengroup.tenpo_cd as tenpo_cd");
		sb.append(", ");
		sb.append(" CASE r_tengroup.tenpo_cd WHEN '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' THEN ");
		sb.append("   	'全店' ");
		sb.append(" ELSE ");
		sb.append("   	r_tenpo.kanji_rn ");
		sb.append(" END as tenpo_na ");
		sb.append(", ");
		sb.append(" r_tengroup.anbun_rt as anbun_rt");
		sb.append(", ");
		sb.append(" r_tengroup.insert_ts as insert_ts");
		sb.append(", ");
		sb.append(" r_tengroup.insert_user_id as insert_user_id");
		sb.append(", ");
		sb.append(" r_tengroup.update_ts as update_ts");
		sb.append(", ");
		sb.append(" r_tengroup.update_user_id as update_user_id");
		sb.append(", ");
		sb.append(" r_tengroup.delete_fg as delete_fg");
		sb.append(" from r_tengroup"); 
		sb.append(" LEFT JOIN r_tenpo ON r_tengroup.tenpo_cd = r_tenpo.tenpo_cd");
		sb.append(" where ");
		sb.append(" r_tengroup.bumon_cd = '" + conv.convertWhereString((String)map.get("bumon_cd")) + "' ");
		sb.append(" and ");
		sb.append(" r_tengroup.groupno_cd = '" + conv.convertWhereString((String)map.get("groupno_cd")) + "' ");
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append("AND ");
			sb.append("r_tengroup.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "' ");
		}
		sb.append(" and ");
		sb.append(" r_tengroup.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append(" and ");
		sb.append(" r_tengroup.yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
		sb.append(" order by ");
		sb.append(" r_tengroup.rank_nb");
		sb.append(",");
		sb.append(" r_tengroup.tenpo_cd");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateSql( Object beanMst ) {
		mstA30201_TenbetuAnbunLBean bean = (mstA30201_TenbetuAnbunLBean)beanMst;
		StringBuffer sb = new StringBuffer();
		
		sb.append("update ");
		sb.append("r_tengroup set ");
		sb.append(" anbun_rt = ");
		sb.append(bean.getAnbunRtString() + ", ");		
		sb.append(" update_ts = ");
		try {
			sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ,");
		}catch (SQLException e){
			e.printStackTrace();
		}
		sb.append(" update_user_id = ");
		sb.append(" '" + bean.getUpdateUserId() + "' ");		
		sb.append(" where");
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		sb.append(" and ");
		sb.append(" groupno_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getGroupnoCd() ) + "'");
		sb.append(" and ");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append(" and ");
		sb.append(" delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append(" and ");
		sb.append(" yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
		return sb.toString();

	}
	
	public String getInsertSql(Object bean) {
		return null;
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql( Object beanMst ){
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
