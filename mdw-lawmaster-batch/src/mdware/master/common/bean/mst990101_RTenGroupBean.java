package mdware.master.common.bean;

import java.util.Iterator;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.11.25) Version 1.1.MDWARE
 * @version 1.X (Create time: 2006/10/16 9:21:17) K.Tanigawa
 */
public class mst990101_RTenGroupBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int BUMON_CD_MAX_LENGTH         = 4;
	public static final int YOTO_KB_MAX_LENGTH          = 1;
	public static final int GROUPNO_CD_MAX_LENGTH       = 2;
	public static final int TENPO_CD_MAX_LENGTH         = 6;
	public static final int KANJI_NA_MAX_LENGTH         = 320;
	public static final int KANA_NA_MAX_LENGTH          = 320;
	public static final int KANJI_RN_MAX_LENGTH         = 80;
	public static final int KANA_RN_MAX_LENGTH          = 80;
	public static final int TENPO_TYPE_KB_MAX_LENGTH    = 1;
	public static final int TENPO_KB_MAX_LENGTH         = 1;
	public static final int KAITEN_DT_MAX_LENGTH        = 8;
	public static final int HEITEN_DT_MAX_LENGTH        = 8;
	public static final int ADDRESS_KANJI_NA_MAX_LENGTH = 480;
	public static final int ADDRESS_KANA_NA_MAX_LENGTH  = 240;
	public static final int ADDRESS_3_NA_MAX_LENGTH     = 128;
	public static final int YUBIN_CD_MAX_LENGTH         = 7;
	public static final int TEL_CD_MAX_LENGTH           = 20;
	public static final int FAX_CD_MAX_LENGTH           = 20;
	public static final int HACHU_ST_DT_MAX_LENGTH      = 8;
	public static final int HACHU_ED_DT_MAX_LENGTH      = 8;
	public static final int GYOTAI_KB_MAX_LENGTH        = 2;
	public static final int INSERT_TS_MAX_LENGTH        = 14;
	public static final int INSERT_USER_ID_MAX_LENGTH   = 20;
	public static final int UPDATE_TS_MAX_LENGTH        = 14;
	public static final int UPDATE_USER_ID_MAX_LENGTH   = 20;
	public static final int DELETE_FG_MAX_LENGTH        = 1;


	private String bumon_cd         = null;
	private String yoto_kb          = null;
	private String groupno_cd       = null;
	private long rank_nb	         = 0;
	private String tenpo_cd         = null;
	private String kanji_na         = null;
	private String kana_na          = null;
	private String kanji_rn         = null;
	private String kana_rn          = null;
	private String tenpo_type_kb    = null;
	private String tenpo_kb         = null;
	private String kaiten_dt        = null;
	private String heiten_dt        = null;
	private String address_kanji_na = null;
	private String address_kana_na  = null;
	private String address_3_na     = null;
	private String yubin_cd         = null;
	private String tel_cd           = null;
	private String fax_cd           = null;
	private String hachu_st_dt      = null;
	private String hachu_ed_dt      = null;
	private String gyotai_kb        = null;
	private String insert_ts        = null;
	private String insert_user_id   = null;
	private String update_ts        = null;
	private String update_user_id   = null;
	private String delete_fg        = null;
//	↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
	private boolean bRegistered = false;
//	↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑

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
	 * Mst990101RTenGroupBeanを１件のみ抽出したい時に使用する
	 */
	public static mst990101_RTenGroupBean getMst990101RTenGroupBean(DataHolder dataHolder)
	{
		mst990101_RTenGroupBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst990101_RTenGroupDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst990101_RTenGroupBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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
	public String getBumonCdString()
	{
		return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH));
	}


	// yoto_kbに対するセッターとゲッターの集合
	public boolean setYotoKb(String yoto_kb)
	{
		this.yoto_kb = yoto_kb;
		return true;
	}
	public String getYotoKb()
	{
		return cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH);
	}
	public String getYotoKbString()
	{
		return "'" + cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH) + "'";
	}
	public String getYotoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH));
	}


	// groupno_cdに対するセッターとゲッターの集合
	public boolean setGroupnoCd(String groupno_cd)
	{
		this.groupno_cd = groupno_cd;
		return true;
	}
	public String getGroupnoCd()
	{
		return cutString(this.groupno_cd,GROUPNO_CD_MAX_LENGTH);
	}
	public String getGroupnoCdString()
	{
		return "'" + cutString(this.groupno_cd,GROUPNO_CD_MAX_LENGTH) + "'";
	}
	public String getGroupnoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.groupno_cd,GROUPNO_CD_MAX_LENGTH));
	}


	// rank_nbに対するセッターとゲッターの集合
	public boolean setRankNb(String rank_nb)
	{
		try
		{
			this.rank_nb = Long.parseLong(rank_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setRankNb(long rank_nb)
	{
		this.rank_nb = rank_nb;
		return true;
	}
	public long getRankNb()
	{
		return this.rank_nb;
	}
	public String getRankNbString()
	{
		return Long.toString(this.rank_nb);
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

//	↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
	// bRegisteredに対するセッターとゲッターの集合
	public boolean isRegistered()
	{
		return bRegistered;
	}
	public void setRegistered(boolean registered)
	{
		bRegistered = registered;
	}
//	↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  bumon_cd = " + getBumonCdString()
			+ "  yoto_kb = " + getYotoKbString()
			+ "  groupno_cd = " + getGroupnoCdString()
			+ "  rank_nb = " + getRankNbString()
			+ "  tenpo_cd = " + getTenpoCdString()
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
			+ "  gyotai_kb = " + getGyotaiKbString()
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
	 * @return Mst990101RTenGroupBean コピー後のクラス
	 */
	public mst990101_RTenGroupBean createClone()
	{
		mst990101_RTenGroupBean bean = new mst990101_RTenGroupBean();
		bean.setBumonCd(this.bumon_cd);
		bean.setYotoKb(this.yoto_kb);
		bean.setGroupnoCd(this.groupno_cd);
		bean.setRankNb(this.rank_nb);
		bean.setTenpoCd(this.tenpo_cd);
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
		bean.setGyotaiKb(this.gyotai_kb);
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
		if( !( o instanceof mst990101_RTenGroupBean ) ) return false;
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
