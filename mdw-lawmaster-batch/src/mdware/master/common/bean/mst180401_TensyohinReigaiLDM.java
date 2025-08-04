/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/23)初版作成
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
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst180401_TensyohinReigaiLDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst180401_TensyohinReigaiLDM()
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
		mst180401_TensyohinReigaiLBean bean = new mst180401_TensyohinReigaiLBean();
		bean.setSentaku( rest.getString("sentaku") );
		//↓↓2006.07.03 chencl カスタマイズ 修正↓↓
//		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setBumonCd( rest.getString("bumon_cd") );
		//↑↑2006.07.03 chencl カスタマイズ修正↑↑
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoNa( rest.getString("tenpo_na") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setTenHachuStDt( rest.getString("ten_hachu_st_dt") );
		bean.setTenHachuEdDt( rest.getString("ten_hachu_ed_dt") );
		bean.setGentankaVl( rest.getDouble("gentanka_vl") );
		bean.setGentankaVlEn( rest.getString("gentanka_vl_en") );
		bean.setGentankaVlSen( rest.getString("gentanka_vl_sen") );
		bean.setBaitankaVl( rest.getLong("baitanka_vl") );
		bean.setMaxHachutaniQt( rest.getDouble("max_hachutani_qt") );
		bean.setEosKb( rest.getString("eos_kb") );
		bean.setEosNa( rest.getString("eos_na") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setSiiresakiNa( rest.getString("siiresaki_na") );
//		↓↓2006.07.01 xuzf カスタマイズ修正↓↓
//		bean.setButuryuKb( rest.getString("buturyu_kb") );
//		bean.setButuryuNa( rest.getString("buturyu_na") );
//		bean.setAreaCd( rest.getString("area_cd") );
//		bean.setAreaNa( rest.getString("area_na") );
//		bean.setTenZaikoKb( rest.getString("ten_zaiko_kb") );
//		bean.setTenZaikoNa( rest.getString("ten_zaiko_na") );
//		↑↑2006.07.01 xuzf カスタマイズ修正↑↑
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setInsertUserNa( rest.getString("insert_user_na") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setUpdateUserNa( rest.getString("update_user_na") );
//		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setGentankaVlStr( rest.getString("gentanka_vl_str") );
		bean.setBaitankaVlStr( rest.getString("baitanka_vl_str") );
		bean.setMaxHachutaniQtStr( rest.getString("max_hachutani_qt_str") );
//		bean.setHontaiVl( rest.getLong("hontai_vl") );
		//↓↓2006.07.03 chencl カスタマイズ 修正↓↓
//		bean.setHontaiVl( rest.getString("hontai_vl") );
//		bean.setHachuPattern1Kb( rest.getString("hachu_pattern_1_kb") );
//		bean.setHachuPattern1Na( rest.getString("hachu_pattern_1_na") );
//		bean.setHachuPattern2Kb( rest.getString("hachu_pattern_2_kb") );
//		bean.setHachuPattern2Na( rest.getString("hachu_pattern_2_na") );
		//↑↑2006.07.03 chencl カスタマイズ修正↑↑
		bean.setBin1Kb( rest.getString("bin_1_kb") );
		bean.setBin1Na( rest.getString("bin_1_na") );
		bean.setBin2Kb( rest.getString("bin_2_kb") );
		bean.setBin2Na( rest.getString("bin_2_na") );
// ↓↓仕様変更（2005.08.04）↓↓
		//↓↓2006.07.03 chencl カスタマイズ 修正↓↓
		bean.setYusenBinKb( rest.getString("yusen_bin_kb") );
		bean.setYusenBinNa( rest.getString("yusen_bin_na") );
		bean.setDeleteFg( rest.getString("delete_fg") );
//		bean.setSinkiTorokuDt( rest.getString("sinki_toroku_dt") );
//		bean.setHenkoDt( rest.getString("henko_dt") );
		//↑↑↑2006.07.03 chencl カスタマイズ修正↑↑
// ↑↑仕様変更（2005.08.04）↑↑
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
		StringBuffer sb = new StringBuffer();

		if (map.get("mode").equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
			//新規時
			sb.append("SELECT ");
			sb.append("		 '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "' AS SENTAKU ");
//			↓↓2006.07.04 chencl カスタマイズ修正↓↓
//			sb.append("		,'" + map.get("hanku_cd") + "' AS HANKU_CD ");
			sb.append("		,'" + map.get("bumon_cd") + "' AS BUMON_CD ");
			//↑↑2006.07.04 chencl カスタマイズ修正↑↑
			sb.append("		,'" + map.get("syohin_cd") + "' AS SYOHIN_CD ");
			sb.append("		,TBL2.TENPO_CD ");
			sb.append("		,TBL2.TENPO_NA ");
//			↓↓移植（2006.05.11）↓↓
//			↓↓2006.07.04 chencl カスタマイズ修正↓↓
			sb.append("		,'" + map.get("yuko_dt") + "' AS YUKO_DT ");
//			sb.append("		,CAST(NULL AS CHAR) AS YUKO_DT ");
//			↑↑2006.07.04 chencl カスタマイズ修正↑↑
			sb.append("		,CAST(NULL AS CHAR) AS TEN_HACHU_ST_DT ");
			sb.append("		,CAST(NULL AS CHAR) AS TEN_HACHU_ED_DT ");
			sb.append("		,CAST(NULL AS CHAR) AS GENTANKA_VL ");
			sb.append("		,CAST(NULL AS CHAR) AS GENTANKA_VL_EN ");
			sb.append("		,CAST(NULL AS CHAR) AS GENTANKA_VL_SEN ");
			sb.append("		,CAST(NULL AS CHAR) AS BAITANKA_VL ");
			sb.append("		,CAST(NULL AS CHAR) AS MAX_HACHUTANI_QT ");
			sb.append("		,CAST(NULL AS CHAR) AS EOS_KB ");
			sb.append("		,CAST(NULL AS CHAR) AS EOS_NA ");
			sb.append("		,CAST(NULL AS CHAR) AS SIIRESAKI_CD ");
			sb.append("		,CAST(NULL AS CHAR) AS SIIRESAKI_NA ");
			//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//			sb.append("		,CAST(NULL AS CHAR) AS BUTURYU_KB ");
//			sb.append("		,CAST(NULL AS CHAR) AS BUTURYU_NA ");
//			sb.append("		,CAST(NULL AS CHAR) AS TEN_ZAIKO_KB ");
//			sb.append("		,CAST(NULL AS CHAR) AS TEN_ZAIKO_NA ");
//			sb.append("		,CAST(NULL AS CHAR) AS AREA_CD ");
//			sb.append("		,CAST(NULL AS CHAR) AS AREA_NA ");
			//↑↑2006.07.04 chencl カスタマイズ修正↑↑
			sb.append("		,CAST(NULL AS CHAR) AS INSERT_TS ");
			sb.append("		,CAST(NULL AS CHAR) AS INSERT_USER_ID ");
			sb.append("		,CAST(NULL AS CHAR) AS INSERT_USER_NA ");
			sb.append("		,CAST(NULL AS CHAR) AS UPDATE_TS ");
			sb.append("		,CAST(NULL AS CHAR) AS UPDATE_USER_ID ");
			sb.append("		,CAST(NULL AS CHAR) AS UPDATE_USER_NA ");
			sb.append("		,CAST(NULL AS CHAR) AS DELETE_FG ");
			sb.append("		,CAST(NULL AS CHAR) AS GENTANKA_VL_STR ");
			sb.append("		,CAST(NULL AS CHAR) AS BAITANKA_VL_STR ");
			sb.append("		,CAST(NULL AS CHAR) AS MAX_HACHUTANI_QT_STR ");
//			↑↑移植（2006.05.11）↑↑
			//生鮮用 Start
//			↓↓移植（2006.05.11）↓↓
			//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//			sb.append("		,CAST(NULL AS CHAR) AS HONTAI_VL ");
//			sb.append("		,CAST(NULL AS CHAR) AS HACHU_PATTERN_1_KB ");
//			sb.append("		,CAST(NULL AS CHAR) AS HACHU_PATTERN_1_NA ");
//			sb.append("		,CAST(NULL AS CHAR) AS HACHU_PATTERN_2_KB ");
//			sb.append("		,CAST(NULL AS CHAR) AS HACHU_PATTERN_2_NA ");
			sb.append("		,CAST(NULL AS CHAR) AS BIN_1_KB ");
			sb.append("		,CAST(NULL AS CHAR) AS BIN_1_NA ");
			sb.append("		,CAST(NULL AS CHAR) AS BIN_2_KB ");
			sb.append("		,CAST(NULL AS CHAR) AS BIN_2_NA ");
			sb.append("		,CAST(NULL AS CHAR) AS YUSEN_BIN_KB ");
			sb.append("		,CAST(NULL AS CHAR) AS YUSEN_BIN_NA ");
			//生鮮用 End
// ↓↓仕様変更（2005.08.04）↓↓
//			sb.append("		,CAST(NULL AS CHAR) AS SINKI_TOROKU_DT ");
//			sb.append("		,CAST(NULL AS CHAR) AS HENKO_DT ");
			//↑↑2006.07.04 chencl カスタマイズ修正↑↑
// ↑↑仕様変更（2005.08.04）↑↑
//			↑↑移植（2006.05.11）↑↑
			sb.append("FROM  ( ");
			sb.append(getTenpoCdSQL0(map));
			sb.append(") TBL2 ");
//			↓↓2006.07.26 chencl カスタマイズ修正↓↓
			sb.append(" ORDER BY TBL2.TENPO_CD");
//			↑↑2006.07.26 chencl カスタマイズ修正↑↑
		} else {
			//修正・削除・照会時
			if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 ) {
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

				// ===BEGIN=== Add by kou 2006/10/16
				sb.append(" WITH TB_BIN ( CODE_CD, KANJI_RN ) AS ( ");
				sb.append(" 	SELECT CODE_CD, KANJI_RN ");
				sb.append(" 	  FROM R_NAMECTF ");
				sb.append(" 	 WHERE SYUBETU_NO_CD = '")
					.append(MessageUtil.getMessage(mst000101_ConstDictionary.SERVICE_DELIVERY, userLocal)).append("'");
				sb.append(" 		AND DELETE_FG = '")
					.append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
				sb.append(" ) ");
				// ===END=== Add by kou 2006/10/16

				sb.append("SELECT ");
				sb.append("		 '0' AS SENTAKU ");
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("		,R_TENSYOHIN_REIGAI.HANKU_CD ");
				sb.append("		,R_TENSYOHIN_REIGAI.BUMON_CD ");
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				sb.append("		,R_TENSYOHIN_REIGAI.SYOHIN_CD ");
				sb.append("		,TBL1.TENPO_CD ");
				sb.append("		,TBL1.TENPO_NA ");
				sb.append("		,R_TENSYOHIN_REIGAI.YUKO_DT ");
				sb.append("		,R_TENSYOHIN_REIGAI.TEN_HACHU_ST_DT ");
				sb.append("		,R_TENSYOHIN_REIGAI.TEN_HACHU_ED_DT ");
				sb.append("		,R_TENSYOHIN_REIGAI.GENTANKA_VL ");
				sb.append("		,SUBSTR(TO_CHAR(R_TENSYOHIN_REIGAI.GENTANKA_VL,'9999990.99'),2,7) AS  GENTANKA_VL_EN ");
				sb.append("		,SUBSTR(TO_CHAR(R_TENSYOHIN_REIGAI.GENTANKA_VL,'9999990.99'),10,2) AS  GENTANKA_VL_SEN ");
				sb.append("		,R_TENSYOHIN_REIGAI.BAITANKA_VL ");
				sb.append("		,R_TENSYOHIN_REIGAI.MAX_HACHUTANI_QT ");
				sb.append("		,R_TENSYOHIN_REIGAI.EOS_KB ");

				// ===BEGIN=== Modify by kou 2006/10/16
//				sb.append("		,(SELECT ");
//				sb.append("			KANJI_RN ");
//				sb.append("		  FROM ");
//				sb.append("			R_NAMECTF ");
//				sb.append("		  WHERE ");
//				sb.append("			  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.EOS_DIVISION + "' ");
//				sb.append("		  AND CODE_CD = R_TENSYOHIN_REIGAI.EOS_KB ");
//				sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//				sb.append("		) AS EOS_NA ");

				sb.append(", CASE WHEN R_TENSYOHIN_REIGAI.EOS_KB IS NULL THEN '' ");
				sb.append(" 	 WHEN R_TENSYOHIN_REIGAI.EOS_KB = '' THEN '' ");
				sb.append(" 	 ELSE ");
				sb.append("		(SELECT ");
				sb.append("			KANJI_RN ");
				sb.append("		  FROM ");
				sb.append("			R_NAMECTF ");
				sb.append("		  WHERE ");
				sb.append("			  SYUBETU_NO_CD = '")
					.append(MessageUtil.getMessage(mst000101_ConstDictionary.EOS_DIVISION, userLocal)).append("' ");
				sb.append("		  AND CODE_CD = R_TENSYOHIN_REIGAI.EOS_KB ");
				sb.append("       AND '")
					.append(mst000801_DelFlagDictionary.INAI.getCode()).append("' = DELETE_FG ");
				sb.append("		) END EOS_NA ");
				// ===END=== Modify by kou 2006/10/16

				sb.append("		,R_TENSYOHIN_REIGAI.SIIRESAKI_CD ");
				// ↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				//業種が生鮮の場合
//				if (map.get("gyosyu_id").equals("04")) {
//					sb.append(",(SELECT KANJI_RN FROM R_SIIRESAKI ");
//					sb.append(" WHERE KANRI_KB = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");
//					sb.append("	AND KANRI_CD = '"+ mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD +"' ");
//					sb.append(" AND SIIRESAKI_CD = R_TENSYOHIN_REIGAI.SIIRESAKI_CD ");
//					sb.append(" AND TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//					sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "'");
//
//				//業種が生鮮以外の場合
//				} else {
//					sb.append(",(SELECT KANJI_RN FROM R_SIIRESAKI ");
//					sb.append(" WHERE KANRI_KB = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//					sb.append("	AND KANRI_CD = '"+ map.get("hanku_cd") +"' ");
//					sb.append(" AND SIIRESAKI_CD = R_TENSYOHIN_REIGAI.SIIRESAKI_CD ");
//					sb.append(" AND TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//					sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "'");
//				}
				// ===BEGIN=== Modify by kou 2006/10/16
//				sb.append(",(SELECT MAX(KANJI_RN) AS KANJI_RN FROM R_SIIRESAKI ");
//				sb.append(" WHERE KANRI_KB = '1' ");
//				sb.append("	AND KANRI_CD = '0000' ");
//				sb.append(" AND SUBSTRING(SIIRESAKI_CD,1,6) = SUBSTRING(R_TENSYOHIN_REIGAI.SIIRESAKI_CD,1,6) ");
//				sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "'");
//				//↑↑2006.09.06 kema 修正↑↑
//				//仕入先名称
//				sb.append("	) AS SIIRESAKI_NA ");

				sb.append(", CASE WHEN R_TENSYOHIN_REIGAI.SIIRESAKI_CD IS NULL THEN '' ");
				sb.append(" 	 WHEN R_TENSYOHIN_REIGAI.SIIRESAKI_CD = '' THEN '' ");
				sb.append(" 	 ELSE ");
				sb.append(" 	(SELECT MAX(KANJI_RN) AS KANJI_RN FROM R_SIIRESAKI ");
				sb.append(" 	WHERE KANRI_KB = '1' ");
				sb.append("		AND KANRI_CD = '0000' ");
				sb.append(" 	AND SUBSTRING(SIIRESAKI_CD,1,6) = SUBSTRING(R_TENSYOHIN_REIGAI.SIIRESAKI_CD,1,6) ");
				sb.append(" 	AND DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
				sb.append("	) END SIIRESAKI_NA ");			//仕入先名称
				// ===END=== Modify by kou 2006/10/16

				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("	,R_TENSYOHIN_REIGAI.AREA_CD, ");
//				sb.append("	(SELECT KANJI_RN  FROM R_NAMECTF ");
//				sb.append("	WHERE SYUBETU_NO_CD = '" + mst000101_ConstDictionary.AREA_CODE + "' ");
//				sb.append(" AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//				sb.append("	AND CODE_CD = R_TENSYOHIN_REIGAI.AREA_CD ");
//				sb.append("	) AS AREA_NA ");
//				sb.append("		,R_TENSYOHIN_REIGAI.BUTURYU_KB ");
//				sb.append("		,(SELECT ");
//				sb.append("			KANJI_RN ");
//				sb.append("		  FROM ");
//				sb.append("			R_NAMECTF ");
//				sb.append("		  WHERE ");
//				sb.append("			  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.DISTRIBUTION_DIVISION + "' ");
//				sb.append("		  AND CODE_CD = R_TENSYOHIN_REIGAI.BUTURYU_KB ");
//			    sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//				sb.append("		) AS BUTURYU_NA ");
//				sb.append("		,R_TENSYOHIN_REIGAI.TEN_ZAIKO_KB ");
////				↓↓移植（2006.05.11）↓↓
//				sb.append("     ,(CASE R_TENSYOHIN_REIGAI.TEN_ZAIKO_KB WHEN '" + mst001901_MiseZaikoKbDictionary.KAITORI.getCode() + "' ");
//				sb.append("                                            THEN '" + mst001901_MiseZaikoKbDictionary.KAITORI.toString() + "' ");
//				sb.append("                                            WHEN '" + mst001901_MiseZaikoKbDictionary.ITAKU.getCode() + "' ");
//				sb.append("                                            THEN '" + mst001901_MiseZaikoKbDictionary.ITAKU.toString() + "' ");
//				sb.append("                                            WHEN '" + mst001901_MiseZaikoKbDictionary.SYOUKA.getCode() + "' ");
//				sb.append("                                            THEN '" + mst001901_MiseZaikoKbDictionary.SYOUKA.toString() + "' ");
//				sb.append("                                            ELSE ''");
//				sb.append("       END) AS TEN_ZAIKO_NA ");
//				↑↑移植（2006.05.11）↑↑
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				sb.append("		,R_TENSYOHIN_REIGAI.INSERT_TS ");
				sb.append("		,R_TENSYOHIN_REIGAI.INSERT_USER_ID ");
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("		,(SELECT ");
//				sb.append("			USER_NA ");
//				sb.append("		  FROM ");
//				sb.append("			SYS_SOSASYA ");
//				sb.append("		  WHERE ");
//				sb.append("				USER_ID = R_TENSYOHIN_REIGAI.INSERT_USER_ID ");
//				sb.append("		  AND HOJIN_CD = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//				sb.append("		) AS INSERT_USER_NA ");
//				sb.append(", (SELECT RIYO_USER_NA FROM R_RIYO_USER ");
//				sb.append("	WHERE RIYO_USER_ID = R_TENSYOHIN_REIGAI.INSERT_USER_ID ");
//				sb.append("	) AS INSERT_USER_NA ");
//				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				sb.append(", (SELECT USER_NA FROM R_PORTAL_USER ");
				sb.append("	WHERE USER_ID = TRIM(R_TENSYOHIN_REIGAI.INSERT_USER_ID) ");
				sb.append("	) AS INSERT_USER_NA ");
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("		,(SELECT ");
//				sb.append("			USER_NA ");
//				sb.append("		  FROM ");
//				sb.append("			SYS_SOSASYA ");
//				sb.append("		  WHERE ");
//				sb.append("				USER_ID = R_TENSYOHIN_REIGAI.UPDATE_USER_ID ");
//				sb.append("		  AND HOJIN_CD = '" + mst000101_ConstDictionary.HOJIN_CD + "' ");
//				sb.append("		) AS UPDATE_USER_NA ");
				sb.append("		,R_TENSYOHIN_REIGAI.UPDATE_TS ");
				sb.append("		,R_TENSYOHIN_REIGAI.UPDATE_USER_ID ");

//				sb.append(", (SELECT RIYO_USER_NA FROM R_RIYO_USER ");
//				sb.append("	WHERE RIYO_USER_ID = R_TENSYOHIN_REIGAI.UPDATE_USER_ID ");
//				sb.append("	) AS UPDATE_USER_NA");
//				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				sb.append(", (SELECT USER_NA FROM R_PORTAL_USER ");
				sb.append("	WHERE USER_ID = TRIM(R_TENSYOHIN_REIGAI.UPDATE_USER_ID) ");
				sb.append("	) AS UPDATE_USER_NA");

//				sb.append("		,R_TENSYOHIN_REIGAI.DELETE_FG ");
				sb.append("		,TO_CHAR(R_TENSYOHIN_REIGAI.GENTANKA_VL,'9999990.99') AS GENTANKA_VL_STR ");
				sb.append("		,TO_CHAR(R_TENSYOHIN_REIGAI.BAITANKA_VL,'9999990') AS BAITANKA_VL_STR ");
				sb.append("		,TO_CHAR(R_TENSYOHIN_REIGAI.MAX_HACHUTANI_QT,'9990.9') AS MAX_HACHUTANI_QT_STR ");
				//生鮮用 Start
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("	    ,TRUNC(R_TENSYOHIN_REIGAI.BAITANKA_VL - R_TENSYOHIN_REIGAI.BAITANKA_VL * "+ mst005401_SyouhizeirtuDictionary.ZEIRITU.getCode()
//																+ " / (100 + " + mst005401_SyouhizeirtuDictionary.ZEIRITU.getCode() + "),0) ");
//				sb.append("		AS HONTAI_VL ");
//				sb.append("		,R_TENSYOHIN_REIGAI.HACHU_PATTERN_1_KB ");
//				sb.append("		,(SELECT ");
//				sb.append("			KANJI_RN ");
//				sb.append("		  FROM ");
//				sb.append("			R_NAMECTF ");
//				sb.append("		  WHERE ");
//				sb.append("			  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.ORDER_PATTERN + "' ");
//				sb.append("		  AND CODE_CD = R_TENSYOHIN_REIGAI.HACHU_PATTERN_1_KB ");
//			    sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//				sb.append("		) AS HACHU_PATTERN_1_NA ");
//				sb.append("		,R_TENSYOHIN_REIGAI.HACHU_PATTERN_2_KB ");
//				sb.append("		,(SELECT ");
//				sb.append("			KANJI_RN ");
//				sb.append("		  FROM ");
//				sb.append("			R_NAMECTF ");
//				sb.append("		  WHERE ");
//				sb.append("			  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.ORDER_PATTERN + "' ");
//				sb.append("		  AND CODE_CD = R_TENSYOHIN_REIGAI.HACHU_PATTERN_2_KB ");
//				sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//				sb.append("		) AS HACHU_PATTERN_2_NA ");
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				sb.append("		,R_TENSYOHIN_REIGAI.BIN_1_KB ");

				// ===BEGIN=== Modify by kou 2006/10/16
////				↓↓移植（2006.05.11）↓↓
//				sb.append(", (SELECT KANJI_RN FROM R_NAMECTF ");
//				sb.append("	WHERE  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.SERVICE_DELIVERY + "' ");
//				sb.append("		  AND CODE_CD = R_TENSYOHIN_REIGAI.BIN_1_KB ");
//				sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//				sb.append("		) AS BIN_1_NA ");
////				↑↑移植（2006.05.11）↑↑

				sb.append(", CASE WHEN R_TENSYOHIN_REIGAI.BIN_1_KB IS NULL THEN '' ");
				sb.append(" 	 WHEN R_TENSYOHIN_REIGAI.BIN_1_KB = '' THEN ''");
				sb.append(" 	 ELSE ");
				sb.append("  	( SELECT KANJI_RN ");
				sb.append(" 		 FROM TB_BIN ");
				sb.append(" 		WHERE CODE_CD = R_TENSYOHIN_REIGAI.BIN_1_KB ");
				sb.append("  ) END BIN_1_NA");
				// ===END=== Modify by kou 2006/10/16

				sb.append("		,R_TENSYOHIN_REIGAI.BIN_2_KB ");
//				↓↓移植（2006.05.11）↓↓

				// ===BEGIN=== Modify by kou 2006/10/16
//				sb.append(", (SELECT KANJI_RN FROM R_NAMECTF ");
//				sb.append("	WHERE  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.SERVICE_DELIVERY + "' ");
//				sb.append("		  AND CODE_CD = R_TENSYOHIN_REIGAI.BIN_2_KB ");
//				sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//				sb.append("		) AS BIN_2_NA ");

				sb.append(", CASE WHEN R_TENSYOHIN_REIGAI.BIN_2_KB IS NULL THEN '' ");
				sb.append(" 	 WHEN R_TENSYOHIN_REIGAI.BIN_2_KB = '' THEN '' ");
				sb.append(" 	 ELSE ");
				sb.append(" ( SELECT KANJI_RN ");
				sb.append(" 	 FROM TB_BIN ");
				sb.append(" 	WHERE CODE_CD = R_TENSYOHIN_REIGAI.BIN_2_KB ");
				sb.append("		) END BIN_2_NA ");
				// ===END=== Modify by kou 2006/10/16

//				↓↓2006.07.04 chencl カスタマイズ修正↓↓
				sb.append("     ,R_TENSYOHIN_REIGAI.YUSEN_BIN_KB ");

				// ===BEGIN=== Modify by kou 2006/10/16
//				sb.append(", (SELECT KANJI_RN FROM R_NAMECTF ");
//				sb.append("	WHERE  SYUBETU_NO_CD = '" + mst000101_ConstDictionary.SERVICE_DELIVERY + "' ");
//				sb.append("		  AND CODE_CD = R_TENSYOHIN_REIGAI.YUSEN_BIN_KB ");
//				sb.append("       AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
//				sb.append("		) AS YUSEN_BIN_NA ");

				sb.append(", CASE WHEN R_TENSYOHIN_REIGAI.YUSEN_BIN_KB IS NULL THEN '' ");
				sb.append(" 	 WHEN R_TENSYOHIN_REIGAI.YUSEN_BIN_KB = '' THEN '' ");
				sb.append(" 	 ELSE ");
				sb.append(" ( SELECT KANJI_RN ");
				sb.append(" 	 FROM TB_BIN ");
				sb.append(" 	WHERE CODE_CD = R_TENSYOHIN_REIGAI.YUSEN_BIN_KB ");
				sb.append("		) END YUSEN_BIN_NA ");
				// ===END=== Modify by kou 2006/10/16

//				↑↑2006.07.04 chencl カスタマイズ修正↑↑
//				↑↑移植（2006.05.11）↑↑
				//生鮮用 End
// ↓↓仕様変更（2005.08.04）↓↓
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("		,R_TENSYOHIN_REIGAI.SINKI_TOROKU_DT ");
//				sb.append("     ,R_TENSYOHIN_REIGAI.HENKO_DT ");
				sb.append("		,R_TENSYOHIN_REIGAI.DELETE_FG ");
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
// ↑↑仕様変更（2005.08.04）↑↑
				sb.append(" FROM ");
				sb.append("		 R_TENSYOHIN_REIGAI ");
				sb.append("	,( ");
				sb.append(getTenpoCdSQL1(map));
				sb.append("	) TBL1 ");
				sb.append("	WHERE ");
				sb.append("		R_TENSYOHIN_REIGAI.TENPO_CD = TBL1.TENPO_CD ");
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 ) {
//					sb.append(" AND ");
//					sb.append("R_TENSYOHIN_REIGAI.hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
//				}

				if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 ) {
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.bumon_cd = '" + conv.convertWhereString( (String)map.get("bumon_cd") ) + "'");
				}
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 ) {
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
				}
				if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 ) {
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
				}
				// ===BEGIN=== Modify by kou 2006/10/17
				//if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 ) {
				if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0
					&& !"GET_ALL".equals(map.get("yuko_dt")) ) {
				// ===END=== Modify by kou 2006/10/17
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
				}
				if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 ){
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
				}
//				↓↓2006.07.26 chencl カスタマイズ修正↓↓
				sb.append(" ORDER BY R_TENSYOHIN_REIGAI.TENPO_CD");
//				↑↑2006.07.26 chencl カスタマイズ修正↑↑
			} else {
				sb.append("SELECT ");
				sb.append("		 DISTINCT ");
				sb.append("		 '0' AS SENTAKU ");
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("		,R_TENSYOHIN_REIGAI.HANKU_CD ");
				sb.append("		,R_TENSYOHIN_REIGAI.BUMON_CD ");
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				sb.append("		,R_TENSYOHIN_REIGAI.SYOHIN_CD ");
				sb.append("		,TBL1.TENPO_CD ");
				sb.append("		,TBL1.TENPO_NA ");
//				↓↓移植（2006.05.11）↓↓
				sb.append("		,CAST(NULL AS CHAR) AS YUKO_DT ");
				sb.append("		,CAST(NULL AS CHAR) AS TEN_HACHU_ST_DT ");
				sb.append("		,CAST(NULL AS CHAR) AS TEN_HACHU_ED_DT ");
				sb.append("		,CAST(NULL AS CHAR) AS GENTANKA_VL ");
				sb.append("		,CAST(NULL AS CHAR) AS  GENTANKA_VL_EN ");
				sb.append("		,CAST(NULL AS CHAR) AS  GENTANKA_VL_SEN ");
				sb.append("		,CAST(NULL AS CHAR) AS BAITANKA_VL ");
				sb.append("		,CAST(NULL AS CHAR) AS MAX_HACHUTANI_QT ");
				sb.append("		,CAST(NULL AS CHAR) AS EOS_KB ");
				sb.append("		,CAST(NULL AS CHAR) AS EOS_NA ");
				sb.append("		,CAST(NULL AS CHAR) AS SIIRESAKI_CD ");
				sb.append("		,CAST(NULL AS CHAR) AS SIIRESAKI_NA ");
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("		,CAST(NULL AS CHAR) AS BUTURYU_KB ");
//				sb.append("		,CAST(NULL AS CHAR) AS BUTURYU_NA ");
//				sb.append("		,CAST(NULL AS CHAR) AS TEN_ZAIKO_KB ");
//				sb.append("		,CAST(NULL AS CHAR) AS TEN_ZAIKO_NA ");
//				sb.append("		,CAST(NULL AS CHAR) AS AREA_CD ");
//				sb.append("		,CAST(NULL AS CHAR) AS AREA_NA ");
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑

				sb.append("		,CAST(NULL AS CHAR) AS INSERT_TS ");
				sb.append("		,CAST(NULL AS CHAR) AS INSERT_USER_ID ");
				sb.append("		,CAST(NULL AS CHAR) AS INSERT_USER_NA ");
				sb.append("		,CAST(NULL AS CHAR) AS UPDATE_TS ");
				sb.append("		,CAST(NULL AS CHAR) AS UPDATE_USER_ID ");
				sb.append("		,CAST(NULL AS CHAR) AS UPDATE_USER_NA ");
				sb.append("		,CAST(NULL AS CHAR) AS DELETE_FG ");
				sb.append("		,CAST(NULL AS CHAR) AS GENTANKA_VL_STR ");
				sb.append("		,CAST(NULL AS CHAR) AS BAITANKA_VL_STR ");
				sb.append("		,CAST(NULL AS CHAR) AS MAX_HACHUTANI_QT_STR ");
				//生鮮用 Start
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				sb.append("		,CAST(NULL AS CHAR) AS HONTAI_VL ");
//				sb.append("		,CAST(NULL AS CHAR) AS HACHU_PATTERN_1_KB ");
//				sb.append("		,CAST(NULL AS CHAR) AS HACHU_PATTERN_1_NA ");
//				sb.append("		,CAST(NULL AS CHAR) AS HACHU_PATTERN_2_KB ");
//				sb.append("		,CAST(NULL AS CHAR) AS HACHU_PATTERN_2_NA ");
				sb.append("		,CAST(NULL AS CHAR) AS BIN_1_KB ");
				sb.append("		,CAST(NULL AS CHAR) AS BIN_1_NA ");
				sb.append("		,CAST(NULL AS CHAR) AS BIN_2_KB ");
				sb.append("		,CAST(NULL AS CHAR) AS BIN_2_NA ");
				sb.append("		,CAST(NULL AS CHAR) AS YUSEN_BIN_KB ");
				sb.append("		,CAST(NULL AS CHAR) AS YUSEN_BIN_NA ");
// ↓↓仕様変更（2005.08.04）↓↓
//				sb.append("		,CAST(NULL AS CHAR) AS SINKI_TOROKU_DT ");
//				sb.append("		,CAST(NULL AS CHAR) AS HENKO_DT ");
// ↑↑仕様変更（2005.08.04）↑↑
//				↑↑移植（2006.05.11）↑↑
				////↑↑2006.07.04 chencl カスタマイズ修正↑↑
				//生鮮用 End
				sb.append(" FROM ");
				sb.append("		 R_TENSYOHIN_REIGAI ");
				sb.append("	,( ");
				sb.append(getTenpoCdSQL1(map));
				sb.append("	) TBL1 ");
				sb.append("	WHERE ");
				sb.append("		R_TENSYOHIN_REIGAI.TENPO_CD = TBL1.TENPO_CD ");
				//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//				if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 ) {
//					sb.append(" AND ");
//					sb.append("R_TENSYOHIN_REIGAI.hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
//				}
				if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 ) {
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.bumon_cd = '" + conv.convertWhereString( (String)map.get("bumon_cd") ) + "'");
				}
				//↑↑2006.07.04 chencl カスタマイズ修正↑↑
				if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 ) {
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
				}
				if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 ) {
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
				}
				if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 ) {
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
				}
