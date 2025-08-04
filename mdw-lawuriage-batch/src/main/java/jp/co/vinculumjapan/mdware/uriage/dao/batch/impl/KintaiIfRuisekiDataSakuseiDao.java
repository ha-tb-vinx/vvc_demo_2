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
 * 
 * <p>タイトル: KintaiIfRuisekiDataSakuseiDao.java クラス</p>
 * <p>説明　: 勤怠IF用累積データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.16) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2023.10.17) TRI.NH #19142 MKV対応
 *
 */
public class KintaiIfRuisekiDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "勤怠IF用累積データ作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "勤怠IF用累積データ";

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
        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // IF時間帯DPT客数ワークから勤怠IF用累積データを登録する
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getDtKintaiIfRuisekiInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を終了します。");

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

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * IF時間帯DPT客数ワークから勤怠IF用累積データを登録するSQLを取得する
     * 
     * @return 勤怠IF用累積データ登録SQL
     */
    private String getDtKintaiIfRuisekiInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO DT_KINTAI_IF_RUISEKI( ");
        sql.append("     COMP_CD ");
        sql.append("    ,SEQ_RB ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,URIAGE_NUKI_VL ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,DATA_RENKEI_FG ");
        // #19142 2020.12.03 TRI.NH ADD (S)
        sql.append("    ,ARARI_VL ");
        // #19142 2020.12.03 TRI.NH ADD (E)
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     WIJDK.COMP_CD ");
        sql.append("    ,SEQ_DT_KINTAI_IF_RUISEKI.NEXTVAL ");
        sql.append("    ,WIJDK.YR || WIJDK.MN || WIJDK.DD ");
        sql.append("    ,WIJDK.TENPO_CD ");
        sql.append("    ,WIJDK.BUNRUI1_CD ");
        sql.append("    ,CASE ");
        sql.append("        WHEN TRIM(WIJDK.TIME) >= '24' THEN '23' ");
        sql.append("        ELSE TRIM(WIJDK.TIME) ");
        sql.append("     END ");
        sql.append("    ,WIJDK.URIAGE_VL ");
        sql.append("    ,WIJDK.URIAGE_QT ");
        sql.append("    ,WIJDK.BUNRUI1_KYAKU_QT ");
        sql.append("    ,'0' ");
        // #19142 2020.12.03 TRI.NH ADD (S)
        sql.append("    ,ARARI_VL ");
        // #19142 2020.12.03 TRI.NH ADD (E)
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_IF_JIKANTAI_DPT_KYKUSU WIJDK ");
        sql.append("WHERE ");
        sql.append("    WIJDK.COMP_CD                       = ? AND ");
        sql.append("    WIJDK.YR || WIJDK.MN || WIJDK.DD    = ? ");

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
            DaoIf dao = new KintaiIfRuisekiDataSakuseiDao();
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
