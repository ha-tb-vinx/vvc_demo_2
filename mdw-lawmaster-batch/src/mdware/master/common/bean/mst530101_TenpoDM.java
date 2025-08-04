/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/04)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.common.util.StringUtility;
//↓↓2006.12.01 H.Yamamoto カスタマイズ修正↓↓
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
//↑↑2006.12.01 H.Yamamoto カスタマイズ修正↑↑

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/04)初版作成
 */
public class mst530101_TenpoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst530101_TenpoDM()
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
		mst530101_TenpoBean bean = new mst530101_TenpoBean();
		bean.setTenpoCd( rest.getString("tenpo_cd") );
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
		bean.setHojinCd( rest.getString("hojin_cd") );
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）		
		bean.setTenpokaisoTiikiCd( rest.getString("tenpokaiso_tiiki_cd") );
		bean.setTenpokaisoAreaCd( rest.getString("tenpokaiso_area_cd") );
		bean.setTenpokaisoBlockCd( rest.getString("tenpokaiso_block_cd") );
		bean.setKanjiNa( rest.getString("kanji_na") );
		bean.setKanaNa( rest.getString("kana_na") );
		bean.setKanjiRn( rest.getString("kanji_rn") );
		bean.setKanaRn( rest.getString("kana_rn") );
		bean.setTenpoTypeKb( rest.getString("tenpo_type_kb") );
		bean.setTenpoKb( rest.getString("tenpo_kb") );
		bean.setKaitenDt( rest.getString("kaiten_dt") );
		bean.setHeitenDt( rest.getString("heiten_dt") );
		bean.setAddressKanjiNa( rest.getString("address_kanji_na") );
		bean.setAddressKanaNa( rest.getString("address_kana_na") );
		bean.setAddress3Na( rest.getString("address_3_na") );
		bean.setYubinCd( rest.getString("yubin_cd") );
		bean.setTelCd( rest.getString("tel_cd") );
		bean.setFaxCd( rest.getString("fax_cd") );
//		↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
		bean.setPosKb( rest.getString("pos_kb") );
		bean.setBotenCd( rest.getString("boten_cd") );
		bean.setGyotaiKb( rest.getString("gyotai_kb") );
//	    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑
		bean.setHachuStDt( rest.getString("hachu_st_dt") );
		bean.setHachuEdDt( rest.getString("hachu_ed_dt") );
		bean.setSndstNehudaDt( rest.getString("sndst_nehuda_dt") );
		bean.setSndstPriceDt( rest.getString("sndst_price_dt") );
		bean.setSndstTagDt( rest.getString("sndst_tag_dt") );
		bean.setSndstPluDt( rest.getString("sndst_plu_dt") );
		bean.setSndstPopDt( rest.getString("sndst_pop_dt") );
		bean.setSndstKeiryokiDt( rest.getString("sndst_keiryoki_dt") );
//		bean.setHanku1KeiryokiType( rest.getString("hanku1_keiryoki_type") );
//		bean.setHanku2KeiryokiType( rest.getString("hanku2_keiryoki_type") );
//		bean.setHanku3KeiryokiType( rest.getString("hanku3_keiryoki_type") );
//		bean.setHanku4KeiryokiType( rest.getString("hanku4_keiryoki_type") );
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
		sb.append("tenpo_cd ");
		sb.append(", ");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
		sb.append("hojin_cd ");
		sb.append(", ");
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）		
		sb.append("tenpokaiso_tiiki_cd ");
		sb.append(", ");
		sb.append("tenpokaiso_area_cd ");
		sb.append(", ");
		sb.append("tenpokaiso_block_cd ");
		sb.append(", ");
		sb.append("trim(kanji_na) kanji_na ");
		sb.append(", ");
		sb.append("trim(kana_na) kana_na ");
		sb.append(", ");
		sb.append("trim(kanji_rn) kanji_rn ");
		sb.append(", ");
		sb.append("trim(kana_rn) kana_rn ");
		sb.append(", ");
		sb.append("tenpo_type_kb ");
		sb.append(", ");
		sb.append("tenpo_kb ");
		sb.append(", ");
		sb.append("kaiten_dt ");
		sb.append(", ");
		sb.append("heiten_dt ");
		sb.append(", ");
		sb.append("trim(address_kanji_na) address_kanji_na ");
		sb.append(", ");
		sb.append("trim(address_kana_na) address_kana_na ");
		sb.append(", ");
		sb.append("trim(address_3_na) address_3_na ");
		sb.append(", ");
		// 2006/02/09 kim START
//      ↓↓移植（2006.05.26）↓↓
        sb.append(" NVL2(yubin_cd ,concat(concat(substr(yubin_cd, 1, 3), '-'), substr(yubin_cd, 4, 4)), yubin_cd) as yubin_cd ");
//      ↑↑移植（2006.05.26）↑↑
		//sb.append("concat(concat(substr(yubin_cd, 0, 3), '-'), substr(yubin_cd, 4, 7))  as yubin_cd ");
		// 2006/02/09 kim END
		sb.append(", ");
		sb.append("trim(tel_cd) tel_cd ");
		sb.append(", ");
		sb.append("trim(fax_cd) fax_cd ");
//		↓↓2006.06.22 zhujl カスタマイズ修正↓↓
		sb.append(", ");
		sb.append("trim(pos_kb) pos_kb ");
		sb.append(", ");
		sb.append("trim(boten_cd) boten_cd ");
		sb.append(", ");
		sb.append("trim(gyotai_kb) gyotai_kb ");
//	    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("hachu_st_dt ");
		sb.append(", ");
		sb.append("hachu_ed_dt ");
		sb.append(", ");
		sb.append("sndst_nehuda_dt ");
		sb.append(", ");
		sb.append("sndst_price_dt ");
		sb.append(", ");
		sb.append("sndst_tag_dt ");
		sb.append(", ");
		sb.append("sndst_plu_dt ");
		sb.append(", ");
		sb.append("sndst_pop_dt ");
		sb.append(", ");
		sb.append("sndst_keiryoki_dt ");
		sb.append(", ");
//		sb.append("hanku1_keiryoki_type ");
//		sb.append(", ");
//		sb.append("hanku2_keiryoki_type ");
//		sb.append(", ");
//		sb.append("hanku3_keiryoki_type ");
//		sb.append(", ");
//		sb.append("hanku4_keiryoki_type ");
//		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("trim(update_ts) update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append("from R_TENPO ");


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
//			↓↓2006.06.22 zhujl カスタマイズ修正↓↓
			//sb.append("tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			sb.append("tenpo_cd = '" + conv.convertWhereString( StringUtility.charFormat((String)map.get("tenpo_cd"),6,"0",true) ) + "'");
