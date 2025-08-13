package mdware.master.batch.process.MSTB147;

import mdware.common.resorces.util.ResorceUtil;
import mdware.master.batch.common.util.CrmDataExporter;
import mdware.master.batch.common.util.CrmDataExporter.ColumnSpec;

/**
 * <p> タイトル: 海外CRM用IF商品マスタ作成処理 </p>
 * <p> 著作権: Copyright (c) </p>
 * <p> 会社名: VVC </p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */
public class MSTB147020_IfCrmSyohinMasterCreate {
	/** CSVファイル名（商品マスタ） */
	private static final String FILE_NAME = "HKCR0080";
	/** バッチ日付取得 */
	private static final String BATCH_DT = ResorceUtil.getInstance().getPropertie("BATCH_DT");
	
	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception 
	{
		CrmDataExporter exporter = new CrmDataExporter();
		exporter.fileName = FILE_NAME;
		exporter.queryString = getSelDataSQL();
		exporter.columnSpecs.add(new ColumnSpec("SYOHIN_CD", 13));
		exporter.columnSpecs.add(new ColumnSpec("REC_HINMEI_KANJI_NA", 120));
		exporter.columnSpecs.add(new ColumnSpec("DAIBUNRUI1_CD", 2));
		exporter.columnSpecs.add(new ColumnSpec("DAIBUNRUI2_CD", 2));
		exporter.columnSpecs.add(new ColumnSpec("BUNRUI1_CD", 2));
		exporter.columnSpecs.add(new ColumnSpec("BUNRUI2_CD", 5));
		exporter.columnSpecs.add(new ColumnSpec("BUNRUI5_CD", 6));
		exporter.columnSpecs.add(new ColumnSpec("KIKAKU_KANJI_2_NA", 100));
		exporter.columnSpecs.add(new ColumnSpec("SIIRESAKI_CD", 6));
		exporter.columnSpecs.add(new ColumnSpec("IYAKUHIN_KB", 2));
		exporter.columnSpecs.add(new ColumnSpec("PB_SYOHIN_KB", 1));
		exporter.columnSpecs.add(new ColumnSpec("WARIBIKI_KB", 1));
		exporter.columnSpecs.add(new ColumnSpec("BAITANKA_VL", 14));
		exporter.columnSpecs.add(new ColumnSpec("GENTANKA_VL", 14));
		exporter.export();
	}
	
	/**
	 * 商品マスタ取得SQL
	 *
	 * @return String
	 */
	private String getSelDataSQL() {
		StringBuffer sb = new StringBuffer("");

		sb.append("	SELECT");
		sb.append("		RS.SYOHIN_CD ");
		sb.append("		, RS.REC_HINMEI_KANJI_NA ");
		sb.append("		, IRDBR2.DAIBUNRUI1_CD ");
		sb.append("		, IRDBR2.DAIBUNRUI2_CD ");
		sb.append("		, RS.BUNRUI1_CD ");
		sb.append(" 	, RS.BUNRUI2_CD ");
		sb.append(" 	, RS.BUNRUI5_CD ");
		sb.append(" 	, RS.KIKAKU_KANJI_2_NA ");
		sb.append(" 	, RS.SIIRESAKI_CD ");
		sb.append("		, RS.IYAKUHIN_KB ");
		sb.append("		, RS.PB_SYOHIN_KB ");
		sb.append("		, RS.WARIBIKI_KB ");
		sb.append("		, RS.BAITANKA_VL ");
		sb.append("		, RS.GENTANKA_VL ");
		sb.append("	FROM ");
		sb.append("	( ");
		sb.append("		SELECT ");
		sb.append("			SYOHIN_CD ");
		sb.append("			, REC_HINMEI_KANJI_NA ");
		sb.append("			, BUNRUI1_CD ");
		sb.append("			, BUNRUI2_CD ");
		sb.append("			, BUNRUI5_CD ");
		sb.append("			, KIKAKU_KANJI_2_NA ");
		sb.append("			, SIIRESAKI_CD ");
		sb.append("			, IYAKUHIN_KB ");
		sb.append("			, PB_SYOHIN_KB	 ");
		sb.append("			, WARIBIKI_KB ");
		sb.append("			, TO_CHAR(BAITANKA_VL, '9990.00') AS BAITANKA_VL");
		sb.append("			, TO_CHAR(GENTANKA_VL, '9990.00') AS GENTANKA_VL ");
		sb.append("		FROM ");
		sb.append("		( ");
		sb.append("			SELECT ");
		sb.append("				RS1.* ");
		sb.append("				, ROW_NUMBER() OVER (PARTITION BY SYOHIN_CD, BUNRUI1_CD ORDER BY YUKO_DT DESC) AS RN ");
		sb.append("			FROM ");
		sb.append("				R_SYOHIN RS1 ");
		sb.append("			WHERE ");
		sb.append("				RS1.YUKO_DT <= " + BATCH_DT);
		sb.append("		) RS2 ");
		sb.append("		WHERE ");
		sb.append("			RS2.RN = 1 ");
		sb.append("	) RS ");
		sb.append(" INNER JOIN ");
		sb.append(" 	IF_R_BUNRUI5 IRBR5 ");
		sb.append("    	ON ");
		sb.append("    		IRBR5.BUNRUI5_CD = RS.BUNRUI5_CD ");
		sb.append(" INNER JOIN ");
		sb.append(" 	IF_R_BUNRUI2 IRBR2 ");
		sb.append("    	ON ");
		sb.append("    		IRBR2.BUNRUI2_CD = RS.BUNRUI2_CD ");
		sb.append(" INNER JOIN ");
		sb.append("   	IF_R_BUNRUI1 IRBR1 ");
		sb.append("    	ON ");
		sb.append("    		IRBR1.BUNRUI1_CD = RS.BUNRUI1_CD ");
		sb.append(" INNER JOIN ");
		sb.append("   	IF_R_DAIBUNRUI2 IRDBR2 ");
		sb.append("    	ON ");
		sb.append("    		IRDBR2.DAIBUNRUI2_CD = IRBR1.DAIBUNRUI2_CD ");
		sb.append(" ORDER BY ");
		sb.append("		IRDBR2.DAIBUNRUI1_CD ");
		sb.append("		, IRDBR2.DAIBUNRUI2_CD ");
		sb.append("		, RS.BUNRUI1_CD ");
		sb.append(" 	, RS.BUNRUI2_CD ");
		sb.append(" 	, RS.BUNRUI5_CD ");

		return sb.toString();
	}
}
