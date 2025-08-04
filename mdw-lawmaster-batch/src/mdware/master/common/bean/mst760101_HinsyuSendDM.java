/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）品種一括送信トランのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する品種一括送信トランのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/22)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）品種一括送信トランのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する品種一括送信トランのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/22)初版作成
 */
public class mst760101_HinsyuSendDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst760101_HinsyuSendDM()
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
		mst760101_HinsyuSendBean bean = new mst760101_HinsyuSendBean();
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setSendDt( rest.getString("send_dt") );
		bean.setSelectKb( rest.getString("select_kb") );
		bean.setEntryKb( rest.getString("entry_kb") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setSendEndKb( rest.getString("send_end_kb") );
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
		sb.append("kanri_kb ");
		sb.append(", ");
		sb.append("kanri_cd ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("send_dt ");
		sb.append(", ");
		sb.append("select_kb ");
		sb.append(", ");
		sb.append("entry_kb ");
		sb.append(", ");
		sb.append("hinsyu_cd ");
		sb.append(", ");
		sb.append("send_end_kb ");
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
		sb.append("from R_HINSYU_SEND ");


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


		// send_dt に対するWHERE区
		if( map.get("send_dt_bef") != null && ((String)map.get("send_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_dt >= '" + conv.convertWhereString( (String)map.get("send_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("send_dt_aft") != null && ((String)map.get("send_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_dt <= '" + conv.convertWhereString( (String)map.get("send_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_dt = '" + conv.convertWhereString( (String)map.get("send_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("send_dt_like") != null && ((String)map.get("send_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_dt like '%" + conv.convertWhereString( (String)map.get("send_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("send_dt_bef_like") != null && ((String)map.get("send_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_dt like '%" + conv.convertWhereString( (String)map.get("send_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("send_dt_aft_like") != null && ((String)map.get("send_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_dt like '" + conv.convertWhereString( (String)map.get("send_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("send_dt_in") != null && ((String)map.get("send_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("send_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("send_dt_not_in") != null && ((String)map.get("send_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("send_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// select_kb に対するWHERE区
		if( map.get("select_kb_bef") != null && ((String)map.get("select_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("select_kb >= '" + conv.convertWhereString( (String)map.get("select_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("select_kb_aft") != null && ((String)map.get("select_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("select_kb <= '" + conv.convertWhereString( (String)map.get("select_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("select_kb") != null && ((String)map.get("select_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("select_kb = '" + conv.convertWhereString( (String)map.get("select_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("select_kb_like") != null && ((String)map.get("select_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("select_kb like '%" + conv.convertWhereString( (String)map.get("select_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("select_kb_bef_like") != null && ((String)map.get("select_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("select_kb like '%" + conv.convertWhereString( (String)map.get("select_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("select_kb_aft_like") != null && ((String)map.get("select_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("select_kb like '" + conv.convertWhereString( (String)map.get("select_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("select_kb_in") != null && ((String)map.get("select_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("select_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("select_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("select_kb_not_in") != null && ((String)map.get("select_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("select_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("select_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// entry_kb に対するWHERE区
		if( map.get("entry_kb_bef") != null && ((String)map.get("entry_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("entry_kb >= '" + conv.convertWhereString( (String)map.get("entry_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("entry_kb_aft") != null && ((String)map.get("entry_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("entry_kb <= '" + conv.convertWhereString( (String)map.get("entry_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("entry_kb") != null && ((String)map.get("entry_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("entry_kb = '" + conv.convertWhereString( (String)map.get("entry_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("entry_kb_like") != null && ((String)map.get("entry_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("entry_kb like '%" + conv.convertWhereString( (String)map.get("entry_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("entry_kb_bef_like") != null && ((String)map.get("entry_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("entry_kb like '%" + conv.convertWhereString( (String)map.get("entry_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("entry_kb_aft_like") != null && ((String)map.get("entry_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("entry_kb like '" + conv.convertWhereString( (String)map.get("entry_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("entry_kb_in") != null && ((String)map.get("entry_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("entry_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("entry_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("entry_kb_not_in") != null && ((String)map.get("entry_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("entry_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("entry_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hinsyu_cd に対するWHERE区
		if( map.get("hinsyu_cd_bef") != null && ((String)map.get("hinsyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd >= '" + conv.convertWhereString( (String)map.get("hinsyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_aft") != null && ((String)map.get("hinsyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd <= '" + conv.convertWhereString( (String)map.get("hinsyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd = '" + conv.convertWhereString( (String)map.get("hinsyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_like") != null && ((String)map.get("hinsyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd like '%" + conv.convertWhereString( (String)map.get("hinsyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_bef_like") != null && ((String)map.get("hinsyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd like '%" + conv.convertWhereString( (String)map.get("hinsyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_aft_like") != null && ((String)map.get("hinsyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd like '" + conv.convertWhereString( (String)map.get("hinsyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_in") != null && ((String)map.get("hinsyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinsyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_not_in") != null && ((String)map.get("hinsyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinsyu_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// send_end_kb に対するWHERE区
		if( map.get("send_end_kb_bef") != null && ((String)map.get("send_end_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_end_kb >= '" + conv.convertWhereString( (String)map.get("send_end_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("send_end_kb_aft") != null && ((String)map.get("send_end_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_end_kb <= '" + conv.convertWhereString( (String)map.get("send_end_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("send_end_kb") != null && ((String)map.get("send_end_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_end_kb = '" + conv.convertWhereString( (String)map.get("send_end_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("send_end_kb_like") != null && ((String)map.get("send_end_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_end_kb like '%" + conv.convertWhereString( (String)map.get("send_end_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("send_end_kb_bef_like") != null && ((String)map.get("send_end_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_end_kb like '%" + conv.convertWhereString( (String)map.get("send_end_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("send_end_kb_aft_like") != null && ((String)map.get("send_end_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_end_kb like '" + conv.convertWhereString( (String)map.get("send_end_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("send_end_kb_in") != null && ((String)map.get("send_end_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_end_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("send_end_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("send_end_kb_not_in") != null && ((String)map.get("send_end_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("send_end_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("send_end_kb_not_in") ),",","','") + "' )");
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
		sb.append("kanri_kb");
		sb.append(",");
		sb.append("kanri_cd");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("send_dt");
		sb.append(",");
		sb.append("select_kb");
		sb.append(",");
		sb.append("entry_kb");
		sb.append(",");
		sb.append("hinsyu_cd");
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
		mst760101_HinsyuSendBean bean = (mst760101_HinsyuSendBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_HINSYU_SEND (");
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" kanri_kb");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanri_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getSendDt() != null && bean.getSendDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" send_dt");
		}
		if( bean.getSelectKb() != null && bean.getSelectKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" select_kb");
		}
		if( bean.getEntryKb() != null && bean.getEntryKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" entry_kb");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinsyu_cd");
		}
		if( bean.getSendEndKb() != null && bean.getSendEndKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" send_end_kb");
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


		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getSendDt() != null && bean.getSendDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSendDt() ) + "'");
		}
		if( bean.getSelectKb() != null && bean.getSelectKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSelectKb() ) + "'");
		}
		if( bean.getEntryKb() != null && bean.getEntryKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getEntryKb() ) + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHinsyuCd() ) + "'");
		}
		if( bean.getSendEndKb() != null && bean.getSendEndKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSendEndKb() ) + "'");
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
		mst760101_HinsyuSendBean bean = (mst760101_HinsyuSendBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_HINSYU_SEND set ");
		if( bean.getSendEndKb() != null && bean.getSendEndKb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" send_end_kb = ");
			sb.append("'" + conv.convertString( bean.getSendEndKb() ) + "'");
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


		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" kanri_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getSendDt() != null && bean.getSendDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" send_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getSendDt() ) + "'");
		}
		if( bean.getSelectKb() != null && bean.getSelectKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" select_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getSelectKb() ) + "'");
		}
		if( bean.getEntryKb() != null && bean.getEntryKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" entry_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getEntryKb() ) + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" hinsyu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getHinsyuCd() ) + "'");
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
		mst760101_HinsyuSendBean bean = (mst760101_HinsyuSendBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_HINSYU_SEND where ");
		sb.append(" kanri_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		sb.append(" AND");
		sb.append(" kanri_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append(" AND");		
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append(" AND");
		sb.append(" send_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getSendDt() ) + "'");
		sb.append(" AND");
		sb.append(" select_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getSelectKb() ) + "'");
		sb.append(" AND");
		sb.append(" entry_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getEntryKb() ) + "'");
		sb.append(" AND");
		sb.append(" hinsyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getHinsyuCd() ) + "'");
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
