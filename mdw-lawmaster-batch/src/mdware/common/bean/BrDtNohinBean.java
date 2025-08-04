package mdware.common.bean;

import java.util.Iterator;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author VINX
 * @version 1.0 2014/06/23 Nghia-HT 海外LAWSON様UTF-8対応
 */
public class BrDtNohinBean {
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int RECORD_KB_MAX_LENGTH = 2;
	public static final int STANDARD_JOB_NA_MAX_LENGTH = 2;
	public static final int SYORI_MD_MAX_LENGTH = 4;
	public static final int GOKI_MAX_LENGTH = 1;
	public static final int BATCH_NB_MAX_LENGTH = 2;
	public static final int TENPO_CD_MAX_LENGTH = 10;
	public static final int TENHANKU_CD_MAX_LENGTH = 4;
	public static final int BR_DENPYO_SYUBETU_KB_MAX_LENGTH = 2;
	public static final int DENPYO_KB_MAX_LENGTH = 2;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH = 11;
	public static final int HACHU_DT_MAX_LENGTH = 8;
	public static final int CENTER_KEIJO_DT_MAX_LENGTH = 8;
	public static final int AITE_TENPO_CD_MAX_LENGTH = 6;
	public static final int AITE_BUMON_CD_MAX_LENGTH = 3;
	public static final int BR_CENTER_CD_MAX_LENGTH = 2;
	public static final int BR_BAITAI_KB_MAX_LENGTH = 1;
	public static final int JET_KB_MAX_LENGTH = 1;
	public static final int KESIKOMI_CENTER_CD_MAX_LENGTH = 6;
	public static final int DENPYO_KIHYOSYA_NA_MAX_LENGTH = 10;
	public static final int SEKININSYA_NA_MAX_LENGTH = 10;
	public static final int SIHARAI_KIBO_DT_MAX_LENGTH = 8;
	public static final int RECORD_NB_MAX_LENGTH = 16;
	public static final int MACHIUKE_FG_MAX_LENGTH = 1;
	public static final int POR_KB_MAX_LENGTH = 1;
	public static final int BIN_KB_MAX_LENGTH = 3;
	public static final int KAISYA_CD_MAX_LENGTH = 6;
	public static final int ASN_TAIO_LV_MAX_LENGTH = 1;
	public static final int MATEHAN_NO_KENPIN_FG_MAX_LENGTH = 1;
	public static final int CENTER_SAGYO_DT_MAX_LENGTH = 8;
	public static final int NOHIN_DT_MAX_LENGTH = 8;
	public static final int SCM_JOHO_MAX_LENGTH = 32;
	public static final int SAMPLE_TENPO_MAX_LENGTH = 1;
	public static final int SIIRE_KEIJO_MAKE_FG_MAX_LENGTH = 1;
	public static final int SIIRE_KEIJO_MAKE_TS_MAX_LENGTH = 14;
	public static final int HOST_KEIJO_SUMI_FG_MAX_LENGTH = 1;
	public static final int HOST_KEIJO_ERROR_CD_MAX_LENGTH = 3;
	public static final int KEIJO_KEKA_MAKE_FG_MAX_LENGTH = 1;
	public static final int KEIJO_KEKA_MAKE_TS_MAX_LENGTH = 14;
	public static final int TENPO_NONYU_MAKE_FG_MAX_LENGTH = 1;
	public static final int TENPO_NONYU_MAKE_TS_MAX_LENGTH = 14;
	public static final int MINO_CHINO_MAKE_FG_MAX_LENGTH = 1;
	public static final int MINO_CHINO_MAKE_TS_MAX_LENGTH = 14;
	public static final int BUPIN_JYURYO_MAKE_FG_MAX_LENGTH = 1;
	public static final int BUPIN_JYURYO_MAKE_TS_MAX_LENGTH = 14;
	public static final int CENTER_FEE_MAKE_FG_MAX_LENGTH = 1;
	public static final int CENTER_FEE_MAKE_TS_MAX_LENGTH = 14;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String record_kb = null;
	private String standard_job_na = null;
	private String syori_md = null;
	private String goki = null;
	private String batch_nb = null;
	private long data_gyo_nb = 0;
	private String tenpo_cd = null;
	private String tenhanku_cd = null;
	private long keijyogo_syusei_bit = 0;
	private String br_denpyo_syubetu_kb = null;
	private String denpyo_kb = null;
	private String torihikisaki_cd = null;
	private long eda_nb = 0;
	private long denpyo_nb = 0;
	private long chumonsyo_nb = 0;
	private long kosei_gyosu = 0;
	private long genka_kei_vl = 0;
	private long baika_kei_vl = 0;
	private String hachu_dt = null;
	private String center_keijo_dt = null;
	private String aite_tenpo_cd = null;
	private String aite_bumon_cd = null;
	private String br_center_cd = null;
	private String br_baitai_kb = null;
	private String jet_kb = null;
	private String kesikomi_center_cd = null;
	private String denpyo_kihyosya_na = null;
	private String sekininsya_na = null;
	private String siharai_kibo_dt = null;
	private String record_nb = null;
	private String machiuke_fg = null;
	private String por_kb = null;
	private String bin_kb = null;
	private String kaisya_cd = null;
	private String asn_taio_lv = null;
	private String matehan_no_kenpin_fg = null;
	private String center_sagyo_dt = null;
	private String nohin_dt = null;
	private String scm_joho = null;
	private String sample_tenpo = null;
	private String siire_keijo_make_fg = null;
	private String siire_keijo_make_ts = null;
	private String host_keijo_sumi_fg = null;
	private String host_keijo_error_cd = null;
	private String keijo_keka_make_fg = null;
	private String keijo_keka_make_ts = null;
	private String tenpo_nonyu_make_fg = null;
	private String tenpo_nonyu_make_ts = null;
	private String mino_chino_make_fg = null;
	private String mino_chino_make_ts = null;
	private String bupin_jyuryo_make_fg = null;
	private String bupin_jyuryo_make_ts = null;
	private String center_fee_make_fg = null;
	private String center_fee_make_ts = null;
	private String riyo_user_id = null;
	private String insert_ts = null;
	private String update_ts = null;

	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase() {
		createDatabase = true;
	}
	public boolean isCreateDatabase() {
		return createDatabase;
	}

