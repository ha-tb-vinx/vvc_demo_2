package mdware.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import java.math.BigDecimal; //20030417 @ADD yamanaka
import java.io.UnsupportedEncodingException; //20031120 @ADD
/**
 * <p>タイトル: RB Site</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 3.00 2003.05.?? 3次本番リリース
 * @version 3.00 2003.04.17 yamanaka 消費税率を追加
 * @version 2.00 2003.04.21 2次本番リリース(予定)
 * @version 1.00 2003.03.12 本番リリース
 */

public class SystemConfBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private static final String CHARSET_NAME = "UTF-8";  //20031120 @ADD

	public static final int HACHU_DATA_DIR_TX_MAX_LENGTH = 250;
// 20021213 @ADD deguchi V2でDB変更により start
	public static final int SHARE_DIR_COLLECT_TX_MAX_LENGTH = 250;
	public static final int SHARE_DIR_DISTRIBUTE_TX_MAX_LENGTH = 250;
	public static final int SHARE_DIR_MASTER_TX_MAX_LENGTH = 250;
	public static final int TEMP_DIR_TX_MAX_LENGTH = 250;
	public static final int DATA_HOME_DIR_TX_MAX_LENGTH = 250;
	public static final int BUCKUP_DIR_TX_MAX_LENGTH = 250;
// 20021213 @ADD deguchi V2でDB変更により end

//20031120 @ADD
	public static final int DOWNLOAD_DIR_TX_MAX_LENGTH       =  250;

// 20030417 @ADD yamanaka 消費税率追加 start
	private static final String NULL_VAL = "NULL";
	public static final int SYOHI_ZEI_RT_SCALE = 2;

	public static final int SYOHI_ZEI_RT_MODE = BigDecimal.ROUND_DOWN; // 切り捨て

	private BigDecimal syohi_zei_rt = null;
// 20030417 @ADD yamanaka 消費税率追加 end

	private int pwd_yuko_dy = 0;
	private int tmp_pwd_yuko_dy = 0;
	private int syslog_disp_dy = 0;
	private int syslog_keep_dy = 0;
	private String hachu_data_dir_tx = null;
// 20021213 @ADD deguchi V2でDB変更により start
	private int backup_hoji_kikan_dy = 0;
	private String share_dir_collect_tx = null;
	private String share_dir_distribute_tx = null;
	private String share_dir_master_tx = null;
	private String temp_dir_tx = null;
	private String data_home_dir_tx = null;
	private String backup_dir_tx = null;
// 20021213 @ADD deguchi V2でDB変更により end

// 20031011 @ADD start
	private long denpyo_meisai_row = 0;
	private long fresh_hachu_yotei_data_qt = 0;
	private long com_hachu_yotei_data_qt = 0;
	private long fresh_hachu_yotei_keep_qt = 0;
	private long com_hachu_yotei_keep_qt = 0;
// 20031011 @ADD end

// 20031219 @ADD kirihara 値引欠品率を追加 start
	public static final int NEBIKI_KEPPIN_RT_SCALE           = 2;
	public static final int NEBIKI_KEPPIN_RT_MODE            = BigDecimal.ROUND_DOWN; // 切り捨て
	private BigDecimal nebiki_keppin_rt = null;
// 20031219 @ADD kirihara 値引欠品率を追加 end

// 20040108 @ADD kurata 値引データ生成日を追加 start
	private int nebiki_make_dy = 0;
// 20040108 @ADD kurata 値引データ生成日を追加 end

// 20050108 @ADD takata 相場商品最大発注数、相場商品リードタイム日数を追加 start
	private int soba_max_hachu_qt = 0;
	private int soba_leadtime_dt = 0;
// 20050108 @ADD takata 相場商品最大発注数、相場商品リードタイム日数を追加 end

