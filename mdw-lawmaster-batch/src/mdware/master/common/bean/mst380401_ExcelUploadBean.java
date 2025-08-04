package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム登録票アープロッドのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する登録票アープロッドのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author baozy
 * @version 1.0
 */
public class mst380401_ExcelUploadBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	public static final int FRE_TORIKOMI_DT_MAX_LENGTH=8;
	public static final int FRE_EXCEL_FILE_SYUBETSU_MAX_LENGTH=3;
	public static final int FRE_BUMON_CD_MAX_LENGTH=4;
	public static final int FRE_UNIT_CD_MAX_LENGTH=4;
	public static final int FRE_HAIFU_CD_MAX_LENGTH=2;
	public static final int FRE_SUBCLASS_CD_MAX_LENGTH=2;
	public static final int FRE_SYOHIN_CD_MAX_LENGTH=13;
	public static final int FRE_YUKO_DT_MAX_LENGTH=8;
	public static final int FRE_MAKER_CD_MAX_LENGTH=9;
	public static final int FRE_HINMEI_KANJI_NA_MAX_LENGTH=80;
	public static final int FRE_KIKAKU_KANJI_NA_MAX_LENGTH=40;
	public static final int FRE_KIKAKU_KANJI_2_NA_MAX_LENGTH=40;
	public static final int FRE_HINMEI_KANA_NA_MAX_LENGTH=80;
	public static final int FRE_KIKAKU_KANA_NA_MAX_LENGTH=40;
	public static final int FRE_KIKAKU_KANA_2_NA_MAX_LENGTH=40;
	public static final int FRE_REC_HINMEI_KANJI_NA_MAX_LENGTH=20;
	public static final int FRE_REC_HINMEI_KANA_NA_MAX_LENGTH=20;
	public static final int FRE_GENTANKA_VL_MAX_LENGTH=10;
	public static final int FRE_BAITANKA_VL_MAX_LENGTH=7;
	public static final int FRE_PLU_SEND_DT_MAX_LENGTH=8;
//	public static final int FRE_AREA_CD_MAX_LENGTH=3;
	public static final int FRE_SIIRESAKI_CD_MAX_LENGTH=6;
	public static final int FRE_TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH=4;
	public static final int FRE_SIIRE_HINBAN_CD_MAX_LENGTH=15;
	public static final int FRE_HANBAI_ST_DT_MAX_LENGTH=8;
	public static final int FRE_HANBAI_ED_DT_MAX_LENGTH=8;
	public static final int FRE_TEN_HACHU_ST_DT_MAX_LENGTH=8;
	public static final int FRE_TEN_HACHU_ED_DT_MAX_LENGTH=8;
	public static final int FRE_EOS_KB_MAX_LENGTH=1;
	public static final int FRE_HONBU_ZAI_KB_MAX_LENGTH=1;
	public static final int FRE_HACHU_TANI_NA_MAX_LENGTH=2;
	public static final int FRE_HACHUTANI_IRISU_QT_MAX_LENGTH=6;
	public static final int FRE_MAX_HACHUTANI_QT_MAX_LENGTH=6;
	public static final int FRE_BLAND_CD_MAX_LENGTH=3;
	public static final int FRE_PB_KB_MAX_LENGTH=1;
	public static final int FRE_MAKER_KIBO_KAKAKU_VL_MAX_LENGTH=7;
	public static final int FRE_BIN_1_KB_MAX_LENGTH=1;
	public static final int FRE_BIN_2_KB_MAX_LENGTH=1;
	public static final int FRE_YUSEN_BIN_KB_MAX_LENGTH=1;
	public static final int FRE_FUJI_SYOHIN_KB_MAX_LENGTH=1;
	public static final int FRE_PC_KB_MAX_LENGTH=1;
	public static final int FRE_DAISI_NO_NB_MAX_LENGTH=2;
	public static final int FRE_DAICHO_TENPO_KB_MAX_LENGTH=1;
	public static final int FRE_DAICHO_HONBU_KB_MAX_LENGTH=1;
	public static final int FRE_DAICHO_SIIRESAKI_KB_MAX_LENGTH=1;
	public static final int FRE_UNIT_PRICE_TANI_KB_MAX_LENGTH=2;
	public static final int FRE_UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH=4;
	public static final int FRE_UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH=4;
	public static final int FRE_SYOHI_KIGEN_DT_MAX_LENGTH=3;
	public static final int FRE_SYUSEI_KB_MAX_LENGTH=1;
	public static final int FRE_MST_MAINTE_FG_MAX_LENGTH=1;
	public static final int FRE_ALARM_MAKE_FG_MAX_LENGTH=1;
	public static final int FRE_INSERT_TS_MAX_LENGTH=14;
	public static final int FRE_INSERT_USER_ID_MAX_LENGTH=20;
	public static final int FRE_UPDATE_TS_MAX_LENGTH=14;
	public static final int FRE_UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int GRO_TORIKOMI_DT_MAX_LENGTH=8;
	public static final int GRO_EXCEL_FILE_SYUBETSU_MAX_LENGTH=3;
	public static final int GRO_BUMON_CD_MAX_LENGTH=4;
	public static final int GRO_UNIT_CD_MAX_LENGTH=4;
	public static final int GRO_HAIFU_CD_MAX_LENGTH=2;
	public static final int GRO_SUBCLASS_CD_MAX_LENGTH=2;
	public static final int GRO_SYOHIN_CD_MAX_LENGTH=13;
	public static final int GRO_YUKO_DT_MAX_LENGTH=8;
	public static final int GRO_MAKER_CD_MAX_LENGTH=9;
	public static final int GRO_HINMEI_KANJI_NA_MAX_LENGTH=80;
	public static final int GRO_KIKAKU_KANJI_NA_MAX_LENGTH=40;
	public static final int GRO_KIKAKU_KANJI_2_NA_MAX_LENGTH=40;
	public static final int GRO_HINMEI_KANA_NA_MAX_LENGTH=80;
	public static final int GRO_KIKAKU_KANA_NA_MAX_LENGTH=40;
	public static final int GRO_KIKAKU_KANA_2_NA_MAX_LENGTH=40;
	public static final int GRO_REC_HINMEI_KANJI_NA_MAX_LENGTH=20;
	public static final int GRO_REC_HINMEI_KANA_NA_MAX_LENGTH=20;
	public static final int GRO_GENTANKA_VL_MAX_LENGTH=10;
	public static final int GRO_BAITANKA_VL_MAX_LENGTH=7;
	public static final int GRO_PLU_SEND_DT_MAX_LENGTH=8;
//	public static final int GRO_AREA_CD_MAX_LENGTH=3;
	public static final int GRO_SIIRESAKI_CD_MAX_LENGTH=6;
	public static final int GRO_TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH=4;
	public static final int GRO_SIIRE_HINBAN_CD_MAX_LENGTH=15;
	public static final int GRO_HANBAI_ST_DT_MAX_LENGTH=8;
	public static final int GRO_HANBAI_ED_DT_MAX_LENGTH=8;
	public static final int GRO_TEN_HACHU_ST_DT_MAX_LENGTH=8;
	public static final int GRO_TEN_HACHU_ED_DT_MAX_LENGTH=8;
	public static final int GRO_EOS_KB_MAX_LENGTH=1;
	public static final int GRO_HONBU_ZAI_KB_MAX_LENGTH=1;
	public static final int GRO_HACHU_TANI_NA_MAX_LENGTH=2;
	public static final int GRO_HACHUTANI_IRISU_QT_MAX_LENGTH=6;
	public static final int GRO_MAX_HACHUTANI_QT_MAX_LENGTH=6;
	public static final int GRO_BLAND_CD_MAX_LENGTH=3;
	public static final int GRO_PB_KB_MAX_LENGTH=1;
	public static final int GRO_MAKER_KIBO_KAKAKU_VL_MAX_LENGTH=7;
	public static final int GRO_FUJI_SYOHIN_KB_MAX_LENGTH=1;
	public static final int GRO_PC_KB_MAX_LENGTH=1;
	public static final int GRO_DAISI_NO_NB_MAX_LENGTH=2;
	public static final int GRO_DAICHO_TENPO_KB_MAX_LENGTH=1;
	public static final int GRO_DAICHO_HONBU_KB_MAX_LENGTH=1;
	public static final int GRO_DAICHO_SIIRESAKI_KB_MAX_LENGTH=1;
	public static final int GRO_SYUZEI_HOKOKU_KB_MAX_LENGTH=5;
	public static final int GRO_UNIT_PRICE_TANI_KB_MAX_LENGTH=2;
	public static final int GRO_UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH=4;
	public static final int GRO_UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH=4;
	public static final int GRO_SYOHI_KIGEN_DT_MAX_LENGTH=3;
	public static final int GRO_SYUSEI_KB_MAX_LENGTH=1;
	public static final int GRO_MST_MAINTE_FG_MAX_LENGTH=1;
	public static final int GRO_ALARM_MAKE_FG_MAX_LENGTH=1;
	public static final int GRO_INSERT_TS_MAX_LENGTH=14;
	public static final int GRO_INSERT_USER_ID_MAX_LENGTH=20;
	public static final int GRO_UPDATE_TS_MAX_LENGTH=14;
	public static final int GRO_UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int A07_TORIKOMI_DT_MAX_LENGTH=8;
	public static final int A07_EXCEL_FILE_SYUBETSU_MAX_LENGTH=3;
	public static final int A07_BUMON_CD_MAX_LENGTH=4;
	public static final int A07_UNIT_CD_MAX_LENGTH=4;
	public static final int A07_HAIFU_CD_MAX_LENGTH=2;
	public static final int A07_SUBCLASS_CD_MAX_LENGTH=2;
	public static final int A07_SYOHIN_CD_MAX_LENGTH=13;
	public static final int A07_YUKO_DT_MAX_LENGTH=8;
	public static final int A07_MAKER_CD_MAX_LENGTH=9;
	public static final int A07_POS_CD_MAX_LENGTH=13;
	public static final int A07_HINMEI_KANJI_NA_MAX_LENGTH=80;
	public static final int A07_KIKAKU_KANJI_NA_MAX_LENGTH=40;
	public static final int A07_KIKAKU_KANJI_2_NA_MAX_LENGTH=40;
	public static final int A07_HINMEI_KANA_NA_MAX_LENGTH=80;
	public static final int A07_KIKAKU_KANA_NA_MAX_LENGTH=40;
	public static final int A07_KIKAKU_KANA_2_NA_MAX_LENGTH=40;
	public static final int A07_REC_HINMEI_KANJI_NA_MAX_LENGTH=20;
	public static final int A07_REC_HINMEI_KANA_NA_MAX_LENGTH=20;
	public static final int A07_SIZE_CD_MAX_LENGTH=4;
	public static final int A07_COLOR_CD_MAX_LENGTH=2;
	public static final int A07_GENTANKA_VL_MAX_LENGTH=10;
	public static final int A07_BAITANKA_VL_MAX_LENGTH=7;
	public static final int A07_KESHI_BAIKA_VL_MAX_LENGTH=7;
	public static final int A07_KEIYAKUSU_QT_MAX_LENGTH=9;
	public static final int A07_TUIKA_KEIYAKUSU_QT_MAX_LENGTH=9;
	public static final int A07_HACHUTANI_IRISU_QT_MAX_LENGTH=6;
	public static final int A07_HACHU_TANI_NA_MAX_LENGTH=2;
	public static final int A07_PLU_SEND_DT_MAX_LENGTH=8;
//	public static final int A07_AREA_CD_MAX_LENGTH=3;
	public static final int A07_SIIRESAKI_CD_MAX_LENGTH=6;
	public static final int A07_SIIRE_HINBAN_CD_MAX_LENGTH=15;
	public static final int A07_HANBAI_ST_DT_MAX_LENGTH=8;
	public static final int A07_HANBAI_ED_DT_MAX_LENGTH=8;
	public static final int A07_TEN_HACHU_ST_DT_MAX_LENGTH=8;
	public static final int A07_TEN_HACHU_ED_DT_MAX_LENGTH=8;
	public static final int A07_EOS_KB_MAX_LENGTH=1;
	public static final int A07_SEASON_CD_MAX_LENGTH=2;
	public static final int A07_BLAND_CD_MAX_LENGTH=3;
	public static final int A07_PB_KB_MAX_LENGTH=1;
	public static final int A07_YORIDORI_VL_MAX_LENGTH=7;
	public static final int A07_YORIDORI_QT_MAX_LENGTH=2;
	public static final int A07_YORIDORI_KB_MAX_LENGTH=1;
	public static final int A07_TANA_NO_NB_MAX_LENGTH=5;
	public static final int A07_NEFUDA_KB_MAX_LENGTH=1;
	public static final int A07_NEFUDA_UKEWATASI_DT_MAX_LENGTH=8;
	public static final int A07_NEFUDA_UKEWATASI_KB_MAX_LENGTH=1;
	public static final int A07_FUJI_SYOHIN_KB_MAX_LENGTH=1;
	public static final int A07_PC_KB_MAX_LENGTH=1;
	public static final int A07_DAISI_NO_NB_MAX_LENGTH=2;
	public static final int A07_SYUSEI_KB_MAX_LENGTH=1;
	public static final int A07_MST_MAINTE_FG_MAX_LENGTH=1;
	public static final int A07_ALARM_MAKE_FG_MAX_LENGTH=1;
	public static final int A07_INSERT_TS_MAX_LENGTH=14;
	public static final int A07_INSERT_USER_ID_MAX_LENGTH=20;
	public static final int A07_UPDATE_TS_MAX_LENGTH=14;
	public static final int A07_UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int A08_TORIKOMI_DT_MAX_LENGTH=8;
	public static final int A08_EXCEL_FILE_SYUBETSU_MAX_LENGTH=3;
	public static final int A08_BUMON_CD_MAX_LENGTH=4;
	public static final int A08_UNIT_CD_MAX_LENGTH=4;
	public static final int A08_HAIFU_CD_MAX_LENGTH=2;
	public static final int A08_SUBCLASS_CD_MAX_LENGTH=2;
	public static final int A08_SYOHIN_CD_MAX_LENGTH=13;
	public static final int A08_YUKO_DT_MAX_LENGTH=8;
	public static final int A08_HINMEI_KANJI_NA_MAX_LENGTH=80;
	public static final int A08_HINMEI_KANA_NA_MAX_LENGTH=80;
	public static final int A08_SIZE_CD_MAX_LENGTH=4;
	public static final int A08_COLOR_CD_MAX_LENGTH=2;
	public static final int A08_GENTANKA_VL_MAX_LENGTH=10;
	public static final int A08_BAITANKA_VL_MAX_LENGTH=7;
	public static final int A08_KESHI_BAIKA_VL_MAX_LENGTH=7;
	public static final int A08_TOKUBETU_GENKA_VL_MAX_LENGTH=10;
	public static final int A08_KEIYAKUSU_QT_MAX_LENGTH=9;
	public static final int A08_TUIKA_KEIYAKUSU_QT_MAX_LENGTH=9;
//	public static final int A08_AREA_CD_MAX_LENGTH=3;
	public static final int A08_SIIRESAKI_CD_MAX_LENGTH=6;
	public static final int A08_SIIRE_HINBAN_CD_MAX_LENGTH=15;
	public static final int A08_HANBAI_ST_DT_MAX_LENGTH=8;
	public static final int A08_HANBAI_ED_DT_MAX_LENGTH=8;
	public static final int A08_TEN_HACHU_ST_DT_MAX_LENGTH=8;
	public static final int A08_TEN_HACHU_ED_DT_MAX_LENGTH=8;
	public static final int A08_EOS_KB_MAX_LENGTH=1;
	public static final int A08_SEASON_CD_MAX_LENGTH=2;
	public static final int A08_BLAND_CD_MAX_LENGTH=3;
	public static final int A08_PB_KB_MAX_LENGTH=1;
	public static final int A08_YORIDORI_VL_MAX_LENGTH=7;
	public static final int A08_YORIDORI_QT_MAX_LENGTH=2;
	public static final int A08_YORIDORI_KB_MAX_LENGTH=1;
	public static final int A08_NEFUDA_KB_MAX_LENGTH=1;
	public static final int A08_NEFUDA_UKEWATASI_DT_MAX_LENGTH=8;
	public static final int A08_NEFUDA_UKEWATASI_KB_MAX_LENGTH=1;
	public static final int A08_FUJI_SYOHIN_KB_MAX_LENGTH=1;
	public static final int A08_SOZAI_CD_MAX_LENGTH=3;
	public static final int A08_ORIAMI_CD_MAX_LENGTH=3;
	public static final int A08_SODE_CD_MAX_LENGTH=3;
	public static final int A08_AGE_CD_MAX_LENGTH=3;
	public static final int A08_PATTERN_CD_MAX_LENGTH=3;
	public static final int A08_SYUSEI_KB_MAX_LENGTH=1;
	public static final int A08_MST_MAINTE_FG_MAX_LENGTH=1;
	public static final int A08_ALARM_MAKE_FG_MAX_LENGTH=1;
	public static final int A08_INSERT_TS_MAX_LENGTH=14;
	public static final int A08_INSERT_USER_ID_MAX_LENGTH=20;
	public static final int A08_UPDATE_TS_MAX_LENGTH=14;
	public static final int A08_UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int TS_TORIKOMI_DT_MAX_LENGTH=8;
	public static final int TS_EXCEL_FILE_SYUBETSU_MAX_LENGTH=3;
//	↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//	public static final int TS_EXCEL_FILE_NA_MAX_LENGTH=30;
	public static final int TS_EXCEL_FILE_NA_MAX_LENGTH=80;
//	↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
//	public static final int TS_AREA_CD_MAX_LENGTH=3;
	public static final int TS_SIIRESAKI_CD_MAX_LENGTH=6;
//	↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//	public static final int TS_UPLOAD_COMMENT_TX_MAX_LENGTH=40;
	public static final int TS_UPLOAD_COMMENT_TX_MAX_LENGTH=80;
//	↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
	public static final int TS_TOROKU_SYONIN_FG_MAX_LENGTH=1;
	public static final int TS_TOROKU_SYONIN_TS_MAX_LENGTH=14;
	public static final int TS_BY_NO_MAX_LENGTH=20;
//	↓↓2006.09.20 H.Yamamoto カスタマイズ修正↓↓
//	public static final int TS_SYONIN_COMMENT_TX_MAX_LENGTH=40;
	public static final int TS_SYONIN_COMMENT_TX_MAX_LENGTH=80;
//	↑↑2006.09.20 H.Yamamoto カスタマイズ修正↑↑
	public static final int TS_EXCEL_SYOHIN_KB_MAX_LENGTH=1;
	public static final int TS_EXCEL_TANPIN_KB_MAX_LENGTH=1;
	public static final int TS_EXCEL_REIGAI_KB_MAX_LENGTH=1;
	public static final int TS_EXCEL_SYOKAI_KB_MAX_LENGTH=1;
	public static final int TS_SYORI_JYOTAI_FG_MAX_LENGTH=1;
	public static final int TS_KAKUNIN_FG_MAX_LENGTH=1;
	public static final int TS_DELETE_FG_MAX_LENGTH=1;
	public static final int TS_INSERT_TS_MAX_LENGTH=14;
	public static final int TS_INSERT_USER_ID_MAX_LENGTH=20;
	public static final int TS_UPDATE_TS_MAX_LENGTH=14;
	public static final int TS_UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int TS_RIYO_USER_NA_MAX_LENGTH=20;	// add by kema 06.09.08
	public static final int SYO_TORIKOMI_DT_MAX_LENGTH=8;
	public static final int SYO_EXCEL_FILE_SYUBETSU_MAX_LENGTH=3;
	public static final int SYO_HACHUNO_CD_MAX_LENGTH=10;
	public static final int SYO_BUMON_CD_MAX_LENGTH=4;
	public static final int SYO_SYOHIN_CD_MAX_LENGTH=13;
	public static final int SYO_TENPO_CD_MAX_LENGTH=6;
	public static final int SYO_THEME_CD_MAX_LENGTH=2;
	public static final int SYO_SURYO_QT_MAX_LENGTH=6;
	public static final int SYO_HACHUTANI_QT_MAX_LENGTH=6;
	public static final int SYO_GENTANKA_VL_MAX_LENGTH=10;
	public static final int SYO_BAITANKA_VL_MAX_LENGTH=7;
	public static final int SYO_HATYU_DT_MAX_LENGTH=8;
	public static final int SYO_NOHIN_DT_MAX_LENGTH=8;
	public static final int SYO_HANBAI_ST_DT_MAX_LENGTH=8;
	public static final int SYO_HANBAI_ED_DT_MAX_LENGTH=8;
	public static final int SYO_SYUSEI_KB_MAX_LENGTH=1;
	public static final int SYO_MST_MAINTE_FG_MAX_LENGTH=1;
	public static final int SYO_ALARM_MAKE_FG_MAX_LENGTH=1;
	public static final int SYO_INSERT_TS_MAX_LENGTH=14;
	public static final int SYO_INSERT_USER_ID_MAX_LENGTH=20;
	public static final int SYO_UPDATE_TS_MAX_LENGTH=14;
	public static final int SYO_UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int REIGAI_TORIKOMI_DT_MAX_LENGTH=8;
	public static final int REIGAI_EXCEL_FILE_SYUBETSU_MAX_LENGTH=3;
	public static final int REIGAI_BUMON_CD_MAX_LENGTH=4;
	public static final int REIGAI_SYOHIN_CD_MAX_LENGTH=13;
	public static final int REIGAI_TENPO_CD_MAX_LENGTH=6;
	public static final int REIGAI_YUKO_DT_MAX_LENGTH=8;
	public static final int REIGAI_GENTANKA_VL_MAX_LENGTH=10;
	public static final int REIGAI_BAITANKA_VL_MAX_LENGTH=7;
	public static final int REIGAI_MAX_HACHUTANI_QT_MAX_LENGTH=6;
	public static final int REIGAI_EOS_KB_MAX_LENGTH=1;
//	public static final int REIGAI_AREA_CD_MAX_LENGTH=3;
	public static final int REIGAI_SIIRESAKI_CD_MAX_LENGTH=6;
	public static final int REIGAI_TEN_HACHU_ST_DT_MAX_LENGTH=8;
	public static final int REIGAI_TEN_HACHU_ED_DT_MAX_LENGTH=8;
	public static final int REIGAI_BUTURYU_KB_MAX_LENGTH=1;
	public static final int REIGAI_BIN_1_KB_MAX_LENGTH=1;
	public static final int REIGAI_BIN_2_KB_MAX_LENGTH=1;
	public static final int REIGAI_YUSEN_BIN_KB_MAX_LENGTH=1;
	public static final int REIGAI_SYUSEI_KB_MAX_LENGTH=1;
	public static final int REIGAI_MST_MAINTE_FG_MAX_LENGTH=1;
	public static final int REIGAI_ALARM_MAKE_FG_MAX_LENGTH=1;
	public static final int REIGAI_INSERT_TS_MAX_LENGTH=14;
	public static final int REIGAI_INSERT_USER_ID_MAX_LENGTH=20;
	public static final int REIGAI_UPDATE_TS_MAX_LENGTH=14;
	public static final int REIGAI_UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int TANPIN_TORIKOMI_DT_MAX_LENGTH=8;
	public static final int TANPIN_EXCEL_FILE_SYUBETSU_MAX_LENGTH=3;
	public static final int TANPIN_BUMON_CD_MAX_LENGTH=4;
	public static final int TANPIN_SYOHIN_CD_MAX_LENGTH=13;
	public static final int TANPIN_TENPO_CD_MAX_LENGTH=6;
	public static final int TANPIN_DONYU_DT_MAX_LENGTH=8;
	public static final int TANPIN_SYUSEI_KB_MAX_LENGTH=1;
	public static final int TANPIN_MST_MAINTE_FG_MAX_LENGTH=1;
	public static final int TANPIN_ALARM_MAKE_FG_MAX_LENGTH=1;
	public static final int TANPIN_INSERT_TS_MAX_LENGTH=14;
	public static final int TANPIN_INSERT_USER_ID_MAX_LENGTH=20;
	public static final int TANPIN_UPDATE_TS_MAX_LENGTH=14;
	public static final int TANPIN_UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int SENTAKU_MAX_LENGTH = 1;

	private String fre_torikomi_dt = null;
	private String fre_excel_file_syubetsu = null;
	private long fre_uketsuke_no = 0;
	private long fre_uketsuke_seq = 0;
	private String fre_bumon_cd = null;
	private String fre_unit_cd = null;
	private String fre_haifu_cd = null;
	private String fre_subclass_cd = null;
	private String fre_syohin_cd = null;
	private String fre_yuko_dt = null;
	private String fre_maker_cd = null;
	private String fre_hinmei_kanji_na = null;
	private String fre_kikaku_kanji_na = null;
	private String fre_kikaku_kanji_2_na = null;
	private String fre_hinmei_kana_na = null;
	private String fre_kikaku_kana_na = null;
	private String fre_kikaku_kana_2_na = null;
	private String fre_rec_hinmei_kanji_na = null;
	private String fre_rec_hinmei_kana_na = null;
	private String fre_gentanka_vl = null;
	private String fre_baitanka_vl = null;
	private String fre_plu_send_dt = null;
//	private String fre_area_cd = null;
	private String fre_siiresaki_cd = null;
	private String fre_ten_siiresaki_kanri_cd = null;
	private String fre_siire_hinban_cd = null;
	private String fre_hanbai_st_dt = null;
	private String fre_hanbai_ed_dt = null;
	private String fre_ten_hachu_st_dt = null;
	private String fre_ten_hachu_ed_dt = null;
	private String fre_eos_kb = null;
	private String fre_honbu_zai_kb = null;
	private String fre_hachu_tani_na = null;
	private String fre_hachutani_irisu_qt = null;
	private String fre_max_hachutani_qt = null;
	private String fre_bland_cd = null;
	private String fre_pb_kb = null;
	private String fre_maker_kibo_kakaku_vl = null;
	private String fre_bin_1_kb = null;
	private String fre_bin_2_kb = null;
	private String fre_yusen_bin_kb = null;
	private String fre_fuji_syohin_kb = null;
	private String fre_pc_kb = null;
	private String fre_daisi_no_nb = null;
	private String fre_daicho_tenpo_kb = null;
	private String fre_daicho_honbu_kb = null;
	private String fre_daicho_siiresaki_kb = null;
	private String fre_unit_price_tani_kb = null;
	private String fre_unit_price_naiyoryo_qt = null;
	private String fre_unit_price_kijun_tani_qt = null;
	private String fre_syohi_kigen_dt = null;
	private String fre_syusei_kb = null;
	private long fre_sakusei_gyo_no = 0;
	private String fre_mst_mainte_fg = null;
	private String fre_alarm_make_fg = null;
	private String fre_insert_ts = null;
	private String fre_insert_user_id = null;
	private String fre_update_ts = null;
	private String fre_update_user_id = null;
	private String gro_torikomi_dt = null;
	private String gro_excel_file_syubetsu = null;
	private long gro_uketsuke_no = 0;
	private long gro_uketsuke_seq = 0;
	private String gro_bumon_cd = null;
	private String gro_unit_cd = null;
	private String gro_haifu_cd = null;
	private String gro_subclass_cd = null;
	private String gro_syohin_cd = null;
	private String gro_yuko_dt = null;
	private String gro_maker_cd = null;
	private String gro_hinmei_kanji_na = null;
	private String gro_kikaku_kanji_na = null;
	private String gro_kikaku_kanji_2_na = null;
	private String gro_hinmei_kana_na = null;
	private String gro_kikaku_kana_na = null;
	private String gro_kikaku_kana_2_na = null;
	private String gro_rec_hinmei_kanji_na = null;
	private String gro_rec_hinmei_kana_na = null;
	private String gro_gentanka_vl = null;
	private String gro_baitanka_vl = null;
	private String gro_plu_send_dt = null;
//	private String gro_area_cd = null;
	private String gro_siiresaki_cd = null;
	private String gro_ten_siiresaki_kanri_cd = null;
	private String gro_siire_hinban_cd = null;
	private String gro_hanbai_st_dt = null;
	private String gro_hanbai_ed_dt = null;
	private String gro_ten_hachu_st_dt = null;
	private String gro_ten_hachu_ed_dt = null;
	private String gro_eos_kb = null;
	private String gro_honbu_zai_kb = null;
	private String gro_hachu_tani_na = null;
	private String gro_hachutani_irisu_qt = null;
	private String gro_max_hachutani_qt = null;
	private String gro_bland_cd = null;
	private String gro_pb_kb = null;
	private String gro_maker_kibo_kakaku_vl = null;
	private String gro_fuji_syohin_kb = null;
	private String gro_pc_kb = null;
	private String gro_daisi_no_nb = null;
	private String gro_daicho_tenpo_kb = null;
	private String gro_daicho_honbu_kb = null;
	private String gro_daicho_siiresaki_kb = null;
	private String gro_syuzei_hokoku_kb = null;
	private String gro_unit_price_tani_kb = null;
	private String gro_unit_price_naiyoryo_qt = null;
	private String gro_unit_price_kijun_tani_qt = null;
	private String gro_syohi_kigen_dt = null;
	private String gro_syusei_kb = null;
	private long gro_sakusei_gyo_no = 0;
	private String gro_mst_mainte_fg = null;
	private String gro_alarm_make_fg = null;
	private String gro_insert_ts = null;
	private String gro_insert_user_id = null;
	private String gro_update_ts = null;
	private String gro_update_user_id = null;
	private String a07_torikomi_dt = null;
	private String a07_excel_file_syubetsu = null;
	private long a07_uketsuke_no = 0;
	private long a07_uketsuke_seq = 0;
	private String a07_bumon_cd = null;
	private String a07_unit_cd = null;
	private String a07_haifu_cd = null;
	private String a07_subclass_cd = null;
	private String a07_syohin_cd = null;
	private String a07_yuko_dt = null;
	private String a07_maker_cd = null;
	private String a07_pos_cd = null;
	private String a07_hinmei_kanji_na = null;
	private String a07_kikaku_kanji_na = null;
	private String a07_kikaku_kanji_2_na = null;
	private String a07_hinmei_kana_na = null;
	private String a07_kikaku_kana_na = null;
	private String a07_kikaku_kana_2_na = null;
	private String a07_rec_hinmei_kanji_na = null;
	private String a07_rec_hinmei_kana_na = null;
	private String a07_size_cd = null;
	private String a07_color_cd = null;
	private String a07_gentanka_vl = null;
	private String a07_baitanka_vl = null;
	private String a07_keshi_baika_vl = null;
	private String a07_keiyakusu_qt = null;
	private String a07_tuika_keiyakusu_qt = null;
	private String a07_hachutani_irisu_qt = null;
	private String a07_hachu_tani_na = null;
	private String a07_plu_send_dt = null;
//	private String a07_area_cd = null;
	private String a07_siiresaki_cd = null;
	private String a07_siire_hinban_cd = null;
	private String a07_hanbai_st_dt = null;
	private String a07_hanbai_ed_dt = null;
	private String a07_ten_hachu_st_dt = null;
	private String a07_ten_hachu_ed_dt = null;
	private String a07_eos_kb = null;
	private String a07_season_cd = null;
	private String a07_bland_cd = null;
	private String a07_pb_kb = null;
	private String a07_yoridori_vl = null;
	private String a07_yoridori_qt = null;
	private String a07_yoridori_kb = null;
	private String a07_tana_no_nb = null;
	private String a07_nefuda_kb = null;
	private String a07_nefuda_ukewatasi_dt = null;
	private String a07_nefuda_ukewatasi_kb = null;
	private String a07_fuji_syohin_kb = null;
	private String a07_pc_kb = null;
	private String a07_daisi_no_nb = null;
	private String a07_syusei_kb = null;
	private long a07_sakusei_gyo_no = 0;
	private String a07_mst_mainte_fg = null;
	private String a07_alarm_make_fg = null;
	private String a07_insert_ts = null;
	private String a07_insert_user_id = null;
	private String a07_update_ts = null;
	private String a07_update_user_id = null;
	private String a08_torikomi_dt = null;
	private String a08_excel_file_syubetsu = null;
	private long a08_uketsuke_no = 0;
	private long a08_uketsuke_seq = 0;
	private String a08_bumon_cd = null;
	private String a08_unit_cd = null;
	private String a08_haifu_cd = null;
	private String a08_subclass_cd = null;
	private String a08_syohin_cd = null;
	private String a08_yuko_dt = null;
	private String a08_hinmei_kanji_na = null;
	private String a08_hinmei_kana_na = null;
	private String a08_size_cd = null;
	private String a08_color_cd = null;
	private String a08_gentanka_vl = null;
	private String a08_baitanka_vl = null;
	private String a08_keshi_baika_vl = null;
	private String a08_tokubetu_genka_vl = null;
	private String a08_keiyakusu_qt = null;
	private String a08_tuika_keiyakusu_qt = null;
//	private String a08_area_cd = null;
	private String a08_siiresaki_cd = null;
	private String a08_siire_hinban_cd = null;
	private String a08_hanbai_st_dt = null;
	private String a08_hanbai_ed_dt = null;
	private String a08_ten_hachu_st_dt = null;
	private String a08_ten_hachu_ed_dt = null;
	private String a08_eos_kb = null;
	private String a08_season_cd = null;
	private String a08_bland_cd = null;
	private String a08_pb_kb = null;
	private String a08_yoridori_vl = null;
	private String a08_yoridori_qt = null;
	private String a08_yoridori_kb = null;
	private String a08_nefuda_kb = null;
	private String a08_nefuda_ukewatasi_dt = null;
	private String a08_nefuda_ukewatasi_kb = null;
	private String a08_fuji_syohin_kb = null;
	private String a08_sozai_cd = null;
	private String a08_oriami_cd = null;
	private String a08_sode_cd = null;
	private String a08_age_cd = null;
	private String a08_pattern_cd = null;
	private String a08_syusei_kb = null;
	private long a08_sakusei_gyo_no = 0;
	private String a08_mst_mainte_fg = null;
	private String a08_alarm_make_fg = null;
	private String a08_insert_ts = null;
	private String a08_insert_user_id = null;
	private String a08_update_ts = null;
	private String a08_update_user_id = null;
	private String ts_torikomi_dt = null;
	private String ts_excel_file_syubetsu = null;
	private long ts_uketsuke_no = 0;
	private String ts_excel_file_na = null;
//	private String ts_area_cd = null;
	private String ts_siiresaki_cd = null;
	private String ts_upload_comment_tx = null;
	private String ts_toroku_syonin_fg = null;
	private String ts_toroku_syonin_ts = null;
	private String ts_by_no = null;
	private String ts_syonin_comment_tx = null;
	private String ts_excel_syohin_kb = null;
	private String ts_excel_tanpin_kb = null;
	private String ts_excel_reigai_kb = null;
	private String ts_excel_syokai_kb = null;
	private double ts_min_neire_rt = 0;
	private double ts_max_neire_rt = 0;
	private long ts_max_uritanka_vl = 0;
	private String ts_syori_jyotai_fg = null;
	private String ts_kakunin_fg = null;
	private String ts_delete_fg = null;
	private String ts_insert_ts = null;
	private String ts_insert_user_id = null;
	private String ts_update_ts = null;
	private String ts_update_user_id = null;
	private String ts_riyo_user_na = null;	// add by kema 06.09.08
	private String syo_torikomi_dt = null;
	private String syo_excel_file_syubetsu = null;
	private long syo_uketsuke_no = 0;
	private long syo_uketsuke_seq = 0;
	private String syo_hachuno_cd = null;
	private String syo_bumon_cd = null;
	private String syo_syohin_cd = null;
	private String syo_tenpo_cd = null;
	private String syo_theme_cd = null;
	private String syo_suryo_qt = null;
	private String syo_hachutani_qt = null;
	private String syo_gentanka_vl = null;
	private String syo_baitanka_vl = null;
	private String syo_hatyu_dt = null;
	private String syo_nohin_dt = null;
	private String syo_hanbai_st_dt = null;
	private String syo_hanbai_ed_dt = null;
	private String syo_syusei_kb = null;
	private long syo_syohin_gyo_no = 0;
	private long syo_sakusei_gyo_no = 0;
	private String syo_mst_mainte_fg = null;
	private String syo_alarm_make_fg = null;
	private String syo_insert_ts = null;
	private String syo_insert_user_id = null;
	private String syo_update_ts = null;
	private String syo_update_user_id = null;
	private String reigai_torikomi_dt = null;
	private String reigai_excel_file_syubetsu = null;
	private long reigai_uketsuke_no = 0;
	private long reigai_uketsuke_seq = 0;
	private String reigai_bumon_cd = null;
	private String reigai_syohin_cd = null;
	private String reigai_tenpo_cd = null;
	private String reigai_yuko_dt = null;
	private String reigai_gentanka_vl = null;
	private String reigai_baitanka_vl = null;
	private String reigai_max_hachutani_qt = null;
	private String reigai_eos_kb = null;
