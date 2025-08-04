package mdware.master.common.bean;

import java.util.Iterator;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import mdware.common.util.StringCheck;

/**
 * <p>タイトル: 店別商品取扱台帳印刷処理データ取得用BEAN</p>
 * <p>説明: 店別商品取扱台帳印刷処理で使用(mst300101_TanpinTenToriatukaiLBeanから流用)</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VJC NAKAZAWA
 * @version 1.0(2006/10/18)初版作成
 */
public class mst300102_TanpinTenToriatukaiLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int BUMON_CD_MAX_LENGTH 			= 4;
	public static final int SYOHIN_CD_MAX_LENGTH 		= 13;
	public static final int HINMEI_KANJI_NA_MAX_LENGTH 	= 18;
	public static final int TENPO_CDL_MAX_LENGTH 		= 1024;
	public static final int TKANJI_RNL_MAX_LENGTH 		= 1024;
	public static final int FLGL_MAX_LENGTH 				= 1024;
	public static final int INSERT_TSL_MAX_LENGTH 		= 1024;
	public static final int INSERT_USER_IDL_MAX_LENGTH 	= 1024;
	public static final int UPDATE_TSL_MAX_LENGTH 		= 1024;
	public static final int UPDATE_USER_IDL_MAX_LENGTH 	= 1024;
	public static final int SENTAKU_MAX_LENGTH 			= 1;
	public static final int OLDFLGL_MAX_LENGTH 			= 1024;

	private String bumon_cd 		= "";
	private String syohin_cd 		= "";
	private String hinmei_kanji_na = "";
	private String tenpo_cdl 		= "";
	private String tkanji_rnl 		= "";
	private String flgl 			= "";
	private String insert_tsl 		= "";
	private String insert_user_idl = "";
	private String update_tsl 		= "";
	private String update_user_idl = "";
	private String sentaku 		= "";
	private String oldFlgl 		= "";
	
	//以下mst300102_TanpinTenToriatukaiLBean用にmst300101_TanpinTenToriatukaiLBeanから追加
	//変数宣言からゲッター、セッターまで記述
	private long tana_no_nb 			= 0;
	private String siire_hinban_cd 	= "";
	private String ten_hachu_st_dt 	= "";
	private String ten_hachu_ed_dt 	= "";
	private String eos_kb 				= "";
	private long hachutani_irisu_qt 	= 0;
	private String size_cd 			= "";
	private String color_cd 			= "";
	private String kikaku_kana_na 		= "";
	private String kikaku_kana_2_na 	= "";
	private double gentanka_vl		= 0;
	private long baitanka_vl 			= 0;
	private String unit_cd 			= "";
	private String siiresaki_cd 		= "";
	private String siiresaki_na 		= "";
	private String hinsyu_cd 			= "";
	private String hinsyu_na 			= "";
	
	public static final int SIIRE_HINBAN_CD_MAX_LENGTH 	= 15;
	public static final int TEN_HACHU_ST_DT_MAX_LENGTH 	= 8;
	public static final int TEN_HACHU_ED_DT_MAX_LENGTH 	= 8;
	public static final int EOS_KB_MAX_LENGTH 			= 1;
	public static final int SIZE_CD_MAX_LENGTH 			= 4;
	public static final int COLOR_CD_MAX_LENGTH 			= 2;
	public static final int KIKAKU_KANA_NA_MAX_LENGTH 	= 160;
	public static final int KIKAKU_KANA_2_NA_MAX_LENGTH 	= 160;
	public static final int UNIT_CD_MAX_LENGTH 			= 4;
	public static final int SIIRESAKI_CD_MAX_LENGTH 		= 9;
	public static final int SIIRESAKI_NA_MAX_LENGTH 		= 14;
	public static final int HINSYU_CD_MAX_LENGTH 		= 4;
	public static final int HINSYU_NA_MAX_LENGTH 		= 80;
	
	// tana_no_nbに対するセッターとゲッターの集合
	public boolean setTanaNoNb(String tana_no_nb)
	{
		try
		{
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
		this.tana_no_nb = tana_no_nb;
		return true;
	}
	public long getTanaNoNb()
	{
		return this.tana_no_nb;
	}
	public String getTanaNoNbString()
	{
		return Long.toString(this.tana_no_nb);
	}


	// siire_hinban_cdに対するセッターとゲッターの集合
	public boolean setSiireHinbanCd(String siire_hinban_cd)
	{
		this.siire_hinban_cd = StringCheck.strIndisCvt( siire_hinban_cd );
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


	// ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setTenHachuStDt(String ten_hachu_st_dt)
	{
		this.ten_hachu_st_dt = StringCheck.strIndisCvt( ten_hachu_st_dt );
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
		this.ten_hachu_ed_dt = StringCheck.strIndisCvt( ten_hachu_ed_dt );
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


	// eos_kbに対するセッターとゲッターの集合
	public boolean setEosKb(String eos_kb)
	{
		this.eos_kb = StringCheck.strIndisCvt( eos_kb );
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


	// hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setHachutaniIrisuQt(String hachutani_irisu_qt)
	{
		try
		{
			this.hachutani_irisu_qt = Long.parseLong(hachutani_irisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHachutaniIrisuQt(long hachutani_irisu_qt)
	{
		this.hachutani_irisu_qt = hachutani_irisu_qt;
		return true;
	}
	public long getHachutaniIrisuQt()
	{
		return this.hachutani_irisu_qt;
	}
	public String getHachutaniIrisuQtString()
	{
		return Long.toString(this.hachutani_irisu_qt);
	}


	// size_cdに対するセッターとゲッターの集合
	public boolean setSizeCd(String size_cd)
	{
		this.size_cd = StringCheck.strIndisCvt( size_cd );
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
		this.color_cd = StringCheck.strIndisCvt( color_cd );
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


	// kikaku_kana_naに対するセッターとゲッターの集合
	public boolean setKikakuKanaNa(String kikaku_kana_na)
	{
		this.kikaku_kana_na = StringCheck.strIndisCvt( kikaku_kana_na );
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


	// kikaku_kana_2_naに対するセッターとゲッターの集合
	public boolean setKikakuKana2Na(String kikaku_kana_2_na)
	{
		this.kikaku_kana_2_na = StringCheck.strIndisCvt( kikaku_kana_2_na );
		return true;
	}
	public String getKikakuKana2Na()
	{
		return cutString(this.kikaku_kana_2_na,KIKAKU_KANA_2_NA_MAX_LENGTH);
	}
	public String getKikakuKana2NaString()
	{
		return "'" + cutString(this.kikaku_kana_2_na,KIKAKU_KANA_2_NA_MAX_LENGTH) + "'";
	}
	public String getKikakuKana2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kana_2_na,KIKAKU_KANA_2_NA_MAX_LENGTH));
	}


	// gentanka_vlに対するセッターとゲッターの集合
	public boolean setGentankaVl(String gentanka_vl)
	{
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


	// unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		this.unit_cd = StringCheck.strIndisCvt( unit_cd );
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


	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = StringCheck.strIndisCvt( siiresaki_cd );
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


	// siiresaki_naに対するセッターとゲッターの集合
	public boolean setSiiresakiNa(String siiresaki_na)
	{
		this.siiresaki_na = StringCheck.strIndisCvt( siiresaki_na );
		return true;
	}
	public String getSiiresakiNa()
	{
		return cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH);
	}
	public String getSiiresakiNaString()
	{
		return "'" + cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH) + "'";
	}
	public String getSiiresakiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH));
	}


	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = StringCheck.strIndisCvt( hinsyu_cd );
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


	// hinsyu_naに対するセッターとゲッターの集合
	public boolean setHinsyuNa(String hinsyu_na)
	{
		this.hinsyu_na = StringCheck.strIndisCvt( hinsyu_na );
		return true;
	}
	public String getHinsyuNa()
	{
		return cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH);
	}
	public String getHinsyuNaString()
	{
		return "'" + cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH) + "'";
	}
	public String getHinsyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH));
	}
	//mst300102_TanpinTenToriatukaiLBean用の追加対応END

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
	 * mst300102_TanpinTenToriatukaiLBeanを１件のみ抽出したい時に使用する
	 */
	public static mst300102_TanpinTenToriatukaiLBean getmst300102_TanpinTenToriatukaiLBean(DataHolder dataHolder)
	{
		mst300102_TanpinTenToriatukaiLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst300102_TanpinTenToriatukaiLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			// １件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst300102_TanpinTenToriatukaiLBean )ite.next();
			// ２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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

//     ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//	hanku_cdに対するセッターとゲッターの集合
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
//     ↑↑2006.06.21 fanglh カスタマイズ修正↑↑

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


	// tenpo_cdlに対するセッターとゲッターの集合
	public boolean setTenpoCdl(String tenpo_cdl)
	{
		this.tenpo_cdl = tenpo_cdl;
		return true;
	}
	public String getTenpoCdl()
	{
		return cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH);
	}
	public String getTenpoCdlString()
	{
		return "'" + cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH) + "'";
	}
	public String getTenpoCdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH));
	}


	// tkanji_rnlに対するセッターとゲッターの集合
	public boolean setTkanjiRnl(String tkanji_rnl)
	{
		this.tkanji_rnl = tkanji_rnl;
		return true;
	}
	public String getTkanjiRnl()
	{
		return cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH);
	}
	public String getTkanjiRnlString()
	{
		return "'" + cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH) + "'";
	}
	public String getTkanjiRnlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH));
	}


	// flglに対するセッターとゲッターの集合
	public boolean setFlgl(String flgl)
	{
		this.flgl = flgl;
		return true;
	}
	public String getFlgl()
	{
		return cutString(this.flgl,FLGL_MAX_LENGTH);
	}
	public String getFlglString()
	{
		return "'" + cutString(this.flgl,FLGL_MAX_LENGTH) + "'";
	}
	public String getFlglHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.flgl,FLGL_MAX_LENGTH));
	}
	// oldFlglに対するセッターとゲッターの集合
	public boolean setOldFlgl(String oldFlgl)
	{
		this.oldFlgl = oldFlgl;
		return true;
	}
	public String getOldFlgl()
	{
		return cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH);
	}
	public String getOldFlglString()
	{
		return "'" + cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH) + "'";
	}
	public String getOldFlglHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH));
	}


	// insert_tslに対するセッターとゲッターの集合
	public boolean setInsertTsl(String insert_tsl)
	{
		this.insert_tsl = insert_tsl;
		return true;
	}
	public String getInsertTsl()
	{
		return cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH);
	}
	public String getInsertTslString()
	{
		return "'" + cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH) + "'";
	}
	public String getInsertTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH));
	}


	// insert_user_idlに対するセッターとゲッターの集合
	public boolean setInsertUserIdl(String insert_user_idl)
	{
		this.insert_user_idl = insert_user_idl;
		return true;
	}
	public String getInsertUserIdl()
	{
		return cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH);
	}
	public String getInsertUserIdlString()
	{
		return "'" + cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH));
	}


	// update_tslに対するセッターとゲッターの集合
	public boolean setUpdateTsl(String update_tsl)
	{
		this.update_tsl = update_tsl;
		return true;
	}
	public String getUpdateTsl()
	{
		return cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH);
	}
	public String getUpdateTslString()
	{
		return "'" + cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH) + "'";
	}
	public String getUpdateTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH));
	}


	// update_user_idlに対するセッターとゲッターの集合
	public boolean setUpdateUserIdl(String update_user_idl)
	{
		this.update_user_idl = update_user_idl;
		return true;
	}
	public String getUpdateUserIdl()
	{
		return cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH);
	}
	public String getUpdateUserIdlString()
	{
		return "'" + cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH));
	}
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  bumon_cd = " + getBumonCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  hinmei_kanji_na = " + getHinmeiKanjiNaString()
			+ "  tana_no_nb = " + getTanaNoNbString()
			+ "  siire_hinban_cd = " + getSiireHinbanCdString()
			+ "  ten_hachu_st_dt = " + getTenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getTenHachuEdDtString()
			+ "  eos_kb = " + getEosKbString()
			+ "  hachutani_irisu_qt = " + getHachutaniIrisuQtString()
			+ "  size_cd = " + getSizeCdString()
			+ "  color_cd = " + getColorCdString()
			+ "  kikaku_kana_na = " + getKikakuKanaNaString()
			+ "  kikaku_kana_2_na = " + getKikakuKana2NaString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  unit_cd = " + getUnitCdString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  siiresaki_na = " + getSiiresakiNaString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hinsyu_na = " + getHinsyuNaString()
			+ " createDatabase  = " + createDatabase;
	}
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst300102_TanpinTenToriatukaiLBean コピー後のクラス
	 */
	public mst300102_TanpinTenToriatukaiLBean createClone()
	{
		mst300102_TanpinTenToriatukaiLBean bean = new mst300102_TanpinTenToriatukaiLBean();
		bean.setBumonCd(this.bumon_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);
		bean.setTanaNoNb(this.tana_no_nb);
		bean.setSiireHinbanCd(this.siire_hinban_cd);
		bean.setTenHachuStDt(this.ten_hachu_st_dt);
		bean.setTenHachuEdDt(this.ten_hachu_ed_dt);
		bean.setEosKb(this.eos_kb);
		bean.setHachutaniIrisuQt(this.hachutani_irisu_qt);
		bean.setSizeCd(this.size_cd);
		bean.setColorCd(this.color_cd);
		bean.setKikakuKanaNa(this.kikaku_kana_na);
		bean.setKikakuKana2Na(this.kikaku_kana_2_na);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setUnitCd(this.unit_cd);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setSiiresakiNa(this.siiresaki_na);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinsyuNa(this.hinsyu_na);
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
		if( !( o instanceof mst300102_TanpinTenToriatukaiLBean ) ) return false;
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
// BUGNO-S051 2005.05.15 Sirius START
// 				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
// BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}
}
