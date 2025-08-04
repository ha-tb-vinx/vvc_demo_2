package mdware.master.batch.process.MSTB992;

import java.sql.SQLException;
import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 *
 * <p>タイトル: MSTB992031_BkPosCategoryCreate.java クラス</p>
 * <p>説明　: WK_POS部門メンテ、WK_POSデプトメンテ、WK_POSクラスメンテ、<br>
 *          WK_POSサブクラスメンテの内容を、ZEN_POS部門メンテ、ZEN_POSデプトメンテ、<br>
 *          ZEN_POSクラスメンテ、ZEN_POSサブクラスメンテに取込む</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2015.08.06) DAI.BQ FIVImart様対応
 *
 */
public class MSTB992031_BkPosCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	/**ログ出力用変数*/
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** テーブル*/
	private static final String TABLE_WK_POS_BUMON = "WK_POS_BUMON"; // WK_POS部門メンテ
	private static final String TABLE_WK_POS_DPT = "WK_POS_DPT"; // WK_POSデプトメンテ
	private static final String TABLE_WK_POS_CLASS_FIVI = "WK_POS_CLASS_FIVI"; // WK_POSクラスメンテ
	private static final String TABLE_WK_POS_SUBCLASS = "WK_POS_SUBCLASS"; // WK_POSサブクラスメンテ
	private static final String TABLE_ZEN_POS_BUMON = "ZEN_POS_BUMON"; // ZEN_POS部門メンテ
	private static final String TABLE_ZEN_POS_DPT = "ZEN_POS_DPT"; // ZEN_POSデプトメンテ
	private static final String TABLE_ZEN_POS_CLASS_FIVI = "ZEN_POS_CLASS_FIVI"; // ZEN_POSクラスメンテ
	private static final String TABLE_ZEN_POS_SUBCLASS = "ZEN_POS_SUBCLASS"; // ZEN_POSサブクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 処理日間隔*/
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992031_BkPosCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992031_BkPosCategoryCreate() {
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

			//商品分類体系作成日取得
			String createDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_SAKUSEI_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
			writeLog(Level.INFO_INT, "商品分類体系作成日： " + createDate);

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。(バッチ処理日≠商品分類体系作成日)");

				return;
			}
			writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");

			// ZEN_POS部門メンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZEN_POS部門メンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN_POS_BUMON));
			writeLog(Level.INFO_INT, "ZEN_POS部門メンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZEN_POS部門メンテの削除処理（全件）を終了します。");

			// ZEN_POS部門メンテのバックアップ（WK→ZEN）
			writeLog(Level.INFO_INT, "ZEN_POS部門メンテのバックアップ処理（WK→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_WK_POS_BUMON, TABLE_ZEN_POS_BUMON));
			writeLog(Level.INFO_INT, "ZEN_POS部門メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "ZEN_POS部門メンテのバックアップ処理（WK→ZEN）を終了します。");

			// ZEN_POSデプトメンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZEN_POSデプトメンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN_POS_DPT));
			writeLog(Level.INFO_INT, "ZEN_POSデプトメンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZEN_POSデプトメンテの削除処理（全件）を終了します。");

			// ZEN_POSデプトメンテの作成（WK→ZEN）
			writeLog(Level.INFO_INT, "ZEN_POSデプトメンテの作成処理（WK→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_WK_POS_DPT, TABLE_ZEN_POS_DPT));
			writeLog(Level.INFO_INT, "ZEN_POSデプトメンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "ZEN_POSデプトメンテの作成処理（WK→ZEN）を終了します。");

			// ZEN_POSクラスメンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZEN_POSクラスメンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN_POS_CLASS_FIVI));
			writeLog(Level.INFO_INT, "ZEN_POSクラスメンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZEN_POSクラスメンテの削除処理（全件）を終了します。");

			// ZEN_POSクラスメンテのバックアップ（WK→ZEN）
			writeLog(Level.INFO_INT, "ZEN_POSクラスメンテのバックアップ処理（WK→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_WK_POS_CLASS_FIVI, TABLE_ZEN_POS_CLASS_FIVI));
			writeLog(Level.INFO_INT, "ZEN_POSクラスメンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "ZEN_POSクラスメンテのバックアップ処理（WK→ZEN）を終了します。");

			// ZEN_POSサブクラスメンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZEN_POSサブクラスメンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN_POS_SUBCLASS));
			writeLog(Level.INFO_INT, "ZEN_POSサブクラスメンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZEN_POSサブクラスメンテの削除処理（全件）を終了します。");

			// ZEN_POSサブクラスメンテの作成（WK→ZEN）
			writeLog(Level.INFO_INT, "ZEN_POSサブクラスメンテの作成処理（WK→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_WK_POS_SUBCLASS, TABLE_ZEN_POS_SUBCLASS));
			writeLog(Level.INFO_INT, "ZEN_POSサブクラスメンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "ZEN_POSサブクラスメンテの作成処理（WK→ZEN）を終了します。");

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
