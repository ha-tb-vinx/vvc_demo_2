/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタ一括修正のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ一括修正のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/17)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタ一括修正のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ一括修正のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/17)初版作成
 */
public class mst170101_SyohinIkkatuMenteDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst170101_SyohinIkkatuMenteDM()
	{
		super( mst000101_ConstDictionary.CONNECTION_STR  );
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
		mst170101_SyohinIkkatuMenteBean bean = new mst170101_SyohinIkkatuMenteBean();
		bean.setTourokuTs( rest.getString("touroku_ts") );
		bean.setTorokuUserId( rest.getString("toroku_user_id") );
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓		
		bean.setUpNeirertChgKb("neire_henko_kb");
		bean.setUpNeirertChgVl("neire_rt");
		bean.setBumonCd("bumon_cd");
		bean.setSiiresakikanriCd(rest.getString("ten_siiresaki_kanri_cd"));
		bean.setBayiaCd(rest.getString("syokai_user_id"));
		bean.setUpEoskbaChgVl("eos_kb");
		bean.setUnitCd("unit_cd");
		bean.setUpHachuStDt("ten_hachu_st_dt");
		bean.setUpHachuEdDt("ten_hachu_ed_dt");	
		bean.setTananoNb("tana_no_nb");
//		bean.setHankuCd( rest.getString("hanku_cd") );
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑			
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setGenkaChgKb( rest.getString("genka_chg_kb") );
		bean.setGenkaChgVl( rest.getLong("genka_chg_vl") );
		bean.setBaikaChgKb( rest.getString("baika_chg_kb") );
		bean.setBaikaChgVl( rest.getLong("baika_chg_vl") );
		bean.setHanbaiStDt( rest.getString("hanbai_st_dt") );
		bean.setHanbaiEdDt( rest.getString("hanbai_ed_dt") );
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
		sb.append("touroku_ts ");
		sb.append(", ");
		sb.append("toroku_user_id ");
		sb.append(", ");
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓		
//		sb.append("hanku_cd ");
//		sb.append(", ");
		sb.append("neire_henko_kb ");
		sb.append(", ");
		sb.append("ten_siiresaki_kanri_cd ");
		sb.append(", ");
		sb.append("bumon_cd ");
		sb.append(", ");
		sb.append("siiresaki_cd ");
		sb.append(", ");
		sb.append("ten_hachu_ed_dt ");
		sb.append(", ");
		sb.append("neire_rt ");
		sb.append(", ");
		sb.append("ten_hachu_st_dt ");
		sb.append(", ");
		sb.append("eos_kb ");
		sb.append(", ");
		sb.append("unit_cd ");
		sb.append(", ");
		sb.append("syokai_user_id ");
		sb.append(", ");
		sb.append("tana_no_nb ");
		sb.append(", ");
		sb.append("yuko_dt");
		sb.append(",");
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑			
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("siiresaki_cd ");
		sb.append(", ");
		sb.append("genka_chg_kb ");
		sb.append(", ");
		sb.append("genka_chg_vl ");
		sb.append(", ");
		sb.append("baika_chg_kb ");
		sb.append(", ");
		sb.append("baika_chg_vl ");
		sb.append(", ");
		sb.append("hanbai_st_dt ");
		sb.append(", ");
		sb.append("hanbai_ed_dt ");
		sb.append(", ");
//		sb.append("   (SELECT riyo_user_na FROM r_riyo_user ");
//		sb.append("         WHERE ");
//		sb.append("           riyo_user_id = R_SYOHIN_IKKATU_MENTE.insert_user_id ");
//		sb.append("         ) insert_user_id, ");
		sb.append("   (SELECT user_na FROM r_portal_user ");
		sb.append("         WHERE ");
		sb.append("           user_id = TRIM(R_SYOHIN_IKKATU_MENTE.insert_user_id) ");
		sb.append("         ) insert_user_id, ");
		sb.append("insert_ts ");
		sb.append(", ");
//		sb.append("   (SELECT riyo_user_na FROM r_riyo_user ");
//		sb.append("         WHERE riyo_user_id = R_SYOHIN_IKKATU_MENTE.update_user_id");
//		sb.append("         ) update_user_nm, ");
		sb.append("   (SELECT user_na FROM r_portal_user ");
		sb.append("         WHERE user_id = TRIM(R_SYOHIN_IKKATU_MENTE.update_user_id) ");
		sb.append("         ) update_user_nm, ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append(", ");
		sb.append("tana_no_nb ");
		
		sb.append("from R_SYOHIN_IKKATU_MENTE  ");


		// touroku_ts に対するWHERE区
		if( map.get("touroku_ts_bef") != null && ((String)map.get("touroku_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("touroku_ts >= '" + conv.convertWhereString( (String)map.get("touroku_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("touroku_ts_aft") != null && ((String)map.get("touroku_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("touroku_ts <= '" + conv.convertWhereString( (String)map.get("touroku_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("touroku_ts") != null && ((String)map.get("touroku_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("touroku_ts = '" + conv.convertWhereString( (String)map.get("touroku_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("touroku_ts_like") != null && ((String)map.get("touroku_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("touroku_ts like '%" + conv.convertWhereString( (String)map.get("touroku_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("touroku_ts_bef_like") != null && ((String)map.get("touroku_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("touroku_ts like '%" + conv.convertWhereString( (String)map.get("touroku_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("touroku_ts_aft_like") != null && ((String)map.get("touroku_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("touroku_ts like '" + conv.convertWhereString( (String)map.get("touroku_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("touroku_ts_in") != null && ((String)map.get("touroku_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("touroku_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("touroku_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("touroku_ts_not_in") != null && ((String)map.get("touroku_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("touroku_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("touroku_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// toroku_user_id に対するWHERE区
		if( map.get("toroku_user_id_bef") != null && ((String)map.get("toroku_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("toroku_user_id >= '" + conv.convertWhereString( (String)map.get("toroku_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("toroku_user_id_aft") != null && ((String)map.get("toroku_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("toroku_user_id <= '" + conv.convertWhereString( (String)map.get("toroku_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("toroku_user_id") != null && ((String)map.get("toroku_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("toroku_user_id = '" + conv.convertWhereString( (String)map.get("toroku_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("toroku_user_id_like") != null && ((String)map.get("toroku_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("toroku_user_id like '%" + conv.convertWhereString( (String)map.get("toroku_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("toroku_user_id_bef_like") != null && ((String)map.get("toroku_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("toroku_user_id like '%" + conv.convertWhereString( (String)map.get("toroku_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("toroku_user_id_aft_like") != null && ((String)map.get("toroku_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("toroku_user_id like '" + conv.convertWhereString( (String)map.get("toroku_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("toroku_user_id_in") != null && ((String)map.get("toroku_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("toroku_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("toroku_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("toroku_user_id_not_in") != null && ((String)map.get("toroku_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("toroku_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("toroku_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}

//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
		// hanku_cd に対するWHERE区
//		if( map.get("hanku_cd_bef") != null && ((String)map.get("hanku_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku_cd >= '" + conv.convertWhereString( (String)map.get("hanku_cd_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku_cd_aft") != null && ((String)map.get("hanku_cd_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku_cd <= '" + conv.convertWhereString( (String)map.get("hanku_cd_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku_cd_like") != null && ((String)map.get("hanku_cd_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku_cd like '%" + conv.convertWhereString( (String)map.get("hanku_cd_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku_cd_bef_like") != null && ((String)map.get("hanku_cd_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku_cd like '%" + conv.convertWhereString( (String)map.get("hanku_cd_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku_cd_aft_like") != null && ((String)map.get("hanku_cd_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku_cd like '" + conv.convertWhereString( (String)map.get("hanku_cd_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku_cd_in") != null && ((String)map.get("hanku_cd_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_cd_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("hanku_cd_not_in") != null && ((String)map.get("hanku_cd_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_cd_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bumon_cd ='" + conv.convertWhereString( (String)map.get("bumon_cd") ) + "'");
			whereStr = andStr;
		}
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑

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


		// siiresaki_cd に対するWHERE区
		if( map.get("siiresaki_cd_bef") != null && ((String)map.get("siiresaki_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd >= '" + conv.convertWhereString( (String)map.get("siiresaki_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_aft") != null && ((String)map.get("siiresaki_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd <= '" + conv.convertWhereString( (String)map.get("siiresaki_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd = '" + conv.convertWhereString( (String)map.get("siiresaki_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_like") != null && ((String)map.get("siiresaki_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd like '%" + conv.convertWhereString( (String)map.get("siiresaki_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_bef_like") != null && ((String)map.get("siiresaki_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd like '%" + conv.convertWhereString( (String)map.get("siiresaki_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_aft_like") != null && ((String)map.get("siiresaki_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd like '" + conv.convertWhereString( (String)map.get("siiresaki_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_in") != null && ((String)map.get("siiresaki_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siiresaki_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_not_in") != null && ((String)map.get("siiresaki_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siiresaki_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// genka_chg_kb に対するWHERE区
		if( map.get("genka_chg_kb_bef") != null && ((String)map.get("genka_chg_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_kb >= '" + conv.convertWhereString( (String)map.get("genka_chg_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("genka_chg_kb_aft") != null && ((String)map.get("genka_chg_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_kb <= '" + conv.convertWhereString( (String)map.get("genka_chg_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("genka_chg_kb") != null && ((String)map.get("genka_chg_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_kb = '" + conv.convertWhereString( (String)map.get("genka_chg_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("genka_chg_kb_like") != null && ((String)map.get("genka_chg_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_kb like '%" + conv.convertWhereString( (String)map.get("genka_chg_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("genka_chg_kb_bef_like") != null && ((String)map.get("genka_chg_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_kb like '%" + conv.convertWhereString( (String)map.get("genka_chg_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("genka_chg_kb_aft_like") != null && ((String)map.get("genka_chg_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_kb like '" + conv.convertWhereString( (String)map.get("genka_chg_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("genka_chg_kb_in") != null && ((String)map.get("genka_chg_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("genka_chg_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("genka_chg_kb_not_in") != null && ((String)map.get("genka_chg_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("genka_chg_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// genka_chg_vl に対するWHERE区
		if( map.get("genka_chg_vl_bef") != null && ((String)map.get("genka_chg_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_vl >= " + (String)map.get("genka_chg_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("genka_chg_vl_aft") != null && ((String)map.get("genka_chg_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_vl <= " + (String)map.get("genka_chg_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("genka_chg_vl") != null && ((String)map.get("genka_chg_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("genka_chg_vl = " + (String)map.get("genka_chg_vl"));
			whereStr = andStr;
		}
		if( map.get("genka_chg_vl_in") != null && ((String)map.get("genka_chg_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_vl in ( " + conv.convertWhereString( (String)map.get("genka_chg_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("genka_chg_vl_not_in") != null && ((String)map.get("genka_chg_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_chg_vl not in ( " + conv.convertWhereString( (String)map.get("genka_chg_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// baika_chg_kb に対するWHERE区
		if( map.get("baika_chg_kb_bef") != null && ((String)map.get("baika_chg_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_kb >= '" + conv.convertWhereString( (String)map.get("baika_chg_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("baika_chg_kb_aft") != null && ((String)map.get("baika_chg_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_kb <= '" + conv.convertWhereString( (String)map.get("baika_chg_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("baika_chg_kb") != null && ((String)map.get("baika_chg_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_kb = '" + conv.convertWhereString( (String)map.get("baika_chg_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("baika_chg_kb_like") != null && ((String)map.get("baika_chg_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_kb like '%" + conv.convertWhereString( (String)map.get("baika_chg_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("baika_chg_kb_bef_like") != null && ((String)map.get("baika_chg_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_kb like '%" + conv.convertWhereString( (String)map.get("baika_chg_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("baika_chg_kb_aft_like") != null && ((String)map.get("baika_chg_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_kb like '" + conv.convertWhereString( (String)map.get("baika_chg_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("baika_chg_kb_in") != null && ((String)map.get("baika_chg_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("baika_chg_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("baika_chg_kb_not_in") != null && ((String)map.get("baika_chg_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("baika_chg_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// baika_chg_vl に対するWHERE区
		if( map.get("baika_chg_vl_bef") != null && ((String)map.get("baika_chg_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_vl >= " + (String)map.get("baika_chg_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("baika_chg_vl_aft") != null && ((String)map.get("baika_chg_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_vl <= " + (String)map.get("baika_chg_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("baika_chg_vl") != null && ((String)map.get("baika_chg_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baika_chg_vl = " + (String)map.get("baika_chg_vl"));
			whereStr = andStr;
		}
		if( map.get("baika_chg_vl_in") != null && ((String)map.get("baika_chg_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_vl in ( " + conv.convertWhereString( (String)map.get("baika_chg_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("baika_chg_vl_not_in") != null && ((String)map.get("baika_chg_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_chg_vl not in ( " + conv.convertWhereString( (String)map.get("baika_chg_vl_not_in") ) + " )");
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


		// hanbai_ed_dt に対するWHERE区
		if( map.get("hanbai_ed_dt_bef") != null && ((String)map.get("hanbai_ed_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_ed_dt >= '" + conv.convertWhereString( (String)map.get("hanbai_ed_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_ed_dt_aft") != null && ((String)map.get("hanbai_ed_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_ed_dt <= '" + conv.convertWhereString( (String)map.get("hanbai_ed_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_ed_dt = '" + conv.convertWhereString( (String)map.get("hanbai_ed_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_ed_dt_like") != null && ((String)map.get("hanbai_ed_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_ed_dt like '%" + conv.convertWhereString( (String)map.get("hanbai_ed_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_ed_dt_bef_like") != null && ((String)map.get("hanbai_ed_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_ed_dt like '%" + conv.convertWhereString( (String)map.get("hanbai_ed_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_ed_dt_aft_like") != null && ((String)map.get("hanbai_ed_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_ed_dt like '" + conv.convertWhereString( (String)map.get("hanbai_ed_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_ed_dt_in") != null && ((String)map.get("hanbai_ed_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_ed_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_ed_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanbai_ed_dt_not_in") != null && ((String)map.get("hanbai_ed_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_ed_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_ed_dt_not_in") ),",","','") + "' )");
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
		sb.append("touroku_ts");
		sb.append(",");
		sb.append("toroku_user_id");
		sb.append(",");
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓		
		sb.append("bumon_cd");
//		sb.append("hanku_cd");
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
		sb.append(",");
		sb.append("syohin_cd");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
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
		mst170101_SyohinIkkatuMenteBean bean = (mst170101_SyohinIkkatuMenteBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_SYOHIN_IKKATU_MENTE (");
		if( bean.getTourokuTs() != null && bean.getTourokuTs().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" touroku_ts");
		}
		if( bean.getTorokuUserId() != null && bean.getTorokuUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" toroku_user_id");
		}
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku_cd");
//		}
		
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bumon_cd");
		}
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siiresaki_cd");
		}
		if( bean.getGenkaChgKb() != null && bean.getGenkaChgKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" genka_chg_kb");
		}
		if( befKanma ) sb.append(",");
		sb.append(" genka_chg_vl");
		if( bean.getBaikaChgKb() != null && bean.getBaikaChgKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" baika_chg_kb");
		}
		sb.append(",");
		sb.append(" baika_chg_vl");
		if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hanbai_st_dt");
		}
		if( bean.getHanbaiEdDt() != null && bean.getHanbaiEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hanbai_ed_dt");
		}
////////↓↓仕様追加による変更（2005.06.20）↓↓////////
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" yuko_dt");
		}
		
////////↑↑仕様追加による変更（2005.06.20）↑↑////////
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
		if( bean.getSiiresakikanriCd() != null && bean.getSiiresakikanriCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" ten_siiresaki_kanri_cd");			
		}
		if( bean.getUpNeirertChgVlString()!= null && bean.getUpNeirertChgVlString().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" neire_rt");			
		}
		if( bean.getUpHachuStDt()!= null && bean.getUpHachuStDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" ten_hachu_st_dt");			
		}
		if( bean.getUpHachuEdDt()!= null && bean.getUpHachuEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" ten_hachu_Ed_dt");			
		}
		if( bean.getUpEoskbaChgVl()!= null &&  bean.getUpEoskbaChgVl().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" eos_kb");			
		}
		if( bean.getUnitCd()!= null && bean.getUnitCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" unit_cd");			
		}
		if( bean.getBayiaCd()!= null && bean.getBayiaCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syokai_user_id");			
		}
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
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
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓		
		if( bean.getUpNeirertChgKb() != null && bean.getUpNeirertChgKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" neire_henko_kb");
		}
		if( bean.getTananoNb() != null && bean.getTananoNb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tana_no_nb");
		}
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
		sb.append(")Values(");


		if( bean.getTourokuTs() != null && bean.getTourokuTs().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTourokuTs() ) + "'");
		}
		if( bean.getTorokuUserId() != null && bean.getTorokuUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTorokuUserId() ) + "'");
		}
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBumonCd() ) + "'");
		}
		
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHankuCd() ) + "'");
//		}
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getGenkaChgKb() != null && bean.getGenkaChgKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getGenkaChgKb() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getGenkaChgVlString());
		if( bean.getBaikaChgKb() != null && bean.getBaikaChgKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getBaikaChgKb() ) + "'");
		}
		sb.append(",");
		sb.append( bean.getBaikaChgVlString());
		if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHanbaiStDt() ) + "'");
		}
		if( bean.getHanbaiEdDt() != null && bean.getHanbaiEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHanbaiEdDt() ) + "'");
		}
////////↓↓仕様追加による変更（2005.06.20）↓↓////////
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
////////↑↑仕様追加による変更（2005.06.20）↑↑////////
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓		
		if( bean.getSiiresakikanriCd() != null && bean.getSiiresakikanriCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'"+ conv.convertString(bean.getSiiresakikanriCd())+"'");			
		}
		if( bean.getUpNeirertChgVlString()!= null && bean.getUpNeirertChgVlString().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'"+ bean.getUpNeirertChgVl()/100.0+"'");			
		}
		if( bean.getUpHachuStDt()!= null && bean.getUpHachuStDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'"+ conv.convertString(bean.getUpHachuStDt())+"'");			
		}
		if( bean.getUpHachuEdDt()!= null && bean.getUpHachuEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'"+ conv.convertString(bean.getUpHachuEdDt())+"'");			
		}
		if( bean.getUpEoskbaChgVl()!= null && bean.getUpEoskbaChgVl().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" +bean.getUpEoskbaChgVl() +"'");			
		}
		if( bean.getUnitCd()!= null && bean.getUnitCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'"+ conv.convertString(bean.getUnitCd())+"'");			
		}
		if( bean.getBayiaCd()!= null && bean.getBayiaCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'"+ conv.convertString(bean.getBayiaCd())+"'");			
		}
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑		
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
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
		if( bean.getUpNeirertChgKb() != null && bean.getUpNeirertChgKb().trim().length() != 0 )
		{
			 sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpNeirertChgKb() ) + "'");
		}
		if( bean.getTananoNb() != null && bean.getTananoNb().trim().length() != 0 )
		{
			 sb.append(",");
			sb.append("'" + bean.getTananoNb() + "'");
		}
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
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
		mst170101_SyohinIkkatuMenteBean bean = (mst170101_SyohinIkkatuMenteBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_SYOHIN_IKKATU_MENTE set ");
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" siiresaki_cd = ");
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getGenkaChgKb() != null && bean.getGenkaChgKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" genka_chg_kb = ");
			sb.append("'" + conv.convertString( bean.getGenkaChgKb() ) + "'");
		}
		if( befKanma ) sb.append(",");
		sb.append(" genka_chg_vl = ");
		sb.append( bean.getGenkaChgVlString());
		if( bean.getBaikaChgKb() != null && bean.getBaikaChgKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" baika_chg_kb = ");
			sb.append("'" + conv.convertString( bean.getBaikaChgKb() ) + "'");
		}
		sb.append(",");
		sb.append(" baika_chg_vl = ");
		sb.append( bean.getBaikaChgVlString());
		if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hanbai_st_dt = ");
			sb.append("'" + conv.convertString( bean.getHanbaiStDt() ) + "'");
		}
		if( bean.getHanbaiEdDt() != null && bean.getHanbaiEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hanbai_ed_dt = ");
			sb.append("'" + conv.convertString( bean.getHanbaiEdDt() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getTourokuTs() != null && bean.getTourokuTs().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" touroku_ts = ");
			sb.append("'" + conv.convertWhereString( bean.getTourokuTs() ) + "'");
		}
		if( bean.getTorokuUserId() != null && bean.getTorokuUserId().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" toroku_user_id = ");
			sb.append("'" + conv.convertWhereString( bean.getTorokuUserId() ) + "'");
		}
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓		
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
//		{
//		if( whereAnd ) sb.append(" AND "); else whereAnd = true;
//			sb.append(" hanku_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" bumon_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		}
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
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
		mst170101_SyohinIkkatuMenteBean bean = (mst170101_SyohinIkkatuMenteBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_SYOHIN_IKKATU_MENTE where ");
		sb.append(" touroku_ts = ");
		sb.append("'" + conv.convertWhereString( bean.getTourokuTs() ) + "'");
		sb.append(" AND");
		sb.append(" toroku_user_id = ");
		sb.append("'" + conv.convertWhereString( bean.getTorokuUserId() ) + "'");
		sb.append(" AND");
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓		
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
//		sb.append(" hanku_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑		
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
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
