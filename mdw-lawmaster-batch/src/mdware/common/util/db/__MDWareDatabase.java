package mdware.common.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.kind.DBStringConvertDBTwo;
import jp.co.vinculumjapan.stc.util.db.kind.DBStringConvertOracle;
import jp.co.vinculumjapan.stc.util.db.kind.DBStringConvertSQLServer;

/**
 * RBSITE用ユーティリティー（ＤＢアクセスクラスStatementを利用）。 <BR>
 * 同じSQLを数回しか投げる予定がない場合は、出来ればこちらを利用してください。 <BR>
 * RbsitePreparedDatabaseを利用すると、同じSQLでパラメータのみが違うものを <BR>
 * キャッシュされているのですが、キャッシュから外れて遅くなる事が考えられます。 <BR>
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
 * RbsiteDatabase db = new RbsiteDatabase("sample_db");
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
 * 日時の変換関数が各社ＤＢによって違うためsetTimestampの利用が出来ない事があります。 <BR>
 * このクラスでは、現在オラクル、ＤＢ２、SQLSERVERのみを対象としサポートしています。 <BR>
 * その他、ＤＢについては下の回避策を参考にしてください。 <BR>
 * &lt;回避策： <BR>
 * &lt;条件としてオラクルで利用できるto_dateが今回利用出来るＤＢにも実装されているものとします。 &lt;日時のカラムをdate_valとします。
 * <BR>
 * &lt;date_valへ条件をセットする場合下のSQLをsetSqlへセットします。 <BR>
 * &lt;・select * from dummy_table where date_val = to_date( ?,
 * 'yyyymmddhh24miss' )<BR>
 * &lt;次に、setString( 1, "20060328165432" )としてSQLを実行します。 <BR>
 * 
 * @deprecated setNull( int pos ) が利用出来ない為隠します。
 * @author telema_yugen777
 */
class __MDWareDatabase extends AbstractMDWareDatabase
{
	private static final StcLog stcLog = StcLog.getInstance();

	private int quotCount = 0;

	private String[] params = null;

	private String sql = null;

	private Statement stmt = null;

	/**
	 * コンストラクタ。
	 * 
	 * @param dbName
	 *            database.propertiesのデータベース名
	 */
	public __MDWareDatabase( String dbName )
	{
		super( dbName );
	}

	/**
	 * 実行するSQLをセットする。 <BR>
	 * この時、条件によって変わる場所は後からパラメータとしてセットするので ? としておきます。 <BR>
	 * 
	 * @param sql
	 *            実行するSQL
	 * @exception SQLException
	 */
	public void setSql( String sql ) throws SQLException
	{
		this.sql = sql;
		params = null;
		quotCount = 0;
		for( int i = 0; i < sql.length(); i++ )
		{
			if( sql.charAt( i ) == '?' )
			{
				quotCount++;
			}
		}
		params = new String[quotCount];
	}

	/**
	 * セット済のパラメータを初期状態に戻します。
	 */
	public void clear() throws SQLException
	{
		params = null;
		params = new String[quotCount];
	}

	/**
	 * SQLとセットされたパラメータよりSQLを組み立てます。
	 * 
	 * @return 組み立てられたSQL
	 */
	private String assembleSql()
	{
		for( int i = 0; i < params.length; i++ )
		{
			if( params[i] == null )
			{
				throw new IllegalArgumentException( "セットされていない項目が存在します。" + 1 + "番目" );
			}
		}
		int pos = 0;
		StringBuffer logSb = new StringBuffer();
		logSb.append("[");
		logSb.append(sql);
		logSb.append("]");
		StringBuffer sb = new StringBuffer( sql );
		for( int i = 0; i < params.length; i++ )
		{
			logSb.append("[");
			logSb.append(params[i]);
			logSb.append("]");
			pos = sb.indexOf( "?", pos );
			sb.delete( pos, pos + 1 );
			sb.insert( pos, params[i] );
			pos += params[i].length();
		}
		stcLog.getLog().debug(logSb.toString());
		return sb.toString();
	}

