package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.mdware.uriage.util.CrmDataExporter;
import jp.co.vinculumjapan.mdware.uriage.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用売上決済データ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */

public class IfCrmUriageKessaiCreateDao implements DaoIf {
    /** CSVファイル名（売上決済データ） */
    private static final String FILE_NAME = "HKCR0020";
    /** ログ出力用変数 */
    private static final String BATCH_NAME = "海外CRM用売上決済データ作成処理";
    /** バッチ日付取得 */
    private static final String BATCH_DT = FiResorceUtility.getPropertie("BATCH_DT");

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
        CrmDataExporter exporter = new CrmDataExporter(invoker, BATCH_NAME);
        exporter.fileName = FILE_NAME;
        exporter.queryString = getSelDataSQL();
        exporter.columnSpecs.add(new ColumnSpec("EIGYO_DT", 8));
        exporter.columnSpecs.add(new ColumnSpec("STORE", 4));
        exporter.columnSpecs.add(new ColumnSpec("POS", 3));
        exporter.columnSpecs.add(new ColumnSpec("TRANS_NO", 13));
        exporter.columnSpecs.add(new ColumnSpec("TORI_TIME", 4));
        exporter.columnSpecs.add(new ColumnSpec("MEM_CARD", 16, true));
        exporter.columnSpecs.add(new ColumnSpec("PYMT_TYPE2", 7));
        exporter.columnSpecs.add(new ColumnSpec("PYMT_AMT", 23));
        exporter.export();
    }

    @Override
    public void setInputObject(Object input) throws Exception {

    }

    @Override
    public Object getOutputObject() throws Exception {
        return null;
    }
    
    /**
     * 売上決済データ取得SQL
     *
     * @return SQL
     */
    private String getSelDataSQL() {
        StringBuffer sb = new StringBuffer();

        sb.append("     SELECT ");
        sb.append("         DPBT.EIGYO_DT ");
        sb.append("         , DPBT.STORE ");
        sb.append("         , DPBT.POS ");
        sb.append("         , (DPBT.STORE || DPBT.POS || DPBT.TRANS_NO) AS TRANS_NO ");
        sb.append("         , DPBT.TORI_TIME ");
        sb.append("         , DPBT.MEM_CARD ");
        sb.append("         , DPCP.PYMT_TYPE2 ");
        sb.append("         , DPCP.PYMT_AMT ");
        sb.append("     FROM ");
        sb.append("         DT_POS_B_TOTAL DPBT ");
        sb.append("     INNER JOIN ");
        sb.append("         DT_POS_C_PAYMENT DPCP ");
        sb.append("         ON ");
        sb.append("             DPCP.STORE = DPBT.STORE ");
        sb.append("             AND DPCP.POS = DPBT.POS ");
        sb.append("             AND DPCP.TRANS_NO = DPBT.TRANS_NO ");
        sb.append("             AND DPCP.CASHIER_ID = DPBT.CASHIER_ID ");
        sb.append("             AND DPCP.EIGYO_DT = DPBT.EIGYO_DT ");
        sb.append("     WHERE ");
        sb.append("         DPBT.EIGYO_DT = " + BATCH_DT);
        
        return sb.toString();
    }
}