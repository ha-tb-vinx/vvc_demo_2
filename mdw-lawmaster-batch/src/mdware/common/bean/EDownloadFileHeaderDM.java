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
public class EDownloadFileHeaderDM extends DataModule
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
				sequence = Integer.parseInt(super.getNextSequence("------","e_download_file_header"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public EDownloadFileHeaderDM()
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
		EDownloadFileHeaderBean bean = new EDownloadFileHeaderBean();
		bean.setFileHeadNb( rest.getString("file_head_nb") );
		bean.setJcaControlNb( rest.getLong("jca_control_nb") );
		bean.setSyoriJyokyoFg( rest.getString("syori_jyokyo_fg") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setServerFileNa( rest.getString("server_file_na") );
		bean.setClientFileNa( rest.getString("client_file_na") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setFileLengthQt( rest.getString("file_length_qt") );
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

		sb.append("file_head_nb,");
		sb.append("jca_control_nb,");
		sb.append("syori_jyokyo_fg,");
		sb.append("riyo_user_id,");
		sb.append("server_file_na,");
		sb.append("client_file_na,");
		sb.append("insert_ts,");
		sb.append("file_length_qt,");
		sb.append("update_ts");

		sb.append(" from e_download_file_header ");
		if( map.get("file_head_nb") != null && ((String)map.get("file_head_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb = '" + (String)map.get("file_head_nb") + "'");
			whereStr = andStr;
		}
		if( map.get("jca_control_nb") != null && ((String)map.get("jca_control_nb")).trim().length() > 0	)
		{
			sb.append(whereStr);
			sb.append("jca_control_nb = " + (String)map.get("jca_control_nb"));
			whereStr = andStr;
		}
		if( map.get("syori_jyokyo_fg") != null && ((String)map.get("syori_jyokyo_fg")).trim().length() > 0	)
		{
			sb.append(whereStr);
			sb.append("syori_jyokyo_fg = '" + (String)map.get("syori_jyokyo_fg") + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
// 20030226 @rep SingleQuote対応　A.Tashiro
//			sb.append("riyo_user_id = '" + (String)map.get("riyo_user_id") + "'");
			sb.append("riyo_user_id = '" + singleQuotesfilter((String)map.get("riyo_user_id")) + "'");
// 20030226 @rep end
			whereStr = andStr;
		}
		if( map.get("server_file_na") != null && ((String)map.get("server_file_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
// 20030226 @rep SingleQuote対応　A.Tashiro
//			sb.append("server_file_na = '" + (String)map.get("server_file_na") + "'");
			sb.append("server_file_na = '" + singleQuotesfilter((String)map.get("server_file_na")) + "'");
// 20030226 @rep end
			whereStr = andStr;
		}
		if( map.get("client_file_na") != null && ((String)map.get("client_file_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
// 20030226 @rep SingleQuote対応　A.Tashiro
//			sb.append("client_file_na = '" + (String)map.get("client_file_na") + "'");
			sb.append("client_file_na = '" + singleQuotesfilter((String)map.get("client_file_na")) + "'");
// 20030226 @rep end
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + (String)map.get("insert_ts") + "'");
			whereStr = andStr;
		}
		if( map.get("file_length_qt") != null && ((String)map.get("file_length_qt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_length_qt = '" + (String)map.get("file_length_qt") + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + (String)map.get("update_ts") + "'");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("file_head_nb");
		sb.append(",");
		sb.append("jca_control_nb");
		sb.append(",");
		sb.append("syori_jyokyo_fg");
		sb.append(",");
		sb.append("riyo_user_id");
		sb.append(",");
		sb.append("server_file_na");
		sb.append(",");
		sb.append("client_file_na");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("file_length_qt");
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
		EDownloadFileHeaderBean bean = (EDownloadFileHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("e_download_file_header (");
		sb.append(" file_head_nb");
		sb.append(",");
		sb.append(" jca_control_nb");
		sb.append(",");
		sb.append(" syori_jyokyo_fg");
		sb.append(",");
		sb.append(" server_file_na");
		sb.append(",");
		sb.append(" client_file_na");
		sb.append(",");
		sb.append(" file_length_qt");
		sb.append(",");
		sb.append(" riyo_user_id");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getFileHeadNbString());
		sb.append(",");
		sb.append( bean.getJcaControlNbString());
		sb.append(",");
		sb.append( bean.getSyoriJyokyoFgString());
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getServerFileNa()) + "'");
		sb.append(",");
		sb.append("'" +  singleQuotesfilter(bean.getClientFileNa()) + "'");
		sb.append(",");
		sb.append( bean.getFileLengthQtString());
		sb.append(",");
		sb.append( "'" + singleQuotesfilter(bean.getRiyoUserId()) + "'");
		sb.append(",");
//		sb.append( bean.getInsertTsString());
		sb.append( "TO_CHAR(sysdate, 'YYYYMMDDHH24MISS')");
		sb.append(",");
//		sb.append( bean.getUpdateTsString());
		sb.append( "TO_CHAR(sysdate, 'YYYYMMDDHH24MISS')");
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateSql( Object beanMst )
	{
		EDownloadFileHeaderBean bean = (EDownloadFileHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("e_download_file_header set ");
		sb.append(" jca_control_nb = ");
		sb.append( bean.getJcaControlNbString());
		sb.append(",");
		sb.append(" syori_jyokyo_fg = ");
		sb.append( bean.getSyoriJyokyoFgString());
		sb.append(",");
		sb.append(" riyo_user_id = ");
// 20030226 @rep SingleQuote対応　A.Tashiro
//		sb.append( bean.getRiyoUserIdString());
		sb.append( "'" + singleQuotesfilter(bean.getRiyoUserId()) + "'");
// 20030226 @rep end
		sb.append(",");
		sb.append(" server_file_na = ");
// 20030226 @rep SingleQuote対応　A.Tashiro
//		sb.append( bean.getServerFileNaString());
		sb.append( "'" + singleQuotesfilter(bean.getServerFileNa()) + "'");
// 20030226 @rep end
		sb.append(",");
		sb.append(" client_file_na = ");
// 20030226 @rep SingleQuote対応　A.Tashiro
//		sb.append( bean.getClientFileNaString());
		sb.append("'" +  singleQuotesfilter(bean.getClientFileNa()) + "'");
// 20030226 @rep end
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" file_length_qt = ");
		sb.append( bean.getFileLengthQtString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" file_head_nb = ");
		sb.append( bean.getFileHeadNbString());
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql( Object beanMst )
	{
		EDownloadFileHeaderBean bean = (EDownloadFileHeaderBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("e_download_file_header where ");
		sb.append(" file_head_nb = ");
		sb.append( bean.getFileHeadNbString());
		return sb.toString();
	}

}
