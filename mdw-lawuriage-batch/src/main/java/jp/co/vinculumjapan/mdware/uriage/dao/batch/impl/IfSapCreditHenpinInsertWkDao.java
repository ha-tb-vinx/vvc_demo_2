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
 * タイトル：IfSapCreditHenpinInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF クレジット返品 ワーク作成処理
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
public class IfSapCreditHenpinInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810060";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF クレジット返品 ワーク作成処理";
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** 送信時の対象計上日開始日（SAP結合機→SAP本番機への切替用） */
    private static final String SAP_SEND_KEIJO_DT_START_DT = FiResorceUtility.getPropertie("SAP_SEND_KEIJO_DT_START_DT");
    
    private String startDt = "";
    
    private String creditShiharaiSyubetsuCd = "";

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF クレジット返品 ワーク作成処理");
        
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

        // ログを出力
        invoker.infoLog(strUserId + "　：　前回のワークを削除します。");
        
        ps = invoker.getDataBase().prepareStatement(truncateWk());
        ps.executeQuery();
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(truncateWK_tax());
        ps.executeQuery();
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(truncateWK_receipt());
        ps.executeQuery();
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(truncateWK_payment());
        ps.executeQuery();
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(truncateWK_base());
        ps.executeQuery();
        
        invoker.infoLog(strUserId + "　：　前回のワークを削除しました。");

        // ログを出力
        invoker.infoLog(strUserId + "　：　ワーク作成を開始します。");
        
        invoker.infoLog(strUserId + "　：　現金返品・クレジット返品共通ワークを作成します。");
        
        ps = null;
        invoker.infoLog(strUserId + "　：　1. 日・店・レシート・税率別集計");
        ps = invoker.getDataBase().prepareStatement(insertWK_tax());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　2. 日・店・レシート別集計");
        ps = invoker.getDataBase().prepareStatement(insertWK_receipt());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　3. 日・店・レシート・支払種別別集計");
        ps = invoker.getDataBase().prepareStatement(insertWK_payment());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　4. 日・店・レシート・支払種別別集計");
        ps = invoker.getDataBase().prepareStatement(mergeWK_receipt());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　5. 按分基礎数値集計");
        ps = invoker.getDataBase().prepareStatement(insertWK_base());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　6. 按分処理");
        ps = invoker.getDataBase().prepareStatement(updateWK_base_anbn());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　7. 端数算出処理");
        ps = invoker.getDataBase().prepareStatement(mergeWK_base_hasu());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　8. 結果算出処理");
        ps = invoker.getDataBase().prepareStatement(updateWK_base_kekka());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        invoker.infoLog(strUserId + "　：　現金返品・クレジット返品共通ワーク作成を終了します。");
        
        invoker.infoLog(strUserId + "　：　ワーク作成を終了します。");
        
        ps = null;

        // クレジット返品データ取得処理SQL
        ps = invoker.getDataBase().prepareStatement(selectWK_creditHenpin());

        // SQLを実行する
        ps.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　ワーク作成を終了します。");

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
    
    private String insertWK_tax() {
        StringBuffer sb = new StringBuffer();
        //売上INVOICE管理明細テーブル（≒POS Aレコード）より、返品額をレシート・税率別に集約する
        //作成行は返品のみに絞る
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_HENPIN_TAX WSHT(  ");
        sb.append("   WSHT.KEIJO_DT ");
        sb.append("   , WSHT.TENPO_CD ");
        sb.append("   , WSHT.REGISTER_NO ");
        sb.append("   , WSHT.TRANSACTION_NO ");
        sb.append("   , WSHT.TAX_RT ");
        sb.append("   , WSHT.URIAGE_VL_NUKI ");
        sb.append("   , WSHT.TAX_VL ");
        sb.append("   , WSHT.URIAGE_VL ");
        sb.append(" )  ");
        sb.append(" SELECT /*+ ORDERED USE_NL(DUIKM DZSS) */ ");
        sb.append("   DUIKM.EIGYO_DT AS KEIJO_DT ");
        sb.append("   , DUIKM.TENPO_CD AS TENPO_CD ");
        sb.append("   , DUIKM.REGI_RB AS REGISTER_NO ");
        sb.append("   , DUIKM.TERMINAL_RB AS TRANSACTION_NO ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TO_NUMBER(TRIM(ZEI_KB)) = 3  ");
        sb.append("     THEN 'AN'  ");
        sb.append("     ELSE TRIM(TO_CHAR(NVL(DUIKM.TAX_RT, 0), '00'))  ");
        sb.append("     END AS TAX_RT ");
        sb.append("   , SUM(ABS(ZEIKOMI_VL)) - SUM(ABS(ZEIGAKU_VL)) AS URIAGE_VL_NUKI ");
        sb.append("   , SUM(ABS(ZEIGAKU_VL)) AS TAX_VL ");
        sb.append("   , SUM(ABS(ZEIKOMI_VL)) AS URIAGE_VL  ");
        sb.append(" FROM ");
        sb.append("   DT_URIAGE_INVOICE_KANRI_M DUIKM  ");
        sb.append("   INNER JOIN ");
        sb.append("   DT_ZENTEN_SEISAN_STATE DZSS");
        sb.append("     ON DZSS.COMP_CD = '" + COMP_CD + "'  ");
        sb.append("     AND DUIKM.EIGYO_DT = DZSS.KEIJO_DT ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2' ");//精算済のみ作成
        sb.append(" WHERE ");
        sb.append("   DUIKM.SALES_TYPE = '0301'  ");
        sb.append("   AND DUIKM.EIGYO_DT >= '" + startDt + "' ");
        sb.append("   AND DUIKM.EIGYO_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");
        sb.append("   AND RECEIPT_SUB_NO = '0'  ");//親レコードのみ取得
        sb.append(" GROUP BY ");
        sb.append("   DUIKM.EIGYO_DT ");
        sb.append("   , DUIKM.TENPO_CD ");
        sb.append("   , DUIKM.REGI_RB ");
        sb.append("   , DUIKM.TERMINAL_RB ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TO_NUMBER(TRIM(ZEI_KB)) = 3  ");
        sb.append("     THEN 'AN'  ");
        sb.append("     ELSE TRIM(TO_CHAR(NVL(DUIKM.TAX_RT, 0), '00'))  ");
        sb.append("     END "); 
        
        return sb.toString();
    }
    
    private String insertWK_receipt(){
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT  ");
        sb.append(" INTO WK_SAP_HENPIN_RECEIPT WSHR(  ");
        sb.append("   WSHR.KEIJO_DT ");
        sb.append("   , WSHR.TENPO_CD ");
        sb.append("   , WSHR.REGISTER_NO ");
        sb.append("   , WSHR.TRANSACTION_NO ");
        sb.append("   , WSHR.T_URIAGE_VL_NUKI_SUM ");
        sb.append("   , WSHR.T_TAX_VL_SUM ");
        sb.append("   , WSHR.T_URIAGE_VL_SUM ");
        sb.append("   , WSHR.P_URIAGE_VL_SUM ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSHT.KEIJO_DT ");
        sb.append("   , WSHT.TENPO_CD ");
        sb.append("   , WSHT.REGISTER_NO ");
        sb.append("   , WSHT.TRANSACTION_NO ");
        sb.append("   , SUM(WSHT.URIAGE_VL_NUKI) AS T_URIAGE_VL_NUKI_SUM ");
        sb.append("   , SUM(WSHT.TAX_VL) AS T_TAX_VL_SUM ");
        sb.append("   , SUM(WSHT.URIAGE_VL) AS T_URIAGE_VL_SUM ");
        sb.append("   , 0 AS P_URIAGE_VL_SUM ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_HENPIN_TAX WSHT  ");
        sb.append(" GROUP BY ");
        sb.append("   WSHT.KEIJO_DT ");
        sb.append("   , WSHT.TENPO_CD ");
        sb.append("   , WSHT.REGISTER_NO ");
        sb.append("   , WSHT.TRANSACTION_NO ");
        
        return sb.toString();
    }
    
    private String insertWK_payment() {
        //店別レシート精算より、返品のあるレコードのみ取得する
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_HENPIN_PAYMENT WSHP(  ");
        sb.append("   WSHP.KEIJO_DT ");
        sb.append("   , WSHP.TENPO_CD ");
        sb.append("   , WSHP.REGISTER_NO ");
        sb.append("   , WSHP.TRANSACTION_NO ");
        sb.append("   , WSHP.SEND_CREDIT_NO ");
        sb.append("   , WSHP.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSHP.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSHP.SHIHARAI_SYUBETSU_GROUP_CD ");
        sb.append("   , WSHP.CREDIT_NO ");
        sb.append("   , WSHP.TEISEIGO_CREDIT_NO ");
        sb.append("   , WSHP.POS_VL ");
        sb.append("   , WSHP.SAGAKU_VL ");
        sb.append("   , WSHP.URIAGE_VL ");
        sb.append("   , WSHP.RANK ");
        sb.append(" )  ");
        sb.append(" SELECT /*+ ORDERED USE_NL(WSHR DTRS)*/ ");
        sb.append("   DTRS.KEIJO_DT ");
        sb.append("   , DTRS.TENPO_CD ");
        sb.append("   , DTRS.REGISTER_NO ");
        sb.append("   , DTRS.TRANSACTION_NO ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TRIM(DTRS.TEISEIGO_CREDIT_NO) IS NOT NULL  ");
        sb.append("     THEN RPAD(NVL(DTRS.TEISEIGO_CREDIT_NO, ' '), 20, ' ')  ");
        sb.append("     ELSE RPAD(NVL(DTRS.CREDIT_NO, ' '), 20, ' ')  ");
        sb.append("     END AS SEND_CREDIT_NO ");
        sb.append("   , DTRS.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , DTRS.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , CASE ");
        sb.append("     WHEN DTRS.SHIHARAI_SYUBETSU_CD IN ( "+ creditShiharaiSyubetsuCd +" ) THEN '02' ");
        sb.append("     ELSE '01' ");
        sb.append("     END AS SHIHARAI_SYUBETSU_GROUP_CD ");
        sb.append("   , DTRS.CREDIT_NO ");
        sb.append("   , DTRS.TEISEIGO_CREDIT_NO  "); 
        sb.append("   , ABS(NVL(DTRS.POS_VL,0)) ");
        sb.append("   , ABS(NVL(DTRS.SAGAKU_VL,0)) ");
        sb.append("   , ABS(NVL(DTRS.POS_VL,0)) + ABS(NVL(DTRS.SAGAKU_VL,0)) ");
        sb.append("   , ROW_NUMBER() OVER ( ");
        sb.append("     PARTITION BY ");
        sb.append("       DTRS.KEIJO_DT ");
        sb.append("       , DTRS.TENPO_CD ");
        sb.append("       , DTRS.REGISTER_NO ");
        sb.append("       , DTRS.TRANSACTION_NO  ");
        sb.append("     ORDER BY ");
        sb.append("       ABS(NVL(DTRS.POS_VL,0)) + ABS(NVL(DTRS.SAGAKU_VL,0)) DESC "); 
        sb.append("       , DTRS.SHIHARAI_SYUBETSU_SUB_CD "); 
        sb.append("     ) AS RANK ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_HENPIN_RECEIPT WSHR ");
        sb.append("   INNER JOIN DT_TEN_RECEIPT_SEISAN DTRS ");
        sb.append("     ON DTRS.COMP_CD = '" + COMP_CD + "'  ");
        sb.append("     AND WSHR.KEIJO_DT = DTRS.KEIJO_DT  ");
        sb.append("     AND WSHR.TENPO_CD = DTRS.TENPO_CD  ");
        sb.append("     AND WSHR.REGISTER_NO = DTRS.REGISTER_NO  ");
        sb.append("     AND WSHR.TRANSACTION_NO = DTRS.TRANSACTION_NO "); 
        
        return sb.toString();
    }
    
    private String mergeWK_receipt() {
        StringBuffer sb = new StringBuffer();
        sb.append(" MERGE /*+ ORDERED USE_NL(WSHP WSHR) */ ");
        sb.append(" INTO WK_SAP_HENPIN_RECEIPT WSHR ");
        sb.append("   USING (  ");
        sb.append("     SELECT ");
        sb.append("       WSHP.KEIJO_DT ");
        sb.append("       , WSHP.TENPO_CD ");
        sb.append("       , WSHP.REGISTER_NO ");
        sb.append("       , WSHP.TRANSACTION_NO ");
        sb.append("       , SUM(WSHP.URIAGE_VL) AS P_URIAGE_VL_SUM  ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_HENPIN_PAYMENT WSHP  ");
        sb.append("     GROUP BY ");
        sb.append("       WSHP.KEIJO_DT ");
        sb.append("       , WSHP.TENPO_CD ");
        sb.append("       , WSHP.REGISTER_NO ");
        sb.append("       , WSHP.TRANSACTION_NO ");
        sb.append("   ) WSHP  ");
        sb.append("     ON (  ");
        sb.append("       WSHR.KEIJO_DT = WSHP.KEIJO_DT  ");
        sb.append("       AND WSHR.TENPO_CD = WSHP.TENPO_CD  ");
        sb.append("       AND WSHR.REGISTER_NO = WSHP.REGISTER_NO  ");
        sb.append("       AND WSHR.TRANSACTION_NO = WSHP.TRANSACTION_NO ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   WSHR.P_URIAGE_VL_SUM = WSHP.P_URIAGE_VL_SUM ");
        
        return sb.toString();
    }

    private String insertWK_base() {
        StringBuffer sb = new StringBuffer();
        
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_HENPIN_BASE WSHB(  ");
        sb.append("   WSHB.KEIJO_DT ");
        sb.append("   , WSHB.TENPO_CD ");
        sb.append("   , WSHB.REGISTER_NO ");
        sb.append("   , WSHB.TRANSACTION_NO ");
        sb.append("   , WSHB.SEND_CREDIT_NO ");
        sb.append("   , WSHB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSHB.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSHB.TAX_RT ");
        sb.append("   , WSHB.SHIHARAI_SYUBETSU_GROUP_CD ");
        sb.append("   , WSHB.R_URIAGE_VL ");
        sb.append("   , WSHB.P_URIAGE_VL_SUM ");
        sb.append("   , WSHB.P_URIAGE_VL ");
        sb.append("   , WSHB.T_URIAGE_VL_NUKI ");
        sb.append("   , WSHB.T_TAX_VL ");
        sb.append("   , WSHB.T_URIAGE_VL ");
        sb.append("   , WSHB.ANBN_URIAGE_VL_NUKI ");
        sb.append("   , WSHB.ANBN_TAX_VL ");
        sb.append("   , WSHB.ANBN_URIAGE_VL ");
        sb.append("   , WSHB.P_RANK ");
        sb.append("   , WSHB.HASU_URIAGE_VL_NUKI ");
        sb.append("   , WSHB.HASU_TAX_VL ");
        sb.append("   , WSHB.HASU_URIAGE_VL ");
        sb.append("   , WSHB.KEKKA_URIAGE_VL_NUKI ");
        sb.append("   , WSHB.KEKKA_TAX_VL ");
        sb.append("   , WSHB.KEKKA_URIAGE_VL ");
        sb.append(" )  ");
        sb.append(" SELECT /*+ ORDERED USE_NL(WSHR WSHP WSHT) */ ");
        sb.append("   WSHP.KEIJO_DT ");
        sb.append("   , WSHP.TENPO_CD ");
        sb.append("   , WSHP.REGISTER_NO ");
        sb.append("   , WSHP.TRANSACTION_NO ");
        sb.append("   , WSHP.SEND_CREDIT_NO ");
        sb.append("   , WSHP.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSHP.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSHT.TAX_RT ");
        sb.append("   , WSHP.SHIHARAI_SYUBETSU_GROUP_CD ");
        sb.append("   , WSHR.T_URIAGE_VL_SUM AS R_URIAGE_VL_SUM ");
        sb.append("   , WSHR.P_URIAGE_VL_SUM AS P_URIAGE_VL_SUM ");
        sb.append("   , WSHP.URIAGE_VL AS P_URIAGE_VL ");
        sb.append("   , WSHT.URIAGE_VL_NUKI AS T_URIAGE_VL_NUKI ");
        sb.append("   , WSHT.TAX_VL AS T_TAX_VL ");
        sb.append("   , WSHT.URIAGE_VL AS T_URIAGE_VL ");
        sb.append("   , 0 AS ANBN_URIAGE_VL_NUKI ");
        sb.append("   , 0 AS ANBN_TAX_VL ");
        sb.append("   , 0 AS ANBN_URIAGE_VL ");
        sb.append("   , WSHP.RANK AS P_RANK ");
        sb.append("   , 0 AS HASU_URIAGE_VL_NUKI ");
        sb.append("   , 0 AS HASU_TAX_VL ");
        sb.append("   , 0 AS HASU_URIAGE_VL ");
        sb.append("   , 0 AS KEKKA_URIAGE_VL_NUKI ");
        sb.append("   , 0 AS KEKKA_TAX_VL ");
        sb.append("   , 0 AS KEKKA_URIAGE_VL  ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_HENPIN_RECEIPT WSHR  ");
        sb.append("   INNER JOIN WK_SAP_HENPIN_PAYMENT WSHP  ");
        sb.append("     ON WSHR.KEIJO_DT = WSHP.KEIJO_DT  ");
        sb.append("     AND WSHR.TENPO_CD = WSHP.TENPO_CD  ");
        sb.append("     AND WSHR.REGISTER_NO = WSHP.REGISTER_NO  ");
        sb.append("     AND WSHR.TRANSACTION_NO = WSHP.TRANSACTION_NO  ");
        sb.append("   INNER JOIN WK_SAP_HENPIN_TAX WSHT  ");
        sb.append("     ON WSHP.KEIJO_DT = WSHT.KEIJO_DT  ");
        sb.append("     AND WSHR.TENPO_CD = WSHT.TENPO_CD  ");
        sb.append("     AND WSHR.REGISTER_NO = WSHT.REGISTER_NO  ");
        sb.append("     AND WSHR.TRANSACTION_NO = WSHT.TRANSACTION_NO "); 

        return sb.toString();
    }
    
    private String updateWK_base_anbn(){
        StringBuffer sb = new StringBuffer();
        // 
        sb.append(" UPDATE WK_SAP_HENPIN_BASE WSHB ");
        sb.append(" SET ");
        sb.append("   WSHB.ANBN_URIAGE_VL_NUKI = ROUND(  ");
        sb.append("     ROUND(  ");
        sb.append("       WSHB.T_URIAGE_VL_NUKI * (WSHB.P_URIAGE_VL / WSHB.P_URIAGE_VL_SUM) ");
        sb.append("       , 1 ");
        sb.append("     ) - 0.1 ");
        sb.append("   )  ");
        sb.append("   , WSHB.ANBN_TAX_VL = ROUND(  ");
        sb.append("     ROUND(  ");
        sb.append("       WSHB.T_TAX_VL * (WSHB.P_URIAGE_VL / WSHB.P_URIAGE_VL_SUM) ");
        sb.append("       , 1 ");
        sb.append("     ) - 0.1 ");
        sb.append("   )  ");
        sb.append("   , WSHB.ANBN_URIAGE_VL = ROUND(  ");
        sb.append("     ROUND(  ");
        sb.append("       WSHB.T_URIAGE_VL * (WSHB.P_URIAGE_VL / WSHB.P_URIAGE_VL_SUM) ");
        sb.append("       , 1 ");
        sb.append("     ) - 0.1 ");
        sb.append("   )  ");
        sb.append(" WHERE  ");
        sb.append("   WSHB.P_URIAGE_VL_SUM > 0  ");

        return sb.toString();
    }
    
    private String mergeWK_base_hasu(){
        StringBuffer sb = new StringBuffer();
        // 
        sb.append(" MERGE /*+ ORDERED USE_NL(WSHB WSHB_ANBN) */");
        sb.append(" INTO WK_SAP_HENPIN_BASE WSHB  ");
        sb.append("   USING (  ");
        sb.append("     SELECT ");
        sb.append("       WSHB_ANBN.KEIJO_DT ");
        sb.append("       , WSHB_ANBN.TENPO_CD ");
        sb.append("       , WSHB_ANBN.REGISTER_NO ");
        sb.append("       , WSHB_ANBN.TRANSACTION_NO ");
        sb.append("       , SUM(WSHB_ANBN.ANBN_URIAGE_VL_NUKI) ANBN_URIAGE_VL_NUKI ");
        sb.append("       , SUM(WSHB_ANBN.ANBN_TAX_VL) ANBN_TAX_VL ");
        sb.append("       , SUM(WSHB_ANBN.ANBN_URIAGE_VL) ANBN_URIAGE_VL  ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_HENPIN_BASE WSHB_ANBN  ");
        sb.append("     GROUP BY ");
        sb.append("       WSHB_ANBN.KEIJO_DT ");
        sb.append("       , WSHB_ANBN.TENPO_CD ");
        sb.append("       , WSHB_ANBN.REGISTER_NO ");
        sb.append("       , WSHB_ANBN.TRANSACTION_NO ");
        sb.append("   ) WSHB_ANBN  ");
        sb.append("     ON (  ");
        sb.append("       WSHB.KEIJO_DT = WSHB_ANBN.KEIJO_DT  ");
        sb.append("       AND WSHB.TENPO_CD = WSHB_ANBN.TENPO_CD  ");
        sb.append("       AND WSHB.REGISTER_NO = WSHB_ANBN.REGISTER_NO  ");
        sb.append("       AND WSHB.TRANSACTION_NO = WSHB_ANBN.TRANSACTION_NO ");
        sb.append("     ) WHEN MATCHED THEN UPDATE  ");
        sb.append(" SET ");
        sb.append("   WSHB.HASU_URIAGE_VL_NUKI = WSHB.T_URIAGE_VL_NUKI - WSHB_ANBN.ANBN_URIAGE_VL_NUKI ");
        sb.append("   , WSHB.HASU_TAX_VL = WSHB.T_TAX_VL - WSHB_ANBN.ANBN_TAX_VL ");
        sb.append("   , WSHB.HASU_URIAGE_VL = WSHB.T_URIAGE_VL - WSHB_ANBN.ANBN_URIAGE_VL "); 

        return sb.toString();
    }
    
    private String updateWK_base_kekka(){
        StringBuffer sb = new StringBuffer();
        // 
        sb.append(" UPDATE WK_SAP_HENPIN_BASE WSHB ");
        sb.append(" SET ");
        sb.append("   WSHB.KEKKA_URIAGE_VL_NUKI = (  ");
        sb.append("     CASE  ");
        sb.append("       WHEN WSHB.P_RANK = 1  ");
        sb.append("       THEN WSHB.ANBN_URIAGE_VL_NUKI + WSHB.HASU_URIAGE_VL_NUKI  ");
        sb.append("       ELSE WSHB.ANBN_URIAGE_VL_NUKI  ");
        sb.append("       END ");
        sb.append("   )  ");
        sb.append("   , WSHB.KEKKA_TAX_VL = (  ");
        sb.append("     CASE  ");
        sb.append("       WHEN WSHB.P_RANK = 1  ");
        sb.append("       THEN WSHB.ANBN_TAX_VL + WSHB.HASU_TAX_VL  ");
        sb.append("       ELSE WSHB.ANBN_TAX_VL  ");
        sb.append("       END ");
        sb.append("   )  ");
        sb.append("   , WSHB.KEKKA_URIAGE_VL = (  ");
        sb.append("     CASE  ");
        sb.append("       WHEN WSHB.P_RANK = 1  ");
        sb.append("       THEN WSHB.ANBN_URIAGE_VL + WSHB.HASU_URIAGE_VL  ");
        sb.append("       ELSE WSHB.ANBN_URIAGE_VL  ");
        sb.append("       END "); 
        sb.append("   )  "); 

        return sb.toString();
    }

    private String selectWK_creditHenpin() {

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_CREDIT_HENPIN WSCH(  ");
        sb.append("   WSCH.KEIJO_DT ");
        sb.append("   , WSCH.TENPO_CD ");
        sb.append("   , WSCH.REGISTER_NO ");
        sb.append("   , WSCH.TRANSACTION_NO ");
        sb.append("   , WSCH.SEND_CREDIT_NO ");
        sb.append("   , WSCH.TAX_RT ");
        sb.append("   , WSCH.URIAGE_VL_NUKI ");
        sb.append("   , WSCH.TAX_VL ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSHB.KEIJO_DT ");
        sb.append("   , WSHB.TENPO_CD ");
        sb.append("   , WSHB.REGISTER_NO ");
        sb.append("   , WSHB.TRANSACTION_NO ");
        sb.append("   , WSHB.SEND_CREDIT_NO ");
        sb.append("   , WSHB.TAX_RT ");
        sb.append("   , SUM(WSHB.KEKKA_URIAGE_VL_NUKI) AS URIAGE_VL_NUKI ");
        sb.append("   , SUM(WSHB.KEKKA_TAX_VL) AS TAX_VL  ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_HENPIN_BASE WSHB  ");
        sb.append(" WHERE ");
        sb.append("   WSHB.SHIHARAI_SYUBETSU_GROUP_CD = '02'  ");
        sb.append(" GROUP BY ");
        sb.append("   WSHB.KEIJO_DT ");
        sb.append("   , WSHB.TENPO_CD ");
        sb.append("   , WSHB.REGISTER_NO ");
        sb.append("   , WSHB.TRANSACTION_NO ");
        sb.append("   , WSHB.SEND_CREDIT_NO ");
        sb.append("   , WSHB.TAX_RT ");

        return sb.toString();
    }
    
    private String truncateWK_tax() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_HENPIN_TAX ");
        return sb.toString();
    }
    
    private String truncateWK_payment() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_HENPIN_PAYMENT ");
        return sb.toString();
    }
    
    private String truncateWK_receipt() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_HENPIN_RECEIPT ");
        return sb.toString();
    }
    
    private String truncateWK_base() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_HENPIN_BASE ");
        return sb.toString();
    }

    private String truncateWk() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_CREDIT_HENPIN ");
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
            DaoIf dao = new IfSapCreditHenpinInsertWkDao();
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