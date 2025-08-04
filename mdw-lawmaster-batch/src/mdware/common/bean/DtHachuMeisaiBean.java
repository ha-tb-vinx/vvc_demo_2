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
public class DtHachuMeisaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int DATA_DENP_NB_MAX_LENGTH = 20;
	public static final int TENPO_CD_MAX_LENGTH = 10;
	public static final int TENHANKU_CD_MAX_LENGTH = 4;
	public static final int L_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int L_HANKU_CD_MAX_LENGTH = 4;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH = 11;
	public static final int NOHIN_DT_MAX_LENGTH = 8;
	public static final int DENPYO_NB_MAX_LENGTH = 10;
	public static final int SYOHIN_CD_MAX_LENGTH = 20;
	public static final int JISYA_SYOHIN_CD_MAX_LENGTH = 20;
	public static final int NOHIN_SYOHIN_CD_MAX_LENGTH = 20;
	public static final int POS_CD_MAX_LENGTH = 20;
	public static final int HINSYU_CD_MAX_LENGTH = 4;
	public static final int TENPO_NA_MAX_LENGTH = 80;
	public static final int SYOHIN_NA_MAX_LENGTH = 80;
	public static final int SYOHIN_KA_MAX_LENGTH = 50;
	public static final int KIKAKU_KA_MAX_LENGTH = 6;
	public static final int HACHU_TANI_NA_MAX_LENGTH = 40;
	public static final int HACHU_TANI_KA_MAX_LENGTH = 20;
	public static final int SANTI_CD_MAX_LENGTH = 4;
	public static final int TOKAIKYU_CD_MAX_LENGTH = 4;
	public static final int KIKAKU_CD_MAX_LENGTH = 4;
	public static final int SANTI_NA_MAX_LENGTH = 20;
	public static final int TOKAIKYU_NA_MAX_LENGTH = 20;
	public static final int KIKAKU_NA_MAX_LENGTH = 20;
	public static final int COLOR_CD_MAX_LENGTH = 2;
	public static final int SIZE_CD_MAX_LENGTH = 2;
	public static final int COLOR_KA_MAX_LENGTH = 5;
	public static final int SIZE_KA_MAX_LENGTH = 7;
	public static final int KEIRETU_KB_MAX_LENGTH = 1;
	public static final int SYOHIN_KIKAKU_NA_MAX_LENGTH = 20;
	public static final int TORIHIKISAKI_SYOHIN_CD_MAX_LENGTH = 20;
	public static final int TEIKAN_KB_MAX_LENGTH = 1;
	public static final int HANBAI_SEISAKU_KB_MAX_LENGTH = 1;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String data_denp_nb = null;
	private long data_denpgyo_nb = 0;
	private String tenpo_cd = null;
	private String tenhanku_cd = null;
	private String l_gyosyu_cd = null;
	private String s_gyosyu_cd = null;
	private String l_hanku_cd = null;
	private String torihikisaki_cd = null;
	private String nohin_dt = null;
	private String denpyo_nb = null;
	private long denpyogyo_nb = 0;
	private String syohin_cd = null;
	private String jisya_syohin_cd = null;
	private String nohin_syohin_cd = null;
	private String pos_cd = null;
	private String hinsyu_cd = null;
	private String tenpo_na = null;
	private String syohin_na = null;
	private String syohin_ka = null;
	private String kikaku_ka = null;
	private String hachu_tani_na = null;
	private String hachu_tani_ka = null;
	private String santi_cd = null;
	private String tokaikyu_cd = null;
	private String kikaku_cd = null;
	private String santi_na = null;
	private String tokaikyu_na = null;
	private String kikaku_na = null;
	private String color_cd = null;
	private String size_cd = null;
	private String color_ka = null;
	private String size_ka = null;
	private String keiretu_kb = null;
	private String syohin_kikaku_na = null;
	private String torihikisaki_syohin_cd = null;
	private String teikan_kb = null;
	private String hanbai_seisaku_kb = null;
	private double gentanka_vl = 0;
	private long baitanka_vl = 0;
	private double syuko_tanka_vl = 0;
	private double irisu_qt = 0;
	private long hachu_tani_qt = 0;
	private long hachu_qt = 0;
	private double hachu_suryo_qt = 0;
	private long kakutei_qt = 0;
	private double kakutei_suryo_qt = 0;
	private double genka_vl = 0;
	private double zeigaku_vl = 0;
	private long baika_vl = 0;
	private double syuko_vl = 0;
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
	 * DtHachuMeisaiBeanを１件のみ抽出したい時に使用する
	 */
	public static DtHachuMeisaiBean getDtHachuMeisaiBean(DataHolder dataHolder)
	{
		DtHachuMeisaiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new DtHachuMeisaiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (DtHachuMeisaiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// data_denp_nbに対するセッターとゲッターの集合
	public boolean setDataDenpNb(String data_denp_nb)
	{
		this.data_denp_nb = data_denp_nb;
		return true;
	}
	public String getDataDenpNb()
	{
		return cutString(this.data_denp_nb,DATA_DENP_NB_MAX_LENGTH);
	}
	public String getDataDenpNbString()
	{
		return "'" + cutString(this.data_denp_nb,DATA_DENP_NB_MAX_LENGTH) + "'";
	}
	public String getDataDenpNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.data_denp_nb,DATA_DENP_NB_MAX_LENGTH));
	}


	// data_denpgyo_nbに対するセッターとゲッターの集合
	public boolean setDataDenpgyoNb(String data_denpgyo_nb)
	{
		try
		{
			this.data_denpgyo_nb = Long.parseLong(data_denpgyo_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setDataDenpgyoNb(long data_denpgyo_nb)
	{
		this.data_denpgyo_nb = data_denpgyo_nb;
		return true;
	}
	public long getDataDenpgyoNb()
	{
		return this.data_denpgyo_nb;
	}
	public String getDataDenpgyoNbString()
	{
		return Long.toString(this.data_denpgyo_nb);
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


	// l_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setLGyosyuCd(String l_gyosyu_cd)
	{
		this.l_gyosyu_cd = l_gyosyu_cd;
		return true;
	}
	public String getLGyosyuCd()
	{
		return cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH);
	}
	public String getLGyosyuCdString()
	{
		return "'" + cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getLGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH));
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


	// l_hanku_cdに対するセッターとゲッターの集合
	public boolean setLHankuCd(String l_hanku_cd)
	{
		this.l_hanku_cd = l_hanku_cd;
		return true;
	}
	public String getLHankuCd()
	{
		return cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH);
	}
	public String getLHankuCdString()
	{
		return "'" + cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getLHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH));
	}


	// torihikisaki_cdに対するセッターとゲッターの集合
	public boolean setTorihikisakiCd(String torihikisaki_cd)
	{
		this.torihikisaki_cd = torihikisaki_cd;
		return true;
	}
	public String getTorihikisakiCd()
	{
		return cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH);
	}
	public String getTorihikisakiCdString()
	{
		return "'" + cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH));
	}


	// nohin_dtに対するセッターとゲッターの集合
	public boolean setNohinDt(String nohin_dt)
	{
		this.nohin_dt = nohin_dt;
		return true;
	}
	public String getNohinDt()
	{
		return cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH);
	}
	public String getNohinDtString()
	{
		return "'" + cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH) + "'";
	}
	public String getNohinDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH));
	}


	// denpyo_nbに対するセッターとゲッターの集合
	public boolean setDenpyoNb(String denpyo_nb)
	{
		this.denpyo_nb = denpyo_nb;
		return true;
	}
	public String getDenpyoNb()
	{
		return cutString(this.denpyo_nb,DENPYO_NB_MAX_LENGTH);
	}
	public String getDenpyoNbString()
	{
		return "'" + cutString(this.denpyo_nb,DENPYO_NB_MAX_LENGTH) + "'";
	}
	public String getDenpyoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.denpyo_nb,DENPYO_NB_MAX_LENGTH));
	}


	// denpyogyo_nbに対するセッターとゲッターの集合
	public boolean setDenpyogyoNb(String denpyogyo_nb)
	{
		try
		{
			this.denpyogyo_nb = Long.parseLong(denpyogyo_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setDenpyogyoNb(long denpyogyo_nb)
	{
		this.denpyogyo_nb = denpyogyo_nb;
		return true;
	}
	public long getDenpyogyoNb()
	{
		return this.denpyogyo_nb;
	}
	public String getDenpyogyoNbString()
	{
		return Long.toString(this.denpyogyo_nb);
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


	// tenpo_naに対するセッターとゲッターの集合
	public boolean setTenpoNa(String tenpo_na)
	{
		this.tenpo_na = tenpo_na;
		return true;
	}
	public String getTenpoNa()
	{
		return cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH);
	}
	public String getTenpoNaString()
	{
		return "'" + cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH) + "'";
	}
	public String getTenpoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH));
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


	// kikaku_kaに対するセッターとゲッターの集合
	public boolean setKikakuKa(String kikaku_ka)
	{
		this.kikaku_ka = kikaku_ka;
		return true;
	}
	public String getKikakuKa()
	{
		return cutString(this.kikaku_ka,KIKAKU_KA_MAX_LENGTH);
	}
	public String getKikakuKaString()
	{
		return "'" + cutString(this.kikaku_ka,KIKAKU_KA_MAX_LENGTH) + "'";
	}
	public String getKikakuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_ka,KIKAKU_KA_MAX_LENGTH));
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


	// color_kaに対するセッターとゲッターの集合
	public boolean setColorKa(String color_ka)
	{
		this.color_ka = color_ka;
		return true;
	}
	public String getColorKa()
	{
		return cutString(this.color_ka,COLOR_KA_MAX_LENGTH);
	}
	public String getColorKaString()
	{
		return "'" + cutString(this.color_ka,COLOR_KA_MAX_LENGTH) + "'";
	}
	public String getColorKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_ka,COLOR_KA_MAX_LENGTH));
	}


	// size_kaに対するセッターとゲッターの集合
	public boolean setSizeKa(String size_ka)
	{
		this.size_ka = size_ka;
		return true;
	}
	public String getSizeKa()
	{
		return cutString(this.size_ka,SIZE_KA_MAX_LENGTH);
	}
	public String getSizeKaString()
	{
		return "'" + cutString(this.size_ka,SIZE_KA_MAX_LENGTH) + "'";
	}
	public String getSizeKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_ka,SIZE_KA_MAX_LENGTH));
	}


	// keiretu_kbに対するセッターとゲッターの集合
	public boolean setKeiretuKb(String keiretu_kb)
	{
		this.keiretu_kb = keiretu_kb;
		return true;
	}
	public String getKeiretuKb()
	{
		return cutString(this.keiretu_kb,SIZE_KA_MAX_LENGTH);
	}
	public String getKeiretuKbString()
	{
		return "'" + cutString(this.keiretu_kb,SIZE_KA_MAX_LENGTH) + "'";
	}
	public String getKeiretuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiretu_kb,SIZE_KA_MAX_LENGTH));
	}


	// syohin_kikaku_naに対するセッターとゲッターの集合
	public boolean setSyohinKikakuNa(String syohin_kikaku_na)
	{
		this.syohin_kikaku_na = syohin_kikaku_na;
		return true;
	}
	public String getSyohinKikakuNa()
	{
		return cutString(this.syohin_kikaku_na,SYOHIN_KIKAKU_NA_MAX_LENGTH);
	}
	public String getSyohinKikakuNaString()
	{
		return "'" + cutString(this.syohin_kikaku_na,SYOHIN_KIKAKU_NA_MAX_LENGTH) + "'";
	}
	public String getSyohinKikakuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_kikaku_na,SYOHIN_KIKAKU_NA_MAX_LENGTH));
	}


	// torihikisaki_syohin_cdに対するセッターとゲッターの集合
	public boolean setTorihikisakiSyohinCd(String torihikisaki_syohin_cd)
	{
		this.torihikisaki_syohin_cd = torihikisaki_syohin_cd;
		return true;
	}
	public String getTorihikisakiSyohinCd()
	{
		return cutString(this.torihikisaki_syohin_cd,TORIHIKISAKI_SYOHIN_CD_MAX_LENGTH);
	}
	public String getTorihikisakiSyohinCdString()
	{
		return "'" + cutString(this.torihikisaki_syohin_cd,TORIHIKISAKI_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torihikisaki_syohin_cd,TORIHIKISAKI_SYOHIN_CD_MAX_LENGTH));
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


	// syuko_tanka_vlに対するセッターとゲッターの集合
	public boolean setSyukoTankaVl(String syuko_tanka_vl)
	{
		try
		{
			this.syuko_tanka_vl = Double.parseDouble(syuko_tanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyukoTankaVl(double syuko_tanka_vl)
	{
		this.syuko_tanka_vl = syuko_tanka_vl;
		return true;
	}
	public double getSyukoTankaVl()
	{
		return this.syuko_tanka_vl;
	}
	public String getSyukoTankaVlString()
	{
		return Double.toString(this.syuko_tanka_vl);
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


	// hachu_qtに対するセッターとゲッターの集合
	public boolean setHachuQt(String hachu_qt)
	{
		try
		{
			this.hachu_qt = Long.parseLong(hachu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHachuQt(long hachu_qt)
	{
		this.hachu_qt = hachu_qt;
		return true;
	}
	public long getHachuQt()
	{
		return this.hachu_qt;
	}
	public String getHachuQtString()
	{
		return Long.toString(this.hachu_qt);
	}


	// hachu_suryo_qtに対するセッターとゲッターの集合
	public boolean setHachuSuryoQt(String hachu_suryo_qt)
	{
		try
		{
			this.hachu_suryo_qt = Double.parseDouble(hachu_suryo_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHachuSuryoQt(double hachu_suryo_qt)
	{
		this.hachu_suryo_qt = hachu_suryo_qt;
		return true;
	}
	public double getHachuSuryoQt()
	{
		return this.hachu_suryo_qt;
	}
	public String getHachuSuryoQtString()
	{
		return Double.toString(this.hachu_suryo_qt);
	}


	// kakutei_qtに対するセッターとゲッターの集合
	public boolean setKakuteiQt(String kakutei_qt)
	{
		try
		{
			this.kakutei_qt = Long.parseLong(kakutei_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setKakuteiQt(long kakutei_qt)
	{
		this.kakutei_qt = kakutei_qt;
		return true;
	}
	public long getKakuteiQt()
	{
		return this.kakutei_qt;
	}
	public String getKakuteiQtString()
	{
		return Long.toString(this.kakutei_qt);
	}


	// kakutei_suryo_qtに対するセッターとゲッターの集合
	public boolean setKakuteiSuryoQt(String kakutei_suryo_qt)
	{
		try
		{
			this.kakutei_suryo_qt = Double.parseDouble(kakutei_suryo_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setKakuteiSuryoQt(double kakutei_suryo_qt)
	{
		this.kakutei_suryo_qt = kakutei_suryo_qt;
		return true;
	}
	public double getKakuteiSuryoQt()
	{
		return this.kakutei_suryo_qt;
	}
	public String getKakuteiSuryoQtString()
	{
		return Double.toString(this.kakutei_suryo_qt);
	}


	// genka_vlに対するセッターとゲッターの集合
	public boolean setGenkaVl(String genka_vl)
	{
		try
		{
			this.genka_vl = Double.parseDouble(genka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGenkaVl(double genka_vl)
	{
		this.genka_vl = genka_vl;
		return true;
	}
	public double getGenkaVl()
	{
		return this.genka_vl;
	}
	public String getGenkaVlString()
	{
		return Double.toString(this.genka_vl);
	}


	// zeigaku_vlに対するセッターとゲッターの集合
	public boolean setZeigakuVl(String zeigaku_vl)
	{
		try
		{
			this.zeigaku_vl = Double.parseDouble(zeigaku_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setZeigakuVl(double zeigaku_vl)
	{
		this.zeigaku_vl = zeigaku_vl;
		return true;
	}
	public double getZeigakuVl()
	{
		return this.zeigaku_vl;
	}
	public String getZeigakuVlString()
	{
		return Double.toString(this.zeigaku_vl);
	}


	// baika_vlに対するセッターとゲッターの集合
	public boolean setBaikaVl(String baika_vl)
	{
		try
		{
			this.baika_vl = Long.parseLong(baika_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaikaVl(long baika_vl)
	{
		this.baika_vl = baika_vl;
		return true;
	}
	public long getBaikaVl()
	{
		return this.baika_vl;
	}
	public String getBaikaVlString()
	{
		return Long.toString(this.baika_vl);
	}


	// syuko_vlに対するセッターとゲッターの集合
	public boolean setSyukoVl(String syuko_vl)
	{
		try
		{
			this.syuko_vl = Double.parseDouble(syuko_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyukoVl(double syuko_vl)
	{
		this.syuko_vl = syuko_vl;
		return true;
	}
	public double getSyukoVl()
	{
		return this.syuko_vl;
	}
	public String getSyukoVlString()
	{
		return Double.toString(this.syuko_vl);
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
		return "  data_denp_nb = " + getDataDenpNbString()
			+ "  data_denpgyo_nb = " + getDataDenpgyoNbString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenhanku_cd = " + getTenhankuCdString()
			+ "  l_gyosyu_cd = " + getLGyosyuCdString()
			+ "  s_gyosyu_cd = " + getSGyosyuCdString()
			+ "  l_hanku_cd = " + getLHankuCdString()
			+ "  torihikisaki_cd = " + getTorihikisakiCdString()
			+ "  nohin_dt = " + getNohinDtString()
			+ "  denpyo_nb = " + getDenpyoNbString()
			+ "  denpyogyo_nb = " + getDenpyogyoNbString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  jisya_syohin_cd = " + getJisyaSyohinCdString()
			+ "  nohin_syohin_cd = " + getNohinSyohinCdString()
			+ "  pos_cd = " + getPosCdString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  tenpo_na = " + getTenpoNaString()
			+ "  syohin_na = " + getSyohinNaString()
			+ "  syohin_ka = " + getSyohinKaString()
			+ "  kikaku_ka = " + getKikakuKaString()
			+ "  hachu_tani_na = " + getHachuTaniNaString()
			+ "  hachu_tani_ka = " + getHachuTaniKaString()
			+ "  santi_cd = " + getSantiCdString()
			+ "  tokaikyu_cd = " + getTokaikyuCdString()
			+ "  kikaku_cd = " + getKikakuCdString()
			+ "  santi_na = " + getSantiNaString()
			+ "  tokaikyu_na = " + getTokaikyuNaString()
			+ "  kikaku_na = " + getKikakuNaString()
			+ "  color_cd = " + getColorCdString()
			+ "  size_cd = " + getSizeCdString()
			+ "  color_ka = " + getColorKaString()
			+ "  size_ka = " + getSizeKaString()
			+ "  keiretu_kb = " + getKeiretuKbString()
			+ "  syohin_kikaku_na = " + getSyohinKikakuNaString()
			+ "  torihikisaki_syohin_cd = " + getTorihikisakiSyohinCdString()
			+ "  teikan_kb = " + getTeikanKbString()
			+ "  hanbai_seisaku_kb = " + getHanbaiSeisakuKbString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  syuko_tanka_vl = " + getSyukoTankaVlString()
			+ "  irisu_qt = " + getIrisuQtString()
			+ "  hachu_tani_qt = " + getHachuTaniQtString()
			+ "  hachu_qt = " + getHachuQtString()
			+ "  hachu_suryo_qt = " + getHachuSuryoQtString()
			+ "  kakutei_qt = " + getKakuteiQtString()
			+ "  kakutei_suryo_qt = " + getKakuteiSuryoQtString()
			+ "  genka_vl = " + getGenkaVlString()
			+ "  zeigaku_vl = " + getZeigakuVlString()
			+ "  baika_vl = " + getBaikaVlString()
			+ "  syuko_vl = " + getSyukoVlString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return DtHachuMeisaiBean コピー後のクラス
	 */
	public DtHachuMeisaiBean createClone()
	{
		DtHachuMeisaiBean bean = new DtHachuMeisaiBean();
		bean.setDataDenpNb(this.data_denp_nb);
		bean.setDataDenpgyoNb(this.data_denpgyo_nb);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenhankuCd(this.tenhanku_cd);
		bean.setLGyosyuCd(this.l_gyosyu_cd);
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setLHankuCd(this.l_hanku_cd);
		bean.setTorihikisakiCd(this.torihikisaki_cd);
		bean.setNohinDt(this.nohin_dt);
		bean.setDenpyoNb(this.denpyo_nb);
		bean.setDenpyogyoNb(this.denpyogyo_nb);
		bean.setSyohinCd(this.syohin_cd);
		bean.setJisyaSyohinCd(this.jisya_syohin_cd);
		bean.setNohinSyohinCd(this.nohin_syohin_cd);
		bean.setPosCd(this.pos_cd);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setTenpoNa(this.tenpo_na);
		bean.setSyohinNa(this.syohin_na);
		bean.setSyohinKa(this.syohin_ka);
		bean.setKikakuKa(this.kikaku_ka);
		bean.setHachuTaniNa(this.hachu_tani_na);
		bean.setHachuTaniKa(this.hachu_tani_ka);
		bean.setSantiCd(this.santi_cd);
		bean.setTokaikyuCd(this.tokaikyu_cd);
		bean.setKikakuCd(this.kikaku_cd);
		bean.setSantiNa(this.santi_na);
		bean.setTokaikyuNa(this.tokaikyu_na);
		bean.setKikakuNa(this.kikaku_na);
		bean.setColorCd(this.color_cd);
		bean.setSizeCd(this.size_cd);
		bean.setColorKa(this.color_ka);
		bean.setSizeKa(this.size_ka);
		bean.setKeiretuKb(this.keiretu_kb);
		bean.setSyohinKikakuNa(this.syohin_kikaku_na);
		bean.setTorihikisakiSyohinCd(this.torihikisaki_syohin_cd);
		bean.setTeikanKb(this.teikan_kb);
		bean.setHanbaiSeisakuKb(this.hanbai_seisaku_kb);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setSyukoTankaVl(this.syuko_tanka_vl);
		bean.setIrisuQt(this.irisu_qt);
		bean.setHachuTaniQt(this.hachu_tani_qt);
		bean.setHachuQt(this.hachu_qt);
		bean.setHachuSuryoQt(this.hachu_suryo_qt);
		bean.setKakuteiQt(this.kakutei_qt);
		bean.setKakuteiSuryoQt(this.kakutei_suryo_qt);
		bean.setGenkaVl(this.genka_vl);
		bean.setZeigakuVl(this.zeigaku_vl);
		bean.setBaikaVl(this.baika_vl);
		bean.setSyukoVl(this.syuko_vl);
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
		if( !( o instanceof DtHachuMeisaiBean ) ) return false;
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
