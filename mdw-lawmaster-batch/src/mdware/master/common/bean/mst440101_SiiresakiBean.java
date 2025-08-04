/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/24)初版作成
 * @version 1.1(2006/03/29)課金種別コード　追加 JINNO
 * @version 1.2(2006/09/24)障害票№0036対応 仕入先システム区分に業種に応じた値を登録するため、仕入先システム区分追加 K.Tanigawa
 */
public class mst440101_SiiresakiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	public static final int KANRI_KB_MAX_LENGTH = 1;//管理区分の長さ
//	public static final int KANRI_CD_MAX_LENGTH = 4;//管理コードの長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;//部門コードの長さ
	public static final int TIKU_CD_MAX_LENGTH = 3;//地区コードの長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;//仕入先コードの長さ
	public static final int KANJI_NA_MAX_LENGTH = 80;//正式名称(漢字)の長さ
////	↓↓仕様変更（2005.08.18）↓↓
////	public static final int KANA_NA_MAX_LENGTH = 80;//正式名称(カナ)の長さ
//	public static final int KANA_NA_MAX_LENGTH = 18;//正式名称(カナ)の長さ
////	↑↑仕様変更（2005.08.18）↑↑
	public static final int KANA_NA_MAX_LENGTH = 20;//正式名称(カナ)の長さ
	public static final int KANJI_RN_MAX_LENGTH = 40;//略式名称(漢字)の長さ
//	↓↓仕様変更（2005.08.18）↓↓
//	public static final int KANA_RN_MAX_LENGTH = 20;//略式名称(カナ)の長さ
//	public static final int KANA_RN_MAX_LENGTH = 8;//略式名称(カナ)の長さ
//	↑↑仕様変更（2005.08.18）↑↑
	public static final int KANA_RN_MAX_LENGTH = 20;//略式名称(カナ)の長さ
//	public static final int YUBIN_CD_MAX_LENGTH = 7;//郵便番号の長さ
	public static final int YUBIN_CD_MAX_LENGTH = 8;//郵便番号の長さ
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	public static final int ADDRESS_KANJI1_NA_MAX_LENGTH = 100;//住所(漢字)1の長さ
	public static final int ADDRESS_KANJI2_NA_MAX_LENGTH = 100;//住所(漢字)2の長さ
	public static final int ADDRESS_KANA1_NA_MAX_LENGTH = 25;//住所(カナ)1の長さ
	public static final int ADDRESS_KANA2_NA_MAX_LENGTH = 25;//住所(カナ)2の長さ
	public static final int MADOGUCHI_TANTO_NA_MAX_LENGTH = 10;//窓口担当者の長さ
	public static final int TEL1_NA_MAX_LENGTH = 20;//通常連絡先TEL1の長さ
	public static final int TEL2_NA_MAX_LENGTH = 20;//通常連絡先TEL2の長さ
	public static final int FAX1_NA_MAX_LENGTH = 20;//通常連絡先FAX1の長さ
	public static final int FAX2_NA_MAX_LENGTH = 20;//通常連絡先FAX2の長さ
	public static final int NIGHT_TEL_NA_MAX_LENGTH = 20;//夜間連絡先TEL(緊急)の長さ
	public static final int EMAIL_NA_MAX_LENGTH = 50;//E-MAILの長さ
	public static final int JOHOSYORI_SEIKYU_KB_MAX_LENGTH = 1;//情報処理料金請求区分の長さ
	public static final int SYHINCD_PRINT_KB_MAX_LENGTH = 1;//商品コード印字区分の長さ
	public static final int TAGHAKO_GYOSYA_KB_MAX_LENGTH = 3;//タグ発行代行業者の長さ
//	↓↓削除（2005.06.30）↓↓
//	public static final int HAKO_BASYO_NA_MAX_LENGTH = 2;//発行場所の長さ
//	↑↑削除（2005.06.30）↑↑
	public static final int HINBANBETU_DENPYO_KB_MAX_LENGTH = 1;//品番別伝票区分の長さ
