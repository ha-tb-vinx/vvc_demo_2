package mdware.master.batch.process.MSTB147;

import mdware.master.batch.common.util.CrmDataExporter;
import mdware.master.batch.common.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用IFPB区分マスタ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */
public class MSTB147100_IfCrmPbKbMasterCreate {
	/** CSVファイル名（PB区分マスタ） */
	private static final String FILE_NAME = "HKCR0160";
	/** 削除区分 */
	private static final String PB_KB_DELETE_FG = "0";
	/** PB区分CD */
	private static final String PB_KB_SYUBETU_NO_CD = "3840";
	
	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception 
	{
		CrmDataExporter exporter = new CrmDataExporter();
		exporter.fileName = FILE_NAME;
		exporter.queryString = getSelDataSQL();
		exporter.columnSpecs.add(new ColumnSpec("CODE_CD", 1));
		exporter.columnSpecs.add(new ColumnSpec("KANJI_NA", 100));
		exporter.export();
	}
	
	/**
	 * PB区分マスタ取得SQL
	 *
	 * @return String
	 */
	private String getSelDataSQL() {
		StringBuffer sb = new StringBuffer("");

		sb.append("	SELECT ");
		sb.append("		CODE_CD ");
		sb.append("		, KANJI_NA ");
		sb.append("	FROM ");
		sb.append("		R_NAMECTF ");
		sb.append("	WHERE ");
		sb.append("		SYUBETU_NO_CD = '" + PB_KB_SYUBETU_NO_CD + "' ");
		sb.append("		AND DELETE_FG = " + PB_KB_DELETE_FG);

		return sb.toString();
	}
}
