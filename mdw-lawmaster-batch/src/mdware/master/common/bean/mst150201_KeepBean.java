/**
 * <p>タイトル: 新ＭＤシステム（マスター管理） 商品マスタ登録（ディリー）の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する  商品マスタ登録（ディリー）の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/08)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理） 商品マスタ登録（ディリー）の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する 商品マスタ登録（ディリー）の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/08)初版作成
 * @version 1.1(2005/05/26)項目修正
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
public class mst150201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();


//	 ↓↓2006.07.10 wangjm カスタマイズ追加↓↓

	public static final int BUMON_CD_MAX_LENGTH 				   = 3;	   //部門コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH 				= 13;	//商品コードの長さ

	public static final int UNIT_CD_MAX_LENGTH                  = 4;    //ユニットコードの長さ

	public static final int SUBCLASS_CD_MAX_LENGTH              = 5;    //サブクラスコードの長さ
	public static final int SYOHIKIGEN_MAX_LENGTH               = 3;    //消費期限の長さ

	public static final int USER_ID_MAX_LENGTH                  = 6;    //バイヤーNoの長さ

	public static final int KIKAKU_KANJI_NA2_MAX_LENGTH         = 14;   //漢字規格2の長さ
	public static final int HAIFU_CD_MAX_LENGTH                 = 2;    //配布コードの長さ

//	 ↑↑2006.07.10 wangjm カスタマイズ追加↑↑


//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	public static final int HANKU_CD_MAX_LENGTH 					= 4;	//販区コードの長さ
//	 ↓↓ DB変更に伴う対応(2005.09.18 Y.Inaba)
//	public static final int SANTI_CD_MAX_LENGTH 					= 3;	//原産国/産地コードの長さ
//	public static final int SANTI_CD_MAX_LENGTH 					= 4;	//原産国/産地コードの長さ
// ↑↑ DB変更に伴う対応(2005.09.18 Y.Inaba)
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
//	public static final int SYOHIN_CD_MAX_LENGTH 				= 13;	//商品コードの長さ
	public static final int YUKO_DT_MAX_LENGTH 					= 8;	//有効日の長さ
	public static final int HACYU_SYOHIN_KB_MAX_LENGTH           = 1;	//発注商品コード区分
	public static final int SYSTEM_KB_MAX_LENGTH                 = 1;	//システム区分
	public static final int GYOSYU_KB_MAX_LENGTH 				= 3;	//業種区分の長さ
	public static final int HINSYU_CD_MAX_LENGTH 				= 4;	//品種コードの長さ
	public static final int TANPIN_CD_MAX_LENGTH 				= 3;	//単品コードの長さ
	public static final int HINMOKU_CD_MAX_LENGTH 				= 4;	//品目の長さ
	public static final int MARK_GROUP_CD_MAX_LENGTH 			= 1;	//マークグループの長さ
	public static final int MARK_CD_MAX_LENGTH 					= 4;	//マークコードの長さ
	public static final int SYOHIN_2_CD_MAX_LENGTH 				= 13;	//商品コード２の長さ
	public static final int KETASU_KB_MAX_LENGTH 				= 1;	//桁数区分の長さ
//	↓↓仕様変更（2005.08.09）↓↓
//	public static final int MAKER_CD_MAX_LENGTH 					= 7;	//メーカーコードの長さ
	public static final int MAKER_CD_MAX_LENGTH 					= 9;	//メーカーコードの長さ
//	↑↑仕様変更（2005.08.09）↑↑
	public static final int HINMEI_KANJI_NA_MAX_LENGTH 			= 80;	//漢字品名の長さ
	public static final int KIKAKU_KANJI_NA_MAX_LENGTH 			= 40;	//漢字規格の長さ
//	↓↓仕様変更（2005.08.18）↓↓
//	public static final int HINMEI_KANA_NA_MAX_LENGTH 			= 80;	//カナ品名の長さ
//	public static final int KIKAKU_KANA_NA_MAX_LENGTH 			= 40;	//カナ規格の長さ
	public static final int HINMEI_KANA_NA_MAX_LENGTH 			= 25;	//カナ品名の長さ
	public static final int KIKAKU_KANA_NA_MAX_LENGTH 			= 10;	//カナ規格の長さ
//	↑↑仕様変更（2005.08.18）↑↑
	public static final int SYOHIN_WIDTH_QT_MAX_LENGTH 			= 4;	//商品サイズ(幅)の長さ
	public static final int SYOHIN_HEIGHT_QT_MAX_LENGTH 			= 4;	//商品サイズ(高さ)の長さ
	public static final int SYOHIN_DEPTH_QT_MAX_LENGTH 			= 4;	//商品サイズ(奥行き)の長さ
	public static final int SIIRE_HINBAN_CD_MAX_LENGTH			= 15;	//仕入先品番の長さ
	public static final int BLAND_CD_MAX_LENGTH 					= 3;	//ブランドコードの長さ
	public static final int YUNYUHIN_KB_MAX_LENGTH 				= 1;	//輸入品区分の長さ

	public static final int MAKER_KIBO_KAKAKU_VL_MAX_LENGTH 		= 10;	//メーカー希望小売り価格(税込み)の長さ
	public static final int NOHIN_ONDO_KB_MAX_LENGTH 			= 1;	//納品温度帯の長さ
	public static final int COMMENT_TX_MAX_LENGTH 				= 20;	//コメントの長さ
	public static final int TEN_HACHU_ST_DT_MAX_LENGTH 			= 8;	//店舗発注開始日の長さ
	public static final int TEN_HACHU_ED_DT_MAX_LENGTH 			= 8;	//店舗発注終了日の長さ
	public static final int GENTANKA_VL_MAX_LENGTH 				= 12;	//原価単価の長さ
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	public static final int GENTANKA_SEN_VL_MAX_LENGTH 			= 2;	//原価単価(銭)の長さ
//	public static final int BUTURYU_KB_MAX_LENGTH 				= 1;	//物流区分の長さ
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->

	public static final int BAITANKA_VL_MAX_LENGTH 				= 10;	//売価単価(税込)の長さ
	public static final int HACHUTANI_IRISU_QT_MAX_LENGTH 		= 8;	//発注単位(入数)の長さ
	public static final int MAX_HACHUTANI_QT_MAX_LENGTH 			= 8;	//最大発注単位の長さ
	public static final int TEIKAN_KB_MAX_LENGTH 				= 1;	//定貫区分の長さ
	public static final int EOS_KB_MAX_LENGTH 					= 1;	//EOS区分の長さ
	public static final int NOHIN_KIGEN_QT_MAX_LENGTH 			= 5;	//納品期限の長さ
	public static final int NOHIN_KIGEN_KB_MAX_LENGTH 			= 1;	//納品期限区分の長さ
	public static final int MST_SIYOFUKA_KB_MAX_LENGTH           = 1;	//マスタ使用不可区分
	public static final int HACHU_TEISI_KB_MAX_LENGTH 			= 1;	//発注停止区分の長さ

//	<!--   ↓↓2006.07.14 wangjm カスタマイズ修正↓↓  -->
	public static final int SIIRESAKI_CD_MAX_LENGTH 				= 6;	//仕入先コードの長さ
	public static final int AREA_CD_MAX_LENGTH 					= 3;	//地区コードの長さ
//	<!--   ↑↑2006.07.14 wangjm カスタマイズ修正↑↑  -->
	public static final int DAIHYO_HAISO_CD_MAX_LENGTH 			= 4;	//代表配送先コードの長さ
	public static final int TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH 	= 4;	//店別仕入先管理コードの長さ
//	public static final int TEN_HAISO_KANRI_CD_MAX_LENGTH 		= 4;	//店別配送先管理コードの長さ(2005/05/26 削除)
	public static final int SOBA_SYOHIN_KB_MAX_LENGTH 			= 1;	//相場商品区分の長さ
	public static final int BIN_1_KB_MAX_LENGTH 					= 1;	//①便区分の長さ
	public static final int HACHU_PATTERN_1_KB_MAX_LENGTH 		= 1;	//①発注パターン区分の長さ
	public static final int SIME_TIME_1_QT_MAX_LENGTH 			= 2;	//①締め時間の長さ
	public static final int C_NOHIN_RTIME_1_KB_MAX_LENGTH 		= 1;	//①センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_1_KB_MAX_LENGTH 		= 1;	//①店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_1_KB_MAX_LENGTH 		= 1;	//①店納品時間帯の長さ
	public static final int BIN_2_KB_MAX_LENGTH 					= 1;	//②便区分の長さ
	public static final int HACHU_PATTERN_2_KB_MAX_LENGTH 		= 1;	//②発注パターン区分の長さ
	public static final int SIME_TIME_2_QT_MAX_LENGTH 			= 2;	//②締め時間の長さ
	public static final int C_NOHIN_RTIME_2_KB_MAX_LENGTH 		= 1;	//②センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_2_KB_MAX_LENGTH 		= 1;	//②店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_2_KB_MAX_LENGTH 		= 1;	//②店納品時間帯の長さ
	public static final int BIN_3_KB_MAX_LENGTH 					= 1;	//③便区分の長さ
	public static final int HACHU_PATTERN_3_KB_MAX_LENGTH 		= 1;	//③発注パターン区分の長さ
	public static final int SIME_TIME_3_QT_MAX_LENGTH 			= 2;	//③締め時間の長さ
	public static final int C_NOHIN_RTIME_3_KB_MAX_LENGTH 		= 1;	//③センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_3_KB_MAX_LENGTH 		= 1;	//③店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_3_KB_MAX_LENGTH 		= 1;	//③店納品時間帯の長さ
	public static final int C_NOHIN_RTIME_KB_MAX_LENGTH 			= 1;	//センタ納品リードタイムの長さ
	public static final int ZEI_KB_MAX_LENGTH                    = 1;	//税区分の長さ
	public static final int HANBAI_KIKAN_KB_MAX_LENGTH           = 1;	//販売期間区分の長さ
	public static final int SYOHIN_KB_MAX_LENGTH 				= 1;	//商品区分の長さ
	public static final int YOKOMOTI_KB_MAX_LENGTH 				= 1;	//横もち区分の長さ
	public static final int TEN_GROUPNO_CD_MAX_LENGTH 			= 2;	//店グルーピングNO(物流）の長さ
	public static final int TEN_ZAIKO_KB_MAX_LENGTH 				= 1;	//店在庫区分の長さ
	public static final int HANBAI_SEISAKU_KB_MAX_LENGTH 		= 1;	//販売政策区分の長さ
// ↓↓ DB変更に伴う対応(2005.09.18 Y.Inaba)
//	public static final int HENPIN_NB_MAX_LENGTH 				= 2;	//返品契約書NOの長さ
	public static final int HENPIN_NB_MAX_LENGTH 				= 11;	//返品契約書NOの長さ
// ↑↑ DB変更に伴う対応(2005.09.18 Y.Inaba)
	public static final int HENPIN_GENKA_VL_MAX_LENGTH 			= 11;	//返品原価の長さ
	public static final int REBATE_FG_MAX_LENGTH 				= 1;	//リベート対象フラグの長さ
	public static final int HANBAI_ST_DT_MAX_LENGTH 				= 8;	//販売開始日の長さ
	public static final int HANBAI_ED_DT_MAX_LENGTH 				= 8;	//販売終了日の長さ

	public static final int HANBAI_LIMIT_QT_MAX_LENGTH 			= 4;	//販売期限の長さ
	public static final int HANBAI_LIMIT_KB_MAX_LENGTH 			= 1;	//販売期限区分の長さ
	public static final int AUTO_DEL_KB_MAX_LENGTH 				= 1;	//自動削除対象区分の長さ
	public static final int GOT_MUJYOKEN_FG_MAX_LENGTH 			= 1;	//GOT無条件表示対象の長さ
	public static final int GOT_START_MM_MAX_LENGTH 				= 2;	//GOT表示開始月の長さ
	public static final int GOT_END_MM_MAX_LENGTH 				= 2;	//GOT表示終了月の長さ
	public static final int E_SHOP_KB_MAX_LENGTH 				= 1;	//Eショップ区分の長さ
	public static final int PLU_SEND_DT_MAX_LENGTH 				= 8;	//PLU送信日の長さ
	public static final int REC_HINMEI_KANJI_NA_MAX_LENGTH 		= 20;	//POSレシート品名(漢字)の長さ
	public static final int REC_HINMEI_KANA_NA_MAX_LENGTH 		= 20;	//POSレシート品名(カナ)の長さ
	public static final int KEYPLU_FG_MAX_LENGTH 				= 1;	//キーPLU対象の長さ
	public static final int PC_KB_MAX_LENGTH 					= 1;	//PC発行区分の長さ
	public static final int DAISI_NO_NB_MAX_LENGTH 				= 2;	//台紙NOの長さ
	public static final int UNIT_PRICE_TANI_KB_MAX_LENGTH 		= 4;	//ユニットプライス-単位区分の長さ
	public static final int UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH 	= 4;	//ユニットプライス-内容量の長さ
	public static final int UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH 	= 4;	//ユニットプライス-基準単量の長さ
	public static final int SHINAZOROE_KB_MAX_LENGTH 			= 1;	//品揃区分の長さ
	public static final int KUMISU_KB_MAX_LENGTH 				= 1;	//組数区分の長さ
	public static final int NEFUDA_KB_MAX_LENGTH 				= 1;	//値札区分の長さ
	public static final int YORIDORI_KB_MAX_LENGTH 				= 1;	//よりどり種類の長さ
	public static final int YORIDORI_QT_MAX_LENGTH 				= 1;	//よりどり数量の長さ
	public static final int TAG_HYOJI_BAIKA_VL_MAX_LENGTH 		= 9;	//タグ表示売価の長さ
	public static final int SEASON_CD_MAX_LENGTH 				= 3;	//シーズンコードの長さ
	public static final int HUKUSYU_CD_MAX_LENGTH 				= 3;	//服種コードの長さ
	public static final int STYLE_CD_MAX_LENGTH 					= 3;	//スタイルコードの長さ
	public static final int SCENE_CD_MAX_LENGTH 					= 3;	//シーンコードの長さ
	public static final int SEX_CD_MAX_LENGTH 					= 3;	//性別コードの長さ
	public static final int AGE_CD_MAX_LENGTH 					= 3;	//年代コードの長さ
	public static final int GENERATION_CD_MAX_LENGTH 			= 3;	//世代コードの長さ
	public static final int SOZAI_CD_MAX_LENGTH 					= 3;	//素材コードの長さ
	public static final int PATTERN_CD_MAX_LENGTH 				= 3;	//パターンコードの長さ
	public static final int ORIAMI_CD_MAX_LENGTH 				= 3;	//織り編みコードの長さ
	public static final int HUKA_KINO_CD_MAX_LENGTH 				= 3;	//付加機能コードの長さ
	public static final int SIZE_CD_MAX_LENGTH 					= 2;	//サイズコードの長さ
	public static final int COLOR_CD_MAX_LENGTH 					= 2;	//カラーコードの長さ
	public static final int SYUZEI_HOKOKU_KB_MAX_LENGTH 			= 5;	//酒税報告分類の長さ
	public static final int TC_JYOUHO_NA_MAX_LENGTH 				= 20;	//TC情報の長さ
	public static final int NOHIN_SYOHIN_CD_MAX_LENGTH 			= 13;	//納品商品コードの長さ
	public static final int ITF_CD_MAX_LENGTH 					= 15;	//ITFコードの長さ
	public static final int CASE_IRISU_QT_MAX_LENGTH 			= 7;	//ケース入り数の長さ
	public static final int NYUKA_SYOHIN_CD_MAX_LENGTH 			= 13;	//入荷時商品コードの長さ
	public static final int PACK_WIDTH_QT_MAX_LENGTH 			= 8;	//外箱サイズ幅の長さ
	public static final int PACK_HEIGTH_QT_MAX_LENGTH 			= 8;	//外箱サイズ高さの長さ
	public static final int PACK_DEPTH_QT_MAX_LENGTH 			= 8;	//外箱サイズ奥行きの長さ
	public static final int PACK_WEIGHT_QT_MAX_LENGTH 			= 8;	//外箱重量の長さ
	public static final int CENTER_ZAIKO_KB_MAX_LENGTH 			= 1;	//センター在庫区分の長さ
	public static final int ZAIKO_HACHU_SAKI_MAX_LENGTH 			= 6;	//在庫補充発注先の長さ
	public static final int ZAIKO_CENTER_CD_MAX_LENGTH 			= 6;	//在庫センターコードの長さ
	public static final int MIN_ZAIKOSU_QT_MAX_LENGTH 			= 8;	//最低在庫数(発注点)の長さ
	public static final int MAX_ZAIKOSU_QT_MAX_LENGTH 			= 8;	//最大在庫数の長さ
	public static final int CENTER_HACHUTANI_KB_MAX_LENGTH 		= 1;	//センター発注単位区分の長さ
	public static final int CENTER_HACHUTANI_QT_MAX_LENGTH 		= 8;	//センター発注単位数の長さ
	public static final int CENTER_IRISU_QT_MAX_LENGTH 			= 8;	//センター入り数の長さ
	public static final int CENTER_WEIGHT_QT_MAX_LENGTH 			= 8;	//センター重量の長さ
	public static final int TANA_NO_NB_MAX_LENGTH 				= 8;	//棚NOの長さ
	public static final int DAN_NO_NB_MAX_LENGTH 				= 6;	//段NOの長さ
	public static final int KEIYAKU_SU_QT_MAX_LENGTH 			= 8;	//契約数の長さ
	public static final int KEIYAKU_PATTERN_KB_MAX_LENGTH 		= 1;	//契約パターンの長さ
	public static final int KEIYAKU_ST_DT_MAX_LENGTH 			= 8;	//契約開始期間の長さ
	public static final int KEIYAKU_ED_DT_MAX_LENGTH 			= 8;	//契約終了期間の長さ
	public static final int KIJUN_ZAIKOSU_QT_MAX_LENGTH 			= 8;	//基準在庫日数の長さ
	public static final int D_ZOKUSEI_1_NA_MAX_LENGTH	 		= 3;	//大属性１の長さ
	public static final int S_ZOKUSEI_1_NA_MAX_LENGTH 			= 3;	//小属性１の長さ
	public static final int D_ZOKUSEI_2_NA_MAX_LENGTH 			= 3;	//大属性２の長さ
	public static final int S_ZOKUSEI_2_NA_MAX_LENGTH 			= 3;	//小属性２の長さ
	public static final int D_ZOKUSEI_3_NA_MAX_LENGTH 			= 3;	//大属性３の長さ
	public static final int S_ZOKUSEI_3_NA_MAX_LENGTH 			= 3;	//小属性３の長さ
	public static final int D_ZOKUSEI_4_NA_MAX_LENGTH 			= 3;	//大属性４の長さ
	public static final int S_ZOKUSEI_4_NA_MAX_LENGTH 			= 3;	//小属性４の長さ
	public static final int D_ZOKUSEI_5_NA_MAX_LENGTH 			= 3;	//大属性５の長さ
	public static final int S_ZOKUSEI_5_NA_MAX_LENGTH 			= 3;	//小属性５の長さ
	public static final int D_ZOKUSEI_6_NA_MAX_LENGTH 			= 3;	//大属性６の長さ
	public static final int S_ZOKUSEI_6_NA_MAX_LENGTH 			= 3;	//小属性６の長さ
	public static final int D_ZOKUSEI_7_NA_MAX_LENGTH 			= 3;	//大属性７の長さ
	public static final int S_ZOKUSEI_7_NA_MAX_LENGTH 			= 3;	//小属性７の長さ
	public static final int D_ZOKUSEI_8_NA_MAX_LENGTH 			= 3;	//大属性８の長さ
	public static final int S_ZOKUSEI_8_NA_MAX_LENGTH 			= 3;	//小属性８の長さ
	public static final int D_ZOKUSEI_9_NA_MAX_LENGTH 			= 3;	//大属性９の長さ
	public static final int S_ZOKUSEI_9_NA_MAX_LENGTH 			= 3;	//小属性９の長さ
	public static final int D_ZOKUSEI_10_NA_MAX_LENGTH 			= 3;	//大属性１０の長さ
	public static final int S_ZOKUSEI_10_NA_MAX_LENGTH 			= 3;	//小属性１０の長さ
	public static final int INSERT_TS_MAX_LENGTH 				= 20;	//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 			= 10;	//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH 				= 20;	//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH 			= 10;	//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH 				= 1;	//削除フラグの長さ

//	2006.08.26 By kema 画面に無いカラムを引継ぐため
	private String gtin_cd          		 = "";			//GTINコード
	private String center_kyoyo_dt          = "";		  	//センター許容日
	private String syohi_kigen_dt           = "";		  	//消費期限
	private String hacyu_syohin_cd          ="";			//発注商品コード
	private String plu_kb                	 ="";			//PLU区分
	private String pre_gentanka_vl          = "";		    // 前回原価単価
	private String pre_baitanka_vl          = "";		    // 前回売価単価
//	↓↓仕様変更（2005.07.28）↓↓
	public static final int SYOKAI_TOROKU_TS_MAX_LENGTH = 20;//初回登録日の長さ
	public static final int SYOKAI_USER_ID_MAX_LENGTH = 6;//初回登録者IDの長さ
//	↑↑仕様変更（2005.07.28）↑↑

	private String zokusei                   = "";              //属性
	private String pregamengentankavl           	 = "";			 //更新前原価単価
	private String pregamenbaitankavl            	 = "";			 //更新前売価単価
    private String syohin_cd                 = "";              //商品コード(検索)
    private String hinmei_kanji          = "";              //商品名(検索)
	private String bumon_cd 				 = "";				//部門コード(検索)
	private String bumon_kanji_rn 			 = "";				//部門名(検索)
	private String tempbumon_cd              = "";              //部門コード(更新用)
//	<!--   ↓↓2006.07.10 wangjm カスタマイズ追加↓↓  -->
	private String unit_cd              	     = "";              //ユニットコード
	private String unit_na                      = "";           //ユニット名
	private String haifu_cd               		 = "";              //配布コード
	private String subclass_cd                  = "";              //サブクラスコード
	private String subclass_na                  = "";              //サブクラス名
	private String user_id                		 = "";              //バイヤーNo
     private String maker_cd                    = "";              //メーカーコード
     private String maker_na                    = "";              //メーカー名
     private String hinmei_kanji_na      	     = "";                  //漢字品名
     private String kikaku_kanji_na       		 = "";                  //漢字規格
     private String hinmei_kana_na              = "";              //カナ品名
     private String kikaku_kana_na              = "";              //カナ規格
     private String rec_hinmei_kanji_na  		 = "";                  //POSレシート品名(漢字)
     private String rec_hinmei_kana_na          = "";              //POSレシート品名(カナ)
	private String kikaku_kanji_na2             = "";              //漢字規格２
	private String kikaku_kana_na2              = "";              //カナ規格２
	private String gentanka_vl        		     = "";                  //原価単価
	private String baitanka_vl          	     = "";                  //売価単価(税込)
	private String plu_send_dt          	     = "";                  //PLU送信日
	private String save_plu_send_dt       	     = "";                  //PLU送信日
	private String siire_hinban_cd      		 = "";                  //仕入先品番
	private String maker_kibo_kakaku_vl 		 = "";				//メーカー希望小売り価格
	private String area_cd              	     = "";              //地区コード
	private String areacd_na                    = "";              //地区名
	private String siiresaki_cd 				 = "";				//仕入先コード
	private String siiresaki_na 				 = "";				//仕入先名
	private String ten_siiresaki_kanri_cd       = "";              //店別仕入先管理コード
	private String ten_siiresaki_kanri_na       = "";              //店別仕入先管理名
	private String hanbai_st_dt                 = "";              //販売開始日
	private String hanbai_ed_dt                 = "";              //販売終了日
	private String ten_hachu_st_dt              = "";              //発注開始日
	private String ten_hachu_ed_dt              = "";              //発注終了日
	private String eos_kb                       = "";              //EOS区分
    private String bland_cd                     = "";              //ブランドコード
    private String bland_na                     = "";              //ブランド名
	private String honbuzai_kb                  = "";              //本部在庫区分
	private String fujisyohin_kb                = "";              //商品区分
	private String pb_kb              		     = "";              //PB区分
	private String hachutani_irisu_qt       	 = "";              //発注単位(入数)
	private String hachutani_na                 = "";              //発注単位呼称
	private String max_hachutani_qt             = "";              //最大発注単位
	private String binkuiti_ct                  = "";              //便区分1
	private String binkuni_ct              	 = "";              //便区分2
	private String yusenbinkb_ct                = "";              //優先便区分
	private String unit_price_tani_kb           = "";              //ユニットプライス単位
	private String unit_price_naiyoryo_qt       = "";              //ユニットプライス内容量
	private String unit_price_kijun_tani_qt     = "";              //ユニットプライス基準単位量
	private String syohikigen                   = "";              //消費期限
	private String pc_kb                        = "";              //プライスシール発行区分
	private String daichotenpo                  = "";              //商品台帳（店舗）
	private String daichohonbu                  = "";              //商品台帳（本部）
	private String daichosiiresaki              = "";              //商品台帳（仕入先）
	private String daisino_kb                   = "";              //プライスシール種類
//	↓↓2006.07.10 wangjm カスタマイズ追加↓↓


//  ↑↑2006.07.10 wangjm カスタマイズ追加↑↑

	private String processingDivision		 = mst000101_ConstDictionary.PROCESS_REFERENCE;//処理区分
	private String errorFlg				 = "";				//エラーフラグ
	private String errorMessage			 = "";				//エラーメッセージ
	private String mode					 = "";				//処理モード
	private String[] menuBar			 	 = null;			//メニューバーアイテム
	private Map ctrlColor				 	 = new HashMap();	//コントロール背景色
	private String firstFocus				 = "";				//フォーカスを最初に取得するオブジェクト名
	private String insertFlg				 = "";				//新規処理利用可能区分
	private String updateFlg				 = "";				//更新処理利用可能区分
	private String deleteFlg				 = "";				//削除処理利用可能区分
	private String referenceFlg			 = "";				//照会処理利用可能区分
	private String CsvFlg					 = "";				// CSV処理利用可能区分
	private String PrintFlg				 = "";				// 印刷処理利用可能区分
	private String updateProcessFlg 		 = "";				//更新処理内容
	private String CheckFlg			     = "";				// チェック処理判断
	private String ExistFlg			     = "";		  		// データ存在(検索[ｷｬﾝｾﾙ]時)
	private String ErrorBackDisp			 = "";				// エラー時戻り画面

	private String yuko_dt 				 = "";				//有効日

	private String genyuko_dt 				 = "";				//現在有効日
	private String tenreigai			 	 = "";				//店舗例外登録
//	2006.01.22 T.hattori　追加　開始　先付け登録項目追加
	private String sakiduketouroku			 = "";				//先付け登録
//	2006.01.22 T.hattori　追加　終了　先付け登録項目追加
	private String hacyu_syohin_kb          = "";				//発注商品区分
	private String system_kb                = "";				//システム区分
	private String gyosyu_kb 				 = "";				//業種区分
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	private String hanku_cd 				 = "";				//販区コード
//	private String hanku_kanji_rn 			 = "";				//販区名
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->

	private String tanpin_cd 			 	 = "";				//単品
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	private String hinsyu_cd 				 = "";				//品種コード
//	private String hinsyu_na 				 = "";				//品種名
//	private String hinmoku_na				 = "";				//品目名
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->

	private String hinban_cd 				 = "";				//品番
	private String mark_group_cd 			 = "";				//マークグループ
	private String mark_cd 				 = "";				//マークコード
	private String syohin_2_cd 			 = "";				//商品コード２
	private String ketasu_kb 				 = "";				//桁数区分
//	private String maker_cd 				 = "";				//メーカーコード
//	private String maker_na                 = "";				//メーカー名
	private String syohin_width_qt 		 = "";				//商品サイズ(幅)
	private String syohin_height_qt 		 = "";				//商品サイズ(高さ)
	private String syohin_depth_qt 		 = "";				//商品サイズ(奥行き)
//	private String siire_hinban_cd 		 = "";				//仕入先品番
//	private String bland_cd 				 = "";				//ブランドコード
//	private String bland_na 				 = "";				//ブランド名
	private String yunyuhin_kb 			 = "";				//輸入品区分

//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	private String gentanka_sen_vl 		 = "";				//原価単価(銭)
//	private String santi_cd 				 = "";				//原産国/産地コード
//	private String santi_na		 		 = "";				//原産国/産地名
//	private String nohin_ondo_kb 			 = "";				//納品温度帯
//	private String comment_tx 				 = "";				//コメント
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
	private String teikan_kb 				 = "";				//定貫区分
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	private String nohin_kigen_qt 			 = "";				//納品期限
//	private String nohin_kigen_kb 			 = "";				//納品期限区分
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
	private String mst_siyofuka_kb          = "";				//マスタ使用不可区分
	private String hachu_teisi_kb 			 = "";				//発注停止区分

	private String daihyo_haiso_cd 		 = "";				//代表配送先コード
	private String daihyo_haiso_na          = "";				//代表配送先名
//	private String ten_siiresaki_kanri_cd 	 = "";				//店別仕入先管理コード
//	private String ten_siiresaki_kanri_na 	 = "";				//店別仕入先管理名
//	private String ten_haiso_kanri_cd 		 = "";				//店別配送先管理コード
//	private String ten_haiso_kanri_na 		 = "";				//店別配送先管理コード
	private String soba_syohin_kb 			 = "";				//相場商品区分
	private String bin_1_kb 				 = "";				//①便区分
	private String hachu_pattern_1_kb 		 = "";				//①発注パターン区分
	private String sime_time_1_qt 			 = "";				//①締め時間
	private String c_nohin_rtime_1_kb 		 = "";				//①センタ納品リードタイム
	private String ten_nohin_rtime_1_kb 	 = "";				//①店納品リードタイム
	private String ten_nohin_time_1_kb 	 = "";				//①店納品時間帯
	private String bin_2_kb 				 = "";				//②便区分
	private String hachu_pattern_2_kb 		 = "";				//②発注パターン区分
	private String sime_time_2_qt 			 = "";				//②締め時間
	private String c_nohin_rtime_2_kb 		 = "";				//②センタ納品リードタイム
	private String ten_nohin_rtime_2_kb 	 = "";				//②店納品リードタイム
	private String ten_nohin_time_2_kb 	 = "";				//②店納品時間帯
	private String bin_3_kb 				 = "";				//③便区分
	private String hachu_pattern_3_kb 		 = "";				//③発注パターン区分
	private String sime_time_3_qt 			 = "";				//③締め時間
	private String c_nohin_rtime_3_kb 		 = "";				//③センタ納品リードタイム
	private String ten_nohin_rtime_3_kb 	 = "";				//③店納品リードタイム
	private String ten_nohin_time_3_kb 	 = "";				//③店納品時間帯
	private String c_nohin_rtime_kb 		 = "";				//センタ納品リードタイム
	private String zei_kb                  = "";				//税区分
	private String hanbai_kikan_kb          = "";				//販売期間区分
	private String syohin_kb 				 = "";				//商品区分

//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	private String buturyu_kb 				 = "";				//物流区分
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
	private String yokomoti_kb 			 = "";				//横もち区分
	private String ten_groupno_cd 			 = "";				//店グルーピングNO(物流）
	private String ten_zaiko_kb 			 = "";				//店在庫区分
	private String hanbai_seisaku_kb 		 = "";				//販売政策区分
	private String henpin_nb 				 = "";				//返品契約書NO
	private String henpin_genka_vl 		 = "";				//返品原価
	private String rebate_fg 				 = "";				//リベート対象フラグ


	private String hanbai_limit_qt 		 = "";				//販売期限
	private String hanbai_limit_kb 		 = "";				//販売期限区分
	private String auto_del_kb 			 = "";				//自動削除対象区分
	private String got_mujyoken_fg 		 = "";				//GOT無条件表示対象
	private String got_start_mm 			 = "";				//GOT表示開始月
	private String got_end_mm 				 = "";				//GOT表示終了月
	private String e_shop_kb 				 = "";				//Eショップ区分

	private String keyplu_fg 				 = "";				//キーPLU対象
	private String daisi_no_nb 			 = "";				//台紙NO
	private String shinazoroe_kb 			 = "";				//品揃区分
	private String kumisu_kb 				 = "";				//組数区分
	private String nefuda_kb 				 = "";				//値札区分
	private String yoridori_kb 			 = "";				//よりどり種類
	private String yoridori_qt 			 = "";				//よりどり数量
	private String tag_hyoji_baika_vl		 = "";				//タグ表示売価
	private String season_cd 				 = "";				//シーズンコード
	private String hukusyu_cd 				 = "";				//服種コード
	private String style_cd 				 = "";				//スタイルコード
	private String scene_cd 				 = "";				//シーンコード
	private String sex_cd 					 = "";				//性別コード
	private String age_cd 					 = "";				//年代コード
	private String generation_cd 			 = "";				//世代コード
	private String sozai_cd 				 = "";				//素材コード
	private String pattern_cd 				 = "";				//パターンコード
	private String oriami_cd 				 = "";				//織り編みコード
	private String huka_kino_cd 			 = "";				//付加機能コード
	private String size_cd 				 = "";				//サイズコード
	private String color_cd 				 = "";				//カラーコード
	private String syuzei_hokoku_kb 		 = "";				//酒税報告分類
	private String tc_jyouho_na 			 = "";				//TC情報
	private String nohin_syohin_cd 		 = "";				//納品商品コード
	private String itf_cd 					 = "";				//ITFコード
	private String case_irisu_qt 			 = "";				//ケース入り数
	private String nyuka_syohin_cd 		 = "";				//入荷時商品コード
	private String pack_width_qt 			 = "";				//外箱サイズ幅
	private String pack_heigth_qt 			 = "";				//外箱サイズ高さ
	private String pack_depth_qt 			 = "";				//外箱サイズ奥行き
	private String pack_weight_qt 			 = "";				//外箱重量
	private String center_zaiko_kb 		 = "";				//センター在庫区分
	private String zaiko_hachu_saki 		 = "";				//在庫補充発注先
	private String zaiko_center_cd 		 = "";				//在庫センターコード
	private String zaiko_center_na 		 = "";				//在庫センター名
	private String min_zaikosu_qt 			 = "";				//最低在庫数(発注点)
	private String max_zaikosu_qt 			 = "";				//最大在庫数
	private String center_hachutani_kb 	 = "";				//センター発注単位区分
	private String center_hachutani_qt 	 = "";				//センター発注単位数
	private String center_irisu_qt 		 = "";				//センター入り数
	private String center_weight_qt 		 = "";				//センター重量
	private String tana_no_nb 				 = "";				//棚NO
	private String dan_no_nb 				 = "";				//段NO
	private String keiyaku_su_qt 			 = "";				//契約数
	private String keiyaku_pattern_kb 		 = "";				//契約パターン
	private String keiyaku_st_dt 			 = "";				//契約開始期間
	private String keiyaku_ed_dt 			 = "";				//契約終了期間
	private String kijun_zaikosu_qt 		 = "";				//基準在庫日数
	private String d_zokusei_1_na 			 = "";				//大属性１
	private String s_zokusei_1_na 			 = "";				//小属性１
	private String d_zokusei_2_na 			 = "";				//大属性２
	private String s_zokusei_2_na 			 = "";				//小属性２
	private String d_zokusei_3_na 			 = "";				//大属性３
	private String s_zokusei_3_na 			 = "";				//小属性３
	private String d_zokusei_4_na 			 = "";				//大属性４
	private String s_zokusei_4_na 			 = "";				//小属性４
	private String d_zokusei_5_na 			 = "";				//大属性５
	private String s_zokusei_5_na 			 = "";				//小属性５
	private String d_zokusei_6_na 			 = "";				//大属性６
	private String s_zokusei_6_na 			 = "";				//小属性６
	private String d_zokusei_7_na 			 = "";				//大属性７
	private String s_zokusei_7_na 			 = "";				//小属性７
	private String d_zokusei_8_na 			 = "";				//大属性８
	private String s_zokusei_8_na 			 = "";				//小属性８
	private String d_zokusei_9_na 			 = "";				//大属性９
	private String s_zokusei_9_na 			 = "";				//小属性９
	private String d_zokusei_10_na 		 = "";				//大属性１０
	private String s_zokusei_10_na 		 = "";				//小属性１０
	private String insert_ts 				 = "";				//作成年月日
	private String insert_user_id 			 = "";				//作成者社員ID
	private String update_ts 				 = "";				//更新年月日
	private String update_user_id 			 = "";				//更新者社員ID
	private String delete_fg 				 = "";				//削除フラグ

//	↓↓仕様変更（2005.07.28）↓↓
	private String syokai_toroku_ts = null;	//初回登録日
	private String syokai_user_id = null;	//初回登録者ID
	private String sinki_toroku_dt = null;//新規登録日
//	↑↑仕様変更（2005.07.28）↑↑

	private String Hairetu_cd1 			 = "";				//配列１
	private String Hairetu_nm1 			 = "";				//配列１名
	private String Hairetu_cd2 			 = "";				//配列２
	private String Hairetu_nm2 			 = "";				//配列２名
	private String CenterPattern1Kb		 = "";				//センターパターン１
	private String CenterPattern2Kb		 = "";				//センターパターン２
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	private String KeiryoukiNo				 = "";				//計量器呼出No
	private String save_ten_hachu_st_dt 	 = "";				//DB店舗発注開始日
	private String save_ten_hachu_ed_dt 	 = "";				//DB店舗発注終了日
	private String save_hanbai_st_dt 		 = "";				//DB販売開始日
	private String save_hanbai_ed_dt 		 = "";				//DB販売終了日
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->

	private String ChangeFlg 		     = "";				//データ変更
  	private static final String INIT_PAGE   = "mst150101_SyohinSeisenInit";	//初期画面JSPを取得
  	private static final String EDIT_PAGE   = "mst150201_SyohinSeisenEdit";	//新規・修正画面JSPを取得
  	private static final String VIEW_PAGE   = "mst150301_SyohinSeisenView";	//削除・照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得




//	↓↓2006.07.10 wangjm カスタマイズ追加↓↓
//  binkuiti_ctに対するセッターとゲッターの集合
	public String getBinkuiti_ct() {
		return this.binkuiti_ct;
	}

	public String getBinkuiti_ctHTMLString() {
		return HTMLStringUtil.convert(this.binkuiti_ct);
	}

	public boolean setBinkuiti_ct(String binkuiti_ct) {
		this.binkuiti_ct = binkuiti_ct;
		return true;
	}

//  binkuni_ctに対するセッターとゲッターの集合
	public String getBinkuni_ct() {
		return this.binkuni_ct;
	}

	public String getBinkuni_ctHTMLString() {
		return HTMLStringUtil.convert(this.binkuni_ct);
	}

	public boolean setBinkuni_ct(String binkuni_ct) {
		this.binkuni_ct = binkuni_ct;
		return true;
	}
//  yusenbinkb_ctに対するセッターとゲッターの集合
	public String getYusenbinkb_ct() {
		return this.yusenbinkb_ct;
	}

	public String getYusenbinkb_ctHTMLString() {
		return HTMLStringUtil.convert(this.yusenbinkb_ct);
	}

	public boolean setYusenbinkb_ct(String yusenbinkb_ct) {
		this.yusenbinkb_ct = yusenbinkb_ct;
		return true;
	}
    //zokuseiに対するセッターとゲッターの集合
    public boolean setZokusei(String zokusei)
    {
        this.zokusei = zokusei;
        return true;
    }
    public String getZokusei()
    {
        return this.zokusei;
    }
    //tempbumon_cdに対するセッターとゲッターの集合
    public boolean setTempBuMonCd(String tempbumon_cd)
    {
        this.tempbumon_cd = tempbumon_cd;
        return true;
    }
    public String getTempBuMonCd()
    {
        return this.tempbumon_cd;
    }
	// bumon_cdに対するセッターとゲッターの集合
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	public String getBumonCd()
	{
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
//  haifu_cdに対するセッターとゲッターの集合
    public boolean setHaiFuCd(String haifu_cd)
    {
        this.haifu_cd = haifu_cd;
        return true;
    }
    public String getHaiFu()
    {
        return cutString(this.haifu_cd,HAIFU_CD_MAX_LENGTH);
    }
    public String getHaiFuString()
    {
        return "'" + cutString(this.haifu_cd,HAIFU_CD_MAX_LENGTH) + "'";
    }
    public String getHaiFuHTMLString()
    {
        return HTMLStringUtil.convert(cutString(this.haifu_cd,HAIFU_CD_MAX_LENGTH));
    }
//   unit_cdに対するセッターとゲッターの集合
    public boolean setUnitCd(String unit_cd)
    {
        this.unit_cd = unit_cd;
        return true;
    }
    public String getUnit()
    {
        return cutString(this.unit_cd,UNIT_CD_MAX_LENGTH);
    }
    public String getUnitString()
    {
        return "'" + cutString(this.unit_cd,UNIT_CD_MAX_LENGTH) + "'";
    }
    public String getUnitHTMLString()
    {
        return HTMLStringUtil.convert(cutString(this.unit_cd,UNIT_CD_MAX_LENGTH));
    }
//   unit_naに対するセッターとゲッターの集合
    public boolean setUnit_Na(String unit_na)
    {
        this.unit_na = unit_na;
        return true;
    }
    public String getUnit_Na()
    {
        return this.unit_na;
    }
//   subclass_cdに対するセッターとゲッターの集合
    public boolean setSubclassCd(String subclass_cd)
    {
        this.subclass_cd = subclass_cd;
        return true;
    }
    public String getSubClass()
    {
        return cutString(this.subclass_cd,SUBCLASS_CD_MAX_LENGTH);
    }
    public String getSubClassString()
    {
        return "'" + cutString(this.subclass_cd,SUBCLASS_CD_MAX_LENGTH) + "'";
    }
    public String getSubClassHTMLString()
    {
        return HTMLStringUtil.convert(cutString(this.subclass_cd,SUBCLASS_CD_MAX_LENGTH));
    }
//  subclass_naに対するセッターとゲッターの集合
    public boolean setSubclass_Na(String subclass_na)
    {
        this.subclass_na = subclass_na;
        return true;
    }
    public String getSubclass_Na()
    {
        return this.subclass_na;
    }
//  user_idに対するセッターとゲッターの集合
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
//  kikaku_kanji_na2に対するセッターとゲッターの集合
    public boolean setKikakuKanji2Na(String kikaku_kanji_na2)
    {
        this.kikaku_kanji_na2 = kikaku_kanji_na2;
        return true;
    }
    public String getKikaku_Kanji_Na2()
    {
        return cutString(this.kikaku_kanji_na2,KIKAKU_KANJI_NA2_MAX_LENGTH);
    }
    public String getKikaku_Kanji_Na2String()
    {
        return "'" + cutString(this.kikaku_kanji_na2,KIKAKU_KANJI_NA2_MAX_LENGTH) + "'";
    }
    public String getKikaku_Kanji_Na2HTMLString()
    {
        return HTMLStringUtil.convert(cutString(this.kikaku_kanji_na2,KIKAKU_KANJI_NA2_MAX_LENGTH));
    }
//  syohikigenに対するセッターとゲッターの集合
    public boolean setSyohikigen(String syohikigen)
    {
        this.syohikigen = syohikigen;
        return true;
    }
    public String getSyohikigen()
    {
        return cutString(this.syohikigen,SYOHIKIGEN_MAX_LENGTH);
    }
    public String getSyohikigentring()
    {
        return "'" + cutString(this.syohikigen,SYOHIKIGEN_MAX_LENGTH) + "'";
    }
    public String getSyohikigenHTMLString()
    {
        return HTMLStringUtil.convert(cutString(this.syohikigen,SYOHIKIGEN_MAX_LENGTH));
    }
//   kikaku_kana_na2に対するセッターとゲッターの集合
    public boolean setKikakuKana2Na(String kikaku_kana_na2)
    {
        this.kikaku_kana_na2 = kikaku_kana_na2;
        return true;
    }
    public String getKikaku_Kana_Na2()
    {
        return this.kikaku_kana_na2;
    }

  //area_cdに対するセッターとゲッターの集合
  	public boolean setAreaCd(String area_cd)
  	{
      this.area_cd = area_cd;
      return true;
  	}
  	public String getArea_Cd()
  	{
      return cutString(this.area_cd,AREA_CD_MAX_LENGTH);
  	}
  	public String getArea_CdString()
  	{
      return "'" + cutString(this.area_cd,AREA_CD_MAX_LENGTH) + "'";
  	}
  	public String getArea_CdHTMLString()
  	{
      return HTMLStringUtil.convert(cutString(this.area_cd,AREA_CD_MAX_LENGTH));
  	}


//  areacd_naに対するセッターとゲッターの集合
    public boolean setAreacd_Na(String areacd_na)
    {
        this.areacd_na = areacd_na;
        return true;
    }
    public String getAreacd_Na()
    {
        return this.areacd_na;
    }
//	 bumon_kanji_rnに対するセッターとゲッターの集合
	public boolean setBumonKanjiRn(String bumon_kanji_rn)
	{
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}
	public String getBumonKanjiRn()
	{
		return this.bumon_kanji_rn;
	}

//  honbuzai_kbに対するセッターとゲッターの集合
    public boolean setHonbuzaiKb(String honbuzai_kb)
    {
        this.honbuzai_kb = honbuzai_kb;
        return true;
    }
    public String getHonbuzai_Kb()
    {
        return this.honbuzai_kb;
    }
//  fujisyohin_kbに対するセッターとゲッターの集合
    public boolean setFujisyohinKb(String fujisyohin_kb)
    {
        this.fujisyohin_kb = fujisyohin_kb;
        return true;
    }
    public String getFujisyohin_Kb()
    {
        return this.fujisyohin_kb;
    }
//   pb_kbに対するセッターとゲッターの集合
    public boolean setPbKb(String pb_kb)
    {
        this.pb_kb = pb_kb;
        return true;
    }
    public String  getPb_Kb()
    {
        return this.pb_kb;
    }
//  hachutani_naに対するセッターとゲッターの集合
    public boolean setHachuTaniNa(String hachutani_na)
    {
        this.hachutani_na = hachutani_na;
        return true;
    }
    public String getHachutani_Na()
    {
        return this.hachutani_na;
    }
//  daichotenpoに対するセッターとゲッターの集合
    public boolean setDaichotenpoKb(String daichotenpo)
    {
        this.daichotenpo = daichotenpo;
        return true;
    }

    public String getDaichotenpoKb()
    {
        return this.daichotenpo;
    }
//   daichohonbuに対するセッターとゲッターの集合
    public boolean setDaichohonbuKb(String daichohonbu)
    {
        this.daichohonbu = daichohonbu;
        return true;
    }
    public String getDaichohonbuKb()
    {
        return this.daichotenpo;
    }
//  daichosiiresakiに対するセッターとゲッターの集合
    public boolean setDaichosiiresakiKb(String daichosiiresaki)
    {
        this.daichosiiresaki = daichosiiresaki;
        return true;
    }
    public String getDaichosiiresakiKb()
    {
        return this.daichosiiresaki;
    }
    // daisino_kbに対するセッターとゲッターの集合
    public boolean setDaisiNokb(String daisino_kb)
    {
        this.daisino_kb = daisino_kb;
        return true;
    }
    public String getDaisiNokb()
    {
        return cutString(this.daisino_kb,DAISI_NO_NB_MAX_LENGTH);
    }
    public String getDaisiNokbString()
    {
        return "'" + cutString(this.daisino_kb,DAISI_NO_NB_MAX_LENGTH) + "'";
    }
    public String getDaisiNokbHTMLString()
    {
        return HTMLStringUtil.convert(cutString(this.daisino_kb,DAISI_NO_NB_MAX_LENGTH));
    }
//	 ↑↑2006.07.10 wangjm カスタマイズ追加↑↑


//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	// hanku_cdに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
//	}
//	public String getHankuCdString()
//	{
//		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHankuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->


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

	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
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

	private String nullToEmpty(String s) {
		return s == null ? "" : s;
	}
	// マスタ日付
	private String yukoStartDt;
	public String getYukoStartDt() {
		return nullToEmpty(yukoStartDt);
	}
	public void setYukoStartDt(String yukoStartDt) {
		this.yukoStartDt = yukoStartDt;
	}
	// マスタ日付の一年後
	private String yukoEndDt;
	public String getYukoEndDt() {
		return nullToEmpty(yukoEndDt);
	}
	public void setYukoEndDt(String yukoEndDt) {
		this.yukoEndDt = yukoEndDt;
	}

	// hacyu_syohin_kbに対するセッターとゲッターの集合
	public boolean setHacyuSyohinKb(String hacyu_syohin_kb)
	{
		this.hacyu_syohin_kb = hacyu_syohin_kb;
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


//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	// hinsyu_cdに対するセッターとゲッターの集合
//	public boolean setHinsyuCd(String hinsyu_cd)
//	{
//		this.hinsyu_cd = hinsyu_cd;
//		return true;
//	}
//	public String getHinsyuCd()
//	{
//		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
//	}
//	public String getHinsyuCdString()
//	{
//		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHinsyuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->

	// tanpin_cdに対するセッターとゲッターの集合
	public boolean setTanpinCd(String tanpin_cd)
	{
		this.tanpin_cd = tanpin_cd;
		return true;
	}
	public String getTanpinCd()
	{
		return cutString(this.tanpin_cd,TANPIN_CD_MAX_LENGTH);
	}
	public String getTanpinCdString()
	{
		return "'" + cutString(this.tanpin_cd,TANPIN_CD_MAX_LENGTH) + "'";
	}
	public String getTanpinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_cd,TANPIN_CD_MAX_LENGTH));
	}
	// hinban_cdに対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinban_cd)
	{
		this.hinban_cd = hinban_cd;
		return true;
	}
	public String getHinbanCd()
	{
		return this.hinban_cd;
	}
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	//hinmoku_naに対するセッターとゲッターの集合
//	public boolean  setHinmokuNa(String hinmoku_na)
//	{
//		this.hinmoku_na = hinmoku_na;
//		return true;
//	}
//	public String getHinmokuNa()
//	{
//		return this.hinmoku_na;
//	}

//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->


	// mark_group_cdに対するセッターとゲッターの集合
	public boolean setMarkGroupCd(String mark_group_cd)
	{
		this.mark_group_cd = mark_group_cd;
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

	//maker_naに対するセッターとゲッターの集合
//	public boolean  setMakerNa(String maker_na)
//	{
//		this.maker_na = maker_na;
//		return true;
//	}
//	public String getMakerNa()
//	{
//		return this.maker_na;
//	}
//  maker_naに対するセッターとゲッターの集合
    public boolean setMaker_Na(String maker_na)
    {
        this.maker_na = maker_na;
        return true;
    }
    public String getMaker_Na()
    {
        return this.maker_na;
    }
	// hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
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
		this.syohin_width_qt = syohin_width_qt;
		return true;
	}
	public String getSyohinWidthQt()
	{
		return cutString(this.syohin_width_qt,SYOHIN_WIDTH_QT_MAX_LENGTH);
	}
	public String getSyohinWidthQtString()
	{
		return "'" + cutString(this.syohin_width_qt,SYOHIN_WIDTH_QT_MAX_LENGTH) + "'";
	}
	public String getSyohinWidthQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_width_qt,SYOHIN_WIDTH_QT_MAX_LENGTH));
	}

	// syohin_height_qtに対するセッターとゲッターの集合
	public boolean setSyohinHeightQt(String syohin_height_qt)
	{
		this.syohin_height_qt = syohin_height_qt;
		return true;
	}
	public String getSyohinHeightQt()
	{
		return cutString(this.syohin_height_qt,SYOHIN_HEIGHT_QT_MAX_LENGTH);
	}
	public String getSyohinHeightQtString()
	{
		return "'" + cutString(this.syohin_height_qt,SYOHIN_HEIGHT_QT_MAX_LENGTH) + "'";
	}
	public String getSyohinHeightQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_height_qt,SYOHIN_HEIGHT_QT_MAX_LENGTH));
	}

	// syohin_depth_qtに対するセッターとゲッターの集合
	public boolean setSyohinDepthQt(String syohin_depth_qt)
	{
		this.syohin_depth_qt = syohin_depth_qt;
		return true;
	}
	public String getSyohinDepthQt()
	{
		return cutString(this.syohin_depth_qt,SYOHIN_DEPTH_QT_MAX_LENGTH);
	}
	public String getSyohinDepthQtString()
	{
		return "'" + cutString(this.syohin_depth_qt,SYOHIN_DEPTH_QT_MAX_LENGTH) + "'";
	}
	public String getSyohinDepthQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_depth_qt,SYOHIN_DEPTH_QT_MAX_LENGTH));
	}

	// siire_hinban_cdに対するセッターとゲッターの集合
	public boolean setSiireHinbanCd(String siire_hinban_cd)
	{
		this.siire_hinban_cd = siire_hinban_cd;
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

//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	// santi_cdに対するセッターとゲッターの集合
//	public boolean setSantiCd(String santi_cd)
//	{
//		this.santi_cd = santi_cd;
//		return true;
//	}
//	public String getSantiCd()
//	{
//		return cutString(this.santi_cd,SANTI_CD_MAX_LENGTH);
//	}
//	public String getSantiCdString()
//	{
//		return "'" + cutString(this.santi_cd,SANTI_CD_MAX_LENGTH) + "'";
//	}
//	public String getSantiCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.santi_cd,SANTI_CD_MAX_LENGTH));
//	}
//
//	//santi_naに対するセッターとゲッターの集合
//	public boolean  setSantiNa(String santi_na)
//	{
//		this.santi_na = santi_na;
//		return true;
//	}
//	public String getSantiNa()
//	{
//		return this.santi_na;
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->



	// maker_kibo_kakaku_vlに対するセッターとゲッターの集合
	public boolean setMakerKiboKakakuVl(String maker_kibo_kakaku_vl)
	{
		this.maker_kibo_kakaku_vl = maker_kibo_kakaku_vl;
		return true;
	}
	public String getMakerKiboKakakuVl()
	{
		return cutString(this.maker_kibo_kakaku_vl,MAKER_KIBO_KAKAKU_VL_MAX_LENGTH);
	}
	public String getMakerKiboKakakuVlString()
	{
		return "'" + cutString(this.maker_kibo_kakaku_vl,MAKER_KIBO_KAKAKU_VL_MAX_LENGTH) + "'";
	}
	public String getMakerKiboKakakuVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.maker_kibo_kakaku_vl,MAKER_KIBO_KAKAKU_VL_MAX_LENGTH));
	}
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	 nohin_ondo_kbに対するセッターとゲッターの集合
//	public boolean setNohinOndoKb(String nohin_ondo_kb)
//	{
//		this.nohin_ondo_kb = nohin_ondo_kb;
//		return true;
//	}
//	public String getNohinOndoKb()
//	{
//		return cutString(this.nohin_ondo_kb,NOHIN_ONDO_KB_MAX_LENGTH);
//	}
//	public String getNohinOndoKbString()
//	{
//		return "'" + cutString(this.nohin_ondo_kb,NOHIN_ONDO_KB_MAX_LENGTH) + "'";
//	}
//	public String getNohinOndoKbHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.nohin_ondo_kb,NOHIN_ONDO_KB_MAX_LENGTH));
//	}
//
//	// comment_txに対するセッターとゲッターの集合
//	public boolean setCommentTx(String comment_tx)
//	{
//		this.comment_tx = comment_tx;
//		return true;
//	}
//	public String getCommentTx()
//	{
//		return cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH);
//	}
//	public String getCommentTxString()
//	{
//		return "'" + cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH) + "'";
//	}
//	public String getCommentTxHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH));
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
//

	// ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setTenHachuStDt(String ten_hachu_st_dt)
	{
		this.ten_hachu_st_dt = ten_hachu_st_dt;
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
		this.gentanka_vl = gentanka_vl;
		return true;
	}
	public String getGentankaVl()
	{
		return cutString(this.gentanka_vl,GENTANKA_VL_MAX_LENGTH);
	}
	public String getGentankaVlString()
	{
		return "'" + cutString(this.gentanka_vl,GENTANKA_VL_MAX_LENGTH) + "'";
	}
	public String getGentankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gentanka_vl,GENTANKA_VL_MAX_LENGTH));
	}

//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->

	// gentanka_sen_vlに対するセッターとゲッターの集合
//	public boolean setGentankaSenVl(String gentanka_sen_vl)
//	{
//		this.gentanka_sen_vl = gentanka_sen_vl;
//		return true;
//	}
//	public String getGentankaSenVl()
//	{
//		return cutString(this.gentanka_sen_vl,GENTANKA_SEN_VL_MAX_LENGTH);
//	}
//	public String getGentankaSenVlString()
//	{
//		return "'" + cutString(this.gentanka_sen_vl,GENTANKA_SEN_VL_MAX_LENGTH) + "'";
//	}
//	public String getGentankaSenVlHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.gentanka_sen_vl,GENTANKA_SEN_VL_MAX_LENGTH));
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->

	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public String getBaitankaVl()
	{
		return cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH);
	}
	public String getBaitankaVlString()
	{
		return "'" + cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getBaitankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH));
	}

	// hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setHachutaniIrisuQt(String hachutani_irisu_qt)
	{
		if(hachutani_irisu_qt == null || "".equals(hachutani_irisu_qt)) {
			this.hachutani_irisu_qt = "";

		} else {
			this.hachutani_irisu_qt = String.valueOf((long)Double.parseDouble(hachutani_irisu_qt));
		}

		return true;
	}
	public String getHachutaniIrisuQt()
	{
		return this.hachutani_irisu_qt;
	}
	public String getHachutaniIrisuQtString()
	{
		return "'" + cutString(this.hachutani_irisu_qt,HACHUTANI_IRISU_QT_MAX_LENGTH) + "'";
	}
	public String getHachutaniIrisuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachutani_irisu_qt,HACHUTANI_IRISU_QT_MAX_LENGTH));
	}

	// max_hachutani_qtに対するセッターとゲッターの集合
	public boolean setMaxHachutaniQt(String max_hachutani_qt)
	{
		if(max_hachutani_qt == null || "".equals(max_hachutani_qt)) {
			this.max_hachutani_qt = "";

		} else {
			this.max_hachutani_qt = String.valueOf((long)Double.parseDouble(max_hachutani_qt));
		}
		return true;
	}
	public String getMaxHachutaniQt()
	{
		return this.max_hachutani_qt;
	}
	public String getMaxHachutaniQtString()
	{
		return "'" + cutString(this.max_hachutani_qt,MAX_HACHUTANI_QT_MAX_LENGTH) + "'";
	}
	public String getMaxHachutaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.max_hachutani_qt,MAX_HACHUTANI_QT_MAX_LENGTH));
	}

	// teikan_kbに対するセッターとゲッターの集合
	public boolean setTeikanKb(String teikan_kb)
	{
		this.teikan_kb = teikan_kb;
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
//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	// nohin_kigen_qtに対するセッターとゲッターの集合
//	public boolean setNohinKigenQt(String nohin_kigen_qt)
//	{
//		this.nohin_kigen_qt = nohin_kigen_qt;
//		return true;
//	}
//	public String getNohinKigenQt()
//	{
//		return cutString(this.nohin_kigen_qt,NOHIN_KIGEN_QT_MAX_LENGTH);
//	}
//	public String getNohinKigenQtString()
//	{
//		return "'" + cutString(this.nohin_kigen_qt,NOHIN_KIGEN_QT_MAX_LENGTH) + "'";
//	}
//	public String getNohinKigenQtHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.nohin_kigen_qt,NOHIN_KIGEN_QT_MAX_LENGTH));
//	}
//
//	// nohin_kigen_kbに対するセッターとゲッターの集合
//	public boolean setNohinKigenKb(String nohin_kigen_kb)
//	{
//		this.nohin_kigen_kb = nohin_kigen_kb;
//		return true;
//	}
//	public String getNohinKigenKb()
//	{
//		return cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH);
//	}
//	public String getNohinKigenKbString()
//	{
//		return "'" + cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH) + "'";
//	}
//	public String getNohinKigenKbHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.nohin_kigen_kb,NOHIN_KIGEN_KB_MAX_LENGTH));
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->


	// mst_siyofuka_kbに対するセッターとゲッターの集合
	public boolean setMstSiyofukaKb(String mst_siyofuka_kb)
	{
		this.mst_siyofuka_kb = mst_siyofuka_kb;
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

/*
	// ten_haiso_kanri_cdに対するセッターとゲッターの集合
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
		this.sime_time_1_qt = sime_time_1_qt;
		return true;
	}
	public String getSimeTime1Qt()
	{
		return cutString(this.sime_time_1_qt,SIME_TIME_1_QT_MAX_LENGTH);
	}
	public String getSimeTime1QtString()
	{
		return "'" + cutString(this.sime_time_1_qt,SIME_TIME_1_QT_MAX_LENGTH) + "'";
	}
	public String getSimeTime1QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sime_time_1_qt,SIME_TIME_1_QT_MAX_LENGTH));
	}


	// c_nohin_rtime_1_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime1Kb(String c_nohin_rtime_1_kb)
	{
		this.c_nohin_rtime_1_kb = c_nohin_rtime_1_kb;
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
		this.sime_time_2_qt = sime_time_2_qt;
		return true;
	}
	public String getSimeTime2Qt()
	{
		return cutString(this.sime_time_2_qt,SIME_TIME_2_QT_MAX_LENGTH);
	}
	public String getSimeTime2QtString()
	{
		return "'" + cutString(this.sime_time_2_qt,SIME_TIME_2_QT_MAX_LENGTH) + "'";
	}
	public String getSimeTime2QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sime_time_2_qt,SIME_TIME_2_QT_MAX_LENGTH));
	}


	// c_nohin_rtime_2_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime2Kb(String c_nohin_rtime_2_kb)
	{
		this.c_nohin_rtime_2_kb = c_nohin_rtime_2_kb;
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
		this.sime_time_3_qt = sime_time_3_qt;
		return true;
	}
	public String getSimeTime3Qt()
	{
		return cutString(this.sime_time_3_qt,SIME_TIME_3_QT_MAX_LENGTH);
	}
	public String getSimeTime3QtString()
	{
		return "'" + cutString(this.sime_time_3_qt,SIME_TIME_3_QT_MAX_LENGTH) + "'";
	}
	public String getSimeTime3QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sime_time_3_qt,SIME_TIME_3_QT_MAX_LENGTH));
	}


	// c_nohin_rtime_3_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime3Kb(String c_nohin_rtime_3_kb)
	{
		this.c_nohin_rtime_3_kb = c_nohin_rtime_3_kb;
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

	// syohin_kbに対するセッターとゲッターの集合
	public boolean setSyohinKb(String syohin_kb)
	{
		this.syohin_kb = syohin_kb;
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


//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
	// buturyu_kbに対するセッターとゲッターの集合
//	public boolean setButuryuKb(String buturyu_kb)
//	{
//		this.buturyu_kb = buturyu_kb;
//		return true;
//	}
//	public String getButuryuKb()
//	{
//		return cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH);
//	}
//	public String getButuryuKbString()
//	{
//		return "'" + cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH) + "'";
//	}
//	public String getButuryuKbHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH));
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->

	// yokomoti_kbに対するセッターとゲッターの集合
	public boolean setYokomotiKb(String yokomoti_kb)
	{
		this.yokomoti_kb = yokomoti_kb;
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
	public boolean setHenpinNb(String henpin_nb)
	{
		this.henpin_nb = henpin_nb;
		return true;
	}
	public String getHenpinNb()
	{
		return cutString(this.henpin_nb,HENPIN_NB_MAX_LENGTH);
	}
	public String getHenpinNbString()
	{
		return "'" + cutString(this.henpin_nb,HENPIN_NB_MAX_LENGTH) + "'";
	}
	public String getHenpinNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.henpin_nb,HENPIN_NB_MAX_LENGTH));
	}


	// henpin_genka_vlに対するセッターとゲッターの集合
	public boolean setHenpinGenkaVl(String henpin_genka_vl)
	{
		this.henpin_genka_vl = henpin_genka_vl;
		return true;
	}
	public String getHenpinGenkaVl()
	{
		return cutString(this.henpin_genka_vl,HENPIN_GENKA_VL_MAX_LENGTH);
	}
	public String getHenpinGenkaVlString()
	{
		return "'" + cutString(this.henpin_genka_vl,HENPIN_GENKA_VL_MAX_LENGTH) + "'";
	}
	public String getHenpinGenkaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.henpin_genka_vl,HENPIN_GENKA_VL_MAX_LENGTH));
	}


	// rebate_fgに対するセッターとゲッターの集合
	public boolean setRebateFg(String rebate_fg)
	{
		this.rebate_fg = rebate_fg;
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
		this.hanbai_limit_qt = hanbai_limit_qt;
		return true;
	}
	public String getHanbaiLimitQt()
	{
		return cutString(this.hanbai_limit_qt,HANBAI_LIMIT_QT_MAX_LENGTH);
	}
	public String getHanbaiLimitQtString()
	{
		return "'" + cutString(this.hanbai_limit_qt,HANBAI_LIMIT_QT_MAX_LENGTH) + "'";
	}
	public String getHanbaiLimitQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_limit_qt,HANBAI_LIMIT_QT_MAX_LENGTH));
	}


	// hanbai_limit_kbに対するセッターとゲッターの集合
	public boolean setHanbaiLimitKb(String hanbai_limit_kb)
	{
		this.hanbai_limit_kb = hanbai_limit_kb;
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
		this.daisi_no_nb = daisi_no_nb;
		return true;
	}
	public String getDaisiNoNb()
	{
		return cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH);
	}
	public String getDaisiNoNbString()
	{
		return "'" + cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH) + "'";
	}
	public String getDaisiNoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH));
	}


	// unit_price_tani_kbに対するセッターとゲッターの集合
	public boolean setUnitPriceTaniKb(String unit_price_tani_kb)
	{
		this.unit_price_tani_kb = unit_price_tani_kb;
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
		this.unit_price_naiyoryo_qt = unit_price_naiyoryo_qt;
		return true;
	}
	public String getUnitPriceNaiyoryoQt()
	{
		return cutString(this.unit_price_naiyoryo_qt,UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH);
	}
	public String getUnitPriceNaiyoryoQtString()
	{
		return "'" + cutString(this.unit_price_naiyoryo_qt,UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH) + "'";
	}
	public String getUnitPriceNaiyoryoQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_price_naiyoryo_qt,UNIT_PRICE_NAIYORYO_QT_MAX_LENGTH));
	}


	// unit_price_kijun_tani_qtに対するセッターとゲッターの集合
	public boolean setUnitPriceKijunTaniQt(String unit_price_kijun_tani_qt)
	{
		this.unit_price_kijun_tani_qt = unit_price_kijun_tani_qt;
		return true;
	}
	public String getUnitPriceKijunTaniQt()
	{
		return cutString(this.unit_price_kijun_tani_qt,UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH);
	}
	public String getUnitPriceKijunTaniQtString()
	{
		return "'" + cutString(this.unit_price_kijun_tani_qt,UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH) + "'";
	}
	public String getUnitPriceKijunTaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_price_kijun_tani_qt,UNIT_PRICE_KIJUN_TANI_QT_MAX_LENGTH));
	}


	// shinazoroe_kbに対するセッターとゲッターの集合
	public boolean setShinazoroeKb(String shinazoroe_kb)
	{
		this.shinazoroe_kb = shinazoroe_kb;
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


	// yoridori_kbに対するセッターとゲッターの集合
	public boolean setYoridoriKb(String yoridori_kb)
	{
		this.yoridori_kb = yoridori_kb;
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
		return true;
	}
	public String getYoridoriQt()
	{
		return cutString(this.yoridori_qt,YORIDORI_QT_MAX_LENGTH);
	}
	public String getYoridoriQtString()
	{
		return "'" + cutString(this.yoridori_qt,YORIDORI_QT_MAX_LENGTH) + "'";
	}
	public String getYoridoriQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yoridori_qt,YORIDORI_QT_MAX_LENGTH));
	}


	// tag_hyoji_baika_vlに対するセッターとゲッターの集合
	public boolean setTagHyojiBaikaVl(String tag_hyoji_baika_vl)
	{
		this.tag_hyoji_baika_vl = tag_hyoji_baika_vl;
		return true;
	}
	public String getTagHyojiBaikaVl()
	{
		return cutString(this.tag_hyoji_baika_vl,TAG_HYOJI_BAIKA_VL_MAX_LENGTH);
	}
	public String getTagHyojiBaikaVlString()
	{
		return "'" + cutString(this.tag_hyoji_baika_vl,TAG_HYOJI_BAIKA_VL_MAX_LENGTH) + "'";
	}
	public String getTagHyojiBaikaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tag_hyoji_baika_vl,TAG_HYOJI_BAIKA_VL_MAX_LENGTH));
	}


	// season_cdに対するセッターとゲッターの集合
	public boolean setSeasonCd(String season_cd)
	{
		this.season_cd = season_cd;
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


	// itf_cdに対するセッターとゲッターの集合
	public boolean setItfCd(String itf_cd)
	{
		this.itf_cd = itf_cd;
		return true;
	}
	public String getItfCd()
	{
		return cutString(this.itf_cd,ITF_CD_MAX_LENGTH);
	}
	public String getItfCdString()
	{
		return "'" + cutString(this.itf_cd,ITF_CD_MAX_LENGTH) + "'";
	}
	public String getItfCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.itf_cd,ITF_CD_MAX_LENGTH));
	}


	// case_irisu_qtに対するセッターとゲッターの集合
	public boolean setCaseIrisuQt(String case_irisu_qt)
	{
		this.case_irisu_qt = case_irisu_qt;
		return true;
	}
	public String getCaseIrisuQt()
	{
		return cutString(this.case_irisu_qt,CASE_IRISU_QT_MAX_LENGTH);
	}
	public String getCaseIrisuQtString()
	{
		return "'" + cutString(this.case_irisu_qt,CASE_IRISU_QT_MAX_LENGTH) + "'";
	}
	public String getCaseIrisuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.case_irisu_qt,CASE_IRISU_QT_MAX_LENGTH));
	}


	// nyuka_syohin_cdに対するセッターとゲッターの集合
	public boolean setNyukaSyohinCd(String nyuka_syohin_cd)
	{
		this.nyuka_syohin_cd = nyuka_syohin_cd;
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
		this.pack_width_qt = pack_width_qt;
		return true;
	}
	public String getPackWidthQt()
	{
		return cutString(this.pack_width_qt,PACK_WIDTH_QT_MAX_LENGTH);
	}
	public String getPackWidthQtString()
	{
		return "'" + cutString(this.pack_width_qt,PACK_WIDTH_QT_MAX_LENGTH) + "'";
	}
	public String getPackWidthQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pack_width_qt,PACK_WIDTH_QT_MAX_LENGTH));
	}


	// pack_heigth_qtに対するセッターとゲッターの集合
	public boolean setPackHeigthQt(String pack_heigth_qt)
	{
		this.pack_heigth_qt = pack_heigth_qt;
		return true;
	}
	public String getPackHeigthQt()
	{
		return cutString(this.pack_heigth_qt,PACK_HEIGTH_QT_MAX_LENGTH);
	}
	public String getPackHeigthQtString()
	{
		return "'" + cutString(this.pack_heigth_qt,PACK_HEIGTH_QT_MAX_LENGTH) + "'";
	}
	public String getPackHeigthQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pack_heigth_qt,PACK_HEIGTH_QT_MAX_LENGTH));
	}


	// pack_depth_qtに対するセッターとゲッターの集合
	public boolean setPackDepthQt(String pack_depth_qt)
	{
		this.pack_depth_qt = pack_depth_qt;
		return true;
	}
	public String getPackDepthQt()
	{
		return cutString(this.pack_depth_qt,PACK_DEPTH_QT_MAX_LENGTH);
	}
	public String getPackDepthQtString()
	{
		return "'" + cutString(this.pack_depth_qt,PACK_DEPTH_QT_MAX_LENGTH) + "'";
	}
	public String getPackDepthQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pack_depth_qt,PACK_DEPTH_QT_MAX_LENGTH));
	}


	// pack_weight_qtに対するセッターとゲッターの集合
	public boolean setPackWeightQt(String pack_weight_qt)
	{
		this.pack_weight_qt = pack_weight_qt;
		return true;
	}
	public String getPackWeightQt()
	{
		return cutString(this.pack_weight_qt,PACK_WEIGHT_QT_MAX_LENGTH);
	}
	public String getPackWeightQtString()
	{
		return "'" + cutString(this.pack_weight_qt,PACK_WEIGHT_QT_MAX_LENGTH) + "'";
	}
	public String getPackWeightQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pack_weight_qt,PACK_WEIGHT_QT_MAX_LENGTH));
	}


	// center_zaiko_kbに対するセッターとゲッターの集合
	public boolean setCenterZaikoKb(String center_zaiko_kb)
	{
		this.center_zaiko_kb = center_zaiko_kb;
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
		this.min_zaikosu_qt = min_zaikosu_qt;
		return true;
	}
	public String getMinZaikosuQt()
	{
		return cutString(this.min_zaikosu_qt,MIN_ZAIKOSU_QT_MAX_LENGTH);
	}
	public String getMinZaikosuQtString()
	{
		return "'" + cutString(this.min_zaikosu_qt,MIN_ZAIKOSU_QT_MAX_LENGTH) + "'";
	}
	public String getMinZaikosuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.min_zaikosu_qt,MIN_ZAIKOSU_QT_MAX_LENGTH));
	}


	// max_zaikosu_qtに対するセッターとゲッターの集合
	public boolean setMaxZaikosuQt(String max_zaikosu_qt)
	{
		this.max_zaikosu_qt = max_zaikosu_qt;
		return true;
	}
	public String getMaxZaikosuQt()
	{
		return cutString(this.max_zaikosu_qt,MAX_ZAIKOSU_QT_MAX_LENGTH);
	}
	public String getMaxZaikosuQtString()
	{
		return "'" + cutString(this.max_zaikosu_qt,MAX_ZAIKOSU_QT_MAX_LENGTH) + "'";
	}
	public String getMaxZaikosuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.max_zaikosu_qt,MAX_ZAIKOSU_QT_MAX_LENGTH));
	}


	// center_hachutani_kbに対するセッターとゲッターの集合
	public boolean setCenterHachutaniKb(String center_hachutani_kb)
	{
		this.center_hachutani_kb = center_hachutani_kb;
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
		this.center_hachutani_qt = center_hachutani_qt;
		return true;
	}
	public String getCenterHachutaniQt()
	{
		return this.center_hachutani_qt;
	}
	public String getCenterHachutaniQtString()
	{
		return "'" + cutString(this.center_hachutani_qt,CENTER_HACHUTANI_QT_MAX_LENGTH) + "'";
	}
	public String getCenterHachutaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_hachutani_qt,CENTER_HACHUTANI_QT_MAX_LENGTH));
	}


	// center_irisu_qtに対するセッターとゲッターの集合
	public boolean setCenterIrisuQt(String center_irisu_qt)
	{
		this.center_irisu_qt = center_irisu_qt;
		return true;
	}
	public String getCenterIrisuQt()
	{
		return this.center_irisu_qt;
	}
	public String getCenterIrisuQtString()
	{
		return "'" + cutString(this.center_irisu_qt,CENTER_IRISU_QT_MAX_LENGTH) + "'";
	}
	public String getCenterIrisuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_irisu_qt,CENTER_IRISU_QT_MAX_LENGTH));
	}


	// center_weight_qtに対するセッターとゲッターの集合
	public boolean setCenterWeightQt(String center_weight_qt)
	{
		this.center_weight_qt = center_weight_qt;
		return true;
	}
	public String getCenterWeightQt()
	{
		return cutString(this.center_weight_qt,CENTER_WEIGHT_QT_MAX_LENGTH);
	}
	public String getCenterWeightQtString()
	{
		return "'" + cutString(this.center_weight_qt,CENTER_WEIGHT_QT_MAX_LENGTH) + "'";
	}
	public String getCenterWeightQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_weight_qt,CENTER_WEIGHT_QT_MAX_LENGTH));
	}


	// tana_no_nbに対するセッターとゲッターの集合
	public boolean setTanaNoNb(String tana_no_nb)
	{
		this.tana_no_nb = tana_no_nb;
		return true;
	}
	public String getTanaNoNb()
	{
		return cutString(this.tana_no_nb,TANA_NO_NB_MAX_LENGTH);
	}
	public String getTanaNoNbString()
	{
		return "'" + cutString(this.tana_no_nb,TANA_NO_NB_MAX_LENGTH) + "'";
	}
	public String getTanaNoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tana_no_nb,TANA_NO_NB_MAX_LENGTH));
	}


	// dan_no_nbに対するセッターとゲッターの集合
	public boolean setDanNoNb(String dan_no_nb)
	{
		this.dan_no_nb = dan_no_nb;
		return true;
	}
	public String getDanNoNb()
	{
		return cutString(this.dan_no_nb,DAN_NO_NB_MAX_LENGTH);
	}
	public String getDanNoNbString()
	{
		return "'" + cutString(this.dan_no_nb,DAN_NO_NB_MAX_LENGTH) + "'";
	}
	public String getDanNoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.dan_no_nb,DAN_NO_NB_MAX_LENGTH));
	}


	// keiyaku_su_qtに対するセッターとゲッターの集合
	public boolean setKeiyakuSuQt(String keiyaku_su_qt)
	{
		this.keiyaku_su_qt = keiyaku_su_qt;
		return true;
	}
	public String getKeiyakuSuQt()
	{
		return cutString(this.keiyaku_su_qt,KEIYAKU_SU_QT_MAX_LENGTH);
	}
	public String getKeiyakuSuQtString()
	{
		return "'" + cutString(this.keiyaku_su_qt,KEIYAKU_SU_QT_MAX_LENGTH) + "'";
	}
	public String getKeiyakuSuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiyaku_su_qt,KEIYAKU_SU_QT_MAX_LENGTH));
	}


	// keiyaku_pattern_kbに対するセッターとゲッターの集合
	public boolean setKeiyakuPatternKb(String keiyaku_pattern_kb)
	{
		this.keiyaku_pattern_kb = keiyaku_pattern_kb;
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
		this.kijun_zaikosu_qt = kijun_zaikosu_qt;
		return true;
	}
	public String getKijunZaikosuQt()
	{
		return cutString(this.kijun_zaikosu_qt,KIJUN_ZAIKOSU_QT_MAX_LENGTH);
	}
	public String getKijunZaikosuQtString()
	{
		return "'" + cutString(this.kijun_zaikosu_qt,KIJUN_ZAIKOSU_QT_MAX_LENGTH) + "'";
	}
	public String getKijunZaikosuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kijun_zaikosu_qt,KIJUN_ZAIKOSU_QT_MAX_LENGTH));
	}


	// d_zokusei_1_naに対するセッターとゲッターの集合
	public boolean setDZokusei1Na(String d_zokusei_1_na)
	{
		this.d_zokusei_1_na = d_zokusei_1_na;
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
		return true;
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


	// d_zokusei_7_naに対するセッターとゲッターの集合
	public boolean setDZokusei7Na(String d_zokusei_7_na)
	{
		this.d_zokusei_7_na = d_zokusei_7_na;
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
	// sinki_toroku_dtに対するセッターとゲッターの集合
	public boolean setSinkiTorokuDt(String sinki_toroku_dt)
	{
		this.sinki_toroku_dt = sinki_toroku_dt;
		return true;
	}
	public String getSinkiTorokuDt()
	{
		return this.sinki_toroku_dt;
	}

	public boolean setPreGentankaVl(String pre_gentanka_vl)
	{
		this.pre_gentanka_vl = pre_gentanka_vl;
		return true;
	}
	public String getPreGentankaVl()
	{
		return this.pre_gentanka_vl;
	}

// prebaitankavlに対するセッターとゲッターの集合
	public boolean setPreBaitankaVl(String pre_baitanka_vl)
	{
		this.pre_baitanka_vl = pre_baitanka_vl;
		return true;
	}
	public String getPreBaitankaVl()
	{
		return this.pre_baitanka_vl;
	}

	// save_plu_send_dtに対するセッターとゲッターの集合
	public boolean setSavePluSendDt(String save_plu_send_dt)
	{
		this.save_plu_send_dt = save_plu_send_dt;
		return true;
	}
	public String getSavePluSendDt()
	{
		return this.save_plu_send_dt;
	}
	// save_ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setSaveTenHachuStDt(String save_ten_hachu_st_dt)
	{
		this.save_ten_hachu_st_dt = save_ten_hachu_st_dt;
		return true;
	}
	public String getSaveTenHachuStDt()
	{
		return this.save_ten_hachu_st_dt;
	}
	// save_ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setSaveTenHachuEdDt(String save_ten_hachu_ed_dt)
	{
		this.save_ten_hachu_ed_dt = save_ten_hachu_ed_dt;
		return true;
	}
	public String getSaveTenHachuEdDt()
	{
		return this.save_ten_hachu_ed_dt;
	}

	// save_hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setSaveHanbaiStDt(String save_hanbai_st_dt)
	{
		this.save_hanbai_st_dt = save_hanbai_st_dt;
		return true;
	}
	public String getSaveHanbaiStDt()
	{
		return this.save_hanbai_st_dt;
	}
	// save_hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setSaveHanbaiEdDt(String save_hanbai_ed_dt)
	{
		this.save_hanbai_ed_dt = save_hanbai_ed_dt;
		return true;
	}
	public String getSaveHanbaiEdDt()
	{
		return this.save_hanbai_ed_dt;
	}

	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
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


	// processingDivisionに対するセッターとゲッターの集合
	public boolean setProcessingDivision(String processingDivision)
	{
		this.processingDivision = processingDivision;
		return true;
	}
	public String getProcessingDivision()
	{
		return this.processingDivision;
	}


	// errorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String errorFlg)
	{
		this.errorFlg = errorFlg;
		return true;
	}
	public String getErrorFlg()
	{
		return this.errorFlg;
	}


	// errorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
		return true;
	}
	public String getErrorMessage()
	{
		return this.errorMessage;
	}


	// modeに対するセッターとゲッターの集合
	public boolean setMode(String mode)
	{
		this.mode = mode;
		return true;
	}
	public String getMode()
	{
		return this.mode;
	}


	// menuBarに対するセッターとゲッターの集合
	public boolean setMenuBar(String[] menuBar)
	{
		this.menuBar = menuBar;
		return true;
	}
	public String[] getMenuBar()
	{
		return this.menuBar;
	}


	// ctrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map ctrlColor)
	{
		this.ctrlColor = ctrlColor;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.ctrlColor;
	}


	// firstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String firstFocus)
	{
		this.firstFocus = firstFocus;
		return true;
	}
	public String getFirstFocus()
	{
		return this.firstFocus;
	}


	// insertFlgに対するセッターとゲッターの集合
	public boolean setInsertFlg(String insertFlg)
	{
		this.insertFlg = insertFlg;
		return true;
	}
	public String getInsertFlg()
	{
		return this.insertFlg;
	}


	// updateFlgに対するセッターとゲッターの集合
	public boolean setUpdateFlg(String updateFlg)
	{
		this.updateFlg = updateFlg;
		return true;
	}
	public String getUpdateFlg()
	{
		return this.updateFlg;
	}


	// deleteFlgに対するセッターとゲッターの集合
	public boolean setDeleteFlg(String deleteFlg)
	{
		this.deleteFlg = deleteFlg;
		return true;
	}
	public String getDeleteFlg()
	{
		return this.deleteFlg;
	}


	// referenceFlgに対するセッターとゲッターの集合
	public boolean setReferenceFlg(String referenceFlg)
	{
		this.referenceFlg = referenceFlg;
		return true;
	}
	public String getReferenceFlg()
	{
		return this.referenceFlg;
	}


//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	// hanku_kanji_rnに対するセッターとゲッターの集合
//	public boolean setHankuKanjiRn(String hanku_kanji_rn)
//	{
//		this.hanku_kanji_rn = hanku_kanji_rn;
//		return true;
//	}
//	public String getHankuKanjiRn()
//	{
//		return this.hanku_kanji_rn;
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
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
	// CsvFlgに対するセッターとゲッターの集合
	public boolean setCsvFlg(String CsvFlg)
	{
		this.CsvFlg = CsvFlg;
		return true;
	}
	public String getCsvFlg()
	{
		return this.CsvFlg;
	}
	// PrintFlgに対するセッターとゲッターの集合
	public boolean setPrintFlg(String PrintFlg)
	{
		this.PrintFlg = PrintFlg;
		return true;
	}
	public String getPrintFlg()
	{
		return this.PrintFlg;
	}

	// CheckFlgに対するセッターとゲッターの集合
	public boolean setCheckFlg(String CheckFlg)
	{
		this.CheckFlg = CheckFlg;
		return true;
	}
	public String getCheckFlg()
	{
		return this.CheckFlg;
	}

	// updateProcessFlgに対するセッターとゲッターの集合
	public boolean setUpdateProcessFlg(String updateProcessFlg)
	{
		this.updateProcessFlg = updateProcessFlg;
		return true;
	}
	public String getUpdateProcessFlg()
	{
		return this.updateProcessFlg;
	}
	//ExistFlgに対するセッターとゲッターの集合
	public boolean setExistFlg(String ExistFlg)
	{
		this.ExistFlg = ExistFlg;
		return true;
	}
	public String getExistFlg()
	{
		return this.ExistFlg;
	}
	//ErrorBackDispに対するセッターとゲッターの集合
	public boolean setErrorBackDisp(String ErrorBackDisp)
	{
		this.ErrorBackDisp = ErrorBackDisp;
		return true;
	}
	public String getErrorBackDisp()
	{
		return this.ErrorBackDisp;
	}

//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	//hinsyu_naに対するセッターとゲッターの集合
//	public boolean setHinsyuNa(String hinsyu_na)
//	{
//		this.hinsyu_na = hinsyu_na;
//		return true;
//	}
//	public String getHinsyuNa()
//	{
//		return this.hinsyu_na;
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
	//bland_naに対するセッターとゲッターの集合
	public boolean setBlandNa(String bland_na)
	{
		this.bland_na = bland_na;
		return true;
	}
	public String getBlandNa()
	{
		return this.bland_na;
	}
	//siiresaki_naに対するセッターとゲッターの集合
	public boolean setSiiresakiNa(String siiresaki_na)
	{
		this.siiresaki_na = siiresaki_na;
		return true;
	}
	public String getSiiresakiNa()
	{
		return this.siiresaki_na;
	}
	//ten_siiresaki_kanri_naに対するセッターとゲッターの集合
	public boolean setTenSiiresakiKanriNa(String ten_siiresaki_kanri_na)
	{
		this.ten_siiresaki_kanri_na = ten_siiresaki_kanri_na;
		return true;
	}
	public String getTenSiiresakiKanriNa()
	{
		return this.ten_siiresaki_kanri_na;
	}
	//daihyo_haiso_naに対するセッターとゲッターの集合
	public boolean setDaihyoHaisoNa(String daihyo_haiso_na)
	{
		this.daihyo_haiso_na = daihyo_haiso_na;
		return true;
	}
	public String getDaihyoHaisoNa()
	{
		return this.daihyo_haiso_na;
	}
/*
	//ten_haiso_kanri_naに対するセッターとゲッターの集合
	public boolean setTenHaisoKanriNa(String ten_haiso_kanri_na)
	{
		this.ten_haiso_kanri_na = ten_haiso_kanri_na;
		return true;
	}
	public String getTenHaisoKanriNa()
	{
		return this.ten_haiso_kanri_na;
	}
*/
	//zaiko_center_naに対するセッターとゲッターの集合
	public boolean setZaikoCenterNa(String zaiko_center_na)
	{
		this.zaiko_center_na = zaiko_center_na;
		return true;
	}
	public String getZaikoCenterNa()
	{
		return this.zaiko_center_na;
	}
	//genyuko_dtに対するセッターとゲッターの集合
	public boolean setGenyukoDt(String genyuko_dt)
	{
		this.genyuko_dt = genyuko_dt;
		return true;
	}
	public String getGenyukoDt()
	{
		return this.genyuko_dt;
	}
	//tenreigaiに対するセッターとゲッターの集合
	public boolean setTenReigai(String tenreigai)
	{
		this.tenreigai = tenreigai;
		return true;
	}
	public String getTenReigai()
	{
		return this.tenreigai;
	}
