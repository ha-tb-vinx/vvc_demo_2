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
import mdware.master.util.RMSTDATEUtil;
/**
*
* <p>タイトル: MSTB908020_CreateBlkTenpoMasterData.java クラス</p>
* <p>説明　: TMP取引先マスタからBlynkに連携する仕入先マスタデータを作成する</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 (2016.10.13) M.Akagi #2277対応
* @version 1.01 (2016.10.20) M.Akagi #2478対応
* @version 1.02 (2016.10.25) S.Takayama #2238対応
*/
public class MSTB908100_CreateBlkShiiresakiMasterData {
	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	// バッチ日付
	private String batchDt = null;
	
	// #2238 MSTB908 2016.10.25 S.Takayama (S)
	private String yokuBatchDt = null;
	// #2238 MSTB908 2016.10.25 S.Takayama (E)
	
	// テーブル
	private static final String TABLE_IF = "IF_BLK_SHIIRESAKI"; // IF_BLK仕入先マスタ
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	private String jobId =userLog.getJobId();

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB908100_CreateBlkShiiresakiMasterData(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB908100_CreateBlkShiiresakiMasterData() {
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
			// #2238 MSTB908 2016.10.25 S.Takayama (S)
			yokuBatchDt = DateChanger.addDate(batchDt, 1);
			// #2238 MSTB908 2016.10.25 S.Takayama (E)
			//

			// IF_BLK仕入先マスタTRUNCATE
			writeLog(Level.INFO_INT, "IF_BLK仕入先マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_BLK仕入先マスタを削除処理を終了します。");

			// IF_BLK仕入先マスタの登録
			writeLog(Level.INFO_INT, "IF_BLK仕入先マスタ登録処理を開始します。");
			iRec = db.executeUpdate(getIfBlkShiiresakiInsertSql());
			writeLog(Level.INFO_INT, "IF_BLK仕入先マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_BLK仕入先マスタ登録処理を終了します。");

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
	 * IF_BLK_SHIIRESAKIを登録するSQLを取得する
	 *
	 * @return IF_BLK_SHIIRESAKI登録SQL
	 */
	private String getIfBlkShiiresakiInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO IF_BLK_SHIIRESAKI ");
		sql.append("	( ");
		sql.append("	 DATA_MAKE_DT");
		sql.append("	 ,SHIIRESAKI_CD");
		sql.append("	 ,SHIIRESAKI_KANJI_NA");
		sql.append("	 ,INSERT_TS");
		sql.append("	 ,INSERT_USER_ID");
		sql.append("	 ,UPDATE_TS");
		sql.append("	 ,UPDATE_USER_ID");
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 MSTB908 2016.10.25 S.Takayama (S)
		//sql.append("	 '" + batchDt + "' ");
		sql.append("	 '" + yokuBatchDt + "' ");
		// #2238 MSTB908 2016.10.25 S.Takayama (E)
		sql.append("	 ,TMP.TORIHIKISAKI_CD ");
		sql.append("	 ,TMP.TORIHIKISAKI_KANJI_NA ");
		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	 ,'" + jobId + "' ");
		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	 ,'" + jobId + "' ");
		sql.append("FROM ");
		sql.append("	TMP_R_TORIHIKISAKI TMP ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("			SELECT ");
		sql.append("				COMP_CD ");
		sql.append("				,CHOAI_KB ");
		sql.append("				,TORIHIKISAKI_CD ");
		sql.append("				,MAX(TEKIYO_START_DT) TEKIYO_START_DT ");
		//2016.10.20 M.Akagi #2478 (S)
		//sql.append("				,MAX(RIREKI_RB) RIREKI_RB ");
		sql.append("				,SUBSTR(MAX(TMP_R_TORIHIKISAKI.TEKIYO_START_DT || TRIM(TO_CHAR(TMP_R_TORIHIKISAKI.RIREKI_RB,'000'))),9,3) AS RIREKI_RB  ");
		//2016.10.20 M.Akagi #2478 (E)
		sql.append("			FROM ");
		sql.append("				TMP_R_TORIHIKISAKI ");
		sql.append("			WHERE ");
		sql.append("				CHOAI_KB = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' ");
		sql.append("				AND ");
		sql.append("				TEKIYO_START_DT <= '" + batchDt + "' ");
		sql.append("				AND ");
		sql.append("				TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
		sql.append("				AND ");
		sql.append("				TORIHIKISAKI_KB = '" + mst000101_ConstDictionary.TORIHIKISAKI_KB_SHIIRESAKI + "' ");
		sql.append("			GROUP BY ");
		sql.append("				COMP_CD ");
		sql.append("				,CHOAI_KB ");
		sql.append("				,TORIHIKISAKI_CD ");
		sql.append("		) SUB ");
		sql.append("	ON ");
		sql.append("		TMP.COMP_CD = SUB.COMP_CD ");
		sql.append("		AND ");
		sql.append("		TMP.CHOAI_KB = SUB.CHOAI_KB ");
		sql.append("		AND ");
		sql.append("		TMP.TORIHIKISAKI_CD = SUB.TORIHIKISAKI_CD ");
		sql.append("		AND ");
		sql.append("		TMP.TEKIYO_START_DT = SUB.TEKIYO_START_DT ");
		sql.append("		AND");
		sql.append("		TMP.RIREKI_RB = SUB.RIREKI_RB ");
		sql.append("	WHERE ");
		sql.append("		TMP.TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
		sql.append("		AND ");
		sql.append("		TMP.TORIHIKI_TEISHI_KB = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");

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
