package mdware.master.batch.process.MSTB001;

import java.sql.SQLException;
import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
/**
*
* <p>タイトル: 指定日POS処理状況更新（開始）処理（MSTB001010）</p>
* <p>説明　: 未来のPOSデータを送信する対象店・対象日のレコードを抽出し、処理中にする</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @Version 1.00 2017.01.16 S.Takayama #1749 FIVImart対応
*/

public class MSTB001010_StiPosStatusUpStart {
	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB001010_StiPosStatusUpStart(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB001010_StiPosStatusUpStart() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception{
		try {
			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// 指定日POS処理状況更新（開始）処理
			writeLog(Level.INFO_INT, "指定日POS処理状況更新（開始）処理を開始します。");
			iRec = db.executeUpdate(getUpdateRPluSiteibiSql());
			writeLog(Level.INFO_INT, "指定日POS処理状況更新（開始）処理を" + iRec + "件更新しました。");
			writeLog(Level.INFO_INT, "指定日POS処理状況更新（開始）処理を終了します。");

			db.commit();

			writeLog(Level.INFO_INT, "処理を終了します。");

		} catch (SQLException se) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

			//その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

			//SQL終了処理
		} finally {
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

	/********** ＳＱＬ生成処理 **********/

	/**
	 * If_Plu_Tanpin(新規・訂正)を登録するSQLを取得する
	 *
	 * @return If_Plu_Tanpin(新規・訂正)登録SQL
	 */
	public String getUpdateRPluSiteibiSql() throws Exception{
		StringBuilder sql= new StringBuilder();

		sql.append("UPDATE R_PLU_SITEIBI ");
		sql.append("	SET SYORI_FG = '1' ");
		sql.append(" WHERE ");
		sql.append("	SYORI_FG = '0'");

		return sql.toString();
	}

	/******* 共通処理 **********/

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
