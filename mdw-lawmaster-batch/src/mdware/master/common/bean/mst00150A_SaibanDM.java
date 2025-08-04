package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DataModule;

import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

public class mst00150A_SaibanDM extends DataModule {

	/**
	 * コンストラクタ
	 */
	public mst00150A_SaibanDM()
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
		mst000601_KoushinInfoBean bean = new mst000601_KoushinInfoBean();
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setInsertUserName( rest.getString("insert_user_name") );
		bean.setUpdateUserName( rest.getString("update_user_name") );
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
		return null;	
	}
		
	/**
	 * 自動採番マスタマスタ検索用ＳＱＬの生成を行う。
	 * 件数取得
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanCntSelectSql(String seqName){
			
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("cur_cnt,");
		sb.append("min_cnt,");
		sb.append("max_cnt,");
		sb.append("ketasu ");	
		sb.append("from ");
		sb.append("r_seq ");
		sb.append("where ");
		sb.append("saiban_id = '" + seqName + "' ");
		sb.append("for update ");

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
	 * タグ採番マスタ検索用ＳＱＬの生成を行う。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA08SelectSql(){
			
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("max(syohin_cd) as syohin_cd ");
		sb.append("from ");
		sb.append("r_saiban_a08 ");

		return sb.toString();
	}
	/**
	 * 自動採番マスタ(8桁商品)挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String getSaiban8KetaInsertSql( String syohinCd, String degitCd,String byNo,String userId  ) throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_saiban_a08 (");
		sb.append(" syohin_cd ");
		sb.append(",");
		sb.append(" check_degit_cd ");
		sb.append(",");
		sb.append(" jyotai_kanri_fg ");
		sb.append(",");
		sb.append(" by_no ");
		sb.append(",");
		sb.append(" comment_tx ");
		sb.append(",");
		sb.append(" haiban_dt ");
		sb.append(",");
		sb.append(" yoyaku_dt ");
		sb.append(",");
		sb.append(" delete_fg ");
		sb.append(",");
		sb.append(" insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id ");
		sb.append(",");
		sb.append(" update_ts ");
		sb.append(",");
		sb.append(" update_user_id ");
		sb.append(")Values(");
		sb.append("'" + syohinCd + "'");
		sb.append(",");		
		sb.append("'" + degitCd + "'");
		sb.append(",");		
		sb.append("'1'");
		sb.append(",");		
		sb.append("'" + byNo + "'");
		sb.append(",");		
		sb.append(" null ");
		sb.append(",");		
		sb.append(" null ");
		sb.append(",");	
		sb.append(" null ");
		sb.append(",");		
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");		
		sb.append(",");	
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "  '" );
		sb.append(",");		
		sb.append("'" + userId + "'");
		sb.append(",");		
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "  '" );
		sb.append(",");		
		sb.append("'" + userId + "'");
		sb.append(")");
		
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String getSaibanUpdateSql(String seqName,String nextVal ){
		
		StringBuffer sb = new StringBuffer();
		sb.append("update ");	
		sb.append(" r_seq ");
		sb.append("set ");
		sb.append(" cur_cnt = " + nextVal + " ");
		sb.append("where ");
		sb.append("saiban_id = '" + seqName + "' ");
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
