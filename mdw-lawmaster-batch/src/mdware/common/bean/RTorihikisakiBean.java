package mdware.common.bean;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: RTorihikisakiBean クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author Bean Creator(2002.09.09) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2003/11/26 10:3:26)
 * @version 1.0 20040210 本番リリース
 * @version 1.1 20040221 変更№70対応
 * @version 1.2 20050302 WEB-EDI本番移行対応
 */
public class RTorihikisakiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private static final String CHARSET_NAME = "UTF-8";

	private static final String NULL_VAL = "NULL";

	public static final int TORIHIKISAKI_CD_MAX_LENGTH       = 11;
	public static final int TORIHIKISAKI_NA_MAX_LENGTH       = 60;
	public static final int TORIHIKISAKI_KA_MAX_LENGTH       = 30;
	public static final int TEL_NB_MAX_LENGTH                = 20;
	public static final int TORIHIKISAKI_KB_MAX_LENGTH       =  1;
	public static final int HACHUYO_IF_FG_MAX_LENGTH         =  1;
	public static final int HACHU_IF_FG_MAX_LENGTH           =  1;
	public static final int HACHU_IF_HAISIN_FG_MAX_LENGTH    =  1;
	public static final int NOHIN_IF_FG_MAX_LENGTH           =  1;
	public static final int JURYO_IF_FG_MAX_LENGTH           =  1;
	public static final int SIHARAI_IF_FG_MAX_LENGTH         =  1;
	public static final int HON_START_DT_MAX_LENGTH          =  8; //2004.02.21 @ADD yamanaka
	public static final int HON_END_DT_MAX_LENGTH            =  8; //2004.02.21 @ADD yamanaka
// 2005/03/02 add takata begin
	public static final int SEISEN_WEB_EDI_TAISYO_FG_MAX_LENGTH         =  1;
	public static final int SEISEN_HON_START_DT_MAX_LENGTH              =  8;
	public static final int SEISEN_HON_END_DT_MAX_LENGTH                =  8;
	public static final int GROCERY_WEB_EDI_TAISYO_FG_MAX_LENGTH        =  1;
	public static final int GROCERY_HON_START_DT_MAX_LENGTH             =  8;
	public static final int GROCERY_HON_END_DT_MAX_LENGTH               =  8;
	public static final int IRYO_WEB_EDI_TAISYO_FG_MAX_LENGTH           =  1;
	public static final int IRYO_HON_START_DT_MAX_LENGTH                =  8;
	public static final int IRYO_HON_END_DT_MAX_LENGTH                  =  8;
	public static final int JUSEIKATU_WEB_EDI_TAISYO_FG_MAX_LENGTH      =  1;
	public static final int JUSEIKATU_HON_START_DT_MAX_LENGTH           =  8;
	public static final int JUSEIKATU_HON_END_DT_MAX_LENGTH             =  8;
// 2005/03/02 add takata end
	public static final int INSERT_TS_MAX_LENGTH             = 20;
	public static final int UPDATE_TS_MAX_LENGTH             = 20;

	private String torihikisaki_cd = null;
	private String torihikisaki_na = null;
	private String torihikisaki_ka = null;
	private String tel_nb = null;
	private String torihikisaki_kb = null;
	private String hachuyo_if_fg = null;
	private String hachu_if_fg = null;
	private String hachu_if_haisin_fg = null;
	private String nohin_if_fg = null;
	private String juryo_if_fg = null;
	private String siharai_if_fg = null;
	private String hon_start_dt = null; //2004.02.21 @ADD yamanaka
	private String hon_end_dt = null; //2004.02.21 @ADD yamanaka
// 2005/03/02 add takata begin
	private String seisen_web_edi_taisyo_fg = null;
	private String seisen_hon_start_dt = null;
	private String seisen_hon_end_dt = null;
	private String grocery_web_edi_taisyo_fg = null;
	private String grocery_hon_start_dt = null;
	private String grocery_hon_end_dt = null;
	private String iryo_web_edi_taisyo_fg = null;
	private String iryo_hon_start_dt = null;
	private String iryo_hon_end_dt = null;
	private String juseikatu_web_edi_taisyo_fg = null;
	private String juseikatu_hon_start_dt = null;
	private String juseikatu_hon_end_dt = null;
