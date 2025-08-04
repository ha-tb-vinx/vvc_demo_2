package mdware.common.util.db;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * RBSITE用ユーティリティー（ＤＢアクセスクラスPreparedStatementを利用）。 <BR>
 * 同じSQLを大量に投げる場合は、出来ればこちらを利用してください。 <BR>
 * RbsiteDatabaseを利用すると、オプティマイザによるSQL解析の時間がかかり <BR>
 * バッチ系の処理は１０～１００倍時間がかかります。 <BR>
 * 利用方法はPreapreadStatementとほぼ同じです。 <BR>
 * １．インスタンス化する時は、database.propertiesに記述したデータベース名をセットします。 <BR>
 * ２．セットする項目が、? のＳＱＬをsetSqlに渡します。 <BR>
 * ３．setStringや、setLong・・・を利用し、? の位置にデータをセットします。 <BR>
 * ４．検索、更新、削除、挿入のSQLを実行します。 <BR>
 * ５．clearを呼び出しセットした内容をクリアします。 <BR>
 * ６．必要であれば、２～５を繰り返し行います。 <BR>
 * ７．コミット、またはロールバックを行います。 <BR>
 * ８．closeを行います。(必ず行ってください。) <BR>
 * <PRE>
 * RbsitePreparedDatabase db = new RbsitePreparedDatabase("sample_db");
 * try
 * {
 * 　　db.setSql( "insert into SAMPLE_TABLE value( ?, ?, ? )" );
 * 　　db.setString( 1, "DATA1" );
 * 　　db.setLong( 2, 123L );
 * 　　db.setDouble( 3, 456.789D );
 * 　　db.execute();
 * 　　db.commit();
 * 　　SQLException sqle = db.getExceptionByCommit();
 * 　　if( sqle != null )
 * 　　　　throw sqle;
 * }
 * catch(SQLException sqle)
 * {
 * 　　db.rollback();
 * }
 * finally
 * {
 * 　　db.close();
 * }
 * </PRE>
 * @author telema_yugen777
 */
