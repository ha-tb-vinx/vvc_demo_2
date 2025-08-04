package mdware.master.batch.process.mb38;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst006501_BySyoninFgDictionary;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.common.dictionary.mst006801_MstMainteFgDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:商品マスタ生成バッチ共通SQLクラス）</p>
 * <p>説明:データを登録するSQLを生成します</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/05/23<BR>
 * @author shimoyama
 */

public class MB380000_CommonSql
{

	/**
	 * 処理結果メッセージデータ新規登録用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedMessageSQL(MasterDataBase dataBase) throws SQLException
	{
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();

		//取込日
		sql1.append("torikomi_dt,");
		sql2.append(" ?,");

		//EXCELファイル種別
		sql1.append("excel_file_syubetsu,");
		sql2.append(" ?,");

		//受付ファイルNo
		sql1.append("uketsuke_no,");
		sql2.append(" ?,");

		//受付SEQNo
		sql1.append("uketsuke_seq,");
		sql2.append(" ?,");

		//シート種別
		sql1.append("sheet_syubetsu,");
		sql2.append(" ?,");

		//結果メッセージコード
		sql1.append("kekka_message_cd,");
		sql2.append(" ?,");

		//結果メッセージ
		sql1.append("kekka_message_tx,");
		sql2.append(" ?,");

		// 作成年月日
		sql1.append("insert_ts,");
		sql2.append(" ?,");

		// 作成者ID
		sql1.append("insert_user_id,");
		sql2.append(" ?,");

		// 更新年月日
		sql1.append("update_ts,");
		sql2.append(" ?,");

		// 更新者ID
		sql1.append("update_user_id");
		sql2.append(" ?");

		sql.append("insert into tr_message ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") values ( ");
		sql.append(sql2.toString());
		sql.append(") ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 非承認データメッセージ作成SQL
	 * @throws
	 */
	public String getHiSyoninMessageSQL(String tableName, String syubetu, Map map)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		str.append(" r_message ");
		str.append("(select ");
		//      ↓↓2006.06.28 zhouj カスタマイズ修正↓↓
		//		str.append("   torikomi_dt,"); //取込日
		//		str.append("   excel_file_syubetsu,"); //EXCELファイル種別
		//		str.append("   uketsuke_no,"); //受付ファイルNo.
		//		str.append("   uketsuke_seq,"); //受付SEQNo.
		//		str.append("'" + syubetu + "',"); //シート種別
		//		str.append("   '0001',"); //結果メッセージコード
		//		str.append("'" + map.get("0001") + "'"); //結果メッセージ
		//		str.append(" from ");
		//		str.append(tableName);
		//		str.append(" where");
		//		str.append("   by_syonin_fg= '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' and");
		//		str.append("   mst_mainte_fg= '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "')");
		str.append("   tr.torikomi_dt,"); //取込日
		str.append("   tr.excel_file_syubetsu,"); //EXCELファイル種別
		str.append("   tr.uketsuke_no,"); //受付ファイルNo.
		str.append("   tr.uketsuke_seq,"); //受付SEQNo.
		str.append("'" + syubetu + "',"); //シート種別
		str.append("   '0001',"); //結果メッセージコード
		str.append("'" + map.get("0001") + "',"); //結果メッセージ
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no, ");
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no ");
		str.append(" from ");
		//		↓↓2006.09.18 H.Yamamoto カスタマイズ修正↓↓
		//		str.append("   tr_toroku_syonin top left outer join ");
		str.append("   tr_toroku_syonin top inner join ");
		//		↑↑2006.09.18 H.Yamamoto カスタマイズ修正↑↑
		str.append(tableName).append(" tr ");
		str.append("   on top.torikomi_dt = tr.torikomi_dt ");
		str.append("   and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("   and top.uketsuke_no = tr.uketsuke_no ");
		str.append(" where");
		str.append(
			"   top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.HISYONIN.getCode()
				+ "' and");
		str.append(
			"   tr.mst_mainte_fg= '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "')");
		//      ↑↑2006.06.28 zhouj カスタマイズ修正↑↑

		return str.toString();
	}

	//	↓↓移植(2006.05.22)↓↓
	/**
	 * 非承認データ処理SQL作成
	 * @throws SQLException 
	 */
	public String getHiSyoninSQL(String tableName, String batchID) throws SQLException
	{
		StringBuffer str = new StringBuffer();

		//		↓↓2006.09.18 H.Yamamoto カスタマイズ修正↓↓
		//		str.append("update /*+ ordered */ ");
		//		str.append(tableName);
		//		str.append(" set ");
		//		// ===BEGIN=== Modify by kou 2006/8/1
		//		// 否認処理はエラーではなく、警告です
		//		//str.append("mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.ERROR.getCode() + "',");
		//		str.append("mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "',");
		//		// ===END=== Modify by kou 2006/8/1
		//		str.append("update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		////		↑↑移植(2006.05.22)↑↑
		//		str.append("update_user_id = '" + batchID + "' ");
		//		str.append("where");
		////      ↓↓2006.06.28 zhouj カスタマイズ修正↓↓
		////		str.append(" by_syonin_fg= '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' and");
		//		str.append(" mst_mainte_fg= '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		//		str.append(" and torikomi_dt||excel_file_syubetsu||uketsuke_no in ");
		//		str.append(" (select ");
		//		str.append("      tr.torikomi_dt||tr.excel_file_syubetsu||tr.uketsuke_no"); //取込日,EXCELファイル種別,受付ファイルNo.
		//		str.append("  from ");
		//		str.append("      tr_toroku_syonin top left outer join ");
		//		str.append(tableName).append(" tr ");
		//		str.append("      on top.torikomi_dt = tr.torikomi_dt ");
		//		str.append("      and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		//		str.append("      and top.uketsuke_no = tr.uketsuke_no ");
		//		str.append("  where");
		//		str.append("      top.toroku_syonin_fg = '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' ");
		//		str.append(" )");
		////      ↑↑2006.06.28 zhouj カスタマイズ修正↑↑

		str.append("update /*+ ordered */ ");
		str.append(tableName).append(" tr ");
		str.append(" set ");
		str.append("mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "',");
		str.append(
			"update_ts = '"
				+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")
				+ "',");
		str.append("update_user_id = '" + batchID + "' ");
		str.append("where");
		str.append(
			" mst_mainte_fg= '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "' and ");
		str.append(" EXISTS ");
		str.append(" (select * ");
		str.append("  from ");
		str.append("      tr_toroku_syonin top ");
		str.append("  where");
		str.append("          top.torikomi_dt = tr.torikomi_dt ");
		str.append("      and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("      and top.uketsuke_no = tr.uketsuke_no ");
		str.append(
			"      and top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.HISYONIN.getCode()
				+ "' ");
		str.append(" )");
		//		↑↑2006.09.18 H.Yamamoto カスタマイズ修正↑↑

		return str.toString();
	}

	/**
	 * 後者優先による登録対象外のデータのメッセージ作成SQL
	 * @throws
	 */
	public String getTaisyogaiMessageSQL(
		String tableName,
		String syubetu,
		Map map,
		String strWhere)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		str.append(" r_message ");
		str.append("(select ");
		//      ↓↓2006.06.28 zhouj カスタマイズ修正↓↓
		//		str.append("   torikomi_dt,"); //取込日
		//		str.append("   excel_file_syubetsu,"); //EXCELファイル種別
		//		str.append("   uketsuke_no,"); //受付ファイルNo.
		//		str.append("   uketsuke_seq,"); //受付SEQNo.
		str.append("   tr.torikomi_dt,"); //取込日
		str.append("   tr.excel_file_syubetsu,"); //EXCELファイル種別
		str.append("   tr.uketsuke_no,"); //受付ファイルNo.
		str.append("   tr.uketsuke_seq,"); //受付SEQNo.
		str.append("'" + syubetu + "',"); //シート種別
		str.append("   '0003',"); //結果メッセージコード
		str.append("'" + map.get("0003") + "',"); //結果メッセージ
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no, ");
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no ");
		str.append(" from ");
		str.append("   tr_toroku_syonin top left outer join ");
		str.append(tableName).append(" tr");
		str.append("   on top.torikomi_dt = tr.torikomi_dt ");
		str.append("   and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("   and top.uketsuke_no = tr.uketsuke_no ");
		str.append(" where");
		//		str.append("   tr.by_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		str.append(
			"   top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and ");
		str.append(
			"   tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		//		↓↓修正（2005.10.07）↓↓
		//		str.append("   not exists ");
		//		str.append("   (select");
		//		str.append("      '1'");
		//		str.append("	(torikomi_dt, excel_file_syubetsu, uketsuke_no, uketsuke_seq) not in ");
		str.append(
			"	(tr.torikomi_dt||tr.excel_file_syubetsu||tr.uketsuke_no||tr.uketsuke_seq) not in ");
		str.append("	(select ");
		//		str.append("		torikomi_dt, ");
		//		str.append("		excel_file_syubetsu, ");
		//		str.append("		uketsuke_no, ");
		//		str.append("		uketsuke_seq ");
		str.append(
			"		sub.torikomi_dt||sub.excel_file_syubetsu||sub.uketsuke_no||sub.uketsuke_seq ");
		//		↑↑修正（2005.10.07）↑↑
		//      ↑↑2006.06.28 zhouj カスタマイズ修正↑↑
		str.append("    from ");
		//		↓↓修正（2005.10.07）↓↓
		//		str.append(strWhere).append(" sub");
		//		str.append("    where");
		//		str.append("    tr.torikomi_dt = sub.torikomi_dt and");
		//		str.append("    tr.excel_file_syubetsu = sub.excel_file_syubetsu and");
		//		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
		//		str.append("    tr.uketsuke_seq = sub.uketsuke_seq");
		str.append(strWhere);
		//      ↓↓2006.06.30 zhouj カスタマイズ修正↓↓
		//		str.append("   )");
		str.append("   sub )");
		//      ↑↑2006.06.30 zhouj カスタマイズ修正↑↑
		//		↑↑修正（2005.10.07）↑↑
		str.append(")");

		return str.toString();
	}

	/**
	 * 後者優先による登録対象外のデータ処理SQL作成
	 * @throws
	 */
	public String getTaisyogaiSQL(String tableName, String batchID, String strWhere)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update /*+ ordered */ ");
		str.append(tableName).append(" tr ");
		str.append("set ");
		str.append("  mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.ERROR.getCode() + "',");
		//      ↓↓2006.06.28 zhouj カスタマイズ修正↓↓
		//		str.append("  update_ts = to_char(sysdate,'yyyymmddhh24miss'),");
		str.append(
			"  update_ts = '"
				+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")
				+ "',");
		str.append("  update_user_id = '" + batchID + "' ");
		str.append("where");
		//		str.append("   tr.by_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		str.append(
			"   tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		str.append("   tr.torikomi_dt||tr.excel_file_syubetsu||tr.uketsuke_no in ");
		str.append("   (select ");
		str.append("      tr.torikomi_dt||tr.excel_file_syubetsu||tr.uketsuke_no");
		//取込日,EXCELファイル種別,受付ファイルNo.
		str.append("    from ");
		str.append("      tr_toroku_syonin top left outer join ");
		str.append(tableName).append(" tr ");
		str.append("      on top.torikomi_dt = tr.torikomi_dt ");
		str.append("      and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("      and top.uketsuke_no = tr.uketsuke_no ");
		str.append("    where");
		str.append(
			"      top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' ");
		str.append("   ) and ");
		//		↓↓修正（2005.10.07）↓↓
		//		str.append("   not exists ");
		//		str.append("   (select");
		//		str.append("      '1'");
		//		str.append("	(torikomi_dt, excel_file_syubetsu, uketsuke_no, uketsuke_seq) not in ");
		//		str.append("	(select ");
		//		str.append("		torikomi_dt, ");
		//		str.append("		excel_file_syubetsu, ");
		//		str.append("		uketsuke_no, ");
		//		str.append("		uketsuke_seq ");
		str.append(
			"	(tr.torikomi_dt||tr.excel_file_syubetsu||tr.uketsuke_no||tr.uketsuke_seq) not in ");
		str.append("	(select ");
		str.append(
			"		sub.torikomi_dt||sub.excel_file_syubetsu||sub.uketsuke_no||sub.uketsuke_seq ");
		//		↑↑修正（2005.10.07）↑↑
		//      ↑↑2006.06.28 zhouj カスタマイズ修正↑↑
		str.append("    from ");
		//		↓↓修正（2005.10.07）↓↓
		//		str.append(strWhere).append(" sub");
		//		str.append("    where");
		//		str.append("    tr.torikomi_dt = sub.torikomi_dt and");
		//		str.append("    tr.excel_file_syubetsu = sub.excel_file_syubetsu and");
		//		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
		//		str.append("    tr.uketsuke_seq = sub.uketsuke_seq");
		str.append(strWhere);
		//      ↓↓2006.06.30 zhouj カスタマイズ修正↓↓
		//		str.append("   )");
		str.append("   sub )");
		//      ↑↑2006.06.30 zhouj カスタマイズ修正↑↑
		//		↑↑修正（2005.10.07）↑↑

		return str.toString();
	}

	//  ↓↓2006.07.05 zhouj カスタマイズ修正↓↓
	//	/**
	//	 * 後者優先による登録対象外のデータを特定する作成SQL（品種、商品コードがキー）
	//	 * @throws
	//	 */
	//	public String getTaisyogaiHinsyuSQL(String tableName) {
	//		StringBuffer str = new StringBuffer();
	//
	//		str.append("(");
	//		str.append("select /*+ ordered */");
	//		str.append("    tr.* ");
	//		str.append("from ");
	//		str.append("").append(tableName).append(" tr,");
	//		str.append("    (");
	//		str.append("    select");
	//		str.append("        tr.hinsyu_cd as hinsyu_cd,");
	//		str.append("        tr.syohin_cd as syohin_cd,");
	//		str.append("        tr.uketsuke_no as uketsuke_no,");
	//		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
	//		str.append("    from ");
	//		str.append("").append(tableName).append(" tr,");
	//		str.append("        (select");
	//		str.append("            tr.hinsyu_cd as hinsyu_cd,");
	//		str.append("            tr.syohin_cd as syohin_cd,");
	//		str.append("            max(uketsuke_no) as uketsuke_no");
	//		str.append("        from ");
	//		str.append("").append(tableName).append(" tr");
	//		str.append("        where");
	//		str.append("            tr.by_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
	//		str.append("            tr.mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "' and");
	//		str.append("            tr.update_ts = ");
	//		str.append("            (select max(update_ts) from ").append(tableName).append(" ts where tr.hinsyu_cd = ts.hinsyu_cd and tr.syohin_cd = ts.syohin_cd and tr.by_syonin_fg = ts.by_syonin_fg and tr.mst_mainte_fg = ts.mst_mainte_fg)");
	//		str.append("        group by");
	//		str.append("            tr.hinsyu_cd,");
	//		str.append("            tr.syohin_cd");
	//		str.append("        ) sub");
	//		str.append("    where");
	//		str.append("        tr.hinsyu_cd = sub.hinsyu_cd and");
	//		str.append("        tr.syohin_cd = sub.syohin_cd and");
	//		str.append("        tr.uketsuke_no = sub.uketsuke_no");
	//		str.append("    group by");
	//		str.append("        tr.hinsyu_cd,");
	//		str.append("        tr.syohin_cd,");
	//		str.append("        tr.uketsuke_no");
	//		str.append("    ) sub ");
	//		str.append("where");
	//		str.append("    tr.hinsyu_cd = sub.hinsyu_cd and");
	//		str.append("    tr.syohin_cd = sub.syohin_cd and");
	//		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
	//		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no");
	////		↓↓移植(2006.05.22)↓↓
	//		str.append(") re");
	////		↑↑移植(2006.05.22)↑↑
	//
	//		return str.toString();
	//	}
	//  ↑↑2006.07.05 zhouj カスタマイズ修正↑↑

	//  ↓↓2006.07.05 zhouj カスタマイズ修正↓↓
	/**
	 * 後者優先による登録対象外のデータを特定する作成SQL（部門、商品コードがキー）
	 * @throws
	 */
	public String getTaisyogaiBumonSQL(String tableName)
	{
		StringBuffer str = new StringBuffer();

		str.append("(");
		str.append("select /*+ ordered */");
		str.append("    tr.* ");
		str.append("from ");
		str.append("").append(tableName).append(" tr,");
		str.append("    (");
		str.append("    select");
		str.append("        tr.bumon_cd as bumon_cd,");
		str.append("        tr.syohin_cd as syohin_cd,");
		str.append("        tr.uketsuke_no as uketsuke_no,");
		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
		str.append("    from ");
		str.append("").append(tableName).append(" tr,");
		str.append("        (select");
		str.append("            tr.bumon_cd as bumon_cd,");
		str.append("            tr.syohin_cd as syohin_cd,");
		str.append("            max(tr.uketsuke_no) as uketsuke_no");
		str.append("        from ");
		str.append("            tr_toroku_syonin top left outer join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"            top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"            tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		str.append("            tr.update_ts = ");
		str.append("            (select max(update_ts) from ").append(tableName).append(
			" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");

		////		=== BEGIN === Modify by kou 2006/7/26
		////		新規の場合、商品コードは空白の状況があり得るため。
		////		str.append("            (select max(update_ts) from ").append(tableName).append(" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		//		str.append("            (select max(update_ts) from ")
		//			.append(tableName)
		//			.append(" ts where tr.bumon_cd = ts.bumon_cd ")
		//			.append("	   and (	tr.syohin_cd = ts.syohin_cd ")
		//			.append("			or (tr.syohin_cd is null and tr.syusei_kb = '")
		//				.append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("')")
		//			.append("			or (ts.syohin_cd is null and ts.syusei_kb = '")
		//				.append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("')	)")
		//			.append("	   and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		////		=== END === Modify by kou 2006/7/26

		str.append("        group by");
		str.append("            tr.bumon_cd,");
		str.append("            tr.syohin_cd");
		str.append("        ) sub");
		str.append("    where");
		str.append("        tr.bumon_cd = sub.bumon_cd and");
		str.append("        tr.syohin_cd = sub.syohin_cd and");

		////		=== BEGIN === Modify by kou 2006/7/26
		////		新規の場合、商品コードは空白の状況があり得るため。
		////		str.append("        tr.syohin_cd = sub.syohin_cd and");
		//		str.append("        (tr.syohin_cd = sub.syohin_cd ");
		//		str.append("         or (tr.syohin_cd is null and tr.syusei_kb = '")
		//			.append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("')");
		//		str.append("         or (sub.syohin_cd is null) ) and ");
		////		=== END === Modify by kou 2006/7/26

		str.append("        tr.uketsuke_no = sub.uketsuke_no");
		str.append("    group by");
		str.append("        tr.bumon_cd,");
		str.append("        tr.syohin_cd,");
		str.append("        tr.uketsuke_no");
		str.append("    ) sub ");
		str.append("where");
		str.append("    tr.bumon_cd = sub.bumon_cd and");
		str.append("    tr.syohin_cd = sub.syohin_cd and");

		////		=== BEGIN === Modify by kou 2006/7/26
		////		新規の場合、商品コードは空白の状況があり得るため。
		////		str.append("    tr.syohin_cd = sub.syohin_cd and");
		//		str.append("    (tr.syohin_cd = sub.syohin_cd ");
		//		str.append("         or (tr.syohin_cd is null and tr.syusei_kb = '")
		//			.append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("')");
		//		str.append("         or (sub.syohin_cd is null) ) and ");
		////		=== END === Modify by kou 2006/7/26

		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no");

		//		===BEGIN=== Add by kou 2006/8/4
		//		新規の場合、商品コードは空白の状況があり得るため。
		//		しかも、同じCSVファイルに多数の商品コードが空白の可能性もある。
		str.append(" union ");
		str.append(" select tr.* from ");
		str.append("            tr_toroku_syonin top left outer join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"              top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' ");
		str.append(
			"          and tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' ");
		str.append("          and tr.syohin_cd is null");
		//		===END=== Add by kou 2006/8/4

		str.append(") re");

		return str.toString();
	}
	//  ↑↑2006.07.05 zhouj カスタマイズ修正↑↑

	//  ↓↓2006.07.05 zhouj カスタマイズ修正↓↓
	/**
	 * 後者優先による登録対象外のデータを特定する作成SQL（部門、商品コードがキー）
	 * （2005/10/12　キーに有効日を追加）
	 * @throws
	 */
	public String getTaisyogaiBumonFreSQL(String tableName)
	{
		StringBuffer str = new StringBuffer();

		str.append("(");
		str.append("select /*+ ordered */");
		str.append("    tr.* ");
		str.append("from ");
		str.append("").append(tableName).append(" tr,");
		str.append("    (");
		str.append("    select");
		str.append("        tr.bumon_cd as bumon_cd,");
		str.append("        tr.syohin_cd as syohin_cd,");
		str.append("		tr.yuko_dt as yuko_dt,");
		str.append("        tr.uketsuke_no as uketsuke_no,");
		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
		str.append("    from ");
		str.append("").append(tableName).append(" tr,");
		str.append("        (select ");
		str.append("            tr.bumon_cd as bumon_cd,");
		str.append("            tr.syohin_cd as syohin_cd,");
		str.append("			tr.yuko_dt as yuko_dt,");
		str.append("            max(tr.uketsuke_no) as uketsuke_no");
		str.append("        from ");
		str.append("            tr_toroku_syonin top left outer join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where ");
		str.append(
			"            top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"            tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		str.append("            tr.update_ts = ");
		str.append("            (select max(update_ts) from ").append(tableName).append(
			" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.yuko_dt = ts.yuko_dt and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		str.append("        group by");
		str.append("            tr.bumon_cd,");
		str.append("            tr.syohin_cd,");
		str.append("            tr.yuko_dt");
		str.append("        ) sub");
		str.append("    where");
		str.append("        tr.bumon_cd = sub.bumon_cd and");
		str.append("        tr.syohin_cd = sub.syohin_cd and");
		str.append("		tr.yuko_dt = sub.yuko_dt and");
		str.append("        tr.uketsuke_no = sub.uketsuke_no");
		str.append("    group by");
		str.append("        tr.bumon_cd,");
		str.append("        tr.syohin_cd,");
		str.append("		tr.yuko_dt,");
		str.append("        tr.uketsuke_no");
		str.append("    ) sub ");
		str.append("where");
		str.append("    tr.bumon_cd = sub.bumon_cd and ");
		str.append("    tr.syohin_cd = sub.syohin_cd and ");
		str.append("	tr.yuko_dt = sub.yuko_dt and ");
		str.append("    tr.uketsuke_no = sub.uketsuke_no and ");
		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no ");
		str.append(") re ");

		return str.toString();
	}
	//  ↑↑2006.07.05 zhouj カスタマイズ修正↑↑

	// ===BEGIN=== Add by kou 2006/8/14
	/**
	 * 後者優先による登録対象外のデータを特定する作成SQL（部門、商品コードがキー）
	 * 実用専用
	 * @throws
	 */
	public String getTaisyogaiBumonA07SQL(String tableName)
	{
		StringBuffer str = new StringBuffer();

		str.append("(");
		str.append("select /*+ ordered */");
		str.append("    tr.* ");
		str.append("from ");
		str.append("").append(tableName).append(" tr,");
		str.append("    (");
		str.append("    select");
		str.append("        tr.bumon_cd as bumon_cd,");
		str.append("        tr.syohin_cd as syohin_cd,");
		// ===BEGIN=== Modify by kou 2006/9/4
		//str.append("		tr.yuko_dt as yuko_dt,");
		// ===END=== Modify by kou 2006/9/14
		str.append("        tr.uketsuke_no as uketsuke_no,");
		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
		str.append("    from ");
		str.append("").append(tableName).append(" tr,");
		str.append("        (select ");
		str.append("            tr.bumon_cd as bumon_cd,");
		str.append("            tr.syohin_cd as syohin_cd,");
		// ===BEGIN=== Modify by kou 2006/9/4
		//str.append("			tr.yuko_dt as yuko_dt,");
		// ===END=== Modify by kou 2006/9/14
		str.append("            max(tr.uketsuke_no) as uketsuke_no");
		str.append("        from ");
		str.append("            tr_toroku_syonin top left outer join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where ");
		str.append(
			"            top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"            tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		str.append("            tr.update_ts = ");
		// ===BEGIN=== Modify by kou 2006/9/4
		//str.append("            (select max(update_ts) from ").append(tableName).append(" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.yuko_dt = ts.yuko_dt and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		str
			.append("            (select max(update_ts) from ")
			.append(tableName)
			.append(" ts where tr.bumon_cd = ts.bumon_cd ")
			.append(" and tr.syohin_cd = ts.syohin_cd ")
			.append(" and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		// ===END=== Modify by kou 2006/9/4
		str.append("        group by");
		str.append("            tr.bumon_cd,");
		// ===BEGIN=== Modify by kou 2006/9/4
		//str.append("            tr.syohin_cd,");
		//str.append("            tr.yuko_dt");
		str.append("            tr.syohin_cd");
		// ===END=== Modify by kou 2006/9/14
		str.append("        ) sub");
		str.append("    where");
		str.append("        tr.bumon_cd = sub.bumon_cd and");
		str.append("        tr.syohin_cd = sub.syohin_cd and");
		// ===BEGIN=== Modify by kou 2006/9/4
		//str.append("		tr.yuko_dt = sub.yuko_dt and");
		// ===END=== Modify by kou 2006/9/14
		str.append("        tr.uketsuke_no = sub.uketsuke_no");
		str.append("    group by");
		str.append("        tr.bumon_cd,");
		str.append("        tr.syohin_cd,");
		// ===BEGIN=== Modify by kou 2006/9/4
		//str.append("		tr.yuko_dt,");
		// ===END=== Modify by kou 2006/9/14
		str.append("        tr.uketsuke_no");
		str.append("    ) sub ");
		str.append("where");
		str.append("    tr.bumon_cd = sub.bumon_cd and ");
		str.append("    tr.syohin_cd = sub.syohin_cd and ");
		// ===BEGIN=== Modify by kou 2006/9/4
		//str.append("	tr.yuko_dt = sub.yuko_dt and ");
		// ===END=== Modify by kou 2006/9/14
		str.append("    tr.uketsuke_no = sub.uketsuke_no and ");
		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no ");

		//		===BEGIN=== Add by kou 2006/8/14
		//		実用の場合、商品コードは空白ではなく、10～59の2桁で、他の場合の空白と同じ意味する。
		str.append(" union ");
		str.append(" select tr.* from ");
		str.append("            tr_toroku_syonin top left outer join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"              top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' ");
		str.append(
			"          and tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' ");
		str.append("          and length(trim(tr.syohin_cd)) = 2 ");
		str.append("          and tr.syohin_cd between '10' and '59' ");
		//		===END=== Add by kou 2006/8/14

		str.append(") re ");

		return str.toString();
	}
	// ===END=== Add by kou 2006/8/14

	/**
	 * 後者優先による登録対象外のデータを特定する作成SQL（品種、商品コード、店舗がキー）
	 * @throws
	 */
	public String getTaisyogaiHinsyuTenSQL(String tableName)
	{
		StringBuffer str = new StringBuffer();

		str.append("(");
		str.append("select /*+ ordered */");
		str.append("    tr.* ");
		str.append("from ");
		str.append("").append(tableName).append(" tr,");
		str.append("    (");
		str.append("    select");
		//      ↓↓2006.07.04 baoql カスタマイズ修正↓↓
		//		str.append("        tr.hinsyu_cd as hinsyu_cd,");
		str.append("        tr.bumon_cd as bumon_cd,");
		str.append("        tr.syohin_cd as syohin_cd,");
		str.append("        tr.uketsuke_no as uketsuke_no,");
		//		str.append("        tr.tenpo_cd as tenpo_cd,");
		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
		str.append("    from ");
		str.append("").append(tableName).append(" tr,");
		str.append("        (select");
		//		str.append("            tr.hinsyu_cd as hinsyu_cd,");
		str.append("            tr.bumon_cd as bumon_cd,");
		str.append("            tr.syohin_cd as syohin_cd,");
		//		str.append("            tr.tenpo_cd as tenpo_cd,");
		str.append("            max(tr.uketsuke_no) as uketsuke_no");
		str.append("        from ");
		str.append("            tr_toroku_syonin top left outer join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"            top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		//		str.append("            tr.by_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		str.append(
			"            tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		str.append("            tr.update_ts = ");
		//		str.append("            (select max(update_ts) from ").append(tableName).append(" ts where tr.hinsyu_cd = ts.hinsyu_cd and tr.syohin_cd = ts.syohin_cd and tr.tenpo_cd = ts.tenpo_cd and tr.by_syonin_fg = ts.by_syonin_fg and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		str.append("            (select max(update_ts) from ").append(tableName).append(
			" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		//      ↑↑2006.07.04 baoql カスタマイズ修正↑↑
		str.append("        group by");
		//      ↓↓2006.07.04 baoql カスタマイズ修正↓↓
		//		str.append("            tr.hinsyu_cd,");
		str.append("            tr.bumon_cd,");
		str.append("            tr.syohin_cd");
		//		str.append("            tr.syohin_cd,");
		//		str.append("            tr.tenpo_cd");
		str.append("        ) sub");
		str.append("    where");
		//		str.append("        tr.hinsyu_cd = sub.hinsyu_cd and");
		str.append("        tr.bumon_cd = sub.bumon_cd and");
		str.append("        tr.syohin_cd = sub.syohin_cd and");
		//		str.append("        tr.tenpo_cd = sub.tenpo_cd and");
		str.append("        tr.uketsuke_no = sub.uketsuke_no");
		str.append("    group by");
		//		str.append("        tr.hinsyu_cd,");
		str.append("        tr.bumon_cd,");
		str.append("        tr.syohin_cd,");
		//		str.append("        tr.tenpo_cd,");
		str.append("        tr.uketsuke_no");
		str.append("    ) sub ");
		str.append("where ");
		//		str.append("    tr.hinsyu_cd = sub.hinsyu_cd and");
		str.append("    tr.bumon_cd = sub.bumon_cd and");
		//      ↑↑2006.07.04 baoql カスタマイズ修正↑↑
		str.append("    tr.syohin_cd = sub.syohin_cd and");
		//		str.append("    tr.tenpo_cd = sub.tenpo_cd and");
		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no");
		str.append(")");

		return str.toString();
	}

	//  ↓↓2006.06.28 zhouj カスタマイズ修正↓↓
	/**
	 * 後者優先による登録対象外のデータを特定する作成SQL（部門、商品コードがキー）
	 * @throws
	 */
	public String getTaisyogaiBumonTenSQL(String tableName)
	{
		StringBuffer str = new StringBuffer();

		str.append("(");
		str.append("select /*+ ordered */");
		str.append("    tr.* ");
		str.append("from ");
		str.append("").append(tableName).append(" tr,");
		str.append("    (");
		str.append("    select");
		str.append("        tr.bumon_cd as bumon_cd,");
		str.append("        tr.syohin_cd as syohin_cd,");
		str.append("        tr.uketsuke_no as uketsuke_no,");
		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
		str.append("    from ");
		str.append("").append(tableName).append(" tr,");
		str.append("        (select");
		str.append("            tr.bumon_cd as bumon_cd,");
		str.append("            tr.syohin_cd as syohin_cd,");
		str.append("            max(tr.uketsuke_no) as uketsuke_no");
		str.append("        from ");
		str.append("            tr_toroku_syonin top left outer join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"            top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"            tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		str.append("            tr.update_ts =  ");
		str.append("            (select max(update_ts) from ").append(tableName).append(
			" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");

		////		=== BEGIN === Modify by kou 2006/8/1
		////		新規の場合、商品コードは空白の状況があり得るため。
		////		str.append("            (select max(update_ts) from ").append(tableName).append(" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		//		str.append("            (select max(update_ts) from ")
		//			.append(tableName)
		//			.append(" ts where tr.bumon_cd = ts.bumon_cd ")
		//			.append("	   and (	tr.syohin_cd = ts.syohin_cd ")
		//			.append("			or (tr.syohin_cd is null and tr.syusei_kb = '")
		//				.append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("')")
		//			.append("			or (ts.syohin_cd is null and ts.syusei_kb = '")
		//				.append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("')	)")
		//			.append("	   and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		////		=== END === Modify by kou 2006/8/1

		str.append("        group by");
		str.append("            tr.bumon_cd,");
		str.append("            tr.syohin_cd ");
		str.append("        ) sub");
		str.append("    where");
		str.append("        tr.bumon_cd = sub.bumon_cd and");
		str.append("        tr.syohin_cd = sub.syohin_cd and");

		////		=== BEGIN === Modify by kou 2006/8/1
		////		新規の場合、商品コードは空白の状況があり得るため。
		////		str.append("        tr.syohin_cd = sub.syohin_cd and");
		//		str.append("        (tr.syohin_cd = sub.syohin_cd ");
		//		str.append("         or (tr.syohin_cd is null and tr.syusei_kb = '")
		//			.append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("')");
		//		str.append("         or (sub.syohin_cd is null) ) and ");
		////		=== END === Modify by kou 2006/8/1

		str.append("        tr.uketsuke_no = sub.uketsuke_no");
		str.append("    group by");
		str.append("        tr.bumon_cd,");
		str.append("        tr.syohin_cd,");
		str.append("        tr.uketsuke_no");
		str.append("    ) sub ");
		str.append("where");
		str.append("    tr.bumon_cd = sub.bumon_cd and");
		str.append("    tr.syohin_cd = sub.syohin_cd and");

		////		=== BEGIN === Modify by kou 2006/8/1
		////		新規の場合、商品コードは空白の状況があり得るため。
		////		str.append("    tr.syohin_cd = sub.syohin_cd and");
		//		str.append("    (tr.syohin_cd = sub.syohin_cd ");
		//		str.append("         or (tr.syohin_cd is null and tr.syusei_kb = '")
		//			.append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("')");
		//		str.append("         or (sub.syohin_cd is null) ) and ");
		////		=== END === Modify by kou 2006/8/1

		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no");

		//		===BEGIN=== Add by kou 2006/8/4
		//		新規の場合、商品コードは空白の状況があり得るため。
		//		しかも、同じCSVファイルに多数の商品コードが空白の可能性もある。
		str.append(" union ");
		str.append(" select tr.* from ");
		str.append("            tr_toroku_syonin top left outer join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"              top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' ");
		str.append(
			"          and tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' ");
		// ===BEGIN=== Modify by kou 2006/9/4
		//str.append("          and tr.syohin_cd is null");
		str.append("          and (tr.syohin_cd is null");
		str.append("               or (tr.syohin_cd like '__           '");
		str.append("                   and tr.syohin_cd between '10' and '59') ");
		str.append("              ) ");
		// ===END=== Modify by kou 2006/9/4
		//		===END=== Add by kou 2006/8/4

		str.append(")");

		return str.toString();
	}
	//  ↑↑2006.06.28 zhouj カスタマイズ修正↑↑

	/**
	 * 後者優先による登録対象外のデータを特定する作成SQL（品種、商品コード、発注Noがキー）
	 * @throws
	 */
	public String getTaisyogaiHinsyuHchuNoSQL(String tableName)
	{
		StringBuffer str = new StringBuffer();

		//		str.append("(");
		//		str.append("select /*+ ordered */");
		//		str.append("    tr.* ");
		//		str.append("from ");
		//		str.append("").append(tableName).append(" tr,");
		//		str.append("    (");
		//		str.append("    select");
		////      ↓↓2006.07.04 baoql カスタマイズ修正↓↓
		////		str.append("        tr.hinsyu_cd as hinsyu_cd,");
		//		str.append("        tr.bumon_cd as bumon_cd,");
		//		str.append("        tr.syohin_cd as syohin_cd,");
		//		str.append("        tr.uketsuke_no as uketsuke_no,");
		////		str.append("        tr.hachuno_cd as hachuno_cd,");
		//		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
		//		str.append("    from ");
		//		str.append("").append(tableName).append(" tr,");
		//		str.append("        (select");
		////		str.append("            tr.hinsyu_cd as hinsyu_cd,");
		//		str.append("            tr.bumon_cd as bumon_cd,");
		////      ↑↑2006.07.04 baoql カスタマイズ修正↑↑
		//		str.append("            tr.syohin_cd as syohin_cd,");
		////		str.append("            tr.hachuno_cd as hachuno_cd,");
		//		str.append("            max(tr.uketsuke_no) as uketsuke_no");
		//		str.append("        from ");
		////      ↓↓2006.06.28 zhouj カスタマイズ修正↓↓
		//		str.append("            tr_toroku_syonin top left outer join ");
		//		str.append("").append(tableName).append(" tr");
		//		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		//		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		//		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		//		str.append("        where");
		//		str.append("            top.toroku_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		////		str.append("            tr.by_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		////      ↑↑2006.06.28 zhouj カスタマイズ修正↑↑
		//		str.append("            tr.mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "' and");
		//		str.append("            tr.update_ts =  ");
		////      ↓↓2006.07.04 baoql カスタマイズ修正↓↓
		////		str.append("            (select max(update_ts) from ").append(tableName).append(" ts where tr.hinsyu_cd = ts.hinsyu_cd and tr.syohin_cd = ts.syohin_cd and tr.hachuno_cd = ts.hachuno_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		//		str.append("            (select max(update_ts) from ").append(tableName).append(" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		//		str.append("        group by");
		////		str.append("            tr.hinsyu_cd,");
		//		str.append("            tr.bumon_cd,");
		//		str.append("            tr.syohin_cd");
		////		str.append("            tr.syohin_cd,");
		////		str.append("            tr.hachuno_cd");
		//		str.append("        ) sub");
		//		str.append("    where");
		////		str.append("        tr.hinsyu_cd = sub.hinsyu_cd and");
		//		str.append("        tr.bumon_cd = sub.bumon_cd and");
		//		str.append("        tr.syohin_cd = sub.syohin_cd and");
		////		str.append("        tr.hachuno_cd = sub.hachuno_cd and");
		//		str.append("        tr.uketsuke_no = sub.uketsuke_no");
		//		str.append("    group by");
		////		str.append("        tr.hinsyu_cd,");
		//		str.append("        tr.bumon_cd,");
		//		str.append("        tr.syohin_cd,");
		////		str.append("        tr.hachuno_cd,");
		//		str.append("        tr.uketsuke_no");
		//		str.append("    ) sub ");
		//		str.append("where");
		////		str.append("    tr.hinsyu_cd = sub.hinsyu_cd and");
		//		str.append("    tr.bumon_cd = sub.bumon_cd and");
		////      ↑↑2006.07.04 baoql カスタマイズ修正↑↑
		//		str.append("    tr.syohin_cd = sub.syohin_cd and");
		////		str.append("    tr.hachuno_cd = sub.hachuno_cd and");
		//		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
		//		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no");
		//
		////		===BEGIN=== Add by kou 2006/8/14
		////		新規の場合、商品コードは空白の状況があり得るため。
		////		しかも、同じCSVファイルに多数の商品コードが空白の可能性もある。
		//		str.append(" union ");
		//		str.append(" select tr.* from ");
		//		str.append("            tr_toroku_syonin top left outer join ");
		//		str.append("").append(tableName).append(" tr");
		//		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		//		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		//		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		//		str.append("        where");
		//		str.append("              top.toroku_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' ");
		//		str.append("          and tr.mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "' ");
		//		// ===BEGIN=== Modify by kou 2006/9/4
		//		//str.append("          and tr.syohin_cd is null");
		//		str.append("          and (tr.syohin_cd is null");
		//		str.append("               or (tr.syohin_cd like '__           '");
		//		str.append("                   and tr.syohin_cd between '10' and '59') ");
		//		str.append("              ) ");
		//		// ===END=== Modify by kou 2006/9/4
		////		===END=== Add by kou 2006/8/14

		//↓↓2006.09.18 H.Yamamoto カスタマイズ修正↓↓
		str.append("(");
		str.append("select /*+ ordered */");
		str.append("    tr.torikomi_dt, ");
		str.append("    tr.excel_file_syubetsu,");
		str.append("    tr.uketsuke_no,");
		str.append("    tr.uketsuke_seq ");
		str.append("from ");
		str.append("").append(tableName).append(" tr,");
		str.append("    (");
		str.append("    select");
		str.append("        tr.bumon_cd as bumon_cd,");
		str.append("        tr.syohin_cd as syohin_cd,");
		str.append("        tr.uketsuke_no as uketsuke_no,");
		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
		str.append("    from ");
		str.append("").append(tableName).append(" tr,");
		str.append("        (select");
		str.append("            tr.bumon_cd as bumon_cd,");
		str.append("            tr.syohin_cd as syohin_cd,");
		str.append("            max(tr.uketsuke_no) as uketsuke_no");
		str.append("        from ");
		str.append("            tr_toroku_syonin top inner join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"            top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"            tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		str.append("            tr.update_ts =  ");
		str.append("            (select max(update_ts) from ").append(tableName).append(
			" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		str.append("        group by");
		str.append("            tr.bumon_cd,");
		str.append("            tr.syohin_cd");
		str.append("        ) sub");
		str.append("    where");
		str.append("        tr.bumon_cd = sub.bumon_cd and");
		str.append("        tr.syohin_cd = sub.syohin_cd and");
		str.append("        tr.uketsuke_no = sub.uketsuke_no");
		str.append("    group by");
		str.append("        tr.bumon_cd,");
		str.append("        tr.syohin_cd,");
		str.append("        tr.uketsuke_no");
		str.append("    ) sub ");
		str.append("where");
		str.append("    tr.bumon_cd = sub.bumon_cd and");
		str.append("    tr.syohin_cd = sub.syohin_cd and");
		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no");
		str.append(" union ");
		str.append(" select ");
		str.append("    tr.torikomi_dt, ");
		str.append("    tr.excel_file_syubetsu,");
		str.append("    tr.uketsuke_no,");
		str.append("    tr.uketsuke_seq ");
		str.append("from ");
		str.append("            tr_toroku_syonin top inner join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"              top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' ");
		str.append(
			"          and tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' ");
		str.append("          and (tr.syohin_cd is null");
		str.append("               or (tr.syohin_cd like '__           '");
		str.append("                   and tr.syohin_cd between '10' and '59') ");
		str.append("              ) ");
		//		↑↑2006.09.18 H.Yamamoto カスタマイズ修正↑↑

		str.append(")");

		return str.toString();
	}

	/**
	 * 登録取消しデータメッセージ作成SQL
	 * @throws
	 */
	public String getCancelMessageSQL(String tableName, String syubetu, Map map)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		str.append(" r_message ");
		str.append("(select ");
		//      ↓↓2006.06.28 zhouj カスタマイズ修正↓↓
		//		str.append("   torikomi_dt,"); //取込日
		//		str.append("   excel_file_syubetsu,"); //EXCELファイル種別
		//		str.append("   uketsuke_no,"); //受付ファイルNo.
		//		str.append("   uketsuke_seq,"); //受付SEQNo.
		str.append("   tr.torikomi_dt,"); //取込日
		str.append("   tr.excel_file_syubetsu,"); //EXCELファイル種別
		str.append("   tr.uketsuke_no,"); //受付ファイルNo.
		str.append("   tr.uketsuke_seq,"); //受付SEQNo.
		str.append("'" + syubetu + "',"); //シート種別
		str.append("   '0002',"); //結果メッセージコード
		str.append("'" + map.get("0002") + "',"); //結果メッセージ
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no, ");
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no ");
		str.append(" from ");
		str.append("   tr_toroku_syonin top left outer join ");
		str.append(tableName);
		str.append("         tr ");
		str.append("   on top.torikomi_dt = tr.torikomi_dt ");
		str.append("   and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("   and top.uketsuke_no = tr.uketsuke_no ");
		str.append(" where");
		str.append("   syusei_kb = '" + mst006701_SyuseiKbDictionary.CANCEL.getCode() + "' and");
		//		str.append("   by_syonin_fg= '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		str.append(
			"   top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		//      ↑↑2006.06.28 zhouj カスタマイズ修正↑↑
		str.append(
			"   mst_mainte_fg= '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "')");

		return str.toString();
	}

	//	↓↓移植(2006.05.22)↓↓
	/**
	 * 登録取消しデータ処理SQL作成
	 * @throws SQLException 
	 */
	public String getCancelSQL(String tableName, String batchID) throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update ");
		//      ↓↓2006.07.06 zhouj カスタマイズ修正↓↓
		//		str.append(tableName);
		str.append(tableName).append(" tr ");
		//      ↑↑2006.07.06 zhouj カスタマイズ修正↑↑
		str.append(" set ");
		// ===BEGIN=== Modify by kou 2006/8/1
		// 登録取消処理は正常ではなく、警告です
		//str.append("mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORIZUMI.getCode() + "',");
		str.append(
			"  mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "',");
		// ===END=== Modify by kou 2006/8/1
		str.append(
			"update_ts = '"
				+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")
				+ "',");
		//		↑↑移植(2006.05.22)↑↑
		//      ↓↓2006.06.28 zhouj カスタマイズ修正↓↓
		str.append("update_user_id = '" + batchID + "' ");
		str.append("where");
		str.append(" syusei_kb = '" + mst006701_SyuseiKbDictionary.CANCEL.getCode() + "' and");
		//		str.append(" by_syonin_fg= '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		str.append(" mst_mainte_fg= '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		str.append(" and torikomi_dt||excel_file_syubetsu||uketsuke_no in ");
		str.append(" (select ");
		str.append("      tr.torikomi_dt||tr.excel_file_syubetsu||tr.uketsuke_no");
		//取込日,EXCELファイル種別,受付ファイルNo.
		str.append("  from ");
		str.append("      tr_toroku_syonin top left outer join ");
		str.append(tableName).append(" tr ");
		str.append("      on top.torikomi_dt = tr.torikomi_dt ");
		str.append("      and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("      and top.uketsuke_no = tr.uketsuke_no ");
		str.append("  where");
		str.append(
			"      top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' ");
		str.append(" )");
		//      ↑↑2006.06.28 zhouj カスタマイズ修正↑↑

		return str.toString();
	}

	/**
	 * 同一処理内のJANコードバッティングデータのメッセージ作成SQL
	 * @throws
	 */
	//	↓↓2006.06.27 jianglm カスタマイズ修正↓↓
	//public String getJanBattingMessageSQL(String tableName, String syubetu, Map map) {
	public String getJanBattingMessageSQL(String tableName, String syubetu, Map map)
		throws SQLException
	{
		//	↑↑2006.06.27 jianglm カスタマイズ修正↑↑
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		str.append(" r_message ");
		str.append("(select");
		str.append("    sub1.torikomi_dt,"); //取込日
		str.append("    sub1.excel_file_syubetsu,"); //EXCELファイル種別
		str.append("    sub1.uketsuke_no,"); //受付ファイルNo.
		str.append("    sub1.uketsuke_seq,"); //受付SEQNo.
		str.append("'" + syubetu + "',"); //シート種別
		str.append("   '0106',"); //結果メッセージコード
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓
		//		str.append("'" + map.get("0106") + "'"); //結果メッセージ
		str.append("'" + map.get("0106") + "',"); //結果メッセージ
		str.append(
			"  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("    sub1.by_no,");
		str.append(
			"  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("    sub1.by_no ");
		str.append(" from");
		str.append("    (select");
		str.append("        tr.torikomi_dt,");
		str.append("        tr.excel_file_syubetsu,");
		str.append("        tr.uketsuke_no,");
		str.append("        tr.uketsuke_seq,");
		//		str.append("        rst.hanku_cd,");
		//		str.append("        tr.syohin_2_cd");
		str.append("        rst.bumon_cd, ");
		str.append("        tr.by_no ");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑
		str.append("    from");
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓
		//		str.append("        ").append(tableName).append(" tr left join r_syohin_taikei rst on (rst.hinsyu_cd = tr.hinsyu_cd)");
		str
			.append("  (select tr1.*,tts.by_no as by_no from tr_toroku_syonin tts   left join  ")
			.append(tableName)
			.append(" tr1   ");
		str.append("    on  tr1.torikomi_dt=tts.torikomi_dt ");
		str.append("     and  tr1.excel_file_syubetsu=tts.excel_file_syubetsu ");
		str.append("    and  tr1.uketsuke_no=tts.uketsuke_no ");
		str.append("    where");
		str
			.append("        tr1.syusei_kb in ('")
			.append(mst006701_SyuseiKbDictionary.INSERT.getCode())
			.append("','")
			.append(mst006701_SyuseiKbDictionary.UPDATE.getCode())
			.append("') and");
		str.append(
			"        tts.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"        tr1.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "') tr");
		str.append("        ").append("").append(
			" left join r_syohin_taikei rst on (rst.unit_cd = tr.unit_cd ");
		str.append("   and  rst.system_kb = '").append(
			mst000901_KanriKbDictionary.BUMON.getCode()).append(
			"') ");
		//		str.append("    where");
		//		str.append("        syusei_kb in ('").append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("','").append(mst006701_SyuseiKbDictionary.UPDATE.getCode()).append("') and");
		//		str.append("        by_syonin_fg= '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and"); 
		//		str.append("        mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑ 
		str.append("    ) sub1,");
		str.append("    (select");
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓
		//		str.append("        hanku_cd,");
		//		str.append("        syohin_2_cd");
		str.append("        sub.bumon_cd ");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑
		str.append("    from");
		str.append("        (select");
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓  
		//		str.append("            rst.hanku_cd,");
		//		str.append("            tr.syohin_2_cd");
		str.append("            rst.bumon_cd ");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑
		str.append("        from");
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓
		//		str.append("            ").append(tableName).append(" tr left join r_syohin_taikei rst on (rst.hinsyu_cd = tr.hinsyu_cd)");
		str
			.append("  ( select tr1.* from  tr_toroku_syonin tts left join        ")
			.append(tableName)
			.append(" tr1 on ");
		str.append(" tts.torikomi_dt=tr1.torikomi_dt ");
		str.append(" and  tts.excel_file_syubetsu=tr1.excel_file_syubetsu ");
		str.append(" and  tts.uketsuke_no=tr1.uketsuke_no ");
		str.append("        where");
		str
			.append("            tr1.syusei_kb in ('")
			.append(mst006701_SyuseiKbDictionary.INSERT.getCode())
			.append("','")
			.append(mst006701_SyuseiKbDictionary.UPDATE.getCode())
			.append("') and");
		str.append(
			"            tts.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"            tr1.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "'");
		str.append("   ) tr        left join r_syohin_taikei rst on (rst.UNIT_CD = tr.UNIT_CD ");
		str.append("   and  rst.system_kb = '").append(
			mst000901_KanriKbDictionary.BUMON.getCode()).append(
			"') ");
		//		str.append("        where");
		//		str.append("            syusei_kb in ('").append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("','").append(mst006701_SyuseiKbDictionary.UPDATE.getCode()).append("') and"); 
		//		str.append("            by_syonin_fg= '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		//		str.append("            mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑
		str.append("        ) sub");
		str.append("    where");
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓   
		//		str.append("        sub.hanku_cd is not null and");
		//		str.append("        sub.syohin_2_cd is not null");
		str.append("        sub.bumon_cd is not null ");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑  
		str.append("    group by");
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓   
		//		str.append("        sub.hanku_cd,");
		//		str.append("        sub.syohin_2_cd");
		str.append("        sub.bumon_cd ");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓   
		//		str.append("    having");
		//		str.append("        count(sub.hanku_cd) > 1");
		str.append("    having");
		str.append("        count(sub.bumon_cd) > 1");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑
		str.append("    ) sub2 ");
		str.append("where");
		//		↓↓2006.06.27 jianglm カスタマイズ修正↓↓   
		//		str.append("    sub1.hanku_cd = sub2.hanku_cd and");
		//		str.append("    sub1.syohin_2_cd = sub2.syohin_2_cd");
		str.append("    sub1.bumon_cd = sub2.bumon_cd ");
		//		↑↑2006.06.27 jianglm カスタマイズ修正↑↑

		str.append(")");

		return str.toString();
	}

	/**
	 * 同一処理内のJANコードバッティングデータのメッセージ作成SQL
	 * Add by kou 2006/10/2
	 * @throws
	 */
	public String getJanBattingMessageSQL(
		String tableName,
		String syubetu,
		Map map,
		String system_kb)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		str.append(" r_message ");
		str.append("(select");
		str.append("    sub1.torikomi_dt,"); 			//取込日
		str.append("    sub1.excel_file_syubetsu,"); 	//EXCELファイル種別
		str.append("    sub1.uketsuke_no,"); 			//受付ファイルNo.
		str.append("    sub1.uketsuke_seq,"); 			//受付SEQNo.
		str.append("'").append(syubetu).append("',");	//シート種別
		str.append("   '0106',"); 						//結果メッセージコード
		str.append("'").append(map.get("0106")).append("',"); 		//結果メッセージ
		str.append("  '")
			.append(AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora"))
			.append("',");
		str.append("    sub1.by_no,");
		str.append("  '")
			.append(AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora"))
			.append("',");
		str.append("    sub1.by_no ");
		str.append(" from");
		
		str.append(getJanBattingSub1(tableName, system_kb));
		str.append(", ");
		str.append(getJanBattingSub2(tableName, system_kb));
		
		str.append(" where");
		str.append("    sub1.pos_cd = sub2.pos_cd  ");
		str.append(")");

		return str.toString();
	}

	//	↓↓移植(2006.05.22)↓↓
	/**
	 * 同一処理内のJANコードバッティングデータ処理SQL作成
	 * @throws SQLException 
	 */
	public String getJanBattingSQL(String tableName, String batchID) throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update ");
		str.append(tableName).append(" tr ");
		str.append("set ");
		str.append("  mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.ERROR.getCode() + "',");
		str.append(
			"  update_ts = '"
				+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")
				+ "',");
		str.append("  update_user_id = '" + batchID + "' ");
		//↓↓(2005.12.22) チューニング
		//  	str.append("where");
		//		str.append("   exists ");
		//		str.append("   (select");
		//		str.append("      '1'");
		//		str.append("    from");
		//		str.append("    (select");
		//		str.append("        sub1.torikomi_dt,");
		//		str.append("        sub1.excel_file_syubetsu,");
		//		str.append("        sub1.uketsuke_no,");
		//		str.append("        sub1.uketsuke_seq");

		str.append("where (torikomi_dt|| excel_file_syubetsu|| uketsuke_no|| uketsuke_seq ) in");
		//↑↑(2005.12.22) チューニング
		str.append("   (select");
		str.append("       sub1.torikomi_dt||");
		str.append("       sub1.excel_file_syubetsu||");
		str.append("       sub1.uketsuke_no||");
		//		↑↑移植(2006.05.22)↑↑
		str.append("       sub1.uketsuke_seq");
		str.append("      from");
		str.append("        (select");
		str.append("            tr.torikomi_dt,");
		str.append("            tr.excel_file_syubetsu,");
		str.append("            tr.uketsuke_no,");
		str.append("            tr.uketsuke_seq,");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓ 
		//		str.append("            rst.hanku_cd,");
		//		str.append("            tr.syohin_2_cd");
		str.append("            rst.bumon_cd ");
		//   	↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		str.append("        from");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓ 
		//		str.append("            ").append(tableName).append(" tr left join r_syohin_taikei rst on (rst.hinsyu_cd = tr.hinsyu_cd)");
		str.append("  ( select tr1.* from tr_toroku_syonin tts left join  ").append(
			tableName).append(
			" tr1 on ");
		str.append("      tr1.torikomi_dt=tts.torikomi_dt  ");
		str.append("     and  tr1.excel_file_syubetsu=tts.excel_file_syubetsu ");
		str.append("     and  tr1.uketsuke_no=tts.uketsuke_no ");
		str.append("        where");
		str
			.append("            syusei_kb in ('")
			.append(mst006701_SyuseiKbDictionary.INSERT.getCode())
			.append("','")
			.append(mst006701_SyuseiKbDictionary.UPDATE.getCode())
			.append("') and");
		str.append(
			"            tts.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"            mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "') tr");
		str.append("      left join r_syohin_taikei rst on  ");
		str.append(" ( rst.system_kb = '").append(
			mst000901_KanriKbDictionary.BUMON.getCode()).append(
			"' ");
		str.append("and rst.unit_cd = tr.unit_cd)");
		//		str.append("        where");
		//		str.append("            syusei_kb in ('").append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("','").append(mst006701_SyuseiKbDictionary.UPDATE.getCode()).append("') and");  
		//		str.append("            by_syonin_fg= '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		//		str.append("            mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		//   	↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		str.append("        ) sub1,");
		str.append("        (select");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓  
		//		str.append("            hanku_cd,");
		//		str.append("            syohin_2_cd");
		str.append("            bumon_cd ");
		//   	↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		str.append("        from");
		str.append("            (select");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓  
		//		str.append("                rst.hanku_cd,");
		//		str.append("                tr.syohin_2_cd");
		str.append("                rst.bumon_cd ");
		//   	↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		str.append("            from");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓  
		//		str.append("                ").append(tableName).append(" tr left join r_syohin_taikei rst on (rst.hinsyu_cd = tr.hinsyu_cd)");
		str
			.append("     ( select tr1.* from tr_toroku_syonin tts left join          ")
			.append(tableName)
			.append(" tr1 on ");
		str.append("    tr1.torikomi_dt=tts.torikomi_dt ");
		str.append("     and  tr1.excel_file_syubetsu=tts.excel_file_syubetsu ");
		str.append("    and  tr1.uketsuke_no=tts.uketsuke_no ");
		str.append("            where");
		str
			.append("                syusei_kb in ('")
			.append(mst006701_SyuseiKbDictionary.INSERT.getCode())
			.append("','")
			.append(mst006701_SyuseiKbDictionary.UPDATE.getCode())
			.append("') and");
		str.append(
			"                tts.TOROKU_SYONIN_FG= '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"                mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "') tr left join ");
		str.append("      r_syohin_taikei rst on  ");
		str.append(" ( rst.system_kb = '").append(
			mst000901_KanriKbDictionary.BUMON.getCode()).append(
			"' ");
		str.append("and rst.unit_cd = tr.unit_cd)");
		//		str.append("            where");
		//		str.append("                syusei_kb in ('").append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("','").append(mst006701_SyuseiKbDictionary.UPDATE.getCode()).append("') and");
		//		str.append("                by_syonin_fg= '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		//		str.append("                mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		//   	↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		str.append("            ) sub");
		str.append("        where");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓ 
		//		str.append("            sub.hanku_cd is not null and");
		//		str.append("            sub.syohin_2_cd is not null");
		str.append("            sub.bumon_cd is not null ");
		//  	↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		str.append("        group by");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓ 
		//		str.append("            sub.hanku_cd,");
		//		str.append("            sub.syohin_2_cd");
		str.append("            sub.bumon_cd ");
		//   	↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		str.append("        having");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓   
		//		str.append("            count(sub.hanku_cd) > 1");
		str.append("            count(sub.bumon_cd) > 1");
		//   	↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		str.append("        ) sub2");
		str.append("      where");
		//   	↓↓2006.06.28 jianglm カスタマイズ修正↓↓   
		//		str.append("        sub1.hanku_cd = sub2.hanku_cd and");
		//		str.append("        sub1.syohin_2_cd = sub2.syohin_2_cd");
		str.append("        sub1.bumon_cd = sub2.bumon_cd ");
		//   	↑↑2006.06.28 jianglm カスタマイズ修正↑↑

		//↓↓(2005.12.22) チューニング
		str.append(" )");
		//		str.append("    ) sub");
		//		str.append("    where");
		//		str.append("        tr.torikomi_dt = sub.torikomi_dt and");
		//		str.append("        tr.excel_file_syubetsu = sub.excel_file_syubetsu and");
		//		str.append("        tr.uketsuke_no = sub.uketsuke_no and");
		//		str.append("        tr.uketsuke_seq = sub.uketsuke_seq");
		//		str.append("   )");
		//↑↑(2005.12.22) チューニング

		return str.toString();
	}

	/**
	 * 同一処理内のJANコードバッティングデータ処理SQL作成
	 * Add by kou 2006/10/2
	 * @throws SQLException 
	 */
	public String getJanBattingSQL(String tableName, String batchID, String system_kb) throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update ");
		str.append(tableName).append(" tr ");
		str.append("set ");
		str.append("  mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.ERROR.getCode() + "',");
		str.append("  update_ts = '")
			.append(AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora"))
			.append("',");
		str.append("  update_user_id = '").append(batchID).append("' ");
		str.append("where (torikomi_dt|| excel_file_syubetsu|| uketsuke_no|| uketsuke_seq ) in");
		str.append("   (select");
		str.append("       sub1.torikomi_dt||");
		str.append("       sub1.excel_file_syubetsu||");
		str.append("       sub1.uketsuke_no||");
		str.append("       sub1.uketsuke_seq");
		str.append("      from");
		
		str.append(getJanBattingSub1(tableName, system_kb));
		str.append(", ");
		str.append(getJanBattingSub2(tableName, system_kb));
		
		str.append("      where");
		str.append("        sub1.pos_cd = sub2.pos_cd");
		str.append(" )");

		return str.toString();
	}

	/**
	 * @param tableName
	 * @param system_kb
	 * @return
	 */
	private String getJanBattingSub2(String tableName, String system_kb)
	{
		StringBuffer str = new StringBuffer();
		
		str.append("        (select");
		str.append("            pos_cd");
		str.append("        from");
		str.append("            (select");
		str.append("                tr.pos_cd");
		str.append("            from");
		str
			.append("     ( select tr1.* from tr_toroku_syonin tts left join          ")
			.append(tableName)
			.append(" tr1 on ");
		str.append("    tr1.torikomi_dt=tts.torikomi_dt ");
		str.append("     and  tr1.excel_file_syubetsu=tts.excel_file_syubetsu ");
		str.append("    and  tr1.uketsuke_no=tts.uketsuke_no ");
		str.append("            where");
		str
			.append("                syusei_kb in ('")
			.append(mst006701_SyuseiKbDictionary.INSERT.getCode())
			.append("','")
			.append(mst006701_SyuseiKbDictionary.UPDATE.getCode())
			.append("') and");
		str.append("                tts.TOROKU_SYONIN_FG= '")
			.append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' and");
		str.append("                mst_mainte_fg = '")
			.append(mst006801_MstMainteFgDictionary.SYORITYU.getCode())
			.append("') tr left join ");
		str.append("      r_syohin_taikei rst on  ");
		str.append(" ( rst.system_kb = '")
			.append(system_kb).append("' ");
		str.append("and rst.unit_cd = tr.unit_cd)");
		str.append("            ) sub");
		str.append("        where");
		str.append("            sub.pos_cd is not null");
		str.append("        group by");
		str.append("            sub.pos_cd");
		str.append("        having");
		str.append("            count(sub.pos_cd) > 1");
		// 商品マスタに登録された全てのPOS_CD
		str.append(" UNION ");
		str.append(" SELECT SYOHIN_2_CD POS_CD  FROM R_SYOHIN ");
		str.append("  WHERE SYSTEM_KB = '").append(system_kb).append("' ");
		str.append("        ) sub2 ");

		return str.toString();
	}

	/**
	 * @param system_kb
	 * @return
	 */
	private String getJanBattingSub1(String tableName, String system_kb)
	{
		StringBuffer str = new StringBuffer();
		
		str.append("        (select");
		str.append("            tr.torikomi_dt,");
		str.append("            tr.excel_file_syubetsu,");
		str.append("            tr.uketsuke_no,");
		str.append("            tr.uketsuke_seq,");
		str.append("            rst.bumon_cd, ");
		str.append("            tr.pos_cd, ");
		str.append("            tr.by_no ");
		str.append("        from");
		str.append("  ( select tr1.*, tts.by_no as by_no ")
			.append(" from tr_toroku_syonin tts left join  ")
			.append(tableName).append(" tr1 on ");
		str.append("      tr1.torikomi_dt=tts.torikomi_dt  ");
		str.append("     and  tr1.excel_file_syubetsu=tts.excel_file_syubetsu ");
		str.append("     and  tr1.uketsuke_no=tts.uketsuke_no ");
		str.append("        where");
		str.append("            syusei_kb in ('")
			.append(mst006701_SyuseiKbDictionary.INSERT.getCode())
			.append("','")
			.append(mst006701_SyuseiKbDictionary.UPDATE.getCode())
			.append("') and");
		str.append("            tts.toroku_syonin_fg = '")
			.append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' and");
		str.append("            mst_mainte_fg = '")
			.append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("') tr");
		str.append("      left join r_syohin_taikei rst on  ");
		str.append(" ( rst.system_kb = '")
			.append(system_kb).append("' ");
		str.append("and rst.unit_cd = tr.unit_cd)");
		str.append("        ) sub1 ");
		
		return str.toString();
	}

	//	↓↓移植(2006.05.22)↓↓
	/**
	 * マスタメンテナンスフラグ更新処理SQL作成
	 * @throws SQLException 
	 */
	public String getMstMainteSQL(
		String tableName,
		String[] key,
		String mast_mainte_fg,
		String batchID)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update ");
		str.append(tableName);
		str.append(" set ");
		str.append("mst_mainte_fg = '" + mast_mainte_fg + "',");
		str.append("update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("update_user_id = '" + batchID + "' ");
		str.append("where");
		str.append(" torikomi_dt = '" + key[0] + "' and");
		str.append(" excel_file_syubetsu = '" + key[1] + "' and");
		str.append(" uketsuke_no = '" + key[2] + "' and");
		str.append(" uketsuke_seq = '" + key[3] + "'");

		return str.toString();
	}

	//	↓↓TRテーブルに商品コード更新処理追加(2006.07.27)↓↓
	/**
	 * 更新処理SQL作成
	 * @throws SQLException 
	 */
	public String updateSyohinCodeSQL(String tableName, String[] key, String syohin_cd)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update ");
		str.append(tableName);
		str.append(" set ");
//		str.append("syohin_cd = '" + syohin_cd + "' ");
		str.append("tr_syohin_cd = '" + syohin_cd + "' ");
		str.append("where");
		str.append(" torikomi_dt = '" + key[0] + "' and");
		str.append(" excel_file_syubetsu = '" + key[1] + "' and");
		str.append(" uketsuke_no = '" + key[2] + "' and");
		str.append(" uketsuke_seq = '" + key[3] + "'");

		return str.toString();
	}

	/*↓↓(2005.12.26)以降のロジックは、全業種対応後に正式(末尾の"2"を外す)とする予定 ------------------------------　*/

	/**
	 * 後者優先による登録対象外のデータをワークに格納するSQL
	 * @throws
	 */
	public String getInsertTaisyogaiSQL(String tableName, String strWhere)
	{
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		//str.append(" session.tmp_tr_syohin ");
				str.append(" tmp_tr_syohin ");
		str.append("(select ");
		//      ↓↓2006.07.05 zhouj カスタマイズ修正↓↓
		//		str.append("   torikomi_dt,"); //取込日
		//		str.append("   excel_file_syubetsu,"); //EXCELファイル種別
		//		str.append("   uketsuke_no,"); //受付ファイルNo.
		//		str.append("   uketsuke_seq "); //受付SEQNo.
		str.append("   tr.torikomi_dt,"); //取込日
		str.append("   tr.excel_file_syubetsu,"); //EXCELファイル種別
		str.append("   tr.uketsuke_no,"); //受付ファイルNo.
		str.append("   tr.uketsuke_seq "); //受付SEQNo.
		str.append(" from ");
		str.append("   tr_toroku_syonin top left outer join ");
		str.append(tableName).append(" tr");
		str.append("   on top.torikomi_dt = tr.torikomi_dt ");
		str.append("   and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("   and top.uketsuke_no = tr.uketsuke_no ");
		str.append(" where");
		//		str.append("   tr.by_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' and");
		str.append(
			"   top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		//      ↑↑2006.07.05 zhouj カスタマイズ修正↑↑
		str.append(
			"   tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		//		↓↓移植(2006.05.22)↓↓
		str.append(
			"	(tr.torikomi_dt || tr.excel_file_syubetsu || tr.uketsuke_no || tr.uketsuke_seq) not in ");
		str.append("	(select ");
		str.append("		re.torikomi_dt || ");
		str.append("		re.excel_file_syubetsu || ");
		str.append("		re.uketsuke_no || ");
		//		↑↑移植(2006.05.22)↑↑
		str.append("		re.uketsuke_seq ");
		str.append("    from ");
		str.append(strWhere);
		str.append("   )");
		str.append(")");

		return str.toString();
	}

	/**
	 * 後者優先による登録対象外のデータのメッセージ作成SQL
	 * @throws
	 */
	public String getTaisyogaiMessageSQL2(String tableName, String syubetu, Map map)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		str.append(" r_message ");
		str.append("(select ");
		//      ↓↓2006.06.30 zhouj カスタマイズ修正↓↓
		str.append("   top.torikomi_dt,"); //取込日
		str.append("   top.excel_file_syubetsu,"); //EXCELファイル種別
		str.append("   top.uketsuke_no,"); //受付ファイルNo.
		str.append("   nvl(tts.uketsuke_seq,0),"); //受付SEQNo.
		str.append("'" + syubetu + "',"); //シート種別
		str.append("   '0003',"); //結果メッセージコード
		//		str.append("'" + map.get("0003") + "'"); //結果メッセージ
		str.append("'" + map.get("0003") + "',"); //結果メッセージ
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no, ");
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no ");
		str.append(" from ");
		// === BEGIN === Modify By kou 2006/7/26
		str.append("   tr_toroku_syonin top right outer join ");
		//		str.append("   tr_toroku_syonin top left outer join ");
		// === END === Modify By kou 2006/7/26

		str.append(" tmp_tr_syohin tts");
		//str.append("   session.tmp_tr_syohin tts ");
		str.append("   on top.torikomi_dt = tts.torikomi_dt ");
		str.append("   and top.excel_file_syubetsu = tts.excel_file_syubetsu ");
		str.append("   and top.uketsuke_no = tts.uketsuke_no ");
		//      ↑↑2006.06.30 zhouj カスタマイズ修正↑↑
		str.append(")");

		return str.toString();
	}

	//	↓↓移植(2006.05.22)↓↓
	/**
	 * 後者優先による登録対象外のデータ処理SQL作成
	 * @throws SQLException 
	 */
	public String getTaisyogaiSQL2(String tableName, String batchID) throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update /*+ ordered */ ");
		str.append(tableName).append(" tr ");
		str.append("set ");
		// ===BEGIN=== Modify by kou 2006/8/1
		// 登録対象外処理はエラーではなく、警告です
		//str.append("  mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.ERROR.getCode() + "',");
		str.append(
			"  mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "',");
		// ===END=== Modify by kou 2006/8/1
		str.append(
			"  update_ts = '"
				+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")
				+ "',");
		str.append("  update_user_id = '" + batchID + "' ");
		str.append("where");
		str.append("  (torikomi_dt|| excel_file_syubetsu|| uketsuke_no|| uketsuke_seq) in");
		str.append("  (select torikomi_dt|| excel_file_syubetsu|| uketsuke_no|| uketsuke_seq ");
		//		↑↑移植(2006.05.22)↑↑
		//str.append("     from session.tmp_tr_syohin ");
		str.append("     from tmp_tr_syohin ");
		str.append("  )");

		return str.toString();
	}

/*	public String getcreatSQL()
	{
		StringBuffer str = new StringBuffer();

		str.append(" DECLARE GLOBAL TEMPORARY TABLE TMP_TR_SYOHIN ");
		str.append(" (TORIKOMI_DT                    CHAR(8) NOT NULL, ");
		str.append(" EXCEL_FILE_SYUBETSU            CHAR(3) NOT NULL, ");
		str.append(" UKETSUKE_NO                    NUMERIC(5,0) NOT NULL, ");
		str.append(" UKETSUKE_SEQ                    NUMERIC(6,0) NOT NULL ");
		str.append(" ) ON COMMIT DELETE ROWS ");

		return str.toString();

	}
*/
	//	===BEGIN=== Add by kou 2006/8/22
	/**
	  * 登録処理状態更新
	 * @param torikomiDT
	 * @param excelSyubetsu
	 * @param uketsukeNO
	 * @param string
	 * 
	 * @return SQL
	 */
	public String getUpdateJyotaiFlgSql(
		String torikomiDT,
		String excelSyubetsu,
		String uketsukeNO,
		String JyotaiFlg,
		String batchID)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update tr_toroku_syonin");
		str.append(" set ");
		str.append("SYORI_JYOTAI_FG = '").append(JyotaiFlg).append("',");
		str
			.append("update_ts = '")
			.append(AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora"))
			.append("',");
		str.append("update_user_id = '").append(batchID).append("' ");
		str.append("where");
		str.append(" torikomi_dt = '").append(torikomiDT).append("' and");
		str.append(" excel_file_syubetsu = '").append(excelSyubetsu).append("' and");
		str.append(" uketsuke_no = '").append(uketsukeNO).append("'");

		return str.toString();
	}
	//	===END=== Add by kou 2006/8/22	

	//	↓↓2006.09.18 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * 対象外データ保存TMP生成SQL作成
	 * @throws
	 */
	public String getTaisyogaiKeyCreateSQL()
	{
		StringBuffer str = new StringBuffer();

		str.append(" DECLARE GLOBAL TEMPORARY TABLE TMP_TR_TAISYOGAI_KEY ");
		str.append(" (TORIKOMI_DT                    CHAR(8) NOT NULL, ");
		str.append(" EXCEL_FILE_SYUBETSU             CHAR(3) NOT NULL, ");
		str.append(" UKETSUKE_NO                     NUMERIC(5,0) NOT NULL, ");
		str.append(" UKETSUKE_SEQ                    NUMERIC(6,0) NOT NULL) ");

		return str.toString();

	}

	/**
	 * 対象外データ保存処理SQL作成
	 * @throws
	 */
	public String getTaisyogaiSQL(String tableName, String strWhere)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("insert into tmp_tr_taisyogai_key ");
		sql.append("( ");

		sql.append("select tr.torikomi_dt,");
		sql.append("       tr.excel_file_syubetsu,");
		sql.append("       tr.uketsuke_no,");
		sql.append("       tr.uketsuke_seq ");
		sql.append("  from ");
		sql.append(tableName).append(" tr ");
		sql.append("  where ");
		sql.append(
			"   tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and ");
		sql.append("   NOT EXISTS ( select * from ");
		sql.append(strWhere).append(" sub ");
		sql.append("   where ");
		sql.append("     tr.torikomi_dt = sub.torikomi_dt and ");
		sql.append("     tr.excel_file_syubetsu = sub.excel_file_syubetsu and ");
		sql.append("     tr.uketsuke_no = sub.uketsuke_no and ");
		sql.append("     tr.uketsuke_seq = sub.uketsuke_seq ) )");

		return sql.toString();
	}

	/**
	 * 後者優先による登録対象外のデータのメッセージ作成SQL
	 * @throws
	 */
	public String getTaisyogaiMessageNewSQL(String tableName, String syubetu, Map map)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		str.append(" r_message ");
		str.append("(select ");
		str.append("   tr.torikomi_dt,"); //取込日
		str.append("   tr.excel_file_syubetsu,"); //EXCELファイル種別
		str.append("   tr.uketsuke_no,"); //受付ファイルNo.
		str.append("   tr.uketsuke_seq,"); //受付SEQNo.
		str.append("'" + syubetu + "',"); //シート種別
		str.append("   '0003',"); //結果メッセージコード
		str.append("'" + map.get("0003") + "',"); //結果メッセージ
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no, ");
		str.append(
			"   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no ");
		str.append(" from ");
		str.append("   tr_toroku_syonin top inner join ");
		str.append("   tmp_tr_taisyogai_key tr");
		str.append("   on top.torikomi_dt = tr.torikomi_dt ");
		str.append("   and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("   and top.uketsuke_no = tr.uketsuke_no ");
		str.append(" where");
		str.append(
			"   top.toroku_syonin_fg = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' ");
		str.append(")");

		return str.toString();
	}

	/**
	 * 後者優先による登録対象外のデータ処理SQL作成
	 * @throws
	 */
	public String getTaisyogaiNewSQL(String tableName, String batchID) throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update /*+ ordered */ ");
		str.append(tableName).append(" tr ");
		str.append("set ");
		str.append(
			"  mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "',");
		str.append(
			"  update_ts = '"
				+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")
				+ "',");
		str.append("  update_user_id = '" + batchID + "' ");
		str.append("where");
		str.append(
			"   tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and ");
		str.append("   EXISTS ( select * from ");
		str.append("   tmp_tr_taisyogai_key sub ");
		str.append("   where ");
		str.append("     tr.torikomi_dt = sub.torikomi_dt and ");
		str.append("     tr.excel_file_syubetsu = sub.excel_file_syubetsu and ");
		str.append("     tr.uketsuke_no = sub.uketsuke_no and ");
		str.append("     tr.uketsuke_seq = sub.uketsuke_seq )");

		return str.toString();
	}
	//	↑↑2006.09.18 H.Yamamoto カスタマイズ修正↑↑

	//	↓↓2006.09.19 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * 後者優先による登録対象外のデータを特定する作成SQL（部門、商品コード、店舗がキー）
	 * @throws
	 */
	public String getTaisyogaiBumonTenNewSQL(String tableName)
	{
		StringBuffer str = new StringBuffer();

		str.append("(");
		str.append("select /*+ ordered */");
		str.append("    tr.torikomi_dt, ");
		str.append("    tr.excel_file_syubetsu,");
		str.append("    tr.uketsuke_no,");
		str.append("    tr.uketsuke_seq ");
		str.append("from ");
		str.append("").append(tableName).append(" tr,");
		str.append("    (");
		str.append("    select");
		str.append("        tr.bumon_cd as bumon_cd,");
		str.append("        tr.syohin_cd as syohin_cd,");
		str.append("        tr.tenpo_cd as tenpo_cd,");
		str.append("        tr.uketsuke_no as uketsuke_no,");
		str.append("        max(tr.sakusei_gyo_no) as sakusei_gyo_no");
		str.append("    from ");
		str.append("").append(tableName).append(" tr,");
		str.append("        (select");
		str.append("            tr.bumon_cd as bumon_cd,");
		str.append("            tr.syohin_cd as syohin_cd,");
		str.append("            tr.tenpo_cd as tenpo_cd,");
		str.append("            max(tr.uketsuke_no) as uketsuke_no");
		str.append("        from ");
		str.append("            tr_toroku_syonin top inner join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"            top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' and");
		str.append(
			"            tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' and");
		str.append("            tr.update_ts =  ");
		str.append("            (select max(update_ts) from ").append(tableName).append(
			" ts where tr.bumon_cd = ts.bumon_cd and tr.syohin_cd = ts.syohin_cd and tr.tenpo_cd = ts.tenpo_cd and tr.mst_mainte_fg = ts.mst_mainte_fg)");
		str.append("        group by");
		str.append("            tr.bumon_cd,");
		str.append("            tr.syohin_cd,");
		str.append("            tr.tenpo_cd ");
		str.append("        ) sub");
		str.append("    where");
		str.append("        tr.bumon_cd = sub.bumon_cd and");
		str.append("        tr.syohin_cd = sub.syohin_cd and");
		str.append("        tr.tenpo_cd = sub.tenpo_cd and");
		str.append("        tr.uketsuke_no = sub.uketsuke_no");
		str.append("    group by");
		str.append("        tr.bumon_cd,");
		str.append("        tr.syohin_cd,");
		str.append("        tr.tenpo_cd,");
		str.append("        tr.uketsuke_no");
		str.append("    ) sub ");
		str.append("where");
		str.append("    tr.bumon_cd = sub.bumon_cd and");
		str.append("    tr.syohin_cd = sub.syohin_cd and");
		str.append("    tr.tenpo_cd = sub.tenpo_cd and");
		str.append("    tr.uketsuke_no = sub.uketsuke_no and");
		str.append("    tr.sakusei_gyo_no = sub.sakusei_gyo_no");
		str.append(" union ");
		str.append(" select ");
		str.append("    tr.torikomi_dt, ");
		str.append("    tr.excel_file_syubetsu,");
		str.append("    tr.uketsuke_no,");
		str.append("    tr.uketsuke_seq ");
		str.append("from ");
		str.append("            tr_toroku_syonin top inner join ");
		str.append("").append(tableName).append(" tr");
		str.append("            on top.torikomi_dt = tr.torikomi_dt ");
		str.append("            and top.excel_file_syubetsu = tr.excel_file_syubetsu ");
		str.append("            and top.uketsuke_no = tr.uketsuke_no ");
		str.append("        where");
		str.append(
			"              top.toroku_syonin_fg = '"
				+ mst006501_BySyoninFgDictionary.SYONIN.getCode()
				+ "' ");
		str.append(
			"          and tr.mst_mainte_fg = '"
				+ mst006801_MstMainteFgDictionary.SYORITYU.getCode()
				+ "' ");
		str.append("          and (tr.syohin_cd is null");
//		str.append("               or (tr.syohin_cd like '__           '");
		str.append("               or (length(trim(tr.syohin_cd)) = 2 ");
		str.append("                   and tr.syohin_cd between '10' and '59') ");
		str.append("              ) ");
		str.append(")");

		return str.toString();
	}
	//	↑↑2006.09.19 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * マスタメンテナンスフラグ更新処理PreparedStatement作成
	 * @throws SQLException 
	 */
	public PreparedStatement getMstMaintePstmt(MasterDataBase dataBase, String tableName)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update ").append(tableName);
		str.append(" set ");
		str.append("mst_mainte_fg = ?,");
		str.append("update_ts = ?,");
		str.append("update_user_id = ? ");
		str.append("where");
		str.append(" torikomi_dt = ? and");
		str.append(" excel_file_syubetsu = ? and");
		str.append(" uketsuke_no = ? and");
		str.append(" uketsuke_seq = ?");

		return dataBase.getPrepareStatement(str.toString());
	}

	/**
	 * マスタメンテナンスフラグ更新処理PreparedStatementのセット
	 * @throws SQLException 
	 */
	public void setMstMaintePstmt(
		PreparedStatement pstmt,
		String[] key,
		String mst_mainte_fg,
		String batchID)
		throws SQLException
	{

		int idx = 0;

		//マスタメンテナンスフラグ
		idx++;
		pstmt.setString(idx, mst_mainte_fg);

		//更新日付
		idx++;
		pstmt.setString(idx, AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora"));

		//更新者ID
		idx++;
		pstmt.setString(idx, batchID);

		//取込日
		idx++;
		pstmt.setString(idx, key[0]);

		//EXCELファイル種別
		idx++;
		pstmt.setString(idx, key[1]);

		//受付ファイルNo
		idx++;
		pstmt.setString(idx, key[2]);

		//受付SEQ
		idx++;
		pstmt.setString(idx, key[3]);
	}

	/**
	  * 登録処理状態更新PreparedStatementを取得
	 * @param torikomiDT
	 * @param excelSyubetsu
	 * @param uketsukeNO
	 * @param string
	 * 
	 * @return PreparedStatement
	 */
	public PreparedStatement getUpdateJyotaiFlgPstmt(MasterDataBase dataBase) throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update tr_toroku_syonin");
		str.append(" set ");
		str.append("SYORI_JYOTAI_FG = ?,");
		str.append("update_ts = ?,");
		str.append("update_user_id = ? ");
		str.append("where");
		str.append(" torikomi_dt = ? and");
		str.append(" excel_file_syubetsu = ? and");
		str.append(" uketsuke_no = ?");

		return dataBase.getPrepareStatement(str.toString());
	}

	/**
	  * 登録処理状態更新PreparedStatementをセット
	 * @param torikomiDT
	 * @param excelSyubetsu
	 * @param uketsukeNO
	 * @param string
	 * 
	 * @return SQL
	 */
	public void setUpdateJyotaiFlgPstmt(
		PreparedStatement pstmt,
		String torikomiDT,
		String excelSyubetsu,
		String uketsukeNO,
		String JyotaiFlg,
		String batchID)
		throws SQLException
	{

		int idx = 0;

		//承認状態フラグ
		idx++;
		pstmt.setString(idx, JyotaiFlg);

		//更新日付
		idx++;
		pstmt.setString(idx, AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora"));

		//更新者ID
		idx++;
		pstmt.setString(idx, batchID);

		//取込日
		idx++;
		pstmt.setString(idx, torikomiDT);

		//EXCELファイル種別
		idx++;
		pstmt.setString(idx, excelSyubetsu);

		//受付ファイルNo
		idx++;
		pstmt.setString(idx, uketsukeNO);
	}

	/**
	 * 商品コード更新処理PreparedStatement作成
	 * TRテーブル（単品取扱、店別例外、初回導入）
	 * @throws SQLException 
	 */
	public PreparedStatement getUpdateSyohinCodePstmt(MasterDataBase dataBase, String tableName)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("update ");
		str.append(tableName);
		str.append(" set ");
		str.append("syohin_cd = ? ");
		str.append("where");
		str.append(" torikomi_dt = ? and");
		str.append(" excel_file_syubetsu = ? and");
		str.append(" uketsuke_no = ? and");
		str.append(" uketsuke_seq = ?");

		return dataBase.getPrepareStatement(str.toString());
	}

	/**
	 * 商品コード更新処理PreparedStatementのセット
	 * TRテーブル（単品取扱、店別例外、初回導入）
	 * @throws SQLException 
	 */
	public void setUpdateSyohinCodePstmt(PreparedStatement pstmt, String[] key, String syohin_cd)
		throws SQLException
	{

		int idx = 0;

		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);

		//取込日
		idx++;
		pstmt.setString(idx, key[0]);

		//EXCELファイル種別
		idx++;
		pstmt.setString(idx, key[1]);

		//受付ファイルNo
		idx++;
		pstmt.setString(idx, key[2]);

		//受付SEQ
		idx++;
		pstmt.setString(idx, key[3]);
	}

	
	/**
	 * 有効日補正SQL
	 * @throws
	 */
	public String getYukoDtFollowSQL(String tableName, String syubetu, String masterDt)
		throws SQLException
	{
		// 有効日≦マスタ管理日付の場合、
		// 翌日の日付に更新する
		StringBuffer str = new StringBuffer();

		str.append("update ");
		str.append(tableName).append(" ");
		str.append("set ");
//		str.append(" tr_yuko_dt = null ");
		str.append(" tr_yuko_dt = '").append(DateChanger.addDate(masterDt, 1)).append("' ");
		str.append("where ");
		str.append(" mst_mainte_fg = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		str.append(" and ");
		str.append(" tr_yuko_dt <= '").append(masterDt).append("' ");
		str.append(" and ");
		str.append(" syusei_kb <> '").append(mst006701_SyuseiKbDictionary.CANCEL.getCode()).append("' ");
		
		return str.toString();
	}
	
}
