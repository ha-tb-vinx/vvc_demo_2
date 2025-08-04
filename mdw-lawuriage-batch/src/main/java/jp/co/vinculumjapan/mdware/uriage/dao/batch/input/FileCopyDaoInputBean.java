package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: FileCopyDaoInputBean クラス</p>
 * <p>説明: FileCopyDaoInputBeanの入力用Bean</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @author S.Arakawa
 * @version 3.00 (2013.10.24) S.Arakawa [CUS00057] ランドローム様　POSインターフェイス仕様変更対応　初版作成
 */

public class FileCopyDaoInputBean {

    private String fromDirId; // コピー元ディレクトリ（システムコントロールID）
    private String fromFileId; // コピー元ファイル名（システムコントロールID）
    private String toDirId; // コピー先ディレクトリ （システムコントロールID）
    private String toFileId; // コピー先ファイル名（システムコントロールID）

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

}