//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	public static final int MEMO_TX_MAX_LENGTH = 30;//メモ欄の長さ
	public static final int MEMO_TX_MAX_LENGTH = 60;//メモ欄の長さ
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	public static final int TOSAN_KB_MAX_LENGTH = 1;//倒産区分の長さ
//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 14;//作成年月日の長さ
//	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;//作成者社員IDの長さ
//	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_TS_MAX_LENGTH = 14;//更新年月日の長さ
//	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 20;//更新者社員IDの長さ
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
	
	//↓↓仕様追加による変更（2005.05.24）
	public static final int EOS_KB_MAX_LENGTH = 1;//EOS区分の長さ
	//↑↑仕様追加による変更（2005.05.24）

//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//	public static final int KAKIN_SYUBETU_CD_MAX_LENGTH = 4; //課金種別コードの長さ
//	↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
	public static final int ASTY_KB_MAX_LENGTH = 1; //アスティ区分の長さ
	public static final int HANBAI_KEIYAKU_KB_MAX_LENGTH = 1; //販売データ契約区分の長さ
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

	//ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分追加 START
	public static final int SIIRE_SYSTEM_KB_MAX_LENGTH = 1; //仕入先システム区分
	//ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分追加 END 

	
//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	private String kanri_kb = null;	//管理区分
//	private String kanri_cd = null;	//管理コード
	private String kanri_cd = null;	//部門コード
	private String area_cd = null;	//地区コード
	private String siiresaki_cd = null;	//仕入先コード
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	private String kanji_na = null;	//正式名称(漢字)
	private String kana_na = null;	//正式名称(カナ)
	private String kanji_rn = null;	//略式名称(漢字)
	private String kana_rn = null;	//略式名称(カナ)
	private String yubin_cd = null;	//郵便番号
	private String address_kanji1_na = null;	//住所(漢字)1
	private String address_kanji2_na = null;	//住所(漢字)2
	private String address_kana1_na = null;	//住所(カナ)1
	private String address_kana2_na = null;	//住所(カナ)2
	private String madoguchi_tanto_na = null;	//窓口担当者
	private String tel1_na = null;	//通常連絡先TEL1
	private String tel2_na = null;	//通常連絡先TEL2
	private String fax1_na = null;	//通常連絡先FAX1
	private String fax2_na = null;	//通常連絡先FAX2
	private String night_tel_na = null;	//夜間連絡先TEL(緊急)
	private String email_na = null;	//E-MAIL
	private String johosyori_seikyu_kb = null;	//情報処理料金請求区分
	private String syhincd_print_kb = null;	//商品コード印字区分
	private String taghako_gyosya_kb = null;	//タグ発行代行業者
//	↓↓削除（2005.06.30）↓↓
//	private String hako_basyo_na = null;	//発行場所
//	↑↑削除（2005.06.30）↑↑
	private String hinbanbetu_denpyo_kb = null;	//品番別伝票区分
	private String memo_tx = null;	//メモ欄
	private String tosan_kb = null;	//倒産区分
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	
	//↓↓仕様追加による変更（2005.05.24）
	private String eos_kb = null;//EOS区分
	//↑↑仕様追加による変更（2005.05.24）

//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//	private String kakin_syubetu_cd = null; //課金種別コードの長さ
//	↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
	private String asty_kb = null; //アスティ区分の長さ
	private String hanbai_keiyaku_kb = null; //販売データ契約区分の長さ
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

	//ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分追加 START
	private String siire_system_kb = null; //仕入先システム区分
	//ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分追加 END 
	
	
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
	 * mst440101_SiiresakiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst440101_SiiresakiBean getmst440101_SiiresakiBean(DataHolder dataHolder)
	{
		mst440101_SiiresakiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst440101_SiiresakiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst440101_SiiresakiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}



//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
	// kanri_kbに対するセッターとゲッターの集合
//	public boolean setKanriKb(String kanri_kb)
//	{
//		this.kanri_kb = kanri_kb;
//		return true;
//	}
//	public String getKanriKb()
//	{
//		return cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH);
//	}
//	public String getKanriKbString()
//	{
//		return "'" + cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH) + "'";
//	}
//	public String getKanriKbHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH));
//	}
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

	
//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	// kanri_cdに対するセッターとゲッターの集合
//	public boolean setKanriCd(String kanri_cd)
//	{
//		this.kanri_cd = kanri_cd;
//		return true;
//	}
//	public String getKanriCd()
//	{
//		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
//	}
//	public String getKanriCdString()
//	{
//		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
//	}
//	public String getKanriCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
//	}
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	

