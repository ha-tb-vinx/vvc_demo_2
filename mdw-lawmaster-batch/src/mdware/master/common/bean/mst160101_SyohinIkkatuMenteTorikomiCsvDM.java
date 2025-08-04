/**
 * <p>タイトル: 新ＭＤシステム（マスター管理） 商品マスタ一括修正（条件指定）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する 商品マスタ一括修正（条件指定）のCSVDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/16)初版作成
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
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理） 商品マスタ一括修正（条件指定）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する 商品マスタ一括修正（条件指定）のCSVDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst160101_SyohinIkkatuMenteTorikomiCsvDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst160101_SyohinIkkatuMenteTorikomiCsvDM()
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
		mst160101_SyohinIkkatuMenteBean bean = new mst160101_SyohinIkkatuMenteBean();

		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setBumonKanjiRn( rest.getString("bumon_kanji_rn") );
		bean.setJanMarkCd( rest.getString("jan_mark_cd") );
		bean.setHinmeiKanjiNa( rest.getString("hinmei_kanji_na") );
		bean.setKikakuKanjiNa( rest.getString("kikaku_kanji_na") );
		bean.setGentankaVl( rest.getDouble("gentanka_vl") );
		bean.setBaitankaVl( rest.getLong("baitanka_vl") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setSiiresakiKanjiRn( rest.getString("siiresaki_kanji_rn") );
		bean.setHanbaiStDt( rest.getString("hanbai_st_dt") );
		bean.setHanbaiEdDt( rest.getString("hanbai_ed_dt") );
		bean.setSeasonCd( rest.getString("season_cd") );
		bean.setSeasonKanjiRn( rest.getString("season_kanji_rn") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setInsertUserNm( rest.getString("insert_user_nm") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setUpdateUserNm( rest.getString("update_user_nm") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setSentakuFg( rest.getString("sentaku_fg") );

		//MOD by Tanigawa 2006/9/22 変更依頼書№0015対応 仕入品番コードを明細表示項目として追加 START
		bean.setSiiresakisyohinCd( rest.getString("siire_hinban_cd") );
		//MOD by Tanigawa 2006/9/22 変更依頼書№0015対応 仕入品番コードを明細表示項目として追加  END

//		 ADD by Tanigawa 2006/11/01 障害票№0194対応 START
		bean.setKeshiBaikaVl(rest.getLong("keshi_baika_vl"));
//		 ADD by Tanigawa 2006/11/01 障害票№0194対応  END

//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		bean.setColorNa(rest.getString("color_na"));
		bean.setSizeNa(rest.getString("size_na"));
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

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

		//▼SELECT句
		sb.append(" SELECT ");

		//部門コード
		sb.append("   TRIM(TBL1.bumon_cd) bumon_cd, ");
		//部門名称
		sb.append("   (SELECT kanji_rn FROM R_NAMECTF nmctf2");
		sb.append("    WHERE nmctf2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append("    AND nmctf2.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "'");
		sb.append("    AND nmctf2.code_cd = TBL1.bumon_cd ");
		sb.append("   ) as bumon_kanji_rn, ");
		//有効日
		sb.append("   TRIM(TBL1.yuko_dt) yuko_dt, ");
		//商品コード
		sb.append("   TRIM(TBL1.syohin_cd) syohin_cd, ");
		//JANメーカーコード
		sb.append("   TRIM(TBL1.maker_cd) jan_mark_cd, ");
		//品名漢字名
		sb.append("   TRIM(TBL1.hinmei_kanji_na) hinmei_kanji_na, ");
		//規格漢字名
		sb.append("   TRIM(TBL1.kikaku_kanji_na) kikaku_kanji_na, ");
		//原価
		sb.append("   TBL1.gentanka_vl gentanka_vl, ");
		//売価
		sb.append("   TBL1.baitanka_vl  baitanka_vl, ");
		//仕入先コード
		sb.append("   TRIM(TBL1.siiresaki_cd) siiresaki_cd, ");
		//仕入先名称
		sb.append("   (SELECT kanji_rn FROM r_siiresaki siiresaki2");
		sb.append("    WHERE siiresaki2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append("    AND siiresaki2.tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
		sb.append("    AND siiresaki2.siiresaki_cd = TBL1.siiresaki_cd ");
		sb.append("    AND siiresaki2.kanri_cd = '0000' ");
		sb.append("    AND siiresaki2.KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
		sb.append("   ) as siiresaki_kanji_rn, ");
		//販売開始日
		sb.append("   TRIM(TBL1.hanbai_st_dt) hanbai_st_dt, ");
		//販売終了日
		sb.append("   TRIM(TBL1.hanbai_ed_dt) hanbai_ed_dt, ");
		//シーズンコード
		sb.append("   TRIM(TBL1.season_cd) season_cd, ");
		//シーズン名称
		sb.append("   (SELECT kanji_rn FROM R_NAMECTF nmctf2");
		sb.append("    WHERE nmctf2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append("    AND nmctf2.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SEASON, userLocal) + "'");
		sb.append("    AND nmctf2.code_cd = TBL1.season_cd ");
		sb.append("   ) as season_kanji_rn, ");
		//作成年月日
		sb.append("   TRIM(TBL1.insert_ts) insert_ts, ");
		//作成社員ID
		sb.append("   TRIM(TBL1.insert_user_id) insert_user_id, ");
		//作成社員名
//		sb.append("   TRIM((SELECT riyo_user_na FROM r_riyo_user ");
//		sb.append("         WHERE ");
//		sb.append("           riyo_user_id = TBL1.insert_user_id ");
//		sb.append("         )) insert_user_nm, ");
		sb.append("   TRIM((SELECT user_na FROM r_portal_user ");
		sb.append("         WHERE ");
		sb.append("           user_id = TRIM(TBL1.insert_user_id) ");
		sb.append("         )) insert_user_nm, ");
		//更新年月日
		sb.append("   TRIM(TBL1.update_ts) update_ts, ");
		//更新社員ID
		sb.append("   TRIM(TBL1.update_user_id) update_user_id, ");
		//更新社員名
//		sb.append("   TRIM((SELECT riyo_user_na FROM r_riyo_user ");
//		sb.append("         WHERE riyo_user_id = TBL1.update_user_id");
//		sb.append("         )) update_user_nm, ");
		sb.append("   TRIM((SELECT user_na FROM r_portal_user ");
		sb.append("         WHERE user_id = TRIM(TBL1.update_user_id)");
		sb.append("         )) update_user_nm, ");
		//削除フラグ
		sb.append("   TRIM(TBL1.delete_fg) delete_fg, ");
		//選択フラグ
		sb.append("   ' ' sentaku_fg ");
		//MOD by Tanigawa 2006/9/22 変更依頼書№0015対応 仕入品番コードを明細表示項目として追加 START
		sb.append("    , ");
		sb.append("   TRIM(TBL1.siire_hinban_cd) siire_hinban_cd ");
		//MOD by Tanigawa 2006/9/22 変更依頼書№0015対応 仕入品番コードを明細表示項目として追加  END
		//ADD by Tanigawa 2006/11/07 障害票№0194対応 START
		sb.append("    , ");
		sb.append("   TRIM(TBL1.KESHI_BAIKA_VL) KESHI_BAIKA_VL ");
		//ADD by Tanigawa 2006/11/07 障害票№0194対応  END

//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		if (map.get("system_kb") != null && map.get("system_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			//タグ衣料の場合は、名称・CTFマスタよりカラー名称、サイズ名称を取得
			sb.append("	    , ");
			sb.append("	    TBL3.KANJI_RN AS color_na, ");	//カラー名称(漢字略称)
			sb.append("	    TBL4.KANJI_RN AS size_na ");		//サイズ名称(漢字略称)
		}else if ( map.get("system_kb") != null && map.get("system_kb").equals("J") ){
			//実用衣料の場合は、商品マスタよりカラー名称、サイズ名称を取得
			sb.append("	    , ");
			sb.append("	    TBL1.KIKAKU_KANJI_NA AS color_na, ");	//カラー名称(漢字規格)
			sb.append("	    TBL1.KIKAKU_KANJI_2_NA AS size_na ");	//サイズ名称(漢字規格２)
		}else {
			//衣料以外
			sb.append("	    , ");
			sb.append("	    '' AS color_na, ");	//カラー名称(漢字規格)
			sb.append("	    '' AS size_na ");	//サイズ名称(漢字規格２)
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		//▼FROM句
		sb.append(" FROM R_SYOHIN TBL1, ");
		sb.append(" R_SYOHIN_TAIKEI TBL2 ");

//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		if (map.get("system_kb") != null && map.get("system_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			// タグ
			sb.append(" LEFT JOIN R_NAMECTF TBL3 ON (TBL3.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)+"' AND TBL1.COLOR_CD = TBL3.CODE_CD) ");	//カラー名称取得用LEFT JOIN
			sb.append(" LEFT JOIN R_NAMECTF TBL4 ON (TBL4.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)+"' AND TBL1.SIZE_CD  = TBL4.CODE_CD) ");	//サイズ名称取得用LEFT JOIN
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		StringBuffer strWhere = new StringBuffer();

		//WHERE句
		strWhere.append(" where TBL2.UNIT_CD = TBL1.UNIT_CD");
		strWhere.append(" and   TBL2.BUMON_CD = TBL1.BUMON_CD");

		//部門コード
		strWhere.append(" and TBL1.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  ");

		//商品コード
		strWhere.append(" and TBL1.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "'  ");

		//delete_fg に対するWHERE区
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			strWhere.append(" and TBL1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		}

		StringBuffer sql = new StringBuffer();
		sql.append(sb);
		sql.append(strWhere);
		// ===BEGIN=== Modify by kou 2006/10/29
		//sql.append(" and TBL1.YUKO_DT = MSP710101_GETSYOHINYUKODT(TBL1.BUMON_CD, TBL1.SYOHIN_CD, cast(null as char)) ");
		sql.append(" and TBL1.YUKO_DT = (SELECT MAX(YUKO_DT) FROM R_SYOHIN RS WHERE RS.BUMON_CD = TBL1.BUMON_CD AND RS.SYOHIN_CD = TBL1.SYOHIN_CD) ");
		// ===END=== Modify by kou 2006/10/29
		sql.append(" order by ");
//		sql.append(" bumon_cd");
//		sql.append(" ,");
//		sql.append(" syohin_cd");
		//MOD by Tanigawa 2006/9/22 変更依頼書№0015対応 データ取込時の並び順を検索時のものと合わせる START
		sql.append(" TBL1.siire_hinban_cd ASC");
		sql.append(" ,");
		sql.append(" TBL1.syohin_cd ASC");
		//MOD by Tanigawa 2006/9/22 変更依頼書№0015対応 データ取込時の並び順を検索時のものと合わせる  END

//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sql.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		return sql.toString();
	}


	/**
	 * 検索用ＳＱＬの生成を行う。 PreparedStatementVersion
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
//	↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//	public String getSelectSqlForPrepared()
	public String getSelectSqlForPrepared(String systemKb)
//	↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sb = new StringBuffer();

		//▼SELECT句
		sb.append(" SELECT ");
		//部門コード
		sb.append("   TRIM(TBL1.bumon_cd) bumon_cd, ");
		//部門名称
		sb.append("   (SELECT kanji_rn FROM R_NAMECTF nmctf2");
		sb.append("    WHERE nmctf2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append("    AND nmctf2.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "'");
		sb.append("    AND nmctf2.code_cd = TBL1.bumon_cd ");
		sb.append("   ) as bumon_kanji_rn, ");
		//有効日
		sb.append("   TRIM(TBL1.yuko_dt) yuko_dt, ");
		//商品コード
		sb.append("   TRIM(TBL1.syohin_cd) syohin_cd, ");
		//JANメーカーコード
		sb.append("   TRIM(TBL1.maker_cd) jan_mark_cd, ");
		//品名漢字名
		sb.append("   TRIM(TBL1.hinmei_kanji_na) hinmei_kanji_na, ");
		//規格漢字名
		sb.append("   TRIM(TBL1.kikaku_kanji_na) kikaku_kanji_na, ");
		//原価
		sb.append("   TBL1.gentanka_vl gentanka_vl, ");
		//売価
		sb.append("   TBL1.baitanka_vl  baitanka_vl, ");
		//仕入先コード
		sb.append("   TRIM(TBL1.siiresaki_cd) siiresaki_cd, ");
		//仕入先名称
		sb.append("   (SELECT kanji_rn FROM r_siiresaki siiresaki2");
		sb.append("    WHERE siiresaki2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append("    AND siiresaki2.tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
		sb.append("    AND siiresaki2.siiresaki_cd = TBL1.siiresaki_cd ");
		sb.append("    AND siiresaki2.kanri_cd = '0000' ");
		sb.append("    AND siiresaki2.KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
		sb.append("   ) as siiresaki_kanji_rn, ");
		//販売開始日
		sb.append("   TRIM(TBL1.hanbai_st_dt) hanbai_st_dt, ");
		//販売終了日
		sb.append("   TRIM(TBL1.hanbai_ed_dt) hanbai_ed_dt, ");
		//シーズンコード
		sb.append("   TRIM(TBL1.season_cd) season_cd, ");
		//シーズン名称
		sb.append("   (SELECT kanji_rn FROM R_NAMECTF nmctf2");
		sb.append("    WHERE nmctf2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append("    AND nmctf2.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SEASON, userLocal) + "'");
		sb.append("    AND nmctf2.code_cd = TBL1.season_cd ");
		sb.append("   ) as season_kanji_rn, ");
		//作成年月日
		sb.append("   TRIM(TBL1.insert_ts) insert_ts, ");
		//作成社員ID
		sb.append("   TRIM(TBL1.insert_user_id) insert_user_id, ");
		//作成社員名
//		sb.append("   TRIM((SELECT riyo_user_na FROM r_riyo_user ");
//		sb.append("         WHERE ");
//		sb.append("           riyo_user_id = TBL1.insert_user_id ");
//		sb.append("         )) insert_user_nm, ");
		sb.append("   TRIM((SELECT user_na FROM r_portal_user ");
		sb.append("         WHERE ");
		sb.append("           user_id = TRIM(TBL1.insert_user_id) ");
		sb.append("         )) insert_user_nm, ");
		//更新年月日
		sb.append("   TRIM(TBL1.update_ts) update_ts, ");
		//更新社員ID
		sb.append("   TRIM(TBL1.update_user_id) update_user_id, ");
		//更新社員名
//		sb.append("   TRIM((SELECT riyo_user_na FROM r_riyo_user ");
//		sb.append("         WHERE riyo_user_id = TBL1.update_user_id");
//		sb.append("         )) update_user_nm, ");
		sb.append("   TRIM((SELECT user_na FROM r_portal_user ");
		sb.append("         WHERE user_id = TRIM(TBL1.update_user_id) ");
		sb.append("         )) update_user_nm, ");
		//削除フラグ
		sb.append("   TRIM(TBL1.delete_fg) delete_fg, ");
		//選択フラグ
		sb.append("   ' ' sentaku_fg ");
		sb.append("    , ");
		sb.append("   TRIM(TBL1.siire_hinban_cd) siire_hinban_cd ");
		sb.append("    , ");
		sb.append("   TRIM(TBL1.KESHI_BAIKA_VL) KESHI_BAIKA_VL ");

//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		if (systemKb != null && systemKb.equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			//タグ衣料の場合は、名称・CTFマスタよりカラー名称、サイズ名称を取得
			sb.append("	    , ");
			sb.append("	    TBL3.KANJI_RN AS color_na, ");	//カラー名称(漢字略称)
			sb.append("	    TBL4.KANJI_RN AS size_na ");		//サイズ名称(漢字略称)
		}else if ( systemKb != null && systemKb.equals("J") ){
			//実用衣料の場合は、商品マスタよりカラー名称、サイズ名称を取得
			sb.append("	    , ");
			sb.append("	    TBL1.KIKAKU_KANJI_NA AS color_na, ");	//カラー名称(漢字規格)
			sb.append("	    TBL1.KIKAKU_KANJI_2_NA AS size_na ");	//サイズ名称(漢字規格２)
		}else {
			//衣料以外
			sb.append("	    , ");
			sb.append("	    '' AS color_na, ");	//カラー名称(漢字規格)
			sb.append("	    '' AS size_na ");	//サイズ名称(漢字規格２)
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		//▼FROM句
		sb.append(" FROM R_SYOHIN TBL1, ");
		sb.append(" R_SYOHIN_TAIKEI TBL2 ");

//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		if (systemKb != null && systemKb.equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			// タグ
			sb.append(" LEFT JOIN R_NAMECTF TBL3 ON (TBL3.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)+"' AND TBL1.COLOR_CD = TBL3.CODE_CD) ");	//カラー名称取得用LEFT JOIN
			sb.append(" LEFT JOIN R_NAMECTF TBL4 ON (TBL4.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)+"' AND TBL1.SIZE_CD  = TBL4.CODE_CD) ");	//サイズ名称取得用LEFT JOIN
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		//WHERE句
		sb.append(" where TBL2.UNIT_CD = TBL1.UNIT_CD");
		sb.append(" and   TBL2.BUMON_CD = TBL1.BUMON_CD");
//		sb.append(" and TBL1.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  ");
//		sb.append(" and TBL1.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "'  ");
//		sb.append(" and TBL1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append(" and TBL1.bumon_cd  = ? ");
		sb.append(" and TBL1.syohin_cd = ? ");
		sb.append(" and TBL1.delete_fg = ? ");
		sb.append(" and TBL1.YUKO_DT = (SELECT MAX(YUKO_DT) FROM R_SYOHIN RS WHERE RS.BUMON_CD = TBL1.BUMON_CD AND RS.SYOHIN_CD = TBL1.SYOHIN_CD) ");
		sb.append(" order by ");
		sb.append(" TBL1.siire_hinban_cd ASC");
		sb.append(" ,");
		sb.append(" TBL1.syohin_cd ASC");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

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
		StringBuffer sb = new StringBuffer();
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
		StringBuffer sb = new StringBuffer();
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
		mst160101_SyohinIkkatuMenteBean bean = (mst160101_SyohinIkkatuMenteBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_SYOHIN where ");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		sb.append(" AND");
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
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
