package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;

/**
 * <p>タイトル: SeisanTorikomiAlarmDao.java クラス</p>
 * <p>説明　: アラーム出力処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.0  (2013.10.17) T.Ooshiro ランドローム様対応
 *
 */
public class SeisanTorikomiAlarmDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "アラーム出力処理";

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");
        // TODO 自動生成されたメソッド・スタブ

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    public void setInputObject(Object input) throws Exception {
        // TODO 自動生成されたメソッド・スタブ

    }

    public Object getOutputObject() throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

}
