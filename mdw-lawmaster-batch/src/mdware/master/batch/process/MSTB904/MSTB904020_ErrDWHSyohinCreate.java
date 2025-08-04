package mdware.master.batch.process.MSTB904;

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
 * <p>タイトル: MSTB904020_ErrDWHSyohinCreate.java クラス</p>
 * <p>説明　: ERR_DWH_商品マスタのエラーデータの内容を、ERR_DWH_商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.08) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 *
 */
public class MSTB904020_ErrDWHSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// // テーブル
	// private static final String TABLE_WK = "DT_ERR_DWH_SYOHIN" // ERR_DWH_商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904020_ErrDWHSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904020_ErrDWHSyohinCreate() {
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

			// ERR_DWH_商品マスタの登録
			writeLog(Level.INFO_INT, "ERR_DWH_商品マスタ登録処理（WK→ERR）を開始します。");
			iRec = db.executeUpdate(getDtErrDwhSyohinInsertSql());
			writeLog(Level.INFO_INT, "ERR_DWH_商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "ERR_DWH_商品マスタ登録処理（WK→ERR）を終了します。");

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
	 * ERR_DWH_SYOHINを登録するSQLを取得する
	 * 
	 * @return ERR_DWH_SYOHIN登録SQL
	 */
	private String getDtErrDwhSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO DT_ERR_DWH_SYOHIN ");
		sql.append("	( ");
		sql.append("	 KIGYO_1_CD ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,SEQ_NB ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,BUNRUI2_CD ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,CATEGORY_4_CD ");
		sql.append("	,CATEGORY_5_CD ");
		sql.append("	,HINMEI_KANJI_NA ");
		sql.append("	,HINMEI_KANA_NA ");
		sql.append("	,GENTANKA_VL ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,NEIRE_RT ");
		sql.append("	,MAKER_CD ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,ATSUKAI_ST_DT ");
		sql.append("	,ATSUKAI_ED_DT ");
		sql.append("	,ZOKUSEI_1_KB ");
		sql.append("	,ZOKUSEI_2_KB ");
		sql.append("	,ZOKUSEI_3_KB ");
		sql.append("	,ZOKUSEI_4_KB ");
		sql.append("	,ZOKUSEI_5_KB ");
		sql.append("	,COMMENT_1_TX ");
		sql.append("	,COMMENT_2_TX ");
		sql.append("	,COMMENT_3_TX ");
		sql.append("	,COMMENT_4_TX ");
		sql.append("	,COMMENT_5_TX ");
		sql.append("	,DELETE_FG ");
		sql.append("	,ERR_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 WDS.KIGYO_1_CD ");
		sql.append("	,WDS.SYOHIN_CD ");
		sql.append("	,SEQ_DT_ERR_DWH_SYOHIN.NEXTVAL ");
		sql.append("	,WDS.BUNRUI1_CD ");
		sql.append("	,WDS.BUNRUI2_CD ");
		sql.append("	,WDS.BUNRUI5_CD ");
		sql.append("	,WDS.CATEGORY_4_CD ");
		sql.append("	,WDS.CATEGORY_5_CD ");
		sql.append("	,WDS.HINMEI_KANJI_NA ");
		sql.append("	,WDS.HINMEI_KANA_NA ");
		sql.append("	,WDS.GENTANKA_VL ");
		sql.append("	,WDS.BAITANKA_VL ");
		sql.append("	,WDS.NEIRE_RT ");
		sql.append("	,WDS.MAKER_CD ");
		sql.append("	,WDS.SIIRESAKI_CD ");
		sql.append("	,WDS.ATSUKAI_ST_DT ");
		sql.append("	,WDS.ATSUKAI_ED_DT ");
		sql.append("	,WDS.ZOKUSEI_1_KB ");
		sql.append("	,WDS.ZOKUSEI_2_KB ");
		sql.append("	,WDS.ZOKUSEI_3_KB ");
		sql.append("	,WDS.ZOKUSEI_4_KB ");
		sql.append("	,WDS.ZOKUSEI_5_KB ");
		sql.append("	,WDS.COMMENT_1_TX ");
		sql.append("	,WDS.COMMENT_2_TX ");
		sql.append("	,WDS.COMMENT_3_TX ");
		sql.append("	,WDS.COMMENT_4_TX ");
		sql.append("	,WDS.COMMENT_5_TX ");
		sql.append("	,WDS.DELETE_FG ");
		sql.append("	,WDS.ERR_KB ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_DWH_SYOHIN WDS ");
		sql.append("WHERE ");
		sql.append("	WDS.ERR_KB	<> '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");

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
