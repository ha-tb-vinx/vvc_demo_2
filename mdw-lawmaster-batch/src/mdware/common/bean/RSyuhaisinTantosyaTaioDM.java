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
public class RSyuhaisinTantosyaTaioDM extends DataModule
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
				sequence = Integer.parseInt(super.getNextSequence("------","r_syuhaisin_tantosya_taio"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public RSyuhaisinTantosyaTaioDM()
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
		RSyuhaisinTantosyaTaioBean bean = new RSyuhaisinTantosyaTaioBean();
		bean.setTantosyaKb( rest.getString("tantosya_kb") );
		bean.setHaisinsakiCd( rest.getString("haisinsaki_cd") );
// 20030210 @UPD tashiro V2でテーブル変更のため start
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
// 20030210 @UPD end
		bean.setKouriCd( rest.getString("kouri_cd") );
// 20030210 @UPD tashiro V2でテーブル変更のため start
//		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
// 20030210 @UPD end
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
		sb.append("select * from r_syuhaisin_tantosya_taio ");
		if( map.get("tantosya_kb") != null && ((String)map.get("tantosya_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tantosya_kb = '" + (String)map.get("tantosya_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd") != null && ((String)map.get("haisinsaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd = '" + (String)map.get("haisinsaki_cd") + "'");
			whereStr = andStr;
		}
// 20030210 @UPD tashiro V2でテーブル変更のため start
		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
			whereStr = andStr;
		}
// 20030210 @UPD end
		if( map.get("kouri_cd") != null && ((String)map.get("kouri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kouri_cd = '" + (String)map.get("kouri_cd") + "'");
			whereStr = andStr;
		}
// 20030210 @UPD tashiro V2でテーブル変更のため start
//		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
//			whereStr = andStr;
//		}
// 20030210 @UPD end
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
		sb.append("tantosya_kb");
		sb.append(",");
		sb.append("torihikisaki_cd");
		if( map.get("reverse") != null )
		{
			sb.append(" desc ");
		}
		sb.append(",");
		sb.append("haisinsaki_cd");
		if( map.get("reverse") != null )
		{
			sb.append(" desc ");
		}
//		sb.append(",");
//		sb.append("kouri_cd");
//		sb.append(",");
//		sb.append("insert_ts");
//		sb.append(",");
//		sb.append("update_ts");
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
		RSyuhaisinTantosyaTaioBean bean = (RSyuhaisinTantosyaTaioBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_syuhaisin_tantosya_taio (");
		sb.append(" tantosya_kb");
		sb.append(",");
		sb.append(" haisinsaki_cd");
// 20030210 @UPD tashiro V2でテーブル変更のため start
		sb.append(",");
		sb.append(" torihikisaki_cd");
// 20030210 @UPD end
		sb.append(",");
		sb.append(" kouri_cd");
// 20030210 @UPD tashiro V2でテーブル変更のため start
//		sb.append(",");
//		sb.append(" torihikisaki_cd");
// 20030210 @UPD end
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getTantosyaKbString());
		sb.append(",");
		sb.append( bean.getHaisinsakiCdString());
// 20030210 @UPD tashiro V2でテーブル変更のため start
		sb.append(",");
		sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
		sb.append(",");
		sb.append( bean.getKouriCdString());
// 20030210 @UPD tashiro V2でテーブル変更のため start
//		sb.append(",");
//		sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
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
		RSyuhaisinTantosyaTaioBean bean = (RSyuhaisinTantosyaTaioBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_syuhaisin_tantosya_taio set ");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
		sb.append(",");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
// 20030210 @UPD tashiro V2でテーブル変更のため start
		sb.append(",");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
		sb.append(",");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
// 20030210 @UPD tashiro V2でテーブル変更のため start
//		sb.append(",");
//		sb.append(" torihikisaki_cd = ");
//		sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
		sb.append(" AND");
		sb.append(" haisinsaki_cd = ");
		sb.append( bean.getHaisinsakiCdString());
// 20030210 @UPD tashiro V2でテーブル変更のため start
		sb.append(" AND");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
// 20030210 @UPD end
		sb.append(" AND");
		sb.append(" kouri_cd = ");
		sb.append( bean.getKouriCdString());
// 20030210 @UPD tashiro V2でテーブル変更のため start
//		sb.append(" AND");
//		sb.append(" torihikisaki_cd = ");
//		sb.append( bean.getTorihikisakiCdString());
//		sb.append(" AND");
//		sb.append(" insert_ts = ");
//		sb.append( bean.getInsertTsString());
//		sb.append(" AND");
//		sb.append(" update_ts = ");
//		sb.append( bean.getUpdateTsString());
// 20030210 @UPD end
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
		RSyuhaisinTantosyaTaioBean bean = (RSyuhaisinTantosyaTaioBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_syuhaisin_tantosya_taio where ");
		sb.append(" tantosya_kb = ");
		sb.append( bean.getTantosyaKbString());
//		sb.append(" AND");
//		sb.append(" haisinsaki_cd = ");
//		sb.append( bean.getHaisinsakiCdString());
//		sb.append(" AND");
//		sb.append(" kouri_cd = ");
//		sb.append( bean.getKouriCdString());
//		sb.append(" AND");
//		sb.append(" torihikisaki_cd = ");
//		sb.append( bean.getTorihikisakiCdString());
//		sb.append(" AND");
//		sb.append(" insert_ts = ");
//		sb.append( bean.getInsertTsString());
//		sb.append(" AND");
//		sb.append(" update_ts = ");
//		sb.append( bean.getUpdateTsString());
		return sb.toString();
	}

}