// 2005/03/02 add takata end
	private String insert_ts = null;
	private String update_ts = null;

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

	// torihikisaki_na に対するセッタメソッド。
	public void setTorihikisakiNa(String torihikisaki_na)
	{
		this.torihikisaki_na = torihikisaki_na;
	}
	// torihikisaki_na に対するゲッタメソッド。
	public String getTorihikisakiNa()
	{
		return cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH);
	}
	public String getTorihikisakiNaString()
	{
		//return "'" + cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH) + "'";
		if(this.torihikisaki_na == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH) + "'";
	}

	// torihikisaki_ka に対するセッタメソッド。
	public void setTorihikisakiKa(String torihikisaki_ka)
	{
		this.torihikisaki_ka = torihikisaki_ka;
	}
	// torihikisaki_ka に対するゲッタメソッド。
	public String getTorihikisakiKa()
	{
		return cutString(this.torihikisaki_ka,TORIHIKISAKI_KA_MAX_LENGTH);
	}
	public String getTorihikisakiKaString()
	{
		//return "'" + cutString(this.torihikisaki_ka,TORIHIKISAKI_KA_MAX_LENGTH) + "'";
		if(this.torihikisaki_ka == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.torihikisaki_ka,TORIHIKISAKI_KA_MAX_LENGTH) + "'";
	}

	// tel_nb に対するセッタメソッド。
	public void setTelNb(String tel_nb)
	{
		this.tel_nb = tel_nb;
	}
	// tel_nb に対するゲッタメソッド。
	public String getTelNb()
	{
		return cutString(this.tel_nb,TEL_NB_MAX_LENGTH);
	}
	public String getTelNbString()
	{
		//return "'" + cutString(this.tel_nb,TEL_NB_MAX_LENGTH) + "'";
		if(this.tel_nb == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.tel_nb,TEL_NB_MAX_LENGTH) + "'";
	}

	// torihikisaki_kb に対するセッタメソッド。
	public void setTorihikisakiKb(String torihikisaki_kb)
	{
		this.torihikisaki_kb = torihikisaki_kb;
	}
	// torihikisaki_kb に対するゲッタメソッド。
	public String getTorihikisakiKb()
	{
		return cutString(this.torihikisaki_kb,TORIHIKISAKI_KB_MAX_LENGTH);
	}
	public String getTorihikisakiKbString()
	{
		//return "'" + cutString(this.torihikisaki_kb,TORIHIKISAKI_KB_MAX_LENGTH) + "'";
		if(this.torihikisaki_kb == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.torihikisaki_kb,TORIHIKISAKI_KB_MAX_LENGTH) + "'";
	}

	// hachuyo_if_fg に対するセッタメソッド。
	public void setHachuyoIfFg(String hachuyo_if_fg)
	{
		this.hachuyo_if_fg = hachuyo_if_fg;
	}
	// hachuyo_if_fg に対するゲッタメソッド。
	public String getHachuyoIfFg()
	{
		return cutString(this.hachuyo_if_fg,HACHUYO_IF_FG_MAX_LENGTH);
	}
	public String getHachuyoIfFgString()
	{
		//return "'" + cutString(this.hachuyo_if_fg,HACHUYO_IF_FG_MAX_LENGTH) + "'";
		if(this.hachuyo_if_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.hachuyo_if_fg,HACHUYO_IF_FG_MAX_LENGTH) + "'";
	}

	// hachu_if_fg に対するセッタメソッド。
	public void setHachuIfFg(String hachu_if_fg)
	{
		this.hachu_if_fg = hachu_if_fg;
	}
	// hachu_if_fg に対するゲッタメソッド。
	public String getHachuIfFg()
	{
		return cutString(this.hachu_if_fg,HACHU_IF_FG_MAX_LENGTH);
	}
	public String getHachuIfFgString()
	{
		//return "'" + cutString(this.hachu_if_fg,HACHU_IF_FG_MAX_LENGTH) + "'";
		if(this.hachu_if_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.hachu_if_fg,HACHU_IF_FG_MAX_LENGTH) + "'";
	}

	// hachu_if_haisin_fg に対するセッタメソッド。
	public void setHachuIfHaisinFg(String hachu_if_haisin_fg)
	{
		this.hachu_if_haisin_fg = hachu_if_haisin_fg;
	}
	// hachu_if_haisin_fg に対するゲッタメソッド。
	public String getHachuIfHaisinFg()
	{
		return cutString(this.hachu_if_haisin_fg,HACHU_IF_HAISIN_FG_MAX_LENGTH);
	}
	public String getHachuIfHaisinFgString()
	{
		//return "'" + cutString(this.hachu_if_haisin_fg,HACHU_IF_HAISIN_FG_MAX_LENGTH) + "'";
		if(this.hachu_if_haisin_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.hachu_if_haisin_fg,HACHU_IF_HAISIN_FG_MAX_LENGTH) + "'";
	}

	// nohin_if_fg に対するセッタメソッド。
	public void setNohinIfFg(String nohin_if_fg)
	{
		this.nohin_if_fg = nohin_if_fg;
	}
	// nohin_if_fg に対するゲッタメソッド。
	public String getNohinIfFg()
	{
		return cutString(this.nohin_if_fg,NOHIN_IF_FG_MAX_LENGTH);
	}
	public String getNohinIfFgString()
	{
		//return "'" + cutString(this.nohin_if_fg,NOHIN_IF_FG_MAX_LENGTH) + "'";
		if(this.nohin_if_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.nohin_if_fg,NOHIN_IF_FG_MAX_LENGTH) + "'";
	}

	// juryo_if_fg に対するセッタメソッド。
	public void setJuryoIfFg(String juryo_if_fg)
	{
		this.juryo_if_fg = juryo_if_fg;
	}
	// juryo_if_fg に対するゲッタメソッド。
	public String getJuryoIfFg()
	{
		return cutString(this.juryo_if_fg,JURYO_IF_FG_MAX_LENGTH);
	}
	public String getJuryoIfFgString()
	{
		//return "'" + cutString(this.juryo_if_fg,JURYO_IF_FG_MAX_LENGTH) + "'";
		if(this.juryo_if_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.juryo_if_fg,JURYO_IF_FG_MAX_LENGTH) + "'";
	}

	// siharai_if_fg に対するセッタメソッド。
	public void setSiharaiIfFg(String siharai_if_fg)
	{
		this.siharai_if_fg = siharai_if_fg;
	}
	// siharai_if_fg に対するゲッタメソッド。
	public String getSiharaiIfFg()
	{
		return cutString(this.siharai_if_fg,SIHARAI_IF_FG_MAX_LENGTH);
	}
	public String getSiharaiIfFgString()
	{
		//return "'" + cutString(this.siharai_if_fg,SIHARAI_IF_FG_MAX_LENGTH) + "'";
		if(this.siharai_if_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.siharai_if_fg,SIHARAI_IF_FG_MAX_LENGTH) + "'";
	}

