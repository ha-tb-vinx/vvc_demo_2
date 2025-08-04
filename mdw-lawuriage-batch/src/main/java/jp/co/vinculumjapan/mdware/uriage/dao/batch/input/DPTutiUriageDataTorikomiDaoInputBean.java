package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 *  <P>タイトル: DPTutiUriageDataTorikomiDaoInputBean クラス</p>
 *  <P>説明: DPT打ち売上データ取込処理DaoInputBean</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author ZHANG ZH
 *  @version 1.0 
 */
public class DPTutiUriageDataTorikomiDaoInputBean {

    // データファイル名
    private String dataFileName = null;
    // バックアップファイル名
    private String backupFileName = null;
    // フォーマットファイル名
    private String formatFileName = null;
    // ログファイル名
    private String logFileName = null;

    /**
     * バックアップファイル名 を返します
     * @return String
     */
    public String getBackupFileName() {
        return backupFileName;
    }

    /**
     * バックアップファイル名 を設定します
     * @param String backupFileName
     */
    public void setBackupFileName(String backupFileName) {
        this.backupFileName = backupFileName;
    }

    /**
     * データファイル名 を返します
     * @return String
     */
    public String getDataFileName() {
        return dataFileName;
    }

    /**
     * データファイル名 を設定します
     * @param String dataFileName
     */
    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    /**
     * フォーマットファイル名 を返します
     * @return String
     */
    public String getFormatFileName() {
        return formatFileName;
    }

    /**
     * フォーマットファイル名 を設定します
     * @param String formatFileName
     */
    public void setFormatFileName(String formatFileName) {
        this.formatFileName = formatFileName;
    }

    /**
     * ログファイル名 を返します
     * @return String
     */
    public String getLogFileName() {
        return logFileName;
    }

    /**
     * ログファイル名 を設定します
     * @param String logFileName
     */
    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

}
