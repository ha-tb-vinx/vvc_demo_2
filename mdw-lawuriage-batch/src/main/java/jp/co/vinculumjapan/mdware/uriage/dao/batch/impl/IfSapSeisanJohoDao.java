package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * タイトル：IfSapSeisanJohoDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 精算情報 IFファイル出力処理
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
public class IfSapSeisanJohoDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810040";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 精算情報 IFファイル出力処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** SAPファイルパス */
    private static final String SAP_SEND_PATH = FiResorceUtility.getPropertie("SAP_SEND_PATH");
    /** SAPファイル名 */
    private static final String SAP_SEND_FILE_SEISANJOHO = FiResorceUtility.getPropertie("SAP_SEND_FILE_URIAGE_SEISANJOHO");
    /** 出力ファイル文字コード */
    private static final String OUTPUT_CHAR_SET = "UTF-8";
    /** 改行文字(CRLF) */
    private static final String LINE_FEED_CHAR = "\r\n";
    /** システム日付 */
    private static final String SYS_DATE = FiResorceUtility.getDBServerTime();

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 精算情報データ IFファイル作成処理を開始します。");

        PreparedStatementEx ps_cnt = null;
        PreparedStatementEx ps_c = null;
        PreparedStatementEx ps_m = null;
        PreparedStatementEx ps_merge = null;
        ResultSet rs_cnt = null;
        ResultSet rs_c = null;
        ResultSet rs_m = null;

        /** 精算情報データ件数 */
        int count = 0;
        /** 精算情報データ件数取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　精算情報データ件数取得処理を開始します。");//
        // 精算情報ワーク件数取得SQL
        ps_cnt = invoker.getDataBase().prepareStatement(selectCountWk());
        // SQLを実行する
        rs_cnt = ps_cnt.executeQuery();

        if (rs_cnt.next()) {
            count = rs_cnt.getInt("COUNT");
        }

        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　" + count + "件の精算情報データを取得しました。");
        invoker.infoLog(strUserId + "　：　精算情報データ件数取得処理を終了します。");

        /** 精算情報データ取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を開始します。");

        // 精算情報データ取得処理SQL
        ps_c = invoker.getDataBase().prepareStatement(select_C());
        // SQLを実行する
        rs_c = ps_c.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を終了します。");

        /** 精算情報データ取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を開始します。");

        // 精算情報データ取得処理SQL
        ps_m = invoker.getDataBase().prepareStatement(select_M());
        // SQLを実行する
        rs_m = ps_m.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を終了します。");

        /** 精算情報データ取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　累積データの登録を開始します。");

        // 精算情報データ取得処理SQL
        ps_merge = invoker.getDataBase().prepareStatement(mergeRuiseki());
        // SQLを実行する
        ps_merge.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　累積データの登録を終了します。");

        /** 精算情報データ取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　累積データの更新を開始します。");

        // ログを出力する
        invoker.infoLog(strUserId + "　：　累積データの更新を終了します。");

        /** 精算情報データファイル作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ファイル作成を開始します。");
        // ファイル名の取得
        File dataFile = new File(SAP_SEND_PATH + "/" + SAP_SEND_FILE_SEISANJOHO + "_" + BATCH_DT + "_" + selectFileNo(invoker) + ".txt");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
        // ファイル出力
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        try {
            // 変更区分「C」
            count = createFile(rs_c, writer);
            // 変更区分「M」
            count = count + createFile(rs_m, writer);
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
        invoker.infoLog(strUserId + "　：　" + count + "件を出力しました。");
        invoker.infoLog(strUserId + "　：　ファイル作成を終了します。");
        
        // ファイル名連番を+1
        updateFileNo(invoker);

        /** 終了処理 */
        invoker.infoLog(strUserId + "　：　SAP IF 精算情報データ IFファイル作成処理を終了します。");

    }

    private int createFile(ResultSet rs, BufferedWriter writer) throws Exception {

        int cnt = 0;

        if (rs == null) {
            return cnt;
        }

        while (rs.next()) {
            StringBuffer line = new StringBuffer();
            line.append(rs.getString("HENKOU_KB"));
            line.append(rs.getString("SAKUSEIBI"));
            line.append(rs.getString("KEIJO_DT"));
            line.append(rs.getString("URIAGE_VL_NUKI"));
            line.append(rs.getString("TAX_VL"));
            line.append(rs.getString("URIAGE_VL"));
            line.append(rs.getString("TENPO_CD"));
            line.append(rs.getString("DAIBUNRUI2_CD"));
            line.append(rs.getString("TAX_RT"));
            line.append(LINE_FEED_CHAR);

            writer.write(line.toString());
            cnt++;
        }
        return cnt;

    }

    private String select_C() {

        StringBuffer sb = new StringBuffer();

        // 「C」
        sb.append(" SELECT /*+ ORDERED USE_NL(WSS DSSR) */ ");
        sb.append("   'C' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_SEISAN WSS  ");
        sb.append("   LEFT OUTER JOIN DT_SAP_SEISAN_RUI DSSR  ");
        sb.append("     ON WSS.KEIJO_DT = DSSR.KEIJO_DT  ");
        sb.append("     AND WSS.TENPO_CD = DSSR.TENPO_CD  ");
        sb.append("     AND WSS.DAIBUNRUI2_CD = DSSR.DAIBUNRUI2_CD  ");
        sb.append("     AND WSS.TAX_RT = DSSR.TAX_RT  ");
        sb.append(" WHERE ");
        sb.append("   DSSR.KEIJO_DT IS NULL ");
        sb.append("   AND NVL(WSS.URIAGE_VL_NUKI, 0) + NVL(WSS.TAX_VL,0) + NVL(WSS.URIAGE_VL,0) <> 0 "); 

        return sb.toString();
    }

    private String select_M() {

        StringBuffer sb = new StringBuffer();
        // 「M」
        sb.append(" SELECT /*+ ORDERED USE_NL(WSS DSSR) */ ");
        sb.append("   'M' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_SEISAN WSS  ");
        sb.append("   INNER JOIN DT_SAP_SEISAN_RUI DSSR  ");
        sb.append("     ON WSS.KEIJO_DT = DSSR.KEIJO_DT  ");
        sb.append("     AND WSS.TENPO_CD = DSSR.TENPO_CD  ");
        sb.append("     AND WSS.DAIBUNRUI2_CD = DSSR.DAIBUNRUI2_CD  ");
        sb.append("     AND WSS.TAX_RT = DSSR.TAX_RT  ");
        sb.append("     AND NVL(WSS.URIAGE_VL_NUKI, 0) + NVL(WSS.TAX_VL,0) + NVL(WSS.URIAGE_VL,0) <> 0 "); 
        sb.append("     AND ( NVL(WSS.URIAGE_VL_NUKI,0) <> NVL(DSSR.URIAGE_VL_NUKI,0)  ");
        sb.append("           OR NVL(WSS.TAX_VL,0) <> NVL(DSSR.TAX_VL,0)  ");        
        sb.append("           OR NVL(WSS.URIAGE_VL,0) <> NVL(DSSR.URIAGE_VL,0) ) ");

        return sb.toString();
    }
    
    private String selectColumn(){
        // 「C」と「M」の共通項目
        StringBuffer sb = new StringBuffer();
        
        sb.append("   , RPAD('"+ BATCH_DT + "', 8, ' ') AS SAKUSEIBI ");
        sb.append("   , RPAD(NVL(WSS.KEIJO_DT, ' '), 8, ' ') AS KEIJO_DT ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSS.URIAGE_VL_NUKI, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS URIAGE_VL_NUKI ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSS.TAX_VL, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS TAX_VL ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSS.URIAGE_VL, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS URIAGE_VL ");
        sb.append("   , RPAD(LPAD(NVL(WSS.TENPO_CD, ' '), 6, '00'), 10, ' ') AS TENPO_CD ");
        sb.append("   , RPAD(NVL(WSS.DAIBUNRUI2_CD, ' '), 10, ' ') AS DAIBUNRUI2_CD ");
        sb.append("   , RPAD(NVL(WSS.TAX_RT, ' '), 2, ' ') AS TAX_RT  ");
        
        return sb.toString();
    }

    private String mergeRuiseki() {
        // 累積テーブルにMERGE
        StringBuffer sb = new StringBuffer();
        sb.append(" MERGE /*+ ORDERED USE_NL(WSS DSSR) */ ");
        sb.append(" INTO DT_SAP_SEISAN_RUI DSSR ");
        sb.append("   USING WK_SAP_SEISAN WSS ");
        sb.append("     ON ( ");
        sb.append("       DSSR.KEIJO_DT = WSS.KEIJO_DT ");
        sb.append("       AND DSSR.TENPO_CD = WSS.TENPO_CD ");
        sb.append("       AND DSSR.DAIBUNRUI2_CD = WSS.DAIBUNRUI2_CD ");
        sb.append("       AND DSSR.TAX_RT = WSS.TAX_RT ");
        sb.append("     ) ");
        sb.append(" WHEN MATCHED THEN UPDATE ");
        sb.append(" SET ");
        sb.append("   DSSR.URIAGE_VL_NUKI = WSS.URIAGE_VL_NUKI ");
        sb.append("   , DSSR.TAX_VL = WSS.TAX_VL ");
        sb.append("   , DSSR.URIAGE_VL = WSS.URIAGE_VL ");
        sb.append("   , DSSR.UPDATE_USER_ID = '"+ BATCH_ID +"' ");
        sb.append("   , DSSR.UPDATE_TS = '"+ SYS_DATE +"' ");
        sb.append(" WHEN NOT MATCHED THEN ");
        sb.append(" INSERT ( ");
        sb.append("   DSSR.KEIJO_DT ");
        sb.append("   , DSSR.TENPO_CD ");
        sb.append("   , DSSR.DAIBUNRUI2_CD ");
        sb.append("   , DSSR.TAX_RT ");
        sb.append("   , DSSR.URIAGE_VL_NUKI ");
        sb.append("   , DSSR.TAX_VL ");
        sb.append("   , DSSR.URIAGE_VL ");
        sb.append("   , DSSR.INSERT_USER_ID ");
        sb.append("   , DSSR.INSERT_TS ");
        sb.append("   , DSSR.UPDATE_USER_ID ");
        sb.append("   , DSSR.UPDATE_TS ");
        sb.append(" )  ");
        sb.append(" VALUES ( ");
        sb.append("   WSS.KEIJO_DT ");
        sb.append("   , WSS.TENPO_CD ");
        sb.append("   , WSS.DAIBUNRUI2_CD ");
        sb.append("   , WSS.TAX_RT ");
        sb.append("   , WSS.URIAGE_VL_NUKI ");
        sb.append("   , WSS.TAX_VL ");
        sb.append("   , WSS.URIAGE_VL ");
        sb.append("   , '"+ BATCH_ID +"' ");
        sb.append("   , '"+ SYS_DATE +"' ");
        sb.append("   , '"+ BATCH_ID +"' ");
        sb.append("   , '"+ SYS_DATE +"' ");
        sb.append(" ) ");

        return sb.toString();
    }

    private String selectCountWk() {

        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");
        sb.append("   COUNT(*) AS COUNT ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_SEISAN  ");

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
        sb.append("   FILEID = '"+ SAP_SEND_FILE_SEISANJOHO +"' ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        rs = ps.executeQuery();
        if (rs.next()) {
            //FILENOを取得できたらその値を返却
            return rs.getString("FILENO");
        }else{
            //FILENOを取得できなくても001を返却（ファイル作成を優先）
            return "001";
        }
    }
    
    private void updateFileNo(DaoInvokerIf invoker) throws SQLException {
        PreparedStatementEx ps = null;
        //結果0件でもファイルを空で作成するので、処理が流れたら+1しておく
        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE R_SAP_FILE_SEQ_URIAGE RSFS ");
        sb.append("   SET RSFS.FILENO = RSFS.FILENO + 1 ");
        sb.append("   , RSFS.UPDATE_USER_ID = '"+ BATCH_ID +"' ");
        sb.append("   , RSFS.UPDATE_TS = '"+ SYS_DATE +"' ");
        sb.append(" WHERE ");
        sb.append("   FILEID = '"+ SAP_SEND_FILE_SEISANJOHO +"' ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        ps.executeQuery();
    }

    public Object getOutputObject() throws Exception {
        return null;
    }

    public void setInputObject(Object arg0) throws Exception {

    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new IfSapSeisanJohoDao();
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