//2004.02.21 @ADD yamanaka 変更№70対応 start
	// hon_start_dt に対するセッタメソッド。
	public void setHonStartDt(String hon_start_dt)
	{
		this.hon_start_dt = hon_start_dt;
	}
	// hon_start_dt に対するゲッタメソッド。
	public String getHonStartDt()
	{
		return cutString(this.hon_start_dt,HON_START_DT_MAX_LENGTH);
	}
	public String getHonStartDtString()
	{
		//return "'" + cutString(this.hon_start_dt,HON_START_DT_MAX_LENGTH) + "'";
		if(this.hon_start_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.hon_start_dt,HON_START_DT_MAX_LENGTH) + "'";
	}

	// hon_end_dt に対するセッタメソッド。
	public void setHonEndDt(String hon_end_dt)
	{
		this.hon_end_dt = hon_end_dt;
	}
	// hon_end_dt に対するゲッタメソッド。
	public String getHonEndDt()
	{
		return cutString(this.hon_end_dt,HON_END_DT_MAX_LENGTH);
	}
	public String getHonEndDtString()
	{
		//return "'" + cutString(this.hon_end_dt,HON_END_DT_MAX_LENGTH) + "'";
		if(this.hon_end_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.hon_end_dt,HON_END_DT_MAX_LENGTH) + "'";
	}
