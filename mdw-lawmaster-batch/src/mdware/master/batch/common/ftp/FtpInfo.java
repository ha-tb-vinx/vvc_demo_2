package mdware.master.batch.common.ftp;

import java.util.ArrayList;
import java.util.List;

import mdware.common.resorces.util.ResorceUtil;

/**
 * <p>タイトル: FtpInfoクラス</p>
 * <p>説明:FTPサーバの接続情報を保持するクラス</p>
 * <p>著作権: Copyright  (C) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * 
 * @author T.Kuzuhara
 * @version 1.00 (2009/07/02) 初版作成
 * @version 3.00 (2013/05/20) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 */
public class FtpInfo {

	/** バッチID */
	private String batchId = null;
	/** バッチ名称 */
	private String batchName = null;
	/** ホスト名 */
	private String host = null;
	/** ポート番号 */
	private int port = 0;
	/** ログインID */
	private String loginName = null;
	/** パスワード */
	private String password = null;
	/** 送信元ディレクトリ */
	private String dirName = null;
	/** 送信対象のファイル名称の一覧 */
	private List fileNameList = null;
	/** 送信先ディレクトリ */
	private String destDirName = null;
	/** ファイルタイプ */
	private String fileType = null;

	/**
	 * バッチIDを返します。
	 * @return バッチID
	 */
	public String getBatchId() {
		return batchId;
	}
	
	/**
	 * バッチIDを設定します。
	 * @param batchId バッチID
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	/**
	 * バッチ名称を返します。
	 * @return バッチ名称
	 */
	public String getBatchName() {
		return batchName;
	}
	
	/**
	 * バッチ名称を設定します。
	 * @param batchName バッチ名称
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	
	/**
	 * 送信先ディレクトリを返します。
	 * @return 送信先ディレクトリ
	 */
	public String getDestDirName() {
		return destDirName;
	}
	
	/**
	 * 送信先ディレクトリを設定します。
	 * @param パラメータID
	 */
	public void setDestDirName(String id) {
		destDirName = ResorceUtil.getInstance().getPropertie(id);
	}
	
	/**
	 * 送信元ディレクトリを返します。
	 * @return 送信元ディレクトリ
	 */
	public String getDirName() {
		return dirName;
	}
	
	/**
	 * 送信元ディレクトリを設定します。
	 * @param パラメータID
	 */
	public void setDirName(String id) {
		dirName = ResorceUtil.getInstance().getPropertie(id);
	}
	
	/**
	 * 送信対象のファイル名称の一覧を返します。
	 * @return 送信対象のファイル名称の一覧
	 */
	public List getFileNameList() {
		return fileNameList;
	}
	
	/**
	 * 送信対象のファイルを追加します。（コントロールマスタよりファイル名を取得してセットする）
	 * @param パラメータID
	 */
	public void addFileName(String id) {
		
		if (fileNameList == null) {
			fileNameList = new ArrayList();
		}
		
		fileNameList.add(ResorceUtil.getInstance().getPropertie(id));
	}

//  2013.05.20 [MSTC00007] AS400商品マスタIF作成 (S)
	/**
	 * 送信対象のファイルを追加します。（直接ファイル名をセットする）
	 * @param ファイル名
	 */
	public void addFileNameString(String fileName) {

		if (fileNameList == null) {
			fileNameList = new ArrayList();
		}

		fileNameList.add(fileName);
	}
//  2013.05.20 [MSTC00007] AS400商品マスタIF作成 (E)

	/**
	 * ホスト名を返します。
	 * @return ホスト名
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * ホスト名を設定します。
	 * @param パラメータID
	 */
	public void setHost(String id) {
		host = ResorceUtil.getInstance().getPropertie(id);
	}
	
	/**
	 * ログイン名称を返します。
	 * @return ログイン名称
	 */
	public String getLoginName() {
		return loginName;
	}
	
	/**
	 * ログイン名称を設定します。
	 * @param パラメータID
	 */
	public void setLoginName(String id) {
		loginName = ResorceUtil.getInstance().getPropertie(id);
	}
	
	/**
	 * パスワードを返します。
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * パスワードを設定します。
	 * @param パラメータID
	 */
	public void setPassword(String id) {
		password = ResorceUtil.getInstance().getPropertie(id);
	}
	
	/**
	 * ポート番号を返します。
	 * @return ポート番号
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * ポート番号を設定します。
	 * @param パラメータID
	 */
	public void setPort(String id) {
		port = Integer.parseInt(ResorceUtil.getInstance().getPropertie(id));
	}
	/**
	 * ファイルタイプを返します。
	 * @return ポート番号
	 */
	public String getFileType() {
		return fileType;
	}
	
	/**
	 * ポート番号を設定します。
	 * @param パラメータID
	 */
	public void setFileType(String id) {
		fileType = ResorceUtil.getInstance().getPropertie(id);
	}

}
