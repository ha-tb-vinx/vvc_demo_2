package mdware.common.bean;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: RSantiBean クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author Bean Creator(2002.09.09) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2003/10/8 11:41:35)
 */
public class RSantiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private static final String CHARSET_NAME = "UTF-8";

	private static final String NULL_VAL = "NULL";

	public static final int SANTI_CD_MAX_LENGTH              =  4;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH       =  11;
	public static final int SANTI_NA_MAX_LENGTH              =  20;
	public static final int INSERT_TS_MAX_LENGTH             =  20;
	public static final int UPDATE_TS_MAX_LENGTH             =  20;


	private String santi_cd = null;
	private String torihikisaki_cd = null;
	private String santi_na = null;
	private String insert_ts = null;
	private String update_ts = null;

	// santi_cd に対するセッタメソッド。
	public void setSantiCd(String santi_cd)
	{
		this.santi_cd = santi_cd;
	}
	// santi_cd に対するゲッタメソッド。
	public String getSantiCd()
	{
		return cutString(this.santi_cd,SANTI_CD_MAX_LENGTH);
	}
	public String getSantiCdString()
	{
		//return "'" + cutString(this.santi_cd,SANTI_CD_MAX_LENGTH) + "'";
		if(this.santi_cd == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.santi_cd,SANTI_CD_MAX_LENGTH) + "'";
	}

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

	// santi_na に対するセッタメソッド。
	public void setSantiNa(String santi_na)
	{
		this.santi_na = santi_na;
	}
	// santi_na に対するゲッタメソッド。
	public String getSantiNa()
	{
		return cutString(this.santi_na,SANTI_NA_MAX_LENGTH);
	}
	public String getSantiNaString()
	{
		//return "'" + cutString(this.santi_na,SANTI_NA_MAX_LENGTH) + "'";
		if(this.santi_na == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.santi_na,SANTI_NA_MAX_LENGTH) + "'";
	}

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
		  " santi_cd = "                  + getSantiCdString()
		+ " torihikisaki_cd = "           + getTorihikisakiCdString()
		+ " santi_na = "                  + getSantiNaString()
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