//2004.02.21 @ADD yamanaka 変更№70対応 end

// 2005/03/02 add takata begin
	// seisen_web_edi_taisyo_fg に対するセッタメソッド。
	public void setSeisenWebEdiTaisyoFg(String seisen_web_edi_taisyo_fg)
	{
		this.seisen_web_edi_taisyo_fg = seisen_web_edi_taisyo_fg;
	}
	// seisen_web_edi_taisyo_fg に対するゲッタメソッド。
	public String getSeisenWebEdiTaisyoFg()
	{
		return cutString(this.seisen_web_edi_taisyo_fg,SEISEN_WEB_EDI_TAISYO_FG_MAX_LENGTH);
	}
	public String getSeisenWebEdiTaisyoFgString()
	{
		//return "'" + cutString(this.seisen_web_edi_taisyo_fg,SEISEN_WEB_EDI_TAISYO_FG_MAX_LENGTH) + "'";
		if(this.seisen_web_edi_taisyo_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.seisen_web_edi_taisyo_fg,SEISEN_WEB_EDI_TAISYO_FG_MAX_LENGTH) + "'";
	}

	// seisen_hon_start_dt に対するセッタメソッド。
	public void setSeisenHonStartDt(String seisen_hon_start_dt)
	{
		this.seisen_hon_start_dt = seisen_hon_start_dt;
	}
	// seisen_hon_start_dt に対するゲッタメソッド。
	public String getSeisenHonStartDt()
	{
		return cutString(this.seisen_hon_start_dt,SEISEN_HON_START_DT_MAX_LENGTH);
	}
	public String getSeisenHonStartDtString()
	{
		//return "'" + cutString(this.seisen_hon_start_dt,SEISEN_HON_START_DT_MAX_LENGTH) + "'";
		if(this.seisen_hon_start_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.seisen_hon_start_dt,SEISEN_HON_START_DT_MAX_LENGTH) + "'";
	}

	// seisen_hon_end_dt に対するセッタメソッド。
	public void setSeisenHonEndDt(String seisen_hon_end_dt)
	{
		this.seisen_hon_end_dt = seisen_hon_end_dt;
	}
	// seisen_hon_end_dt に対するゲッタメソッド。
	public String getSeisenHonEndDt()
	{
		return cutString(this.seisen_hon_end_dt,SEISEN_HON_END_DT_MAX_LENGTH);
	}
	public String getSeisenHonEndDtString()
	{
		//return "'" + cutString(this.seisen_hon_start_dt,SEISEN_HON_END_DT_MAX_LENGTH) + "'";
		if(this.seisen_hon_end_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.seisen_hon_end_dt,SEISEN_HON_END_DT_MAX_LENGTH) + "'";
	}

	// grocery_web_edi_taisyo_fg に対するセッタメソッド。
	public void setGroceryWebEdiTaisyoFg(String grocery_web_edi_taisyo_fg)
	{
		this.grocery_web_edi_taisyo_fg = grocery_web_edi_taisyo_fg;
	}
	// grocery_web_edi_taisyo_fg に対するゲッタメソッド。
	public String getGroceryWebEdiTaisyoFg()
	{
		return cutString(this.grocery_web_edi_taisyo_fg,GROCERY_WEB_EDI_TAISYO_FG_MAX_LENGTH);
	}
	public String getGroceryWebEdiTaisyoFgString()
	{
		//return "'" + cutString(this.grocery_web_edi_taisyo_fg,GROCERY_WEB_EDI_TAISYO_FG_MAX_LENGTH) + "'";
		if(this.grocery_web_edi_taisyo_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.grocery_web_edi_taisyo_fg,GROCERY_WEB_EDI_TAISYO_FG_MAX_LENGTH) + "'";
	}

	// grocery_hon_start_dt に対するセッタメソッド。
	public void setGroceryHonStartDt(String grocery_hon_start_dt)
	{
		this.grocery_hon_start_dt = grocery_hon_start_dt;
	}
	// grocery_hon_start_dt に対するゲッタメソッド。
	public String getGroceryHonStartDt()
	{
		return cutString(this.grocery_hon_start_dt,GROCERY_HON_START_DT_MAX_LENGTH);
	}
	public String getGroceryHonStartDtString()
	{
		//return "'" + cutString(this.grocery_hon_start_dt,GROCERY_HON_START_DT_MAX_LENGTH) + "'";
		if(this.grocery_hon_start_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.grocery_hon_start_dt,GROCERY_HON_START_DT_MAX_LENGTH) + "'";
	}

	// grocery_hon_end_dt に対するセッタメソッド。
	public void setGroceryHonEndDt(String grocery_hon_end_dt)
	{
		this.grocery_hon_end_dt = grocery_hon_end_dt;
	}
	// grocery_hon_end_dt に対するゲッタメソッド。
	public String getGroceryHonEndDt()
	{
		return cutString(this.grocery_hon_end_dt,GROCERY_HON_END_DT_MAX_LENGTH);
	}
	public String getGroceryHonEndDtString()
	{
		//return "'" + cutString(this.grocery_hon_start_dt,GROCERY_HON_END_DT_MAX_LENGTH) + "'";
		if(this.grocery_hon_end_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.grocery_hon_end_dt,GROCERY_HON_END_DT_MAX_LENGTH) + "'";
	}

	// iryo_web_edi_taisyo_fg に対するセッタメソッド。
	public void setIryoWebEdiTaisyoFg(String iryo_web_edi_taisyo_fg)
	{
		this.iryo_web_edi_taisyo_fg = iryo_web_edi_taisyo_fg;
	}
	// iryo_web_edi_taisyo_fg に対するゲッタメソッド。
	public String getIryoWebEdiTaisyoFg()
	{
		return cutString(this.iryo_web_edi_taisyo_fg,IRYO_WEB_EDI_TAISYO_FG_MAX_LENGTH);
	}
	public String getIryoWebEdiTaisyoFgString()
	{
		//return "'" + cutString(this.iryo_web_edi_taisyo_fg,IRYO_WEB_EDI_TAISYO_FG_MAX_LENGTH) + "'";
		if(this.iryo_web_edi_taisyo_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.iryo_web_edi_taisyo_fg,IRYO_WEB_EDI_TAISYO_FG_MAX_LENGTH) + "'";
	}

	// iryo_hon_start_dt に対するセッタメソッド。
	public void setIryoHonStartDt(String iryo_hon_start_dt)
	{
		this.iryo_hon_start_dt = iryo_hon_start_dt;
	}
	// iryo_hon_start_dt に対するゲッタメソッド。
	public String getIryoHonStartDt()
	{
		return cutString(this.iryo_hon_start_dt,IRYO_HON_START_DT_MAX_LENGTH);
	}
	public String getIryoHonStartDtString()
	{
		//return "'" + cutString(this.iryo_hon_start_dt,IRYO_HON_START_DT_MAX_LENGTH) + "'";
		if(this.iryo_hon_start_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.iryo_hon_start_dt,IRYO_HON_START_DT_MAX_LENGTH) + "'";
	}

	// iryo_hon_end_dt に対するセッタメソッド。
	public void setIryoHonEndDt(String iryo_hon_end_dt)
	{
		this.iryo_hon_end_dt = iryo_hon_end_dt;
	}
	// iryo_hon_end_dt に対するゲッタメソッド。
	public String getIryoHonEndDt()
	{
		return cutString(this.iryo_hon_end_dt,IRYO_HON_END_DT_MAX_LENGTH);
	}
	public String getIryoHonEndDtString()
	{
		//return "'" + cutString(this.iryo_hon_start_dt,IRYO_HON_END_DT_MAX_LENGTH) + "'";
		if(this.iryo_hon_end_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.iryo_hon_end_dt,IRYO_HON_END_DT_MAX_LENGTH) + "'";
	}

	// juseikatu_web_edi_taisyo_fg に対するセッタメソッド。
	public void setJuseikatuWebEdiTaisyoFg(String juseikatu_web_edi_taisyo_fg)
	{
		this.juseikatu_web_edi_taisyo_fg = juseikatu_web_edi_taisyo_fg;
	}
	// juseikatu_web_edi_taisyo_fg に対するゲッタメソッド。
	public String getJuseikatuWebEdiTaisyoFg()
	{
		return cutString(this.juseikatu_web_edi_taisyo_fg,JUSEIKATU_WEB_EDI_TAISYO_FG_MAX_LENGTH);
	}
	public String getJuseikatuWebEdiTaisyoFgString()
	{
		//return "'" + cutString(this.juseikatu_web_edi_taisyo_fg,JUSEIKATU_WEB_EDI_TAISYO_FG_MAX_LENGTH) + "'";
		if(this.juseikatu_web_edi_taisyo_fg == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.juseikatu_web_edi_taisyo_fg,JUSEIKATU_WEB_EDI_TAISYO_FG_MAX_LENGTH) + "'";
	}

	// juseikatu_hon_start_dt に対するセッタメソッド。
	public void setJuseikatuHonStartDt(String juseikatu_hon_start_dt)
	{
		this.juseikatu_hon_start_dt = juseikatu_hon_start_dt;
	}
	// juseikatu_hon_start_dt に対するゲッタメソッド。
	public String getJuseikatuHonStartDt()
	{
		return cutString(this.juseikatu_hon_start_dt,JUSEIKATU_HON_START_DT_MAX_LENGTH);
	}
	public String getJuseikatuHonStartDtString()
	{
		//return "'" + cutString(this.juseikatu_hon_start_dt,JUSEIKATU_HON_START_DT_MAX_LENGTH) + "'";
		if(this.juseikatu_hon_start_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.juseikatu_hon_start_dt,JUSEIKATU_HON_START_DT_MAX_LENGTH) + "'";
	}

	// juseikatu_hon_end_dt に対するセッタメソッド。
	public void setJuseikatuHonEndDt(String juseikatu_hon_end_dt)
	{
		this.juseikatu_hon_end_dt = juseikatu_hon_end_dt;
	}
	// juseikatu_hon_end_dt に対するゲッタメソッド。
	public String getJuseikatuHonEndDt()
	{
		return cutString(this.juseikatu_hon_end_dt,JUSEIKATU_HON_END_DT_MAX_LENGTH);
	}
	public String getJuseikatuHonEndDtString()
	{
		//return "'" + cutString(this.juseikatu_hon_start_dt,JUSEIKATU_HON_END_DT_MAX_LENGTH) + "'";
		if(this.juseikatu_hon_end_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.juseikatu_hon_end_dt,JUSEIKATU_HON_END_DT_MAX_LENGTH) + "'";
	}

// 2005/03/02 add takata begin

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
		  " torihikisaki_cd = "           + getTorihikisakiCdString()
		+ " torihikisaki_na = "           + getTorihikisakiNaString()
		+ " torihikisaki_ka = "           + getTorihikisakiKaString()
		+ " tel_nb = "                    + getTelNbString()
		+ " torihikisaki_kb = "           + getTorihikisakiKbString()
		+ " hachuyo_if_fg = "             + getHachuyoIfFgString()
		+ " hachu_if_fg = "               + getHachuIfFgString()
		+ " hachu_if_haisin_fg = "        + getHachuIfHaisinFgString()
		+ " nohin_if_fg = "               + getNohinIfFgString()
		+ " juryo_if_fg = "               + getJuryoIfFgString()
		+ " siharai_if_fg = "             + getSiharaiIfFgString()
		+ " hon_start_dt = "              + getHonStartDtString() //2004.02.21 @ADD yamanaka 変更№70
		+ " hon_end_dt = "                + getHonEndDtString() //2004.02.21 @ADD yamanaka 変更№70
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
