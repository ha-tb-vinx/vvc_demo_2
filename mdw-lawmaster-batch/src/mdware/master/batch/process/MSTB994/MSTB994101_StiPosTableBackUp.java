package mdware.master.batch.process.MSTB994;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB994101_StiPosTableBackUp.java クラス</p>
 * <p>説明　: IF_指定日POS部門メンテ、IF_指定日POSデプトメンテ、IF_指定日POSクラスメンテ、IF_指定日POSサブクラスメンテ、
 * <br>IF_指定日PLU単品メンテ、IF_指定日POS支払種別メンテ、IF_指定日POS特売種別メンテ　のバックアップを作成する。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.01.16) 新規作成 #1749対応 T.Han
 * @version 1.01 (2017.06.05) #5009対応 S.Takayama
 *
 */
public class MSTB994101_StiPosTableBackUp {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	private static final String IF_STI_PLU_TANPIN = "IF_STI_PLU_TANPIN"; 				// IF_指定日_PLU単品メンテ
	private static final String BK_IF_STI_PLU_TANPIN = "BK_IF_STI_PLU_TANPIN"; 			// BK_IF_指定日_PLU単品メンテ
	private static final String IF_STI_POS_PAYMENT = "IF_STI_POS_PAYMENT"; 				// IF_指定日_POS支払種別メンテ
	private static final String BK_IF_STI_POS_PAYMENT = "BK_IF_STI_POS_PAYMENT"; 		// BK_IF_指定日_POS支払種別メンテ
	private static final String IF_STI_POS_DISCOUNT = "IF_STI_POS_DISCOUNT"; 			// IF_指定日_POS特売種別メンテ
	private static final String BK_IF_STI_POS_DISCOUNT = "BK_IF_STI_POS_DISCOUNT"; 		// BK_IF_指定日_POS特売種別メンテ
    private static final String IF_STI_POS_BUMON = "IF_STI_POS_BUMON"; 					// IF_指定日_POS部門メンテ
	private static final String BK_IF_STI_POS_BUMON = "BK_IF_STI_POS_BUMON"; 			// BK_IF_指定日_POS部門メンテ
	private static final String IF_STI_POS_DPT = "IF_STI_POS_DPT"; 						// IF_指定日_POSデプトメンテ
	private static final String BK_IF_STI_POS_DPT = "BK_IF_STI_POS_DPT"; 				// BK_IF_指定日_POSデプトメンテ
	private static final String IF_STI_POS_CLASS_FIVI = "IF_STI_POS_CLASS_FIVI"; 		// IF_指定日_POSクラスメンテ
	private static final String BK_IF_STI_POS_CLASS_FIVI = "BK_IF_STI_POS_CLASS_FIVI"; 	// BK_IF_指定日_POSクラスメンテ
	private static final String IF_STI_POS_SUBCLASS = "IF_STI_POS_SUBCLASS"; 			// IF_指定日_POSサブクラスメンテ
	private static final String BK_IF_STI_POS_SUBCLASS = "BK_IF_STI_POS_SUBCLASS"; 		// BK_IF_指定日_POSサブクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB994101_StiPosTableBackUp(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB994101_StiPosTableBackUp() {
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

			// IF_指定日_PLU単品メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_指定日_PLU単品メンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #5009 Add 2017.06.05 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_STI_PLU_TANPIN, BK_IF_STI_PLU_TANPIN));
			iRec = db.executeUpdate(getAllInsertSQL(IF_STI_PLU_TANPIN, BK_IF_STI_PLU_TANPIN, batchDate));
			// #5009 Add 2017.06.05 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_指定日_PLU単品メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_指定日_PLU単品メンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_指定日_POS支払種別メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_指定日_POS支払種別メンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #5009 Add 2017.06.05 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_PAYMENT, BK_IF_STI_POS_PAYMENT));
			iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_PAYMENT, BK_IF_STI_POS_PAYMENT, batchDate));
			// #5009 Add 2017.06.05 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_指定日_POS支払種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POS支払種別メンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_指定日_POS特売種別メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_指定日_POS特売種別メンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #5009 Add 2017.06.05 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_DISCOUNT, BK_IF_STI_POS_DISCOUNT));
			iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_DISCOUNT, BK_IF_STI_POS_DISCOUNT, batchDate));
			// #5009 Add 2017.06.05 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_指定日_POS特売種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POS特売種別メンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_指定日_POS部門メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_指定日_POS部門メンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #5009 Add 2017.06.05 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_BUMON, BK_IF_STI_POS_BUMON));
			iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_BUMON, BK_IF_STI_POS_BUMON, batchDate));
			// #5009 Add 2017.06.05 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_指定日_POS部門メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POS部門メンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_指定日_POSデプトメンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_指定日_POSデプトメンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #5009 Add 2017.06.05 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_DPT, BK_IF_STI_POS_DPT));
			iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_DPT, BK_IF_STI_POS_DPT, batchDate));
			// #5009 Add 2017.06.05 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_指定日_POSデプトメンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POSデプトメンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_指定日_POSクラスメンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_指定日_POSクラスメンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #5009 Add 2017.06.05 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_CLASS_FIVI, BK_IF_STI_POS_CLASS_FIVI));
			iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_CLASS_FIVI, BK_IF_STI_POS_CLASS_FIVI, batchDate));
			// #5009 Add 2017.06.05 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_指定日_POSクラスメンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POSクラスメンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_指定日_POSサブクラスメンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_指定日_POSサブクラスメンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #5009 Add 2017.06.05 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_SUBCLASS, BK_IF_STI_POS_SUBCLASS));
			iRec = db.executeUpdate(getAllInsertSQL(IF_STI_POS_SUBCLASS, BK_IF_STI_POS_SUBCLASS, batchDate));
			// #5009 Add 2017.06.05 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_指定日_POSサブクラスメンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POSサブクラスメンテのバックアップ処理（IF→BK_IF）を終了します。");

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
	 * @return SQL
	 * @throws Exception
	 */
	// #5009 Add 2017.06.05 S.Takayama (S)
	//private String getAllInsertSQL(String tableIdFrom, String tableIdTo) throws Exception {
	private String getAllInsertSQL(String tableIdFrom, String tableIdTo, String batchDt) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
	// #5009 Add 2017.06.05 S.Takayama (E)

		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + tableIdTo + " ");
		strSql.append("SELECT ");
		// #5009 Add 2017.06.05 S.Takayama (S)
		strSql.append("	'" + batchDt + "' ");
		strSql.append("	,'" + systemDt + "', ");
		// #5009 Add 2017.06.05 S.Takayama (E)
		strSql.append("	" + tableIdFrom + ".* ");
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
