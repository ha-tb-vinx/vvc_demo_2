package mdware.master.batch.process.mbA3;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.batch.common.ftp.FtpInfo;
import mdware.master.batch.common.ftp.MasterFtpClient;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル: MBA39001_SendIfDataクラス</p>
 * <p>説明:CSV送信処理</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @version 1.0 (2009/07/03) 初版作成
 * @Version 1.1  (2015.09.01) THE.VV FIVIMART対応
*/
public class MBA39001_SendIfData  {
	
	/* FTPホスト名 */
    private static final String PARAM_ID_HOST = "JOHO_FTP_HOST";

    /* FTPポート番号 */
    private static final String PARAM_ID_PORT = "JOHO_FTP_PORT";

    /* FTPログイン名 */
    private static final String PARAM_ID_LOGIN_NAME = "JOHO_FTP_LOGIN_NAME";

    /* FTPパスワード */
    private static final String PARAM_ID_PASSWORD = "JOHO_FTP_PASSWORD";

    /* FTP転送元ディレクトリ名*/
    private static final String PARAM_ID_SOURCE_DIR_NAME = "JOHO_FTP_SOURCE_DIR_NAME";
    
    /* FTP転送先ディレクトリ名*/
    private static final String PARAM_ID_DST_DEST_NAME = "JOHO_FTP_DEST_DIR_NAME";
    
    /* 送信ファイル名 分類１マスタ */
    private static final String PARAM_JOHO_FTP_FILE_BUNRUI1 = "JOHO_FTP_FILE_BUNRUI1";
    
    /* 送信ファイル名 分類２マスタ */
    private static final String PARAM_JOHO_FTP_FILE_BUNRUI2 = "JOHO_FTP_FILE_BUNRUI2";
    
    /* 送信ファイル名 分類５マスタ */
    private static final String PARAM_JOHO_FTP_FILE_BUNRUI5 = "JOHO_FTP_FILE_BUNRUI5";

    /* 送信ファイル名 大分類１マスタ */
    private static final String PARAM_JOHO_FTP_FILE_DAIBUNRUI1 = "JOHO_FTP_FILE_DAIBUNRUI1";
    
    /* 送信ファイル名 大分類２マスタ */
    private static final String PARAM_JOHO_FTP_FILE_DAIBUNRUI2 = "JOHO_FTP_FILE_DAIBUNRUI2";

    /* 送信ファイル名 店舗マスタ */
    private static final String PARAM_JOHO_FTP_FILE_TENPO = "JOHO_FTP_FILE_TENPO";

    /* 送信ファイル名 店舗分類１マスタ */
    private static final String PARAM_JOHO_FTP_FILE_TENPO_BUNRUI1 = "JOHO_FTP_FILE_TENPO_BUNRUI1";

    /* 送信ファイル名 名称マスタ */
    private static final String PARAM_JOHO_FTP_FILE_NAMECTF = "JOHO_FTP_FILE_NAMECTF";

    /* 送信ファイル名 税率マスタ */
    private static final String PARAM_JOHO_FTP_FILE_TAX_RATE = "JOHO_FTP_FILE_TAX_RATE";

    /* 送信ファイル名 商品マスタ */
    private static final String PARAM_JOHO_FTP_FILE_SYOHIN = "JOHO_FTP_FILE_SYOHIN";

    // Add 2015.09.01 THE.VV 送信ファイル名 商品マスタ ASN(S)
    /* 送信ファイル名 商品マスタ ASN */
    private static final String PARAM_JOHO_FTP_FILE_SYOHIN_ASN = "JOHO_FTP_FILE_SYOHIN_ASN";
    // Add 2015.09.01 THE.VV 送信ファイル名 商品マスタ ASN(E)


    /* 送信ファイル名 システムコントロールマスタ */
    private static final String PARAM_JOHO_FTP_FILE_SYSTEM_CONTROL = "JOHO_FTP_FILE_SYSTEM_CONTROL";

    /* 送信ファイル名 ディクショナリコントロールマスタ */
    private static final String PARAM_JOHO_FTP_FILE_DICTIONARY_CONTROL = "JOHO_FTP_FILE_DICTIONARY_CONTROL";

    /* 送信ファイル名 計量器マスタ */
    private static final String PARAM_JOHO_FTP_FILE_KEIRYOKI = "JOHO_FTP_FILE_KEIRYOKI";

    /* 送信ファイル名 商品分類体系マスタ */
    private static final String PARAM_JOHO_FTP_FILE_SYOHIN_TAIKEI = "JOHO_FTP_FILE_SYOHIN_TAIKEI";

    /* 送信ファイル名 店別商品マスタ */
    private static final String PARAM_JOHO_FTP_FILE_TENBETU_SYOHIN = "JOHO_FTP_FILE_TENBETU_SYOHIN";

    /* 送信ファイル名 昨対比マスタ */
    private static final String PARAM_JOHO_FTP_FILE_SAKUTAIHI = "JOHO_FTP_FILE_SAKUTAIHI";

    /* 送信ファイル名 メッセージマスタ */
    private static final String PARAM_JOHO_FTP_FILE_MESSAGE = "JOHO_FTP_FILE_MESSAGE";

	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	private String jobId = userLog.getJobId();

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA39001_SendIfData( MasterDataBase db ) {
	}

	/**
	 * コンストラクタ
	 */
	public MBA39001_SendIfData() {

		this( new MasterDataBase( "rbsite_ora" ) );
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
	 * @return　FTPサーバの接続情報
	 */
	protected FtpInfo createFtpInfo() {
		FtpInfo ftpInfo = new FtpInfo();
		ftpInfo.setBatchId(jobId);
		ftpInfo.setBatchName(Jobs.getInstance().get(jobId).getJobName());
		ftpInfo.setHost(PARAM_ID_HOST);
		ftpInfo.setPort(PARAM_ID_PORT);
		ftpInfo.setLoginName(PARAM_ID_LOGIN_NAME);
		ftpInfo.setPassword(PARAM_ID_PASSWORD);
		ftpInfo.setDirName(PARAM_ID_SOURCE_DIR_NAME);
		ftpInfo.setDestDirName(PARAM_ID_DST_DEST_NAME);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_BUNRUI1);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_BUNRUI2);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_BUNRUI5);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_DAIBUNRUI1);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_DAIBUNRUI2);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_TENPO);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_TENPO_BUNRUI1);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_NAMECTF);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_TAX_RATE);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_SYOHIN);

		// Add 2015.09.01 THE.VV 送信ファイル名 商品マスタ ASN(S)
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_SYOHIN_ASN);
		// Add 2015.09.01 THE.VV 送信ファイル名 商品マスタ ASN(E)

		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_SYSTEM_CONTROL);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_DICTIONARY_CONTROL);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_KEIRYOKI);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_SYOHIN_TAIKEI);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_TENBETU_SYOHIN);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_MESSAGE);
		ftpInfo.addFileName(PARAM_JOHO_FTP_FILE_SAKUTAIHI);
		
		return ftpInfo;
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
