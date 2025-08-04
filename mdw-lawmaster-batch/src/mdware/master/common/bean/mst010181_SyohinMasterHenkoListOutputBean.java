package mdware.master.common.bean;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: 商品マスタ変更リストのレコード格納用クラス </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX Corp.</p>
 * @author O.Uemura
 * @version 3.0 (2013.12.20) 初版作成 ランドローム様対応
 * @version 3.1 (2015.12.04) Nhat.NgoM FIVImart様対応
 * @version 3.2 (2017.02.01) X.Liu #2799 FIVImart様対応
 */
public class mst010181_SyohinMasterHenkoListOutputBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	 public static final int GENERATION_MAX_LENGTH = 1;
	 public static final int SYOHIN_CD_MAX_LENGTH = 13;
	 public static final int BUNRUI1_CD_FOR_KAIPAGE_MAX_LENGTH = 2;
	 public static final int KB_MAX_LENGTH = 4;
	 public static final int YUKO_DT_MAX_LENGTH = 8;
	 public static final int BUNRUI1_CD_MAX_LENGTH = 2;
	 //public static final int BUNRUI5_CD_MAX_LENGTH = 5;
	 public static final int BUNRUI5_CD_MAX_LENGTH = 6;
	 public static final int KIKAKU_KANJI_2_NA_MAX_LENGTH = 40;
	 public static final int HINMEI_KANJI_NA_MAX_LENGTH = 80;
	 public static final int REC_HINMEI_KANJI_NA_MAX_LENGTH = 40;
	 public static final int HINMEI_KANA_NA_MAX_LENGTH = 80;
	 public static final int KIKAKU_KANJI_NA_MAX_LENGTH = 40;
	 public static final int BAITANKA_VL_MAX_LENGTH = 8;
	 public static final int BAIKA_HAISHIN_FG_MAX_LENGTH = 1;
	 public static final int GENTANKA_VL_MAX_LENGTH = 10;
	 public static final int SIIRESAKI_CD_MAX_LENGTH = 9;
	 public static final int HACHUTANI_IRISU_QT_MAX_LENGTH = 6;
	 public static final int EOS_KB_MAX_LENGTH = 1;
	 public static final int BIN_1_KB_MAX_LENGTH = 1;
	 public static final int BIN_2_KB_MAX_LENGTH = 1;
	 public static final int UPDATE_USER_ID_MAX_LENGTH = 20;
	 public static final int UPDATE_TS_MAX_LENGTH = 14;
	 public static final int SYSTEM_KB_MAX_LENGTH = 1;
	 public static final int GYOSYU_KB_MAX_LENGTH = 3;
	 public static final int SYOHIN_KB_MAX_LENGTH = 1;
	 public static final int BUNRUI2_CD_MAX_LENGTH = 5;
	 public static final int SYOHIN_2_CD_MAX_LENGTH = 13;
	 public static final int ZAIKO_SYOHIN_CD_MAX_LENGTH = 13;
	 public static final int JYOHO_SYOHIN_CD_MAX_LENGTH = 13;
	 public static final int KIKAKU_KANA_NA_MAX_LENGTH = 40;
	 public static final int KIKAKU_KANA_2_NA_MAX_LENGTH = 40;
	 public static final int REC_HINMEI_KANA_NA_MAX_LENGTH = 20;
	 public static final int SYOHIN_WIDTH_QT_MAX_LENGTH = 6;
	 public static final int SYOHIN_HEIGHT_QT_MAX_LENGTH = 6;
	 public static final int SYOHIN_DEPTH_QT_MAX_LENGTH = 6;
	 public static final int ZEI_KB_MAX_LENGTH = 1;
	 public static final int TAX_RATE_KB_MAX_LENGTH = 1;
	 public static final int GENTANKA_NUKI_VL_MAX_LENGTH = 10;
	 public static final int BAITANKA_NUKI_VL_MAX_LENGTH = 8;
	 public static final int MAKER_KIBO_KAKAKU_VL_MAX_LENGTH = 8;
	 public static final int HACYU_SYOHIN_KB_MAX_LENGTH = 1;
	 public static final int HACYU_SYOHIN_CD_MAX_LENGTH = 13;
	 public static final int HACHU_TANI_NA_MAX_LENGTH = 10;
	 public static final int HANBAI_TANI_MAX_LENGTH = 2;
	 public static final int MAX_HACHUTANI_QT_MAX_LENGTH = 6;
	 public static final int CASE_HACHU_KB_MAX_LENGTH = 1;
	 public static final int BARA_IRISU_QT_MAX_LENGTH = 6;
	 public static final int TEN_HACHU_ST_DT_MAX_LENGTH = 8;
	 public static final int TEN_HACHU_ED_DT_MAX_LENGTH = 8;
	 public static final int HANBAI_ST_DT_MAX_LENGTH = 8;
	 public static final int HANBAI_ED_DT_MAX_LENGTH = 8;
	 public static final int HANBAI_KIKAN_KB_MAX_LENGTH = 1;
	 public static final int TEIKAN_KB_MAX_LENGTH = 1;
	 public static final int SOBA_SYOHIN_KB_MAX_LENGTH = 1;
	 public static final int NOHIN_KIGEN_KB_MAX_LENGTH = 1;
	 public static final int NOHIN_KIGEN_QT_MAX_LENGTH = 4;
	 public static final int HACHU_PATTERN_1_KB_MAX_LENGTH = 1;
	 public static final int SIME_TIME_1_QT_MAX_LENGTH = 3;
	 public static final int C_NOHIN_RTIME_1_KB_MAX_LENGTH = 1;
	 public static final int TEN_NOHIN_RTIME_1_KB_MAX_LENGTH = 1;
	 public static final int TEN_NOHIN_TIME_1_KB_MAX_LENGTH = 1;
	 public static final int HACHU_PATTERN_2_KB_MAX_LENGTH = 1;
	 public static final int SIME_TIME_2_QT_MAX_LENGTH = 3;
	 public static final int C_NOHIN_RTIME_2_KB_MAX_LENGTH = 1;
	 public static final int TEN_NOHIN_RTIME_2_KB_MAX_LENGTH = 1;
	 public static final int TEN_NOHIN_TIME_2_KB_MAX_LENGTH = 1;
	 public static final int YUSEN_BIN_KB_MAX_LENGTH = 1;
	 public static final int F_BIN_KB_MAX_LENGTH = 1;
	 public static final int BUTURYU_KB_MAX_LENGTH = 2;
	 public static final int GOT_START_MM_MAX_LENGTH = 4;
	 public static final int GOT_END_MM_MAX_LENGTH = 4;
	 public static final int HACHU_TEISI_KB_MAX_LENGTH = 1;
	 public static final int CGC_HENPIN_KB_MAX_LENGTH = 1;
	 public static final int HANBAI_LIMIT_KB_MAX_LENGTH = 1;
	 public static final int HANBAI_LIMIT_QT_MAX_LENGTH = 4;
	 public static final int PLU_SEND_DT_MAX_LENGTH = 8;
	 public static final int KEYPLU_FG_MAX_LENGTH = 1;
	 public static final int PLU_KB_MAX_LENGTH = 1;
	 public static final int SYUZEI_HOKOKU_KB_MAX_LENGTH = 5;
	 public static final int SAKE_NAIYORYO_QT_MAX_LENGTH = 6;
	 public static final int TAG_HYOJI_BAIKA_VL_MAX_LENGTH = 7;
	 public static final int KESHI_BAIKA_VL_MAX_LENGTH = 8;
	 public static final int YORIDORI_KB_MAX_LENGTH = 1;
	 public static final int PC_KB_MAX_LENGTH = 1;
	 public static final int DAISI_NO_NB_MAX_LENGTH = 2;
	 public static final int UNIT_PRICE_TANI_KB_MAX_LENGTH = 2;
	 public static final int UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH = 6;
	 public static final int UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH = 5;
	 public static final int SYOHI_KIGEN_KB_MAX_LENGTH = 1;
	 public static final int SYOHI_KIGEN_DT_MAX_LENGTH = 4;
	 public static final int FREE_1_KB_MAX_LENGTH = 3;
	 public static final int FREE_2_KB_MAX_LENGTH = 3;
	 public static final int FREE_3_KB_MAX_LENGTH = 3;
	 public static final int FREE_4_KB_MAX_LENGTH = 3;
	 public static final int FREE_5_KB_MAX_LENGTH = 3;
	 public static final int COMMENT_1_TX_MAX_LENGTH = 50;
	 public static final int COMMENT_2_TX_MAX_LENGTH = 50;
	 public static final int FREE_CD_MAX_LENGTH = 5;
	 public static final int COMMENT_TX_MAX_LENGTH = 2;
	 public static final int OTHERS_MOD_KB_MAX_LENGTH = 2;

	public static final int YYYYMMDD_MAX_LENGTH = 8;
	public static final int SEQ_MAX_LENGTH = 5;

	// #2799対応 2017.02.02 X.Liu Add  (S)
	public static final int OROSI_BAITANKA_NUKI_VL_MAX_LENGTH = 12;
	public static final int MIN_HACHU_QT_MAX_LENGTH = 13;
	public static final int PLU_HANEI_TIME_MAX_LENGTH = 4;
	public static final int SYOKAI_USER_ID_MAX_LENGTH = 20;
	public static final int PACK_WEIGHT_QT_MAX_LENGTH = 5;
	public static final int COUNTRY_CD_MAX_LENGTH = 3;
	public static final int MAKER_CD_MAX_LENGTH = 6;
	public static final int HANBAI_HOHO_KB_MAX_LENGTH = 1;
	public static final int OROSI_BAITANKA_VL_MAX_LENGTH = 12;
	public static final int OROSI_BAITANKA_VL_KOURI_MAX_LENGTH = 12;
	public static final int SIIRE_ZEI_KB_MAX_LENGTH = 1;
	public static final int OROSI_ZEI_KB_MAX_LENGTH = 1;
	public static final int MIN_ZAIKO_QT_MAX_LENGTH = 5;
	public static final int PER_TXT_MAX_LENGTH = 21;
	public static final int CENTER_KYOYO_DT_MAX_LENGTH = 3;
	public static final int CENTER_KYOYO_KB_MAX_LENGTH = 1;
	public static final int SYOHI_KIGEN_HYOJI_PATTER_MAX_LENGTH = 1;
	public static final int SYOHIN_EIJI_NA_MAX_LENGTH = 25;
	public static final int LABEL_SEIBUN_MAX_LENGTH = 20;
	public static final int LABEL_HOKAN_HOHO_MAX_LENGTH = 16;
	public static final int LABEL_TUKAIKATA_MAX_LENGTH = 15;
	public static final int NENREI_SEIGEN_KB_MAX_LENGTH = 11;
	public static final int NENREI_MAX_LENGTH = 3;
	public static final int KAN_KB_MAX_LENGTH = 1;
	public static final int HOSHOUKIN_MAX_LENGTH = 12;
	public static final int SECURITY_TAG_FG_MAX_LENGTH = 1;
	public static final int HAMPER_SYOHIN_FG_MAX_LENGTH = 11;
	public static final int SINKI_TOROKU_DT_MAX_LENGTH = 8;
	public static final int SIIRE_TAX_RATE_KB_MAX_LENGTH = 1;
	public static final int OROSI_TAX_RATE_KB_MAX_LENGTH = 1;
	// #2799対応 2017.02.02 X.Liu Add  (E)
	
	 private String generation = null;
	 private String syohin_cd = null;
	 private String bunrui1_cd_for_kaipage = null;
	 private String kb = null;
	 private String yuko_dt = null;
	 private String bunrui1_cd = null;
	 private String bunrui5_cd = null;
	 private String kikaku_kanji_2_na = null;
	 private String hinmei_kanji_na = null;
	 private String rec_hinmei_kanji_na = null;
	 // #2799対応 2017.02.02 X.Liu Del  (S)
	 //private String hinmei_kana_na = null;
	 // #2799対応 2017.02.02 X.Liu Del  (E)
	 private String kikaku_kanji_na = null;
	 private String baitanka_vl = null;
	 // #2799対応 2017.02.02 X.Liu Mod  (S)
	 //private String baika_haishin_fg = null;
	 private String gentanka_vl = null;
	 private String orosi_baitanka_nuki_vl = null;
	 // #2799対応 2017.02.02 X.Liu Mod  (E)
	 private String siiresaki_cd = null;
	 //#2799対応 2017.02.02 X.Liu Add  (S)
	 private String plu_hanei_time = null;
	 // #2799対応 2017.02.02 X.Liu ADD  (E)
	 private String hachutani_irisu_qt = null;
	 // #2799対応 2017.02.02 X.Liu Del  (S)
	 //private String eos_kb = null;
	 //private String bin_1_kb = null;
	 //private String bin_2_kb = null;
	 // #2799対応 2017.02.02 X.Liu Del  (E)
	 private String update_user_id = null;
	 private String update_ts = null;
	 //#2799対応 2017.02.02 X.Liu Add  (S)
	 private String syokai_user_id = null;
	 //#2799対応 2017.02.02 X.Liu Add  (E)
	 private String system_kb = null;
	 private String gyosyu_kb = null;
	 private String syohin_kb = null;
	 private String bunrui2_cd = null;
	 private String syohin_2_cd = null;
	 private String zaiko_syohin_cd = null;
	 private String jyoho_syohin_cd = null;
	 private String kikaku_kana_na = null;
	 private String kikaku_kana_2_na = null;
	 private String rec_hinmei_kana_na = null;
	 private String syohin_width_qt = null;
	 private String syohin_height_qt = null;
	 private String syohin_depth_qt = null;
	 private String zei_kb = null;
	 private String tax_rate_kb = null;
	 private String gentanka_nuki_vl = null;
	 private String baitanka_nuki_vl = null;
	 private String maker_kibo_kakaku_vl = null;
	 private String hacyu_syohin_kb = null;
	 private String hacyu_syohin_cd = null;
	 private String hachu_tani_na = null;
	 private String hanbai_tani = null;
	 private String max_hachutani_qt = null;
	 private String case_hachu_kb = null;
	 private String bara_irisu_qt = null;
	 private String ten_hachu_st_dt = null;
	 private String ten_hachu_ed_dt = null;
	 private String hanbai_st_dt = null;
	 private String hanbai_ed_dt = null;
	 private String hanbai_kikan_kb = null;
	 private String teikan_kb = null;
	 private String soba_syohin_kb = null;
	 private String nohin_kigen_kb = null;
	 private String nohin_kigen_qt = null;
	 private String hachu_pattern_1_kb = null;
	 private String sime_time_1_qt = null;
	 private String c_nohin_rtime_1_kb = null;
	 private String ten_nohin_rtime_1_kb = null;
	 private String ten_nohin_time_1_kb = null;
	 private String hachu_pattern_2_kb = null;
	 private String sime_time_2_qt = null;
	 private String c_nohin_rtime_2_kb = null;
	 private String ten_nohin_rtime_2_kb = null;
	 private String ten_nohin_time_2_kb = null;
	 private String yusen_bin_kb = null;
	 private String f_bin_kb = null;
	 private String buturyu_kb = null;
	 private String got_start_mm = null;
	 private String got_end_mm = null;
	 private String hachu_teisi_kb = null;
	 private String cgc_henpin_kb = null;
	 private String hanbai_limit_kb = null;
	 private String hanbai_limit_qt = null;
	 private String plu_send_dt = null;
	 private String keyplu_fg = null;
	 private String plu_kb = null;
	 private String syuzei_hokoku_kb = null;
	 private String sake_naiyoryo_qt = null;
	 private String tag_hyoji_baika_vl = null;
	 private String keshi_baika_vl = null;
	 private String yoridori_kb = null;
	 private String pc_kb = null;
	 private String daisi_no_nb = null;
	 private String unit_price_tani_kb = null;
	 private String unit_price_naiyoryo_qt = null;
	 private String unit_price_kijun_tani_qt = null;
	 private String syohi_kigen_kb = null;
	 private String syohi_kigen_dt = null;
	 private String free_1_kb = null;
	 private String free_2_kb = null;
	 private String free_3_kb = null;
	 private String free_4_kb = null;
	 private String free_5_kb = null;
	 private String comment_1_tx = null;
	 private String comment_2_tx = null;
	 private String free_cd = null;
	 private String comment_tx = null;
	 private String others_mod_kb = null;
	 // #2799対応 2017.02.02 X.Liu Add  (S)
	 private String min_hachu_qt = null;
	 private String pack_weight_qt = null;
	 private String country_cd = null;
	 private String maker_cd = null;
	 private String hanbai_hoho_kb = null;
	 private String orosi_baitanka_vl = null;
	 private String orosi_baitanka_vl_kouri = null;
	 private String siire_zei_kb = null;
	 private String orosi_zei_kb = null;
	 private String min_zaiko_qt = null;
	 private String per_txt = null;
	 private String center_kyoyo_dt = null;
	 private String center_kyoyo_kb = null;
	 private String syohi_kigen_hyoji_patter = null;
	 private String syohin_eiji_na = null;
	 private String label_seibun = null;
	 private String label_hokan_hoho = null;
	 private String label_tukaikata = null;
	 private String nenrei_seigen_kb = null;
	 private String nenrei = null;
	 private String kan_kb = null;
	 private String hoshoukin = null;
	 private String security_tag_fg = null;
	 private String hamper_syohin_fg = null;
     private String sinki_toroku_dt = null;
	 private String siire_tax_rate_kb = null;
     private String orosi_tax_rate_kb = null;
	 // #2799対応 2017.02.02 X.Liu Add  (E)
	
	private String hikaku_taisyo_dt = null;
	private String yukospan_start_dt = null;
	private String yukospan_end_dt = null;
	private String seq = null;


	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase()
	{
		createDatabase = true;
	}
	public boolean isCreateDatabase()
	{
		return createDatabase;
	}

