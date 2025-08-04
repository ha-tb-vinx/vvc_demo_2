package mdware.master.common.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * *<P>タイトル:  PLU指定日マスタ取得処理</P>
 * <P>説明:      PLU指定日マスタ取得クラス。</P>
 * <P>著作権:	 Copyright (c) 2017</P>
 * <P>会社名:	 Vinculum Japan Corp.</P>
 *
 * @author VINX
 * @Version 1.00  (2017.01.17) M.Akagi #1749
 * @see なし
 */
public class MstbGetPluMaster {
	/** DB接続フラグ */
	private boolean closeDb = false;
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// 取得結果
	private ResultSet rs = null;

	/**
	 * コンストラクタ
	 *
	 * @param dataBase
	 */
	public MstbGetPluMaster(DataBase db) {
		if (db == null) {
			new DataBase(CONNECTION_STR);
			closeDb = true;
			}
	}
	/**
	 * コンストラクタ
	 *
	 * @param dataBase
	 */
	public MstbGetPluMaster() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}

	/**
	 * 本処理
	 *
	 * @return 処理したResultSet
	 *
	 * @throws Exception
	 */
	public ResultSet process(DataBase db) throws Exception {
		try {
			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// PLU指定日マスタ取得処理
			writeLog(Level.INFO_INT, "PLU指定日マスタ取得処理を開始します。");
			rs = db.executeQuery(getPluShiteibiMaster());
			writeLog(Level.INFO_INT, "PLU指定日マスタ取得処理を終了します。");

			//SQLエラーが発生した場合の処理
		}  catch (SQLException se) {
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
		}

		return rs;
	}

	/**
	 * PLU指定日マスタを取得します
	 *
	 * @return R_PLU_SITEIBI取得SQL
	 */
	private String getPluShiteibiMaster() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT ");
		sql.append("		UKETSUKE_NO ");
		sql.append("		,TENPO_CD ");
		sql.append("		,TAISYO_DT ");
		sql.append("	FROM ");
		sql.append("		R_PLU_SITEIBI ");
		sql.append("	WHERE ");
		sql.append("		SYORI_FG = '1' ");

		return sql.toString();
	}


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
