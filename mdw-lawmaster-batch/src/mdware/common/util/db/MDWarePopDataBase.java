package mdware.common.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.vinculumjapan.stc.util.db.CollectConnections;
import jp.co.vinculumjapan.stc.util.db.DBInitialSettingBean;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.util.text.Unicode;

/**
 *	子画面用DataBase(子画面以外でも組み込みは可能)
 *　大量のデータを取得した時の遅延を少なくするために作成した。
 *　基本的にDataBaseクラスのコピペです。
 *	@author Nakazawa
 *	@version 1.0 2006/09/26
 */
public class MDWarePopDataBase extends DataBase//DBConnection
{
	
	/*20060706に行ったトランザクションログ無しテーブル対応について
	 *DB2は　ジャーナル（トランザクションログ）を記録するか否かをテーブル毎に設定可能です。
	 *通常はジャーナルを残す設定になっています
	 *ジャーナルを残さない設定にするとロールバック、コミット時に例外が発生します
	 *ただし処理時間の大幅な短縮がある為、一部のバッチ処理等で有益です
	 *第二引数を持つコンストラクタを追加しました　true でDataBaseクラスをインスタンス化すると
	 *commit rollback メソッドが呼ばれた際処理をスルーします。
	 */
	private Statement stmt = null;
	//protected ResultSet rest = null;
	private String databaseName = "";
	private String encoding = null;
	//20060706#トランザクションログ無しテーブル対応開始  
	//commitとrollbackを無効にするか否かを示すフラグ
	private boolean disableTransaction = false;
	//20060706#トランザクションログ無しテーブル対応終了

	/**
	 *	コンストラクタ
	 */
	public MDWarePopDataBase( String databaseName )
	{
		super( databaseName );
		this.databaseName = databaseName;
		DBInitialSettingBean settingBean = CollectConnections.getInstance().getDBSetting( databaseName );
		if( settingBean == null )
		{
			stcLog.getSysLog().fatal("指定されたDatabase名が存在しないか、web.xmlでＤＢを利用しない設定になっている事が考えられます。");
			throw new IllegalArgumentException("指定されたDatabase名が存在しないか、web.xmlでＤＢを利用しない設定になっている事が考えられます。"); 
		}
		this.encoding = settingBean.getEncoding();
	}
	//20060706#トランザクションログ無しテーブル対応開始
	public MDWarePopDataBase( String databaseName , boolean disableTransaction)
	{
		this(databaseName);
	
		this.setDisableTransaction(disableTransaction);

	}
	//20060706#トランザクションログ無しテーブル対応終了
	/**
	 *	実行
	 *	@param sql String
	 *	@exception java.sql.SQLException
	 */
	public void execute( String sql )
		throws SQLException
	{
		stcLog.getLog().debug(sql);
		createStatement();
//		20041116 mod yoshi M2様対応 設定ファイルでコード変換を行うように変更
//		DBExecute( Unicode.convert( sql, Unicode.ACCESS_DB_STRING, this.encoding ) );
//		DBExecute( Unicode.convert( sql ) );
		DBExecute( Unicode.convertToDatabase( sql, databaseName ) );
	}

	/**
	 *	更新
	 *	@param sql String
	 *	@exception java.sql.SQLException
	 */
	public int executeUpdate( String sql )
		throws SQLException
	{
		stcLog.getLog().debug(sql);
		createStatement();
//		20041116 mod yoshi M2様対応 設定ファイルでコード変換を行うように変更
//		return DBExecuteUpdate( Unicode.convert( sql, Unicode.ACCESS_DB_STRING, this.encoding ) );
//		return DBExecuteUpdate( Unicode.convert( sql ) );
		return DBExecuteUpdate( Unicode.convertToDatabase( sql, databaseName ) );
	}

	/**
	 *	検索
	 *	@param sql String
	 *	@exception java.sql.SQLException
	 */
	public ResultSet executeQuery( String sql )
		throws SQLException
	{
		stcLog.getLog().debug(sql);
		createStatement();
//		20041116 mod yoshi M2様対応 設定ファイルでコード変換を行うように変更
//		return DBExecuteQuery( Unicode.convert( sql, Unicode.ACCESS_DB_STRING, this.encoding ) );
//		return DBExecuteQuery( Unicode.convert( sql ) );
		return DBExecuteQuery( Unicode.convertToDatabase( sql, databaseName ) );
	}

	/**
	 *	更新、挿入削除などを反映する
	 */
	public void commit()
	{	
		//20060706#トランザクションログ無しテーブル対応開始
		if(isDisableTransaction()){
			stcLog.getSysLog().info("現在のセッションはcommitメソッドが無効になっています。");
			return;
		}
		//20060706#トランザクションログ無しテーブル対応終了
		if( conn == null )
		{
			try
			{
				connect( databaseName );
			}
			catch(Exception e)
			{
			}
		}
		super.commit();
	}

	/**
	 *	更新、挿入削除などを反映しない
	 */
	public void rollback()
	{
		//20060706#トランザクションログ無しテーブル対応終了
		if(isDisableTransaction()){
			stcLog.getSysLog().info("現在のセッションはrollbackメソッドが無効になっています。");
			return;
		}
		//20060706#トランザクションログ無しテーブル対応開始
		if( conn == null )
		{
			try
			{
				connect( databaseName );
			}
			catch(Exception e)
			{
			}
		}
		super.rollback();
	}

