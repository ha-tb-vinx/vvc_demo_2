package mdware.master.batch.process.MSTB147;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.resorces.util.ResorceUtil;

public class MSTB147910_IfCrmSendFile {
	/** ログ出力用変数 */
	private static final BatchLog BATCH_LOG = BatchLog.getInstance();
	/** ログ出力用変数 */
	private static final BatchUserLog USER_LOG = BatchUserLog.getInstance();
	/** バッチID */
	private static final String JOB_ID = "MSTB147-910";
	/** バッチ名 */
	private static final String JOB_NAME = "海外CRM用IFファイル送信処理";
	/** IFファイル格納ディレクトリ */
	private static final String IF_CRM_PATH = ResorceUtil.getInstance().getPropertie("IF_CRM_PATH");
	/** SFTP接続用変数 */
	private static final String IF_CRM_SFTP_EXE = ResorceUtil.getInstance().getPropertie("IF_CRM_SFTP_EXE");
	/** SFTP接続用変数 */
	private static final String IF_CRM_SFTP_HOST = ResorceUtil.getInstance().getPropertie("IF_CRM_SFTP_HOST");
	/** SFTP接続用変数 */
	private static final String IF_CRM_SFTP_PORT = ResorceUtil.getInstance().getPropertie("IF_CRM_SFTP_PORT");
	/** SFTP接続用変数 */
	private static final String IF_CRM_SFTP_LOGIN = ResorceUtil.getInstance().getPropertie("IF_CRM_SFTP_LOGIN");
	/** SFTP接続用変数 */
	private static final String IF_CRM_SFTP_PASSWORD = ResorceUtil.getInstance().getPropertie("IF_CRM_SFTP_PASSWORD");
	/** SFTP接続用変数 */
	private static final String IF_CRM_SEND_DIR = ResorceUtil.getInstance().getPropertie("IF_CRM_SEND_DIR");
	/** IFファイル */
	private static final List<String> IF_FILES = Arrays.asList(
			"HKCR0010.zip"
			, "HKCR0020.zip"
			, "HKCR0070.zip"
			, "HKCR0080.zip"
			, "HKCR0090.zip"
			, "HKCR0100.zip"
			, "HKCR0110.zip"
			, "HKCR0120.zip"
			, "HKCR0130.zip"
			, "HKCR0140.zip"
			, "HKCR0150.zip"
			, "HKCR0160.zip"
			, "HKCR0170.zip"
			, "HKCR0180.zip");
	
	/**
	 * 本処理
	 * 
	 * @throws Exception 例外
	 */
	public void execute() throws Exception 
	{
		writeLog(Level.INFO_INT, "処理を開始します。");
		
		//パスの設定をチェックする
		if (IF_CRM_PATH.trim().length() == 0)
		{
			writeLog(Level.ERROR_INT, "システムコントロールから、パスが取得できませんでした");
			throw new Exception("パスが設定されていません");
		}
		
		//ディレクトリの存在をチェックする
		File fromPath = new File(IF_CRM_PATH);
		if (!fromPath.exists()) {
			writeLog(Level.ERROR_INT, "パスが存在しません");
			throw new Exception("パスが存在しません。");
		}
		
		//IFファイルをチェックする
		for (String filename : IF_FILES) {
			File file = new File(fromPath + File.separator + filename);
			if (!file.exists()) {
				writeLog(Level.ERROR_INT, filename +  "が存在しません");
				throw new Exception(filename +  "が存在しません");
			}
		}
		
		//ファイルを送信する
		writeLog(Level.INFO_INT, "ファイルを送信を開始します。");
		for (String filename : IF_FILES) {
			String filePath = fromPath + File.separator + filename;
			String command = "\"" + IF_CRM_SFTP_EXE + "\" -P " + IF_CRM_SFTP_PORT +  " -pw " + IF_CRM_SFTP_PASSWORD + " \"" + filePath + "\" " +
					IF_CRM_SFTP_LOGIN + "@" + IF_CRM_SFTP_HOST + ":" + IF_CRM_SEND_DIR;
			Process process = Runtime.getRuntime().exec(command);
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				writeLog(Level.INFO_INT, filename + "が送信されました。");
			} else {
				writeLog(Level.ERROR_INT, filename + "が送信失敗しました。");
				throw new Exception(filename +  "が送信失敗しました : " + exitCode);
			}
		}
		writeLog(Level.INFO_INT, "ファイルを送信を終了しました。");
		
		writeLog(Level.INFO_INT, "処理を終了しました。");
	}
	
	public static void writeLog(int level, String message) {
		switch (level) {
			case Level.DEBUG_INT:
				USER_LOG.debug(message);
				BATCH_LOG.getLog().debug(JOB_ID, JOB_NAME, message);
				break;

			case Level.INFO_INT:
				USER_LOG.info(message);
				BATCH_LOG.getLog().info(JOB_ID, JOB_NAME, message);
				break;

			case Level.ERROR_INT:
				USER_LOG.error(message);
				BATCH_LOG.getLog().error(JOB_ID, JOB_NAME, message);
				break;

			case Level.FATAL_INT:
				USER_LOG.fatal(message);
				BATCH_LOG.getLog().fatal(JOB_ID, JOB_NAME, message);
				break;
		}
	}
}
