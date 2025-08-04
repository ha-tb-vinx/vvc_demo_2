package mdware.master.batch.common.util;

/**
 * <p>タイトル: FileCopyDaoInputBean クラス</p>
 * <p>説明: FileCopyDaoInputBeanの入力用Bean</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: </p>
 * @author E.Takumi
 * @version 1.00 (2009/09/05)
 */

public class FileCopyDaoInputBean {

    private String fromDirId; 	// コピー元ディレクトリ（システムコントロールID）
    private String fromFileId; // コピー元ファイル名（システムコントロールID）
    private String toDirId; 	// コピー先ディレクトリ （システムコントロールID）
    private String toFileId; 	// コピー先ファイル名（システムコントロールID）
    private String addString;	// 既にファイルが存在した場合に、既存のファイル名に付与する文字列

    /**
     * @return fromDirId
     */
    public String getFromDirId() {
        return fromDirId;
    }

    /**
     * @param fromDirId 設定する fromDirId
     */
    public void setFromDirId(String fromDirId) {
        this.fromDirId = fromDirId;
    }

    /**
     * @return fromFileId
     */
    public String getFromFileId() {
        return fromFileId;
    }

    /**
     * @param fromFileId 設定する fromFileId
     */
    public void setFromFileId(String fromFileId) {
        this.fromFileId = fromFileId;
    }

    /**
     * @return toDirId
     */
    public String getToDirId() {
        return toDirId;
    }

    /**
     * @param toDirId 設定する toDirId
     */
    public void setToDirId(String toDirId) {
        this.toDirId = toDirId;
    }

    /**
     * @return toFileId
     */
    public String getToFileId() {
        return toFileId;
    }

    /**
     * @param toFileId 設定する toFileId
     */
    public void setToFileId(String toFileId) {
        this.toFileId = toFileId;
    }

    /**
     * @return fromDirId
     */
    public String getAddString() {
        return addString;
    }

    /**
     * @param fromDirId 設定する fromDirId
     */
    public void setAddString(String addString) {
        this.addString = addString;
    }


}
