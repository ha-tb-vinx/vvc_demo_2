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
 * タイトル：IfSapGenkinHenpinInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 現金返品 ワーク作成処理
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
public class IfSapGenkinHenpinInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810070";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 現金返品 ワーク作成処理";
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        invoker.infoLog(strUserId + "　：　SAP IF 現金返品 ワーク作成処理を開始します。");

        PreparedStatementEx ps = null;

        invoker.infoLog(strUserId + "　：　前回のワークを削除します。");
        
        ps = invoker.getDataBase().prepareStatement(truncateWK());
        ps.executeQuery();

        invoker.infoLog(strUserId + "　：　前回のワークを削除しました。");

        invoker.infoLog(strUserId + "　：　ワークを作成します。");
        
        ps = null;
        ps = invoker.getDataBase().prepareStatement(insertWK());
        ps.executeQuery();
        invoker.getDataBase().commit();
        
        invoker.infoLog(strUserId + "　：　ワーク作成を終了します。");
    }
    
    private String insertWK(){
        StringBuffer sb = new StringBuffer();
        // 
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_GENKIN_HENPIN WSHG(  ");
        sb.append("   WSHG.KEIJO_DT ");
        sb.append("   , WSHG.TENPO_CD ");
        sb.append("   , WSHG.REGISTER_NO ");
        sb.append("   , WSHG.TRANSACTION_NO ");
        sb.append("   , WSHG.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSHG.TAX_RT ");
        sb.append("   , WSHG.URIAGE_VL_NUKI ");
        sb.append("   , WSHG.TAX_VL ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSHB.KEIJO_DT ");
        sb.append("   , WSHB.TENPO_CD ");
        sb.append("   , WSHB.REGISTER_NO ");
        sb.append("   , WSHB.TRANSACTION_NO ");
        sb.append("   , WSHB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSHB.TAX_RT ");
        sb.append("   , SUM(WSHB.KEKKA_URIAGE_VL_NUKI) AS URIAGE_VL_NUKI ");
        sb.append("   , SUM(WSHB.KEKKA_TAX_VL) AS TAX_VL  ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_HENPIN_BASE WSHB  ");
        sb.append("   INNER JOIN DT_ZENTEN_SEISAN_STATE DZSS");
        sb.append("     ON DZSS.COMP_CD = '"+COMP_CD+"' ");
        sb.append("     AND WSHB.KEIJO_DT = DZSS.KEIJO_DT ");
        sb.append("     AND DZSS.SEISAN_STATE_FG = '2' ");//精算済のみ作成
        sb.append(" WHERE ");
        sb.append("   WSHB.SHIHARAI_SYUBETSU_GROUP_CD <> '02'  ");
        sb.append("   AND WSHB.SHIHARAI_SYUBETSU_CD <> 'MRS'  ");
        sb.append(" GROUP BY ");
        sb.append("   WSHB.KEIJO_DT ");
        sb.append("   , WSHB.TENPO_CD ");
        sb.append("   , WSHB.REGISTER_NO ");
        sb.append("   , WSHB.TRANSACTION_NO ");
        sb.append("   , WSHB.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSHB.TAX_RT ");

        return sb.toString();
    }
    
    private String truncateWK() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_GENKIN_HENPIN ");
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
            DaoIf dao = new IfSapGenkinHenpinInsertWkDao();
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