/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/12)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
//↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mstA60101_HonbuTonyuLDM extends DataModule
{
	private ArrayList tenpoItiran = null;//店舗配列
	/**
	 * コンストラクタ
	 */
	public mstA60101_HonbuTonyuLDM()
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
		mstA60101_HonbuTonyuLBean bean = new mstA60101_HonbuTonyuLBean();
		bean.setBumonCd( rest.getString("BUMON_CD") );
		bean.setTenpoCd( rest.getString("TENPO_CD") );
		bean.setUnitCd(rest.getString("UNIT_CD") );
		bean.setUnitNm(rest.getString("UNIT_NM") );
		bean.setFujiSyohinKb( rest.getString("FUJI_SYOHIN_KB") );
		bean.setSyohinKbNm( rest.getString("SYOHIN_KB_NM") );
		bean.setSiireHinbanCd( rest.getString("SIIRE_HINBAN_CD") );
		bean.setSyohinCd( rest.getString("SYOHIN_CD") );
		bean.setSiiresakiCd( rest.getString("SIIRESAKI_CD") );
		bean.setSiiresakiNm( rest.getString("SIIRESAKI_NM") );
		bean.setGentankaVl( rest.getDouble("GENTANKA_VL") );
		bean.setSuryoQt( rest.getLong("SURYO_QT") );
		bean.setBaitankaVl( rest.getLong("BAITANKA_VL") );
		bean.setTenHachuStDt(rest.getString("TEN_HACHU_ST_DT") );
		bean.setTenHachuEdDt( rest.getString("TEN_HACHU_ED_DT") );
		bean.setHanbaiStDt(rest.getString("HANBAI_ST_DT") );
		bean.setHanbaiEdDt(rest.getString("HANBAI_ED_DT") );
		bean.setHachutaniIrisuQt( rest.getDouble("HACHUTANI_IRISU_QT") );
		bean.setCreateDatabase();

		//===BEGIN=== Add By kou 2006/11/13
		bean.setSyohinNm( rest.getString("SYOHIN_NA") );
		bean.setNohinDt( rest.getString("NOHIN_DT") );
		//===END=== Add by kou 2006/11/13

//		↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
		bean.setColorNa(rest.getString("color_na"));
		bean.setSizeNa(rest.getString("size_na"));
//		↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑

//		↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
		bean.setInsertTs( rest.getString("INSERT_TS") );
//		↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑

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
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append("     min(S.UNIT_CD) as UNIT_CD, ");
		sb.append("     min(S.FUJI_SYOHIN_KB) as FUJI_SYOHIN_KB, ");
		sb.append("     min(S.GENTANKA_VL) as GENTANKA_VL, ");
		sb.append("     min(S.BAITANKA_VL) as BAITANKA_VL, ");
		sb.append("     min(S.TEN_HACHU_ST_DT) as TEN_HACHU_ST_DT, ");
		sb.append("     max(S.TEN_HACHU_ED_DT) as TEN_HACHU_ED_DT, ");
		sb.append("     min(S.HANBAI_ST_DT) as HANBAI_ST_DT, ");
		sb.append("     max(S.HANBAI_ED_DT) as HANBAI_ED_DT, ");
		sb.append("     min(S.HACHUTANI_IRISU_QT) as HACHUTANI_IRISU_QT, ");
		sb.append("     min(S.SYSTEM_KB) as SYSTEM_KB, ");
		sb.append("     min(S.BUMON_CD) as BUMON_CD, ");
		if (map.get("hyouji")!= null && "0".equals(map.get("hyouji")) ){
			sb.append("     min(S.SYOHIN_CD) as SYOHIN_CD, ");
//			↓↓2006.11.29 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("     S.SIIRE_HINBAN_CD as SIIRE_HINBAN_CD, ");
			sb.append("     coalesce(S.SIIRE_HINBAN_CD,'') as SIIRE_HINBAN_CD, ");
//			↑↑2006.11.29 H.Yamamoto カスタマイズ修正↑↑
			sb.append("     S.SIIRESAKI_CD as SIIRESAKI_CD, ");
		}
		if (map.get("hyouji")!= null && "1".equals(map.get("hyouji")) ){
			sb.append("     S.SYOHIN_CD as SYOHIN_CD, ");
//			↓↓2006.11.29 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("     min(S.SIIRE_HINBAN_CD) as SIIRE_HINBAN_CD, ");
			sb.append("     coalesce(min(S.SIIRE_HINBAN_CD),'') as SIIRE_HINBAN_CD, ");
//			↑↑2006.11.29 H.Yamamoto カスタマイズ修正↑↑
			sb.append("     min(S.SIIRESAKI_CD) as SIIRESAKI_CD, ");
		}
		//===BEGIN=== Add by kou 2006/11/13
		sb.append(" 	MIN(S.HINMEI_KANJI_NA) AS SYOHIN_NA,");
		//===END=== Add by kou 2006/11/13
//		↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("     Tmp_syokai.TENPO_CD as TENPO_CD, ");
//		sb.append("     sum(Tmp_syokai.SURYO_QT) as SURYO_QT, ");
//		//===BEGIN=== Add by kou 2006/11/13
//		sb.append(" 	MIN(TMP_SYOKAI.NOHIN_DT) AS NOHIN_DT,");
//		sb.append(" 	MIN(TMP_SYOKAI.INSERT_TS) AS INSERT_TS,");
//		//===END=== Add by kou 2006/11/13
		sb.append("     C.TENPO_CD as TENPO_CD, ");
		sb.append("     sum(C.SURYO_QT) as SURYO_QT, ");
		sb.append(" 	C.NOHIN_DT AS NOHIN_DT,");
		if (map.get("sort")!= null && "2".equals(map.get("sort")) ){
			sb.append(" 	left(C.INSERT_TS,6) AS INSERT_TS,");
		} else {
			sb.append(" 	'' AS INSERT_TS,");
		}
//		↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append("     max(E.KANJI_RN) as UNIT_NM, ");
		sb.append("     max(G.KANJI_RN) as SYOHIN_KB_NM, ");
		sb.append("     max(F.KANJI_RN) as SIIRESAKI_NM ");
//		↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
		if (map.get("hyouji")!= null && "1".equals(map.get("hyouji")) ){
			// 単品単位
			if (map.get("sysKb") != null && map.get("sysKb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
				//タグ衣料の場合は、名称・CTFマスタよりカラー名称、サイズ名称を取得
				sb.append("	    , ");
				sb.append("	    max(H.KANJI_RN) AS color_na, ");	//カラー名称(漢字略称)
				sb.append("	    max(I.KANJI_RN) AS size_na ");		//サイズ名称(漢字略称)
			}else if ( map.get("sysKb") != null && map.get("sysKb").equals("J") ){
				//実用衣料の場合は、商品マスタよりカラー名称、サイズ名称を取得
				sb.append("	    , ");
				sb.append("	    max(S.COLOR_NA) AS color_na, ");	//カラー名称(漢字規格)
				sb.append("	    max(S.SIZE_NA) AS size_na ");	//サイズ名称(漢字規格２)
			}else {
				//衣料以外
				sb.append("	    , ");
				sb.append("	    '' AS color_na, ");	//カラー名称(漢字規格)
				sb.append("	    '' AS size_na ");	//サイズ名称(漢字規格２)
			}
		}else {
			//アイテム単位
			sb.append("	    , ");
			sb.append("	    '' AS color_na, ");	//カラー名称(漢字規格)
			sb.append("	    '' AS size_na ");	//サイズ名称(漢字規格２)
		}
//		↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" FROM ");
		sb.append("     (SELECT ");
		sb.append("          B.UNIT_CD as UNIT_CD , ");
		sb.append("          B.FUJI_SYOHIN_KB  as FUJI_SYOHIN_KB , ");
		sb.append("          B.GENTANKA_VL as GENTANKA_VL , ");
		sb.append("          B.BAITANKA_VL as BAITANKA_VL , ");
		sb.append("          B.TEN_HACHU_ST_DT as TEN_HACHU_ST_DT , ");
		sb.append("          B.TEN_HACHU_ED_DT as TEN_HACHU_ED_DT , ");
		sb.append("          B.HANBAI_ST_DT as HANBAI_ST_DT , ");
		sb.append("          B.HANBAI_ED_DT as HANBAI_ED_DT , ");
//		↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("          B.HACHUTANI_IRISU_QT as HACHUTANI_IRISU_QT, ");
		if (map.get("sysKb") != null && map.get("sysKb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			//タグ衣料の場合は、1固定
			sb.append("          1 as HACHUTANI_IRISU_QT, ");
		} else {
			sb.append("          B.HACHUTANI_IRISU_QT as HACHUTANI_IRISU_QT, ");
		}
//		↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑
		sb.append("          B.SYSTEM_KB as SYSTEM_KB, ");
		sb.append("          B.BUMON_CD as BUMON_CD, ");
		sb.append("          B.SYOHIN_CD as SYOHIN_CD, ");
		//===BEGIN=== Add by kou 2006/11/13
		sb.append(" 		 B.HINMEI_KANJI_NA AS HINMEI_KANJI_NA,");
		//===END=== Add by kou 2006/11/13
		sb.append("          B.SIIRE_HINBAN_CD as SIIRE_HINBAN_CD, ");
		sb.append("          B.SIIRESAKI_CD as SIIRESAKI_CD ");
//		↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	         , ");
		sb.append("          B.COLOR_CD AS color_cd, ");	//カラー
		sb.append("	         B.SIZE_CD AS size_cd, ");	//サイズ
		sb.append("          B.KIKAKU_KANJI_NA AS color_na, ");	//カラー名称(漢字規格)
		sb.append("	         B.KIKAKU_KANJI_2_NA AS size_na ");	//サイズ名称(漢字規格２)
//		↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑
		sb.append("      FROM ");

		sb.append("          R_SYOHIN_TAIKEI A , ");
		sb.append("          R_SYOHIN B  ");
//		↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//		if( "G".equals(map.get("sysKb"))){
		if(("G".equals(map.get("sysKb")) || "F".equals(map.get("sysKb")))
					&& ((map.get("tanadaiNb_from")!=null && map.get("tanadaiNb_from").toString().trim().length()!=0)
					||  (map.get("tanadaiNb_to")!=null && map.get("tanadaiNb_to").toString().trim().length()!=0))){
//		↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
		    sb.append("    , R_TANA_LOCATION H ");
		}
		sb.append("     WHERE ");
		sb.append("          A.BUMON_CD = B.BUMON_CD ");
		sb.append("       AND ");
		sb.append("          A.UNIT_CD = B.UNIT_CD ");
		sb.append("       AND ");
		sb.append("          A.SYSTEM_KB = '"+ map.get("sysKb") +"' ");

		//bumon_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
		    sb.append("  AND ");
		    sb.append("      A.bumon_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
		}

		//hinban_cd に対するWHERE区
		if( map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0 )
		{
		    sb.append("  AND ");
		    sb.append("     A.hinban_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd") , 4, "0", true)) + "'");
		}

		//hinsyu_cd に対するWHERE区
		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
		    sb.append("  AND ");
		    sb.append("     A.hinsyu_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinsyu_cd") , 4, "0", true)) + "'");
		}

		//line_cd に対するWHERE区
		if( map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0 )
		{
		    sb.append("  AND ");
		    sb.append("     A.line_cd = '" + conv.convertWhereString( (String)map.get("line_cd") ) + "'");
		}

		//unit_cd に対するWHERE区
		if( map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0 )
		{
		    sb.append("  AND ");
		    sb.append("     A.unit_cd = '" + conv.convertWhereString( (String)map.get("unit_cd") ) + "'");
		}

		//tanadaiNb_from に対するWHERE区
		//tanadaiNb_to に対するWHERE区
		if("J".equals(map.get("sysKb"))){
		    if(map.get("tanadaiNb_from")!=null && map.get("tanadaiNb_from").toString().trim().length()!=0){
		        sb.append(" AND ");
		        sb.append("    B.TANA_NO_NB >= "+ map.get("tanadaiNb_from"));
		    }
		    if(map.get("tanadaiNb_to")!=null && map.get("tanadaiNb_to").toString().trim().length()!=0){
		        sb.append(" AND ");
		        sb.append("    B.TANA_NO_NB <= "+ map.get("tanadaiNb_to"));
		    }
//		↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//		}else if("G".equals(map.get("sysKb"))){
		}else if(("G".equals(map.get("sysKb")) || "F".equals(map.get("sysKb")))
					&& ((map.get("tanadaiNb_from")!=null && map.get("tanadaiNb_from").toString().trim().length()!=0)
					||  (map.get("tanadaiNb_to")!=null && map.get("tanadaiNb_to").toString().trim().length()!=0))){
//			sb.append("  AND ");
//			sb.append("     H.bumon_cd = B.bumon_cd ");
//			sb.append("  AND ");
//			sb.append("     H.KEIKAKU_SYOHIN_CD = B.syohin_cd ");
			sb.append("  AND ");
			sb.append("     H.SYOHIN_CD = B.syohin_cd ");
//		↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
		    if(map.get("tanadaiNb_from")!=null && map.get("tanadaiNb_from").toString().trim().length()!=0){
		        sb.append(" AND ");
		        sb.append("    H.tanadai_nb >= '"+ map.get("tanadaiNb_from") +"'");
		    }
		    if(map.get("tanadaiNb_to")!=null && map.get("tanadaiNb_to").toString().trim().length()!=0){
		        sb.append(" AND ");
		        sb.append("    H.tanadai_nb <= '"+ map.get("tanadaiNb_to") +"' ");
		    }
		}
		sb.append("        AND ");
