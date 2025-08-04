package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: </p>
 * @author VINX
 * @Version 1.00 2014/09/15 Ha.ntt 海外LAWSON様通貨対応
 */
public class mst370201_DownloadListDM extends DataModule {
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

	//ADD by Tanigawa 2006/9/21 画面上で選択した業種コードにより、検索時に使用するテーブル、取得するカラム、検索結果として表示する項目が異なるため、
	//							""やむを得ず""クラス内で業種コードを共有できる様に、クラス内で参照できる変数を作成。 START
	private String p_str_gyosyu_kb = null;
	//ADD by Tanigawa 2006/9/21   END


	/**
	 * コンストラクタ
	 */
	public mst370201_DownloadListDM() {
		super("rbsite_ora");
	}
	/**
	 * 検索後にＢＥＡＮをインスタンス化する所。
	 * 検索した結果セットをＢＥＡＮとして持ち直す。
	 * DataModuleから呼び出され返したObjectをListに追加する。
	 * @param rest ResultSet
	 * @return Object インスタンス化されたＢＥＡＮ
	 */
	protected Object instanceBean(ResultSet rest) throws SQLException {
		mst370201_DownloadListBean bean = new mst370201_DownloadListBean();
//    ↓↓2006.06.26 yebo カスタマイズ修正↓↓
		// bean.setHankuCd(rest.getString("hanku_cd"));
		bean.setBumonCd(rest.getString("bumon_cd"));
		bean.setSiireHinbanCd(rest.getString("siire_hinban_cd"));
//     ↑↑2006.06.26 yebo カスタマイズ修正↑↑
		bean.setSyohinCd(rest.getString("syohin_cd"));
		bean.setYukoDt(rest.getString("yuko_dt"));
		bean.setHinmeiKanjiNa(rest.getString("hinmei_kanji_na"));
		bean.setKikakuKanjiNa(rest.getString("kikaku_kanji_na"));
		bean.setGentankaVl(rest.getDouble("gentanka_vl"));
		bean.setBaitankaVl(rest.getDouble("baitanka_vl"));
		bean.setHanbaiStDt(rest.getString("hanbai_st_dt"));
		bean.setHanbaiEdDt(rest.getString("hanbai_ed_dt"));

		//ADD by Tanigawa 2006/9/21 障害票No.0013対応 カラー名称、サイズ名称の取得及び、保持 START
		if( this.p_str_gyosyu_kb != null ){
			if( this.p_str_gyosyu_kb.trim().equals("T") || this.p_str_gyosyu_kb.trim().equals("J") ){	//タグ衣料か実用衣料の場合のみ、画面に表示
				bean.setColorNa(rest.getString("color_na"));
				bean.setSizeNa(rest.getString("size_na"));
			}
		}
		//ADD by Tanigawa 2006/9/21 障害票No.0013対応  END

		bean.setCreateDatabase();
		return bean;
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql(Map map) {

//DEL by Tanigawa 2006/10/25 障害票№0193対応 START
//
//
////	    ↓↓2006.06.26 yebo カスタマイズ修正↓↓
////		DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName());
//
//		String DEL_FG = mst000801_DelFlagDictionary.INAI.getCode();
//
//
//		//ADD by Tanigawa 2006/9/21 カラー名称、サイズ名称取得用 START
//		if ( map.get("gyosyu_kb") != null ) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
//			this.p_str_gyosyu_kb = (String)map.get("gyosyu_kb");
//		}
//		//ADD by Tanigawa 2006/9/21 カラー名称、サイズ名称取得用  END
//
//
//		//Where句の作成
//		StringBuffer strWhere = new StringBuffer();
////
////		//販区
////		if (map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0) {
////			strWhere.append(" and ");
////			strWhere.append("rs.hanku_cd = '" + conv.convertWhereString((String)map.get("hanku_cd")) + "'");
////		}
////
////		//品種
////		if (map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0) {
////			strWhere.append(" and ");
////			strWhere.append("rs.hinsyu_cd = '" + conv.convertWhereString((String)map.get("hinsyu_cd")) + "'");
////		}
////
////		//仕入先
////		if (map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0) {
////			strWhere.append(" and ");
////			strWhere.append("rs.siiresaki_cd = '" + conv.convertWhereString((String)map.get("siiresaki_cd")) + "'");
////		}
////
////		//JANメーカ
////		if (map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0) {
////			strWhere.append(" and ");
////			strWhere.append("rs.maker_cd = '" + conv.convertWhereString((String)map.get("maker_cd")) + "'");
////		}
////
//////2005.12.07 A.Jo Edit START 「販売期間」の条件指定を「販売終了日」の条件指定に変更する。
//////		//販売期間
//////		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0) {
//////			strWhere.append(" and ");
//////			strWhere.append("rs.hanbai_st_dt <= '" + conv.convertWhereString((String)map.get("hanbai_ed_dt")) + "'");
//////		}
//////
//////		if (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0) {
//////			strWhere.append(" and ");
//////			strWhere.append("rs.hanbai_ed_dt >= '" + conv.convertWhereString((String)map.get("hanbai_st_dt")) + "'");
//////		}
////		//販売終了日
////		if (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0) {
////			strWhere.append(" and ");
////			strWhere.append("rs.hanbai_ed_dt >= '" + conv.convertWhereString((String)map.get("hanbai_st_dt")) + "'");
////		}
////
////		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0) {
////			strWhere.append(" and ");
////			strWhere.append("rs.hanbai_ed_dt <= '" + conv.convertWhereString((String)map.get("hanbai_ed_dt")) + "'");
////		}
//////2005.12.07 A.Jo Edit END
////
////		//シーズン
////		if (map.get("season_cd") != null && ((String)map.get("season_cd")).trim().length() > 0) {
////			strWhere.append(" and ");
////			strWhere.append("rs.season_cd = '" + conv.convertWhereString((String)map.get("season_cd")) + "'");
////		}
////
////		StringBuffer sb = new StringBuffer();
////
////		sb.append("select ");
////		sb.append("	rs.hanku_cd as hanku_cd");
////		sb.append("	, ");
////		sb.append("	rs.syohin_cd as syohin_cd");
////		sb.append("	, ");
////		sb.append("	rs.yuko_dt as yuko_dt");
////		sb.append("	, ");
////		sb.append("	rs.hinmei_kanji_na as hinmei_kanji_na");
////		sb.append("	, ");
////		sb.append("	rs.kikaku_kanji_na as kikaku_kanji_na");
////		sb.append("	, ");
////		sb.append("	rs.gentanka_vl as gentanka_vl");
////		sb.append("	, ");
////		sb.append("	rs.baitanka_vl as baitanka_vl");
////		sb.append("	, ");
////		sb.append("	rs.hanbai_st_dt as hanbai_st_dt");
////		sb.append("	, ");
////		sb.append("	rs.hanbai_ed_dt as hanbai_ed_dt ");
////		sb.append("from ");
////		sb.append("	r_syohin rs ");
////		sb.append("where ");
////		sb.append(" rs.gyosyu_kb = '" + conv.convertWhereString((String)map.get("gyosyu_kb")) + "'"); //ファイル種別
////		sb.append(" and ");
////		sb.append(" rs.delete_fg = '").append(DEL_FG).append("' ");
////		sb.append(strWhere);
////		sb.append(" and ");
////		sb.append(" rs.yuko_dt = (select max(yuko_dt) from r_syohin sub where sub.hanku_cd = rs.hanku_cd and sub.syohin_cd = rs.syohin_cd and sub.yuko_dt <= '").append((String)map.get("yuko_dt")).append("')"); //有効日
////
////		StringBuffer sql = new StringBuffer();
////
////		sql.append(sb);
////		sql.append("union all ");
////		sql.append("select ");
////		sql.append(" rs.hanku_cd as hanku_cd");
////		sql.append(" , ");
////		sql.append(" rs.syohin_cd as syohin_cd");
////		sql.append(" , ");
////		sql.append(" rs.yuko_dt as yuko_dt");
////		sql.append(" , ");
////		sql.append(" rs.hinmei_kanji_na as hinmei_kanji_na");
////		sql.append(" , ");
////		sql.append(" rs.kikaku_kanji_na as kikaku_kanji_na");
////		sql.append(" , ");
////		sql.append(" rs.gentanka_vl as gentanka_vl");
////		sql.append(" , ");
////		sql.append(" rs.baitanka_vl as baitanka_vl");
////		sql.append(" , ");
////		sql.append(" rs.hanbai_st_dt as hanbai_st_dt");
////		sql.append(" , ");
////		sql.append(" rs.hanbai_ed_dt as hanbai_ed_dt ");
////		sql.append("from ");
////		sql.append(" r_syohin rs ");
////		sql.append("where ");
////		sql.append(" rs.gyosyu_kb = '" + conv.convertWhereString((String)map.get("gyosyu_kb")) + "'"); //ファイル種別
////		sql.append(strWhere);
////		sql.append(" and ");
////		sql.append(" rs.yuko_dt = (select min(yuko_dt) from r_syohin sub where sub.hanku_cd = rs.hanku_cd and sub.syohin_cd = rs.syohin_cd and sub.yuko_dt > '").append((String)map.get("yuko_dt")).append("' and sub.delete_fg = '").append(DEL_FG).append("')"); //有効日
////		sql.append(" and");
////		sql.append(" not exists");
////		sql.append(" (");
////		sql.append("  select '1'");
////		sql.append("  from");
////		sql.append("   (").append(sb).append(") sub2");
////		sql.append("  where");
////		sql.append("    sub2.hanku_cd = rs.hanku_cd and");
////		sql.append("    sub2.syohin_cd = rs.syohin_cd");
////		sql.append(" ) ");
////		sql.append("order by ");
////		sql.append(" hanku_cd,");
////		sql.append(" syohin_cd,");
////		sql.append(" yuko_dt");
////		return sql.toString();
//
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//		StringBuffer strWhere2 = new StringBuffer();
//
//		//	システム区分
//		strWhere2.append(" and rst.system_kb = '" + map.get("gyosyu_kb").toString() + "' ");
//		//	部門コード
//		strWhere2.append(" and rst.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  ");
//		//	品番
//		if (map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0) {
//			strWhere2.append(" and rst.hinban_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinban_cd"),4)) + "'");
//		}
//		//  品種
//		if (map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0) {
//			strWhere2.append(" and rst.hinsyu_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinsyu_cd"),4)) + "'");
//		}
//		//  ライン
//		if (map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0) {
//			strWhere2.append(" and rst.line_cd = '" + conv.convertWhereString((String)map.get("line_cd")) + "' ");
//		}
//		//  ユニット
//		if (map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0) {
//			strWhere2.append(" and rst.unit_cd = '" + conv.convertWhereString((String)map.get("unit_cd")) + "' ");
//		}
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//
//		String strGyosyuKb = null;
//
//		if (map.get("gyosyu_kb").equals("T")) {
//			// タグ
//			strGyosyuKb = mst000601_GyoshuKbDictionary.A08.getCode() ;
//		} else if (map.get("gyosyu_kb").equals("J")) {
//			// 実用
//			strGyosyuKb = mst000601_GyoshuKbDictionary.A07.getCode();
//		} else if (map.get("gyosyu_kb").equals("G")) {
//			// グロ・バラ
//			strGyosyuKb = mst000601_GyoshuKbDictionary.GRO.getCode();
//		} else if (map.get("gyosyu_kb").equals("F")) {
//			// デイリー
//			strGyosyuKb = mst000601_GyoshuKbDictionary.FRE.getCode() ;
//		}
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
////		//	部門コード
////		strWhere.append("  and rs.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  ");
////		//	品番
////		if (map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0) {
////			strWhere.append(" and rs.hinban_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinban_cd"),4)) + "'");
////		}
////		// ユニット
////		if (map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0) {
////			strWhere.append(" and ");
////			strWhere.append(" rs.unit_cd = '" + conv.convertWhereString((String)map.get("unit_cd")) + "' ");
////		}
////		//  品種とラインコード入力場合
////		if ((map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0) && (map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0)) {
////			strWhere.append(" and ");
////			strWhere.append("rs.unit_cd = ( select ");
////			strWhere.append("                 rst.unit_cd as unit_cd ");
////			strWhere.append("                 from r_syohin_taikei rst ");
////			strWhere.append("					where ");
////			strWhere.append("                 rst.system_kb = '" + conv.convertWhereString((String)map.get("gyosyu_kb")) + "' and  ");
////			strWhere.append("                 rst.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'   ");
////			// 品番
////			if (map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0) {
////				strWhere.append(" and ");
////				strWhere.append("             rst.hinban_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinban_cd"),4)) + "'  ");
////			}
////			strWhere.append(" and ");
////			strWhere.append("                 rst.hinsyu_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinsyu_cd"),4)) + "' ");
////			strWhere.append(" and ");
////			strWhere.append("                 rst.line_cd = '" + conv.convertWhereString((String)map.get("line_cd")) + "' ) ");
////		}
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//		// 仕入先
////		↓↓2006.08.01 H.Yamamoto カスタマイズ修正↓↓
////		if (map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0) {
////			strWhere.append(" and ");
////		↓↓2006.08.12 H.Yamamoto カスタマイズ修正↓↓
//////		strWhere.append(" (rs.siiresaki_cd = '" + conv.convertWhereString((String)map.get("siiresaki_cd")) + "'");
////			strWhere.append(" (rs.siiresaki_cd like '" + conv.convertWhereString((String)map.get("siiresaki_cd")).substring(0,6) + "%'");
////		↑↑2006.08.12 H.Yamamoto カスタマイズ修正↑↑
////			strWhere.append(" or  ");
////			strWhere.append(" rs.ten_siiresaki_kanri_cd in (  ");
////			strWhere.append(" select rtn.ten_siiresaki_kanri_cd   ");
////			strWhere.append(" from  r_tenbetu_siiresaki  rtn ");
////			strWhere.append(" where ");
////			strWhere.append(" rtn.kanri_kb = '"+mst000901_KanriKbDictionary.BUMON.getCode() +"' and " );
////			strWhere.append(" rtn.kanri_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  and ");
////		↓↓2006.08.12 H.Yamamoto カスタマイズ修正↓↓
//////		strWhere.append(" rtn.siiresaki_cd =  '" + conv.convertWhereString((String)map.get("siiresaki_cd")) + "' ) ");
////			strWhere.append(" rtn.siiresaki_cd like '" + conv.convertWhereString((String)map.get("siiresaki_cd")).substring(0,6) + "%' and ");
////			strWhere.append(" rtn.delete_flg =  '0' ) ");
////		↑↑2006.08.12 H.Yamamoto カスタマイズ修正↑↑
////			strWhere.append(" ) ");
////		}
////		↑↑2006.08.01 H.Yamamoto カスタマイズ修正↑↑
//		// JANメーカー
//		if (map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.maker_cd = '" + conv.convertWhereString((String)map.get("maker_cd")) + "' ");
//		}
//		// 販売開始日
//		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.hanbai_st_dt <= '" + conv.convertWhereString((String)map.get("hanbai_ed_dt")) + "' ");
//		}
//		// 販売終了日
//		if (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.hanbai_ed_dt >= '" + conv.convertWhereString((String)map.get("hanbai_st_dt")) + "' ");
//		}
//		// シーズン
//		if (map.get("season_cd") != null && ((String)map.get("season_cd")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.season_cd = '" + conv.convertWhereString((String)map.get("season_cd")) + "' ");
//		}
//		// 商品コード
//		if (map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
//		}
//		// 仕入先品番
//		if (map.get("siire_hinban_cd") != null && ((String)map.get("siire_hinban_cd")).trim().length() > 0) {
//			strWhere.append(" and ");
////			↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
////			strWhere.append(" rs.siire_hinban_cd = '" + conv.convertWhereString((String)map.get("siire_hinban_cd")) + "' ");
//			strWhere.append(" rs.siire_hinban_cd like '" + conv.convertWhereString((String)map.get("siire_hinban_cd")) + "%' ");
////			↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
//		}
//		// EOS区分
//		if (map.get("ct_eoskb") != null && ((String)map.get("ct_eoskb")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.eos_kb = '" + conv.convertWhereString((String)map.get("ct_eoskb")) + "' ");
//		}
//		// 店舗
//		if (map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.syohin_cd = (select ");
//			strWhere.append("                    rt.syohin_cd as syohin_cd ");
//			strWhere.append("                    from ");
//			strWhere.append("                    r_tanpinten_toriatukai rt ");
//			strWhere.append("					   where ");
////			↓↓2006.07.20 H.Yamamoto カスタマイズ・バグ修正↓↓
////			strWhere.append("                    rt.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "' and ");
//			strWhere.append("                    rt.bumon_cd = rs.bumon_cd and ");
//			strWhere.append("                    rt.syohin_cd = rs.syohin_cd and ");
////			↑↑2006.07.20 H.Yamamoto カスタマイズ・バグ修正↑↑
//			strWhere.append("                    rt.tenpo_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("tenpo_cd"),6)) + "' )");
//		}
////		↓↓2006.08.01 H.Yamamoto 練習サイト向け修正↓↓
//		// 仕入先
//		if (map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0) {
//			// ===BEGIN=== Modify by kou 2006/9/12
//			strWhere.append(" and ");
////			strWhere.append(" (rs.siiresaki_cd like '" + conv.convertWhereString((String)map.get("siiresaki_cd")).substring(0,6) + "%'");
////			strWhere.append(" or  ");
////			strWhere.append(" (select count(*)  ");
////			strWhere.append(" from  r_tensyohin_reigai  rtr ");
////			strWhere.append(" where ");
////			strWhere.append(" rtr.bumon_cd = rs.bumon_cd and " );
////			strWhere.append(" rtr.syohin_cd = rs.syohin_cd and ");
////			strWhere.append(" rtr.siiresaki_cd like '" + conv.convertWhereString((String)map.get("siiresaki_cd")).substring(0,6) + "%' and ");
////			strWhere.append(" rtr.delete_fg = '0') ");
////			strWhere.append(" > 0) ");
//			strWhere.append("( rs.bumon_cd || rs.syohin_cd in ( ");
//			strWhere.append(" 		select dt.bumon_cd || dt.syohin_cd  ");
//			strWhere.append(" 		  from dt_siiresaki_syohin dt ");
//			strWhere.append(" 		 where dt.siiresaki_cd = '");
//			strWhere.append(conv.convertWhereString((String)map.get("siiresaki_cd")).substring(0,6));
//			strWhere.append("' ) or ");
//			strWhere.append(" rs.bumon_cd || rs.syohin_cd in ( ");
//			strWhere.append(" 		select bumon_cd || syohin_cd  ");
//			strWhere.append(" 		  from r_syohin  ");
//			strWhere.append(" 		 where substring(siiresaki_cd,1,6) = '");
//			strWhere.append(conv.convertWhereString((String)map.get("siiresaki_cd")).substring(0,6));
//			strWhere.append("' ) ) ");
//			// ===END=== modify by kou 2006/9/12
//		}
////		↑↑2006.08.01 H.Yamamoto 練習サイト向け修正↑↑
//
//		// ===BEGIN=== Add by kou 2006/9/12
//		// 画面の検索条件追加により
//		// サブクラス
//		if (map.get("subClassCd") != null && ((String)map.get("subClassCd")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.subclass_cd = '");
//			strWhere.append(conv.convertWhereString((String)map.get("subClassCd")));
//			strWhere.append("' ");
//		}
//		// 棚番
//		if (map.get("tanaNbFrom") != null && ((String)map.get("tanaNbFrom")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.tana_no_nb >= ");
//			strWhere.append(conv.convertWhereString((String)map.get("tanaNbFrom")));
//		}
//		if (map.get("tanaNbTo") != null && ((String)map.get("tanaNbTo")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.tana_no_nb <= ");
//			strWhere.append(conv.convertWhereString((String)map.get("tanaNbTo")));
//		}
//		// 商品区分
//		if (map.get("syohinKb") != null && ((String)map.get("syohinKb")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.fuji_syohin_kb = '");
//			strWhere.append(conv.convertWhereString((String)map.get("syohinKb")));
//			strWhere.append("' ");
//		}
//		// ＰＢ区分
//		if (map.get("pbKb") != null && ((String)map.get("pbKb")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.pb_kb = '");
//			strWhere.append(conv.convertWhereString((String)map.get("pbKb")));
//			strWhere.append("' ");
//		}
//		// バイヤーＮｏ
//		if (map.get("byNb") != null && ((String)map.get("byNb")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.syokai_user_id = '");
//			strWhere.append("0000" + conv.convertWhereString((String)map.get("byNb")));
//			strWhere.append("' ");
//		}
//		// 新規登録日
//		if (map.get("newInsertDt") != null && ((String)map.get("newInsertDt")).trim().length() > 0) {
//			strWhere.append(" and ");
//			strWhere.append(" rs.sinki_toroku_dt = '");
//			strWhere.append(conv.convertWhereString((String)map.get("newInsertDt")));
//			strWhere.append("' ");
//		}
//		// ===END=== Add by kou 2006/9/12
//
//		StringBuffer sb = new StringBuffer();
//		sb.append("select ");
//		sb.append("     rs.siire_hinban_cd as siire_hinban_cd, ");
//		sb.append("	    rs.bumon_cd as bumon_cd,");
//		sb.append("	    rs.syohin_cd as syohin_cd,");
//		sb.append("     rs.yuko_dt as yuko_dt,");
//		sb.append("	    rs.hinmei_kanji_na as hinmei_kanji_na,");
//		sb.append("	    rs.kikaku_kanji_na as kikaku_kanji_na,");
//		sb.append("	    rs.gentanka_vl as gentanka_vl,");
//		sb.append("	    rs.baitanka_vl as baitanka_vl,");
//		sb.append("	    rs.hanbai_st_dt as hanbai_st_dt,");
//		sb.append("	    rs.hanbai_ed_dt as hanbai_ed_dt ");
//
//	if (map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
//		//タグ衣料の場合は、名称・CTFマスタよりカラー名称、サイズ名称を取得
//		sb.append("	    , ");
//		sb.append("	    T1.KANJI_RN AS color_na, ");	//カラー名称(漢字略称)
//		sb.append("	    T2.KANJI_RN AS size_na ");		//サイズ名称(漢字略称)
//	}else if ( map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("J") ){
//		//実用衣料の場合は、商品マスタよりカラー名称、サイズ名称を取得
//		sb.append("	    , ");
//		sb.append("	    rs.KIKAKU_KANJI_NA AS color_na, ");	//カラー名称(漢字規格)
//		sb.append("	    rs.KIKAKU_KANJI_2_NA AS size_na ");	//サイズ名称(漢字規格２)
//	}
//
//		sb.append(" from ");
//		sb.append("     r_syohin rs ");
////		↓↓2006.07.09 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("        inner join r_syohin_taikei rst ");
//		sb.append("         on  rst.bumon_cd = rs.bumon_cd ");
//		sb.append("         and rst.unit_cd  = rs.unit_cd ");
//		sb.append(strWhere2);
////		↑↑2006.07.09 H.Yamamoto カスタマイズ修正↑↑
//
//		//ADD by Tanigawa 2006/9/21 カラー名称、サイズ名称取得用 START
//	if (map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
//		// タグ
//		sb.append(" LEFT JOIN R_NAMECTF T1 ON (T1.SYUBETU_NO_CD = '"+mst000101_ConstDictionary.COLOR+"' AND rs.COLOR_CD = T1.CODE_CD) ");	//カラー名称取得用LEFT JOIN
//		sb.append(" LEFT JOIN R_NAMECTF T2 ON (T2.SYUBETU_NO_CD = '"+mst000101_ConstDictionary.SIZE+"' AND rs.SIZE_CD  = T2.CODE_CD) ");	//サイズ名称取得用LEFT JOIN
//
//	}
//		//ADD by Tanigawa 2006/9/21 カラー名称、サイズ名称取得用  END
//
//
//		sb.append("where ");
//		sb.append("     rs.gyosyu_kb = '" + strGyosyuKb + "'  "); // 業種区分
//		sb.append("     and rs.delete_fg = '").append(DEL_FG).append("' ");
//		sb.append(strWhere);
//		sb.append(" and ");
////		↓↓2006.08.02 H.Yamamoto カスタマイズ修正↓↓
////		sb.append(" rs.yuko_dt = (select max(yuko_dt) from r_syohin sub where sub.bumon_cd = rs.bumon_cd and sub.syohin_cd = rs.syohin_cd and sub.yuko_dt <= '").append((String)map.get("yuko_dt")).append("') "); //有効日
//		sb.append(" rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, '" + (String)map.get("yuko_dt") + "') ");
////		↑↑2006.08.02 H.Yamamoto カスタマイズ修正↑↑
//
////		↓↓2006.07.28 H.Yamamoto カスタマイズ修正↓↓
////		StringBuffer sb2 = new StringBuffer();
////		sb2.append("select ");
////		sb2.append("     rs.siire_hinban_cd as siire_hinban_cd, ");
////		sb2.append("	    rs.bumon_cd as bumon_cd,");
////		sb2.append("	    rs.syohin_cd as syohin_cd,");
////		sb2.append("     rs.yuko_dt as yuko_dt,");
////		sb2.append("	    rs.hinmei_kanji_na as hinmei_kanji_na,");
////		sb2.append("	    rs.kikaku_kanji_na as kikaku_kanji_na,");
////		sb2.append("	    rs.gentanka_vl as gentanka_vl,");
////		sb2.append("	    rs.baitanka_vl as baitanka_vl,");
////		sb2.append("	    rs.hanbai_st_dt as hanbai_st_dt,");
////		sb2.append("	    rs.hanbai_ed_dt as hanbai_ed_dt ");
////		sb2.append(" from ");
////		sb2.append("     r_syohin rs ");
////		sb2.append("        inner join r_syohin_taikei rst ");
////		sb2.append("         on  rst.bumon_cd = rs.bumon_cd ");
////		sb2.append("         and rst.unit_cd  = rs.unit_cd ");
////		sb2.append(strWhere2);
////		sb2.append("where ");
////		sb2.append("     rs.gyosyu_kb = '" + strGyosyuKb + "'  "); // 業種区分
////		sb2.append("     and rs.delete_fg = '").append(DEL_FG).append("' ");
////		sb2.append(strWhere);
////		sb2.append(" and ");
////		sb2.append(" rs.yuko_dt = (select min(yuko_dt) from r_syohin sub where sub.bumon_cd = rs.bumon_cd and sub.syohin_cd = rs.syohin_cd and sub.yuko_dt > '").append((String)map.get("yuko_dt")).append("')"); //有効日
////		↑↑2006.07.28 H.Yamamoto カスタマイズ修正↑↑
//
//		StringBuffer sql = new StringBuffer();
//		sql.append(sb);
////		↓↓2006.08.02 H.Yamamoto カスタマイズ修正↓↓
////		sql.append("union all ");
//////		↓↓2006.07.28 H.Yamamoto カスタマイズ修正↓↓
//////		sql.append(" select ");
//////		sql.append("     rs.siire_hinban_cd as siire_hinban_cd, ");
//////		sql.append("	    rs.bumon_cd as bumon_cd,");
//////		sql.append("	    rs.syohin_cd as syohin_cd,");
//////		sql.append("     rs.yuko_dt as yuko_dt,");
//////		sql.append("	    rs.hinmei_kanji_na as hinmei_kanji_na,");
//////		sql.append("	    rs.kikaku_kanji_na as kikaku_kanji_na,");
//////		sql.append("	    rs.gentanka_vl as gentanka_vl,");
//////		sql.append("	    rs.baitanka_vl as baitanka_vl,");
//////		sql.append("	    rs.hanbai_st_dt as hanbai_st_dt,");
//////		sql.append("	    rs.hanbai_ed_dt as hanbai_ed_dt ");
//////		sql.append(" from ");
//////		sql.append("     r_syohin rs ");
//////		sql.append(" where ");
//////		sql.append("     rs.gyosyu_kb = '" + strGyosyuKb + "'  "); // 業種区分
//////		sql.append(strWhere);
//////		sql.append(" and ");
//////		sql.append(" rs.yuko_dt = (select min(yuko_dt) from r_syohin sub where sub.bumon_cd = rs.bumon_cd and sub.syohin_cd = rs.syohin_cd and sub.yuko_dt > '").append((String)map.get("yuko_dt")).append("' and sub.delete_fg = '").append(DEL_FG).append("')"); //有効日
////		sql.append(sb2);
//////		↑↑2006.07.28 H.Yamamoto カスタマイズ修正↑↑
////		sql.append(" and");
////		sql.append(" not exists");
////		sql.append(" (");
////		sql.append("  select '1'");
////		sql.append("  from");
////		sql.append("   (").append(sb).append(") sub2");
////		sql.append("  where");
////		sql.append("    sub2.bumon_cd = rs.bumon_cd and");
////		sql.append("    sub2.syohin_cd = rs.syohin_cd");
////		sql.append(" ) ");
////		↑↑2006.08.02 H.Yamamoto カスタマイズ修正↑↑
//		sql.append("order by ");
//		sql.append(" bumon_cd,");
//		sql.append(" siire_hinban_cd,");
//		sql.append(" syohin_cd");
////	     ↑↑2006.06.26 yebo カスタマイズ修正↑↑

//		DEL by Tanigawa 2006/10/25 障害票№0193対応  END

		String DEL_FG = mst000801_DelFlagDictionary.INAI.getCode();

		if ( map.get("gyosyu_kb") != null ) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			this.p_str_gyosyu_kb = (String)map.get("gyosyu_kb");
		}

		String strGyosyuKb = null;
		if (map.get("gyosyu_kb").equals("T")) {
			// タグ
			strGyosyuKb = mst000601_GyoshuKbDictionary.A08.getCode() ;
		} else if (map.get("gyosyu_kb").equals("J")) {
			// 実用
			strGyosyuKb = mst000601_GyoshuKbDictionary.A07.getCode();
		} else if (map.get("gyosyu_kb").equals("G")) {
			// グロ・バラ
			strGyosyuKb = mst000601_GyoshuKbDictionary.GRO.getCode();
		} else if (map.get("gyosyu_kb").equals("F")) {
			// デイリー
			strGyosyuKb = mst000601_GyoshuKbDictionary.FRE.getCode() ;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("     rs.siire_hinban_cd as siire_hinban_cd, ");
		sb.append("	    rs.bumon_cd as bumon_cd,");
		sb.append("	    rs.syohin_cd as syohin_cd,");
		sb.append("     rs.yuko_dt as yuko_dt,");
		sb.append("	    rs.hinmei_kanji_na as hinmei_kanji_na,");
		sb.append("	    rs.kikaku_kanji_na as kikaku_kanji_na,");
		sb.append("	    rs.gentanka_vl as gentanka_vl,");
		sb.append("	    rs.baitanka_vl as baitanka_vl,");
		sb.append("	    rs.hanbai_st_dt as hanbai_st_dt,");
		sb.append("	    rs.hanbai_ed_dt as hanbai_ed_dt ");

	if (map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
		//タグ衣料の場合は、名称・CTFマスタよりカラー名称、サイズ名称を取得
		sb.append("	    , ");
		sb.append("	    T1.KANJI_RN AS color_na, ");	//カラー名称(漢字略称)
		sb.append("	    T2.KANJI_RN AS size_na ");		//サイズ名称(漢字略称)
	}else if ( map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("J") ){
		//実用衣料の場合は、商品マスタよりカラー名称、サイズ名称を取得
		sb.append("	    , ");
		sb.append("	    rs.KIKAKU_KANJI_NA AS color_na, ");	//カラー名称(漢字規格)
		sb.append("	    rs.KIKAKU_KANJI_2_NA AS size_na ");	//サイズ名称(漢字規格２)
	}

		sb.append(" from ");
		sb.append("     r_syohin rs,  ");
		sb.append("     r_syohin_taikei rst ");

		//カラー名称、サイズ名称取得用 START
		if (map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			// タグ
			sb.append(" LEFT JOIN R_NAMECTF T1 ON (T1.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)+"' AND rs.COLOR_CD = T1.CODE_CD) ");	//カラー名称取得用LEFT JOIN
			sb.append(" LEFT JOIN R_NAMECTF T2 ON (T2.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)+"' AND rs.SIZE_CD  = T2.CODE_CD) ");	//サイズ名称取得用LEFT JOIN
		}

		sb.append(" where ");

		sb.append("         rs.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "' ");
		sb.append("     and rs.gyosyu_kb = '" + strGyosyuKb + "'  "); // 業種区分
		sb.append("     and rs.delete_fg = '").append(DEL_FG).append("' ");

		// 仕入先
		// JANメーカー
		if (map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.maker_cd = '" + conv.convertWhereString((String)map.get("maker_cd")) + "' ");
		}

		//===BEGIN=== Modify by kou 2006/11/27
		//障害票142の対応、微妙！
		//条件の販売期間と仕様の販売期間の定義が不一致になったような気もしますが。
//		// 販売開始日
//		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0) {
//			sb.append(" and ");
//			sb.append(" rs.hanbai_st_dt <= '" + conv.convertWhereString((String)map.get("hanbai_ed_dt")) + "' ");
//		}
//		// 販売終了日
//		if (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0) {
//			sb.append(" and ");
//			sb.append(" rs.hanbai_ed_dt >= '" + conv.convertWhereString((String)map.get("hanbai_st_dt")) + "' ");
//		}
		//販売開始日のみ指定
		if (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0
			&& (map.get("hanbai_ed_dt") == null || ((String)map.get("hanbai_ed_dt")).trim().length() == 0)
			) {
				sb.append(" and ");
				sb.append(" rs.hanbai_st_dt = '")
					.append(conv.convertWhereString((String)map.get("hanbai_st_dt"))).append("' ");
		}

		//販売終了日のみ指定
		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0
			&& (map.get("hanbai_st_dt") == null || ((String)map.get("hanbai_st_dt")).trim().length() == 0)
			) {
				sb.append(" and ");
				sb.append(" rs.hanbai_ed_dt = '")
					.append(conv.convertWhereString((String)map.get("hanbai_ed_dt"))).append("' ");
		}

		//両方指定
		if (map.get("hanbai_ed_dt") != null && ((String)map.get("hanbai_ed_dt")).trim().length() > 0
			&& (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0)
			) {
				sb.append(" and ");
				sb.append(" rs.hanbai_st_dt >= '")
					.append(conv.convertWhereString((String)map.get("hanbai_st_dt"))).append("' ");
				sb.append(" and ");
				sb.append(" rs.hanbai_ed_dt <= '")
					.append(conv.convertWhereString((String)map.get("hanbai_ed_dt"))).append("' ");
		}

		//===END=== Modify by kou 2006/11/27

		// シーズン
		if (map.get("season_cd") != null && ((String)map.get("season_cd")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.season_cd = '" + conv.convertWhereString((String)map.get("season_cd")) + "' ");
		}
		// 商品コード
		if (map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
		}
		// 仕入先品番
		if (map.get("siire_hinban_cd") != null && ((String)map.get("siire_hinban_cd")).trim().length() > 0) {
			sb.append(" and ");
//			sb.append(" rs.siire_hinban_cd like '" + conv.convertWhereString((String)map.get("siire_hinban_cd")) + "%' ");

//			 ADD by Tanigawa 2006/11/14 障害票№0143対応 START
			if( map.get("siiresakisyohincdChecked") != null && ( new Boolean((String)map.get("siiresakisyohincdChecked")).booleanValue() ) ){
				sb.append(" rs.siire_hinban_cd like '" + conv.convertWhereString((String)map.get("siire_hinban_cd")) + "%' ");
			}else{
				sb.append(" rs.siire_hinban_cd = '" + conv.convertWhereString((String)map.get("siire_hinban_cd")) + "' ");
			}
//			 ADD by Tanigawa 2006/11/14 障害票№0143対応  END
		}
		// EOS区分
		if (map.get("ct_eoskb") != null && ((String)map.get("ct_eoskb")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.eos_kb = '" + conv.convertWhereString((String)map.get("ct_eoskb")) + "' ");
		}
		// 店舗
		if (map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.syohin_cd = (select ");
			sb.append("                    rt.syohin_cd as syohin_cd ");
			sb.append("                    from ");
			sb.append("                    r_tanpinten_toriatukai rt ");
			sb.append("					   where ");
			sb.append("                    rt.bumon_cd  = rs.bumon_cd and ");
			sb.append("                    rt.syohin_cd = rs.syohin_cd and ");
//			sb.append("                    rt.tenpo_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("tenpo_cd"),6)) + "' )");
//			 MOD by Tanigawa 2006/10/25 障害票№0206対応 START
			sb.append("                    rt.tenpo_cd  = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("tenpo_cd"),6)) + "' and ");
			sb.append("                    rt.delete_fg = '" + DEL_FG + "' )");
//			 MOD by Tanigawa 2006/10/25 障害票№0206対応  END

		}
		// 仕入先
		if (map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0) {
			sb.append(" and ");
			sb.append("( ");
			sb.append("  rs.syohin_cd in ( ");
			sb.append(" 		select dt.syohin_cd  ");
			sb.append(" 		  from dt_siiresaki_syohin dt ");
			sb.append(" 		 where dt.siiresaki_cd = '" + conv.convertWhereString((String)map.get("siiresaki_cd")).substring(0,6) + "' ");
			sb.append("            and dt.bumon_cd = '"+ conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) +"' ");
			sb.append("  ) or ");
			sb.append("  substring(rs.siiresaki_cd,1,6) = '");
			sb.append(conv.convertWhereString((String)map.get("siiresaki_cd")).substring(0,6));
			sb.append("' ");
			sb.append(") ");
		}

//		 ADD by Tanigawa 2006/11/16 障害票№0205対応 START
		if (map.get("maker_cd2") != null && ((String)map.get("maker_cd2")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.TEN_SIIRESAKI_KANRI_CD = '"+conv.convertWhereString((String)map.get("maker_cd2"))+"' ");
		}
//		 ADD by Tanigawa 2006/11/16 障害票№0205対応  END


		// サブクラス
		if (map.get("subClassCd") != null && ((String)map.get("subClassCd")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.subclass_cd = '");
			sb.append(conv.convertWhereString((String)map.get("subClassCd")));
			sb.append("' ");
		}
		// 棚番
		if (map.get("tanaNbFrom") != null && ((String)map.get("tanaNbFrom")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.tana_no_nb >= ");
			sb.append(conv.convertWhereString((String)map.get("tanaNbFrom")));
		}
		if (map.get("tanaNbTo") != null && ((String)map.get("tanaNbTo")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.tana_no_nb <= ");
			sb.append(conv.convertWhereString((String)map.get("tanaNbTo")));
		}
		// 商品区分
		if (map.get("syohinKb") != null && ((String)map.get("syohinKb")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.fuji_syohin_kb = '");
			sb.append(conv.convertWhereString((String)map.get("syohinKb")));
			sb.append("' ");
		}
		// ＰＢ区分
		if (map.get("pbKb") != null && ((String)map.get("pbKb")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.pb_kb = '");
			sb.append(conv.convertWhereString((String)map.get("pbKb")));
			sb.append("' ");
		}
		// バイヤーＮｏ
		if (map.get("byNb") != null && ((String)map.get("byNb")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.syokai_user_id = '");
			sb.append("0000" + conv.convertWhereString((String)map.get("byNb")));
			sb.append("' ");
		}
		// 新規登録日
		if (map.get("newInsertDt") != null && ((String)map.get("newInsertDt")).trim().length() > 0) {
			sb.append(" and ");
			sb.append(" rs.sinki_toroku_dt = '");
			sb.append(conv.convertWhereString((String)map.get("newInsertDt")));
			sb.append("' ");
		}

//		sb.append(" and ");
//		sb.append(" rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, '" + (String)map.get("yuko_dt") + "') ");
//		 MOD by Tanigawa 2006/10/29 障害票№0220対応 START
		sb.append(" and ");
		sb.append(" rs.yuko_dt = (SELECT MAX(T1.YUKO_DT) FROM R_SYOHIN T1 WHERE T1.BUMON_CD=rs.BUMON_CD AND T1.SYOHIN_CD=rs.SYOHIN_CD) ");
//		 MOD by Tanigawa 2006/10/29 障害票№0220対応  END

		//	システム区分
		sb.append("         and rst.system_kb = '" + map.get("gyosyu_kb").toString() + "' ");
		//	部門コード
		sb.append("         and rst.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  ");
		//	品番
		if (map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0) {
			sb.append("     and rst.hinban_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinban_cd"),4)) + "'");
		}
		//  品種
		if (map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0) {
			sb.append("     and rst.hinsyu_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("hinsyu_cd"),4)) + "'");
		}
		//  ライン
		if (map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0) {
			sb.append("     and rst.line_cd = '" + conv.convertWhereString((String)map.get("line_cd")) + "' ");
		}
		//  ユニット
		if (map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0) {
			sb.append("     and rst.unit_cd = '" + conv.convertWhereString((String)map.get("unit_cd")) + "' ");
		}
		sb.append("         and rs.bumon_cd = rst.bumon_cd ");
		sb.append("         and rs.unit_cd  = rst.unit_cd ");

		sb.append("order by ");
		sb.append(" bumon_cd,");
		sb.append(" siire_hinban_cd,");
		sb.append(" syohin_cd");

		return sb.toString();
	}


//	 ADD by Tanigawa 2006/11/16 障害票№0205対応 START
	public String getSelectSqlForPrepared( Map map ){

		String DEL_FG = mst000801_DelFlagDictionary.INAI.getCode();

		if ( map.get("gyosyu_kb") != null ) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			this.p_str_gyosyu_kb = (String)map.get("gyosyu_kb");
		}

		String strGyosyuKb = null;
		if (map.get("gyosyu_kb").equals("T")) {
			// タグ
			strGyosyuKb = mst000601_GyoshuKbDictionary.A08.getCode() ;
		} else if (map.get("gyosyu_kb").equals("J")) {
			// 実用
			strGyosyuKb = mst000601_GyoshuKbDictionary.A07.getCode();
		} else if (map.get("gyosyu_kb").equals("G")) {
			// グロ・バラ
			strGyosyuKb = mst000601_GyoshuKbDictionary.GRO.getCode();
		} else if (map.get("gyosyu_kb").equals("F")) {
			// デイリー
			strGyosyuKb = mst000601_GyoshuKbDictionary.FRE.getCode() ;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("     rs.siire_hinban_cd as siire_hinban_cd, ");
		sb.append("	    rs.bumon_cd as bumon_cd,");
		sb.append("	    rs.syohin_cd as syohin_cd,");
		sb.append("     rs.yuko_dt as yuko_dt,");
		sb.append("	    rs.hinmei_kanji_na as hinmei_kanji_na,");
		sb.append("	    rs.kikaku_kanji_na as kikaku_kanji_na,");
		sb.append("	    rs.gentanka_vl as gentanka_vl,");
		sb.append("	    rs.baitanka_vl as baitanka_vl,");
		sb.append("	    rs.hanbai_st_dt as hanbai_st_dt,");
		sb.append("	    rs.hanbai_ed_dt as hanbai_ed_dt ");

	if (map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
		//タグ衣料の場合は、名称・CTFマスタよりカラー名称、サイズ名称を取得
		sb.append("	    , ");
		sb.append("	    T1.KANJI_RN AS color_na, ");	//カラー名称(漢字略称)
		sb.append("	    T2.KANJI_RN AS size_na ");		//サイズ名称(漢字略称)
	}else if ( map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("J") ){
		//実用衣料の場合は、商品マスタよりカラー名称、サイズ名称を取得
		sb.append("	    , ");
		sb.append("	    rs.KIKAKU_KANJI_NA AS color_na, ");	//カラー名称(漢字規格)
		sb.append("	    rs.KIKAKU_KANJI_2_NA AS size_na ");	//サイズ名称(漢字規格２)
	}

		sb.append(" from ");
		sb.append("     r_syohin rs,  ");
		sb.append("     r_syohin_taikei rst ");

		//カラー名称、サイズ名称取得用 START
		if (map.get("gyosyu_kb") != null && map.get("gyosyu_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			// タグ
			sb.append(" LEFT JOIN R_NAMECTF T1 ON (T1.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)+"' AND rs.COLOR_CD = T1.CODE_CD) ");	//カラー名称取得用LEFT JOIN
			sb.append(" LEFT JOIN R_NAMECTF T2 ON (T2.SYUBETU_NO_CD = '"+MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)+"' AND rs.SIZE_CD  = T2.CODE_CD) ");	//サイズ名称取得用LEFT JOIN
		}

		sb.append(" where ");

//		sb.append("         rs.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "' ");
//		sb.append("     and rs.gyosyu_kb = '" + strGyosyuKb + "'  "); // 業種区分
//		sb.append("     and rs.delete_fg = '"+DEL_FG+"' ");
//		sb.append("     and rs.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
		sb.append("         rs.bumon_cd  = ? ");
		sb.append("     and rs.syohin_cd = ? ");
		sb.append("     and rs.gyosyu_kb = '" + strGyosyuKb + "'  "); // 業種区分
		sb.append("     and rs.delete_fg = '"+DEL_FG+"' ");
		sb.append("     and rs.yuko_dt = (SELECT MAX(T1.YUKO_DT) FROM R_SYOHIN T1 WHERE T1.BUMON_CD=rs.BUMON_CD AND T1.SYOHIN_CD=rs.SYOHIN_CD) ");
		sb.append("     and rst.system_kb = '" + map.get("gyosyu_kb").toString() + "' ");//	システム区分
		//	部門コード
//		sb.append("         and rst.bumon_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("bumon_cd"),4)) + "'  ");
		sb.append("     and rs.bumon_cd = rst.bumon_cd ");
		sb.append("     and rs.unit_cd  = rst.unit_cd ");

		//===BEGIN=== Add by kou 2006/11/28
		if (map.get("siiresaki_cd") != null
			&& ((String)map.get("siiresaki_cd")).trim().length() >= 6) {
				sb.append(" and substr(rs.siiresaki_cd,1,6) = '")
					.append(((String)map.get("siiresaki_cd")).trim().substring(0,6)).append("'");
		}
		//===END=== Add by kou 2006/11/28

		return sb.toString();
	}
//	 ADD by Tanigawa 2006/11/16 障害票№0205対応  END

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql(Object beanMst) {
		return null;
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql(Object beanMst) {
		return null;
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql(Object beanMst) {
		return null;
	}

	/**
	 * JDK1.4からは使用できるようになったString.replaceAllをJDK1.3以前用に作成する。
	 * @param base
	 * @param before
	 * @param after
	 * @return
	 */
	protected String replaceAll(String base, String before, String after) {
		if (base == null)
			return base;
		int pos = base.lastIndexOf(before);
		if (pos < 0)
			return base;
		int befLen = before.length();
		StringBuffer sb = new StringBuffer(base);
		while (pos >= 0 && (pos = base.lastIndexOf(before, pos)) >= 0) {
			sb.delete(pos, pos + befLen);
			sb.insert(pos, after);
			pos--;
		}
		return sb.toString();
	}
}