//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	// kanri_cdに対するセッターとゲッターの集合
	public boolean setBumonCd(String kanri_cd)
	{
		this.kanri_cd = kanri_cd;
		return true;
	}
	public String getBumonCd()
	{
		return cutString(this.kanri_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString()
	{
		return "'" + cutString(this.kanri_cd,BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_cd,BUMON_CD_MAX_LENGTH));
	}
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑


//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
	// area_cdに対するセッターとゲッターの集合
	public boolean setTikuCd(String area_cd)
	{
		this.area_cd = area_cd;
		return true;
	}
	public String getTikuCd()
	{
		return cutString(this.area_cd,TIKU_CD_MAX_LENGTH);
	}
	public String getTikuCdString()
	{
		return "'" + cutString(this.area_cd,TIKU_CD_MAX_LENGTH) + "'";
	}
	public String getTikuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.area_cd,TIKU_CD_MAX_LENGTH));
	}
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	
	
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


	// address_kanji1_naに対するセッターとゲッターの集合
	public boolean setAddressKanji1Na(String address_kanji1_na)
	{
		this.address_kanji1_na = address_kanji1_na;
		return true;
	}
	public String getAddressKanji1Na()
	{
		return cutString(this.address_kanji1_na,ADDRESS_KANJI1_NA_MAX_LENGTH);
	}
	public String getAddressKanji1NaString()
	{
		return "'" + cutString(this.address_kanji1_na,ADDRESS_KANJI1_NA_MAX_LENGTH) + "'";
	}
	public String getAddressKanji1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_kanji1_na,ADDRESS_KANJI1_NA_MAX_LENGTH));
	}


	// address_kanji2_naに対するセッターとゲッターの集合
	public boolean setAddressKanji2Na(String address_kanji2_na)
	{
		this.address_kanji2_na = address_kanji2_na;
		return true;
	}
	public String getAddressKanji2Na()
	{
		return cutString(this.address_kanji2_na,ADDRESS_KANJI2_NA_MAX_LENGTH);
	}
	public String getAddressKanji2NaString()
	{
		return "'" + cutString(this.address_kanji2_na,ADDRESS_KANJI2_NA_MAX_LENGTH) + "'";
	}
	public String getAddressKanji2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_kanji2_na,ADDRESS_KANJI2_NA_MAX_LENGTH));
	}


	// address_kana1_naに対するセッターとゲッターの集合
	public boolean setAddressKana1Na(String address_kana1_na)
	{
		this.address_kana1_na = address_kana1_na;
		return true;
	}
	public String getAddressKana1Na()
	{
		return cutString(this.address_kana1_na,ADDRESS_KANA1_NA_MAX_LENGTH);
	}
	public String getAddressKana1NaString()
	{
		return "'" + cutString(this.address_kana1_na,ADDRESS_KANA1_NA_MAX_LENGTH) + "'";
	}
	public String getAddressKana1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_kana1_na,ADDRESS_KANA1_NA_MAX_LENGTH));
	}


	// address_kana2_naに対するセッターとゲッターの集合
	public boolean setAddressKana2Na(String address_kana2_na)
	{
		this.address_kana2_na = address_kana2_na;
		return true;
	}
	public String getAddressKana2Na()
	{
		return cutString(this.address_kana2_na,ADDRESS_KANA2_NA_MAX_LENGTH);
	}
	public String getAddressKana2NaString()
	{
		return "'" + cutString(this.address_kana2_na,ADDRESS_KANA2_NA_MAX_LENGTH) + "'";
	}
	public String getAddressKana2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_kana2_na,ADDRESS_KANA2_NA_MAX_LENGTH));
	}


	// madoguchi_tanto_naに対するセッターとゲッターの集合
	public boolean setMadoguchiTantoNa(String madoguchi_tanto_na)
	{
		this.madoguchi_tanto_na = madoguchi_tanto_na;
		return true;
	}
	public String getMadoguchiTantoNa()
	{
		return cutString(this.madoguchi_tanto_na,MADOGUCHI_TANTO_NA_MAX_LENGTH);
	}
	public String getMadoguchiTantoNaString()
	{
		return "'" + cutString(this.madoguchi_tanto_na,MADOGUCHI_TANTO_NA_MAX_LENGTH) + "'";
	}
	public String getMadoguchiTantoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.madoguchi_tanto_na,MADOGUCHI_TANTO_NA_MAX_LENGTH));
	}


	// tel1_naに対するセッターとゲッターの集合
	public boolean setTel1Na(String tel1_na)
	{
		this.tel1_na = tel1_na;
		return true;
	}
	public String getTel1Na()
	{
		return cutString(this.tel1_na,TEL1_NA_MAX_LENGTH);
	}
	public String getTel1NaString()
	{
		return "'" + cutString(this.tel1_na,TEL1_NA_MAX_LENGTH) + "'";
	}
	public String getTel1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tel1_na,TEL1_NA_MAX_LENGTH));
	}


	// tel2_naに対するセッターとゲッターの集合
	public boolean setTel2Na(String tel2_na)
	{
		this.tel2_na = tel2_na;
		return true;
	}
	public String getTel2Na()
	{
		return cutString(this.tel2_na,TEL2_NA_MAX_LENGTH);
	}
	public String getTel2NaString()
	{
		return "'" + cutString(this.tel2_na,TEL2_NA_MAX_LENGTH) + "'";
	}
	public String getTel2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tel2_na,TEL2_NA_MAX_LENGTH));
	}


	// fax1_naに対するセッターとゲッターの集合
	public boolean setFax1Na(String fax1_na)
	{
		this.fax1_na = fax1_na;
		return true;
	}
	public String getFax1Na()
	{
		return cutString(this.fax1_na,FAX1_NA_MAX_LENGTH);
	}
	public String getFax1NaString()
	{
		return "'" + cutString(this.fax1_na,FAX1_NA_MAX_LENGTH) + "'";
	}
	public String getFax1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fax1_na,FAX1_NA_MAX_LENGTH));
	}


	// fax2_naに対するセッターとゲッターの集合
	public boolean setFax2Na(String fax2_na)
	{
		this.fax2_na = fax2_na;
		return true;
	}
	public String getFax2Na()
	{
		return cutString(this.fax2_na,FAX2_NA_MAX_LENGTH);
	}
	public String getFax2NaString()
	{
		return "'" + cutString(this.fax2_na,FAX2_NA_MAX_LENGTH) + "'";
	}
	public String getFax2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fax2_na,FAX2_NA_MAX_LENGTH));
	}


	// night_tel_naに対するセッターとゲッターの集合
	public boolean setNightTelNa(String night_tel_na)
	{
		this.night_tel_na = night_tel_na;
		return true;
	}
	public String getNightTelNa()
	{
		return cutString(this.night_tel_na,NIGHT_TEL_NA_MAX_LENGTH);
	}
	public String getNightTelNaString()
	{
		return "'" + cutString(this.night_tel_na,NIGHT_TEL_NA_MAX_LENGTH) + "'";
	}
	public String getNightTelNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.night_tel_na,NIGHT_TEL_NA_MAX_LENGTH));
	}


	// email_naに対するセッターとゲッターの集合
	public boolean setEmailNa(String email_na)
	{
		this.email_na = email_na;
		return true;
	}
	public String getEmailNa()
	{
		return cutString(this.email_na,EMAIL_NA_MAX_LENGTH);
	}
	public String getEmailNaString()
	{
		return "'" + cutString(this.email_na,EMAIL_NA_MAX_LENGTH) + "'";
	}
	public String getEmailNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.email_na,EMAIL_NA_MAX_LENGTH));
	}


	// johosyori_seikyu_kbに対するセッターとゲッターの集合
	public boolean setJohosyoriSeikyuKb(String johosyori_seikyu_kb)
	{
		this.johosyori_seikyu_kb = johosyori_seikyu_kb;
		return true;
	}
	public String getJohosyoriSeikyuKb()
	{
		return cutString(this.johosyori_seikyu_kb,JOHOSYORI_SEIKYU_KB_MAX_LENGTH);
	}
	public String getJohosyoriSeikyuKbString()
	{
		return "'" + cutString(this.johosyori_seikyu_kb,JOHOSYORI_SEIKYU_KB_MAX_LENGTH) + "'";
	}
	public String getJohosyoriSeikyuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.johosyori_seikyu_kb,JOHOSYORI_SEIKYU_KB_MAX_LENGTH));
	}


	// syhincd_print_kbに対するセッターとゲッターの集合
	public boolean setSyhincdPrintKb(String syhincd_print_kb)
	{
		this.syhincd_print_kb = syhincd_print_kb;
		return true;
	}
	public String getSyhincdPrintKb()
	{
		return cutString(this.syhincd_print_kb,SYHINCD_PRINT_KB_MAX_LENGTH);
	}
	public String getSyhincdPrintKbString()
	{
		return "'" + cutString(this.syhincd_print_kb,SYHINCD_PRINT_KB_MAX_LENGTH) + "'";
	}
	public String getSyhincdPrintKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syhincd_print_kb,SYHINCD_PRINT_KB_MAX_LENGTH));
	}


	// taghako_gyosya_kbに対するセッターとゲッターの集合
	public boolean setTaghakoGyosyaKb(String taghako_gyosya_kb)
	{
		this.taghako_gyosya_kb = taghako_gyosya_kb;
		return true;
	}
	public String getTaghakoGyosyaKb()
	{
		return cutString(this.taghako_gyosya_kb,TAGHAKO_GYOSYA_KB_MAX_LENGTH);
	}
	public String getTaghakoGyosyaKbString()
	{
		return "'" + cutString(this.taghako_gyosya_kb,TAGHAKO_GYOSYA_KB_MAX_LENGTH) + "'";
	}
	public String getTaghakoGyosyaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.taghako_gyosya_kb,TAGHAKO_GYOSYA_KB_MAX_LENGTH));
	}


