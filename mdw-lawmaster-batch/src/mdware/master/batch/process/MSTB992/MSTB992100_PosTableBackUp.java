package mdware.master.batch.process.MSTB992;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB992100_PosTableBackUp.java クラス</p>
 * <p>説明　: IF_PLU単品メンテ、IF_POSラインメンテ、IF_POSクラスメンテのバックアップを行う。</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.03) T.Ooshiro [CUS00063] ランドローム様対応 POSインターフェイス仕様変更対応
 *
 */
public class MSTB992100_PosTableBackUp {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String IF_TEC_PLU = "IF_TEC_PLU"; // IF_PLU単品メンテ
	private static final String BK_IF_TEC_PLU = "BK_IF_TEC_PLU"; // BK_IF_PLU単品メンテ
	private static final String IF_POS_LINE = "IF_POS_LINE"; // IF_POSラインメンテ
	private static final String BK_IF_POS_LINE = "BK_IF_POS_LINE"; // BK_IF_POSラインメンテ
	private static final String IF_POS_CLASS = "IF_POS_CLASS"; // IF_POSクラスメンテ
	private static final String BK_IF_POS_CLASS = "BK_IF_POS_CLASS"; // BK_IF_POSクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992100_PosTableBackUp(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992100_PosTableBackUp() {
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

			//商品分類体系作成日取得
			String createDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_SAKUSEI_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);

			// IF_PLU単品メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_PLU単品メンテのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(IF_TEC_PLU, BK_IF_TEC_PLU, batchDate));
			writeLog(Level.INFO_INT, "BK_IF_PLU単品メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_PLU単品メンテのバックアップ処理（IF→BK_IF）を終了します。");

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。(バッチ処理日≠商品分類体系作成日)");
			} else {
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");

				// IF_POSラインメンテのバックアップ（IF→BK_IF）
				writeLog(Level.INFO_INT, "IF_POSラインメンテのバックアップ処理（IF→BK_IF）を開始します。");
				iRec = db.executeUpdate(getAllInsertSQL(IF_POS_LINE, BK_IF_POS_LINE, batchDate));
				writeLog(Level.INFO_INT, "BK_IF_POSラインメンテへ" + iRec + "件登録しました。");
				writeLog(Level.INFO_INT, "IF_POSラインメンテのバックアップ処理（IF→BK_IF）を終了します。");

				// IF_POSクラスメンテのバックアップ（IF→BK_IF）
				writeLog(Level.INFO_INT, "IF_POSクラスメンテのバックアップ処理（IF→BK_IF）を開始します。");
				iRec = db.executeUpdate(getAllInsertSQL(IF_POS_CLASS, BK_IF_POS_CLASS, batchDate));
				writeLog(Level.INFO_INT, "BK_IF_POSクラスメンテへ" + iRec + "件登録しました。");
				writeLog(Level.INFO_INT, "IF_POSクラスメンテのバックアップ処理（IF→BK_IF）を終了します。");
			}

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
	 * 全件追加用SQLを生成する。
	 * @param tableIdFrom	追加元テーブル
	 * @param tableIdTo		追加先テーブル
	 * @param batchDt		バッチ日付
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllInsertSQL(String tableIdFrom, String tableIdTo, String batchDt) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + tableIdTo + " ");
		strSql.append("SELECT ");
		strSql.append("	'" + batchDt + "' ");
		strSql.append("	,'" + systemDt + "' ");
		strSql.append("	," + tableIdFrom + ".* ");
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
