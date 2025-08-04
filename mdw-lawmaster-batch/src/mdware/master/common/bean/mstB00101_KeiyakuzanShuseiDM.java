/**
 * <p>タイトル: 新ＭＤシステム 契約残確認・修正画面のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する契約残確認・修正画面のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sunpt
 * @version 1.0(2006/07/12)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;
//↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑

/**
 * <p>タイトル: 新ＭＤシステム契約残確認・修正画面のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する契約残確認・修正画面のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mstB00101_KeiyakuzanShuseiDM extends DataModule
{
	//private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

	private ArrayList tenpoItiran = null; //店舗配列
	private ArrayList syohinItiran = null; //商品コード

	private String bumon_cd = null; //部門コード
	private String syohin_cd = null; //商品コード

	/**
	 * コンストラクタ
	 */
	public mstB00101_KeiyakuzanShuseiDM()
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
	protected Object instanceBean(ResultSet rest) throws SQLException
	{
		mstB00101_KeiyakuzanShuseiBean bean = new mstB00101_KeiyakuzanShuseiBean();
		//mstB00301_KeepMeisaiBean bean = new mstB00301_KeepMeisaiBean();
		//契約数管理マスタのカラム
		bean.setBumonCd(trim(rest.getString("bumon_cd"))); //部門コード
		bean.setSyohinCd(trim(rest.getString("syohin_cd"))); //商品コード
		bean.setSyokaikeiyaku_qt(trim(rest.getString("syokai_keiyaku_qt"))); //初回契約数量
		bean.setRuikeikeiyaku_qt(trim(rest.getString("ruikei_keiyaku_qt"))); //累計契約数量
		bean.setRuikeihachu_qt(trim(rest.getString("ruikei_hachu_qt"))); //累計発注数量
		bean.setZensyuhachu_qt(trim(rest.getString("zensyu_hachu_qt"))); //前週発注数量
		bean.setRuikeihanbai_qt(trim(rest.getString("ruikei_hanbai_qt"))); //累計販売数量
		bean.setZensyuhanbai_qt(trim(rest.getString("zensyu_hanbai_qt"))); //前週販売数量
		bean.setRuikeituikeiyaku_qt(trim(rest.getString("ruikei_tuikeiyaku_qt"))); //累計追加契約数量
		bean.setKonkaituikeiyaku_qt(trim(rest.getString("konkai_tuikeiyaku_qt"))); //今回追加契約数量
		bean.setZenkaituikeiyaku_qt(trim(rest.getString("zenkai_tuikeiyaku_qt"))); //前回追加契約数量
		bean.setMino_qt(trim(rest.getString("mino_qt"))); //未納数量
		bean.setTino_qt(trim(rest.getString("tino_qt"))); //遅納数量
		bean.setToriatsukaiten_qt(trim(rest.getString("toriatsukai_ten_qt"))); //取扱店舗数
		bean.setSyokaritu_rt(trim(rest.getString("syokaritu_rt"))); //消化率
		bean.setSyokayotei_nb(trim(rest.getString("syoka_yotei_nb"))); //消化予定日数
		bean.setDelete_flg(trim(rest.getString("delete_fg"))); //削除フラグ
		bean.setInsert_ts(trim(rest.getString("insert_ts"))); //作成年月日
		bean.setInsert_userid(trim(rest.getString("insert_user_id"))); //作成者ID
		bean.setUpdate_Ts(trim(rest.getString("update_ts"))); //更新年月日
		bean.setUpdate_userid(trim(rest.getString("update_user_id"))); //更新者ID

		//商品マスタのカラム
		bean.setYuko_dt(trim(rest.getString("yuko_dt"))); //有効日
		bean.setSiirehinban_cd(trim(rest.getString("siire_hinban_cd"))); //仕入先商品コード
		bean.setSiiresaki_cd(trim(rest.getString("siiresaki_cd"))); //仕入先コード
		bean.setGentanka_vl(trim(rest.getString("gentanka_vl"))); //原価単価
		bean.setBaitanka_vl(trim(rest.getString("baitanka_vl"))); //売価単価
		bean.setSyohin_nm(trim(rest.getString("hinmei_kanji_na"))); //漢字品名
		bean.setHanbai_start_dt(trim(rest.getString("hanbai_st_dt"))); //販売開始日
		bean.setHanbai_end_dt(trim(rest.getString("hanbai_ed_dt"))); //販売終了日
		bean.setSaveHanbai_end_dt(trim(rest.getString("hanbai_ed_dt")));
//		↓↓2006.12.15 H.Yamamoto カスタマイズ修正↓↓
		bean.setSystemKb(trim(rest.getString("system_kb"))); //システム区分
//		↑↑2006.12.15 H.Yamamoto カスタマイズ修正↑↑
		//販売終了日を退避 by kema 06.09.19
		bean.setHachu_start_dt(trim(rest.getString("ten_hachu_st_dt"))); //発注開始日
		bean.setHachu_end_dt(trim(rest.getString("ten_hachu_ed_dt"))); //発注終了日
		bean.setSaveHachu_end_dt(trim(rest.getString("ten_hachu_ed_dt")));
		//発注終了日を退避 by kema 06.09.19
		bean.setSize_cd(trim(rest.getString("size_cd"))); //サイズコード
		bean.setColor_cd(trim(rest.getString("color_cd"))); //カラーコード
//		↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
		bean.setNefudaUkewatasiKb(trim(rest.getString("nefuda_ukewatasi_kb")));
		bean.setSaveNefudaUkewatasiKb(trim(rest.getString("nefuda_ukewatasi_kb")));
		bean.setNefudaUkewatasiNm(trim(rest.getString("nefuda_ukewatasi_nm")));
//		↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑
		//
		bean.setSiiresaki_nm(trim(rest.getString("siiresaki_nm")));
		bean.setSize_nm(trim(rest.getString("size_nm")));
		bean.setColor_nm(trim(rest.getString("color_nm")));
		bean.setInsertTs(trim(rest.getString("insertTs"))); //作成年月日
		bean.setUpdateTs(trim(rest.getString("updateTs")));

		bean.setOriginalHachuEndDt(bean.getHachu_end_dt());
		bean.setOriginalHanbaiEndDt(bean.getHanbai_end_dt());

		bean.setCreateDatabase();
		return bean;
	}

	private String trim(String s)
	{
		if (s == null)
		{
			return "";
		}
		return s.trim();
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql(Map map)
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		//検索用ＳＱＬ
		StringBuffer sb = new StringBuffer();
		//SELECT 契約数管理M.全項目,  商品M.必要項目
		//契約数管理マスタのカラム
		sb.append(" SELECT ");
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" B.bumon_cd, ");
		sb.append(" A.bumon_cd, ");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" B.syohin_cd, ");
		sb.append(" syokai_keiyaku_qt, ");
		sb.append(" ruikei_keiyaku_qt, ");
		sb.append(" ruikei_hachu_qt, ");
		sb.append(" zensyu_hachu_qt, ");
		sb.append(" ruikei_hanbai_qt, ");
		sb.append(" zensyu_hanbai_qt, ");
		sb.append(" ruikei_tuikeiyaku_qt, ");
		sb.append(" konkai_tuikeiyaku_qt, ");
		sb.append(" zenkai_tuikeiyaku_qt, ");
		sb.append(" mino_qt, ");
		sb.append(" tino_qt, ");
		sb.append(" toriatsukai_ten_qt, ");
		sb.append(" syokaritu_rt, ");
		sb.append(" syoka_yotei_nb, ");
		sb.append(" B.delete_fg, ");
		sb.append(" B.insert_ts, ");
		sb.append(" B.insert_user_id, ");
		sb.append(" B.update_ts, ");
		sb.append(" B.update_user_id, ");
		//商品マスタのカラム
		sb.append(" A.yuko_dt, ");
		sb.append(" siire_hinban_cd, ");
		sb.append(" A.siiresaki_cd, ");
		sb.append(" gentanka_vl, ");
		sb.append(" baitanka_vl, ");
		sb.append(" hinmei_kanji_na, ");
		sb.append(" hanbai_st_dt, ");
		sb.append(" hanbai_ed_dt, ");
		sb.append(" ten_hachu_st_dt, ");
		sb.append(" ten_hachu_ed_dt, ");
		sb.append(" A.insert_ts as insertTs, ");
		sb.append(" A.update_ts as updateTs, ");
		sb.append(" A.size_cd as size_cd, ");
		sb.append(" A.color_cd as color_cd, ");
//		↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" coalesce(A.nefuda_ukewatasi_kb,'') as nefuda_ukewatasi_kb, ");
		sb.append(" coalesce(F.Kanji_rn,'') as nefuda_ukewatasi_nm, ");
