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
public class EQuestionnaireAnswerDM extends DataModule
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
				sequence = Integer.parseInt(super.getNextSequence("------","e_questionnaire_answer"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public EQuestionnaireAnswerDM()
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
		EQuestionnaireAnswerBean bean = new EQuestionnaireAnswerBean();
		bean.setQuestionnaireNb( rest.getLong("questionnaire_nb") );
		bean.setSitumonNb( rest.getLong("situmon_nb") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setSentakusi1Kb( rest.getString("sentakusi_1_kb") );
		bean.setSentakusi2Kb( rest.getString("sentakusi_2_kb") );
		bean.setSentakusi3Kb( rest.getString("sentakusi_3_kb") );
		bean.setSentakusi4Kb( rest.getString("sentakusi_4_kb") );
		bean.setSentakusi5Kb( rest.getString("sentakusi_5_kb") );
		bean.setSentakusi6Kb( rest.getString("sentakusi_6_kb") );
		bean.setSentakusi7Kb( rest.getString("sentakusi_7_kb") );
		bean.setSentakusi8Kb( rest.getString("sentakusi_8_kb") );
		bean.setSentakusi9Kb( rest.getString("sentakusi_9_kb") );
		bean.setJiyukaitoTx( rest.getString("jiyukaito_tx") );
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
		sb.append("riyo_user_id,");
		sb.append("sentakusi_1_kb,");
		sb.append("sentakusi_2_kb,");
		sb.append("sentakusi_3_kb,");
		sb.append("sentakusi_4_kb,");
		sb.append("sentakusi_5_kb,");
		sb.append("sentakusi_6_kb,");
		sb.append("sentakusi_7_kb,");
		sb.append("sentakusi_8_kb,");
		sb.append("sentakusi_9_kb,");
		sb.append("jiyukaito_tx,");
		sb.append("insert_ts,");
		sb.append("update_ts");

		sb.append(" from e_questionnaire_answer ");
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
		if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id = '" + singleQuotesfilter((String)map.get("riyo_user_id")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_1_kb") != null && ((String)map.get("sentakusi_1_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_1_kb = '" + singleQuotesfilter((String)map.get("sentakusi_1_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_2_kb") != null && ((String)map.get("sentakusi_2_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_2_kb = '" + singleQuotesfilter((String)map.get("sentakusi_2_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_3_kb") != null && ((String)map.get("sentakusi_3_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_3_kb = '" + singleQuotesfilter((String)map.get("sentakusi_3_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_4_kb") != null && ((String)map.get("sentakusi_4_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_4_kb = '" + singleQuotesfilter((String)map.get("sentakusi_4_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_5_kb") != null && ((String)map.get("sentakusi_5_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_5_kb = '" + singleQuotesfilter((String)map.get("sentakusi_5_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_6_kb") != null && ((String)map.get("sentakusi_6_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_6_kb = '" + singleQuotesfilter((String)map.get("sentakusi_6_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_7_kb") != null && ((String)map.get("sentakusi_7_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_7_kb = '" + singleQuotesfilter((String)map.get("sentakusi_7_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_8_kb") != null && ((String)map.get("sentakusi_8_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_8_kb = '" + singleQuotesfilter((String)map.get("sentakusi_8_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("sentakusi_9_kb") != null && ((String)map.get("sentakusi_9_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sentakusi_9_kb = '" + singleQuotesfilter((String)map.get("sentakusi_9_kb")) + "'");
			whereStr = andStr;
		}
		if( map.get("jiyukaito_tx") != null && ((String)map.get("jiyukaito_tx")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jiyukaito_tx = '" + singleQuotesfilter((String)map.get("jiyukaito_tx")) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + singleQuotesfilter((String)map.get("insert_ts")) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + singleQuotesfilter((String)map.get("update_ts")) + "'");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("questionnaire_nb");
		sb.append(",");
		sb.append("situmon_nb");
		sb.append(",");
		sb.append("riyo_user_id");
		sb.append(",");
		sb.append("sentakusi_1_kb");
		sb.append(",");
		sb.append("sentakusi_2_kb");
		sb.append(",");
		sb.append("sentakusi_3_kb");
		sb.append(",");
		sb.append("sentakusi_4_kb");
		sb.append(",");
		sb.append("sentakusi_5_kb");
		sb.append(",");
		sb.append("sentakusi_6_kb");
		sb.append(",");
		sb.append("sentakusi_7_kb");
		sb.append(",");
		sb.append("sentakusi_8_kb");
		sb.append(",");
		sb.append("sentakusi_9_kb");
		sb.append(",");
		sb.append("jiyukaito_tx");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("update_ts");
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
		EQuestionnaireAnswerBean bean = (EQuestionnaireAnswerBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("e_questionnaire_answer (");
		sb.append(" questionnaire_nb");
		sb.append(",");
		sb.append(" situmon_nb");
		sb.append(",");
		sb.append(" riyo_user_id");
		sb.append(",");
		sb.append(" sentakusi_1_kb");
		sb.append(",");
		sb.append(" sentakusi_2_kb");
		sb.append(",");
		sb.append(" sentakusi_3_kb");
		sb.append(",");
		sb.append(" sentakusi_4_kb");
		sb.append(",");
		sb.append(" sentakusi_5_kb");
		sb.append(",");
		sb.append(" sentakusi_6_kb");
		sb.append(",");
		sb.append(" sentakusi_7_kb");
		sb.append(",");
		sb.append(" sentakusi_8_kb");
		sb.append(",");
		sb.append(" sentakusi_9_kb");
		sb.append(",");
		sb.append(" jiyukaito_tx");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getQuestionnaireNbString());
		sb.append(",");
		sb.append( bean.getSitumonNbString());
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getRiyoUserId()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi1Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi2Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi3Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi4Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi5Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi6Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi7Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi8Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi9Kb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getJiyukaitoTx()) + "'");
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
		EQuestionnaireAnswerBean bean = (EQuestionnaireAnswerBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("e_questionnaire_answer set ");
/*		sb.append(" questionnaire_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getQuestionnaireNb()) + "'");
		sb.append(",");
		sb.append(" situmon_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSitumonNb()) + "'");
		sb.append(",");*/
		sb.append(" riyo_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getRiyoUserId()) + "'");
		sb.append(",");
		sb.append(" sentakusi_1_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi1Kb()) + "'");
		sb.append(",");
		sb.append(" sentakusi_2_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi2Kb()) + "'");
		sb.append(",");
		sb.append(" sentakusi_3_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi3Kb()) + "'");
		sb.append(",");
		sb.append(" sentakusi_4_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi4Kb()) + "'");
		sb.append(",");
		sb.append(" sentakusi_5_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi5Kb()) + "'");
		sb.append(",");
		sb.append(" sentakusi_6_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi6Kb()) + "'");
		sb.append(",");
		sb.append(" sentakusi_7_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi7Kb()) + "'");
		sb.append(",");
		sb.append(" sentakusi_8_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi8Kb()) + "'");
		sb.append(",");
		sb.append(" sentakusi_9_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi9Kb()) + "'");
		sb.append(",");
		sb.append(" jiyukaito_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getJiyukaitoTx()) + "'");
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
		sb.append(" AND");
		sb.append(" riyo_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getRiyoUserId()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_1_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi1Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_2_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi2Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_3_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi3Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_4_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi4Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_5_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi5Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_6_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi6Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_7_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi7Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_8_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi8Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_9_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi9Kb()) + "'");
		sb.append(" AND");
		sb.append(" jiyukaito_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getJiyukaitoTx()) + "'");
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
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
		EQuestionnaireAnswerBean bean = (EQuestionnaireAnswerBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("e_questionnaire_answer where ");
		sb.append(" questionnaire_nb = ");
		sb.append( bean.getQuestionnaireNbString());
/*		sb.append(" AND");
		sb.append(" situmon_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSitumonNb()) + "'");
		sb.append(" AND");
		sb.append(" riyo_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getRiyoUserId()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_1_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi1Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_2_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi2Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_3_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi3Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_4_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi4Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_5_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi5Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_6_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi6Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_7_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi7Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_8_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi8Kb()) + "'");
		sb.append(" AND");
		sb.append(" sentakusi_9_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getSentakusi9Kb()) + "'");
		sb.append(" AND");
		sb.append(" jiyukaito_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getJiyukaitoTx()) + "'");
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");*/
		return sb.toString();
	}

}
