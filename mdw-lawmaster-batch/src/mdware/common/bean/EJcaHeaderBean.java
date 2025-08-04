package mdware.common.bean;

import java.util.*;
import java.io.UnsupportedEncodingException;
import jp.co.vinculumjapan.stc.log.StcLog;
/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 * @version pre1.0 2003.12.18 取引先コードを追加
 */
public class EJcaHeaderBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private static final String CHARSET_NAME = "UTF-8";

	private static final String NULL_VAL = "NULL";

	public static final int ID_CD_MAX_LENGTH = 10;
	public static final int KOURI_CD_MAX_LENGTH = 20;
	public static final int HAISINSAKI_CD_MAX_LENGTH = 11;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH       =  11;
	public static final int YOKYU_KB_MAX_LENGTH = 2;
	public static final int DENSO_DT_MAX_LENGTH = 20;
	public static final int CENTER_CD_MAX_LENGTH = 10;
	public static final int SIKIBETUSI_KB_MAX_LENGTH = 2;
	public static final int DATA_SYURUI_KB_MAX_LENGTH = 2;
	public static final int SYORI_KB_MAX_LENGTH = 2;
	public static final int DATA_CONVERT_DT_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 20;
//	public static final int DATABASE_INSERT_DT_MAX_LENGTH = 1000; // 20021218 @UPD yamanaka
	public static final int DATABASE_INSERT_DT_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
//	public static final int FILE_NA_MAX_LENGTH = 20; // 20021218 @UPD yamanaka
	public static final int FILE_NA_MAX_LENGTH = 1000;

// 20021211 @ADD yamanaka V2でDB変更により start
	public static final int DATA_SYUBETU_CD_MAX_LENGTH = 2;
// 20021211 @ADD yamanaka V2でDB変更により end

	private long jca_control_nb = 0;
	private String id_cd = "";
	private String kouri_cd = "";
	private String haisinsaki_cd = "";
	private String torihikisaki_cd = null;
	private String yokyu_kb = "";
	private String denso_dt = "";
	private String center_cd = "";
	private String sikibetusi_kb = "";
	private String data_syurui_kb = "";
	private long data_count_1_vl = 0;
	private long data_count_2_vl = 0;
	private String syori_kb = "";
	private String data_convert_dt = "";
	private String insert_ts = "";
	private String database_insert_dt = "";
	private String update_ts = "";
	private String file_na = "";

// 20021211 @ADD yamanaka V2でDB変更により start
	private String data_syubetu_cd = null;
