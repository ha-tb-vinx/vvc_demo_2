package mdware.common.util.db;

import java.io.*;
import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import jp.co.vinculumjapan.stc.util.properties.StcLibProperty;
import mdware.common.batch.util.properties.BatchProperty;

/**
 *	DataBaseクラスを拡張し
 *	PreapredStatementやCallableStatementを実行できるようになります。<BR>
 *	<BR>
 *	設定ファイルを用意してください。<BR>
 *  設定ファイルの場所は&ltparam-name&gtproperty-dir&lt/param-name&gt/vitaminsです。<BR>
 *  　設定ファイルは２ファイル用意します。<BR>
 *	　設定ファイルは名前\t説明\tＳＱＬ等を１行とし複数行記述します。<BR>
 *	　名前は２ファイル内でユニークな名前にします。<BR>
 *	　もし、どちらかが必要ない時は空のファイルとして置きます。<BR>
 *	　・callablestatement.properties<BR>
 *  　　　login\tログイン用のＳＰを利用し１件取得する\t{ call login(?,?,?) }\n<BR>
 *  　　　userlist\tユーザの一覧を取得する\t{ ? = call test.userlist(?,?) }\n<BR>
 *	　・preparedstatement.properties<BR>
 *  　　　projectlist\tプロジェクトのリストを取得する\tselect project_cd, project_na from project where company_cd = ?\n<BR>
 *  　<B>注意：\tは区切り文字を示します。\nは改行を示します。BeanHolderでの利用は考慮されていません。</B><BR>
 *	<BR>
 *	利用<BR>
 *	　database.propertiesで指定したＤＢの名前を渡しインスタンス化します。<BR>
 *	　activeStatement()で今から利用するStatementの名前を渡し利用することを明示します。<BR>
 *	　setString()などを利用しデータをセットします。<BR>
 *	　execute()、executeUpdate()、executeQuery()で実行を行います。<BR>
 *	　このクラスではStatementを再利用します。<BR>
 *	　次の例を参考に利用してください。<BR>
 *	　activeStatement()→set()→execute()→set()→execute()→commit()→<BR>
 *	　clean()→<BR>
 *	　activeStatement()→set()→execute()→set()→execute()→commit()→<BR>
 *	　の順で行います。<BR>
 *	　clean()はStatementを破棄します。<BR>
 *	　必ずcommit()、rollback()を行ってください。<BR>
 *	　必ずclose()を行ってください。<BR>
 *	<BR>
 *	例：<BR>
 *	　PreparedStatementの時<BR>
 *	　　ＮＡＭＥ：getname<BR>
 *	　　ＳＱＬ：select user_na from ma_user where user_cd = ? and password = ?<BR>
 *	　　ソース：<BR>
 *　　　　　　db.activeStatement("getname");<BR>
 *　　　　　　db.setString(1,"test1_1_1");<BR>
 *　　　　　　db.setString(2,"test1_1_1");<BR>
 *　　　　　　ResultSet rest = db.executeQuery();<BR>
 *<BR>
 *	　CallableStatementの時（カーソルなし）<BR>
 *	　　ＮＡＭＥ：test1<BR>
 *	　　ＳＰ：{ call test1(?,?,?) }<BR>
 *	　　ソース：<BR>
 *	　　　　　db.activeStatement("test1");<BR>
 *　　　　　　db.registerOutParameter(1,java.sql.Types.VARCHAR);<BR>
 *　　　　　　db.registerOutParameter(2,java.sql.Types.VARCHAR);<BR>
 *　　　　　　db.registerOutParameter(3,java.sql.Types.VARCHAR);<BR>
 *　　　　　　db.setString(1,"test1_1_1");<BR>
 *　　　　　　db.setString(2,"test1_1_1");<BR>
 *　　　　　　db.execute();<BR>
 *　　　　　　String name = db.getString(3);<BR>
 *<BR>
 *	　CallableStatementの時（カーソルあり）[オラクルでの例]<BR>
 *	　　ＮＡＭＥ：test3<BR>
 *	　　ＳＰ：{ ? = call test.test3(?,?) }<BR>
 *	　　ソース：<BR>
 *	　　　　　db.activeStatement("test3");<BR>
 *　　　　　　db.registerOutParameter(1,oracle.jdbc.driver.OracleTypes.CURSOR);<BR>
 *　　　　　　db.registerOutParameter(2,java.sql.Types.VARCHAR);<BR>
 *　　　　　　db.registerOutParameter(3,java.sql.Types.VARCHAR);<BR>
 *　　　　　　db.setString(1,"test1_1_1");<BR>
 *　　　　　　db.setString(2,"test1_1_1");<BR>
 *　　　　　　ResultSet rest1 = db.executeQueryCall();<BR>
 *<BR>
 *	@author Yoshimoto
 *	@version 1.0
 */
