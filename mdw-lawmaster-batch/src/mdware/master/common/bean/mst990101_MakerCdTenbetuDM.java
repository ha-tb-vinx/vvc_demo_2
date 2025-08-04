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
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
import mdware.master.util.RMSTDATEUtil;
//↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑


/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.MDWARE
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst990101_MakerCdTenbetuDM extends DataModule
{
/* DM 生成時に使用した SQL 文です。
SELECT
     TEN_SIIRESAKI_KANRI_CD
    ,MAX(TEN_SIIRESAKI_KANRI_NA) AS TEN_SIIRESAKI_KANRI_NA
    ,SIIRESAKI_CD
    ,MAX(KANJI_RN) AS SIIRESAKI_NA
    ,TENPO_COUNT_NUM
FROM (
    SELECT
         T3.TEN_SIIRESAKI_KANRI_CD
        ,T4.KANJI_RN AS TEN_SIIRESAKI_KANRI_NA
        ,T3.SIIRESAKI_CD
        ,T5.KANJI_RN
        ,(SELECT COUNT(*) FROM R_TENBETU_SIIRESAKI WHERE KANRI_KB = '1' AND KANRI_CD = '0241' AND TEN_SIIRESAKI_KANRI_CD = T3.TEN_SIIRESAKI_KANRI_CD AND SIIRESAKI_CD = T3.SIIRESAKI_CD) AS TENPO_COUNT_NUM
    FROM
         R_TENBETU_SIIRESAKI T3
        ,R_TENGROUP          T1
        ,R_TENPO             T2
        ,R_NAMECTF           T4
        ,R_SIIRESAKI         T5
    WHERE
         T1.BUMON_CD     = '0241'
     AND T1.YOTO_KB      = '1'
     AND T1.GROUPNO_CD   = '01'
     AND T1.DELETE_FG    = '0'
     AND T3.KANRI_KB     = '1'
     AND T3.KANRI_CD     = T1.BUMON_CD
     AND T3.TEN_SIIRESAKI_KANRI_CD = '0006'
     AND T3.SIIRESAKI_CD = '030242072'
     AND T4.SYUBETU_NO_CD= '0150'
     AND T1.TENPO_CD     = T2.TENPO_CD
     AND T3.TEN_SIIRESAKI_KANRI_CD = T4.CODE_CD
     AND T3.SIIRESAKI_CD = T5.SIIRESAKI_CD
) T1
GROUP BY
     T1.TEN_SIIRESAKI_KANRI_CD
    ,T1.SIIRESAKI_CD
    ,T1.TENPO_COUNT_NUM
*/

	/**
	 * コンストラクタ
	 */
	public mst990101_MakerCdTenbetuDM()
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
		mst990101_MakerCdTenbetuBean bean = new mst990101_MakerCdTenbetuBean();
		bean.setTenSiiresakiKanriCd( rest.getString("ten_siiresaki_kanri_cd") );
		bean.setTenSiiresakiKanriNa( rest.getString("ten_siiresaki_kanri_na") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setSiiresakiNa( rest.getString("siiresaki_na") );
		bean.setTenpoCountNum( rest.getLong("tenpo_count_num") );
//		↓↓2007.01.21 H.Yamamoto カスタマイズ修正↓↓
//		bean.setTenpoCd(rest.getString("tenpo_cd"));
//		↑↑2007.01.21 H.Yamamoto カスタマイズ修正↑↑
		bean.setCreateDatabase();
		return bean;
	}

	/**
	 * **** 一発で画面に表示するデータを全て取得するSQL ****
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

//		sb.append("SELECT                                                         ");
//		sb.append("     TEN_SIIRESAKI_KANRI_CD                                    ");
//		sb.append("    ,MAX(TEN_SIIRESAKI_KANRI_NA) AS TEN_SIIRESAKI_KANRI_NA     ");
//		sb.append("    ,SIIRESAKI_CD                                              ");
//		sb.append("    ,MAX(KANJI_RN) AS SIIRESAKI_NA                             ");
//		sb.append("    ,TENPO_COUNT_NUM                                           ");
//
//	for( int i = 0; i < this.tenpoList.size(); i++ ){
//		sb.append("    ,MAX(TENPO_"+i+") AS TENPO_"+i+"_EXIST                     ");
//	}
//		sb.append("FROM (                                                         ");
//		sb.append("    SELECT                                                     ");
//		sb.append("         T3.TEN_SIIRESAKI_KANRI_CD                             ");
//		sb.append("        ,T4.KANJI_RN AS TEN_SIIRESAKI_KANRI_NA                 ");
//		sb.append("        ,T3.SIIRESAKI_CD                                       ");
//		sb.append("        ,T5.KANJI_RN                                           ");
//		sb.append("        ,(SELECT COUNT(*) FROM R_TENBETU_SIIRESAKI WHERE KANRI_KB = '1' AND KANRI_CD = '"+conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) )+"' AND TEN_SIIRESAKI_KANRI_CD = T3.TEN_SIIRESAKI_KANRI_CD AND SIIRESAKI_CD = T3.SIIRESAKI_CD) AS TENPO_COUNT_NUM     ");
//
//	for( int i = 0; i < this.tenpoList.size(); i++ ){
//		mst990101_RTenGroupBean bean = (mst990101_RTenGroupBean)this.tenpoList.get(i);
//		sb.append("        ,CASE WHEN T3.TENPO_CD = '"+bean.getTenpoCd().trim()+"' THEN 1 ELSE 0 END AS TENPO_"+i+"     ");
//	}
//		sb.append("    FROM                                                      ");
//		sb.append("         R_TENGROUP          T1                               ");
//		sb.append("        ,R_TENPO             T2                               ");
//		sb.append("        ,R_TENBETU_SIIRESAKI T3                               ");
//		sb.append("        ,R_NAMECTF           T4                               ");
//		sb.append("        ,R_SIIRESAKI         T5                               ");
//		sb.append("    WHERE                                                     ");
//
//
////		String whereStr = " where ";
//		sb.append("	        T1.BUMON_CD     = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "'  	");
//		sb.append("     AND T1.YOTO_KB      = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "'                                                    	");
//		sb.append("     AND T1.GROUPNO_CD   = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("groupno_cd"),2,"0",true) ) + "'	");
//		sb.append("     AND T1.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		sb.append("     AND T3.KANRI_KB     = '"+mst000901_KanriKbDictionary.BUMON.getCode()+"'    ");
//		sb.append("     AND T3.KANRI_CD     = T1.BUMON_CD                                          ");
//
//	if( map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0 ){
//		sb.append("     AND T3.TEN_SIIRESAKI_KANRI_CD = '"+conv.convertWhereString( (String)map.get("maker_cd") )+"' ");
//	}
//	if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 ){
//		sb.append("     AND T3.SIIRESAKI_CD LIKE '"+conv.convertWhereString( (String)map.get("siiresaki_cd") )+"%' ");
//	}
//		sb.append("     AND T4.SYUBETU_NO_CD = '0150' ");
//		sb.append("     AND T1.TENPO_CD     = T2.TENPO_CD                        ");
//		sb.append("     AND T3.TEN_SIIRESAKI_KANRI_CD = T4.CODE_CD               ");
//		sb.append("     AND T3.SIIRESAKI_CD = T5.SIIRESAKI_CD                    ");
//		sb.append(") T1                                                          ");
//		sb.append("GROUP BY                                                      ");
//		sb.append("     T1.TEN_SIIRESAKI_KANRI_CD                                ");
//		sb.append("    ,T1.SIIRESAKI_CD                                          ");
//		sb.append("    ,T1.TENPO_COUNT_NUM                                       ");





//		↓↓2007.01.21 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" SELECT                                                              ");
//		sb.append("      TEN_SIIRESAKI_KANRI_CD                                         ");
//		sb.append("     ,TEN_SIIRESAKI_KANRI_NA                                         ");
//		sb.append("     ,SIIRESAKI_CD                                                   ");
//		sb.append("     ,KANJI_RN AS SIIRESAKI_NA                                       ");
//		sb.append("     ,TENPO_COUNT_NUM                                                ");
//		sb.append("     ,TENPO_CD                                                       ");
//		sb.append(" FROM                                                                ");
//		sb.append("     (SELECT                                                         ");
//		sb.append("          T3.TEN_SIIRESAKI_KANRI_CD                                  ");
//		sb.append("         ,T4.KANJI_RN AS TEN_SIIRESAKI_KANRI_NA                      ");
//		sb.append("         ,T3.SIIRESAKI_CD                                            ");
//		sb.append("         ,T5.KANJI_RN                                                ");
//		sb.append("         ,(SELECT                                                    ");
////		↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("              COUNT(*)                                               ");
////		sb.append("         FROM                                                        ");
////		sb.append("              R_TENBETU_SIIRESAKI                                    ");
////		sb.append("         WHERE                                                       ");
////		sb.append("              KANRI_KB     = '"+mst000901_KanriKbDictionary.BUMON.getCode()+"' ");
////		sb.append("          AND KANRI_CD     = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "' ");
////		sb.append("          AND TEN_SIIRESAKI_KANRI_CD = T3.TEN_SIIRESAKI_KANRI_CD     ");
////		sb.append("          AND SIIRESAKI_CD = T3.SIIRESAKI_CD                         ");
//		sb.append("              COUNT(T7.TENPO_CD)                                     ");
//		sb.append("         FROM                                                        ");
//		sb.append("              R_TENBETU_SIIRESAKI T7                                 ");
////		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("         INNER JOIN R_TENGROUP T8                                    ");
////		sb.append("         ON   T8.YOTO_KB    = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
////		sb.append("          AND T8.GROUPNO_CD = '" + conv.convertWhereString( (String)map.get("groupno_cd") ) + "' ");
////		sb.append("          AND T8.TENPO_CD   = T7.TENPO_CD                            ");
////		sb.append("          AND T8.BUMON_CD   = T7.KANRI_CD                            ");
////		sb.append("          AND T8.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("         INNER JOIN R_TENPO T9                                    ");
//		sb.append("         ON   T9.TENPO_CD   = T7.TENPO_CD                            ");
////		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("          AND T9.TENPO_KB  = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//		sb.append("          AND NVL(T9.HEITEN_DT,'99991231') >= '" + RMSTDATEUtil.getMstDateDt() + "' ");
////		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("          AND T9.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("         INNER JOIN R_NAMECTF T10                                    ");
////		sb.append("         ON   T10.SYUBETU_NO_CD = '0150'                             ");
////		sb.append("          AND T10.CODE_CD       = T7.TEN_SIIRESAKI_KANRI_CD          ");
////		sb.append("         INNER JOIN R_SIIRESAKI T11                                  ");
////		sb.append("         ON   T11.SIIRESAKI_CD = T7.SIIRESAKI_CD                     ");
////		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("         WHERE                                                       ");
//		sb.append("              T7.KANRI_KB     = '"+mst000901_KanriKbDictionary.BUMON.getCode()+"' ");
//		sb.append("          AND T7.KANRI_CD     = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "' ");
//		sb.append("          AND T7.TEN_SIIRESAKI_KANRI_CD = T3.TEN_SIIRESAKI_KANRI_CD     ");
//		sb.append("          AND T7.SIIRESAKI_CD = T3.SIIRESAKI_CD                         ");
//		sb.append("          AND T7.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("         ) AS TENPO_COUNT_NUM                                        ");
////		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("         ,T2.GYOTAI_KB                                               ");
////		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("         ,T3.TENPO_CD                                                ");
//		sb.append("     FROM                                                            ");
//		sb.append("         R_TENBETU_SIIRESAKI T3                                      ");
////		↓↓2007.01.11 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("         INNER JOIN R_TENGROUP T12                                   ");
//		sb.append("         ON   T12.YOTO_KB    = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
//		sb.append("          AND T12.GROUPNO_CD = '" + conv.convertWhereString( (String)map.get("groupno_cd") ) + "' ");
//		sb.append("          AND T12.TENPO_CD   = T3.TENPO_CD                           ");
//		sb.append("          AND T12.BUMON_CD   = T3.KANRI_CD                           ");
//		sb.append("          AND T12.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↑↑2007.01.11 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("        ,R_TENPO             T2                                      ");
//		sb.append("        ,R_NAMECTF           T4                                      ");
//		sb.append("        ,R_SIIRESAKI         T5                                      ");
//		sb.append("     WHERE                                                           ");
//		sb.append("          T3.KANRI_KB     = '"+mst000901_KanriKbDictionary.BUMON.getCode()+"' ");
//		sb.append("      AND T3.KANRI_CD     = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "' ");
//
////	↓↓2006.11.27 H.Yamamoto カスタマイズ修正↓↓
////	if( map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0 ){
////		sb.append("      AND T3.TEN_SIIRESAKI_KANRI_CD = '"+conv.convertWhereString( (String)map.get("maker_cd") )+"' ");
////	}
////
////	if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 ){
//////		sb.append("      AND T3.SIIRESAKI_CD LIKE '"+conv.convertWhereString( (String)map.get("siiresaki_cd") )+"%' ");
//////		 MOD by Tanigawa 2006/11/17 障害票№0266対応 START
////		sb.append("      AND LEFT(T3.SIIRESAKI_CD, 6)  = '"+conv.convertWhereString( (String)map.get("siiresaki_cd") )+"' ");
//////		 MOD by Tanigawa 2006/11/17 障害票№0266対応  END
////	}
//	sb.append("      AND T3.TEN_SIIRESAKI_KANRI_CD IN ");
//	sb.append("          (");
//	sb.append("          SELECT ");
////	↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//	sb.append("          DISTINCT ");
////	↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
//	sb.append("              TEN_SIIRESAKI_KANRI_CD ");
//	sb.append("          FROM ");
//	sb.append("              R_TENBETU_SIIRESAKI T6 ");
//	sb.append("          WHERE                                                           ");
//	sb.append("              T6.KANRI_KB     = '"+mst000901_KanriKbDictionary.BUMON.getCode()+"' ");
//	sb.append("          AND T6.KANRI_CD     = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "' ");
//	if( map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0 ){
//		sb.append("          AND T6.TEN_SIIRESAKI_KANRI_CD = '"+conv.convertWhereString( (String)map.get("maker_cd") )+"' ");
//	}
//	if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 ){
//		sb.append("          AND LEFT(T6.SIIRESAKI_CD, 6)  = '"+conv.convertWhereString( (String)map.get("siiresaki_cd") )+"' ");
//	}
//	sb.append("          AND T6.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//	sb.append("          ) ");
////	↑↑2006.11.27 H.Yamamoto カスタマイズ修正↑↑
//
//		sb.append("      AND T3.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
////		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("      AND T2.TENPO_KB  = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//		sb.append("      AND NVL(T2.HEITEN_DT,'99991231') >= '" + RMSTDATEUtil.getMstDateDt() + "' ");
////		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("      AND T2.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		sb.append("      AND T4.SYUBETU_NO_CD = '0150'                                  ");
//		sb.append("      AND T3.TEN_SIIRESAKI_KANRI_CD = T4.CODE_CD                     ");
//		sb.append("      AND T3.TENPO_CD = T2.TENPO_CD                                  ");
//		sb.append("      AND T3.SIIRESAKI_CD = T5.SIIRESAKI_CD                          ");
//		sb.append("     ) T1                                                            ");
//		sb.append(" ORDER BY                                                            ");
//		sb.append("      T1.TEN_SIIRESAKI_KANRI_CD                                      ");
//		sb.append("     ,T1.SIIRESAKI_CD                                                ");
////		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("     ,T1.GYOTAI_KB                                                   ");
////		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
//		sb.append("     ,T1.TENPO_CD                                                    ");
//		sb.append(" FOR READ ONLY ");

		sb.append(" SELECT ");
		sb.append("      T1.TEN_SIIRESAKI_KANRI_CD ");
		sb.append("     ,T6.KANJI_RN AS TEN_SIIRESAKI_KANRI_NA ");
		sb.append("     ,T1.SIIRESAKI_CD ");
		sb.append("     ,T7.KANJI_RN AS SIIRESAKI_NA ");
		sb.append("     ,(SELECT ");
		sb.append("          COUNT(T8.TENPO_CD) ");
		sb.append("     FROM ");
		sb.append("          R_TENBETU_SIIRESAKI T8 ");
		sb.append("     INNER JOIN R_TENPO T9 ");
		sb.append("     ON   T9.TENPO_CD   = T8.TENPO_CD ");
		sb.append("      AND T9.TENPO_KB  = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//		↓↓2007.01.22 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("      AND NVL(T9.HEITEN_DT,'99991231') >= '" + RMSTDATEUtil.getMstDateDt() + "' ");
		sb.append("      AND T9.HEITEN_DT >= '" + RMSTDATEUtil.getMstDateDt() + "' ");
//		↑↑2007.01.22 H.Yamamoto カスタマイズ修正↑↑
		sb.append("      AND T9.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("     WHERE ");
		sb.append("          T8.KANRI_KB     = '"+ mst000901_KanriKbDictionary.BUMON.getCode()+"' ");
		sb.append("      AND T8.KANRI_CD     = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "' ");
		sb.append("      AND T8.TEN_SIIRESAKI_KANRI_CD = T1.TEN_SIIRESAKI_KANRI_CD ");
		sb.append("      AND T8.SIIRESAKI_CD = T1.SIIRESAKI_CD ");
		sb.append("      AND T8.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("     ) AS TENPO_COUNT_NUM ");
		sb.append(" FROM ");
		sb.append("     (SELECT ");
		sb.append("          T2.TEN_SIIRESAKI_KANRI_CD ");
		sb.append("         ,T2.SIIRESAKI_CD ");
		sb.append("     FROM ");
		sb.append("         R_TENBETU_SIIRESAKI T2 ");
		sb.append("         INNER JOIN R_TENPO T3 ");
		sb.append("         ON   T3.TENPO_CD   = T2.TENPO_CD ");
		sb.append("          AND T3.TENPO_KB  = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
//		↓↓2007.01.22 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("          AND NVL(T3.HEITEN_DT,'99991231') >= '" + RMSTDATEUtil.getMstDateDt() + "' ");
		sb.append("          AND T3.HEITEN_DT >= '" + RMSTDATEUtil.getMstDateDt() + "' ");
//		↑↑2007.01.22 H.Yamamoto カスタマイズ修正↑↑
		sb.append("          AND T3.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("         INNER JOIN R_TENGROUP T4 ");
		sb.append("         ON   T4.BUMON_CD   = T2.KANRI_CD ");
		sb.append("          AND T4.YOTO_KB    = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
		sb.append("          AND T4.GROUPNO_CD = '" + conv.convertWhereString( (String)map.get("groupno_cd") ) + "' ");
		sb.append("          AND T4.TENPO_CD   = T2.TENPO_CD ");
		sb.append("          AND T4.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("     WHERE ");
		sb.append("          T2.KANRI_KB     = '"+ mst000901_KanriKbDictionary.BUMON.getCode()+"' ");
		sb.append("      AND T2.KANRI_CD     = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "' ");
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("      AND T2.TEN_SIIRESAKI_KANRI_CD IN ");
//		sb.append("          ( ");
//		sb.append("          SELECT ");
//		sb.append("          DISTINCT ");
//		sb.append("              TEN_SIIRESAKI_KANRI_CD ");
//		sb.append("          FROM ");
//		sb.append("              R_TENBETU_SIIRESAKI T5 ");
//		sb.append("          WHERE ");
//		sb.append("              T5.KANRI_KB     = T2.KANRI_KB ");
//		sb.append("          AND T5.KANRI_CD     = T2.KANRI_CD ");
//		if( map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0 ){
//			sb.append("          AND T5.TEN_SIIRESAKI_KANRI_CD = '"+conv.convertWhereString( (String)map.get("maker_cd") )+"' ");
//		}
//		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 ){
//			sb.append("          AND LEFT(T5.SIIRESAKI_CD, 6)  = '"+conv.convertWhereString( (String)map.get("siiresaki_cd") )+"' ");
//		}
//		sb.append("          AND T5.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		sb.append("          ) ");
		if( map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0 ){
			sb.append("      AND T2.TEN_SIIRESAKI_KANRI_CD = '"+conv.convertWhereString( (String)map.get("maker_cd") )+"' ");
		}
		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 ){
			sb.append("      AND T2.TEN_SIIRESAKI_KANRI_CD IN ");
			sb.append("          ( ");
			sb.append("          SELECT ");
			sb.append("          DISTINCT ");
			sb.append("              TEN_SIIRESAKI_KANRI_CD ");
			sb.append("          FROM ");
			sb.append("              R_TENBETU_SIIRESAKI T5 ");
			sb.append("          WHERE ");
			sb.append("              T5.KANRI_KB     = '"+ mst000901_KanriKbDictionary.BUMON.getCode()+"' ");
			sb.append("          AND T5.KANRI_CD     = '" + conv.convertWhereString( StringUtility.charFormat( (String)map.get("bumon_cd"),4,"0",true) ) + "' ");
			if( map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0 ){
				sb.append("          AND T5.TEN_SIIRESAKI_KANRI_CD = '"+conv.convertWhereString( (String)map.get("maker_cd") )+"' ");
			}
			sb.append("          AND LEFT(T5.SIIRESAKI_CD, 6)  = '"+conv.convertWhereString( (String)map.get("siiresaki_cd") )+"' ");
			sb.append("          AND T5.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			sb.append("          ) ");
		}
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
		sb.append("      AND T2.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("     GROUP BY ");
		sb.append("          T2.TEN_SIIRESAKI_KANRI_CD ");
		sb.append("         ,T2.SIIRESAKI_CD ");
		sb.append("     ) T1 ");
		sb.append("     INNER JOIN R_NAMECTF T6 ");
		sb.append("     ON   T6.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.MAKER_NAME, userLocal) + "' ");
		sb.append("      AND T6.CODE_CD       = T1.TEN_SIIRESAKI_KANRI_CD ");
		sb.append("     INNER JOIN R_SIIRESAKI T7 ");
		sb.append("     ON   T7.KANRI_KB     = '"+ mst000901_KanriKbDictionary.BUMON.getCode()+"' ");
		sb.append("      AND T7.KANRI_CD = '0000' ");
		sb.append("      AND T7.SIIRESAKI_CD = T1.SIIRESAKI_CD ");
		sb.append(" ORDER BY ");
		sb.append("      T1.TEN_SIIRESAKI_KANRI_CD ");
		sb.append("     ,T1.SIIRESAKI_CD ");
		sb.append(" FOR READ ONLY ");
//		↑↑2007.01.21 H.Yamamoto カスタマイズ修正↑↑


/*
 		//↓DM作成直後の where と group by
		sb.append("         T1.BUMON_CD     = '0241'                             ");
		sb.append("     AND T1.YOTO_KB      = '1'                                ");
		sb.append("     AND T1.GROUPNO_CD   = '01'                               ");
		sb.append("     AND T1.DELETE_FG    = '0'                                ");
		sb.append("     AND T3.KANRI_KB     = '1'                                ");
		sb.append("     AND T3.KANRI_CD     = T1.BUMON_CD                        ");
		sb.append("     AND T3.TEN_SIIRESAKI_KANRI_CD = '0006'                   ");
		sb.append("     AND T3.SIIRESAKI_CD = '030242072'                        ");
		sb.append("     AND T4.SYUBETU_NO_CD= '0150'                             ");
		sb.append("     AND T1.TENPO_CD     = T2.TENPO_CD                        ");
		sb.append("     AND T3.TEN_SIIRESAKI_KANRI_CD = T4.CODE_CD               ");
		sb.append("     AND T3.SIIRESAKI_CD = T5.SIIRESAKI_CD                    ");
		sb.append(") T1                                                          ");
		sb.append("GROUP BY                                                      ");
		sb.append("     T1.TEN_SIIRESAKI_KANRI_CD                                ");
		sb.append("    ,T1.SIIRESAKI_CD                                          ");
		sb.append("    ,T1.TENPO_COUNT_NUM                                       ");
 		//↑DM作成直後の where と group by

*/
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
