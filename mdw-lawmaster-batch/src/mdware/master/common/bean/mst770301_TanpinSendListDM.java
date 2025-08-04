/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品一括送信の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品一括送信画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T. Kimura
 * @version 1.0(2005/05/10)初版作成
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
import mdware.master.common.dictionary.mst006101_SyoriKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品一括送信の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品一括送信画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst770301_TanpinSendListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
	private String strKanriKb = null;
	/**
	 * コンストラクタ
	 */
	public mst770301_TanpinSendListDM()
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
		mst770301_TanpinSendListBean bean = new mst770301_TanpinSendListBean();
		if (strKanriKb.equals(mst006101_SyoriKbDictionary.TOUROKU.getCode()) ||
				strKanriKb.equals(mst006101_SyoriKbDictionary.SAKUZYO.getCode())) {
		bean.setSyoriSts(rest.getString("syori_sts"));
	    bean.setTenpoCd(rest.getString("tenpo_cd"));
		bean.setTenpoKanjiRn(rest.getString("tenpo_kanji_rn"));
//     bean.setHinsyuCd(rest.getString("hinsyu_cd"));
		bean.setUnitCd(rest.getString("unit_cd"));
		bean.setKanjiRn(rest.getString("kanji_rn"));
//		//bean.setMstTanpinCnt(rest.getLong("mst_tanpin_cnt"));
		bean.setSendDt(rest.getString("send_dt"));
		bean.setInsertTs( rest.getString("insert_ts"));
		bean.setInsertUserId( rest.getString("insert_user_id"));
		bean.setUpdateTs( rest.getString("update_ts"));
		bean.setUpdateUserId( rest.getString("update_user_id"));
		bean.setDeleteFg( rest.getString("delete_fg"));
//		bean.setHankuCd( rest.getString("hanku_cd"));
		bean.setHinbanCd( rest.getString("hinban_cd"));
		bean.setSyohinCd( rest.getString("syohin_cd"));
		bean.setRecHinmeiKanaNa( rest.getString("rec_hinmei_kana_na"));
		bean.setRecHinmeiKanjiNa( rest.getString("rec_hinmei_kanji_na"));
		bean.setBumonCd( rest.getString("bumon_cd"));
		}else {
			bean.setSyoriSts(rest.getString("syori_sts"));
		    bean.setTenpoCd(rest.getString("tenpo_cd"));
			bean.setTenpoKanjiRn(rest.getString("tenpo_kanji_rn"));
			bean.setUnitCd(rest.getString("unit_cd"));
			bean.setKanjiRn(rest.getString("kanji_rn"));
			bean.setSendDt(rest.getString("send_dt"));
			bean.setInsertTs( rest.getString("insert_ts"));
			bean.setBumonCd( rest.getString("bumon_cd"));
			bean.setPc_send_end_KB( rest.getString("pc_send_end_kb"));
			bean.setInsertUserId( rest.getString("insert_user_id"));
			bean.setUpdateTs( rest.getString("update_ts"));
			bean.setUpdateUserId( rest.getString("update_user_id"));
			bean.setDeleteFg( rest.getString("delete_fg"));
			bean.setSyohinCd( rest.getString("syohin_cd"));
			bean.setRecHinmeiKanaNa( rest.getString("rec_hinmei_kana_na"));
			bean.setRecHinmeiKanjiNa( rest.getString("rec_hinmei_kanji_na"));
		}
//		//商品名を漢字品名からレシート商品名に変更。
//		//レシート商品名がなければ、カナ品名を表示に変更。 Add 2006-030-23 M.Kawamoto
//		//bean.setRecHinmeiKannaNa(rest.getString("rec_hinmei_kana_na"));
//		//bean.setHinmeiKannaNa(rest.getString("hinmei_kana_na"));
//		//ここまで
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑
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
		String rb_syori_kb = (String)map.get("rb_syori_kb");
