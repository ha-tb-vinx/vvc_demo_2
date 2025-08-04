package mdware.master.batch.process.MSTB147;

import mdware.master.batch.common.util.CrmDataExporter;
import mdware.master.batch.common.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用IFクラスマスタ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */
public class MSTB147070_IfCrmBunrui5MasterCreate {
	/** CSVファイル名（クラスマスタ） */
	private static final String FILE_NAME = "HKCR0130";
	
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
		exporter.columnSpecs.add(new ColumnSpec("BUNRUI1_CD", 2));
		exporter.columnSpecs.add(new ColumnSpec("BUNRUI2_CD", 5));
		exporter.columnSpecs.add(new ColumnSpec("BUNRUI5_CD", 6));
		exporter.columnSpecs.add(new ColumnSpec("BUNRUI5_KANJI_NA", 100));
		exporter.export();
	}
	
	/**
	 * クラスマスタ取得SQL
	 *
	 * @return String
	 */
	private String getSelDataSQL() {
		StringBuffer sb = new StringBuffer("");
	
		sb.append(" SELECT ");
		sb.append("    IRDBR2.DAIBUNRUI1_CD ");
		sb.append("    , IRDBR2.DAIBUNRUI2_CD ");
		sb.append("    , IRBR1.BUNRUI1_CD ");
		sb.append("    , IRBR2.BUNRUI2_CD ");
		sb.append("    , IRBR5.BUNRUI5_CD ");
		sb.append("    , BUNRUI5_KANJI_NA ");
		sb.append(" FROM ");
		sb.append("    IF_R_BUNRUI5 IRBR5 ");
		sb.append(" INNER JOIN ");
		sb.append(" 	IF_R_BUNRUI2 IRBR2 ");
		sb.append("    	ON ");
		sb.append("    		IRBR2.BUNRUI2_CD = IRBR5.BUNRUI2_CD ");
		sb.append(" INNER JOIN ");
		sb.append("   	IF_R_BUNRUI1 IRBR1 ");
		sb.append("    	ON ");
		sb.append("    		IRBR2.BUNRUI1_CD = IRBR1.BUNRUI1_CD ");
		sb.append(" INNER JOIN ");
		sb.append("   	IF_R_DAIBUNRUI2 IRDBR2 ");
		sb.append("    	ON ");
		sb.append("    		IRDBR2.DAIBUNRUI2_CD = IRBR1.DAIBUNRUI2_CD ");
		sb.append(" ORDER BY ");
		sb.append("    IRDBR2.DAIBUNRUI1_CD ");
		sb.append("    , IRDBR2.DAIBUNRUI2_CD ");
		sb.append("    , IRBR1.BUNRUI1_CD ");
		sb.append("    , IRBR2.BUNRUI2_CD ");
		sb.append("    , IRBR5.BUNRUI5_CD ");
	
		return sb.toString();
	}
}
