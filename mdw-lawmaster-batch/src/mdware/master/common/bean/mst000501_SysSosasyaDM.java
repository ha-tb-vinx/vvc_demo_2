/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）操作者テーブルのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する操作者テーブルのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/02/26)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）操作者テーブルのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する操作者テーブルのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/02/26)初版作成
 */
public class mst000501_SysSosasyaDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst000501_SysSosasyaDM()
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
		mst000501_SysSosasyaBean bean = new mst000501_SysSosasyaBean();
		bean.setUserId( rest.getString("user_id") );
		bean.setUserPassword( rest.getString("user_password") );
		bean.setHojinCd( rest.getString("hojin_cd") );
		bean.setGyosyuCd( rest.getString("gyosyu_cd") );
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setUriku1Cd( rest.getString("uriku1_cd") );
		bean.setUriku2Cd( rest.getString("uriku2_cd") );
		bean.setUriku3Cd( rest.getString("uriku3_cd") );
		bean.setKengenCd( rest.getString("kengen_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setUserNa( rest.getString("user_na") );
		bean.setYukoStartDt( rest.getString("yuko_start_dt") );
		bean.setYukoEndDt( rest.getString("yuko_end_dt") );
		bean.setIpAddress( rest.getString("ip_address") );
		bean.setUpdateDt( rest.getString("update_dt") );
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
		sb.append("user_id ");
		sb.append(", ");
		sb.append("user_password ");
		sb.append(", ");
		sb.append("hojin_cd ");
		sb.append(", ");
		sb.append("gyosyu_cd ");
		sb.append(", ");
		sb.append("s_gyosyu_cd ");
		sb.append(", ");
		sb.append("uriku1_cd ");
		sb.append(", ");
		sb.append("uriku2_cd ");
		sb.append(", ");
		sb.append("uriku3_cd ");
		sb.append(", ");
		sb.append("kengen_cd ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("user_na ");
		sb.append(", ");
		sb.append("yuko_start_dt ");
		sb.append(", ");
		sb.append("yuko_end_dt ");
		sb.append(", ");
		sb.append("ip_address ");
		sb.append(", ");
		sb.append("update_dt ");
		sb.append("from sys_sosasya ");


		// user_id に対するWHERE区
		if( map.get("user_id_bef") != null && ((String)map.get("user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_id >= '" + conv.convertWhereString( (String)map.get("user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("user_id_aft") != null && ((String)map.get("user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_id <= '" + conv.convertWhereString( (String)map.get("user_id_aft") ) + "'");
			whereStr = andStr;
		}
//		if( map.get("user_id") != null && ((String)map.get("user_id")).trim().length() > 0 )
//		{
			sb.append(whereStr);
			sb.append("user_id = '" + conv.convertWhereString( (String)map.get("user_id") ) + "'");
			whereStr = andStr;
//		}
		if( map.get("user_id_like") != null && ((String)map.get("user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_id like '%" + conv.convertWhereString( (String)map.get("user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("user_id_bef_like") != null && ((String)map.get("user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_id like '%" + conv.convertWhereString( (String)map.get("user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("user_id_aft_like") != null && ((String)map.get("user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_id like '" + conv.convertWhereString( (String)map.get("user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("user_id_in") != null && ((String)map.get("user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("user_id_not_in") != null && ((String)map.get("user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// user_password に対するWHERE区
		if( map.get("user_password_bef") != null && ((String)map.get("user_password_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_password >= '" + conv.convertWhereString( (String)map.get("user_password_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("user_password_aft") != null && ((String)map.get("user_password_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_password <= '" + conv.convertWhereString( (String)map.get("user_password_aft") ) + "'");
			whereStr = andStr;
		}
//		if( map.get("user_password") != null && ((String)map.get("user_password")).trim().length() > 0 )
//		{
			sb.append(whereStr);
			sb.append("user_password = '" + conv.convertWhereString( (String)map.get("user_password") ) + "'");
			whereStr = andStr;
//		}
		if( map.get("user_password_like") != null && ((String)map.get("user_password_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_password like '%" + conv.convertWhereString( (String)map.get("user_password_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("user_password_bef_like") != null && ((String)map.get("user_password_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_password like '%" + conv.convertWhereString( (String)map.get("user_password_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("user_password_aft_like") != null && ((String)map.get("user_password_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_password like '" + conv.convertWhereString( (String)map.get("user_password_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("user_password_in") != null && ((String)map.get("user_password_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_password in ( '" + replaceAll(conv.convertWhereString( (String)map.get("user_password_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("user_password_not_in") != null && ((String)map.get("user_password_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_password not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("user_password_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hojin_cd に対するWHERE区
		if( map.get("hojin_cd_bef") != null && ((String)map.get("hojin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd >= '" + conv.convertWhereString( (String)map.get("hojin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_aft") != null && ((String)map.get("hojin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd <= '" + conv.convertWhereString( (String)map.get("hojin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd") != null && ((String)map.get("hojin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd = '" + conv.convertWhereString( (String)map.get("hojin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_like") != null && ((String)map.get("hojin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd like '%" + conv.convertWhereString( (String)map.get("hojin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_bef_like") != null && ((String)map.get("hojin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd like '%" + conv.convertWhereString( (String)map.get("hojin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_aft_like") != null && ((String)map.get("hojin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd like '" + conv.convertWhereString( (String)map.get("hojin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_in") != null && ((String)map.get("hojin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hojin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_not_in") != null && ((String)map.get("hojin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hojin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// gyosyu_cd に対するWHERE区
		if( map.get("gyosyu_cd_bef") != null && ((String)map.get("gyosyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gyosyu_cd >= '" + conv.convertWhereString( (String)map.get("gyosyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gyosyu_cd_aft") != null && ((String)map.get("gyosyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gyosyu_cd <= '" + conv.convertWhereString( (String)map.get("gyosyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gyosyu_cd") != null && ((String)map.get("gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gyosyu_cd = '" + conv.convertWhereString( (String)map.get("gyosyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gyosyu_cd_like") != null && ((String)map.get("gyosyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("gyosyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("gyosyu_cd_bef_like") != null && ((String)map.get("gyosyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("gyosyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gyosyu_cd_aft_like") != null && ((String)map.get("gyosyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gyosyu_cd like '" + conv.convertWhereString( (String)map.get("gyosyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("gyosyu_cd_in") != null && ((String)map.get("gyosyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gyosyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gyosyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("gyosyu_cd_not_in") != null && ((String)map.get("gyosyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gyosyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gyosyu_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


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


		// uriku1_cd に対するWHERE区
		if( map.get("uriku1_cd_bef") != null && ((String)map.get("uriku1_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku1_cd >= '" + conv.convertWhereString( (String)map.get("uriku1_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku1_cd_aft") != null && ((String)map.get("uriku1_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku1_cd <= '" + conv.convertWhereString( (String)map.get("uriku1_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku1_cd") != null && ((String)map.get("uriku1_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku1_cd = '" + conv.convertWhereString( (String)map.get("uriku1_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku1_cd_like") != null && ((String)map.get("uriku1_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku1_cd like '%" + conv.convertWhereString( (String)map.get("uriku1_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("uriku1_cd_bef_like") != null && ((String)map.get("uriku1_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku1_cd like '%" + conv.convertWhereString( (String)map.get("uriku1_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku1_cd_aft_like") != null && ((String)map.get("uriku1_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku1_cd like '" + conv.convertWhereString( (String)map.get("uriku1_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("uriku1_cd_in") != null && ((String)map.get("uriku1_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku1_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku1_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("uriku1_cd_not_in") != null && ((String)map.get("uriku1_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku1_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku1_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// uriku2_cd に対するWHERE区
		if( map.get("uriku2_cd_bef") != null && ((String)map.get("uriku2_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku2_cd >= '" + conv.convertWhereString( (String)map.get("uriku2_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku2_cd_aft") != null && ((String)map.get("uriku2_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku2_cd <= '" + conv.convertWhereString( (String)map.get("uriku2_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku2_cd") != null && ((String)map.get("uriku2_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku2_cd = '" + conv.convertWhereString( (String)map.get("uriku2_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku2_cd_like") != null && ((String)map.get("uriku2_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku2_cd like '%" + conv.convertWhereString( (String)map.get("uriku2_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("uriku2_cd_bef_like") != null && ((String)map.get("uriku2_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku2_cd like '%" + conv.convertWhereString( (String)map.get("uriku2_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku2_cd_aft_like") != null && ((String)map.get("uriku2_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku2_cd like '" + conv.convertWhereString( (String)map.get("uriku2_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("uriku2_cd_in") != null && ((String)map.get("uriku2_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku2_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku2_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("uriku2_cd_not_in") != null && ((String)map.get("uriku2_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku2_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku2_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// uriku3_cd に対するWHERE区
		if( map.get("uriku3_cd_bef") != null && ((String)map.get("uriku3_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku3_cd >= '" + conv.convertWhereString( (String)map.get("uriku3_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku3_cd_aft") != null && ((String)map.get("uriku3_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku3_cd <= '" + conv.convertWhereString( (String)map.get("uriku3_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku3_cd") != null && ((String)map.get("uriku3_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku3_cd = '" + conv.convertWhereString( (String)map.get("uriku3_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku3_cd_like") != null && ((String)map.get("uriku3_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku3_cd like '%" + conv.convertWhereString( (String)map.get("uriku3_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("uriku3_cd_bef_like") != null && ((String)map.get("uriku3_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku3_cd like '%" + conv.convertWhereString( (String)map.get("uriku3_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("uriku3_cd_aft_like") != null && ((String)map.get("uriku3_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku3_cd like '" + conv.convertWhereString( (String)map.get("uriku3_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("uriku3_cd_in") != null && ((String)map.get("uriku3_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku3_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku3_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("uriku3_cd_not_in") != null && ((String)map.get("uriku3_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("uriku3_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("uriku3_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kengen_cd に対するWHERE区
		if( map.get("kengen_cd_bef") != null && ((String)map.get("kengen_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd >= '" + conv.convertWhereString( (String)map.get("kengen_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kengen_cd_aft") != null && ((String)map.get("kengen_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd <= '" + conv.convertWhereString( (String)map.get("kengen_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kengen_cd") != null && ((String)map.get("kengen_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd = '" + conv.convertWhereString( (String)map.get("kengen_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kengen_cd_like") != null && ((String)map.get("kengen_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd like '%" + conv.convertWhereString( (String)map.get("kengen_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kengen_cd_bef_like") != null && ((String)map.get("kengen_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd like '%" + conv.convertWhereString( (String)map.get("kengen_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kengen_cd_aft_like") != null && ((String)map.get("kengen_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd like '" + conv.convertWhereString( (String)map.get("kengen_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kengen_cd_in") != null && ((String)map.get("kengen_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kengen_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kengen_cd_not_in") != null && ((String)map.get("kengen_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kengen_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kengen_cd_not_in") ),",","','") + "' )");
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


		// user_na に対するWHERE区
		if( map.get("user_na_bef") != null && ((String)map.get("user_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_na >= '" + conv.convertWhereString( (String)map.get("user_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("user_na_aft") != null && ((String)map.get("user_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_na <= '" + conv.convertWhereString( (String)map.get("user_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("user_na") != null && ((String)map.get("user_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_na = '" + conv.convertWhereString( (String)map.get("user_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("user_na_like") != null && ((String)map.get("user_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_na like '%" + conv.convertWhereString( (String)map.get("user_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("user_na_bef_like") != null && ((String)map.get("user_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_na like '%" + conv.convertWhereString( (String)map.get("user_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("user_na_aft_like") != null && ((String)map.get("user_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_na like '" + conv.convertWhereString( (String)map.get("user_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("user_na_in") != null && ((String)map.get("user_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("user_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("user_na_not_in") != null && ((String)map.get("user_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("user_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("user_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// yuko_start_dt に対するWHERE区
		if( map.get("yuko_start_dt_bef") != null && ((String)map.get("yuko_start_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_start_dt >= '" + conv.convertWhereString( (String)map.get("yuko_start_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_start_dt_aft") != null && ((String)map.get("yuko_start_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_start_dt <= '" + conv.convertWhereString( (String)map.get("yuko_start_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_start_dt") != null && ((String)map.get("yuko_start_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_start_dt = '" + conv.convertWhereString( (String)map.get("yuko_start_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_start_dt_like") != null && ((String)map.get("yuko_start_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_start_dt like '%" + conv.convertWhereString( (String)map.get("yuko_start_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_start_dt_bef_like") != null && ((String)map.get("yuko_start_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_start_dt like '%" + conv.convertWhereString( (String)map.get("yuko_start_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_start_dt_aft_like") != null && ((String)map.get("yuko_start_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_start_dt like '" + conv.convertWhereString( (String)map.get("yuko_start_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_start_dt_in") != null && ((String)map.get("yuko_start_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_start_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_start_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yuko_start_dt_not_in") != null && ((String)map.get("yuko_start_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_start_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_start_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// yuko_end_dt に対するWHERE区
		if( map.get("yuko_end_dt_bef") != null && ((String)map.get("yuko_end_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_end_dt >= '" + conv.convertWhereString( (String)map.get("yuko_end_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_end_dt_aft") != null && ((String)map.get("yuko_end_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_end_dt <= '" + conv.convertWhereString( (String)map.get("yuko_end_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_end_dt") != null && ((String)map.get("yuko_end_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_end_dt = '" + conv.convertWhereString( (String)map.get("yuko_end_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_end_dt_like") != null && ((String)map.get("yuko_end_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_end_dt like '%" + conv.convertWhereString( (String)map.get("yuko_end_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_end_dt_bef_like") != null && ((String)map.get("yuko_end_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_end_dt like '%" + conv.convertWhereString( (String)map.get("yuko_end_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_end_dt_aft_like") != null && ((String)map.get("yuko_end_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_end_dt like '" + conv.convertWhereString( (String)map.get("yuko_end_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_end_dt_in") != null && ((String)map.get("yuko_end_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_end_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_end_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yuko_end_dt_not_in") != null && ((String)map.get("yuko_end_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_end_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_end_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ip_address に対するWHERE区
		if( map.get("ip_address_bef") != null && ((String)map.get("ip_address_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ip_address >= '" + conv.convertWhereString( (String)map.get("ip_address_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ip_address_aft") != null && ((String)map.get("ip_address_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ip_address <= '" + conv.convertWhereString( (String)map.get("ip_address_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ip_address") != null && ((String)map.get("ip_address")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ip_address = '" + conv.convertWhereString( (String)map.get("ip_address") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ip_address_like") != null && ((String)map.get("ip_address_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ip_address like '%" + conv.convertWhereString( (String)map.get("ip_address_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ip_address_bef_like") != null && ((String)map.get("ip_address_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ip_address like '%" + conv.convertWhereString( (String)map.get("ip_address_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ip_address_aft_like") != null && ((String)map.get("ip_address_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ip_address like '" + conv.convertWhereString( (String)map.get("ip_address_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ip_address_in") != null && ((String)map.get("ip_address_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ip_address in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ip_address_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ip_address_not_in") != null && ((String)map.get("ip_address_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ip_address not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ip_address_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_dt に対するWHERE区
		if( map.get("update_dt_bef") != null && ((String)map.get("update_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt >= '" + conv.convertWhereString( (String)map.get("update_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_dt_aft") != null && ((String)map.get("update_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt <= '" + conv.convertWhereString( (String)map.get("update_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_dt") != null && ((String)map.get("update_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt = '" + conv.convertWhereString( (String)map.get("update_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_dt_like") != null && ((String)map.get("update_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt like '%" + conv.convertWhereString( (String)map.get("update_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_dt_bef_like") != null && ((String)map.get("update_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt like '%" + conv.convertWhereString( (String)map.get("update_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_dt_aft_like") != null && ((String)map.get("update_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt like '" + conv.convertWhereString( (String)map.get("update_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_dt_in") != null && ((String)map.get("update_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_dt_not_in") != null && ((String)map.get("update_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("user_id");
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
		mst000501_SysSosasyaBean bean = (mst000501_SysSosasyaBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("sys_sosasya (");
		if( bean.getUserId() != null && bean.getUserId().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" user_id");
		}
		if( bean.getUserPassword() != null && bean.getUserPassword().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" user_password");
		}
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hojin_cd");
		}
		if( bean.getGyosyuCd() != null && bean.getGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gyosyu_cd");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_gyosyu_cd");
		}
		if( bean.getUriku1Cd() != null && bean.getUriku1Cd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku1_cd");
		}
		if( bean.getUriku2Cd() != null && bean.getUriku2Cd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku2_cd");
		}
		if( bean.getUriku3Cd() != null && bean.getUriku3Cd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku3_cd");
		}
		if( bean.getKengenCd() != null && bean.getKengenCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kengen_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getUserNa() != null && bean.getUserNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" user_na");
		}
		if( bean.getYukoStartDt() != null && bean.getYukoStartDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_start_dt");
		}
		if( bean.getYukoEndDt() != null && bean.getYukoEndDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_end_dt");
		}
		if( bean.getIpAddress() != null && bean.getIpAddress().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ip_address");
		}
		if( bean.getUpdateDt() != null && bean.getUpdateDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_dt");
		}


		sb.append(")Values(");


		if( bean.getUserId() != null && bean.getUserId().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUserId() ) + "'");
		}
		if( bean.getUserPassword() != null && bean.getUserPassword().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUserPassword() ) + "'");
		}
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
		}
		if( bean.getGyosyuCd() != null && bean.getGyosyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getGyosyuCd() ) + "'");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSGyosyuCd() ) + "'");
		}
		if( bean.getUriku1Cd() != null && bean.getUriku1Cd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUriku1Cd() ) + "'");
		}
		if( bean.getUriku2Cd() != null && bean.getUriku2Cd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUriku2Cd() ) + "'");
		}
		if( bean.getUriku3Cd() != null && bean.getUriku3Cd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUriku3Cd() ) + "'");
		}
		if( bean.getKengenCd() != null && bean.getKengenCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKengenCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getUserNa() != null && bean.getUserNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUserNa() ) + "'");
		}
		if( bean.getYukoStartDt() != null && bean.getYukoStartDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoStartDt() ) + "'");
		}
		if( bean.getYukoEndDt() != null && bean.getYukoEndDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoEndDt() ) + "'");
		}
		if( bean.getIpAddress() != null && bean.getIpAddress().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getIpAddress() ) + "'");
		}
		if( bean.getUpdateDt() != null && bean.getUpdateDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateDt() ) + "'");
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
		mst000501_SysSosasyaBean bean = (mst000501_SysSosasyaBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("sys_sosasya set ");
		if( bean.getUserPassword() != null && bean.getUserPassword().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" user_password = ");
			sb.append("'" + conv.convertString( bean.getUserPassword() ) + "'");
		}
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hojin_cd = ");
			sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
		}
		if( bean.getGyosyuCd() != null && bean.getGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gyosyu_cd = ");
			sb.append("'" + conv.convertString( bean.getGyosyuCd() ) + "'");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_gyosyu_cd = ");
			sb.append("'" + conv.convertString( bean.getSGyosyuCd() ) + "'");
		}
		if( bean.getUriku1Cd() != null && bean.getUriku1Cd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku1_cd = ");
			sb.append("'" + conv.convertString( bean.getUriku1Cd() ) + "'");
		}
		if( bean.getUriku2Cd() != null && bean.getUriku2Cd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku2_cd = ");
			sb.append("'" + conv.convertString( bean.getUriku2Cd() ) + "'");
		}
		if( bean.getUriku3Cd() != null && bean.getUriku3Cd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" uriku3_cd = ");
			sb.append("'" + conv.convertString( bean.getUriku3Cd() ) + "'");
		}
		if( bean.getKengenCd() != null && bean.getKengenCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kengen_cd = ");
			sb.append("'" + conv.convertString( bean.getKengenCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getUserNa() != null && bean.getUserNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" user_na = ");
			sb.append("'" + conv.convertString( bean.getUserNa() ) + "'");
		}
		if( bean.getYukoStartDt() != null && bean.getYukoStartDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_start_dt = ");
			sb.append("'" + conv.convertString( bean.getYukoStartDt() ) + "'");
		}
		if( bean.getYukoEndDt() != null && bean.getYukoEndDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_end_dt = ");
			sb.append("'" + conv.convertString( bean.getYukoEndDt() ) + "'");
		}
		if( bean.getIpAddress() != null && bean.getIpAddress().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ip_address = ");
			sb.append("'" + conv.convertString( bean.getIpAddress() ) + "'");
		}
		if( bean.getUpdateDt() != null && bean.getUpdateDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_dt = ");
			sb.append("'" + conv.convertString( bean.getUpdateDt() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getUserId() != null && bean.getUserId().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" user_id = ");
			sb.append("'" + conv.convertWhereString( bean.getUserId() ) + "'");
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
		mst000501_SysSosasyaBean bean = (mst000501_SysSosasyaBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("sys_sosasya where ");
		sb.append(" user_id = ");
		sb.append("'" + conv.convertWhereString( bean.getUserId() ) + "'");
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
