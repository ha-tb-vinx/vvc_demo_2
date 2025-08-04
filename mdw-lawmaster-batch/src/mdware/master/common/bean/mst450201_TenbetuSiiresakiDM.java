/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別仕入先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別仕入先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別仕入先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別仕入先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
public class mst450201_TenbetuSiiresakiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst450201_TenbetuSiiresakiDM()
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
		mst450201_TenbetuSiiresakiBean bean = new mst450201_TenbetuSiiresakiBean();
//	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓		
//		bean.setKanriKb( rest.getString("kanri_kb") );
//		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setBumonCd( rest.getString("kanri_cd") );
//	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑		
		bean.setTenSiiresakiKanriCd( rest.getString("ten_siiresaki_kanri_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setInsertTs( mst000401_LogicBean.chkNullString(rest.getString("insert_ts")).trim() );
		bean.setInsertUserId( mst000401_LogicBean.chkNullString(rest.getString("insert_user_id")).trim() );
		bean.setUpdateTs( mst000401_LogicBean.chkNullString(rest.getString("update_ts")).trim() );
		bean.setUpdateUserId( mst000401_LogicBean.chkNullString(rest.getString("update_user_id")).trim() );
		bean.setDeleteFg( rest.getString("delete_fg") );
//		↓↓仕様追加による変更（2005.06.30）↓↓
		bean.setSinkiTorokuDt( rest.getString("sinki_toroku_dt") );
		bean.setHenkoDt( rest.getString("henko_dt") );
//		↑↑仕様追加による変更（2005.06.30）↑↑
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
		sb.append("ten_siiresaki_kanri_cd ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("siiresaki_cd ");
		sb.append(", ");
		sb.append("trim(insert_ts) as insert_ts ");
		sb.append(", ");
		sb.append("trim(insert_user_id) as insert_user_id ");
		sb.append(", ");
		sb.append("trim(update_ts) as  update_ts ");
		sb.append(", ");
		sb.append("trim(update_user_id) as update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		//↓↓仕様追加による変更（2005.06.29）↓↓
		sb.append(", ");
		sb.append("sinki_toroku_dt ");
		sb.append(", ");
		sb.append("henko_dt ");
		//↑↑仕様追加による変更（2005.06.29）↑↑

		sb.append("from r_tenbetu_siiresaki ");


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


		// ten_siiresaki_kanri_cd に対するWHERE区
		if( map.get("ten_siiresaki_kanri_cd_bef") != null && ((String)map.get("ten_siiresaki_kanri_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_siiresaki_kanri_cd >= '" + conv.convertWhereString( (String)map.get("ten_siiresaki_kanri_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_siiresaki_kanri_cd_aft") != null && ((String)map.get("ten_siiresaki_kanri_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_siiresaki_kanri_cd <= '" + conv.convertWhereString( (String)map.get("ten_siiresaki_kanri_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_siiresaki_kanri_cd") != null && ((String)map.get("ten_siiresaki_kanri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_siiresaki_kanri_cd = '" + conv.convertWhereString( (String)map.get("ten_siiresaki_kanri_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_siiresaki_kanri_cd_like") != null && ((String)map.get("ten_siiresaki_kanri_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_siiresaki_kanri_cd like '%" + conv.convertWhereString( (String)map.get("ten_siiresaki_kanri_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_siiresaki_kanri_cd_bef_like") != null && ((String)map.get("ten_siiresaki_kanri_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_siiresaki_kanri_cd like '%" + conv.convertWhereString( (String)map.get("ten_siiresaki_kanri_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ten_siiresaki_kanri_cd_aft_like") != null && ((String)map.get("ten_siiresaki_kanri_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_siiresaki_kanri_cd like '" + conv.convertWhereString( (String)map.get("ten_siiresaki_kanri_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ten_siiresaki_kanri_cd_in") != null && ((String)map.get("ten_siiresaki_kanri_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_siiresaki_kanri_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_siiresaki_kanri_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ten_siiresaki_kanri_cd_not_in") != null && ((String)map.get("ten_siiresaki_kanri_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ten_siiresaki_kanri_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ten_siiresaki_kanri_cd_not_in") ),",","','") + "' )");
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
		sb.append("ten_siiresaki_kanri_cd");
		sb.append(",");
		sb.append("tenpo_cd");
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
		mst450201_TenbetuSiiresakiBean bean = (mst450201_TenbetuSiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_tenbetu_siiresaki (");
//	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓		
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			befKanma = true;
//			sb.append(" kanri_kb");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" kanri_cd");
//		}
		
		befKanma = true;
		sb.append(" kanri_kb");
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanri_cd");
		}
//	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑			
		if( bean.getTenSiiresakiKanriCd() != null && bean.getTenSiiresakiKanriCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ten_siiresaki_kanri_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siiresaki_cd");
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
		//↓↓仕様追加による変更（2005.06.29）↓↓
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
		//↑↑仕様追加による変更（2005.06.29）↑↑


		sb.append(")Values(");

//	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓	
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
//		}
		aftKanma = true;
		sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + StringUtility.charFormat(conv.convertString( bean.getBumonCd() ),4,"0",true) + "'");
		}
		
//	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑		
		if( bean.getTenSiiresakiKanriCd() != null && bean.getTenSiiresakiKanriCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenSiiresakiKanriCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
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
		//↓↓仕様追加による変更（2005.06.29）↓↓
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
		//↑↑仕様追加による変更（2005.06.29）↑↑
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
		mst450201_TenbetuSiiresakiBean bean = (mst450201_TenbetuSiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_tenbetu_siiresaki set ");
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" siiresaki_cd = ");
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
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
		//↓↓仕様追加による変更（2005.06.29）↓↓
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
		//↑↑仕様追加による変更（2005.06.29）↑↑


		sb.append(" WHERE");
//	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			whereAnd = true;
//			sb.append(" kanri_kb = ");
//			sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
//			sb.append(" kanri_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
//		}
		whereAnd = true;
		sb.append(" kanri_kb = ");
		sb.append("'" +  mst000901_KanriKbDictionary.BUMON.getCode() + "'");
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_cd = ");
			sb.append("'" + StringUtility.charFormat(conv.convertWhereString( bean.getBumonCd() ),4,"0",true) + "'");
		}
//	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑	
		if( bean.getTenSiiresakiKanriCd() != null && bean.getTenSiiresakiKanriCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" ten_siiresaki_kanri_cd = ");
			sb.append("'"+ conv.convertWhereString( bean.getTenSiiresakiKanriCd() ) + "'");
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
		mst450201_TenbetuSiiresakiBean bean = (mst450201_TenbetuSiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_tenbetu_siiresaki where ");
//	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓		
//		sb.append(" kanri_kb = ");
//		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
//		sb.append(" AND");
//		sb.append(" kanri_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append(" kanri_kb = ");
		sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
		sb.append(" AND");
		sb.append(" kanri_cd = ");
		sb.append("'" + StringUtility.charFormat(conv.convertWhereString( bean.getBumonCd() ),4,"0",true) + "'");
//	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑
		sb.append(" AND");
		sb.append(" ten_siiresaki_kanri_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenSiiresakiKanriCd() ) + "'");
		// ===BEGIN=== Modify out by kou 2006/10/13
		//sb.append(" AND");
		//sb.append(" tenpo_cd = ");
		//sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		//sb.append(" AND");
		//sb.append(" delete_fg = ");
		//sb.append("'" + conv.convertWhereString( mst000801_DelFlagDictionary.IRU.getCode()) + "'");
		if(bean.getTenpoCd() != null) {
			sb.append(" AND");
			sb.append(" tenpo_cd = ");
			sb.append("'").append(conv.convertWhereString( bean.getTenpoCd() )).append("'");
		}
		if(bean.getDeleteFg() != null) {
			sb.append(" AND");
			sb.append(" delete_fg = ");
			sb.append("'").append(conv.convertWhereString( bean.getDeleteFg() )).append("'");
		}
		// ===END=== Modify out by kou 2006/10/13
		
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
