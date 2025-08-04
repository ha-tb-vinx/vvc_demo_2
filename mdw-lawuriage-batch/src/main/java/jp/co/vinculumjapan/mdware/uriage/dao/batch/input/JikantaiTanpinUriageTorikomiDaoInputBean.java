package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * 
 * <p>タイトル: JikantaiTanpinUriageTorikomiDaoInputBean.java クラス</p>
 * <p>説明　: 時間帯単品売上取込処理InputBean</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.10.28) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応 結合テスト課題対応 №058
 *
 */
public class JikantaiTanpinUriageTorikomiDaoInputBean {

    /** 時刻置換文字列 */
    private String timeChgStr = "";

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
