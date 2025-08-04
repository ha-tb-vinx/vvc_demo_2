package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * タイトル：IfSapCreditHenkinDao クラス
 * </p>
 * <p>
 * 説明：SAP IF クレジット返品 IFファイル作成処理
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
public class IfSapCreditHenpinDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810061";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF クレジット返品 IFファイル作成処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** SAPファイルパス */
    private static final String SAP_SEND_PATH = FiResorceUtility.getPropertie("SAP_SEND_PATH");
    /** SAPファイル名 */
    private static final String SAP_SEND_FILE_URIAGE_CREDITHENPIN = FiResorceUtility.getPropertie("SAP_SEND_FILE_URIAGE_CREDITHENPIN");
    /** 出力ファイル文字コード */
    private static final String OUTPUT_CHAR_SET = "UTF-8";
    /** 改行文字(CRLF) */
    private static final String LINE_FEED_CHAR = "\r\n";
    /** システム日付 */
    private static final String SYS_DATE = FiResorceUtility.getDBServerTime();

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF クレジット返品 IFファイル作成処理を開始します。");

        PreparedStatementEx ps_cnt = null;
        PreparedStatementEx ps_c = null;
        PreparedStatementEx ps_m = null;
        PreparedStatementEx ps_d = null;
        PreparedStatementEx ps_merge = null;
        ResultSet rs_cnt = null;
        ResultSet rs_c = null;
        ResultSet rs_m = null;
        ResultSet rs_d = null;

        /** クレジット返金データ件数 */
        int count = 0;
        /** クレジット返金データ件数取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　クレジット返金データ件数取得処理を開始します。");
        // クレジット返金データ件数取得SQL
        ps_cnt = invoker.getDataBase().prepareStatement(selectCountWK());

        // SQLを実行する
        rs_cnt = ps_cnt.executeQuery();

        if (rs_cnt.next()) {
            count = rs_cnt.getInt("COUNT");
        }

        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　" + count + "件のクレジット返金データを取得しました。");
        invoker.infoLog(strUserId + "　：　クレジット返金データ件数取得処理を終了します。");

        /** 変更区分「C」分の抽出 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を開始します。");
        ps_c = invoker.getDataBase().prepareStatement(select_C());
        // SQLを実行する
        rs_c = ps_c.executeQuery();
        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を終了します。");

        /** 変更区分「M」分の抽出 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を開始します。");
        ps_m = invoker.getDataBase().prepareStatement(select_M());
        // SQLを実行する
        rs_m = ps_m.executeQuery();
        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を終了します。");

        /** 変更区分「D」分の抽出 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「D」分の抽出を開始します。");
        ps_d = invoker.getDataBase().prepareStatement(select_D_ForCreditNoChanged());
        // SQLを実行する
        rs_d = ps_d.executeQuery();
        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「D」分の抽出を終了します。");
        
        /** 累積データの更新 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　累積データの更新を開始します。");
        ps_merge = invoker.getDataBase().prepareStatement(mergeRuiseki());
        // SQLを実行する
        ps_merge.executeQuery();
        // ログを出力する
        invoker.infoLog(strUserId + "　：　累積データの更新を終了します。");

        /** ファイル作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ファイル作成を開始します。");
        // ファイル名の取得
        File dataFile = new File(SAP_SEND_PATH + "/" + SAP_SEND_FILE_URIAGE_CREDITHENPIN + "_" + BATCH_DT + "_" + selectFileNo(invoker) + ".txt");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
        // ファイル出力
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        count = 0;

        try {
            // 変更区分「C」
            count = count + createFile(rs_c, writer);
            // 変更区分「M」
            count = count + createFile(rs_m, writer);
            // 変更区分「D」
            count = count + createFile(rs_d, writer);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        // ログを出力
        invoker.infoLog(strUserId + "　：　" + count + "件を作成しました。");
        invoker.infoLog(strUserId + "　：　ファイル作成を終了します。");

        // ファイル名連番を+1
        updateFileNo(invoker);
        
        /** 終了処理 */
        invoker.infoLog(strUserId + "　：　SAP IF クレジット返品 IFファイル作成処理を終了します。");

    }

    private int createFile(ResultSet rs, BufferedWriter writer) throws Exception {

        int cnt = 0;

        if (rs == null) {
            return cnt;
        }
        while (rs.next()) {
            StringBuffer line = new StringBuffer();

            line.append(rs.getString("HENKOU_KB"));
            line.append(rs.getString("SAKUSEI_DT"));
            line.append(rs.getString("KEIJO_DT"));
            line.append(rs.getString("RECEIPT_NO"));
            line.append(rs.getString("SEND_CREDIT_NO"));
            line.append(rs.getString("URIAGE_VL_NUKI"));
            line.append(rs.getString("TAX_VL"));
            line.append(rs.getString("TAX_RT"));
            line.append(rs.getString("TENPO_CD"));
            line.append(LINE_FEED_CHAR);

            writer.write(line.toString());
            cnt++;
        }
        return cnt;
    }

    private String select_C() {
        StringBuffer sb = new StringBuffer();
        // 累積テーブルに無いデータを「C」として抽出
        sb.append(" SELECT /*+ ORDERED USE_NL(WSCH DSCHR) */ ");
        sb.append("   'C' AS HENKOU_KB ");
        sb.append(selectColumn("C"));
        sb.append(" FROM ");
        sb.append("   WK_SAP_CREDIT_HENPIN WSCH  ");
        sb.append("   LEFT OUTER JOIN DT_SAP_CREDIT_HENPIN_RUI DSCHR  ");
        sb.append("     ON WSCH.KEIJO_DT = DSCHR.KEIJO_DT  ");
        sb.append("     AND WSCH.TENPO_CD = DSCHR.TENPO_CD  ");
        sb.append("     AND WSCH.REGISTER_NO = DSCHR.REGISTER_NO  ");
        sb.append("     AND WSCH.TRANSACTION_NO = DSCHR.TRANSACTION_NO  ");
        sb.append("     AND NVL(WSCH.SEND_CREDIT_NO,' ') = NVL(DSCHR.SEND_CREDIT_NO,' ')  ");
        sb.append("     AND WSCH.TAX_RT = DSCHR.TAX_RT  ");
        sb.append(" WHERE ");
        sb.append("   DSCHR.KEIJO_DT IS NULL "); 
        sb.append("   AND NVL(WSCH.URIAGE_VL_NUKI, 0) <> 0 "); 

        return sb.toString();
    }

    private String select_M() {
        StringBuffer sb = new StringBuffer();
        // 累積テーブルにあり、変更あるデータを「M」として抽出
        sb.append(" SELECT /*+ ORDERED USE_NL(WSCH DSCHR) */ ");
        sb.append("   'M' AS HENKOU_KB ");
        sb.append(selectColumn("M"));
        sb.append(" FROM ");
        sb.append("   WK_SAP_CREDIT_HENPIN WSCH  ");
        sb.append("   INNER JOIN DT_SAP_CREDIT_HENPIN_RUI DSCHR  ");
        sb.append("     ON WSCH.KEIJO_DT = DSCHR.KEIJO_DT  ");
        sb.append("     AND WSCH.TENPO_CD = DSCHR.TENPO_CD  ");
        sb.append("     AND WSCH.REGISTER_NO = DSCHR.REGISTER_NO  ");
        sb.append("     AND WSCH.TRANSACTION_NO = DSCHR.TRANSACTION_NO  ");
        sb.append("     AND NVL(WSCH.SEND_CREDIT_NO,' ') = NVL(DSCHR.SEND_CREDIT_NO,' ')  ");
        sb.append("     AND WSCH.TAX_RT = DSCHR.TAX_RT  ");
        sb.append("     AND ( WSCH.URIAGE_VL_NUKI <> DSCHR.URIAGE_VL_NUKI  ");
        sb.append("           OR WSCH.TAX_VL <> DSCHR.TAX_VL ) ");

        return sb.toString();
    }
    
    private String select_D_ForCreditNoChanged() {
        StringBuffer sb = new StringBuffer();
        // クレジット番号訂正データの訂正前の「D」を作成
        // PKが一致して,クレジット番号が異なるデータがある場合,「D」
        sb.append(" SELECT /*+ ORDERED USE_NL(WSCH DSCHR) */  ");
        sb.append("   'D' AS HENKOU_KB ");
        sb.append(selectColumn("D"));
        sb.append(" FROM ");
        sb.append("   WK_SAP_CREDIT_HENPIN WSCH  ");
        sb.append("   INNER JOIN DT_SAP_CREDIT_HENPIN_RUI DSCHR  ");
        sb.append("     ON WSCH.KEIJO_DT = DSCHR.KEIJO_DT  ");
        sb.append("     AND WSCH.TENPO_CD = DSCHR.TENPO_CD  ");
        sb.append("     AND WSCH.REGISTER_NO = DSCHR.REGISTER_NO  ");
        sb.append("     AND WSCH.TRANSACTION_NO = DSCHR.TRANSACTION_NO  ");
        sb.append("     AND WSCH.TAX_RT = DSCHR.TAX_RT  ");
        sb.append("     AND NVL(WSCH.SEND_CREDIT_NO,' ') <> NVL(DSCHR.SEND_CREDIT_NO,' ')  ");

        return sb.toString();
    }
    
    private String selectColumn(String henkouKb){
        // 「C」と「M」と「D」の共通項目
        // 「D」（=クレジット番号変更前分）のときは、クレジット番号は累積テーブルの番号を送信する
        
        String alias = "";
        if(StringUtils.equals(henkouKb, "D")){
            alias = "DSCHR";
        }else{
            alias = "WSCH";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("   , '" + BATCH_DT + "' AS SAKUSEI_DT ");
        sb.append("   , RPAD(WSCH.KEIJO_DT, 8, ' ') AS KEIJO_DT ");
        sb.append("   , RPAD(  ");
        sb.append("     SUBSTR("+alias+".TENPO_CD, 3, 4) || "+alias+".REGISTER_NO || "+alias+".TRANSACTION_NO ");
        sb.append("     , 15 ");
        sb.append("     , ' ' ");
        sb.append("   ) AS RECEIPT_NO ");
        sb.append("   , RPAD(NVL("+alias+".SEND_CREDIT_NO, ' '), 20, ' ') AS SEND_CREDIT_NO ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL("+alias+".URIAGE_VL_NUKI, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS URIAGE_VL_NUKI ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL("+alias+".TAX_VL, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS TAX_VL ");
        sb.append("   , RPAD("+alias+".TAX_RT, 2, ' ') AS TAX_RT");
        sb.append("   , RPAD("+alias+".TENPO_CD, 10, ' ') AS TENPO_CD  ");
        return sb.toString();
    }

    private String mergeRuiseki() {
        StringBuffer sb = new StringBuffer();
        //
        sb.append(" MERGE /*+ ORDERED USE_NL(WSCH DSCHR) */  ");
        sb.append(" INTO DT_SAP_CREDIT_HENPIN_RUI DSCHR  ");
        sb.append("   USING WK_SAP_CREDIT_HENPIN WSCH  ");
        sb.append("     ON (  ");
        sb.append("       DSCHR.KEIJO_DT = WSCH.KEIJO_DT  ");
        sb.append("       AND DSCHR.TENPO_CD = WSCH.TENPO_CD  ");
        sb.append("       AND DSCHR.REGISTER_NO = WSCH.REGISTER_NO  ");
        sb.append("       AND DSCHR.TRANSACTION_NO = WSCH.TRANSACTION_NO  ");
        sb.append("       AND DSCHR.SEND_CREDIT_NO = WSCH.SEND_CREDIT_NO  ");
        sb.append("       AND DSCHR.TAX_RT = WSCH.TAX_RT ");
        sb.append("     )   ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   DSCHR.URIAGE_VL_NUKI = WSCH.URIAGE_VL_NUKI ");
        sb.append("   , DSCHR.TAX_VL = WSCH.TAX_VL ");
        sb.append("   , DSCHR.UPDATE_USER_ID = '" + BATCH_ID + "' ");
        sb.append("   , DSCHR.UPDATE_TS = '" + SYS_DATE + "' ");
        sb.append(" WHEN NOT MATCHED THEN INSERT (  ");
        sb.append("   DSCHR.KEIJO_DT ");
        sb.append("   , DSCHR.TENPO_CD ");
        sb.append("   , DSCHR.REGISTER_NO ");
        sb.append("   , DSCHR.TRANSACTION_NO ");
        sb.append("   , DSCHR.SEND_CREDIT_NO ");
        sb.append("   , DSCHR.TAX_RT ");
        sb.append("   , DSCHR.URIAGE_VL_NUKI ");
        sb.append("   , DSCHR.TAX_VL ");
        sb.append("   , DSCHR.INSERT_USER_ID ");
        sb.append("   , DSCHR.INSERT_TS ");
        sb.append("   , DSCHR.UPDATE_USER_ID ");
        sb.append("   , DSCHR.UPDATE_TS ");
        sb.append(" )  ");
        sb.append(" VALUES (  ");
        sb.append("   WSCH.KEIJO_DT ");
        sb.append("   , WSCH.TENPO_CD ");
        sb.append("   , WSCH.REGISTER_NO ");
        sb.append("   , WSCH.TRANSACTION_NO ");
        sb.append("   , WSCH.SEND_CREDIT_NO ");
        sb.append("   , WSCH.TAX_RT ");
        sb.append("   , WSCH.URIAGE_VL_NUKI ");
        sb.append("   , WSCH.TAX_VL ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append(" )  ");

        return sb.toString();
    }

    private String selectCountWK() {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append("   COUNT(*) AS COUNT");
        sb.append(" FROM");
        sb.append("   WK_SAP_CREDIT_HENPIN ");
        return sb.toString();

    }

    private String selectFileNo(DaoInvokerIf invoker) throws SQLException {
        PreparedStatementEx ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   LPAD(FILENO,3,'0') AS FILENO ");
        sb.append(" FROM ");
        sb.append("   R_SAP_FILE_SEQ_URIAGE ");
        sb.append(" WHERE ");
        sb.append("   FILEID = '" + SAP_SEND_FILE_URIAGE_CREDITHENPIN + "' ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        rs = ps.executeQuery();
        if (rs.next()) {
            // FILENOを取得できたらその値を返却
            return rs.getString("FILENO");
        } else {
            // FILENOを取得できなくても001を返却（ファイル作成を優先）
            return "001";
        }
    }

    private void updateFileNo(DaoInvokerIf invoker) throws SQLException {
        PreparedStatementEx ps = null;
        // 結果0件でもファイルを空で作成するので、処理が流れたら+1しておく
        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE R_SAP_FILE_SEQ_URIAGE RSFS ");
        sb.append("   SET RSFS.FILENO = RSFS.FILENO + 1 ");
        sb.append("   , RSFS.UPDATE_USER_ID = '" + BATCH_ID + "' ");
        sb.append("   , RSFS.UPDATE_TS = '" + SYS_DATE + "' ");
        sb.append(" WHERE ");
        sb.append("   FILEID = '" + SAP_SEND_FILE_URIAGE_CREDITHENPIN + "' ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        ps.executeQuery();
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
            DaoIf dao = new IfSapCreditHenpinDao();
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