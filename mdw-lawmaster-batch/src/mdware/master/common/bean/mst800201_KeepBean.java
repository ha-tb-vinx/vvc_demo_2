/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst800201_Keiryoki用計量器マスタの画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst800201_Keiryoki用計量器マスタの画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.*;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
//BUGNO-S052 2005.05.14 S.Takahashi START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 S.Takahashi END

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst800201_Keiryoki用計量器マスタの画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst800201_Keiryoki用計量器マスタの画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst800201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KEIRYO_HANKU_CD_MAX_LENGTH = 1;		// 計量販区コードの長さ
	public static final int KEIRYO_TYPE_CD_MAX_LENGTH = 1;		// 計量器タイプの長さ
	public static final int KEIRYO_LABEL_CD_MAX_LENGTH = 1;		// 計量器ラベルの長さ
	public static final int SYOHIN_YOBIDASI_MAX_LENGTH = 4;		// 呼出NOの長さ
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;			// 小業種コード (FK)の長さ
	public static final int THEME_CD_MAX_LENGTH = 2;				// テーマコード (FK)の長さ
	public static final int HANEI_DT_MAX_LENGTH = 8;				// 反映日の長さ
	public static final int SYOHIN_KBN_MAX_LENGTH = 1;			// 商品区分の長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;			// 商品コードの長さ
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 18;		// 漢字品名の長さ
	public static final int KEIRYOKI_NA_MAX_LENGTH = 50;			// 計量器名称の長さ
	public static final int KEIRYOKI2_NA_MAX_LENGTH = 50;		// 計量器名称２の長さ
	public static final int KEIRYOKI3_NA_MAX_LENGTH = 50;		// 計量器名称３の長さ
	public static final int RECEIPT_HINMEI_NA_MAX_LENGTH = 30;	// レシート品名の長さ
	public static final int TEIGAKU_UP_KB_MAX_LENGTH = 1;		// 定額・UP区分の長さ
	public static final int TEIGAKUJI_TANI_KB_MAX_LENGTH = 2;	// 定額時単位区分の長さ
	public static final int SANTI_KB_MAX_LENGTH = 3;				// 産地区分の長さ
	public static final int SYOMIKIKAN_KB_MAX_LENGTH = 1;		// 賞味期間計算区分の長さ
	public static final int KAKOJIKOKU_PRINT_KB_MAX_LENGTH = 1;	// 加工時刻印字区分の長さ
	public static final int CHORIYO_KOKOKUBUN_KB_MAX_LENGTH = 3;	// 調理用広告文の長さ
	public static final int HOZON_ONDOTAI_KB_MAX_LENGTH = 2;		// 保存温度帯区分の長さ
	public static final int START_KB_MAX_LENGTH = 2;				// 開始日付区分の長さ
	public static final int BACK_LABEL_KB_MAX_LENGTH = 1;		// 裏面ラベル項目文区分の長さ
	public static final int EIYO_SEIBUN_NA_MAX_LENGTH = 30;		// 栄養成分表示の長さ
	public static final int COMMENT_KB_MAX_LENGTH = 2;			// コメント区分の長さ
	public static final int BIKO_TX_MAX_LENGTH = 100;			// 備考の長さ
	public static final int GENZAIRYO_NA_MAX_LENGTH = 864;		// 原材料名称の長さ
	public static final int TENKABUTU_NA_MAX_LENGTH = 864;		// 添加物名称の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;			// 作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;		// 作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;			// 更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;		// 更新者社員IDの長さ

	private String ProcessingDivision	= "0";				// 処理区分
	private String ErrorFlg			= "";				// エラーフラグ
	private String ErrorMessage		= "";				// エラーメッセージ
	private String Mode				= "";				// 処理モード
	private String[] MenuBar			= null;			// メニューバーアイテム
	private Map CtrlColor				= new HashMap();	// コントロール前景色
	private String FirstFocus			= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg			= "";				// 新規処理利用可能区分
	private String UpdateFlg			= "";				// 更新処理利用可能区分
	private String DeleteFlg			= "";				// 削除処理利用可能区分
	private String ReferenceFlg		= "";				// 照会処理利用可能区分
	private String CsvFlg				= "";				// CSV処理利用可能区分
	private String PrintFlg			= "";				// 印刷処理利用可能区分
	private String DispUrl				= "";				// 現画面URL
	private String BeforeDispKb		= "";				// 前画面区分(Menu:0 Menu以外:1)
	private String BeforeDispUrl		= "";				// 前画面URL
	private String DecisionFlg			= "";				// 確定ボタン押下フラグ（エラー判定時用）
	private String changekeyflg		= "";				//

	private String s_gyosyu_cd			= "";				//小業種コード (FK)
	private String theme_cd			= "";				//テーマコード (FK)
	private String keiryo_hanku_cd		= "";				//計量販区コード
	private String keiryo_type_cd		= "";				//計量器タイプ
	private String keiryo_label_cd		= "";				//計量器ラベル
	private String syohin_yobidasi		= "";				//呼出NO
	private String hanei_dt			= "";				//反映日
	private String syohin_kbn			= "";				//商品区分
	private String syohin_cd			= "";				//商品コード
	private String hinmei_kanji_na		= "";				//漢字品名
	private String keiryoki_na			= "";				//計量器名称
	private String keiryoki2_na		= "";				//計量器名称２
	private String keiryoki3_na		= "";				//計量器名称３
	private String receipt_hinmei_na	= "";				//レシート品名
	private String teigaku_up_kb		= "";				//定額・UP区分
	private String tanka_vl 			= "";				//単価
	private long teigaku_vl = 0;							//定額時内容量
