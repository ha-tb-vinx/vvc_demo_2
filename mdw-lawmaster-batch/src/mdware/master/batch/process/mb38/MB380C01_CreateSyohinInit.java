package mdware.master.batch.process.mb38;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst006401_DataKindDictionary;
import mdware.master.common.dictionary.mst006801_MstMainteFgDictionary;
import mdware.master.util.db.MasterDataBase;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.common.db.util.DBUtil;

/**
 * <p>タイトル:商品マスタ生成バッチ初期処理クラス</p>
 * <p>説明:商品マスタ生成処理の初期処理を行います。</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/05/23<BR>
 * @author shimoyama
 * @version 2.00 2006/7/25<BR>
 * @version 2.01 (2010.09.02) A.Fujiwara	販促データワーニングチェック対応
 * @version 3.00 (2013.05.06) M.Ayuakwa ランドローム様対応 [MSTM00004] VIEW UPDATE対応
 * @version 3.01 2021/12/14 HOAI.TTT #6409 対応
 * @author kou
 */

public class MB380C01_CreateSyohinInit {

	private MasterDataBase dataBase = null;

	//batchID
	private String batchID;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	private MB380000_CommonSql comSql = new MB380000_CommonSql(); //共通SQLクラス

	/**
	 * コンストラクタ
	 */
	public MB380C01_CreateSyohinInit() {
		dataBase = new MasterDataBase("rbsite_ora");
	}


	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		batchID = batchId;
		execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		String jobId = userLog.getJobId();

