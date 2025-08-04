/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/19)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/19)初版作成
 */
public class mst620101_SyohinZokuseiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst620101_SyohinZokuseiDM()
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
		mst620101_SyohinZokuseiBean bean = new mst620101_SyohinZokuseiBean();
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setGyosyuKb( rest.getString("gyosyu_kb") );
		bean.setHinmeiKanjiNa( rest.getString("hinmei_kanji_na") );
		bean.setDZokusei1Na( rest.getString("d_zokusei_1_na") );
		bean.setSZokusei1Na( rest.getString("s_zokusei_1_na") );
		bean.setDZokusei2Na( rest.getString("d_zokusei_2_na") );
		bean.setSZokusei2Na( rest.getString("s_zokusei_2_na") );
		bean.setDZokusei3Na( rest.getString("d_zokusei_3_na") );
		bean.setSZokusei3Na( rest.getString("s_zokusei_3_na") );
		bean.setDZokusei4Na( rest.getString("d_zokusei_4_na") );
		bean.setSZokusei4Na( rest.getString("s_zokusei_4_na") );
		bean.setDZokusei5Na( rest.getString("d_zokusei_5_na") );
		bean.setSZokusei5Na( rest.getString("s_zokusei_5_na") );
		bean.setDZokusei6Na( rest.getString("d_zokusei_6_na") );
		bean.setSZokusei6Na( rest.getString("s_zokusei_6_na") );
		bean.setDZokusei7Na( rest.getString("d_zokusei_7_na") );
		bean.setSZokusei7Na( rest.getString("s_zokusei_7_na") );
		bean.setDZokusei8Na( rest.getString("d_zokusei_8_na") );
		bean.setSZokusei8Na( rest.getString("s_zokusei_8_na") );
		bean.setDZokusei9Na( rest.getString("d_zokusei_9_na") );
		bean.setSZokusei9Na( rest.getString("s_zokusei_9_na") );
		bean.setDZokusei10Na( rest.getString("d_zokusei_10_na") );
		bean.setSZokusei10Na( rest.getString("s_zokusei_10_na") );
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("hanku_cd ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("yuko_dt ");
		sb.append(", ");
		sb.append("gyosyu_kb ");
		sb.append(", ");
		sb.append("hinmei_kanji_na ");
		sb.append(", ");
		sb.append("d_zokusei_1_na ");
		sb.append(", ");
		sb.append("s_zokusei_1_na ");
		sb.append(", ");
		sb.append("d_zokusei_2_na ");
		sb.append(", ");
		sb.append("s_zokusei_2_na ");
		sb.append(", ");
		sb.append("d_zokusei_3_na ");
		sb.append(", ");
		sb.append("s_zokusei_3_na ");
		sb.append(", ");
		sb.append("d_zokusei_4_na ");
		sb.append(", ");
		sb.append("s_zokusei_4_na ");
		sb.append(", ");
		sb.append("d_zokusei_5_na ");
		sb.append(", ");
		sb.append("s_zokusei_5_na ");
		sb.append(", ");
		sb.append("d_zokusei_6_na ");
		sb.append(", ");
		sb.append("s_zokusei_6_na ");
		sb.append(", ");
		sb.append("d_zokusei_7_na ");
		sb.append(", ");
		sb.append("s_zokusei_7_na ");
		sb.append(", ");
		sb.append("d_zokusei_8_na ");
		sb.append(", ");
		sb.append("s_zokusei_8_na ");
		sb.append(", ");
		sb.append("d_zokusei_9_na ");
		sb.append(", ");
		sb.append("s_zokusei_9_na ");
		sb.append(", ");
		sb.append("d_zokusei_10_na ");
		sb.append(", ");
		sb.append("s_zokusei_10_na ");
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
		sb.append("from r_syohin ");


		// hanku_cd に対するWHERE区
		if( map.get("hanku_cd_bef") != null && ((String)map.get("hanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd >= '" + conv.convertWhereString( (String)map.get("hanku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_aft") != null && ((String)map.get("hanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd <= '" + conv.convertWhereString( (String)map.get("hanku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_like") != null && ((String)map.get("hanku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd like '%" + conv.convertWhereString( (String)map.get("hanku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_bef_like") != null && ((String)map.get("hanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd like '%" + conv.convertWhereString( (String)map.get("hanku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_aft_like") != null && ((String)map.get("hanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd like '" + conv.convertWhereString( (String)map.get("hanku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_in") != null && ((String)map.get("hanku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanku_cd_not_in") != null && ((String)map.get("hanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_cd に対するWHERE区
		if( map.get("syohin_cd_bef") != null && ((String)map.get("syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd >= '" + conv.convertWhereString( (String)map.get("syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft") != null && ((String)map.get("syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd <= '" + conv.convertWhereString( (String)map.get("syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_like") != null && ((String)map.get("syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_bef_like") != null && ((String)map.get("syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft_like") != null && ((String)map.get("syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '" + conv.convertWhereString( (String)map.get("syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_in") != null && ((String)map.get("syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_not_in") != null && ((String)map.get("syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// yuko_dt に対するWHERE区
		if( map.get("yuko_dt_bef") != null && ((String)map.get("yuko_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt >= '" + conv.convertWhereString( (String)map.get("yuko_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_aft") != null && ((String)map.get("yuko_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt <= '" + conv.convertWhereString( (String)map.get("yuko_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_like") != null && ((String)map.get("yuko_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '%" + conv.convertWhereString( (String)map.get("yuko_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_bef_like") != null && ((String)map.get("yuko_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '%" + conv.convertWhereString( (String)map.get("yuko_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_aft_like") != null && ((String)map.get("yuko_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '" + conv.convertWhereString( (String)map.get("yuko_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_in") != null && ((String)map.get("yuko_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_not_in") != null && ((String)map.get("yuko_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hinmei_kanji_na に対するWHERE区
		if( map.get("hinmei_kanji_na_bef") != null && ((String)map.get("hinmei_kanji_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmei_kanji_na >= '" + conv.convertWhereString( (String)map.get("hinmei_kanji_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmei_kanji_na_aft") != null && ((String)map.get("hinmei_kanji_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmei_kanji_na <= '" + conv.convertWhereString( (String)map.get("hinmei_kanji_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmei_kanji_na") != null && ((String)map.get("hinmei_kanji_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmei_kanji_na = '" + conv.convertWhereString( (String)map.get("hinmei_kanji_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmei_kanji_na_like") != null && ((String)map.get("hinmei_kanji_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmei_kanji_na like '%" + conv.convertWhereString( (String)map.get("hinmei_kanji_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinmei_kanji_na_bef_like") != null && ((String)map.get("hinmei_kanji_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmei_kanji_na like '%" + conv.convertWhereString( (String)map.get("hinmei_kanji_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmei_kanji_na_aft_like") != null && ((String)map.get("hinmei_kanji_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmei_kanji_na like '" + conv.convertWhereString( (String)map.get("hinmei_kanji_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinmei_kanji_na_in") != null && ((String)map.get("hinmei_kanji_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmei_kanji_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinmei_kanji_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hinmei_kanji_na_not_in") != null && ((String)map.get("hinmei_kanji_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmei_kanji_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinmei_kanji_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_1_na に対するWHERE区
		if( map.get("s_zokusei_1_na_bef") != null && ((String)map.get("s_zokusei_1_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_1_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_1_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_1_na_aft") != null && ((String)map.get("s_zokusei_1_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_1_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_1_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_1_na") != null && ((String)map.get("s_zokusei_1_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_1_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_1_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_1_na_like") != null && ((String)map.get("s_zokusei_1_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_1_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_1_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_1_na_bef_like") != null && ((String)map.get("s_zokusei_1_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_1_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_1_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_1_na_aft_like") != null && ((String)map.get("s_zokusei_1_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_1_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_1_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_1_na_in") != null && ((String)map.get("s_zokusei_1_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_1_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_1_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_1_na_not_in") != null && ((String)map.get("s_zokusei_1_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_1_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_1_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_2_na に対するWHERE区
		if( map.get("d_zokusei_2_na_bef") != null && ((String)map.get("d_zokusei_2_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_2_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_2_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_2_na_aft") != null && ((String)map.get("d_zokusei_2_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_2_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_2_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_2_na") != null && ((String)map.get("d_zokusei_2_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_2_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_2_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_2_na_like") != null && ((String)map.get("d_zokusei_2_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_2_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_2_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_2_na_bef_like") != null && ((String)map.get("d_zokusei_2_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_2_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_2_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_2_na_aft_like") != null && ((String)map.get("d_zokusei_2_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_2_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_2_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_2_na_in") != null && ((String)map.get("d_zokusei_2_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_2_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_2_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_2_na_not_in") != null && ((String)map.get("d_zokusei_2_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_2_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_2_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_2_na に対するWHERE区
		if( map.get("s_zokusei_2_na_bef") != null && ((String)map.get("s_zokusei_2_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_2_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_2_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_2_na_aft") != null && ((String)map.get("s_zokusei_2_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_2_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_2_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_2_na") != null && ((String)map.get("s_zokusei_2_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_2_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_2_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_2_na_like") != null && ((String)map.get("s_zokusei_2_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_2_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_2_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_2_na_bef_like") != null && ((String)map.get("s_zokusei_2_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_2_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_2_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_2_na_aft_like") != null && ((String)map.get("s_zokusei_2_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_2_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_2_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_2_na_in") != null && ((String)map.get("s_zokusei_2_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_2_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_2_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_2_na_not_in") != null && ((String)map.get("s_zokusei_2_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_2_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_2_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_3_na に対するWHERE区
		if( map.get("d_zokusei_3_na_bef") != null && ((String)map.get("d_zokusei_3_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_3_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_3_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_3_na_aft") != null && ((String)map.get("d_zokusei_3_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_3_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_3_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_3_na") != null && ((String)map.get("d_zokusei_3_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_3_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_3_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_3_na_like") != null && ((String)map.get("d_zokusei_3_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_3_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_3_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_3_na_bef_like") != null && ((String)map.get("d_zokusei_3_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_3_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_3_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_3_na_aft_like") != null && ((String)map.get("d_zokusei_3_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_3_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_3_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_3_na_in") != null && ((String)map.get("d_zokusei_3_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_3_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_3_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_3_na_not_in") != null && ((String)map.get("d_zokusei_3_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_3_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_3_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_3_na に対するWHERE区
		if( map.get("s_zokusei_3_na_bef") != null && ((String)map.get("s_zokusei_3_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_3_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_3_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_3_na_aft") != null && ((String)map.get("s_zokusei_3_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_3_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_3_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_3_na") != null && ((String)map.get("s_zokusei_3_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_3_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_3_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_3_na_like") != null && ((String)map.get("s_zokusei_3_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_3_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_3_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_3_na_bef_like") != null && ((String)map.get("s_zokusei_3_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_3_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_3_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_3_na_aft_like") != null && ((String)map.get("s_zokusei_3_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_3_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_3_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_3_na_in") != null && ((String)map.get("s_zokusei_3_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_3_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_3_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_3_na_not_in") != null && ((String)map.get("s_zokusei_3_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_3_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_3_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_4_na に対するWHERE区
		if( map.get("d_zokusei_4_na_bef") != null && ((String)map.get("d_zokusei_4_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_4_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_4_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_4_na_aft") != null && ((String)map.get("d_zokusei_4_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_4_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_4_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_4_na") != null && ((String)map.get("d_zokusei_4_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_4_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_4_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_4_na_like") != null && ((String)map.get("d_zokusei_4_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_4_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_4_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_4_na_bef_like") != null && ((String)map.get("d_zokusei_4_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_4_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_4_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_4_na_aft_like") != null && ((String)map.get("d_zokusei_4_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_4_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_4_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_4_na_in") != null && ((String)map.get("d_zokusei_4_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_4_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_4_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_4_na_not_in") != null && ((String)map.get("d_zokusei_4_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_4_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_4_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_4_na に対するWHERE区
		if( map.get("s_zokusei_4_na_bef") != null && ((String)map.get("s_zokusei_4_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_4_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_4_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_4_na_aft") != null && ((String)map.get("s_zokusei_4_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_4_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_4_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_4_na") != null && ((String)map.get("s_zokusei_4_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_4_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_4_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_4_na_like") != null && ((String)map.get("s_zokusei_4_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_4_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_4_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_4_na_bef_like") != null && ((String)map.get("s_zokusei_4_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_4_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_4_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_4_na_aft_like") != null && ((String)map.get("s_zokusei_4_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_4_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_4_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_4_na_in") != null && ((String)map.get("s_zokusei_4_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_4_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_4_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_4_na_not_in") != null && ((String)map.get("s_zokusei_4_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_4_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_4_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_5_na に対するWHERE区
		if( map.get("d_zokusei_5_na_bef") != null && ((String)map.get("d_zokusei_5_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_5_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_5_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_5_na_aft") != null && ((String)map.get("d_zokusei_5_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_5_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_5_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_5_na") != null && ((String)map.get("d_zokusei_5_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_5_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_5_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_5_na_like") != null && ((String)map.get("d_zokusei_5_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_5_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_5_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_5_na_bef_like") != null && ((String)map.get("d_zokusei_5_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_5_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_5_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_5_na_aft_like") != null && ((String)map.get("d_zokusei_5_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_5_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_5_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_5_na_in") != null && ((String)map.get("d_zokusei_5_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_5_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_5_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_5_na_not_in") != null && ((String)map.get("d_zokusei_5_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_5_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_5_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_5_na に対するWHERE区
		if( map.get("s_zokusei_5_na_bef") != null && ((String)map.get("s_zokusei_5_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_5_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_5_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_5_na_aft") != null && ((String)map.get("s_zokusei_5_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_5_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_5_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_5_na") != null && ((String)map.get("s_zokusei_5_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_5_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_5_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_5_na_like") != null && ((String)map.get("s_zokusei_5_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_5_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_5_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_5_na_bef_like") != null && ((String)map.get("s_zokusei_5_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_5_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_5_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_5_na_aft_like") != null && ((String)map.get("s_zokusei_5_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_5_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_5_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_5_na_in") != null && ((String)map.get("s_zokusei_5_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_5_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_5_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_5_na_not_in") != null && ((String)map.get("s_zokusei_5_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_5_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_5_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_6_na に対するWHERE区
		if( map.get("d_zokusei_6_na_bef") != null && ((String)map.get("d_zokusei_6_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_6_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_6_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_6_na_aft") != null && ((String)map.get("d_zokusei_6_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_6_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_6_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_6_na") != null && ((String)map.get("d_zokusei_6_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_6_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_6_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_6_na_like") != null && ((String)map.get("d_zokusei_6_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_6_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_6_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_6_na_bef_like") != null && ((String)map.get("d_zokusei_6_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_6_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_6_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_6_na_aft_like") != null && ((String)map.get("d_zokusei_6_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_6_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_6_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_6_na_in") != null && ((String)map.get("d_zokusei_6_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_6_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_6_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_6_na_not_in") != null && ((String)map.get("d_zokusei_6_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_6_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_6_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_6_na に対するWHERE区
		if( map.get("s_zokusei_6_na_bef") != null && ((String)map.get("s_zokusei_6_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_6_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_6_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_6_na_aft") != null && ((String)map.get("s_zokusei_6_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_6_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_6_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_6_na") != null && ((String)map.get("s_zokusei_6_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_6_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_6_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_6_na_like") != null && ((String)map.get("s_zokusei_6_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_6_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_6_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_6_na_bef_like") != null && ((String)map.get("s_zokusei_6_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_6_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_6_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_6_na_aft_like") != null && ((String)map.get("s_zokusei_6_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_6_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_6_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_6_na_in") != null && ((String)map.get("s_zokusei_6_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_6_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_6_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_6_na_not_in") != null && ((String)map.get("s_zokusei_6_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_6_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_6_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_7_na に対するWHERE区
		if( map.get("d_zokusei_7_na_bef") != null && ((String)map.get("d_zokusei_7_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_7_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_7_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_7_na_aft") != null && ((String)map.get("d_zokusei_7_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_7_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_7_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_7_na") != null && ((String)map.get("d_zokusei_7_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_7_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_7_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_7_na_like") != null && ((String)map.get("d_zokusei_7_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_7_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_7_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_7_na_bef_like") != null && ((String)map.get("d_zokusei_7_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_7_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_7_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_7_na_aft_like") != null && ((String)map.get("d_zokusei_7_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_7_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_7_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_7_na_in") != null && ((String)map.get("d_zokusei_7_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_7_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_7_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_7_na_not_in") != null && ((String)map.get("d_zokusei_7_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_7_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_7_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_7_na に対するWHERE区
		if( map.get("s_zokusei_7_na_bef") != null && ((String)map.get("s_zokusei_7_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_7_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_7_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_7_na_aft") != null && ((String)map.get("s_zokusei_7_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_7_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_7_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_7_na") != null && ((String)map.get("s_zokusei_7_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_7_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_7_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_7_na_like") != null && ((String)map.get("s_zokusei_7_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_7_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_7_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_7_na_bef_like") != null && ((String)map.get("s_zokusei_7_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_7_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_7_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_7_na_aft_like") != null && ((String)map.get("s_zokusei_7_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_7_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_7_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_7_na_in") != null && ((String)map.get("s_zokusei_7_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_7_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_7_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_7_na_not_in") != null && ((String)map.get("s_zokusei_7_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_7_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_7_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_8_na に対するWHERE区
		if( map.get("d_zokusei_8_na_bef") != null && ((String)map.get("d_zokusei_8_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_8_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_8_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_8_na_aft") != null && ((String)map.get("d_zokusei_8_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_8_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_8_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_8_na") != null && ((String)map.get("d_zokusei_8_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_8_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_8_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_8_na_like") != null && ((String)map.get("d_zokusei_8_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_8_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_8_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_8_na_bef_like") != null && ((String)map.get("d_zokusei_8_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_8_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_8_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_8_na_aft_like") != null && ((String)map.get("d_zokusei_8_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_8_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_8_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_8_na_in") != null && ((String)map.get("d_zokusei_8_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_8_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_8_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_8_na_not_in") != null && ((String)map.get("d_zokusei_8_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_8_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_8_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_8_na に対するWHERE区
		if( map.get("s_zokusei_8_na_bef") != null && ((String)map.get("s_zokusei_8_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_8_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_8_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_8_na_aft") != null && ((String)map.get("s_zokusei_8_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_8_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_8_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_8_na") != null && ((String)map.get("s_zokusei_8_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_8_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_8_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_8_na_like") != null && ((String)map.get("s_zokusei_8_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_8_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_8_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_8_na_bef_like") != null && ((String)map.get("s_zokusei_8_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_8_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_8_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_8_na_aft_like") != null && ((String)map.get("s_zokusei_8_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_8_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_8_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_8_na_in") != null && ((String)map.get("s_zokusei_8_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_8_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_8_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_8_na_not_in") != null && ((String)map.get("s_zokusei_8_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_8_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_8_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_9_na に対するWHERE区
		if( map.get("d_zokusei_9_na_bef") != null && ((String)map.get("d_zokusei_9_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_9_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_9_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_9_na_aft") != null && ((String)map.get("d_zokusei_9_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_9_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_9_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_9_na") != null && ((String)map.get("d_zokusei_9_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_9_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_9_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_9_na_like") != null && ((String)map.get("d_zokusei_9_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_9_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_9_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_9_na_bef_like") != null && ((String)map.get("d_zokusei_9_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_9_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_9_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_9_na_aft_like") != null && ((String)map.get("d_zokusei_9_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_9_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_9_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_9_na_in") != null && ((String)map.get("d_zokusei_9_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_9_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_9_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_9_na_not_in") != null && ((String)map.get("d_zokusei_9_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_9_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_9_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_9_na に対するWHERE区
		if( map.get("s_zokusei_9_na_bef") != null && ((String)map.get("s_zokusei_9_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_9_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_9_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_9_na_aft") != null && ((String)map.get("s_zokusei_9_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_9_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_9_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_9_na") != null && ((String)map.get("s_zokusei_9_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_9_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_9_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_9_na_like") != null && ((String)map.get("s_zokusei_9_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_9_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_9_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_9_na_bef_like") != null && ((String)map.get("s_zokusei_9_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_9_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_9_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_9_na_aft_like") != null && ((String)map.get("s_zokusei_9_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_9_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_9_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_9_na_in") != null && ((String)map.get("s_zokusei_9_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_9_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_9_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_9_na_not_in") != null && ((String)map.get("s_zokusei_9_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_9_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_9_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// d_zokusei_10_na に対するWHERE区
		if( map.get("d_zokusei_10_na_bef") != null && ((String)map.get("d_zokusei_10_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_10_na >= '" + conv.convertWhereString( (String)map.get("d_zokusei_10_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_10_na_aft") != null && ((String)map.get("d_zokusei_10_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_10_na <= '" + conv.convertWhereString( (String)map.get("d_zokusei_10_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_10_na") != null && ((String)map.get("d_zokusei_10_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_10_na = '" + conv.convertWhereString( (String)map.get("d_zokusei_10_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_10_na_like") != null && ((String)map.get("d_zokusei_10_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_10_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_10_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_10_na_bef_like") != null && ((String)map.get("d_zokusei_10_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_10_na like '%" + conv.convertWhereString( (String)map.get("d_zokusei_10_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_10_na_aft_like") != null && ((String)map.get("d_zokusei_10_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_10_na like '" + conv.convertWhereString( (String)map.get("d_zokusei_10_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_10_na_in") != null && ((String)map.get("d_zokusei_10_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_10_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_10_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("d_zokusei_10_na_not_in") != null && ((String)map.get("d_zokusei_10_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("d_zokusei_10_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("d_zokusei_10_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_zokusei_10_na に対するWHERE区
		if( map.get("s_zokusei_10_na_bef") != null && ((String)map.get("s_zokusei_10_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_10_na >= '" + conv.convertWhereString( (String)map.get("s_zokusei_10_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_10_na_aft") != null && ((String)map.get("s_zokusei_10_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_10_na <= '" + conv.convertWhereString( (String)map.get("s_zokusei_10_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_10_na") != null && ((String)map.get("s_zokusei_10_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_10_na = '" + conv.convertWhereString( (String)map.get("s_zokusei_10_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_10_na_like") != null && ((String)map.get("s_zokusei_10_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_10_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_10_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_10_na_bef_like") != null && ((String)map.get("s_zokusei_10_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_10_na like '%" + conv.convertWhereString( (String)map.get("s_zokusei_10_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_10_na_aft_like") != null && ((String)map.get("s_zokusei_10_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_10_na like '" + conv.convertWhereString( (String)map.get("s_zokusei_10_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_10_na_in") != null && ((String)map.get("s_zokusei_10_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_10_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_10_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_zokusei_10_na_not_in") != null && ((String)map.get("s_zokusei_10_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_zokusei_10_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_zokusei_10_na_not_in") ),",","','") + "' )");
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
		sb.append(" order by ");
		sb.append("hanku_cd");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("yuko_dt");
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
		mst620101_SyohinZokuseiBean bean = (mst620101_SyohinZokuseiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_syohin (");
		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" hanku_cd");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_dt");
		}
		if( bean.getGyosyuKb() != null && bean.getGyosyuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gyosyu_kb");
		}
		if( bean.getHinmeiKanjiNa() != null && bean.getHinmeiKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinmei_kanji_na");
		}
		if( bean.getDZokusei1Na() != null && bean.getDZokusei1Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_1_na");
		}
		if( bean.getSZokusei1Na() != null && bean.getSZokusei1Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_1_na");
		}
		if( bean.getDZokusei2Na() != null && bean.getDZokusei2Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_2_na");
		}
		if( bean.getSZokusei2Na() != null && bean.getSZokusei2Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_2_na");
		}
		if( bean.getDZokusei3Na() != null && bean.getDZokusei3Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_3_na");
		}
		if( bean.getSZokusei3Na() != null && bean.getSZokusei3Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_3_na");
		}
		if( bean.getDZokusei4Na() != null && bean.getDZokusei4Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_4_na");
		}
		if( bean.getSZokusei4Na() != null && bean.getSZokusei4Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_4_na");
		}
		if( bean.getDZokusei5Na() != null && bean.getDZokusei5Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_5_na");
		}
		if( bean.getSZokusei5Na() != null && bean.getSZokusei5Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_5_na");
		}
		if( bean.getDZokusei6Na() != null && bean.getDZokusei6Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_6_na");
		}
		if( bean.getSZokusei6Na() != null && bean.getSZokusei6Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_6_na");
		}
		if( bean.getDZokusei7Na() != null && bean.getDZokusei7Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_7_na");
		}
		if( bean.getSZokusei7Na() != null && bean.getSZokusei7Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_7_na");
		}
		if( bean.getDZokusei8Na() != null && bean.getDZokusei8Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_8_na");
		}
		if( bean.getSZokusei8Na() != null && bean.getSZokusei8Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_8_na");
		}
		if( bean.getDZokusei9Na() != null && bean.getDZokusei9Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_9_na");
		}
		if( bean.getSZokusei9Na() != null && bean.getSZokusei9Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_9_na");
		}
		if( bean.getDZokusei10Na() != null && bean.getDZokusei10Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" d_zokusei_10_na");
		}
		if( bean.getSZokusei10Na() != null && bean.getSZokusei10Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" s_zokusei_10_na");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHankuCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		if( bean.getGyosyuKb() != null && bean.getGyosyuKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getGyosyuKb() ) + "'");
		}
		if( bean.getHinmeiKanjiNa() != null && bean.getHinmeiKanjiNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHinmeiKanjiNa() ) + "'");
		}
		if( bean.getDZokusei1Na() != null && bean.getDZokusei1Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei1Na() ) + "'");
		}
		if( bean.getSZokusei1Na() != null && bean.getSZokusei1Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei1Na() ) + "'");
		}
		if( bean.getDZokusei2Na() != null && bean.getDZokusei2Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei2Na() ) + "'");
		}
		if( bean.getSZokusei2Na() != null && bean.getSZokusei2Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei2Na() ) + "'");
		}
		if( bean.getDZokusei3Na() != null && bean.getDZokusei3Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei3Na() ) + "'");
		}
		if( bean.getSZokusei3Na() != null && bean.getSZokusei3Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei3Na() ) + "'");
		}
		if( bean.getDZokusei4Na() != null && bean.getDZokusei4Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei4Na() ) + "'");
		}
		if( bean.getSZokusei4Na() != null && bean.getSZokusei4Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei4Na() ) + "'");
		}
		if( bean.getDZokusei5Na() != null && bean.getDZokusei5Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei5Na() ) + "'");
		}
		if( bean.getSZokusei5Na() != null && bean.getSZokusei5Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei5Na() ) + "'");
		}
		if( bean.getDZokusei6Na() != null && bean.getDZokusei6Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei6Na() ) + "'");
		}
		if( bean.getSZokusei6Na() != null && bean.getSZokusei6Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei6Na() ) + "'");
		}
		if( bean.getDZokusei7Na() != null && bean.getDZokusei7Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei7Na() ) + "'");
		}
		if( bean.getSZokusei7Na() != null && bean.getSZokusei7Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei7Na() ) + "'");
		}
		if( bean.getDZokusei8Na() != null && bean.getDZokusei8Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei8Na() ) + "'");
		}
		if( bean.getSZokusei8Na() != null && bean.getSZokusei8Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei8Na() ) + "'");
		}
		if( bean.getDZokusei9Na() != null && bean.getDZokusei9Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei9Na() ) + "'");
		}
		if( bean.getSZokusei9Na() != null && bean.getSZokusei9Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei9Na() ) + "'");
		}
		if( bean.getDZokusei10Na() != null && bean.getDZokusei10Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDZokusei10Na() ) + "'");
		}
		if( bean.getSZokusei10Na() != null && bean.getSZokusei10Na().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSZokusei10Na() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
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
		mst620101_SyohinZokuseiBean bean = (mst620101_SyohinZokuseiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_syohin set ");
		if( bean.getGyosyuKb() != null && bean.getGyosyuKb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" gyosyu_kb = ");
			sb.append("'" + conv.convertString( bean.getGyosyuKb() ) + "'");
		}
		if( bean.getHinmeiKanjiNa() != null && bean.getHinmeiKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinmei_kanji_na = ");
			sb.append("'" + conv.convertString( bean.getHinmeiKanjiNa() ) + "'");
		}
//BUGNO-S017 2005.04.22 S.Takahashi START
//		if( bean.getDZokusei1Na() != null && bean.getDZokusei1Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_1_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei1Na() ) + "'");
//		}
//		if( bean.getSZokusei1Na() != null && bean.getSZokusei1Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_1_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei1Na() ) + "'");
//		}
//		if( bean.getDZokusei2Na() != null && bean.getDZokusei2Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_2_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei2Na() ) + "'");
//		}
//		if( bean.getSZokusei2Na() != null && bean.getSZokusei2Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_2_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei2Na() ) + "'");
//		}
//		if( bean.getDZokusei3Na() != null && bean.getDZokusei3Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_3_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei3Na() ) + "'");
//		}
//		if( bean.getSZokusei3Na() != null && bean.getSZokusei3Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_3_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei3Na() ) + "'");
//		}
//		if( bean.getDZokusei4Na() != null && bean.getDZokusei4Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_4_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei4Na() ) + "'");
//		}
//		if( bean.getSZokusei4Na() != null && bean.getSZokusei4Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_4_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei4Na() ) + "'");
//		}
//		if( bean.getDZokusei5Na() != null && bean.getDZokusei5Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_5_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei5Na() ) + "'");
//		}
//		if( bean.getSZokusei5Na() != null && bean.getSZokusei5Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_5_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei5Na() ) + "'");
//		}
//		if( bean.getDZokusei6Na() != null && bean.getDZokusei6Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_6_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei6Na() ) + "'");
//		}
//		if( bean.getSZokusei6Na() != null && bean.getSZokusei6Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_6_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei6Na() ) + "'");
//		}
//		if( bean.getDZokusei7Na() != null && bean.getDZokusei7Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_7_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei7Na() ) + "'");
//		}
//		if( bean.getSZokusei7Na() != null && bean.getSZokusei7Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_7_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei7Na() ) + "'");
//		}
//		if( bean.getDZokusei8Na() != null && bean.getDZokusei8Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_8_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei8Na() ) + "'");
//		}
//		if( bean.getSZokusei8Na() != null && bean.getSZokusei8Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_8_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei8Na() ) + "'");
//		}
//		if( bean.getDZokusei9Na() != null && bean.getDZokusei9Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_9_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei9Na() ) + "'");
//		}
//		if( bean.getSZokusei9Na() != null && bean.getSZokusei9Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_9_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei9Na() ) + "'");
//		}
//		if( bean.getDZokusei10Na() != null && bean.getDZokusei10Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" d_zokusei_10_na = ");
//			sb.append("'" + conv.convertString( bean.getDZokusei10Na() ) + "'");
//		}
//		if( bean.getSZokusei10Na() != null && bean.getSZokusei10Na().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" s_zokusei_10_na = ");
//			sb.append("'" + conv.convertString( bean.getSZokusei10Na() ) + "'");
//		}
		if( bean.getSentaku1() != null && bean.getSentaku1().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_1_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei1Na()) ) + "'");
		}
		if( bean.getSentaku1() != null && bean.getSentaku1().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_1_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei1Na()) ) + "'");
		}
		if( bean.getSentaku2() != null && bean.getSentaku2().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_2_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei2Na()) ) + "'");
		}
		if( bean.getSentaku2() != null && bean.getSentaku2().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_2_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei2Na()) ) + "'");
		}
		if( bean.getSentaku3() != null && bean.getSentaku3().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_3_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei3Na()) ) + "'");
		}
		if( bean.getSentaku3() != null && bean.getSentaku3().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_3_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei3Na()) ) + "'");
		}
		if( bean.getSentaku4() != null && bean.getSentaku4().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_4_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei4Na()) ) + "'");
		}
		if( bean.getSentaku4() != null && bean.getSentaku4().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_4_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei4Na()) ) + "'");
		}
		if( bean.getSentaku5() != null && bean.getSentaku5().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_5_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei5Na()) ) + "'");
		}
		if( bean.getSentaku5() != null && bean.getSentaku5().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_5_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei5Na()) ) + "'");
		}
		if( bean.getSentaku6() != null && bean.getSentaku6().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_6_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei6Na()) ) + "'");
		}
		if( bean.getSentaku6() != null && bean.getSentaku6().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_6_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei6Na()) ) + "'");
		}
		if( bean.getSentaku7() != null && bean.getSentaku7().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_7_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei7Na()) ) + "'");
		}
		if( bean.getSentaku7() != null && bean.getSentaku7().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_7_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei7Na()) ) + "'");
		}
		if( bean.getSentaku8() != null && bean.getSentaku8().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_8_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei8Na()) ) + "'");
		}
		if( bean.getSentaku8() != null && bean.getSentaku8().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_8_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei8Na()) ) + "'");
		}
		if( bean.getSentaku9() != null && bean.getSentaku9().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_9_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei9Na()) ) + "'");
		}
		if( bean.getSentaku9() != null && bean.getSentaku9().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_9_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei9Na()) ) + "'");
		}
		if( bean.getSentaku10() != null && bean.getSentaku10().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" d_zokusei_10_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getDZokusei10Na()) ) + "'");
		}
		if( bean.getSentaku10() != null && bean.getSentaku10().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_zokusei_10_na = ");
			sb.append("'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSZokusei10Na()) ) + "'");
		}
//BUGNO-S017 2005.04.22 S.Takahashi END
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


		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" hanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" yuko_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
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
		mst620101_SyohinZokuseiBean bean = (mst620101_SyohinZokuseiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_syohin where ");
		sb.append(" hanku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
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