//	↓↓削除（2005.06.30）↓↓
	// hako_basyo_naに対するセッターとゲッターの集合
//	public boolean setHakoBasyoNa(String hako_basyo_na)
//	{
//		this.hako_basyo_na = hako_basyo_na;
//		return true;
//	}
//	public String getHakoBasyoNa()
//	{
//		return cutString(this.hako_basyo_na,HAKO_BASYO_NA_MAX_LENGTH);
//	}
//	public String getHakoBasyoNaString()
//	{
//		return "'" + cutString(this.hako_basyo_na,HAKO_BASYO_NA_MAX_LENGTH) + "'";
//	}
//	public String getHakoBasyoNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hako_basyo_na,HAKO_BASYO_NA_MAX_LENGTH));
//	}
//	↑↑削除（2005.06.30）↑↑

	// hinbanbetu_denpyo_kbに対するセッターとゲッターの集合
	public boolean setHinbanbetuDenpyoKb(String hinbanbetu_denpyo_kb)
	{
		this.hinbanbetu_denpyo_kb = hinbanbetu_denpyo_kb;
		return true;
	}
	public String getHinbanbetuDenpyoKb()
	{
		return cutString(this.hinbanbetu_denpyo_kb,HINBANBETU_DENPYO_KB_MAX_LENGTH);
	}
	public String getHinbanbetuDenpyoKbString()
	{
		return "'" + cutString(this.hinbanbetu_denpyo_kb,HINBANBETU_DENPYO_KB_MAX_LENGTH) + "'";
	}
	public String getHinbanbetuDenpyoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinbanbetu_denpyo_kb,HINBANBETU_DENPYO_KB_MAX_LENGTH));
	}


	// memo_txに対するセッターとゲッターの集合
	public boolean setMemoTx(String memo_tx)
	{
		this.memo_tx = memo_tx;
		return true;
	}
	public String getMemoTx()
	{
		return cutString(this.memo_tx,MEMO_TX_MAX_LENGTH);
	}
	public String getMemoTxString()
	{
		return "'" + cutString(this.memo_tx,MEMO_TX_MAX_LENGTH) + "'";
	}
	public String getMemoTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.memo_tx,MEMO_TX_MAX_LENGTH));
	}


	// tosan_kbに対するセッターとゲッターの集合
	public boolean setTosanKb(String tosan_kb)
	{
		this.tosan_kb = tosan_kb;
		return true;
	}
	public String getTosanKb()
	{
		return cutString(this.tosan_kb,TOSAN_KB_MAX_LENGTH);
	}
	public String getTosanKbString()
	{
		return "'" + cutString(this.tosan_kb,TOSAN_KB_MAX_LENGTH) + "'";
	}
	public String getTosanKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tosan_kb,TOSAN_KB_MAX_LENGTH));
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
	
	
	//↓↓仕様追加による変更（2005.05.24）
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
	//↑↑仕様追加による変更（2005.05.24）
	
