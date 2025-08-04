/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）カラーサイズマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するカラーサイズマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author D.Matsumoto
 * @version 1.0(2006/03/17) D.Matsumoto 初版作成
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

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）カラーサイズマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するカラーサイズマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst930101_ColorSizeDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst930101_ColorSizeDM()
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
		mst930101_ColorSizeBean bean = new mst930101_ColorSizeBean();

		bean.setHinsyuCd(rest.getString("HINSYU_CD"));
		bean.setColorCd(rest.getString("COLOR_CD"));
		bean.setColorNm(rest.getString("COLOR_NM"));
		bean.setSizeCd(rest.getString("SIZE_CD"));
		bean.setSizeNm(rest.getString("SIZE_NM"));
		bean.setColorSizeCd(rest.getString("COLOR_SIZE_CD"));
		bean.setInsertTs(rest.getString("INSERT_TS"));
		bean.setInsertUserId(rest.getString("INSERT_USER_ID"));
		bean.setUpdateTs(rest.getString("UPDATE_TS"));
		bean.setUpdateUserId(rest.getString("UPDATE_USER_ID"));
		bean.setDeleteFg(rest.getString("DELETE_FG"));
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
		StringBuffer sb = new StringBuffer();
		//▼SELECT句
		sb.append(" SELECT ");
		sb.append(" cs.HINSYU_CD as HINSYU_CD,");
		sb.append(" cs.COLOR_CD as COLOR_CD,");
		sb.append(" na1.COLOR_NM as COLOR_NM,");
		sb.append(" cs.SIZE_CD as SIZE_CD,");
		sb.append(" na2.SIZE_NM AS SIZE_NM,");
		sb.append(" cs.COLOR_SIZE_CD as COLOR_SIZE_CD,");
		sb.append(" cs.INSERT_TS as INSERT_TS,");
		sb.append(" cs.INSERT_USER_ID as INSERT_USER_ID,");
		sb.append(" cs.UPDATE_TS as UPDATE_TS,");
		sb.append(" cs.UPDATE_USER_ID as UPDATE_USER_ID,");
		sb.append(" cs.DELETE_FG as DELETE_FG");
		//▼FROM句
		sb.append(" FROM ");
		sb.append("   R_SAIBAN_COLORSIZE cs,");
		sb.append(" ( SELECT ");
		sb.append("    na.CODE_CD AS COLOR_CD,");
		sb.append("    na.KANJI_NA AS COLOR_NM");
		sb.append("   FROM ");
		sb.append("    R_NAMECTF na");
		sb.append("   WHERE ");
		sb.append("       na.DELETE_FG = '0' ");
		sb.append("   AND na.SYUBETU_NO_CD = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal) +"' ");
		sb.append("  )na1, ");
		sb.append(" ( SELECT ");
		sb.append("    SUBSTR(na0.CODE_CD,5) AS SIZE_CD,");
		sb.append("    na0.KANJI_NA AS SIZE_NM");
		sb.append("   FROM ");
		sb.append("    R_NAMECTF na0");
		sb.append("   WHERE ");
		sb.append("       na0.DELETE_FG = '0' ");
		sb.append("   AND na0.SYUBETU_NO_CD = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal) +"' ");
		sb.append("   AND na0.CODE_CD LIKE '"+ map.get("tx_hinsyu_cd") +"%' ");
		sb.append("  )na2 ");
		//▼WHERE句
		sb.append(" WHERE ");
		sb.append("   cs.DELETE_FG <> '1' ");
		sb.append(" AND  cs.COLOR_CD = na1.COLOR_CD (+) ");
		sb.append(" AND  cs.SIZE_CD = na2.SIZE_CD (+)");
		//品種コード
		if (map.get("tx_hinsyu_cd") != null && map.get("tx_hinsyu_cd").toString().trim().length() > 0 ) {
			sb.append(" AND cs.HINSYU_CD = '"+ map.get("tx_hinsyu_cd") +"'");
		}
		//カラーコード
		if (map.get("tx_color_cd") != null && map.get("tx_color_cd").toString().trim().length() > 0 ) {
			sb.append(" AND cs.COLOR_CD >= '"+ map.get("tx_color_cd") +"'");
		}
		//サイズコード
		if (map.get("tx_size_cd") != null && map.get("tx_size_cd").toString().trim().length() > 0 ) {
			sb.append(" AND cs.SIZE_CD >= '"+ map.get("tx_size_cd") +"'");
		}
		//カラーサイズコード
		if (map.get("tx_colorsize_cd") != null && map.get("tx_colorsize_cd").toString().trim().length() > 0 ) {
			sb.append(" AND cs.COLOR_SIZE_CD >= '"+ map.get("tx_colorsize_cd") +"'");
		}


		//▼ORDER BY句
		sb.append(" ORDER BY ");
		if (map.get("rb_kensaku_kb").equals("2")){
			sb.append(" cs.COLOR_SIZE_CD,");
		}
		sb.append(" cs.COLOR_CD,");
		sb.append(" cs.SIZE_CD");

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
