package mdware.master.batch.process.MSTB920;

import java.sql.SQLException;
import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 *
 * <p>タイトル: MSTB920060_IfEmgPosPaymentCreate.java クラス</p>
 * <p>説明　: IF_緊急POS支払種別メンテ、IF_緊急POS特売種別メンテを作成する。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.04.05) T.Han #2463対応 新規作成
 * @version 1.01 (2017.04.27) S.Nakazato #4824対応
 */
public class MSTB920060_IfEmgPosPaymentCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF_EMG_POS_PAYMENT  = "IF_EMG_POS_PAYMENT";  // IF_緊急POS支払種別メンテ
	private static final String TABLE_IF_EMG_POS_DISCOUNT = "IF_EMG_POS_DISCOUNT"; // IF_緊急POS特売種別メンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB920060_IfEmgPosPaymentCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB920060_IfEmgPosPaymentCreate() {
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

			// IF_緊急POS支払種別メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_緊急POS支払種別メンテ 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_EMG_POS_PAYMENT);
			writeLog(Level.INFO_INT, "IF_緊急POS支払種別メンテ 削除処理を終了します。");

			// IF_緊急POS特売種別メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_緊急POS特売種別メンテ 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_EMG_POS_DISCOUNT);
			writeLog(Level.INFO_INT, "IF_緊急POS特売種別メンテ 削除処理を終了します。");

			// IF_緊急POS支払種別メンテの登録
			writeLog(Level.INFO_INT, "IF_緊急POS支払種別メンテ 登録処理を開始します。");
			iRec = db.executeUpdate(getIfEmgPosPaymentInsertSql());
			writeLog(Level.INFO_INT, "IF_緊急POS支払種別メンテ を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_緊急POS支払種別メンテ 登録処理を終了します。");

			db.commit();

			// IF_緊急POS特売種別メンテの登録
			writeLog(Level.INFO_INT, "IF_緊急POS特売種別メンテ 登録処理を開始します。");
			iRec = db.executeUpdate(getIfEmgPosDiscountInsertSql());
			writeLog(Level.INFO_INT, "IF_緊急POS特売種別メンテ を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_緊急POS特売種別メンテ 登録処理を終了します。");

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
	 * IF_緊急POS支払種別メンテを作成するSQLを取得する
	 *
	 * @return IF_EMG_POS_PAYMENT登録SQL
	 */
	private String getIfEmgPosPaymentInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_EMG_POS_PAYMENT ");
		sql.append("	( ");
		sql.append("	  EIGYO_DT ");
		sql.append("	 ,TENPO_CD ");
		sql.append("	 ,TOROKU_ID ");
		sql.append("	 ,SHIHARAI_SYUBETSU_SEQ ");
		sql.append("	 ,SHIHARAI_SYUBETSU_EN_NA ");
		sql.append("	 ,SHIHARAI_SYUBETSU_VN_NA ");
		sql.append("	 ,POS_SEQ ");
		sql.append("	 ,OVER_TYPE ");
		sql.append("	 ,NEED_AUTHORITY ");
		sql.append("	 ,NEED_EXPIRY ");
		sql.append("	 ,CARD_NUMBER ");
		sql.append("	 ,PROCESS_TYPE ");
		sql.append("	 ,SHIHARAI_SYUBETSU_GROUP_SEQ ");
		sql.append("	 ,CARD_LENGTH ");
		sql.append("	 ,SHIHARAI_SYUBETSU_SUB_CD ");
		sql.append("	 ,DISPLAY_FG ");
		sql.append("	 ,VOID_FG ");
		sql.append("	 ,RETURN_FG ");
		sql.append("	 ,OPEN_DRAWER_FG ");
		sql.append("	 ,EXTRA_RECEIPT ");
		sql.append("	 ,MAXIMUM_RECEIPT ");
		sql.append("	 ,YUKO_START_DT ");
		sql.append("	 ,YUKO_END_DT ");
		sql.append("	 ,GYOTAI_KB ");
		sql.append("	 ,JIKASYOHI_KB ");
		sql.append("	 ,JIKASYOHI_RECEIPT_VN_NA ");
		sql.append("	 ,INSERT_TS ");
		sql.append("	 ,INSERT_USER_ID ");
		sql.append("	 ,UPDATE_TS ");
		sql.append("	 ,UPDATE_USER_ID ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 WEPP.EIGYO_DT ");
		sql.append("	,WEPP.TENPO_CD ");
		sql.append("	,WEPP.TOROKU_ID ");
		sql.append("	,WEPP.SHIHARAI_SYUBETSU_SEQ ");
		sql.append("	,WEPP.SHIHARAI_SYUBETSU_EN_NA ");
		sql.append("	,WEPP.SHIHARAI_SYUBETSU_VN_NA ");
		sql.append("	,WEPP.POS_SEQ ");
		sql.append("	,WEPP.OVER_TYPE ");
		sql.append("	,WEPP.NEED_AUTHORITY ");
		sql.append("	,WEPP.NEED_EXPIRY ");
		sql.append("	,WEPP.CARD_NUMBER ");
		sql.append("	,WEPP.PROCESS_TYPE ");
		sql.append("	,WEPP.SHIHARAI_SYUBETSU_GROUP_SEQ ");
		sql.append("	,WEPP.CARD_LENGTH ");
		sql.append("	,WEPP.SHIHARAI_SYUBETSU_SUB_CD ");
		sql.append("	,WEPP.DISPLAY_FG ");
		sql.append("	,WEPP.VOID_FG ");
		sql.append("	,WEPP.RETURN_FG ");
		sql.append("	,WEPP.OPEN_DRAWER_FG ");
		sql.append("	,WEPP.EXTRA_RECEIPT ");
		sql.append("	,WEPP.MAXIMUM_RECEIPT ");
		sql.append("	,NVL(WEPP.YUKO_START_DT, '        ') ");
		sql.append("	,NVL(WEPP.YUKO_END_DT, '        ') ");
		sql.append("	,WEPP.GYOTAI_KB ");
		sql.append("	,WEPP.JIKASYOHI_KB ");
		sql.append("	,SUBSTR(WEPP.JIKASYOHI_RECEIPT_VN_NA, 0, 40) ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append(" FROM ");
		sql.append("	WK_EMG_POS_PAYMENT WEPP ");
		sql.append("	LEFT JOIN ZEN_POS_PAYMENT ZPP ");
		sql.append("	ON ");
		sql.append("		WEPP.SHIHARAI_SYUBETSU_SEQ = ZPP.SHIHARAI_SYUBETSU_SEQ AND ");
		sql.append("		WEPP.TENPO_CD = ZPP.TENPO_CD ");
		sql.append(" WHERE ");
		sql.append(" 	ZPP.SHIHARAI_SYUBETSU_SEQ IS NULL ");
		sql.append(" 	OR WEPP.SHIHARAI_SYUBETSU_EN_NA <> ZPP.SHIHARAI_SYUBETSU_EN_NA ");
		sql.append(" 	OR WEPP.SHIHARAI_SYUBETSU_VN_NA <> ZPP.SHIHARAI_SYUBETSU_VN_NA ");
		sql.append(" 	OR WEPP.POS_SEQ <> ZPP.POS_SEQ ");
		sql.append(" 	OR WEPP.OVER_TYPE <> ZPP.OVER_TYPE ");
		sql.append(" 	OR WEPP.NEED_AUTHORITY <> ZPP.NEED_AUTHORITY ");
		sql.append(" 	OR WEPP.NEED_EXPIRY <> ZPP.NEED_EXPIRY ");
		sql.append(" 	OR WEPP.CARD_NUMBER <> ZPP.CARD_NUMBER ");
		sql.append(" 	OR WEPP.PROCESS_TYPE <> ZPP.PROCESS_TYPE ");
		sql.append(" 	OR WEPP.SHIHARAI_SYUBETSU_GROUP_SEQ <> ZPP.SHIHARAI_SYUBETSU_GROUP_SEQ ");
		sql.append(" 	OR WEPP.CARD_LENGTH <> ZPP.CARD_LENGTH ");
		sql.append(" 	OR WEPP.SHIHARAI_SYUBETSU_SUB_CD <> ZPP.SHIHARAI_SYUBETSU_SUB_CD ");
		sql.append(" 	OR WEPP.DISPLAY_FG <> ZPP.DISPLAY_FG ");
		sql.append(" 	OR WEPP.VOID_FG <> ZPP.VOID_FG ");
		sql.append(" 	OR WEPP.RETURN_FG <> ZPP.RETURN_FG ");
		sql.append(" 	OR WEPP.OPEN_DRAWER_FG <> ZPP.OPEN_DRAWER_FG ");
		sql.append(" 	OR WEPP.EXTRA_RECEIPT <> ZPP.EXTRA_RECEIPT ");
		sql.append(" 	OR WEPP.MAXIMUM_RECEIPT <> ZPP.MAXIMUM_RECEIPT ");
		sql.append(" 	OR WEPP.YUKO_START_DT <> ZPP.YUKO_START_DT ");
		sql.append(" 	OR WEPP.YUKO_END_DT <> ZPP.YUKO_END_DT ");
		sql.append(" 	OR WEPP.JIKASYOHI_KB <> ZPP.JIKASYOHI_KB ");
		sql.append(" 	OR WEPP.JIKASYOHI_RECEIPT_VN_NA <> ZPP.JIKASYOHI_RECEIPT_VN_NA ");

		return sql.toString();
	}

	/**
	 * IF_緊急POS特売種別メンテを作成するSQLを取得する
	 *
	 * @return IF_EMG_POS_DISCOUNT登録SQL
	 */
	private String getIfEmgPosDiscountInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_EMG_POS_DISCOUNT ");
		sql.append("	( ");
		sql.append("	 EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,DISCOUNT_CD ");
		sql.append("	,SUB_DISCOUNT_CD ");
		sql.append("	,DISCOUNT_NA ");
		sql.append("	,SUB_DISCOUNT_NA ");
		sql.append("	,RECEIPT_QT ");
		sql.append("	,MAX_RECEIPT_QT ");
		sql.append("	,NEBIKI_RITU_VL ");
		sql.append("	,YUKO_START_DT ");
		sql.append("	,YUKO_END_DT ");
		sql.append("	,MAX_NEBIKI_GAKU_VL ");
		sql.append("	,GYOTAI_KB");
		sql.append("	,CARD_KB");
		sql.append("	,INSERT_TS ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	,UPDATE_USER_ID ");
		// 2017.04.27 S.Nakazato #4824対応（S)
		sql.append("	,SHIHARAI_JOKEN_CD ");
		// 2017.04.27 S.Nakazato #4824対応（E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 WEPD.EIGYO_DT ");
		sql.append("	,WEPD.TENPO_CD ");
		sql.append("	,WEPD.TOROKU_ID ");
		sql.append("	,WEPD.DISCOUNT_CD ");
		sql.append("	,WEPD.SUB_DISCOUNT_CD ");
		sql.append("	,WEPD.DISCOUNT_NA ");
		sql.append("	,WEPD.SUB_DISCOUNT_NA ");
		sql.append("	,WEPD.RECEIPT_QT ");
		sql.append("	,WEPD.MAX_RECEIPT_QT ");
		sql.append("	,NVL(WEPD.NEBIKI_RITU_VL, '  ') ");
		sql.append("	,NVL(WEPD.YUKO_START_DT, '        ') ");
		sql.append("	,NVL(WEPD.YUKO_END_DT, '        ') ");
		sql.append("	,NVL(WEPD.MAX_NEBIKI_GAKU_VL, 00000000000000.00) ");
		sql.append("	,WEPD.GYOTAI_KB ");
		sql.append("	,WEPD.CARD_KB ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		// 2017.04.27 S.Nakazato #4824対応（S)
		sql.append("	,WEPD.SHIHARAI_JOKEN_CD ");
		// 2017.04.27 S.Nakazato #4824対応（E)
		sql.append(" FROM ");
		sql.append("	WK_EMG_POS_DISCOUNT WEPD ");
		sql.append("	LEFT JOIN ZEN_POS_DISCOUNT ZPD ");
		sql.append("	ON ");
		sql.append("		WEPD.SUB_DISCOUNT_CD = ZPD.SUB_DISCOUNT_CD AND ");
		sql.append("		WEPD.TENPO_CD = ZPD.TENPO_CD ");
		sql.append(" WHERE ");
		sql.append(" 	ZPD.SUB_DISCOUNT_CD IS NULL ");
		sql.append(" 	OR WEPD.DISCOUNT_CD <> ZPD.DISCOUNT_CD ");
		sql.append(" 	OR WEPD.DISCOUNT_NA <> ZPD.DISCOUNT_NA ");
		sql.append(" 	OR WEPD.SUB_DISCOUNT_NA <> ZPD.SUB_DISCOUNT_NA ");
		sql.append(" 	OR WEPD.RECEIPT_QT <> ZPD.RECEIPT_QT ");
		sql.append(" 	OR WEPD.MAX_RECEIPT_QT <> ZPD.MAX_RECEIPT_QT ");
		sql.append(" 	OR WEPD.NEBIKI_RITU_VL <> ZPD.NEBIKI_RITU_VL ");
		sql.append(" 	OR WEPD.YUKO_START_DT <> ZPD.YUKO_START_DT ");
		sql.append(" 	OR WEPD.YUKO_END_DT <> ZPD.YUKO_END_DT ");
		sql.append(" 	OR WEPD.MAX_NEBIKI_GAKU_VL <> ZPD.MAX_NEBIKI_GAKU_VL ");
		sql.append(" 	OR WEPD.CARD_KB <> ZPD.CARD_KB ");
		// 2017.04.27 S.Nakazato #4824対応（S)
		sql.append("	OR WEPD.SHIHARAI_JOKEN_CD <> ZPD.SHIHARAI_JOKEN_CD ");
		// 2017.04.27 S.Nakazato #4824対応（E)
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
