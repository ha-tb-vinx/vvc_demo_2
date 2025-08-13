package mdware.master.batch.process.MSTB147;

import mdware.master.batch.common.util.CrmDataExporter;
import mdware.master.batch.common.util.CrmDataExporter.ColumnSpec;

/**
 * <p>タイトル: 海外CRM用IF取引先マスタ作成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VVC</p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */

public class MSTB147080_IfCrmShiiresakiMasterCreate {
	/** CSVファイル名（取引先マスタ） */
	private static final String FILE_NAME = "HKCR0140";
		
	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception
	{
		CrmDataExporter exporter = new CrmDataExporter();
		exporter.fileName = FILE_NAME;
		exporter.queryString = getSelDataSQL();
		exporter.columnSpecs.add(new ColumnSpec("SHIIRESAKI_CD", 6));
		exporter.columnSpecs.add(new ColumnSpec("SHIIRESAKI_KANJI_NA", 100));
		exporter.export();
	}
		
	/**
	 * 取引先マスタ取得SQL
	 *
	 * @return String
	 */
	private String getSelDataSQL()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT ");
		sb.append("     SHIIRESAKI_CD ");
		sb.append("     , SHIIRESAKI_KANJI_NA  ");
		sb.append(" FROM ");
		sb.append("     R_SHIIRESAKI");
		
		return sb.toString();
	}
}
