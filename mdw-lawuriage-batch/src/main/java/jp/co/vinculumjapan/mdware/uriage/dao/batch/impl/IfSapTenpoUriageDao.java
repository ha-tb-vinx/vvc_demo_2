package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
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
 * タイトル：IfSapTenpoUriageDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 店舗売上 IFファイル出力処理
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
public class IfSapTenpoUriageDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810020";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 店舗売上 IFファイル出力処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** SAPファイルパス */
    private static final String SAP_SEND_PATH = FiResorceUtility.getPropertie("SAP_SEND_PATH");
    /** SAPファイル名 */
    private static final String SAP_SEND_FILE_URIAGE_TENPOURIAGE = FiResorceUtility.getPropertie("SAP_SEND_FILE_URIAGE_TENPOURIAGE");
    /** 出力ファイル文字コード */
    private static final String OUTPUT_CHAR_SET = "UTF-8";
    /** 改行文字(CRLF) */
    private static final String LINE_FEED_CHAR = "\r\n";
    /** 文字列変換用 */
    private static final int MODE_STR = 1;
    /** システム日付 */
    private static final String SYS_DATE = FiResorceUtility.getDBServerTime();

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        /** ログを出力 */
        invoker.infoLog(strUserId + "　：　SAP IF 店舗売上 IFファイル出力処理を開始します。");

        PreparedStatementEx ps_cnt = null;
        PreparedStatementEx ps_c = null;
        PreparedStatementEx ps_m = null;
        PreparedStatementEx ps_merge = null;
        ResultSet rs_cnt = null;
        ResultSet rs_c = null;
        ResultSet rs_m = null;

        /** 店舗売上データ件数 */
        int count = 0;
        /** 店舗売上データ件数取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　店舗売上データ件数取得処理を開始します。");
        // 店舗売上データ件数取得SQL
        ps_cnt = invoker.getDataBase().prepareStatement(selectCountWK());

        // SQLを実行する
        rs_cnt = ps_cnt.executeQuery();

        if (rs_cnt.next()) {
            count = rs_cnt.getInt("COUNT");
        }

        // ログを出力する
        invoker.infoLog(strUserId + "　：　" + count + "件の店舗売上データを取得しました。");
        invoker.infoLog(strUserId + "　：　店舗売上データ件数取得処理を終了します。");

        /** 変更区分「C」分の抽出 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を開始します。");

        // 店舗売上データ取得処理SQL
        ps_c = invoker.getDataBase().prepareStatement(select_C());

        // SQLを実行する
        rs_c = ps_c.executeQuery();
        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を終了します。");

        /** 変更区分「M」分の抽出 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を開始します。");

        // 店舗売上データ取得処理SQL
        ps_m = invoker.getDataBase().prepareStatement(select_M());

        // SQLを実行する
        rs_m = ps_m.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を終了します。");

        /** 累積データの作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　累積データの作成を開始します。");
        // 違算入力データ更新処理SQL
        ps_merge = invoker.getDataBase().prepareStatement(mergeRuiseki());
        // SQLを実行する
        ps_merge.executeQuery();
        // ログを出力する
        invoker.infoLog(strUserId + "　：　累積データの作成を終了します。");

        /** ファイル作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ファイル作成を開始します。");
        // ファイル名の取得
        File dataFile = new File(SAP_SEND_PATH + "/" + SAP_SEND_FILE_URIAGE_TENPOURIAGE + "_" + BATCH_DT + "_" + selectFileNo(invoker) + ".txt");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
        // ファイル出力
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        count = 0;

        try {
            // 変更区分「C」
            count = count + createFile(rs_c, writer);
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
        invoker.infoLog(strUserId + "　：　" + count + "件を作成しました。");
        invoker.infoLog(strUserId + "　：　ファイル作成を終了します。");

        // ファイル名連番を+1
        updateFileNo(invoker);

        /** 終了処理 */
        invoker.infoLog(strUserId + "　：　SAP IF 店舗売上 IFファイル出力処理を終了します。");

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
            line.append(rs.getString("SHIHARAI_SYUBETSU_CD"));
            line.append(rs.getString("TENPO_CD"));
            line.append(rs.getString("URIAGE"));
            line.append(rs.getString("VOUCHER"));
            line.append(rs.getString("RECEIPT_NO"));
            line.append(rs.getString("SHIHARAI_SYUBETSU_SUB_CD"));
            line.append(LINE_FEED_CHAR);

            writer.write(line.toString());
            cnt++;
        }

        return cnt;

    }

    private String select_C() {

        StringBuffer sb = new StringBuffer();
        // 「C」
        sb.append(" SELECT /*+ ORDERED USE_NL(WSTU DSTUR) */ ");
        sb.append("   'C' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_TEN_URIAGE WSTU  ");
        sb.append("   LEFT OUTER JOIN DT_SAP_TEN_URIAGE_RUI DSTUR  ");
        sb.append("     ON WSTU.KEIJO_DT = DSTUR.KEIJO_DT  ");
        sb.append("     AND WSTU.TENPO_CD = DSTUR.TENPO_CD  ");
        sb.append("     AND WSTU.REGISTER_NO = DSTUR.REGISTER_NO  ");
        sb.append("     AND WSTU.TRANSACTION_NO = DSTUR.TRANSACTION_NO  ");
        sb.append("     AND WSTU.SHIHARAI_SYUBETSU_CD = DSTUR.SHIHARAI_SYUBETSU_CD  ");
        sb.append("     AND WSTU.SHIHARAI_SYUBETSU_SUB_CD = DSTUR.SHIHARAI_SYUBETSU_SUB_CD  ");
        sb.append("     AND WSTU.DATE_EXPIRY = DSTUR.DATE_EXPIRY  ");
        sb.append("     AND WSTU.PYMT_AMT = DSTUR.PYMT_AMT  ");
        sb.append("     AND WSTU.POINT_ISSUDE_DT = DSTUR.POINT_ISSUDE_DT  ");
        sb.append(" WHERE ");
        sb.append("   DSTUR.KEIJO_DT IS NULL ");
        sb.append("   AND NVL(WSTU.POS_VL, 0) + NVL(WSTU.SAGAKU_VL, 0) <> 0 "); 

        return sb.toString();
    }

    private String select_M() {

        StringBuffer sb = new StringBuffer();
        // 「M」
        sb.append(" SELECT /*+ ORDERED USE_NL(WSTU DSTUR) */ ");
        sb.append("   'M' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_TEN_URIAGE WSTU  ");
        sb.append("   INNER JOIN DT_SAP_TEN_URIAGE_RUI DSTUR  ");
        sb.append("     ON WSTU.KEIJO_DT = DSTUR.KEIJO_DT  ");
        sb.append("     AND WSTU.TENPO_CD = DSTUR.TENPO_CD  ");
        sb.append("     AND WSTU.REGISTER_NO = DSTUR.REGISTER_NO  ");
        sb.append("     AND WSTU.TRANSACTION_NO = DSTUR.TRANSACTION_NO  ");
        sb.append("     AND WSTU.SHIHARAI_SYUBETSU_CD = DSTUR.SHIHARAI_SYUBETSU_CD  ");
        sb.append("     AND WSTU.SHIHARAI_SYUBETSU_SUB_CD = DSTUR.SHIHARAI_SYUBETSU_SUB_CD  ");
        sb.append("     AND WSTU.DATE_EXPIRY = DSTUR.DATE_EXPIRY  ");
        sb.append("     AND WSTU.PYMT_AMT = DSTUR.PYMT_AMT  ");
        sb.append("     AND WSTU.POINT_ISSUDE_DT = DSTUR.POINT_ISSUDE_DT  ");
        sb.append("     AND ( NVL(WSTU.POS_VL,0) <> NVL(DSTUR.POS_VL,0)  ");
        sb.append("           OR NVL(WSTU.SAGAKU_VL,0) <> NVL(DSTUR.SAGAKU_VL,0)  ");
        sb.append("           OR NVL(WSTU.ACTUAL_AMT,0) <> NVL(DSTUR.ACTUAL_AMT,0) )  ");

        return sb.toString();
    }

    private String selectColumn() {
        // 「C」と「M」の共通項目
        StringBuffer sb = new StringBuffer();
        sb.append("   , '" + BATCH_DT + "' AS SAKUSEI_DT ");
        sb.append("   , WSTU.KEIJO_DT AS KEIJO_DT ");
        sb.append("   , RPAD(WSTU.SHIHARAI_SYUBETSU_CD, 3, ' ') AS SHIHARAI_SYUBETSU_CD ");
        sb.append("   , RPAD(WSTU.TENPO_CD, 10, ' ') AS TENPO_CD ");
        sb.append("   , TRIM(TO_CHAR(  ");
        sb.append("     (NVL(WSTU.POS_VL, 0) + NVL(WSTU.SAGAKU_VL, 0)) * 100 ");
        sb.append("     , '000000000000000') ");
        sb.append("   ) AS URIAGE ");
        sb.append("   , CASE  ");
        sb.append("     WHEN WSTU.SHIHARAI_SYUBETSU_CD = 'OVC' AND TRIM(WSTU.DATE_EXPIRY) IS NOT NULL ");
        sb.append("     THEN RPAD( TRIM(WSTU.SHIHARAI_SYUBETSU_VN_NA)  ");
        sb.append("     || '-' || WSTU.PYMT_AMT ");
        sb.append("     || '-' || SUBSTR(NVL(WSTU.DATE_EXPIRY, ''), 7, 2) ");
        sb.append("     || '/' || SUBSTR(NVL(WSTU.DATE_EXPIRY, ''), 5, 2) ");
        sb.append("     || '/' || SUBSTR(NVL(WSTU.DATE_EXPIRY, ''), 1, 4) ");
        sb.append("     , 50, ' ')  ");
        sb.append("     ELSE RPAD(NVL(WSTU.POINT_ISSUDE_DT,' '), 50, ' ')  ");
        sb.append("     END AS VOUCHER  ");
        sb.append("   , RPAD(SUBSTR(WSTU.TENPO_CD,3,4) || WSTU.REGISTER_NO || WSTU.TRANSACTION_NO , 15, ' ') AS RECEIPT_NO  ");
        sb.append("   , RPAD(NVL(TRIM(WSTU.SHIHARAI_SYUBETSU_SUB_CD),' '), 7, ' ') AS SHIHARAI_SYUBETSU_SUB_CD ");

        return sb.toString();
    }

    private String mergeRuiseki() {

        StringBuffer sb = new StringBuffer();
        // 累積テーブルにMERGE
        sb.append(" MERGE /*+ ORDERED USE_NL(WSTU DSTUR) */ ");
        sb.append(" INTO DT_SAP_TEN_URIAGE_RUI DSTUR  ");
        sb.append("   USING WK_SAP_TEN_URIAGE WSTU  ");
        sb.append("     ON (  ");
        sb.append("       DSTUR.KEIJO_DT = WSTU.KEIJO_DT  ");
        sb.append("       AND DSTUR.TENPO_CD = WSTU.TENPO_CD  ");
        sb.append("       AND DSTUR.REGISTER_NO = WSTU.REGISTER_NO  ");
        sb.append("       AND DSTUR.TRANSACTION_NO = WSTU.TRANSACTION_NO  ");
        sb.append("       AND DSTUR.SHIHARAI_SYUBETSU_CD = WSTU.SHIHARAI_SYUBETSU_CD  ");
        sb.append("       AND DSTUR.SHIHARAI_SYUBETSU_SUB_CD = WSTU.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("       AND DSTUR.DATE_EXPIRY = WSTU.DATE_EXPIRY ");
        sb.append("       AND DSTUR.PYMT_AMT = WSTU.PYMT_AMT ");
        sb.append("       AND DSTUR.POINT_ISSUDE_DT = WSTU.POINT_ISSUDE_DT ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   DSTUR.POS_VL = WSTU.POS_VL ");
        sb.append("   , DSTUR.SAGAKU_VL = WSTU.SAGAKU_VL ");
        sb.append("   , DSTUR.ACTUAL_AMT = WSTU.ACTUAL_AMT ");
        sb.append("   , DSTUR.SHIHARAI_SYUBETSU_VN_NA = WSTU.SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , DSTUR.UPDATE_USER_ID = '" + BATCH_ID + "' ");
        sb.append("   , DSTUR.UPDATE_TS = '" + SYS_DATE + "' ");
        sb.append(" WHEN NOT MATCHED THEN INSERT (  ");
        sb.append("   DSTUR.KEIJO_DT ");
        sb.append("   , DSTUR.TENPO_CD ");
        sb.append("   , DSTUR.REGISTER_NO ");
        sb.append("   , DSTUR.TRANSACTION_NO ");
        sb.append("   , DSTUR.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , DSTUR.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , DSTUR.DATE_EXPIRY ");
        sb.append("   , DSTUR.PYMT_AMT ");
        sb.append("   , DSTUR.POS_VL ");
        sb.append("   , DSTUR.SAGAKU_VL ");
        sb.append("   , DSTUR.ACTUAL_AMT ");
        sb.append("   , DSTUR.SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , DSTUR.POINT_ISSUDE_DT ");
        sb.append("   , DSTUR.INSERT_USER_ID ");
        sb.append("   , DSTUR.INSERT_TS ");
        sb.append("   , DSTUR.UPDATE_USER_ID ");
        sb.append("   , DSTUR.UPDATE_TS ");
        sb.append(" )  ");
        sb.append(" VALUES (  ");
        sb.append("   WSTU.KEIJO_DT ");
        sb.append("   , WSTU.TENPO_CD ");
        sb.append("   , WSTU.REGISTER_NO ");
        sb.append("   , WSTU.TRANSACTION_NO ");
        sb.append("   , WSTU.SHIHARAI_SYUBETSU_CD ");
        sb.append("   , WSTU.SHIHARAI_SYUBETSU_SUB_CD ");
        sb.append("   , WSTU.DATE_EXPIRY ");
        sb.append("   , WSTU.PYMT_AMT ");
        sb.append("   , WSTU.POS_VL ");
        sb.append("   , WSTU.SAGAKU_VL ");
        sb.append("   , WSTU.ACTUAL_AMT ");
        sb.append("   , WSTU.SHIHARAI_SYUBETSU_VN_NA ");
        sb.append("   , WSTU.POINT_ISSUDE_DT ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append("   , '" + BATCH_ID + "' ");
        sb.append("   , '" + SYS_DATE + "' ");
        sb.append(" )  ");

        return sb.toString();
    }

    private String selectCountWK() {

        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");
        sb.append("   COUNT(*) AS COUNT  ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_TEN_URIAGE   ");

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
        sb.append("   FILEID = '" + SAP_SEND_FILE_URIAGE_TENPOURIAGE + "' ");
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
        sb.append("   FILEID = '" + SAP_SEND_FILE_URIAGE_TENPOURIAGE + "' ");
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
            DaoIf dao = new IfSapTenpoUriageDao();
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