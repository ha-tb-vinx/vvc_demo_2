package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <P>タイトル: UriageRuisekiDataSyukeiDao クラス</p>
 *  <P>説明: 売上累積データ集計処理です。</p>
 *  <P>著作権: Copyright (c) 2011</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author T.Kuzuhara
 *  @version 1.0 
 */
public class UriageRuisekiDataSyukeiDao implements DaoIf {
    
    // バッチ処理名
    private static final String BATCH_NAME = "売上累積データ集計処理";
    
    // バッチID
    private static final String BATCH_ID = "URIB221020";
    
    // 削除用SQL文変数
    private static final String DEL_URIAGE_RUISEKI = "deleteUriageRuiseki";
    
    // 登録用SQL文変数
    private static final String INS_URIAGE_RUISEKI = "createUriageRuiseki";
    
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /**
     * 売上累積データ集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        int insertCount = 0;
        
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExDel = null;
        PreparedStatementEx preparedStatementExIns = null;
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　売上累積データ集計処理を開始します。");
        invoker.infoLog(strUserID + "　：　売上累積データ追加処理を開始します。");
        
        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementExDel = invoker.getDataBase().prepareStatement(DEL_URIAGE_RUISEKI);
        preparedStatementExIns = invoker.getDataBase().prepareStatement(INS_URIAGE_RUISEKI);
        preparedStatementExIns.setString(1, DBSERVER_DT);
        preparedStatementExIns.setString(2, BATCH_ID);
        preparedStatementExIns.setString(3, DBSERVER_DT);
        preparedStatementExIns.setString(4, BATCH_ID);
        preparedStatementExIns.setString(5, DBSERVER_DT);
        preparedStatementExIns.setString(6, COMP_CD);
        
        // 全件削除
        preparedStatementExDel.executeUpdate();
        
        // 累積データ追加
        insertCount = preparedStatementExIns.executeUpdate();
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　" + insertCount + "件の売上累積データを追加しました。");
        invoker.infoLog(strUserID + "　：　売上累積データ追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　売上累積データ集計処理を終了します。");
        
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

}
