package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.util.HashMap;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: DPTUriageRuisekiDataSelectDao クラス</p>
 *  <P>説明: DPT売上累積データ抽出処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 
 */
public class DPTUriageRuisekiDataSelectDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "DPT売上累積データ抽出処理";
    // バッチID
    private static final String BATCH_ID = "URIB131020";
    // 削除DPT別売上累積ワークSQL文用定数
    private static final String DEL_DPT_RUISEKI = "deleteDPTRuiseki";
    // 登録DPT別売上累積ワークSQL文用定数
    private static final String INS_DPT_RUISEKI = "createDPTRuiseki";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    // エラー表示用DPT取得
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラー表示用DPT
    private static final String ERR_HYOJI_DPT = ERROR_DPT.keySet().toString().replace("[", "").replace("]", "");  

    /**
     * DPT売上累積データ抽出処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // DPT別売上累積ワーク削除した件数
        int intDeleteWKCount = 0;
        // DPT別売上累積ワーク追加した件数
        int intCreateWKCount = 0;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　DPT売上累積データ抽出処理を開始します。");
        invoker.infoLog(strUserID + "　：　DPT別売上累積ワーク削除処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(DEL_DPT_RUISEKI);
        preparedStatementEx.setString(1, COMP_CD);

        // SQLを実行して、｢DPT別売上累積ワーク」のデータを全件削除する
        intDeleteWKCount = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intDeleteWKCount + "件のDPT別売上累積ワークを削除しました。");
        invoker.infoLog(strUserID + "　：　DPT別売上累積ワーク削除処理を終了します。");
        invoker.infoLog(strUserID + "　：　DPT別売上累積ワーク追加処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_DPT_RUISEKI);
        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // 作成者ID
        preparedStatementEx.setString(2, BATCH_ID);
        // 作成年月日
        preparedStatementEx.setString(3, DBSERVER_DT);
        // 更新者ID
        preparedStatementEx.setString(4, BATCH_ID);
        // 更新年月日
        preparedStatementEx.setString(5, DBSERVER_DT);
        // 法人コード
        preparedStatementEx.setString(6, COMP_CD);
        // 法人コード
        preparedStatementEx.setString(7, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(8, BATCH_DT);
        // DPTコード
        preparedStatementEx.setString(9, ERR_HYOJI_DPT);

        // SQLを実行する
        intCreateWKCount = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateWKCount + "件のDPT別売上累積ワークを追加しました。");
        invoker.infoLog(strUserID + "　：　DPT別売上累積ワーク追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　DPT売上累積データ抽出処理を終了します。");

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
            DaoIf dao = new DPTUriageRuisekiDataSelectDao();
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
