package mdware.master.batch.process.MSTB909;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p> タイトル: MSTB909130_BlkKinkyuuHaishinResetクラス</p>
 * <p> 説明: 緊急配信リセット処理</p>
 * <p> 著作権: Copyright (c) 2017 </p>
 * <p> 会社名: Vinculum Japan Corporation </p>
 * @author VINX
 * @Version 1.00 (2017.04.07) M.Son #2463 FIVIMART対応
 */

public class MSTB909130_BlkKinkyuuHaishinReset {

	private MasterDataBase db = null;

	// ログ出力用変数
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();

	/**
	 * コンストラクタ
	 *
	 * @param dataBase
	 */
	public MSTB909130_BlkKinkyuuHaishinReset(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB909130_BlkKinkyuuHaishinReset() {
		this(new MasterDataBase("rbsite_ora"));
	}

	/**
	 * 外部からの実行メソッド
	 * @param なんし
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {

		// jobId
		String jobId = userLog.getJobId();

		// バッチ登録件数をカウント
		int iRec = 0;

		try {

			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "を開始します。");

			writeLog(Level.INFO_INT, "Blynk配信シーケンス管理テーブルの更新処理を開始します。");
			// Blynk配信シーケンス管理テーブルのファイルNoを更新
			iRec = db.executeUpdate(updateFileNoOfBlkFileSeqSql());
			writeLog(Level.INFO_INT, "Blynk配信シーケンス管理テーブルを" + iRec + "件更新しました。");
			writeLog(Level.INFO_INT, "Blynk配信シーケンス管理テーブルの更新処理を終了します。");

			db.commit();

		// SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			db.rollback();
			// ログ出力
			writeError(se);
			throw se;

		// その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			// ログ出力
			writeError(e);
			throw e;

			// SQL終了処理
		} finally {
			db.close();
			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 * PLU配信シーケンス管理テーブルのファイルNoを更新する
	 * @param     なんし
	 * @return SQL文
	 */
	private String updateFileNoOfBlkFileSeqSql() throws SQLException {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" UPDATE ");
		strSql.append("        BLK_FILE_SEQ BFS ");
		strSql.append("    SET ");
		strSql.append("        BFS.FILENO = '2' "); // ファイルNo = '2'

		return strSql.toString();
	}

	/********* 以下、ログ出力用メソッド *********/

	/**
	 * ユーザーログとバッチログにログを出力します。
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message) {
		String jobId = userLog.getJobId();

		switch (level) {
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}

	/**
	 * エラーをログファイルに出力します。 ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}
}