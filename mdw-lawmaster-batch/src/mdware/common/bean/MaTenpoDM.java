package mdware.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import jp.co.vinculumjapan.stc.util.db.DataModule;

/**
 * <p>タイトル: MaTenpoDM クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2002.09.09) Version 1.0.IST_CUSTOM.1
 * @version X.X (Create time: 2003/10/8 9:17:53)
 */
public class MaTenpoDM extends DataModule
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
				sequence = Integer.parseInt(super.getNextSequence("------","ma_tenpo"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public MaTenpoDM()
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
		MaTenpoBean bean = new MaTenpoBean();
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoNa( rest.getString("tenpo_na") );
		bean.setTenpoKa( rest.getString("tenpo_ka") );
		bean.setEigyoKb( rest.getString("eigyo_kb") );
		bean.setKaitenDt( rest.getString("kaiten_dt") );
		bean.setEdiKb( rest.getString("edi_kb") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
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
		sb.append("select * from ma_tenpo ");
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd = '" + (String)map.get("tenpo_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_na") != null && ((String)map.get("tenpo_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			sb.append("tenpo_na = '" + (String)map.get("tenpo_na") + "'");
                        sb.append("tenpo_na like '%" + (String)map.get("tenpo_na") + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_ka") != null && ((String)map.get("tenpo_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka = '" + (String)map.get("tenpo_ka") + "'");
			whereStr = andStr;
		}
		if( map.get("eigyo_kb") != null && ((String)map.get("eigyo_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyo_kb = '" + (String)map.get("eigyo_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("kaiten_dt") != null && ((String)map.get("kaiten_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kaiten_dt = '" + (String)map.get("kaiten_dt") + "'");
			whereStr = andStr;
		}
		if( map.get("edi_kb") != null && ((String)map.get("edi_kb")).trim().length() > 0 ||
                ( map.get("edi_kb2") != null && ((String)map.get("edi_kb2")).trim().length() > 0 ) )
		{
                    if( map.get("edi_kb") != null && ((String)map.get("edi_kb")).trim().length() > 0 )
                    {
                        sb.append(whereStr);
		        if( map.get("edi_kb2") != null && ((String)map.get("edi_kb2")).trim().length() > 0 )
                        {
                            sb.append(" (");
                        }
			sb.append("edi_kb = '" + (String)map.get("edi_kb") + "'");
			whereStr = andStr;
		    }
                    if( map.get("edi_kb2") != null && ((String)map.get("edi_kb2")).trim().length() > 0 )
                    {
                        if( map.get("edi_kb") != null && ((String)map.get("edi_kb")).trim().length() > 0 )
		        {
                            sb.append(" or ");
                        }else{
                            sb.append(whereStr);
                        }
                            sb.append("edi_kb = '" + (String)map.get("edi_kb2") + "'");
                            whereStr = andStr;
                        if( map.get("edi_kb") != null && ((String)map.get("edi_kb")).trim().length() > 0 )
		        {
                            sb.append(" )");
                        }
                    }
		}
                if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id = '" + (String)map.get("riyo_user_id") + "'");
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
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("tenpo_na");
		sb.append(",");
		sb.append("tenpo_ka");
		sb.append(",");
		sb.append("eigyo_kb");
		sb.append(",");
		sb.append("kaiten_dt");
		sb.append(",");
		sb.append("edi_kb");
		sb.append(",");
		sb.append("riyo_user_id");
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
		MaTenpoBean bean = (MaTenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("ma_tenpo (");
		sb.append(" tenpo_cd");
		sb.append(",");
		sb.append(" tenpo_na");
		sb.append(",");
		sb.append(" tenpo_ka");
		sb.append(",");
		sb.append(" eigyo_kb");
		sb.append(",");
		sb.append(" kaiten_dt");
		sb.append(",");
		sb.append(" edi_kb");
		sb.append(",");
		sb.append(" riyo_user_id");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getTenpoCdString());
		sb.append(",");
		sb.append( bean.getTenpoNaString());
		sb.append(",");
		sb.append( bean.getTenpoKaString());
		sb.append(",");
		sb.append( bean.getEigyoKbString());
		sb.append(",");
		sb.append( bean.getKaitenDtString());
		sb.append(",");
		sb.append( bean.getEdiKbString());
		sb.append(",");
		sb.append( bean.getRiyoUserIdString());
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
		MaTenpoBean bean = (MaTenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("ma_tenpo set ");
		sb.append(" tenpo_cd = ");
		sb.append( bean.getTenpoCdString());
		sb.append(",");
		sb.append(" tenpo_na = ");
		sb.append( bean.getTenpoNaString());
		sb.append(",");
		sb.append(" tenpo_ka = ");
		sb.append( bean.getTenpoKaString());
		sb.append(",");
		sb.append(" eigyo_kb = ");
		sb.append( bean.getEigyoKbString());
		sb.append(",");
		sb.append(" kaiten_dt = ");
		sb.append( bean.getKaitenDtString());
		sb.append(",");
		sb.append(" edi_kb = ");
		sb.append( bean.getEdiKbString());
		sb.append(",");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" tenpo_cd = ");
		sb.append( bean.getTenpoCdString());
/*		sb.append(" AND");
		sb.append(" tenpo_na = ");
		sb.append( bean.getTenpoNaString());
		sb.append(" AND");
		sb.append(" tenpo_ka = ");
		sb.append( bean.getTenpoKaString());
		sb.append(" AND");
		sb.append(" eigyo_kb = ");
		sb.append( bean.getEigyoKbString());
		sb.append(" AND");
		sb.append(" kaiten_dt = ");
		sb.append( bean.getKaitenDtString());
		sb.append(" AND");
		sb.append(" edi_kb = ");
		sb.append( bean.getEdiKbString());
		sb.append(" AND");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
*/System.out.println(sb.toString());
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
		MaTenpoBean bean = (MaTenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("ma_tenpo where ");
		sb.append(" tenpo_cd = ");
		sb.append( bean.getTenpoCdString());
		sb.append(" AND");
		sb.append(" tenpo_na = ");
		sb.append( bean.getTenpoNaString());
		sb.append(" AND");
		sb.append(" tenpo_ka = ");
		sb.append( bean.getTenpoKaString());
		sb.append(" AND");
		sb.append(" eigyo_kb = ");
		sb.append( bean.getEigyoKbString());
		sb.append(" AND");
		sb.append(" kaiten_dt = ");
		sb.append( bean.getKaitenDtString());
		sb.append(" AND");
		sb.append(" edi_kb = ");
		sb.append( bean.getEdiKbString());
		sb.append(" AND");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		return sb.toString();
	}

}
