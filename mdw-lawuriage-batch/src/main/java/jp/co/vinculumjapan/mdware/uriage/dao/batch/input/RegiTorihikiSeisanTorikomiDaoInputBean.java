package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: RegiTorihikiSeisanTorikomiDaoInputBean クラス</p>
 * <p>説明　: POS実績取込処理(レジ取引精算)用Inputクラス</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.17) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * 
 */
public class RegiTorihikiSeisanTorikomiDaoInputBean {

    /** ファイルID */
    private String fileId = null;

    /** JOB-ID */
    private String jobId = null;

    /** バックアップフォルダPID  */
    private String backUpDirPID = null;

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
     * JOB-IDを取得します。
     * @return JOB-ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * JOB-IDを設定します。
     * @param jobId JOB-ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * バックアップフォルダPIDを取得します。
     * @return バックアップフォルダPID
     */
    public String getBackUpDirPID() {
        return backUpDirPID;
    }

    /**
     * バックアップフォルダPIDを設定します。
     * @param backUpDirPID バックアップフォルダPID
     */
    public void setBackUpDirPID(String backUpDirPID) {
        this.backUpDirPID = backUpDirPID;
    }

}
