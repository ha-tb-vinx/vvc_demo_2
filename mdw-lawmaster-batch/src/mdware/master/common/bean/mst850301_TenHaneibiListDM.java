/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/14)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/14)初版作成
 */
public class mst850301_TenHaneibiListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst850301_TenHaneibiListDM()
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
		mst850301_TenHaneibiListBean bean = new mst850301_TenHaneibiListBean();
		bean.setCheckFlg("0");
		bean.setOptFlg("0");
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setKanjiRn( rest.getString("kanji_rn") );
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setThemeCd( rest.getString("theme_cd") );
		bean.setKeiryoHankuCd( rest.getString("keiryo_hanku_cd") );
		bean.setSyohinYobidasi( rest.getString("syohin_yobidasi") );
		bean.setHaneiDt( rest.getString("hanei_dt") );
		bean.setTenHaneiDt( rest.getString("ten_hanei_dt") );
		bean.setUpdateTs( rest.getString("update_ts") );
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


		String table_kbn = (String)map.get("table_kbn");
		//計量器マスタから遷移の場合
		if(table_kbn.equals("1") ){
			sb.append(" SELECT ");
			sb.append(" DECODE(TBL2.TEN_HANEI_DT,NULL, "
						+ mst000801_DelFlagDictionary.IRU.getCode() 
						+ "," + mst000801_DelFlagDictionary.INAI.getCode() 
						+ ") AS DELETE_FG, ");
			sb.append("		TBL1.TENPO_CD, ");
			sb.append("		TBL1.KANJI_RN, ");
			sb.append("		TBL2.S_GYOSYU_CD, ");
			sb.append("		TBL2.THEME_CD, ");
			sb.append("		TBL2.KEIRYO_HANKU_CD, ");
			sb.append("		TBL2.SYOHIN_YOBIDASI, ");
			sb.append("		TBL2.HANEI_DT, ");
			sb.append("		TBL2.TEN_HANEI_DT, ");
			sb.append("		TBL2.UPDATE_TS ");
			sb.append(" FROM ");
			sb.append("		R_TENPO TBL1, ");
			sb.append("		( \n");
			sb.append("			SELECT \n");
//			sb.append("				TBL1.S_GYOSYU_CD, ");
//			sb.append("				TBL1.THEME_CD, ");
//			sb.append("				TBL1.KEIRYO_HANKU_CD, ");
//			sb.append("				TBL1.SYOHIN_YOBIDASI, ");
//			sb.append("				TBL1.HANEI_DT, ");
			sb.append("				TBL2.S_GYOSYU_CD, \n");
			sb.append("				TBL2.THEME_CD, \n");
			sb.append("				TBL2.KEIRYO_HANKU_CD, \n");
			sb.append("				TBL2.SYOHIN_YOBIDASI, \n");

			sb.append("             (\n");			
			sb.append("              SELECT HANEI_DT FROM R_KEIRYOKI TBL1\n");			
			sb.append("                     WHERE TBL1.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'\n");			
			sb.append("			            AND TBL1.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "' \n");
			sb.append("			            AND TBL1.THEME_CD = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "' \n");
			sb.append("			            AND TBL1.KEIRYO_HANKU_CD = '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd") ) + "' \n");
			sb.append("			            AND TBL1.SYOHIN_YOBIDASI = '" + conv.convertWhereString( (String)map.get("syohin_yobidasi") ) + "' \n");
			sb.append("             ) AS HANEI_DT,\n");			
			
			sb.append("				DECODE(TBL2.DELETE_FG,'1',NULL,TBL2.TEN_HANEI_DT) TEN_HANEI_DT, \n");
			sb.append("				TBL2.TENPO_CD, \n");
			sb.append("				TBL2.UPDATE_TS \n");
			sb.append("			FROM \n");
//			sb.append("				R_KEIRYOKI TBL1, \n");
			sb.append("				R_YOBIDASINO_TENHANEIBI TBL2 \n");
			sb.append("			WHERE \n");
//			sb.append("				TBL1.DELETE_FG = '0' ");
			sb.append("				TBL2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' \n");
//			sb.append("			AND	TBL1.S_GYOSYU_CD  = TBL2.S_GYOSYU_CD(+) ");
//			sb.append("			AND TBL1.THEME_CD  = TBL2.THEME_CD(+) ");
//			sb.append("			AND TBL1.KEIRYO_HANKU_CD  = TBL2.KEIRYO_HANKU_CD(+) ");
//			sb.append("			AND TBL1.SYOHIN_YOBIDASI  = TBL2.SYOHIN_YOBIDASI(+) ");
//			sb.append("			AND TBL1.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "' ");
//			sb.append("			AND TBL1.THEME_CD = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "' ");
//			sb.append("			AND TBL1.KEIRYO_HANKU_CD = '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd") ) + "' ");
//			sb.append("			AND TBL1.SYOHIN_YOBIDASI = '" + conv.convertWhereString( (String)map.get("syohin_yobidasi") ) + "' ");
			sb.append("			AND TBL2.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "' \n");
			sb.append("			AND TBL2.THEME_CD = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "' \n");
			sb.append("			AND TBL2.KEIRYO_HANKU_CD = '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd") ) + "' \n");
			sb.append("			AND TBL2.SYOHIN_YOBIDASI = '" + conv.convertWhereString( (String)map.get("syohin_yobidasi") ) + "' \n");
			sb.append("		) TBL2 ");
			sb.append("	WHERE ");
			sb.append("		TBL1.TENPO_CD = TBL2.TENPO_CD(+) ");
			sb.append("	AND	TBL1.TENPO_KB = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//BUGNO-S045 2005.04.27 T.Makuta START
			sb.append("	AND	TBL1.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//BUGNO-S045 2005.04.27 T.Makuta END
			sb.append(" ORDER BY ");
			sb.append("		TENPO_CD ");
		}



		//テーマ別アイテム一覧の場合
		if(table_kbn.equals("2") ){
/*
			sb.append("	SELECT ");
			sb.append(" DECODE(TBL2.TEN_HANEI_DT,NULL, "
						+ mst000801_DelFlagDictionary.IRU.getCode() 
						+ "," + mst000801_DelFlagDictionary.INAI.getCode() 
						+ ") AS DELETE_FG, ");
			sb.append("		TBL1.TENPO_CD, ");
			sb.append("		TBL1.KANJI_RN, ");
			sb.append("		TBL2.S_GYOSYU_CD, ");
			sb.append("		TBL2.THEME_CD, ");
			sb.append("		TBL2.KEIRYO_HANKU_CD, ");
			sb.append("		TBL2.SYOHIN_YOBIDASI, ");
			sb.append("		TBL2.HANEI_DT, ");
			sb.append("		TBL2.TEN_HANEI_DT, ");
			sb.append("		TBL2.UPDATE_TS ");
			sb.append(" FROM ");
			sb.append("		R_TENPO TBL1, ");
			sb.append("		( ");
			sb.append("			SELECT ");
			sb.append("				TBL1.S_GYOSYU_CD, ");
			sb.append("				TBL1.THEME_CD, ");
			sb.append("				'' KEIRYO_HANKU_CD, ");
			sb.append("				'' SYOHIN_YOBIDASI, ");
			sb.append("				TBL1.HANEI_DT, ");
			sb.append("				DECODE(TBL1.DELETE_FG,'1',NULL,TBL1.TEN_HANEI_DT) TEN_HANEI_DT, ");
			sb.append("				TBL1.TENPO_CD, ");
			sb.append("				TBL1.UPDATE_TS ");
			sb.append("			FROM ");
			sb.append("				R_THEME_TENHANEIBI TBL1 ");
			sb.append("			WHERE ");
			sb.append("				TBL1.DELETE_FG = '0' ");
			sb.append("			    TBL1.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "' ");
			sb.append("			AND	TBL1.THEME_CD = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "' ");
			sb.append("		) TBL2 ");
			sb.append(" WHERE ");
			sb.append("		TBL1.TENPO_CD = TBL2.TENPO_CD(+) ");
			sb.append("	AND	TBL1.TENPO_KB = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
			sb.append(" ORDER BY ");
			sb.append("		TENPO_CD ");
*/
			sb.append("	SELECT ");
			sb.append(" 	'0' AS DELETE_FG, ");
			sb.append("		TBL1.TENPO_CD, ");
			sb.append("		TBL1.KANJI_RN, ");
			sb.append("		'' S_GYOSYU_CD, ");
			sb.append("		'' THEME_CD, ");
			sb.append("		'' KEIRYO_HANKU_CD, ");
			sb.append("		'' SYOHIN_YOBIDASI, ");
			sb.append("		'' HANEI_DT, ");
			sb.append("		'' TEN_HANEI_DT, ");
			sb.append("		'' UPDATE_TS ");
			sb.append(" FROM ");
			sb.append("		R_TENPO TBL1 ");
			sb.append(" WHERE ");
			sb.append("		TBL1.TENPO_KB = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//			BUGNO-S045 2005.04.27 T.Makuta START
			sb.append("	AND	TBL1.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//			BUGNO-S045 2005.04.27 T.Makuta END
			sb.append(" ORDER BY ");
			sb.append("		TENPO_CD ");
		}


		//計量器テーマから遷移の場合
		if(table_kbn.equals("3") ){
/*
			sb.append("	SELECT ");
			sb.append(" DECODE(TBL2.TEN_HANEI_DT,NULL, "
						+ mst000801_DelFlagDictionary.IRU.getCode() 
						+ "," + mst000801_DelFlagDictionary.INAI.getCode() 
						+ ") AS DELETE_FG, ");
			sb.append("		TBL1.TENPO_CD, ");
			sb.append("		TBL1.KANJI_RN, ");
			sb.append("		TBL2.S_GYOSYU_CD, ");
			sb.append("		TBL2.THEME_CD, ");
			sb.append("		TBL2.KEIRYO_HANKU_CD, ");
			sb.append("		TBL2.SYOHIN_YOBIDASI, ");
			sb.append("		TBL2.HANEI_DT, ");
			sb.append("		TBL2.TEN_HANEI_DT, ");
			sb.append("		TBL2.UPDATE_TS ");
			sb.append("	FROM ");
			sb.append("		R_TENPO TBL1, ");
			sb.append("		( ");
			sb.append("			SELECT ");
			sb.append("				TBL1.S_GYOSYU_CD, ");
			sb.append("				TBL1.THEME_CD, ");
			sb.append("				'' KEIRYO_HANKU_CD, ");
			sb.append("				'' SYOHIN_YOBIDASI, ");
			sb.append("				TBL1.HANEI_DT, ");
			sb.append("				DECODE(TBL2.DELETE_FG,'1',NULL,TBL2.TEN_HANEI_DT) TEN_HANEI_DT, ");
			sb.append("				TBL2.TENPO_CD, ");
			sb.append("				TBL2.UPDATE_TS ");
			sb.append("			FROM ");
			sb.append("				R_KEIRYOKI_THEME TBL1, ");
			sb.append("				R_THEME_TENHANEIBI TBL2 ");
			sb.append("			WHERE ");
			sb.append("				TBL1.DELETE_FG = '0' ");
//			sb.append("			AND	TBL2.DELETE_FG = '0' ");
			sb.append("			AND	TBL1.S_GYOSYU_CD  = TBL2.S_GYOSYU_CD(+) ");
			sb.append("			AND	TBL1.THEME_CD  = TBL2.THEME_CD(+) ");
			sb.append("			AND	TBL1.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "' ");
			sb.append("			AND	TBL1.THEME_CD = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "' ");
			sb.append("		) TBL2 ");
			sb.append("	WHERE ");
			sb.append("		TBL1.TENPO_CD = TBL2.TENPO_CD(+) ");
			sb.append("	AND	TBL1.TENPO_KB = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
			sb.append("	ORDER BY ");
			sb.append("		TENPO_CD ");
*/
			sb.append("	SELECT ");
			sb.append(" DECODE(TBL2.TEN_HANEI_DT,NULL, "
						+ mst000801_DelFlagDictionary.IRU.getCode() 
						+ "," + mst000801_DelFlagDictionary.INAI.getCode() 
						+ ") AS DELETE_FG, ");
			sb.append("		TBL1.TENPO_CD, ");
			sb.append("		TBL1.KANJI_RN, ");
			sb.append("		TBL2.S_GYOSYU_CD, ");
			sb.append("		TBL2.THEME_CD, ");
			sb.append("		TBL2.KEIRYO_HANKU_CD, ");
			sb.append("		TBL2.SYOHIN_YOBIDASI, ");
			sb.append("		TBL2.HANEI_DT, ");
			sb.append("		TBL2.TEN_HANEI_DT, ");
			sb.append("		TBL2.UPDATE_TS ");
			sb.append("	FROM ");
			sb.append("		R_TENPO TBL1, ");
			sb.append("		( ");
			sb.append("			SELECT ");
			sb.append("				TBL2.S_GYOSYU_CD, ");
			sb.append("				TBL2.THEME_CD, ");
			sb.append("				'' KEIRYO_HANKU_CD, ");
			sb.append("				'' SYOHIN_YOBIDASI, ");
			sb.append("				'' HANEI_DT, ");
			sb.append("				DECODE(TBL2.DELETE_FG,'1',NULL,TBL2.TEN_HANEI_DT) TEN_HANEI_DT, ");
			sb.append("				TBL2.TENPO_CD, ");
			sb.append("				TBL2.UPDATE_TS ");
			sb.append("			FROM ");
			sb.append("				R_THEME_TENHANEIBI TBL2 ");
			sb.append("			WHERE ");
			sb.append("				TBL2.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "' ");
			sb.append("			AND	TBL2.THEME_CD = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "' ");
			sb.append("		) TBL2 ");
			sb.append("	WHERE ");
			sb.append("		TBL1.TENPO_CD = TBL2.TENPO_CD(+) ");
			sb.append("	AND	TBL1.TENPO_KB = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//			BUGNO-S045 2005.04.27 T.Makuta START
			sb.append("	AND	TBL1.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//			BUGNO-S045 2005.04.27 T.Makuta END
			sb.append("	ORDER BY ");
			sb.append("		TENPO_CD ");
		}



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
