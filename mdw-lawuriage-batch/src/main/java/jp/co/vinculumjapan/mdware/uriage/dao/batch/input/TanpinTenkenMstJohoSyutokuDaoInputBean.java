package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * 
 * <p>タイトル: TanpinTenkenMstJohoSyutokuDaoInputBean.java クラス</p>
 * <p>説明　: マスタ情報取得処理(単品点検) Input Bean</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.0  (2013.10.18) T.Ooshiro ランドローム様対応
 *
 */
public class TanpinTenkenMstJohoSyutokuDaoInputBean {

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
