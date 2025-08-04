package mdware.common.util.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jp.co.vinculumjapan.stc.util.db.DataBase;

/**
 * このクラスは、AbstractRbsiteDatabaseとRbsitePreparedDatabaseに置き換えた。
 * こちらのソースを有効にする事を考えクラスをpublicからpackageに変更し残す事にした。
 * 
 * @author telema_yugen777
 */
class __MDWarePreparedDatabase extends DataBase
{
	private PreparedStatement pstmt = null;

	private String dbName = null;

	private SQLException sqlException = null;

	public __MDWarePreparedDatabase( String dbName )
	{
		super( dbName );
		this.dbName = dbName;
	}

	public SQLException getExceptionByCommit()
	{
		return sqlException;
	}

	public void commit()
	{
		try
		{
			conn.commit();
		}
		catch( SQLException sqle )
		{
			sqlException = sqle;
		}
	}

	public void rollback()
	{
		try
		{
			conn.rollback();
		}
		catch( SQLException sqle )
		{
			//ロールバック処理における例外は無効にする
		}
	}

	public void close()
	{
		try
		{
			pstmt.close();
		}
		catch( Exception e )
		{
		}
		pstmt = null;
		try
		{
			conn.rollback();
		}
		catch( Exception e )
		{
		}
		super.close();
	}

	public void clear() throws SQLException
	{
		pstmt.clearParameters();
	}

	public void execute() throws SQLException
	{
		pstmt.execute();
	}

	public int executeUpdate() throws SQLException
	{
		return pstmt.executeUpdate();
	}

	public ResultSet executeQuery() throws SQLException
	{
		return pstmt.executeQuery();
	}

	public void setSql( String sql ) throws SQLException
	{
		if( conn == null || conn.isClosed() )
		{
			super.connect( dbName );
		}
		if( pstmt != null )
		{
			pstmt.close();
		}
		pstmt = conn.prepareStatement( sql );
	}

	public void setString( int pos, String val ) throws SQLException
	{
		pstmt.setString( pos, val );
	}

	public void setInt( int pos, int val ) throws SQLException
	{
		pstmt.setInt( pos, val );
	}

	public void setLong( int pos, long val ) throws SQLException
	{
		pstmt.setLong( pos, val );
	}

	public void setDouble( int pos, double val ) throws SQLException
	{
		pstmt.setDouble( pos, val );
	}

	public void setTimestamp( int pos, Date val ) throws SQLException
	{
		Timestamp modVal = new Timestamp( val.getTime() );
		pstmt.setTimestamp( pos, modVal );
	}

	public void execute( String dmy ) throws SQLException
	{
		throw new UnsupportedOperationException( "このメソッドはRbsitePreparedDatabaseでは利用出来ません。" );
	}

	public int executeUpdate( String dmy ) throws SQLException
	{
		throw new UnsupportedOperationException( "このメソッドはRbsitePreparedDatabaseでは利用出来ません。" );
	}

	public ResultSet executeQuery( String dmy ) throws SQLException
	{
		throw new UnsupportedOperationException( "このメソッドはRbsitePreparedDatabaseでは利用出来ません。" );
	}
}