//	BUGNO-006 2005.04.20 S.Murata START
	private String teigaku_vls			= "";				//定額時内容量
//	BUGNO-006 2005.04.20 S.Murata END
	private String teigakuji_tani_kb	= "";				//定額時単位区分
	private String syomikikan_kb		= "";				//賞味期間計算区分
	private long syomikikan_vl		= 0;				//賞味期間
	private String santi_kb 			= "";				//産地番号
	private String kakojikoku_print_kb	= "";				//加工時刻印字区分
	private String choriyo_kokokubun_kb	= "";			//調理用広告文
	private String hozon_ondotai_kb	= "";				//保存温度帯区分
	private String start_kb			= "";				//開始日付区分
	private String back_label_kb		= "";				//裏面ラベル項目文区分
	private String eiyo_seibun_na		= "";				//栄養成分表示
	private String comment_kb			= "";				//コメント区分
	private String biko_tx				= "";				//備考
	private String insert_ts			= "";				//作成年月日
	private String genzairyo_na 		= "";				//原材料名称
	private String tenkabutu_na 		= "";				//添加物名称
	private String insert_user_id		= "";				//作成者社員ID
	private String update_ts			= "";				//更新年月日
	private String update_user_id		= "";				//更新者社員ID
	private String delete_fg			= "";				//削除フラグ

//	BUGNO-S005 2005.04.21 T.Makuta START
	private String changeflg			= "0";				//変更フラグ
//	BUGNO-S005 2005.04.21 T.Makuta END
//	BUGNO-S052 2005.05.14 S.Takahashi START
	private static final String INIT_PAGE   = "mst800101_KeiryokiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE   = "mst800201_KeiryokiEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE   = "mst800301_KeiryokiView";	// 削除・照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
	private static final String GEN_EDIT_PAGE = "mst810101_KeiryokiGenzairyoEdit";	// 計量器マスタ原材料新規・修正画面JSPを取得
	private static final String GEN_VIEW_PAGE = "mst810201_KeiryokiGenzairyoView";	// 計量器マスタ原材料削除・照会画面JSPを取得
