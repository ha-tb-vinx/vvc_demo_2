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
public class RKeijibanDM extends DataModule
{
	private static int sequence = -1;
	private static Object o = new Object();
	/**
	 * 連番を使用しINSERTを行う時はこのメソッドを呼び出してください。
	 * @return 最大の連番＋１を返す。
	 */
	public synchronized int getNextSeq()
	{
		int retSeq = -1;
		synchronized(o)
		{
			if( sequence < 0 )
				sequence = Integer.parseInt(super.getNextSequence("keiji_nb","r_keijiban"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	public RKeijibanDM()
	{
		super( "rbsite_ora");
	}
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		RKeijibanBean bean = new RKeijibanBean();
		bean.setKeijiNb( rest.getLong("keiji_nb") );
		bean.setTitleTx( rest.getString("title_tx") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setJuyodoKb( rest.getString("juyodo_kb") );
		bean.setKeisaiKaisiTs( rest.getString("keisai_kaisi_ts") );
		bean.setKeisaiSyuryoTs( rest.getString("keisai_syuryo_ts") );
		bean.setNaiyoTx( rest.getString("naiyo_tx") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setQuestionnaireFg( rest.getLong("questionnaire_fg"));
		return bean;
	}

	public String getSelectSql( Map map )
	{
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append("keiji_nb,");
		sb.append("title_tx,");
		sb.append("insert_user_id,");
		sb.append("juyodo_kb,");
		sb.append("keisai_kaisi_ts,");
		sb.append("keisai_syuryo_ts,");
		sb.append("naiyo_tx,");
		sb.append("insert_ts,");
		sb.append("update_ts,");
		sb.append("questionnaire_fg");

		sb.append(" from r_keijiban ");
		if( map.get("keiji_nb") != null && ((String)map.get("keiji_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("keiji_nb = " + singleQuotesfilter((String)map.get("keiji_nb")));
			whereStr = andStr;
		}
		return sb.toString();
	}

	public String getInsertSql( Object beanMst )
	{
		RKeijibanBean bean = (RKeijibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_keijiban (");
		sb.append(" keiji_nb");
		sb.append(",");
		sb.append(" title_tx");
		sb.append(",");
		sb.append(" insert_user_id");
		sb.append(",");
		sb.append(" juyodo_kb");
		sb.append(",");
		sb.append(" keisai_kaisi_ts");
		sb.append(",");
		sb.append(" keisai_syuryo_ts");
		sb.append(",");
		sb.append(" naiyo_tx");
		sb.append(",");
		sb.append(" questionnaire_fg");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getKeijiNbString());
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getTitleTx()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getInsertUserId()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getJuyodoKb()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getKeisaiKaisiTs()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getKeisaiSyuryoTs()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getNaiyoTx()) + "'");
		sb.append(",");
		sb.append( bean.getQuestionnaireFgString());
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
		sb.append(")");
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		RKeijibanBean bean = (RKeijibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_keijiban set ");
/*		sb.append(" keiji_nb = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeijiNb()) + "'");
		sb.append(",");*/
		sb.append(" title_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getTitleTx()) + "'");
		sb.append(",");
		sb.append(" insert_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertUserId()) + "'");
		sb.append(",");
		sb.append(" juyodo_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getJuyodoKb()) + "'");
		sb.append(",");
		sb.append(" keisai_kaisi_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeisaiKaisiTs()) + "'");
		sb.append(",");
		sb.append(" keisai_syuryo_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeisaiSyuryoTs()) + "'");
		sb.append(",");
		sb.append(" naiyo_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getNaiyoTx()) + "'");
		sb.append(",");
/*		sb.append(" questionnaire_fg = ");
		sb.append( "'" + singleQuotesfilter(bean.getQuestionnaireFg());
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(",");*/
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
		sb.append(" where");
		sb.append(" keiji_nb = ");
		sb.append( bean.getKeijiNbString());
/*		sb.append(" AND");
		sb.append(" title_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getTitleTx()) + "'");
		sb.append(" AND");
		sb.append(" insert_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertUserId()) + "'");
		sb.append(" AND");
		sb.append(" juyodo_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getJuyodoKb()) + "'");
		sb.append(" AND");
		sb.append(" keisai_kaisi_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeisaiKaisiTs()) + "'");
		sb.append(" AND");
		sb.append(" keisai_syuryo_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeisaiSyuryoTs()) + "'");
		sb.append(" AND");
		sb.append(" naiyo_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getNaiyoTx()) + "'");
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
*/
		return sb.toString();
	}

	//	検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		RKeijibanBean bean = (RKeijibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_keijiban where ");
		sb.append(" keiji_nb = ");
		sb.append( bean.getKeijiNbString());
/*		sb.append(" AND");
		sb.append(" title_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getTitleTx()) + "'");
		sb.append(" AND");
		sb.append(" insert_user_id = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertUserId()) + "'");
		sb.append(" AND");
		sb.append(" juyodo_kb = ");
		sb.append( "'" + singleQuotesfilter(bean.getJuyodoKb()) + "'");
		sb.append(" AND");
		sb.append(" keisai_kaisi_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeisaiKaisiTs()) + "'");
		sb.append(" AND");
		sb.append(" keisai_syuryo_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getKeisaiSyuryoTs()) + "'");
		sb.append(" AND");
		sb.append(" naiyo_tx = ");
		sb.append( "'" + singleQuotesfilter(bean.getNaiyoTx()) + "'");
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getInsertTs()) + "'");
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( "'" + singleQuotesfilter(bean.getUpdateTs()) + "'");
*/
		return sb.toString();
	}

}
