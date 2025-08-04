package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: InshizeiKensuDataSyukeiDao クラス</p>
 *  <P>説明: 印紙税件数データ集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author ZH.ZHANG
 *  @version 1.0 (2009.06.04) 初版作成
 */
public class InshizeiKensuDataSyukeiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "印紙税件数データ集計処理";
    // バッチID
    private static final String BATCH_ID = "URIB071020";
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    /**
     * 印紙税件数データ集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 店別印紙税対象データ追加件数
        int intInsCount = 0;
        // 店別印紙税対象データ更新件数
        int intUpdateCount = 0;
        
        // ユーザーID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　印紙税件数データ集計処理を開始します。");
        
        // オブジェクトを初期化する
        PreparedStatementEx updatePs = null;
 
        // ログ出力
        invoker.infoLog(strUserID + "　：　店別印紙税対象データ更新処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        updatePs = invoker.getDataBase().prepareStatement("updateKeiTenSirJiseki");
        updatePs.setString(1, BATCH_ID);
        updatePs.setString(2, DBSERVER_DT);
        
        // SQLを実行して、処理件数をセットする
        intUpdateCount = updatePs.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intUpdateCount + "件の店別印紙税対象データを更新しました。");
        invoker.infoLog(strUserID + "　：　店別印紙税対象データ更新処理を終了します。");
        invoker.infoLog(strUserID + "　：　店別印紙税対象データ追加処理を開始します。");        
        
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement("insertKeiTenSirJiseki");

        // SQLを実行して、処理件数をセットする
        intInsCount = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intInsCount + "件の店別印紙税対象データを追加しました。");
        invoker.infoLog(strUserID + "　：　店別印紙税対象データ追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　印紙税件数データ集計処理を終了します。");

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
            DaoIf dao = new InshizeiKensuDataSyukeiDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
