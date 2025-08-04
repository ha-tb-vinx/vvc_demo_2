package mdware.master.batch.process.mbC0;

import java.sql.SQLException;
import java.util.Vector;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.file.FileBackupUtil;
import mdware.common.file.factory.FileBackupFromXMLFactory;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:ファイルバックアップ</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author T.Yokoyama
 */
 
public class MBC00201_FileBackup{

	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	private String xmlFileName= "";
	private String pram = null;
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBC00201_FileBackup( MasterDataBase db ) {
	}

	/**
	 * コンストラクタ
	 */
	public MBC00201_FileBackup() {

		this( new MasterDataBase( "rbsite_ora" ) );
	}

	
	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String xmlFileName, String pram) throws Exception {
		this.pram = pram;
		execute(xmlFileName);
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String xmlFileName) throws Exception {
		this.xmlFileName = xmlFileName;
		execute();
	}


	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		String jobId = userLog.getJobId();
		String xml = "";
		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "を開始します。");
			
			// 設定ファイル
            if (xmlFileName == null || xmlFileName.trim().length() == 0) {
            	xml = mdware.common.batch.util.properties.BatchProperty.propertiesDir + "/mdware_xml/backupConfig.xml";
            } else {
    			xml = mdware.common.batch.util.properties.BatchProperty.propertiesDir + "/mdware_xml/" + xmlFileName;
            }
			Vector vec =  FileBackupFromXMLFactory.createFileBackupUtil(xml);
			for(int tmp = 0; tmp < vec.size(); tmp++){
				FileBackupUtil fbUtil = (FileBackupUtil)vec.get(tmp);

				// ファイル名置き換え
				String tmpFileName = fbUtil.getFromFileName();
				if (pram != null && tmpFileName != null) {
					fbUtil.setFromFileName(tmpFileName.replace("\\1", pram));
				}
				tmpFileName = fbUtil.getToFileName();
				if (pram != null && tmpFileName != null) {
					fbUtil.setToFileName(tmpFileName.replace("\\1", pram));
				}

				writeLog(Level.INFO_INT, fbUtil.getFromFileName() +"のバックアップ処理を開始します。");

				int ret = fbUtil.backUp();
				
				if(ret == FileBackupUtil.SUCCESS){
					writeLog(Level.INFO_INT, fbUtil.getFromFileName() +"のバックアップを完了しました。");
				}else{
					writeLog(Level.INFO_INT, fbUtil.getFromFileName() +"のバックアップが完了できませんでした。");
				}
			}

		// 例外処理
		} catch ( Exception e ) {

			// ログ出力
			writeError(e);

			throw e;
		}finally{
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "が終了しました。");
		}
	}

	/********* 以下、ログ出力用メソッド *********/
	
	/**
	 * ユーザーログとバッチログにログを出力します。
	 * 
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