//	/**
//	 * mst010181_SyohinMasterHenkoListOutputBean を１件のみ抽出したい時に使用する
//	 */
//	public static mst010181_SyohinMasterHenkoListOutputBean getmst010181_SyohinMasterHenkoListOutputBean(DataHolder dataHolder)
//	{
//		mst010181_SyohinMasterHenkoListOutputBean bean = null;
//		BeanHolder beanHolder = new BeanHolder( new mst010182_SyohinMasterHenkoListOutputDM());
//		try
//		{
//			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
//			//１件以上存在する時はまず保存する
//			if( ite.hasNext() )
//				bean = (mst010181_SyohinMasterHenkoListOutputBean )ite.next();
//			//２件以上存在する時はＮＵＬＬにする
//			if( ite.hasNext() )
//				bean = null;
//		}
//		catch(Exception e)
//		{
//		}
//		return bean;
//	}

	// hikaku_taisyo_dtに対するセッターとゲッターの集合
	public boolean setHikakuTaisyoDt(String hikaku_taisyo_dt)
	{
		this.hikaku_taisyo_dt = hikaku_taisyo_dt;
		return true;
	}
	public String getHikakuTaisyoDt()
	{
		return cutString(this.hikaku_taisyo_dt,YYYYMMDD_MAX_LENGTH);
	}
	public String getHikakuTaisyoDtString()
	{
		return "'" + cutString(this.hikaku_taisyo_dt,YYYYMMDD_MAX_LENGTH) + "'";
	}
	public String getHikakuTaisyoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hikaku_taisyo_dt,YYYYMMDD_MAX_LENGTH));
	}

	// yukospan_start_dtに対するセッターとゲッターの集合
	public boolean setJisekiStartDt(String yukospan_start_dt)
	{
		this.yukospan_start_dt = yukospan_start_dt;
		return true;
	}
	public String getJisekiStartDt()
	{
		return cutString(this.yukospan_start_dt,YYYYMMDD_MAX_LENGTH);
	}
	public String getJisekiStartDtString()
	{
		return "'" + cutString(this.yukospan_start_dt,YYYYMMDD_MAX_LENGTH) + "'";
	}
	public String getJisekiStartDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yukospan_start_dt,YYYYMMDD_MAX_LENGTH));
	}

	// yukospan_end_dtに対するセッターとゲッターの集合
	public boolean setJisekiEndDt(String yukospan_end_dt)
	{
		this.yukospan_end_dt = yukospan_end_dt;
		return true;
	}
	public String getJisekiEndDt()
	{
		return cutString(this.yukospan_end_dt,YYYYMMDD_MAX_LENGTH);
	}
	public String getJisekiEndDtString()
	{
		return "'" + cutString(this.yukospan_end_dt,YYYYMMDD_MAX_LENGTH) + "'";
	}
	public String getJisekiEndDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yukospan_end_dt,YYYYMMDD_MAX_LENGTH));
	}

	// seqに対するセッターとゲッターの集合
	public boolean setSeq(String seq)
	{
		this.seq = seq;
		return true;
	}
	public String getSeq()
	{
		return cutString(this.seq,SEQ_MAX_LENGTH);
	}
	public String getSeqString()
	{
		return "'" + cutString(this.seq,SEQ_MAX_LENGTH) + "'";
	}
	public String getSeqHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.seq,SEQ_MAX_LENGTH));
	}

	// bunrui1_cdに対するセッターとゲッターの集合
	public boolean setBunrui1Cd(String bunrui1_cd)
	{
		this.bunrui1_cd = bunrui1_cd;
		return true;
	}
	public String getBunrui1Cd()
	{
		return cutString(this.bunrui1_cd,BUNRUI1_CD_MAX_LENGTH);
	}
	public String getBunrui1CdString()
	{
		return "'" + cutString(this.bunrui1_cd,BUNRUI1_CD_MAX_LENGTH) + "'";
	}
	public String getBunrui1CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bunrui1_cd,BUNRUI1_CD_MAX_LENGTH));
	}

	// bunrui5_cdに対するセッターとゲッターの集合
	public boolean setBunrui5Cd(String bunrui5_cd)
	{
		this.bunrui5_cd = bunrui5_cd;
		return true;
	}
	public String getBunrui5Cd()
	{
		return cutString(this.bunrui5_cd,BUNRUI5_CD_MAX_LENGTH);
	}
	public String getBunrui5CdString()
	{
		return "'" + cutString(this.bunrui5_cd,BUNRUI5_CD_MAX_LENGTH) + "'";
	}
	public String getBunrui5CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bunrui5_cd,BUNRUI5_CD_MAX_LENGTH));
	}

	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohinCd()
	{
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString()
	{
		return "'" + cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
	}

	// generationに対するセッターとゲッターの集合
	public boolean setGeneration(String generation)
	{
		this.generation = generation;
		return true;
	}
	public String getGeneration()
	{
		return cutString(this.generation,GENERATION_MAX_LENGTH);
	}
	public String getGenerationString()
	{
		return "'" + cutString(this.generation,GENERATION_MAX_LENGTH) + "'";
	}
	public String getGenerationHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.generation,GENERATION_MAX_LENGTH));
	}

	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohin_Cd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohin_Cd()
	{
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohin_CdString()
	{
		return "'" + cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
	}

	// bunrui1_cd_for_kaipageに対するセッターとゲッターの集合
	public boolean setBunrui1_Cd_For_Kaipage(String bunrui1_cd_for_kaipage)
	{
		this.bunrui1_cd_for_kaipage = bunrui1_cd_for_kaipage;
		return true;
	}
	public String getBunrui1_Cd_For_Kaipage()
	{
		return cutString(this.bunrui1_cd_for_kaipage,BUNRUI1_CD_FOR_KAIPAGE_MAX_LENGTH);
	}
	public String getBunrui1_Cd_For_KaipageString()
	{
		return "'" + cutString(this.bunrui1_cd_for_kaipage,BUNRUI1_CD_FOR_KAIPAGE_MAX_LENGTH) + "'";
	}
	public String getBunrui1_Cd_For_KaipageHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bunrui1_cd_for_kaipage,BUNRUI1_CD_FOR_KAIPAGE_MAX_LENGTH));
	}

	// kbに対するセッターとゲッターの集合
	public boolean setKb(String kb)
	{
		this.kb = kb;
		return true;
	}
	public String getKb()
	{
		// No.136 MST01018 2015/12/09 Nhat.NgoM Mod (S)
//		return cutString(this.kb,KB_MAX_LENGTH);
		return this.kb;
		// No.136 MST01018 2015/12/09 Nhat.NgoM Mod (E)
	}
	public String getKbString()
	{
		// No.136 MST01018 2015/12/09 Nhat.NgoM Mod (S)
//		return "'" + cutString(this.kb,KB_MAX_LENGTH) + "'";
		return "'" + this.kb + "'";
		// No.136 MST01018 2015/12/09 Nhat.NgoM Mod (E)
	}
	public String getKbHTMLString()
	{
		// No.136 MST01018 2015/12/09 Nhat.NgoM Mod (S)
//		return HTMLStringUtil.convert(cutString(this.kb,KB_MAX_LENGTH));
		return HTMLStringUtil.convert(this.kb);
		// No.136 MST01018 2015/12/09 Nhat.NgoM Mod (E)
	}

	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYuko_Dt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYuko_Dt()
	{
		return cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH);
	}
	public String getYuko_DtString()
	{
		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYuko_DtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
	}

	// bunrui1_cdに対するセッターとゲッターの集合
	public boolean setBunrui1_Cd(String bunrui1_cd)
	{
		this.bunrui1_cd = bunrui1_cd;
		return true;
	}
	public String getBunrui1_Cd()
	{
		return cutString(this.bunrui1_cd,BUNRUI1_CD_MAX_LENGTH);
	}
	public String getBunrui1_CdString()
	{
		return "'" + cutString(this.bunrui1_cd,BUNRUI1_CD_MAX_LENGTH) + "'";
	}
	public String getBunrui1_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bunrui1_cd,BUNRUI1_CD_MAX_LENGTH));
	}

	// bunrui5_cdに対するセッターとゲッターの集合
	public boolean setBunrui5_Cd(String bunrui5_cd)
	{
		this.bunrui5_cd = bunrui5_cd;
		return true;
	}
	public String getBunrui5_Cd()
	{
		return cutString(this.bunrui5_cd,BUNRUI5_CD_MAX_LENGTH);
	}
	public String getBunrui5_CdString()
	{
		return "'" + cutString(this.bunrui5_cd,BUNRUI5_CD_MAX_LENGTH) + "'";
	}
	public String getBunrui5_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bunrui5_cd,BUNRUI5_CD_MAX_LENGTH));
	}

	// kikaku_kanji_2_naに対するセッターとゲッターの集合
	public boolean setKikaku_Kanji_2_Na(String kikaku_kanji_2_na)
	{
		this.kikaku_kanji_2_na = kikaku_kanji_2_na;
		return true;
	}
	public String getKikaku_Kanji_2_Na()
	{
		return cutString(this.kikaku_kanji_2_na,KIKAKU_KANJI_2_NA_MAX_LENGTH);
	}
	public String getKikaku_Kanji_2_NaString()
	{
		return "'" + cutString(this.kikaku_kanji_2_na,KIKAKU_KANJI_2_NA_MAX_LENGTH) + "'";
	}
	public String getKikaku_Kanji_2_NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kanji_2_na,KIKAKU_KANJI_2_NA_MAX_LENGTH));
	}

	// hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setHinmei_Kanji_Na(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
		return true;
	}
	public String getHinmei_Kanji_Na()
	{
		return cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getHinmei_Kanji_NaString()
	{
		return "'" + cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getHinmei_Kanji_NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH));
	}

	// rec_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setRec_Hinmei_Kanji_Na(String rec_hinmei_kanji_na)
	{
		this.rec_hinmei_kanji_na = rec_hinmei_kanji_na;
		return true;
	}
	public String getRec_Hinmei_Kanji_Na()
	{
		return cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getRec_Hinmei_Kanji_NaString()
	{
		return "'" + cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getRec_Hinmei_Kanji_NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA_MAX_LENGTH));
	}

	// #2799対応 2017.02.02 X.Liu Del  (S)
	// hinmei_kana_naに対するセッターとゲッターの集合
	//public boolean setHinmei_Kana_Na(String hinmei_kana_na)
	//{
	//	this.hinmei_kana_na = hinmei_kana_na;
	//	return true;
	//}
	//public String getHinmei_Kana_Na()
	//{
	//	return cutString(this.hinmei_kana_na,HINMEI_KANA_NA_MAX_LENGTH);
	//}
	//public String getHinmei_Kana_NaString()
	//{
	//	return "'" + cutString(this.hinmei_kana_na,HINMEI_KANA_NA_MAX_LENGTH) + "'";
	//}
	//public String getHinmei_Kana_NaHTMLString()
	//{
	//	return HTMLStringUtil.convert(cutString(this.hinmei_kana_na,HINMEI_KANA_NA_MAX_LENGTH));
	//}
	// #2799対応 2017.02.02 X.Liu Del  (E)

	// kikaku_kanji_naに対するセッターとゲッターの集合
	public boolean setKikaku_Kanji_Na(String kikaku_kanji_na)
	{
		this.kikaku_kanji_na = kikaku_kanji_na;
		return true;
	}
	public String getKikaku_Kanji_Na()
	{
		return cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH);
	}
	public String getKikaku_Kanji_NaString()
	{
		return "'" + cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKikaku_Kanji_NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH));
	}

	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitanka_Vl(String baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public String getBaitanka_Vl()
	{
		return cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH);
	}
	public String getBaitanka_VlString()
	{
		return "'" + cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getBaitanka_VlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH));
	}

	// #2799対応 2017.02.02 X.Liu Del  (S)
	// baika_haishin_fgに対するセッターとゲッターの集合
	//public boolean setBaika_Haishin_Fg(String baika_haishin_fg)
	//{
	//	this.baika_haishin_fg = baika_haishin_fg;
	//	return true;
	//}
	//public String getBaika_Haishin_Fg()
	//{
	//	return cutString(this.baika_haishin_fg,BAIKA_HAISHIN_FG_MAX_LENGTH);
	//}
	//public String getBaika_Haishin_FgString()
	//{
	//	return "'" + cutString(this.baika_haishin_fg,BAIKA_HAISHIN_FG_MAX_LENGTH) + "'";
	//}
	//public String getBaika_Haishin_FgHTMLString()
	//{
	//	return HTMLStringUtil.convert(cutString(this.baika_haishin_fg,BAIKA_HAISHIN_FG_MAX_LENGTH));
	//}
	// #2799対応 2017.02.02 X.Liu Del  (E)
	// gentanka_vlに対するセッターとゲッターの集合
	public boolean setGentanka_Vl(String gentanka_vl)
	{
		this.gentanka_vl = gentanka_vl;
		return true;
	}
	public String getGentanka_Vl()
	{
		return cutString(this.gentanka_vl,GENTANKA_VL_MAX_LENGTH);
	}
	public String getGentanka_VlString()
	{
		return "'" + cutString(this.gentanka_vl,GENTANKA_VL_MAX_LENGTH) + "'";
	}
	public String getGentanka_VlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gentanka_vl,GENTANKA_VL_MAX_LENGTH));
	}

	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresaki_Cd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
	public String getSiiresaki_Cd()
	{
		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getSiiresaki_CdString()
	{
		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresaki_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
	}

	// hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setHachutani_Irisu_Qt(String hachutani_irisu_qt)
	{
		this.hachutani_irisu_qt = hachutani_irisu_qt;
		return true;
	}
	public String getHachutani_Irisu_Qt()
	{
		return cutString(this.hachutani_irisu_qt,HACHUTANI_IRISU_QT_MAX_LENGTH);
	}
	public String getHachutani_Irisu_QtString()
	{
		return "'" + cutString(this.hachutani_irisu_qt,HACHUTANI_IRISU_QT_MAX_LENGTH) + "'";
	}
	public String getHachutani_Irisu_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachutani_irisu_qt,HACHUTANI_IRISU_QT_MAX_LENGTH));
	}

	// #2799対応 2017.02.02 X.Liu Del  (S)
	// eos_kbに対するセッターとゲッターの集合
	//public boolean setEos_Kb(String eos_kb)
	//{
	//	this.eos_kb = eos_kb;
	//	return true;
	//}
	//public String getEos_Kb()
	//{
	//	return cutString(this.eos_kb,EOS_KB_MAX_LENGTH);
	//}
	//public String getEos_KbString()
	//{
	//	return "'" + cutString(this.eos_kb,EOS_KB_MAX_LENGTH) + "'";
	//}
	//public String getEos_KbHTMLString()
	//{
	//	return HTMLStringUtil.convert(cutString(this.eos_kb,EOS_KB_MAX_LENGTH));
	//}

	// bin_1_kbに対するセッターとゲッターの集合
	//public boolean setBin_1_Kb(String bin_1_kb)
	//{
	//	this.bin_1_kb = bin_1_kb;
	//	return true;
	//}
	//public String getBin_1_Kb()
	//{
	//	return cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH);
	//}
	//public String getBin_1_KbString()
	//{
	//	return "'" + cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH) + "'";
	//}
	//public String getBin_1_KbHTMLString()
	//{
	//	return HTMLStringUtil.convert(cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH));
	//}

	// bin_2_kbに対するセッターとゲッターの集合
	//public boolean setBin_2_Kb(String bin_2_kb)
	//{
	//	this.bin_2_kb = bin_2_kb;
	//	return true;
	//}
	//public String getBin_2_Kb()
	//{
	//	return cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH);
	//}
	//public String getBin_2_KbString()
	//{
	//	return "'" + cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH) + "'";
	//}
	//public String getBin_2_KbHTMLString()
	//{
	//	return HTMLStringUtil.convert(cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH));
	//}
	// #2799対応 2017.02.02 X.Liu Del  (E)

	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdate_User_Id(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdate_User_Id()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdate_User_IdString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdate_User_IdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}

	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdate_Ts(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdate_Ts()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdate_TsString()
	{
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdate_TsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}

	// system_kbに対するセッターとゲッターの集合
	public boolean setSystem_Kb(String system_kb)
	{
		this.system_kb = system_kb;
		return true;
	}
	public String getSystem_Kb()
	{
		return cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH);
	}
	public String getSystem_KbString()
	{
		return "'" + cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH) + "'";
	}
	public String getSystem_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH));
	}

	// gyosyu_kbに対するセッターとゲッターの集合
	public boolean setGyosyu_Kb(String gyosyu_kb)
	{
		this.gyosyu_kb = gyosyu_kb;
		return true;
	}
	public String getGyosyu_Kb()
	{
		return cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH);
	}
	public String getGyosyu_KbString()
	{
		return "'" + cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH) + "'";
	}
	public String getGyosyu_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH));
	}

	// syohin_kbに対するセッターとゲッターの集合
	public boolean setSyohin_Kb(String syohin_kb)
	{
		this.syohin_kb = syohin_kb;
		return true;
	}
	public String getSyohin_Kb()
	{
		return cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH);
	}
	public String getSyohin_KbString()
	{
		return "'" + cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getSyohin_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH));
	}

	// bunrui2_cdに対するセッターとゲッターの集合
	public boolean setBunrui2_Cd(String bunrui2_cd)
	{
		this.bunrui2_cd = bunrui2_cd;
		return true;
	}
	public String getBunrui2_Cd()
	{
		return cutString(this.bunrui2_cd,BUNRUI2_CD_MAX_LENGTH);
	}
	public String getBunrui2_CdString()
	{
		return "'" + cutString(this.bunrui2_cd,BUNRUI2_CD_MAX_LENGTH) + "'";
	}
	public String getBunrui2_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bunrui2_cd,BUNRUI2_CD_MAX_LENGTH));
	}

	// syohin_2_cdに対するセッターとゲッターの集合
	public boolean setSyohin_2_Cd(String syohin_2_cd)
	{
		this.syohin_2_cd = syohin_2_cd;
		return true;
	}
	public String getSyohin_2_Cd()
	{
		return cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH);
	}
	public String getSyohin_2_CdString()
	{
		return "'" + cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin_2_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH));
	}

	// zaiko_syohin_cdに対するセッターとゲッターの集合
	public boolean setZaiko_Syohin_Cd(String zaiko_syohin_cd)
	{
		this.zaiko_syohin_cd = zaiko_syohin_cd;
		return true;
	}
	public String getZaiko_Syohin_Cd()
	{
		return cutString(this.zaiko_syohin_cd,ZAIKO_SYOHIN_CD_MAX_LENGTH);
	}
	public String getZaiko_Syohin_CdString()
	{
		return "'" + cutString(this.zaiko_syohin_cd,ZAIKO_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getZaiko_Syohin_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zaiko_syohin_cd,ZAIKO_SYOHIN_CD_MAX_LENGTH));
	}

	// jyoho_syohin_cdに対するセッターとゲッターの集合
	public boolean setJyoho_Syohin_Cd(String jyoho_syohin_cd)
	{
		this.jyoho_syohin_cd = jyoho_syohin_cd;
		return true;
	}
	public String getJyoho_Syohin_Cd()
	{
		return cutString(this.jyoho_syohin_cd,JYOHO_SYOHIN_CD_MAX_LENGTH);
	}
	public String getJyoho_Syohin_CdString()
	{
		return "'" + cutString(this.jyoho_syohin_cd,JYOHO_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getJyoho_Syohin_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jyoho_syohin_cd,JYOHO_SYOHIN_CD_MAX_LENGTH));
	}

	// kikaku_kana_naに対するセッターとゲッターの集合
	public boolean setKikaku_Kana_Na(String kikaku_kana_na)
	{
		this.kikaku_kana_na = kikaku_kana_na;
		return true;
	}
	public String getKikaku_Kana_Na()
	{
		return cutString(this.kikaku_kana_na,KIKAKU_KANA_NA_MAX_LENGTH);
	}
	public String getKikaku_Kana_NaString()
	{
		return "'" + cutString(this.kikaku_kana_na,KIKAKU_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getKikaku_Kana_NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kana_na,KIKAKU_KANA_NA_MAX_LENGTH));
	}

	// kikaku_kana_2_naに対するセッターとゲッターの集合
	public boolean setKikaku_Kana_2_Na(String kikaku_kana_2_na)
	{
		this.kikaku_kana_2_na = kikaku_kana_2_na;
		return true;
	}
	public String getKikaku_Kana_2_Na()
	{
		return cutString(this.kikaku_kana_2_na,KIKAKU_KANA_2_NA_MAX_LENGTH);
	}
	public String getKikaku_Kana_2_NaString()
	{
		return "'" + cutString(this.kikaku_kana_2_na,KIKAKU_KANA_2_NA_MAX_LENGTH) + "'";
	}
	public String getKikaku_Kana_2_NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kana_2_na,KIKAKU_KANA_2_NA_MAX_LENGTH));
	}

	// rec_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setRec_Hinmei_Kana_Na(String rec_hinmei_kana_na)
	{
		this.rec_hinmei_kana_na = rec_hinmei_kana_na;
		return true;
	}
	public String getRec_Hinmei_Kana_Na()
	{
		return cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getRec_Hinmei_Kana_NaString()
	{
		return "'" + cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getRec_Hinmei_Kana_NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA_MAX_LENGTH));
	}

	// syohin_width_qtに対するセッターとゲッターの集合
	public boolean setSyohin_Width_Qt(String syohin_width_qt)
	{
		this.syohin_width_qt = syohin_width_qt;
		return true;
	}
	public String getSyohin_Width_Qt()
	{
		return cutString(this.syohin_width_qt,SYOHIN_WIDTH_QT_MAX_LENGTH);
	}
	public String getSyohin_Width_QtString()
	{
		return "'" + cutString(this.syohin_width_qt,SYOHIN_WIDTH_QT_MAX_LENGTH) + "'";
	}
	public String getSyohin_Width_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_width_qt,SYOHIN_WIDTH_QT_MAX_LENGTH));
	}

	// syohin_height_qtに対するセッターとゲッターの集合
	public boolean setSyohin_Height_Qt(String syohin_height_qt)
	{
		this.syohin_height_qt = syohin_height_qt;
		return true;
	}
	public String getSyohin_Height_Qt()
	{
		return cutString(this.syohin_height_qt,SYOHIN_HEIGHT_QT_MAX_LENGTH);
	}
	public String getSyohin_Height_QtString()
	{
		return "'" + cutString(this.syohin_height_qt,SYOHIN_HEIGHT_QT_MAX_LENGTH) + "'";
	}
	public String getSyohin_Height_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_height_qt,SYOHIN_HEIGHT_QT_MAX_LENGTH));
	}

	// syohin_depth_qtに対するセッターとゲッターの集合
	public boolean setSyohin_Depth_Qt(String syohin_depth_qt)
	{
		this.syohin_depth_qt = syohin_depth_qt;
		return true;
	}
	public String getSyohin_Depth_Qt()
	{
		return cutString(this.syohin_depth_qt,SYOHIN_DEPTH_QT_MAX_LENGTH);
	}
	public String getSyohin_Depth_QtString()
	{
		return "'" + cutString(this.syohin_depth_qt,SYOHIN_DEPTH_QT_MAX_LENGTH) + "'";
	}
	public String getSyohin_Depth_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_depth_qt,SYOHIN_DEPTH_QT_MAX_LENGTH));
	}

	// zei_kbに対するセッターとゲッターの集合
	public boolean setZei_Kb(String zei_kb)
	{
		this.zei_kb = zei_kb;
		return true;
	}
	public String getZei_Kb()
	{
		return cutString(this.zei_kb,ZEI_KB_MAX_LENGTH);
	}
	public String getZei_KbString()
	{
		return "'" + cutString(this.zei_kb,ZEI_KB_MAX_LENGTH) + "'";
	}
	public String getZei_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zei_kb,ZEI_KB_MAX_LENGTH));
	}

	// tax_rate_kbに対するセッターとゲッターの集合
	public boolean setTax_Rate_Kb(String tax_rate_kb)
	{
		this.tax_rate_kb = tax_rate_kb;
		return true;
	}
	public String getTax_Rate_Kb()
	{
		return cutString(this.tax_rate_kb,TAX_RATE_KB_MAX_LENGTH);
	}
	public String getTax_Rate_KbString()
	{
		return "'" + cutString(this.tax_rate_kb,TAX_RATE_KB_MAX_LENGTH) + "'";
	}
	public String getTax_Rate_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tax_rate_kb,TAX_RATE_KB_MAX_LENGTH));
	}

	// gentanka_nuki_vlに対するセッターとゲッターの集合
	public boolean setGentanka_Nuki_Vl(String gentanka_nuki_vl)
	{
		this.gentanka_nuki_vl = gentanka_nuki_vl;
		return true;
	}
	public String getGentanka_Nuki_Vl()
	{
		return cutString(this.gentanka_nuki_vl,GENTANKA_NUKI_VL_MAX_LENGTH);
	}
	public String getGentanka_Nuki_VlString()
	{
		return "'" + cutString(this.gentanka_nuki_vl,GENTANKA_NUKI_VL_MAX_LENGTH) + "'";
	}
	public String getGentanka_Nuki_VlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gentanka_nuki_vl,GENTANKA_NUKI_VL_MAX_LENGTH));
	}

	// baitanka_nuki_vlに対するセッターとゲッターの集合
	public boolean setBaitanka_Nuki_Vl(String baitanka_nuki_vl)
	{
		this.baitanka_nuki_vl = baitanka_nuki_vl;
		return true;
	}
	public String getBaitanka_Nuki_Vl()
	{
		return cutString(this.baitanka_nuki_vl,BAITANKA_NUKI_VL_MAX_LENGTH);
	}
	public String getBaitanka_Nuki_VlString()
	{
		return "'" + cutString(this.baitanka_nuki_vl,BAITANKA_NUKI_VL_MAX_LENGTH) + "'";
	}
	public String getBaitanka_Nuki_VlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.baitanka_nuki_vl,BAITANKA_NUKI_VL_MAX_LENGTH));
	}

	// maker_kibo_kakaku_vlに対するセッターとゲッターの集合
	public boolean setMaker_Kibo_Kakaku_Vl(String maker_kibo_kakaku_vl)
	{
		this.maker_kibo_kakaku_vl = maker_kibo_kakaku_vl;
		return true;
	}
	public String getMaker_Kibo_Kakaku_Vl()
	{
		return cutString(this.maker_kibo_kakaku_vl,MAKER_KIBO_KAKAKU_VL_MAX_LENGTH);
	}
	public String getMaker_Kibo_Kakaku_VlString()
	{
		return "'" + cutString(this.maker_kibo_kakaku_vl,MAKER_KIBO_KAKAKU_VL_MAX_LENGTH) + "'";
	}
	public String getMaker_Kibo_Kakaku_VlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.maker_kibo_kakaku_vl,MAKER_KIBO_KAKAKU_VL_MAX_LENGTH));
	}

	// hacyu_syohin_kbに対するセッターとゲッターの集合
	public boolean setHacyu_Syohin_Kb(String hacyu_syohin_kb)
	{
		this.hacyu_syohin_kb = hacyu_syohin_kb;
		return true;
	}
	public String getHacyu_Syohin_Kb()
	{
		return cutString(this.hacyu_syohin_kb,HACYU_SYOHIN_KB_MAX_LENGTH);
	}
	public String getHacyu_Syohin_KbString()
	{
		return "'" + cutString(this.hacyu_syohin_kb,HACYU_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getHacyu_Syohin_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hacyu_syohin_kb,HACYU_SYOHIN_KB_MAX_LENGTH));
	}

	// hacyu_syohin_cdに対するセッターとゲッターの集合
	public boolean setHacyu_Syohin_Cd(String hacyu_syohin_cd)
	{
		this.hacyu_syohin_cd = hacyu_syohin_cd;
		return true;
	}
	public String getHacyu_Syohin_Cd()
	{
		return cutString(this.hacyu_syohin_cd,HACYU_SYOHIN_CD_MAX_LENGTH);
	}
	public String getHacyu_Syohin_CdString()
	{
		return "'" + cutString(this.hacyu_syohin_cd,HACYU_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getHacyu_Syohin_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hacyu_syohin_cd,HACYU_SYOHIN_CD_MAX_LENGTH));
	}

	// hachu_tani_naに対するセッターとゲッターの集合
	public boolean setHachu_Tani_Na(String hachu_tani_na)
	{
		this.hachu_tani_na = hachu_tani_na;
		return true;
	}
	public String getHachu_Tani_Na()
	{
		return cutString(this.hachu_tani_na,HACHU_TANI_NA_MAX_LENGTH);
	}
	public String getHachu_Tani_NaString()
	{
		return "'" + cutString(this.hachu_tani_na,HACHU_TANI_NA_MAX_LENGTH) + "'";
	}
	public String getHachu_Tani_NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_tani_na,HACHU_TANI_NA_MAX_LENGTH));
	}

	// hanbai_taniに対するセッターとゲッターの集合
	public boolean setHanbai_Tani(String hanbai_tani)
	{
		this.hanbai_tani = hanbai_tani;
		return true;
	}
	public String getHanbai_Tani()
	{
		return cutString(this.hanbai_tani,HANBAI_TANI_MAX_LENGTH);
	}
	public String getHanbai_TaniString()
	{
		return "'" + cutString(this.hanbai_tani,HANBAI_TANI_MAX_LENGTH) + "'";
	}
	public String getHanbai_TaniHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_tani,HANBAI_TANI_MAX_LENGTH));
	}

	// max_hachutani_qtに対するセッターとゲッターの集合
	public boolean setMax_Hachutani_Qt(String max_hachutani_qt)
	{
		this.max_hachutani_qt = max_hachutani_qt;
		return true;
	}
	public String getMax_Hachutani_Qt()
	{
		return cutString(this.max_hachutani_qt,MAX_HACHUTANI_QT_MAX_LENGTH);
	}
	public String getMax_Hachutani_QtString()
	{
		return "'" + cutString(this.max_hachutani_qt,MAX_HACHUTANI_QT_MAX_LENGTH) + "'";
	}
	public String getMax_Hachutani_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.max_hachutani_qt,MAX_HACHUTANI_QT_MAX_LENGTH));
	}

	// case_hachu_kbに対するセッターとゲッターの集合
	public boolean setCase_Hachu_Kb(String case_hachu_kb)
	{
		this.case_hachu_kb = case_hachu_kb;
		return true;
	}
	public String getCase_Hachu_Kb()
	{
		return cutString(this.case_hachu_kb,CASE_HACHU_KB_MAX_LENGTH);
	}
	public String getCase_Hachu_KbString()
	{
		return "'" + cutString(this.case_hachu_kb,CASE_HACHU_KB_MAX_LENGTH) + "'";
	}
	public String getCase_Hachu_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.case_hachu_kb,CASE_HACHU_KB_MAX_LENGTH));
	}

	// bara_irisu_qtに対するセッターとゲッターの集合
	public boolean setBara_Irisu_Qt(String bara_irisu_qt)
	{
		this.bara_irisu_qt = bara_irisu_qt;
		return true;
	}
	public String getBara_Irisu_Qt()
	{
		return cutString(this.bara_irisu_qt,BARA_IRISU_QT_MAX_LENGTH);
	}
	public String getBara_Irisu_QtString()
	{
		return "'" + cutString(this.bara_irisu_qt,BARA_IRISU_QT_MAX_LENGTH) + "'";
	}
	public String getBara_Irisu_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bara_irisu_qt,BARA_IRISU_QT_MAX_LENGTH));
	}

	// ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setTen_Hachu_St_Dt(String ten_hachu_st_dt)
	{
		this.ten_hachu_st_dt = ten_hachu_st_dt;
		return true;
	}
	public String getTen_Hachu_St_Dt()
	{
		return cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH);
	}
	public String getTen_Hachu_St_DtString()
	{
		return "'" + cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getTen_Hachu_St_DtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH));
	}

	// ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setTen_Hachu_Ed_Dt(String ten_hachu_ed_dt)
	{
		this.ten_hachu_ed_dt = ten_hachu_ed_dt;
		return true;
	}
	public String getTen_Hachu_Ed_Dt()
	{
		return cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH);
	}
	public String getTen_Hachu_Ed_DtString()
	{
		return "'" + cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getTen_Hachu_Ed_DtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH));
	}

	// hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setHanbai_St_Dt(String hanbai_st_dt)
	{
		this.hanbai_st_dt = hanbai_st_dt;
		return true;
	}
	public String getHanbai_St_Dt()
	{
		return cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getHanbai_St_DtString()
	{
		return "'" + cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getHanbai_St_DtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH));
	}

	// hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setHanbai_Ed_Dt(String hanbai_ed_dt)
	{
		this.hanbai_ed_dt = hanbai_ed_dt;
		return true;
	}
	public String getHanbai_Ed_Dt()
	{
		return cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getHanbai_Ed_DtString()
	{
		return "'" + cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getHanbai_Ed_DtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH));
	}

	// hanbai_kikan_kbに対するセッターとゲッターの集合
	public boolean setHanbai_Kikan_Kb(String hanbai_kikan_kb)
	{
		this.hanbai_kikan_kb = hanbai_kikan_kb;
		return true;
	}
	public String getHanbai_Kikan_Kb()
	{
		return cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH);
	}
	public String getHanbai_Kikan_KbString()
	{
		return "'" + cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getHanbai_Kikan_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH));
	}

	// teikan_kbに対するセッターとゲッターの集合
	public boolean setTeikan_Kb(String teikan_kb)
	{
		this.teikan_kb = teikan_kb;
		return true;
	}
	public String getTeikan_Kb()
	{
		return cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH);
	}
	public String getTeikan_KbString()
	{
		return "'" + cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getTeikan_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH));
	}

	// soba_syohin_kbに対するセッターとゲッターの集合
	public boolean setSoba_Syohin_Kb(String soba_syohin_kb)
	{
		this.soba_syohin_kb = soba_syohin_kb;
		return true;
	}
	public String getSoba_Syohin_Kb()
	{
		return cutString(this.soba_syohin_kb,SOBA_SYOHIN_KB_MAX_LENGTH);
	}
	public String getSoba_Syohin_KbString()
	{
		return "'" + cutString(this.soba_syohin_kb,SOBA_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getSoba_Syohin_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.soba_syohin_kb,SOBA_SYOHIN_KB_MAX_LENGTH));
	}

	// nohin_kigen_kbに対するセッターとゲッターの集合
	public boolean setNohin_Kigen_Kb(String nohin_kigen_kb)
	{
		this.nohin_kigen_kb = nohin_kigen_kb;
		return true;
	}
	public String getNohin_Kigen_Kb()
	{
		return cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH);
	}
	public String getNohin_Kigen_KbString()
	{
		return "'" + cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH) + "'";
	}
	public String getNohin_Kigen_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH));
	}

	// nohin_kigen_qtに対するセッターとゲッターの集合
	public boolean setNohin_Kigen_Qt(String nohin_kigen_qt)
	{
		this.nohin_kigen_qt = nohin_kigen_qt;
		return true;
	}
	public String getNohin_Kigen_Qt()
	{
		return cutString(this.nohin_kigen_qt,NOHIN_KIGEN_QT_MAX_LENGTH);
	}
	public String getNohin_Kigen_QtString()
	{
		return "'" + cutString(this.nohin_kigen_qt,NOHIN_KIGEN_QT_MAX_LENGTH) + "'";
	}
	public String getNohin_Kigen_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_kigen_qt,NOHIN_KIGEN_QT_MAX_LENGTH));
	}

	// hachu_pattern_1_kbに対するセッターとゲッターの集合
	public boolean setHachu_Pattern_1_Kb(String hachu_pattern_1_kb)
	{
		this.hachu_pattern_1_kb = hachu_pattern_1_kb;
		return true;
	}
	public String getHachu_Pattern_1_Kb()
	{
		return cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH);
	}
	public String getHachu_Pattern_1_KbString()
	{
		return "'" + cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH) + "'";
	}
	public String getHachu_Pattern_1_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH));
	}

	// sime_time_1_qtに対するセッターとゲッターの集合
	public boolean setSime_Time_1_Qt(String sime_time_1_qt)
	{
		this.sime_time_1_qt = sime_time_1_qt;
		return true;
	}
	public String getSime_Time_1_Qt()
	{
		return cutString(this.sime_time_1_qt,SIME_TIME_1_QT_MAX_LENGTH);
	}
	public String getSime_Time_1_QtString()
	{
		return "'" + cutString(this.sime_time_1_qt,SIME_TIME_1_QT_MAX_LENGTH) + "'";
	}
	public String getSime_Time_1_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sime_time_1_qt,SIME_TIME_1_QT_MAX_LENGTH));
	}

	// c_nohin_rtime_1_kbに対するセッターとゲッターの集合
	public boolean setC_Nohin_Rtime_1_Kb(String c_nohin_rtime_1_kb)
	{
		this.c_nohin_rtime_1_kb = c_nohin_rtime_1_kb;
		return true;
	}
	public String getC_Nohin_Rtime_1_Kb()
	{
		return cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH);
	}
	public String getC_Nohin_Rtime_1_KbString()
	{
		return "'" + cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getC_Nohin_Rtime_1_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH));
	}

	// ten_nohin_rtime_1_kbに対するセッターとゲッターの集合
	public boolean setTen_Nohin_Rtime_1_Kb(String ten_nohin_rtime_1_kb)
	{
		this.ten_nohin_rtime_1_kb = ten_nohin_rtime_1_kb;
		return true;
	}
	public String getTen_Nohin_Rtime_1_Kb()
	{
		return cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH);
	}
	public String getTen_Nohin_Rtime_1_KbString()
	{
		return "'" + cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getTen_Nohin_Rtime_1_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH));
	}

	// ten_nohin_time_1_kbに対するセッターとゲッターの集合
	public boolean setTen_Nohin_Time_1_Kb(String ten_nohin_time_1_kb)
	{
		this.ten_nohin_time_1_kb = ten_nohin_time_1_kb;
		return true;
	}
	public String getTen_Nohin_Time_1_Kb()
	{
		return cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH);
	}
	public String getTen_Nohin_Time_1_KbString()
	{
		return "'" + cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getTen_Nohin_Time_1_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH));
	}

	// hachu_pattern_2_kbに対するセッターとゲッターの集合
	public boolean setHachu_Pattern_2_Kb(String hachu_pattern_2_kb)
	{
		this.hachu_pattern_2_kb = hachu_pattern_2_kb;
		return true;
	}
	public String getHachu_Pattern_2_Kb()
	{
		return cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH);
	}
	public String getHachu_Pattern_2_KbString()
	{
		return "'" + cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH) + "'";
	}
	public String getHachu_Pattern_2_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH));
	}

	// sime_time_2_qtに対するセッターとゲッターの集合
	public boolean setSime_Time_2_Qt(String sime_time_2_qt)
	{
		this.sime_time_2_qt = sime_time_2_qt;
		return true;
	}
	public String getSime_Time_2_Qt()
	{
		return cutString(this.sime_time_2_qt,SIME_TIME_2_QT_MAX_LENGTH);
	}
	public String getSime_Time_2_QtString()
	{
		return "'" + cutString(this.sime_time_2_qt,SIME_TIME_2_QT_MAX_LENGTH) + "'";
	}
	public String getSime_Time_2_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sime_time_2_qt,SIME_TIME_2_QT_MAX_LENGTH));
	}

	// c_nohin_rtime_2_kbに対するセッターとゲッターの集合
	public boolean setC_Nohin_Rtime_2_Kb(String c_nohin_rtime_2_kb)
	{
		this.c_nohin_rtime_2_kb = c_nohin_rtime_2_kb;
		return true;
	}
	public String getC_Nohin_Rtime_2_Kb()
	{
		return cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH);
	}
	public String getC_Nohin_Rtime_2_KbString()
	{
		return "'" + cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getC_Nohin_Rtime_2_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH));
	}

	// ten_nohin_rtime_2_kbに対するセッターとゲッターの集合
	public boolean setTen_Nohin_Rtime_2_Kb(String ten_nohin_rtime_2_kb)
	{
		this.ten_nohin_rtime_2_kb = ten_nohin_rtime_2_kb;
		return true;
	}
	public String getTen_Nohin_Rtime_2_Kb()
	{
		return cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH);
	}
	public String getTen_Nohin_Rtime_2_KbString()
	{
		return "'" + cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getTen_Nohin_Rtime_2_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH));
	}

	// ten_nohin_time_2_kbに対するセッターとゲッターの集合
	public boolean setTen_Nohin_Time_2_Kb(String ten_nohin_time_2_kb)
	{
		this.ten_nohin_time_2_kb = ten_nohin_time_2_kb;
		return true;
	}
	public String getTen_Nohin_Time_2_Kb()
	{
		return cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH);
	}
	public String getTen_Nohin_Time_2_KbString()
	{
		return "'" + cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getTen_Nohin_Time_2_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH));
	}

	// yusen_bin_kbに対するセッターとゲッターの集合
	public boolean setYusen_Bin_Kb(String yusen_bin_kb)
	{
		this.yusen_bin_kb = yusen_bin_kb;
		return true;
	}
	public String getYusen_Bin_Kb()
	{
		return cutString(this.yusen_bin_kb,YUSEN_BIN_KB_MAX_LENGTH);
	}
	public String getYusen_Bin_KbString()
	{
		return "'" + cutString(this.yusen_bin_kb,YUSEN_BIN_KB_MAX_LENGTH) + "'";
	}
	public String getYusen_Bin_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yusen_bin_kb,YUSEN_BIN_KB_MAX_LENGTH));
	}

	// f_bin_kbに対するセッターとゲッターの集合
	public boolean setF_Bin_Kb(String f_bin_kb)
	{
		this.f_bin_kb = f_bin_kb;
		return true;
	}
	public String getF_Bin_Kb()
	{
		return cutString(this.f_bin_kb,F_BIN_KB_MAX_LENGTH);
	}
	public String getF_Bin_KbString()
	{
		return "'" + cutString(this.f_bin_kb,F_BIN_KB_MAX_LENGTH) + "'";
	}
	public String getF_Bin_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.f_bin_kb,F_BIN_KB_MAX_LENGTH));
	}

	// buturyu_kbに対するセッターとゲッターの集合
	public boolean setButuryu_Kb(String buturyu_kb)
	{
		this.buturyu_kb = buturyu_kb;
		return true;
	}
	public String getButuryu_Kb()
	{
		return cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH);
	}
	public String getButuryu_KbString()
	{
		return "'" + cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH) + "'";
	}
	public String getButuryu_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH));
	}

	// got_start_mmに対するセッターとゲッターの集合
	public boolean setGot_Start_Mm(String got_start_mm)
	{
		this.got_start_mm = got_start_mm;
		return true;
	}
	public String getGot_Start_Mm()
	{
		return cutString(this.got_start_mm,GOT_START_MM_MAX_LENGTH);
	}
	public String getGot_Start_MmString()
	{
		return "'" + cutString(this.got_start_mm,GOT_START_MM_MAX_LENGTH) + "'";
	}
	public String getGot_Start_MmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.got_start_mm,GOT_START_MM_MAX_LENGTH));
	}

	// got_end_mmに対するセッターとゲッターの集合
	public boolean setGot_End_Mm(String got_end_mm)
	{
		this.got_end_mm = got_end_mm;
		return true;
	}
	public String getGot_End_Mm()
	{
		return cutString(this.got_end_mm,GOT_END_MM_MAX_LENGTH);
	}
	public String getGot_End_MmString()
	{
		return "'" + cutString(this.got_end_mm,GOT_END_MM_MAX_LENGTH) + "'";
	}
	public String getGot_End_MmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.got_end_mm,GOT_END_MM_MAX_LENGTH));
	}

	// hachu_teisi_kbに対するセッターとゲッターの集合
	public boolean setHachu_Teisi_Kb(String hachu_teisi_kb)
	{
		this.hachu_teisi_kb = hachu_teisi_kb;
		return true;
	}
	public String getHachu_Teisi_Kb()
	{
		return cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH);
	}
	public String getHachu_Teisi_KbString()
	{
		return "'" + cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH) + "'";
	}
	public String getHachu_Teisi_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH));
	}

	// cgc_henpin_kbに対するセッターとゲッターの集合
	public boolean setCgc_Henpin_Kb(String cgc_henpin_kb)
	{
		this.cgc_henpin_kb = cgc_henpin_kb;
		return true;
	}
	public String getCgc_Henpin_Kb()
	{
		return cutString(this.cgc_henpin_kb,CGC_HENPIN_KB_MAX_LENGTH);
	}
	public String getCgc_Henpin_KbString()
	{
		return "'" + cutString(this.cgc_henpin_kb,CGC_HENPIN_KB_MAX_LENGTH) + "'";
	}
	public String getCgc_Henpin_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.cgc_henpin_kb,CGC_HENPIN_KB_MAX_LENGTH));
	}

	// hanbai_limit_kbに対するセッターとゲッターの集合
	public boolean setHanbai_Limit_Kb(String hanbai_limit_kb)
	{
		this.hanbai_limit_kb = hanbai_limit_kb;
		return true;
	}
	public String getHanbai_Limit_Kb()
	{
		return cutString(this.hanbai_limit_kb,HANBAI_LIMIT_KB_MAX_LENGTH);
	}
	public String getHanbai_Limit_KbString()
	{
		return "'" + cutString(this.hanbai_limit_kb,HANBAI_LIMIT_KB_MAX_LENGTH) + "'";
	}
	public String getHanbai_Limit_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_limit_kb,HANBAI_LIMIT_KB_MAX_LENGTH));
	}

	// hanbai_limit_qtに対するセッターとゲッターの集合
	public boolean setHanbai_Limit_Qt(String hanbai_limit_qt)
	{
		this.hanbai_limit_qt = hanbai_limit_qt;
		return true;
	}
	public String getHanbai_Limit_Qt()
	{
		return cutString(this.hanbai_limit_qt,HANBAI_LIMIT_QT_MAX_LENGTH);
	}
	public String getHanbai_Limit_QtString()
	{
		return "'" + cutString(this.hanbai_limit_qt,HANBAI_LIMIT_QT_MAX_LENGTH) + "'";
	}
	public String getHanbai_Limit_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_limit_qt,HANBAI_LIMIT_QT_MAX_LENGTH));
	}

	// plu_send_dtに対するセッターとゲッターの集合
	public boolean setPlu_Send_Dt(String plu_send_dt)
	{
		this.plu_send_dt = plu_send_dt;
		return true;
	}
	public String getPlu_Send_Dt()
	{
		return cutString(this.plu_send_dt,PLU_SEND_DT_MAX_LENGTH);
	}
	public String getPlu_Send_DtString()
	{
		return "'" + cutString(this.plu_send_dt,PLU_SEND_DT_MAX_LENGTH) + "'";
	}
	public String getPlu_Send_DtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.plu_send_dt,PLU_SEND_DT_MAX_LENGTH));
	}

	// keyplu_fgに対するセッターとゲッターの集合
	public boolean setKeyplu_Fg(String keyplu_fg)
	{
		this.keyplu_fg = keyplu_fg;
		return true;
	}
	public String getKeyplu_Fg()
	{
		return cutString(this.keyplu_fg,KEYPLU_FG_MAX_LENGTH);
	}
	public String getKeyplu_FgString()
	{
		return "'" + cutString(this.keyplu_fg,KEYPLU_FG_MAX_LENGTH) + "'";
	}
	public String getKeyplu_FgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keyplu_fg,KEYPLU_FG_MAX_LENGTH));
	}

	// plu_kbに対するセッターとゲッターの集合
	public boolean setPlu_Kb(String plu_kb)
	{
		this.plu_kb = plu_kb;
		return true;
	}
	public String getPlu_Kb()
	{
		return cutString(this.plu_kb,PLU_KB_MAX_LENGTH);
	}
	public String getPlu_KbString()
	{
		return "'" + cutString(this.plu_kb,PLU_KB_MAX_LENGTH) + "'";
	}
	public String getPlu_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.plu_kb,PLU_KB_MAX_LENGTH));
	}

	// syuzei_hokoku_kbに対するセッターとゲッターの集合
	public boolean setSyuzei_Hokoku_Kb(String syuzei_hokoku_kb)
	{
		this.syuzei_hokoku_kb = syuzei_hokoku_kb;
		return true;
	}
	public String getSyuzei_Hokoku_Kb()
	{
		return cutString(this.syuzei_hokoku_kb,SYUZEI_HOKOKU_KB_MAX_LENGTH);
	}
	public String getSyuzei_Hokoku_KbString()
	{
		return "'" + cutString(this.syuzei_hokoku_kb,SYUZEI_HOKOKU_KB_MAX_LENGTH) + "'";
	}
	public String getSyuzei_Hokoku_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syuzei_hokoku_kb,SYUZEI_HOKOKU_KB_MAX_LENGTH));
	}

	// sake_naiyoryo_qtに対するセッターとゲッターの集合
	public boolean setSake_Naiyoryo_Qt(String sake_naiyoryo_qt)
	{
		this.sake_naiyoryo_qt = sake_naiyoryo_qt;
		return true;
	}
	public String getSake_Naiyoryo_Qt()
	{
		return cutString(this.sake_naiyoryo_qt,SAKE_NAIYORYO_QT_MAX_LENGTH);
	}
	public String getSake_Naiyoryo_QtString()
	{
		return "'" + cutString(this.sake_naiyoryo_qt,SAKE_NAIYORYO_QT_MAX_LENGTH) + "'";
	}
	public String getSake_Naiyoryo_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sake_naiyoryo_qt,SAKE_NAIYORYO_QT_MAX_LENGTH));
	}

	// tag_hyoji_baika_vlに対するセッターとゲッターの集合
	public boolean setTag_Hyoji_Baika_Vl(String tag_hyoji_baika_vl)
	{
		this.tag_hyoji_baika_vl = tag_hyoji_baika_vl;
		return true;
	}
	public String getTag_Hyoji_Baika_Vl()
	{
		return cutString(this.tag_hyoji_baika_vl,TAG_HYOJI_BAIKA_VL_MAX_LENGTH);
	}
	public String getTag_Hyoji_Baika_VlString()
	{
		return "'" + cutString(this.tag_hyoji_baika_vl,TAG_HYOJI_BAIKA_VL_MAX_LENGTH) + "'";
	}
	public String getTag_Hyoji_Baika_VlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tag_hyoji_baika_vl,TAG_HYOJI_BAIKA_VL_MAX_LENGTH));
	}

	// keshi_baika_vlに対するセッターとゲッターの集合
	public boolean setKeshi_Baika_Vl(String keshi_baika_vl)
	{
		this.keshi_baika_vl = keshi_baika_vl;
		return true;
	}
	public String getKeshi_Baika_Vl()
	{
		return cutString(this.keshi_baika_vl,KESHI_BAIKA_VL_MAX_LENGTH);
	}
	public String getKeshi_Baika_VlString()
	{
		return "'" + cutString(this.keshi_baika_vl,KESHI_BAIKA_VL_MAX_LENGTH) + "'";
	}
	public String getKeshi_Baika_VlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keshi_baika_vl,KESHI_BAIKA_VL_MAX_LENGTH));
	}

	// yoridori_kbに対するセッターとゲッターの集合
	public boolean setYoridori_Kb(String yoridori_kb)
	{
		this.yoridori_kb = yoridori_kb;
		return true;
	}
	public String getYoridori_Kb()
	{
		return cutString(this.yoridori_kb,YORIDORI_KB_MAX_LENGTH);
	}
	public String getYoridori_KbString()
	{
		return "'" + cutString(this.yoridori_kb,YORIDORI_KB_MAX_LENGTH) + "'";
	}
	public String getYoridori_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yoridori_kb,YORIDORI_KB_MAX_LENGTH));
	}

	// pc_kbに対するセッターとゲッターの集合
	public boolean setPc_Kb(String pc_kb)
	{
		this.pc_kb = pc_kb;
		return true;
	}
	public String getPc_Kb()
	{
		return cutString(this.pc_kb,PC_KB_MAX_LENGTH);
	}
	public String getPc_KbString()
	{
		return "'" + cutString(this.pc_kb,PC_KB_MAX_LENGTH) + "'";
	}
	public String getPc_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pc_kb,PC_KB_MAX_LENGTH));
	}

	// daisi_no_nbに対するセッターとゲッターの集合
	public boolean setDaisi_No_Nb(String daisi_no_nb)
	{
		this.daisi_no_nb = daisi_no_nb;
		return true;
	}
	public String getDaisi_No_Nb()
	{
		return cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH);
	}
	public String getDaisi_No_NbString()
	{
		return "'" + cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH) + "'";
	}
	public String getDaisi_No_NbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH));
	}

	// unit_price_tani_kbに対するセッターとゲッターの集合
	public boolean setUnit_Price_Tani_Kb(String unit_price_tani_kb)
	{
		this.unit_price_tani_kb = unit_price_tani_kb;
		return true;
	}
	public String getUnit_Price_Tani_Kb()
	{
		return cutString(this.unit_price_tani_kb,UNIT_PRICE_TANI_KB_MAX_LENGTH);
	}
	public String getUnit_Price_Tani_KbString()
	{
		return "'" + cutString(this.unit_price_tani_kb,UNIT_PRICE_TANI_KB_MAX_LENGTH) + "'";
	}
	public String getUnit_Price_Tani_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_price_tani_kb,UNIT_PRICE_TANI_KB_MAX_LENGTH));
	}

	// unit_price_naiyoryo_qtに対するセッターとゲッターの集合
	public boolean setUnit_Price_Naiyoryo_Qt(String unit_price_naiyoryo_qt)
	{
		this.unit_price_naiyoryo_qt = unit_price_naiyoryo_qt;
		return true;
	}
	public String getUnit_Price_Naiyoryo_Qt()
	{
		return cutString(this.unit_price_naiyoryo_qt,UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH);
	}
	public String getUnit_Price_Naiyoryo_QtString()
	{
		return "'" + cutString(this.unit_price_naiyoryo_qt,UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH) + "'";
	}
	public String getUnit_Price_Naiyoryo_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_price_naiyoryo_qt,UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH));
	}

	// unit_price_kijun_tani_qtに対するセッターとゲッターの集合
	public boolean setUnit_Price_Kijun_Tani_Qt(String unit_price_kijun_tani_qt)
	{
		this.unit_price_kijun_tani_qt = unit_price_kijun_tani_qt;
		return true;
	}
	public String getUnit_Price_Kijun_Tani_Qt()
	{
		return cutString(this.unit_price_kijun_tani_qt,UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH);
	}
	public String getUnit_Price_Kijun_Tani_QtString()
	{
		return "'" + cutString(this.unit_price_kijun_tani_qt,UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH) + "'";
	}
	public String getUnit_Price_Kijun_Tani_QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_price_kijun_tani_qt,UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH));
	}

	// syohi_kigen_kbに対するセッターとゲッターの集合
	public boolean setSyohi_Kigen_Kb(String syohi_kigen_kb)
	{
		this.syohi_kigen_kb = syohi_kigen_kb;
		return true;
	}
	public String getSyohi_Kigen_Kb()
	{
		return cutString(this.syohi_kigen_kb,SYOHI_KIGEN_KB_MAX_LENGTH);
	}
	public String getSyohi_Kigen_KbString()
	{
		return "'" + cutString(this.syohi_kigen_kb,SYOHI_KIGEN_KB_MAX_LENGTH) + "'";
	}
	public String getSyohi_Kigen_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohi_kigen_kb,SYOHI_KIGEN_KB_MAX_LENGTH));
	}

	// syohi_kigen_dtに対するセッターとゲッターの集合
	public boolean setSyohi_Kigen_Dt(String syohi_kigen_dt)
	{
		this.syohi_kigen_dt = syohi_kigen_dt;
		return true;
	}
	public String getSyohi_Kigen_Dt()
	{
		return cutString(this.syohi_kigen_dt,SYOHI_KIGEN_DT_MAX_LENGTH);
	}
	public String getSyohi_Kigen_DtString()
	{
		return "'" + cutString(this.syohi_kigen_dt,SYOHI_KIGEN_DT_MAX_LENGTH) + "'";
	}
	public String getSyohi_Kigen_DtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohi_kigen_dt,SYOHI_KIGEN_DT_MAX_LENGTH));
	}

	// free_1_kbに対するセッターとゲッターの集合
	public boolean setFree_1_Kb(String free_1_kb)
	{
		this.free_1_kb = free_1_kb;
		return true;
	}
	public String getFree_1_Kb()
	{
		return cutString(this.free_1_kb,FREE_1_KB_MAX_LENGTH);
	}
	public String getFree_1_KbString()
	{
		return "'" + cutString(this.free_1_kb,FREE_1_KB_MAX_LENGTH) + "'";
	}
	public String getFree_1_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.free_1_kb,FREE_1_KB_MAX_LENGTH));
	}

	// free_2_kbに対するセッターとゲッターの集合
	public boolean setFree_2_Kb(String free_2_kb)
	{
		this.free_2_kb = free_2_kb;
		return true;
	}
	public String getFree_2_Kb()
	{
		return cutString(this.free_2_kb,FREE_2_KB_MAX_LENGTH);
	}
	public String getFree_2_KbString()
	{
		return "'" + cutString(this.free_2_kb,FREE_2_KB_MAX_LENGTH) + "'";
	}
	public String getFree_2_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.free_2_kb,FREE_2_KB_MAX_LENGTH));
	}

	// free_3_kbに対するセッターとゲッターの集合
	public boolean setFree_3_Kb(String free_3_kb)
	{
		this.free_3_kb = free_3_kb;
		return true;
	}
	public String getFree_3_Kb()
	{
		return cutString(this.free_3_kb,FREE_3_KB_MAX_LENGTH);
	}
	public String getFree_3_KbString()
	{
		return "'" + cutString(this.free_3_kb,FREE_3_KB_MAX_LENGTH) + "'";
	}
	public String getFree_3_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.free_3_kb,FREE_3_KB_MAX_LENGTH));
	}

	// free_4_kbに対するセッターとゲッターの集合
	public boolean setFree_4_Kb(String free_4_kb)
	{
		this.free_4_kb = free_4_kb;
		return true;
	}
	public String getFree_4_Kb()
	{
		return cutString(this.free_4_kb,FREE_4_KB_MAX_LENGTH);
	}
	public String getFree_4_KbString()
	{
		return "'" + cutString(this.free_4_kb,FREE_4_KB_MAX_LENGTH) + "'";
	}
	public String getFree_4_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.free_4_kb,FREE_4_KB_MAX_LENGTH));
	}

	// free_5_kbに対するセッターとゲッターの集合
	public boolean setFree_5_Kb(String free_5_kb)
	{
		this.free_5_kb = free_5_kb;
		return true;
	}
	public String getFree_5_Kb()
	{
		return cutString(this.free_5_kb,FREE_5_KB_MAX_LENGTH);
	}
	public String getFree_5_KbString()
	{
		return "'" + cutString(this.free_5_kb,FREE_5_KB_MAX_LENGTH) + "'";
	}
	public String getFree_5_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.free_5_kb,FREE_5_KB_MAX_LENGTH));
	}

	// comment_1_txに対するセッターとゲッターの集合
	public boolean setComment_1_Tx(String comment_1_tx)
	{
		this.comment_1_tx = comment_1_tx;
		return true;
	}
	public String getComment_1_Tx()
	{
		return cutString(this.comment_1_tx,COMMENT_1_TX_MAX_LENGTH);
	}
	public String getComment_1_TxString()
	{
		return "'" + cutString(this.comment_1_tx,COMMENT_1_TX_MAX_LENGTH) + "'";
	}
	public String getComment_1_TxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.comment_1_tx,COMMENT_1_TX_MAX_LENGTH));
	}

	// comment_2_txに対するセッターとゲッターの集合
	public boolean setComment_2_Tx(String comment_2_tx)
	{
		this.comment_2_tx = comment_2_tx;
		return true;
	}
	public String getComment_2_Tx()
	{
		return cutString(this.comment_2_tx,COMMENT_2_TX_MAX_LENGTH);
	}
	public String getComment_2_TxString()
	{
		return "'" + cutString(this.comment_2_tx,COMMENT_2_TX_MAX_LENGTH) + "'";
	}
	public String getComment_2_TxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.comment_2_tx,COMMENT_2_TX_MAX_LENGTH));
	}

	// free_cdに対するセッターとゲッターの集合
	public boolean setFree_Cd(String free_cd)
	{
		this.free_cd = free_cd;
		return true;
	}
	public String getFree_Cd()
	{
		return cutString(this.free_cd,FREE_CD_MAX_LENGTH);
	}
	public String getFree_CdString()
	{
		return "'" + cutString(this.free_cd,FREE_CD_MAX_LENGTH) + "'";
	}
	public String getFree_CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.free_cd,FREE_CD_MAX_LENGTH));
	}

	// comment_txに対するセッターとゲッターの集合
	public boolean setComment_Tx(String comment_tx)
	{
		this.comment_tx = comment_tx;
		return true;
	}
	public String getComment_Tx()
	{
		return cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH);
	}
	public String getComment_TxString()
	{
		return "'" + cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH) + "'";
	}
	public String getComment_TxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH));
	}

	// others_mod_kbに対するセッターとゲッターの集合
	public boolean setOthers_Mod_Kb(String others_mod_kb)
	{
		this.others_mod_kb = others_mod_kb;
		return true;
	}
	public String getOthers_Mod_Kb()
	{
		return cutString(this.others_mod_kb,OTHERS_MOD_KB_MAX_LENGTH);
	}
	public String getOthers_Mod_KbString()
	{
		return "'" + cutString(this.others_mod_kb,OTHERS_MOD_KB_MAX_LENGTH) + "'";
	}
	public String getOthers_Mod_KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.others_mod_kb,OTHERS_MOD_KB_MAX_LENGTH));
	}

	// #2799対応 2017.02.02 X.Liu Add  (S)
		//Orosi_baitanka_nuki_vl
		public boolean setOrosi_baitanka_nuki_vl(String orosi_baitanka_nuki_vl) {  
		    this.orosi_baitanka_nuki_vl = orosi_baitanka_nuki_vl;
		    return true;
		}
		public String getOrosi_baitanka_nuki_vl() {
		    return cutString(this.orosi_baitanka_nuki_vl,OROSI_BAITANKA_NUKI_VL_MAX_LENGTH);
		}
	    public String getOrosi_baitanka_nuki_vlString()
	    {
	        return "'" + cutString(this.orosi_baitanka_nuki_vl,OROSI_BAITANKA_NUKI_VL_MAX_LENGTH) + "'";
	    }
	    public String getOrosi_baitanka_nuki_vlHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.orosi_baitanka_nuki_vl,OROSI_BAITANKA_NUKI_VL_MAX_LENGTH));
	    }
	    //Min_hachu_qt
	    public boolean setMin_hachu_qt(String min_hachu_qt) {
	        this.min_hachu_qt = min_hachu_qt;
	        return true;
	    }
	    public String getMin_hachu_qt() {
	        return cutString(this.min_hachu_qt,MIN_HACHU_QT_MAX_LENGTH);
	    }
	    public String getMin_hachu_qtString()
	    {
	        return "'" + cutString(this.min_hachu_qt,MIN_HACHU_QT_MAX_LENGTH) + "'";
	    }
	    public String getMin_hachu_qtHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.min_hachu_qt,MIN_HACHU_QT_MAX_LENGTH));
	    }
	    //Plu_hanei_time
	    public boolean setPlu_hanei_time(String plu_hanei_time) {
	        this.plu_hanei_time = plu_hanei_time;
	        return true;
	    }    
	    public String getPlu_hanei_time() {
	        return cutString(this.plu_hanei_time,PLU_HANEI_TIME_MAX_LENGTH);
	    }
	    public String getPlu_hanei_timeString()
	    {
	        return "'" + cutString(this.plu_hanei_time,PLU_HANEI_TIME_MAX_LENGTH) + "'";
	    }
	    public String getPlu_hanei_timeHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.plu_hanei_time,PLU_HANEI_TIME_MAX_LENGTH));
	    }
	    //Syokai_user_id
	    public boolean setSyokai_user_id(String syokai_user_id) {
	        this.syokai_user_id = syokai_user_id;
	        return true;
	    }
	    public String getSyokai_user_id() {
	        return cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH);
	    }
	    public String getSyokai_user_idString()
	    {
	        return "'" + cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH) + "'";
	    }
	    public String getSyokai_user_idHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH));
	    }
	    //Pack_weight_qt
	    public boolean setPack_weight_qt(String pack_weight_qt) {
	        this.pack_weight_qt = pack_weight_qt;
	        return true;
	    }
	    public String getPack_weight_qt() {
	        return cutString(this.pack_weight_qt,PACK_WEIGHT_QT_MAX_LENGTH);
	    }
	    public String getPack_weight_qtString()
	    {
	        return "'" + cutString(this.pack_weight_qt,PACK_WEIGHT_QT_MAX_LENGTH) + "'";
	    }
	    public String getPack_weight_qtHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.pack_weight_qt,PACK_WEIGHT_QT_MAX_LENGTH));
	    }
	    //Country_cd
	    public boolean setCountry_cd(String country_cd) {
	        this.country_cd = country_cd;
	        return true;
	    }
	    public String getCountry_cd() {
	        return cutString(this.country_cd,COUNTRY_CD_MAX_LENGTH);
	    }
	    public String getCountry_cdString()
	    {
	        return "'" + cutString(this.country_cd,COUNTRY_CD_MAX_LENGTH) + "'";
	    }
	    public String getCountry_cdHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.country_cd,COUNTRY_CD_MAX_LENGTH));
	    }
	    //Maker_cd
	    public boolean setMaker_cd(String maker_cd) {
	        this.maker_cd = maker_cd;
	        return true;
	    }
	    public String getMaker_cd() {
	        return cutString(this.maker_cd,MAKER_CD_MAX_LENGTH);
	    }
	    public String getMaker_cdString()
	    {
	        return "'" + cutString(this.maker_cd,MAKER_CD_MAX_LENGTH) + "'";
	    }
	    public String getMaker_cdHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.maker_cd,MAKER_CD_MAX_LENGTH));
	    }
	    //Hanbai_hoho_kb
	    public boolean setHanbai_hoho_kb(String hanbai_hoho_kb) {
	        this.hanbai_hoho_kb = hanbai_hoho_kb;
	        return true;
	    }
	    public String getHanbai_hoho_kb() {
	        return cutString(this.hanbai_hoho_kb,HANBAI_HOHO_KB_MAX_LENGTH);
	    }
	    public String getHanbai_hoho_kbString()
	    {
	        return "'" + cutString(this.hanbai_hoho_kb,HANBAI_HOHO_KB_MAX_LENGTH) + "'";
	    }
	    public String getHanbai_hoho_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.hanbai_hoho_kb,HANBAI_HOHO_KB_MAX_LENGTH));
	    }
	    //Orosi_baitanka_vl
	    public boolean setOrosi_baitanka_vl(String orosi_baitanka_vl) {
	        this.orosi_baitanka_vl = orosi_baitanka_vl;
	        return true;
	    }
	    public String getOrosi_baitanka_vl() {
	        return cutString(this.orosi_baitanka_vl,OROSI_BAITANKA_VL_MAX_LENGTH);
	    }
	    public String getOrosi_baitanka_vlString()
	    {
	        return "'" + cutString(this.orosi_baitanka_vl,OROSI_BAITANKA_VL_MAX_LENGTH) + "'";
	    }
	    public String getOrosi_baitanka_vlHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.orosi_baitanka_vl,OROSI_BAITANKA_VL_MAX_LENGTH));
	    }
	    //Orosi_baitanka_vl_kouri
	    public boolean setOrosi_baitanka_vl_kouri(String orosi_baitanka_vl_kouri) {
	        this.orosi_baitanka_vl_kouri = orosi_baitanka_vl_kouri;
	        return true;
	   }
	    public String getOrosi_baitanka_vl_kouri() {
	        return cutString(this.orosi_baitanka_vl_kouri,OROSI_BAITANKA_VL_KOURI_MAX_LENGTH);
	    }
	    public String getOrosi_baitanka_vl_kouriString()
	    {
	        return "'" + cutString(this.orosi_baitanka_vl_kouri,OROSI_BAITANKA_VL_KOURI_MAX_LENGTH) + "'";
	    }
	    public String getOrosi_baitanka_vl_kouriHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.orosi_baitanka_vl_kouri,OROSI_BAITANKA_VL_KOURI_MAX_LENGTH));
	    }
	    //Siire_zei_kb
	    public boolean setSiire_zei_kb(String siire_zei_kb) {
	        this.siire_zei_kb = siire_zei_kb;
	        return true;
	    }
	    public String getSiire_zei_kb() {
	        return cutString(this.siire_zei_kb,SIIRE_ZEI_KB_MAX_LENGTH);
	    }
	    public String getSiire_zei_kbString()
	    {
	        return "'" + cutString(this.siire_zei_kb,SIIRE_ZEI_KB_MAX_LENGTH) + "'";
	    }
	    public String getSiire_zei_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.siire_zei_kb,SIIRE_ZEI_KB_MAX_LENGTH));
	    }
	    //Orosi_zei_kb
	    public boolean setOrosi_zei_kb(String orosi_zei_kb) {
	        this.orosi_zei_kb = orosi_zei_kb;
	        return true;
	    }
	    public String getOrosi_zei_kb() {
	        return cutString(this.orosi_zei_kb,OROSI_ZEI_KB_MAX_LENGTH);
	    }
	    public String getOrosi_zei_kbString()
	    {
	        return "'" + cutString(this.orosi_zei_kb,OROSI_ZEI_KB_MAX_LENGTH) + "'";
	    }
	    public String getOrosi_zei_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.orosi_zei_kb,OROSI_ZEI_KB_MAX_LENGTH));
	    }
	    //Min_zaiko_qt
	    public boolean setMin_zaiko_qt(String min_zaiko_qt) {
	        this.min_zaiko_qt = min_zaiko_qt;
	        return true;
	    }
	    public String getMin_zaiko_qt() {
	        return cutString(this.min_zaiko_qt,MIN_ZAIKO_QT_MAX_LENGTH);
	    }
	    public String getMin_zaiko_qtString()
	    {
	        return "'" + cutString(this.min_zaiko_qt,MIN_ZAIKO_QT_MAX_LENGTH) + "'";
	    }
	    public String getMin_zaiko_qtHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.min_zaiko_qt,MIN_ZAIKO_QT_MAX_LENGTH));
	    }
	    //Per_txt
	    public boolean setPer_txt(String per_txt) {
	        this.per_txt = per_txt;
	        return true;
	    }
	    public String getPer_txt() {
	        return cutString(this.per_txt,PER_TXT_MAX_LENGTH);
	    }
	    public String getPer_txtString()
	    {
	        return "'" + cutString(this.per_txt,PER_TXT_MAX_LENGTH) + "'";
	    }
	    public String getPer_txtHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.per_txt,PER_TXT_MAX_LENGTH));
	    }
	    //Center_kyoyo_dt
	    public boolean setCenter_kyoyo_dt(String center_kyoyo_dt) {
	        this.center_kyoyo_dt = center_kyoyo_dt;
	        return true;
	    }
	    public String getCenter_kyoyo_dt() {
	        return cutString(this.center_kyoyo_dt,CENTER_KYOYO_DT_MAX_LENGTH);
	    }
	    public String getCenter_kyoyo_dtString()
	    {
	        return "'" + cutString(this.center_kyoyo_dt,CENTER_KYOYO_DT_MAX_LENGTH) + "'";
	    }
	    public String getCenter_kyoyo_dtHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.center_kyoyo_dt,CENTER_KYOYO_DT_MAX_LENGTH));
	    }
	    //Center_kyoyo_kb
	    public boolean setCenter_kyoyo_kb(String center_kyoyo_kb) {
	        this.center_kyoyo_kb = center_kyoyo_kb;
	        return true;
	    }
	    public String getCenter_kyoyo_kb() {
	        return cutString(this.center_kyoyo_kb,CENTER_KYOYO_KB_MAX_LENGTH);
	    }
	    public String getCenter_kyoyo_kbString()
	    {
	        return "'" + cutString(this.center_kyoyo_kb,CENTER_KYOYO_KB_MAX_LENGTH) + "'";
	    }
	    public String getCenter_kyoyo_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.center_kyoyo_kb,CENTER_KYOYO_KB_MAX_LENGTH));
	    }
	    //Syohi_kigen_hyoji_patter
	    public boolean setSyohi_kigen_hyoji_patter(String syohi_kigen_hyoji_patter) {
	        this.syohi_kigen_hyoji_patter = syohi_kigen_hyoji_patter;
	        return true;
	    }
	    public String getSyohi_kigen_hyoji_patter() {
	        return cutString(this.syohi_kigen_hyoji_patter,SYOHI_KIGEN_HYOJI_PATTER_MAX_LENGTH);
	    }
	    public String getSyohi_kigen_hyoji_patterString()
	    {
	        return "'" + cutString(this.syohi_kigen_hyoji_patter,SYOHI_KIGEN_HYOJI_PATTER_MAX_LENGTH) + "'";
	    }
	    public String getSyohi_kigen_hyoji_patterHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.syohi_kigen_hyoji_patter,SYOHI_KIGEN_HYOJI_PATTER_MAX_LENGTH));
	    }
	    //Syohin_eiji_na
	    public boolean setSyohin_eiji_na(String syohin_eiji_na) {
	        this.syohin_eiji_na = syohin_eiji_na;
	        return true;
	    }
	    public String getSyohin_eiji_na() {
	        return cutString(this.syohin_eiji_na,SYOHIN_EIJI_NA_MAX_LENGTH);
	    }
	    public String getSyohin_eiji_naString()
	    {
	        return "'" + cutString(this.syohin_eiji_na,SYOHIN_EIJI_NA_MAX_LENGTH) + "'";
	    }
	    public String getSyohin_eiji_naHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.syohin_eiji_na,SYOHIN_EIJI_NA_MAX_LENGTH));
	    }
	    //label_hokan_hoho
	    public boolean setLabel_hokan_hoho(String label_hokan_hoho) {
	        this.label_hokan_hoho = label_hokan_hoho;
	        return true;
	    }
	    public String getLabel_hokan_hoho() {
	        return cutString(this.label_hokan_hoho,LABEL_HOKAN_HOHO_MAX_LENGTH);
	    }
	    public String getLabel_hokan_hohoString()
	    {
	        return "'" + cutString(this.label_hokan_hoho,LABEL_HOKAN_HOHO_MAX_LENGTH) + "'";
	    }
	    public String getLabel_hokan_hohoHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.label_seibun,LABEL_HOKAN_HOHO_MAX_LENGTH));
	    }
	    //Label_seibun
	    public boolean setLabel_seibun(String label_seibun) {
	        this.label_seibun = label_seibun;
	        return true;
	    }
	    public String getLabel_seibun() {
	        return cutString(this.label_seibun,LABEL_SEIBUN_MAX_LENGTH);
	    }
	    public String getLabel_seibunString()
	    {
	        return "'" + cutString(this.label_seibun,LABEL_SEIBUN_MAX_LENGTH) + "'";
	    }
	    public String getLabel_seibunHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.label_seibun,LABEL_SEIBUN_MAX_LENGTH));
	    }
	    //Label_tukaikata
	    public boolean setLabel_tukaikata(String label_tukaikata) {
	        this.label_tukaikata = label_tukaikata;
	        return true;
	    }
	    public String getLabel_tukaikata() {
	        return cutString(this.label_tukaikata,LABEL_TUKAIKATA_MAX_LENGTH);
	    }
	    public String getLabel_tukaikataString()
	    {
	        return "'" + cutString(this.label_tukaikata,LABEL_TUKAIKATA_MAX_LENGTH) + "'";
	    }
	    public String getLabel_tukaikataHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.label_tukaikata,LABEL_TUKAIKATA_MAX_LENGTH));
	    }
	    //Label_tukaikata
	    public boolean setcgc_henpin_kb(String label_tukaikata) {
	        this.label_tukaikata = label_tukaikata;
	        return true;
	    }
	    public String getcgc_henpin_kb() {
	        return cutString(this.cgc_henpin_kb,CGC_HENPIN_KB_MAX_LENGTH);
	    }
	    public String getcgc_henpin_kbString()
	    {
	        return "'" + cutString(this.cgc_henpin_kb,CGC_HENPIN_KB_MAX_LENGTH) + "'";
	    }
	    public String getcgc_henpin_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.cgc_henpin_kb,CGC_HENPIN_KB_MAX_LENGTH));
	    }

	   //Nenrei_seigen_kb
	    public boolean setNenrei_seigen_kb(String nenrei_seigen_kb) {
	        this.nenrei_seigen_kb = nenrei_seigen_kb;
	        return true;
	    }
	    public String getNenrei_seigen_kb() {
	        return cutString(this.nenrei_seigen_kb,NENREI_SEIGEN_KB_MAX_LENGTH);
	    }
	    public String getNenrei_seigen_kbString()
	    {
	        return "'" + cutString(this.nenrei_seigen_kb,NENREI_SEIGEN_KB_MAX_LENGTH) + "'";
	    }
	    public String getNenrei_seigen_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.nenrei_seigen_kb,NENREI_SEIGEN_KB_MAX_LENGTH));
	    }
	    //Nenrei
	    public boolean setNenrei(String nenrei) {
	        this.nenrei = nenrei;
	        return true;
	    }
	    public String getNenrei() {
	        return cutString(this.nenrei,NENREI_MAX_LENGTH);
	    }
	    public String getNenreiString()
	    {
	        return "'" + cutString(this.nenrei,NENREI_MAX_LENGTH) + "'";
	    }
	    public String getNenreiHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.nenrei,NENREI_MAX_LENGTH));
	    }
	    //Kan_kb
	    public boolean setKan_kb(String kan_kb) {
	        this.kan_kb = kan_kb;
	        return true;
	    }
	    public String getKan_kb() {
	        return cutString(this.kan_kb,KAN_KB_MAX_LENGTH);
	    }
	    public String getKan_kbString()
	    {
	        return "'" + cutString(this.kan_kb,KAN_KB_MAX_LENGTH) + "'";
	    }
	    public String getKan_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.kan_kb,KAN_KB_MAX_LENGTH));
	    }
	    //Hoshoukin
	    public boolean setHoshoukin(String hoshoukin) {
	        this.hoshoukin = hoshoukin;
	        return true;
	    }
	    public String getHoshoukin() {
	        return cutString(this.hoshoukin,HOSHOUKIN_MAX_LENGTH);
	    }
	    public String getHoshoukinString()
	    {
	        return "'" + cutString(this.hoshoukin,HOSHOUKIN_MAX_LENGTH) + "'";
	    }
	    public String getHoshoukinHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.hoshoukin,HOSHOUKIN_MAX_LENGTH));
	    }
	    //Security_tag_fg
	    public boolean setSecurity_tag_fg(String security_tag_fg) {
	        this.security_tag_fg = security_tag_fg;
	        return true;
	   }
	    public String getSecurity_tag_fg() {
	        return cutString(this.security_tag_fg,SECURITY_TAG_FG_MAX_LENGTH);
	    }
	    public String getSecurity_tag_fgString()
	    {
	        return "'" + cutString(this.security_tag_fg,SECURITY_TAG_FG_MAX_LENGTH) + "'";
	    }
	    public String getSecurity_tag_fgHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.security_tag_fg,SECURITY_TAG_FG_MAX_LENGTH));
	    }
	    //Hamper_syohin_fg
	    public boolean setHamper_syohin_fg(String hamper_syohin_fg) {
	        this.hamper_syohin_fg = hamper_syohin_fg;
	        return true;
	    }
	    public String getHamper_syohin_fg() {
	        return cutString(this.hamper_syohin_fg,HAMPER_SYOHIN_FG_MAX_LENGTH);
	    }
	    public String getHamper_syohin_fgString()
	    {
	        return "'" + cutString(this.hamper_syohin_fg,HAMPER_SYOHIN_FG_MAX_LENGTH) + "'";
	    }
	    public String getHamper_syohin_fgHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.hamper_syohin_fg,HAMPER_SYOHIN_FG_MAX_LENGTH));
	    }
	    //sinki_toroku_dt
	    public boolean setSinki_toroku_dt(String sinki_toroku_dt) {
	        this.sinki_toroku_dt = sinki_toroku_dt;
	        return true;
	    }
	    public String getSinki_toroku_dt() {
	        return cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH);
	    }
	    public String getSinki_toroku_dtString()
	    {
	        return "'" + cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH) + "'";
	    }
	    public String getSinki_toroku_dtHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH));
	    }
	    //sinki_toroku_dt
	    public boolean setSiire_tax_rate_kb(String siire_tax_rate_kb) {
	        this.siire_tax_rate_kb = siire_tax_rate_kb;
	        return true;
	    }
	    public String getSiire_tax_rate_kb() {
	        return cutString(this.siire_tax_rate_kb,SIIRE_TAX_RATE_KB_MAX_LENGTH);
	    }
	    public String getSiire_tax_rate_kbString()
	    {
	        return "'" + cutString(this.siire_tax_rate_kb,SIIRE_TAX_RATE_KB_MAX_LENGTH) + "'";
	    }
	    public String getSiire_tax_rate_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.siire_tax_rate_kb,SIIRE_TAX_RATE_KB_MAX_LENGTH));
	    }
	    //orosi_tax_rate_kb
	    public boolean setOrosi_tax_rate_kb(String orosi_tax_rate_kb) {
	        this.orosi_tax_rate_kb = orosi_tax_rate_kb;
	        return true;
	    }
	    public String getOrosi_tax_rate_kb() {
	        return cutString(this.orosi_tax_rate_kb,OROSI_TAX_RATE_KB_MAX_LENGTH);
	    }
	    public String getOrosi_tax_rate_kbString()
	    {
	        return "'" + cutString(this.orosi_tax_rate_kb,OROSI_TAX_RATE_KB_MAX_LENGTH) + "'";
	    }
	    public String getOrosi_tax_rate_kbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.orosi_tax_rate_kb,OROSI_TAX_RATE_KB_MAX_LENGTH));
	    }
	    // #2799対応 2017.02.02 X.Liu Add  (E)

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{

		return "  generation = " + getGenerationString()
			+ "  syohin_cd = " + getSyohin_CdString()
			+ "  bunrui1_cd_for_kaipage = " + getBunrui1_Cd_For_KaipageString()
			+ "  kb = " + getKbString()
			+ "  yuko_dt = " + getYuko_DtString()
			+ "  bunrui1_cd = " + getBunrui1_CdString()
			+ "  bunrui5_cd = " + getBunrui5_CdString()
			+ "  kikaku_kanji_2_na = " + getKikaku_Kanji_2_NaString()
			+ "  hinmei_kanji_na = " + getHinmei_Kanji_NaString()
			+ "  rec_hinmei_kanji_na = " + getRec_Hinmei_Kanji_NaString()
			// #2799対応 2017.02.02 X.Liu Del  (S)
			//+ "  hinmei_kana_na = " + getHinmei_Kana_NaString()
			// #2799対応 2017.02.02 X.Liu Del  (E)
			+ "  kikaku_kanji_na = " + getKikaku_Kanji_NaString()
			+ "  baitanka_vl = " + getBaitanka_VlString()
			// #2799対応 2017.02.02 X.Liu Mod  (S)
			//+ "  baika_haishin_fg = " + getBaika_Haishin_FgString()
			+ "  gentanka_vl = " + getGentanka_VlString()
			+ "  orosi_baitanka_nuki_vl = " + getOrosi_baitanka_nuki_vl()
			// #2799対応 2017.02.02 X.Liu Mod  (E)
			+ "  siiresaki_cd = " + getSiiresaki_CdString()
			// #2799対応 2017.02.02 X.Liu Add  (S)
			+ "  min_hachu_qt = " + getMin_hachu_qt()
			+ "  plu_hanei_time = " + getPlu_hanei_time()
			// #2799対応 2017.02.02 X.Liu Add  (E)
			+ "  hachutani_irisu_qt = " + getHachutani_Irisu_QtString()
			//# #2799対応 2017.02.02 X.Liu Del  (S)
			//+ "  eos_kb = " + getEos_KbString()
			//+ "  bin_1_kb = " + getBin_1_KbString()
			//+ "  bin_2_kb = " + getBin_2_KbString()
			// #2799対応 2017.02.02 X.Liu Del  (E)
			+ "  update_user_id = " + getUpdate_User_IdString()
			+ "  update_ts = " + getUpdate_TsString()
			// #2799対応 2017.02.02 X.Liu Add  (S)
			+ "  syokai_user_id = " + getSyokai_user_id()
			// #2799対応 2017.02.02 X.Liu Add  (S)
			+ "  system_kb = " + getSystem_KbString()
			+ "  gyosyu_kb = " + getGyosyu_KbString()
			+ "  syohin_kb = " + getSyohin_KbString()
			+ "  bunrui2_cd = " + getBunrui2_CdString()
			+ "  syohin_2_cd = " + getSyohin_2_CdString()
			+ "  zaiko_syohin_cd = " + getZaiko_Syohin_CdString()
			+ "  jyoho_syohin_cd = " + getJyoho_Syohin_CdString()
			+ "  kikaku_kana_na = " + getKikaku_Kana_NaString()
			+ "  kikaku_kana_2_na = " + getKikaku_Kana_2_NaString()
			+ "  rec_hinmei_kana_na = " + getRec_Hinmei_Kana_NaString()
			+ "  syohin_width_qt = " + getSyohin_Width_QtString()
			+ "  syohin_height_qt = " + getSyohin_Height_QtString()
			+ "  syohin_depth_qt = " + getSyohin_Depth_QtString()
			+ "  zei_kb = " + getZei_KbString()
			+ "  tax_rate_kb = " + getTax_Rate_KbString()
			+ "  gentanka_nuki_vl = " + getGentanka_Nuki_VlString()
			+ "  baitanka_nuki_vl = " + getBaitanka_Nuki_VlString()
			+ "  maker_kibo_kakaku_vl = " + getMaker_Kibo_Kakaku_VlString()
			+ "  hacyu_syohin_kb = " + getHacyu_Syohin_KbString()
			+ "  hacyu_syohin_cd = " + getHacyu_Syohin_CdString()
			+ "  hachu_tani_na = " + getHachu_Tani_NaString()
			+ "  hanbai_tani = " + getHanbai_TaniString()
			+ "  max_hachutani_qt = " + getMax_Hachutani_QtString()
			+ "  case_hachu_kb = " + getCase_Hachu_KbString()
			+ "  bara_irisu_qt = " + getBara_Irisu_QtString()
			+ "  ten_hachu_st_dt = " + getTen_Hachu_St_DtString()
			+ "  ten_hachu_ed_dt = " + getTen_Hachu_Ed_DtString()
			+ "  hanbai_st_dt = " + getHanbai_St_DtString()
			+ "  hanbai_ed_dt = " + getHanbai_Ed_DtString()
			+ "  hanbai_kikan_kb = " + getHanbai_Kikan_KbString()
			+ "  teikan_kb = " + getTeikan_KbString()
			+ "  soba_syohin_kb = " + getSoba_Syohin_KbString()
			+ "  nohin_kigen_kb = " + getNohin_Kigen_KbString()
			+ "  nohin_kigen_qt = " + getNohin_Kigen_QtString()
			+ "  hachu_pattern_1_kb = " + getHachu_Pattern_1_KbString()
			+ "  sime_time_1_qt = " + getSime_Time_1_QtString()
			+ "  c_nohin_rtime_1_kb = " + getC_Nohin_Rtime_1_KbString()
			+ "  ten_nohin_rtime_1_kb = " + getTen_Nohin_Rtime_1_KbString()
			+ "  ten_nohin_time_1_kb = " + getTen_Nohin_Time_1_KbString()
			+ "  hachu_pattern_2_kb = " + getHachu_Pattern_2_KbString()
			+ "  sime_time_2_qt = " + getSime_Time_2_QtString()
			+ "  c_nohin_rtime_2_kb = " + getC_Nohin_Rtime_2_KbString()
			+ "  ten_nohin_rtime_2_kb = " + getTen_Nohin_Rtime_2_KbString()
			+ "  ten_nohin_time_2_kb = " + getTen_Nohin_Time_2_KbString()
			+ "  yusen_bin_kb = " + getYusen_Bin_KbString()
			+ "  f_bin_kb = " + getF_Bin_KbString()
			+ "  buturyu_kb = " + getButuryu_KbString()
			+ "  got_start_mm = " + getGot_Start_MmString()
			+ "  got_end_mm = " + getGot_End_MmString()
			+ "  hachu_teisi_kb = " + getHachu_Teisi_KbString()
			+ "  cgc_henpin_kb = " + getCgc_Henpin_KbString()
			+ "  hanbai_limit_kb = " + getHanbai_Limit_KbString()
			+ "  hanbai_limit_qt = " + getHanbai_Limit_QtString()
			+ "  plu_send_dt = " + getPlu_Send_DtString()
			+ "  keyplu_fg = " + getKeyplu_FgString()
			+ "  plu_kb = " + getPlu_KbString()
			+ "  syuzei_hokoku_kb = " + getSyuzei_Hokoku_KbString()
			+ "  sake_naiyoryo_qt = " + getSake_Naiyoryo_QtString()
			+ "  tag_hyoji_baika_vl = " + getTag_Hyoji_Baika_VlString()
			+ "  keshi_baika_vl = " + getKeshi_Baika_VlString()
			+ "  yoridori_kb = " + getYoridori_KbString()
			+ "  pc_kb = " + getPc_KbString()
			+ "  daisi_no_nb = " + getDaisi_No_NbString()
			+ "  unit_price_tani_kb = " + getUnit_Price_Tani_KbString()
			+ "  unit_price_naiyoryo_qt = " + getUnit_Price_Naiyoryo_QtString()
			+ "  unit_price_kijun_tani_qt = " + getUnit_Price_Kijun_Tani_QtString()
			+ "  syohi_kigen_kb = " + getSyohi_Kigen_KbString()
			+ "  syohi_kigen_dt = " + getSyohi_Kigen_DtString()
			+ "  free_1_kb = " + getFree_1_KbString()
			+ "  free_2_kb = " + getFree_2_KbString()
			+ "  free_3_kb = " + getFree_3_KbString()
			+ "  free_4_kb = " + getFree_4_KbString()
			+ "  free_5_kb = " + getFree_5_KbString()
			+ "  comment_1_tx = " + getComment_1_TxString()
			+ "  comment_2_tx = " + getComment_2_TxString()
			+ "  free_cd = " + getFree_CdString()
			+ "  comment_tx = " + getComment_TxString()
			+ "  others_mod_kb = " + getOthers_Mod_KbString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mstC01111_KeiryokiYobidashiNoOutputBean コピー後のクラス
	 */
	public mst010181_SyohinMasterHenkoListOutputBean createClone()
	{
		mst010181_SyohinMasterHenkoListOutputBean bean = new mst010181_SyohinMasterHenkoListOutputBean();
		bean.setGeneration(this.generation);
		bean.setSyohin_Cd(this.syohin_cd);
		bean.setBunrui1_Cd_For_Kaipage(this.bunrui1_cd_for_kaipage);
		bean.setKb(this.kb);
		bean.setYuko_Dt(this.yuko_dt);
		bean.setBunrui1_Cd(this.bunrui1_cd);
		bean.setBunrui5_Cd(this.bunrui5_cd);
		bean.setKikaku_Kanji_2_Na(this.kikaku_kanji_2_na);
		bean.setHinmei_Kanji_Na(this.hinmei_kanji_na);
		bean.setRec_Hinmei_Kanji_Na(this.rec_hinmei_kanji_na);
		// #2799対応 2017.02.02 X.Liu Del  (S)
		//bean.setHinmei_Kana_Na(this.hinmei_kana_na);
		// #2799対応 2017.02.02 X.Liu Del  (E)
		bean.setKikaku_Kanji_Na(this.kikaku_kanji_na);
		bean.setBaitanka_Vl(this.baitanka_vl);
		// #2799対応 2017.02.02 X.Liu Mod  (S)
		//bean.setBaika_Haishin_Fg(this.baika_haishin_fg);
		bean.setGentanka_Vl(this.gentanka_vl);
		bean.setOrosi_baitanka_nuki_vl(this.orosi_baitanka_nuki_vl);
		// #2799対応 2017.02.02 X.Liu Mod  (E)
		bean.setSiiresaki_Cd(this.siiresaki_cd);
		// #2799対応 2017.02.02 X.Liu Add  (S)
		bean.setMin_hachu_qt(this.min_hachu_qt);
		bean.setPlu_hanei_time(this.plu_hanei_time);
		// #2799対応 2017.02.02 X.Liu Add  (E)
		bean.setHachutani_Irisu_Qt(this.hachutani_irisu_qt);
		// #2799対応 2017.02.02 X.Liu Del  (S)
		//bean.setEos_Kb(this.eos_kb);
		//bean.setBin_1_Kb(this.bin_1_kb);
		//bean.setBin_2_Kb(this.bin_2_kb);
		// #2799対応 2017.02.02 X.Liu Del  (S)
		bean.setUpdate_User_Id(this.update_user_id);
		bean.setUpdate_Ts(this.update_ts);
		// #2799対応 2017.02.02 X.Liu Add  (S)
		bean.setUpdate_Ts(this.syokai_user_id);
		// #2799対応 2017.02.02 X.Liu Add  (E)
		bean.setSystem_Kb(this.system_kb);
		bean.setGyosyu_Kb(this.gyosyu_kb);
		bean.setSyohin_Kb(this.syohin_kb);
		bean.setBunrui2_Cd(this.bunrui2_cd);
		bean.setSyohin_2_Cd(this.syohin_2_cd);
		bean.setZaiko_Syohin_Cd(this.zaiko_syohin_cd);
		bean.setJyoho_Syohin_Cd(this.jyoho_syohin_cd);
		bean.setKikaku_Kana_Na(this.kikaku_kana_na);
		bean.setKikaku_Kana_2_Na(this.kikaku_kana_2_na);
		bean.setRec_Hinmei_Kana_Na(this.rec_hinmei_kana_na);
		bean.setSyohin_Width_Qt(this.syohin_width_qt);
		bean.setSyohin_Height_Qt(this.syohin_height_qt);
		bean.setSyohin_Depth_Qt(this.syohin_depth_qt);
		bean.setZei_Kb(this.zei_kb);
		bean.setTax_Rate_Kb(this.tax_rate_kb);
		bean.setGentanka_Nuki_Vl(this.gentanka_nuki_vl);
		bean.setBaitanka_Nuki_Vl(this.baitanka_nuki_vl);
		bean.setMaker_Kibo_Kakaku_Vl(this.maker_kibo_kakaku_vl);
		bean.setHacyu_Syohin_Kb(this.hacyu_syohin_kb);
		bean.setHacyu_Syohin_Cd(this.hacyu_syohin_cd);
		bean.setHachu_Tani_Na(this.hachu_tani_na);
		bean.setHanbai_Tani(this.hanbai_tani);
		bean.setMax_Hachutani_Qt(this.max_hachutani_qt);
		bean.setCase_Hachu_Kb(this.case_hachu_kb);
		bean.setBara_Irisu_Qt(this.bara_irisu_qt);
		bean.setTen_Hachu_St_Dt(this.ten_hachu_st_dt);
		bean.setTen_Hachu_Ed_Dt(this.ten_hachu_ed_dt);
		bean.setHanbai_St_Dt(this.hanbai_st_dt);
		bean.setHanbai_Ed_Dt(this.hanbai_ed_dt);
		bean.setHanbai_Kikan_Kb(this.hanbai_kikan_kb);
		bean.setTeikan_Kb(this.teikan_kb);
		bean.setSoba_Syohin_Kb(this.soba_syohin_kb);
		bean.setNohin_Kigen_Kb(this.nohin_kigen_kb);
		bean.setNohin_Kigen_Qt(this.nohin_kigen_qt);
		bean.setHachu_Pattern_1_Kb(this.hachu_pattern_1_kb);
		bean.setSime_Time_1_Qt(this.sime_time_1_qt);
		bean.setC_Nohin_Rtime_1_Kb(this.c_nohin_rtime_1_kb);
		bean.setTen_Nohin_Rtime_1_Kb(this.ten_nohin_rtime_1_kb);
		bean.setTen_Nohin_Time_1_Kb(this.ten_nohin_time_1_kb);
		bean.setHachu_Pattern_2_Kb(this.hachu_pattern_2_kb);
		bean.setSime_Time_2_Qt(this.sime_time_2_qt);
		bean.setC_Nohin_Rtime_2_Kb(this.c_nohin_rtime_2_kb);
		bean.setTen_Nohin_Rtime_2_Kb(this.ten_nohin_rtime_2_kb);
		bean.setTen_Nohin_Time_2_Kb(this.ten_nohin_time_2_kb);
		bean.setYusen_Bin_Kb(this.yusen_bin_kb);
		bean.setF_Bin_Kb(this.f_bin_kb);
		bean.setButuryu_Kb(this.buturyu_kb);
		bean.setGot_Start_Mm(this.got_start_mm);
		bean.setGot_End_Mm(this.got_end_mm);
		bean.setHachu_Teisi_Kb(this.hachu_teisi_kb);
		bean.setCgc_Henpin_Kb(this.cgc_henpin_kb);
		bean.setHanbai_Limit_Kb(this.hanbai_limit_kb);
		bean.setHanbai_Limit_Qt(this.hanbai_limit_qt);
		bean.setPlu_Send_Dt(this.plu_send_dt);
		bean.setKeyplu_Fg(this.keyplu_fg);
		bean.setPlu_Kb(this.plu_kb);
		bean.setSyuzei_Hokoku_Kb(this.syuzei_hokoku_kb);
		bean.setSake_Naiyoryo_Qt(this.sake_naiyoryo_qt);
		bean.setTag_Hyoji_Baika_Vl(this.tag_hyoji_baika_vl);
		bean.setKeshi_Baika_Vl(this.keshi_baika_vl);
		bean.setYoridori_Kb(this.yoridori_kb);
		bean.setPc_Kb(this.pc_kb);
		bean.setDaisi_No_Nb(this.daisi_no_nb);
		bean.setUnit_Price_Tani_Kb(this.unit_price_tani_kb);
		bean.setUnit_Price_Naiyoryo_Qt(this.unit_price_naiyoryo_qt);
		bean.setUnit_Price_Kijun_Tani_Qt(this.unit_price_kijun_tani_qt);
		bean.setSyohi_Kigen_Kb(this.syohi_kigen_kb);
		bean.setSyohi_Kigen_Dt(this.syohi_kigen_dt);
		// #2799対応 2017.02.02 X.Liu Add  (S)
		bean.setPer_txt(this.per_txt);
		bean.setMin_zaiko_qt(this.min_zaiko_qt);
		bean.setOrosi_zei_kb(this.orosi_zei_kb);
		bean.setSiire_zei_kb(this.siire_zei_kb);
		bean.setOrosi_baitanka_vl_kouri(this.orosi_baitanka_vl_kouri);
		bean.setOrosi_baitanka_vl(this.orosi_baitanka_vl);
		bean.setHanbai_hoho_kb(this.maker_cd);
		bean.setMaker_cd(this.maker_cd);
		bean.setPack_weight_qt(this.pack_weight_qt);
		bean.setCountry_cd(this.country_cd);
		bean.setGot_End_Mm(this.got_end_mm);
		bean.setPc_Kb(this.pc_kb);
		bean.setDaisi_No_Nb(this.daisi_no_nb);
		bean.setHanbai_Limit_Qt(this.hanbai_limit_qt);
		bean.setHanbai_Limit_Kb(this.hanbai_limit_kb);
		bean.setCenter_kyoyo_dt(this.center_kyoyo_dt);
		bean.setCenter_kyoyo_kb(this.center_kyoyo_kb);
		bean.setUnit_Price_Tani_Kb(this.unit_price_tani_kb);
		bean.setUnit_Price_Naiyoryo_Qt(this.unit_price_naiyoryo_qt);
		bean.setUnit_Price_Tani_Kb(this.unit_price_kijun_tani_qt);
		bean.setSyohi_kigen_hyoji_patter(this.syohi_kigen_hyoji_patter);
		bean.setSyohi_Kigen_Dt(this.syohi_kigen_dt);
		bean.setSyohin_eiji_na(this.syohin_eiji_na);
		bean.setLabel_seibun(this.label_seibun);
		bean.setLabel_hokan_hoho(this.label_hokan_hoho);
		bean.setLabel_tukaikata(this.label_tukaikata);
		bean.setCgc_Henpin_Kb(this.cgc_henpin_kb);
		bean.setNenrei_seigen_kb(this.nenrei_seigen_kb);
		bean.setNenrei(this.nenrei);
		bean.setKan_kb(this.kan_kb);
		bean.setHoshoukin(this.hoshoukin);
		bean.setSecurity_tag_fg(this.security_tag_fg);
		bean.setHamper_syohin_fg(this.hamper_syohin_fg);
		bean.setSinki_toroku_dt(this.sinki_toroku_dt);
		// #2799対応 2017.02.02 X.Liu Add  (E)
		bean.setSiire_tax_rate_kb(this.orosi_tax_rate_kb);
		bean.setSiire_tax_rate_kb(this.siire_tax_rate_kb);
		bean.setFree_1_Kb(this.free_1_kb);
		bean.setFree_2_Kb(this.free_2_kb);
		bean.setFree_3_Kb(this.free_3_kb);
		bean.setFree_4_Kb(this.free_4_kb);
		bean.setFree_5_Kb(this.free_5_kb);
		bean.setComment_1_Tx(this.comment_1_tx);
		bean.setComment_2_Tx(this.comment_2_tx);
		bean.setFree_Cd(this.free_cd);
		bean.setComment_Tx(this.comment_tx);
		bean.setOthers_Mod_Kb(this.others_mod_kb);

		if( this.isCreateDatabase() ) bean.setCreateDatabase();
		return bean;
	}
	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o )
	{
		if( o == null ) return false;
		if( !( o instanceof mst010181_SyohinMasterHenkoListOutputBean ) ) return false;
		return this.toString().equals( o.toString() );
	}
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max )
	{
		if( base == null ) return null;
		String wk = "";
		for( int pos = 0,count = 0; pos < max && pos < base.length(); pos++ )
		{
			try
			{
				//#2799 X.Liu 2017.02.07 Mod (S)
//				byte bt[] = base.substring(pos,pos+1).getBytes("UTF-8");
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				//#2799 X.Liu 2017.02.07 Mod (E)
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			}
			catch(Exception e)
			{
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
