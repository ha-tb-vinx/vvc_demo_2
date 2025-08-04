package mdware.master.batch.process.mbD0;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.batch.common.ftp.FtpInfo;
import mdware.master.batch.common.ftp.MasterFtpClient;
import mdware.master.batch.common.util.FileControl;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル: MBD00201_SendIfDataAS400クラス</p>
 * <p>説明:CSVファイルをFTPにて送信する処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: Vinx Corporation</p>
 * @version 3.00 (2013/05/20) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 * @version 3.01 (2013/07/25) M.Ayukawa    [MSTC00028] 商品変更リスト作成対応
 */
public class MBD00401_SendIfDataAS400 {

	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	private String jobId = userLog.getJobId();

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBD00401_SendIfDataAS400( MasterDataBase db ) {
	}

	/**
	 * コンストラクタ
	 */
	public MBD00401_SendIfDataAS400() {

		this( new MasterDataBase( mst000101_ConstDictionary.CONNECTION_STR ) );
	}

	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		try {
			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "を開始します。");

			// FTPクライアントの作成
			MasterFtpClient client = new MasterFtpClient();

			// FTP送信
			client.put(createFtpInfo());

		// 例外処理
		} catch ( Exception e ) {
			writeError(e);
			throw e;
		}finally{
			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "が終了しました。");
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
		ftpInfo.setHost(mst000101_ConstDictionary.AS400_FTP_HOST);
		ftpInfo.setPort(mst000101_ConstDictionary.AS400_FTP_PORT);
		ftpInfo.setFileType(mst000101_ConstDictionary.AS400_FTP_FILE_TYPE);
		ftpInfo.setLoginName(mst000101_ConstDictionary.AS400_FTP_LOGIN_NAME);
		ftpInfo.setPassword(mst000101_ConstDictionary.AS400_FTP_PASSWORD);
		ftpInfo.setDirName(mst000101_ConstDictionary.AS400_FTP_SOURCE_DIR_NAME);
		ftpInfo.setDestDirName(mst000101_ConstDictionary.AS400_FTP_DST_DEST_NAME);

		// ファイル名の設定
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_NEW);
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_ADD);
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_UPDLIST);
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_NEW_FINISH);
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_ADD_FINISH);
		setFtpFileName(ftpInfo, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_UPDLIST_FINISH);

		return ftpInfo;
	}

	/**
	 * ファイル名リストから１つずつファイル名を取り出しFtpInfoに格納する
	 * @param FtpInfo
	 * @param String
	 */
	protected void setFtpFileName(FtpInfo fi, String param) {
		// ファイル名の取得（ワイルドカード「*」が指定されている場合は、該当ファイル名を取得する）
		FileControl fc = new FileControl();
		String dirName = fi.getDirName();
		String fileName = ResorceUtil.getInstance().getPropertie(param);

		// ファイル名をセット
		List syohinNewData = fc.listFileNames(dirName, fileName, false);
		for (Iterator iter = syohinNewData.iterator(); iter.hasNext();) {
			fi.addFileNameString((String) iter.next());
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