	/**
	 * BrDtNohinBeanを１件のみ抽出したい時に使用する
	 */
	public static BrDtNohinBean getBrDtNohinBean(DataHolder dataHolder) {
		BrDtNohinBean bean = null;
		BeanHolder beanHolder = new BeanHolder(new BrDtNohinDM());
		try {
			Iterator ite = beanHolder.getPageBeanListFromDataHolder(dataHolder).iterator();
			//１件以上存在する時はまず保存する
			if (ite.hasNext())
				bean = (BrDtNohinBean)ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if (ite.hasNext())
				bean = null;
		} catch (Exception e) {
		}
		return bean;
	}

	// record_kbに対するセッターとゲッターの集合
	public boolean setRecordKb(String record_kb) {
		this.record_kb = record_kb;
		return true;
	}
	public String getRecordKb() {
		return cutString(this.record_kb, RECORD_KB_MAX_LENGTH);
	}
	public String getRecordKbString() {
		return "'" + cutString(this.record_kb, RECORD_KB_MAX_LENGTH) + "'";
	}
	public String getRecordKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.record_kb, RECORD_KB_MAX_LENGTH));
	}

	// standard_job_naに対するセッターとゲッターの集合
	public boolean setStandardJobNa(String standard_job_na) {
		this.standard_job_na = standard_job_na;
		return true;
	}
	public String getStandardJobNa() {
		return cutString(this.standard_job_na, STANDARD_JOB_NA_MAX_LENGTH);
	}
	public String getStandardJobNaString() {
		return "'" + cutString(this.standard_job_na, STANDARD_JOB_NA_MAX_LENGTH) + "'";
	}
	public String getStandardJobNaHTMLString() {
		return HTMLStringUtil.convert(cutString(this.standard_job_na, STANDARD_JOB_NA_MAX_LENGTH));
	}

	// syori_mdに対するセッターとゲッターの集合
	public boolean setSyoriMd(String syori_md) {
		this.syori_md = syori_md;
		return true;
	}
	public String getSyoriMd() {
		return cutString(this.syori_md, SYORI_MD_MAX_LENGTH);
	}
	public String getSyoriMdString() {
		return "'" + cutString(this.syori_md, SYORI_MD_MAX_LENGTH) + "'";
	}
	public String getSyoriMdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.syori_md, SYORI_MD_MAX_LENGTH));
	}

	// gokiに対するセッターとゲッターの集合
	public boolean setGoki(String goki) {
		this.goki = goki;
		return true;
	}
	public String getGoki() {
		return cutString(this.goki, GOKI_MAX_LENGTH);
	}
	public String getGokiString() {
		return "'" + cutString(this.goki, GOKI_MAX_LENGTH) + "'";
	}
	public String getGokiHTMLString() {
		return HTMLStringUtil.convert(cutString(this.goki, GOKI_MAX_LENGTH));
	}

	// batch_nbに対するセッターとゲッターの集合
	public boolean setBatchNb(String batch_nb) {
		this.batch_nb = batch_nb;
		return true;
	}
	public String getBatchNb() {
		return cutString(this.batch_nb, BATCH_NB_MAX_LENGTH);
	}
	public String getBatchNbString() {
		return "'" + cutString(this.batch_nb, BATCH_NB_MAX_LENGTH) + "'";
	}
	public String getBatchNbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.batch_nb, BATCH_NB_MAX_LENGTH));
	}

	// data_gyo_nbに対するセッターとゲッターの集合
	public boolean setDataGyoNb(String data_gyo_nb) {
		try {
			this.data_gyo_nb = Long.parseLong(data_gyo_nb);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean setDataGyoNb(long data_gyo_nb) {
		this.data_gyo_nb = data_gyo_nb;
		return true;
	}
	public long getDataGyoNb() {
		return this.data_gyo_nb;
	}
	public String getDataGyoNbString() {
		return Long.toString(this.data_gyo_nb);
	}

	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd) {
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd() {
		return cutString(this.tenpo_cd, TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString() {
		return "'" + cutString(this.tenpo_cd, TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getTenpoCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.tenpo_cd, TENPO_CD_MAX_LENGTH));
	}

	// tenhanku_cdに対するセッターとゲッターの集合
	public boolean setTenhankuCd(String tenhanku_cd) {
		this.tenhanku_cd = tenhanku_cd;
		return true;
	}
	public String getTenhankuCd() {
		return cutString(this.tenhanku_cd, TENHANKU_CD_MAX_LENGTH);
	}
	public String getTenhankuCdString() {
		return "'" + cutString(this.tenhanku_cd, TENHANKU_CD_MAX_LENGTH) + "'";
	}
	public String getTenhankuCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.tenhanku_cd, TENHANKU_CD_MAX_LENGTH));
	}

	// keijyogo_syusei_bitに対するセッターとゲッターの集合
	public boolean setKeijyogoSyuseiBit(String keijyogo_syusei_bit) {
		try {
			this.keijyogo_syusei_bit = Long.parseLong(keijyogo_syusei_bit);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean setKeijyogoSyuseiBit(long keijyogo_syusei_bit) {
		this.keijyogo_syusei_bit = keijyogo_syusei_bit;
		return true;
	}
	public long getKeijyogoSyuseiBit() {
		return this.keijyogo_syusei_bit;
	}
	public String getKeijyogoSyuseiBitString() {
		return Long.toString(this.keijyogo_syusei_bit);
	}

	// br_denpyo_syubetu_kbに対するセッターとゲッターの集合
	public boolean setBrDenpyoSyubetuKb(String br_denpyo_syubetu_kb) {
		this.br_denpyo_syubetu_kb = br_denpyo_syubetu_kb;
		return true;
	}
	public String getBrDenpyoSyubetuKb() {
		return cutString(this.br_denpyo_syubetu_kb, BR_DENPYO_SYUBETU_KB_MAX_LENGTH);
	}
	public String getBrDenpyoSyubetuKbString() {
		return "'" + cutString(this.br_denpyo_syubetu_kb, BR_DENPYO_SYUBETU_KB_MAX_LENGTH) + "'";
	}
	public String getBrDenpyoSyubetuKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.br_denpyo_syubetu_kb, BR_DENPYO_SYUBETU_KB_MAX_LENGTH));
	}

	// denpyo_kbに対するセッターとゲッターの集合
	public boolean setDenpyoKb(String denpyo_kb) {
		this.denpyo_kb = denpyo_kb;
		return true;
	}
	public String getDenpyoKb() {
		return cutString(this.denpyo_kb, DENPYO_KB_MAX_LENGTH);
	}
	public String getDenpyoKbString() {
		return "'" + cutString(this.denpyo_kb, DENPYO_KB_MAX_LENGTH) + "'";
	}
	public String getDenpyoKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.denpyo_kb, DENPYO_KB_MAX_LENGTH));
	}

	// torihikisaki_cdに対するセッターとゲッターの集合
	public boolean setTorihikisakiCd(String torihikisaki_cd) {
		this.torihikisaki_cd = torihikisaki_cd;
		return true;
	}
	public String getTorihikisakiCd() {
		return cutString(this.torihikisaki_cd, TORIHIKISAKI_CD_MAX_LENGTH);
	}
	public String getTorihikisakiCdString() {
		return "'" + cutString(this.torihikisaki_cd, TORIHIKISAKI_CD_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.torihikisaki_cd, TORIHIKISAKI_CD_MAX_LENGTH));
	}

	// eda_nbに対するセッターとゲッターの集合
	public boolean setEdaNb(String eda_nb) {
		try {
			this.eda_nb = Long.parseLong(eda_nb);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean setEdaNb(long eda_nb) {
		this.eda_nb = eda_nb;
		return true;
	}
	public long getEdaNb() {
		return this.eda_nb;
	}
	public String getEdaNbString() {
		return Long.toString(this.eda_nb);
	}

	// denpyo_nbに対するセッターとゲッターの集合
	public boolean setDenpyoNb(String denpyo_nb) {
		try {
			this.denpyo_nb = Long.parseLong(denpyo_nb);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean setDenpyoNb(long denpyo_nb) {
		this.denpyo_nb = denpyo_nb;
		return true;
	}
	public long getDenpyoNb() {
		return this.denpyo_nb;
	}
	public String getDenpyoNbString() {
		return Long.toString(this.denpyo_nb);
	}

	// chumonsyo_nbに対するセッターとゲッターの集合
	public boolean setChumonsyoNb(String chumonsyo_nb) {
		try {
			this.chumonsyo_nb = Long.parseLong(chumonsyo_nb);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean setChumonsyoNb(long chumonsyo_nb) {
		this.chumonsyo_nb = chumonsyo_nb;
		return true;
	}
	public long getChumonsyoNb() {
		return this.chumonsyo_nb;
	}
	public String getChumonsyoNbString() {
		return Long.toString(this.chumonsyo_nb);
	}

	// kosei_gyosuに対するセッターとゲッターの集合
	public boolean setKoseiGyosu(String kosei_gyosu) {
		try {
			this.kosei_gyosu = Long.parseLong(kosei_gyosu);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean setKoseiGyosu(long kosei_gyosu) {
		this.kosei_gyosu = kosei_gyosu;
		return true;
	}
	public long getKoseiGyosu() {
		return this.kosei_gyosu;
	}
	public String getKoseiGyosuString() {
		return Long.toString(this.kosei_gyosu);
	}

	// genka_kei_vlに対するセッターとゲッターの集合
	public boolean setGenkaKeiVl(String genka_kei_vl) {
		try {
			this.genka_kei_vl = Long.parseLong(genka_kei_vl);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean setGenkaKeiVl(long genka_kei_vl) {
		this.genka_kei_vl = genka_kei_vl;
		return true;
	}
	public long getGenkaKeiVl() {
		return this.genka_kei_vl;
	}
	public String getGenkaKeiVlString() {
		return Long.toString(this.genka_kei_vl);
	}

	// baika_kei_vlに対するセッターとゲッターの集合
	public boolean setBaikaKeiVl(String baika_kei_vl) {
		try {
			this.baika_kei_vl = Long.parseLong(baika_kei_vl);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean setBaikaKeiVl(long baika_kei_vl) {
		this.baika_kei_vl = baika_kei_vl;
		return true;
	}
	public long getBaikaKeiVl() {
		return this.baika_kei_vl;
	}
	public String getBaikaKeiVlString() {
		return Long.toString(this.baika_kei_vl);
	}

	// hachu_dtに対するセッターとゲッターの集合
	public boolean setHachuDt(String hachu_dt) {
		this.hachu_dt = hachu_dt;
		return true;
	}
	public String getHachuDt() {
		return cutString(this.hachu_dt, HACHU_DT_MAX_LENGTH);
	}
	public String getHachuDtString() {
		return "'" + cutString(this.hachu_dt, HACHU_DT_MAX_LENGTH) + "'";
	}
	public String getHachuDtHTMLString() {
		return HTMLStringUtil.convert(cutString(this.hachu_dt, HACHU_DT_MAX_LENGTH));
	}

	// center_keijo_dtに対するセッターとゲッターの集合
	public boolean setCenterKeijoDt(String center_keijo_dt) {
		this.center_keijo_dt = center_keijo_dt;
		return true;
	}
	public String getCenterKeijoDt() {
		return cutString(this.center_keijo_dt, CENTER_KEIJO_DT_MAX_LENGTH);
	}
	public String getCenterKeijoDtString() {
		return "'" + cutString(this.center_keijo_dt, CENTER_KEIJO_DT_MAX_LENGTH) + "'";
	}
	public String getCenterKeijoDtHTMLString() {
		return HTMLStringUtil.convert(cutString(this.center_keijo_dt, CENTER_KEIJO_DT_MAX_LENGTH));
	}

	// aite_tenpo_cdに対するセッターとゲッターの集合
	public boolean setAiteTenpoCd(String aite_tenpo_cd) {
		this.aite_tenpo_cd = aite_tenpo_cd;
		return true;
	}
	public String getAiteTenpoCd() {
		return cutString(this.aite_tenpo_cd, AITE_TENPO_CD_MAX_LENGTH);
	}
	public String getAiteTenpoCdString() {
		return "'" + cutString(this.aite_tenpo_cd, AITE_TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getAiteTenpoCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.aite_tenpo_cd, AITE_TENPO_CD_MAX_LENGTH));
	}

	// aite_bumon_cdに対するセッターとゲッターの集合
	public boolean setAiteBumonCd(String aite_bumon_cd) {
		this.aite_bumon_cd = aite_bumon_cd;
		return true;
	}
	public String getAiteBumonCd() {
		return cutString(this.aite_bumon_cd, AITE_BUMON_CD_MAX_LENGTH);
	}
	public String getAiteBumonCdString() {
		return "'" + cutString(this.aite_bumon_cd, AITE_BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getAiteBumonCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.aite_bumon_cd, AITE_BUMON_CD_MAX_LENGTH));
	}

	// br_center_cdに対するセッターとゲッターの集合
	public boolean setBrCenterCd(String br_center_cd) {
		this.br_center_cd = br_center_cd;
		return true;
	}
	public String getBrCenterCd() {
		return cutString(this.br_center_cd, BR_CENTER_CD_MAX_LENGTH);
	}
	public String getBrCenterCdString() {
		return "'" + cutString(this.br_center_cd, BR_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getBrCenterCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.br_center_cd, BR_CENTER_CD_MAX_LENGTH));
	}

	// br_baitai_kbに対するセッターとゲッターの集合
	public boolean setBrBaitaiKb(String br_baitai_kb) {
		this.br_baitai_kb = br_baitai_kb;
		return true;
	}
	public String getBrBaitaiKb() {
		return cutString(this.br_baitai_kb, BR_BAITAI_KB_MAX_LENGTH);
	}
	public String getBrBaitaiKbString() {
		return "'" + cutString(this.br_baitai_kb, BR_BAITAI_KB_MAX_LENGTH) + "'";
	}
	public String getBrBaitaiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.br_baitai_kb, BR_BAITAI_KB_MAX_LENGTH));
	}

	// jet_kbに対するセッターとゲッターの集合
	public boolean setJetKb(String jet_kb) {
		this.jet_kb = jet_kb;
		return true;
	}
	public String getJetKb() {
		return cutString(this.jet_kb, JET_KB_MAX_LENGTH);
	}
	public String getJetKbString() {
		return "'" + cutString(this.jet_kb, JET_KB_MAX_LENGTH) + "'";
	}
	public String getJetKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.jet_kb, JET_KB_MAX_LENGTH));
	}

	// kesikomi_center_cdに対するセッターとゲッターの集合
	public boolean setKesikomiCenterCd(String kesikomi_center_cd) {
		this.kesikomi_center_cd = kesikomi_center_cd;
		return true;
	}
	public String getKesikomiCenterCd() {
		return cutString(this.kesikomi_center_cd, KESIKOMI_CENTER_CD_MAX_LENGTH);
	}
	public String getKesikomiCenterCdString() {
		return "'" + cutString(this.kesikomi_center_cd, KESIKOMI_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getKesikomiCenterCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.kesikomi_center_cd, KESIKOMI_CENTER_CD_MAX_LENGTH));
	}

	// denpyo_kihyosya_naに対するセッターとゲッターの集合
	public boolean setDenpyoKihyosyaNa(String denpyo_kihyosya_na) {
		this.denpyo_kihyosya_na = denpyo_kihyosya_na;
		return true;
	}
	public String getDenpyoKihyosyaNa() {
		return cutString(this.denpyo_kihyosya_na, DENPYO_KIHYOSYA_NA_MAX_LENGTH);
	}
	public String getDenpyoKihyosyaNaString() {
		return "'" + cutString(this.denpyo_kihyosya_na, DENPYO_KIHYOSYA_NA_MAX_LENGTH) + "'";
	}
	public String getDenpyoKihyosyaNaHTMLString() {
		return HTMLStringUtil.convert(cutString(this.denpyo_kihyosya_na, DENPYO_KIHYOSYA_NA_MAX_LENGTH));
	}

	// sekininsya_naに対するセッターとゲッターの集合
	public boolean setSekininsyaNa(String sekininsya_na) {
		this.sekininsya_na = sekininsya_na;
		return true;
	}
	public String getSekininsyaNa() {
		return cutString(this.sekininsya_na, SEKININSYA_NA_MAX_LENGTH);
	}
	public String getSekininsyaNaString() {
		return "'" + cutString(this.sekininsya_na, SEKININSYA_NA_MAX_LENGTH) + "'";
	}
	public String getSekininsyaNaHTMLString() {
		return HTMLStringUtil.convert(cutString(this.sekininsya_na, SEKININSYA_NA_MAX_LENGTH));
	}

	// siharai_kibo_dtに対するセッターとゲッターの集合
	public boolean setSiharaiKiboDt(String siharai_kibo_dt) {
		this.siharai_kibo_dt = siharai_kibo_dt;
		return true;
	}
	public String getSiharaiKiboDt() {
		return cutString(this.siharai_kibo_dt, SIHARAI_KIBO_DT_MAX_LENGTH);
	}
	public String getSiharaiKiboDtString() {
		return "'" + cutString(this.siharai_kibo_dt, SIHARAI_KIBO_DT_MAX_LENGTH) + "'";
	}
	public String getSiharaiKiboDtHTMLString() {
		return HTMLStringUtil.convert(cutString(this.siharai_kibo_dt, SIHARAI_KIBO_DT_MAX_LENGTH));
	}

	// record_nbに対するセッターとゲッターの集合
	public boolean setRecordNb(String record_nb) {
		this.record_nb = record_nb;
		return true;
	}
	public String getRecordNb() {
		return cutString(this.record_nb, RECORD_NB_MAX_LENGTH);
	}
	public String getRecordNbString() {
		return "'" + cutString(this.record_nb, RECORD_NB_MAX_LENGTH) + "'";
	}
	public String getRecordNbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.record_nb, RECORD_NB_MAX_LENGTH));
	}

	// machiuke_fgに対するセッターとゲッターの集合
	public boolean setMachiukeFg(String machiuke_fg) {
		this.machiuke_fg = machiuke_fg;
		return true;
	}
	public String getMachiukeFg() {
		return cutString(this.machiuke_fg, MACHIUKE_FG_MAX_LENGTH);
	}
	public String getMachiukeFgString() {
		return "'" + cutString(this.machiuke_fg, MACHIUKE_FG_MAX_LENGTH) + "'";
	}
	public String getMachiukeFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.machiuke_fg, MACHIUKE_FG_MAX_LENGTH));
	}

	// por_kbに対するセッターとゲッターの集合
	public boolean setPorKb(String por_kb) {
		this.por_kb = por_kb;
		return true;
	}
	public String getPorKb() {
		return cutString(this.por_kb, POR_KB_MAX_LENGTH);
	}
	public String getPorKbString() {
		return "'" + cutString(this.por_kb, POR_KB_MAX_LENGTH) + "'";
	}
	public String getPorKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.por_kb, POR_KB_MAX_LENGTH));
	}

	// bin_kbに対するセッターとゲッターの集合
	public boolean setBinKb(String bin_kb) {
		this.bin_kb = bin_kb;
		return true;
	}
	public String getBinKb() {
		return cutString(this.bin_kb, BIN_KB_MAX_LENGTH);
	}
	public String getBinKbString() {
		return "'" + cutString(this.bin_kb, BIN_KB_MAX_LENGTH) + "'";
	}
	public String getBinKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.bin_kb, BIN_KB_MAX_LENGTH));
	}

	// kaisya_cdに対するセッターとゲッターの集合
	public boolean setKaisyaCd(String kaisya_cd) {
		this.kaisya_cd = kaisya_cd;
		return true;
	}
	public String getKaisyaCd() {
		return cutString(this.kaisya_cd, KAISYA_CD_MAX_LENGTH);
	}
	public String getKaisyaCdString() {
		return "'" + cutString(this.kaisya_cd, KAISYA_CD_MAX_LENGTH) + "'";
	}
	public String getKaisyaCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.kaisya_cd, KAISYA_CD_MAX_LENGTH));
	}

	// asn_taio_lvに対するセッターとゲッターの集合
	public boolean setAsnTaioLv(String asn_taio_lv) {
		this.asn_taio_lv = asn_taio_lv;
		return true;
	}
	public String getAsnTaioLv() {
		return cutString(this.asn_taio_lv, ASN_TAIO_LV_MAX_LENGTH);
	}
	public String getAsnTaioLvString() {
		return "'" + cutString(this.asn_taio_lv, ASN_TAIO_LV_MAX_LENGTH) + "'";
	}
	public String getAsnTaioLvHTMLString() {
		return HTMLStringUtil.convert(cutString(this.asn_taio_lv, ASN_TAIO_LV_MAX_LENGTH));
	}

	// matehan_no_kenpin_fgに対するセッターとゲッターの集合
	public boolean setMatehanNoKenpinFg(String matehan_no_kenpin_fg) {
		this.matehan_no_kenpin_fg = matehan_no_kenpin_fg;
		return true;
	}
	public String getMatehanNoKenpinFg() {
		return cutString(this.matehan_no_kenpin_fg, MATEHAN_NO_KENPIN_FG_MAX_LENGTH);
	}
	public String getMatehanNoKenpinFgString() {
		return "'" + cutString(this.matehan_no_kenpin_fg, MATEHAN_NO_KENPIN_FG_MAX_LENGTH) + "'";
	}
	public String getMatehanNoKenpinFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.matehan_no_kenpin_fg, MATEHAN_NO_KENPIN_FG_MAX_LENGTH));
	}

	// center_sagyo_dtに対するセッターとゲッターの集合
	public boolean setCenterSagyoDt(String center_sagyo_dt) {
		this.center_sagyo_dt = center_sagyo_dt;
		return true;
	}
	public String getCenterSagyoDt() {
		return cutString(this.center_sagyo_dt, CENTER_SAGYO_DT_MAX_LENGTH);
	}
	public String getCenterSagyoDtString() {
		return "'" + cutString(this.center_sagyo_dt, CENTER_SAGYO_DT_MAX_LENGTH) + "'";
	}
	public String getCenterSagyoDtHTMLString() {
		return HTMLStringUtil.convert(cutString(this.center_sagyo_dt, CENTER_SAGYO_DT_MAX_LENGTH));
	}

	// nohin_dtに対するセッターとゲッターの集合
	public boolean setNohinDt(String nohin_dt) {
		this.nohin_dt = nohin_dt;
		return true;
	}
	public String getNohinDt() {
		return cutString(this.nohin_dt, NOHIN_DT_MAX_LENGTH);
	}
	public String getNohinDtString() {
		return "'" + cutString(this.nohin_dt, NOHIN_DT_MAX_LENGTH) + "'";
	}
	public String getNohinDtHTMLString() {
		return HTMLStringUtil.convert(cutString(this.nohin_dt, NOHIN_DT_MAX_LENGTH));
	}

	// scm_johoに対するセッターとゲッターの集合
	public boolean setScmJoho(String scm_joho) {
		this.scm_joho = scm_joho;
		return true;
	}
	public String getScmJoho() {
		return cutString(this.scm_joho, SCM_JOHO_MAX_LENGTH);
	}
	public String getScmJohoString() {
		return "'" + cutString(this.scm_joho, SCM_JOHO_MAX_LENGTH) + "'";
	}
	public String getScmJohoHTMLString() {
		return HTMLStringUtil.convert(cutString(this.scm_joho, SCM_JOHO_MAX_LENGTH));
	}

	// sample_tenpoに対するセッターとゲッターの集合
	public boolean setSampleTenpo(String sample_tenpo) {
		this.sample_tenpo = sample_tenpo;
		return true;
	}
	public String getSampleTenpo() {
		return cutString(this.sample_tenpo, SAMPLE_TENPO_MAX_LENGTH);
	}
	public String getSampleTenpoString() {
		return "'" + cutString(this.sample_tenpo, SAMPLE_TENPO_MAX_LENGTH) + "'";
	}
	public String getSampleTenpoHTMLString() {
		return HTMLStringUtil.convert(cutString(this.sample_tenpo, SAMPLE_TENPO_MAX_LENGTH));
	}

	// siire_keijo_make_fgに対するセッターとゲッターの集合
	public boolean setSiireKeijoMakeFg(String siire_keijo_make_fg) {
		this.siire_keijo_make_fg = siire_keijo_make_fg;
		return true;
	}
	public String getSiireKeijoMakeFg() {
		return cutString(this.siire_keijo_make_fg, SIIRE_KEIJO_MAKE_FG_MAX_LENGTH);
	}
	public String getSiireKeijoMakeFgString() {
		return "'" + cutString(this.siire_keijo_make_fg, SIIRE_KEIJO_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getSiireKeijoMakeFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.siire_keijo_make_fg, SIIRE_KEIJO_MAKE_FG_MAX_LENGTH));
	}

	// siire_keijo_make_tsに対するセッターとゲッターの集合
	public boolean setSiireKeijoMakeTs(String siire_keijo_make_ts) {
		this.siire_keijo_make_ts = siire_keijo_make_ts;
		return true;
	}
	public String getSiireKeijoMakeTs() {
		return cutString(this.siire_keijo_make_ts, SIIRE_KEIJO_MAKE_TS_MAX_LENGTH);
	}
	public String getSiireKeijoMakeTsString() {
		return "'" + cutString(this.siire_keijo_make_ts, SIIRE_KEIJO_MAKE_TS_MAX_LENGTH) + "'";
	}
	public String getSiireKeijoMakeTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.siire_keijo_make_ts, SIIRE_KEIJO_MAKE_TS_MAX_LENGTH));
	}

	// host_keijo_sumi_fgに対するセッターとゲッターの集合
	public boolean setHostKeijoSumiFg(String host_keijo_sumi_fg) {
		this.host_keijo_sumi_fg = host_keijo_sumi_fg;
		return true;
	}
	public String getHostKeijoSumiFg() {
		return cutString(this.host_keijo_sumi_fg, HOST_KEIJO_SUMI_FG_MAX_LENGTH);
	}
	public String getHostKeijoSumiFgString() {
		return "'" + cutString(this.host_keijo_sumi_fg, HOST_KEIJO_SUMI_FG_MAX_LENGTH) + "'";
	}
	public String getHostKeijoSumiFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.host_keijo_sumi_fg, HOST_KEIJO_SUMI_FG_MAX_LENGTH));
	}

	// host_keijo_error_cdに対するセッターとゲッターの集合
	public boolean setHostKeijoErrorCd(String host_keijo_error_cd) {
		this.host_keijo_error_cd = host_keijo_error_cd;
		return true;
	}
	public String getHostKeijoErrorCd() {
		return cutString(this.host_keijo_error_cd, HOST_KEIJO_ERROR_CD_MAX_LENGTH);
	}
	public String getHostKeijoErrorCdString() {
		return "'" + cutString(this.host_keijo_error_cd, HOST_KEIJO_ERROR_CD_MAX_LENGTH) + "'";
	}
	public String getHostKeijoErrorCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.host_keijo_error_cd, HOST_KEIJO_ERROR_CD_MAX_LENGTH));
	}

	// keijo_keka_make_fgに対するセッターとゲッターの集合
	public boolean setKeijoKekaMakeFg(String keijo_keka_make_fg) {
		this.keijo_keka_make_fg = keijo_keka_make_fg;
		return true;
	}
	public String getKeijoKekaMakeFg() {
		return cutString(this.keijo_keka_make_fg, KEIJO_KEKA_MAKE_FG_MAX_LENGTH);
	}
	public String getKeijoKekaMakeFgString() {
		return "'" + cutString(this.keijo_keka_make_fg, KEIJO_KEKA_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getKeijoKekaMakeFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.keijo_keka_make_fg, KEIJO_KEKA_MAKE_FG_MAX_LENGTH));
	}

	// keijo_keka_make_tsに対するセッターとゲッターの集合
	public boolean setKeijoKekaMakeTs(String keijo_keka_make_ts) {
		this.keijo_keka_make_ts = keijo_keka_make_ts;
		return true;
	}
	public String getKeijoKekaMakeTs() {
		return cutString(this.keijo_keka_make_ts, KEIJO_KEKA_MAKE_TS_MAX_LENGTH);
	}
	public String getKeijoKekaMakeTsString() {
		return "'" + cutString(this.keijo_keka_make_ts, KEIJO_KEKA_MAKE_TS_MAX_LENGTH) + "'";
	}
	public String getKeijoKekaMakeTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.keijo_keka_make_ts, KEIJO_KEKA_MAKE_TS_MAX_LENGTH));
	}

	// tenpo_nonyu_make_fgに対するセッターとゲッターの集合
	public boolean setTenpoNonyuMakeFg(String tenpo_nonyu_make_fg) {
		this.tenpo_nonyu_make_fg = tenpo_nonyu_make_fg;
		return true;
	}
	public String getTenpoNonyuMakeFg() {
		return cutString(this.tenpo_nonyu_make_fg, TENPO_NONYU_MAKE_FG_MAX_LENGTH);
	}
	public String getTenpoNonyuMakeFgString() {
		return "'" + cutString(this.tenpo_nonyu_make_fg, TENPO_NONYU_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getTenpoNonyuMakeFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.tenpo_nonyu_make_fg, TENPO_NONYU_MAKE_FG_MAX_LENGTH));
	}

	// tenpo_nonyu_make_tsに対するセッターとゲッターの集合
	public boolean setTenpoNonyuMakeTs(String tenpo_nonyu_make_ts) {
		this.tenpo_nonyu_make_ts = tenpo_nonyu_make_ts;
		return true;
	}
	public String getTenpoNonyuMakeTs() {
		return cutString(this.tenpo_nonyu_make_ts, TENPO_NONYU_MAKE_TS_MAX_LENGTH);
	}
	public String getTenpoNonyuMakeTsString() {
		return "'" + cutString(this.tenpo_nonyu_make_ts, TENPO_NONYU_MAKE_TS_MAX_LENGTH) + "'";
	}
	public String getTenpoNonyuMakeTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.tenpo_nonyu_make_ts, TENPO_NONYU_MAKE_TS_MAX_LENGTH));
	}

	// mino_chino_make_fgに対するセッターとゲッターの集合
	public boolean setMinoChinoMakeFg(String mino_chino_make_fg) {
		this.mino_chino_make_fg = mino_chino_make_fg;
		return true;
	}
	public String getMinoChinoMakeFg() {
		return cutString(this.mino_chino_make_fg, MINO_CHINO_MAKE_FG_MAX_LENGTH);
	}
	public String getMinoChinoMakeFgString() {
		return "'" + cutString(this.mino_chino_make_fg, MINO_CHINO_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getMinoChinoMakeFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.mino_chino_make_fg, MINO_CHINO_MAKE_FG_MAX_LENGTH));
	}

	// mino_chino_make_tsに対するセッターとゲッターの集合
	public boolean setMinoChinoMakeTs(String mino_chino_make_ts) {
		this.mino_chino_make_ts = mino_chino_make_ts;
		return true;
	}
	public String getMinoChinoMakeTs() {
		return cutString(this.mino_chino_make_ts, MINO_CHINO_MAKE_TS_MAX_LENGTH);
	}
	public String getMinoChinoMakeTsString() {
		return "'" + cutString(this.mino_chino_make_ts, MINO_CHINO_MAKE_TS_MAX_LENGTH) + "'";
	}
	public String getMinoChinoMakeTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.mino_chino_make_ts, MINO_CHINO_MAKE_TS_MAX_LENGTH));
	}

	// bupin_jyuryo_make_fgに対するセッターとゲッターの集合
	public boolean setBupinJyuryoMakeFg(String bupin_jyuryo_make_fg) {
		this.bupin_jyuryo_make_fg = bupin_jyuryo_make_fg;
		return true;
	}
	public String getBupinJyuryoMakeFg() {
		return cutString(this.bupin_jyuryo_make_fg, BUPIN_JYURYO_MAKE_FG_MAX_LENGTH);
	}
	public String getBupinJyuryoMakeFgString() {
		return "'" + cutString(this.bupin_jyuryo_make_fg, BUPIN_JYURYO_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getBupinJyuryoMakeFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.bupin_jyuryo_make_fg, BUPIN_JYURYO_MAKE_FG_MAX_LENGTH));
	}

	// bupin_jyuryo_make_tsに対するセッターとゲッターの集合
	public boolean setBupinJyuryoMakeTs(String bupin_jyuryo_make_ts) {
		this.bupin_jyuryo_make_ts = bupin_jyuryo_make_ts;
		return true;
	}
	public String getBupinJyuryoMakeTs() {
		return cutString(this.bupin_jyuryo_make_ts, BUPIN_JYURYO_MAKE_TS_MAX_LENGTH);
	}
	public String getBupinJyuryoMakeTsString() {
		return "'" + cutString(this.bupin_jyuryo_make_ts, BUPIN_JYURYO_MAKE_TS_MAX_LENGTH) + "'";
	}
	public String getBupinJyuryoMakeTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.bupin_jyuryo_make_ts, BUPIN_JYURYO_MAKE_TS_MAX_LENGTH));
	}

	// center_fee_make_fgに対するセッターとゲッターの集合
	public boolean setCenterFeeMakeFg(String center_fee_make_fg) {
		this.center_fee_make_fg = center_fee_make_fg;
		return true;
	}
	public String getCenterFeeMakeFg() {
		return cutString(this.center_fee_make_fg, CENTER_FEE_MAKE_FG_MAX_LENGTH);
	}
	public String getCenterFeeMakeFgString() {
		return "'" + cutString(this.center_fee_make_fg, CENTER_FEE_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getCenterFeeMakeFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.center_fee_make_fg, CENTER_FEE_MAKE_FG_MAX_LENGTH));
	}

	// center_fee_make_tsに対するセッターとゲッターの集合
	public boolean setCenterFeeMakeTs(String center_fee_make_ts) {
		this.center_fee_make_ts = center_fee_make_ts;
		return true;
	}
	public String getCenterFeeMakeTs() {
		return cutString(this.center_fee_make_ts, CENTER_FEE_MAKE_TS_MAX_LENGTH);
	}
	public String getCenterFeeMakeTsString() {
		return "'" + cutString(this.center_fee_make_ts, CENTER_FEE_MAKE_TS_MAX_LENGTH) + "'";
	}
	public String getCenterFeeMakeTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.center_fee_make_ts, CENTER_FEE_MAKE_TS_MAX_LENGTH));
	}

	// riyo_user_idに対するセッターとゲッターの集合
	public boolean setRiyoUserId(String riyo_user_id) {
		this.riyo_user_id = riyo_user_id;
		return true;
	}
	public String getRiyoUserId() {
		return cutString(this.riyo_user_id, RIYO_USER_ID_MAX_LENGTH);
	}
	public String getRiyoUserIdString() {
		return "'" + cutString(this.riyo_user_id, RIYO_USER_ID_MAX_LENGTH) + "'";
	}
	public String getRiyoUserIdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.riyo_user_id, RIYO_USER_ID_MAX_LENGTH));
	}

	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts) {
		this.insert_ts = insert_ts;
		return true;
	}
	public String getInsertTs() {
		return cutString(this.insert_ts, INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsString() {
		return "'" + cutString(this.insert_ts, INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getInsertTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.insert_ts, INSERT_TS_MAX_LENGTH));
	}

	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdateTs(String update_ts) {
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdateTs() {
		return cutString(this.update_ts, UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString() {
		return "'" + cutString(this.update_ts, UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdateTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.update_ts, UPDATE_TS_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString() {
		return "  record_kb = "
			+ getRecordKbString()
			+ "  standard_job_na = "
			+ getStandardJobNaString()
			+ "  syori_md = "
			+ getSyoriMdString()
			+ "  goki = "
			+ getGokiString()
			+ "  batch_nb = "
			+ getBatchNbString()
			+ "  data_gyo_nb = "
			+ getDataGyoNbString()
			+ "  tenpo_cd = "
			+ getTenpoCdString()
			+ "  tenhanku_cd = "
			+ getTenhankuCdString()
			+ "  keijyogo_syusei_bit = "
			+ getKeijyogoSyuseiBitString()
			+ "  br_denpyo_syubetu_kb = "
			+ getBrDenpyoSyubetuKbString()
			+ "  denpyo_kb = "
			+ getDenpyoKbString()
			+ "  torihikisaki_cd = "
			+ getTorihikisakiCdString()
			+ "  eda_nb = "
			+ getEdaNbString()
			+ "  denpyo_nb = "
			+ getDenpyoNbString()
			+ "  chumonsyo_nb = "
			+ getChumonsyoNbString()
			+ "  kosei_gyosu = "
			+ getKoseiGyosuString()
			+ "  genka_kei_vl = "
			+ getGenkaKeiVlString()
			+ "  baika_kei_vl = "
			+ getBaikaKeiVlString()
			+ "  hachu_dt = "
			+ getHachuDtString()
			+ "  center_keijo_dt = "
			+ getCenterKeijoDtString()
			+ "  aite_tenpo_cd = "
			+ getAiteTenpoCdString()
			+ "  aite_bumon_cd = "
			+ getAiteBumonCdString()
			+ "  br_center_cd = "
			+ getBrCenterCdString()
			+ "  br_baitai_kb = "
			+ getBrBaitaiKbString()
			+ "  jet_kb = "
			+ getJetKbString()
			+ "  kesikomi_center_cd = "
			+ getKesikomiCenterCdString()
			+ "  denpyo_kihyosya_na = "
			+ getDenpyoKihyosyaNaString()
			+ "  sekininsya_na = "
			+ getSekininsyaNaString()
			+ "  siharai_kibo_dt = "
			+ getSiharaiKiboDtString()
			+ "  record_nb = "
			+ getRecordNbString()
			+ "  machiuke_fg = "
			+ getMachiukeFgString()
			+ "  por_kb = "
			+ getPorKbString()
			+ "  bin_kb = "
			+ getBinKbString()
			+ "  kaisya_cd = "
			+ getKaisyaCdString()
			+ "  asn_taio_lv = "
			+ getAsnTaioLvString()
			+ "  matehan_no_kenpin_fg = "
			+ getMatehanNoKenpinFgString()
			+ "  center_sagyo_dt = "
			+ getCenterSagyoDtString()
			+ "  nohin_dt = "
			+ getNohinDtString()
			+ "  scm_joho = "
			+ getScmJohoString()
			+ "  sample_tenpo = "
			+ getSampleTenpoString()
			+ "  siire_keijo_make_fg = "
			+ getSiireKeijoMakeFgString()
			+ "  siire_keijo_make_ts = "
			+ getSiireKeijoMakeTsString()
			+ "  host_keijo_sumi_fg = "
			+ getHostKeijoSumiFgString()
			+ "  host_keijo_error_cd = "
			+ getHostKeijoErrorCdString()
			+ "  keijo_keka_make_fg = "
			+ getKeijoKekaMakeFgString()
			+ "  keijo_keka_make_ts = "
			+ getKeijoKekaMakeTsString()
			+ "  tenpo_nonyu_make_fg = "
			+ getTenpoNonyuMakeFgString()
			+ "  tenpo_nonyu_make_ts = "
			+ getTenpoNonyuMakeTsString()
			+ "  mino_chino_make_fg = "
			+ getMinoChinoMakeFgString()
			+ "  mino_chino_make_ts = "
			+ getMinoChinoMakeTsString()
			+ "  bupin_jyuryo_make_fg = "
			+ getBupinJyuryoMakeFgString()
			+ "  bupin_jyuryo_make_ts = "
			+ getBupinJyuryoMakeTsString()
			+ "  center_fee_make_fg = "
			+ getCenterFeeMakeFgString()
			+ "  center_fee_make_ts = "
			+ getCenterFeeMakeTsString()
			+ "  riyo_user_id = "
			+ getRiyoUserIdString()
			+ "  insert_ts = "
			+ getInsertTsString()
			+ "  update_ts = "
			+ getUpdateTsString()
			+ " createDatabase  = "
			+ createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return BrDtNohinBean コピー後のクラス
	 */
	public BrDtNohinBean createClone() {
		BrDtNohinBean bean = new BrDtNohinBean();
		bean.setRecordKb(this.record_kb);
		bean.setStandardJobNa(this.standard_job_na);
		bean.setSyoriMd(this.syori_md);
		bean.setGoki(this.goki);
		bean.setBatchNb(this.batch_nb);
		bean.setDataGyoNb(this.data_gyo_nb);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenhankuCd(this.tenhanku_cd);
		bean.setKeijyogoSyuseiBit(this.keijyogo_syusei_bit);
		bean.setBrDenpyoSyubetuKb(this.br_denpyo_syubetu_kb);
		bean.setDenpyoKb(this.denpyo_kb);
		bean.setTorihikisakiCd(this.torihikisaki_cd);
		bean.setEdaNb(this.eda_nb);
		bean.setDenpyoNb(this.denpyo_nb);
		bean.setChumonsyoNb(this.chumonsyo_nb);
		bean.setKoseiGyosu(this.kosei_gyosu);
		bean.setGenkaKeiVl(this.genka_kei_vl);
		bean.setBaikaKeiVl(this.baika_kei_vl);
		bean.setHachuDt(this.hachu_dt);
		bean.setCenterKeijoDt(this.center_keijo_dt);
		bean.setAiteTenpoCd(this.aite_tenpo_cd);
		bean.setAiteBumonCd(this.aite_bumon_cd);
		bean.setBrCenterCd(this.br_center_cd);
		bean.setBrBaitaiKb(this.br_baitai_kb);
		bean.setJetKb(this.jet_kb);
		bean.setKesikomiCenterCd(this.kesikomi_center_cd);
		bean.setDenpyoKihyosyaNa(this.denpyo_kihyosya_na);
		bean.setSekininsyaNa(this.sekininsya_na);
		bean.setSiharaiKiboDt(this.siharai_kibo_dt);
		bean.setRecordNb(this.record_nb);
		bean.setMachiukeFg(this.machiuke_fg);
		bean.setPorKb(this.por_kb);
		bean.setBinKb(this.bin_kb);
		bean.setKaisyaCd(this.kaisya_cd);
		bean.setAsnTaioLv(this.asn_taio_lv);
		bean.setMatehanNoKenpinFg(this.matehan_no_kenpin_fg);
		bean.setCenterSagyoDt(this.center_sagyo_dt);
		bean.setNohinDt(this.nohin_dt);
		bean.setScmJoho(this.scm_joho);
		bean.setSampleTenpo(this.sample_tenpo);
		bean.setSiireKeijoMakeFg(this.siire_keijo_make_fg);
		bean.setSiireKeijoMakeTs(this.siire_keijo_make_ts);
		bean.setHostKeijoSumiFg(this.host_keijo_sumi_fg);
		bean.setHostKeijoErrorCd(this.host_keijo_error_cd);
		bean.setKeijoKekaMakeFg(this.keijo_keka_make_fg);
		bean.setKeijoKekaMakeTs(this.keijo_keka_make_ts);
		bean.setTenpoNonyuMakeFg(this.tenpo_nonyu_make_fg);
		bean.setTenpoNonyuMakeTs(this.tenpo_nonyu_make_ts);
		bean.setMinoChinoMakeFg(this.mino_chino_make_fg);
		bean.setMinoChinoMakeTs(this.mino_chino_make_ts);
		bean.setBupinJyuryoMakeFg(this.bupin_jyuryo_make_fg);
		bean.setBupinJyuryoMakeTs(this.bupin_jyuryo_make_ts);
		bean.setCenterFeeMakeFg(this.center_fee_make_fg);
		bean.setCenterFeeMakeTs(this.center_fee_make_ts);
		bean.setRiyoUserId(this.riyo_user_id);
		bean.setInsertTs(this.insert_ts);
		bean.setUpdateTs(this.update_ts);
		if (this.isCreateDatabase())
			bean.setCreateDatabase();
		return bean;
	}
	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof BrDtNohinBean))
			return false;
		return this.toString().equals(o.toString());
	}
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString(String base, int max) {
		if (base == null)
			return null;
		String wk = "";
		for (int pos = 0, count = 0; pos < max && pos < base.length(); pos++) {
			try {
				byte bt[] = base.substring(pos, pos + 1).getBytes("UTF-8");
				count += bt.length;
				if (count > max)
					break;
				wk += base.substring(pos, pos + 1);
			} catch (Exception e) {
				stcLog.getLog().debug(
					this.getClass().getName()
						+ "/cutString/"
						+ base
						+ "/"
						+ base.substring(pos, pos + 1)
						+ "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
