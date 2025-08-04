/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別例外マスタ（各画面共通）商品マスタ表示部のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別例外マスタ（各画面共通）商品マスタ表示部のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
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
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別例外マスタ（各画面共通）商品マスタ表示部のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別例外マスタ（各画面共通）商品マスタ表示部のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst180101_TensyohinReigaiSyohinDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst180101_TensyohinReigaiSyohinDM()
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
	protected Object instanceBean( ResultSet rest ) throws SQLException {

		mst180101_TensyohinReigaiSyohinBean bean = new mst180101_TensyohinReigaiSyohinBean();

//		↓↓2006.07.01 xuzf カスタマイズ修正↓↓
//		bean.setHankuCd( rest.getString("hanku_cd") );
//		bean.setHankuNa( rest.getString("hanku_na") );
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setBumonNa( rest.getString("bumon_na") );
//		↑↑2006.07.01 xuzf カスタマイズ修正↑↑

		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setHinmeiKanjiNa( rest.getString("hinmei_kanji_na") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setTenHachuStDt( rest.getString("ten_hachu_st_dt") );
		bean.setTenHachuEdDt( rest.getString("ten_hachu_ed_dt") );
		bean.setGentankaVl( rest.getDouble("gentanka_vl") );
		bean.setBaitankaVl( rest.getLong("baitanka_vl") );
		bean.setGentankaVlStr(rest.getString("gentanka_vl_str"));
		bean.setBaitankaVlStr(rest.getString("baitanka_vl_str"));
		bean.setMaxHachutaniQtStr( rest.getString("max_hachutani_qt_str") );
		bean.setMaxHachutaniQt( rest.getDouble("max_hachutani_qt") );
		bean.setEosKb( rest.getString("eos_kb") );
		bean.setEosNa( rest.getString("eos_na") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setSiiresakiNa( rest.getString("siiresaki_na") );

//		↓↓2006.07.01 xuzf カスタマイズ修正↓↓
//		bean.setAreaCd( rest.getString("area_cd") );
//		bean.setAreaNa( rest.getString("area_na") );
//		↑↑2006.07.01 xuzf カスタマイズ修正↑↑

//		bean.setTenZaikoKb( rest.getString("ten_zaiko_kb") );
//		bean.setTenZaikoNa( rest.getString("ten_zaiko_na") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setInsertUserNa( rest.getString("insert_user_na") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setUpdateUserNa( rest.getString("update_user_na") );

//		↓↓2006.07.10 shijun カスタマイズ修正↓↓
//		bean.setHinsyuCd( rest.getString("hinsyu_cd") );

//		↓↓2006.07.01 xuzf カスタマイズ修正↓↓
//		bean.setHontaiVl( rest.getLong("hontai_vl") );
//		↑↑2006.07.01 xuzf カスタマイズ修正↑↑

//		bean.setHachuPattern1Kb( rest.getString("hachu_pattern_1_kb") );
//		bean.setHachuPattern1Na( rest.getString("hachu_pattern_1_na") );
//		bean.setHachuPattern2Kb( rest.getString("hachu_pattern_2_kb") );
//		bean.setHachuPattern2Na( rest.getString("hachu_pattern_2_na") );
//		↑↑2006.07.10 shijun カスタマイズ修正↑↑

		bean.setBin1Kb( rest.getString("bin_1_kb") );
		bean.setBin1Na( rest.getString("bin_1_na") );
		bean.setBin2Kb( rest.getString("bin_2_kb") );
		bean.setBin2Na( rest.getString("bin_2_na") );
		//↓↓2006.07.01 chencl カスタマイズ修正↓↓
		bean.setYusenBinKb( rest.getString("yusen_bin_kb") );
		bean.setYusenBinNa( rest.getString("yusen_bin_na") );
		//↑↑2006.07.01 chencl カスタマイズ修正↑↑
// 2006.01.05 Y.Inaba Add ↓
//		bean.setDeleteFg( rest.getString("delete_fg") );
// 2006.01.05 Y.Inaba Add ↑
		bean.setCreateDatabase();
		return bean;
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql( Map map ) {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = " WHERE ";
		String andStr = " AND ";
		StringBuffer sb = new StringBuffer();


		// ===BEGIN=== Add by kou 2006/10/17
		sb.append(" WITH TB_BIN ( CODE_CD, KANJI_RN ) AS ( ");
		sb.append(" 	SELECT CODE_CD, KANJI_RN ");
		sb.append(" 	  FROM R_NAMECTF ");
		sb.append(" 	 WHERE SYUBETU_NO_CD = '")
			.append(MessageUtil.getMessage(mst000101_ConstDictionary.SERVICE_DELIVERY, userLocal)).append("'");
		sb.append(" 		AND DELETE_FG = '")
			.append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
		sb.append(" ) ");
		// ===END=== Add by kou 2006/10/17

		sb.append("SELECT ");

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
//		sb.append("	 shn.HANKU_CD, ");
		sb.append("	 shn.BUMON_CD, ");
//		↑↑2006.07.04 xuzf カスタマイズ修正↑↑

		sb.append("	 (SELECT KANJI_RN FROM R_NAMECTF ");

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
//		sb.append("	WHERE SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' ");
		sb.append("	WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
//		↑↑2006.07.04 xuzf カスタマイズ修正↑↑

	  	sb.append(" AND '" + mst000801_DelFlagDictionary.INAI.getCode() + "' = DELETE_FG ");

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
//		sb.append(" AND CODE_CD = shn.HANKU_CD ");
//		sb.append("	) AS HANKU_NA, ");
		sb.append(" AND CODE_CD = shn.BUMON_CD ");
		sb.append("	) AS BUMON_NA, ");
//		↑↑2006.07.04 xuzf カスタマイズ修正↑↑

		sb.append("	shn.SYOHIN_CD, ");
		sb.append("	shn.HINMEI_KANJI_NA, ");
		sb.append("	shn.YUKO_DT, ");
		sb.append("	shn.TEN_HACHU_ST_DT, ");
		sb.append("	shn.TEN_HACHU_ED_DT, ");
		sb.append("	shn.GENTANKA_VL, ");
		sb.append("	shn.BAITANKA_VL, ");
		sb.append("	TO_CHAR(shn.GENTANKA_VL,'9999990.99') AS GENTANKA_VL_STR, ");
		sb.append("	TO_CHAR(shn.BAITANKA_VL,'9999990') AS BAITANKA_VL_STR, ");
		sb.append("	TO_CHAR(shn.MAX_HACHUTANI_QT,'9990.9') AS MAX_HACHUTANI_QT_STR, ");
		sb.append("	shn.MAX_HACHUTANI_QT, ");
		sb.append("	shn.EOS_KB, ");
		sb.append("	(SELECT  KANJI_RN  FROM  R_NAMECTF ");
		sb.append("	WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.EOS_DIVISION, userLocal) + "' ");
	    sb.append("     AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
		sb.append("		AND CODE_CD = shn.EOS_KB ");
		sb.append("	) AS EOS_NA, ");
		sb.append("	shn.SIIRESAKI_CD, ");

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
		//業種が生鮮の場合
//		if (map.get("gyosyu_id").equals("04")) {
//			sb.append("(SELECT KANJI_RN FROM R_SIIRESAKI ");
//			sb.append(" WHERE KANRI_KB = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");
//			sb.append("	AND KANRI_CD = '"+ mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD +"' ");
//			sb.append(" AND SIIRESAKI_CD = shn.SIIRESAKI_CD ");
//			sb.append(" AND TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//			sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "'");
//
//		//業種が生鮮以外の場合
//		} else {
//			sb.append("(SELECT KANJI_RN FROM R_SIIRESAKI ");
//			sb.append(" WHERE KANRI_KB = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//			sb.append("	AND KANRI_CD = shn.HANKU_CD ");
//			sb.append(" AND SIIRESAKI_CD = shn.SIIRESAKI_CD ");
//			sb.append(" AND TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//			sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "'");
//		}

		// ===BEGIN=== Modify by kou 2006/10/17
//		sb.append("(SELECT MAX(KANJI_RN) AS KANJI_RN FROM R_SIIRESAKI ");
//		sb.append(" WHERE KANRI_KB = '1' ");
//		sb.append("	AND KANRI_CD = '0000' ");
//		sb.append(" AND SUBSTRING(SIIRESAKI_CD,1,6) = SUBSTRING(shn.SIIRESAKI_CD,1,6) ");
//		sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "'");
////		↑↑2006.09.06 kema 修正↑↑
//		//仕入先名称
//		sb.append("	) AS SIIRESAKI_NA, ");

		sb.append(" CASE WHEN shn.SIIRESAKI_CD IS NULL THEN '' ");
		sb.append(" 	 WHEN shn.SIIRESAKI_CD = '' THEN '' ");
		sb.append(" 	 ELSE ");
		sb.append("(SELECT MAX(KANJI_RN) AS KANJI_RN FROM R_SIIRESAKI ");
		sb.append(" WHERE KANRI_KB = '1' ");
		sb.append("	AND KANRI_CD = '0000' ");
		sb.append(" AND SUBSTRING(SIIRESAKI_CD,1,6) = SUBSTRING(shn.SIIRESAKI_CD,1,6) ");
		sb.append(" AND DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
		sb.append("	) END SIIRESAKI_NA, ");			//仕入先名称
		// ===END=== Modify by kou 2006/10/17

////      ↓↓移植（2006.05.11）↓↓
//		sb.append(" (CASE shn.TEN_ZAIKO_KB WHEN '" + mst001901_MiseZaikoKbDictionary.KAITORI.getCode() + "' ");
//		sb.append("                        THEN '" + mst001901_MiseZaikoKbDictionary.KAITORI.toString() + "' ");
//		sb.append("                        WHEN '" + mst001901_MiseZaikoKbDictionary.ITAKU.getCode() + "' ");
//		sb.append("                        THEN '" + mst001901_MiseZaikoKbDictionary.ITAKU.toString() + "' ");
//		sb.append("                        WHEN '" + mst001901_MiseZaikoKbDictionary.SYOUKA.getCode() + "' ");
//		sb.append("                        THEN  '" + mst001901_MiseZaikoKbDictionary.SYOUKA.toString() + "'");
//		sb.append("                        ELSE '' ");
//		sb.append(" END) AS TEN_ZAIKO_NA, ");
//      ↑↑移植（2006.05.11）↑↑
		sb.append("	shn.INSERT_TS, ");
		sb.append("	shn.INSERT_USER_ID, ");
//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
//		sb.append("	(SELECT USER_NA FROM SYS_SOSASYA ");
//		sb.append("	WHERE USER_ID = shn.INSERT_USER_ID ");
//		sb.append("	AND HOJIN_CD = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append("	(SELECT RIYO_USER_NA FROM R_RIYO_USER ");
//		sb.append("	WHERE RIYO_USER_ID = shn.INSERT_USER_ID ");
////		↑↑2006.07.04 xuzf カスタマイズ修正↑↑
		sb.append("	(SELECT USER_NA FROM R_PORTAL_USER ");
		sb.append("	WHERE USER_ID = TRIM(shn.INSERT_USER_ID) ");

		sb.append("	) AS INSERT_USER_NA, ");
		sb.append("	shn.UPDATE_TS, ");
		sb.append("	shn.UPDATE_USER_ID, ");

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
//		sb.append("	(SELECT USER_NA FROM SYS_SOSASYA ");
//		sb.append("	WHERE USER_ID = shn.UPDATE_USER_ID ");
//		sb.append("	AND HOJIN_CD = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//		sb.append("	(SELECT RIYO_USER_NA FROM R_RIYO_USER ");
//		sb.append("	WHERE RIYO_USER_ID = shn.UPDATE_USER_ID ");
////		↑↑2006.07.04 xuzf カスタマイズ修正↑↑
		sb.append("	(SELECT USER_NA FROM R_PORTAL_USER ");
		sb.append("	WHERE USER_ID = TRIM(shn.UPDATE_USER_ID) ");

		sb.append("	) AS UPDATE_USER_NA, ");
//		↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append("	shn.HINSYU_CD, ");
//		↑↑2006.07.04 chencl カスタマイズ修正↑↑
//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
		//生鮮用 Start
////      ↓↓移植（2006.06.06）↓↓
//		sb.append("	shn.BAITANKA_VL - float(shn.BAITANKA_VL) * "+ mst005401_SyouhizeirtuDictionary.ZEIRITU.getCode()
//					+ " / (100 + " + mst005401_SyouhizeirtuDictionary.ZEIRITU.getCode() + ") ");
////      ↑↑移植（2006.06.06）↑↑
//		sb.append("	AS HONTAI_VL, ");

//		sb.append("	shn.HACHU_PATTERN_1_KB, ");
//		sb.append("	(SELECT KANJI_RN FROM R_NAMECTF ");
//		sb.append("	WHERE SYUBETU_NO_CD = '" + mst000101_ConstDictionary.ORDER_PATTERN + "' ");
//	  	sb.append(" AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//		↑↑2006.07.04 xuzf カスタマイズ修正↑↑
//		↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append("	AND CODE_CD = shn.HACHU_PATTERN_1_KB ");
//		sb.append("	) AS HACHU_PATTERN_1_NA, ");
//		sb.append("	shn.HACHU_PATTERN_2_KB, ");
//		sb.append("	(SELECT KANJI_RN FROM R_NAMECTF ");
//		sb.append("	WHERE SYUBETU_NO_CD = '" + mst000101_ConstDictionary.ORDER_PATTERN + "' ");
//		sb.append(" AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//		sb.append("	AND CODE_CD = shn.HACHU_PATTERN_2_KB ");
//		sb.append("	) AS HACHU_PATTERN_2_NA, ");
//		↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append("	shn.BIN_1_KB ");
//		↓↓移植（2006.05.11）↓↓

		// ===BEGIN=== Modify by kou 2006/10/17
//		sb.append(", (SELECT KANJI_RN FROM R_NAMECTF ");
//		sb.append("	WHERE  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.SERVICE_DELIVERY + "' ");
//		sb.append("		  AND CODE_CD = shn.BIN_1_KB ");
//		sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//		sb.append("		) AS BIN_1_NA ");

		sb.append(", CASE WHEN shn.BIN_1_KB IS NULL THEN '' ");
		sb.append(" 	 WHEN shn.BIN_1_KB = '' THEN ''");
		sb.append(" 	 ELSE ");
		sb.append("  	( SELECT KANJI_RN ");
		sb.append(" 		 FROM TB_BIN ");
		sb.append(" 		WHERE CODE_CD = shn.BIN_1_KB ");
		sb.append("  ) END BIN_1_NA");
		// ===END=== Modify by kou 2006/10/17

//		↑↑移植（2006.05.11）↑↑
		sb.append("		,shn.BIN_2_KB ");
//		↓↓移植（2006.05.11）↓↓

		// ===BEGIN=== Modify by kou 2006/10/17
//		sb.append(", (SELECT KANJI_RN FROM R_NAMECTF ");
//		sb.append("	WHERE  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.SERVICE_DELIVERY + "' ");
//		sb.append("		  AND CODE_CD = shn.BIN_2_KB ");
//		sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//		sb.append("		) AS BIN_2_NA ");

		sb.append(", CASE WHEN shn.BIN_2_KB IS NULL THEN '' ");
		sb.append(" 	 WHEN shn.BIN_2_KB = '' THEN ''");
		sb.append(" 	 ELSE ");
		sb.append("  	( SELECT KANJI_RN ");
		sb.append(" 		 FROM TB_BIN ");
		sb.append(" 		WHERE CODE_CD = shn.BIN_2_KB ");
		sb.append("  ) END BIN_2_NA");
		// ===END=== Modify by kou 2006/10/17

//		↓↓2006.07.04 chencl カスタマイズ修正↓↓
		sb.append("     ,shn.YUSEN_BIN_KB");

		// ===BEGIN=== Modify by kou 2006/10/17
//		sb.append(", (SELECT KANJI_RN FROM R_NAMECTF ");
//		sb.append("	WHERE  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.SERVICE_DELIVERY + "' ");
//		sb.append("		  AND CODE_CD = shn.YUSEN_BIN_KB ");
//		sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//		sb.append("		) AS YUSEN_BIN_NA ");

		sb.append(" , CASE WHEN shn.YUSEN_BIN_KB IS NULL THEN '' ");
		sb.append(" 	 WHEN shn.YUSEN_BIN_KB = '' THEN ''");
		sb.append(" 	 ELSE ");
		sb.append("  	( SELECT KANJI_RN ");
		sb.append(" 		 FROM TB_BIN ");
		sb.append(" 		WHERE CODE_CD = shn.YUSEN_BIN_KB ");
		sb.append("  ) END YUSEN_BIN_NA");
		// ===END=== Modify by kou 2006/10/17

//		↑↑2006.07.04 chencl カスタマイズ修正↑↑
//      ↑↑移植（2006.05.11）↑↑
		//生鮮用 End
// 2006.01.05 Y.Inaba Add ↓
//		sb.append(" shn.DELETE_FG ");
// 2006.01.05 Y.Inaba Add ↑

		sb.append("	FROM ");
		sb.append("		 R_SYOHIN shn ");


//		sb.append(whereStr);

//		whereStr = andStr;

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
//		// hanku_cd に対するWHERE句
//		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("shn.hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
//		↑↑2006.07.04 xuzf カスタマイズ修正↑↑
		// bumon_cd に対するWHERE句
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			↓↓2006.07.10 shijun カスタマイズ修正↓↓
//			sb.append("shn.bumon_cd = '" + conv.convertWhereString( (String)map.get("bumon_cd") ) + "'");
			sb.append("shn.bumon_cd = '" + conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
//			↑↑2006.07.10 shijun カスタマイズ修正↑↑

			whereStr = andStr;
		}

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
		// hanku_na に対するWHERE句
//		if( map.get("hanku_na") != null && ((String)map.get("hanku_na")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("shn.hanku_na = '" + conv.convertWhereString( (String)map.get("hanku_na") ) + "'");
		// bumon_na に対するWHERE
		if( map.get("bumon_na") != null && ((String)map.get("bumon_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.bumon_na = '" + conv.convertWhereString( (String)map.get("bumon_na") ) + "'");
//		↑↑2006.07.04 xuzf カスタマイズ修正↑↑

			whereStr = andStr;
		}

		// syohin_cd に対するWHERE句
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
			whereStr = andStr;
		}

		// hinmei_kanji_na に対するWHERE句
		if( map.get("hinmei_kanji_na") != null && ((String)map.get("hinmei_kanji_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.hinmei_kanji_na = '" + conv.convertWhereString( (String)map.get("hinmei_kanji_na") ) + "'");
			whereStr = andStr;
		}

		// yuko_dt に対するWHERE句
		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			sb.append("shn.yuko_dt = MSP710101_GETSYOHINYUKODT(");
//			sb.append("'" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "',");
//			sb.append("'" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "',");
//			sb.append("'" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "')");
			sb.append("shn.yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
			whereStr = andStr;
		}

		// ten_hachu_st_dt に対するWHERE句
		if( map.get("ten_hachu_st_dt") != null && ((String)map.get("ten_hachu_st_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.ten_hachu_st_dt = '" + conv.convertWhereString( (String)map.get("ten_hachu_st_dt") ) + "'");
			whereStr = andStr;
		}

		// ten_hachu_ed_dt に対するWHERE句
		if( map.get("ten_hachu_ed_dt") != null && ((String)map.get("ten_hachu_ed_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.ten_hachu_ed_dt = '" + conv.convertWhereString( (String)map.get("ten_hachu_ed_dt") ) + "'");
			whereStr = andStr;
		}

		// gentanka_vl に対するWHERE句
		if( map.get("gentanka_vl") != null && ((String)map.get("gentanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("shn.gentanka_vl = " + (String)map.get("gentanka_vl"));
			whereStr = andStr;
		}

		// baitanka_vl に対するWHERE句
		if( map.get("baitanka_vl") != null && ((String)map.get("baitanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("shn.baitanka_vl = " + (String)map.get("baitanka_vl"));
			whereStr = andStr;
		}

		// max_hachutani_qt に対するWHERE句
		if( map.get("max_hachutani_qt") != null && ((String)map.get("max_hachutani_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("shn.max_hachutani_qt = " + (String)map.get("max_hachutani_qt"));
			whereStr = andStr;
		}

		// eos_kb に対するWHERE句
		if( map.get("eos_kb") != null && ((String)map.get("eos_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.eos_kb = '" + conv.convertWhereString( (String)map.get("eos_kb") ) + "'");
			whereStr = andStr;
		}

		// eos_na に対するWHERE句
		if( map.get("eos_na") != null && ((String)map.get("eos_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.eos_na = '" + conv.convertWhereString( (String)map.get("eos_na") ) + "'");
			whereStr = andStr;
		}

		// siiresaki_cd に対するWHERE句
		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.siiresaki_cd = '" + conv.convertWhereString( (String)map.get("siiresaki_cd") ) + "'");
			whereStr = andStr;
		}

		// kanji_rn に対するWHERE句
		if( map.get("kanji_rn") != null && ((String)map.get("kanji_rn")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.kanji_rn = '" + conv.convertWhereString( (String)map.get("kanji_rn") ) + "'");
			whereStr = andStr;
		}

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
//		// buturyu_kb に対するWHERE句
//		if( map.get("buturyu_kb") != null && ((String)map.get("buturyu_kb")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("shn.buturyu_kb = '" + conv.convertWhereString( (String)map.get("buturyu_kb") ) + "'");
//		// area_cd に対するWHERE句
//		if( map.get("area_cd") != null && ((String)map.get("area_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("shn.area_cd = '" + conv.convertWhereString( (String)map.get("area_cd") ) + "'");
//		↑↑2006.07.04 xuzf カスタマイズ修正↑↑

//			whereStr = andStr;
//		}

//		↓↓2006.07.04 xuzf カスタマイズ修正↓↓
//		// buturyu_na に対するWHERE句
//		if( map.get("buturyu_na") != null && ((String)map.get("buturyu_na")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("buturyu_na = '" + conv.convertWhereString( (String)map.get("buturyu_na") ) + "'");
//		// area_na に対するWHERE句
//		if( map.get("area_na") != null && ((String)map.get("area_na")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("area_na = '" + conv.convertWhereString( (String)map.get("area_na") ) + "'");
//		↑↑2006.07.04 xuzf カスタマイズ修正↑↑
//			whereStr = andStr;
//		}

		// ten_zaiko_kb に対するWHERE句
		if( map.get("ten_zaiko_kb") != null && ((String)map.get("ten_zaiko_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.ten_zaiko_kb = '" + conv.convertWhereString( (String)map.get("ten_zaiko_kb") ) + "'");
			whereStr = andStr;
		}

		// ten_zaiko_na に対するWHERE句
		if( map.get("ten_zaiko_na") != null && ((String)map.get("ten_zaiko_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.ten_zaiko_na = '" + conv.convertWhereString( (String)map.get("ten_zaiko_na") ) + "'");
			whereStr = andStr;
		}

		// insert_ts に対するWHERE句
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}

		// insert_user_id に対するWHERE句
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}

		// insert_user_na に対するWHERE句
		if( map.get("insert_user_na") != null && ((String)map.get("insert_user_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.insert_user_na = '" + conv.convertWhereString( (String)map.get("insert_user_na") ) + "'");
			whereStr = andStr;
		}

		// update_ts に対するWHERE句
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}

		// update_user_id に対するWHERE句
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}

		// update_user_na に対するWHERE句
		if( map.get("update_user_na") != null && ((String)map.get("update_user_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("shn.update_user_na = '" + conv.convertWhereString( (String)map.get("update_user_na") ) + "'");
			whereStr = andStr;
		}

		// delete_fg に対するWHERE句
		if ( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0)
		{
			sb.append(whereStr);
			sb.append("shn.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}

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
