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
public class RTantosyaKbBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TANTOSYA_KB_MAX_LENGTH = 4;
	public static final int TANTOSYA_KB_NA_MAX_LENGTH = 60;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String tantosya_kb = null;
	private String tantosya_kb_na = null;
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

	public void setTantosyaKbNa(String tantosya_kb_na)
	{
		this.tantosya_kb_na = tantosya_kb_na;
	}
	public String getTantosyaKbNa()
	{
		return cutString(this.tantosya_kb_na,TANTOSYA_KB_NA_MAX_LENGTH);
	}
	public String getTantosyaKbNaString()
	{
		return "'" + cutString(this.tantosya_kb_na,TANTOSYA_KB_NA_MAX_LENGTH) + "'";
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
		return "  tantosya_kb = " + getTantosyaKbString()  + "  tantosya_kb_na = " + getTantosyaKbNaString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
	}
	private String cutString( String base, int max )
	{
		String wk = "";
    //Null対応
    if( base == null )  //2002/08/15 added by e.kato
        return wk;      //2002/08/15 added by e.kato

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
