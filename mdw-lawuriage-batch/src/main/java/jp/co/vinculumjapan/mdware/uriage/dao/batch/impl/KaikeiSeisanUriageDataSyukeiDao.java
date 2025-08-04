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
 * <p>タイトル: KaikeiSeisanUriageDataTorikomiDao クラス</p>
 * <p>説明　　:「会計精算売上集計前ワーク」より、「会計精算売上ワーク」へ挿入する</p>
 * <p>著作権　: Copyright (c) 2016</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 *
 * @author VINX
 * @Version 1.00 (2016.11.14) Y.Itaki FIVI対応(#2676)
 *
 */
public class KaikeiSeisanUriageDataSyukeiDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "会計精算売上データ集計処理";
    /** 登録先テーブル名称 */
    private static final String TABLE_NAME = "会計精算売上ワークテーブル";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_KAIKEI_SEISAN";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        PreparedStatementEx preparedStatementDelete = null;
        String dbServerTime = "";

        try {

            // レジ別取引精算データの追加
            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkKaikiSeisanSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, userId);
            preparedStatementExIns.setString(6, dbServerTime);
            preparedStatementExIns.setString(7, userId);
            preparedStatementExIns.setString(8, dbServerTime);


            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME + "の追加を終了します。");

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
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 会計精算売上集計前ワークから会計精算売上ワークへ登録するSQLを取得する
     *
     * @return 会計精算売上ワーク登録クエリー
     */
    private String getWkKaikiSeisanSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_KAIKEI_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,SEISANHYO_KOMOKU_CD ");
        sql.append("    ,URIKAKE_KOMOKU_CD ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,KEN_QT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT  ");
        sql.append("     COMP_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,SEISANHYO_KOMOKU_CD ");
        sql.append("    ,URIKAKE_KOMOKU_CD ");
        sql.append("    ,SUM(KINGAKU_VL) AS KINGAKU_VL ");
        sql.append("    ,SUM(KEN_QT) AS KEN_QT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_KAIKEI_SEISAN_SYUKEIMAE ");
        sql.append("GROUP BY ");
        sql.append("     COMP_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,SEISANHYO_KOMOKU_CD ");
        sql.append("    ,URIKAKE_KOMOKU_CD ");
        sql.append("    ,DATA_SAKUSEI_DT ");

        sql.append("UNION ALL ");
        sql.append("SELECT  ");
        sql.append("     COMP_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,SEISANHYO_KOMOKU_CD ");
        sql.append("    ,' ' AS URIKAKE_KOMOKU_CD ");
        sql.append("    ,SUM(KINGAKU_VL) AS KINGAKU_VL ");
        sql.append("    ,SUM(KEN_QT) AS KEN_QT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_KAIKEI_SEISAN_SYUKEIMAE ");
        sql.append("WHERE ");
        sql.append("    SEISANHYO_KOMOKU_CD = '0002' ");
        sql.append("GROUP BY ");
        sql.append("     COMP_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,SEISANHYO_KOMOKU_CD ");
        sql.append("    ,DATA_SAKUSEI_DT ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
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
            DaoIf dao = new RegiTorihikiSeisanMstJohoSyutokuDao();
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
    // 2016/05/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

}
