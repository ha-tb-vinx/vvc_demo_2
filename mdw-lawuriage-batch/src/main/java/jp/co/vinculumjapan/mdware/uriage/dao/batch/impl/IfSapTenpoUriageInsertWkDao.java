package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import org.apache.commons.lang.StringUtils;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>
 * タイトル：IfSapTenpoUriageInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 店舗売上 IFファイル出力処理
 * </p>
 * <p>
 * 著作権：Copyright (c) 2016
 * </p>
 * <p>
 * 会社名：VINX
 * </p>
 * 
 * @author VINX
 * @version 1.00 (2016.11.4) FIVIMART対応
 */
public class IfSapTenpoUriageInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810020";
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 店舗売上 ワーク作成処理";
    
    private String startDt = "";
    
    private String creditShiharaiSyubetsuCd = "";
    
    private String voucherShiharaiSyubetsuCreditNa = "";
    
    /** 送信時の対象計上日開始日（SAP結合機→SAP本番機への切替用） */
    private static final String SAP_SEND_KEIJO_DT_START_DT = FiResorceUtility.getPropertie("SAP_SEND_KEIJO_DT_START_DT");

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 店舗売上 ワーク作成処理を開始します。");

        PreparedStatementEx ps = null;
        ResultSet rs = null;
        
        ps = invoker.getDataBase().prepareStatement(selectMishimeDt());
        rs = ps.executeQuery();
        if (rs.next()) {
            startDt = rs.getString("START_DT");
        }
        
        ps = null;
        rs = null;
        ps = invoker.getDataBase().prepareStatement(selectCreditShiharaiSyubetsuCd());
        rs = ps.executeQuery();
        while (rs.next()) {
            creditShiharaiSyubetsuCd = creditShiharaiSyubetsuCd + "'" + rs.getString("SHIHARAI_SYUBETSU_CD") + "'," ;
        }
        creditShiharaiSyubetsuCd = StringUtils.substringBeforeLast(creditShiharaiSyubetsuCd, ",");
        
        ps = null;
        rs = null;
        ps = invoker.getDataBase().prepareStatement(selectVoucherShiharaiSyubetsuNa());
        rs = ps.executeQuery();
        if (rs.next()) {
            voucherShiharaiSyubetsuCreditNa = rs.getString("SHIHARAI_SYUBETSU_VN_NA");
        }
        
        invoker.infoLog(strUserId + "　：　前回のワークを削除します。");
        ps = null;
        rs = null;
        ps = invoker.getDataBase().prepareStatement(truncateWk_base());
        rs = ps.executeQuery();
        ps = null;
        rs = null;
        ps = invoker.getDataBase().prepareStatement(truncateWk());
        rs = ps.executeQuery();
        invoker.infoLog(strUserId + "　：　前回のワークを削除しました。");
        
        invoker.infoLog(strUserId + "　：　POS Cレコード 累積データ作成を開始します。");
        ps = null;
        rs = null;
        ps = invoker.getDataBase().prepareStatement(mergeWk_posC());
        rs = ps.executeQuery();
        invoker.infoLog(strUserId + "　：　POS Cレコード 累積データ作成を終了します。");

        invoker.infoLog(strUserId + "　：　売上基本 ワークOVC以外分作成を作成します。");
        ps = null;
        rs = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_base());
        rs = ps.executeQuery();
        invoker.infoLog(strUserId + "　：　売上基本 ワークOVC以外分作成を終了します。");

        invoker.infoLog(strUserId + "　：　売上基本 ワークOVC分作成を開始します。");
        rs = null;
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_voucher_base());
        rs = ps.executeQuery();
        invoker.infoLog(strUserId + "　：　売上基本 ワークOVC分作成を終了します。");
        
        invoker.infoLog(strUserId + "　：　店舗売上 ワークCCP以外作成を開始します。");
        rs = null;
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_tenUriage());
        rs = ps.executeQuery();
        invoker.infoLog(strUserId + "　：　店舗売上 ワークCCP以外分作成を終了します。");
        
        invoker.infoLog(strUserId + "　：　店舗売上 ワークCCP分作成を開始します。");
        rs = null;
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_tenUriageCCP());
        rs = ps.executeQuery();
        invoker.infoLog(strUserId + "　：　店舗売上 ワークCCP分作成を終了します。");
        
        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 店舗売上 ワーク作成処理を終了します。");

    }
    
    private String selectMishimeDt() {
        StringBuffer sb = new StringBuffer();
        //本締されていない会計年月の開始日を取得しておく
        //（処理時間考慮のため、主テーブルとの結合はさせず、AP側に保持する）
        sb.append(" SELECT ");
        sb.append("   MIN(START_DT) AS START_DT ");
        sb.append(" FROM ");
        sb.append("   R_CALENDAR  ");
        sb.append(" WHERE "); 
        sb.append("   HONZIMESYORI_KB < '2' ");
        return sb.toString();
    }
    
    private String selectCreditShiharaiSyubetsuCd() {
        //支払種別マスタのクレジット分にあたる支払種別コードを取得しておく
        //（処理時間考慮のため、主テーブルとの結合はさせず、AP側に保持する）
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   TRIM(SHIHARAI_SYUBETSU_CD) AS SHIHARAI_SYUBETSU_CD");
        sb.append(" FROM ");
        sb.append("   R_PAYMENT  ");
        sb.append(" WHERE "); 
        sb.append("   SHIHARAI_SYUBETSU_GROUP_CD = '02' ");
        return sb.toString();
    }
    
    private String selectVoucherShiharaiSyubetsuNa() {
        //支払種別マスタのOVC分にあたる支払種別名を1件のみ取得しておく。SEQは初期移行の最小値。
        //（処理時間考慮のため、主テーブルとの結合はさせず、AP側に保持する）
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   TRIM(RP.SHIHARAI_SYUBETSU_VN_NA) AS SHIHARAI_SYUBETSU_VN_NA");
        sb.append(" FROM ");
        sb.append("   R_PAYMENT RP ");
        sb.append(" WHERE "); 
        sb.append("   RP.SHIHARAI_SYUBETSU_CD = 'OVC' ");
        sb.append("   AND RP.SHIHARAI_SYUBETSU_SUB_CD = 'OVC0000' ");
        sb.append("   AND RP.SHIHARAI_SYUBETSU_SEQ = ");
        sb.append("   ( SELECT  ");
        sb.append("       MIN(RP2.SHIHARAI_SYUBETSU_SEQ)  ");
        sb.append("     FROM ");
        sb.append("       R_PAYMENT RP2 ");
        sb.append("     WHERE  ");
        sb.append("       RP.SHIHARAI_SYUBETSU_CD = RP2.SHIHARAI_SYUBETSU_CD  ");
        sb.append("       AND RP.SHIHARAI_SYUBETSU_SUB_CD = RP2.SHIHARAI_SYUBETSU_SUB_CD  ");
        sb.append("   )  ");
        return sb.toString();
    }
    
    private String mergeWk_posC(){
        StringBuffer sb = new StringBuffer();
        sb.append(" MERGE /*+ ORDERED USE_NL(DPCP DSPCPR) INDEX(DSPCPR IDX1_DT_SAP_POS_C_PAYMENT_RUI)*/  ");
        sb.append(" INTO DT_SAP_POS_C_PAYMENT_RUI DSPCPR  ");
        sb.append("   USING (  ");
        sb.append("     SELECT DISTINCT ");
        sb.append("       STORE ");
        sb.append("       , POS ");
        sb.append("       , TRANS_NO ");
        sb.append("       , CTYPE ");
        sb.append("       , PYMT_TYPE ");
        sb.append("       , PYMT_TYPE2 ");
        sb.append("       , CREDIT_CARD_NUMBER ");
        sb.append("       , MERCHANT_CODE ");
        sb.append("       , TERMINAL_ID ");
        sb.append("       , TRACE_NO ");
        sb.append("       , EIGYO_DT ");
        sb.append("     FROM ");
        sb.append("       DT_POS_C_PAYMENT DPCP ");
        sb.append("     WHERE ");
        sb.append("       DPCP.EIGYO_DT >= '" + startDt + "' ");
        sb.append("   ) DPCP  ");
        sb.append("     ON (  ");
        sb.append("       DSPCPR.STORE = DPCP.STORE  ");
        sb.append("       AND DSPCPR.POS = DPCP.POS  ");
        sb.append("       AND DSPCPR.TRANS_NO = DPCP.TRANS_NO  ");
        sb.append("       AND DSPCPR.CTYPE = DPCP.CTYPE  ");
        sb.append("       AND DSPCPR.PYMT_TYPE = DPCP.PYMT_TYPE  ");
        sb.append("       AND DSPCPR.PYMT_TYPE2 = DPCP.PYMT_TYPE2  ");
        sb.append("       AND NVL(DSPCPR.CREDIT_CARD_NUMBER,' ') = NVL(DPCP.CREDIT_CARD_NUMBER,' ') ");
        sb.append("       AND DSPCPR.EIGYO_DT = DPCP.EIGYO_DT ");
        sb.append("     ) WHEN NOT MATCHED THEN  ");
        sb.append(" INSERT (  ");
        sb.append("   SEQ_NB ");
        sb.append("   , STORE ");
        sb.append("   , POS ");
        sb.append("   , TRANS_NO ");
        sb.append("   , CTYPE ");
        sb.append("   , PYMT_TYPE ");
        sb.append("   , PYMT_TYPE2 ");
        sb.append("   , CREDIT_CARD_NUMBER ");
        sb.append("   , MERCHANT_CODE ");
        sb.append("   , TERMINAL_ID ");
        sb.append("   , TRACE_NO ");
        sb.append("   , EIGYO_DT ");
        sb.append(" )  ");
        sb.append(" VALUES (  ");
        sb.append("   SEQ_DT_SAP_POS_C_PAYMENT_RUI.NEXTVAL ");
        sb.append("   , DPCP.STORE ");
        sb.append("   , DPCP.POS ");
        sb.append("   , DPCP.TRANS_NO ");
        sb.append("   , DPCP.CTYPE ");
        sb.append("   , DPCP.PYMT_TYPE ");
        sb.append("   , DPCP.PYMT_TYPE2 ");
        sb.append("   , DPCP.CREDIT_CARD_NUMBER ");
        sb.append("   , DPCP.MERCHANT_CODE ");
        sb.append("   , DPCP.TERMINAL_ID ");
        sb.append("   , DPCP.TRACE_NO ");
        sb.append("   , DPCP.EIGYO_DT ");
        sb.append(" ) ");
        return sb.toString();
    }

    private String insertWK_base() {

        //バウチャー以外
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_URIAGE_BASE(  ");
        sb.append("   KEIJO_DT ");
        sb.append("   , TENPO_CD ");
        sb.append("   , REGISTER_NO ");
        sb.append("   , TRANSACTION_NO ");
        sb.append("   , SHIHARAI_SYUBETSU_CD ");
        sb.append("   , SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , CREDIT_NO ");
        sb.append("   , DATE_EXPIRY ");
        sb.append("   , PYMT_AMT ");
        sb.append("   , SHONIN_NO ");
        sb.append("   , POS_VL ");
        sb.append("   , TEISEIGO_VL ");
        sb.append("   , SAGAKU_VL ");
        sb.append("   , TEISEIGO_CREDIT_NO ");
        sb.append("   , TEISEIGO_SHONIN_NO ");
        sb.append("   , CHECKER_CD ");
        sb.append("   , OFFLINE_V_KB ");
        sb.append("   , KINSYU_VL ");
        sb.append("   , ACTUAL_AMT ");
        sb.append("   , SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , CREDIT_TERMINAL_ID ");
        sb.append("   , TRACE_NO ");
        sb.append("   , MERCHANT_CODE ");
        sb.append("   , POINT_ISSUDE_DT ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   /*+ ORDERED USE_NL(DTRS DZSS DSPCPR) INDEX(DZSS PK_DT_TEN_SEISAN_STATE) INDEX(DSPCPR IDX1_DT_SAP_POS_C_PAYMENT_RUI) */ ");
        sb.append("   DISTINCT ");
        sb.append("   DTRS.KEIJO_DT ");
        sb.append("   , DTRS.TENPO_CD ");
        sb.append("   , DTRS.REGISTER_NO ");
        sb.append("   , DTRS.TRANSACTION_NO ");
        sb.append("   , DTRS.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , DTRS.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , DTRS.CREDIT_NO ");
        sb.append("   , '99999999' AS DATE_EXPIRY ");
        sb.append("   , 0 AS PYMT_AMT ");
        sb.append("   , NVL(DTRS.TEISEIGO_SHONIN_NO,DTRS.SHONIN_NO) ");
        sb.append("   , DTRS.POS_VL ");
        sb.append("   , DTRS.TEISEIGO_VL ");
        sb.append("   , DTRS.SAGAKU_VL ");
        sb.append("   , DTRS.TEISEIGO_CREDIT_NO ");
        sb.append("   , DTRS.TEISEIGO_SHONIN_NO ");
        sb.append("   , DTRS.CHECKER_CD ");
        sb.append("   , DTRS.OFFLINE_V_KB ");
        sb.append("   , DTRS.KINSYU_VL ");
        sb.append("   , 0 AS ACTUAL_AMT ");
        sb.append("   , NULL AS SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , DSPCPR.TERMINAL_ID ");
        sb.append("   , DSPCPR.TRACE_NO ");
        sb.append("   , DSPCPR.MERCHANT_CODE  ");
        sb.append("   , NVL(DTRS.POINT_ISSUDE_DT,' ') ");
        sb.append(" FROM ");
        sb.append("   DT_TEN_RECEIPT_SEISAN DTRS  ");
        sb.append("   INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS  ");
        sb.append("     ON DTRS.COMP_CD = DZSS.COMP_CD  ");
        sb.append("     AND DTRS.KEIJO_DT = DZSS.KEIJO_DT  ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2'  ");
        sb.append("   INNER JOIN DT_SAP_POS_C_PAYMENT_RUI DSPCPR  ");
        sb.append("     ON DTRS.KEIJO_DT = DSPCPR.EIGYO_DT  ");
        sb.append("     AND SUBSTR(DTRS.TENPO_CD, 3, 4) = DSPCPR.STORE  ");
        sb.append("     AND DTRS.REGISTER_NO = DSPCPR.POS  ");
        sb.append("     AND DTRS.TRANSACTION_NO = DSPCPR.TRANS_NO  ");
        sb.append("     AND DTRS.SHIHARAI_SYUBETSU_CD = DSPCPR.PYMT_TYPE  ");
        sb.append("     AND RPAD(DTRS.SHIHARAI_SYUBETSU_SUB_CD,7,' ') = RPAD(DSPCPR.PYMT_TYPE2,7,' ')  ");
        sb.append("     AND NVL(TRIM(DTRS.CREDIT_NO),' ') = NVL(TRIM(DSPCPR.CREDIT_CARD_NUMBER),' ')  ");
        sb.append("     AND DSPCPR.CTYPE = '0012' ");
        sb.append(" WHERE ");
        sb.append("   DTRS.COMP_CD = '" + COMP_CD + "'  ");
        sb.append("   AND DTRS.KEIJO_DT >= '" + startDt + "'  ");
        sb.append("   AND DTRS.KEIJO_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");
        sb.append("   AND DTRS.SHIHARAI_SYUBETSU_CD NOT IN ('MRS', 'OVC', 'OFF' )  ");

        return sb.toString();
    }

    private String insertWK_voucher_base() {

        //バウチャー分
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_URIAGE_BASE(  ");
        sb.append("   KEIJO_DT ");
        sb.append("   , TENPO_CD ");
        sb.append("   , REGISTER_NO ");
        sb.append("   , TRANSACTION_NO ");
        sb.append("   , SHIHARAI_SYUBETSU_CD ");
        sb.append("   , SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , CREDIT_NO ");
        sb.append("   , DATE_EXPIRY ");
        sb.append("   , PYMT_AMT ");
        sb.append("   , SHONIN_NO ");
        sb.append("   , POS_VL ");
        sb.append("   , TEISEIGO_VL ");
        sb.append("   , SAGAKU_VL ");
        sb.append("   , TEISEIGO_CREDIT_NO ");
        sb.append("   , TEISEIGO_SHONIN_NO ");
        sb.append("   , CHECKER_CD ");
        sb.append("   , OFFLINE_V_KB ");
        sb.append("   , KINSYU_VL ");
        sb.append("   , ACTUAL_AMT ");
        sb.append("   , SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , CREDIT_TERMINAL_ID ");
        sb.append("   , TRACE_NO ");
        sb.append("   , MERCHANT_CODE ");
        sb.append("   , POINT_ISSUDE_DT ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   DVJ.KEIJO_DT ");
        sb.append("   , DVJ.TENPO_CD ");
        sb.append("   , DVJ.REGISTER_NO ");
        sb.append("   , DVJ.TRANSACTION_NO ");
        sb.append("   , DVJ.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , DVJ.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , DVJ.CREDIT_NO ");
        sb.append("   , NVL(DVJ.V_EXPIRY_TS, '        ') AS DATE_EXPIRY ");
        sb.append("   , NVL(DVJ.KINSYU_VL, 0) AS PYMT_AMT ");
        sb.append("   , NULL AS SHONIN_NO ");
        sb.append("   , SUM(NVL(DVJ.SHIHARAI_VL, 0)) AS POS_VL ");
        sb.append("   , 0 AS TEISEIGO_VL ");
        sb.append("   , 0 AS SAGAKU_VL ");
        sb.append("   , NULL AS TEISEIGO_CREDIT_NO ");
        sb.append("   , NULL AS TEISEIGO_SHONIN_NO ");
        sb.append("   , NULL AS CHECKER_CD ");
        sb.append("   , NULL AS OFFLINE_V_KB ");
        sb.append("   , 0 AS KINSYU_VL ");
        sb.append("   , 0 AS ACTUAL_AMT ");
        sb.append("   , '" + voucherShiharaiSyubetsuCreditNa + "' AS SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , NULL AS CREDIT_TERMINAL_ID ");
        sb.append("   , NULL AS TRACE_NO ");
        sb.append("   , NULL AS MERCHANT_CODE  ");
        sb.append("   , ' ' AS POINT_ISSUDE_DT  ");
        sb.append(" FROM ");
        sb.append("   DT_VOUCHER_JISEKI DVJ  ");
        sb.append("   INNER JOIN DT_TEN_SEISAN_STATE DZSS  ");
        sb.append("     ON DVJ.COMP_CD = DZSS.COMP_CD  ");
        sb.append("     AND DVJ.KEIJO_DT = DZSS.KEIJO_DT  ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2'  ");
        sb.append(" WHERE ");
        sb.append("   DVJ.COMP_CD = '" + COMP_CD + "'  ");
        sb.append("   AND DVJ.KEIJO_DT >= '" + startDt + "' "); 
        sb.append("   AND DVJ.KEIJO_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");
        sb.append(" GROUP BY ");
        sb.append("   DVJ.KEIJO_DT ");
        sb.append("   , DVJ.TENPO_CD ");
        sb.append("   , DVJ.REGISTER_NO ");
        sb.append("   , DVJ.TRANSACTION_NO ");
        sb.append("   , DVJ.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , DVJ.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , DVJ.CREDIT_NO ");
        sb.append("   , NVL(DVJ.V_EXPIRY_TS, '        ') ");
        sb.append("   , NVL(DVJ.KINSYU_VL, 0) ");

        return sb.toString();
    }
    
    private String insertWK_tenUriage() {

        //CCP以外
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_TEN_URIAGE(  ");
        sb.append("   KEIJO_DT ");
        sb.append("   , TENPO_CD ");
        sb.append("   , REGISTER_NO ");
        sb.append("   , TRANSACTION_NO ");
        sb.append("   , SHIHARAI_SYUBETSU_CD ");
        sb.append("   , SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , DATE_EXPIRY ");
        sb.append("   , PYMT_AMT ");
        sb.append("   , POS_VL ");
        sb.append("   , SAGAKU_VL ");
        sb.append("   , ACTUAL_AMT ");
        sb.append("   , SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , POINT_ISSUDE_DT ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSUB.KEIJO_DT ");
        sb.append("   , WSUB.TENPO_CD ");
        sb.append("   , WSUB.REGISTER_NO ");
        sb.append("   , WSUB.TRANSACTION_NO ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , ' ' AS SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSUB.DATE_EXPIRY ");
        sb.append("   , WSUB.PYMT_AMT ");
        sb.append("   , SUM(WSUB.POS_VL) ");
        sb.append("   , SUM(WSUB.SAGAKU_VL) ");
        sb.append("   , SUM(WSUB.ACTUAL_AMT) ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_VN_NA  ");
        sb.append("   , WSUB.POINT_ISSUDE_DT ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_URIAGE_BASE WSUB  ");
        sb.append(" WHERE ");
        sb.append("   WSUB.SHIHARAI_SYUBETSU_CD NOT IN ( 'CCP', " + creditShiharaiSyubetsuCd +")  ");
        sb.append(" GROUP BY ");
        sb.append("   WSUB.KEIJO_DT ");
        sb.append("   , WSUB.TENPO_CD ");
        sb.append("   , WSUB.REGISTER_NO ");
        sb.append("   , WSUB.TRANSACTION_NO ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSUB.DATE_EXPIRY ");
        sb.append("   , WSUB.PYMT_AMT ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_VN_NA "); 
        sb.append("   , WSUB.POINT_ISSUDE_DT ");

        return sb.toString();
    }
    
    private String insertWK_tenUriageCCP() {

        //CCP
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_TEN_URIAGE(  ");
        sb.append("   KEIJO_DT ");
        sb.append("   , TENPO_CD ");
        sb.append("   , REGISTER_NO ");
        sb.append("   , TRANSACTION_NO ");
        sb.append("   , SHIHARAI_SYUBETSU_CD ");
        sb.append("   , SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , DATE_EXPIRY ");
        sb.append("   , PYMT_AMT ");
        sb.append("   , POS_VL ");
        sb.append("   , SAGAKU_VL ");
        sb.append("   , ACTUAL_AMT ");
        sb.append("   , SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , POINT_ISSUDE_DT ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSUB.KEIJO_DT ");
        sb.append("   , WSUB.TENPO_CD ");
        sb.append("   , WSUB.REGISTER_NO ");
        sb.append("   , WSUB.TRANSACTION_NO ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSUB.DATE_EXPIRY ");
        sb.append("   , WSUB.PYMT_AMT ");
        sb.append("   , SUM(WSUB.POS_VL) ");
        sb.append("   , SUM(WSUB.SAGAKU_VL) ");
        sb.append("   , SUM(WSUB.ACTUAL_AMT) ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_VN_NA  ");
        sb.append("   , WSUB.POINT_ISSUDE_DT ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_URIAGE_BASE WSUB  ");
        sb.append(" WHERE ");
        sb.append("   WSUB.SHIHARAI_SYUBETSU_CD = 'CCP'  ");
        sb.append(" GROUP BY ");
        sb.append("   WSUB.KEIJO_DT ");
        sb.append("   , WSUB.TENPO_CD ");
        sb.append("   , WSUB.REGISTER_NO ");
        sb.append("   , WSUB.TRANSACTION_NO ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSUB.DATE_EXPIRY ");
        sb.append("   , WSUB.PYMT_AMT ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_VN_NA "); 
        sb.append("   , WSUB.POINT_ISSUDE_DT ");

        return sb.toString();
    }

    private String truncateWk_base() {
        StringBuffer sb = new StringBuffer();

        sb.append(" TRUNCATE TABLE WK_SAP_URIAGE_BASE ");

        return sb.toString();
    }
    
    private String truncateWk() {
        StringBuffer sb = new StringBuffer();

        sb.append(" TRUNCATE TABLE WK_SAP_TEN_URIAGE ");

        return sb.toString();
    }

    @Override
    public Object getOutputObject() throws Exception {
        return null;
    }

    @Override
    public void setInputObject(Object arg0) throws Exception {

    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new IfSapTenpoUriageInsertWkDao();
            new StandAloneDaoInvoker("MD").InvokeDao(dao);
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}