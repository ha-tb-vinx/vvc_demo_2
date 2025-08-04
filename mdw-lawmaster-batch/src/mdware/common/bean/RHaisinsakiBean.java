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
public class RHaisinsakiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int HAISINSAKI_CD_MAX_LENGTH = 11;
	public static final int HAISINSAKI_NA_MAX_LENGTH = 60;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH = 11;    // 20021219 add A.Tashiro for V2
	public static final int DATA_DOWNLOAD_KAHI_FG_MAX_LENGTH = 1;
	public static final int DENPATU_KAHI_FG_MAX_LENGTH = 1;
	public static final int FILE_KEISIKI_KB_MAX_LENGTH = 1;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int DATA_MERGE_KAHI_KB_MAX_LENGTH = 1;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
// 20021225 add start A.Tashiro for V2
	public static final int ZIP_KB_MAX_LENGTH = 1;
	public static final int PASSWORD_KEY_CD_MAX_LENGTH = 20;
// 20021225 add end

	private String haisinsaki_cd = null;
	private String haisinsaki_na = null;
	private String torihikisaki_cd = null;              //20021219 add A.Tashiro for V2
	private String data_download_kahi_fg = null;
	private String denpatu_kahi_fg = null;
	private String file_keisiki_kb = null;
	private long data_hoji_kijun_dt = 0;
	private long data_hoji_kikan_mn = 0;
	private String insert_ts = null;
	private String data_merge_kahi_kb = null;
	private String update_ts = null;
// 20021225 add start A.Tashiro for V2
	private String zip_kb = null;
	private String password_key_cd = null;
// 20021225 add end

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

	public void setHaisinsakiNa(String haisinsaki_na)
	{
		this.haisinsaki_na = haisinsaki_na;
	}
	public String getHaisinsakiNa()
	{
		return cutString(this.haisinsaki_na,HAISINSAKI_NA_MAX_LENGTH);
	}
	public String getHaisinsakiNaString()
	{
		return "'" + cutString(this.haisinsaki_na,HAISINSAKI_NA_MAX_LENGTH) + "'";
	}

// 20021219 add start A.Tashiro for V2
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
// 20021219 add end


	public void setDataDownloadKahiFg(String data_download_kahi_fg)
	{
		this.data_download_kahi_fg = data_download_kahi_fg;
	}
	public String getDataDownloadKahiFg()
	{
		return cutString(this.data_download_kahi_fg,DATA_DOWNLOAD_KAHI_FG_MAX_LENGTH);
	}
	public String getDataDownloadKahiFgString()
	{
		return "'" + cutString(this.data_download_kahi_fg,DATA_DOWNLOAD_KAHI_FG_MAX_LENGTH) + "'";
	}

	public void setDenpatuKahiFg(String denpatu_kahi_fg)
	{
		this.denpatu_kahi_fg = denpatu_kahi_fg;
	}
	public String getDenpatuKahiFg()
	{
		return cutString(this.denpatu_kahi_fg,DENPATU_KAHI_FG_MAX_LENGTH);
	}
	public String getDenpatuKahiFgString()
	{
		return "'" + cutString(this.denpatu_kahi_fg,DENPATU_KAHI_FG_MAX_LENGTH) + "'";
	}

	public void setFileKeisikiKb(String file_keisiki_kb)
	{
		this.file_keisiki_kb = file_keisiki_kb;
	}
	public String getFileKeisikiKb()
	{
		return cutString(this.file_keisiki_kb,FILE_KEISIKI_KB_MAX_LENGTH);
	}
	public String getFileKeisikiKbString()
	{
		return "'" + cutString(this.file_keisiki_kb,FILE_KEISIKI_KB_MAX_LENGTH) + "'";
	}

	public void setDataHojiKijunDt(String data_hoji_kijun_dt)
	{
		this.data_hoji_kijun_dt = Long.parseLong(data_hoji_kijun_dt);
	}
	public void setDataHojiKijunDt(long data_hoji_kijun_dt)
	{
		this.data_hoji_kijun_dt = data_hoji_kijun_dt;
	}
	public long getDataHojiKijunDt()
	{
		return this.data_hoji_kijun_dt;
	}
	public String getDataHojiKijunDtString()
	{
		return Long.toString(this.data_hoji_kijun_dt);
	}

	public void setDataHojiKikanMn(String data_hoji_kikan_mn)
	{
		this.data_hoji_kikan_mn = Long.parseLong(data_hoji_kikan_mn);
	}
	public void setDataHojiKikanMn(long data_hoji_kikan_mn)
	{
		this.data_hoji_kikan_mn = data_hoji_kikan_mn;
	}
	public long getDataHojiKikanMn()
	{
		return this.data_hoji_kikan_mn;
	}
	public String getDataHojiKikanMnString()
	{
		return Long.toString(this.data_hoji_kikan_mn);
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

	public void setDataMergeKahiKb(String data_merge_kahi_kb)
	{
		this.data_merge_kahi_kb = data_merge_kahi_kb;
	}
	public String getDataMergeKahiKb()
	{
		return cutString(this.data_merge_kahi_kb,DATA_MERGE_KAHI_KB_MAX_LENGTH);
	}
	public String getDataMergeKahiKbString()
	{
		return "'" + cutString(this.data_merge_kahi_kb,DATA_MERGE_KAHI_KB_MAX_LENGTH) + "'";
	}
// 20021225 add start A.Tashiro for V2
	public void setZipKb(String zip_kb)
	{
		this.zip_kb = zip_kb;
	}
	public String getZipKb()
	{
		return cutString(this.zip_kb,ZIP_KB_MAX_LENGTH);
	}
	public String getZipKbString()
	{
		return "'" + cutString(this.zip_kb,ZIP_KB_MAX_LENGTH) + "'";
	}

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
// 20021225 add end

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
// 20021225 rep start A.Tashiro for V2
//		return "  haisinsaki_cd = " + getHaisinsakiCdString()  + "  haisinsaki_na = " + getHaisinsakiNaString()  + "  data_download_kahi_fg = " + getDataDownloadKahiFgString()  + "  denpatu_kahi_fg = " + getDenpatuKahiFgString()  + "  file_keisiki_kb = " + getFileKeisikiKbString()  + "  data_hoji_kijun_dt = " + getDataHojiKijunDtString()  + "  data_hoji_kikan_mn = " + getDataHojiKikanMnString()  + "  insert_ts = " + getInsertTsString()  + "  data_merge_kahi_kb = " + getDataMergeKahiKbString()  + "  update_ts = " + getUpdateTsString() ;
		return "  haisinsaki_cd = " + getHaisinsakiCdString()  + "  haisinsaki_na = " + getHaisinsakiNaString()  + " torihikisaki_cd = " + getTorihikisakiCdString() + " data_download_kahi_fg = " + getDataDownloadKahiFgString()  + "  denpatu_kahi_fg = " + getDenpatuKahiFgString()  + "  file_keisiki_kb = " + getFileKeisikiKbString()  + "  data_hoji_kijun_dt = " + getDataHojiKijunDtString()  + "  data_hoji_kikan_mn = " + getDataHojiKikanMnString()  + "  insert_ts = " + getInsertTsString()  + "  data_merge_kahi_kb = " + getDataMergeKahiKbString()  + " zip_kb = " + getZipKbString() + " password_key_cd = " + getPasswordKeyCdString() + "  update_ts = " + getUpdateTsString();
// 20021225 rep end
	}
	private String cutString( String base, int max )
	{
		String wk = "";
		//Null対応
		if( base == null )  //2002/08/09 added by yoshimoto
			return wk;      //2002/08/09 added by yoshimoto

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
