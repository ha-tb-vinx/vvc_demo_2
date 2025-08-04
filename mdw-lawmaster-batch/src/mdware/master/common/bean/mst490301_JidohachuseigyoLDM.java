/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）自動発注制御マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する自動発注制御マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/31)初版作成
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
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）自動発注制御マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する自動発注制御マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst490301_JidohachuseigyoLDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst490301_JidohachuseigyoLDM()
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
		mst490301_JidohachuseigyoLBean bean = new mst490301_JidohachuseigyoLBean();
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setHankuNa( rest.getString("hanku_na") );
		bean.setHinshuCd( rest.getString("hinshu_cd") );
		bean.setHinshuNa( rest.getString("hinshu_na") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoNa( rest.getString("tenpo_na") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setJidoHachuKb( rest.getString("jido_hachu_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setSentaku( rest.getString("sentaku") );
		bean.setSentakuOrg( rest.getString("sentaku_org") );
		bean.setDisabled( rest.getString("disabled") );
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
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		sb.append("   KANRI_KB, ");
		//販区コード
		sb.append("	  CASE KANRI_KB WHEN '"+ mst000901_KanriKbDictionary.HANKU.getCode() +"' THEN ");
		sb.append("     KANRI_CD ");
		sb.append("   ELSE ");
		sb.append("	    (SELECT HANKU_CD FROM R_SYOHIN_TAIKEI WHERE HINSYU_CD = KANRI_CD) ");
		sb.append("	  END AS HANKU_CD, ");
		//販区名称
		sb.append("	  CASE KANRI_KB WHEN '"+ mst000901_KanriKbDictionary.HANKU.getCode() +"' THEN ");
		sb.append("	    (SELECT KANJI_RN FROM R_NAMECTF ");
		sb.append("	     WHERE TRIM(CODE_CD) = TRIM(KANRI_CD) ");
		sb.append("	     AND SYUBETU_NO_CD = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) +"') ");
		sb.append("	  ELSE ");
		sb.append("	    (SELECT KANJI_RN FROM R_NAMECTF ");
		sb.append("	     WHERE TRIM(CODE_CD) = TRIM((SELECT HANKU_CD FROM R_SYOHIN_TAIKEI WHERE HINSYU_CD = KANRI_CD)) ");
		sb.append("	     AND SYUBETU_NO_CD = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) +"') ");
		sb.append("	  END AS HANKU_NA, ");
		//品種コード
		sb.append("   CASE KANRI_KB WHEN '"+ mst000901_KanriKbDictionary.HINSYU.getCode() +"' THEN KANRI_CD ");
		sb.append("   ELSE NULL END AS HINSHU_CD, ");
		//品種名称
		sb.append("	  CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() +"' THEN ");
		sb.append("     (SELECT KANJI_RN FROM R_NAMECTF ");
		sb.append("      WHERE TRIM(CODE_CD) = TRIM(KANRI_CD) ");
		sb.append("	     AND SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "') ELSE NULL ");
		sb.append("	  END AS HINSHU_NA, ");
		//管理コード
		sb.append("	  KANRI_CD, ");
		sb.append("	  TENPO_CD, ");
		sb.append("	  TENPO_NA, ");
		sb.append("	  YUKO_DT, ");
		sb.append("	  JIDO_HACHU_KB, ");
		sb.append("	  INSERT_TS, ");
		sb.append("   INSERT_USER_ID, ");
		sb.append("	  UPDATE_TS, ");
		sb.append("	  UPDATE_USER_ID, ");
		sb.append("	  DELETE_FG ");
		//処理状況:新規
		if (map.get("mode").equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
			sb.append("	,SENTAKU ");
			sb.append("	,SENTAKU AS SENTAKU_ORG ");
			sb.append("	,DECODE(SENTAKU, '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "', '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "', '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "') AS DISABLED ");

		//処理状況:修正
		} else if (map.get("mode").equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
			sb.append("	,DECODE(DELETE_FG, '" + mst000801_DelFlagDictionary.INAI.getCode() + "', '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "', '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "') AS SENTAKU ");
			sb.append("	,DECODE(DELETE_FG, '" + mst000801_DelFlagDictionary.INAI.getCode() + "', '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "', '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "') AS SENTAKU_ORG ");
//			sb.append("	,CASE WHEN MAXYUKO > YUKO_DT THEN '1' ");
//			sb.append("	 ELSE DECODE(SENTAKU, '1', '0', '1') ");
//			sb.append("	 END AS DISABLED ");
			sb.append("	,DECODE(SENTAKU, '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "', '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "', '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "') AS DISABLED ");

		//処理状況:削除
		} else if (map.get("mode").equals(mst000101_ConstDictionary.PROCESS_DELETE)) {
			sb.append("	,DECODE(DELETE_FG, '" + mst000801_DelFlagDictionary.INAI.getCode() + "', (CASE WHEN '" + map.get("yuko_dt") + "' >= MAXYUKO THEN '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' ELSE '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "' END), '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "') AS SENTAKU ");
			sb.append("	,DECODE(DELETE_FG, '" + mst000801_DelFlagDictionary.INAI.getCode() + "', (CASE WHEN '" + map.get("yuko_dt") + "' >= MAXYUKO THEN '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' ELSE '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "' END), '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "') AS SENTAKU_ORG ");
			sb.append("	,'" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' AS DISABLED ");

		//処理状況:照会
		} else {
			sb.append("	,SENTAKU ");
			sb.append("	,SENTAKU AS SENTAKU_ORG ");
			sb.append("	,'" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' AS DISABLED ");
		}

//		sb.append("	,DECODE(YUKO_DT, '"+ map.get("yuko_dt") +"', '"+ mst000101_ConstDictionary.RECORD_EXISTENCE +"', '"+ mst000101_ConstDictionary.RECORD_NON_EXISTENCE +"') SENTAKU");
//		sb.append("	,DECODE(YUKO_DT, '"+ map.get("yuko_dt") +"', '"+ mst000101_ConstDictionary.RECORD_EXISTENCE +"', '"+ mst000101_ConstDictionary.RECORD_NON_EXISTENCE +"') SENTAKU_ORG");
//		sb.append("	,DECODE(SENTAKU, '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "', '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "', '" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "') AS DISABLED ");


		sb.append("	FROM ");
		sb.append("		(SELECT ");
		sb.append("			 NVL(JIDOU.KANRI_KB,'" + map.get("kanri_kb") + "') AS KANRI_KB ");
		sb.append("			,TMP.KANRI_CD ");
		sb.append("			,TMP.TENPO_CD ");
		sb.append("			,TMP.TENPO_NA ");
		sb.append("			,RJC.YUKO_DT ");
		sb.append("			,RJC.JIDO_HACHU_KB ");
		sb.append("			,RJC.INSERT_TS ");
		sb.append("			,RJC.INSERT_USER_ID ");
		sb.append("			,RJC.UPDATE_TS ");
		sb.append("			,RJC.UPDATE_USER_ID ");
		sb.append("			,RJC.DELETE_FG ");
		sb.append("			,DECODE(JIDOU.KANRI_KB,NULL,'" + mst000101_ConstDictionary.RECORD_NON_EXISTENCE + "','" + mst000101_ConstDictionary.RECORD_EXISTENCE + "') AS SENTAKU ");
		sb.append("			,RJC.MAXYUKO AS MAXYUKO ");
		sb.append("		FROM ");
		sb.append("			(SELECT ");
		sb.append("				 SUB0.KANRI_KB ");
		sb.append("				,SUB0.KANRI_CD ");
		sb.append("				,SUB0.TENPO_CD ");
		sb.append("				,SUB0.YUKO_DT ");
		sb.append("				,SUB0.JIDO_HACHU_KB ");
		sb.append("				,SUB0.INSERT_TS ");
		sb.append("				,SUB0.INSERT_USER_ID ");
		sb.append("				,SUB0.UPDATE_TS ");
		sb.append("				,SUB0.UPDATE_USER_ID ");
		sb.append("				,SUB0.DELETE_FG ");
		sb.append(" 			,(SELECT MAX(YUKO_DT) AS YUKO_DT FROM R_JIDOHACHU_CTL SUBT ");
		sb.append("				  WHERE	SUB0.KANRI_KB = SUBT.KANRI_KB ");
		sb.append("				  AND SUB0.KANRI_CD = SUBT.KANRI_CD ");
		sb.append("				  AND SUB0.TENPO_CD = SUBT.TENPO_CD ) AS MAXYUKO ");
		sb.append("			FROM ");
		sb.append("				R_JIDOHACHU_CTL SUB0, ");

		sb.append(" (select ");
		sb.append(" 	rj1.kanri_cd as kanri_cd, ");
		sb.append(" 	rj1.kanri_kb as kanri_kb, ");
		sb.append(" 	rj1.tenpo_cd as tenpo_cd, ");
		sb.append(" 	rj1.yuko_dt as yuko_dt ");
		sb.append(" from ");
		sb.append(" 	r_jidohachu_ctl rj1, ");
		sb.append(" ( ");

		sb.append("				SELECT ");
		sb.append("					 KANRI_KB ");
		sb.append("					,KANRI_CD ");
		sb.append("					,TENPO_CD ");
		sb.append("					,MAX(YUKO_DT) AS YUKO_DT ");
		sb.append("				FROM ");
		sb.append("					R_JIDOHACHU_CTL ");

		if (!(map.get("mode").equals(mst000101_ConstDictionary.PROCESS_REFERENCE) && map.get("yuko_dt").toString().length() == 0)) {
			sb.append("				WHERE ");
//仕様変更 20050930 sawabe 有効日は入力日付以前のもので検索する
//			if (map.get("mode").equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
//				sb.append("	YUKO_DT >= '" + map.get("yuko_dt") + "' ");
//			} else {
				sb.append("	YUKO_DT <= '" + map.get("yuko_dt") + "' ");
				/* Add by Kou 2006-03-27 ===BEGIN===
				 * 削除データを除く*/
				sb.append("		 AND KANRI_CD >= '" + map.get("kanri_cd") + "' ");
				sb.append("		 AND KANRI_KB = '" + map.get("kanri_kb") + "' ");
				/* Add by Kou 2006-03-27 ===END===*/

//			}
		}
//		if (!(map.get("mode").equals(mst000101_ConstDictionary.PROCESS_REFERENCE) && map.get("yuko_dt").toString().length() == 0)) {
//			sb.append("	AND	DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		} else {
//			sb.append("	WHERE DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		}
		sb.append("				GROUP BY ");
		sb.append("					 KANRI_KB ");
		sb.append("					,KANRI_CD ");
		sb.append("					,TENPO_CD ");

		sb.append(" )rj ");
		sb.append(" where ");
		sb.append(" 	rj1.kanri_cd = rj.kanri_cd and ");
		sb.append(" 	rj1.kanri_kb = rj.kanri_kb and ");
		sb.append(" 	rj1.tenpo_cd = rj.tenpo_cd and ");
		sb.append(" 	rj1.yuko_dt = rj.yuko_dt and ");
		sb.append(" 	rj1.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		sb.append("				) SUB1 ");
		sb.append("			WHERE ");
		sb.append("				SUB0.KANRI_KB = SUB1.KANRI_KB ");
		sb.append("			AND SUB0.KANRI_CD = SUB1.KANRI_CD ");
		sb.append("			AND SUB0.TENPO_CD = SUB1.TENPO_CD ");
		sb.append("			AND SUB0.YUKO_DT  = SUB1.YUKO_DT ");
		sb.append("			) RJC, ");

		sb.append(" (select ");
		sb.append(" 	rj1.kanri_cd as kanri_cd, ");
		sb.append(" 	rj1.kanri_kb as kanri_kb, ");
		sb.append(" 	rj1.tenpo_cd as tenpo_cd, ");
		sb.append(" 	rj1.yuko_dt as yuko_dt ");
		sb.append(" from ");
		sb.append(" 	r_jidohachu_ctl rj1, ");
		sb.append(" ( ");

		sb.append("			SELECT ");
		sb.append("				 KANRI_KB ");
		sb.append("				,KANRI_CD ");
		sb.append("				,TENPO_CD ");
		sb.append("				,MAX(YUKO_DT) YUKO_DT ");
		sb.append("			 FROM ");
		sb.append("				R_JIDOHACHU_CTL ");
		sb.append("			 WHERE ");
		sb.append("				KANRI_KB = '" + map.get("kanri_kb") + "' ");
		if (!(map.get("mode").equals(mst000101_ConstDictionary.PROCESS_REFERENCE) && map.get("yuko_dt").toString().length() == 0)) {
//仕様変更 20050930 sawabe 有効日は入力日付以前のもので検索する
//			if (map.get("mode").equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
//				sb.append("		 AND YUKO_DT >= '" + map.get("yuko_dt") + "' ");
//			} else {
				sb.append("		 AND YUKO_DT <= '" + map.get("yuko_dt") + "' ");
				/* Add by Kou 2006-03-27 ===BEGIN===
				 * 削除データを除く*/
				sb.append("		 AND KANRI_CD >= '" + map.get("kanri_cd") + "' ");
				/* Add by Kou 2006-03-27 ===END===*/

//			}

		}
//		sb.append("			AND	DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("			 GROUP BY ");
		sb.append("				 KANRI_KB ");
		sb.append("				,KANRI_CD ");
		sb.append("				,TENPO_CD ");

		sb.append(" )rj ");
		sb.append(" where ");
		sb.append(" 	rj1.kanri_cd = rj.kanri_cd and ");
		sb.append(" 	rj1.kanri_kb = rj.kanri_kb and ");
		sb.append(" 	rj1.tenpo_cd = rj.tenpo_cd and ");
		sb.append(" 	rj1.yuko_dt = rj.yuko_dt and ");
		sb.append(" 	rj1.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		sb.append("		) JIDOU ");
		sb.append("			,(SELECT ");
		sb.append("				 KANRI_CD ");
		sb.append("				,TENPO_CD ");
		sb.append("				,TENPO_NA ");
		sb.append("			  FROM ");
		sb.append("				(SELECT ");
		sb.append("					 TENPO_CD ");
		sb.append("					,KANJI_RN AS TENPO_NA ");
		sb.append("				 FROM ");
		sb.append("					 R_TENPO ");
		sb.append("				 WHERE ");
		sb.append("					 TENPO_KB = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
		sb.append("				 AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("				 ORDER BY ");
		sb.append("					 TENPO_CD ");
		sb.append("			) TENPO ");
		sb.append("			,(SELECT ");
		sb.append("				 CODE_CD AS KANRI_CD ");
		sb.append("				,KANJI_RN AS KANRI_NA ");
		sb.append("			  FROM ");
		sb.append("				 R_NAMECTF ");
		sb.append("				,(SELECT DISTINCT ");
		if (map.get("kanri_kb").equals(mst000901_KanriKbDictionary.HANKU.getCode())) {
			sb.append("				HANKU_CD AS KANRI_CD ");
		} else {
			sb.append("				HINSYU_CD AS KANRI_CD ");
		}
		sb.append("				  FROM ");
		sb.append("					R_SYOHIN_TAIKEI ");

		// 小業種コードが入っている場合
		if( map.get("s_gyosyu_cd") != null && ((String)map.get("s_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(" WHERE S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "'");

		}else{

			if(map.get("selected_gyousyu_cd").equals("01")){

				// 選択業種が01(衣料)
				sb.append(" WHERE D_GYOSYU_CD = '0011'");

			}else if(map.get("selected_gyousyu_cd").equals("02")){

				// 選択業種が02(住生活)
				sb.append(" WHERE D_GYOSYU_CD NOT IN('0011', '0033', '0044')");

			}else if(map.get("selected_gyousyu_cd").equals("03")){

				// 選択業種が03(加工食品)
				sb.append(" WHERE  D_GYOSYU_CD = '0033'");
				sb.append("AND S_GYOSYU_CD = '0087'");

			}else if(map.get("selected_gyousyu_cd").equals("04")){

				// 選択業種が04(生鮮)
				sb.append(" WHERE D_GYOSYU_CD = '0033'");
				sb.append("AND S_GYOSYU_CD <> '0087'");
			}
		}

		sb.append("	) RST ");

		sb.append(" WHERE ");
		if (map.get("kanri_kb").equals(mst000901_KanriKbDictionary.HANKU.getCode())) {
			sb.append(" SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
		} else {
			sb.append("	SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
		}

		if (map.get("mode").equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
			sb.append("	AND CODE_CD >= '" + map.get("kanri_cd") + "' ");
		} else {
			sb.append(" AND	CODE_CD IN (SELECT KANRI_CD FROM R_JIDOHACHU_CTL ");
			sb.append("		WHERE KANRI_KB = '" + map.get("kanri_kb") + "' ");
			sb.append("		AND   KANRI_CD >= '" + map.get("kanri_cd") + "' ");

			if (!(map.get("mode").equals(mst000101_ConstDictionary.PROCESS_REFERENCE) && map.get("yuko_dt").toString().length() == 0)) {
			sb.append("				AND YUKO_DT <= '" + map.get("yuko_dt") + "' ");
			}

			sb.append("	AND	DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			sb.append("	GROUP BY KANRI_CD ");
			sb.append("	) ");
		}
		sb.append("	AND RST.KANRI_CD = R_NAMECTF.CODE_CD ");
		sb.append("	AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("	ORDER BY ");
		sb.append("	CODE_CD ");
		sb.append("	) KANRI ");
		sb.append("	ORDER BY ");
		sb.append("	KANRI_CD ");
		sb.append("	,TENPO_CD ");
		sb.append("	) TMP ");
		sb.append("	WHERE ");
		sb.append("	  JIDOU.KANRI_KB(+) = '" + map.get("kanri_kb") + "' ");
		sb.append("	AND JIDOU.KANRI_CD(+) = TMP.KANRI_CD ");
		sb.append("	AND JIDOU.TENPO_CD(+) = TMP.TENPO_CD ");
		sb.append("	AND RJC.KANRI_KB(+) = JIDOU.KANRI_KB ");
		sb.append("	AND RJC.KANRI_CD(+) = JIDOU.KANRI_CD ");
		sb.append("	AND RJC.TENPO_CD(+) = JIDOU.TENPO_CD ");
		/* Add by Kou 2006-03-27 ===BEGIN===
		 * 削除データを除く*/
		sb.append("	AND	DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		/* Add by Kou 2006-03-27 ===END===*/
		sb.append(" ) ");
		sb.append("	ORDER BY ");
		sb.append("	  KANRI_KB ");
		sb.append("	 ,KANRI_CD ");
		sb.append("	 ,TENPO_CD ");

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