//	private String reigai_area_cd = null;
	private String reigai_siiresaki_cd = null;
	private String reigai_ten_hachu_st_dt = null;
	private String reigai_ten_hachu_ed_dt = null;
	private String reigai_buturyu_kb = null;
	private String reigai_bin_1_kb = null;
	private String reigai_bin_2_kb = null;
	private String reigai_yusen_bin_kb = null;
	private String reigai_syusei_kb = null;
	private long reigai_syohin_gyo_no = 0;
	private long reigai_sakusei_gyo_no = 0;
	private String reigai_mst_mainte_fg = null;
	private String reigai_alarm_make_fg = null;
	private String reigai_insert_ts = null;
	private String reigai_insert_user_id = null;
	private String reigai_update_ts = null;
	private String reigai_update_user_id = null;
	private String tanpin_torikomi_dt = null;
	private String tanpin_excel_file_syubetsu = null;
	private long tanpin_uketsuke_no = 0;
	private long tanpin_uketsuke_seq = 0;
	private String tanpin_bumon_cd = null;
	private String tanpin_syohin_cd = null;
	private String tanpin_tenpo_cd = null;
	private String tanpin_donyu_dt = null;
	private String tanpin_syusei_kb = null;
	private long tanpin_syohin_gyo_no = 0;
	private long tanpin_sakusei_gyo_no = 0;
	private String tanpin_mst_mainte_fg = null;
	private String tanpin_alarm_make_fg = null;
	private String tanpin_insert_ts = null;
	private String tanpin_insert_user_id = null;
	private String tanpin_update_ts = null;
	private String tanpin_update_user_id = null;
	private String sentaku = "";	

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

	/**
	 * mst380401_ExcelUploadBeanを１件のみ抽出したい時に使用する
	 */
	public static mst380401_ExcelUploadBean getmst380401_ExcelUploadBean(DataHolder dataHolder)
	{
		mst380401_ExcelUploadBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst380401_ExcelUploadDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst380401_ExcelUploadBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// fre_torikomi_dtに対するセッターとゲッターの集合
	public boolean setFreTorikomiDt(String fre_torikomi_dt)
	{
		this.fre_torikomi_dt = fre_torikomi_dt;
		return true;
	}
	public String getFreTorikomiDt()
	{
		return cutString(this.fre_torikomi_dt,FRE_TORIKOMI_DT_MAX_LENGTH);
	}
	public String getFreTorikomiDtString()
	{
		return "'" + cutString(this.fre_torikomi_dt,FRE_TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getFreTorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_torikomi_dt,FRE_TORIKOMI_DT_MAX_LENGTH));
	}


	// fre_excel_file_syubetsuに対するセッターとゲッターの集合
	public boolean setFreExcelFileSyubetsu(String fre_excel_file_syubetsu)
	{
		this.fre_excel_file_syubetsu = fre_excel_file_syubetsu;
		return true;
	}
	public String getFreExcelFileSyubetsu()
	{
		return cutString(this.fre_excel_file_syubetsu,FRE_EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getFreExcelFileSyubetsuString()
	{
		return "'" + cutString(this.fre_excel_file_syubetsu,FRE_EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getFreExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_excel_file_syubetsu,FRE_EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}


	// fre_uketsuke_noに対するセッターとゲッターの集合
	public boolean setFreUketsukeNo(String fre_uketsuke_no)
	{
		try
		{
			this.fre_uketsuke_no = Long.parseLong(fre_uketsuke_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setFreUketsukeNo(long fre_uketsuke_no)
	{
		this.fre_uketsuke_no = fre_uketsuke_no;
		return true;
	}
	public long getFreUketsukeNo()
	{
		return this.fre_uketsuke_no;
	}
	public String getFreUketsukeNoString()
	{
		return Long.toString(this.fre_uketsuke_no);
	}


	// fre_uketsuke_seqに対するセッターとゲッターの集合
	public boolean setFreUketsukeSeq(String fre_uketsuke_seq)
	{
		try
		{
			this.fre_uketsuke_seq = Long.parseLong(fre_uketsuke_seq);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setFreUketsukeSeq(long fre_uketsuke_seq)
	{
		this.fre_uketsuke_seq = fre_uketsuke_seq;
		return true;
	}
	public long getFreUketsukeSeq()
	{
		return this.fre_uketsuke_seq;
	}
	public String getFreUketsukeSeqString()
	{
		return Long.toString(this.fre_uketsuke_seq);
	}


	// fre_bumon_cdに対するセッターとゲッターの集合
	public boolean setFreBumonCd(String fre_bumon_cd)
	{
		this.fre_bumon_cd = fre_bumon_cd;
		return true;
	}
	public String getFreBumonCd()
	{
		return cutString(this.fre_bumon_cd,FRE_BUMON_CD_MAX_LENGTH);
	}
	public String getFreBumonCdString()
	{
		return "'" + cutString(this.fre_bumon_cd,FRE_BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getFreBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_bumon_cd,FRE_BUMON_CD_MAX_LENGTH));
	}


	// fre_unit_cdに対するセッターとゲッターの集合
	public boolean setFreUnitCd(String fre_unit_cd)
	{
		this.fre_unit_cd = fre_unit_cd;
		return true;
	}
	public String getFreUnitCd()
	{
		return cutString(this.fre_unit_cd,FRE_UNIT_CD_MAX_LENGTH);
	}
	public String getFreUnitCdString()
	{
		return "'" + cutString(this.fre_unit_cd,FRE_UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getFreUnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_unit_cd,FRE_UNIT_CD_MAX_LENGTH));
	}


	// fre_haifu_cdに対するセッターとゲッターの集合
	public boolean setFreHaifuCd(String fre_haifu_cd)
	{
		this.fre_haifu_cd = fre_haifu_cd;
		return true;
	}
	public String getFreHaifuCd()
	{
		return cutString(this.fre_haifu_cd,FRE_HAIFU_CD_MAX_LENGTH);
	}
	public String getFreHaifuCdString()
	{
		return "'" + cutString(this.fre_haifu_cd,FRE_HAIFU_CD_MAX_LENGTH) + "'";
	}
	public String getFreHaifuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_haifu_cd,FRE_HAIFU_CD_MAX_LENGTH));
	}


	// fre_subclass_cdに対するセッターとゲッターの集合
	public boolean setFreSubclassCd(String fre_subclass_cd)
	{
		this.fre_subclass_cd = fre_subclass_cd;
		return true;
	}
	public String getFreSubclassCd()
	{
		return cutString(this.fre_subclass_cd,FRE_SUBCLASS_CD_MAX_LENGTH);
	}
	public String getFreSubclassCdString()
	{
		return "'" + cutString(this.fre_subclass_cd,FRE_SUBCLASS_CD_MAX_LENGTH) + "'";
	}
	public String getFreSubclassCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_subclass_cd,FRE_SUBCLASS_CD_MAX_LENGTH));
	}


	// fre_syohin_cdに対するセッターとゲッターの集合
	public boolean setFreSyohinCd(String fre_syohin_cd)
	{
		this.fre_syohin_cd = fre_syohin_cd;
		return true;
	}
	public String getFreSyohinCd()
	{
		return cutString(this.fre_syohin_cd,FRE_SYOHIN_CD_MAX_LENGTH);
	}
	public String getFreSyohinCdString()
	{
		return "'" + cutString(this.fre_syohin_cd,FRE_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getFreSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_syohin_cd,FRE_SYOHIN_CD_MAX_LENGTH));
	}


	// fre_yuko_dtに対するセッターとゲッターの集合
	public boolean setFreYukoDt(String fre_yuko_dt)
	{
		this.fre_yuko_dt = fre_yuko_dt;
		return true;
	}
	public String getFreYukoDt()
	{
		return cutString(this.fre_yuko_dt,FRE_YUKO_DT_MAX_LENGTH);
	}
	public String getFreYukoDtString()
	{
		return "'" + cutString(this.fre_yuko_dt,FRE_YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getFreYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_yuko_dt,FRE_YUKO_DT_MAX_LENGTH));
	}


	// fre_maker_cdに対するセッターとゲッターの集合
	public boolean setFreMakerCd(String fre_maker_cd)
	{
		this.fre_maker_cd = fre_maker_cd;
		return true;
	}
	public String getFreMakerCd()
	{
		return cutString(this.fre_maker_cd,FRE_MAKER_CD_MAX_LENGTH);
	}
	public String getFreMakerCdString()
	{
		return "'" + cutString(this.fre_maker_cd,FRE_MAKER_CD_MAX_LENGTH) + "'";
	}
	public String getFreMakerCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_maker_cd,FRE_MAKER_CD_MAX_LENGTH));
	}


	// fre_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setFreHinmeiKanjiNa(String fre_hinmei_kanji_na)
	{
		this.fre_hinmei_kanji_na = fre_hinmei_kanji_na;
		return true;
	}
	public String getFreHinmeiKanjiNa()
	{
		return cutString(this.fre_hinmei_kanji_na,FRE_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getFreHinmeiKanjiNaString()
	{
		return "'" + cutString(this.fre_hinmei_kanji_na,FRE_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getFreHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_hinmei_kanji_na,FRE_HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// fre_kikaku_kanji_naに対するセッターとゲッターの集合
	public boolean setFreKikakuKanjiNa(String fre_kikaku_kanji_na)
	{
		this.fre_kikaku_kanji_na = fre_kikaku_kanji_na;
		return true;
	}
	public String getFreKikakuKanjiNa()
	{
		return cutString(this.fre_kikaku_kanji_na,FRE_KIKAKU_KANJI_NA_MAX_LENGTH);
	}
	public String getFreKikakuKanjiNaString()
	{
		return "'" + cutString(this.fre_kikaku_kanji_na,FRE_KIKAKU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getFreKikakuKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_kikaku_kanji_na,FRE_KIKAKU_KANJI_NA_MAX_LENGTH));
	}


	// fre_kikaku_kanji_2_naに対するセッターとゲッターの集合
	public boolean setFreKikakuKanji2Na(String fre_kikaku_kanji_2_na)
	{
		this.fre_kikaku_kanji_2_na = fre_kikaku_kanji_2_na;
		return true;
	}
	public String getFreKikakuKanji2Na()
	{
		return cutString(this.fre_kikaku_kanji_2_na,FRE_KIKAKU_KANJI_2_NA_MAX_LENGTH);
	}
	public String getFreKikakuKanji2NaString()
	{
		return "'" + cutString(this.fre_kikaku_kanji_2_na,FRE_KIKAKU_KANJI_2_NA_MAX_LENGTH) + "'";
	}
	public String getFreKikakuKanji2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_kikaku_kanji_2_na,FRE_KIKAKU_KANJI_2_NA_MAX_LENGTH));
	}


	// fre_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setFreHinmeiKanaNa(String fre_hinmei_kana_na)
	{
		this.fre_hinmei_kana_na = fre_hinmei_kana_na;
		return true;
	}
	public String getFreHinmeiKanaNa()
	{
		return cutString(this.fre_hinmei_kana_na,FRE_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getFreHinmeiKanaNaString()
	{
		return "'" + cutString(this.fre_hinmei_kana_na,FRE_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getFreHinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_hinmei_kana_na,FRE_HINMEI_KANA_NA_MAX_LENGTH));
	}


	// fre_kikaku_kana_naに対するセッターとゲッターの集合
	public boolean setFreKikakuKanaNa(String fre_kikaku_kana_na)
	{
		this.fre_kikaku_kana_na = fre_kikaku_kana_na;
		return true;
	}
	public String getFreKikakuKanaNa()
	{
		return cutString(this.fre_kikaku_kana_na,FRE_KIKAKU_KANA_NA_MAX_LENGTH);
	}
	public String getFreKikakuKanaNaString()
	{
		return "'" + cutString(this.fre_kikaku_kana_na,FRE_KIKAKU_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getFreKikakuKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_kikaku_kana_na,FRE_KIKAKU_KANA_NA_MAX_LENGTH));
	}


	// fre_kikaku_kana_2_naに対するセッターとゲッターの集合
	public boolean setFreKikakuKana2Na(String fre_kikaku_kana_2_na)
	{
		this.fre_kikaku_kana_2_na = fre_kikaku_kana_2_na;
		return true;
	}
	public String getFreKikakuKana2Na()
	{
		return cutString(this.fre_kikaku_kana_2_na,FRE_KIKAKU_KANA_2_NA_MAX_LENGTH);
	}
	public String getFreKikakuKana2NaString()
	{
		return "'" + cutString(this.fre_kikaku_kana_2_na,FRE_KIKAKU_KANA_2_NA_MAX_LENGTH) + "'";
	}
	public String getFreKikakuKana2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_kikaku_kana_2_na,FRE_KIKAKU_KANA_2_NA_MAX_LENGTH));
	}


	// fre_rec_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setFreRecHinmeiKanjiNa(String fre_rec_hinmei_kanji_na)
	{
		this.fre_rec_hinmei_kanji_na = fre_rec_hinmei_kanji_na;
		return true;
	}
	public String getFreRecHinmeiKanjiNa()
	{
		return cutString(this.fre_rec_hinmei_kanji_na,FRE_REC_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getFreRecHinmeiKanjiNaString()
	{
		return "'" + cutString(this.fre_rec_hinmei_kanji_na,FRE_REC_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getFreRecHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_rec_hinmei_kanji_na,FRE_REC_HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// fre_rec_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setFreRecHinmeiKanaNa(String fre_rec_hinmei_kana_na)
	{
		this.fre_rec_hinmei_kana_na = fre_rec_hinmei_kana_na;
		return true;
	}
	public String getFreRecHinmeiKanaNa()
	{
		return cutString(this.fre_rec_hinmei_kana_na,FRE_REC_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getFreRecHinmeiKanaNaString()
	{
		return "'" + cutString(this.fre_rec_hinmei_kana_na,FRE_REC_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getFreRecHinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_rec_hinmei_kana_na,FRE_REC_HINMEI_KANA_NA_MAX_LENGTH));
	}


	// fre_gentanka_vlに対するセッターとゲッターの集合
	public boolean setFreGentankaVl(String fre_gentanka_vl)
	{
		this.fre_gentanka_vl = fre_gentanka_vl;
		return true;
	}
	public String getFreGentankaVl()
	{
		return cutString(this.fre_gentanka_vl,FRE_GENTANKA_VL_MAX_LENGTH);
	}
	public String getFreGentankaVlString()
	{
		return "'" + cutString(this.fre_gentanka_vl,FRE_GENTANKA_VL_MAX_LENGTH) + "'";
	}
	public String getFreGentankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_gentanka_vl,FRE_GENTANKA_VL_MAX_LENGTH));
	}


	// fre_baitanka_vlに対するセッターとゲッターの集合
	public boolean setFreBaitankaVl(String fre_baitanka_vl)
	{
		this.fre_baitanka_vl = fre_baitanka_vl;
		return true;
	}
	public String getFreBaitankaVl()
	{
		return cutString(this.fre_baitanka_vl,FRE_BAITANKA_VL_MAX_LENGTH);
	}
	public String getFreBaitankaVlString()
	{
		return "'" + cutString(this.fre_baitanka_vl,FRE_BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getFreBaitankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_baitanka_vl,FRE_BAITANKA_VL_MAX_LENGTH));
	}


	// fre_plu_send_dtに対するセッターとゲッターの集合
	public boolean setFrePluSendDt(String fre_plu_send_dt)
	{
		this.fre_plu_send_dt = fre_plu_send_dt;
		return true;
	}
	public String getFrePluSendDt()
	{
		return cutString(this.fre_plu_send_dt,FRE_PLU_SEND_DT_MAX_LENGTH);
	}
	public String getFrePluSendDtString()
	{
		return "'" + cutString(this.fre_plu_send_dt,FRE_PLU_SEND_DT_MAX_LENGTH) + "'";
	}
	public String getFrePluSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_plu_send_dt,FRE_PLU_SEND_DT_MAX_LENGTH));
	}


//	// fre_area_cdに対するセッターとゲッターの集合
//	public boolean setFreAreaCd(String fre_area_cd)
//	{
//		this.fre_area_cd = fre_area_cd;
//		return true;
//	}
//	public String getFreAreaCd()
//	{
//		return cutString(this.fre_area_cd,FRE_AREA_CD_MAX_LENGTH);
//	}
//	public String getFreAreaCdString()
//	{
//		return "'" + cutString(this.fre_area_cd,FRE_AREA_CD_MAX_LENGTH) + "'";
//	}
//	public String getFreAreaCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.fre_area_cd,FRE_AREA_CD_MAX_LENGTH));
//	}


	// fre_siiresaki_cdに対するセッターとゲッターの集合
	public boolean setFreSiiresakiCd(String fre_siiresaki_cd)
	{
		this.fre_siiresaki_cd = fre_siiresaki_cd;
		return true;
	}
	public String getFreSiiresakiCd()
	{
		return cutString(this.fre_siiresaki_cd,FRE_SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getFreSiiresakiCdString()
	{
		return "'" + cutString(this.fre_siiresaki_cd,FRE_SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getFreSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_siiresaki_cd,FRE_SIIRESAKI_CD_MAX_LENGTH));
	}


	// fre_ten_siiresaki_kanri_cdに対するセッターとゲッターの集合
	public boolean setFreTenSiiresakiKanriCd(String fre_ten_siiresaki_kanri_cd)
	{
		this.fre_ten_siiresaki_kanri_cd = fre_ten_siiresaki_kanri_cd;
		return true;
	}
	public String getFreTenSiiresakiKanriCd()
	{
		return cutString(this.fre_ten_siiresaki_kanri_cd,FRE_TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH);
	}
	public String getFreTenSiiresakiKanriCdString()
	{
		return "'" + cutString(this.fre_ten_siiresaki_kanri_cd,FRE_TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getFreTenSiiresakiKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_ten_siiresaki_kanri_cd,FRE_TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH));
	}


	// fre_siire_hinban_cdに対するセッターとゲッターの集合
	public boolean setFreSiireHinbanCd(String fre_siire_hinban_cd)
	{
		this.fre_siire_hinban_cd = fre_siire_hinban_cd;
		return true;
	}
	public String getFreSiireHinbanCd()
	{
		return cutString(this.fre_siire_hinban_cd,FRE_SIIRE_HINBAN_CD_MAX_LENGTH);
	}
	public String getFreSiireHinbanCdString()
	{
		return "'" + cutString(this.fre_siire_hinban_cd,FRE_SIIRE_HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getFreSiireHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_siire_hinban_cd,FRE_SIIRE_HINBAN_CD_MAX_LENGTH));
	}


	// fre_hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setFreHanbaiStDt(String fre_hanbai_st_dt)
	{
		this.fre_hanbai_st_dt = fre_hanbai_st_dt;
		return true;
	}
	public String getFreHanbaiStDt()
	{
		return cutString(this.fre_hanbai_st_dt,FRE_HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getFreHanbaiStDtString()
	{
		return "'" + cutString(this.fre_hanbai_st_dt,FRE_HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getFreHanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_hanbai_st_dt,FRE_HANBAI_ST_DT_MAX_LENGTH));
	}


	// fre_hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setFreHanbaiEdDt(String fre_hanbai_ed_dt)
	{
		this.fre_hanbai_ed_dt = fre_hanbai_ed_dt;
		return true;
	}
	public String getFreHanbaiEdDt()
	{
		return cutString(this.fre_hanbai_ed_dt,FRE_HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getFreHanbaiEdDtString()
	{
		return "'" + cutString(this.fre_hanbai_ed_dt,FRE_HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getFreHanbaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_hanbai_ed_dt,FRE_HANBAI_ED_DT_MAX_LENGTH));
	}


	// fre_ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setFreTenHachuStDt(String fre_ten_hachu_st_dt)
	{
		this.fre_ten_hachu_st_dt = fre_ten_hachu_st_dt;
		return true;
	}
	public String getFreTenHachuStDt()
	{
		return cutString(this.fre_ten_hachu_st_dt,FRE_TEN_HACHU_ST_DT_MAX_LENGTH);
	}
	public String getFreTenHachuStDtString()
	{
		return "'" + cutString(this.fre_ten_hachu_st_dt,FRE_TEN_HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getFreTenHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_ten_hachu_st_dt,FRE_TEN_HACHU_ST_DT_MAX_LENGTH));
	}


	// fre_ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setFreTenHachuEdDt(String fre_ten_hachu_ed_dt)
	{
		this.fre_ten_hachu_ed_dt = fre_ten_hachu_ed_dt;
		return true;
	}
	public String getFreTenHachuEdDt()
	{
		return cutString(this.fre_ten_hachu_ed_dt,FRE_TEN_HACHU_ED_DT_MAX_LENGTH);
	}
	public String getFreTenHachuEdDtString()
	{
		return "'" + cutString(this.fre_ten_hachu_ed_dt,FRE_TEN_HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getFreTenHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_ten_hachu_ed_dt,FRE_TEN_HACHU_ED_DT_MAX_LENGTH));
	}


	// fre_eos_kbに対するセッターとゲッターの集合
	public boolean setFreEosKb(String fre_eos_kb)
	{
		this.fre_eos_kb = fre_eos_kb;
		return true;
	}
	public String getFreEosKb()
	{
		return cutString(this.fre_eos_kb,FRE_EOS_KB_MAX_LENGTH);
	}
	public String getFreEosKbString()
	{
		return "'" + cutString(this.fre_eos_kb,FRE_EOS_KB_MAX_LENGTH) + "'";
	}
	public String getFreEosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_eos_kb,FRE_EOS_KB_MAX_LENGTH));
	}


	// fre_honbu_zai_kbに対するセッターとゲッターの集合
	public boolean setFreHonbuZaiKb(String fre_honbu_zai_kb)
	{
		this.fre_honbu_zai_kb = fre_honbu_zai_kb;
		return true;
	}
	public String getFreHonbuZaiKb()
	{
		return cutString(this.fre_honbu_zai_kb,FRE_HONBU_ZAI_KB_MAX_LENGTH);
	}
	public String getFreHonbuZaiKbString()
	{
		return "'" + cutString(this.fre_honbu_zai_kb,FRE_HONBU_ZAI_KB_MAX_LENGTH) + "'";
	}
	public String getFreHonbuZaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_honbu_zai_kb,FRE_HONBU_ZAI_KB_MAX_LENGTH));
	}


	// fre_hachu_tani_naに対するセッターとゲッターの集合
	public boolean setFreHachuTaniNa(String fre_hachu_tani_na)
	{
		this.fre_hachu_tani_na = fre_hachu_tani_na;
		return true;
	}
	public String getFreHachuTaniNa()
	{
		return cutString(this.fre_hachu_tani_na,FRE_HACHU_TANI_NA_MAX_LENGTH);
	}
	public String getFreHachuTaniNaString()
	{
		return "'" + cutString(this.fre_hachu_tani_na,FRE_HACHU_TANI_NA_MAX_LENGTH) + "'";
	}
	public String getFreHachuTaniNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_hachu_tani_na,FRE_HACHU_TANI_NA_MAX_LENGTH));
	}


	// fre_hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setFreHachutaniIrisuQt(String fre_hachutani_irisu_qt)
	{
		this.fre_hachutani_irisu_qt = fre_hachutani_irisu_qt;
		return true;
	}
	public String getFreHachutaniIrisuQt()
	{
		return cutString(this.fre_hachutani_irisu_qt,FRE_HACHUTANI_IRISU_QT_MAX_LENGTH);
	}
	public String getFreHachutaniIrisuQtString()
	{
		return "'" + cutString(this.fre_hachutani_irisu_qt,FRE_HACHUTANI_IRISU_QT_MAX_LENGTH) + "'";
	}
	public String getFreHachutaniIrisuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_hachutani_irisu_qt,FRE_HACHUTANI_IRISU_QT_MAX_LENGTH));
	}


	// fre_max_hachutani_qtに対するセッターとゲッターの集合
	public boolean setFreMaxHachutaniQt(String fre_max_hachutani_qt)
	{
		this.fre_max_hachutani_qt = fre_max_hachutani_qt;
		return true;
	}
	public String getFreMaxHachutaniQt()
	{
		return cutString(this.fre_max_hachutani_qt,FRE_MAX_HACHUTANI_QT_MAX_LENGTH);
	}
	public String getFreMaxHachutaniQtString()
	{
		return "'" + cutString(this.fre_max_hachutani_qt,FRE_MAX_HACHUTANI_QT_MAX_LENGTH) + "'";
	}
	public String getFreMaxHachutaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_max_hachutani_qt,FRE_MAX_HACHUTANI_QT_MAX_LENGTH));
	}


	// fre_bland_cdに対するセッターとゲッターの集合
	public boolean setFreBlandCd(String fre_bland_cd)
	{
		this.fre_bland_cd = fre_bland_cd;
		return true;
	}
	public String getFreBlandCd()
	{
		return cutString(this.fre_bland_cd,FRE_BLAND_CD_MAX_LENGTH);
	}
	public String getFreBlandCdString()
	{
		return "'" + cutString(this.fre_bland_cd,FRE_BLAND_CD_MAX_LENGTH) + "'";
	}
	public String getFreBlandCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_bland_cd,FRE_BLAND_CD_MAX_LENGTH));
	}


	// fre_pb_kbに対するセッターとゲッターの集合
	public boolean setFrePbKb(String fre_pb_kb)
	{
		this.fre_pb_kb = fre_pb_kb;
		return true;
	}
	public String getFrePbKb()
	{
		return cutString(this.fre_pb_kb,FRE_PB_KB_MAX_LENGTH);
	}
	public String getFrePbKbString()
	{
		return "'" + cutString(this.fre_pb_kb,FRE_PB_KB_MAX_LENGTH) + "'";
	}
	public String getFrePbKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_pb_kb,FRE_PB_KB_MAX_LENGTH));
	}


	// fre_maker_kibo_kakaku_vlに対するセッターとゲッターの集合
	public boolean setFreMakerKiboKakakuVl(String fre_maker_kibo_kakaku_vl)
	{
		this.fre_maker_kibo_kakaku_vl = fre_maker_kibo_kakaku_vl;
		return true;
	}
	public String getFreMakerKiboKakakuVl()
	{
		return cutString(this.fre_maker_kibo_kakaku_vl,FRE_MAKER_KIBO_KAKAKU_VL_MAX_LENGTH);
	}
	public String getFreMakerKiboKakakuVlString()
	{
		return "'" + cutString(this.fre_maker_kibo_kakaku_vl,FRE_MAKER_KIBO_KAKAKU_VL_MAX_LENGTH) + "'";
	}
	public String getFreMakerKiboKakakuVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_maker_kibo_kakaku_vl,FRE_MAKER_KIBO_KAKAKU_VL_MAX_LENGTH));
	}


	// fre_bin_1_kbに対するセッターとゲッターの集合
	public boolean setFreBin1Kb(String fre_bin_1_kb)
	{
		this.fre_bin_1_kb = fre_bin_1_kb;
		return true;
	}
	public String getFreBin1Kb()
	{
		return cutString(this.fre_bin_1_kb,FRE_BIN_1_KB_MAX_LENGTH);
	}
	public String getFreBin1KbString()
	{
		return "'" + cutString(this.fre_bin_1_kb,FRE_BIN_1_KB_MAX_LENGTH) + "'";
	}
	public String getFreBin1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_bin_1_kb,FRE_BIN_1_KB_MAX_LENGTH));
	}


	// fre_bin_2_kbに対するセッターとゲッターの集合
	public boolean setFreBin2Kb(String fre_bin_2_kb)
	{
		this.fre_bin_2_kb = fre_bin_2_kb;
		return true;
	}
	public String getFreBin2Kb()
	{
		return cutString(this.fre_bin_2_kb,FRE_BIN_2_KB_MAX_LENGTH);
	}
	public String getFreBin2KbString()
	{
		return "'" + cutString(this.fre_bin_2_kb,FRE_BIN_2_KB_MAX_LENGTH) + "'";
	}
	public String getFreBin2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_bin_2_kb,FRE_BIN_2_KB_MAX_LENGTH));
	}


	// fre_yusen_bin_kbに対するセッターとゲッターの集合
	public boolean setFreYusenBinKb(String fre_yusen_bin_kb)
	{
		this.fre_yusen_bin_kb = fre_yusen_bin_kb;
		return true;
	}
	public String getFreYusenBinKb()
	{
		return cutString(this.fre_yusen_bin_kb,FRE_YUSEN_BIN_KB_MAX_LENGTH);
	}
	public String getFreYusenBinKbString()
	{
		return "'" + cutString(this.fre_yusen_bin_kb,FRE_YUSEN_BIN_KB_MAX_LENGTH) + "'";
	}
	public String getFreYusenBinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_yusen_bin_kb,FRE_YUSEN_BIN_KB_MAX_LENGTH));
	}


	// fre_fuji_syohin_kbに対するセッターとゲッターの集合
	public boolean setFreFujiSyohinKb(String fre_fuji_syohin_kb)
	{
		this.fre_fuji_syohin_kb = fre_fuji_syohin_kb;
		return true;
	}
	public String getFreFujiSyohinKb()
	{
		return cutString(this.fre_fuji_syohin_kb,FRE_FUJI_SYOHIN_KB_MAX_LENGTH);
	}
	public String getFreFujiSyohinKbString()
	{
		return "'" + cutString(this.fre_fuji_syohin_kb,FRE_FUJI_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getFreFujiSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_fuji_syohin_kb,FRE_FUJI_SYOHIN_KB_MAX_LENGTH));
	}


	// fre_pc_kbに対するセッターとゲッターの集合
	public boolean setFrePcKb(String fre_pc_kb)
	{
		this.fre_pc_kb = fre_pc_kb;
		return true;
	}
	public String getFrePcKb()
	{
		return cutString(this.fre_pc_kb,FRE_PC_KB_MAX_LENGTH);
	}
	public String getFrePcKbString()
	{
		return "'" + cutString(this.fre_pc_kb,FRE_PC_KB_MAX_LENGTH) + "'";
	}
	public String getFrePcKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_pc_kb,FRE_PC_KB_MAX_LENGTH));
	}


	// fre_daisi_no_nbに対するセッターとゲッターの集合
	public boolean setFreDaisiNoNb(String fre_daisi_no_nb)
	{
		this.fre_daisi_no_nb = fre_daisi_no_nb;
		return true;
	}
	public String getFreDaisiNoNb()
	{
		return cutString(this.fre_daisi_no_nb,FRE_DAISI_NO_NB_MAX_LENGTH);
	}
	public String getFreDaisiNoNbString()
	{
		return "'" + cutString(this.fre_daisi_no_nb,FRE_DAISI_NO_NB_MAX_LENGTH) + "'";
	}
	public String getFreDaisiNoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_daisi_no_nb,FRE_DAISI_NO_NB_MAX_LENGTH));
	}


	// fre_daicho_tenpo_kbに対するセッターとゲッターの集合
	public boolean setFreDaichoTenpoKb(String fre_daicho_tenpo_kb)
	{
		this.fre_daicho_tenpo_kb = fre_daicho_tenpo_kb;
		return true;
	}
	public String getFreDaichoTenpoKb()
	{
		return cutString(this.fre_daicho_tenpo_kb,FRE_DAICHO_TENPO_KB_MAX_LENGTH);
	}
	public String getFreDaichoTenpoKbString()
	{
		return "'" + cutString(this.fre_daicho_tenpo_kb,FRE_DAICHO_TENPO_KB_MAX_LENGTH) + "'";
	}
	public String getFreDaichoTenpoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_daicho_tenpo_kb,FRE_DAICHO_TENPO_KB_MAX_LENGTH));
	}


	// fre_daicho_honbu_kbに対するセッターとゲッターの集合
	public boolean setFreDaichoHonbuKb(String fre_daicho_honbu_kb)
	{
		this.fre_daicho_honbu_kb = fre_daicho_honbu_kb;
		return true;
	}
	public String getFreDaichoHonbuKb()
	{
		return cutString(this.fre_daicho_honbu_kb,FRE_DAICHO_HONBU_KB_MAX_LENGTH);
	}
	public String getFreDaichoHonbuKbString()
	{
		return "'" + cutString(this.fre_daicho_honbu_kb,FRE_DAICHO_HONBU_KB_MAX_LENGTH) + "'";
	}
	public String getFreDaichoHonbuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_daicho_honbu_kb,FRE_DAICHO_HONBU_KB_MAX_LENGTH));
	}


	// fre_daicho_siiresaki_kbに対するセッターとゲッターの集合
	public boolean setFreDaichoSiiresakiKb(String fre_daicho_siiresaki_kb)
	{
		this.fre_daicho_siiresaki_kb = fre_daicho_siiresaki_kb;
		return true;
	}
	public String getFreDaichoSiiresakiKb()
	{
		return cutString(this.fre_daicho_siiresaki_kb,FRE_DAICHO_SIIRESAKI_KB_MAX_LENGTH);
	}
	public String getFreDaichoSiiresakiKbString()
	{
		return "'" + cutString(this.fre_daicho_siiresaki_kb,FRE_DAICHO_SIIRESAKI_KB_MAX_LENGTH) + "'";
	}
	public String getFreDaichoSiiresakiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_daicho_siiresaki_kb,FRE_DAICHO_SIIRESAKI_KB_MAX_LENGTH));
	}


	// fre_unit_price_tani_kbに対するセッターとゲッターの集合
	public boolean setFreUnitPriceTaniKb(String fre_unit_price_tani_kb)
	{
		this.fre_unit_price_tani_kb = fre_unit_price_tani_kb;
		return true;
	}
	public String getFreUnitPriceTaniKb()
	{
		return cutString(this.fre_unit_price_tani_kb,FRE_UNIT_PRICE_TANI_KB_MAX_LENGTH);
	}
	public String getFreUnitPriceTaniKbString()
	{
		return "'" + cutString(this.fre_unit_price_tani_kb,FRE_UNIT_PRICE_TANI_KB_MAX_LENGTH) + "'";
	}
	public String getFreUnitPriceTaniKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_unit_price_tani_kb,FRE_UNIT_PRICE_TANI_KB_MAX_LENGTH));
	}


	// fre_unit_price_naiyoryo_qtに対するセッターとゲッターの集合
	public boolean setFreUnitPriceNaiyoryoQt(String fre_unit_price_naiyoryo_qt)
	{
		this.fre_unit_price_naiyoryo_qt = fre_unit_price_naiyoryo_qt;
		return true;
	}
	public String getFreUnitPriceNaiyoryoQt()
	{
		return cutString(this.fre_unit_price_naiyoryo_qt,FRE_UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH);
	}
	public String getFreUnitPriceNaiyoryoQtString()
	{
		return "'" + cutString(this.fre_unit_price_naiyoryo_qt,FRE_UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH) + "'";
	}
	public String getFreUnitPriceNaiyoryoQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_unit_price_naiyoryo_qt,FRE_UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH));
	}


	// fre_unit_price_kijun_tani_qtに対するセッターとゲッターの集合
	public boolean setFreUnitPriceKijunTaniQt(String fre_unit_price_kijun_tani_qt)
	{
		this.fre_unit_price_kijun_tani_qt = fre_unit_price_kijun_tani_qt;
		return true;
	}
	public String getFreUnitPriceKijunTaniQt()
	{
		return cutString(this.fre_unit_price_kijun_tani_qt,FRE_UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH);
	}
	public String getFreUnitPriceKijunTaniQtString()
	{
		return "'" + cutString(this.fre_unit_price_kijun_tani_qt,FRE_UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH) + "'";
	}
	public String getFreUnitPriceKijunTaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_unit_price_kijun_tani_qt,FRE_UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH));
	}


	// fre_syohi_kigen_dtに対するセッターとゲッターの集合
	public boolean setFreSyohiKigenDt(String fre_syohi_kigen_dt)
	{
		this.fre_syohi_kigen_dt = fre_syohi_kigen_dt;
		return true;
	}
	public String getFreSyohiKigenDt()
	{
		return cutString(this.fre_syohi_kigen_dt,FRE_SYOHI_KIGEN_DT_MAX_LENGTH);
	}
	public String getFreSyohiKigenDtString()
	{
		return "'" + cutString(this.fre_syohi_kigen_dt,FRE_SYOHI_KIGEN_DT_MAX_LENGTH) + "'";
	}
	public String getFreSyohiKigenDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_syohi_kigen_dt,FRE_SYOHI_KIGEN_DT_MAX_LENGTH));
	}


	// fre_syusei_kbに対するセッターとゲッターの集合
	public boolean setFreSyuseiKb(String fre_syusei_kb)
	{
		this.fre_syusei_kb = fre_syusei_kb;
		return true;
	}
	public String getFreSyuseiKb()
	{
		return cutString(this.fre_syusei_kb,FRE_SYUSEI_KB_MAX_LENGTH);
	}
	public String getFreSyuseiKbString()
	{
		return "'" + cutString(this.fre_syusei_kb,FRE_SYUSEI_KB_MAX_LENGTH) + "'";
	}
	public String getFreSyuseiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_syusei_kb,FRE_SYUSEI_KB_MAX_LENGTH));
	}


	// fre_sakusei_gyo_noに対するセッターとゲッターの集合
	public boolean setFreSakuseiGyoNo(String fre_sakusei_gyo_no)
	{
		try
		{
			this.fre_sakusei_gyo_no = Long.parseLong(fre_sakusei_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setFreSakuseiGyoNo(long fre_sakusei_gyo_no)
	{
		this.fre_sakusei_gyo_no = fre_sakusei_gyo_no;
		return true;
	}
	public long getFreSakuseiGyoNo()
	{
		return this.fre_sakusei_gyo_no;
	}
	public String getFreSakuseiGyoNoString()
	{
		return Long.toString(this.fre_sakusei_gyo_no);
	}


	// fre_mst_mainte_fgに対するセッターとゲッターの集合
	public boolean setFreMstMainteFg(String fre_mst_mainte_fg)
	{
		this.fre_mst_mainte_fg = fre_mst_mainte_fg;
		return true;
	}
	public String getFreMstMainteFg()
	{
		return cutString(this.fre_mst_mainte_fg,FRE_MST_MAINTE_FG_MAX_LENGTH);
	}
	public String getFreMstMainteFgString()
	{
		return "'" + cutString(this.fre_mst_mainte_fg,FRE_MST_MAINTE_FG_MAX_LENGTH) + "'";
	}
	public String getFreMstMainteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_mst_mainte_fg,FRE_MST_MAINTE_FG_MAX_LENGTH));
	}


	// fre_alarm_make_fgに対するセッターとゲッターの集合
	public boolean setFreAlarmMakeFg(String fre_alarm_make_fg)
	{
		this.fre_alarm_make_fg = fre_alarm_make_fg;
		return true;
	}
	public String getFreAlarmMakeFg()
	{
		return cutString(this.fre_alarm_make_fg,FRE_ALARM_MAKE_FG_MAX_LENGTH);
	}
	public String getFreAlarmMakeFgString()
	{
		return "'" + cutString(this.fre_alarm_make_fg,FRE_ALARM_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getFreAlarmMakeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_alarm_make_fg,FRE_ALARM_MAKE_FG_MAX_LENGTH));
	}


	// fre_insert_tsに対するセッターとゲッターの集合
	public boolean setFreInsertTs(String fre_insert_ts)
	{
		this.fre_insert_ts = fre_insert_ts;
		return true;
	}
	public String getFreInsertTs()
	{
		return cutString(this.fre_insert_ts,FRE_INSERT_TS_MAX_LENGTH);
	}
	public String getFreInsertTsString()
	{
		return "'" + cutString(this.fre_insert_ts,FRE_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getFreInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_insert_ts,FRE_INSERT_TS_MAX_LENGTH));
	}


	// fre_insert_user_idに対するセッターとゲッターの集合
	public boolean setFreInsertUserId(String fre_insert_user_id)
	{
		this.fre_insert_user_id = fre_insert_user_id;
		return true;
	}
	public String getFreInsertUserId()
	{
		return cutString(this.fre_insert_user_id,FRE_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getFreInsertUserIdString()
	{
		return "'" + cutString(this.fre_insert_user_id,FRE_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getFreInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_insert_user_id,FRE_INSERT_USER_ID_MAX_LENGTH));
	}


	// fre_update_tsに対するセッターとゲッターの集合
	public boolean setFreUpdateTs(String fre_update_ts)
	{
		this.fre_update_ts = fre_update_ts;
		return true;
	}
	public String getFreUpdateTs()
	{
		return cutString(this.fre_update_ts,FRE_UPDATE_TS_MAX_LENGTH);
	}
	public String getFreUpdateTsString()
	{
		return "'" + cutString(this.fre_update_ts,FRE_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getFreUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_update_ts,FRE_UPDATE_TS_MAX_LENGTH));
	}


	// fre_update_user_idに対するセッターとゲッターの集合
	public boolean setFreUpdateUserId(String fre_update_user_id)
	{
		this.fre_update_user_id = fre_update_user_id;
		return true;
	}
	public String getFreUpdateUserId()
	{
		return cutString(this.fre_update_user_id,FRE_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getFreUpdateUserIdString()
	{
		return "'" + cutString(this.fre_update_user_id,FRE_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getFreUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fre_update_user_id,FRE_UPDATE_USER_ID_MAX_LENGTH));
	}


	// gro_torikomi_dtに対するセッターとゲッターの集合
	public boolean setGroTorikomiDt(String gro_torikomi_dt)
	{
		this.gro_torikomi_dt = gro_torikomi_dt;
		return true;
	}
	public String getGroTorikomiDt()
	{
		return cutString(this.gro_torikomi_dt,GRO_TORIKOMI_DT_MAX_LENGTH);
	}
	public String getGroTorikomiDtString()
	{
		return "'" + cutString(this.gro_torikomi_dt,GRO_TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getGroTorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_torikomi_dt,GRO_TORIKOMI_DT_MAX_LENGTH));
	}


	// gro_excel_file_syubetsuに対するセッターとゲッターの集合
	public boolean setGroExcelFileSyubetsu(String gro_excel_file_syubetsu)
	{
		this.gro_excel_file_syubetsu = gro_excel_file_syubetsu;
		return true;
	}
	public String getGroExcelFileSyubetsu()
	{
		return cutString(this.gro_excel_file_syubetsu,GRO_EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getGroExcelFileSyubetsuString()
	{
		return "'" + cutString(this.gro_excel_file_syubetsu,GRO_EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getGroExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_excel_file_syubetsu,GRO_EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}


	// gro_uketsuke_noに対するセッターとゲッターの集合
	public boolean setGroUketsukeNo(String gro_uketsuke_no)
	{
		try
		{
			this.gro_uketsuke_no = Long.parseLong(gro_uketsuke_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGroUketsukeNo(long gro_uketsuke_no)
	{
		this.gro_uketsuke_no = gro_uketsuke_no;
		return true;
	}
	public long getGroUketsukeNo()
	{
		return this.gro_uketsuke_no;
	}
	public String getGroUketsukeNoString()
	{
		return Long.toString(this.gro_uketsuke_no);
	}


	// gro_uketsuke_seqに対するセッターとゲッターの集合
	public boolean setGroUketsukeSeq(String gro_uketsuke_seq)
	{
		try
		{
			this.gro_uketsuke_seq = Long.parseLong(gro_uketsuke_seq);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGroUketsukeSeq(long gro_uketsuke_seq)
	{
		this.gro_uketsuke_seq = gro_uketsuke_seq;
		return true;
	}
	public long getGroUketsukeSeq()
	{
		return this.gro_uketsuke_seq;
	}
	public String getGroUketsukeSeqString()
	{
		return Long.toString(this.gro_uketsuke_seq);
	}


	// gro_bumon_cdに対するセッターとゲッターの集合
	public boolean setGroBumonCd(String gro_bumon_cd)
	{
		this.gro_bumon_cd = gro_bumon_cd;
		return true;
	}
	public String getGroBumonCd()
	{
		return cutString(this.gro_bumon_cd,GRO_BUMON_CD_MAX_LENGTH);
	}
	public String getGroBumonCdString()
	{
		return "'" + cutString(this.gro_bumon_cd,GRO_BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getGroBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_bumon_cd,GRO_BUMON_CD_MAX_LENGTH));
	}


	// gro_unit_cdに対するセッターとゲッターの集合
	public boolean setGroUnitCd(String gro_unit_cd)
	{
		this.gro_unit_cd = gro_unit_cd;
		return true;
	}
	public String getGroUnitCd()
	{
		return cutString(this.gro_unit_cd,GRO_UNIT_CD_MAX_LENGTH);
	}
	public String getGroUnitCdString()
	{
		return "'" + cutString(this.gro_unit_cd,GRO_UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getGroUnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_unit_cd,GRO_UNIT_CD_MAX_LENGTH));
	}


	// gro_haifu_cdに対するセッターとゲッターの集合
	public boolean setGroHaifuCd(String gro_haifu_cd)
	{
		this.gro_haifu_cd = gro_haifu_cd;
		return true;
	}
	public String getGroHaifuCd()
	{
		return cutString(this.gro_haifu_cd,GRO_HAIFU_CD_MAX_LENGTH);
	}
	public String getGroHaifuCdString()
	{
		return "'" + cutString(this.gro_haifu_cd,GRO_HAIFU_CD_MAX_LENGTH) + "'";
	}
	public String getGroHaifuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_haifu_cd,GRO_HAIFU_CD_MAX_LENGTH));
	}


	// gro_subclass_cdに対するセッターとゲッターの集合
	public boolean setGroSubclassCd(String gro_subclass_cd)
	{
		this.gro_subclass_cd = gro_subclass_cd;
		return true;
	}
	public String getGroSubclassCd()
	{
		return cutString(this.gro_subclass_cd,GRO_SUBCLASS_CD_MAX_LENGTH);
	}
	public String getGroSubclassCdString()
	{
		return "'" + cutString(this.gro_subclass_cd,GRO_SUBCLASS_CD_MAX_LENGTH) + "'";
	}
	public String getGroSubclassCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_subclass_cd,GRO_SUBCLASS_CD_MAX_LENGTH));
	}


	// gro_syohin_cdに対するセッターとゲッターの集合
	public boolean setGroSyohinCd(String gro_syohin_cd)
	{
		this.gro_syohin_cd = gro_syohin_cd;
		return true;
	}
	public String getGroSyohinCd()
	{
		return cutString(this.gro_syohin_cd,GRO_SYOHIN_CD_MAX_LENGTH);
	}
	public String getGroSyohinCdString()
	{
		return "'" + cutString(this.gro_syohin_cd,GRO_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getGroSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_syohin_cd,GRO_SYOHIN_CD_MAX_LENGTH));
	}


	// gro_yuko_dtに対するセッターとゲッターの集合
	public boolean setGroYukoDt(String gro_yuko_dt)
	{
		this.gro_yuko_dt = gro_yuko_dt;
		return true;
	}
	public String getGroYukoDt()
	{
		return cutString(this.gro_yuko_dt,GRO_YUKO_DT_MAX_LENGTH);
	}
	public String getGroYukoDtString()
	{
		return "'" + cutString(this.gro_yuko_dt,GRO_YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getGroYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_yuko_dt,GRO_YUKO_DT_MAX_LENGTH));
	}


	// gro_maker_cdに対するセッターとゲッターの集合
	public boolean setGroMakerCd(String gro_maker_cd)
	{
		this.gro_maker_cd = gro_maker_cd;
		return true;
	}
	public String getGroMakerCd()
	{
		return cutString(this.gro_maker_cd,GRO_MAKER_CD_MAX_LENGTH);
	}
	public String getGroMakerCdString()
	{
		return "'" + cutString(this.gro_maker_cd,GRO_MAKER_CD_MAX_LENGTH) + "'";
	}
	public String getGroMakerCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_maker_cd,GRO_MAKER_CD_MAX_LENGTH));
	}


	// gro_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setGroHinmeiKanjiNa(String gro_hinmei_kanji_na)
	{
		this.gro_hinmei_kanji_na = gro_hinmei_kanji_na;
		return true;
	}
	public String getGroHinmeiKanjiNa()
	{
		return cutString(this.gro_hinmei_kanji_na,GRO_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getGroHinmeiKanjiNaString()
	{
		return "'" + cutString(this.gro_hinmei_kanji_na,GRO_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getGroHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_hinmei_kanji_na,GRO_HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// gro_kikaku_kanji_naに対するセッターとゲッターの集合
	public boolean setGroKikakuKanjiNa(String gro_kikaku_kanji_na)
	{
		this.gro_kikaku_kanji_na = gro_kikaku_kanji_na;
		return true;
	}
	public String getGroKikakuKanjiNa()
	{
		return cutString(this.gro_kikaku_kanji_na,GRO_KIKAKU_KANJI_NA_MAX_LENGTH);
	}
	public String getGroKikakuKanjiNaString()
	{
		return "'" + cutString(this.gro_kikaku_kanji_na,GRO_KIKAKU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getGroKikakuKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_kikaku_kanji_na,GRO_KIKAKU_KANJI_NA_MAX_LENGTH));
	}


	// gro_kikaku_kanji_2_naに対するセッターとゲッターの集合
	public boolean setGroKikakuKanji2Na(String gro_kikaku_kanji_2_na)
	{
		this.gro_kikaku_kanji_2_na = gro_kikaku_kanji_2_na;
		return true;
	}
	public String getGroKikakuKanji2Na()
	{
		return cutString(this.gro_kikaku_kanji_2_na,GRO_KIKAKU_KANJI_2_NA_MAX_LENGTH);
	}
	public String getGroKikakuKanji2NaString()
	{
		return "'" + cutString(this.gro_kikaku_kanji_2_na,GRO_KIKAKU_KANJI_2_NA_MAX_LENGTH) + "'";
	}
	public String getGroKikakuKanji2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_kikaku_kanji_2_na,GRO_KIKAKU_KANJI_2_NA_MAX_LENGTH));
	}


	// gro_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setGroHinmeiKanaNa(String gro_hinmei_kana_na)
	{
		this.gro_hinmei_kana_na = gro_hinmei_kana_na;
		return true;
	}
	public String getGroHinmeiKanaNa()
	{
		return cutString(this.gro_hinmei_kana_na,GRO_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getGroHinmeiKanaNaString()
	{
		return "'" + cutString(this.gro_hinmei_kana_na,GRO_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getGroHinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_hinmei_kana_na,GRO_HINMEI_KANA_NA_MAX_LENGTH));
	}


	// gro_kikaku_kana_naに対するセッターとゲッターの集合
	public boolean setGroKikakuKanaNa(String gro_kikaku_kana_na)
	{
		this.gro_kikaku_kana_na = gro_kikaku_kana_na;
		return true;
	}
	public String getGroKikakuKanaNa()
	{
		return cutString(this.gro_kikaku_kana_na,GRO_KIKAKU_KANA_NA_MAX_LENGTH);
	}
	public String getGroKikakuKanaNaString()
	{
		return "'" + cutString(this.gro_kikaku_kana_na,GRO_KIKAKU_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getGroKikakuKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_kikaku_kana_na,GRO_KIKAKU_KANA_NA_MAX_LENGTH));
	}


	// gro_kikaku_kana_2_naに対するセッターとゲッターの集合
	public boolean setGroKikakuKana2Na(String gro_kikaku_kana_2_na)
	{
		this.gro_kikaku_kana_2_na = gro_kikaku_kana_2_na;
		return true;
	}
	public String getGroKikakuKana2Na()
	{
		return cutString(this.gro_kikaku_kana_2_na,GRO_KIKAKU_KANA_2_NA_MAX_LENGTH);
	}
	public String getGroKikakuKana2NaString()
	{
		return "'" + cutString(this.gro_kikaku_kana_2_na,GRO_KIKAKU_KANA_2_NA_MAX_LENGTH) + "'";
	}
	public String getGroKikakuKana2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_kikaku_kana_2_na,GRO_KIKAKU_KANA_2_NA_MAX_LENGTH));
	}


	// gro_rec_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setGroRecHinmeiKanjiNa(String gro_rec_hinmei_kanji_na)
	{
		this.gro_rec_hinmei_kanji_na = gro_rec_hinmei_kanji_na;
		return true;
	}
	public String getGroRecHinmeiKanjiNa()
	{
		return cutString(this.gro_rec_hinmei_kanji_na,GRO_REC_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getGroRecHinmeiKanjiNaString()
	{
		return "'" + cutString(this.gro_rec_hinmei_kanji_na,GRO_REC_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getGroRecHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_rec_hinmei_kanji_na,GRO_REC_HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// gro_rec_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setGroRecHinmeiKanaNa(String gro_rec_hinmei_kana_na)
	{
		this.gro_rec_hinmei_kana_na = gro_rec_hinmei_kana_na;
		return true;
	}
	public String getGroRecHinmeiKanaNa()
	{
		return cutString(this.gro_rec_hinmei_kana_na,GRO_REC_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getGroRecHinmeiKanaNaString()
	{
		return "'" + cutString(this.gro_rec_hinmei_kana_na,GRO_REC_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getGroRecHinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_rec_hinmei_kana_na,GRO_REC_HINMEI_KANA_NA_MAX_LENGTH));
	}


	// gro_gentanka_vlに対するセッターとゲッターの集合
	public boolean setGroGentankaVl(String gro_gentanka_vl)
	{
		this.gro_gentanka_vl = gro_gentanka_vl;
		return true;
	}
	public String getGroGentankaVl()
	{
		return cutString(this.gro_gentanka_vl,GRO_GENTANKA_VL_MAX_LENGTH);
	}
	public String getGroGentankaVlString()
	{
		return "'" + cutString(this.gro_gentanka_vl,GRO_GENTANKA_VL_MAX_LENGTH) + "'";
	}
	public String getGroGentankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_gentanka_vl,GRO_GENTANKA_VL_MAX_LENGTH));
	}


	// gro_baitanka_vlに対するセッターとゲッターの集合
	public boolean setGroBaitankaVl(String gro_baitanka_vl)
	{
		this.gro_baitanka_vl = gro_baitanka_vl;
		return true;
	}
	public String getGroBaitankaVl()
	{
		return cutString(this.gro_baitanka_vl,GRO_BAITANKA_VL_MAX_LENGTH);
	}
	public String getGroBaitankaVlString()
	{
		return "'" + cutString(this.gro_baitanka_vl,GRO_BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getGroBaitankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_baitanka_vl,GRO_BAITANKA_VL_MAX_LENGTH));
	}


	// gro_plu_send_dtに対するセッターとゲッターの集合
	public boolean setGroPluSendDt(String gro_plu_send_dt)
	{
		this.gro_plu_send_dt = gro_plu_send_dt;
		return true;
	}
	public String getGroPluSendDt()
	{
		return cutString(this.gro_plu_send_dt,GRO_PLU_SEND_DT_MAX_LENGTH);
	}
	public String getGroPluSendDtString()
	{
		return "'" + cutString(this.gro_plu_send_dt,GRO_PLU_SEND_DT_MAX_LENGTH) + "'";
	}
	public String getGroPluSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_plu_send_dt,GRO_PLU_SEND_DT_MAX_LENGTH));
	}


