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
public class DtHachuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int DATA_DENP_NB_MAX_LENGTH = 20;
	public static final int FILE_HEAD_NB_MAX_LENGTH = 20;
	public static final int GAITO_SYSTEM_KB_MAX_LENGTH = 1;
	public static final int TENPO_CD_MAX_LENGTH = 10;
	public static final int TENHANKU_CD_MAX_LENGTH = 4;
	public static final int L_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int L_HANKU_CD_MAX_LENGTH = 4;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH = 11;
	public static final int NOHIN_DT_MAX_LENGTH = 8;
	public static final int DENPYO_NB_MAX_LENGTH = 10;
	public static final int HACHU_DT_MAX_LENGTH = 8;
	public static final int KB_BUSYO_KB_MAX_LENGTH = 2;
	public static final int BIN_NB_MAX_LENGTH = 10;
	public static final int TEI_BIN_NB_MAX_LENGTH = 10;
	public static final int TENPO_NA_MAX_LENGTH = 80;
	public static final int TENPO_KA_MAX_LENGTH = 40;
	public static final int TENHANKU_NA_MAX_LENGTH = 36;
	public static final int S_GYOSYU_NA_MAX_LENGTH = 36;
	public static final int TORIHIKISAKI_NA_MAX_LENGTH = 60;
	public static final int TORIHIKISAKI_KA_MAX_LENGTH = 30;
	public static final int SEISEN_FG_MAX_LENGTH = 1;
	public static final int KB_SOBA_KB_MAX_LENGTH = 1;
	public static final int HAISO_KB_MAX_LENGTH = 1;
	public static final int TEI_HAISO_KB_MAX_LENGTH = 1;
	public static final int DENPYO_KB_MAX_LENGTH = 2;
	public static final int ZEI_KB_MAX_LENGTH = 1;
	public static final int HACHU_KB_MAX_LENGTH = 1;
	public static final int TENPO_HACHU_KB_MAX_LENGTH = 2;
	public static final int IKATU_DENP_FG_MAX_LENGTH = 1;
	public static final int SYOHIN_THEME_NA_MAX_LENGTH = 12;
	public static final int HAISOSAKI_CD_MAX_LENGTH = 5;
	public static final int HAISOSAKI_NA_MAX_LENGTH = 8;
	public static final int NOHIN_CENTER_CD_MAX_LENGTH = 4;
	public static final int KEIYU_CENTER_CD_MAX_LENGTH = 4;
	public static final int TENHAI_CENTER_CD_MAX_LENGTH = 4;
	public static final int ZAIKO_CENTER_CD_MAX_LENGTH = 4;
	public static final int TEI_NOHIN_CENTER_CD_MAX_LENGTH = 4;
	public static final int TEI_KEIYU_CENTER_CD_MAX_LENGTH = 4;
	public static final int TEI_TENHAI_CENTER_CD_MAX_LENGTH = 4;
	public static final int TEI_ZAIKO_CENTER_CD_MAX_LENGTH = 4;
	public static final int JUCHU_LIST_OUTPUT_KB_MAX_LENGTH = 1;
	public static final int NOHIN_SYORI_KB_MAX_LENGTH = 1;
	public static final int HAISINSAKI_CD_MAX_LENGTH = 11;
	public static final int HOJIN_CD_MAX_LENGTH = 4;
	public static final int HOJIN_NA_MAX_LENGTH = 10;
	public static final int NOHIN_MAKE_FG_MAX_LENGTH = 1;
	public static final int NOHIN_MAKE_DT_MAX_LENGTH = 20;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String data_denp_nb = null;
	private String file_head_nb = null;
	private String gaito_system_kb = null;
	private String tenpo_cd = null;
	private String tenhanku_cd = null;
	private String l_gyosyu_cd = null;
	private String s_gyosyu_cd = null;
	private String l_hanku_cd = null;
	private String torihikisaki_cd = null;
	private String nohin_dt = null;
	private String denpyo_nb = null;
	private String hachu_dt = null;
	private String kb_busyo_kb = null;
	private String bin_nb = null;
	private String tei_bin_nb = null;
	private String tenpo_na = null;
	private String tenpo_ka = null;
	private String tenhanku_na = null;
	private String s_gyosyu_na = null;
	private String torihikisaki_na = null;
	private String torihikisaki_ka = null;
	private long genka_kei_vl = 0;
	private long baika_kei_vl = 0;
	private long tei_genka_kei_vl = 0;
	private long tei_baika_kei_vl = 0;
	private String seisen_fg = null;
	private String kb_soba_kb = null;
	private String haiso_kb = null;
	private String tei_haiso_kb = null;
	private String denpyo_kb = null;
	private String zei_kb = null;
	private String hachu_kb = null;
	private String tenpo_hachu_kb = null;
	private String ikatu_denp_fg = null;
	private String syohin_theme_na = null;
	private String haisosaki_cd = null;
	private String haisosaki_na = null;
	private String nohin_center_cd = null;
	private String keiyu_center_cd = null;
	private String tenhai_center_cd = null;
	private String zaiko_center_cd = null;
	private String tei_nohin_center_cd = null;
	private String tei_keiyu_center_cd = null;
	private String tei_tenhai_center_cd = null;
	private String tei_zaiko_center_cd = null;
	private String juchu_list_output_kb = null;
	private String nohin_syori_kb = null;
	private String haisinsaki_cd = null;
	private String hojin_cd = null;
	private String hojin_na = null;
	private String nohin_make_fg = null;
	private String nohin_make_dt = null;
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
	 * DtHachuBeanを１件のみ抽出したい時に使用する
	 */
	public static DtHachuBean getDtHachuBean(DataHolder dataHolder)
	{
		DtHachuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new DtHachuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (DtHachuBean )ite.next();
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


	// file_head_nbに対するセッターとゲッターの集合
	public boolean setFileHeadNb(String file_head_nb)
	{
		this.file_head_nb = file_head_nb;
		return true;
	}
	public String getFileHeadNb()
	{
		return cutString(this.file_head_nb,FILE_HEAD_NB_MAX_LENGTH);
	}
	public String getFileHeadNbString()
	{
		return "'" + cutString(this.file_head_nb,FILE_HEAD_NB_MAX_LENGTH) + "'";
	}
	public String getFileHeadNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.file_head_nb,FILE_HEAD_NB_MAX_LENGTH));
	}


	// gaito_system_kbに対するセッターとゲッターの集合
	public boolean setGaitoSystemKb(String gaito_system_kb)
	{
		this.gaito_system_kb = gaito_system_kb;
		return true;
	}
	public String getGaitoSystemKb()
	{
		return cutString(this.gaito_system_kb,GAITO_SYSTEM_KB_MAX_LENGTH);
	}
	public String getGaitoSystemKbString()
	{
		return "'" + cutString(this.gaito_system_kb,GAITO_SYSTEM_KB_MAX_LENGTH) + "'";
	}
	public String getGaitoSystemKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gaito_system_kb,GAITO_SYSTEM_KB_MAX_LENGTH));
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


	// hachu_dtに対するセッターとゲッターの集合
	public boolean setHachuDt(String hachu_dt)
	{
		this.hachu_dt = hachu_dt;
		return true;
	}
	public String getHachuDt()
	{
		return cutString(this.hachu_dt,HACHU_DT_MAX_LENGTH);
	}
	public String getHachuDtString()
	{
		return "'" + cutString(this.hachu_dt,HACHU_DT_MAX_LENGTH) + "'";
	}
	public String getHachuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_dt,HACHU_DT_MAX_LENGTH));
	}


	// kb_busyo_kbに対するセッターとゲッターの集合
	public boolean setKbBusyoKb(String kb_busyo_kb)
	{
		this.kb_busyo_kb = kb_busyo_kb;
		return true;
	}
	public String getKbBusyoKb()
	{
		return cutString(this.kb_busyo_kb,KB_BUSYO_KB_MAX_LENGTH);
	}
	public String getKbBusyoKbString()
	{
		return "'" + cutString(this.kb_busyo_kb,KB_BUSYO_KB_MAX_LENGTH) + "'";
	}
	public String getKbBusyoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kb_busyo_kb,KB_BUSYO_KB_MAX_LENGTH));
	}


	// bin_nbに対するセッターとゲッターの集合
	public boolean setBinNb(String bin_nb)
	{
		this.bin_nb = bin_nb;
		return true;
	}
	public String getBinNb()
	{
		return cutString(this.bin_nb,BIN_NB_MAX_LENGTH);
	}
	public String getBinNbString()
	{
		return "'" + cutString(this.bin_nb,BIN_NB_MAX_LENGTH) + "'";
	}
	public String getBinNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_nb,BIN_NB_MAX_LENGTH));
	}


	// tei_bin_nbに対するセッターとゲッターの集合
	public boolean setTeiBinNb(String tei_bin_nb)
	{
		this.tei_bin_nb = tei_bin_nb;
		return true;
	}
	public String getTeiBinNb()
	{
		return cutString(this.tei_bin_nb,TEI_BIN_NB_MAX_LENGTH);
	}
	public String getTeiBinNbString()
	{
		return "'" + cutString(this.tei_bin_nb,TEI_BIN_NB_MAX_LENGTH) + "'";
	}
	public String getTeiBinNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tei_bin_nb,TEI_BIN_NB_MAX_LENGTH));
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


	// tenpo_kaに対するセッターとゲッターの集合
	public boolean setTenpoKa(String tenpo_ka)
	{
		this.tenpo_ka = tenpo_ka;
		return true;
	}
	public String getTenpoKa()
	{
		return cutString(this.tenpo_ka,TENPO_KA_MAX_LENGTH);
	}
	public String getTenpoKaString()
	{
		return "'" + cutString(this.tenpo_ka,TENPO_KA_MAX_LENGTH) + "'";
	}
	public String getTenpoKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_ka,TENPO_KA_MAX_LENGTH));
	}


	// tenhanku_naに対するセッターとゲッターの集合
	public boolean setTenhankuNa(String tenhanku_na)
	{
		this.tenhanku_na = tenhanku_na;
		return true;
	}
	public String getTenhankuNa()
	{
		return cutString(this.tenhanku_na,TENHANKU_NA_MAX_LENGTH);
	}
	public String getTenhankuNaString()
	{
		return "'" + cutString(this.tenhanku_na,TENHANKU_NA_MAX_LENGTH) + "'";
	}
	public String getTenhankuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhanku_na,TENHANKU_NA_MAX_LENGTH));
	}


	// s_gyosyu_naに対するセッターとゲッターの集合
	public boolean setSGyosyuNa(String s_gyosyu_na)
	{
		this.s_gyosyu_na = s_gyosyu_na;
		return true;
	}
	public String getSGyosyuNa()
	{
		return cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH);
	}
	public String getSGyosyuNaString()
	{
		return "'" + cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH) + "'";
	}
	public String getSGyosyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH));
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


	// genka_kei_vlに対するセッターとゲッターの集合
	public boolean setGenkaKeiVl(String genka_kei_vl)
	{
		try
		{
			this.genka_kei_vl = Long.parseLong(genka_kei_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGenkaKeiVl(long genka_kei_vl)
	{
		this.genka_kei_vl = genka_kei_vl;
		return true;
	}
	public long getGenkaKeiVl()
	{
		return this.genka_kei_vl;
	}
	public String getGenkaKeiVlString()
	{
		return Long.toString(this.genka_kei_vl);
	}


	// baika_kei_vlに対するセッターとゲッターの集合
	public boolean setBaikaKeiVl(String baika_kei_vl)
	{
		try
		{
			this.baika_kei_vl = Long.parseLong(baika_kei_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaikaKeiVl(long baika_kei_vl)
	{
		this.baika_kei_vl = baika_kei_vl;
		return true;
	}
	public long getBaikaKeiVl()
	{
		return this.baika_kei_vl;
	}
	public String getBaikaKeiVlString()
	{
		return Long.toString(this.baika_kei_vl);
	}


	// tei_genka_kei_vlに対するセッターとゲッターの集合
	public boolean setTeiGenkaKeiVl(String tei_genka_kei_vl)
	{
		try
		{
			this.tei_genka_kei_vl = Long.parseLong(tei_genka_kei_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTeiGenkaKeiVl(long tei_genka_kei_vl)
	{
		this.tei_genka_kei_vl = tei_genka_kei_vl;
		return true;
	}
	public long getTeiGenkaKeiVl()
	{
		return this.tei_genka_kei_vl;
	}
	public String getTeiGenkaKeiVlString()
	{
		return Long.toString(this.tei_genka_kei_vl);
	}


	// tei_baika_kei_vlに対するセッターとゲッターの集合
	public boolean setTeiBaikaKeiVl(String tei_baika_kei_vl)
	{
		try
		{
			this.tei_baika_kei_vl = Long.parseLong(tei_baika_kei_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTeiBaikaKeiVl(long tei_baika_kei_vl)
	{
		this.tei_baika_kei_vl = tei_baika_kei_vl;
		return true;
	}
	public long getTeiBaikaKeiVl()
	{
		return this.tei_baika_kei_vl;
	}
	public String getTeiBaikaKeiVlString()
	{
		return Long.toString(this.tei_baika_kei_vl);
	}


	// seisen_fgに対するセッターとゲッターの集合
	public boolean setSeisenFg(String seisen_fg)
	{
		this.seisen_fg = seisen_fg;
		return true;
	}
	public String getSeisenFg()
	{
		return cutString(this.seisen_fg,SEISEN_FG_MAX_LENGTH);
	}
	public String getSeisenFgString()
	{
		return "'" + cutString(this.seisen_fg,SEISEN_FG_MAX_LENGTH) + "'";
	}
	public String getSeisenFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.seisen_fg,SEISEN_FG_MAX_LENGTH));
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


	// haiso_kbに対するセッターとゲッターの集合
	public boolean setHaisoKb(String haiso_kb)
	{
		this.haiso_kb = haiso_kb;
		return true;
	}
	public String getHaisoKb()
	{
		return cutString(this.haiso_kb,HAISO_KB_MAX_LENGTH);
	}
	public String getHaisoKbString()
	{
		return "'" + cutString(this.haiso_kb,HAISO_KB_MAX_LENGTH) + "'";
	}
	public String getHaisoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haiso_kb,HAISO_KB_MAX_LENGTH));
	}


	// tei_haiso_kbに対するセッターとゲッターの集合
	public boolean setTeiHaisoKb(String tei_haiso_kb)
	{
		this.tei_haiso_kb = tei_haiso_kb;
		return true;
	}
	public String getTeiHaisoKb()
	{
		return cutString(this.tei_haiso_kb,TEI_HAISO_KB_MAX_LENGTH);
	}
	public String getTeiHaisoKbString()
	{
		return "'" + cutString(this.tei_haiso_kb,TEI_HAISO_KB_MAX_LENGTH) + "'";
	}
	public String getTeiHaisoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tei_haiso_kb,TEI_HAISO_KB_MAX_LENGTH));
	}


	// denpyo_kbに対するセッターとゲッターの集合
	public boolean setDenpyoKb(String denpyo_kb)
	{
		this.denpyo_kb = denpyo_kb;
		return true;
	}
	public String getDenpyoKb()
	{
		return cutString(this.denpyo_kb,DENPYO_KB_MAX_LENGTH);
	}
	public String getDenpyoKbString()
	{
		return "'" + cutString(this.denpyo_kb,DENPYO_KB_MAX_LENGTH) + "'";
	}
	public String getDenpyoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.denpyo_kb,DENPYO_KB_MAX_LENGTH));
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


	// hachu_kbに対するセッターとゲッターの集合
	public boolean setHachuKb(String hachu_kb)
	{
		this.hachu_kb = hachu_kb;
		return true;
	}
	public String getHachuKb()
	{
		return cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH);
	}
	public String getHachuKbString()
	{
		return "'" + cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH) + "'";
	}
	public String getHachuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH));
	}


	// tenpo_hachu_kbに対するセッターとゲッターの集合
	public boolean setTenpoHachuKb(String tenpo_hachu_kb)
	{
		this.tenpo_hachu_kb = tenpo_hachu_kb;
		return true;
	}
	public String getTenpoHachuKb()
	{
		return cutString(this.tenpo_hachu_kb,TENPO_HACHU_KB_MAX_LENGTH);
	}
	public String getTenpoHachuKbString()
	{
		return "'" + cutString(this.tenpo_hachu_kb,TENPO_HACHU_KB_MAX_LENGTH) + "'";
	}
	public String getTenpoHachuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_hachu_kb,TENPO_HACHU_KB_MAX_LENGTH));
	}


	// ikatu_denp_fgに対するセッターとゲッターの集合
	public boolean setIkatuDenpFg(String ikatu_denp_fg)
	{
		this.ikatu_denp_fg = ikatu_denp_fg;
		return true;
	}
	public String getIkatuDenpFg()
	{
		return cutString(this.ikatu_denp_fg,IKATU_DENP_FG_MAX_LENGTH);
	}
	public String getIkatuDenpFgString()
	{
		return "'" + cutString(this.ikatu_denp_fg,IKATU_DENP_FG_MAX_LENGTH) + "'";
	}
	public String getIkatuDenpFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ikatu_denp_fg,IKATU_DENP_FG_MAX_LENGTH));
	}


	// syohin_theme_naに対するセッターとゲッターの集合
	public boolean setSyohinThemeNa(String syohin_theme_na)
	{
		this.syohin_theme_na = syohin_theme_na;
		return true;
	}
	public String getSyohinThemeNa()
	{
		return cutString(this.syohin_theme_na,SYOHIN_THEME_NA_MAX_LENGTH);
	}
	public String getSyohinThemeNaString()
	{
		return "'" + cutString(this.syohin_theme_na,SYOHIN_THEME_NA_MAX_LENGTH) + "'";
	}
	public String getSyohinThemeNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_theme_na,SYOHIN_THEME_NA_MAX_LENGTH));
	}


	// haisosaki_cdに対するセッターとゲッターの集合
	public boolean setHaisosakiCd(String haisosaki_cd)
	{
		this.haisosaki_cd = haisosaki_cd;
		return true;
	}
	public String getHaisosakiCd()
	{
		return cutString(this.haisosaki_cd,HAISOSAKI_CD_MAX_LENGTH);
	}
	public String getHaisosakiCdString()
	{
		return "'" + cutString(this.haisosaki_cd,HAISOSAKI_CD_MAX_LENGTH) + "'";
	}
	public String getHaisosakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haisosaki_cd,HAISOSAKI_CD_MAX_LENGTH));
	}


	// haisosaki_naに対するセッターとゲッターの集合
	public boolean setHaisosakiNa(String haisosaki_na)
	{
		this.haisosaki_na = haisosaki_na;
		return true;
	}
	public String getHaisosakiNa()
	{
		return cutString(this.haisosaki_na,HAISOSAKI_NA_MAX_LENGTH);
	}
	public String getHaisosakiNaString()
	{
		return "'" + cutString(this.haisosaki_na,HAISOSAKI_NA_MAX_LENGTH) + "'";
	}
	public String getHaisosakiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haisosaki_na,HAISOSAKI_NA_MAX_LENGTH));
	}


	// nohin_center_cdに対するセッターとゲッターの集合
	public boolean setNohinCenterCd(String nohin_center_cd)
	{
		this.nohin_center_cd = nohin_center_cd;
		return true;
	}
	public String getNohinCenterCd()
	{
		return cutString(this.nohin_center_cd,NOHIN_CENTER_CD_MAX_LENGTH);
	}
	public String getNohinCenterCdString()
	{
		if(this.nohin_center_cd == null) {
			return "";
		}
		return "'" + cutString(this.nohin_center_cd,NOHIN_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getNohinCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_center_cd,NOHIN_CENTER_CD_MAX_LENGTH));
	}


	// keiyu_center_cdに対するセッターとゲッターの集合
	public boolean setKeiyuCenterCd(String keiyu_center_cd)
	{
		this.keiyu_center_cd = keiyu_center_cd;
		return true;
	}
	public String getKeiyuCenterCd()
	{
		return cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH);
	}
	public String getKeiyuCenterCdString()
	{
		if(this.keiyu_center_cd == null) {
			return "";
		}
		return "'" + cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getKeiyuCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH));
	}


	// tenhai_center_cdに対するセッターとゲッターの集合
	public boolean setTenhaiCenterCd(String tenhai_center_cd)
	{
		this.tenhai_center_cd = tenhai_center_cd;
		return true;
	}
	public String getTenhaiCenterCd()
	{
		return cutString(this.tenhai_center_cd,TENHAI_CENTER_CD_MAX_LENGTH);
	}
	public String getTenhaiCenterCdString()
	{
		if(this.tenhai_center_cd == null) {
			return "";
		}
		return "'" + cutString(this.tenhai_center_cd,TENHAI_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getTenhaiCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhai_center_cd,TENHAI_CENTER_CD_MAX_LENGTH));
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
		if(this.zaiko_center_cd == null) {
			return "";
		}
		return "'" + cutString(this.zaiko_center_cd,ZAIKO_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getZaikoCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zaiko_center_cd,ZAIKO_CENTER_CD_MAX_LENGTH));
	}


	// tei_nohin_center_cdに対するセッターとゲッターの集合
	public boolean setTeiNohinCenterCd(String tei_nohin_center_cd)
	{
		this.tei_nohin_center_cd = tei_nohin_center_cd;
		return true;
	}
	public String getTeiNohinCenterCd()
	{
		return cutString(this.tei_nohin_center_cd,TEI_NOHIN_CENTER_CD_MAX_LENGTH);
	}
	public String getTeiNohinCenterCdString()
	{
		if(this.tei_nohin_center_cd == null) {
			return "";
		}
		return "'" + cutString(this.tei_nohin_center_cd,TEI_NOHIN_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getTeiNohinCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tei_nohin_center_cd,TEI_NOHIN_CENTER_CD_MAX_LENGTH));
	}


	// tei_keiyu_center_cdに対するセッターとゲッターの集合
	public boolean setTeiKeiyuCenterCd(String tei_keiyu_center_cd)
	{
		this.tei_keiyu_center_cd = tei_keiyu_center_cd;
		return true;
	}
	public String getTeiKeiyuCenterCd()
	{
		return cutString(this.tei_keiyu_center_cd,TEI_KEIYU_CENTER_CD_MAX_LENGTH);
	}
	public String getTeiKeiyuCenterCdString()
	{
		if(this.tei_keiyu_center_cd == null) {
			return "";
		}
		return "'" + cutString(this.tei_keiyu_center_cd,TEI_KEIYU_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getTeiKeiyuCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tei_keiyu_center_cd,TEI_KEIYU_CENTER_CD_MAX_LENGTH));
	}


	// tei_tenhai_center_cdに対するセッターとゲッターの集合
	public boolean setTeiTenhaiCenterCd(String tei_tenhai_center_cd)
	{
		this.tei_tenhai_center_cd = tei_tenhai_center_cd;
		return true;
	}
	public String getTeiTenhaiCenterCd()
	{
		return cutString(this.tei_tenhai_center_cd,TEI_TENHAI_CENTER_CD_MAX_LENGTH);
	}
	public String getTeiTenhaiCenterCdString()
	{
		if(this.tei_tenhai_center_cd == null) {
			return "";
		}
		return "'" + cutString(this.tei_tenhai_center_cd,TEI_TENHAI_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getTeiTenhaiCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tei_tenhai_center_cd,TEI_TENHAI_CENTER_CD_MAX_LENGTH));
	}


	// tei_zaiko_center_cdに対するセッターとゲッターの集合
	public boolean setTeiZaikoCenterCd(String tei_zaiko_center_cd)
	{
		this.tei_zaiko_center_cd = tei_zaiko_center_cd;
		return true;
	}
	public String getTeiZaikoCenterCd()
	{
		return cutString(this.tei_zaiko_center_cd,TEI_ZAIKO_CENTER_CD_MAX_LENGTH);
	}
	public String getTeiZaikoCenterCdString()
	{
		if(this.tei_zaiko_center_cd == null) {
			return "";
		}
		return "'" + cutString(this.tei_zaiko_center_cd,TEI_ZAIKO_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getTeiZaikoCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tei_zaiko_center_cd,TEI_ZAIKO_CENTER_CD_MAX_LENGTH));
	}


	// juchu_list_output_kbに対するセッターとゲッターの集合
	public boolean setJuchuListOutputKb(String juchu_list_output_kb)
	{
		this.juchu_list_output_kb = juchu_list_output_kb;
		return true;
	}
	public String getJuchuListOutputKb()
	{
		return cutString(this.juchu_list_output_kb,JUCHU_LIST_OUTPUT_KB_MAX_LENGTH);
	}
	public String getJuchuListOutputKbString()
	{
		return "'" + cutString(this.juchu_list_output_kb,JUCHU_LIST_OUTPUT_KB_MAX_LENGTH) + "'";
	}
	public String getJuchuListOutputKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.juchu_list_output_kb,JUCHU_LIST_OUTPUT_KB_MAX_LENGTH));
	}


	// nohin_syori_kbに対するセッターとゲッターの集合
	public boolean setNohinSyoriKb(String nohin_syori_kb)
	{
		this.nohin_syori_kb = nohin_syori_kb;
		return true;
	}
	public String getNohinSyoriKb()
	{
		return cutString(this.nohin_syori_kb,NOHIN_SYORI_KB_MAX_LENGTH);
	}
	public String getNohinSyoriKbString()
	{
		return "'" + cutString(this.nohin_syori_kb,NOHIN_SYORI_KB_MAX_LENGTH) + "'";
	}
	public String getNohinSyoriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_syori_kb,NOHIN_SYORI_KB_MAX_LENGTH));
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


	// hojin_cdに対するセッターとゲッターの集合
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


	// hojin_naに対するセッターとゲッターの集合
	public boolean setHojinNa(String hojin_na)
	{
		this.hojin_na = hojin_na;
		return true;
	}
	public String getHojinNa()
	{
		return cutString(this.hojin_na,HOJIN_NA_MAX_LENGTH);
	}
	public String getHojinNaString()
	{
		return "'" + cutString(this.hojin_na,HOJIN_NA_MAX_LENGTH) + "'";
	}
	public String getHojinNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hojin_na,HOJIN_NA_MAX_LENGTH));
	}


	// nohin_make_fgに対するセッターとゲッターの集合
	public boolean setNohinMakeFg(String nohin_make_fg)
	{
		this.nohin_make_fg = nohin_make_fg;
		return true;
	}
	public String getNohinMakeFg()
	{
		return cutString(this.nohin_make_fg,NOHIN_MAKE_FG_MAX_LENGTH);
	}
	public String getNohinMakeFgString()
	{
		return "'" + cutString(this.nohin_make_fg,NOHIN_MAKE_FG_MAX_LENGTH) + "'";
	}
	public String getNohinMakeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_make_fg,NOHIN_MAKE_FG_MAX_LENGTH));
	}


	// nohin_make_dtに対するセッターとゲッターの集合
	public boolean setNohinMakeDt(String nohin_make_dt)
	{
		this.nohin_make_dt = nohin_make_dt;
		return true;
	}
	public String getNohinMakeDt()
	{
		return cutString(this.nohin_make_dt,NOHIN_MAKE_DT_MAX_LENGTH);
	}
	public String getNohinMakeDtString()
	{
		return "'" + cutString(this.nohin_make_dt,NOHIN_MAKE_DT_MAX_LENGTH) + "'";
	}
	public String getNohinMakeDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_make_dt,NOHIN_MAKE_DT_MAX_LENGTH));
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
			+ "  file_head_nb = " + getFileHeadNbString()
			+ "  gaito_system_kb = " + getGaitoSystemKbString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenhanku_cd = " + getTenhankuCdString()
			+ "  l_gyosyu_cd = " + getLGyosyuCdString()
			+ "  s_gyosyu_cd = " + getSGyosyuCdString()
			+ "  l_hanku_cd = " + getLHankuCdString()
			+ "  torihikisaki_cd = " + getTorihikisakiCdString()
			+ "  nohin_dt = " + getNohinDtString()
			+ "  denpyo_nb = " + getDenpyoNbString()
			+ "  hachu_dt = " + getHachuDtString()
			+ "  kb_busyo_kb = " + getKbBusyoKbString()
			+ "  bin_nb = " + getBinNbString()
			+ "  tei_bin_nb = " + getTeiBinNbString()
			+ "  tenpo_na = " + getTenpoNaString()
			+ "  tenpo_ka = " + getTenpoKaString()
			+ "  tenhanku_na = " + getTenhankuNaString()
			+ "  s_gyosyu_na = " + getSGyosyuNaString()
			+ "  torihikisaki_na = " + getTorihikisakiNaString()
			+ "  torihikisaki_ka = " + getTorihikisakiKaString()
			+ "  genka_kei_vl = " + getGenkaKeiVlString()
			+ "  baika_kei_vl = " + getBaikaKeiVlString()
			+ "  tei_genka_kei_vl = " + getTeiGenkaKeiVlString()
			+ "  tei_baika_kei_vl = " + getTeiBaikaKeiVlString()
			+ "  seisen_fg = " + getSeisenFgString()
			+ "  kb_soba_kb = " + getKbSobaKbString()
			+ "  haiso_kb = " + getHaisoKbString()
			+ "  tei_haiso_kb = " + getTeiHaisoKbString()
			+ "  denpyo_kb = " + getDenpyoKbString()
			+ "  zei_kb = " + getZeiKbString()
			+ "  hachu_kb = " + getHachuKbString()
			+ "  tenpo_hachu_kb = " + getTenpoHachuKbString()
			+ "  ikatu_denp_fg = " + getIkatuDenpFgString()
			+ "  syohin_theme_na = " + getSyohinThemeNaString()
			+ "  haisosaki_cd = " + getHaisosakiCdString()
			+ "  haisosaki_na = " + getHaisosakiNaString()
			+ "  nohin_center_cd = " + getNohinCenterCdString()
			+ "  keiyu_center_cd = " + getKeiyuCenterCdString()
			+ "  tenhai_center_cd = " + getTenhaiCenterCdString()
			+ "  zaiko_center_cd = " + getZaikoCenterCdString()
			+ "  tei_nohin_center_cd = " + getTeiNohinCenterCdString()
			+ "  tei_keiyu_center_cd = " + getTeiKeiyuCenterCdString()
			+ "  tei_tenhai_center_cd = " + getTeiTenhaiCenterCdString()
			+ "  tei_zaiko_center_cd = " + getTeiZaikoCenterCdString()
			+ "  juchu_list_output_kb = " + getJuchuListOutputKbString()
			+ "  nohin_syori_kb = " + getNohinSyoriKbString()
			+ "  haisinsaki_cd = " + getHaisinsakiCdString()
			+ "  hojin_cd = " + getHojinCdString()
			+ "  hojin_na = " + getHojinNaString()
			+ "  nohin_make_fg = " + getNohinMakeFgString()
			+ "  nohin_make_dt = " + getNohinMakeDtString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return DtHachuBean コピー後のクラス
	 */
	public DtHachuBean createClone()
	{
		DtHachuBean bean = new DtHachuBean();
		bean.setDataDenpNb(this.data_denp_nb);
		bean.setFileHeadNb(this.file_head_nb);
		bean.setGaitoSystemKb(this.gaito_system_kb);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenhankuCd(this.tenhanku_cd);
		bean.setLGyosyuCd(this.l_gyosyu_cd);
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setLHankuCd(this.l_hanku_cd);
		bean.setTorihikisakiCd(this.torihikisaki_cd);
		bean.setNohinDt(this.nohin_dt);
		bean.setDenpyoNb(this.denpyo_nb);
		bean.setHachuDt(this.hachu_dt);
		bean.setKbBusyoKb(this.kb_busyo_kb);
		bean.setBinNb(this.bin_nb);
		bean.setTeiBinNb(this.tei_bin_nb);
		bean.setTenpoNa(this.tenpo_na);
		bean.setTenpoKa(this.tenpo_ka);
		bean.setTenhankuNa(this.tenhanku_na);
		bean.setSGyosyuNa(this.s_gyosyu_na);
		bean.setTorihikisakiNa(this.torihikisaki_na);
		bean.setTorihikisakiKa(this.torihikisaki_ka);
		bean.setGenkaKeiVl(this.genka_kei_vl);
		bean.setBaikaKeiVl(this.baika_kei_vl);
		bean.setTeiGenkaKeiVl(this.tei_genka_kei_vl);
		bean.setTeiBaikaKeiVl(this.tei_baika_kei_vl);
		bean.setSeisenFg(this.seisen_fg);
		bean.setKbSobaKb(this.kb_soba_kb);
		bean.setHaisoKb(this.haiso_kb);
		bean.setTeiHaisoKb(this.tei_haiso_kb);
		bean.setDenpyoKb(this.denpyo_kb);
		bean.setZeiKb(this.zei_kb);
		bean.setHachuKb(this.hachu_kb);
		bean.setTenpoHachuKb(this.tenpo_hachu_kb);
		bean.setIkatuDenpFg(this.ikatu_denp_fg);
		bean.setSyohinThemeNa(this.syohin_theme_na);
		bean.setHaisosakiCd(this.haisosaki_cd);
		bean.setHaisosakiNa(this.haisosaki_na);
		bean.setNohinCenterCd(this.nohin_center_cd);
		bean.setKeiyuCenterCd(this.keiyu_center_cd);
		bean.setTenhaiCenterCd(this.tenhai_center_cd);
		bean.setZaikoCenterCd(this.zaiko_center_cd);
		bean.setTeiNohinCenterCd(this.tei_nohin_center_cd);
		bean.setTeiKeiyuCenterCd(this.tei_keiyu_center_cd);
		bean.setTeiTenhaiCenterCd(this.tei_tenhai_center_cd);
		bean.setTeiZaikoCenterCd(this.tei_zaiko_center_cd);
		bean.setJuchuListOutputKb(this.juchu_list_output_kb);
		bean.setNohinSyoriKb(this.nohin_syori_kb);
		bean.setHaisinsakiCd(this.haisinsaki_cd);
		bean.setHojinCd(this.hojin_cd);
		bean.setHojinNa(this.hojin_na);
		bean.setNohinMakeFg(this.nohin_make_fg);
		bean.setNohinMakeDt(this.nohin_make_dt);
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
		if( !( o instanceof DtHachuBean ) ) return false;
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