// 20031120 @ADD
	private String download_dir_tx = null;


	public void setPwdYukoDy(int pwd_yuko_dy) {
		this.pwd_yuko_dy = pwd_yuko_dy;
	}
	public int getPwdYukoDy() {
		return this.pwd_yuko_dy;
	}
	public String getPwdYukoDyString() {
		return Integer.toString(this.pwd_yuko_dy);
	}
	public void setTmpPwdYukoDy(int tmp_pwd_yuko_dy) {
		this.tmp_pwd_yuko_dy = tmp_pwd_yuko_dy;
	}
	public int getTmpPwdYukoDy() {
		return this.tmp_pwd_yuko_dy;
	}
	public String getTmpPwdYukoDyString() {
		return Integer.toString(this.tmp_pwd_yuko_dy);
	}
	public void setSyslogDispDy(int syslog_disp_dy) {
		this.syslog_disp_dy = syslog_disp_dy;
	}
	public int getSyslogDispDy() {
		return this.syslog_disp_dy;
	}
	public String getSyslogDispDyString() {
	    return Integer.toString(this.syslog_disp_dy);
	}
	public void setSyslogKeepDy(int syslog_keep_dy) {
	    this.syslog_keep_dy = syslog_keep_dy;
	}
	public int getSyslogKeepDy() {
		return this.syslog_keep_dy;
	}
	public String getSyslogKeepDyString() {
		return Integer.toString(this.syslog_keep_dy);
	}
	public void setHachuDataDirTx(String hachu_data_dir_tx) {
		this.hachu_data_dir_tx = hachu_data_dir_tx;
	}
// 20021213 @UPD deguchi V2でgetterメソッド追加により start
	public String getHachuDataDirTx() {
		return cutString(this.hachu_data_dir_tx, HACHU_DATA_DIR_TX_MAX_LENGTH);
	}
// 20021213 @UPD deguchi V2でgetterメソッド追加により end
// 20021213 @ADD deguchi V2でgetterメソッド追加により start
	public String getHachuDataDirTxString() {
		return "'" + cutString(this.hachu_data_dir_tx, HACHU_DATA_DIR_TX_MAX_LENGTH) + "'";
	}
// 20021213 @ADD deguchi V2でgetterメソッド追加により end
// 20021213 @ADD deguchi V2でDB変更により start
	public void setBackupHojiKikanDy(int backup_hoji_kikan_dy)
	{
		this.backup_hoji_kikan_dy = backup_hoji_kikan_dy;
	}
	public int getBackupHojiKikanDy()
	{
		return this.backup_hoji_kikan_dy;
	}
	public String getBackupHojiKikanDyString()
	{
		return Integer.toString(this.backup_hoji_kikan_dy);
	}

	public void setShareDirCollectTx(String share_dir_collect_tx)
	{
		this.share_dir_collect_tx = share_dir_collect_tx;
	}
	public String getShareDirCollectTx()
	{
		return cutString(this.share_dir_collect_tx,SHARE_DIR_COLLECT_TX_MAX_LENGTH);
	}
	public String getShareDirCollectTxString()
	{
		return "'" + cutString(this.share_dir_collect_tx,SHARE_DIR_COLLECT_TX_MAX_LENGTH) + "'";
	}

	public void setShareDirDistributeTx(String share_dir_distribute_tx)
	{
		this.share_dir_distribute_tx = share_dir_distribute_tx;
	}
	public String getShareDirDistributeTx()
	{
		return cutString(this.share_dir_distribute_tx,SHARE_DIR_DISTRIBUTE_TX_MAX_LENGTH);
	}
	public String getShareDirDistributeTxString()
	{
		return "'" + cutString(this.share_dir_distribute_tx,SHARE_DIR_DISTRIBUTE_TX_MAX_LENGTH) + "'";
	}

	public void setShareDirMasterTx(String share_dir_master_tx)
	{
		this.share_dir_master_tx = share_dir_master_tx;
	}
	public String getShareDirMasterTx()
	{
		return cutString(this.share_dir_master_tx,SHARE_DIR_MASTER_TX_MAX_LENGTH);
	}
	public String getShareDirMasterTxString()
	{
		return "'" + cutString(this.share_dir_master_tx,SHARE_DIR_MASTER_TX_MAX_LENGTH) + "'";
	}

	public void setTempDirTx(String temp_dir_tx)
	{
		this.temp_dir_tx = temp_dir_tx;
	}
	public String getTempDirTx()
	{
		return cutString(this.temp_dir_tx,TEMP_DIR_TX_MAX_LENGTH);
	}
	public String getTempDirTxString()
	{
		return "'" + cutString(this.temp_dir_tx,TEMP_DIR_TX_MAX_LENGTH) + "'";
	}

	public void setDataHomeDirTx(String data_home_dir_tx)
	{
		this.data_home_dir_tx = data_home_dir_tx;
	}
	public String getDataHomeDirTx()
	{
		return cutString(this.data_home_dir_tx,DATA_HOME_DIR_TX_MAX_LENGTH);
	}
	public String getDataHomeDirTxString()
	{
		return "'" + cutString(this.data_home_dir_tx,DATA_HOME_DIR_TX_MAX_LENGTH) + "'";
	}

	public void setBackupDirTx(String backup_dir_tx)
	{
		this.backup_dir_tx = backup_dir_tx;
	}
	public String getBackupDirTx()
	{
		return cutString(this.backup_dir_tx,BUCKUP_DIR_TX_MAX_LENGTH);
	}
	public String getBackupDirTxString()
	{
		return "'" + cutString(this.backup_dir_tx,BUCKUP_DIR_TX_MAX_LENGTH) + "'";
	}

