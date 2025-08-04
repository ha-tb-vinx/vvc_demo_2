package mdware.master.batch.process.MSTB905;

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
 * <p>タイトル: MSTB905020_ErrVanSyohinCreate.java クラス</p>
 * <p>説明　: ERR_VAN_商品マスタのエラーデータの内容を、ERR_VAN_商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.20) T.Ooshiro [CUS00102] レクサスEDIインターフェイス仕様変更対応（マスタ）対応
 *
 */
public class MSTB905020_ErrVanSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// // テーブル
	// private static final String TABLE_WK = "DT_ERR_VAN_SYOHIN" // ERR_VAN_商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB905020_ErrVanSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB905020_ErrVanSyohinCreate() {
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

			// ERR_VAN_商品マスタの登録
			writeLog(Level.INFO_INT, "ERR_VAN_商品マスタ登録処理（WK→ERR）を開始します。");
			iRec = db.executeUpdate(getDtErrVanSyohinInsertSql());
			writeLog(Level.INFO_INT, "ERR_VAN_商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "ERR_VAN_商品マスタ登録処理（WK→ERR）を終了します。");

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
	private String getDtErrVanSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO DT_ERR_VAN_SYOHIN ");
		sql.append("	( ");
		sql.append("	 SIIRESAKI_CD ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,SEQ_NB ");
		sql.append("	,REC_HINMEI_KANJI_NA ");
		sql.append("	,KIKAKU_KANJI_NA ");
		sql.append("	,HINMEI_KANA_NA ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,HACHUTANI_IRISU_QT ");
		sql.append("	,GENTANKA_VL ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,ERR_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 WVS.SIIRESAKI_CD ");
		sql.append("	,WVS.SYOHIN_CD ");
		sql.append("	,SEQ_DT_ERR_VAN_SYOHIN.NEXTVAL ");
		sql.append("	,WVS.REC_HINMEI_KANJI_NA ");
		sql.append("	,WVS.KIKAKU_KANJI_NA ");
		sql.append("	,WVS.HINMEI_KANA_NA ");
		sql.append("	,WVS.BUNRUI1_CD ");
		sql.append("	,WVS.HACHUTANI_IRISU_QT ");
		sql.append("	,WVS.GENTANKA_VL ");
		sql.append("	,WVS.BAITANKA_VL ");
		sql.append("	,WVS.BUNRUI5_CD ");
		sql.append("	,WVS.ERR_KB ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_VAN_SYOHIN WVS ");
		sql.append("WHERE ");
		sql.append("	WVS.ERR_KB	<> '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");

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
