package mdware.common.stc.util.db;

import java.sql.*;
import java.util.*;

import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>タイトル: PreparedStatementDataBase.java
 * <p>説明: ＳＱＬをＤＢに投げ処理を行う</p>
 * <pre>
 * 
 * 
 * </pre>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * <p>日付: 2004.07.14</p>
 *	@author k.arai
 *	@version 1.0
 */
public class PreparedStatementDataBase extends DataBase
{

	private static String databaseName = "rbsite_ora";
	private PreparedStatement pstmt = null; 

	/**
	 * コンストラクタ
	 * @param
	 */
	public PreparedStatementDataBase()
	{
		super(databaseName);		
	}

	/**
	 * PreparedStatement発行するメソッド
	 * @param String sql
	 */
	public PreparedStatement createPreparedStatement( String sql )
		throws SQLException
	{
		if( conn == null )
		{
			connect( databaseName );
		}
		return ( pstmt = conn.prepareStatement( sql ));		
	}

	/**
	 * closeメソッド
	 */
	public void close()
	{
		try{
			pstmt.close();
			pstmt = null;		
		}catch(Exception e){
			
		}			
		super.close();
	}

}
