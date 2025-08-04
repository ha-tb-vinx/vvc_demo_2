package mdware.master.batch.process.MSTB147;

import mdware.master.batch.common.util.CrmDataExporter;
import mdware.master.batch.common.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用IF部門マスタ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */

public class MSTB147040_IfCrmDaibunrui2MasterCreate {
	/** CSVファイル名（部門マスタ） */
	private static final String FILE_NAME = "HKCR0100";
	
	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception 
	{
		CrmDataExporter exporter = new CrmDataExporter();
		exporter.fileName = FILE_NAME;
		exporter.queryString = getSelDataSQL();
		exporter.columnSpecs.add(new ColumnSpec("DAIBUNRUI1_CD", 2));
		exporter.columnSpecs.add(new ColumnSpec("DAIBUNRUI2_CD", 2));
		exporter.columnSpecs.add(new ColumnSpec("DAIBUNRUI2_KANJI_NA", 100));
		exporter.export();
	}
	
	/**
	 * 部門マスタ取得SQLを取得します。
	 *
	 * @return String
	 */
	private String getSelDataSQL() {
		StringBuffer sb = new StringBuffer("");
	
		sb.append(" SELECT ");
		sb.append("    IRD2.DAIBUNRUI1_CD ");
		sb.append("    , IRD2.DAIBUNRUI2_CD ");
		sb.append("    , IRD2.DAIBUNRUI2_KANJI_NA ");
		sb.append(" FROM ");
		sb.append("    IF_R_DAIBUNRUI2 IRD2 ");
		sb.append(" INNER JOIN ");
		sb.append("    IF_R_DAIBUNRUI1 IRD1 ");
		sb.append("    ON ");
		sb.append("			IRD2.DAIBUNRUI1_CD = IRD1.DAIBUNRUI1_CD ");
		sb.append(" ORDER BY ");
		sb.append("    IRD2.DAIBUNRUI1_CD ");
		sb.append("    , IRD2.DAIBUNRUI2_CD ");
	
		return sb.toString();
	}
}
