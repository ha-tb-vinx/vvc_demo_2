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
public class EQuestionnaireAnswerBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int SENTAKUSI_1_KB_MAX_LENGTH = 1;
	public static final int SENTAKUSI_2_KB_MAX_LENGTH = 1;
	public static final int SENTAKUSI_3_KB_MAX_LENGTH = 1;
	public static final int SENTAKUSI_4_KB_MAX_LENGTH = 1;
	public static final int SENTAKUSI_5_KB_MAX_LENGTH = 1;
	public static final int SENTAKUSI_6_KB_MAX_LENGTH = 1;
	public static final int SENTAKUSI_7_KB_MAX_LENGTH = 1;
	public static final int SENTAKUSI_8_KB_MAX_LENGTH = 1;
	public static final int SENTAKUSI_9_KB_MAX_LENGTH = 1;
	public static final int JIYUKAITO_TX_MAX_LENGTH = 256;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private long questionnaire_nb = 0;
	private long situmon_nb = 0;
	private String riyo_user_id = "";
	private String sentakusi_1_kb = "0";
	private String sentakusi_2_kb = "0";
	private String sentakusi_3_kb = "0";
	private String sentakusi_4_kb = "0";
	private String sentakusi_5_kb = "0";
	private String sentakusi_6_kb = "0";
	private String sentakusi_7_kb = "0";
	private String sentakusi_8_kb = "0";
	private String sentakusi_9_kb = "0";
	private String jiyukaito_tx = "";
	private String insert_ts = null;
	private String update_ts = null;

	public void setQuestionnaireNb(String questionnaire_nb)
	{
		this.questionnaire_nb = Long.parseLong(questionnaire_nb);
	}
	public void setQuestionnaireNb(long questionnaire_nb)
	{
		this.questionnaire_nb = questionnaire_nb;
	}
	public long getQuestionnaireNb()
	{
		return this.questionnaire_nb;
	}
	public String getQuestionnaireNbString()
	{
		return Long.toString(this.questionnaire_nb);
	}

	public void setSitumonNb(String situmon_nb)
	{
		this.situmon_nb = Long.parseLong(situmon_nb);
	}
	public void setSitumonNb(long situmon_nb)
	{
		this.situmon_nb = situmon_nb;
	}
	public long getSitumonNb()
	{
		return this.situmon_nb;
	}
	public String getSitumonNbString()
	{
		return Long.toString(this.situmon_nb);
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

	public void setSentakusi1Kb(String sentakusi_1_kb)
	{
		this.sentakusi_1_kb = sentakusi_1_kb;
	}
	public String getSentakusi1Kb()
	{
		return cutString(this.sentakusi_1_kb,SENTAKUSI_1_KB_MAX_LENGTH);
	}
	public String getSentakusi1KbString()
	{
		return "'" + cutString(this.sentakusi_1_kb,SENTAKUSI_1_KB_MAX_LENGTH) + "'";
	}

	public void setSentakusi2Kb(String sentakusi_2_kb)
	{
		this.sentakusi_2_kb = sentakusi_2_kb;
	}
	public String getSentakusi2Kb()
	{
		return cutString(this.sentakusi_2_kb,SENTAKUSI_2_KB_MAX_LENGTH);
	}
	public String getSentakusi2KbString()
	{
		return "'" + cutString(this.sentakusi_2_kb,SENTAKUSI_2_KB_MAX_LENGTH) + "'";
	}

	public void setSentakusi3Kb(String sentakusi_3_kb)
	{
		this.sentakusi_3_kb = sentakusi_3_kb;
	}
	public String getSentakusi3Kb()
	{
		return cutString(this.sentakusi_3_kb,SENTAKUSI_3_KB_MAX_LENGTH);
	}
	public String getSentakusi3KbString()
	{
		return "'" + cutString(this.sentakusi_3_kb,SENTAKUSI_3_KB_MAX_LENGTH) + "'";
	}

	public void setSentakusi4Kb(String sentakusi_4_kb)
	{
		this.sentakusi_4_kb = sentakusi_4_kb;
	}
	public String getSentakusi4Kb()
	{
		return cutString(this.sentakusi_4_kb,SENTAKUSI_4_KB_MAX_LENGTH);
	}
	public String getSentakusi4KbString()
	{
		return "'" + cutString(this.sentakusi_4_kb,SENTAKUSI_4_KB_MAX_LENGTH) + "'";
	}

	public void setSentakusi5Kb(String sentakusi_5_kb)
	{
		this.sentakusi_5_kb = sentakusi_5_kb;
	}
	public String getSentakusi5Kb()
	{
		return cutString(this.sentakusi_5_kb,SENTAKUSI_5_KB_MAX_LENGTH);
	}
	public String getSentakusi5KbString()
	{
		return "'" + cutString(this.sentakusi_5_kb,SENTAKUSI_5_KB_MAX_LENGTH) + "'";
	}

	public void setSentakusi6Kb(String sentakusi_6_kb)
	{
		this.sentakusi_6_kb = sentakusi_6_kb;
	}
	public String getSentakusi6Kb()
	{
		return cutString(this.sentakusi_6_kb,SENTAKUSI_6_KB_MAX_LENGTH);
	}
	public String getSentakusi6KbString()
	{
		return "'" + cutString(this.sentakusi_6_kb,SENTAKUSI_6_KB_MAX_LENGTH) + "'";
	}

	public void setSentakusi7Kb(String sentakusi_7_kb)
	{
		this.sentakusi_7_kb = sentakusi_7_kb;
	}
	public String getSentakusi7Kb()
	{
		return cutString(this.sentakusi_7_kb,SENTAKUSI_7_KB_MAX_LENGTH);
	}
	public String getSentakusi7KbString()
	{
		return "'" + cutString(this.sentakusi_7_kb,SENTAKUSI_7_KB_MAX_LENGTH) + "'";
	}

	public void setSentakusi8Kb(String sentakusi_8_kb)
	{
		this.sentakusi_8_kb = sentakusi_8_kb;
	}
	public String getSentakusi8Kb()
	{
		return cutString(this.sentakusi_8_kb,SENTAKUSI_8_KB_MAX_LENGTH);
	}
	public String getSentakusi8KbString()
	{
		return "'" + cutString(this.sentakusi_8_kb,SENTAKUSI_8_KB_MAX_LENGTH) + "'";
	}

	public void setSentakusi9Kb(String sentakusi_9_kb)
	{
		this.sentakusi_9_kb = sentakusi_9_kb;
	}
	public String getSentakusi9Kb()
	{
		return cutString(this.sentakusi_9_kb,SENTAKUSI_9_KB_MAX_LENGTH);
	}
	public String getSentakusi9KbString()
	{
		return "'" + cutString(this.sentakusi_9_kb,SENTAKUSI_9_KB_MAX_LENGTH) + "'";
	}

	public void setJiyukaitoTx(String jiyukaito_tx)
	{
		this.jiyukaito_tx = jiyukaito_tx;
	}
	public String getJiyukaitoTx()
	{
		return cutString(this.jiyukaito_tx,JIYUKAITO_TX_MAX_LENGTH);
	}
	public String getJiyukaitoTxString()
	{
		return "'" + cutString(this.jiyukaito_tx,JIYUKAITO_TX_MAX_LENGTH) + "'";
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
	public String toString()
	{
		return "  questionnaire_nb = " + getQuestionnaireNbString()  + "  situmon_nb = " + getSitumonNbString()  + "  riyo_user_id = " + getRiyoUserIdString()  + "  sentakusi_1_kb = " + getSentakusi1KbString()  + "  sentakusi_2_kb = " + getSentakusi2KbString()  + "  sentakusi_3_kb = " + getSentakusi3KbString()  + "  sentakusi_4_kb = " + getSentakusi4KbString()  + "  sentakusi_5_kb = " + getSentakusi5KbString()  + "  sentakusi_6_kb = " + getSentakusi6KbString()  + "  sentakusi_7_kb = " + getSentakusi7KbString()  + "  sentakusi_8_kb = " + getSentakusi8KbString()  + "  sentakusi_9_kb = " + getSentakusi9KbString()  + "  jiyukaito_tx = " + getJiyukaitoTxString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
	}
	private String cutString( String base, int max )
	{
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
