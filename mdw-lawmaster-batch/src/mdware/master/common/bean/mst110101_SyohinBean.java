/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/04)初版作成
 * @version 1.1(2005/05/11)新ER対応
 * 　　　　　追加…発注商品区分(hacyu_syohin_kb)、システム区分(system_kb)、
 * 　　　　　　　　マスタ使用不可区分(mst_siyofuka_kb)、税区分(zei_kb)、販売期間区分(hanbai_kikan_kb)
 * 　　　　　削除…店配送先管理コード(ten_haiso_kanri_cd)
 * 　　　　　修正…仕入品番(siire_hinban_cd)⇒13桁→15桁
 * 　　　　　　　　代表配送先コード(daihyo_haiso_cd)⇒5桁→4桁
 * 　　　　　　　　POSレシート品名(漢字)(rec_hinmei_kanji_na)⇒30桁→20桁
 * 　　　　　　　　POSレシート品名(カナ)(rec_hinmei_kana_na)⇒30桁→20桁
 * 　　　　　　　　酒税報告区分(syuzei_hokoku_kb)⇒1桁→2桁
 * 　　　　　　　　入荷商品コード(nyuka_syohin_cd)⇒3件→13桁
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;


/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/04)初版作成
 * @version 1.1(2005/05/11)新ER対応
 * 　　　　　追加…発注商品区分(hacyu_syohin_kb)、システム区分(system_kb)、タグ発行数量(tag_hako_qt)、
 * 　　　　　　　　マスタ使用不可区分(mst_siyofuka_kb)、税区分(zei_kb)、販売期間区分(hanbai_kikan_kb)
 * 　　　　　削除…店配送先管理コード(ten_haiso_kanri_cd)
 * 　　　　　修正…仕入品番(siire_hinban_cd)⇒13桁→15桁
 * 　　　　　　　　代表配送先コード(daihyo_haiso_cd)⇒5桁→4桁
 * 　　　　　　　　POSレシート品名(漢字)(rec_hinmei_kanji_na)⇒30桁→20桁
 * 　　　　　　　　POSレシート品名(カナ)(rec_hinmei_kana_na)⇒30桁→20桁
 * 　　　　　　　　酒税報告区分(syuzei_hokoku_kb)⇒1桁→2桁
 * 　　　　　　　　入荷商品コード(nyuka_syohin_cd)⇒3件→13桁
 */
public class mst110101_SyohinBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int BUMON_CD_MAX_LENGTH = 4;//部門コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int HACYU_SYOHIN_KB_MAX_LENGTH = 1;//発注商品区分の長さ
	public static final int SYSTEM_KB_MAX_LENGTH = 1;//システム区分の長さ
	public static final int GYOSYU_KB_MAX_LENGTH = 3;//業種区分の長さ
	public static final int HINSYU_CD_MAX_LENGTH = 4;//品種コードの長さ
	public static final int HINMOKU_CD_MAX_LENGTH = 4;//品目の長さ
	public static final int MARK_GROUP_CD_MAX_LENGTH = 1;//マークグループの長さ
	public static final int MARK_CD_MAX_LENGTH = 4;//マークコードの長さ
	public static final int SYOHIN_2_CD_MAX_LENGTH = 13;//商品コード２の長さ
	public static final int KETASU_KB_MAX_LENGTH = 1;//桁数区分の長さ
//	↓↓仕様変更（2005.07.25）↓↓
//	public static final int MAKER_CD_MAX_LENGTH = 7;//メーカーコードの長さ
	public static final int MAKER_CD_MAX_LENGTH = 9;//メーカーコードの長さ
//	↑↑仕様変更（2005.07.25）↑↑
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 80;//漢字品名の長さ
	public static final int KIKAKU_KANJI_NA_MAX_LENGTH = 40;//漢字規格の長さ
	public static final int HINMEI_KANA_NA_MAX_LENGTH = 80;//カナ品名の長さ
	public static final int KIKAKU_KANA_NA_MAX_LENGTH = 40;//カナ規格の長さ
	public static final int SIIRE_HINBAN_CD_MAX_LENGTH = 15;//仕入先品番の長さ
	public static final int BLAND_CD_MAX_LENGTH = 3;//ブランドコードの長さ
	public static final int YUNYUHIN_KB_MAX_LENGTH = 1;//輸入品区分の長さ
// ↓↓ DB変更に伴う対応(2005.09.18 Y.Inaba)
//	public static final int SANTI_CD_MAX_LENGTH = 3;//原産国/産地コードの長さ
	public static final int SANTI_CD_MAX_LENGTH = 4;//原産国/産地コードの長さ
// ↑↑ DB変更に伴う対応(2005.09.18 Y.Inaba)
	public static final int NOHIN_ONDO_KB_MAX_LENGTH = 1;//納品温度帯の長さ
	public static final int COMMENT_TX_MAX_LENGTH = 20;//コメントの長さ
	public static final int TEN_HACHU_ST_DT_MAX_LENGTH = 8;//店舗発注開始日の長さ
	public static final int TEN_HACHU_ED_DT_MAX_LENGTH = 8;//店舗発注終了日の長さ
	public static final int TEIKAN_KB_MAX_LENGTH = 1;//定貫区分の長さ
	public static final int EOS_KB_MAX_LENGTH = 1;//EOS区分の長さ
	public static final int NOHIN_KIGEN_KB_MAX_LENGTH = 1;//納品期限区分の長さ
	public static final int MST_SIYOFUKA_KB_MAX_LENGTH = 1;//マスタ使用不可区分の長さ
	public static final int HACHU_TEISI_KB_MAX_LENGTH = 1;//発注停止区分の長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;//仕入先コードの長さ
	public static final int DAIHYO_HAISO_CD_MAX_LENGTH = 4;//代表配送先コードの長さ
	public static final int TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH = 4;//店別仕入先管理コードの長さ
//	public static final int TEN_HAISO_KANRI_CD_MAX_LENGTH = 4;//店別配送先管理コードの長さ
	public static final int SOBA_SYOHIN_KB_MAX_LENGTH = 1;//相場商品区分の長さ
	public static final int BIN_1_KB_MAX_LENGTH = 1;//①便区分の長さ
	public static final int HACHU_PATTERN_1_KB_MAX_LENGTH = 1;//①発注パターン区分の長さ
	public static final int C_NOHIN_RTIME_1_KB_MAX_LENGTH = 1;//①センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_1_KB_MAX_LENGTH = 1;//①店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_1_KB_MAX_LENGTH = 1;//①店納品時間帯の長さ
	public static final int BIN_2_KB_MAX_LENGTH = 1;//②便区分の長さ
	public static final int HACHU_PATTERN_2_KB_MAX_LENGTH = 1;//②発注パターン区分の長さ
	public static final int C_NOHIN_RTIME_2_KB_MAX_LENGTH = 1;//②センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_2_KB_MAX_LENGTH = 1;//②店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_2_KB_MAX_LENGTH = 1;//②店納品時間帯の長さ
	public static final int BIN_3_KB_MAX_LENGTH = 1;//③便区分の長さ
	public static final int HACHU_PATTERN_3_KB_MAX_LENGTH = 1;//③発注パターン区分の長さ
	public static final int C_NOHIN_RTIME_3_KB_MAX_LENGTH = 1;//③センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_3_KB_MAX_LENGTH = 1;//③店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_3_KB_MAX_LENGTH = 1;//③店納品時間帯の長さ
	public static final int C_NOHIN_RTIME_KB_MAX_LENGTH = 1;//センタ納品リードタイムの長さ
	public static final int ZEI_KB_MAX_LENGTH = 1;//税区分の長さ
	public static final int HANBAI_KIKAN_KB_MAX_LENGTH = 1;//販売期間区分の長さ
	public static final int SYOHIN_KB_MAX_LENGTH = 1;//商品区分の長さ
	public static final int BUTURYU_KB_MAX_LENGTH = 1;//物流区分の長さ
	public static final int YOKOMOTI_KB_MAX_LENGTH = 1;//横もち区分の長さ
	public static final int TEN_GROUPNO_CD_MAX_LENGTH = 2;//店グルーピングNO(物流）の長さ
	public static final int TEN_ZAIKO_KB_MAX_LENGTH = 1;//店在庫区分の長さ
	public static final int HACYU_SYOHIN_CD_MAX_LENGTH = 13;//発注商品コード
	// ↓↓ DB変更に伴う対応(2005.09.18 Y.Inaba)
	public static final int HENPIN_NB_MAX_LENGTH = 13;//返品契約書NOの長さ
// ↑↑ DB変更に伴う対応(2005.09.18 Y.Inaba)
	public static final int HANBAI_SEISAKU_KB_MAX_LENGTH = 1;//販売政策区分の長さ
	public static final int REBATE_FG_MAX_LENGTH = 1;//リベート対象フラグの長さ
	public static final int HANBAI_ST_DT_MAX_LENGTH = 8;//販売開始日の長さ
	public static final int HANBAI_ED_DT_MAX_LENGTH = 8;//販売終了日の長さ
	public static final int HANBAI_LIMIT_KB_MAX_LENGTH = 1;//販売期限区分の長さ
	public static final int AUTO_DEL_KB_MAX_LENGTH = 1;//自動削除対象区分の長さ
	public static final int GOT_MUJYOKEN_FG_MAX_LENGTH = 1;//GOT無条件表示対象の長さ
	public static final int GOT_START_MM_MAX_LENGTH = 2;//GOT表示開始月の長さ
	public static final int GOT_END_MM_MAX_LENGTH = 2;//GOT表示終了月の長さ
	public static final int E_SHOP_KB_MAX_LENGTH = 1;//Eショップ区分の長さ
	public static final int PLU_SEND_DT_MAX_LENGTH = 8;//PLU送信日の長さ
	public static final int REC_HINMEI_KANJI_NA_MAX_LENGTH = 20;//POSレシート品名(漢字)の長さ
	public static final int REC_HINMEI_KANA_NA_MAX_LENGTH = 20;//POSレシート品名(カナ)の長さ
	public static final int KEYPLU_FG_MAX_LENGTH = 1;//キーPLU対象の長さ
	public static final int PC_KB_MAX_LENGTH = 1;//PC発行区分の長さ
	public static final int UNIT_PRICE_TANI_KB_MAX_LENGTH = 4;//ユニットプライス-単位区分の長さ
	public static final int SHINAZOROE_KB_MAX_LENGTH = 1;//品揃区分の長さ
	public static final int KUMISU_KB_MAX_LENGTH = 1;//組数区分の長さ
	public static final int NEFUDA_KB_MAX_LENGTH = 1;//値札区分の長さ
	public static final int NEFUDA_DT_MAX_LENGTH = 8;//値札区分の長さ
	public static final int TAG_HAKO_QT_MAX_LENGTH = 6;//値札枚数の長さ
	public static final int YORIDORI_KB_MAX_LENGTH = 1;//よりどり種類の長さ
	public static final int SEASON_CD_MAX_LENGTH = 3;//シーズンコードの長さ
	public static final int HUKUSYU_CD_MAX_LENGTH = 3;//服種コードの長さ
	public static final int STYLE_CD_MAX_LENGTH = 3;//スタイルコードの長さ
	public static final int SCENE_CD_MAX_LENGTH = 3;//シーンコードの長さ
	public static final int SEX_CD_MAX_LENGTH = 3;//性別コードの長さ
	public static final int AGE_CD_MAX_LENGTH = 3;//年代コードの長さ
	public static final int GENERATION_CD_MAX_LENGTH = 3;//世代コードの長さ
	public static final int SOZAI_CD_MAX_LENGTH = 3;//素材コードの長さ
	public static final int PATTERN_CD_MAX_LENGTH = 3;//パターンコードの長さ
	public static final int ORIAMI_CD_MAX_LENGTH = 3;//織り編みコードの長さ
	public static final int HUKA_KINO_CD_MAX_LENGTH = 3;//付加機能コードの長さ
	public static final int SIZE_CD_MAX_LENGTH = 4;//サイズコードの長さ
	public static final int COLOR_CD_MAX_LENGTH = 2;//カラーコードの長さ
	public static final int SYUZEI_HOKOKU_KB_MAX_LENGTH = 5;//酒税報告分類の長さ
	public static final int TC_JYOUHO_NA_MAX_LENGTH = 20;//TC情報の長さ
	public static final int NOHIN_SYOHIN_CD_MAX_LENGTH = 13;//納品商品コードの長さ
	public static final int ITFCD_MAX_LENGTH = 15;//ITFコードの長さ
	public static final int NYUKA_SYOHIN_CD_MAX_LENGTH = 13;//入荷時商品コードの長さ
	public static final int CENTER_ZAIKO_KB_MAX_LENGTH = 1;//センター在庫区分の長さ
	public static final int ZAIKO_HACHU_SAKI_MAX_LENGTH = 6;//在庫補充発注先の長さ
	public static final int ZAIKO_CENTER_CD_MAX_LENGTH = 6;//在庫センターコードの長さ
	public static final int CENTER_HACHUTANI_KB_MAX_LENGTH = 1;//センター発注単位区分の長さ
	public static final int KEIYAKU_PATTERN_KB_MAX_LENGTH = 1;//契約パターンの長さ
	public static final int KEIYAKU_ST_DT_MAX_LENGTH = 8;//契約開始期間の長さ
	public static final int KEIYAKU_ED_DT_MAX_LENGTH = 8;//契約終了期間の長さ
	public static final int D_ZOKUSEI_1_NA_MAX_LENGTH = 3;//大属性１の長さ
	public static final int S_ZOKUSEI_1_NA_MAX_LENGTH = 3;//小属性１の長さ
	public static final int D_ZOKUSEI_2_NA_MAX_LENGTH = 3;//大属性２の長さ
	public static final int S_ZOKUSEI_2_NA_MAX_LENGTH = 3;//小属性２の長さ
	public static final int D_ZOKUSEI_3_NA_MAX_LENGTH = 3;//大属性３の長さ
	public static final int S_ZOKUSEI_3_NA_MAX_LENGTH = 3;//小属性３の長さ
	public static final int D_ZOKUSEI_4_NA_MAX_LENGTH = 3;//大属性４の長さ
	public static final int S_ZOKUSEI_4_NA_MAX_LENGTH = 3;//小属性４の長さ
	public static final int D_ZOKUSEI_5_NA_MAX_LENGTH = 3;//大属性５の長さ
	public static final int S_ZOKUSEI_5_NA_MAX_LENGTH = 3;//小属性５の長さ
	public static final int D_ZOKUSEI_6_NA_MAX_LENGTH = 3;//大属性６の長さ
	public static final int S_ZOKUSEI_6_NA_MAX_LENGTH = 3;//小属性６の長さ
	public static final int D_ZOKUSEI_7_NA_MAX_LENGTH = 3;//大属性７の長さ
	public static final int S_ZOKUSEI_7_NA_MAX_LENGTH = 3;//小属性７の長さ
	public static final int D_ZOKUSEI_8_NA_MAX_LENGTH = 3;//大属性８の長さ
	public static final int S_ZOKUSEI_8_NA_MAX_LENGTH = 3;//小属性８の長さ
	public static final int D_ZOKUSEI_9_NA_MAX_LENGTH = 3;//大属性９の長さ
	public static final int S_ZOKUSEI_9_NA_MAX_LENGTH = 3;//小属性９の長さ
	public static final int D_ZOKUSEI_10_NA_MAX_LENGTH = 3;//大属性１０の長さ
	public static final int S_ZOKUSEI_10_NA_MAX_LENGTH = 3;//小属性１０の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
	public static final int UNIT_CD_MAX_LENGTH 				    = 4;	//ユニットコードの長さ
	public static final int HAIFU_CD_MAX_LENGTH 				    = 2;	//配布コードの長さ
	public static final int SUBCLASS_CD_MAX_LENGTH 			    = 5;	//サブクラスコードの長さ
	public static final int USER_ID_MAX_LENGTH 				    = 6;	//バイヤーNoの長さ
	public static final int KIKAKU_KANJI_NA2_MAX_LENGTH 			= 14;	//漢字規格2の長さ
	public static final int KIKAKU_KANA_NA2_MAX_LENGTH 			= 7;	//カナ規格2の長さ
//	↓↓2006.07.12 xubq カスタマイズ修正↓↓
//	public static final int AREA_CD_MAX_LENGTH 			        	= 3;	//地区コードの長さ
//	↑↑2006.07.12 xubq カスタマイズ修正↑↑
	public static final int HACHUTANI_NA_MAX_LENGTH 			    = 8;	//発注単位呼称の長さ
	public static final int CENTER_KYOYO_DT_MAX_LENGTH 			= 3;	//センター許容日
	public static final int SYOHIKIGEN_MAX_LENGTH 			    = 3;	//消費期限の長さ
	public static final int SODE_CD_MAX_LENGTH                   = 3;    //袖丈コード
	public static final int NEFUDA_UKEWATASI_DT_MAX_LENGTH       = 8;    //値札受渡日
	public static final int NEFUDA_UKEWATASI_KB_MAX_LENGTH       = 1;    //値札受方法
//	↓↓仕様追加による変更（2005.06.29）↓↓
	public static final int SINKI_TOROKU_DT_MAX_LENGTH = 8;//新規登録日の長さ
	public static final int HENKO_DT_MAX_LENGTH = 8;//変更日の長さ
//	↑↑仕様追加による変更（2005.06.29）↑↑
//	↓↓仕様変更（2005.07.28）↓↓
	public static final int SYOKAI_TOROKU_TS_MAX_LENGTH = 20;//初回登録日の長さ
	public static final int SYOKAI_USER_ID_MAX_LENGTH = 6;//初回登録者IDの長さ
//	↑↑仕様変更（2005.07.28）↑↑

//	↓↓2006.06.26 zhujl カスタマイズ修正↓↓
	public static final int UNIT_NA_MAX_LENGTH = 20;//ユニット名称
	public static final int SUBCLASS_NA_MAX_LENGTH = 20;//サブクラス名称
	public static final int AREA_NA_MAX_LENGTH = 20;//地区名称
	public static final int TOKUBETU_GENKA_VL_MAX_LENGTH = 9;//特別原価
	public static final int SYOKAI_KEIYAKU_QT_MAX_LENGTH = 5;//契約数量
	public static final int KONKAI_TUIKEIYAKU_QT_MAX_LENGTH = 5;//追加契約数量
	public static final int YORIDORI_VL_MAX_LENGTH = 7;//よりどり価格
	public static final int YORIDORI_QT_MAX_LENGTH = 5;//よりどり数量
	public static final int KESHI_BAIKA_VL_MAX_LENGTH = 7;//消札売価
//	↑↑2006.06.26 zhujl カスタマイズ修正↑↑

	private String bumon_cd = null;	//販区コード
	private String syohin_cd = null;	//商品コード
	private String yuko_dt = null;	//有効日
	private String hacyu_syohin_kb = null; //発注商品区分
	private String system_kb = null; //システム区分
	private String gyosyu_kb = null;	//業種区分
	private String hinsyu_cd = null;	//品種コード
	private String hinmoku_cd = null;	//品目
	private String mark_group_cd = null;	//マークグループ
	private String mark_cd = null;	//マークコード
	private String syohin_2_cd = null;	//商品コード２
	private String ketasu_kb = null;	//桁数区分
	private String maker_cd = null;	//メーカーコード
	private String hinmei_kanji_na = null;	//漢字品名
	private String kikaku_kanji_na = null;	//漢字規格
	private String hinmei_kana_na = null;	//カナ品名
	private String kikaku_kana_na = null;	//カナ規格
	private long syohin_width_qt = 0;	//商品サイズ(幅)
	private long syohin_height_qt = 0;	//商品サイズ(高さ)
	private long syohin_depth_qt = 0;	//商品サイズ(奥行き)
	private String siire_hinban_cd = null;	//仕入先品番
	private String bland_cd = null;	//ブランドコード
	private String yunyuhin_kb = null;	//輸入品区分
	private String santi_cd = null;	//原産国/産地コード
	private long maker_kibo_kakaku_vl = 0;	//メーカー希望小売り価格(税込み)
	private String nohin_ondo_kb = null;	//納品温度帯
	private String comment_tx = null;	//コメント
	private String ten_hachu_st_dt = null;	//店舗発注開始日
	private String ten_hachu_ed_dt = null;	//店舗発注終了日
	private double gentanka_vl = 0;	//原価単価
	private long baitanka_vl = 0;	//売価単価(税込)
	private double hachutani_irisu_qt = 0;	//発注単位(入数)
	private double max_hachutani_qt = 0;	//最大発注単位
	private String teikan_kb = null;	//定貫区分
	private String eos_kb = null;	//EOS区分
	private long nohin_kigen_qt = 0;	//納品期限
	private String nohin_kigen_kb = null;	//納品期限区分
	private String mst_siyofuka_kb = null; //マスタ使用不可区分
	private String hachu_teisi_kb = null;	//発注停止区分
	private String siiresaki_cd = null;	//仕入先コード
	private String daihyo_haiso_cd = null;	//代表配送先コード
	private String ten_siiresaki_kanri_cd = null;	//店別仕入先管理コード
//	private String ten_haiso_kanri_cd = null;	//店別配送先管理コード
	private String soba_syohin_kb = null;	//相場商品区分
	private String bin_1_kb = null;	//①便区分
	private String hachu_pattern_1_kb = null;	//①発注パターン区分
	private long sime_time_1_qt = 0;	//①締め時間
	private String c_nohin_rtime_1_kb = null;	//①センタ納品リードタイム
	private String ten_nohin_rtime_1_kb = null;	//①店納品リードタイム
	private String ten_nohin_time_1_kb = null;	//①店納品時間帯
	private String bin_2_kb = null;	//②便区分
	private String hachu_pattern_2_kb = null;	//②発注パターン区分
	private long sime_time_2_qt = 0;	//②締め時間
	private String c_nohin_rtime_2_kb = null;	//②センタ納品リードタイム
	private String ten_nohin_rtime_2_kb = null;	//②店納品リードタイム
	private String ten_nohin_time_2_kb = null;	//②店納品時間帯
	private String bin_3_kb = null;	//③便区分
	private String hachu_pattern_3_kb = null;	//③発注パターン区分
	private long sime_time_3_qt = 0;	//③締め時間
	private String c_nohin_rtime_3_kb = null;	//③センタ納品リードタイム
	private String ten_nohin_rtime_3_kb = null;	//③店納品リードタイム
	private String ten_nohin_time_3_kb = null;	//③店納品時間帯
	private String c_nohin_rtime_kb = null;	//センタ納品リードタイム
	private String zei_kb = null; //税区分
	private String hanbai_kikan_kb = null; //販売期間区分
	private String syohin_kb = null;	//商品区分
	private String buturyu_kb = null;	//物流区分
	private String yokomoti_kb = null;	//横もち区分
	private String ten_groupno_cd = null;	//店グルーピングNO(物流）
	private String ten_zaiko_kb = null;	//店在庫区分
	private String hanbai_seisaku_kb = null;	//販売政策区分
// ↓↓ DB変更に伴う対応(2005.09.18 Y.Inaba)
//	private long henpin_nb = 0;	//返品契約書NO
	private String henpin_nb = null;
