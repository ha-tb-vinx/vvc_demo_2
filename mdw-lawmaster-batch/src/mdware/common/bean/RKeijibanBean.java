package mdware.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.*;
/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class RKeijibanBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TITLE_TX_MAX_LENGTH = 256;
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;
	public static final int JUYODO_KB_MAX_LENGTH = 1;
	public static final int KEISAI_KAISI_TS_MAX_LENGTH = 14;
	public static final int KEISAI_SYURYO_TS_MAX_LENGTH = 14;
	public static final int NAIYO_TX_MAX_LENGTH = 1000;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private long keiji_nb = 0;
	private String title_tx = null;
	private String insert_user_id = null;
	private String juyodo_kb = null;
	private String keisai_kaisi_ts = null;
	private String keisai_syuryo_ts = null;
	private String naiyo_tx = null;
	private String insert_ts = null;
	private String update_ts = null;
	private long questionnaire_fg = 0;

	public void setKeijiNb(String keiji_nb)
	{
		this.keiji_nb = Long.parseLong(keiji_nb);
	}
	public void setKeijiNb(long keiji_nb)
	{
		this.keiji_nb = keiji_nb;
	}
	public long getKeijiNb()
	{
		return this.keiji_nb;
	}
	public String getKeijiNbString()
	{
		return Long.toString(this.keiji_nb);
	}

	public void setTitleTx(String title_tx)
	{
		this.title_tx = title_tx;
	}
	public String getTitleTx()
	{
		return cutString(this.title_tx,TITLE_TX_MAX_LENGTH);
	}
	public String getTitleTxString()
	{
		return "'" + cutString(this.title_tx,TITLE_TX_MAX_LENGTH) + "'";
	}

	public void setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
	}
	public String getInsertUserId()
	{
		return cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString()
	{
		return "'" + cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH) + "'";
	}

	public void setJuyodoKb(String juyodo_kb)
	{
		this.juyodo_kb = juyodo_kb;
	}
	public String getJuyodoKb()
	{
		return cutString(this.juyodo_kb,JUYODO_KB_MAX_LENGTH);
	}
	public String getJuyodoKbString()
	{
		return "'" + cutString(this.juyodo_kb,JUYODO_KB_MAX_LENGTH) + "'";
	}

	public void setKeisaiKaisiTs(String keisai_kaisi_ts)
	{
		this.keisai_kaisi_ts = keisai_kaisi_ts;
	}
	public String getKeisaiKaisiTs()
	{
		return cutString(this.keisai_kaisi_ts,KEISAI_KAISI_TS_MAX_LENGTH);
	}
	public String getKeisaiKaisiTsString()
	{
		return "'" + cutString(this.keisai_kaisi_ts,KEISAI_KAISI_TS_MAX_LENGTH) + "'";
	}

	public void setKeisaiSyuryoTs(String keisai_syuryo_ts)
	{
		this.keisai_syuryo_ts = keisai_syuryo_ts;
	}
	public String getKeisaiSyuryoTs()
	{
		return cutString(this.keisai_syuryo_ts,KEISAI_SYURYO_TS_MAX_LENGTH);
	}
	public String getKeisaiSyuryoTsString()
	{
		return "'" + cutString(this.keisai_syuryo_ts,KEISAI_SYURYO_TS_MAX_LENGTH) + "'";
	}

	public void setNaiyoTx(String naiyo_tx)
	{
		this.naiyo_tx = naiyo_tx;
	}
	public String getNaiyoTx()
	{
		return cutString(this.naiyo_tx,NAIYO_TX_MAX_LENGTH);
	}
	public String getNaiyoTxString()
	{
		return "'" + cutString(this.naiyo_tx,NAIYO_TX_MAX_LENGTH) + "'";
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

	public void setQuestionnaireFg(String questionnaire_fg)
	{
		this.questionnaire_fg = Long.parseLong(questionnaire_fg);
	}
	public void setQuestionnaireFg(long questionnaire_fg)
	{
		this.questionnaire_fg = questionnaire_fg;
	}
	public long getQuestionnaireFg()
	{
		return this.questionnaire_fg;
	}
	public String getQuestionnaireFgString()
	{
		return Long.toString(this.questionnaire_fg);
	}


	public String toString()
	{
		return "  keiji_nb = " + getKeijiNbString()  + "  title_tx = " + getTitleTxString()  + "  insert_user_id = " + getInsertUserIdString()  + "  juyodo_kb = " + getJuyodoKbString()  + "  keisai_kaisi_ts = " + getKeisaiKaisiTsString()  + "  keisai_syuryo_ts = " + getKeisaiSyuryoTsString()  + "  naiyo_tx = " + getNaiyoTxString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() + "  questionnaire_fg = " + getQuestionnaireFgString() ;
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