//		↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.12.15 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" A.system_kb as system_kb, ");
//		↑↑2006.12.15 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" C.Kanji_rn as siiresaki_nm, ");
//		↓↓2007.01.17 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" D.Kanji_rn as size_nm, ");
//		sb.append(" E.Kanji_rn as color_nm ");
		if (map.get("systemKb") != null && map.get("systemKb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			//タグ衣料の場合は、名称・CTFマスタよりカラー名称、サイズ名称を取得
			sb.append(" D.Kanji_rn as size_nm, ");
			sb.append(" E.Kanji_rn as color_nm ");
		}else if ( map.get("systemKb") != null && map.get("systemKb").equals("J") ){
			//実用衣料の場合は、商品マスタよりカラー名称、サイズ名称を取得
			sb.append(" A.kikaku_kanji_2_na as size_nm, ");
			sb.append(" A.kikaku_kanji_na as color_nm ");
		}else {
			//衣料以外
			sb.append("	'' as size_nm, ");
			sb.append("	'' as color_nm ");
		}
//		↑↑2007.01.17 H.Yamamoto カスタマイズ修正↑↑

		//FROM 商品M   INNER JOIN  契約数管理M  ON  商品M.部門コード= 契約数管理M.部門コード
		//AND 商品M.商品コード= 契約管理M.商品コード
		sb.append(" FROM ");
		sb.append(" 	R_SYOHIN A");
		sb.append(" INNER JOIN ");
		sb.append(" 	R_KEIYAKUSU B");
		sb.append(" ON ");
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" 	A.bumon_cd = B.bumon_cd AND ");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" 	A.syohin_cd = B.syohin_cd ");
		sb.append(" LEFT OUTER JOIN ");
		sb.append(" 	R_SIIRESAKI C ");
		sb.append(" ON ");