	/**
	 *	ＤＢを閉じる
	 */
	public void close()
	{
		try
		{
			rest.close();
		}
		catch(Exception e)
		{
		}
		rest = null;
		try
		{
			stmt.close();
		}
		catch(Exception e)
		{
		}
		stmt = null;
		super.close();
	}

	/**
	 *	挿入を行う
	 *	@param sql String insertを行うＳＱＬ
	 *	@exception java.sql.SQLException
	 */
	private void DBExecute( String sql )
		throws SQLException
	{
		try
		{
			stmt.execute( sql );
		}
		catch(SQLException sqlex)
		{
			String exstr = "";
			exstr += "ＳＱＬ例外です。\n";
			exstr += "使用したＳＱＬは\n↓\n" + sql + "\n↑\nです。";
			exstr += sqlex.toString();
			stcLog.getLog().error(exstr, sqlex);
//			例外の情報を文字列以外を付けてあげるようにする
//			throw new SQLException( exstr );
			SQLException sqle = new SQLException(exstr, sqlex.getSQLState(), sqlex.getErrorCode() );
			throw sqle;
		}
	}

	/**
	 *	更新削除処理
	 *	@param sql String 更新削除を行うＳＱＬ
	 *	@return int 更新削除を行った件数
	 *	@exception java.sql.SQLException
	 */
	private int DBExecuteUpdate( String sql )
		throws SQLException
	{
		try
		{
			return stmt.executeUpdate( sql );
		}
		catch(SQLException sqlex)
		{
			String exstr = "";
			exstr += "ＳＱＬ例外です。\n";
			exstr += "使用したＳＱＬは\n↓\n" + sql + "\n↑\nです。";
			exstr += sqlex.toString();
			stcLog.getLog().error(exstr, sqlex);
//			例外の情報を文字列以外を付けてあげるようにする
//			throw new SQLException( exstr );
			SQLException sqle = new SQLException(exstr, sqlex.getSQLState(), sqlex.getErrorCode() );
			throw sqle;
		}
	}

	/**
	 *	検索処理
	 *	@param sql String 検索を行うＳＱＬ
	 *	@return java.sql.ResultSet 検索を行った内容
	 *	@exception java.sql.SQLException
	 */
	private ResultSet DBExecuteQuery( String sql )
		throws SQLException
	{
		try
		{
			if( rest != null )
				rest.close();
			rest = null;
		}
		catch(Exception ex)
		{
		}

		try
		{
			return (rest = stmt.executeQuery( sql ));
		}
		catch(SQLException sqlex)
		{
			String exstr = "";
			exstr += "ＳＱＬ例外です。\n";
			exstr += "使用したＳＱＬは\n↓\n" + sql + "\n↑\nです。";
			exstr += sqlex.toString();
			stcLog.getLog().error(exstr, sqlex);
//			例外の情報を文字列以外を付けてあげるようにする
//			throw new SQLException( exstr );
			SQLException sqle = new SQLException(exstr, sqlex.getSQLState(), sqlex.getErrorCode() );
			throw sqle;
		}
	}

	/**
	 *	Statementを新しくする
	 *	@exception java.sql.SQLException
	 */
	private void createStatement()
		throws SQLException
	{
		try
		{
			if( conn == null )
			{
				connect( databaseName );
			}

			try
			{
				if( stmt != null )
					stmt.close();
				stmt = null;
			}
			catch( Exception ex )
			{
			}
			//createStatement()に引数を持たせることでカーソル操作を可能にする2006/10/01
			//stmt = conn.createStatement();
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
		}
		catch( SQLException sqlex )
		{
			String exstr = "";
			exstr += "createStatementで例外が発生しました。\n";
			exstr += sqlex.toString();
			stcLog.getLog().error(exstr, sqlex);
//			例外の情報を文字列以外を付けてあげるようにする
//			throw new SQLException( exstr );
			SQLException sqle = new SQLException(exstr, sqlex.getSQLState(), sqlex.getErrorCode() );
			throw sqle;
		}
	}

	/**
	 * ＤＢの作成した会社名を返す。
	 * @return
	 */
	public String getDatabaseProductName()
	{
		String retName = "";
		try
		{
			if( conn == null )
				connect( databaseName );

			retName = conn.getMetaData().getDatabaseProductName();
		}
		catch(SQLException sqle)
		{
			retName = "UNKNOWN";
		}

		return retName;
	}

	/**
	 * ＤＢ名を返す？。
	 * @return
	 */
	public String getDatabaseProductVersion()
	{
		String retName = "";
		try
		{
			if( conn == null )
				connect( databaseName );

			retName = conn.getMetaData().getDatabaseProductVersion();
		}
		catch(SQLException sqle)
		{
			retName = "UNKNOWN";
		}

		return retName;
	}



	/**
	 *	このクラスが未使用になった時に強制的にＣＬＯＳＥする。
	 *	主に、close()することを忘れたソースに対しての救済策であり、通常はソース中にclose()を必ず記述する。
	 */
	protected void finalize() throws Throwable
	{
		close();
	}
	//20060706#トランザクションログ無しテーブル対応開始
	public boolean isDisableTransaction() {
		return disableTransaction;
	}

	public void setDisableTransaction(boolean b) {
		try{
			if(conn==null){
				connect( databaseName );
			}
			super.conn.setAutoCommit(b); 
		}catch(SQLException e){
			throw new RuntimeException("データベースコネクションのautoCommit設定に失敗しました。→set"+b); 
		}
		disableTransaction = b;
		stcLog.getSysLog().info("データベースコネクションのautoCommit設定を行いました autocommit→"+b);
	}
	//20060706#トランザクションログ無しテーブル対応終了
}
