package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: FtpGetDaoInputBean クラス</p>
 * <p>説明: FtpGetDaoの入力用Bean</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: </p>
 * @author H.Takahashi
 * @version 1.00 (2009/03/11)
 */

public class FtpGetDaoInputBean {
    
    /** サーバアドレス */
    private String host;

     /** FTPユーザ */
    private String loginName;

     /** FTPパスワード */
    private String password;

     /** FTPポート番号 */
    private int port;

     /** リモートディレクトリ */
    private String remoteDirName;

     /** リモートファイル名 */
    private String remoteFileName;    

     /** ローカルディレクトリ */   
    private String localDirName;   
        
     /** ローカルファイル名 */    
    private String localFileName;  
        
        
    /** 
     * サーバアドレス を返します    
     * @return String   
     */ 
    public String getHost() {   
        return host;
    }   
        
    /** 
     * FTPユーザ を返します 
     * @return String   
     */ 
    public String getLoginName() {  
        return loginName;
    }   
        
    /** 
     * FTPパスワード を返します   
     * @return String   
     */ 
    public String getPassword() {   
        return password;
    }   
        
    /** 
     * FTPポート番号 を返します   
     * @return int  
     */ 
    public int getPort() {  
        return port;
    }   
        
    /** 
     * リモートディレクトリ を返します  
     * @return String   
     */ 
    public String getRemoteDirName() {    
        return remoteDirName;
    }   
        
    /** 
     * リモートファイル名 を返します   
     * @return String   
     */ 
    public String getRemoteFileName() {   
        return remoteFileName;
    }   
        
    /** 
     * ローカルディレクトリ を返します  
     * @return String   
     */ 
    public String getLocalDirName() {  
        return localDirName;
    }   
        
    /** 
     * ローカルファイル名 を返します   
     * @return String   
     */ 
    public String getLocalFileName() { 
        return localFileName;
    }   
        
        
    /** 
     * サーバアドレス を設定します   
     * @param String string 
     */ 
    public void setHost(String string) {    
        host = string;
    }   
        
    /** 
     * FTPユーザ を設定します    
     * @param String string 
     */ 
    public void setLoginName(String string) {   
        loginName = string;
    }   
        
    /** 
     * FTPパスワード を設定します  
     * @param String string 
     */ 
    public void setPassword(String string) {    
        password = string;
    }   
        
    /** 
     * FTPポート番号 を設定します  
     * @param int i 
     */ 
    public void setPort(int i) {    
        port = i;
    }   

    /** 
     * リモートディレクトリ を設定します 
     * @param String string 
     */ 
    public void setRemoteDirName(String string) { 
        remoteDirName = string;
    }   

    /** 
     *リモートファイル名 を設定します  
     * @param String string 
     */ 
    public void setRemoteFileName(String string) {    
        remoteFileName = string;
    }   

    /** 
     * ローカルディレクトリ を設定します 
     * @param String string 
     */ 
    public void setLocalDirName(String string) {   
        localDirName = string;
    }   

    /** 
     * ローカルファイル名 を設定します  
     * @param String string 
     */ 
    public void setLocalFileName(String string) {  
        localFileName = string;
    }

}