//	// gro_area_cdに対するセッターとゲッターの集合
//	public boolean setGroAreaCd(String gro_area_cd)
//	{
//		this.gro_area_cd = gro_area_cd;
//		return true;
//	}
//	public String getGroAreaCd()
//	{
//		return cutString(this.gro_area_cd,GRO_AREA_CD_MAX_LENGTH);
//	}
//	public String getGroAreaCdString()
//	{
//		return "'" + cutString(this.gro_area_cd,GRO_AREA_CD_MAX_LENGTH) + "'";
//	}
//	public String getGroAreaCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.gro_area_cd,GRO_AREA_CD_MAX_LENGTH));
//	}


	// gro_siiresaki_cdに対するセッターとゲッターの集合
	public boolean setGroSiiresakiCd(String gro_siiresaki_cd)
	{
		this.gro_siiresaki_cd = gro_siiresaki_cd;
		return true;
	}
	public String getGroSiiresakiCd()
	{
		return cutString(this.gro_siiresaki_cd,GRO_SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getGroSiiresakiCdString()
	{
		return "'" + cutString(this.gro_siiresaki_cd,GRO_SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getGroSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_siiresaki_cd,GRO_SIIRESAKI_CD_MAX_LENGTH));
	}


	// gro_ten_siiresaki_kanri_cdに対するセッターとゲッターの集合
	public boolean setGroTenSiiresakiKanriCd(String gro_ten_siiresaki_kanri_cd)
	{
		this.gro_ten_siiresaki_kanri_cd = gro_ten_siiresaki_kanri_cd;
		return true;
	}
	public String getGroTenSiiresakiKanriCd()
	{
		return cutString(this.gro_ten_siiresaki_kanri_cd,GRO_TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH);
	}
	public String getGroTenSiiresakiKanriCdString()
	{
		return "'" + cutString(this.gro_ten_siiresaki_kanri_cd,GRO_TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getGroTenSiiresakiKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_ten_siiresaki_kanri_cd,GRO_TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH));
	}


	// gro_siire_hinban_cdに対するセッターとゲッターの集合
	public boolean setGroSiireHinbanCd(String gro_siire_hinban_cd)
	{
		this.gro_siire_hinban_cd = gro_siire_hinban_cd;
		return true;
	}
	public String getGroSiireHinbanCd()
	{
		return cutString(this.gro_siire_hinban_cd,GRO_SIIRE_HINBAN_CD_MAX_LENGTH);
	}
	public String getGroSiireHinbanCdString()
	{
		return "'" + cutString(this.gro_siire_hinban_cd,GRO_SIIRE_HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getGroSiireHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_siire_hinban_cd,GRO_SIIRE_HINBAN_CD_MAX_LENGTH));
	}


	// gro_hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setGroHanbaiStDt(String gro_hanbai_st_dt)
	{
		this.gro_hanbai_st_dt = gro_hanbai_st_dt;
		return true;
	}
	public String getGroHanbaiStDt()
	{
		return cutString(this.gro_hanbai_st_dt,GRO_HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getGroHanbaiStDtString()
	{
		return "'" + cutString(this.gro_hanbai_st_dt,GRO_HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getGroHanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_hanbai_st_dt,GRO_HANBAI_ST_DT_MAX_LENGTH));
	}


	// gro_hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setGroHanbaiEdDt(String gro_hanbai_ed_dt)
	{
		this.gro_hanbai_ed_dt = gro_hanbai_ed_dt;
		return true;
	}
	public String getGroHanbaiEdDt()
	{
		return cutString(this.gro_hanbai_ed_dt,GRO_HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getGroHanbaiEdDtString()
	{
		return "'" + cutString(this.gro_hanbai_ed_dt,GRO_HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getGroHanbaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_hanbai_ed_dt,GRO_HANBAI_ED_DT_MAX_LENGTH));
	}


	// gro_ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setGroTenHachuStDt(String gro_ten_hachu_st_dt)
	{
		this.gro_ten_hachu_st_dt = gro_ten_hachu_st_dt;
		return true;
	}
	public String getGroTenHachuStDt()
	{
		return cutString(this.gro_ten_hachu_st_dt,GRO_TEN_HACHU_ST_DT_MAX_LENGTH);
	}
	public String getGroTenHachuStDtString()
	{
		return "'" + cutString(this.gro_ten_hachu_st_dt,GRO_TEN_HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getGroTenHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_ten_hachu_st_dt,GRO_TEN_HACHU_ST_DT_MAX_LENGTH));
	}


	// gro_ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setGroTenHachuEdDt(String gro_ten_hachu_ed_dt)
	{
		this.gro_ten_hachu_ed_dt = gro_ten_hachu_ed_dt;
		return true;
	}
	public String getGroTenHachuEdDt()
	{
		return cutString(this.gro_ten_hachu_ed_dt,GRO_TEN_HACHU_ED_DT_MAX_LENGTH);
	}
	public String getGroTenHachuEdDtString()
	{
		return "'" + cutString(this.gro_ten_hachu_ed_dt,GRO_TEN_HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getGroTenHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_ten_hachu_ed_dt,GRO_TEN_HACHU_ED_DT_MAX_LENGTH));
	}


	// gro_eos_kbに対するセッターとゲッターの集合
	public boolean setGroEosKb(String gro_eos_kb)
	{
		this.gro_eos_kb = gro_eos_kb;
		return true;
	}
	public String getGroEosKb()
	{
		return cutString(this.gro_eos_kb,GRO_EOS_KB_MAX_LENGTH);
	}
	public String getGroEosKbString()
	{
		return "'" + cutString(this.gro_eos_kb,GRO_EOS_KB_MAX_LENGTH) + "'";
	}
	public String getGroEosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_eos_kb,GRO_EOS_KB_MAX_LENGTH));
	}


	// gro_honbu_zai_kbに対するセッターとゲッターの集合
	public boolean setGroHonbuZaiKb(String gro_honbu_zai_kb)
	{
		this.gro_honbu_zai_kb = gro_honbu_zai_kb;
		return true;
	}
	public String getGroHonbuZaiKb()
	{
		return cutString(this.gro_honbu_zai_kb,GRO_HONBU_ZAI_KB_MAX_LENGTH);
	}
	public String getGroHonbuZaiKbString()
	{
		return "'" + cutString(this.gro_honbu_zai_kb,GRO_HONBU_ZAI_KB_MAX_LENGTH) + "'";
	}
	public String getGroHonbuZaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_honbu_zai_kb,GRO_HONBU_ZAI_KB_MAX_LENGTH));
	}


	// gro_hachu_tani_naに対するセッターとゲッターの集合
	public boolean setGroHachuTaniNa(String gro_hachu_tani_na)
	{
		this.gro_hachu_tani_na = gro_hachu_tani_na;
		return true;
	}
	public String getGroHachuTaniNa()
	{
		return cutString(this.gro_hachu_tani_na,GRO_HACHU_TANI_NA_MAX_LENGTH);
	}
	public String getGroHachuTaniNaString()
	{
		return "'" + cutString(this.gro_hachu_tani_na,GRO_HACHU_TANI_NA_MAX_LENGTH) + "'";
	}
	public String getGroHachuTaniNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_hachu_tani_na,GRO_HACHU_TANI_NA_MAX_LENGTH));
	}


	// gro_hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setGroHachutaniIrisuQt(String gro_hachutani_irisu_qt)
	{
		this.gro_hachutani_irisu_qt = gro_hachutani_irisu_qt;
		return true;
	}
	public String getGroHachutaniIrisuQt()
	{
		return cutString(this.gro_hachutani_irisu_qt,GRO_HACHUTANI_IRISU_QT_MAX_LENGTH);
	}
	public String getGroHachutaniIrisuQtString()
	{
		return "'" + cutString(this.gro_hachutani_irisu_qt,GRO_HACHUTANI_IRISU_QT_MAX_LENGTH) + "'";
	}
	public String getGroHachutaniIrisuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_hachutani_irisu_qt,GRO_HACHUTANI_IRISU_QT_MAX_LENGTH));
	}


	// gro_max_hachutani_qtに対するセッターとゲッターの集合
	public boolean setGroMaxHachutaniQt(String gro_max_hachutani_qt)
	{
		this.gro_max_hachutani_qt = gro_max_hachutani_qt;
		return true;
	}
	public String getGroMaxHachutaniQt()
	{
		return cutString(this.gro_max_hachutani_qt,GRO_MAX_HACHUTANI_QT_MAX_LENGTH);
	}
	public String getGroMaxHachutaniQtString()
	{
		return "'" + cutString(this.gro_max_hachutani_qt,GRO_MAX_HACHUTANI_QT_MAX_LENGTH) + "'";
	}
	public String getGroMaxHachutaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_max_hachutani_qt,GRO_MAX_HACHUTANI_QT_MAX_LENGTH));
	}


	// gro_bland_cdに対するセッターとゲッターの集合
	public boolean setGroBlandCd(String gro_bland_cd)
	{
		this.gro_bland_cd = gro_bland_cd;
		return true;
	}
	public String getGroBlandCd()
	{
		return cutString(this.gro_bland_cd,GRO_BLAND_CD_MAX_LENGTH);
	}
	public String getGroBlandCdString()
	{
		return "'" + cutString(this.gro_bland_cd,GRO_BLAND_CD_MAX_LENGTH) + "'";
	}
	public String getGroBlandCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_bland_cd,GRO_BLAND_CD_MAX_LENGTH));
	}


	// gro_pb_kbに対するセッターとゲッターの集合
	public boolean setGroPbKb(String gro_pb_kb)
	{
		this.gro_pb_kb = gro_pb_kb;
		return true;
	}
	public String getGroPbKb()
	{
		return cutString(this.gro_pb_kb,GRO_PB_KB_MAX_LENGTH);
	}
	public String getGroPbKbString()
	{
		return "'" + cutString(this.gro_pb_kb,GRO_PB_KB_MAX_LENGTH) + "'";
	}
	public String getGroPbKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_pb_kb,GRO_PB_KB_MAX_LENGTH));
	}


	// gro_maker_kibo_kakaku_vlに対するセッターとゲッターの集合
	public boolean setGroMakerKiboKakakuVl(String gro_maker_kibo_kakaku_vl)
	{
		this.gro_maker_kibo_kakaku_vl = gro_maker_kibo_kakaku_vl;
		return true;
	}
	public String getGroMakerKiboKakakuVl()
	{
		return cutString(this.gro_maker_kibo_kakaku_vl,GRO_MAKER_KIBO_KAKAKU_VL_MAX_LENGTH);
	}
	public String getGroMakerKiboKakakuVlString()
	{
		return "'" + cutString(this.gro_maker_kibo_kakaku_vl,GRO_MAKER_KIBO_KAKAKU_VL_MAX_LENGTH) + "'";
	}
	public String getGroMakerKiboKakakuVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_maker_kibo_kakaku_vl,GRO_MAKER_KIBO_KAKAKU_VL_MAX_LENGTH));
	}


	// gro_fuji_syohin_kbに対するセッターとゲッターの集合
	public boolean setGroFujiSyohinKb(String gro_fuji_syohin_kb)
	{
		this.gro_fuji_syohin_kb = gro_fuji_syohin_kb;
		return true;
	}
	public String getGroFujiSyohinKb()
	{
		return cutString(this.gro_fuji_syohin_kb,GRO_FUJI_SYOHIN_KB_MAX_LENGTH);
	}
	public String getGroFujiSyohinKbString()
	{
		return "'" + cutString(this.gro_fuji_syohin_kb,GRO_FUJI_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getGroFujiSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_fuji_syohin_kb,GRO_FUJI_SYOHIN_KB_MAX_LENGTH));
	}


	// gro_pc_kbに対するセッターとゲッターの集合
	public boolean setGroPcKb(String gro_pc_kb)
	{
		this.gro_pc_kb = gro_pc_kb;
		return true;
	}
	public String getGroPcKb()
	{
		return cutString(this.gro_pc_kb,GRO_PC_KB_MAX_LENGTH);
	}
	public String getGroPcKbString()
	{
		return "'" + cutString(this.gro_pc_kb,GRO_PC_KB_MAX_LENGTH) + "'";
	}
	public String getGroPcKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_pc_kb,GRO_PC_KB_MAX_LENGTH));
	}


	// gro_daisi_no_nbに対するセッターとゲッターの集合
	public boolean setGroDaisiNoNb(String gro_daisi_no_nb)
	{
		this.gro_daisi_no_nb = gro_daisi_no_nb;
		return true;
	}
	public String getGroDaisiNoNb()
	{
		return cutString(this.gro_daisi_no_nb,GRO_DAISI_NO_NB_MAX_LENGTH);
	}
	public String getGroDaisiNoNbString()
	{
		return "'" + cutString(this.gro_daisi_no_nb,GRO_DAISI_NO_NB_MAX_LENGTH) + "'";
	}
	public String getGroDaisiNoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_daisi_no_nb,GRO_DAISI_NO_NB_MAX_LENGTH));
	}


	// gro_daicho_tenpo_kbに対するセッターとゲッターの集合
	public boolean setGroDaichoTenpoKb(String gro_daicho_tenpo_kb)
	{
		this.gro_daicho_tenpo_kb = gro_daicho_tenpo_kb;
		return true;
	}
	public String getGroDaichoTenpoKb()
	{
		return cutString(this.gro_daicho_tenpo_kb,GRO_DAICHO_TENPO_KB_MAX_LENGTH);
	}
	public String getGroDaichoTenpoKbString()
	{
		return "'" + cutString(this.gro_daicho_tenpo_kb,GRO_DAICHO_TENPO_KB_MAX_LENGTH) + "'";
	}
	public String getGroDaichoTenpoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_daicho_tenpo_kb,GRO_DAICHO_TENPO_KB_MAX_LENGTH));
	}


	// gro_daicho_honbu_kbに対するセッターとゲッターの集合
	public boolean setGroDaichoHonbuKb(String gro_daicho_honbu_kb)
	{
		this.gro_daicho_honbu_kb = gro_daicho_honbu_kb;
		return true;
	}
	public String getGroDaichoHonbuKb()
	{
		return cutString(this.gro_daicho_honbu_kb,GRO_DAICHO_HONBU_KB_MAX_LENGTH);
	}
	public String getGroDaichoHonbuKbString()
	{
		return "'" + cutString(this.gro_daicho_honbu_kb,GRO_DAICHO_HONBU_KB_MAX_LENGTH) + "'";
	}
	public String getGroDaichoHonbuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_daicho_honbu_kb,GRO_DAICHO_HONBU_KB_MAX_LENGTH));
	}


	// gro_daicho_siiresaki_kbに対するセッターとゲッターの集合
	public boolean setGroDaichoSiiresakiKb(String gro_daicho_siiresaki_kb)
	{
		this.gro_daicho_siiresaki_kb = gro_daicho_siiresaki_kb;
		return true;
	}
	public String getGroDaichoSiiresakiKb()
	{
		return cutString(this.gro_daicho_siiresaki_kb,GRO_DAICHO_SIIRESAKI_KB_MAX_LENGTH);
	}
	public String getGroDaichoSiiresakiKbString()
	{
		return "'" + cutString(this.gro_daicho_siiresaki_kb,GRO_DAICHO_SIIRESAKI_KB_MAX_LENGTH) + "'";
	}
	public String getGroDaichoSiiresakiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_daicho_siiresaki_kb,GRO_DAICHO_SIIRESAKI_KB_MAX_LENGTH));
	}


	// gro_syuzei_hokoku_kbに対するセッターとゲッターの集合
	public boolean setGroSyuzeiHokokuKb(String gro_syuzei_hokoku_kb)
	{
		this.gro_syuzei_hokoku_kb = gro_syuzei_hokoku_kb;
		return true;
	}
	public String getGroSyuzeiHokokuKb()
	{
		return cutString(this.gro_syuzei_hokoku_kb,GRO_SYUZEI_HOKOKU_KB_MAX_LENGTH);
	}
	public String getGroSyuzeiHokokuKbString()
	{
		return "'" + cutString(this.gro_syuzei_hokoku_kb,GRO_SYUZEI_HOKOKU_KB_MAX_LENGTH) + "'";
	}
	public String getGroSyuzeiHokokuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_syuzei_hokoku_kb,GRO_SYUZEI_HOKOKU_KB_MAX_LENGTH));
	}


	// gro_unit_price_tani_kbに対するセッターとゲッターの集合
	public boolean setGroUnitPriceTaniKb(String gro_unit_price_tani_kb)
	{
		this.gro_unit_price_tani_kb = gro_unit_price_tani_kb;
		return true;
	}
	public String getGroUnitPriceTaniKb()
	{
		return cutString(this.gro_unit_price_tani_kb,GRO_UNIT_PRICE_TANI_KB_MAX_LENGTH);
	}
	public String getGroUnitPriceTaniKbString()
	{
		return "'" + cutString(this.gro_unit_price_tani_kb,GRO_UNIT_PRICE_TANI_KB_MAX_LENGTH) + "'";
	}
	public String getGroUnitPriceTaniKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_unit_price_tani_kb,GRO_UNIT_PRICE_TANI_KB_MAX_LENGTH));
	}


	// gro_unit_price_naiyoryo_qtに対するセッターとゲッターの集合
	public boolean setGroUnitPriceNaiyoryoQt(String gro_unit_price_naiyoryo_qt)
	{
		this.gro_unit_price_naiyoryo_qt = gro_unit_price_naiyoryo_qt;
		return true;
	}
	public String getGroUnitPriceNaiyoryoQt()
	{
		return cutString(this.gro_unit_price_naiyoryo_qt,GRO_UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH);
	}
	public String getGroUnitPriceNaiyoryoQtString()
	{
		return "'" + cutString(this.gro_unit_price_naiyoryo_qt,GRO_UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH) + "'";
	}
	public String getGroUnitPriceNaiyoryoQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_unit_price_naiyoryo_qt,GRO_UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH));
	}


	// gro_unit_price_kijun_tani_qtに対するセッターとゲッターの集合
	public boolean setGroUnitPriceKijunTaniQt(String gro_unit_price_kijun_tani_qt)
	{
		this.gro_unit_price_kijun_tani_qt = gro_unit_price_kijun_tani_qt;
		return true;
	}
	public String getGroUnitPriceKijunTaniQt()
	{
		return cutString(this.gro_unit_price_kijun_tani_qt,GRO_UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH);
	}
	public String getGroUnitPriceKijunTaniQtString()
	{
		return "'" + cutString(this.gro_unit_price_kijun_tani_qt,GRO_UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH) + "'";
	}
	public String getGroUnitPriceKijunTaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_unit_price_kijun_tani_qt,GRO_UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH));
	}


	// gro_syohi_kigen_dtに対するセッターとゲッターの集合
	public boolean setGroSyohiKigenDt(String gro_syohi_kigen_dt)
	{
		this.gro_syohi_kigen_dt = gro_syohi_kigen_dt;
		return true;
	}
	public String getGroSyohiKigenDt()
	{
		return cutString(this.gro_syohi_kigen_dt,GRO_SYOHI_KIGEN_DT_MAX_LENGTH);
	}
	public String getGroSyohiKigenDtString()
	{
		return "'" + cutString(this.gro_syohi_kigen_dt,GRO_SYOHI_KIGEN_DT_MAX_LENGTH) + "'";
	}
	public String getGroSyohiKigenDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_syohi_kigen_dt,GRO_SYOHI_KIGEN_DT_MAX_LENGTH));
	}


	// gro_syusei_kbに対するセッターとゲッターの集合
	public boolean setGroSyuseiKb(String gro_syusei_kb)
	{
		this.gro_syusei_kb = gro_syusei_kb;
		return true;
	}
	public String getGroSyuseiKb()
	{
		return cutString(this.gro_syusei_kb,GRO_SYUSEI_KB_MAX_LENGTH);
	}
	public String getGroSyuseiKbString()
	{
		return "'" + cutString(this.gro_syusei_kb,GRO_SYUSEI_KB_MAX_LENGTH) + "'";
	}
	public String getGroSyuseiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_syusei_kb,GRO_SYUSEI_KB_MAX_LENGTH));
	}


	// gro_sakusei_gyo_noに対するセッターとゲッターの集合
	public boolean setGroSakuseiGyoNo(String gro_sakusei_gyo_no)
	{
		try
		{
			this.gro_sakusei_gyo_no = Long.parseLong(gro_sakusei_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGroSakuseiGyoNo(long gro_sakusei_gyo_no)
	{
		this.gro_sakusei_gyo_no = gro_sakusei_gyo_no;
		return true;
	}
	public long getGroSakuseiGyoNo()
	{
		return this.gro_sakusei_gyo_no;
	}
	public String getGroSakuseiGyoNoString()
	{
		return Long.toString(this.gro_sakusei_gyo_no);
	}


	// gro_mst_mainte_fgに対するセッターとゲッターの集合
	public boolean setGroMstMainteFg(String gro_mst_mainte_fg)
	{
		this.gro_mst_mainte_fg = gro_mst_mainte_fg;
		return true;
	}
	public String getGroMstMainteFg()
	{
		return cutString(this.gro_mst_mainte_fg,GRO_MST_MAINTE_FG_MAX_LENGTH);
	}
	public String getGroMstMainteFgString()
	{
		return "'" + cutString(this.gro_mst_mainte_fg,GRO_MST_MAINTE_FG_MAX_LENGTH) + "'";
	}
	public String getGroMstMainteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_mst_mainte_fg,GRO_MST_MAINTE_FG_MAX_LENGTH));
	}


	// gro_alarm_make_fgに対するセッターとゲッターの集合
	public boolean setGroAlarmMakeFg(String gro_alarm_make_fg)
	{
		this.gro_alarm_make_fg = gro_alarm_make_fg;
		return true;
	}
	public String getGroAlarmMakeFg()
	{
		return cutString(this.gro_alarm_make_fg,GRO_ALARM_MAKE_FG_MAX_LENGTH);
	}
	public String getGroAlarmMakeFgString()
	{
		return "'" + cutString(this.gro_alarm_make_fg,GRO_ALARM_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getGroAlarmMakeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_alarm_make_fg,GRO_ALARM_MAKE_FG_MAX_LENGTH));
	}


	// gro_insert_tsに対するセッターとゲッターの集合
	public boolean setGroInsertTs(String gro_insert_ts)
	{
		this.gro_insert_ts = gro_insert_ts;
		return true;
	}
	public String getGroInsertTs()
	{
		return cutString(this.gro_insert_ts,GRO_INSERT_TS_MAX_LENGTH);
	}
	public String getGroInsertTsString()
	{
		return "'" + cutString(this.gro_insert_ts,GRO_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getGroInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_insert_ts,GRO_INSERT_TS_MAX_LENGTH));
	}


	// gro_insert_user_idに対するセッターとゲッターの集合
	public boolean setGroInsertUserId(String gro_insert_user_id)
	{
		this.gro_insert_user_id = gro_insert_user_id;
		return true;
	}
	public String getGroInsertUserId()
	{
		return cutString(this.gro_insert_user_id,GRO_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getGroInsertUserIdString()
	{
		return "'" + cutString(this.gro_insert_user_id,GRO_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getGroInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_insert_user_id,GRO_INSERT_USER_ID_MAX_LENGTH));
	}


	// gro_update_tsに対するセッターとゲッターの集合
	public boolean setGroUpdateTs(String gro_update_ts)
	{
		this.gro_update_ts = gro_update_ts;
		return true;
	}
	public String getGroUpdateTs()
	{
		return cutString(this.gro_update_ts,GRO_UPDATE_TS_MAX_LENGTH);
	}
	public String getGroUpdateTsString()
	{
		return "'" + cutString(this.gro_update_ts,GRO_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getGroUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_update_ts,GRO_UPDATE_TS_MAX_LENGTH));
	}


	// gro_update_user_idに対するセッターとゲッターの集合
	public boolean setGroUpdateUserId(String gro_update_user_id)
	{
		this.gro_update_user_id = gro_update_user_id;
		return true;
	}
	public String getGroUpdateUserId()
	{
		return cutString(this.gro_update_user_id,GRO_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getGroUpdateUserIdString()
	{
		return "'" + cutString(this.gro_update_user_id,GRO_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getGroUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gro_update_user_id,GRO_UPDATE_USER_ID_MAX_LENGTH));
	}


	// a07_torikomi_dtに対するセッターとゲッターの集合
	public boolean setA07TorikomiDt(String a07_torikomi_dt)
	{
		this.a07_torikomi_dt = a07_torikomi_dt;
		return true;
	}
	public String getA07TorikomiDt()
	{
		return cutString(this.a07_torikomi_dt,A07_TORIKOMI_DT_MAX_LENGTH);
	}
	public String getA07TorikomiDtString()
	{
		return "'" + cutString(this.a07_torikomi_dt,A07_TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getA07TorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_torikomi_dt,A07_TORIKOMI_DT_MAX_LENGTH));
	}


	// a07_excel_file_syubetsuに対するセッターとゲッターの集合
	public boolean setA07ExcelFileSyubetsu(String a07_excel_file_syubetsu)
	{
		this.a07_excel_file_syubetsu = a07_excel_file_syubetsu;
		return true;
	}
	public String getA07ExcelFileSyubetsu()
	{
		return cutString(this.a07_excel_file_syubetsu,A07_EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getA07ExcelFileSyubetsuString()
	{
		return "'" + cutString(this.a07_excel_file_syubetsu,A07_EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getA07ExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_excel_file_syubetsu,A07_EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}


	// a07_uketsuke_noに対するセッターとゲッターの集合
	public boolean setA07UketsukeNo(String a07_uketsuke_no)
	{
		try
		{
			this.a07_uketsuke_no = Long.parseLong(a07_uketsuke_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setA07UketsukeNo(long a07_uketsuke_no)
	{
		this.a07_uketsuke_no = a07_uketsuke_no;
		return true;
	}
	public long getA07UketsukeNo()
	{
		return this.a07_uketsuke_no;
	}
	public String getA07UketsukeNoString()
	{
		return Long.toString(this.a07_uketsuke_no);
	}


	// a07_uketsuke_seqに対するセッターとゲッターの集合
	public boolean setA07UketsukeSeq(String a07_uketsuke_seq)
	{
		try
		{
			this.a07_uketsuke_seq = Long.parseLong(a07_uketsuke_seq);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setA07UketsukeSeq(long a07_uketsuke_seq)
	{
		this.a07_uketsuke_seq = a07_uketsuke_seq;
		return true;
	}
	public long getA07UketsukeSeq()
	{
		return this.a07_uketsuke_seq;
	}
	public String getA07UketsukeSeqString()
	{
		return Long.toString(this.a07_uketsuke_seq);
	}


	// a07_bumon_cdに対するセッターとゲッターの集合
	public boolean setA07BumonCd(String a07_bumon_cd)
	{
		this.a07_bumon_cd = a07_bumon_cd;
		return true;
	}
	public String getA07BumonCd()
	{
		return cutString(this.a07_bumon_cd,A07_BUMON_CD_MAX_LENGTH);
	}
	public String getA07BumonCdString()
	{
		return "'" + cutString(this.a07_bumon_cd,A07_BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getA07BumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_bumon_cd,A07_BUMON_CD_MAX_LENGTH));
	}


	// a07_unit_cdに対するセッターとゲッターの集合
	public boolean setA07UnitCd(String a07_unit_cd)
	{
		this.a07_unit_cd = a07_unit_cd;
		return true;
	}
	public String getA07UnitCd()
	{
		return cutString(this.a07_unit_cd,A07_UNIT_CD_MAX_LENGTH);
	}
	public String getA07UnitCdString()
	{
		return "'" + cutString(this.a07_unit_cd,A07_UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getA07UnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_unit_cd,A07_UNIT_CD_MAX_LENGTH));
	}


	// a07_haifu_cdに対するセッターとゲッターの集合
	public boolean setA07HaifuCd(String a07_haifu_cd)
	{
		this.a07_haifu_cd = a07_haifu_cd;
		return true;
	}
	public String getA07HaifuCd()
	{
		return cutString(this.a07_haifu_cd,A07_HAIFU_CD_MAX_LENGTH);
	}
	public String getA07HaifuCdString()
	{
		return "'" + cutString(this.a07_haifu_cd,A07_HAIFU_CD_MAX_LENGTH) + "'";
	}
	public String getA07HaifuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_haifu_cd,A07_HAIFU_CD_MAX_LENGTH));
	}


	// a07_subclass_cdに対するセッターとゲッターの集合
	public boolean setA07SubclassCd(String a07_subclass_cd)
	{
		this.a07_subclass_cd = a07_subclass_cd;
		return true;
	}
	public String getA07SubclassCd()
	{
		return cutString(this.a07_subclass_cd,A07_SUBCLASS_CD_MAX_LENGTH);
	}
	public String getA07SubclassCdString()
	{
		return "'" + cutString(this.a07_subclass_cd,A07_SUBCLASS_CD_MAX_LENGTH) + "'";
	}
	public String getA07SubclassCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_subclass_cd,A07_SUBCLASS_CD_MAX_LENGTH));
	}


	// a07_syohin_cdに対するセッターとゲッターの集合
	public boolean setA07SyohinCd(String a07_syohin_cd)
	{
		this.a07_syohin_cd = a07_syohin_cd;
		return true;
	}
	public String getA07SyohinCd()
	{
		return cutString(this.a07_syohin_cd,A07_SYOHIN_CD_MAX_LENGTH);
	}
	public String getA07SyohinCdString()
	{
		return "'" + cutString(this.a07_syohin_cd,A07_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getA07SyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_syohin_cd,A07_SYOHIN_CD_MAX_LENGTH));
	}


	// a07_yuko_dtに対するセッターとゲッターの集合
	public boolean setA07YukoDt(String a07_yuko_dt)
	{
		this.a07_yuko_dt = a07_yuko_dt;
		return true;
	}
	public String getA07YukoDt()
	{
		return cutString(this.a07_yuko_dt,A07_YUKO_DT_MAX_LENGTH);
	}
	public String getA07YukoDtString()
	{
		return "'" + cutString(this.a07_yuko_dt,A07_YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getA07YukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_yuko_dt,A07_YUKO_DT_MAX_LENGTH));
	}


	// a07_maker_cdに対するセッターとゲッターの集合
	public boolean setA07MakerCd(String a07_maker_cd)
	{
		this.a07_maker_cd = a07_maker_cd;
		return true;
	}
	public String getA07MakerCd()
	{
		return cutString(this.a07_maker_cd,A07_MAKER_CD_MAX_LENGTH);
	}
	public String getA07MakerCdString()
	{
		return "'" + cutString(this.a07_maker_cd,A07_MAKER_CD_MAX_LENGTH) + "'";
	}
	public String getA07MakerCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_maker_cd,A07_MAKER_CD_MAX_LENGTH));
	}


	// a07_pos_cdに対するセッターとゲッターの集合
	public boolean setA07PosCd(String a07_pos_cd)
	{
		this.a07_pos_cd = a07_pos_cd;
		return true;
	}
	public String getA07PosCd()
	{
		return cutString(this.a07_pos_cd,A07_POS_CD_MAX_LENGTH);
	}
	public String getA07PosCdString()
	{
		return "'" + cutString(this.a07_pos_cd,A07_POS_CD_MAX_LENGTH) + "'";
	}
	public String getA07PosCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_pos_cd,A07_POS_CD_MAX_LENGTH));
	}


	// a07_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setA07HinmeiKanjiNa(String a07_hinmei_kanji_na)
	{
		this.a07_hinmei_kanji_na = a07_hinmei_kanji_na;
		return true;
	}
	public String getA07HinmeiKanjiNa()
	{
		return cutString(this.a07_hinmei_kanji_na,A07_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getA07HinmeiKanjiNaString()
	{
		return "'" + cutString(this.a07_hinmei_kanji_na,A07_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getA07HinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_hinmei_kanji_na,A07_HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// a07_kikaku_kanji_naに対するセッターとゲッターの集合
	public boolean setA07KikakuKanjiNa(String a07_kikaku_kanji_na)
	{
		this.a07_kikaku_kanji_na = a07_kikaku_kanji_na;
		return true;
	}
	public String getA07KikakuKanjiNa()
	{
		return cutString(this.a07_kikaku_kanji_na,A07_KIKAKU_KANJI_NA_MAX_LENGTH);
	}
	public String getA07KikakuKanjiNaString()
	{
		return "'" + cutString(this.a07_kikaku_kanji_na,A07_KIKAKU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getA07KikakuKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_kikaku_kanji_na,A07_KIKAKU_KANJI_NA_MAX_LENGTH));
	}


	// a07_kikaku_kanji_2_naに対するセッターとゲッターの集合
	public boolean setA07KikakuKanji2Na(String a07_kikaku_kanji_2_na)
	{
		this.a07_kikaku_kanji_2_na = a07_kikaku_kanji_2_na;
		return true;
	}
	public String getA07KikakuKanji2Na()
	{
		return cutString(this.a07_kikaku_kanji_2_na,A07_KIKAKU_KANJI_2_NA_MAX_LENGTH);
	}
	public String getA07KikakuKanji2NaString()
	{
		return "'" + cutString(this.a07_kikaku_kanji_2_na,A07_KIKAKU_KANJI_2_NA_MAX_LENGTH) + "'";
	}
	public String getA07KikakuKanji2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_kikaku_kanji_2_na,A07_KIKAKU_KANJI_2_NA_MAX_LENGTH));
	}


	// a07_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setA07HinmeiKanaNa(String a07_hinmei_kana_na)
	{
		this.a07_hinmei_kana_na = a07_hinmei_kana_na;
		return true;
	}
	public String getA07HinmeiKanaNa()
	{
		return cutString(this.a07_hinmei_kana_na,A07_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getA07HinmeiKanaNaString()
	{
		return "'" + cutString(this.a07_hinmei_kana_na,A07_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getA07HinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_hinmei_kana_na,A07_HINMEI_KANA_NA_MAX_LENGTH));
	}


	// a07_kikaku_kana_naに対するセッターとゲッターの集合
	public boolean setA07KikakuKanaNa(String a07_kikaku_kana_na)
	{
		this.a07_kikaku_kana_na = a07_kikaku_kana_na;
		return true;
	}
	public String getA07KikakuKanaNa()
	{
		return cutString(this.a07_kikaku_kana_na,A07_KIKAKU_KANA_NA_MAX_LENGTH);
	}
	public String getA07KikakuKanaNaString()
	{
		return "'" + cutString(this.a07_kikaku_kana_na,A07_KIKAKU_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getA07KikakuKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_kikaku_kana_na,A07_KIKAKU_KANA_NA_MAX_LENGTH));
	}


	// a07_kikaku_kana_2_naに対するセッターとゲッターの集合
	public boolean setA07KikakuKana2Na(String a07_kikaku_kana_2_na)
	{
		this.a07_kikaku_kana_2_na = a07_kikaku_kana_2_na;
		return true;
	}
	public String getA07KikakuKana2Na()
	{
		return cutString(this.a07_kikaku_kana_2_na,A07_KIKAKU_KANA_2_NA_MAX_LENGTH);
	}
	public String getA07KikakuKana2NaString()
	{
		return "'" + cutString(this.a07_kikaku_kana_2_na,A07_KIKAKU_KANA_2_NA_MAX_LENGTH) + "'";
	}
	public String getA07KikakuKana2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_kikaku_kana_2_na,A07_KIKAKU_KANA_2_NA_MAX_LENGTH));
	}


	// a07_rec_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setA07RecHinmeiKanjiNa(String a07_rec_hinmei_kanji_na)
	{
		this.a07_rec_hinmei_kanji_na = a07_rec_hinmei_kanji_na;
		return true;
	}
	public String getA07RecHinmeiKanjiNa()
	{
		return cutString(this.a07_rec_hinmei_kanji_na,A07_REC_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getA07RecHinmeiKanjiNaString()
	{
		return "'" + cutString(this.a07_rec_hinmei_kanji_na,A07_REC_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getA07RecHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_rec_hinmei_kanji_na,A07_REC_HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// a07_rec_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setA07RecHinmeiKanaNa(String a07_rec_hinmei_kana_na)
	{
		this.a07_rec_hinmei_kana_na = a07_rec_hinmei_kana_na;
		return true;
	}
	public String getA07RecHinmeiKanaNa()
	{
		return cutString(this.a07_rec_hinmei_kana_na,A07_REC_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getA07RecHinmeiKanaNaString()
	{
		return "'" + cutString(this.a07_rec_hinmei_kana_na,A07_REC_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getA07RecHinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_rec_hinmei_kana_na,A07_REC_HINMEI_KANA_NA_MAX_LENGTH));
	}


	// a07_size_cdに対するセッターとゲッターの集合
	public boolean setA07SizeCd(String a07_size_cd)
	{
		this.a07_size_cd = a07_size_cd;
		return true;
	}
	public String getA07SizeCd()
	{
		return cutString(this.a07_size_cd,A07_SIZE_CD_MAX_LENGTH);
	}
	public String getA07SizeCdString()
	{
		return "'" + cutString(this.a07_size_cd,A07_SIZE_CD_MAX_LENGTH) + "'";
	}
	public String getA07SizeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_size_cd,A07_SIZE_CD_MAX_LENGTH));
	}


	// a07_color_cdに対するセッターとゲッターの集合
	public boolean setA07ColorCd(String a07_color_cd)
	{
		this.a07_color_cd = a07_color_cd;
		return true;
	}
	public String getA07ColorCd()
	{
		return cutString(this.a07_color_cd,A07_COLOR_CD_MAX_LENGTH);
	}
	public String getA07ColorCdString()
	{
		return "'" + cutString(this.a07_color_cd,A07_COLOR_CD_MAX_LENGTH) + "'";
	}
	public String getA07ColorCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_color_cd,A07_COLOR_CD_MAX_LENGTH));
	}


	// a07_gentanka_vlに対するセッターとゲッターの集合
	public boolean setA07GentankaVl(String a07_gentanka_vl)
	{
		this.a07_gentanka_vl = a07_gentanka_vl;
		return true;
	}
	public String getA07GentankaVl()
	{
		return cutString(this.a07_gentanka_vl,A07_GENTANKA_VL_MAX_LENGTH);
	}
	public String getA07GentankaVlString()
	{
		return "'" + cutString(this.a07_gentanka_vl,A07_GENTANKA_VL_MAX_LENGTH) + "'";
	}
	public String getA07GentankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_gentanka_vl,A07_GENTANKA_VL_MAX_LENGTH));
	}


	// a07_baitanka_vlに対するセッターとゲッターの集合
	public boolean setA07BaitankaVl(String a07_baitanka_vl)
	{
		this.a07_baitanka_vl = a07_baitanka_vl;
		return true;
	}
	public String getA07BaitankaVl()
	{
		return cutString(this.a07_baitanka_vl,A07_BAITANKA_VL_MAX_LENGTH);
	}
	public String getA07BaitankaVlString()
	{
		return "'" + cutString(this.a07_baitanka_vl,A07_BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getA07BaitankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_baitanka_vl,A07_BAITANKA_VL_MAX_LENGTH));
	}


	// a07_keshi_baika_vlに対するセッターとゲッターの集合
	public boolean setA07KeshiBaikaVl(String a07_keshi_baika_vl)
	{
		this.a07_keshi_baika_vl = a07_keshi_baika_vl;
		return true;
	}
	public String getA07KeshiBaikaVl()
	{
		return cutString(this.a07_keshi_baika_vl,A07_KESHI_BAIKA_VL_MAX_LENGTH);
	}
	public String getA07KeshiBaikaVlString()
	{
		return "'" + cutString(this.a07_keshi_baika_vl,A07_KESHI_BAIKA_VL_MAX_LENGTH) + "'";
	}
	public String getA07KeshiBaikaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_keshi_baika_vl,A07_KESHI_BAIKA_VL_MAX_LENGTH));
	}


	// a07_keiyakusu_qtに対するセッターとゲッターの集合
	public boolean setA07KeiyakusuQt(String a07_keiyakusu_qt)
	{
		this.a07_keiyakusu_qt = a07_keiyakusu_qt;
		return true;
	}
	public String getA07KeiyakusuQt()
	{
		return cutString(this.a07_keiyakusu_qt,A07_KEIYAKUSU_QT_MAX_LENGTH);
	}
	public String getA07KeiyakusuQtString()
	{
		return "'" + cutString(this.a07_keiyakusu_qt,A07_KEIYAKUSU_QT_MAX_LENGTH) + "'";
	}
	public String getA07KeiyakusuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_keiyakusu_qt,A07_KEIYAKUSU_QT_MAX_LENGTH));
	}


	// a07_tuika_keiyakusu_qtに対するセッターとゲッターの集合
	public boolean setA07TuikaKeiyakusuQt(String a07_tuika_keiyakusu_qt)
	{
		this.a07_tuika_keiyakusu_qt = a07_tuika_keiyakusu_qt;
		return true;
	}
	public String getA07TuikaKeiyakusuQt()
	{
		return cutString(this.a07_tuika_keiyakusu_qt,A07_TUIKA_KEIYAKUSU_QT_MAX_LENGTH);
	}
	public String getA07TuikaKeiyakusuQtString()
	{
		return "'" + cutString(this.a07_tuika_keiyakusu_qt,A07_TUIKA_KEIYAKUSU_QT_MAX_LENGTH) + "'";
	}
	public String getA07TuikaKeiyakusuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_tuika_keiyakusu_qt,A07_TUIKA_KEIYAKUSU_QT_MAX_LENGTH));
	}


	// a07_hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setA07HachutaniIrisuQt(String a07_hachutani_irisu_qt)
	{
		this.a07_hachutani_irisu_qt = a07_hachutani_irisu_qt;
		return true;
	}
	public String getA07HachutaniIrisuQt()
	{
		return cutString(this.a07_hachutani_irisu_qt,A07_HACHUTANI_IRISU_QT_MAX_LENGTH);
	}
	public String getA07HachutaniIrisuQtString()
	{
		return "'" + cutString(this.a07_hachutani_irisu_qt,A07_HACHUTANI_IRISU_QT_MAX_LENGTH) + "'";
	}
	public String getA07HachutaniIrisuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_hachutani_irisu_qt,A07_HACHUTANI_IRISU_QT_MAX_LENGTH));
	}


	// a07_hachu_tani_naに対するセッターとゲッターの集合
	public boolean setA07HachuTaniNa(String a07_hachu_tani_na)
	{
		this.a07_hachu_tani_na = a07_hachu_tani_na;
		return true;
	}
	public String getA07HachuTaniNa()
	{
		return cutString(this.a07_hachu_tani_na,A07_HACHU_TANI_NA_MAX_LENGTH);
	}
	public String getA07HachuTaniNaString()
	{
		return "'" + cutString(this.a07_hachu_tani_na,A07_HACHU_TANI_NA_MAX_LENGTH) + "'";
	}
	public String getA07HachuTaniNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_hachu_tani_na,A07_HACHU_TANI_NA_MAX_LENGTH));
	}


	// a07_plu_send_dtに対するセッターとゲッターの集合
	public boolean setA07PluSendDt(String a07_plu_send_dt)
	{
		this.a07_plu_send_dt = a07_plu_send_dt;
		return true;
	}
	public String getA07PluSendDt()
	{
		return cutString(this.a07_plu_send_dt,A07_PLU_SEND_DT_MAX_LENGTH);
	}
	public String getA07PluSendDtString()
	{
		return "'" + cutString(this.a07_plu_send_dt,A07_PLU_SEND_DT_MAX_LENGTH) + "'";
	}
	public String getA07PluSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_plu_send_dt,A07_PLU_SEND_DT_MAX_LENGTH));
	}


