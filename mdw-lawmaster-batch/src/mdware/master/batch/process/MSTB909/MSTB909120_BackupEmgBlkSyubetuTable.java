package mdware.master.batch.process.MSTB909;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB909120_BackupEmgBlkSyubetuTable.java クラス</p>
 * <p>説明　: WK_緊急BLK支払種別マスタ、WK_緊急BLK特売種別マスタの内容を、ZEN_BLK支払種別マスタ、ZEN_BLK特売種別マスタに取込む。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.04.07) M.Son #2463 FIVImart様対応
 *
 */
public class MSTB909120_BackupEmgBlkSyubetuTable {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String WK_EMG_BLK_PAYMENT = "WK_EMG_BLK_PAYMENT"; // WK_緊急BLK支払種別マスタ
	private static final String ZEN_BLK_PAYMENT = "ZEN_BLK_PAYMENT"; // ZENBLK支払種別マスタ

	private static final String WK_EMG_BLK_DISCOUNT = "WK_EMG_BLK_DISCOUNT"; // WK_緊急BLK特売種別マスタ
	private static final String ZEN_BLK_DISCOUNT = "ZEN_BLK_DISCOUNT"; // ZENBLK特売種別マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB909120_BackupEmgBlkSyubetuTable(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB909120_BackupEmgBlkSyubetuTable() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try {

			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// ZENBLK支払種別マスタの全件削除（DELETE）
			writeLog(Level.INFO_INT, "ZENBLK支払種別マスタの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(ZEN_BLK_PAYMENT));
			writeLog(Level.INFO_INT, "ZENBLK支払種別マスタを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZENBLK支払種別マスタの削除処理（全件）を終了します。");

			// WK_緊急BLK支払種別マスタのバックアップ
			writeLog(Level.INFO_INT, "WK_緊急BLK支払種別マスタのバックアップ処理を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(WK_EMG_BLK_PAYMENT, ZEN_BLK_PAYMENT));
			writeLog(Level.INFO_INT, "ZENBLK支払種別マスタへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "WK_緊急BLK支払種別マスタのバックアップ処理を終了します。");

			// ZENBLK特売種別マスタの全件削除（DELETE）
			writeLog(Level.INFO_INT, "ZENBLK特売種別マスタの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(ZEN_BLK_DISCOUNT));
			writeLog(Level.INFO_INT, "ZENBLK特売種別マスタを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZENBLK特売種別マスタの削除処理（全件）を終了します。");

			// WK_緊急BLK特売種別マスタのバックアップ
			writeLog(Level.INFO_INT, "WK_緊急BLK特売種別マスタのバックアップ処理を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(WK_EMG_BLK_DISCOUNT, ZEN_BLK_DISCOUNT));
			writeLog(Level.INFO_INT, "ZENBLK特売種別マスタへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "WK_緊急BLK特売種別マスタのバックアップ処理を終了します。");

			db.commit();

			writeLog(Level.INFO_INT, "処理を終了します。");

			//SQLエラーが発生した場合の処理
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
	 * 全件削除用SQLを生成する。
	 * @param tableId	削除テーブル
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllDeleteSQL(String tableId) throws Exception {
		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("DELETE FROM " + tableId + " ");

		return strSql.toString();
	}

	/**
	 * 全件追加用SQLを生成する。
	 * @param tableIdFrom	追加元テーブル
	 * @param tableIdTo		追加先テーブル
	 * @param batchDt		バッチ日付
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllInsertSQL(String tableIdFrom, String tableIdTo) throws Exception {
		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + tableIdTo + " ");
		strSql.append("SELECT ");
		strSql.append("	" + tableIdFrom + ".* ");
		strSql.append("FROM " + tableIdFrom + " ");

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