		try {
            String sql = "";

            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");

			// 2008/11/21 TMPテーブルから実テーブルへ変更
			DBUtil.truncateTable(dataBase, "WK_MB38_UKETSUKE_NO");
			DBUtil.truncateTable(dataBase, "WK_MB38_SYOHIN");
			DBUtil.truncateTable(dataBase, "WK_MB38_TAISYO");
			DBUtil.truncateTable(dataBase, "WK_MB38_TAISYOGAI");

			//未処理のデータを処理中にする。
			int iCount = 0;

			//処理対象データのレコード数を取得
			iCount = dataBase.executeUpdate(getTargetUketukeNoSQL());
			dataBase.commit();

			if(iCount == 0){
				writeLog(Level.INFO_INT, "処理対象データがありません。");
				return;
			}else{
				writeLog(Level.INFO_INT, iCount + "件の処理対象データ処理を開始します。");
			}

			//商品マスタ
			iCount = dataBase.executeUpdate(getSyohinInitSQL("TR_SYOHIN"));
			writeLog(Level.INFO_INT, iCount + "件の商品マスタメンテトランを処理中に更新しました。");

			//ギフト商品マスタ
			iCount = dataBase.executeUpdate(getSyohinInitSQL("TR_GIFT_SYOHIN"));
			writeLog(Level.INFO_INT, iCount + "件のギフト商品マスタメンテトランを処理中に更新しました。");

			//計量器マスタ
			iCount = dataBase.executeUpdate(getSyohinInitSQL("TR_KEIRYOKI"));
			writeLog(Level.INFO_INT, iCount + "件の計量器マスタメンテトランを処理中に更新しました。");

//			//単品店取扱マスタ
//			iCount = dataBase.executeUpdate(getSyohinInitSQL("tr_tanpinten_toriatukai"));
//			writeLog(Level.INFO_INT, iCount + "件の単品店取扱マスタメンテトランを処理中に更新しました。");

			//店別商品例外マスタ
			iCount = dataBase.executeUpdate(getSyohinInitSQL("TR_TENSYOHIN_REIGAI"));
			writeLog(Level.INFO_INT, iCount + "件の店別商品例外マスタメンテトランを処理中に更新しました。");

//			//初回導入
//			iCount = dataBase.executeUpdate(getSyohinInitSQL("tr_syokaidonyu"));
//			writeLog(Level.INFO_INT, iCount + "件の初回導入マスタメンテトランを処理中に更新しました。");

			dataBase.commit();


			//
			//	後者優先処理
			//

			writeLog(Level.INFO_INT, "後者優先処理開始");

			// #6409 Mod 2021.12.14 HOAI.TTT (S)
			//Map map = MB380001_CommonMessage.getMsg();
			Map map = MB380C01_CommonMessage.getMsg();
			// #6409 Mod 2021.12.14 HOAI.TTT (E)
			
//			//否認のデータを処理する
//			writeLog(Level.INFO_INT, "否認データ処理開始");
//			sql = comSql.getHiSyoninMessageSQL(TABLE_NA, SYUBETU, map);
//			iCount = dataBase.executeUpdate(sql);
//			writeLog(Level.INFO_INT, iCount + "件の否認データのメッセージを登録しました。");
//
//			sql = comSql.getHiSyoninSQL(TABLE_NA, BATCH_ID);
//			iCount = dataBase.executeUpdate(sql);
//			writeLog(Level.INFO_INT, iCount + "件の否認データを処理済（エラー）に更新しました。");
//			dataBase.commit();
//			writeLog(Level.INFO_INT, "否認データ処理終了");

			// 後者優先処理（アップロード単位での後者優先）
			sql = getKosyaYusenTaisyoSyohin1();
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象データをワークに格納しました。");

			// 後者優先処理（各シート内での後者優先）
			sql = getKosyaYusenTaisyoSyohin2("TR_SYOHIN", mst006401_DataKindDictionary.SYOHIN.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象データ(商品)をワークに格納しました。");

			sql = getKosyaYusenTaisyoSyohin2("TR_GIFT_SYOHIN", mst006401_DataKindDictionary.GIFT.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象データ(ギフト)をワークに格納しました。");

			sql = getKosyaYusenTaisyoSyohin2("TR_KEIRYOKI", mst006401_DataKindDictionary.KEIRYOKI.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象データ(計量器)をワークに格納しました。");

			// 後者優先処理（対象外データをワークに格納）
			sql = getKosyaYusenTaisyogai("TR_SYOHIN", mst006401_DataKindDictionary.SYOHIN.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象外データ(商品)をワークに格納しました。");

			sql = getKosyaYusenTaisyogai("TR_GIFT_SYOHIN", mst006401_DataKindDictionary.GIFT.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象外データ(ギフト)をワークに格納しました。");

			sql = getKosyaYusenTaisyogai("TR_KEIRYOKI", mst006401_DataKindDictionary.KEIRYOKI.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象外データ(計量器)をワークに格納しました。");

			dataBase.commit();


			// メッセージ登録
			sql = getKosyaYusenMessageSQL(map);
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象外データのメッセージを登録しました。");

			// 状態フラグ変更
			sql = getUpdKosyaYusenTaisyogai("TR_SYOHIN", mst006401_DataKindDictionary.SYOHIN.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象外データ(商品)を処理済(警告)に更新しました。");

			sql = getUpdKosyaYusenTaisyogai("TR_GIFT_SYOHIN", mst006401_DataKindDictionary.GIFT.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象外データ(ギフト)を処理済(警告)に更新しました。");

			sql = getUpdKosyaYusenTaisyogai("TR_KEIRYOKI", mst006401_DataKindDictionary.KEIRYOKI.getCode());
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の登録対象外データ(計量器)を処理済(警告)に更新しました。");

			writeLog(Level.INFO_INT, "後者優先処理終了");

			//店別商品例外マスタ
//			iCount = dataBase.executeUpdate(getSyohinInitSQL("TR_TENSYOHIN_REIGAI"));
//			writeLog(Level.INFO_INT, iCount + "件の店別商品例外マスタメンテトランを処理中に更新しました。");

			//処理終了ログ
			writeLog(Level.INFO_INT, "商品マスタ生成バッチ初期処理を終了します。");

		} catch (Exception e) {
			dataBase.rollback();
			writeError(e);
			throw e;
		} finally {
			dataBase.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 * 商品マスタ生成バッチ初期処理SQL作成
	 * （処理対象の受付№を取得する為のＳＱＬを生成）
	 * @throws
	 */
	private String getTargetUketukeNoSQL() {
		StringBuffer sql = new StringBuffer();

		sql.append("insert into WK_MB38_UKETSUKE_NO");
		sql.append("( ");
		sql.append("       torikomi_dt,");
		sql.append("       excel_file_syubetsu,");
		sql.append("       uketsuke_no");
		sql.append(")");

		sql.append("select torikomi_dt,");
		sql.append("       excel_file_syubetsu,");
		sql.append("       uketsuke_no ");
		sql.append("  from tr_toroku_syonin");
		sql.append("  where delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
//2010.09.02 A.Fujiwara Mod 販促データワーニングチェック対応 Start
//		sql.append("  and   syori_jyotai_fg = '0'");
		// 「0」エラーなし、「3」エラーなし、ワーニングあり
		sql.append("  and   (syori_jyotai_fg = '" + mst000101_ConstDictionary.CHECKED_OK +
								"' or syori_jyotai_fg = '" + mst000101_ConstDictionary.CHECKED_WARN + "') ");
//2010.09.02 A.Fujiwara Mod 販促データワーニングチェック対応 End
		sql.append("  and   toroku_syonin_fg = '1'");

//テストロジック
//		sql.append("  and   uketsuke_no < 1000");
//		writeLog(Level.INFO_INT, "");
//		writeLog(Level.INFO_INT, "＊＊＊＊＊＊＊＊＊テストロジック！！＊＊＊＊＊＊＊＊＊");
//		writeLog(Level.INFO_INT, "");
//テストロジック

		return sql.toString();
	}

	/**
	 * 商品マスタ生成バッチ初期処理SQL作成
	 * （処理対象のデータのマスタメンテフラグを"処理中"に更新する）
	 * @throws
	 */
	private String getSyohinInitSQL(String tableName) {
		StringBuffer sql = new StringBuffer();

		sql.append("update ").append(tableName);
		sql.append(" set");
		sql.append(" mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "',");
		//更新日付を元に登録データを判別するため、この時点で更新日付を変更してはいけない
		//		sql.append(" update_ts = to_char(sysdate,'yyyymmddhh24miss'),");
		sql.append(" update_user_id = '" + batchID + "' ");
		sql.append("where");
		sql.append(" mst_mainte_fg = '" + mst006801_MstMainteFgDictionary.MISYORI.getCode() + "' and");
		sql.append(" (uketsuke_no) in ");
		sql.append(" (select uketsuke_no");
		sql.append("    from WK_MB38_UKETSUKE_NO");
		sql.append(" )");

		return sql.toString();
	}


	/**
	 * 後者優先処理（各アップロード単位）
	 * ※複数回アップロードされている商品は、最終アップロード分を採用する
	 * @throws
	 */
	private String getKosyaYusenTaisyoSyohin1() throws Exception {

		StringBuffer sql = new StringBuffer();;

		sql.append("INSERT ");
		sql.append("  INTO WK_MB38_SYOHIN ");
		sql.append("SELECT TR_BUNRUI1_CD, ");
		sql.append("       TR_SYOHIN_CD, ");
		sql.append("       TO_NUMBER(SUBSTR(MAX(KEY), 15, 5)) AS UKETSUKE_NO ");
		sql.append("  FROM ( ");
		sql.append("           SELECT TR.TR_BUNRUI1_CD, ");
		sql.append("                  TR.TR_SYOHIN_CD, ");
		sql.append("                  TR.UPDATE_TS || TO_CHAR(TR.UKETSUKE_NO, 'FM00000') AS KEY ");
		sql.append("             FROM TR_SYOHIN TR ");
		sql.append("            WHERE TR.MST_MAINTE_FG           = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		sql.append("              AND LENGTH(TRIM(TR.TR_SYOHIN_CD)) > 4 ");

		sql.append("           UNION ALL ");

		sql.append("           SELECT TR.TR_BUNRUI1_CD, ");
		sql.append("                  TR.TR_SYOHIN_CD, ");
		sql.append("                  TR.UPDATE_TS || TO_CHAR(TR.UKETSUKE_NO, 'FM00000') AS KEY ");
		sql.append("             FROM TR_GIFT_SYOHIN TR ");
		sql.append("            WHERE TR.MST_MAINTE_FG           = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		sql.append("              AND LENGTH(TRIM(TR.TR_SYOHIN_CD)) > 4 ");

		sql.append("           UNION ALL ");

		sql.append("           SELECT TR.TR_BUNRUI1_CD, ");
		sql.append("                  TR.TR_SYOHIN_CD, ");
		sql.append("                  TR.UPDATE_TS || TO_CHAR(TR.UKETSUKE_NO, 'FM00000') AS KEY ");
		sql.append("             FROM TR_KEIRYOKI TR ");
		sql.append("            WHERE TR.MST_MAINTE_FG           = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		sql.append("              AND LENGTH(TRIM(TR.TR_SYOHIN_CD)) > 4 ");

//		sql.append("           UNION ALL ");
//
//		sql.append("           SELECT DISTINCT ");
//		sql.append("                  TR.TR_BUNRUI1_CD, ");
//		sql.append("                  TR.TR_SYOHIN_CD, ");
//		sql.append("                  TR.UPDATE_TS || TO_CHAR(TR.UKETSUKE_NO, 'FM00000') AS KEY ");
//		sql.append("             FROM TR_TENSYOHIN_REIGAI TR ");
//		sql.append("            WHERE TR.MST_MAINTE_FG           = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
//		sql.append("              AND LENGTH(TRIM(TR.TR_SYOHIN_CD)) > 4 ");
		sql.append("       ) TBL");
		sql.append(" GROUP BY ");
		sql.append("       TR_BUNRUI1_CD, ");
		sql.append("       TR_SYOHIN_CD ");

		return sql.toString();
	}

	/**
	 * 後者優先処理（各シート内）
	 * ※複数行登録されている場合は、最終行を採用する
	 * @throws
	 */
	private String getKosyaYusenTaisyoSyohin2(String strTableName, String strSheetSyubetsu) throws Exception {

		StringBuffer sql = new StringBuffer();;

		sql.append("INSERT ");
		sql.append("  INTO WK_MB38_TAISYO ");
		sql.append("SELECT TR.TORIKOMI_DT, ");
		sql.append("  	   TR.EXCEL_FILE_SYUBETSU, ");
		sql.append("  	   TR.UKETSUKE_NO, ");
		sql.append("  	   MAX(TR.UKETSUKE_SEQ) AS UKETSUKE_SEQ, ");			// 作成元行№とすべき？？
		sql.append("  	   TR.TR_BUNRUI1_CD, ");
		sql.append("  	   TR.TR_SYOHIN_CD, ");
		sql.append("  	   '" + strSheetSyubetsu + "' AS SHEET_SYUBETSU ");
		sql.append("  FROM " + strTableName + " TR ");
		sql.append(" INNER JOIN ");
		sql.append("       WK_MB38_SYOHIN WK ");
		sql.append("    ON WK.TR_BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
		sql.append("   AND WK.TR_SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("   AND WK.UKETSUKE_NO   = TR.UKETSUKE_NO ");
		sql.append(" WHERE TR.MST_MAINTE_FG = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		sql.append(" GROUP BY ");
		sql.append("       TR.TORIKOMI_DT, ");
		sql.append("  	   TR.EXCEL_FILE_SYUBETSU, ");
		sql.append("  	   TR.UKETSUKE_NO, ");
		sql.append("  	   TR.TR_BUNRUI1_CD, ");
		sql.append("  	   TR.TR_SYOHIN_CD ");

		return sql.toString();
	}


	/**
	 * 後者優先処理（処理対象外データ抽出）
	 * @throws
	 */
	private String getKosyaYusenTaisyogai(String strTableName, String strSheetSyubetsu) throws Exception {

		StringBuffer sql = new StringBuffer();;

		sql.append("INSERT ");
		sql.append("  INTO WK_MB38_TAISYOGAI ");
		sql.append("SELECT TR.TORIKOMI_DT, ");
		sql.append("  	   TR.EXCEL_FILE_SYUBETSU, ");
		sql.append("  	   TR.UKETSUKE_NO, ");
		sql.append("  	   TR.UKETSUKE_SEQ, ");
		sql.append("  	   TR.TR_BUNRUI1_CD, ");
		sql.append("  	   TR.TR_SYOHIN_CD, ");
		sql.append("  	   '" + strSheetSyubetsu + "' AS SHEET_SYUBETSU ");
		sql.append("  FROM " + strTableName + " TR ");
		sql.append("  LEFT JOIN ");
		sql.append("       WK_MB38_TAISYO WK ");
		sql.append("    ON WK.TORIKOMI_DT          = TR.TORIKOMI_DT ");
		sql.append("   AND WK.EXCEL_FILE_SYUBETSU  = TR.EXCEL_FILE_SYUBETSU ");
		sql.append("   AND WK.UKETSUKE_NO          = TR.UKETSUKE_NO ");
		sql.append("   AND WK.UKETSUKE_SEQ         = TR.UKETSUKE_SEQ ");
		sql.append("   AND WK.SHEET_SYUBETSU       = '" + strSheetSyubetsu + "' ");
		sql.append(" WHERE TR.MST_MAINTE_FG = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		sql.append("   AND LENGTH(TRIM(TR.TR_SYOHIN_CD)) > 4 ");
		sql.append("   AND WK.TORIKOMI_DT IS NULL ");

		return sql.toString();
	}


	private String getUpdKosyaYusenTaisyogai(String strTableName, String strSheetSyubetsu) throws Exception {

		StringBuffer sql = new StringBuffer();;

	//  2013.05.06 [MSTM00004] VIEW UPDATE対応(S)
		sql.append("UPDATE ");
		sql.append("	" + strTableName + " TR ");
		sql.append("SET  MST_MAINTE_FG  = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "' ");
		sql.append("    ,UPDATE_USER_ID = '" + batchID + "' ");
		sql.append("WHERE ");
		sql.append("    TR.MST_MAINTE_FG = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		sql.append("AND	EXISTS ");
		sql.append("		( ");
		sql.append("		SELECT '' ");
		sql.append("		FROM  ");
		sql.append("			WK_MB38_TAISYOGAI WK 									");
		sql.append("		WHERE WK.SHEET_SYUBETSU       = '" + strSheetSyubetsu + "' 	");
		sql.append("        AND   TR.TORIKOMI_DT          = WK.TORIKOMI_DT 				");
		sql.append("        AND   TR.EXCEL_FILE_SYUBETSU  = WK.EXCEL_FILE_SYUBETSU 		");
		sql.append("        AND   TR.UKETSUKE_NO          = WK.UKETSUKE_NO 				");
		sql.append("        AND   TR.UKETSUKE_SEQ         = WK.UKETSUKE_SEQ 			");
		sql.append("		) ");

//		sql.append("UPDATE ( ");
//		sql.append("    SELECT /*+ BYPASS_UJVC */ ");
//		sql.append("  	       TR.MST_MAINTE_FG, ");
//		sql.append("  	       TR.UPDATE_TS, ");
//		sql.append("  	       TR.UPDATE_USER_ID ");
//		sql.append("      FROM WK_MB38_TAISYOGAI WK ");
//		sql.append("     INNER JOIN ");
//		sql.append("           " + strTableName + " TR ");
//		sql.append("        ON TR.TORIKOMI_DT          = WK.TORIKOMI_DT ");
//		sql.append("       AND TR.EXCEL_FILE_SYUBETSU  = WK.EXCEL_FILE_SYUBETSU ");
//		sql.append("       AND TR.UKETSUKE_NO          = WK.UKETSUKE_NO ");
//		sql.append("       AND TR.UKETSUKE_SEQ         = WK.UKETSUKE_SEQ ");
//		sql.append("       AND TR.MST_MAINTE_FG = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
//		sql.append("     WHERE WK.SHEET_SYUBETSU = '" + strSheetSyubetsu + "' ");
//		sql.append(")");
//		sql.append("SET MST_MAINTE_FG  = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "',");
//		sql.append("    UPDATE_USER_ID = '" + batchID + "' ");

	//  2013.05.06 [MSTM00004] VIEW UPDATE対応(E)

		return sql.toString();
	}

	/**
	 * 後者優先による登録対象外のデータのメッセージ作成SQL
	 * @throws
	 */
	private String getKosyaYusenMessageSQL(Map map)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("INSERT INTO 				");
		str.append(" TR_MESSAGE 				");
		str.append("(SELECT 					");
		str.append("   TTS.TORIKOMI_DT,			"); 	//取込日
		str.append("   TTS.EXCEL_FILE_SYUBETSU,	"); 	//EXCELファイル種別
		str.append("   TTS.UKETSUKE_NO,			"); 	//受付ファイルNo.
		str.append("   NVL(TTS.UKETSUKE_SEQ,0),	"); 	//受付SEQNo.
		str.append("   SHEET_SYUBETSU, 			"); 	//シート種別
		str.append("   '0003',					"); 	//結果メッセージコード
		str.append("'" + map.get("0003") + "',"); 		//結果メッセージ
		str.append("   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   TOP.BY_NO, 				");
		str.append("   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   TOP.BY_NO 				");
		str.append(" FROM 														");
		str.append("   TR_TOROKU_SYONIN TOP RIGHT OUTER JOIN 					");
		str.append(" WK_MB38_TAISYOGAI TTS										");
		str.append("   ON 	TOP.TORIKOMI_DT 		= TTS.TORIKOMI_DT 			");
		str.append("   AND 	TOP.EXCEL_FILE_SYUBETSU = TTS.EXCEL_FILE_SYUBETSU 	");
		str.append("   AND 	TOP.UKETSUKE_NO 		= TTS.UKETSUKE_NO 			");
		str.append(")");

		return str.toString();
	}

	/**
	 * ユーザーログとバッチログにログを出力します。
	 *
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message){
		String jobId = userLog.getJobId();

		switch(level){
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}

	/**
	 * エラーをログファイルに出力します。
	 * ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 *
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました");
		} else {
			userLog.error("エラーが発生しました");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}
}