//	// a07_area_cdに対するセッターとゲッターの集合
//	public boolean setA07AreaCd(String a07_area_cd)
//	{
//		this.a07_area_cd = a07_area_cd;
//		return true;
//	}
//	public String getA07AreaCd()
//	{
//		return cutString(this.a07_area_cd,A07_AREA_CD_MAX_LENGTH);
//	}
//	public String getA07AreaCdString()
//	{
//		return "'" + cutString(this.a07_area_cd,A07_AREA_CD_MAX_LENGTH) + "'";
//	}
//	public String getA07AreaCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.a07_area_cd,A07_AREA_CD_MAX_LENGTH));
//	}


	// a07_siiresaki_cdに対するセッターとゲッターの集合
	public boolean setA07SiiresakiCd(String a07_siiresaki_cd)
	{
		this.a07_siiresaki_cd = a07_siiresaki_cd;
		return true;
	}
	public String getA07SiiresakiCd()
	{
		return cutString(this.a07_siiresaki_cd,A07_SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getA07SiiresakiCdString()
	{
		return "'" + cutString(this.a07_siiresaki_cd,A07_SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getA07SiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_siiresaki_cd,A07_SIIRESAKI_CD_MAX_LENGTH));
	}


	// a07_siire_hinban_cdに対するセッターとゲッターの集合
	public boolean setA07SiireHinbanCd(String a07_siire_hinban_cd)
	{
		this.a07_siire_hinban_cd = a07_siire_hinban_cd;
		return true;
	}
	public String getA07SiireHinbanCd()
	{
		return cutString(this.a07_siire_hinban_cd,A07_SIIRE_HINBAN_CD_MAX_LENGTH);
	}
	public String getA07SiireHinbanCdString()
	{
		return "'" + cutString(this.a07_siire_hinban_cd,A07_SIIRE_HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getA07SiireHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_siire_hinban_cd,A07_SIIRE_HINBAN_CD_MAX_LENGTH));
	}


	// a07_hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setA07HanbaiStDt(String a07_hanbai_st_dt)
	{
		this.a07_hanbai_st_dt = a07_hanbai_st_dt;
		return true;
	}
	public String getA07HanbaiStDt()
	{
		return cutString(this.a07_hanbai_st_dt,A07_HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getA07HanbaiStDtString()
	{
		return "'" + cutString(this.a07_hanbai_st_dt,A07_HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getA07HanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_hanbai_st_dt,A07_HANBAI_ST_DT_MAX_LENGTH));
	}


	// a07_hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setA07HanbaiEdDt(String a07_hanbai_ed_dt)
	{
		this.a07_hanbai_ed_dt = a07_hanbai_ed_dt;
		return true;
	}
	public String getA07HanbaiEdDt()
	{
		return cutString(this.a07_hanbai_ed_dt,A07_HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getA07HanbaiEdDtString()
	{
		return "'" + cutString(this.a07_hanbai_ed_dt,A07_HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getA07HanbaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_hanbai_ed_dt,A07_HANBAI_ED_DT_MAX_LENGTH));
	}


	// a07_ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setA07TenHachuStDt(String a07_ten_hachu_st_dt)
	{
		this.a07_ten_hachu_st_dt = a07_ten_hachu_st_dt;
		return true;
	}
	public String getA07TenHachuStDt()
	{
		return cutString(this.a07_ten_hachu_st_dt,A07_TEN_HACHU_ST_DT_MAX_LENGTH);
	}
	public String getA07TenHachuStDtString()
	{
		return "'" + cutString(this.a07_ten_hachu_st_dt,A07_TEN_HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getA07TenHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_ten_hachu_st_dt,A07_TEN_HACHU_ST_DT_MAX_LENGTH));
	}


	// a07_ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setA07TenHachuEdDt(String a07_ten_hachu_ed_dt)
	{
		this.a07_ten_hachu_ed_dt = a07_ten_hachu_ed_dt;
		return true;
	}
	public String getA07TenHachuEdDt()
	{
		return cutString(this.a07_ten_hachu_ed_dt,A07_TEN_HACHU_ED_DT_MAX_LENGTH);
	}
	public String getA07TenHachuEdDtString()
	{
		return "'" + cutString(this.a07_ten_hachu_ed_dt,A07_TEN_HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getA07TenHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_ten_hachu_ed_dt,A07_TEN_HACHU_ED_DT_MAX_LENGTH));
	}


	// a07_eos_kbに対するセッターとゲッターの集合
	public boolean setA07EosKb(String a07_eos_kb)
	{
		this.a07_eos_kb = a07_eos_kb;
		return true;
	}
	public String getA07EosKb()
	{
		return cutString(this.a07_eos_kb,A07_EOS_KB_MAX_LENGTH);
	}
	public String getA07EosKbString()
	{
		return "'" + cutString(this.a07_eos_kb,A07_EOS_KB_MAX_LENGTH) + "'";
	}
	public String getA07EosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_eos_kb,A07_EOS_KB_MAX_LENGTH));
	}


	// a07_season_cdに対するセッターとゲッターの集合
	public boolean setA07SeasonCd(String a07_season_cd)
	{
		this.a07_season_cd = a07_season_cd;
		return true;
	}
	public String getA07SeasonCd()
	{
		return cutString(this.a07_season_cd,A07_SEASON_CD_MAX_LENGTH);
	}
	public String getA07SeasonCdString()
	{
		return "'" + cutString(this.a07_season_cd,A07_SEASON_CD_MAX_LENGTH) + "'";
	}
	public String getA07SeasonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_season_cd,A07_SEASON_CD_MAX_LENGTH));
	}


	// a07_bland_cdに対するセッターとゲッターの集合
	public boolean setA07BlandCd(String a07_bland_cd)
	{
		this.a07_bland_cd = a07_bland_cd;
		return true;
	}
	public String getA07BlandCd()
	{
		return cutString(this.a07_bland_cd,A07_BLAND_CD_MAX_LENGTH);
	}
	public String getA07BlandCdString()
	{
		return "'" + cutString(this.a07_bland_cd,A07_BLAND_CD_MAX_LENGTH) + "'";
	}
	public String getA07BlandCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_bland_cd,A07_BLAND_CD_MAX_LENGTH));
	}


	// a07_pb_kbに対するセッターとゲッターの集合
	public boolean setA07PbKb(String a07_pb_kb)
	{
		this.a07_pb_kb = a07_pb_kb;
		return true;
	}
	public String getA07PbKb()
	{
		return cutString(this.a07_pb_kb,A07_PB_KB_MAX_LENGTH);
	}
	public String getA07PbKbString()
	{
		return "'" + cutString(this.a07_pb_kb,A07_PB_KB_MAX_LENGTH) + "'";
	}
	public String getA07PbKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_pb_kb,A07_PB_KB_MAX_LENGTH));
	}


	// a07_yoridori_vlに対するセッターとゲッターの集合
	public boolean setA07YoridoriVl(String a07_yoridori_vl)
	{
		this.a07_yoridori_vl = a07_yoridori_vl;
		return true;
	}
	public String getA07YoridoriVl()
	{
		return cutString(this.a07_yoridori_vl,A07_YORIDORI_VL_MAX_LENGTH);
	}
	public String getA07YoridoriVlString()
	{
		return "'" + cutString(this.a07_yoridori_vl,A07_YORIDORI_VL_MAX_LENGTH) + "'";
	}
	public String getA07YoridoriVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_yoridori_vl,A07_YORIDORI_VL_MAX_LENGTH));
	}


	// a07_yoridori_qtに対するセッターとゲッターの集合
	public boolean setA07YoridoriQt(String a07_yoridori_qt)
	{
		this.a07_yoridori_qt = a07_yoridori_qt;
		return true;
	}
	public String getA07YoridoriQt()
	{
		return cutString(this.a07_yoridori_qt,A07_YORIDORI_QT_MAX_LENGTH);
	}
	public String getA07YoridoriQtString()
	{
		return "'" + cutString(this.a07_yoridori_qt,A07_YORIDORI_QT_MAX_LENGTH) + "'";
	}
	public String getA07YoridoriQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_yoridori_qt,A07_YORIDORI_QT_MAX_LENGTH));
	}


	// a07_yoridori_kbに対するセッターとゲッターの集合
	public boolean setA07YoridoriKb(String a07_yoridori_kb)
	{
		this.a07_yoridori_kb = a07_yoridori_kb;
		return true;
	}
	public String getA07YoridoriKb()
	{
		return cutString(this.a07_yoridori_kb,A07_YORIDORI_KB_MAX_LENGTH);
	}
	public String getA07YoridoriKbString()
	{
		return "'" + cutString(this.a07_yoridori_kb,A07_YORIDORI_KB_MAX_LENGTH) + "'";
	}
	public String getA07YoridoriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_yoridori_kb,A07_YORIDORI_KB_MAX_LENGTH));
	}


	// a07_tana_no_nbに対するセッターとゲッターの集合
	public boolean setA07TanaNoNb(String a07_tana_no_nb)
	{
		this.a07_tana_no_nb = a07_tana_no_nb;
		return true;
	}
	public String getA07TanaNoNb()
	{
		return cutString(this.a07_tana_no_nb,A07_TANA_NO_NB_MAX_LENGTH);
	}
	public String getA07TanaNoNbString()
	{
		return "'" + cutString(this.a07_tana_no_nb,A07_TANA_NO_NB_MAX_LENGTH) + "'";
	}
	public String getA07TanaNoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_tana_no_nb,A07_TANA_NO_NB_MAX_LENGTH));
	}


	// a07_nefuda_kbに対するセッターとゲッターの集合
	public boolean setA07NefudaKb(String a07_nefuda_kb)
	{
		this.a07_nefuda_kb = a07_nefuda_kb;
		return true;
	}
	public String getA07NefudaKb()
	{
		return cutString(this.a07_nefuda_kb,A07_NEFUDA_KB_MAX_LENGTH);
	}
	public String getA07NefudaKbString()
	{
		return "'" + cutString(this.a07_nefuda_kb,A07_NEFUDA_KB_MAX_LENGTH) + "'";
	}
	public String getA07NefudaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_nefuda_kb,A07_NEFUDA_KB_MAX_LENGTH));
	}


	// a07_nefuda_ukewatasi_dtに対するセッターとゲッターの集合
	public boolean setA07NefudaUkewatasiDt(String a07_nefuda_ukewatasi_dt)
	{
		this.a07_nefuda_ukewatasi_dt = a07_nefuda_ukewatasi_dt;
		return true;
	}
	public String getA07NefudaUkewatasiDt()
	{
		return cutString(this.a07_nefuda_ukewatasi_dt,A07_NEFUDA_UKEWATASI_DT_MAX_LENGTH);
	}
	public String getA07NefudaUkewatasiDtString()
	{
		return "'" + cutString(this.a07_nefuda_ukewatasi_dt,A07_NEFUDA_UKEWATASI_DT_MAX_LENGTH) + "'";
	}
	public String getA07NefudaUkewatasiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_nefuda_ukewatasi_dt,A07_NEFUDA_UKEWATASI_DT_MAX_LENGTH));
	}


	// a07_nefuda_ukewatasi_kbに対するセッターとゲッターの集合
	public boolean setA07NefudaUkewatasiKb(String a07_nefuda_ukewatasi_kb)
	{
		this.a07_nefuda_ukewatasi_kb = a07_nefuda_ukewatasi_kb;
		return true;
	}
	public String getA07NefudaUkewatasiKb()
	{
		return cutString(this.a07_nefuda_ukewatasi_kb,A07_NEFUDA_UKEWATASI_KB_MAX_LENGTH);
	}
	public String getA07NefudaUkewatasiKbString()
	{
		return "'" + cutString(this.a07_nefuda_ukewatasi_kb,A07_NEFUDA_UKEWATASI_KB_MAX_LENGTH) + "'";
	}
	public String getA07NefudaUkewatasiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_nefuda_ukewatasi_kb,A07_NEFUDA_UKEWATASI_KB_MAX_LENGTH));
	}


	// a07_fuji_syohin_kbに対するセッターとゲッターの集合
	public boolean setA07FujiSyohinKb(String a07_fuji_syohin_kb)
	{
		this.a07_fuji_syohin_kb = a07_fuji_syohin_kb;
		return true;
	}
	public String getA07FujiSyohinKb()
	{
		return cutString(this.a07_fuji_syohin_kb,A07_FUJI_SYOHIN_KB_MAX_LENGTH);
	}
	public String getA07FujiSyohinKbString()
	{
		return "'" + cutString(this.a07_fuji_syohin_kb,A07_FUJI_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getA07FujiSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_fuji_syohin_kb,A07_FUJI_SYOHIN_KB_MAX_LENGTH));
	}


	// a07_pc_kbに対するセッターとゲッターの集合
	public boolean setA07PcKb(String a07_pc_kb)
	{
		this.a07_pc_kb = a07_pc_kb;
		return true;
	}
	public String getA07PcKb()
	{
		return cutString(this.a07_pc_kb,A07_PC_KB_MAX_LENGTH);
	}
	public String getA07PcKbString()
	{
		return "'" + cutString(this.a07_pc_kb,A07_PC_KB_MAX_LENGTH) + "'";
	}
	public String getA07PcKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_pc_kb,A07_PC_KB_MAX_LENGTH));
	}


	// a07_daisi_no_nbに対するセッターとゲッターの集合
	public boolean setA07DaisiNoNb(String a07_daisi_no_nb)
	{
		this.a07_daisi_no_nb = a07_daisi_no_nb;
		return true;
	}
	public String getA07DaisiNoNb()
	{
		return cutString(this.a07_daisi_no_nb,A07_DAISI_NO_NB_MAX_LENGTH);
	}
	public String getA07DaisiNoNbString()
	{
		return "'" + cutString(this.a07_daisi_no_nb,A07_DAISI_NO_NB_MAX_LENGTH) + "'";
	}
	public String getA07DaisiNoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_daisi_no_nb,A07_DAISI_NO_NB_MAX_LENGTH));
	}


	// a07_syusei_kbに対するセッターとゲッターの集合
	public boolean setA07SyuseiKb(String a07_syusei_kb)
	{
		this.a07_syusei_kb = a07_syusei_kb;
		return true;
	}
	public String getA07SyuseiKb()
	{
		return cutString(this.a07_syusei_kb,A07_SYUSEI_KB_MAX_LENGTH);
	}
	public String getA07SyuseiKbString()
	{
		return "'" + cutString(this.a07_syusei_kb,A07_SYUSEI_KB_MAX_LENGTH) + "'";
	}
	public String getA07SyuseiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_syusei_kb,A07_SYUSEI_KB_MAX_LENGTH));
	}


	// a07_sakusei_gyo_noに対するセッターとゲッターの集合
	public boolean setA07SakuseiGyoNo(String a07_sakusei_gyo_no)
	{
		try
		{
			this.a07_sakusei_gyo_no = Long.parseLong(a07_sakusei_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setA07SakuseiGyoNo(long a07_sakusei_gyo_no)
	{
		this.a07_sakusei_gyo_no = a07_sakusei_gyo_no;
		return true;
	}
	public long getA07SakuseiGyoNo()
	{
		return this.a07_sakusei_gyo_no;
	}
	public String getA07SakuseiGyoNoString()
	{
		return Long.toString(this.a07_sakusei_gyo_no);
	}


	// a07_mst_mainte_fgに対するセッターとゲッターの集合
	public boolean setA07MstMainteFg(String a07_mst_mainte_fg)
	{
		this.a07_mst_mainte_fg = a07_mst_mainte_fg;
		return true;
	}
	public String getA07MstMainteFg()
	{
		return cutString(this.a07_mst_mainte_fg,A07_MST_MAINTE_FG_MAX_LENGTH);
	}
	public String getA07MstMainteFgString()
	{
		return "'" + cutString(this.a07_mst_mainte_fg,A07_MST_MAINTE_FG_MAX_LENGTH) + "'";
	}
	public String getA07MstMainteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_mst_mainte_fg,A07_MST_MAINTE_FG_MAX_LENGTH));
	}


	// a07_alarm_make_fgに対するセッターとゲッターの集合
	public boolean setA07AlarmMakeFg(String a07_alarm_make_fg)
	{
		this.a07_alarm_make_fg = a07_alarm_make_fg;
		return true;
	}
	public String getA07AlarmMakeFg()
	{
		return cutString(this.a07_alarm_make_fg,A07_ALARM_MAKE_FG_MAX_LENGTH);
	}
	public String getA07AlarmMakeFgString()
	{
		return "'" + cutString(this.a07_alarm_make_fg,A07_ALARM_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getA07AlarmMakeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_alarm_make_fg,A07_ALARM_MAKE_FG_MAX_LENGTH));
	}


	// a07_insert_tsに対するセッターとゲッターの集合
	public boolean setA07InsertTs(String a07_insert_ts)
	{
		this.a07_insert_ts = a07_insert_ts;
		return true;
	}
	public String getA07InsertTs()
	{
		return cutString(this.a07_insert_ts,A07_INSERT_TS_MAX_LENGTH);
	}
	public String getA07InsertTsString()
	{
		return "'" + cutString(this.a07_insert_ts,A07_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getA07InsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_insert_ts,A07_INSERT_TS_MAX_LENGTH));
	}


	// a07_insert_user_idに対するセッターとゲッターの集合
	public boolean setA07InsertUserId(String a07_insert_user_id)
	{
		this.a07_insert_user_id = a07_insert_user_id;
		return true;
	}
	public String getA07InsertUserId()
	{
		return cutString(this.a07_insert_user_id,A07_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getA07InsertUserIdString()
	{
		return "'" + cutString(this.a07_insert_user_id,A07_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getA07InsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_insert_user_id,A07_INSERT_USER_ID_MAX_LENGTH));
	}


	// a07_update_tsに対するセッターとゲッターの集合
	public boolean setA07UpdateTs(String a07_update_ts)
	{
		this.a07_update_ts = a07_update_ts;
		return true;
	}
	public String getA07UpdateTs()
	{
		return cutString(this.a07_update_ts,A07_UPDATE_TS_MAX_LENGTH);
	}
	public String getA07UpdateTsString()
	{
		return "'" + cutString(this.a07_update_ts,A07_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getA07UpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_update_ts,A07_UPDATE_TS_MAX_LENGTH));
	}


	// a07_update_user_idに対するセッターとゲッターの集合
	public boolean setA07UpdateUserId(String a07_update_user_id)
	{
		this.a07_update_user_id = a07_update_user_id;
		return true;
	}
	public String getA07UpdateUserId()
	{
		return cutString(this.a07_update_user_id,A07_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getA07UpdateUserIdString()
	{
		return "'" + cutString(this.a07_update_user_id,A07_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getA07UpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a07_update_user_id,A07_UPDATE_USER_ID_MAX_LENGTH));
	}


	// a08_torikomi_dtに対するセッターとゲッターの集合
	public boolean setA08TorikomiDt(String a08_torikomi_dt)
	{
		this.a08_torikomi_dt = a08_torikomi_dt;
		return true;
	}
	public String getA08TorikomiDt()
	{
		return cutString(this.a08_torikomi_dt,A08_TORIKOMI_DT_MAX_LENGTH);
	}
	public String getA08TorikomiDtString()
	{
		return "'" + cutString(this.a08_torikomi_dt,A08_TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getA08TorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_torikomi_dt,A08_TORIKOMI_DT_MAX_LENGTH));
	}


	// a08_excel_file_syubetsuに対するセッターとゲッターの集合
	public boolean setA08ExcelFileSyubetsu(String a08_excel_file_syubetsu)
	{
		this.a08_excel_file_syubetsu = a08_excel_file_syubetsu;
		return true;
	}
	public String getA08ExcelFileSyubetsu()
	{
		return cutString(this.a08_excel_file_syubetsu,A08_EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getA08ExcelFileSyubetsuString()
	{
		return "'" + cutString(this.a08_excel_file_syubetsu,A08_EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getA08ExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_excel_file_syubetsu,A08_EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}


	// a08_uketsuke_noに対するセッターとゲッターの集合
	public boolean setA08UketsukeNo(String a08_uketsuke_no)
	{
		try
		{
			this.a08_uketsuke_no = Long.parseLong(a08_uketsuke_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setA08UketsukeNo(long a08_uketsuke_no)
	{
		this.a08_uketsuke_no = a08_uketsuke_no;
		return true;
	}
	public long getA08UketsukeNo()
	{
		return this.a08_uketsuke_no;
	}
	public String getA08UketsukeNoString()
	{
		return Long.toString(this.a08_uketsuke_no);
	}


	// a08_uketsuke_seqに対するセッターとゲッターの集合
	public boolean setA08UketsukeSeq(String a08_uketsuke_seq)
	{
		try
		{
			this.a08_uketsuke_seq = Long.parseLong(a08_uketsuke_seq);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setA08UketsukeSeq(long a08_uketsuke_seq)
	{
		this.a08_uketsuke_seq = a08_uketsuke_seq;
		return true;
	}
	public long getA08UketsukeSeq()
	{
		return this.a08_uketsuke_seq;
	}
	public String getA08UketsukeSeqString()
	{
		return Long.toString(this.a08_uketsuke_seq);
	}


	// a08_bumon_cdに対するセッターとゲッターの集合
	public boolean setA08BumonCd(String a08_bumon_cd)
	{
		this.a08_bumon_cd = a08_bumon_cd;
		return true;
	}
	public String getA08BumonCd()
	{
		return cutString(this.a08_bumon_cd,A08_BUMON_CD_MAX_LENGTH);
	}
	public String getA08BumonCdString()
	{
		return "'" + cutString(this.a08_bumon_cd,A08_BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getA08BumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_bumon_cd,A08_BUMON_CD_MAX_LENGTH));
	}


	// a08_unit_cdに対するセッターとゲッターの集合
	public boolean setA08UnitCd(String a08_unit_cd)
	{
		this.a08_unit_cd = a08_unit_cd;
		return true;
	}
	public String getA08UnitCd()
	{
		return cutString(this.a08_unit_cd,A08_UNIT_CD_MAX_LENGTH);
	}
	public String getA08UnitCdString()
	{
		return "'" + cutString(this.a08_unit_cd,A08_UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getA08UnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_unit_cd,A08_UNIT_CD_MAX_LENGTH));
	}


	// a08_haifu_cdに対するセッターとゲッターの集合
	public boolean setA08HaifuCd(String a08_haifu_cd)
	{
		this.a08_haifu_cd = a08_haifu_cd;
		return true;
	}
	public String getA08HaifuCd()
	{
		return cutString(this.a08_haifu_cd,A08_HAIFU_CD_MAX_LENGTH);
	}
	public String getA08HaifuCdString()
	{
		return "'" + cutString(this.a08_haifu_cd,A08_HAIFU_CD_MAX_LENGTH) + "'";
	}
	public String getA08HaifuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_haifu_cd,A08_HAIFU_CD_MAX_LENGTH));
	}


	// a08_subclass_cdに対するセッターとゲッターの集合
	public boolean setA08SubclassCd(String a08_subclass_cd)
	{
		this.a08_subclass_cd = a08_subclass_cd;
		return true;
	}
	public String getA08SubclassCd()
	{
		return cutString(this.a08_subclass_cd,A08_SUBCLASS_CD_MAX_LENGTH);
	}
	public String getA08SubclassCdString()
	{
		return "'" + cutString(this.a08_subclass_cd,A08_SUBCLASS_CD_MAX_LENGTH) + "'";
	}
	public String getA08SubclassCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_subclass_cd,A08_SUBCLASS_CD_MAX_LENGTH));
	}


	// a08_syohin_cdに対するセッターとゲッターの集合
	public boolean setA08SyohinCd(String a08_syohin_cd)
	{
		this.a08_syohin_cd = a08_syohin_cd;
		return true;
	}
	public String getA08SyohinCd()
	{
		return cutString(this.a08_syohin_cd,A08_SYOHIN_CD_MAX_LENGTH);
	}
	public String getA08SyohinCdString()
	{
		return "'" + cutString(this.a08_syohin_cd,A08_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getA08SyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_syohin_cd,A08_SYOHIN_CD_MAX_LENGTH));
	}


	// a08_yuko_dtに対するセッターとゲッターの集合
	public boolean setA08YukoDt(String a08_yuko_dt)
	{
		this.a08_yuko_dt = a08_yuko_dt;
		return true;
	}
	public String getA08YukoDt()
	{
		return cutString(this.a08_yuko_dt,A08_YUKO_DT_MAX_LENGTH);
	}
	public String getA08YukoDtString()
	{
		return "'" + cutString(this.a08_yuko_dt,A08_YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getA08YukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_yuko_dt,A08_YUKO_DT_MAX_LENGTH));
	}


	// a08_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setA08HinmeiKanjiNa(String a08_hinmei_kanji_na)
	{
		this.a08_hinmei_kanji_na = a08_hinmei_kanji_na;
		return true;
	}
	public String getA08HinmeiKanjiNa()
	{
		return cutString(this.a08_hinmei_kanji_na,A08_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getA08HinmeiKanjiNaString()
	{
		return "'" + cutString(this.a08_hinmei_kanji_na,A08_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getA08HinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_hinmei_kanji_na,A08_HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// a08_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setA08HinmeiKanaNa(String a08_hinmei_kana_na)
	{
		this.a08_hinmei_kana_na = a08_hinmei_kana_na;
		return true;
	}
	public String getA08HinmeiKanaNa()
	{
		return cutString(this.a08_hinmei_kana_na,A08_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getA08HinmeiKanaNaString()
	{
		return "'" + cutString(this.a08_hinmei_kana_na,A08_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getA08HinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_hinmei_kana_na,A08_HINMEI_KANA_NA_MAX_LENGTH));
	}


	// a08_size_cdに対するセッターとゲッターの集合
	public boolean setA08SizeCd(String a08_size_cd)
	{
		this.a08_size_cd = a08_size_cd;
		return true;
	}
	public String getA08SizeCd()
	{
		return cutString(this.a08_size_cd,A08_SIZE_CD_MAX_LENGTH);
	}
	public String getA08SizeCdString()
	{
		return "'" + cutString(this.a08_size_cd,A08_SIZE_CD_MAX_LENGTH) + "'";
	}
	public String getA08SizeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_size_cd,A08_SIZE_CD_MAX_LENGTH));
	}


	// a08_color_cdに対するセッターとゲッターの集合
	public boolean setA08ColorCd(String a08_color_cd)
	{
		this.a08_color_cd = a08_color_cd;
		return true;
	}
	public String getA08ColorCd()
	{
		return cutString(this.a08_color_cd,A08_COLOR_CD_MAX_LENGTH);
	}
	public String getA08ColorCdString()
	{
		return "'" + cutString(this.a08_color_cd,A08_COLOR_CD_MAX_LENGTH) + "'";
	}
	public String getA08ColorCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_color_cd,A08_COLOR_CD_MAX_LENGTH));
	}


	// a08_gentanka_vlに対するセッターとゲッターの集合
	public boolean setA08GentankaVl(String a08_gentanka_vl)
	{
		this.a08_gentanka_vl = a08_gentanka_vl;
		return true;
	}
	public String getA08GentankaVl()
	{
		return cutString(this.a08_gentanka_vl,A08_GENTANKA_VL_MAX_LENGTH);
	}
	public String getA08GentankaVlString()
	{
		return "'" + cutString(this.a08_gentanka_vl,A08_GENTANKA_VL_MAX_LENGTH) + "'";
	}
	public String getA08GentankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_gentanka_vl,A08_GENTANKA_VL_MAX_LENGTH));
	}


	// a08_baitanka_vlに対するセッターとゲッターの集合
	public boolean setA08BaitankaVl(String a08_baitanka_vl)
	{
		this.a08_baitanka_vl = a08_baitanka_vl;
		return true;
	}
	public String getA08BaitankaVl()
	{
		return cutString(this.a08_baitanka_vl,A08_BAITANKA_VL_MAX_LENGTH);
	}
	public String getA08BaitankaVlString()
	{
		return "'" + cutString(this.a08_baitanka_vl,A08_BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getA08BaitankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_baitanka_vl,A08_BAITANKA_VL_MAX_LENGTH));
	}


	// a08_keshi_baika_vlに対するセッターとゲッターの集合
	public boolean setA08KeshiBaikaVl(String a08_keshi_baika_vl)
	{
		this.a08_keshi_baika_vl = a08_keshi_baika_vl;
		return true;
	}
	public String getA08KeshiBaikaVl()
	{
		return cutString(this.a08_keshi_baika_vl,A08_KESHI_BAIKA_VL_MAX_LENGTH);
	}
	public String getA08KeshiBaikaVlString()
	{
		return "'" + cutString(this.a08_keshi_baika_vl,A08_KESHI_BAIKA_VL_MAX_LENGTH) + "'";
	}
	public String getA08KeshiBaikaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_keshi_baika_vl,A08_KESHI_BAIKA_VL_MAX_LENGTH));
	}


	// a08_tokubetu_genka_vlに対するセッターとゲッターの集合
	public boolean setA08TokubetuGenkaVl(String a08_tokubetu_genka_vl)
	{
		this.a08_tokubetu_genka_vl = a08_tokubetu_genka_vl;
		return true;
	}
	public String getA08TokubetuGenkaVl()
	{
		return cutString(this.a08_tokubetu_genka_vl,A08_TOKUBETU_GENKA_VL_MAX_LENGTH);
	}
	public String getA08TokubetuGenkaVlString()
	{
		return "'" + cutString(this.a08_tokubetu_genka_vl,A08_TOKUBETU_GENKA_VL_MAX_LENGTH) + "'";
	}
	public String getA08TokubetuGenkaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_tokubetu_genka_vl,A08_TOKUBETU_GENKA_VL_MAX_LENGTH));
	}


	// a08_keiyakusu_qtに対するセッターとゲッターの集合
	public boolean setA08KeiyakusuQt(String a08_keiyakusu_qt)
	{
		this.a08_keiyakusu_qt = a08_keiyakusu_qt;
		return true;
	}
	public String getA08KeiyakusuQt()
	{
		return cutString(this.a08_keiyakusu_qt,A08_KEIYAKUSU_QT_MAX_LENGTH);
	}
	public String getA08KeiyakusuQtString()
	{
		return "'" + cutString(this.a08_keiyakusu_qt,A08_KEIYAKUSU_QT_MAX_LENGTH) + "'";
	}
	public String getA08KeiyakusuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_keiyakusu_qt,A08_KEIYAKUSU_QT_MAX_LENGTH));
	}


	// a08_tuika_keiyakusu_qtに対するセッターとゲッターの集合
	public boolean setA08TuikaKeiyakusuQt(String a08_tuika_keiyakusu_qt)
	{
		this.a08_tuika_keiyakusu_qt = a08_tuika_keiyakusu_qt;
		return true;
	}
	public String getA08TuikaKeiyakusuQt()
	{
		return cutString(this.a08_tuika_keiyakusu_qt,A08_TUIKA_KEIYAKUSU_QT_MAX_LENGTH);
	}
	public String getA08TuikaKeiyakusuQtString()
	{
		return "'" + cutString(this.a08_tuika_keiyakusu_qt,A08_TUIKA_KEIYAKUSU_QT_MAX_LENGTH) + "'";
	}
	public String getA08TuikaKeiyakusuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_tuika_keiyakusu_qt,A08_TUIKA_KEIYAKUSU_QT_MAX_LENGTH));
	}