public class StatementManager extends DataBase
{
	//ＤＢの名前
	private String dbname;
	//設定ファイルの名前
	private static final String CALLABLE_STATEMENT_FILE_NAME = "callablestatement.properties";
	private static final String PREAPRED_STATEMENT_FILE_NAME = "preparedstatement.properties";
	//設定ファイルのセパレータ(カンマやパイプは利用できない)
	private static final String SEPARATOR = "\t";
	//ＳＱＬを保持するＭＡＰ
	private static final Map callableStmtSQLMap = new HashMap();
	private static final Map preparedStmtSQLMap = new HashMap();
	//Statementのタイプ
	private static int CLEAR_STATEMENT = 0;
	private static int CALLABLE_STATEMENT = 1;
	private static int PREPARED_STATEMENT = 2;
	//このクラスが利用できるかを保持する
	private static boolean isUse = true;
	//利用するStatement
	private PreparedStatement activeStmt;
	//利用するStatementの名前
	private String activeStmtName = "";
	//利用するStatementのＳＱＬ
	private String activeSql = "";
	//利用するStatementのタイプ
	private int activeStmtType = 0;

	static
	{
		init();
	}

	/**
	 * 初期化
	 * 設定ファイルを読み込む
	 */
	private static void init()
	{
		String dir = StcLibProperty.propertiesDir;		
		if( dir == null ){		
			dir = BatchProperty.propertiesDir;
		}
		analysisProperties( new File( dir, CALLABLE_STATEMENT_FILE_NAME ), callableStmtSQLMap );
		analysisProperties( new File( dir, PREAPRED_STATEMENT_FILE_NAME ), preparedStmtSQLMap );
		Iterator ite = callableStmtSQLMap.keySet().iterator();
		while( isUse && ite.hasNext() )
		{
			String key = (String)ite.next();
			if( preparedStmtSQLMap.get(key) != null )
			{
				stcLog.getSysLog().fatal(CALLABLE_STATEMENT_FILE_NAME + "と" + PREAPRED_STATEMENT_FILE_NAME + "で同一の名前が存在します。StatementManagerは利用できません。");
				isUse = false;
			}
		}
	}

