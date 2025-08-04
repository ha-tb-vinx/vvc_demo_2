package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: VatInvoiceRegistHakkouHuyoDao クラス</p>
 * <p>説明　: INVOICE発行済情報付与</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.07.20) Y.Itaki FIVI対応(#1707)
 * @Version 1.01 (2016.10.11) Y.Itaki FIVI対応(#1816)
 * @Version 1.02 (2016.11.02) Y.Itaki FIVI対応(#2263)
 * @Version 1.03 (2016.11.10) J.Endo  FIVI対応(#2313)
 * @Version 1.04 (2016.12.08) J.Endo  FIVI対応(#3242)
 * @Version 1.05 (2016.12.22) T.Kamei FIVI対応(#3463)
 * @Version 1.06 (2017.01.13) J.Endo  FIVI対応(#3502)
 * @Version 1.07 (2017.02.20) J.Endo  FIVI対応(#4097)
 * @Version 1.08 (2017.04.18) X.Liu   FIVI対応(#4768)
 * @Version 1.09 (2017.05.25) X.Liu   FIVI対応(#5149)
 * @Version 1.10 (2017.06.19) X.Liu   FIVI対応(#5270)
 * @Version 1.11 (2017.07.04) N.Kato  FIVI対応(#5550)
 * @Version 1.12 (2017.07.10) X.Liu   FIVI対応(#5580)
 * @Version 1.13 (2017.07.26) X.Liu   FIVI対応(#5668)
 */
public class VatInvoiceRegistHakouHuyoDao implements DaoIf {

    // 処理名
    private static final String DAO_NAME = "INVOICE発行済情報付与";

    // テーブル名
    private static final String TABLE_NAME_H = "売上INVOICE管理ヘッダデータ";
    private static final String TABLE_NAME_M = "売上INVOICE管理明細データ";

    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();


    // 処理実行
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = userId + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        //

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;


        int insertCount = 0;
        try {
            //サーバー時間
            String dbServerTime = FiResorceUtility.getDBServerTime();


            // 売上INVOICE管理ヘッダデータ
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME_H + "の追加を開始します。");

            // 売上INVOICE管理ヘッダデータに登録
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getDtUriageInvoiceKanriHInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            //#5580 Del X.Liu 2017.07.10 (S)
//            preparedStatementExIns.setString(5, BATCH_DT);
            //#5580 Del X.Liu 2017.07.10 (E)
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TABLE_NAME_H + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME_H + "の追加を終了します。");


            // 売上INVOICE管理明細データ
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME_M + "の追加を開始します。");

            // 売上INVOICE管理明細データに登録
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getDtUriageInvoiceKanriMInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TABLE_NAME_M + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME_M + "の追加を終了します。");

//            //#5270 Add X.Liu 2017.06.19 (S)
//            // ログ出力
//            invoker.infoLog(strUserId + "　：　" + TABLE_NAME_M + "の更新を開始します。");
//            
//            //売上INVOICE管理ヘッダデータを更新
//            preparedStatementExIns = invoker.getDataBase().prepareStatement(getDtUriageInvoiceKanriHUpdateSql());
//            preparedStatementExIns.setString(1, userId);
//            preparedStatementExIns.setString(2, dbServerTime);
//            insertCount = preparedStatementExIns.executeUpdate();
//            
//            // ログ出力
//            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TABLE_NAME_H + "を更新しました。");
//            invoker.infoLog(strUserId + "　：　" + TABLE_NAME_H + "の更新を終了します。");
//            //#5270 Add X.Liu 2017.06.19 (E)

            // APPEND INSERTした内容確定する必要があるのでcommitを行う
            invoker.getDataBase().commit();

            // #5550 Add N.Kato 2017.07.04 (S)
            // ログ出力
            //#5580 Del X.Liu 2017.07.10 (S)
//            invoker.infoLog(strUserId + "　：　" + TABLE_NAME_M + "の更新を開始します。");
//            
//            //売上INVOICE管理ヘッダデータを更新
//            preparedStatementExIns = invoker.getDataBase().prepareStatement(getDtUriageInvoiceKanriHUpdateSql());
//            preparedStatementExIns.setString(1, userId);
//            preparedStatementExIns.setString(2, dbServerTime);
//            insertCount = preparedStatementExIns.executeUpdate();
//            
//            // ログ出力
//            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TABLE_NAME_H + "を更新しました。");
//            invoker.infoLog(strUserId + "　：　" + TABLE_NAME_H + "の更新を終了します。");
            //#5580 Del X.Liu 2017.07.10 (E)
            // #5550 Add N.Kato 2017.07.04 (E)

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
     * 売上INVOICE管理ヘッダーデータ登録用SQLを取得する
     *
     * @return 売上INVOICE管理ヘッダーデータ登録用SQL
     */
    private String getDtUriageInvoiceKanriHInsertSql() {
        StringBuilder sql = new StringBuilder();

        //2016.12.08 J.Endo FIVI対応(#3242) MOD(S)
        //sql.append("INSERT /*+ APPEND */ INTO DT_URIAGE_INVOICE_KANRI_H( ");
        //sql.append("    COMMAND_CD ");
        //sql.append("    ,TENPO_CD ");
        //sql.append("    ,REGI_RB ");
        //sql.append("    ,TERMINAL_RB ");
        //sql.append("    ,EIGYO_DT ");
        //sql.append("    ,SALES_TS ");
        //sql.append("    ,ZEINUKI_VL ");
        //sql.append("    ,TAX_VL ");
        //sql.append("    ,ZEIKOMI_VL ");
        //sql.append("    ,MEMBER_ID ");
        //sql.append("    ,INVOICE_NB ");
        //sql.append("    ,INVOICE_KB ");
        //sql.append("    ,INVOICE_RB ");
        //sql.append("    ,OLD_TENPO_CD ");
        //sql.append("    ,TENPO_NA ");
        //sql.append("    ,CASHIER_CD ");
        //sql.append("    ,USER_NA ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) ADD(S)
        //sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        //sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) ADD(E)
        //sql.append("    ,HAKOU_DT ");
        //sql.append("    ,INSERT_USER_ID ");
        //sql.append("    ,INSERT_TS ");
        //sql.append("    ,UPDATE_USER_ID ");
        //sql.append("    ,UPDATE_TS ");
        //sql.append("    ,DELETE_FG ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) ADD(S)
        //sql.append("    ,KOJIN_NA ");
        //sql.append("    ,KONYU_SYA ");
        //sql.append("    ,KAISYA_ZEI_CD ");
        //sql.append("    ,JYUSYO ");
        //sql.append("    ,SHIHARAI_HOUHOU ");
        //sql.append("    ,KOZA_NO ");
        //sql.append("    ,ZEINUKI_TOT_16IKOU_VL ");
        //sql.append("    ,RECEIPT_DISCOUNT_TOT_VL ");
        //sql.append("    ,VAT_5_VL ");
        //sql.append("    ,VAT_10_VL ");
        //sql.append("    ,HIKAZEI_TOT_VL ");
        //sql.append("    ,ZEIKOMI_TOT_0_VL ");
        //sql.append("    ,ZEIKOMI_TOT_5_VL ");
        //sql.append("    ,ZEIKOMI_TOT_10_VL ");
        //sql.append("    ,VAT_PRINT_KB ");
        //sql.append("    ,UPDATE_FG ");
        //sql.append("    ,CREDIT_NA ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) ADD(E)
        ////2016.11.10 J.Endo FIVI対応(#2313) ADD(S)
        //sql.append("    ,INVOICE_AUTO_NB ");
        //sql.append("    ,INVOICE_AUTO_KB ");
        //sql.append("    ,INVOICE_AUTO_RB ");
        //sql.append("    ,VAT_AUTO_DT ");
        ////2016.11.10 J.Endo FIVI対応(#2313) ADD(E)
        //sql.append(") ");
        //sql.append("SELECT ");
        //sql.append("    H_WK.COMMAND_CD ");
        //sql.append("    ,H_WK.TENPO_CD ");
        //sql.append("    ,H_WK.REGI_RB ");
        //sql.append("    ,H_WK.TERMINAL_RB ");
        //sql.append("    ,H_WK.EIGYO_DT ");
        //sql.append("    ,H_WK.SALES_TS ");
        ////2016.11.10 J.Endo FIVI対応(#2313) MOD(S)
        ////sql.append("    ,H_WK.ZEINUKI_VL ");
        ////sql.append("    ,H_WK.TAX_VL ");
        ////sql.append("    ,H_WK.ZEIKOMI_VL ");
        //sql.append("    ,NVL(H_WK.ZEINUKI_VL, 0) ");
        //sql.append("    ,NVL(H_WK.TAX_VL, 0) ");
        //sql.append("    ,NVL(H_WK.ZEIKOMI_VL, 0) ");
        ////2016.11.10 J.Endo FIVI対応(#2313) MOD(E)
        //sql.append("    ,H_WK.MEMBER_ID ");
        ////sql.append("    ,CASE WHEN H_WK.COMMAND_CD = '0043' THEN TRIM( TPFI.SNI_INV_FORM ) ELSE TRIM( TPFI.SNI_REFUND_INV_FORM ) END AS INVOICE_NB ");
        ////sql.append("    ,CASE WHEN H_WK.COMMAND_CD = '0043' THEN TRIM( TPFI.SIN_INV_NO ) ELSE TRIM( TPFI.SIN_REF_INV_NO ) END AS INVOICE_KB ");
        ////sql.append("    ,CASE WHEN H_WK.COMMAND_CD = '0043' THEN TRIM( TPFI.SIN_INV_SERIAL ) ELSE TRIM( TPFI.SIN_REFUND_INV_SERIAL ) END AS INVOICE_RB ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) MOD(S)
        ////sql.append("    ,CASE WHEN POS_F.SNI_INV_FORM IS NULL THEN POS_F.SNI_REFUND_INV_FORM ELSE POS_F.SNI_INV_FORM END AS INVOICE_NB ");
        ////sql.append("    ,CASE WHEN POS_F.SNI_INV_FORM IS NULL THEN POS_F.SIN_REFUND_INV_SERIAL ELSE POS_F.SIN_INV_SERIAL END AS INVOICE_KB ");
        ////sql.append("    ,CASE WHEN POS_F.SNI_INV_FORM IS NULL THEN POS_F.SIN_REF_INV_NO ELSE POS_F.SIN_INV_NO END AS INVOICE_RB ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) MOD(E)
        ////2016.10.11 Y.Itaki FIVI対応(#2263) MOD(S)
        //sql.append("    ,CASE WHEN POS_F.SNI_INV_FORM IS NOT NULL THEN POS_F.SNI_INV_FORM END AS INVOICE_NB ");
        //sql.append("    ,CASE WHEN POS_F.SNI_INV_FORM IS NOT NULL THEN POS_F.SIN_INV_SERIAL END AS INVOICE_KB ");
        //sql.append("    ,CASE WHEN POS_F.SNI_INV_FORM IS NOT NULL THEN POS_F.SIN_INV_NO END AS INVOICE_RB ");
        ////2016.10.11 Y.Itaki FIVI対応(#2263) MOD(E)
        //sql.append("    ,H_WK.OLD_TENPO_CD ");
        //sql.append("    ,H_WK.TENPO_NA ");
        //sql.append("    ,H_WK.CASHIER_CD ");
        //sql.append("    ,H_WK.USER_NA ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) MOD(S)
        ////sql.append("    ,CASE ");
        ////sql.append("          WHEN LENGTH( TRIM ( TPFI.SIN_INV_SERIAL )) > '0' THEN H_WK.HAKOU_DT ");
        ////sql.append("          WHEN LENGTH( TRIM ( TPFI.SIN_REFUND_INV_SERIAL )) > '0' THEN H_WK.HAKOU_DT ");
        ////sql.append("          ELSE '' END AS HAKOU_DT ");
        ////sql.append("    ,H_WK.INSERT_USER_ID ");
        ////sql.append("    ,H_WK.INSERT_TS ");
        ////sql.append("    ,H_WK.UPDATE_USER_ID ");
        ////sql.append("    ,H_WK.UPDATE_TS ");
        //sql.append("    ,H_WK.SHIHARAI_SYUBETSU_CD ");
        //sql.append("    ,H_WK.SHIHARAI_SYUBETSU_SUB_CD ");
        //sql.append("    ,SUBSTR(POS_F.EIGYO_DT,1,8) AS HAKOU_DT ");
        //sql.append("    ,? AS INSERT_USER_ID ");
        //sql.append("    ,? AS INSERT_TS ");
        //sql.append("    ,? AS UPDATE_USER_ID ");
        //sql.append("    ,? AS UPDATE_TS ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) MOD(E)
        //sql.append("    ,H_WK.DELETE_FG ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) ADD(S)
        //sql.append("    ,SUBSTR(POS_F.SNI_CUST_NAME,1,60) AS KOJIN_NA ");
        //sql.append("    ,SUBSTR(POS_F.SNI_CUST_COMPANY,1,120) AS KONYU_SYA ");
        //sql.append("    ,SUBSTRB(POS_F.SNI_CUST_TAX_CODE,1,10) AS KAISYA_ZEI_CD ");
        //sql.append("    ,SUBSTR(POS_F.SNI_CUST_ADDRS,1,90) AS JYUSYO ");
        //sql.append("    ,H_WK.SHIHARAI_HOUHOU ");
        //sql.append("    ,H_WK.KOZA_NO ");
        ////2016.11.10 J.Endo FIVI対応(#2313) MOD(S)
        ////sql.append("    ,H_WK.ZEINUKI_TOT_16IKOU_VL ");
        ////sql.append("    ,H_WK.RECEIPT_DISCOUNT_TOT_VL ");
        ////sql.append("    ,H_WK.VAT_5_VL ");
        ////sql.append("    ,H_WK.VAT_10_VL ");
        ////sql.append("    ,H_WK.HIKAZEI_TOT_VL ");
        ////sql.append("    ,H_WK.ZEIKOMI_TOT_0_VL ");
        ////sql.append("    ,H_WK.ZEIKOMI_TOT_5_VL ");
        ////sql.append("    ,H_WK.ZEIKOMI_TOT_10_VL ");
        //sql.append("    ,NVL(H_WK.ZEINUKI_TOT_16IKOU_VL, 0) ");
        //sql.append("    ,NVL(H_WK.RECEIPT_DISCOUNT_TOT_VL, 0) ");
        //sql.append("    ,NVL(H_WK.VAT_5_VL, 0) ");
        //sql.append("    ,NVL(H_WK.VAT_10_VL, 0) ");
        //sql.append("    ,NVL(H_WK.HIKAZEI_TOT_VL, 0) ");
        //sql.append("    ,NVL(H_WK.ZEIKOMI_TOT_0_VL, 0) ");
        //sql.append("    ,NVL(H_WK.ZEIKOMI_TOT_5_VL, 0) ");
        //sql.append("    ,NVL(H_WK.ZEIKOMI_TOT_10_VL, 0) ");
        ////2016.11.10 J.Endo FIVI対応(#2313) MOD(E)
        //sql.append("    ,'3' "); // 3(店舗発行)
        //sql.append("    ,'1' "); // 1(更新済)
        //sql.append("    ,H_WK.CREDIT_NA ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) ADD(E)
        ////2016.11.10 J.Endo FIVI対応(#2313) ADD(S)
        //sql.append("    ,POS_F.SNI_INV_FORM2 ");
        //sql.append("    ,POS_F.SIN_INV_SERIAL2 ");
        //sql.append("    ,POS_F.SIN_INV_NO2 ");
        //sql.append("    ,SUBSTR(POS_F.EIGYO_DT,1,8) ");
        ////2016.11.10 J.Endo FIVI対応(#2313) ADD(E)
        //sql.append("FROM ");
        //sql.append("    WK_URIAGE_INVOICE_KANRI_H H_WK ");
        //sql.append("LEFT OUTER JOIN ");
        //sql.append("    DT_POS_F_INVOICE POS_F ");
        //sql.append("ON  H_WK.COMMAND_CD = POS_F.COMMAND ");
        //sql.append("AND H_WK.TENPO_CD = '00'||POS_F.STORE ");
        //sql.append("AND H_WK.REGI_RB = POS_F.POS ");
        //sql.append("AND H_WK.TERMINAL_RB = POS_F.TRANS_NO ");
        //sql.append("AND H_WK.EIGYO_DT = SUBSTR(POS_F.EIGYO_DT,1,8) ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) MOD(S)
        ////sql.append("WHERE ");
        ////sql.append("    H_WK.EIGYO_DT = ? ");
        //sql.append("AND POS_F.DATA_SAKUSEI_DT = ? ");
        ////2016.10.11 Y.Itaki FIVI対応(#1816) MOD(E)
        sql.append("INSERT /*+ APPEND */ INTO DT_URIAGE_INVOICE_KANRI_H ( ");
        sql.append("    COMMAND_CD ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,REGI_RB ");
        sql.append("   ,TERMINAL_RB ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,SALES_TS ");
        sql.append("   ,ZEINUKI_VL ");
        sql.append("   ,TAX_VL ");
        sql.append("   ,ZEIKOMI_VL ");
        sql.append("   ,MEMBER_ID ");
        sql.append("   ,INVOICE_NB ");
        sql.append("   ,INVOICE_KB ");
        sql.append("   ,INVOICE_RB ");
        sql.append("   ,OLD_TENPO_CD ");
        sql.append("   ,TENPO_NA ");
        sql.append("   ,CASHIER_CD ");
        sql.append("   ,USER_NA ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,HAKOU_DT ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("   ,DELETE_FG ");
        sql.append("   ,KOJIN_NA ");
        sql.append("   ,KONYU_SYA ");
        sql.append("   ,KAISYA_ZEI_CD ");
        sql.append("   ,JYUSYO ");
        sql.append("   ,SHIHARAI_HOUHOU ");
        sql.append("   ,KOZA_NO ");
        sql.append("   ,ZEINUKI_TOT_16IKOU_VL ");
        sql.append("   ,RECEIPT_DISCOUNT_TOT_VL ");
        sql.append("   ,VAT_5_VL ");
        sql.append("   ,VAT_10_VL ");
        sql.append("   ,HIKAZEI_TOT_VL ");
        sql.append("   ,ZEIKOMI_TOT_0_VL ");
        sql.append("   ,ZEIKOMI_TOT_5_VL ");
        sql.append("   ,ZEIKOMI_TOT_10_VL ");
        sql.append("   ,VAT_PRINT_KB ");
        sql.append("   ,UPDATE_FG ");
        sql.append("   ,CREDIT_NA ");
        sql.append("   ,INVOICE_AUTO_NB ");
        sql.append("   ,INVOICE_AUTO_KB ");
        sql.append("   ,INVOICE_AUTO_RB ");
        sql.append("   ,VAT_AUTO_DT ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(S)
        sql.append("   ,RECEIPT_NO ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("   ,RECEIPT_SUB_NO ");
        sql.append("   ,MIHAKO_KB ");
        //#4768 Add X.Liu 2017.04.18 (E)
        //#5149 Add X.Liu 2017.05.25 (S)
        sql.append("   ,ZEIMU_MEISAI_QT ");
        sql.append("   ,ZEIMU_ZEINUKI_VL ");
        sql.append("   ,ZEIMU_ZEIKOMI_VL ");
        //#5149 Add X.Liu 2017.05.25 (E)
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("   ,VAT_PRINT_USER_ID ");
        sql.append("   ,VAT_PRINT_USER_NA ");
        sql.append("   ,VAT_CANCEL_USER_ID ");
        sql.append("   ,VAT_CANCEL_USER_NA ");
        //#5580 Add X.Liu 2017.07.10 (E)
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append("   ,BETSU_SYS_DATA_KB ");
        sql.append("   ,BETSU_SYS_VAT_PRINT_KB ");
        //#5668 Add X.Liu 2017.07.26 (E)
        sql.append(") ");
        sql.append("SELECT ");
        sql.append("    WUIK.COMMAND_CD ");
        sql.append("   ,WUIK.TENPO_CD ");
        sql.append("   ,WUIK.REGI_RB ");
        sql.append("   ,WUIK.TERMINAL_RB ");
        sql.append("   ,WUIK.EIGYO_DT ");
        sql.append("   ,WUIK.SALES_TS ");
        sql.append("   ,NVL(WUIK.ZEINUKI_VL, 0) ");
        sql.append("   ,NVL(WUIK.TAX_VL, 0) ");
        sql.append("   ,NVL(WUIK.ZEIKOMI_VL, 0) ");
        sql.append("   ,WUIK.MEMBER_ID ");
        //#5580 Mod X.Liu 2017.07.10 (S)
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.INVOICE_NB ELSE DPFI.SNI_INV_FORM   END AS INVOICE_NB ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.INVOICE_KB ELSE DPFI.SIN_INV_SERIAL END AS INVOICE_KB ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.INVOICE_RB ELSE DPFI.SIN_INV_NO     END AS INVOICE_RB ");
        sql.append("   ,WUIK.INVOICE_NB ");
        sql.append("   ,WUIK.INVOICE_KB ");
        sql.append("   ,WUIK.INVOICE_RB ");
        //#5580 Mod X.Liu 2017.07.10 (E)
        sql.append("   ,WUIK.OLD_TENPO_CD ");
        sql.append("   ,WUIK.TENPO_NA ");
        sql.append("   ,WUIK.CASHIER_CD ");
        sql.append("   ,WUIK.USER_NA ");
        sql.append("   ,WUIK.SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,WUIK.SHIHARAI_SYUBETSU_SUB_CD ");
        //#5580 Mod X.Liu 2017.07.10 (S)
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.HAKOU_DT ELSE SUBSTR(DPFI.EIGYO_DT,1,8) END AS HAKOU_DT ");
        sql.append("   ,WUIK.HAKOU_DT ");
        //#5580 Mod X.Liu 2017.07.10 (E)
        sql.append("   ,? AS INSERT_USER_ID ");
        sql.append("   ,? AS INSERT_TS ");
        sql.append("   ,? AS UPDATE_USER_ID ");
        sql.append("   ,? AS UPDATE_TS ");
        sql.append("   ,WUIK.DELETE_FG ");
        //#5580 Mod X.Liu 2017.07.10 (S)
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.KOJIN_NA      ELSE SUBSTR(DPFI.SNI_CUST_NAME,1,60)      END AS KOJIN_NA ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.KONYU_SYA     ELSE SUBSTR(DPFI.SNI_CUST_COMPANY,1,120)  END AS KONYU_SYA ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.KAISYA_ZEI_CD ELSE SUBSTRB(DPFI.SNI_CUST_TAX_CODE,1,10) END AS KAISYA_ZEI_CD ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.JYUSYO        ELSE SUBSTR(DPFI.SNI_CUST_ADDRS,1,90)     END AS JYUSYO ");
        sql.append("   ,WUIK.KOJIN_NA ");
        sql.append("   ,WUIK.KONYU_SYA ");
        sql.append("   ,WUIK.KAISYA_ZEI_CD ");
        sql.append("   ,WUIK.JYUSYO ");
        //#5580 Mod X.Liu 2017.07.10 (E)
        sql.append("   ,WUIK.SHIHARAI_HOUHOU ");
        sql.append("   ,WUIK.KOZA_NO ");
        sql.append("   ,NVL(WUIK.ZEINUKI_TOT_16IKOU_VL, 0) ");
        sql.append("   ,NVL(WUIK.RECEIPT_DISCOUNT_TOT_VL, 0) ");
        sql.append("   ,NVL(WUIK.VAT_5_VL, 0) ");
        sql.append("   ,NVL(WUIK.VAT_10_VL, 0) ");
        sql.append("   ,NVL(WUIK.HIKAZEI_TOT_VL, 0) ");
        sql.append("   ,NVL(WUIK.ZEIKOMI_TOT_0_VL, 0) ");
        sql.append("   ,NVL(WUIK.ZEIKOMI_TOT_5_VL, 0) ");
        sql.append("   ,NVL(WUIK.ZEIKOMI_TOT_10_VL, 0) ");
        //#5580 Mod X.Liu 2017.07.10 (S)
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.VAT_PRINT_KB ELSE '3' END ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.UPDATE_FG    ELSE '1' END ");
        sql.append("   ,WUIK.VAT_PRINT_KB ");
        sql.append("   ,WUIK.UPDATE_FG ");
        //#5580 Mod X.Liu 2017.07.10 (E)
        sql.append("   ,WUIK.CREDIT_NA ");
        //#5580 Mod X.Liu 2017.07.10 (S)
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.INVOICE_AUTO_NB ELSE DPFI.SNI_INV_FORM2        END AS INVOICE_AUTO_NB ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.INVOICE_AUTO_KB ELSE DPFI.SIN_INV_SERIAL2      END AS INVOICE_AUTO_KB ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.INVOICE_AUTO_RB ELSE DPFI.SIN_INV_NO2          END AS INVOICE_AUTO_RB ");
//        sql.append("   ,CASE WHEN DPFI.COMMAND IS NULL THEN WUIK.VAT_AUTO_DT     ELSE SUBSTR(DPFI.EIGYO_DT,1,8) END AS VAT_AUTO_DT ");
        sql.append("   ,WUIK.INVOICE_AUTO_NB ");
        sql.append("   ,WUIK.INVOICE_AUTO_KB ");
        sql.append("   ,WUIK.INVOICE_AUTO_RB ");
        sql.append("   ,WUIK.VAT_AUTO_DT ");
        //#5580 Mod X.Liu 2017.07.10 (E)
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(S)
        sql.append("   ,RECEIPT_NO ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("   ,RECEIPT_SUB_NO ");
        sql.append("   ,MIHAKO_KB ");
        //#4768 Add X.Liu 2017.04.18 (E)
        //#5149 Add X.Liu 2017.05.25 (S)
        sql.append("   ,WUIK.ZEIMU_MEISAI_QT ");
        sql.append("   ,WUIK.ZEIMU_ZEINUKI_VL ");
        sql.append("   ,WUIK.ZEIMU_ZEIKOMI_VL ");
        //#5149 Add X.Liu 2017.05.25 (E)
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("   ,NULL AS VAT_PRINT_USER_ID ");
        sql.append("   ,NULL AS VAT_PRINT_USER_NA ");
        sql.append("   ,NULL AS VAT_CANCEL_USER_ID ");
        sql.append("   ,NULL AS VAT_CANCEL_USER_NA ");
        //#5580 Add X.Liu 2017.07.10 (E)
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append("   ,WUIK.BETSU_SYS_DATA_KB ");
        sql.append("   ,WUIK.BETSU_SYS_VAT_PRINT_KB ");
        //#5668 Add X.Liu 2017.07.26 (E)
        sql.append("FROM ");
        sql.append("    WK_URIAGE_INVOICE_KANRI_H WUIK ");
        //#5580 Del X.Liu 2017.07.10 (S)
//        sql.append("LEFT OUTER JOIN ");
//        sql.append("    DT_POS_F_INVOICE DPFI ");
//        sql.append("ON  WUIK.COMMAND_CD = DPFI.COMMAND ");
//        sql.append("AND WUIK.TENPO_CD = '00'||DPFI.STORE ");
//        sql.append("AND WUIK.REGI_RB = DPFI.POS ");
//        sql.append("AND WUIK.TERMINAL_RB = DPFI.TRANS_NO ");
//        sql.append("AND WUIK.EIGYO_DT = SUBSTR(DPFI.EIGYO_DT,1,8) ");
//        sql.append("AND DPFI.DATA_SAKUSEI_DT = ? ");
        //#5580 Del X.Liu 2017.07.10 (E)
        //2016.12.08 J.Endo FIVI対応(#3242) MOD(E)

        return sql.toString();
    }



    /**
     * 売上INVOICE管理明細データ登録用SQLを取得する
     *
     * @return 売上INVOICE管理明細データ登録用SQL
     */
    private String getDtUriageInvoiceKanriMInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_URIAGE_INVOICE_KANRI_M( ");
        sql.append("    COMMAND_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_RB ");
        sql.append("    ,TERMINAL_RB ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,SEQ_RB ");
        sql.append("    ,SALES_TYPE ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,SURYO_QT ");
        sql.append("    ,BAITANKA_VL ");
        sql.append("    ,ZEINUKI_VL ");
        sql.append("    ,TAX_RT ");
        sql.append("    ,ZEIGAKU_VL ");
        sql.append("    ,ZEIKOMI_VL ");
        sql.append("    ,OLD_SYOHIN_CD ");
        sql.append("    ,SYOHIN_NA ");
        sql.append("    ,HANBAI_TANI_TX ");
        sql.append("    ,GENTANKA_VL ");
        sql.append("    ,ZEI_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,DELETE_FG ");
        sql.append("    ,HANBAI_WEIGHT_QT ");
        //2016.11.10 J.Endo FIVI対応(#2313) ADD(S)
        sql.append("    ,HAMPER_ITEM_CD ");
        sql.append("    ,NEBIKI_VATIN_VL ");
        sql.append("    ,REG_SELL ");
        sql.append("    ,REG_SELL_WOT ");
        //2016.11.10 J.Endo FIVI対応(#2313) ADD(E)
        //2017.01.13 J.Endo FIVI対応(#3502) ADD(S)
        sql.append("    ,TEIKAN_KB ");
        //2017.01.13 J.Endo FIVI対応(#3502) ADD(E)
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(S)
        sql.append("    ,RECEIPT_NO ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("    ,RECEIPT_SUB_NO ");
        sql.append("    ,VAT_PRINT_KB ");
        sql.append("    ,SONEBIKIGO_ZEIKOMI_VL ");
        sql.append("    ,SONEBIKIGO_ZEINUKI_VL ");
        //#4768 Add X.Liu 2017.04.18 (E)
        sql.append(") ");
        sql.append("SELECT ");
        sql.append("    COMMAND_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_RB ");
        sql.append("    ,TERMINAL_RB ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,SEQ_RB ");
        sql.append("    ,SALES_TYPE ");
        sql.append("    ,SYOHIN_CD ");
        //2016.11.10 J.Endo FIVI対応(#2313) MOD(S)
        //sql.append("    ,SURYO_QT ");
        //sql.append("    ,BAITANKA_VL ");
        //sql.append("    ,ZEINUKI_VL ");
        //sql.append("    ,TAX_RT ");
        //sql.append("    ,ZEIGAKU_VL ");
        //sql.append("    ,ZEIKOMI_VL ");
        sql.append("    ,NVL(SURYO_QT, 0) ");
        sql.append("    ,NVL(BAITANKA_VL, 0) ");
        sql.append("    ,NVL(ZEINUKI_VL, 0) ");
        sql.append("    ,NVL(TAX_RT, 0) ");
        sql.append("    ,NVL(ZEIGAKU_VL, 0) ");
        sql.append("    ,NVL(ZEIKOMI_VL, 0) ");
        //2016.11.10 J.Endo FIVI対応(#2313) MOD(E)
        sql.append("    ,OLD_SYOHIN_CD ");
        sql.append("    ,SYOHIN_NA ");
        sql.append("    ,HANBAI_TANI_TX ");
        //2016.12.22 T.Kamei FIVI対応(#3463) MOD(S)
        //sql.append("    ,GENTANKA_VL ");
        //sql.append("    ,ZEI_KB ");
        sql.append("    ,NVL(GENTANKA_VL, 0) ");
        sql.append("    ,NVL(ZEI_KB, '0') ");
        //2016.12.22 T.Kamei FIVI対応(#3463) MOD(E)
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,DELETE_FG ");
        //2016.11.10 J.Endo FIVI対応(#2313) MOD(S)
        //sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,NVL(HANBAI_WEIGHT_QT, 0) ");
        //2016.11.10 J.Endo FIVI対応(#2313) MOD(E)
        //2016.11.10 J.Endo FIVI対応(#2313) ADD(S)
        sql.append("    ,HAMPER_ITEM_CD ");
        sql.append("    ,NVL(NEBIKI_VATIN_VL, 0) ");
        sql.append("    ,NVL(REG_SELL, 0) ");
        sql.append("    ,NVL(REG_SELL_WOT, 0) ");
        //2016.11.10 J.Endo FIVI対応(#2313) ADD(E)
        //2017.01.13 J.Endo FIVI対応(#3502) ADD(S)
        sql.append("    ,TEIKAN_KB ");
        //2017.01.13 J.Endo FIVI対応(#3502) ADD(E)
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(S)
        sql.append("    ,RECEIPT_NO ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("    ,RECEIPT_SUB_NO ");
        sql.append("    ,VAT_PRINT_KB ");
        sql.append("    ,SONEBIKIGO_ZEIKOMI_VL ");
        sql.append("    ,SONEBIKIGO_ZEINUKI_VL ");
        //#4768 Add X.Liu 2017.04.18 (E)
        sql.append("FROM ");
        sql.append("    WK_URIAGE_INVOICE_KANRI_M ");

        return sql.toString();
    }

    //#5270 Add X.Liu 2017.06.19 (S)
    //#5580 Del X.Liu 2017.07.10 (S)
//    /**
//     * 売上INVOICE管理ヘッダデータ更新用SQLを取得する
//     *
//     * @return 売上INVOICE管理ヘッダデータ更新用SQL
//     */
//    private String getDtUriageInvoiceKanriHUpdateSql(){
//        StringBuffer sql = new StringBuffer();
//        
//        sql.append("UPDATE DT_URIAGE_INVOICE_KANRI_H DUIKH ");
//        sql.append("SET");
//        sql.append("  ( ");
//        sql.append("    INVOICE_NB");
//        sql.append("    , INVOICE_KB");
//        sql.append("    , INVOICE_RB");
//        sql.append("    , HAKOU_DT");
//        sql.append("    , KOJIN_NA");
//        sql.append("    , KONYU_SYA");
//        sql.append("    , KAISYA_ZEI_CD");
//        sql.append("    , JYUSYO");
//        sql.append("    , SHIHARAI_HOUHOU");
//        sql.append("    , KOZA_NO");
//        sql.append("    , INVOICE_AUTO_NB");
//        sql.append("    , INVOICE_AUTO_KB");
//        sql.append("    , INVOICE_AUTO_RB");
//        sql.append("    , VAT_AUTO_DT");
//        sql.append("    , UPDATE_USER_ID");
//        sql.append("    , UPDATE_TS");
//        sql.append("  ) = ( ");
//        sql.append("    SELECT");
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
//        sql.append("    ,? AS UPDATE_USER_ID    ");
//        sql.append("    ,? AS UPDATE_TS ");
//        sql.append("    FROM");
//        sql.append("      WK_OROSHI_SYUKA_DENPYO_CANCEL WOSDC ");
//        sql.append("    WHERE");
//        sql.append("      DUIKH.COMMAND_CD = '0000' ");
//        sql.append("      AND DUIKH.TENPO_CD = WOSDC.SYUKA_TENPO_CD ");
//        sql.append("      AND DUIKH.REGI_RB = SUBSTR(WOSDC.DENPYO_NO,1,3) ");
//        sql.append("      AND DUIKH.TERMINAL_RB = SUBSTR(WOSDC.DENPYO_NO,4) ");
//        sql.append("      AND DUIKH.EIGYO_DT = WOSDC.SYUKA_DT ");
//        sql.append("      AND DUIKH.RECEIPT_SUB_NO = 0 ");
//        sql.append("  ) ");
//        sql.append("WHERE");
//        sql.append("  EXISTS ( ");
//        sql.append("    SELECT");
//        sql.append("      1 FROM WK_OROSHI_SYUKA_DENPYO_CANCEL WOSDC ");
//        sql.append("    WHERE");
//        sql.append("      DUIKH.COMMAND_CD = '0000' ");
//        sql.append("      AND DUIKH.TENPO_CD = WOSDC.SYUKA_TENPO_CD ");
//        sql.append("      AND DUIKH.REGI_RB = SUBSTR(WOSDC.DENPYO_NO,1,3) ");
//        sql.append("      AND DUIKH.TERMINAL_RB = SUBSTR(WOSDC.DENPYO_NO,4) ");
//        sql.append("      AND DUIKH.EIGYO_DT = WOSDC.SYUKA_DT ");
//        sql.append("      AND DUIKH.RECEIPT_SUB_NO = 0 ");
//        sql.append("  ) ");
//        
//        return sql.toString();
//    }
    //#5580 Del X.Liu 2017.07.10 (E)
    //#5270 Add X.Liu 2017.06.19 (E)

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
