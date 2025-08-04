package mdware.master.batch.process.MSTB09;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.mdware.common.util.DateChanger;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;
/**
*
* <p>タイトル: 退役処理(POSIFデータ)</p>
* <p>説明　: POSIFの累積情報の削除処理を行う。</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @Version 1.00 2017.06.05 S.Takayama #5009対応
*/

public class MSTB090201_IFPosRetiredProcess {
	
	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	/* バッチ日付 */
	private String batchDt = null;
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 保持期間(履歴) */
	private Map deleteIntervalPosIf = ResorceUtil.getInstance().getPropertieMap("DELETE_INTERVAL_POSIF", "MASTER");
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB090201_IFPosRetiredProcess(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}
	
	/**
	 * コンストラクタ
	 */
	public MSTB090201_IFPosRetiredProcess() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception{
		try {
			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;
			
			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し
			
			// ディクショナリコントロールマスタのデータ取得
			Iterator iterator = deleteIntervalPosIf.entrySet().iterator();
			
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				String tableNa = (String) entry.getKey();
				String interval = (String) entry.getValue();
				
				// マスタ削除基準日数取得
				batchDt = RMSTDATEUtil.getBatDateDt();
				String baseDt = DateChanger.addDate(batchDt, Integer.parseInt(interval) * (-1));
				
				// 履歴データの削除
				writeLog(Level.INFO_INT, "削除処理 開始 (BK_IF_" + tableNa + ") 処理日：" + baseDt + " 以前のデータを削除します。");
				iRec = db.executeUpdate(getDeleteHistorySql(tableNa, baseDt));
				writeLog(Level.INFO_INT, "削除処理 終了 (BK_IF_" + tableNa + ") " + iRec + "件");
				
			}
			db.commit();
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
	 * 履歴データの削除
	 *
	 * @return 履歴データの削除SQL
	 */
	public String getDeleteHistorySql(String tableNa, String baseDt) throws Exception{
		StringBuilder sql= new StringBuilder();
		
		sql.append(" DELETE FROM BK_IF_" + tableNa );
		sql.append(" WHERE ");
		sql.append("	SYORI_DT < '" + baseDt + "' ");
		
		return sql.toString();
	}
	
	/******* 共通処理 **********/

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
