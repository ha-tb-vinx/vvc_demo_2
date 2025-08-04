package mdware.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.DataModule;
/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class EKeijibanHaisinBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int HAISINSAKI_CD_MAX_LENGTH = 11;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private long keiji_nb = 0;
	private String haisinsaki_cd = null;
	private String insert_ts = null;
	private String update_ts = null;

	public void setKeijiNb(String keiji_nb)
	{
		this.keiji_nb = Long.parseLong(keiji_nb);
	}
	public void setKeijiNb(long keiji_nb)
	{
		this.keiji_nb = keiji_nb;
	}
	public long getKeijiNb()
	{
		return this.keiji_nb;
	}
	public String getKeijiNbString()
	{
		return Long.toString(this.keiji_nb);
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
		return "  keiji_nb = " + getKeijiNbString()  + "  haisinsaki_cd = " + getHaisinsakiCdString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
	}
	private String cutString( String base, int max )
	{
		String wk = "";
		if( base == null ) return "";    // 20030124 @Add start A.Tashiro
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
