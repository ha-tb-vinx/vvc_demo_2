package mdware.master.batch.process.MSTB910;

import java.sql.SQLException;
import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB910060_WkHamperKoseiUpdate.java クラス</p>
 * <p>説明　: WKハンパー構成の売価配信フラグを更新する。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.03.29) #4417対応 T.Han 新規作成
 * @version 1.01 (2017.04.24) #4775対応 S.Takayama
 */
public class MSTB910060_WkHamperKoseiUpdate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** 特殊日付("99999999") */
	private static final String SPECIAL_DATE = "99999999";

	// バッチ日付
	private String batchDt = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB910060_WkHamperKoseiUpdate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB910060_WkHamperKoseiUpdate() {
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

			// WK_ハンパー構成 の更新
			writeLog(Level.INFO_INT, "WK_ハンパー構成 更新処理を開始します。");
			iRec = db.executeUpdate(getWkHamperKoseiUpdateSql());
			writeLog(Level.INFO_INT, "WK_ハンパー構成 を" + iRec + "件更新しました。");
			writeLog(Level.INFO_INT, "WK_ハンパー構成 更新処理を終了します。");

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
	 * WK_HAMPER_KOSEIを更新するSQLを取得する
	 * 
	 * @return WK_HAMPER_KOSEI更新SQL
	 */
	private String getWkHamperKoseiUpdateSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

//		sql.append(" UPDATE ");
//		sql.append("     WK_HAMPER_KOSEI WHK ");
//		sql.append(" SET ");
//		sql.append("     WHK.BAIKA_HAISHIN_FG =  ");
//		sql.append("         (  ");
//		sql.append("             SELECT  ");
//		sql.append("                 TRETR.BAIKA_HAISHIN_FG ");
//		sql.append("             FROM  ");
//		sql.append("             WK_HAMPER_KOSEI WHK ");
//		sql.append("             INNER JOIN ");
//		sql.append("                 TMP_R_EMG_TENSYOHIN_REIGAI TRETR ");
//		sql.append("             ON ");
//		sql.append("                 TRETR.SYOHIN_CD  = WHK.HAMPER_SYOHIN_CD AND ");
//		sql.append("                 TRETR.TENPO_CD  = WHK.TENPO_CD ");
//		sql.append("            ,( ");
//		sql.append("                 SELECT ");
//		sql.append("                     MAX(YUKO_DT) AS YUKO_DT ");
//		sql.append("                    ,SYOHIN_CD AS SYOHIN_CD");
//		sql.append("                 FROM ");
//		sql.append("                     TMP_R_EMG_TENSYOHIN_REIGAI ");
//		sql.append("                 WHERE ");
//		sql.append("                     (PLU_SEND_DT <= '" + batchDt + "' OR PLU_SEND_DT = '" + SPECIAL_DATE + "') AND ");
//		sql.append("                     YUKO_DT <= '" + batchDt + "' ");
//		sql.append("                 GROUP BY ");
//		sql.append("                     SYOHIN_CD ");
//		sql.append("             ) SUB ");
//		sql.append("            WHERE ");
//		sql.append("                TRETR.YUKO_DT = SUB.YUKO_DT AND ");
//		sql.append("                TRETR.SYOHIN_CD = SUB.SYOHIN_CD ");
//		sql.append("         ) ");
		sql.append(" MERGE INTO ");
		sql.append("     WK_HAMPER_KOSEI WHK ");
		sql.append(" USING ");
		sql.append("          ( ");
		sql.append("              SELECT /*+ BYPASS_UJVC */ ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("                   TRETR.BAIKA_HAISHIN_FG AS BAIKA_HAISHIN_FG ");
		sql.append("                   CASE ");
		sql.append("                   WHEN TRES.DELETE_FG = '1' OR TRETR.DELETE_FG = '1' ");
		sql.append("                   THEN '1' ");
		sql.append("                   ELSE NVL(TRETR.BAIKA_HAISHIN_FG, TRES.BAIKA_HAISHIN_FG ) ");
		sql.append("                   END AS BAIKA_HAISHIN_FG ");
		sql.append("                  ,TRES.SYOHIN_CD AS TRES_SYOHIN_CD ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		sql.append("                  ,TRETR.SYOHIN_CD AS SYOHIN_CD ");
		sql.append("                  ,TRETR.TENPO_CD AS TENPO_CD ");
		sql.append("                  ,TRETR.YUKO_DT AS YUKO_DT ");
		sql.append("              FROM  ");
		sql.append("                  TMP_R_EMG_TENSYOHIN_REIGAI TRETR ");
		sql.append("                 ,(  ");
		sql.append("                      SELECT  ");
		sql.append("                          MAX(YUKO_DT) AS YUKO_DT ");
		sql.append("                         ,SYOHIN_CD AS SYOHIN_CD ");
		sql.append("                      FROM  ");
		sql.append("                          TMP_R_EMG_TENSYOHIN_REIGAI ");
		sql.append("                      WHERE  ");
		sql.append("                          (PLU_SEND_DT <= '" + batchDt + "' OR PLU_SEND_DT = '" + SPECIAL_DATE + "') AND ");
		sql.append("                          YUKO_DT <= '" + batchDt + "'  ");
		sql.append("                      GROUP BY ");
		sql.append("                          SYOHIN_CD ");
		sql.append("                  ) SUB ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		sql.append("              INNER JOIN  ");
		sql.append("                    TMP_R_EMG_SYOHIN TRES ");
		sql.append("                    ON TRES.YUKO_DT = ( ");
		sql.append("                                        SELECT ");
		sql.append("                                              MAX(TRES1.YUKO_DT) AS YUKO_DT ");
		sql.append("                                        FROM TMP_R_EMG_SYOHIN TRES1 ");
		sql.append("                                        WHERE TRES1.YUKO_DT <= '" + batchDt + "' ");
		sql.append("                                        AND TRES1.PLU_SEND_DT <= '" + batchDt + "' ");
		sql.append("                                        AND TRES1.SYOHIN_CD = TRES.SYOHIN_CD ");
		sql.append("                                        ) ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		sql.append("              WHERE ");
		sql.append("                  TRETR.YUKO_DT = SUB.YUKO_DT AND ");
		sql.append("                  TRETR.SYOHIN_CD = SUB.SYOHIN_CD ");
		sql.append("          ) MAIN_DATA  ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append(" ON ( MAIN_DATA.SYOHIN_CD = WHK.HAMPER_SYOHIN_CD AND ");
		sql.append(" ON ( MAIN_DATA.SYOHIN_CD = WHK.KOSEI_SYOHIN_CD AND ");
		sql.append("      MAIN_DATA.TRES_SYOHIN_CD = WHK.KOSEI_SYOHIN_CD AND ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		sql.append("      MAIN_DATA.TENPO_CD = WHK.TENPO_CD ) ");
		sql.append(" WHEN MATCHED THEN ");
		sql.append("  UPDATE SET ");
		sql.append("       WHK.BAIKA_HAISHIN_FG = MAIN_DATA.BAIKA_HAISHIN_FG ");
		sql.append("      ,WHK.UPDATE_USER_ID = '" + userLog.getJobId() + "' ");
		sql.append("      ,WHK.UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");

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
