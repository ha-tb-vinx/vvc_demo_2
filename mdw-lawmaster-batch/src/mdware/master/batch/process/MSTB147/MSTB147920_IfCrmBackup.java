package mdware.master.batch.process.MSTB147;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.batch.util.convert.DataExchanger;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <p> タイトル: 海外CRM用IFバックアップ処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */
public class MSTB147920_IfCrmBackup {
	/** ログ出力用変数 */
	private static final BatchLog BATCH_LOG = BatchLog.getInstance();
	/** ログ出力用変数 */
	private static final BatchUserLog USER_LOG = BatchUserLog.getInstance();
	/** バッチID */
	private static final String JOB_ID = "MSTB147-920";
	/** バッチ名 */
	private static final String JOB_NAME = "海外CRM用IFバックアップ処理";
	/** IFファイル格納ディレクトリ */
	private static final String IF_CRM_PATH = ResorceUtil.getInstance().getPropertie("IF_CRM_PATH");
	/** バックアップディレクトリ */
	private static final String IF_CRM_BK_PATH = ResorceUtil.getInstance().getPropertie("IF_CRM_BK_PATH");
	
	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception 
	{
		writeLog(Level.INFO_INT, "処理を開始します。");
		
		//パスの設定をチェックする
		if (IF_CRM_PATH == null || IF_CRM_PATH.trim().length() == 0 || IF_CRM_BK_PATH == null || IF_CRM_BK_PATH.trim().length() == 0)
		{
			writeLog(Level.ERROR_INT, "システムコントロールから、パスが取得できませんでした");
			throw new Exception("パスが設定されていません");
		}
		
		//ディレクトリの存在をチェックする
		File fromPath = new File(IF_CRM_PATH);
		File toPath = new File(IF_CRM_BK_PATH);
		if (!fromPath.exists() || !toPath.exists()) {
			writeLog(Level.ERROR_INT, "パスが存在しません");
			throw new Exception(" パスが存在しません。");
		}
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMdd-HHmm");
		String timestamp = sdf.format(now);
		String toFilePath = toPath + File.separator + timestamp + ".zip";
		List<String> zipFiles = new ArrayList<String>();
		File[] files = fromPath.listFiles();
		for (int i = 0; i < files.length; i++) {
		    File file = files[i];
		    if (file.isFile() && file.getName().toLowerCase().endsWith(".zip")) {
		        zipFiles.add(file.getAbsolutePath());
		    }
		}
		DataExchanger.encodeZip(zipFiles, toFilePath);
		
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