//			↑↑2006.06.20 zhujl カスタマイズ修正↑↑			
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

		
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
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
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）


		// tenpokaiso_tiiki_cd に対するWHERE区
		if( map.get("tenpokaiso_tiiki_cd_bef") != null && ((String)map.get("tenpokaiso_tiiki_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_tiiki_cd >= '" + conv.convertWhereString( (String)map.get("tenpokaiso_tiiki_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_tiiki_cd_aft") != null && ((String)map.get("tenpokaiso_tiiki_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_tiiki_cd <= '" + conv.convertWhereString( (String)map.get("tenpokaiso_tiiki_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_tiiki_cd") != null && ((String)map.get("tenpokaiso_tiiki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_tiiki_cd = '" + conv.convertWhereString( (String)map.get("tenpokaiso_tiiki_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_tiiki_cd_like") != null && ((String)map.get("tenpokaiso_tiiki_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_tiiki_cd like '%" + conv.convertWhereString( (String)map.get("tenpokaiso_tiiki_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_tiiki_cd_bef_like") != null && ((String)map.get("tenpokaiso_tiiki_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_tiiki_cd like '%" + conv.convertWhereString( (String)map.get("tenpokaiso_tiiki_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_tiiki_cd_aft_like") != null && ((String)map.get("tenpokaiso_tiiki_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_tiiki_cd like '" + conv.convertWhereString( (String)map.get("tenpokaiso_tiiki_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_tiiki_cd_in") != null && ((String)map.get("tenpokaiso_tiiki_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_tiiki_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpokaiso_tiiki_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_tiiki_cd_not_in") != null && ((String)map.get("tenpokaiso_tiiki_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_tiiki_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpokaiso_tiiki_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpokaiso_area_cd に対するWHERE区
		if( map.get("tenpokaiso_area_cd_bef") != null && ((String)map.get("tenpokaiso_area_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_area_cd >= '" + conv.convertWhereString( (String)map.get("tenpokaiso_area_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_area_cd_aft") != null && ((String)map.get("tenpokaiso_area_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_area_cd <= '" + conv.convertWhereString( (String)map.get("tenpokaiso_area_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_area_cd") != null && ((String)map.get("tenpokaiso_area_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_area_cd = '" + conv.convertWhereString( (String)map.get("tenpokaiso_area_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_area_cd_like") != null && ((String)map.get("tenpokaiso_area_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_area_cd like '%" + conv.convertWhereString( (String)map.get("tenpokaiso_area_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_area_cd_bef_like") != null && ((String)map.get("tenpokaiso_area_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_area_cd like '%" + conv.convertWhereString( (String)map.get("tenpokaiso_area_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_area_cd_aft_like") != null && ((String)map.get("tenpokaiso_area_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_area_cd like '" + conv.convertWhereString( (String)map.get("tenpokaiso_area_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_area_cd_in") != null && ((String)map.get("tenpokaiso_area_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_area_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpokaiso_area_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_area_cd_not_in") != null && ((String)map.get("tenpokaiso_area_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_area_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpokaiso_area_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpokaiso_block_cd に対するWHERE区
		if( map.get("tenpokaiso_block_cd_bef") != null && ((String)map.get("tenpokaiso_block_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_block_cd >= '" + conv.convertWhereString( (String)map.get("tenpokaiso_block_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_block_cd_aft") != null && ((String)map.get("tenpokaiso_block_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_block_cd <= '" + conv.convertWhereString( (String)map.get("tenpokaiso_block_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_block_cd") != null && ((String)map.get("tenpokaiso_block_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_block_cd = '" + conv.convertWhereString( (String)map.get("tenpokaiso_block_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_block_cd_like") != null && ((String)map.get("tenpokaiso_block_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_block_cd like '%" + conv.convertWhereString( (String)map.get("tenpokaiso_block_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_block_cd_bef_like") != null && ((String)map.get("tenpokaiso_block_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_block_cd like '%" + conv.convertWhereString( (String)map.get("tenpokaiso_block_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_block_cd_aft_like") != null && ((String)map.get("tenpokaiso_block_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_block_cd like '" + conv.convertWhereString( (String)map.get("tenpokaiso_block_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_block_cd_in") != null && ((String)map.get("tenpokaiso_block_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_block_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpokaiso_block_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpokaiso_block_cd_not_in") != null && ((String)map.get("tenpokaiso_block_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpokaiso_block_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpokaiso_block_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kanji_na に対するWHERE区
		if( map.get("kanji_na_bef") != null && ((String)map.get("kanji_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na >= '" + conv.convertWhereString( (String)map.get("kanji_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_aft") != null && ((String)map.get("kanji_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na <= '" + conv.convertWhereString( (String)map.get("kanji_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na") != null && ((String)map.get("kanji_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na = '" + conv.convertWhereString( (String)map.get("kanji_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_like") != null && ((String)map.get("kanji_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '%" + conv.convertWhereString( (String)map.get("kanji_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_bef_like") != null && ((String)map.get("kanji_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '%" + conv.convertWhereString( (String)map.get("kanji_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_aft_like") != null && ((String)map.get("kanji_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '" + conv.convertWhereString( (String)map.get("kanji_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_in") != null && ((String)map.get("kanji_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanji_na_not_in") != null && ((String)map.get("kanji_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kana_na に対するWHERE区
		if( map.get("kana_na_bef") != null && ((String)map.get("kana_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na >= '" + conv.convertWhereString( (String)map.get("kana_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_aft") != null && ((String)map.get("kana_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na <= '" + conv.convertWhereString( (String)map.get("kana_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na") != null && ((String)map.get("kana_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na = '" + conv.convertWhereString( (String)map.get("kana_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_like") != null && ((String)map.get("kana_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '%" + conv.convertWhereString( (String)map.get("kana_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_na_bef_like") != null && ((String)map.get("kana_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '%" + conv.convertWhereString( (String)map.get("kana_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_aft_like") != null && ((String)map.get("kana_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '" + conv.convertWhereString( (String)map.get("kana_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_na_in") != null && ((String)map.get("kana_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kana_na_not_in") != null && ((String)map.get("kana_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kanji_rn に対するWHERE区
		if( map.get("kanji_rn_bef") != null && ((String)map.get("kanji_rn_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn >= '" + conv.convertWhereString( (String)map.get("kanji_rn_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_aft") != null && ((String)map.get("kanji_rn_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn <= '" + conv.convertWhereString( (String)map.get("kanji_rn_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn") != null && ((String)map.get("kanji_rn")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn = '" + conv.convertWhereString( (String)map.get("kanji_rn") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_like") != null && ((String)map.get("kanji_rn_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '%" + conv.convertWhereString( (String)map.get("kanji_rn_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_bef_like") != null && ((String)map.get("kanji_rn_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '%" + conv.convertWhereString( (String)map.get("kanji_rn_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_aft_like") != null && ((String)map.get("kanji_rn_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '" + conv.convertWhereString( (String)map.get("kanji_rn_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_in") != null && ((String)map.get("kanji_rn_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_rn_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_not_in") != null && ((String)map.get("kanji_rn_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_rn_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kana_rn に対するWHERE区
		if( map.get("kana_rn_bef") != null && ((String)map.get("kana_rn_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn >= '" + conv.convertWhereString( (String)map.get("kana_rn_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_aft") != null && ((String)map.get("kana_rn_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn <= '" + conv.convertWhereString( (String)map.get("kana_rn_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn") != null && ((String)map.get("kana_rn")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn = '" + conv.convertWhereString( (String)map.get("kana_rn") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_like") != null && ((String)map.get("kana_rn_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '%" + conv.convertWhereString( (String)map.get("kana_rn_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_bef_like") != null && ((String)map.get("kana_rn_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '%" + conv.convertWhereString( (String)map.get("kana_rn_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_aft_like") != null && ((String)map.get("kana_rn_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '" + conv.convertWhereString( (String)map.get("kana_rn_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_in") != null && ((String)map.get("kana_rn_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_rn_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kana_rn_not_in") != null && ((String)map.get("kana_rn_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_rn_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpo_type_kb に対するWHERE区
		if( map.get("tenpo_type_kb_bef") != null && ((String)map.get("tenpo_type_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_type_kb >= '" + conv.convertWhereString( (String)map.get("tenpo_type_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_type_kb_aft") != null && ((String)map.get("tenpo_type_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_type_kb <= '" + conv.convertWhereString( (String)map.get("tenpo_type_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_type_kb") != null && ((String)map.get("tenpo_type_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_type_kb = '" + conv.convertWhereString( (String)map.get("tenpo_type_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_type_kb_like") != null && ((String)map.get("tenpo_type_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_type_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_type_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_type_kb_bef_like") != null && ((String)map.get("tenpo_type_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_type_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_type_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_type_kb_aft_like") != null && ((String)map.get("tenpo_type_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_type_kb like '" + conv.convertWhereString( (String)map.get("tenpo_type_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_type_kb_in") != null && ((String)map.get("tenpo_type_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_type_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_type_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_type_kb_not_in") != null && ((String)map.get("tenpo_type_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_type_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_type_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpo_kb に対するWHERE区
		if( map.get("tenpo_kb_bef") != null && ((String)map.get("tenpo_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_kb >= '" + conv.convertWhereString( (String)map.get("tenpo_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_kb_aft") != null && ((String)map.get("tenpo_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_kb <= '" + conv.convertWhereString( (String)map.get("tenpo_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_kb") != null && ((String)map.get("tenpo_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_kb = '" + conv.convertWhereString( (String)map.get("tenpo_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_kb_like") != null && ((String)map.get("tenpo_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_kb_bef_like") != null && ((String)map.get("tenpo_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_kb_aft_like") != null && ((String)map.get("tenpo_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_kb like '" + conv.convertWhereString( (String)map.get("tenpo_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_kb_in") != null && ((String)map.get("tenpo_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_kb_not_in") != null && ((String)map.get("tenpo_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kaiten_dt に対するWHERE区
		if( map.get("kaiten_dt_bef") != null && ((String)map.get("kaiten_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt >= '" + conv.convertWhereString( (String)map.get("kaiten_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kaiten_dt_aft") != null && ((String)map.get("kaiten_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt <= '" + conv.convertWhereString( (String)map.get("kaiten_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kaiten_dt") != null && ((String)map.get("kaiten_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt = '" + conv.convertWhereString( (String)map.get("kaiten_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kaiten_dt_like") != null && ((String)map.get("kaiten_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt like '%" + conv.convertWhereString( (String)map.get("kaiten_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kaiten_dt_bef_like") != null && ((String)map.get("kaiten_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt like '%" + conv.convertWhereString( (String)map.get("kaiten_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kaiten_dt_aft_like") != null && ((String)map.get("kaiten_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt like '" + conv.convertWhereString( (String)map.get("kaiten_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kaiten_dt_in") != null && ((String)map.get("kaiten_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kaiten_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kaiten_dt_not_in") != null && ((String)map.get("kaiten_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kaiten_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// heiten_dt に対するWHERE区
		if( map.get("heiten_dt_bef") != null && ((String)map.get("heiten_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("heiten_dt >= '" + conv.convertWhereString( (String)map.get("heiten_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("heiten_dt_aft") != null && ((String)map.get("heiten_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("heiten_dt <= '" + conv.convertWhereString( (String)map.get("heiten_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("heiten_dt") != null && ((String)map.get("heiten_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("heiten_dt = '" + conv.convertWhereString( (String)map.get("heiten_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("heiten_dt_like") != null && ((String)map.get("heiten_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("heiten_dt like '%" + conv.convertWhereString( (String)map.get("heiten_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("heiten_dt_bef_like") != null && ((String)map.get("heiten_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("heiten_dt like '%" + conv.convertWhereString( (String)map.get("heiten_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("heiten_dt_aft_like") != null && ((String)map.get("heiten_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("heiten_dt like '" + conv.convertWhereString( (String)map.get("heiten_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("heiten_dt_in") != null && ((String)map.get("heiten_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("heiten_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("heiten_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("heiten_dt_not_in") != null && ((String)map.get("heiten_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("heiten_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("heiten_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// address_kanji_na に対するWHERE区
		if( map.get("address_kanji_na_bef") != null && ((String)map.get("address_kanji_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji_na >= '" + conv.convertWhereString( (String)map.get("address_kanji_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji_na_aft") != null && ((String)map.get("address_kanji_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji_na <= '" + conv.convertWhereString( (String)map.get("address_kanji_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji_na") != null && ((String)map.get("address_kanji_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji_na = '" + conv.convertWhereString( (String)map.get("address_kanji_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji_na_like") != null && ((String)map.get("address_kanji_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji_na like '%" + conv.convertWhereString( (String)map.get("address_kanji_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kanji_na_bef_like") != null && ((String)map.get("address_kanji_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji_na like '%" + conv.convertWhereString( (String)map.get("address_kanji_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji_na_aft_like") != null && ((String)map.get("address_kanji_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji_na like '" + conv.convertWhereString( (String)map.get("address_kanji_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kanji_na_in") != null && ((String)map.get("address_kanji_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kanji_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("address_kanji_na_not_in") != null && ((String)map.get("address_kanji_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kanji_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// address_kana_na に対するWHERE区
		if( map.get("address_kana_na_bef") != null && ((String)map.get("address_kana_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana_na >= '" + conv.convertWhereString( (String)map.get("address_kana_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana_na_aft") != null && ((String)map.get("address_kana_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana_na <= '" + conv.convertWhereString( (String)map.get("address_kana_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana_na") != null && ((String)map.get("address_kana_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana_na = '" + conv.convertWhereString( (String)map.get("address_kana_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana_na_like") != null && ((String)map.get("address_kana_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana_na like '%" + conv.convertWhereString( (String)map.get("address_kana_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kana_na_bef_like") != null && ((String)map.get("address_kana_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana_na like '%" + conv.convertWhereString( (String)map.get("address_kana_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana_na_aft_like") != null && ((String)map.get("address_kana_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana_na like '" + conv.convertWhereString( (String)map.get("address_kana_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kana_na_in") != null && ((String)map.get("address_kana_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kana_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("address_kana_na_not_in") != null && ((String)map.get("address_kana_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kana_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// address_3_na に対するWHERE区
		if( map.get("address_3_na_bef") != null && ((String)map.get("address_3_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_3_na >= '" + conv.convertWhereString( (String)map.get("address_3_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_3_na_aft") != null && ((String)map.get("address_3_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_3_na <= '" + conv.convertWhereString( (String)map.get("address_3_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_3_na") != null && ((String)map.get("address_3_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_3_na = '" + conv.convertWhereString( (String)map.get("address_3_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_3_na_like") != null && ((String)map.get("address_3_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_3_na like '%" + conv.convertWhereString( (String)map.get("address_3_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_3_na_bef_like") != null && ((String)map.get("address_3_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_3_na like '%" + conv.convertWhereString( (String)map.get("address_3_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_3_na_aft_like") != null && ((String)map.get("address_3_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_3_na like '" + conv.convertWhereString( (String)map.get("address_3_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_3_na_in") != null && ((String)map.get("address_3_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_3_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_3_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("address_3_na_not_in") != null && ((String)map.get("address_3_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_3_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_3_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// yubin_cd に対するWHERE区
		if( map.get("yubin_cd_bef") != null && ((String)map.get("yubin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd >= '" + conv.convertWhereString( (String)map.get("yubin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_aft") != null && ((String)map.get("yubin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd <= '" + conv.convertWhereString( (String)map.get("yubin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd") != null && ((String)map.get("yubin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd = '" + conv.convertWhereString( (String)map.get("yubin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_like") != null && ((String)map.get("yubin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd like '%" + conv.convertWhereString( (String)map.get("yubin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_bef_like") != null && ((String)map.get("yubin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd like '%" + conv.convertWhereString( (String)map.get("yubin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_aft_like") != null && ((String)map.get("yubin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd like '" + conv.convertWhereString( (String)map.get("yubin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_in") != null && ((String)map.get("yubin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yubin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_not_in") != null && ((String)map.get("yubin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yubin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tel_cd に対するWHERE区
		if( map.get("tel_cd_bef") != null && ((String)map.get("tel_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel_cd >= '" + conv.convertWhereString( (String)map.get("tel_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel_cd_aft") != null && ((String)map.get("tel_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel_cd <= '" + conv.convertWhereString( (String)map.get("tel_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel_cd") != null && ((String)map.get("tel_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel_cd = '" + conv.convertWhereString( (String)map.get("tel_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel_cd_like") != null && ((String)map.get("tel_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel_cd like '%" + conv.convertWhereString( (String)map.get("tel_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tel_cd_bef_like") != null && ((String)map.get("tel_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel_cd like '%" + conv.convertWhereString( (String)map.get("tel_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel_cd_aft_like") != null && ((String)map.get("tel_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel_cd like '" + conv.convertWhereString( (String)map.get("tel_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tel_cd_in") != null && ((String)map.get("tel_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tel_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tel_cd_not_in") != null && ((String)map.get("tel_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tel_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// fax_cd に対するWHERE区
		if( map.get("fax_cd_bef") != null && ((String)map.get("fax_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax_cd >= '" + conv.convertWhereString( (String)map.get("fax_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax_cd_aft") != null && ((String)map.get("fax_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax_cd <= '" + conv.convertWhereString( (String)map.get("fax_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax_cd") != null && ((String)map.get("fax_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax_cd = '" + conv.convertWhereString( (String)map.get("fax_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax_cd_like") != null && ((String)map.get("fax_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax_cd like '%" + conv.convertWhereString( (String)map.get("fax_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("fax_cd_bef_like") != null && ((String)map.get("fax_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax_cd like '%" + conv.convertWhereString( (String)map.get("fax_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax_cd_aft_like") != null && ((String)map.get("fax_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax_cd like '" + conv.convertWhereString( (String)map.get("fax_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("fax_cd_in") != null && ((String)map.get("fax_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("fax_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("fax_cd_not_in") != null && ((String)map.get("fax_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("fax_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_st_dt に対するWHERE区
		if( map.get("hachu_st_dt_bef") != null && ((String)map.get("hachu_st_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_st_dt >= '" + conv.convertWhereString( (String)map.get("hachu_st_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_st_dt_aft") != null && ((String)map.get("hachu_st_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_st_dt <= '" + conv.convertWhereString( (String)map.get("hachu_st_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_st_dt") != null && ((String)map.get("hachu_st_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_st_dt = '" + conv.convertWhereString( (String)map.get("hachu_st_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_st_dt_like") != null && ((String)map.get("hachu_st_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_st_dt like '%" + conv.convertWhereString( (String)map.get("hachu_st_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_st_dt_bef_like") != null && ((String)map.get("hachu_st_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_st_dt like '%" + conv.convertWhereString( (String)map.get("hachu_st_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_st_dt_aft_like") != null && ((String)map.get("hachu_st_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_st_dt like '" + conv.convertWhereString( (String)map.get("hachu_st_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_st_dt_in") != null && ((String)map.get("hachu_st_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_st_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_st_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_st_dt_not_in") != null && ((String)map.get("hachu_st_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_st_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_st_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_ed_dt に対するWHERE区
		if( map.get("hachu_ed_dt_bef") != null && ((String)map.get("hachu_ed_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_ed_dt >= '" + conv.convertWhereString( (String)map.get("hachu_ed_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_ed_dt_aft") != null && ((String)map.get("hachu_ed_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_ed_dt <= '" + conv.convertWhereString( (String)map.get("hachu_ed_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_ed_dt") != null && ((String)map.get("hachu_ed_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_ed_dt = '" + conv.convertWhereString( (String)map.get("hachu_ed_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_ed_dt_like") != null && ((String)map.get("hachu_ed_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_ed_dt like '%" + conv.convertWhereString( (String)map.get("hachu_ed_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_ed_dt_bef_like") != null && ((String)map.get("hachu_ed_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_ed_dt like '%" + conv.convertWhereString( (String)map.get("hachu_ed_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_ed_dt_aft_like") != null && ((String)map.get("hachu_ed_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_ed_dt like '" + conv.convertWhereString( (String)map.get("hachu_ed_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_ed_dt_in") != null && ((String)map.get("hachu_ed_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_ed_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_ed_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_ed_dt_not_in") != null && ((String)map.get("hachu_ed_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_ed_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_ed_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sndst_nehuda_dt に対するWHERE区
		if( map.get("sndst_nehuda_dt_bef") != null && ((String)map.get("sndst_nehuda_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_nehuda_dt >= '" + conv.convertWhereString( (String)map.get("sndst_nehuda_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_nehuda_dt_aft") != null && ((String)map.get("sndst_nehuda_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_nehuda_dt <= '" + conv.convertWhereString( (String)map.get("sndst_nehuda_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_nehuda_dt") != null && ((String)map.get("sndst_nehuda_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_nehuda_dt = '" + conv.convertWhereString( (String)map.get("sndst_nehuda_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_nehuda_dt_like") != null && ((String)map.get("sndst_nehuda_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_nehuda_dt like '%" + conv.convertWhereString( (String)map.get("sndst_nehuda_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_nehuda_dt_bef_like") != null && ((String)map.get("sndst_nehuda_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_nehuda_dt like '%" + conv.convertWhereString( (String)map.get("sndst_nehuda_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_nehuda_dt_aft_like") != null && ((String)map.get("sndst_nehuda_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_nehuda_dt like '" + conv.convertWhereString( (String)map.get("sndst_nehuda_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_nehuda_dt_in") != null && ((String)map.get("sndst_nehuda_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_nehuda_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_nehuda_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sndst_nehuda_dt_not_in") != null && ((String)map.get("sndst_nehuda_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_nehuda_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_nehuda_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sndst_price_dt に対するWHERE区
		if( map.get("sndst_price_dt_bef") != null && ((String)map.get("sndst_price_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_price_dt >= '" + conv.convertWhereString( (String)map.get("sndst_price_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_price_dt_aft") != null && ((String)map.get("sndst_price_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_price_dt <= '" + conv.convertWhereString( (String)map.get("sndst_price_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_price_dt") != null && ((String)map.get("sndst_price_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_price_dt = '" + conv.convertWhereString( (String)map.get("sndst_price_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_price_dt_like") != null && ((String)map.get("sndst_price_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_price_dt like '%" + conv.convertWhereString( (String)map.get("sndst_price_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_price_dt_bef_like") != null && ((String)map.get("sndst_price_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_price_dt like '%" + conv.convertWhereString( (String)map.get("sndst_price_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_price_dt_aft_like") != null && ((String)map.get("sndst_price_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_price_dt like '" + conv.convertWhereString( (String)map.get("sndst_price_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_price_dt_in") != null && ((String)map.get("sndst_price_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_price_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_price_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sndst_price_dt_not_in") != null && ((String)map.get("sndst_price_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_price_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_price_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sndst_tag_dt に対するWHERE区
		if( map.get("sndst_tag_dt_bef") != null && ((String)map.get("sndst_tag_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_tag_dt >= '" + conv.convertWhereString( (String)map.get("sndst_tag_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_tag_dt_aft") != null && ((String)map.get("sndst_tag_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_tag_dt <= '" + conv.convertWhereString( (String)map.get("sndst_tag_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_tag_dt") != null && ((String)map.get("sndst_tag_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_tag_dt = '" + conv.convertWhereString( (String)map.get("sndst_tag_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_tag_dt_like") != null && ((String)map.get("sndst_tag_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_tag_dt like '%" + conv.convertWhereString( (String)map.get("sndst_tag_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_tag_dt_bef_like") != null && ((String)map.get("sndst_tag_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_tag_dt like '%" + conv.convertWhereString( (String)map.get("sndst_tag_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_tag_dt_aft_like") != null && ((String)map.get("sndst_tag_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_tag_dt like '" + conv.convertWhereString( (String)map.get("sndst_tag_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_tag_dt_in") != null && ((String)map.get("sndst_tag_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_tag_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_tag_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sndst_tag_dt_not_in") != null && ((String)map.get("sndst_tag_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_tag_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_tag_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sndst_plu_dt に対するWHERE区
		if( map.get("sndst_plu_dt_bef") != null && ((String)map.get("sndst_plu_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_plu_dt >= '" + conv.convertWhereString( (String)map.get("sndst_plu_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_plu_dt_aft") != null && ((String)map.get("sndst_plu_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_plu_dt <= '" + conv.convertWhereString( (String)map.get("sndst_plu_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_plu_dt") != null && ((String)map.get("sndst_plu_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_plu_dt = '" + conv.convertWhereString( (String)map.get("sndst_plu_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_plu_dt_like") != null && ((String)map.get("sndst_plu_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_plu_dt like '%" + conv.convertWhereString( (String)map.get("sndst_plu_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_plu_dt_bef_like") != null && ((String)map.get("sndst_plu_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_plu_dt like '%" + conv.convertWhereString( (String)map.get("sndst_plu_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_plu_dt_aft_like") != null && ((String)map.get("sndst_plu_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_plu_dt like '" + conv.convertWhereString( (String)map.get("sndst_plu_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_plu_dt_in") != null && ((String)map.get("sndst_plu_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_plu_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_plu_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sndst_plu_dt_not_in") != null && ((String)map.get("sndst_plu_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_plu_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_plu_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sndst_pop_dt に対するWHERE区
		if( map.get("sndst_pop_dt_bef") != null && ((String)map.get("sndst_pop_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_pop_dt >= '" + conv.convertWhereString( (String)map.get("sndst_pop_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_pop_dt_aft") != null && ((String)map.get("sndst_pop_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_pop_dt <= '" + conv.convertWhereString( (String)map.get("sndst_pop_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_pop_dt") != null && ((String)map.get("sndst_pop_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_pop_dt = '" + conv.convertWhereString( (String)map.get("sndst_pop_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_pop_dt_like") != null && ((String)map.get("sndst_pop_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_pop_dt like '%" + conv.convertWhereString( (String)map.get("sndst_pop_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_pop_dt_bef_like") != null && ((String)map.get("sndst_pop_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_pop_dt like '%" + conv.convertWhereString( (String)map.get("sndst_pop_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_pop_dt_aft_like") != null && ((String)map.get("sndst_pop_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_pop_dt like '" + conv.convertWhereString( (String)map.get("sndst_pop_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_pop_dt_in") != null && ((String)map.get("sndst_pop_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_pop_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_pop_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sndst_pop_dt_not_in") != null && ((String)map.get("sndst_pop_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_pop_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_pop_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sndst_keiryoki_dt に対するWHERE区
		if( map.get("sndst_keiryoki_dt_bef") != null && ((String)map.get("sndst_keiryoki_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_keiryoki_dt >= '" + conv.convertWhereString( (String)map.get("sndst_keiryoki_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_keiryoki_dt_aft") != null && ((String)map.get("sndst_keiryoki_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_keiryoki_dt <= '" + conv.convertWhereString( (String)map.get("sndst_keiryoki_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_keiryoki_dt") != null && ((String)map.get("sndst_keiryoki_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_keiryoki_dt = '" + conv.convertWhereString( (String)map.get("sndst_keiryoki_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_keiryoki_dt_like") != null && ((String)map.get("sndst_keiryoki_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_keiryoki_dt like '%" + conv.convertWhereString( (String)map.get("sndst_keiryoki_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_keiryoki_dt_bef_like") != null && ((String)map.get("sndst_keiryoki_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_keiryoki_dt like '%" + conv.convertWhereString( (String)map.get("sndst_keiryoki_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sndst_keiryoki_dt_aft_like") != null && ((String)map.get("sndst_keiryoki_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_keiryoki_dt like '" + conv.convertWhereString( (String)map.get("sndst_keiryoki_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sndst_keiryoki_dt_in") != null && ((String)map.get("sndst_keiryoki_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_keiryoki_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_keiryoki_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sndst_keiryoki_dt_not_in") != null && ((String)map.get("sndst_keiryoki_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sndst_keiryoki_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sndst_keiryoki_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hanku1_keiryoki_type に対するWHERE区
//		if( map.get("hanku1_keiryoki_type_bef") != null && ((String)map.get("hanku1_keiryoki_type_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku1_keiryoki_type >= '" + conv.convertWhereString( (String)map.get("hanku1_keiryoki_type_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku1_keiryoki_type_aft") != null && ((String)map.get("hanku1_keiryoki_type_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku1_keiryoki_type <= '" + conv.convertWhereString( (String)map.get("hanku1_keiryoki_type_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku1_keiryoki_type") != null && ((String)map.get("hanku1_keiryoki_type")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku1_keiryoki_type = '" + conv.convertWhereString( (String)map.get("hanku1_keiryoki_type") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku1_keiryoki_type_like") != null && ((String)map.get("hanku1_keiryoki_type_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku1_keiryoki_type like '%" + conv.convertWhereString( (String)map.get("hanku1_keiryoki_type_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku1_keiryoki_type_bef_like") != null && ((String)map.get("hanku1_keiryoki_type_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku1_keiryoki_type like '%" + conv.convertWhereString( (String)map.get("hanku1_keiryoki_type_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku1_keiryoki_type_aft_like") != null && ((String)map.get("hanku1_keiryoki_type_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku1_keiryoki_type like '" + conv.convertWhereString( (String)map.get("hanku1_keiryoki_type_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku1_keiryoki_type_in") != null && ((String)map.get("hanku1_keiryoki_type_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku1_keiryoki_type in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku1_keiryoki_type_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("hanku1_keiryoki_type_not_in") != null && ((String)map.get("hanku1_keiryoki_type_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku1_keiryoki_type not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku1_keiryoki_type_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}


		// hanku2_keiryoki_type に対するWHERE区
//		if( map.get("hanku2_keiryoki_type_bef") != null && ((String)map.get("hanku2_keiryoki_type_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku2_keiryoki_type >= '" + conv.convertWhereString( (String)map.get("hanku2_keiryoki_type_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku2_keiryoki_type_aft") != null && ((String)map.get("hanku2_keiryoki_type_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku2_keiryoki_type <= '" + conv.convertWhereString( (String)map.get("hanku2_keiryoki_type_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku2_keiryoki_type") != null && ((String)map.get("hanku2_keiryoki_type")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku2_keiryoki_type = '" + conv.convertWhereString( (String)map.get("hanku2_keiryoki_type") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku2_keiryoki_type_like") != null && ((String)map.get("hanku2_keiryoki_type_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku2_keiryoki_type like '%" + conv.convertWhereString( (String)map.get("hanku2_keiryoki_type_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku2_keiryoki_type_bef_like") != null && ((String)map.get("hanku2_keiryoki_type_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku2_keiryoki_type like '%" + conv.convertWhereString( (String)map.get("hanku2_keiryoki_type_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku2_keiryoki_type_aft_like") != null && ((String)map.get("hanku2_keiryoki_type_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku2_keiryoki_type like '" + conv.convertWhereString( (String)map.get("hanku2_keiryoki_type_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku2_keiryoki_type_in") != null && ((String)map.get("hanku2_keiryoki_type_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku2_keiryoki_type in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku2_keiryoki_type_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("hanku2_keiryoki_type_not_in") != null && ((String)map.get("hanku2_keiryoki_type_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku2_keiryoki_type not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku2_keiryoki_type_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}


		// hanku3_keiryoki_type に対するWHERE区
//		if( map.get("hanku3_keiryoki_type_bef") != null && ((String)map.get("hanku3_keiryoki_type_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku3_keiryoki_type >= '" + conv.convertWhereString( (String)map.get("hanku3_keiryoki_type_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku3_keiryoki_type_aft") != null && ((String)map.get("hanku3_keiryoki_type_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku3_keiryoki_type <= '" + conv.convertWhereString( (String)map.get("hanku3_keiryoki_type_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku3_keiryoki_type") != null && ((String)map.get("hanku3_keiryoki_type")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku3_keiryoki_type = '" + conv.convertWhereString( (String)map.get("hanku3_keiryoki_type") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku3_keiryoki_type_like") != null && ((String)map.get("hanku3_keiryoki_type_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku3_keiryoki_type like '%" + conv.convertWhereString( (String)map.get("hanku3_keiryoki_type_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku3_keiryoki_type_bef_like") != null && ((String)map.get("hanku3_keiryoki_type_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku3_keiryoki_type like '%" + conv.convertWhereString( (String)map.get("hanku3_keiryoki_type_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku3_keiryoki_type_aft_like") != null && ((String)map.get("hanku3_keiryoki_type_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku3_keiryoki_type like '" + conv.convertWhereString( (String)map.get("hanku3_keiryoki_type_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku3_keiryoki_type_in") != null && ((String)map.get("hanku3_keiryoki_type_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku3_keiryoki_type in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku3_keiryoki_type_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("hanku3_keiryoki_type_not_in") != null && ((String)map.get("hanku3_keiryoki_type_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku3_keiryoki_type not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku3_keiryoki_type_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}


		// hanku4_keiryoki_type に対するWHERE区
//		if( map.get("hanku4_keiryoki_type_bef") != null && ((String)map.get("hanku4_keiryoki_type_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku4_keiryoki_type >= '" + conv.convertWhereString( (String)map.get("hanku4_keiryoki_type_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku4_keiryoki_type_aft") != null && ((String)map.get("hanku4_keiryoki_type_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku4_keiryoki_type <= '" + conv.convertWhereString( (String)map.get("hanku4_keiryoki_type_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku4_keiryoki_type") != null && ((String)map.get("hanku4_keiryoki_type")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku4_keiryoki_type = '" + conv.convertWhereString( (String)map.get("hanku4_keiryoki_type") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku4_keiryoki_type_like") != null && ((String)map.get("hanku4_keiryoki_type_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku4_keiryoki_type like '%" + conv.convertWhereString( (String)map.get("hanku4_keiryoki_type_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku4_keiryoki_type_bef_like") != null && ((String)map.get("hanku4_keiryoki_type_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku4_keiryoki_type like '%" + conv.convertWhereString( (String)map.get("hanku4_keiryoki_type_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku4_keiryoki_type_aft_like") != null && ((String)map.get("hanku4_keiryoki_type_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku4_keiryoki_type like '" + conv.convertWhereString( (String)map.get("hanku4_keiryoki_type_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("hanku4_keiryoki_type_in") != null && ((String)map.get("hanku4_keiryoki_type_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku4_keiryoki_type in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku4_keiryoki_type_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("hanku4_keiryoki_type_not_in") != null && ((String)map.get("hanku4_keiryoki_type_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("hanku4_keiryoki_type not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku4_keiryoki_type_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}


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
		 // gyotai_kb に対するWHERE区
		  if( map.get("gyotai_kb_bef") != null && ((String)map.get("gyotai_kb_bef")).trim().length() > 0 )
		  {
		   sb.append(whereStr);
		   sb.append("gyotai_kb >= '" + conv.convertWhereString( (String)map.get("gyotai_kb_bef") ) + "'");
		   whereStr = andStr;
		  }
		  if( map.get("gyotai_kb_aft") != null && ((String)map.get("gyotai_kb_aft")).trim().length() > 0 )
		  {
		   sb.append(whereStr);
		   sb.append("gyotai_kb <= '" + conv.convertWhereString( (String)map.get("gyotai_kb_aft") ) + "'");
		   whereStr = andStr;
		  }
		  if( map.get("gyotai_kb") != null && ((String)map.get("gyotai_kb")).trim().length() > 0 )
		  {
		   sb.append(whereStr);
		   sb.append("gyotai_kb = '" + conv.convertWhereString( (String)map.get("gyotai_kb") ) + "'");
		   whereStr = andStr;
		  }
		  if( map.get("gyotai_kb_like") != null && ((String)map.get("gyotai_kb_like")).trim().length() > 0 )
		  {
		   sb.append(whereStr);
		   sb.append("gyotai_kb like '%" + conv.convertWhereString( (String)map.get("gyotai_kb_like") ) + "%'");
		   whereStr = andStr;
		  }
		  if( map.get("gyotai_kb_bef_like") != null && ((String)map.get("gyotai_kb_bef_like")).trim().length() > 0 )
		  {
		   sb.append(whereStr);
		   sb.append("gyotai_kb like '%" + conv.convertWhereString( (String)map.get("gyotai_kb_bef_like") ) + "'");
		   whereStr = andStr;
		  }
		  if( map.get("gyotai_kb_aft_like") != null && ((String)map.get("gyotai_kb_aft_like")).trim().length() > 0 )
		  {
		   sb.append(whereStr);
		   sb.append("gyotai_kb like '" + conv.convertWhereString( (String)map.get("gyotai_kb_aft_like") ) + "%'");
		   whereStr = andStr;
		  }
		  if( map.get("gyotai_kb_in") != null && ((String)map.get("gyotai_kb_in")).trim().length() > 0 )
		  {
		   sb.append(whereStr);
		   sb.append("gyotai_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gyotai_kb_in") ),",","','") + "' )");
		   whereStr = andStr;
		  }
		  if( map.get("gyotai_kb_not_in") != null && ((String)map.get("gyotai_kb_not_in")).trim().length() > 0 )
		  {
		   sb.append(whereStr);
		   sb.append("gyotai_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gyotai_kb_not_in") ),",","','") + "' )");
		   whereStr = andStr;
		  }

		//===BEGIN=== Add by kou 2006/11/15
		if( map.get("tenpoCd") != null && ((String)map.get("tenpoCd")).trim().length() > 0 )
		{
			sb.append(" AND TENPO_CD = '")
				.append(conv.convertWhereString(StringUtility.charFormat((String)map.get("tenpoCd") , 6, "0", true))).append("'");
		} else {
			if( map.get("tenGroupNo") != null && ((String)map.get("tenGroupNo")).trim().length() > 0 )
			{
				sb.append(" AND TENPO_CD IN (");
				sb.append(" 		SELECT TENPO_CD ");
				sb.append(" 		  FROM R_TENGROUP ");
				sb.append(" 		 WHERE GROUPNO_CD = '").append(map.get("tenGroupNo")).append("'");
				sb.append(" 		   AND BUMON_CD = '")
					.append(conv.convertWhereString(StringUtility.charFormat((String)map.get("bumon_cd") , 4, "0", true))).append("'");
//				↓↓2006.12.01 H.Yamamoto カスタマイズ修正↓↓
				sb.append(" 		   AND YOTO_KB = '")
					.append(mst003901_YotoKbDictionary.HACHU.getCode()).append("'");
				sb.append(" 		   AND DELETE_FG = '")
					.append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//				↑↑2006.12.01 H.Yamamoto カスタマイズ修正↑↑
				sb.append(" ) ");
			}
		}
		//===END=== Add by kou 2006/11/15
		
		sb.append(" order by ");
//		// ===BEGIN=== Add by kou 2006/10/5
//		sb.append(" GYOTAI_KB, ");
//		// ===END=== Add by kou 2006/10/5
		sb.append("tenpo_cd");
//		↓↓2006.12.01 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.12.01 H.Yamamoto カスタマイズ修正↑↑

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
		mst530101_TenpoBean bean = (mst530101_TenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_TENPO (");
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" tenpo_cd");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hojin_cd");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getTenpokaisoTiikiCd() != null && bean.getTenpokaisoTiikiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpokaiso_tiiki_cd");
		}
		if( bean.getTenpokaisoAreaCd() != null && bean.getTenpokaisoAreaCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpokaiso_area_cd");
		}
		if( bean.getTenpokaisoBlockCd() != null && bean.getTenpokaisoBlockCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpokaiso_block_cd");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_na");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_na");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_rn");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_rn");
		}
		if( bean.getTenpoTypeKb() != null && bean.getTenpoTypeKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_type_kb");
		}
		if( bean.getTenpoKb() != null && bean.getTenpoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_kb");
		}
		if( bean.getKaitenDt() != null && bean.getKaitenDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kaiten_dt");
		}
		if( bean.getHeitenDt() != null && bean.getHeitenDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" heiten_dt");
		}
		if( bean.getAddressKanjiNa() != null && bean.getAddressKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji_na");
		}
		if( bean.getAddressKanaNa() != null && bean.getAddressKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana_na");
		}
		if( bean.getAddress3Na() != null && bean.getAddress3Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_3_na");
		}
		if( bean.getYubinCd() != null && bean.getYubinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yubin_cd");
		}
		if( bean.getTelCd() != null && bean.getTelCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel_cd");
		}
		if( bean.getFaxCd() != null && bean.getFaxCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax_cd");
		}
		
//		↓↓2006.06.22 zhujl カスタマイズ修正↓↓
		if( bean.getPosKb() != null && bean.getPosKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pos_kb");
		}
		if( bean.getBotenCd() != null && bean.getBotenCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" boten_cd");
		}
		if( bean.getGyotaiKb() != null && bean.getGyotaiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gyotai_kb");
		}
//	    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑
		
		if( bean.getHachuStDt() != null && bean.getHachuStDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_st_dt");
		}
		if( bean.getHachuEdDt() != null && bean.getHachuEdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_ed_dt");
		}
		if( bean.getSndstNehudaDt() != null && bean.getSndstNehudaDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_nehuda_dt");
		}
		if( bean.getSndstPriceDt() != null && bean.getSndstPriceDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_price_dt");
		}
		if( bean.getSndstTagDt() != null && bean.getSndstTagDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_tag_dt");
		}
		if( bean.getSndstPluDt() != null && bean.getSndstPluDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_plu_dt");
		}
		if( bean.getSndstPopDt() != null && bean.getSndstPopDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_pop_dt");
		}
		if( bean.getSndstKeiryokiDt() != null && bean.getSndstKeiryokiDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_keiryoki_dt");
		}
//		if( bean.getHanku1KeiryokiType() != null && bean.getHanku1KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku1_keiryoki_type");
//		}
//		if( bean.getHanku2KeiryokiType() != null && bean.getHanku2KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku2_keiryoki_type");
//		}
//		if( bean.getHanku3KeiryokiType() != null && bean.getHanku3KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku3_keiryoki_type");
//		}
//		if( bean.getHanku4KeiryokiType() != null && bean.getHanku4KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku4_keiryoki_type");
//		}
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

//		↓↓2006.06.22 zhujl カスタマイズ修正↓↓
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			aftKanma = true;
			//sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
			sb.append("'" + conv.convertString( StringUtility.charFormat(bean.getTenpoCd(), 6, "0", true) ) + "'");
			
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			//sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
			sb.append("''");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
//	    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑
		if( bean.getTenpokaisoTiikiCd() != null && bean.getTenpokaisoTiikiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpokaisoTiikiCd() ) + "'");
		}
		if( bean.getTenpokaisoAreaCd() != null && bean.getTenpokaisoAreaCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpokaisoAreaCd() ) + "'");
		}
		if( bean.getTenpokaisoBlockCd() != null && bean.getTenpokaisoBlockCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpokaisoBlockCd() ) + "'");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
		}
		if( bean.getTenpoTypeKb() != null && bean.getTenpoTypeKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoTypeKb() ) + "'");
		}
		if( bean.getTenpoKb() != null && bean.getTenpoKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoKb() ) + "'");
		}
		if( bean.getKaitenDt() != null && bean.getKaitenDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKaitenDt() ) + "'");
		}
		if( bean.getHeitenDt() != null && bean.getHeitenDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHeitenDt() ) + "'");
		}
		if( bean.getAddressKanjiNa() != null && bean.getAddressKanjiNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAddressKanjiNa() ) + "'");
		}
		if( bean.getAddressKanaNa() != null && bean.getAddressKanaNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAddressKanaNa() ) + "'");
		}
		if( bean.getAddress3Na() != null && bean.getAddress3Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAddress3Na() ) + "'");
		}
		if( bean.getYubinCd() != null && bean.getYubinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYubinCd() ) + "'");
		}
		if( bean.getTelCd() != null && bean.getTelCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTelCd() ) + "'");
		}
		if( bean.getFaxCd() != null && bean.getFaxCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getFaxCd() ) + "'");
		}
		
//		↓↓2006.06.22 zhujl カスタマイズ修正↓↓
		if( bean.getPosKb() != null && bean.getPosKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPosKb() ) + "'");
		}
		if( bean.getBotenCd() != null && bean.getBotenCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBotenCd() ) + "'");
		}
		if( bean.getGyotaiKb() != null && bean.getGyotaiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getGyotaiKb() ) + "'");
		}
//	    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑
		
		if( bean.getHachuStDt() != null && bean.getHachuStDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuStDt() ) + "'");
		}
		if( bean.getHachuEdDt() != null && bean.getHachuEdDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuEdDt() ) + "'");
		}
		if( bean.getSndstNehudaDt() != null && bean.getSndstNehudaDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSndstNehudaDt() ) + "'");
		}
		if( bean.getSndstPriceDt() != null && bean.getSndstPriceDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSndstPriceDt() ) + "'");
		}
		if( bean.getSndstTagDt() != null && bean.getSndstTagDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSndstTagDt() ) + "'");
		}
		if( bean.getSndstPluDt() != null && bean.getSndstPluDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSndstPluDt() ) + "'");
		}
		if( bean.getSndstPopDt() != null && bean.getSndstPopDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSndstPopDt() ) + "'");
		}
		if( bean.getSndstKeiryokiDt() != null && bean.getSndstKeiryokiDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSndstKeiryokiDt() ) + "'");
		}
//		if( bean.getHanku1KeiryokiType() != null && bean.getHanku1KeiryokiType().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHanku1KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku2KeiryokiType() != null && bean.getHanku2KeiryokiType().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHanku2KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku3KeiryokiType() != null && bean.getHanku3KeiryokiType().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHanku3KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku4KeiryokiType() != null && bean.getHanku4KeiryokiType().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHanku4KeiryokiType() ) + "'");
//		}
		 
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
//	      ↓↓　移植(2006.05.11) ↓↓
//			try {
//		          sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
//		     } catch (SQLException e) {
//		          e.printStackTrace();
//		     }
//	      ↑↑　移植(2006.05.11) ↑↑
//			↓↓2006.06.22 zhujl カスタマイズ修正↓↓
			sb.append(" '" + conv.convertString( bean.getInsertTs()) + "' ");
//			↑↑2006.06.20 zhujl カスタマイズ修正↑↑
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
//	      ↓↓　移植(2006.05.11) ↓↓
//			try {
//		          sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
//		     } catch (SQLException e) {
//		          e.printStackTrace();
//		     }
//	      ↑↑　移植(2006.05.11) ↑↑
			
//			↓↓2006.06.22 zhujl カスタマイズ修正↓↓
			sb.append(" '" + conv.convertString( bean.getInsertTs()) + "' ");
//			↑↑2006.06.20 zhujl カスタマイズ修正↑↑
			
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
		mst530101_TenpoBean bean = (mst530101_TenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_TENPO set ");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
//		{
//			befKanma = true;
//			sb.append(" hojin_cd = ");
//			sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
//		}
//		↓↓2006.06.22 zhujl カスタマイズ修正↓↓		
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			befKanma = true;
			//sb.append(" hojin_cd = ");
			//sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
			sb.append(" hojin_cd = ''");		
		}
//	    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getTenpokaisoTiikiCd() != null && bean.getTenpokaisoTiikiCd().trim().length() != 0 )
		{
//			↓↓ＤＢバージョンアップによる変更（2005.05.19）
			if( befKanma ) sb.append(","); else befKanma = true;
//			befKanma = true;
//			↑↑ＤＢバージョンアップによる変更（2005.05.19）			
			sb.append(" tenpokaiso_tiiki_cd = ");
			sb.append("'" + conv.convertString( bean.getTenpokaisoTiikiCd() ) + "'");
		}
		if( bean.getTenpokaisoAreaCd() != null )// && bean.getTenpokaisoAreaCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpokaiso_area_cd = ");
			sb.append("'" + conv.convertString( bean.getTenpokaisoAreaCd() ) + "'");
		}
		if( bean.getTenpokaisoBlockCd() != null )//&& bean.getTenpokaisoBlockCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpokaiso_block_cd = ");
			sb.append("'" + conv.convertString( bean.getTenpokaisoBlockCd() ) + "'");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_na = ");
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_na = ");
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_rn = ");
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_rn = ");
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
		}
		if( bean.getTenpoTypeKb() != null && bean.getTenpoTypeKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_type_kb = ");
			sb.append("'" + conv.convertString( bean.getTenpoTypeKb() ) + "'");
		}
		if( bean.getTenpoKb() != null && bean.getTenpoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_kb = ");
			sb.append("'" + conv.convertString( bean.getTenpoKb() ) + "'");
		}
		if( bean.getKaitenDt() != null && bean.getKaitenDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kaiten_dt = ");
			sb.append("'" + conv.convertString( bean.getKaitenDt() ) + "'");
		}
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" heiten_dt = ");
		sb.append("'" + conv.convertString( bean.getHeitenDt() ) + "'");
		if( bean.getAddressKanjiNa() != null)// && bean.getAddressKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKanjiNa() ) + "'");
		}
		if( bean.getAddressKanaNa() != null )//&& bean.getAddressKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKanaNa() ) + "'");
		}
		if( bean.getAddress3Na() != null ) // && bean.getAddress3Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_3_na = ");
			sb.append("'" + conv.convertString( bean.getAddress3Na() ) + "'");
		}
		if( bean.getYubinCd() != null )//&& bean.getYubinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yubin_cd = ");
			sb.append("'" + conv.convertString( bean.getYubinCd() ) + "'");
		}
		if( bean.getTelCd() != null )//&& bean.getTelCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel_cd = ");
			sb.append("'" + conv.convertString( bean.getTelCd() ) + "'");
		}
		if( bean.getFaxCd() != null )//&& bean.getFaxCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax_cd = ");
			sb.append("'" + conv.convertString( bean.getFaxCd() ) + "'");
		}
		
//		↓↓2006.06.22 zhujl カスタマイズ修正↓↓
		if( bean.getPosKb() != null )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pos_kb = ");
			sb.append("'" + conv.convertString( bean.getPosKb() ) + "'");
		}
		if( bean.getBotenCd() != null )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" boten_cd = ");
			sb.append("'" + conv.convertString( bean.getBotenCd() ) + "'");
		}
		if( bean.getGyotaiKb() != null )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gyotai_kb = ");
			sb.append("'" + conv.convertString( bean.getGyotaiKb() ) + "'");
		}
//	    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑		
		
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" hachu_st_dt = ");
		sb.append("'" + conv.convertString( bean.getHachuStDt() ) + "'");
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" hachu_ed_dt = ");
		sb.append("'" + conv.convertString( bean.getHachuEdDt() ) + "'");
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" sndst_nehuda_dt = ");
		sb.append("'" + conv.convertString( bean.getSndstNehudaDt() ) + "'");
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" sndst_price_dt = ");
		sb.append("'" + conv.convertString( bean.getSndstPriceDt() ) + "'");
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" sndst_tag_dt = ");
		sb.append("'" + conv.convertString( bean.getSndstTagDt() ) + "'");
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" sndst_plu_dt = ");
		sb.append("'" + conv.convertString( bean.getSndstPluDt() ) + "'");
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" sndst_pop_dt = ");
		sb.append("'" + conv.convertString( bean.getSndstPopDt() ) + "'");
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" sndst_keiryoki_dt = ");
		sb.append("'" + conv.convertString( bean.getSndstKeiryokiDt() ) + "'");
//		if( bean.getHanku1KeiryokiType() != null && bean.getHanku1KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku1_keiryoki_type = ");
//			sb.append("'" + conv.convertString( bean.getHanku1KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku2KeiryokiType() != null && bean.getHanku2KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku2_keiryoki_type = ");
//			sb.append("'" + conv.convertString( bean.getHanku2KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku3KeiryokiType() != null && bean.getHanku3KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku3_keiryoki_type = ");
//			sb.append("'" + conv.convertString( bean.getHanku3KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku4KeiryokiType() != null && bean.getHanku4KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku4_keiryoki_type = ");
//			sb.append("'" + conv.convertString( bean.getHanku4KeiryokiType() ) + "'");
//		}
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
//	      ↓↓　移植(2006.05.11) ↓↓
//			try {
//		          sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
//		     } catch (SQLException e) {
//		          e.printStackTrace();
//		     }
//	      ↑↑　移植(2006.05.11) ↑↑
			sb.append(" '" + conv.convertString( bean.getUpdateTs() ) + "' ");
			
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

//		↓↓2006.06.22 zhujl カスタマイズ修正↓↓
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" tenpo_cd = ");
			//sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat(bean.getTenpoCd(), 6, "0", true) ) + "'");
		}
//	    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑		
		return sb.toString();
	}

	/**
	 * 更新用(削除)ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateDeleteSql( Object beanMst )
	{
		boolean befKanma = false;
		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst530101_TenpoBean bean = (mst530101_TenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_TENPO set ");
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" hojin_cd = ");
			sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）	

		if( bean.getTenpokaisoTiikiCd() != null && bean.getTenpokaisoTiikiCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" tenpokaiso_tiiki_cd = ");
			sb.append("'" + conv.convertString( bean.getTenpokaisoTiikiCd() ) + "'");
		}
		if( bean.getTenpokaisoAreaCd() != null && bean.getTenpokaisoAreaCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpokaiso_area_cd = ");
			sb.append("'" + conv.convertString( bean.getTenpokaisoAreaCd() ) + "'");
		}
		if( bean.getTenpokaisoBlockCd() != null && bean.getTenpokaisoBlockCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpokaiso_block_cd = ");
			sb.append("'" + conv.convertString( bean.getTenpokaisoBlockCd() ) + "'");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_na = ");
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_na = ");
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_rn = ");
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_rn = ");
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
		}
		if( bean.getTenpoTypeKb() != null && bean.getTenpoTypeKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_type_kb = ");
			sb.append("'" + conv.convertString( bean.getTenpoTypeKb() ) + "'");
		}
		if( bean.getTenpoKb() != null && bean.getTenpoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_kb = ");
			sb.append("'" + conv.convertString( bean.getTenpoKb() ) + "'");
		}
		if( bean.getKaitenDt() != null && bean.getKaitenDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kaiten_dt = ");
			sb.append("'" + conv.convertString( bean.getKaitenDt() ) + "'");
		}
		if( bean.getHeitenDt() != null && bean.getHeitenDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" heiten_dt = ");
			sb.append("'" + conv.convertString( bean.getHeitenDt() ) + "'");
		}
		if( bean.getAddressKanjiNa() != null && bean.getAddressKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKanjiNa() ) + "'");
		}
		if( bean.getAddressKanaNa() != null && bean.getAddressKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKanaNa() ) + "'");
		}
		if( bean.getAddress3Na() != null && bean.getAddress3Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_3_na = ");
			sb.append("'" + conv.convertString( bean.getAddress3Na() ) + "'");
		}
		if( bean.getYubinCd() != null && bean.getYubinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yubin_cd = ");
			sb.append("'" + conv.convertString( bean.getYubinCd() ) + "'");
		}
		if( bean.getTelCd() != null && bean.getTelCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel_cd = ");
			sb.append("'" + conv.convertString( bean.getTelCd() ) + "'");
		}
		if( bean.getFaxCd() != null && bean.getFaxCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax_cd = ");
			sb.append("'" + conv.convertString( bean.getFaxCd() ) + "'");
		}
		if( bean.getHachuStDt() != null && bean.getHachuStDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_st_dt = ");
			sb.append("'" + conv.convertString( bean.getHachuStDt() ) + "'");
		}
		if( bean.getHachuEdDt() != null && bean.getHachuEdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_ed_dt = ");
			sb.append("'" + conv.convertString( bean.getHachuEdDt() ) + "'");
		}
		if( bean.getSndstNehudaDt() != null && bean.getSndstNehudaDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_nehuda_dt = ");
			sb.append("'" + conv.convertString( bean.getSndstNehudaDt() ) + "'");
		}
		if( bean.getSndstPriceDt() != null && bean.getSndstPriceDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_price_dt = ");
			sb.append("'" + conv.convertString( bean.getSndstPriceDt() ) + "'");
		}
		if( bean.getSndstTagDt() != null && bean.getSndstTagDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_tag_dt = ");
			sb.append("'" + conv.convertString( bean.getSndstTagDt() ) + "'");
		}
		if( bean.getSndstPluDt() != null && bean.getSndstPluDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_plu_dt = ");
			sb.append("'" + conv.convertString( bean.getSndstPluDt() ) + "'");
		}
		if( bean.getSndstPopDt() != null && bean.getSndstPopDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_pop_dt = ");
			sb.append("'" + conv.convertString( bean.getSndstPopDt() ) + "'");
		}
		if( bean.getSndstKeiryokiDt() != null && bean.getSndstKeiryokiDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sndst_keiryoki_dt = ");
			sb.append("'" + conv.convertString( bean.getSndstKeiryokiDt() ) + "'");
		}
//		if( bean.getHanku1KeiryokiType() != null && bean.getHanku1KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku1_keiryoki_type = ");
//			sb.append("'" + conv.convertString( bean.getHanku1KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku2KeiryokiType() != null && bean.getHanku2KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku2_keiryoki_type = ");
//			sb.append("'" + conv.convertString( bean.getHanku2KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku3KeiryokiType() != null && bean.getHanku3KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku3_keiryoki_type = ");
//			sb.append("'" + conv.convertString( bean.getHanku3KeiryokiType() ) + "'");
//		}
//		if( bean.getHanku4KeiryokiType() != null && bean.getHanku4KeiryokiType().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hanku4_keiryoki_type = ");
//			sb.append("'" + conv.convertString( bean.getHanku4KeiryokiType() ) + "'");
//		}
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
//	      ↓↓　移植(2006.05.11) ↓↓
			try {
		          sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		     } catch (SQLException e) {
		          e.printStackTrace();
		     }
//	      ↑↑　移植(2006.05.11) ↑↑
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


		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" tenpo_cd = ");
//			↓↓2006.06.22 zhujl カスタマイズ修正↓↓
			//sb.append("'" + conv.convertWhereString( bean.getTenpoCd()) + "'");
			sb.append("'" + conv.convertWhereString(StringUtility.charFormat(bean.getTenpoCd(), 6, "0", true) ) + "'");
//		    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑	
			
		}
		return sb.toString();
	}
	
//	↓↓2006.06.22 zhujl カスタマイズ修正↓↓
	/**
	 * 更新用(削除)ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @param String tebleName
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateDeleteSql( Object beanMst, String tebleName)
	{
		boolean befKanma = false;
		boolean whereAnd = false;
		mst530101_TenpoBean bean = (mst530101_TenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(tebleName);
		sb.append(" set ");
		
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			try {
		          sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		     } catch (SQLException e) {
		          e.printStackTrace();
		     }
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


		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString(StringUtility.charFormat(bean.getTenpoCd(), 6, "0", true) ) + "'");
		}
		
		return sb.toString();
	}
//    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql( Object beanMst )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst530101_TenpoBean bean = (mst530101_TenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_TENPO where ");
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
