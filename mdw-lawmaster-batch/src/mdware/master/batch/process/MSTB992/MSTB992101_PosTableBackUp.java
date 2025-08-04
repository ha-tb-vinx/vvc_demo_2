package mdware.master.batch.process.MSTB992;

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
 * <p>タイトル: MSTB992101_PosTableBackUp.java クラス</p>
 * <p>説明　: IF_POS部門メンテ、IF_POSデプトメンテ、IF_POSクラスメンテ、IF_POSサブクラスメンテ、IF_PLU単品メンテのバックアップを作成する。</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2015.08.10) DAI.BQ FIVImart様対応
 * @version 1.01 (2016.09.12) #1748対応 VINX t.han
 * @version 1.02 (2017.04.11) #4582対応 VINX S.Takayama
 * @version 1.03 (2017.06.05) #5009対応 VINX S.Takayama
 *
 */
public class MSTB992101_PosTableBackUp {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String IF_TEC_PLU = "IF_TEC_PLU"; // IF_PLU単品メンテ
	//add 2016.08.31 nv.cuong(S)
	private static final String IF_PLU_TANPIN = "IF_PLU_TANPIN"; // IF_PLU単品メンテ
	private static final String BK_IF_PLU_TANPIN = "BK_IF_PLU_TANPIN"; // BK_IF_PLU単品メンテ
	//add 2016.08.31 nv.cuong(E)
    // 2016/09/12 VINX t.han #1748対応（S)
	private static final String IF_POS_PAYMENT = "IF_POS_PAYMENT"; // IF_POS支払種別メンテ
	private static final String BK_IF_POS_PAYMENT = "BK_IF_POS_PAYMENT"; // BK_IF_POS支払種別メンテ
	private static final String IF_POS_DISCOUNT = "IF_POS_DISCOUNT"; // IF_POS特売種別メンテ
	private static final String BK_IF_POS_DISCOUNT = "BK_IF_POS_DISCOUNT"; // BK_IF_POS特売種別メンテ
    // 2016/09/12 VINX t.han #1748対応（E)
	private static final String BK_IF_TEC_PLU = "BK_IF_TEC_PLU"; // BK_IF_PLU単品メンテ
	private static final String IF_POS_BUMON = "IF_POS_BUMON"; // IF_POS部門メンテ
	private static final String BK_IF_POS_BUMON = "BK_IF_POS_BUMON"; // BK_IF_POS部門メンテ
	private static final String IF_POS_DPT = "IF_POS_DPT"; // IF_POSデプトメンテ
	private static final String BK_IF_POS_DPT = "BK_IF_POS_DPT"; // BK_IF_POSデプトメンテ
	private static final String IF_POS_CLASS_FIVI = "IF_POS_CLASS_FIVI"; // IF_POSクラスメンテ
	private static final String BK_IF_POS_CLASS_FIVI = "BK_IF_POS_CLASS_FIVI"; // BK_IF_POSクラスメンテ
	private static final String IF_POS_SUBCLASS = "IF_POS_SUBCLASS"; // IF_POSサブクラスメンテ
	private static final String BK_IF_POS_SUBCLASS = "BK_IF_POS_SUBCLASS"; // BK_IF_POSサブクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992101_PosTableBackUp(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992101_PosTableBackUp() {
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

			// IF_PLU単品メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_PLU単品メンテのバックアップ処理（IF→BK_IF）を開始します。");
			//add 2016.08.31 nv.cuong(S)
			//iRec = db.executeUpdate(getInsertTecBluSQL(IF_TEC_PLU, BK_IF_TEC_PLU, batchDate));
			iRec = db.executeUpdate(getInsertTecBluSQL(IF_PLU_TANPIN, BK_IF_PLU_TANPIN, batchDate));
			//add 2016.08.31 nv.cuong(E)
			writeLog(Level.INFO_INT, "BK_IF_PLU単品メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_PLU単品メンテのバックアップ処理（IF→BK_IF）を終了します。");

