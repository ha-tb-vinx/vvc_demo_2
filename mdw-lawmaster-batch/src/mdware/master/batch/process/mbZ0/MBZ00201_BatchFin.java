package mdware.master.batch.process.mbZ0;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst014001_JobEndFlagDictionary;

import org.apache.log4j.Level;

/**
 * <p>タイトル: ジョブ終了処理</p>
 * <p>説明　　: 実行ジョブの終了判断を行う。</p>
 * <p>著作権　: Copyright (c) 2013</p>
 * <p>会社名　: VINX Corp.</p>
 * @author S.Matsushita
 * @version 3.00 (2013/05/30) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 */
public class MBZ00201_BatchFin {

	// テーブル
	private static final String TABLE_JOB	= "SYSTEM_JOB_END_KANRI";		// システムジョブ終了管理マスタ

	private DataBase db	= null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// ジョブＩＤ
	private String jobId = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBZ00201_BatchFin(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase( mst000101_ConstDictionary.CONNECTION_STR );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBZ00201_BatchFin() {
		this(new DataBase( mst000101_ConstDictionary.CONNECTION_STR ));
		closeDb = true;
	}


	/**
	 * 本処理（JOBIDを取得）
	 * @throws Exception
	 */
	public void execute(String jobId) throws Exception {
		this.jobId = jobId;
		this.execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try{

			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			// 処理開始ログ
			this.writeLog(Level.INFO_INT, "処理を開始します。");

			// 引数のチェック（JOBIDがない場合はエラー）
			if (this.jobId == null) {
				this.writeLog(Level.WARN_INT, "引数jobIdがありません。処理を中断します。");
				return;
			}

			// ジョブ終了フラグを終了に更新する。
			iRec = db.executeUpdate(getJobKanriUpdateSQL(jobId));

			db.commit();

			if (iRec > 0) {
				this.writeLog(Level.INFO_INT, jobId + "ジョブ終了管理データを" + iRec + "件更新しました。");
				this.writeLog(Level.INFO_INT, "処理を終了します。");
			} else {
				this.writeLog(Level.ERROR_INT, "更新件数が0件の為、処理を中断します。");
				throw new Exception();
			}

		//SQLエラーが発生した場合の処理
		}catch(SQLException se){
			db.rollback();
			this.writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

		//その他のエラーが発生した場合の処理
		}catch(Exception e){
			db.rollback();
			this.writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

		//SQL終了処理
		}finally{
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * ジョブ管理テーブル更新SQL
	 * @param serverTime
	 * @param jobId
	 * @return String
	 */
	private String getJobKanriUpdateSQL(String jobId) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("UPDATE ");
		strSql.append("	" + TABLE_JOB +  " ");
		strSql.append("SET ");
		strSql.append("	  JOB_END_FG = '" + mst014001_JobEndFlagDictionary.SYURYO.getCode() + "' ");
		strSql.append("	, JOB_END_TS = '" + systemDt + "' ");
		strSql.append("WHERE ");
		strSql.append("	JOB_ID = '" + jobId + "' ");

		return strSql.toString();
	}


/********** 共通処理 **********/

	/**
	 * ユーザーログとバッチログにログを出力します。
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

		case Level.WARN_INT:
			userLog.warn(message);
			batchLog.getLog().warn(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
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
