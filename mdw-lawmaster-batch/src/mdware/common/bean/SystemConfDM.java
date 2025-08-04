package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import java.math.BigDecimal; //20030417 @ADD yamanaka

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

public class SystemConfDM extends DataModule {

	public SystemConfDM() {
		super( "rbsite_ora");
	}

	protected Object instanceBean(ResultSet rest) throws SQLException {
		SystemConfBean bean = new SystemConfBean();
		bean.setPwdYukoDy(rest.getInt("pwd_yuko_dy"));
		bean.setTmpPwdYukoDy(rest.getInt("tmp_pwd_yuko_dy"));
		bean.setSyslogDispDy(rest.getInt("syslog_disp_dy"));
		bean.setSyslogKeepDy(rest.getInt("syslog_keep_dy"));
		bean.setHachuDataDirTx(rest.getString("hachu_data_dir_tx"));
// 20021213 @ADD deguchi V2でDB変更により start
		bean.setBackupHojiKikanDy( rest.getInt("backup_hoji_kikan_dy") );
		bean.setShareDirCollectTx( rest.getString("share_dir_collect_tx") );
		bean.setShareDirDistributeTx( rest.getString("share_dir_distribute_tx") );
		bean.setShareDirMasterTx( rest.getString("share_dir_master_tx") );
		bean.setTempDirTx( rest.getString("temp_dir_tx") );
		bean.setDataHomeDirTx( rest.getString("data_home_dir_tx") );
		bean.setBackupDirTx( rest.getString("backup_dir_tx") );

//20031120 @ADD
		bean.setDownloadDirTx( rest.getString("download_dir_tx") );

// 20021213 @ADD deguchi V2でDB変更により end
// 20030417 @ADD yamanaka 消費税率追加 start
		bean.setSyohiZeiRt( rest.getBigDecimal("syohi_zei_rt") );
// 20030417 @ADD yamanaka 消費税率追加 end

// 20031011 @ADD start
		bean.setDenpyoMeisaiRow( rest.getLong("denpyo_meisai_row") );
		bean.setFreshHachuYoteiDataQt( rest.getLong("fresh_hachu_yotei_data_qt") );
		bean.setComHachuYoteiDataQt( rest.getLong("com_hachu_yotei_data_qt") );
		bean.setFreshHachuYoteiKeepQt( rest.getLong("fresh_hachu_yotei_keep_qt") );
		bean.setComHachuYoteiKeepQt( rest.getLong("com_hachu_yotei_keep_qt") );
// 20031011 @ADD end

// 20031219 @ADD kirihara 値引欠品率を追加 start
		bean.setNebikiKeppinRt( rest.getBigDecimal("nebiki_keppin_rt") );
// 20031219 @ADD kirihara 値引欠品率を追加 end

// 20040108 @ADD kurata 値引データ生成日を追加 start
		bean.setNebikiMakeDy( rest.getInt("nebiki_make_dy") );
// 20040108 @ADD kurata 値引データ生成日を追加 end

// 20050108 @ADD takata 相場商品最大発注数を追加 start
		bean.setSobaMaxHachuQt( rest.getInt("soba_max_hachu_qt") );
// 20050108 @ADD takata 相場商品最大発注数を追加 end

// 20050108 @ADD takata 相場商品リードタイム日数を追加 start
		bean.setSobaLeadtimeDt( rest.getInt("soba_leadtime_dt") );
// 20050108 @ADD takata 相場商品リードタイム日数を追加 end

		return bean;
	}

