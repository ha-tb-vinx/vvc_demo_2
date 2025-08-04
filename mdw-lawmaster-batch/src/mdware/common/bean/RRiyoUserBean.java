package mdware.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 * @version 1.1 (2004.09.02) hashimoto ver3.1用改修。
 * @version 1.2 (2005.01.27) morita
 */
// 20020819 yoshi cutString()でnull時に例外が起こることへの対応を入れる
public class RRiyoUserBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int COMPANY_NA_MAX_LENGTH = 60;
	public static final int RIYO_USER_NA_MAX_LENGTH = 80;
	public static final int DEPARTMENT_NA_MAX_LENGTH = 60;
	public static final int RIYO_USER_SYUBETU_KB_MAX_LENGTH = 1;
	public static final int TENPO_CD_MAX_LENGTH = 10;

//2005/01/27 morita add start
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int TENHANKU_CD_MAX_LENGTH = 4;
//2005/01/27 morita add end

	public static final int PASSWORD_KEY_CD_MAX_LENGTH = 20;
	public static final int YUKO_KAISI_DT_MAX_LENGTH = 8;
	public static final int YUKO_SYURYO_DT_MAX_LENGTH = 8;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	public static final int KOURI_CD_MAX_LENGTH = 10;
	public static final int KENGEN_CD_MAX_LENGTH = 4;
	private String riyo_user_id = null;
	private String company_na = null;
	private String riyo_user_na = null;
	private String department_na = null;
	private String riyo_user_syubetu_kb = null;
	private String tenpo_cd = null;

//2005/01/27 morita add start
	private String s_gyosyu_cd = null;
	private String tenhanku_cd = null;
//2005/01/27 morita add end

	private String password_key_cd = null;
	private String yuko_kaisi_dt = null;
	private String yuko_syuryo_dt = null;
	private String insert_ts = null;
	private String update_ts = null;
	private String kouri_cd = null;
	private String kengen_cd = null;

	public void setRiyoUserId(String riyo_user_id)
	{
		this.riyo_user_id = riyo_user_id;
	}
	public String getRiyoUserId()
	{
		return cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH);
	}
	public String getRiyoUserIdString()
	{
		return "'" + cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
	}

	public void setCompanyNa(String company_na)
	{
		this.company_na = company_na;
	}
	public String getCompanyNa()
	{
		return cutString(this.company_na,COMPANY_NA_MAX_LENGTH);
	}
	public String getCompanyNaString()
	{
		return "'" + cutString(this.company_na,COMPANY_NA_MAX_LENGTH) + "'";
	}

	public void setRiyoUserNa(String riyo_user_na)
	{
		this.riyo_user_na = riyo_user_na;
	}
	public String getRiyoUserNa()
	{
		return cutString(this.riyo_user_na,RIYO_USER_NA_MAX_LENGTH);
	}
	public String getRiyoUserNaString()
	{
		return "'" + cutString(this.riyo_user_na,RIYO_USER_NA_MAX_LENGTH) + "'";
	}

	public void setDepartmentNa(String department_na)
	{
		this.department_na = department_na;
	}
	public String getDepartmentNa()
	{
		return cutString(this.department_na,DEPARTMENT_NA_MAX_LENGTH);
	}
	public String getDepartmentNaString()
	{
		return "'" + cutString(this.department_na,DEPARTMENT_NA_MAX_LENGTH) + "'";
	}

	public void setRiyoUserSyubetuKb(String riyo_user_syubetu_kb)
	{
		this.riyo_user_syubetu_kb = riyo_user_syubetu_kb;
	}
	public String getRiyoUserSyubetuKb()
	{
		return cutString(this.riyo_user_syubetu_kb,RIYO_USER_SYUBETU_KB_MAX_LENGTH);
	}
	public String getRiyoUserSyubetuKbString()
	{
		return "'" + cutString(this.riyo_user_syubetu_kb,RIYO_USER_SYUBETU_KB_MAX_LENGTH) + "'";
	}
	public void setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
	}
	public String getTenpoCd()
	{
		return cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		return "'" + cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
	}

