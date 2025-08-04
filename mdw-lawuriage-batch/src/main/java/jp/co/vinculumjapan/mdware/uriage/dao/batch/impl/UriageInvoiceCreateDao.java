package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;


/**
 *  <P>タイトル: UriageInvoiceCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2016</p>
 *  <P>会社名: VINX</P>
 *  @author T.Chihara
 *  @Version 1.00 2016.11.18  T.Chihara  FIVI対応 新規作成
 *  @Version 1.01 2017.03.31  X.Liu  #4500
 *  @Version 1.02 2017.04.04  X.Liu  #4389
 *  @Version 1.03 2017.05.25  X.Liu  #5149
 */
public class UriageInvoiceCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "売上INVOICE作成処理";
    /** バッチID */
    private static final String BATCH_ID = "URIB501020";
    /** DB日付取得 */
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    /** バッチ日付取得 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /**
     * 
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        int intInsertCount = 0;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementIns = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　売上INVOICE作成処理を開始します。");
        //#5149 Add X.Liu 2017.05.25 (S)
        invoker.infoLog(strUserID + "　：　売上INVOICE管理明細ワークの登録処理を開始します。");

        // 売上INVOICE管理明細ワーク挿入処理
        // SQLを取得し、パラメータを条件にバインドする
        int IDX = 1;
        intInsertCount = 0;
        preparedStatementIns = null;
        preparedStatementIns = invoker.getDataBase().prepareStatement("insertWkUriageInvoiceKanriM");
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, COMP_CD); 
        preparedStatementIns.setString(IDX++, BATCH_DT); 
        preparedStatementIns.setString(IDX++, BATCH_DT); 
        preparedStatementIns.setString(IDX++, COMP_CD); 
        // 実行
        intInsertCount = preparedStatementIns.executeUpdate();
        // ログ出力
        invoker.infoLog(strUserID + "　：　売上INVOICE管理明細ワークの登録処理を終了します。登録件数は" + intInsertCount + "件です。");
        //#5149 Add X.Liu 2017.05.25 (E)
        invoker.infoLog(strUserID + "　：　売上INVOICE管理ヘッダワークの登録処理を開始します。");

        // 売上INVOICE管理ヘッダワーク挿入処理
        // SQLを取得し、パラメータを条件にバインドする
        //#5149 Mod X.Liu 2017.05.25 (S)
//        int IDX = 1;
        IDX = 1;
        //#5149 Mod X.Liu 2017.05.25 (E)
        preparedStatementIns = invoker.getDataBase().prepareStatement("insertWkUriageInvoiceKanriH");
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        //#4500 Del X.Liu 2017.03.31 (S)
//        preparedStatementIns.setString(IDX++, BATCH_DT);
        //#4500 Del X.Liu 2017.03.31 (E)
        preparedStatementIns.setString(IDX++, COMP_CD);
        //#4389 X.Liu Del 2017.04.04 (S)
//        preparedStatementIns.setString(IDX++, BATCH_DT);
        //#4389 X.Liu Del 2017.04.04 (E)
        // 実行
        intInsertCount = preparedStatementIns.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　売上INVOICE管理ヘッダワークの登録処理を終了します。登録件数は" + intInsertCount + "件です。");
        //#5149 Del X.Liu 2017.05.25 (S)
//        invoker.infoLog(strUserID + "　：　売上INVOICE管理明細ワークの登録処理を開始します。");
//
//        // 売上INVOICE管理明細ワーク挿入処理
//        // SQLを取得し、パラメータを条件にバインドする
//        IDX = 1;
//        intInsertCount = 0;
//        preparedStatementIns = null;
//        preparedStatementIns = invoker.getDataBase().prepareStatement("insertWkUriageInvoiceKanriM");
//        preparedStatementIns.setString(IDX++, BATCH_ID);
//        preparedStatementIns.setString(IDX++, DBSERVER_DT);
//        preparedStatementIns.setString(IDX++, BATCH_ID);
//        preparedStatementIns.setString(IDX++, DBSERVER_DT);
//        preparedStatementIns.setString(IDX++, COMP_CD);
//        preparedStatementIns.setString(IDX++, BATCH_DT);
//        preparedStatementIns.setString(IDX++, BATCH_DT);
//        preparedStatementIns.setString(IDX++, COMP_CD);
//        //#4389 X.Liu Del 2017.04.04 (S)
////        preparedStatementIns.setString(IDX++, BATCH_DT);
//        //#4389 X.Liu Del 2017.04.04 (E)
//        // 実行
//        intInsertCount = preparedStatementIns.executeUpdate();
//
//        // ログ出力
//        invoker.infoLog(strUserID + "　：　売上INVOICE管理明細ワークの登録処理を終了します。登録件数は" + intInsertCount + "件です。");
        //#5149 Del X.Liu 2017.05.25 (E)
        invoker.infoLog(strUserID + "　：　売上INVOICE作成処理を終了します。");
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