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
 * <p>タイトル: IssueCancelUpdateDao クラス</p>
 * <p>説明:INVOICE発行／取消情報更新</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: VINX</p>
 *
 * @author VINX
 * @Version 1.00 (2017.07.10) X.Liu FIVI対応(#5580)
 * @Version 1.01 (2017.07.26) X.Liu FIVI対応(#5668)
 * @Version 1.02 (2017.08.03) X.Liu FIVI対応(#5754)
 * @see なし
 */
public class IssueCancelUpdateDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "INVOICE発行／取消情報更新";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // バッチID
    private static final String BATCH_ID = "URIB012407";

    // ＤＢシステム日時
    String DBServerTime = FiResorceUtility.getDBServerTime();
    
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    private static final String TABLE_NAME_H = "売上INVOICE管理ヘッダデータ";
    
    /**
     * INVOICE発行／取消情報更新
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
            invoker.infoLog(strUserID + "　：　" + TABLE_NAME_H +"の更新を開始します。");
            ps = invoker.getDataBase().prepareStatement(getDtUriageInvoiceKanriHUpdateSql1());
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, DBServerTime);
            ps.setString(intI++, BATCH_DT);
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, DBServerTime);
            ps.setString(intI++, BATCH_DT);
            ps.setString(intI++, BATCH_DT);
            cnt = ps.executeUpdate();
            ps.close();
            invoker.infoLog(strUserID + "　：　" + cnt + "件の" + TABLE_NAME_H + "を更新しました。");

            invoker.infoLog(strUserID + "　：　" + TABLE_NAME_H +"の更新を開始します。");
            intI = 1;
            ps = invoker.getDataBase().prepareStatement(getDtUriageInvoiceKanriHUpdateSql2());
            ps.setString(intI++, BATCH_ID);
            ps.setString(intI++, DBServerTime);
            cnt = ps.executeUpdate();
            ps.close();
            invoker.infoLog(strUserID + "　：　" + cnt + "件の" + TABLE_NAME_H + "を更新しました。");
            
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
     * 売上INVOICE管理ヘッダデータ更新用SQL1を取得する
     *
     * @return 売上INVOICE管理ヘッダデータ更新用SQL
     */
    private String getDtUriageInvoiceKanriHUpdateSql1() {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE DT_URIAGE_INVOICE_KANRI_H DUIKH ");
        sql.append("SET");
        sql.append("  ( ");
        sql.append("    INVOICE_NB");
        sql.append("    , INVOICE_KB");
        sql.append("    , INVOICE_RB");
        sql.append("    , HAKOU_DT");
        sql.append("    , KOJIN_NA");
        sql.append("    , KONYU_SYA");
        sql.append("    , KAISYA_ZEI_CD");
        sql.append("    , JYUSYO");
        sql.append("    , SHIHARAI_HOUHOU");
        sql.append("    , KOZA_NO");
        sql.append("    , VAT_PRINT_KB");
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append("    , BETSU_SYS_VAT_PRINT_KB");
        //#5668 Add X.Liu 2017.07.26 (E)
        sql.append("    , UPDATE_FG");
        sql.append("    , INVOICE_AUTO_NB");
        sql.append("    , INVOICE_AUTO_KB");
        sql.append("    , INVOICE_AUTO_RB");
        sql.append("    , VAT_AUTO_DT");
        sql.append("    , VAT_PRINT_USER_ID");
        sql.append("    , VAT_PRINT_USER_NA");
        sql.append("    , VAT_CANCEL_USER_ID");
        sql.append("    , VAT_CANCEL_USER_NA");
        sql.append("    , UPDATE_USER_ID");
        sql.append("    , UPDATE_TS");
        sql.append("  ) = ( ");
        sql.append("    SELECT");
        sql.append("      DPCR.SNI_INV_FORM AS INVOICE_NB");
        sql.append("      , DPCR.SIN_INV_SERIAL AS INVOICE_KB");
        sql.append("      , DPCR.SIN_INV_NO AS INVOICE_RB");
        sql.append("      , SUBSTR(DPCR.EIGYO_DT, 1, 8) AS HAKOU_DT");
        sql.append("      , SUBSTR(DPCR.SNI_CUST_NAME, 1, 180) AS KOJIN_NA");
        sql.append("      , DPCR.SNI_CUST_COMPANY AS KONYU_SYA");
        sql.append("      , DPCR.SNI_CUST_TAX_CODE AS KAISYA_ZEI_CD");
        sql.append("      , SUBSTR(DPCR.SNI_CUST_ADDRS, 1, 330) AS JYUSYO");
        sql.append("      , NULL AS SHIHARAI_HOUHOU");
        sql.append("      , NULL AS KOZA_NO");
        //#5668 Mod X.Liu 2017.07.26(S)
//        sql.append("      , CASE ");
//        sql.append("        WHEN DPCR.SIN_INV_SKIP_KIND = '9' ");
//        sql.append("        AND DUIKH.VAT_PRINT_KB = '0' ");
//        sql.append("        THEN '3' ");
//        sql.append("        ELSE DUIKH.VAT_PRINT_KB ");
//        sql.append("        END VAT_PRINT_KB");
        sql.append("      , CASE ");
        sql.append("        WHEN DPCR.SIN_INV_SKIP_KIND = '9' ");
        sql.append("        THEN '9' ");
        sql.append("        ELSE '0' ");
        sql.append("        END AS VAT_PRINT_KB");
        sql.append("      , CASE ");
        sql.append("        WHEN DPCR.SIN_INV_SKIP_KIND = '9' ");
        sql.append("        THEN '1' ");
        sql.append("        ELSE '2' ");
        sql.append("        END AS BETSU_SYS_VAT_PRINT_KB");
        //#5668 Mod X.Liu 2017.07.26(E)
        sql.append("      , '1' AS UPDATE_FG");
        sql.append("      , NULL AS INVOICE_AUTO_NB");
        sql.append("      , NULL AS INVOICE_AUTO_KB");
        sql.append("      , NULL AS INVOICE_AUTO_RB");
        sql.append("      , NULL AS VAT_AUTO_DT");
        sql.append("      , DPCR.SNI_INV_ISSUED_CASHERID AS VAT_PRINT_USER_ID");
        sql.append("      , RPU.USER_NA AS VAT_PRINT_USER_NA");
        sql.append("      , NULL AS VAT_CANCEL_USER_ID");
        sql.append("      , NULL AS VAT_CANCEL_USER_NA");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS ");
        sql.append("    FROM");
        sql.append("      DT_POS_F_CUT_RIREKI DPCR ");
        sql.append("      LEFT OUTER JOIN R_PORTAL_USER RPU ");
        sql.append("        ON TRIM(RPU.USER_ID) = TRIM(DPCR.SNI_INV_ISSUED_CASHERID) ");
        sql.append("    WHERE");
        sql.append("      DUIKH.COMMAND_CD = DPCR.COMMAND ");
        sql.append("      AND DUIKH.TENPO_CD = LPAD(DPCR.STORE, 6, '0') ");
        sql.append("      AND DUIKH.REGI_RB = DPCR.POS ");
        sql.append("      AND DUIKH.TERMINAL_RB = DPCR.TRANS_NO ");
        sql.append("      AND DUIKH.EIGYO_DT = SUBSTR(DPCR.EIGYO_DT, 1, 8) ");
        sql.append("      AND DPCR.DATA_SAKUSEI_DT = ? ");
        sql.append("      AND DPCR.SIN_INV_SKIP_KIND = '9' ");
        sql.append("    UNION ALL ");
        sql.append("    SELECT");
        sql.append("      Null AS INVOICE_NB");
        sql.append("      , Null AS INVOICE_KB");
        sql.append("      , Null AS INVOICE_RB");
        sql.append("      , Null AS HAKOU_DT");
        sql.append("      , Null AS KOJIN_NA");
        sql.append("      , Null AS KONYU_SYA");
        sql.append("      , Null AS KAISYA_ZEI_CD");
        sql.append("      , Null AS JYUSYO");
        sql.append("      , NULL AS SHIHARAI_HOUHOU");
        sql.append("      , NULL AS KOZA_NO");
        //#5668 Mod X.Liu 2017.07.26 (S)
//        sql.append("      , CASE ");
//        sql.append("        WHEN DPCR.SIN_INV_SKIP_KIND = '1' ");
//        sql.append("        AND DUIKH.VAT_PRINT_KB = '3' ");
//        sql.append("        THEN '0' ");
//        sql.append("        ELSE DUIKH.VAT_PRINT_KB ");
//        sql.append("        END VAT_PRINT_KB");
        sql.append("      , CASE ");
        sql.append("        WHEN DPCR.SIN_INV_SKIP_KIND = '9' ");
        sql.append("        THEN '9' ");
        sql.append("        ELSE '0' ");
        sql.append("        END AS VAT_PRINT_KB");
        sql.append("      , CASE ");
        sql.append("        WHEN DPCR.SIN_INV_SKIP_KIND = '9' ");
        sql.append("        THEN '1' ");
        sql.append("        ELSE '2' ");
        sql.append("        END AS BETSU_SYS_VAT_PRINT_KB");
        //#5668 Mod X.Liu 2017.07.26 (E)
        sql.append("      , '1' AS UPDATE_FG");
        sql.append("      , NULL AS INVOICE_AUTO_NB");
        sql.append("      , NULL AS INVOICE_AUTO_KB");
        sql.append("      , NULL AS INVOICE_AUTO_RB");
        sql.append("      , NULL AS VAT_AUTO_DT");
        sql.append("      , DPCR.SNI_INV_ISSUED_CASHERID AS VAT_PRINT_USER_ID");
        sql.append("      , RPU1.USER_NA AS VAT_PRINT_USER_NA");
        sql.append("      , DPCR.CASHIER_ID AS VAT_CANCEL_USER_ID");
        sql.append("      , RPU2.USER_NA AS VAT_CANCEL_USER_NA");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS ");
        sql.append("    FROM");
        sql.append("      DT_POS_F_CUT_RIREKI DPCR ");
        sql.append("      LEFT OUTER JOIN R_PORTAL_USER RPU1 ");
        sql.append("        ON TRIM(RPU1.USER_ID) = TRIM(DPCR.SNI_INV_ISSUED_CASHERID) ");
        sql.append("      LEFT OUTER JOIN R_PORTAL_USER RPU2 ");
        sql.append("        ON TRIM(RPU2.USER_ID) = TRIM(DPCR.CASHIER_ID) ");
        sql.append("    WHERE");
        sql.append("      DUIKH.COMMAND_CD = DPCR.COMMAND ");
        sql.append("      AND DUIKH.TENPO_CD = LPAD(DPCR.STORE, 6, '0') ");
        sql.append("      AND DUIKH.REGI_RB = DPCR.POS ");
        sql.append("      AND DUIKH.TERMINAL_RB = DPCR.TRANS_NO ");
        sql.append("      AND DUIKH.EIGYO_DT = SUBSTR(DPCR.EIGYO_DT, 1, 8) ");
        sql.append("      AND DPCR.DATA_SAKUSEI_DT = ? ");
        sql.append("      AND DPCR.SIN_INV_SKIP_KIND = '1'");
        sql.append("  ) ");
        sql.append("WHERE");
        sql.append("  EXISTS ( ");
        sql.append("    SELECT ");
        sql.append("1");
        sql.append("    FROM");
        sql.append("      DT_POS_F_CUT_RIREKI DPCR ");
        sql.append("    WHERE");
        sql.append("      DUIKH.COMMAND_CD = DPCR.COMMAND ");
        sql.append("      AND DUIKH.TENPO_CD = LPAD(DPCR.STORE, 6, '0') ");
        sql.append("      AND DUIKH.REGI_RB = DPCR.POS ");
        sql.append("      AND DUIKH.TERMINAL_RB = DPCR.TRANS_NO ");
        sql.append("      AND DUIKH.EIGYO_DT = SUBSTR(DPCR.EIGYO_DT, 1, 8) ");
        sql.append("      AND DPCR.SIN_INV_SKIP_KIND IN ('1', '9') ");
        sql.append("      AND DPCR.DATA_SAKUSEI_DT = ?");
        sql.append("  ) ");


        return sql.toString();
    }
    
    /**
     * 売上INVOICE管理ヘッダデータ更新用SQL2を取得する
     *
     * @return 売上INVOICE管理ヘッダデータ更新用SQL
     */
    private String getDtUriageInvoiceKanriHUpdateSql2(){
        StringBuffer sql = new StringBuffer();
        
        sql.append("UPDATE DT_URIAGE_INVOICE_KANRI_H DUIKH ");
        sql.append("SET");
        sql.append("  ( ");
        sql.append("    INVOICE_NB");
        sql.append("    , INVOICE_KB");
        sql.append("    , INVOICE_RB");
        sql.append("    , HAKOU_DT");
        sql.append("    , KOJIN_NA");
        sql.append("    , KONYU_SYA");
        sql.append("    , KAISYA_ZEI_CD");
        sql.append("    , JYUSYO");
        sql.append("    , SHIHARAI_HOUHOU");
        sql.append("    , KOZA_NO");
        sql.append("    , INVOICE_AUTO_NB");
        sql.append("    , INVOICE_AUTO_KB");
        sql.append("    , INVOICE_AUTO_RB");
        sql.append("    , VAT_AUTO_DT");
        sql.append("    , UPDATE_USER_ID");
        sql.append("    , UPDATE_TS");
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append("    , VAT_PRINT_KB");
        sql.append("    , BETSU_SYS_VAT_PRINT_KB");
        //#5668 Add X.Liu 2017.07.26 (E)
        sql.append("  ) = ( ");
        sql.append("    SELECT");
        //#5754 Mod X.Liu 2017.08.03 (S)
//        sql.append("     INVOICE_KEYINNO_NB INVOICE_NB  ");
//        sql.append("    ,INVOICE_KEYINNO_KB INVOICE_KB  ");
//        sql.append("    ,INVOICE_KEYINNO_RB INVOICE_RB  ");
//        sql.append("    ,INVOICE_KEYINNO_HAKOU_DT HAKOU_DT  ");
//        sql.append("    ,PERSONAL_NAME KOJIN_NA ");
//        sql.append("    ,CUSTOMER_NAME KONYU_SYA    ");
//        sql.append("    ,VAT_CODE KAISYA_ZEI_CD ");
//        sql.append("    ,ADDRESS JYUSYO ");
//        sql.append("    ,SHIHARAI_HOHO SHIHARAI_HOUHOU  ");
//        sql.append("    ,KOZA_NB KOZA_NO    ");
//        sql.append("    ,INVOICE_AUTONO_NB INVOICE_AUTO_NB  ");
//        sql.append("    ,INVOICE_AUTONO_KB INVOICE_AUTO_KB  ");
//        sql.append("    ,INVOICE_AUTONO_RB INVOICE_AUTO_RB  ");
//        sql.append("    ,INVOICE_AUTONO_HAKOU_DT VAT_AUTO_DT    ");
        sql.append("      WOSDC.INVOICE_KEYINNO_NB AS INVOICE_NB");
        sql.append("      , WOSDC.INVOICE_KEYINNO_KB AS INVOICE_KB");
        sql.append("      , WOSDC.INVOICE_KEYINNO_RB AS INVOICE_RB");
        sql.append("      , WOSDC.INVOICE_KEYINNO_HAKOU_DT AS HAKOU_DT");
        sql.append("      , CASE ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '01' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        THEN WOSDC.PERSONAL_NAME ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '02' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        AND WOSDC.URI_VAT_KB IS NULL ");
        sql.append("        THEN WOSDC.PERSONAL_NAME ");
        sql.append("        ELSE NULL ");
        sql.append("        END AS KOJIN_NA");
        sql.append("      , CASE ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '01' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        THEN WOSDC.CUSTOMER_NAME ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '02' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        AND WOSDC.URI_VAT_KB IS NULL ");
        sql.append("        THEN WOSDC.CUSTOMER_NAME ");
        sql.append("        ELSE NULL ");
        sql.append("        END AS KONYU_SYA");
        sql.append("      , CASE ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '01' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        THEN WOSDC.VAT_CODE ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '02' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        AND WOSDC.URI_VAT_KB IS NULL ");
        sql.append("        THEN WOSDC.VAT_CODE ");
        sql.append("        ELSE NULL ");
        sql.append("        END AS KAISYA_ZEI_CD");
        sql.append("      , CASE ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '01' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        THEN WOSDC.ADDRESS ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '02' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        AND WOSDC.URI_VAT_KB IS NULL ");
        sql.append("        THEN WOSDC.ADDRESS ");
        sql.append("        ELSE NULL ");
        sql.append("        END AS JYUSYO");
        sql.append("      , CASE ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '01' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        THEN WOSDC.SHIHARAI_HOHO ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '02' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        AND WOSDC.URI_VAT_KB IS NULL ");
        sql.append("        THEN WOSDC.SHIHARAI_HOHO ");
        sql.append("        ELSE NULL ");
        sql.append("        END AS SHIHARAI_HOUHOU");
        sql.append("      , CASE ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '01' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        THEN WOSDC.KOZA_NB ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '02' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        AND WOSDC.URI_VAT_KB IS NULL ");
        sql.append("        THEN WOSDC.KOZA_NB ");
        sql.append("        ELSE NULL ");
        sql.append("        END AS KOZA_NO");
        sql.append("      , WOSDC.INVOICE_AUTONO_NB AS INVOICE_AUTO_NB");
        sql.append("      , WOSDC.INVOICE_AUTONO_KB AS INVOICE_AUTO_KB");
        sql.append("      , WOSDC.INVOICE_AUTONO_RB AS INVOICE_AUTO_RB");
        sql.append("      , WOSDC.INVOICE_AUTONO_HAKOU_DT AS VAT_AUTO_DT");
        sql.append("      , ? AS UPDATE_USER_ID    ");
        sql.append("      , ? AS UPDATE_TS ");
        //#5668 Add X.Liu 2017.07.26 (S)
//        sql.append("    ,CASE WHEN WOSDC.DENPYO_PRINT_QT = 0 THEN '0' ");
//        sql.append("     ELSE '9' END AS VAT_PRINT_KB ");
//        sql.append("    ,'2' AS BETSU_SYS_VAT_PRINT_KB    ");
        sql.append("      , CASE ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '01' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        THEN '9' ");
        sql.append("        WHEN WOSDC.DENPYO_KB = '02' ");
        sql.append("        AND WOSDC.DENPYO_PRINT_QT > 0 ");
        sql.append("        AND WOSDC.URI_VAT_KB IS NULL ");
        sql.append("        THEN '9' ");
        sql.append("        ELSE '0' ");
        sql.append("        END AS VAT_PRINT_KB");
        sql.append("      , CASE ");
        sql.append("        WHEN WOSDC.SAIHAKOU_FG = '1' ");
        sql.append("        THEN '2' ");
        sql.append("        ELSE '1' ");
        sql.append("        END AS BETSU_SYS_VAT_PRINT_KB");
        //#5668 Add X.Liu 2017.07.26 (E)
        //#5754 Mod X.Liu 2017.08.03 (E)
        sql.append("    FROM");
        sql.append("      WK_OROSHI_SYUKA_DENPYO_CANCEL WOSDC ");
        sql.append("    WHERE");
        sql.append("      DUIKH.COMMAND_CD = '0000' ");
        sql.append("      AND DUIKH.TENPO_CD = WOSDC.SYUKA_TENPO_CD ");
        sql.append("      AND DUIKH.REGI_RB = SUBSTR(WOSDC.DENPYO_NO,1,3) ");
        sql.append("      AND DUIKH.TERMINAL_RB = SUBSTR(WOSDC.DENPYO_NO,4) ");
        sql.append("      AND DUIKH.EIGYO_DT = WOSDC.SYUKA_DT ");
        sql.append("      AND DUIKH.RECEIPT_SUB_NO = 0 ");
        //#5754 Add X.Liu 2017.08.03 (S)
        sql.append("      AND DUIKH.DELETE_FG = '0' ");
        //#5754 Add X.Liu 2017.08.03 (E)
        sql.append("  ) ");
        sql.append("WHERE");
        sql.append("  EXISTS ( ");
        sql.append("    SELECT");
        sql.append("      1 FROM WK_OROSHI_SYUKA_DENPYO_CANCEL WOSDC ");
        sql.append("    WHERE");
        sql.append("      DUIKH.COMMAND_CD = '0000' ");
        sql.append("      AND DUIKH.TENPO_CD = WOSDC.SYUKA_TENPO_CD ");
        sql.append("      AND DUIKH.REGI_RB = SUBSTR(WOSDC.DENPYO_NO,1,3) ");
        sql.append("      AND DUIKH.TERMINAL_RB = SUBSTR(WOSDC.DENPYO_NO,4) ");
        sql.append("      AND DUIKH.EIGYO_DT = WOSDC.SYUKA_DT ");
        sql.append("      AND DUIKH.RECEIPT_SUB_NO = 0 ");
        //#5754 Add X.Liu 2017.08.03 (S)
        sql.append("      AND DUIKH.DELETE_FG = '0' ");
        //#5754 Add X.Liu 2017.08.03 (E)
        sql.append("  ) ");
        
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
