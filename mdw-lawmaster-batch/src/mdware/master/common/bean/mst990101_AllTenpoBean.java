package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Tanigawa
 * @version 1.0(2006/10/18)初版作成
 */
public class mst990101_AllTenpoBean
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
	
//  ↓↓2006.06.22 zhujl カスタマイズ修正↓↓
	public static final int POS_KB_MAX_LENGTH = 1;//新旧POS区分
	public static final int BOTEN_CD_MAX_LENGTH = 3;//母店コード
	public static final int GYOTAI_KB_MAX_LENGTH = 2;//業態区分
//    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑
	
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	public static final int HANKU1_KEIRYOKI_TYPE_MAX_LENGTH = 1;//販区１計量器タイプの長さ
//	public static final int HANKU2_KEIRYOKI_TYPE_MAX_LENGTH = 1;//販区２計量器タイプの長さ
//	public static final int HANKU3_KEIRYOKI_TYPE_MAX_LENGTH = 1;//販区３計量器タイプの長さ
//	public static final int HANKU4_KEIRYOKI_TYPE_MAX_LENGTH = 1;//販区４計量器タイプの長さ
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

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
	
//  ↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
	private String pos_kb = null;	//新旧POS区分
	private String boten_cd = null;	//母店コード
	private String gyotai_kb = null;	//業態区分
	
	private String insert_user_na = null;	//作成者社員名称
	private String update_user_na = null;	//更新者社員名称
