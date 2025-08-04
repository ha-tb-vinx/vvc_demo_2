package mdware.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author VINX
 * @version 1.0 2014/06/23 Nghia-HT 海外LAWSON様UTF-8対応
 */
public class DtFreshHachuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH = 10;
	public static final int TENHANKU_CD_MAX_LENGTH = 4;
	public static final int SYOHIN_CD_MAX_LENGTH = 20;
	public static final int STD_TORIHIKISAKI_CD_MAX_LENGTH = 11;
	public static final int SANTI_CD_MAX_LENGTH = 4;
	public static final int TOKAIKYU_CD_MAX_LENGTH = 4;
	public static final int KIKAKU_CD_MAX_LENGTH = 4;
	public static final int KAKUTEI_KB_MAX_LENGTH = 1;
	public static final int NOHIN_SYOHIN_CD_MAX_LENGTH = 20;
	public static final int POS_CD_MAX_LENGTH = 20;
	public static final int JISYA_SYOHIN_CD_MAX_LENGTH = 20;
	public static final int SYOHIN_NA_MAX_LENGTH = 80;
	public static final int SYOHIN_KA_MAX_LENGTH = 50;
	public static final int HACHU_TANI_NA_MAX_LENGTH = 40;
	public static final int HACHU_TANI_KA_MAX_LENGTH = 20;
	public static final int SANTI_NA_MAX_LENGTH = 20;
	public static final int TOKAIKYU_NA_MAX_LENGTH = 20;
	public static final int KIKAKU_NA_MAX_LENGTH = 20;
	public static final int TORIHIKISAKI_NA_MAX_LENGTH = 60;
	public static final int TORIHIKISAKI_KA_MAX_LENGTH = 30;
	public static final int HINSYU_CD_MAX_LENGTH = 4;
	public static final int HINMOKU_CD_MAX_LENGTH = 4;
	public static final int KB_SOBA_KB_MAX_LENGTH = 1;
	public static final int TEIKAN_KB_MAX_LENGTH = 1;
	public static final int EOS_KB_MAX_LENGTH = 1;
	public static final int HACHU_PATTERN_KB_MAX_LENGTH = 1;
	public static final int SIMEJIKAN_TM_MAX_LENGTH = 2;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String tenpo_cd = null;
	private String tenhanku_cd = null;
	private String syohin_cd = null;
	private String std_torihikisaki_cd = null;
	private String santi_cd = null;
	private String tokaikyu_cd = null;
	private String kikaku_cd = null;
	private String kakutei_kb = null;
	private long kakutei_seq = 0;
	private String nohin_syohin_cd = null;
	private String pos_cd = null;
	private String jisya_syohin_cd = null;
	private String syohin_na = null;
	private String syohin_ka = null;
	private String hachu_tani_na = null;
	private String hachu_tani_ka = null;
	private String santi_na = null;
	private String tokaikyu_na = null;
	private String kikaku_na = null;
	private String torihikisaki_na = null;
	private String torihikisaki_ka = null;
	private String hinsyu_cd = null;
	private String hinmoku_cd = null;
	private double irisu_qt = 0;
	private long hachu_tani_qt = 0;
	private double gentanka_vl = 0;
	private long baitanka_vl = 0;
	private String kb_soba_kb = null;
	private String teikan_kb = null;
	private String eos_kb = null;
	private String hachu_pattern_kb = null;
	private String simejikan_tm = null;
	private double zenjitu_tei_han_qt = 0;
	private long zenjitu_tei_han_vl = 0;
	private double zensyu_tei_han_qt = 0;
	private long zensyu_tei_han_vl = 0;
	private double zenjitu_toku_han_qt = 0;
	private long zenjitu_toku_han_vl = 0;
	private double zensyu_toku_han_qt = 0;
	private long zensyu_toku_han_vl = 0;
	private double baihen_suryo_qt = 0;
	private long baihen_suryo_worst = 0;
	private long baihen_vl = 0;
	private long baihen_vl_worst = 0;
	private String riyo_user_id = null;
	private String insert_ts = null;
	private String update_ts = null;

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
	 * DtFreshHachuBeanを１件のみ抽出したい時に使用する
	 */
	public static DtFreshHachuBean getDtFreshHachuBean(DataHolder dataHolder)
	{
		DtFreshHachuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new DtFreshHachuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (DtFreshHachuBean )ite.next();
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


	// tenhanku_cdに対するセッターとゲッターの集合
	public boolean setTenhankuCd(String tenhanku_cd)
	{
		this.tenhanku_cd = tenhanku_cd;
		return true;
	}
	public String getTenhankuCd()
	{
		return cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH);
	}
	public String getTenhankuCdString()
	{
		return "'" + cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH) + "'";
	}
	public String getTenhankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH));
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


	// std_torihikisaki_cdに対するセッターとゲッターの集合
	public boolean setStdTorihikisakiCd(String std_torihikisaki_cd)
	{
		this.std_torihikisaki_cd = std_torihikisaki_cd;
		return true;
	}
	public String getStdTorihikisakiCd()
	{
		return cutString(this.std_torihikisaki_cd,STD_TORIHIKISAKI_CD_MAX_LENGTH);
	}
	public String getStdTorihikisakiCdString()
	{
		return "'" + cutString(this.std_torihikisaki_cd,STD_TORIHIKISAKI_CD_MAX_LENGTH) + "'";
	}
	public String getStdTorihikisakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.std_torihikisaki_cd,STD_TORIHIKISAKI_CD_MAX_LENGTH));
	}


	// santi_cdに対するセッターとゲッターの集合
	public boolean setSantiCd(String santi_cd)
	{
		this.santi_cd = santi_cd;
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


	// tokaikyu_cdに対するセッターとゲッターの集合
	public boolean setTokaikyuCd(String tokaikyu_cd)
	{
		this.tokaikyu_cd = tokaikyu_cd;
		return true;
	}
	public String getTokaikyuCd()
	{
		return cutString(this.tokaikyu_cd,TOKAIKYU_CD_MAX_LENGTH);
	}
	public String getTokaikyuCdString()
	{
		return "'" + cutString(this.tokaikyu_cd,TOKAIKYU_CD_MAX_LENGTH) + "'";
	}
	public String getTokaikyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tokaikyu_cd,TOKAIKYU_CD_MAX_LENGTH));
	}


	// kikaku_cdに対するセッターとゲッターの集合
	public boolean setKikakuCd(String kikaku_cd)
	{
		this.kikaku_cd = kikaku_cd;
		return true;
	}
	public String getKikakuCd()
	{
		return cutString(this.kikaku_cd,KIKAKU_CD_MAX_LENGTH);
	}
	public String getKikakuCdString()
	{
		return "'" + cutString(this.kikaku_cd,KIKAKU_CD_MAX_LENGTH) + "'";
	}
	public String getKikakuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_cd,KIKAKU_CD_MAX_LENGTH));
	}


	// kakutei_kbに対するセッターとゲッターの集合
	public boolean setKakuteiKb(String kakutei_kb)
	{
		this.kakutei_kb = kakutei_kb;
		return true;
	}
	public String getKakuteiKb()
	{
		return cutString(this.kakutei_kb,KAKUTEI_KB_MAX_LENGTH);
	}
	public String getKakuteiKbString()
	{
		return "'" + cutString(this.kakutei_kb,KAKUTEI_KB_MAX_LENGTH) + "'";
	}
	public String getKakuteiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kakutei_kb,KAKUTEI_KB_MAX_LENGTH));
	}


	// kakutei_seqに対するセッターとゲッターの集合
	public boolean setKakuteiSeq(String kakutei_seq)
	{
		try
		{
			this.kakutei_seq = Long.parseLong(kakutei_seq);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setKakuteiSeq(long kakutei_seq)
	{
		this.kakutei_seq = kakutei_seq;
		return true;
	}
	public long getKakuteiSeq()
	{
		return this.kakutei_seq;
	}
	public String getKakuteiSeqString()
	{
		return Long.toString(this.kakutei_seq);
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


	// pos_cdに対するセッターとゲッターの集合
	public boolean setPosCd(String pos_cd)
	{
		this.pos_cd = pos_cd;
		return true;
	}
	public String getPosCd()
	{
		return cutString(this.pos_cd,POS_CD_MAX_LENGTH);
	}
	public String getPosCdString()
	{
		return "'" + cutString(this.pos_cd,POS_CD_MAX_LENGTH) + "'";
	}
	public String getPosCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pos_cd,POS_CD_MAX_LENGTH));
	}


	// jisya_syohin_cdに対するセッターとゲッターの集合
	public boolean setJisyaSyohinCd(String jisya_syohin_cd)
	{
		this.jisya_syohin_cd = jisya_syohin_cd;
		return true;
	}
	public String getJisyaSyohinCd()
	{
		return cutString(this.jisya_syohin_cd,JISYA_SYOHIN_CD_MAX_LENGTH);
	}
	public String getJisyaSyohinCdString()
	{
		return "'" + cutString(this.jisya_syohin_cd,JISYA_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getJisyaSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisya_syohin_cd,JISYA_SYOHIN_CD_MAX_LENGTH));
	}


	// syohin_naに対するセッターとゲッターの集合
	public boolean setSyohinNa(String syohin_na)
	{
		this.syohin_na = syohin_na;
		return true;
	}
	public String getSyohinNa()
	{
		return cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH);
	}
	public String getSyohinNaString()
	{
		return "'" + cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH) + "'";
	}
	public String getSyohinNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH));
	}


	// syohin_kaに対するセッターとゲッターの集合
	public boolean setSyohinKa(String syohin_ka)
	{
		this.syohin_ka = syohin_ka;
		return true;
	}
	public String getSyohinKa()
	{
		return cutString(this.syohin_ka,SYOHIN_KA_MAX_LENGTH);
	}
	public String getSyohinKaString()
	{
		return "'" + cutString(this.syohin_ka,SYOHIN_KA_MAX_LENGTH) + "'";
	}
	public String getSyohinKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_ka,SYOHIN_KA_MAX_LENGTH));
	}


	// hachu_tani_naに対するセッターとゲッターの集合
	public boolean setHachuTaniNa(String hachu_tani_na)
	{
		this.hachu_tani_na = hachu_tani_na;
		return true;
	}
	public String getHachuTaniNa()
	{
		return cutString(this.hachu_tani_na,HACHU_TANI_NA_MAX_LENGTH);
	}
	public String getHachuTaniNaString()
	{
		return "'" + cutString(this.hachu_tani_na,HACHU_TANI_NA_MAX_LENGTH) + "'";
	}
	public String getHachuTaniNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_tani_na,HACHU_TANI_NA_MAX_LENGTH));
	}


	// hachu_tani_kaに対するセッターとゲッターの集合
	public boolean setHachuTaniKa(String hachu_tani_ka)
	{
		this.hachu_tani_ka = hachu_tani_ka;
		return true;
	}
	public String getHachuTaniKa()
	{
		return cutString(this.hachu_tani_ka,HACHU_TANI_KA_MAX_LENGTH);
	}
	public String getHachuTaniKaString()
	{
		return "'" + cutString(this.hachu_tani_ka,HACHU_TANI_KA_MAX_LENGTH) + "'";
	}
	public String getHachuTaniKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_tani_ka,HACHU_TANI_KA_MAX_LENGTH));
	}


	// santi_naに対するセッターとゲッターの集合
	public boolean setSantiNa(String santi_na)
	{
		this.santi_na = santi_na;
		return true;
	}
	public String getSantiNa()
	{
		return cutString(this.santi_na,SANTI_NA_MAX_LENGTH);
	}
	public String getSantiNaString()
	{
		return "'" + cutString(this.santi_na,SANTI_NA_MAX_LENGTH) + "'";
	}
	public String getSantiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.santi_na,SANTI_NA_MAX_LENGTH));
	}


	// tokaikyu_naに対するセッターとゲッターの集合
	public boolean setTokaikyuNa(String tokaikyu_na)
	{
		this.tokaikyu_na = tokaikyu_na;
		return true;
	}
	public String getTokaikyuNa()
	{
		return cutString(this.tokaikyu_na,TOKAIKYU_NA_MAX_LENGTH);
	}
	public String getTokaikyuNaString()
	{
		return "'" + cutString(this.tokaikyu_na,TOKAIKYU_NA_MAX_LENGTH) + "'";
	}
	public String getTokaikyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tokaikyu_na,TOKAIKYU_NA_MAX_LENGTH));
	}


	// kikaku_naに対するセッターとゲッターの集合
	public boolean setKikakuNa(String kikaku_na)
	{
		this.kikaku_na = kikaku_na;
		return true;
	}
	public String getKikakuNa()
	{
		return cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH);
	}
	public String getKikakuNaString()
	{
		return "'" + cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH) + "'";
	}
	public String getKikakuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH));
	}


	// torihikisaki_naに対するセッターとゲッターの集合
	public boolean setTorihikisakiNa(String torihikisaki_na)
	{
		this.torihikisaki_na = torihikisaki_na;
		return true;
	}
	public String getTorihikisakiNa()
	{
		return cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH);
	}
	public String getTorihikisakiNaString()
	{
		return "'" + cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH));
	}


	// torihikisaki_kaに対するセッターとゲッターの集合
	public boolean setTorihikisakiKa(String torihikisaki_ka)
	{
		this.torihikisaki_ka = torihikisaki_ka;
		return true;
	}
	public String getTorihikisakiKa()
	{
		return cutString(this.torihikisaki_ka,TORIHIKISAKI_KA_MAX_LENGTH);
	}
	public String getTorihikisakiKaString()
	{
		return "'" + cutString(this.torihikisaki_ka,TORIHIKISAKI_KA_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torihikisaki_ka,TORIHIKISAKI_KA_MAX_LENGTH));
	}


	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
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


	// irisu_qtに対するセッターとゲッターの集合
	public boolean setIrisuQt(String irisu_qt)
	{
		try
		{
			this.irisu_qt = Double.parseDouble(irisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setIrisuQt(double irisu_qt)
	{
		this.irisu_qt = irisu_qt;
		return true;
	}
	public double getIrisuQt()
	{
		return this.irisu_qt;
	}
	public String getIrisuQtString()
	{
		return Double.toString(this.irisu_qt);
	}


	// hachu_tani_qtに対するセッターとゲッターの集合
	public boolean setHachuTaniQt(String hachu_tani_qt)
	{
		try
		{
			this.hachu_tani_qt = Long.parseLong(hachu_tani_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHachuTaniQt(long hachu_tani_qt)
	{
		this.hachu_tani_qt = hachu_tani_qt;
		return true;
	}
	public long getHachuTaniQt()
	{
		return this.hachu_tani_qt;
	}
	public String getHachuTaniQtString()
	{
		return Long.toString(this.hachu_tani_qt);
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


	// kb_soba_kbに対するセッターとゲッターの集合
	public boolean setKbSobaKb(String kb_soba_kb)
	{
		this.kb_soba_kb = kb_soba_kb;
		return true;
	}
	public String getKbSobaKb()
	{
		return cutString(this.kb_soba_kb,KB_SOBA_KB_MAX_LENGTH);
	}
	public String getKbSobaKbString()
	{
		return "'" + cutString(this.kb_soba_kb,KB_SOBA_KB_MAX_LENGTH) + "'";
	}
	public String getKbSobaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kb_soba_kb,KB_SOBA_KB_MAX_LENGTH));
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


	// hachu_pattern_kbに対するセッターとゲッターの集合
	public boolean setHachuPatternKb(String hachu_pattern_kb)
	{
		this.hachu_pattern_kb = hachu_pattern_kb;
		return true;
	}
	public String getHachuPatternKb()
	{
		return cutString(this.hachu_pattern_kb,HACHU_PATTERN_KB_MAX_LENGTH);
	}
	public String getHachuPatternKbString()
	{
		return "'" + cutString(this.hachu_pattern_kb,HACHU_PATTERN_KB_MAX_LENGTH) + "'";
	}
	public String getHachuPatternKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_kb,HACHU_PATTERN_KB_MAX_LENGTH));
	}


	// simejikan_tmに対するセッターとゲッターの集合
	public boolean setSimejikanTm(String simejikan_tm)
	{
		this.simejikan_tm = simejikan_tm;
		return true;
	}
	public String getSimejikanTm()
	{
		return cutString(this.simejikan_tm,SIMEJIKAN_TM_MAX_LENGTH);
	}
	public String getSimejikanTmString()
	{
		return "'" + cutString(this.simejikan_tm,SIMEJIKAN_TM_MAX_LENGTH) + "'";
	}
	public String getSimejikanTmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.simejikan_tm,SIMEJIKAN_TM_MAX_LENGTH));
	}


	// zenjitu_tei_han_qtに対するセッターとゲッターの集合
	public boolean setZenjituTeiHanQt(String zenjitu_tei_han_qt)
	{
		try
		{
			this.zenjitu_tei_han_qt = Double.parseDouble(zenjitu_tei_han_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZenjituTeiHanQt(double zenjitu_tei_han_qt)
	{
		this.zenjitu_tei_han_qt = zenjitu_tei_han_qt;
		return true;
	}
	public double getZenjituTeiHanQt()
	{
		return this.zenjitu_tei_han_qt;
	}
	public String getZenjituTeiHanQtString()
	{
		return Double.toString(this.zenjitu_tei_han_qt);
	}


	// zenjitu_tei_han_vlに対するセッターとゲッターの集合
	public boolean setZenjituTeiHanVl(String zenjitu_tei_han_vl)
	{
		try
		{
			this.zenjitu_tei_han_vl = Long.parseLong(zenjitu_tei_han_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZenjituTeiHanVl(long zenjitu_tei_han_vl)
	{
		this.zenjitu_tei_han_vl = zenjitu_tei_han_vl;
		return true;
	}
	public long getZenjituTeiHanVl()
	{
		return this.zenjitu_tei_han_vl;
	}
	public String getZenjituTeiHanVlString()
	{
		return Long.toString(this.zenjitu_tei_han_vl);
	}


	// zensyu_tei_han_qtに対するセッターとゲッターの集合
	public boolean setZensyuTeiHanQt(String zensyu_tei_han_qt)
	{
		try
		{
			this.zensyu_tei_han_qt = Double.parseDouble(zensyu_tei_han_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZensyuTeiHanQt(double zensyu_tei_han_qt)
	{
		this.zensyu_tei_han_qt = zensyu_tei_han_qt;
		return true;
	}
	public double getZensyuTeiHanQt()
	{
		return this.zensyu_tei_han_qt;
	}
	public String getZensyuTeiHanQtString()
	{
		return Double.toString(this.zensyu_tei_han_qt);
	}


	// zensyu_tei_han_vlに対するセッターとゲッターの集合
	public boolean setZensyuTeiHanVl(String zensyu_tei_han_vl)
	{
		try
		{
			this.zensyu_tei_han_vl = Long.parseLong(zensyu_tei_han_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZensyuTeiHanVl(long zensyu_tei_han_vl)
	{
		this.zensyu_tei_han_vl = zensyu_tei_han_vl;
		return true;
	}
	public long getZensyuTeiHanVl()
	{
		return this.zensyu_tei_han_vl;
	}
	public String getZensyuTeiHanVlString()
	{
		return Long.toString(this.zensyu_tei_han_vl);
	}


	// zenjitu_toku_han_qtに対するセッターとゲッターの集合
	public boolean setZenjituTokuHanQt(String zenjitu_toku_han_qt)
	{
		try
		{
			this.zenjitu_toku_han_qt = Double.parseDouble(zenjitu_toku_han_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZenjituTokuHanQt(double zenjitu_toku_han_qt)
	{
		this.zenjitu_toku_han_qt = zenjitu_toku_han_qt;
		return true;
	}
	public double getZenjituTokuHanQt()
	{
		return this.zenjitu_toku_han_qt;
	}
	public String getZenjituTokuHanQtString()
	{
		return Double.toString(this.zenjitu_toku_han_qt);
	}


	// zenjitu_toku_han_vlに対するセッターとゲッターの集合
	public boolean setZenjituTokuHanVl(String zenjitu_toku_han_vl)
	{
		try
		{
			this.zenjitu_toku_han_vl = Long.parseLong(zenjitu_toku_han_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZenjituTokuHanVl(long zenjitu_toku_han_vl)
	{
		this.zenjitu_toku_han_vl = zenjitu_toku_han_vl;
		return true;
	}
	public long getZenjituTokuHanVl()
	{
		return this.zenjitu_toku_han_vl;
	}
	public String getZenjituTokuHanVlString()
	{
		return Long.toString(this.zenjitu_toku_han_vl);
	}


	// zensyu_toku_han_qtに対するセッターとゲッターの集合
	public boolean setZensyuTokuHanQt(String zensyu_toku_han_qt)
	{
		try
		{
			this.zensyu_toku_han_qt = Double.parseDouble(zensyu_toku_han_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZensyuTokuHanQt(double zensyu_toku_han_qt)
	{
		this.zensyu_toku_han_qt = zensyu_toku_han_qt;
		return true;
	}
	public double getZensyuTokuHanQt()
	{
		return this.zensyu_toku_han_qt;
	}
	public String getZensyuTokuHanQtString()
	{
		return Double.toString(this.zensyu_toku_han_qt);
	}


	// zensyu_toku_han_vlに対するセッターとゲッターの集合
	public boolean setZensyuTokuHanVl(String zensyu_toku_han_vl)
	{
		try
		{
			this.zensyu_toku_han_vl = Long.parseLong(zensyu_toku_han_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZensyuTokuHanVl(long zensyu_toku_han_vl)
	{
		this.zensyu_toku_han_vl = zensyu_toku_han_vl;
		return true;
	}
	public long getZensyuTokuHanVl()
	{
		return this.zensyu_toku_han_vl;
	}
	public String getZensyuTokuHanVlString()
	{
		return Long.toString(this.zensyu_toku_han_vl);
	}


	// baihen_suryo_qtに対するセッターとゲッターの集合
	public boolean setBaihenSuryoQt(String baihen_suryo_qt)
	{
		try
		{
			this.baihen_suryo_qt = Double.parseDouble(baihen_suryo_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaihenSuryoQt(double baihen_suryo_qt)
	{
		this.baihen_suryo_qt = baihen_suryo_qt;
		return true;
	}
	public double getBaihenSuryoQt()
	{
		return this.baihen_suryo_qt;
	}
	public String getBaihenSuryoQtString()
	{
		return Double.toString(this.baihen_suryo_qt);
	}


	// baihen_suryo_worstに対するセッターとゲッターの集合
	public boolean setBaihenSuryoWorst(String baihen_suryo_worst)
	{
		try
		{
			this.baihen_suryo_worst = Long.parseLong(baihen_suryo_worst);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaihenSuryoWorst(long baihen_suryo_worst)
	{
		this.baihen_suryo_worst = baihen_suryo_worst;
		return true;
	}
	public long getBaihenSuryoWorst()
	{
		return this.baihen_suryo_worst;
	}
	public String getBaihenSuryoWorstString()
	{
		return Long.toString(this.baihen_suryo_worst);
	}


	// baihen_vlに対するセッターとゲッターの集合
	public boolean setBaihenVl(String baihen_vl)
	{
		try
		{
			this.baihen_vl = Long.parseLong(baihen_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaihenVl(long baihen_vl)
	{
		this.baihen_vl = baihen_vl;
		return true;
	}
	public long getBaihenVl()
	{
		return this.baihen_vl;
	}
	public String getBaihenVlString()
	{
		return Long.toString(this.baihen_vl);
	}


	// baihen_vl_worstに対するセッターとゲッターの集合
	public boolean setBaihenVlWorst(String baihen_vl_worst)
	{
		try
		{
			this.baihen_vl_worst = Long.parseLong(baihen_vl_worst);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaihenVlWorst(long baihen_vl_worst)
	{
		this.baihen_vl_worst = baihen_vl_worst;
		return true;
	}
	public long getBaihenVlWorst()
	{
		return this.baihen_vl_worst;
	}
	public String getBaihenVlWorstString()
	{
		return Long.toString(this.baihen_vl_worst);
	}


	// riyo_user_idに対するセッターとゲッターの集合
	public boolean setRiyoUserId(String riyo_user_id)
	{
		this.riyo_user_id = riyo_user_id;
		return true;
	}
	public String getRiyoUserId()
	{
		return cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH);
	}
	public String getRiyoUserIdString()
	{
		return "'" + cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
	}
	public String getRiyoUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH));
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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  tenhanku_cd = " + getTenhankuCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  std_torihikisaki_cd = " + getStdTorihikisakiCdString()
			+ "  santi_cd = " + getSantiCdString()
			+ "  tokaikyu_cd = " + getTokaikyuCdString()
			+ "  kikaku_cd = " + getKikakuCdString()
			+ "  kakutei_kb = " + getKakuteiKbString()
			+ "  kakutei_seq = " + getKakuteiSeqString()
			+ "  nohin_syohin_cd = " + getNohinSyohinCdString()
			+ "  pos_cd = " + getPosCdString()
			+ "  jisya_syohin_cd = " + getJisyaSyohinCdString()
			+ "  syohin_na = " + getSyohinNaString()
			+ "  syohin_ka = " + getSyohinKaString()
			+ "  hachu_tani_na = " + getHachuTaniNaString()
			+ "  hachu_tani_ka = " + getHachuTaniKaString()
			+ "  santi_na = " + getSantiNaString()
			+ "  tokaikyu_na = " + getTokaikyuNaString()
			+ "  kikaku_na = " + getKikakuNaString()
			+ "  torihikisaki_na = " + getTorihikisakiNaString()
			+ "  torihikisaki_ka = " + getTorihikisakiKaString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hinmoku_cd = " + getHinmokuCdString()
			+ "  irisu_qt = " + getIrisuQtString()
			+ "  hachu_tani_qt = " + getHachuTaniQtString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  kb_soba_kb = " + getKbSobaKbString()
			+ "  teikan_kb = " + getTeikanKbString()
			+ "  eos_kb = " + getEosKbString()
			+ "  hachu_pattern_kb = " + getHachuPatternKbString()
			+ "  simejikan_tm = " + getSimejikanTmString()
			+ "  zenjitu_tei_han_qt = " + getZenjituTeiHanQtString()
			+ "  zenjitu_tei_han_vl = " + getZenjituTeiHanVlString()
			+ "  zensyu_tei_han_qt = " + getZensyuTeiHanQtString()
			+ "  zensyu_tei_han_vl = " + getZensyuTeiHanVlString()
			+ "  zenjitu_toku_han_qt = " + getZenjituTokuHanQtString()
			+ "  zenjitu_toku_han_vl = " + getZenjituTokuHanVlString()
			+ "  zensyu_toku_han_qt = " + getZensyuTokuHanQtString()
			+ "  zensyu_toku_han_vl = " + getZensyuTokuHanVlString()
			+ "  baihen_suryo_qt = " + getBaihenSuryoQtString()
			+ "  baihen_suryo_worst = " + getBaihenSuryoWorstString()
			+ "  baihen_vl = " + getBaihenVlString()
			+ "  baihen_vl_worst = " + getBaihenVlWorstString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return DtFreshHachuBean コピー後のクラス
	 */
	public DtFreshHachuBean createClone()
	{
		DtFreshHachuBean bean = new DtFreshHachuBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenhankuCd(this.tenhanku_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setStdTorihikisakiCd(this.std_torihikisaki_cd);
		bean.setSantiCd(this.santi_cd);
		bean.setTokaikyuCd(this.tokaikyu_cd);
		bean.setKikakuCd(this.kikaku_cd);
		bean.setKakuteiKb(this.kakutei_kb);
		bean.setKakuteiSeq(this.kakutei_seq);
		bean.setNohinSyohinCd(this.nohin_syohin_cd);
		bean.setPosCd(this.pos_cd);
		bean.setJisyaSyohinCd(this.jisya_syohin_cd);
		bean.setSyohinNa(this.syohin_na);
		bean.setSyohinKa(this.syohin_ka);
		bean.setHachuTaniNa(this.hachu_tani_na);
		bean.setHachuTaniKa(this.hachu_tani_ka);
		bean.setSantiNa(this.santi_na);
		bean.setTokaikyuNa(this.tokaikyu_na);
		bean.setKikakuNa(this.kikaku_na);
		bean.setTorihikisakiNa(this.torihikisaki_na);
		bean.setTorihikisakiKa(this.torihikisaki_ka);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinmokuCd(this.hinmoku_cd);
		bean.setIrisuQt(this.irisu_qt);
		bean.setHachuTaniQt(this.hachu_tani_qt);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setKbSobaKb(this.kb_soba_kb);
		bean.setTeikanKb(this.teikan_kb);
		bean.setEosKb(this.eos_kb);
		bean.setHachuPatternKb(this.hachu_pattern_kb);
		bean.setSimejikanTm(this.simejikan_tm);
		bean.setZenjituTeiHanQt(this.zenjitu_tei_han_qt);
		bean.setZenjituTeiHanVl(this.zenjitu_tei_han_vl);
		bean.setZensyuTeiHanQt(this.zensyu_tei_han_qt);
		bean.setZensyuTeiHanVl(this.zensyu_tei_han_vl);
		bean.setZenjituTokuHanQt(this.zenjitu_toku_han_qt);
		bean.setZenjituTokuHanVl(this.zenjitu_toku_han_vl);
		bean.setZensyuTokuHanQt(this.zensyu_toku_han_qt);
		bean.setZensyuTokuHanVl(this.zensyu_toku_han_vl);
		bean.setBaihenSuryoQt(this.baihen_suryo_qt);
		bean.setBaihenSuryoWorst(this.baihen_suryo_worst);
		bean.setBaihenVl(this.baihen_vl);
		bean.setBaihenVlWorst(this.baihen_vl_worst);
		bean.setRiyoUserId(this.riyo_user_id);
		bean.setInsertTs(this.insert_ts);
		bean.setUpdateTs(this.update_ts);
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
		if( !( o instanceof DtFreshHachuBean ) ) return false;
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
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
