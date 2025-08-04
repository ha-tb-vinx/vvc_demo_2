package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: IfSapShiiresakiImportDaoInputBean クラス</p>
 * <p>説明: SAP IF 卸返品VATINVOICE 取込処理 InputBean</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @author VINX
 * @Version 1.00 (2016.09.07) A.Narita FIVIMART対応
 */

public class IfSapOroshiHenpinVatInvoiceImportDaoInputBean {

    // データファイル名
    private String dataFileName = null;
    // バックアップファイル名
    private String backupFileName = null;
    // フォーマットファイル名
    private String formatFileName = null;
    // ログファイル名
    private String logFileName = null;

    /**
     * バックアップファイル名 を返します
     * @return String
     */
    public String getBackupFileName() {
        return backupFileName;
    }

    /**
     * バックアップファイル名 を設定します
     * @param String backupFileName
     */
    public void setBackupFileName(String backupFileName) {
        this.backupFileName = backupFileName;
    }

    /**
     * データファイル名 を返します
     * @return String
     */
    public String getDataFileName() {
        return dataFileName;
    }

    /**
     * データファイル名 を設定します
     * @param String dataFileName
     */
    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    /**
     * フォーマットファイル名 を返します
     * @return String
     */
    public String getFormatFileName() {
        return formatFileName;
    }

    /**
     * フォーマットファイル名 を設定します
     * @param String formatFileName
     */
    public void setFormatFileName(String formatFileName) {
        this.formatFileName = formatFileName;
    }

    /**
     * ログファイル名 を返します
     * @return String
     */
    public String getLogFileName() {
        return logFileName;
    }

    /**
     * ログファイル名 を設定します
     * @param String logFileName
     */
    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

}
