/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングNOマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングNOマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/11)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.*;
import mdware.common.util.StringUtility;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングNOマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングNOマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/11)初版作成
 * @version 1.1(2006/10/09)障害票№0128対応 更新処理成功時に、更新年月日の値をBeanにセットする(古い更新年月日が残ったままだと、再更新時の更新年月日チェック処理でひっかかるため) K.Tanigawa
 */
public class mst580101_TenGroupNoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst580101_TenGroupNoDM()
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
		mst580101_TenGroupNoBean bean = new mst580101_TenGroupNoBean();
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		bean.setLGyosyuCd( rest.getString("l_gyosyu_cd") );
//		bean.setYotoKb( rest.getString("yoto_kb") );
		bean.setBumonCd(rest.getString("bumon_cd"));
		bean.setGroupnoCd( rest.getString("groupno_cd") );
		bean.setInsertRn(rest.getString("insert_user_rn"));
		bean.setUpdateRn(rest.getString("update_user_rn"));
//		bean.setGroupnoCd( rest.getLong("groupno_cd") );
//      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		bean.setNameNa( rest.getString("name_na") );
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
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		sb.append("l_gyosyu_cd ");
//		sb.append(", ");
//		sb.append("yoto_kb ");
//		sb.append(", ");
		sb.append("bumon_cd \n");
		sb.append("	, \n");
		sb.append("groupno_cd ");
		sb.append(", ");
		sb.append("name_na ");
		sb.append(", ");
//		sb.append("insert_ts ");
		sb.append("RTP.insert_ts ");
		sb.append(", ");
		sb.append("RTP.insert_user_id ");
		sb.append("	, ");
		sb.append("user1.riyo_user_na   insert_user_rn ");
		sb.append(", ");
		sb.append("RTP.update_ts ");
		sb.append(", ");
		sb.append("RTP.update_user_id ");
		sb.append(", ");
		sb.append("user2.riyo_user_na   update_user_rn ");
		sb.append(", ");
		sb.append("delete_fg ");
//		sb.append("from r_tengroupno ");
		sb.append("from r_tengroupno RTP ");
//		sb.append(" left outer join r_riyo_user  user1 on user1.riyo_user_id =  RTP.insert_user_id ");
//		sb.append(" left outer join r_riyo_user  user2 on user2.riyo_user_id =  RTP.update_user_id ");	
		sb.append(" left outer join r_portal_user  user1 on user1.user_id =  TRIM(RTP.insert_user_id) ");
		sb.append(" left outer join r_portal_user  user2 on user2.user_id =  TRIM(RTP.update_user_id) ");	
			//bumon_cdに対する
		if(map.get("bumon_cd")!=null&&((String)map.get("bumon_cd")).trim().length() > 0)
		{
			sb.append(whereStr);
			sb.append("bumon_cd='"+StringUtility.charFormat(conv.convertWhereString( (String)map.get("bumon_cd") ),4,"0",true) + "'");
			whereStr = andStr;
		}
		
			sb.append(whereStr);
			sb.append("yoto_kb = '"+ mst003901_YotoKbDictionary.HACHU.getCode()+"'");
			whereStr = andStr;
//      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
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


		// yoto_kb に対するWHERE区