//	  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
		strKanriKb = rb_syori_kb;
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑

		//「登録データ作成」「削除データ作成」時は新規検索
		if (rb_syori_kb.equals(mst006101_SyoriKbDictionary.TOUROKU.getCode()) ||
			rb_syori_kb.equals(mst006101_SyoriKbDictionary.SAKUZYO.getCode())) {
			return getSelectSqlForNewSearch(map);
		}
		//「作成データ照会」「作成データ取消」時は既存データ検索
		else {
			return getSelectSqlForExistSearch(map);
		}
	}

	/**
	 * 検索用ＳＱＬ（新規検索）の生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 * @2005.09.01 Inaba View名修正 VW_SYOHIN→VW_R_SYOHIN
	 */
	public String getSelectSqlForNewSearch( Map map )
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

//		  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//		// 「全店」モード
//		boolean isZenten = false;
//		if (map.get("tx_tenpo_cd").equals("000000")) {
//			isZenten = true;
//		}


//		sb.append("SELECT \n");
//		sb.append("R_TANPIN_SEND.ENTRY_KB SYORI_STS,  \n");
//
//		if (isZenten) {
//			sb.append("'000000' TENPO_CD,  \n");
//			sb.append("'全店' TENPO_KANJI_RN,  \n");
//		} else {
//			sb.append("R_TANPIN_SEND.TENPO_CD TENPO_CD,  \n");
//			sb.append("R_TENPO.KANJI_RN TENPO_KANJI_RN,  \n");
//		}
//
//		sb.append("R_NAMECTF.KANJI_RN HINSYU_KANJI_RN, \n");
//		sb.append("VW_R_SYOHIN.REC_HINMEI_KANJI_NA REC_HINMEI_KANJI_NA, \n");
//		sb.append("VW_R_SYOHIN.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA, \n");
//		sb.append("VW_R_SYOHIN.HINMEI_KANA_NA AS HINMEI_KANA_NA, \n");
//		sb.append("VW_R_SYOHIN.HINSYU_CD,  \n");
//		sb.append("R_TANPIN_SEND.SEND_DT,  \n");
//		sb.append("R_TANPIN_SEND.INSERT_TS,  \n");
//		sb.append("R_TANPIN_SEND.INSERT_USER_ID,  \n");
//		sb.append("R_TANPIN_SEND.UPDATE_TS,  \n");
//		sb.append("R_TANPIN_SEND.UPDATE_USER_ID,  \n");
//		sb.append("R_TANPIN_SEND.DELETE_FG,  \n");
//		sb.append("R_TANPIN_SEND.SYOHIN_CD SYOHIN_CD, \n");
//		sb.append("R_TANPIN_SEND.HANKU_CD HANKU_CD  \n");
//
//		sb.append("FROM  \n");
//		sb.append("R_TANPIN_SEND, \n");
//
//		if (!isZenten) {
//			sb.append("R_TENPO,  \n");
//		}
//
//		sb.append("R_NAMECTF, \n");
//		sb.append("VW_R_SYOHIN \n");
//
//		sb.append("WHERE R_TANPIN_SEND.KANRI_KB = '" + map.get("rb_kanri_kb") + "'  \n");
//		sb.append("AND R_TANPIN_SEND.KANRI_CD = '" + map.get("tx_kanri_cd") + "'  \n");
//		sb.append("AND R_TANPIN_SEND.SEND_DT = '" + map.get("tx_send_dt") + "'  \n");
//		if (!isZenten) {
//		    sb.append("AND R_TANPIN_SEND.TENPO_CD = '" + map.get("tx_tenpo_cd") + "'  \n");
//		}
//		sb.append("AND R_TANPIN_SEND.REQUEST_KB = '" + map.get("rb_request_kb") + "'  \n");
//		sb.append("AND VW_R_SYOHIN.SYOHIN_CD = R_TANPIN_SEND.SYOHIN_CD \n");
//		sb.append("AND VW_R_SYOHIN.HANKU_CD = R_TANPIN_SEND.HANKU_CD \n");
//
//		if (!isZenten) {
//			sb.append("AND R_TANPIN_SEND.TENPO_CD = R_TENPO.TENPO_CD  \n");
//		}
//
//		sb.append("AND R_NAMECTF.CODE_CD = VW_R_SYOHIN.HINSYU_CD \n");
//		sb.append("AND R_NAMECTF.SYUBETU_NO_CD = '" + mst000101_ConstDictionary.KIND + "'  \n");
//
//		sb.append("AND R_TANPIN_SEND.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//		if (!isZenten) {
//			sb.append("AND R_TENPO.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//		}
//		sb.append("AND R_NAMECTF.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//
//		sb.append("ORDER BY R_TANPIN_SEND.TENPO_CD, VW_R_SYOHIN.HINSYU_CD, R_TANPIN_SEND.SYOHIN_CD \n");

		sb.append("select distinct\n");
		sb.append("cast(null as char) syori_sts, \n");