		    // 2016/09/12 VINX t.han #1748対応（S)
			// IF_POS支払種別メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_POS支払種別メンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #4582 MSTB992101 2017.04.11 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_POS_PAYMENT, BK_IF_POS_PAYMENT));
			iRec = db.executeUpdate(getInsertTecBluSQL(IF_POS_PAYMENT, BK_IF_POS_PAYMENT, batchDate));
			// #4582 MSTB992101 2017.04.11 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_POS支払種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_POS支払種別メンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_POS特売種別メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_POS特売種別メンテのバックアップ処理（IF→BK_IF）を開始します。");
			// #4582 MSTB992101 2017.04.11 S.Takayama (S)
			//iRec = db.executeUpdate(getAllInsertSQL(IF_POS_DISCOUNT, BK_IF_POS_DISCOUNT));
			iRec = db.executeUpdate(getInsertTecBluSQL(IF_POS_DISCOUNT, BK_IF_POS_DISCOUNT, batchDate));
			// #4582 MSTB992101 2017.04.11 S.Takayama (E)
			writeLog(Level.INFO_INT, "BK_IF_POS特売種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_POS特売種別メンテのバックアップ処理（IF→BK_IF）を終了します。");
		    // 2016/09/12 VINX t.han #1748対応（E)

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。(バッチ処理日≠商品分類体系作成日)");
			} else {
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");

				// IF_POS部門メンテのバックアップ（IF→BK_IF）
				writeLog(Level.INFO_INT, "IF_POS部門メンテのバックアップ処理（IF→BK_IF）を開始します。");
				// #5009 Add 2017.06.05 S.Takayama (S)
				//iRec = db.executeUpdate(getAllInsertSQL(IF_POS_BUMON, BK_IF_POS_BUMON));
				iRec = db.executeUpdate(getAllInsertSQL(IF_POS_BUMON, BK_IF_POS_BUMON, batchDate));
				// #5009 Add 2017.06.05 S.Takayama (E)
				writeLog(Level.INFO_INT, "BK_IF_POS部門メンテへ" + iRec + "件登録しました。");
				writeLog(Level.INFO_INT, "IF_POS部門メンテのバックアップ処理（IF→BK_IF）を終了します。");

				// IF_POSデプトメンテのバックアップ（IF→BK_IF）
				writeLog(Level.INFO_INT, "IF_POSデプトメンテのバックアップ処理（IF→BK_IF）を開始します。");
				// #5009 Add 2017.06.05 S.Takayama (S)
				//iRec = db.executeUpdate(getAllInsertSQL(IF_POS_DPT, BK_IF_POS_DPT));
				iRec = db.executeUpdate(getAllInsertSQL(IF_POS_DPT, BK_IF_POS_DPT, batchDate));
				// #5009 Add 2017.06.05 S.Takayama (E)
				writeLog(Level.INFO_INT, "BK_IF_POSデプトメンテへ" + iRec + "件登録しました。");
				writeLog(Level.INFO_INT, "IF_POSデプトメンテのバックアップ処理（IF→BK_IF）を終了します。");

				// IF_POSクラスメンテのバックアップ（IF→BK_IF）
				writeLog(Level.INFO_INT, "IF_POSクラスメンテのバックアップ処理（IF→BK_IF）を開始します。");
				// #5009 Add 2017.06.05 S.Takayama (S)
				//iRec = db.executeUpdate(getAllInsertSQL(IF_POS_CLASS_FIVI, BK_IF_POS_CLASS_FIVI));
				iRec = db.executeUpdate(getAllInsertSQL(IF_POS_CLASS_FIVI, BK_IF_POS_CLASS_FIVI, batchDate));
				// #5009 Add 2017.06.05 S.Takayama (E)
				writeLog(Level.INFO_INT, "BK_IF_POSクラスメンテへ" + iRec + "件登録しました。");
				writeLog(Level.INFO_INT, "IF_POSクラスメンテのバックアップ処理（IF→BK_IF）を終了します。");

				// IF_POSサブクラスメンテのバックアップ（IF→BK_IF）
				writeLog(Level.INFO_INT, "IF_POSサブクラスメンテのバックアップ処理（IF→BK_IF）を開始します。");
				// #5009 Add 2017.06.05 S.Takayama (S)
				//iRec = db.executeUpdate(getAllInsertSQL(IF_POS_SUBCLASS, BK_IF_POS_SUBCLASS));
				iRec = db.executeUpdate(getAllInsertSQL(IF_POS_SUBCLASS, BK_IF_POS_SUBCLASS, batchDate));
				// #5009 Add 2017.06.05 S.Takayama (E)
				writeLog(Level.INFO_INT, "BK_IF_POSサブクラスメンテへ" + iRec + "件登録しました。");
				writeLog(Level.INFO_INT, "IF_POSサブクラスメンテのバックアップ処理（IF→BK_IF）を終了します。");
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
	private String getInsertTecBluSQL(String tableIdFrom, String tableIdTo, String batchDt) throws Exception {
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
		strSql.append("	" + batchDt + "");
		strSql.append("	, " + systemDt + ",");
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