//	↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
//	//　↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//	// kakin_syubetu_cdに対するセッターとゲッターの集合
//	public boolean setKakinSyubetuCd(String kakin_syubetu_cd)
//	{
//		this.kakin_syubetu_cd = kakin_syubetu_cd;
//		return true;
//	}
//	public String getKakinSyubetuCd()
//	{
//		return cutString(this.kakin_syubetu_cd,KAKIN_SYUBETU_CD_MAX_LENGTH);
//	}
//	public String getKakinSyubetuCdString()
//	{
//		return "'" + cutString(this.kakin_syubetu_cd,KAKIN_SYUBETU_CD_MAX_LENGTH) + "'";
//	}
//	public String getKakinSyubetuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kakin_syubetu_cd,KAKIN_SYUBETU_CD_MAX_LENGTH));
//	}
//	//　↑↑課金種別コード　追加 JINNO (2006.03.29) ↓↓
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//	↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
	// asty_kbに対するセッターとゲッターの集合
	public boolean setAstyKb(String asty_kb)
	{
		this.asty_kb = asty_kb;
		return true;
	}
	public String getAstyKb()
	{
		return cutString(this.asty_kb,ASTY_KB_MAX_LENGTH);
	}
	public String getAstyKbString()
	{
		return "'" + cutString(this.asty_kb,ASTY_KB_MAX_LENGTH) + "'";
	}
	public String getAstyKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.asty_kb,ASTY_KB_MAX_LENGTH));
	}
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	
//	↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
	// hanbai_keiyaku_kbに対するセッターとゲッターの集合
	public boolean setHanbaiKeiyakuKb(String hanbai_keiyaku_kb)
	{
		this.hanbai_keiyaku_kb = hanbai_keiyaku_kb;
		return true;
	}
	public String getHanbaiKeiyakuKb()
	{
		return cutString(this.hanbai_keiyaku_kb,HANBAI_KEIYAKU_KB_MAX_LENGTH);
	}
	public String getHanbaiKeiyakuKbString()
	{
		return "'" + cutString(this.hanbai_keiyaku_kb,HANBAI_KEIYAKU_KB_MAX_LENGTH) + "'";
	}
	public String getHanbaiKeiyakuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_keiyaku_kb,HANBAI_KEIYAKU_KB_MAX_LENGTH));
	}
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	

	//ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分追加 START
	// siire_system_kbに対するセッターとゲッターの集合
	public boolean setSiireSystemKb(String siire_system_kb)
	{
		this.siire_system_kb = siire_system_kb;
		return true;
	}
	public String getSiireSystemKb()
	{
		return cutString(this.siire_system_kb,SIIRE_SYSTEM_KB_MAX_LENGTH);
	}
	public String getSiireSystemKbString()
	{
		return "'" + cutString(this.siire_system_kb,SIIRE_SYSTEM_KB_MAX_LENGTH) + "'";
	}
	public String getSiireSystemKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siire_system_kb,SIIRE_SYSTEM_KB_MAX_LENGTH));
	}
	//ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分追加 END 

	
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//	   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		return "  kanri_kb = " + getKanriKbString()
		return "  kanri_cd = " + getBumonCdString()
