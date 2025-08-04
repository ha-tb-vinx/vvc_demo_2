package mdware.master.batch.process.MSTB909;

import java.nio.CharBuffer;
import java.sql.SQLException;
import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB909090_WkEmgBlkPaymentCreate.java クラス</p>
 * <p>説明　: Blynkに連携する支払種別マスタ、特売種別マスタデータを作成する。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.04.06) T.Han #2463 FIVImart様対応 新規作成 
 * @version 1.01 (2017.04.25) S.Nakazato #4824 FIVIMART対応
 * @version 1.02 (2017.05.18) M.Son #5044 FIVIMART対応
 */
public class MSTB909090_WkEmgBlkPaymentCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK_EMG_BLK_PAYMENT  = "WK_EMG_BLK_PAYMENT";  // WK_緊急BLK支払種別マスタ
	private static final String TABLE_WK_EMG_BLK_DISCOUNT = "WK_EMG_BLK_DISCOUNT"; // WK_緊急BLK特売種別マスタ

	/** 自家消費レシート印字文言（VN） */
	private static final int LENGTH_SHIHARAI_SYUBETSU_SUB_NA = 40;

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	
	/** 最大レシート発行数 */
	private String maxImumReceipt = "";
	private String MAXIMUM_RECEIPT = "";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB909090_WkEmgBlkPaymentCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB909090_WkEmgBlkPaymentCreate() {
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

			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();

			// システムコントロール情報取得
			this.getSystemControl();
			MAXIMUM_RECEIPT = maxImumReceipt;

			// WK_緊急BLK支払種別マスタのTRUNCATE
			writeLog(Level.INFO_INT, "WK_緊急BLK支払種別マスタ 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_EMG_BLK_PAYMENT);
			writeLog(Level.INFO_INT, "WK_緊急BLK支払種別マスタ 削除処理を終了します。");

			// WK_緊急BLK特売種別マスタのTRUNCATE
			writeLog(Level.INFO_INT, "WK_緊急BLK特売種別マスタ 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_EMG_BLK_DISCOUNT);
			writeLog(Level.INFO_INT, "WK_緊急BLK特売種別マスタ 削除処理を終了します。");

			// WK_緊急BLK支払種別マスタの登録
			writeLog(Level.INFO_INT, "WK_緊急BLK支払種別マスタ 登録処理を開始します。");
			iRec = db.executeUpdate(getWkEmgBlkPaymentInsertSql());
			writeLog(Level.INFO_INT, "WK_緊急BLK支払種別マスタ を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_緊急BLK支払種別マスタ 登録処理を終了します。");

			db.commit();

			// WK_緊急BLK特売種別マスタの登録
			writeLog(Level.INFO_INT, "WK_緊急BLK特売種別マスタ 登録処理を開始します。");
			iRec = db.executeUpdate(getWkEmgBlkDiscountInsertSql());
			writeLog(Level.INFO_INT, "WK_緊急BLK特売種別マスタ を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_緊急BLK特売種別マスタ 登録処理を終了します。");

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
	 * WK_緊急BLK支払種別マスタを作成するSQLを取得する
	 *
	 * @return WK_EMG_BLK_PAYMENT登録SQL
	 */
	private String getWkEmgBlkPaymentInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_EMG_BLK_PAYMENT ");
		sql.append("	( ");
		sql.append("	  DATA_MAKE_DT ");
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
		sql.append("	 ,JIKASYOHI_KB ");
		sql.append("	 ,JIKASYOHI_RECEIPT_VN_NA ");
		sql.append("	 ,INSERT_TS ");
		sql.append("	 ,INSERT_USER_ID ");
		sql.append("	 ,UPDATE_TS ");
		sql.append("	 ,UPDATE_USER_ID ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + batchDt + "' AS DATA_MAKE_DT");
		sql.append("	,'A' AS TOROKU_ID ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_SEQ ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_EN_NA ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_VN_NA ");
		sql.append("	,RP.POS_SEQ ");
		sql.append("	,RP.OVER_TYPE ");
		sql.append("	,' ' AS NEED_AUTHORITY ");
		sql.append("	,' ' AS NEED_EXPIRY ");
		sql.append("	,' ' AS CARD_NUMBER ");
		sql.append("	,RP.PROCESS_TYPE ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_GROUP_SEQ ");
		sql.append("	,' ' AS CARD_LENGTH ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_SUB_CD ");
		sql.append("	,RP.DISPLAY_FG ");
		sql.append("	,RP.VOID_FG ");
		sql.append("	,RP.RETURN_FG ");
		sql.append("	,RP.OPEN_DRAWER_FG ");
		sql.append("	,RP.EXTRA_RECEIPT ");
		sql.append("  ,'" + MAXIMUM_RECEIPT + "' AS MAXIMUM_RECEIPT");
		sql.append("	,NVL(RP.YUKO_START_DT, '        ') AS YUKO_START_DT ");
		sql.append("	,NVL(RP.YUKO_END_DT, '        ') AS YUKO_END_DT ");
		sql.append("    ,CASE WHEN  RP.SHIHARAI_SYUBETSU_CD = 'MRS' THEN 'Y' ELSE 'N' END JIKASYOHI_KB ");
		sql.append("    ,CASE WHEN  RP.SHIHARAI_SYUBETSU_CD = 'MRS' THEN RP.SHIHARAI_SYUBETSU_SUB_NA ELSE '"+spaces(LENGTH_SHIHARAI_SYUBETSU_SUB_NA)+"' END JIKASYOHI_RECEIPT_VN_NA ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append(" FROM ");
		sql.append("	R_PAYMENT RP ");

		return sql.toString();
	}

	/**
	 * WK_緊急BLK特売種別マスタを作成するSQLを取得する
	 *
	 * @return WK_EMG_BLK_DISCOUNT登録SQL
	 */
	private String getWkEmgBlkDiscountInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_EMG_BLK_DISCOUNT ");
		sql.append("	( ");
		sql.append("	 DATA_MAKE_DT ");
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
		sql.append("	,CARD_KB");
		sql.append("	,INSERT_TS ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	,UPDATE_USER_ID ");
		// 2017.04.25 S.Nakazato #4824対応（S)
		sql.append("	 ,SHIHARAI_JOKEN_CD ");
		// 2017.04.25 S.Nakazato #4824対応（E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + batchDt + "' AS DATA_MAKE_DT");
		sql.append("	,'A' AS TOROKU_ID ");
		sql.append("	,RD.DISCOUNT_CD ");
		sql.append("	,RD.SUB_DISCOUNT_CD ");
		sql.append("	,RD.DISCOUNT_NA ");
		sql.append("	,RD.SUB_DISCOUNT_NA ");
		sql.append("	,RD.RECEIPT_QT ");
		sql.append("	,'" + MAXIMUM_RECEIPT + "' AS MAX_RECEIPT_QT");
		sql.append("	,NVL(RD.NEBIKI_RITU_VL, '  ') AS NEBIKI_RITU_VL ");
		sql.append("	,NVL(RD.YUKO_START_DT, '        ') AS YUKO_START_DT ");
		sql.append("	,NVL(RD.YUKO_END_DT, '        ') AS YUKO_END_DT ");
		sql.append("	,NVL(RD.MAX_NEBIKI_GAKU_VL, 00000000000000.00) AS MAX_NEBIKI_GAKU_VL ");
		sql.append("	,RD.CARD_KB");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		// 2017.04.25 S.Nakazato #4824対応（S)
		// 2017.05.17 M.Son #5044対応 (S)
		//sql.append("	 ,SHIHARAI_JOKEN_CD ");
		sql.append("	 ,RP.SHIHARAI_SYUBETSU_SEQ ");
		// 2017.05.17 M.Son #5044対応 (E)
		// 2017.04.25 S.Nakazato #4824対応（E)
		sql.append(" FROM ");
		sql.append("	R_DISCOUNT RD ");
		// 2017.05.18 M.Son #5044対応 (S)
		sql.append(" LEFT JOIN ");
		sql.append("	R_PAYMENT RP ");
		sql.append(" ON ");
		sql.append("	RD.SHIHARAI_JOKEN_CD = RP.SHIHARAI_SYUBETSU_SUB_CD ");
		// 2017.05.18 M.Son #5044対応 (E)

		return sql.toString();
	}

	/**
	 * @param spaces
	 * @return String
	 */
	private String spaces(int spaces){
		return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
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
	 * システムコントロール情報取得
	 * 
	 * @param なし
	 * @throws Exception
	 *             例外
	 */
	private void getSystemControl() throws Exception {
		maxImumReceipt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.MAX_RECEIPT_QT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
	}

}
