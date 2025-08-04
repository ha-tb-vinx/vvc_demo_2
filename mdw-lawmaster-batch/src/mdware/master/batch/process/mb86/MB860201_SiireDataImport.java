package mdware.master.batch.process.mb86;

import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.util.db.MasterDataBase;
import mdware.common.batch.util.control.jobProperties.Jobs;

/**
 * <p>タイトル:単品仕入データ取得処理</p>
 * <p>説明:仕入管理サブシステムから提供されているIFテーブルの内容を、マスタ管理サブ内のワークに取込む</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2009/03/19 マミーマート様向け初版作成 T.Mori
 */
 
public class MB860201_SiireDataImport {
	
	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();
	
	private final String TRUNCATE_TABLE = "WK_TANPIN_SHIIRE";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB860201_SiireDataImport(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB860201_SiireDataImport() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}
	
	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		execute();
	}
	
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
			
		try {
			
			//SQL文生成用
			StringBuffer strSql = new StringBuffer();
			
			int insNum = 0; 
			
			writeLog(Level.INFO_INT, "処理を開始します。");
			
			writeLog(Level.INFO_INT, "単品仕入ワークテーブル削除処理を開始します。");
			//単品仕入ワークテーブル削除
			trancateTable(this.TRUNCATE_TABLE);
			writeLog(Level.INFO_INT, "単品仕入ワークテーブル削除処理を終了します。");
			
			writeLog(Level.INFO_INT, "単品仕入ワークテーブル登録処理を開始します。");

			//登録する
			strSql.delete(0,strSql.length());
			strSql.append("INSERT INTO WK_TANPIN_SHIIRE(");
			strSql.append("  COMP_CD,");
			strSql.append("  TENPO_CD,");
			strSql.append("  BUNRUI1_CD,");
			strSql.append("  DENPYO_DT,");
			strSql.append("  KANRI_DT,");
			strSql.append("  SYOHIN_CD,");
			strSql.append("  INSERT_TS) ");
			strSql.append("  SELECT ");
			strSql.append("	   COMP_CD, ");
			strSql.append("	   TENPO_CD, ");
			strSql.append("	   BUNRUI1_CD, ");
			strSql.append("	   DENPYO_DT, ");
			strSql.append("	   KANRI_DT, ");
			strSql.append("	   SYOHIN_CD, ");
			strSql.append("	   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");	
			strSql.append("  FROM ");
			strSql.append("    IF_WK_SHOHIN_JISEKI ");	
			
			insNum += db.executeUpdate(strSql.toString());

			writeLog(Level.INFO_INT, insNum + "件の単品仕入ワークテーブルレコードを登録しました。");	
			writeLog(Level.INFO_INT, "単品仕入ワークテーブル削除処理を終了します。");

			db.commit();
			
			writeLog(Level.INFO_INT, "処理を終了します。");

		// SQL例外処理
		}catch( SQLException se ){
			db.rollback();

			this.writeError(se);

			throw se;	
			
		}catch( Exception e ){
			db.rollback();

			this.writeError(e);

			throw e;

		}finally{
			// クローズ
			if (closeDb || db != null) {
				db.close();
			}
		}
	}
	
	/**
	 * テーブルをトランケートします
	 * @param tableName トランケートするテーブル名
	 * @throws Exception
	 */
	protected void trancateTable(String tableName) throws SQLException {
		String sql = "TRUNCATE TABLE " + tableName;
		this.db.executeUpdate(sql);
	}

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