package mdware.master.batch.process.MSTB910;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst910020_EmgFlagDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p> タイトル: MSTB910030_KinkyuuHaishinResetSyoriクラス</p>
 * <p> 説明: 緊急配信リセット処理</p>
 * <p> 著作権: Copyright (c) 2015 </p>
 * <p> 会社名: Vinculum Japan Corporation </p>
 * @author VINX
 * @Version 1.00 (2015.10.15) DUNG.NQ FIVIMART対応
 * @version 1.01 (2016.09.16) VINX h.sakamoto #1954対応
 * @version 1.02 (2016.09.27) VINX M.Akagi #1954対応
 * @version 1.03 (2016.09.30) VINX M.Akagi #2228対応
 * @version 1.03 (2016.12.05) VINX M.Akagi #3102対応
 * @version 1.04 (2022.11.09) VU.TD #6698 MKH対応
 */

public class MSTB910030_KinkyuuHaishinResetSyori {

	private MasterDataBase db = null;

	// ログ出力用変数
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	//2016.09.27 M.Akagi #1954対応 (S)
	// バッチ日付
    private String batchDt = null;
    //2016.09.27 M.Akagi #1954対応 (E)

	/**
	 * コンストラクタ
	 *
	 * @param dataBase
	 */
	public MSTB910030_KinkyuuHaishinResetSyori(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB910030_KinkyuuHaishinResetSyori() {
		this(new MasterDataBase("rbsite_ora"));
	}

	/**
	 * 外部からの実行メソッド
	 * @param なんし
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {

		// jobId
		String jobId = userLog.getJobId();

		// バッチ登録件数をカウント
		int iRec = 0;

		try {
			//2016.09.27 M.Akagi #1954対応 (S)
			// バッチ日付
            batchDt = RMSTDATEUtil.getBatDateDt();
			//2016.09.27 M.Akagi #1954対応 (E)

			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "を開始します。");

			writeLog(Level.INFO_INT, "商品マスタテーブルの更新処理を開始します。");
			// 商品マスタテーブルの緊急配信フラグを更新
			iRec = db.executeUpdate(updateEmgFlagOfSyohinSql());

			writeLog(Level.INFO_INT, "商品マスタテーブルを" + iRec + "件更新しました。");
			writeLog(Level.INFO_INT, "商品マスタテーブルの更新処理を終了します。");

			//2016.09.30 M.Akagi #2228対応 (S)
			writeLog(Level.INFO_INT, "店別商品例外マスタテーブルの更新処理を開始します。");
			// 店別商品例外マスタテーブルの緊急配信フラグを更新
			iRec = db.executeUpdate(updateEmgFlagOfSyohinReigaiSql());

			writeLog(Level.INFO_INT, "店別商品例外マスタテーブルを" + iRec + "件更新しました。");
			writeLog(Level.INFO_INT, "店別商品例外マスタテーブルの更新処理を終了します。");
			//2016.09.30 M.Akagi #2228対応 (E)

			writeLog(Level.INFO_INT, "PLU配信シーケンス管理テーブルの更新処理を開始します。");
			// PLU配信シーケンス管理テーブルのファイルNoを更新
			iRec = db.executeUpdate(updateFileNoOfPosFileSeqSql());

			writeLog(Level.INFO_INT, "PLU配信シーケンス管理テーブルを" + iRec + "件更新しました。");
			writeLog(Level.INFO_INT, "PLU配信シーケンス管理テーブルの更新処理を終了します。");

			db.commit();

			// SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			db.rollback();
			// ログ出力
			writeError(se);
			throw se;

			// その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			// ログ出力
			writeError(e);
			throw e;

			// SQL終了処理
		} finally {
			db.close();
			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}

	}

	/**
	 * 商品マスタテーブルの緊急配信フラグを更新する
	 * @param     なんし
	 * @return SQL文
	 */
	private String updateEmgFlagOfSyohinSql() throws SQLException {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" UPDATE ");
		strSql.append("        R_SYOHIN RS ");
		strSql.append("    SET ");
		strSql.append("        RS.EMG_FLAG = '"+ mst910020_EmgFlagDictionary.OFF.getCode() +"'"); // 緊急配信フラグ = 【0】：オフ
		strSql.append("  WHERE ");
		strSql.append("        RS.EMG_FLAG  = '"+ mst910020_EmgFlagDictionary.ON.getCode() +"'");// 緊急配信フラグ = 【1】：オン
		//2016.09.27 M.Akagi #1954対応 (S)
		strSql.append("        AND");
		// #6698 MOD 2022.11.08 VU.TD (S)
		//	strSql.append("        RS.PLU_SEND_DT  = '"+ batchDt +"'");
		strSql.append("        (RS.PLU_SEND_DT  = '"+ batchDt +"'");
		strSql.append("        OR	");
		strSql.append("        EXISTS (	");
		strSql.append("        SELECT DISTINCT SYOHIN_CD	");
		strSql.append("        FROM	");
		strSql.append("        		BK_IF_PLU_TANPIN BIPT");
		strSql.append("        WHERE	");
		strSql.append("        		RS.SYOHIN_CD = 	BIPT.SYOHIN_CD	");
		strSql.append("        AND	BIPT.SYORI_DT = '" + DateChanger.addDate(batchDt, -1) + "'	");
		strSql.append("        )	");
		strSql.append("        )	");
		// #6698 MOD 2022.11.08 VU.TD (E)
		//2016.09.27 M.Akagi #1954対応 (E)
		return strSql.toString();
	}

