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
 * タイトル：IfSapIsanNyuryokuDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 違算入力 IFファイル出力処理
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
public class IfSapIsanNyuryokuDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810011";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 違算入力 IFファイル出力処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** SAPファイルパス */
    private static final String SAP_SEND_PATH = FiResorceUtility.getPropertie("SAP_SEND_PATH");
    /** SAPファイル名 */
    private static final String SAP_SEND_FILE_URIAGE_ISANN = FiResorceUtility.getPropertie("SAP_SEND_FILE_URIAGE_ISAN");
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
        invoker.infoLog(strUserId + "　：　SAP IF 違算入力 IFファイル出力処理を開始します。");

        PreparedStatementEx ps_cnt = null;
        PreparedStatementEx ps_c = null;
        PreparedStatementEx ps_m = null;
        PreparedStatementEx ps_insert = null;
        ResultSet rs_cnt = null;
        ResultSet rs_c = null;
        ResultSet rs_m = null;

        /** 違算入力データ件数 */
        int count = 0;
        /** 違算入力データ件数取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　違算入力ワーク件数取得処理を開始します。");
        // 違算入力データ件数取得SQL
        ps_cnt = invoker.getDataBase().prepareStatement(countWk());
        // SQLを実行する
        rs_cnt = ps_cnt.executeQuery();
        if (rs_cnt.next()) {
            count = rs_cnt.getInt("COUNT");
        }
        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　" + count + "件の違算入力ワークを取得しました。");
        invoker.infoLog(strUserId + "　：　違算入力ワーク件数取得処理を終了します。");

        /** 累積テーブルに無いデータの抽出処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を開始します。");

        // 違算入力データ出力処理SQL(変更区分「C」)
        ps_c = invoker.getDataBase().prepareStatement(select_C());
        // SQLを実行する
        rs_c = ps_c.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「C」分の抽出を終了します。");

        /** 累積テーブルに有るデータの抽出処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を開始します。");

        // 違算入力データ出力処理SQL(変更区分「M」)
        ps_m = invoker.getDataBase().prepareStatement(select_M());
        // SQLを実行する
        rs_m = ps_m.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「M」分の抽出を終了します。");

        /** 累積データの更新処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　累積データの作成を開始します。");

        // 違算入力データ更新処理SQL
        ps_insert = invoker.getDataBase().prepareStatement(mergeRuiseki());
        // SQLを実行する
        ps_insert.executeQuery();
        // ログを出力する

        invoker.infoLog(strUserId + "　：　累積データの作成を終了します。");

        /** ファイル作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ファイル作成を開始します。");
        // ファイル名の取得
        File dataFile = new File(SAP_SEND_PATH + "/" + SAP_SEND_FILE_URIAGE_ISANN + "_" + BATCH_DT + "_" + selectFileNo(invoker) + ".txt");

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
        invoker.infoLog(strUserId + "　：　SAP IF 違算入力 IFファイル出力処理を終了します。");
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
            line.append(rs.getString("TOUROKU_KINGAKU"));
            line.append(rs.getString("ISAN_PLUS"));
            line.append(rs.getString("ISAN_MINUS"));
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
        sb.append(" SELECT /*+ ORDERED USE_NL(WSI DSIR) */ ");
        sb.append("   'C' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_ISAN WSI ");
        sb.append("   LEFT OUTER JOIN DT_SAP_ISAN_RUI DSIR ");
        sb.append("     ON WSI.KEIJO_DT = DSIR.KEIJO_DT ");
        sb.append("     AND WSI.TENPO_CD = DSIR.TENPO_CD ");
        sb.append(" WHERE ");
        sb.append("   DSIR.KEIJO_DT IS NULL ");
        return sb.toString();
    }

    private String select_M() {
        StringBuffer sb = new StringBuffer();
        // 累積テーブルにあり、変更あるデータを「M」として抽出
        sb.append(" SELECT /*+ ORDERED USE_NL(WSI DSIR) */ ");
        sb.append("   'M' AS HENKOU_KB ");
        sb.append(selectColumn());
        sb.append(" FROM ");
        sb.append("   WK_SAP_ISAN WSI ");
        sb.append("   INNER JOIN DT_SAP_ISAN_RUI DSIR ");
        sb.append("     ON WSI.KEIJO_DT = DSIR.KEIJO_DT ");
        sb.append("     AND WSI.TENPO_CD = DSIR.TENPO_CD ");
        sb.append("     AND ( NVL(WSI.ARIDAKA_VL,0) <> NVL(DSIR.ARIDAKA_VL,0) ");
        sb.append("           OR NVL(WSI.ISAN_PLUS,0) <> NVL(DSIR.ISAN_PLUS,0) ");
        sb.append("           OR NVL(WSI.ISAN_MINUS,0) <> NVL(DSIR.ISAN_MINUS,0) ");
        sb.append("         ) ");
        return sb.toString();
    }
    
    private String selectColumn(){
        // 「C」と「M」の共通項目
        StringBuffer sb = new StringBuffer();
        sb.append("   , '" + BATCH_DT + "' AS SAKUSEIBI ");
        sb.append("   , TRIM(WSI.KEIJO_DT) AS KEIJO_DT ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSI.ARIDAKA_VL, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS TOUROKU_KINGAKU ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSI.ISAN_PLUS, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS ISAN_PLUS ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       NVL(WSI.ISAN_MINUS, 0) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS ISAN_MINUS ");
        sb.append("   , RPAD(WSI.TENPO_CD, 10, ' ') AS TENPO_CD  ");
        return sb.toString();
    }

    private String mergeRuiseki() {
        // 累積テーブルにMERGE
        StringBuffer sb = new StringBuffer();
        sb.append(" MERGE /*+ ORDERED USE_NL(WSI DSIR) */ ");
        sb.append(" INTO DT_SAP_ISAN_RUI DSIR  ");
        sb.append("   USING WK_SAP_ISAN WSI  ");
        sb.append("     ON (  ");
        sb.append("       DSIR.KEIJO_DT = WSI.KEIJO_DT  ");
        sb.append("       AND DSIR.TENPO_CD = WSI.TENPO_CD ");
        sb.append("     ) ");
        sb.append(" WHEN MATCHED THEN UPDATE  ");
        sb.append(" SET ");
        sb.append("   DSIR.URIAGE_VL = WSI.URIAGE_VL ");
        sb.append("   , DSIR.ARIDAKA_VL = WSI.ARIDAKA_VL ");
        sb.append("   , DSIR.ISAN_PLUS = WSI.ISAN_PLUS ");
        sb.append("   , DSIR.ISAN_MINUS = WSI.ISAN_MINUS ");
        sb.append("   , DSIR.UPDATE_USER_ID = '"+ BATCH_ID +"' ");
        sb.append("   , DSIR.UPDATE_TS = '"+ SYS_DATE +"' ");
        sb.append(" WHEN NOT MATCHED THEN  ");
        sb.append(" INSERT (  ");
        sb.append("   DSIR.KEIJO_DT ");
        sb.append("   , DSIR.TENPO_CD ");
        sb.append("   , DSIR.URIAGE_VL ");
        sb.append("   , DSIR.ARIDAKA_VL ");
        sb.append("   , DSIR.ISAN_PLUS ");
        sb.append("   , DSIR.ISAN_MINUS ");
        sb.append("   , DSIR.INSERT_USER_ID ");
        sb.append("   , DSIR.INSERT_TS ");
        sb.append("   , DSIR.UPDATE_USER_ID ");
        sb.append("   , DSIR.UPDATE_TS ");
        sb.append(" )  ");
        sb.append(" VALUES (  ");
        sb.append("   WSI.KEIJO_DT ");
        sb.append("   , WSI.TENPO_CD ");
        sb.append("   , WSI.URIAGE_VL ");
        sb.append("   , WSI.ARIDAKA_VL ");
        sb.append("   , WSI.ISAN_PLUS ");
        sb.append("   , WSI.ISAN_MINUS ");
        sb.append("   , '"+ BATCH_ID +"' ");
        sb.append("   , '"+ SYS_DATE +"' ");
        sb.append("   , '"+ BATCH_ID +"' ");
        sb.append("   , '"+ SYS_DATE +"' ");
        sb.append(" )  ");
        return sb.toString();
    }

    private String countWk() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   COUNT(*) AS COUNT ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_ISAN ");
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
        sb.append("   FILEID = '"+ SAP_SEND_FILE_URIAGE_ISANN +"' ");
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
        sb.append("   FILEID = '"+ SAP_SEND_FILE_URIAGE_ISANN +"' ");
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
            DaoIf dao = new IfSapIsanNyuryokuDao();
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