public class MDWarePreparedDatabase extends AbstractMDWareDatabase
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String sql = null;

	private Map paramsMap = new HashMap();

	private PreparedStatement pstmt = null;

	/**
	 * コンストラクタ。
	 * 
	 * @param dbName database.propertiesのデータベース名
	 */
	public MDWarePreparedDatabase( String dbName )
	{
		super( dbName );
	}

	/**
	 * セット済のパラメータを初期状態に戻します。
	 */
	public void clear() throws SQLException
	{
		paramsMap.clear();
		pstmt.clearParameters();
	}

	/**
	 * 挿入のSQLを実行する。
	 * 
	 * @throws SQLException
	 */
	public void execute() throws SQLException
	{
		if( stcLog.getLog().isDebugEnabled() )
		{
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			sb.append(sql);
			sb.append("]");
			List sortList = new ArrayList(paramsMap.keySet());
			Collections.sort(sortList);
			Iterator ite = sortList.iterator();
			while( ite.hasNext() )
			{
				String key = (String)ite.next();
				String val = (String)paramsMap.get(key);
				sb.append("[");
				sb.append(key);
				sb.append(":");
				sb.append(val);
				sb.append("]");
			}
			stcLog.getLog().debug(sb.toString());
		}
		try
		{
			pstmt.execute();
		}
		catch(SQLException sqle)
		{
			StringBuffer sb = new StringBuffer("例外発生：");
			sb.append("[");
			sb.append(sql);
			sb.append("]");
			List sortList = new ArrayList(paramsMap.keySet());
			Collections.sort(sortList);
			Iterator ite = sortList.iterator();
			while( ite.hasNext() )
			{
				String key = (String)ite.next();
				String val = (String)paramsMap.get(key);
				sb.append("[");
				sb.append(key);
				sb.append(":");
				sb.append(val);
				sb.append("]");
			}
			stcLog.getLog().fatal(sb.toString(), sqle);
			throw sqle;
		}
	}

	/**
	 * 更新削除のSQLを実行する
	 * 
	 * @return 更新、削除の件数
	 * @throws SQLException
	 */
	public int executeUpdate() throws SQLException
	{
		if( stcLog.getLog().isDebugEnabled() )
		{
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			sb.append(sql);
			sb.append("]");
			List sortList = new ArrayList(paramsMap.keySet());
			Collections.sort(sortList);
			Iterator ite = sortList.iterator();
			while( ite.hasNext() )
			{
				String key = (String)ite.next();
				String val = (String)paramsMap.get(key);
				sb.append("[");
				sb.append(key);
				sb.append(":");
				sb.append(val);
				sb.append("]");
			}
			stcLog.getLog().debug(sb.toString());
		}
		try
		{
			return pstmt.executeUpdate();
		}
		catch(SQLException sqle)
		{
			StringBuffer sb = new StringBuffer("例外発生：");
			sb.append("[");
			sb.append(sql);
			sb.append("]");
			List sortList = new ArrayList(paramsMap.keySet());
			Collections.sort(sortList);
			Iterator ite = sortList.iterator();
			while( ite.hasNext() )
			{
				String key = (String)ite.next();
				String val = (String)paramsMap.get(key);
				sb.append("[");
				sb.append(key);
				sb.append(":");
				sb.append(val);
				sb.append("]");
			}
			stcLog.getLog().fatal(sb.toString(), sqle);
			throw sqle;
		}
	}

	/**
	 * 検索のSQLを実行する
	 * 
	 * @return ResultSet 結果セット
	 * @throws SQLException
	 */
	public ResultSet executeQuery() throws SQLException
	{
		if( stcLog.getLog().isDebugEnabled() )
		{
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			sb.append(sql);
			sb.append("]");
			List sortList = new ArrayList(paramsMap.keySet());
			Collections.sort(sortList);
			Iterator ite = sortList.iterator();
			while( ite.hasNext() )
			{
				String key = (String)ite.next();
				String val = (String)paramsMap.get(key);
				sb.append("[");
				sb.append(key);
				sb.append(":");
				sb.append(val);
				sb.append("]");
			}
			stcLog.getLog().debug(sb.toString());
		}
		try
		{
			return pstmt.executeQuery();
		}
		catch(SQLException sqle)
		{
			StringBuffer sb = new StringBuffer("例外発生：");
			sb.append("[");
			sb.append(sql);
			sb.append("]");
			List sortList = new ArrayList(paramsMap.keySet());
			Collections.sort(sortList);
			Iterator ite = sortList.iterator();
			while( ite.hasNext() )
			{
				String key = (String)ite.next();
				String val = (String)paramsMap.get(key);
				sb.append("[");
				sb.append(key);
				sb.append(":");
				sb.append(val);
				sb.append("]");
			}
			stcLog.getLog().fatal(sb.toString(), sqle);
			throw sqle;
		}
	}

	/**
	 * 実行するSQLをセットする。 <BR>
	 * この時、条件によって変わる場所は後からパラメータとしてセットするので ? としておきます。 <BR>
	 * 
	 * @param sql 実行するSQL
	 * @exception SQLException
	 */
	public void setSql( String sql ) throws SQLException
	{
		this.sql = sql;

		connect();
		if( pstmt != null )
		{
			try
			{
				pstmt.close();
			}
			catch( Exception e )
			{
			}
		}
		pstmt = conn.prepareStatement( sql );
	}

	/**
	 * SQLの?の位置に文字列をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする文字列
	 * @throws SQLException
	 */
	public void setString( int pos, String val ) throws SQLException
	{
		if( stcLog.getLog().isDebugEnabled() )
		{
			String key = "000" + pos;
			key = key.substring(key.length()-3);
			paramsMap.put(key, val);
		}
		pstmt.setString( pos, val );
	}

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする値
	 * @throws SQLException
	 */
	public void setInt( int pos, int val ) throws SQLException
	{
		if( stcLog.getLog().isDebugEnabled() )
		{
			String key = "000" + pos;
			key = key.substring(key.length()-3);
			paramsMap.put(key, Integer.toString(val) );
		}
		pstmt.setInt( pos, val );
	}

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする値
	 * @throws SQLException
	 */
	public void setLong( int pos, long val ) throws SQLException
	{
		if( stcLog.getLog().isDebugEnabled() )
		{
			String key = "000" + pos;
			key = key.substring(key.length()-3);
			paramsMap.put(key, Long.toString(val) );
		}
		pstmt.setLong( pos, val );
	}

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ?にセットする値
	 * @throws SQLException
	 */
	public void setDouble( int pos, double val ) throws SQLException
	{
		if( stcLog.getLog().isDebugEnabled() )
		{
			String key = "000" + pos;
			key = key.substring(key.length()-3);
			paramsMap.put(key, Double.toString(val) );
		}
		pstmt.setDouble( pos, val );
	}

	/**
	 * SQLの?の位置に日時をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする日時
	 * @throws SQLException
	 */
	public void setTimestamp( int pos, Date val ) throws SQLException
	{
		if( stcLog.getLog().isDebugEnabled() )
		{
			String key = "000" + pos;
			key = key.substring(key.length()-3);
			paramsMap.put(key, DateChanger.dateToString(val) );
		}
		Timestamp modVal = new Timestamp( val.getTime() );
		pstmt.setTimestamp( pos, modVal );
	}

	/**
	 * SQLの?の位置にNULLをセットする
	 * 
	 * @param pos ? の場所
	 * @param sqlType java.sql.Types
	 * @throws SQLException
	 */
	public void setNull( int pos, int sqlType ) throws SQLException
	{
		pstmt.setNull( pos, sqlType );
	}
	
	/**
	 * SQLの?の位置にBinaryStreamをセットする
	 * 
	 * @param pos ? の場所
	 * @throws SQLException
	 */
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException
	{
		pstmt.setBinaryStream( parameterIndex, x, length );
	}
	
	
}