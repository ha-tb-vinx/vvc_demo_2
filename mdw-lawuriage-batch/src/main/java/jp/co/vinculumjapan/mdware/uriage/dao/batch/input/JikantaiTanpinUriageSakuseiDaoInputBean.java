package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: JikantaiTanpinUriageSakuseiDaoInputBean クラス</p>
 * <p>説明　: 時間帯単品売上作成処理用Inputクラス</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.16) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.20) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応(ファイル送信処理)
 * 
 */
public class JikantaiTanpinUriageSakuseiDaoInputBean {

    /** ファイル名 */
    private String fileName = "";
    /** 日付置換文字列PID */
    private String dateChgStrPID = "";
    /** 時刻置換文字列 */
    private String timeChgStr = "";

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

    /**
     * 日付置換文字列PIDを取得します。
     * @return 日付置換文字列PID
     */
    public String getDateChgStrPID() {
        return dateChgStrPID;
    }

    /**
     * 日付置換文字列PIDを設定します。
     * @param dateChgStrPID 日付置換文字列PID
     */
    public void setDateChgStrPID(String dateChgStrPID) {
        this.dateChgStrPID = dateChgStrPID;
    }

    /**
     * 時刻置換文字列を取得します。
     * @return 時刻置換文字列
     */
    public String getTimeChgStr() {
        return timeChgStr;
    }

    /**
     * 時刻置換文字列を設定します。
     * @param timeChgStr 時刻置換文字列
     */
    public void setTimeChgStr(String timeChgStr) {
        this.timeChgStr = timeChgStr;
    }

}
