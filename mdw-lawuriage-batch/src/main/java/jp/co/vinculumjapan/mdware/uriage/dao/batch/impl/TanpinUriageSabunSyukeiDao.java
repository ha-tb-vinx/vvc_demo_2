package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: TanpinUriageSabunSyukeiDao クラス</p>
 *  <P>説明: 単品売上差分集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author HX.Su
 *  @version 1.0 
 */
public class TanpinUriageSabunSyukeiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "単品売上差分集計処理";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // 削除単品用SQL文変数
    private static final String DEL_NEWANPIN = "deleteNewTanpinSummary"; 
    // 登録単品用SQL文変数
    private static final String INS_NEWANPIN = "insertNewTanpinSummary"; 

    /**
     * 単品売上差分集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 新単品サマリワーク追加件数
        int intCountNewTanpin = 0;      
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　単品売上差分集計処理を開始します。");
        invoker.infoLog(strUserID + "　：　新単品サマリワーク削除処理を開始します。");
        
        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(DEL_NEWANPIN);
        preparedStatementEx.setString(1, COMP_CD);

        // 新単品サマリワーク（仕入用）削除
        intCountNewTanpin = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCountNewTanpin + "件の新単品サマリワークを削除しました。");
        invoker.infoLog(strUserID + "　：　新単品サマリワーク削除処理を終了します。");

        invoker.infoLog(strUserID + "　：　新単品サマリワーク追加処理を開始します。");


        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_NEWANPIN);
        preparedStatementEx.setString(1, COMP_CD);

        // 新単品サマリワーク追加件数を設定する
        intCountNewTanpin = 0;
        intCountNewTanpin = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCountNewTanpin + "件の新単品サマリワークを追加しました。");
        invoker.infoLog(strUserID + "　：　新単品サマリワーク追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　単品売上差分集計処理を終了します。");
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
            DaoIf dao = new TanpinUriageSabunSyukeiDao();
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
