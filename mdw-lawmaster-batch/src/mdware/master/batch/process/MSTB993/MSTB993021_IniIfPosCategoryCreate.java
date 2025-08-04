package mdware.master.batch.process.MSTB993;

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
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;

/**
 *
 * <p>タイトル: MSTB993021_IfIniPosCategoryCreate.java クラス</p>
 * <p>説明　: イニシャルPLU用IFカテゴリマスタ作成
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @version 1.00  (2016.10.03) VINX H.Sakamoto #1979対応
 * @version 1.01 (2016.10.18) Li.Sheng #2238
 * @version 1.02 (2016.11.24) nv.cuong #2839
 * @version 1.03 (2021.12.14) KHOI.ND #6406
 * @version 1.04 (2022.05.05) SIEU.D #6553
 */
public class MSTB993021_IniIfPosCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** バッチ日付 */ 
	private String batchDt = null;
	/** バッチ日付翌日 */ 
	private String yokuBatchDt = null;
	/** システム日時 */ 
	private String systemTs = null;
	/** 定数 01 */
	private String ZERO_ONE = "01";
	/** 登録ID 固定文字 */
	private String TOROKU_ID = "A";

	// テーブル
	private static final String TABLE_BUMON = "IF_INI_POS_BUMON"; // IF_INI_POS部門メンテ
	private static final String TABLE_DPT = "IF_INI_POS_DPT"; // IF_INI_POSデプトメンテ
	private static final String TABLE_CLASS = "IF_INI_POS_CLASS_FIVI"; // IF_INI_POSクラスメンテ
	private static final String TABLE_SUBCLASS = "IF_INI_POS_SUBCLASS"; // IF_INI_POSサブクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB993021_IniIfPosCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB993021_IniIfPosCategoryCreate() {
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
			batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			//バッチ日付の翌日取得
			yokuBatchDt = DateChanger.addDate(batchDt, 1);
			//
			systemTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

			// IF_INI_POS部門メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_INI_POS部門メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_BUMON);
			writeLog(Level.INFO_INT, "IF_INI_POS部門メンテ削除処理を終了します。");

			// IF_INI_POSデプトメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_INI_POSデプトメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_DPT);
			writeLog(Level.INFO_INT, "IF_INI_POSデプトメンテ削除処理を終了します。");

			// IF_INI_POSクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_INI_POSクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_CLASS);
			writeLog(Level.INFO_INT, "IF_INI_POSクラスメンテ削除処理を終了します。");

			// IF_INI_POSサブクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_INI_POSサブクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_SUBCLASS);
			writeLog(Level.INFO_INT, "IF_INI_POSサブクラスメンテ削除処理を終了します。");

			// IF_INI_POS部門メンテ登録
			writeLog(Level.INFO_INT, "IF_INI_POS部門メンテ登録処理を開始します。");
			iRec = db.executeUpdate(getIfIniPosBumonInsertSql());
			writeLog(Level.INFO_INT, "IF_INI_POS部門メンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_INI_POS部門メンテ登録処理を終了します。");

			db.commit();

			// IF_INI_POSデプトメンテ登録
			writeLog(Level.INFO_INT, "IF_INI_POSデプトメンテ登録処理を開始します。");
			iRec = db.executeUpdate(getIfIniPosDPTInsertSql());
			writeLog(Level.INFO_INT, "IF_INI_POSデプトメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_INI_POSデプトメンテ登録処理を終了します。");

			db.commit();

			// IF_INI_POSクラスメンテ登録
			writeLog(Level.INFO_INT, "IF_INI_POSクラスメンテ登録処理を開始します。");
			iRec = db.executeUpdate(getIfIniPosClassInsertSql());
			writeLog(Level.INFO_INT, "IF_INI_POSクラスメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_INI_POSクラスメンテ登録処理を終了します。");

			db.commit();

			// IF_INI_POSサブクラスメンテ登録
			writeLog(Level.INFO_INT, "IF_INI_POSサブクラスメンテ登録処理を開始します。");
			iRec = db.executeUpdate(getIfIniPosSubClassInsertSql());
			writeLog(Level.INFO_INT, "IF_INI_POSサブクラスメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_INI_POSサブクラスメンテ登録処理を終了します。");

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
	 * IF_INI_POS部門メンテを登録するSQLを取得する
	 *
	 * @return IF_INI_POS_BUMON登録SQL
	 */
	private String getIfIniPosBumonInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT  ");
		sql.append("INTO IF_INI_POS_BUMON(  ");
		sql.append("  BATCH_DT ");
		sql.append("  , TENPO_CD ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append("  , GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)
		sql.append("  , SEND_QT ");
		sql.append("  , TOROKU_ID ");
		sql.append("  , DIVISION_CD ");
		sql.append("  , DIVISION_NA ");
		sql.append("  , PRIME_GROUP ");
		sql.append("  , PRIME_GROUP_NA ");
		sql.append("  , INSERT_TS ");
		sql.append("  , INSERT_USER_ID ");
		sql.append("  , UPDATE_TS ");
		sql.append("  , UPDATE_USER_ID ");
		sql.append(")  ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("  '" + batchDt + "' ");
		sql.append("  '" + yokuBatchDt + "' ");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("  , TRT.TENPO_CD ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append("  , TRT.GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)
		sql.append("  , '" + ZERO_ONE + "' ");
		sql.append("  , '" + TOROKU_ID + "' ");
		sql.append("  , WPB.DIVISION_CD ");
		sql.append("  , WPB.DIVISION_NA ");
		sql.append("  , WPB.PRIME_GROUP ");
		sql.append("  , WPB.PRIME_GROUP_NA  ");
		sql.append(" , '" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append(" , '" + systemTs + "' AS INSERT_TS ");
		sql.append(" , '" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append(" , '" + systemTs + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("  WK_POS_BUMON WPB  ");
		sql.append("  INNER JOIN TMP_R_TENPO TRT  ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("    ON TRT.TENPO_KB = '"+ mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() +"' AND ");
		// sql.append("    TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		// #6553 MOD 2022.05.05 SIEU.D (S)
		// sql.append("    ON TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		sql.append("    ON ");
		// #6553 MOD 2022.05.05 SIEU.D (E)
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("    TRT.ZAIMU_END_DT >= '"+ batchDt +"' AND ");
		sql.append("    TRT.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		sql.append("    TRT.SAI_SEND_PLU_DT = '"+ yokuBatchDt +"' ");

		return sql.toString();
	}

	/**
	 * IF_INI_POSデプトメンテを登録するSQLを取得する
	 *
	 * @return IF_INI_POS_DPT登録SQL
	 */
	private String getIfIniPosDPTInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT  ");
		sql.append("INTO IF_INI_POS_DPT(  ");
		sql.append("  BATCH_DT ");
		sql.append("  , TENPO_CD ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append("  , GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)
		sql.append("  , SEND_QT ");
		sql.append("  , TOROKU_ID ");
		sql.append("  , DIVISION_CD ");
		sql.append("  , DEPARTMENT_CD ");
		sql.append("  , DEPARTMENT_NA ");
		sql.append("  , INSERT_TS ");
		sql.append("  , INSERT_USER_ID ");
		sql.append("  , UPDATE_TS ");
		sql.append("  , UPDATE_USER_ID ");
		sql.append(")  ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("  '" + batchDt + "' ");
		sql.append("  '" + yokuBatchDt + "' ");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("  , TRT.TENPO_CD ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append("  , TRT.GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)
		sql.append("  , '" + ZERO_ONE + "' ");
		sql.append("  , '" + TOROKU_ID + "' ");
		sql.append("  , WPD.DIVISION_CD ");
		sql.append("  , WPD.DEPARTMENT_CD ");
		sql.append("  , WPD.DEPARTMENT_NA  ");
		sql.append("  , '" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("  , '" + systemTs + "' AS INSERT_TS ");
		sql.append("  , '" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("  , '" + systemTs + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("  WK_POS_DPT WPD  ");
		sql.append("  INNER JOIN TMP_R_TENPO TRT  ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("    ON TRT.TENPO_KB = '"+ mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() +"' AND ");
		// sql.append("    TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
        // #6553 MOD 2022.05.05 SIEU.D (S)
		// sql.append("    ON TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		sql.append("    ON ");
        // #6553 MOD 2022.05.05 SIEU.D (E)
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("    TRT.ZAIMU_END_DT >= '"+ batchDt +"' AND ");
		sql.append("    TRT.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		sql.append("    TRT.SAI_SEND_PLU_DT = '"+ yokuBatchDt +"' ");

		return sql.toString();
	}

	/**
	 * IF_INI_POSクラスメンテを登録するSQLを取得する
	 *
	 * @return IF_INI_POS_CLASS_FIVI登録SQL
	 */
	private String getIfIniPosClassInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT  ");
		sql.append("INTO IF_INI_POS_CLASS_FIVI(  ");
		sql.append("  BATCH_DT ");
		sql.append("  , TENPO_CD ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append("  , GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)
		sql.append("  , SEND_QT ");
		sql.append("  , TOROKU_ID ");
		sql.append("  , DEPARTMENT_CD ");
		sql.append("  , CLASS_CD ");
		sql.append("  , CLASS_NA ");
		sql.append("  , DEPARTMENT_TYPE ");
		sql.append("  , INSERT_TS ");
		sql.append("  , INSERT_USER_ID ");
		sql.append("  , UPDATE_TS ");
		sql.append("  , UPDATE_USER_ID ");
		sql.append(")  ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("  '" + batchDt + "' ");
		sql.append("  '" + yokuBatchDt + "' ");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("  , TRT.TENPO_CD ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append("  , TRT.GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)
		sql.append("  , '" + ZERO_ONE + "' ");
		sql.append("  , '" + TOROKU_ID + "' ");
		sql.append("  , WPCF.DEPARTMENT_CD ");
		sql.append("  , WPCF.CLASS_CD ");
		sql.append("  , WPCF.CLASS_NA ");
		sql.append("  , WPCF.DEPARTMENT_TYPE  ");
		sql.append("  , '" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("  , '" + systemTs + "' AS INSERT_TS ");
		sql.append("  , '" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("  , '" + systemTs + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("  WK_POS_CLASS_FIVI WPCF  ");
		sql.append("  INNER JOIN TMP_R_TENPO TRT  ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("    ON TRT.TENPO_KB = '"+ mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() +"' AND ");
		// sql.append("    TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
        // #6553 MOD 2022.05.05 SIEU.D (S)
		// sql.append("    ON TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
        sql.append("    ON ");
        // #6553 MOD 2022.05.05 SIEU.D (E)
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("    TRT.ZAIMU_END_DT >= '"+ batchDt +"' AND ");
		sql.append("    TRT.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		sql.append("    TRT.SAI_SEND_PLU_DT = '"+ yokuBatchDt +"' ");

		return sql.toString();
	}

	/**
	 * IF_INI_POSサブクラスメンテを登録するSQLを取得する
	 *
	 * @return IF_INI_POS_SUBCLASS登録SQL
	 */
	private String getIfIniPosSubClassInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT  ");
		sql.append("INTO IF_INI_POS_SUBCLASS(  ");
		sql.append("  BATCH_DT ");
		sql.append("  , TENPO_CD ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append("  , GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)
		sql.append("  , SEND_QT ");
		sql.append("  , TOROKU_ID ");
		sql.append("  , CLASS_CD ");
		sql.append("  , SUBCLASS_CD ");
		sql.append("  , SUBCLASS_NA ");
		sql.append("  , AEON_CARD_NEBIKI_FG ");
		sql.append("  , CHG_VL_NEBIKI_BTN_FG ");
		sql.append("  , KAIIN_CARD_NEBIKI_RT ");
		sql.append("  , MATERNITY_CARD_NEBIKI_RT ");
		sql.append("  , INSERT_TS ");
		sql.append("  , INSERT_USER_ID ");
		sql.append("  , UPDATE_TS ");
		sql.append("  , UPDATE_USER_ID ");
		sql.append(")  ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("  '" + batchDt + "' ");
		sql.append("  '" + yokuBatchDt + "' ");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("  , TRT.TENPO_CD ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append("  , TRT.GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)
		sql.append("  , '" + ZERO_ONE + "' ");
		sql.append("  , '" + TOROKU_ID + "' ");
		sql.append("  , WPS.CLASS_CD ");
		sql.append("  , WPS.SUBCLASS_CD ");
		sql.append("  , WPS.SUBCLASS_NA ");
		sql.append("  , WPS.AEON_CARD_NEBIKI_FG ");
		sql.append("  , WPS.CHG_VL_NEBIKI_BTN_FG ");
		sql.append("  , WPS.KAIIN_CARD_NEBIKI_RT ");
		sql.append("  , WPS.MATERNITY_CARD_NEBIKI_RT  ");
		sql.append("  , '" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("  , '" + systemTs + "' AS INSERT_TS ");
		sql.append("  , '" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("  , '" + systemTs + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("  WK_POS_SUBCLASS WPS  ");
		sql.append("  INNER JOIN TMP_R_TENPO TRT  ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("    ON TRT.TENPO_KB = '"+ mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() +"' AND ");
		// sql.append("    TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		// #6553 MOD 2022.05.05 SIEU.D (S)
		// sql.append("    ON TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		sql.append("    ON ");
		// #6553 MOD 2022.05.05 SIEU.D (E)
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("    TRT.ZAIMU_END_DT >= '"+ batchDt +"' AND ");
		sql.append("    TRT.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		sql.append("    TRT.SAI_SEND_PLU_DT = '"+ yokuBatchDt +"' ");
		
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