//		if (isZenten) {
//			sb.append("'000' tenpo_cd, \n");
//			sb.append("'全店' tenpo_kanji_rn, \n");
//		} else {
		sb.append("substr(r_tanpinten_toriatukai.tenpo_cd,4,3) tenpo_cd, \n");
		sb.append("r_tenpo.kanji_rn tenpo_kanji_rn, \n");
//		}
		sb.append("r_syohin_taikei.hinban_cd hinban_cd, \n");
		sb.append("r_namectf.kanji_rn kanji_rn, \n");

		//**
		sb.append("r_tanpinten_toriatukai.syohin_cd syohin_cd, \n");
		sb.append("r_syohin.rec_hinmei_kana_na rec_hinmei_kana_na, \n");
		sb.append("r_syohin.bumon_cd bumon_cd, \n");
		sb.append("r_syohin.rec_hinmei_kanji_na rec_hinmei_kanji_na, \n");
		sb.append("'" + map.get("tx_send_dt") + "' send_dt, \n");
		sb.append("cast(null as char) as insert_ts, cast(null as char) as insert_user_id, cast(null as char) as update_ts, cast(null as char) as update_user_id, '0' as delete_fg,  \n");
		sb.append("r_syohin.unit_cd unit_cd, \n");
		sb.append("r_syohin_taikei.system_kb systemKbn, \n");
		sb.append("r_syohin.syohin_cd syohin_cd_order, \n");
		sb.append("r_tenpo.tenpo_cd tenpo_cd_order \n");

		sb.append("from  \n");
//		if (!isZenten) {
		sb.append("r_tenpo,  \n");
//		}
		sb.append("r_namectf, \n");
		sb.append("r_syohin, \n");
		sb.append("r_syohin_taikei, \n");
		sb.append("r_tanpinten_toriatukai  \n");
		sb.append("where  \n");
		if (map.get("systemKbn")!= null && !"null".equals(map.get("systemKbn")) &&  ! "".equals(map.get("systemKbn"))){
			sb.append("r_syohin_taikei.system_kb = '" + map.get("systemKbn") + "'  \n");
			sb.append("and  \n");
		}
//		//販区指定の場合
//		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.HANKU.getCode())) {
//			sb.append("VW_R_SYOHIN.HANKU_CD = '" + map.get("tx_kanri_cd") + "' \n");
//		}
		//品番指定の場合
		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.HINBAN.getCode())) {
			sb.append("r_syohin_taikei.hinban_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("tx_kanri_cd"),4)) + "'   ");
		}
//		//品種指定の場合
//		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.HINSYU.getCode())) {
//			sb.append("VW_R_SYOHIN.HINSYU_CD = '" + map.get("tx_kanri_cd") + "' \n");
//		}
		//ユニット指定の場合
		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.UNIT.getCode())) {
			sb.append("r_syohin_taikei.unit_cd = '" + map.get("tx_kanri_cd") + "' \n");
		}

