package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: SeisanJokyoDataCreateDao クラス</p>
 *  <P>説明: 精算状況データ作成処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 
 */

public class SeisanJokyoDataCreateDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "精算状況データ作成処理";
    // バッチID
    private static final String BATCH_ID = "URIB021010";
    // 登録精算状況SQL文用変数
    private static final String INS_SEISAN_JOKYO = "createSeisanJokyo";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /**
     * 店別精算状況データ追加処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 店別精算状況データ追加件数
        int intCount = 0;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　精算状況データ作成処理を開始します。");
        invoker.infoLog(strUserID + "　：　店別精算状況データ追加処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_SEISAN_JOKYO);
        preparedStatementEx.setString(1, BATCH_DT);
        preparedStatementEx.setString(2, BATCH_ID);
        preparedStatementEx.setString(3, DBSERVER_DT);
        preparedStatementEx.setString(4, BATCH_ID);
        preparedStatementEx.setString(5, DBSERVER_DT);
        preparedStatementEx.setString(6, COMP_CD);
        preparedStatementEx.setString(7, BATCH_DT);
        preparedStatementEx.setString(8, COMP_CD);
        preparedStatementEx.setString(9, BATCH_DT);
        preparedStatementEx.setString(10, BATCH_DT);

        // SQLを実行して、処理件数をセットする
        intCount = preparedStatementEx.executeUpdate();

        invoker.infoLog(strUserID + "　：　" + intCount + "件の店別精算状況データを追加しました。");
        invoker.infoLog(strUserID + "　：　店別精算状況データ追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　精算状況データ作成処理を終了します。");

    }

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {

        return null;
    }

    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {

    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new SeisanJokyoDataCreateDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
