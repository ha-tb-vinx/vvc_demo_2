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
 * タイトル：IfSapIsanNyuryokuInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 違算入力 ワーク作成処理
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
public class IfSapIsanNyuryokuInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810010";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 違算入力 ワーク作成処理";
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** 送信時の対象計上日開始日（SAP結合機→SAP本番機への切替用） */
    private static final String SAP_SEND_KEIJO_DT_START_DT = FiResorceUtility.getPropertie("SAP_SEND_KEIJO_DT_START_DT");
    
    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 違算入力 ワーク作成処理を開始します。");

        PreparedStatementEx ps_trancate = null;
        PreparedStatementEx ps_insert = null;

        /** 前回の違算入力データWK削除処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　前回の違算入力ワークを削除します。");
        // 違算入力データを削除するSQL
        ps_trancate = invoker.getDataBase().prepareStatement(truncateWK());
        // SQLを実行する
        ps_trancate.executeQuery();
        // ログを出力
        invoker.infoLog(strUserId + "　：　前回の違算入力ワークを削除しました。");

        /** 違算入力データWK更新処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　違算入力ワークを作成します。");

        // 違算入力データ作成処理SQL
        ps_insert = invoker.getDataBase().prepareStatement(insertWK());
        // SQLを実行する
        ps_insert.executeQuery();
        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　SAP IF 違算入力 ワーク作成処理を終了します。");
    }

    private String insertWK() {
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_ISAN(  ");
        sb.append("   KEIJO_DT ");
        sb.append("   , TENPO_CD ");
        sb.append("   , URIAGE_VL ");
        sb.append("   , ARIDAKA_VL ");
        sb.append("   , ISAN_PLUS ");
        sb.append("   , ISAN_MINUS ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   DTIS.KEIJO_DT ");
        sb.append("   , DTIS.TENPO_CD ");
        sb.append("   , DTIS.URIAGE_VL ");
        sb.append("   , DTIS.ARIDAKA_VL ");
        sb.append("   , DTIS.ISAN_PLUS ");
        sb.append("   , DTIS.ISAN_MINUS  ");
        sb.append(" FROM ");
        sb.append("   (  ");
        sb.append("     SELECT ");
        sb.append("       DTIS.KEIJO_DT ");
        sb.append("       , DTIS.TENPO_CD ");
        sb.append("       , SUM(DTIS.URIAGE_VL) AS URIAGE_VL ");
        sb.append("       , SUM(DTIS.ARIDAKA_VL) AS ARIDAKA_VL ");
        sb.append("       , SUM(DTIS.ISAN_PLUS) AS ISAN_PLUS ");
        sb.append("       , SUM(DTIS.ISAN_MINUS) AS ISAN_MINUS  ");
        sb.append("     FROM ");
        sb.append("       (  ");
        sb.append("         SELECT ");
        sb.append("           DTIS.KEIJO_DT ");
        sb.append("           , DTIS.TENPO_CD ");
        sb.append("           , NVL(DTIS.TEISEIGO_URIAGE_VL, NVL(DTIS.URIAGE_VL, 0)) AS URIAGE_VL ");//訂正後売上金額を優先
        sb.append("           , DTIS.ARIDAKA_VL ");
        sb.append("           , CASE  ");
        sb.append("             WHEN NVL(DTIS.ARIDAKA_VL, 0) - NVL(DTIS.TEISEIGO_URIAGE_VL, NVL(DTIS.URIAGE_VL, 0)) > 0  ");
        sb.append("             THEN NVL(DTIS.ARIDAKA_VL, 0) - NVL(DTIS.TEISEIGO_URIAGE_VL, NVL(DTIS.URIAGE_VL, 0))  ");
        sb.append("             ELSE 0  ");
        sb.append("             END AS ISAN_PLUS ");
        sb.append("           , CASE  ");
        sb.append("             WHEN NVL(DTIS.TEISEIGO_URIAGE_VL, NVL(DTIS.URIAGE_VL, 0)) - NVL(DTIS.ARIDAKA_VL, 0) > 0  ");
        sb.append("             THEN NVL(DTIS.TEISEIGO_URIAGE_VL, NVL(DTIS.URIAGE_VL, 0)) - NVL(DTIS.ARIDAKA_VL, 0)  ");
        sb.append("             ELSE 0  ");
        sb.append("             END AS ISAN_MINUS  ");
        sb.append("         FROM ");
        sb.append("           DT_TEN_ISAN_SEISAN DTIS  ");
        sb.append("           INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS  ");
        sb.append("             ON DTIS.COMP_CD = DZSS.COMP_CD  ");
        sb.append("             AND DTIS.KEIJO_DT = DZSS.KEIJO_DT  ");
        sb.append("             AND DZSS.SEISAN_STATE_FG = '2'  ");//精算済のみ作成
        sb.append("         WHERE ");
        sb.append("           DTIS.COMP_CD = '"+COMP_CD +"'  ");
        sb.append("           AND DTIS.KEIJO_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");
        sb.append("       ) DTIS  ");
        sb.append("     GROUP BY ");
        sb.append("       DTIS.KEIJO_DT ");
        sb.append("       , DTIS.TENPO_CD ");
        sb.append("   ) DTIS  ");
        sb.append(" WHERE ");
        sb.append("   DTIS.ARIDAKA_VL >= 0  ");
        sb.append("   AND DTIS.ARIDAKA_VL + DTIS.ISAN_PLUS + DTIS.ISAN_MINUS > 0 "); 

        return sb.toString();
    }

    private String truncateWK() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_ISAN ");
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
            DaoIf dao = new IfSapIsanNyuryokuInsertWkDao();
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