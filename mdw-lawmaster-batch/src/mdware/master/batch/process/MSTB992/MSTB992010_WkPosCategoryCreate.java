package mdware.master.batch.process.MSTB992;

import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB992010_WkPosCategoryCreate.java クラス</p>
 * <p>説明　: TMP商品分類体系マスタの内容を、WK_POSラインメンテとWK_POSクラスメンテに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 *
 */
public class MSTB992010_WkPosCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK_LINE = "WK_POS_LINE"; // WK_POSラインメンテ
	private static final String TABLE_WK_CLASS = "WK_POS_CLASS"; // WK_POSクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 正式名称（漢字）開始カラム */
	private static final String KANJI_NA_START_COLUMN = "1";
	/** 正式名称（漢字）桁数 ライン */
	private static final String KANJI_NA_LENGTH_LINE = "20";
	/** 正式名称（漢字）桁数 クラス */
	private static final String KANJI_NA_LENGTH_CLASS = "28";
	/** 正式名称(カナ)開始カラム */
	private static final String KANA_NA_START_COLUMN = "1";
	/** 正式名称(カナ)桁数 */
	private static final String KANA_NA_LENGTH = "12";
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992010_WkPosCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992010_WkPosCategoryCreate() {
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

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。(バッチ処理日≠商品分類体系作成日)");

				return;
			}
			writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");

			// WK_POSラインメンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK_POSラインメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_LINE);
			writeLog(Level.INFO_INT, "WK_POSラインメンテを削除処理を終了します。");

			// WK_POSクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK_POSクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_CLASS);
			writeLog(Level.INFO_INT, "WK_POSクラスメンテを削除処理を終了します。");

			// WK_POSラインメンテの登録
			writeLog(Level.INFO_INT, "WK_POSラインメンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkPosLineInsertSql());
			writeLog(Level.INFO_INT, "WK_POSラインメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_POSラインメンテ登録処理（TMP→WK）を終了します。");

			// WK_POSクラスメンテの登録
			writeLog(Level.INFO_INT, "WK_POSクラスメンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkPosClassInsertSql());
			writeLog(Level.INFO_INT, "WK_POSクラスメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_POSクラスメンテ登録処理（TMP→WK）を終了します。");

			db.commit();

			// WK_POSラインメンテ重複削除
			writeLog(Level.INFO_INT, "WK_POSラインメンテ重複削除処理を開始します。");
			iRec = db.executeUpdate(getWkPosLineDeleteSql());
			writeLog(Level.INFO_INT, "WK_POSラインメンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "WK_POSラインメンテ重複削除処理を終了します。");


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
	 * WK_POS_LINEを登録するSQLを取得する
	 *
	 * @return WK_POS_LINE登録SQL
	 */
	private String getWkPosLineInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_POS_LINE ");
		sql.append("	( ");
		sql.append("	 BUNRUI2_CD ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,KANJI_NA ");
		sql.append("	,KANA_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 TRST.BUNRUI2_CD ");
		sql.append("	,TRST.BUNRUI1_CD ");
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH_LINE + ") ");
		sql.append("	,SUBSTRB(TRN.KANA_NA, " + KANA_NA_START_COLUMN + ", " + KANA_NA_LENGTH + ") ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	TMP_R_SYOHIN_TAIKEI TRST ");
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("			,TRN.KANA_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		sql.append("			TRST.BUNRUI2_CD	= TRN.CODE_CD ");
		sql.append("GROUP BY ");
		sql.append("	 TRST.BUNRUI2_CD ");
		sql.append("	,TRST.BUNRUI1_CD ");
		sql.append("	,TRN.KANJI_NA ");
		sql.append("	,TRN.KANA_NA ");

		return sql.toString();
	}

	/**
	 * WK_POS_CLASSを登録するSQLを取得する
	 *
	 * @return WK_POS_CLASS登録SQL
	 */
	private String getWkPosClassInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_POS_CLASS ");
		sql.append("	( ");
		sql.append("	 BUNRUI5_CD ");
		sql.append("	,BUNRUI2_CD ");
		sql.append("	,KANJI_NA ");
		sql.append("	,KANA_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 TRST.BUNRUI5_CD ");
		sql.append("	,TRST.BUNRUI2_CD ");
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH_CLASS + ") ");
		sql.append("	,SUBSTRB(TRN.KANA_NA, " + KANA_NA_START_COLUMN + ", " + KANA_NA_LENGTH + ") ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	TMP_R_SYOHIN_TAIKEI TRST ");
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("			,TRN.KANA_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		sql.append("			TRST.SYSTEM_KB || TRST.BUNRUI5_CD	= TRN.CODE_CD ");

		return sql.toString();
	}

	/**
	 * WK_POS_LINEを削除するSQLを取得する
	 *
	 * @return WK_POS_LINE削除SQL
	 */
	private String getWkPosLineDeleteSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append(" DELETE FROM WK_POS_LINE WPL 								 ");
		sql.append(" 	WHERE WPL.ROWID IN ( 									 ");
		sql.append(" 		SELECT 												 ");
		sql.append(" 			LINE_RANK.ID 									 ");
		sql.append(" 		FROM 												 ");
		sql.append(" 			( 												 ");
		sql.append(" 			SELECT 											 ");
		sql.append(" 				WPL2.ROWID AS ID, 							 ");
		sql.append(" 				RANK() OVER ( 								 ");
		sql.append(" 						PARTITION BY 						 ");
		sql.append(" 							SUBSTR(WPL2.BUNRUI2_CD, 1, 2) 	 ");
		sql.append("						 || SUBSTR(WPL2.BUNRUI2_CD, 4, 2) 	 ");
		sql.append(" 						ORDER BY 							 ");
		sql.append(" 							WPL2.BUNRUI2_CD 				 ");
		sql.append(" 						) AS RANKING 						 ");
		sql.append(" 			FROM 											 ");
		sql.append(" 				WK_POS_LINE WPL2 							 ");
		sql.append(" 			) LINE_RANK 									 ");
		sql.append(" 		WHERE 												 ");
		sql.append(" 			LINE_RANK.RANKING > 1 							 ");
		sql.append(" 	) 														 ");

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