//	// a08_area_cdに対するセッターとゲッターの集合
//	public boolean setA08AreaCd(String a08_area_cd)
//	{
//		this.a08_area_cd = a08_area_cd;
//		return true;
//	}
//	public String getA08AreaCd()
//	{
//		return cutString(this.a08_area_cd,A08_AREA_CD_MAX_LENGTH);
//	}
//	public String getA08AreaCdString()
//	{
//		return "'" + cutString(this.a08_area_cd,A08_AREA_CD_MAX_LENGTH) + "'";
//	}
//	public String getA08AreaCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.a08_area_cd,A08_AREA_CD_MAX_LENGTH));
//	}


	// a08_siiresaki_cdに対するセッターとゲッターの集合
	public boolean setA08SiiresakiCd(String a08_siiresaki_cd)
	{
		this.a08_siiresaki_cd = a08_siiresaki_cd;
		return true;
	}
	public String getA08SiiresakiCd()
	{
		return cutString(this.a08_siiresaki_cd,A08_SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getA08SiiresakiCdString()
	{
		return "'" + cutString(this.a08_siiresaki_cd,A08_SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getA08SiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_siiresaki_cd,A08_SIIRESAKI_CD_MAX_LENGTH));
	}


	// a08_siire_hinban_cdに対するセッターとゲッターの集合
	public boolean setA08SiireHinbanCd(String a08_siire_hinban_cd)
	{
		this.a08_siire_hinban_cd = a08_siire_hinban_cd;
		return true;
	}
	public String getA08SiireHinbanCd()
	{
		return cutString(this.a08_siire_hinban_cd,A08_SIIRE_HINBAN_CD_MAX_LENGTH);
	}
	public String getA08SiireHinbanCdString()
	{
		return "'" + cutString(this.a08_siire_hinban_cd,A08_SIIRE_HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getA08SiireHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_siire_hinban_cd,A08_SIIRE_HINBAN_CD_MAX_LENGTH));
	}


	// a08_hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setA08HanbaiStDt(String a08_hanbai_st_dt)
	{
		this.a08_hanbai_st_dt = a08_hanbai_st_dt;
		return true;
	}
	public String getA08HanbaiStDt()
	{
		return cutString(this.a08_hanbai_st_dt,A08_HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getA08HanbaiStDtString()
	{
		return "'" + cutString(this.a08_hanbai_st_dt,A08_HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getA08HanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_hanbai_st_dt,A08_HANBAI_ST_DT_MAX_LENGTH));
	}


	// a08_hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setA08HanbaiEdDt(String a08_hanbai_ed_dt)
	{
		this.a08_hanbai_ed_dt = a08_hanbai_ed_dt;
		return true;
	}
	public String getA08HanbaiEdDt()
	{
		return cutString(this.a08_hanbai_ed_dt,A08_HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getA08HanbaiEdDtString()
	{
		return "'" + cutString(this.a08_hanbai_ed_dt,A08_HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getA08HanbaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_hanbai_ed_dt,A08_HANBAI_ED_DT_MAX_LENGTH));
	}


	// a08_ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setA08TenHachuStDt(String a08_ten_hachu_st_dt)
	{
		this.a08_ten_hachu_st_dt = a08_ten_hachu_st_dt;
		return true;
	}
	public String getA08TenHachuStDt()
	{
		return cutString(this.a08_ten_hachu_st_dt,A08_TEN_HACHU_ST_DT_MAX_LENGTH);
	}
	public String getA08TenHachuStDtString()
	{
		return "'" + cutString(this.a08_ten_hachu_st_dt,A08_TEN_HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getA08TenHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_ten_hachu_st_dt,A08_TEN_HACHU_ST_DT_MAX_LENGTH));
	}


	// a08_ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setA08TenHachuEdDt(String a08_ten_hachu_ed_dt)
	{
		this.a08_ten_hachu_ed_dt = a08_ten_hachu_ed_dt;
		return true;
	}
	public String getA08TenHachuEdDt()
	{
		return cutString(this.a08_ten_hachu_ed_dt,A08_TEN_HACHU_ED_DT_MAX_LENGTH);
	}
	public String getA08TenHachuEdDtString()
	{
		return "'" + cutString(this.a08_ten_hachu_ed_dt,A08_TEN_HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getA08TenHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_ten_hachu_ed_dt,A08_TEN_HACHU_ED_DT_MAX_LENGTH));
	}


	// a08_eos_kbに対するセッターとゲッターの集合
	public boolean setA08EosKb(String a08_eos_kb)
	{
		this.a08_eos_kb = a08_eos_kb;
		return true;
	}
	public String getA08EosKb()
	{
		return cutString(this.a08_eos_kb,A08_EOS_KB_MAX_LENGTH);
	}
	public String getA08EosKbString()
	{
		return "'" + cutString(this.a08_eos_kb,A08_EOS_KB_MAX_LENGTH) + "'";
	}
	public String getA08EosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_eos_kb,A08_EOS_KB_MAX_LENGTH));
	}


	// a08_season_cdに対するセッターとゲッターの集合
	public boolean setA08SeasonCd(String a08_season_cd)
	{
		this.a08_season_cd = a08_season_cd;
		return true;
	}
	public String getA08SeasonCd()
	{
		return cutString(this.a08_season_cd,A08_SEASON_CD_MAX_LENGTH);
	}
	public String getA08SeasonCdString()
	{
		return "'" + cutString(this.a08_season_cd,A08_SEASON_CD_MAX_LENGTH) + "'";
	}
	public String getA08SeasonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_season_cd,A08_SEASON_CD_MAX_LENGTH));
	}


	// a08_bland_cdに対するセッターとゲッターの集合
	public boolean setA08BlandCd(String a08_bland_cd)
	{
		this.a08_bland_cd = a08_bland_cd;
		return true;
	}
	public String getA08BlandCd()
	{
		return cutString(this.a08_bland_cd,A08_BLAND_CD_MAX_LENGTH);
	}
	public String getA08BlandCdString()
	{
		return "'" + cutString(this.a08_bland_cd,A08_BLAND_CD_MAX_LENGTH) + "'";
	}
	public String getA08BlandCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_bland_cd,A08_BLAND_CD_MAX_LENGTH));
	}


	// a08_pb_kbに対するセッターとゲッターの集合
	public boolean setA08PbKb(String a08_pb_kb)
	{
		this.a08_pb_kb = a08_pb_kb;
		return true;
	}
	public String getA08PbKb()
	{
		return cutString(this.a08_pb_kb,A08_PB_KB_MAX_LENGTH);
	}
	public String getA08PbKbString()
	{
		return "'" + cutString(this.a08_pb_kb,A08_PB_KB_MAX_LENGTH) + "'";
	}
	public String getA08PbKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_pb_kb,A08_PB_KB_MAX_LENGTH));
	}


	// a08_yoridori_vlに対するセッターとゲッターの集合
	public boolean setA08YoridoriVl(String a08_yoridori_vl)
	{
		this.a08_yoridori_vl = a08_yoridori_vl;
		return true;
	}
	public String getA08YoridoriVl()
	{
		return cutString(this.a08_yoridori_vl,A08_YORIDORI_VL_MAX_LENGTH);
	}
	public String getA08YoridoriVlString()
	{
		return "'" + cutString(this.a08_yoridori_vl,A08_YORIDORI_VL_MAX_LENGTH) + "'";
	}
	public String getA08YoridoriVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_yoridori_vl,A08_YORIDORI_VL_MAX_LENGTH));
	}


	// a08_yoridori_qtに対するセッターとゲッターの集合
	public boolean setA08YoridoriQt(String a08_yoridori_qt)
	{
		this.a08_yoridori_qt = a08_yoridori_qt;
		return true;
	}
	public String getA08YoridoriQt()
	{
		return cutString(this.a08_yoridori_qt,A08_YORIDORI_QT_MAX_LENGTH);
	}
	public String getA08YoridoriQtString()
	{
		return "'" + cutString(this.a08_yoridori_qt,A08_YORIDORI_QT_MAX_LENGTH) + "'";
	}
	public String getA08YoridoriQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_yoridori_qt,A08_YORIDORI_QT_MAX_LENGTH));
	}


	// a08_yoridori_kbに対するセッターとゲッターの集合
	public boolean setA08YoridoriKb(String a08_yoridori_kb)
	{
		this.a08_yoridori_kb = a08_yoridori_kb;
		return true;
	}
	public String getA08YoridoriKb()
	{
		return cutString(this.a08_yoridori_kb,A08_YORIDORI_KB_MAX_LENGTH);
	}
	public String getA08YoridoriKbString()
	{
		return "'" + cutString(this.a08_yoridori_kb,A08_YORIDORI_KB_MAX_LENGTH) + "'";
	}
	public String getA08YoridoriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_yoridori_kb,A08_YORIDORI_KB_MAX_LENGTH));
	}


	// a08_nefuda_kbに対するセッターとゲッターの集合
	public boolean setA08NefudaKb(String a08_nefuda_kb)
	{
		this.a08_nefuda_kb = a08_nefuda_kb;
		return true;
	}
	public String getA08NefudaKb()
	{
		return cutString(this.a08_nefuda_kb,A08_NEFUDA_KB_MAX_LENGTH);
	}
	public String getA08NefudaKbString()
	{
		return "'" + cutString(this.a08_nefuda_kb,A08_NEFUDA_KB_MAX_LENGTH) + "'";
	}
	public String getA08NefudaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_nefuda_kb,A08_NEFUDA_KB_MAX_LENGTH));
	}


	// a08_nefuda_ukewatasi_dtに対するセッターとゲッターの集合
	public boolean setA08NefudaUkewatasiDt(String a08_nefuda_ukewatasi_dt)
	{
		this.a08_nefuda_ukewatasi_dt = a08_nefuda_ukewatasi_dt;
		return true;
	}
	public String getA08NefudaUkewatasiDt()
	{
		return cutString(this.a08_nefuda_ukewatasi_dt,A08_NEFUDA_UKEWATASI_DT_MAX_LENGTH);
	}
	public String getA08NefudaUkewatasiDtString()
	{
		return "'" + cutString(this.a08_nefuda_ukewatasi_dt,A08_NEFUDA_UKEWATASI_DT_MAX_LENGTH) + "'";
	}
	public String getA08NefudaUkewatasiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_nefuda_ukewatasi_dt,A08_NEFUDA_UKEWATASI_DT_MAX_LENGTH));
	}


	// a08_nefuda_ukewatasi_kbに対するセッターとゲッターの集合
	public boolean setA08NefudaUkewatasiKb(String a08_nefuda_ukewatasi_kb)
	{
		this.a08_nefuda_ukewatasi_kb = a08_nefuda_ukewatasi_kb;
		return true;
	}
	public String getA08NefudaUkewatasiKb()
	{
		return cutString(this.a08_nefuda_ukewatasi_kb,A08_NEFUDA_UKEWATASI_KB_MAX_LENGTH);
	}
	public String getA08NefudaUkewatasiKbString()
	{
		return "'" + cutString(this.a08_nefuda_ukewatasi_kb,A08_NEFUDA_UKEWATASI_KB_MAX_LENGTH) + "'";
	}
	public String getA08NefudaUkewatasiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_nefuda_ukewatasi_kb,A08_NEFUDA_UKEWATASI_KB_MAX_LENGTH));
	}


	// a08_fuji_syohin_kbに対するセッターとゲッターの集合
	public boolean setA08FujiSyohinKb(String a08_fuji_syohin_kb)
	{
		this.a08_fuji_syohin_kb = a08_fuji_syohin_kb;
		return true;
	}
	public String getA08FujiSyohinKb()
	{
		return cutString(this.a08_fuji_syohin_kb,A08_FUJI_SYOHIN_KB_MAX_LENGTH);
	}
	public String getA08FujiSyohinKbString()
	{
		return "'" + cutString(this.a08_fuji_syohin_kb,A08_FUJI_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getA08FujiSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_fuji_syohin_kb,A08_FUJI_SYOHIN_KB_MAX_LENGTH));
	}


	// a08_sozai_cdに対するセッターとゲッターの集合
	public boolean setA08SozaiCd(String a08_sozai_cd)
	{
		this.a08_sozai_cd = a08_sozai_cd;
		return true;
	}
	public String getA08SozaiCd()
	{
		return cutString(this.a08_sozai_cd,A08_SOZAI_CD_MAX_LENGTH);
	}
	public String getA08SozaiCdString()
	{
		return "'" + cutString(this.a08_sozai_cd,A08_SOZAI_CD_MAX_LENGTH) + "'";
	}
	public String getA08SozaiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_sozai_cd,A08_SOZAI_CD_MAX_LENGTH));
	}


	// a08_oriami_cdに対するセッターとゲッターの集合
	public boolean setA08OriamiCd(String a08_oriami_cd)
	{
		this.a08_oriami_cd = a08_oriami_cd;
		return true;
	}
	public String getA08OriamiCd()
	{
		return cutString(this.a08_oriami_cd,A08_ORIAMI_CD_MAX_LENGTH);
	}
	public String getA08OriamiCdString()
	{
		return "'" + cutString(this.a08_oriami_cd,A08_ORIAMI_CD_MAX_LENGTH) + "'";
	}
	public String getA08OriamiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_oriami_cd,A08_ORIAMI_CD_MAX_LENGTH));
	}


	// a08_sode_cdに対するセッターとゲッターの集合
	public boolean setA08SodeCd(String a08_sode_cd)
	{
		this.a08_sode_cd = a08_sode_cd;
		return true;
	}
	public String getA08SodeCd()
	{
		return cutString(this.a08_sode_cd,A08_SODE_CD_MAX_LENGTH);
	}
	public String getA08SodeCdString()
	{
		return "'" + cutString(this.a08_sode_cd,A08_SODE_CD_MAX_LENGTH) + "'";
	}
	public String getA08SodeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_sode_cd,A08_SODE_CD_MAX_LENGTH));
	}


	// a08_age_cdに対するセッターとゲッターの集合
	public boolean setA08AgeCd(String a08_age_cd)
	{
		this.a08_age_cd = a08_age_cd;
		return true;
	}
	public String getA08AgeCd()
	{
		return cutString(this.a08_age_cd,A08_AGE_CD_MAX_LENGTH);
	}
	public String getA08AgeCdString()
	{
		return "'" + cutString(this.a08_age_cd,A08_AGE_CD_MAX_LENGTH) + "'";
	}
	public String getA08AgeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_age_cd,A08_AGE_CD_MAX_LENGTH));
	}


	// a08_pattern_cdに対するセッターとゲッターの集合
	public boolean setA08PatternCd(String a08_pattern_cd)
	{
		this.a08_pattern_cd = a08_pattern_cd;
		return true;
	}
	public String getA08PatternCd()
	{
		return cutString(this.a08_pattern_cd,A08_PATTERN_CD_MAX_LENGTH);
	}
	public String getA08PatternCdString()
	{
		return "'" + cutString(this.a08_pattern_cd,A08_PATTERN_CD_MAX_LENGTH) + "'";
	}
	public String getA08PatternCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_pattern_cd,A08_PATTERN_CD_MAX_LENGTH));
	}


	// a08_syusei_kbに対するセッターとゲッターの集合
	public boolean setA08SyuseiKb(String a08_syusei_kb)
	{
		this.a08_syusei_kb = a08_syusei_kb;
		return true;
	}
	public String getA08SyuseiKb()
	{
		return cutString(this.a08_syusei_kb,A08_SYUSEI_KB_MAX_LENGTH);
	}
	public String getA08SyuseiKbString()
	{
		return "'" + cutString(this.a08_syusei_kb,A08_SYUSEI_KB_MAX_LENGTH) + "'";
	}
	public String getA08SyuseiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_syusei_kb,A08_SYUSEI_KB_MAX_LENGTH));
	}


	// a08_sakusei_gyo_noに対するセッターとゲッターの集合
	public boolean setA08SakuseiGyoNo(String a08_sakusei_gyo_no)
	{
		try
		{
			this.a08_sakusei_gyo_no = Long.parseLong(a08_sakusei_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setA08SakuseiGyoNo(long a08_sakusei_gyo_no)
	{
		this.a08_sakusei_gyo_no = a08_sakusei_gyo_no;
		return true;
	}
	public long getA08SakuseiGyoNo()
	{
		return this.a08_sakusei_gyo_no;
	}
	public String getA08SakuseiGyoNoString()
	{
		return Long.toString(this.a08_sakusei_gyo_no);
	}


	// a08_mst_mainte_fgに対するセッターとゲッターの集合
	public boolean setA08MstMainteFg(String a08_mst_mainte_fg)
	{
		this.a08_mst_mainte_fg = a08_mst_mainte_fg;
		return true;
	}
	public String getA08MstMainteFg()
	{
		return cutString(this.a08_mst_mainte_fg,A08_MST_MAINTE_FG_MAX_LENGTH);
	}
	public String getA08MstMainteFgString()
	{
		return "'" + cutString(this.a08_mst_mainte_fg,A08_MST_MAINTE_FG_MAX_LENGTH) + "'";
	}
	public String getA08MstMainteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_mst_mainte_fg,A08_MST_MAINTE_FG_MAX_LENGTH));
	}


	// a08_alarm_make_fgに対するセッターとゲッターの集合
	public boolean setA08AlarmMakeFg(String a08_alarm_make_fg)
	{
		this.a08_alarm_make_fg = a08_alarm_make_fg;
		return true;
	}
	public String getA08AlarmMakeFg()
	{
		return cutString(this.a08_alarm_make_fg,A08_ALARM_MAKE_FG_MAX_LENGTH);
	}
	public String getA08AlarmMakeFgString()
	{
		return "'" + cutString(this.a08_alarm_make_fg,A08_ALARM_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getA08AlarmMakeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_alarm_make_fg,A08_ALARM_MAKE_FG_MAX_LENGTH));
	}


	// a08_insert_tsに対するセッターとゲッターの集合
	public boolean setA08InsertTs(String a08_insert_ts)
	{
		this.a08_insert_ts = a08_insert_ts;
		return true;
	}
	public String getA08InsertTs()
	{
		return cutString(this.a08_insert_ts,A08_INSERT_TS_MAX_LENGTH);
	}
	public String getA08InsertTsString()
	{
		return "'" + cutString(this.a08_insert_ts,A08_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getA08InsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_insert_ts,A08_INSERT_TS_MAX_LENGTH));
	}


	// a08_insert_user_idに対するセッターとゲッターの集合
	public boolean setA08InsertUserId(String a08_insert_user_id)
	{
		this.a08_insert_user_id = a08_insert_user_id;
		return true;
	}
	public String getA08InsertUserId()
	{
		return cutString(this.a08_insert_user_id,A08_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getA08InsertUserIdString()
	{
		return "'" + cutString(this.a08_insert_user_id,A08_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getA08InsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_insert_user_id,A08_INSERT_USER_ID_MAX_LENGTH));
	}


	// a08_update_tsに対するセッターとゲッターの集合
	public boolean setA08UpdateTs(String a08_update_ts)
	{
		this.a08_update_ts = a08_update_ts;
		return true;
	}
	public String getA08UpdateTs()
	{
		return cutString(this.a08_update_ts,A08_UPDATE_TS_MAX_LENGTH);
	}
	public String getA08UpdateTsString()
	{
		return "'" + cutString(this.a08_update_ts,A08_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getA08UpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_update_ts,A08_UPDATE_TS_MAX_LENGTH));
	}


	// a08_update_user_idに対するセッターとゲッターの集合
	public boolean setA08UpdateUserId(String a08_update_user_id)
	{
		this.a08_update_user_id = a08_update_user_id;
		return true;
	}
	public String getA08UpdateUserId()
	{
		return cutString(this.a08_update_user_id,A08_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getA08UpdateUserIdString()
	{
		return "'" + cutString(this.a08_update_user_id,A08_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getA08UpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.a08_update_user_id,A08_UPDATE_USER_ID_MAX_LENGTH));
	}


	// ts_torikomi_dtに対するセッターとゲッターの集合
	public boolean setTsTorikomiDt(String ts_torikomi_dt)
	{
		this.ts_torikomi_dt = ts_torikomi_dt;
		return true;
	}
	public String getTsTorikomiDt()
	{
		return cutString(this.ts_torikomi_dt,TS_TORIKOMI_DT_MAX_LENGTH);
	}
	public String getTsTorikomiDtString()
	{
		return "'" + cutString(this.ts_torikomi_dt,TS_TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getTsTorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_torikomi_dt,TS_TORIKOMI_DT_MAX_LENGTH));
	}


	// ts_excel_file_syubetsuに対するセッターとゲッターの集合
	public boolean setTsExcelFileSyubetsu(String ts_excel_file_syubetsu)
	{
		this.ts_excel_file_syubetsu = ts_excel_file_syubetsu;
		return true;
	}
	public String getTsExcelFileSyubetsu()
	{
		return cutString(this.ts_excel_file_syubetsu,TS_EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getTsExcelFileSyubetsuString()
	{
		return "'" + cutString(this.ts_excel_file_syubetsu,TS_EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getTsExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_excel_file_syubetsu,TS_EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}


	// ts_uketsuke_noに対するセッターとゲッターの集合
	public boolean setTsUketsukeNo(String ts_uketsuke_no)
	{
		try
		{
			this.ts_uketsuke_no = Long.parseLong(ts_uketsuke_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTsUketsukeNo(long ts_uketsuke_no)
	{
		this.ts_uketsuke_no = ts_uketsuke_no;
		return true;
	}
	public long getTsUketsukeNo()
	{
		return this.ts_uketsuke_no;
	}
	public String getTsUketsukeNoString()
	{
		return Long.toString(this.ts_uketsuke_no);
	}


	// ts_excel_file_naに対するセッターとゲッターの集合
	public boolean setTsExcelFileNa(String ts_excel_file_na)
	{
		this.ts_excel_file_na = ts_excel_file_na;
		return true;
	}
	public String getTsExcelFileNa()
	{
		return cutString(this.ts_excel_file_na,TS_EXCEL_FILE_NA_MAX_LENGTH);
	}
	public String getTsExcelFileNaString()
	{
		return "'" + cutString(this.ts_excel_file_na,TS_EXCEL_FILE_NA_MAX_LENGTH) + "'";
	}
	public String getTsExcelFileNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_excel_file_na,TS_EXCEL_FILE_NA_MAX_LENGTH));
	}


//	// ts_area_cdに対するセッターとゲッターの集合
//	public boolean setTsAreaCd(String ts_area_cd)
//	{
//		this.ts_area_cd = ts_area_cd;
//		return true;
//	}
//	public String getTsAreaCd()
//	{
//		return cutString(this.ts_area_cd,TS_AREA_CD_MAX_LENGTH);
//	}
//	public String getTsAreaCdString()
//	{
//		return "'" + cutString(this.ts_area_cd,TS_AREA_CD_MAX_LENGTH) + "'";
//	}
//	public String getTsAreaCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.ts_area_cd,TS_AREA_CD_MAX_LENGTH));
//	}


	// ts_siiresaki_cdに対するセッターとゲッターの集合
	public boolean setTsSiiresakiCd(String ts_siiresaki_cd)
	{
		this.ts_siiresaki_cd = ts_siiresaki_cd;
		return true;
	}
	public String getTsSiiresakiCd()
	{
		return cutString(this.ts_siiresaki_cd,TS_SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getTsSiiresakiCdString()
	{
		return "'" + cutString(this.ts_siiresaki_cd,TS_SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getTsSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_siiresaki_cd,TS_SIIRESAKI_CD_MAX_LENGTH));
	}


	// ts_upload_comment_txに対するセッターとゲッターの集合
	public boolean setTsUploadCommentTx(String ts_upload_comment_tx)
	{
		this.ts_upload_comment_tx = ts_upload_comment_tx;
		return true;
	}
	public String getTsUploadCommentTx()
	{
		return cutString(this.ts_upload_comment_tx,TS_UPLOAD_COMMENT_TX_MAX_LENGTH);
	}
	public String getTsUploadCommentTxString()
	{
		return "'" + cutString(this.ts_upload_comment_tx,TS_UPLOAD_COMMENT_TX_MAX_LENGTH) + "'";
	}
	public String getTsUploadCommentTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_upload_comment_tx,TS_UPLOAD_COMMENT_TX_MAX_LENGTH));
	}


	// ts_toroku_syonin_fgに対するセッターとゲッターの集合
	public boolean setTsTorokuSyoninFg(String ts_toroku_syonin_fg)
	{
		this.ts_toroku_syonin_fg = ts_toroku_syonin_fg;
		return true;
	}
	public String getTsTorokuSyoninFg()
	{
		return cutString(this.ts_toroku_syonin_fg,TS_TOROKU_SYONIN_FG_MAX_LENGTH);
	}
	public String getTsTorokuSyoninFgString()
	{
		return "'" + cutString(this.ts_toroku_syonin_fg,TS_TOROKU_SYONIN_FG_MAX_LENGTH) + "'";
	}
	public String getTsTorokuSyoninFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_toroku_syonin_fg,TS_TOROKU_SYONIN_FG_MAX_LENGTH));
	}


	// ts_toroku_syonin_tsに対するセッターとゲッターの集合
	public boolean setTsTorokuSyoninTs(String ts_toroku_syonin_ts)
	{
		this.ts_toroku_syonin_ts = ts_toroku_syonin_ts;
		return true;
	}
	public String getTsTorokuSyoninTs()
	{
		return cutString(this.ts_toroku_syonin_ts,TS_TOROKU_SYONIN_TS_MAX_LENGTH);
	}
	public String getTsTorokuSyoninTsString()
	{
		return "'" + cutString(this.ts_toroku_syonin_ts,TS_TOROKU_SYONIN_TS_MAX_LENGTH) + "'";
	}
	public String getTsTorokuSyoninTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_toroku_syonin_ts,TS_TOROKU_SYONIN_TS_MAX_LENGTH));
	}


	// ts_by_noに対するセッターとゲッターの集合
	public boolean setTsByNo(String ts_by_no)
	{
		this.ts_by_no = ts_by_no;
		return true;
	}
	public String getTsByNo()
	{
		return cutString(this.ts_by_no,TS_BY_NO_MAX_LENGTH);
	}
	public String getTsByNoString()
	{
		return "'" + cutString(this.ts_by_no,TS_BY_NO_MAX_LENGTH) + "'";
	}
	public String getTsByNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_by_no,TS_BY_NO_MAX_LENGTH));
	}


	// ts_syonin_comment_txに対するセッターとゲッターの集合
	public boolean setTsSyoninCommentTx(String ts_syonin_comment_tx)
	{
		this.ts_syonin_comment_tx = ts_syonin_comment_tx;
		return true;
	}
	public String getTsSyoninCommentTx()
	{
		return cutString(this.ts_syonin_comment_tx,TS_SYONIN_COMMENT_TX_MAX_LENGTH);
	}
	public String getTsSyoninCommentTxString()
	{
		return "'" + cutString(this.ts_syonin_comment_tx,TS_SYONIN_COMMENT_TX_MAX_LENGTH) + "'";
	}
	public String getTsSyoninCommentTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_syonin_comment_tx,TS_SYONIN_COMMENT_TX_MAX_LENGTH));
	}


	// ts_excel_syohin_kbに対するセッターとゲッターの集合
	public boolean setTsExcelSyohinKb(String ts_excel_syohin_kb)
	{
		this.ts_excel_syohin_kb = ts_excel_syohin_kb;
		return true;
	}
	public String getTsExcelSyohinKb()
	{
		return cutString(this.ts_excel_syohin_kb,TS_EXCEL_SYOHIN_KB_MAX_LENGTH);
	}
	public String getTsExcelSyohinKbString()
	{
		return "'" + cutString(this.ts_excel_syohin_kb,TS_EXCEL_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getTsExcelSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_excel_syohin_kb,TS_EXCEL_SYOHIN_KB_MAX_LENGTH));
	}


	// ts_excel_tanpin_kbに対するセッターとゲッターの集合
	public boolean setTsExcelTanpinKb(String ts_excel_tanpin_kb)
	{
		this.ts_excel_tanpin_kb = ts_excel_tanpin_kb;
		return true;
	}
	public String getTsExcelTanpinKb()
	{
		return cutString(this.ts_excel_tanpin_kb,TS_EXCEL_TANPIN_KB_MAX_LENGTH);
	}
	public String getTsExcelTanpinKbString()
	{
		return "'" + cutString(this.ts_excel_tanpin_kb,TS_EXCEL_TANPIN_KB_MAX_LENGTH) + "'";
	}
	public String getTsExcelTanpinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_excel_tanpin_kb,TS_EXCEL_TANPIN_KB_MAX_LENGTH));
	}


	// ts_excel_reigai_kbに対するセッターとゲッターの集合
	public boolean setTsExcelReigaiKb(String ts_excel_reigai_kb)
	{
		this.ts_excel_reigai_kb = ts_excel_reigai_kb;
		return true;
	}
	public String getTsExcelReigaiKb()
	{
		return cutString(this.ts_excel_reigai_kb,TS_EXCEL_REIGAI_KB_MAX_LENGTH);
	}
	public String getTsExcelReigaiKbString()
	{
		return "'" + cutString(this.ts_excel_reigai_kb,TS_EXCEL_REIGAI_KB_MAX_LENGTH) + "'";
	}
	public String getTsExcelReigaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_excel_reigai_kb,TS_EXCEL_REIGAI_KB_MAX_LENGTH));
	}


	// ts_excel_syokai_kbに対するセッターとゲッターの集合
	public boolean setTsExcelSyokaiKb(String ts_excel_syokai_kb)
	{
		this.ts_excel_syokai_kb = ts_excel_syokai_kb;
		return true;
	}
	public String getTsExcelSyokaiKb()
	{
		return cutString(this.ts_excel_syokai_kb,TS_EXCEL_SYOKAI_KB_MAX_LENGTH);
	}
	public String getTsExcelSyokaiKbString()
	{
		return "'" + cutString(this.ts_excel_syokai_kb,TS_EXCEL_SYOKAI_KB_MAX_LENGTH) + "'";
	}
	public String getTsExcelSyokaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_excel_syokai_kb,TS_EXCEL_SYOKAI_KB_MAX_LENGTH));
	}


	// ts_min_neire_rtに対するセッターとゲッターの集合
	public boolean setTsMinNeireRt(String ts_min_neire_rt)
	{
		try
		{
			this.ts_min_neire_rt = Double.parseDouble(ts_min_neire_rt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTsMinNeireRt(double ts_min_neire_rt)
	{
		this.ts_min_neire_rt = ts_min_neire_rt;
		return true;
	}
	public double getTsMinNeireRt()
	{
		return this.ts_min_neire_rt;
	}
	public String getTsMinNeireRtString()
	{
		return Double.toString(this.ts_min_neire_rt);
	}


	// ts_max_neire_rtに対するセッターとゲッターの集合
	public boolean setTsMaxNeireRt(String ts_max_neire_rt)
	{
		try
		{
			this.ts_max_neire_rt = Double.parseDouble(ts_max_neire_rt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTsMaxNeireRt(double ts_max_neire_rt)
	{
		this.ts_max_neire_rt = ts_max_neire_rt;
		return true;
	}
	public double getMaxNeireRt()
	{
		return this.ts_max_neire_rt;
	}
	public String getTsMaxNeireRtString()
	{
		return Double.toString(this.ts_max_neire_rt);
	}


	// ts_max_uritanka_vlに対するセッターとゲッターの集合
	public boolean setTsMaxUritankaVl(String ts_max_uritanka_vl)
	{
		try
		{
			this.ts_max_uritanka_vl = Long.parseLong(ts_max_uritanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTsMaxUritankaVl(long ts_max_uritanka_vl)
	{
		this.ts_max_uritanka_vl = ts_max_uritanka_vl;
		return true;
	}
	public long getTsMaxUritankaVl()
	{
		return this.ts_max_uritanka_vl;
	}
	public String getTsMaxUritankaVlString()
	{
		return Long.toString(this.ts_max_uritanka_vl);
	}


	// ts_syori_jyotai_fgに対するセッターとゲッターの集合
	public boolean setTsSyoriJyotaiFg(String ts_syori_jyotai_fg)
	{
		this.ts_syori_jyotai_fg = ts_syori_jyotai_fg;
		return true;
	}
	public String getTsSyoriJyotaiFg()
	{
		return cutString(this.ts_syori_jyotai_fg,TS_SYORI_JYOTAI_FG_MAX_LENGTH);
	}
	public String getTsSyoriJyotaiFgString()
	{
		return "'" + cutString(this.ts_syori_jyotai_fg,TS_SYORI_JYOTAI_FG_MAX_LENGTH) + "'";
	}
	public String getTsSyoriJyotaiFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_syori_jyotai_fg,TS_SYORI_JYOTAI_FG_MAX_LENGTH));
	}


	// ts_kakunin_fgに対するセッターとゲッターの集合
	public boolean setTsKakuninFg(String ts_kakunin_fg)
	{
		this.ts_kakunin_fg = ts_kakunin_fg;
		return true;
	}
	public String getTsKakuninFg()
	{
		return cutString(this.ts_kakunin_fg,TS_KAKUNIN_FG_MAX_LENGTH);
	}
	public String getTsKakuninFgString()
	{
		return "'" + cutString(this.ts_kakunin_fg,TS_KAKUNIN_FG_MAX_LENGTH) + "'";
	}
	public String getTsKakuninFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_kakunin_fg,TS_KAKUNIN_FG_MAX_LENGTH));
	}


	// ts_delete_fgに対するセッターとゲッターの集合
	public boolean setTsDeleteFg(String ts_delete_fg)
	{
		this.ts_delete_fg = ts_delete_fg;
		return true;
	}
	public String getTsDeleteFg()
	{
		return cutString(this.ts_delete_fg,TS_DELETE_FG_MAX_LENGTH);
	}
	public String getTsDeleteFgString()
	{
		return "'" + cutString(this.ts_delete_fg,TS_DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getTsDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_delete_fg,TS_DELETE_FG_MAX_LENGTH));
	}


	// ts_insert_tsに対するセッターとゲッターの集合
	public boolean setTsInsertTs(String ts_insert_ts)
	{
		this.ts_insert_ts = ts_insert_ts;
		return true;
	}
	public String getTsInsertTs()
	{
		return cutString(this.ts_insert_ts,TS_INSERT_TS_MAX_LENGTH);
	}
	public String getTsInsertTsString()
	{
		return "'" + cutString(this.ts_insert_ts,TS_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getTsInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_insert_ts,TS_INSERT_TS_MAX_LENGTH));
	}


	// ts_insert_user_idに対するセッターとゲッターの集合
	public boolean setTsInsertUserId(String ts_insert_user_id)
	{
		this.ts_insert_user_id = ts_insert_user_id;
		return true;
	}
	public String getTsInsertUserId()
	{
		return cutString(this.ts_insert_user_id,TS_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getTsInsertUserIdString()
	{
		return "'" + cutString(this.ts_insert_user_id,TS_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getTsInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_insert_user_id,TS_INSERT_USER_ID_MAX_LENGTH));
	}


	// ts_update_tsに対するセッターとゲッターの集合
	public boolean setTsUpdateTs(String ts_update_ts)
	{
		this.ts_update_ts = ts_update_ts;
		return true;
	}
	public String getTsUpdateTs()
	{
		return cutString(this.ts_update_ts,TS_UPDATE_TS_MAX_LENGTH);
	}
	public String getTsUpdateTsString()
	{
		return "'" + cutString(this.ts_update_ts,TS_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getTsUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_update_ts,TS_UPDATE_TS_MAX_LENGTH));
	}


	// ts_update_user_idに対するセッターとゲッターの集合
	public boolean setTsUpdateUserId(String ts_update_user_id)
	{
		this.ts_update_user_id = ts_update_user_id;
		return true;
	}
	public String getTsUpdateUserId()
	{
		return cutString(this.ts_update_user_id,TS_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getTsUpdateUserIdString()
	{
		return "'" + cutString(this.ts_update_user_id,TS_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getTsUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_update_user_id,TS_UPDATE_USER_ID_MAX_LENGTH));
	}
	
	// ts_riyo_user_naに対するセッターとゲッターの集合 by kema 06.09.08
	public boolean setTsRiyoUserNa(String ts_riyo_user_na)
	{
		this.ts_riyo_user_na = ts_riyo_user_na;
		return true;
	}
	public String getTsRiyoUserNa()
	{
		return cutString(this.ts_riyo_user_na,TS_RIYO_USER_NA_MAX_LENGTH);
	}
	public String getTsRiyoUserNaString()
	{
		return "'" + cutString(this.ts_riyo_user_na,TS_RIYO_USER_NA_MAX_LENGTH) + "'";
	}
	public String getTsRiyoUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ts_riyo_user_na,TS_RIYO_USER_NA_MAX_LENGTH));
	}
	
	// syo_torikomi_dtに対するセッターとゲッターの集合
	public boolean setSyoTorikomiDt(String syo_torikomi_dt)
	{
		this.syo_torikomi_dt = syo_torikomi_dt;
		return true;
	}
	public String getSyoTorikomiDt()
	{
		return cutString(this.syo_torikomi_dt,SYO_TORIKOMI_DT_MAX_LENGTH);
	}
	public String getSyoTorikomiDtString()
	{
		return "'" + cutString(this.syo_torikomi_dt,SYO_TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getSyoTorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_torikomi_dt,SYO_TORIKOMI_DT_MAX_LENGTH));
	}


	// syo_excel_file_syubetsuに対するセッターとゲッターの集合
	public boolean setSyoExcelFileSyubetsu(String syo_excel_file_syubetsu)
	{
		this.syo_excel_file_syubetsu = syo_excel_file_syubetsu;
		return true;
	}
	public String getSyoExcelFileSyubetsu()
	{
		return cutString(this.syo_excel_file_syubetsu,SYO_EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getSyoExcelFileSyubetsuString()
	{
		return "'" + cutString(this.syo_excel_file_syubetsu,SYO_EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getSyoExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_excel_file_syubetsu,SYO_EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}


	// syo_uketsuke_noに対するセッターとゲッターの集合
	public boolean setSyoUketsukeNo(String syo_uketsuke_no)
	{
		try
		{
			this.syo_uketsuke_no = Long.parseLong(syo_uketsuke_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyoUketsukeNo(long syo_uketsuke_no)
	{
		this.syo_uketsuke_no = syo_uketsuke_no;
		return true;
	}
	public long getSyoUketsukeNo()
	{
		return this.syo_uketsuke_no;
	}
	public String getSyoUketsukeNoString()
	{
		return Long.toString(this.syo_uketsuke_no);
	}


	// syo_uketsuke_seqに対するセッターとゲッターの集合
	public boolean setSyoUketsukeSeq(String syo_uketsuke_seq)
	{
		try
		{
			this.syo_uketsuke_seq = Long.parseLong(syo_uketsuke_seq);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyoUketsukeSeq(long syo_uketsuke_seq)
	{
		this.syo_uketsuke_seq = syo_uketsuke_seq;
		return true;
	}
	public long getSyoUketsukeSeq()
	{
		return this.syo_uketsuke_seq;
	}
	public String getSyoUketsukeSeqString()
	{
		return Long.toString(this.syo_uketsuke_seq);
	}


	// syo_hachuno_cdに対するセッターとゲッターの集合
	public boolean setSyoHachunoCd(String syo_hachuno_cd)
	{
		this.syo_hachuno_cd = syo_hachuno_cd;
		return true;
	}
	public String getSyoHachunoCd()
	{
		return cutString(this.syo_hachuno_cd,SYO_HACHUNO_CD_MAX_LENGTH);
	}
	public String getSyoHachunoCdString()
	{
		return "'" + cutString(this.syo_hachuno_cd,SYO_HACHUNO_CD_MAX_LENGTH) + "'";
	}
	public String getSyoHachunoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_hachuno_cd,SYO_HACHUNO_CD_MAX_LENGTH));
	}


	// syo_bumon_cdに対するセッターとゲッターの集合
	public boolean setSyoBumonCd(String syo_bumon_cd)
	{
		this.syo_bumon_cd = syo_bumon_cd;
		return true;
	}
	public String getSyoBumonCd()
	{
		return cutString(this.syo_bumon_cd,SYO_BUMON_CD_MAX_LENGTH);
	}
	public String getSyoBumonCdString()
	{
		return "'" + cutString(this.syo_bumon_cd,SYO_BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getSyoBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_bumon_cd,SYO_BUMON_CD_MAX_LENGTH));
	}


	// syo_syohin_cdに対するセッターとゲッターの集合
	public boolean setSyoSyohinCd(String syo_syohin_cd)
	{
		this.syo_syohin_cd = syo_syohin_cd;
		return true;
	}
	public String getSyoSyohinCd()
	{
		return cutString(this.syo_syohin_cd,SYO_SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyoSyohinCdString()
	{
		return "'" + cutString(this.syo_syohin_cd,SYO_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyoSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_syohin_cd,SYO_SYOHIN_CD_MAX_LENGTH));
	}


	// syo_tenpo_cdに対するセッターとゲッターの集合
	public boolean setSyoTenpoCd(String syo_tenpo_cd)
	{
		this.syo_tenpo_cd = syo_tenpo_cd;
		return true;
	}
	public String getSyoTenpoCd()
	{
		return cutString(this.syo_tenpo_cd,SYO_TENPO_CD_MAX_LENGTH);
	}
	public String getSyoTenpoCdString()
	{
		return "'" + cutString(this.syo_tenpo_cd,SYO_TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getSyoTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_tenpo_cd,SYO_TENPO_CD_MAX_LENGTH));
	}


	// syo_theme_cdに対するセッターとゲッターの集合
	public boolean setSyoThemeCd(String syo_theme_cd)
	{
		this.syo_theme_cd = syo_theme_cd;
		return true;
	}
	public String getSyoThemeCd()
	{
		return cutString(this.syo_theme_cd,SYO_THEME_CD_MAX_LENGTH);
	}
	public String getSyoThemeCdString()
	{
		return "'" + cutString(this.syo_theme_cd,SYO_THEME_CD_MAX_LENGTH) + "'";
	}
	public String getSyoThemeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_theme_cd,SYO_THEME_CD_MAX_LENGTH));
	}


	// syo_suryo_qtに対するセッターとゲッターの集合
	public boolean setSyoSuryoQt(String syo_suryo_qt)
	{
		this.syo_suryo_qt = syo_suryo_qt;
		return true;
	}
	public String getSyoSuryoQt()
	{
		return cutString(this.syo_suryo_qt,SYO_SURYO_QT_MAX_LENGTH);
	}
	public String getSyoSuryoQtString()
	{
		return "'" + cutString(this.syo_suryo_qt,SYO_SURYO_QT_MAX_LENGTH) + "'";
	}
	public String getSyoSuryoQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_suryo_qt,SYO_SURYO_QT_MAX_LENGTH));
	}


	// syo_hachutani_qtに対するセッターとゲッターの集合
	public boolean setSyoHachutaniQt(String syo_hachutani_qt)
	{
		this.syo_hachutani_qt = syo_hachutani_qt;
		return true;
	}
	public String getSyoHachutaniQt()
	{
		return cutString(this.syo_hachutani_qt,SYO_HACHUTANI_QT_MAX_LENGTH);
	}
	public String getSyoHachutaniQtString()
	{
		return "'" + cutString(this.syo_hachutani_qt,SYO_HACHUTANI_QT_MAX_LENGTH) + "'";
	}
	public String getSyoHachutaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_hachutani_qt,SYO_HACHUTANI_QT_MAX_LENGTH));
	}


	// syo_gentanka_vlに対するセッターとゲッターの集合
	public boolean setSyoGentankaVl(String syo_gentanka_vl)
	{
		this.syo_gentanka_vl = syo_gentanka_vl;
		return true;
	}
	public String getSyoGentankaVl()
	{
		return cutString(this.syo_gentanka_vl,SYO_GENTANKA_VL_MAX_LENGTH);
	}
	public String getSyoGentankaVlString()
	{
		return "'" + cutString(this.syo_gentanka_vl,SYO_GENTANKA_VL_MAX_LENGTH) + "'";
	}
	public String getSyoGentankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_gentanka_vl,SYO_GENTANKA_VL_MAX_LENGTH));
	}


	// syo_baitanka_vlに対するセッターとゲッターの集合
	public boolean setSyoBaitankaVl(String syo_baitanka_vl)
	{
		this.syo_baitanka_vl = syo_baitanka_vl;
		return true;
	}
	public String getSyoBaitankaVl()
	{
		return cutString(this.syo_baitanka_vl,SYO_BAITANKA_VL_MAX_LENGTH);
	}
	public String getSyoBaitankaVlString()
	{
		return "'" + cutString(this.syo_baitanka_vl,SYO_BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getSyoBaitankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_baitanka_vl,SYO_BAITANKA_VL_MAX_LENGTH));
	}


	// syo_hatyu_dtに対するセッターとゲッターの集合
	public boolean setSyoHatyuDt(String syo_hatyu_dt)
	{
		this.syo_hatyu_dt = syo_hatyu_dt;
		return true;
	}
	public String getSyoHatyuDt()
	{
		return cutString(this.syo_hatyu_dt,SYO_HATYU_DT_MAX_LENGTH);
	}
	public String getSyoHatyuDtString()
	{
		return "'" + cutString(this.syo_hatyu_dt,SYO_HATYU_DT_MAX_LENGTH) + "'";
	}
	public String getSyoHatyuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_hatyu_dt,SYO_HATYU_DT_MAX_LENGTH));
	}


	// syo_nohin_dtに対するセッターとゲッターの集合
	public boolean setSyoNohinDt(String syo_nohin_dt)
	{
		this.syo_nohin_dt = syo_nohin_dt;
		return true;
	}
	public String getSyoNohinDt()
	{
		return cutString(this.syo_nohin_dt,SYO_NOHIN_DT_MAX_LENGTH);
	}
	public String getSyoNohinDtString()
	{
		return "'" + cutString(this.syo_nohin_dt,SYO_NOHIN_DT_MAX_LENGTH) + "'";
	}
	public String getSyoNohinDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_nohin_dt,SYO_NOHIN_DT_MAX_LENGTH));
	}


	// syo_hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setSyoHanbaiStDt(String syo_hanbai_st_dt)
	{
		this.syo_hanbai_st_dt = syo_hanbai_st_dt;
		return true;
	}
	public String getSyoHanbaiStDt()
	{
		return cutString(this.syo_hanbai_st_dt,SYO_HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getSyoHanbaiStDtString()
	{
		return "'" + cutString(this.syo_hanbai_st_dt,SYO_HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getSyoHanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_hanbai_st_dt,SYO_HANBAI_ST_DT_MAX_LENGTH));
	}


	// syo_hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setSyoHanbaiEdDt(String syo_hanbai_ed_dt)
	{
		this.syo_hanbai_ed_dt = syo_hanbai_ed_dt;
		return true;
	}
	public String getSyoHanbaiEdDt()
	{
		return cutString(this.syo_hanbai_ed_dt,SYO_HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getSyoHanbaiEdDtString()
	{
		return "'" + cutString(this.syo_hanbai_ed_dt,SYO_HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getSyoHanbaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_hanbai_ed_dt,SYO_HANBAI_ED_DT_MAX_LENGTH));
	}


	// syo_syusei_kbに対するセッターとゲッターの集合
	public boolean setSyoSyuseiKb(String syo_syusei_kb)
	{
		this.syo_syusei_kb = syo_syusei_kb;
		return true;
	}
	public String getSyoSyuseiKb()
	{
		return cutString(this.syo_syusei_kb,SYO_SYUSEI_KB_MAX_LENGTH);
	}
	public String getSyoSyuseiKbString()
	{
		return "'" + cutString(this.syo_syusei_kb,SYO_SYUSEI_KB_MAX_LENGTH) + "'";
	}
	public String getSyoSyuseiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_syusei_kb,SYO_SYUSEI_KB_MAX_LENGTH));
	}


	// syo_syohin_gyo_noに対するセッターとゲッターの集合
	public boolean setSyoSyohinGyoNo(String syo_syohin_gyo_no)
	{
		try
		{
			this.syo_syohin_gyo_no = Long.parseLong(syo_syohin_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyoSyohinGyoNo(long syo_syohin_gyo_no)
	{
		this.syo_syohin_gyo_no = syo_syohin_gyo_no;
		return true;
	}
	public long getSyoSyohinGyoNo()
	{
		return this.syo_syohin_gyo_no;
	}
	public String getSyoSyohinGyoNoString()
	{
		return Long.toString(this.syo_syohin_gyo_no);
	}


	// syo_sakusei_gyo_noに対するセッターとゲッターの集合
	public boolean setSyoSakuseiGyoNo(String syo_sakusei_gyo_no)
	{
		try
		{
			this.syo_sakusei_gyo_no = Long.parseLong(syo_sakusei_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyoSakuseiGyoNo(long syo_sakusei_gyo_no)
	{
		this.syo_sakusei_gyo_no = syo_sakusei_gyo_no;
		return true;
	}
	public long getSyoSakuseiGyoNo()
	{
		return this.syo_sakusei_gyo_no;
	}
	public String getSyoSakuseiGyoNoString()
	{
		return Long.toString(this.syo_sakusei_gyo_no);
	}


	// syo_mst_mainte_fgに対するセッターとゲッターの集合
	public boolean setSyoMstMainteFg(String syo_mst_mainte_fg)
	{
		this.syo_mst_mainte_fg = syo_mst_mainte_fg;
		return true;
	}
	public String getSyoMstMainteFg()
	{
		return cutString(this.syo_mst_mainte_fg,SYO_MST_MAINTE_FG_MAX_LENGTH);
	}
	public String getSyoMstMainteFgString()
	{
		return "'" + cutString(this.syo_mst_mainte_fg,SYO_MST_MAINTE_FG_MAX_LENGTH) + "'";
	}
	public String getSyoMstMainteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_mst_mainte_fg,SYO_MST_MAINTE_FG_MAX_LENGTH));
	}


	// syo_alarm_make_fgに対するセッターとゲッターの集合
	public boolean setSyoAlarmMakeFg(String syo_alarm_make_fg)
	{
		this.syo_alarm_make_fg = syo_alarm_make_fg;
		return true;
	}
	public String getSyoAlarmMakeFg()
	{
		return cutString(this.syo_alarm_make_fg,SYO_ALARM_MAKE_FG_MAX_LENGTH);
	}
	public String getSyoAlarmMakeFgString()
	{
		return "'" + cutString(this.syo_alarm_make_fg,SYO_ALARM_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getSyoAlarmMakeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_alarm_make_fg,SYO_ALARM_MAKE_FG_MAX_LENGTH));
	}


	// syo_insert_tsに対するセッターとゲッターの集合
	public boolean setSyoInsertTs(String syo_insert_ts)
	{
		this.syo_insert_ts = syo_insert_ts;
		return true;
	}
	public String getSyoInsertTs()
	{
		return cutString(this.syo_insert_ts,SYO_INSERT_TS_MAX_LENGTH);
	}
	public String getSyoInsertTsString()
	{
		return "'" + cutString(this.syo_insert_ts,SYO_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getSyoInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_insert_ts,SYO_INSERT_TS_MAX_LENGTH));
	}


	// syo_insert_user_idに対するセッターとゲッターの集合
	public boolean setSyoInsertUserId(String syo_insert_user_id)
	{
		this.syo_insert_user_id = syo_insert_user_id;
		return true;
	}
	public String getSyoInsertUserId()
	{
		return cutString(this.syo_insert_user_id,SYO_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getSyoInsertUserIdString()
	{
		return "'" + cutString(this.syo_insert_user_id,SYO_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyoInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_insert_user_id,SYO_INSERT_USER_ID_MAX_LENGTH));
	}


	// syo_update_tsに対するセッターとゲッターの集合
	public boolean setSyoUpdateTs(String syo_update_ts)
	{
		this.syo_update_ts = syo_update_ts;
		return true;
	}
	public String getSyoUpdateTs()
	{
		return cutString(this.syo_update_ts,SYO_UPDATE_TS_MAX_LENGTH);
	}
	public String getSyoUpdateTsString()
	{
		return "'" + cutString(this.syo_update_ts,SYO_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getSyoUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_update_ts,SYO_UPDATE_TS_MAX_LENGTH));
	}


	// syo_update_user_idに対するセッターとゲッターの集合
	public boolean setSyoUpdateUserId(String syo_update_user_id)
	{
		this.syo_update_user_id = syo_update_user_id;
		return true;
	}
	public String getSyoUpdateUserId()
	{
		return cutString(this.syo_update_user_id,SYO_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getSyoUpdateUserIdString()
	{
		return "'" + cutString(this.syo_update_user_id,SYO_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyoUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syo_update_user_id,SYO_UPDATE_USER_ID_MAX_LENGTH));
	}


	// reigai_torikomi_dtに対するセッターとゲッターの集合
	public boolean setReigaiTorikomiDt(String reigai_torikomi_dt)
	{
		this.reigai_torikomi_dt = reigai_torikomi_dt;
		return true;
	}
	public String getReigaiTorikomiDt()
	{
		return cutString(this.reigai_torikomi_dt,REIGAI_TORIKOMI_DT_MAX_LENGTH);
	}
	public String getReigaiTorikomiDtString()
	{
		return "'" + cutString(this.reigai_torikomi_dt,REIGAI_TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getReigaiTorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_torikomi_dt,REIGAI_TORIKOMI_DT_MAX_LENGTH));
	}


	// reigai_excel_file_syubetsuに対するセッターとゲッターの集合
	public boolean setReigaiExcelFileSyubetsu(String reigai_excel_file_syubetsu)
	{
		this.reigai_excel_file_syubetsu = reigai_excel_file_syubetsu;
		return true;
	}
	public String getReigaiExcelFileSyubetsu()
	{
		return cutString(this.reigai_excel_file_syubetsu,REIGAI_EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getReigaiExcelFileSyubetsuString()
	{
		return "'" + cutString(this.reigai_excel_file_syubetsu,REIGAI_EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getReigaiExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_excel_file_syubetsu,REIGAI_EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}


	// reigai_uketsuke_noに対するセッターとゲッターの集合
	public boolean setReigaiUketsukeNo(String reigai_uketsuke_no)
	{
		try
		{
			this.reigai_uketsuke_no = Long.parseLong(reigai_uketsuke_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setReigaiUketsukeNo(long reigai_uketsuke_no)
	{
		this.reigai_uketsuke_no = reigai_uketsuke_no;
		return true;
	}
	public long getReigaiUketsukeNo()
	{
		return this.reigai_uketsuke_no;
	}
	public String getReigaiUketsukeNoString()
	{
		return Long.toString(this.reigai_uketsuke_no);
	}


	// reigai_uketsuke_seqに対するセッターとゲッターの集合
	public boolean setReigaiUketsukeSeq(String reigai_uketsuke_seq)
	{
		try
		{
			this.reigai_uketsuke_seq = Long.parseLong(reigai_uketsuke_seq);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setReigaiUketsukeSeq(long reigai_uketsuke_seq)
	{
		this.reigai_uketsuke_seq = reigai_uketsuke_seq;
		return true;
	}
	public long getReigaiUketsukeSeq()
	{
		return this.reigai_uketsuke_seq;
	}
	public String getReigaiUketsukeSeqString()
	{
		return Long.toString(this.reigai_uketsuke_seq);
	}


	// reigai_bumon_cdに対するセッターとゲッターの集合
	public boolean setReigaiBumonCd(String reigai_bumon_cd)
	{
		this.reigai_bumon_cd = reigai_bumon_cd;
		return true;
	}
	public String getReigaiBumonCd()
	{
		return cutString(this.reigai_bumon_cd,REIGAI_BUMON_CD_MAX_LENGTH);
	}
	public String getReigaiBumonCdString()
	{
		return "'" + cutString(this.reigai_bumon_cd,REIGAI_BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getReigaiBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_bumon_cd,REIGAI_BUMON_CD_MAX_LENGTH));
	}


	// reigai_syohin_cdに対するセッターとゲッターの集合
	public boolean setReigaiSyohinCd(String reigai_syohin_cd)
	{
		this.reigai_syohin_cd = reigai_syohin_cd;
		return true;
	}
	public String getReigaiSyohinCd()
	{
		return cutString(this.reigai_syohin_cd,REIGAI_SYOHIN_CD_MAX_LENGTH);
	}
	public String getReigaiSyohinCdString()
	{
		return "'" + cutString(this.reigai_syohin_cd,REIGAI_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getReigaiSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_syohin_cd,REIGAI_SYOHIN_CD_MAX_LENGTH));
	}


	// reigai_tenpo_cdに対するセッターとゲッターの集合
	public boolean setReigaiTenpoCd(String reigai_tenpo_cd)
	{
		this.reigai_tenpo_cd = reigai_tenpo_cd;
		return true;
	}
	public String getReigaiTenpoCd()
	{
		return cutString(this.reigai_tenpo_cd,REIGAI_TENPO_CD_MAX_LENGTH);
	}
	public String getReigaiTenpoCdString()
	{
		return "'" + cutString(this.reigai_tenpo_cd,REIGAI_TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getReigaiTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_tenpo_cd,REIGAI_TENPO_CD_MAX_LENGTH));
	}


	// reigai_yuko_dtに対するセッターとゲッターの集合
	public boolean setReigaiYukoDt(String reigai_yuko_dt)
	{
		this.reigai_yuko_dt = reigai_yuko_dt;
		return true;
	}
	public String getReigaiYukoDt()
	{
		return cutString(this.reigai_yuko_dt,REIGAI_YUKO_DT_MAX_LENGTH);
	}
	public String getReigaiYukoDtString()
	{
		return "'" + cutString(this.reigai_yuko_dt,REIGAI_YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getReigaiYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_yuko_dt,REIGAI_YUKO_DT_MAX_LENGTH));
	}


	// reigai_gentanka_vlに対するセッターとゲッターの集合
	public boolean setReigaiGentankaVl(String reigai_gentanka_vl)
	{
		this.reigai_gentanka_vl = reigai_gentanka_vl;
		return true;
	}
	public String getReigaiGentankaVl()
	{
		return cutString(this.reigai_gentanka_vl,REIGAI_GENTANKA_VL_MAX_LENGTH);
	}
	public String getReigaiGentankaVlString()
	{
		return "'" + cutString(this.reigai_gentanka_vl,REIGAI_GENTANKA_VL_MAX_LENGTH) + "'";
	}
	public String getReigaiGentankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_gentanka_vl,REIGAI_GENTANKA_VL_MAX_LENGTH));
	}


	// reigai_baitanka_vlに対するセッターとゲッターの集合
	public boolean setReigaiBaitankaVl(String reigai_baitanka_vl)
	{
		this.reigai_baitanka_vl = reigai_baitanka_vl;
		return true;
	}
	public String getReigaiBaitankaVl()
	{
		return cutString(this.reigai_baitanka_vl,REIGAI_BAITANKA_VL_MAX_LENGTH);
	}
	public String getReigaiBaitankaVlString()
	{
		return "'" + cutString(this.reigai_baitanka_vl,REIGAI_BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getReigaiBaitankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_baitanka_vl,REIGAI_BAITANKA_VL_MAX_LENGTH));
	}


	// reigai_max_hachutani_qtに対するセッターとゲッターの集合
	public boolean setReigaiMaxHachutaniQt(String reigai_max_hachutani_qt)
	{
		this.reigai_max_hachutani_qt = reigai_max_hachutani_qt;
		return true;
	}
	public String getReigaiMaxHachutaniQt()
	{
		return cutString(this.reigai_max_hachutani_qt,REIGAI_MAX_HACHUTANI_QT_MAX_LENGTH);
	}
	public String getReigaiMaxHachutaniQtString()
	{
		return "'" + cutString(this.reigai_max_hachutani_qt,REIGAI_MAX_HACHUTANI_QT_MAX_LENGTH) + "'";
	}
	public String getReigaiMaxHachutaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_max_hachutani_qt,REIGAI_MAX_HACHUTANI_QT_MAX_LENGTH));
	}


	// reigai_eos_kbに対するセッターとゲッターの集合
	public boolean setReigaiEosKb(String reigai_eos_kb)
	{
		this.reigai_eos_kb = reigai_eos_kb;
		return true;
	}
	public String getReigaiEosKb()
	{
		return cutString(this.reigai_eos_kb,REIGAI_EOS_KB_MAX_LENGTH);
	}
	public String getReigaiEosKbString()
	{
		return "'" + cutString(this.reigai_eos_kb,REIGAI_EOS_KB_MAX_LENGTH) + "'";
	}
	public String getReigaiEosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_eos_kb,REIGAI_EOS_KB_MAX_LENGTH));
	}


