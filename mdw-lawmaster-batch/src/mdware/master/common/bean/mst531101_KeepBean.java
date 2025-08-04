/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス mst531101_Tenpo用店マスタ(詳細)の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst530201_Tenpo用店マスタ(詳細)の画面項目格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius M.Nakajima
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.*;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス mst531101_Tenpo用店マスタ(詳細)の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst531101_Tenpo用店マスタ(詳細)の画面項目格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius M.Nakajima
 * @version 1.0
 * @see なし								
 */
public class mst531101_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コードの長さ
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
	public static final int HOJIN_CD_MAX_LENGTH = 4;//法人コードの長さ
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	public static final int TENPOKAISO_TIIKI_CD_MAX_LENGTH = 6;//店舗階層(地域)の長さ
	public static final int TENPOKAISO_AREA_CD_MAX_LENGTH = 6;//店舗階層(エリア)の長さ
	public static final int TENPOKAISO_BLOCK_CD_MAX_LENGTH = 6;//店舗階層(ブロック)の長さ
	public static final int KANJI_NA_MAX_LENGTH = 80;//正式名称(漢字)の長さ
//	↓↓仕様変更（2005.08.18）↓↓
//	public static final int KANA_NA_MAX_LENGTH = 80;//正式名称(カナ)の長さ
	public static final int KANA_NA_MAX_LENGTH = 20;//正式名称(カナ)の長さ
//	↑↑仕様変更（2005.08.18）↑↑
	public static final int KANJI_RN_MAX_LENGTH = 40;//略式名称(漢字)の長さ
//	↓↓仕様変更（2005.08.18）↓↓
//	public static final int KANA_RN_MAX_LENGTH = 20;//略式名称(カナ)の長さ
	public static final int KANA_RN_MAX_LENGTH = 20;//略式名称(カナ)の長さ
//	↑↑仕様変更（2005.08.18）↑↑
	public static final int TENPO_TYPE_KB_MAX_LENGTH = 1;//店舗タイプの長さ
	public static final int TENPO_KB_MAX_LENGTH = 1;//店舗区分の長さ
	public static final int KAITEN_DT_MAX_LENGTH = 8;//開店日の長さ
	public static final int HEITEN_DT_MAX_LENGTH = 8;//閉店日の長さ
	public static final int ADDRESS_KANJI_NA_MAX_LENGTH = 240;//住所(漢字)の長さ
	public static final int ADDRESS_KANA_NA_MAX_LENGTH = 60;//住所(カナ)の長さ
	public static final int ADDRESS_3_NA_MAX_LENGTH = 32;//住所３の長さ
	public static final int YUBIN_CD_MAX_LENGTH = 8;//郵便番号の長さ
	public static final int TEL_CD_MAX_LENGTH = 20;//電話番号の長さ
	public static final int FAX_CD_MAX_LENGTH = 20;//FAX番号の長さ
	public static final int HACHU_ST_DT_MAX_LENGTH = 8;//発注可能開始日の長さ
	public static final int HACHU_ED_DT_MAX_LENGTH = 8;//発注可能終了日の長さ
	public static final int SNDST_NEHUDA_DT_MAX_LENGTH = 8;//送信開始日：値札シールの長さ
	public static final int SNDST_PRICE_DT_MAX_LENGTH = 8;//送信開始日：プライスカードの長さ
	public static final int SNDST_TAG_DT_MAX_LENGTH = 8;//送信開始日：タグの長さ
	public static final int SNDST_PLU_DT_MAX_LENGTH = 8;//送信開始日：ＰＬＵの長さ
	public static final int SNDST_POP_DT_MAX_LENGTH = 8;//送信開始日：ＰＯＰの長さ
	public static final int SNDST_KEIRYOKI_DT_MAX_LENGTH = 8;//送信開始日：計量器の長さ
	public static final int HANKU1_KEIRYOKI_TYPE_MAX_LENGTH = 1;//販区１計量器タイプの長さ
	public static final int HANKU2_KEIRYOKI_TYPE_MAX_LENGTH = 1;//販区２計量器タイプの長さ
	public static final int HANKU3_KEIRYOKI_TYPE_MAX_LENGTH = 1;//販区３計量器タイプの長さ
	public static final int HANKU4_KEIRYOKI_TYPE_MAX_LENGTH = 1;//販区４計量器タイプの長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int INSERT_USER_NA_MAX_LENGTH = 82;//作成者社員名の長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int UPDATE_USER_NA_MAX_LENGTH = 82;//更新者社員名の長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
