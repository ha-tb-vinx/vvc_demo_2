/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/22)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst240101_AsortmentDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst240101_AsortmentDM()
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
		mst240101_AsortmentBean bean = new mst240101_AsortmentBean();
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setColorCd( rest.getString("color_cd") );
		bean.setColorRm( rest.getString("color_rm") );
		bean.setSizeCd( rest.getString("size_cd") );
		bean.setSizeRm( rest.getString("size_rm") );
		bean.setPattern1Qt( rest.getString("pattern_1_qt") );
		bean.setPattern2Qt( rest.getString("pattern_2_qt") );
		bean.setPattern3Qt( rest.getString("pattern_3_qt") );
		bean.setPattern4Qt( rest.getString("pattern_4_qt") );
		bean.setPattern5Qt( rest.getString("pattern_5_qt") );
//		↓↓ＤＢバージョンアップによる変更（2005.05.25）
//		bean.setNehudaQt( rest.getString("nehuda_qt") );
//		↑↑ＤＢバージョンアップによる変更（2005.05.25）
		bean.setTagHakoQt( rest.getString("tag_hako_qt") );
		bean.setTotalTagHakoQt( rest.getString("total_tag_hako_qt") );
//		↓↓ＤＢバージョンアップによる変更（2005.05.20）
		bean.setHachuTeisiKb( rest.getString("hachu_teisi_kb") );
