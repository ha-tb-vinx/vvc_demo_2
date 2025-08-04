package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <P>タイトル: UriageRuisekiIsouDao クラス</p>
 *  <P>説明: 売上累積データ移送処理です。</p>
 *  <P>著作権: Copyright (c) 2011</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author T.Kuzuhara
 *  @version 1.0 
 *  @Version 3.00 (2013.10.09) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応 課題対応 №025
 */
public class UriageRuisekiIsouDao implements DaoIf {
    
    // バッチID
    private static final String BATCH_ID = "URIB241010";
    
    // バッチ処理名
    private static final String BATCH_NAME = "売上累積データ移送処理";
    
    // 削除用SQL文変数
    private static final String DEL_IF_URIAGE_RUISEKI = "deleteIfUriageRuiseki";
    
    // 登録用SQL文変数
    private static final String INS_IF_URIAGE_RUISEKI = "createIfUriageRuiseki";
    
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /**
     * 売上累積データ移送処理を行う
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
        invoker.infoLog(strUserID + "　：　売上累積データ移送処理を開始します。");
        invoker.infoLog(strUserID + "　：　IF売上累積データ追加処理を開始します。");
        
        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementExDel = invoker.getDataBase().prepareStatement(DEL_IF_URIAGE_RUISEKI);
        preparedStatementExIns = invoker.getDataBase().prepareStatement(INS_IF_URIAGE_RUISEKI);
        preparedStatementExIns.setString(1, DBSERVER_DT);
        preparedStatementExIns.setString(2, BATCH_ID);
        preparedStatementExIns.setString(3, DBSERVER_DT);
        preparedStatementExIns.setString(4, BATCH_ID);
        preparedStatementExIns.setString(5, DBSERVER_DT);
        
        // 全件削除
        preparedStatementExDel.executeUpdate();
        
        // 移送
        insertCount = preparedStatementExIns.executeUpdate();
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　" + insertCount + "件のIF売上累積データを追加しました。");
        invoker.infoLog(strUserID + "　：　IF売上累積データ追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　売上累積データ移送処理を終了します。");
        
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
