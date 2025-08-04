package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 * <p>タイトル: VoucherSiyouJisekiImportDao クラス</p>
 * <p>説明　: Voucher使用実績取込処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.10.18) VINX J.Endo FIVI対応
 * @Version 1.01 (2016.12.09) VINX J.Endo FIVI対応 Voucher使用実績データレイアウト変更 #3012
 * @Version 1.02 (2017.01.19) VINX J.Endo FIVI対応 Cレコードが複数レコード発生する対応 #3468
 * @Version 1.03 (2017.05.18) VINX J.Endo FIVI対応 #5075
 */
public class VoucherSiyouJisekiImportDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ処理名
    private static final String DAO_NAME = "Voucher使用実績取込処理";
    // 支払種別カテゴリーコードの抽出対象
    private static final String PYMT_TYPE_TARGET = "OVC";

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
        PreparedStatementEx preparedStatementExInsert = null;

        int insertCount = 0;

        try {
            int index = 1;
            // SQL文を取得する
            preparedStatementExInsert = invoker.getDataBase().prepareStatement(getInsertSql());
            preparedStatementExInsert.setString(index++, COMP_CD);
            preparedStatementExInsert.setString(index++, userId);
            preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
            preparedStatementExInsert.setString(index++, userId);
            preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
            preparedStatementExInsert.setString(index++, String.format("%-30s", ""));
            preparedStatementExInsert.setString(index++, PYMT_TYPE_TARGET);
            
            insertCount = preparedStatementExInsert.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件のVoucher使用実績を追加しました。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExInsert != null) {
                    preparedStatementExInsert.close();
                }
            } catch (Exception e2) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 出力データ登録用SQLを取得する
     * @return 出力データ登録用SQL
     */
    private String getInsertSql() {
        StringBuilder sql = new StringBuilder();

        //2017.01.19 J.Endo FIVI対応(#3468) MOD(S)
        //sql.append("INSERT INTO DT_VOUCHER_JISEKI( ");
        //sql.append("    COMP_CD ");
        //sql.append("   ,KEIJO_DT ");
        //sql.append("   ,TENPO_CD ");
        //sql.append("   ,REGISTER_NO ");
        //sql.append("   ,TRANSACTION_NO ");
        //sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        //sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        //sql.append("   ,CREDIT_NO ");
        //sql.append("   ,V_EXPIRY_TS ");
        //sql.append("   ,SHIHARAI_VL ");
        //sql.append("   ,ONLINE_V_KB ");
        //sql.append("   ,SAP_KB ");
        //sql.append("   ,BLYNK_KB ");
        //sql.append("   ,INSERT_USER_ID ");
        //sql.append("   ,INSERT_TS ");
        //sql.append("   ,UPDATE_USER_ID ");
        //sql.append("   ,UPDATE_TS ");
        //sql.append("   ,V_BAR_CD ");
        ////2016.12.09 J.Endo FIVI対応(#3012) ADD(S)
        //sql.append("   ,CASHIER_ID ");
        ////2016.12.09 J.Endo FIVI対応(#3012) ADD(E)
        //sql.append("    ) ");
        //sql.append("SELECT ");
        //sql.append("    ? ");
        //sql.append("   ,LPAD(STORE,6,'0') ");
        //sql.append("   ,POS ");
        //sql.append("   ,TRANS_NO ");
        //sql.append("   ,PYMT_TYPE ");
        //sql.append("   ,PYMT_TYPE2 ");
        //sql.append("   ,CREDIT_CARD_NUMBER ");
        //sql.append("   ,DATE_EXPIRY ");
        //sql.append("   ,'0' ");
        //sql.append("   ,'0' ");
        //sql.append("   ,'0' ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        ////2016.12.09 J.Endo FIVI対応(#3012) ADD(S)
        //sql.append("   ,CASHIER_ID ");
        ////2016.12.09 J.Endo FIVI対応(#3012) ADD(E)
        //sql.append("FROM");
        //sql.append("    WK_POS_C_PAYMENT ");
        //sql.append("WHERE");
        //sql.append("    PYMT_TYPE = ? ");
        sql.append("INSERT /*+ APPEND */ INTO DT_VOUCHER_JISEKI( ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,REGISTER_NO ");
        sql.append("   ,TRANSACTION_NO ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,CREDIT_NO ");
        sql.append("   ,V_EXPIRY_TS ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,ONLINE_V_KB ");
        sql.append("   ,SAP_KB ");
        sql.append("   ,BLYNK_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("   ,V_BAR_CD ");
        sql.append("   ,CASHIER_ID ");
        sql.append("   ,KINSYU_VL ");
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    ? ");
        sql.append("   ,SUBSTR(EIGYO_DT,1,8) ");
        sql.append("   ,LPAD(STORE,6,'0') ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,PYMT_TYPE ");
        sql.append("   ,PYMT_TYPE2 ");
        sql.append("   ,CREDIT_CARD_NUMBER ");
        sql.append("   ,MIN(DATE_EXPIRY) AS DATE_EXPIRY ");
        sql.append("   ,SUM(PYMT_AMT) AS PYMT_AMT ");
        sql.append("   ,'0' ");
        sql.append("   ,'0' ");
        sql.append("   ,'0' ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,MIN(CASHIER_ID) AS CASHIER_ID ");
        sql.append("   ,MAX(PYMT_AMT) AS KINSYU_VL ");
        sql.append("FROM");
        sql.append("    WK_POS_C_PAYMENT ");
        sql.append("WHERE");
        //2017/05/18 J.Endo FIVI対応(#5075) MOD(S)
        //sql.append("    PYMT_TYPE = ? ");
        sql.append("    PYMT_TYPE = ? AND ");
        sql.append("    ERR_KB = '0' ");
        //2017/05/18 J.Endo FIVI対応(#5075) MOD(E)
        sql.append("GROUP BY ");
        sql.append("    EIGYO_DT ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,PYMT_TYPE ");
        sql.append("   ,PYMT_TYPE2 ");
        sql.append("   ,DATE_EXPIRY ");
        sql.append("   ,CREDIT_CARD_NUMBER ");
        //2017.01.19 J.Endo FIVI対応(#3468) MOD(E)

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    /**
     * アウトプットBeanを取得する
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }
}
