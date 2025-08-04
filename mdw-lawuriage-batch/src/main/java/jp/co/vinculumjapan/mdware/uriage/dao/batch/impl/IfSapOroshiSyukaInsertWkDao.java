package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

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
 * タイトル：IfSapOroshiSyukaInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 卸出荷 ワーク作成処理
 * </p>
 * <p>
 * 著作権：Copyright (c) 2017
 * </p>
 * <p>
 * 会社名：VINX
 * </p>
 * 
 * @author VINX
 * @version 1.00 (2017.1.9) A.Narita FIVIMART対応
 */
public class IfSapOroshiSyukaInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810090";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 卸出荷 ワーク作成処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** 送信時の対象計上日開始日（SAP結合機→SAP本番機への切替用） */
    private static final String SAP_SEND_KEIJO_DT_START_DT = FiResorceUtility.getPropertie("SAP_SEND_KEIJO_DT_START_DT");

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 卸出荷 ワーク作成処理を開始します。");

        PreparedStatementEx ps = null;

        /** 前回の卸出荷データWK削除処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　前回のワークを削除します。");
        // 卸出荷データを削除するSQL
        ps = invoker.getDataBase().prepareStatement(truncateWK());
        // SQLを実行する
        ps.executeQuery();
        // ログを出力
        invoker.infoLog(strUserId + "　：　前回のワークを削除しました。");

        /** 卸出荷データWK更新処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ワークを作成します。");

        ps = null;
        invoker.infoLog(strUserId + "　：　1. C ヘッダー部");
        ps = invoker.getDataBase().prepareStatement(insertWK_C_Header());
        ps.executeQuery();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　2. C 明細部");
        ps = invoker.getDataBase().prepareStatement(insertWK_C_Meisai());
        ps.executeQuery();
        
        ps = null;
        invoker.infoLog(strUserId + "　：　3. D ヘッダー部");
        ps = invoker.getDataBase().prepareStatement(insertWK_D_Header());
        ps.executeQuery();
        
        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　SAP IF 卸出荷 ワーク作成処理を終了します。");
    }
    
    private String insertWKColumn(){
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_OROSHI_SYUKA(  ");
        sb.append("   HENKOU_KB ");
        sb.append("   , REF_1 ");
        sb.append("   , MEISAI_KB ");
        sb.append("   , OROSHISAKI_CD ");
        sb.append("   , TAX_RT ");
        sb.append("   , DAIBUNRUI2_CD ");
        sb.append("   , SAKUSEI_DT ");
        sb.append("   , SYUKA_DT ");
        sb.append("   , REF_2 ");
        sb.append("   , BAIKA_ZEINUKI_VL ");
        sb.append("   , BAIKA_ZEIGAKU_VL ");
        sb.append("   , SYUKA_TENPO_CD ");
        sb.append("   , INVOICE_AUTONO_NB ");
        sb.append("   , INVOICE_AUTONO_KB ");
        sb.append("   , INVOICE_AUTONO_RB ");
        sb.append("   , INVOICE_KEYINNO_NB ");
        sb.append("   , INVOICE_KEYINNO_KB ");
        sb.append("   , INVOICE_KEYINNO_RB ");
        sb.append(" )  ");
        return sb.toString();
    }
    
    //下記、計上済伝票の抽出条件は、WkOroshiSyukaDenpyoCreateDao最新ver.を参照すること。
    //最終的にSYORI_KB = '2'（計上済）となったデータを抽出する。

    private String insertWK_C_Header() {
        StringBuffer sb = new StringBuffer();
        sb.append(insertWKColumn());
        sb.append(" SELECT ");
        sb.append("   '0' AS HENKOU_KB ");
        sb.append("   , DOSD.CYOHYO_DENPYO_NO || '06' || SUBSTR(TO_CHAR(DOSD.DENPYO_EDA_RB, 999),-1)  AS REF_1 ");
        sb.append("   , '0' AS MEISAI_KB ");
        sb.append("   , DOSD.OROSHISAKI_CD ");
        sb.append("   , ' ' AS TAX_RT ");
        sb.append("   , ' ' AS DAIBUNRUI2_CD ");
        sb.append("   , '"+BATCH_DT+"' AS SAKUSEI_DT ");
        sb.append("   , DOSD.SYUKA_DT ");
        sb.append("   , ' ' AS REF_2 ");
        sb.append("   , CASE WHEN NVL(DOSD.SUMBAIKA_ZEINUKI_VL, 0) = 0 THEN 1 ELSE DOSD.SUMBAIKA_ZEINUKI_VL END AS BAIKA_ZEINUKI_VL ");//ヘッダ上0VNDの場合は1VNDに変換
        sb.append("   , DOSD.SUMBAIKA_ZEIGAKU_VL ");
        sb.append("   , DOSD.SYUKA_TENPO_CD ");
        sb.append("   , DOSD.INVOICE_AUTONO_NB ");
        sb.append("   , DOSD.INVOICE_AUTONO_KB ");
        sb.append("   , DOSD.INVOICE_AUTONO_RB ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_NB, DOSD.OROSHI_VAT_KEYINNO_NB) ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_KB, DOSD.OROSHI_VAT_KEYINNO_KB) ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_RB, DOSD.OROSHI_VAT_KEYINNO_RB) ");
        sb.append(" FROM ");
        sb.append("   DT_OROSHI_SYUKA_DENPYO DOSD  ");
        sb.append("   INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS  ");
        sb.append("     ON DOSD.COMP_CD = DZSS.COMP_CD  ");
        sb.append("     AND DOSD.SYUKA_DT = DZSS.KEIJO_DT  ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2'  ");
        sb.append(" WHERE ");
        sb.append("   DOSD.COMP_CD = '"+COMP_CD+"'  ");
        sb.append("   AND DOSD.DENPYO_KB = '01'  ");//出荷
        sb.append("   AND DOSD.SYORI_STATUS_KB = '2'  ");
        sb.append("   AND DOSD.DELETE_FG = '0'  ");
        sb.append("   AND DOSD.MOTO_DENPYO_NO IS NULL ");
        sb.append("   AND DOSD.SYUKA_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");

        return sb.toString();
    }
    
    private String insertWK_C_Meisai() {
        StringBuffer sb = new StringBuffer();
        sb.append(insertWKColumn());
        sb.append(" SELECT ");
        sb.append("   '0' AS HENKOU_KB ");
        sb.append("   , DOSD.CYOHYO_DENPYO_NO || '06' || SUBSTR(TO_CHAR(DOSD.DENPYO_EDA_RB, 999),-1) AS REF_1 ");
        sb.append("   , '1' AS MEISAI_KB ");
        sb.append("   , ' ' AS OROSHISAKI_CD ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TRIM(OROSHI_ZEI_KB) IS NOT NULL ");
        sb.append("     THEN CASE  ");
        sb.append("       WHEN TO_NUMBER(TRIM(OROSHI_ZEI_KB)) = 3  ");
        sb.append("       THEN 'AN'  ");
        sb.append("       ELSE TRIM(TO_CHAR(NVL(DOSM.OROSHI_TAX_RT, 0), '00'))  ");
        sb.append("       END  ");
        sb.append("     ELSE CASE  ");
        sb.append("       WHEN TO_NUMBER(TRIM(KOURI_ZEI_KB)) = 3  ");
        sb.append("       THEN 'AN'  ");
        sb.append("       ELSE TRIM(TO_CHAR(NVL(DOSM.KOURI_TAX_RT, 0), '00'))  ");
        sb.append("       END  ");
        sb.append("     END AS TAX_RT ");
        sb.append("   , RFST.DAIBUNRUI2_CD ");
        sb.append("   , '"+BATCH_DT+"' AS SAKUSEI_DT ");
        sb.append("   , DOSD.SYUKA_DT ");
        sb.append("   , ' ' AS REF_2 ");
        sb.append("   , SUM(DOSM.BAIKA_ZEINUKI_VL) ");
        sb.append("   , SUM(DOSM.BAIKA_ZEIGAKU_VL) ");
        sb.append("   , DOSD.SYUKA_TENPO_CD ");
        sb.append("   , DOSD.INVOICE_AUTONO_NB ");
        sb.append("   , DOSD.INVOICE_AUTONO_KB ");
        sb.append("   , DOSD.INVOICE_AUTONO_RB ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_NB, DOSD.OROSHI_VAT_KEYINNO_NB) ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_KB, DOSD.OROSHI_VAT_KEYINNO_KB) ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_RB, DOSD.OROSHI_VAT_KEYINNO_RB) ");
        sb.append(" FROM ");
        sb.append("   DT_OROSHI_SYUKA_DENPYO DOSD  ");
        sb.append("   INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS  ");
        sb.append("     ON DOSD.COMP_CD = DZSS.COMP_CD  ");
        sb.append("     AND DOSD.SYUKA_DT = DZSS.KEIJO_DT  ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2'  ");
        sb.append("   INNER JOIN DT_OROSHI_SYUKA_MEI DOSM  ");
        sb.append("     ON DOSD.COMP_CD = DOSM.COMP_CD  ");
        sb.append("     AND DOSD.SEQ_NB = DOSM.SEQ_NB  ");
        sb.append("   INNER JOIN R_FI_SYOHIN_TAIKEI RFST  ");
        sb.append("     ON DOSM.COMP_CD = RFST.COMP_CD  ");
        sb.append("     AND DOSM.BUNRUI5_CD = RFST.BUNRUI5_CD  ");
        sb.append("     AND RFST.YUKO_DT = (  ");
        sb.append("       SELECT ");
        sb.append("         MAX(RFST2.YUKO_DT)  ");
        sb.append("       FROM ");
        sb.append("         R_FI_SYOHIN_TAIKEI RFST2  ");
        sb.append("       WHERE ");
        sb.append("         DOSM.COMP_CD = RFST2.COMP_CD  ");
        sb.append("         AND DOSM.BUNRUI5_CD = RFST2.BUNRUI5_CD  ");
        sb.append("         AND DOSD.SYUKA_DT >= RFST2.YUKO_DT ");
        sb.append("     )  ");
        sb.append(" WHERE ");
        sb.append("   DOSD.COMP_CD = '"+COMP_CD+"'  ");
        sb.append("   AND DOSD.DENPYO_KB = '01'  ");//出荷
        sb.append("   AND DOSD.SYORI_STATUS_KB = '2'  ");
        sb.append("   AND DOSD.DELETE_FG = '0'  ");
        sb.append("   AND DOSD.OROSHI_CANCEL_KB IS NULL ");
        sb.append("   AND DOSD.SYUKA_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");
        sb.append(" GROUP BY ");
        sb.append("   DOSD.SYUKA_DT ");
        sb.append("   , DOSD.INVOICE_AUTONO_NB ");
        sb.append("   , DOSD.INVOICE_AUTONO_KB ");
        sb.append("   , DOSD.INVOICE_AUTONO_RB ");
        sb.append("   , DOSD.CYOHYO_DENPYO_NO || '06' || SUBSTR(TO_CHAR(DOSD.DENPYO_EDA_RB, 999),-1) ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TRIM(OROSHI_ZEI_KB) IS NOT NULL  ");
        sb.append("     THEN CASE  ");
        sb.append("       WHEN TO_NUMBER(TRIM(OROSHI_ZEI_KB)) = 3  ");
        sb.append("       THEN 'AN'  ");
        sb.append("       ELSE TRIM(TO_CHAR(NVL(DOSM.OROSHI_TAX_RT, 0), '00'))  ");
        sb.append("       END  ");
        sb.append("     ELSE CASE  ");
        sb.append("       WHEN TO_NUMBER(TRIM(KOURI_ZEI_KB)) = 3  ");
        sb.append("       THEN 'AN'  ");
        sb.append("       ELSE TRIM(TO_CHAR(NVL(DOSM.KOURI_TAX_RT, 0), '00'))  ");
        sb.append("       END  ");
        sb.append("     END ");
        sb.append("   , DOSD.SYUKA_TENPO_CD ");
        sb.append("   , RFST.DAIBUNRUI2_CD ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_NB, DOSD.OROSHI_VAT_KEYINNO_NB) ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_KB, DOSD.OROSHI_VAT_KEYINNO_KB) ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_RB, DOSD.OROSHI_VAT_KEYINNO_RB) ");

        return sb.toString();
    }
    
    private String insertWK_D_Header() {
        StringBuffer sb = new StringBuffer();
        sb.append(insertWKColumn());
        sb.append(" SELECT ");
        sb.append("   '1' AS HENKOU_KB ");
        sb.append("   , DOSD.CYOHYO_DENPYO_NO || '06' || SUBSTR(TO_CHAR(DOSD.DENPYO_EDA_RB, 999),-1) AS REF_1 ");
        sb.append("   , '0' AS MEISAI_KB ");
        sb.append("   , DOSD.OROSHISAKI_CD ");
        sb.append("   , ' ' AS TAX_RT ");
        sb.append("   , ' ' AS DAIBUNRUI2_CD ");
        sb.append("   , '"+BATCH_DT+"' AS SAKUSEI_DT ");
        sb.append("   , DOSD.SYUKA_DT ");
        sb.append("   , ' ' AS REF_2 ");
        sb.append("   , CASE WHEN NVL(DOSD.SUMBAIKA_ZEINUKI_VL, 0) = 0 THEN 1 ELSE DOSD.SUMBAIKA_ZEINUKI_VL END AS BAIKA_ZEINUKI_VL ");//ヘッダ上0VNDの場合は1VNDに変換
        sb.append("   , DOSD.SUMBAIKA_ZEIGAKU_VL ");
        sb.append("   , DOSD.SYUKA_TENPO_CD ");
        sb.append("   , DOSD.INVOICE_AUTONO_NB ");
        sb.append("   , DOSD.INVOICE_AUTONO_KB ");
        sb.append("   , DOSD.INVOICE_AUTONO_RB ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_NB, DOSD.OROSHI_VAT_KEYINNO_NB) ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_KB, DOSD.OROSHI_VAT_KEYINNO_KB) ");
        sb.append("   , NVL(DOSD.INVOICE_KEYINNO_RB, DOSD.OROSHI_VAT_KEYINNO_RB) ");
        sb.append(" FROM ");
        sb.append("   DT_OROSHI_SYUKA_DENPYO DOSD  ");
        sb.append("   INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS  ");
        sb.append("     ON DOSD.COMP_CD = DZSS.COMP_CD  ");
        sb.append("     AND DOSD.SYUKA_DT = DZSS.KEIJO_DT  ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2'  ");
        sb.append(" WHERE ");
        sb.append("   DOSD.COMP_CD = '"+COMP_CD+"'  ");
        sb.append("   AND DOSD.DENPYO_KB = '01'  ");//出荷
        sb.append("   AND DOSD.SYORI_STATUS_KB = '2'  ");
        sb.append("   AND DOSD.DELETE_FG = '0'  ");
        sb.append("   AND DOSD.OROSHI_CANCEL_KB = '0' ");
        sb.append("   AND DOSD.SYUKA_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");

        return sb.toString();
    }

    private String truncateWK() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_OROSHI_SYUKA ");
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
            DaoIf dao = new IfSapOroshiSyukaInsertWkDao();
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