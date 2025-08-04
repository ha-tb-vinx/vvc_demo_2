package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;


/**
 * <p>タイトル: BumonTenkenJisekiSyukeiDao クラス</p>
 * <p>説明　: 実績集計処理(部門点検)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.05.20) Hirata FIVImart様対応
 * 
 */
public class BumonTenkenJisekiSyukeiDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "実績集計処理(部門点検)";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMP部門点検データ";

    // 追加SQL文用定数
    private static final String INS_SQL = "insertTmpBumonTenken";
    // 削除SQL文用定数
    private static final String DEL_SQL = "deleteTmpBumonTenken";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        // 
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDelete = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();
            
            // SQLを取得し、TMPテーブルを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            // SQLを取得し、パラメータを条件にバインドする
            preparedStatementExIns = invoker.getDataBase().prepareStatement(INS_SQL);
            int index = 1;
            preparedStatementExIns.setString(index++, userId);
            preparedStatementExIns.setString(index++, dbServerTime);
            preparedStatementExIns.setString(index++, userId);
            preparedStatementExIns.setString(index++, dbServerTime);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // APPEND INSERTした内容確定する必要があるのでcommitを行う
            invoker.getDataBase().commit();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }

        }
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object
     */
    public void setInputObject(Object input) throws Exception {
    }

    /**
     * アウトプットBeanを取得する
     * 
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new BumonTenkenJisekiSyukeiDao();
            new StandAloneDaoInvoker("mm").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
