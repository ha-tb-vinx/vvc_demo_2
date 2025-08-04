/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst850101_Yobidasinotenhaneibi用呼出NO別店別反映日マスタのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst850101_Yobidasinotenhaneibi用呼出NO別店別反映日マスタのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst850101_Yobidasinotenhaneibi用呼出NO別店別反映日マスタのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst850101_Yobidasinotenhaneibi用呼出NO別店別反映日マスタのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst850101_TenHaneibiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst850101_TenHaneibiDM()
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
		mst850101_TenHaneibiBean bean = new mst850101_TenHaneibiBean();
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setThemeCd( rest.getString("theme_cd") );
		bean.setKeiryoHankuCd( rest.getString("keiryo_hanku_cd") );
		bean.setSyohinYobidasi( rest.getString("syohin_yobidasi") );
		bean.setHaneiDt( rest.getString("hanei_dt") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenHaneiDt( rest.getString("ten_hanei_dt") );
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
		sb.append("s_gyosyu_cd ");
		sb.append(", ");
		sb.append("theme_cd ");
		sb.append(", ");
		sb.append("keiryo_hanku_cd ");
		sb.append(", ");
		sb.append("syohin_yobidasi ");
		sb.append(", ");
		sb.append("hanei_dt ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("ten_hanei_dt ");
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
		sb.append("from R_YOBIDASINO_TENHANEIBI ");


		// s_gyosyu_cd に対するWHERE区
		if( map.get("s_gyosyu_cd_bef") != null && ((String)map.get("s_gyosyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd >= '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_aft") != null && ((String)map.get("s_gyosyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd <= '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd") != null && ((String)map.get("s_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_like") != null && ((String)map.get("s_gyosyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_bef_like") != null && ((String)map.get("s_gyosyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_aft_like") != null && ((String)map.get("s_gyosyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd like '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_in") != null && ((String)map.get("s_gyosyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_gyosyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_not_in") != null && ((String)map.get("s_gyosyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_gyosyu_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// theme_cd に対するWHERE区
		if( map.get("theme_cd_bef") != null && ((String)map.get("theme_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("theme_cd >= '" + conv.convertWhereString( (String)map.get("theme_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_aft") != null && ((String)map.get("theme_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("theme_cd <= '" + conv.convertWhereString( (String)map.get("theme_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("theme_cd") != null && ((String)map.get("theme_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("theme_cd = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_like") != null && ((String)map.get("theme_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("theme_cd like '%" + conv.convertWhereString( (String)map.get("theme_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_bef_like") != null && ((String)map.get("theme_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("theme_cd like '%" + conv.convertWhereString( (String)map.get("theme_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_aft_like") != null && ((String)map.get("theme_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("theme_cd like '" + conv.convertWhereString( (String)map.get("theme_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_in") != null && ((String)map.get("theme_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("theme_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("theme_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("theme_cd_not_in") != null && ((String)map.get("theme_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("theme_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("theme_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// keiryo_hanku_cd に対するWHERE区
		if( map.get("keiryo_hanku_cd_bef") != null && ((String)map.get("keiryo_hanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiryo_hanku_cd >= '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_aft") != null && ((String)map.get("keiryo_hanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiryo_hanku_cd <= '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd") != null && ((String)map.get("keiryo_hanku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiryo_hanku_cd = '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_like") != null && ((String)map.get("keiryo_hanku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiryo_hanku_cd like '%" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_bef_like") != null && ((String)map.get("keiryo_hanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiryo_hanku_cd like '%" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_aft_like") != null && ((String)map.get("keiryo_hanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiryo_hanku_cd like '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_in") != null && ((String)map.get("keiryo_hanku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiryo_hanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiryo_hanku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_not_in") != null && ((String)map.get("keiryo_hanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiryo_hanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiryo_hanku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_yobidasi に対するWHERE区
		if( map.get("syohin_yobidasi_bef") != null && ((String)map.get("syohin_yobidasi_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_yobidasi >= '" + conv.convertWhereString( (String)map.get("syohin_yobidasi_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_aft") != null && ((String)map.get("syohin_yobidasi_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_yobidasi <= '" + conv.convertWhereString( (String)map.get("syohin_yobidasi_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi") != null && ((String)map.get("syohin_yobidasi")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_yobidasi = '" + conv.convertWhereString( (String)map.get("syohin_yobidasi") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_like") != null && ((String)map.get("syohin_yobidasi_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_yobidasi like '%" + conv.convertWhereString( (String)map.get("syohin_yobidasi_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_bef_like") != null && ((String)map.get("syohin_yobidasi_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_yobidasi like '%" + conv.convertWhereString( (String)map.get("syohin_yobidasi_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_aft_like") != null && ((String)map.get("syohin_yobidasi_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_yobidasi like '" + conv.convertWhereString( (String)map.get("syohin_yobidasi_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_in") != null && ((String)map.get("syohin_yobidasi_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_yobidasi in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_yobidasi_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_not_in") != null && ((String)map.get("syohin_yobidasi_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_yobidasi not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_yobidasi_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hanei_dt に対するWHERE区
		if( map.get("hanei_dt_bef") != null && ((String)map.get("hanei_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanei_dt >= '" + conv.convertWhereString( (String)map.get("hanei_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_aft") != null && ((String)map.get("hanei_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanei_dt <= '" + conv.convertWhereString( (String)map.get("hanei_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt") != null && ((String)map.get("hanei_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanei_dt = '" + conv.convertWhereString( (String)map.get("hanei_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_like") != null && ((String)map.get("hanei_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanei_dt like '%" + conv.convertWhereString( (String)map.get("hanei_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_bef_like") != null && ((String)map.get("hanei_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanei_dt like '%" + conv.convertWhereString( (String)map.get("hanei_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_aft_like") != null && ((String)map.get("hanei_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanei_dt like '" + conv.convertWhereString( (String)map.get("hanei_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_in") != null && ((String)map.get("hanei_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanei_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanei_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_not_in") != null && ((String)map.get("hanei_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanei_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanei_dt_not_in") ),",","','") + "' )");
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


		// ten_hanei_dt に対するWHERE区
		if( map.get("ten_hanei_dt_bef") != null && ((String)map.get("ten_hanei_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hanei_dt >= '" + conv.convertWhereString( (String)map.get("ten_hanei_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hanei_dt_aft") != null && ((String)map.get("ten_hanei_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hanei_dt <= '" + conv.convertWhereString( (String)map.get("ten_hanei_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hanei_dt") != null && ((String)map.get("ten_hanei_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hanei_dt = '" + conv.convertWhereString( (String)map.get("ten_hanei_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hanei_dt_like") != null && ((String)map.get("ten_hanei_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hanei_dt like '%" + conv.convertWhereString( (String)map.get("ten_hanei_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_hanei_dt_bef_like") != null && ((String)map.get("ten_hanei_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hanei_dt like '%" + conv.convertWhereString( (String)map.get("ten_hanei_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_hanei_dt_aft_like") != null && ((String)map.get("ten_hanei_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hanei_dt like '" + conv.convertWhereString( (String)map.get("ten_hanei_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_hanei_dt_in") != null && ((String)map.get("ten_hanei_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hanei_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_hanei_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_hanei_dt_not_in") != null && ((String)map.get("ten_hanei_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_hanei_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_hanei_dt_not_in") ),",","','") + "' )");
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
		sb.append("s_gyosyu_cd");
		sb.append(",");
		sb.append("theme_cd");
		sb.append(",");
		sb.append("keiryo_hanku_cd");
		sb.append(",");
		sb.append("syohin_yobidasi");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("hanei_dt");
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
		mst850101_TenHaneibiBean bean = (mst850101_TenHaneibiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_YOBIDASINO_TENHANEIBI (");
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" s_gyosyu_cd");
		}
		if( bean.getThemeCd() != null && bean.getThemeCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" theme_cd");
		}
		if( bean.getKeiryoHankuCd() != null && bean.getKeiryoHankuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiryo_hanku_cd");
		}
		if( bean.getSyohinYobidasi() != null && bean.getSyohinYobidasi().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_yobidasi");
		}
		if( bean.getHaneiDt() != null && bean.getHaneiDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanei_dt");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getTenHaneiDt() != null && bean.getTenHaneiDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_hanei_dt");
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


		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSGyosyuCd() ) + "'");
		}
		if( bean.getThemeCd() != null && bean.getThemeCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getThemeCd() ) + "'");
		}
		if( bean.getKeiryoHankuCd() != null && bean.getKeiryoHankuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKeiryoHankuCd() ) + "'");
		}
		if( bean.getSyohinYobidasi() != null && bean.getSyohinYobidasi().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinYobidasi() ) + "'");
		}
		if( bean.getHaneiDt() != null && bean.getHaneiDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHaneiDt() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getTenHaneiDt() != null && bean.getTenHaneiDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenHaneiDt() ) + "'");
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
		boolean befKanma = false;
		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst850101_TenHaneibiBean bean = (mst850101_TenHaneibiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_YOBIDASINO_TENHANEIBI set ");
		if( bean.getTenHaneiDt() != null && bean.getTenHaneiDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" ten_hanei_dt = ");
			sb.append("'" + conv.convertString( bean.getTenHaneiDt() ) + "'");
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


		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" s_gyosyu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSGyosyuCd() ) + "'");
		}
		if( bean.getThemeCd() != null && bean.getThemeCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" theme_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getThemeCd() ) + "'");
		}
		if( bean.getKeiryoHankuCd() != null && bean.getKeiryoHankuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" keiryo_hanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKeiryoHankuCd() ) + "'");
		}
		if( bean.getSyohinYobidasi() != null && bean.getSyohinYobidasi().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_yobidasi = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinYobidasi() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
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
		mst850101_TenHaneibiBean bean = (mst850101_TenHaneibiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_YOBIDASINO_TENHANEIBI where ");
		sb.append(" s_gyosyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSGyosyuCd() ) + "'");
		sb.append(" AND");
		sb.append(" theme_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getThemeCd() ) + "'");
		sb.append(" AND");
		sb.append(" keiryo_hanku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKeiryoHankuCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_yobidasi = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinYobidasi() ) + "'");
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			sb.append(" AND ");
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(" AND ");
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getDeleteFg() ) + "'");
		}

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
