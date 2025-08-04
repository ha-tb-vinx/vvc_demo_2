/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するのDMクラス(DmCreatorにより自動生成)</p>
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
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
public class mst450101_TenbetuSiiresakiListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	private ArrayList tenpoItiran			= null;	//店舗配列
//    ↓↓2006.07.03 maojm カスタマイズ修正↓↓
	private String tenpoNmItiran       = null;    //店舗名称配列
	private String kanri_kb				= null;	//管理区分
//	private String kanri_cd				= null;	//管理コード
	private String bumon_cd				= null;	//部門コード
	private int inti = 0;                                 
//    ↑↑2006.07.03 maojm カスタマイズ修正↑↑
	private String ten_siiresaki_kanri_cd	= null;	//店仕入先管理コード
	private String processingdivision 		= null;	//処理状況

	/**
	 * コンストラクタ
	 */
	public mst450101_TenbetuSiiresakiListDM()
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
		mst450101_TenbetuSiiresakiListBean bean = new mst450101_TenbetuSiiresakiListBean();
		bean.setAllTenpoFlg( rest.getString("alltenpo_flg") );
//	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓
//		bean.setKanriKb( rest.getString("kanri_kb") );
//		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setBumonCd( rest.getString("kanri_cd") );
		bean.setSentaku( rest.getString("sentaku") );
//	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑	
		if(mst000401_LogicBean.isSingleNumber(rest.getString("siiresaki_cd"))){
			bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		} else {
			bean.setSiiresakiCd( "" );
		}		
		bean.setKanjiRn( mst000401_LogicBean.chkNullString(rest.getString("kanji_rn")).trim() );
		bean.setYubinCd( mst000401_LogicBean.chkNullString(rest.getString("yubin_cd")).trim() );
		bean.setAddressKanji1Na( mst000401_LogicBean.chkNullString(rest.getString("address_kanji1_na")).trim() );
		bean.setAllTenpoTs( mst000401_LogicBean.chkNullString(rest.getString("all_tenpo_ts")).trim() );
//		↓↓2006.11.14 H.Yamamoto カスタマイズ修正↓↓
////	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓
////		bean.setTenpoCdl( rest.getString("tenpo_cdl") );
////		bean.setTenpoCdl( tenpoItiran.toString());
////		bean.setTKanjiRnl( rest.getString("tkanji_rnl") );
//		bean.setTKanjiRnl(tenpoNmItiran);
////	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑		
		bean.setTenpoCdl("");
		bean.setTKanjiRnl("");
		bean.setFlgl("");
		bean.setDefaultFlgl("");
		bean.setUpdateTsl("");
		bean.setInsertTsl("");
//		↑↑2006.11.14 H.Yamamoto カスタマイズ修正↑↑
//		bean.setFlgl( rest.getString("flgl") );
//		bean.setUpdateTsl( mst000401_LogicBean.chkNullString(rest.getString("update_tsl")).trim() );
//		bean.setInsertTsl( mst000401_LogicBean.chkNullString(rest.getString("insert_tsl")).trim() );
		bean.setCreateDatabase();
		return bean;
	}
	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