//    ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
			+ "  siiresaki_cd = " + getSiiresakiCdString() + getTikuCdString()
			+ "  kanji_na = " + getKanjiNaString()
			+ "  kana_na = " + getKanaNaString()
			+ "  kanji_rn = " + getKanjiRnString()
			+ "  kana_rn = " + getKanaRnString()
			+ "  yubin_cd = " + getYubinCdString()
			+ "  address_kanji1_na = " + getAddressKanji1NaString()
			+ "  address_kanji2_na = " + getAddressKanji2NaString()
			+ "  address_kana1_na = " + getAddressKana1NaString()
			+ "  address_kana2_na = " + getAddressKana2NaString()
			+ "  madoguchi_tanto_na = " + getMadoguchiTantoNaString()
			+ "  tel1_na = " + getTel1NaString()
			+ "  tel2_na = " + getTel2NaString()
			+ "  fax1_na = " + getFax1NaString()
			+ "  fax2_na = " + getFax2NaString()
			+ "  night_tel_na = " + getNightTelNaString()
			+ "  email_na = " + getEmailNaString()
			+ "  johosyori_seikyu_kb = " + getJohosyoriSeikyuKbString()
			+ "  syhincd_print_kb = " + getSyhincdPrintKbString()
			+ "  taghako_gyosya_kb = " + getTaghakoGyosyaKbString()