// 20021213 @ADD deguchi V2でDB変更により end

// 20030417 @ADD yamanaka 消費税率追加 start
	// syohi_zei_rt に対するセッタメソッド。
	public void setSyohiZeiRt(String syohi_zei_rt)
	{
		try {
			if(syohi_zei_rt == null || "".equals(syohi_zei_rt)) {
				this.syohi_zei_rt = null;
			} else {
				setSyohiZeiRt(new BigDecimal(syohi_zei_rt));
			}
		} catch(NumberFormatException e) {
			;
		}
	}
	public void setSyohiZeiRt(BigDecimal syohi_zei_rt) {
		this.syohi_zei_rt = syohi_zei_rt;
	}
	public void setSyohiZeiRt(long syohi_zei_rt) {
		setSyohiZeiRt(String.valueOf(syohi_zei_rt));
	}
	public void setSyohiZeiRt(double syohi_zei_rt) {
		setSyohiZeiRt(String.valueOf(syohi_zei_rt));
	}
	// syohi_zei_rt に対するゲッタメソッド。
	public long getSyohiZeiRtLong() {
		if(syohi_zei_rt == null) {
			return 0L;
		}
		return syohi_zei_rt.longValue();
	}
	public double getSyohiZeiRtDouble() {
		if(syohi_zei_rt == null) {
			return 0.0;
		}
		return syohi_zei_rt.doubleValue();
	}
	public String getSyohiZeiRtString() {
		if(syohi_zei_rt == null) {
			return NULL_VAL;
		}
		return (syohi_zei_rt.setScale(SYOHI_ZEI_RT_SCALE, SYOHI_ZEI_RT_MODE)).toString();
	}
	public BigDecimal getSyohiZeiRt() {
		return syohi_zei_rt;
	}
// 20030417 @ADD yamanaka 消費税率追加 end

