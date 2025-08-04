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
 * <p>説明: 新ＭＤシステムで使用する 商品マスタ一括修正（条件指定）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst160101_SyohinIkkatuMenteDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst160101_SyohinIkkatuMenteDM()
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
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
		//bean.setHankuCd( rest.getString("hanku_cd") );
		//bean.setHankuKanjiRn( rest.getString("hanku_kanji_rn") );
		//bean.setSyohinCd( rest.getString("syohin_cd") );
		//bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		//bean.setSyohin1Cd( rest.getString("syohin_1_cd") );
		//bean.setSyohin2Cd( rest.getString("syohin_2_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		//ADD by Tanigawa 2006/9/20 変更依頼書№0015対応 START
		bean.setSiiresakisyohinCd( rest.getString("siire_hinban_cd") );	//商品マスタ.仕入先品番は、画面上では仕入先商品コードとして表示される
		//ADD by Tanigawa 2006/9/20 変更依頼書№0015対応  END
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setBumonKanjiRn( rest.getString("bumon_kanji_rn") );
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
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
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
//		String whereStr = " WHERE ";
//		String andStr = " AND ";
		StringBuffer sb = new StringBuffer();

		//▼SELECT句
		//sb.append(" SELECT /*+ ordered */ ");
		sb.append(" SELECT DISTINCT ");

		//販区コード
		//sb.append("   TRIM(TBL1.hanku_cd) hanku_cd, ");
		//販区名称
		//sb.append("   (SELECT kanji_rn FROM R_NAMECTF nmctf1");
		//sb.append("    WHERE nmctf1.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		//sb.append("    AND nmctf1.syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "'");
		//sb.append("    AND nmctf1.code_cd = TBL1.hanku_cd ");
		//sb.append("   ) as hanku_kanji_rn, ");
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

//		 ADD by Tanigawa 2006/9/20 変更依頼票№0015対応 START
		//仕入先品番(画面上では、仕入先商品コード)
		sb.append("   TRIM(TBL1.siire_hinban_cd) siire_hinban_cd, ");