// ↑↑ DB変更に伴う対応(2005.09.18 Y.Inaba)
	private String hacyu_syohin_cd =  null;//発注商品コード
	private long henpin_genka_vl = 0;	//返品原価
	private String rebate_fg = null;	//リベート対象フラグ
	private String hanbai_st_dt = null;	//販売開始日
	private String hanbai_ed_dt = null;	//販売終了日
	private long hanbai_limit_qt = 0;	//販売期限
	private String hanbai_limit_kb = null;	//販売期限区分
	private String auto_del_kb = null;	//自動削除対象区分
	private String got_mujyoken_fg = null;	//GOT無条件表示対象
	private String got_start_mm = null;	//GOT表示開始月
	private String got_end_mm = null;	//GOT表示終了月
	private String e_shop_kb = null;	//Eショップ区分
	private String plu_send_dt = null;	//PLU送信日
	private String rec_hinmei_kanji_na = null;	//POSレシート品名(漢字)
	private String rec_hinmei_kana_na = null;	//POSレシート品名(カナ)
	private String keyplu_fg = null;	//キーPLU対象
	private String pc_kb = null;	//PC発行区分
	private String daisi_no_nb = null;	//台紙NO
	private String unit_price_tani_kb = null;	//ユニットプライス-単位区分
	private long unit_price_naiyoryo_qt = 0;	//ユニットプライス-内容量
	private long unit_price_kijun_tani_qt = 0;	//ユニットプライス-基準単量
	private String shinazoroe_kb = null;	//品揃区分
	private String kumisu_kb = null;	//組数区分
	private String nefuda_kb = null;	//値札区分
	private String nefuda_dt = null;	//値札日
	private String tag_hako_qt = null;	//値札発行枚数
	private String yoridori_kb = null;	//よりどり種類
	private String yoridori_qt = null;	//よりどり数量
	private long tag_hyoji_baika_vl = 0;	//タグ表示売価
	private String season_cd = null;	//シーズンコード
	private String hukusyu_cd = null;	//服種コード
	private String style_cd = null;	//スタイルコード
	private String scene_cd = null;	//シーンコード
	private String sex_cd = null;	//性別コード
	private String age_cd = null;	//年代コード
	private String generation_cd = null;	//世代コード
	private String sozai_cd = null;	//素材コード
	private String pattern_cd = null;	//パターンコード
	private String oriami_cd = null;	//織り編みコード
	private String huka_kino_cd = null;	//付加機能コード
	private String size_cd = null;	//サイズコード
	private String color_cd = null;	//カラーコード
	private String syuzei_hokoku_kb = null;	//酒税報告分類
	private String tc_jyouho_na = null;	//TC情報
	private String nohin_syohin_cd = null;	//納品商品コード
	private String itfCd = null;	//itfCd
	private double case_irisu_qt = 0;	//ケース入り数
	private String nyuka_syohin_cd = null;	//入荷時商品コード
	private long pack_width_qt = 0;	//外箱サイズ幅
	private long pack_heigth_qt = 0;	//外箱サイズ高さ
	private long pack_depth_qt = 0;	//外箱サイズ奥行き
	private long pack_weight_qt = 0;	//外箱重量
	private String center_zaiko_kb = null;	//センター在庫区分
	private String zaiko_hachu_saki = null;	//在庫補充発注先
	private String zaiko_center_cd = null;	//在庫センターコード
	private long min_zaikosu_qt = 0;	//最低在庫数(発注点)
	private long max_zaikosu_qt = 0;	//最大在庫数
	private String center_hachutani_kb = null;	//センター発注単位区分
	private double center_hachutani_qt = 0;	//センター発注単位数
	private double center_irisu_qt = 0;	//センター入り数
	private long center_weight_qt = 0;	//センター重量
	private long tana_no_nb = 0;	//棚NO
	private long dan_no_nb = 0;	//段NO
	private long keiyaku_su_qt = 0;	//契約数
	private String keiyaku_pattern_kb = null;	//契約パターン
	private String keiyaku_st_dt = null;	//契約開始期間
	private String keiyaku_ed_dt = null;	//契約終了期間
	private long kijun_zaikosu_qt = 0;	//基準在庫日数
	private String d_zokusei_1_na = null;	//大属性１
	private String s_zokusei_1_na = null;	//小属性１
	private String d_zokusei_2_na = null;	//大属性２
	private String s_zokusei_2_na = null;	//小属性２
	private String d_zokusei_3_na = null;	//大属性３
	private String s_zokusei_3_na = null;	//小属性３
	private String d_zokusei_4_na = null;	//大属性４
	private String s_zokusei_4_na = null;	//小属性４
	private String d_zokusei_5_na = null;	//大属性５
	private String s_zokusei_5_na = null;	//小属性５
	private String d_zokusei_6_na = null;	//大属性６
	private String s_zokusei_6_na = null;	//小属性６
	private String d_zokusei_7_na = null;	//大属性７
	private String s_zokusei_7_na = null;	//小属性７
	private String d_zokusei_8_na = null;	//大属性８
	private String s_zokusei_8_na = null;	//小属性８
	private String d_zokusei_9_na = null;	//大属性９
	private String s_zokusei_9_na = null;	//小属性９
	private String d_zokusei_10_na = null;	//大属性１０
	private String s_zokusei_10_na = null;	//小属性１０
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	private String yoridori_vl = null;
	public  String unit_cd				         = "";	      //ユニットコード
	public  String haifu_cd				     = "";	      //配布コード
	public  String subclass_cd 			     = "";	      //サブクラスコード
	public String  user_id 				     = "";	      //バイヤーNo
	public String kikaku_kanji_2_na		     = "";	      //漢字規格2
	public String kikaku_kana_2_na				 = "";	      //カナ規格2
//	↓↓2006.07.12 xubq カスタマイズ修正↓↓
//	public String  area_cd	                	 = "";	      //地区コード
//	↑↑2006.07.12 xubq カスタマイズ修正↑↑
	public  String unit_na				         = "";	      //ユニット名
	public  String subclass_na 			     = "";	      //サブクラス名
	public String  areacd_na	             	 = "";	      //地区名
	public String  hachu_tani_na              	 = "";	      //発注単位呼称
	public String  syohikigen                	 = "";	      //消費期限
	public String  honbu_zai_kb                 = "";	      //本部在庫区分
	public String  fujisyohin_kb                = "";	      //商品区分
	public String  pb_kb                   	 = "";	      //ＰＢ区分
	public String  daicho_tenpo_kb              = "";	      //商品台帳（店舗）
	public String  daicho_honbu_kb              = "";	      //商品台帳（本部）
	public String  syuzeihokokukb               = "";	      //酒税報告分類
	public String  daicho_siiresaki_kb          = "";	  	  //商品台帳（仕入先）
	public String   hinban_cd                   = "";	      //品番
	public String   gtin_cd                     = "";
	public String   keshibaikavl                = "";
	public String   tokubetugenkavl             ="";
	public double   pre_gentanka_vl            =0;		  // 前回原価単価
	public long   pre_baitanka_vl              =0;		  // 前回売価単価
	public String   center_kyoyo_dt             =null;		  //センター許容日
	public String   st_syohi_kigen_dt           =null;		  //消費期限
	public String   syohi_kigen_dt              =null;		  	  //消費期限
	public String   yusenbinkb                	 ="";			//優先便区分
	public String   plu_kb                	 	 ="";			//PLU区分
	private String  sode_cd                   	 = "";
//	↓↓仕様追加による変更（2005.06.29）↓↓
	private String sinki_toroku_dt = null;//新規登録日
	private String henko_dt = null;	//変更日
//	↑↑仕様追加による変更（2005.06.29）↑↑
//	↓↓仕様変更（2005.07.28）↓↓
	private String syokai_toroku_ts = null;	//初回登録日
	private String syokai_user_id = null;	//初回登録者ID
//	↑↑仕様変更（2005.07.28）↑↑

	private String st_syohin_width_qt = null;	//商品サイズ(幅)
	private String st_syohin_height_qt = null;	//商品サイズ(高さ)
	private String st_syohin_depth_qt = null;	//商品サイズ(奥行き)
	private String st_maker_kibo_kakaku_vl = null;	//メーカー希望小売り価格(税込み)
	private String st_gentanka_vl = null;	//原価単価
	private String st_baitanka_vl = null;	//売価単価(税込)
	private String st_hachutani_irisu_qt = null;	//発注単位(入数)
	private String st_max_hachutani_qt = null;	//最大発注単位
	private String st_nohin_kigen_qt = null;	//納品期限
	private String st_sime_time_1_qt = null;	//①締め時間
	private String st_sime_time_2_qt = null;	//②締め時間
	private String st_sime_time_3_qt = null;	//③締め時間
	private String st_henpin_nb = null;	//返品契約書NO
	private String st_henpin_genka_vl = null;	//返品原価
	private String st_hanbai_limit_qt = null;	//販売期限
	private String st_daisi_no_nb = null;	//台紙NO
	private String st_unit_price_naiyoryo_qt = null;	//ユニットプライス-内容量
	private String st_unit_price_kijun_tani_qt = null;	//ユニットプライス-基準単量
	private String st_yoridori_qt = null;	//よりどり数量
	private String st_tag_hyoji_baika_vl = null;	//タグ表示売価
	private String st_case_irisu_qt = null;	//ケース入り数
	private String st_pack_width_qt = null;	//外箱サイズ幅
	private String st_pack_heigth_qt = null;	//外箱サイズ高さ
	private String st_pack_depth_qt = null;	//外箱サイズ奥行き
	private String st_pack_weight_qt = null;	//外箱重量
	private String st_min_zaikosu_qt = null;	//最低在庫数(発注点)
	private String st_max_zaikosu_qt = null;	//最大在庫数
	private String st_center_hachutani_qt = null;	//センター発注単位数
	private String st_center_irisu_qt = null;	//センター入り数
	private String st_center_weight_qt = null;	//センター重量
	private String st_tana_no_nb = null;	//棚NO
	private String st_dan_no_nb = null;	//段NO
	private String st_keiyaku_su_qt = null;	//契約数
	private String st_kijun_zaikosu_qt = null;	//基準在庫日数
	private String nefuda_ukewatasi_dt = null;
	private String nefuda_ukewatasi_kb = null;
//	↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
	private String st_pre_gentanka_vl            =null;		  // 前回原価単価
	private String st_pre_baitanka_vl            =null;		  // 前回売価単価
//	↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
//BUGNO-S075 2005.07.20 Sirius START
	private HashMap gamenFlg = new HashMap();	//画面使用フラグ
//BUGNO-S075 2005.07.20 Sirius END

//	↓↓2006.06.26 zhujl カスタマイズ修正↓↓
	private String area_na = null;//地区名称
	private String keshi_baika_vl = null;//消札売価
	private String tokubetu_genka_vl = null;//特別原価
	private String syokai_keiyaku_qt = null;//契約数量
	private String konkai_tuikeiyaku_qt = null;//追加契約数量
	private String fuji_syohin_kb = null;//FUJI商品区分
	private String bumon_kanji_rn  = null;//部門名(検索)
	private String hinmei_kanji  = null;	//商品名(検索)
