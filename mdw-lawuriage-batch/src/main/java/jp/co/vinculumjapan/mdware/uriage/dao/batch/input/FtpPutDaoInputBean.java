package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: BatchWatchDaoInputBean クラス</p>
 * <p>説明: BatchWatchDaoの入力用Bean</p>
 * <p>著作権: Copyright (c) 2008</p>
 * <p>会社名: </p>
 * @author A.Yoshida
 * @version 1.00 (2008/01/28)
 */

public class FtpPutDaoInputBean {
    
    /** サーバアドレス */
    private String host;

     /** FTPユーザ */
    private String loginName;

     /** FTPパスワード */
    private String password;

     /** FTPポート番号 */
    private int port;

     /** 送信先ディレクトリ */
    private String destDirName;

     /** 送信先ファイル名 */
    private String destFileName;    

     /** 送信元ディレクトリ */   
    private String sourceDirName;   
        
     /** 送信元ファイル名 */    
    private String sourceFileName;  
        
        
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
     * 送信先ディレクトリ を返します  
     * @return String   
     */ 
    public String getDestDirName() {    
        return destDirName;
    }   
        
    /** 
     * 送信先ファイル名 を返します   
     * @return String   
     */ 
    public String getDestFileName() {   
        return destFileName;
    }   
        
    /** 
     * 送信元ディレクトリ を返します  
     * @return String   
     */ 
    public String getSourceDirName() {  
        return sourceDirName;
    }   
        
    /** 
     * 送信元ファイル名 を返します   
     * @return String   
     */ 
    public String getSourceFileName() { 
        return sourceFileName;
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
     * 送信先ディレクトリ を設定します 
     * @param String string 
     */ 
    public void setDestDirName(String string) { 
        destDirName = string;
    }   

    /** 
     * 送信先ファイル名 を設定します  
     * @param String string 
     */ 
    public void setDestFileName(String string) {    
        destFileName = string;
    }   

    /** 
     * 送信元ディレクトリ を設定します 
     * @param String string 
     */ 
    public void setSourceDirName(String string) {   
        sourceDirName = string;
    }   

    /** 
     * 送信元ファイル名 を設定します  
     * @param String string 
     */ 
    public void setSourceFileName(String string) {  
        sourceFileName = string;
    }

}
