/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流店舗マスタ（生鮮）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流店舗マスタ（生鮮）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/19)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流店舗マスタ（生鮮）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流店舗マスタ（生鮮）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/19)初版作成
 */
public class mst560101_ButuryuTenpoLDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst560101_ButuryuTenpoLDM()
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
		mst560101_ButuryuTenpoLBean bean = new mst560101_ButuryuTenpoLBean();
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setKanjiRn( rest.getString("kanji_rn") );
// 仕様変更による廃止
//		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
//		bean.setButuryuCenterCd( rest.getString("buturyu_center_cd") );
//		bean.setBcNa( rest.getString("bc_na") );
//		↓↓ＤＢバージョンアップによる変更
//		bean.setHaiso1Fg( rest.getString("haiso_1_fg") );
//		bean.setHaiso2Fg( rest.getString("haiso_2_fg") );
//		bean.setHaiso3Fg( rest.getString("haiso_3_fg") );
		bean.setBinKb( rest.getString("bin_kb") );
//		↑↑ＤＢバージョンアップによる変更
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setInsertUserNm( rest.getString("insert_user_nm") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setUpdateUserNm( rest.getString("update_user_nm") );
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
		sb.append("SELECT \n");
		sb.append("	TB1.TENPO_CD \n");
		sb.append("	, \n");
		sb.append("	TB1.KANJI_RN \n");
		sb.append("	, \n");
// 仕様変更による廃止
//		sb.append("	TB2.S_GYOSYU_CD \n");
//		sb.append("	, \n");

//		if (map.get("mode").equals("insert")) {
//			sb.append("	'' AS BC_NA \n");
//		} else {
//			sb.append("	TB3.KANJI_RN AS BC_NA \n");
//		}
//		sb.append("	, \n");
//		sb.append("	TB2.BUTURYU_CENTER_CD \n");
//		sb.append("	, \n");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		sb.append("	TB2.HAISO_1_FG \n");
//		sb.append("	, \n");
//		sb.append("	TB2.HAISO_2_FG \n");
//		sb.append("	, \n");
//		sb.append("	TB2.HAISO_3_FG \n");
//		sb.append("	, \n");
		sb.append("	TB2.BIN_KB \n");
		sb.append("	, \n");
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		sb.append("	TB2.INSERT_TS \n");
		sb.append("	, \n");
		sb.append("	TB2.INSERT_USER_ID \n");
		sb.append("	, \n");
		sb.append("	TRIM(SY1.USER_NA) AS INSERT_USER_NM \n");
		sb.append("	, \n");
		sb.append("	TB2.UPDATE_TS \n");
		sb.append("	, \n");
		sb.append("	TB2.UPDATE_USER_ID \n");
		sb.append("	, \n");
		sb.append("	TRIM(SY2.USER_NA) AS UPDATE_USER_NM \n");
		sb.append("FROM \n");
		sb.append("	( \n");
		sb.append("	SELECT \n");
		sb.append("		TENPO_CD \n");
		sb.append("		, \n");
		sb.append("		KANJI_RN \n");
		sb.append("	FROM \n");
		sb.append("		R_TENPO \n");
		sb.append("	WHERE \n");
		sb.append("		TENPO_KB = '1' \n");
		sb.append("		AND \n");
		sb.append("		DELETE_FG = '0') TB1 \n");
		sb.append("		, \n");
		sb.append("		( \n");
		sb.append("	SELECT \n");
		sb.append("		TENPO_CD \n");
//仕様変更による廃止
//		sb.append("		, \n");
//		sb.append("		S_GYOSYU_CD \n");
//		sb.append("		, \n");
//		sb.append("		BUTURYU_CENTER_CD \n");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		sb.append("		, \n");
//		sb.append("		HAISO_1_FG \n");
//		sb.append("		, \n");
//		sb.append("		HAISO_2_FG \n");
//		sb.append("		, \n");
//		sb.append("		HAISO_3_FG \n");
		sb.append("		, \n");
		sb.append("		BIN_KB \n");
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		sb.append("		, \n");
		sb.append("		INSERT_TS \n");
		sb.append("		, \n");
		sb.append("		INSERT_USER_ID \n");
		sb.append("		, \n");
		sb.append("		UPDATE_TS \n");
		sb.append("		, \n");
		sb.append("		UPDATE_USER_ID \n");
		sb.append("	FROM \n");
		sb.append("		R_BUTURYUTENPO \n");
		sb.append("	WHERE \n");
// 仕様変更による廃止
//		sb.append("		(S_GYOSYU_CD IS NULL \n");
//		sb.append("		OR \n");

// 仕様変更による廃止		
//		String wk = getWhereSqlStr(map, "s_gyosyu_cd", "");
//		sb.append(wk);
		
//		sb.append("		) \n");
//		sb.append("		AND \n");		
		sb.append("		DELETE_FG = '0'  ) TB2 \n");
		sb.append("		, \n");
		sb.append("		SYS_SOSASYA SY1 \n");
		sb.append("		, \n");
		sb.append("		SYS_SOSASYA SY2 \n");

		if (map.get("mode").equals("update")) {
			sb.append("		, \n");
//BUGNO-S005 2005.04.21 S.Takahashi START
//			sb.append("		R_TENPO TB3\n");
			sb.append("		(SELECT * \n");
			sb.append("		 FROM   R_TENPO \n");
			sb.append("		 WHERE DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' \n");
			sb.append("		) TB3\n");
//BUGNO-S005 2005.04.21 S.Takahashi END
		}
		
		sb.append("	WHERE \n");
		if (map.get("mode").equals("insert")) {
			sb.append("		TB1.TENPO_CD = TB2.TENPO_CD(+) \n");
// 仕様変更による廃止
//			sb.append(" and \n");
//			sb.append("     TB2.S_GYOSYU_CD IS NULL\n");
//			sb.append("		AND \n");
//			sb.append(" NVL(TB2.HAISO_1_FG,' ') = ' ' ");
//			sb.append("		AND \n");
//			sb.append(" NVL(TB2.HAISO_2_FG,' ') = ' ' ");
//			sb.append("		AND \n");
//			sb.append(" NVL(TB2.HAISO_3_FG,' ') = ' ' ");
			sb.append("		AND \n");
			sb.append(" NVL(TB2.BIN_KB,' ') = ' ' ");

		}
		if (map.get("mode").equals("update")) {
			sb.append("		TB1.TENPO_CD = TB2.TENPO_CD \n");
			
			sb.append("		AND \n");
			sb.append("		TB1.TENPO_CD = TB3.TENPO_CD \n");
			sb.append("		AND \n");
			sb.append("		TB2.TENPO_CD = TB3.TENPO_CD \n");
// 仕様変更による廃止
//			sb.append("		AND \n");
//BUGNO-S005 2005.04.21 S.Takahashi START
//			sb.append("		TB3.TENPO_CD = TB2.BUTURYU_CENTER_CD \n");
// 仕様変更による廃止
//			sb.append("		TB3.TENPO_CD(+) = TB2.BUTURYU_CENTER_CD \n");
//BUGNO-S005 2005.04.21 S.Takahashi END
		}
		sb.append("		AND \n");
		sb.append("		TB2.INSERT_USER_ID = SY1.USER_ID(+) \n");
		sb.append("		AND \n");
		sb.append("		TB2.UPDATE_USER_ID = SY2.USER_ID(+) \n");

//		sb.append(") \n");
		sb.append("ORDER BY \n");
		sb.append("	TB1.TENPO_CD \n");

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

}
