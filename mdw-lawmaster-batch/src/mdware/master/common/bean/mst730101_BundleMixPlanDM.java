/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM企画特売マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM企画特売マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/15)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM企画特売マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM企画特売マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/15)初版作成
 */
public class mst730101_BundleMixPlanDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst730101_BundleMixPlanDM()
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
		mst730101_BundleMixPlanBean bean = new mst730101_BundleMixPlanBean();
		bean.setKikakuTokubainoCd( rest.getString("kikaku_tokubaino_cd") );
		bean.setNameNa( rest.getString("name_na") );
		bean.setKikakuStDt( rest.getString("kikaku_st_dt") );
		bean.setKikakuEdDt( rest.getString("kikaku_ed_dt") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );

		//A.Tozaki
		bean.setSinkiTorokuDt( rest.getString("sinki_toroku_dt") );
		bean.setHenkoDt( rest.getString("henko_dt") );

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
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("kikaku_tokubaino_cd ");
		sb.append(", ");
		sb.append("name_na ");
		sb.append(", ");
		sb.append("kikaku_st_dt ");
		sb.append(", ");
		sb.append("kikaku_ed_dt ");
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

		//A.Tozaki
		sb.append(", ");
		sb.append("sinki_toroku_dt ");
		sb.append(", ");
		sb.append("henko_dt ");

		
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//		sb.append("from R_BANDLEMIX_KIKAKUTOKUBAI ");
		sb.append("from R_BUNDLEMIX_KIKAKUTOKUBAI ");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑

		// kikaku_tokubaino_cd に対するWHERE区
		if( map.get("kikaku_tokubaino_cd_bef") != null && ((String)map.get("kikaku_tokubaino_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_tokubaino_cd >= '" + conv.convertWhereString( (String)map.get("kikaku_tokubaino_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_tokubaino_cd_aft") != null && ((String)map.get("kikaku_tokubaino_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_tokubaino_cd <= '" + conv.convertWhereString( (String)map.get("kikaku_tokubaino_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_tokubaino_cd") != null && ((String)map.get("kikaku_tokubaino_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_tokubaino_cd = '" + conv.convertWhereString( (String)map.get("kikaku_tokubaino_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_tokubaino_cd_like") != null && ((String)map.get("kikaku_tokubaino_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_tokubaino_cd like '%" + conv.convertWhereString( (String)map.get("kikaku_tokubaino_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_tokubaino_cd_bef_like") != null && ((String)map.get("kikaku_tokubaino_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_tokubaino_cd like '%" + conv.convertWhereString( (String)map.get("kikaku_tokubaino_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_tokubaino_cd_aft_like") != null && ((String)map.get("kikaku_tokubaino_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_tokubaino_cd like '" + conv.convertWhereString( (String)map.get("kikaku_tokubaino_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_tokubaino_cd_in") != null && ((String)map.get("kikaku_tokubaino_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_tokubaino_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_tokubaino_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kikaku_tokubaino_cd_not_in") != null && ((String)map.get("kikaku_tokubaino_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_tokubaino_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_tokubaino_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// name_na に対するWHERE区
		if( map.get("name_na_bef") != null && ((String)map.get("name_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na >= '" + conv.convertWhereString( (String)map.get("name_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("name_na_aft") != null && ((String)map.get("name_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na <= '" + conv.convertWhereString( (String)map.get("name_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("name_na") != null && ((String)map.get("name_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na = '" + conv.convertWhereString( (String)map.get("name_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("name_na_like") != null && ((String)map.get("name_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na like '%" + conv.convertWhereString( (String)map.get("name_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("name_na_bef_like") != null && ((String)map.get("name_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na like '%" + conv.convertWhereString( (String)map.get("name_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("name_na_aft_like") != null && ((String)map.get("name_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na like '" + conv.convertWhereString( (String)map.get("name_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("name_na_in") != null && ((String)map.get("name_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("name_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("name_na_not_in") != null && ((String)map.get("name_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("name_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kikaku_st_dt に対するWHERE区
		if( map.get("kikaku_st_dt_bef") != null && ((String)map.get("kikaku_st_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_st_dt >= '" + conv.convertWhereString( (String)map.get("kikaku_st_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_st_dt_aft") != null && ((String)map.get("kikaku_st_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_st_dt <= '" + conv.convertWhereString( (String)map.get("kikaku_st_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_st_dt") != null && ((String)map.get("kikaku_st_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_st_dt = '" + conv.convertWhereString( (String)map.get("kikaku_st_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_st_dt_like") != null && ((String)map.get("kikaku_st_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_st_dt like '%" + conv.convertWhereString( (String)map.get("kikaku_st_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_st_dt_bef_like") != null && ((String)map.get("kikaku_st_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_st_dt like '%" + conv.convertWhereString( (String)map.get("kikaku_st_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_st_dt_aft_like") != null && ((String)map.get("kikaku_st_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_st_dt like '" + conv.convertWhereString( (String)map.get("kikaku_st_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_st_dt_in") != null && ((String)map.get("kikaku_st_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_st_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_st_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kikaku_st_dt_not_in") != null && ((String)map.get("kikaku_st_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_st_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_st_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kikaku_ed_dt に対するWHERE区
		if( map.get("kikaku_ed_dt_bef") != null && ((String)map.get("kikaku_ed_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ed_dt >= '" + conv.convertWhereString( (String)map.get("kikaku_ed_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ed_dt_aft") != null && ((String)map.get("kikaku_ed_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ed_dt <= '" + conv.convertWhereString( (String)map.get("kikaku_ed_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ed_dt") != null && ((String)map.get("kikaku_ed_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ed_dt = '" + conv.convertWhereString( (String)map.get("kikaku_ed_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ed_dt_like") != null && ((String)map.get("kikaku_ed_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ed_dt like '%" + conv.convertWhereString( (String)map.get("kikaku_ed_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ed_dt_bef_like") != null && ((String)map.get("kikaku_ed_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ed_dt like '%" + conv.convertWhereString( (String)map.get("kikaku_ed_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ed_dt_aft_like") != null && ((String)map.get("kikaku_ed_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ed_dt like '" + conv.convertWhereString( (String)map.get("kikaku_ed_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ed_dt_in") != null && ((String)map.get("kikaku_ed_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ed_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_ed_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kikaku_ed_dt_not_in") != null && ((String)map.get("kikaku_ed_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ed_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_ed_dt_not_in") ),",","','") + "' )");
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

		//A.Tozaki

		// sinki_toroku_dt に対するWHERE区
		if( map.get("sinki_toroku_dt_bef") != null && ((String)map.get("sinki_toroku_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sinki_toroku_dt >= '" + conv.convertWhereString( (String)map.get("sinki_toroku_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sinki_toroku_dt_aft") != null && ((String)map.get("sinki_toroku_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sinki_toroku_dt <= '" + conv.convertWhereString( (String)map.get("sinki_toroku_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sinki_toroku_dt") != null && ((String)map.get("sinki_toroku_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sinki_toroku_dt = '" + conv.convertWhereString( (String)map.get("sinki_toroku_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sinki_toroku_dt_like") != null && ((String)map.get("sinki_toroku_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sinki_toroku_dt like '%" + conv.convertWhereString( (String)map.get("sinki_toroku_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sinki_toroku_dt_bef_like") != null && ((String)map.get("sinki_toroku_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sinki_toroku_dt like '%" + conv.convertWhereString( (String)map.get("sinki_toroku_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sinki_toroku_dt_aft_like") != null && ((String)map.get("sinki_toroku_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sinki_toroku_dt like '" + conv.convertWhereString( (String)map.get("sinki_toroku_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sinki_toroku_dt_in") != null && ((String)map.get("sinki_toroku_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sinki_toroku_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sinki_toroku_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sinki_toroku_dt_not_in") != null && ((String)map.get("sinki_toroku_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sinki_toroku_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sinki_toroku_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		

		// henko_dt に対するWHERE区
		if( map.get("henko_dt_bef") != null && ((String)map.get("henko_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("henko_dt >= '" + conv.convertWhereString( (String)map.get("henko_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("henko_dt_aft") != null && ((String)map.get("henko_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("henko_dt <= '" + conv.convertWhereString( (String)map.get("henko_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("henko_dt") != null && ((String)map.get("henko_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("henko_dt = '" + conv.convertWhereString( (String)map.get("henko_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("henko_dt_like") != null && ((String)map.get("henko_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("henko_dt like '%" + conv.convertWhereString( (String)map.get("henko_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("henko_dt_bef_like") != null && ((String)map.get("henko_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("henko_dt like '%" + conv.convertWhereString( (String)map.get("henko_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("henko_dt_aft_like") != null && ((String)map.get("henko_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("henko_dt like '" + conv.convertWhereString( (String)map.get("henko_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("henko_dt_in") != null && ((String)map.get("henko_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("henko_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("henko_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("henko_dt_not_in") != null && ((String)map.get("henko_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("henko_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("henko_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		


		sb.append(" order by ");
		sb.append("kikaku_tokubaino_cd");
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
		mst730101_BundleMixPlanBean bean = (mst730101_BundleMixPlanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//		sb.append("R_BANDLEMIX_KIKAKUTOKUBAI (");
		sb.append("R_BUNDLEMIX_KIKAKUTOKUBAI (");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
		if( bean.getKikakuTokubainoCd() != null && bean.getKikakuTokubainoCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" kikaku_tokubaino_cd");
		}
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" name_na");
		}
		if( bean.getKikakuStDt() != null && bean.getKikakuStDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kikaku_st_dt");
		}
		if( bean.getKikakuEdDt() != null && bean.getKikakuEdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kikaku_ed_dt");
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
//A.Tozaki
		if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sinki_toroku_dt");
		}
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" henko_dt");
		}


		sb.append(")Values(");


		if( bean.getKikakuTokubainoCd() != null && bean.getKikakuTokubainoCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKikakuTokubainoCd() ) + "'");
		}
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNameNa() ) + "'");
		}
		if( bean.getKikakuStDt() != null && bean.getKikakuStDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKikakuStDt() ) + "'");
		}
		if( bean.getKikakuEdDt() != null && bean.getKikakuEdDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKikakuEdDt() ) + "'");
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

		//A.Tozaki
		if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSinkiTorokuDt() ) + "'");
		}
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
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
		mst730101_BundleMixPlanBean bean = (mst730101_BundleMixPlanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//		sb.append("R_BANDLEMIX_KIKAKUTOKUBAI set ");
		sb.append("R_BUNDLEMIX_KIKAKUTOKUBAI set ");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" name_na = ");
			sb.append("'" + conv.convertString( bean.getNameNa() ) + "'");
		}
		if( bean.getKikakuStDt() != null && bean.getKikakuStDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kikaku_st_dt = ");
			sb.append("'" + conv.convertString( bean.getKikakuStDt() ) + "'");
		}
		if( bean.getKikakuEdDt() != null && bean.getKikakuEdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kikaku_ed_dt = ");
			sb.append("'" + conv.convertString( bean.getKikakuEdDt() ) + "'");
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

		//A.Tozaki
		if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sinki_toroku_dt = ");
			sb.append("'" + conv.convertString( bean.getSinkiTorokuDt() ) + "'");
		}
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" henko_dt = ");
			sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
		}
		


		sb.append(" WHERE");


		if( bean.getKikakuTokubainoCd() != null && bean.getKikakuTokubainoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" kikaku_tokubaino_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKikakuTokubainoCd() ) + "'");
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst730101_BundleMixPlanBean bean = (mst730101_BundleMixPlanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//		sb.append("R_BANDLEMIX_KIKAKUTOKUBAI where ");
		sb.append("R_BUNDLEMIX_KIKAKUTOKUBAI where ");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
		sb.append(" kikaku_tokubaino_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKikakuTokubainoCd() ) + "'");
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
