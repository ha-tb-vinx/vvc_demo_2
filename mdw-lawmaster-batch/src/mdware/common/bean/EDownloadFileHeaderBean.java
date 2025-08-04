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
public class EDownloadFileHeaderBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int FILE_HEAD_NB_MAX_LENGTH = 20;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int SERVER_FILE_NA_MAX_LENGTH = 1000;
	public static final int CLIENT_FILE_NA_MAX_LENGTH = 1000;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int FILE_LENGTH_QT_MAX_LENGTH = 14;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
// 20030416 @ADD deguchi DB内フィールド型変更により start
	public static final int SYORI_JYOKYO_FG_MAX_LENGTH = 1;
// 20030416 @ADD deguchi DB内フィールド型変更により end

	private String file_head_nb = null;
	private long jca_control_nb = 0;
	private String syori_jyokyo_fg = null;
	private String riyo_user_id = null;
	private String server_file_na = null;
	private String client_file_na = null;
	private String insert_ts = null;
	private String file_length_qt = null;
	private String update_ts = null;

	public void setFileHeadNb(String file_head_nb)
	{
		this.file_head_nb = file_head_nb;
	}
	public String getFileHeadNb()
	{
		return cutString(this.file_head_nb,FILE_HEAD_NB_MAX_LENGTH);
	}
	public String getFileHeadNbString()
	{
		return "'" + cutString(this.file_head_nb,FILE_HEAD_NB_MAX_LENGTH) + "'";
	}

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

	public void setSyoriJyokyoFg(String syori_jyokyo_fg)
	{
		this.syori_jyokyo_fg = syori_jyokyo_fg;
	}
	public String getSyoriJyokyoFg()
	{
		return cutString(this.syori_jyokyo_fg,SYORI_JYOKYO_FG_MAX_LENGTH);
	}
	public String getSyoriJyokyoFgString()
	{
		return "'" + cutString(this.syori_jyokyo_fg,SYORI_JYOKYO_FG_MAX_LENGTH) + "'";
	}

	public void setRiyoUserId(String riyo_user_id)
	{
		this.riyo_user_id = riyo_user_id;
	}
	public String getRiyoUserId()
	{
		return cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH);
	}
	public String getRiyoUserIdString()
	{
		return "'" + cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
	}

	public void setServerFileNa(String server_file_na)
	{
		this.server_file_na = server_file_na;
	}
	public String getServerFileNa()
	{
		return cutString(this.server_file_na,SERVER_FILE_NA_MAX_LENGTH);
	}
	public String getServerFileNaString()
	{
		return "'" + cutString(this.server_file_na,SERVER_FILE_NA_MAX_LENGTH) + "'";
	}

	public void setClientFileNa(String client_file_na)
	{
		this.client_file_na = client_file_na;
	}
	public String getClientFileNa()
	{
		return cutString(this.client_file_na,CLIENT_FILE_NA_MAX_LENGTH);
	}
	public String getClientFileNaString()
	{
		return "'" + cutString(this.client_file_na,CLIENT_FILE_NA_MAX_LENGTH) + "'";
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

	public void setFileLengthQt(String file_length_qt)
	{
		this.file_length_qt = file_length_qt;
	}
	public String getFileLengthQt()
	{
		return cutString(this.file_length_qt,FILE_LENGTH_QT_MAX_LENGTH);
	}
	public String getFileLengthQtString()
	{
		return "'" + cutString(this.file_length_qt,FILE_LENGTH_QT_MAX_LENGTH) + "'";
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
	public String toString()
	{
		return "  file_head_nb = " + getFileHeadNbString()  + "  jca_control_nb = " + getJcaControlNbString()  + "  syori_jyokyo_fg = " + getSyoriJyokyoFgString()  + "  riyo_user_id = " + getRiyoUserIdString()	+ "  server_file_na = " + getServerFileNaString()  + "  client_file_na = " + getClientFileNaString()  + "  insert_ts = " + getInsertTsString()  + "  file_length_qt = " + getFileLengthQtString()	+ "  update_ts = " + getUpdateTsString() ;
	}
	private String cutString( String base, int max )
	{
		String wk = "";
		if( base == null ) return "";    // 20030124 @Add start A.Tashiro
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
