package mdware.master.batch.process.MSTB903;

import java.sql.SQLException;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 *
 * <p>タイトル: MSTB903050_IfPopTenbetuSyohinCreate.java クラス</p>
 * <p>説明　: WK_POP店別商品マスタの内容を、IF_POP店別商品マスタに取込む<br>
 *            ZEN_POP店別商品マスタに登録されていてWK_POP店別商品マスタに登録されていない内容を、IF_POP店別商品マスタに取込む</p>
  * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 1.0 2014/06/19 Ha.ntt 海外LAWSON様通貨対応
 *
 */
public class MSTB903050_IfPopTenbetuSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF = "IF_POP_TENBETU_SYOHIN"; // IF_POP_店別商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** メンテ区分(新規・訂正) */
	private static final String MAINTE_KB_SHINKI_TEISEI = "1";
	/** メンテ区分(削除) */
	private static final String MAINTE_KB_SAKUJO = "3";
	/** ゼロ（固定値） */
	private static final String CONST_ZERO = "0";
	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** 店舗コード桁数 */
	//private static final String TENPO_CD_LENGTH = "5";
	private static final String TENPO_CD_LENGTH = "6";
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB903050_IfPopTenbetuSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB903050_IfPopTenbetuSyohinCreate() {
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

			// IF_POP店別商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "IF_POP店別商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_POP店別商品マスタを削除処理を終了します。");

			// IF_POP店別商品マスタの登録(新規分・修正分)
			writeLog(Level.INFO_INT, "IF_POP店別商品マスタ(新規分・修正分)登録処理（WK→ZEN_IF）を開始します。");
			iRec = db.executeUpdate(getIfPopTenbetuSyohinInsertSql());
			writeLog(Level.INFO_INT, "IF_POP店別商品マスタ(新規分・修正分)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POP店別商品マスタ(新規分・修正分)登録処理（WK→ZEN_IF）を終了します。");

			db.commit();

		// IF_POP店別商品マスタの登録(削除分)
			writeLog(Level.INFO_INT, "IF_POP店別商品マスタ(削除分)登録処理（BK→ZEN_IF）を開始します。");
			iRec = db.executeUpdate(getIfPopTenbetuSyohinDelInsertSql());
			writeLog(Level.INFO_INT, "IF_POP店別商品マスタ(削除分)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POP店別商品マスタ(削除分)登録処理（BK→ZEN_IF）を終了します。");

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
	 * IF_POP_TENBETU_SYOHINを登録するSQLを取得する(新規分・修正分)
	 *
	 * @return IF_POP_TENBETU_SYOHIN登録SQL(新規分・修正分)
	 */
	private String getIfPopTenbetuSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_POP_TENBETU_SYOHIN ");
		sql.append("	( ");
		sql.append("	 MAINTE_KB ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,TENPO_CD ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,TAX_RT ");
		sql.append("	,BAITANKA_NUKI_VL ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,HACHU_TANI ");
		sql.append("	,AUTO_HAT_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + MAINTE_KB_SHINKI_TEISEI + "'                                         AS MAINTE_KB ");
		sql.append("	,WPTS.SYOHIN_CD                                                            AS SYOHIN_CD ");
		sql.append("	,LPAD(TRIM(WPTS.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,WPTS.BAITANKA_VL                                                          AS BAITANKA_VL ");
		sql.append("	,WPTS.TAX_RT                                                               AS TAX_RT ");
		sql.append("	,GET_ZEINUKI_MARUME(WPTS.BAITANKA_VL, " + MoneyUtil.getFractionSellUnitLen() + ", WPTS.ZEI_KB, WPTS.TAX_RT, ");
		sql.append(MoneyUtil.getFractionSellUnitMode() + ") AS BAITANKA_NUKI_VL ");
		sql.append("	,WPTS.SIIRESAKI_CD                                                         AS SIIRESAKI_CD ");
		sql.append("	,WPTS.HACHUTANI_IRISU_QT                                                   AS HACHU_TANI ");
		sql.append("	,''                                                                        AS AUTO_HAT_KB ");
		sql.append("	,'" + userLog.getJobId() + "'                                              AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                         AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                              AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                         AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_POP_TENBETU_SYOHIN WPTS ");
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 SYOHIN_CD ");
		sql.append("			,TENPO_CD ");
		sql.append("			,BAITANKA_VL ");
		sql.append("			,ZEI_KB ");
		sql.append("			,TAX_RATE_KB ");
		sql.append("			,TAX_RT ");
		sql.append("			,SIIRESAKI_CD ");
		sql.append("			,HACHUTANI_IRISU_QT ");
		sql.append("		FROM ");
		sql.append("			ZEN_POP_TENBETU_SYOHIN ");
		sql.append("		) ZPTS ");
		sql.append("		ON ");
		sql.append("			WPTS.SYOHIN_CD	= ZPTS.SYOHIN_CD	AND ");
		sql.append("			WPTS.TENPO_CD	= ZPTS.TENPO_CD ");
		sql.append("WHERE ");
		sql.append("	NVL(WPTS.SYOHIN_CD, ' ')				<> NVL(ZPTS.SYOHIN_CD, ' ')			OR ");
		sql.append("	NVL(WPTS.BAITANKA_VL, 0)				<> NVL(ZPTS.BAITANKA_VL, 0)			OR ");
		sql.append("	NVL(WPTS.ZEI_KB, ' ')					<> NVL(ZPTS.ZEI_KB, ' ')			OR ");
		sql.append("	NVL(WPTS.TAX_RATE_KB, ' ')				<> NVL(ZPTS.TAX_RATE_KB, ' ')		OR ");
		sql.append("	NVL(WPTS.TAX_RT, 0)						<> NVL(ZPTS.TAX_RT, 0)				OR ");
		sql.append("	NVL(WPTS.SIIRESAKI_CD, ' ')				<> NVL(ZPTS.SIIRESAKI_CD, ' ')		OR ");
		sql.append("	NVL(WPTS.HACHUTANI_IRISU_QT, 0)			<> NVL(ZPTS.HACHUTANI_IRISU_QT, 0) ");

		return sql.toString();
	}

	/**
	 * IF_POP_TENBETU_SYOHINを登録するSQLを取得する(削除分)
	 *
	 * @return IF_POP_TENBETU_SYOHIN登録SQL(削除分)
	 */
	private String getIfPopTenbetuSyohinDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_POP_TENBETU_SYOHIN ");
		sql.append("	( ");
		sql.append("	 MAINTE_KB ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,TENPO_CD ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,TAX_RT ");
		sql.append("	,BAITANKA_NUKI_VL ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,HACHU_TANI ");
		sql.append("	,AUTO_HAT_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + MAINTE_KB_SAKUJO + "'                                                AS MAINTE_KB ");
		sql.append("	,ZPTS.SYOHIN_CD                                                            AS SYOHIN_CD ");
		sql.append("	,LPAD(TRIM(ZPTS.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	," + CONST_ZERO + "                                                        AS BAITANKA_VL ");
		sql.append("	," + CONST_ZERO + "                                                        AS TAX_RT ");
		sql.append("	," + CONST_ZERO + "                                                        AS BAITANKA_NUKI_VL ");
		sql.append("	,''                                                                        AS SIIRESAKI_CD ");
		sql.append("	," + CONST_ZERO + "                                                        AS HACHU_TANI ");
		sql.append("	,''                                                                        AS AUTO_HAT_KB ");
		sql.append("	,'" + userLog.getJobId() + "'                                              AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                         AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                              AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                         AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	ZEN_POP_TENBETU_SYOHIN ZPTS ");
		sql.append("WHERE ");
		sql.append("	NOT EXISTS ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 WPTS.SYOHIN_CD ");
		sql.append("			,WPTS.TENPO_CD ");
		sql.append("		FROM ");
		sql.append("			WK_POP_TENBETU_SYOHIN WPTS ");
		sql.append("		WHERE ");
		sql.append("			WPTS.SYOHIN_CD	= ZPTS.SYOHIN_CD	AND ");
		sql.append("			WPTS.TENPO_CD	= ZPTS.TENPO_CD ");
		sql.append("		) ");

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