//	// reigai_area_cdに対するセッターとゲッターの集合
//	public boolean setReigaiAreaCd(String reigai_area_cd)
//	{
//		this.reigai_area_cd = reigai_area_cd;
//		return true;
//	}
//	public String getReigaiAreaCd()
//	{
//		return cutString(this.reigai_area_cd,REIGAI_AREA_CD_MAX_LENGTH);
//	}
//	public String getReigaiAreaCdString()
//	{
//		return "'" + cutString(this.reigai_area_cd,REIGAI_AREA_CD_MAX_LENGTH) + "'";
//	}
//	public String getReigaiAreaCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.reigai_area_cd,REIGAI_AREA_CD_MAX_LENGTH));
//	}


	// reigai_siiresaki_cdに対するセッターとゲッターの集合
	public boolean setReigaiSiiresakiCd(String reigai_siiresaki_cd)
	{
		this.reigai_siiresaki_cd = reigai_siiresaki_cd;
		return true;
	}
	public String getReigaiSiiresakiCd()
	{
		return cutString(this.reigai_siiresaki_cd,REIGAI_SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getReigaiSiiresakiCdString()
	{
		return "'" + cutString(this.reigai_siiresaki_cd,REIGAI_SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getReigaiSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_siiresaki_cd,REIGAI_SIIRESAKI_CD_MAX_LENGTH));
	}


	// reigai_ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setReigaiTenHachuStDt(String reigai_ten_hachu_st_dt)
	{
		this.reigai_ten_hachu_st_dt = reigai_ten_hachu_st_dt;
		return true;
	}
	public String getReigaiTenHachuStDt()
	{
		return cutString(this.reigai_ten_hachu_st_dt,REIGAI_TEN_HACHU_ST_DT_MAX_LENGTH);
	}
	public String getReigaiTenHachuStDtString()
	{
		return "'" + cutString(this.reigai_ten_hachu_st_dt,REIGAI_TEN_HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getReigaiTenHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_ten_hachu_st_dt,REIGAI_TEN_HACHU_ST_DT_MAX_LENGTH));
	}


	// reigai_ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setReigaiTenHachuEdDt(String reigai_ten_hachu_ed_dt)
	{
		this.reigai_ten_hachu_ed_dt = reigai_ten_hachu_ed_dt;
		return true;
	}
	public String getReigaiTenHachuEdDt()
	{
		return cutString(this.reigai_ten_hachu_ed_dt,REIGAI_TEN_HACHU_ED_DT_MAX_LENGTH);
	}
	public String getReigaiTenHachuEdDtString()
	{
		return "'" + cutString(this.reigai_ten_hachu_ed_dt,REIGAI_TEN_HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getReigaiTenHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_ten_hachu_ed_dt,REIGAI_TEN_HACHU_ED_DT_MAX_LENGTH));
	}


	// reigai_buturyu_kbに対するセッターとゲッターの集合
	public boolean setReigaiButuryuKb(String reigai_buturyu_kb)
	{
		this.reigai_buturyu_kb = reigai_buturyu_kb;
		return true;
	}
	public String getReigaiButuryuKb()
	{
		return cutString(this.reigai_buturyu_kb,REIGAI_BUTURYU_KB_MAX_LENGTH);
	}
	public String getReigaiButuryuKbString()
	{
		return "'" + cutString(this.reigai_buturyu_kb,REIGAI_BUTURYU_KB_MAX_LENGTH) + "'";
	}
	public String getReigaiButuryuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_buturyu_kb,REIGAI_BUTURYU_KB_MAX_LENGTH));
	}


	// reigai_bin_1_kbに対するセッターとゲッターの集合
	public boolean setReigaiBin1Kb(String reigai_bin_1_kb)
	{
		this.reigai_bin_1_kb = reigai_bin_1_kb;
		return true;
	}
	public String getReigaiBin1Kb()
	{
		return cutString(this.reigai_bin_1_kb,REIGAI_BIN_1_KB_MAX_LENGTH);
	}
	public String getReigaiBin1KbString()
	{
		return "'" + cutString(this.reigai_bin_1_kb,REIGAI_BIN_1_KB_MAX_LENGTH) + "'";
	}
	public String getReigaiBin1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_bin_1_kb,REIGAI_BIN_1_KB_MAX_LENGTH));
	}


	// reigai_bin_2_kbに対するセッターとゲッターの集合
	public boolean setReigaiBin2Kb(String reigai_bin_2_kb)
	{
		this.reigai_bin_2_kb = reigai_bin_2_kb;
		return true;
	}
	public String getReigaiBin2Kb()
	{
		return cutString(this.reigai_bin_2_kb,REIGAI_BIN_2_KB_MAX_LENGTH);
	}
	public String getReigaiBin2KbString()
	{
		return "'" + cutString(this.reigai_bin_2_kb,REIGAI_BIN_2_KB_MAX_LENGTH) + "'";
	}
	public String getReigaiBin2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_bin_2_kb,REIGAI_BIN_2_KB_MAX_LENGTH));
	}


	// reigai_yusen_bin_kbに対するセッターとゲッターの集合
	public boolean setReigaiYusenBinKb(String reigai_yusen_bin_kb)
	{
		this.reigai_yusen_bin_kb = reigai_yusen_bin_kb;
		return true;
	}
	public String getReigaiYusenBinKb()
	{
		return cutString(this.reigai_yusen_bin_kb,REIGAI_YUSEN_BIN_KB_MAX_LENGTH);
	}
	public String getReigaiYusenBinKbString()
	{
		return "'" + cutString(this.reigai_yusen_bin_kb,REIGAI_YUSEN_BIN_KB_MAX_LENGTH) + "'";
	}
	public String getReigaiYusenBinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_yusen_bin_kb,REIGAI_YUSEN_BIN_KB_MAX_LENGTH));
	}


	// reigai_syusei_kbに対するセッターとゲッターの集合
	public boolean setReigaiSyuseiKb(String reigai_syusei_kb)
	{
		this.reigai_syusei_kb = reigai_syusei_kb;
		return true;
	}
	public String getReigaiSyuseiKb()
	{
		return cutString(this.reigai_syusei_kb,REIGAI_SYUSEI_KB_MAX_LENGTH);
	}
	public String getReigaiSyuseiKbString()
	{
		return "'" + cutString(this.reigai_syusei_kb,REIGAI_SYUSEI_KB_MAX_LENGTH) + "'";
	}
	public String getReigaiSyuseiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_syusei_kb,REIGAI_SYUSEI_KB_MAX_LENGTH));
	}


	// reigai_syohin_gyo_noに対するセッターとゲッターの集合
	public boolean setReigaiSyohinGyoNo(String reigai_syohin_gyo_no)
	{
		try
		{
			this.reigai_syohin_gyo_no = Long.parseLong(reigai_syohin_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setReigaiSyohinGyoNo(long reigai_syohin_gyo_no)
	{
		this.reigai_syohin_gyo_no = reigai_syohin_gyo_no;
		return true;
	}
	public long getReigaiSyohinGyoNo()
	{
		return this.reigai_syohin_gyo_no;
	}
	public String getReigaiSyohinGyoNoString()
	{
		return Long.toString(this.reigai_syohin_gyo_no);
	}


	// reigai_sakusei_gyo_noに対するセッターとゲッターの集合
	public boolean setReigaiSakuseiGyoNo(String reigai_sakusei_gyo_no)
	{
		try
		{
			this.reigai_sakusei_gyo_no = Long.parseLong(reigai_sakusei_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setReigaiSakuseiGyoNo(long reigai_sakusei_gyo_no)
	{
		this.reigai_sakusei_gyo_no = reigai_sakusei_gyo_no;
		return true;
	}
	public long getReigaiSakuseiGyoNo()
	{
		return this.reigai_sakusei_gyo_no;
	}
	public String getReigaiSakuseiGyoNoString()
	{
		return Long.toString(this.reigai_sakusei_gyo_no);
	}


	// reigai_mst_mainte_fgに対するセッターとゲッターの集合
	public boolean setReigaiMstMainteFg(String reigai_mst_mainte_fg)
	{
		this.reigai_mst_mainte_fg = reigai_mst_mainte_fg;
		return true;
	}
	public String getReigaiMstMainteFg()
	{
		return cutString(this.reigai_mst_mainte_fg,REIGAI_MST_MAINTE_FG_MAX_LENGTH);
	}
	public String getReigaiMstMainteFgString()
	{
		return "'" + cutString(this.reigai_mst_mainte_fg,REIGAI_MST_MAINTE_FG_MAX_LENGTH) + "'";
	}
	public String getReigaiMstMainteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_mst_mainte_fg,REIGAI_MST_MAINTE_FG_MAX_LENGTH));
	}


	// reigai_alarm_make_fgに対するセッターとゲッターの集合
	public boolean setReigaiAlarmMakeFg(String reigai_alarm_make_fg)
	{
		this.reigai_alarm_make_fg = reigai_alarm_make_fg;
		return true;
	}
	public String getReigaiAlarmMakeFg()
	{
		return cutString(this.reigai_alarm_make_fg,REIGAI_ALARM_MAKE_FG_MAX_LENGTH);
	}
	public String getReigaiAlarmMakeFgString()
	{
		return "'" + cutString(this.reigai_alarm_make_fg,REIGAI_ALARM_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getReigaiAlarmMakeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_alarm_make_fg,REIGAI_ALARM_MAKE_FG_MAX_LENGTH));
	}


	// reigai_insert_tsに対するセッターとゲッターの集合
	public boolean setReigaiInsertTs(String reigai_insert_ts)
	{
		this.reigai_insert_ts = reigai_insert_ts;
		return true;
	}
	public String getReigaiInsertTs()
	{
		return cutString(this.reigai_insert_ts,REIGAI_INSERT_TS_MAX_LENGTH);
	}
	public String getReigaiInsertTsString()
	{
		return "'" + cutString(this.reigai_insert_ts,REIGAI_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getReigaiInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_insert_ts,REIGAI_INSERT_TS_MAX_LENGTH));
	}


	// reigai_insert_user_idに対するセッターとゲッターの集合
	public boolean setReigaiInsertUserId(String reigai_insert_user_id)
	{
		this.reigai_insert_user_id = reigai_insert_user_id;
		return true;
	}
	public String getReigaiInsertUserId()
	{
		return cutString(this.reigai_insert_user_id,REIGAI_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getReigaiInsertUserIdString()
	{
		return "'" + cutString(this.reigai_insert_user_id,REIGAI_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getReigaiInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_insert_user_id,REIGAI_INSERT_USER_ID_MAX_LENGTH));
	}


	// reigai_update_tsに対するセッターとゲッターの集合
	public boolean setReigaiUpdateTs(String reigai_update_ts)
	{
		this.reigai_update_ts = reigai_update_ts;
		return true;
	}
	public String getReigaiUpdateTs()
	{
		return cutString(this.reigai_update_ts,REIGAI_UPDATE_TS_MAX_LENGTH);
	}
	public String getReigaiUpdateTsString()
	{
		return "'" + cutString(this.reigai_update_ts,REIGAI_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getReigaiUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_update_ts,REIGAI_UPDATE_TS_MAX_LENGTH));
	}


	// reigai_update_user_idに対するセッターとゲッターの集合
	public boolean setReigaiUpdateUserId(String reigai_update_user_id)
	{
		this.reigai_update_user_id = reigai_update_user_id;
		return true;
	}
	public String getReigaiUpdateUserId()
	{
		return cutString(this.reigai_update_user_id,REIGAI_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getReigaiUpdateUserIdString()
	{
		return "'" + cutString(this.reigai_update_user_id,REIGAI_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getReigaiUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reigai_update_user_id,REIGAI_UPDATE_USER_ID_MAX_LENGTH));
	}


	// tanpin_torikomi_dtに対するセッターとゲッターの集合
	public boolean setTanpinTorikomiDt(String tanpin_torikomi_dt)
	{
		this.tanpin_torikomi_dt = tanpin_torikomi_dt;
		return true;
	}
	public String getTanpinTorikomiDt()
	{
		return cutString(this.tanpin_torikomi_dt,TANPIN_TORIKOMI_DT_MAX_LENGTH);
	}
	public String getTanpinTorikomiDtString()
	{
		return "'" + cutString(this.tanpin_torikomi_dt,TANPIN_TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getTanpinTorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_torikomi_dt,TANPIN_TORIKOMI_DT_MAX_LENGTH));
	}


	// tanpin_excel_file_syubetsuに対するセッターとゲッターの集合
	public boolean setTanpinExcelFileSyubetsu(String tanpin_excel_file_syubetsu)
	{
		this.tanpin_excel_file_syubetsu = tanpin_excel_file_syubetsu;
		return true;
	}
	public String getTanpinExcelFileSyubetsu()
	{
		return cutString(this.tanpin_excel_file_syubetsu,TANPIN_EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getTanpinExcelFileSyubetsuString()
	{
		return "'" + cutString(this.tanpin_excel_file_syubetsu,TANPIN_EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getTanpinExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_excel_file_syubetsu,TANPIN_EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}


	// tanpin_uketsuke_noに対するセッターとゲッターの集合
	public boolean setTanpinUketsukeNo(String tanpin_uketsuke_no)
	{
		try
		{
			this.tanpin_uketsuke_no = Long.parseLong(tanpin_uketsuke_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTanpinUketsukeNo(long tanpin_uketsuke_no)
	{
		this.tanpin_uketsuke_no = tanpin_uketsuke_no;
		return true;
	}
	public long getTanpinUketsukeNo()
	{
		return this.tanpin_uketsuke_no;
	}
	public String getTanpinUketsukeNoString()
	{
		return Long.toString(this.tanpin_uketsuke_no);
	}


	// tanpin_uketsuke_seqに対するセッターとゲッターの集合
	public boolean setTanpinUketsukeSeq(String tanpin_uketsuke_seq)
	{
		try
		{
			this.tanpin_uketsuke_seq = Long.parseLong(tanpin_uketsuke_seq);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTanpinUketsukeSeq(long tanpin_uketsuke_seq)
	{
		this.tanpin_uketsuke_seq = tanpin_uketsuke_seq;
		return true;
	}
	public long getTanpinUketsukeSeq()
	{
		return this.tanpin_uketsuke_seq;
	}
	public String getTanpinUketsukeSeqString()
	{
		return Long.toString(this.tanpin_uketsuke_seq);
	}


	// tanpin_bumon_cdに対するセッターとゲッターの集合
	public boolean setTanpinBumonCd(String tanpin_bumon_cd)
	{
		this.tanpin_bumon_cd = tanpin_bumon_cd;
		return true;
	}
	public String getTanpinBumonCd()
	{
		return cutString(this.tanpin_bumon_cd,TANPIN_BUMON_CD_MAX_LENGTH);
	}
	public String getTanpinBumonCdString()
	{
		return "'" + cutString(this.tanpin_bumon_cd,TANPIN_BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getTanpinBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_bumon_cd,TANPIN_BUMON_CD_MAX_LENGTH));
	}


	// tanpin_syohin_cdに対するセッターとゲッターの集合
	public boolean setTanpinSyohinCd(String tanpin_syohin_cd)
	{
		this.tanpin_syohin_cd = tanpin_syohin_cd;
		return true;
	}
	public String getTanpinSyohinCd()
	{
		return cutString(this.tanpin_syohin_cd,TANPIN_SYOHIN_CD_MAX_LENGTH);
	}
	public String getTanpinSyohinCdString()
	{
		return "'" + cutString(this.tanpin_syohin_cd,TANPIN_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getTanpinSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_syohin_cd,TANPIN_SYOHIN_CD_MAX_LENGTH));
	}


	// tanpin_tenpo_cdに対するセッターとゲッターの集合
	public boolean setTanpinTenpoCd(String tanpin_tenpo_cd)
	{
		this.tanpin_tenpo_cd = tanpin_tenpo_cd;
		return true;
	}
	public String getTanpinTenpoCd()
	{
		return cutString(this.tanpin_tenpo_cd,TANPIN_TENPO_CD_MAX_LENGTH);
	}
	public String getTanpinTenpoCdString()
	{
		return "'" + cutString(this.tanpin_tenpo_cd,TANPIN_TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getTanpinTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_tenpo_cd,TANPIN_TENPO_CD_MAX_LENGTH));
	}


	// tanpin_donyu_dtに対するセッターとゲッターの集合
	public boolean setTanpinDonyuDt(String tanpin_donyu_dt)
	{
		this.tanpin_donyu_dt = tanpin_donyu_dt;
		return true;
	}
	public String getTanpinDonyuDt()
	{
		return cutString(this.tanpin_donyu_dt,TANPIN_DONYU_DT_MAX_LENGTH);
	}
	public String getTanpinDonyuDtString()
	{
		return "'" + cutString(this.tanpin_donyu_dt,TANPIN_DONYU_DT_MAX_LENGTH) + "'";
	}
	public String getTanpinDonyuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_donyu_dt,TANPIN_DONYU_DT_MAX_LENGTH));
	}


	// tanpin_syusei_kbに対するセッターとゲッターの集合
	public boolean setTanpinSyuseiKb(String tanpin_syusei_kb)
	{
		this.tanpin_syusei_kb = tanpin_syusei_kb;
		return true;
	}
	public String getTanpinSyuseiKb()
	{
		return cutString(this.tanpin_syusei_kb,TANPIN_SYUSEI_KB_MAX_LENGTH);
	}
	public String getTanpinSyuseiKbString()
	{
		return "'" + cutString(this.tanpin_syusei_kb,TANPIN_SYUSEI_KB_MAX_LENGTH) + "'";
	}
	public String getTanpinSyuseiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_syusei_kb,TANPIN_SYUSEI_KB_MAX_LENGTH));
	}


	// tanpin_syohin_gyo_noに対するセッターとゲッターの集合
	public boolean setTanpinSyohinGyoNo(String tanpin_syohin_gyo_no)
	{
		try
		{
			this.tanpin_syohin_gyo_no = Long.parseLong(tanpin_syohin_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTanpinSyohinGyoNo(long tanpin_syohin_gyo_no)
	{
		this.tanpin_syohin_gyo_no = tanpin_syohin_gyo_no;
		return true;
	}
	public long getTanpinSyohinGyoNo()
	{
		return this.tanpin_syohin_gyo_no;
	}
	public String getTanpinSyohinGyoNoString()
	{
		return Long.toString(this.tanpin_syohin_gyo_no);
	}


	// tanpin_sakusei_gyo_noに対するセッターとゲッターの集合
	public boolean setTanpinSakuseiGyoNo(String tanpin_sakusei_gyo_no)
	{
		try
		{
			this.tanpin_sakusei_gyo_no = Long.parseLong(tanpin_sakusei_gyo_no);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTanpinSakuseiGyoNo(long tanpin_sakusei_gyo_no)
	{
		this.tanpin_sakusei_gyo_no = tanpin_sakusei_gyo_no;
		return true;
	}
	public long getTanpinSakuseiGyoNo()
	{
		return this.tanpin_sakusei_gyo_no;
	}
	public String getTanpinSakuseiGyoNoString()
	{
		return Long.toString(this.tanpin_sakusei_gyo_no);
	}


	// tanpin_mst_mainte_fgに対するセッターとゲッターの集合
	public boolean setTanpinMstMainteFg(String tanpin_mst_mainte_fg)
	{
		this.tanpin_mst_mainte_fg = tanpin_mst_mainte_fg;
		return true;
	}
	public String getTanpinMstMainteFg()
	{
		return cutString(this.tanpin_mst_mainte_fg,TANPIN_MST_MAINTE_FG_MAX_LENGTH);
	}
	public String getTanpinMstMainteFgString()
	{
		return "'" + cutString(this.tanpin_mst_mainte_fg,TANPIN_MST_MAINTE_FG_MAX_LENGTH) + "'";
	}
	public String getTanpinMstMainteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_mst_mainte_fg,TANPIN_MST_MAINTE_FG_MAX_LENGTH));
	}


	// tanpin_alarm_make_fgに対するセッターとゲッターの集合
	public boolean setTanpinAlarmMakeFg(String tanpin_alarm_make_fg)
	{
		this.tanpin_alarm_make_fg = tanpin_alarm_make_fg;
		return true;
	}
	public String getTanpinAlarmMakeFg()
	{
		return cutString(this.tanpin_alarm_make_fg,TANPIN_ALARM_MAKE_FG_MAX_LENGTH);
	}
	public String getTanpinAlarmMakeFgString()
	{
		return "'" + cutString(this.tanpin_alarm_make_fg,TANPIN_ALARM_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getTanpinAlarmMakeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_alarm_make_fg,TANPIN_ALARM_MAKE_FG_MAX_LENGTH));
	}


	// tanpin_insert_tsに対するセッターとゲッターの集合
	public boolean setTanpinInsertTs(String tanpin_insert_ts)
	{
		this.tanpin_insert_ts = tanpin_insert_ts;
		return true;
	}
	public String getTanpinInsertTs()
	{
		return cutString(this.tanpin_insert_ts,TANPIN_INSERT_TS_MAX_LENGTH);
	}
	public String getTanpinInsertTsString()
	{
		return "'" + cutString(this.tanpin_insert_ts,TANPIN_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getTanpinInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_insert_ts,TANPIN_INSERT_TS_MAX_LENGTH));
	}


	// tanpin_insert_user_idに対するセッターとゲッターの集合
	public boolean setTanpinInsertUserId(String tanpin_insert_user_id)
	{
		this.tanpin_insert_user_id = tanpin_insert_user_id;
		return true;
	}
	public String getTanpinInsertUserId()
	{
		return cutString(this.tanpin_insert_user_id,TANPIN_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getTanpinInsertUserIdString()
	{
		return "'" + cutString(this.tanpin_insert_user_id,TANPIN_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getTanpinInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_insert_user_id,TANPIN_INSERT_USER_ID_MAX_LENGTH));
	}


	// tanpin_update_tsに対するセッターとゲッターの集合
	public boolean setTanpinUpdateTs(String tanpin_update_ts)
	{
		this.tanpin_update_ts = tanpin_update_ts;
		return true;
	}
	public String getTanpinUpdateTs()
	{
		return cutString(this.tanpin_update_ts,TANPIN_UPDATE_TS_MAX_LENGTH);
	}
	public String getTanpinUpdateTsString()
	{
		return "'" + cutString(this.tanpin_update_ts,TANPIN_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getTanpinUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_update_ts,TANPIN_UPDATE_TS_MAX_LENGTH));
	}


	// tanpin_update_user_idに対するセッターとゲッターの集合
	public boolean setTanpinUpdateUserId(String tanpin_update_user_id)
	{
		this.tanpin_update_user_id = tanpin_update_user_id;
		return true;
	}
	public String getTanpinUpdateUserId()
	{
		return cutString(this.tanpin_update_user_id,TANPIN_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getTanpinUpdateUserIdString()
	{
		return "'" + cutString(this.tanpin_update_user_id,TANPIN_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getTanpinUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_update_user_id,TANPIN_UPDATE_USER_ID_MAX_LENGTH));
	}


	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return cutString(this.sentaku,SENTAKU_MAX_LENGTH);
	}
	public String getSentakuString()
	{
		return "'" + cutString(this.sentaku,SENTAKU_MAX_LENGTH) + "'";
	}
	public String getSentakuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentaku,SENTAKU_MAX_LENGTH));
	}


	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  torikomi_dt = " + getFreTorikomiDtString()
			+ "  excel_file_syubetsu = " + getFreExcelFileSyubetsuString()
			+ "  uketsuke_no = " + getFreUketsukeNoString()
			+ "  uketsuke_seq = " + getFreUketsukeSeqString()
			+ "  bumon_cd = " + getFreBumonCdString()
			+ "  unit_cd = " + getFreUnitCdString()
			+ "  haifu_cd = " + getFreHaifuCdString()
			+ "  subclass_cd = " + getFreSubclassCdString()
			+ "  syohin_cd = " + getFreSyohinCdString()
			+ "  yuko_dt = " + getFreYukoDtString()
			+ "  maker_cd = " + getFreMakerCdString()
			+ "  hinmei_kanji_na = " + getFreHinmeiKanjiNaString()
			+ "  kikaku_kanji_na = " + getFreKikakuKanjiNaString()
			+ "  kikaku_kanji_2_na = " + getFreKikakuKanji2NaString()
			+ "  hinmei_kana_na = " + getFreHinmeiKanaNaString()
			+ "  kikaku_kana_na = " + getFreKikakuKanaNaString()
			+ "  kikaku_kana_2_na = " + getFreKikakuKana2NaString()
			+ "  rec_hinmei_kanji_na = " + getFreRecHinmeiKanjiNaString()
			+ "  rec_hinmei_kana_na = " + getFreRecHinmeiKanaNaString()
			+ "  gentanka_vl = " + getFreGentankaVlString()
			+ "  baitanka_vl = " + getFreBaitankaVlString()
			+ "  plu_send_dt = " + getFrePluSendDtString()
