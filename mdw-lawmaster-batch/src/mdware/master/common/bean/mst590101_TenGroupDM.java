/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/13)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/13)初版作成
 */
public class mst590101_TenGroupDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst590101_TenGroupDM()
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
		mst590101_TenGroupBean bean = new mst590101_TenGroupBean();
//		 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓		
//		bean.setLGyosyuCd( rest.getString("l_gyosyu_cd") );
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setYotoKb( rest.getString("yoto_kb") );
//		bean.setGroupnoCd( rest.getLong("groupno_cd") );
		bean.setGroupnoCd( rest.getString("groupno_cd") );
//		 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setRankNb( rest.getLong("rank_nb") );
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
		String whereStr = "where \n";
		String andStr = " and \n";
		StringBuffer sb = new StringBuffer();
		sb.append("select \n");
//		 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓
//		sb.append("l_gyosyu_cd \n");
//		sb.append(", \n");
		sb.append("bumon_cd \n");
		sb.append(", \n");
//		 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑		
		sb.append("yoto_kb \n");
		sb.append(", ");
		sb.append("groupno_cd \n");
		sb.append(", ");
		sb.append("tenpo_cd \n");
		sb.append(", ");
		sb.append("rank_nb \n");
		sb.append(", ");
		sb.append("insert_ts \n");
		sb.append(", \n");
		sb.append("insert_user_id \n");
		sb.append(", \n");
		sb.append("update_ts \n");
		sb.append(", \n");
		sb.append("update_user_id \n");
		sb.append(", \n");
		sb.append("delete_fg \n");
		sb.append("from r_tengroup \n");

//		 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓
		// l_gyosyu_cd に対するWHERE区
