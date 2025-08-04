package mdware.master.batch.process.MSTB147;

import mdware.master.batch.common.util.CrmDataExporter;
import mdware.master.batch.common.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用IF決済種別マスタ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */
public class MSTB147120_IfCrmKessaiSyubetuMasterCreate {
	/** CSVファイル名（決済種別マスタ） */
	private static final String FILE_NAME = "HKCR0180";
	
	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception 
	{
		CrmDataExporter exporter = new CrmDataExporter();
		exporter.fileName = FILE_NAME;
		exporter.queryString = getSelDataSQL();
		exporter.columnSpecs.add(new ColumnSpec("SHIHARAI_SYUBETSU_SUB_CD", 7));
		exporter.columnSpecs.add(new ColumnSpec("SHIHARAI_SYUBETSU_SUB_NA", 100));
		exporter.export();
	}
	
	/**
	 * 決済種別マスタ取得SQL
	 *
	 * @return String
	 */
	private String getSelDataSQL() {
		StringBuffer sb = new StringBuffer("");

		sb.append("	SELECT ");
		sb.append("		SHIHARAI_SYUBETSU_SUB_CD ");
		sb.append("		, SHIHARAI_SYUBETSU_SUB_NA ");
		sb.append(" FROM ");
		sb.append("		R_PAYMENT RP ");
		sb.append("	WHERE ");
		sb.append("		RP.SHIHARAI_SYUBETSU_SUB_CD IS NOT NULL ");
		sb.append("	ORDER BY ");
		sb.append("		RP.SHIHARAI_SYUBETSU_SUB_CD ");

		return sb.toString();
	}
}
