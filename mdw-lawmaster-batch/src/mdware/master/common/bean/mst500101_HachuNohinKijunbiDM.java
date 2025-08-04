/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）発注納品基準日マスタ（生鮮以外）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する発注納品基準日マスタ（生鮮以外）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/07)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）発注納品基準日マスタ（生鮮以外）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する発注納品基準日マスタ（生鮮以外）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/07)初版作成
 */
public class mst500101_HachuNohinKijunbiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst500101_HachuNohinKijunbiDM()
	{
		//super( mst000201_ConstBean.CONNECTION_STR );
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
		//RHachunohinkijunbiBean bean = new RHachunohinkijunbiBean();
		mst500101_HachuNohinKijunbiBean bean = new mst500101_HachuNohinKijunbiBean();

		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setHachuHohoKb( rest.getString("hachu_hoho_kb") );
		bean.setHachuSimeTimeKb( rest.getString("hachu_sime_time_kb") );
		bean.setHachuMonFg( rest.getString("hachu_mon_fg") );
		bean.setHachuTueFg( rest.getString("hachu_tue_fg") );
		bean.setHachuWedFg( rest.getString("hachu_wed_fg") );
		bean.setHachuThuFg( rest.getString("hachu_thu_fg") );
		bean.setHachuFriFg( rest.getString("hachu_fri_fg") );
		bean.setHachuSatFg( rest.getString("hachu_sat_fg") );
		bean.setHachuSunFg( rest.getString("hachu_sun_fg") );
		bean.setRtimeMonKb( rest.getString("rtime_mon_kb") );
		bean.setRtimeTueKb( rest.getString("rtime_tue_kb") );
		bean.setRtimeWedKb( rest.getString("rtime_wed_kb") );
		bean.setRtimeThuKb( rest.getString("rtime_thu_kb") );
		bean.setRtimeFriKb( rest.getString("rtime_fri_kb") );
		bean.setRtimeSatKb( rest.getString("rtime_sat_kb") );
		bean.setRtimeSunKb( rest.getString("rtime_sun_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
//		↓↓仕様変更（2005.07.28）↓↓
		bean.setSyokaiTorokuTs( mst000401_LogicBean.chkNullString(rest.getString("syokai_toroku_ts")) );
		bean.setSyokaiUserId( mst000401_LogicBean.chkNullString(rest.getString("syokai_user_id")) );
//		↑↑仕様変更（2005.07.28）↑↑
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
		sb.append("yuko_dt ");
		sb.append(", ");
		sb.append("kanri_kb ");
		sb.append(", ");
		sb.append("kanri_cd ");
		sb.append(", ");
		sb.append("siiresaki_cd ");
		sb.append(", ");
		sb.append("hachu_hoho_kb ");
		sb.append(", ");
		sb.append("hachu_sime_time_kb ");
		sb.append(", ");
		sb.append("hachu_mon_fg ");
		sb.append(", ");
		sb.append("hachu_tue_fg ");
		sb.append(", ");
		sb.append("hachu_wed_fg ");
		sb.append(", ");
		sb.append("hachu_thu_fg ");
		sb.append(", ");
		sb.append("hachu_fri_fg ");
		sb.append(", ");
		sb.append("hachu_sat_fg ");
		sb.append(", ");
		sb.append("hachu_sun_fg ");
		sb.append(", ");
		sb.append("rtime_mon_kb ");
		sb.append(", ");
		sb.append("rtime_tue_kb ");
		sb.append(", ");
		sb.append("rtime_wed_kb ");
		sb.append(", ");
		sb.append("rtime_thu_kb ");
		sb.append(", ");
		sb.append("rtime_fri_kb ");
		sb.append(", ");
		sb.append("rtime_sat_kb ");
		sb.append(", ");
		sb.append("rtime_sun_kb ");
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
//		↓↓仕様変更（2005.07.28）↓↓
		sb.append(", ");
		sb.append("syokai_toroku_ts ");
		sb.append(", ");
		sb.append("syokai_user_id ");
//		↑↑仕様変更（2005.07.28）↑↑
		sb.append("from r_hachunohinkijunbi ");


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


		// kanri_kb に対するWHERE区
		if( map.get("kanri_kb_bef") != null && ((String)map.get("kanri_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb >= '" + conv.convertWhereString( (String)map.get("kanri_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_aft") != null && ((String)map.get("kanri_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb <= '" + conv.convertWhereString( (String)map.get("kanri_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb") != null && ((String)map.get("kanri_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb = '" + conv.convertWhereString( (String)map.get("kanri_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_like") != null && ((String)map.get("kanri_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb like '%" + conv.convertWhereString( (String)map.get("kanri_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_bef_like") != null && ((String)map.get("kanri_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb like '%" + conv.convertWhereString( (String)map.get("kanri_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_aft_like") != null && ((String)map.get("kanri_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb like '" + conv.convertWhereString( (String)map.get("kanri_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_in") != null && ((String)map.get("kanri_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_not_in") != null && ((String)map.get("kanri_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kanri_cd に対するWHERE区
		if( map.get("kanri_cd_bef") != null && ((String)map.get("kanri_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_aft") != null && ((String)map.get("kanri_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd") != null && ((String)map.get("kanri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd = '" + conv.convertWhereString( (String)map.get("kanri_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_like") != null && ((String)map.get("kanri_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd like '%" + conv.convertWhereString( (String)map.get("kanri_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_bef_like") != null && ((String)map.get("kanri_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd like '%" + conv.convertWhereString( (String)map.get("kanri_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_aft_like") != null && ((String)map.get("kanri_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd like '" + conv.convertWhereString( (String)map.get("kanri_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_in") != null && ((String)map.get("kanri_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_not_in") != null && ((String)map.get("kanri_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_cd_not_in") ),",","','") + "' )");
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


		// hachu_hoho_kb に対するWHERE区
		if( map.get("hachu_hoho_kb_bef") != null && ((String)map.get("hachu_hoho_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_hoho_kb >= '" + conv.convertWhereString( (String)map.get("hachu_hoho_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_hoho_kb_aft") != null && ((String)map.get("hachu_hoho_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_hoho_kb <= '" + conv.convertWhereString( (String)map.get("hachu_hoho_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_hoho_kb") != null && ((String)map.get("hachu_hoho_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_hoho_kb = '" + conv.convertWhereString( (String)map.get("hachu_hoho_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_hoho_kb_like") != null && ((String)map.get("hachu_hoho_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_hoho_kb like '%" + conv.convertWhereString( (String)map.get("hachu_hoho_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_hoho_kb_bef_like") != null && ((String)map.get("hachu_hoho_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_hoho_kb like '%" + conv.convertWhereString( (String)map.get("hachu_hoho_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_hoho_kb_aft_like") != null && ((String)map.get("hachu_hoho_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_hoho_kb like '" + conv.convertWhereString( (String)map.get("hachu_hoho_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_hoho_kb_in") != null && ((String)map.get("hachu_hoho_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_hoho_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_hoho_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_hoho_kb_not_in") != null && ((String)map.get("hachu_hoho_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_hoho_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_hoho_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_sime_time_kb に対するWHERE区
		if( map.get("hachu_sime_time_kb_bef") != null && ((String)map.get("hachu_sime_time_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sime_time_kb >= '" + conv.convertWhereString( (String)map.get("hachu_sime_time_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sime_time_kb_aft") != null && ((String)map.get("hachu_sime_time_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sime_time_kb <= '" + conv.convertWhereString( (String)map.get("hachu_sime_time_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sime_time_kb") != null && ((String)map.get("hachu_sime_time_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sime_time_kb = '" + conv.convertWhereString( (String)map.get("hachu_sime_time_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sime_time_kb_like") != null && ((String)map.get("hachu_sime_time_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sime_time_kb like '%" + conv.convertWhereString( (String)map.get("hachu_sime_time_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_sime_time_kb_bef_like") != null && ((String)map.get("hachu_sime_time_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sime_time_kb like '%" + conv.convertWhereString( (String)map.get("hachu_sime_time_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sime_time_kb_aft_like") != null && ((String)map.get("hachu_sime_time_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sime_time_kb like '" + conv.convertWhereString( (String)map.get("hachu_sime_time_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_sime_time_kb_in") != null && ((String)map.get("hachu_sime_time_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sime_time_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_sime_time_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_sime_time_kb_not_in") != null && ((String)map.get("hachu_sime_time_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sime_time_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_sime_time_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_mon_fg に対するWHERE区
		if( map.get("hachu_mon_fg_bef") != null && ((String)map.get("hachu_mon_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_mon_fg >= '" + conv.convertWhereString( (String)map.get("hachu_mon_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_mon_fg_aft") != null && ((String)map.get("hachu_mon_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_mon_fg <= '" + conv.convertWhereString( (String)map.get("hachu_mon_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_mon_fg") != null && ((String)map.get("hachu_mon_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_mon_fg = '" + conv.convertWhereString( (String)map.get("hachu_mon_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_mon_fg_like") != null && ((String)map.get("hachu_mon_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_mon_fg like '%" + conv.convertWhereString( (String)map.get("hachu_mon_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_mon_fg_bef_like") != null && ((String)map.get("hachu_mon_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_mon_fg like '%" + conv.convertWhereString( (String)map.get("hachu_mon_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_mon_fg_aft_like") != null && ((String)map.get("hachu_mon_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_mon_fg like '" + conv.convertWhereString( (String)map.get("hachu_mon_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_mon_fg_in") != null && ((String)map.get("hachu_mon_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_mon_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_mon_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_mon_fg_not_in") != null && ((String)map.get("hachu_mon_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_mon_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_mon_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_tue_fg に対するWHERE区
		if( map.get("hachu_tue_fg_bef") != null && ((String)map.get("hachu_tue_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tue_fg >= '" + conv.convertWhereString( (String)map.get("hachu_tue_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tue_fg_aft") != null && ((String)map.get("hachu_tue_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tue_fg <= '" + conv.convertWhereString( (String)map.get("hachu_tue_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tue_fg") != null && ((String)map.get("hachu_tue_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tue_fg = '" + conv.convertWhereString( (String)map.get("hachu_tue_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tue_fg_like") != null && ((String)map.get("hachu_tue_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tue_fg like '%" + conv.convertWhereString( (String)map.get("hachu_tue_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_tue_fg_bef_like") != null && ((String)map.get("hachu_tue_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tue_fg like '%" + conv.convertWhereString( (String)map.get("hachu_tue_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tue_fg_aft_like") != null && ((String)map.get("hachu_tue_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tue_fg like '" + conv.convertWhereString( (String)map.get("hachu_tue_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_tue_fg_in") != null && ((String)map.get("hachu_tue_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tue_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_tue_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_tue_fg_not_in") != null && ((String)map.get("hachu_tue_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tue_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_tue_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_wed_fg に対するWHERE区
		if( map.get("hachu_wed_fg_bef") != null && ((String)map.get("hachu_wed_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_wed_fg >= '" + conv.convertWhereString( (String)map.get("hachu_wed_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_wed_fg_aft") != null && ((String)map.get("hachu_wed_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_wed_fg <= '" + conv.convertWhereString( (String)map.get("hachu_wed_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_wed_fg") != null && ((String)map.get("hachu_wed_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_wed_fg = '" + conv.convertWhereString( (String)map.get("hachu_wed_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_wed_fg_like") != null && ((String)map.get("hachu_wed_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_wed_fg like '%" + conv.convertWhereString( (String)map.get("hachu_wed_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_wed_fg_bef_like") != null && ((String)map.get("hachu_wed_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_wed_fg like '%" + conv.convertWhereString( (String)map.get("hachu_wed_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_wed_fg_aft_like") != null && ((String)map.get("hachu_wed_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_wed_fg like '" + conv.convertWhereString( (String)map.get("hachu_wed_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_wed_fg_in") != null && ((String)map.get("hachu_wed_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_wed_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_wed_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_wed_fg_not_in") != null && ((String)map.get("hachu_wed_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_wed_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_wed_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_thu_fg に対するWHERE区
		if( map.get("hachu_thu_fg_bef") != null && ((String)map.get("hachu_thu_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_thu_fg >= '" + conv.convertWhereString( (String)map.get("hachu_thu_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_thu_fg_aft") != null && ((String)map.get("hachu_thu_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_thu_fg <= '" + conv.convertWhereString( (String)map.get("hachu_thu_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_thu_fg") != null && ((String)map.get("hachu_thu_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_thu_fg = '" + conv.convertWhereString( (String)map.get("hachu_thu_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_thu_fg_like") != null && ((String)map.get("hachu_thu_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_thu_fg like '%" + conv.convertWhereString( (String)map.get("hachu_thu_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_thu_fg_bef_like") != null && ((String)map.get("hachu_thu_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_thu_fg like '%" + conv.convertWhereString( (String)map.get("hachu_thu_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_thu_fg_aft_like") != null && ((String)map.get("hachu_thu_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_thu_fg like '" + conv.convertWhereString( (String)map.get("hachu_thu_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_thu_fg_in") != null && ((String)map.get("hachu_thu_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_thu_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_thu_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_thu_fg_not_in") != null && ((String)map.get("hachu_thu_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_thu_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_thu_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_fri_fg に対するWHERE区
		if( map.get("hachu_fri_fg_bef") != null && ((String)map.get("hachu_fri_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_fri_fg >= '" + conv.convertWhereString( (String)map.get("hachu_fri_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_fri_fg_aft") != null && ((String)map.get("hachu_fri_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_fri_fg <= '" + conv.convertWhereString( (String)map.get("hachu_fri_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_fri_fg") != null && ((String)map.get("hachu_fri_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_fri_fg = '" + conv.convertWhereString( (String)map.get("hachu_fri_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_fri_fg_like") != null && ((String)map.get("hachu_fri_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_fri_fg like '%" + conv.convertWhereString( (String)map.get("hachu_fri_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_fri_fg_bef_like") != null && ((String)map.get("hachu_fri_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_fri_fg like '%" + conv.convertWhereString( (String)map.get("hachu_fri_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_fri_fg_aft_like") != null && ((String)map.get("hachu_fri_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_fri_fg like '" + conv.convertWhereString( (String)map.get("hachu_fri_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_fri_fg_in") != null && ((String)map.get("hachu_fri_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_fri_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_fri_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_fri_fg_not_in") != null && ((String)map.get("hachu_fri_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_fri_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_fri_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_sat_fg に対するWHERE区
		if( map.get("hachu_sat_fg_bef") != null && ((String)map.get("hachu_sat_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sat_fg >= '" + conv.convertWhereString( (String)map.get("hachu_sat_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sat_fg_aft") != null && ((String)map.get("hachu_sat_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sat_fg <= '" + conv.convertWhereString( (String)map.get("hachu_sat_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sat_fg") != null && ((String)map.get("hachu_sat_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sat_fg = '" + conv.convertWhereString( (String)map.get("hachu_sat_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sat_fg_like") != null && ((String)map.get("hachu_sat_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sat_fg like '%" + conv.convertWhereString( (String)map.get("hachu_sat_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_sat_fg_bef_like") != null && ((String)map.get("hachu_sat_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sat_fg like '%" + conv.convertWhereString( (String)map.get("hachu_sat_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sat_fg_aft_like") != null && ((String)map.get("hachu_sat_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sat_fg like '" + conv.convertWhereString( (String)map.get("hachu_sat_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_sat_fg_in") != null && ((String)map.get("hachu_sat_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sat_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_sat_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_sat_fg_not_in") != null && ((String)map.get("hachu_sat_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sat_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_sat_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_sun_fg に対するWHERE区
		if( map.get("hachu_sun_fg_bef") != null && ((String)map.get("hachu_sun_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sun_fg >= '" + conv.convertWhereString( (String)map.get("hachu_sun_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sun_fg_aft") != null && ((String)map.get("hachu_sun_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sun_fg <= '" + conv.convertWhereString( (String)map.get("hachu_sun_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sun_fg") != null && ((String)map.get("hachu_sun_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sun_fg = '" + conv.convertWhereString( (String)map.get("hachu_sun_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sun_fg_like") != null && ((String)map.get("hachu_sun_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sun_fg like '%" + conv.convertWhereString( (String)map.get("hachu_sun_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_sun_fg_bef_like") != null && ((String)map.get("hachu_sun_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sun_fg like '%" + conv.convertWhereString( (String)map.get("hachu_sun_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_sun_fg_aft_like") != null && ((String)map.get("hachu_sun_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sun_fg like '" + conv.convertWhereString( (String)map.get("hachu_sun_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_sun_fg_in") != null && ((String)map.get("hachu_sun_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sun_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_sun_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_sun_fg_not_in") != null && ((String)map.get("hachu_sun_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_sun_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_sun_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// rtime_mon_kb に対するWHERE区
		if( map.get("rtime_mon_kb_bef") != null && ((String)map.get("rtime_mon_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_mon_kb >= '" + conv.convertWhereString( (String)map.get("rtime_mon_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_mon_kb_aft") != null && ((String)map.get("rtime_mon_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_mon_kb <= '" + conv.convertWhereString( (String)map.get("rtime_mon_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_mon_kb") != null && ((String)map.get("rtime_mon_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_mon_kb = '" + conv.convertWhereString( (String)map.get("rtime_mon_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_mon_kb_like") != null && ((String)map.get("rtime_mon_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_mon_kb like '%" + conv.convertWhereString( (String)map.get("rtime_mon_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_mon_kb_bef_like") != null && ((String)map.get("rtime_mon_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_mon_kb like '%" + conv.convertWhereString( (String)map.get("rtime_mon_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_mon_kb_aft_like") != null && ((String)map.get("rtime_mon_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_mon_kb like '" + conv.convertWhereString( (String)map.get("rtime_mon_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_mon_kb_in") != null && ((String)map.get("rtime_mon_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_mon_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_mon_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("rtime_mon_kb_not_in") != null && ((String)map.get("rtime_mon_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_mon_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_mon_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// rtime_tue_kb に対するWHERE区
		if( map.get("rtime_tue_kb_bef") != null && ((String)map.get("rtime_tue_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_tue_kb >= '" + conv.convertWhereString( (String)map.get("rtime_tue_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_tue_kb_aft") != null && ((String)map.get("rtime_tue_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_tue_kb <= '" + conv.convertWhereString( (String)map.get("rtime_tue_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_tue_kb") != null && ((String)map.get("rtime_tue_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_tue_kb = '" + conv.convertWhereString( (String)map.get("rtime_tue_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_tue_kb_like") != null && ((String)map.get("rtime_tue_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_tue_kb like '%" + conv.convertWhereString( (String)map.get("rtime_tue_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_tue_kb_bef_like") != null && ((String)map.get("rtime_tue_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_tue_kb like '%" + conv.convertWhereString( (String)map.get("rtime_tue_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_tue_kb_aft_like") != null && ((String)map.get("rtime_tue_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_tue_kb like '" + conv.convertWhereString( (String)map.get("rtime_tue_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_tue_kb_in") != null && ((String)map.get("rtime_tue_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_tue_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_tue_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("rtime_tue_kb_not_in") != null && ((String)map.get("rtime_tue_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_tue_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_tue_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// rtime_wed_kb に対するWHERE区
		if( map.get("rtime_wed_kb_bef") != null && ((String)map.get("rtime_wed_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_wed_kb >= '" + conv.convertWhereString( (String)map.get("rtime_wed_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_wed_kb_aft") != null && ((String)map.get("rtime_wed_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_wed_kb <= '" + conv.convertWhereString( (String)map.get("rtime_wed_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_wed_kb") != null && ((String)map.get("rtime_wed_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_wed_kb = '" + conv.convertWhereString( (String)map.get("rtime_wed_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_wed_kb_like") != null && ((String)map.get("rtime_wed_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_wed_kb like '%" + conv.convertWhereString( (String)map.get("rtime_wed_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_wed_kb_bef_like") != null && ((String)map.get("rtime_wed_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_wed_kb like '%" + conv.convertWhereString( (String)map.get("rtime_wed_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_wed_kb_aft_like") != null && ((String)map.get("rtime_wed_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_wed_kb like '" + conv.convertWhereString( (String)map.get("rtime_wed_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_wed_kb_in") != null && ((String)map.get("rtime_wed_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_wed_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_wed_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("rtime_wed_kb_not_in") != null && ((String)map.get("rtime_wed_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_wed_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_wed_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// rtime_thu_kb に対するWHERE区
		if( map.get("rtime_thu_kb_bef") != null && ((String)map.get("rtime_thu_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_thu_kb >= '" + conv.convertWhereString( (String)map.get("rtime_thu_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_thu_kb_aft") != null && ((String)map.get("rtime_thu_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_thu_kb <= '" + conv.convertWhereString( (String)map.get("rtime_thu_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_thu_kb") != null && ((String)map.get("rtime_thu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_thu_kb = '" + conv.convertWhereString( (String)map.get("rtime_thu_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_thu_kb_like") != null && ((String)map.get("rtime_thu_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_thu_kb like '%" + conv.convertWhereString( (String)map.get("rtime_thu_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_thu_kb_bef_like") != null && ((String)map.get("rtime_thu_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_thu_kb like '%" + conv.convertWhereString( (String)map.get("rtime_thu_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_thu_kb_aft_like") != null && ((String)map.get("rtime_thu_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_thu_kb like '" + conv.convertWhereString( (String)map.get("rtime_thu_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_thu_kb_in") != null && ((String)map.get("rtime_thu_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_thu_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_thu_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("rtime_thu_kb_not_in") != null && ((String)map.get("rtime_thu_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_thu_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_thu_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// rtime_fri_kb に対するWHERE区
		if( map.get("rtime_fri_kb_bef") != null && ((String)map.get("rtime_fri_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_fri_kb >= '" + conv.convertWhereString( (String)map.get("rtime_fri_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_fri_kb_aft") != null && ((String)map.get("rtime_fri_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_fri_kb <= '" + conv.convertWhereString( (String)map.get("rtime_fri_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_fri_kb") != null && ((String)map.get("rtime_fri_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_fri_kb = '" + conv.convertWhereString( (String)map.get("rtime_fri_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_fri_kb_like") != null && ((String)map.get("rtime_fri_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_fri_kb like '%" + conv.convertWhereString( (String)map.get("rtime_fri_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_fri_kb_bef_like") != null && ((String)map.get("rtime_fri_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_fri_kb like '%" + conv.convertWhereString( (String)map.get("rtime_fri_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_fri_kb_aft_like") != null && ((String)map.get("rtime_fri_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_fri_kb like '" + conv.convertWhereString( (String)map.get("rtime_fri_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_fri_kb_in") != null && ((String)map.get("rtime_fri_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_fri_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_fri_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("rtime_fri_kb_not_in") != null && ((String)map.get("rtime_fri_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_fri_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_fri_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// rtime_sat_kb に対するWHERE区
		if( map.get("rtime_sat_kb_bef") != null && ((String)map.get("rtime_sat_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sat_kb >= '" + conv.convertWhereString( (String)map.get("rtime_sat_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_sat_kb_aft") != null && ((String)map.get("rtime_sat_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sat_kb <= '" + conv.convertWhereString( (String)map.get("rtime_sat_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_sat_kb") != null && ((String)map.get("rtime_sat_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sat_kb = '" + conv.convertWhereString( (String)map.get("rtime_sat_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_sat_kb_like") != null && ((String)map.get("rtime_sat_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sat_kb like '%" + conv.convertWhereString( (String)map.get("rtime_sat_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_sat_kb_bef_like") != null && ((String)map.get("rtime_sat_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sat_kb like '%" + conv.convertWhereString( (String)map.get("rtime_sat_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_sat_kb_aft_like") != null && ((String)map.get("rtime_sat_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sat_kb like '" + conv.convertWhereString( (String)map.get("rtime_sat_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_sat_kb_in") != null && ((String)map.get("rtime_sat_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sat_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_sat_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("rtime_sat_kb_not_in") != null && ((String)map.get("rtime_sat_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sat_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_sat_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// rtime_sun_kb に対するWHERE区
		if( map.get("rtime_sun_kb_bef") != null && ((String)map.get("rtime_sun_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sun_kb >= '" + conv.convertWhereString( (String)map.get("rtime_sun_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_sun_kb_aft") != null && ((String)map.get("rtime_sun_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sun_kb <= '" + conv.convertWhereString( (String)map.get("rtime_sun_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_sun_kb") != null && ((String)map.get("rtime_sun_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sun_kb = '" + conv.convertWhereString( (String)map.get("rtime_sun_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_sun_kb_like") != null && ((String)map.get("rtime_sun_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sun_kb like '%" + conv.convertWhereString( (String)map.get("rtime_sun_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_sun_kb_bef_like") != null && ((String)map.get("rtime_sun_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sun_kb like '%" + conv.convertWhereString( (String)map.get("rtime_sun_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("rtime_sun_kb_aft_like") != null && ((String)map.get("rtime_sun_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sun_kb like '" + conv.convertWhereString( (String)map.get("rtime_sun_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("rtime_sun_kb_in") != null && ((String)map.get("rtime_sun_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sun_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_sun_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("rtime_sun_kb_not_in") != null && ((String)map.get("rtime_sun_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rtime_sun_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("rtime_sun_kb_not_in") ),",","','") + "' )");
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
		
//		↓↓仕様変更（2005.07.28）↓↓
		// syokai_toroku_ts に対するWHERE区
		if( map.get("syokai_toroku_ts_bef") != null && ((String)map.get("syokai_toroku_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_toroku_ts >= '" + conv.convertWhereString( (String)map.get("syokai_toroku_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syokai_toroku_ts_aft") != null && ((String)map.get("syokai_toroku_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_toroku_ts <= '" + conv.convertWhereString( (String)map.get("syokai_toroku_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syokai_toroku_ts") != null && ((String)map.get("syokai_toroku_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_toroku_ts = '" + conv.convertWhereString( (String)map.get("syokai_toroku_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syokai_toroku_ts_like") != null && ((String)map.get("syokai_toroku_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_toroku_ts like '%" + conv.convertWhereString( (String)map.get("syokai_toroku_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syokai_toroku_ts_bef_like") != null && ((String)map.get("syokai_toroku_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_toroku_ts like '%" + conv.convertWhereString( (String)map.get("syokai_toroku_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syokai_toroku_ts_aft_like") != null && ((String)map.get("syokai_toroku_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_toroku_ts like '" + conv.convertWhereString( (String)map.get("syokai_toroku_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syokai_toroku_ts_in") != null && ((String)map.get("syokai_toroku_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_toroku_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syokai_toroku_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syokai_toroku_ts_not_in") != null && ((String)map.get("syokai_toroku_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_toroku_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syokai_toroku_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		
		// syokai_user_id に対するWHERE区
		if( map.get("syokai_user_id_bef") != null && ((String)map.get("syokai_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_user_id >= '" + conv.convertWhereString( (String)map.get("syokai_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syokai_user_id_aft") != null && ((String)map.get("syokai_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_user_id <= '" + conv.convertWhereString( (String)map.get("syokai_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syokai_user_id") != null && ((String)map.get("syokai_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_user_id = '" + conv.convertWhereString( (String)map.get("syokai_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syokai_user_id_like") != null && ((String)map.get("syokai_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_user_id like '%" + conv.convertWhereString( (String)map.get("syokai_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syokai_user_id_bef_like") != null && ((String)map.get("syokai_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_user_id like '%" + conv.convertWhereString( (String)map.get("syokai_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syokai_user_id_aft_like") != null && ((String)map.get("syokai_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_user_id like '" + conv.convertWhereString( (String)map.get("syokai_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syokai_user_id_in") != null && ((String)map.get("syokai_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syokai_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syokai_user_id_not_in") != null && ((String)map.get("syokai_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syokai_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syokai_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
//		↑↑仕様変更（2005.07.28）↑↑
		sb.append(" order by ");
		sb.append("yuko_dt");
		sb.append(",");
		sb.append("kanri_kb");
		sb.append(",");
		sb.append("kanri_cd");
		sb.append(",");
		sb.append("siiresaki_cd");
		sb.append(",");
		sb.append("hachu_hoho_kb");
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
		//RHachunohinkijunbiBean bean = (RHachunohinkijunbiBean)beanMst;
		mst500101_HachuNohinKijunbiBean bean = (mst500101_HachuNohinKijunbiBean)beanMst;
		
		////////　↓↓　仕様追加（2005.06.24）　↓↓　////////
		if( bean.getSiiresakiCd() == null || bean.getSiiresakiCd().trim().length() == 0 ) {
			bean.setSiiresakiCd("000000");
		}
		////////　↑↑　仕様追加（2005.06.24）　↑↑　////////

		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_hachunohinkijunbi (");
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" yuko_dt");
		}
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanri_kb");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanri_cd");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siiresaki_cd");
		}
		if( bean.getHachuHohoKb() != null && bean.getHachuHohoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_hoho_kb");
		}
		if( bean.getHachuSimeTimeKb() != null && bean.getHachuSimeTimeKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_sime_time_kb");
		}
		if( bean.getHachuMonFg() != null && bean.getHachuMonFg().trim().length() != 0)
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_mon_fg");
		}

		if( bean.getHachuTueFg() != null && bean.getHachuTueFg().trim().length() != 0)
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_tue_fg");
		}
		if( bean.getHachuWedFg() != null  && bean.getHachuWedFg().trim().length() != 0)
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_wed_fg");
		}
		if( bean.getHachuThuFg() != null  && bean.getHachuThuFg().trim().length() != 0)
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_thu_fg");
		}
		if( bean.getHachuFriFg() != null && bean.getHachuFriFg().trim().length() != 0)
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_fri_fg");
		}
		if( bean.getHachuSatFg() != null  && bean.getHachuSatFg().trim().length() != 0)
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_sat_fg");
		}
		if( bean.getHachuSunFg() != null && bean.getHachuSunFg().trim().length() != 0)
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_sun_fg");
		}		
		if( bean.getRtimeMonKb() != null && bean.getRtimeMonKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_mon_kb");
		}
		if( bean.getRtimeTueKb() != null && bean.getRtimeTueKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_tue_kb");
		}
		if( bean.getRtimeWedKb() != null && bean.getRtimeWedKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_wed_kb");
		}
		if( bean.getRtimeThuKb() != null && bean.getRtimeThuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_thu_kb");
		}
		if( bean.getRtimeFriKb() != null && bean.getRtimeFriKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_fri_kb");
		}
		if( bean.getRtimeSatKb() != null && bean.getRtimeSatKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_sat_kb");
		}
		if( bean.getRtimeSunKb() != null && bean.getRtimeSunKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_sun_kb");
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
//		↓↓仕様変更（2005.07.28）↓↓
		if( bean.getSyokaiTorokuTs() != null && bean.getSyokaiTorokuTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syokai_toroku_ts");
		}
		if( bean.getSyokaiUserId() != null && bean.getSyokaiUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syokai_user_id");
		}
//		↑↑仕様変更（2005.07.28）↑↑


		sb.append(")Values(");


		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getHachuHohoKb() != null && bean.getHachuHohoKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuHohoKb() ) + "'");
		}
		if( bean.getHachuSimeTimeKb() != null && bean.getHachuSimeTimeKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuSimeTimeKb() ) + "'");
		}
		if( bean.getHachuMonFg() != null && bean.getHachuMonFg().trim().length() != 0)
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuMonFg() ) + "'");
		}
		if( bean.getHachuTueFg() != null && bean.getHachuTueFg().trim().length() != 0)
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuTueFg() ) + "'");
		}
		if( bean.getHachuWedFg() != null && bean.getHachuWedFg().trim().length() != 0)
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuWedFg() ) + "'");
		}		
		if( bean.getHachuThuFg() != null  && bean.getHachuThuFg().trim().length() != 0)
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuThuFg() ) + "'");
		}
		if( bean.getHachuFriFg() != null && bean.getHachuFriFg().trim().length() != 0)
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuFriFg() ) + "'");
		}
		if( bean.getHachuSatFg() != null  && bean.getHachuSatFg().trim().length() != 0)
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuSatFg() ) + "'");
		}
		if( bean.getHachuSunFg() != null && bean.getHachuSunFg().trim().length() != 0)
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuSunFg() ) + "'");
		}
		if( bean.getRtimeMonKb() != null && bean.getRtimeMonKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getRtimeMonKb() ) + "'");
		}
		if( bean.getRtimeTueKb() != null && bean.getRtimeTueKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getRtimeTueKb() ) + "'");
		}
		if( bean.getRtimeWedKb() != null && bean.getRtimeWedKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getRtimeWedKb() ) + "'");
		}
		if( bean.getRtimeThuKb() != null && bean.getRtimeThuKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getRtimeThuKb() ) + "'");
		}
		if( bean.getRtimeFriKb() != null && bean.getRtimeFriKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getRtimeFriKb() ) + "'");
		}
		if( bean.getRtimeSatKb() != null && bean.getRtimeSatKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getRtimeSatKb() ) + "'");
		}
		if( bean.getRtimeSunKb() != null && bean.getRtimeSunKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getRtimeSunKb() ) + "'");
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
//		↓↓仕様変更（2005.07.28）↓↓
		if( bean.getSyokaiTorokuTs() != null && bean.getSyokaiTorokuTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append("'" + conv.convertString( bean.getSyokaiTorokuTs() ) + "'");
		}
		if( bean.getSyokaiUserId() != null && bean.getSyokaiUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append("'" + conv.convertString( bean.getSyokaiUserId() ) + "'");
		}
//		↑↑仕様変更（2005.07.28）↑↑

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
		//RHachunohinkijunbiBean bean = (RHachunohinkijunbiBean)beanMst;
		mst500101_HachuNohinKijunbiBean bean = (mst500101_HachuNohinKijunbiBean)beanMst;

		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_hachunohinkijunbi set ");

//		↓↓仕様変更（2005.09.26）↓↓
		if( bean.getHachuSimeTimeKb() != null && bean.getHachuSimeTimeKb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" hachu_sime_time_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuSimeTimeKb() ) + "'");
		}
//		befKanma = true;
//		sb.append(" hachu_sime_time_kb = ");
//		sb.append("'" + mst000401_LogicBean.chkNullString(conv.convertString( bean.getHachuSimeTimeKb() )) + "'");
//		↑↑仕様変更（2005.09.26）↑↑

		if( bean.getHachuMonFg() != null && bean.getHachuMonFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_mon_fg = ");
			sb.append("'" + conv.convertString( bean.getHachuMonFg() ) + "'");
		}
		if( bean.getHachuTueFg() != null && bean.getHachuTueFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_tue_fg = ");
			sb.append("'" + conv.convertString( bean.getHachuTueFg() ) + "'");
		}
		if( bean.getHachuWedFg() != null && bean.getHachuWedFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_wed_fg = ");
			sb.append("'" + conv.convertString( bean.getHachuWedFg() ) + "'");
		}
		if( bean.getHachuThuFg() != null && bean.getHachuThuFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_thu_fg = ");
			sb.append("'" + conv.convertString( bean.getHachuThuFg() ) + "'");
		}
		if( bean.getHachuFriFg() != null && bean.getHachuFriFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_fri_fg = ");
			sb.append("'" + conv.convertString( bean.getHachuFriFg() ) + "'");
		}
		if( bean.getHachuSatFg() != null && bean.getHachuSatFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_sat_fg = ");
			sb.append("'" + conv.convertString( bean.getHachuSatFg() ) + "'");
		}
		if( bean.getHachuSunFg() != null && bean.getHachuSunFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_sun_fg = ");
			sb.append("'" + conv.convertString( bean.getHachuSunFg() ) + "'");
		}
//		↓↓仕様変更（2005.09.26）↓↓----beanに値がある項目のみ、updateの対象とする----
		if( bean.getRtimeMonKb() != null && bean.getRtimeMonKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_mon_kb = ");
			sb.append("'" + conv.convertString( bean.getRtimeMonKb() ) + "'");
//			sb.append("'" + mst000401_LogicBean.chkNullString(conv.convertString( bean.getRtimeMonKb() )) + "'");
		}
		if( bean.getRtimeTueKb() != null && bean.getRtimeTueKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_tue_kb = ");
			sb.append("'" + conv.convertString( bean.getRtimeTueKb() ) + "'");
//			sb.append("'" + mst000401_LogicBean.chkNullString(conv.convertString( bean.getRtimeTueKb() )) + "'");
		}
		if( bean.getRtimeWedKb() != null && bean.getRtimeWedKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_wed_kb = ");
			sb.append("'" + conv.convertString( bean.getRtimeWedKb() ) + "'");
//			sb.append("'" + mst000401_LogicBean.chkNullString(conv.convertString( bean.getRtimeWedKb() )) + "'");
		}
		if( bean.getRtimeThuKb() != null && bean.getRtimeThuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_thu_kb = ");
			sb.append("'" + conv.convertString( bean.getRtimeThuKb() ) + "'");
//			sb.append("'" + mst000401_LogicBean.chkNullString(conv.convertString( bean.getRtimeThuKb() )) + "'");
		}
		if( bean.getRtimeFriKb() != null && bean.getRtimeFriKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_fri_kb = ");
			sb.append("'" + conv.convertString( bean.getRtimeFriKb() ) + "'");
//			sb.append("'" + mst000401_LogicBean.chkNullString(conv.convertString( bean.getRtimeFriKb() )) + "'");
		}
		if( bean.getRtimeSatKb() != null && bean.getRtimeSatKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_sat_kb = ");
			sb.append("'" + conv.convertString( bean.getRtimeSatKb() ) + "'");
//			sb.append("'" + mst000401_LogicBean.chkNullString(conv.convertString( bean.getRtimeSatKb() )) + "'");
		}
		if( bean.getRtimeSunKb() != null && bean.getRtimeSunKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" rtime_sun_kb = ");
			sb.append("'" + conv.convertString( bean.getRtimeSunKb() ) + "'");
//			sb.append("'" + mst000401_LogicBean.chkNullString(conv.convertString( bean.getRtimeSunKb() )) + "'");
		}
//		↑↑仕様変更（2005.09.26）↑↑----beanに値がある項目のみ、updateの対象とする----
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
//		↓↓仕様変更（2005.07.28）↓↓
		if( bean.getSyokaiTorokuTs() != null && bean.getSyokaiTorokuTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syokai_toroku_ts = ");
			sb.append("'" + conv.convertString( bean.getSyokaiTorokuTs() ) + "'");
		}
		if( bean.getSyokaiUserId() != null && bean.getSyokaiUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syokai_user_id = ");
			sb.append("'" + conv.convertString( bean.getSyokaiUserId() ) + "'");
		}
//		↑↑仕様変更（2005.07.28）↑↑



		sb.append(" WHERE");


		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" yuko_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		}
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" siiresaki_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getHachuHohoKb() != null && bean.getHachuHohoKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" hachu_hoho_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getHachuHohoKb() ) + "'");
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
		//RHachunohinkijunbiBean bean = (RHachunohinkijunbiBean)beanMst;
		mst500101_HachuNohinKijunbiBean bean = (mst500101_HachuNohinKijunbiBean)beanMst;

		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_hachunohinkijunbi where ");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		sb.append(" AND");
		sb.append(" kanri_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		sb.append(" AND");
		sb.append(" kanri_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append(" AND");
		sb.append(" siiresaki_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() ) + "'");
		sb.append(" AND");
		sb.append(" hachu_hoho_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getHachuHohoKb() ) + "'");
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
