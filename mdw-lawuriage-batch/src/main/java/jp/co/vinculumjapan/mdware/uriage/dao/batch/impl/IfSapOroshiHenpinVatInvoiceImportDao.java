package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.vinculumjapan.mdware.common.dao.output.DataImportDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.IfSapOroshiHenpinVatInvoiceImportDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiDataImportDaoUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 * <p>
 * タイトル: SAP IF 卸返品VATINVOICE取込 クラス
 * </p>
 * <p>
 * 説明: 卸返品VATINVOICEをファイルからloadする
 * </p>
 * 
 * @author VINX
 * @Version 1.00 (2016.10.06) A.Narita FIVIMART対応
 */

public class IfSapOroshiHenpinVatInvoiceImportDao implements DaoIf {

    /** バッチID */
    private static final String BATCH_ID = "URIB820040";
    /** バッチ名 */
    private static final String BATCH_NAME = "SAP IF 卸返品VATINVOICE ワーク作成処理";
    /** バッチ日 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** システム日付 */
    private static final String SYS_DATE = FiResorceUtility.getDBServerTime();
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** SAPファイルパス */
    private static final String SAP_GET_PATH = "SAP_GET_PATH";
    /** SAPファイルパス 文字列 */
    private static final String SAP_GET_PATH_TX = FiResorceUtility.getPropertie(SAP_GET_PATH);
    /** SAPファイル名 */
    private static final String SAP_FILE_NAME = "SAP_GET_FILE_OROSHI_HENPIN_VAT_INVOICE";
    /** SAPファイル名 文字列 */
    private static final String SAP_FILE_NAME_TX = FiResorceUtility.getPropertie(SAP_FILE_NAME);
    /** SAPバックアップパス名 */
    private static final String SAP_GET_BK_PATH = "SAP_GET_BK_PATH";
    /** カラム情報一覧 */
    List<String[]> columnInfoList = new ArrayList<String[]>();
    /** 入力Bean */
    private IfSapOroshiHenpinVatInvoiceImportDaoInputBean inputBean = null;
    /** CHKワーク用前回SEQ_NB最大値 */
    private String MAX_WK_SEQ_NB = "";
    /** 累積データ用前回SEQ_NB最大値 */
    private String MAX_SEQ_NB = "";
    /** チェック項目：正常 */
    private final String CHK_00 = "00";
    /** チェック項目：必須エラー */
    private final String CHK_01 = "01";
    /** チェック項目：区分値エラー */
    private final String CHK_11 = "11";
    /** チェック項目：文字種エラー */
    private final String CHK_12 = "12";
    /** チェック項目：桁数エラー */
    private final String CHK_13 = "13";
    /** チェック項目：日付書式エラー */
    private final String CHK_14 = "14";
    /** チェック項目：関連エラー */
    private final String CHK_20 = "20";
    /** チェック項目：重複エラー */
    private final String CHK_30 = "30";
    /** チェック項目：エラーあり（CHK_RESULTにのみセット） */
    private final String CHK_99 = "99";

    /**
     * 
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        /** ユーザーID */
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        invoker.infoLog(strUserId + "　：　SAP IF 卸返品VATINVOICE ワーク作成処理を開始します。");

        // ファイル名の一覧を取得する
        File file = new File(FiResorceUtility.getPropertie(SAP_GET_PATH));
        File files[] = file.listFiles();
        // ファイル名順でソートする（重複チェック時、最新ファイルを有効とするため）
        java.util.Arrays.sort(files, new java.util.Comparator<File>() {
            public int compare(File file1, File file2){
                return file1.getName().compareTo(file2.getName());
            }
        });

        // データディレクトリパスの設定
        FiDataImportDaoUtility.setDataDirPath(SAP_GET_PATH);
        // バックアップディレクトリパスの設定
        FiDataImportDaoUtility.setBackupDirPtah(SAP_GET_BK_PATH);

        boolean existFlg = false;
        PreparedStatementEx ps = null;
        
