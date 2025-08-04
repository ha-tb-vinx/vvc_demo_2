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
 * タイトル：IfSapSeisanJohoInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 精算情報 ワーク作成処理
 * </p>
 * <p>
 * 著作権：Copyright (c) 2016
 * </p>
 * <p>
 * 会社名：VINX
 * </p>
 * 
 * @author VINX
 * @version 1.00  (2016.7.4) A.Narita FIVIMART対応
 */
public class IfSapSeisanJohoInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810041";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 精算情報 ワーク作成処理";
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** 送信時の対象計上日開始日（SAP結合機→SAP本番機への切替用） */
    private static final String SAP_SEND_KEIJO_DT_START_DT = FiResorceUtility.getPropertie("SAP_SEND_KEIJO_DT_START_DT");

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 精算情報 ワーク作成処理を開始します。");

        PreparedStatementEx ps = null;
        ResultSet rs = null;

        invoker.infoLog(strUserId + "　：　前回の精算情報ワークを削除します。");
        ps = invoker.getDataBase().prepareStatement(truncateWK());
        rs = ps.executeQuery();
        invoker.infoLog(strUserId + "　：　前回の精算情報ワークを削除しました。");

        /** 精算情報ワーク取得処理 */
        invoker.infoLog(strUserId + "　：　精算情報ワークを作成します。");
        rs = null;
        ps = invoker.getDataBase().prepareStatement(insertWk_POS());
        rs = ps.executeQuery();
        invoker.getDataBase().commit();

        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　精算情報ワーク作成処理を終了します。");
    }

    private String insertWk_POS() {

        StringBuffer sb = new StringBuffer();

        //単品精算データより、精算情報ワークを作成する
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_SEISAN WSS( ");
        sb.append("   WSS.KEIJO_DT ");
        sb.append("   , WSS.TENPO_CD ");
        sb.append("   , WSS.DAIBUNRUI2_CD ");
        sb.append("   , WSS.TAX_RT ");
        sb.append("   , WSS.URIAGE_VL_NUKI ");
        sb.append("   , WSS.TAX_VL ");
        sb.append("   , WSS.URIAGE_VL ");
        sb.append(" ) ");
        sb.append(" SELECT ");
        sb.append("   DTS.KEIJO_DT ");
        sb.append("   , DTS.TENPO_CD ");
        sb.append("   , DTS.DAIBUNRUI2_CD ");
        sb.append("   , CASE  ");
        sb.append("     WHEN DTS.ZEI_KB = '3'  ");
        sb.append("     THEN 'AN'  ");
        sb.append("     ELSE TRIM(TO_CHAR(DTS.TAX_RT, '00'))  ");
        sb.append("     END AS TAX_RT ");
        sb.append("   , SUM(NVL(DTS.URIAGE_NUKI_SOURI_VL,0)) ");//集計対象は売上額のみとし、返品額は含めない。
        sb.append("   , SUM(NVL(DTS.URIAGE_ZEI_VL,0)) ");//集計対象は売上額のみとし、返品額は含めない。
        sb.append("   , SUM(NVL(DTS.URIAGE_SOURI_VL,0)) ");//集計対象は売上額のみとし、返品額は含めない。
        sb.append(" FROM ");
        sb.append("   DT_TANPIN_SEISAN DTS ");
        sb.append("   INNER JOIN ");
        sb.append("   DT_ZENTEN_SEISAN_STATE DZSS");
        sb.append("     ON DTS.COMP_CD = DZSS.COMP_CD ");
        sb.append("     AND DTS.KEIJO_DT = DZSS.KEIJO_DT ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2' ");//精算済のみ作成
        sb.append(" WHERE ");
        sb.append("   DTS.COMP_CD = '"+COMP_CD+"'");
        sb.append("   AND DTS.URIAGE_KB = '1' ");//1:POSのみ。2:自家消費、3:卸は除く
        sb.append("   AND DTS.KEIJO_DT >= '" + SAP_SEND_KEIJO_DT_START_DT + "' ");
        sb.append(" GROUP BY ");
        sb.append("   DTS.KEIJO_DT ");
        sb.append("   , DTS.TENPO_CD ");
        sb.append("   , DTS.DAIBUNRUI2_CD ");
        sb.append("   , CASE  ");
        sb.append("     WHEN DTS.ZEI_KB = '3'  ");
        sb.append("     THEN 'AN'  ");
        sb.append("     ELSE TRIM(TO_CHAR(DTS.TAX_RT, '00'))  ");
        sb.append("     END  ");

        return sb.toString();
    }

    private String truncateWK() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_SEISAN ");
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
            DaoIf dao = new IfSapSeisanJohoInsertWkDao();
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