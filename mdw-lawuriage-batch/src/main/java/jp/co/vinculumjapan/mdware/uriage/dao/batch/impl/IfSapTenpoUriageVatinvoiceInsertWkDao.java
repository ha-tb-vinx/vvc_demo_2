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
 * タイトル：IfSapTenpoUriageVatinvoiceInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 店舗売上VATINVOICE情報 ワーク作成処理
 * </p>
 * <p>
 * 著作権：Copyright (c) 2016
 * </p>
 * <p>
 * 会社名：VINX
 * </p>
 * 
 * @author VINX
 * @version 1.00 (2016.7.4) A.Narita FIVIMART対応
 */
public class IfSapTenpoUriageVatinvoiceInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810050";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 店舗売上VATINVOICE情報 ワーク作成処理";
    /** 送信時の対象計上日開始日（SAP結合機→SAP本番機への切替用） */
    private static final String SAP_SEND_KEIJO_DT_START_DT = FiResorceUtility.getPropertie("SAP_SEND_KEIJO_DT_START_DT");
    
    private String startDt = "";

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 店舗売上VATINVOICE情報 ワーク作成処理を開始します。");

        PreparedStatementEx ps = null;
        ResultSet rs = null;
        
        ps = invoker.getDataBase().prepareStatement(selectMishimeDt());
        rs = ps.executeQuery();
        if (rs.next()) {
            startDt = rs.getString("START_DT");
        }

        ps = null;
        invoker.infoLog(strUserId + "　：　前回のワークを削除します。");
        ps = invoker.getDataBase().prepareStatement(truncateWK_vatKanri());
        ps.executeQuery();
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(truncateWK_vatSkip());
        ps.executeQuery();
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(truncateWK_tenVat());
        ps.executeQuery();
        
        
        invoker.infoLog(strUserId + "　：　前回のワークを削除しました。");

        invoker.infoLog(strUserId + "　：　ワーク作成処理を開始します。");
        
        invoker.infoLog(strUserId + "　：　1.VAT採番情報作成");
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_vatSaiban());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        invoker.infoLog(strUserId + "　：　2.VAT管理情報作成");
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_vatKanri("AN"));
        ps.executeQuery();
        invoker.getDataBase().commit();
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_vatKanri("00"));
        ps.executeQuery();
        invoker.getDataBase().commit();
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_vatKanri("05"));
        ps.executeQuery();
        invoker.getDataBase().commit();
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_vatKanri("10"));
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        invoker.infoLog(strUserId + "　：　3.店舗VAT売上INVOICE情報作成");

        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_tenVat(true));
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK_tenVat(false));
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        
        invoker.infoLog(strUserId + "　：　SAP IF 店舗売上VATINVOICE情報 ワーク作成処理を終了します。");

    }
    
    private String insertWK_vatSaiban() {
        StringBuffer sb = new StringBuffer();
        
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_URI_V_SAIBAN WSUVS(  ");
        sb.append("   NUMBERING_HISTORY_NO ");
        sb.append("   , KEIJO_DT ");
        sb.append("   , TENPO_CD ");
        sb.append("   , INVOICE_AUTO_NB ");
        sb.append("   , INVOICE_AUTO_KB ");
        sb.append("   , INVOICE_AUTO_RB ");
        sb.append("   , INVOICE_KEYIN_NB ");
        sb.append("   , INVOICE_KEYIN_KB ");
        sb.append("   , INVOICE_KEYIN_RB ");
        sb.append("   , DATA_KB ");
        sb.append("   , REASON_KB ");
        sb.append("   , TRAN_KEY ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   /*+ ORDERED USE_NL(NH VSNF) */ ");
        sb.append("   NH.NUMBERING_HISTORY_NO ");
        sb.append("   , NH.RECORDED_DATE AS KEIJO_DT ");
        sb.append("   , VSNF.STORE_CODE AS TENPO_CD ");
        sb.append("   , VNF.TEMPLATE_NUMBER AS INVOICE_AUTO_NB ");
        sb.append("   , VNF.SYMBOL AS INVOICE_AUTO_KB ");
        sb.append("   , NH.AUTO_USED_NUMBER AS INVOICE_AUTO_RB ");
        sb.append("   , NVL(TRIM(NH.KEYIN_TEMPLATE_NUMBER),' ') AS INVOICE_KEYIN_NB ");
        sb.append("   , NVL(TRIM(NH.KEYIN_SYMBOL),' ') AS INVOICE_KEYIN_KB ");
        sb.append("   , NVL(TRIM(NH.KEYIN_USED_NUMBER), ' ') AS INVOICE_KEYIN_RB ");
        sb.append("   , CASE ");
        sb.append("     WHEN VSNF.BUSINESS_TYPE IN ('2','3','6') THEN '01' ");//01:売上
        sb.append("     WHEN VSNF.BUSINESS_TYPE = '7' THEN '02' ");//02:小売返品
        sb.append("     WHEN VSNF.BUSINESS_TYPE = '8' THEN '03' ");//03:自家消費
        sb.append("     WHEN VSNF.BUSINESS_TYPE = '9' THEN '04' ");//04:自家消費返品 ★VAT採番管理上は9/19時点未使用
        sb.append("     WHEN VSNF.BUSINESS_TYPE = '4' THEN '05' ");//05:卸売
        sb.append("     WHEN VSNF.BUSINESS_TYPE = '5' THEN '06' ");//06:卸返品
        sb.append("     ELSE ' ' END AS DATA_KB ");
        sb.append("   , CASE ");
        sb.append("     WHEN TRIM(NH.BUSINESS_VALUE_1) = '9' THEN '0' "); // 元が9:通常 → IF上は0:通常 に変換
        sb.append("     WHEN LENGTH(TRIM(NH.BUSINESS_VALUE_1)) = 1 THEN TRIM(NH.BUSINESS_VALUE_1) "); // 元が20桁だが、IF上は1桁のため、1桁以外は空白に
        sb.append("     ELSE ' ' END AS REASON_KB ");
        sb.append("   , NH.BUSINESS_VALUE_2 AS TRAN_KEY  ");//参考用。POSレシートNo.または卸伝票番号。ただしPOS未発行サマリの場合はNULL
        sb.append(" FROM ");
        sb.append("   NUMBERING_HISTORY NH  ");
        sb.append("   INNER JOIN VAT_STORE_NUMBERING_FRAME VSNF  ");
        sb.append("     ON NH.STORE_NUMBERING_ID = VSNF.STORE_NUMBERING_ID  ");
        sb.append("     AND VSNF.BUSINESS_TYPE <> '1'  ");//仕入返品は除く
        sb.append("   INNER JOIN VAT_NUMBERING_FRAME VNF   ");
        sb.append("     ON VNF.NUMBERING_ID = VSNF.NUMBERING_ID ");
        sb.append(" WHERE ");
        sb.append("   TRIM(NH.RECORDED_DATE) IS NOT NULL  ");
        sb.append("   AND NH.RECORDED_DATE >= '" + startDt + "'  ");
        sb.append("   AND NH.RECORDED_DATE >= '" + SAP_SEND_KEIJO_DT_START_DT + "'  ");
        sb.append("   AND NH.BUSINESS_VALUE_1 IS NOT NULL  ");
        sb.append("   AND NH.KEYIN_TEMPLATE_NUMBER IS NOT NULL  ");
        sb.append("   AND NH.KEYIN_SYMBOL IS NOT NULL  ");
        
        return sb.toString();
    }

    private String insertWK_vatKanri(String taxRt) {

        StringBuffer sb = new StringBuffer();
        
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_URI_V_KANRI WSVK(  ");
        sb.append("   WSVK.KEIJO_DT ");
        sb.append("   , WSVK.TENPO_CD ");
        sb.append("   , WSVK.INVOICE_AUTO_NB ");
        sb.append("   , WSVK.INVOICE_AUTO_KB ");
        sb.append("   , WSVK.INVOICE_AUTO_RB ");
        sb.append("   , WSVK.DATA_KB ");
        sb.append("   , WSVK.TAX_RT ");
        sb.append("   , WSVK.URIAGE_VL_NUKI ");
        sb.append("   , WSVK.TAX_VL ");
        sb.append("   , WSVK.URIAGE_VL ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   DUIKH.*  ");
        sb.append(" FROM ");
        sb.append("   (  ");
        sb.append("     SELECT ");
        sb.append("       DUIKH.KEIJO_DT ");
        sb.append("       , DUIKH.TENPO_CD ");
        sb.append("       , DUIKH.INVOICE_AUTO_NB ");
        sb.append("       , DUIKH.INVOICE_AUTO_KB ");
        sb.append("       , DUIKH.INVOICE_AUTO_RB ");
        sb.append("       , DUIKH.DATA_KB ");
        sb.append("       , DUIKH.TAX_RT ");
        sb.append("       , ABS(SUM(DUIKH.URIAGE_VL_NUKI)) AS URIAGE_VL_NUKI ");
        sb.append("       , ABS(SUM(DUIKH.TAX_VL)) AS TAX_VL ");
        sb.append("       , ABS(SUM(DUIKH.URIAGE_VL)) AS URIAGE_VL  ");
        sb.append("     FROM ");
        sb.append("       (  ");
        sb.append("         SELECT ");
        sb.append("           DUIKH.EIGYO_DT AS KEIJO_DT ");
        sb.append("           , DUIKH.TENPO_CD ");
        sb.append("           , NVL(DUIKH.INVOICE_AUTO_NB, ' ') AS INVOICE_AUTO_NB ");
        sb.append("           , NVL(DUIKH.INVOICE_AUTO_KB, ' ') AS INVOICE_AUTO_KB ");
        sb.append("           , NVL(DUIKH.INVOICE_AUTO_RB, ' ') AS INVOICE_AUTO_RB ");
        sb.append("           , CASE  ");
        sb.append("             WHEN DUIKH.BETSU_SYS_DATA_KB = '3' THEN '01' ");//01:POS売上
        sb.append("             WHEN DUIKH.BETSU_SYS_DATA_KB = '5' THEN '02' ");//02:POS返品
        sb.append("             WHEN DUIKH.BETSU_SYS_DATA_KB = '6' THEN '03' ");//03:自家消費売上
        sb.append("             WHEN DUIKH.BETSU_SYS_DATA_KB = '7' THEN '04' ");//04:自家消費返品
        sb.append("             WHEN DUIKH.BETSU_SYS_DATA_KB = '4' THEN '05' ");//05:卸売上
        sb.append("             WHEN DUIKH.BETSU_SYS_DATA_KB = '8' THEN '06' ");//06:卸返品
        sb.append("             ELSE ' '  ");
        sb.append("             END AS DATA_KB ");
        sb.append(selectColumn(taxRt));//税率別に取得先のカラムを変動する
        sb.append("         FROM ");
        sb.append("           DT_URIAGE_INVOICE_KANRI_H DUIKH  ");
        sb.append("         WHERE ");
        sb.append("           DUIKH.EIGYO_DT >= '" + startDt + "' ");
        sb.append("           AND DUIKH.EIGYO_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");
        sb.append("           AND DUIKH.VAT_PRINT_KB <> '0'  ");//VAT未発行は除く
        sb.append("           AND DUIKH.INVOICE_AUTO_NB IS NOT NULL  ");
        sb.append("           AND DUIKH.INVOICE_AUTO_KB IS NOT NULL  ");
        sb.append("           AND DUIKH.INVOICE_AUTO_RB IS NOT NULL  ");
        sb.append("           AND DUIKH.RECEIPT_SUB_NO <> '0'  ");//子のレコードのみとする
        sb.append("           AND DUIKH.RECEIPT_SUB_NO IS NOT NULL ");
        sb.append("       ) DUIKH  ");
        sb.append("     GROUP BY ");
        sb.append("       DUIKH.KEIJO_DT ");
        sb.append("       , DUIKH.TENPO_CD ");
        sb.append("       , DUIKH.INVOICE_AUTO_NB ");
        sb.append("       , DUIKH.INVOICE_AUTO_KB ");
        sb.append("       , DUIKH.INVOICE_AUTO_RB ");
        sb.append("       , DUIKH.DATA_KB ");
        sb.append("       , DUIKH.TAX_RT ");
        sb.append("   ) DUIKH  ");
        sb.append(" WHERE ");
        sb.append("   DUIKH.URIAGE_VL_NUKI + DUIKH.TAX_VL + DUIKH.URIAGE_VL > 0 "); //税率別に抽出したあと、0VNDのものは除外する
        
        return sb.toString();
    }
    
    private String selectColumn(String taxRt) {
        StringBuffer sb = new StringBuffer();

        if (StringUtils.equals(taxRt, "AN")) {
            // 税率AN（非課税）
            sb.append("   , 'AN' AS TAX_RT ");
            sb.append("   , HIKAZEI_TOT_VL AS URIAGE_VL_NUKI ");
            sb.append("   , 0 AS TAX_VL ");
            sb.append("   , HIKAZEI_TOT_VL AS URIAGE_VL  ");
        } else if (StringUtils.equals(taxRt, "00")) {
            // 税率00
            sb.append("   , '00' AS TAX_RT ");
            sb.append("   , ZEIKOMI_TOT_0_VL AS URIAGE_VL_NUKI ");
            sb.append("   , 0 AS TAX_VL ");
            sb.append("   , ZEIKOMI_TOT_0_VL AS URIAGE_VL  ");
        } else if (StringUtils.equals(taxRt, "05")) {
            // 税率05
            sb.append("   , '05' AS TAX_RT ");
            sb.append("   , ZEIKOMI_TOT_5_VL - VAT_5_VL AS URIAGE_VL_NUKI ");
            sb.append("   , VAT_5_VL AS TAX_VL ");
            sb.append("   , ZEIKOMI_TOT_5_VL AS URIAGE_VL  ");
        } else if (StringUtils.equals(taxRt, "10")) {
            // 税率10
            sb.append("   , '10' AS TAX_RT ");
            sb.append("   , ZEIKOMI_TOT_10_VL - VAT_10_VL AS URIAGE_VL_NUKI ");
            sb.append("   , VAT_10_VL AS TAX_VL ");
            sb.append("   , ZEIKOMI_TOT_10_VL AS URIAGE_VL  ");
        }

        return sb.toString();
    }
    
    private String insertWK_tenVat(boolean skip) {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_TEN_VAT(  ");
        sb.append("   NUMBERING_HISTORY_NO ");
        sb.append("   , TAX_RT ");
        sb.append("   , KEIJO_DT ");
        sb.append("   , TENPO_CD ");
        sb.append("   , INVOICE_AUTO_NB ");
        sb.append("   , INVOICE_AUTO_KB ");
        sb.append("   , INVOICE_AUTO_RB ");
        sb.append("   , INVOICE_KEYIN_NB ");
        sb.append("   , INVOICE_KEYIN_KB ");
        sb.append("   , INVOICE_KEYIN_RB ");
        sb.append("   , DATA_KB ");
        sb.append("   , REASON_KB ");
        sb.append("   , URIAGE_VL_NUKI ");
        sb.append("   , TAX_VL ");
        sb.append("   , URIAGE_VL ");
        sb.append(" )  ");
        
        if(skip){
            //理由区分 = 2:紛失、3:廃止
            //採番履歴のみから取得。金額等は0。
            sb.append(" SELECT ");
            sb.append("   /*+ ORDERED USE_NL(WSUVS WSUVK) */ ");
            sb.append("   WSUVS.NUMBERING_HISTORY_NO ");
            sb.append("   , '00' AS TAX_RT ");
            sb.append("   , WSUVS.KEIJO_DT AS KEIJO_DT ");
            sb.append("   , WSUVS.TENPO_CD AS TENPO_CD ");
            sb.append("   , WSUVS.INVOICE_AUTO_NB AS INVOICE_AUTO_NB ");
            sb.append("   , WSUVS.INVOICE_AUTO_KB AS INVOICE_AUTO_KB ");
            sb.append("   , WSUVS.INVOICE_AUTO_RB AS INVOICE_AUTO_RB ");
            sb.append("   , WSUVS.INVOICE_KEYIN_NB AS INVOICE_KEYIN_NB ");
            sb.append("   , WSUVS.INVOICE_KEYIN_KB AS INVOICE_KEYIN_KB ");
            sb.append("   , WSUVS.INVOICE_KEYIN_RB AS INVOICE_KEYIN_RB ");
            sb.append("   , WSUVS.DATA_KB AS DATA_KB ");// ※注 理由区分 = 紛失、廃止の場合は、採番履歴より取得。
            sb.append("   , WSUVS.REASON_KB AS REASON_KB ");
            sb.append("   , 0 AS URIAGE_VL_NUKI ");
            sb.append("   , 0 AS TAX_VL ");
            sb.append("   , 0 AS URIAGE_VL ");
            sb.append(" FROM ");
            sb.append("   WK_SAP_URI_V_SAIBAN WSUVS  ");
            sb.append(" WHERE ");
            sb.append("   WSUVS.REASON_KB IN ('2','3') ");
            
        }else{
            //理由区分 <> 2:紛失、3:廃止
            //採番履歴と売上INVOICE管理から取得。金額等とデータ区分は売上INVOICE管理から取得。
            sb.append(" SELECT ");
            sb.append("   /*+ ORDERED USE_NL(WSUVS WSUVK) */ ");
            sb.append("   WSUVS.NUMBERING_HISTORY_NO ");
            sb.append("   , WSUVK.TAX_RT AS TAX_RT ");
            sb.append("   , WSUVS.KEIJO_DT AS KEIJO_DT ");
            sb.append("   , WSUVS.TENPO_CD AS TENPO_CD ");
            sb.append("   , WSUVS.INVOICE_AUTO_NB AS INVOICE_AUTO_NB ");
            sb.append("   , WSUVS.INVOICE_AUTO_KB AS INVOICE_AUTO_KB ");
            sb.append("   , WSUVS.INVOICE_AUTO_RB AS INVOICE_AUTO_RB ");
            sb.append("   , WSUVS.INVOICE_KEYIN_NB AS INVOICE_KEYIN_NB ");
            sb.append("   , WSUVS.INVOICE_KEYIN_KB AS INVOICE_KEYIN_KB ");
            sb.append("   , WSUVS.INVOICE_KEYIN_RB AS INVOICE_KEYIN_RB ");
            sb.append("   , WSUVK.DATA_KB AS DATA_KB ");// ※注 理由区分 <> 紛失、廃止の場合は、売上INVOICE管理より取得。
            sb.append("   , WSUVS.REASON_KB AS REASON_KB ");
            sb.append("   , WSUVK.URIAGE_VL_NUKI AS URIAGE_VL_NUKI ");
            sb.append("   , WSUVK.TAX_VL AS TAX_VL ");
            sb.append("   , WSUVK.URIAGE_VL AS URIAGE_VL ");
            sb.append(" FROM ");
            sb.append("   WK_SAP_URI_V_SAIBAN WSUVS  ");
            sb.append("   INNER JOIN WK_SAP_URI_V_KANRI WSUVK  ");
            sb.append("     ON WSUVS.KEIJO_DT = WSUVK.KEIJO_DT  ");
            sb.append("     AND WSUVS.TENPO_CD = WSUVK.TENPO_CD  ");
            sb.append("     AND WSUVS.INVOICE_AUTO_NB = WSUVK.INVOICE_AUTO_NB  ");
            sb.append("     AND WSUVS.INVOICE_AUTO_KB = WSUVK.INVOICE_AUTO_KB  ");
            sb.append("     AND WSUVS.INVOICE_AUTO_RB = WSUVK.INVOICE_AUTO_RB  ");
            sb.append(" WHERE ");
            sb.append("   WSUVS.REASON_KB NOT IN ('2','3') ");
        }

        return sb.toString();
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

    private String truncateWK_vatKanri() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_URI_V_KANRI ");
        return sb.toString();
    }
    
    private String truncateWK_vatSkip() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_URI_V_SAIBAN ");
        return sb.toString();
    }
    
    private String truncateWK_tenVat() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_TEN_VAT ");
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
            DaoIf dao = new IfSapTenpoUriageVatinvoiceInsertWkDao();
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