	public String getSelectSql(Map map) {
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append("pwd_yuko_dy,");
		sb.append("tmp_pwd_yuko_dy,");
		sb.append("syslog_disp_dy,");
		sb.append("syslog_keep_dy,");
		sb.append("hachu_data_dir_tx,");
// 20021213 @ADD deguchi V2でDB変更により start
		sb.append("backup_hoji_kikan_dy,");
		sb.append("share_dir_collect_tx,");
		sb.append("share_dir_distribute_tx,");
		sb.append("share_dir_master_tx,");
		sb.append("temp_dir_tx,");
		sb.append("data_home_dir_tx, ");
		sb.append("backup_dir_tx ");
// 20021213 @ADD deguchi V2でDB変更により end

//20031120 @ADD
		sb.append(",");
		sb.append("download_dir_tx ");


// 20030417 @ADD yamanaka 消費税率追加 start
		sb.append(",");
		sb.append("syohi_zei_rt ");
// 20030417 @ADD yamanaka 消費税率追加 end
		sb.append(",");
		sb.append("denpyo_meisai_row, ");
		sb.append("fresh_hachu_yotei_data_qt, ");
		sb.append("com_hachu_yotei_data_qt, ");
		sb.append("fresh_hachu_yotei_keep_qt, ");
		sb.append("com_hachu_yotei_keep_qt, ");
// 20031219 @ADD kirihara 値引欠品率を追加 start
		sb.append("nebiki_keppin_rt, ");
// 20031219 @ADD kirihara 値引欠品率を追加 end
// 20040108 @ADD kurata 値引データ生成日を追加 start
		sb.append("nebiki_make_dy, ");
// 20040108 @ADD kurata 値引データ生成日を追加 end

// 20050108 @ADD takata 相場商品最大発注数を追加 start
		sb.append("soba_max_hachu_qt, ");
// 20050108 @ADD takata 相場商品最大発注数を追加 end

// 20050108 @ADD takata 相場商品リードタイム日数を追加 start
		sb.append("soba_leadtime_dt ");
// 20050108 @ADD takata 相場商品リードタイム日数を追加 end

		sb.append(" from system_conf ");
		return sb.toString();
	}


	public String getInsertSql( Object beanMst )
	{
// 20021213 @DEL deguchi システムパラメータテーブルのinsertは行わない為 start
/*
		SystemConfBean bean = (SystemConfBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("system_conf (");
		sb.append(" pwd_yuko_dy");
		sb.append(",");
		sb.append(" syslog_disp_dy");
		sb.append(",");
		sb.append(" syslog_keep_dy");
		sb.append(",");
		sb.append(" hachu_data_dir_tx");
		sb.append(")Values(");
		sb.append( bean.getPwdYukoDyString());
		sb.append(",");
		sb.append( bean.getTmpPwdYukoDyString());
		sb.append(",");
		sb.append( bean.getSyslogDispDyString());
		sb.append(",");
		sb.append( bean.getSyslogKeepDyString());
		sb.append(",");
		sb.append( bean.getHachuDataDirTx());
		sb.append(")");
		return sb.toString();
*/
// 20021213 @DEL deguchi システムパラメータテーブルのinsertは行わない為 end
// 20021213 @ADD deguchi システムパラメータテーブルのinsertは行わない為 start
		// 当メソッドは使用しないためnullを返します。
		return null;
// 20021213 @ADD deguchi システムパラメータテーブルのinsertは行わない為 end
	}



	public String getUpdateSql( Object beanMst )
	{
// 20021213 @DEL deguchi システムパラメータテーブルのupdateは行わない為 start
/*
		SystemConfBean bean = (SystemConfBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("sytem_conf set ");
		sb.append(" pwd_yuko_dy = ");
		sb.append( bean.getPwdYukoDyString());
		sb.append(",");
		sb.append(" tmp_pwd_yuko_dy = ");
		sb.append( bean.getTmpPwdYukoDyString());
		sb.append(",");
		sb.append(" syslog_disp_dy = ");
		sb.append( bean.getSyslogDispDyString());
		sb.append(",");
		sb.append(" syslog_keep_dy = ");
		sb.append( bean.getSyslogKeepDyString());
		sb.append(",");
		sb.append(" hachu_data_dir_tx = ");
		sb.append( bean.getHachuDataDirTx());
		return sb.toString();
*/
// 20021213 @DEL deguchi システムパラメータテーブルのupdateは行わない為 end
// 20021213 @ADD deguchi システムパラメータテーブルのupdateは行わない為 start
		// 当メソッドは使用しないためnullを返します。
		return null;
// 20021213 @ADD deguchi システムパラメータテーブルのupdateは行わない為 end
	}


	public String getDeleteSql( Object beanMst )
	{
// 20021213 @DEL deguchi システムパラメータテーブルのdeleteは行わない為 start
/*

		SystemConfBean bean = (SystemConfBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("system_conf");
		return sb.toString();
*/
// 20021213 @DEL deguchi システムパラメータテーブルのdeleteは行わない為 end
// 20021213 @ADD deguchi システムパラメータテーブルのdeleteは行わない為 start
		// 当メソッドは使用しないためnullを返します。
		return null;
// 20021213 @ADD deguchi システムパラメータテーブルのdeleteは行わない為 end
	}

}
