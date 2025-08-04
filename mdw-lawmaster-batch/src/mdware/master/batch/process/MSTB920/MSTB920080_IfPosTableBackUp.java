package mdware.master.batch.process.MSTB920;

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
 * <p>タイトル: MSTB920080_IfPosTableBackUp.java クラス</p>
 * <p>説明　: IF_緊急PLU単品メンテ、IF_緊急POS支払種別メンテ、IF_緊急POS特売種別メンテのバックアップを作成する。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.04.06) M.Son #2463 FIVImart様対応
 * @version 1.01 (2020.09.30) KHAI.NN #6238 MKV対応
 * @Version 1.02 (2024.01.16) DUY.HM #15277 MKH対応
 *
 */
public class MSTB920080_IfPosTableBackUp {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String IF_PLU_EMG_TANPIN = "IF_PLU_EMG_TANPIN"; // IF_緊急PLU単品メンテ
	private static final String BK_IF_PLU_TANPIN = "BK_IF_PLU_TANPIN"; // BK_IF_PLU単品メンテ

	private static final String IF_EMG_POS_PAYMENT = "IF_EMG_POS_PAYMENT"; // IF_緊急POS支払種別メンテ
	private static final String BK_IF_POS_PAYMENT = "BK_IF_POS_PAYMENT"; // BK_IF_POS支払種別メンテ

	private static final String IF_EMG_POS_DISCOUNT = "IF_EMG_POS_DISCOUNT"; // IF_緊急POS特売種別メンテ
	private static final String BK_IF_POS_DISCOUNT = "BK_IF_POS_DISCOUNT"; // BK_IF_POS特売種別メンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB920080_IfPosTableBackUp(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB920080_IfPosTableBackUp() {
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

