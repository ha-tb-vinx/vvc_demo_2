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
 */
public class UsableMenuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int MENU_CD_MAX_LENGTH = 20;
	public static final int KOURI_CD_MAX_LENGTH = 10;
	public static final int MENU_NA_MAX_LENGTH = 60;
	public static final int KINO_CD_MAX_LENGTH = 2;
	public static final int KINO_NA_MAX_LENGTH = 30;
	public static final int RIYO_USER_KB_MAX_LENGTH = 1;
	public static final int DATA_SYUBETU_CD_MAX_LENGTH = 2;
	public static final int DATA_SYUBETU_NA_MAX_LENGTH = 30;
	public static final int DATA_UP_DOWN_KB_MAX_LENGTH = 1;
	public static final int HISU_MENU_CD_MAX_LENGTH = 20;
	public static final int KENGEN_CD_MAX_LENGTH = 4;
	public static final int KENGEN_NA_MAX_LENGTH = 60;
	public static final int RIYO_USER_SYUBETU_KB_MAX_LENGTH = 1;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int RIYO_USER_NA_MAX_LENGTH = 80;
	public static final int COMPANY_NA_MAX_LENGTH = 60;
	public static final int DEPARTMENT_NA_MAX_LENGTH = 60;

	private String menu_cd = null;
	private String kouri_cd = null;
	private String menu_na = null;
	private String kino_cd = null;
	private String kino_na = null;
	private String riyo_user_kb = null;
	private String data_syubetu_cd = null;
	private String data_syubetu_na = null;
	private String data_up_down_kb = null;
	private String hisu_menu_cd = null;
	private String kengen_cd = null;
	private String kengen_na = null;
	private String riyo_user_syubetu_kb = null;
	private String riyo_user_id = null;
	private String riyo_user_na = null;
	private String company_na = null;
	private String department_na = null;

	public void setMenuCd(String menu_cd)
	{
		this.menu_cd = menu_cd;
	}
	public String getMenuCd()
	{
		return cutString(this.menu_cd,MENU_CD_MAX_LENGTH);
	}
	public String getMenuCdString()
	{
		return "'" + cutString(this.menu_cd,MENU_CD_MAX_LENGTH) + "'";
	}

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

	public void setMenuNa(String menu_na)
	{
		this.menu_na = menu_na;
	}
	public String getMenuNa()
	{
		return cutString(this.menu_na,MENU_NA_MAX_LENGTH);
	}
	public String getMenuNaString()
	{
		return "'" + cutString(this.menu_na,MENU_NA_MAX_LENGTH) + "'";
	}

	public void setKinoCd(String kino_cd)
	{
		this.kino_cd = kino_cd;
	}
	public String getKinoCd()
	{
		return cutString(this.kino_cd,KINO_CD_MAX_LENGTH);
	}
	public String getKinoCdString()
	{
		return "'" + cutString(this.kino_cd,KINO_CD_MAX_LENGTH) + "'";
	}

	public void setKinoNa(String kino_na)
	{
		this.kino_na = kino_na;
	}
	public String getKinoNa()
	{
		return cutString(this.kino_na,KINO_NA_MAX_LENGTH);
	}
	public String getKinoNaString()
	{
		return "'" + cutString(this.kino_na,KINO_NA_MAX_LENGTH) + "'";
	}

	public void setRiyoUserKb(String riyo_user_kb)
	{
		this.riyo_user_kb = riyo_user_kb;
	}
	public String getRiyoUserKb()
	{
		return cutString(this.riyo_user_kb,RIYO_USER_KB_MAX_LENGTH);
	}
	public String getRiyoUserKbString()
	{
		return "'" + cutString(this.riyo_user_kb,RIYO_USER_KB_MAX_LENGTH) + "'";
	}

	public void setDataSyubetuCd(String data_syubetu_cd)
	{
		this.data_syubetu_cd = data_syubetu_cd;
	}
	public String getDataSyubetuCd()
	{
		return cutString(this.data_syubetu_cd,DATA_SYUBETU_CD_MAX_LENGTH);
	}
	public String getDataSyubetuCdString()
	{
		return "'" + cutString(this.data_syubetu_cd,DATA_SYUBETU_CD_MAX_LENGTH) + "'";
	}

	public void setDataSyubetuNa(String data_syubetu_na)
	{
		this.data_syubetu_na = data_syubetu_na;
	}
	public String getDataSyubetuNa()
	{
		return cutString(this.data_syubetu_na,DATA_SYUBETU_NA_MAX_LENGTH);
	}
	public String getDataSyubetuNaString()
	{
		return "'" + cutString(this.data_syubetu_na,DATA_SYUBETU_NA_MAX_LENGTH) + "'";
	}

	public void setDataUpDownKb(String data_up_down_kb)
	{
		this.data_up_down_kb = data_up_down_kb;
	}
	public String getDataUpDownKb()
	{
		return cutString(this.data_up_down_kb,DATA_UP_DOWN_KB_MAX_LENGTH);
	}
	public String getDataUpDownKbString()
	{
		return "'" + cutString(this.data_up_down_kb,DATA_UP_DOWN_KB_MAX_LENGTH) + "'";
	}

	public void setHisuMenuCd(String hisu_menu_cd)
	{
		this.hisu_menu_cd = hisu_menu_cd;
	}
	public String getHisuMenuCd()
	{
		return cutString(this.hisu_menu_cd,HISU_MENU_CD_MAX_LENGTH);
	}
	public String getHisuMenuCdString()
	{
		return "'" + cutString(this.hisu_menu_cd,HISU_MENU_CD_MAX_LENGTH) + "'";
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

	public void setKengenNa(String kengen_na)
	{
		this.kengen_na = kengen_na;
	}
	public String getKengenNa()
	{
		return cutString(this.kengen_na,KENGEN_NA_MAX_LENGTH);
	}
	public String getKengenNaString()
	{
		return "'" + cutString(this.kengen_na,KENGEN_NA_MAX_LENGTH) + "'";
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
	public String toString()
	{
		return "  menu_cd = " + getMenuCdString()  + "  kouri_cd = " + getKouriCdString()  + "  menu_na = " + getMenuNaString()  + "  kino_cd = " + getKinoCdString()  + "  kino_na = " + getKinoNaString()  + "  riyo_user_kb = " + getRiyoUserKbString()  + "  data_syubetu_cd = " + getDataSyubetuCdString()  + "  data_syubetu_na = " + getDataSyubetuNaString()  + "  data_up_down_kb = " + getDataUpDownKbString()  + "  hisu_menu_cd = " + getHisuMenuCdString()  + "  kengen_cd = " + getKengenCdString()  + "  kengen_na = " + getKengenNaString()  + "  riyo_user_syubetu_kb = " + getRiyoUserSyubetuKbString()  + "  riyo_user_id = " + getRiyoUserIdString()  + "  riyo_user_na = " + getRiyoUserNaString()  + "  company_na = " + getCompanyNaString()  + "  department_na = " + getDepartmentNaString() ;
	}
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