// 20021211 @ADD yamanaka V2でDB変更により end

	public void setJcaControlNb(String jca_control_nb)
	{
		this.jca_control_nb = Long.parseLong(jca_control_nb);
	}
	public void setJcaControlNb(long jca_control_nb)
	{
		this.jca_control_nb = jca_control_nb;
	}
	public long getJcaControlNb()
	{
		return this.jca_control_nb;
	}
	public String getJcaControlNbString()
	{
		return Long.toString(this.jca_control_nb);
	}

	public void setIdCd(String id_cd)
	{
		this.id_cd = id_cd;
	}
	public String getIdCd()
	{
		return cutString(this.id_cd,ID_CD_MAX_LENGTH);
	}
	public String getIdCdString()
	{
	// 20030311 @UPD '対応
		//return "'" + cutString(this.id_cd,ID_CD_MAX_LENGTH) + "'";
		return "'" + mdware.common.util.StringUtility.singleQuotesfilter(cutString(this.id_cd,ID_CD_MAX_LENGTH)) + "'";
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

	public void setYokyuKb(String yokyu_kb)
	{
		this.yokyu_kb = yokyu_kb;
	}
	public String getYokyuKb()
	{
		return cutString(this.yokyu_kb,YOKYU_KB_MAX_LENGTH);
	}
	public String getYokyuKbString()
	{
	// 20030311 @UPD '対応
		//return "'" + cutString(this.yokyu_kb,YOKYU_KB_MAX_LENGTH) + "'";
		return "'" + mdware.common.util.StringUtility.singleQuotesfilter(cutString(this.yokyu_kb,YOKYU_KB_MAX_LENGTH)) + "'";
	}

	public void setDensoDt(String denso_dt)
	{
		this.denso_dt = denso_dt;
	}
	public String getDensoDt()
	{
		return cutString(this.denso_dt,DENSO_DT_MAX_LENGTH);
	}
	public String getDensoDtString()
	{
		return "'" + cutString(this.denso_dt,DENSO_DT_MAX_LENGTH) + "'";
	}

	public void setCenterCd(String center_cd)
	{
		this.center_cd = center_cd;
	}
	public String getCenterCd()
	{
		return cutString(this.center_cd,CENTER_CD_MAX_LENGTH);
	}
	public String getCenterCdString()
	{
		return "'" + cutString(this.center_cd,CENTER_CD_MAX_LENGTH) + "'";
	}

	public void setSikibetusiKb(String sikibetusi_kb)
	{
		this.sikibetusi_kb = sikibetusi_kb;
	}
	public String getSikibetusiKb()
	{
		return cutString(this.sikibetusi_kb,SIKIBETUSI_KB_MAX_LENGTH);
	}
	public String getSikibetusiKbString()
	{
	// 20030311 @UPD '対応
		//return "'" + cutString(this.sikibetusi_kb,SIKIBETUSI_KB_MAX_LENGTH) + "'";
		return "'" + mdware.common.util.StringUtility.singleQuotesfilter(cutString(this.sikibetusi_kb,SIKIBETUSI_KB_MAX_LENGTH)) + "'";
	}

	public void setDataSyuruiKb(String data_syurui_kb)
	{
		this.data_syurui_kb = data_syurui_kb;
	}
	public String getDataSyuruiKb()
	{
		return cutString(this.data_syurui_kb,DATA_SYURUI_KB_MAX_LENGTH);
	}
	public String getDataSyuruiKbString()
	{
	// 20030311 @UPD '対応
		//return "'" + cutString(this.data_syurui_kb,DATA_SYURUI_KB_MAX_LENGTH) + "'";
		return "'" + mdware.common.util.StringUtility.singleQuotesfilter(cutString(this.data_syurui_kb,DATA_SYURUI_KB_MAX_LENGTH)) + "'";
	}

	public void setDataCount1Vl(String data_count_1_vl)
	{
		this.data_count_1_vl = Long.parseLong(data_count_1_vl);
	}
	public void setDataCount1Vl(long data_count_1_vl)
	{
		this.data_count_1_vl = data_count_1_vl;
	}
	public long getDataCount1Vl()
	{
		return this.data_count_1_vl;
	}
	public String getDataCount1VlString()
	{
		return Long.toString(this.data_count_1_vl);
	}

	public void setDataCount2Vl(String data_count_2_vl)
	{
		this.data_count_2_vl = Long.parseLong(data_count_2_vl);
	}
	public void setDataCount2Vl(long data_count_2_vl)
	{
		this.data_count_2_vl = data_count_2_vl;
	}
	public long getDataCount2Vl()
	{
		return this.data_count_2_vl;
	}
	public String getDataCount2VlString()
	{
		return Long.toString(this.data_count_2_vl);
	}

	public void setSyoriKb(String syori_kb)
	{
		this.syori_kb = syori_kb;
	}
	public String getSyoriKb()
	{
		return cutString(this.syori_kb,SYORI_KB_MAX_LENGTH);
	}
	public String getSyoriKbString()
	{
	// 20030311 @UPD '対応
		//return "'" + cutString(this.syori_kb,SYORI_KB_MAX_LENGTH) + "'";
		return "'" + mdware.common.util.StringUtility.singleQuotesfilter(cutString(this.syori_kb,SYORI_KB_MAX_LENGTH)) + "'";
	}

	public void setDataConvertDt(String data_convert_dt)
	{
		this.data_convert_dt = data_convert_dt;
	}
	public String getDataConvertDt()
	{
		return cutString(this.data_convert_dt,DATA_CONVERT_DT_MAX_LENGTH);
	}
	public String getDataConvertDtString()
	{
		return "'" + cutString(this.data_convert_dt,DATA_CONVERT_DT_MAX_LENGTH) + "'";
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

	public void setDatabaseInsertDt(String database_insert_dt)
	{
		this.database_insert_dt = database_insert_dt;
	}
	public String getDatabaseInsertDt()
	{
		return cutString(this.database_insert_dt,DATABASE_INSERT_DT_MAX_LENGTH);
	}
	public String getDatabaseInsertDtString()
	{
		return "'" + cutString(this.database_insert_dt,DATABASE_INSERT_DT_MAX_LENGTH) + "'";
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

	public void setFileNa(String file_na)
	{
		this.file_na = file_na;
	}
	public String getFileNa()
	{
		return cutString(this.file_na,FILE_NA_MAX_LENGTH);
	}
	public String getFileNaString()
	{
	// 20030311 @UPD '対応
		//return "'" + cutString(this.file_na,FILE_NA_MAX_LENGTH) + "'";
		return "'" + mdware.common.util.StringUtility.singleQuotesfilter(cutString(this.file_na,FILE_NA_MAX_LENGTH)) + "'";
	}

// 20021211 @ADD yamanaka V2でDB変更により start
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
// 20021211 @ADD yamanaka V2でDB変更により end


//@add 20031218
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


	public String toString()
	{
// 20021211 @UPD yamanaka V2でDB変更により start
//		return "  jca_control_nb = " + getJcaControlNbString()  + "  id_cd = " + getIdCdString()	+ "  kouri_cd = " + getKouriCdString()	+ "  haisinsaki_cd = " + getHaisinsakiCdString()  + "  yokyu_kb = " + getYokyuKbString()  + "  denso_dt = " + getDensoDtString()	+ "  center_cd = " + getCenterCdString()  + "  sikibetusi_kb = " + getSikibetusiKbString()	+ "  data_syurui_kb = " + getDataSyuruiKbString()  + "  data_count_1_vl = " + getDataCount1VlString()  + "  data_count_2_vl = " + getDataCount2VlString()  + "  syori_kb = " + getSyoriKbString()  + "  data_convert_dt = " + getDataConvertDtString()  + "  insert_ts = " + getInsertTsString()  + "  database_insert_dt = " + getDatabaseInsertDtString()  + "  update_ts = " + getUpdateTsString()  + "  file_na = " + getFileNaString() ;
		return "  jca_control_nb = " + getJcaControlNbString()  + "  kouri_cd = " + getKouriCdString()  + "  id_cd = " + getIdCdString()  + "  haisinsaki_cd = " + getHaisinsakiCdString()  + "  yokyu_kb = " + getYokyuKbString()  + "  denso_dt = " + getDensoDtString()  + "  center_cd = " + getCenterCdString()  + "  sikibetusi_kb = " + getSikibetusiKbString()  + "  data_syurui_kb = " + getDataSyuruiKbString()  + "  data_count_1_vl = " + getDataCount1VlString()  + "  data_count_2_vl = " + getDataCount2VlString()  + "  data_convert_dt = " + getDataConvertDtString()  + "  database_insert_dt = " + getDatabaseInsertDtString()  + "  syori_kb = " + getSyoriKbString()  + "  data_syubetu_cd = " + getDataSyubetuCdString()  + "  file_na = " + getFileNaString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
// 20021211 @UPD yamanaka V2でDB変更により end
	}
	private String cutString( String base, int max )
	{
// 20021211 @ADD yamanaka nullは危険なので追加 start
		if( base == null ) return "";
// 20021211 @ADD yamanaka nullは危険なので追加 end
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

}
