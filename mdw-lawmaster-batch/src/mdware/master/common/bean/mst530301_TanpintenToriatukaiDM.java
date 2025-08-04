/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/18)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/18)初版作成
 */
public class mst530301_TanpintenToriatukaiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst530301_TanpintenToriatukaiDM()
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
		mst530301_TanpintenToriatukaiBean bean = new mst530301_TanpintenToriatukaiBean();
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setHanbaiStDt( rest.getString("hanbai_st_dt") );
		bean.setNonActKb( rest.getString("non_act_kb") );
		bean.setHaseimotoKb( rest.getString("haseimoto_kb") );
		bean.setTanawariPatern( rest.getString("tanawari_patern") );
		bean.setJukiNb( rest.getString("juki_nb") );
		bean.setTanaNb( rest.getString("tana_nb") );
		bean.setFaceQt( rest.getString("face_qt") );
		bean.setMinTinretuQt( rest.getString("min_tinretu_qt") );
		bean.setMaxTinretuQt( rest.getString("max_tinretu_qt") );
		bean.setBaseZaikoNisuQt( rest.getString("base_zaiko_nisu_qt") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
//		******** ＤＢ Ver3.6修正箇所 *****************************************
		bean.setUpdateUserTs( rest.getString("update_user_id") );
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
		sb.append("tenpo_cd ");
		sb.append(", ");
// 2006/02/09 kim START
// 		sb.append("yuko_dt ");
//		sb.append(", ");
//		sb.append("hanbai_st_dt ");
//		sb.append(", ");
// 2006/02/09 kim END
		sb.append("non_act_kb ");
		sb.append(", ");
		sb.append("haseimoto_kb ");
		sb.append(", ");
		sb.append("tanawari_patern ");
		sb.append(", ");
		sb.append("juki_nb ");
		sb.append(", ");
		sb.append("tana_nb ");
		sb.append(", ");
		sb.append("face_qt ");
		sb.append(", ");
		sb.append("min_tinretu_qt ");
		sb.append(", ");
		sb.append("max_tinretu_qt ");
		sb.append(", ");
		sb.append("base_zaiko_nisu_qt ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
//		******** ＤＢ Ver3.6修正箇所 *****************************************
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append("from R_TANPINTEN_TORIATUKAI ");


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


		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd >= '" + conv.convertWhereString( (String)map.get("tenpo_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft") != null && ((String)map.get("tenpo_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd <= '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_like") != null && ((String)map.get("tenpo_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_bef_like") != null && ((String)map.get("tenpo_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft_like") != null && ((String)map.get("tenpo_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_in") != null && ((String)map.get("tenpo_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_not_in") != null && ((String)map.get("tenpo_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_not_in") ),",","','") + "' )");
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


		// hanbai_st_dt に対するWHERE区
		if( map.get("hanbai_st_dt_bef") != null && ((String)map.get("hanbai_st_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_st_dt >= '" + conv.convertWhereString( (String)map.get("hanbai_st_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_st_dt_aft") != null && ((String)map.get("hanbai_st_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_st_dt <= '" + conv.convertWhereString( (String)map.get("hanbai_st_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_st_dt = '" + conv.convertWhereString( (String)map.get("hanbai_st_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_st_dt_like") != null && ((String)map.get("hanbai_st_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_st_dt like '%" + conv.convertWhereString( (String)map.get("hanbai_st_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_st_dt_bef_like") != null && ((String)map.get("hanbai_st_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_st_dt like '%" + conv.convertWhereString( (String)map.get("hanbai_st_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_st_dt_aft_like") != null && ((String)map.get("hanbai_st_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_st_dt like '" + conv.convertWhereString( (String)map.get("hanbai_st_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_st_dt_in") != null && ((String)map.get("hanbai_st_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_st_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_st_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanbai_st_dt_not_in") != null && ((String)map.get("hanbai_st_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_st_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_st_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// non_act_kb に対するWHERE区
		if( map.get("non_act_kb_bef") != null && ((String)map.get("non_act_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("non_act_kb >= '" + conv.convertWhereString( (String)map.get("non_act_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("non_act_kb_aft") != null && ((String)map.get("non_act_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("non_act_kb <= '" + conv.convertWhereString( (String)map.get("non_act_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("non_act_kb") != null && ((String)map.get("non_act_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("non_act_kb = '" + conv.convertWhereString( (String)map.get("non_act_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("non_act_kb_like") != null && ((String)map.get("non_act_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("non_act_kb like '%" + conv.convertWhereString( (String)map.get("non_act_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("non_act_kb_bef_like") != null && ((String)map.get("non_act_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("non_act_kb like '%" + conv.convertWhereString( (String)map.get("non_act_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("non_act_kb_aft_like") != null && ((String)map.get("non_act_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("non_act_kb like '" + conv.convertWhereString( (String)map.get("non_act_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("non_act_kb_in") != null && ((String)map.get("non_act_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("non_act_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("non_act_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("non_act_kb_not_in") != null && ((String)map.get("non_act_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("non_act_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("non_act_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// haseimoto_kb に対するWHERE区
		if( map.get("haseimoto_kb_bef") != null && ((String)map.get("haseimoto_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haseimoto_kb >= '" + conv.convertWhereString( (String)map.get("haseimoto_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haseimoto_kb_aft") != null && ((String)map.get("haseimoto_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haseimoto_kb <= '" + conv.convertWhereString( (String)map.get("haseimoto_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haseimoto_kb") != null && ((String)map.get("haseimoto_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haseimoto_kb = '" + conv.convertWhereString( (String)map.get("haseimoto_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haseimoto_kb_like") != null && ((String)map.get("haseimoto_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haseimoto_kb like '%" + conv.convertWhereString( (String)map.get("haseimoto_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haseimoto_kb_bef_like") != null && ((String)map.get("haseimoto_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haseimoto_kb like '%" + conv.convertWhereString( (String)map.get("haseimoto_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haseimoto_kb_aft_like") != null && ((String)map.get("haseimoto_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haseimoto_kb like '" + conv.convertWhereString( (String)map.get("haseimoto_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haseimoto_kb_in") != null && ((String)map.get("haseimoto_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haseimoto_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haseimoto_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("haseimoto_kb_not_in") != null && ((String)map.get("haseimoto_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haseimoto_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haseimoto_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tanawari_patern に対するWHERE区
		if( map.get("tanawari_patern_bef") != null && ((String)map.get("tanawari_patern_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tanawari_patern >= '" + conv.convertWhereString( (String)map.get("tanawari_patern_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tanawari_patern_aft") != null && ((String)map.get("tanawari_patern_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tanawari_patern <= '" + conv.convertWhereString( (String)map.get("tanawari_patern_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tanawari_patern") != null && ((String)map.get("tanawari_patern")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tanawari_patern = '" + conv.convertWhereString( (String)map.get("tanawari_patern") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tanawari_patern_like") != null && ((String)map.get("tanawari_patern_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tanawari_patern like '%" + conv.convertWhereString( (String)map.get("tanawari_patern_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tanawari_patern_bef_like") != null && ((String)map.get("tanawari_patern_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tanawari_patern like '%" + conv.convertWhereString( (String)map.get("tanawari_patern_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tanawari_patern_aft_like") != null && ((String)map.get("tanawari_patern_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tanawari_patern like '" + conv.convertWhereString( (String)map.get("tanawari_patern_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tanawari_patern_in") != null && ((String)map.get("tanawari_patern_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tanawari_patern in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tanawari_patern_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tanawari_patern_not_in") != null && ((String)map.get("tanawari_patern_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tanawari_patern not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tanawari_patern_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// juki_nb に対するWHERE区
		if( map.get("juki_nb_bef") != null && ((String)map.get("juki_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juki_nb >= '" + conv.convertWhereString( (String)map.get("juki_nb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("juki_nb_aft") != null && ((String)map.get("juki_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juki_nb <= '" + conv.convertWhereString( (String)map.get("juki_nb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("juki_nb") != null && ((String)map.get("juki_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juki_nb = '" + conv.convertWhereString( (String)map.get("juki_nb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("juki_nb_like") != null && ((String)map.get("juki_nb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juki_nb like '%" + conv.convertWhereString( (String)map.get("juki_nb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("juki_nb_bef_like") != null && ((String)map.get("juki_nb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juki_nb like '%" + conv.convertWhereString( (String)map.get("juki_nb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("juki_nb_aft_like") != null && ((String)map.get("juki_nb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juki_nb like '" + conv.convertWhereString( (String)map.get("juki_nb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("juki_nb_in") != null && ((String)map.get("juki_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juki_nb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("juki_nb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("juki_nb_not_in") != null && ((String)map.get("juki_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juki_nb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("juki_nb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tana_nb に対するWHERE区
		if( map.get("tana_nb_bef") != null && ((String)map.get("tana_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tana_nb >= '" + conv.convertWhereString( (String)map.get("tana_nb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tana_nb_aft") != null && ((String)map.get("tana_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tana_nb <= '" + conv.convertWhereString( (String)map.get("tana_nb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tana_nb") != null && ((String)map.get("tana_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tana_nb = '" + conv.convertWhereString( (String)map.get("tana_nb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tana_nb_like") != null && ((String)map.get("tana_nb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tana_nb like '%" + conv.convertWhereString( (String)map.get("tana_nb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tana_nb_bef_like") != null && ((String)map.get("tana_nb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tana_nb like '%" + conv.convertWhereString( (String)map.get("tana_nb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tana_nb_aft_like") != null && ((String)map.get("tana_nb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tana_nb like '" + conv.convertWhereString( (String)map.get("tana_nb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tana_nb_in") != null && ((String)map.get("tana_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tana_nb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tana_nb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tana_nb_not_in") != null && ((String)map.get("tana_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tana_nb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tana_nb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// face_qt に対するWHERE区
		if( map.get("face_qt_bef") != null && ((String)map.get("face_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("face_qt >= '" + conv.convertWhereString( (String)map.get("face_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("face_qt_aft") != null && ((String)map.get("face_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("face_qt <= '" + conv.convertWhereString( (String)map.get("face_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("face_qt") != null && ((String)map.get("face_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("face_qt = '" + conv.convertWhereString( (String)map.get("face_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("face_qt_like") != null && ((String)map.get("face_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("face_qt like '%" + conv.convertWhereString( (String)map.get("face_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("face_qt_bef_like") != null && ((String)map.get("face_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("face_qt like '%" + conv.convertWhereString( (String)map.get("face_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("face_qt_aft_like") != null && ((String)map.get("face_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("face_qt like '" + conv.convertWhereString( (String)map.get("face_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("face_qt_in") != null && ((String)map.get("face_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("face_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("face_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("face_qt_not_in") != null && ((String)map.get("face_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("face_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("face_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// min_tinretu_qt に対するWHERE区
		if( map.get("min_tinretu_qt_bef") != null && ((String)map.get("min_tinretu_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("min_tinretu_qt >= '" + conv.convertWhereString( (String)map.get("min_tinretu_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("min_tinretu_qt_aft") != null && ((String)map.get("min_tinretu_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("min_tinretu_qt <= '" + conv.convertWhereString( (String)map.get("min_tinretu_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("min_tinretu_qt") != null && ((String)map.get("min_tinretu_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("min_tinretu_qt = '" + conv.convertWhereString( (String)map.get("min_tinretu_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("min_tinretu_qt_like") != null && ((String)map.get("min_tinretu_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("min_tinretu_qt like '%" + conv.convertWhereString( (String)map.get("min_tinretu_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("min_tinretu_qt_bef_like") != null && ((String)map.get("min_tinretu_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("min_tinretu_qt like '%" + conv.convertWhereString( (String)map.get("min_tinretu_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("min_tinretu_qt_aft_like") != null && ((String)map.get("min_tinretu_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("min_tinretu_qt like '" + conv.convertWhereString( (String)map.get("min_tinretu_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("min_tinretu_qt_in") != null && ((String)map.get("min_tinretu_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("min_tinretu_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("min_tinretu_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("min_tinretu_qt_not_in") != null && ((String)map.get("min_tinretu_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("min_tinretu_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("min_tinretu_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// max_tinretu_qt に対するWHERE区
		if( map.get("max_tinretu_qt_bef") != null && ((String)map.get("max_tinretu_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_tinretu_qt >= '" + conv.convertWhereString( (String)map.get("max_tinretu_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("max_tinretu_qt_aft") != null && ((String)map.get("max_tinretu_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_tinretu_qt <= '" + conv.convertWhereString( (String)map.get("max_tinretu_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("max_tinretu_qt") != null && ((String)map.get("max_tinretu_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_tinretu_qt = '" + conv.convertWhereString( (String)map.get("max_tinretu_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("max_tinretu_qt_like") != null && ((String)map.get("max_tinretu_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_tinretu_qt like '%" + conv.convertWhereString( (String)map.get("max_tinretu_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("max_tinretu_qt_bef_like") != null && ((String)map.get("max_tinretu_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_tinretu_qt like '%" + conv.convertWhereString( (String)map.get("max_tinretu_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("max_tinretu_qt_aft_like") != null && ((String)map.get("max_tinretu_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_tinretu_qt like '" + conv.convertWhereString( (String)map.get("max_tinretu_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("max_tinretu_qt_in") != null && ((String)map.get("max_tinretu_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_tinretu_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("max_tinretu_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("max_tinretu_qt_not_in") != null && ((String)map.get("max_tinretu_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("max_tinretu_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("max_tinretu_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// base_zaiko_nisu_qt に対するWHERE区
		if( map.get("base_zaiko_nisu_qt_bef") != null && ((String)map.get("base_zaiko_nisu_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("base_zaiko_nisu_qt >= '" + conv.convertWhereString( (String)map.get("base_zaiko_nisu_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("base_zaiko_nisu_qt_aft") != null && ((String)map.get("base_zaiko_nisu_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("base_zaiko_nisu_qt <= '" + conv.convertWhereString( (String)map.get("base_zaiko_nisu_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("base_zaiko_nisu_qt") != null && ((String)map.get("base_zaiko_nisu_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("base_zaiko_nisu_qt = '" + conv.convertWhereString( (String)map.get("base_zaiko_nisu_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("base_zaiko_nisu_qt_like") != null && ((String)map.get("base_zaiko_nisu_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("base_zaiko_nisu_qt like '%" + conv.convertWhereString( (String)map.get("base_zaiko_nisu_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("base_zaiko_nisu_qt_bef_like") != null && ((String)map.get("base_zaiko_nisu_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("base_zaiko_nisu_qt like '%" + conv.convertWhereString( (String)map.get("base_zaiko_nisu_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("base_zaiko_nisu_qt_aft_like") != null && ((String)map.get("base_zaiko_nisu_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("base_zaiko_nisu_qt like '" + conv.convertWhereString( (String)map.get("base_zaiko_nisu_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("base_zaiko_nisu_qt_in") != null && ((String)map.get("base_zaiko_nisu_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("base_zaiko_nisu_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("base_zaiko_nisu_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("base_zaiko_nisu_qt_not_in") != null && ((String)map.get("base_zaiko_nisu_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("base_zaiko_nisu_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("base_zaiko_nisu_qt_not_in") ),",","','") + "' )");
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
//			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append("update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append("update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
			whereStr = andStr;
		}
//		******** ＤＢ Ver3.6修正箇所 *****************************************
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append("update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append("update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			******** ＤＢ Ver3.6修正箇所 *****************************************
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
		sb.append("tenpo_cd");
// 2006/02/09 kim START
// 		sb.append(",");
//		sb.append("yuko_dt");
// 2006/02/09 kim END
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
		mst530301_TanpintenToriatukaiBean bean = (mst530301_TanpintenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_TANPINTEN_TORIATUKAI (");
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
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_dt");
		}
		if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanbai_st_dt");
		}
		if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" non_act_kb");
		}
		if( bean.getHaseimotoKb() != null && bean.getHaseimotoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" haseimoto_kb");
		}
		if( bean.getTanawariPatern() != null && bean.getTanawariPatern().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tanawari_patern");
		}
		if( bean.getJukiNb() != null && bean.getJukiNb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" juki_nb");
		}
		if( bean.getTanaNb() != null && bean.getTanaNb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tana_nb");
		}
		if( bean.getFaceQt() != null && bean.getFaceQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" face_qt");
		}
		if( bean.getMinTinretuQt() != null && bean.getMinTinretuQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" min_tinretu_qt");
		}
		if( bean.getMaxTinretuQt() != null && bean.getMaxTinretuQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" max_tinretu_qt");
		}
		if( bean.getBaseZaikoNisuQt() != null && bean.getBaseZaikoNisuQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" base_zaiko_nisu_qt");
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
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
//			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
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
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHanbaiStDt() ) + "'");
		}
		if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNonActKb() ) + "'");
		}
		if( bean.getHaseimotoKb() != null && bean.getHaseimotoKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHaseimotoKb() ) + "'");
		}
		if( bean.getTanawariPatern() != null && bean.getTanawariPatern().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTanawariPatern() ) + "'");
		}
		if( bean.getJukiNb() != null && bean.getJukiNb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getJukiNb() ) + "'");
		}
		if( bean.getTanaNb() != null && bean.getTanaNb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTanaNb() ) + "'");
		}
		if( bean.getFaceQt() != null && bean.getFaceQt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getFaceQt() ) + "'");
		}
		if( bean.getMinTinretuQt() != null && bean.getMinTinretuQt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getMinTinretuQt() ) + "'");
		}
		if( bean.getMaxTinretuQt() != null && bean.getMaxTinretuQt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getMaxTinretuQt() ) + "'");
		}
		if( bean.getBaseZaikoNisuQt() != null && bean.getBaseZaikoNisuQt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBaseZaikoNisuQt() ) + "'");
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
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
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
		mst530301_TanpintenToriatukaiBean bean = (mst530301_TanpintenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_TANPINTEN_TORIATUKAI set ");
		if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" hanbai_st_dt = ");
			sb.append("'" + conv.convertString( bean.getHanbaiStDt() ) + "'");
		}
		if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" non_act_kb = ");
			sb.append("'" + conv.convertString( bean.getNonActKb() ) + "'");
		}
		if( bean.getHaseimotoKb() != null && bean.getHaseimotoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" haseimoto_kb = ");
			sb.append("'" + conv.convertString( bean.getHaseimotoKb() ) + "'");
		}
		if( bean.getTanawariPatern() != null && bean.getTanawariPatern().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tanawari_patern = ");
			sb.append("'" + conv.convertString( bean.getTanawariPatern() ) + "'");
		}
		if( bean.getJukiNb() != null && bean.getJukiNb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" juki_nb = ");
			sb.append("'" + conv.convertString( bean.getJukiNb() ) + "'");
		}
		if( bean.getTanaNb() != null && bean.getTanaNb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tana_nb = ");
			sb.append("'" + conv.convertString( bean.getTanaNb() ) + "'");
		}
		if( bean.getFaceQt() != null && bean.getFaceQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" face_qt = ");
			sb.append("'" + conv.convertString( bean.getFaceQt() ) + "'");
		}
		if( bean.getMinTinretuQt() != null && bean.getMinTinretuQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" min_tinretu_qt = ");
			sb.append("'" + conv.convertString( bean.getMinTinretuQt() ) + "'");
		}
		if( bean.getMaxTinretuQt() != null && bean.getMaxTinretuQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" max_tinretu_qt = ");
			sb.append("'" + conv.convertString( bean.getMaxTinretuQt() ) + "'");
		}
		if( bean.getBaseZaikoNisuQt() != null && bean.getBaseZaikoNisuQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" base_zaiko_nisu_qt = ");
			sb.append("'" + conv.convertString( bean.getBaseZaikoNisuQt() ) + "'");
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
			sb.append(" to_char(SYSDATE,'YYYYMMDDHH24MISS')");
		}
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
//			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
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
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
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
		mst530301_TanpintenToriatukaiBean bean = (mst530301_TanpintenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_TANPINTEN_TORIATUKAI where ");
		sb.append(" hanku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
//		******** ＤＢ Ver3.6修正箇所 *****************************************
//		sb.append(" AND");
//		sb.append(" yuko_dt = ");
//		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
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
