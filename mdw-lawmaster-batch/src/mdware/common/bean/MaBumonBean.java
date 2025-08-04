package mdware.common.bean;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: MaBumonBean クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author Bean Creator(2002.09.09) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2003/10/6 14:59:58)
 */
public class MaBumonBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private static final String CHARSET_NAME = "UTF-8";

	private static final String NULL_VAL = "NULL";

	public static final int BUMON_CD_MAX_LENGTH              =  2;
	public static final int BUMON_NA_MAX_LENGTH              =  20;
	public static final int RIYO_USER_ID_MAX_LENGTH          =  20;
	public static final int INSERT_TS_MAX_LENGTH             =  20;
	public static final int UPDATE_TS_MAX_LENGTH             =  20;

	private String bumon_cd = null;
	private String bumon_na = null;
	private String riyo_user_id = null;
	private String insert_ts = null;
	private String update_ts = null;

	// bumon_cd に対するセッタメソッド。
	public void setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
	}
	// bumon_cd に対するゲッタメソッド。
	public String getBumonCd()
	{
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString()
	{
		//return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
		if(this.bumon_cd == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}

	// bumon_na に対するセッタメソッド。
	public void setBumonNa(String bumon_na)
	{
		this.bumon_na = bumon_na;
	}
	// bumon_na に対するゲッタメソッド。
	public String getBumonNa()
	{
		return cutString(this.bumon_na,BUMON_NA_MAX_LENGTH);
	}
	public String getBumonNaString()
	{
		//return "'" + cutString(this.bumon_na,BUMON_NA_MAX_LENGTH) + "'";
		if(this.bumon_na == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.bumon_na,BUMON_NA_MAX_LENGTH) + "'";
	}

	// riyo_user_id に対するセッタメソッド。
	public void setRiyoUserId(String riyo_user_id)
	{
		this.riyo_user_id = riyo_user_id;
	}
	// riyo_user_id に対するゲッタメソッド。
	public String getRiyoUserId()
	{
		return cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH);
	}
	public String getRiyoUserIdString()
	{
		//return "'" + cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
		if(this.riyo_user_id == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
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
		  " bumon_cd = "                  + getBumonCdString()
		+ " bumon_na = "                  + getBumonNaString()
		+ " riyo_user_id = "              + getRiyoUserIdString()
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
