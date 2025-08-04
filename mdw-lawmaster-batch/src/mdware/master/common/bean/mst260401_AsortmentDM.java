/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/25)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst260401_AsortmentDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst260401_AsortmentDM()
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
		mst260401_AsortmentBean bean = new mst260401_AsortmentBean();
		bean.setColorCd( rest.getString("color_cd") );
		bean.setSizeCd( rest.getString("size_cd") );
		bean.setColorNm( rest.getString("color_nm") );
		bean.setSizeNm( rest.getString("size_nm") );
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
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("select \n ");
		sb.append("	asort.color_cd ");
		sb.append("	, \n ");
		sb.append("	asort.size_cd ");
		sb.append("	, \n ");
		sb.append("	name1.kanji_rn  color_nm ");
		sb.append("	, \n ");
		sb.append("	name2.kanji_rn  size_nm \n ");
		sb.append("from \n ");
//		↓↓ＤＢバージョンアップによる変更（2005.05.25）
//		sb.append("	r_asortment asort ");
		sb.append("	r_assortment asort ");
//		↑↑ＤＢバージョンアップによる変更（2005.05.25）
		sb.append("	, \n ");
		sb.append("	r_namectf name1 ");
		sb.append("	, \n ");
		sb.append("	r_namectf name2 \n ");
		sb.append("where \n ");
		sb.append("	name1.code_cd = asort.color_cd ");
		sb.append("	and \n ");
		sb.append("	name1.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal) +"' ");
		sb.append("	and \n ");
		sb.append("	name2.code_cd = asort.size_cd ");
		sb.append("	and \n ");
		sb.append("	name2.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal) +"' ");
		sb.append("	and \n ");
		sb.append(" asort.syohin_cd = '" + conv.convertWhereString(mst000401_LogicBean.chkNullString((String)map.get("syohin_cd")).trim()) + "' ");
		sb.append("	and \n ");
		sb.append(" asort.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' \n ");
		sb.append("order by asort.color_cd, asort.size_cd ");

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