//		if( map.get("yoto_kb_bef") != null && ((String)map.get("yoto_kb_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("yoto_kb >= '" + conv.convertWhereString( (String)map.get("yoto_kb_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("yoto_kb_aft") != null && ((String)map.get("yoto_kb_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("yoto_kb <= '" + conv.convertWhereString( (String)map.get("yoto_kb_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("yoto_kb") != null && ((String)map.get("yoto_kb")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("yoto_kb = '" + conv.convertWhereString( (String)map.get("yoto_kb") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("yoto_kb_like") != null && ((String)map.get("yoto_kb_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("yoto_kb like '%" + conv.convertWhereString( (String)map.get("yoto_kb_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("yoto_kb_bef_like") != null && ((String)map.get("yoto_kb_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("yoto_kb like '%" + conv.convertWhereString( (String)map.get("yoto_kb_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("yoto_kb_aft_like") != null && ((String)map.get("yoto_kb_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("yoto_kb like '" + conv.convertWhereString( (String)map.get("yoto_kb_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("yoto_kb_in") != null && ((String)map.get("yoto_kb_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("yoto_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yoto_kb_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("yoto_kb_not_in") != null && ((String)map.get("yoto_kb_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("yoto_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yoto_kb_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 

		// groupno_cd に対するWHERE区
		if( map.get("groupno_cd_bef") != null && ((String)map.get("groupno_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd >= " + (String)map.get("groupno_cd_bef") );
			whereStr = andStr;
		}
		if( map.get("groupno_cd_aft") != null && ((String)map.get("groupno_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd <= " + (String)map.get("groupno_cd_aft") );
			whereStr = andStr;
		}
		if( map.get("groupno_cd") != null && ((String)map.get("groupno_cd")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("groupno_cd = " + (String)map.get("groupno_cd"));
			whereStr = andStr;
		}
		if( map.get("groupno_cd_in") != null && ((String)map.get("groupno_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd in ( " + conv.convertWhereString( (String)map.get("groupno_cd_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("groupno_cd_not_in") != null && ((String)map.get("groupno_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("groupno_cd not in ( " + conv.convertWhereString( (String)map.get("groupno_cd_not_in") ) + " )");
			whereStr = andStr;
		}


		// name_na に対するWHERE区
		if( map.get("name_na_bef") != null && ((String)map.get("name_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na >= '" + conv.convertWhereString( (String)map.get("name_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("name_na_aft") != null && ((String)map.get("name_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na <= '" + conv.convertWhereString( (String)map.get("name_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("name_na") != null && ((String)map.get("name_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na = '" + conv.convertWhereString( (String)map.get("name_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("name_na_like") != null && ((String)map.get("name_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na like '%" + conv.convertWhereString( (String)map.get("name_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("name_na_bef_like") != null && ((String)map.get("name_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na like '%" + conv.convertWhereString( (String)map.get("name_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("name_na_aft_like") != null && ((String)map.get("name_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na like '" + conv.convertWhereString( (String)map.get("name_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("name_na_in") != null && ((String)map.get("name_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("name_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("name_na_not_in") != null && ((String)map.get("name_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("name_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("name_na_not_in") ),",","','") + "' )");
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
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		sb.append("l_gyosyu_cd");
//		sb.append(",");
//		sb.append("yoto_kb");
//		sb.append(",");
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		sb.append("groupno_cd");
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
		mst580101_TenGroupNoBean bean = (mst580101_TenGroupNoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_tengroupno (");
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
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
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" yoto_kb");

		if( befKanma ) sb.append(",");
		sb.append(" groupno_cd");
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" name_na");
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
//      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append(" update_ts");
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append(" update_user_id");
//		}
		sb.append(",");
		sb.append(" update_ts");
		sb.append(",");
		sb.append(" update_user_id");
//      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg");
		}


		sb.append(")Values(");

//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		if( bean.getLGyosyuCd() != null && bean.getLGyosyuCd().trim().length() != 0 )
//		{
//			aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getLGyosyuCd() ) + "'");
//		}
//		if( bean.getYotoKb() != null && bean.getYotoKb().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getYotoKb() ) + "'");
//		}
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
		if(bean.getBumonCd()!=null&&(bean.getBumonCd().trim().length()!=0))
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append("'"+ mst000901_KanriKbDictionary.BUMON.getCode()+"'");
		if( aftKanma ) sb.append(",");
		sb.append( bean.getGroupnoCdString());
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getNameNa() ) + "'");
		}
/*
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
*/
		sb.append(",");
//      ↓↓移植（2006.05.11）↓↓ 
        try {		
		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}
//      ↑↑移植（2006.05.11）↑↑ 	
//      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}

//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
//			sb.append(",");
////          ↓↓移植（2006.05.11）↓↓ 
//            try {		
//    		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
//            } catch (SQLException e) {
//    			e.printStackTrace();
//    		}
////          ↑↑移植（2006.05.11）↑↑ 	
//		}
//
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//		}
		sb.append(",");
        try {		
		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}
        sb.append(",");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
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
		mst580101_TenGroupNoBean bean = (mst580101_TenGroupNoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_tengroupno set ");
		if( bean.getNameNa() != null && bean.getNameNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" name_na = ");
			sb.append("'" + conv.convertString( bean.getNameNa() ) + "'");
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
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTsForUpdate() ) + "'");
////          ↓↓移植（2006.05.11）↓↓ 
//            try {		
//        		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
////            	sb.append("'"+bean.getUpdateTs()+"'");
//            } catch (SQLException e) {
//    			e.printStackTrace();
//    		}
////          ↑↑移植（2006.05.11）↑↑ 	
//		}		
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

//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		if( bean.getLGyosyuCd() != null && bean.getLGyosyuCd().trim().length() != 0 )
//		{
//			whereAnd = true;
//			sb.append(" l_gyosyu_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getLGyosyuCd() ) + "'");
//		}
//		if( bean.getYotoKb() != null && bean.getYotoKb().trim().length() != 0 )
//		{
//			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
//			sb.append(" yoto_kb = ");
//			sb.append("'" + conv.convertWhereString( bean.getYotoKb() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" bumon_cd = ");
			sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		}
		if( whereAnd ) sb.append(" AND "); else whereAnd = true;
		sb.append(" yoto_kb = ");
		sb.append("'" + mst003901_YotoKbDictionary.HACHU.getCode() + "'");
//		if(bean.getYotoKb() != null && bean.getYotoKb().trim().length() != 0 )
//		{
//			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
//			sb.append("yoto_kb = ");
//			sb.append("'"+ mst000901_KanriKbDictionary.BUMON .getCode()+"'");
//		}
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		if( whereAnd ) sb.append(" AND ");
		sb.append(" groupno_cd = ");
		sb.append( bean.getGroupnoCdString());
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
		mst580101_TenGroupNoBean bean = (mst580101_TenGroupNoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_tengroupno where ");
//             ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓		
//		sb.append(" l_gyosyu_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getLGyosyuCd() ) + "'");
//		sb.append(" yoto_kb = ");
//		sb.append("'" + conv.convertWhereString( bean.getYotoKb() ) + "'");
		sb.append("bumon_cd=");
		sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		sb.append(" AND");
		sb.append(" yoto_kb = ");
		sb.append("'" + mst003901_YotoKbDictionary.HACHU.getCode()+ "'");
//             ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑ 
		sb.append(" AND");
		sb.append(" groupno_cd = ");
		sb.append( bean.getGroupnoCdString());
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