//	2006.01.22 T.hattori 追加　開始　先付け登録項目追加
	//sakidukehinsyutourokuに対するセッターとゲッターの集合
	public boolean setSakiduketouroku(String sakiduketouroku)
	{
		this.sakiduketouroku = sakiduketouroku;
		return true;
	}
	public String getSakiduketouroku()
	{
		return this.sakiduketouroku;
	}
//	2006.01.22 T.hattori 追加　開始　先付け登録項目追加
	//Hairetu_cd1に対するセッターとゲッターの集合
	public boolean setHairetuCd1(String Hairetu_cd1)
	{
		this.Hairetu_cd1 = Hairetu_cd1;
		return true;
	}
	public String getHairetuCd1()
	{
		return this.Hairetu_cd1;
	}
	//Hairetu_nm1に対するセッターとゲッターの集合
	public boolean setHairetuNm1(String Hairetu_nm1)
	{
		this.Hairetu_nm1 = Hairetu_nm1;
		return true;
	}
	public String getHairetuNm1()
	{
		return this.Hairetu_nm1;
	}
	//Hairetu_cd2に対するセッターとゲッターの集合
	public boolean setHairetuCd2(String Hairetu_cd2)
	{
		this.Hairetu_cd2 = Hairetu_cd2;
		return true;
	}
	public String getHairetuCd2()
	{
		return this.Hairetu_cd2;
	}
	//Hairetu_nm2に対するセッターとゲッターの集合
	public boolean  setHairetuNm2(String Hairetu_nm2)
	{
		this.Hairetu_nm2 = Hairetu_nm2;
		return true;
	}
	public String getHairetuNm2()
	{
		return this.Hairetu_nm2;
	}
	//CenterPattern1Kbに対するセッターとゲッターの集合
	public boolean  setCenterPattern1Kb(String CenterPattern1Kb)
	{
		this.CenterPattern1Kb = CenterPattern1Kb;
		return true;
	}
	public String getCenterPattern1Kb()
	{
		return this.CenterPattern1Kb;
	}
	//CenterPattern2Kbに対するセッターとゲッターの集合
	public boolean  setCenterPattern2Kb(String CenterPattern2Kb)
	{
		this.CenterPattern2Kb = CenterPattern2Kb;
		return true;
	}
	public String getCenterPattern2Kb()
	{
		return this.CenterPattern2Kb;
	}

