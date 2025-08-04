package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * 
 * <p>タイトル: PosNejogeDataSakuseiDaoInputBean.java クラス</p>
 * <p>説明　: PosNejogeDataSakuseiDao用InputBean</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013/10/20) T.Ooshiro ランドローム様対応(ファイル送信処理)
 *
 */
public class PosNejogeDataSakuseiDaoInputBean {

    /** ファイル名 */
    private String fileName = "";

    /**
     * ファイル名を取得します。
     * @return ファイル名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * ファイル名を設定します。
     * @param fileName ファイル名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