//2005/01/27 morita add start
	public void setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
	}
	public String getSGyosyuCd()
	{
		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSGyosyuCdString()
	{
		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	}

	public void setTenhankuCd(String tenhanku_cd)
	{
		this.tenhanku_cd = tenhanku_cd;
	}
	public String getTenhankuCd()
	{
		return cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH);
	}
	public String getTenhankuCdString()
	{
		return "'" + cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH) + "'";
	}
//2005/01/27 morita add end

	public void setPasswordKeyCd(String password_key_cd)
	{
		this.password_key_cd = password_key_cd;
	}
	public String getPasswordKeyCd()
	{
		return cutString(this.password_key_cd,PASSWORD_KEY_CD_MAX_LENGTH);
	}
	public String getPasswordKeyCdString()
	{
		return "'" + cutString(this.password_key_cd,PASSWORD_KEY_CD_MAX_LENGTH) + "'";
	}

	public void setYukoKaisiDt(String yuko_kaisi_dt)
	{
		this.yuko_kaisi_dt = yuko_kaisi_dt;
	}
	public String getYukoKaisiDt()
	{
		return cutString(this.yuko_kaisi_dt,YUKO_KAISI_DT_MAX_LENGTH);
	}
	public String getYukoKaisiDtString()
	{
		return "'" + cutString(this.yuko_kaisi_dt,YUKO_KAISI_DT_MAX_LENGTH) + "'";
	}

	public void setYukoSyuryoDt(String yuko_syuryo_dt)
	{
		this.yuko_syuryo_dt = yuko_syuryo_dt;
	}
	public String getYukoSyuryoDt()
	{
		return cutString(this.yuko_syuryo_dt,YUKO_SYURYO_DT_MAX_LENGTH);
	}
	public String getYukoSyuryoDtString()
	{
		return "'" + cutString(this.yuko_syuryo_dt,YUKO_SYURYO_DT_MAX_LENGTH) + "'";
	}

	public void setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
	}
	public String getInsertTs()
	{
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsString()
	{
		return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}

	public void setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
	}
	public String getUpdateTs()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString()
	{
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String toString()
	{
// 20021205 @UPD yamanaka V2でDB変更により start
//		return "  riyo_user_id = " + getRiyoUserIdString()  + "  company_na = " + getCompanyNaString()  + "  riyo_user_na = " + getRiyoUserNaString()  + "  department_na = " + getDepartmentNaString()  + "  riyo_user_syubetu_kb = " + getRiyoUserSyubetuKbString()  + "  password_key_cd = " + getPasswordKeyCdString()  + "  yuko_kaisi_dt = " + getYukoKaisiDtString()	+ "  yuko_syuryo_dt = " + getYukoSyuryoDtString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
		return "  riyo_user_id = " + getRiyoUserIdString()  + "  kouri_cd = " + getKouriCdString()  + "  riyo_user_na = " + getRiyoUserNaString()  + "  kengen_cd = " + getKengenCdString()  + "  company_na = " + getCompanyNaString()  + "  department_na = " + getDepartmentNaString()  + "  riyo_user_syubetu_kb = " + getRiyoUserSyubetuKbString()  + "  password_key_cd = " + getPasswordKeyCdString()  + "  yuko_kaisi_dt = " + getYukoKaisiDtString()  + "  yuko_syuryo_dt = " + getYukoSyuryoDtString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
// 20021205 @UPD yamanaka V2でDB変更により end
	}

// 20021205 @ADD yamanaka V2でDB変更により start
	public void setKouriCd(String kouri_cd)
	{
		this.kouri_cd = kouri_cd;
	}
	public String getKouriCd()
	{
		return cutString(this.kouri_cd,KOURI_CD_MAX_LENGTH);
	}
	public String getKouriCdString()
	{
		return "'" + cutString(this.kouri_cd,KOURI_CD_MAX_LENGTH) + "'";
	}
	public void setKengenCd(String kengen_cd)
	{
		this.kengen_cd = kengen_cd;
	}
	public String getKengenCd()
	{
		return cutString(this.kengen_cd,KENGEN_CD_MAX_LENGTH);
	}
	public String getKengenCdString()
	{
		return "'" + cutString(this.kengen_cd,KENGEN_CD_MAX_LENGTH) + "'";
	}
// 20021205 @ADD yamanaka V2でDB変更により end

	private String cutString( String base, int max )
	{
		// nullの時に落ちていたので空文字を返すように修正する
		if( base == null )
			return "";
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
