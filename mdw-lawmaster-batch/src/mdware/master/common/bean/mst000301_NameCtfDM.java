/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用名称CTFのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用名称CTFのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし
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
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用名称CTFのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用名称CTFのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @see なし
 */
public class mst000301_NameCtfDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst000301_NameCtfDM()
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
		mst000301_NameCtfBean bean = new mst000301_NameCtfBean();
		bean.setSyubetuNoCd( rest.getString("syubetu_no_cd") );
		bean.setCodeCd( rest.getString("code_cd") );
		bean.setKanjiNa( rest.getString("kanji_na") );
		bean.setKanaNa( rest.getString("kana_na") );
		bean.setKanjiRn( rest.getString("kanji_rn") );
		bean.setKanaRn( rest.getString("kana_rn") );
		bean.setZokuseiCd( rest.getString("zokusei_cd") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
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
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("syubetu_no_cd ");
		sb.append(", ");
		sb.append("code_cd ");
		sb.append(", ");
		sb.append("kanji_na ");
		sb.append(", ");
		sb.append("kana_na ");
		sb.append(", ");
		sb.append("kanji_rn ");
		sb.append(", ");
		sb.append("kana_rn ");
		sb.append(", ");
		sb.append("zokusei_cd ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append("from r_namectf ");


		// syubetu_no_cd に対するWHERE区
		if( map.get("syubetu_no_cd_bef") != null && ((String)map.get("syubetu_no_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syubetu_no_cd >= '" + conv.convertWhereString( (String)map.get("syubetu_no_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syubetu_no_cd_aft") != null && ((String)map.get("syubetu_no_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syubetu_no_cd <= '" + conv.convertWhereString( (String)map.get("syubetu_no_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syubetu_no_cd") != null && ((String)map.get("syubetu_no_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syubetu_no_cd = '" + conv.convertWhereString( MessageUtil.getMessage((String)map.get("syubetu_no_cd"), userLocal) ) + "'");
			whereStr = andStr;
		}
		if( map.get("syubetu_no_cd_like") != null && ((String)map.get("syubetu_no_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syubetu_no_cd like '%" + conv.convertWhereString( (String)map.get("syubetu_no_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syubetu_no_cd_bef_like") != null && ((String)map.get("syubetu_no_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syubetu_no_cd like '%" + conv.convertWhereString( (String)map.get("syubetu_no_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syubetu_no_cd_aft_like") != null && ((String)map.get("syubetu_no_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syubetu_no_cd like '" + conv.convertWhereString( (String)map.get("syubetu_no_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syubetu_no_cd_in") != null && ((String)map.get("syubetu_no_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syubetu_no_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syubetu_no_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syubetu_no_cd_not_in") != null && ((String)map.get("syubetu_no_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syubetu_no_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syubetu_no_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// code_cd に対するWHERE区
		if( map.get("code_cd_bef") != null && ((String)map.get("code_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("code_cd >= '" + conv.convertWhereString( (String)map.get("code_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("code_cd_aft") != null && ((String)map.get("code_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("code_cd <= '" + conv.convertWhereString( (String)map.get("code_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("code_cd") != null && ((String)map.get("code_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("code_cd = '" + conv.convertWhereString( (String)map.get("code_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("code_cd_like") != null && ((String)map.get("code_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("code_cd like '%" + conv.convertWhereString( (String)map.get("code_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("code_cd_bef_like") != null && ((String)map.get("code_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("code_cd like '%" + conv.convertWhereString( (String)map.get("code_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("code_cd_aft_like") != null && ((String)map.get("code_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("code_cd like '" + conv.convertWhereString( (String)map.get("code_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("code_cd_in") != null && ((String)map.get("code_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("code_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("code_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("code_cd_not_in") != null && ((String)map.get("code_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("code_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("code_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kanji_na に対するWHERE区
		if( map.get("kanji_na_bef") != null && ((String)map.get("kanji_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na >= '" + conv.convertWhereString( (String)map.get("kanji_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_aft") != null && ((String)map.get("kanji_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na <= '" + conv.convertWhereString( (String)map.get("kanji_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na") != null && ((String)map.get("kanji_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na = '" + conv.convertWhereString( (String)map.get("kanji_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_like") != null && ((String)map.get("kanji_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '%" + conv.convertWhereString( (String)map.get("kanji_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_bef_like") != null && ((String)map.get("kanji_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '%" + conv.convertWhereString( (String)map.get("kanji_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_aft_like") != null && ((String)map.get("kanji_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '" + conv.convertWhereString( (String)map.get("kanji_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_in") != null && ((String)map.get("kanji_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanji_na_not_in") != null && ((String)map.get("kanji_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kana_na に対するWHERE区
		if( map.get("kana_na_bef") != null && ((String)map.get("kana_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na >= '" + conv.convertWhereString( (String)map.get("kana_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_aft") != null && ((String)map.get("kana_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na <= '" + conv.convertWhereString( (String)map.get("kana_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na") != null && ((String)map.get("kana_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na = '" + conv.convertWhereString( (String)map.get("kana_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_like") != null && ((String)map.get("kana_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '%" + conv.convertWhereString( (String)map.get("kana_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_na_bef_like") != null && ((String)map.get("kana_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '%" + conv.convertWhereString( (String)map.get("kana_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_aft_like") != null && ((String)map.get("kana_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '" + conv.convertWhereString( (String)map.get("kana_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_na_in") != null && ((String)map.get("kana_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kana_na_not_in") != null && ((String)map.get("kana_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kanji_rn に対するWHERE区
		if( map.get("kanji_rn_bef") != null && ((String)map.get("kanji_rn_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn >= '" + conv.convertWhereString( (String)map.get("kanji_rn_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_aft") != null && ((String)map.get("kanji_rn_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn <= '" + conv.convertWhereString( (String)map.get("kanji_rn_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn") != null && ((String)map.get("kanji_rn")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn = '" + conv.convertWhereString( (String)map.get("kanji_rn") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_like") != null && ((String)map.get("kanji_rn_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '%" + conv.convertWhereString( (String)map.get("kanji_rn_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_bef_like") != null && ((String)map.get("kanji_rn_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '%" + conv.convertWhereString( (String)map.get("kanji_rn_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_aft_like") != null && ((String)map.get("kanji_rn_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '" + conv.convertWhereString( (String)map.get("kanji_rn_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_in") != null && ((String)map.get("kanji_rn_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_rn_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_not_in") != null && ((String)map.get("kanji_rn_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_rn_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kana_rn に対するWHERE区
		if( map.get("kana_rn_bef") != null && ((String)map.get("kana_rn_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn >= '" + conv.convertWhereString( (String)map.get("kana_rn_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_aft") != null && ((String)map.get("kana_rn_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn <= '" + conv.convertWhereString( (String)map.get("kana_rn_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn") != null && ((String)map.get("kana_rn")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn = '" + conv.convertWhereString( (String)map.get("kana_rn") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_like") != null && ((String)map.get("kana_rn_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '%" + conv.convertWhereString( (String)map.get("kana_rn_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_bef_like") != null && ((String)map.get("kana_rn_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '%" + conv.convertWhereString( (String)map.get("kana_rn_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_aft_like") != null && ((String)map.get("kana_rn_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '" + conv.convertWhereString( (String)map.get("kana_rn_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_in") != null && ((String)map.get("kana_rn_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_rn_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kana_rn_not_in") != null && ((String)map.get("kana_rn_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_rn_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// zokusei_cd に対するWHERE区
		if( map.get("zokusei_cd_bef") != null && ((String)map.get("zokusei_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zokusei_cd >= '" + conv.convertWhereString( (String)map.get("zokusei_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zokusei_cd_aft") != null && ((String)map.get("zokusei_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zokusei_cd <= '" + conv.convertWhereString( (String)map.get("zokusei_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zokusei_cd") != null && ((String)map.get("zokusei_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zokusei_cd = '" + conv.convertWhereString( (String)map.get("zokusei_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zokusei_cd_like") != null && ((String)map.get("zokusei_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zokusei_cd like '%" + conv.convertWhereString( (String)map.get("zokusei_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("zokusei_cd_bef_like") != null && ((String)map.get("zokusei_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zokusei_cd like '%" + conv.convertWhereString( (String)map.get("zokusei_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zokusei_cd_aft_like") != null && ((String)map.get("zokusei_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zokusei_cd like '" + conv.convertWhereString( (String)map.get("zokusei_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("zokusei_cd_in") != null && ((String)map.get("zokusei_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zokusei_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("zokusei_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("zokusei_cd_not_in") != null && ((String)map.get("zokusei_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zokusei_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("zokusei_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_ts に対するWHERE区
		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_user_id に対するWHERE区
		if( map.get("insert_user_id_bef") != null && ((String)map.get("insert_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id >= '" + conv.convertWhereString( (String)map.get("insert_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft") != null && ((String)map.get("insert_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id <= '" + conv.convertWhereString( (String)map.get("insert_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_like") != null && ((String)map.get("insert_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_bef_like") != null && ((String)map.get("insert_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft_like") != null && ((String)map.get("insert_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '" + conv.convertWhereString( (String)map.get("insert_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_in") != null && ((String)map.get("insert_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_not_in") != null && ((String)map.get("insert_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_ts に対するWHERE区
		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_user_id に対するWHERE区
		if( map.get("update_user_id_bef") != null && ((String)map.get("update_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// delete_fg に対するWHERE区
		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}

		if(map.get("assort") != null && map.get("assort").equals("true")){
			sb.append(whereStr);
			sb.append(" syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal) + "' ");
			sb.append(" or ");
			sb.append(" ( syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal) + "' ");
			sb.append("   and ");
			sb.append("   code_cd like '" + conv.convertWhereString( (String)map.get("hinsyu_cd") ) + "%' ");
			sb.append(" )");
		}

		sb.append(" order by ");
		sb.append("syubetu_no_cd");
		sb.append(",");
		sb.append("code_cd");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
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
		mst000301_NameCtfBean bean = (mst000301_NameCtfBean)beanMst;
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
		if( bean.getZokuseiCd() != null && bean.getZokuseiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" zokusei_cd");
		}
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
		if( bean.getZokuseiCd() != null && bean.getZokuseiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getZokuseiCd() ) + "'");
		}
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
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		boolean befKanma = false;
		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst000301_NameCtfBean bean = (mst000301_NameCtfBean)beanMst;
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
		if( bean.getZokuseiCd() != null && bean.getZokuseiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" zokusei_cd = ");
			sb.append("'" + conv.convertString( bean.getZokuseiCd() ) + "'");
		}
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
		mst000301_NameCtfBean bean = (mst000301_NameCtfBean)beanMst;
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
