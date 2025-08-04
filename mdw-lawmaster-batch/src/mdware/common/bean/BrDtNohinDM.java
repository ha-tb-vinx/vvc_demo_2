package mdware.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator for SQL (2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2005/6/25 10:55:50)
 */
public class BrDtNohinDM extends DataModule {
	/**
	 * コンストラクタ
	 */
	public BrDtNohinDM() {
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
		BrDtNohinBean bean = new BrDtNohinBean();
		bean.setRecordKb(rest.getString("record_kb"));
		bean.setStandardJobNa(rest.getString("standard_job_na"));
		bean.setSyoriMd(rest.getString("syori_md"));
		bean.setGoki(rest.getString("goki"));
		bean.setBatchNb(rest.getString("batch_nb"));
		bean.setDataGyoNb(rest.getLong("data_gyo_nb"));
		bean.setTenpoCd(rest.getString("tenpo_cd"));
		bean.setTenhankuCd(rest.getString("tenhanku_cd"));
		bean.setKeijyogoSyuseiBit(rest.getLong("keijyogo_syusei_bit"));
		bean.setBrDenpyoSyubetuKb(rest.getString("br_denpyo_syubetu_kb"));
		bean.setDenpyoKb(rest.getString("denpyo_kb"));
		bean.setTorihikisakiCd(rest.getString("torihikisaki_cd"));
		bean.setEdaNb(rest.getLong("eda_nb"));
		bean.setDenpyoNb(rest.getLong("denpyo_nb"));
		bean.setChumonsyoNb(rest.getLong("chumonsyo_nb"));
		bean.setKoseiGyosu(rest.getLong("kosei_gyosu"));
		bean.setGenkaKeiVl(rest.getLong("genka_kei_vl"));
		bean.setBaikaKeiVl(rest.getLong("baika_kei_vl"));
		bean.setHachuDt(rest.getString("hachu_dt"));
		bean.setCenterKeijoDt(rest.getString("center_keijo_dt"));
		bean.setAiteTenpoCd(rest.getString("aite_tenpo_cd"));
		bean.setAiteBumonCd(rest.getString("aite_bumon_cd"));
		bean.setBrCenterCd(rest.getString("br_center_cd"));
		bean.setBrBaitaiKb(rest.getString("br_baitai_kb"));
		bean.setJetKb(rest.getString("jet_kb"));
		bean.setKesikomiCenterCd(rest.getString("kesikomi_center_cd"));
		bean.setDenpyoKihyosyaNa(rest.getString("denpyo_kihyosya_na"));
		bean.setSekininsyaNa(rest.getString("sekininsya_na"));
		bean.setSiharaiKiboDt(rest.getString("siharai_kibo_dt"));
		bean.setRecordNb(rest.getString("record_nb"));
		bean.setMachiukeFg(rest.getString("machiuke_fg"));
		bean.setPorKb(rest.getString("por_kb"));
		bean.setBinKb(rest.getString("bin_kb"));
		bean.setKaisyaCd(rest.getString("kaisya_cd"));
		bean.setAsnTaioLv(rest.getString("asn_taio_lv"));
		bean.setMatehanNoKenpinFg(rest.getString("matehan_no_kenpin_fg"));
		bean.setCenterSagyoDt(rest.getString("center_sagyo_dt"));
		bean.setNohinDt(rest.getString("nohin_dt"));
		bean.setScmJoho(rest.getString("scm_joho"));
		bean.setSampleTenpo(rest.getString("sample_tenpo"));
		bean.setSiireKeijoMakeFg(rest.getString("siire_keijo_make_fg"));
		bean.setSiireKeijoMakeTs(rest.getString("siire_keijo_make_ts"));
		bean.setHostKeijoSumiFg(rest.getString("host_keijo_sumi_fg"));
		bean.setHostKeijoErrorCd(rest.getString("host_keijo_error_cd"));
		bean.setKeijoKekaMakeFg(rest.getString("keijo_keka_make_fg"));
		bean.setKeijoKekaMakeTs(rest.getString("keijo_keka_make_ts"));
		bean.setTenpoNonyuMakeFg(rest.getString("tenpo_nonyu_make_fg"));
		bean.setTenpoNonyuMakeTs(rest.getString("tenpo_nonyu_make_ts"));
		bean.setMinoChinoMakeFg(rest.getString("mino_chino_make_fg"));
		bean.setMinoChinoMakeTs(rest.getString("mino_chino_make_ts"));
		bean.setBupinJyuryoMakeFg(rest.getString("bupin_jyuryo_make_fg"));
		bean.setBupinJyuryoMakeTs(rest.getString("bupin_jyuryo_make_ts"));
		bean.setCenterFeeMakeFg(rest.getString("center_fee_make_fg"));
		bean.setCenterFeeMakeTs(rest.getString("center_fee_make_ts"));
		bean.setRiyoUserId(rest.getString("riyo_user_id"));
		bean.setInsertTs(rest.getString("insert_ts"));
		bean.setUpdateTs(rest.getString("update_ts"));
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName());
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("	record_kb ");
		sb.append("	, ");
		sb.append("	standard_job_na ");
		sb.append("	, ");
		sb.append("	syori_md ");
		sb.append("	, ");
		sb.append("	goki ");
		sb.append("	, ");
		sb.append("	batch_nb ");
		sb.append("	, ");
		sb.append("	data_gyo_nb ");
		sb.append("	, ");
		sb.append("	tenpo_cd ");
		sb.append("	, ");
		sb.append("	tenhanku_cd ");
		sb.append("	, ");
		sb.append("	keijyogo_syusei_bit ");
		sb.append("	, ");
		sb.append("	br_denpyo_syubetu_kb ");
		sb.append("	, ");
		sb.append("	denpyo_kb ");
		sb.append("	, ");
		sb.append("	torihikisaki_cd ");
		sb.append("	, ");
		sb.append("	eda_nb ");
		sb.append("	, ");
		sb.append("	denpyo_nb ");
		sb.append("	, ");
		sb.append("	chumonsyo_nb ");
		sb.append("	, ");
		sb.append("	kosei_gyosu ");
		sb.append("	, ");
		sb.append("	genka_kei_vl ");
		sb.append("	, ");
		sb.append("	baika_kei_vl ");
		sb.append("	, ");
		sb.append("	hachu_dt ");
		sb.append("	, ");
		sb.append("	center_keijo_dt ");
		sb.append("	, ");
		sb.append("	aite_tenpo_cd ");
		sb.append("	, ");
		sb.append("	aite_bumon_cd ");
		sb.append("	, ");
		sb.append("	br_center_cd ");
		sb.append("	, ");
		sb.append("	br_baitai_kb ");
		sb.append("	, ");
		sb.append("	jet_kb ");
		sb.append("	, ");
		sb.append("	kesikomi_center_cd ");
		sb.append("	, ");
		sb.append("	denpyo_kihyosya_na ");
		sb.append("	, ");
		sb.append("	sekininsya_na ");
		sb.append("	, ");
		sb.append("	siharai_kibo_dt ");
		sb.append("	, ");
		sb.append("	record_nb ");
		sb.append("	, ");
		sb.append("	machiuke_fg ");
		sb.append("	, ");
		sb.append("	por_kb ");
		sb.append("	, ");
		sb.append("	bin_kb ");
		sb.append("	, ");
		sb.append("	kaisya_cd ");
		sb.append("	, ");
		sb.append("	asn_taio_lv ");
		sb.append("	, ");
		sb.append("	matehan_no_kenpin_fg ");
		sb.append("	, ");
		sb.append("	center_sagyo_dt ");
		sb.append("	, ");
		sb.append("	nohin_dt ");
		sb.append("	, ");
		sb.append("	scm_joho ");
		sb.append("	, ");
		sb.append("	sample_tenpo ");
		sb.append("	, ");
		sb.append("	siire_keijo_make_fg ");
		sb.append("	, ");
		sb.append("	siire_keijo_make_ts ");
		sb.append("	, ");
		sb.append("	host_keijo_sumi_fg ");
		sb.append("	, ");
		sb.append("	host_keijo_error_cd ");
		sb.append("	, ");
		sb.append("	keijo_keka_make_fg ");
		sb.append("	, ");
		sb.append("	keijo_keka_make_ts ");
		sb.append("	, ");
		sb.append("	tenpo_nonyu_make_fg ");
		sb.append("	, ");
		sb.append("	tenpo_nonyu_make_ts ");
		sb.append("	, ");
		sb.append("	mino_chino_make_fg ");
		sb.append("	, ");
		sb.append("	mino_chino_make_ts ");
		sb.append("	, ");
		sb.append("	bupin_jyuryo_make_fg ");
		sb.append("	, ");
		sb.append("	bupin_jyuryo_make_ts ");
		sb.append("	, ");
		sb.append("	center_fee_make_fg ");
		sb.append("	, ");
		sb.append("	center_fee_make_ts ");
		sb.append("	, ");
		sb.append("	riyo_user_id ");
		sb.append("	, ");
		sb.append("	insert_ts ");
		sb.append("	, ");
		sb.append("	update_ts ");
		sb.append("from ");
		sb.append("	br_dt_nohin ");

		// denpyo_nb に対するWHERE区
		if( map.get("denpyo_nb") != null && ((String)map.get("denpyo_nb")).trim().length() > 0  )
		{
			sb.append(" WHERE ");
			sb.append("denpyo_nb = " + (String)map.get("denpyo_nb"));
		}

		return sb.toString();
	}

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