//    ↓↓2006.06.22 zhujl カスタマイズ修正↓↓
	public static final int POS_KB_MAX_LENGTH = 1;//新旧POS区分
	public static final int BOTEN_CD_MAX_LENGTH = 3;//母店コード
	public static final int GYOTAI_KB_MAX_LENGTH = 2;//業態区分
//    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑

	private String tenpo_cd = null;	//店舗コード
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
	private String hojin_cd = null;	//法人コード
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	private String tenpokaiso_tiiki_cd = null;	//店舗階層(地域)
	private String tenpokaiso_area_cd = null;	//店舗階層(エリア)
	private String tenpokaiso_block_cd = null;	//店舗階層(ブロック)
	private String kanji_na = null;	//正式名称(漢字)
	private String kana_na = null;	//正式名称(カナ)
	private String kanji_rn = null;	//略式名称(漢字)
	private String kana_rn = null;	//略式名称(カナ)
	private String tenpo_type_kb = null;	//店舗タイプ
	private String tenpo_kb = null;	//店舗区分
	private String kaiten_dt = null;	//開店日
	private String heiten_dt = null;	//閉店日
	private String address_kanji_na = null;	//住所(漢字)
	private String address_kana_na = null;	//住所(カナ)
	private String address_3_na = null;	//住所３
	private String yubin_cd = null;	//郵便番号
	private String tel_cd = null;	//電話番号
	private String fax_cd = null;	//FAX番号
	private String hachu_st_dt = null;	//発注可能開始日
	private String hachu_ed_dt = null;	//発注可能終了日
	private String sndst_nehuda_dt = null;	//送信開始日：値札シール
	private String sndst_price_dt = null;	//送信開始日：プライスカード
	private String sndst_tag_dt = null;	//送信開始日：タグ
	private String sndst_plu_dt = null;	//送信開始日：ＰＬＵ
	private String sndst_pop_dt = null;	//送信開始日：ＰＯＰ
	private String sndst_keiryoki_dt = null;	//送信開始日：計量器
	private String hanku1_keiryoki_type = null;	//販区１計量器タイプ
	private String hanku2_keiryoki_type = null;	//販区２計量器タイプ
	private String hanku3_keiryoki_type = null;	//販区３計量器タイプ
	private String hanku4_keiryoki_type = null;	//販区４計量器タイプ
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
//    ↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
	private String pos_kb = null;	//新旧POS区分
	private String boten_cd = null;	//母店コード
	private String gyotai_kb = null;	//業態区分
	
	private String insert_user_na = null; 	//作成者社員名称
	private String update_user_na = null; 	//更新者社員名称
	private String back_kb  = null;         //戻る区分
//    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑

	private String processingDivision	= "0";				//処理区分
	private String errorFlg			= "";				//エラーフラグ
	private String errorMessage		= "";				//エラーメッセージ
	private String mode				= "";				//処理モード
	private String[] menuBar			= null;			//メニューバーアイテム
	private Map ctrlColor				= new HashMap();	//コントロール背景色
	private String firstFocus			= "";				//フォーカスを最初に取得するオブジェクト名
	private String insertFlg			= "";				//新規処理利用可能区分
	private String updateFlg			= "";				//更新処理利用可能区分
	private String deleteFlg			= "";				//削除処理利用可能区分
	private String referenceFlg		= "";				//照会処理利用可能区分
	private String kKanji_rn           = "";				//検索条件店舗名称

