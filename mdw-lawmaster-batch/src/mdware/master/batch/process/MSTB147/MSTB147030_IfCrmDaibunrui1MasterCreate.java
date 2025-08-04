package mdware.master.batch.process.MSTB147;

import mdware.master.batch.common.util.CrmDataExporter;
import mdware.master.batch.common.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用IFグループマスタ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */

public class MSTB147030_IfCrmDaibunrui1MasterCreate {
	/** CSVファイル名（グループマスタ） */
	private static final String FILE_NAME = "HKCR0090";
	
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
		exporter.columnSpecs.add(new ColumnSpec("DAIBUNRUI1_KANJI_NA", 100));
		exporter.export();
	}
	
	/**
	 * グループマスタ取得SQL
	 *
	 * @return String
	 */
	private String getSelDataSQL() {
		StringBuffer sb = new StringBuffer("");
	
		sb.append(" SELECT ");
		sb.append("    DAIBUNRUI1_CD ");
		sb.append("    , DAIBUNRUI1_KANJI_NA ");
		sb.append(" FROM ");
		sb.append("    IF_R_DAIBUNRUI1 ");
		sb.append(" ORDER BY ");
		sb.append("    DAIBUNRUI1_CD ");
	
		return sb.toString();
	}
}
