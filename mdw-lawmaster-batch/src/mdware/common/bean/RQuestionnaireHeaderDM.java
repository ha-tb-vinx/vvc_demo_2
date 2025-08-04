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
public class RQuestionnaireHeaderDM extends DataModule
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
				sequence = Integer.parseInt(super.getNextSequence("------","r_questionnaire_header"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public RQuestionnaireHeaderDM()
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
		RQuestionnaireHeaderBean bean = new RQuestionnaireHeaderBean();
		bean.setQuestionnaireNb( rest.getLong("questionnaire_nb") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setQuestionnaireNa( rest.getString("questionnaire_na") );
		bean.setSetumeiTx( rest.getString("setumei_tx") );
		bean.setKeijiNb( rest.getLong("keiji_nb") );
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
		sb.append("insert_user_id,");
		sb.append("questionnaire_na,");
		sb.append("setumei_tx,");
		sb.append("keiji_nb,");
		sb.append("insert_ts,");
		sb.append("update_ts");

		sb.append(" from r_questionnaire_header ");
		if( map.get("questionnaire_nb") != null && ((String)map.get("questionnaire_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("questionnaire_nb = " + singleQuotesfilter((String)map.get("questionnaire_nb")));
			whereStr = andStr;
		}
/*		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id = '" + (String)map.get("insert_user_id") + "'");
			whereStr = andStr;
		}
		if( map.get("questionnaire_na") != null && ((String)map.get("questionnaire_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("questionnaire_na = '" + (String)map.get("questionnaire_na") + "'");
			whereStr = andStr;
		}
		if( map.get("setumei_tx") != null && ((String)map.get("setumei_tx")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("setumei_tx = '" + (String)map.get("setumei_tx") + "'");
			whereStr = andStr;
		}*/
		if( map.get("keiji_nb") != null && ((String)map.get("keiji_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("keiji_nb = " + singleQuotesfilter((String)map.get("keiji_nb")));
			whereStr = andStr;
		}
/*		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + (String)map.get("insert_ts") + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + (String)map.get("update_ts") + "'");
			whereStr = andStr;
		}*/
		sb.append(" order by ");
		sb.append("questionnaire_nb");
		sb.append(",");
		sb.append("insert_user_id");
		sb.append(",");
		sb.append("questionnaire_na");
		sb.append(",");
		sb.append("setumei_tx");
		sb.append(",");
		sb.append("keiji_nb");
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
		RQuestionnaireHeaderBean bean = (RQuestionnaireHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_questionnaire_header (");
		sb.append(" questionnaire_nb");
		sb.append(",");
		sb.append(" insert_user_id");
		sb.append(",");
		sb.append(" questionnaire_na");
		sb.append(",");
		sb.append(" setumei_tx");
		sb.append(",");
		sb.append(" keiji_nb");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getQuestionnaireNbString());
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getInsertUserId()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getQuestionnaireNa()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getSetumeiTx()) + "'");
		sb.append(",");
		sb.append( bean.getKeijiNbString());
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
		RQuestionnaireHeaderBean bean = (RQuestionnaireHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_questionnaire_header set ");
/*		sb.append(" questionnaire_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getQuestionnaireNb()) + "'");
		sb.append(",");*/
		sb.append(" insert_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertUserId()) + "'");
		sb.append(",");
		sb.append(" questionnaire_na = ");
		sb.append( "'" + singleQuotesfilter(bean.getQuestionnaireNa()) + "'");
		sb.append(",");
		sb.append(" setumei_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSetumeiTx()) + "'");
		sb.append(",");
/*		sb.append(" keiji_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeijiNb()) + "'");
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(",");*/
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
		sb.append(" where");
		sb.append(" questionnaire_nb = ");
		sb.append( bean.getQuestionnaireNbString());
/*		sb.append(" AND");
		sb.append(" insert_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertUserId()) + "'");
		sb.append(" AND");
		sb.append(" questionnaire_na = ");
		sb.append( "'" + singleQuotesfilter(bean.getQuestionnaireNa()) + "'");
		sb.append(" AND");
		sb.append(" setumei_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSetumeiTx()) + "'");
		sb.append(" AND");
		sb.append(" keiji_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeijiNb()) + "'");
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
		RQuestionnaireHeaderBean bean = (RQuestionnaireHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_questionnaire_header where ");
		sb.append(" questionnaire_nb = ");
		sb.append( bean.getQuestionnaireNbString());
/*		sb.append(" AND");
		sb.append(" insert_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertUserId()) + "'");
		sb.append(" AND");
		sb.append(" questionnaire_na = ");
		sb.append( "'" + singleQuotesfilter(bean.getQuestionnaireNa()) + "'");
		sb.append(" AND");
		sb.append(" setumei_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getSetumeiTx()) + "'");
		sb.append(" AND");
		sb.append(" keiji_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeijiNb()) + "'");
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");*/
		return sb.toString();
	}

}