//		 ADD by Tanigawa 2006/9/20 変更依頼票№0015対応  END

		//品種コード
		//sb.append("   TRIM(TBL1.hinsyu_cd) hinsyu_cd, ");
		//商品コードの前2桁
		//sb.append("   TRIM(SUBSTR(TBL1.syohin_cd,1,2)) syohin_1_cd, ");
		//商品コード2
		//sb.append("   TRIM(TBL1.syohin_2_cd) syohin_2_cd, ");
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
		//JANメーカーコード
		sb.append("   TRIM(TBL1.maker_cd) jan_mark_cd, "); //バグ修正（2005.07.19）
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
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
		//仕入先名称
		//sb.append("   TRIM(srk.kanji_rn) siiresaki_kanji_rn, ");
		sb.append("   (SELECT kanji_rn FROM r_siiresaki siiresaki2");
		sb.append("    WHERE siiresaki2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append("    AND siiresaki2.tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
		sb.append("    AND siiresaki2.siiresaki_cd = TBL1.siiresaki_cd ");
// ↓↓2006.08.03 HuangJiugen カスタマイズ修正↓↓
//		sb.append("    AND siiresaki2.kanri_cd = TBL1.bumon_cd ");
		sb.append("    AND siiresaki2.kanri_cd = '0000' ");
// ↑↑2006.08.03 HuangJiugen カスタマイズ修正↑↑
		sb.append("    AND siiresaki2.KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
		sb.append("   ) as siiresaki_kanji_rn, ");
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
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
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
		//sb.append("    AND nmctf2.code_cd = TBL1.hanku_cd ");
		sb.append("    AND nmctf2.code_cd = TBL1.season_cd ");
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
		sb.append("   ) as season_kanji_rn, ");
		//作成年月日
		sb.append("   TRIM(TBL1.insert_ts) insert_ts, ");
		//作成社員ID
		sb.append("   TRIM(TBL1.insert_user_id) insert_user_id, ");
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
		//作成社員名
		//sb.append("   TRIM((SELECT user_na FROM sys_sosasya ");
//		sb.append("   TRIM((SELECT riyo_user_na FROM r_riyo_user ");
		sb.append("   TRIM((SELECT user_na FROM r_portal_user ");
		sb.append("         WHERE ");
		//sb.append("           user_id = TBL1.insert_user_id ");
//		sb.append("           riyo_user_id = TBL1.insert_user_id ");
		sb.append("           user_id = TRIM(TBL1.insert_user_id) ");
		//sb.append("         AND hojin_cd = '"+  mst000101_ConstDictionary.HOJIN_CD +"'");
		sb.append("         )) insert_user_nm, ");
		//更新年月日
		sb.append("   TRIM(TBL1.update_ts) update_ts, ");
		//更新社員ID
		sb.append("   TRIM(TBL1.update_user_id) update_user_id, ");
		//更新社員名
		//sb.append("   TRIM((SELECT user_na FROM	sys_sosasya ");
		//sb.append("         WHERE user_id = TBL1.update_user_id");
//		sb.append("   TRIM((SELECT riyo_user_na FROM r_riyo_user ");
//		sb.append("         WHERE riyo_user_id = TBL1.update_user_id");
		sb.append("   TRIM((SELECT user_na FROM r_portal_user ");
		sb.append("         WHERE user_id = TRIM(TBL1.update_user_id)");
		//sb.append("         AND hojin_cd = '"+  mst000101_ConstDictionary.HOJIN_CD +"'");
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
		sb.append("         )) update_user_nm, ");
		//削除フラグ
		sb.append("   TRIM(TBL1.delete_fg) delete_fg, ");
//		//選択フラグ
//		sb.append("   ' ' sentaku_fg ");

//		 ADD by Tanigawa 2006/11/01 障害票№0194対応 START
		//選択フラグ
		sb.append("   ' ' sentaku_fg, ");
		sb.append("   TRIM(TBL1.KESHI_BAIKA_VL) KESHI_BAIKA_VL ");
//		 ADD by Tanigawa 2006/11/01 障害票№0194対応  END

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

//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
// ↓↓2006.08.08 HuangJiugen カスタマイズ修正↓↓
//		if("J".equals(map.get("system_kb")) || "G".equals(map.get("system_kb"))){
		boolean isNeedTanaLocation = false;
//		↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//		if("G".equals(map.get("system_kb"))
		if(("G".equals(map.get("system_kb")) || "F".equals(map.get("system_kb")))
//		↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
				&& ((map.get("tanawaribangou_fm")!=null && map.get("tanawaribangou_fm").toString().trim().length()!=0)
					|| (map.get("tanawaribangou_to")!=null && map.get("tanawaribangou_to").toString().trim().length()!=0))) {
			isNeedTanaLocation = true;
// ↑↑2006.08.08 HuangJiugen カスタマイズ修正↑↑
			sb.append("   R_TANA_LOCATION RTL,");
		}
		//sb.append(" R_SIIRESAKI srk ");
		sb.append(" R_SYOHIN_TAIKEI TBL2 ");
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
//		sb.append("   (SELECT TBL.HANKU_CD,TBL.SYOHIN_CD,MIN(TBL.YUKO_DT) YUKO_DT");
//		sb.append("    FROM (SELECT HANKU_CD,SYOHIN_CD,MAX(YUKO_DT) YUKO_DT");
//		sb.append("          FROM R_SYOHIN ");
//		sb.append("          WHERE DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()+ "' ");
//		sb.append("          AND   YUKO_DT <= TO_CHAR(SYSDATE,'YYYYMMDD') ");
//		sb.append("          GROUP BY HANKU_CD,SYOHIN_CD ");
//		sb.append("			 UNION ALL ");
//		sb.append("          SELECT HANKU_CD,SYOHIN_CD,MIN(YUKO_DT) YUKO_DT ");
//		sb.append("          FROM R_SYOHIN ");
//		sb.append("          WHERE    YUKO_DT >= TO_CHAR(SYSDATE + 1,'YYYYMMDD') ");
//		sb.append("          GROUP BY HANKU_CD,SYOHIN_CD ");
//		sb.append("         ) TBL ");
//		sb.append("		GROUP BY TBL.HANKU_CD,TBL.SYOHIN_CD ");

//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		if (map.get("system_kb") != null && map.get("system_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			// タグ
			sb.append(" LEFT JOIN R_NAMECTF TBL3 ON (TBL3.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)+"' AND TBL1.COLOR_CD = TBL3.CODE_CD) ");	//カラー名称取得用LEFT JOIN
			sb.append(" LEFT JOIN R_NAMECTF TBL4 ON (TBL4.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)+"' AND TBL1.SIZE_CD  = TBL4.CODE_CD) ");	//サイズ名称取得用LEFT JOIN
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		//▼WHERE句
		//sb.append(whereStr);
//		↓↓移植（2006.5.16）↓↓
//		sb.append(" ROWNUM <= 301 ");
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
		StringBuffer strWhere = new StringBuffer();

		strWhere.append(" where TBL2.UNIT_CD = TBL1.UNIT_CD");
		strWhere.append(" and   TBL2.BUMON_CD = TBL1.BUMON_CD");

		//システム区分
		if( map.get("system_kb") != null && ((String)map.get("system_kb")).trim().length() > 0 )
		{
			strWhere.append(" and TBL2.SYSTEM_KB = '" + conv.convertWhereString( (String)map.get("system_kb") ) + "'");
		}

		//部門コード
		strWhere.append(" and TBL2.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  ");

		//品番
		if (map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0) {
			strWhere.append(" and TBL2.hinban_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinban_cd"),4)) + "'");
		}

		//品種
		if (map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0) {
			strWhere.append(" and TBL2.hinsyu_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinsyu_cd"),4)) + "'");
		}

		//ライン
		if (map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0) {
			strWhere.append(" and TBL2.line_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("line_cd"),4)) + "'");
		}

		//ユニット
		if (map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0) {
			strWhere.append(" and TBL2.unit_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("unit_cd"),4)) + "'");
		}


//		 ADD by Tanigawa 2006/10/26 障害票№0194対応 START
		//部門コード
		strWhere.append(" and TBL1.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  ");
		//システム区分
		if( map.get("system_kb") != null && ((String)map.get("system_kb")).trim().length() > 0 )
		{
			strWhere.append(" and TBL1.SYSTEM_KB = '" + conv.convertWhereString( (String)map.get("system_kb") ) + "'");
		}
//		 ADD by Tanigawa 2006/10/26 障害票№0194対応  END


		//仕入先
		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 )
		{
			strWhere.append(" and TBL1.siiresaki_cd like '" + conv.convertWhereString( (String)map.get("siiresaki_cd") ) + "%'");
		}

		//仕入先品番
		if (map.get("siiresakisyihin_cd") != null && ((String)map.get("siiresakisyihin_cd")).trim().length() > 0) {
//			strWhere.append(" and TBL1.SIIRE_HINBAN_CD like '" + conv.convertWhereString((String)map.get("siiresakisyihin_cd")) + "%' ");
//			 ADD by Tanigawa 2006/11/14 障害票№0143対応 START
			if( map.get("siiresakisyohincdChecked") != null && ( new Boolean((String)map.get("siiresakisyohincdChecked")).booleanValue() ) ){
				strWhere.append(" and TBL1.SIIRE_HINBAN_CD like '" + conv.convertWhereString((String)map.get("siiresakisyihin_cd")) + "%' ");
			}else{
				strWhere.append(" and TBL1.SIIRE_HINBAN_CD = '" + conv.convertWhereString((String)map.get("siiresakisyihin_cd")) + "' ");
			}
//			 ADD by Tanigawa 2006/11/14 障害票№0143対応  END
		}

		//JANメーカー
		if (map.get("jan_mark_cd") != null && ((String)map.get("jan_mark_cd")).trim().length() > 0) {
			strWhere.append(" and TBL1.MAKER_CD = '" + conv.convertWhereString((String)map.get("jan_mark_cd")) + "' ");
		}

		//販売期間
		//===BEGIN=== Modify by kou 2006/11/27
//		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0 &&
//			map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0) {
//			strWhere.append(" and TBL1.hanbai_st_dt <= '" + conv.convertWhereString((String)map.get("hanbai_ed_dt")) + "' ");
//			strWhere.append(" and TBL1.hanbai_ed_dt >= '" + conv.convertWhereString((String)map.get("hanbai_st_dt")) + "' ");
//		} else if (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0) {
//			strWhere.append(" and TBL1.hanbai_st_dt <= '" + conv.convertWhereString((String)map.get("hanbai_st_dt")) + "' ");
//			strWhere.append(" and TBL1.hanbai_ed_dt >= '" + conv.convertWhereString((String)map.get("hanbai_st_dt")) + "' ");
//		}
		//販売開始日のみ指定
		if (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0
			&& (map.get("hanbai_ed_dt") == null || ((String)map.get("hanbai_ed_dt")).trim().length() == 0)
			) {
				strWhere.append(" and TBL1.hanbai_st_dt = '")
					.append(conv.convertWhereString((String)map.get("hanbai_st_dt"))).append("' ");
		}

		//販売終了日のみ指定
		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0
			&& (map.get("hanbai_st_dt") == null || ((String)map.get("hanbai_st_dt")).trim().length() == 0)
			) {
				strWhere.append(" and TBL1.hanbai_ed_dt = '")
					.append(conv.convertWhereString((String)map.get("hanbai_ed_dt"))).append("' ");
		}

		//両方指定
		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0
			&& (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0)
			) {
				strWhere.append(" and TBL1.hanbai_st_dt >= '")
					.append(conv.convertWhereString((String)map.get("hanbai_st_dt"))).append("' ");
				strWhere.append(" and TBL1.hanbai_ed_dt <= '")
					.append(conv.convertWhereString((String)map.get("hanbai_ed_dt"))).append("' ");
		}

		//===END=== modify by kou 2006/11/27

		//発注期間