	//2016.09.30 M.Akagi #2228対応 (S)
	/**
	 * 店別商品例外マスタテーブルの緊急配信フラグを更新する
	 * @param     なんし
	 * @return SQL文
	 */
	private String updateEmgFlagOfSyohinReigaiSql() throws SQLException {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" UPDATE ");
		strSql.append("        R_TENSYOHIN_REIGAI RTR ");
		strSql.append("    SET ");
		strSql.append("        RTR.EMG_FLAG = '"+ mst910020_EmgFlagDictionary.OFF.getCode() +"'"); // 緊急配信フラグ = 【0】：オフ
		strSql.append("  WHERE ");
		strSql.append("        RTR.EMG_FLAG  = '"+ mst910020_EmgFlagDictionary.ON.getCode() +"'");// 緊急配信フラグ = 【1】：オン
		strSql.append("        AND");
		// #6698 MOD 2022.11.08 VU.TD (S)
		//	strSql.append("        RTR.PLU_SEND_DT  = '"+ batchDt +"'");
		strSql.append("        (RTR.PLU_SEND_DT  = '"+ batchDt +"'");
		strSql.append("        OR	");
		strSql.append("        EXISTS (	");
		strSql.append("        SELECT DISTINCT SYOHIN_CD	");
		strSql.append("        FROM	");
		strSql.append("        		BK_IF_PLU_TANPIN	BIPT ");
		strSql.append("        WHERE	");
		strSql.append("        		RTR.SYOHIN_CD = BIPT.SYOHIN_CD	");
		strSql.append("        AND 	RTR.TENPO_CD = BIPT.TENPO_CD	");
		strSql.append("        AND	BIPT.SYORI_DT = '" + DateChanger.addDate(batchDt, -1) + "'	");
		strSql.append("        )	");
		strSql.append("        )	");
		// #6698 MOD 2022.11.08 VU.TD (E)
		return strSql.toString();
	}
	//2016.09.30 M.Akagi #2228対応 (E)

	/**
	 * PLU配信シーケンス管理テーブルのファイルNoを更新する
	 * @param     なんし
	 * @return SQL文
	 */
	private String updateFileNoOfPosFileSeqSql() throws SQLException {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" UPDATE ");
		strSql.append("        POS_FILE_SEQ PFS ");
		strSql.append("    SET ");
		strSql.append("        PFS.FILENO = '2' "); // ファイルNo = '2'
		// 2016.12.05 M.Akagi #3102 (S)
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
		//strSql.append("       ,PFS.SEND_TIME = '0800' "); // PLU反映時間 = '0800'
		strSql.append("       ,PFS.SEND_TIME = '0730' "); // PLU反映時間 = '0730'
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
		// 2016.12.05 M.Akagi #3102 (E)

		return strSql.toString();
	}

	/********* 以下、ログ出力用メソッド *********/

	/**
	 * ユーザーログとバッチログにログを出力します。
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message) {
		String jobId = userLog.getJobId();

		switch (level) {
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}

	/**
	 * エラーをログファイルに出力します。 ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}
}