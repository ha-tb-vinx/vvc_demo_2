package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;


public class KspspSyohinMasterCreateFileDaoInputBean {

    // CSVファイル名 
    private String csvFileName = null;
    
    /**
     * @return csvFileName
     */
    public String getcsvFileName() {
        return csvFileName;
    }
    
    /**
     * @param csvFileName 設定する csvFileName
     */
    public void setcsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }
}
