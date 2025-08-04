package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: UriageRuisekiSabunSakuseiDao クラス</p>
 *  <P>説明: 「IF売上累積データ」に「売上累積差分データ」を加算する。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author T.Urano
 *  @version 1.0 (2011.09.14) 初版作成
 */
public class UriageRuisekiSabunIsouDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "売上累積差分データ移送処理";
    // バッチID
    private static final String BATCH_ID = "URIB224020";
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();    
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /**
     * 売上累積差分データ移送処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　売上累積差分データ移送処理を開始します。");

        // SQLを取得するし、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement("selectDtUriageRuisekiSabun");
        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        
        // SQLを実行
        ResultSet rs = preparedStatementEx.executeQuery();

        // 0件の場合
        if (!rs.next()) {
            rs.close();

            // ログ出力
            invoker.infoLog(strUserID + "　：　売上累積差分データが存在しません。");
            invoker.infoLog(strUserID + "　：　売上累積差分データ移送処理は行われませんでした。");
            
            // 処理終了
            invoker.infoLog(strUserID + "　：　売上累積差分データ移送処理を終了します。");
            return;
        } 
        rs.close();
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　IF売上累積データ加算処理を開始します。");
        
        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement("updateIfDtUriageRuisekiSabun");
        // 更新者ID
        preparedStatementEx.setString(1, BATCH_ID);
        // 更新年月日
        preparedStatementEx.setString(2, DBSERVER_DT);

        // IF売上累積データ更新
        int intUpdCount = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intUpdCount + "件のIF売上累積データを更新しました。");
        invoker.infoLog(strUserID + "　：　IF売上累積データ加算処理を終了します。");

        
        // ログ出力
        invoker.infoLog(strUserID + "　：　IF売上累積データ追加処理を開始します。");

        // SQLを取得するし、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement("insertIfDtUriageRuisekiSabun");
        // IF作成日時
        preparedStatementEx.setString(1, DBSERVER_DT);
        // 作成者ID
        preparedStatementEx.setString(2, BATCH_ID);
        // 作成年月日
        preparedStatementEx.setString(3, DBSERVER_DT);
        // 更新者ID
        preparedStatementEx.setString(4, BATCH_ID);
        // 更新年月日
        preparedStatementEx.setString(5, DBSERVER_DT);

        // SQLを実行して、処理件数をセットする
        int intInsCount = preparedStatementEx.executeUpdate();
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intInsCount + "件のIF売上累積データを追加しました。”");
        invoker.infoLog(strUserID + "　：　IF売上累積データ追加処理を終了します。"); 

        invoker.infoLog(strUserID + "　：　売上累積差分データ移送処理を終了します。");
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
            DaoIf dao = new UriageRuisekiSabunIsouDao();
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