//		↑↑ＤＢバージョンアップによる変更（2005.05.20）
		bean.setSaihakoFg( rest.getString("saihako_fg") );
		bean.setTorihikisakiRendobiDt( rest.getString("torihikisaki_rendobi_dt") );
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
//		sb.append("select ");
//		sb.append("R_Assortment.syohin_cd ");
//		sb.append(", ");
//		sb.append("R_Assortment.color_cd ");
//		sb.append(", ");
//		sb.append("R_Assortment.size_cd ");
//		sb.append(", ");
//		sb.append(" '" + ((String)map.get("ct_syohincd")).substring(0,4) + "' + R_Assortment.size_cd as size_check ");
//		sb.append(", ");
//		sb.append("R_Assortment.pattern_1_qt ");
//		sb.append(", ");
//		sb.append("R_Assortment.pattern_2_qt ");
//		sb.append(", ");
//		sb.append("R_Assortment.pattern_3_qt ");
//		sb.append(", ");
//		sb.append("R_Assortment.pattern_4_qt ");
//		sb.append(", ");
//		sb.append("R_Assortment.pattern_5_qt ");
//		sb.append(", ");
////		↓↓ＤＢバージョンアップによる変更（2005.05.25）
////		sb.append("R_Assortment.nehuda_qt ");
////		sb.append(", ");
////		↑↑ＤＢバージョンアップによる変更（2005.05.25）
//		sb.append("R_Assortment.tag_hako_qt ");
//		sb.append(", ");
//		sb.append("R_Assortment.total_tag_hako_qt ");
//		sb.append(", ");
////		↓↓ＤＢバージョンアップによる変更（2005.05.20）
//		sb.append("R_Assortment.hachu_teisi_kb ");
//		sb.append(", ");
////		↑↑ＤＢバージョンアップによる変更（2005.05.20）
//		sb.append("R_Assortment.saihako_fg ");
//		sb.append(", ");
//		sb.append("R_Assortment.torihikisaki_rendobi_dt ");
//		sb.append(", ");
//		sb.append("R_Assortment.insert_ts ");
//		sb.append(", ");
//		sb.append("R_Assortment.insert_user_id ");
//		sb.append(", ");
//		sb.append("R_Assortment.update_ts ");
//		sb.append(", ");
//		sb.append("R_Assortment.update_user_id ");
//		sb.append(", ");
//		sb.append("R_Assortment.delete_fg ");
//		sb.append(", ");
//		sb.append("R_NAMECTF0090.KANJI_RN as color_rm ");
//		sb.append(", ");
//		sb.append("R_NAMECTF0100.KANJI_RN as size_rm ");
////		↓↓ＤＢバージョンアップによる変更（2005.05.20）
////		sb.append("from R_Assortment LEFT OUTER JOIN (SELECT * FROM R_NAMECTF WHERE SYUBETU_NO_CD = '").append(mst000101_ConstDictionary.COLOR).append("') R_NAMECTF0090 ON ");
//		sb.append("from R_Assortment INNER JOIN (SELECT * FROM R_NAMECTF WHERE SYUBETU_NO_CD = '").append(mst000101_ConstDictionary.COLOR).append("') R_NAMECTF0090 ON ");
////		↑↑ＤＢバージョンアップによる変更（2005.05.20）
//		sb.append("     R_Assortment.color_cd = R_NAMECTF0090.code_cd ");
////		↓↓ＤＢバージョンアップによる変更（2005.05.20）
////		sb.append("     LEFT OUTER JOIN (SELECT * FROM R_NAMECTF WHERE SYUBETU_NO_CD = '").append(mst000101_ConstDictionary.SIZE).append("') R_NAMECTF0100 ON ");
//		sb.append("     INNER JOIN (SELECT * FROM R_NAMECTF WHERE SYUBETU_NO_CD = '").append(mst000101_ConstDictionary.SIZE).append("') R_NAMECTF0100 ON ");
////		↑↑ＤＢバージョンアップによる変更（2005.05.20）
////		sb.append("     R_Assortment.size_cd = R_NAMECTF0100.code_cd ");
//		sb.append("     R_Assortment.size_check = R_NAMECTF0100.code_cd ");
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		sb.append("select ");
		sb.append("a.syohin_cd ");
		sb.append(", ");
		sb.append("a.color_cd ");
		sb.append(", ");
		sb.append("a.size_cd ");
		sb.append(", ");
		sb.append("a.size_check ");
		sb.append(", ");
		sb.append("a.pattern_1_qt ");
		sb.append(", ");
		sb.append("a.pattern_2_qt ");
		sb.append(", ");
		sb.append("a.pattern_3_qt ");
		sb.append(", ");
		sb.append("a.pattern_4_qt ");
		sb.append(", ");
		sb.append("a.pattern_5_qt ");
		sb.append(", ");
		sb.append("a.tag_hako_qt ");
		sb.append(", ");
		sb.append("a.total_tag_hako_qt ");
		sb.append(", ");
		sb.append("a.hachu_teisi_kb ");
		sb.append(", ");
		sb.append("a.saihako_fg ");
		sb.append(", ");
		sb.append("a.torihikisaki_rendobi_dt ");
		sb.append(", ");
		sb.append("a.insert_ts ");
		sb.append(", ");
		sb.append("a.insert_user_id ");
		sb.append(", ");
		sb.append("a.update_ts ");
		sb.append(", ");
		sb.append("a.update_user_id ");
		sb.append(", ");
		sb.append("a.delete_fg ");
		sb.append(", ");
		sb.append("R_NAMECTF0090.code_cd as color_cd_ctf ");
		sb.append(", ");
		sb.append("R_NAMECTF0090.KANJI_RN as color_rm ");
		sb.append(", ");
		sb.append("R_NAMECTF0100.code_cd as size_cd_ctf ");
		sb.append(", ");
		sb.append("R_NAMECTF0100.KANJI_RN as size_rm ");
		sb.append("from ");
		sb.append("  (select /*+ ALL_ROWS */ ");
		sb.append("     R_Assortment.syohin_cd ");
		sb.append(", ");
		sb.append("		R_Assortment.color_cd ");
		sb.append(", ");
		sb.append("		R_Assortment.size_cd ");
		sb.append(", ");
		sb.append(" '" + ((String)map.get("ct_syohincd")).substring(0,4) + "' || R_Assortment.size_cd as size_check ");
		sb.append(", ");
		sb.append("		R_Assortment.pattern_1_qt ");
		sb.append(", ");
		sb.append("		R_Assortment.pattern_2_qt ");
		sb.append(", ");
		sb.append("		R_Assortment.pattern_3_qt ");
		sb.append(", ");
		sb.append("		R_Assortment.pattern_4_qt ");
		sb.append(", ");
		sb.append("		R_Assortment.pattern_5_qt ");
		sb.append(", ");
		sb.append("		R_Assortment.tag_hako_qt ");
		sb.append(", ");
		sb.append("		R_Assortment.total_tag_hako_qt ");
		sb.append(", ");
		sb.append("		R_Assortment.hachu_teisi_kb ");
		sb.append(", ");
		sb.append("		R_Assortment.saihako_fg ");
		sb.append(", ");
		sb.append("		R_Assortment.torihikisaki_rendobi_dt ");
		sb.append(", ");
		sb.append("		R_Assortment.insert_ts ");
		sb.append(", ");
		sb.append("		R_Assortment.insert_user_id ");
		sb.append(", ");
		sb.append("		R_Assortment.update_ts ");
		sb.append(", ");
		sb.append("		R_Assortment.update_user_id ");
		sb.append(", ");
		sb.append("		R_Assortment.delete_fg ");
		sb.append("   from ");
		sb.append("     R_Assortment ");
		sb.append("   where ");
		sb.append("     R_Assortment.syohin_cd = '" + map.get("ct_syohincd") + "' ");
