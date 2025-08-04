package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;


/**
 *  <P>タイトル: DtRegiTorihikiSeisanCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2016</p>
 *  <P>会社名: VINX</P>
 *  @author T.Chihara
 *  @Version 1.00 2016.11.28  T.Chihara  FIVI対応 新規作成
 *  @Version 1.01 2017.01.19  T.Kamei    FIVI対応 #3637
 *  @Version 1.02 2017.04.04  X.Liu    FIVI対応 #4389
 */
public class DtRegiTorihikiSeisanCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "レジ別取引精算データ作成処理";
    /** バッチID */
    private static final String BATCH_ID = "URIB501030";
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
        invoker.infoLog(strUserID + "　：　レジ別取引精算データ作成処理を開始します。");
        // #3637 2017.01.19 T.Kamei MOD (S)
        //invoker.infoLog(strUserID + "　：　レジ別取引精算データの登録処理を開始します。");
        invoker.infoLog(strUserID + "　：　レジ別取引精算データの登録・更新処理を開始します。");
        // #3637 2017.01.19 T.Kamei MOD (E)

        // レジ別取引精算データ挿入処理
        // SQLを取得し、パラメータを条件にバインドする
        int IDX = 1;
        IDX = 1;
        preparedStatementIns = invoker.getDataBase().prepareStatement("insertDtRegiTorihikiSeisan");
        // #3637 2017.01.19 T.Kamei ADD (S)
        preparedStatementIns.setString(IDX++, COMP_CD);
        //#4389 Del X.Liu 2017.04.04 (S)
//        preparedStatementIns.setString(IDX++, BATCH_DT);
        //#4389 Del X.Liu 2017.04.04 (E)
        // #3637 2017.01.19 T.Kamei ADD (E)
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        // #3637 2017.01.19 T.Kamei ADD (S)
        preparedStatementIns.setString(IDX++, BATCH_DT);
        // #3637 2017.01.19 T.Kamei ADD (E)
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        // #3637 2017.01.19 T.Kamei ADD (S)
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        // #3637 2017.01.19 T.Kamei ADD (E)
        preparedStatementIns.setString(IDX++, BATCH_DT);
        // #3637 2017.01.19 T.Kamei DEL (S)
        //preparedStatementIns.setString(IDX++, COMP_CD);
        //preparedStatementIns.setString(IDX++, BATCH_DT);
        // #3637 2017.01.19 T.Kamei DEL (E)

        // 実行
        intInsertCount = preparedStatementIns.executeUpdate();

        // ログ出力
        // #3637 2017.01.19 T.Kamei MOD (S)
        //invoker.infoLog(strUserID + "　：　レジ別取引精算データの登録処理を終了します。登録件数は" + intInsertCount + "件です。");
        invoker.infoLog(strUserID + "　：　レジ別取引精算データの登録・更新処理を終了します。登録・更新件数は" + intInsertCount + "件です。");
        // #3637 2017.01.19 T.Kamei MOD (E)
        invoker.infoLog(strUserID + "　：　レジ別取引精算データ作成処理を終了します。");
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