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
public class RQuestionnaireBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KAITO_KEISIKI_KB_MAX_LENGTH = 1;
	public static final int TOI_TX_MAX_LENGTH = 256;
	public static final int JIYUKAITORAN_UMU_KB_MAX_LENGTH = 1;
	public static final int SONOTA_IKEN_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_1_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_2_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_3_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_4_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_5_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_6_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_7_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_8_TX_MAX_LENGTH = 256;
	public static final int SENTAKUSI_9_TX_MAX_LENGTH = 256;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private long questionnaire_nb = 0;
	private long situmon_nb = 0;
	private String kaito_keisiki_kb = "0";
	private String toi_tx = "";
	private String jiyukaitoran_umu_kb = "0";
	private String sonota_iken_tx = "";
	private String sentakusi_1_tx = "";
	private String sentakusi_2_tx = "";
	private String sentakusi_3_tx = "";
	private String sentakusi_4_tx = "";
	private String sentakusi_5_tx = "";
	private String sentakusi_6_tx = "";
	private String sentakusi_7_tx = "";
	private String sentakusi_8_tx = "";
	private String sentakusi_9_tx = "";
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

	public void setKaitoKeisikiKb(String kaito_keisiki_kb)
	{
		this.kaito_keisiki_kb = kaito_keisiki_kb;
	}
	public String getKaitoKeisikiKb()
	{
		return cutString(this.kaito_keisiki_kb,KAITO_KEISIKI_KB_MAX_LENGTH);
	}
	public String getKaitoKeisikiKbString()
	{
		return "'" + cutString(this.kaito_keisiki_kb,KAITO_KEISIKI_KB_MAX_LENGTH) + "'";
	}

	public void setToiTx(String toi_tx)
	{
		this.toi_tx = toi_tx;
	}
	public String getToiTx()
	{
		return cutString(this.toi_tx,TOI_TX_MAX_LENGTH);
	}
	public String getToiTxString()
	{
		return "'" + cutString(this.toi_tx,TOI_TX_MAX_LENGTH) + "'";
	}

	public void setJiyukaitoranUmuKb(String jiyukaitoran_umu_kb)
	{
		this.jiyukaitoran_umu_kb = jiyukaitoran_umu_kb;
	}
	public String getJiyukaitoranUmuKb()
	{
		return cutString(this.jiyukaitoran_umu_kb,JIYUKAITORAN_UMU_KB_MAX_LENGTH);
	}
	public String getJiyukaitoranUmuKbString()
	{
		return "'" + cutString(this.jiyukaitoran_umu_kb,JIYUKAITORAN_UMU_KB_MAX_LENGTH) + "'";
	}

	public void setSonotaIkenTx(String sonota_iken_tx)
	{
		this.sonota_iken_tx = sonota_iken_tx;
	}
	public String getSonotaIkenTx()
	{
		return cutString(this.sonota_iken_tx,SONOTA_IKEN_TX_MAX_LENGTH);
	}
	public String getSonotaIkenTxString()
	{
		return "'" + cutString(this.sonota_iken_tx,SONOTA_IKEN_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi1Tx(String sentakusi_1_tx)
	{
		this.sentakusi_1_tx = sentakusi_1_tx;
	}
	public String getSentakusi1Tx()
	{
		return cutString(this.sentakusi_1_tx,SENTAKUSI_1_TX_MAX_LENGTH);
	}
	public String getSentakusi1TxString()
	{
		return "'" + cutString(this.sentakusi_1_tx,SENTAKUSI_1_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi2Tx(String sentakusi_2_tx)
	{
		this.sentakusi_2_tx = sentakusi_2_tx;
	}
	public String getSentakusi2Tx()
	{
		return cutString(this.sentakusi_2_tx,SENTAKUSI_2_TX_MAX_LENGTH);
	}
	public String getSentakusi2TxString()
	{
		return "'" + cutString(this.sentakusi_2_tx,SENTAKUSI_2_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi3Tx(String sentakusi_3_tx)
	{
		this.sentakusi_3_tx = sentakusi_3_tx;
	}
	public String getSentakusi3Tx()
	{
		return cutString(this.sentakusi_3_tx,SENTAKUSI_3_TX_MAX_LENGTH);
	}
	public String getSentakusi3TxString()
	{
		return "'" + cutString(this.sentakusi_3_tx,SENTAKUSI_3_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi4Tx(String sentakusi_4_tx)
	{
		this.sentakusi_4_tx = sentakusi_4_tx;
	}
	public String getSentakusi4Tx()
	{
		return cutString(this.sentakusi_4_tx,SENTAKUSI_4_TX_MAX_LENGTH);
	}
	public String getSentakusi4TxString()
	{
		return "'" + cutString(this.sentakusi_4_tx,SENTAKUSI_4_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi5Tx(String sentakusi_5_tx)
	{
		this.sentakusi_5_tx = sentakusi_5_tx;
	}
	public String getSentakusi5Tx()
	{
		return cutString(this.sentakusi_5_tx,SENTAKUSI_5_TX_MAX_LENGTH);
	}
	public String getSentakusi5TxString()
	{
		return "'" + cutString(this.sentakusi_5_tx,SENTAKUSI_5_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi6Tx(String sentakusi_6_tx)
	{
		this.sentakusi_6_tx = sentakusi_6_tx;
	}
	public String getSentakusi6Tx()
	{
		return cutString(this.sentakusi_6_tx,SENTAKUSI_6_TX_MAX_LENGTH);
	}
	public String getSentakusi6TxString()
	{
		return "'" + cutString(this.sentakusi_6_tx,SENTAKUSI_6_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi7Tx(String sentakusi_7_tx)
	{
		this.sentakusi_7_tx = sentakusi_7_tx;
	}
	public String getSentakusi7Tx()
	{
		return cutString(this.sentakusi_7_tx,SENTAKUSI_7_TX_MAX_LENGTH);
	}
	public String getSentakusi7TxString()
	{
		return "'" + cutString(this.sentakusi_7_tx,SENTAKUSI_7_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi8Tx(String sentakusi_8_tx)
	{
		this.sentakusi_8_tx = sentakusi_8_tx;
	}
	public String getSentakusi8Tx()
	{
		return cutString(this.sentakusi_8_tx,SENTAKUSI_8_TX_MAX_LENGTH);
	}
	public String getSentakusi8TxString()
	{
		return "'" + cutString(this.sentakusi_8_tx,SENTAKUSI_8_TX_MAX_LENGTH) + "'";
	}

	public void setSentakusi9Tx(String sentakusi_9_tx)
	{
		this.sentakusi_9_tx = sentakusi_9_tx;
	}
	public String getSentakusi9Tx()
	{
		return cutString(this.sentakusi_9_tx,SENTAKUSI_9_TX_MAX_LENGTH);
	}
	public String getSentakusi9TxString()
	{
		return "'" + cutString(this.sentakusi_9_tx,SENTAKUSI_9_TX_MAX_LENGTH) + "'";
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
		return "  questionnaire_nb = " + getQuestionnaireNbString()  + "  situmon_nb = " + getSitumonNbString()  + "  kaito_keisiki_kb = " + getKaitoKeisikiKbString()  + "  toi_tx = " + getToiTxString()  + "  jiyukaitoran_umu_kb = " + getJiyukaitoranUmuKbString()  + "  sonota_iken_tx = " + getSonotaIkenTxString()  + "  sentakusi_1_tx = " + getSentakusi1TxString()  + "  sentakusi_2_tx = " + getSentakusi2TxString()  + "  sentakusi_3_tx = " + getSentakusi3TxString()  + "  sentakusi_4_tx = " + getSentakusi4TxString()  + "  sentakusi_5_tx = " + getSentakusi5TxString()  + "  sentakusi_6_tx = " + getSentakusi6TxString()  + "  sentakusi_7_tx = " + getSentakusi7TxString()  + "  sentakusi_8_tx = " + getSentakusi8TxString()  + "  sentakusi_9_tx = " + getSentakusi9TxString()  + "  insert_ts = " + getInsertTsString()  + "  update_ts = " + getUpdateTsString() ;
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