        invoker.infoLog(strUserId + "　：　前回のワークを削除します。");
        ps = invoker.getDataBase().prepareStatement(truncateWK_Chk());
        ps.executeQuery();
        invoker.infoLog(strUserId + "　：　前回のワークを削除しました。");
        
        ResultSet rs = null;
        ps = invoker.getDataBase().prepareStatement(selectColumnInfo());
        rs = ps.executeQuery();
        while (rs.next()) {
            String[] col = { rs.getString("COLUMN_NAME"), rs.getString("CHAR_LENGTH") };
            columnInfoList.add(col);
        }

        invoker.infoLog(strUserId + "　：　該当ファイル　" + SAP_GET_PATH_TX + "\\" + SAP_FILE_NAME_TX + "*");

        // 取得した一覧分ループする
        for (int i = 0; i < files.length; i++) {
            invoker.infoLog(strUserId + "　：　　" + (i + 1) + "件目　検知結果：" + files[i]);

            // 検知したファイル名
            inputBean.setDataFileName(new File(files[i].toString()).getName());

            // 検知したファイルと、システムコントロール設定値を比較
            if (StringUtils.contains(SAP_GET_PATH_TX + "\\" + inputBean.getDataFileName(), SAP_GET_PATH_TX + "\\" + SAP_FILE_NAME_TX)
                    && files[i].isFile()
                    && inputBean.getDataFileName().length() == 25
                    && StringUtils.equalsIgnoreCase(getSuffix(inputBean.getDataFileName()),"TXT")
            ) {
                existFlg = true;
                
                // IMPワーク truncate
                ps = null;
                ps = invoker.getDataBase().prepareStatement(truncateWK_Imp());
                ps.executeQuery();
                
                // IFファイル → IMPワーク load
                DataImportDaoOutputBean outBean = FiDataImportDaoUtility.executeDataAccess(invoker, inputBean.getDataFileName(), inputBean.getFormatFileName(), inputBean.getBackupFileName(),
                        inputBean.getLogFileName(), getSelectCountTable());
                invoker.getDataBase().commit();
                invoker.infoLog(strUserId + "　：　　" + (i + 1) + "件目　取込しました。（" + outBean.getResultCount() + "行）　" + files[i]);
                
                // IMPワーク → CHKワーク insert
                invoker.infoLog(strUserId + "　：　CHKワーク　insertを開始します。");
                ps = null;
                selectWkMaxSeqNb(invoker);
                ps = invoker.getDataBase().prepareStatement(insertWK_Chk(i + 1,inputBean.getDataFileName()));
                ps.executeQuery();
                invoker.getDataBase().commit();
                invoker.infoLog(strUserId + "　：　CHKワーク　insertを終了しました。");

            } else {
                invoker.infoLog(strUserId + "　：　　" + (i + 1) + "件目　該当ファイルではないためスキップしました。：" + SAP_GET_PATH_TX + "\\" + inputBean.getDataFileName());

            }
        }