//		sb.append("AND VW_R_SYOHIN.HINSYU_CD = R_NAMECTF.CODE_CD  \n");
//		sb.append("AND R_NAMECTF.SYUBETU_NO_CD = '" + mst000101_ConstDictionary.KIND + "'  \n");
//
//
//		if (!isZenten) {
//			sb.append("AND R_TENPO.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//		}
//		sb.append("AND R_NAMECTF.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//		sb.append("ORDER BY TENPO_CD, VW_R_SYOHIN.HINSYU_CD, VW_R_SYOHIN.SYOHIN_CD \n");
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("and r_syohin_taikei.unit_cd = r_namectf.code_cd  \n");
//		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.HINBAN.getCode())){
//			sb.append("and r_namectf.syubetu_no_cd = '" + mst000101_ConstDictionary.GOODS_NUMBER + "'  \n");
//		}
//		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.UNIT.getCode())) {
//			sb.append("and r_namectf.syubetu_no_cd = '" + mst000101_ConstDictionary.UNIT + "'  \n");
//		}
		sb.append("and r_namectf.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "'  \n");
		sb.append("and r_namectf.code_cd = r_syohin_taikei.system_kb || r_syohin_taikei.unit_cd  \n");
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
		sb.append("and r_syohin.bumon_cd = r_syohin_taikei.bumon_cd   \n");
		sb.append("and r_syohin.unit_cd = r_syohin_taikei.unit_cd   \n");
		sb.append("and r_tanpinten_toriatukai.bumon_cd = r_syohin.bumon_cd   \n");
		sb.append("and r_tanpinten_toriatukai.syohin_cd = r_syohin.syohin_cd   \n");
		sb.append("and r_tanpinten_toriatukai.tenpo_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("tx_tenpo_cd"),6)) + "'   ");
		sb.append("and r_tenpo.tenpo_cd = r_tanpinten_toriatukai.tenpo_cd \n");
		sb.append("and r_namectf.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
		sb.append("and r_syohin.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
		sb.append("and r_tanpinten_toriatukai.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
		sb.append("and r_tenpo.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("and r_syohin.yuko_dt = " + "MSP710101_GETSYOHINYUKODT(r_syohin.bumon_cd,r_syohin.syohin_cd,cast(null as char))" + "  \n");
		sb.append(" and ");
		sb.append("r_syohin.yuko_dt = ");
		sb.append("( ");
		sb.append("select ");
		sb.append("max(rsw.yuko_dt) ");
		sb.append("from ");
		sb.append("r_syohin rsw ");
		sb.append("where ");
		sb.append("rsw.bumon_cd = r_syohin.bumon_cd ");
		sb.append(" and ");
		sb.append("rsw.syohin_cd = r_syohin.syohin_cd ");
		sb.append(" and ");
		sb.append("rsw.yuko_dt <= '" + map.get("tx_send_dt") + "' ");
		sb.append(") \n");
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
		sb.append("order by tenpo_cd_order, unit_cd,  syohin_cd_order \n");
//	  ↑↑2006.06.22 lulh カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬ（既存データ）の生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSqlForExistSearch( Map map )
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

//      ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//		// 「全店」モード
//		boolean isZenten = false;
//		if (map.get("tx_tenpo_cd").equals("000000")) {
//			isZenten = true;
//		}
//		sb.append("SELECT \n");
//		sb.append("R_TANPIN_SEND.ENTRY_KB SYORI_STS,  \n");
//
//		if (isZenten) {
//			sb.append("'000000' TENPO_CD,  \n");
//			sb.append("'全店' TENPO_KANJI_RN,  \n");
//		} else {
//			sb.append("R_TANPIN_SEND.TENPO_CD TENPO_CD,  \n");
//			sb.append("R_TENPO.KANJI_RN TENPO_KANJI_RN,  \n");
//		}
//
//		sb.append("R_NAMECTF.KANJI_RN HINSYU_KANJI_RN, \n");
//		sb.append("VW_R_SYOHIN.REC_HINMEI_KANJI_NA REC_HINMEI_KANJI_NA, \n");
//		sb.append("VW_R_SYOHIN.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA, \n");
//		sb.append("VW_R_SYOHIN.HINMEI_KANA_NA AS HINMEI_KANA_NA, \n");
//		sb.append("VW_R_SYOHIN.HINSYU_CD,  \n");
//		sb.append("R_TANPIN_SEND.SEND_DT,  \n");
//		sb.append("R_TANPIN_SEND.INSERT_TS,  \n");
//		sb.append("R_TANPIN_SEND.INSERT_USER_ID,  \n");
//		sb.append("R_TANPIN_SEND.UPDATE_TS,  \n");
//		sb.append("R_TANPIN_SEND.UPDATE_USER_ID,  \n");
//		sb.append("R_TANPIN_SEND.DELETE_FG,  \n");
//		sb.append("R_TANPIN_SEND.SYOHIN_CD SYOHIN_CD, \n");
//		sb.append("R_TANPIN_SEND.HANKU_CD HANKU_CD  \n");
//
//		sb.append("FROM  \n");
//		sb.append("R_TANPIN_SEND, \n");
//
//		if (!isZenten) {
//			sb.append("R_TENPO,  \n");
//		}
//
//		sb.append("R_NAMECTF, \n");
//		sb.append("VW_R_SYOHIN \n");
//
//		sb.append("WHERE R_TANPIN_SEND.KANRI_KB = '" + map.get("rb_kanri_kb") + "'  \n");
//		sb.append("AND R_TANPIN_SEND.KANRI_CD = '" + map.get("tx_kanri_cd") + "'  \n");
//		sb.append("AND R_TANPIN_SEND.SEND_DT = '" + map.get("tx_send_dt") + "'  \n");
//		if (!isZenten) {
//		    sb.append("AND R_TANPIN_SEND.TENPO_CD = '" + map.get("tx_tenpo_cd") + "'  \n");
//		}
//		sb.append("AND R_TANPIN_SEND.REQUEST_KB = '" + map.get("rb_request_kb") + "'  \n");
//		sb.append("AND VW_R_SYOHIN.SYOHIN_CD = R_TANPIN_SEND.SYOHIN_CD \n");
//		sb.append("AND VW_R_SYOHIN.HANKU_CD = R_TANPIN_SEND.HANKU_CD \n");
//
//		if (!isZenten) {
//			sb.append("AND R_TANPIN_SEND.TENPO_CD = R_TENPO.TENPO_CD  \n");
//		}
//
//		sb.append("AND R_NAMECTF.CODE_CD = VW_R_SYOHIN.HINSYU_CD \n");
//		sb.append("AND R_NAMECTF.SYUBETU_NO_CD = '" + mst000101_ConstDictionary.KIND + "'  \n");
//
//		sb.append("AND R_TANPIN_SEND.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//		if (!isZenten) {
//			sb.append("AND R_TENPO.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//		}
//		sb.append("AND R_NAMECTF.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
//
//		sb.append("ORDER BY R_TANPIN_SEND.TENPO_CD, VW_R_SYOHIN.HINSYU_CD, R_TANPIN_SEND.SYOHIN_CD \n");

		sb.append("select \n");
		sb.append("r_tanpin_send.entry_kb syori_sts,  \n");

//		if (isZenten) {
//			sb.append("'000' tenpo_cd,  \n");
//			sb.append("'全店' tenpo_kanji_rn,  \n");
//		} else {
		sb.append("r_tenpo.kanji_rn tenpo_kanji_rn,  \n");
//		}
		sb.append("r_namectf.kanji_rn kanji_rn, \n");
		sb.append("substr(r_tanpin_send.tenpo_cd,4,3) tenpo_cd,  \n");
		sb.append("r_tanpin_send.send_dt,  \n");
		sb.append("r_tanpin_send.insert_ts,  \n");
		sb.append("r_tanpin_send.insert_user_id,  \n");
		sb.append("r_tanpin_send.update_ts,  \n");
		sb.append("r_tanpin_send.update_user_id,  \n");
		sb.append("r_tanpin_send.delete_fg,  \n");
		sb.append("r_syohin.bumon_cd bumon_cd, \n");
		sb.append("r_tanpin_send. pc_send_end_kb   pc_send_end_kb , \n");
		sb.append("r_syohin.rec_hinmei_kana_na rec_hinmei_kana_na, \n");
		sb.append("r_syohin.rec_hinmei_kanji_na rec_hinmei_kanji_na, \n");
		sb.append("r_tanpin_send.syohin_cd syohin_cd, \n");
  	    sb.append("r_syohin.unit_cd unit_cd  \n");

  	    sb.append("from  \n");
		sb.append("r_namectf, \n");
		sb.append("r_tenpo, \n");
		sb.append("r_syohin, \n");
		sb.append("r_tanpin_send \n");

		//品番指定の場合
		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.HINBAN.getCode())) {
			sb.append("where r_tanpin_send.kanri_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("tx_kanri_cd"),4)) + "'  ");
		}
		//	ユニット指定の場合
		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.UNIT.getCode())) {
			sb.append("where r_tanpin_send.kanri_cd = '" + map.get("tx_kanri_cd") + "'  \n");
		}
		sb.append("and r_tanpin_send.kanri_kb = '" + map.get("rb_kanri_kb") + "'  \n");
		sb.append("and r_tanpin_send.send_dt = '" + map.get("tx_send_dt") + "'  \n");
		sb.append("and r_tanpin_send.tenpo_cd = '" + conv.convertWhereString( mst000401_LogicBean.formatZero((String)map.get("tx_tenpo_cd"),6)) + "'  ");
		sb.append("and r_tanpin_send.request_kb = '" + "1" + "'  \n");
		sb.append("and r_tanpin_send.bumon_cd = r_syohin.bumon_cd  \n");
		sb.append("and r_tanpin_send.syohin_cd = r_syohin.syohin_cd  \n");
		sb.append("and r_tanpin_send.tenpo_cd = r_tenpo.tenpo_cd  \n");
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("and r_syohin.unit_cd = r_namectf.code_cd  \n");
//		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.HINBAN.getCode())){
//			sb.append("and r_namectf.syubetu_no_cd = '" + mst000101_ConstDictionary.GOODS_NUMBER + "'  \n");
//		}
//		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.UNIT.getCode())) {
//			sb.append("and r_namectf.syubetu_no_cd = '" + mst000101_ConstDictionary.UNIT + "'  \n");
//		}
		sb.append("and r_namectf.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "'  \n");
		sb.append("and r_namectf.code_cd = r_syohin.system_kb || r_syohin.unit_cd  \n");
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
		sb.append("and r_tenpo.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'    \n");
		sb.append("and r_tanpin_send.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'    \n");
		sb.append("and r_syohin.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'    \n");
		sb.append("and r_namectf.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'    \n");
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("and r_syohin.yuko_dt = " + "msp710101_getsyohinyukodt(r_syohin.bumon_cd,r_syohin.syohin_cd,cast(null as char))" + "  \n");
		sb.append(" and ");
		sb.append("r_syohin.yuko_dt = ");
		sb.append("( ");
		sb.append("select ");
		sb.append("max(rsw.yuko_dt) ");
		sb.append("from ");
		sb.append("r_syohin rsw ");
		sb.append("where ");
		sb.append("rsw.bumon_cd = r_syohin.bumon_cd ");
		sb.append(" and ");
		sb.append("rsw.syohin_cd = r_syohin.syohin_cd ");
		sb.append(" and ");
		sb.append("rsw.yuko_dt <= '" + map.get("tx_send_dt") + "' ");
		sb.append(") \n");
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
		sb.append("order by syohin_cd, unit_cd,  tenpo_cd \n");
//	  ↑↑2006.06.22 lulh カスタマイズ修正↑↑
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