//		if( map.get("l_gyosyu_cd_bef") != null && ((String)map.get("l_gyosyu_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("l_gyosyu_cd >= '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("l_gyosyu_cd_aft") != null && ((String)map.get("l_gyosyu_cd_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("l_gyosyu_cd <= '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("l_gyosyu_cd") != null && ((String)map.get("l_gyosyu_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("l_gyosyu_cd = '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("l_gyosyu_cd_like") != null && ((String)map.get("l_gyosyu_cd_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("l_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("l_gyosyu_cd_bef_like") != null && ((String)map.get("l_gyosyu_cd_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("l_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("l_gyosyu_cd_aft_like") != null && ((String)map.get("l_gyosyu_cd_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("l_gyosyu_cd like '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("l_gyosyu_cd_in") != null && ((String)map.get("l_gyosyu_cd_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("l_gyosyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("l_gyosyu_cd_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("l_gyosyu_cd_not_in") != null && ((String)map.get("l_gyosyu_cd_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("l_gyosyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("l_gyosyu_cd_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		 bumon_cd に対するWHERE区
			if( map.get("bumon_cd_bef") != null && ((String)map.get("bumon_cd_bef")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("bumon_cd >= '" + conv.convertWhereString(StringUtility.charFormat((String)map.get("bumon_cd_bef")  ,4,"0",true)) + "'");				
				whereStr = andStr;
			}
			if( map.get("bumon_cd_aft") != null && ((String)map.get("bumon_cd_aft")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("bumon_cd <= '" + conv.convertWhereString( StringUtility.charFormat((String)map.get("bumon_cd_aft")  ,4,"0",true) ) + "'");
				whereStr = andStr;
			}
			if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("bumon_cd = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd")  ,4,"0",true) ) + "'");
				whereStr = andStr;
			}
			if( map.get("bumon_cd_like") != null && ((String)map.get("bumon_cd_like")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("bumon_cd like '%" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd_like")  ,4,"0",true) ) + "%'");
				whereStr = andStr;
			}
			if( map.get("bumon_cd_bef_like") != null && ((String)map.get("bumon_cd_bef_like")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("bumon_cd like '%" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd_bef_like")  ,4,"0",true) ) + "'");
				whereStr = andStr;
			}
			if( map.get("bumon_cd_aft_like") != null && ((String)map.get("bumon_cd_aft_like")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("bumon_cd like '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd_aft_like")  ,4,"0",true) ) + "%'");
				whereStr = andStr;
			}
			if( map.get("bumon_cd_in") != null && ((String)map.get("bumon_cd_in")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("bumon_cd in ( '" + replaceAll(conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd_in")  ,4,"0",true) ),",","','") + "' )");
				whereStr = andStr;
			}
			if( map.get("bumon_cd_not_in") != null && ((String)map.get("bumon_cd_not_in")).trim().length() > 0 )
			{
				sb.append(whereStr);
				sb.append("bumon_cd not in ( '" + replaceAll(conv.convertWhereString( StringUtility.charFormat((String)map.get("bumon_cd_not_in")  ,4,"0",true) ),",","','") + "' )");
				whereStr = andStr;
			}
//		 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑

		// yoto_kb に対するWHERE区
		if( map.get("yoto_kb_bef") != null && ((String)map.get("yoto_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yoto_kb >= '" + conv.convertWhereString( (String)map.get("yoto_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yoto_kb_aft") != null && ((String)map.get("yoto_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yoto_kb <= '" + conv.convertWhereString( (String)map.get("yoto_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yoto_kb") != null && ((String)map.get("yoto_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yoto_kb = '" + conv.convertWhereString( (String)map.get("yoto_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yoto_kb_like") != null && ((String)map.get("yoto_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yoto_kb like '%" + conv.convertWhereString( (String)map.get("yoto_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yoto_kb_bef_like") != null && ((String)map.get("yoto_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yoto_kb like '%" + conv.convertWhereString( (String)map.get("yoto_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yoto_kb_aft_like") != null && ((String)map.get("yoto_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yoto_kb like '" + conv.convertWhereString( (String)map.get("yoto_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yoto_kb_in") != null && ((String)map.get("yoto_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yoto_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yoto_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yoto_kb_not_in") != null && ((String)map.get("yoto_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yoto_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yoto_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// groupno_cd に対するWHERE区
		if( map.get("groupno_cd_bef") != null && ((String)map.get("groupno_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd >= '" + (String)map.get("groupno_cd_bef") +" '" );
			whereStr = andStr;
		}
		if( map.get("groupno_cd_aft") != null && ((String)map.get("groupno_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd <= '" + (String)map.get("groupno_cd_aft") +" '");
			whereStr = andStr;
		}
		if( map.get("groupno_cd") != null && ((String)map.get("groupno_cd")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("groupno_cd = '" + (String)map.get("groupno_cd") +" '");
			whereStr = andStr;
		}
		if( map.get("groupno_cd_in") != null && ((String)map.get("groupno_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd in ( '" + conv.convertWhereString( (String)map.get("groupno_cd_in") ) + " ' )");
			whereStr = andStr;
		}
		if( map.get("groupno_cd_not_in") != null && ((String)map.get("groupno_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd not in (' " + conv.convertWhereString( (String)map.get("groupno_cd_not_in") ) + " ' )");
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


		// rank_nb に対するWHERE区
		if( map.get("rank_nb_bef") != null && ((String)map.get("rank_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rank_nb >= " + (String)map.get("rank_nb_bef") );
			whereStr = andStr;
		}
		if( map.get("rank_nb_aft") != null && ((String)map.get("rank_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rank_nb <= " + (String)map.get("rank_nb_aft") );
			whereStr = andStr;
		}
		if( map.get("rank_nb") != null && ((String)map.get("rank_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("rank_nb = " + (String)map.get("rank_nb"));
			whereStr = andStr;
		}
		if( map.get("rank_nb_in") != null && ((String)map.get("rank_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rank_nb in ( " + conv.convertWhereString( (String)map.get("rank_nb_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("rank_nb_not_in") != null && ((String)map.get("rank_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rank_nb not in ( " + conv.convertWhereString( (String)map.get("rank_nb_not_in") ) + " )");
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
		sb.append("\n order by \n");
		sb.append("rank_nb\n");
/*
		sb.append("l_gyosyu_cd");
		sb.append(",");
		sb.append("yoto_kb");
		sb.append(",");
		sb.append("groupno_cd");
		sb.append(",");
		sb.append("tenpo_cd");
*/
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
		mst590101_TenGroupBean bean = (mst590101_TenGroupBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_tengroup (");
//		 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓
//		if( bean.getLGyosyuCd() != null && bean.getLGyosyuCd().trim().length() != 0 )
//		{
//			befKanma = true;
//			sb.append(" l_gyosyu_cd");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
			{
				befKanma = true;
				sb.append(" bumon_cd");
			}
//		 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑
		if( bean.getYotoKb() != null && bean.getYotoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yoto_kb");
		}
		if( befKanma ) sb.append(",");
		sb.append(" groupno_cd");
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tenpo_cd");
		}
		sb.append(",");
		sb.append(" rank_nb");
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

//		 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓
//		if( bean.getLGyosyuCd() != null && bean.getLGyosyuCd().trim().length() != 0 )
//		{
//			aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getLGyosyuCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
			{
				aftKanma = true;
//				sb.append("'" + conv.convertString( bean.getBumonCd() ) + "'");
				sb.append("'" + conv.convertString(StringUtility.charFormat(  bean.getBumonCd() ,4,"0",true) ) + "'");
			}	
		if( bean.getYotoKb() != null && bean.getYotoKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYotoKb() ) + "'");
		}
		if( aftKanma ) sb.append(",");
//		sb.append( bean.getGroupnoCdString());
		sb.append("'" + StringUtility.charFormat( bean.getGroupnoCd() ,2,"0",true) +"'");	
//		 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑	
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		sb.append(",");
		sb.append( bean.getRankNbString());
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		}

		sb.append(",");
//		↓↓移植（2006.5.11）↓↓
		try {
			sb.append("'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		↑↑移植（2006.5.11）↑↑	
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
//			sb.append(",");
//			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
			sb.append(",");
//			↓↓移植（2006.5.11）↓↓
			try {
				sb.append("'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			↑↑移植（2006.5.11）↑↑
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
		mst590101_TenGroupBean bean = (mst590101_TenGroupBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_tengroup set ");
		befKanma = true;
		sb.append(" rank_nb = ");
		sb.append( bean.getRankNbString());
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
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append(" update_ts = ");
//			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
//		}
		sb.append(",");
		sb.append(" update_ts = ");
//		↓↓移植（2006.5.11）↓↓
		try {
			sb.append("'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		↑↑移植（2006.5.11）↑↑
		
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

//		 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓
//		if( bean.getLGyosyuCd() != null && bean.getLGyosyuCd().trim().length() != 0 )
//		{
//			whereAnd = true;
//			sb.append(" l_gyosyu_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getLGyosyuCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
			{
				whereAnd = true;
				sb.append(" bumon_cd = ");
//				sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
				sb.append("'" + conv.convertWhereString( StringUtility.charFormat(  bean.getBumonCd() ,4,"0",true) ) + "'");				
			}

		if( bean.getYotoKb() != null && bean.getYotoKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" yoto_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getYotoKb() ) + "'");
		}
		if( whereAnd ) sb.append(" AND ");
		sb.append(" groupno_cd = ");
//		sb.append( bean.getGroupnoCdString());
		sb.append("'"+ StringUtility.charFormat( bean.getGroupnoCd() ,2,"0",true) +"'");
//		 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑		
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			sb.append(" AND ");
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
		mst590101_TenGroupBean bean = (mst590101_TenGroupBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_tengroup where ");
//		 ↓↓2006.06.29 wangluping カスタマイズ修正↓↓
//		sb.append(" l_gyosyu_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getLGyosyuCd() ) + "'");		
//		sb.append(" AND");
		sb.append(" bumon_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");	
		sb.append("'" + conv.convertWhereString( StringUtility.charFormat(  bean.getBumonCd() ,4,"0",true) ) + "'");			
		sb.append(" AND");
		sb.append(" yoto_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getYotoKb() ) + "'");
		sb.append(" AND");
		sb.append(" groupno_cd = ");
//		sb.append( bean.getGroupnoCdString());
		sb.append( "'" +StringUtility.charFormat( bean.getGroupnoCd() ,2,"0",true) + "'");
//		 ↑↑2006.06.29 wangluping カスタマイズ修正↑↑		
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
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