//			+ "  area_cd = " + getFreAreaCdString()
			+ "  siiresaki_cd = " + getFreSiiresakiCdString()
			+ "  ten_siiresaki_kanri_cd = " + getFreTenSiiresakiKanriCdString()
			+ "  siire_hinban_cd = " + getFreSiireHinbanCdString()
			+ "  hanbai_st_dt = " + getFreHanbaiStDtString()
			+ "  hanbai_ed_dt = " + getFreHanbaiEdDtString()
			+ "  ten_hachu_st_dt = " + getFreTenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getFreTenHachuEdDtString()
			+ "  eos_kb = " + getFreEosKbString()
			+ "  honbu_zai_kb = " + getFreHonbuZaiKbString()
			+ "  hachu_tani_na = " + getFreHachuTaniNaString()
			+ "  hachutani_irisu_qt = " + getFreHachutaniIrisuQtString()
			+ "  max_hachutani_qt = " + getFreMaxHachutaniQtString()
			+ "  bland_cd = " + getFreBlandCdString()
			+ "  pb_kb = " + getFrePbKbString()
			+ "  maker_kibo_kakaku_vl = " + getFreMakerKiboKakakuVlString()
			+ "  bin_1_kb = " + getFreBin1KbString()
			+ "  bin_2_kb = " + getFreBin2KbString()
			+ "  yusen_bin_kb = " + getFreYusenBinKbString()
			+ "  fuji_syohin_kb = " + getFreFujiSyohinKbString()
			+ "  pc_kb = " + getFrePcKbString()
			+ "  daisi_no_nb = " + getFreDaisiNoNbString()
			+ "  daicho_tenpo_kb = " + getFreDaichoTenpoKbString()
			+ "  daicho_honbu_kb = " + getFreDaichoHonbuKbString()
			+ "  daicho_siiresaki_kb = " + getFreDaichoSiiresakiKbString()
			+ "  unit_price_tani_kb = " + getFreUnitPriceTaniKbString()
			+ "  unit_price_naiyoryo_qt = " + getFreUnitPriceNaiyoryoQtString()
			+ "  unit_price_kijun_tani_qt = " + getFreUnitPriceKijunTaniQtString()
			+ "  syohi_kigen_dt = " + getFreSyohiKigenDtString()
			+ "  syusei_kb = " + getFreSyuseiKbString()
			+ "  sakusei_gyo_no = " + getFreSakuseiGyoNoString()
			+ "  mst_mainte_fg = " + getFreMstMainteFgString()
			+ "  alarm_make_fg = " + getFreAlarmMakeFgString()
			+ "  insert_ts = " + getFreInsertTsString()
			+ "  insert_user_id = " + getFreInsertUserIdString()
			+ "  update_ts = " + getFreUpdateTsString()
			+ "  update_user_id = " + getFreUpdateUserIdString()
			+ "  torikomi_dt = " + getGroTorikomiDtString()
			+ "  excel_file_syubetsu = " + getGroExcelFileSyubetsuString()
			+ "  uketsuke_no = " + getGroUketsukeNoString()
			+ "  uketsuke_seq = " + getGroUketsukeSeqString()
			+ "  bumon_cd = " + getGroBumonCdString()
			+ "  unit_cd = " + getGroUnitCdString()
			+ "  haifu_cd = " + getGroHaifuCdString()
			+ "  subclass_cd = " + getGroSubclassCdString()
			+ "  syohin_cd = " + getGroSyohinCdString()
			+ "  yuko_dt = " + getGroYukoDtString()
			+ "  maker_cd = " + getGroMakerCdString()
			+ "  hinmei_kanji_na = " + getGroHinmeiKanjiNaString()
			+ "  kikaku_kanji_na = " + getGroKikakuKanjiNaString()
			+ "  kikaku_kanji_2_na = " + getGroKikakuKanji2NaString()
			+ "  hinmei_kana_na = " + getGroHinmeiKanaNaString()
			+ "  kikaku_kana_na = " + getGroKikakuKanaNaString()
			+ "  kikaku_kana_2_na = " + getGroKikakuKana2NaString()
			+ "  rec_hinmei_kanji_na = " + getGroRecHinmeiKanjiNaString()
			+ "  rec_hinmei_kana_na = " + getGroRecHinmeiKanaNaString()
			+ "  gentanka_vl = " + getGroGentankaVlString()
			+ "  baitanka_vl = " + getGroBaitankaVlString()
			+ "  plu_send_dt = " + getGroPluSendDtString()
//			+ "  area_cd = " + getGroAreaCdString()
			+ "  siiresaki_cd = " + getGroSiiresakiCdString()
			+ "  ten_siiresaki_kanri_cd = " + getGroTenSiiresakiKanriCdString()
			+ "  siire_hinban_cd = " + getGroSiireHinbanCdString()
			+ "  hanbai_st_dt = " + getGroHanbaiStDtString()
			+ "  hanbai_ed_dt = " + getGroHanbaiEdDtString()
			+ "  ten_hachu_st_dt = " + getGroTenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getGroTenHachuEdDtString()
			+ "  eos_kb = " + getGroEosKbString()
			+ "  honbu_zai_kb = " + getGroHonbuZaiKbString()
			+ "  hachu_tani_na = " + getGroHachuTaniNaString()
			+ "  hachutani_irisu_qt = " + getGroHachutaniIrisuQtString()
			+ "  max_hachutani_qt = " + getGroMaxHachutaniQtString()
			+ "  bland_cd = " + getGroBlandCdString()
			+ "  pb_kb = " + getGroPbKbString()
			+ "  maker_kibo_kakaku_vl = " + getGroMakerKiboKakakuVlString()
			+ "  fuji_syohin_kb = " + getGroFujiSyohinKbString()
			+ "  pc_kb = " + getGroPcKbString()
			+ "  daisi_no_nb = " + getGroDaisiNoNbString()
			+ "  daicho_tenpo_kb = " + getGroDaichoTenpoKbString()
			+ "  daicho_honbu_kb = " + getGroDaichoHonbuKbString()
			+ "  daicho_siiresaki_kb = " + getGroDaichoSiiresakiKbString()
			+ "  syuzei_hokoku_kb = " + getGroSyuzeiHokokuKbString()
			+ "  unit_price_tani_kb = " + getGroUnitPriceTaniKbString()
			+ "  unit_price_naiyoryo_qt = " + getGroUnitPriceNaiyoryoQtString()
			+ "  unit_price_kijun_tani_qt = " + getGroUnitPriceKijunTaniQtString()
			+ "  syohi_kigen_dt = " + getGroSyohiKigenDtString()
			+ "  syusei_kb = " + getGroSyuseiKbString()
			+ "  sakusei_gyo_no = " + getGroSakuseiGyoNoString()
			+ "  mst_mainte_fg = " + getGroMstMainteFgString()
			+ "  alarm_make_fg = " + getGroAlarmMakeFgString()
			+ "  insert_ts = " + getGroInsertTsString()
			+ "  insert_user_id = " + getGroInsertUserIdString()
			+ "  update_ts = " + getGroUpdateTsString()
			+ "  update_user_id = " + getGroUpdateUserIdString()
			+ "  torikomi_dt = " + getA07TorikomiDtString()
			+ "  excel_file_syubetsu = " + getA07ExcelFileSyubetsuString()
			+ "  uketsuke_no = " + getA07UketsukeNoString()
			+ "  uketsuke_seq = " + getA07UketsukeSeqString()
			+ "  bumon_cd = " + getA07BumonCdString()
			+ "  unit_cd = " + getA07UnitCdString()
			+ "  haifu_cd = " + getA07HaifuCdString()
			+ "  subclass_cd = " + getA07SubclassCdString()
			+ "  syohin_cd = " + getA07SyohinCdString()
			+ "  yuko_dt = " + getA07YukoDtString()
			+ "  maker_cd = " + getA07MakerCdString()
			+ "  pos_cd = " + getA07PosCdString()
			+ "  hinmei_kanji_na = " + getA07HinmeiKanjiNaString()
			+ "  kikaku_kanji_na = " + getA07KikakuKanjiNaString()
			+ "  kikaku_kanji_2_na = " + getA07KikakuKanji2NaString()
			+ "  hinmei_kana_na = " + getA07HinmeiKanaNaString()
			+ "  kikaku_kana_na = " + getA07KikakuKanaNaString()
			+ "  kikaku_kana_2_na = " + getA07KikakuKana2NaString()
			+ "  rec_hinmei_kanji_na = " + getA07RecHinmeiKanjiNaString()
			+ "  rec_hinmei_kana_na = " + getA07RecHinmeiKanaNaString()
			+ "  size_cd = " + getA07SizeCdString()
			+ "  color_cd = " + getA07ColorCdString()
			+ "  gentanka_vl = " + getA07GentankaVlString()
			+ "  baitanka_vl = " + getA07BaitankaVlString()
			+ "  keshi_baika_vl = " + getA07KeshiBaikaVlString()
			+ "  keiyakusu_qt = " + getA07KeiyakusuQtString()
			+ "  tuika_keiyakusu_qt = " + getA07TuikaKeiyakusuQtString()
			+ "  hachutani_irisu_qt = " + getA07HachutaniIrisuQtString()
			+ "  hachu_tani_na = " + getA07HachuTaniNaString()
			+ "  plu_send_dt = " + getA07PluSendDtString()