	/**
	 * 挿入のSQLを実行する。
	 * 
	 * @throws SQLException
	 */
	public void execute() throws SQLException
	{
		if( stmt != null )
		{
			try
			{
				stmt.close();
			}
			catch( Exception e )
			{
			}
		}
		connect();
		try
		{
			stmt = conn.createStatement();
			stmt.execute( assembleSql() );
		}
		catch(SQLException sqle)
		{
			StringBuffer logSb = new StringBuffer("例外発生：");
			logSb.append("[");
			logSb.append(sql);
			logSb.append("]");
			for( int i = 0; i < params.length; i++ )
			{
				logSb.append("[");
				logSb.append(params[i]);
				logSb.append("]");
			}
			stcLog.getLog().fatal(logSb.toString(), sqle);
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
		if( stmt != null )
		{
			try
			{
				stmt.close();
			}
			catch( Exception e )
			{
			}
		}
		connect();
		try
		{
			stmt = conn.createStatement();
			return stmt.executeUpdate( assembleSql() );
		}
		catch(SQLException sqle)
		{
			StringBuffer logSb = new StringBuffer("例外発生：");
			logSb.append("[");
			logSb.append(sql);
			logSb.append("]");
			for( int i = 0; i < params.length; i++ )
			{
				logSb.append("[");
				logSb.append(params[i]);
				logSb.append("]");
			}
			stcLog.getLog().fatal(logSb.toString(), sqle);
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
		if( stmt != null )
		{
			try
			{
				stmt.close();
			}
			catch( Exception e )
			{
			}
		}
		connect();
		try
		{
			stmt = conn.createStatement();
			return stmt.executeQuery( assembleSql() );
		}
		catch(SQLException sqle)
		{
			StringBuffer logSb = new StringBuffer("例外発生：");
			logSb.append("[");
			logSb.append(sql);
			logSb.append("]");
			for( int i = 0; i < params.length; i++ )
			{
				logSb.append("[");
				logSb.append(params[i]);
				logSb.append("]");
			}
			stcLog.getLog().fatal(logSb.toString(), sqle);
			throw sqle;
		}
}

	/**
	 * SQLの?の位置に文字列をセットする
	 * 
	 * @param pos ?
	 *            の場所
	 * @param val ?
	 *            にセットする文字列
	 * @throws SQLException
	 */
	public void setString( int pos, String val ) throws SQLException
	{
		StringBuffer sb = new StringBuffer( "'" );
		params[pos - 1] = sb.append( MDWareDBUtility.sanitize( val ) ).append( "'" ).toString();
	}

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ?
	 *            の場所
	 * @param val ?
	 *            にセットする値
	 * @throws SQLException
	 */
	public void setInt( int pos, int val ) throws SQLException
	{
		params[pos - 1] = Integer.toString( val );
	}

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ?
	 *            の場所
	 * @param val ?
	 *            にセットする値
	 * @throws SQLException
	 */
	public void setLong( int pos, long val ) throws SQLException
	{
		params[pos - 1] = Long.toString( val );
	}

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ?
	 *            の場所
	 * @param val ?
	 *            にセットする値
	 * @throws SQLException
	 */
	public void setDouble( int pos, double val ) throws SQLException
	{
		params[pos - 1] = Double.toString( val );
	}

	/**
	 * SQLの?の位置に日時をセットする
	 * 
	 * @param pos ?
	 *            の場所
	 * @param val ?
	 *            にセットする日時
	 * @throws SQLException
	 */
	public void setTimestamp( int pos, Date val ) throws SQLException
	{
		String product = super.getDatabaseProductName();
		DBStringConvert dbConvert = DBStringConvert.getDBStringConvert( product );
		if( dbConvert instanceof DBStringConvertOracle )
		{
			params[pos - 1] = "to_date('" + DateChanger.toSeparatorTimeStamp( DateChanger.dateToString( val ) ) + "', 'yyyy/mm/dd hh24:mi:ss')";
		}
		else if( dbConvert instanceof DBStringConvertSQLServer )
		{
			params[pos - 1] = "convert(timestamp, '" + DateChanger.toSeparatorTimeStamp( DateChanger.dateToString( val ) ) + "')";
		}
		else if( dbConvert instanceof DBStringConvertDBTwo )
		{
			params[pos - 1] = "timestamp('" + DateChanger.dateToString( val ) + "')";
		}
		else
		{
			throw new UnsupportedOperationException( "このメソッドはRbsiteDatabaseでは利用出来ません。" );
		}
	}

	/**
	 * SQLの?の位置にNULLをセットする
	 * 
	 * @param pos ? の場所
	 * @param sqlType java.sql.Types
	 * @throws SQLException
	 * @deprecated
	 */
	public void setNull( int pos, int sqlType ) throws SQLException
	{
	}
}