//				↓↓2006.07.26 chencl カスタマイズ修正↓↓
				if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 ){
					sb.append(" AND ");
					sb.append("R_TENSYOHIN_REIGAI.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
				}
				sb.append(" ORDER BY TBL1.TENPO_CD");
//				↑↑2006.07.26 chencl カスタマイズ修正↑↑
			}
		}

		return sb.toString();
	}

	/**
	 * 店舗コード取得SQLの生成（新規時）
	 * @return String SQL文字列
	 */
	private String getTenpoCdSQL0( Map map )
	{
		StringBuffer sb = new StringBuffer();

		// ===BEGIN=== Modify by kou 2006/10/26
		// 下記のSQLは開発環境のDB2で正しく動くが、本番のDB2で動きがおかしい場合があった。
		// 本番のDB2のパーチの対応不完全のせいだと思いますが、問題を避けるため、SQLを書き換え。

//		//全店舗（営業店舗且つ削除されていない）
//		sb.append(" SELECT ");
//		sb.append(" 	 TENPO_CD ");
////		店舗に名称が入っていないとおかしくなるため by kema 06.09.21
//		sb.append(" 	,case WHEN KANJI_RN is null THEN '' else KANJI_RN end AS TENPO_NA ");
//		sb.append("	FROM ");
//		sb.append(" 	R_TENPO ");
//		sb.append("	WHERE ");
//		sb.append(" 	TENPO_KB  = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//		// ===BEGIN=== Add by kou 2006/10/11
//		sb.append(" AND	DELETE_FG = '")
//			.append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//		// ===END=== Add by kou 2006/10/11
////		↓↓2006.07.26 chencl カスタマイズ修正↓↓
////		sb.append(" AND	DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↑↑2006.07.26 chencl カスタマイズ修正↑↑
//		//現在有効レコードを除外
////		↓↓移植（2006.05.23）↓↓
//		sb.append(" EXCEPT ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append(" SELECT ");
//		sb.append(" 	 R_TENSYOHIN_REIGAI.TENPO_CD ");
////		店舗に名称が入っていないとおかしくなるため by kema 06.09.21
//		sb.append(" 	,(SELECT case WHEN KANJI_RN is null THEN '' else KANJI_RN end FROM R_TENPO ");
//		sb.append(" WHERE TENPO_CD = R_TENSYOHIN_REIGAI.TENPO_CD) AS TENPO_NA ");
//		sb.append(" FROM ");
//		sb.append(" 	 R_TENSYOHIN_REIGAI ");
//		sb.append(" 	,( ");
//		sb.append(" 	SELECT ");
//		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
////		sb.append(" 		 HANKU_CD ");
//		sb.append(" 		 BUMON_CD ");
//		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
//		sb.append(" 		,SYOHIN_CD ");
//		sb.append(" 		,TENPO_CD ");
//		sb.append(" 		,MAX(YUKO_DT) AS YUKO_DT ");
//		sb.append(" 	FROM ");
//		sb.append("  		R_TENSYOHIN_REIGAI ");
//		sb.append(" 	WHERE ");
//		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
////		sb.append("  		HANKU_CD = '" + map.get("hanku_cd") + "' ");
//		sb.append("  		BUMON_CD = '" + map.get("bumon_cd") + "' ");
//		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
//		sb.append(" 	AND SYOHIN_CD = '" + map.get("syohin_cd") + "' ");
//// 2006.01.23 Y.Inaba Mod ↓
////		sb.append(" 	AND YUKO_DT <= TO_CHAR(SYSDATE,'YYYYMMDD') ");
//		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 ) {
//			sb.append("AND YUKO_DT <= '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
//		} else{
//			sb.append(" AND YUKO_DT <= '"+ RMSTDATEUtil.getMstDateDt() +"'");
//		}
//// 2006.01.23 Y.Inaba Mod ↑
//		sb.append(" 	GROUP BY ");
//		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
////		sb.append(" 		 HANKU_CD ");
//		sb.append(" 		 BUMON_CD ");
//		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
//		sb.append(" 		,SYOHIN_CD ");
//		sb.append(" 		,TENPO_CD ");
//		sb.append(" 	) TBL1 ");
//		sb.append(" WHERE ");
//		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
////		sb.append(" 	R_TENSYOHIN_REIGAI.HANKU_CD = TBL1.HANKU_CD ");
//		sb.append(" 	R_TENSYOHIN_REIGAI.BUMON_CD = TBL1.BUMON_CD ");
//		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
//		sb.append(" AND R_TENSYOHIN_REIGAI.SYOHIN_CD = TBL1.SYOHIN_CD ");
//		sb.append(" AND R_TENSYOHIN_REIGAI.TENPO_CD = TBL1.TENPO_CD ");
//		sb.append(" AND R_TENSYOHIN_REIGAI.YUKO_DT = TBL1.YUKO_DT ");
//		sb.append(" AND R_TENSYOHIN_REIGAI.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		//予約レコードを除外
////		↓↓移植（2006.05.23）↓↓
//		sb.append(" EXCEPT ");
////		↑↑移植（2006.05.23）↑↑
//		sb.append(" SELECT ");
//		sb.append(" 	 DISTINCT ");
//		sb.append(" 	 TENPO_CD ");
////		店舗に名称が入っていないとおかしくなるため by kema 06.09.21
//		sb.append(" 	,(SELECT case WHEN KANJI_RN is null THEN '' else KANJI_RN end FROM R_TENPO ");
//		sb.append(" WHERE TENPO_CD = R_TENSYOHIN_REIGAI.TENPO_CD) AS TENPO_NA ");
//		sb.append(" FROM ");
//		sb.append(" 	 R_TENSYOHIN_REIGAI ");
//		sb.append(" WHERE ");
////		↓↓2006.07.04 chencl カスタマイズ修正↓↓
////		sb.append(" 	HANKU_CD = '" + map.get("hanku_cd") + "' ");
//		sb.append(" 	BUMON_CD = '" + map.get("bumon_cd") + "' ");
////		↑↑2006.07.04 chencl カスタマイズ修正↑↑
//		sb.append(" AND SYOHIN_CD = '" + map.get("syohin_cd") + "' ");
//// 2006.01.23 Y.Inaba Mod ↓
////		sb.append(" AND YUKO_DT > TO_CHAR(SYSDATE,'YYYYMMDD') ");
//		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 ) {
//			sb.append("AND YUKO_DT > '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
//		} else{
//			sb.append(" AND YUKO_DT > '"+ RMSTDATEUtil.getMstDateDt() +"'");
//		}
//// 2006.01.23 Y.Inaba Mod ↑

	 sb.append(" SELECT ");
	 sb.append(" 		  TMP.TENPO_CD ");
	 sb.append(" 		, R_TENPO.KANJI_RN TENPO_NA ");
	 sb.append("   FROM	");
	 sb.append(" ( ");
	 //全店舗（営業店舗且つ削除されていない）
	 sb.append(" SELECT ");
	 sb.append(" 	 TENPO_CD ");
	 sb.append("	FROM R_TENPO ");
	 sb.append("	WHERE ");
	 sb.append(" 	TENPO_KB  = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
	 sb.append(" AND	DELETE_FG = '")
		 .append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
	 sb.append(" EXCEPT ");
	 //現在有効レコードを除外
	 sb.append(" SELECT ");
	 sb.append(" 	 R_TENSYOHIN_REIGAI.TENPO_CD ");
	 sb.append(" FROM ");
	 sb.append(" 	 R_TENSYOHIN_REIGAI ");
	 sb.append(" 	,( ");
	 sb.append(" 	SELECT ");
	 sb.append(" 		 BUMON_CD ");
	 sb.append(" 		,SYOHIN_CD ");
	 sb.append(" 		,TENPO_CD ");
	 sb.append(" 		,MAX(YUKO_DT) AS YUKO_DT ");
	 sb.append(" 	FROM ");
	 sb.append("  		R_TENSYOHIN_REIGAI ");
	 sb.append(" 	WHERE ");
	 sb.append("  		BUMON_CD = '").append(map.get("bumon_cd")).append("' ");
	 sb.append(" 	AND SYOHIN_CD = '").append(map.get("syohin_cd")).append("' ");
	 if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 ) {
		 sb.append("AND YUKO_DT <= '")
		 	.append(conv.convertWhereString( (String)map.get("yuko_dt") )).append("'");
	 } else{
		 sb.append(" AND YUKO_DT <= '")
		 	.append(RMSTDATEUtil.getMstDateDt()).append("'");
	 }
	 sb.append(" 	GROUP BY ");
	 sb.append(" 		 BUMON_CD ");
	 sb.append(" 		,SYOHIN_CD ");
	 sb.append(" 		,TENPO_CD ");
	 sb.append(" 	) TBL1 ");
	 sb.append(" WHERE ");
	 sb.append(" 	R_TENSYOHIN_REIGAI.BUMON_CD = TBL1.BUMON_CD ");
	 sb.append(" AND R_TENSYOHIN_REIGAI.SYOHIN_CD = TBL1.SYOHIN_CD ");
	 sb.append(" AND R_TENSYOHIN_REIGAI.TENPO_CD = TBL1.TENPO_CD ");
	 sb.append(" AND R_TENSYOHIN_REIGAI.YUKO_DT = TBL1.YUKO_DT ");
	 sb.append(" AND R_TENSYOHIN_REIGAI.DELETE_FG = '")
	 	.append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
	 sb.append(" EXCEPT ");
	 //予約レコードを除外
	 sb.append(" SELECT ");
	 sb.append(" 	 DISTINCT TENPO_CD ");
	 sb.append(" FROM ");
	 sb.append(" 	 R_TENSYOHIN_REIGAI ");
	 sb.append(" WHERE ");
	 sb.append(" 	 BUMON_CD = '").append(map.get("bumon_cd")).append("' ");
	 sb.append(" AND SYOHIN_CD = '").append(map.get("syohin_cd")).append("' ");
	 if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 ) {
		 sb.append("AND YUKO_DT > '")
		 	.append(conv.convertWhereString( (String)map.get("yuko_dt") )).append("'");
	 } else{
		 sb.append(" AND YUKO_DT > '").append(RMSTDATEUtil.getMstDateDt()).append("'");
	 }
	 sb.append(" ) TMP ");
	 sb.append(" , R_TENPO ");
	 sb.append(" WHERE ");
	 sb.append(" 		TMP.TENPO_CD = R_TENPO.TENPO_CD ");
	// ===END== Modify by kou 2006/10/26

		return sb.toString();
	}

	/**
	 * 店舗コード取得SQLの生成（新規時以外）
	 * @return String SQL文字列
	 */
	private String getTenpoCdSQL1( Map map )
	{
		StringBuffer sb = new StringBuffer();

		// ===BEGIN=== Modify by kou 2006/10/16
		sb.append(" SELECT ");
		sb.append(" 	 DISTINCT ");
		sb.append(" 	 TENPO_CD ");
		sb.append(" 	,(SELECT KANJI_RN FROM R_TENPO WHERE TENPO_CD = R_TENSYOHIN_REIGAI.TENPO_CD) AS TENPO_NA ");
		sb.append(" FROM ");
		sb.append(" 	R_TENSYOHIN_REIGAI ");
		sb.append(" WHERE ");
		//↓↓2006.07.04 chencl カスタマイズ修正↓↓
//		sb.append(" 	HANKU_CD = '" + map.get("hanku_cd") + "' ");
		sb.append(" 	BUMON_CD = '" + map.get("bumon_cd") + "' ");
		//↑↑2006.07.04 chencl カスタマイズ修正↑↑
		sb.append(" AND SYOHIN_CD = '" + map.get("syohin_cd") + "' ");

//		sb.append(" SELECT ");
//		sb.append("			TMP.TENPO_CD, ");
//		sb.append("			R_TENPO.KANJI_RN AS TENPO_NA ");
//		sb.append("	  FROM ( ");
//		sb.append("			SELECT DISTINCT RTR.TENPO_CD ");
//		sb.append("			  FROM R_TENSYOHIN_REIGAI RTR ");
//		sb.append("			 WHERE ");
//		sb.append("					BUMON_CD = '").append(map.get("bumon_cd")).append("'");
//		sb.append("				AND SYOHIN_CD = '").append(map.get("syohin_cd")).append("'");
//		sb.append("			) TMP ");
////		sb.append("			LEFT JOIN R_TENPO ");
////		sb.append("			ON R_TENPO.TENPO_CD = TMP.TENPO_CD ");
//		sb.append("			, R_TENPO ");
//		sb.append("	 WHERE R_TENPO.TENPO_CD = TMP.TENPO_CD ");
		// ===END=== Modify by kou 2006/10/16

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
