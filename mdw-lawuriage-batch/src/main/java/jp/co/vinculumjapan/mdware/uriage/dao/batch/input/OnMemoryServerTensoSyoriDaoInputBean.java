package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: OnMemoryServerTensoSyoriDaoInputBean クラス</p>
 * <p>説明: OnMemoryServerTensoSyoriDaoの入力用Bean</p>
 * <p>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 * @author CH.HE
 * @version 1.0
 */

public class OnMemoryServerTensoSyoriDaoInputBean {

    /** リモートディレクトリPID */
    private String remoteFileDirPID = "";
    /** ファイル名群PID */
    private String fileNamesPID = "";
    /** 日付置換文字列PID */
    private String dateChgStrPID = "";
    /** 時刻置換文字列 */
    private String timeChgStr = "";
    
    /**
     * @return dateChgStrPID
     */
    public String getDateChgStrPID() {
        return dateChgStrPID;
    }
    
    /**
     * @param dateChgStrPID 設定する dateChgStrPID
     */
    public void setDateChgStrPID(String dateChgStrPID) {
        this.dateChgStrPID = dateChgStrPID;
    }
    
    /**
     * @return fileNamesPID
     */
    public String getFileNamesPID() {
        return fileNamesPID;
    }
    
    /**
     * @param fileNamesPID 設定する fileNamesPID
     */
    public void setFileNamesPID(String fileNamesPID) {
        this.fileNamesPID = fileNamesPID;
    }
    
    /**
     * @return remoteFileDirPID
     */
    public String getRemoteFileDirPID() {
        return remoteFileDirPID;
    }
    
    /**
     * @param remoteFileDirPID 設定する remoteFileDirPID
     */
    public void setRemoteFileDirPID(String remoteFileDirPID) {
        this.remoteFileDirPID = remoteFileDirPID;
    }
    
    /**
     * @return timeChgStr
     */
    public String getTimeChgStr() {
        return timeChgStr;
    }
    
    /**
     * @param timeChgStr 設定する timeChgStr
     */
    public void setTimeChgStr(String timeChgStr) {
        this.timeChgStr = timeChgStr;
    }


}
