package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル: リリース確認処理</p>
 * <p>説明　　: 売上管理のリリース確認をします</p>
 * <p>著作権　: Copyright (c) 2014</p>
 * <p>会社名　: VINX</p>
 * @author S.Arakawa
 * @version 3.00 (2014.02.26) S.Arakawa [結合0122] ランドローム様対応 リリース確認用テストバッチ
 */
public class ReleaseCheckDao implements DaoIf {

    /** バッチ名称 */
    private static final String BAT_NAME = "リリース確認（売上管理）";

    /**
     * データアクセス処理を実行します。
     */
    public void executeDataAccess(DaoInvokerIf invokerIf) throws Exception {

        /** ユーザーIDの取得 */
        String userId = invokerIf.getUserId();

        /** バッチ開始ログ出力 */
        invokerIf.infoLog(userId + " " + BAT_NAME + "：" + "リリースの確認処理を開始します。");

        /** オンライン日付の取得 */
        {
            // 開始ログ出力
            invokerIf.infoLog(userId + " " + BAT_NAME + "：" + "オンライン日付の取得を開始します。");

            // オンライン日付
            String onlineDt = ResorceUtil.getInstance().getPropertie("ONLINE_DT");

            // オンライン日付出力
            invokerIf.infoLog(userId + " " + BAT_NAME + "：" + "オンライン日付は [" + onlineDt + "] です。");

            // 終了ログ出力
            invokerIf.infoLog(userId + " " + BAT_NAME + "：" + "オンライン日付の取得を終了しました。");
        }

        /** バッチ日付の取得 */
        {
            // 開始ログ出力
            invokerIf.infoLog(userId + " " + BAT_NAME + "：" + "バッチ日付の取得を開始します。");

            // バッチ日付
            String batchDt = ResorceUtil.getInstance().getPropertie("BATCH_DT");

            // バッチ日付出力
            invokerIf.infoLog(userId + " " + BAT_NAME + "：" + "バッチ日付は [" + batchDt + "] です。");

            // 終了ログ出力
            invokerIf.infoLog(userId + " " + BAT_NAME + "：" + "バッチ日付の取得を終了しました。");
        }

        /** バッチ終了ログ出力 */
        invokerIf.infoLog(userId + " " + BAT_NAME + "：" + "リリースの確認処理を終了します。");

    }

    public Object getOutputObject() {
        return null;
    }

    public void setInputObject(Object obj) {
    }

}
