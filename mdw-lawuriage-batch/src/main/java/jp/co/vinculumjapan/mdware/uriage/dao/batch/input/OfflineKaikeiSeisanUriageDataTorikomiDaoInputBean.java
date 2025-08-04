package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

public class OfflineKaikeiSeisanUriageDataTorikomiDaoInputBean {

    // ファイル名
    private String dataFileName = null;
    // バックアップファイル名
    private String backupFileName = null;
    // フォーマットファイル名
    private String formatFileName = null;
    // フォーマットファイル名
    private String logFileName = null;
    
    public String getBackupFileName() {
        return backupFileName;
    }
    
    public void setBackupFileName(String backupFileName) {
        this.backupFileName = backupFileName;
    }
    
    public String getDataFileName() {
        return dataFileName;
    }
    
    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }
    
    public String getFormatFileName() {
        return formatFileName;
    }
    
    public void setFormatFileName(String formatFileName) {
        this.formatFileName = formatFileName;
    }
    
    public String getLogFileName() {
        return logFileName;
    }
    
    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }
    

}
