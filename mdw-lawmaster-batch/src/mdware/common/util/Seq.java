package mdware.common.util;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: RB Site連番管理テーブル(seq)より新しい連番を取得する。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author nob
 * @version 1.0
 */

import java.sql.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.CollectConnections;
import jp.co.vinculumjapan.stc.util.db.ConnectionPool;

public class Seq {
// 20021212 @UPD yamanaka コネクションプール使用する・しないで処理を分ける start
//	private static final ConnectionPool connectionPool = CollectConnections.getInstance().getConnectionPool("rbsite_ora"); // コネクションプールの取得
	private static final String databaseName = "rbsite_ora";
	private static final ConnectionPool connectionPool = CollectConnections.getInstance().getConnectionPool(databaseName); // コネクションプールの取得
// 20021212 @UPD yamanaka コネクションプール使用する・しないで処理を分ける end

// 20030325 @ADD deguchi サイクリック処理追加 start
	//採番マスタMax番号の許容される最大値
	public static final int SEQ_MAX_NUMBER = 1999999999;
// 20030325 @ADD deguchi サイクリック処理追加 end

	/**
	 * 連番の取得を行う。
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static int nextVal(String seqName) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int nextVal = 0;	// 新しい連番

		try{
// 20021212 @UPD yamanaka コネクションプール使用する・しないで処理を分ける start
//			connection = connectionPool.getConnection();
			if (connectionPool != null) { //コネクションプール使用する場合
			    connection = connectionPool.getConnection();
			} else { //コネクションプール使用しない場合
				jp.co.vinculumjapan.stc.util.db.DBInitialSettingBean dbSetting = CollectConnections.getInstance().getDBSetting( databaseName );
				if( dbSetting == null )
					throw new SQLException("DATABASEを初期化する情報が存在しません。");

				try
				{
					Class.forName( dbSetting.getDriver() );
				}
				catch( ClassNotFoundException cnfex )
				{
					String exstr = "";
					exstr += "DBDriverが見つかりません。\n";
					exstr += "指定されたDriverは" + dbSetting.getDriver() + "です。\n";
					exstr += "CLASSPATHを確認してください。\n";
					exstr += cnfex.toString();
					StcLog.getInstance().getLog().fatal( exstr );
					throw new SQLException( exstr );
				}

				try
				{
					connection = DriverManager.getConnection( dbSetting.getUrl(), dbSetting.getUser(), dbSetting.getPswd() );
				}
				catch( SQLException sqlex )
				{
					String exstr = "";
					exstr += "DataBaseに接続できませんでした。\n";
					exstr += "指定されたDB用URLは" + dbSetting.getUrl() + "です。\n";
					exstr += "指定されたDB用USERは" + dbSetting.getUser() + "です。\n";
					exstr += "指定されたDB用PASSWORDは" + dbSetting.getPswd() + "です。\n";
					exstr += sqlex.toString();
					StcLog.getInstance().getLog().fatal( exstr );
					throw new SQLException( exstr );
				}

				try
				{
					connection.setAutoCommit( false );
				}
				catch(Exception e)
				{
				}
			}
// 20021212 @UPD yamanaka コネクションプール使用する・しないで処理を分ける end

			connection.commit();	// 暗黙のトランザクション開始

			// 新しい連番の取得
			statement = connection.prepareStatement("select max_nb +1 from seq  where rtrim(saiban_id) = ? FOR UPDATE");
			statement.setString(1, seqName);
			resultSet = statement.executeQuery();
			resultSet.next();
			nextVal = resultSet.getInt(1);

            // 採番出来た場合
            if(nextVal != 0){
    			statement.close();
	    		statement = null;

// 20030325 @ADD deguchi サイクリック処理追加 start
	    		if ( nextVal <= SEQ_MAX_NUMBER ) {
				} else {
					//nextValが許容される最大値を超えた場合
// 20030506 @UPD deguchi サイクリック時初期値を変更により start
//					nextVal = 0;
					nextVal = 1;
// 20030506 @UPD deguchi サイクリック時初期値を変更により end
				}
// 20030325 @ADD deguchi サイクリック処理追加 end

		    	// 新しい連番の更新
			    statement = connection.prepareStatement("update seq set max_nb = ? where rtrim(saiban_id) = ?");
    			statement.setInt(1, nextVal);
    			statement.setString(2, seqName);

    			// 更新が成功した場合はcommit、失敗した場合はrollbackし0を返す。
    			if(statement.executeUpdate() == 1) {
    				connection.commit();
    			}
    			else {
    				connection.rollback();
    				StcLog.getInstance().getLog().fatal("連番の更新に失敗しました。");
    				nextVal = 0; // 返値に0をセット
    			}
            }
            // 採番出来なかった場合
            else{
   				StcLog.getInstance().getLog().fatal("連番名称がありません。名称:" + seqName);
            }
		}
		catch(SQLException sqle) {
			StcLog.getInstance().getLog().fatal("連番の取得に失敗しました。");
			nextVal = 0; // 返値に0をセット
		}
		finally {
			try{resultSet.close();}catch(SQLException sqle){};
			try{statement.close();}catch(SQLException sqle){};
			try{connectionPool.free(connection);}catch(Exception e){};
			resultSet = null;
			statement = null;
		}

		return nextVal;
	}
}
