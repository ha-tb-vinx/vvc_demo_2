package mdware.master.batch.process.mbZ0;

import java.sql.ResultSet;
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
 * <p>タイトル: 先行ジョブ終了待機処理</p>
 * <p>説明　　: 先行ジョブが終了するまで待機する。</p>
 * <p>著作権　: Copyright (c) 2013</p>
 * <p>会社名　: VINX Corp.</p>
 * @author S.Matsushita
 * @version 3.00 (2013/05/30) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 */
public class MBZ00101_BatchWait {

	// テーブル
	private static final String TABLE_JOB	= "SYSTEM_JOB_END_KANRI";		// システムジョブ終了管理マスタ

	private DataBase db	= null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// ジョブＩＤ（先行ジョブ）
	private String previousJobId = null;
	// ジョブＩＤ（開始ジョブ）
	private String thisJobId = null;
	// 先行ジョブ終了チェック間隔待機秒数(msec)
	private int timeInterval = 30000;
	// 先行ジョブ終了待機時間(分)
	private int timeWait = 0;	// 0で無制限

	/** 処理終了フラグ **/
	private boolean isStop = false;

	/** スリープ回数チェック **/
	private static int check = 0;

	/** 異常終了フラグ（true:異常、false:正常） **/
	private static boolean errorFg = false;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBZ00101_BatchWait(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase( mst000101_ConstDictionary.CONNECTION_STR );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBZ00101_BatchWait() {
		this(new DataBase( mst000101_ConstDictionary.CONNECTION_STR ));
		closeDb = true;
	}


	/**
	 * 本処理（JOBID、待機時間を取得）
	 * @throws Exception
	 */
	public void execute(String previousJobId, String thisJobId, String timeInterval, String timeWait) throws Exception {
		this.timeInterval = Integer.parseInt(timeInterval);
		this.timeWait = Integer.parseInt(timeWait);
		this.execute(previousJobId, thisJobId);
	}

	/**
	 * 本処理（JOBIDを取得）
	 * @throws Exception
	 */
	public void execute(String previousJobId, String thisJobId) throws Exception {
		this.previousJobId = previousJobId;
		this.thisJobId = thisJobId;
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
			if (this.previousJobId == null) {
				this.writeLog(Level.WARN_INT, "引数previousJobIdがありません。処理を中断します。");
				return;
			}

			// 引数のチェック（JOBIDがない場合はエラー）
			if (this.thisJobId == null) {
				this.writeLog(Level.WARN_INT, "引数thisJobIdがありません。処理を中断します。");
				return;
			}

			// 秒数へ変換(msec)
			int waitTime = this.timeWait * 60 * 1000;

			//先行ジョブ終了チェック処理
			this.writeLog(Level.INFO_INT, "先行ジョブ終了確認。（待機時間" + this.timeWait + "分、間隔" + this.timeInterval + "ミリ秒）※待機時間0分は無制限");
			while (!isStop) {
				// 先行ジョブの状態を確認する。（DBサービス非稼動時も考慮）
				ResultSet rs = null;
				String waitMessage = "先行ジョブが終了していない";
				try {
					rs = db.executeQuery(getJobKanriSelectSql(this.previousJobId));
					if (rs.next()) {
						isStop = true;
					}
				} catch (SQLException sqle) {
					// DBサービスが停止している可能性あり。
					waitMessage = "DB接続に何らかの問題あり";
				}finally{
					if (rs != null) {
						rs.close();
						rs = null;
					}
				}

				// 先行ジョブの情報が取得できなかった場合待機する。
				if (!isStop) {
					check++;
					this.writeLog(Level.INFO_INT, check + "回目待機中...（" + waitMessage + "）");
					Thread.sleep(this.timeInterval);

					// 終了待機時間以内に終了していなければ、異常終了
					if ((waitTime % (this.timeInterval * check) == 0) && (waitTime / (this.timeInterval * check) == 1)) {
						isStop = true;
						errorFg = true;
					}
				}
			}

			// 待機時間がオーバーした場合エラー
			if (errorFg) {
				this.writeLog(Level.ERROR_INT, timeWait + "分待機しましたが、先行ジョブ " + this.previousJobId + " が終了しない為、処理を中断します。");
				throw new Exception();
			}

			this.writeLog(Level.INFO_INT, "先行ジョブ " + this.previousJobId + " は終了しています。");

			// 先行ジョブのジョブ終了フラグを「終了」→「待機」に更新する。
			iRec = db.executeUpdate(getJobKanriUpdateSql(this.previousJobId));
			this.writeLog(Level.INFO_INT, iRec + "件のジョブ終了管理データ（先行ジョブ" + this.previousJobId + "）を更新しました。");

			// 開始ジョブのジョブ終了フラグを「待機」にし、「開始日時」を更新する。
			iRec = db.executeUpdate(getJobKanriUpdateNextJobSql(this.thisJobId));
			this.writeLog(Level.INFO_INT, iRec + "件のジョブ終了管理データ（開始ジョブ" + this.thisJobId + "）を更新しました。");

			db.commit();

			this.writeLog(Level.INFO_INT, "処理を終了します。");

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
	 * ジョブ管理テーブル検索SQL
	 * @param previousJobId
	 * @return String
	 */
	private String getJobKanriSelectSql(String previousJobId) {

		//SQL文生成用
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append("	  JOB_ID ");
		sb.append("	, JOB_END_FG ");
		sb.append("	, JOB_END_TS ");
		sb.append("FROM ");
		sb.append("	" + TABLE_JOB +  " ");
		sb.append("WHERE ");
		sb.append("		JOB_ID = '" + previousJobId + "' ");
		sb.append("	AND JOB_END_FG = '" + mst014001_JobEndFlagDictionary.SYURYO.getCode() + "' ");

		return sb.toString();
	}

	/**
	 * ジョブ管理テーブル更新（先行ジョブ）SQL
	 * @param previousJobId
	 * @return String
	 */
	private String getJobKanriUpdateSql(String previousJobId) {

		//SQL文生成用
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE ");
		sb.append("	" + TABLE_JOB +  " ");
		sb.append("SET ");
		sb.append("	JOB_END_FG = '" + mst014001_JobEndFlagDictionary.TAIKI.getCode() + "' ");
		sb.append("WHERE ");
		sb.append("	JOB_ID = '" + previousJobId + "' ");

		return sb.toString();
	}

	/**
	 * ジョブ管理テーブル更新（開始ジョブ）SQL
	 * @param thisJobId
	 * @return String
	 */
	private String getJobKanriUpdateNextJobSql(String thisJobId) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

		//SQL文生成用
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE ");
		sb.append("	" + TABLE_JOB +  " ");
		sb.append("SET ");
		sb.append("	  JOB_END_FG = '" + mst014001_JobEndFlagDictionary.TAIKI.getCode() + "' ");	// 基本的には待機状態になっているが、念の為に更新する。
		sb.append("	, JOB_START_TS = '" + systemDt + "' ");
		sb.append("WHERE ");
		sb.append("	JOB_ID = '" + thisJobId + "' ");

		return sb.toString();
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
