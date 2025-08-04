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
 *  <P>タイトル: DPTutiUriageDataSyukeiDao クラス</p>
 *  <P>説明: DPT打ち売上データ集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author ZH.ZHANG
 *  @version 1.0 (2009.06.04) 初版作成
 */
public class DPTutiUriageDataSyukeiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "DPT打ち売上データ集計処理";
    // バッチID
    private static final String BATCH_ID = "URIB071050";
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();    
    /**
     * DPT打ち売上データ集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 店別DPT打ち売上データ更新件数
        int intUpdCount = 0;
        
        // 店別DPT打ち売上データ追加件数
        int intInsCount = 0;        
        // ユーザーID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　DPT打ち売上データ集計処理を開始します。");
        invoker.infoLog(strUserID + "　：　店別DPT打ち売上データ更新処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement("updateTenDptuchiUri");
        // バッチID
        preparedStatementEx.setString(1, BATCH_ID);
        // システム日付
        preparedStatementEx.setString(2, DBSERVER_DT);
        
        // SQLを実行して、処理件数をセットする
        intUpdCount = preparedStatementEx.executeUpdate();
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intUpdCount + "件の店別DPT打ち売上データを更新しました。");
        invoker.infoLog(strUserID + "　：　店別DPT打ち売上データ更新処理を終了します。"); 
        invoker.infoLog(strUserID + "　：　店別DPT打ち売上データ追加処理を開始します。");
        
        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement("insertTenDptuchiUri");

        // SQLを実行して、処理件数をセットする
        intInsCount = preparedStatementEx.executeUpdate();
        // ログ出力       
        invoker.infoLog(strUserID + "　：　" + intInsCount + "件の店別DPT打ち売上データを追加しました。");
        invoker.infoLog(strUserID + "　：　店別DPT打ち売上データ追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　DPT打ち売上データ集計処理を終了します。");

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
            DaoIf dao = new DPTutiUriageDataSyukeiDao();
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