//		↓↓2006.11.30 H.Yamamoto カスタマイズ修正↓↓
//		if (map.get("hattyuu_ed_dt") != null && ((String)map.get("hattyuu_ed_dt")).trim().length() > 0 &&
//				map.get("hattyuu_st_dt") != null && ((String)map.get("hattyuu_st_dt")).trim().length() > 0) {
//			strWhere.append(" and TBL1.TEN_HACHU_ST_DT <= '" + conv.convertWhereString((String)map.get("hattyuu_ed_dt")) + "' ");
//			strWhere.append(" and TBL1.TEN_HACHU_ED_DT >= '" + conv.convertWhereString((String)map.get("hattyuu_st_dt")) + "' ");
//		} else if (map.get("hattyuu_st_dt") != null && ((String)map.get("hattyuu_st_dt")).trim().length() > 0) {
//			strWhere.append(" and TBL1.TEN_HACHU_ST_DT <= '" + conv.convertWhereString((String)map.get("hattyuu_st_dt")) + "' ");
//			strWhere.append(" and TBL1.TEN_HACHU_ED_DT >= '" + conv.convertWhereString((String)map.get("hattyuu_st_dt")) + "' ");
//		}
		//発注開始日のみ指定
		if (map.get("hattyuu_st_dt") != null && ((String)map.get("hattyuu_st_dt")).trim().length() > 0
			&& (map.get("hattyuu_ed_dt") == null || ((String)map.get("hattyuu_ed_dt")).trim().length() == 0)
			) {
				strWhere.append(" and TBL1.ten_hachu_st_dt = '")
					.append(conv.convertWhereString((String)map.get("hattyuu_st_dt"))).append("' ");
		}

		//発注終了日のみ指定
		if (map.get("hattyuu_ed_dt") != null && ((String)map.get("hattyuu_ed_dt")).trim().length() > 0
			&& (map.get("hattyuu_st_dt") == null || ((String)map.get("hattyuu_st_dt")).trim().length() == 0)
			) {
				strWhere.append(" and TBL1.ten_hachu_ed_dt = '")
					.append(conv.convertWhereString((String)map.get("hattyuu_ed_dt"))).append("' ");
		}

		//両方指定
		if (map.get("hattyuu_ed_dt") != null && ((String)map.get("hattyuu_ed_dt")).trim().length() > 0
			&& (map.get("hattyuu_st_dt") != null && ((String)map.get("hattyuu_st_dt")).trim().length() > 0)
			) {
				strWhere.append(" and TBL1.ten_hachu_st_dt >= '")
					.append(conv.convertWhereString((String)map.get("hattyuu_st_dt"))).append("' ");
				strWhere.append(" and TBL1.ten_hachu_ed_dt <= '")
					.append(conv.convertWhereString((String)map.get("hattyuu_ed_dt"))).append("' ");
		}