//		↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" 	A.siiresaki_cd =C.siiresaki_cd AND ");
//		sb.append(" 	'0000' = C.KANRI_CD AND ");
//		sb.append(" 	C.KANRI_KB = '1' ");
		sb.append(" 	C.KANRI_KB = '1' AND ");
		sb.append(" 	C.KANRI_CD = '0000' AND ");
		sb.append(" 	C.siiresaki_cd = ");
		sb.append(" ( ");
		sb.append(" SELECT ");
		sb.append(" 	MAX(CC.siiresaki_cd) ");
		sb.append(" FROM ");
		sb.append(" 	R_SIIRESAKI CC ");
		sb.append(" WHERE ");
		sb.append(" 	CC.KANRI_KB = '1' AND ");
		sb.append(" 	CC.KANRI_CD = '0000' AND ");
		sb.append(" 	(CC.siiresaki_cd = A.siiresaki_cd OR ");
		sb.append(" 	 SUBSTR(CC.siiresaki_cd,1,6) = A.siiresaki_cd) AND");
		sb.append(" 	case a.system_kb ")
		  .append("       when 'T' then case when CC.siire_system_kb in ('2','3') THEN CC.delete_fg else '1' end ")
		  .append("                else case when CC.siire_system_kb in ('1','3') THEN CC.delete_fg else '1' end ")
		  .append("     end = '0' AND ");
		sb.append(" 	CC.tosan_kb = '"+ mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' ");
		sb.append(" ) ");
//		↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" LEFT OUTER JOIN ");
		sb.append(" 	R_NAMECTF D ");
		sb.append(" ON ");
		sb.append(" 	A.size_cd= D.CODE_CD AND ");
		sb.append(" 	D.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal) + "' ");
		sb.append(" LEFT JOIN ");
		sb.append(" 	R_NAMECTF E ");
		sb.append(" ON ");
		sb.append(" 	A.color_cd = E.CODE_CD AND ");
//		↓↓2007.01.17 H.Yamamoto カスタマイズ修正↓↓
//		//実用は異なる種別を使用
//		sb.append(
//			" E.SYUBETU_NO_CD = (case WHEN A.system_kb = 'T' THEN '"
//				+ mst000101_ConstDictionary.COLOR
//				+ "' ");
//		sb.append("     else '" + mst000101_ConstDictionary.COLOR2 + "' end )");
		// タグ衣料の場合のみ、名称・CTFマスタよりカラー名称を取得
		sb.append(" 	E.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal) + "' ");
//		↑↑2007.01.17 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" LEFT OUTER JOIN ");
		sb.append(" 	R_NAMECTF F ");
		sb.append(" ON ");
		sb.append(" 	F.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.RECEIVE_OF_TAG, userLocal) + "' ");
		sb.append(" AND ");
		sb.append(" 	F.CODE_CD = A.system_kb || A.nefuda_ukewatasi_kb ");
//		↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑

		//WHERE 商品M.有効日 <= マスタ管理日付
		//AND  商品M.部門コード=入力部門

		sb.append(" WHERE ");
		sb.append(" A.yuko_dt = (");
		sb.append("     select max(R.yuko_dt) from R_SYOHIN R ");
		sb.append("      where R.yuko_dt <= '" + map.get("yuko_dt") + "' ");
		sb.append("        and A.bumon_cd = R.bumon_cd ");
		sb.append("        and A.syohin_cd = R.syohin_cd ) ");
		sb.append(" AND A.bumon_cd = '" + map.get("bumon_cd") + "' ");
		//品番
		sb.append(" AND A.hinban_cd = '" + map.get("hinban_cd") + "' ");
//		↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
		//システム区分
		sb.append(" AND A.system_kb = '" + map.get("systemKb") + "' ");
//		↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑

		//仕入先指定ありのとき
		if (map.get("siiresaki_cd") != null
			&& map.get("siiresaki_cd").toString().trim().length() != 0)
		{
			sb.append("  AND   SUBSTRING(A.siiresaki_cd,1,6) = '" + map.get("siiresaki_cd") + "' ");
		}
		//仕入先商品コード指定ありのとき
		//AND  商品M.仕入先品番 = 入力仕入先商品
		//		↓↓xubq 2006.08.30 カスタマイズ修正↓↓
		//		if(map.get("siiresaki_syohin_cd")!=null && map.get("siiresaki_syohin_cd").toString().trim().length()!=0){
		//			sb.append(" AND    rtrim(A.SIIRE_HINBAN_CD) = '"+ map.get("siiresaki_syohin_cd") +"' ");
		//		}
		if (map.get("siiresaki_syohin_cd") != null
			&& map.get("siiresaki_syohin_cd").toString().trim().length() != 0)
		{
			//===BEGIN=== Modify by kou 2006/11/14
			//sb.append(
			//	" AND    rtrim(A.SIIRE_HINBAN_CD) like '" + map.get("siiresaki_syohin_cd") + "%' ");
			if("1".equals(map.get("sscdFlg")))
			{
				sb.append(" AND    rtrim(A.SIIRE_HINBAN_CD) like '")
					.append(map.get("siiresaki_syohin_cd")).append("%' ");
			}
			else
			{
				sb.append(" AND    A.SIIRE_HINBAN_CD = '")
					.append(map.get("siiresaki_syohin_cd")).append("' ");
			}
			//===END=== Modify by kou 2006/11/14
		}
		//		↑↑xubq 2006.08.30 カスタマイズ修正↑↑
		//棚番指定ありのとき
		//AND  商品M.棚No BETWEEN  入力棚From  AND  入力棚To
		if (map.get("tanawari_bangou_fm") != null
			&& map.get("tanawari_bangou_fm").toString().trim().length() != 0)
		{
			sb.append("  AND   A.tana_no_nb >= " + map.get("tanawari_bangou_fm") + " ");
		}
		if (map.get("tanawari_bangou_to") != null
			&& map.get("tanawari_bangou_to").toString().trim().length() != 0)
		{
			sb.append(" AND   A.tana_no_nb <= " + map.get("tanawari_bangou_to") + " ");
		}

		//===BEGIN=== Add by kou 2006/11/08
		if("0".equals(map.get("percentFg"))) {
		//===END=== Add by kou 2006/11/09
			//消化率指定ありのとき
			//AND  契約数管理M.消化率  BETWEEN  入力From  AND  入力To
			if (map.get("shoukaritu_min") != null
				&& map.get("shoukaritu_min").toString().trim().length() != 0)
			{
				sb.append("	AND   B.syokaritu_rt >= " + map.get("shoukaritu_min") + " ");
			}
			if (map.get("shoukaritu_max") != null
				&& map.get("shoukaritu_max").toString().trim().length() != 0)
			{
				sb.append("	AND   B.syokaritu_rt <= " + map.get("shoukaritu_max") + " ");
			}

		//===BEGIN=== Add by kou 2006/11/09
		} else {
			//残枚数指定ありのとき
			if (map.get("maisuMin") != null
				&& map.get("maisuMin").toString().trim().length() != 0)
			{
				sb.append("	AND   B.ruikei_keiyaku_qt - B.ruikei_hachu_qt >= ").append(map.get("maisuMin")).append(" ");
			}
			if (map.get("maisuMax") != null
				&& map.get("maisuMax").toString().trim().length() != 0)
			{
				sb.append("	AND   B.ruikei_keiyaku_qt - B.ruikei_hachu_qt <= ").append(map.get("maisuMax")).append(" ");
			}
		}
		//===END=== Add by kou 2006/11/09

		//ユニット指定ありのとき
		if (map.get("unit_cd") != null && map.get("unit_cd").toString().trim().length() != 0)
		{
			sb.append("	AND		A.unit_cd = '" + map.get("unit_cd") + "' ");
		}
		else
		{

			//品番、品種、ライン指定ありのとき
			//AND 商品M.ユニット IN (SELECT ユニット FROM 商品体系　WHERE 商品体系.部門=入力 AND 商品体系.品番=入力
			//AND  商品体系.品種コード=入力
			//AND  商品体系.ラインコード=入力)
			if (map.get("hinsyu_cd") != null
				&& map.get("hinsyu_cd").toString().trim().length() != 0
				&& (map.get("line_cd") != null && map.get("line_cd").toString().trim().length() != 0)
				&& (map.get("hinban_cd") != null
					&& map.get("hinban_cd").toString().trim().length() != 0))
			{
				sb.append("	AND		A.unit_cd IN ( ");
				sb.append("	 						SELECT ");
				sb.append(" 								C.unit_cd ");
				sb.append(" 						FROM   ");
				sb.append(" 								R_SYOHIN_TAIKEI C ");
				sb.append(" 						WHERE ");
				sb.append(
					" 								C.bumon_cd = '" + map.get("bumon_cd") + "' ");
				sb.append(
					" 							AND C.hinban_cd = '"
						+ map.get("hinban_cd")
						+ "' ");
				sb.append(
					" 							AND C.hinsyu_cd = '"
						+ map.get("hinsyu_cd")
						+ "' ");
				sb.append(
					" 							AND C.line_cd = '" + map.get("line_cd") + "' )");
			}
			//品番、品種指定ありのとき
			//AND 商品M.ユニット IN (SELECT ユニット FROM 商品体系　WHERE 商品体系.部門=入力 AND 商品体系.品番=入力品番）
			//AND  商品体系.品種=入力品種）
			else if (
				(map.get("hinban_cd") != null
					&& map.get("hinban_cd").toString().trim().length() != 0)
					&& (map.get("hinsyu_cd") != null
						&& map.get("hinsyu_cd").toString().trim().length() != 0))
			{
				sb.append("	AND		A.unit_cd IN ( ");
				sb.append("	 						SELECT ");
				sb.append(" 								C.unit_cd ");
				sb.append(" 						FROM   ");
				sb.append(" 								R_SYOHIN_TAIKEI C ");
				sb.append(" 						WHERE ");
				sb.append(
					" 								C.bumon_cd = '" + map.get("bumon_cd") + "' ");
				sb.append(
					" 							AND C.hinban_cd = '"
						+ map.get("hinban_cd")
						+ "' ");
				sb.append(
					" 							AND C.hinsyu_cd = '"
						+ map.get("hinsyu_cd")
						+ "' )");

			}
			//品番、ライン指定ありのとき
			//AND 商品M.ユニット IN (SELECT ユニット FROM 商品体系　WHERE 商品体系.部門=入力 AND 商品体系.品番=入力品番）
			//AND  商品体系.ライン=入力ライン）
			else if (
				(map.get("hinban_cd") != null
					&& map.get("hinban_cd").toString().trim().length() != 0)
					&& (map.get("line_cd") != null
						&& map.get("line_cd").toString().trim().length() != 0))
			{
				sb.append("	AND		A.unit_cd IN ( ");
				sb.append("	 						SELECT ");
				sb.append(" 								C.unit_cd ");
				sb.append(" 						FROM   ");
				sb.append(" 								R_SYOHIN_TAIKEI C ");
				sb.append(" 						WHERE ");
				sb.append(
					" 								C.bumon_cd = '" + map.get("bumon_cd") + "' ");
				sb.append(
					" 							AND C.hinban_cd = '"
						+ map.get("hinban_cd")
						+ "' ");
				sb.append(
					" 							AND C.line_cd = '" + map.get("line_cd") + "' )");

			}
			//品番のみ入力ありのとき
			//AND 商品M.ユニット IN (SELECT ユニット FROM 商品体系　WHERE 商品体系.部門=入力 AND 商品体系.品番=入力品番）
			else if (
				map.get("hinban_cd") != null
					&& map.get("hinban_cd").toString().trim().length() != 0)
			{
				sb.append(" AND		A.unit_cd IN ( ");
				sb.append("							SELECT  ");
				sb.append("									C.unit_cd  ");
				sb.append("							FROM  ");
				sb.append("									R_SYOHIN_TAIKEI C ");
				sb.append("							WHERE  ");
				sb.append(
					"								  	C.bumon_cd = '"
						+ map.get("bumon_cd")
						+ "' ");
				sb.append(
					"								AND C.hinban_cd = '"
						+ map.get("hinban_cd")
						+ "' )");
			}
		}
		//商品コード指定ありのとき
		if (map.get("syohin_cd") != null && map.get("syohin_cd").toString().trim().length() != 0)
		{
			sb.append(" AND   rtrim(A.syohin_cd) = '" + map.get("syohin_cd") + "' ");
		}
		//サブクラスコード指定ありのとき
		if (map.get("subClass_cd") != null
			&& map.get("subClass_cd").toString().trim().length() != 0)
		{
			sb.append("  AND   A.subclass_cd = '" + map.get("subClass_cd") + "' ");
		}

		// ===BEGIN=== Add by kou 2006/10/17
		//バイヤーNO指定ありのとき
		if (map.get("by_no") != null
			&& map.get("by_no").toString().trim().length() != 0)
		{
			sb.append("  AND   A.syokai_user_id = 0000 || '").append(map.get("by_no")).append("' ");
		}
		//EOS区分指定ありのとき
		if (map.get("eos_kb") != null
			&& map.get("eos_kb").toString().trim().length() != 0)
		{
			sb.append("  AND   A.eos_kb = '").append(map.get("eos_kb")).append("' ");
		}
		// ===END=== Add by kou 2006/10/17
//		↓↓2006.11.27 H.Yamamoto カスタマイズ修正↓↓
		//店舗発注終了日指定ありのとき
		if (map.get("ten_hachu_ed_dt") != null
			&& map.get("ten_hachu_ed_dt").toString().trim().length() != 0)
		{
			sb.append("  AND   A.ten_hachu_ed_dt >= '" + map.get("ten_hachu_ed_dt") + "' ");
		}
//		↑↑2006.11.27 H.Yamamoto カスタマイズ修正↑↑

		sb.append("   AND A.delete_fg = '0' ");
		sb.append("   AND B.delete_fg = '0' ");
		sb.append("   ORDER BY ");
		//アイテム順① 仕入先商品コード② 仕入先コード③商品コード
		if ((mstB00201_KeepBean.SORT_ITEM).equals(map.get("rd_sort")))
		{
			sb.append("     SIIRE_HINBAN_CD, siiresaki_cd, SYOHIN_CD ");
		}
		//消化率(昇順）	①消化率(昇順）②仕入先商品コード③ 仕入先コード④商品コード
		else if ((mstB00201_KeepBean.SORT_SYOKARI_ASC).equals(map.get("rd_sort")))
		{
			// ↓↓2006.08.14 HuangJiugen カスタマイズ修正↓↓
			//			sb.append("     syokaritu_rt, SIIRE_HINBAN_CD, siiresaki_cd, SYOHIN_CD ");
			sb.append(
				"     case when syokaritu_rt is null then 0 else syokaritu_rt end, SIIRE_HINBAN_CD, siiresaki_cd, SYOHIN_CD ");
			// ↓↓2006.08.14 HuangJiugen カスタマイズ修正↓↓
		}
		//消化率(降順）①消化率(降順）②仕入先商品コード③ 仕入先コード④商品コード
		else if ((mstB00201_KeepBean.SORT_SYOKARI_DESC).equals(map.get("rd_sort")))
		{
			// ↓↓2006.08.14 HuangJiugen カスタマイズ修正↓↓
			//			sb.append("     syokaritu_rt DESC, SIIRE_HINBAN_CD, siiresaki_cd, SYOHIN_CD ");
			sb.append(
				"     case when syokaritu_rt is null then 0 else syokaritu_rt end DESC, SIIRE_HINBAN_CD, siiresaki_cd, SYOHIN_CD ");
			// ↑↑2006.08.14 HuangJiugen カスタマイズ修正↑↑
		}
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getInsertSql(Object beanMst)
	{
		return null;
	}
	/**
	 * 更新用ＳＱＬの生成を行う。
	 *  商品マスタ更新用SQL文を作成する
	 */
	public String getUpdateSql(Object beanMst)
	{
		mstB00101_KeiyakuzanShuseiBean bean = (mstB00101_KeiyakuzanShuseiBean) beanMst;
		StringBuffer sql = new StringBuffer();
		sql.append(" update R_SYOHIN ");
		sql.append(" set UPDATE_TS = '").append(bean.getDbSystemDate()).append("'");
		sql.append("    ,UPDATE_USER_ID = '").append(bean.getUpdateUserId()).append("'");
		sql.append("    ,DELETE_FG = '0'");
		sql.append("    ,HENKO_DT = '" + bean.getHenkoDT() + "' ");
		sql.append("    ,HANBAI_ED_DT = '" + bean.getHanbai_end_dt() + "' ");
		sql.append("    ,TEN_HACHU_ED_DT = '" + bean.getHachu_end_dt() + "' ");
//		↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
		sql.append("    ,NEFUDA_UKEWATASI_KB = '" + bean.getNefudaUkewatasiKb() + "' ");
//		↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑
		sql.append(" where BUMON_CD = '").append(bean.getBumonCd()).append("'");
		sql.append("   and rtrim(SYOHIN_CD) = '").append(bean.getSyohinCd()).append("'");
		sql.append("   and YUKO_DT = '" + bean.getYuko_dt() + "'");
		return sql.toString();
	}

	/**
	 * 商品マスタに追加するSQLの取得
	 * @param beanMst 商品追加の条件
	 * @param 有効日:追加する有効日
	 * @return
	 */
	public String getInsertSyohinSql(Object beanMst, String MstDt1)
	{

		mstB00101_KeiyakuzanShuseiBean bean = (mstB00101_KeiyakuzanShuseiBean) beanMst;
		StringBuffer strSql = new StringBuffer();

		strSql.append("insert into");
		strSql.append("  r_syohin (");
		//部門コード
		strSql.append("  bumon_cd,");
		//商品コード
		strSql.append("  syohin_cd,");
		//有効日
		strSql.append("  yuko_dt,");
		//発注商品コード区分
		strSql.append("  hacyu_syohin_kb,");
		//システム区分
		strSql.append("  system_kb,");
		//業種区分
		strSql.append("  gyosyu_kb,");
		//ユニットコード
		strSql.append("  unit_cd,");
		//品目
		strSql.append("  hinmoku_cd,");
		//マークグループ
		strSql.append("  mark_group_cd,");
		//マークコード
		strSql.append("  mark_cd,");
		//商品コード２
		strSql.append("  syohin_2_cd,");
		//桁数区分
		strSql.append("  ketasu_kb,");
		//ＪＡＮメーカーコード
		strSql.append("  maker_cd,");
		//漢字品名
		strSql.append("  hinmei_kanji_na,");
		//漢字規格
		strSql.append("  kikaku_kanji_na,");
		//カナ品名
		strSql.append("  hinmei_kana_na,");
		//カナ規格
		strSql.append("  kikaku_kana_na,");
		//商品サイズ(幅)
		strSql.append("  syohin_width_qt,");
		//商品サイズ(高さ)
		strSql.append("  syohin_height_qt,");
		//商品サイズ(奥行き)
		strSql.append("  syohin_depth_qt,");
		//仕入先品番
		strSql.append("  siire_hinban_cd,");
		//ブランドコード
		strSql.append("  bland_cd,");
		//輸入品区分
		strSql.append("  yunyuhin_kb,");
		//原産国/産地コード
		strSql.append("  santi_cd,");
		//メーカー希望小売り価格(税込み)
		strSql.append("  maker_kibo_kakaku_vl,");
		//納品温度帯
		strSql.append("  nohin_ondo_kb,");
		//コメント
		strSql.append("  comment_tx,");
		//店舗発注開始日
		strSql.append("  ten_hachu_st_dt,");
		//店舗発注終了日
		strSql.append("  ten_hachu_ed_dt,");
		//原価単価
		strSql.append("  gentanka_vl,");
		//売価単価(税込)
		strSql.append("  baitanka_vl,");
		//発注単位(入数)
		strSql.append("  hachutani_irisu_qt,");
		//最大発注単位
		strSql.append("  max_hachutani_qt,");
		//定貫区分
		strSql.append("  teikan_kb,");
		//EOS区分
		strSql.append("  eos_kb,");
		//納品期限
		strSql.append("  nohin_kigen_qt,");
		//納品期限区分
		strSql.append("  nohin_kigen_kb,");
		//マスタ使用不可区分
		strSql.append("  mst_siyofuka_kb,");
		//発注停止区分
		strSql.append("  hachu_teisi_kb,");
		//仕入先コード
		strSql.append("  siiresaki_cd,");
		//代表配送先コード
		strSql.append("  daihyo_haiso_cd,");
		//店別仕入先管理コード
		strSql.append("  ten_siiresaki_kanri_cd,");
		//相場商品区分
		strSql.append("  soba_syohin_kb,");
		//①便区分
		strSql.append("  bin_1_kb,");
		//①発注パターン区分
		strSql.append("  hachu_pattern_1_kb,");
		//①締め時間
		strSql.append("  sime_time_1_qt,");
		//①センタ納品リードタイム
		strSql.append("  c_nohin_rtime_1_kb,");
		//①店納品リードタイム
		strSql.append("  ten_nohin_rtime_1_kb,");
		//①店納品時間帯
		strSql.append("  ten_nohin_time_1_kb,");
		//②便区分
		strSql.append("  bin_2_kb,");
		//②発注パターン区分
		strSql.append("  hachu_pattern_2_kb,");
		//②締め時間
		strSql.append("  sime_time_2_qt,");
		//②センタ納品リードタイム
		strSql.append("  c_nohin_rtime_2_kb,");
		//②店納品リードタイム
		strSql.append("  ten_nohin_rtime_2_kb,");
		//②店納品時間帯
		strSql.append("  ten_nohin_time_2_kb,");
		//③便区分
		strSql.append("  bin_3_kb,");
		//③発注パターン区分
		strSql.append("  hachu_pattern_3_kb,");
		//③締め時間
		strSql.append("  sime_time_3_qt,");
		//③センタ納品リードタイム
		strSql.append("  c_nohin_rtime_3_kb,");
		//③店納品リードタイム
		strSql.append("  ten_nohin_rtime_3_kb,");
		//③店納品時間帯
		strSql.append("  ten_nohin_time_3_kb,");
		//センタ納品リードタイム
		strSql.append("  c_nohin_rtime_kb,");
		//税区分
		strSql.append("  zei_kb,");
		//販売期間区分
		strSql.append("  hanbai_kikan_kb,");
		//商品区分
		strSql.append("  syohin_kb,");
		//物流区分
		strSql.append("  buturyu_kb,");
		//横もち区分
		strSql.append("  yokomoti_kb,");
		//店グルーピングNO(物流）
		strSql.append("  ten_groupno_cd,");
		//店在庫区分
		strSql.append("  ten_zaiko_kb,");
		//販売政策区分
		strSql.append("  hanbai_seisaku_kb,");
		//返品契約書NO
		strSql.append("  henpin_nb,");
		//返品原価
		strSql.append("  henpin_genka_vl,");
		//リベート対象フラグ
		strSql.append("  rebate_fg,");
		//販売開始日
		strSql.append("  hanbai_st_dt,");
		//販売終了日
		strSql.append("  hanbai_ed_dt,");
		//販売期限
		strSql.append("  hanbai_limit_qt,");
		//販売期限区分
		strSql.append("  hanbai_limit_kb,");
		//自動削除対象区分
		strSql.append("  auto_del_kb,");
		//GOT無条件表示対象
		strSql.append("  got_mujyoken_fg,");
		//GOT表示開始月
		strSql.append("  got_start_mm,");
		//GOT表示終了月
		strSql.append("  got_end_mm,");
		//Eショップ区分
		strSql.append("  e_shop_kb,");
		//PLU送信日
		strSql.append("  plu_send_dt,");
		//POSレシート品名(漢字)
		strSql.append("  rec_hinmei_kanji_na,");
		//POSレシート品名(カナ)
		strSql.append("  rec_hinmei_kana_na,");
		//キーPLU対象
		strSql.append("  keyplu_fg,");
		//PC発行区分
		strSql.append("  pc_kb,");
		//台紙NO
		strSql.append("  daisi_no_nb,");
		//ユニットプライス-単位区分
		strSql.append("  unit_price_tani_kb,");
		//ユニットプライス-内容量
		strSql.append("  unit_price_naiyoryo_qt,");
		//ユニットプライス-基準単位量
		strSql.append("  unit_price_kijun_tani_qt,");
		//品揃区分
		strSql.append("  shinazoroe_kb,");
		//組数区分
		strSql.append("  kumisu_kb,");
		//値札区分
		strSql.append("  nefuda_kb,");
		//よりどり種類
		strSql.append("  yoridori_kb,");
		//よりどり数量
		strSql.append("  yoridori_qt,");
		//タグ表示売価
		strSql.append("  tag_hyoji_baika_vl,");
		//シーズンコード
		strSql.append("  season_cd,");
		//服種コード
		strSql.append("  hukusyu_cd,");
		//スタイルコード
		strSql.append("  style_cd,");
		//シーンコード
		strSql.append("  scene_cd,");
		//性別コード
		strSql.append("  sex_cd,");
		//年代コード
		strSql.append("  age_cd,");
		//世代コード
		strSql.append("  generation_cd,");
		//素材コード
		strSql.append("  sozai_cd,");
		//パターンコード
		strSql.append("  pattern_cd,");
		//織り編みコード
		strSql.append("  oriami_cd,");
		//付加機能コード
		strSql.append("  huka_kino_cd,");
		//サイズコード
		strSql.append("  size_cd,");
		//カラーコード
		strSql.append("  color_cd,");
		//酒税報告分類
		strSql.append("  syuzei_hokoku_kb,");
		//TC情報
		strSql.append("  tc_jyouho_na,");
		//納品商品コード
		strSql.append("  nohin_syohin_cd,");
		//ITFコード
		strSql.append("  itf_cd,");
		//ケース入り数
		strSql.append("  case_irisu_qt,");
		//入荷時商品コード
		strSql.append("  nyuka_syohin_cd,");
		//外箱サイズ幅
		strSql.append("  pack_width_qt,");
		//外箱サイズ高さ
		strSql.append("  pack_heigth_qt,");
		//外箱サイズ奥行き
		strSql.append("  pack_depth_qt,");
		//外箱重量
		strSql.append("  pack_weight_qt,");
		//センター在庫区分
		strSql.append("  center_zaiko_kb,");
		//在庫補充発注先
		strSql.append("  zaiko_hachu_saki,");
		//在庫センターコード
		strSql.append("  zaiko_center_cd,");
		//最低在庫数(発注点)
		strSql.append("  min_zaikosu_qt,");
		//最大在庫数
		strSql.append("  max_zaikosu_qt,");
		//センター発注単位区分
		strSql.append("  center_hachutani_kb,");
		//センター発注単位数
		strSql.append("  center_hachutani_qt,");
		//センター入り数
		strSql.append("  center_irisu_qt,");
		//センター重量
		strSql.append("  center_weight_qt,");
		//棚NO
		strSql.append("  tana_no_nb,");
		//段NO1
		strSql.append("  dan_no_nb,");
		//契約数
		strSql.append("  keiyaku_su_qt,");
		//契約パターン
		strSql.append("  keiyaku_pattern_kb,");
		//契約開始期間
		strSql.append("  keiyaku_st_dt,");
		//契約終了期間
		strSql.append("  keiyaku_ed_dt,");
		//基準在庫日数
		strSql.append("  kijun_zaikosu_qt,");
		//大属性１
		strSql.append("  d_zokusei_1_na,");
		//小属性１
		strSql.append("  s_zokusei_1_na,");
		//大属性２
		strSql.append("  d_zokusei_2_na,");
		//小属性２
		strSql.append("  s_zokusei_2_na,");
		//大属性３
		strSql.append("  d_zokusei_3_na,");
		//小属性３
		strSql.append("  s_zokusei_3_na,");
		//大属性４
		strSql.append("  d_zokusei_4_na,");
		//小属性４
		strSql.append("  s_zokusei_4_na,");
		//大属性５
		strSql.append("  d_zokusei_5_na,");
		//小属性５
		strSql.append("  s_zokusei_5_na,");
		//大属性６
		strSql.append("  d_zokusei_6_na,");
		//小属性６
		strSql.append("  s_zokusei_6_na,");
		//大属性７
		strSql.append("  d_zokusei_7_na,");
		//小属性７
		strSql.append("  s_zokusei_7_na,");
		//大属性８
		strSql.append("  d_zokusei_8_na,");
		//小属性８
		strSql.append("  s_zokusei_8_na,");
		//大属性９
		strSql.append("  d_zokusei_9_na,");
		//小属性９
		strSql.append("  s_zokusei_9_na,");
		//大属性１０
		strSql.append("  d_zokusei_10_na,");
		//小属性１０
		strSql.append("  s_zokusei_10_na,");
		//変更日
		strSql.append("  henko_dt,");
		//作成年月日
		strSql.append("  insert_ts,");
		//作成者ID
		strSql.append("  insert_user_id,");
		//更新年月日
		strSql.append("  update_ts,");
		//更新者ID
		strSql.append("  update_user_id,");
		//削除フラグ
		strSql.append("  delete_fg,");
		//新規登録日
		strSql.append("  sinki_toroku_dt,");
		//初回登録日
		strSql.append("  syokai_toroku_ts,");
		//初回登録社員ID
		strSql.append("  syokai_user_id,");
		//品番コード
		strSql.append("  hinban_cd,");
		//サブクラスコード
		strSql.append("  subclass_cd,");
		//配布コード
		strSql.append("  haifu_cd,");
		//GTINコード
		strSql.append("  gtin_cd,");
		//漢字規格２
		strSql.append("  kikaku_kanji_2_na,");
		//カナ規格２
		strSql.append("  kikaku_kana_2_na,");
		//PB区分
		strSql.append("  pb_kb,");
		//消札売価
		strSql.append("  keshi_baika_vl,");
		//特別原価
		strSql.append("  tokubetu_genka_vl,");
		//前回原価単価
		strSql.append("  pre_gentanka_vl,");
		//前回売価単価
		strSql.append("  pre_baitanka_vl,");
		//発注単位呼称
		strSql.append("  hachu_tani_na,");
		//センター許容日
		strSql.append("  center_kyoyo_dt,");
		//消費期限
		strSql.append("  syohi_kigen_dt,");
		//値札受渡日
		strSql.append("  nefuda_ukewatasi_dt,");
		//値札受渡方法
		strSql.append("  nefuda_ukewatasi_kb,");
		//よりどり価格
		strSql.append("  yoridori_vl,");
		//商品台帳(店舗)
		strSql.append("  daicho_tenpo_kb,");
		//商品台帳(本部)
		strSql.append("  daicho_honbu_kb,");
		//商品台帳(仕入先)
		strSql.append("  daicho_siiresaki_kb,");
		//袖丈コード
		strSql.append("  sode_cd,");
		//本部在庫区分
		strSql.append("  honbu_zai_kb,");
		//FUJI商品区分
		strSql.append("  fuji_syohin_kb,");
		//発注商品コード
		strSql.append("  hacyu_syohin_cd,");
		//優先便区分
		strSql.append("  yusen_bin_kb,");
		//PLU区分
		strSql.append("  plu_kb,");
		//廃番フラグ
		strSql.append("  haiban_fg )(");
		strSql.append(" select ");
		//部門コード
		strSql.append("  bumon_cd,");
		//商品コード
		strSql.append("  syohin_cd,");
		//有効日
		strSql.append("  '" + MstDt1 + "',");
		//発注商品コード区分
		strSql.append("  hacyu_syohin_kb,");
		//システム区分
		strSql.append("  system_kb,");
		//業種区分
		strSql.append("  gyosyu_kb,");
		//ユニットコード
		strSql.append("  unit_cd,");
		//品目
		strSql.append("  hinmoku_cd,");
		//マークグループ
		strSql.append("  mark_group_cd,");
		//マークコード
		strSql.append("  mark_cd,");
		//商品コード２
		strSql.append("  syohin_2_cd,");
		//桁数区分
		strSql.append("  ketasu_kb,");
		//ＪＡＮメーカーコード
		strSql.append("  maker_cd,");
		//漢字品名
		strSql.append("  hinmei_kanji_na,");
		//漢字規格
		strSql.append("  kikaku_kanji_na,");
		//カナ品名
		strSql.append("  hinmei_kana_na,");
		//カナ規格
		strSql.append("  kikaku_kana_na,");
		//商品サイズ(幅)
		strSql.append("  syohin_width_qt,");
		//商品サイズ(高さ)
		strSql.append("  syohin_height_qt,");
		//商品サイズ(奥行き)
		strSql.append("  syohin_depth_qt,");
		//仕入先品番
		strSql.append("  siire_hinban_cd,");
		//ブランドコード
		strSql.append("  bland_cd,");
		//輸入品区分
		strSql.append("  yunyuhin_kb,");
		//原産国/産地コード
		strSql.append("  santi_cd,");
		//メーカー希望小売り価格(税込み)
		strSql.append("  maker_kibo_kakaku_vl,");
		//納品温度帯
		strSql.append("  nohin_ondo_kb,");
		//コメント
		strSql.append("  comment_tx,");
		//店舗発注開始日
		strSql.append("  ten_hachu_st_dt,");
		//店舗発注終了日
		strSql.append("  '" + bean.getHachu_end_dt() + "', ");
		//原価単価
		strSql.append("  gentanka_vl ,");
		//売価単価(税込)
		strSql.append("  baitanka_vl,");
		//発注単位(入数)
		strSql.append("  hachutani_irisu_qt,");
		//最大発注単位
		strSql.append("  max_hachutani_qt,");
		//定貫区分
		strSql.append("  teikan_kb,");
		//EOS区分
		strSql.append("  eos_kb,");
		//納品期限
		strSql.append("  nohin_kigen_qt,");
		//納品期限区分
		strSql.append("  nohin_kigen_kb,");
		//マスタ使用不可区分
		strSql.append("  mst_siyofuka_kb,");
		//発注停止区分
		strSql.append("  hachu_teisi_kb,");
		//仕入先コード
		strSql.append("  siiresaki_cd,");
		//代表配送先コード
		strSql.append("  daihyo_haiso_cd,");
		//店別仕入先管理コード
		strSql.append("  ten_siiresaki_kanri_cd,");
		//相場商品区分
		strSql.append("  soba_syohin_kb,");
		//①便区分
		strSql.append("  bin_1_kb,");
		//①発注パターン区分
		strSql.append("  hachu_pattern_1_kb,");
		//①締め時間
		strSql.append("  sime_time_1_qt,");
		//①センタ納品リードタイム
		strSql.append("  c_nohin_rtime_1_kb,");
		//①店納品リードタイム
		strSql.append("  ten_nohin_rtime_1_kb,");
		//①店納品時間帯
		strSql.append("  ten_nohin_time_1_kb,");
		//②便区分
		strSql.append("  bin_2_kb,");
		//②発注パターン区分
		strSql.append("  hachu_pattern_2_kb,");
		//②締め時間
		strSql.append("  sime_time_2_qt,");
		//②センタ納品リードタイム
		strSql.append("  c_nohin_rtime_2_kb,");
		//②店納品リードタイム
		strSql.append("  ten_nohin_rtime_2_kb,");
		//②店納品時間帯
		strSql.append("  ten_nohin_time_2_kb,");
		//③便区分
		strSql.append("  bin_3_kb,");
		//③発注パターン区分
		strSql.append("  hachu_pattern_3_kb,");
		//③締め時間
		strSql.append("  sime_time_3_qt,");
		//③センタ納品リードタイム
		strSql.append("  c_nohin_rtime_3_kb,");
		//③店納品リードタイム
		strSql.append("  ten_nohin_rtime_3_kb,");
		//③店納品時間帯
		strSql.append("  ten_nohin_time_3_kb,");
		//センタ納品リードタイム
		strSql.append("  c_nohin_rtime_kb,");
		//税区分
		strSql.append("  zei_kb,");
		//販売期間区分
		strSql.append("  hanbai_kikan_kb,");
		//商品区分
		strSql.append("  syohin_kb,");
		//物流区分
		strSql.append("  buturyu_kb,");
		//横もち区分
		strSql.append("  yokomoti_kb,");
		//店グルーピングNO(物流）
		strSql.append("  ten_groupno_cd,");
		//店在庫区分
		strSql.append("  ten_zaiko_kb,");
		//販売政策区分
		strSql.append("  hanbai_seisaku_kb,");
		//返品契約書NO
		strSql.append("  henpin_nb,");
		//返品原価
		strSql.append("  henpin_genka_vl,");
		//リベート対象フラグ
		strSql.append("  rebate_fg,");
		//販売開始日
		strSql.append("  hanbai_st_dt,");
		//販売終了日
		strSql.append("  '" + bean.getHanbai_end_dt() + "', ");
		//販売期限
		strSql.append("  hanbai_limit_qt,");
		//販売期限区分
		strSql.append("  hanbai_limit_kb,");
		//自動削除対象区分
		strSql.append("  auto_del_kb,");
		//GOT無条件表示対象
		strSql.append("  got_mujyoken_fg,");
		//GOT表示開始月
		strSql.append("  got_start_mm,");
		//GOT表示終了月
		strSql.append("  got_end_mm,");
		//Eショップ区分
		strSql.append("  e_shop_kb,");
		//PLU送信日
		strSql.append("  plu_send_dt,");
		//POSレシート品名(漢字)
		strSql.append("  rec_hinmei_kanji_na,");
		//POSレシート品名(カナ)
		strSql.append("  rec_hinmei_kana_na,");
		//キーPLU対象
		strSql.append("  keyplu_fg,");
		//PC発行区分
		strSql.append("  pc_kb,");
		//台紙NO
		strSql.append("  daisi_no_nb,");
		//ユニットプライス-単位区分
		strSql.append("  unit_price_tani_kb,");
		//ユニットプライス-内容量
		strSql.append("  unit_price_naiyoryo_qt,");
		//ユニットプライス-基準単位量
		strSql.append("  unit_price_kijun_tani_qt,");
		//品揃区分
		strSql.append("  shinazoroe_kb,");
		//組数区分
		strSql.append("  kumisu_kb,");
		//値札区分
		strSql.append("  nefuda_kb,");
		//よりどり種類
		strSql.append("  yoridori_kb,");
		//よりどり数量
		strSql.append("  yoridori_qt,");
		//タグ表示売価
		strSql.append("  tag_hyoji_baika_vl,");
		//シーズンコード
		strSql.append("  season_cd,");
		//服種コード
		strSql.append("  hukusyu_cd,");
		//スタイルコード
		strSql.append("  style_cd,");
		//シーンコード
		strSql.append("  scene_cd,");
		//性別コード
		strSql.append("  sex_cd,");
		//年代コード
		strSql.append("  age_cd,");
		//世代コード
		strSql.append("  generation_cd,");
		//素材コード
		strSql.append("  sozai_cd,");
		//パターンコード
		strSql.append("  pattern_cd,");
		//織り編みコード
		strSql.append("  oriami_cd,");
		//付加機能コード
		strSql.append("  huka_kino_cd,");
		//サイズコード
		strSql.append("  size_cd,");
		//カラーコード
		strSql.append("  color_cd,");
		//酒税報告分類
		strSql.append("  syuzei_hokoku_kb,");
		//TC情報
		strSql.append("  tc_jyouho_na,");
		//納品商品コード
		strSql.append("  nohin_syohin_cd,");
		//ITFコード
		strSql.append("  itf_cd,");
		//ケース入り数
		strSql.append("  case_irisu_qt,");
		//入荷時商品コード
		strSql.append("  nyuka_syohin_cd,");
		//外箱サイズ幅
		strSql.append("  pack_width_qt,");
		//外箱サイズ高さ
		strSql.append("  pack_heigth_qt,");
		//外箱サイズ奥行き
		strSql.append("  pack_depth_qt,");
		//外箱重量
		strSql.append("  pack_weight_qt,");
		//センター在庫区分
		strSql.append("  center_zaiko_kb,");
		//在庫補充発注先
		strSql.append("  zaiko_hachu_saki,");
		//在庫センターコード
		strSql.append("  zaiko_center_cd,");
		//最低在庫数(発注点)
		strSql.append("  min_zaikosu_qt,");
		//最大在庫数
		strSql.append("  max_zaikosu_qt,");
		//センター発注単位区分
		strSql.append("  center_hachutani_kb,");
		//センター発注単位数
		strSql.append("  center_hachutani_qt,");
		//センター入り数
		strSql.append("  center_irisu_qt,");
		//センター重量
		strSql.append("  center_weight_qt,");
		//棚NO
		strSql.append("  tana_no_nb,");
		//段NO1
		strSql.append("  dan_no_nb,");
		//契約数
		strSql.append("  keiyaku_su_qt,");
		//契約パターン
		strSql.append("  keiyaku_pattern_kb,");
		//契約開始期間
		strSql.append("  keiyaku_st_dt,");
		//契約終了期間
		strSql.append("  keiyaku_ed_dt,");
		//基準在庫日数
		strSql.append("  kijun_zaikosu_qt,");
		//大属性１
		strSql.append("  d_zokusei_1_na,");
		//小属性１
		strSql.append("  s_zokusei_1_na,");
		//大属性２
		strSql.append("  d_zokusei_2_na,");
		//小属性２
		strSql.append("  s_zokusei_2_na,");
		//大属性３
		strSql.append("  d_zokusei_3_na,");
		//小属性３
		strSql.append("  s_zokusei_3_na,");
		//大属性４
		strSql.append("  d_zokusei_4_na,");
		//小属性４
		strSql.append("  s_zokusei_4_na,");
		//大属性５
		strSql.append("  d_zokusei_5_na,");
		//小属性５
		strSql.append("  s_zokusei_5_na,");
		//大属性６
		strSql.append("  d_zokusei_6_na,");
		//小属性６
		strSql.append("  s_zokusei_6_na,");
		//大属性７
		strSql.append("  d_zokusei_7_na,");
		//小属性７
		strSql.append("  s_zokusei_7_na,");
		//大属性８
		strSql.append("  d_zokusei_8_na,");
		//小属性８
		strSql.append("  s_zokusei_8_na,");
		//大属性９
		strSql.append("  d_zokusei_9_na,");
		//小属性９
		strSql.append("  s_zokusei_9_na,");
		//大属性１０
		strSql.append("  d_zokusei_10_na,");
		//小属性１０
		strSql.append("  s_zokusei_10_na,");
		//変更日
		strSql.append(" '" + bean.getHenkoDT() + "', ");
		//作成年月日
		strSql.append(" insert_ts,");
		//作成者ID
		strSql.append(" insert_user_id,");
		//更新年月日
		strSql.append(" '").append(bean.getDbSystemDate()).append("', ");
		//更新者ID
		strSql.append(" '").append(bean.getUpdateUserId()).append("', ");
		//削除フラグ
		strSql.append(" '0',");
		//新規登録日
		strSql.append("  sinki_toroku_dt,");
		//初回登録日
		strSql.append("  syokai_toroku_ts,");
		//初回登録社員ID
		strSql.append("  syokai_user_id,");
		//品番コード
		strSql.append("  hinban_cd,");
		//サブクラスコード
		strSql.append("  subclass_cd,");
		//配布コード
		strSql.append("  haifu_cd,");
		//GTINコード
		strSql.append("  gtin_cd,");
		//漢字規格２
		strSql.append("  kikaku_kanji_2_na,");
		//カナ規格２
		strSql.append("  kikaku_kana_2_na,");
		//PB区分
		strSql.append("  pb_kb,");
		//消札売価
		strSql.append("  keshi_baika_vl,");
		//特別原価
		strSql.append("  tokubetu_genka_vl,");
		//前回原価単価
		strSql.append("  pre_gentanka_vl,");
		//前回売価単価
		strSql.append("  pre_baitanka_vl,");
		//発注単位呼称
		strSql.append("  hachu_tani_na,");
		//センター許容日
		strSql.append("  center_kyoyo_dt,");
		//消費期限
		strSql.append("  syohi_kigen_dt,");
		//値札受渡日
		strSql.append("  nefuda_ukewatasi_dt,");
		//値札受渡方法
//		↓↓2006.12.13 H.Yamamoto カスタマイズ修正↓↓
//		strSql.append("  nefuda_ukewatasi_kb,");
		strSql.append(" '").append(bean.getNefudaUkewatasiKb()).append("', ");
//		↑↑2006.12.13 H.Yamamoto カスタマイズ修正↑↑
		//よりどり価格
		strSql.append("  yoridori_vl,");
		//商品台帳(店舗)
		strSql.append("  daicho_tenpo_kb,");
		//商品台帳(本部)
		strSql.append("  daicho_honbu_kb,");
		//商品台帳(仕入先)
		strSql.append("  daicho_siiresaki_kb,");
		//袖丈コード
		strSql.append("  sode_cd,");
		//本部在庫区分
		strSql.append("  honbu_zai_kb,");
		//FUJI商品区分
		strSql.append("  fuji_syohin_kb,");
		//発注商品コード
		strSql.append("  hacyu_syohin_cd,");
		//優先便区分
		strSql.append("  yusen_bin_kb,");
		//PLU区分
		strSql.append("  plu_kb, ");
		//廃番フラグ
		strSql.append("  haiban_fg ");
		strSql.append(" from r_syohin ");
		strSql.append(" where ");
		strSql.append("       delete_fg = '0' ");
		strSql.append("   and BUMON_CD = '").append(bean.getBumonCd()).append("'");
		strSql.append("   and rtrim(SYOHIN_CD) = '").append(bean.getSyohinCd()).append("'");
		//有効日の当日までの直近データ取得
		strSql.append(" AND YUKO_DT = ");
		strSql.append("     (SELECT max(YUKO_DT)");
		strSql.append("      FROM R_SYOHIN ");
		strSql.append("      WHERE BUMON_CD = '").append(bean.getBumonCd()).append("'");
		strSql.append("      AND   SYOHIN_CD = '").append(bean.getSyohinCd()).append("'");
		strSql.append("      AND   YUKO_DT < '" + MstDt1 + "' ");
		strSql.append("      AND   DELETE_FG= '0') ");
		strSql.append(" )");

		return strSql.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql(Object beanMst)
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
	protected String replaceAll(String base, String before, String after)
	{
		if (base == null)
			return base;
		int pos = base.lastIndexOf(before);
		if (pos < 0)
			return base;
		int befLen = before.length();
		StringBuffer sb = new StringBuffer(base);
		while (pos >= 0 && (pos = base.lastIndexOf(before, pos)) >= 0)
		{
			sb.delete(pos, pos + befLen);
			sb.insert(pos, after);
			pos--;
		}
		return sb.toString();
	}
	/**
	 * 店舗配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoItiran();　戻り値　店舗配列<br>
	 * <br>
	 * @return ArrayList 店舗配列
	 */
	public ArrayList getTenpoItiran()
	{
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
	public void setTenpoItiran(ArrayList list)
	{
		tenpoItiran = list;
	}

	/**
	 * 商品コード取得配列に対するセッター
	 * @param bumon_cd
	 * @return
	 */
	public void setSyohinItiran(ArrayList list)
	{
		syohinItiran = list;
	}

	/**
	 * 商品コード取得配列に対するゲッター
	 * @param bumon_cd
	 * @return
	 */
	public ArrayList getSyohinItiran()
	{
		return syohinItiran;
	}

	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}

	/**
	 * 販区コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBumonCd()
	{
		return this.bumon_cd;
	}

	/**
	 * 商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}

	/**
	 * 商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohinCd()
	{
		return this.syohin_cd;
	}

	/**
	 * 契約数取得用SQL文を作成する
	 * @param bumonCd 部門コード
	 * @param syohinCd 商品コード
	 * @return 契約数取得用SQL文
	 */
	public static String getSelectKeiyakusuSql(String bumonCd, String syohinCd)
	{
		StringBuffer sql = new StringBuffer();
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sql.append("  select BUMON_CD");
		sql.append("  select '").append(bumonCd).append("' AS BUMON_CD");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
		sql.append("        ,SYOHIN_CD");
		sql.append("        ,SYOKAI_KEIYAKU_QT");
		sql.append("        ,RUIKEI_KEIYAKU_QT");
		sql.append("        ,RUIKEI_TUIKEIYAKU_QT");
		sql.append("        ,KONKAI_TUIKEIYAKU_QT");
		sql.append("        ,ZENKAI_TUIKEIYAKU_QT");
		sql.append("        ,DELETE_FG");
		sql.append("        ,INSERT_TS");
		sql.append("        ,INSERT_USER_ID");
		sql.append("        ,UPDATE_TS");
		sql.append("        ,UPDATE_USER_ID ");
		sql.append("    from R_KEIYAKUSU ");
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sql.append("   where BUMON_CD = '").append(bumonCd).append("' ");
//		sql.append("     and rtrim(SYOHIN_CD) = '").append(syohinCd).append("' ");
		sql.append("   where rtrim(SYOHIN_CD) = '").append(syohinCd).append("' ");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sql.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
		return sql.toString();
	}

	/**
	 * 契約数登録用SQL文を作成する
	 * @param bumonCd 部門コード
	 * @param syohinCd 商品コード
	 * @param keiyakuQt 初回契約数
	 * @param updateUserId ログインユーザ
	 * @param dbSystemTime DBシステム時間
	 * @return 契約数登録用SQL文
	 */
	public static String getInsertKeiyakusuSql(
		String bumonCd,
		String syohinCd,
		int keiyakuQt,
		String updateUserId,
		String dbSystemTime)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into R_KEIYAKUSU ");
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sql.append("(BUMON_CD");
//		sql.append(",SYOHIN_CD");
		sql.append("(SYOHIN_CD");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
		sql.append(",SYOKAI_KEIYAKU_QT");
		sql.append(",RUIKEI_KEIYAKU_QT");
		sql.append(",RUIKEI_HACHU_QT");
		sql.append(",ZENSYU_HACHU_QT");
		sql.append(",RUIKEI_HANBAI_QT");
		sql.append(",ZENSYU_HANBAI_QT");
		sql.append(",RUIKEI_TUIKEIYAKU_QT");
		sql.append(",KONKAI_TUIKEIYAKU_QT");
		sql.append(",ZENKAI_TUIKEIYAKU_QT");
		//===BEGIN=== Add by kou 2006/11/09
		sql.append(",MINO_QT");
		sql.append(",TINO_QT");
		sql.append(",TORIATSUKAI_TEN_QT");
		sql.append(",SYOKARITU_RT");
		sql.append(",SYOKA_YOTEI_NB");
		//===END=== Add by kou 2006/11/09
		sql.append(",DELETE_FG");
		sql.append(",INSERT_TS");
		sql.append(",INSERT_USER_ID");
		sql.append(",UPDATE_TS");
		sql.append(",UPDATE_USER_ID");
		sql.append(") values ");
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sql.append("('").append(bumonCd).append("'");
//		sql.append(",'").append(syohinCd).append("'");
		sql.append("('").append(syohinCd).append("'");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
		sql.append(",").append(keiyakuQt);
		sql.append(",").append(keiyakuQt);
		sql.append(",0");
		sql.append(",0");
		sql.append(",0");
		sql.append(",0");
		sql.append(",0");
		sql.append(",0");
		sql.append(",0");
		//===BEGIN=== Add by kou 2006/11/09
		sql.append(",0");
		sql.append(",0");
		sql.append(",0");
		sql.append(",0");
		sql.append(",0");
		//===END=== Add by kou 2006/11/09
		sql.append(",'0'");
		sql.append(",'").append(dbSystemTime).append("'");
		sql.append(",'").append(updateUserId).append("'");
		sql.append(",'").append(dbSystemTime).append("'");
		sql.append(",'").append(updateUserId).append("'");
		sql.append(") ");
		return sql.toString();
	}

	/**
	 * 契約数更新用SQL文を作成する
	 * @param bumonCd 部門コード
	 * @param syohinCd 商品コード
	 * @param keiyakuQt 追加契約数
	 * @param updateUserId ログインユーザ
	 * @param dbSystemTime DBシステム時間
	 * @return 契約数更新用SQL文
	 */
	public static String getUpdateKeiyakusuSql(
		String bumonCd,
		String syohinCd,
		int keiyakuQt,
		String updateUserId,
		String dbSystemTime)
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" update R_KEIYAKUSU ");
		sql.append(" set UPDATE_TS = '").append(dbSystemTime).append("'");
		sql.append("    ,UPDATE_USER_ID = '").append(updateUserId).append("'");
		sql.append("    ,DELETE_FG = '0'");
		// 元データがヌルのとき加算されないため修正 by kema 06.09.19
		sql.append("    ,RUIKEI_KEIYAKU_QT = case WHEN RUIKEI_KEIYAKU_QT is null then ").append(
			keiyakuQt);
		sql.append("     else RUIKEI_KEIYAKU_QT + ").append(keiyakuQt);
		sql.append("     end ");
		sql.append(
			"    ,RUIKEI_TUIKEIYAKU_QT = case WHEN RUIKEI_TUIKEIYAKU_QT is null then ").append(
			keiyakuQt);
		sql.append("     else RUIKEI_TUIKEIYAKU_QT + ").append(keiyakuQt);
		sql.append("     end ");
		sql.append("    ,ZENKAI_TUIKEIYAKU_QT = KONKAI_TUIKEIYAKU_QT");
		sql.append("    ,KONKAI_TUIKEIYAKU_QT = ").append(keiyakuQt);
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sql.append(" where BUMON_CD = '").append(bumonCd).append("'");
//		sql.append("   and rtrim(SYOHIN_CD) = '").append(syohinCd).append("'");
		sql.append(" where rtrim(SYOHIN_CD) = '").append(syohinCd).append("'");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
		return sql.toString();
	}

//	↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
//	/**
//	 * 商品マスタ更新用SQL文を作成する
//	 * @param bumonCd 部門コード
//	 * @param syohinCd 商品コード
//	 * @param yukoDT 有効日
//	 * @param HanbaiDT 販売終了日
//	 * @param hachuDT 店舗発注終了日
//	 * @param updateUserId ログインユーザ
//	 * @param dbSystemTime DBシステム時間
//	 * @return 商品マスタ更新用SQL文
//	 */
//	public static String getUpdateSyohinSql(
//		String bumonCd,
//		String syohinCd,
//		String yukoDT,
//		String HanbaiDT,
//		String hachuDT,
//		String henkoDT,
//		String updateUserId,
//		String dbSystemTime)
//	{
//		StringBuffer sql = new StringBuffer();
//		sql.append(" update R_SYOHIN ");
//		sql.append(" set UPDATE_TS = '").append(dbSystemTime).append("'");
//		sql.append("    ,UPDATE_USER_ID = '").append(updateUserId).append("'");
//		sql.append("    ,DELETE_FG = '0'");
//		sql.append("    ,HENKO_DT = '" + henkoDT + "' ");
//		sql.append("    ,HANBAI_ED_DT = '" + HanbaiDT + "' ");
//		sql.append("    ,TEN_HACHU_ED_DT = '" + hachuDT + "' ");
//		sql.append(" where BUMON_CD = '").append(bumonCd).append("'");
//		sql.append("   and rtrim(SYOHIN_CD) = '").append(syohinCd).append("'");
//		sql.append("   and YUKO_DT = '" + yukoDT + "'");
//		return sql.toString();
//	}
//	↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * 更新用ＳＱＬの生成を行う。
	 *  商品マスタ更新用SQL文を作成する
	 */
	public static String getUpdateSyohinSql(mstB00101_KeiyakuzanShuseiBean bean)
	{
		//mstB00101_KeiyakuzanShuseiBean bean = (mstB00101_KeiyakuzanShuseiBean)beanMst;
		StringBuffer sql = new StringBuffer();
		sql.append(" update R_SYOHIN ");
		sql.append(" set UPDATE_TS = '").append(bean.getDbSystemDate()).append("'");
		sql.append("    ,UPDATE_USER_ID = '").append(bean.getUpdateUserId()).append("'");
		sql.append("    ,DELETE_FG = '0'");
		sql.append("    ,HENKO_DT = '" + bean.getHenkoDT() + "' ");
		sql.append("    ,HANBAI_ED_DT = '" + bean.getHanbai_end_dt() + "' ");
		sql.append("    ,TEN_HACHU_ED_DT = '" + bean.getHachu_end_dt() + "' ");
//		↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
		sql.append("    ,NEFUDA_UKEWATASI_KB = '" + bean.getNefudaUkewatasiKb() + "' ");
//		↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑
		sql.append(" where BUMON_CD = '").append(bean.getBumonCd()).append("'");
		sql.append("   and rtrim(SYOHIN_CD) = '").append(bean.getSyohinCd()).append("'");
		sql.append("   and YUKO_DT = '" + bean.getYuko_dt() + "'");

		return sql.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 *  商品マスタ更新用SQL文を作成する
	 * @param updateFuture trueの場合、先付けレコードも更新
	 * Add by kou 2006/10/4
	 */
	public static String getUpdateSyohinSql(
		mstB00101_KeiyakuzanShuseiBean bean,
		boolean updateFuture)
	{
		if (updateFuture)
		{
			StringBuffer sql = new StringBuffer();
			sql.append(" update R_SYOHIN ");
			sql.append(" set UPDATE_TS = '").append(bean.getDbSystemDate()).append("'");
			sql.append("    ,UPDATE_USER_ID = '").append(bean.getUpdateUserId()).append("'");
			sql.append("    ,HENKO_DT = '").append(bean.getHenkoDT()).append("' ");
			sql.append(" where BUMON_CD = '").append(bean.getBumonCd()).append("'");
			sql.append("   and rtrim(SYOHIN_CD) = '").append(bean.getSyohinCd()).append("'");
			sql.append("   and YUKO_DT >= '").append(bean.getYuko_dt()).append("'");

			return sql.toString();
		}
		else
		{
			return getUpdateSyohinSql(bean);
		}
	}
}
