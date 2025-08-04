package mdware.master.batch.common.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import mdware.common.batch.log.BatchLog;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTP;

/**
 * <p>タイトル: FtpClientクラス</p>
 * <p>説明:FTPクライアント</p>
 * <p>著作権: Copyright  (C) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * 
 * @author T.Kuzuhara
 * @version 1.0 (2009/07/02) 初版作成
 */
public class MasterFtpClient {

	/** ログ出力オブジェト */
	protected BatchLog batchLog = BatchLog.getInstance();
	
	/**
	 * 指定された情報でFTPサーバに接続し、ファイルを送信します。
	 * @param ftpInfo FTPサーバの接続情報
	 * @throws Exception 例外
	 */
	public void put(FtpInfo ftpInfo) throws Exception {
	
		FTPClient fp = null;
	    FileInputStream is = null;
	    String batchId = ftpInfo.getBatchId();
		String batchName = ftpInfo.getBatchName();
		String host = ftpInfo.getHost();
		int port = ftpInfo.getPort();
		String loginName = ftpInfo.getLoginName();
		String password = ftpInfo.getPassword();
		String dirName = ftpInfo.getDirName();
		List fileNameList = ftpInfo.getFileNameList();
		String destDirName = ftpInfo.getDestDirName();
		String fileType = ftpInfo.getFileType();
	    
		if (fileNameList == null) {
			return;
		}
		
	    try {
	    	
	    	fp = new FTPClient();
	    	
	        // 接続処理
	        fp.connect(host, port);
	        if (!FTPReply.isPositiveCompletion(fp.getReplyCode())) {
	        	throw new FtpException("FTP接続に失敗しました。");
	        }
	
	        // ログイン処理
	        if (fp.login(loginName, password) == false) {
	        	throw new FtpException("ログインに失敗しました。");
	        }

	        String[] fileList = fp.listNames(destDirName);
            if (!FTPReply.isPositiveCompletion(fp.getReplyCode())) {
                throw new FtpException("FTP送信先ディレクトリが存在しません。");
            }
            if (fileList == null) {
                throw new FtpException("FTP送信先ディレクトリが存在しません。");
            }

	        // ファイルタイプ設定
	        if (fileType != null && fileType.trim().length() != 0) {

	        	String wkStr= fileType.trim().toLowerCase();
	        	
	        	if (wkStr.equals("ascii")) {
	        		fp.setFileType(FTP.ASCII_FILE_TYPE);
	        	} else if (wkStr.equals("binary")) {
	        		fp.setFileType(FTP.BINARY_FILE_TYPE);
	        	} else {
		        	throw new FtpException("サポートされていないファイルタイプが指定されました（" + wkStr + "）");
	        	}
	        } 
	        
	        for (Iterator iter = fileNameList.iterator(); iter.hasNext();) {
	        	
				String fileName = (String) iter.next();
				File file = new File(dirName, fileName);

				if (file.exists()){ 
					batchLog.getLog().info(batchId, batchName, fileName + "を送信します。");
					// ファイル送信
			        is = new FileInputStream(dirName + "/" + fileName);
		        
			        if (!fp.storeFile(destDirName + "/" + fileName, is)) {
			        	throw new FtpException(fileName + "の送信に失敗しました。");
			        }
			        is.close();
			        batchLog.getLog().info(batchId, batchName, fileName + "を送信しました。");
				} else {
					batchLog.getLog().warn(batchId, batchName, fileName + "は存在しません。");
				}
			}
	        
	    } catch (Exception e) {
	        throw e;
	    } finally {
	    	
	    	if (fp != null && fp.isConnected()) {
	    		fp.disconnect();
	    	}
	    	
	    	if (is != null) {
	    		is.close();
	    	}
	        
	    }
	}
	
}