//	↑↑2006.06.26 zhujl カスタマイズ修正↑↑


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
	 * mst11_SyohinBeanを１件のみ抽出したい時に使用する
	 */
	public static mst110101_SyohinBean getmst11_SyohinBean(DataHolder dataHolder)
	{
		mst110101_SyohinBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst110101_SyohinDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst110101_SyohinBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


	// hanku_cdに対するセッターとゲッターの集合
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bumon_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getBumonCd()
	{
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString()
	{
		return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH));
	}

	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
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

	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("yuko_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getYukoDt()
	{
		return cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH);
	}
	public String getYukoDtString()
	{
		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
	}

	// hacyu_syohin_kbに対するセッターとゲッターの集合
	public boolean setHacyuSyohinKb(String hacyu_syohin_kb)
	{
		this.hacyu_syohin_kb = hacyu_syohin_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hacyu_syohin_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHacyuSyohinKb()
	{
		return cutString(this.hacyu_syohin_kb,HACYU_SYOHIN_KB_MAX_LENGTH);
	}
	public String getHacyuSyohinKbString()
	{
		return "'" + cutString(this.hacyu_syohin_kb,HACYU_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getHacyuSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hacyu_syohin_kb,HACYU_SYOHIN_KB_MAX_LENGTH));
	}

	// system_kbに対するセッターとゲッターの集合
	public boolean setSystemKb(String system_kb)
	{
		this.system_kb = system_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("system_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSystemKb()
	{
		return cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH);
	}
	public String getSystemKbString()
	{
		return "'" + cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH) + "'";
	}
	public String getSystemKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH));
	}

	// gyosyu_kbに対するセッターとゲッターの集合
	public boolean setGyosyuKb(String gyosyu_kb)
	{
		this.gyosyu_kb = gyosyu_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("gyosyu_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getGyosyuKb()
	{
		return cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH);
	}
	public String getGyosyuKbString()
	{
		return "'" + cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH) + "'";
	}
	public String getGyosyuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH));
	}

	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hinsyu_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
	}

	// hinmoku_cdに対するセッターとゲッターの集合
	public boolean setHinmokuCd(String hinmoku_cd)
	{
		this.hinmoku_cd = hinmoku_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hinmoku_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHinmokuCd()
	{
		return cutString(this.hinmoku_cd,HINMOKU_CD_MAX_LENGTH);
	}
	public String getHinmokuCdString()
	{
		return "'" + cutString(this.hinmoku_cd,HINMOKU_CD_MAX_LENGTH) + "'";
	}
	public String getHinmokuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmoku_cd,HINMOKU_CD_MAX_LENGTH));
	}

	// mark_group_cdに対するセッターとゲッターの集合
	public boolean setMarkGroupCd(String mark_group_cd)
	{
		this.mark_group_cd = mark_group_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("mark_group_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getMarkGroupCd()
	{
		return cutString(this.mark_group_cd,MARK_GROUP_CD_MAX_LENGTH);
	}
	public String getMarkGroupCdString()
	{
		return "'" + cutString(this.mark_group_cd,MARK_GROUP_CD_MAX_LENGTH) + "'";
	}
	public String getMarkGroupCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.mark_group_cd,MARK_GROUP_CD_MAX_LENGTH));
	}

	// mark_cdに対するセッターとゲッターの集合
	public boolean setMarkCd(String mark_cd)
	{
		this.mark_cd = mark_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("mark_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getMarkCd()
	{
		return cutString(this.mark_cd,MARK_CD_MAX_LENGTH);
	}
	public String getMarkCdString()
	{
		return "'" + cutString(this.mark_cd,MARK_CD_MAX_LENGTH) + "'";
	}
	public String getMarkCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.mark_cd,MARK_CD_MAX_LENGTH));
	}

	// syohin_2_cdに対するセッターとゲッターの集合
	public boolean setSyohin2Cd(String syohin_2_cd)
	{
		this.syohin_2_cd = syohin_2_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_2_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSyohin2Cd()
	{
		return cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH);
	}
	public String getSyohin2CdString()
	{
		return "'" + cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin2CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH));
	}

	// ketasu_kbに対するセッターとゲッターの集合
	public boolean setKetasuKb(String ketasu_kb)
	{
		this.ketasu_kb = ketasu_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ketasu_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getKetasuKb()
	{
		return cutString(this.ketasu_kb,KETASU_KB_MAX_LENGTH);
	}
	public String getKetasuKbString()
	{
		return "'" + cutString(this.ketasu_kb,KETASU_KB_MAX_LENGTH) + "'";
	}
	public String getKetasuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ketasu_kb,KETASU_KB_MAX_LENGTH));
	}

	// maker_cdに対するセッターとゲッターの集合
	public boolean setMakerCd(String maker_cd)
	{
		this.maker_cd = maker_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("maker_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getMakerCd()
	{
		return cutString(this.maker_cd,MAKER_CD_MAX_LENGTH);
	}
	public String getMakerCdString()
	{
		return "'" + cutString(this.maker_cd,MAKER_CD_MAX_LENGTH) + "'";
	}
	public String getMakerCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.maker_cd,MAKER_CD_MAX_LENGTH));
	}

	// hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hinmei_kanji_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHinmeiKanjiNa()
	{
		return cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getHinmeiKanjiNaString()
	{
		return "'" + cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH));
	}

	// kikaku_kanji_naに対するセッターとゲッターの集合
	public boolean setKikakuKanjiNa(String kikaku_kanji_na)
	{
		this.kikaku_kanji_na = kikaku_kanji_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("kikaku_kanji_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getKikakuKanjiNa()
	{
		return cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH);
	}
	public String getKikakuKanjiNaString()
	{
		return "'" + cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKikakuKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH));
	}

	// hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setHinmeiKanaNa(String hinmei_kana_na)
	{
		this.hinmei_kana_na = hinmei_kana_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hinmei_kana_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHinmeiKanaNa()
	{
		return cutString(this.hinmei_kana_na,HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getHinmeiKanaNaString()
	{
		return "'" + cutString(this.hinmei_kana_na,HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kana_na,HINMEI_KANA_NA_MAX_LENGTH));
	}

	// kikaku_kana_naに対するセッターとゲッターの集合
	public boolean setKikakuKanaNa(String kikaku_kana_na)
	{
		this.kikaku_kana_na = kikaku_kana_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("kikaku_kana_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getKikakuKanaNa()
	{
		return cutString(this.kikaku_kana_na,KIKAKU_KANA_NA_MAX_LENGTH);
	}
	public String getKikakuKanaNaString()
	{
		return "'" + cutString(this.kikaku_kana_na,KIKAKU_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getKikakuKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kana_na,KIKAKU_KANA_NA_MAX_LENGTH));
	}

	// syohin_width_qtに対するセッターとゲッターの集合
	public boolean setSyohinWidthQt(String syohin_width_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_width_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.syohin_width_qt = Long.parseLong(syohin_width_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyohinWidthQt(long syohin_width_qt)
	{
		this.syohin_width_qt = syohin_width_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_width_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getSyohinWidthQt()
	{
		return this.syohin_width_qt;
	}
	public String getSyohinWidthQtString()
	{
		return Long.toString(this.syohin_width_qt);
	}

	// syohin_height_qtに対するセッターとゲッターの集合
	public boolean setSyohinHeightQt(String syohin_height_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_height_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.syohin_height_qt = Long.parseLong(syohin_height_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyohinHeightQt(long syohin_height_qt)
	{
		this.syohin_height_qt = syohin_height_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_height_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getSyohinHeightQt()
	{
		return this.syohin_height_qt;
	}
	public String getSyohinHeightQtString()
	{
		return Long.toString(this.syohin_height_qt);
	}

	// syohin_depth_qtに対するセッターとゲッターの集合
	public boolean setSyohinDepthQt(String syohin_depth_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_depth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.syohin_depth_qt = Long.parseLong(syohin_depth_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyohinDepthQt(long syohin_depth_qt)
	{
		this.syohin_depth_qt = syohin_depth_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_depth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getSyohinDepthQt()
	{
		return this.syohin_depth_qt;
	}
	public String getSyohinDepthQtString()
	{
		return Long.toString(this.syohin_depth_qt);
	}

	// siire_hinban_cdに対するセッターとゲッターの集合
	public boolean setSiireHinbanCd(String siire_hinban_cd)
	{
		this.siire_hinban_cd = siire_hinban_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("siire_hinban_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSiireHinbanCd()
	{
		return cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH);
	}
	public String getSiireHinbanCdString()
	{
		return "'" + cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getSiireHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH));
	}

	// bland_cdに対するセッターとゲッターの集合
	public boolean setBlandCd(String bland_cd)
	{
		this.bland_cd = bland_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bland_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getBlandCd()
	{
		return cutString(this.bland_cd,BLAND_CD_MAX_LENGTH);
	}
	public String getBlandCdString()
	{
		return "'" + cutString(this.bland_cd,BLAND_CD_MAX_LENGTH) + "'";
	}
	public String getBlandCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bland_cd,BLAND_CD_MAX_LENGTH));
	}

	// yunyuhin_kbに対するセッターとゲッターの集合
	public boolean setYunyuhinKb(String yunyuhin_kb)
	{
		this.yunyuhin_kb = yunyuhin_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("yunyuhin_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getYunyuhinKb()
	{
		return cutString(this.yunyuhin_kb,YUNYUHIN_KB_MAX_LENGTH);
	}
	public String getYunyuhinKbString()
	{
		return "'" + cutString(this.yunyuhin_kb,YUNYUHIN_KB_MAX_LENGTH) + "'";
	}
	public String getYunyuhinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yunyuhin_kb,YUNYUHIN_KB_MAX_LENGTH));
	}

	// santi_cdに対するセッターとゲッターの集合
	public boolean setSantiCd(String santi_cd)
	{
		this.santi_cd = santi_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("santi_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSantiCd()
	{
		return cutString(this.santi_cd,SANTI_CD_MAX_LENGTH);
	}
	public String getSantiCdString()
	{
		return "'" + cutString(this.santi_cd,SANTI_CD_MAX_LENGTH) + "'";
	}
	public String getSantiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.santi_cd,SANTI_CD_MAX_LENGTH));
	}

	// maker_kibo_kakaku_vlに対するセッターとゲッターの集合
	public boolean setMakerKiboKakakuVl(String maker_kibo_kakaku_vl)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("maker_kibo_kakaku_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.maker_kibo_kakaku_vl = Long.parseLong(maker_kibo_kakaku_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMakerKiboKakakuVl(long maker_kibo_kakaku_vl)
	{
		this.maker_kibo_kakaku_vl = maker_kibo_kakaku_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("maker_kibo_kakaku_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getMakerKiboKakakuVl()
	{
		return this.maker_kibo_kakaku_vl;
	}
	public String getMakerKiboKakakuVlString()
	{
		return Long.toString(this.maker_kibo_kakaku_vl);
	}

	// nohin_ondo_kbに対するセッターとゲッターの集合
	public boolean setNohinOndoKb(String nohin_ondo_kb)
	{
		this.nohin_ondo_kb = nohin_ondo_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("nohin_ondo_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getNohinOndoKb()
	{
		return cutString(this.nohin_ondo_kb,NOHIN_ONDO_KB_MAX_LENGTH);
	}
	public String getNohinOndoKbString()
	{
		return "'" + cutString(this.nohin_ondo_kb,NOHIN_ONDO_KB_MAX_LENGTH) + "'";
	}
	public String getNohinOndoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_ondo_kb,NOHIN_ONDO_KB_MAX_LENGTH));
	}

	// comment_txに対するセッターとゲッターの集合
	public boolean setCommentTx(String comment_tx)
	{
		this.comment_tx = comment_tx;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("comment_tx","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCommentTx()
	{
		return cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH);
	}
	public String getCommentTxString()
	{
		return "'" + cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH) + "'";
	}
	public String getCommentTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH));
	}

	// ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setTenHachuStDt(String ten_hachu_st_dt)
	{
		this.ten_hachu_st_dt = ten_hachu_st_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_hachu_st_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenHachuStDt()
	{
		return cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH);
	}
	public String getTenHachuStDtString()
	{
		return "'" + cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getTenHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH));
	}

	// ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setTenHachuEdDt(String ten_hachu_ed_dt)
	{
		this.ten_hachu_ed_dt = ten_hachu_ed_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_hachu_ed_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenHachuEdDt()
	{
		return cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH);
	}
	public String getTenHachuEdDtString()
	{
		return "'" + cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getTenHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH));
	}

	// gentanka_vlに対するセッターとゲッターの集合
	public boolean setGentankaVl(String gentanka_vl)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("gentanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.gentanka_vl = Double.parseDouble(gentanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGentankaVl(double gentanka_vl)
	{
		this.gentanka_vl = gentanka_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("gentanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getGentankaVl()
	{
		return this.gentanka_vl;
	}
	public String getGentankaVlString()
	{
		return Double.toString(this.gentanka_vl);
	}

	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("baitanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.baitanka_vl = Long.parseLong(baitanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaitankaVl(long baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("baitanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getBaitankaVl()
	{
		return this.baitanka_vl;
	}
	public String getBaitankaVlString()
	{
		return Long.toString(this.baitanka_vl);
	}

	// hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setHachutaniIrisuQt(String hachutani_irisu_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachutani_irisu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.hachutani_irisu_qt = Double.parseDouble(hachutani_irisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHachutaniIrisuQt(double hachutani_irisu_qt)
	{
		this.hachutani_irisu_qt = hachutani_irisu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachutani_irisu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getHachutaniIrisuQt()
	{
		return this.hachutani_irisu_qt;
	}
	public String getHachutaniIrisuQtString()
	{
		return Double.toString(this.hachutani_irisu_qt);
	}

	// max_hachutani_qtに対するセッターとゲッターの集合
	public boolean setMaxHachutaniQt(String max_hachutani_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("max_hachutani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.max_hachutani_qt = Double.parseDouble(max_hachutani_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMaxHachutaniQt(double max_hachutani_qt)
	{
		this.max_hachutani_qt = max_hachutani_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("max_hachutani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getMaxHachutaniQt()
	{
		return this.max_hachutani_qt;
	}
	public String getMaxHachutaniQtString()
	{
		return Double.toString(this.max_hachutani_qt);
	}

	// teikan_kbに対するセッターとゲッターの集合
	public boolean setTeikanKb(String teikan_kb)
	{
		this.teikan_kb = teikan_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("teikan_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTeikanKb()
	{
		return cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH);
	}
	public String getTeikanKbString()
	{
		return "'" + cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getTeikanKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH));
	}

	// eos_kbに対するセッターとゲッターの集合
	public boolean setEosKb(String eos_kb)
	{
		this.eos_kb = eos_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("eos_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getEosKb()
	{
		return cutString(this.eos_kb,EOS_KB_MAX_LENGTH);
	}
	public String getEosKbString()
	{
		return "'" + cutString(this.eos_kb,EOS_KB_MAX_LENGTH) + "'";
	}
	public String getEosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eos_kb,EOS_KB_MAX_LENGTH));
	}

	// nohin_kigen_qtに対するセッターとゲッターの集合
	public boolean setNohinKigenQt(String nohin_kigen_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("nohin_kigen_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.nohin_kigen_qt = Long.parseLong(nohin_kigen_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setNohinKigenQt(long nohin_kigen_qt)
	{
		this.nohin_kigen_qt = nohin_kigen_qt;
		return true;
	}
	public long getNohinKigenQt()
	{
		return this.nohin_kigen_qt;
	}
	public String getNohinKigenQtString()
	{
		return Long.toString(this.nohin_kigen_qt);
	}

	// nohin_kigen_kbに対するセッターとゲッターの集合
	public boolean setNohinKigenKb(String nohin_kigen_kb)
	{
		this.nohin_kigen_kb = nohin_kigen_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("nohin_kigen_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getNohinKigenKb()
	{
		return cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH);
	}
	public String getNohinKigenKbString()
	{
		return "'" + cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH) + "'";
	}
	public String getNohinKigenKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH));
	}

	// mst_siyofuka_kbに対するセッターとゲッターの集合
	public boolean setMstSiyofukaKb(String mst_siyofuka_kb)
	{
		this.mst_siyofuka_kb = mst_siyofuka_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("mst_siyofuka_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getMstSiyofukaKb()
	{
		return cutString(this.mst_siyofuka_kb,MST_SIYOFUKA_KB_MAX_LENGTH);
	}
	public String getMstSiyofukaKbString()
	{
		return "'" + cutString(this.mst_siyofuka_kb,MST_SIYOFUKA_KB_MAX_LENGTH) + "'";
	}
	public String getMstSiyofukaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.mst_siyofuka_kb,MST_SIYOFUKA_KB_MAX_LENGTH));
	}

	// hachu_teisi_kbに対するセッターとゲッターの集合
	public boolean setHachuTeisiKb(String hachu_teisi_kb)
	{
		this.hachu_teisi_kb = hachu_teisi_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachu_teisi_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHachuTeisiKb()
	{
		return cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH);
	}
	public String getHachuTeisiKbString()
	{
		return "'" + cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH) + "'";
	}
	public String getHachuTeisiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH));
	}

	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("siiresaki_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSiiresakiCd()
	{
		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getSiiresakiCdString()
	{
		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
	}

	// daihyo_haiso_cdに対するセッターとゲッターの集合
	public boolean setDaihyoHaisoCd(String daihyo_haiso_cd)
	{
		this.daihyo_haiso_cd = daihyo_haiso_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("daihyo_haiso_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDaihyoHaisoCd()
	{
		return cutString(this.daihyo_haiso_cd,DAIHYO_HAISO_CD_MAX_LENGTH);
	}
	public String getDaihyoHaisoCdString()
	{
		return "'" + cutString(this.daihyo_haiso_cd,DAIHYO_HAISO_CD_MAX_LENGTH) + "'";
	}
	public String getDaihyoHaisoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.daihyo_haiso_cd,DAIHYO_HAISO_CD_MAX_LENGTH));
	}

	// ten_siiresaki_kanri_cdに対するセッターとゲッターの集合
	public boolean setTenSiiresakiKanriCd(String ten_siiresaki_kanri_cd)
	{
		this.ten_siiresaki_kanri_cd = ten_siiresaki_kanri_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_siiresaki_kanri_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenSiiresakiKanriCd()
	{
		return cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH);
	}
	public String getTenSiiresakiKanriCdString()
	{
		return "'" + cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getTenSiiresakiKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH));
	}

/*	// ten_haiso_kanri_cdに対するセッターとゲッターの集合
	public boolean setTenHaisoKanriCd(String ten_haiso_kanri_cd)
	{
		this.ten_haiso_kanri_cd = ten_haiso_kanri_cd;
		return true;
	}
	public String getTenHaisoKanriCd()
	{
		return cutString(this.ten_haiso_kanri_cd,TEN_HAISO_KANRI_CD_MAX_LENGTH);
	}
	public String getTenHaisoKanriCdString()
	{
		return "'" + cutString(this.ten_haiso_kanri_cd,TEN_HAISO_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getTenHaisoKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_haiso_kanri_cd,TEN_HAISO_KANRI_CD_MAX_LENGTH));
	}
*/

	// soba_syohin_kbに対するセッターとゲッターの集合
	public boolean setSobaSyohinKb(String soba_syohin_kb)
	{
		this.soba_syohin_kb = soba_syohin_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("soba_syohin_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSobaSyohinKb()
	{
		return cutString(this.soba_syohin_kb,SOBA_SYOHIN_KB_MAX_LENGTH);
	}
	public String getSobaSyohinKbString()
	{
		return "'" + cutString(this.soba_syohin_kb,SOBA_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getSobaSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.soba_syohin_kb,SOBA_SYOHIN_KB_MAX_LENGTH));
	}

	// bin_1_kbに対するセッターとゲッターの集合
	public boolean setBin1Kb(String bin_1_kb)
	{
		this.bin_1_kb = bin_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bin_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getBin1Kb()
	{
		return cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH);
	}
	public String getBin1KbString()
	{
		return "'" + cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH) + "'";
	}
	public String getBin1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH));
	}

	// hachu_pattern_1_kbに対するセッターとゲッターの集合
	public boolean setHachuPattern1Kb(String hachu_pattern_1_kb)
	{
		this.hachu_pattern_1_kb = hachu_pattern_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachu_pattern_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHachuPattern1Kb()
	{
		return cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH);
	}
	public String getHachuPattern1KbString()
	{
		return "'" + cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH) + "'";
	}
	public String getHachuPattern1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH));
	}

	// sime_time_1_qtに対するセッターとゲッターの集合
	public boolean setSimeTime1Qt(String sime_time_1_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_1_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.sime_time_1_qt = Long.parseLong(sime_time_1_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSimeTime1Qt(long sime_time_1_qt)
	{
		this.sime_time_1_qt = sime_time_1_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_1_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getSimeTime1Qt()
	{
		return this.sime_time_1_qt;
	}
	public String getSimeTime1QtString()
	{
		return Long.toString(this.sime_time_1_qt);
	}

	// c_nohin_rtime_1_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime1Kb(String c_nohin_rtime_1_kb)
	{
		this.c_nohin_rtime_1_kb = c_nohin_rtime_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("c_nohin_rtime_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCNohinRtime1Kb()
	{
		return cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH);
	}
	public String getCNohinRtime1KbString()
	{
		return "'" + cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getCNohinRtime1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH));
	}

	// ten_nohin_rtime_1_kbに対するセッターとゲッターの集合
	public boolean setTenNohinRtime1Kb(String ten_nohin_rtime_1_kb)
	{
		this.ten_nohin_rtime_1_kb = ten_nohin_rtime_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_rtime_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinRtime1Kb()
	{
		return cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH);
	}
	public String getTenNohinRtime1KbString()
	{
		return "'" + cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinRtime1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH));
	}

	// ten_nohin_time_1_kbに対するセッターとゲッターの集合
	public boolean setTenNohinTime1Kb(String ten_nohin_time_1_kb)
	{
		this.ten_nohin_time_1_kb = ten_nohin_time_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_time_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinTime1Kb()
	{
		return cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH);
	}
	public String getTenNohinTime1KbString()
	{
		return "'" + cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinTime1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH));
	}

	// bin_2_kbに対するセッターとゲッターの集合
	public boolean setBin2Kb(String bin_2_kb)
	{
		this.bin_2_kb = bin_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bin_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getBin2Kb()
	{
		return cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH);
	}
	public String getBin2KbString()
	{
		return "'" + cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH) + "'";
	}
	public String getBin2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH));
	}

	// hachu_pattern_2_kbに対するセッターとゲッターの集合
	public boolean setHachuPattern2Kb(String hachu_pattern_2_kb)
	{
		this.hachu_pattern_2_kb = hachu_pattern_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachu_pattern_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHachuPattern2Kb()
	{
		return cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH);
	}
	public String getHachuPattern2KbString()
	{
		return "'" + cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH) + "'";
	}
	public String getHachuPattern2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH));
	}

	// sime_time_2_qtに対するセッターとゲッターの集合
	public boolean setSimeTime2Qt(String sime_time_2_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_2_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.sime_time_2_qt = Long.parseLong(sime_time_2_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSimeTime2Qt(long sime_time_2_qt)
	{
		this.sime_time_2_qt = sime_time_2_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_2_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getSimeTime2Qt()
	{
		return this.sime_time_2_qt;
	}
	public String getSimeTime2QtString()
	{
		return Long.toString(this.sime_time_2_qt);
	}

	// c_nohin_rtime_2_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime2Kb(String c_nohin_rtime_2_kb)
	{
		this.c_nohin_rtime_2_kb = c_nohin_rtime_2_kb;
//		↓↓仕様変更（2005.08.24）↓↓
		gamenFlg.put("c_nohin_rtime_2_kb","1");
//		↑↑仕様変更（2005.08.24）↑↑
		return true;
	}
	public String getCNohinRtime2Kb()
	{
		return cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH);
	}
	public String getCNohinRtime2KbString()
	{
		return "'" + cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getCNohinRtime2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH));
	}

	// ten_nohin_rtime_2_kbに対するセッターとゲッターの集合
	public boolean setTenNohinRtime2Kb(String ten_nohin_rtime_2_kb)
	{
		this.ten_nohin_rtime_2_kb = ten_nohin_rtime_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_rtime_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinRtime2Kb()
	{
		return cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH);
	}
	public String getTenNohinRtime2KbString()
	{
		return "'" + cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinRtime2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH));
	}

	// ten_nohin_time_2_kbに対するセッターとゲッターの集合
	public boolean setTenNohinTime2Kb(String ten_nohin_time_2_kb)
	{
		this.ten_nohin_time_2_kb = ten_nohin_time_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_time_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinTime2Kb()
	{
		return cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH);
	}
	public String getTenNohinTime2KbString()
	{
		return "'" + cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinTime2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH));
	}

	// bin_3_kbに対するセッターとゲッターの集合
	public boolean setBin3Kb(String bin_3_kb)
	{
		this.bin_3_kb = bin_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bin_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getBin3Kb()
	{
		return cutString(this.bin_3_kb,BIN_3_KB_MAX_LENGTH);
	}
	public String getBin3KbString()
	{
		return "'" + cutString(this.bin_3_kb,BIN_3_KB_MAX_LENGTH) + "'";
	}
	public String getBin3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_3_kb,BIN_3_KB_MAX_LENGTH));
	}

	// hachu_pattern_3_kbに対するセッターとゲッターの集合
	public boolean setHachuPattern3Kb(String hachu_pattern_3_kb)
	{
		this.hachu_pattern_3_kb = hachu_pattern_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachu_pattern_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHachuPattern3Kb()
	{
		return cutString(this.hachu_pattern_3_kb,HACHU_PATTERN_3_KB_MAX_LENGTH);
	}
	public String getHachuPattern3KbString()
	{
		return "'" + cutString(this.hachu_pattern_3_kb,HACHU_PATTERN_3_KB_MAX_LENGTH) + "'";
	}
	public String getHachuPattern3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_3_kb,HACHU_PATTERN_3_KB_MAX_LENGTH));
	}

	// sime_time_3_qtに対するセッターとゲッターの集合
	public boolean setSimeTime3Qt(String sime_time_3_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_3_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.sime_time_3_qt = Long.parseLong(sime_time_3_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSimeTime3Qt(long sime_time_3_qt)
	{
		this.sime_time_3_qt = sime_time_3_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_3_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getSimeTime3Qt()
	{
		return this.sime_time_3_qt;
	}
	public String getSimeTime3QtString()
	{
		return Long.toString(this.sime_time_3_qt);
	}

	// c_nohin_rtime_3_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime3Kb(String c_nohin_rtime_3_kb)
	{
		this.c_nohin_rtime_3_kb = c_nohin_rtime_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("c_nohin_rtime_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCNohinRtime3Kb()
	{
		return cutString(this.c_nohin_rtime_3_kb,C_NOHIN_RTIME_3_KB_MAX_LENGTH);
	}
	public String getCNohinRtime3KbString()
	{
		return "'" + cutString(this.c_nohin_rtime_3_kb,C_NOHIN_RTIME_3_KB_MAX_LENGTH) + "'";
	}
	public String getCNohinRtime3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_3_kb,C_NOHIN_RTIME_3_KB_MAX_LENGTH));
	}

	// ten_nohin_rtime_3_kbに対するセッターとゲッターの集合
	public boolean setTenNohinRtime3Kb(String ten_nohin_rtime_3_kb)
	{
		this.ten_nohin_rtime_3_kb = ten_nohin_rtime_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_rtime_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinRtime3Kb()
	{
		return cutString(this.ten_nohin_rtime_3_kb,TEN_NOHIN_RTIME_3_KB_MAX_LENGTH);
	}
	public String getTenNohinRtime3KbString()
	{
		return "'" + cutString(this.ten_nohin_rtime_3_kb,TEN_NOHIN_RTIME_3_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinRtime3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_rtime_3_kb,TEN_NOHIN_RTIME_3_KB_MAX_LENGTH));
	}

	// ten_nohin_time_3_kbに対するセッターとゲッターの集合
	public boolean setTenNohinTime3Kb(String ten_nohin_time_3_kb)
	{
		this.ten_nohin_time_3_kb = ten_nohin_time_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_time_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinTime3Kb()
	{
		return cutString(this.ten_nohin_time_3_kb,TEN_NOHIN_TIME_3_KB_MAX_LENGTH);
	}
	public String getTenNohinTime3KbString()
	{
		return "'" + cutString(this.ten_nohin_time_3_kb,TEN_NOHIN_TIME_3_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinTime3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_time_3_kb,TEN_NOHIN_TIME_3_KB_MAX_LENGTH));
	}

	// c_nohin_rtime_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtimeKb(String c_nohin_rtime_kb)
	{
		this.c_nohin_rtime_kb = c_nohin_rtime_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("c_nohin_rtime_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCNohinRtimeKb()
	{
		return cutString(this.c_nohin_rtime_kb,C_NOHIN_RTIME_KB_MAX_LENGTH);
	}
	public String getCNohinRtimeKbString()
	{
		return "'" + cutString(this.c_nohin_rtime_kb,C_NOHIN_RTIME_KB_MAX_LENGTH) + "'";
	}
	public String getCNohinRtimeKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_kb,C_NOHIN_RTIME_KB_MAX_LENGTH));
	}

	// zei_kbに対するセッターとゲッターの集合
	public boolean setZeiKb(String zei_kb)
	{
		this.zei_kb = zei_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("zei_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getZeiKb()
	{
		return cutString(this.zei_kb,ZEI_KB_MAX_LENGTH);
	}
	public String getZeiKbString()
	{
		return "'" + cutString(this.zei_kb,ZEI_KB_MAX_LENGTH) + "'";
	}
	public String getZeiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zei_kb,ZEI_KB_MAX_LENGTH));
	}

	// hanbai_kikan_kbに対するセッターとゲッターの集合
	public boolean setHanbaiKikanKb(String hanbai_kikan_kb)
	{
		this.hanbai_kikan_kb = hanbai_kikan_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("zei_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHanbaiKikanKb()
	{
		return cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH);
	}
	public String getHanbaiKikanKbString()
	{
		return "'" + cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getHanbaiKikanKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH));
	}

	// syohin_kbに対するセッターとゲッターの集合
	public boolean setSyohinKb(String syohin_kb)
	{
		this.syohin_kb = syohin_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSyohinKb()
	{
		return cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH);
	}
	public String getSyohinKbString()
	{
		return "'" + cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH));
	}

	// buturyu_kbに対するセッターとゲッターの集合
	public boolean setButuryuKb(String buturyu_kb)
	{
		this.buturyu_kb = buturyu_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("buturyu_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getButuryuKb()
	{
		return cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH);
	}
	public String getButuryuKbString()
	{
		return "'" + cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH) + "'";
	}
	public String getButuryuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH));
	}

	// yokomoti_kbに対するセッターとゲッターの集合
	public boolean setYokomotiKb(String yokomoti_kb)
	{
		this.yokomoti_kb = yokomoti_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("yokomoti_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getYokomotiKb()
	{
		return cutString(this.yokomoti_kb,YOKOMOTI_KB_MAX_LENGTH);
	}
	public String getYokomotiKbString()
	{
		return "'" + cutString(this.yokomoti_kb,YOKOMOTI_KB_MAX_LENGTH) + "'";
	}
	public String getYokomotiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yokomoti_kb,YOKOMOTI_KB_MAX_LENGTH));
	}

	// ten_groupno_cdに対するセッターとゲッターの集合
	public boolean setTenGroupnoCd(String ten_groupno_cd)
	{
		this.ten_groupno_cd = ten_groupno_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_groupno_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenGroupnoCd()
	{
		return cutString(this.ten_groupno_cd,TEN_GROUPNO_CD_MAX_LENGTH);
	}
	public String getTenGroupnoCdString()
	{
		return "'" + cutString(this.ten_groupno_cd,TEN_GROUPNO_CD_MAX_LENGTH) + "'";
	}
	public String getTenGroupnoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_groupno_cd,TEN_GROUPNO_CD_MAX_LENGTH));
	}

	// ten_zaiko_kbに対するセッターとゲッターの集合
	public boolean setTenZaikoKb(String ten_zaiko_kb)
	{
		this.ten_zaiko_kb = ten_zaiko_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_zaiko_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenZaikoKb()
	{
		return cutString(this.ten_zaiko_kb,TEN_ZAIKO_KB_MAX_LENGTH);
	}
	public String getTenZaikoKbString()
	{
		return "'" + cutString(this.ten_zaiko_kb,TEN_ZAIKO_KB_MAX_LENGTH) + "'";
	}
	public String getTenZaikoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_zaiko_kb,TEN_ZAIKO_KB_MAX_LENGTH));
	}

	// hanbai_seisaku_kbに対するセッターとゲッターの集合
	public boolean setHanbaiSeisakuKb(String hanbai_seisaku_kb)
	{
		this.hanbai_seisaku_kb = hanbai_seisaku_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hanbai_seisaku_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHanbaiSeisakuKb()
	{
		return cutString(this.hanbai_seisaku_kb,HANBAI_SEISAKU_KB_MAX_LENGTH);
	}
	public String getHanbaiSeisakuKbString()
	{
		return "'" + cutString(this.hanbai_seisaku_kb,HANBAI_SEISAKU_KB_MAX_LENGTH) + "'";
	}
	public String getHanbaiSeisakuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_seisaku_kb,HANBAI_SEISAKU_KB_MAX_LENGTH));
	}

	// henpin_nbに対するセッターとゲッターの集合
// ↓↓ DB変更に伴う対応(2005.09.18 Y.Inaba)
/*	public boolean setHenpinNb(String henpin_nb)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("henpin_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.henpin_nb = Long.parseLong(henpin_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHenpinNb(long henpin_nb)
	{
		this.henpin_nb = henpin_nb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("henpin_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getHenpinNb()
	{
		return this.henpin_nb;
	}
	public String getHenpinNbString()
	{
		return Long.toString(this.henpin_nb);
	}
*/
	public boolean setHenpinNb(String henpin_nb){
		this.henpin_nb = henpin_nb;
		gamenFlg.put("henpin_nb","1");
		return true;
	}
	public String getHenpinNb(){
		return cutString(this.henpin_nb,HENPIN_NB_MAX_LENGTH);
	}
	public String getHenpinNbString(){
		return "'" + cutString(this.henpin_nb,HENPIN_NB_MAX_LENGTH) + "'";
	}
	public String getHenpinNbHTMLString(){
		return HTMLStringUtil.convert(cutString(this.henpin_nb,HENPIN_NB_MAX_LENGTH));
	}