	/**
	 * 設定ファイルを解析しＭａｐにセットする
	 * @param file 設定ファイル
	 * @param sqlMap 保持するＭａｐ
	 */
	private static void analysisProperties( File file, Map sqlMap )
	{
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader( file );
			br = new BufferedReader( fr );
			String line = null;
			while( ( line = br.readLine() ) != null )
			{
				line = line.trim();
				if( line.startsWith("#") )
					continue;
				if( line.length() == 0 )
					continue;
				String key = null;
				String sql = null;
				StringTokenizer st = new StringTokenizer( line, SEPARATOR, true );
				int pos = 0;
				while( st.hasMoreTokens() )
				{
					String wk = st.nextToken().trim();
					if( SEPARATOR.equals(wk) || wk.length() == 0 )
					{
						pos++;
						continue;
					}
					switch( pos )
					{
						case 0:
							key = wk;
							break;
						case 1:
							break;
						case 2:
							sql = wk;
							break;
					}

				}
				if( sql == null || sql.length() == 0 )
					continue;
				if( sqlMap.put(key, sql) != null )
					throw new java.lang.IllegalArgumentException(file.toString() + "内で" + key + "はすでに使用されています。");
			}
		}
		catch(Exception e)
		{
			stcLog.getSysLog().fatal( file.toString() + "の解析中に例外が発生しました。StatementManagerは利用できません。", e);
			isUse = false;
		}
		finally
		{
			try{ br.close(); }catch(Exception e){}
			try{ fr.close(); }catch(Exception e){}
		}
	}

	/**
	 * 現在利用しているStatementの破棄
	 */
	public void clean()
	{
		if( conn != null )
		{
//			this.activeStmt = null;
			this.activeSql = "";
			this.activeStmtName = "";
			this.activeStmtType = this.CLEAR_STATEMENT;
		}
	}

	public void prepareClose()
	{
		if( conn != null )
		{
			try
			{
				this.activeStmt.close();
				this.activeStmt = null;
			} catch(SQLException e) {
				stcLog.getSysLog().fatal(e);
			}
		}		
		
	}

	/**
	 * コンストラクタ
	 * @param dbname database.propertiesで指定しているＤＢの名前
	 */
	public StatementManager( String dbname )
	{
		super( dbname );
		this.dbname = dbname;
	}

	/**
	 * Connectionの解放
	 */
	public void close()
	{
		clean();
		super.close();
	}

	/**
	 * Statementを利用できるようにする。
	 * @param key
	 * @throws SQLException
	 */
	public void activeStatement( String key )
		throws SQLException
	{
		if( key == null || key.trim().length() == 0 )
			throw new NullPointerException();

		if( !isUse )
			throw new IllegalStateException("StatementManagerはpropertiesファイル読み込み時に問題が存在したため利用できません。");

		key = key.trim();

		if( !this.callableStmtSQLMap.containsKey(key) && !this.preparedStmtSQLMap.containsKey(key) )
			throw new IllegalArgumentException(key + "で登録されたStatementは存在しません。");

		if( conn == null )
			connect( this.dbname );

		if( key.equals(this.activeStmtName) && this.activeStmt != null )
			return;

		if( (this.activeSql = (String)this.callableStmtSQLMap.get(key)) != null )
		{
			this.activeStmt = conn.prepareCall(this.activeSql);
			this.activeStmtName = key;
			this.activeStmtType = this.CALLABLE_STATEMENT;
		}
		else
		if( (this.activeSql = (String)this.preparedStmtSQLMap.get(key)) != null )
		{
			this.activeStmt = conn.prepareStatement(this.activeSql);
			this.activeStmtName = key;
			this.activeStmtType = this.PREPARED_STATEMENT;
		}
	}

	/**
	 * 挿入を行うためのStatement実行メソッド
	 * @return 成功or失敗
	 * @throws SQLException
	 */
	public boolean execute()
		throws SQLException
	{
		try
		{
			if( this.activeStmt == null )
				throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
			return this.activeStmt.execute();
		}
		catch(SQLException sqle)
		{
			stcLog.getLog().fatal("例外が発生しています。SQLは" + this.activeSql );
			throw sqle;
		}
	}

	/**
	 * 更新削除を行うためのStatement実行メソッド
	 * @return int 更新削除を行った行数
	 * @throws SQLException
	 */
	public int executeUpdate()
		throws SQLException
	{
		try
		{
			if( this.activeStmt == null )
				throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
			return this.activeStmt.executeUpdate();
		}
		catch(SQLException sqle)
		{
			stcLog.getLog().fatal("例外が発生しています。SQLは" + this.activeSql );
			throw sqle;
		}
	}

	/**
	 * 検索を行うためのStatement実行メソッド
	 * @return ResultSet 検索結果
	 * @throws SQLException
	 */
	public ResultSet executeQuery()
		throws SQLException
	{
		try
		{
			if( this.activeStmt == null )
				throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
			return this.activeStmt.executeQuery();
		}
		catch(SQLException sqle)
		{
			stcLog.getLog().fatal("例外が発生しています。SQLは" + this.activeSql );
			throw sqle;
		}
	}

	/**
	 * 検索を行うためのStatement実行メソッド
	 * @return ResultSet 検索結果
	 * @throws SQLException
	 */
	public ResultSet executeQueryCall()
		throws SQLException
	{
		try
		{
			if( this.activeStmt == null )
				throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
			this.activeStmt.execute();
			return (ResultSet)((CallableStatement)this.activeStmt).getObject(1);
		}
		catch(SQLException sqle)
		{
			stcLog.getLog().fatal("例外が発生しています。SQLは" + this.activeSql );
			throw sqle;
		}
	}

	/**
	 * Statementの指定した場所にStringをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setString( int pos, String val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		this.activeStmt.setString(pos, val);
	}

	/**
	 * Statementの指定した場所にlongをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setLong( int pos, long val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		this.activeStmt.setLong(pos, val);
	}

	/**
	 * Statementの指定した場所にintをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setInt( int pos, int val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		this.activeStmt.setInt(pos, val);
	}

	/**
	 * Statementの指定した場所にfloatをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setFloat( int pos, float val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		this.activeStmt.setFloat(pos, val);
	}

	/**
	 * Statementの指定した場所にdoubleをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setDouble( int pos, double val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		this.activeStmt.setDouble(pos, val);
	}

	/**
	 * Statementの指定した場所にDateをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setDate( int pos, java.util.Date val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		java.sql.Date sqlDate = new java.sql.Date( val.getTime() );
		this.activeStmt.setDate(pos, sqlDate);
	}

	/**
	 * Statementの指定した場所にTimeをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setTime( int pos, Time val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		this.activeStmt.setTime(pos, val);
	}

	/**
	 * Statementの指定した場所にTimeをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setTime( int pos, java.util.Date val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		java.sql.Time sqlTime = new java.sql.Time( val.getTime() );
		this.activeStmt.setTime(pos, sqlTime);
	}

	/**
	 * Statementの指定した場所にTimestampをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setTimestamp( int pos, Timestamp val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		this.activeStmt.setTimestamp(pos, val);
	}

	/**
	 * Statementの指定した場所にTimestampをセットする
	 * @param pos Statementの?の位置を示す。１～
	 * @param val Statementの?の位置にセットする内容
	 * @throws SQLException
	 */
	public void setTimestamp( int pos, java.util.Date val )
		throws SQLException
	{
		if( this.activeStmt == null )
			throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp( val.getTime() );
		this.activeStmt.setTimestamp(pos, sqlTimestamp);
	}

	/**
	 * 現在利用できるStatementの名前を返す
	 * @return 名前
	 */
	public String getActimeStatementName()
	{
		return this.activeStmtName;
	}

	/**
	 * 現在利用できるStatementのSqlを返す
	 * @return SQL
	 */
	public String getActiveStatementSql()
	{
		return this.activeSql;
	}

	/**
	 * CallableStatementを利用している時のみ引数の型を指定できます。
	 * @param pos 設定する場所。１～
	 * @param type SQLでのTYPEを指定する(java.sql.Types.??)
	 * @throws SQLException
	 */
	public void registerOutParameter(int pos, int type )
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementが利用可能になっていません。");
		((CallableStatement)this.activeStmt).registerOutParameter(pos, type);
	}

	/**
	 * CallableStatementを利用している時のみ引数の型を指定できます。
	 * @param pos 設定する場所。１～
	 * @param type SQLでのTYPEを指定する(java.sql.Types.??)
	 * @param len パラメータの長さを指定する
	 * @throws SQLException
	 */
	public void registerOutParameter(int pos, int type, int len )
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementが利用可能になっていません。");
		((CallableStatement)this.activeStmt).registerOutParameter(pos, type, len);
	}

	/**
	 * 指定の場所の内容をString型で取得する。
	 * CallablesStatementを利用しカーソルを使用していない時のみ利用可能
	 * @param pos 指定の場所
	 * @return 指定の場所の値
	 * @throws SQLException
	 */
	public String getString(int pos)
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		return ((CallableStatement)this.activeStmt).getString(pos);
	}

	/**
	 * 指定の場所の内容をdouble型で取得する。
	 * CallablesStatementを利用しカーソルを使用していない時のみ利用可能
	 * @param pos 指定の場所
	 * @return 指定の場所の値
	 * @throws SQLException
	 */
	public double getDouble(int pos)
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		return ((CallableStatement)this.activeStmt).getDouble(pos);
	}

	/**
	 * 指定の場所の内容をfloat型で取得する。
	 * CallablesStatementを利用しカーソルを使用していない時のみ利用可能
	 * @param pos 指定の場所
	 * @return 指定の場所の値
	 * @throws SQLException
	 */
	public float getFloat(int pos)
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		return ((CallableStatement)this.activeStmt).getFloat(pos);
	}

	/**
	 * 指定の場所の内容をlong型で取得する。
	 * CallablesStatementを利用しカーソルを使用していない時のみ利用可能
	 * @param pos 指定の場所
	 * @return 指定の場所の値
	 * @throws SQLException
	 */
	public long getLong(int pos)
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		return ((CallableStatement)this.activeStmt).getLong(pos);
	}

	/**
	 * 指定の場所の内容をint型で取得する。
	 * CallablesStatementを利用しカーソルを使用していない時のみ利用可能
	 * @param pos 指定の場所
	 * @return 指定の場所の値
	 * @throws SQLException
	 */
	public int getInt(int pos)
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		return ((CallableStatement)this.activeStmt).getInt(pos);
	}

	/**
	 * 指定の場所の内容をDate型で取得する。
	 * CallablesStatementを利用しカーソルを使用していない時のみ利用可能
	 * @param pos 指定の場所
	 * @return 指定の場所の値
	 * @throws SQLException
	 */
	public java.sql.Date getDate(int pos)
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		return ((CallableStatement)this.activeStmt).getDate(pos);
	}

	/**
	 * 指定の場所の内容をTime型で取得する。
	 * CallablesStatementを利用しカーソルを使用していない時のみ利用可能
	 * @param pos 指定の場所
	 * @return 指定の場所の値
	 * @throws SQLException
	 */
	public java.sql.Time getTime(int pos)
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		return ((CallableStatement)this.activeStmt).getTime(pos);
	}

	/**
	 * 指定の場所の内容をTimestamp型で取得する。
	 * CallablesStatementを利用しカーソルを使用していない時のみ利用可能
	 * @param pos 指定の場所
	 * @return 指定の場所の値
	 * @throws SQLException
	 */
	public java.sql.Timestamp getTimestamp(int pos)
		throws SQLException
	{
		if( this.activeStmtType != this.CALLABLE_STATEMENT )
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		return ((CallableStatement)this.activeStmt).getTimestamp(pos);
	}
	

	/**
	 * addBatchメソッドを追加する
	 * @throws SQLException
	 */
	public void addBatch()
		throws SQLException
	{
		try {
		
		this.activeStmt.addBatch();
		} catch(SQLException ex) {
			stcLog.getLog().fatal("StatementManager:addBatch" + "SQL例外が発生しました。" + ex.getMessage());
			throw new java.lang.IllegalStateException("CallableStatementを実行した時のみ利用できるメソッドです。");
		}
	}

	/**
	 * executeBatchメソッドを追加する
	 * @return 
	 * @throws SQLException
	 */
	public int[] executeBatch()
		throws SQLException
	{	
		try
		{
			if( this.activeStmt == null )
				throw new NullPointerException("StatementがNULLです。activeStatement()実行後でないと利用できません。");	
		
			return this.activeStmt.executeBatch();
			
		} catch(SQLException sqle)
		{
			stcLog.getLog().fatal("例外が発生しています。SQLは" + this.activeSql );
			throw sqle;
		}
		
	}
}