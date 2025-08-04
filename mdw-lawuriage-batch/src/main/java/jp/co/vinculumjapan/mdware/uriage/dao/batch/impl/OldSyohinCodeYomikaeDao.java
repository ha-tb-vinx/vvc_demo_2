package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル: OldSyohinCodeYomikaeDao クラス</p>
 * <p>説明:旧商品コード読替（Aレコード）</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: VINX</p>
 *
 * @author VINX
 * @Version 1.00 (2017.04.20) J.Endo FIVI対応(#4704)
 *
 * @see なし
 */
public class OldSyohinCodeYomikaeDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "旧商品コード読替（Aレコード）";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_POS_A_ITEM_YOMIKAE";

    /**
     * 旧商品コード読替（Aレコード）
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // Aレコードチェックデータを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            // 旧商品コード読替（Aレコード）
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getMaseterCheckTmpPosAItemDaoSql());

            int i=1;
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(i++, userId);
            preparedStatementExIns.setString(i++, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(i++, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件の旧商品コード読替（Aレコード）データを追加しました。");

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
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    /**
     * 旧商品コード読替（Aレコード）SQLを取得する
     *
     * @return 旧商品コード読替（Aレコード）
     */
    private String getMaseterCheckTmpPosAItemDaoSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_POS_A_ITEM_YOMIKAE ( ");
        sql.append("    COMMAND ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,CASHIER_ID ");
        sql.append("   ,FORMAT ");
        sql.append("   ,ATYPE ");
        sql.append("   ,ODR_LINE_IDX ");
        sql.append("   ,SKU ");
        sql.append("   ,QTY ");
        sql.append("   ,WEIGHT_SOLD ");
        sql.append("   ,REG_SELL ");
        sql.append("   ,ACTUAL_SELL2 ");
        sql.append("   ,ACTUAL_SELL ");
        sql.append("   ,REG_SELL_WOT ");
        sql.append("   ,ACTUAL_SELL_WOT2 ");
        sql.append("   ,ACTUAL_SELL_WOT ");
        sql.append("   ,EMP_PURCH ");
        sql.append("   ,ITEM_WEIGH ");
        sql.append("   ,REGULAR_UNIT_SELL ");
        sql.append("   ,GST_TAX ");
        sql.append("   ,DISC4_AMT ");
        sql.append("   ,ITEM_NAME_RECEIPT ");
        sql.append("   ,ITEM_UOM_RECEIPT ");
        sql.append("   ,PRCCHG_NO ");
        sql.append("   ,PRCCHG1_NO ");
        sql.append("   ,PRCCHG2_NO ");
        sql.append("   ,PRCCHG3_NO ");
        sql.append("   ,PRIVILEGE_MEM ");
        sql.append("   ,OVER_WRITE_FLAG ");
        sql.append("   ,HAMPER_ITEM_CD ");
        sql.append("   ,SUPERVISOR_ID ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    COMMAND ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,CASHIER_ID ");
        sql.append("   ,FORMAT ");
        sql.append("   ,ATYPE ");
        sql.append("   ,ODR_LINE_IDX ");
        sql.append("   ,NVL(RS.SYOHIN_CD, SKU) "); // 旧商品コードに一致した場合は商品コード(新)を設定
        sql.append("   ,QTY ");
        sql.append("   ,WEIGHT_SOLD ");
        sql.append("   ,REG_SELL ");
        sql.append("   ,ACTUAL_SELL2 ");
        sql.append("   ,ACTUAL_SELL ");
        sql.append("   ,REG_SELL_WOT ");
        sql.append("   ,ACTUAL_SELL_WOT2 ");
        sql.append("   ,ACTUAL_SELL_WOT ");
        sql.append("   ,EMP_PURCH ");
        sql.append("   ,ITEM_WEIGH ");
        sql.append("   ,REGULAR_UNIT_SELL ");
        sql.append("   ,GST_TAX ");
        sql.append("   ,DISC4_AMT ");
        sql.append("   ,ITEM_NAME_RECEIPT ");
        sql.append("   ,ITEM_UOM_RECEIPT ");
        sql.append("   ,PRCCHG_NO ");
        sql.append("   ,PRCCHG1_NO ");
        sql.append("   ,PRCCHG2_NO ");
        sql.append("   ,PRCCHG3_NO ");
        sql.append("   ,PRIVILEGE_MEM ");
        sql.append("   ,OVER_WRITE_FLAG ");
        sql.append("   ,HAMPER_ITEM_CD ");
        sql.append("   ,SUPERVISOR_ID ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,? ");
        sql.append("   ,'0' ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ");
        sql.append("    TMP_POS_A_ITEM ");
        sql.append("LEFT OUTER JOIN ( ");
        sql.append("    SELECT ");
        sql.append("        RS1.SYOHIN_CD     AS SYOHIN_CD ");
        sql.append("       ,RS1.OLD_SYOHIN_CD AS OLD_SYOHIN_CD ");
        sql.append("    FROM R_SYOHIN RS1 ");
        sql.append("    INNER JOIN ( ");
        sql.append("        SELECT ");
        sql.append("            SYOHIN_CD ");
        sql.append("           ,MAX(YUKO_DT) YUKO_DT ");
        sql.append("        FROM R_SYOHIN ");
        sql.append("        WHERE YUKO_DT <= ? ");
        sql.append("        GROUP BY SYOHIN_CD ");
        sql.append("        ) RS2 ");
        sql.append("        ON  RS1.SYOHIN_CD = RS2.SYOHIN_CD AND ");
        sql.append("            RS1.YUKO_DT  = RS2.YUKO_DT ");
        sql.append("    GROUP BY ");
        sql.append("        RS1.SYOHIN_CD ");
        sql.append("       ,RS1.OLD_SYOHIN_CD ");
        sql.append("    ) RS ");
        sql.append("    ON  LPAD(TRIM(SKU),13,'0') = LPAD(TRIM(RS.OLD_SYOHIN_CD),13,'0') ");

        return sql.toString();
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
            DaoIf dao = new VatInvoiceRegistDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
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
