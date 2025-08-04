package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

//↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
import mdware.master.util.RMSTDATEUtil;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
//↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.MDWARE
 * @version 1.X (Create time: 2006/10/16 9:21:17) K.Tanigawa
 */
public class mst990101_RTenGroupDM extends DataModule
{
/* DM 生成時に使用した SQL 文です。
SELECT 
     T1.BUMON_CD
    ,T1.YOTO_KB
    ,T1.GROUPNO_CD
    ,T1.RANK_NB
    ,T1.TENPO_CD
    ,T2.KANJI_NA
    ,T2.KANA_NA
    ,T2.KANJI_RN
    ,T2.KANA_RN
    ,T2.TENPO_TYPE_KB
    ,T2.TENPO_KB
    ,T2.KAITEN_DT
    ,T2.HEITEN_DT
    ,T2.ADDRESS_KANJI_NA
    ,T2.ADDRESS_KANA_NA
    ,T2.ADDRESS_3_NA
    ,T2.YUBIN_CD
    ,T2.TEL_CD
    ,T2.FAX_CD
    ,T2.HACHU_ST_DT
    ,T2.HACHU_ED_DT
    ,T2.GYOTAI_KB
    ,T2.INSERT_TS
    ,T2.INSERT_USER_ID
    ,T2.UPDATE_TS
    ,T2.UPDATE_USER_ID
    ,T2.DELETE_FG
FROM 
     R_TENGROUP T1,
     R_TENPO    T2
WHERE 
     T1.BUMON_CD = '0241' 
 AND T1.YOTO_KB  = '1'
 AND T1.GROUPNO_CD = '01' 
 AND T1.DELETE_FG= '0'
 AND T1.TENPO_CD = T2.TENPO_CD
ORDER BY 
     T1.TENPO_CD
*/
	/**
	 * コンストラクタ
	 */
	public mst990101_RTenGroupDM()
	{
		super( "rbsite_ora");
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
		mst990101_RTenGroupBean bean = new mst990101_RTenGroupBean();
		bean.setBumonCd( rest.getString("bumon_cd") );
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		bean.setYotoKb( rest.getString("yoto_kb") );
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		bean.setGroupnoCd( rest.getString("groupno_cd") );
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		bean.setRankNb( rest.getLong("rank_nb") );
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		bean.setTenpoCd( rest.getString("tenpo_cd") );
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		bean.setKanjiNa( rest.getString("kanji_na") );
//		bean.setKanaNa( rest.getString("kana_na") );
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		bean.setKanjiRn( rest.getString("kanji_rn") );
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		bean.setKanaRn( rest.getString("kana_rn") );
//		bean.setTenpoTypeKb( rest.getString("tenpo_type_kb") );
//		bean.setTenpoKb( rest.getString("tenpo_kb") );
//		bean.setKaitenDt( rest.getString("kaiten_dt") );
//		bean.setHeitenDt( rest.getString("heiten_dt") );
//		bean.setAddressKanjiNa( rest.getString("address_kanji_na") );
//		bean.setAddressKanaNa( rest.getString("address_kana_na") );
//		bean.setAddress3Na( rest.getString("address_3_na") );
//		bean.setYubinCd( rest.getString("yubin_cd") );
//		bean.setTelCd( rest.getString("tel_cd") );
//		bean.setFaxCd( rest.getString("fax_cd") );
//		bean.setHachuStDt( rest.getString("hachu_st_dt") );
//		bean.setHachuEdDt( rest.getString("hachu_ed_dt") );
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		bean.setGyotaiKb( rest.getString("gyotai_kb") );
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		bean.setInsertTs( rest.getString("insert_ts") );
//		bean.setInsertUserId( rest.getString("insert_user_id") );
//		bean.setUpdateTs( rest.getString("update_ts") );
//		bean.setUpdateUserId( rest.getString("update_user_id") );
//		bean.setDeleteFg( rest.getString("delete_fg") );
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
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
		
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	T1.BUMON_CD ");
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("	, ");
//		sb.append("	T1.YOTO_KB ");
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	, ");
		sb.append("	T1.GROUPNO_CD ");
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("	, ");
//		sb.append("	T1.RANK_NB ");
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	, ");
		sb.append("	T1.TENPO_CD ");
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("	, ");
//		sb.append("	T2.KANJI_NA ");
//		sb.append("	, ");
//		sb.append("	T2.KANA_NA ");
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	, ");
		sb.append("	T2.KANJI_RN ");
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("	, ");
//		sb.append("	T2.KANA_RN ");
//		sb.append("	, ");
//		sb.append("	T2.TENPO_TYPE_KB ");
//		sb.append("	, ");
//		sb.append("	T2.TENPO_KB ");
//		sb.append("	, ");
//		sb.append("	T2.KAITEN_DT ");
//		sb.append("	, ");
//		sb.append("	T2.HEITEN_DT ");
//		sb.append("	, ");
//		sb.append("	T2.ADDRESS_KANJI_NA ");
//		sb.append("	, ");
//		sb.append("	T2.ADDRESS_KANA_NA ");
//		sb.append("	, ");
//		sb.append("	T2.ADDRESS_3_NA ");
//		sb.append("	, ");
//		sb.append("	T2.YUBIN_CD ");
//		sb.append("	, ");
//		sb.append("	T2.TEL_CD ");
//		sb.append("	, ");
//		sb.append("	T2.FAX_CD ");
//		sb.append("	, ");
//		sb.append("	T2.HACHU_ST_DT ");
//		sb.append("	, ");
//		sb.append("	T2.HACHU_ED_DT ");
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	, ");
		sb.append("	T2.GYOTAI_KB ");
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("	, ");
//		sb.append("	T2.INSERT_TS ");
//		sb.append("	, ");
//		sb.append("	T2.INSERT_USER_ID ");
//		sb.append("	, ");
//		sb.append("	T2.UPDATE_TS ");
//		sb.append("	, ");
//		sb.append("	T2.UPDATE_USER_ID ");
//		sb.append("	, ");
//		sb.append("	T2.DELETE_FG ");
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append("FROM ");
		sb.append("	R_TENGROUP T1 ");
		sb.append("	, ");
		sb.append("	R_TENPO    T2 ");

		sb.append(" WHERE ");

		sb.append("     T1.BUMON_CD   = '" + conv.convertWhereString( (String)map.get("bumon_cd") ) + "' ");
		sb.append(" AND T1.YOTO_KB    = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
		sb.append(" AND T1.GROUPNO_CD = '" + conv.convertWhereString( (String)map.get("groupno_cd") ) + "' ");
		sb.append(" AND T1.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" AND T2.TENPO_KB  = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//		↓↓2007.01.22 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" AND NVL(T2.HEITEN_DT,'99991231') >= '" + RMSTDATEUtil.getMstDateDt() + "' ");
		sb.append(" AND T2.HEITEN_DT >= '" + RMSTDATEUtil.getMstDateDt() + "' ");
//		↑↑2007.01.22 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" AND T2.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append(" AND T1.TENPO_CD   = T2.TENPO_CD ");

		sb.append("ORDER BY ");
		sb.append("	T2.GYOTAI_KB ");
		sb.append(",T1.TENPO_CD ");
//		sb.append(" FOR READ ONLY ");
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑


		// ↓　ここに条件作成のサンプルを置きますので適切な場所に置いてください。

/*


		// bumon_cd に対するWHERE区
		if( map.get("bumon_cd_bef") != null && ((String)map.get("bumon_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("bumon_cd >= '" + conv.convertWhereString( (String)map.get("bumon_cd_bef") ) + "'");
		}
		if( map.get("bumon_cd_aft") != null && ((String)map.get("bumon_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("bumon_cd <= '" + conv.convertWhereString( (String)map.get("bumon_cd_aft") ) + "'");
		}
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("bumon_cd = '" + conv.convertWhereString( (String)map.get("bumon_cd") ) + "'");
		}
		if( map.get("bumon_cd_like") != null && ((String)map.get("bumon_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("bumon_cd like '%" + conv.convertWhereString( (String)map.get("bumon_cd_like") ) + "%'");
		}
		if( map.get("bumon_cd_bef_like") != null && ((String)map.get("bumon_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("bumon_cd like '%" + conv.convertWhereString( (String)map.get("bumon_cd_bef_like") ) + "'");
		}
		if( map.get("bumon_cd_aft_like") != null && ((String)map.get("bumon_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("bumon_cd like '" + conv.convertWhereString( (String)map.get("bumon_cd_aft_like") ) + "%'");
		}
		if( map.get("bumon_cd_in") != null && ((String)map.get("bumon_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("bumon_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bumon_cd_in") ),",","','") + "' )");
		}
		if( map.get("bumon_cd_not_in") != null && ((String)map.get("bumon_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("bumon_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bumon_cd_not_in") ),",","','") + "' )");
		}


		// yoto_kb に対するWHERE区
		if( map.get("yoto_kb_bef") != null && ((String)map.get("yoto_kb_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yoto_kb >= '" + conv.convertWhereString( (String)map.get("yoto_kb_bef") ) + "'");
		}
		if( map.get("yoto_kb_aft") != null && ((String)map.get("yoto_kb_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yoto_kb <= '" + conv.convertWhereString( (String)map.get("yoto_kb_aft") ) + "'");
		}
		if( map.get("yoto_kb") != null && ((String)map.get("yoto_kb")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yoto_kb = '" + conv.convertWhereString( (String)map.get("yoto_kb") ) + "'");
		}
		if( map.get("yoto_kb_like") != null && ((String)map.get("yoto_kb_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yoto_kb like '%" + conv.convertWhereString( (String)map.get("yoto_kb_like") ) + "%'");
		}
		if( map.get("yoto_kb_bef_like") != null && ((String)map.get("yoto_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yoto_kb like '%" + conv.convertWhereString( (String)map.get("yoto_kb_bef_like") ) + "'");
		}
		if( map.get("yoto_kb_aft_like") != null && ((String)map.get("yoto_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yoto_kb like '" + conv.convertWhereString( (String)map.get("yoto_kb_aft_like") ) + "%'");
		}
		if( map.get("yoto_kb_in") != null && ((String)map.get("yoto_kb_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yoto_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yoto_kb_in") ),",","','") + "' )");
		}
		if( map.get("yoto_kb_not_in") != null && ((String)map.get("yoto_kb_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yoto_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yoto_kb_not_in") ),",","','") + "' )");
		}


		// groupno_cd に対するWHERE区
		if( map.get("groupno_cd_bef") != null && ((String)map.get("groupno_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("groupno_cd >= '" + conv.convertWhereString( (String)map.get("groupno_cd_bef") ) + "'");
		}
		if( map.get("groupno_cd_aft") != null && ((String)map.get("groupno_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("groupno_cd <= '" + conv.convertWhereString( (String)map.get("groupno_cd_aft") ) + "'");
		}
		if( map.get("groupno_cd") != null && ((String)map.get("groupno_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("groupno_cd = '" + conv.convertWhereString( (String)map.get("groupno_cd") ) + "'");
		}
		if( map.get("groupno_cd_like") != null && ((String)map.get("groupno_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("groupno_cd like '%" + conv.convertWhereString( (String)map.get("groupno_cd_like") ) + "%'");
		}
		if( map.get("groupno_cd_bef_like") != null && ((String)map.get("groupno_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("groupno_cd like '%" + conv.convertWhereString( (String)map.get("groupno_cd_bef_like") ) + "'");
		}
		if( map.get("groupno_cd_aft_like") != null && ((String)map.get("groupno_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("groupno_cd like '" + conv.convertWhereString( (String)map.get("groupno_cd_aft_like") ) + "%'");
		}
		if( map.get("groupno_cd_in") != null && ((String)map.get("groupno_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("groupno_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("groupno_cd_in") ),",","','") + "' )");
		}
		if( map.get("groupno_cd_not_in") != null && ((String)map.get("groupno_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("groupno_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("groupno_cd_not_in") ),",","','") + "' )");
		}


		// rank_nb に対するWHERE区
		if( map.get("rank_nb_bef") != null && ((String)map.get("rank_nb_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("rank_nb >= " + (String)map.get("rank_nb_bef") );
		}
		if( map.get("rank_nb_aft") != null && ((String)map.get("rank_nb_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("rank_nb <= " + (String)map.get("rank_nb_aft") );
		}
		if( map.get("rank_nb") != null && ((String)map.get("rank_nb")).trim().length() > 0  )
		{
			sb.append(" AND ");
			sb.append("rank_nb = " + (String)map.get("rank_nb"));
		}
		if( map.get("rank_nb_in") != null && ((String)map.get("rank_nb_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("rank_nb in ( " + conv.convertWhereString( (String)map.get("rank_nb_in") ) + " )");
		}
		if( map.get("rank_nb_not_in") != null && ((String)map.get("rank_nb_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("rank_nb not in ( " + conv.convertWhereString( (String)map.get("rank_nb_not_in") ) + " )");
		}


		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd >= '" + conv.convertWhereString( (String)map.get("tenpo_cd_bef") ) + "'");
		}
		if( map.get("tenpo_cd_aft") != null && ((String)map.get("tenpo_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd <= '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft") ) + "'");
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
		}
		if( map.get("tenpo_cd_like") != null && ((String)map.get("tenpo_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_like") ) + "%'");
		}
		if( map.get("tenpo_cd_bef_like") != null && ((String)map.get("tenpo_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_bef_like") ) + "'");
		}
		if( map.get("tenpo_cd_aft_like") != null && ((String)map.get("tenpo_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd like '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft_like") ) + "%'");
		}
		if( map.get("tenpo_cd_in") != null && ((String)map.get("tenpo_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_in") ),",","','") + "' )");
		}
		if( map.get("tenpo_cd_not_in") != null && ((String)map.get("tenpo_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_not_in") ),",","','") + "' )");
		}


		// kanji_na に対するWHERE区
		if( map.get("kanji_na_bef") != null && ((String)map.get("kanji_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_na >= '" + conv.convertWhereString( (String)map.get("kanji_na_bef") ) + "'");
		}
		if( map.get("kanji_na_aft") != null && ((String)map.get("kanji_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_na <= '" + conv.convertWhereString( (String)map.get("kanji_na_aft") ) + "'");
		}
		if( map.get("kanji_na") != null && ((String)map.get("kanji_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_na = '" + conv.convertWhereString( (String)map.get("kanji_na") ) + "'");
		}
		if( map.get("kanji_na_like") != null && ((String)map.get("kanji_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_na like '%" + conv.convertWhereString( (String)map.get("kanji_na_like") ) + "%'");
		}
		if( map.get("kanji_na_bef_like") != null && ((String)map.get("kanji_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_na like '%" + conv.convertWhereString( (String)map.get("kanji_na_bef_like") ) + "'");
		}
		if( map.get("kanji_na_aft_like") != null && ((String)map.get("kanji_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_na like '" + conv.convertWhereString( (String)map.get("kanji_na_aft_like") ) + "%'");
		}
		if( map.get("kanji_na_in") != null && ((String)map.get("kanji_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_na_in") ),",","','") + "' )");
		}
		if( map.get("kanji_na_not_in") != null && ((String)map.get("kanji_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_na_not_in") ),",","','") + "' )");
		}


		// kana_na に対するWHERE区
		if( map.get("kana_na_bef") != null && ((String)map.get("kana_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_na >= '" + conv.convertWhereString( (String)map.get("kana_na_bef") ) + "'");
		}
		if( map.get("kana_na_aft") != null && ((String)map.get("kana_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_na <= '" + conv.convertWhereString( (String)map.get("kana_na_aft") ) + "'");
		}
		if( map.get("kana_na") != null && ((String)map.get("kana_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_na = '" + conv.convertWhereString( (String)map.get("kana_na") ) + "'");
		}
		if( map.get("kana_na_like") != null && ((String)map.get("kana_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_na like '%" + conv.convertWhereString( (String)map.get("kana_na_like") ) + "%'");
		}
		if( map.get("kana_na_bef_like") != null && ((String)map.get("kana_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_na like '%" + conv.convertWhereString( (String)map.get("kana_na_bef_like") ) + "'");
		}
		if( map.get("kana_na_aft_like") != null && ((String)map.get("kana_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_na like '" + conv.convertWhereString( (String)map.get("kana_na_aft_like") ) + "%'");
		}
		if( map.get("kana_na_in") != null && ((String)map.get("kana_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_na_in") ),",","','") + "' )");
		}
		if( map.get("kana_na_not_in") != null && ((String)map.get("kana_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_na_not_in") ),",","','") + "' )");
		}


		// kanji_rn に対するWHERE区
		if( map.get("kanji_rn_bef") != null && ((String)map.get("kanji_rn_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_rn >= '" + conv.convertWhereString( (String)map.get("kanji_rn_bef") ) + "'");
		}
		if( map.get("kanji_rn_aft") != null && ((String)map.get("kanji_rn_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_rn <= '" + conv.convertWhereString( (String)map.get("kanji_rn_aft") ) + "'");
		}
		if( map.get("kanji_rn") != null && ((String)map.get("kanji_rn")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_rn = '" + conv.convertWhereString( (String)map.get("kanji_rn") ) + "'");
		}
		if( map.get("kanji_rn_like") != null && ((String)map.get("kanji_rn_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_rn like '%" + conv.convertWhereString( (String)map.get("kanji_rn_like") ) + "%'");
		}
		if( map.get("kanji_rn_bef_like") != null && ((String)map.get("kanji_rn_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_rn like '%" + conv.convertWhereString( (String)map.get("kanji_rn_bef_like") ) + "'");
		}
		if( map.get("kanji_rn_aft_like") != null && ((String)map.get("kanji_rn_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_rn like '" + conv.convertWhereString( (String)map.get("kanji_rn_aft_like") ) + "%'");
		}
		if( map.get("kanji_rn_in") != null && ((String)map.get("kanji_rn_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_rn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_rn_in") ),",","','") + "' )");
		}
		if( map.get("kanji_rn_not_in") != null && ((String)map.get("kanji_rn_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanji_rn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_rn_not_in") ),",","','") + "' )");
		}


		// kana_rn に対するWHERE区
		if( map.get("kana_rn_bef") != null && ((String)map.get("kana_rn_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_rn >= '" + conv.convertWhereString( (String)map.get("kana_rn_bef") ) + "'");
		}
		if( map.get("kana_rn_aft") != null && ((String)map.get("kana_rn_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_rn <= '" + conv.convertWhereString( (String)map.get("kana_rn_aft") ) + "'");
		}
		if( map.get("kana_rn") != null && ((String)map.get("kana_rn")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_rn = '" + conv.convertWhereString( (String)map.get("kana_rn") ) + "'");
		}
		if( map.get("kana_rn_like") != null && ((String)map.get("kana_rn_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_rn like '%" + conv.convertWhereString( (String)map.get("kana_rn_like") ) + "%'");
		}
		if( map.get("kana_rn_bef_like") != null && ((String)map.get("kana_rn_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_rn like '%" + conv.convertWhereString( (String)map.get("kana_rn_bef_like") ) + "'");
		}
		if( map.get("kana_rn_aft_like") != null && ((String)map.get("kana_rn_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_rn like '" + conv.convertWhereString( (String)map.get("kana_rn_aft_like") ) + "%'");
		}
		if( map.get("kana_rn_in") != null && ((String)map.get("kana_rn_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_rn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_rn_in") ),",","','") + "' )");
		}
		if( map.get("kana_rn_not_in") != null && ((String)map.get("kana_rn_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kana_rn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_rn_not_in") ),",","','") + "' )");
		}


		// tenpo_type_kb に対するWHERE区
		if( map.get("tenpo_type_kb_bef") != null && ((String)map.get("tenpo_type_kb_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_type_kb >= '" + conv.convertWhereString( (String)map.get("tenpo_type_kb_bef") ) + "'");
		}
		if( map.get("tenpo_type_kb_aft") != null && ((String)map.get("tenpo_type_kb_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_type_kb <= '" + conv.convertWhereString( (String)map.get("tenpo_type_kb_aft") ) + "'");
		}
		if( map.get("tenpo_type_kb") != null && ((String)map.get("tenpo_type_kb")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_type_kb = '" + conv.convertWhereString( (String)map.get("tenpo_type_kb") ) + "'");
		}
		if( map.get("tenpo_type_kb_like") != null && ((String)map.get("tenpo_type_kb_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_type_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_type_kb_like") ) + "%'");
		}
		if( map.get("tenpo_type_kb_bef_like") != null && ((String)map.get("tenpo_type_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_type_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_type_kb_bef_like") ) + "'");
		}
		if( map.get("tenpo_type_kb_aft_like") != null && ((String)map.get("tenpo_type_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_type_kb like '" + conv.convertWhereString( (String)map.get("tenpo_type_kb_aft_like") ) + "%'");
		}
		if( map.get("tenpo_type_kb_in") != null && ((String)map.get("tenpo_type_kb_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_type_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_type_kb_in") ),",","','") + "' )");
		}
		if( map.get("tenpo_type_kb_not_in") != null && ((String)map.get("tenpo_type_kb_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_type_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_type_kb_not_in") ),",","','") + "' )");
		}


		// tenpo_kb に対するWHERE区
		if( map.get("tenpo_kb_bef") != null && ((String)map.get("tenpo_kb_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_kb >= '" + conv.convertWhereString( (String)map.get("tenpo_kb_bef") ) + "'");
		}
		if( map.get("tenpo_kb_aft") != null && ((String)map.get("tenpo_kb_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_kb <= '" + conv.convertWhereString( (String)map.get("tenpo_kb_aft") ) + "'");
		}
		if( map.get("tenpo_kb") != null && ((String)map.get("tenpo_kb")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_kb = '" + conv.convertWhereString( (String)map.get("tenpo_kb") ) + "'");
		}
		if( map.get("tenpo_kb_like") != null && ((String)map.get("tenpo_kb_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_kb_like") ) + "%'");
		}
		if( map.get("tenpo_kb_bef_like") != null && ((String)map.get("tenpo_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_kb_bef_like") ) + "'");
		}
		if( map.get("tenpo_kb_aft_like") != null && ((String)map.get("tenpo_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_kb like '" + conv.convertWhereString( (String)map.get("tenpo_kb_aft_like") ) + "%'");
		}
		if( map.get("tenpo_kb_in") != null && ((String)map.get("tenpo_kb_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_kb_in") ),",","','") + "' )");
		}
		if( map.get("tenpo_kb_not_in") != null && ((String)map.get("tenpo_kb_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_kb_not_in") ),",","','") + "' )");
		}


		// kaiten_dt に対するWHERE区
		if( map.get("kaiten_dt_bef") != null && ((String)map.get("kaiten_dt_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kaiten_dt >= '" + conv.convertWhereString( (String)map.get("kaiten_dt_bef") ) + "'");
		}
		if( map.get("kaiten_dt_aft") != null && ((String)map.get("kaiten_dt_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kaiten_dt <= '" + conv.convertWhereString( (String)map.get("kaiten_dt_aft") ) + "'");
		}
		if( map.get("kaiten_dt") != null && ((String)map.get("kaiten_dt")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kaiten_dt = '" + conv.convertWhereString( (String)map.get("kaiten_dt") ) + "'");
		}
		if( map.get("kaiten_dt_like") != null && ((String)map.get("kaiten_dt_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kaiten_dt like '%" + conv.convertWhereString( (String)map.get("kaiten_dt_like") ) + "%'");
		}
		if( map.get("kaiten_dt_bef_like") != null && ((String)map.get("kaiten_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kaiten_dt like '%" + conv.convertWhereString( (String)map.get("kaiten_dt_bef_like") ) + "'");
		}
		if( map.get("kaiten_dt_aft_like") != null && ((String)map.get("kaiten_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kaiten_dt like '" + conv.convertWhereString( (String)map.get("kaiten_dt_aft_like") ) + "%'");
		}
		if( map.get("kaiten_dt_in") != null && ((String)map.get("kaiten_dt_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kaiten_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kaiten_dt_in") ),",","','") + "' )");
		}
		if( map.get("kaiten_dt_not_in") != null && ((String)map.get("kaiten_dt_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kaiten_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kaiten_dt_not_in") ),",","','") + "' )");
		}


		// heiten_dt に対するWHERE区
		if( map.get("heiten_dt_bef") != null && ((String)map.get("heiten_dt_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("heiten_dt >= '" + conv.convertWhereString( (String)map.get("heiten_dt_bef") ) + "'");
		}
		if( map.get("heiten_dt_aft") != null && ((String)map.get("heiten_dt_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("heiten_dt <= '" + conv.convertWhereString( (String)map.get("heiten_dt_aft") ) + "'");
		}
		if( map.get("heiten_dt") != null && ((String)map.get("heiten_dt")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("heiten_dt = '" + conv.convertWhereString( (String)map.get("heiten_dt") ) + "'");
		}
		if( map.get("heiten_dt_like") != null && ((String)map.get("heiten_dt_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("heiten_dt like '%" + conv.convertWhereString( (String)map.get("heiten_dt_like") ) + "%'");
		}
		if( map.get("heiten_dt_bef_like") != null && ((String)map.get("heiten_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("heiten_dt like '%" + conv.convertWhereString( (String)map.get("heiten_dt_bef_like") ) + "'");
		}
		if( map.get("heiten_dt_aft_like") != null && ((String)map.get("heiten_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("heiten_dt like '" + conv.convertWhereString( (String)map.get("heiten_dt_aft_like") ) + "%'");
		}
		if( map.get("heiten_dt_in") != null && ((String)map.get("heiten_dt_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("heiten_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("heiten_dt_in") ),",","','") + "' )");
		}
		if( map.get("heiten_dt_not_in") != null && ((String)map.get("heiten_dt_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("heiten_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("heiten_dt_not_in") ),",","','") + "' )");
		}


		// address_kanji_na に対するWHERE区
		if( map.get("address_kanji_na_bef") != null && ((String)map.get("address_kanji_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kanji_na >= '" + conv.convertWhereString( (String)map.get("address_kanji_na_bef") ) + "'");
		}
		if( map.get("address_kanji_na_aft") != null && ((String)map.get("address_kanji_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kanji_na <= '" + conv.convertWhereString( (String)map.get("address_kanji_na_aft") ) + "'");
		}
		if( map.get("address_kanji_na") != null && ((String)map.get("address_kanji_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kanji_na = '" + conv.convertWhereString( (String)map.get("address_kanji_na") ) + "'");
		}
		if( map.get("address_kanji_na_like") != null && ((String)map.get("address_kanji_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kanji_na like '%" + conv.convertWhereString( (String)map.get("address_kanji_na_like") ) + "%'");
		}
		if( map.get("address_kanji_na_bef_like") != null && ((String)map.get("address_kanji_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kanji_na like '%" + conv.convertWhereString( (String)map.get("address_kanji_na_bef_like") ) + "'");
		}
		if( map.get("address_kanji_na_aft_like") != null && ((String)map.get("address_kanji_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kanji_na like '" + conv.convertWhereString( (String)map.get("address_kanji_na_aft_like") ) + "%'");
		}
		if( map.get("address_kanji_na_in") != null && ((String)map.get("address_kanji_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kanji_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kanji_na_in") ),",","','") + "' )");
		}
		if( map.get("address_kanji_na_not_in") != null && ((String)map.get("address_kanji_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kanji_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kanji_na_not_in") ),",","','") + "' )");
		}


		// address_kana_na に対するWHERE区
		if( map.get("address_kana_na_bef") != null && ((String)map.get("address_kana_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kana_na >= '" + conv.convertWhereString( (String)map.get("address_kana_na_bef") ) + "'");
		}
		if( map.get("address_kana_na_aft") != null && ((String)map.get("address_kana_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kana_na <= '" + conv.convertWhereString( (String)map.get("address_kana_na_aft") ) + "'");
		}
		if( map.get("address_kana_na") != null && ((String)map.get("address_kana_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kana_na = '" + conv.convertWhereString( (String)map.get("address_kana_na") ) + "'");
		}
		if( map.get("address_kana_na_like") != null && ((String)map.get("address_kana_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kana_na like '%" + conv.convertWhereString( (String)map.get("address_kana_na_like") ) + "%'");
		}
		if( map.get("address_kana_na_bef_like") != null && ((String)map.get("address_kana_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kana_na like '%" + conv.convertWhereString( (String)map.get("address_kana_na_bef_like") ) + "'");
		}
		if( map.get("address_kana_na_aft_like") != null && ((String)map.get("address_kana_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kana_na like '" + conv.convertWhereString( (String)map.get("address_kana_na_aft_like") ) + "%'");
		}
		if( map.get("address_kana_na_in") != null && ((String)map.get("address_kana_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kana_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kana_na_in") ),",","','") + "' )");
		}
		if( map.get("address_kana_na_not_in") != null && ((String)map.get("address_kana_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_kana_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kana_na_not_in") ),",","','") + "' )");
		}


		// address_3_na に対するWHERE区
		if( map.get("address_3_na_bef") != null && ((String)map.get("address_3_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_3_na >= '" + conv.convertWhereString( (String)map.get("address_3_na_bef") ) + "'");
		}
		if( map.get("address_3_na_aft") != null && ((String)map.get("address_3_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_3_na <= '" + conv.convertWhereString( (String)map.get("address_3_na_aft") ) + "'");
		}
		if( map.get("address_3_na") != null && ((String)map.get("address_3_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_3_na = '" + conv.convertWhereString( (String)map.get("address_3_na") ) + "'");
		}
		if( map.get("address_3_na_like") != null && ((String)map.get("address_3_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_3_na like '%" + conv.convertWhereString( (String)map.get("address_3_na_like") ) + "%'");
		}
		if( map.get("address_3_na_bef_like") != null && ((String)map.get("address_3_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_3_na like '%" + conv.convertWhereString( (String)map.get("address_3_na_bef_like") ) + "'");
		}
		if( map.get("address_3_na_aft_like") != null && ((String)map.get("address_3_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_3_na like '" + conv.convertWhereString( (String)map.get("address_3_na_aft_like") ) + "%'");
		}
		if( map.get("address_3_na_in") != null && ((String)map.get("address_3_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_3_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_3_na_in") ),",","','") + "' )");
		}
		if( map.get("address_3_na_not_in") != null && ((String)map.get("address_3_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("address_3_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_3_na_not_in") ),",","','") + "' )");
		}


		// yubin_cd に対するWHERE区
		if( map.get("yubin_cd_bef") != null && ((String)map.get("yubin_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yubin_cd >= '" + conv.convertWhereString( (String)map.get("yubin_cd_bef") ) + "'");
		}
		if( map.get("yubin_cd_aft") != null && ((String)map.get("yubin_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yubin_cd <= '" + conv.convertWhereString( (String)map.get("yubin_cd_aft") ) + "'");
		}
		if( map.get("yubin_cd") != null && ((String)map.get("yubin_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yubin_cd = '" + conv.convertWhereString( (String)map.get("yubin_cd") ) + "'");
		}
		if( map.get("yubin_cd_like") != null && ((String)map.get("yubin_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yubin_cd like '%" + conv.convertWhereString( (String)map.get("yubin_cd_like") ) + "%'");
		}
		if( map.get("yubin_cd_bef_like") != null && ((String)map.get("yubin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yubin_cd like '%" + conv.convertWhereString( (String)map.get("yubin_cd_bef_like") ) + "'");
		}
		if( map.get("yubin_cd_aft_like") != null && ((String)map.get("yubin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yubin_cd like '" + conv.convertWhereString( (String)map.get("yubin_cd_aft_like") ) + "%'");
		}
		if( map.get("yubin_cd_in") != null && ((String)map.get("yubin_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yubin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yubin_cd_in") ),",","','") + "' )");
		}
		if( map.get("yubin_cd_not_in") != null && ((String)map.get("yubin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yubin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yubin_cd_not_in") ),",","','") + "' )");
		}


		// tel_cd に対するWHERE区
		if( map.get("tel_cd_bef") != null && ((String)map.get("tel_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tel_cd >= '" + conv.convertWhereString( (String)map.get("tel_cd_bef") ) + "'");
		}
		if( map.get("tel_cd_aft") != null && ((String)map.get("tel_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tel_cd <= '" + conv.convertWhereString( (String)map.get("tel_cd_aft") ) + "'");
		}
		if( map.get("tel_cd") != null && ((String)map.get("tel_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tel_cd = '" + conv.convertWhereString( (String)map.get("tel_cd") ) + "'");
		}
		if( map.get("tel_cd_like") != null && ((String)map.get("tel_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tel_cd like '%" + conv.convertWhereString( (String)map.get("tel_cd_like") ) + "%'");
		}
		if( map.get("tel_cd_bef_like") != null && ((String)map.get("tel_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tel_cd like '%" + conv.convertWhereString( (String)map.get("tel_cd_bef_like") ) + "'");
		}
		if( map.get("tel_cd_aft_like") != null && ((String)map.get("tel_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tel_cd like '" + conv.convertWhereString( (String)map.get("tel_cd_aft_like") ) + "%'");
		}
		if( map.get("tel_cd_in") != null && ((String)map.get("tel_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tel_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tel_cd_in") ),",","','") + "' )");
		}
		if( map.get("tel_cd_not_in") != null && ((String)map.get("tel_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tel_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tel_cd_not_in") ),",","','") + "' )");
		}


		// fax_cd に対するWHERE区
		if( map.get("fax_cd_bef") != null && ((String)map.get("fax_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("fax_cd >= '" + conv.convertWhereString( (String)map.get("fax_cd_bef") ) + "'");
		}
		if( map.get("fax_cd_aft") != null && ((String)map.get("fax_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("fax_cd <= '" + conv.convertWhereString( (String)map.get("fax_cd_aft") ) + "'");
		}
		if( map.get("fax_cd") != null && ((String)map.get("fax_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("fax_cd = '" + conv.convertWhereString( (String)map.get("fax_cd") ) + "'");
		}
		if( map.get("fax_cd_like") != null && ((String)map.get("fax_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("fax_cd like '%" + conv.convertWhereString( (String)map.get("fax_cd_like") ) + "%'");
		}
		if( map.get("fax_cd_bef_like") != null && ((String)map.get("fax_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("fax_cd like '%" + conv.convertWhereString( (String)map.get("fax_cd_bef_like") ) + "'");
		}
		if( map.get("fax_cd_aft_like") != null && ((String)map.get("fax_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("fax_cd like '" + conv.convertWhereString( (String)map.get("fax_cd_aft_like") ) + "%'");
		}
		if( map.get("fax_cd_in") != null && ((String)map.get("fax_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("fax_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("fax_cd_in") ),",","','") + "' )");
		}
		if( map.get("fax_cd_not_in") != null && ((String)map.get("fax_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("fax_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("fax_cd_not_in") ),",","','") + "' )");
		}


		// hachu_st_dt に対するWHERE区
		if( map.get("hachu_st_dt_bef") != null && ((String)map.get("hachu_st_dt_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_st_dt >= '" + conv.convertWhereString( (String)map.get("hachu_st_dt_bef") ) + "'");
		}
		if( map.get("hachu_st_dt_aft") != null && ((String)map.get("hachu_st_dt_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_st_dt <= '" + conv.convertWhereString( (String)map.get("hachu_st_dt_aft") ) + "'");
		}
		if( map.get("hachu_st_dt") != null && ((String)map.get("hachu_st_dt")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_st_dt = '" + conv.convertWhereString( (String)map.get("hachu_st_dt") ) + "'");
		}
		if( map.get("hachu_st_dt_like") != null && ((String)map.get("hachu_st_dt_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_st_dt like '%" + conv.convertWhereString( (String)map.get("hachu_st_dt_like") ) + "%'");
		}
		if( map.get("hachu_st_dt_bef_like") != null && ((String)map.get("hachu_st_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_st_dt like '%" + conv.convertWhereString( (String)map.get("hachu_st_dt_bef_like") ) + "'");
		}
		if( map.get("hachu_st_dt_aft_like") != null && ((String)map.get("hachu_st_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_st_dt like '" + conv.convertWhereString( (String)map.get("hachu_st_dt_aft_like") ) + "%'");
		}
		if( map.get("hachu_st_dt_in") != null && ((String)map.get("hachu_st_dt_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_st_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_st_dt_in") ),",","','") + "' )");
		}
		if( map.get("hachu_st_dt_not_in") != null && ((String)map.get("hachu_st_dt_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_st_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_st_dt_not_in") ),",","','") + "' )");
		}


		// hachu_ed_dt に対するWHERE区
		if( map.get("hachu_ed_dt_bef") != null && ((String)map.get("hachu_ed_dt_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_ed_dt >= '" + conv.convertWhereString( (String)map.get("hachu_ed_dt_bef") ) + "'");
		}
		if( map.get("hachu_ed_dt_aft") != null && ((String)map.get("hachu_ed_dt_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_ed_dt <= '" + conv.convertWhereString( (String)map.get("hachu_ed_dt_aft") ) + "'");
		}
		if( map.get("hachu_ed_dt") != null && ((String)map.get("hachu_ed_dt")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_ed_dt = '" + conv.convertWhereString( (String)map.get("hachu_ed_dt") ) + "'");
		}
		if( map.get("hachu_ed_dt_like") != null && ((String)map.get("hachu_ed_dt_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_ed_dt like '%" + conv.convertWhereString( (String)map.get("hachu_ed_dt_like") ) + "%'");
		}
		if( map.get("hachu_ed_dt_bef_like") != null && ((String)map.get("hachu_ed_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_ed_dt like '%" + conv.convertWhereString( (String)map.get("hachu_ed_dt_bef_like") ) + "'");
		}
		if( map.get("hachu_ed_dt_aft_like") != null && ((String)map.get("hachu_ed_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_ed_dt like '" + conv.convertWhereString( (String)map.get("hachu_ed_dt_aft_like") ) + "%'");
		}
		if( map.get("hachu_ed_dt_in") != null && ((String)map.get("hachu_ed_dt_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_ed_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_ed_dt_in") ),",","','") + "' )");
		}
		if( map.get("hachu_ed_dt_not_in") != null && ((String)map.get("hachu_ed_dt_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hachu_ed_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_ed_dt_not_in") ),",","','") + "' )");
		}


		// gyotai_kb に対するWHERE区
		if( map.get("gyotai_kb_bef") != null && ((String)map.get("gyotai_kb_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("gyotai_kb >= '" + conv.convertWhereString( (String)map.get("gyotai_kb_bef") ) + "'");
		}
		if( map.get("gyotai_kb_aft") != null && ((String)map.get("gyotai_kb_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("gyotai_kb <= '" + conv.convertWhereString( (String)map.get("gyotai_kb_aft") ) + "'");
		}
		if( map.get("gyotai_kb") != null && ((String)map.get("gyotai_kb")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("gyotai_kb = '" + conv.convertWhereString( (String)map.get("gyotai_kb") ) + "'");
		}
		if( map.get("gyotai_kb_like") != null && ((String)map.get("gyotai_kb_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("gyotai_kb like '%" + conv.convertWhereString( (String)map.get("gyotai_kb_like") ) + "%'");
		}
		if( map.get("gyotai_kb_bef_like") != null && ((String)map.get("gyotai_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("gyotai_kb like '%" + conv.convertWhereString( (String)map.get("gyotai_kb_bef_like") ) + "'");
		}
		if( map.get("gyotai_kb_aft_like") != null && ((String)map.get("gyotai_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("gyotai_kb like '" + conv.convertWhereString( (String)map.get("gyotai_kb_aft_like") ) + "%'");
		}
		if( map.get("gyotai_kb_in") != null && ((String)map.get("gyotai_kb_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("gyotai_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gyotai_kb_in") ),",","','") + "' )");
		}
		if( map.get("gyotai_kb_not_in") != null && ((String)map.get("gyotai_kb_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("gyotai_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gyotai_kb_not_in") ),",","','") + "' )");
		}


		// insert_ts に対するWHERE区
		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
		}
		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
		}
		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
		}
		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
		}
		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
		}
		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
		}
		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
		}


		// insert_user_id に対するWHERE区
		if( map.get("insert_user_id_bef") != null && ((String)map.get("insert_user_id_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_id >= '" + conv.convertWhereString( (String)map.get("insert_user_id_bef") ) + "'");
		}
		if( map.get("insert_user_id_aft") != null && ((String)map.get("insert_user_id_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_id <= '" + conv.convertWhereString( (String)map.get("insert_user_id_aft") ) + "'");
		}
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
		}
		if( map.get("insert_user_id_like") != null && ((String)map.get("insert_user_id_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_like") ) + "%'");
		}
		if( map.get("insert_user_id_bef_like") != null && ((String)map.get("insert_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_bef_like") ) + "'");
		}
		if( map.get("insert_user_id_aft_like") != null && ((String)map.get("insert_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_id like '" + conv.convertWhereString( (String)map.get("insert_user_id_aft_like") ) + "%'");
		}
		if( map.get("insert_user_id_in") != null && ((String)map.get("insert_user_id_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_in") ),",","','") + "' )");
		}
		if( map.get("insert_user_id_not_in") != null && ((String)map.get("insert_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_not_in") ),",","','") + "' )");
		}


		// update_ts に対するWHERE区
		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
		}
		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
		}
		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
		}
		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
		}
		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
		}
		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
		}
		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
		}


		// update_user_id に対するWHERE区
		if( map.get("update_user_id_bef") != null && ((String)map.get("update_user_id_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
		}
		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
		}
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
		}
		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
		}
		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
		}
		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
		}
		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
		}
		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_not_in") ),",","','") + "' )");
		}


		// delete_fg に対するWHERE区
		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
		}
*/

		// ↑　ここに条件作成のサンプルを置きますので適切な場所に置いてください。


		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		return null;
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
