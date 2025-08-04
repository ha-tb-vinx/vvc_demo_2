package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: HamperItemSyukeiDao クラス</p>
 * <p>説明　: 店舗・計上日・商品コードで売上金額等を集約する。</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.06.01) M.Kanno 新規作成
 * @Version 1.01 (2016.06.02) K.Hirano FIVI(#1504対応)
 * @Version 1.02 (2016.08.05) Y.Itaki FIVI(#1879対応)
 *
 */


public class HamperItemSyukeiDao implements DaoIf{

    // バッチ処理名
    private static final String DAO_NAME = "ハンパー商品集約";
    // バッチID
    private static final String BATCH_ID = "URIB012530";
    // システム日付取得
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // 検索店別DPT売上SQL文用定数
    private static final String INS_TANPIN_SEISAN_H_B_C = "insertDtTanpinSeisanHmprBrCk";



    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + DAO_NAME;
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;
        int rs = 0;

        // ログ出力
        invoker.infoLog(strUserID + "　：　ハンパー商品集約処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_TANPIN_SEISAN_H_B_C);

        preparedStatementEx.setString(1, BATCH_ID);
        preparedStatementEx.setString(2, DBSERVER_DT);
        preparedStatementEx.setString(3, BATCH_ID);
        preparedStatementEx.setString(4, DBSERVER_DT);
        // 2016.06.02 VINX K.Hirano FIVI(#1504対応) ADD(S)
        preparedStatementEx.setString(5, BATCH_DT);
        // 2016.06.02 VINX K.Hirano FIVI(#1504対応) ADD(E)

        // SQLを実行する
        rs = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + rs + "件の店別DPT売上データを更新しました。");
        invoker.infoLog(strUserID + "　：　ハンパー商品集約処理を終了します。");

    }


    public Object getOutputObject() throws Exception {

        return null;
    }

    public void setInputObject(Object input) throws Exception {

    }

}