//		↑↑2006.11.30 H.Yamamoto カスタマイズ修正↑↑

		//サブクラス
		if( map.get("sabukurasu_cd") != null && ((String)map.get("sabukurasu_cd")).trim().length() > 0 )
		{
			strWhere.append(" and TBL1.subclass_cd = '" + conv.convertWhereString( (String)map.get("sabukurasu_cd") ) + "'");
		}

		//棚番
		if("J".equals(map.get("system_kb"))){
			if(map.get("tanawaribangou_fm")!=null && map.get("tanawaribangou_fm").toString().trim().length()!=0){
				strWhere.append(" and TBL1.tana_no_nb >= "+ replaceAll((String)map.get("tanawaribangou_fm"), ",", "") + " ");
			}
			if(map.get("tanawaribangou_to")!=null && map.get("tanawaribangou_to").toString().trim().length()!=0){
				strWhere.append(" and TBL1.tana_no_nb <= "+ replaceAll((String)map.get("tanawaribangou_to"), ",", "") + " ");
			}
// ↓↓2006.08.08 HuangJiugen カスタマイズ修正↓↓
//		} else if ("G".equals(map.get("system_kb"))){
		} else if (isNeedTanaLocation){
// ↑↑2006.08.08 HuangJiugen カスタマイズ修正↑↑
// ↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			strWhere.append(" and rtl.bumon_cd = TBL1.bumon_cd ");
//			strWhere.append(" and rtl.KEIKAKU_SYOHIN_CD = TBL1.syohin_cd ");
			strWhere.append(" and rtl.SYOHIN_CD = TBL1.syohin_cd ");
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			if(map.get("tanawaribangou_fm")!=null && map.get("tanawaribangou_fm").toString().trim().length()!=0){
				strWhere.append(" and rtl.tanadai_nb >= "+ replaceAll((String)map.get("tanawaribangou_fm"), ",", "") + " ");
			}
			if(map.get("tanawaribangou_to")!=null && map.get("tanawaribangou_to").toString().trim().length()!=0){
				strWhere.append(" and rtl.tanadai_nb <= "+ replaceAll((String)map.get("tanawaribangou_to"), ",", "") + " ");
			}
		}

		//シーズン
		if( map.get("season_cd") != null && ((String)map.get("season_cd")).trim().length() > 0 )
		{
			strWhere.append(" and TBL1.SEASON_CD = '" + conv.convertWhereString( (String)map.get("season_cd") ) + "'");
		}

		//売単価
		if(map.get("baitankavl_fm")!=null && map.get("baitankavl_fm").toString().trim().length()!=0){
			if (!replaceAll(conv.convertWhereString( (String)map.get("baitankavl_fm")),",","").equals("")) {
				strWhere.append(" AND TBL1.baitanka_vl >= "+ replaceAll(conv.convertWhereString( (String)map.get("baitankavl_fm")),",","") +" ");

			}
		}
		if(map.get("baitankavl_to")!=null && map.get("baitankavl_to").toString().trim().length()!=0){
			if (!replaceAll(conv.convertWhereString( (String)map.get("baitankavl_to")),",","").equals("")) {
				strWhere.append(" AND TBL1.baitanka_vl <= "+ replaceAll(conv.convertWhereString( (String)map.get("baitankavl_to")),",","") +" ");
			}
		}

		//商品区分
		if (map.get("syouhin_kb") != null && ((String)map.get("syouhin_kb")).trim().length() > 0) {
			strWhere.append(" and TBL1.FUJI_SYOHIN_KB = '" + conv.convertWhereString((String)map.get("syouhin_kb")).substring(conv.convertWhereString((String)map.get("syouhin_kb")).length()-1) + "' ");
		}

		//byNo.
		if (map.get("byno_cd") != null && ((String)map.get("byno_cd")).trim().length() > 0) {
			strWhere.append(" and TBL1.syokai_user_id = '" + conv.convertWhereString(mst000401_LogicBean.formatZero((String)map.get("byno_cd"),10)) + "' ");
		}

		// delete_fg に対するWHERE区
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			strWhere.append(" and TBL1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		}

		//選択業種が生鮮の場合
		//if (map.get("selected_gyousyu_cd").equals("04")) {
		//	sb.append(" and srk.kanri_kb = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "'");
			// 2005.08.18 インデックスを使わせないため(|| '')を入れました
		//	sb.append(" and srk.kanri_cd || '' = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "'");

		//選択業種が生鮮以外の場合
		//} else {
		//	sb.append(" and srk.kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "'");
		//	sb.append(" and srk.kanri_cd = TBL1.hanku_cd ");

		//}
		//sb.append(" and srk.siiresaki_cd = TBL1.siiresaki_cd ");
		//sb.append(" and srk.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		//sb.append(" and srk.tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");

//		sb.append(" and TBL1.hanku_cd 		in  ( SELECT TBL00.hanku_cd "
//											+ "   			From  R_SYOHIN_TAIKEI TBL00, "
//											+ "				      SYS_SOSASYA TBL01 "
//											+ "			    WHERE (TBL00.uriku_cd = TBL01.uriku1_cd "
//											+ "					or TBL00.uriku_cd = TBL01.uriku2_cd "
//											+ "					or TBL00.uriku_cd = TBL01.uriku3_cd) "
//											+ "				   and TBL01.user_id = '" +  conv.convertWhereString( (String)map.get("user_id") ) + "'"
//											+ "                and TBL01.hojin_cd ='" +  mst000101_ConstDictionary.HOJIN_CD + "'"
//											+ " ) ");

		//sb.append(" and TBL1.hanku_cd 		in  ( SELECT DISTINCT TBL00.hanku_cd ");
		//sb.append("   			                    FROM R_SYOHIN_TAIKEI TBL00 ");

		// 小業種コードが入っている場合
		//if( map.get("s_gyosyu_cd") != null && ((String)map.get("s_gyosyu_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL00.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "'");
		//	whereStr = andStr;

		//}else{

			//if(map.get("selected_gyousyu_cd").equals("01")){

				// 選択業種が01(衣料)
				//sb.append(whereStr);
//				↓↓仕様変更（2005.10.12）↓↓
//				sb.append("TBL00.D_GYOSYU_CD = '0011'");
				//sb.append("TBL00.D_GYOSYU_CD IN ('0011', '0044', '0061') ");
//				↑↑仕様変更（2005.10.12）↑↑
				//whereStr = andStr;

			//}else if(map.get("selected_gyousyu_cd").equals("02")){

				// 選択業種が02(住生活)
				//sb.append(whereStr);
//				↓↓仕様変更（2005.10.12）↓↓
//				sb.append("TBL00.D_GYOSYU_CD NOT IN('0011', '0033', '0044')");
				//sb.append("TBL00.D_GYOSYU_CD NOT IN('0011', '0033', '0044', '0061') ");
//				↑↑仕様変更（2005.10.12）↑↑
				//whereStr = andStr;

			//}else if(map.get("selected_gyousyu_cd").equals("03")){

				// 選択業種が03(加工食品)
				//sb.append(whereStr);
				//sb.append("    TBL00.D_GYOSYU_CD = '0033'");
//				↓↓仕様変更（2005.10.12）↓↓
//				sb.append("AND TBL00.S_GYOSYU_CD = '0087'");
				//sb.append("AND TBL00.S_GYOSYU_CD IN ('0087', '0088')");
//				↑↑仕様変更（2005.10.12）↑↑
				//whereStr = andStr;

			///}else if(map.get("selected_gyousyu_cd").equals("04")){

				// 選択業種が04(生鮮)
				//sb.append(whereStr);
				//sb.append("    TBL00.D_GYOSYU_CD = '0033'");
//				↓↓仕様変更（2005.10.12）↓↓
//				sb.append("AND TBL00.S_GYOSYU_CD <> '0087'");
				//sb.append("AND TBL00.S_GYOSYU_CD NOT IN ('0087', '0088')");
//				↑↑仕様変更（2005.10.12）↑↑
				//whereStr = andStr;

			//}
		//}

//		sb.append(" AND TBL1.hinsyu_cd = TBL00.hinsyu_cd ");

		// hanku_cd に対するWHERE区
		//if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL00.hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
		//	whereStr = andStr;
		//}

		//sb.append(" ) ");

////////////////////////////////////////

		//whereStr = andStr;

		// yuko_dt に対するWHERE区
		//if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
		//	whereStr = andStr;
		//}

		// hanku_cd に対するWHERE区
		//if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
			//whereStr = andStr;
		//}


		// syohin_cd に対するWHERE区
		//if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
		//	whereStr = andStr;
		//}

		// hinsyu_cd に対するWHERE区
		//if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.hinsyu_cd = '" + conv.convertWhereString( (String)map.get("hinsyu_cd") ) + "'");
		//	whereStr = andStr;
		//}

		// syohin_2_cd に対するWHERE区
		//if( map.get("syohin_2_cd") != null && ((String)map.get("syohin_2_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.syohin_2_cd = '" + conv.convertWhereString( (String)map.get("syohin_2_cd") ) + "'");
		//	whereStr = andStr;
		//}

		// siiresaki_cd に対するWHERE区
		//if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.siiresaki_cd = '" + conv.convertWhereString( (String)map.get("siiresaki_cd") ) + "'");
		//	whereStr = andStr;
		//}

		// ten_groupno_cd に対するWHERE区
		//if( map.get("ten_groupno_cd") != null && ((String)map.get("ten_groupno_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.ten_groupno_cd = '" + conv.convertWhereString( (String)map.get("ten_groupno_cd") ) + "'");
		//	whereStr = andStr;
		//}

		// season_cd に対するWHERE区
		//if( map.get("season_cd") != null && ((String)map.get("season_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.season_cd = '" + conv.convertWhereString( (String)map.get("season_cd") ) + "'");
		//	whereStr = andStr;
		//}

		// insert_ts に対するWHERE区
		//if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
		//	whereStr = andStr;
		//}

		// insert_user_id に対するWHERE区
		//if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
		//	whereStr = andStr;
		//}

		// update_ts に対するWHERE区
		//if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
		//	whereStr = andStr;
		//}

		// update_user_id に対するWHERE区
		//if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
		//	whereStr = andStr;
		//}

		// delete_fg に対するWHERE区
		//if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("TBL1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		//	whereStr = andStr;
		//}

		//hanbai_ed_dt に対するWHERE区
		//if( map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0 ){
		//	if( map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0 ){
		//		sb.append(whereStr);
		//		sb.append("     TBL1.hanbai_ed_dt >= '" + conv.convertWhereString( (String)map.get("hanbai_st_dt") ) + "'");
		//		sb.append(" and TBL1.hanbai_ed_dt <= '" + conv.convertWhereString( (String)map.get("hanbai_ed_dt") ) + "'");
		//		whereStr = andStr;
		//	} else {
		//		sb.append(whereStr);
		//		sb.append("     TBL1.hanbai_ed_dt >= '" + conv.convertWhereString( (String)map.get("hanbai_st_dt") ) + "'");
		//		whereStr = andStr;
		//	}
		//}

		//jan_mark_cd に対するWHERE区
		//if( map.get("jan_mark_cd") != null && ((String)map.get("jan_mark_cd")).trim().length() > 0 )
		//{
		//	sb.append(whereStr);
		//	sb.append("    (TBL1.syohin_cd like '" + conv.convertWhereString( (String)map.get("jan_mark_cd") ) + "%'" );
		//	sb.append(" or TBL1.syohin_2_cd like '" + conv.convertWhereString( (String)map.get("jan_mark_cd") ) + "%' )" );
		//	whereStr = andStr;
		//}

		StringBuffer sql = new StringBuffer();
		sql.append(sb);
		sql.append(strWhere);
// ↓↓2006.08.03 HuangJiugen カスタマイズ修正↓↓
//		sql.append(" and TBL1.YUKO_DT = MSP710101_GETSYOHINYUKODT(TBL1.BUMON_CD, TBL1.SYOHIN_CD,'') ");
		// ===BEGIN=== Modify by kou 2006/10/29
		//sql.append(" and TBL1.YUKO_DT = MSP710101_GETSYOHINYUKODT(TBL1.BUMON_CD, TBL1.SYOHIN_CD, cast(null as char)) ");
		sql.append(" and TBL1.YUKO_DT = (SELECT MAX(YUKO_DT) FROM R_SYOHIN RS WHERE RS.BUMON_CD = TBL1.BUMON_CD AND RS.SYOHIN_CD = TBL1.SYOHIN_CD) ");
		// ===END=== Modify by kou 2006/10/29
// ↑↑2006.08.03 HuangJiugen カスタマイズ修正↑↑
//		sql.append(" order by ");
//		sql.append(" bumon_cd");
//		sql.append(" ,");
//		sql.append(" syohin_cd");

		//MOD by Tanigawa 2006/9/18 特定の画面だけ20:00～23:00間も動作する様に修正 START
		sql.append(" order by ");
		sql.append(" siire_hinban_cd ASC");
		sql.append(" ,");
		sql.append(" syohin_cd ASC");

		//MOD by Tanigawa 2006/9/18 特定の画面だけ20:00～23:00間も動作する様に修正  END

//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sql.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		return sql.toString();

	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst )
	{
//		↓↓2006.07.20 lixy カスタマイズ修正↓↓
//		boolean befKanma = false;
//		boolean aftKanma = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//		mst160101_SyohinIkkatuMenteBean bean = (mst160101_SyohinIkkatuMenteBean)beanMst;
//		↑↑2006.07.20 lixy カスタマイズ修正↑↑
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
//		↓↓2006.07.20 lixy カスタマイズ修正↓↓
//		boolean befKanma = false;
//		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//		mst160101_SyohinIkkatuMenteBean bean = (mst160101_SyohinIkkatuMenteBean)beanMst;
//		↑↑2006.07.20 lixy カスタマイズ修正↑↑
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst160101_SyohinIkkatuMenteBean bean = (mst160101_SyohinIkkatuMenteBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_SYOHIN where ");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		sb.append(" AND");
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
		//sb.append(" hanku_cd = ");
		//sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
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