//	public String getSelectSql( Map map )
//	{
//
//		String disp = "";
//		
//		//店舗配列が設定されていないので空文字列を戻す
//		if (tenpoItiran.size() == 0) {
//			return "";
//		}
//
//		//新規・修正
//		if(processingdivision.equals(mst000101_ConstDictionary.PROCESS_INSERT)
//			|| processingdivision.equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
//				disp = "EDIT";
//				
//		//照会・削除
//		} else {
//				disp = "VIEW";
//		}
//
////		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//		StringBuffer sb = new StringBuffer();
//
//		sb.append(" SELECT * FROM ( ");
//		sb.append(" SELECT DISTINCT ");
//		sb.append(" 	CASE WHEN TENPO_CD = '000000' THEN '1' ");
//		sb.append(" 		 ELSE '0' ");
//		sb.append(" 	 END AS ALLTENPO_FLG, ");
////	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓
////		sb.append("		NVL(TBL0.KANRI_KB,'" + getKanriKb() + "') AS KANRI_KB, ");
////		sb.append("		NVL(TBL0.KANRI_CD,'" + getKanriCd() + "') AS KANRI_CD, ");
//		sb.append("		NVL(TBL0.KANRI_KB,'" + mst000901_KanriKbDictionary.BUMON.getCode() + "') AS KANRI_KB, ");
//		sb.append("		NVL(TBL0.KANRI_CD,'" +StringUtility.charFormat( getBumonCd(),4,"0",true) + "') AS KANRI_CD, ");
//	
////	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑
//		sb.append("		TBL0.SIIRESAKI_CD, ");
////	    ↓↓2006.07.06 maojm カスタマイズ修正↓↓		
////		sb.append("		KANJI_RN, ");
//		sb.append("		tbl.KANJI_RN KANJI_RN, ");
////	    ↑↑2006.07.06 maojm カスタマイズ修正↑↑		
//		sb.append("		YUBIN_CD, ");
//		sb.append("		ADDRESS_KANJI1_NA, ");
//		
//		
//		//ALL0店舗更新日
//		sb.append(" nvl((SELECT UPDATE_TS ");
//		sb.append("      FROM R_TENBETU_SIIRESAKI T1 ");
//		sb.append("      WHERE DELETE_FG = '0' ");
//		
////		BUGNO-S006 20050421 S.Murata START
////												+ " AND	T1.KANRI_KB = TBL.KANRI_KB "
////												+ "	AND T1.KANRI_CD = TBL.KANRI_CD "
////												+ "	AND T1.SIIRESAKI_CD = TBL.SIIRESAKI_CD "
////	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓	
////		sb.append("      AND T1.KANRI_KB = '" + getKanriKb() + "' ");
////		sb.append("      AND T1.KANRI_CD = '" + getKanriCd() + "' ");
//		sb.append("      AND T1.KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//		sb.append("      AND T1.KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");
////	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑	
//		sb.append("      AND T1.TENPO_CD = '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' ");
//		sb.append("      AND T1.TEN_SIIRESAKI_KANRI_CD = '" + getTenSiiresakiKanriCd() + "') , '@@@@@@@@') ALL_TENPO_TS, ");
//		
//
//		//店舗コード
//		for(int i = 0; i < tenpoItiran.size(); i++){
//			sb.append("	( SELECT TENPO_CD FROM R_TENPO ");
//			sb.append("	  WHERE DELETE_FG = '0' ");
//			sb.append("   AND TENPO_CD = '" + tenpoItiran.get(i) + "') ");
//			if(i < tenpoItiran.size() - 1){
//				sb.append(" || ',' || ");
//			}
//		}
//		sb.append("		AS TENPO_CDL, ");
//
//		//店舗略称
//		for(int i = 0; i < tenpoItiran.size(); i++){
//			sb.append("	( SELECT TRIM(KANJI_RN) FROM R_TENPO ");
//			sb.append("	  WHERE DELETE_FG = '0' ");
//			sb.append("	  AND TENPO_CD = '" + tenpoItiran.get(i) + "') ");
//			if(i < tenpoItiran.size() - 1){
//				sb.append(" || ',' || ");
//			}
//		}
//		sb.append("		AS TKANJI_RNL, ");
//
//		//店舗存在フラグ
//		for(int i = 0; i < tenpoItiran.size(); i++){
//			sb.append("	NVL((SELECT '1' AS COL FROM R_TENBETU_SIIRESAKI T1 ");
//			sb.append("	     WHERE DELETE_FG = '0' ");
////			BUGNO-S006 20050421 S.Murata START
////													+ " AND T1.KANRI_KB = TBL.KANRI_KB "
////													+ "	AND T1.KANRI_CD = TBL.KANRI_CD "
////													+ "	AND T1.SIIRESAKI_CD = TBL.SIIRESAKI_CD "
////		    ↓↓2006.07.03 maojm カスタマイズ修正↓↓		
////			sb.append("	      AND T1.KANRI_KB = '" + getKanriKb() + "' ");
////			sb.append("	      AND T1.KANRI_CD = '" + getKanriCd() + "' ");
//			sb.append("	      AND T1.KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//			sb.append("	      AND T1.KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");
////		    ↑↑2006.07.03 maojm カスタマイズ修正↑↑
//			sb.append("	      AND T1.SIIRESAKI_CD = TBL0.SIIRESAKI_CD ");
////			sb.append("	      AND T1.TENPO_CD = '" + tenpoItiran.get(i) + "' ");
//
//			sb.append("	      AND T1.TENPO_CD IN( '" + tenpoItiran.get(i) + "', '000000') ");
//
//			sb.append("	      AND T1.TEN_SIIRESAKI_KANRI_CD = '" + getTenSiiresakiKanriCd() + "') , '0') ");
//			if(i <tenpoItiran.size() - 1){
//				sb.append(" || ',' || ");
//			}
//		}
//		sb.append("		AS FLGL, ");
//
//		//レコード登録日時
//		for(int i = 0; i < tenpoItiran.size(); i++){
//			sb.append("nvl((SELECT INSERT_TS from R_TENBETU_SIIRESAKI T1 ");
//			sb.append("     WHERE DELETE_FG = '0' ");
////			BUGNO-S006 20050421 S.Murata START
////													+ " AND	T1.KANRI_KB = TBL.KANRI_KB "
////													+ "	AND T1.KANRI_CD = TBL.KANRI_CD "
////													+ "	AND T1.SIIRESAKI_CD = TBL.SIIRESAKI_CD "
////		    ↓↓2006.07.03 maojm カスタマイズ修正↓↓			
////			sb.append("     AND	T1.KANRI_KB = '" + getKanriKb() + "' ");
////			sb.append("     AND T1.KANRI_CD = '" + getKanriCd() + "' ");
//			sb.append("	      AND T1.KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//			sb.append("	      AND T1.KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");
////		    ↑↑2006.07.03 maojm カスタマイズ修正↑↑	
//			sb.append("     AND T1.SIIRESAKI_CD = TBL0.SIIRESAKI_CD ");
//			sb.append("     AND T1.TENPO_CD = '" + tenpoItiran.get(i) + "' ");
//			sb.append("     AND T1.TEN_SIIRESAKI_KANRI_CD = '" + getTenSiiresakiKanriCd() + "') , '@@@@@@@@') ");
//			if(i <tenpoItiran.size() - 1){
//				sb.append(" || ',' || ");
//			}
//		}
//		sb.append(" AS INSERT_TSL, ");
//
//		//レコード更新日時
//		for(int i = 0; i < tenpoItiran.size(); i++){
//			sb.append("nvl((SELECT UPDATE_TS from R_TENBETU_SIIRESAKI T1 ");
//			sb.append("     WHERE DELETE_FG = '0' ");
//
////			BUGNO-S006 20050421 S.Murata START
////													+ " AND	T1.KANRI_KB = TBL.KANRI_KB "
////													+ "	AND T1.KANRI_CD = TBL.KANRI_CD "
////													+ "	AND T1.SIIRESAKI_CD = TBL.SIIRESAKI_CD "
////		    ↓↓2006.07.03 maojm カスタマイズ修正↓↓	
////			sb.append("     AND	T1.KANRI_KB = '" + getKanriKb() + "' ");
////			sb.append("     AND T1.KANRI_CD = '" + getKanriCd() + "' ");
//			sb.append("	      AND T1.KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//			sb.append("	      AND T1.KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");
////		    ↑↑2006.07.03 maojm カスタマイズ修正↑↑		
//			sb.append("     AND T1.SIIRESAKI_CD = TBL0.SIIRESAKI_CD ");
//			sb.append("     AND T1.TENPO_CD = '" + tenpoItiran.get(i) + "' ");
//			sb.append("     AND T1.TEN_SIIRESAKI_KANRI_CD = '" + getTenSiiresakiKanriCd() + "') , '@@@@@@@@') ");
//			if(i <tenpoItiran.size() - 1){
//				sb.append(" || ',' || ");
//			}
//		}
//		sb.append(" AS UPDATE_TSL ");
//		
//		sb.append("	FROM ");
//		sb.append("		(SELECT * FROM ");
//		sb.append(" 	  ( (SELECT ");
//		sb.append("			  TENPO_CD, ");
//		sb.append("			  KANRI_KB, ");
//		sb.append("			  KANRI_CD, ");
//
//		sb.append("			  SIIRESAKI_CD "); 		
//		sb.append("		    FROM ");
//		sb.append("			  R_TENBETU_SIIRESAKI ");
//		sb.append("		    WHERE ");
////	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓
////		sb.append("	 		  KANRI_KB = '" + getKanriKb() + "' ");
////		sb.append("	 		AND	KANRI_CD = '" + getKanriCd() + "' ");
//		sb.append("	 		  KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//		sb.append("	 		AND	KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");	
////	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑
//		sb.append("	 		AND	TEN_SIIRESAKI_KANRI_CD = '" + getTenSiiresakiKanriCd() + "' ");
//		sb.append("	 		AND	DELETE_FG = '0' ");
//		sb.append(" 	    ) ");
//		
//		//新規・修正の場合
//		
//		if(disp.equals("EDIT")){
//			for(int i = 0; i < tenpoItiran.size(); i++){
//				sb.append(" UNION ALL  ");
//				sb.append("	(SELECT '1' AS TENPO_CD,");
////			    ↓↓2006.07.03 maojm カスタマイズ修正↓↓			
////				sb.append("	'" + getKanriKb() + "' AS KANRI_KB,");
////				sb.append(" '"+	getKanriCd() + "' AS KANRI_CD,");
//				sb.append("	'" + mst000901_KanriKbDictionary.BUMON.getCode() + "' AS KANRI_KB,");
//				sb.append(" '"+	StringUtility.charFormat(getBumonCd(),4,"0",true) + "' AS KANRI_CD,");
////			    ↑↑2006.07.03 maojm カスタマイズ修正↑↑	
//				sb.append(" '@' || lpad('" + i + "',5,'0') AS SIIRESAKI_CD FROM DUAL)  ");
//			}
//		}
//
//		
//		sb.append("		)tbla ) TBL0 ");
////      ↓↓移植（2006.05.11）↓↓
//		sb.append(" left outer join	");
//		
//		//仕入先マスタ
////	    ↓↓2006.07.03 maojm カスタマイズ修正↓↓	
////		//選択業種が生鮮の場合
////		if (map.get("gyosyu_id").equals("04")) {			
////			sb.append("		(SELECT * FROM R_SIIRESAKI ");
////			sb.append("	 	 WHERE KANRI_KB = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "' ");
////			sb.append("	 	 AND KANRI_CD = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "' ");
////			sb.append("	 	 AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////			sb.append("      AND TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
////			sb.append("		) TBL ");
////			
////		//選択業種が生鮮以外の場合
////		} else {
////			sb.append("		(SELECT * FROM R_SIIRESAKI ");
////			sb.append("	 	 WHERE KANRI_KB = '" + getKanriKb() + "' ");
////			sb.append("	 	 AND KANRI_CD = '" + getKanriCd() + "' ");
////			sb.append("	 	 AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////			sb.append("      AND TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
////			sb.append("		) TBL ");
//			
////		}
//		
//		sb.append("		(SELECT * FROM R_SIIRESAKI ");
//		sb.append("	 	 WHERE KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//		sb.append("	 	 AND KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");
//		sb.append("	 	 AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		sb.append("      AND TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//		sb.append("		) TBL ");
////	    ↑↑2006.07.03 maojm カスタマイズ修正↑↑	
//		//新規・修正の場合
//		if(disp.equals("EDIT")){
//			sb.append("	on ");
//			sb.append("		TBL0.KANRI_KB     = TBL.KANRI_KB ");
//			sb.append("	AND TBL0.KANRI_CD     = TBL.KANRI_CD ");
//			sb.append("	AND TBL0.SIIRESAKI_CD = TBL.SIIRESAKI_CD ");
//		
//		//照会・削除
//		} else {
//			sb.append("	on ");
////			BUGNO-S006 20050421 S.Murata START
////			sb.append("		TBL0.KANRI_KB     = TBL.KANRI_KB ");
////			sb.append("	AND TBL0.KANRI_CD     = TBL.KANRI_CD ");
////			sb.append("	AND TBL0.SIIRESAKI_CD = TBL.SIIRESAKI_CD ");
//			sb.append("		TBL0.KANRI_KB     = TBL.KANRI_KB ");
//			sb.append("	AND TBL0.KANRI_CD     = TBL.KANRI_CD ");
//			sb.append("	AND TBL0.SIIRESAKI_CD = TBL.SIIRESAKI_CD ");
//
//		}
////      ↑↑移植（2006.05.11）↑↑
//			
//		sb.append(" ORDER BY ");
//		sb.append(" 	TBL0.SIIRESAKI_CD ");
//		sb.append(" ) tablea ");
//
//// 2005.10.23 Y.Inaba 代表店を先頭に表示 START
//		sb.append(" ORDER BY ");
////	    ↓↓2006.07.06 maojm カスタマイズ修正↓↓	
////		sb.append("     ALLTENPO_FLG DESC,SIIRESAKI_CD");
//		sb.append("    kanri_cd,siiresaki_cd");
////	    ↑↑2006.07.06 maojm カスタマイズ修正↑↑		
//// 2005.10.23 Y.Inaba 代表店を先頭に表示 END
//		if(disp.equals("EDIT")){
////		      ↓↓移植（2006.05.11）↓↓
////		        sb.append(" fetch first " + tenpoItiran.size() + " rows only ");
////		      ↑↑移植（2006.05.11）↑↑			
//			}
//		return sb.toString();
//	}
	
