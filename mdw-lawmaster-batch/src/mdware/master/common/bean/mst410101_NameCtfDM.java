/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）名称・CTFマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する名称・CTFマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/29)初版作成
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
 * <p>タイトル: 新ＭＤシステム（マスター管理）名称・CTFマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する名称・CTFマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/29)初版作成
 */
public class mst410101_NameCtfDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst410101_NameCtfDM()
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
		mst410101_NameCtfBean bean = new mst410101_NameCtfBean();
		bean.setSentaku(		rest.getString("sentaku") );
		bean.setSyubetuNoCd( 	rest.getString("syubetu_no_cd") );
		bean.setCodeCd( 		rest.getString("code_cd") );
		bean.setKanjiNa( 		rest.getString("kanji_na") );
		bean.setKanaNa( 		rest.getString("kana_na") );
		bean.setKanjiRn( 		rest.getString("kanji_rn") );
		bean.setKanaRn( 		rest.getString("kana_rn") );
//		↓↓2006.08.15 wangluping カスタマイズ修正↓↓
//		bean.setZokuseiCd( 		rest.getString("zokusei_cd") );
//		↑↑2006.08.15 wangluping カスタマイズ修正↑↑
		bean.setInsertTs( 		rest.getString("insert_ts") );
		bean.setInsertTsShort( 	rest.getString("insert_ts") );
		bean.setInsertUserId( 	rest.getString("insert_user_id") );
		bean.setInsertUserNm( 	rest.getString("insert_user_nm") );
		bean.setUpdateTs( 		rest.getString("update_ts") );
		bean.setUpdateTsShort( 	rest.getString("update_ts") );
		bean.setUpdateUserId(	rest.getString("update_user_id") );
		bean.setUpdateUserNm(	rest.getString("update_user_nm") );
		bean.setDeleteFg(		rest.getString("delete_fg") );
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
		String wk = "";
		StringBuffer sb = new StringBuffer();
//	    ↓↓2006.06.22 shiyy カスタマイズ修正↓↓
//		sb.append("select ");
//		sb.append("'0' sentaku ");
//		sb.append(", ");
//		sb.append("syubetu_no_cd ");
//		sb.append(", ");
//		sb.append("code_cd ");
//		sb.append(", ");
//		sb.append("kanji_na ");
//		sb.append(", ");
//		sb.append("kana_na ");
//		sb.append(", ");
//		sb.append("kanji_rn ");
//		sb.append(", ");
//		sb.append("kana_rn ");
//		sb.append(", ");
//		sb.append("zokusei_cd ");
//		sb.append(", ");
//		sb.append("insert_ts ");
//		sb.append(", ");
//		sb.append("insert_user_id ");
//		sb.append(", ");
//		sb.append("(select user_na from sys_sosasya where hojin_cd = '"
//						+ mst000101_ConstDictionary.HOJIN_CD
//						+ "' and user_id = r_namectf.insert_user_id) insert_user_nm ");
//		sb.append(", ");
//		sb.append("update_ts ");
//		sb.append(", ");
//		sb.append("update_user_id ");
//		sb.append(", ");
//		sb.append("(select user_na from sys_sosasya where hojin_cd = '"
//						+ mst000101_ConstDictionary.HOJIN_CD
//						+ "' and user_id = r_namectf.update_user_id) update_user_nm ");
//		sb.append(", ");
//		sb.append("delete_fg ");
//		sb.append("from r_namectf ");
		sb.append("select ");
		sb.append("'0' sentaku ");
		sb.append(", ");
		sb.append("rn.syubetu_no_cd syubetu_no_cd");
		sb.append(", ");
		sb.append("rn.code_cd code_cd");
		sb.append(", ");
		sb.append("rn.kanji_na kanji_na ");
		sb.append(", ");
		sb.append("rn.kana_na kana_na");
		sb.append(", ");
		sb.append("rn.kanji_rn kanji_rn");
		sb.append(", ");
		sb.append("rn.kana_rn kana_rn");
		sb.append(", ");
		sb.append("rn.zokusei_cd zokusei_cd");
		sb.append(", ");
		sb.append("rn.insert_ts insert_ts");
		sb.append(", ");
		sb.append("rn.insert_user_id insert_user_id");
		sb.append(", ");
		sb.append("user1.riyo_user_na insert_user_nm");
		sb.append(", ");
		sb.append("rn.update_ts update_ts");
		sb.append(", ");
		sb.append("rn.update_user_id update_user_id");
		sb.append(", ");
		sb.append("user2.riyo_user_na update_user_nm");
		sb.append(", ");
		sb.append("rn.delete_fg delete_fg");
		sb.append(" from r_namectf rn");
