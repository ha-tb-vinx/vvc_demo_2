package mdware.master.batch.process.MSTB904;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB904030_IfDWHSyohinCreate.java クラス</p>
 * <p>説明　: ZEN_IF_DWH_商品マスタのエラーデータの内容を、IF_DWH_商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.08) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 *
 */
public class MSTB904030_IfDWHSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF = "IF_DWH_SYOHIN"; // IF_DWH_商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904030_IfDWHSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904030_IfDWHSyohinCreate() {
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

			// IF_DWH_商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "IF_DWH_商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_DWH_商品マスタを削除処理を終了します。");

			// IF_DWH_商品マスタの登録
			writeLog(Level.INFO_INT, "IF_DWH_商品マスタ登録処理（ZEN_IF→IF）を開始します。");
			iRec = db.executeUpdate(getIfDwhSyohinInsertSql());
			writeLog(Level.INFO_INT, "IF_DWH_商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_DWH_商品マスタ登録処理（ZEN_IF→IF）を終了します。");

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
	 * IF_DWH_SYOHINを登録するSQLを取得する
	 * 
	 * @return IF_DWH_SYOHIN登録SQL
	 */
	private String getIfDwhSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_DWH_SYOHIN ");
		sql.append("	( ");
		sql.append("	 KIGYO_1_CD ");
		sql.append("	,SYOHIN_CD ");
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
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 ZIDS.KIGYO_1_CD ");
		sql.append("	,ZIDS.SYOHIN_CD ");
		sql.append("	,ZIDS.BUNRUI1_CD ");
		sql.append("	,ZIDS.BUNRUI2_CD ");
		sql.append("	,ZIDS.BUNRUI5_CD ");
		sql.append("	,ZIDS.CATEGORY_4_CD ");
		sql.append("	,ZIDS.CATEGORY_5_CD ");
		sql.append("	,ZIDS.HINMEI_KANJI_NA ");
		sql.append("	,ZIDS.HINMEI_KANA_NA ");
		sql.append("	,ZIDS.GENTANKA_VL ");
		sql.append("	,ZIDS.BAITANKA_VL ");
		sql.append("	,ZIDS.NEIRE_RT ");
		sql.append("	,ZIDS.MAKER_CD ");
		sql.append("	,ZIDS.SIIRESAKI_CD ");
		sql.append("	,ZIDS.ATSUKAI_ST_DT ");
		sql.append("	,ZIDS.ATSUKAI_ED_DT ");
		sql.append("	,ZIDS.ZOKUSEI_1_KB ");
		sql.append("	,ZIDS.ZOKUSEI_2_KB ");
		sql.append("	,ZIDS.ZOKUSEI_3_KB ");
		sql.append("	,ZIDS.ZOKUSEI_4_KB ");
		sql.append("	,ZIDS.ZOKUSEI_5_KB ");
		sql.append("	,ZIDS.COMMENT_1_TX ");
		sql.append("	,ZIDS.COMMENT_2_TX ");
		sql.append("	,ZIDS.COMMENT_3_TX ");
		sql.append("	,ZIDS.COMMENT_4_TX ");
		sql.append("	,ZIDS.COMMENT_5_TX ");
		sql.append("	,ZIDS.DELETE_FG ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	ZEN_IF_DWH_SYOHIN ZIDS ");

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
