package mdware.master.batch.process.MSTB904;

import java.sql.SQLException;

import org.apache.log4j.Level;

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

/**
 * 
 * <p>タイトル: MSTB904140_IfDWHCategoryCreate.java クラス</p>
 * <p>説明　: WK_DWH_カテゴリマスタの内容を、IF_DWH_カテゴリマスタに取込む<br>
 *            ZEN_DWH_カテゴリマスタに登録されているが、WK_DWH_カテゴリマスタに登録されていない内容を、IF_DWH_カテゴリマスタに廃止済データとして取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.06) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 *
 */
public class MSTB904140_IfDWHCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF = "IF_DWH_CATEGORY"; // IF_DWH_カテゴリマスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** カテゴリ文字列長 */
	private static final String CATEGORY_LENGTH = "6";
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904140_IfDWHCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904140_IfDWHCategoryCreate() {
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

			// IF_DWH_カテゴリマスタのTRUNCATE
			writeLog(Level.INFO_INT, "IF_DWH_カテゴリマスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_DWH_カテゴリマスタを削除処理を終了します。");

			// IF_DWH_カテゴリマスタ(運用中)の登録
			writeLog(Level.INFO_INT, "IF_DWH_カテゴリマスタ(運用中)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfDwhCategoryInsertSql());
			writeLog(Level.INFO_INT, "IF_DWH_カテゴリマスタ(運用中)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_DWH_カテゴリマスタ(運用中)登録処理（WK→IF）を終了します。");
			db.commit();

			// IF_DWH_カテゴリマスタ(廃止)の登録
			writeLog(Level.INFO_INT, "IF_DWH_カテゴリマスタ(廃止)登録処理（BK→IF）を開始します。");
			iRec = db.executeUpdate(getIfDwhCategoryHaishiInsertSql());
			writeLog(Level.INFO_INT, "IF_DWH_カテゴリマスタ(廃止)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_DWH_カテゴリマスタ(廃止)登録処理（BK→IF）を終了します。");
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
	 * IF_DWH_CATEGORY(運用中データ)を登録するSQLを取得する
	 * 
	 * @return IF_DWH_CATEGORY(運用中データ)登録SQL
	 */
	private String getIfDwhCategoryInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_DWH_CATEGORY ");
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
		sql.append("SELECT ");
		sql.append("	 WDC.KIGYO_1_CD ");
		sql.append("	,LPAD(TRIM(WDC.BUNRUI1_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRIM(WDC.BUNRUI2_CD) IS NULL THEN WDC.BUNRUI2_CD ");
		sql.append("		ELSE LPAD(TRIM(WDC.BUNRUI2_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	 END AS BUNRUI2_CD ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRIM(WDC.BUNRUI5_CD) IS NULL THEN WDC.BUNRUI5_CD ");
		sql.append("		ELSE LPAD(TRIM(WDC.BUNRUI5_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	 END AS BUNRUI5_CD ");
		sql.append("	,WDC.CATEGORY_4_CD ");
		sql.append("	,WDC.CATEGORY_5_CD ");
		sql.append("	,WDC.CATEGORY_LEVEL ");
		sql.append("	,WDC.CATEGORY_KANJI_NA ");
		sql.append("	,WDC.CATEGORY_KANA_NA ");
		sql.append("	,WDC.NEIRE_RT ");
		sql.append("	,WDC.COMMENT_1_TX ");
		sql.append("	,WDC.COMMENT_2_TX ");
		sql.append("	,WDC.COMMENT_3_TX ");
		sql.append("	,WDC.COMMENT_4_TX ");
		sql.append("	,WDC.COMMENT_5_TX ");
		sql.append("	,'" + mst000102_IfConstDictionary.DWH_DELETE_KB_ACTIVE  + "' AS DELETE_FG ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_DWH_CATEGORY WDC ");

		return sql.toString();
	}

	/**
	 * IF_DWH_CATEGORY(廃止データ)を登録するSQLを取得する
	 * 
	 * @return IF_DWH_CATEGORY(廃止データ)登録SQL
	 */
	private String getIfDwhCategoryHaishiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_DWH_CATEGORY ");
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
		sql.append("SELECT ");
		sql.append("	 ZDC.KIGYO_1_CD ");
		sql.append("	,LPAD(TRIM(ZDC.BUNRUI1_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRIM(ZDC.BUNRUI2_CD) IS NULL THEN ZDC.BUNRUI2_CD ");
		sql.append("		ELSE LPAD(TRIM(ZDC.BUNRUI2_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	 END AS BUNRUI2_CD ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRIM(ZDC.BUNRUI5_CD) IS NULL THEN ZDC.BUNRUI5_CD ");
		sql.append("		ELSE LPAD(TRIM(ZDC.BUNRUI5_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	 END AS BUNRUI5_CD ");
		sql.append("	,ZDC.CATEGORY_4_CD ");
		sql.append("	,ZDC.CATEGORY_5_CD ");
		sql.append("	,ZDC.CATEGORY_LEVEL ");
		sql.append("	,ZDC.CATEGORY_KANJI_NA ");
		sql.append("	,ZDC.CATEGORY_KANA_NA ");
		sql.append("	,ZDC.NEIRE_RT ");
		sql.append("	,ZDC.COMMENT_1_TX ");
		sql.append("	,ZDC.COMMENT_2_TX ");
		sql.append("	,ZDC.COMMENT_3_TX ");
		sql.append("	,ZDC.COMMENT_4_TX ");
		sql.append("	,ZDC.COMMENT_5_TX ");
		sql.append("	,'" + mst000102_IfConstDictionary.DWH_DELETE_KB_HAISI  + "' AS DELETE_FG ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	ZEN_DWH_CATEGORY ZDC ");
		sql.append("WHERE ");
		sql.append("	NOT EXISTS ");
		sql.append("		( ");
		sql.append("			SELECT ");
		sql.append("				 WDC.BUNRUI1_CD ");
		sql.append("				,WDC.BUNRUI2_CD ");
		sql.append("				,WDC.BUNRUI5_CD ");
		sql.append("			FROM ");
		sql.append("				WK_DWH_CATEGORY WDC ");
		sql.append("			WHERE ");
		sql.append("				WDC.BUNRUI1_CD		= ZDC.BUNRUI1_CD		AND ");
		sql.append("				WDC.BUNRUI2_CD		= ZDC.BUNRUI2_CD		AND ");
		sql.append("				WDC.BUNRUI5_CD		= ZDC.BUNRUI5_CD		AND ");
		sql.append("				WDC.CATEGORY_LEVEL	= ZDC.CATEGORY_LEVEL ");
		sql.append("		) ");

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
