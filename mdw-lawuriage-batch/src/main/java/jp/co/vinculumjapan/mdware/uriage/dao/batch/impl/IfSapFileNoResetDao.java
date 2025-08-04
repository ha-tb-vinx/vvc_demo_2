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
 * タイトル：IfSapFileNoResetDao クラス
 * </p>
 * <p>
 * 説明：SAP IF ファイル名連番リセット処理
 * </p>
 * <p>
 * 著作権：Copyright (c) 2016
 * </p>
 * <p>
 * 会社名：VINX
 * </p>
 * 
 * @author VINX
 * @version 1.00 (2016.7.4) FIVIMART対応
 */
public class IfSapFileNoResetDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810000";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF ファイル名連番リセット処理";
    /** システム日付 */
    private static final String SYS_DATE = FiResorceUtility.getDBServerTime();

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF ファイル名連番リセット処理を開始します。");

        PreparedStatementEx ps_update = null;
        // クレジット返金データ取得処理SQL
        ps_update = invoker.getDataBase().prepareStatement(updateSapFileSeq());
        // SQLを実行する
        ps_update.executeQuery();

        /** 終了処理 */
        invoker.infoLog(strUserId + "　：　SAP IF ファイル名連番リセット処理処理を終了します。");

    }

    private String updateSapFileSeq() {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE R_SAP_FILE_SEQ_URIAGE RSFS ");
        sb.append(" SET  ");
        sb.append("   RSFS.FILENO = 1 ");
        sb.append("   , RSFS.UPDATE_USER_ID = '"+ BATCH_ID +"' ");
        sb.append("   , RSFS.UPDATE_TS = '"+ SYS_DATE +"' ");
        
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
            DaoIf dao = new IfSapFileNoResetDao();
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