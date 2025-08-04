package mdware.master.batch.process.MSTB992;

import java.nio.CharBuffer;
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
 * <p>タイトル: MSTB992011_WkPosCategoryCreate.java クラス</p>
 * <p>説明　: TMP商品分類体系マスタの内容を、WK_POS部門メンテ、WK_POSデプトメンテ、WK_POSクラスメンテ、WK_POSサブクラスメンテに取込む</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2015.08.05) DAI.BQ FIVImart様対応
 * @version 1.01 (2016.04.04) Huy.NT 設計書No.651(#1211) FIVIMART対応
 */
public class MSTB992011_WkPosCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK_POS_BUMON = "WK_POS_BUMON"; // WK_POS部門メンテ
	private static final String TABLE_WK_POS_DPT = "WK_POS_DPT"; // WKPOSデプトメンテ
	private static final String TABLE_WK_POS_CLASS_FIVI = "WK_POS_CLASS_FIVI"; // WK_POSクラスメンテ
	private static final String TABLE_WK_POS_SUBCLASS = "WK_POS_SUBCLASS"; // WK_POSサブクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	// No.651 MSTB992 Del 2016.04.04 Huy.NT (S)
	/** Prime Group桁数 ライン */
	/*private static final int PRIME_GROUP_LENGTH = 3;*/
	// No.651 MSTB992 Del 2016.04.04 Huy.NT (E)
	/** Prime Group 名桁数 ライン */
	private static final int PRIME_GROUP_NA_LENGTH = 30;
	/** 正式名称（漢字）開始カラム */
	private static final String KANJI_NA_START_COLUMN = "1";
	/** 正式名称（漢字）桁数 ライン */
	private static final String KANJI_NA_LENGTH = "30";
	/** Class名 ライン */
	private static final String CLASS_NA_LENGTH = "40";
	/** 処理日間隔*/
	private static final int SPAN_DAYS = 1;
	// No.651 MSTB992 Mod 2016.04.04 Huy.NT (S)
	/** （POS）金額変更／値引ボタンフラグ*/
	/*private static final String CHG_VL_NEBIKI_BTN_FG = "0";*/
	private static final String CHG_VL_NEBIKI_BTN_FG = "3";
	// No.651 MSTB992 Mod 2016.04.04 Huy.NT (E)
	/** 会員カード値引率*/
	private static final String KAIIN_CARD_NEBIKI_RT = "000";
	/** マタニティカード値引率*/
	private static final String MATERNITY_CARD_NEBIKI_RT = "000";
	/** 新規・訂正 登録ID */
	private static final String DEPARTMENT_TYPE = "D";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992011_WkPosCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992011_WkPosCategoryCreate() {
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

			// WK_POS部門メンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK_POS部門メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_POS_BUMON);
			writeLog(Level.INFO_INT, "WK_POS部門メンテを削除処理を終了します。");

			// WK_POSデプトメンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK_POSデプトメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_POS_DPT);
			writeLog(Level.INFO_INT, "WK_POSデプトメンテを削除処理を終了します。");

			// WK_POSクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK_POSクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_POS_CLASS_FIVI);
			writeLog(Level.INFO_INT, "WK_POSクラスメンテを削除処理を終了します。");

			// WK_POSサブクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK_POSサブクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_POS_SUBCLASS);
			writeLog(Level.INFO_INT, "WK_POSサブクラスメンテを削除処理を終了します。");

			// WK_POS部門メンテの登録
			writeLog(Level.INFO_INT, "WK_POS部門メンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkPosBumonInsertSql());
			writeLog(Level.INFO_INT, "WK_POS部門メンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_POS部門メンテ登録処理（TMP→WK）を終了します。");

			// WK_POSデプトメンテの登録
			writeLog(Level.INFO_INT, "WK_POSデプトメンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkPosDPTInsertSql());
			writeLog(Level.INFO_INT, "WK_POSデプトメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_POSデプトメンテ登録処理（TMP→WK）を終了します。");

			// WK_POSクラスメンテの登録
			writeLog(Level.INFO_INT, "WK_POSクラスメンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkPosClassFiviInsertSql());
			writeLog(Level.INFO_INT, "WK_POSクラスメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_POSクラスメンテ登録処理（TMP→WK）を終了します。");

			// WK_POSサブクラスメンテの登録
			writeLog(Level.INFO_INT, "WK_POSサブクラスメンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkPosSubClassInsertSql());
			writeLog(Level.INFO_INT, "WK_POSサブクラスメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_POSサブクラスメンテ登録処理（TMP→WK）を終了します。");

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
	 *WKPOS部門メンテを登録するSQLを取得する
	 *
	 * @return WKPOS部門メンテ登録SQL
	 */
	private String getWkPosBumonInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_POS_BUMON ");
		sql.append("	( ");
		sql.append("	 DIVISION_CD ");
		sql.append("	,DIVISION_NA ");
		sql.append("	,PRIME_GROUP ");
		sql.append("	,PRIME_GROUP_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 SUBSTRB(LTRIM(TRST.DAIBUNRUI2_CD, '0'), 1, 3) AS DIVISION_CD");
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") AS DIVISION_NA ");
		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (S)
		/*sql.append("	,'" + spaces(PRIME_GROUP_LENGTH) + "' AS PRIME_GROUP ") ;*/
		sql.append("	,SUBSTRB(LTRIM(TRST.DAIBUNRUI1_CD, '0'), 1, 3) AS PRIME_GROUP");
		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (E)
		sql.append("	,'" + spaces(PRIME_GROUP_NA_LENGTH) + "' AS PRIME_GROUP_NA ");
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
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.DAIBUNRUI2, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		sql.append("			TRIM(TRST.DAIBUNRUI2_CD) = TRN.CODE_CD ");
		sql.append("GROUP BY ");
		sql.append("	 TRST.DAIBUNRUI2_CD ");
		// No.651 MSTB992 Add 2016.04.11 Huy.NT (S)
		sql.append("	,TRST.DAIBUNRUI1_CD ");
		// No.651 MSTB992 Add 2016.04.11 Huy.NT (E)
		sql.append("	,TRN.KANJI_NA ");

		return sql.toString();
	}

	/**
	 * WK_POS_DPTを登録するSQLを取得する
	 *
	 * @return WK_POS_DPT登録SQL
	 */
	private String getWkPosDPTInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_POS_DPT ");
		sql.append("	( ");
		sql.append("	 DIVISION_CD ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,DEPARTMENT_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 SUBSTRB(LTRIM(TRST.DAIBUNRUI2_CD, '0'), 1, 3) AS DIVISION_CD");
		sql.append("	,SUBSTRB(LTRIM(TRST.BUNRUI1_CD, '0'), 1, 3) AS DEPARTMENT_CD ");
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") AS DEPARTMENT_NA ");
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
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		sql.append("			TRIM(TRST.BUNRUI1_CD)	= TRN.CODE_CD ");
		sql.append("GROUP BY ");
		sql.append("	 TRST.BUNRUI1_CD	 ");
		sql.append("	,TRST.DAIBUNRUI2_CD ");
		sql.append("	,TRN.KANJI_NA ");

		return sql.toString();
	}

	/**
	 * WK_POS_CLASS_FIVIを登録するSQLを取得する
	 *
	 * @return WK_POS_CLASS_FIVI登録SQL
	 */
	private String getWkPosClassFiviInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_POS_CLASS_FIVI ");
		sql.append("	( ");
		sql.append("	 DEPARTMENT_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,CLASS_NA ");
		sql.append("	,DEPARTMENT_TYPE ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 SUBSTRB(LTRIM(TRST.BUNRUI1_CD, '0'), 1, 3 ) AS DEPARTMENT_CD");
		sql.append("	,LTRIM(TRST.BUNRUI2_CD, '0') AS CLASS_CD ");
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + CLASS_NA_LENGTH + ") AS CLASS_NA ");
		sql.append("	,'" + DEPARTMENT_TYPE + "' AS DEPARTMENT_TYPE ");
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
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		sql.append("			TRIM(TRST.BUNRUI2_CD)	= TRN.CODE_CD ");
		sql.append("GROUP BY ");
		sql.append("	 TRST.BUNRUI2_CD	 ");
		sql.append("	,TRST.BUNRUI1_CD ");
		sql.append("	,TRN.KANJI_NA ");

		return sql.toString();
	}

	/**
	 * WK_POS_SUBCLASSを登録するSQLを取得する
	 *
	 * @return WK_POS_SUBCLASS登録SQL
	 */
	private String getWkPosSubClassInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_POS_SUBCLASS ");
		sql.append("	( ");
		sql.append("	 CLASS_CD ");
		sql.append("	,SUBCLASS_CD ");
		sql.append("	,SUBCLASS_NA ");
		sql.append("	,AEON_CARD_NEBIKI_FG ");
		sql.append("	,CHG_VL_NEBIKI_BTN_FG ");
		sql.append("	,KAIIN_CARD_NEBIKI_RT ");
		sql.append("	,MATERNITY_CARD_NEBIKI_RT ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 LTRIM(TRST.BUNRUI2_CD,'0') AS CLASS_CD ");
		sql.append("	,LTRIM(TRST.BUNRUI5_CD,'0') AS SUBCLASS_CD ");
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") AS SUBCLASS_NA ");
		sql.append("	,' ' AS AEON_CARD_NEBIKI_FG ");
		sql.append("	,'" + CHG_VL_NEBIKI_BTN_FG + "' AS CHG_VL_NEBIKI_BTN_FG ");
		sql.append("	,'" + KAIIN_CARD_NEBIKI_RT + "' AS KAIIN_CARD_NEBIKI_RT ");
		sql.append("	,'" + MATERNITY_CARD_NEBIKI_RT + "' AS MATERNITY_CARD_NEBIKI_RT ");
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
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		sql.append("			TRIM(CONCAT(TRST.SYSTEM_KB, TRST.BUNRUI5_CD))	= TRN.CODE_CD ");

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

	/**
	 * @param spaces
	 * @return String
	 */
	private String spaces(int spaces){
		return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
	}

}
