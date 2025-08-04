package mdware.master.batch.process.MSTB904;

import java.sql.SQLException;

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
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB904060_WkDWHTenbetuSyohinCreate.java クラス</p>
 * <p>説明　: 店別商品マスタの内容を、WK_DWH_店別商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.08) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 * @version 3.01 (2013.12.12) T.Ooshiro [CUS00059] D3(DWH)システムインターフェイス仕様変更対応 結合テスト課題対応 №008, №009
 * @version 3.02 (2013.12.13) T.Ooshiro [CUS00059] D3(DWH)システムインターフェイス仕様変更対応 結合テスト課題対応 №008, №014
 * @version 3.03 (2013.12.24) T.Ooshiro [CUS00059] D3(DWH)システムインターフェイス仕様変更対応 チェックディジット対応
 *
 */
public class MSTB904060_WkDWHTenbetuSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK = "WK_DWH_TENBETU_SYOHIN"; // WK_DWH_店別商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;

	/** 値入率：切上げ用補正値 */
	private static final String NEIRE_RT_ROUNDUP_CORRECTION_VALUE = "10000";
	/** 金額項目エラー判定値 */
	private static final String KINGAKU_ERROR_CHECK_VL = "999999";
	/** 定数ZERO */
	private static final String CONST_ZERO = "0";

	/** DWHC/Dフラグ */
	private static final String DWH_CD_FG = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.DWH_CD_FG);
	/** 商品コード開始位置 */
	private static final String SYOHIN_CD_ST_COLUMN = "1";
	/** 商品コード桁数 */
	private static final String SYOHIN_CD_LENGTH = "12";
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904060_WkDWHTenbetuSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904060_WkDWHTenbetuSyohinCreate() {
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
			yokuBatchDt = DateChanger.addDate(batchDt, 1);

			// WK_DWH_商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "WK_DWH_店別商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK);
			writeLog(Level.INFO_INT, "WK_DWH_店別商品マスタを削除処理を終了します。");

			// WK_DWH_商品マスタの登録
			writeLog(Level.INFO_INT, "WK_DWH_店別商品マスタ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkTenbetuDwhSyohinInsertSql());
			writeLog(Level.INFO_INT, "WK_DWH_店別商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_DWH_店別商品マスタ登録処理（TMP→WK）を終了します。");

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
	 * WK_DWH_TENBETU_SYOHINを登録するSQLを取得する
	 * 
	 * @return WK_DWH_TENBETU_SYOHIN登録SQL
	 */
	private String getWkTenbetuDwhSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_DWH_TENBETU_SYOHIN ");
		sql.append("	( ");
		sql.append("	 KIGYO_1_CD ");
		sql.append("	,KIGYO_2_CD ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,GENTANKA_VL ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,NEIRE_RT ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,ERR_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + mst000102_IfConstDictionary.DWH_COMP_CD + "' AS KIGYO_1_CD ");
		sql.append("	,'" + mst000102_IfConstDictionary.DWH_COMP_CD + "' AS KIGYO_2_CD ");
		sql.append("	,TRIM(DTS.TENPO_CD) AS TENPO_CD ");
		if (DWH_CD_FG.equals(mst000101_ConstDictionary.DWH_CD_ON)) {

			sql.append("	,CASE GET_JAN_SYUBETSU(DTS.SYOHIN_CD) ");
			sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_02 + "' ");
			sql.append("			THEN ");
			sql.append("				SUBSTR(DTS.SYOHIN_CD, " + SYOHIN_CD_ST_COLUMN + ", " + SYOHIN_CD_LENGTH + ") ||  CALC_CHECK_DIGIT(DTS.SYOHIN_CD) ");
			sql.append("		ELSE ");
			sql.append("			DTS.SYOHIN_CD ");
			sql.append("	 END ");
		
		} else {
			sql.append("	,DTS.SYOHIN_CD ");
		}
		sql.append("	,DTS.GENTANKA_VL ");
		sql.append("	,DTS.BAITANKA_VL ");
		sql.append("	,CASE ");
		sql.append("		WHEN ");
		sql.append("			DTS.GENTANKA_VL IS NOT NULL AND ");
		sql.append("			DTS.BAITANKA_VL IS NOT NULL AND ");
		sql.append("			DTS.BAITANKA_VL <> " + CONST_ZERO + " AND ");
		sql.append("			DTS.BAITANKA_VL > DTS.GENTANKA_VL ");
		sql.append("				THEN CEIL((((DTS.BAITANKA_VL - DTS.GENTANKA_VL) / DTS.BAITANKA_VL)) * " + NEIRE_RT_ROUNDUP_CORRECTION_VALUE + ") / " + NEIRE_RT_ROUNDUP_CORRECTION_VALUE + " ");
		sql.append("		ELSE " + CONST_ZERO + " ");
		sql.append("	 END AS NEIRE_RT ");
		sql.append("	,TRIM(TO_CHAR(TO_NUMBER(DTS.SIIRESAKI_CD))) ");
		sql.append("	,CASE ");
		sql.append("		WHEN DTS.GENTANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		sql.append("		WHEN DTS.BAITANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		sql.append("		ELSE '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");
		sql.append("	 END AS ERR_KB ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	DT_TENBETU_SYOHIN DTS ");
		sql.append("	INNER JOIN ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			 DTS.TENPO_CD ");
		sql.append("			,DTS.SYOHIN_CD ");
		sql.append("			,MAX(DTS.YUKO_DT) AS YUKO_DT ");
		sql.append("		FROM ");
		sql.append("			DT_TENBETU_SYOHIN DTS ");
		sql.append("		WHERE ");
		sql.append("			DTS.YUKO_DT <= '" + yokuBatchDt + "' ");
		sql.append("		GROUP BY ");
		sql.append("			 DTS.TENPO_CD ");
		sql.append("			,DTS.SYOHIN_CD ");
		sql.append("	) DTS2 ");
		sql.append("	ON ");
		sql.append("		DTS.TENPO_CD	= DTS2.TENPO_CD		AND ");
		sql.append("		DTS.SYOHIN_CD	= DTS2.SYOHIN_CD	AND ");
		sql.append("		DTS.YUKO_DT		= DTS2.YUKO_DT ");
		sql.append("WHERE ");
		sql.append("	DTS.SYOHIN_DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");

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
