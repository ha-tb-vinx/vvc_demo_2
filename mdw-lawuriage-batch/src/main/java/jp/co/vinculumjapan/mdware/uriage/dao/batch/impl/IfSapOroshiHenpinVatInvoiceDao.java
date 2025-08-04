package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

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
 * タイトル: SAP IF 卸返品VATINVOICE取込 クラス
 * </p>
 * <p>
 * 説明: 卸返品VATINVOICEをファイルからloadする
 * </p>
 * 
 * @author VINX
 * @Version 1.00 (2016.11.06) A.Narita FIVIMART対応
 */

public class IfSapOroshiHenpinVatInvoiceDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB820041";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 卸返品VATINVOICE 取込処理";
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** システム日付 */
    private static final String SYS_DATE = FiResorceUtility.getDBServerTime();
    /** チェック項目：正常 */
    private final String CHK_00 = "00";
    /**
     * 
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        invoker.infoLog(strUserId + "　：　SAP IF 卸返品VATINVOICE 取込処理を開始します。");

        PreparedStatementEx ps = null;

        // 取込処理は、変更区分「D」→「C」→「M」の順で行う

        invoker.infoLog(strUserId + "　：　卸出荷伝票データ 変更区分「D」分の反映を開始します。");

        ps = null;
        ps = invoker.getDataBase().prepareStatement(mergeDenpyo("D"));
        ps.executeQuery();
        invoker.infoLog(strUserId + "　：　卸出荷伝票データ 変更区分「D」分の反映を終了しました。");

        invoker.infoLog(strUserId + "　：　卸出荷伝票データ 変更区分「C」分の反映を開始します。");
        ps = null;
        ps = invoker.getDataBase().prepareStatement(mergeDenpyo("C"));
        ps.executeQuery();
        invoker.infoLog(strUserId + "　：　卸出荷伝票データ 変更区分「C」分の反映を終了しました。");

        invoker.infoLog(strUserId + "　：　卸出荷伝票データ 変更区分「M」分の反映を開始します。");
        ps = null;
        ps = invoker.getDataBase().prepareStatement(mergeDenpyo("M"));
        ps.executeQuery();
        invoker.infoLog(strUserId + "　：　卸出荷伝票データ 変更区分「M」分の反映を終了しました。");
        
        //★卸先のVAT番号は税務帳票に移送するかは、未確定

//        invoker.infoLog(strUserId + "　：　売上INVOICE管理ヘッダー 変更区分「D」分の反映を開始します。");
//        ps = null;
//        ps = invoker.getDataBase().prepareStatement(mergeInvoice("D"));
//        ps.executeQuery();
//        invoker.infoLog(strUserId + "　：　売上INVOICE管理ヘッダー 変更区分「D」分の反映を終了しました。");
//
//        invoker.infoLog(strUserId + "　：　売上INVOICE管理ヘッダー 変更区分「C」分の反映を開始します。");
//        ps = null;
//        ps = invoker.getDataBase().prepareStatement(mergeInvoice("C"));
//        ps.executeQuery();
//        invoker.infoLog(strUserId + "　：　売上INVOICE管理ヘッダー 変更区分「C」分の反映を終了しました。");
//
//        invoker.infoLog(strUserId + "　：　売上INVOICE管理ヘッダー 変更区分「M」分の反映を開始します。");
//        ps = null;
//        ps = invoker.getDataBase().prepareStatement(mergeInvoice("M"));
//        ps.executeQuery();
//        invoker.infoLog(strUserId + "　：　売上INVOICE管理ヘッダー 変更区分「M」分の反映を終了しました。");

        invoker.infoLog(strUserId + "　：　SAP IF 卸返品VATINVOICE 取込処理を終了します。");

    }

    private String mergeDenpyo(String henkouKb) {
        // 変更区分「D」の場合は、インボイス関連項目をNULLに更新
        // 変更区分「C」「M」の場合は、インボイス関連項目を受領データ値に更新

        StringBuilder sb = new StringBuilder();

        sb.append(" MERGE ");
        sb.append(" INTO DT_OROSHI_SYUKA_DENPYO DOSD  ");
        sb.append("   USING (  ");
        sb.append("     SELECT DISTINCT ");

        if (StringUtils.equals(henkouKb, "D")) {
            sb.append("       REF_1  ");
        } else {
            sb.append("       REF_1  ");
            sb.append("       , INVOICE_NB  ");
            sb.append("       , INVOICE_KB  ");
            sb.append("       , INVOICE_RB  ");
            sb.append("       , INVOICE_HAKOU_DT  ");
        }

        sb.append("     FROM ");
        sb.append("       WK_SAP_OROSHI_HENV_CHK  ");
        sb.append("     WHERE ");
        sb.append("       CHK_RESULT = '"+ CHK_00 +"'  ");
        sb.append("       AND HENKOU_KB = '" + henkouKb + "' ");
        sb.append("   ) WSOHC  ");
        sb.append("     ON (  ");
        sb.append("       SUBSTR(WSOHC.REF_1,1,17) = DOSD.CYOHYO_DENPYO_NO  ");
        sb.append("       AND TO_NUMBER(SUBSTR(WSOHC.REF_1,20,1)) = DOSD.DENPYO_EDA_RB  ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");

        if (StringUtils.equals(henkouKb, "D")) {
            sb.append("   DOSD.OROSHI_VAT_KEYINNO_NB = NULL ");
            sb.append("   , DOSD.OROSHI_VAT_KEYINNO_KB = NULL ");
            sb.append("   , DOSD.OROSHI_VAT_KEYINNO_RB = NULL ");
            sb.append("   , DOSD.OROSHI_VAT_KEYINNO_HAKOU_DT = NULL ");
//            sb.append("   , DOSD.INVOICE_AUTONO_NB = NULL ");
//            sb.append("   , DOSD.INVOICE_AUTONO_KB = NULL ");
//            sb.append("   , DOSD.INVOICE_AUTONO_RB = NULL ");
//            sb.append("   , DOSD.INVOICE_AUTONO_HAKOU_DT = NULL ");
        } else {
            sb.append("   DOSD.OROSHI_VAT_KEYINNO_NB = WSOHC.INVOICE_NB ");
            sb.append("   , DOSD.OROSHI_VAT_KEYINNO_KB = WSOHC.INVOICE_KB ");
            sb.append("   , DOSD.OROSHI_VAT_KEYINNO_RB = WSOHC.INVOICE_RB ");
            sb.append("   , DOSD.OROSHI_VAT_KEYINNO_HAKOU_DT = WSOHC.INVOICE_HAKOU_DT ");
//            sb.append("   , DOSD.INVOICE_AUTONO_NB = WSOHC.INVOICE_NB ");
//            sb.append("   , DOSD.INVOICE_AUTONO_KB = WSOHC.INVOICE_KB ");
//            sb.append("   , DOSD.INVOICE_AUTONO_RB = WSOHC.INVOICE_RB ");
//            sb.append("   , DOSD.INVOICE_AUTONO_HAKOU_DT = WSOHC.INVOICE_HAKOU_DT ");
        }
        sb.append("   , DOSD.UPDATE_USER_ID = '" + BATCH_ID + "' ");
        sb.append("   , DOSD.UPDATE_TS = '" + SYS_DATE + "'  ");

        return sb.toString();
    }

    private String mergeInvoice(String henkouKb) {
        // 変更区分「D」の場合は、インボイス関連項目をNULLに更新
        // 変更区分「C」「M」の場合は、インボイス関連項目を受領データ値に更新

        StringBuilder sb = new StringBuilder();

        sb.append(" MERGE ");
        sb.append(" INTO DT_URIAGE_INVOICE_KANRI_H DUIKH  ");
        sb.append("   USING (  ");
        sb.append("     SELECT DISTINCT ");
        sb.append("       SYUKA_TENPO_CD ");
        sb.append("       , DENPYO_NO ");
        sb.append("       , SYUKA_DT ");
        sb.append("       , INVOICE_NB ");
        sb.append("       , INVOICE_KB ");
        sb.append("       , INVOICE_RB ");
        sb.append("       , INVOICE_HAKOU_DT  ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_OROSHI_HENV_CHK WSOHC  ");
        sb.append("       INNER JOIN DT_OROSHI_SYUKA_DENPYO DOSD  ");
        sb.append("         ON DOSD.COMP_CD = '" + COMP_CD + "'  ");
        sb.append("         AND SUBSTR(WSOHC.REF_1, 1, 17) = DOSD.CYOHYO_DENPYO_NO  ");
        sb.append("         AND TO_NUMBER(SUBSTR(WSOHC.REF_1, 20, 1)) = DOSD.DENPYO_EDA_RB  ");
        sb.append("     WHERE ");
        sb.append("       WSOHC.CHK_RESULT = '" + CHK_00 + "'  ");
        sb.append("       AND WSOHC.HENKOU_KB = '" + henkouKb + "' ");
        sb.append("   ) WSOHC  ");
        sb.append("     ON (  ");
        sb.append("       DUIKH.COMMAND_CD = '0000'  ");// 卸の場合はALL0固定
        sb.append("       AND DUIKH.TENPO_CD = WSOHC.SYUKA_TENPO_CD  ");
        sb.append("       AND DUIKH.REGI_RB = SUBSTR(WSOHC.DENPYO_NO, 1, 3)  ");
        sb.append("       AND DUIKH.TERMINAL_RB = SUBSTR(WSOHC.DENPYO_NO, 4, 6)  ");
        sb.append("       AND DUIKH.EIGYO_DT = WSOHC.SYUKA_DT ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");

        if (StringUtils.equals(henkouKb, "D")) {
            sb.append("   DUIKH.INVOICE_NB = NULL ");
            sb.append("   , DUIKH.INVOICE_KB = NULL ");
            sb.append("   , DUIKH.INVOICE_RB = NULL ");
            sb.append("   , DUIKH.HAKOU_DT = NULL ");
            sb.append("   , DUIKH.INVOICE_AUTO_NB = NULL ");
            sb.append("   , DUIKH.INVOICE_AUTO_KB = NULL ");
            sb.append("   , DUIKH.INVOICE_AUTO_RB = NULL ");
            sb.append("   , DUIKH.VAT_AUTO_DT = NULL ");
            sb.append("   , DUIKH.UPDATE_USER_ID = '" + BATCH_ID + "' ");
            sb.append("   , DUIKH.UPDATE_TS = '" + SYS_DATE + "' ");
        } else {
            sb.append("   DUIKH.INVOICE_NB = WSOHC.INVOICE_NB ");
            sb.append("   , DUIKH.INVOICE_KB = WSOHC.INVOICE_KB ");
            sb.append("   , DUIKH.INVOICE_RB = WSOHC.INVOICE_RB ");
            sb.append("   , DUIKH.HAKOU_DT = WSOHC.INVOICE_HAKOU_DT ");
            sb.append("   , DUIKH.INVOICE_AUTO_NB = WSOHC.INVOICE_NB ");
            sb.append("   , DUIKH.INVOICE_AUTO_KB = WSOHC.INVOICE_KB ");
            sb.append("   , DUIKH.INVOICE_AUTO_RB = WSOHC.INVOICE_RB ");
            sb.append("   , DUIKH.VAT_AUTO_DT = WSOHC.INVOICE_HAKOU_DT ");
            sb.append("   , DUIKH.UPDATE_USER_ID = '" + BATCH_ID + "' ");
            sb.append("   , DUIKH.UPDATE_TS = '" + SYS_DATE + "' ");
        }

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
            DaoIf dao = new IfSapOroshiHenpinInsertWkDao();
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
