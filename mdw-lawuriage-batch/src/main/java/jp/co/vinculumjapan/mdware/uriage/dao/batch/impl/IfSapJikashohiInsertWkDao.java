package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

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
 * タイトル：IfSapJikashohiInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 自家消費 ワーク作成処理
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
public class IfSapJikashohiInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810080";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 自家消費 ワーク作成処理";
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** 送信時の対象計上日開始日（SAP結合機→SAP本番機への切替用） */
    private static final String SAP_SEND_KEIJO_DT_START_DT = FiResorceUtility.getPropertie("SAP_SEND_KEIJO_DT_START_DT");
    
    private String startDt = "";

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 自家消費 ワーク作成処理");

        invoker.infoLog(strUserId + "　：　前回のワークを削除します。");
        
        PreparedStatementEx ps = null;
        ResultSet rs = null;
        
        ps = invoker.getDataBase().prepareStatement(selectMishimeDt());
        rs = ps.executeQuery();
        if (rs.next()) {
            startDt = rs.getString("START_DT");
        }
        
        ps = null;
        rs = null;
        
        ps = invoker.getDataBase().prepareStatement(truncateWK_jikashohi());
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

        invoker.infoLog(strUserId + "　：　ワーク作成を開始します。");
        
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
        invoker.infoLog(strUserId + "　：　6. 按分基礎数値集計");
        ps = invoker.getDataBase().prepareStatement(mergeWK_base());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　7. 按分処理");
        ps = invoker.getDataBase().prepareStatement(updateWK_base_anbn());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　8. 端数算出処理");
        ps = invoker.getDataBase().prepareStatement(mergeWK_base_rank());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　9. 端数算出処理");
        ps = invoker.getDataBase().prepareStatement(mergeWK_base_hasu());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　10. 按分結果算出処理");
        ps = invoker.getDataBase().prepareStatement(updateWK_base_kekka());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　11. ワーク移送処理");
        ps = invoker.getDataBase().prepareStatement(insertWk_jikashohi());

        ps.executeQuery();

        invoker.infoLog(strUserId + "　：　ワーク作成を終了します。");

    }
    
    private String insertWK_tax() {
        StringBuffer sb = new StringBuffer();
        //売上INVOICE管理明細テーブル（≒POS Aレコード）より、自家消費額をレシート・税率・DIV別に集約する
        //作成行は自家消費売上・自家消費返品のみに絞る
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_JIKASHOHI_TAX WSJT(  ");
        sb.append("   WSJT.KEIJO_DT ");
        sb.append("   , WSJT.TENPO_CD ");
        sb.append("   , WSJT.REGISTER_NO ");
        sb.append("   , WSJT.TRANSACTION_NO ");
        sb.append("   , WSJT.TAX_RT ");
        sb.append("   , WSJT.DAIBUNRUI2_CD ");
        sb.append("   , WSJT.SALES_TYPE ");
        sb.append("   , WSJT.URIAGE_VL_NUKI ");
        sb.append("   , WSJT.TAX_VL ");
        sb.append("   , WSJT.URIAGE_VL ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   DUIKM.EIGYO_DT AS KEIJO_DT ");
        sb.append("   , DUIKM.TENPO_CD AS TENPO_CD ");
        sb.append("   , DUIKM.REGI_RB AS REGISTER_NO ");
        sb.append("   , DUIKM.TERMINAL_RB AS TRANSACTION_NO ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TO_NUMBER(TRIM(DUIKM.ZEI_KB)) = 3  ");
        sb.append("     THEN 'AN'  ");
        sb.append("     ELSE TRIM(TO_CHAR(NVL(DUIKM.TAX_RT, 0), '00'))  ");
        sb.append("     END AS TAX_RT ");
        sb.append("   , RFST.DAIBUNRUI2_CD ");
        sb.append("   , DUIKM.SALES_TYPE ");
        sb.append("   , SUM(ABS(ZEIKOMI_VL)) - SUM(ABS(ZEIGAKU_VL)) AS URIAGE_VL_NUKI ");
        sb.append("   , SUM(ABS(ZEIGAKU_VL)) AS TAX_VL ");
        sb.append("   , SUM(ABS(ZEIKOMI_VL)) AS URIAGE_VL  ");
        sb.append(" FROM ");
        sb.append("   DT_URIAGE_INVOICE_KANRI_M DUIKM  ");
        sb.append("   INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS");
        sb.append("     ON DZSS.COMP_CD = '"+COMP_CD+"' ");
        sb.append("     AND DUIKM.EIGYO_DT = DZSS.KEIJO_DT ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2' ");//精算済のみ作成
        sb.append("   INNER JOIN R_SYOHIN RS  ");
        sb.append("     ON RS.SYOHIN_CD = DUIKM.SYOHIN_CD  ");
        sb.append("     AND RS.YUKO_DT = (  ");
        sb.append("       SELECT ");
        sb.append("         MAX(RS.YUKO_DT)  ");
        sb.append("       FROM ");
        sb.append("         R_SYOHIN RS  ");
        sb.append("       WHERE ");
        sb.append("         RS.SYOHIN_CD = DUIKM.SYOHIN_CD  ");
        sb.append("         AND RS.YUKO_DT <= DUIKM.EIGYO_DT ");
        sb.append("     )  ");
        sb.append("   INNER JOIN R_FI_SYOHIN_TAIKEI RFST  ");
        sb.append("     ON RFST.COMP_CD = '" + COMP_CD + "'  ");
        sb.append("     AND RFST.BUNRUI5_CD = RS.BUNRUI5_CD  ");
        sb.append("     AND RFST.YUKO_DT = (  ");
        sb.append("       SELECT ");
        sb.append("         MAX(RFST.YUKO_DT)  ");
        sb.append("       FROM ");
        sb.append("         R_FI_SYOHIN_TAIKEI RFST  ");
        sb.append("       WHERE ");
        sb.append("         RFST.COMP_CD = '" + COMP_CD + "'  ");
        sb.append("         AND RFST.BUNRUI5_CD = RS.BUNRUI5_CD  ");
        sb.append("         AND RFST.YUKO_DT <= DUIKM.EIGYO_DT ");
        sb.append("     )  ");
        sb.append(" WHERE ");
        sb.append("   DUIKM.SALES_TYPE IN ('1011', '1311')  ");
        sb.append("   AND DUIKM.EIGYO_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");
        sb.append("   AND DUIKM.RECEIPT_SUB_NO = '0'  ");//親レコードのみ取得
        sb.append(" GROUP BY ");
        sb.append("   DUIKM.EIGYO_DT ");
        sb.append("   , DUIKM.TENPO_CD ");
        sb.append("   , DUIKM.REGI_RB ");
        sb.append("   , DUIKM.TERMINAL_RB ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TO_NUMBER(TRIM(DUIKM.ZEI_KB)) = 3  ");
        sb.append("     THEN 'AN'  ");
        sb.append("     ELSE TRIM(TO_CHAR(NVL(DUIKM.TAX_RT, 0), '00'))  ");
        sb.append("     END ");
        sb.append("   , RFST.DAIBUNRUI2_CD "); 
        sb.append("   , DUIKM.SALES_TYPE ");
        
        return sb.toString();
    }
    
    private String insertWK_receipt(){
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT  ");
        sb.append(" INTO WK_SAP_JIKASHOHI_RECEIPT WSJR(  ");
        sb.append("   WSJR.KEIJO_DT ");
        sb.append("   , WSJR.TENPO_CD ");
        sb.append("   , WSJR.REGISTER_NO ");
        sb.append("   , WSJR.TRANSACTION_NO ");
        sb.append("   , WSJR.T_URIAGE_VL_NUKI_SUM ");
        sb.append("   , WSJR.T_TAX_VL_SUM ");
        sb.append("   , WSJR.T_URIAGE_VL_SUM ");
        sb.append("   , WSJR.P_URIAGE_VL_SUM ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSJT.KEIJO_DT ");
        sb.append("   , WSJT.TENPO_CD ");
        sb.append("   , WSJT.REGISTER_NO ");
        sb.append("   , WSJT.TRANSACTION_NO ");
        sb.append("   , SUM(WSJT.URIAGE_VL_NUKI) AS T_URIAGE_VL_NUKI_SUM ");
        sb.append("   , SUM(WSJT.TAX_VL) AS T_TAX_VL_SUM ");
        sb.append("   , SUM(WSJT.URIAGE_VL) AS T_URIAGE_VL_SUM ");
        sb.append("   , 0 AS P_URIAGE_VL_SUM ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_JIKASHOHI_TAX WSJT  ");
        sb.append(" GROUP BY ");
        sb.append("   WSJT.KEIJO_DT ");
        sb.append("   , WSJT.TENPO_CD ");
        sb.append("   , WSJT.REGISTER_NO ");
        sb.append("   , WSJT.TRANSACTION_NO ");
        
        return sb.toString();
    }
    
    private String insertWK_payment() {
        //店別レシート精算より、自家消費のレコードのみ取得する
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_JIKASHOHI_PAYMENT WSJP(  ");
        sb.append("   WSJP.KEIJO_DT ");
        sb.append("   , WSJP.TENPO_CD ");
        sb.append("   , WSJP.REGISTER_NO ");
        sb.append("   , WSJP.TRANSACTION_NO ");
        sb.append("   , WSJP.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSJP.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSJP.POS_VL ");
        sb.append("   , WSJP.SAGAKU_VL ");
        sb.append("   , WSJP.URIAGE_VL ");
        sb.append("   , WSJP.RANK ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   DTRS.KEIJO_DT ");
        sb.append("   , DTRS.TENPO_CD ");
        sb.append("   , DTRS.REGISTER_NO ");
        sb.append("   , DTRS.TRANSACTION_NO ");
        sb.append("   , DTRS.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , DTRS.SHIHARAI_SYUBETSU_SUB_CD ");
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
        sb.append("   WK_SAP_JIKASHOHI_RECEIPT WSJR ");
        sb.append("   INNER JOIN DT_TEN_RECEIPT_SEISAN DTRS ");
        sb.append("     ON WSJR.KEIJO_DT = DTRS.KEIJO_DT  ");
        sb.append("     AND WSJR.TENPO_CD = DTRS.TENPO_CD  ");
        sb.append("     AND WSJR.REGISTER_NO = DTRS.REGISTER_NO  ");
        sb.append("     AND WSJR.TRANSACTION_NO = DTRS.TRANSACTION_NO "); 
        sb.append("     AND DTRS.SHIHARAI_SYUBETSU_CD = 'MRS' ");
        sb.append(" WHERE ");
        sb.append("   DTRS.COMP_CD = '"+ COMP_CD +"' "); 
        
        return sb.toString();
    }
    
    private String mergeWK_receipt() {
        StringBuffer sb = new StringBuffer();
        sb.append(" MERGE ");
        sb.append(" INTO WK_SAP_JIKASHOHI_RECEIPT WSJR ");
        sb.append("   USING (  ");
        sb.append("     SELECT ");
        sb.append("       WSJP.KEIJO_DT ");
        sb.append("       , WSJP.TENPO_CD ");
        sb.append("       , WSJP.REGISTER_NO ");
        sb.append("       , WSJP.TRANSACTION_NO ");
        sb.append("       , SUM(WSJP.URIAGE_VL) AS P_URIAGE_VL_SUM  ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_JIKASHOHI_PAYMENT WSJP  ");
        sb.append("     GROUP BY ");
        sb.append("       WSJP.KEIJO_DT ");
        sb.append("       , WSJP.TENPO_CD ");
        sb.append("       , WSJP.REGISTER_NO ");
        sb.append("       , WSJP.TRANSACTION_NO ");
        sb.append("   ) WSJP  ");
        sb.append("     ON (  ");
        sb.append("       WSJR.KEIJO_DT = WSJP.KEIJO_DT  ");
        sb.append("       AND WSJR.TENPO_CD = WSJP.TENPO_CD  ");
        sb.append("       AND WSJR.REGISTER_NO = WSJP.REGISTER_NO  ");
        sb.append("       AND WSJR.TRANSACTION_NO = WSJP.TRANSACTION_NO ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   WSJR.P_URIAGE_VL_SUM = WSJP.P_URIAGE_VL_SUM ");
        
        return sb.toString();
    }

    private String insertWK_base() {
        StringBuffer sb = new StringBuffer();
        
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_JIKASHOHI_BASE WSJB(  ");
        sb.append("   WSJB.KEIJO_DT ");
        sb.append("   , WSJB.TENPO_CD ");
        sb.append("   , WSJB.REGISTER_NO ");
        sb.append("   , WSJB.TRANSACTION_NO ");
        sb.append("   , WSJB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSJB.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSJB.TAX_RT ");
        sb.append("   , WSJB.DAIBUNRUI2_CD ");
        sb.append("   , WSJB.SALES_TYPE ");
        sb.append("   , WSJB.R_URIAGE_VL_NUKI ");
        sb.append("   , WSJB.R_TAX_VL ");
        sb.append("   , WSJB.R_URIAGE_VL ");
        sb.append("   , WSJB.P_URIAGE_VL_BASE ");
        sb.append("   , WSJB.P_URIAGE_VL_SUM ");
        sb.append("   , WSJB.P_URIAGE_VL ");
        sb.append("   , WSJB.T_URIAGE_VL_NUKI ");
        sb.append("   , WSJB.T_TAX_VL ");
        sb.append("   , WSJB.T_URIAGE_VL ");
        sb.append("   , WSJB.ANBN_URIAGE_VL_NUKI ");
        sb.append("   , WSJB.ANBN_TAX_VL ");
        sb.append("   , WSJB.ANBN_URIAGE_VL ");
        sb.append("   , WSJB.P_RANK ");
        sb.append("   , WSJB.HASU_URIAGE_VL_NUKI ");
        sb.append("   , WSJB.HASU_TAX_VL ");
        sb.append("   , WSJB.HASU_URIAGE_VL ");
        sb.append("   , WSJB.KEKKA_URIAGE_VL_NUKI ");
        sb.append("   , WSJB.KEKKA_TAX_VL ");
        sb.append("   , WSJB.KEKKA_URIAGE_VL ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSJP.KEIJO_DT ");
        sb.append("   , WSJP.TENPO_CD ");
        sb.append("   , WSJP.REGISTER_NO ");
        sb.append("   , WSJP.TRANSACTION_NO ");
        sb.append("   , WSJP.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSJP.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSJT.TAX_RT ");
        sb.append("   , WSJT.DAIBUNRUI2_CD ");
        sb.append("   , WSJT.SALES_TYPE ");
        sb.append("   , 0 AS R_URIAGE_VL_NUKI ");
        sb.append("   , 0 AS R_TAX_VL ");
        sb.append("   , 0 AS R_URIAGE_VL ");
        sb.append("   , WSJR.P_URIAGE_VL_SUM AS P_URIAGE_VL_BASE ");
        sb.append("   , 0 AS P_URIAGE_VL_SUM ");
        sb.append("   , WSJP.URIAGE_VL AS P_URIAGE_VL ");
        sb.append("   , CASE WHEN NVL(WSJP.URIAGE_VL,0) = 0 THEN 0 ELSE WSJT.URIAGE_VL_NUKI END AS T_URIAGE_VL_NUKI ");
        sb.append("   , CASE WHEN NVL(WSJP.URIAGE_VL,0) = 0 THEN 0 ELSE WSJT.TAX_VL END AS T_TAX_VL ");
        sb.append("   , CASE WHEN NVL(WSJP.URIAGE_VL,0) = 0 THEN 0 ELSE WSJT.URIAGE_VL END AS T_URIAGE_VL ");
        sb.append("   , 0 AS ANBN_URIAGE_VL_NUKI ");
        sb.append("   , 0 AS ANBN_TAX_VL ");
        sb.append("   , 0 AS ANBN_URIAGE_VL ");
        sb.append("   , WSJP.RANK AS P_RANK ");
        sb.append("   , 0 AS HASU_URIAGE_VL_NUKI ");
        sb.append("   , 0 AS HASU_TAX_VL ");
        sb.append("   , 0 AS HASU_URIAGE_VL ");
        sb.append("   , 0 AS KEKKA_URIAGE_VL_NUKI ");
        sb.append("   , 0 AS KEKKA_TAX_VL ");
        sb.append("   , 0 AS KEKKA_URIAGE_VL  ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_JIKASHOHI_RECEIPT WSJR  ");
        sb.append("   INNER JOIN WK_SAP_JIKASHOHI_PAYMENT WSJP  ");
        sb.append("     ON WSJR.KEIJO_DT = WSJP.KEIJO_DT  ");
        sb.append("     AND WSJR.TENPO_CD = WSJP.TENPO_CD  ");
        sb.append("     AND WSJR.REGISTER_NO = WSJP.REGISTER_NO  ");
        sb.append("     AND WSJR.TRANSACTION_NO = WSJP.TRANSACTION_NO  ");
        sb.append("   INNER JOIN WK_SAP_JIKASHOHI_TAX WSJT  ");
        sb.append("     ON WSJP.KEIJO_DT = WSJT.KEIJO_DT  ");
        sb.append("     AND WSJR.TENPO_CD = WSJT.TENPO_CD  ");
        sb.append("     AND WSJR.REGISTER_NO = WSJT.REGISTER_NO  ");
        sb.append("     AND WSJR.TRANSACTION_NO = WSJT.TRANSACTION_NO "); 

        return sb.toString();
    }
    
    private String mergeWK_base() {
        StringBuffer sb = new StringBuffer();
        sb.append(" MERGE ");
        sb.append(" INTO WK_SAP_JIKASHOHI_BASE WSJB ");
        sb.append("   USING (  ");
        sb.append("     SELECT ");
        sb.append("       WSJB.KEIJO_DT ");
        sb.append("       , WSJB.TENPO_CD ");
        sb.append("       , WSJB.REGISTER_NO ");
        sb.append("       , WSJB.TRANSACTION_NO ");
        sb.append("       , SUM(WSJB.T_URIAGE_VL_NUKI) AS T_URIAGE_VL_NUKI_SUM  ");
        sb.append("       , SUM(WSJB.T_TAX_VL) AS T_TAX_VL_SUM  ");
        sb.append("       , SUM(WSJB.T_URIAGE_VL) AS T_URIAGE_VL_SUM  ");
        sb.append("       , SUM(WSJB.P_URIAGE_VL) AS P_URIAGE_VL_SUM  ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_JIKASHOHI_BASE WSJB  ");
        sb.append("     GROUP BY ");
        sb.append("       WSJB.KEIJO_DT ");
        sb.append("       , WSJB.TENPO_CD ");
        sb.append("       , WSJB.REGISTER_NO ");
        sb.append("       , WSJB.TRANSACTION_NO ");
        sb.append("   ) WSJB_SUM  ");
        sb.append("     ON (  ");
        sb.append("       WSJB.KEIJO_DT = WSJB_SUM.KEIJO_DT  ");
        sb.append("       AND WSJB.TENPO_CD = WSJB_SUM.TENPO_CD  ");
        sb.append("       AND WSJB.REGISTER_NO = WSJB_SUM.REGISTER_NO  ");
        sb.append("       AND WSJB.TRANSACTION_NO = WSJB_SUM.TRANSACTION_NO ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   WSJB.R_URIAGE_VL_NUKI = WSJB_SUM.T_URIAGE_VL_NUKI_SUM ");
        sb.append("   , WSJB.R_TAX_VL = WSJB_SUM.T_TAX_VL_SUM ");
        sb.append("   , WSJB.R_URIAGE_VL = WSJB_SUM.T_URIAGE_VL_SUM ");
        sb.append("   , WSJB.P_URIAGE_VL_SUM = WSJB_SUM.P_URIAGE_VL_SUM ");
        
        return sb.toString();
    }
    
    private String updateWK_base_anbn(){
        StringBuffer sb = new StringBuffer();
        // 
        sb.append(" UPDATE WK_SAP_JIKASHOHI_BASE WSJB ");
        sb.append(" SET ");
        sb.append("   WSJB.ANBN_URIAGE_VL_NUKI = ROUND(  ");
        sb.append("     ROUND(  ");
        sb.append("       (  ");
        sb.append("         WSJB.P_URIAGE_VL_BASE * (  ");
        sb.append("           (P_URIAGE_VL / NULLIF(P_URIAGE_VL_SUM, 0)) + (WSJB.T_URIAGE_VL / NULLIF(WSJB.R_URIAGE_VL, 0)) ");
        sb.append("         ) / 2 ");
        sb.append("       ) / (  ");
        sb.append("         1 + DECODE(WSJB.TAX_RT, 'AN', 0, WSJB.TAX_RT) / 100 ");
        sb.append("       )  ");
        sb.append("       , 1 ");
        sb.append("     ) - 0.1 ");
        sb.append("   )  ");
        sb.append("   , WSJB.ANBN_TAX_VL = ROUND(  ");
        sb.append("     ROUND(  ");
        sb.append("       WSJB.P_URIAGE_VL_BASE * (  ");
        sb.append("         (P_URIAGE_VL / NULLIF(P_URIAGE_VL_SUM, 0)) + (WSJB.T_URIAGE_VL / NULLIF(WSJB.R_URIAGE_VL, 0)) ");
        sb.append("       ) / 2 ");
        sb.append("       , 1 ");
        sb.append("     ) - 0.1 ");
        sb.append("   ) - ROUND(  ");
        sb.append("     ROUND(  ");
        sb.append("       (  ");
        sb.append("         WSJB.P_URIAGE_VL_BASE * (  ");
        sb.append("           (P_URIAGE_VL / NULLIF(P_URIAGE_VL_SUM, 0)) + (WSJB.T_URIAGE_VL / NULLIF(WSJB.R_URIAGE_VL, 0)) ");
        sb.append("         ) / 2 ");
        sb.append("       ) / (  ");
        sb.append("         1 + DECODE(WSJB.TAX_RT, 'AN', 0, WSJB.TAX_RT) / 100 ");
        sb.append("       )  ");
        sb.append("       , 1 ");
        sb.append("     ) - 0.1 ");
        sb.append("   )  ");
        sb.append("   , WSJB.ANBN_URIAGE_VL = ROUND(  ");
        sb.append("     ROUND(  ");
        sb.append("       WSJB.P_URIAGE_VL_BASE * (  ");
        sb.append("         (P_URIAGE_VL / NULLIF(P_URIAGE_VL_SUM, 0)) + (WSJB.T_URIAGE_VL / NULLIF(WSJB.R_URIAGE_VL, 0)) ");
        sb.append("       ) / 2 ");
        sb.append("       , 1 ");
        sb.append("     ) - 0.1 ");
        sb.append("   )  "); 


        return sb.toString();
    }
    
    private String mergeWK_base_rank(){
        StringBuffer sb = new StringBuffer();
        // 
        sb.append(" MERGE ");
        sb.append(" INTO WK_SAP_JIKASHOHI_BASE WSJB  ");
        sb.append("   USING (  ");
        sb.append("     SELECT ");
        sb.append("       WSJB_RANK.KEIJO_DT ");
        sb.append("       , WSJB_RANK.TENPO_CD ");
        sb.append("       , WSJB_RANK.REGISTER_NO ");
        sb.append("       , WSJB_RANK.TRANSACTION_NO ");
        sb.append("       , WSJB_RANK.SHIHARAI_SYUBETSU_CD ");
        sb.append("       , WSJB_RANK.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("       , WSJB_RANK.TAX_RT ");
        sb.append("       , WSJB_RANK.DAIBUNRUI2_CD ");
        sb.append("       , WSJB_RANK.SALES_TYPE ");
        sb.append("       , ROW_NUMBER() OVER ( ");
        sb.append("           PARTITION BY ");
        sb.append("             WSJB_RANK.KEIJO_DT ");
        sb.append("             , WSJB_RANK.TENPO_CD ");
        sb.append("             , WSJB_RANK.REGISTER_NO ");
        sb.append("             , WSJB_RANK.TRANSACTION_NO  ");
        sb.append("         ORDER BY ");
        sb.append("           ABS(WSJB_RANK.ANBN_URIAGE_VL) DESC "); 
        sb.append("           , WSJB_RANK.TAX_RT "); 
        sb.append("           , WSJB_RANK.DAIBUNRUI2_CD "); 
        sb.append("           , WSJB_RANK.SALES_TYPE "); 
        sb.append("         ) AS RANK ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_JIKASHOHI_BASE WSJB_RANK  ");
        sb.append("   ) WSJB_RANK  ");
        sb.append("     ON (  ");
        sb.append("       WSJB.KEIJO_DT = WSJB_RANK.KEIJO_DT  ");
        sb.append("       AND WSJB.TENPO_CD = WSJB_RANK.TENPO_CD  ");
        sb.append("       AND WSJB.REGISTER_NO = WSJB_RANK.REGISTER_NO  ");
        sb.append("       AND WSJB.TRANSACTION_NO = WSJB_RANK.TRANSACTION_NO ");
        sb.append("       AND WSJB.SHIHARAI_SYUBETSU_CD = WSJB_RANK.SHIHARAI_SYUBETSU_CD ");
        sb.append("       AND WSJB.SHIHARAI_SYUBETSU_SUB_CD = WSJB_RANK.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("       AND WSJB.TAX_RT = WSJB_RANK.TAX_RT ");
        sb.append("       AND WSJB.DAIBUNRUI2_CD = WSJB_RANK.DAIBUNRUI2_CD ");
        sb.append("       AND WSJB.SALES_TYPE = WSJB_RANK.SALES_TYPE ");
        sb.append("     ) WHEN MATCHED THEN UPDATE  ");
        sb.append(" SET ");
        sb.append("   WSJB.P_RANK = WSJB_RANK.RANK  ");

        return sb.toString();
    }
    
    private String mergeWK_base_hasu(){
        StringBuffer sb = new StringBuffer();
        // 
        sb.append(" MERGE ");
        sb.append(" INTO WK_SAP_JIKASHOHI_BASE WSJB  ");
        sb.append("   USING (  ");
        sb.append("     SELECT ");
        sb.append("       WSJB_ANBN.KEIJO_DT ");
        sb.append("       , WSJB_ANBN.TENPO_CD ");
        sb.append("       , WSJB_ANBN.REGISTER_NO ");
        sb.append("       , WSJB_ANBN.TRANSACTION_NO ");
        sb.append("       , SUM(WSJB_ANBN.ANBN_URIAGE_VL_NUKI) ANBN_URIAGE_VL_NUKI ");
        sb.append("       , SUM(WSJB_ANBN.ANBN_TAX_VL) ANBN_TAX_VL ");
        sb.append("       , SUM(WSJB_ANBN.ANBN_URIAGE_VL) ANBN_URIAGE_VL  ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_JIKASHOHI_BASE WSJB_ANBN  ");
        sb.append("     GROUP BY ");
        sb.append("       WSJB_ANBN.KEIJO_DT ");
        sb.append("       , WSJB_ANBN.TENPO_CD ");
        sb.append("       , WSJB_ANBN.REGISTER_NO ");
        sb.append("       , WSJB_ANBN.TRANSACTION_NO ");
        sb.append("   ) WSJB_ANBN  ");
        sb.append("     ON (  ");
        sb.append("       WSJB.KEIJO_DT = WSJB_ANBN.KEIJO_DT  ");
        sb.append("       AND WSJB.TENPO_CD = WSJB_ANBN.TENPO_CD  ");
        sb.append("       AND WSJB.REGISTER_NO = WSJB_ANBN.REGISTER_NO  ");
        sb.append("       AND WSJB.TRANSACTION_NO = WSJB_ANBN.TRANSACTION_NO ");
        sb.append("     ) ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   WSJB.HASU_URIAGE_VL_NUKI = ROUND(  ");
        sb.append("     (WSJB.P_URIAGE_VL_BASE - WSJB_ANBN.ANBN_URIAGE_VL) / (1 + DECODE(WSJB.TAX_RT, 'AN', 0, WSJB.TAX_RT) / 100) ");
        sb.append("   , 0 )  ");
        sb.append("   , WSJB.HASU_TAX_VL = (  ");
        sb.append("     WSJB.P_URIAGE_VL_BASE - WSJB_ANBN.ANBN_URIAGE_VL ");
        sb.append("   ) - ROUND(  ");
        sb.append("     (WSJB.P_URIAGE_VL_BASE - WSJB_ANBN.ANBN_URIAGE_VL) / (1 + DECODE(WSJB.TAX_RT, 'AN', 0, WSJB.TAX_RT) / 100) ");
        sb.append("   , 0 )  "); 
        sb.append("   , WSJB.HASU_URIAGE_VL = WSJB.P_URIAGE_VL_BASE - WSJB_ANBN.ANBN_URIAGE_VL ");

        return sb.toString();
    }
    
    private String updateWK_base_kekka(){
        StringBuffer sb = new StringBuffer();
        // 
        sb.append(" UPDATE WK_SAP_JIKASHOHI_BASE WSJB ");
        sb.append(" SET ");
        sb.append("   WSJB.KEKKA_URIAGE_VL_NUKI = NVL((  ");
        sb.append("     CASE  ");
        sb.append("       WHEN WSJB.P_RANK = 1  ");
        sb.append("       THEN WSJB.ANBN_URIAGE_VL_NUKI + WSJB.HASU_URIAGE_VL_NUKI  ");
        sb.append("       ELSE WSJB.ANBN_URIAGE_VL_NUKI  ");
        sb.append("       END ");
        sb.append("   ),0)  ");
        sb.append("   , WSJB.KEKKA_TAX_VL = NVL((  ");
        sb.append("     CASE  ");
        sb.append("       WHEN WSJB.P_RANK = 1  ");
        sb.append("       THEN WSJB.ANBN_TAX_VL + WSJB.HASU_TAX_VL  ");
        sb.append("       ELSE WSJB.ANBN_TAX_VL  ");
        sb.append("       END ");
        sb.append("   ),0)  ");
        sb.append("   , WSJB.KEKKA_URIAGE_VL = NVL((  ");
        sb.append("     CASE  ");
        sb.append("       WHEN WSJB.P_RANK = 1  ");
        sb.append("       THEN WSJB.ANBN_URIAGE_VL + WSJB.HASU_URIAGE_VL  ");
        sb.append("       ELSE WSJB.ANBN_URIAGE_VL  ");
        sb.append("       END "); 
        sb.append("   ),0)  "); 

        return sb.toString();
    }

    private String insertWk_jikashohi() {

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_JIKASHOHI WSJ(  ");
        sb.append("   WSJ.KEIJO_DT ");
        sb.append("   , WSJ.TENPO_CD ");
        sb.append("   , WSJ.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSJ.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSJ.TAX_RT ");
        sb.append("   , WSJ.DAIBUNRUI2_CD ");
        sb.append("   , WSJ.SALES_TYPE ");
        sb.append("   , WSJ.URIAGE_VL_NUKI ");
        sb.append("   , WSJ.TAX_VL ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSJB.KEIJO_DT ");
        sb.append("   , WSJB.TENPO_CD ");
        sb.append("   , WSJB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSJB.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSJB.TAX_RT ");
        sb.append("   , WSJB.DAIBUNRUI2_CD ");
        sb.append("   , WSJB.SALES_TYPE ");
        sb.append("   , SUM(WSJB.KEKKA_URIAGE_VL_NUKI) AS URIAGE_VL_NUKI ");
        sb.append("   , SUM(WSJB.KEKKA_TAX_VL) AS TAX_VL  ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_JIKASHOHI_BASE WSJB  ");
        sb.append(" GROUP BY ");
        sb.append("   WSJB.KEIJO_DT ");
        sb.append("   , WSJB.TENPO_CD ");
        sb.append("   , WSJB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSJB.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSJB.TAX_RT ");
        sb.append("   , WSJB.DAIBUNRUI2_CD ");
        sb.append("   , WSJB.SALES_TYPE ");

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
    
    private String truncateWK_tax() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_JIKASHOHI_TAX ");
        return sb.toString();
    }
    
    private String truncateWK_payment() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_JIKASHOHI_PAYMENT ");
        return sb.toString();
    }
    
    private String truncateWK_receipt() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_JIKASHOHI_RECEIPT ");
        return sb.toString();
    }
    
    private String truncateWK_base() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_JIKASHOHI_BASE ");
        return sb.toString();
    }

    private String truncateWK_jikashohi() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_JIKASHOHI ");
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
            DaoIf dao = new IfSapJikashohiInsertWkDao();
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