// ↑↑ DB変更に伴う対応(2005.09.18 Y.Inaba)

	// henpin_genka_vlに対するセッターとゲッターの集合
	public boolean setHenpinGenkaVl(String henpin_genka_vl)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("henpin_genka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.henpin_genka_vl = Long.parseLong(henpin_genka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHenpinGenkaVl(long henpin_genka_vl)
	{
		this.henpin_genka_vl = henpin_genka_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("henpin_genka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getHenpinGenkaVl()
	{
		return this.henpin_genka_vl;
	}
	public String getHenpinGenkaVlString()
	{
		return Long.toString(this.henpin_genka_vl);
	}

	// rebate_fgに対するセッターとゲッターの集合
	public boolean setRebateFg(String rebate_fg)
	{
		this.rebate_fg = rebate_fg;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("rebate_fg","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getRebateFg()
	{
		return cutString(this.rebate_fg,REBATE_FG_MAX_LENGTH);
	}
	public String getRebateFgString()
	{
		return "'" + cutString(this.rebate_fg,REBATE_FG_MAX_LENGTH) + "'";
	}
	public String getRebateFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rebate_fg,REBATE_FG_MAX_LENGTH));
	}

	// hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setHanbaiStDt(String hanbai_st_dt)
	{
		this.hanbai_st_dt = hanbai_st_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hanbai_st_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHanbaiStDt()
	{
		return cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getHanbaiStDtString()
	{
		return "'" + cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH));
	}

	// hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setHanbaiEdDt(String hanbai_ed_dt)
	{
		this.hanbai_ed_dt = hanbai_ed_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hanbai_ed_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHanbaiEdDt()
	{
		return cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getHanbaiEdDtString()
	{
		return "'" + cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH));
	}

	// hanbai_limit_qtに対するセッターとゲッターの集合
	public boolean setHanbaiLimitQt(String hanbai_limit_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hanbai_limit_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.hanbai_limit_qt = Long.parseLong(hanbai_limit_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHanbaiLimitQt(long hanbai_limit_qt)
	{
		this.hanbai_limit_qt = hanbai_limit_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hanbai_limit_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getHanbaiLimitQt()
	{
		return this.hanbai_limit_qt;
	}
	public String getHanbaiLimitQtString()
	{
		return Long.toString(this.hanbai_limit_qt);
	}

	// hanbai_limit_kbに対するセッターとゲッターの集合
	public boolean setHanbaiLimitKb(String hanbai_limit_kb)
	{
		this.hanbai_limit_kb = hanbai_limit_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hanbai_limit_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHanbaiLimitKb()
	{
		return cutString(this.hanbai_limit_kb,HANBAI_LIMIT_KB_MAX_LENGTH);
	}
	public String getHanbaiLimitKbString()
	{
		return "'" + cutString(this.hanbai_limit_kb,HANBAI_LIMIT_KB_MAX_LENGTH) + "'";
	}
	public String getHanbaiLimitKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_limit_kb,HANBAI_LIMIT_KB_MAX_LENGTH));
	}

	// auto_del_kbに対するセッターとゲッターの集合
	public boolean setAutoDelKb(String auto_del_kb)
	{
		this.auto_del_kb = auto_del_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("auto_del_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getAutoDelKb()
	{
		return cutString(this.auto_del_kb,AUTO_DEL_KB_MAX_LENGTH);
	}
	public String getAutoDelKbString()
	{
		return "'" + cutString(this.auto_del_kb,AUTO_DEL_KB_MAX_LENGTH) + "'";
	}
	public String getAutoDelKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.auto_del_kb,AUTO_DEL_KB_MAX_LENGTH));
	}

	// got_mujyoken_fgに対するセッターとゲッターの集合
	public boolean setGotMujyokenFg(String got_mujyoken_fg)
	{
		this.got_mujyoken_fg = got_mujyoken_fg;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("got_mujyoken_fg","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getGotMujyokenFg()
	{
		return cutString(this.got_mujyoken_fg,GOT_MUJYOKEN_FG_MAX_LENGTH);
	}
	public String getGotMujyokenFgString()
	{
		return "'" + cutString(this.got_mujyoken_fg,GOT_MUJYOKEN_FG_MAX_LENGTH) + "'";
	}
	public String getGotMujyokenFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.got_mujyoken_fg,GOT_MUJYOKEN_FG_MAX_LENGTH));
	}

	// got_start_mmに対するセッターとゲッターの集合
	public boolean setGotStartMm(String got_start_mm)
	{
		this.got_start_mm = got_start_mm;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("got_start_mm","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getGotStartMm()
	{
		return cutString(this.got_start_mm,GOT_START_MM_MAX_LENGTH);
	}
	public String getGotStartMmString()
	{
		return "'" + cutString(this.got_start_mm,GOT_START_MM_MAX_LENGTH) + "'";
	}
	public String getGotStartMmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.got_start_mm,GOT_START_MM_MAX_LENGTH));
	}

	// got_end_mmに対するセッターとゲッターの集合
	public boolean setGotEndMm(String got_end_mm)
	{
		this.got_end_mm = got_end_mm;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("got_end_mm","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getGotEndMm()
	{
		return cutString(this.got_end_mm,GOT_END_MM_MAX_LENGTH);
	}
	public String getGotEndMmString()
	{
		return "'" + cutString(this.got_end_mm,GOT_END_MM_MAX_LENGTH) + "'";
	}
	public String getGotEndMmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.got_end_mm,GOT_END_MM_MAX_LENGTH));
	}

	// e_shop_kbに対するセッターとゲッターの集合
	public boolean setEShopKb(String e_shop_kb)
	{
		this.e_shop_kb = e_shop_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("e_shop_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getEShopKb()
	{
		return cutString(this.e_shop_kb,E_SHOP_KB_MAX_LENGTH);
	}
	public String getEShopKbString()
	{
		return "'" + cutString(this.e_shop_kb,E_SHOP_KB_MAX_LENGTH) + "'";
	}
	public String getEShopKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.e_shop_kb,E_SHOP_KB_MAX_LENGTH));
	}

	// plu_send_dtに対するセッターとゲッターの集合
	public boolean setPluSendDt(String plu_send_dt)
	{
		this.plu_send_dt = plu_send_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("plu_send_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getPluSendDt()
	{
		return cutString(this.plu_send_dt,PLU_SEND_DT_MAX_LENGTH);
	}
	public String getPluSendDtString()
	{
		return "'" + cutString(this.plu_send_dt,PLU_SEND_DT_MAX_LENGTH) + "'";
	}
	public String getPluSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.plu_send_dt,PLU_SEND_DT_MAX_LENGTH));
	}

	// rec_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setRecHinmeiKanjiNa(String rec_hinmei_kanji_na)
	{
		this.rec_hinmei_kanji_na = rec_hinmei_kanji_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("rec_hinmei_kanji_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getRecHinmeiKanjiNa()
	{
		return cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getRecHinmeiKanjiNaString()
	{
		return "'" + cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getRecHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA_MAX_LENGTH));
	}

	// rec_hinmei_kana_naに対するセッターとゲッターの集合
	public boolean setRecHinmeiKanaNa(String rec_hinmei_kana_na)
	{
		this.rec_hinmei_kana_na = rec_hinmei_kana_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("rec_hinmei_kana_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getRecHinmeiKanaNa()
	{
		return cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA_MAX_LENGTH);
	}
	public String getRecHinmeiKanaNaString()
	{
		return "'" + cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getRecHinmeiKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA_MAX_LENGTH));
	}

	// keyplu_fgに対するセッターとゲッターの集合
	public boolean setKeypluFg(String keyplu_fg)
	{
		this.keyplu_fg = keyplu_fg;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("keyplu_fg","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getKeypluFg()
	{
		return cutString(this.keyplu_fg,KEYPLU_FG_MAX_LENGTH);
	}
	public String getKeypluFgString()
	{
		return "'" + cutString(this.keyplu_fg,KEYPLU_FG_MAX_LENGTH) + "'";
	}
	public String getKeypluFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keyplu_fg,KEYPLU_FG_MAX_LENGTH));
	}

	// pc_kbに対するセッターとゲッターの集合
	public boolean setPcKb(String pc_kb)
	{
		this.pc_kb = pc_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pc_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getPcKb()
	{
		return cutString(this.pc_kb,PC_KB_MAX_LENGTH);
	}
	public String getPcKbString()
	{
		return "'" + cutString(this.pc_kb,PC_KB_MAX_LENGTH) + "'";
	}
	public String getPcKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pc_kb,PC_KB_MAX_LENGTH));
	}

	// daisi_no_nbに対するセッターとゲッターの集合
	public boolean setDaisiNoNb(String daisi_no_nb)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("daisi_no_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
			this.daisi_no_nb = daisi_no_nb;
			return true;
	}

	public String getDaisiNoNb()
	{
		return this.daisi_no_nb;
	}


	// unit_price_tani_kbに対するセッターとゲッターの集合
	public boolean setUnitPriceTaniKb(String unit_price_tani_kb)
	{
		this.unit_price_tani_kb = unit_price_tani_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("unit_price_tani_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getUnitPriceTaniKb()
	{
		return cutString(this.unit_price_tani_kb,UNIT_PRICE_TANI_KB_MAX_LENGTH);
	}
	public String getUnitPriceTaniKbString()
	{
		return "'" + cutString(this.unit_price_tani_kb,UNIT_PRICE_TANI_KB_MAX_LENGTH) + "'";
	}
	public String getUnitPriceTaniKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_price_tani_kb,UNIT_PRICE_TANI_KB_MAX_LENGTH));
	}

	// unit_price_naiyoryo_qtに対するセッターとゲッターの集合
	public boolean setUnitPriceNaiyoryoQt(String unit_price_naiyoryo_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("unit_price_naiyoryo_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
//			↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
			this.st_unit_price_naiyoryo_qt = unit_price_naiyoryo_qt;
//			↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
			this.unit_price_naiyoryo_qt = Long.parseLong(unit_price_naiyoryo_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setUnitPriceNaiyoryoQt(long unit_price_naiyoryo_qt)
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
		this.st_unit_price_naiyoryo_qt = Long.toString(unit_price_naiyoryo_qt);
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
		this.unit_price_naiyoryo_qt = unit_price_naiyoryo_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("unit_price_naiyoryo_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
//	↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//	public long getUnitPriceNaiyoryoQt()
//	{
//		return this.unit_price_naiyoryo_qt;
//	}
//	↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	public String getUnitPriceNaiyoryoQt()
	{
		return this.st_unit_price_naiyoryo_qt;
	}
	public String getUnitPriceNaiyoryoQtString()
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//		return Long.toString(this.unit_price_naiyoryo_qt);
		return this.st_unit_price_naiyoryo_qt;
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	}

	// unit_price_kijun_tani_qtに対するセッターとゲッターの集合
	public boolean setUnitPriceKijunTaniQt(String unit_price_kijun_tani_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("unit_price_kijun_tani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
//			↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
			this.st_unit_price_kijun_tani_qt = unit_price_kijun_tani_qt;
//			↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
			this.unit_price_kijun_tani_qt = Long.parseLong(unit_price_kijun_tani_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setUnitPriceKijunTaniQt(long unit_price_kijun_tani_qt)
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
		this.st_unit_price_kijun_tani_qt = Long.toString(unit_price_kijun_tani_qt);
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
		this.unit_price_kijun_tani_qt = unit_price_kijun_tani_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("unit_price_kijun_tani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
//	↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//	public long getUnitPriceKijunTaniQt()
//	{
//		return this.unit_price_kijun_tani_qt;
//	}
	public String getUnitPriceKijunTaniQt()
	{
		return this.st_unit_price_kijun_tani_qt;
	}
//	↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	public String getUnitPriceKijunTaniQtString()
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//		return Long.toString(this.unit_price_kijun_tani_qt);
		return this.st_unit_price_kijun_tani_qt;
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	}

	// shinazoroe_kbに対するセッターとゲッターの集合
	public boolean setShinazoroeKb(String shinazoroe_kb)
	{
		this.shinazoroe_kb = shinazoroe_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("shinazoroe_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getShinazoroeKb()
	{
		return cutString(this.shinazoroe_kb,SHINAZOROE_KB_MAX_LENGTH);
	}
	public String getShinazoroeKbString()
	{
		return "'" + cutString(this.shinazoroe_kb,SHINAZOROE_KB_MAX_LENGTH) + "'";
	}
	public String getShinazoroeKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.shinazoroe_kb,SHINAZOROE_KB_MAX_LENGTH));
	}

	// kumisu_kbに対するセッターとゲッターの集合
	public boolean setKumisuKb(String kumisu_kb)
	{
		this.kumisu_kb = kumisu_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("kumisu_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getKumisuKb()
	{
		return cutString(this.kumisu_kb,KUMISU_KB_MAX_LENGTH);
	}
	public String getKumisuKbString()
	{
		return "'" + cutString(this.kumisu_kb,KUMISU_KB_MAX_LENGTH) + "'";
	}
	public String getKumisuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kumisu_kb,KUMISU_KB_MAX_LENGTH));
	}

	// nefuda_kbに対するセッターとゲッターの集合
	public boolean setNefudaKb(String nefuda_kb)
	{
		this.nefuda_kb = nefuda_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("nefuda_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getNefudaKb()
	{
		return cutString(this.nefuda_kb,NEFUDA_KB_MAX_LENGTH);
	}
	public String getNefudaKbString()
	{
		return "'" + cutString(this.nefuda_kb,NEFUDA_KB_MAX_LENGTH) + "'";
	}
	public String getNefudaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefuda_kb,NEFUDA_KB_MAX_LENGTH));
	}
	// nefuda_dtに対するセッターとゲッターの集合
	public boolean setNefudaDt(String nefuda_dt)
	{
		this.nefuda_dt = nefuda_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("nefuda_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getNefudaDt()
	{
		return cutString(this.nefuda_dt,NEFUDA_DT_MAX_LENGTH);
	}
	public String getNefudaDtString()
	{
		return "'" + cutString(this.nefuda_dt,NEFUDA_DT_MAX_LENGTH) + "'";
	}
	public String getNefudaDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefuda_dt,NEFUDA_DT_MAX_LENGTH));
	}
	// tag_hako_qtに対するセッターとゲッターの集合
	public boolean setTagHakoQt(String tag_hako_qt)
	{
		this.tag_hako_qt = tag_hako_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tag_hako_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTagHakoQt()
	{
		return cutString(this.tag_hako_qt,TAG_HAKO_QT_MAX_LENGTH);
	}
	public String getTagHakoQtString()
	{
		return "'" + cutString(this.tag_hako_qt,TAG_HAKO_QT_MAX_LENGTH) + "'";
	}
	public String getTagHakoQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tag_hako_qt,TAG_HAKO_QT_MAX_LENGTH));
	}

	// yoridori_kbに対するセッターとゲッターの集合
	public boolean setYoridoriKb(String yoridori_kb)
	{
		this.yoridori_kb = yoridori_kb;
		gamenFlg.put("yoridori_kb","1");
		return true;
	}
	public String getYoridoriKb()
	{
		return cutString(this.yoridori_kb,YORIDORI_KB_MAX_LENGTH);
	}
	public String getYoridoriKbString()
	{
		return "'" + cutString(this.yoridori_kb,YORIDORI_KB_MAX_LENGTH) + "'";
	}
	public String getYoridoriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yoridori_kb,YORIDORI_KB_MAX_LENGTH));
	}

	// yoridori_qtに対するセッターとゲッターの集合
	public boolean setYoridoriQt(String yoridori_qt)
	{
		this.yoridori_qt = yoridori_qt;
		gamenFlg.put("yoridori_qt","1");
		return true;
	}
	public String getYoridoriQt()
	{
		if(this.yoridori_qt == null){
			return "";
		} else {
			return this.yoridori_qt;
		}
	}
	public String getYoridoriQtString()
	{
		return this.yoridori_qt;
	}

	// tag_hyoji_baika_vlに対するセッターとゲッターの集合
	public boolean setTagHyojiBaikaVl(String tag_hyoji_baika_vl)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tag_hyoji_baika_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.tag_hyoji_baika_vl = Long.parseLong(tag_hyoji_baika_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTagHyojiBaikaVl(long tag_hyoji_baika_vl)
	{
		this.tag_hyoji_baika_vl = tag_hyoji_baika_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tag_hyoji_baika_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getTagHyojiBaikaVl()
	{
		return this.tag_hyoji_baika_vl;
	}
	public String getTagHyojiBaikaVlString()
	{
		return Long.toString(this.tag_hyoji_baika_vl);
	}

	// season_cdに対するセッターとゲッターの集合
	public boolean setSeasonCd(String season_cd)
	{
		this.season_cd = season_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("season_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSeasonCd()
	{
		return cutString(this.season_cd,SEASON_CD_MAX_LENGTH);
	}
	public String getSeasonCdString()
	{
		return "'" + cutString(this.season_cd,SEASON_CD_MAX_LENGTH) + "'";
	}
	public String getSeasonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.season_cd,SEASON_CD_MAX_LENGTH));
	}

	// hukusyu_cdに対するセッターとゲッターの集合
	public boolean setHukusyuCd(String hukusyu_cd)
	{
		this.hukusyu_cd = hukusyu_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hukusyu_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHukusyuCd()
	{
		return cutString(this.hukusyu_cd,HUKUSYU_CD_MAX_LENGTH);
	}
	public String getHukusyuCdString()
	{
		return "'" + cutString(this.hukusyu_cd,HUKUSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHukusyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hukusyu_cd,HUKUSYU_CD_MAX_LENGTH));
	}

	// style_cdに対するセッターとゲッターの集合
	public boolean setStyleCd(String style_cd)
	{
		this.style_cd = style_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("style_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStyleCd()
	{
		return cutString(this.style_cd,STYLE_CD_MAX_LENGTH);
	}
	public String getStyleCdString()
	{
		return "'" + cutString(this.style_cd,STYLE_CD_MAX_LENGTH) + "'";
	}
	public String getStyleCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.style_cd,STYLE_CD_MAX_LENGTH));
	}

	// scene_cdに対するセッターとゲッターの集合
	public boolean setSceneCd(String scene_cd)
	{
		this.scene_cd = scene_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("scene_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSceneCd()
	{
		return cutString(this.scene_cd,SCENE_CD_MAX_LENGTH);
	}
	public String getSceneCdString()
	{
		return "'" + cutString(this.scene_cd,SCENE_CD_MAX_LENGTH) + "'";
	}
	public String getSceneCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.scene_cd,SCENE_CD_MAX_LENGTH));
	}

	// sex_cdに対するセッターとゲッターの集合
	public boolean setSexCd(String sex_cd)
	{
		this.sex_cd = sex_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sex_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSexCd()
	{
		return cutString(this.sex_cd,SEX_CD_MAX_LENGTH);
	}
	public String getSexCdString()
	{
		return "'" + cutString(this.sex_cd,SEX_CD_MAX_LENGTH) + "'";
	}
	public String getSexCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sex_cd,SEX_CD_MAX_LENGTH));
	}

	// age_cdに対するセッターとゲッターの集合
	public boolean setAgeCd(String age_cd)
	{
		this.age_cd = age_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("age_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getAgeCd()
	{
		return cutString(this.age_cd,AGE_CD_MAX_LENGTH);
	}
	public String getAgeCdString()
	{
		return "'" + cutString(this.age_cd,AGE_CD_MAX_LENGTH) + "'";
	}
	public String getAgeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.age_cd,AGE_CD_MAX_LENGTH));
	}

	// generation_cdに対するセッターとゲッターの集合
	public boolean setGenerationCd(String generation_cd)
	{
		this.generation_cd = generation_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("generation_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getGenerationCd()
	{
		return cutString(this.generation_cd,GENERATION_CD_MAX_LENGTH);
	}
	public String getGenerationCdString()
	{
		return "'" + cutString(this.generation_cd,GENERATION_CD_MAX_LENGTH) + "'";
	}
	public String getGenerationCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.generation_cd,GENERATION_CD_MAX_LENGTH));
	}

	// sozai_cdに対するセッターとゲッターの集合
	public boolean setSozaiCd(String sozai_cd)
	{
		this.sozai_cd = sozai_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sozai_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSozaiCd()
	{
		return cutString(this.sozai_cd,SOZAI_CD_MAX_LENGTH);
	}
	public String getSozaiCdString()
	{
		return "'" + cutString(this.sozai_cd,SOZAI_CD_MAX_LENGTH) + "'";
	}
	public String getSozaiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sozai_cd,SOZAI_CD_MAX_LENGTH));
	}

	// pattern_cdに対するセッターとゲッターの集合
	public boolean setPatternCd(String pattern_cd)
	{
		this.pattern_cd = pattern_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pattern_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getPatternCd()
	{
		return cutString(this.pattern_cd,PATTERN_CD_MAX_LENGTH);
	}
	public String getPatternCdString()
	{
		return "'" + cutString(this.pattern_cd,PATTERN_CD_MAX_LENGTH) + "'";
	}
	public String getPatternCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pattern_cd,PATTERN_CD_MAX_LENGTH));
	}

	// oriami_cdに対するセッターとゲッターの集合
	public boolean setOriamiCd(String oriami_cd)
	{
		this.oriami_cd = oriami_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("oriami_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getOriamiCd()
	{
		return cutString(this.oriami_cd,ORIAMI_CD_MAX_LENGTH);
	}
	public String getOriamiCdString()
	{
		return "'" + cutString(this.oriami_cd,ORIAMI_CD_MAX_LENGTH) + "'";
	}
	public String getOriamiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.oriami_cd,ORIAMI_CD_MAX_LENGTH));
	}

	// huka_kino_cdに対するセッターとゲッターの集合
	public boolean setHukaKinoCd(String huka_kino_cd)
	{
		this.huka_kino_cd = huka_kino_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("huka_kino_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHukaKinoCd()
	{
		return cutString(this.huka_kino_cd,HUKA_KINO_CD_MAX_LENGTH);
	}
	public String getHukaKinoCdString()
	{
		return "'" + cutString(this.huka_kino_cd,HUKA_KINO_CD_MAX_LENGTH) + "'";
	}
	public String getHukaKinoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.huka_kino_cd,HUKA_KINO_CD_MAX_LENGTH));
	}

	// size_cdに対するセッターとゲッターの集合
	public boolean setSizeCd(String size_cd)
	{
		this.size_cd = size_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("size_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSizeCd()
	{
		return cutString(this.size_cd,SIZE_CD_MAX_LENGTH);
	}
	public String getSizeCdString()
	{
		return "'" + cutString(this.size_cd,SIZE_CD_MAX_LENGTH) + "'";
	}
	public String getSizeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_cd,SIZE_CD_MAX_LENGTH));
	}

	// color_cdに対するセッターとゲッターの集合
	public boolean setColorCd(String color_cd)
	{
		this.color_cd = color_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("color_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getColorCd()
	{
		return cutString(this.color_cd,COLOR_CD_MAX_LENGTH);
	}
	public String getColorCdString()
	{
		return "'" + cutString(this.color_cd,COLOR_CD_MAX_LENGTH) + "'";
	}
	public String getColorCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_cd,COLOR_CD_MAX_LENGTH));
	}

	// syuzei_hokoku_kbに対するセッターとゲッターの集合
	public boolean setSyuzeiHokokuKb(String syuzei_hokoku_kb)
	{
		this.syuzei_hokoku_kb = syuzei_hokoku_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syuzei_hokoku_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSyuzeiHokokuKb()
	{
		return cutString(this.syuzei_hokoku_kb,SYUZEI_HOKOKU_KB_MAX_LENGTH);
	}
	public String getSyuzeiHokokuKbString()
	{
		return "'" + cutString(this.syuzei_hokoku_kb,SYUZEI_HOKOKU_KB_MAX_LENGTH) + "'";
	}
	public String getSyuzeiHokokuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syuzei_hokoku_kb,SYUZEI_HOKOKU_KB_MAX_LENGTH));
	}

	// tc_jyouho_naに対するセッターとゲッターの集合
	public boolean setTcJyouhoNa(String tc_jyouho_na)
	{
		this.tc_jyouho_na = tc_jyouho_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tc_jyouho_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTcJyouhoNa()
	{
		return cutString(this.tc_jyouho_na,TC_JYOUHO_NA_MAX_LENGTH);
	}
	public String getTcJyouhoNaString()
	{
		return "'" + cutString(this.tc_jyouho_na,TC_JYOUHO_NA_MAX_LENGTH) + "'";
	}
	public String getTcJyouhoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tc_jyouho_na,TC_JYOUHO_NA_MAX_LENGTH));
	}

	// nohin_syohin_cdに対するセッターとゲッターの集合
	public boolean setNohinSyohinCd(String nohin_syohin_cd)
	{
		this.nohin_syohin_cd = nohin_syohin_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("nohin_syohin_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getNohinSyohinCd()
	{
		return cutString(this.nohin_syohin_cd,NOHIN_SYOHIN_CD_MAX_LENGTH);
	}
	public String getNohinSyohinCdString()
	{
		return "'" + cutString(this.nohin_syohin_cd,NOHIN_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getNohinSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_syohin_cd,NOHIN_SYOHIN_CD_MAX_LENGTH));
	}

	// itfCdに対するセッターとゲッターの集合
	public boolean setItfCd(String itfCd)
	{
		this.itfCd = itfCd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("itfCd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getItfCd()
	{
		return cutString(this.itfCd,ITFCD_MAX_LENGTH);
	}
	public String getItfCdString()
	{
		return "'" + cutString(this.itfCd,ITFCD_MAX_LENGTH) + "'";
	}
	public String getItfCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.itfCd,ITFCD_MAX_LENGTH));
	}

	// case_irisu_qtに対するセッターとゲッターの集合
	public boolean setCaseIrisuQt(String case_irisu_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("case_irisu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.case_irisu_qt = Double.parseDouble(case_irisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setCaseIrisuQt(long case_irisu_qt)
	{
		this.case_irisu_qt = case_irisu_qt;
		return true;
	}
	public double getCaseIrisuQt()
	{
		return this.case_irisu_qt;
	}
	public String getCaseIrisuQtString()
	{
		return Double.toString(this.case_irisu_qt);
	}

	// nyuka_syohin_cdに対するセッターとゲッターの集合
	public boolean setNyukaSyohinCd(String nyuka_syohin_cd)
	{
		this.nyuka_syohin_cd = nyuka_syohin_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("nyuka_syohin_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getNyukaSyohinCd()
	{
		return cutString(this.nyuka_syohin_cd,NYUKA_SYOHIN_CD_MAX_LENGTH);
	}
	public String getNyukaSyohinCdString()
	{
		return "'" + cutString(this.nyuka_syohin_cd,NYUKA_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getNyukaSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nyuka_syohin_cd,NYUKA_SYOHIN_CD_MAX_LENGTH));
	}

	// pack_width_qtに対するセッターとゲッターの集合
	public boolean setPackWidthQt(String pack_width_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_width_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.pack_width_qt = Long.parseLong(pack_width_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPackWidthQt(long pack_width_qt)
	{
		this.pack_width_qt = pack_width_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_width_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getPackWidthQt()
	{
		return this.pack_width_qt;
	}
	public String getPackWidthQtString()
	{
		return Long.toString(this.pack_width_qt);
	}

	// pack_heigth_qtに対するセッターとゲッターの集合
	public boolean setPackHeigthQt(String pack_heigth_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_heigth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.pack_heigth_qt = Long.parseLong(pack_heigth_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPackHeigthQt(long pack_heigth_qt)
	{
		this.pack_heigth_qt = pack_heigth_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_heigth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getPackHeigthQt()
	{
		return this.pack_heigth_qt;
	}
	public String getPackHeigthQtString()
	{
		return Long.toString(this.pack_heigth_qt);
	}

	// pack_depth_qtに対するセッターとゲッターの集合
	public boolean setPackDepthQt(String pack_depth_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_depth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.pack_depth_qt = Long.parseLong(pack_depth_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPackDepthQt(long pack_depth_qt)
	{
		this.pack_depth_qt = pack_depth_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_depth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getPackDepthQt()
	{
		return this.pack_depth_qt;
	}
	public String getPackDepthQtString()
	{
		return Long.toString(this.pack_depth_qt);
	}

	// pack_weight_qtに対するセッターとゲッターの集合
	public boolean setPackWeightQt(String pack_weight_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_weight_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.pack_weight_qt = Long.parseLong(pack_weight_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPackWeightQt(long pack_weight_qt)
	{
		this.pack_weight_qt = pack_weight_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_weight_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getPackWeightQt()
	{
		return this.pack_weight_qt;
	}
	public String getPackWeightQtString()
	{
		return Long.toString(this.pack_weight_qt);
	}

	// center_zaiko_kbに対するセッターとゲッターの集合
	public boolean setCenterZaikoKb(String center_zaiko_kb)
	{
		this.center_zaiko_kb = center_zaiko_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_zaiko_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCenterZaikoKb()
	{
		return cutString(this.center_zaiko_kb,CENTER_ZAIKO_KB_MAX_LENGTH);
	}
	public String getCenterZaikoKbString()
	{
		return "'" + cutString(this.center_zaiko_kb,CENTER_ZAIKO_KB_MAX_LENGTH) + "'";
	}
	public String getCenterZaikoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_zaiko_kb,CENTER_ZAIKO_KB_MAX_LENGTH));
	}

	// zaiko_hachu_sakiに対するセッターとゲッターの集合
	public boolean setZaikoHachuSaki(String zaiko_hachu_saki)
	{
		this.zaiko_hachu_saki = zaiko_hachu_saki;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("zaiko_hachu_saki","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getZaikoHachuSaki()
	{
		return cutString(this.zaiko_hachu_saki,ZAIKO_HACHU_SAKI_MAX_LENGTH);
	}
	public String getZaikoHachuSakiString()
	{
		return "'" + cutString(this.zaiko_hachu_saki,ZAIKO_HACHU_SAKI_MAX_LENGTH) + "'";
	}
	public String getZaikoHachuSakiHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zaiko_hachu_saki,ZAIKO_HACHU_SAKI_MAX_LENGTH));
	}

	// zaiko_center_cdに対するセッターとゲッターの集合
	public boolean setZaikoCenterCd(String zaiko_center_cd)
	{
		this.zaiko_center_cd = zaiko_center_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("zaiko_center_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getZaikoCenterCd()
	{
		return cutString(this.zaiko_center_cd,ZAIKO_CENTER_CD_MAX_LENGTH);
	}
	public String getZaikoCenterCdString()
	{
		return "'" + cutString(this.zaiko_center_cd,ZAIKO_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getZaikoCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zaiko_center_cd,ZAIKO_CENTER_CD_MAX_LENGTH));
	}

	// min_zaikosu_qtに対するセッターとゲッターの集合
	public boolean setMinZaikosuQt(String min_zaikosu_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("min_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.min_zaikosu_qt = Long.parseLong(min_zaikosu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMinZaikosuQt(long min_zaikosu_qt)
	{
		this.min_zaikosu_qt = min_zaikosu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("min_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getMinZaikosuQt()
	{
		return this.min_zaikosu_qt;
	}
	public String getMinZaikosuQtString()
	{
		return Long.toString(this.min_zaikosu_qt);
	}

	// max_zaikosu_qtに対するセッターとゲッターの集合
	public boolean setMaxZaikosuQt(String max_zaikosu_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("max_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.max_zaikosu_qt = Long.parseLong(max_zaikosu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMaxZaikosuQt(long max_zaikosu_qt)
	{
		this.max_zaikosu_qt = max_zaikosu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("max_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getMaxZaikosuQt()
	{
		return this.max_zaikosu_qt;
	}
	public String getMaxZaikosuQtString()
	{
		return Long.toString(this.max_zaikosu_qt);
	}

	// center_hachutani_kbに対するセッターとゲッターの集合
	public boolean setCenterHachutaniKb(String center_hachutani_kb)
	{
		this.center_hachutani_kb = center_hachutani_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_hachutani_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCenterHachutaniKb()
	{
		return cutString(this.center_hachutani_kb,CENTER_HACHUTANI_KB_MAX_LENGTH);
	}
	public String getCenterHachutaniKbString()
	{
		return "'" + cutString(this.center_hachutani_kb,CENTER_HACHUTANI_KB_MAX_LENGTH) + "'";
	}
	public String getCenterHachutaniKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_hachutani_kb,CENTER_HACHUTANI_KB_MAX_LENGTH));
	}

	// center_hachutani_qtに対するセッターとゲッターの集合
	public boolean setCenterHachutaniQt(String center_hachutani_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_hachutani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.center_hachutani_qt = Double.parseDouble(center_hachutani_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setCenterHachutaniQt(long center_hachutani_qt)
	{
		this.center_hachutani_qt = center_hachutani_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_hachutani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getCenterHachutaniQt()
	{
		return this.center_hachutani_qt;
	}
	public String getCenterHachutaniQtString()
	{
		return Double.toString(this.center_hachutani_qt);
	}

	// center_irisu_qtに対するセッターとゲッターの集合
	public boolean setCenterIrisuQt(String center_irisu_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_irisu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.center_irisu_qt = Double.parseDouble(center_irisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setCenterIrisuQt(long center_irisu_qt)
	{
		this.center_irisu_qt = center_irisu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_irisu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getCenterIrisuQt()
	{
		return this.center_irisu_qt;
	}
	public String getCenterIrisuQtString()
	{
		return Double.toString(this.center_irisu_qt);
	}

	// center_weight_qtに対するセッターとゲッターの集合
	public boolean setCenterWeightQt(String center_weight_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_weight_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.center_weight_qt = Long.parseLong(center_weight_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setCenterWeightQt(long center_weight_qt)
	{
		this.center_weight_qt = center_weight_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_weight_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getCenterWeightQt()
	{
		return this.center_weight_qt;
	}
	public String getCenterWeightQtString()
	{
		return Long.toString(this.center_weight_qt);
	}

	// tana_no_nbに対するセッターとゲッターの集合
	public boolean setTanaNoNb(String tana_no_nb)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tana_no_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
//			↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
			this.st_tana_no_nb = tana_no_nb;
//			↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
			this.tana_no_nb = Long.parseLong(tana_no_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTanaNoNb(long tana_no_nb)
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
		this.st_tana_no_nb = Long.toString(tana_no_nb);
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
		this.tana_no_nb = tana_no_nb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tana_no_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
//	↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//	public long getTanaNoNb()
//	{
//		return this.tana_no_nb;
//	}
	public String getTanaNoNb()
	{
		return this.st_tana_no_nb;
	}
//	↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	public String getTanaNoNbString()
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//		return Long.toString(this.tana_no_nb);
		return this.st_tana_no_nb;
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	}

	// dan_no_nbに対するセッターとゲッターの集合
	public boolean setDanNoNb(String dan_no_nb)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("dan_no_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
//			↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
			this.st_dan_no_nb = dan_no_nb;
//			↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
			this.dan_no_nb = Long.parseLong(dan_no_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setDanNoNb(long dan_no_nb)
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
		this.st_dan_no_nb = Long.toString(dan_no_nb);
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
		this.dan_no_nb = dan_no_nb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("dan_no_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
//	↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//	public long getDanNoNb()
//	{
//		return this.dan_no_nb;
//	}
	public String getDanNoNb()
	{
		return this.st_dan_no_nb;
	}
//	↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	public String getDanNoNbString()
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//		return Long.toString(this.dan_no_nb);
		return this.st_dan_no_nb;
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	}

	// keiyaku_su_qtに対するセッターとゲッターの集合
	public boolean setKeiyakuSuQt(String keiyaku_su_qt)
	{
		gamenFlg.put("keiyaku_su_qt","1");
		try
		{
			this.keiyaku_su_qt = Long.parseLong(keiyaku_su_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setKeiyakuSuQt(long keiyaku_su_qt)
	{
		this.keiyaku_su_qt = keiyaku_su_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("keiyaku_su_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getKeiyakuSuQt()
	{
		return this.keiyaku_su_qt;
	}
	public String getKeiyakuSuQtString()
	{
		return Long.toString(this.keiyaku_su_qt);
	}

	// keiyaku_pattern_kbに対するセッターとゲッターの集合
	public boolean setKeiyakuPatternKb(String keiyaku_pattern_kb)
	{
		this.keiyaku_pattern_kb = keiyaku_pattern_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("keiyaku_pattern_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getKeiyakuPatternKb()
	{
		return cutString(this.keiyaku_pattern_kb,KEIYAKU_PATTERN_KB_MAX_LENGTH);
	}
	public String getKeiyakuPatternKbString()
	{
		return "'" + cutString(this.keiyaku_pattern_kb,KEIYAKU_PATTERN_KB_MAX_LENGTH) + "'";
	}
	public String getKeiyakuPatternKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiyaku_pattern_kb,KEIYAKU_PATTERN_KB_MAX_LENGTH));
	}

	// keiyaku_st_dtに対するセッターとゲッターの集合
	public boolean setKeiyakuStDt(String keiyaku_st_dt)
	{
		this.keiyaku_st_dt = keiyaku_st_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("keiyaku_st_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getKeiyakuStDt()
	{
		return cutString(this.keiyaku_st_dt,KEIYAKU_ST_DT_MAX_LENGTH);
	}
	public String getKeiyakuStDtString()
	{
		return "'" + cutString(this.keiyaku_st_dt,KEIYAKU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getKeiyakuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiyaku_st_dt,KEIYAKU_ST_DT_MAX_LENGTH));
	}

	// keiyaku_ed_dtに対するセッターとゲッターの集合
	public boolean setKeiyakuEdDt(String keiyaku_ed_dt)
	{
		this.keiyaku_ed_dt = keiyaku_ed_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("keiyaku_ed_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getKeiyakuEdDt()
	{
		return cutString(this.keiyaku_ed_dt,KEIYAKU_ED_DT_MAX_LENGTH);
	}
	public String getKeiyakuEdDtString()
	{
		return "'" + cutString(this.keiyaku_ed_dt,KEIYAKU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getKeiyakuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiyaku_ed_dt,KEIYAKU_ED_DT_MAX_LENGTH));
	}

	// kijun_zaikosu_qtに対するセッターとゲッターの集合
	public boolean setKijunZaikosuQt(String kijun_zaikosu_qt)
	{
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("kijun_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		try
		{
			this.kijun_zaikosu_qt = Long.parseLong(kijun_zaikosu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setKijunZaikosuQt(long kijun_zaikosu_qt)
	{
		this.kijun_zaikosu_qt = kijun_zaikosu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("kijun_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public long getKijunZaikosuQt()
	{
		return this.kijun_zaikosu_qt;
	}
	public String getKijunZaikosuQtString()
	{
		return Long.toString(this.kijun_zaikosu_qt);
	}

	// d_zokusei_1_naに対するセッターとゲッターの集合
	public boolean setDZokusei1Na(String d_zokusei_1_na)
	{
		this.d_zokusei_1_na = d_zokusei_1_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_1_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei1Na()
	{
		return cutString(this.d_zokusei_1_na,D_ZOKUSEI_1_NA_MAX_LENGTH);
	}
	public String getDZokusei1NaString()
	{
		return "'" + cutString(this.d_zokusei_1_na,D_ZOKUSEI_1_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_1_na,D_ZOKUSEI_1_NA_MAX_LENGTH));
	}

	// s_zokusei_1_naに対するセッターとゲッターの集合
	public boolean setSZokusei1Na(String s_zokusei_1_na)
	{
		this.s_zokusei_1_na = s_zokusei_1_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_1_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei1Na()
	{
		return cutString(this.s_zokusei_1_na,S_ZOKUSEI_1_NA_MAX_LENGTH);
	}
	public String getSZokusei1NaString()
	{
		return "'" + cutString(this.s_zokusei_1_na,S_ZOKUSEI_1_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_1_na,S_ZOKUSEI_1_NA_MAX_LENGTH));
	}

	// d_zokusei_2_naに対するセッターとゲッターの集合
	public boolean setDZokusei2Na(String d_zokusei_2_na)
	{
		this.d_zokusei_2_na = d_zokusei_2_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_2_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei2Na()
	{
		return cutString(this.d_zokusei_2_na,D_ZOKUSEI_2_NA_MAX_LENGTH);
	}
	public String getDZokusei2NaString()
	{
		return "'" + cutString(this.d_zokusei_2_na,D_ZOKUSEI_2_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_2_na,D_ZOKUSEI_2_NA_MAX_LENGTH));
	}

	// s_zokusei_2_naに対するセッターとゲッターの集合
	public boolean setSZokusei2Na(String s_zokusei_2_na)
	{
		this.s_zokusei_2_na = s_zokusei_2_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_2_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei2Na()
	{
		return cutString(this.s_zokusei_2_na,S_ZOKUSEI_2_NA_MAX_LENGTH);
	}
	public String getSZokusei2NaString()
	{
		return "'" + cutString(this.s_zokusei_2_na,S_ZOKUSEI_2_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_2_na,S_ZOKUSEI_2_NA_MAX_LENGTH));
	}

	// d_zokusei_3_naに対するセッターとゲッターの集合
	public boolean setDZokusei3Na(String d_zokusei_3_na)
	{
		this.d_zokusei_3_na = d_zokusei_3_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_3_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei3Na()
	{
		return cutString(this.d_zokusei_3_na,D_ZOKUSEI_3_NA_MAX_LENGTH);
	}
	public String getDZokusei3NaString()
	{
		return "'" + cutString(this.d_zokusei_3_na,D_ZOKUSEI_3_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei3NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_3_na,D_ZOKUSEI_3_NA_MAX_LENGTH));
	}

	// s_zokusei_3_naに対するセッターとゲッターの集合
	public boolean setSZokusei3Na(String s_zokusei_3_na)
	{
		this.s_zokusei_3_na = s_zokusei_3_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_3_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei3Na()
	{
		return cutString(this.s_zokusei_3_na,S_ZOKUSEI_3_NA_MAX_LENGTH);
	}
	public String getSZokusei3NaString()
	{
		return "'" + cutString(this.s_zokusei_3_na,S_ZOKUSEI_3_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei3NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_3_na,S_ZOKUSEI_3_NA_MAX_LENGTH));
	}

	// d_zokusei_4_naに対するセッターとゲッターの集合
	public boolean setDZokusei4Na(String d_zokusei_4_na)
	{
		this.d_zokusei_4_na = d_zokusei_4_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_4_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei4Na()
	{
		return cutString(this.d_zokusei_4_na,D_ZOKUSEI_4_NA_MAX_LENGTH);
	}
	public String getDZokusei4NaString()
	{
		return "'" + cutString(this.d_zokusei_4_na,D_ZOKUSEI_4_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei4NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_4_na,D_ZOKUSEI_4_NA_MAX_LENGTH));
	}

	// s_zokusei_4_naに対するセッターとゲッターの集合
	public boolean setSZokusei4Na(String s_zokusei_4_na)
	{
		this.s_zokusei_4_na = s_zokusei_4_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_4_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei4Na()
	{
		return cutString(this.s_zokusei_4_na,S_ZOKUSEI_4_NA_MAX_LENGTH);
	}
	public String getSZokusei4NaString()
	{
		return "'" + cutString(this.s_zokusei_4_na,S_ZOKUSEI_4_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei4NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_4_na,S_ZOKUSEI_4_NA_MAX_LENGTH));
	}

	// d_zokusei_5_naに対するセッターとゲッターの集合
	public boolean setDZokusei5Na(String d_zokusei_5_na)
	{
		this.d_zokusei_5_na = d_zokusei_5_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_5_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei5Na()
	{
		return cutString(this.d_zokusei_5_na,D_ZOKUSEI_5_NA_MAX_LENGTH);
	}
	public String getDZokusei5NaString()
	{
		return "'" + cutString(this.d_zokusei_5_na,D_ZOKUSEI_5_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei5NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_5_na,D_ZOKUSEI_5_NA_MAX_LENGTH));
	}

	// s_zokusei_5_naに対するセッターとゲッターの集合
	public boolean setSZokusei5Na(String s_zokusei_5_na)
	{
		this.s_zokusei_5_na = s_zokusei_5_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_5_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei5Na()
	{
		return cutString(this.s_zokusei_5_na,S_ZOKUSEI_5_NA_MAX_LENGTH);
	}
	public String getSZokusei5NaString()
	{
		return "'" + cutString(this.s_zokusei_5_na,S_ZOKUSEI_5_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei5NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_5_na,S_ZOKUSEI_5_NA_MAX_LENGTH));
	}

	// d_zokusei_6_naに対するセッターとゲッターの集合
	public boolean setDZokusei6Na(String d_zokusei_6_na)
	{
		this.d_zokusei_6_na = d_zokusei_6_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_6_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei6Na()
	{
		return cutString(this.d_zokusei_6_na,D_ZOKUSEI_6_NA_MAX_LENGTH);
	}
	public String getDZokusei6NaString()
	{
		return "'" + cutString(this.d_zokusei_6_na,D_ZOKUSEI_6_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei6NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_6_na,D_ZOKUSEI_6_NA_MAX_LENGTH));
	}

	// s_zokusei_6_naに対するセッターとゲッターの集合
	public boolean setSZokusei6Na(String s_zokusei_6_na)
	{
		this.s_zokusei_6_na = s_zokusei_6_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_6_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
//	 yoridori_vlに対するセッターとゲッターの集合
	public boolean setYoridoriVl(String yoridori_vl)
	{
		this.yoridori_vl = yoridori_vl;
		this.gamenFlg.put("yoridori_vl", "1");
		return true;
	}
	public String getYoridoriVl()
	{
		if(this.yoridori_vl == null){
			return "";
		} else {
			return this.yoridori_vl;
		}
	}
	public String getYoridoriVlString()
	{
		return this.yoridori_vl;
	}

	public String getSZokusei6Na()
	{
		return cutString(this.s_zokusei_6_na,S_ZOKUSEI_6_NA_MAX_LENGTH);
	}
	public String getSZokusei6NaString()
	{
		return "'" + cutString(this.s_zokusei_6_na,S_ZOKUSEI_6_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei6NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_6_na,S_ZOKUSEI_6_NA_MAX_LENGTH));
	}
//	 nefuda_ukewatasi_dtに対するセッターとゲッターの集合
	public boolean setNefudaUkewatasiDt(String nefuda_ukewatasi_dt)
	{
		this.nefuda_ukewatasi_dt = nefuda_ukewatasi_dt;
		this.gamenFlg.put("nefuda_ukewatasi_dt", "1");
		return true;
	}
	public String getNefudaUkewatasiDt()
	{
		return cutString(this.nefuda_ukewatasi_dt,NEFUDA_UKEWATASI_DT_MAX_LENGTH);
	}
	public String getNefudaUkewatasiDtString()
	{
		return "'" + cutString(this.nefuda_ukewatasi_dt,NEFUDA_UKEWATASI_DT_MAX_LENGTH) + "'";
	}
	public String getNefudaUkewatasiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefuda_ukewatasi_dt,NEFUDA_UKEWATASI_DT_MAX_LENGTH));
	}


	// nefuda_ukewatasi_kbに対するセッターとゲッターの集合
	public boolean setNefudaUkewatasiKb(String nefuda_ukewatasi_kb)
	{
		this.nefuda_ukewatasi_kb = nefuda_ukewatasi_kb;
		this.gamenFlg.put("nefuda_ukewatasi_kb", "1");
		return true;
	}
	public String getNefudaUkewatasiKb()
	{
		return cutString(this.nefuda_ukewatasi_kb,NEFUDA_UKEWATASI_KB_MAX_LENGTH);
	}
	public String getNefudaUkewatasiKbString()
	{
		return "'" + cutString(this.nefuda_ukewatasi_kb,NEFUDA_UKEWATASI_KB_MAX_LENGTH) + "'";
	}
	public String getNefudaUkewatasiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefuda_ukewatasi_kb,NEFUDA_UKEWATASI_KB_MAX_LENGTH));
	}

	// d_zokusei_7_naに対するセッターとゲッターの集合
	public boolean setDZokusei7Na(String d_zokusei_7_na)
	{
		this.d_zokusei_7_na = d_zokusei_7_na;
//BUGNO-S075 2005.07.20 Sirius START
		 gamenFlg.put("d_zokusei_7_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei7Na()
	{
		return cutString(this.d_zokusei_7_na,D_ZOKUSEI_7_NA_MAX_LENGTH);
	}
	public String getDZokusei7NaString()
	{
		return "'" + cutString(this.d_zokusei_7_na,D_ZOKUSEI_7_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei7NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_7_na,D_ZOKUSEI_7_NA_MAX_LENGTH));
	}

	// s_zokusei_7_naに対するセッターとゲッターの集合
	public boolean setSZokusei7Na(String s_zokusei_7_na)
	{
		this.s_zokusei_7_na = s_zokusei_7_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_7_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei7Na()
	{
		return cutString(this.s_zokusei_7_na,S_ZOKUSEI_7_NA_MAX_LENGTH);
	}
	public String getSZokusei7NaString()
	{
		return "'" + cutString(this.s_zokusei_7_na,S_ZOKUSEI_7_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei7NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_7_na,S_ZOKUSEI_7_NA_MAX_LENGTH));
	}

	// d_zokusei_8_naに対するセッターとゲッターの集合
	public boolean setDZokusei8Na(String d_zokusei_8_na)
	{
		this.d_zokusei_8_na = d_zokusei_8_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_8_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei8Na()
	{
		return cutString(this.d_zokusei_8_na,D_ZOKUSEI_8_NA_MAX_LENGTH);
	}
	public String getDZokusei8NaString()
	{
		return "'" + cutString(this.d_zokusei_8_na,D_ZOKUSEI_8_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei8NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_8_na,D_ZOKUSEI_8_NA_MAX_LENGTH));
	}

	// s_zokusei_8_naに対するセッターとゲッターの集合
	public boolean setSZokusei8Na(String s_zokusei_8_na)
	{
		this.s_zokusei_8_na = s_zokusei_8_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_8_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei8Na()
	{
		return cutString(this.s_zokusei_8_na,S_ZOKUSEI_8_NA_MAX_LENGTH);
	}
	public String getSZokusei8NaString()
	{
		return "'" + cutString(this.s_zokusei_8_na,S_ZOKUSEI_8_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei8NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_8_na,S_ZOKUSEI_8_NA_MAX_LENGTH));
	}

	// d_zokusei_9_naに対するセッターとゲッターの集合
	public boolean setDZokusei9Na(String d_zokusei_9_na)
	{
		this.d_zokusei_9_na = d_zokusei_9_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_9_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei9Na()
	{
		return cutString(this.d_zokusei_9_na,D_ZOKUSEI_9_NA_MAX_LENGTH);
	}
	public String getDZokusei9NaString()
	{
		return "'" + cutString(this.d_zokusei_9_na,D_ZOKUSEI_9_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei9NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_9_na,D_ZOKUSEI_9_NA_MAX_LENGTH));
	}

	// s_zokusei_9_naに対するセッターとゲッターの集合
	public boolean setSZokusei9Na(String s_zokusei_9_na)
	{
		this.s_zokusei_9_na = s_zokusei_9_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_9_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei9Na()
	{
		return cutString(this.s_zokusei_9_na,S_ZOKUSEI_9_NA_MAX_LENGTH);
	}
	public String getSZokusei9NaString()
	{
		return "'" + cutString(this.s_zokusei_9_na,S_ZOKUSEI_9_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei9NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_9_na,S_ZOKUSEI_9_NA_MAX_LENGTH));
	}

	// d_zokusei_10_naに対するセッターとゲッターの集合
	public boolean setDZokusei10Na(String d_zokusei_10_na)
	{
		this.d_zokusei_10_na = d_zokusei_10_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("d_zokusei_10_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDZokusei10Na()
	{
		return cutString(this.d_zokusei_10_na,D_ZOKUSEI_10_NA_MAX_LENGTH);
	}
	public String getDZokusei10NaString()
	{
		return "'" + cutString(this.d_zokusei_10_na,D_ZOKUSEI_10_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei10NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_10_na,D_ZOKUSEI_10_NA_MAX_LENGTH));
	}

	// s_zokusei_10_naに対するセッターとゲッターの集合
	public boolean setSZokusei10Na(String s_zokusei_10_na)
	{
		this.s_zokusei_10_na = s_zokusei_10_na;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("s_zokusei_10_na","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSZokusei10Na()
	{
		return cutString(this.s_zokusei_10_na,S_ZOKUSEI_10_NA_MAX_LENGTH);
	}
	public String getSZokusei10NaString()
	{
		return "'" + cutString(this.s_zokusei_10_na,S_ZOKUSEI_10_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei10NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_10_na,S_ZOKUSEI_10_NA_MAX_LENGTH));
	}

	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("insert_ts","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getInsertTs()
	{
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsString()
	{
		return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_ts,INSERT_TS_MAX_LENGTH));
	}

	// insert_user_idに対するセッターとゲッターの集合
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("insert_user_id","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getInsertUserId()
	{
		return cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString()
	{
		return "'" + cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH));
	}

	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("update_ts","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getUpdateTs()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString()
	{
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}

	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("update_user_id","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getUpdateUserId()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserIdString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}

	// delete_fgに対するセッターとゲッターの集合
	public boolean setDeleteFg(String delete_fg)
	{
		this.delete_fg = delete_fg;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("delete_fg","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getDeleteFg()
	{
		return cutString(this.delete_fg,DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString()
	{
		return "'" + cutString(this.delete_fg,DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_fg,DELETE_FG_MAX_LENGTH));
	}

//	↓↓仕様追加による変更（2005.06.29）↓↓
	// sinki_toroku_dtに対するセッターとゲッターの集合
	public boolean setSinkiTorokuDt(String sinki_toroku_dt)
	{
		this.sinki_toroku_dt = sinki_toroku_dt;
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sinki_toroku_dt","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSinkiTorokuDt()
	{
		return cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH);
	}
	public String getSinkiTorokuDtString()
	{
		return "'" + cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH) + "'";
	}
	public String getSinkiTorokuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH));
	}

	// henko_dtに対するセッターとゲッターの集合
	public boolean setHenkoDt(String henko_dt)
	{
		this.henko_dt = henko_dt;
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("henko_dt","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHenkoDt()
	{
		return cutString(this.henko_dt,HENKO_DT_MAX_LENGTH);
	}
	public String getHenkoDtString()
	{
		return "'" + cutString(this.henko_dt,HENKO_DT_MAX_LENGTH) + "'";
	}
	public String getHenkoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.henko_dt,HENKO_DT_MAX_LENGTH));
	}
//	↑↑仕様追加による変更（2005.06.29）↑↑
//	↓↓仕様変更（2005.07.28）↓↓
	// syokai_toroku_tsに対するセッターとゲッターの集合
	public boolean setSyokaiTorokuTs(String syokai_toroku_ts)
	{
		this.syokai_toroku_ts = syokai_toroku_ts;
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syokai_toroku_ts","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSyokaiTorokuTs()
	{
		return cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH);
	}
	public String getSyokaiTorokuTsString()
	{
		return "'" + cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH) + "'";
	}
	public String getSyokaiTorokuTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH));
	}


	// syokai_user_idに対するセッターとゲッターの集合
	public boolean setSyokaiUserId(String syokai_user_id)
	{
		this.syokai_user_id = syokai_user_id;
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syokai_user_id","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSyokaiUserId()
	{
		return cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH);
	}
	public String getSyokaiUserIdString()
	{
		return "'" + cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyokaiUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH));
	}
//	↑↑仕様変更（2005.07.28）↑↑

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//st_syohin_width_qtに対するセッターとゲッターの集合
	public boolean setStSyohinWidthQt(String st_syohin_width_qt)
	{
		this.st_syohin_width_qt = st_syohin_width_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_width_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStSyohinWidthQt()
	{
		if(this.st_syohin_width_qt == null){
			return "";
		} else {
			return this.getSyohinWidthQtString();
		}
	}

	//st_syohin_height_qtに対するセッターとゲッターの集合
	public boolean setStSyohinHeightQt(String st_syohin_height_qt)
	{
		this.st_syohin_height_qt = st_syohin_height_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_height_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStSyohinHeightQt()
	{
		if(this.st_syohin_height_qt == null){
			return "";
		} else {
			return this.getSyohinHeightQtString();
		}
	}

	//st_syohin_height_qtに対するセッターとゲッターの集合
	public boolean setStSyohinDepthQt(String st_syohin_depth_qt)
	{
		this.st_syohin_depth_qt = st_syohin_depth_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_depth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStSyohinDepthQt()
	{
		if(this.st_syohin_depth_qt == null){
			return "";
		} else {
			return this.getSyohinDepthQtString();
		}
	}

	//st_maker_kibo_kakaku_vlに対するセッターとゲッターの集合
	public boolean setStMakerKiboKakakuVl(String st_maker_kibo_kakaku_vl)
	{
		this.st_maker_kibo_kakaku_vl = st_maker_kibo_kakaku_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("maker_kibo_kakaku_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStMakerKiboKakakuVl()
	{
		if(this.st_maker_kibo_kakaku_vl == null){
			return "";
		} else {
			return this.getMakerKiboKakakuVlString();
		}
	}

	//st_gentanka_vlに対するセッターとゲッターの集合
	public boolean setStGentankaVl(String st_gentanka_vl)
	{
		this.st_gentanka_vl = st_gentanka_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("gentanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStGentankaVl()
	{
		if(this.st_gentanka_vl == null){
			return "";
		} else {
			return this.getGentankaVlString();
		}

	}

	//st_baitanka_vlに対するセッターとゲッターの集合
	public boolean setStBaitankaVl(String st_baitanka_vl)
	{
		this.st_baitanka_vl = st_baitanka_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("baitanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStBaitankaVl()
	{
		if(this.st_baitanka_vl == null){
			return "";
		} else {
			return this.getBaitankaVlString();
		}
	}

	//st_hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setStHachutaniIrisuQt(String st_hachutani_irisu_qt)
	{
		this.st_hachutani_irisu_qt = st_hachutani_irisu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachutani_irisu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStHachutaniIrisuQt()
	{
		if(this.st_hachutani_irisu_qt == null){
			return "";
		} else {
			return this.getHachutaniIrisuQtString();
		}
	}
//	↓↓仕様変更（2005.08.24）↓↓
	public String getStringHachutaniIrisuQt()
	{
		if (this.st_hachutani_irisu_qt == null) {
			return "";
		} else {
			return this.st_hachutani_irisu_qt;
		}
	}
//	↑↑仕様変更（2005.08.24）↑↑

	//st_max_hachutani_qtに対するセッターとゲッターの集合
	public boolean setStMaxHachutaniQt(String st_max_hachutani_qt)
	{
		this.st_max_hachutani_qt = st_max_hachutani_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("max_hachutani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStMaxHachutaniQt()
	{
		if(this.st_max_hachutani_qt == null){
			return "";
		} else {
			return this.getMaxHachutaniQtString();
		}
	}
//	↓↓仕様変更（2005.08.24）↓↓
	public String getStringMaxHachutaniQt()
	{
		if (this.st_max_hachutani_qt == null) {
			return "";
		} else {
			return this.st_max_hachutani_qt;
		}
	}
//	↑↑仕様変更（2005.08.24）↑↑

	//st_nohin_kigen_qtに対するセッターとゲッターの集合
	public boolean setStNohinKigenQt(String st_nohin_kigen_qt)
	{
		this.st_nohin_kigen_qt = st_nohin_kigen_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("nohin_kigen_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStNohinKigenQt()
	{
		if(this.st_nohin_kigen_qt == null){
			return "";
		} else {
			return this.getNohinKigenQtString();
		}
	}

	//st_sime_time_1_qtに対するセッターとゲッターの集合
	public boolean setStSimeTime1Qt(String st_sime_time_1_qt)
	{
		this.st_sime_time_1_qt = st_sime_time_1_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_1_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStSimeTime1Qt()
	{
		if(this.st_sime_time_1_qt == null){
			return "";
		} else {
			return this.getSimeTime1QtString();
		}
	}

	//st_sime_time_2_qtに対するセッターとゲッターの集合
	public boolean setStSimeTime2Qt(String st_sime_time_2_qt)
	{
		this.st_sime_time_2_qt = st_sime_time_2_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_2_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStSimeTime2Qt()
	{
		if(this.st_sime_time_2_qt == null){
			return "";
		} else {
			return this.getSimeTime2QtString();
		}
	}
//	 hacyu_syohin_cdに対するセッターとゲッターの集合
	public boolean setHacyuSyohinCd(String hacyu_syohin_cd)
	{
		this.hacyu_syohin_cd = hacyu_syohin_cd;
		return true;
	}
	public String getHacyuSyohinCd()
	{
		return cutString(this.hacyu_syohin_cd,HACYU_SYOHIN_CD_MAX_LENGTH);
	}
	public String getHacyuSyohinCdString()
	{
		return "'" + cutString(this.hacyu_syohin_cd,HACYU_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getHacyuSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hacyu_syohin_cd,HACYU_SYOHIN_CD_MAX_LENGTH));
	}

	//st_sime_time_3_qtに対するセッターとゲッターの集合
	public boolean setStSimeTime3Qt(String st_sime_time_3_qt)
	{
		this.st_sime_time_3_qt = st_sime_time_3_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_3_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStSimeTime3Qt()
	{
		if(this.st_sime_time_3_qt == null){
			return "";
		} else {
			return this.getSimeTime3QtString();
		}
	}

	//st_henpin_nbに対するセッターとゲッターの集合
	public boolean setStHenpinNb(String st_henpin_nb)
	{
		this.st_henpin_nb = st_henpin_nb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("henpin_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStHenpinNb()
	{
		if(this.st_henpin_nb == null){
			return "";
		} else {
			return this.getHenpinNbString();
		}
	}

	//st_henpin_genka_vlに対するセッターとゲッターの集合
	public boolean setStHenpinGenkaVl(String st_henpin_genka_vl)
	{
		this.st_henpin_genka_vl = st_henpin_genka_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("henpin_genka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStHenpinGenkavl()
	{
		if(this.st_henpin_genka_vl == null){
			return "";
		} else {
			return this.getHenpinGenkaVlString();
		}
	}

	//st_hanbai_limit_qtに対するセッターとゲッターの集合
	public boolean setStHanbaiLimitQt(String st_hanbai_limit_qt)
	{
		this.st_hanbai_limit_qt = st_hanbai_limit_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hanbai_limit_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStHanbaiLimitQt()
	{
		if(this.st_hanbai_limit_qt == null){
			return "";
		} else {
			return this.getHanbaiLimitQtString();
		}
	}

	//st_daisi_no_nbに対するセッターとゲッターの集合
	public boolean setStDaisiNoNb(String st_daisi_no_nb)
	{
		this.st_daisi_no_nb = st_daisi_no_nb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("daisi_no_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
//	public String getStDaisiNoNb()
//	{
//		if(this.st_daisi_no_nb == null){
//			return "";
//		} else {
//			return this.getDaisiNoNbString();
//		}
//	}

	//st_unit_price_naiyoryo_qtに対するセッターとゲッターの集合
	public boolean setStUnitPriceNaiyoryoQt(String st_unit_price_naiyoryo_qt)
	{
		this.st_unit_price_naiyoryo_qt = st_unit_price_naiyoryo_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("unit_price_naiyoryo_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStUnitPriceNaiyoryoQt()
	{
		if(this.st_unit_price_naiyoryo_qt == null ){
			return "";
		} else {
			return this.st_unit_price_naiyoryo_qt;
		}
	}

	//st_unit_price_kijun_tani_qtに対するセッターとゲッターの集合
	public boolean setStUnitPriceKijunTaniQt(String st_unit_price_kijun_tani_qt)
	{
		this.st_unit_price_kijun_tani_qt = st_unit_price_kijun_tani_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("unit_price_kijun_tani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStUnitPriceKijunTaniQt()
	{
		if(this.st_unit_price_kijun_tani_qt == null ){
			return "";
		} else {
			return this.st_unit_price_kijun_tani_qt;
		}
	}
	//st_yoridori_qtに対するセッターとゲッターの集合
	public boolean setStYoridoriQt(String st_yoridori_qt)
	{
		this.st_yoridori_qt = st_yoridori_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("yoridori_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStYoridoriQt()
	{
		if(this.st_yoridori_qt == null){
			return "";
		} else {
			return this.getYoridoriQtString();
		}
	}

	//st_tag_hyoji_baika_vlに対するセッターとゲッターの集合
	public boolean setStTagHyojiBaikaVl(String st_tag_hyoji_baika_vl)
	{
		this.st_tag_hyoji_baika_vl = st_tag_hyoji_baika_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tag_hyoji_baika_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStTagHyojiBaikaVl()
	{
		if(this.st_tag_hyoji_baika_vl == null){
			return "";
		} else {
			return this.getTagHyojiBaikaVlString();
		}
	}

	//st_case_irisu_qtに対するセッターとゲッターの集合
	public boolean setStCaseIrisuQt(String st_case_irisu_qt)
	{
		this.st_case_irisu_qt = st_case_irisu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("case_irisu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStCaseIrisuQt()
	{
		if(this.st_case_irisu_qt == null){
			return "";
		} else {
			return this.getCaseIrisuQtString();
		}
	}

	//st_pack_width_qtに対するセッターとゲッターの集合
	public boolean setStPackWidthQt(String st_pack_width_qt)
	{
		this.st_pack_width_qt = st_pack_width_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_width_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStPackWidthQt()
	{
		if(this.st_pack_width_qt == null){
			return "";
		} else {
			return this.getPackWidthQtString();
		}
	}

	//st_pack_heigth_qtに対するセッターとゲッターの集合
	public boolean setStPackHeigthQt(String st_pack_heigth_qt)
	{
		this.st_pack_heigth_qt = st_pack_heigth_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_heigth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStPackHeigthQt()
	{
		if(this.st_pack_heigth_qt == null){
			return "";
		} else {
			return this.getPackHeigthQtString();
		}
	}
//	 sode_cdに対するセッターとゲッターの集合
	public boolean setSodeCd(String sode_cd)
	{
		this.sode_cd = sode_cd;
		gamenFlg.put("sode_cd","1");
		return true;
	}
	public String getSodeCd()
	{
		return cutString(this.sode_cd,SODE_CD_MAX_LENGTH);
	}
	public String getSodeCdString()
	{
		return "'" + cutString(this.sode_cd,SODE_CD_MAX_LENGTH) + "'";
	}
	public String getSodeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sode_cd,SODE_CD_MAX_LENGTH));
	}


	//st_pack_depth_qtに対するセッターとゲッターの集合
	public boolean setStPackDepthQt(String st_pack_depth_qt)
	{
		this.st_pack_depth_qt = st_pack_depth_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_depth_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStPackDepthQt()
	{
		if(this.st_pack_depth_qt == null){
			return "";
		} else {
			return this.getPackDepthQtString();
		}
	}

	//st_pack_weight_qtに対するセッターとゲッターの集合
	public boolean setStPackWeightQt(String st_pack_weight_qt)
	{
		this.st_pack_weight_qt = st_pack_weight_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("pack_weight_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStPackWeightQt()
	{
		if(this.st_pack_weight_qt == null){
			return "";
		} else {
			return this.getPackWeightQtString();
		}
	}

	//st_min_zaikosu_qtに対するセッターとゲッターの集合
	public boolean setStMinZaikosuQt(String st_min_zaikosu_qt)
	{
		this.st_min_zaikosu_qt = st_min_zaikosu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("min_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStMinZaikosuQt()
	{
		if(this.st_min_zaikosu_qt == null){
			return "";
		} else {
			return this.getMinZaikosuQtString();
		}
	}

	//st_max_zaikosu_qtに対するセッターとゲッターの集合
	public boolean setStMaxZaikosuQt(String st_max_zaikosu_qt)
	{
		this.st_max_zaikosu_qt = st_max_zaikosu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("max_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStMaxZaikosuQt()
	{
		if(this.st_max_zaikosu_qt == null){
			return "";
		} else {
			return this.getMaxZaikosuQtString();
		}
	}

	//st_center_hachutani_qtに対するセッターとゲッターの集合
	public boolean setStCenterHachutaniQt(String st_center_hachutani_qt)
	{
		this.st_center_hachutani_qt = st_center_hachutani_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_hachutani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStCenterHachutaniQt()
	{
		if(this.st_center_hachutani_qt == null){
			return "";
		} else {
			return this.getCenterHachutaniQtString();
		}
	}

	//st_center_irisu_qtに対するセッターとゲッターの集合
	public boolean setStCenterIrisuQt(String st_center_irisu_qt)
	{
		this.st_center_irisu_qt = st_center_irisu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		 gamenFlg.put("center_irisu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStCenterIrisuQt()
	{
		if(this.st_center_irisu_qt == null){
			return "";
		} else {
			return this.getCenterIrisuQtString();
		}
	}

	//st_center_weight_qtに対するセッターとゲッターの集合
	public boolean setStCenterWeightQt(String st_center_weight_qt)
	{
		this.st_center_weight_qt = st_center_weight_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("center_weight_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStCenterWeightQt()
	{
		if(this.st_center_weight_qt == null){
			return "";
		} else {
			return this.getCenterWeightQtString();
		}
	}

	//st_tana_no_nbに対するセッターとゲッターの集合
	public boolean setStTanaNoNb(String st_tana_no_nb)
	{
		this.st_tana_no_nb = st_tana_no_nb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tana_no_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStTanaNoNb()
	{
		if(this.st_tana_no_nb == null){
			return "";
		} else {
			return this.getTanaNoNbString();
		}
	}

	//st_dan_no_nbに対するセッターとゲッターの集合
	public boolean setStDanNoNb(String st_dan_no_nb)
	{
		this.st_dan_no_nb = st_dan_no_nb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("dan_no_nb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStDanNoNb()
	{
		if(this.st_dan_no_nb == null){
			return "";
		} else {
			return this.getDanNoNbString();
		}
	}

	//st_keiyaku_su_qtに対するセッターとゲッターの集合
	public boolean setStKeiyakuSuQt(String st_keiyaku_su_qt)
	{
		this.st_keiyaku_su_qt = st_keiyaku_su_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("keiyaku_su_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStKeiyakuSuQt()
	{
		if(this.st_keiyaku_su_qt == null){
			return "";
		} else {
			return this.getKeiyakuSuQtString();
		}
	}

	//st_kijun_zaikosu_qtに対するセッターとゲッターの集合
	public boolean setStKijunZaikosuQt(String st_kijun_zaikosu_qt)
	{
		this.st_kijun_zaikosu_qt = st_kijun_zaikosu_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("kijun_zaikosu_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getStKijunZaikosuQt()
	{
		if(this.st_kijun_zaikosu_qt == null){
			return "";
		} else {
			return this.getKijunZaikosuQtString();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//st_syohin_width_qtに対するセッターとゲッターの集合
	public String getUpSyohinWidthQt()
	{
		if(this.st_syohin_width_qt == null || this.st_syohin_width_qt.equals("")){
			return "";
		} else {
			this.setSyohinWidthQt(st_syohin_width_qt);
			return this.getSyohinWidthQtString();
		}
	}

	//st_syohin_height_qtに対するセッターとゲッターの集合
	public String getUpSyohinHeightQt()
	{
		if(this.st_syohin_height_qt == null || this.st_syohin_height_qt.equals("")){
			return "";
		} else {
			this.setSyohinHeightQt(st_syohin_height_qt);
			return this.getSyohinHeightQtString();
		}
	}

	//st_syohin_height_qtに対するセッターとゲッターの集合
	public String getUpSyohinDepthQt()
	{
		if(this.st_syohin_depth_qt == null || this.st_syohin_depth_qt.equals("")){
			return "";
		} else {
			this.setSyohinDepthQt(st_syohin_depth_qt);
			return this.getSyohinDepthQtString();
		}
	}

	//st_maker_kibo_kakaku_vlに対するセッターとゲッターの集合
	public String getUpMakerKiboKakakuVl()
	{
		if(this.st_maker_kibo_kakaku_vl == null || this.st_maker_kibo_kakaku_vl.equals("")){
			return "";
		} else {
			this.setMakerKiboKakakuVl(st_maker_kibo_kakaku_vl);
			return this.getMakerKiboKakakuVlString();
		}
	}

	//st_gentanka_vlに対するセッターとゲッターの集合
	public String getUpGentankaVl()
	{
		if(this.st_gentanka_vl == null || this.st_gentanka_vl.equals("")){
			return "";
		} else {
			this.setGentankaVl(st_gentanka_vl);
			return this.getGentankaVlString();
		}

	}

	//st_baitanka_vlに対するセッターとゲッターの集合
	public String getUpBaitankaVl()
	{
		if(this.st_baitanka_vl == null || this.st_baitanka_vl.equals("")){
			return "";
		} else {
			this.setBaitankaVl(st_baitanka_vl);
			return this.getBaitankaVlString();
		}
	}

	//st_hachutani_irisu_qtに対するセッターとゲッターの集合
	public String getUpHachutaniIrisuQt()
	{
		if(this.st_hachutani_irisu_qt == null || this.st_hachutani_irisu_qt.equals("")){
			return "";
		} else {
			this.setHachutaniIrisuQt(st_hachutani_irisu_qt);
			return this.getHachutaniIrisuQtString();
		}
	}

	//st_max_hachutani_qtに対するセッターとゲッターの集合
	public String getUpMaxHachutaniQt()
	{
		if(this.st_max_hachutani_qt == null || this.st_max_hachutani_qt.equals("")){
			return "";
		} else {
			this.setMaxHachutaniQt(st_max_hachutani_qt);
			return this.getMaxHachutaniQtString();
		}
	}

	//st_nohin_kigen_qtに対するセッターとゲッターの集合
	public String getUpNohinKigenQt()
	{
		if(this.st_nohin_kigen_qt == null || this.st_nohin_kigen_qt.equals("")){
			return "";
		} else {
			this.setNohinKigenQt(st_nohin_kigen_qt);
			return this.getNohinKigenQtString();
		}
	}

	//st_sime_time_1_qtに対するセッターとゲッターの集合
	public String getUpSimeTime1Qt()
	{
		if(this.st_sime_time_1_qt == null || this.st_sime_time_1_qt.equals("")){
			return "";
		} else {
			this.setSimeTime1Qt(st_sime_time_1_qt);
			return this.getSimeTime1QtString();
		}
	}

	//st_sime_time_2_qtに対するセッターとゲッターの集合
	public String getUpSimeTime2Qt()
	{
		if(this.st_sime_time_2_qt == null || this.st_sime_time_2_qt.equals("")){
			return "";
		} else {
			this.setSimeTime2Qt(st_sime_time_2_qt);
			return this.getSimeTime2QtString();
		}
	}

	//st_sime_time_3_qtに対するセッターとゲッターの集合
	public String getUpSimeTime3Qt()
	{
		if(this.st_sime_time_3_qt == null || this.st_sime_time_3_qt.equals("")){
			return "";
		} else {
			this.setSimeTime3Qt(st_sime_time_3_qt);
			return this.getSimeTime3QtString();
		}
	}

	//st_henpin_nbに対するセッターとゲッターの集合
	public String getUpHenpinNb()
	{
		if(this.st_henpin_nb == null || this.st_henpin_nb.equals("")){
			return "";
		} else {
			this.setHenpinNb(st_henpin_nb);
			return this.getHenpinNbString();
		}
	}

	//st_henpin_genka_vlに対するセッターとゲッターの集合
	public String getUpHenpinGenkaVl()
	{
		if(this.st_henpin_genka_vl == null || this.st_henpin_genka_vl.equals("")){
			return "";
		} else {
			this.setHenpinGenkaVl(st_henpin_genka_vl);
			return this.getHenpinGenkaVlString();
		}
	}

	//st_hanbai_limit_qtに対するセッターとゲッターの集合
	public String getUpHanbaiLimitQt()
	{
		if(this.st_hanbai_limit_qt == null || this.st_hanbai_limit_qt.equals("")){
			return "";
		} else {
			this.setHanbaiLimitQt(st_hanbai_limit_qt);
			return this.getHanbaiLimitQtString();
		}
	}

	//st_daisi_no_nbに対するセッターとゲッターの集合
//	public String getUpDaisiNoNb()
//	{
//		if(this.st_daisi_no_nb == null || this.st_daisi_no_nb.equals("")){
//			return "";
//		} else {
//			this.setDaisiNoNb(st_daisi_no_nb);
//			return this.getDaisiNoNbString();
//		}
//	}

	//st_unit_price_naiyoryo_qtに対するセッターとゲッターの集合
	public String getUpUnitPriceNaiyoryoQt()
	{
		if(this.st_unit_price_naiyoryo_qt == null || this.st_unit_price_naiyoryo_qt.equals("")){
			return "";
		} else {
			this.setUnitPriceNaiyoryoQt(st_unit_price_naiyoryo_qt);
			return this.getUnitPriceNaiyoryoQtString();
		}
	}

	//st_unit_price_kijun_tani_qtに対するセッターとゲッターの集合
	public String getUpUnitPriceKijunTaniQt()
	{
		if(this.st_unit_price_kijun_tani_qt == null || this.st_unit_price_kijun_tani_qt.equals("")){
			return "";
		} else {
			this.setUnitPriceKijunTaniQt(st_unit_price_kijun_tani_qt);
			return this.getUnitPriceKijunTaniQtString();
		}
	}

	//st_yoridori_qtに対するセッターとゲッターの集合
	public String getUpYoridoriQt()
	{
		if(this.st_yoridori_qt == null || this.st_yoridori_qt.equals("")){
			return "";
		} else {
			this.setYoridoriQt(st_yoridori_qt);
			return this.getYoridoriQtString();
		}
	}

	//st_tag_hyoji_baika_vlに対するセッターとゲッターの集合
	public String getUpTagHyojiBaikaVl()
	{
		if(this.st_tag_hyoji_baika_vl == null || this.st_tag_hyoji_baika_vl.equals("")){
			return "";
		} else {
			this.setTagHyojiBaikaVl(st_tag_hyoji_baika_vl);
			return this.getTagHyojiBaikaVlString();
		}
	}

	//st_case_irisu_qtに対するセッターとゲッターの集合
	public String getUpCaseIrisuQt()
	{
		if(this.st_case_irisu_qt == null || this.st_case_irisu_qt.equals("")){
			return "";
		} else {
			this.setCaseIrisuQt(st_case_irisu_qt);
			return this.getCaseIrisuQtString();
		}
	}

	//st_pack_width_qtに対するセッターとゲッターの集合
	public String getUpPackWidthQt()
	{
		if(this.st_pack_width_qt == null || this.st_pack_width_qt.equals("")){
			return "";
		} else {
			this.setPackWidthQt(st_pack_width_qt);
			return this.getPackWidthQtString();
		}
	}

	//st_pack_heigth_qtに対するセッターとゲッターの集合
	public String getUpPackHeigthQt()
	{
		if(this.st_pack_heigth_qt == null || this.st_pack_heigth_qt.equals("")){
			return "";
		} else {
			this.setPackHeigthQt(st_pack_heigth_qt);
			return this.getPackHeigthQtString();
		}
	}

	//st_pack_depth_qtに対するセッターとゲッターの集合
	public String getUpPackDepthQt()
	{
		if(this.st_pack_depth_qt == null || this.st_pack_depth_qt.equals("")){
			return "";
		} else {
			this.setPackDepthQt(st_pack_depth_qt);
			return this.getPackDepthQtString();
		}
	}

	//st_pack_weight_qtに対するセッターとゲッターの集合
	public String getUpPackWeightQt()
	{
		if(this.st_pack_weight_qt == null || this.st_pack_weight_qt.equals("")){
			return "";
		} else {
			this.setPackWeightQt(st_pack_weight_qt);
			return this.getPackWeightQtString();
		}
	}

	//st_min_zaikosu_qtに対するセッターとゲッターの集合
	public String getUpMinZaikosuQt()
	{
		if(this.st_min_zaikosu_qt == null || this.st_min_zaikosu_qt.equals("")){
			return "";
		} else {
			this.setMinZaikosuQt(st_min_zaikosu_qt);
			return this.getMinZaikosuQtString();
		}
	}

	//st_max_zaikosu_qtに対するセッターとゲッターの集合
	public String getUpMaxZaikosuQt()
	{
		if(this.st_max_zaikosu_qt == null || this.st_max_zaikosu_qt.equals("")){
			return "";
		} else {
			this.setMaxZaikosuQt(st_max_zaikosu_qt);
			return this.getMaxZaikosuQtString();
		}
	}

	//st_center_hachutani_qtに対するセッターとゲッターの集合
	public String getUpCenterHachutaniQt()
	{
		if(this.st_center_hachutani_qt == null || this.st_center_hachutani_qt.equals("")){
			return "";
		} else {
			this.setCenterHachutaniQt(st_center_hachutani_qt);
			return this.getCenterHachutaniQtString();
		}
	}

	//st_center_irisu_qtに対するセッターとゲッターの集合
	public String getUpCenterIrisuQt()
	{
		if(this.st_center_irisu_qt == null || this.st_center_irisu_qt.equals("")){
			return "";
		} else {
			this.setCenterIrisuQt(st_center_irisu_qt);
			return this.getCenterIrisuQtString();
		}
	}

	//st_center_weight_qtに対するセッターとゲッターの集合
	public String getUpCenterWeightQt()
	{
		if(this.st_center_weight_qt == null || this.st_center_weight_qt.equals("")){
			return "";
		} else {
			this.setCenterWeightQt(st_center_weight_qt);
			return this.getCenterWeightQtString();
		}
	}

	//st_tana_no_nbに対するセッターとゲッターの集合
	public String getUpTanaNoNb()
	{
		if(this.st_tana_no_nb == null || this.st_tana_no_nb.equals("")){
			return "";
		} else {
			this.setTanaNoNb(st_tana_no_nb);
			return this.getTanaNoNbString();
		}
	}

	//st_dan_no_nbに対するセッターとゲッターの集合
	public String getUpDanNoNb()
	{
		if(this.st_dan_no_nb == null || this.st_dan_no_nb.equals("")){
			return "";
		} else {
			this.setDanNoNb(st_dan_no_nb);
			return this.getDanNoNbString();
		}
	}

	//st_keiyaku_su_qtに対するセッターとゲッターの集合
	public String getUpKeiyakuSuQt()
	{
		if(this.st_keiyaku_su_qt == null || this.st_keiyaku_su_qt.equals("")){
			return "";
		} else {
			this.setKeiyakuSuQt(st_keiyaku_su_qt);
			return this.getKeiyakuSuQtString();
		}
	}

	//st_kijun_zaikosu_qtに対するセッターとゲッターの集合
	public String getUpKijunZaikosuQt()
	{
		if(this.st_kijun_zaikosu_qt == null || this.st_kijun_zaikosu_qt.equals("")){
			return "";
		} else {
			this.setKijunZaikosuQt(st_kijun_zaikosu_qt);
			return this.getKijunZaikosuQtString();
		}
	}

//	↓↓2006.06.26 zhujl カスタマイズ修正↓↓
//	 haifu_cdに対するセッターとゲッターの集合
	public boolean setHaiFuCd(String haifu_cd)
	{
		this.haifu_cd = haifu_cd;
		return true;
	}

//	 unit_naに対するセッターとゲッターの集合
	public boolean setUnit_Na(String unit_na)
	{
		this.unit_na = unit_na;
		return true;
	}
	public String getUnit_Na()
	{
		return this.unit_na;
	}

// subclass_naに対するセッターとゲッターの集合
	public boolean setSubclass_Na(String subclass_na)
	{
		this.subclass_na = subclass_na;
		return true;
	}
	public String getSubclass_Na()
	{
		return this.subclass_na;
	}
// user_idに対するセッターとゲッターの集合
	public boolean setUserId(String user_id)
	{
		this.user_id = user_id;

		return true;
	}
	public String getUserId()
	{
		return cutString(this.user_id,USER_ID_MAX_LENGTH);
	}
	public String getUserIdString()
	{
		return "'" + cutString(this.user_id,USER_ID_MAX_LENGTH) + "'";
	}
	public String getUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_id,USER_ID_MAX_LENGTH));
	}
// kikaku_kanji_na2に対するセッターとゲッターの集合
	public boolean setKikakuKanji2Na(String kikaku_kanji_2_na)
	{
		this.kikaku_kanji_2_na = kikaku_kanji_2_na;
		gamenFlg.put("kikaku_kanji_2_na","1");
		return true;
	}
	public String getKikaku_Kanji_Na2()
	{
		return cutString(this.kikaku_kanji_2_na,KIKAKU_KANJI_NA2_MAX_LENGTH);
	}
	public String getKikaku_Kanji_Na2String()
	{
		return "'" + cutString(this.kikaku_kanji_2_na,KIKAKU_KANJI_NA2_MAX_LENGTH) + "'";
	}
	public String getKikaku_Kanji_Na2HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kanji_2_na,KIKAKU_KANJI_NA2_MAX_LENGTH));
	}
//  kikaku_kana_na2に対するセッターとゲッターの集合
	public boolean setKikakuKana2Na(String kikaku_kana_2_na)
	{
		this.kikaku_kana_2_na = kikaku_kana_2_na;
		gamenFlg.put("kikaku_kana_2_na","1");
		return true;
	}
	public String getKikaku_Kana_Na2()
	{
		return this.kikaku_kana_2_na;
	}
//areacd_naに対するセッターとゲッターの集合
	public boolean setAreacd_Na(String areacd_na)
	{
		this.areacd_na = areacd_na;
		return true;
	}
	public String getAreacd_Na()
	{
		return this.areacd_na;
	}
// hachutani_naに対するセッターとゲッターの集合
	public boolean setHachuTaniNa(String hachu_tani_na)
	{
		this.hachu_tani_na = hachu_tani_na;
		gamenFlg.put("hachu_tani_na","1");
		return true;
	}
	public String getHachutani_Na()
	{
		return this.hachu_tani_na;
	}
//syohikigenに対するセッターとゲッターの集合
	public boolean setSyuhikigenDt(String syohikigen)
	{
		this.syohikigen = syohikigen;
		return true;
	}
	public String getSyuhikigenDt()
	{
		return cutString(this.syohikigen,SYOHIKIGEN_MAX_LENGTH);
	}
	public String getSyuhikigenDtString()
	{
		return "'" + cutString(this.syohikigen,SYOHIKIGEN_MAX_LENGTH) + "'";
	}
	public String getSyuhikigenDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohikigen,SYOHIKIGEN_MAX_LENGTH));
	}
// honbuzai_kbに対するセッターとゲッターの集合
	public boolean setHonbuzaiKb(String honbu_zai_kb)
	{
		this.honbu_zai_kb = honbu_zai_kb;
		gamenFlg.put("honbu_zai_kb","1");
		return true;
	}

	public String getHonbuzai_Kb()
	{
		return this.honbu_zai_kb;
	}
// fujisyohin_kbに対するセッターとゲッターの集合
	public boolean setFujisyohinKb(String fuji_syohin_kb)
	{
		this.fuji_syohin_kb = fuji_syohin_kb;
		gamenFlg.put("fuji_syohin_kb","1");
		return true;
	}

	public String getFujisyohin_Kb()
	{
		return this.fuji_syohin_kb;
	}

//  daichotenpoに対するセッターとゲッターの集合
	public boolean setDaichotenpoKb(String daicho_tenpo_kb)
	{
		this.daicho_tenpo_kb = daicho_tenpo_kb;
		gamenFlg.put("daicho_tenpo_kb","1");
		return true;
	}

	public String getDaichotenpoKb()
	{
		return this.daicho_tenpo_kb;
	}
//  daichohonbuに対するセッターとゲッターの集合
	public boolean setDaichohonbuKb(String daicho_honbu_kb)
	{
		this.daicho_honbu_kb = daicho_honbu_kb;
		gamenFlg.put("daicho_honbu_kb","1");
		return true;
	}

	public String getDaichohonbuKb()
	{
		return this.daicho_honbu_kb;
	}
// daichosiiresakiに対するセッターとゲッターの集合
	public boolean setDaichosiiresakiKb(String daicho_siiresaki_kb)
	{
		this.daicho_siiresaki_kb = daicho_siiresaki_kb;
//		<!--   ↓↓2006.07.20 wangjm カスタマイズ追加↓↓  -->
		gamenFlg.put("daicho_siiresaki_kb","1");
//		<!--   ↑↑2006.07.20 wangjm カスタマイズ追加↑↑  -->

		return true;
	}

	public String getDaichosiiresakiKb()
	{
		return this.daicho_siiresaki_kb;
	}
//  HinbanCd	に対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinban_cd)
	{
		this.hinban_cd = hinban_cd;
		gamenFlg.put("hinban_cd","1");
		return true;
	}

	public String getHinbanCd()
	{
		return this.hinban_cd;
	}
//  gtincdに対するセッターとゲッターの集合
	public boolean setGtinCd(String gtin_cd)
	{
		this.gtin_cd = gtin_cd;
		gamenFlg.put("gtin_cd","1");
		return true;
	}

	public String getGtinCd()
	{
		return this.gtin_cd;
	}

	public boolean setPreGentankaVl(String pre_gentanka_vl)
	{
		try
		{
//			↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
			this.st_pre_gentanka_vl = pre_gentanka_vl;
//			↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
			this.pre_gentanka_vl = Double.parseDouble(pre_gentanka_vl);
			gamenFlg.put("pre_gentanka_vl","1");
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	//	 pregentanka_vlに対するセッターとゲッターの集合
	public boolean setPreGentankaVl(double pre_gentanka_vl)
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
		this.st_pre_gentanka_vl = Double.toString(pre_gentanka_vl);
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
		this.pre_gentanka_vl = pre_gentanka_vl;
		gamenFlg.put("pre_gentanka_vl","1");
		return true;
	}
//	↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//	public double getPreGentankaVl()
//	{
//		return this.pre_gentanka_vl;
//	}
	public String getPreGentankaVl()
	{
		return this.st_pre_gentanka_vl;
	}
//	↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑

	public String getPreGentankaVlString()
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//		return Double.toString(this.pre_gentanka_vl);
		return this.st_pre_gentanka_vl;
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	}

// prebaitankavlに対するセッターとゲッターの集合
	public boolean setPreBaitankaVl(String pre_baitanka_vl)
	{
		try
		{
//			↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
			this.st_pre_baitanka_vl = pre_baitanka_vl;
//			↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
			this.pre_baitanka_vl = Long.parseLong(pre_baitanka_vl);
			gamenFlg.put("pre_baitanka_vl","1");
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPreBaitankaVl(long pre_baitanka_vl)
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
		this.st_pre_baitanka_vl = Long.toString(pre_baitanka_vl);
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
		this.pre_baitanka_vl = pre_baitanka_vl;
		gamenFlg.put("pre_baitanka_vl","1");
		return true;
	}
//	↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//	public long getPreBaitankaVl()
//	{
//		return this.pre_baitanka_vl;
//	}
	public String getPreBaitankaVl()
	{
		return this.st_pre_baitanka_vl;
	}
//	↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	public String getPreBaitankaVlString()
	{
//		↓↓2006.09.22 H.Yamamoto カスタマイズ修正↓↓
//		return Double.toString(this.pre_baitanka_vl);
		return this.st_pre_baitanka_vl;
//		↑↑2006.09.22 H.Yamamoto カスタマイズ修正↑↑
	}
// centerkyoyodtに対するセッターとゲッターの集合
	public boolean setCenterKyoyoDt(String center_kyoyo_dt)
	{
		this.center_kyoyo_dt = center_kyoyo_dt;
		gamenFlg.put("center_kyoyo_dt","1");
		return true;
	}

	public String getCenterKyoyoDt()
	{
		return this.center_kyoyo_dt;
	}
	//st_hanbai_limit_qtに対するセッターとゲッターの集合
	public boolean setStSyohiKigenDt(String st_syohi_kigen_dt)
	{
		this.st_syohi_kigen_dt = st_syohi_kigen_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohi_kigen_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	//st_hanbai_limit_qtに対するセッターとゲッターの集合
		public boolean setSyohiKigenDt(String syohi_kigen_dt)
	{
		this.syohi_kigen_dt = syohi_kigen_dt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohi_kigen_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSyohiKigenDt()
	{
		return this.syohi_kigen_dt;
	}

//  yusenbinkbに対するセッターとゲッターの集合
	public boolean setYusenBinKb(String yusenbinkb)
	{
		this.yusenbinkb = yusenbinkb;
		gamenFlg.put("yusen_bin_kb","1");
		return true;
	}

	public String getYusenBinKb()
	{
		return this.yusenbinkb;
	}

//  yusenbinkbに対するセッターとゲッターの集合
	public boolean setPluKb(String plu_kb)
	{
		this.plu_kb = plu_kb;
		gamenFlg.put("plu_kb","1");
		return true;
	}

	public String getPluKb()
	{
		return this.plu_kb;
	}

//	↓↓2006.06.26 zhujl カスタマイズ修正↓↓
	// unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		this.unit_cd = unit_cd;
		gamenFlg.put("unit_cd","1");
		return true;
	}
	public String getUnitCd()
	{
		return cutString(this.unit_cd,UNIT_CD_MAX_LENGTH);
	}
	public String getUnitCdString()
	{
		return "'" + cutString(this.unit_cd,UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getUnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_cd,UNIT_CD_MAX_LENGTH));
	}

	// haifu_cdに対するセッターとゲッターの集合
	public boolean setHaifuCd(String haifu_cd)
	{
		this.haifu_cd = haifu_cd;
		gamenFlg.put("haifu_cd","1");
		return true;
	}
	public String getHaifuCd()
	{
		return cutString(this.haifu_cd,HAIFU_CD_MAX_LENGTH);
	}
	public String getHaifuCdString()
	{
		return "'" + cutString(this.haifu_cd,HAIFU_CD_MAX_LENGTH) + "'";
	}
	public String getHaifuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haifu_cd,HAIFU_CD_MAX_LENGTH));
	}

	// subclass_cdに対するセッターとゲッターの集合
	public boolean setSubclassCd(String subclass_cd)
	{
		this.subclass_cd = subclass_cd;
		gamenFlg.put("subclass_cd","1");
		return true;
	}
	public String getSubclassCd()
	{
		return cutString(this.subclass_cd,SUBCLASS_CD_MAX_LENGTH);
	}
	public String getSubclassCdString()
	{
		return "'" + cutString(this.subclass_cd,SUBCLASS_CD_MAX_LENGTH) + "'";
	}
	public String getSubclassCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.subclass_cd,SUBCLASS_CD_MAX_LENGTH));
	}
	//	 tokubetu_genka_vlに対するセッターとゲッターの集合
	public boolean setTokubetuGenkaVl(String tokubetu_genka_vl)
	{
		this.tokubetu_genka_vl = tokubetu_genka_vl;
		gamenFlg.put("tokubetu_genka_vl","1");
		return true;
	}
	public String getTokubetuGenkaVl()
	{
		if(this.tokubetu_genka_vl == null){
			return "";
		} else {
			return this.tokubetu_genka_vl;
		}
	}
	public String getTokubetuGenkaVlString()
	{
		return this.tokubetu_genka_vl;
	}
	//	 syokai_keiyaku_qtに対するセッターとゲッターの集合
	public boolean setSyokaiKeiyakuQt(String syokai_keiyaku_qt)
	{
		this.syokai_keiyaku_qt = syokai_keiyaku_qt;
		gamenFlg.put("syokai_keiyaku_qt","1");
		return true;
	}
	public String getSyokaiKeiyakuQt()
	{
		return cutString(this.syokai_keiyaku_qt,SYOKAI_KEIYAKU_QT_MAX_LENGTH);
	}
	public String getSyokaiKeiyakuQtString()
	{
		return "'" + cutString(this.syokai_keiyaku_qt,SYOKAI_KEIYAKU_QT_MAX_LENGTH) + "'";
	}
	public String getSyokaiKeiyakuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syokai_keiyaku_qt,SYOKAI_KEIYAKU_QT_MAX_LENGTH));
	}

	//	 konkai_tuikeiyaku_qtに対するセッターとゲッターの集合
	public boolean setKonkaiTuikeiyakuQt(String konkai_tuikeiyaku_qt)
	{
		this.konkai_tuikeiyaku_qt = konkai_tuikeiyaku_qt;
		gamenFlg.put("syokai_keiyaku_qt","1");
		return true;
	}
	public String getKonkaiTuikeiyakuQt()
	{
		if(this.konkai_tuikeiyaku_qt == null){
			return "";
		} else {
			return this.konkai_tuikeiyaku_qt;
		}
	}
	public String getKonkaiTuikeiyakuQtString()
	{
		if(this.konkai_tuikeiyaku_qt == null){
			return "";
		} else {
			return "'" + this.konkai_tuikeiyaku_qt + "'";
		}
	}
	public String getKonkaiTuikeiyakuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.konkai_tuikeiyaku_qt,KONKAI_TUIKEIYAKU_QT_MAX_LENGTH));
	}

//	↓↓2006.07.12 xubq カスタマイズ修正↓↓
//	//	 area_cdに対するセッターとゲッターの集合
//	public boolean setAreaCd(String area_cd)
//	{
//		this.area_cd = area_cd;
//		gamenFlg.put("area_cd","1");
//		return true;
//	}
//	public String getAreaCd()
//	{
//		return cutString(this.area_cd,KONKAI_TUIKEIYAKU_QT_MAX_LENGTH);
//	}
//	public String getAreaCdString()
//	{
//		return "'" + cutString(this.area_cd,KONKAI_TUIKEIYAKU_QT_MAX_LENGTH) + "'";
//	}
//	public String getAreaCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.area_cd,KONKAI_TUIKEIYAKU_QT_MAX_LENGTH));
//	}
//	↑↑2006.07.12 xubq カスタマイズ修正↑↑

	//	 fuji_syohin_kbに対するセッターとゲッターの集合
	public boolean setFujiSyohinKb(String fuji_syohin_kb)
	{
		this.fuji_syohin_kb = fuji_syohin_kb;
		gamenFlg.put("fuji_syohin_kb","1");
		return true;
	}
	public String getFujiSyohinKb()
	{
		return this.fuji_syohin_kb;
	}
	public String getFujiSyohinKbString()
	{
		return "'" + this.fuji_syohin_kb + "'";
	}
	public String getFujiSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(this.fuji_syohin_kb);
	}

	//	 pb_kbに対するセッターとゲッターの集合
	public boolean setPbKb(String pb_kb)
	{
		this.pb_kb = pb_kb;
		gamenFlg.put("pb_kb","1");
		return true;
	}
	public String getPbKb()
	{
		return this.pb_kb;
	}
	public String getPbKbString()
	{
		return "'" + this.pb_kb + "'";
	}
	public String getPbKbHTMLString()
	{
		return HTMLStringUtil.convert(this.pb_kb);
	}

	//	 keshi_baika_vlに対するセッターとゲッターの集合
	public boolean setKeshiBaikaVl(String keshi_baika_vl)
	{
		this.keshi_baika_vl = keshi_baika_vl;
		gamenFlg.put("keshi_baika_vl","1");
		return true;
	}
	public String getKeshiBaikaVl()
	{
		if(this.keshi_baika_vl == null){
			return "";
		} else {
			return this.keshi_baika_vl;
		}
	}
	public String getKeshiBaikaVlString()
	{
		return String.valueOf(this.keshi_baika_vl);
	}

	//	 area_naに対するセッターとゲッターの集合
	public boolean setAreaNa(String area_na)
	{
		this.area_na = area_na;
		return true;
	}
	public String getAreaNa()
	{
		return cutString(this.area_na,AREA_NA_MAX_LENGTH);
	}
	public String getAreaNaString()
	{
		return "'" + cutString(this.area_na,AREA_NA_MAX_LENGTH) + "'";
	}
	public String getAreaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.area_na,AREA_NA_MAX_LENGTH));
	}

	// bumon_kanji_rnに対するセッターとゲッターの集合
	public boolean setBumonKanjiRn(String bumon_kanji_rn)
	{
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}
	public String getBumonKanjiRn()
	{
		return this.bumon_kanji_rn;
	}

	// hinmei_kanjiに対するセッターとゲッターの集合
	public boolean setHinmeiKanji(String hinmei_kanji)
	{
		this.hinmei_kanji = hinmei_kanji;
		return true;
	}
	public String getHinmeiKanji()
	{
		return this.hinmei_kanji;
	}

//	↑↑2006.06.26 zhujl カスタマイズ修正↑↑

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  bumon_cd = " + getBumonCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  hacyu_syohin_kb = " + getHacyuSyohinKbString()
			+ "  system_kb = " + getSystemKbString()
			+ "  gyosyu_kb = " + getGyosyuKbString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hinmoku_cd = " + getHinmokuCdString()
			+ "  mark_group_cd = " + getMarkGroupCdString()
			+ "  mark_cd = " + getMarkCdString()
			+ "  syohin_2_cd = " + getSyohin2CdString()
			+ "  ketasu_kb = " + getKetasuKbString()
			+ "  maker_cd = " + getMakerCdString()
			+ "  hinmei_kanji_na = " + getHinmeiKanjiNaString()
			+ "  kikaku_kanji_na = " + getKikakuKanjiNaString()
			+ "  hinmei_kana_na = " + getHinmeiKanaNaString()
			+ "  kikaku_kana_na = " + getKikakuKanaNaString()
			+ "  syohin_width_qt = " + getSyohinWidthQtString()
			+ "  syohin_height_qt = " + getSyohinHeightQtString()
			+ "  syohin_depth_qt = " + getSyohinDepthQtString()
			+ "  siire_hinban_cd = " + getSiireHinbanCdString()
			+ "  bland_cd = " + getBlandCdString()
			+ "  yunyuhin_kb = " + getYunyuhinKbString()
			+ "  santi_cd = " + getSantiCdString()
			+ "  maker_kibo_kakaku_vl = " + getMakerKiboKakakuVlString()
			+ "  nohin_ondo_kb = " + getNohinOndoKbString()
			+ "  comment_tx = " + getCommentTxString()
			+ "  ten_hachu_st_dt = " + getTenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getTenHachuEdDtString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  hachutani_irisu_qt = " + getHachutaniIrisuQtString()
			+ "  max_hachutani_qt = " + getMaxHachutaniQtString()
			+ "  teikan_kb = " + getTeikanKbString()
			+ "  eos_kb = " + getEosKbString()
			+ "  nohin_kigen_qt = " + getNohinKigenQtString()
			+ "  nohin_kigen_kb = " + getNohinKigenKbString()
			+ "  mst_siyofuka_kb = " + getMstSiyofukaKbString()
			+ "  hachu_teisi_kb = " + getHachuTeisiKbString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  daihyo_haiso_cd = " + getDaihyoHaisoCdString()
			+ "  ten_siiresaki_kanri_cd = " + getTenSiiresakiKanriCdString()
//			+ "  ten_haiso_kanri_cd = " + getTenHaisoKanriCdString()
			+ "  soba_syohin_kb = " + getSobaSyohinKbString()
			+ "  bin_1_kb = " + getBin1KbString()
			+ "  hachu_pattern_1_kb = " + getHachuPattern1KbString()
			+ "  sime_time_1_qt = " + getSimeTime1QtString()
			+ "  c_nohin_rtime_1_kb = " + getCNohinRtime1KbString()
			+ "  ten_nohin_rtime_1_kb = " + getTenNohinRtime1KbString()
			+ "  ten_nohin_time_1_kb = " + getTenNohinTime1KbString()
			+ "  bin_2_kb = " + getBin2KbString()
			+ "  hachu_pattern_2_kb = " + getHachuPattern2KbString()
			+ "  sime_time_2_qt = " + getSimeTime2QtString()
			+ "  c_nohin_rtime_2_kb = " + getCNohinRtime2KbString()
			+ "  ten_nohin_rtime_2_kb = " + getTenNohinRtime2KbString()
			+ "  ten_nohin_time_2_kb = " + getTenNohinTime2KbString()
			+ "  bin_3_kb = " + getBin3KbString()
			+ "  hachu_pattern_3_kb = " + getHachuPattern3KbString()
			+ "  sime_time_3_qt = " + getSimeTime3QtString()
			+ "  c_nohin_rtime_3_kb = " + getCNohinRtime3KbString()
			+ "  ten_nohin_rtime_3_kb = " + getTenNohinRtime3KbString()
			+ "  ten_nohin_time_3_kb = " + getTenNohinTime3KbString()
			+ "  c_nohin_rtime_kb = " + getCNohinRtimeKbString()
			+ "  zei_kb = " + getZeiKbString()
			+ "  hanbai_kikan_kb = " + getHanbaiKikanKbString()
			+ "  syohin_kb = " + getSyohinKbString()
			+ "  buturyu_kb = " + getButuryuKbString()
			+ "  yokomoti_kb = " + getYokomotiKbString()
			+ "  ten_groupno_cd = " + getTenGroupnoCdString()
			+ "  ten_zaiko_kb = " + getTenZaikoKbString()
			+ "  hanbai_seisaku_kb = " + getHanbaiSeisakuKbString()
			+ "  henpin_nb = " + getHenpinNbString()
			+ "  henpin_genka_vl = " + getHenpinGenkaVlString()
			+ "  rebate_fg = " + getRebateFgString()
			+ "  hanbai_st_dt = " + getHanbaiStDtString()
			+ "  hanbai_ed_dt = " + getHanbaiEdDtString()
			+ "  hanbai_limit_qt = " + getHanbaiLimitQtString()
			+ "  hanbai_limit_kb = " + getHanbaiLimitKbString()
			+ "  auto_del_kb = " + getAutoDelKbString()
			+ "  got_mujyoken_fg = " + getGotMujyokenFgString()
			+ "  got_start_mm = " + getGotStartMmString()
			+ "  got_end_mm = " + getGotEndMmString()
			+ "  e_shop_kb = " + getEShopKbString()
			+ "  plu_send_dt = " + getPluSendDtString()
			+ "  rec_hinmei_kanji_na = " + getRecHinmeiKanjiNaString()
			+ "  rec_hinmei_kana_na = " + getRecHinmeiKanaNaString()
			+ "  keyplu_fg = " + getKeypluFgString()
			+ "  pc_kb = " + getPcKbString()
			+ "  daisi_no_nb = " + getDaisiNoNb()
			+ "  unit_price_tani_kb = " + getUnitPriceTaniKbString()
			+ "  unit_price_naiyoryo_qt = " + getUnitPriceNaiyoryoQtString()
			+ "  unit_price_kijun_tani_qt = " + getUnitPriceKijunTaniQtString()
			+ "  shinazoroe_kb = " + getShinazoroeKbString()
			+ "  kumisu_kb = " + getKumisuKbString()
			+ "  nefuda_kb = " + getNefudaKbString()
			+ "  yoridori_kb = " + getYoridoriKbString()
			+ "  yoridori_qt = " + getYoridoriQtString()
			+ "  tag_hyoji_baika_vl = " + getTagHyojiBaikaVlString()
			+ "  season_cd = " + getSeasonCdString()
			+ "  hukusyu_cd = " + getHukusyuCdString()
			+ "  style_cd = " + getStyleCdString()
			+ "  scene_cd = " + getSceneCdString()
			+ "  sex_cd = " + getSexCdString()
			+ "  age_cd = " + getAgeCdString()
			+ "  generation_cd = " + getGenerationCdString()
			+ "  sozai_cd = " + getSozaiCdString()
			+ "  pattern_cd = " + getPatternCdString()
			+ "  oriami_cd = " + getOriamiCdString()
			+ "  huka_kino_cd = " + getHukaKinoCdString()
			+ "  size_cd = " + getSizeCdString()
			+ "  color_cd = " + getColorCdString()
			+ "  syuzei_hokoku_kb = " + getSyuzeiHokokuKbString()
			+ "  tc_jyouho_na = " + getTcJyouhoNaString()
			+ "  nohin_syohin_cd = " + getNohinSyohinCdString()
			+ "  itfCd = " + getItfCdString()
			+ "  case_irisu_qt = " + getCaseIrisuQtString()
			+ "  nyuka_syohin_cd = " + getNyukaSyohinCdString()
			+ "  pack_width_qt = " + getPackWidthQtString()
			+ "  pack_heigth_qt = " + getPackHeigthQtString()
			+ "  pack_depth_qt = " + getPackDepthQtString()
			+ "  pack_weight_qt = " + getPackWeightQtString()
			+ "  center_zaiko_kb = " + getCenterZaikoKbString()
			+ "  zaiko_hachu_saki = " + getZaikoHachuSakiString()
			+ "  zaiko_center_cd = " + getZaikoCenterCdString()
			+ "  min_zaikosu_qt = " + getMinZaikosuQtString()
			+ "  max_zaikosu_qt = " + getMaxZaikosuQtString()
			+ "  center_hachutani_kb = " + getCenterHachutaniKbString()
			+ "  center_hachutani_qt = " + getCenterHachutaniQtString()
			+ "  center_irisu_qt = " + getCenterIrisuQtString()
			+ "  center_weight_qt = " + getCenterWeightQtString()
			+ "  tana_no_nb = " + getTanaNoNbString()
			+ "  dan_no_nb = " + getDanNoNbString()
			+ "  keiyaku_su_qt = " + getKeiyakuSuQtString()
			+ "  keiyaku_pattern_kb = " + getKeiyakuPatternKbString()
			+ "  keiyaku_st_dt = " + getKeiyakuStDtString()
			+ "  keiyaku_ed_dt = " + getKeiyakuEdDtString()
			+ "  kijun_zaikosu_qt = " + getKijunZaikosuQtString()
			+ "  d_zokusei_1_na = " + getDZokusei1NaString()
			+ "  s_zokusei_1_na = " + getSZokusei1NaString()
			+ "  d_zokusei_2_na = " + getDZokusei2NaString()
			+ "  s_zokusei_2_na = " + getSZokusei2NaString()
			+ "  d_zokusei_3_na = " + getDZokusei3NaString()
			+ "  s_zokusei_3_na = " + getSZokusei3NaString()
			+ "  d_zokusei_4_na = " + getDZokusei4NaString()
			+ "  s_zokusei_4_na = " + getSZokusei4NaString()
			+ "  d_zokusei_5_na = " + getDZokusei5NaString()
			+ "  s_zokusei_5_na = " + getSZokusei5NaString()
			+ "  d_zokusei_6_na = " + getDZokusei6NaString()
			+ "  s_zokusei_6_na = " + getSZokusei6NaString()
			+ "  d_zokusei_7_na = " + getDZokusei7NaString()
			+ "  s_zokusei_7_na = " + getSZokusei7NaString()
			+ "  d_zokusei_8_na = " + getDZokusei8NaString()
			+ "  s_zokusei_8_na = " + getSZokusei8NaString()
			+ "  d_zokusei_9_na = " + getDZokusei9NaString()
			+ "  s_zokusei_9_na = " + getSZokusei9NaString()
			+ "  d_zokusei_10_na = " + getDZokusei10NaString()
			+ "  s_zokusei_10_na = " + getSZokusei10NaString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
//			↓↓仕様追加による変更（2005.06.29）↓↓
			+ "  sinki_toroku_dt = " + getSinkiTorokuDtString()
			+ "  henko_dt = " + getHenkoDtString()
//			↑↑仕様追加による変更（2005.06.29）↑↑

			//		↓↓2006.06.26 zhujl カスタマイズ修正↓↓
		+ "  unit_cd = " + getUnitCdString()
		+ "  haifu_cd = " + getHaifuCdString()
		+ "  subclass_cd = " + getSubclassCdString()
		+ "  sode_cd = " + getSodeCdString()
		+ "  keshi_baika_vl = " + getKeshiBaikaVlString()
		+ "  tokubetu_genka_vl = " + getTokubetuGenkaVlString()
		+ "  syokai_keiyaku_qt = " + getSyokaiKeiyakuQtString()
		+ "  konkai_tuikeiyaku_qt = " + getKonkaiTuikeiyakuQtString()
//		↓↓2006.07.12 xubq カスタマイズ修正↓↓
//		+ "  area_cd = " + getAreaCdString()
//		↑↑2006.07.12 xubq カスタマイズ修正↑↑
		+ "  fuji_syohin_kb = " + getFujiSyohinKbString()
		+ "  pb_kb = " + getPbKbString()
		+ "  yoridori_vl = " + getYoridoriVlString()
		+ "  nefuda_ukewatasi_dt = " + getNefudaUkewatasiDtString()
		+ "  nefuda_ukewatasi_kb = " + getNefudaUkewatasiKbString()
//		↑↑2006.06.26 zhujl カスタマイズ修正↑↑

			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst11_SyohinBean コピー後のクラス
	 */
	public mst110101_SyohinBean createClone()
	{
		mst110101_SyohinBean bean = new mst110101_SyohinBean();
		bean.setBumonCd(this.bumon_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setHacyuSyohinKb(this.hacyu_syohin_kb);
		bean.setSystemKb(this.system_kb);
		bean.setGyosyuKb(this.gyosyu_kb);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinmokuCd(this.hinmoku_cd);
		bean.setMarkGroupCd(this.mark_group_cd);
		bean.setMarkCd(this.mark_cd);
		bean.setSyohin2Cd(this.syohin_2_cd);
		bean.setKetasuKb(this.ketasu_kb);
		bean.setMakerCd(this.maker_cd);
		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);
		bean.setKikakuKanjiNa(this.kikaku_kanji_na);
		bean.setHinmeiKanaNa(this.hinmei_kana_na);
		bean.setKikakuKanaNa(this.kikaku_kana_na);
		bean.setSyohinWidthQt(this.syohin_width_qt);
		bean.setSyohinHeightQt(this.syohin_height_qt);
		bean.setSyohinDepthQt(this.syohin_depth_qt);
		bean.setSiireHinbanCd(this.siire_hinban_cd);
		bean.setBlandCd(this.bland_cd);
		bean.setYunyuhinKb(this.yunyuhin_kb);
		bean.setSantiCd(this.santi_cd);
		bean.setMakerKiboKakakuVl(this.maker_kibo_kakaku_vl);
		bean.setNohinOndoKb(this.nohin_ondo_kb);
		bean.setCommentTx(this.comment_tx);
		bean.setTenHachuStDt(this.ten_hachu_st_dt);
		bean.setTenHachuEdDt(this.ten_hachu_ed_dt);
		bean.setGentankaVl(Double.toString(this.gentanka_vl));
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setHachutaniIrisuQt(Double.toString(this.hachutani_irisu_qt));
		bean.setMaxHachutaniQt(Double.toString(this.max_hachutani_qt));
		bean.setTeikanKb(this.teikan_kb);
		bean.setEosKb(this.eos_kb);
		bean.setNohinKigenQt(this.nohin_kigen_qt);
		bean.setNohinKigenKb(this.nohin_kigen_kb);
		bean.setMstSiyofukaKb(this.mst_siyofuka_kb);
		bean.setHachuTeisiKb(this.hachu_teisi_kb);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setDaihyoHaisoCd(this.daihyo_haiso_cd);
		bean.setTenSiiresakiKanriCd(this.ten_siiresaki_kanri_cd);
//		bean.setTenHaisoKanriCd(this.ten_haiso_kanri_cd);
		bean.setSobaSyohinKb(this.soba_syohin_kb);
		bean.setBin1Kb(this.bin_1_kb);
		bean.setHachuPattern1Kb(this.hachu_pattern_1_kb);
		bean.setSimeTime1Qt(this.sime_time_1_qt);
		bean.setCNohinRtime1Kb(this.c_nohin_rtime_1_kb);
		bean.setTenNohinRtime1Kb(this.ten_nohin_rtime_1_kb);
		bean.setTenNohinTime1Kb(this.ten_nohin_time_1_kb);
		bean.setBin2Kb(this.bin_2_kb);
		bean.setHachuPattern2Kb(this.hachu_pattern_2_kb);
		bean.setSimeTime2Qt(this.sime_time_2_qt);
		bean.setCNohinRtime2Kb(this.c_nohin_rtime_2_kb);
		bean.setTenNohinRtime2Kb(this.ten_nohin_rtime_2_kb);
		bean.setTenNohinTime2Kb(this.ten_nohin_time_2_kb);
		bean.setBin3Kb(this.bin_3_kb);
		bean.setHachuPattern3Kb(this.hachu_pattern_3_kb);
		bean.setSimeTime3Qt(this.sime_time_3_qt);
		bean.setCNohinRtime3Kb(this.c_nohin_rtime_3_kb);
		bean.setTenNohinRtime3Kb(this.ten_nohin_rtime_3_kb);
		bean.setTenNohinTime3Kb(this.ten_nohin_time_3_kb);
		bean.setCNohinRtimeKb(this.c_nohin_rtime_kb);
		bean.setZeiKb(this.zei_kb);
		bean.setHanbaiKikanKb(this.hanbai_kikan_kb);
		bean.setSyohinKb(this.syohin_kb);
		bean.setButuryuKb(this.buturyu_kb);
		bean.setYokomotiKb(this.yokomoti_kb);
		bean.setTenGroupnoCd(this.ten_groupno_cd);
		bean.setTenZaikoKb(this.ten_zaiko_kb);
		bean.setHanbaiSeisakuKb(this.hanbai_seisaku_kb);
		bean.setHenpinNb(this.henpin_nb);
		bean.setHenpinGenkaVl(this.henpin_genka_vl);
		bean.setRebateFg(this.rebate_fg);
		bean.setHanbaiStDt(this.hanbai_st_dt);
		bean.setHanbaiEdDt(this.hanbai_ed_dt);
		bean.setHanbaiLimitQt(this.hanbai_limit_qt);
		bean.setHanbaiLimitKb(this.hanbai_limit_kb);
		bean.setAutoDelKb(this.auto_del_kb);
		bean.setGotMujyokenFg(this.got_mujyoken_fg);
		bean.setGotStartMm(this.got_start_mm);
		bean.setGotEndMm(this.got_end_mm);
		bean.setEShopKb(this.e_shop_kb);
		bean.setPluSendDt(this.plu_send_dt);
		bean.setRecHinmeiKanjiNa(this.rec_hinmei_kanji_na);
		bean.setRecHinmeiKanaNa(this.rec_hinmei_kana_na);
		bean.setKeypluFg(this.keyplu_fg);
		bean.setPcKb(this.pc_kb);
		bean.setDaisiNoNb(this.daisi_no_nb);
		bean.setUnitPriceTaniKb(this.unit_price_tani_kb);
		bean.setUnitPriceNaiyoryoQt(this.unit_price_naiyoryo_qt);
		bean.setUnitPriceKijunTaniQt(this.unit_price_kijun_tani_qt);
		bean.setShinazoroeKb(this.shinazoroe_kb);
		bean.setKumisuKb(this.kumisu_kb);
		bean.setNefudaKb(this.nefuda_kb);
		bean.setYoridoriKb(this.yoridori_kb);
		bean.setYoridoriQt(this.yoridori_qt);
		bean.setTagHyojiBaikaVl(this.tag_hyoji_baika_vl);
		bean.setSeasonCd(this.season_cd);
		bean.setHukusyuCd(this.hukusyu_cd);
		bean.setStyleCd(this.style_cd);
		bean.setSceneCd(this.scene_cd);
		bean.setSexCd(this.sex_cd);
		bean.setAgeCd(this.age_cd);
		bean.setGenerationCd(this.generation_cd);
		bean.setSozaiCd(this.sozai_cd);
		bean.setPatternCd(this.pattern_cd);
		bean.setOriamiCd(this.oriami_cd);
		bean.setHukaKinoCd(this.huka_kino_cd);
		bean.setSizeCd(this.size_cd);
		bean.setColorCd(this.color_cd);
		bean.setSyuzeiHokokuKb(this.syuzei_hokoku_kb);
		bean.setTcJyouhoNa(this.tc_jyouho_na);
		bean.setNohinSyohinCd(this.nohin_syohin_cd);
		bean.setItfCd(this.itfCd);
		bean.setCaseIrisuQt(Double.toString(this.case_irisu_qt));
		bean.setNyukaSyohinCd(this.nyuka_syohin_cd);
		bean.setPackWidthQt(this.pack_width_qt);
		bean.setPackHeigthQt(this.pack_heigth_qt);
		bean.setPackDepthQt(this.pack_depth_qt);
		bean.setPackWeightQt(this.pack_weight_qt);
		bean.setCenterZaikoKb(this.center_zaiko_kb);
		bean.setZaikoHachuSaki(this.zaiko_hachu_saki);
		bean.setZaikoCenterCd(this.zaiko_center_cd);
		bean.setMinZaikosuQt(this.min_zaikosu_qt);
		bean.setMaxZaikosuQt(this.max_zaikosu_qt);
		bean.setCenterHachutaniKb(this.center_hachutani_kb);
		bean.setCenterHachutaniQt(Double.toString(this.center_hachutani_qt));
		bean.setCenterIrisuQt(Double.toString(this.center_irisu_qt));
		bean.setCenterWeightQt(this.center_weight_qt);
		bean.setTanaNoNb(this.tana_no_nb);
		bean.setDanNoNb(this.dan_no_nb);
		bean.setKeiyakuSuQt(this.keiyaku_su_qt);
		bean.setKeiyakuPatternKb(this.keiyaku_pattern_kb);
		bean.setKeiyakuStDt(this.keiyaku_st_dt);
		bean.setKeiyakuEdDt(this.keiyaku_ed_dt);
		bean.setKijunZaikosuQt(this.kijun_zaikosu_qt);
		bean.setDZokusei1Na(this.d_zokusei_1_na);
		bean.setSZokusei1Na(this.s_zokusei_1_na);
		bean.setDZokusei2Na(this.d_zokusei_2_na);
		bean.setSZokusei2Na(this.s_zokusei_2_na);
		bean.setDZokusei3Na(this.d_zokusei_3_na);
		bean.setSZokusei3Na(this.s_zokusei_3_na);
		bean.setDZokusei4Na(this.d_zokusei_4_na);
		bean.setSZokusei4Na(this.s_zokusei_4_na);
		bean.setDZokusei5Na(this.d_zokusei_5_na);
		bean.setSZokusei5Na(this.s_zokusei_5_na);
		bean.setDZokusei6Na(this.d_zokusei_6_na);
		bean.setSZokusei6Na(this.s_zokusei_6_na);
		bean.setDZokusei7Na(this.d_zokusei_7_na);
		bean.setSZokusei7Na(this.s_zokusei_7_na);
		bean.setDZokusei8Na(this.d_zokusei_8_na);
		bean.setSZokusei8Na(this.s_zokusei_8_na);
		bean.setDZokusei9Na(this.d_zokusei_9_na);
		bean.setSZokusei9Na(this.s_zokusei_9_na);
		bean.setDZokusei10Na(this.d_zokusei_10_na);
		bean.setSZokusei10Na(this.s_zokusei_10_na);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
//		↓↓仕様追加による変更（2005.06.29）↓↓
		bean.setSinkiTorokuDt(this.sinki_toroku_dt);
		bean.setHenkoDt(this.henko_dt);
//		↑↑仕様追加による変更（2005.06.29）↑↑
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
		if( !( o instanceof mst110101_SyohinBean ) ) return false;
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
				byte bt[] = base.substring(pos,pos+1).getBytes("UTF-8");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			}
			catch(Exception e)
			{
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END
			}
		}
		return wk;
	}

//BUGNO-S075 2005.07.20 Sirius START
	/**
	 * @return
	 */
	public HashMap getGamenFlg() {
		return gamenFlg;
	}

	/**
	 * @param map
	 */
	public void setGamenFlg(HashMap map) {
		gamenFlg = map;
	}
//BUGNO-S075 2005.07.20 Sirius END

}