//			+ "  area_cd = " + getA07AreaCdString()
			+ "  siiresaki_cd = " + getA07SiiresakiCdString()
			+ "  siire_hinban_cd = " + getA07SiireHinbanCdString()
			+ "  hanbai_st_dt = " + getA07HanbaiStDtString()
			+ "  hanbai_ed_dt = " + getA07HanbaiEdDtString()
			+ "  ten_hachu_st_dt = " + getA07TenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getA07TenHachuEdDtString()
			+ "  eos_kb = " + getA07EosKbString()
			+ "  season_cd = " + getA07SeasonCdString()
			+ "  bland_cd = " + getA07BlandCdString()
			+ "  pb_kb = " + getA07PbKbString()
			+ "  yoridori_vl = " + getA07YoridoriVlString()
			+ "  yoridori_qt = " + getA07YoridoriQtString()
			+ "  yoridori_kb = " + getA07YoridoriKbString()
			+ "  tana_no_nb = " + getA07TanaNoNbString()
			+ "  nefuda_kb = " + getA07NefudaKbString()
			+ "  nefuda_ukewatasi_dt = " + getA07NefudaUkewatasiDtString()
			+ "  nefuda_ukewatasi_kb = " + getA07NefudaUkewatasiKbString()
			+ "  fuji_syohin_kb = " + getA07FujiSyohinKbString()
			+ "  pc_kb = " + getA07PcKbString()
			+ "  daisi_no_nb = " + getA07DaisiNoNbString()
			+ "  syusei_kb = " + getA07SyuseiKbString()
			+ "  sakusei_gyo_no = " + getA07SakuseiGyoNoString()
			+ "  mst_mainte_fg = " + getA07MstMainteFgString()
			+ "  alarm_make_fg = " + getA07AlarmMakeFgString()
			+ "  insert_ts = " + getA07InsertTsString()
			+ "  insert_user_id = " + getA07InsertUserIdString()
			+ "  update_ts = " + getA07UpdateTsString()
			+ "  update_user_id = " + getA07UpdateUserIdString()
			+ "  torikomi_dt = " + getA08TorikomiDtString()
			+ "  excel_file_syubetsu = " + getA08ExcelFileSyubetsuString()
			+ "  uketsuke_no = " + getA08UketsukeNoString()
			+ "  uketsuke_seq = " + getA08UketsukeSeqString()
			+ "  bumon_cd = " + getA08BumonCdString()
			+ "  unit_cd = " + getA08UnitCdString()
			+ "  haifu_cd = " + getA08HaifuCdString()
			+ "  subclass_cd = " + getA08SubclassCdString()
			+ "  syohin_cd = " + getA08SyohinCdString()
			+ "  yuko_dt = " + getA08YukoDtString()
			+ "  hinmei_kanji_na = " + getA08HinmeiKanjiNaString()
			+ "  hinmei_kana_na = " + getA08HinmeiKanaNaString()
			+ "  size_cd = " + getA08SizeCdString()
			+ "  color_cd = " + getA08ColorCdString()
			+ "  gentanka_vl = " + getA08GentankaVlString()
			+ "  baitanka_vl = " + getA08BaitankaVlString()
			+ "  keshi_baika_vl = " + getA08KeshiBaikaVlString()
			+ "  tokubetu_genka_vl = " + getA08TokubetuGenkaVlString()
			+ "  keiyakusu_qt = " + getA08KeiyakusuQtString()
			+ "  tuika_keiyakusu_qt = " + getA08TuikaKeiyakusuQtString()
//			+ "  area_cd = " + getA08AreaCdString()
			+ "  siiresaki_cd = " + getA08SiiresakiCdString()
			+ "  siire_hinban_cd = " + getA08SiireHinbanCdString()
			+ "  hanbai_st_dt = " + getA08HanbaiStDtString()
			+ "  hanbai_ed_dt = " + getA08HanbaiEdDtString()
			+ "  ten_hachu_st_dt = " + getA08TenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getA08TenHachuEdDtString()
			+ "  eos_kb = " + getA08EosKbString()
			+ "  season_cd = " + getA08SeasonCdString()
			+ "  bland_cd = " + getA08BlandCdString()
			+ "  pb_kb = " + getA08PbKbString()
			+ "  yoridori_vl = " + getA08YoridoriVlString()
			+ "  yoridori_qt = " + getA08YoridoriQtString()
			+ "  yoridori_kb = " + getA08YoridoriKbString()
			+ "  nefuda_kb = " + getA08NefudaKbString()
			+ "  nefuda_ukewatasi_dt = " + getA08NefudaUkewatasiDtString()
			+ "  nefuda_ukewatasi_kb = " + getA08NefudaUkewatasiKbString()
			+ "  fuji_syohin_kb = " + getA08FujiSyohinKbString()
			+ "  sozai_cd = " + getA08SozaiCdString()
			+ "  oriami_cd = " + getA08OriamiCdString()
			+ "  sode_cd = " + getA08SodeCdString()
			+ "  age_cd = " + getA08AgeCdString()
			+ "  pattern_cd = " + getA08PatternCdString()
			+ "  syusei_kb = " + getA08SyuseiKbString()
			+ "  sakusei_gyo_no = " + getA08SakuseiGyoNoString()
			+ "  mst_mainte_fg = " + getA08MstMainteFgString()
			+ "  alarm_make_fg = " + getA08AlarmMakeFgString()
			+ "  insert_ts = " + getA08InsertTsString()
			+ "  insert_user_id = " + getA08InsertUserIdString()
			+ "  update_ts = " + getA08UpdateTsString()
			+ "  update_user_id = " + getA08UpdateUserIdString()
			+ "  torikomi_dt = " + getTsTorikomiDtString()
			+ "  excel_file_syubetsu = " + getTsExcelFileSyubetsuString()
			+ "  uketsuke_no = " + getTsUketsukeNoString()
			+ "  excel_file_na = " + getTsExcelFileNaString()
//			+ "  area_cd = " + getTsAreaCdString()
			+ "  siiresaki_cd = " + getTsSiiresakiCdString()
			+ "  upload_comment_tx = " + getTsUploadCommentTxString()
			+ "  toroku_syonin_fg = " + getTsTorokuSyoninFgString()
			+ "  toroku_syonin_ts = " + getTsTorokuSyoninTsString()
			+ "  by_no = " + getTsByNoString()
			+ "  syonin_comment_tx = " + getTsSyoninCommentTxString()
			+ "  excel_syohin_kb = " + getTsExcelSyohinKbString()
			+ "  excel_tanpin_kb = " + getTsExcelTanpinKbString()
			+ "  excel_reigai_kb = " + getTsExcelReigaiKbString()
			+ "  excel_syokai_kb = " + getTsExcelSyokaiKbString()
			+ "  min_neire_rt = " + getTsMinNeireRtString()
			+ "  max_neire_rt = " + getTsMaxNeireRtString()
			+ "  max_uritanka_vl = " + getTsMaxUritankaVlString()
			+ "  syori_jyotai_fg = " + getTsSyoriJyotaiFgString()
			+ "  kakunin_fg = " + getTsKakuninFgString()
			+ "  delete_fg = " + getTsDeleteFgString()
			+ "  insert_ts = " + getTsInsertTsString()
			+ "  insert_user_id = " + getTsInsertUserIdString()
			+ "  update_ts = " + getTsUpdateTsString()
			+ "  update_user_id = " + getTsUpdateUserIdString()
			+ "  torikomi_dt = " + getSyoTorikomiDtString()
			+ "  excel_file_syubetsu = " + getSyoExcelFileSyubetsuString()
			+ "  uketsuke_no = " + getSyoUketsukeNoString()
			+ "  uketsuke_seq = " + getSyoUketsukeSeqString()
			+ "  hachuno_cd = " + getSyoHachunoCdString()
			+ "  bumon_cd = " + getSyoBumonCdString()
			+ "  syohin_cd = " + getSyoSyohinCdString()
			+ "  tenpo_cd = " + getSyoTenpoCdString()
			+ "  theme_cd = " + getSyoThemeCdString()
			+ "  suryo_qt = " + getSyoSuryoQtString()
			+ "  hachutani_qt = " + getSyoHachutaniQtString()
			+ "  gentanka_vl = " + getSyoGentankaVlString()
			+ "  baitanka_vl = " + getSyoBaitankaVlString()
			+ "  hatyu_dt = " + getSyoHatyuDtString()
			+ "  nohin_dt = " + getSyoNohinDtString()
			+ "  hanbai_st_dt = " + getSyoHanbaiStDtString()
			+ "  hanbai_ed_dt = " + getSyoHanbaiEdDtString()
			+ "  syusei_kb = " + getSyoSyuseiKbString()
			+ "  syohin_gyo_no = " + getSyoSyohinGyoNoString()
			+ "  sakusei_gyo_no = " + getSyoSakuseiGyoNoString()
			+ "  mst_mainte_fg = " + getSyoMstMainteFgString()
			+ "  alarm_make_fg = " + getSyoAlarmMakeFgString()
			+ "  insert_ts = " + getSyoInsertTsString()
			+ "  insert_user_id = " + getSyoInsertUserIdString()
			+ "  update_ts = " + getSyoUpdateTsString()
			+ "  update_user_id = " + getSyoUpdateUserIdString()
			+ "  torikomi_dt = " + getReigaiTorikomiDtString()
			+ "  excel_file_syubetsu = " + getReigaiExcelFileSyubetsuString()
			+ "  uketsuke_no = " + getReigaiUketsukeNoString()
			+ "  uketsuke_seq = " + getReigaiUketsukeSeqString()
			+ "  bumon_cd = " + getReigaiBumonCdString()
			+ "  syohin_cd = " + getReigaiSyohinCdString()
			+ "  tenpo_cd = " + getReigaiTenpoCdString()
			+ "  yuko_dt = " + getReigaiYukoDtString()
			+ "  gentanka_vl = " + getReigaiGentankaVlString()
			+ "  baitanka_vl = " + getReigaiBaitankaVlString()
			+ "  max_hachutani_qt = " + getReigaiMaxHachutaniQtString()
			+ "  eos_kb = " + getReigaiEosKbString()
//			+ "  area_cd = " + getReigaiAreaCdString()
			+ "  siiresaki_cd = " + getReigaiSiiresakiCdString()
			+ "  ten_hachu_st_dt = " + getReigaiTenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getReigaiTenHachuEdDtString()
			+ "  buturyu_kb = " + getReigaiButuryuKbString()
			+ "  bin_1_kb = " + getReigaiBin1KbString()
			+ "  bin_2_kb = " + getReigaiBin2KbString()
			+ "  yusen_bin_kb = " + getReigaiYusenBinKbString()
			+ "  syusei_kb = " + getReigaiSyuseiKbString()
			+ "  syohin_gyo_no = " + getReigaiSyohinGyoNoString()
			+ "  sakusei_gyo_no = " + getReigaiSakuseiGyoNoString()
			+ "  mst_mainte_fg = " + getReigaiMstMainteFgString()
			+ "  alarm_make_fg = " + getReigaiAlarmMakeFgString()
			+ "  insert_ts = " + getReigaiInsertTsString()
			+ "  insert_user_id = " + getReigaiInsertUserIdString()
			+ "  update_ts = " + getReigaiUpdateTsString()
			+ "  update_user_id = " + getReigaiUpdateUserIdString()
			+ "  torikomi_dt = " + getTanpinTorikomiDtString()
			+ "  excel_file_syubetsu = " + getTanpinExcelFileSyubetsuString()
			+ "  uketsuke_no = " + getTanpinUketsukeNoString()
			+ "  uketsuke_seq = " + getTanpinUketsukeSeqString()
			+ "  bumon_cd = " + getTanpinBumonCdString()
			+ "  syohin_cd = " + getTanpinSyohinCdString()
			+ "  tenpo_cd = " + getTanpinTenpoCdString()
			+ "  donyu_dt = " + getTanpinDonyuDtString()
			+ "  syusei_kb = " + getTanpinSyuseiKbString()
			+ "  syohin_gyo_no = " + getTanpinSyohinGyoNoString()
			+ "  sakusei_gyo_no = " + getTanpinSakuseiGyoNoString()
			+ "  mst_mainte_fg = " + getTanpinMstMainteFgString()
			+ "  alarm_make_fg = " + getTanpinAlarmMakeFgString()
			+ "  insert_ts = " + getTanpinInsertTsString()
			+ "  insert_user_id = " + getTanpinInsertUserIdString()
			+ "  update_ts = " + getTanpinUpdateTsString()
			+ "  update_user_id = " + getTanpinUpdateUserIdString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst380401_ExcelUploadBean コピー後のクラス
	 */
	public mst380401_ExcelUploadBean createClone()
	{
		mst380401_ExcelUploadBean bean = new mst380401_ExcelUploadBean();
		bean.setFreTorikomiDt(this.fre_torikomi_dt);
		bean.setFreExcelFileSyubetsu(this.fre_excel_file_syubetsu);
		bean.setFreUketsukeNo(this.fre_uketsuke_no);
		bean.setFreUketsukeSeq(this.fre_uketsuke_seq);
		bean.setFreBumonCd(this.fre_bumon_cd);
		bean.setFreUnitCd(this.fre_unit_cd);
		bean.setFreHaifuCd(this.fre_haifu_cd);
		bean.setFreSubclassCd(this.fre_subclass_cd);
		bean.setFreSyohinCd(this.fre_syohin_cd);
		bean.setFreYukoDt(this.fre_yuko_dt);
		bean.setFreMakerCd(this.fre_maker_cd);
		bean.setFreHinmeiKanjiNa(this.fre_hinmei_kanji_na);
		bean.setFreKikakuKanjiNa(this.fre_kikaku_kanji_na);
		bean.setFreKikakuKanji2Na(this.fre_kikaku_kanji_2_na);
		bean.setFreHinmeiKanaNa(this.fre_hinmei_kana_na);
		bean.setFreKikakuKanaNa(this.fre_kikaku_kana_na);
		bean.setFreKikakuKana2Na(this.fre_kikaku_kana_2_na);
		bean.setFreRecHinmeiKanjiNa(this.fre_rec_hinmei_kanji_na);
		bean.setFreRecHinmeiKanaNa(this.fre_rec_hinmei_kana_na);
		bean.setFreGentankaVl(this.fre_gentanka_vl);
		bean.setFreBaitankaVl(this.fre_baitanka_vl);
		bean.setFrePluSendDt(this.fre_plu_send_dt);
//		bean.setFreAreaCd(this.fre_area_cd);
		bean.setFreSiiresakiCd(this.fre_siiresaki_cd);
		bean.setFreTenSiiresakiKanriCd(this.fre_ten_siiresaki_kanri_cd);
		bean.setFreSiireHinbanCd(this.fre_siire_hinban_cd);
		bean.setFreHanbaiStDt(this.fre_hanbai_st_dt);
		bean.setFreHanbaiEdDt(this.fre_hanbai_ed_dt);
		bean.setFreTenHachuStDt(this.fre_ten_hachu_st_dt);
		bean.setFreTenHachuEdDt(this.fre_ten_hachu_ed_dt);
		bean.setFreEosKb(this.fre_eos_kb);
		bean.setFreHonbuZaiKb(this.fre_honbu_zai_kb);
		bean.setFreHachuTaniNa(this.fre_hachu_tani_na);
		bean.setFreHachutaniIrisuQt(this.fre_hachutani_irisu_qt);
		bean.setFreMaxHachutaniQt(this.fre_max_hachutani_qt);
		bean.setFreBlandCd(this.fre_bland_cd);
		bean.setFrePbKb(this.fre_pb_kb);
		bean.setFreMakerKiboKakakuVl(this.fre_maker_kibo_kakaku_vl);
		bean.setFreBin1Kb(this.fre_bin_1_kb);
		bean.setFreBin2Kb(this.fre_bin_2_kb);
		bean.setFreYusenBinKb(this.fre_yusen_bin_kb);
		bean.setFreFujiSyohinKb(this.fre_fuji_syohin_kb);
		bean.setFrePcKb(this.fre_pc_kb);
		bean.setFreDaisiNoNb(this.fre_daisi_no_nb);
		bean.setFreDaichoTenpoKb(this.fre_daicho_tenpo_kb);
		bean.setFreDaichoHonbuKb(this.fre_daicho_honbu_kb);
		bean.setFreDaichoSiiresakiKb(this.fre_daicho_siiresaki_kb);
		bean.setFreUnitPriceTaniKb(this.fre_unit_price_tani_kb);
		bean.setFreUnitPriceNaiyoryoQt(this.fre_unit_price_naiyoryo_qt);
		bean.setFreUnitPriceKijunTaniQt(this.fre_unit_price_kijun_tani_qt);
		bean.setFreSyohiKigenDt(this.fre_syohi_kigen_dt);
		bean.setFreSyuseiKb(this.fre_syusei_kb);
		bean.setFreSakuseiGyoNo(this.fre_sakusei_gyo_no);
		bean.setFreMstMainteFg(this.fre_mst_mainte_fg);
		bean.setFreAlarmMakeFg(this.fre_alarm_make_fg);
		bean.setFreInsertTs(this.fre_insert_ts);
		bean.setFreInsertUserId(this.fre_insert_user_id);
		bean.setFreUpdateTs(this.fre_update_ts);
		bean.setFreUpdateUserId(this.fre_update_user_id);
		bean.setGroTorikomiDt(this.gro_torikomi_dt);
		bean.setGroExcelFileSyubetsu(this.gro_excel_file_syubetsu);
		bean.setGroUketsukeNo(this.gro_uketsuke_no);
		bean.setGroUketsukeSeq(this.gro_uketsuke_seq);
		bean.setGroBumonCd(this.gro_bumon_cd);
		bean.setGroUnitCd(this.gro_unit_cd);
		bean.setGroHaifuCd(this.gro_haifu_cd);
		bean.setGroSubclassCd(this.gro_subclass_cd);
		bean.setGroSyohinCd(this.gro_syohin_cd);
		bean.setGroYukoDt(this.gro_yuko_dt);
		bean.setGroMakerCd(this.gro_maker_cd);
		bean.setGroHinmeiKanjiNa(this.gro_hinmei_kanji_na);
		bean.setGroKikakuKanjiNa(this.gro_kikaku_kanji_na);
		bean.setGroKikakuKanji2Na(this.gro_kikaku_kanji_2_na);
		bean.setGroHinmeiKanaNa(this.gro_hinmei_kana_na);
		bean.setGroKikakuKanaNa(this.gro_kikaku_kana_na);
		bean.setGroKikakuKana2Na(this.gro_kikaku_kana_2_na);
		bean.setGroRecHinmeiKanjiNa(this.gro_rec_hinmei_kanji_na);
		bean.setGroRecHinmeiKanaNa(this.gro_rec_hinmei_kana_na);
		bean.setGroGentankaVl(this.gro_gentanka_vl);
		bean.setGroBaitankaVl(this.gro_baitanka_vl);
		bean.setGroPluSendDt(this.gro_plu_send_dt);
//		bean.setGroAreaCd(this.gro_area_cd);
		bean.setGroSiiresakiCd(this.gro_siiresaki_cd);
		bean.setGroTenSiiresakiKanriCd(this.gro_ten_siiresaki_kanri_cd);
		bean.setGroSiireHinbanCd(this.gro_siire_hinban_cd);
		bean.setGroHanbaiStDt(this.gro_hanbai_st_dt);
		bean.setGroHanbaiEdDt(this.gro_hanbai_ed_dt);
		bean.setGroTenHachuStDt(this.gro_ten_hachu_st_dt);
		bean.setGroTenHachuEdDt(this.gro_ten_hachu_ed_dt);
		bean.setGroEosKb(this.gro_eos_kb);
		bean.setGroHonbuZaiKb(this.gro_honbu_zai_kb);
		bean.setGroHachuTaniNa(this.gro_hachu_tani_na);
		bean.setGroHachutaniIrisuQt(this.gro_hachutani_irisu_qt);
		bean.setGroMaxHachutaniQt(this.gro_max_hachutani_qt);
		bean.setGroBlandCd(this.gro_bland_cd);
		bean.setGroPbKb(this.gro_pb_kb);
		bean.setGroMakerKiboKakakuVl(this.gro_maker_kibo_kakaku_vl);
		bean.setGroFujiSyohinKb(this.gro_fuji_syohin_kb);
		bean.setGroPcKb(this.gro_pc_kb);
		bean.setGroDaisiNoNb(this.gro_daisi_no_nb);
		bean.setGroDaichoTenpoKb(this.gro_daicho_tenpo_kb);
		bean.setGroDaichoHonbuKb(this.gro_daicho_honbu_kb);
		bean.setGroDaichoSiiresakiKb(this.gro_daicho_siiresaki_kb);
		bean.setGroSyuzeiHokokuKb(this.gro_syuzei_hokoku_kb);
		bean.setGroUnitPriceTaniKb(this.gro_unit_price_tani_kb);
		bean.setGroUnitPriceNaiyoryoQt(this.gro_unit_price_naiyoryo_qt);
		bean.setGroUnitPriceKijunTaniQt(this.gro_unit_price_kijun_tani_qt);
		bean.setGroSyohiKigenDt(this.gro_syohi_kigen_dt);
		bean.setGroSyuseiKb(this.gro_syusei_kb);
		bean.setGroSakuseiGyoNo(this.gro_sakusei_gyo_no);
		bean.setGroMstMainteFg(this.gro_mst_mainte_fg);
		bean.setGroAlarmMakeFg(this.gro_alarm_make_fg);
		bean.setGroInsertTs(this.gro_insert_ts);
		bean.setGroInsertUserId(this.gro_insert_user_id);
		bean.setGroUpdateTs(this.gro_update_ts);
		bean.setGroUpdateUserId(this.gro_update_user_id);
		bean.setA07TorikomiDt(this.a07_torikomi_dt);
		bean.setA07ExcelFileSyubetsu(this.a07_excel_file_syubetsu);
		bean.setA07UketsukeNo(this.a07_uketsuke_no);
		bean.setA07UketsukeSeq(this.a07_uketsuke_seq);
		bean.setA07BumonCd(this.a07_bumon_cd);
		bean.setA07UnitCd(this.a07_unit_cd);
		bean.setA07HaifuCd(this.a07_haifu_cd);
		bean.setA07SubclassCd(this.a07_subclass_cd);
		bean.setA07SyohinCd(this.a07_syohin_cd);
		bean.setA07YukoDt(this.a07_yuko_dt);
		bean.setA07MakerCd(this.a07_maker_cd);
		bean.setA07PosCd(this.a07_pos_cd);
		bean.setA07HinmeiKanjiNa(this.a07_hinmei_kanji_na);
		bean.setA07KikakuKanjiNa(this.a07_kikaku_kanji_na);
		bean.setA07KikakuKanji2Na(this.a07_kikaku_kanji_2_na);
		bean.setA07HinmeiKanaNa(this.a07_hinmei_kana_na);
		bean.setA07KikakuKanaNa(this.a07_kikaku_kana_na);
		bean.setA07KikakuKana2Na(this.a07_kikaku_kana_2_na);
		bean.setA07RecHinmeiKanjiNa(this.a07_rec_hinmei_kanji_na);
		bean.setA07RecHinmeiKanaNa(this.a07_rec_hinmei_kana_na);
		bean.setA07SizeCd(this.a07_size_cd);
		bean.setA07ColorCd(this.a07_color_cd);
		bean.setA07GentankaVl(this.a07_gentanka_vl);
		bean.setA07BaitankaVl(this.a07_baitanka_vl);
		bean.setA07KeshiBaikaVl(this.a07_keshi_baika_vl);
		bean.setA07KeiyakusuQt(this.a07_keiyakusu_qt);
		bean.setA07TuikaKeiyakusuQt(this.a07_tuika_keiyakusu_qt);
		bean.setA07HachutaniIrisuQt(this.a07_hachutani_irisu_qt);
		bean.setA07HachuTaniNa(this.a07_hachu_tani_na);
		bean.setA07PluSendDt(this.a07_plu_send_dt);
//		bean.setA07AreaCd(this.a07_area_cd);
		bean.setA07SiiresakiCd(this.a07_siiresaki_cd);
		bean.setA07SiireHinbanCd(this.a07_siire_hinban_cd);
		bean.setA07HanbaiStDt(this.a07_hanbai_st_dt);
		bean.setA07HanbaiEdDt(this.a07_hanbai_ed_dt);
		bean.setA07TenHachuStDt(this.a07_ten_hachu_st_dt);
		bean.setA07TenHachuEdDt(this.a07_ten_hachu_ed_dt);
		bean.setA07EosKb(this.a07_eos_kb);
		bean.setA07SeasonCd(this.a07_season_cd);
		bean.setA07BlandCd(this.a07_bland_cd);
		bean.setA07PbKb(this.a07_pb_kb);
		bean.setA07YoridoriVl(this.a07_yoridori_vl);
		bean.setA07YoridoriQt(this.a07_yoridori_qt);
		bean.setA07YoridoriKb(this.a07_yoridori_kb);
		bean.setA07TanaNoNb(this.a07_tana_no_nb);
		bean.setA07NefudaKb(this.a07_nefuda_kb);
		bean.setA07NefudaUkewatasiDt(this.a07_nefuda_ukewatasi_dt);
		bean.setA07NefudaUkewatasiKb(this.a07_nefuda_ukewatasi_kb);
		bean.setA07FujiSyohinKb(this.a07_fuji_syohin_kb);
		bean.setA07PcKb(this.a07_pc_kb);
		bean.setA07DaisiNoNb(this.a07_daisi_no_nb);
		bean.setA07SyuseiKb(this.a07_syusei_kb);
		bean.setA07SakuseiGyoNo(this.a07_sakusei_gyo_no);
		bean.setA07MstMainteFg(this.a07_mst_mainte_fg);
		bean.setA07AlarmMakeFg(this.a07_alarm_make_fg);
		bean.setA07InsertTs(this.a07_insert_ts);
		bean.setA07InsertUserId(this.a07_insert_user_id);
		bean.setA07UpdateTs(this.a07_update_ts);
		bean.setA07UpdateUserId(this.a07_update_user_id);
		bean.setA08TorikomiDt(this.a08_torikomi_dt);
		bean.setA08ExcelFileSyubetsu(this.a08_excel_file_syubetsu);
		bean.setA08UketsukeNo(this.a08_uketsuke_no);
		bean.setA08UketsukeSeq(this.a08_uketsuke_seq);
		bean.setA08BumonCd(this.a08_bumon_cd);
		bean.setA08UnitCd(this.a08_unit_cd);
		bean.setA08HaifuCd(this.a08_haifu_cd);
		bean.setA08SubclassCd(this.a08_subclass_cd);
		bean.setA08SyohinCd(this.a08_syohin_cd);
		bean.setA08YukoDt(this.a08_yuko_dt);
		bean.setA08HinmeiKanjiNa(this.a08_hinmei_kanji_na);
		bean.setA08HinmeiKanaNa(this.a08_hinmei_kana_na);
		bean.setA08SizeCd(this.a08_size_cd);
		bean.setA08ColorCd(this.a08_color_cd);
		bean.setA08GentankaVl(this.a08_gentanka_vl);
		bean.setA08BaitankaVl(this.a08_baitanka_vl);
		bean.setA08KeshiBaikaVl(this.a08_keshi_baika_vl);
		bean.setA08TokubetuGenkaVl(this.a08_tokubetu_genka_vl);
		bean.setA08KeiyakusuQt(this.a08_keiyakusu_qt);
		bean.setA08TuikaKeiyakusuQt(this.a08_tuika_keiyakusu_qt);
//		bean.setA08AreaCd(this.a08_area_cd);
		bean.setA08SiiresakiCd(this.a08_siiresaki_cd);
		bean.setA08SiireHinbanCd(this.a08_siire_hinban_cd);
		bean.setA08HanbaiStDt(this.a08_hanbai_st_dt);
		bean.setA08HanbaiEdDt(this.a08_hanbai_ed_dt);
		bean.setA08TenHachuStDt(this.a08_ten_hachu_st_dt);
		bean.setA08TenHachuEdDt(this.a08_ten_hachu_ed_dt);
		bean.setA08EosKb(this.a08_eos_kb);
		bean.setA08SeasonCd(this.a08_season_cd);
		bean.setA08BlandCd(this.a08_bland_cd);
		bean.setA08PbKb(this.a08_pb_kb);
		bean.setA08YoridoriVl(this.a08_yoridori_vl);
		bean.setA08YoridoriQt(this.a08_yoridori_qt);
		bean.setA08YoridoriKb(this.a08_yoridori_kb);
		bean.setA08NefudaKb(this.a08_nefuda_kb);
		bean.setA08NefudaUkewatasiDt(this.a08_nefuda_ukewatasi_dt);
		bean.setA08NefudaUkewatasiKb(this.a08_nefuda_ukewatasi_kb);
		bean.setA08FujiSyohinKb(this.a08_fuji_syohin_kb);
		bean.setA08SozaiCd(this.a08_sozai_cd);
		bean.setA08OriamiCd(this.a08_oriami_cd);
		bean.setA08SodeCd(this.a08_sode_cd);
		bean.setA08AgeCd(this.a08_age_cd);
		bean.setA08PatternCd(this.a08_pattern_cd);
		bean.setA08SyuseiKb(this.a08_syusei_kb);
		bean.setA08SakuseiGyoNo(this.a08_sakusei_gyo_no);
		bean.setA08MstMainteFg(this.a08_mst_mainte_fg);
		bean.setA08AlarmMakeFg(this.a08_alarm_make_fg);
		bean.setA08InsertTs(this.a08_insert_ts);
		bean.setA08InsertUserId(this.a08_insert_user_id);
		bean.setA08UpdateTs(this.a08_update_ts);
		bean.setA08UpdateUserId(this.a08_update_user_id);
		bean.setTsTorikomiDt(this.ts_torikomi_dt);
		bean.setTsExcelFileSyubetsu(this.ts_excel_file_syubetsu);
		bean.setTsUketsukeNo(this.ts_uketsuke_no);
		bean.setTsExcelFileNa(this.ts_excel_file_na);
//		bean.setTsAreaCd(this.ts_area_cd);
		bean.setTsSiiresakiCd(this.ts_siiresaki_cd);
		bean.setTsUploadCommentTx(this.ts_upload_comment_tx);
		bean.setTsTorokuSyoninFg(this.ts_toroku_syonin_fg);
		bean.setTsTorokuSyoninTs(this.ts_toroku_syonin_ts);
		bean.setTsByNo(this.ts_by_no);
		bean.setTsSyoninCommentTx(this.ts_syonin_comment_tx);
		bean.setTsExcelSyohinKb(this.ts_excel_syohin_kb);
		bean.setTsExcelTanpinKb(this.ts_excel_tanpin_kb);
		bean.setTsExcelReigaiKb(this.ts_excel_reigai_kb);
		bean.setTsExcelSyokaiKb(this.ts_excel_syokai_kb);
		bean.setTsMinNeireRt(this.ts_min_neire_rt);
		bean.setTsMaxNeireRt(this.ts_max_neire_rt);
		bean.setTsMaxUritankaVl(this.ts_max_uritanka_vl);
		bean.setTsSyoriJyotaiFg(this.ts_syori_jyotai_fg);
		bean.setTsKakuninFg(this.ts_kakunin_fg);
		bean.setTsDeleteFg(this.ts_delete_fg);
		bean.setTsInsertTs(this.ts_insert_ts);
		bean.setTsInsertUserId(this.ts_insert_user_id);
		bean.setTsUpdateTs(this.ts_update_ts);
		bean.setTsUpdateUserId(this.ts_update_user_id);
		bean.setSyoTorikomiDt(this.syo_torikomi_dt);
		bean.setSyoExcelFileSyubetsu(this.syo_excel_file_syubetsu);
		bean.setSyoUketsukeNo(this.syo_uketsuke_no);
		bean.setSyoUketsukeSeq(this.syo_uketsuke_seq);
		bean.setSyoHachunoCd(this.syo_hachuno_cd);
		bean.setSyoBumonCd(this.syo_bumon_cd);
		bean.setSyoSyohinCd(this.syo_syohin_cd);
		bean.setSyoTenpoCd(this.syo_tenpo_cd);
		bean.setSyoThemeCd(this.syo_theme_cd);
		bean.setSyoSuryoQt(this.syo_suryo_qt);
		bean.setSyoHachutaniQt(this.syo_hachutani_qt);
		bean.setSyoGentankaVl(this.syo_gentanka_vl);
		bean.setSyoBaitankaVl(this.syo_baitanka_vl);
		bean.setSyoHatyuDt(this.syo_hatyu_dt);
		bean.setSyoNohinDt(this.syo_nohin_dt);
		bean.setSyoHanbaiStDt(this.syo_hanbai_st_dt);
		bean.setSyoHanbaiEdDt(this.syo_hanbai_ed_dt);
		bean.setSyoSyuseiKb(this.syo_syusei_kb);
		bean.setSyoSyohinGyoNo(this.syo_syohin_gyo_no);
		bean.setSyoSakuseiGyoNo(this.syo_sakusei_gyo_no);
		bean.setSyoMstMainteFg(this.syo_mst_mainte_fg);
		bean.setSyoAlarmMakeFg(this.syo_alarm_make_fg);
		bean.setSyoInsertTs(this.syo_insert_ts);
		bean.setSyoInsertUserId(this.syo_insert_user_id);
		bean.setSyoUpdateTs(this.syo_update_ts);
		bean.setSyoUpdateUserId(this.syo_update_user_id);
		bean.setReigaiTorikomiDt(this.reigai_torikomi_dt);
		bean.setReigaiExcelFileSyubetsu(this.reigai_excel_file_syubetsu);
		bean.setReigaiUketsukeNo(this.reigai_uketsuke_no);
		bean.setReigaiUketsukeSeq(this.reigai_uketsuke_seq);
		bean.setReigaiBumonCd(this.reigai_bumon_cd);
		bean.setReigaiSyohinCd(this.reigai_syohin_cd);
		bean.setReigaiTenpoCd(this.reigai_tenpo_cd);
		bean.setReigaiYukoDt(this.reigai_yuko_dt);
		bean.setReigaiGentankaVl(this.reigai_gentanka_vl);
		bean.setReigaiBaitankaVl(this.reigai_baitanka_vl);
		bean.setReigaiMaxHachutaniQt(this.reigai_max_hachutani_qt);
		bean.setReigaiEosKb(this.reigai_eos_kb);
//		bean.setReigaiAreaCd(this.reigai_area_cd);
		bean.setReigaiSiiresakiCd(this.reigai_siiresaki_cd);
		bean.setReigaiTenHachuStDt(this.reigai_ten_hachu_st_dt);
		bean.setReigaiTenHachuEdDt(this.reigai_ten_hachu_ed_dt);
		bean.setReigaiButuryuKb(this.reigai_buturyu_kb);
		bean.setReigaiBin1Kb(this.reigai_bin_1_kb);
		bean.setReigaiBin2Kb(this.reigai_bin_2_kb);
		bean.setReigaiYusenBinKb(this.reigai_yusen_bin_kb);
		bean.setReigaiSyuseiKb(this.reigai_syusei_kb);
		bean.setReigaiSyohinGyoNo(this.reigai_syohin_gyo_no);
		bean.setReigaiSakuseiGyoNo(this.reigai_sakusei_gyo_no);
		bean.setReigaiMstMainteFg(this.reigai_mst_mainte_fg);
		bean.setReigaiAlarmMakeFg(this.reigai_alarm_make_fg);
		bean.setReigaiInsertTs(this.reigai_insert_ts);
		bean.setReigaiInsertUserId(this.reigai_insert_user_id);
		bean.setReigaiUpdateTs(this.reigai_update_ts);
		bean.setReigaiUpdateUserId(this.reigai_update_user_id);
		bean.setTanpinTorikomiDt(this.tanpin_torikomi_dt);
		bean.setTanpinExcelFileSyubetsu(this.tanpin_excel_file_syubetsu);
		bean.setTanpinUketsukeNo(this.tanpin_uketsuke_no);
		bean.setTanpinUketsukeSeq(this.tanpin_uketsuke_seq);
		bean.setTanpinBumonCd(this.tanpin_bumon_cd);
		bean.setTanpinSyohinCd(this.tanpin_syohin_cd);
		bean.setTanpinTenpoCd(this.tanpin_tenpo_cd);
		bean.setTanpinDonyuDt(this.tanpin_donyu_dt);
		bean.setTanpinSyuseiKb(this.tanpin_syusei_kb);
		bean.setTanpinSyohinGyoNo(this.tanpin_syohin_gyo_no);
		bean.setTanpinSakuseiGyoNo(this.tanpin_sakusei_gyo_no);
		bean.setTanpinMstMainteFg(this.tanpin_mst_mainte_fg);
		bean.setTanpinAlarmMakeFg(this.tanpin_alarm_make_fg);
		bean.setTanpinInsertTs(this.tanpin_insert_ts);
		bean.setTanpinInsertUserId(this.tanpin_insert_user_id);
		bean.setTanpinUpdateTs(this.tanpin_update_ts);
		bean.setTanpinUpdateUserId(this.tanpin_update_user_id);
		
		
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
		if( !( o instanceof mst380401_ExcelUploadBean ) ) return false;
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
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
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
