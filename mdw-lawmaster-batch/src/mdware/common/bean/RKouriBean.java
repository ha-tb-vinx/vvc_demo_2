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
public class RKouriBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KOURI_CD_MAX_LENGTH = 10;
	public static final int KOURI_NA_MAX_LENGTH = 60;
	public static final int HOST_IF_KEISIKI_KB_MAX_LENGTH = 1;
	public static final int DATA_SAISYORI_KB_MAX_LENGTH = 1;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
// 20030201 @ADD tashiro pororoca対応でDB変更により start
	public static final int MASTER_DOWNLOAD_KB_MAX_LENGTH = 1;
// 20021212 @ADD tashiro pororoca対応でDB変更により end

	private String kouri_cd = null;
	private String kouri_na = null;
	private String host_if_keisiki_kb = null;
	private String data_saisyori_kb = null;
	private long data_hoji_kijun_dt = 0;
	private long data_hoji_kikan_mn = 0;
// 20021212 @ADD yamanaka V2でDB変更により start
	private long data_input_lines_qt = 0;
// 20021212 @ADD yamanaka V2でDB変更により end
	private String insert_ts = null;
	private String update_ts = null;
// 20030201 @ADD tashiro pororoca対応でDB変更により start
	private String master_download_kb = null;
// 20021212 @ADD tashiro pororoca対応でDB変更により end
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

	public void setKouriNa(String kouri_na)
	{
		this.kouri_na = kouri_na;
	}
	public String getKouriNa()
	{
		return cutString(this.kouri_na,KOURI_NA_MAX_LENGTH);
	}
	public String getKouriNaString()
	{
		return "'" + cutString(this.kouri_na,KOURI_NA_MAX_LENGTH) + "'";
	}

	public void setHostIfKeisikiKb(String host_if_keisiki_kb)
	{
		this.host_if_keisiki_kb = host_if_keisiki_kb;
	}
	public String getHostIfKeisikiKb()
	{
		return cutString(this.host_if_keisiki_kb,HOST_IF_KEISIKI_KB_MAX_LENGTH);
	}
	public String getHostIfKeisikiKbString()
	{
		return "'" + cutString(this.host_if_keisiki_kb,HOST_IF_KEISIKI_KB_MAX_LENGTH) + "'";
	}

	public void setDataSaisyoriKb(String data_saisyori_kb)
	{
		this.data_saisyori_kb = data_saisyori_kb;
	}
	public String getDataSaisyoriKb()
	{
		return cutString(this.data_saisyori_kb,DATA_SAISYORI_KB_MAX_LENGTH);
	}
	public String getDataSaisyoriKbString()
	{
		return "'" + cutString(this.data_saisyori_kb,DATA_SAISYORI_KB_MAX_LENGTH) + "'";
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
// 20021212 @ADD yamanaka V2でDB変更により start
	public void setDataInputLinesQt(String data_input_lines_qt)
	{
		this.data_input_lines_qt = Long.parseLong(data_input_lines_qt);
	}
	public void setDataInputLinesQt(long data_input_lines_qt)
	{
		this.data_input_lines_qt = data_input_lines_qt;
	}
	public long getDataInputLinesQt()
	{
		return this.data_input_lines_qt;
	}
	public String getDataInputLinesQtString()
	{
		return Long.toString(this.data_input_lines_qt);
	}
// 20021212 @ADD yamanaka V2でDB変更により end

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
// 20030201 @Add tashiro pororoca対応でDB変更により start
	public void setMasterDownloadKb(String master_download_kb)
	{
		this.master_download_kb = master_download_kb;
	}
	public String getMasterDownloadKb()
	{
		return cutString(this.master_download_kb,MASTER_DOWNLOAD_KB_MAX_LENGTH);
	}
	public String getMasterDownloadKbString()
	{
		return "'" + cutString(this.master_download_kb,MASTER_DOWNLOAD_KB_MAX_LENGTH) + "'";
	}
// 20030201 @Add end

	public String toString()
	{

// 20021212 @UPD yamanaka V2でDB変更により start  : 20030201 @UPD tashiro pororoca対応でDB変更により start
//		return "  kouri_cd = " + getKouriCdString()  + "  kouri_na = " + getKouriNaString()  + "  host_if_keisiki_kb = " + getHostIfKeisikiKbString()  + "  data_saisyori_kb = " + getDataSaisyoriKbString()  + "  data_hoji_kijun_dt = " + getDataHojiKijunDtString()	+ "  data_hoji_kikan_mn = " + getDataHojiKikanMnString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
//		return "  kouri_cd = " + getKouriCdString()  + "  kouri_na = " + getKouriNaString()  + "  host_if_keisiki_kb = " + getHostIfKeisikiKbString()  + "  data_saisyori_kb = " + getDataSaisyoriKbString()  + "  data_hoji_kijun_dt = " + getDataHojiKijunDtString()	+ "  data_hoji_kikan_mn = " + getDataHojiKikanMnString()  + "  data_input_lines_qt = " + getDataInputLinesQtString() + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
		return "  kouri_cd = " + getKouriCdString()  + "  kouri_na = " + getKouriNaString()  + "  host_if_keisiki_kb = " + getHostIfKeisikiKbString()  + "  data_saisyori_kb = " + getDataSaisyoriKbString()  + "  data_hoji_kijun_dt = " + getDataHojiKijunDtString()	+ "  data_hoji_kikan_mn = " + getDataHojiKikanMnString()  + "  data_input_lines_qt = " + getDataInputLinesQtString() + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() + " master_download_kb = " + getMasterDownloadKbString();
// 20021212 @UPD yamanaka V2でDB変更により end : 20030201 @UPD tashiro end
	}
	private String cutString( String base, int max )
	{
		String wk = "";
		if( base == null )
			return "";
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
