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
 * <p>タイトル: DtPosFCutRirekiCreateDao クラス</p>
 * <p>説明:Ｆレコード履歴カットデータ作成</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: VINX</p>
 *
 * @author VINX
 * @Version 1.00 (2017.07.11) X.Liu FIVI対応(#5580)
 * @see なし
 */
public class DtPosFCutRirekiCreateDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "Ｆレコード履歴カットデータ作成";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // バッチID
    private static final String BATCH_ID = "URIB012406";
    
    // テーブル名
    private static final String TABLE_NAME = "Fレコード履歴カットデータ";

    // ＤＢシステム日時
    String DBServerTime = FiResorceUtility.getDBServerTime();

    /**
     * Ｆレコード履歴カットデータ作成
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx ps = null;
        int cnt = 0;
        int intI = 1;
        try {
            invoker.infoLog(strUserID + "　：　" + TABLE_NAME + "の追加を開始します。");
            ps = invoker.getDataBase().prepareStatement(getVatSkipRirekiCreateSql());
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, DBServerTime);
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, DBServerTime);
            cnt = ps.executeUpdate();
            invoker.infoLog(strUserID + "　：　" + cnt + "件の"+ TABLE_NAME + "を追加しました。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    /**
     * VATスキップ履歴作成（Fレコード）SQLを取得する
     *
     * @return VATスキップ履歴作成（Fレコード）SQL
     */
    private String getVatSkipRirekiCreateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT /*+ APPEND */");
        sql.append("INTO DT_POS_F_CUT_RIREKI( ");
        sql.append("  COMMAND");
        sql.append("  , STORE");
        sql.append("  , POS");
        sql.append("  , TRANS_NO");
        sql.append("  , CASHIER_ID");
        sql.append("  , FORMAT");
        sql.append("  , SNI_INV_FORM");
        sql.append("  , SIN_INV_SERIAL");
        sql.append("  , SIN_INV_NO");
        sql.append("  , SNI_REFUND_INV_FORM");
        sql.append("  , SIN_REFUND_INV_SERIAL");
        sql.append("  , SIN_REF_INV_NO");
        sql.append("  , SNI_CUST_NAME");
        sql.append("  , SNI_CUST_COMPANY");
        sql.append("  , SNI_CUST_ADDRS");
        sql.append("  , SNI_CUST_TAX_CODE");
        sql.append("  , SNI_INV_FORM2");
        sql.append("  , SIN_INV_SERIAL2");
        sql.append("  , SIN_INV_NO2");
        sql.append("  , SIN_INV_SKIP_KIND");
        sql.append("  , SIN_INV_SKIP_REASON");
        sql.append("  , SNI_INV_ISSUED_CASHERID");
        sql.append("  , EIGYO_DT");
        sql.append("  , DATA_SAKUSEI_DT");
        sql.append("  , ERR_KB");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS");
        sql.append(") ");
        sql.append("SELECT");
        sql.append("  FF1.COMMAND");
        sql.append("  , FF1.STORE");
        sql.append("  , FF1.POS");
        sql.append("  , FF1.TRANS_NO");
        sql.append("  , FF1.CASHIER_ID");
        sql.append("  , FF1.FORMAT");
        sql.append("  , FF1.SNI_INV_FORM");
        sql.append("  , FF1.SIN_INV_SERIAL");
        sql.append("  , FF1.SIN_INV_NO");
        sql.append("  , FF1.SNI_REFUND_INV_FORM");
        sql.append("  , FF1.SIN_REFUND_INV_SERIAL");
        sql.append("  , FF1.SIN_REF_INV_NO");
        sql.append("  , FF1.SNI_CUST_NAME");
        sql.append("  , FF1.SNI_CUST_COMPANY");
        sql.append("  , FF1.SNI_CUST_ADDRS");
        sql.append("  , FF1.SNI_CUST_TAX_CODE");
        sql.append("  , FF1.SNI_INV_FORM2");
        sql.append("  , FF1.SIN_INV_SERIAL2");
        sql.append("  , FF1.SIN_INV_NO2");
        sql.append("  , FF1.SIN_INV_SKIP_KIND");
        sql.append("  , FF1.SIN_INV_SKIP_REASON");
        sql.append("  , FF1.SNI_INV_ISSUED_CASHERID");
        sql.append("  , FF1.EIGYO_DT");
        sql.append("  , FF1.DATA_SAKUSEI_DT");
        sql.append("  , FF1.ERR_KB");
        sql.append("  , ? AS INSERT_USER_ID");
        sql.append("  , ? AS INSERT_TS");
        sql.append("  , ? AS UPDATE_USER_ID");
        sql.append("  , ? AS UPDATE_TS");
        sql.append(" FROM");
        sql.append("  ( ");
        sql.append("    SELECT");
        sql.append("      COMMAND");
        sql.append("      , STORE");
        sql.append("      , POS");
        sql.append("      , TRANS_NO");
        sql.append("      , EIGYO_DT");
        sql.append("      , SUBSTR(VAT, 1, 1) AS RANK1");
        sql.append("      , SUBSTR(VAT, 2, 15) AS VAT1");
        sql.append("      , SUBSTR(VAT, 17, 10) AS VAT2");
        sql.append("      , SUBSTR(VAT, 27, 16) AS VAT3");
        sql.append("      , CASE ");
        sql.append("        WHEN SUBSTR(VAT, 1, 1) = '1' ");
        sql.append("        THEN '1' ");
        sql.append("        WHEN SUBSTR(VAT, 1, 1) = '5' ");
        sql.append("        THEN '1' ");
        sql.append("        WHEN SUBSTR(VAT, 1, 1) = '9' ");
        sql.append("        THEN '9' ");
        sql.append("        ELSE '0' ");
        sql.append("        END AS SKIP_KB ");
        sql.append("    FROM");
        sql.append("      ( ");
        sql.append("        SELECT");
        sql.append("          COMMAND");
        sql.append("          , STORE");
        sql.append("          , POS");
        sql.append("          , TRANS_NO");
        sql.append("          , EIGYO_DT");
        sql.append("          , MAX( ");
        sql.append("            RANK1 || SNI_INV_FORM || SIN_INV_SERIAL || SIN_INV_NO");
        sql.append("          ) VAT ");
        sql.append("        FROM");
        sql.append("          ( ");
        sql.append("            SELECT");
        sql.append("              COMMAND");
        sql.append("              , STORE");
        sql.append("              , POS");
        sql.append("              , TRANS_NO");
        sql.append("              , EIGYO_DT");
        sql.append("              , SNI_INV_FORM");
        sql.append("              , SIN_INV_SERIAL");
        sql.append("              , SIN_INV_NO");
        sql.append("              , (SKIP_1 * 10) + SKIP_9 AS SKIP_19");
        sql.append("              , CASE ");
        sql.append("                WHEN (SKIP_1 > 0 AND SKIP_9 > 0) ");
        sql.append("                THEN '5' ");
        sql.append("                WHEN (SKIP_1 > 0 AND SKIP_9 = 0) ");
        sql.append("                THEN '1' ");
        sql.append("                WHEN (SKIP_1 = 0 AND SKIP_9 > 0) ");
        sql.append("                THEN '9' ");
        sql.append("                ELSE '0' ");
        sql.append("                END AS RANK1 ");
        sql.append("            FROM");
        sql.append("              ( ");
        sql.append("                SELECT");
        sql.append("                  COMMAND");
        sql.append("                  , STORE");
        sql.append("                  , POS");
        sql.append("                  , TRANS_NO");
        sql.append("                  , EIGYO_DT");
        sql.append("                  , SNI_INV_FORM");
        sql.append("                  , SIN_INV_SERIAL");
        sql.append("                  , SIN_INV_NO");
        sql.append("                  , SUM(SKIP_1) AS SKIP_1");
        sql.append("                  , SUM(SKIP_9) AS SKIP_9 ");
        sql.append("                FROM");
        sql.append("                  ( ");
        sql.append("                    SELECT");
        sql.append("                      COMMAND");
        sql.append("                      , STORE");
        sql.append("                      , POS");
        sql.append("                      , TRANS_NO");
        sql.append("                      , EIGYO_DT");
        sql.append("                      , SNI_INV_FORM");
        sql.append("                      , SIN_INV_SERIAL");
        sql.append("                      , SIN_INV_NO");
        sql.append("                      , 1 AS SKIP_1");
        sql.append("                      , 0 AS SKIP_9 ");
        sql.append("                    FROM");
        sql.append("                      WK_POS_F_INVOICE ");
        sql.append("                    WHERE");
        sql.append("                      SIN_INV_SKIP_KIND = '1' ");
        sql.append("                      AND ERR_KB = '0' ");
        sql.append("                    UNION ALL ");
        sql.append("                    SELECT");
        sql.append("                      COMMAND");
        sql.append("                      , STORE");
        sql.append("                      , POS");
        sql.append("                      , TRANS_NO");
        sql.append("                      , EIGYO_DT");
        sql.append("                      , SNI_INV_FORM");
        sql.append("                      , SIN_INV_SERIAL");
        sql.append("                      , SIN_INV_NO");
        sql.append("                      , 0 AS SKIP_1");
        sql.append("                      , 1 AS SKIP_9 ");
        sql.append("                    FROM");
        sql.append("                      WK_POS_F_INVOICE ");
        sql.append("                    WHERE");
        sql.append("                      SIN_INV_SKIP_KIND = '9' ");
        sql.append("                      AND ERR_KB = '0'");
        sql.append("                  ) ");
        sql.append("                GROUP BY");
        sql.append("                  COMMAND");
        sql.append("                  , STORE");
        sql.append("                  , POS");
        sql.append("                  , TRANS_NO");
        sql.append("                  , EIGYO_DT");
        sql.append("                  , SNI_INV_FORM");
        sql.append("                  , SIN_INV_SERIAL");
        sql.append("                  , SIN_INV_NO");
        sql.append("              )");
        sql.append("          ) ");
        sql.append("        GROUP BY");
        sql.append("          COMMAND");
        sql.append("          , STORE");
        sql.append("          , POS");
        sql.append("          , TRANS_NO");
        sql.append("          , EIGYO_DT");
        sql.append("      )");
        sql.append("  ) LV1 ");
        sql.append("  INNER JOIN WK_POS_F_INVOICE FF1 ");
        sql.append("    ON LV1.COMMAND = FF1.COMMAND ");
        sql.append("    AND LV1.STORE = FF1.STORE ");
        sql.append("    AND LV1.POS = FF1.POS ");
        sql.append("    AND LV1.TRANS_NO = FF1.TRANS_NO ");
        sql.append("    AND LV1.EIGYO_DT = FF1.EIGYO_DT ");
        sql.append("    AND LV1.VAT1 = FF1.SNI_INV_FORM ");
        sql.append("    AND LV1.VAT2 = FF1.SIN_INV_SERIAL ");
        sql.append("    AND LV1.VAT3 = FF1.SIN_INV_NO ");
        sql.append("    AND LV1.SKIP_KB = FF1.SIN_INV_SKIP_KIND");

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
