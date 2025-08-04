package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 * <p>タイトル: TenbetsuReceiptbetsuNebikiDataSakuseiDao クラス</p>
 * <p>説明  : 店別レシート別値引データ作成</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: VINX</p>
 *
 * @Version 1.00 (2017.01.31) J.Endo FIVI対応
 *
 */
public class TenbetsuReceiptbetsuNebikiDataSakuseiDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "店別レシート別値引データ作成";

    /** バッチID */
    private static final String BATCH_ID = "URIB012890";

    /** 登録先テーブル名称(店別レシート別値引データ) */
    private static final String DT_TEN_RECEIPT_SEISAN = "店別レシート別値引データ";

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;

        try {
            // データ登録

            // 店別レシート別値引データ
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_TEN_RECEIPT_SEISAN + "の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getBumonSeisanInsertSql());
            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, BATCH_ID);
            preparedStatementExIns.setString(3, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(4, BATCH_ID);
            preparedStatementExIns.setString(5, FiResorceUtility.getDBServerTime());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_TEN_RECEIPT_SEISAN + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_TEN_RECEIPT_SEISAN + "の追加を終了します。");

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
     * 店別レシート別値引データ登録用SQLを取得する
     *
     * @return 店別レシート別値引データ登録用SQL
     */
    private String getBumonSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_TEN_RECEIPT_DISCOUNT ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,REGISTER_NO ");
        sql.append("   ,TRANSACTION_NO ");
        sql.append("   ,SUB_DISCOUNT_CD ");
        sql.append("   ,KENSU ");
        sql.append("   ,NEBIKI_VL ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    ? ");
        sql.append("   ,SUBSTR(EIGYO_DT,1,8) ");
        sql.append("   ,LPAD(STORE,6,'0') ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,DISC_SUBCTGR ");
        sql.append("   ,COUNT(DISC_AMT) ");
        sql.append("   ,SUM(DISC_AMT) ");
        sql.append("   ,MIN(CASHIER_ID) ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ");
        sql.append("    WK_POS_K_DISCOUNT ");
        sql.append("WHERE ");
        sql.append("    ERR_KB = '0' ");
        sql.append("GROUP BY ");
        sql.append("    STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,DISC_SUBCTGR ");

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
}
