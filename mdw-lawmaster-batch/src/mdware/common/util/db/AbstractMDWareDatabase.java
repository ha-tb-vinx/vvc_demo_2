package mdware.common.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import jp.co.vinculumjapan.stc.util.db.DataBase;

/**
 * RBSITE用ユーティリティー（ＤＢアクセスの抽象クラス）。 <BR>
 * STCLIBのDataBaseクラスをPreparedStatement的に利用するように拡張しています。 <BR>
 * ・RbsiteDatabaseは、Statementを利用します。 <BR>
 * そのため、オプティマイザによるＳＱＬの解析に時間がかかります。 <BR>
 * 時間は掛かっても問題ない時や一回切りのＳＱＬの時は、こちらを利用してください。 <BR>
 * ・RbsitePreparedDatabaseは、PreparedStatementを利用します。 <BR>
 * こちらは、同じSQLを大量に投げる時に利用します。 <BR>
 * 複数種類のSQLを投げると、オプティマイザが解析した結果を <BR>
 * 保持しているキャッシュを食いつぶす事も考えられますので <BR>
 * こちらを使う時は、同時に何種類のSQLをキャッシュするのかを <BR>
 * 考慮して利用してください。 <BR>
 * ※ＤＢコネクションはSTCLIB内で管理しているので外部には絶対出さないでください。 <BR>
 * 解放忘れが発生した時は、時間がたっても利用出来ないシステムになってしまいます。 <BR>
 * 
 * @author telema_yugen777
 */
public abstract class AbstractMDWareDatabase extends DataBase
{
	private SQLException sqlException = null;

	private String dbName = null;

	/**
	 * コンストラクタ。
	 * 
	 * @param dbName database.propertiesのデータベース名
	 */
	protected AbstractMDWareDatabase( String dbName )
	{
		super( dbName );
		this.dbName = dbName;
	}

	/**
	 * データベースのコネクションを取得します。
	 * 
	 * @throws SQLException
	 */
	protected void connect() throws SQLException
	{
		if( conn == null || conn.isClosed() )
		{
			conn = null;
			connect( dbName );
		}
	}

	/**
	 * コミットを行った時に発生した例外を取得出来ます。 <BR>
	 * コミット後に呼び出してください。 <BR>
	 * 例外は発生していない時は、nullが返ります。 <BR>
	 * 例外が発生していた時は、SQLExceptionが返ります。 <BR>
	 * 
	 * @return SQLException
	 */
	public SQLException getExceptionByCommit()
	{
		return sqlException;
	}

	/**
	 * コミットを行います。
	 */
	public void commit()
	{
		sqlException = null;
		try
		{
			conn.commit();
		}
		catch( SQLException sqle )
		{
			sqlException = sqle;
		}
	}

	/**
	 * ロールバックを行います。
	 */
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

	/**
	 * データベースへの接続をＤＢＣＰに返します。
	 */
	public void close()
	{
		try
		{
			conn.rollback();
		}
		catch( Exception e )
		{
		}
		super.close();
	}

	/**
	 * DataBase#executeを利用不可にした
	 * 
	 * @deprecated
	 */
	public void execute( String dmy ) throws SQLException
	{
		throw new UnsupportedOperationException( "このメソッドはRbsitePreparedDatabaseでは利用出来ません。" );
	}

	/**
	 * DataBase#executeUpdateを利用不可にした
	 * 
	 * @deprecated
	 */
	public int executeUpdate( String dmy ) throws SQLException
	{
		throw new UnsupportedOperationException( "このメソッドはRbsitePreparedDatabaseでは利用出来ません。" );
	}

	/**
	 * DataBase#executeQueryを利用不可にした
	 * 
	 * @deprecated
	 */
	public ResultSet executeQuery( String dmy ) throws SQLException
	{
		throw new UnsupportedOperationException( "このメソッドはRbsitePreparedDatabaseでは利用出来ません。" );
	}

	/**
	 * SQLへセットしたパラメータを初期状態に戻す
	 * 
	 * @throws SQLException
	 */
	abstract public void clear() throws SQLException;

	/**
	 * 挿入のSQLを実行する。
	 * 
	 * @throws SQLException
	 */
	abstract public void execute() throws SQLException;

	/**
	 * 更新削除のSQLを実行する
	 * 
	 * @return 更新、削除の件数
	 * @throws SQLException
	 */
	abstract public int executeUpdate() throws SQLException;

	/**
	 * 検索のSQLを実行する
	 * 
	 * @return ResultSet 結果セット
	 * @throws SQLException
	 */
	abstract public ResultSet executeQuery() throws SQLException;

	/**
	 * SQLをセットする
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	abstract public void setSql( String sql ) throws SQLException;

	/**
	 * SQLの?の位置に文字列をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする文字列
	 * @throws SQLException
	 */
	abstract public void setString( int pos, String val ) throws SQLException;

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする値
	 * @throws SQLException
	 */
	abstract public void setInt( int pos, int val ) throws SQLException;

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする値
	 * @throws SQLException
	 */
	abstract public void setLong( int pos, long val ) throws SQLException;

	/**
	 * SQLの?の位置に数値をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする値
	 * @throws SQLException
	 */
	abstract public void setDouble( int pos, double val ) throws SQLException;

	/**
	 * SQLの?の位置に日時をセットする
	 * 
	 * @param pos ? の場所
	 * @param val ? にセットする日時
	 * @throws SQLException
	 */
	abstract public void setTimestamp( int pos, Date val ) throws SQLException;

	/**
	 * SQLの？の位置にNULLをセットする
	 * 
	 * @param pos ? の場所
	 * @param sqlType java.sql.Types
	 * @throws SQLException
	 */
	abstract public void setNull( int pos, int sqlType ) throws SQLException;
}