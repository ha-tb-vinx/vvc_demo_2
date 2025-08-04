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
public class RQuestionnaireHeaderBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int INSERT_USER_ID_MAX_LENGTH = 20;
	public static final int QUESTIONNAIRE_NA_MAX_LENGTH = 60;
	public static final int SETUMEI_TX_MAX_LENGTH = 256;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private long questionnaire_nb = 0;
	private String insert_user_id = "";
	private String questionnaire_na = "";
	private String setumei_tx = "";
	private long keiji_nb = 0;
	private String insert_ts = "";
	private String update_ts = "";

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

	public void setQuestionnaireNa(String questionnaire_na)
	{
		this.questionnaire_na = questionnaire_na;
	}
	public String getQuestionnaireNa()
	{
		return cutString(this.questionnaire_na,QUESTIONNAIRE_NA_MAX_LENGTH);
	}
	public String getQuestionnaireNaString()
	{
		return "'" + cutString(this.questionnaire_na,QUESTIONNAIRE_NA_MAX_LENGTH) + "'";
	}

	public void setSetumeiTx(String setumei_tx)
	{
		this.setumei_tx = setumei_tx;
	}
	public String getSetumeiTx()
	{
		return cutString(this.setumei_tx,SETUMEI_TX_MAX_LENGTH);
	}
	public String getSetumeiTxString()
	{
		return "'" + cutString(this.setumei_tx,SETUMEI_TX_MAX_LENGTH) + "'";
	}

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
		return "  questionnaire_nb = " + getQuestionnaireNbString()  + "  insert_user_id = " + getInsertUserIdString()  + "  questionnaire_na = " + getQuestionnaireNaString()  + "  setumei_tx = " + getSetumeiTxString()  + "  keiji_nb = " + getKeijiNbString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
	}
	private String cutString( String base, int max )
	{
		if( base == null ) return "";    // 20030124 @Add start A.Tashiro
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