//	<!--   ↓↓2006.07.11 wangjm カスタマイズ削除↓↓  -->
//	//CenterPattern2Kbに対するセッターとゲッターの集合
//	public boolean  setKeiryoukiNo(String KeiryoukiNo)
//	{
//		this.KeiryoukiNo = KeiryoukiNo;
//		return true;
//	}
//	public String getKeiryoukiNo()
//	{
//		return this.KeiryoukiNo;
//	}
//	<!--   ↑↑2006.07.11 wangjm カスタマイズ削除↑↑  -->
	//CenterPattern2Kbに対するセッターとゲッターの集合
	public boolean  setChangeFlg(String ChangeFlg)
	{
		this.ChangeFlg = ChangeFlg;
		return true;
	}
	public String getChangeFlg()
	{
		return this.ChangeFlg;
	}

//	↓↓仕様変更（2005.07.28）↓↓
	// syokai_toroku_tsに対するセッターとゲッターの集合
	public boolean setSyokaiTorokuTs(String syokai_toroku_ts)
	{
		this.syokai_toroku_ts = syokai_toroku_ts;
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


//	2006.08.26 By kema 画面に無いカラムを引継ぐため
	// gtin_cdに対するセッターとゲッターの集合
	public boolean setGtinCd(String gtin_cd)
	{
		this.gtin_cd = gtin_cd;
		return true;
	}
	public String getGtinCd()
	{
		return this.gtin_cd;
	}

	// center_kyoyo_dtに対するセッターとゲッターの集合
	public boolean setCenterKyoyoDt(String center_kyoyo_dt)
	{
		this.center_kyoyo_dt = center_kyoyo_dt;
		return true;
	}
	public String getCenterKyoyoDt()
	{
		return this.center_kyoyo_dt;
	}

	// syohi_kigen_dtに対するセッターとゲッターの集合
	public boolean setSyohiKigenDt(String syohi_kigen_dt)
	{
		this.syohi_kigen_dt = syohi_kigen_dt;
		return true;
	}
	public String getSyohiKigenDt()
	{
		return this.syohi_kigen_dt;
	}

	// hacyu_syohin_cdに対するセッターとゲッターの集合
	public boolean setHacyuSyohinCd(String hacyu_syohin_cd)
	{
		this.hacyu_syohin_cd = hacyu_syohin_cd;
		return true;
	}
	public String getHacyuSyohinCd()
	{
		return this.hacyu_syohin_cd;
	}
	// plu_kbに対するセッターとゲッターの集合
	public boolean setPluKb(String plu_kb)
	{
		this.plu_kb = plu_kb;
		return true;
	}
	public String getPluKb()
	{
		return this.plu_kb;
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
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}

  	/**
   	 * 初期画面URL取得(ログ出力有り)
   	 * <br>
   	 * Ex)<br>
   	 * getInitUrl("logHeader","logMsg") -&gt; String<br>
   	 * <br>
   	 * @param String logHeader
   	 * @param String logMsg
   	 * @return		String
   	 */
	public String getInitUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}

		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}

		return	INIT_PAGE;
	}
	/**
	 * 初期画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getInitUrl()
	{
		return	INIT_PAGE;
	}

	/**
	 * 登録画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getEditUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getEditUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}

		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}

		return	EDIT_PAGE;
	}
	/**
	 * 登録画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getEditUrl()
	{
		return	EDIT_PAGE;
	}

	/**
	 * 照会画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getViewUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getViewUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}

		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}

		return	VIEW_PAGE;
	}
	/**
	 * 照会画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getViewUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getViewUrl()
	{
		return	VIEW_PAGE;
	}

	/**
	 * 権限エラー画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getKengenErr("logHeader") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @return		String
	 */
	public String getKengenErr(String logHeader)
	{
		stcLog.getLog().error(logHeader + InfoStrings.getInstance().getInfo("49999"));
		return KENGEN_PAGE;
	}




}


