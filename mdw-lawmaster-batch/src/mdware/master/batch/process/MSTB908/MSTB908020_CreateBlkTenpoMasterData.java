package mdware.master.batch.process.MSTB908;

import java.sql.SQLException;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import org.apache.log4j.Level;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;
/**
*
* <p>タイトル: MSTB908020_CreateBlkTenpoMasterData.java クラス</p>
* <p>説明　: TMP店舗マスタからBlynkに連携する店舗マスタデータを作成する</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 (2015.08.07) TAM.NM FIVImart様対応
* @version 1.01 (2016.06.02) M.Kanno #1516 FIVImart様対応
* @version 1.02 (2016.06.09) M.Kanno #1578 FIVImart様対応
* @version 1.03 (2016.10.18) VINX S.Takayama #2238 対応
*
*/
public class MSTB908020_CreateBlkTenpoMasterData {
	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	// バッチ日付
	private String batchDt = null;
	
	// #2238 MSTB908 2016.10.24 S.Takayama (S)
    // 翌日のバッチ日付
    private String yokuBatchDt = null;
    // #2238 MSTB908 2016.10.24 S.Takayama (E)
    
	// テーブル
	private static final String TABLE_IF = "IF_BLK_TENPO"; // IF_BLK店舗マスタ
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	private String jobId =userLog.getJobId();

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB908020_CreateBlkTenpoMasterData(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB908020_CreateBlkTenpoMasterData() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}
	public void execute() throws Exception{

		try {
			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し
			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");
			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDt);
			
			// #2238 MSTB908 2016.10.24 S.Takayama (S)
            yokuBatchDt = DateChanger.addDate(batchDt, 1);
            // #2238 MSTB908 2016.10.24 S.Takayama (E)

			// IF_BLK店舗マスタTRUNCATE
			writeLog(Level.INFO_INT, "IF_BLK店舗マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_BLK店舗マスタを削除処理を終了します。");

			// IF_BLK店舗マスタの登録
			writeLog(Level.INFO_INT, "IF_BLK店舗マスタ登録処理を開始します。");
			iRec = db.executeUpdate(getIfBlkTenpoInsertSql());
			writeLog(Level.INFO_INT, "IF_BLK店舗マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_BLK店舗マスタ登録処理を終了します。");

			db.commit();
			writeLog(Level.INFO_INT, "処理を終了します。");

		//SQLエラーが発生した場合の処理
		}catch (SQLException se) {
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
	 * IF_BLK_TENPOを登録するSQLを取得する
	 *
	 * @return IF_BLK_TENPO登録SQL
	 */
	private String getIfBlkTenpoInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO IF_BLK_TENPO ");
		sql.append("	( ");
		sql.append("	 DATA_MAKE_DT");
		sql.append("	 ,TENPO_CD");
		sql.append("	 ,TENPO_NA");
		sql.append("	 ,TENPO_KB");
		sql.append("	 ,KAITEN_DT");
		sql.append("	 ,HEITEN_DT");
		sql.append("	 ,INSERT_TS");
		sql.append("	 ,INSERT_USER_ID");
		sql.append("	 ,UPDATE_TS");
		sql.append("	 ,UPDATE_USER_ID");
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 MSTB908020 2016.10.18 S.Takayama (S)
		//sql.append("	 '" + batchDt + "' ");
		sql.append("	 '" + yokuBatchDt + "' ");
		// #2238 MSTB908020 2016.10.18 S.Takayama (E)
		//#1516対応 2016.06.02 M.Kanno (S)
		//sql.append("	 ,TMP.TENPO_CD ");
		sql.append("	 ,RPAD(SUBSTR(TMP.TENPO_CD , 3 , 6) , 6 , ' ') TENPO_CD ");
		//#1516対応 2016.06.02 M.Kanno (E)
		sql.append("	 ,TMP.KANJI_NA ");
		sql.append("	 ,TMP.TENPO_KB ");
		sql.append("	 ,TMP.KAITEN_DT ");
		sql.append("	 ,TMP.HEITEN_DT ");
		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	 ,'" + jobId + "' ");
		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	 ,'" + jobId + "' ");
		sql.append("FROM ");
		sql.append("	TMP_R_TENPO TMP ");
		sql.append("	WHERE ");
		sql.append("	TMP.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		//#1578対応 2016.06.02 M.Kanno 店舗区分 7：集計店 以外を抽出(S)
		sql.append("	AND ");
		sql.append("	TMP.TENPO_KB <> '7' ");
		//#1578対応 2016.06.02 M.Kanno (E)

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
