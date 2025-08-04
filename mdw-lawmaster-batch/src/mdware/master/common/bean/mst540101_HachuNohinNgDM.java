/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/03)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import java.text.DecimalFormat;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/03)初版作成
 */
public class mst540101_HachuNohinNgDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst540101_HachuNohinNgDM()
	{
		super(  mst000101_ConstDictionary.CONNECTION_STR );
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
		 mst540101_HachuNohinNgBean bean = new  mst540101_HachuNohinNgBean();
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoKanjiRn( rest.getString("tenpo_kanji_rn") );
		bean.setYmdDt( rest.getString("ymd_dt") );
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setHachuNgFg( rest.getString("hachu_ng_fg") );
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		bean.setNohinNgFg( rest.getString("nohin_ng_fg") );
		bean.setInsertTs( rest.getString("insert_ts") );
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setInsertUserTs( rest.getString("insert_user_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		bean.setUpdateTs( rest.getString("update_ts") );
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setUpdateUserTs( rest.getString("update_user_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）

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
		if( map.get("cnt") != null && ((String)map.get("cnt")).trim().length() > 0 ){
			sb.append("select ");
			sb.append("TRIM(TBL0.tenpo_cd) tenpo_cd ");
			sb.append(", ");
			sb.append("TRIM(TBL0.tenpo_kanji_rn) tenpo_kanji_rn ");
			sb.append(", ");			
			sb.append("TRIM(TBL0.ymd_dt) ymd_dt ");
			sb.append(", ");
//			↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			sb.append("MAX(TRIM(TBL0.hachu_ng_fg)) hachu_ng_fg ");
//			sb.append(", ");
//			↑↑ＤＢバージョンアップによる変更（2005.05.19）
			sb.append("MAX(TRIM(TBL0.nohin_ng_fg)) nohin_ng_fg ");
			sb.append(", ");
			sb.append("MAX(TRIM(TBL0.insert_ts)) insert_ts ");
			sb.append(", ");
//			↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			sb.append("MAX(TRIM(TBL0.insert_user_ts)) insert_user_ts ");
			sb.append("MAX(TRIM(TBL0.insert_user_id)) insert_user_id ");
//			↑↑ＤＢバージョンアップによる変更（2005.05.19）
			sb.append(", ");
			sb.append("MAX(TRIM(TBL0.update_ts)) update_ts ");
			sb.append(", ");
//			↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			sb.append("MAX(TRIM(TBL0.update_user_ts)) update_user_ts ");
			sb.append("MAX(TRIM(TBL0.update_user_id)) update_user_id ");
//			↑↑ＤＢバージョンアップによる変更（2005.05.19）
			sb.append(", ");
			sb.append("MAX(TRIM(TBL0.delete_fg)) delete_fg ");
			sb.append(" FROM (");
				
			int cnt = Integer.parseInt(((String)map.get("cnt")));
			DecimalFormat strNum = new DecimalFormat("00");
			for(int i=1; i<=cnt; i++ ){
				whereStr = "where ";
				sb.append("select '");
				sb.append(conv.convertWhereString( (String)map.get("tenpo_cd") ));				
				sb.append("' AS tenpo_cd ");
				sb.append(", ");
				sb.append("TRIM(TBL2.KANJI_RN) tenpo_kanji_rn ");
				sb.append(", '");				
				sb.append(conv.convertWhereString( (String)map.get("ymd_dt_ym") )+ strNum.format(i));
				sb.append("' AS ymd_dt ");
				sb.append(", ");
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//				sb.append(" '1' AS hachu_ng_fg ");
//				sb.append(", ");
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
				sb.append(" '1' AS nohin_ng_fg ");
				sb.append(", ");
				sb.append("'' AS insert_ts ");
				sb.append(", ");
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//				sb.append("'' AS insert_user_ts ");
				sb.append("'' AS insert_user_id ");
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
				sb.append(", ");
				sb.append("'' AS update_ts ");
				sb.append(", ");
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//				sb.append("'' AS update_user_ts ");
				sb.append("'' AS update_user_id ");
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
				sb.append(", ");
				sb.append("'' AS delete_fg ");				
				sb.append(" FROM DUAL   TBL1");
				sb.append(", ");
				sb.append("		R_TENPO TBL2 ");
				whereStr =  "where ";
				// 店マスタに対するWHERE区			
				if( map.get("tenpo_cd_mst") != null && ((String)map.get("tenpo_cd_mst")).trim().length() > 0 )
				{
					sb.append(whereStr);
					sb.append(" TBL2.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd_mst") ) + "'");
					whereStr = andStr;
				}			
				sb.append("     UNION ALL ");	
			}				
		}		
		
		whereStr = "where ";
		sb.append("select ");
		sb.append("TRIM(TBL1.tenpo_cd) tenpo_cd ");
		sb.append(", ");
		if( map.get("tenpo_cd_mst") != null && ((String)map.get("tenpo_cd_mst")).trim().length() > 0 ){
			sb.append("TRIM(TBL2.KANJI_RN) tenpo_kanji_rn ");		
		} else {
			sb.append("'' tenpo_kanji_rn ");
		}
		sb.append(", ");
		sb.append("TRIM(TBL1.ymd_dt) ymd_dt ");
		sb.append(", ");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		sb.append("TRIM(TBL1.hachu_ng_fg) hachu_ng_fg ");
//		sb.append(", ");
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		sb.append("TRIM(TBL1.nohin_ng_fg) nohin_ng_fg ");
		sb.append(", ");
		sb.append("TRIM(TBL1.insert_ts) insert_ts ");
		sb.append(", ");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		sb.append("TRIM(TBL1.insert_user_ts) insert_user_ts ");
		sb.append("TRIM(TBL1.insert_user_id) insert_user_id ");
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		sb.append(", ");
		sb.append("TRIM(TBL1.update_ts) update_ts ");
		sb.append(", ");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		sb.append("TRIM(TBL1.update_user_ts) update_user_ts ");
		sb.append("TRIM(TBL1.update_user_id) update_user_id ");
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		sb.append(", ");
		sb.append("TRIM(TBL1.delete_fg) delete_fg ");
		sb.append("from  R_HACHUNOHIN_NG TBL1 ");
		sb.append(", ");
		sb.append("		R_TENPO TBL2 ");

		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenpo_cd >= '" + conv.convertWhereString( (String)map.get("TBL1.tenpo_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft") != null && ((String)map.get("tenpo_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenpo_cd <= '" + conv.convertWhereString( (String)map.get("TBL1.tenpo_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_like") != null && ((String)map.get("tenpo_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_bef_like") != null && ((String)map.get("tenpo_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft_like") != null && ((String)map.get("tenpo_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenpo_cd like '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_in") != null && ((String)map.get("tenpo_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenpo_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_not_in") != null && ((String)map.get("tenpo_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenpo_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ymd_dt に対するWHERE区
		if( map.get("ymd_dt_bef") != null && ((String)map.get("ymd_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt >= '" + conv.convertWhereString( (String)map.get("ymd_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ymd_dt_aft") != null && ((String)map.get("ymd_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt <= '" + conv.convertWhereString( (String)map.get("ymd_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ymd_dt") != null && ((String)map.get("ymd_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt = '" + conv.convertWhereString( (String)map.get("ymd_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ymd_dt_like") != null && ((String)map.get("ymd_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt like '%" + conv.convertWhereString( (String)map.get("ymd_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ymd_dt_bef_like") != null && ((String)map.get("ymd_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt like '%" + conv.convertWhereString( (String)map.get("ymd_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ymd_dt_aft_like") != null && ((String)map.get("ymd_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt like '" + conv.convertWhereString( (String)map.get("ymd_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ymd_dt_in") != null && ((String)map.get("ymd_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ymd_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ymd_dt_not_in") != null && ((String)map.get("ymd_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ymd_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ymd_dt_ym") != null && ((String)map.get("ymd_dt_ym")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.ymd_dt BETWEEN  '" + conv.convertWhereString( (String)map.get("ymd_dt_ym") ) + "01'" 
									+ " AND '" + conv.convertWhereString( (String)map.get("ymd_dt_ym") ) + "31'");
			whereStr = andStr;
		}


//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
		// hachu_ng_fg に対するWHERE区
//		if( map.get("hachu_ng_fg_bef") != null && ((String)map.get("hachu_ng_fg_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.hachu_ng_fg >= '" + conv.convertWhereString( (String)map.get("hachu_ng_fg_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hachu_ng_fg_aft") != null && ((String)map.get("hachu_ng_fg_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.hachu_ng_fg <= '" + conv.convertWhereString( (String)map.get("hachu_ng_fg_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hachu_ng_fg") != null && ((String)map.get("hachu_ng_fg")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.hachu_ng_fg = '" + conv.convertWhereString( (String)map.get("hachu_ng_fg") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hachu_ng_fg_like") != null && ((String)map.get("hachu_ng_fg_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.hachu_ng_fg like '%" + conv.convertWhereString( (String)map.get("hachu_ng_fg_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hachu_ng_fg_bef_like") != null && ((String)map.get("hachu_ng_fg_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.hachu_ng_fg like '%" + conv.convertWhereString( (String)map.get("hachu_ng_fg_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hachu_ng_fg_aft_like") != null && ((String)map.get("hachu_ng_fg_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.hachu_ng_fg like '" + conv.convertWhereString( (String)map.get("hachu_ng_fg_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hachu_ng_fg_in") != null && ((String)map.get("hachu_ng_fg_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.hachu_ng_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_ng_fg_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("hachu_ng_fg_not_in") != null && ((String)map.get("hachu_ng_fg_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.hachu_ng_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_ng_fg_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）


		// nohin_ng_fg に対するWHERE区
		if( map.get("nohin_ng_fg_bef") != null && ((String)map.get("nohin_ng_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.nohin_ng_fg >= '" + conv.convertWhereString( (String)map.get("nohin_ng_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_ng_fg_aft") != null && ((String)map.get("nohin_ng_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.nohin_ng_fg <= '" + conv.convertWhereString( (String)map.get("nohin_ng_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_ng_fg") != null && ((String)map.get("nohin_ng_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.nohin_ng_fg = '" + conv.convertWhereString( (String)map.get("nohin_ng_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_ng_fg_like") != null && ((String)map.get("nohin_ng_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.nohin_ng_fg like '%" + conv.convertWhereString( (String)map.get("nohin_ng_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_ng_fg_bef_like") != null && ((String)map.get("nohin_ng_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.nohin_ng_fg like '%" + conv.convertWhereString( (String)map.get("nohin_ng_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_ng_fg_aft_like") != null && ((String)map.get("nohin_ng_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.nohin_ng_fg like '" + conv.convertWhereString( (String)map.get("nohin_ng_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_ng_fg_in") != null && ((String)map.get("nohin_ng_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.nohin_ng_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_ng_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("nohin_ng_fg_not_in") != null && ((String)map.get("nohin_ng_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.nohin_ng_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_ng_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_ts に対するWHERE区
		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}

//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		// insert_user_ts に対するWHERE区
//		if( map.get("insert_user_ts_bef") != null && ((String)map.get("insert_user_ts_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.insert_user_ts >= '" + conv.convertWhereString( (String)map.get("insert_user_ts_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_ts_aft") != null && ((String)map.get("insert_user_ts_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.insert_user_ts <= '" + conv.convertWhereString( (String)map.get("insert_user_ts_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_ts") != null && ((String)map.get("insert_user_ts")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.insert_user_ts = '" + conv.convertWhereString( (String)map.get("insert_user_ts") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_ts_like") != null && ((String)map.get("insert_user_ts_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.insert_user_ts like '%" + conv.convertWhereString( (String)map.get("insert_user_ts_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_ts_bef_like") != null && ((String)map.get("insert_user_ts_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.insert_user_ts like '%" + conv.convertWhereString( (String)map.get("insert_user_ts_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_ts_aft_like") != null && ((String)map.get("insert_user_ts_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.insert_user_ts like '" + conv.convertWhereString( (String)map.get("insert_user_ts_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_ts_in") != null && ((String)map.get("insert_user_ts_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.insert_user_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_ts_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("insert_user_ts_not_in") != null && ((String)map.get("insert_user_ts_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.insert_user_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_ts_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
		if( map.get("insert_user_id_bef") != null && ((String)map.get("insert_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id >= '" + conv.convertWhereString( (String)map.get("insert_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft") != null && ((String)map.get("insert_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id <= '" + conv.convertWhereString( (String)map.get("insert_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_like") != null && ((String)map.get("insert_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_bef_like") != null && ((String)map.get("insert_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft_like") != null && ((String)map.get("insert_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id like '" + conv.convertWhereString( (String)map.get("insert_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_in") != null && ((String)map.get("insert_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_not_in") != null && ((String)map.get("insert_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）


		// update_ts に対するWHERE区
		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		// update_user_ts に対するWHERE区
//		if( map.get("update_user_ts_bef") != null && ((String)map.get("update_user_ts_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.update_user_ts >= '" + conv.convertWhereString( (String)map.get("update_user_ts_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_ts_aft") != null && ((String)map.get("update_user_ts_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.update_user_ts <= '" + conv.convertWhereString( (String)map.get("update_user_ts_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_ts") != null && ((String)map.get("update_user_ts")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.update_user_ts = '" + conv.convertWhereString( (String)map.get("update_user_ts") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_ts_like") != null && ((String)map.get("update_user_ts_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.update_user_ts like '%" + conv.convertWhereString( (String)map.get("update_user_ts_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_ts_bef_like") != null && ((String)map.get("update_user_ts_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.update_user_ts like '%" + conv.convertWhereString( (String)map.get("update_user_ts_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_ts_aft_like") != null && ((String)map.get("update_user_ts_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.update_user_ts like '" + conv.convertWhereString( (String)map.get("update_user_ts_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_ts_in") != null && ((String)map.get("update_user_ts_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.update_user_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_ts_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("update_user_ts_not_in") != null && ((String)map.get("update_user_ts_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL1.update_user_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_ts_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}

		if( map.get("update_user_id_bef") != null && ((String)map.get("update_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）

		// delete_fg に対するWHERE区
		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		// 店マスタに対するWHERE区			
		if( map.get("tenpo_cd_mst") != null && ((String)map.get("tenpo_cd_mst")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(" TBL2.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd_mst") ) + "'");
			whereStr = andStr;
		}			
		
		sb.append(" order by ");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("ymd_dt");
		
		if( map.get("cnt") != null && ((String)map.get("cnt")).trim().length() > 0 ){
			sb.append(" ) TBL0 ");
			sb.append(" group by ");	
			sb.append("TBL0.tenpo_cd ");
			sb.append(", ");
			sb.append("TBL0.tenpo_kanji_rn ");
			sb.append(", ");			
			sb.append("TBL0.ymd_dt ");			
			sb.append(" order by ");
			sb.append("TBL0.tenpo_cd");
			sb.append(",");
			sb.append("TBL0.ymd_dt");			
				
		}		
		
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
		 mst540101_HachuNohinNgBean bean = ( mst540101_HachuNohinNgBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(" R_HACHUNOHIN_NG (");
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getYmdDt() != null && bean.getYmdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ymd_dt");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getHachuNgFg() != null && bean.getHachuNgFg().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hachu_ng_fg");
//		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getNohinNgFg() != null && bean.getNohinNgFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" nohin_ng_fg");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getInsertUserTs() != null && bean.getInsertUserTs().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_user_ts");
//		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" update_user_ts");
//		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getYmdDt() != null && bean.getYmdDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYmdDt() ) + "'");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getHachuNgFg() != null && bean.getHachuNgFg().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHachuNgFg() ) + "'");
//		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getNohinNgFg() != null && bean.getNohinNgFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNohinNgFg() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			//sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
			sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getInsertUserTs() != null && bean.getInsertUserTs().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getInsertUserTs() ) + "'");
//		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
//		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
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
		 mst540101_HachuNohinNgBean bean = ( mst540101_HachuNohinNgBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(" R_HACHUNOHIN_NG set ");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getHachuNgFg() != null && bean.getHachuNgFg().trim().length() != 0 )
//		{
//			befKanma = true;
//			sb.append(" hachu_ng_fg = ");
//			sb.append("'" + conv.convertString( bean.getHachuNgFg() ) + "'");
//		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getNohinNgFg() != null && bean.getNohinNgFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" nohin_ng_fg = ");
			sb.append("'" + conv.convertString( bean.getNohinNgFg() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getInsertUserTs() != null && bean.getInsertUserTs().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_user_ts = ");
//			sb.append("'" + conv.convertString( bean.getInsertUserTs() ) + "'");
//		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			//sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
			sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" update_user_ts = ");
//			sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
//		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getYmdDt() != null && bean.getYmdDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" ymd_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getYmdDt() ) + "'");
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
		 mst540101_HachuNohinNgBean bean = ( mst540101_HachuNohinNgBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(" R_HACHUNOHIN_NG where ");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append(" AND");
		sb.append(" ymd_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYmdDt() ) + "'");
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
