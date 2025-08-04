package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: JikantaiDataTorikomiDaoInputBean クラス</p>
 * <p>説明　: 時間帯データ取込用Inputクラス</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.05.20) Hirata FIVImart様対応
 * 
 */
public class JikantaiDataTorikomiDaoInputBean {

    /** ファイルID */
    private String fileId = null;

    /** ファイルフォルダPID  */
    private String fileDirPID = null;

    /**
     * ファイルIDを取得します。
     * @return ファイルID
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * ファイルIDを設定します。
     * @param fileId ファイルID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    
    /**
     * ファイルフォルダPIDを取得します。
     * @return ファイルフォルダPID
     */
    public String getFileDirPID() {
        return fileDirPID;
    }

    /**
     * ファイルフォルダPIDを設定します。
     * @param fileDirPID ファイルフォルダPID
     */
    public void setFileDirPID(String fileDirPID) {
        this.fileDirPID = fileDirPID;
    }

}