//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst530101_TenpoInit";		//初期画面JSPを取得
	private static final String EDIT_PAGE = "mst530201_TenpoEdit";		//新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst530301_TenpoView";		//削除・照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END

	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd()
	{
		return cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		return "'" + cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH));
	}
	
	
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setHojinCd(String hojin_cd)
	{
		this.hojin_cd = hojin_cd;
		return true;
	}
	public String getHojinCd()
	{
		return cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH);
	}
	public String getHojinCdString()
	{
		return "'" + cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH) + "'";
	}
	public String getHojinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH));
	}
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）


	// tenpokaiso_tiiki_cdに対するセッターとゲッターの集合
	public boolean setTenpokaisoTiikiCd(String tenpokaiso_tiiki_cd)
	{
		this.tenpokaiso_tiiki_cd = tenpokaiso_tiiki_cd;
		return true;
	}
	public String getTenpokaisoTiikiCd()
	{
		return cutString(this.tenpokaiso_tiiki_cd,TENPOKAISO_TIIKI_CD_MAX_LENGTH);
	}
	public String getTenpokaisoTiikiCdString()
	{
		return "'" + cutString(this.tenpokaiso_tiiki_cd,TENPOKAISO_TIIKI_CD_MAX_LENGTH) + "'";
	}
	public String getTenpokaisoTiikiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpokaiso_tiiki_cd,TENPOKAISO_TIIKI_CD_MAX_LENGTH));
	}


	// tenpokaiso_area_cdに対するセッターとゲッターの集合
	public boolean setTenpokaisoAreaCd(String tenpokaiso_area_cd)
	{
		this.tenpokaiso_area_cd = tenpokaiso_area_cd;
		return true;
	}
	public String getTenpokaisoAreaCd()
	{
		return cutString(this.tenpokaiso_area_cd,TENPOKAISO_AREA_CD_MAX_LENGTH);
	}
	public String getTenpokaisoAreaCdString()
	{
		return "'" + cutString(this.tenpokaiso_area_cd,TENPOKAISO_AREA_CD_MAX_LENGTH) + "'";
	}
	public String getTenpokaisoAreaCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpokaiso_area_cd,TENPOKAISO_AREA_CD_MAX_LENGTH));
	}


	// tenpokaiso_block_cdに対するセッターとゲッターの集合
	public boolean setTenpokaisoBlockCd(String tenpokaiso_block_cd)
	{
		this.tenpokaiso_block_cd = tenpokaiso_block_cd;
		return true;
	}
	public String getTenpokaisoBlockCd()
	{
		return cutString(this.tenpokaiso_block_cd,TENPOKAISO_BLOCK_CD_MAX_LENGTH);
	}
	public String getTenpokaisoBlockCdString()
	{
		return "'" + cutString(this.tenpokaiso_block_cd,TENPOKAISO_BLOCK_CD_MAX_LENGTH) + "'";
	}
	public String getTenpokaisoBlockCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpokaiso_block_cd,TENPOKAISO_BLOCK_CD_MAX_LENGTH));
	}


	// kanji_naに対するセッターとゲッターの集合
	public boolean setKanjiNa(String kanji_na)
	{
		this.kanji_na = kanji_na;
		return true;
	}
	public String getKanjiNa()
	{
		return cutString(this.kanji_na,KANJI_NA_MAX_LENGTH);
	}
	public String getKanjiNaString()
	{
		return "'" + cutString(this.kanji_na,KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na,KANJI_NA_MAX_LENGTH));
	}


	// kana_naに対するセッターとゲッターの集合
	public boolean setKanaNa(String kana_na)
	{
		this.kana_na = kana_na;
		return true;
	}
	public String getKanaNa()
	{
		return cutString(this.kana_na,KANA_NA_MAX_LENGTH);
	}
	public String getKanaNaString()
	{
		return "'" + cutString(this.kana_na,KANA_NA_MAX_LENGTH) + "'";
	}
	public String getKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kana_na,KANA_NA_MAX_LENGTH));
	}


	// kanji_rnに対するセッターとゲッターの集合
	public boolean setKanjiRn(String kanji_rn)
	{
		this.kanji_rn = kanji_rn;
		return true;
	}
	public String getKanjiRn()
	{
		return cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH);
	}
	public String getKanjiRnString()
	{
		return "'" + cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH));
	}


	// kana_rnに対するセッターとゲッターの集合
	public boolean setKanaRn(String kana_rn)
	{
		this.kana_rn = kana_rn;
		return true;
	}
	public String getKanaRn()
	{
		return cutString(this.kana_rn,KANA_RN_MAX_LENGTH);
	}
	public String getKanaRnString()
	{
		return "'" + cutString(this.kana_rn,KANA_RN_MAX_LENGTH) + "'";
	}
	public String getKanaRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kana_rn,KANA_RN_MAX_LENGTH));
	}


	// tenpo_type_kbに対するセッターとゲッターの集合
	public boolean setTenpoTypeKb(String tenpo_type_kb)
	{
		this.tenpo_type_kb = tenpo_type_kb;
		return true;
	}
	public String getTenpoTypeKb()
	{
		return cutString(this.tenpo_type_kb,TENPO_TYPE_KB_MAX_LENGTH);
	}
	public String getTenpoTypeKbString()
	{
		return "'" + cutString(this.tenpo_type_kb,TENPO_TYPE_KB_MAX_LENGTH) + "'";
	}
	public String getTenpoTypeKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_type_kb,TENPO_TYPE_KB_MAX_LENGTH));
	}


	// tenpo_kbに対するセッターとゲッターの集合
	public boolean setTenpoKb(String tenpo_kb)
	{
		this.tenpo_kb = tenpo_kb;
		return true;
	}
	public String getTenpoKb()
	{
		return cutString(this.tenpo_kb,TENPO_KB_MAX_LENGTH);
	}
	public String getTenpoKbString()
	{
		return "'" + cutString(this.tenpo_kb,TENPO_KB_MAX_LENGTH) + "'";
	}
	public String getTenpoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_kb,TENPO_KB_MAX_LENGTH));
	}


	// kaiten_dtに対するセッターとゲッターの集合
	public boolean setKaitenDt(String kaiten_dt)
	{
		this.kaiten_dt = kaiten_dt;
		return true;
	}
	public String getKaitenDt()
	{
		return cutString(this.kaiten_dt,KAITEN_DT_MAX_LENGTH);
	}
	public String getKaitenDtString()
	{
		return "'" + cutString(this.kaiten_dt,KAITEN_DT_MAX_LENGTH) + "'";
	}
	public String getKaitenDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kaiten_dt,KAITEN_DT_MAX_LENGTH));
	}


	// heiten_dtに対するセッターとゲッターの集合
	public boolean setHeitenDt(String heiten_dt)
	{
		this.heiten_dt = heiten_dt;
		return true;
	}
	public String getHeitenDt()
	{
		return cutString(this.heiten_dt,HEITEN_DT_MAX_LENGTH);
	}
	public String getHeitenDtString()
	{
		return "'" + cutString(this.heiten_dt,HEITEN_DT_MAX_LENGTH) + "'";
	}
	public String getHeitenDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.heiten_dt,HEITEN_DT_MAX_LENGTH));
	}


	// address_kanji_naに対するセッターとゲッターの集合
	public boolean setAddressKanjiNa(String address_kanji_na)
	{
		this.address_kanji_na = address_kanji_na;
		return true;
	}
	public String getAddressKanjiNa()
	{
		return cutString(this.address_kanji_na,ADDRESS_KANJI_NA_MAX_LENGTH);
	}
	public String getAddressKanjiNaString()
	{
		return "'" + cutString(this.address_kanji_na,ADDRESS_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getAddressKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_kanji_na,ADDRESS_KANJI_NA_MAX_LENGTH));
	}


	// address_kana_naに対するセッターとゲッターの集合
	public boolean setAddressKanaNa(String address_kana_na)
	{
		this.address_kana_na = address_kana_na;
		return true;
	}
	public String getAddressKanaNa()
	{
		return cutString(this.address_kana_na,ADDRESS_KANA_NA_MAX_LENGTH);
	}
	public String getAddressKanaNaString()
	{
		return "'" + cutString(this.address_kana_na,ADDRESS_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getAddressKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_kana_na,ADDRESS_KANA_NA_MAX_LENGTH));
	}


	// address_3_naに対するセッターとゲッターの集合
	public boolean setAddress3Na(String address_3_na)
	{
		this.address_3_na = address_3_na;
		return true;
	}
	public String getAddress3Na()
	{
		return cutString(this.address_3_na,ADDRESS_3_NA_MAX_LENGTH);
	}
	public String getAddress3NaString()
	{
		return "'" + cutString(this.address_3_na,ADDRESS_3_NA_MAX_LENGTH) + "'";
	}
	public String getAddress3NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_3_na,ADDRESS_3_NA_MAX_LENGTH));
	}


	// yubin_cdに対するセッターとゲッターの集合
	public boolean setYubinCd(String yubin_cd)
	{
		this.yubin_cd = yubin_cd;
		return true;
	}
	public String getYubinCd()
	{
		return cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH);
	}
	public String getYubinCdString()
	{
		return "'" + cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH) + "'";
	}
	public String getYubinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH));
	}


	// tel_cdに対するセッターとゲッターの集合
	public boolean setTelCd(String tel_cd)
	{
		this.tel_cd = tel_cd;
		return true;
	}
	public String getTelCd()
	{
		return cutString(this.tel_cd,TEL_CD_MAX_LENGTH);
	}
	public String getTelCdString()
	{
		return "'" + cutString(this.tel_cd,TEL_CD_MAX_LENGTH) + "'";
	}
	public String getTelCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tel_cd,TEL_CD_MAX_LENGTH));
	}


	// fax_cdに対するセッターとゲッターの集合
	public boolean setFaxCd(String fax_cd)
	{
		this.fax_cd = fax_cd;
		return true;
	}
	public String getFaxCd()
	{
		return cutString(this.fax_cd,FAX_CD_MAX_LENGTH);
	}
	public String getFaxCdString()
	{
		return "'" + cutString(this.fax_cd,FAX_CD_MAX_LENGTH) + "'";
	}
	public String getFaxCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fax_cd,FAX_CD_MAX_LENGTH));
	}


	// hachu_st_dtに対するセッターとゲッターの集合
	public boolean setHachuStDt(String hachu_st_dt)
	{
		this.hachu_st_dt = hachu_st_dt;
		return true;
	}
	public String getHachuStDt()
	{
		return cutString(this.hachu_st_dt,HACHU_ST_DT_MAX_LENGTH);
	}
	public String getHachuStDtString()
	{
		return "'" + cutString(this.hachu_st_dt,HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_st_dt,HACHU_ST_DT_MAX_LENGTH));
	}


	// hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setHachuEdDt(String hachu_ed_dt)
	{
		this.hachu_ed_dt = hachu_ed_dt;
		return true;
	}
	public String getHachuEdDt()
	{
		return cutString(this.hachu_ed_dt,HACHU_ED_DT_MAX_LENGTH);
	}
	public String getHachuEdDtString()
	{
		return "'" + cutString(this.hachu_ed_dt,HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_ed_dt,HACHU_ED_DT_MAX_LENGTH));
	}


	// sndst_nehuda_dtに対するセッターとゲッターの集合
	public boolean setSndstNehudaDt(String sndst_nehuda_dt)
	{
		this.sndst_nehuda_dt = sndst_nehuda_dt;
		return true;
	}
	public String getSndstNehudaDt()
	{
		return cutString(this.sndst_nehuda_dt,SNDST_NEHUDA_DT_MAX_LENGTH);
	}
	public String getSndstNehudaDtString()
	{
		return "'" + cutString(this.sndst_nehuda_dt,SNDST_NEHUDA_DT_MAX_LENGTH) + "'";
	}
	public String getSndstNehudaDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sndst_nehuda_dt,SNDST_NEHUDA_DT_MAX_LENGTH));
	}


	// sndst_price_dtに対するセッターとゲッターの集合
	public boolean setSndstPriceDt(String sndst_price_dt)
	{
		this.sndst_price_dt = sndst_price_dt;
		return true;
	}
	public String getSndstPriceDt()
	{
		return cutString(this.sndst_price_dt,SNDST_PRICE_DT_MAX_LENGTH);
	}
	public String getSndstPriceDtString()
	{
		return "'" + cutString(this.sndst_price_dt,SNDST_PRICE_DT_MAX_LENGTH) + "'";
	}
	public String getSndstPriceDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sndst_price_dt,SNDST_PRICE_DT_MAX_LENGTH));
	}


	// sndst_tag_dtに対するセッターとゲッターの集合
	public boolean setSndstTagDt(String sndst_tag_dt)
	{
		this.sndst_tag_dt = sndst_tag_dt;
		return true;
	}
	public String getSndstTagDt()
	{
		return cutString(this.sndst_tag_dt,SNDST_TAG_DT_MAX_LENGTH);
	}
	public String getSndstTagDtString()
	{
		return "'" + cutString(this.sndst_tag_dt,SNDST_TAG_DT_MAX_LENGTH) + "'";
	}
	public String getSndstTagDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sndst_tag_dt,SNDST_TAG_DT_MAX_LENGTH));
	}


	// sndst_plu_dtに対するセッターとゲッターの集合
	public boolean setSndstPluDt(String sndst_plu_dt)
	{
		this.sndst_plu_dt = sndst_plu_dt;
		return true;
	}
	public String getSndstPluDt()
	{
		return cutString(this.sndst_plu_dt,SNDST_PLU_DT_MAX_LENGTH);
	}
	public String getSndstPluDtString()
	{
		return "'" + cutString(this.sndst_plu_dt,SNDST_PLU_DT_MAX_LENGTH) + "'";
	}
	public String getSndstPluDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sndst_plu_dt,SNDST_PLU_DT_MAX_LENGTH));
	}


	// sndst_pop_dtに対するセッターとゲッターの集合
	public boolean setSndstPopDt(String sndst_pop_dt)
	{
		this.sndst_pop_dt = sndst_pop_dt;
		return true;
	}
	public String getSndstPopDt()
	{
		return cutString(this.sndst_pop_dt,SNDST_POP_DT_MAX_LENGTH);
	}
	public String getSndstPopDtString()
	{
		return "'" + cutString(this.sndst_pop_dt,SNDST_POP_DT_MAX_LENGTH) + "'";
	}
	public String getSndstPopDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sndst_pop_dt,SNDST_POP_DT_MAX_LENGTH));
	}


	// sndst_keiryoki_dtに対するセッターとゲッターの集合
	public boolean setSndstKeiryokiDt(String sndst_keiryoki_dt)
	{
		this.sndst_keiryoki_dt = sndst_keiryoki_dt;
		return true;
	}
	public String getSndstKeiryokiDt()
	{
		return cutString(this.sndst_keiryoki_dt,SNDST_KEIRYOKI_DT_MAX_LENGTH);
	}
	public String getSndstKeiryokiDtString()
	{
		return "'" + cutString(this.sndst_keiryoki_dt,SNDST_KEIRYOKI_DT_MAX_LENGTH) + "'";
	}
	public String getSndstKeiryokiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sndst_keiryoki_dt,SNDST_KEIRYOKI_DT_MAX_LENGTH));
	}


	// hanku1_keiryoki_typeに対するセッターとゲッターの集合
	public boolean setHanku1KeiryokiType(String hanku1_keiryoki_type)
	{
		this.hanku1_keiryoki_type = hanku1_keiryoki_type;
		return true;
	}
	public String getHanku1KeiryokiType()
	{
		return cutString(this.hanku1_keiryoki_type,HANKU1_KEIRYOKI_TYPE_MAX_LENGTH);
	}
	public String getHanku1KeiryokiTypeString()
	{
		return "'" + cutString(this.hanku1_keiryoki_type,HANKU1_KEIRYOKI_TYPE_MAX_LENGTH) + "'";
	}
	public String getHanku1KeiryokiTypeHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku1_keiryoki_type,HANKU1_KEIRYOKI_TYPE_MAX_LENGTH));
	}


	// hanku2_keiryoki_typeに対するセッターとゲッターの集合
	public boolean setHanku2KeiryokiType(String hanku2_keiryoki_type)
	{
		this.hanku2_keiryoki_type = hanku2_keiryoki_type;
		return true;
	}
	public String getHanku2KeiryokiType()
	{
		return cutString(this.hanku2_keiryoki_type,HANKU2_KEIRYOKI_TYPE_MAX_LENGTH);
	}
	public String getHanku2KeiryokiTypeString()
	{
		return "'" + cutString(this.hanku2_keiryoki_type,HANKU2_KEIRYOKI_TYPE_MAX_LENGTH) + "'";
	}
	public String getHanku2KeiryokiTypeHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku2_keiryoki_type,HANKU2_KEIRYOKI_TYPE_MAX_LENGTH));
	}


	// hanku3_keiryoki_typeに対するセッターとゲッターの集合
	public boolean setHanku3KeiryokiType(String hanku3_keiryoki_type)
	{
		this.hanku3_keiryoki_type = hanku3_keiryoki_type;
		return true;
	}
	public String getHanku3KeiryokiType()
	{
		return cutString(this.hanku3_keiryoki_type,HANKU3_KEIRYOKI_TYPE_MAX_LENGTH);
	}
	public String getHanku3KeiryokiTypeString()
	{
		return "'" + cutString(this.hanku3_keiryoki_type,HANKU3_KEIRYOKI_TYPE_MAX_LENGTH) + "'";
	}
	public String getHanku3KeiryokiTypeHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku3_keiryoki_type,HANKU3_KEIRYOKI_TYPE_MAX_LENGTH));
	}


	// hanku4_keiryoki_typeに対するセッターとゲッターの集合
	public boolean setHanku4KeiryokiType(String hanku4_keiryoki_type)
	{
		this.hanku4_keiryoki_type = hanku4_keiryoki_type;
		return true;
	}
	public String getHanku4KeiryokiType()
	{
		return cutString(this.hanku4_keiryoki_type,HANKU4_KEIRYOKI_TYPE_MAX_LENGTH);
	}
	public String getHanku4KeiryokiTypeString()
	{
		return "'" + cutString(this.hanku4_keiryoki_type,HANKU4_KEIRYOKI_TYPE_MAX_LENGTH) + "'";
	}
	public String getHanku4KeiryokiTypeHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku4_keiryoki_type,HANKU4_KEIRYOKI_TYPE_MAX_LENGTH));
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
		return HTMLStringUtil.convert(cutString(this.insert_ts, 8));
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

	//	 insert_user_naに対するセッターとゲッターの集合
	public boolean setInsertUserNa(String insert_user_na)
	{
		this.insert_user_na = insert_user_na;
		return true;
	}
	public String getInsertUserNa()
	{
		return cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH);
	}
	public String getInsertUserNaString()
	{
		return "'" + cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH) + "'";
	}
	public String getInsertUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH));
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
		return HTMLStringUtil.convert(cutString(this.update_ts, 8));
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
	
	// update_user_naに対するセッターとゲッターの集合
	public boolean setUpdateUserNa(String update_user_na)
	{
		this.update_user_na = update_user_na;
		return true;
	}
	public String getUpdateUserNa()
	{
		return cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH);
	}
	public String getUpdateUserNaString()
	{
		return "'" + cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH));
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
	
