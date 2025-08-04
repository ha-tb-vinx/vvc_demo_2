package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/9/24 15:7:37)
 * @version 1.0 2006/09/24,2006/09/25,2006/09/26 仕入先システム区分チェック処理のため作成(元のDMは、検索条件を直接入力しており、編集すると動作しなくなるリスクが高かったため、新規にDMクラスを作成しました) K.Tanigawa
 */
public class mst440401_RSiiresakiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KANRI_KB_MAX_LENGTH=1;
	public static final int KANRI_CD_MAX_LENGTH=4;
	public static final int SIIRESAKI_CD_MAX_LENGTH=9;
	public static final int EOS_KB_MAX_LENGTH=1;
	public static final int KANJI_NA_MAX_LENGTH=320;
	public static final int KANA_NA_MAX_LENGTH=320;
	public static final int KANJI_RN_MAX_LENGTH=80;
	public static final int KANA_RN_MAX_LENGTH=80;
	public static final int YUBIN_CD_MAX_LENGTH=7;
	public static final int ADDRESS_KANJI1_NA_MAX_LENGTH=480;
	public static final int ADDRESS_KANJI2_NA_MAX_LENGTH=480;
	public static final int ADDRESS_KANA1_NA_MAX_LENGTH=240;
	public static final int ADDRESS_KANA2_NA_MAX_LENGTH=240;
	public static final int MADOGUCHI_TANTO_NA_MAX_LENGTH=50;
	public static final int TEL1_NA_MAX_LENGTH=20;
	public static final int TEL2_NA_MAX_LENGTH=20;
	public static final int FAX1_NA_MAX_LENGTH=20;
	public static final int FAX2_NA_MAX_LENGTH=20;
	public static final int NIGHT_TEL_NA_MAX_LENGTH=20;
	public static final int EMAIL_NA_MAX_LENGTH=50;
	public static final int EIGYOSYO_KANJI_NA_MAX_LENGTH=160;
	public static final int EIGYOSYO_KANA_NA_MAX_LENGTH=80;
	public static final int JOHOSYORI_SEIKYU_KB_MAX_LENGTH=1;
	public static final int SYHINCD_PRINT_KB_MAX_LENGTH=1;
	public static final int TAGHAKO_GYOSYA_KB_MAX_LENGTH=3;
	public static final int HINBANBETU_DENPYO_KB_MAX_LENGTH=1;
	public static final int MEMO_TX_MAX_LENGTH=120;
	public static final int TOSAN_KB_MAX_LENGTH=1;
	public static final int HANBAI_KEIYAKU_KB_MAX_LENGTH=1;
	public static final int ASTY_KB_MAX_LENGTH=1;
	public static final int HAISINSAKI_CD_MAX_LENGTH=11;
	public static final int SIIRE_SYSTEM_KB_MAX_LENGTH=1;
	public static final int INSERT_TS_MAX_LENGTH=14;
	public static final int INSERT_USER_ID_MAX_LENGTH=20;
	public static final int UPDATE_TS_MAX_LENGTH=14;
	public static final int UPDATE_USER_ID_MAX_LENGTH=20;
	public static final int DELETE_FG_MAX_LENGTH=1;

	private String kanri_kb = null;
	private String kanri_cd = null;
	private String siiresaki_cd = null;
	private String eos_kb = null;
	private String kanji_na = null;
	private String kana_na = null;
	private String kanji_rn = null;
	private String kana_rn = null;
	private String yubin_cd = null;
	private String address_kanji1_na = null;
	private String address_kanji2_na = null;
	private String address_kana1_na = null;
	private String address_kana2_na = null;
	private String madoguchi_tanto_na = null;
	private String tel1_na = null;
	private String tel2_na = null;
	private String fax1_na = null;
	private String fax2_na = null;
	private String night_tel_na = null;
	private String email_na = null;
	private String eigyosyo_kanji_na = null;
	private String eigyosyo_kana_na = null;
	private String johosyori_seikyu_kb = null;
	private String syhincd_print_kb = null;
	private String taghako_gyosya_kb = null;
	private String hinbanbetu_denpyo_kb = null;
	private String memo_tx = null;
	private String tosan_kb = null;
	private String hanbai_keiyaku_kb = null;
	private String asty_kb = null;
	private String haisinsaki_cd = null;
	private String siire_system_kb = null;
	private String insert_ts = null;
	private String insert_user_id = null;
	private String update_ts = null;
	private String update_user_id = null;
	private String delete_fg = null;

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
	 * RSiiresakiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst440401_RSiiresakiBean getRSiiresakiBean(DataHolder dataHolder)
	{
		mst440401_RSiiresakiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst440401_RSiiresakiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst440401_RSiiresakiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// kanri_kbに対するセッターとゲッターの集合
	public boolean setKanriKb(String kanri_kb)
	{
		this.kanri_kb = kanri_kb;
		return true;
	}
	public String getKanriKb()
	{
		return cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH);
	}
	public String getKanriKbString()
	{
		return "'" + cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH) + "'";
	}
	public String getKanriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH));
	}


	// kanri_cdに対するセッターとゲッターの集合
	public boolean setKanriCd(String kanri_cd)
	{
		this.kanri_cd = kanri_cd;
		return true;
	}
	public String getKanriCd()
	{
		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
	}
	public String getKanriCdString()
	{
		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
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


	// eigyosyo_kanji_naに対するセッターとゲッターの集合
	public boolean setEigyosyoKanjiNa(String eigyosyo_kanji_na)
	{
		this.eigyosyo_kanji_na = eigyosyo_kanji_na;
		return true;
	}
	public String getEigyosyoKanjiNa()
	{
		return cutString(this.eigyosyo_kanji_na,EIGYOSYO_KANJI_NA_MAX_LENGTH);
	}
	public String getEigyosyoKanjiNaString()
	{
		return "'" + cutString(this.eigyosyo_kanji_na,EIGYOSYO_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getEigyosyoKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eigyosyo_kanji_na,EIGYOSYO_KANJI_NA_MAX_LENGTH));
	}


	// eigyosyo_kana_naに対するセッターとゲッターの集合
	public boolean setEigyosyoKanaNa(String eigyosyo_kana_na)
	{
		this.eigyosyo_kana_na = eigyosyo_kana_na;
		return true;
	}
	public String getEigyosyoKanaNa()
	{
		return cutString(this.eigyosyo_kana_na,EIGYOSYO_KANA_NA_MAX_LENGTH);
	}
	public String getEigyosyoKanaNaString()
	{
		return "'" + cutString(this.eigyosyo_kana_na,EIGYOSYO_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getEigyosyoKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eigyosyo_kana_na,EIGYOSYO_KANA_NA_MAX_LENGTH));
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


	// haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setHaisinsakiCd(String haisinsaki_cd)
	{
		this.haisinsaki_cd = haisinsaki_cd;
		return true;
	}
	public String getHaisinsakiCd()
	{
		return cutString(this.haisinsaki_cd,HAISINSAKI_CD_MAX_LENGTH);
	}
	public String getHaisinsakiCdString()
	{
		return "'" + cutString(this.haisinsaki_cd,HAISINSAKI_CD_MAX_LENGTH) + "'";
	}
	public String getHaisinsakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haisinsaki_cd,HAISINSAKI_CD_MAX_LENGTH));
	}


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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  kanri_kb = " + getKanriKbString()
			+ "  kanri_cd = " + getKanriCdString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  eos_kb = " + getEosKbString()
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
			+ "  eigyosyo_kanji_na = " + getEigyosyoKanjiNaString()
			+ "  eigyosyo_kana_na = " + getEigyosyoKanaNaString()
			+ "  johosyori_seikyu_kb = " + getJohosyoriSeikyuKbString()
			+ "  syhincd_print_kb = " + getSyhincdPrintKbString()
			+ "  taghako_gyosya_kb = " + getTaghakoGyosyaKbString()
			+ "  hinbanbetu_denpyo_kb = " + getHinbanbetuDenpyoKbString()
			+ "  memo_tx = " + getMemoTxString()
			+ "  tosan_kb = " + getTosanKbString()
			+ "  hanbai_keiyaku_kb = " + getHanbaiKeiyakuKbString()
			+ "  asty_kb = " + getAstyKbString()
			+ "  haisinsaki_cd = " + getHaisinsakiCdString()
			+ "  siire_system_kb = " + getSiireSystemKbString()
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
	 * @return RSiiresakiBean コピー後のクラス
	 */
	public mst440401_RSiiresakiBean createClone()
	{
		mst440401_RSiiresakiBean bean = new mst440401_RSiiresakiBean();
		bean.setKanriKb(this.kanri_kb);
		bean.setKanriCd(this.kanri_cd);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setEosKb(this.eos_kb);
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
		bean.setEigyosyoKanjiNa(this.eigyosyo_kanji_na);
		bean.setEigyosyoKanaNa(this.eigyosyo_kana_na);
		bean.setJohosyoriSeikyuKb(this.johosyori_seikyu_kb);
		bean.setSyhincdPrintKb(this.syhincd_print_kb);
		bean.setTaghakoGyosyaKb(this.taghako_gyosya_kb);
		bean.setHinbanbetuDenpyoKb(this.hinbanbetu_denpyo_kb);
		bean.setMemoTx(this.memo_tx);
		bean.setTosanKb(this.tosan_kb);
		bean.setHanbaiKeiyakuKb(this.hanbai_keiyaku_kb);
		bean.setAstyKb(this.asty_kb);
		bean.setHaisinsakiCd(this.haisinsaki_cd);
		bean.setSiireSystemKb(this.siire_system_kb);
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
		if( !( o instanceof mst440401_RSiiresakiBean ) ) return false;
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
