package mdware.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import jp.co.vinculumjapan.stc.util.db.DataModule;

/**
 * <p>タイトル: RTokaikyuDM クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2002.09.09) Version 1.0.IST_CUSTOM.1
 * @version X.X (Create time: 2003/10/8 11:42:25)
 */
public class RTokaikyuDM extends DataModule
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
				sequence = Integer.parseInt(super.getNextSequence("------","r_tokaikyu"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public RTokaikyuDM()
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
		RTokaikyuBean bean = new RTokaikyuBean();
		bean.setTokaikyuCd( rest.getString("tokaikyu_cd") );
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
		bean.setTokaikyuNa( rest.getString("tokaikyu_na") );
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
		sb.append("select * from r_tokaikyu ");
		if( map.get("tokaikyu_cd") != null && ((String)map.get("tokaikyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd = '" + (String)map.get("tokaikyu_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na") != null && ((String)map.get("tokaikyu_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na = '" + (String)map.get("tokaikyu_na") + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
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
		}
		sb.append(" order by ");
		sb.append("tokaikyu_cd");
		sb.append(",");
		sb.append("torihikisaki_cd");
		sb.append(",");
		sb.append("tokaikyu_na");
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
		RTokaikyuBean bean = (RTokaikyuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_tokaikyu (");
		sb.append(" tokaikyu_cd");
		sb.append(",");
		sb.append(" torihikisaki_cd");
		sb.append(",");
		sb.append(" tokaikyu_na");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getTokaikyuCdString());
		sb.append(",");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(",");
		sb.append( bean.getTokaikyuNaString());
		sb.append(",");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append( bean.getUpdateTsString());
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	//  検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		RTokaikyuBean bean = (RTokaikyuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_tokaikyu set ");
		sb.append(" tokaikyu_cd = ");
		sb.append( bean.getTokaikyuCdString());
		sb.append(",");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(",");
		sb.append(" tokaikyu_na = ");
		sb.append( bean.getTokaikyuNaString());
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" tokaikyu_cd = ");
		sb.append( bean.getTokaikyuCdString());
		sb.append(" AND");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(" AND");
		sb.append(" tokaikyu_na = ");
		sb.append( bean.getTokaikyuNaString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	//  検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		RTokaikyuBean bean = (RTokaikyuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_tokaikyu where ");
		sb.append(" tokaikyu_cd = ");
		sb.append( bean.getTokaikyuCdString());
		sb.append(" AND");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(" AND");
		sb.append(" tokaikyu_na = ");
		sb.append( bean.getTokaikyuNaString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		return sb.toString();
	}

}
