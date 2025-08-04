package mdware.master.batch.process.mbC0;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.CollectConnections;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:H160101RebuildTablesクラス</p>
 * <p>説明:アナライズを行うクラスです。</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author T.Kuzuhara
 * @version 1.0 (2009/09/14) 初版作成
*/
public class MBC00401_RebuildTables {
	
	private boolean closeDb = false;

	//DataBaseクラス
	private MasterDataBase db = null;

	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	

	/*
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBC00401_RebuildTables(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBC00401_RebuildTables() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 各バッチの処理を実行します。
	 */
	public void execute() throws Exception {
		String jobId = userLog.getJobId();
		try {
			Map map = ResorceUtil.getInstance().getPropertieMap("ANALYZE_TABLE");
			
			if (map == null) {
				writeLog(Level.INFO_INT, "アナライズ対象のテーブルが設定されていません。");
				return;
			}
			
			Map treeMap = convert(map);
			String ownName = CollectConnections.getInstance().getDBSetting("rbsite_ora").getUser().toUpperCase();
			
			Iterator iterator = treeMap.entrySet().iterator();
			
			while (iterator.hasNext()) {
				
				Entry entry = (Entry) iterator.next();
				
				// アナライズ
				analyzeTable(ownName, (String) entry.getValue());
				
			}
	
			db.commit();
			
		} catch (Exception e) {
			writeError(e);
			db.rollback();
	
			//SQL終了処理
		} finally {
			if (db != null) {
				db.close();
			}
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "が終了しました。");
		}
	
	}
	
	/**
	 * 指定したテーブルの統計情報を収集します。
	 * @param ownName ユーザ名
	 * @param tableName テーブル名
	 * @throws SQLException データベース例外
	 */
	private void analyzeTable(String ownName, String tableName) throws SQLException {
		PreparedStatement pstmtAnalize = null;
		try {
			writeLog(Level.INFO_INT, "アナライズを開始します。(ANALYZE:" + tableName + ")");
			db.executeUpdate(getAnalizeSql(ownName, tableName));
			writeLog(Level.INFO_INT, "アナライズを終了します。(ANALYZE:" + tableName + ")");
		} finally {
			if (pstmtAnalize != null) {
				pstmtAnalize.close();
			}
		}
	}
	
	/**
	 * アナライズするSQLを返します。
	 * @param ownName ユーザ名
	 * @param tableName テーブル名
	 * @return　アナライズするSQL
	 */
	private String getAnalizeSql(String ownName, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("BEGIN");
		sql.append("	DBMS_STATS.GATHER_TABLE_STATS(");
		sql.append("		OWNNAME => '" + ownName + "',");
		sql.append("		TABNAME => '" + tableName + "',");
		sql.append("		METHOD_OPT => 'FOR ALL INDEXED',");
		sql.append("		CASCADE => TRUE");
		sql.append("	);");
		sql.append("END;");
		return sql.toString();
	}
	
	/**
	 * TreeMapにコンバートします。
	 * @param map 再編成対象テーブル情報
	 * @return　TreeMap
	 */
	protected Map convert(Map map) {
		
		Iterator iterator = map.entrySet().iterator();
		
		Map treeMap = new TreeMap();
		
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			treeMap.put(entry.getKey(), entry.getValue());
		}
		return treeMap;
	}
	
	/********* 以下、ログ出力用メソッド *********/
	
	/**
	 * ユーザーログとバッチログにログを出力します。
	 * 
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message){
		String jobId = userLog.getJobId();

		switch(level){
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
			
		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}
	
	/**
	 * エラーをログファイルに出力します。
	 * ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 * 
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}
