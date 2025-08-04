package mdware.master.batch.process.MSTB995;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB995020_ErrKeiryokiSyohinCreate.java クラス</p>
 * <p>説明　: WK_IF_計量器のエラーデータの内容を、ERR_IF_計量器に取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.24) T.Ooshiro [CUS00065] ランドローム様対応 計量器インターフェイス仕様変更対応
 *
 */
public class MSTB995020_ErrKeiryokiSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// // テーブル
	// private static final String TABLE_WK = "DT_ERR_IF_KEIKRYOKI" // ERR_IF_計量器

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB995020_ErrKeiryokiSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB995020_ErrKeiryokiSyohinCreate() {
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

			// ERR_IF_計量器の登録
			writeLog(Level.INFO_INT, "ERR_IF_計量器登録処理（WK→ERR）を開始します。");
			iRec = db.executeUpdate(getDtErrIfKeiryokiInsertSql());
			writeLog(Level.INFO_INT, "ERR_IF_計量器を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "ERR_IF_計量器登録処理（WK→ERR）を終了します。");

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
	 * DT_ERR_VAN_SYOHINを登録するSQLを取得する
	 * 
	 * @return DT_ERR_VAN_SYOHIN登録SQL
	 */
	private String getDtErrIfKeiryokiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO DT_ERR_IF_KEIRYOKI ");
		sql.append("	( ");
		sql.append("	 SEQNB ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,SYOHIN_YOBIDASI ");
		sql.append("	,BUNRUI2_CD ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,NETSUKEKI_NA ");
		sql.append("	,TEIKAN_KB ");
		sql.append("	,TEIKAN_TANI_KB ");
		sql.append("	,TEIKAN_WEIGHT_QT ");
		sql.append("	,FUTAI_WEIGHT_QT ");
		sql.append("	,KAKOBI_PRINT_KB ");
		sql.append("	,SYOMIKIKAN_KB ");
		sql.append("	,SYOMIKIKAN_VL ");
		sql.append("	,KAKOJIKOKU_PRINT_KB ");
		sql.append("	,SENTAKU_COMMENT_CD ");
		sql.append("	,HOZON_ONDOTAI_KB ");
		sql.append("	,REC_HINMEI_KANJI_NA ");
		sql.append("	,TRACEABILITY_FG ");
		sql.append("	,TENKABUTU_NA ");
		sql.append("	,SYOHIN_UPDATE_TS ");
		sql.append("	,ERR_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 SEQ_DT_ERR_IF_KEIRYOKI.NEXTVAL ");
		sql.append("	,WIK.BUNRUI1_CD ");
		sql.append("	,WIK.SYOHIN_CD ");
		sql.append("	,WIK.SYOHIN_YOBIDASI ");
		sql.append("	,WIK.BUNRUI2_CD ");
		sql.append("	,WIK.BUNRUI5_CD ");
		sql.append("	,WIK.NETSUKEKI_NA ");
		sql.append("	,WIK.TEIKAN_KB ");
		sql.append("	,WIK.TEIKAN_TANI_KB ");
		sql.append("	,WIK.TEIKAN_WEIGHT_QT ");
		sql.append("	,WIK.FUTAI_WEIGHT_QT ");
		sql.append("	,WIK.KAKOBI_PRINT_KB ");
		sql.append("	,WIK.SYOMIKIKAN_KB ");
		sql.append("	,WIK.SYOMIKIKAN_VL ");
		sql.append("	,WIK.KAKOJIKOKU_PRINT_KB ");
		sql.append("	,WIK.SENTAKU_COMMENT_CD ");
		sql.append("	,WIK.HOZON_ONDOTAI_KB ");
		sql.append("	,WIK.REC_HINMEI_KANJI_NA ");
		sql.append("	,WIK.TRACEABILITY_FG ");
		sql.append("	,WIK.TENKABUTU_NA ");
		sql.append("	,WIK.SYOHIN_UPDATE_TS ");
		sql.append("	,WIK.ERR_KB ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_IF_KEIRYOKI WIK ");
		sql.append("WHERE ");
		sql.append("	WIK.ERR_KB	<> '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");

		return sql.toString();
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
