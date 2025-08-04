package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.mdware.uriage.util.CrmDataExporter;
import jp.co.vinculumjapan.mdware.uriage.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用売上明細データ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */

public class IfCrmUriageMesaiCreateDao implements DaoIf {
    /** CSVファイル名（売上明細データ） */
    private static final String FILE_NAME = "HKCR0010";
    /** ログ出力用変数 */
    private static final String BATCH_NAME = "海外CRM用売上明細データ作成処理";
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
        exporter.columnSpecs.add(new ColumnSpec("SKU", 13));
        exporter.columnSpecs.add(new ColumnSpec("QTY", 9));
        exporter.columnSpecs.add(new ColumnSpec("ACTUAL_SELL", 23));
        exporter.columnSpecs.add(new ColumnSpec("GROSS_AMT", 23));
        exporter.columnSpecs.add(new ColumnSpec("DISCOUNT_AMOUNT", 23));
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
     * 売上明細データ取得SQL
     *
     * @return SQL
     */
    private String getSelDataSQL() {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");
        sb.append("     EIGYO_DT ");
        sb.append("     , STORE ");
        sb.append("     , POS ");
        sb.append("     , TRANS_NO ");
        sb.append("     , TORI_TIME ");
        sb.append("     , MEM_CARD ");
        sb.append("     , SKU ");
        sb.append("     , QTY ");
        sb.append("     , ACTUAL_SELL ");
        sb.append("     , CASE ");
        sb.append("         WHEN ");
        sb.append("             GROSS_AMT < 0 ");
        sb.append("         THEN ");
        sb.append("             '-' || LPAD(TO_CHAR(ABS(GROSS_AMT), 'FM9999999999999999990.00'), 22, '0') ");
        sb.append("         ELSE ");
        sb.append("             LPAD(TO_CHAR(ABS(GROSS_AMT), 'FM9999999999999999990.00'), 23, '0') ");
        sb.append("         END GROSS_AMT ");
        sb.append("     , CASE ");
        sb.append("         WHEN ");
        sb.append("             DISCOUNT_AMOUNT < 0 ");
        sb.append("         THEN ");
        sb.append("             '-' || LPAD(TO_CHAR(ABS(DISCOUNT_AMOUNT), 'FM9999999999999999990.00'), 22, '0') ");
        sb.append("         ELSE ");
        sb.append("             LPAD(TO_CHAR(ABS(DISCOUNT_AMOUNT), 'FM9999999999999999990.00'), 23, '0') ");
        sb.append("         END DISCOUNT_AMOUNT ");
        sb.append(" FROM ( ");
        sb.append("     SELECT ");
        sb.append("         DPBT.EIGYO_DT ");
        sb.append("         , DPBT.STORE ");
        sb.append("         , DPBT.POS ");
        sb.append("         , (DPBT.STORE || DPBT.POS || DPBT.TRANS_NO) AS TRANS_NO ");
        sb.append("         , DPBT.TORI_TIME ");
        sb.append("         , DPBT.MEM_CARD ");
        sb.append("         , DPAI.SKU ");
        sb.append("         , DPAI.QTY ");
        sb.append("         , DPAI.ACTUAL_SELL ");
        sb.append("         , (TO_NUMBER(ACTUAL_SELL) - (NVL(RTR.GENTANKA_VL, RS.GENTANKA_VL) * TO_NUMBER(QTY))) AS GROSS_AMT ");
        sb.append("         , (TO_NUMBER(REG_SELL) - TO_NUMBER(ACTUAL_SELL)) AS DISCOUNT_AMOUNT ");
        sb.append("     FROM ");
        sb.append("         DT_POS_A_ITEM DPAI ");
        sb.append("     INNER JOIN ");
        sb.append("         DT_POS_B_TOTAL DPBT ");
        sb.append("         ON ");
        sb.append("             DPAI.STORE = DPBT.STORE ");
        sb.append("             AND DPAI.POS = DPBT.POS ");
        sb.append("             AND DPAI.TRANS_NO = DPBT.TRANS_NO ");
        sb.append("             AND DPAI.CASHIER_ID = DPBT.CASHIER_ID ");
        sb.append("             AND DPAI.EIGYO_DT = DPBT.EIGYO_DT ");
        sb.append("     INNER JOIN ");
        sb.append("         R_SYOHIN RS ");
        sb.append("         ON ");
        sb.append("             DPAI.SKU = RS.SYOHIN_CD ");
        sb.append("             AND RS.YUKO_DT = ( ");
        sb.append("                 SELECT ");
        sb.append("                     MAX(RS1.YUKO_DT) ");
        sb.append("                 FROM ");
        sb.append("                     R_SYOHIN RS1 ");
        sb.append("                 WHERE ");
        sb.append("                     RS1.SYOHIN_CD = DPAI.SKU ");
        sb.append("                     AND RS1.YUKO_DT <= " + BATCH_DT);
        sb.append("                 ) ");
        sb.append("     LEFT JOIN ");
        sb.append("     ( ");
        sb.append("         SELECT ");
        sb.append("             SYOHIN_CD ");
        sb.append("             , TENPO_CD ");
        sb.append("             , GENTANKA_VL ");
        sb.append("         FROM ( ");
        sb.append("             SELECT ");
        sb.append("                 RTR1.* ");
        sb.append("                 , ROW_NUMBER() OVER (PARTITION BY SYOHIN_CD, BUNRUI1_CD, TENPO_CD ORDER BY YUKO_DT DESC) AS RN ");
        sb.append("             FROM ");
        sb.append("                 R_TENSYOHIN_REIGAI RTR1 ");
        sb.append("             WHERE ");
        sb.append("                 RTR1.YUKO_DT <= " + BATCH_DT);
        sb.append("         ) RTR2 ");
        sb.append("         WHERE ");
        sb.append("             RTR2.RN = 1 ");
        sb.append("     ) RTR ");
        sb.append("     ON ");
        sb.append("         RTR.SYOHIN_CD = DPAI.SKU ");
        sb.append("         AND RTR.TENPO_CD = LPAD(TO_CHAR(DPAI.STORE), 6, '0') ");
        sb.append("     WHERE ");
        sb.append("         DPAI.EIGYO_DT = " + BATCH_DT);
        sb.append(" ) ");
        
        return sb.toString();
    }
}