//		↓↓2006.10.31 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("           B.YUKO_DT = MSP710101_GETSYOHINYUKODT(B.BUMON_CD, B.SYOHIN_CD,cast(null as char))");
		sb.append("           B.YUKO_DT = (");
		sb.append(" select ");
		sb.append(" max(b2.yuko_dt) ");
		sb.append(" from ");
		sb.append(" r_syohin  b2 ");
		sb.append(" where ");
		sb.append(" B.BUMON_CD = b2.bumon_cd ");
		sb.append(" and ");
		sb.append(" B.SYOHIN_CD = b2.syohin_cd ");
		sb.append(" ) ");
		sb.append(" AND ");
		sb.append(" B.DELETE_FG = '0' ");
//		↑↑2006.10.31 H.Yamamoto カスタマイズ修正↑↑
		sb.append("   ) S ");

		sb.append("     left outer join R_NAMECTF  E ");
		sb.append("     on E.CODE_CD = S.SYSTEM_KB|| S.UNIT_CD AND E.SYUBETU_NO_CD='"+ MessageUtil.getMessage("0060", userLocal) +"' AND E.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append("     left outer join R_NAMECTF G ");
		sb.append("     on G.CODE_CD = S.SYSTEM_KB|| S.FUJI_SYOHIN_KB AND G.SYUBETU_NO_CD='"+ MessageUtil.getMessage("1060", userLocal) +"' AND G.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
//		↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("     left outer join R_SIIRESAKI  F ");
//		sb.append("     on S.SIIRESAKI_CD = F.SIIRESAKI_CD AND F.KANRI_CD ='0000' AND F.KANRI_KB = '1' AND F.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append(" ");
		sb.append("left outer join ");
		sb.append("R_SIIRESAKI F ");
		sb.append(" on ");
		sb.append("F.KANRI_KB = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
		sb.append(" AND ");
		sb.append("F.KANRI_CD ='0000' ");
		sb.append(" AND ");
		sb.append("F.SIIRESAKI_CD = ");
		sb.append("( ");
		sb.append("SELECT ");
		sb.append("max(F2.SIIRESAKI_CD) ");
		sb.append("FROM ");
		sb.append("R_SIIRESAKI F2 ");
		sb.append("WHERE ");
		sb.append("F2.KANRI_KB = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
		sb.append(" AND ");
		sb.append("F2.KANRI_CD ='0000' ");
		sb.append(" AND ");
		sb.append("(F2.SIIRESAKI_CD = S.SIIRESAKI_CD ");
		sb.append(" OR ");
		sb.append("SUBSTR(F2.SIIRESAKI_CD,1,6) = S.SIIRESAKI_CD) ");
		sb.append(" AND ");
		sb.append("F2.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append(") ");
//		↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
		if (map.get("hyouji")!= null && "1".equals(map.get("hyouji")) ){
			// 単品単位
			if (map.get("sysKb") != null && map.get("sysKb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
				// タグ
				sb.append(" LEFT JOIN R_NAMECTF H ON (H.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)+"' AND S.COLOR_CD = H.CODE_CD) ");	//カラー名称取得用LEFT JOIN
				sb.append(" LEFT JOIN R_NAMECTF I ON (I.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)+"' AND S.SIZE_CD  = I.CODE_CD) ");	//サイズ名称取得用LEFT JOIN
			}
		}
//		↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("      , ");
//
//		sb.append("     (SELECT ");
//		sb.append("          C.BUMON_CD as BUMON_CD , ");
//		sb.append("          C.SYOHIN_CD  as SYOHIN_CD , ");
//		sb.append("          C.TENPO_CD as TENPO_CD , ");
//		//===BEGIN=== Add by kou 2006/11/13
//		sb.append(" 		 MIN(C.INSERT_TS) AS INSERT_TS,");
//		sb.append(" 		 MIN(C.NOHIN_DT) AS NOHIN_DT,");
//		//===END=== Add by kou 2006/11/13
//		sb.append("          SUM(C.SURYO_QT) as SURYO_QT ");
//		sb.append("      FROM ");
//		sb.append("          R_SYOKAIDONYU C ");
//		sb.append("     WHERE ");
//		sb.append("          C.DELETE_FG = '0' ");
		sb.append(" ");
		sb.append("inner join ");
		sb.append("R_SYOKAIDONYU C ");
		sb.append(" on ");
		sb.append("C.BUMON_CD = S.BUMON_CD ");
		sb.append(" AND ");
		sb.append("C.SYOHIN_CD = S.SYOHIN_CD ");
		sb.append(" AND ");
		sb.append("C.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//		↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑

		//bumon_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
		    sb.append("   AND ");
		    sb.append("      C.BUMON_CD = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
		}

		//hachuEnDt に対するWHERE区
		if(map.get("hachuStDt")!=null && map.get("hachuStDt").toString().trim().length()!=0 &&
				map.get("hachuEnDt")!=null && map.get("hachuEnDt").toString().trim().length()!=0){
			sb.append("   AND ");
			sb.append("      C.HATYU_DT <= '"+ map.get("hachuEnDt") +"'");
			sb.append("   AND ");
			sb.append("      C.HATYU_DT >= '"+ map.get("hachuStDt") +"'");
		}
		if(map.get("hachuStDt")!=null && map.get("hachuStDt").toString().trim().length()!=0 &&
				(map.get("hachuEnDt")==null || map.get("hachuEnDt").toString().trim().length()==0)){
			sb.append("   AND ");
			sb.append("      C.HATYU_DT >= '"+ map.get("hachuStDt") +"'");
		}

		//nohinEnDt に対するWHERE区
		if(map.get("nohinStDt")!=null && map.get("nohinStDt").toString().trim().length()!=0 &&
				map.get("nohinEnDt")!=null && map.get("nohinEnDt").toString().trim().length()!=0){
			sb.append("   AND ");
			sb.append("      C.NOHIN_DT <= '"+ map.get("nohinEnDt") +"'");
			sb.append("   AND ");
			sb.append("      C.NOHIN_DT >= '"+ map.get("nohinStDt") +"'");
		}
		if(map.get("nohinStDt")!=null && map.get("nohinStDt").toString().trim().length()!=0 &&
				(map.get("nohinEnDt")==null || map.get("nohinEnDt").toString().trim().length()==0)){
			sb.append("   AND ");
			sb.append("      C.NOHIN_DT >= '"+ map.get("nohinStDt") +"'");
		}

		//===BEGIN=== Add by kou 2006/11/13
		if( map.get("tenpoCd") != null && ((String)map.get("tenpoCd")).trim().length() > 0 )
		{
			sb.append(" AND C.TENPO_CD = '")
				.append(conv.convertWhereString(StringUtility.charFormat((String)map.get("tenpoCd") , 6, "0", true))).append("'");
		} else {
			if( map.get("tenGroupNo") != null && ((String)map.get("tenGroupNo")).trim().length() > 0 )
			{
//				↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
//				sb.append(" AND C.TENPO_CD IN (");
//				sb.append(" 		SELECT TENPO_CD ");
//				sb.append(" 		  FROM R_TENGROUP ");
//				sb.append(" 		 WHERE GROUPNO_CD = '").append(map.get("tenGroupNo")).append("'");
//				sb.append(" 		   AND BUMON_CD = '")
//					.append(conv.convertWhereString(StringUtility.charFormat((String)map.get("bumon_cd") , 4, "0", true))).append("'");
//				sb.append(" ) ");
				sb.append(" ");
				sb.append("inner join ");
				sb.append("R_TENGROUP D ");
				sb.append(" on ");
				sb.append("D.BUMON_CD = C.BUMON_CD ");
				sb.append(" and ");
				sb.append("D.TENPO_CD = C.TENPO_CD ");
				sb.append(" and ");
				sb.append("D.YOTO_KB = '").append(mst003901_YotoKbDictionary.HACHU.getCode()).append("' ");
				sb.append(" and ");
				sb.append("D.GROUPNO_CD = '").append(map.get("tenGroupNo")).append("' ");
				sb.append(" and ");
				sb.append("D.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//				↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
			}
		}
		//===END=== Add by kou 2006/11/13

//		↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("     GROUP BY ");
//		sb.append("          C.BUMON_CD , ");
//		sb.append("          C.SYOHIN_CD ,");
//		sb.append("          C.TENPO_CD  ");
//		sb.append("   ) Tmp_syokai ");
//
//		sb.append(" WHERE ");
//		sb.append("      S.BUMON_CD=Tmp_syokai.BUMON_CD ");
//		sb.append("   AND ");
//		sb.append("      S.SYOHIN_CD=Tmp_syokai.SYOHIN_CD ");
//		↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑

		sb.append(" GROUP BY ");
		if (map.get("hyouji")!= null && "0".equals(map.get("hyouji")) ){
			sb.append("    S.SIIRE_HINBAN_CD , ");
			sb.append("    S.SIIRESAKI_CD ,");
//			↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    Tmp_syokai.TENPO_CD  ");
//			↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
		}
		if (map.get("hyouji")!= null && "1".equals(map.get("hyouji")) ){
			sb.append("    S.SYOHIN_CD , ");
//			↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    Tmp_syokai.TENPO_CD  ");
//			↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
		}
//		↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append("    C.NOHIN_DT ");
		sb.append("	   , ");
		sb.append("    C.TENPO_CD ");
		if (map.get("sort")!= null && "2".equals(map.get("sort")) ){
			sb.append("	   , ");
			sb.append("    left(C.INSERT_TS,6) ");
		}
//		↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑

		sb.append(" ORDER BY ");
		//===BEGIN=== Modify by kou 2006/11/13
		//if (map.get("hyouji")!= null && "0".equals(map.get("hyouji")) ){
		//	sb.append("    S.SIIRE_HINBAN_CD , ");
		//	sb.append("    S.SIIRESAKI_CD ,");
		//	sb.append("    min(S.SYOHIN_CD)  ");
		//}
		//if (map.get("hyouji")!= null && "1".equals(map.get("hyouji")) ){
		//	sb.append("    S.SYOHIN_CD ");
		//}

//			if (map.get("sort")!= null && "0".equals(map.get("sort")) ){
//				sb.append("    SIIRE_HINBAN_CD , ");
//				sb.append("    SIIRESAKI_CD ");
//			}
//			if (map.get("sort")!= null && "1".equals(map.get("sort")) ){
//				sb.append("	   NOHIN_DT DESC  ");
//			}
//			if (map.get("sort")!= null && "2".equals(map.get("sort")) ){
//				sb.append("    INSERT_TS DESC ");
//			}
		if (map.get("hyouji")!= null && "0".equals(map.get("hyouji")) ){
			if (map.get("sort")!= null && "0".equals(map.get("sort")) ){
				sb.append("    SIIRE_HINBAN_CD , ");
				sb.append("    SIIRESAKI_CD ");
//				↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
				sb.append("	   , ");
				sb.append("	   NOHIN_DT DESC ");
//				↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
			}
			if (map.get("sort")!= null && "1".equals(map.get("sort")) ){
				sb.append("	   NOHIN_DT DESC , ");
				sb.append("    SIIRE_HINBAN_CD , ");
				sb.append("    SIIRESAKI_CD  ");
			}
			if (map.get("sort")!= null && "2".equals(map.get("sort")) ){
				sb.append("    INSERT_TS DESC , ");
				sb.append("    SIIRE_HINBAN_CD , ");
				sb.append("    SIIRESAKI_CD ");
//				↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
				sb.append("	   , ");
				sb.append("	   NOHIN_DT DESC ");
//				↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
			}
		}
		if (map.get("hyouji")!= null && "1".equals(map.get("hyouji")) ){
			if (map.get("sort")!= null && "0".equals(map.get("sort")) ){
				sb.append("    SIIRE_HINBAN_CD , ");
				sb.append("    SYOHIN_CD  ");
//				↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
				sb.append("	   , ");
				sb.append("	   NOHIN_DT DESC ");
//				↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
			}
			if (map.get("sort")!= null && "1".equals(map.get("sort")) ){
				sb.append("		NOHIN_DT DESC  , ");
				sb.append("    SYOHIN_CD ");
			}
			if (map.get("sort")!= null && "2".equals(map.get("sort")) ){
				sb.append("    INSERT_TS DESC , ");
				sb.append("    SYOHIN_CD ");
//				↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
				sb.append("	   , ");
				sb.append("	   NOHIN_DT DESC ");
//				↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
			}
		}
		//===END=== Modify by kou 2006/11/13
//		↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}


//	/**
//	 * 検索用ＳＱＬの生成を行う。
//	 * 渡されたMapを元にWHERE区を作成する。
//	 * @param map Map
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getSelectSql( Map map )
//	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//		StringBuffer sb = new StringBuffer();
//		sb.append(" SELECT ");
//		sb.append("   rs.*, ");
//		sb.append("   E.KANJI_RN as UNIT_NM , ");
//		sb.append("   G.KANJI_RN  as SYOHIN_KB_NM, ");
//		sb.append("   F.KANJI_RN  as SIIRESAKI_NM ");
//
//		sb.append(" FROM ");
//		sb.append("  (SELECT ");
//
//		sb.append("		min(D.UNIT_CD) as UNIT_CD	, ");
//		sb.append("		min( D.FUJI_SYOHIN_KB ) as FUJI_SYOHIN_KB , ");
//		sb.append("		min(D.SYOHIN_CD ) as SYOHIN_CD , ");
//		sb.append("		min(D.GENTANKA_VL ) as GENTANKA_VL , ");
//		sb.append("		min(D.BAITANKA_VL ) as BAITANKA_VL , ");
//		sb.append("		min(D.TEN_HACHU_ST_DT 	) as TEN_HACHU_ST_DT 	, ");
//		sb.append("		max(D.TEN_HACHU_ED_DT 	) as TEN_HACHU_ED_DT 	, ");
//		sb.append("		min(D.HANBAI_ST_DT 	) as HANBAI_ST_DT 	, ");
//		sb.append("		max(D.HANBAI_ED_DT 	) as HANBAI_ED_DT 	, ");
//		sb.append("		min(D.HACHUTANI_IRISU_QT	) as HACHUTANI_IRISU_QT	, ");
//		sb.append("		sum(C.SURYO_QT) as SURYO_QT,");
//		sb.append("		min(D.SYSTEM_KB) as SYSTEM_KB, ");
//		sb.append("		min(D.BUMON_CD ) as BUMON_CD ,");
//
//		if (map.get("hyouji")!= null && "0".equals(map.get("hyouji")) ){
//			sb.append("	D.SIIRESAKI_CD , ");
//			sb.append("	D.SIIRE_HINBAN_CD    ");
//		}
//		if (map.get("hyouji")!= null && "1".equals(map.get("hyouji")) ){
//			sb.append(" min(D.SIIRESAKI_CD ) as SIIRESAKI_CD , ");
//			sb.append(" min(D.SIIRE_HINBAN_CD) as SIIRE_HINBAN_CD  ");
//
//		}
//
//		sb.append("   FROM ");
//
//		sb.append("	R_SYOKAIDONYU C ,");
//		sb.append("	R_SYOHIN D  ,");
//		sb.append("	(select ");
//		sb.append(" 	B.BUMON_CD , ");
//		sb.append("		B.SYOHIN_CD  ");
//		sb.append("from ");
//		sb.append("	R_SYOHIN_TAIKEI  A 	, ");
//		sb.append("	R_SYOHIN B  ");
//		if( "G".equals(map.get("sysKb"))){
//			sb.append("  , R_TANA_LOCATION H ");
//		}
//		sb.append(" where ");
//		sb.append("	A.BUMON_CD = B.BUMON_CD ");
//		sb.append("	and ");
//		sb.append("	A.UNIT_CD = B.UNIT_CD ");
//		sb.append("	and ");
//		sb.append("     A.system_kb = '"+ map.get("sysKb") +"' ");
//
//		// bumon_cd に対するWHERE区
//		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
//		{
//			sb.append(" AND ");
//			sb.append("A.bumon_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
//		}
//
//		//hinban_cd に対するWHERE区
//		if( map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0 )
//		{
//			sb.append(" AND ");
//			sb.append("A.hinban_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd") , 4, "0", true)) + "'");
//		}
//
//		//hinsyu_cd に対するWHERE区
//		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
//		{
//			sb.append(" AND ");
//			sb.append("A.hinsyu_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinsyu_cd") , 4, "0", true)) + "'");
//		}
//
//		//line_cd に対するWHERE区
//		if( map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0 )
//		{
//			sb.append(" AND ");
//			sb.append("A.line_cd = '" + conv.convertWhereString( (String)map.get("line_cd") ) + "'");
//		}
//
//		//unit_cd に対するWHERE区
//		if( map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0 )
//		{
//			sb.append(" AND ");
//			sb.append("A.unit_cd = '" + conv.convertWhereString( (String)map.get("unit_cd") ) + "'");
//		}
//
//		//tanadaiNb_from に対するWHERE区
//		//tanadaiNb_to に対するWHERE区
//		if("J".equals(map.get("sysKb"))){
//			if(map.get("tanadaiNb_from")!=null && map.get("tanadaiNb_from").toString().trim().length()!=0){
//				sb.append(" AND ");
//				sb.append("     B.TANA_NO_NB >= "+ map.get("tanadaiNb_from"));
//			}
//			if(map.get("tanadaiNb_to")!=null && map.get("tanadaiNb_to").toString().trim().length()!=0){
//				sb.append(" AND ");
//				sb.append("     B.TANA_NO_NB <= "+ map.get("tanadaiNb_to"));
//			}
//		}else if("G".equals(map.get("sysKb"))){
//			sb.append(" AND ");
//			sb.append("    H.bumon_cd = B.bumon_cd ");
//			sb.append(" AND ");
//			sb.append("    H.KEIKAKU_SYOHIN_CD = B.syohin_cd ");
//			if(map.get("tanadaiNb_from")!=null && map.get("tanadaiNb_from").toString().trim().length()!=0){
//				sb.append(" AND ");
//				sb.append("     H.tanadai_nb >= '"+ map.get("tanadaiNb_from") +"'");
//			}
//			if(map.get("tanadaiNb_to")!=null && map.get("tanadaiNb_to").toString().trim().length()!=0){
//				sb.append(" AND ");
//				sb.append("     H.tanadai_nb <= '"+ map.get("tanadaiNb_to") +"' ");
//			}
//		}
//
//		sb.append(" AND ");
//		sb.append("   B.YUKO_DT = MSP710101_GETSYOHINYUKODT(B.BUMON_CD, B.SYOHIN_CD,cast(null as char)) ");
//
//		sb.append(" group by ");
//		sb.append(" B.BUMON_CD ,  ");
//		sb.append(" B.SYOHIN_CD    ) rs1 ");
//
//      	sb.append(" WHERE ");
//		sb.append(" D.BUMON_CD = rs1.BUMON_CD 	and  ");
//		sb.append(" D.SYOHIN_CD = rs1.SYOHIN_CD  and ");
//		sb.append("   D.YUKO_DT = MSP710101_GETSYOHINYUKODT(D.BUMON_CD, D.SYOHIN_CD,cast(null as char))  and");
//		sb.append(" C.BUMON_CD = D.BUMON_CD 	and ");
//		sb.append(" C.SYOHIN_CD = D.SYOHIN_CD 	and  ");
//		sb.append(" C.DELETE_FG = '0'   ");
//		//hachuStDt に対するWHERE区
//		//hachuEnDt に対するWHERE区
//		if(map.get("hachuStDt")!=null && map.get("hachuStDt").toString().trim().length()!=0 &&
//				map.get("hachuEnDt")!=null && map.get("hachuEnDt").toString().trim().length()!=0){
//			sb.append(" AND ");
//			sb.append("     C.HATYU_DT <= '"+ map.get("hachuEnDt") +"'");
//			sb.append(" AND ");
//			sb.append("     C.HATYU_DT >= '"+ map.get("hachuStDt") +"'");
//		}
//		if(map.get("hachuStDt")!=null && map.get("hachuStDt").toString().trim().length()!=0 &&
//				(map.get("hachuEnDt")==null || map.get("hachuEnDt").toString().trim().length()==0)){
//			sb.append(" AND ");
//			sb.append("     C.HATYU_DT >= '"+ map.get("hachuStDt") +"'");
//		}
//		//nohinStDt に対するWHERE区
//		//nohinEnDt に対するWHERE区
//		if(map.get("nohinStDt")!=null && map.get("nohinStDt").toString().trim().length()!=0 &&
//				map.get("nohinEnDt")!=null && map.get("nohinEnDt").toString().trim().length()!=0){
//			sb.append(" AND ");
//			sb.append("     C.NOHIN_DT <= '"+ map.get("nohinEnDt") +"'");
//			sb.append(" AND ");
//			sb.append("     C.NOHIN_DT >= '"+ map.get("nohinStDt") +"'");
//		}
//		if(map.get("nohinStDt")!=null && map.get("nohinStDt").toString().trim().length()!=0 &&
//				(map.get("nohinEnDt")==null || map.get("nohinEnDt").toString().trim().length()==0)){
//			sb.append(" AND ");
//			sb.append("     C.NOHIN_DT >= '"+ map.get("nohinStDt") +"'");
//		}
//
//        sb.append("   GROUP BY ");
//        if (map.get("hyouji")!= null && "0".equals(map.get("hyouji")) ){
//        	sb.append("   D.SIIRESAKI_CD ,");
//            sb.append("   D.SIIRE_HINBAN_CD  ");
//		}
//		if (map.get("hyouji")!= null && "1".equals(map.get("hyouji")) ){
//			sb.append("     D.SYOHIN_CD");
//		}
//        sb.append("   ) rs ");
//
//        sb.append("   left outer join 	R_NAMECTF  E ");
//        sb.append("   on 	E.CODE_CD =  rs.SYSTEM_KB|| rs.UNIT_CD 	and 	E.SYUBETU_NO_CD='0060' and E.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
//        sb.append("   left outer join 	R_NAMECTF  G  ");
//        sb.append("   on 	G.CODE_CD = rs.SYSTEM_KB|| rs.FUJI_SYOHIN_KB 	and 	G.SYUBETU_NO_CD='1060' and G.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
//        sb.append("   left outer join 	R_SIIRESAKI  F ");
//        sb.append("   on 	rs.SIIRESAKI_CD = F.SIIRESAKI_CD 	and 	F.KANRI_CD ='0000'	and 	F.KANRI_KB = '1' and F.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
//
//		sb.append("   ORDER BY ");
//		//仕入先コード
//		sb.append(" rs.SIIRESAKI_CD");
//		//販売開始日
//		sb.append(" ,rs.HANBAI_ST_DT");
//		//仕入先商品コード
//		sb.append(" ,rs.SIIRE_HINBAN_CD");
//
//		return sb.toString();
//	}
//
//	/**
//	 * 検索用ＳＱＬの生成を行う。
//	 * 渡されたMapを元にWHERE区を作成する。
//	 * @param tenpo_bumon_cd String
//	 * @param tenpo_syohin_cd String
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getSelectSql1( String tenpo_bumon_cd, String tenpo_syohin_cd  )
//	{
//		StringBuffer sb = new StringBuffer();
//		sb.append(" SELECT ");
//		sb.append(" 	TENPO_CD ,");
//		sb.append(" 	sum(SURYO_QT) as SURYO ");
//		sb.append(" FROM ");
//		sb.append(" 	R_SYOKAIDONYU ");
//		sb.append(" WHERE ");
//		sb.append("     BUMON_CD =  '"+ tenpo_bumon_cd +"'");
//		sb.append(" AND ");
//		sb.append("     SYOHIN_CD =  '"+ tenpo_syohin_cd +"'");
//		sb.append(" AND ");
//		sb.append(" 	DELETE_FG = '0' ");
//		sb.append(" GROUP BY ");
//		sb.append(" 	TENPO_CD  ");
//		return sb.toString();
//	}

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
