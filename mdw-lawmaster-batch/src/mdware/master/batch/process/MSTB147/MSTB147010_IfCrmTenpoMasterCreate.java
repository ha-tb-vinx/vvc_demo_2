package mdware.master.batch.process.MSTB147;

import mdware.common.resorces.util.ResorceUtil;
import mdware.master.batch.common.util.CrmDataExporter;
import mdware.master.batch.common.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用IF店舗マスタ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */

public class MSTB147010_IfCrmTenpoMasterCreate {

	/** CSVファイル名（店舗マスタ） */
	private static final String FILE_NAME = "HKCR0070";
	/** 削除区分 */
	private static final String TENPO_DELETE_FG = "0";
	/** 店舗区分 */
	private static final String TENPO_GYOTAI_KB = "3";
	/** バッチ日付取得 */
	private static final String BATCH_DT = ResorceUtil.getInstance().getPropertie("BATCH_DT");

	/**
	 * 本処理
	 * 
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		CrmDataExporter exporter = new CrmDataExporter();
		exporter.fileName = FILE_NAME;
		exporter.queryString = getSelDataSQL();
		exporter.columnSpecs.add(new ColumnSpec("TENPO_CD", 4));
		exporter.columnSpecs.add(new ColumnSpec("KANJI_NA", 100));
		exporter.columnSpecs.add(new ColumnSpec("SEISAN_ST_DT", 8));
		exporter.columnSpecs.add(new ColumnSpec("SEISAN_ED_DT", 8));
		exporter.export();
	}

	/**
	 * 店舗マスタ取得SQL
	 *
	 * @return String
	 */
	private String getSelDataSQL() {
		StringBuffer sb = new StringBuffer("");

		sb.append(" SELECT ");
		sb.append("    SUBSTR(TRIM(TENPO_CD), -4) AS TENPO_CD ");
		sb.append("    , KANJI_NA ");
		sb.append("    , SEISAN_ST_DT ");
		sb.append("    , CASE ");
		sb.append("    		WHEN ");
		sb.append("    			SEISAN_ED_DT <= " + BATCH_DT);
		sb.append("    		THEN ");
		sb.append("    			SEISAN_ED_DT ");
		sb.append("    		ELSE ");
		sb.append("    			NULL ");
		sb.append("    END AS SEISAN_ED_DT ");
		sb.append(" FROM ");
		sb.append("    R_TENPO ");
		sb.append(" WHERE ");
		sb.append("    GYOTAI_KB = " + TENPO_GYOTAI_KB);
		sb.append("    AND DELETE_FG = " + TENPO_DELETE_FG);
		sb.append(" ORDER BY ");
		sb.append("    TENPO_CD ");

		return sb.toString();
	}
}