//	back_kbに対応するセッターとゲッターの集合
	public String  setBackKb(String back_kb)
	{
		return this.back_kb = back_kb;
	}

	public String getBackKb()
	{
	   return this.back_kb;
		
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

	// kKanji_rnに対するセッターとゲッターの集合
	public boolean setKKanjiRn(String kKanji_rn)
	{
		this.kKanji_rn = kKanji_rn;
		return true;
	}
	public String getKKanjiRn()
	{
		return this.kKanji_rn;
	}

// ↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
	// pos_kbに対するセッターとゲッターの集合
	public boolean setPosKb(String pos_kb)
	{
		this.pos_kb = pos_kb;
		return true;
	}
	public String getPosKb()
	{
		return cutString(this.pos_kb,POS_KB_MAX_LENGTH);
	}
	public String getPosKbString()
	{
		return "'" + cutString(this.pos_kb,POS_KB_MAX_LENGTH) + "'";
	}
	public String getPosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pos_kb,POS_KB_MAX_LENGTH));
	}
	
	// boten_cdに対するセッターとゲッターの集合
	public boolean setBotenCd(String boten_cd)
	{
		this.boten_cd = boten_cd;
		return true;
	}
	public String getBotenCd()
	{
		return cutString(this.boten_cd,BOTEN_CD_MAX_LENGTH);
	}
	public String getBotenCdString()
	{
		return "'" + cutString(this.boten_cd,BOTEN_CD_MAX_LENGTH) + "'";
	}
	public String getBotenCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.boten_cd,BOTEN_CD_MAX_LENGTH));
	}
	
	// gyotai_kbに対するセッターとゲッターの集合
	public boolean setGyotaiKb(String gyotai_kb)
	{
		this.gyotai_kb = gyotai_kb;
		return true;
	}
	public String getGyotaiKb()
	{
		return cutString(this.gyotai_kb,GYOTAI_KB_MAX_LENGTH);
	}
	public String getGyotaiKbString()
	{
		return "'" + cutString(this.gyotai_kb,GYOTAI_KB_MAX_LENGTH) + "'";
	}
	public String getGyotaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gyotai_kb,GYOTAI_KB_MAX_LENGTH));
	}
	
//	↑↑2006.06.22 zhujl カスタマイズ修正↑↑		
	
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

//BUGNO-S052 2005.05.14 SIRIUS START
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
//BUGNO-S052 2005.05.14 SIRIUS START
}
