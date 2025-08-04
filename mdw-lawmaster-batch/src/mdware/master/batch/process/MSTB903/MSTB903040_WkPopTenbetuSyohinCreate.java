package mdware.master.batch.process.MSTB903;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst010101_SyohinKbDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB903040_WkPopTenbetuSyohinCreate.java クラス</p>
 * <p>説明　: TMP店別商品例外マスタの内容を、WK_POP店別商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.06) T.Ooshiro [CUS00066] ランドローム様対応 POPインターフェイス仕様変更対応対応
 *
 */
public class MSTB903040_WkPopTenbetuSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK = "WK_POP_TENBETU_SYOHIN"; // WK_POP店別商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB903040_WkPopTenbetuSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB903040_WkPopTenbetuSyohinCreate() {
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

			// WK_POP店別商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "WK_POP店別商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK);
			writeLog(Level.INFO_INT, "WK_POP店別商品マスタを削除処理を終了します。");

			// WK_POP店別商品マスタの登録
			writeLog(Level.INFO_INT, "WK_POP店別商品マスタ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkPopSyohinInsertSql());
			writeLog(Level.INFO_INT, "WK_POP店別商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_POP店別商品マスタ登録処理（TMP→WK）を終了します。");

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
	 * WK_POP_TENBETU_SYOHINを登録するSQLを取得する
	 * 
	 * @return WK_POP_TENBETU_SYOHIN登録SQL
	 */
	private String getWkPopSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO WK_POP_TENBETU_SYOHIN ");
		sql.append("	( ");
		sql.append("	 SYOHIN_CD ");
		sql.append("	,TENPO_CD ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,ZEI_KB ");
		sql.append("	,TAX_RATE_KB ");
		sql.append("	,TAX_RT ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,HACHUTANI_IRISU_QT ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 TRTR.SYOHIN_CD                            AS SYOHIN_CD ");
		sql.append("	,TRTR.TENPO_CD                             AS TENPO_CD ");
		sql.append("	,NVL(TRTR.BAITANKA_VL , TRS.BAITANKA_VL)   AS BAITANKA_VL ");
		sql.append("	,TRS.ZEI_KB                                AS ZEI_KB ");
		sql.append("	,TRS.TAX_RATE_KB                           AS TAX_RATE_KB ");
		sql.append("	,TRTR1.TAX_RT                              AS TAX_RT ");
		sql.append("	,NVL(TRTR.SIIRESAKI_CD, TRS.SIIRESAKI_CD)  AS SIIRESAKI_CD ");
		sql.append("	,TRS.HACHUTANI_IRISU_QT                    AS HACHUTANI_IRISU_QT ");
		sql.append("	,'" + userLog.getJobId() + "'              AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                         AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'              AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                         AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	TMP_R_TENSYOHIN_REIGAI TRTR ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRTR.SYOHIN_CD ");
		sql.append("			,TRTR.TENPO_CD ");
		sql.append("			,MAX(TRTR.YUKO_DT)	AS YUKO_DT ");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENSYOHIN_REIGAI TRTR ");
		sql.append("		WHERE ");
		sql.append("			TRTR.YUKO_DT	<= '" + yokuBatchDt + "' ");
		sql.append("		GROUP BY ");
		sql.append("			 TRTR.SYOHIN_CD ");
		sql.append("			,TRTR.TENPO_CD ");
		sql.append("		) TRTR1 ");
		sql.append("		ON ");
		sql.append("			TRTR.SYOHIN_CD	= TRTR1.SYOHIN_CD	AND ");
		sql.append("			TRTR.TENPO_CD	= TRTR1.TENPO_CD	AND ");
		sql.append("			TRTR.YUKO_DT	= TRTR1.YUKO_DT ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRS.SYOHIN_CD ");
		sql.append("			,TRS.BAITANKA_VL ");
		sql.append("			,TRS.ZEI_KB ");
		sql.append("			,TRS.TAX_RATE_KB ");
		sql.append("			,TRS.SIIRESAKI_CD ");
		sql.append("			,TRS.HACHUTANI_IRISU_QT ");
		sql.append("		FROM ");
		sql.append("			TMP_R_SYOHIN TRS ");
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("				SELECT ");
		sql.append("					 TRS.SYOHIN_CD ");
		sql.append("					,MAX(TRS.YUKO_DT)	AS YUKO_DT ");
		sql.append("				FROM ");
		sql.append("					TMP_R_SYOHIN TRS ");
		sql.append("				WHERE ");
		sql.append("					TRS.YUKO_DT						<= '" + yokuBatchDt + "'	AND ");
		sql.append("					GET_JAN_SYUBETSU(TRS.SYOHIN_CD)	<> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_23 + "'	AND ");
		sql.append("					GET_JAN_SYUBETSU(TRS.SYOHIN_CD)	<> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "' ");
		sql.append("				GROUP BY ");
		sql.append("					TRS.SYOHIN_CD ");
		sql.append("				) TRS1 ");
		sql.append("				ON ");
		sql.append("					TRS.SYOHIN_CD	= TRS1.SYOHIN_CD	AND ");
		sql.append("					TRS.YUKO_DT		= TRS1.YUKO_DT ");
		sql.append("		WHERE ");
		sql.append("			TRS.DELETE_FG	 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'	AND ");
		sql.append("			TRS.SYOHIN_KB	<> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "' ");
		sql.append("		) TRS ");
		sql.append("		ON ");
		sql.append("			TRTR.SYOHIN_CD	= TRS.SYOHIN_CD ");
		// 税率マスタ
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRTR.TAX_RATE_KB ");
		sql.append("			,TRTR.TAX_RT ");
		sql.append("		FROM ");
		sql.append("			TMP_R_TAX_RATE TRTR ");
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("				SELECT ");
		sql.append("					 TRTR.TAX_RATE_KB ");
		sql.append("					,MAX(TRTR.YUKO_DT) AS YUKO_DT ");
		sql.append("				FROM ");
		sql.append("					TMP_R_TAX_RATE TRTR ");
		sql.append("				WHERE ");
		sql.append("					TRTR.YUKO_DT	<= '" + yokuBatchDt + "' ");
		sql.append("				GROUP BY ");
		sql.append("					TRTR.TAX_RATE_KB ");
		sql.append("				) TRTR1 ");
		sql.append("				ON ");
		sql.append("					TRTR.TAX_RATE_KB	= TRTR1.TAX_RATE_KB	AND ");
		sql.append("					TRTR.YUKO_DT		= TRTR1.YUKO_DT ");
		sql.append("		WHERE ");
		sql.append("			TRTR.DELETE_FG	 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRTR1 ");
		sql.append("		ON ");
		sql.append("			TRS.TAX_RATE_KB	= TRTR1.TAX_RATE_KB ");
		sql.append("WHERE ");
		sql.append("	TRTR.DELETE_FG	 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");

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
