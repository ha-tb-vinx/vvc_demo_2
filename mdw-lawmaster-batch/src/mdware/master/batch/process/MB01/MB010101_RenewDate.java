package mdware.master.batch.process.MB01;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:システム日付更新処理</p>
 * <p>説明:システムコントロールのシステム日付（オンライン日付）を更新する。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/05/24<BR>
 * @author hara
 * @version 2.00 2009/02/13 マミーマート様向け初版作成 T.Mori
 * @version 3.00 (2013.06.01)  M.Ayukawa ランドローム様  コメント整理
 */
 
public class MB010101_RenewDate {
	
	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	public static final int SYORI_DAYS = 1;
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB010101_RenewDate(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB010101_RenewDate() {
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
		
		try{
			
			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			//SQL文生成用
			StringBuffer strSql = new StringBuffer();
			
			int iRec = 0;
			
			//オンライン日付格納用変数
			String mRec =null;
			
			//オンライン日付を取得
			strSql.delete(0,strSql.length());
			strSql.append("SELECT ");			
			strSql.append("  PARAMETER_TX ");				
			strSql.append("FROM ");			
			strSql.append("  SYSTEM_CONTROL ");
			strSql.append("WHERE ");	
			strSql.append( " SUBSYSTEM_ID = '" ).append( mst000101_ConstDictionary.SUBSYSTEM_DIVISION ).append( "' " );
			strSql.append( " AND PARAMETER_ID = '" ).append( mst000101_ConstDictionary.ONLINE_DT ).append( "' " );
			
			ResultSet mrs = db.executeQuery(strSql.toString());

			//オンライン日付を変数に格納する
			if(mrs.next()){
				mRec = mrs.getString("PARAMETER_TX");
			}
			db.closeResultSet(mrs);
			
			writeLog(Level.INFO_INT, "（マスタ）オンライン日付更新処理を開始します。【変更前："+ DateChanger.toSeparatorDate(mRec) +"】");
			
			/********************************
			 * オンライン日付を＋1日更新する。
			 ********************************/
			strSql.delete(0,strSql.length());
			strSql.append("UPDATE ");
			strSql.append("  SYSTEM_CONTROL ");

			strSql.append("SET ");

			strSql.append("  PARAMETER_TX = '" + DateChanger.addDate(mRec,SYORI_DAYS) + "',");											//オンライン日付に＋１日、追加したものを代入
			strSql.append("  UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");		//更新日付(システム日付）
			strSql.append("  UPDATE_USER_ID = '" + userLog.getJobId() + "' ");													//更新者社員ＩＤ（バッチＩＤ）
			strSql.append("WHERE ");	
			strSql.append( " SUBSYSTEM_ID = '" ).append( mst000101_ConstDictionary.SUBSYSTEM_DIVISION ).append( "' " );
			strSql.append( " AND PARAMETER_ID = '" ).append( mst000101_ConstDictionary.ONLINE_DT ).append( "' " );
			
			
			iRec = db.executeUpdate(strSql.toString());
			
			//レコード件数が0件の場合は以降の処理を行わない。
			if(iRec == 0){
				writeLog(Level.ERROR_INT, "システムコントロールマスタにデータが存在しません。");
				throw new Exception();
			}else{
				writeLog(Level.INFO_INT, "（マスタ）オンライン日付更新に成功しました。  【変更後："+ DateChanger.toSeparatorDate(DateChanger.addDate(mRec,SYORI_DAYS)) +"】");
			}
			
			db.commit();
			
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