			// IF_緊急PLU単品メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getIfPluEmgTanPinSql(IF_PLU_EMG_TANPIN, BK_IF_PLU_TANPIN, batchDate));
			writeLog(Level.INFO_INT, "BK_IF_PLU単品メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_緊急POS支払種別メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_緊急POS支払種別メンテのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(IF_EMG_POS_PAYMENT, BK_IF_POS_PAYMENT, batchDate));
			writeLog(Level.INFO_INT, "BK_IF_POS支払種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_緊急POS支払種別メンテのバックアップ処理（IF→BK_IF）を終了します。");

			// IF_緊急POS特売種別メンテのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_緊急POS特売種別メンテのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(IF_EMG_POS_DISCOUNT, BK_IF_POS_DISCOUNT, batchDate));
			writeLog(Level.INFO_INT, "BK_IF_POS特売種別メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "IF_緊急POS特売種別メンテのバックアップ処理（IF→BK_IF）を終了します。");

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
	private String getIfPluEmgTanPinSql(String tableIdFrom, String tableIdTo, String batchDt) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + tableIdTo + " (");
		strSql.append("		SYORI_DT ");
		strSql.append("		,SYORI_TS ");
		strSql.append("		,EIGYO_DT ");
		strSql.append("		,TENPO_CD ");
		strSql.append("		,SEND_QT ");
		strSql.append("		,TOROKU_ID ");
		strSql.append("		,SYOHIN_CD ");
		strSql.append("		,SYOHIN_RN ");
		strSql.append("		,SYOHIN_NA ");
		strSql.append("		,SYOHIN_RN_CHN ");
		strSql.append("		,SYOHIN_NA_CHN ");
		strSql.append("		,SYOHIN_BAR_CD ");
		strSql.append("		,BAITANKA_VL ");
		strSql.append("		,KAIIN_BAITANKA_VL ");
		strSql.append("		,HANBAI_TN ");
		strSql.append("		,DIVISION_CD ");
		strSql.append("		,DEPARTMENT_CD ");
		strSql.append("		,CLASS_CD ");
		strSql.append("		,SUBCLASS_CD ");
		strSql.append("		,TEIKAN_FG ");
		strSql.append("		,PLU_FG ");
		strSql.append("		,CREATE_TS ");
		strSql.append("		,ZEI_KB ");
		strSql.append("		,ZEI_RT ");
		strSql.append("		,SEASON_ID ");
		strSql.append("		,HANBAI_ZEI_RT ");
		strSql.append("		,STUDENT_WARIBIKI_CARD_FG ");
		strSql.append("		,SYOHI_KIGEN_DT ");
		strSql.append("		,CARD_FG ");
		strSql.append("		,INJI_HANBAI_TN ");
		strSql.append("		,INJI_SEIZOU_DT ");
		strSql.append("		,ZEI_CD ");
		strSql.append("		,INSERT_TS ");
		strSql.append("		,INSERT_USER_ID ");
		strSql.append("		,UPDATE_TS ");
		strSql.append("		,UPDATE_USER_ID ");
		strSql.append("		,OLD_SYOHIN_CD ");
		strSql.append("		,SIIRESAKI_CD ");
		strSql.append("		,SYOHI_KIGEN_HYOJI_PATTER ");
		strSql.append("		,LABEL_SEIBUN ");
		strSql.append("		,LABEL_HOKAN_HOHO ");
		strSql.append("		,LABEL_TUKAIKATA ");
		strSql.append("		,GYOTAI_KB ");
		strSql.append("		,LABEL_COUNTRY_NA ");
		strSql.append("		,INJI_HANBAI_TN_EN ");
		// #6238 MSTB920 Mod 2020.09.30 KHAI.NN (S)
		strSql.append("		,ITEM_OFFICIAL_NA ");
		// #6238 MSTB920 Mod 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		strSql.append("		,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		strSql.append(") ");
		strSql.append("SELECT ");
		strSql.append("		'" + batchDt + "' ");
		strSql.append("		,'" + systemDt + "' ");
		strSql.append("		,EIGYO_DT ");
		strSql.append("		,TENPO_CD ");
		strSql.append("		,SEND_QT ");
		strSql.append("		,TOROKU_ID ");
		strSql.append("		,SYOHIN_CD ");
		strSql.append("		,SYOHIN_RN ");
		strSql.append("		,SYOHIN_NA ");
		strSql.append("		,SYOHIN_RN_CHN ");
		strSql.append("		,SYOHIN_NA_CHN ");
		strSql.append("		,SYOHIN_BAR_CD ");
		strSql.append("		,BAITANKA_VL ");
		strSql.append("		,KAIIN_BAITANKA_VL ");
		strSql.append("		,HANBAI_TN ");
		strSql.append("		,DIVISION_CD ");
		strSql.append("		,DEPARTMENT_CD ");
		strSql.append("		,CLASS_CD ");
		strSql.append("		,SUBCLASS_CD ");
		strSql.append("		,TEIKAN_FG ");
		strSql.append("		,PLU_FG ");
		strSql.append("		,CREATE_TS ");
		strSql.append("		,ZEI_KB ");
		strSql.append("		,ZEI_RT ");
		strSql.append("		,SEASON_ID ");
		strSql.append("		,HANBAI_ZEI_RT ");
		strSql.append("		,STUDENT_WARIBIKI_CARD_FG ");
		strSql.append("		,SYOHI_KIGEN_DT ");
		strSql.append("		,CARD_FG ");
		strSql.append("		,INJI_HANBAI_TN ");
		strSql.append("		,INJI_SEIZOU_DT ");
		strSql.append("		,ZEI_CD ");
		strSql.append("		,INSERT_TS ");
		strSql.append("		,INSERT_USER_ID ");
		strSql.append("		,UPDATE_TS ");
		strSql.append("		,UPDATE_USER_ID ");
		strSql.append("		,OLD_SYOHIN_CD ");
		strSql.append("		,SIIRESAKI_CD ");
		strSql.append("		,SYOHI_KIGEN_HYOJI_PATTER ");
		strSql.append("		,LABEL_SEIBUN ");
		strSql.append("		,LABEL_HOKAN_HOHO ");
		strSql.append("		,LABEL_TUKAIKATA ");
		strSql.append("		,GYOTAI_KB ");
		strSql.append("		,LABEL_COUNTRY_NA ");
		strSql.append("		,INJI_HANBAI_TN_EN ");
		// #6238 MSTB920 Mod 2020.09.30 KHAI.NN (S)
		strSql.append("		,ITEM_OFFICIAL_NA ");
		// #6238 MSTB920 Mod 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		strSql.append("		,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		strSql.append("FROM " + tableIdFrom + " ");

		return strSql.toString();
	}

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