//20031011 @ADD start
	// denpyo_meisai_row に対するセッタメソッド。
	public void setDenpyoMeisaiRow(String denpyo_meisai_row)
	{
		this.denpyo_meisai_row = Long.parseLong(denpyo_meisai_row);
	}
	public void setDenpyoMeisaiRow(long denpyo_meisai_row)
	{
		this.denpyo_meisai_row = denpyo_meisai_row;
	}
	public long getDenpyoMeisaiRow()
	{
		return this.denpyo_meisai_row;
	}
	public String getDenpyoMeisaiRowString()
	{
		return Long.toString(this.denpyo_meisai_row);
	}

	// fresh_hachu_yotei_data_qt に対するセッタメソッド。
	public void setFreshHachuYoteiDataQt(String fresh_hachu_yotei_data_qt)
	{
		this.fresh_hachu_yotei_data_qt = Long.parseLong(fresh_hachu_yotei_data_qt);
	}
	public void setFreshHachuYoteiDataQt(long fresh_hachu_yotei_data_qt)
	{
		this.fresh_hachu_yotei_data_qt = fresh_hachu_yotei_data_qt;
	}
	public long getFreshHachuYoteiDataQt()
	{
		return this.fresh_hachu_yotei_data_qt;
	}
	public String getFreshHachuYoteiDataQtString()
	{
		return Long.toString(this.fresh_hachu_yotei_data_qt);
	}

	// com_hachu_yotei_data_qt に対するセッタメソッド。
	public void setComHachuYoteiDataQt(String com_hachu_yotei_data_qt)
	{
		this.com_hachu_yotei_data_qt = Long.parseLong(com_hachu_yotei_data_qt);
	}
	public void setComHachuYoteiDataQt(long com_hachu_yotei_data_qt)
	{
		this.com_hachu_yotei_data_qt = com_hachu_yotei_data_qt;
	}
	public long getComHachuYoteiDataQt()
	{
		return this.com_hachu_yotei_data_qt;
	}
	public String getComHachuYoteiDataQtString()
	{
		return Long.toString(this.com_hachu_yotei_data_qt);
	}

	// fresh_hachu_yotei_keep_qt に対するセッタメソッド。
	public void setFreshHachuYoteiKeepQt(String fresh_hachu_yotei_keep_qt)
	{
		this.fresh_hachu_yotei_keep_qt = Long.parseLong(fresh_hachu_yotei_keep_qt);
	}
	public void setFreshHachuYoteiKeepQt(long fresh_hachu_yotei_keep_qt)
	{
		this.fresh_hachu_yotei_keep_qt = fresh_hachu_yotei_keep_qt;
	}
	public long getFreshHachuYoteiKeepQt()
	{
		return this.fresh_hachu_yotei_keep_qt;
	}
	public String getFreshHachuYoteiKeepQtString()
	{
		return Long.toString(this.fresh_hachu_yotei_keep_qt);
	}

	// com_hachu_yotei_keep_qt に対するセッタメソッド。
	public void setComHachuYoteiKeepQt(String com_hachu_yotei_keep_qt)
	{
		this.com_hachu_yotei_keep_qt = Long.parseLong(com_hachu_yotei_keep_qt);
	}
	public void setComHachuYoteiKeepQt(long com_hachu_yotei_keep_qt)
	{
		this.com_hachu_yotei_keep_qt = com_hachu_yotei_keep_qt;
	}
	public long getComHachuYoteiKeepQt()
	{
		return this.com_hachu_yotei_keep_qt;
	}
	public String getComHachuYoteiKeepQtString()
	{
		return Long.toString(this.com_hachu_yotei_keep_qt);
	}
//20031011 @ADD end

// 20031219 @ADD kirihara 値引欠品率を追加 start
	// nebiki_keppin_rt に対するセッタメソッド。
	public void setNebikiKeppinRt(String nebiki_keppin_rt)
	{
		try {
			if(nebiki_keppin_rt == null || nebiki_keppin_rt.length() == 0) {
				this.nebiki_keppin_rt = null;
			} else {
				setNebikiKeppinRt(new BigDecimal(nebiki_keppin_rt));
			}
		} catch(NumberFormatException e) {
			;
		}
	}
	public void setNebikiKeppinRt(BigDecimal nebiki_keppin_rt) {
		this.nebiki_keppin_rt = nebiki_keppin_rt;
	}
	public void setNebikiKeppinRt(long nebiki_keppin_rt) {
		setNebikiKeppinRt(String.valueOf(nebiki_keppin_rt));
	}
	public void setNebikiKeppinRt(double nebiki_keppin_rt) {
		setNebikiKeppinRt(String.valueOf(nebiki_keppin_rt));
	}
	// nebiki_keppin_rt に対するゲッタメソッド。
	public long getNebikiKeppinRtLong() {
		if(nebiki_keppin_rt == null) {
			return 0L;
		}
		return nebiki_keppin_rt.longValue();
	}
	public double getNebikiKeppinRtDouble() {
		if(nebiki_keppin_rt == null) {
			return 0.0;
		}
		return nebiki_keppin_rt.doubleValue();
	}
	public String getNebikiKeppinRtString() {
		if(nebiki_keppin_rt == null) {
			return NULL_VAL;
		}
		return (nebiki_keppin_rt.setScale(NEBIKI_KEPPIN_RT_SCALE, NEBIKI_KEPPIN_RT_MODE)).toString();
	}
	public BigDecimal getNebikiKeppinRt() {
		return nebiki_keppin_rt;
	}
	public BigDecimal getNebikiKeppinRt(int roundingMode) {
		if(nebiki_keppin_rt == null) {
			return null;
		}
		return (nebiki_keppin_rt.setScale(NEBIKI_KEPPIN_RT_SCALE, roundingMode));
	}
