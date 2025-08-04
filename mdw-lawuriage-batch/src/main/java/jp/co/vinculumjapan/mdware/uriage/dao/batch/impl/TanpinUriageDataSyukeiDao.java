package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: TanpinUriageDataSyukeiDao クラス</p>
 *  <P>説明: 単品売上データ集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 
 *  @Version 3.0  (2013.10.17) S.Arakawa [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 */
public class TanpinUriageDataSyukeiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "単品売上データ集計処理";
    // バッチID
    private static final String BATCH_ID = "URIB081020";
    // 登録単品用SQL文変数
    private static final String INS_TANPIN = "createTanpin";
    private static final String INS_TANPIN_MST = "createTanpinMst";
    private static final String INS_TANPIN_TNP = "createTanpinTnp";
    private static final String INS_SAMMARY_SIR = "createSammarySir";
//    // 削除単品用SQL文変数
//    private static final String DEL_TANPIN = "deleteTanpin";    
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    
    /**
     * 単品売上データ集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // IF単品別売上データ追加件数
        int intCountTanpin = 0;
        // IF単品別売上データ削除件数
        int intCountTanpinDel = 0;        
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　単品売上データ集計処理を開始します。");

        invoker.infoLog(strUserID + "　：　IF単品別売上データ(マスタ用)追加処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする(マスタ用)
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_TANPIN_MST);
        preparedStatementEx.setString(1, DBSERVER_DT);
        preparedStatementEx.setString(2, BATCH_ID);
        preparedStatementEx.setString(3, DBSERVER_DT);
        preparedStatementEx.setString(4, BATCH_ID);
        preparedStatementEx.setString(5, DBSERVER_DT);
        preparedStatementEx.setString(6, COMP_CD);

        // IF単品別売上データ追加件数を設定する(マスタ用)
        intCountTanpin = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCountTanpin + "件のIF単品別売上データ(マスタ用)を追加しました。");
        invoker.infoLog(strUserID + "　：　IF単品別売上データ(マスタ用)追加処理を終了します。");

        invoker.infoLog(strUserID + "　：　IF単品別売上データ(店舗用)追加処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする(店舗用)
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_TANPIN_TNP);
        preparedStatementEx.setString(1, DBSERVER_DT);
        preparedStatementEx.setString(2, BATCH_ID);
        preparedStatementEx.setString(3, DBSERVER_DT);
        preparedStatementEx.setString(4, BATCH_ID);
        preparedStatementEx.setString(5, DBSERVER_DT);
        preparedStatementEx.setString(6, COMP_CD);

        // IF単品別売上データ追加件数を設定する(店舗用)
        intCountTanpin = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCountTanpin + "件のIF単品別売上データ(店舗用)を追加しました。");
        invoker.infoLog(strUserID + "　：　IF単品別売上データ(店舗用)追加処理を終了します。");

        invoker.infoLog(strUserID + "　：　新単品サマリワーク(仕入用)追加処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする(仕入用)
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_SAMMARY_SIR);
        preparedStatementEx.setString(1, COMP_CD);

        // IF単品別売上データ追加件数を設定する(仕入用)
        intCountTanpin = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCountTanpin + "件の新単品サマリワーク(仕入用)を追加しました。");
        invoker.infoLog(strUserID + "　：　新単品サマリワーク(仕入用)追加処理を終了します。");

        invoker.infoLog(strUserID + "　：　単品売上データ集計処理を終了します。");
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
            DaoIf dao = new TanpinUriageDataSyukeiDao();
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
