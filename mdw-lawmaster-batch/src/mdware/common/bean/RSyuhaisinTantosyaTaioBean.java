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
public class RSyuhaisinTantosyaTaioBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TANTOSYA_KB_MAX_LENGTH = 4;
	public static final int HAISINSAKI_CD_MAX_LENGTH = 11;
	public static final int KOURI_CD_MAX_LENGTH = 10;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH = 11;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String tantosya_kb = null;
	private String haisinsaki_cd = null;
	private String kouri_cd = null;
	private String torihikisaki_cd = null;
	private String insert_ts = null;
	private String update_ts = null;

	public void setTantosyaKb(String tantosya_kb)
	{
		this.tantosya_kb = tantosya_kb;
	}
	public String getTantosyaKb()
	{
		return cutString(this.tantosya_kb,TANTOSYA_KB_MAX_LENGTH);
	}
	public String getTantosyaKbString()
	{
		return "'" + cutString(this.tantosya_kb,TANTOSYA_KB_MAX_LENGTH) + "'";
	}

	public void setHaisinsakiCd(String haisinsaki_cd)
	{
		this.haisinsaki_cd = haisinsaki_cd;
	}
	public String getHaisinsakiCd()
	{
		return cutString(this.haisinsaki_cd,HAISINSAKI_CD_MAX_LENGTH);
	}
	public String getHaisinsakiCdString()
	{
		return "'" + cutString(this.haisinsaki_cd,HAISINSAKI_CD_MAX_LENGTH) + "'";
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

	public void setTorihikisakiCd(String torihikisaki_cd)
	{
		this.torihikisaki_cd = torihikisaki_cd;
	}
	public String getTorihikisakiCd()
	{
		return cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH);
	}
	public String getTorihikisakiCdString()
	{
		return "'" + cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH) + "'";
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
		return "  tantosya_kb = " + getTantosyaKbString()  + "  haisinsaki_cd = " + getHaisinsakiCdString()  + "  kouri_cd = " + getKouriCdString()  + "  torihikisaki_cd = " + getTorihikisakiCdString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
	}
	private String cutString( String base, int max )
	{
		if( base == null ) return "";
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
