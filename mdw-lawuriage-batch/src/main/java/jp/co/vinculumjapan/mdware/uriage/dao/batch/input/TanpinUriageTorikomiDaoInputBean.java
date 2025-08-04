package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

public class TanpinUriageTorikomiDaoInputBean {

    // ファイル名
    private String dataFileName = null;
    // バックアップファイル名
    private String backupFileName = null;
    // フォーマットファイル名
    private String formatFileName = null;
    // フォーマットファイル名
    private String logFileName = null;
    
    /**
     * @return backupFileName
     */
    public String getBackupFileName() {
        return backupFileName;
    }
    
    /**
     * @param backupFileName 設定する backupFileName
     */
    public void setBackupFileName(String backupFileName) {
        this.backupFileName = backupFileName;
    }
    
    /**
     * @return dataFileName
     */
    public String getDataFileName() {
        return dataFileName;
    }
    
    /**
     * @param dataFileName 設定する dataFileName
     */
    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }
    
    /**
     * @return formatFileName
     */
    public String getFormatFileName() {
        return formatFileName;
    }
    
    /**
     * @param formatFileName 設定する formatFileName
     */
    public void setFormatFileName(String formatFileName) {
        this.formatFileName = formatFileName;
    }
    
    /**
     * @return logFileName
     */
    public String getLogFileName() {
        return logFileName;
    }
    
    /**
     * @param logFileName 設定する logFileName
     */
    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

}