//    ↓↓2006.07.18 maojm カスタマイズ修正↓↓
	public String getSelectSql( Map map )
	{
		String disp = "";
		
		//店舗配列が設定されていないので空文字列を戻す
		if (tenpoItiran.size() == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
//		↓↓2006.11.14 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" select  ");
//		sb.append(" distinct ");
//		sb.append("		NVL(TBL0.KANRI_KB,'" + mst000901_KanriKbDictionary.BUMON.getCode() + "') AS KANRI_KB, ");
//		sb.append("		NVL(TBL0.KANRI_CD,'" +StringUtility.charFormat( getBumonCd(),4,"0",true) + "') AS KANRI_CD, ");
//		sb.append(			mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku, ");
//		// ===BEGIN=== Modify by kou 2006/10/13
//		//sb.append("  CASE WHEN TENPO_CD = '000000' THEN '1' ");
//		//sb.append(" 		 ELSE '0' ");
//		//sb.append(" 	 END AS ALLTENPO_FLG, ");
//		sb.append(" 	 '0' AS ALLTENPO_FLG, ");
//		// ===END=== Modify by kou 2006/10/13
//		sb.append("		TBL0.SIIRESAKI_CD, ");
//		sb.append("		tbl.KANJI_RN KANJI_RN, ");
//		sb.append("		YUBIN_CD, ");
//		sb.append("		ADDRESS_KANJI1_NA, ");
//		//ALL0店舗更新日
//		sb.append(" nvl((SELECT UPDATE_TS ");
//		sb.append("      FROM R_TENBETU_SIIRESAKI T1 ");
//		sb.append("      WHERE DELETE_FG = '0' ");
//		sb.append("      AND T1.KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//		sb.append("      AND T1.KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");
//		sb.append("      AND T1.TENPO_CD = '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' ");
//		sb.append("      AND T1.TEN_SIIRESAKI_KANRI_CD = '" + getTenSiiresakiKanriCd() + "') , '@@@@@@@@') ALL_TENPO_TS ");
//		sb.append("	FROM ");
//		sb.append("		(SELECT * FROM ");
//		sb.append(" 	  ( (SELECT ");
//		sb.append("			  TENPO_CD, ");
//		sb.append("			  KANRI_KB, ");
//		sb.append("			  KANRI_CD, ");
//		sb.append("			  SIIRESAKI_CD "); 		
//		sb.append("		    FROM ");
//		sb.append("			  R_TENBETU_SIIRESAKI ");
//		sb.append("		    WHERE ");
//		sb.append("	 		  KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//		sb.append("	 		AND	KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");	
//		sb.append("	 		AND	TEN_SIIRESAKI_KANRI_CD = '" + getTenSiiresakiKanriCd() + "' ");
//		sb.append("	 		AND	DELETE_FG = '0' ");
//		sb.append(" 	    ) ");	
//		sb.append("		)tbla ) TBL0 ");
//		sb.append(" left outer join	");
//		sb.append("		(SELECT * FROM R_SIIRESAKI ");
//		sb.append("	 	 WHERE KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//		sb.append("	 	 AND KANRI_CD = '0000' ");
//		sb.append("	 	 AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		sb.append("      AND TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//		sb.append("		) TBL ");
//		sb.append("	on ");
//	    sb.append("		TBL0.KANRI_KB     = TBL.KANRI_KB ");
////	    sb.append("	AND TBL0.KANRI_CD     = TBL.KANRI_CD ");
//	    sb.append("	AND TBL0.SIIRESAKI_CD = TBL.SIIRESAKI_CD ");
////	  mao	    
//    	sb.append(" where ");
//    	sb.append("   EXISTS ");
//		sb.append("   (SELECT KANRI_CD ");
//		sb.append("    FROM R_TENBETU_SIIRESAKI rts ");
//		sb.append("    WHERE rts.SIIRESAKI_CD=tbl0.SIIRESAKI_CD ");
//		sb.append("   and  rts.KANRI_KB ='" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//		sb.append("	 		AND	rts.KANRI_CD = '" + StringUtility.charFormat(getBumonCd(),4,"0",true) + "' ");	
//		sb.append("	 		AND	rts.TEN_SIIRESAKI_KANRI_CD = '" + getTenSiiresakiKanriCd() + "' ");
//		sb.append("    AND rts.DELETE_FG='0'");
//		// ===BEGIN=== Modify by kou 2006/10/13
//		//sb.append("    AND rts.TENPO_CD IN( '000000'");
//		//for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
//		//		sb.append(", '"+tenpoItiran.get(intCnt)+"'");
//		//}
//		sb.append("    AND rts.TENPO_CD IN( ");
//		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++)
//		{
//			if(intCnt == 0) 
//			{
//				sb.append("'").append(tenpoItiran.get(intCnt)).append("'");
//			}
//			else
//			{
//				sb.append(", '").append(tenpoItiran.get(intCnt)).append("'");
//			}
//		}
//		// ===END=== Modify by kou 2006/10/13
//		sb.append("    ))  ");
////mao	    
//		sb.append(" order by ");
//		sb.append(" kanri_cd,");
//		sb.append(" tbl0.siiresaki_cd ");
//		
//	if(processingdivision.equals(mst000101_ConstDictionary.PROCESS_INSERT)
//	  || processingdivision.equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
//      sb.append(" fetch first " + tenpoItiran.size()  + " rows only ");
//	}
		sb.append(" select  ");
		sb.append(" distinct ");
		sb.append("		rts.KANRI_KB AS KANRI_KB, ");
		sb.append("		rts.KANRI_CD AS KANRI_CD, ");
		sb.append("		'").append(mst000101_ConstDictionary.RECORD_NON_EXISTENCE).append("' AS SENTAKU, ");
		sb.append(" 	'0' AS ALLTENPO_FLG, ");
		sb.append("		rts.SIIRESAKI_CD AS SIIRESAKI_CD, ");
		sb.append("		rs.KANJI_RN AS KANJI_RN, ");
		sb.append("		rs.YUBIN_CD AS YUBIN_CD, ");
		sb.append("		rs.ADDRESS_KANJI1_NA AS ADDRESS_KANJI1_NA, ");
		sb.append("		'@@@@@@@@' AS ALL_TENPO_TS ");
		sb.append("	FROM ");
		sb.append("		R_TENBETU_SIIRESAKI rts ");
		sb.append(" LEFT OUTER JOIN	");
		sb.append("		R_SIIRESAKI rs ");
		sb.append("	ON ");
		sb.append("		rs.KANRI_KB = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
		sb.append("	AND rs.KANRI_CD = '0000' ");
		sb.append("	AND rs.SIIRESAKI_CD = rts.SIIRESAKI_CD ");
		sb.append("	AND rs.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("	AND rs.TOSAN_KB = '").append(mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode()).append("' ");
		sb.append(" WHERE ");
		sb.append("		rts.KANRI_KB ='").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
		sb.append("	AND	rts.KANRI_CD = '").append(StringUtility.charFormat(getBumonCd(),4,"0",true)).append("' ");	
		sb.append("	AND	rts.TEN_SIIRESAKI_KANRI_CD = '").append(getTenSiiresakiKanriCd()).append("' ");
		sb.append("	AND rts.DELETE_FG = '0' ");
		sb.append("	AND rts.TENPO_CD IN( ");
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++)
		{
			if(intCnt == 0) 
			{
				sb.append("'").append(tenpoItiran.get(intCnt)).append("'");
			}
			else
			{
				sb.append(", '").append(tenpoItiran.get(intCnt)).append("'");
			}
		}
		sb.append("		) ");
		sb.append(" ORDER BY ");
		sb.append("		rts.SIIRESAKI_CD ");
//		↑↑2006.11.14 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();	
		
	}	
//    ↑↑2006.07.18 maojm カスタマイズ修正↑↑
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
//    ↓↓2006.07.03 maojm カスタマイズ修正↓↓	
	/**
	 * 管理区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//		public boolean setKanriKb(String kanri_kb)
//		{
//			this.kanri_kb = kanri_kb;
//			return true;
//		}
	/**
	 * 管理区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
//		public String getKanriKb()
//		{
//			return this.kanri_kb;
//		}


	/**
	 * 管理コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
//		public boolean setKanriCd(String kanri_cd)
//		{
//			this.kanri_cd = kanri_cd;
//			return true;
//		}
	/**
	 * 管理コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
//		public String getKanriCd()
//		{
//			return this.kanri_cd;
//		}
//
	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setBumonCd(String bumon_cd)
		{
			this.bumon_cd = bumon_cd;
			return true;
		}
	/**
	 * 部門コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getBumonCd()
		{
			return this.bumon_cd;
		}	
//	   ↑↑2006.07.03 maojm カスタマイズ修正↑↑
	/**
	 * 店仕入先管理コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenSiiresakiKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setTenSiiresakiKanriCd(String ten_siiresaki_kanri_cd)
		{
			this.ten_siiresaki_kanri_cd = ten_siiresaki_kanri_cd;
			return true;
		}
	/**
	 * 店仕入先管理コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenSiiresakiKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getTenSiiresakiKanriCd()
		{
			return this.ten_siiresaki_kanri_cd;
		}

	/**
	 * 店舗配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoItiran();　戻り値　店舗配列<br>
	 * <br>
	 * @return ArrayList 店舗配列
	 */
	public ArrayList getTenpoItiran() {
		return tenpoItiran;
	}

	/**
	 * 店舗配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoItiran("店舗配列");<br>
	 * <br>
	 * @param list 店舗配列
	 */
	public void setTenpoItiran(ArrayList list) {
		tenpoItiran = list;
	}

//    ↓↓2006.07.13 maojm カスタマイズ修正↓↓
	public String getTenpoNmItiran() {
		return tenpoNmItiran;
	}
	public void setTenpoNmItiran(String list) {
		tenpoNmItiran = list;
	}
	
	public int geti() {
		 return inti;
	}
	public void seti(int i) {
		inti = i;
	}
//    ↑↑2006.07.13 maojm カスタマイズ修正↑↑	
	/**
	 * 処理状況に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setProcessingdivision("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setProcessingDivision(String processingdivision)
		{
			this.processingdivision = processingdivision;
			return true;
		}
	/**
	 * 処理状況に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getProcessingdivision();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getProcessingDivision()
		{
			return this.processingdivision;
		}


}
