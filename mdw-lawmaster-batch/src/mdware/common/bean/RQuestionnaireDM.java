package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class RQuestionnaireDM extends DataModule
{
	private static int sequence = -1;
	private static Object o = new Object();
	/**
	 * 連番を使用しINSERTを行う時はこのメソッドを呼び出してください。
	 * @return 最大の連番＋１を返す。
	 */
	private synchronized int getNextSeq()
	{
		int retSeq = -1;
		synchronized(o)
		{
			if( sequence < 0 )
				sequence = Integer.parseInt(super.getNextSequence("------","r_questionnaire"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public RQuestionnaireDM()
	{
		super( "rbsite_ora");
	}
	/**
	 * 検索後にＢＥＡＮをインスタンス化する所。
	 * 検索した結果セットをＢＥＡＮとして持ち直す。
	 * DataModuleから呼び出され返したObjectをListに追加する。
	 * @param rest ResultSet
	 * @return Object インスタンス化されたＢＥＡＮ
	 */
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		RQuestionnaireBean bean = new RQuestionnaireBean();
		bean.setQuestionnaireNb( rest.getLong("questionnaire_nb") );
		bean.setSitumonNb( rest.getLong("situmon_nb") );
		bean.setKaitoKeisikiKb( rest.getString("kaito_keisiki_kb") );
		bean.setToiTx( rest.getString("toi_tx") );
		bean.setJiyukaitoranUmuKb( rest.getString("jiyukaitoran_umu_kb") );
		bean.setSonotaIkenTx( rest.getString("sonota_iken_tx") );
		bean.setSentakusi1Tx( rest.getString("sentakusi_1_tx") );
		bean.setSentakusi2Tx( rest.getString("sentakusi_2_tx") );
		bean.setSentakusi3Tx( rest.getString("sentakusi_3_tx") );
		bean.setSentakusi4Tx( rest.getString("sentakusi_4_tx") );
		bean.setSentakusi5Tx( rest.getString("sentakusi_5_tx") );
		bean.setSentakusi6Tx( rest.getString("sentakusi_6_tx") );
		bean.setSentakusi7Tx( rest.getString("sentakusi_7_tx") );
		bean.setSentakusi8Tx( rest.getString("sentakusi_8_tx") );
		bean.setSentakusi9Tx( rest.getString("sentakusi_9_tx") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		return bean;
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql( Map map )
	{
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append("questionnaire_nb,");
		sb.append("situmon_nb,");
		sb.append("kaito_keisiki_kb,");
		sb.append("toi_tx,");
		sb.append("jiyukaitoran_umu_kb,");
		sb.append("sonota_iken_tx,");
		sb.append("sentakusi_1_tx,");
		sb.append("sentakusi_2_tx,");
		sb.append("sentakusi_3_tx,");
		sb.append("sentakusi_4_tx,");
		sb.append("sentakusi_5_tx,");
		sb.append("sentakusi_6_tx,");
		sb.append("sentakusi_7_tx,");
		sb.append("sentakusi_8_tx,");
		sb.append("sentakusi_9_tx,");
		sb.append("insert_ts,");
		sb.append("update_ts");

		sb.append(" from r_questionnaire ");
		if( map.get("questionnaire_nb") != null && ((String)map.get("questionnaire_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("questionnaire_nb = " + singleQuotesfilter((String)map.get("questionnaire_nb")));
			whereStr = andStr;
		}
		if( map.get("situmon_nb") != null && ((String)map.get("situmon_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("situmon_nb = " + singleQuotesfilter((String)map.get("situmon_nb")));
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("questionnaire_nb");
		sb.append(",");
		sb.append("situmon_nb");
		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst )
	{
		RQuestionnaireBean bean = (RQuestionnaireBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_questionnaire (");
		sb.append(" questionnaire_nb");
		sb.append(",");
		sb.append(" situmon_nb");
		sb.append(",");
		sb.append(" kaito_keisiki_kb");
		sb.append(",");
		sb.append(" toi_tx");
		sb.append(",");
		sb.append(" jiyukaitoran_umu_kb");
		sb.append(",");
		sb.append(" sonota_iken_tx");
		sb.append(",");
		sb.append(" sentakusi_1_tx");
		sb.append(",");
		sb.append(" sentakusi_2_tx");
		sb.append(",");
		sb.append(" sentakusi_3_tx");
		sb.append(",");
		sb.append(" sentakusi_4_tx");
		sb.append(",");
		sb.append(" sentakusi_5_tx");
		sb.append(",");
		sb.append(" sentakusi_6_tx");
		sb.append(",");
		sb.append(" sentakusi_7_tx");
		sb.append(",");
		sb.append(" sentakusi_8_tx");
		sb.append(",");
		sb.append(" sentakusi_9_tx");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getQuestionnaireNbString());
		sb.append(",");
		sb.append( bean.getSitumonNbString());
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getKaitoKeisikiKb()) + "'");
		sb.append(",");
		//2004.09.03 okawa SQLSever→Oracle対応　start (アンケート確定機能にてInsertに失敗する)
		if(singleQuotesfilter(bean.getToiTx()) == null || singleQuotesfilter(bean.getToiTx()).length() < 1)
			sb.append( "' '");
		else
			sb.append( "'" + singleQuotesfilter(bean.getToiTx()) + "'");
		//2004.09.03 okawa SQLSever→Oracle対応  end
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getJiyukaitoranUmuKb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSonotaIkenTx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi1Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi2Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi3Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi4Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi5Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi6Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi7Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi8Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi9Tx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		RQuestionnaireBean bean = (RQuestionnaireBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_questionnaire set ");
/*		sb.append(" questionnaire_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getQuestionnaireNb()) + "'");
		sb.append(",");
		sb.append(" situmon_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSitumonNb()) + "'");
		sb.append(",");*/
		sb.append(" kaito_keisiki_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getKaitoKeisikiKb()) + "'");
		sb.append(",");
		sb.append(" toi_tx = ");		
		//2004.09.03 okawa SQLSever→Oracle対応　start (アンケート確定機能にてInsertに失敗する)
		if(singleQuotesfilter(bean.getToiTx()) == null || singleQuotesfilter(bean.getToiTx()).length() < 1)
			sb.append( "' '");
		else
			sb.append( "'" + singleQuotesfilter(bean.getToiTx()) + "'");
		//2004.09.03 okawa SQLSever→Oracle対応  end		
		sb.append(",");
		sb.append(" jiyukaitoran_umu_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getJiyukaitoranUmuKb()) + "'");
		sb.append(",");
		sb.append(" sonota_iken_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSonotaIkenTx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_1_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi1Tx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_2_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi2Tx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_3_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi3Tx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_4_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi4Tx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_5_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi5Tx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_6_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi6Tx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_7_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi7Tx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_8_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi8Tx()) + "'");
		sb.append(",");
		sb.append(" sentakusi_9_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi9Tx()) + "'");
		sb.append(",");
/*		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(",");*/
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
		sb.append(" where");
		sb.append(" questionnaire_nb = ");
		sb.append( bean.getQuestionnaireNbString());
		sb.append(" AND");
		sb.append(" situmon_nb = ");
		sb.append( bean.getSitumonNbString());
/*		sb.append(" AND");
		sb.append(" kaito_keisiki_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getKaitoKeisikiKb()) + "'");
		sb.append(" AND");
		sb.append(" toi_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getToiTx()) + "'");
		sb.append(" AND");
		sb.append(" jiyukaitoran_umu_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getJiyukaitoranUmuKb()) + "'");
		sb.append(" AND");
		sb.append(" sonota_iken_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSonotaIkenTx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_1_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi1Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_2_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi2Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_3_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi3Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_4_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi4Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_5_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi5Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_6_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi6Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_7_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi7Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_8_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi8Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_9_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi9Tx()) + "'");
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");*/
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		RQuestionnaireBean bean = (RQuestionnaireBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_questionnaire where ");
		sb.append(" questionnaire_nb = ");
		sb.append( bean.getQuestionnaireNbString());
/*		sb.append(" AND");
		sb.append(" situmon_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSitumonNb()) + "'");
		sb.append(" AND");
		sb.append(" kaito_keisiki_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getKaitoKeisikiKb()) + "'");
		sb.append(" AND");
		sb.append(" toi_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getToiTx()) + "'");
		sb.append(" AND");
		sb.append(" jiyukaitoran_umu_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getJiyukaitoranUmuKb()) + "'");
		sb.append(" AND");
		sb.append(" sonota_iken_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSonotaIkenTx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_1_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi1Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_2_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi2Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_3_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi3Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_4_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi4Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_5_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi5Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_6_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi6Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_7_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi7Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_8_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi8Tx()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_9_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi9Tx()) + "'");
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");*/
		return sb.toString();
	}

}
