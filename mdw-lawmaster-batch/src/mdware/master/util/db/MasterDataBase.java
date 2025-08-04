package mdware.master.util.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.co.vinculumjapan.stc.util.db.DataBase;


/**
 * <P>タイトル:  RBSite用拡張データベースクラス</P>
 * <P>説明: 複数ResultSetに対応させたデータベースクラス</P>
 * <p>著作権：Copyright (c) 2004</p>
 * <p>会社名：Vinculum Japan Corp.</p>
 * <PRE>
 *
 * </PRE>
 * @author takuwa
 * @version 1.00 2004/12/10 takuwa 新規作成
 * @version 1.01 2005/04/25 shimoyama マスタメンテプロジェクト用に変更
*/

public class MasterDataBase extends DataBase
{
	private class RsetAndStmtBean{
		ResultSet rset = null;
		Statement stmt = null;
		public RsetAndStmtBean(ResultSet rset,Statement stmt){
			this.rset = rset;
			this.stmt = stmt;
		}
		public void close(){
			try{this.rset.close();}catch(Exception e){}
			try{this.stmt.close();}catch(Exception e){}
		}
	}
	
	private String databaseName = null;

	/**
	 * Statement,ResultSetを保持するリスト
	 */
	private List RsList   = null;

	/**
	 * prepareStatementを保持するリスト
	 */
	private PreparedStatement ps = null;

	/**
	 *	コンストラクタ
	 */
	public MasterDataBase( String databaseName )
	{
		super(databaseName);
		this.databaseName = databaseName;
		this.RsList   = new ArrayList();
	}


	/**
	 *	検索(オーバーライド)
	 *	@param sql String
	 *	@exception java.sql.SQLException
	 */
	public ResultSet executeQuery( String sql ) throws SQLException {
		Statement stmt = createStatement();
		if( conn != null )
			stcLog.getLog().debug("Connection : " + conn.toString() + "/sql:" + sql);
		return DBExecuteQuery(stmt, sql );
	}

	/**
	 *	ＤＢを閉じる(オーバーライド)
	 */
	public void close() {
		if( conn != null ){

				stcLog.getLog().debug("Connection : " + conn.toString() + "/close");

				if(RsList != null){
					for(int i = RsList.size() -1; i >= 0; i--){
						try{
							RsetAndStmtBean rsetAndStmt = (RsetAndStmtBean)RsList.get(i);
							rsetAndStmt.close();
							rsetAndStmt = null;
						}finally{
							RsList.remove(i);
						}	
					}
				}
		}
		super.close();
	}

	/**
	 *	検索処理
	 *	@param sql String 検索を行うＳＱＬ
	 *	@return java.sql.ResultSet 検索を行った内容
	 *	@exception java.sql.SQLException
	 */
	private ResultSet DBExecuteQuery(Statement stmt, String sql ) throws SQLException	{
		ResultSet rset = null;
		try
		{
			rset = stmt.executeQuery( sql );
			RsList.add(new RsetAndStmtBean(rset,stmt));
			return rset;
		}
		catch(SQLException sqlex)
		{
			String exstr = "";
			exstr += "ＳＱＬ例外です。\n";

			if( conn != null )
				exstr += "Connection : " + conn.toString() + "\n";
			exstr += "SQLState : " + sqlex.getSQLState() + "\n";
			exstr += "ErrorCode : " + sqlex.getErrorCode() + "\n";

			exstr += "使用したＳＱＬは\n↓\n" + sql + "\n↑\nです。";
			exstr += sqlex.toString();
			stcLog.getLog().error(exstr, sqlex);
			SQLException sqle2 = new SQLException( exstr, sqlex.getSQLState(), sqlex.getErrorCode() );
			sqle2.setNextException(sqlex);
			throw sqle2;
		}
	}

	/**
	 *	Statementを新しくする(オーバーライド)
	 *	@exception java.sql.SQLException
	 */
	protected Statement createStatement() throws SQLException
	{
		Statement stmt = null;
		
		if( conn == null )
		{
			connect( databaseName );
		}

		// ResultSetがクローズされている場合はStatementをクローズして
		// メモリ資源を節約する
		// ※RecordSetやStatementはClose()されると参照がNULLになる
		for(int i = RsList.size() -1; i >= 0; i--){
			if(RsList.get(i) == null){
				RsList.remove(i);
			}else{
				RsetAndStmtBean rsetAndStmt = (RsetAndStmtBean)RsList.get(i);
				if(rsetAndStmt.rset == null || rsetAndStmt.stmt == null){
					rsetAndStmt.close();
					RsList.remove(i);
				}
			}
		}

		if(stmt == null){
			try
			{
				stmt = conn.createStatement();
			}
			catch( SQLException sqlex )
			{
				String exstr = "";
				exstr += "createStatementで例外が発生しました。\n";
	
				if( conn != null )
					exstr += "Connection : " + conn.toString() + "\n";
				exstr += "SQLState : " + sqlex.getSQLState() + "\n";
				exstr += "ErrorCode : " + sqlex.getErrorCode() + "\n";
	
				exstr += sqlex.toString();
				stcLog.getLog().error(exstr, sqlex);
				SQLException sqle2 = new SQLException( exstr, sqlex.getSQLState(), sqlex.getErrorCode() );
				throw sqle2;
			}
		}
		
		return stmt;
	}

	/**
	 *	レコードセットクローズ
	 *	@param rset クローズするレコードセット
	 *	@return boolean クローズしたかどうか
	 *	@exception java.sql.SQLException
	 */
	public boolean closeResultSet(ResultSet rset) throws SQLException	{
		for(int i = RsList.size() -1; i >= 0; i--){
			if(RsList.get(i) == null){
				RsList.remove(i);
			}else{
				RsetAndStmtBean rsetAndStmt = (RsetAndStmtBean)RsList.get(i);
				if(rsetAndStmt.rset != null){
					if (rsetAndStmt.rset.equals(rset)){
						rsetAndStmt.close();
						RsList.remove(i);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 *	レコードセットクローズ
	 *	@param rset クローズするレコードセット
	 *	@return boolean クローズしたかどうか
	 *	@exception java.sql.SQLException
	 */
	/**
	 *	検索処理
	 *	@param sql String 検索を行うＳＱＬ
	 *	@return java.sql.ResultSet 検索を行った内容
	 *	@exception java.sql.SQLException
	 */
	public PreparedStatement getPrepareStatement(String sql) throws SQLException	{
		try
		{
			if( conn == null )
			{
				connect( databaseName );
			}
			ps = conn.prepareStatement( sql );
		}
		catch( SQLException e ) {
			String errMessage = "";
			errMessage += "PreparedStatementで例外が発生しました。\n";
			errMessage += "SQLState : " + e.getSQLState() + "\r\n\n";
			errMessage += "ErrorCode : " + e.getErrorCode() + "\r\n\n";
			errMessage += e.toString();

			stcLog.getLog().error(errMessage, e);
			throw e;
		}
		
		return ps;
	}

	/**
	 *	このクラスが未使用になった時に強制的にＣＬＯＳＥする。(オーバーライド)
	 */
	protected void finalize() throws Throwable
	{
		this.close();
	}
}
