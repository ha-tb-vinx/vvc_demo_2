package mdware.master.batch.process.MSTB920;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB920070_PosSyubetuTableBackUp.java クラス</p>
 * <p>説明　: WK_緊急POS支払種別メンテ、WK_緊急POS特売種別メンテの内容を、ZEN_POS支払種別メンテ、ZEN_POS特売種別メンテに取込む</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.04.06) M.Son #2463 FIVImart様対応
 *
 */
public class MSTB920070_PosSyubetuTableBackUp {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String WK_EMG_POS_PAYMENT = "WK_EMG_POS_PAYMENT"; // WK緊急POS支払種別メンテ
	private static final String ZEN_POS_PAYMENT = "ZEN_POS_PAYMENT"; // ZEN_POS支払種別メンテ
	private static final String WK_EMG_POS_DISCOUNT = "WK_EMG_POS_DISCOUNT"; // WK緊急POS特売種別メンテ
	private static final String ZEN_POS_DISCOUNT = "ZEN_POS_DISCOUNT"; // ZENPOS特売種別メンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB920070_PosSyubetuTableBackUp(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB920070_PosSyubetuTableBackUp() {
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

			//バッチ日付取得
			String batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDate);

			// ZENPOS支払種別メンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZENPOS支払種別メンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(ZEN_POS_PAYMENT));
			writeLog(Level.INFO_INT, "ZENPOS支払種別メンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZENPOS支払種別メンテの削除処理（全件）を終了します。");

			// ZENPOS特売種別メンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZENPOS特売種別メンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(ZEN_POS_DISCOUNT));
			writeLog(Level.INFO_INT, "ZENPOS特売種別メンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZENPOS特売種別メンテの削除処理（全件）を終了します。");

			// WK緊急POS支払種別メンテのバックアップ（WK→ZEN_POS）
			writeLog(Level.INFO_INT, "WK緊急POS支払種別メンテのバックアップ処理（WK→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(WK_EMG_POS_PAYMENT, ZEN_POS_PAYMENT));
			writeLog(Level.INFO_INT, "ZENPOS支払種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "WK緊急POS支払種別メンテのバックアップ処理（WK→ZEN）を終了します。");

			// WK緊急POS特売種別メンテのバックアップ（WK→ZEN_POS）
			writeLog(Level.INFO_INT, "WK緊急POS特売種別メンテのバックアップ処理（WK→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(WK_EMG_POS_DISCOUNT, ZEN_POS_DISCOUNT));
			writeLog(Level.INFO_INT, "ZENPOS特売種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "WK緊急POS特売種別メンテのバックアップ処理（WK→ZEN）を終了します。");

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
