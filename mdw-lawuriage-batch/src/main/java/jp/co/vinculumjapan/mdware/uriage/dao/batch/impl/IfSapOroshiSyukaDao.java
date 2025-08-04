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
 * タイトル：IfSapOroshiSyukaDao クラス
 * </p>
 * <p>
 * 説明：SAP IF 卸出荷 IFファイル出力処理
 * </p>
 * <p>
 * 著作権：Copyright (c) 2017
 * </p>
 * <p>
 * 会社名：VINX
 * </p>
 * 
 * @author VINX
 * @version 1.00 (2017.1.9) A.Narita FIVIMART対応
 */
public class IfSapOroshiSyukaDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB810091";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 卸出荷 IFファイル出力処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** SAPファイルパス */
    private static final String SAP_SEND_PATH = FiResorceUtility.getPropertie("SAP_SEND_PATH");
    /** SAPファイル名 */
    private static final String SAP_SEND_FILE_URIAGE_OROSHI_SYUKA = FiResorceUtility.getPropertie("SAP_SEND_FILE_URIAGE_OROSHI_SYUKA");
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
        invoker.infoLog(strUserId + "　：　SAP IF 卸出荷 IFファイル出力処理を開始します。");

        PreparedStatementEx ps_cnt = null;
        PreparedStatementEx ps = null;
        PreparedStatementEx ps_merge = null;
        ResultSet rs_cnt = null;
        ResultSet rs = null;

        /** 卸出荷データ件数 */
        int count = 0;
        /** 卸出荷データ件数取得処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　卸出荷ワーク件数取得処理を開始します。");
        // 卸出荷データ件数取得SQL
        ps_cnt = invoker.getDataBase().prepareStatement(countWk());
        // SQLを実行する
        rs_cnt = ps_cnt.executeQuery();
        if (rs_cnt.next()) {
            count = rs_cnt.getInt("COUNT");
        }
        // 実行後処理
        // ログを出力する
        invoker.infoLog(strUserId + "　：　" + count + "件の卸出荷ワークを取得しました。");
        invoker.infoLog(strUserId + "　：　卸出荷ワーク件数取得処理を終了します。");

        /** 累積テーブルに無いデータの抽出処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　変更区分「C」「D」分の抽出を開始します。");

        // 卸出荷データ出力処理SQL
        ps = invoker.getDataBase().prepareStatement(select());
        // SQLを実行する
        rs = ps.executeQuery();

        // ログを出力する
        invoker.infoLog(strUserId + "　：　変更区分「C」「D」分の抽出を終了します。");

        /** 累積データの更新処理 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　累積データの作成を開始します。");

        // 卸出荷データ更新処理SQL
        ps_merge = invoker.getDataBase().prepareStatement(mergeRuiseki());
        // SQLを実行する
        ps_merge.executeQuery();
        // ログを出力する

        invoker.infoLog(strUserId + "　：　累積データの作成を終了します。");

        /** ファイル作成 */
        // ログを出力
        invoker.infoLog(strUserId + "　：　ファイル作成を開始します。");
        // ファイル名の取得
        File dataFile = new File(SAP_SEND_PATH + "/" + SAP_SEND_FILE_URIAGE_OROSHI_SYUKA + "_" + BATCH_DT + "_" + selectFileNo(invoker) + ".txt");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
        // ファイル出力
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        count = 0;

        try {
            count = count + createFile(rs, writer);
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
        invoker.infoLog(strUserId + "　：　SAP IF 卸出荷 IFファイル出力処理を終了します。");
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
            line.append(rs.getString("INVOICE_AUTONO_NB_KB"));
            line.append(rs.getString("INVOICE_AUTONO_RB"));
            line.append(rs.getString("REF_1"));
            line.append(rs.getString("REF_2"));
            line.append(rs.getString("OROSHISAKI_CD"));
            line.append(rs.getString("BAIKA_ZEINUKI_VL"));
            line.append(rs.getString("TAX_RT"));
            line.append(rs.getString("BAIKA_ZEIGAKU_VL"));
            line.append(rs.getString("SYUKA_TENPO_CD"));
            line.append(rs.getString("DAIBUNRUI2_CD"));
            line.append(rs.getString("INVOICE_KEYINNO_NB_KB"));
            line.append(rs.getString("INVOICE_KEYINNO_RB"));
            line.append(LINE_FEED_CHAR);

            writer.write(line.toString());
            cnt++;
        }
        return cnt;
    }

    private String select() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   CASE  ");
        sb.append("     WHEN TRIM(WSOS.HENKOU_KB) = '1'  ");
        sb.append("     THEN 'D'  ");
        sb.append("     WHEN TRIM(WSOS.HENKOU_KB) = '0' AND DSOSR.HENKOU_KB IS NOT NULL THEN 'M'  ");
        sb.append("     ELSE 'C'  ");
        sb.append("     END AS HENKOU_KB ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.SAKUSEI_DT),' '), 8, ' ') AS SAKUSEI_DT ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.SYUKA_DT),' '), 8, ' ') AS KEIJO_DT ");
        sb.append("   , RPAD(  ");
        sb.append("     NVL(TRIM(WSOS.INVOICE_AUTONO_NB) || TRIM(WSOS.INVOICE_AUTONO_KB),' ') ");
        sb.append("     , 25 ");
        sb.append("     , ' ' ");
        sb.append("   ) AS INVOICE_AUTONO_NB_KB ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.INVOICE_AUTONO_RB),' '), 16, ' ') AS INVOICE_AUTONO_RB ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.REF_1),' '), 20, ' ') AS REF_1 ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.REF_2),' '), 20, ' ') AS REF_2 ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.OROSHISAKI_CD),' '), 10, ' ') AS OROSHISAKI_CD ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       (NVL(WSOS.BAIKA_ZEINUKI_VL, 0)) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS BAIKA_ZEINUKI_VL ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.TAX_RT),' '), 2, ' ') AS TAX_RT ");
        sb.append("   , TRIM(  ");
        sb.append("     TO_CHAR(  ");
        sb.append("       (NVL(WSOS.BAIKA_ZEIGAKU_VL, 0)) * 100 ");
        sb.append("       , '000000000000000' ");
        sb.append("     ) ");
        sb.append("   ) AS BAIKA_ZEIGAKU_VL ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.SYUKA_TENPO_CD),' '), 10, ' ') AS SYUKA_TENPO_CD ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.DAIBUNRUI2_CD),' '), 10, ' ') AS DAIBUNRUI2_CD ");
        sb.append("   , RPAD(  ");
        sb.append("     NVL(TRIM(WSOS.INVOICE_KEYINNO_NB) || TRIM(WSOS.INVOICE_KEYINNO_KB),' ') ");
        sb.append("     , 25 ");
        sb.append("     , ' ' ");
        sb.append("   ) AS INVOICE_KEYINNO_NB_KB ");
        sb.append("   , RPAD(NVL(TRIM(WSOS.INVOICE_KEYINNO_RB),' '), 16, ' ') AS INVOICE_KEYINNO_RB  ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_OROSHI_SYUKA WSOS  ");
        sb.append("   LEFT OUTER JOIN DT_SAP_OROSHI_SYUKA_RUI DSOSR ");
        sb.append("     ON WSOS.HENKOU_KB = DSOSR.HENKOU_KB ");
        sb.append("     AND WSOS.REF_1 = DSOSR.REF_1 ");
        sb.append("     AND WSOS.MEISAI_KB = DSOSR.MEISAI_KB ");
        sb.append("     AND WSOS.OROSHISAKI_CD = DSOSR.OROSHISAKI_CD ");
        sb.append("     AND WSOS.TAX_RT = DSOSR.TAX_RT ");
        sb.append("     AND WSOS.DAIBUNRUI2_CD = DSOSR.DAIBUNRUI2_CD ");
        sb.append(" WHERE ");
        sb.append("   DSOSR.HENKOU_KB IS NULL ");
        sb.append("   OR NVL(TRIM(WSOS.INVOICE_AUTONO_NB),' ') <> NVL(TRIM(DSOSR.INVOICE_AUTONO_NB),' ') ");
        sb.append("   OR NVL(TRIM(WSOS.INVOICE_AUTONO_KB),' ') <> NVL(TRIM(DSOSR.INVOICE_AUTONO_KB),' ') ");
        sb.append("   OR NVL(TRIM(WSOS.INVOICE_AUTONO_RB),' ') <> NVL(TRIM(DSOSR.INVOICE_AUTONO_RB),' ') ");
        sb.append("   OR NVL(TRIM(WSOS.INVOICE_KEYINNO_NB),' ') <> NVL(TRIM(DSOSR.INVOICE_KEYINNO_NB),' ') ");
        sb.append("   OR NVL(TRIM(WSOS.INVOICE_KEYINNO_KB),' ') <> NVL(TRIM(DSOSR.INVOICE_KEYINNO_KB),' ') ");
        sb.append("   OR NVL(TRIM(WSOS.INVOICE_KEYINNO_RB),' ') <> NVL(TRIM(DSOSR.INVOICE_KEYINNO_RB),' ') ");
        sb.append(" ORDER BY ");
        sb.append("   WSOS.HENKOU_KB ");
        sb.append("   , WSOS.REF_1 ");
        sb.append("   , WSOS.MEISAI_KB ");
        sb.append("   , WSOS.OROSHISAKI_CD ");
        sb.append("   , WSOS.TAX_RT ");
        sb.append("   , WSOS.DAIBUNRUI2_CD "); 
        return sb.toString();
    }

    private String mergeRuiseki() {
        // 累積テーブルにMERGE
        StringBuffer sb = new StringBuffer();
        sb.append(" MERGE /*+ ORDERED USE_NL(WSOS DSOSR) */ ");
        sb.append(" INTO DT_SAP_OROSHI_SYUKA_RUI DSOSR  ");
        sb.append("   USING WK_SAP_OROSHI_SYUKA WSOS  ");
        sb.append("     ON (  ");
        sb.append("       DSOSR.HENKOU_KB = WSOS.HENKOU_KB  ");
        sb.append("       AND DSOSR.REF_1 = WSOS.REF_1  ");
        sb.append("       AND DSOSR.MEISAI_KB = WSOS.MEISAI_KB  ");
        sb.append("       AND DSOSR.OROSHISAKI_CD = WSOS.OROSHISAKI_CD  ");
        sb.append("       AND DSOSR.TAX_RT = WSOS.TAX_RT  ");
        sb.append("       AND DSOSR.DAIBUNRUI2_CD = WSOS.DAIBUNRUI2_CD ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE  ");
        sb.append(" SET ");
        sb.append("   DSOSR.SAKUSEI_DT = WSOS.SAKUSEI_DT ");
        sb.append("   , DSOSR.SYUKA_DT = WSOS.SYUKA_DT ");
        sb.append("   , DSOSR.REF_2 = WSOS.REF_2 ");
        sb.append("   , DSOSR.BAIKA_ZEINUKI_VL = WSOS.BAIKA_ZEINUKI_VL ");
        sb.append("   , DSOSR.BAIKA_ZEIGAKU_VL = WSOS.BAIKA_ZEIGAKU_VL ");
        sb.append("   , DSOSR.SYUKA_TENPO_CD = WSOS.SYUKA_TENPO_CD ");
        sb.append("   , DSOSR.INVOICE_KEYINNO_NB = WSOS.INVOICE_KEYINNO_NB ");
        sb.append("   , DSOSR.INVOICE_KEYINNO_KB = WSOS.INVOICE_KEYINNO_KB ");
        sb.append("   , DSOSR.INVOICE_KEYINNO_RB = WSOS.INVOICE_KEYINNO_RB ");
        sb.append("   , DSOSR.INVOICE_AUTONO_NB = WSOS.INVOICE_AUTONO_NB ");
        sb.append("   , DSOSR.INVOICE_AUTONO_KB = WSOS.INVOICE_AUTONO_KB ");
        sb.append("   , DSOSR.INVOICE_AUTONO_RB = WSOS.INVOICE_AUTONO_RB ");
        sb.append("   , DSOSR.UPDATE_USER_ID = '"+ BATCH_ID +"' ");
        sb.append("   , DSOSR.UPDATE_TS = '"+ SYS_DATE +"' ");
        sb.append(" WHEN NOT MATCHED THEN  ");
        sb.append(" INSERT (  ");
        sb.append("   DSOSR.HENKOU_KB ");
        sb.append("   , DSOSR.REF_1 ");
        sb.append("   , DSOSR.MEISAI_KB ");
        sb.append("   , DSOSR.OROSHISAKI_CD ");
        sb.append("   , DSOSR.TAX_RT ");
        sb.append("   , DSOSR.DAIBUNRUI2_CD ");
        sb.append("   , DSOSR.SAKUSEI_DT ");
        sb.append("   , DSOSR.SYUKA_DT ");
        sb.append("   , DSOSR.REF_2 ");
        sb.append("   , DSOSR.BAIKA_ZEINUKI_VL ");
        sb.append("   , DSOSR.BAIKA_ZEIGAKU_VL ");
        sb.append("   , DSOSR.SYUKA_TENPO_CD ");
        sb.append("   , DSOSR.INVOICE_KEYINNO_NB ");
        sb.append("   , DSOSR.INVOICE_KEYINNO_KB ");
        sb.append("   , DSOSR.INVOICE_KEYINNO_RB ");
        sb.append("   , DSOSR.INVOICE_AUTONO_NB ");
        sb.append("   , DSOSR.INVOICE_AUTONO_KB ");
        sb.append("   , DSOSR.INVOICE_AUTONO_RB ");
        sb.append("   , DSOSR.INSERT_USER_ID ");
        sb.append("   , DSOSR.INSERT_TS ");
        sb.append("   , DSOSR.UPDATE_USER_ID ");
        sb.append("   , DSOSR.UPDATE_TS ");
        sb.append(" )  ");
        sb.append(" VALUES (  ");
        sb.append("   WSOS.HENKOU_KB ");
        sb.append("   , WSOS.REF_1 ");
        sb.append("   , WSOS.MEISAI_KB ");
        sb.append("   , WSOS.OROSHISAKI_CD ");
        sb.append("   , WSOS.TAX_RT ");
        sb.append("   , WSOS.DAIBUNRUI2_CD ");
        sb.append("   , WSOS.SAKUSEI_DT ");
        sb.append("   , WSOS.SYUKA_DT ");
        sb.append("   , WSOS.REF_2 ");
        sb.append("   , WSOS.BAIKA_ZEINUKI_VL ");
        sb.append("   , WSOS.BAIKA_ZEIGAKU_VL ");
        sb.append("   , WSOS.SYUKA_TENPO_CD ");
        sb.append("   , WSOS.INVOICE_KEYINNO_NB ");
        sb.append("   , WSOS.INVOICE_KEYINNO_KB ");
        sb.append("   , WSOS.INVOICE_KEYINNO_RB ");
        sb.append("   , WSOS.INVOICE_AUTONO_NB ");
        sb.append("   , WSOS.INVOICE_AUTONO_KB ");
        sb.append("   , WSOS.INVOICE_AUTONO_RB ");
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
        sb.append("   WK_SAP_OROSHI_SYUKA ");
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
        sb.append("   FILEID = '"+ SAP_SEND_FILE_URIAGE_OROSHI_SYUKA +"' ");
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
        sb.append("   FILEID = '"+ SAP_SEND_FILE_URIAGE_OROSHI_SYUKA +"' ");
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
            DaoIf dao = new IfSapOroshiSyukaDao();
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