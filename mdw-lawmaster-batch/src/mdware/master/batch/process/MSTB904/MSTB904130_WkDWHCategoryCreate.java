package mdware.master.batch.process.MSTB904;

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
import mdware.master.common.dictionary.mst000102_IfConstDictionary;

import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB904120_IfDWHShiiresakiCreate.java クラス</p>
 * <p>説明　: TMP商品分類体系マスタの内容を、WK_DWH_カテゴリマスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 *
 */
public class MSTB904130_WkDWHCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK = "WK_DWH_CATEGORY"; // WK_DWH_カテゴリマスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 取引先名(漢字)開始カラム */
	private static final String KANJI_NA_START_COLUMN = "1";
	/** 取引先名(漢字)桁数 */
	private static final String KANJI_NA_LENGTH = "30";
	/** 取引先名(カナ)開始カラム */
	private static final String KANA_NA_START_COLUMN = "1";
	/** 取引先名(カナ)桁数 */
	private static final String KANA_NA_LENGTH = "15";
	/** ライン：ダミー値 */
	private static final String LINE_DUMMY = "     ";
	/** クラス：ダミー値 */
	private static final String CLASS_DUMMY = "     ";
	/** カテゴリ：ダミー値 */
	private static final String CATEGORY_DUMMY = "      ";
	/** 値入率：コンスト値 */
	private static final String NEIRE_RT = "0.00";
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904130_WkDWHCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904130_WkDWHCategoryCreate() {
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

			// WK_DWH_カテゴリマスタのTRUNCATE
			writeLog(Level.INFO_INT, "WK_DWH_カテゴリマスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK);
			writeLog(Level.INFO_INT, "WK_DWH_カテゴリマスタを削除処理を終了します。");

			// WK_DWH_カテゴリマスタの登録
			writeLog(Level.INFO_INT, "WK_DWH_カテゴリマスタ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkDwhCategoryInsertSql());
			writeLog(Level.INFO_INT, "WK_DWH_カテゴリマスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_DWH_カテゴリマスタ登録処理（TMP→WK）を終了します。");

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
	 * WK_DWH_CATEGORYを登録するSQLを取得する
	 *
	 * @return WK_DWH_CATEGORY登録SQL
	 */
	private String getWkDwhCategoryInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_DWH_CATEGORY ");
		sql.append("	( ");
		sql.append("	 KIGYO_1_CD ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,BUNRUI2_CD ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,CATEGORY_4_CD ");
		sql.append("	,CATEGORY_5_CD ");
		sql.append("	,CATEGORY_LEVEL ");
		sql.append("	,CATEGORY_KANJI_NA ");
		sql.append("	,CATEGORY_KANA_NA ");
		sql.append("	,NEIRE_RT ");
		sql.append("	,COMMENT_1_TX ");
		sql.append("	,COMMENT_2_TX ");
		sql.append("	,COMMENT_3_TX ");
		sql.append("	,COMMENT_4_TX ");
		sql.append("	,COMMENT_5_TX ");
		sql.append("	,DELETE_FG ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT  ");
		sql.append("	 '" + mst000102_IfConstDictionary.DWH_COMP_CD  + "' AS KIGYO_1_CD ");
		sql.append("	,TRST.BUNRUI1_CD ");
		sql.append("	,TRST.BUNRUI2_CD ");
		sql.append("	,TRST.BUNRUI5_CD ");
		sql.append("	,TRST.CATEGORY_4_CD ");
		sql.append("	,TRST.CATEGORY_5_CD ");
		sql.append("	,TRST.CATEGORY_LEVEL ");
		sql.append("	,SUBSTRB(TRST.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") AS KANJI_NA ");
		sql.append("	,SUBSTRB(TRST.KANA_NA, " + KANA_NA_START_COLUMN + ", " + KANA_NA_LENGTH + ") AS KANA_NA ");
		sql.append("	,'" + NEIRE_RT + "' AS NEIRE_RT ");
		sql.append("	,'' AS COMMENT_1_TX ");
		sql.append("	,'' AS COMMENT_2_TX ");
		sql.append("	,'' AS COMMENT_3_TX ");
		sql.append("	,'' AS COMMENT_4_TX ");
		sql.append("	,'' AS COMMENT_5_TX ");
		sql.append("	,'" + mst000102_IfConstDictionary.DWH_DELETE_KB_ACTIVE  + "' AS DELETE_FG ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			 TRIM(TRST.BUNRUI1_CD) AS BUNRUI1_CD ");
		sql.append("			,'" + LINE_DUMMY + "' AS BUNRUI2_CD ");
		sql.append("			,'" + CLASS_DUMMY + "' AS BUNRUI5_CD ");
		sql.append("			,'" + CATEGORY_DUMMY + "' AS CATEGORY_4_CD  ");
		sql.append("			,'" + CATEGORY_DUMMY + "' AS CATEGORY_5_CD  ");
		sql.append("			,'" + mst000102_IfConstDictionary.CATEGORY_LEVEL_DPT  + "' AS CATEGORY_LEVEL ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("			,TRN.KANA_NA ");
		sql.append("		FROM  ");
		sql.append("			TMP_R_SYOHIN_TAIKEI TRST ");
		sql.append("			LEFT JOIN ");
		sql.append("			( ");
		sql.append("				SELECT ");
		sql.append("					 TRN.CODE_CD ");
		sql.append("					,TRN.KANJI_NA ");
		sql.append("					,TRN.KANA_NA ");
		sql.append("				FROM ");
		sql.append("					TMP_R_NAMECTF TRN ");
		sql.append("				WHERE ");
		sql.append("					TRN.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)  + "' ");
		sql.append("			) TRN ");
		sql.append("			ON ");
		sql.append("				TRIM(TRST.BUNRUI1_CD)	= TRN.CODE_CD ");
		sql.append("		GROUP BY ");
		sql.append("			TRST.BUNRUI1_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("			,TRN.KANA_NA ");
		sql.append("	 ");
		sql.append("	UNION ");
		sql.append("		SELECT ");
		sql.append("			 TRIM(TRST.BUNRUI1_CD) AS BUNRUI1_CD ");
		sql.append("			,TRIM(TRST.BUNRUI2_CD) AS BUNRUI2_CD ");
		sql.append("			,'" + CLASS_DUMMY + "' AS BUNRUI5_CD ");
		sql.append("			,'" + CATEGORY_DUMMY + "' AS CATEGORY_4_CD  ");
		sql.append("			,'" + CATEGORY_DUMMY + "' AS CATEGORY_5_CD  ");
		sql.append("			,'" + mst000102_IfConstDictionary.CATEGORY_LEVEL_LINE  + "' AS CATEGORY_LEVEL ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("			,TRN.KANA_NA ");
		sql.append("		FROM  ");
		sql.append("			TMP_R_SYOHIN_TAIKEI TRST ");
		sql.append("			LEFT JOIN ");
		sql.append("			( ");
		sql.append("				SELECT ");
		sql.append("					 TRN.CODE_CD ");
		sql.append("					,TRN.KANJI_NA ");
		sql.append("					,TRN.KANA_NA ");
		sql.append("				FROM ");
		sql.append("					TMP_R_NAMECTF TRN ");
		sql.append("				WHERE ");
		sql.append("					TRN.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal)  + "' ");
		sql.append("			) TRN ");
		sql.append("			ON ");
		sql.append("				TRIM(TRST.BUNRUI2_CD)	= TRN.CODE_CD ");
		sql.append("		GROUP BY ");
		sql.append("			 TRST.BUNRUI1_CD ");
		sql.append("			,TRST.BUNRUI2_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("			,TRN.KANA_NA ");
		sql.append("	UNION ");
		sql.append("		SELECT ");
		sql.append("			 TRIM(TRST.BUNRUI1_CD) AS BUNRUI1_CD ");
		sql.append("			,TRIM(TRST.BUNRUI2_CD) AS BUNRUI2_CD ");
		sql.append("			,TRIM(TRST.BUNRUI5_CD) AS BUNRUI5_CD ");
		sql.append("			,'" + CATEGORY_DUMMY + "' AS CATEGORY_4_CD  ");
		sql.append("			,'" + CATEGORY_DUMMY + "' AS CATEGORY_5_CD  ");
		sql.append("			,'" + mst000102_IfConstDictionary.CATEGORY_LEVEL_CLASS  + "' AS CATEGORY_LEVEL ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("			,TRN.KANA_NA ");
		sql.append("		FROM  ");
		sql.append("			TMP_R_SYOHIN_TAIKEI TRST ");
		sql.append("			LEFT JOIN ");
		sql.append("			( ");
		sql.append("				SELECT ");
		sql.append("					 TRN.CODE_CD ");
		sql.append("					,TRN.KANJI_NA ");
		sql.append("					,TRN.KANA_NA ");
		sql.append("				FROM ");
		sql.append("					TMP_R_NAMECTF TRN ");
		sql.append("				WHERE ");
		sql.append("					TRN.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)  + "' ");
		sql.append("			) TRN ");
		sql.append("			ON ");
		sql.append("				TRST.SYSTEM_KB || TRIM(TRST.BUNRUI5_CD)	= TRN.CODE_CD ");
		sql.append("		GROUP BY ");
		sql.append("			 TRST.BUNRUI1_CD ");
		sql.append("			,TRST.BUNRUI2_CD ");
		sql.append("			,TRST.BUNRUI5_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("			,TRN.KANA_NA ");
		sql.append("	) TRST ");

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