//		sb.append(" left outer join r_riyo_user  user1 on user1.riyo_user_id =  rn.insert_user_id ");
//		sb.append(" left outer join r_riyo_user  user2 on user2.riyo_user_id =  rn.update_user_id ");
////	    ↑↑2006.06.22 shiyy カスタマイズ修正↑↑
		sb.append(" left outer join r_portal_user  user1 on user1.user_id =  TRIM(rn.insert_user_id) ");
		sb.append(" left outer join r_portal_user  user2 on user2.user_id =  TRIM(rn.update_user_id) ");
		wk = getWhereSqlStr(map, "syubetu_no_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "code_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kanji_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kana_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kanji_rn", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kana_rn", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "zokusei_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "delete_fg", whereStr);
		sb.append(wk);
		whereStr = andStr;
//	    ↓↓2006.06.22 shiyy カスタマイズ修正↓↓
//		sb.append(" order by ");
//		sb.append("syubetu_no_cd");
//		sb.append(",");
//		sb.append("code_cd");
		sb.append(" order by ");
		sb.append("rn.syubetu_no_cd");
		sb.append(",");
		sb.append("rn.code_cd");
//	    ↑↑2006.06.22 shiyy カスタマイズ修正↑↑
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
			if ("syubetu_no_cd".equals(Key)) {
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				sb.append(whereStr);
				sb.append(Key + " = '" + conv.convertWhereString( MessageUtil.getMessage((String)map.get(Key), userLocal) ) + "'");
				whereStr = andStr;
			} else{
				sb.append(whereStr);
				sb.append(Key + " = '" + conv.convertWhereString( (String)map.get(Key) ) + "'");
				whereStr = andStr;
			}
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

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ日付・時刻型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlDate(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
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
			sb.append(Key + " = '" + (String)map.get(Key) + "'");
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

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ数値型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlNum(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append(Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプOTHER）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlOther(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append(Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
			whereStr = andStr;
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
		boolean befKanma = false;
		boolean aftKanma = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst410101_NameCtfBean bean = (mst410101_NameCtfBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_namectf (");
		if( bean.getSyubetuNoCd() != null && bean.getSyubetuNoCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" syubetu_no_cd");
		}
		if( bean.getCodeCd() != null && bean.getCodeCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" code_cd");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_na");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_na");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_rn");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_rn");
		}
//		↓↓2006.08.15 wangluping カスタマイズ修正↓↓
//		if( bean.getZokuseiCd() != null && bean.getZokuseiCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" zokusei_cd");
//		}
//		↑↑2006.08.15 wangluping カスタマイズ修正↑↑
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


		if( bean.getSyubetuNoCd() != null && bean.getSyubetuNoCd().trim().length() != 0 )
		{
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			aftKanma = true;
			sb.append("'" + conv.convertString( MessageUtil.getMessage(bean.getSyubetuNoCd(), userLocal) ) + "'");
		}
		if( bean.getCodeCd() != null && bean.getCodeCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCodeCd() ) + "'");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
		}
//		↓↓2006.08.15 wangluping カスタマイズ修正↓↓
//		if( bean.getZokuseiCd() != null && bean.getZokuseiCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getZokuseiCd() ) + "'");
//		}
//		↑↑2006.08.15 wangluping カスタマイズ修正↑↑
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
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
		boolean befKanma = false;
		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst410101_NameCtfBean bean = (mst410101_NameCtfBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_namectf set ");
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" kanji_na = ");
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_na = ");
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_rn = ");
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_rn = ");
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
		}
//		↓↓2006.08.15 wangluping カスタマイズ修正↓↓
//		if( bean.getZokuseiCd() != null && bean.getZokuseiCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" zokusei_cd = ");
//			sb.append("'" + conv.convertString( bean.getZokuseiCd() ) + "'");
//		}
//BUGNO-S073 2005.07.19 H.Futagami START
//		else {
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" zokusei_cd = NULL");
//		}
//		↑↑2006.08.15 wangluping カスタマイズ修正↑↑
//BUGNO-S073 2005.07.19 H.Futagami END
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getSyubetuNoCd() != null && bean.getSyubetuNoCd().trim().length() != 0 )
		{
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			whereAnd = true;
			sb.append(" syubetu_no_cd = ");
			sb.append("'" + conv.convertWhereString( MessageUtil.getMessage(bean.getSyubetuNoCd(), userLocal) ) + "'");
		}
		if( bean.getCodeCd() != null && bean.getCodeCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" code_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getCodeCd() ) + "'");
		}
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
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst410101_NameCtfBean bean = (mst410101_NameCtfBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_namectf where ");
		sb.append(" syubetu_no_cd = ");
		sb.append("'" + conv.convertWhereString( MessageUtil.getMessage(bean.getSyubetuNoCd(), userLocal) ) + "'");
		sb.append(" AND");
		sb.append(" code_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getCodeCd() ) + "'");
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
