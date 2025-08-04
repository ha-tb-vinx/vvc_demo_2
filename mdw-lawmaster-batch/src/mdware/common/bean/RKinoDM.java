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
public class RKinoDM extends DataModule
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
				sequence = Integer.parseInt(super.getNextSequence("------","R_KINO"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public RKinoDM()
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
		RKinoBean bean = new RKinoBean();
		bean.setKinoCd( rest.getString("kino_cd") );
		bean.setKinoNa( rest.getString("kino_na") );
		bean.setDataSyubetuCd( rest.getString("data_syubetu_cd") );
		bean.setDataSyubetuNa( rest.getString("data_syubetu_na") );
		bean.setHostUpDownKb( rest.getString("host_up_down_kb") );
		bean.setDataHojiKikanDy( rest.getLong("data_hoji_kikan_dy") );
// 20021216 @ADD yamanaka start
		bean.setIfFileHeadNa( rest.getString("if_file_head_na") );
// 20021216 @ADD yamanaka end
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
		sb.append("select * from R_KINO ");
		if( map.get("kino_cd") != null && ((String)map.get("kino_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kino_cd = '" + (String)map.get("kino_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("kino_na") != null && ((String)map.get("kino_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kino_na = '" + (String)map.get("kino_na") + "'");
			whereStr = andStr;
		}
		if( map.get("data_syubetu_cd") != null && ((String)map.get("data_syubetu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_syubetu_cd = '" + (String)map.get("data_syubetu_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("data_syubetu_na") != null && ((String)map.get("data_syubetu_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_syubetu_na = '" + (String)map.get("data_syubetu_na") + "'");
			whereStr = andStr;
		}
		if( map.get("host_up_down_kb") != null && ((String)map.get("host_up_down_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("host_up_down_kb = '" + (String)map.get("host_up_down_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("data_hoji_kikan_dy") != null && ((String)map.get("data_hoji_kikan_dy")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("data_hoji_kikan_dy = " + (String)map.get("data_hoji_kikan_dy"));
			whereStr = andStr;
		}
// 20021216 @ADD yamanaka start
		if( map.get("if_file_head_na") != null && ((String)map.get("if_file_head_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("if_file_head_na = '" + (String)map.get("if_file_head_na") + "'");
			whereStr = andStr;
		}
// 20021216 @ADD yamanaka end
		sb.append(" order by ");
		sb.append("kino_cd");
		sb.append(",");
		sb.append("kino_na");
		sb.append(",");
		sb.append("data_syubetu_cd");
		sb.append(",");
		sb.append("data_syubetu_na");
		sb.append(",");
		sb.append("host_up_down_kb");
		sb.append(",");
		sb.append("data_hoji_kikan_dy");
// 20021216 @ADD yamanaka start
		sb.append(",");
		sb.append("if_file_head_na");
// 20021216 @ADD yamanaka end
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
		return null;
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
		return null;
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
		return null;
	}

}