//			↓↓削除（2005.06.30）↓↓
//			+ "  hako_basyo_na = " + getHakoBasyoNaString()
//			↑↑削除（2005.06.30）↑↑
			+ "  hinbanbetu_denpyo_kb = " + getHinbanbetuDenpyoKbString()
			+ "  memo_tx = " + getMemoTxString()
			+ "  tosan_kb = " + getTosanKbString()
//	 ↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
			+ "  hanbai_keiyaku_kb = " + getHanbaiKeiyakuKbString()
			+ "  asty_kb = " + getAstyKbString()
//	 ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
		//↓↓仕様追加による変更（2005.05.24）
			+ "  eos_kb = " + getEosKbString()
		//↑↑仕様追加による変更（2005.05.24）
			
//	 ↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
//　↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//			+ "  kakin_syubetu_cd = " + getKakinSyubetuCdString()		
//　↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//	 ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
			
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst440101_SiiresakiBean コピー後のクラス
	 */
	public mst440101_SiiresakiBean createClone()
	{
		mst440101_SiiresakiBean bean = new mst440101_SiiresakiBean();
//	   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		bean.setKanriKb(this.kanri_kb);
//		bean.setKanriCd(this.kanri_cd);
		bean.setBumonCd(this.kanri_cd);
		bean.setTikuCd(this.area_cd);
//	   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setKanjiNa(this.kanji_na);
		bean.setKanaNa(this.kana_na);
		bean.setKanjiRn(this.kanji_rn);
		bean.setKanaRn(this.kana_rn);
		bean.setYubinCd(this.yubin_cd);
		bean.setAddressKanji1Na(this.address_kanji1_na);
		bean.setAddressKanji2Na(this.address_kanji2_na);
		bean.setAddressKana1Na(this.address_kana1_na);
		bean.setAddressKana2Na(this.address_kana2_na);
		bean.setMadoguchiTantoNa(this.madoguchi_tanto_na);
		bean.setTel1Na(this.tel1_na);
		bean.setTel2Na(this.tel2_na);
		bean.setFax1Na(this.fax1_na);
		bean.setFax2Na(this.fax2_na);
		bean.setNightTelNa(this.night_tel_na);
		bean.setEmailNa(this.email_na);
		bean.setJohosyoriSeikyuKb(this.johosyori_seikyu_kb);
		bean.setSyhincdPrintKb(this.syhincd_print_kb);
		bean.setTaghakoGyosyaKb(this.taghako_gyosya_kb);
//		↓↓削除（2005.06.30）↓↓
//		bean.setHakoBasyoNa(this.hako_basyo_na);
//		↑↑削除（2005.06.30）↑↑
		bean.setHinbanbetuDenpyoKb(this.hinbanbetu_denpyo_kb);
		bean.setMemoTx(this.memo_tx);
		bean.setTosanKb(this.tosan_kb);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		//↓↓仕様追加による変更（2005.05.24）
		bean.setEosKb(this.eos_kb);
		//↑↑仕様追加による変更（2005.05.24）
//↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
//↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//		bean.setKakinSyubetuCd(this.kakin_syubetu_cd);
//↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
		bean.setUpdateUserId(this.asty_kb);
		bean.setDeleteFg(this.hanbai_keiyaku_kb);
//↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
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
		if( !( o instanceof mst440101_SiiresakiBean ) ) return false;
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
