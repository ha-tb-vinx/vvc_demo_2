package mdware.master.batch.process.MSTB904;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB904190_DWHTableBackUp.java クラス</p>
 * <p>説明　: IF_DWH商品マスタ、IF_DWH店別商品マスタ、IF_DWH仕入先マスタ、<br>
 *            IF_DWH店舗マスタ、IF_DWHカテゴリマスタのバックアップを行う。</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.13) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 *
 */
public class MSTB904190_DWHTableBackUp {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String IF_DWH_SYOHIN = "IF_DWH_SYOHIN"; // IF_DWH_商品マスタ
	private static final String BK_IF_DWH_SYOHIN = "BK_IF_DWH_SYOHIN"; // BK_IF_DWH_商品マスタ
	private static final String IF_DWH_TENBETU_SYOHIN = "IF_DWH_TENBETU_SYOHIN"; // IF_DWH_店別商品マスタ
	private static final String BK_IF_DWH_TENBETU_SYOHIN = "BK_IF_DWH_TENBETU_SYOHIN"; // BK_IF_DWH_店別商品マスタ
	private static final String IF_DWH_TENPO = "IF_DWH_TENPO"; // IF_DWH_店舗マスタ
	private static final String BK_IF_DWH_TENPO = "BK_IF_DWH_TENPO"; // BK_IF_DWH_店舗マスタ
	private static final String IF_DWH_SHIIRESAKI = "IF_DWH_SHIIRESAKI"; // IF_DWH_仕入先マスタ
	private static final String BK_IF_DWH_SHIIRESAKI = "BK_IF_DWH_SHIIRESAKI"; // BK_IF_DWH_仕入先マスタ
	private static final String IF_DWH_CATEGORY = "IF_DWH_CATEGORY"; // IF_DWH_カテゴリマスタ
	private static final String BK_IF_DWH_CATEGORY = "BK_IF_DWH_CATEGORY"; // BK_IF_DWH_カテゴリマスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904190_DWHTableBackUp(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904190_DWHTableBackUp() {
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

			// IF_DWH商品マスタのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_DWH商品マスタのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(IF_DWH_SYOHIN, BK_IF_DWH_SYOHIN, batchDate));
			writeLog(Level.INFO_INT, "BK_IF_DWH商品マスタへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_DWH商品マスタマスタのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_DWH店別商品マスタのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_DWH店別商品マスタのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(IF_DWH_TENBETU_SYOHIN, BK_IF_DWH_TENBETU_SYOHIN, batchDate));
			writeLog(Level.INFO_INT, "BK_IF_DW店別H商品マスタへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_DWH店別商品マスタマスタのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_DWH店舗マスタのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_DWH店舗マスタのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(IF_DWH_TENPO, BK_IF_DWH_TENPO, batchDate));
			writeLog(Level.INFO_INT, "BK_IF_DW店舗マスタへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_DWH店舗マスタマスタのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_DWH仕入先マスタのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_DWH仕入先マスタのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(IF_DWH_SHIIRESAKI, BK_IF_DWH_SHIIRESAKI, batchDate));
			writeLog(Level.INFO_INT, "BK_IF_DW仕入先マスタへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_DWH仕入先マスタマスタのバックアップ処理（IF→BK_IF）を終了します。");

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。(バッチ処理日≠商品分類体系作成日)");
			} else {
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");

				// IF_DWHカテゴリマスタのバックアップ（IF→BK_IF）
				writeLog(Level.INFO_INT, "IF_DWHカテゴリマスタのバックアップ処理（IF→BK_IF）を開始します。");
				iRec = db.executeUpdate(getAllInsertSQL(IF_DWH_CATEGORY, BK_IF_DWH_CATEGORY, batchDate));
				writeLog(Level.INFO_INT, "BK_IF_DWカテゴリマスタへ" + iRec + "件登録しました。");
				writeLog(Level.INFO_INT, "IF_DWHカテゴリマスタマスタのバックアップ処理（IF→BK_IF）を終了します。");
			}

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
	 * 全件追加用SQLを生成する。
	 * @param tableIdFrom	追加元テーブル
	 * @param tableIdTo		追加先テーブル
	 * @param batchDt		バッチ日付
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllInsertSQL(String tableIdFrom, String tableIdTo, String batchDt) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + tableIdTo + " ");
		strSql.append("SELECT ");
		strSql.append("	'" + batchDt + "' ");
		strSql.append("	,'" + systemDt + "' ");
		strSql.append("	," + tableIdFrom + ".* ");
		strSql.append("FROM " + tableIdFrom + " ");

		return strSql.toString();
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
