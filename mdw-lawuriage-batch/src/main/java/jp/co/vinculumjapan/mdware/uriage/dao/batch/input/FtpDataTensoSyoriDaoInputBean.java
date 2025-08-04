package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * 
 * <p>タイトル: FtpDataTensoSyoriDaoInputBean.java クラス</p>
 * <p>説明　: FtpDataTensoSyoriDao用InputBean</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.20) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 ファイル送信処理対応
 *
 */
public class FtpDataTensoSyoriDaoInputBean {

    /** サーバアドレス */
    private String ftpHost = "";

    /** FTPユーザ */
    private String ftpLoginName = "";

    /** FTPパスワード */
    private String ftpPassword = "";

    /** FTPポート番号 */
    private String ftpPort = "";

    /** 送信先ディレクトリ */
    private String pathSendToDir = "";

    /** 送信元ディレクトリ */
    private String pathSendFromDir = "";

    /** ファイル名 */
    private String fileName = "";

    /**
     * サーバアドレスを取得します。
     * @return サーバアドレス
     */
    public String getFtpHost() {
        return ftpHost;
    }

    /**
     * サーバアドレスを設定します。
     * @param ftpHost サーバアドレス
     */
    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    /**
     * FTPユーザを取得します。
     * @return FTPユーザ
     */
    public String getFtpLoginName() {
        return ftpLoginName;
    }

    /**
     * FTPユーザを設定します。
     * @param ftpLoginName FTPユーザ
     */
    public void setFtpLoginName(String ftpLoginName) {
        this.ftpLoginName = ftpLoginName;
    }

    /**
     * FTPパスワードを取得します。
     * @return FTPパスワード
     */
    public String getFtpPassword() {
        return ftpPassword;
    }

    /**
     * FTPパスワードを設定します。
     * @param ftpPassword FTPパスワード
     */
    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    /**
     * FTPポート番号を取得します。
     * @return FTPポート番号
     */
    public String getFtpPort() {
        return ftpPort;
    }

    /**
     * FTPポート番号を設定します。
     * @param ftpPort FTPポート番号
     */
    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    /**
     * 送信先ディレクトリを取得します。
     * @return 送信先ディレクトリ
     */
    public String getPathSendToDir() {
        return pathSendToDir;
    }

    /**
     * 送信先ディレクトリを設定します。
     * @param pathSendToDir 送信先ディレクトリ
     */
    public void setPathSendToDir(String pathSendToDir) {
        this.pathSendToDir = pathSendToDir;
    }

    /**
     * 送信元ディレクトリを取得します。
     * @return 送信元ディレクトリ
     */
    public String getPathSendFromDir() {
        return pathSendFromDir;
    }

    /**
     * 送信元ディレクトリを設定します。
     * @param pathSendFromDir 送信元ディレクトリ
     */
    public void setPathSendFromDir(String pathSendFromDir) {
        this.pathSendFromDir = pathSendFromDir;
    }

    /**
     * ファイル名を取得します。
     * @return ファイル名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * ファイル名を設定します。
     * @param fileName ファイル名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
