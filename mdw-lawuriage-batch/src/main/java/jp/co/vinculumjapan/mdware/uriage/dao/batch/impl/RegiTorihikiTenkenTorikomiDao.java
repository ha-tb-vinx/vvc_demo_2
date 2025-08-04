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
 * <p>タイトル: RegiTorihikiTenkenTorikomiDao クラス</p>
 * <p>説明　: POS実績取込処理(レジ取引点検)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.18) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * @Version 3.02 (2016.05.20) Hirata FIVImart様対応
 * @Version 3.03 (2023.02.24) TUNG.LT #6733 MKH対応
 * 
 */
public class RegiTorihikiTenkenTorikomiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理(レジ取引点検)";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMPレジ取引点検データ";

    // 追加SQL文用定数
    private static final String INS_SQL = "insertTmpRegiTorihikiTenken";
    // 削除SQL文用定数
    private static final String DEL_SQL = "deleteTmpRegiTorihikiTenken";
    // #6733 ADD 2023.02.24 TUNG.LT (S)
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // #6733 ADD 2023.02.24 TUNG.LT (E)

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
            preparedStatementExIns.setString(index++, COMP_CD);
            preparedStatementExIns.setString(index++, userId);
            preparedStatementExIns.setString(index++, dbServerTime);
            preparedStatementExIns.setString(index++, userId);
            preparedStatementExIns.setString(index++, dbServerTime);
            // #6733 ADD 2023.02.24 TUNG.LT (S)
            preparedStatementExIns.setString(index++, BATCH_DT);
            // #6733 ADD 2023.02.24 TUNG.LT (E)

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
            DaoIf dao = new RegiTorihikiTenkenTorikomiDao();
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
