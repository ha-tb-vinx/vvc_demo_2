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
public class RKinoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KINO_CD_MAX_LENGTH = 2;
	public static final int KINO_NA_MAX_LENGTH = 30;
	public static final int DATA_SYUBETU_CD_MAX_LENGTH = 2;
	public static final int DATA_SYUBETU_NA_MAX_LENGTH = 30;
	public static final int HOST_UP_DOWN_KB_MAX_LENGTH = 1;
// 20021216 @ADD yamanaka start
	public static final int IF_FILE_HEAD_NA_MAX_LENGTH = 10;
// 20021216 @ADD yamanaka end

	private String kino_cd = null;
	private String kino_na = null;
	private String data_syubetu_cd = null;
	private String data_syubetu_na = null;
	private String host_up_down_kb = null;
	private long data_hoji_kikan_dy = 0;
// 20021216 @ADD yamanaka start
	private String if_file_head_na = null;
// 20021216 @ADD yamanaka end

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

	public void setHostUpDownKb(String host_up_down_kb)
	{
		this.host_up_down_kb = host_up_down_kb;
	}
	public String getHostUpDownKb()
	{
		return cutString(this.host_up_down_kb,HOST_UP_DOWN_KB_MAX_LENGTH);
	}
	public String getHostUpDownKbString()
	{
		return "'" + cutString(this.host_up_down_kb,HOST_UP_DOWN_KB_MAX_LENGTH) + "'";
	}

	public void setDataHojiKikanDy(String data_hoji_kikan_dy)
	{
		this.data_hoji_kikan_dy = Long.parseLong(data_hoji_kikan_dy);
	}
	public void setDataHojiKikanDy(long data_hoji_kikan_dy)
	{
		this.data_hoji_kikan_dy = data_hoji_kikan_dy;
	}
	public long getDataHojiKikanDy()
	{
		return this.data_hoji_kikan_dy;
	}
	public String getDataHojiKikanDyString()
	{
		return Long.toString(this.data_hoji_kikan_dy);
	}
// 20021216 @ADD yamanaka start
	public void setIfFileHeadNa(String if_file_head_na)
	{
		this.if_file_head_na = if_file_head_na;
	}
	public String getIfFileHeadNa()
	{
		return cutString(this.if_file_head_na,IF_FILE_HEAD_NA_MAX_LENGTH);
	}
	public String getIfFileHeadNaString()
	{
		return "'" + cutString(this.if_file_head_na,IF_FILE_HEAD_NA_MAX_LENGTH) + "'";
	}
// 20021216 @ADD yamanaka end
	public String toString()
	{
// 20021216 @UPD yamanaka start
//		return "  kino_cd = " + getKinoCdString()  + "  kino_na = " + getKinoNaString()  + "  data_syubetu_cd = " + getDataSyubetuCdString()  + "  data_syubetu_na = " + getDataSyubetuNaString()  + "  host_up_down_kb = " + getHostUpDownKbString()  + "  data_hoji_kikan_dy = " + getDataHojiKikanDyString() ;
		return "  kino_cd = " + getKinoCdString()  + "  kino_na = " + getKinoNaString()  + "  data_syubetu_cd = " + getDataSyubetuCdString()  + "  data_syubetu_na = " + getDataSyubetuNaString()  + "  host_up_down_kb = " + getHostUpDownKbString()  + "  data_hoji_kikan_dy = " + getDataHojiKikanDyString()  + "  if_file_head_na = " + getIfFileHeadNaString() ;
// 20021216 @UPD yamanaka end
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