//    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑
	
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	private String hanku1_keiryoki_type = null;	//販区１計量器タイプ
//	private String hanku2_keiryoki_type = null;	//販区２計量器タイプ
//	private String hanku3_keiryoki_type = null;	//販区３計量器タイプ
//	private String hanku4_keiryoki_type = null;	//販区４計量器タイプ
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ

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
	 * RTENPOBeanを１件のみ抽出したい時に使用する
	 */
	public static mst990101_AllTenpoBean getRTENPOBean(DataHolder dataHolder)
	{
		mst990101_AllTenpoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst990101_AllTenpoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst990101_AllTenpoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




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

//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
	// hanku1_keiryoki_typeに対するセッターとゲッターの集合
//	public boolean setHanku1KeiryokiType(String hanku1_keiryoki_type)
//	{
//		this.hanku1_keiryoki_type = hanku1_keiryoki_type;
//		return true;
//	}
//	public String getHanku1KeiryokiType()
//	{
//		return cutString(this.hanku1_keiryoki_type,HANKU1_KEIRYOKI_TYPE_MAX_LENGTH);
//	}
//	public String getHanku1KeiryokiTypeString()
//	{
//		return "'" + cutString(this.hanku1_keiryoki_type,HANKU1_KEIRYOKI_TYPE_MAX_LENGTH) + "'";
//	}
//	public String getHanku1KeiryokiTypeHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku1_keiryoki_type,HANKU1_KEIRYOKI_TYPE_MAX_LENGTH));
//	}
//
//	// hanku2_keiryoki_typeに対するセッターとゲッターの集合
//	public boolean setHanku2KeiryokiType(String hanku2_keiryoki_type)
//	{
//		this.hanku2_keiryoki_type = hanku2_keiryoki_type;
//		return true;
//	}
//	public String getHanku2KeiryokiType()
//	{
//		return cutString(this.hanku2_keiryoki_type,HANKU2_KEIRYOKI_TYPE_MAX_LENGTH);
//	}
//	public String getHanku2KeiryokiTypeString()
//	{
//		return "'" + cutString(this.hanku2_keiryoki_type,HANKU2_KEIRYOKI_TYPE_MAX_LENGTH) + "'";
//	}
//	public String getHanku2KeiryokiTypeHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku2_keiryoki_type,HANKU2_KEIRYOKI_TYPE_MAX_LENGTH));
//	}
//
//
//	// hanku3_keiryoki_typeに対するセッターとゲッターの集合
//	public boolean setHanku3KeiryokiType(String hanku3_keiryoki_type)
//	{
//		this.hanku3_keiryoki_type = hanku3_keiryoki_type;
//		return true;
//	}
//	public String getHanku3KeiryokiType()
//	{
//		return cutString(this.hanku3_keiryoki_type,HANKU3_KEIRYOKI_TYPE_MAX_LENGTH);
//	}
//	public String getHanku3KeiryokiTypeString()
//	{
//		return "'" + cutString(this.hanku3_keiryoki_type,HANKU3_KEIRYOKI_TYPE_MAX_LENGTH) + "'";
//	}
//	public String getHanku3KeiryokiTypeHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku3_keiryoki_type,HANKU3_KEIRYOKI_TYPE_MAX_LENGTH));
//	}
//
//	// hanku4_keiryoki_typeに対するセッターとゲッターの集合
//	public boolean setHanku4KeiryokiType(String hanku4_keiryoki_type)
//	{
//		this.hanku4_keiryoki_type = hanku4_keiryoki_type;
//		return true;
//	}
//	public String getHanku4KeiryokiType()
//	{
//		return cutString(this.hanku4_keiryoki_type,HANKU4_KEIRYOKI_TYPE_MAX_LENGTH);
//	}
//	public String getHanku4KeiryokiTypeString()
//	{
//		return "'" + cutString(this.hanku4_keiryoki_type,HANKU4_KEIRYOKI_TYPE_MAX_LENGTH) + "'";
//	}
//	public String getHanku4KeiryokiTypeHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku4_keiryoki_type,HANKU4_KEIRYOKI_TYPE_MAX_LENGTH));
//	}
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）

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
//    ↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
	// insert_user_naに対するセッターとゲッターの集合
	public boolean setInsertUserNa(String insert_user_id)
	{
		this.insert_user_na = insert_user_na;
		return true;
	}
	public String getInsertUserNa()
	{
		return this.insert_user_na;
	}
	public String getInsertUserNaString()
	{
		return "'" + this.insert_user_na + "'";
	}
	public String getInsertUserNaHTMLString()
	{
		return HTMLStringUtil.convert(this.insert_user_na);
	}
//    ↑↑2006.06.22 zhujl カスタマイズ修正↑↑	
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
//  ↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
	// update_user_naに対するセッターとゲッターの集合
	public boolean setUpdateUserNa(String update_user_na)
	{
		this.update_user_na = update_user_na;
		return true;
	}
	public String getUpdateUserNa()
	{
		return this.update_user_na;
	}
	public String getUpdateUserNaString()
	{
		return "'" + this.update_user_na + "'";
	}
	public String getUpdateUserNaHTMLString()
	{
		return HTMLStringUtil.convert(this.update_user_na);
	}
//  ↑↑2006.06.22 zhujl カスタマイズ修正↑↑	

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
	
//	 ↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
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
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenpo_cd = " + getTenpoCdString()
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
			+ "  hojin_cd = " + getHojinCd()
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）			
			+ "  tenpokaiso_tiiki_cd = " + getTenpokaisoTiikiCdString()
			+ "  tenpokaiso_area_cd = " + getTenpokaisoAreaCdString()
			+ "  tenpokaiso_block_cd = " + getTenpokaisoBlockCdString()
			+ "  kanji_na = " + getKanjiNaString()
			+ "  kana_na = " + getKanaNaString()
			+ "  kanji_rn = " + getKanjiRnString()
			+ "  kana_rn = " + getKanaRnString()
			+ "  tenpo_type_kb = " + getTenpoTypeKbString()
			+ "  tenpo_kb = " + getTenpoKbString()
			+ "  kaiten_dt = " + getKaitenDtString()
			+ "  heiten_dt = " + getHeitenDtString()
			+ "  address_kanji_na = " + getAddressKanjiNaString()
			+ "  address_kana_na = " + getAddressKanaNaString()
			+ "  address_3_na = " + getAddress3NaString()
			+ "  yubin_cd = " + getYubinCdString()
			+ "  tel_cd = " + getTelCdString()
			+ "  fax_cd = " + getFaxCdString()
			+ "  hachu_st_dt = " + getHachuStDtString()
			+ "  hachu_ed_dt = " + getHachuEdDtString()
			+ "  sndst_nehuda_dt = " + getSndstNehudaDtString()
			+ "  sndst_price_dt = " + getSndstPriceDtString()
			+ "  sndst_tag_dt = " + getSndstTagDtString()
			+ "  sndst_plu_dt = " + getSndstPluDtString()
			+ "  sndst_pop_dt = " + getSndstPopDtString()
			+ "  sndst_keiryoki_dt = " + getSndstKeiryokiDtString()
//	 ↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
			+ "  pos_kb = " + getPosKbString()
			+ "  boten_cd = " + getBotenCdString()
			+ "  gyotai_kb = " + getGyotaiKbString()
//	↑↑2006.06.22 zhujl カスタマイズ修正↑↑			
			
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			+ "  hanku1_keiryoki_type = " + getHanku1KeiryokiTypeString()
//			+ "  hanku2_keiryoki_type = " + getHanku2KeiryokiTypeString()
//			+ "  hanku3_keiryoki_type = " + getHanku3KeiryokiTypeString()
//			+ "  hanku4_keiryoki_type = " + getHanku4KeiryokiTypeString()
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RTENPOBean コピー後のクラス
	 */
	public mst990101_AllTenpoBean createClone()
	{
		mst990101_AllTenpoBean bean = new mst990101_AllTenpoBean();
		bean.setTenpoCd(this.tenpo_cd);
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
		bean.setHojinCd(this.hojin_cd);
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		bean.setTenpokaisoTiikiCd(this.tenpokaiso_tiiki_cd);
		bean.setTenpokaisoAreaCd(this.tenpokaiso_area_cd);
		bean.setTenpokaisoBlockCd(this.tenpokaiso_block_cd);
		bean.setKanjiNa(this.kanji_na);
		bean.setKanaNa(this.kana_na);
		bean.setKanjiRn(this.kanji_rn);
		bean.setKanaRn(this.kana_rn);
		bean.setTenpoTypeKb(this.tenpo_type_kb);
		bean.setTenpoKb(this.tenpo_kb);
		bean.setKaitenDt(this.kaiten_dt);
		bean.setHeitenDt(this.heiten_dt);
		bean.setAddressKanjiNa(this.address_kanji_na);
		bean.setAddressKanaNa(this.address_kana_na);
		bean.setAddress3Na(this.address_3_na);
		bean.setYubinCd(this.yubin_cd);
		bean.setTelCd(this.tel_cd);
		bean.setFaxCd(this.fax_cd);
		bean.setHachuStDt(this.hachu_st_dt);
		bean.setHachuEdDt(this.hachu_ed_dt);
		bean.setSndstNehudaDt(this.sndst_nehuda_dt);
		bean.setSndstPriceDt(this.sndst_price_dt);
		bean.setSndstTagDt(this.sndst_tag_dt);
		bean.setSndstPluDt(this.sndst_plu_dt);
		bean.setSndstPopDt(this.sndst_pop_dt);
		bean.setSndstKeiryokiDt(this.sndst_keiryoki_dt);
//		 ↓↓2006.06.22 zhujl カスタマイズ修正↓↓	
		bean.setPosKb(this.pos_kb);
		bean.setBotenCd(this.boten_cd);
		bean.setGyotaiKb(this.gyotai_kb);
//		↑↑2006.06.22 zhujl カスタマイズ修正↑↑	
		
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setHanku1KeiryokiType(this.hanku1_keiryoki_type);
//		bean.setHanku2KeiryokiType(this.hanku2_keiryoki_type);
//		bean.setHanku3KeiryokiType(this.hanku3_keiryoki_type);
//		bean.setHanku4KeiryokiType(this.hanku4_keiryoki_type);
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
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
		if( !( o instanceof mst990101_AllTenpoBean ) ) return false;
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
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}
}