// 20031219 @ADD kirihara 値引欠品率を追加 end

// 20040108 @ADD kurata 値引データ生成日を追加 start
	public void setNebikiMakeDy(int nebiki_make_dy) {
		this.nebiki_make_dy = nebiki_make_dy;
	}
	public int getNebikiMakeDy() {
		return this.nebiki_make_dy;
	}
	public String getNebikiMakeDyString() {
		return Integer.toString(this.nebiki_make_dy);
	}
// 20040108 @ADD kurata 値引データ生成日を追加 end

//20031120 @ADD start
	// download_dir_tx に対するセッタメソッド。
	public void setDownloadDirTx(String download_dir_tx)
	{
		this.download_dir_tx = download_dir_tx;
	}
	// download_dir_tx に対するゲッタメソッド。
	public String getDownloadDirTx()
	{
		return cutString(this.download_dir_tx,DOWNLOAD_DIR_TX_MAX_LENGTH);
	}
	public String getDownloadDirTxString()
	{
		//return "'" + cutString(this.download_dir_tx,DOWNLOAD_DIR_TX_MAX_LENGTH) + "'";
		if(this.download_dir_tx == null) {
			return NULL_VAL;
		}
		return "'" + cutStringForSql(this.download_dir_tx,DOWNLOAD_DIR_TX_MAX_LENGTH) + "'";
	}

//	20050108 @ADD takata 相場商品最大発注数を追加 start
	 public void setSobaMaxHachuQt(int soba_max_hachu_qt) {
		 this.soba_max_hachu_qt = soba_max_hachu_qt;
	 }
	 public int getSobaMaxHachuQt() {
		 return this.soba_max_hachu_qt;
	 }
	 public String getSobaMaxHachuQtString() {
		 return Integer.toString(this.soba_max_hachu_qt);
	 }
//	20050108 @ADD takata 相場商品最大発注数を追加 end

//	20050108 @ADD takata 相場商品リードタイム日数を追加 start
	 public void setSobaLeadtimeDt(int soba_leadtime_dt) {
		 this.soba_leadtime_dt = soba_leadtime_dt;
	 }
	 public int getSobaLeadtimeDt() {
		 return this.soba_leadtime_dt;
	 }
	 public String getSobaLeadtimeDtString() {
		 return Integer.toString(this.soba_leadtime_dt);
	 }
//	20050108 @ADD takata 相場商品リードタイム日数を追加 end

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

//20031120 @ADD end



// 20021213 @ADD deguchi 新規メソッド追加 start
	public String toString()
	{
		return "  pwd_yuko_dy = " + getPwdYukoDyString()  + "  tmp_pwd_yuko_dy = " + getTmpPwdYukoDyString()  + "  syslog_disp_dy = " + getSyslogDispDyString()  + "  syslog_keep_dy = " + getSyslogKeepDyString()  + "  hachu_data_dir_tx = " + getHachuDataDirTxString()  + "  backup_hoji_kikan_dy = " + getBackupHojiKikanDyString()  + "  share_dir_collect_tx = " + getShareDirCollectTxString()  + "  share_dir_distribute_tx = " + getShareDirDistributeTxString()  + "  share_dir_master_tx = " + getShareDirMasterTxString()  + "  temp_dir_tx = " + getTempDirTxString()  + "  data_home_dir_tx = " + getDataHomeDirTxString() + "  backup_dir_tx = " + getBackupDirTxString();
	}
// 20021213 @ADD deguchi 新規メソッド追加 end


	private String cutString( String base, int max )
	{
// 20021213 @ADD deguchi 必要な処理がなかった為 start
		// nullの時に落ちていたので空文字を返すように修正する
		if( base == null ) return "";
// 20021213 @ADD deguchi 必要な処理がなかった為 end
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
