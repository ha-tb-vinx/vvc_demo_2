package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import org.apache.commons.lang.StringUtils;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiStringUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>
 * タイトル：IfSapTenpoCreditHanbaiJohoInsertWkDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 店舗クレジット販売情報 ワーク作成処理
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
public class IfSapTenpoCreditHanbaiJohoInsertWkDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810030";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 店舗クレジット販売情報 ワーク作成処理";
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    private String creditShiharaiSyubetsuCd = "";

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 店舗クレジット販売情報 ワーク作成処理を開始します。");

        PreparedStatementEx ps = null;
        ResultSet rs = null;
        
        ps = invoker.getDataBase().prepareStatement(selectCreditShiharaiSyubetsuCd());
        rs = ps.executeQuery();
        while (rs.next()) {
            creditShiharaiSyubetsuCd = creditShiharaiSyubetsuCd + "'" + rs.getString("SHIHARAI_SYUBETSU_CD") + "'," ;
        }
        creditShiharaiSyubetsuCd = StringUtils.substringBeforeLast(creditShiharaiSyubetsuCd, ",");
        
        ps = null;
        rs = null;
        invoker.infoLog(strUserId + "　：　前回の店舗クレジット販売情報ワークを削除します。");
        ps = invoker.getDataBase().prepareStatement(truncateWK());
        rs = ps.executeQuery();
        invoker.infoLog(strUserId + "　：　前回の店舗クレジット販売情報ワークを削除しました。");

        invoker.infoLog(strUserId + "　：　店舗クレジット販売情報ワークを作成します。");

        ps = null;
        rs = null;
        ps = invoker.getDataBase().prepareStatement(insertWK());
        rs = ps.executeQuery();

        invoker.infoLog(strUserId + "　：　店舗クレジット販売情報ワーク取得処理を終了します。");

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

    private String insertWK() {

        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_CREDIT_URIAGE WSCU(  ");
        sb.append("   WSCU.KEIJO_DT ");
        sb.append("   , WSCU.TENPO_CD ");
        sb.append("   , WSCU.REGISTER_NO ");
        sb.append("   , WSCU.TRANSACTION_NO ");
        sb.append("   , WSCU.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSCU.SEND_CREDIT_NO ");
        sb.append("   , WSCU.CREDIT_NO ");
        sb.append("   , WSCU.TEISEIGO_CREDIT_NO ");
        sb.append("   , WSCU.POS_VL ");
        sb.append("   , WSCU.SAGAKU_VL ");
        sb.append("   , WSCU.SHONIN_NO ");
        sb.append("   , WSCU.CREDIT_TERMINAL_ID ");
        sb.append("   , WSCU.TRACE_NO ");
        sb.append("   , WSCU.MERCHANT_CODE ");
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   WSUB.KEIJO_DT ");
        sb.append("   , WSUB.TENPO_CD ");
        sb.append("   , WSUB.REGISTER_NO ");
        sb.append("   , WSUB.TRANSACTION_NO ");
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TRIM(WSUB.TEISEIGO_CREDIT_NO) IS NOT NULL  ");
        sb.append("     THEN RPAD(NVL(WSUB.TEISEIGO_CREDIT_NO, ' '), 20, ' ')  ");
        sb.append("     ELSE RPAD(NVL(WSUB.CREDIT_NO, ' '), 20, ' ')  ");
        sb.append("     END AS SEND_CREDIT_NO ");
        sb.append("   , WSUB.CREDIT_NO ");
        sb.append("   , WSUB.TEISEIGO_CREDIT_NO ");
        sb.append("   , SUM(WSUB.POS_VL) ");
        sb.append("   , SUM(WSUB.SAGAKU_VL)  ");
        sb.append("   , WSUB.SHONIN_NO ");
        sb.append("   , WSUB.CREDIT_TERMINAL_ID ");
        sb.append("   , WSUB.TRACE_NO ");
        sb.append("   , WSUB.MERCHANT_CODE ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_URIAGE_BASE WSUB  ");
        sb.append(" WHERE ");
        sb.append("   WSUB.SHIHARAI_SYUBETSU_CD IN ( " + creditShiharaiSyubetsuCd +")  ");
        sb.append(" GROUP BY ");
        sb.append("   WSUB.KEIJO_DT ");
        sb.append("   , WSUB.TENPO_CD ");
        sb.append("   , WSUB.REGISTER_NO ");
        sb.append("   , WSUB.TRANSACTION_NO ");  
        sb.append("   , WSUB.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , CASE  ");
        sb.append("     WHEN TRIM(WSUB.TEISEIGO_CREDIT_NO) IS NOT NULL  ");
        sb.append("     THEN RPAD(NVL(WSUB.TEISEIGO_CREDIT_NO, ' '), 20, ' ')  ");
        sb.append("     ELSE RPAD(NVL(WSUB.CREDIT_NO, ' '), 20, ' ')  ");
        sb.append("     END ");
        sb.append("   , WSUB.CREDIT_NO ");
        sb.append("   , WSUB.TEISEIGO_CREDIT_NO ");
        sb.append("   , WSUB.SHONIN_NO ");
        sb.append("   , WSUB.CREDIT_TERMINAL_ID ");
        sb.append("   , WSUB.TRACE_NO ");
        sb.append("   , WSUB.MERCHANT_CODE ");

        return sb.toString();
    }

    private String truncateWK() {
        StringBuffer sb = new StringBuffer();
        sb.append(" TRUNCATE TABLE WK_SAP_CREDIT_URIAGE ");
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
            DaoIf dao = new IfSapTenpoCreditHanbaiJohoInsertWkDao();
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