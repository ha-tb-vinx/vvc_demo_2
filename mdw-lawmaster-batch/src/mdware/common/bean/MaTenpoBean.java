package mdware.common.bean;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: MaTenpoBean クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author Bean Creator(2002.09.09) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2003/10/8 9:17:53)
 */
public class MaTenpoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private static final String CHARSET_NAME = "UTF-8";

	private static final String NULL_VAL = "NULL";

	public static final int TENPO_CD_MAX_LENGTH              =  10;
	public static final int TENPO_NA_MAX_LENGTH              =  80;
	public static final int TENPO_KA_MAX_LENGTH              =  40;
	public static final int EIGYO_KB_MAX_LENGTH              =  1;
	public static final int KAITEN_DT_MAX_LENGTH             =  8;
	public static final int EDI_KB_MAX_LENGTH                =  1;
	public static final int RIYO_USER_ID_MAX_LENGTH          =  20;
	public static final int INSERT_TS_MAX_LENGTH             =  20;
	public static final int UPDATE_TS_MAX_LENGTH             =  20;


	private String tenpo_cd = null;
	private String tenpo_na = null;
	private String tenpo_ka = null;
	private String eigyo_kb = null;
	private String kaiten_dt = null;
	private String edi_kb = null;
	private String riyo_user_id = null;
	private String insert_ts = null;
	private String update_ts = null;

	// tenpo_cd に対するセッタメソッド。
	public void setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
	}
	// tenpo_cd に対するゲッタメソッド。
	public String getTenpoCd()
	{
		return cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		//return "'" + cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
		if(this.tenpo_cd == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
	}

	// tenpo_na に対するセッタメソッド。
	public void setTenpoNa(String tenpo_na)
	{
		this.tenpo_na = tenpo_na;
	}
	// tenpo_na に対するゲッタメソッド。
	public String getTenpoNa()
	{
		return cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH);
	}
	public String getTenpoNaString()
	{
		//return "'" + cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH) + "'";
		if(this.tenpo_na == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.tenpo_na,TENPO_NA_MAX_LENGTH) + "'";
	}

	// tenpo_ka に対するセッタメソッド。
	public void setTenpoKa(String tenpo_ka)
	{
		this.tenpo_ka = tenpo_ka;
	}
	// tenpo_ka に対するゲッタメソッド。
	public String getTenpoKa()
	{
		return cutString(this.tenpo_ka,TENPO_KA_MAX_LENGTH);
	}
	public String getTenpoKaString()
	{
		//return "'" + cutString(this.tenpo_ka,TENPO_KA_MAX_LENGTH) + "'";
		if(this.tenpo_ka == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.tenpo_ka,TENPO_KA_MAX_LENGTH) + "'";
	}

	// eigyo_kb に対するセッタメソッド。
	public void setEigyoKb(String eigyo_kb)
	{
		this.eigyo_kb = eigyo_kb;
	}
	// eigyo_kb に対するゲッタメソッド。
	public String getEigyoKb()
	{
		return cutString(this.eigyo_kb,EIGYO_KB_MAX_LENGTH);
	}
	public String getEigyoKbString()
	{
		//return "'" + cutString(this.eigyo_kb,EIGYO_KB_MAX_LENGTH) + "'";
		if(this.eigyo_kb == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.eigyo_kb,EIGYO_KB_MAX_LENGTH) + "'";
	}

	// kaiten_dt に対するセッタメソッド。
	public void setKaitenDt(String kaiten_dt)
	{
		this.kaiten_dt = kaiten_dt;
	}
	// kaiten_dt に対するゲッタメソッド。
	public String getKaitenDt()
	{
		return cutString(this.kaiten_dt,KAITEN_DT_MAX_LENGTH);
	}
	public String getKaitenDtString()
	{
		//return "'" + cutString(this.kaiten_dt,KAITEN_DT_MAX_LENGTH) + "'";
		if(this.kaiten_dt == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.kaiten_dt,KAITEN_DT_MAX_LENGTH) + "'";
	}

	// edi_kb に対するセッタメソッド。
	public void setEdiKb(String edi_kb)
	{
		this.edi_kb = edi_kb;
	}
	// edi_kb に対するゲッタメソッド。
	public String getEdiKb()
	{
		return cutString(this.edi_kb,EDI_KB_MAX_LENGTH);
	}
	public String getEdiKbString()
	{
		//return "'" + cutString(this.edi_kb,EDI_KB_MAX_LENGTH) + "'";
		if(this.edi_kb == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.edi_kb,EDI_KB_MAX_LENGTH) + "'";
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
		  " tenpo_cd = "                  + getTenpoCdString()
		+ " tenpo_na = "                  + getTenpoNaString()
		+ " tenpo_ka = "                  + getTenpoKaString()
		+ " eigyo_kb = "                  + getEigyoKbString()
		+ " kaiten_dt = "                 + getKaitenDtString()
		+ " edi_kb = "                    + getEdiKbString()
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
