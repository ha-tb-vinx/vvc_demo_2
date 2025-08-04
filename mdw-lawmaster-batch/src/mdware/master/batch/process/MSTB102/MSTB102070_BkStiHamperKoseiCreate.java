package mdware.master.batch.process.MSTB102;

import java.sql.SQLException;
import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 *
 * <p>タイトル: 指定日ハンパー構成バックアップ処理(MSTB102070)</p>
 * <p>説明　: WWK_指定日ハンパー構成のバックアップ処理を実施する。</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.01.17) #1749対応 S.Takayama 新規作成
 */
public class MSTB102070_BkStiHamperKoseiCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_BK_STI_HAMPER_KOSEI = "BK_STI_HAMPER_KOSEI"; // BK_指定日ハンパー構成

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB102070_BkStiHamperKoseiCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB102070_BkStiHamperKoseiCreate() {
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

			// BK_指定日ハンパー構成 のTRUNCATE
			writeLog(Level.INFO_INT, "BK_指定日ハンパー構成 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_BK_STI_HAMPER_KOSEI);
			writeLog(Level.INFO_INT, "BK_指定日ハンパー構成 削除処理を終了します。");

			// BK_指定日ハンパー構成 の登録
			writeLog(Level.INFO_INT, "BK_指定日ハンパー構成 登録処理を開始します。");
			iRec = db.executeUpdate(getBkStiHamperKoseiInsertSql());
			writeLog(Level.INFO_INT, "BK_指定日ハンパー構成 を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_指定日ハンパー構成 登録処理を終了します。");

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
	 * BK_指定日ハンパー構成を作成するSQLを取得する
	 * 
	 * @return BK_STI_HAMPER_KOSEI登録SQL
	 */
	private String getBkStiHamperKoseiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO BK_STI_HAMPER_KOSEI ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,TAISYO_DT ");
		sql.append("	,HAMPER_SYOHIN_CD ");
		sql.append("	,KOSEI_SYOHIN_CD ");
		sql.append("	,HANBAI_START_DT ");
		sql.append("	,HANBAI_END_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,BAIKA_HAISHIN_FG ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 WSHK.UKETSUKE_NO ");
		sql.append("	,WSHK.TAISYO_DT ");
		sql.append("	,WSHK.HAMPER_SYOHIN_CD ");
		sql.append("	,WSHK.KOSEI_SYOHIN_CD ");
		sql.append("	,WSHK.HANBAI_START_DT ");
		sql.append("	,WSHK.HANBAI_END_DT ");
		sql.append("	,WSHK.TENPO_CD ");
		sql.append("	,WSHK.BAIKA_HAISHIN_FG ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_STI_HAMPER_KOSEI WSHK ");

		return sql.toString();
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