        if (!existFlg) {
            invoker.infoLog(strUserId + "　：　該当ファイルがありませんでした。");

        } else {

            // 値変換
            invoker.infoLog(strUserId + "　：　CHKワーク　禁則文字除外を開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Exclude_NG_Character());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　禁則文字除外を終了します。");

            // 入力チェック
            invoker.infoLog(strUserId + "　：　CHKワーク　必須チェックを開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Required());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　必須チェックを終了します。");

            invoker.infoLog(strUserId + "　：　CHKワーク　値チェックを開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Value());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　値チェックを終了します。");
            
            invoker.infoLog(strUserId + "　：　CHKワーク　チェック結果まとめを開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Result());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　チェック結果まとめを終了します。");
            
            invoker.infoLog(strUserId + "　：　CHKワーク　日付書式チェックを開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_YYYYMMDD());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　日付書式チェックを終了します。");

            invoker.infoLog(strUserId + "　：　CHKワーク　関連チェック1を開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Relation_1());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　関連チェック1を終了します。");

            invoker.infoLog(strUserId + "　：　CHKワーク　チェック結果まとめを開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Result());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　チェック結果まとめを終了します。");
            
            invoker.infoLog(strUserId + "　：　CHKワーク　関連チェック2を開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Relation_2());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　関連チェック2を終了します。");

            invoker.infoLog(strUserId + "　：　CHKワーク　チェック結果まとめを開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Result());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　チェック結果まとめを終了します。");

            invoker.infoLog(strUserId + "　：　CHKワーク　重複チェックを開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Duplication());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　重複チェックを終了します。");

            invoker.infoLog(strUserId + "　：　CHKワーク　チェック結果まとめを開始します。");
            ps = null;
            ps = invoker.getDataBase().prepareStatement(updateWK_Chk_Result());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　CHKワーク　チェック結果まとめを終了します。");

            // CHKワーク → 累積データ insert
            invoker.infoLog(strUserId + "　：　累積データへの反映を開始します。");
            ps = null;
            selectMaxSeqNb(invoker);
            ps = invoker.getDataBase().prepareStatement(insertRuiseki());
            ps.executeQuery();
            invoker.getDataBase().commit();
            invoker.infoLog(strUserId + "　：　累積データへの反映を終了しました。");

            invoker.infoLog(strUserId + "　：　SAP IF 卸返品VATINVOICE ワーク作成処理を終了します。");
        }
    }

    /**
     * loader 結果行数の合計
     * 
     * @return
     */
    private String getSelectCountTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(" WK_SAP_OROSHI_HENV_IMP ");
        return sb.toString();
    }

    /**
     * truncate loader先 WKテーブル
     * 
     * @return
     */
    private String truncateWK_Imp() {
        StringBuilder sb = new StringBuilder();
        sb.append(" TRUNCATE TABLE WK_SAP_OROSHI_HENV_IMP ");
        return sb.toString();
    }

    /**
     * truncate chk WKテーブル
     * 
     * @return
     */
    private String truncateWK_Chk() {
        StringBuilder sb = new StringBuilder();
        sb.append(" TRUNCATE TABLE WK_SAP_OROSHI_HENV_CHK ");
        return sb.toString();
    }

    /**
     * insert chk WKテーブル
     * 
     * @return
     */
    private String insertWK_Chk(int fileSeqNb,String fileNm) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb_col = new StringBuilder();
        StringBuilder sb_val = new StringBuilder();
        
        int cnt_col = 0;
        int cnt_val = 0;
        int charLengthIntSum = 1;
        
        for (int i = 0; i < columnInfoList.size(); i++) {
            String columnName = columnInfoList.get(i)[0];
            String charLength = columnInfoList.get(i)[1];
            int charLengthInt = Integer.parseInt(charLength);
            
            if(cnt_col > 0){
                sb_col.append(" , ");
            }
            sb_col.append(columnName);
            cnt_col++;
            
            if(cnt_val > 0){
                sb_val.append(" , ");
            }
            if(StringUtils.equalsIgnoreCase(columnName, "INSERT_USER_ID")
            || StringUtils.equalsIgnoreCase(columnName, "UPDATE_USER_ID")){
                sb_val.append(" '" + BATCH_ID + "' AS " + columnName);
                cnt_val++;
            } else if (StringUtils.equalsIgnoreCase(columnName, "INSERT_TS")
                    || StringUtils.equalsIgnoreCase(columnName, "UPDATE_TS")){
                sb_val.append(" '" + SYS_DATE + "' AS " + columnName);
                cnt_val++;
            } else if (StringUtils.equalsIgnoreCase(columnName, "LOAD_TS")
                    || StringUtils.equalsIgnoreCase(columnName, "REC_NB")){
                sb_val.append(" " + columnName + " AS " + columnName);
                cnt_val++;
            } else if (StringUtils.equalsIgnoreCase(StringUtils.substring(columnName,0,4), "CHK_")){
                sb_val.append(" '" + CHK_00 + "' AS " + columnName);
                cnt_val++;
            } else if (StringUtils.equalsIgnoreCase(columnName, "SEQ_NB")){
                sb_val.append(" ROW_NUMBER() OVER (ORDER BY LOAD_TS, REC_NB) + " + MAX_WK_SEQ_NB + " AS " + columnName);
                cnt_val++;
            } else if (StringUtils.equalsIgnoreCase(columnName, "FILE_SEQ_NB")){
                sb_val.append(" " + String.valueOf(fileSeqNb) + " AS " + columnName);
                cnt_val++;
            } else if (StringUtils.equalsIgnoreCase(columnName, "FILE_NM")){
                sb_val.append(" '" + fileNm + "' AS " + columnName);
                cnt_val++;
            } else {
                sb_val.append(" RTRIM(SUBSTR(TXT_DATA, " + charLengthIntSum + ", "+ charLength + ")) AS " + columnName);
                charLengthIntSum = charLengthIntSum + charLengthInt;
                cnt_val++;
            }
        }
        
        sb.append(" INSERT ");
        sb.append(" INTO WK_SAP_OROSHI_HENV_CHK(  ");
        sb.append(sb_col);
        sb.append(" )  ");
        sb.append(" SELECT  ");
        sb.append(sb_val);
        sb.append(" FROM ");
        sb.append("   WK_SAP_OROSHI_HENV_IMP ");
        
        return sb.toString();
    }

    /**
     * 禁則文字除外
     * 
     * @return
     */
    private String updateWK_Chk_Exclude_NG_Character() {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE WK_SAP_OROSHI_HENV_CHK ");
        sb.append(" SET ");
        for (int i = 0; i < columnInfoList.size(); i++) {
            if(i > 0){
                sb.append(" , ");
            }
            String columnName = columnInfoList.get(i)[0];
            sb.append("   " + columnName + " = REPLACE ("+ columnName +", CHR(34)) ");
        }
        return sb.toString();
    }

    /**
     * 必須チェック
     * 
     * @return
     */
    private String updateWK_Chk_Required() {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE WK_SAP_OROSHI_HENV_CHK ");
        sb.append(" SET ");
        sb.append("   CHK_REF_1 = ( CASE WHEN REF_1 IS NULL THEN '" + CHK_01 + "' ELSE CHK_REF_1 END )  ");
        sb.append("   , CHK_INVOICE_NB = ( CASE WHEN INVOICE_NB IS NULL AND HENKOU_KB IN ('C', 'M') THEN '" + CHK_01 + "' ELSE CHK_INVOICE_NB END )  ");
        sb.append("   , CHK_INVOICE_KB = ( CASE WHEN INVOICE_KB IS NULL AND HENKOU_KB IN ('C', 'M') THEN '" + CHK_01 + "' ELSE CHK_INVOICE_KB END )  ");
        sb.append("   , CHK_INVOICE_RB = ( CASE WHEN INVOICE_RB IS NULL AND HENKOU_KB IN ('C', 'M') THEN '" + CHK_01 + "' ELSE CHK_INVOICE_RB END )  ");
        sb.append("   , CHK_INVOICE_HAKOU_DT = ( CASE WHEN INVOICE_HAKOU_DT IS NULL AND HENKOU_KB IN ('C', 'M') THEN '" + CHK_01 + "' ELSE CHK_INVOICE_HAKOU_DT END )  ");
        sb.append("   , CHK_HENKOU_KB = ( CASE WHEN HENKOU_KB IS NULL THEN '" + CHK_01 + "' ELSE CHK_HENKOU_KB END )  ");

        return sb.toString();
    }

    /**
     * 値チェック
     * 
     * @return
     */
    private String updateWK_Chk_Value() {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE WK_SAP_OROSHI_HENV_CHK ");
        sb.append(" SET ");
        sb.append("   CHK_REF_1 = (  ");
        sb.append("     CASE  ");
        sb.append("       WHEN TRIM(TRANSLATE(REF_1, '0123456789', ' ')) IS NOT NULL AND HENKOU_KB IN ('C', 'M')  ");
        sb.append("       THEN '" + CHK_12 + "'  ");
        sb.append("       WHEN LENGTH(TRIM(REF_1)) <> 20 AND HENKOU_KB IN ('C', 'M')  ");
        sb.append("       THEN '" + CHK_13 + "'  ");
        sb.append("       ELSE CHK_REF_1  ");
        sb.append("       END ");
        sb.append("   )  ");
        sb.append("   , CHK_INVOICE_HAKOU_DT = (  "); 
        sb.append("     CASE  ");
        sb.append("       WHEN TRIM(TRANSLATE(INVOICE_HAKOU_DT, '0123456789', ' ')) IS NOT NULL AND HENKOU_KB IN ('C', 'M')  ");
        sb.append("       THEN '" + CHK_12 + "'  ");
        sb.append("       WHEN LENGTH(TRIM(INVOICE_HAKOU_DT)) <> 8 AND HENKOU_KB IN ('C', 'M')  ");
        sb.append("       THEN '" + CHK_13 + "'  ");
        sb.append("       ELSE CHK_INVOICE_HAKOU_DT  ");
        sb.append("       END ");
        sb.append("   )  ");
        sb.append("   , CHK_HENKOU_KB = (  ");
        sb.append("     CASE  ");
        sb.append("       WHEN HENKOU_KB NOT IN ('C', 'M', 'D')  ");
        sb.append("       THEN '" + CHK_11 + "'  ");
        sb.append("       ELSE CHK_HENKOU_KB  ");
        sb.append("       END ");
        sb.append("   )  ");

        return sb.toString();
    }

    /**
     * 日付書式チェック
     *   前提として、事前に値チェックで数値かつ8桁であるデータのみチェックすること
     *   正常：
     *     1,3,5,7,8,10,12月 ・・・ 日が 1～31日であること
     *     4,6,9,11月 ・・・ 日が 1～30日であること
     *     2月 ・・・ 年がうるう年（4の倍数）以外であれば、日が1～28日であること
     *     2月 ・・・ 年がうるう年（4の倍数）であれば、日が1～29日であること
     *   異常：
     *     上記以外
     * @return
     */
    private String updateWK_Chk_YYYYMMDD() {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE WK_SAP_OROSHI_HENV_CHK ");
        sb.append(" SET ");
        sb.append("   CHK_INVOICE_HAKOU_DT = ( ");
        sb.append("     CASE  ");
        sb.append("     WHEN HENKOU_KB = 'D' THEN CHK_INVOICE_HAKOU_DT ");
        sb.append("     WHEN ");
        sb.append("      ( SUBSTR(INVOICE_HAKOU_DT, 5, 2) IN ('01', '03', '05', '07', '08', '10', '12')  ");
        sb.append("        AND SUBSTR(INVOICE_HAKOU_DT, 7, 2) >= '01' AND SUBSTR(INVOICE_HAKOU_DT, 7, 2) <= '31' )   ");//1,3,5,7,8,10,12月 ・・・ 日が 1～31日であること
        sb.append("     OR ");
        sb.append("      ( SUBSTR(INVOICE_HAKOU_DT, 5, 2) IN ('04', '06', '09', '11')  ");
        sb.append("        AND SUBSTR(INVOICE_HAKOU_DT, 7, 2) >= '01' AND SUBSTR(INVOICE_HAKOU_DT, 7, 2) <= '30' )  ");//4,6,9,11月 ・・・ 日が 1～30日であること
        sb.append("     OR ");
        sb.append("      ( MOD(TO_NUMBER(SUBSTR(INVOICE_HAKOU_DT, 1, 4)), 4) <> 0  ");
        sb.append("        AND SUBSTR(INVOICE_HAKOU_DT, 5, 2) = '02'  ");
        sb.append("        AND SUBSTR(INVOICE_HAKOU_DT, 7, 2) >= '01' AND SUBSTR(INVOICE_HAKOU_DT, 7, 2) <= '28') ");//2月 ・・・ 年がうるう年（4の倍数）以外であれば、日が1～28日であること
        sb.append("     OR  ");
        sb.append("      ( MOD(TO_NUMBER(SUBSTR(INVOICE_HAKOU_DT, 1, 4)), 4) = 0  ");
        sb.append("        AND SUBSTR(INVOICE_HAKOU_DT, 5, 2) = '02'  ");
        sb.append("        AND SUBSTR(INVOICE_HAKOU_DT, 7, 2) >= '01' AND SUBSTR(INVOICE_HAKOU_DT, 7, 2) <= '29') ");//2月 ・・・ 年がうるう年（4の倍数）であれば、日が1～29日であること
        sb.append("     THEN CHK_INVOICE_HAKOU_DT  ");
        sb.append("     ELSE '" + CHK_14 + "'  ");//上記を満たしていなければエラー
        sb.append("     END ) "); 
        sb.append(" WHERE "); 
        sb.append("   CHK_RESULT = '" + CHK_00 + "' ");//前提として、事前に値チェックで数値かつ8桁であるデータのみチェックすること

        return sb.toString();
    }
    
    /**
     * 関連チェック
     * 卸出荷伝票テーブルに該当の伝票が存在しない
     * 
     * @return
     */
    private String updateWK_Chk_Relation_1() {
        StringBuilder sb = new StringBuilder();
        sb.append(" MERGE ");
        sb.append(" INTO WK_SAP_OROSHI_HENV_CHK WSOHC  ");
        sb.append("   USING (  ");
        sb.append("     SELECT ");
        sb.append("       WSOHC.SEQ_NB ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_OROSHI_HENV_CHK WSOHC  ");
        sb.append("       LEFT OUTER JOIN DT_OROSHI_SYUKA_DENPYO DOSD  ");
        sb.append("         ON SUBSTR(WSOHC.REF_1,1,17) = DOSD.CYOHYO_DENPYO_NO  ");
        sb.append("         AND TO_NUMBER(SUBSTR(WSOHC.REF_1,20,1)) = DOSD.DENPYO_EDA_RB  ");
        sb.append("     WHERE ");
        sb.append("       WSOHC.CHK_RESULT = '" + CHK_00 + "'  ");
        sb.append("       AND DOSD.SEQ_NB IS NULL ");
        sb.append("   ) WSOHC_CHK  ");
        sb.append("     ON (  ");
        sb.append("       WSOHC.SEQ_NB = WSOHC_CHK.SEQ_NB  ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   WSOHC.CHK_REF_1 = '" + CHK_20 + "' ");

        return sb.toString();
    }
    
    /**
     * 関連チェック
     * 顧客マスタのVAT発行区分が1（FIVIが発行 = MDでVAT番号採番したデータ）
     * 
     * @return
     */
    private String updateWK_Chk_Relation_2() {
        StringBuilder sb = new StringBuilder();
        sb.append(" MERGE ");
        sb.append(" INTO WK_SAP_OROSHI_HENV_CHK WSOHC  ");
        sb.append("   USING (  ");
        sb.append("     SELECT ");
        sb.append("       WSOHC.SEQ_NB  ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_OROSHI_HENV_CHK WSOHC  ");
        sb.append("       INNER JOIN DT_OROSHI_SYUKA_DENPYO DOSD  ");
        sb.append("         ON DOSD.COMP_CD = '"+ COMP_CD +"'  ");
        sb.append("         AND SUBSTR(WSOHC.REF_1, 1, 17) = DOSD.CYOHYO_DENPYO_NO  ");
        sb.append("         AND TO_NUMBER(SUBSTR(WSOHC.REF_1, 20, 1)) = DOSD.DENPYO_EDA_RB  ");
        sb.append("       INNER JOIN R_KOKYAKU_KIHON RKK  ");
        sb.append("         ON RKK.KOKYAKU_KB = '1'  ");
        sb.append("         AND RKK.KOKYAKU_CD = DOSD.OROSHISAKI_CD  ");
        sb.append("         AND RKK.OROSHI_HENPIN_VATINVOICE_KB = '1'  ");
        sb.append("         AND RKK.YUKO_START_DT = (  ");
        sb.append("           SELECT ");
        sb.append("             MAX(RKK1.YUKO_START_DT)  ");
        sb.append("           FROM ");
        sb.append("             R_KOKYAKU_KIHON RKK1  ");
        sb.append("           WHERE ");
        sb.append("             RKK1.KOKYAKU_KB = '1'  ");
        sb.append("             AND RKK1.KOKYAKU_CD = DOSD.OROSHISAKI_CD  ");
        sb.append("             AND RKK1.YUKO_START_DT <= DOSD.SYUKA_DT ");
        sb.append("         )  ");
        sb.append("     WHERE ");
        sb.append("       WSOHC.CHK_RESULT = '" + CHK_00 + "'  ");
        sb.append("   ) WSOHC_CHK  ");
        sb.append("     ON (WSOHC.SEQ_NB = WSOHC_CHK.SEQ_NB)  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   WSOHC.CHK_REF_1 = '" + CHK_20 + "' "); 

        return sb.toString();
    }

    /**
     * 重複チェック 
     * キー重複発生時は、SEQ_NBが最大のレコードを優先させる
     * （＝最新のデータを有効にする）
     * 
     * @return
     */
    private String updateWK_Chk_Duplication() {
        StringBuilder sb = new StringBuilder();
        sb.append(" MERGE ");
        sb.append(" INTO WK_SAP_OROSHI_HENV_CHK WSOHC  ");
        sb.append("   USING (  ");
        sb.append("     SELECT  ");
        sb.append("       WSOHC.SEQ_NB ");
        sb.append("     FROM ");
        sb.append("       WK_SAP_OROSHI_HENV_CHK WSOHC  ");
        sb.append("     WHERE ");
        sb.append("       WSOHC.CHK_RESULT = '" + CHK_00 + "'  ");
        sb.append("       AND EXISTS  ");
        sb.append("       ( SELECT ");
        sb.append("           1 ");
        sb.append("         FROM  ");
        sb.append("           WK_SAP_OROSHI_HENV_CHK WSOHC_CHK ");
        sb.append("         WHERE ");
        sb.append("           WSOHC.SEQ_NB < WSOHC_CHK.SEQ_NB ");
        sb.append("           AND WSOHC.REF_1 = WSOHC_CHK.REF_1 ");
        sb.append("           AND WSOHC_CHK.CHK_RESULT = '" + CHK_00 + "' ");
        sb.append("       ) ");
        sb.append("   ) CHK  ");
        sb.append("     ON (  ");
        sb.append("       WSOHC.SEQ_NB = CHK.SEQ_NB  ");
        sb.append("     )  ");
        sb.append(" WHEN MATCHED THEN UPDATE SET ");
        sb.append("   WSOHC.CHK_REF_1 = '" + CHK_30 + "' ");

        return sb.toString();
    }

    /**
     * チェック結果まとめ
     * 
     * @return
     */
    private String updateWK_Chk_Result() {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE WK_SAP_OROSHI_HENV_CHK ");
        sb.append(" SET ");
        sb.append("   CHK_RESULT = (  ");
        sb.append("     CASE WHEN ");
        
        int cnt_col = 0;
        for (int i = 0; i < columnInfoList.size(); i++) {
            String columnName = columnInfoList.get(i)[0];
            if(StringUtils.equalsIgnoreCase(StringUtils.substring(columnName,0,4), "CHK_")){
                if(cnt_col > 0){
                    sb.append(" OR ");
                }
                sb.append(columnName +" <> '"+CHK_00+"'  ");
                cnt_col++;
            }
        }
        sb.append("       THEN '"+CHK_99+"'  ");
        sb.append("       ELSE '"+CHK_00+"'  ");
        sb.append("       END ");
        sb.append("   )  ");

        return sb.toString();
    }

    private String insertRuiseki() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb_col = new StringBuilder();
        StringBuilder sb_val = new StringBuilder();

        int cnt_col = 0;
        int charLengthIntSum = 1;
        
        for (int i = 0; i < columnInfoList.size(); i++) {
            String columnName = columnInfoList.get(i)[0];
            String charLength = columnInfoList.get(i)[1];
            int charLengthInt = Integer.parseInt(charLength);
            
            if(cnt_col > 0){
                sb_col.append(" , ");
            }
            sb_col.append(columnName);
            cnt_col++;
            
            if(!StringUtils.equalsIgnoreCase(columnName, "SEQ_NB")){
                sb_val.append(" , " + columnName);
                charLengthIntSum = charLengthIntSum + charLengthInt;
            }
        }
        
        sb.append(" INSERT ");
        sb.append(" INTO DT_SAP_OROSHI_HENV_RUI(  ");
        sb.append(sb_col);
        sb.append(" )  ");
        sb.append(" SELECT ");
        sb.append("   SEQ_NB + " + MAX_SEQ_NB + " ");
        sb.append(sb_val);
        sb.append(" FROM ");
        sb.append("   WK_SAP_OROSHI_HENV_CHK ");

        return sb.toString();
    }
    
    private void selectMaxSeqNb(DaoInvokerIf invoker) throws SQLException {
        PreparedStatementEx ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   NVL(MAX(SEQ_NB),0) AS SEQ_NB ");
        sb.append(" FROM ");
        sb.append("   DT_SAP_OROSHI_HENV_RUI ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        rs = ps.executeQuery();
        if (rs.next()) {
            // SEQ_NBを取得できたらその値を返却
            MAX_SEQ_NB = rs.getString("SEQ_NB");
        } else {
            // SEQ_NBを取得できなかったら0
            MAX_SEQ_NB = "0";
        }
    }
    
    private void selectWkMaxSeqNb(DaoInvokerIf invoker) throws SQLException {
        PreparedStatementEx ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("   NVL(MAX(SEQ_NB),0) AS SEQ_NB ");
        sb.append(" FROM ");
        sb.append("   WK_SAP_OROSHI_HENV_CHK ");
        ps = invoker.getDataBase().prepareStatement(sb.toString());
        rs = ps.executeQuery();
        if (rs.next()) {
            // SEQ_NBを取得できたらその値を返却
            MAX_WK_SEQ_NB = rs.getString("SEQ_NB");
        } else {
            // SEQ_NBを取得できなかったら0
            MAX_WK_SEQ_NB = "0";
        }
    }

    private String selectColumnInfo() {

        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ");
        sb.append("   COLUMN_NAME ");
        sb.append("   , CHAR_LENGTH ");
        sb.append(" FROM ");
        sb.append("   USER_TAB_COLUMNS  ");
        sb.append(" WHERE ");
        sb.append("   TABLE_NAME = 'WK_SAP_OROSHI_HENV_CHK' ");
        sb.append(" ORDER BY COLUMN_ID ");

        return sb.toString();
    }

    /**
     * IFファイルの存在をチェックする
     * 
     * @param String strDataFileDirPath データファイルディレクトリのパス
     * @param String strDataFileName データファイル名
     * 
     * @return IFファイルの存在チェック正しければtrue, 誤っていればfalse
     */
    public static boolean pathFileExists(String strDataFileDirPath, String strDataFileName) {

        // データファイル
        String dataFilePath = new File(strDataFileDirPath + "/" + strDataFileName).getAbsolutePath();

        // データファイル存在チェック
        if (!new File(dataFilePath).exists()) {
            return false;
        }
        return true;
    }
    
    /**
     * ファイル名から拡張子を返します。
     * @param fileName ファイル名
     * @return ファイルの拡張子
     */
    private String getSuffix(String fileName) {
        if (fileName == null)
            return "";
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(point + 1);
        }
        return fileName;
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {
        inputBean = (IfSapOroshiHenpinVatInvoiceImportDaoInputBean) input;
    }

    /**
     * アウトプットBeanを取得する
     * 
     * @return Object
     */
    public Object getOutputObject() throws Exception {
        return null;
    }
}