//	BUGNO-S052 2005.05.14 S.Takahashi END

	// Processingdivisionに対するセッターとゲッターの集合
	public boolean setProcessingDivision(String ProcessingDivision)
	{
		this.ProcessingDivision = ProcessingDivision;
		return true;
	}
	public String getProcessingDivision()
	{
		return this.ProcessingDivision;
	}

	// ErrorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String ErrorFlg)
	{
		this.ErrorFlg = ErrorFlg;
		return true;
	}
	public String getErrorFlg()
	{
		return this.ErrorFlg;
	}

	// ErrorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
		return true;
	}
	public String getErrorMessage()
	{
		return this.ErrorMessage;
	}

	// Modeに対するセッターとゲッターの集合
	public boolean setMode(String Mode)
	{
		this.Mode = Mode;
		return true;
	}
	public String getMode()
	{
		return this.Mode;
	}

	// MenuBarに対するセッターとゲッターの集合
	public boolean setMenuBar(String[] MenuBar)
	{
		this.MenuBar = MenuBar;
		return true;
	}
	public String[] getMenuBar()
	{
		return this.MenuBar;
	}

	// CtrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map CtrlColor)
	{
		this.CtrlColor = CtrlColor;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.CtrlColor;
	}

	// FirstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String FirstFocus)
	{
		this.FirstFocus = FirstFocus;
		return true;
	}
	public String getFirstFocus()
	{
		return this.FirstFocus;
	}
	// InsertFlgに対するセッターとゲッターの集合
	public boolean setInsertFlg(String InsertFlg)
	{
		this.InsertFlg = InsertFlg;
		return true;
	}
	public String getInsertFlg()
	{
		return this.InsertFlg;
	}
	// UpdateFlgに対するセッターとゲッターの集合
	public boolean setUpdateFlg(String UpdateFlg)
	{
		this.UpdateFlg = UpdateFlg;
		return true;
	}
	public String getUpdateFlg()
	{
		return this.UpdateFlg;
	}
	// DeleteFlgに対するセッターとゲッターの集合
	public boolean setDeleteFlg(String DeleteFlg)
	{
		this.DeleteFlg = DeleteFlg;
		return true;
	}
	public String getDeleteFlg()
	{
		return this.DeleteFlg;
	}
	// ReferenceFlgに対するセッターとゲッターの集合
	public boolean setReferenceFlg(String ReferenceFlg)
	{
		this.ReferenceFlg = ReferenceFlg;
		return true;
	}
	public String getReferenceFlg()
	{
		return this.ReferenceFlg;
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
	// BeforeDispKbに対するセッターとゲッターの集合
	public boolean setBeforeDispKb(String BeforeDispKb)
	{
		this.BeforeDispKb = BeforeDispKb;
		return true;
	}
	public String getBeforeDispKb()
	{
		return this.BeforeDispKb;
	}
	// DispUrlに対するセッターとゲッターの集合
	public boolean setDispUrl(String DispUrl)
	{
		this.DispUrl = DispUrl;
		return true;
	}
	public String getDispUrl()
	{
		return this.DispUrl;
	}
	// BeforeDispUrlに対するセッターとゲッターの集合
	public boolean setBeforeDispUrl(String BeforeDispUrl)
	{
		this.BeforeDispUrl = BeforeDispUrl;
		return true;
	}
	public String getBeforeDispUrl()
	{
		return this.BeforeDispUrl;
	}
	// DecisionFlgに対するセッターとゲッターの集合
	public boolean setDecisionFlg(String DecisionFlg)
	{
		this.DecisionFlg = DecisionFlg;
		return true;
	}
	public String getDecisionFlg()
	{
		return this.DecisionFlg;
	}

	// keiryo_hanku_cdに対するセッターとゲッターの集合
	public boolean setKeiryoHankuCd(String keiryo_hanku_cd)
	{
		this.keiryo_hanku_cd = keiryo_hanku_cd;
		return true;
	}
	public String getKeiryoHankuCd()
	{
		return cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH);
	}
	public String getKeiryoHankuCdString()
	{
		return "'" + cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getKeiryoHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH));
	}

	// keiryo_type_cdに対するセッターとゲッターの集合
	public boolean setKeiryoTypeCd(String keiryo_type_cd)
	{
		this.keiryo_type_cd = keiryo_type_cd;
		return true;
	}
	public String getKeiryoTypeCd()
	{
		return cutString(this.keiryo_type_cd,KEIRYO_TYPE_CD_MAX_LENGTH);
	}
	public String getKeiryoTypeCdString()
	{
		return "'" + cutString(this.keiryo_type_cd,KEIRYO_TYPE_CD_MAX_LENGTH) + "'";
	}
	public String getKeiryoTypeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryo_type_cd,KEIRYO_TYPE_CD_MAX_LENGTH));
	}

	// keiryo_label_cdに対するセッターとゲッターの集合
	public boolean setKeiryoLabelCd(String keiryo_label_cd)
	{
		this.keiryo_label_cd = keiryo_label_cd;
		return true;
	}
	public String getKeiryoLabelCd()
	{
		return cutString(this.keiryo_label_cd,KEIRYO_LABEL_CD_MAX_LENGTH);
	}
	public String getKeiryoLabelCdString()
	{
		return "'" + cutString(this.keiryo_label_cd,KEIRYO_LABEL_CD_MAX_LENGTH) + "'";
	}
	public String getKeiryoLabelCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryo_label_cd,KEIRYO_LABEL_CD_MAX_LENGTH));
	}

	// syohin_yobidasiに対するセッターとゲッターの集合
	public boolean setSyohinYobidasi(String syohin_yobidasi)
	{
		this.syohin_yobidasi = syohin_yobidasi;
		return true;
	}
	public String getSyohinYobidasi()
	{
		return cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH);
	}
	public String getSyohinYobidasiString()
	{
		return "'" + cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH) + "'";
	}
	public String getSyohinYobidasiHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH));
	}


	// s_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
		return true;
	}
	public String getSGyosyuCd()
	{
		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSGyosyuCdString()
	{
		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
	}


	// theme_cdに対するセッターとゲッターの集合
	public boolean setThemeCd(String theme_cd)
	{
		this.theme_cd = theme_cd;
		return true;
	}
	public String getThemeCd()
	{
		return cutString(this.theme_cd,THEME_CD_MAX_LENGTH);
	}
	public String getThemeCdString()
	{
		return "'" + cutString(this.theme_cd,THEME_CD_MAX_LENGTH) + "'";
	}
	public String getThemeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.theme_cd,THEME_CD_MAX_LENGTH));
	}


	// hanei_dtに対するセッターとゲッターの集合
	public boolean setHaneiDt(String hanei_dt)
	{
		this.hanei_dt = hanei_dt;
		return true;
	}
	public String getHaneiDt()
	{
		return cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH);
	}
	public String getHaneiDtString()
	{
		return "'" + cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH) + "'";
	}
	public String getHaneiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH));
	}


	// syohin_kbnに対するセッターとゲッターの集合
	public boolean setSyohinKbn(String syohin_kbn)
	{
		this.syohin_kbn = syohin_kbn;
		return true;
	}
	public String getSyohinKbn()
	{
		return cutString(this.syohin_kbn,SYOHIN_KBN_MAX_LENGTH);
	}
	public String getSyohinKbnString()
	{
		return "'" + cutString(this.syohin_kbn,SYOHIN_KBN_MAX_LENGTH) + "'";
	}
	public String getSyohinKbnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_kbn,SYOHIN_KBN_MAX_LENGTH));
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

	// hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
		return true;
	}
	public String getHinmeiKanjiNa()
	{
//		BUGNO-006 2005.04.20 T.kikuchi START		
			  //return cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH);
			  return this.hinmei_kanji_na;
//		BUGNO-006 2005.04.20 T.kikuchi END		
	}
	public String getHinmeiKanjiNaString()
	{
		return "'" + cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// keiryoki_naに対するセッターとゲッターの集合
	public boolean setKeiryokiNa(String keiryoki_na)
	{
		this.keiryoki_na = keiryoki_na;
		return true;
	}
	public String getKeiryokiNa()
	{
		return cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH);
	}
	public String getKeiryokiNaString()
	{
		return "'" + cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH) + "'";
	}
	public String getKeiryokiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH));
	}

	// keiryoki2_naに対するセッターとゲッターの集合
	public boolean setKeiryoki2Na(String keiryoki2_na)
	{
		this.keiryoki2_na = keiryoki2_na;
		return true;
	}
	public String getKeiryoki2Na()
	{
		return cutString(this.keiryoki2_na,KEIRYOKI2_NA_MAX_LENGTH);
	}
	public String getKeiryoki2NaString()
	{
		return "'" + cutString(this.keiryoki2_na,KEIRYOKI2_NA_MAX_LENGTH) + "'";
	}
	public String getKeiryoki2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryoki2_na,KEIRYOKI2_NA_MAX_LENGTH));
	}

	// keiryoki3_naに対するセッターとゲッターの集合
	public boolean setKeiryoki3Na(String keiryoki3_na)
	{
		this.keiryoki3_na = keiryoki3_na;
		return true;
	}
	public String getKeiryoki3Na()
	{
		return cutString(this.keiryoki3_na,KEIRYOKI3_NA_MAX_LENGTH);
	}
	public String getKeiryoki3NaString()
	{
		return "'" + cutString(this.keiryoki3_na,KEIRYOKI3_NA_MAX_LENGTH) + "'";
	}
	public String getKeiryoki3NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryoki3_na,KEIRYOKI3_NA_MAX_LENGTH));
	}

	// receipt_hinmei_naに対するセッターとゲッターの集合
	public boolean setReceiptHinmeiNa(String receipt_hinmei_na)
	{
		this.receipt_hinmei_na = receipt_hinmei_na;
		return true;
	}
	public String getReceiptHinmeiNa()
	{
		return cutString(this.receipt_hinmei_na,RECEIPT_HINMEI_NA_MAX_LENGTH);
	}
	public String getReceiptHinmeiNaString()
	{
		return "'" + cutString(this.receipt_hinmei_na,RECEIPT_HINMEI_NA_MAX_LENGTH) + "'";
	}
	public String getReceiptHinmeiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.receipt_hinmei_na,RECEIPT_HINMEI_NA_MAX_LENGTH));
	}


	// teigaku_up_kbに対するセッターとゲッターの集合
	public boolean setTeigakuUpKb(String teigaku_up_kb)
	{
		this.teigaku_up_kb = teigaku_up_kb;
		return true;
	}
	public String getTeigakuUpKb()
	{
		return cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH);
	}
	public String getTeigakuUpKbString()
	{
		return "'" + cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH) + "'";
	}
	public String getTeigakuUpKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH));
	}


	// tanka_vlに対するセッターとゲッターの集合
	public boolean setTankaVl(String tanka_vl)
	{
		this.tanka_vl = tanka_vl;
		return true;
	}
	public String getTankaVl()
	{
		return this.tanka_vl;
	}
	public String getTankaVlString()
	{
		return this.tanka_vl;
	}


	// teigaku_vlに対するセッターとゲッターの集合
	public boolean setTeigakuVl(String teigaku_vl)
	{
		try
		{
			this.teigaku_vl = Long.parseLong(teigaku_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTeigakuVl(long teigaku_vl)
	{
		this.teigaku_vl = teigaku_vl;
		return true;
	}
	public long getTeigakuVl()
	{
		return this.teigaku_vl;
	}
	public String getTeigakuVlString()
	{
		return Long.toString(this.teigaku_vl);
	}


	// teigakuji_tani_kbに対するセッターとゲッターの集合
	public boolean setTeigakujiTaniKb(String teigakuji_tani_kb)
	{
		this.teigakuji_tani_kb = teigakuji_tani_kb;
		return true;
	}
	public String getTeigakujiTaniKb()
	{
		return cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH);
	}
	public String getTeigakujiTaniKbString()
	{
		return "'" + cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH) + "'";
	}
	public String getTeigakujiTaniKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH));
	}


	// syomikikan_kbに対するセッターとゲッターの集合
	public boolean setSyomikikanKb(String syomikikan_kb)
	{
		this.syomikikan_kb = syomikikan_kb;
		return true;
	}
	public String getSyomikikanKb()
	{
		return cutString(this.syomikikan_kb,SYOMIKIKAN_KB_MAX_LENGTH);
	}
	public String getSyomikikanKbString()
	{
		return "'" + cutString(this.syomikikan_kb,SYOMIKIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getSyomikikanKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syomikikan_kb,SYOMIKIKAN_KB_MAX_LENGTH));
	}

	// syomikikan_vlに対するセッターとゲッターの集合
	public boolean setSyomikikanVl(String syomikikan_vl)
	{
		try
		{
			this.syomikikan_vl = Long.parseLong(syomikikan_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyomikikanVl(long syomikikan_vl)
	{
		this.syomikikan_vl = syomikikan_vl;
		return true;
	}
	public long getSyomikikanVl()
	{
		return this.syomikikan_vl;
	}
	public String getSyomikikanVlString()
	{
		return Long.toString(this.syomikikan_vl);
	}

	// santi_kbに対するセッターとゲッターの集合
	public boolean setSantiKb(String santi_kb)
	{
		this.santi_kb = santi_kb;
		return true;
	}
	public String getSantiKb()
	{
		return cutString(this.santi_kb,SANTI_KB_MAX_LENGTH);
	}
	public String getSantiKbString()
	{
		return "'" + cutString(this.santi_kb,SANTI_KB_MAX_LENGTH) + "'";
	}
	public String getSantiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.santi_kb,SANTI_KB_MAX_LENGTH));
	}

	// kakojikoku_print_kbに対するセッターとゲッターの集合
	public boolean setKakojikokuPrintKb(String kakojikoku_print_kb)
	{
		this.kakojikoku_print_kb = kakojikoku_print_kb;
		return true;
	}
	public String getKakojikokuPrintKb()
	{
		return cutString(this.kakojikoku_print_kb,KAKOJIKOKU_PRINT_KB_MAX_LENGTH);
	}
	public String getKakojikokuPrintKbString()
	{
		return "'" + cutString(this.kakojikoku_print_kb,KAKOJIKOKU_PRINT_KB_MAX_LENGTH) + "'";
	}
	public String getKakojikokuPrintKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kakojikoku_print_kb,KAKOJIKOKU_PRINT_KB_MAX_LENGTH));
	}


	// choriyo_kokokubun_kbに対するセッターとゲッターの集合
	public boolean setChoriyoKokokubunKb(String choriyo_kokokubun_kb)
	{
		this.choriyo_kokokubun_kb = choriyo_kokokubun_kb;
		return true;
	}
	public String getChoriyoKokokubunKb()
	{
		return cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH);
	}
	public String getChoriyoKokokubunKbString()
	{
		return "'" + cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH) + "'";
	}
	public String getChoriyoKokokubunKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH));
	}


	// hozon_ondotai_kbに対するセッターとゲッターの集合
	public boolean setHozonOndotaiKb(String hozon_ondotai_kb)
	{
		this.hozon_ondotai_kb = hozon_ondotai_kb;
		return true;
	}
	public String getHozonOndotaiKb()
	{
		return cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH);
	}
	public String getHozonOndotaiKbString()
	{
		return "'" + cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH) + "'";
	}
	public String getHozonOndotaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH));
	}


	// start_kbに対するセッターとゲッターの集合
	public boolean setStartKb(String start_kb)
	{
		this.start_kb = start_kb;
		return true;
	}
	public String getStartKb()
	{
		return cutString(this.start_kb,START_KB_MAX_LENGTH);
	}
	public String getStartKbString()
	{
		return "'" + cutString(this.start_kb,START_KB_MAX_LENGTH) + "'";
	}
	public String getStartKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.start_kb,START_KB_MAX_LENGTH));
	}


	// back_label_kbに対するセッターとゲッターの集合
	public boolean setBackLabelKb(String back_label_kb)
	{
		this.back_label_kb = back_label_kb;
		return true;
	}
	public String getBackLabelKb()
	{
		return cutString(this.back_label_kb,BACK_LABEL_KB_MAX_LENGTH);
	}
	public String getBackLabelKbString()
	{
		return "'" + cutString(this.back_label_kb,BACK_LABEL_KB_MAX_LENGTH) + "'";
	}
	public String getBackLabelKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.back_label_kb,BACK_LABEL_KB_MAX_LENGTH));
	}


	// eiyo_seibun_naに対するセッターとゲッターの集合
	public boolean setEiyoSeibunNa(String eiyo_seibun_na)
	{
		this.eiyo_seibun_na = eiyo_seibun_na;
		return true;
	}
	public String getEiyoSeibunNa()
	{
		return cutString(this.eiyo_seibun_na,EIYO_SEIBUN_NA_MAX_LENGTH);
	}
	public String getEiyoSeibunNaString()
	{
		return "'" + cutString(this.eiyo_seibun_na,EIYO_SEIBUN_NA_MAX_LENGTH) + "'";
	}
	public String getEiyoSeibunNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eiyo_seibun_na,EIYO_SEIBUN_NA_MAX_LENGTH));
	}


	// comment_kbに対するセッターとゲッターの集合
	public boolean setCommentKb(String comment_kb)
	{
		this.comment_kb = comment_kb;
		return true;
	}
	public String getCommentKb()
	{
		return cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH);
	}
	public String getCommentKbString()
	{
		return "'" + cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH) + "'";
	}
	public String getCommentKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH));
	}


	// biko_txに対するセッターとゲッターの集合
	public boolean setBikoTx(String biko_tx)
	{
		this.biko_tx = biko_tx;
		return true;
	}
	public String getBikoTx()
	{
		return cutString(this.biko_tx,BIKO_TX_MAX_LENGTH);
	}
	public String getBikoTxString()
	{
		return "'" + cutString(this.biko_tx,BIKO_TX_MAX_LENGTH) + "'";
	}
	public String getBikoTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.biko_tx,BIKO_TX_MAX_LENGTH));
	}


	// genzairyo_naに対するセッターとゲッターの集合
	public boolean setGenzairyoNa(String genzairyo_na)
	{
		this.genzairyo_na = genzairyo_na;
		return true;
	}
	public String getGenzairyoNa()
	{
		return cutString(this.genzairyo_na,GENZAIRYO_NA_MAX_LENGTH);
	}
	public String getGenzairyoNaString()
	{
		return "'" + cutString(this.genzairyo_na,GENZAIRYO_NA_MAX_LENGTH) + "'";
	}
	public String getGenzairyoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.genzairyo_na,GENZAIRYO_NA_MAX_LENGTH));
	}


	// tenkabutu_naに対するセッターとゲッターの集合
	public boolean setTenkabutuNa(String tenkabutu_na)
	{
		this.tenkabutu_na = tenkabutu_na;
		return true;
	}
	public String getTenkabutuNa()
	{
		return cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH);
	}
	public String getTenkabutuNaString()
	{
		return "'" + cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH) + "'";
	}
	public String getTenkabutuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH));
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


	// changekeyflgに対するセッターとゲッターの集合
	public boolean setChangeKeyFlg(String changekeyflg)
	{
		this.changekeyflg = changekeyflg;
		return true;
	}
	public String getChangeKeyFlg()
	{
		return cutString(this.changekeyflg,UPDATE_USER_ID_MAX_LENGTH);
	}
	
