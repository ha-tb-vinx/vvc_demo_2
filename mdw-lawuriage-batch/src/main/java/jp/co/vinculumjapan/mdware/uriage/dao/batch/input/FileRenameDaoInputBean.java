package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: FileRenameDaoInputBean クラス</p>
 * <p>説明: FileRenameDaoの入力用Bean</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @author Y.Tominaga
 * @version 3.00 2013.10.18 ランドローム様対応　ファイルリネーム処理　初版作成
 * 
 */

public class FileRenameDaoInputBean {
    
    /* リネームディレクトリパス */
    private String renameDirPath;
    
    /* リネーム日付 */
    private String renameDt;
    
    /* リネーム時間 */
    private String renameTime;
    
    /* リネームファイル名群 */
    private String renameFile;
    
    
    /**
     * @return renameDt
     */
    public String getRenameDt() {
        return renameDt;
    }

    
    /**
     * @param renameDt セットする renameDt
     */
    public void setRenameDt(String renameDt) {
        this.renameDt = renameDt;
    }

    
    /**
     * @return renameTime
     */
    public String getRenameTime() {
        return renameTime;
    }

    
    /**
     * @param renameTime セットする renameTime
     */
    public void setRenameTime(String renameTime) {
        this.renameTime = renameTime;
    }

    
    /**
     * @return renameDirPath
     */
    public String getRenameDirPath() {
        return renameDirPath;
    }

    
    /**
     * @param renameDirPath セットする renameDirPath
     */
    public void setRenameDirPath(String renameDirPath) {
        this.renameDirPath = renameDirPath;
    }

    
    /**
     * @return renameFile
     */
    public String getRenameFile() {
        return renameFile;
    }

    
    /**
     * @param renameFile セットする renameFile
     */
    public void setRenameFile(String renameFile) {
        this.renameFile = renameFile;
    }


}
