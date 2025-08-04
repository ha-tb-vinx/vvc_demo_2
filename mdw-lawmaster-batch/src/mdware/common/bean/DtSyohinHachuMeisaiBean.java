package mdware.common.bean;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: DtSeiHachuMeiBean クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author VINX
 * @version 1.0 2014/06/23 Nghia-HT 海外LAWSON様UTF-8対応
 */
public class DtSyohinHachuMeisaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private static final String CHARSET_NAME = "UTF-8";

	private static final String NULL_VAL = "NULL"; 
	
	public static final int BUMON_CD_MAX_LENGTH              =  3;
	public static final int SYOHIN_CD_MAX_LENGTH             =  20;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH       =  11;
	public static final int SANTI_CD_MAX_LENGTH              =  3;
	public static final int TOKAIKYU_CD_MAX_LENGTH           =  3;
	public static final int KIKAKU_CD_MAX_LENGTH             =  3;
	public static final int NOHIN_DT_MAX_LENGTH              =  8;
	public static final int TENPO_CD_MAX_LENGTH              =  10;
	public static final int DATA_DENP_NB_MAX_LENGTH          =  20;
	public static final int SANTI_NA_MAX_LENGTH              =  20;
	public static final int TOKAIKYU_NA_MAX_LENGTH           =  20;
	public static final int KIKAKU_NA_MAX_LENGTH             =  20;
	public static final int TENPO_NA_MAX_LENGTH              =  80;
	public static final int SYOHIN_NA_MAX_LENGTH             =  80;
	public static final int SYOHIN_KA_MAX_LENGTH             =  50;
	public static final int DENPYO_HAKO_KB_MAX_LENGTH        =  2;
	public static final int HACHU_DENPYO_KB_MAX_LENGTH       =  2;
	public static final int NOHIN_HOHO_KB_MAX_LENGTH         =  2;
	public static final int BUTURYU_KB_MAX_LENGTH            =  2;
	public static final int PC_DC_KB_MAX_LENGTH              =  1;
	public static final int KB_TOKUBAI_KB_MAX_LENGTH         =  1;
	public static final int TOKUBAI_CD_MAX_LENGTH            =  18;
	public static final int DENPYO_NB_MAX_LENGTH             =  10;
	public static final int JUCHU_LIST_OUTPUT_KB_MAX_LENGTH  =  1;
	public static final int NOHIN_SYORI_KB_MAX_LENGTH        =  1;
	public static final int RIYO_USER_ID_MAX_LENGTH          =  20;
	public static final int INSERT_TS_MAX_LENGTH             =  20;
	public static final int UPDATE_TS_MAX_LENGTH             =  20;

	public static final int GENTANKA_VL_SCALE                = 2;
	public static final int BAITANKA_VL_SCALE                = 0;
	public static final int HACHU_TANI_QT_SCALE              = 0;
	public static final int HACHU_QT_SCALE                   = 0;
	public static final int HACHU_SURYO_QT_SCALE             = 2;
	public static final int KAKUTEI_QT_SCALE                 = 0;
	public static final int KAKUTEI_SURYO_QT_SCALE           = 2;

	public static final int GENTANKA_VL_MODE                 = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int BAITANKA_VL_MODE                 = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int HACHU_TANI_QT_MODE               = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int HACHU_QT_MODE                    = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int HACHU_SURYO_QT_MODE              = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int KAKUTEI_QT_MODE                  = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int KAKUTEI_SURYO_QT_MODE            = BigDecimal.ROUND_DOWN; // 切り捨て

	private String bumon_cd = null;
	private String syohin_cd = null;
	private String torihikisaki_cd = null;
	private String santi_cd = null;
	private String tokaikyu_cd = null;
	private String kikaku_cd = null;
	private String nohin_dt = null;
	private String tenpo_cd = null;
	private String data_denp_nb = null;
	private long data_denpgyo_nb = 0;
	private String santi_na = null;
	private String tokaikyu_na = null;
	private String kikaku_na = null;
	private String tenpo_na = null;
	private String syohin_na = null;
	private String syohin_ka = null;
	private BigDecimal gentanka_vl = null;
	private BigDecimal baitanka_vl = null;
	private BigDecimal hachu_tani_qt = null;
	private BigDecimal hachu_qt = null;
	private BigDecimal hachu_suryo_qt = null;
	private BigDecimal kakutei_qt = null;
	private BigDecimal kakutei_suryo_qt = null;
	private String denpyo_hako_kb = null;
	private String hachu_denpyo_kb = null;
	private String nohin_hoho_kb = null;
	private String buturyu_kb = null;
	private String pc_dc_kb = null;
	private String kb_tokubai_kb = null;
	private String tokubai_cd = null;
	private String denpyo_nb = null;
	private long denpyogyo_nb = 0;
	private String juchu_list_output_kb = null;
	private String nohin_syori_kb = null;
	private String riyo_user_id = null;
	private String insert_ts = null;
	private String update_ts = null;

	// bumon_cd に対するセッタメソッド。
	public void setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
	}
	// bumon_cd に対するゲッタメソッド。
	public String getBumonCd()
	{
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString()
	{
		//return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
		if(this.bumon_cd == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}

	// syohin_cd に対するセッタメソッド。
	public void setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
	}
	// syohin_cd に対するゲッタメソッド。
	public String getSyohinCd()
	{
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString()
	{
		//return "'" + cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
		if(this.syohin_cd == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
	}

	// torihikisaki_cd に対するセッタメソッド。
	public void setTorihikisakiCd(String torihikisaki_cd)
	{
		this.torihikisaki_cd = torihikisaki_cd;
	}
	// torihikisaki_cd に対するゲッタメソッド。
	public String getTorihikisakiCd()
	{
		return cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH);
	}
	public String getTorihikisakiCdString()
	{
		//return "'" + cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH) + "'";
		if(this.torihikisaki_cd == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH) + "'";
	}

	// santi_cd に対するセッタメソッド。
	public void setSantiCd(String santi_cd)
	{
		this.santi_cd = santi_cd;
	}
	// santi_cd に対するゲッタメソッド。
	public String getSantiCd()
	{
		return cutString(this.santi_cd,SANTI_CD_MAX_LENGTH);
	}
	public String getSantiCdString()
	{
		//return "'" + cutString(this.santi_cd,SANTI_CD_MAX_LENGTH) + "'";
		if(this.santi_cd == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.santi_cd,SANTI_CD_MAX_LENGTH) + "'";
	}

	// tokaikyu_cd に対するセッタメソッド。
	public void setTokaikyuCd(String tokaikyu_cd)
	{
		this.tokaikyu_cd = tokaikyu_cd;
	}
	// tokaikyu_cd に対するゲッタメソッド。
	public String getTokaikyuCd()
	{
		return cutString(this.tokaikyu_cd,TOKAIKYU_CD_MAX_LENGTH);
	}
	public String getTokaikyuCdString()
	{
		//return "'" + cutString(this.tokaikyu_cd,TOKAIKYU_CD_MAX_LENGTH) + "'";
		if(this.tokaikyu_cd == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.tokaikyu_cd,TOKAIKYU_CD_MAX_LENGTH) + "'";
	}

	// kikaku_cd に対するセッタメソッド。
	public void setKikakuCd(String kikaku_cd)
	{
		this.kikaku_cd = kikaku_cd;
	}
	// kikaku_cd に対するゲッタメソッド。
	public String getKikakuCd()
	{
		return cutString(this.kikaku_cd,KIKAKU_CD_MAX_LENGTH);
	}
	public String getKikakuCdString()
	{
		//return "'" + cutString(this.kikaku_cd,KIKAKU_CD_MAX_LENGTH) + "'";
		if(this.kikaku_cd == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.kikaku_cd,KIKAKU_CD_MAX_LENGTH) + "'";
	}

	// nohin_dt に対するセッタメソッド。
	public void setNohinDt(String nohin_dt)
	{
		this.nohin_dt = nohin_dt;
	}
	// nohin_dt に対するゲッタメソッド。
	public String getNohinDt()
	{
		return cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH);
	}
	public String getNohinDtString()
	{
		//return "'" + cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH) + "'";
		if(this.nohin_dt == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.nohin_dt,NOHIN_DT_MAX_LENGTH) + "'";
	}

	// tenpo_cd に対するセッタメソッド。
	public void setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
	}
	// tenpo_cd に対するゲッタメソッド。
	public String getTenpoCd()
	{
		return cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		//return "'" + cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
		if(this.tenpo_cd == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
	}

	// data_denp_nb に対するセッタメソッド。
	public void setDataDenpNb(String data_denp_nb)
	{
		this.data_denp_nb = data_denp_nb;
	}
	// data_denp_nb に対するゲッタメソッド。
	public String getDataDenpNb()
	{
		return cutString(this.data_denp_nb,DATA_DENP_NB_MAX_LENGTH);
	}
	public String getDataDenpNbString()
	{
		//return "'" + cutString(this.data_denp_nb,DATA_DENP_NB_MAX_LENGTH) + "'";
		if(this.data_denp_nb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.data_denp_nb,DATA_DENP_NB_MAX_LENGTH) + "'";
	}

	// data_denpgyo_nb に対するセッタメソッド。
	public void setDataDenpgyoNb(String data_denpgyo_nb)
	{
		this.data_denpgyo_nb = Long.parseLong(data_denpgyo_nb);
	}
	public void setDataDenpgyoNb(long data_denpgyo_nb)
	{
		this.data_denpgyo_nb = data_denpgyo_nb;
	}
	public long getDataDenpgyoNb()
	{
		return this.data_denpgyo_nb;
	}
	public String getDataDenpgyoNbString()
	{
		return Long.toString(this.data_denpgyo_nb);
	}

	// santi_na に対するセッタメソッド。
	public void setSantiNa(String santi_na)
	{
		this.santi_na = santi_na;
	}
	// santi_na に対するゲッタメソッド。
	public String getSantiNa()
	{
		return cutString(this.santi_na,SANTI_NA_MAX_LENGTH);
	}
	public String getSantiNaString()
	{
		//return "'" + cutString(this.santi_na,SANTI_NA_MAX_LENGTH) + "'";
		if(this.santi_na == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.santi_na,SANTI_NA_MAX_LENGTH) + "'";
	}

	// tokaikyu_na に対するセッタメソッド。
	public void setTokaikyuNa(String tokaikyu_na)
	{
		this.tokaikyu_na = tokaikyu_na;
	}
	// tokaikyu_na に対するゲッタメソッド。
	public String getTokaikyuNa()
	{
		return cutString(this.tokaikyu_na,TOKAIKYU_NA_MAX_LENGTH);
	}
	public String getTokaikyuNaString()
	{
		//return "'" + cutString(this.tokaikyu_na,TOKAIKYU_NA_MAX_LENGTH) + "'";
		if(this.tokaikyu_na == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.tokaikyu_na,TOKAIKYU_NA_MAX_LENGTH) + "'";
	}

	// kikaku_na に対するセッタメソッド。
	public void setKikakuNa(String kikaku_na)
	{
		this.kikaku_na = kikaku_na;
	}
	// kikaku_na に対するゲッタメソッド。
	public String getKikakuNa()
	{
		return cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH);
	}
	public String getKikakuNaString()
	{
		//return "'" + cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH) + "'";
		if(this.kikaku_na == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.kikaku_na,KIKAKU_NA_MAX_LENGTH) + "'";
	}

	// tenpo_na に対するセッタメソッド。
	public void setTenpoNa(String tenpo_na)
	{
		this.tenpo_na = tenpo_na;
	}
	// tenpo_na に対するゲッタメソッド。
	public String getTenpoNa()
	{
		return cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH);
	}
	public String getTenpoNaString()
	{
		//return "'" + cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH) + "'";
		if(this.tenpo_na == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.tenpo_na,TENPO_NA_MAX_LENGTH) + "'";
	}

	// syohin_na に対するセッタメソッド。
	public void setSyohinNa(String syohin_na)
	{
		this.syohin_na = syohin_na;
	}
	// syohin_na に対するゲッタメソッド。
	public String getSyohinNa()
	{
		return cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH);
	}
	public String getSyohinNaString()
	{
		//return "'" + cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH) + "'";
		if(this.syohin_na == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.syohin_na,SYOHIN_NA_MAX_LENGTH) + "'";
	}

	// syohin_ka に対するセッタメソッド。
	public void setSyohinKa(String syohin_ka)
	{
		this.syohin_ka = syohin_ka;
	}
	// syohin_ka に対するゲッタメソッド。
	public String getSyohinKa()
	{
		return cutString(this.syohin_ka,SYOHIN_KA_MAX_LENGTH);
	}
	public String getSyohinKaString()
	{
		//return "'" + cutString(this.syohin_ka,SYOHIN_KA_MAX_LENGTH) + "'";
		if(this.syohin_ka == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.syohin_ka,SYOHIN_KA_MAX_LENGTH) + "'";
	}

	// gentanka_vl に対するセッタメソッド。
	public void setGentankaVl(String gentanka_vl)
	{ 
		try { 
			if(gentanka_vl == null || gentanka_vl.length() == 0) { 
				this.gentanka_vl = null; 
			} else { 
				setGentankaVl(new BigDecimal(gentanka_vl)); 
			} 
		} catch(NumberFormatException e) { 
			; 
		} 
	} 
	public void setGentankaVl(BigDecimal gentanka_vl) { 
		this.gentanka_vl = gentanka_vl; 
	} 
	public void setGentankaVl(long gentanka_vl) { 
		setGentankaVl(String.valueOf(gentanka_vl)); 
	} 
	public void setGentankaVl(double gentanka_vl) { 
		setGentankaVl(String.valueOf(gentanka_vl)); 
	} 
	// gentanka_vl に対するゲッタメソッド。
	public long getGentankaVlLong() { 
		if(gentanka_vl == null) { 
			return 0L; 
		} 
		return gentanka_vl.longValue(); 
	} 
	public double getGentankaVlDouble() { 
		if(gentanka_vl == null) { 
			return 0.0; 
		} 
		return gentanka_vl.doubleValue(); 
	} 
	public String getGentankaVlString() { 
		if(gentanka_vl == null) { 
			return NULL_VAL; 
		} 
		return (gentanka_vl.setScale(GENTANKA_VL_SCALE, GENTANKA_VL_MODE)).toString(); 
	} 
	public BigDecimal getGentankaVl() { 
		return gentanka_vl; 
	} 
	public BigDecimal getGentankaVl(int roundingMode) { 
		if(gentanka_vl == null) { 
			return null; 
		} 
		return (gentanka_vl.setScale(GENTANKA_VL_SCALE, roundingMode)); 
	} 

	// baitanka_vl に対するセッタメソッド。
	public void setBaitankaVl(String baitanka_vl)
	{ 
		try { 
			if(baitanka_vl == null || baitanka_vl.length() == 0) { 
				this.baitanka_vl = null; 
			} else { 
				setBaitankaVl(new BigDecimal(baitanka_vl)); 
			} 
		} catch(NumberFormatException e) { 
			; 
		} 
	} 
	public void setBaitankaVl(BigDecimal baitanka_vl) { 
		this.baitanka_vl = baitanka_vl; 
	} 
	public void setBaitankaVl(long baitanka_vl) { 
		setBaitankaVl(String.valueOf(baitanka_vl)); 
	} 
	public void setBaitankaVl(double baitanka_vl) { 
		setBaitankaVl(String.valueOf(baitanka_vl)); 
	} 
	// baitanka_vl に対するゲッタメソッド。
	public long getBaitankaVlLong() { 
		if(baitanka_vl == null) { 
			return 0L; 
		} 
		return baitanka_vl.longValue(); 
	} 
	public double getBaitankaVlDouble() { 
		if(baitanka_vl == null) { 
			return 0.0; 
		} 
		return baitanka_vl.doubleValue(); 
	} 
	public String getBaitankaVlString() { 
		if(baitanka_vl == null) { 
			return NULL_VAL; 
		} 
		return (baitanka_vl.setScale(BAITANKA_VL_SCALE, BAITANKA_VL_MODE)).toString(); 
	} 
	public BigDecimal getBaitankaVl() { 
		return baitanka_vl; 
	} 
	public BigDecimal getBaitankaVl(int roundingMode) { 
		if(baitanka_vl == null) { 
			return null; 
		} 
		return (baitanka_vl.setScale(BAITANKA_VL_SCALE, roundingMode)); 
	}

	// hachu_tani_qt に対するセッタメソッド。
	public void setHachuTaniQt(String hachu_tani_qt)
	{ 
		try { 
			if(hachu_tani_qt == null || hachu_tani_qt.length() == 0) { 
				this.hachu_tani_qt = null; 
			} else { 
				setHachuTaniQt(new BigDecimal(hachu_tani_qt)); 
			} 
		} catch(NumberFormatException e) { 
			; 
		} 
	} 
	public void setHachuTaniQt(BigDecimal hachu_tani_qt) { 
		this.hachu_tani_qt = hachu_tani_qt; 
	} 
	public void setHachuTaniQt(long hachu_tani_qt) { 
		setHachuTaniQt(String.valueOf(hachu_tani_qt)); 
	} 
	public void setHachuTaniQt(double hachu_tani_qt) { 
		setHachuTaniQt(String.valueOf(hachu_tani_qt)); 
	} 
	// hachu_tani_qt に対するゲッタメソッド。
	public long getHachuTaniQtLong() { 
		if(hachu_tani_qt == null) { 
			return 0L; 
		} 
		return hachu_tani_qt.longValue(); 
	} 
	public double getHachuTaniQtDouble() { 
		if(hachu_tani_qt == null) { 
			return 0.0; 
		} 
		return hachu_tani_qt.doubleValue(); 
	} 
	public String getHachuTaniQtString() { 
		if(hachu_tani_qt == null) { 
			return NULL_VAL; 
		} 
		return (hachu_tani_qt.setScale(HACHU_TANI_QT_SCALE, HACHU_TANI_QT_MODE)).toString(); 
	} 
	public BigDecimal getHachuTaniQt() { 
		return hachu_tani_qt; 
	} 
	public BigDecimal getHachuTaniQt(int roundingMode) { 
		if(hachu_tani_qt == null) { 
			return null; 
		} 
		return (hachu_tani_qt.setScale(HACHU_TANI_QT_SCALE, roundingMode)); 
	} 

	// hachu_qt に対するセッタメソッド。
	public void setHachuQt(String hachu_qt)
	{ 
		try { 
			if(hachu_qt == null || hachu_qt.length() == 0) { 
				this.hachu_qt = null; 
			} else { 
				setHachuQt(new BigDecimal(hachu_qt)); 
			} 
		} catch(NumberFormatException e) { 
			; 
		} 
	} 
	public void setHachuQt(BigDecimal hachu_qt) { 
		this.hachu_qt = hachu_qt; 
	} 
	public void setHachuQt(long hachu_qt) { 
		setHachuQt(String.valueOf(hachu_qt)); 
	} 
	public void setHachuQt(double hachu_qt) { 
		setHachuQt(String.valueOf(hachu_qt)); 
	} 
	// hachu_qt に対するゲッタメソッド。
	public long getHachuQtLong() { 
		if(hachu_qt == null) { 
			return 0L; 
		} 
		return hachu_qt.longValue(); 
	} 
	public double getHachuQtDouble() { 
		if(hachu_qt == null) { 
			return 0.0; 
		} 
		return hachu_qt.doubleValue(); 
	} 
	public String getHachuQtString() { 
		if(hachu_qt == null) { 
			return NULL_VAL; 
		} 
		return (hachu_qt.setScale(HACHU_QT_SCALE, HACHU_QT_MODE)).toString(); 
	} 
	public BigDecimal getHachuQt() { 
		return hachu_qt; 
	} 
	public BigDecimal getHachuQt(int roundingMode) { 
		if(hachu_qt == null) { 
			return null; 
		} 
		return (hachu_qt.setScale(HACHU_QT_SCALE, roundingMode)); 
	} 

	// hachu_suryo_qt に対するセッタメソッド。
	public void setHachuSuryoQt(String hachu_suryo_qt)
	{ 
		try { 
			if(hachu_suryo_qt == null || hachu_suryo_qt.length() == 0) { 
				this.hachu_suryo_qt = null; 
			} else { 
				setHachuSuryoQt(new BigDecimal(hachu_suryo_qt)); 
			} 
		} catch(NumberFormatException e) { 
			; 
		} 
	} 
	public void setHachuSuryoQt(BigDecimal hachu_suryo_qt) { 
		this.hachu_suryo_qt = hachu_suryo_qt; 
	} 
	public void setHachuSuryoQt(long hachu_suryo_qt) { 
		setHachuSuryoQt(String.valueOf(hachu_suryo_qt)); 
	} 
	public void setHachuSuryoQt(double hachu_suryo_qt) { 
		setHachuSuryoQt(String.valueOf(hachu_suryo_qt)); 
	} 
	// hachu_suryo_qt に対するゲッタメソッド。
	public long getHachuSuryoQtLong() { 
		if(hachu_suryo_qt == null) { 
			return 0L; 
		} 
		return hachu_suryo_qt.longValue(); 
	} 
	public double getHachuSuryoQtDouble() { 
		if(hachu_suryo_qt == null) { 
			return 0.0; 
		} 
		return hachu_suryo_qt.doubleValue(); 
	} 
	public String getHachuSuryoQtString() { 
		if(hachu_suryo_qt == null) { 
			return NULL_VAL; 
		} 
		return (hachu_suryo_qt.setScale(HACHU_SURYO_QT_SCALE, HACHU_SURYO_QT_MODE)).toString(); 
	} 
	public BigDecimal getHachuSuryoQt() { 
		return hachu_suryo_qt; 
	} 
	public BigDecimal getHachuSuryoQt(int roundingMode) { 
		if(hachu_suryo_qt == null) { 
			return null; 
		} 
		return (hachu_suryo_qt.setScale(HACHU_SURYO_QT_SCALE, roundingMode)); 
	} 

	// kakutei_qt に対するセッタメソッド。
	public void setKakuteiQt(String kakutei_qt)
	{ 
		try { 
			if(kakutei_qt == null || kakutei_qt.length() == 0) { 
				this.kakutei_qt = null; 
			} else { 
				setKakuteiQt(new BigDecimal(kakutei_qt)); 
			} 
		} catch(NumberFormatException e) { 
			; 
		} 
	} 
	public void setKakuteiQt(BigDecimal kakutei_qt) { 
		this.kakutei_qt = kakutei_qt; 
	} 
	public void setKakuteiQt(long kakutei_qt) { 
		setKakuteiQt(String.valueOf(kakutei_qt)); 
	} 
	public void setKakuteiQt(double kakutei_qt) { 
		setKakuteiQt(String.valueOf(kakutei_qt)); 
	} 
	// kakutei_qt に対するゲッタメソッド。
	public long getKakuteiQtLong() { 
		if(kakutei_qt == null) { 
			return 0L; 
		} 
		return kakutei_qt.longValue(); 
	} 
	public double getKakuteiQtDouble() { 
		if(kakutei_qt == null) { 
			return 0.0; 
		} 
		return kakutei_qt.doubleValue(); 
	} 
	public String getKakuteiQtString() { 
		if(kakutei_qt == null) { 
			return NULL_VAL; 
		} 
		return (kakutei_qt.setScale(KAKUTEI_QT_SCALE, KAKUTEI_QT_MODE)).toString(); 
	} 
	public BigDecimal getKakuteiQt() { 
		return kakutei_qt; 
	} 
	public BigDecimal getKakuteiQt(int roundingMode) { 
		if(kakutei_qt == null) { 
			return null; 
		} 
		return (kakutei_qt.setScale(KAKUTEI_QT_SCALE, roundingMode)); 
	} 

	// kakutei_suryo_qt に対するセッタメソッド。
	public void setKakuteiSuryoQt(String kakutei_suryo_qt)
	{ 
		try { 
			if(kakutei_suryo_qt == null || kakutei_suryo_qt.length() == 0) { 
				this.kakutei_suryo_qt = null; 
			} else { 
				setKakuteiSuryoQt(new BigDecimal(kakutei_suryo_qt)); 
			} 
		} catch(NumberFormatException e) { 
			; 
		} 
	} 
	public void setKakuteiSuryoQt(BigDecimal kakutei_suryo_qt) { 
		this.kakutei_suryo_qt = kakutei_suryo_qt; 
	} 
	public void setKakuteiSuryoQt(long kakutei_suryo_qt) { 
		setKakuteiSuryoQt(String.valueOf(kakutei_suryo_qt)); 
	} 
	public void setKakuteiSuryoQt(double kakutei_suryo_qt) { 
		setKakuteiSuryoQt(String.valueOf(kakutei_suryo_qt)); 
	} 
	// kakutei_suryo_qt に対するゲッタメソッド。
	public long getKakuteiSuryoQtLong() { 
		if(kakutei_suryo_qt == null) { 
			return 0L; 
		} 
		return kakutei_suryo_qt.longValue(); 
	} 
	public double getKakuteiSuryoQtDouble() { 
		if(kakutei_suryo_qt == null) { 
			return 0.0; 
		} 
		return kakutei_suryo_qt.doubleValue(); 
	} 
	public String getKakuteiSuryoQtString() { 
		if(kakutei_suryo_qt == null) { 
			return NULL_VAL; 
		} 
		return (kakutei_suryo_qt.setScale(KAKUTEI_SURYO_QT_SCALE, KAKUTEI_SURYO_QT_MODE)).toString(); 
	} 
	public BigDecimal getKakuteiSuryoQt() { 
		return kakutei_suryo_qt; 
	} 
	public BigDecimal getKakuteiSuryoQt(int roundingMode) { 
		if(kakutei_suryo_qt == null) { 
			return null; 
		} 
		return (kakutei_suryo_qt.setScale(KAKUTEI_SURYO_QT_SCALE, roundingMode)); 
	} 

	// denpyo_hako_kb に対するセッタメソッド。
	public void setDenpyoHakoKb(String denpyo_hako_kb)
	{
		this.denpyo_hako_kb = denpyo_hako_kb;
	}
	// denpyo_hako_kb に対するゲッタメソッド。
	public String getDenpyoHakoKb()
	{
		return cutString(this.denpyo_hako_kb,DENPYO_HAKO_KB_MAX_LENGTH);
	}
	public String getDenpyoHakoKbString()
	{
		//return "'" + cutString(this.denpyo_hako_kb,DENPYO_HAKO_KB_MAX_LENGTH) + "'";
		if(this.denpyo_hako_kb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.denpyo_hako_kb,DENPYO_HAKO_KB_MAX_LENGTH) + "'";
	}
	// hachu_denpyo_kb に対するセッタメソッド。
	public void setHachuDenpyoKb(String hachu_denpyo_kb)
	{
		this.hachu_denpyo_kb = hachu_denpyo_kb;
	}
	// hachu_denpyo_kb に対するゲッタメソッド。
	public String getHachuDenpyoKb()
	{
		return cutString(this.hachu_denpyo_kb,HACHU_DENPYO_KB_MAX_LENGTH);
	}
	public String getHachuDenpyoKbString()
	{
		//return "'" + cutString(this.hachu_denpyo_kb,HACHU_DENPYO_KB_MAX_LENGTH) + "'";
		if(this.hachu_denpyo_kb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.hachu_denpyo_kb,HACHU_DENPYO_KB_MAX_LENGTH) + "'";
	}
	// nohin_hoho_kb に対するセッタメソッド。
	public void setNohinHohoKb(String nohin_hoho_kb)
	{
		this.nohin_hoho_kb = nohin_hoho_kb;
	}
	// nohin_hoho_kb に対するゲッタメソッド。
	public String getNohinHohoKb()
	{
		return cutString(this.nohin_hoho_kb,NOHIN_HOHO_KB_MAX_LENGTH);
	}
	public String getNohinHohoKbString()
	{
		//return "'" + cutString(this.nohin_hoho_kb,NOHIN_HOHO_KB_MAX_LENGTH) + "'";
		if(this.nohin_hoho_kb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.nohin_hoho_kb,NOHIN_HOHO_KB_MAX_LENGTH) + "'";
	}
	// buturyu_kb に対するセッタメソッド。
	public void setButuryuKb(String buturyu_kb)
	{
		this.buturyu_kb = buturyu_kb;
	}
	// buturyu_kb に対するゲッタメソッド。
	public String getButuryuKb()
	{
		return cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH);
	}
	public String getButuryuKbString()
	{
		//return "'" + cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH) + "'";
		if(this.buturyu_kb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH) + "'";
	}
	// pc_dc_kb に対するセッタメソッド。
	public void setPcDcKb(String pc_dc_kb)
	{
		this.pc_dc_kb = pc_dc_kb;
	}
	// pc_dc_kb に対するゲッタメソッド。
	public String getPcDcKb()
	{
		return cutString(this.pc_dc_kb,PC_DC_KB_MAX_LENGTH);
	}
	public String getPcDcKbString()
	{
		//return "'" + cutString(this.pc_dc_kb,PC_DC_KB_MAX_LENGTH) + "'";
		if(this.pc_dc_kb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.pc_dc_kb,PC_DC_KB_MAX_LENGTH) + "'";
	}	

	// kb_tokubai_kb に対するセッタメソッド。
	public void setKbTokubaiKb(String kb_tokubai_kb)
	{
		this.kb_tokubai_kb = kb_tokubai_kb;
	}
	// kb_tokubai_kb に対するゲッタメソッド。
	public String getKbTokubaiKb()
	{
		return cutString(this.kb_tokubai_kb,KB_TOKUBAI_KB_MAX_LENGTH);
	}
	public String getKbTokubaiKbString()
	{
		//return "'" + cutString(this.kb_tokubai_kb,KB_TOKUBAI_KB_MAX_LENGTH) + "'";
		if(this.kb_tokubai_kb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.kb_tokubai_kb,KB_TOKUBAI_KB_MAX_LENGTH) + "'";
	}
	
	// tokubai_cd に対するセッタメソッド。
	public void setTokubaiCd(String tokubai_cd)
	{
		 this.tokubai_cd = tokubai_cd;
	}
	// tokubai_cd に対するゲッタメソッド。
	public String getTokubaiCd()
	{
		 return cutString(this.tokubai_cd,TOKUBAI_CD_MAX_LENGTH);
	}
	public String getTokubaiCdString()
	{
		//return "'" + cutString(this.tokubai_cd,TOKUBAI_CD_MAX_LENGTH) + "'";
		if(this.tokubai_cd == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.tokubai_cd,TOKUBAI_CD_MAX_LENGTH) + "'";
	}

	// denpyo_nb に対するセッタメソッド。
	public void setDenpyoNb(String denpyo_nb)
	{
		this.denpyo_nb = denpyo_nb;
	}
	// denpyo_nb に対するゲッタメソッド。
	public String getDenpyoNb()
	{
		return cutString(this.denpyo_nb,DENPYO_NB_MAX_LENGTH);
	}
	public String getDenpyoNbString()
	{
		//return "'" + cutString(this.denpyo_nb,DENPYO_NB_MAX_LENGTH) + "'";
		if(this.denpyo_nb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.denpyo_nb,DENPYO_NB_MAX_LENGTH) + "'";
	}

	// denpyogyo_nb に対するセッタメソッド。
	public void setDenpyogyoNb(String denpyogyo_nb)
	{
		this.denpyogyo_nb = Long.parseLong(denpyogyo_nb);
	}
	public void setDenpyogyoNb(long denpyogyo_nb)
	{
		this.denpyogyo_nb = denpyogyo_nb;
	}
	public long getDenpyogyoNb()
	{
		return this.denpyogyo_nb;
	}
	public String getDenpyogyoNbString()
	{
		return Long.toString(this.denpyogyo_nb);
	}

	// juchu_list_output_kb に対するセッタメソッド。
	public void setJuchuListOutputKb(String juchu_list_output_kb)
	{
		this.juchu_list_output_kb = juchu_list_output_kb;
	}
	// juchu_list_output_kb に対するゲッタメソッド。
	public String getJuchuListOutputKb()
	{
		return cutString(this.juchu_list_output_kb,JUCHU_LIST_OUTPUT_KB_MAX_LENGTH);
	}
	public String getJuchuListOutputKbString()
	{
		//return "'" + cutString(this.juchu_list_output_kb,JUCHU_LIST_OUTPUT_KB_MAX_LENGTH) + "'";
		if(this.juchu_list_output_kb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.juchu_list_output_kb,JUCHU_LIST_OUTPUT_KB_MAX_LENGTH) + "'";
	}

	// nohin_syori_kb に対するセッタメソッド。
	public void setNohinSyoriKb(String nohin_syori_kb)
	{
		this.nohin_syori_kb = nohin_syori_kb;
	}
	// nohin_syori_kb に対するゲッタメソッド。
	public String getNohinSyoriKb()
	{
		return cutString(this.nohin_syori_kb,NOHIN_SYORI_KB_MAX_LENGTH);
	}
	public String getNohinSyoriKbString()
	{
		//return "'" + cutString(this.nohin_syori_kb,NOHIN_SYORI_KB_MAX_LENGTH) + "'";
		if(this.nohin_syori_kb == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.nohin_syori_kb,NOHIN_SYORI_KB_MAX_LENGTH) + "'";
	}
	
	//	riyo_user_id に対するセッタメソッド。
	public void setRiyoUserId(String riyo_user_id)
	{
		this.riyo_user_id = riyo_user_id;
	}
	// riyo_user_id に対するゲッタメソッド。
	public String getRiyoUserId()
	{
		return cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH);
	}
	public String getRiyoUserIdString()
	{
		//return "'" + cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
		if(this.riyo_user_id == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
	}

	// insert_ts に対するセッタメソッド。
	public void setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
	}
	// insert_ts に対するゲッタメソッド。
	public String getInsertTs()
	{
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsString()
	{
		//return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
		if(this.insert_ts == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}

	// update_ts に対するセッタメソッド。
	public void setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
	}
	// update_ts に対するゲッタメソッド。
	public String getUpdateTs()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString()
	{
		//return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
		if(this.update_ts == null) { 
			return NULL_VAL; 
		}
		return "'" + cutStringForSql(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String toString() { 
		return 
		  " bumon_cd = "                  + getBumonCdString() 
		+ " syohin_cd = "                 + getSyohinCdString() 
		+ " torihikisaki_cd = "           + getTorihikisakiCdString() 
		+ " santi_cd = "                  + getSantiCdString() 
		+ " tokaikyu_cd = "               + getTokaikyuCdString() 
		+ " kikaku_cd = "                 + getKikakuCdString() 
		+ " nohin_dt = "                  + getNohinDtString() 
		+ " tenpo_cd = "                  + getTenpoCdString() 
		+ " data_denp_nb = "              + getDataDenpNbString() 
		+ " data_denpgyo_nb = "           + getDataDenpgyoNbString() 
		+ " santi_na = "                  + getSantiNaString() 
		+ " tokaikyu_na = "               + getTokaikyuNaString() 
		+ " kikaku_na = "                 + getKikakuNaString() 
		+ " tenpo_na = "                  + getTenpoNaString() 
		+ " syohin_na = "                 + getSyohinNaString() 
		+ " syohin_ka = "                 + getSyohinKaString() 
		+ " gentanka_vl = "               + getGentankaVlString() 
		+ " baitanka_vl = "               + getBaitankaVlString() 
		+ " hachu_tani_qt = "             + getHachuTaniQtString() 
		+ " hachu_qt = "                  + getHachuQtString() 
		+ " hachu_suryo_qt = "            + getHachuSuryoQtString() 
		+ " kakutei_qt = "                + getKakuteiQtString() 
		+ " kakutei_suryo_qt = "          + getKakuteiSuryoQtString() 
		+ " denpyo_hako_kb = "            + getDenpyoHakoKbString() 
		+ " hachu_denpyo_kb = "           + getHachuDenpyoKbString() 
		+ " nohin_hoho_kb = "             + getNohinHohoKbString() 
		+ " buturyu_kb = "                + getButuryuKbString() 
		+ " pc_dc_kb = "                  + getPcDcKbString() 
		+ " kb_tokubai_kb = "             + getKbTokubaiKbString() 
		+ " tokubai_cd = "                + getTokubaiCdString() 
		+ " denpyo_nb = "                 + getDenpyoNbString() 
		+ " denpyogyo_nb = "              + getDenpyogyoNbString() 
		+ " juchu_list_output_kb = "      + getJuchuListOutputKbString() 
		+ " nohin_syori_kb = "            + getNohinSyoriKbString() 
		+ " riyo_user_id = "              + getRiyoUserIdString() 
		+ " insert_ts = "                 + getInsertTsString() 
		+ " update_ts = "                 + getUpdateTsString() 
		;
	}
	/** 
	 * 指定された文字列を許容される最大バイト数で切り捨てます。 
	 * また、指定された文字列にシングルクォート ' が存在する場合、 
	 * '' に変換されます。 
	 * <pre> 
	 * 【注意】 
	 *   シングルクォートの安全化処理が行われた場合、返却される 
	 *   String の最大バイト数は以下のようになります。 
	 *     返却される String のバイト数 = 許容される最大バイト数 + シングルクォートの個数 
	 * </pre> 
	 * 
	 * @param base  変換対象文字列 
	 * @param max   許容される最大バイト数 
	 * @return      許容される最大バイト数で切り捨てられた String<br> 
	 *             （シングルクォートの安全化処理済） 
	 */ 
	private String cutStringForSql( String base, int max ) { 
		if ( base == null ) { 
			return null; 
		} 
		StringBuffer sb = new StringBuffer(); 
		for (int pos = 0, count = 0; pos < max && pos < base.length(); pos++) { 
			String tmp = null; 
			try { 
				tmp = base.substring(pos, pos + 1); 
				byte bt[] = tmp.getBytes(CHARSET_NAME); 
				count += bt.length; 
				if (count > max) { 
					break; 
				} 
				if (tmp.charAt(0) == '\'') { 
					sb.append('\''); 
				} 
				sb.append(tmp); 
 
			} catch(UnsupportedEncodingException e) { 
				stcLog.getLog().fatal(this.getClass().getName() + "/cutString/" + base + "/" + tmp + "を" + CHARSET_NAME + "に変換できませんでした。"); 
			} 
		} 
		return sb.toString(); 
	} 
 
	/** 
	 * 指定された文字列を許容される最大バイト数で切り捨てます。 
	 * シングルクォートの安全化処理は行われません。 
	 * 
	 * @param base  変換対象文字列 
	 * @param max   許容される最大バイト数 
	 * @return      許容される最大バイト数で切り捨てられた String 
	 */ 
	private String cutString( String base, int max ) { 
		if ( base == null ) { 
			return null; 
		} 
		StringBuffer sb = new StringBuffer(); 
		for (int pos = 0, count = 0; pos < max && pos < base.length(); pos++) { 
			String tmp = null; 
			try { 
				tmp = base.substring(pos, pos + 1); 
				byte bt[] = tmp.getBytes(CHARSET_NAME); 
				count += bt.length; 
				if (count > max) { 
					break; 
				} 
				sb.append(tmp); 
 
			} catch(UnsupportedEncodingException e) { 
				stcLog.getLog().fatal(this.getClass().getName() + "/cutString/" + base + "/" + tmp + "を" + CHARSET_NAME + "に変換できませんでした。"); 
			} 
		} 
		return sb.toString(); 
	} 
/* 旧 cutString
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
*/
}
