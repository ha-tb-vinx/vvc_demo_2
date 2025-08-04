package mdware.master.batch.process.MSTB999;

import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <p>タイトル: リリース確認処理</p>
 * <p>説明　　: マスタ管理のリリース確認をします</p>
 * <p>著作権　: Copyright (c) 2014</p>
 * <p>会社名　: VINX</p>
 * @author S.Arakawa
 * @version 3.00 (2014.02.26) S.Arakawa [結合0122] ランドローム様対応 リリース確認用テストバッチ
 */
public class MSTB999999_ReleaseCheck {

    
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

    /**
     * データアクセス処理を実行します。
     */
    public void execute() throws Exception {

        try {

            /** バッチ開始ログ出力 */
            writeLog(Level.INFO_INT, "リリースの確認処理を開始します。");

            /** オンライン日付の取得 */
            {
                // 開始ログ出力
            	writeLog(Level.INFO_INT, "オンライン日付の取得を開始します。");

                // オンライン日付
                String onlineDt = ResorceUtil.getInstance().getPropertie("ONLINE_DT");

                // オンライン日付出力
                writeLog(Level.INFO_INT, "オンライン日付は [" + onlineDt + "] です。");

                // 終了ログ出力
                writeLog(Level.INFO_INT, "オンライン日付の取得を終了しました。");
            }

            /** バッチ日付の取得 */
            {
                // 開始ログ出力
            	writeLog(Level.INFO_INT, "バッチ日付の取得を開始します。");

                // バッチ日付
                String batchDt = ResorceUtil.getInstance().getPropertie("BATCH_DT");

                // バッチ日付出力
                writeLog(Level.INFO_INT, "バッチ日付は [" + batchDt + "] です。");

                // 終了ログ出力
                writeLog(Level.INFO_INT, "バッチ日付の取得を終了しました。");
            }

            /** バッチ終了ログ出力 */
            writeLog(Level.INFO_INT, "リリースの確認処理を終了します。");

        }catch (Exception e) {
			this.writeError(e);
			throw e;
        }

    }

    public Object getOutputObject() {
        return null;
    }

    public void setInputObject(Object obj) {
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