//BUGNO-S070 2005.07.11 Sirius START
		//sb.append("   and R_Assortment.delete_fg = '0' ");
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append("and R_Assortment.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		}
//BUGNO-S070 2005.07.11 Sirius END
		sb.append("   ) a ");
		sb.append("	    INNER JOIN R_NAMECTF R_NAMECTF0090 ON ");
		sb.append("     	R_NAMECTF0090.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal) + "' and R_NAMECTF0090.code_cd = a.color_cd ");
		sb.append("     INNER JOIN R_NAMECTF R_NAMECTF0100 ON ");
		sb.append("     	R_NAMECTF0100.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal) + "' and R_NAMECTF0100.code_cd = a.size_check ");


//////↓↓　以下、R_Assortment. →　a.　に変更（2005.07.04）↓↓//////

		// syohin_cd に対するWHERE区
		if( map.get("syohin_cd_bef") != null && ((String)map.get("syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.syohin_cd >= '" + conv.convertWhereString( (String)map.get("syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft") != null && ((String)map.get("syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.syohin_cd <= '" + conv.convertWhereString( (String)map.get("syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_like") != null && ((String)map.get("syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_bef_like") != null && ((String)map.get("syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft_like") != null && ((String)map.get("syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.syohin_cd like '" + conv.convertWhereString( (String)map.get("syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_in") != null && ((String)map.get("syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_not_in") != null && ((String)map.get("syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// color_cd に対するWHERE区
		if( map.get("color_cd_bef") != null && ((String)map.get("color_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.color_cd >= '" + conv.convertWhereString( (String)map.get("color_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_cd_aft") != null && ((String)map.get("color_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.color_cd <= '" + conv.convertWhereString( (String)map.get("color_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_cd") != null && ((String)map.get("color_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.color_cd = '" + conv.convertWhereString( (String)map.get("color_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_cd_like") != null && ((String)map.get("color_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.color_cd like '%" + conv.convertWhereString( (String)map.get("color_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("color_cd_bef_like") != null && ((String)map.get("color_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.color_cd like '%" + conv.convertWhereString( (String)map.get("color_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_cd_aft_like") != null && ((String)map.get("color_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.color_cd like '" + conv.convertWhereString( (String)map.get("color_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("color_cd_in") != null && ((String)map.get("color_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.color_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("color_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("color_cd_not_in") != null && ((String)map.get("color_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.color_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("color_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// size_cd に対するWHERE区
		if( map.get("size_cd_bef") != null && ((String)map.get("size_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.size_cd >= '" + conv.convertWhereString( (String)map.get("size_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_cd_aft") != null && ((String)map.get("size_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.size_cd <= '" + conv.convertWhereString( (String)map.get("size_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_cd") != null && ((String)map.get("size_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.size_cd = '" + conv.convertWhereString( (String)map.get("size_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_cd_like") != null && ((String)map.get("size_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.size_cd like '%" + conv.convertWhereString( (String)map.get("size_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("size_cd_bef_like") != null && ((String)map.get("size_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.size_cd like '%" + conv.convertWhereString( (String)map.get("size_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_cd_aft_like") != null && ((String)map.get("size_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.size_cd like '" + conv.convertWhereString( (String)map.get("size_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("size_cd_in") != null && ((String)map.get("size_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.size_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("size_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("size_cd_not_in") != null && ((String)map.get("size_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.size_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("size_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pattern_1_qt に対するWHERE区
		if( map.get("pattern_1_qt_bef") != null && ((String)map.get("pattern_1_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_1_qt >= '" + conv.convertWhereString( (String)map.get("pattern_1_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_1_qt_aft") != null && ((String)map.get("pattern_1_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_1_qt <= '" + conv.convertWhereString( (String)map.get("pattern_1_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_1_qt") != null && ((String)map.get("pattern_1_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_1_qt = '" + conv.convertWhereString( (String)map.get("pattern_1_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_1_qt_like") != null && ((String)map.get("pattern_1_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_1_qt like '%" + conv.convertWhereString( (String)map.get("pattern_1_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_1_qt_bef_like") != null && ((String)map.get("pattern_1_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_1_qt like '%" + conv.convertWhereString( (String)map.get("pattern_1_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_1_qt_aft_like") != null && ((String)map.get("pattern_1_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pattern_1_qt like '" + conv.convertWhereString( (String)map.get("pattern_1_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_1_qt_in") != null && ((String)map.get("pattern_1_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_1_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_1_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pattern_1_qt_not_in") != null && ((String)map.get("pattern_1_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_1_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_1_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pattern_2_qt に対するWHERE区
		if( map.get("pattern_2_qt_bef") != null && ((String)map.get("pattern_2_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_2_qt >= '" + conv.convertWhereString( (String)map.get("pattern_2_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_2_qt_aft") != null && ((String)map.get("pattern_2_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_2_qt <= '" + conv.convertWhereString( (String)map.get("pattern_2_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_2_qt") != null && ((String)map.get("pattern_2_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_2_qt = '" + conv.convertWhereString( (String)map.get("pattern_2_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_2_qt_like") != null && ((String)map.get("pattern_2_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_2_qt like '%" + conv.convertWhereString( (String)map.get("pattern_2_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_2_qt_bef_like") != null && ((String)map.get("pattern_2_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_2_qt like '%" + conv.convertWhereString( (String)map.get("pattern_2_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_2_qt_aft_like") != null && ((String)map.get("pattern_2_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_2_qt like '" + conv.convertWhereString( (String)map.get("pattern_2_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_2_qt_in") != null && ((String)map.get("pattern_2_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_2_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_2_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pattern_2_qt_not_in") != null && ((String)map.get("pattern_2_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_2_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_2_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pattern_3_qt に対するWHERE区
		if( map.get("pattern_3_qt_bef") != null && ((String)map.get("pattern_3_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_3_qt >= '" + conv.convertWhereString( (String)map.get("pattern_3_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_3_qt_aft") != null && ((String)map.get("pattern_3_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_3_qt <= '" + conv.convertWhereString( (String)map.get("pattern_3_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_3_qt") != null && ((String)map.get("pattern_3_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_3_qt = '" + conv.convertWhereString( (String)map.get("pattern_3_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_3_qt_like") != null && ((String)map.get("pattern_3_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_3_qt like '%" + conv.convertWhereString( (String)map.get("pattern_3_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_3_qt_bef_like") != null && ((String)map.get("pattern_3_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_3_qt like '%" + conv.convertWhereString( (String)map.get("pattern_3_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_3_qt_aft_like") != null && ((String)map.get("pattern_3_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_3_qt like '" + conv.convertWhereString( (String)map.get("pattern_3_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_3_qt_in") != null && ((String)map.get("pattern_3_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_3_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_3_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pattern_3_qt_not_in") != null && ((String)map.get("pattern_3_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_3_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_3_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pattern_4_qt に対するWHERE区
		if( map.get("pattern_4_qt_bef") != null && ((String)map.get("pattern_4_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_4_qt >= '" + conv.convertWhereString( (String)map.get("pattern_4_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_4_qt_aft") != null && ((String)map.get("pattern_4_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_4_qt <= '" + conv.convertWhereString( (String)map.get("pattern_4_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_4_qt") != null && ((String)map.get("pattern_4_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_4_qt = '" + conv.convertWhereString( (String)map.get("pattern_4_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_4_qt_like") != null && ((String)map.get("pattern_4_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_4_qt like '%" + conv.convertWhereString( (String)map.get("pattern_4_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_4_qt_bef_like") != null && ((String)map.get("pattern_4_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_4_qt like '%" + conv.convertWhereString( (String)map.get("pattern_4_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_4_qt_aft_like") != null && ((String)map.get("pattern_4_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_4_qt like '" + conv.convertWhereString( (String)map.get("pattern_4_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_4_qt_in") != null && ((String)map.get("pattern_4_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_4_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_4_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pattern_4_qt_not_in") != null && ((String)map.get("pattern_4_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_4_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_4_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pattern_5_qt に対するWHERE区
		if( map.get("pattern_5_qt_bef") != null && ((String)map.get("pattern_5_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_5_qt >= '" + conv.convertWhereString( (String)map.get("pattern_5_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_5_qt_aft") != null && ((String)map.get("pattern_5_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_5_qt <= '" + conv.convertWhereString( (String)map.get("pattern_5_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_5_qt") != null && ((String)map.get("pattern_5_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_5_qt = '" + conv.convertWhereString( (String)map.get("pattern_5_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_5_qt_like") != null && ((String)map.get("pattern_5_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_5_qt like '%" + conv.convertWhereString( (String)map.get("pattern_5_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_5_qt_bef_like") != null && ((String)map.get("pattern_5_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_5_qt like '%" + conv.convertWhereString( (String)map.get("pattern_5_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pattern_5_qt_aft_like") != null && ((String)map.get("pattern_5_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_5_qt like '" + conv.convertWhereString( (String)map.get("pattern_5_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pattern_5_qt_in") != null && ((String)map.get("pattern_5_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_5_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_5_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pattern_5_qt_not_in") != null && ((String)map.get("pattern_5_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.pattern_5_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pattern_5_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


//		↓↓ＤＢバージョンアップによる変更（2005.05.25）
		// nehuda_qt に対するWHERE区
//		if( map.get("nehuda_qt_bef") != null && ((String)map.get("nehuda_qt_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("a.nehuda_qt >= '" + conv.convertWhereString( (String)map.get("nehuda_qt_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("nehuda_qt_aft") != null && ((String)map.get("nehuda_qt_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("a.nehuda_qt <= '" + conv.convertWhereString( (String)map.get("nehuda_qt_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("nehuda_qt") != null && ((String)map.get("nehuda_qt")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("a.nehuda_qt = '" + conv.convertWhereString( (String)map.get("nehuda_qt") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("nehuda_qt_like") != null && ((String)map.get("nehuda_qt_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("a.nehuda_qt like '%" + conv.convertWhereString( (String)map.get("nehuda_qt_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("nehuda_qt_bef_like") != null && ((String)map.get("nehuda_qt_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("a.nehuda_qt like '%" + conv.convertWhereString( (String)map.get("nehuda_qt_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("nehuda_qt_aft_like") != null && ((String)map.get("nehuda_qt_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("a.nehuda_qt like '" + conv.convertWhereString( (String)map.get("nehuda_qt_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("nehuda_qt_in") != null && ((String)map.get("nehuda_qt_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("a.nehuda_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nehuda_qt_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("nehuda_qt_not_in") != null && ((String)map.get("nehuda_qt_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("a.nehuda_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nehuda_qt_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.25）

		// tag_hako_qt に対するWHERE区
		if( map.get("tag_hako_qt_bef") != null && ((String)map.get("tag_hako_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.tag_hako_qt >= '" + conv.convertWhereString( (String)map.get("tag_hako_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tag_hako_qt_aft") != null && ((String)map.get("tag_hako_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.tag_hako_qt <= '" + conv.convertWhereString( (String)map.get("tag_hako_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tag_hako_qt") != null && ((String)map.get("tag_hako_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.tag_hako_qt = '" + conv.convertWhereString( (String)map.get("tag_hako_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tag_hako_qt_like") != null && ((String)map.get("tag_hako_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.tag_hako_qt like '%" + conv.convertWhereString( (String)map.get("tag_hako_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tag_hako_qt_bef_like") != null && ((String)map.get("tag_hako_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.tag_hako_qt like '%" + conv.convertWhereString( (String)map.get("tag_hako_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tag_hako_qt_aft_like") != null && ((String)map.get("tag_hako_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.tag_hako_qt like '" + conv.convertWhereString( (String)map.get("tag_hako_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tag_hako_qt_in") != null && ((String)map.get("tag_hako_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.tag_hako_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tag_hako_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tag_hako_qt_not_in") != null && ((String)map.get("tag_hako_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.tag_hako_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tag_hako_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// total_tag_hako_qt に対するWHERE区
		if( map.get("total_tag_hako_qt_bef") != null && ((String)map.get("total_tag_hako_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.total_tag_hako_qt >= '" + conv.convertWhereString( (String)map.get("total_tag_hako_qt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("total_tag_hako_qt_aft") != null && ((String)map.get("total_tag_hako_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.total_tag_hako_qt <= '" + conv.convertWhereString( (String)map.get("total_tag_hako_qt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("total_tag_hako_qt") != null && ((String)map.get("total_tag_hako_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.total_tag_hako_qt = '" + conv.convertWhereString( (String)map.get("total_tag_hako_qt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("total_tag_hako_qt_like") != null && ((String)map.get("total_tag_hako_qt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.total_tag_hako_qt like '%" + conv.convertWhereString( (String)map.get("total_tag_hako_qt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("total_tag_hako_qt_bef_like") != null && ((String)map.get("total_tag_hako_qt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.total_tag_hako_qt like '%" + conv.convertWhereString( (String)map.get("total_tag_hako_qt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("total_tag_hako_qt_aft_like") != null && ((String)map.get("total_tag_hako_qt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.total_tag_hako_qt like '" + conv.convertWhereString( (String)map.get("total_tag_hako_qt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("total_tag_hako_qt_in") != null && ((String)map.get("total_tag_hako_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.total_tag_hako_qt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("total_tag_hako_qt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("total_tag_hako_qt_not_in") != null && ((String)map.get("total_tag_hako_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.total_tag_hako_qt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("total_tag_hako_qt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


//		↓↓ＤＢバージョンアップによる変更（2005.05.20）
		// hachu_teisi_kb に対するWHERE区
		if( map.get("hachu_teisi_kb_bef") != null && ((String)map.get("hachu_teisi_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.hachu_teisi_kb >= '" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_aft") != null && ((String)map.get("hachu_teisi_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.hachu_teisi_kb <= '" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb") != null && ((String)map.get("hachu_teisi_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.hachu_teisi_kb = '" + conv.convertWhereString( (String)map.get("hachu_teisi_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_like") != null && ((String)map.get("hachu_teisi_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.hachu_teisi_kb like '%" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_bef_like") != null && ((String)map.get("hachu_teisi_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.hachu_teisi_kb like '%" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_aft_like") != null && ((String)map.get("hachu_teisi_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.hachu_teisi_kb like '" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_in") != null && ((String)map.get("hachu_teisi_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.hachu_teisi_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_teisi_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_not_in") != null && ((String)map.get("hachu_teisi_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.hachu_teisi_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_teisi_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.20）


		// saihako_fg に対するWHERE区
		if( map.get("saihako_fg_bef") != null && ((String)map.get("saihako_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.saihako_fg >= '" + conv.convertWhereString( (String)map.get("saihako_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("saihako_fg_aft") != null && ((String)map.get("saihako_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.saihako_fg <= '" + conv.convertWhereString( (String)map.get("saihako_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("saihako_fg") != null && ((String)map.get("saihako_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.saihako_fg = '" + conv.convertWhereString( (String)map.get("saihako_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("saihako_fg_like") != null && ((String)map.get("saihako_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.saihako_fg like '%" + conv.convertWhereString( (String)map.get("saihako_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("saihako_fg_bef_like") != null && ((String)map.get("saihako_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.saihako_fg like '%" + conv.convertWhereString( (String)map.get("saihako_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("saihako_fg_aft_like") != null && ((String)map.get("saihako_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.saihako_fg like '" + conv.convertWhereString( (String)map.get("saihako_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("saihako_fg_in") != null && ((String)map.get("saihako_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.saihako_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("saihako_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("saihako_fg_not_in") != null && ((String)map.get("saihako_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.saihako_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("saihako_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// torihikisaki_rendobi_dt に対するWHERE区
		if( map.get("torihikisaki_rendobi_dt_bef") != null && ((String)map.get("torihikisaki_rendobi_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.torihikisaki_rendobi_dt >= '" + conv.convertWhereString( (String)map.get("torihikisaki_rendobi_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_rendobi_dt_aft") != null && ((String)map.get("torihikisaki_rendobi_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.torihikisaki_rendobi_dt <= '" + conv.convertWhereString( (String)map.get("torihikisaki_rendobi_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_rendobi_dt") != null && ((String)map.get("torihikisaki_rendobi_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.torihikisaki_rendobi_dt = '" + conv.convertWhereString( (String)map.get("torihikisaki_rendobi_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_rendobi_dt_like") != null && ((String)map.get("torihikisaki_rendobi_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.torihikisaki_rendobi_dt like '%" + conv.convertWhereString( (String)map.get("torihikisaki_rendobi_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_rendobi_dt_bef_like") != null && ((String)map.get("torihikisaki_rendobi_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.torihikisaki_rendobi_dt like '%" + conv.convertWhereString( (String)map.get("torihikisaki_rendobi_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_rendobi_dt_aft_like") != null && ((String)map.get("torihikisaki_rendobi_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.torihikisaki_rendobi_dt like '" + conv.convertWhereString( (String)map.get("torihikisaki_rendobi_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_rendobi_dt_in") != null && ((String)map.get("torihikisaki_rendobi_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.torihikisaki_rendobi_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_rendobi_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_rendobi_dt_not_in") != null && ((String)map.get("torihikisaki_rendobi_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.torihikisaki_rendobi_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_rendobi_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_ts に対するWHERE区
		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_user_id に対するWHERE区
		if( map.get("insert_user_id_bef") != null && ((String)map.get("insert_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_user_id >= '" + conv.convertWhereString( (String)map.get("insert_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft") != null && ((String)map.get("insert_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_user_id <= '" + conv.convertWhereString( (String)map.get("insert_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_like") != null && ((String)map.get("insert_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_bef_like") != null && ((String)map.get("insert_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft_like") != null && ((String)map.get("insert_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_user_id like '" + conv.convertWhereString( (String)map.get("insert_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_in") != null && ((String)map.get("insert_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_not_in") != null && ((String)map.get("insert_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.insert_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_ts に対するWHERE区
		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_user_id に対するWHERE区
		if( map.get("update_user_id_bef") != null && ((String)map.get("update_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.update_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// delete_fg に対するWHERE区
		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("a.delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("a.syohin_cd");
		sb.append(",");
		sb.append("a.size_cd");
		sb.append(",");
		sb.append("a.color_cd");
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
		mst240101_AsortmentBean bean = (mst240101_AsortmentBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_Assortment (");
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getColorCd() != null && bean.getColorCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" color_cd");
		}
		if( bean.getSizeCd() != null && bean.getSizeCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" size_cd");
		}
		if( bean.getPattern1Qt() != null && bean.getPattern1Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_1_qt");
		}
		if( bean.getPattern2Qt() != null && bean.getPattern2Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_2_qt");
		}
		if( bean.getPattern3Qt() != null && bean.getPattern3Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_3_qt");
		}
		if( bean.getPattern4Qt() != null && bean.getPattern4Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_4_qt");
		}
		if( bean.getPattern5Qt() != null && bean.getPattern5Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_5_qt");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.25）
//		if( bean.getNehudaQt() != null && bean.getNehudaQt().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" nehuda_qt");
//		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.25）
		if( bean.getTagHakoQt() != null && bean.getTagHakoQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tag_hako_qt");
		}
		if( bean.getTotalTagHakoQt() != null && bean.getTotalTagHakoQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" total_tag_hako_qt");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.20）
		if( bean.getHachuTeisiKb() != null && bean.getHachuTeisiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_teisi_kb");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.20）
		if( bean.getSaihakoFg() != null && bean.getSaihakoFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" saihako_fg");
		}
		if( bean.getTorihikisakiRendobiDt() != null && bean.getTorihikisakiRendobiDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" torihikisaki_rendobi_dt");
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


		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getColorCd() != null && bean.getColorCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getColorCd() ) + "'");
		}
		if( bean.getSizeCd() != null && bean.getSizeCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSizeCd() ) + "'");
		}
		if( bean.getPattern1Qt() != null && bean.getPattern1Qt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPattern1Qt() ) + "'");
		}
		if( bean.getPattern2Qt() != null && bean.getPattern2Qt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPattern2Qt() ) + "'");
		}
		if( bean.getPattern3Qt() != null && bean.getPattern3Qt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPattern3Qt() ) + "'");
		}
		if( bean.getPattern4Qt() != null && bean.getPattern4Qt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPattern4Qt() ) + "'");
		}
		if( bean.getPattern5Qt() != null && bean.getPattern5Qt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPattern5Qt() ) + "'");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.25）
//		if( bean.getNehudaQt() != null && bean.getNehudaQt().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getNehudaQt() ) + "'");
//		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.25）
		if( bean.getTagHakoQt() != null && bean.getTagHakoQt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTagHakoQt() ) + "'");
		}
		if( bean.getTotalTagHakoQt() != null && bean.getTotalTagHakoQt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTotalTagHakoQt() ) + "'");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.20）
		if( bean.getHachuTeisiKb() != null && bean.getHachuTeisiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuTeisiKb() ) + "'");
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.20）
		if( bean.getSaihakoFg() != null && bean.getSaihakoFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSaihakoFg() ) + "'");
		}
		if( bean.getTorihikisakiRendobiDt() != null && bean.getTorihikisakiRendobiDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTorihikisakiRendobiDt() ) + "'");
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
		mst240101_AsortmentBean bean = (mst240101_AsortmentBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_Assortment set ");
		if( bean.getPattern1Qt() != null && bean.getPattern1Qt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" pattern_1_qt = ");
			sb.append("'" + conv.convertString( bean.getPattern1Qt() ) + "'");
		} else {
			befKanma = true;
			sb.append(" pattern_1_qt = null");
		}
		if( bean.getPattern2Qt() != null && bean.getPattern2Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_2_qt = ");
			sb.append("'" + conv.convertString( bean.getPattern2Qt() ) + "'");
		} else {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_2_qt = null");
		}
		if( bean.getPattern3Qt() != null && bean.getPattern3Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_3_qt = ");
			sb.append("'" + conv.convertString( bean.getPattern3Qt() ) + "'");
		} else {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_3_qt = null");
		}
		if( bean.getPattern4Qt() != null && bean.getPattern4Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_4_qt = ");
			sb.append("'" + conv.convertString( bean.getPattern4Qt() ) + "'");
		} else {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_4_qt = null");
		}
		if( bean.getPattern5Qt() != null && bean.getPattern5Qt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_5_qt = ");
			sb.append("'" + conv.convertString( bean.getPattern5Qt() ) + "'");
		} else {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pattern_5_qt = null");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.25）
//		if( bean.getNehudaQt() != null && bean.getNehudaQt().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" nehuda_qt = ");
//			sb.append("'" + conv.convertString( bean.getNehudaQt() ) + "'");
//		} else {
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" nehuda_qt = null");
//		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.25）
		if( bean.getTagHakoQt() != null && bean.getTagHakoQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tag_hako_qt = ");
			sb.append("'" + conv.convertString( bean.getTagHakoQt() ) + "'");
//BUGNO-S070 2005.07.11 Sirius START
		} else {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tag_hako_qt = null");
//BUGNO-S070 2005.07.11 Sirius END
		}
		if( bean.getTotalTagHakoQt() != null && bean.getTotalTagHakoQt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" total_tag_hako_qt = ");
			sb.append("'" + conv.convertString( bean.getTotalTagHakoQt() ) + "'");
		}
//		↓↓ＤＢバージョンアップによる変更（2005.05.20）
		if( bean.getHachuTeisiKb() != null && bean.getHachuTeisiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_teisi_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuTeisiKb() ) + "'");
//BUGNO-S070 2005.07.11 Sirius START
		} else {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_teisi_kb = null");
//BUGNO-S070 2005.07.11 Sirius END
		}
//		↑↑ＤＢバージョンアップによる変更（2005.05.20）
		if( bean.getSaihakoFg() != null && bean.getSaihakoFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" saihako_fg = ");
			sb.append("'" + conv.convertString( bean.getSaihakoFg() ) + "'");
		}
		if( bean.getTorihikisakiRendobiDt() != null && bean.getTorihikisakiRendobiDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" torihikisaki_rendobi_dt = ");
			sb.append("'" + conv.convertString( bean.getTorihikisakiRendobiDt() ) + "'");
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
//BUGNO-S074 2005.07.19 Sirius START
		else {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = null ");
		}
//BUGNO-S074 2005.07.19 Sirius END
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
//BUGNO-S074 2005.07.19 Sirius START
		else {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = null ");
	 	}
//BUGNO-S074 2005.07.19 Sirius END
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getColorCd() != null && bean.getColorCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" color_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getColorCd() ) + "'");
		}
		if( bean.getSizeCd() != null && bean.getSizeCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" size_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSizeCd() ) + "'");
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
		mst240101_AsortmentBean bean = (mst240101_AsortmentBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_Assortment where ");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" color_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getColorCd() ) + "'");
		sb.append(" AND");
		sb.append(" size_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSizeCd() ) + "'");
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
