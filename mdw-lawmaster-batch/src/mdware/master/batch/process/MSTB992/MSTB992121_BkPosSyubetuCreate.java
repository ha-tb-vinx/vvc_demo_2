package mdware.master.batch.process.MSTB992;

import java.sql.SQLException;
import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 *
 * <p>タイトル: MSTB992121_BkPosSyubetuCreate.java クラス</p>
 * <p>説明　: IF_POS支払種別メンテ、IF_POS特売種別メンテの内容を、<br>
 *          ZEN_POS支払種別メンテ、ZEN_POS特売種別メンテに取込む</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.04.04) T.Han #2463 FIVImart様対応
 *
 */
public class MSTB992121_BkPosSyubetuCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	/**ログ出力用変数*/
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** テーブル*/
	private static final String TABLE_IF_POS_PAYMENT = "IF_POS_PAYMENT";     // IF_POS支払種別メンテ
	private static final String TABLE_IF_POS_DISCOUNT = "IF_POS_DISCOUNT";   // IF_POS特売種別メンテ
	private static final String TABLE_ZEN_POS_PAYMENT = "ZEN_POS_PAYMENT";   // ZEN_POS支払種別メンテ
	private static final String TABLE_ZEN_POS_DISCOUNT = "ZEN_POS_DISCOUNT"; // ZEN_POS特売種別メンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992121_BkPosSyubetuCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992121_BkPosSyubetuCreate() {
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

			// ZEN_POS支払種別メンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZEN_POS支払種別メンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN_POS_PAYMENT));
			writeLog(Level.INFO_INT, "ZEN_POS支払種別メンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZEN_POS支払種別メンテの削除処理（全件）を終了します。");

			// ZEN_POS支払種別メンテのバックアップ（IF→ZEN）
			writeLog(Level.INFO_INT, "ZEN_POS支払種別メンテのバックアップ処理（IF→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_IF_POS_PAYMENT, TABLE_ZEN_POS_PAYMENT));
			writeLog(Level.INFO_INT, "ZEN_POS支払種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "ZEN_POS支払種別メンテのバックアップ処理（IF→ZEN）を終了します。");

			// ZEN_POS特売種別メンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZEN_POS特売種別メンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN_POS_DISCOUNT));
			writeLog(Level.INFO_INT, "ZEN_POS特売種別メンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZEN_POS特売種別メンテの削除処理（全件）を終了します。");

			// ZEN_POS特売種別メンテのバックアップ（IF→ZEN）
			writeLog(Level.INFO_INT, "ZEN_POS特売種別メンテのバックアップ処理（IF→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_IF_POS_DISCOUNT, TABLE_ZEN_POS_DISCOUNT));
			writeLog(Level.INFO_INT, "ZEN_POS特売種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "ZEN_POS特売種別メンテのバックアップ処理（IF→ZEN）を終了します。");

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
	 * 全件追加用SQLを生成する。（前提としてテーブルレイアウトが同じであること）
	 * @param tableIdFrom	追加元テーブル
	 * @param tableIdTo	追加先テーブル
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllInsertSQL(String tableIdFrom, String tableIdTo) throws Exception {
		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + tableIdTo + " "); // テーブルレイアウトが同じ前提である為、カラムは指定しない。
		strSql.append("SELECT * "); // テーブルレイアウトが同じ前提である為「*」を使う。
		strSql.append("FROM " + tableIdFrom + " ");

		return strSql.toString();
	}

/********** 共通処理 **********/

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
		batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}
