package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator for SQL (2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2005/4/13 13:28:33)
 */
public class mst610101_TanTenSeigyoDM extends DataModule
{

	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst610101_TanTenSeigyoDM()
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
		mst610301_KeepMeisaiBean bean = new mst610301_KeepMeisaiBean();
//	    ↓↓2006.07.11 magp カスタマイズ修正↓↓
//		bean.setKanriKb( rest.getString("kanri_kb") );
//		bean.setKanriCd( rest.getString("kanri_cd") );
//		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
//		bean.setSGyosyuNa( rest.getString("s_gyosyu_na") );
//		bean.setHankuCd( rest.getString("hanku_cd") );
//		bean.setHankuNa( rest.getString("hanku_na") );
//		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
//		bean.setHinsyuNa( rest.getString("hinsyu_na") );
		bean.setUnitCd( rest.getString("unit_cd") );
		bean.setUnitNa( rest.getString("unit_na") );
//		↑↑2006.07.11 magp カスタマイズ修正↑↑
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoNa( rest.getString("tenpo_na") );
		bean.setSyohinAddtimeFg( rest.getString("syohin_addtime_fg") );
		bean.setSyohin_addtime_fg_csv( rest.getString("syohin_addtime_fg") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserNa( rest.getString("insert_user_na") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserNa( rest.getString("update_user_na") );
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

		StringBuffer sb = new StringBuffer();
		String BaseSql = new String();

//	    ↓↓2006.07.11 magp カスタマイズ修正↓↓
//		if (map.get("kanri_kb").equals(mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode())) {
//			//小業種時
//			BaseSql = getBaseSQL1(map);
//		} else if (map.get("kanri_kb").equals(mst000901_KanriKbDictionary.HANKU.getCode())) {
//			//販区時
//			BaseSql = getBaseSQL2(map);
//		} else {
//			//品種時
//			BaseSql = getBaseSQL3(map);
//		}
		BaseSql = getBaseSQL(map);
//		↑↑2006.07.11 magp カスタマイズ修正↑↑

		sb.append(BaseSql);
		return sb.toString();
	}

//    ↓↓2006.07.11 magp カスタマイズ修正↓↓
//	/**
//	 * 検索用ＳＱＬのベース部分の生成を行う。小業種抽出時
//	 * 渡されたMapを元にWHERE区を作成する。
//	 * @param map Map
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getBaseSQL1( Map map )
//	{
//
////		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//		StringBuffer sb = new StringBuffer();
//		boolean whereAnd = false;
//
//		sb.append(" SELECT ");
//		sb.append("     kanri_kb, ");
//		sb.append("     kanri_cd, ");
//
//		// 小業種コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd ) ");
//		sb.append("     END AS s_gyosyu_cd, ");
//
//		// 小業種名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("     END AS s_gyosyu_na, ");
//
//		// 販区コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_cd, ");
//
//		// 販区名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_na, ");
//
//		// 品種コード取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         kanri_cd  ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_cd, ");
//
//		// 品種名称取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' AND code_cd = kanri_cd) ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_na, ");
//
//		sb.append("     tenpo_cd, ");
//		sb.append("     CASE tenpo_cd WHEN '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' THEN ");
//		sb.append("     	'全店' ");
//		sb.append("     ELSE ");
//		sb.append("     	tenpo_na ");
//		sb.append("     END as tenpo_na, ");
//		sb.append("     syohin_addtime_fg, ");
//		sb.append("     insert_ts, ");
//		sb.append("     insert_user_na, ");
//		sb.append("     update_ts, ");
//		sb.append("     update_user_na, ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("     delete_fg, ");
//		sb.append("          CASE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       ' ' ");
//		sb.append("             END) ");
//		sb.append("		     WHEN ' ' THEN '0000' ELSE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       cast(null as char) ");
//		sb.append("             END) ");
//		sb.append("  		END as ord_hanku_cd , ");
//		sb.append("         CASE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			' ' ");
//		sb.append("     		END) ");
//		sb.append("		 	WHEN ' ' THEN '0000' ELSE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 cast(null as char) ");
//		sb.append("     		END) ");
//		sb.append("			END as ord_hinsyu_cd , ");
//		sb.append("  		CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN '0' ELSE kanri_kb END as ord_kanri_kb ");
//		sb.append("	FROM ( ");
//		sb.append("      SELECT ");
//		sb.append("     	 rt.kanri_kb, ");
//		sb.append("          rt.kanri_cd, ");
//		sb.append("          rt.tenpo_cd, ");
//		sb.append("     	 tp.kanji_rn AS tenpo_na, ");
//		sb.append("     	 rt.syohin_addtime_fg, ");
//		sb.append("     	 rt.insert_ts, ");
//		sb.append("     	 ss1.user_na AS insert_user_na, ");
//		sb.append("     	 rt.update_ts, ");
//		sb.append("     	 ss2.user_na AS update_user_na, ");
//		sb.append("     	 rt.delete_fg ");
//		sb.append(" 	 FROM ");
//		sb.append("     	 r_tanpinten_jidosakusei rt ");
//		sb.append("			 LEFT JOIN sys_sosasya ss1 ON rt.insert_user_id = ss1.user_id AND ss1.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append("			 LEFT JOIN sys_sosasya ss2 ON rt.update_user_id = ss2.user_id AND ss2.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     	 LEFT JOIN r_tenpo tp ON rt.tenpo_cd = tp.tenpo_cd ");
//		sb.append(" 	 WHERE ");
//		sb.append("     	 rt.kanri_kb = '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' ");
//		if( map.get("kanri_cd_from") != null && ((String)map.get("kanri_cd_from")).trim().length() > 0 )
//		{
//			sb.append("   AND ");
//			sb.append("      rt.kanri_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_from") ) + "'");
//		}
//
//		if( map.get("kanri_cd_to") != null && ((String)map.get("kanri_cd_to")).trim().length() > 0 )
//		{
//			sb.append("   AND ");
//			sb.append("      rt.kanri_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_to") ) + "'");
//		}
//		sb.append("       AND ");
//		sb.append("          rt.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		sb.append("		 ) TBL1 ");
//		sb.append(" UNION ");
//		sb.append(" SELECT ");
//		sb.append("     kanri_kb, ");
//		sb.append("     kanri_cd, ");
//
//		// 小業種コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd ) ");
//		sb.append("     END AS s_gyosyu_cd, ");
//
//		// 小業種名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("     END AS s_gyosyu_na, ");
//
//		// 販区コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_cd, ");
//
//		// 販区名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_na, ");
//
//		// 品種コード取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         kanri_cd  ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_cd, ");
//
//		// 品種名称取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' AND code_cd = kanri_cd) ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_na, ");
//
//		sb.append("     tenpo_cd, ");
//		sb.append("     CASE tenpo_cd WHEN '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' THEN ");
//		sb.append("     	'全店' ");
//		sb.append("     ELSE ");
//		sb.append("     	tenpo_na ");
//		sb.append("     END as tenpo_na, ");
//		sb.append("     syohin_addtime_fg, ");
//		sb.append("     insert_ts, ");
//		sb.append("     insert_user_na, ");
//		sb.append("     update_ts, ");
//		sb.append("     update_user_na, ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("     delete_fg, ");
//		sb.append("          CASE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       ' ' ");
//		sb.append("             END) ");
//		sb.append("		     WHEN ' ' THEN '0000' ELSE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       cast(null as char) ");
//		sb.append("             END) ");
//		sb.append("  		END as ord_hanku_cd , ");
//		sb.append("         CASE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			' ' ");
//		sb.append("     		END) ");
//		sb.append("		 	WHEN ' ' THEN '0000' ELSE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 cast(null as char) ");
//		sb.append("     		END) ");
//		sb.append("			END as ord_hinsyu_cd , ");
//		sb.append("  		CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN '0' ELSE kanri_kb END as ord_kanri_kb ");
//		sb.append("	FROM ( ");
//		sb.append("      SELECT ");
//		sb.append("     	 rt.kanri_kb, ");
//		sb.append("          rt.kanri_cd, ");
//		sb.append("          rt.tenpo_cd, ");
//		sb.append("     	 tp.kanji_rn AS tenpo_na, ");
//		sb.append("     	 rt.syohin_addtime_fg, ");
//		sb.append("     	 rt.insert_ts, ");
//		sb.append("     	 ss1.user_na AS insert_user_na, ");
//		sb.append("     	 rt.update_ts, ");
//		sb.append("     	 ss2.user_na AS update_user_na, ");
//		sb.append("     	 rt.delete_fg ");
//		sb.append(" 	 FROM ");
//		sb.append("     	 r_tanpinten_jidosakusei rt ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("			 LEFT JOIN sys_sosasya ss1 ON rt.insert_user_id = ss1.user_id AND ss1.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append("			 LEFT JOIN sys_sosasya ss2 ON rt.update_user_id = ss2.user_id AND ss2.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     	 LEFT JOIN r_tenpo tp ON rt.tenpo_cd = tp.tenpo_cd ");
//		sb.append(" 	 WHERE ");
//		sb.append("     rt.kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' AND ");
//		sb.append("     rt.kanri_cd in (SELECT DISTINCT(hanku_cd) FROM r_syohin_taikei ");
//		if( map.get("kanri_cd_from") != null && ((String)map.get("kanri_cd_from")).trim().length() > 0 )
//		{
//			sb.append("WHERE "); whereAnd = true;
//			sb.append("s_gyosyu_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_from") ) + "'");
//		}
//
//		if( map.get("kanri_cd_to") != null && ((String)map.get("kanri_cd_to")).trim().length() > 0 )
//		{
//			if (whereAnd) {
//				sb.append("AND ");
//			} else {
//				sb.append("WHERE "); whereAnd = false;
//			}
//			sb.append("s_gyosyu_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_to") ) + "'");
//		}
//		sb.append("		) ");
//		sb.append("AND ");
//		sb.append("     rt.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("		 ) TBL1 ");
//		sb.append(" UNION ");
//		sb.append(" SELECT ");
//		sb.append("     kanri_kb, ");
//		sb.append("     kanri_cd, ");
//
//		// 小業種コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd ) ");
//		sb.append("     END AS s_gyosyu_cd, ");
//
//		// 小業種名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("     END AS s_gyosyu_na, ");
//
//		// 販区コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_cd, ");
//
//		// 販区名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_na, ");
//
//		// 品種コード取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         kanri_cd  ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_cd, ");
//
//		// 品種名称取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' AND code_cd = kanri_cd) ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_na, ");
//
//		sb.append("     tenpo_cd, ");
//		sb.append("     CASE tenpo_cd WHEN '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' THEN ");
//		sb.append("     	'全店' ");
//		sb.append("     ELSE ");
//		sb.append("     	tenpo_na ");
//		sb.append("     END as tenpo_na, ");
//		sb.append("     syohin_addtime_fg, ");
//		sb.append("     insert_ts, ");
//		sb.append("     insert_user_na, ");
//		sb.append("     update_ts, ");
//		sb.append("     update_user_na, ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("     delete_fg, ");
//		sb.append("          CASE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       ' ' ");
//		sb.append("             END) ");
//		sb.append("		     WHEN ' ' THEN '0000' ELSE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       cast(null as char) ");
//		sb.append("             END) ");
//		sb.append("  		END as ord_hanku_cd , ");
//		sb.append("         CASE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			  ' ' ");
//		sb.append("     		END) ");
//		sb.append("		 	WHEN  ' ' THEN '0000' ELSE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 cast(null as char) ");
//		sb.append("     		END) ");
//		sb.append("			END as ord_hinsyu_cd , ");
//		sb.append("  		CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN '0' ELSE kanri_kb END as ord_kanri_kb ");
//		sb.append("	FROM ( ");
//		sb.append("      SELECT ");
//		sb.append("     	 rt.kanri_kb, ");
//		sb.append("          rt.kanri_cd, ");
//		sb.append("          rt.tenpo_cd, ");
//		sb.append("     	 tp.kanji_rn AS tenpo_na, ");
//		sb.append("     	 rt.syohin_addtime_fg, ");
//		sb.append("     	 rt.insert_ts, ");
//		sb.append("     	 ss1.user_na AS insert_user_na, ");
//		sb.append("     	 rt.update_ts, ");
//		sb.append("     	 ss2.user_na AS update_user_na, ");
//		sb.append("     	 rt.delete_fg ");
//		sb.append(" 	 FROM ");
//		sb.append("     	 r_tanpinten_jidosakusei rt ");
//		sb.append("			 LEFT JOIN sys_sosasya ss1 ON rt.insert_user_id = ss1.user_id AND ss1.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append("			 LEFT JOIN sys_sosasya ss2 ON rt.update_user_id = ss2.user_id AND ss2.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     	 LEFT JOIN r_tenpo tp ON rt.tenpo_cd = tp.tenpo_cd ");
//		sb.append(" 	 WHERE ");
//		sb.append("     rt.kanri_kb = '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' AND ");
//		sb.append("     rt.kanri_cd in (SELECT DISTINCT(hinsyu_cd) FROM r_syohin_taikei ");
//		if( map.get("kanri_cd_from") != null && ((String)map.get("kanri_cd_from")).trim().length() > 0 )
//		{
//			sb.append("WHERE "); whereAnd = true;
//			sb.append("s_gyosyu_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_from") ) + "'");
//		}
//
//		if( map.get("kanri_cd_to") != null && ((String)map.get("kanri_cd_to")).trim().length() > 0 )
//		{
//			if (whereAnd) {
//				sb.append("AND ");
//			} else {
//				sb.append("WHERE "); whereAnd = false;
//			}
//			sb.append("s_gyosyu_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_to") ) + "'");
//		}
//		sb.append("		) ");
//		sb.append("AND ");
//		sb.append("     rt.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("		 ) TBL1 ");
////		sb.append("	ORDER BY DECODE(kanri_kb, '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "','0',kanri_kb), kanri_cd, tenpo_cd ");
//		sb.append("	ORDER BY s_gyosyu_cd, ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("	ord_hanku_cd,	ord_hinsyu_cd, ord_kanri_kb,   tenpo_cd ");
////		↑↑移植（2006.05.23）↑↑
//
//		return sb.toString();
//	}
//
//	/**
//	 * 検索用ＳＱＬのベース部分の生成を行う。販区抽出時
//	 * 渡されたMapを元にWHERE区を作成する。
//	 * @param map Map
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getBaseSQL2( Map map )
//	{
//
////		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//		StringBuffer sb = new StringBuffer();
//		boolean whereAnd = false;
//
//		sb.append(" SELECT ");
//		sb.append("     kanri_kb, ");
//		sb.append("     kanri_cd, ");
//
//		// 小業種コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd ) ");
//		sb.append("     END AS s_gyosyu_cd, ");
//
//		// 小業種名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("     END AS s_gyosyu_na, ");
//
//		// 販区コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_cd, ");
//
//		// 販区名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_na, ");
//
//		// 品種コード取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         kanri_cd  ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_cd, ");
//
//		// 品種名称取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' AND code_cd = kanri_cd) ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_na, ");
//
//		sb.append("     tenpo_cd, ");
//		sb.append("     CASE tenpo_cd WHEN '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' THEN ");
//		sb.append("     	'全店' ");
//		sb.append("     ELSE ");
//		sb.append("     	tenpo_na ");
//		sb.append("     END as tenpo_na, ");
//		sb.append("     syohin_addtime_fg, ");
//		sb.append("     insert_ts, ");
//		sb.append("     insert_user_na, ");
//		sb.append("     update_ts, ");
//		sb.append("     update_user_na, ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("     delete_fg, ");
//		sb.append("          CASE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                      ' ' ");
//		sb.append("             END) ");
//		sb.append("		     WHEN ' ' THEN '0000' ELSE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       cast(null as char) ");
//		sb.append("             END) ");
//		sb.append("  		END as ord_hanku_cd , ");
//		sb.append("         CASE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 ' ' ");
//		sb.append("     		END) ");
//		sb.append("		 	WHEN ' ' THEN '0000' ELSE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 cast(null as char) ");
//		sb.append("     		END) ");
//		sb.append("			END as ord_hinsyu_cd , ");
//		sb.append("  		CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN '0' ELSE kanri_kb END as ord_kanri_kb ");
//		sb.append("	FROM ( ");
//		sb.append("  SELECT ");
//		sb.append("     rt.kanri_kb, ");
//		sb.append("     rt.kanri_cd, ");
//		sb.append("     rt.tenpo_cd, ");
//		sb.append("     tp.kanji_rn AS tenpo_na, ");
//		sb.append("     rt.syohin_addtime_fg, ");
//		sb.append("     rt.insert_ts, ");
//		sb.append("     ss1.user_na AS insert_user_na, ");
//		sb.append("     rt.update_ts, ");
//		sb.append("     ss2.user_na  AS update_user_na, ");
//		sb.append("     rt.delete_fg ");
//		sb.append(" FROM ");
//		sb.append("     r_tanpinten_jidosakusei rt ");
//		sb.append("		LEFT JOIN sys_sosasya ss1 ON rt.insert_user_id = ss1.user_id AND ss1.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append("		LEFT JOIN sys_sosasya ss2 ON rt.update_user_id = ss2.user_id AND ss2.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     LEFT JOIN r_tenpo tp ON rt.tenpo_cd = tp.tenpo_cd ");
//		sb.append(" WHERE ");
//		sb.append("     rt.kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//
//		if( map.get("kanri_cd_from") != null && ((String)map.get("kanri_cd_from")).trim().length() > 0 )
//		{
//			sb.append("AND ");
//			sb.append("rt.kanri_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_from") ) + "' ");
//		}
//
//		if( map.get("kanri_cd_to") != null && ((String)map.get("kanri_cd_to")).trim().length() > 0 )
//		{
//			sb.append("AND ");
//			sb.append("rt.kanri_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_to") ) + "' ");
//		}
//		sb.append("AND ");
//		sb.append("     rt.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		sb.append("		 ) TBL1 ");
//		sb.append("	UNION ");
//		sb.append(" SELECT ");
//		sb.append("     kanri_kb, ");
//		sb.append("     kanri_cd, ");
//
//		// 小業種コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd ) ");
//		sb.append("     END AS s_gyosyu_cd, ");
//
//		// 小業種名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("     END AS s_gyosyu_na, ");
//
//		// 販区コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_cd, ");
//
//		// 販区名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_na, ");
//
//		// 品種コード取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         kanri_cd  ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_cd, ");
//
//		// 品種名称取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' AND code_cd = kanri_cd) ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_na, ");
//
//		sb.append("     tenpo_cd, ");
//		sb.append("     CASE tenpo_cd WHEN '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' THEN ");
//		sb.append("     	'全店' ");
//		sb.append("     ELSE ");
//		sb.append("     	tenpo_na ");
//		sb.append("     END as tenpo_na, ");
//		sb.append("     syohin_addtime_fg, ");
//		sb.append("     insert_ts, ");
//		sb.append("     insert_user_na, ");
//		sb.append("     update_ts, ");
//		sb.append("     update_user_na, ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("     delete_fg, ");
//		sb.append("          CASE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                        ' ' ");
//		sb.append("             END) ");
//		sb.append("		     WHEN  ' ' THEN '0000' ELSE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       cast(null as char) ");
//		sb.append("             END) ");
//		sb.append("  		END as ord_hanku_cd , ");
//		sb.append("         CASE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 ' ' ");
//		sb.append("     		END) ");
//		sb.append("		 	WHEN  ' ' THEN '0000' ELSE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 cast(null as char) ");
//		sb.append("     		END) ");
//		sb.append("			END as ord_hinsyu_cd , ");
//		sb.append("  		CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN '0' ELSE kanri_kb END as ord_kanri_kb ");
//		sb.append("	FROM ( ");
//		sb.append("  SELECT ");
//		sb.append("     rt.kanri_kb, ");
//		sb.append("     rt.kanri_cd, ");
//		sb.append("     rt.tenpo_cd, ");
//		sb.append("     tp.kanji_rn AS tenpo_na, ");
//		sb.append("     rt.syohin_addtime_fg, ");
//		sb.append("     rt.insert_ts, ");
//		sb.append("     ss1.user_na AS insert_user_na, ");
//		sb.append("     rt.update_ts, ");
//		sb.append("     ss2.user_na  AS update_user_na, ");
//		sb.append("     rt.delete_fg ");
//		sb.append(" FROM ");
//		sb.append("     r_tanpinten_jidosakusei rt ");
//		sb.append("		LEFT JOIN sys_sosasya ss1 ON rt.insert_user_id = ss1.user_id AND ss1.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append("		LEFT JOIN sys_sosasya ss2 ON rt.update_user_id = ss2.user_id AND ss2.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     LEFT JOIN r_tenpo tp ON rt.tenpo_cd = tp.tenpo_cd ");
//		sb.append(" WHERE ");
//		sb.append("     rt.kanri_kb = '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' AND ");
//		sb.append("     rt.kanri_cd in (SELECT DISTINCT(hinsyu_cd) FROM r_syohin_taikei ");
//
//		if( map.get("kanri_cd_from") != null && ((String)map.get("kanri_cd_from")).trim().length() > 0 )
//		{
//			sb.append("WHERE "); whereAnd = true;
//			sb.append("hanku_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_from") ) + "' ");
//		}
//
//		if( map.get("kanri_cd_to") != null && ((String)map.get("kanri_cd_to")).trim().length() > 0 )
//		{
//			if (whereAnd) {
//				sb.append("AND ");
//			} else {
//				sb.append("WHERE ");
//			}
//			sb.append("hanku_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_to") ) + "' ");
//		}
//		sb.append("		) ");
//		sb.append("AND ");
//		sb.append("     rt.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("		 ) TBL1 ");
////		sb.append("	ORDER BY DECODE(kanri_kb, '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "','0',kanri_kb), kanri_cd, tenpo_cd ");
//		sb.append("	ORDER BY s_gyosyu_cd, ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("	ord_hanku_cd,	ord_hinsyu_cd, ord_kanri_kb,   tenpo_cd ");
////		↑↑移植（2006.05.23）↑↑
//
//		return sb.toString();
//	}

//	/**
//	 * 検索用ＳＱＬのベース部分の生成を行う。品種抽出時
//	 * 渡されたMapを元にWHERE区を作成する。
//	 * @param map Map
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getBaseSQL3( Map map )
//	{
//
////		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//		StringBuffer sb = new StringBuffer();
//
//		sb.append(" SELECT ");
//		sb.append("     kanri_kb, ");
//		sb.append("     kanri_cd, ");
//
//		// 小業種コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd ) ");
//		sb.append("     END AS s_gyosyu_cd, ");
//
//		// 小業種名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hanku_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "' AND code_cd = (SELECT DISTINCT (s_gyosyu_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("     END AS s_gyosyu_na, ");
//
//		// 販区コード取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	kanri_cd  ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_cd, ");
//
//		// 販区名称取得
//		sb.append("     CASE kanri_kb ");
//		sb.append("     	WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         	(SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = kanri_cd) ");
//		sb.append("         WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' AND code_cd = (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )) ");
//		sb.append("         ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("             cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hanku_na, ");
//
//		// 品種コード取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         kanri_cd  ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_cd, ");
//
//		// 品種名称取得
//		sb.append("     CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         (SELECT kanji_rn FROM r_namectf WHERE syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' AND code_cd = kanri_cd) ");
//		sb.append("     ELSE  ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("         cast(null as char) ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     END AS hinsyu_na, ");
//
//		sb.append("     tenpo_cd, ");
//		sb.append("     CASE tenpo_cd WHEN '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' THEN ");
//		sb.append("     	'全店' ");
//		sb.append("     ELSE ");
//		sb.append("     	tenpo_na ");
//		sb.append("     END as tenpo_na, ");
//		sb.append("     syohin_addtime_fg, ");
//		sb.append("     insert_ts, ");
//		sb.append("     insert_user_na, ");
//		sb.append("     update_ts, ");
//		sb.append("     update_user_na, ");
//		sb.append("     delete_fg ");
//		sb.append("	FROM ( ");
//		sb.append(" SELECT ");
//		sb.append("     rt.kanri_kb, ");
//		sb.append("     rt.kanri_cd, ");
//		sb.append("     rt.tenpo_cd, ");
//		sb.append("     tp.kanji_rn AS tenpo_na, ");
//		sb.append("     rt.syohin_addtime_fg, ");
//		sb.append("     rt.insert_ts, ");
//		sb.append("     ss1.user_na AS insert_user_na, ");
//		sb.append("     rt.update_ts, ");
//		sb.append("     ss2.user_na  AS update_user_na, ");
//		sb.append("     rt.delete_fg ");
//		sb.append(" FROM ");
//		sb.append("     r_tanpinten_jidosakusei rt ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("		LEFT JOIN sys_sosasya ss1 ON rt.insert_user_id = ss1.user_id AND ss1.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append("		LEFT JOIN sys_sosasya ss2 ON rt.update_user_id = ss2.user_id AND ss2.hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append("     LEFT JOIN r_tenpo tp ON rt.tenpo_cd = tp.tenpo_cd ");
//		sb.append(" WHERE ");
//		sb.append("     rt.kanri_kb = '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' ");
//
//		if( map.get("kanri_cd_from") != null && ((String)map.get("kanri_cd_from")).trim().length() > 0 )
//		{
//			sb.append("AND ");
//			sb.append("rt.kanri_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_from") ) + "' ");
//		}
//
//		if( map.get("kanri_cd_to") != null && ((String)map.get("kanri_cd_to")).trim().length() > 0 )
//		{
//			sb.append("AND ");
//			sb.append("rt.kanri_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_to") ) + "' ");
//		}
//
//		sb.append("AND ");
//		sb.append("     rt.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		sb.append("		 ) TBL1 ");
////		sb.append("	ORDER BY DECODE(kanri_kb, '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "','0',kanri_kb), kanri_cd, tenpo_cd ");
//		sb.append("	ORDER BY s_gyosyu_cd, ");
////		↓↓移植（2006.05.23）↓↓
//		sb.append("          CASE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       cast(null as char) ");
//		sb.append("             END) ");
//		sb.append("		     WHEN cast(null as char) THEN '0000' ELSE ");
//		sb.append("     		(CASE kanri_kb ");
//		sb.append("     			 WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN  ");
//		sb.append("         			  kanri_cd  ");
//		sb.append("         		 WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("             	      (SELECT DISTINCT (hanku_cd) FROM r_syohin_taikei WHERE hinsyu_cd = kanri_cd )  ");
//		sb.append("         	     ELSE  ");
//		sb.append("                       cast(null as char) ");
//		sb.append("             END) ");
//		sb.append("  		END, ");
//		sb.append("         CASE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 cast(null as char) ");
//		sb.append("     		END) ");
//		sb.append("		 	WHEN cast(null as char) THEN '0000' ELSE ");
//		sb.append("     	   (CASE kanri_kb ");
//		sb.append(" 				WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN  ");
//		sb.append("         			 kanri_cd  ");
//		sb.append("     			ELSE  ");
//		sb.append("         			 cast(null as char) ");
//		sb.append("     		END) ");
//		sb.append("			END, ");
//		sb.append("  		CASE kanri_kb WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN '0' ELSE kanri_kb END, ");
//		sb.append("		    tenpo_cd ");
////		↑↑移植（2006.05.23）↑↑
//
//		return sb.toString();
//	}

	/**
	 * 検索用ＳＱＬのベース部分の生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getBaseSQL( Map map )
	{

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		sb.append("     unit_cd, ");
		sb.append("     unit_na, ");
		sb.append("     tenpo_cd, ");
		sb.append("     CASE tenpo_cd WHEN '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' THEN ");
		sb.append("     	'全店' ");
		sb.append("     ELSE ");
		sb.append("     	tenpo_na ");
		sb.append("     END as tenpo_na, ");
		sb.append("     syohin_addtime_fg, ");
		sb.append("     insert_ts, ");
		sb.append("     insert_user_na, ");
		sb.append("     update_ts, ");
		sb.append("     update_user_na, ");
		sb.append("     delete_fg ");
		sb.append("	FROM ( ");
		sb.append(" SELECT ");
		sb.append("     rt.kanri_cd AS unit_cd, ");
		sb.append("     rn.kanji_rn AS unit_na, ");
		sb.append("     rt.tenpo_cd, ");
		sb.append("     tp.kanji_rn AS tenpo_na, ");
		sb.append("     rt.syohin_addtime_fg, ");
		sb.append("     rt.insert_ts, ");
		sb.append("     trim(rr1.riyo_user_na) AS insert_user_na, ");
		sb.append("     rt.update_ts, ");
		sb.append("     trim(rr2.riyo_user_na) AS update_user_na, ");
		sb.append("     rt.delete_fg ");
		sb.append(" FROM ");
		sb.append("     r_tanpinten_jidosakusei rt ");
//		sb.append("		LEFT JOIN r_riyo_user rr1 ON rt.insert_user_id = rr1.riyo_user_id");
//		sb.append("		LEFT JOIN r_riyo_user rr2 ON rt.update_user_id = rr2.riyo_user_id");
		sb.append("		LEFT JOIN r_portal_user rr1 ON TRIM(rt.insert_user_id) = rr1.user_id");
		sb.append("		LEFT JOIN r_portal_user rr2 ON TRIM(rt.update_user_id) = rr2.user_id");

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		sb.append("     LEFT JOIN r_namectf rn ON rn.code_cd = '"+map.get("ct_select") +"' || "+ "rt.kanri_cd AND rn.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
		sb.append("     LEFT JOIN r_tenpo tp ON rt.tenpo_cd = tp.tenpo_cd ");
		sb.append(" WHERE ");
		sb.append("     rt.kanri_kb = '" + mst000901_KanriKbDictionary.UNIT.getCode() + "' ");

		if( map.get("ct_select") != null && ((String)map.get("ct_select")).trim().length() > 0 )
		{
			sb.append("AND ");
			sb.append("rt.system_kb = '" + conv.convertWhereString( (String)map.get("ct_select") ) + "' ");
		}

		if( map.get("unit_cd_from") != null && ((String)map.get("unit_cd_from")).trim().length() > 0 )
		{
			sb.append("AND ");
			sb.append("rt.kanri_cd >= '" + conv.convertWhereString( (String)map.get("unit_cd_from") ) + "' ");
		}

		if( map.get("unit_cd_to") != null && ((String)map.get("unit_cd_to")).trim().length() > 0 )
		{
			sb.append("AND ");
			sb.append("rt.kanri_cd <= '" + conv.convertWhereString( (String)map.get("unit_cd_to") ) + "' ");
		}

		sb.append("AND ");
		sb.append("     rt.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("		 ) TBL1 ");
		sb.append("	ORDER BY unit_cd, tenpo_cd ");

		return sb.toString();
	}
//	↑↑2006.07.11 magp カスタマイズ修正↑↑

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst)
	{
		boolean befKanma = false;
		boolean aftKanma = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst610301_KeepMeisaiBean bean = (mst610301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_TANPINTEN_JIDOSAKUSEI (");
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" kanri_kb");
		}
//	    ↓↓2006.07.12 magp カスタマイズ修正↓↓
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		if( bean.getUnitCd() != null && bean.getUnitCd().trim().length() != 0 )
//		↑↑2006.07.12 magp カスタマイズ修正↑↑
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanri_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getSyohinAddtimeFg() != null && bean.getSyohinAddtimeFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_addtime_fg");
		}

		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" insert_user_id");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(",");
		sb.append(" update_user_id");
		sb.append(",");
		sb.append(" delete_fg");
		sb.append(",");
		sb.append(" system_kb");

		sb.append(")Values(");


		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
		}
//	    ↓↓2006.07.12 magp カスタマイズ修正↓↓
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
//		}
		if( bean.getUnitCd() != null && bean.getUnitCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUnitCd() ) + "'");
		}
//		↑↑2006.07.12 magp カスタマイズ修正↑↑
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getSyohinAddtimeFg() != null && bean.getSyohinAddtimeFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinAddtimeFg() ) + "'");
		}

		if( aftKanma ) sb.append(","); else aftKanma = true;
//		↓↓移植（2006.05.23）↓↓
		try {
			sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		↑↑移植（2006.05.23）↑↑
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		sb.append(",");
//	    ↓↓2006.07.12 magp カスタマイズ修正↓↓
//		sb.append("''");
		try {
			sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		↑↑2006.07.12 magp カスタマイズ修正↑↑
		sb.append(",");
//	    ↓↓2006.07.12 magp カスタマイズ修正↓↓
//		sb.append("''");
		sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		↑↑2006.07.12 magp カスタマイズ修正↑↑
		sb.append(",");
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append(",");
		sb.append("'" + bean.getSystemkb() + "'");

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
//	    ↓↓2006.07.12 magp カスタマイズ修正↓↓
//		boolean whereAnd = false;
//		↑↑2006.07.12 magp カスタマイズ修正↑↑
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst610301_KeepMeisaiBean bean = (mst610301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_TANPINTEN_JIDOSAKUSEI set ");
		if( bean.getSyohinAddtimeFg() != null && bean.getSyohinAddtimeFg().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" syohin_addtime_fg = ");
			sb.append("'" + conv.convertString( bean.getSyohinAddtimeFg() ) + "'");
		}

		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" update_ts = ");
//		↓↓移植（2006.05.23）↓↓
		try {
			sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		↑↑移植（2006.05.23）↑↑
		sb.append(",");
		sb.append(" update_user_id = ");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		sb.append(",");
		sb.append(" delete_fg = ");
		sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");

		sb.append(" WHERE");
		sb.append(" kanri_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		sb.append(" AND ");
		sb.append(" kanri_cd = ");
//	    ↓↓2006.07.12 magp カスタマイズ修正↓↓
//		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append("'" + conv.convertWhereString( bean.getUnitCd() ) + "'");
//		↑↑2006.07.12 magp カスタマイズ修正↑↑
		sb.append(" AND ");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");

//	    ↓↓2006.08.08 magp カスタマイズ修正↓↓
		sb.append(" AND ");
		sb.append(" system_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getSystemkb() ) + "'");
//		↑↑2006.08.08 magp カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * 更新チェック用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ
	 *
	 * @param List where句
	 * @return String 生成されたＳＱＬ sb.toString()
	 */
	public String getSelectUpdateSql(StringBuffer sbWhere) {

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		sb.append("	   KANRI_KB, ");
		sb.append("	   KANRI_CD, ");
		sb.append("    TENPO_CD, ");
		sb.append("    TRIM(INSERT_TS) INSERT_TS, ");
		sb.append("    TRIM(UPDATE_TS) UPDATE_TS, ");
		sb.append("    TRIM(DELETE_FG) DELETE_FG ");
		sb.append(" FROM ");
		sb.append("	   R_TANPINTEN_JIDOSAKUSEI" );
		sb.append(" WHERE ");
		sb.append("    " + sbWhere.toString() + " ");

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
		mst610301_KeepMeisaiBean bean = (mst610301_KeepMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_TANPINTEN_JIDOSAKUSEI where ");
		sb.append(" kanri_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		sb.append(" AND");
		sb.append(" kanri_cd = ");
//	    ↓↓2006.07.12 magp カスタマイズ修正↓↓
//		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append("'" + conv.convertWhereString( bean.getUnitCd() ) + "'");
//		↑↑2006.07.12 magp カスタマイズ修正↑↑
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
//	    ↓↓2006.08.08 magp カスタマイズ修正↓↓
		sb.append(" AND ");
		sb.append(" system_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getSystemkb() ) + "'");
//		↑↑2006.08.08 magp カスタマイズ修正↑↑

		sb.append(" AND");
		sb.append(" delete_fg = ");
		sb.append("'" + mst000801_DelFlagDictionary.IRU.getCode() + "'");
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
