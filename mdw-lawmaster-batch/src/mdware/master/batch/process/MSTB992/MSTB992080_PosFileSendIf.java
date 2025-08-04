package mdware.master.batch.process.MSTB992;

import java.io.File;
import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.batch.common.ftp.FtpInfo;
import mdware.master.batch.common.ftp.MasterFtpClient;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * 
 * <p>タイトル: MSTB992080_PosFileSendIf.java クラス</p>
 * <p>説明　: 単品メンテナンス、単品メンテナンス(ヘッダーファイル)、<br>
 *            クラスメンテナンス、クラスメンテナンス(ヘッダーファイル)、<br>
 *            ラインメンテナンス、ラインメンテナンス(ヘッダーファイル)をFTP送信する。</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.03) T.Ooshiro [CUS00063] ランドローム様対応 POSインターフェイス仕様変更対応
 *
 */
public class MSTB992080_PosFileSendIf {

	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	private String jobId = userLog.getJobId();

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992080_PosFileSendIf( MasterDataBase db ) {
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992080_PosFileSendIf() {
	}



	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		try {
			writeLog(Level.INFO_INT, Jobs.getInstance().get(jobId).getJobName() + "を開始します。");

			// FTPクライアントの作成
			MasterFtpClient client = new MasterFtpClient();

			// FTP送信
			client.put(createFtpInfo());

		// 例外処理
		} catch ( Exception e ) {
			writeError(e);
			throw e;
		}finally{
			writeLog(Level.INFO_INT, Jobs.getInstance().get(jobId).getJobName() + "が終了しました。");
		}
	}

	/**
	 * FTPサーバの接続情報を作成します。
	 * @return FTPサーバの接続情報
	 */
	protected FtpInfo createFtpInfo() {
		FtpInfo ftpInfo = new FtpInfo();
		ftpInfo.setBatchId(jobId);
		ftpInfo.setBatchName(Jobs.getInstance().get(jobId).getJobName());
		ftpInfo.setHost(mst000101_ConstDictionary.FTP_POS_HOST);
		ftpInfo.setPort(mst000101_ConstDictionary.FTP_POS_PORT);
		ftpInfo.setFileType(mst000101_ConstDictionary.FTP_POS_FILE_TYPE);
		ftpInfo.setLoginName(mst000101_ConstDictionary.FTP_POS_LOGIN_NAME);
		ftpInfo.setPassword(mst000101_ConstDictionary.FTP_POS_PASSWORD);
		ftpInfo.setDirName(mst000101_ConstDictionary.PATH_SEND_PLU);
		ftpInfo.setDestDirName(mst000101_ConstDictionary.PATH_SEND_DEST_PLU);

		// ファイル名の設定
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.POS_FTP_TANPIN_MAINTE, mst000101_ConstDictionary.POS_FTP_TANPIN_MAINTE_HDS);
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.POS_FTP_CLASS_MAINTE, mst000101_ConstDictionary.POS_FTP_CLASS_MAINTE_HDS);
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.POS_FTP_LINE_MAINTE, mst000101_ConstDictionary.POS_FTP_LINE_MAINTE_HDS);

		return ftpInfo;
	}

	/**
	 * ファイル名リストから１つずつファイル名を取り出しFtpInfoに格納する
	 * @param FtpInfo
	 * @param param データファイル名 システムパラメタ
	 * @param paramHdr ヘッダファイル名 システムパラメタ
	 */
	private void setFtpFileName(FtpInfo fi, String param, String paramHdr) {
		String dirName = fi.getDirName();
		String fileName = ResorceUtil.getInstance().getPropertie(param);
		String fileNameHdr = ResorceUtil.getInstance().getPropertie(paramHdr);
		
		File file = new File(new File(dirName), fileName);
		File fileHdr = new File(new File(dirName), fileNameHdr);
		// ファイルが作成されている場合、ファイル名をセット
		if (file.exists() && fileHdr.exists()) {
			fi.addFileName(param);
			fi.addFileName(paramHdr);
		}
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