//	BUGNO-006 2005.04.20 S.Murata START
	// santi_nbsに対するセッターとゲッターの集合
//	public String getSanti_nbs() {
//		return santi_nbs;
//	}
//	public void setSanti_nbs(String string) {
//		santi_nbs = string;
//	}

	// teigaku_vlsに対するセッターとゲッターの集合
	public String getTeigaku_vls() {
		return teigaku_vls;
	}

	public void setTeigaku_vls(String string) {
		teigaku_vls = string;
	}
//	BUGNO-006 2005.04.20 S.Murata END

//	BUGNO-S005 2005.04.21 T.Makuta START
	// changeflgに対するセッターとゲッターの集合
	public String getChangeflg() {
		return changeflg;
	}

	public void setChangeflg(String string) {
		changeflg = string;
	}
//	BUGNO-S005 2005.04.21 T.Makuta END

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
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}

//	BUGNO-S052 2005.05.14 SIRIUS START
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
		  if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.ErrorFlg.equals("")){
			  //通常系
			  if(!this.ErrorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.ErrorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.ErrorMessage);
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
		  if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.ErrorFlg.equals("")){
			  //通常系
			  if(!this.ErrorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.ErrorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.ErrorMessage);
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
		  if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.ErrorFlg.equals("")){
			  //通常系
			  if(!this.ErrorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.ErrorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.ErrorMessage);
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
	 * 原材料登録画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getGenEditUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getGenEditUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.ErrorFlg.equals("")){
			//通常系
			if(!this.ErrorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.ErrorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.ErrorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	GEN_EDIT_PAGE;
	}
	/**
	 * 原材料登録画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getGenEditUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getGenEditUrl()
	{
		return	GEN_EDIT_PAGE;
	}

	/**
	 * 原材料照会画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getGenViewUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getGenViewUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.ErrorFlg.equals("")){
			//通常系
			if(!this.ErrorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.ErrorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.ErrorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	GEN_VIEW_PAGE;
	}
	/**
	 * 原材料照会画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getGenViewUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getGenViewUrl()
	{
		return	GEN_VIEW_PAGE;
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
//	BUGNO-S052 2005.05.14 SIRIUS START
  }

