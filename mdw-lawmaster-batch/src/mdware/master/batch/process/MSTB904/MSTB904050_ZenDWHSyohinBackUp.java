package mdware.master.batch.process.MSTB904;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB904050_ZenDWHSyohinBackUp.java クラス</p>
 * <p>説明　: WK_DWH_商品マスタの内容を、ZEN_DWH_商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.08) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 *
 */
public class MSTB904050_ZenDWHSyohinBackUp {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK = "WK_DWH_SYOHIN"; // WK_DWH_商品マスタ
	private static final String TABLE_ZEN = "ZEN_DWH_SYOHIN"; // ZEN_DWH_商品マスタ
	private static final String TABLE_ZEN_BK	= "BK_ZEN_DWH_SYOHIN";	// BK_ZEN_DWH_商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904050_ZenDWHSyohinBackUp(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904050_ZenDWHSyohinBackUp() {
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

			// BK_ZEN_DWH_.商品マスタの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "BK_ZEN_DWH_.商品マスタの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN_BK));
			writeLog(Level.INFO_INT, "BK_ZEN_DWH_.商品マスタを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "BK_ZEN_DWH_.商品マスタの削除処理（全件）を終了します。");

			// ZEN_DWH_.商品マスタのバックアップ（ZEN→BK_ZEN）
			writeLog(Level.INFO_INT, "ZEN_DWH_.商品マスタのバックアップ処理（ZEN→BK_ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_ZEN, TABLE_ZEN_BK));
			writeLog(Level.INFO_INT, "BK_ZEN_DWH_.商品マスタへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "ZEN_DWH_.商品マスタのバックアップ処理（ZEN→BK_ZEN）を終了します。");


			// ZEN_DWH_商品マスタの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZEN_DWH_商品マスタの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN));
			writeLog(Level.INFO_INT, "ZEN_DWH_商品マスタを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "ZEN_DWH_商品マスタの削除処理（全件）を終了します。");

			// ZEN_DWH_商品マスタの作成（WK→ZEN）
			writeLog(Level.INFO_INT, "ZEN_DWH_商品マスタの作成処理（WK→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_WK, TABLE_ZEN));
			writeLog(Level.INFO_INT, "ZEN_DWH_商品マスタへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "ZEN_DWH_商品マスタの作成処理（WK→ZEN）を終了します。");

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
