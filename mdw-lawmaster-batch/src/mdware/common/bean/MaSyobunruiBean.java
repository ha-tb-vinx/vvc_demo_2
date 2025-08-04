package mdware.common.bean;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: MaSyobunruiBean クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: </p>
 * @author Bean Creator(2002.09.09) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2004/1/21 17:51:7)
 */
public class MaSyobunruiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private static final String CHARSET_NAME = "UTF-8";

	private static final String NULL_VAL = "NULL";

	public static final int SYOBUNRUI_CD_MAX_LENGTH          =  6;
	public static final int SYOBUNRUI_NA_MAX_LENGTH          =  40;
	public static final int SYOBUNRUI_KA_MAX_LENGTH          =  30;
	public static final int DAIBUNRUI_CD_MAX_LENGTH          =  2;
	public static final int CHUBUNRUI_CD_MAX_LENGTH          =  4;
	public static final int RIYO_USER_ID_MAX_LENGTH          =  20;
	public static final int INSERT_TS_MAX_LENGTH             =  20;
	public static final int UPDATE_TS_MAX_LENGTH             =  20;

	private String syobunrui_cd = null;
	private String syobunrui_na = null;
	private String syobunrui_ka = null;
	private String daibunrui_cd = null;
	private String chubunrui_cd = null;
	private String riyo_user_id = null;
	private String insert_ts = null;
	private String update_ts = null;

	// syobunrui_cd に対するセッタメソッド。
	public void setSyobunruiCd(String syobunrui_cd)
	{
		this.syobunrui_cd = syobunrui_cd;
	}
	// syobunrui_cd に対するゲッタメソッド。
	public String getSyobunruiCd()
	{
		return cutString(this.syobunrui_cd,SYOBUNRUI_CD_MAX_LENGTH);
	}
	public String getSyobunruiCdString()
	{
		//return "'" + cutString(this.syobunrui_cd,SYOBUNRUI_CD_MAX_LENGTH) + "'";
		if(this.syobunrui_cd == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.syobunrui_cd,SYOBUNRUI_CD_MAX_LENGTH) + "'";
	}

	// syobunrui_na に対するセッタメソッド。
	public void setSyobunruiNa(String syobunrui_na)
	{
		this.syobunrui_na = syobunrui_na;
	}
	// syobunrui_na に対するゲッタメソッド。
	public String getSyobunruiNa()
	{
		return cutString(this.syobunrui_na,SYOBUNRUI_NA_MAX_LENGTH);
	}
	public String getSyobunruiNaString()
	{
		//return "'" + cutString(this.syobunrui_na,SYOBUNRUI_NA_MAX_LENGTH) + "'";
		if(this.syobunrui_na == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.syobunrui_na,SYOBUNRUI_NA_MAX_LENGTH) + "'";
	}

	// syobunrui_ka に対するセッタメソッド。
	public void setSyobunruiKa(String syobunrui_ka)
	{
		this.syobunrui_ka = syobunrui_ka;
	}
	// syobunrui_ka に対するゲッタメソッド。
	public String getSyobunruiKa()
	{
		return cutString(this.syobunrui_ka,SYOBUNRUI_KA_MAX_LENGTH);
	}
	public String getSyobunruiKaString()
	{
		//return "'" + cutString(this.syobunrui_ka,SYOBUNRUI_KA_MAX_LENGTH) + "'";
		if(this.syobunrui_ka == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.syobunrui_ka,SYOBUNRUI_KA_MAX_LENGTH) + "'";
	}

	// daibunrui_cd に対するセッタメソッド。
	public void setDaibunruiCd(String daibunrui_cd)
	{
		this.daibunrui_cd = daibunrui_cd;
	}
	// daibunrui_cd に対するゲッタメソッド。
	public String getDaibunruiCd()
	{
		return cutString(this.daibunrui_cd,DAIBUNRUI_CD_MAX_LENGTH);
	}
	public String getDaibunruiCdString()
	{
		//return "'" + cutString(this.daibunrui_cd,DAIBUNRUI_CD_MAX_LENGTH) + "'";
		if(this.daibunrui_cd == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.daibunrui_cd,DAIBUNRUI_CD_MAX_LENGTH) + "'";
	}

	// chubunrui_cd に対するセッタメソッド。
	public void setChubunruiCd(String chubunrui_cd)
	{
		this.chubunrui_cd = chubunrui_cd;
	}
	// chubunrui_cd に対するゲッタメソッド。
	public String getChubunruiCd()
	{
		return cutString(this.chubunrui_cd,CHUBUNRUI_CD_MAX_LENGTH);
	}
	public String getChubunruiCdString()
	{
		//return "'" + cutString(this.chubunrui_cd,CHUBUNRUI_CD_MAX_LENGTH) + "'";
		if(this.chubunrui_cd == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.chubunrui_cd,CHUBUNRUI_CD_MAX_LENGTH) + "'";
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
		  " syobunrui_cd = "              + getSyobunruiCdString()
		+ " syobunrui_na = "              + getSyobunruiNaString()
		+ " syobunrui_ka = "              + getSyobunruiKaString()
		+ " daibunrui_cd = "              + getDaibunruiCdString()
		+ " chubunrui_cd = "              + getChubunruiCdString()
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
