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
 * タイトル：IfSapJikashohiDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 自家消費 IFファイル作成処理
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
public class IfSapJikashohiDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810081";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 自家消費 IFファイル作成処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** SAPファイルパス */
    private static final String SAP_SEND_PATH = FiResorceUtility.getPropertie("SAP_SEND_PATH");
    /** SAPファイル名 */
    private static final String SAP_SEND_FILE_URIAGE_JIKASHOHI = FiResorceUtility.getPropertie("SAP_SEND_FILE_JIKASHOHI");
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
        invoker.infoLog(strUserId + "　：　SAP IF 自家消費 IFファイル作成処理を開始します。");

        PreparedStatementEx ps_cnt = null;
        PreparedStatementEx ps_c = null;
        PreparedStatementEx ps_merge = null;
        ResultSet rs_cnt = null;
        ResultSet rs_c = null;

        /** 自家消費件数 */
        int count = 0;
        /** 自家消費件数取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　自家消費ワーク件数取得処理を開始します。");
        // 自家消費件数取得SQL
        ps_cnt = invoker.getDataBase().prepareStatement(countWk());

        // SQLを実行する
        rs_cnt = ps_cnt.executeQuery();

        if (rs_cnt.next()) {
            count = rs_cnt.getInt("COUNT");
        }

        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　" + count + "件の自家消費を取得しました。");
        invoker.infoLog(strUserId + "　：　自家消費ワーク件数取得処理を終了します。");

        /** 抽出 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　抽出を開始します。");
        ps_c = invoker.getDataBase().prepareStatement(select_C());
        // SQLを実行する
        rs_c = ps_c.executeQuery();
        // ログを出力する
        invoker.infoLog(strUserId + "　：　抽出を終了します。");
        
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
        File dataFile = new File(SAP_SEND_PATH + "/" + SAP_SEND_FILE_URIAGE_JIKASHOHI + "_" + BATCH_DT + "_" + selectFileNo(invoker) + ".txt");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
        // ファイル出力
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        count = 0;

        try {
            // 変更区分「C」
            count = count + createFile(rs_c, writer);
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
        invoker.infoLog(strUserId + "　：　SAP IF 自家消費 IFファイル作成処理を終了します。");

    }

    private int createFile(ResultSet rs, BufferedWriter writer) throws Exception {

        int cnt = 0;

        if (rs == null) {
            return cnt;
        }
        while (rs.next()) {
            StringBuffer line = new StringBuffer();

            line.append(rs.getString("SAKUSEI_DT"));
            line.append(rs.getString("KEIJO_DT"));
            line.append(rs.getString("KAMOKU_CD"));
            line.append(rs.getString("URIAGE_VL_NUKI"));
            line.append(rs.getString("TAX_RT"));
            line.append(rs.getString("TAX_VL"));
            line.append(rs.getString("TENPO_CD"));
            line.append(rs.getString("DAIBUNRUI2_CD"));
            line.append(rs.getString("PLUS_OR_MINUS_FLAG"));
            line.append(LINE_FEED_CHAR);

            writer.write(line.toString());
            cnt++;
        }
        return cnt;
    }

    private String select_C() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT /*+ ORDERED USE_NL(WSJ DSJR) */ ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_JIKASHOHI WSJ  ");
        sb.append("   LEFT OUTER JOIN DT_SAP_JIKASHOHI_RUI DSJR  ");
        sb.append("     ON WSJ.KEIJO_DT = DSJR.KEIJO_DT  ");
        sb.append("     AND WSJ.TENPO_CD = DSJR.TENPO_CD  ");
        sb.append("     AND WSJ.SHIHARAI_SYUBETSU_CD = DSJR.SHIHARAI_SYUBETSU_CD  ");
        sb.append("     AND WSJ.SHIHARAI_SYUBETSU_SUB_CD = DSJR.SHIHARAI_SYUBETSU_SUB_CD  ");
        sb.append("     AND WSJ.TAX_RT = DSJR.TAX_RT  ");
        sb.append("     AND WSJ.DAIBUNRUI2_CD = DSJR.DAIBUNRUI2_CD  ");
        sb.append("     AND WSJ.SALES_TYPE = DSJR.SALES_TYPE  ");
        sb.append(" WHERE ");
        sb.append("   ( DSJR.KEIJO_DT IS NULL ");
        sb.append("     AND NVL(WSJ.URIAGE_VL_NUKI, 0) <> 0 ");
        sb.append("   ) OR (  ");
        sb.append("     DSJR.KEIJO_DT IS NOT NULL  ");
        sb.append("     AND (  ");
        sb.append("       NVL(WSJ.URIAGE_VL_NUKI, 0) <> NVL(DSJR.URIAGE_VL_NUKI, 0)  ");
        sb.append("       OR NVL(WSJ.TAX_RT, 0) <> NVL(DSJR.TAX_RT, 0) ");
        sb.append("     ) ");
        sb.append("   )  ");
        sb.append(" ORDER BY ");
        sb.append("   WSJ.KEIJO_DT ");
        sb.append("   , WSJ.TENPO_CD ");
        sb.append("   , WSJ.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSJ.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSJ.TAX_RT ");
        sb.append("   , WSJ.DAIBUNRUI2_CD ");
        sb.append("   , WSJ.SALES_TYPE "); 

        return sb.toString();
    }

    private String selectColumn(){
        StringBuffer sb = new StringBuffer();
        sb.append("   '" + BATCH_DT + "' AS SAKUSEI_DT ");
        sb.append("   , RPAD(WSJ.KEIJO_DT, 8, ' ') AS KEIJO_DT ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(TRIM(SUBSTR(WSJ.SHIHARAI_SYUBETSU_SUB_CD, 6, 2)),0) ");//7桁対応前の支払種別サブコードは'00'になります
        sb.append("       , '00' ");
        sb.append("     ) ");
        sb.append("   ) AS KAMOKU_CD ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSJ.URIAGE_VL_NUKI, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS URIAGE_VL_NUKI ");
        sb.append("   , RPAD(WSJ.TAX_RT, 2, ' ') AS TAX_RT ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(NVL(WSJ.TAX_VL, 0) * 100, '000000000000000') ");
        sb.append("   ) AS TAX_VL ");
        sb.append("   , RPAD(WSJ.TENPO_CD, 10, ' ') AS TENPO_CD");
        sb.append("   , RPAD(WSJ.DAIBUNRUI2_CD, 10, ' ') AS DAIBUNRUI2_CD ");
        sb.append("   , CASE  ");
        sb.append("     WHEN WSJ.SALES_TYPE = '1011'  ");
        sb.append("     THEN '0'  ");
        sb.append("     ELSE '1'  ");
        sb.append("     END AS PLUS_OR_MINUS_FLAG  ");

        return sb.toString();
    }

    private String mergeRuiseki() {
        StringBuffer sb = new StringBuffer();
        //
        sb.append(" MERGE /*+ ORDERED USE_NL(WSJ DSJR) */ ");
        sb.append(" INTO DT_SAP_JIKASHOHI_RUI DSJR  ");
        sb.append("   USING WK_SAP_JIKASHOHI WSJ  ");
        sb.append("     ON (  ");
        sb.append("       DSJR.KEIJO_DT = WSJ.KEIJO_DT  ");
        sb.append("       AND DSJR.TENPO_CD = WSJ.TENPO_CD  ");
        sb.append("       AND DSJR.SHIHARAI_SYUBETSU_CD = WSJ.SHIHARAI_SYUBETSU_CD  ");
        sb.append("       AND DSJR.SHIHARAI_SYUBETSU_SUB_CD = WSJ.SHIHARAI_SYUBETSU_SUB_CD  ");
        sb.append("       AND DSJR.TAX_RT = WSJ.TAX_RT  ");
        sb.append("       AND DSJR.DAIBUNRUI2_CD = WSJ.DAIBUNRUI2_CD  ");
        sb.append("       AND DSJR.SALES_TYPE = WSJ.SALES_TYPE ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE ");
        sb.append(" SET ");
        sb.append("   DSJR.URIAGE_VL_NUKI = WSJ.URIAGE_VL_NUKI ");
        sb.append("   , DSJR.TAX_VL = WSJ.TAX_VL ");
        sb.append("   , DSJR.UPDATE_USER_ID = '" + BATCH_ID + "' ");
        sb.append("   , DSJR.UPDATE_TS = '" + SYS_DATE + "'  ");
        sb.append(" WHEN NOT MATCHED THEN ");
        sb.append(" INSERT (  ");
        sb.append("   DSJR.KEIJO_DT ");
        sb.append("   , DSJR.TENPO_CD ");
        sb.append("   , DSJR.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , DSJR.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , DSJR.TAX_RT ");
        sb.append("   , DSJR.DAIBUNRUI2_CD ");
        sb.append("   , DSJR.SALES_TYPE ");
        sb.append("   , DSJR.URIAGE_VL_NUKI ");
        sb.append("   , DSJR.TAX_VL ");
        sb.append("   , DSJR.INSERT_USER_ID ");
        sb.append("   , DSJR.INSERT_TS ");
        sb.append("   , DSJR.UPDATE_USER_ID ");
        sb.append("   , DSJR.UPDATE_TS ");
        sb.append(" ) VALUES (  ");
        sb.append("   WSJ.KEIJO_DT ");
        sb.append("   , WSJ.TENPO_CD ");
        sb.append("   , WSJ.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSJ.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSJ.TAX_RT ");
        sb.append("   , WSJ.DAIBUNRUI2_CD ");
        sb.append("   , WSJ.SALES_TYPE ");
        sb.append("   , WSJ.URIAGE_VL_NUKI ");
        sb.append("   , WSJ.TAX_VL ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append(" )  ");

        return sb.toString();
    }

    private String countWk() {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append("   COUNT(*) AS COUNT");
        sb.append(" FROM");
        sb.append("   WK_SAP_JIKASHOHI ");
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
        sb.append("   FILEID = '" + SAP_SEND_FILE_URIAGE_JIKASHOHI + "' ");
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
        sb.append("   FILEID = '" + SAP_SEND_FILE_URIAGE_JIKASHOHI + "' ");
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
            DaoIf dao = new IfSapJikashohiDao();
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