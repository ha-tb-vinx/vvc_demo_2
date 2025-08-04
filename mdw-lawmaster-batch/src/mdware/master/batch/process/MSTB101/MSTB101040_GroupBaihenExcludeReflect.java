package mdware.master.batch.process.MSTB101;

import java.sql.SQLException;
import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB101040_GroupBaihenExcludeReflect.java クラス</p>
 * <p>説明　: WK_グループ売変除外品ASNを作成して、WK_PLU店別商品にマージする</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2016.09.02) 新規作成 #1566対応 VINX t.han
 * @version 1.01 (2016.09.09) #1921対応 VINX t.han
 * @version 1.02 (2016.11.28) #2839対応 VINX S.Takayama
 * @version 1.03 (2016.12.09) #3049対応 VINX t.han
 * @version 1.04 (2017.02.09) #3765対応 T.Han FIVImart対応
 * @version 1.05 (2017.02.21) #3686対応 T.Yajima FIVImart対応
 * @version 1.06 (2017.02.24) #4064対応 T.Yajima FIVImart対応
 * @version 1.07 (2017.03.29) #4417対応 M.Son FIVImart対応
 * @version 1.08 (2017.04.19) #4705対応 T.Han FIVImart対応
 * @version 1.09 (2020.07.13) KHAI.NN #6167 MKV対応
 * @Version 1.10 (2024.01.16) DUY.HM #15277 MKH対応
 */
public class MSTB101040_GroupBaihenExcludeReflect {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK_GROUPBAIHEN_EXCLUDE_ASN = "WK_GROUPBAIHEN_EXCLUDE_ASN"; // WK_グループ売変除外品ASN

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB101040_GroupBaihenExcludeReflect(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB101040_GroupBaihenExcludeReflect() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try {

			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();
			yokuBatchDt = DateChanger.addDate(batchDt, 1);
			
			// WK_グループ売変除外品ASN のTRUNCATE
			writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_GROUPBAIHEN_EXCLUDE_ASN);
			writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN を削除処理を終了します。");

			// WK_グループ売変除外品ASN の登録
			writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN 登録処理を開始します。");
			iRec = db.executeUpdate(getWkGroupBaihenExcludeASNInsertSql());
			writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN 登録処理を終了します。");

			db.commit();

			// WK_PLU店別商品マージ処理
			writeLog(Level.INFO_INT, "WK_PLU店別商品マージ処理を開始します。");
			iRec = db.executeUpdate(getWkTecPluMargeSql());
			writeLog(Level.INFO_INT, "WK_PLU店別商品 を" + iRec + "件マージしました。");
			writeLog(Level.INFO_INT, "WK_PLU店別商品マージ処理を終了します。");

			db.commit();

			writeLog(Level.INFO_INT, "処理を終了します。");

			//SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

			//その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

			//SQL終了処理
		} finally {
			if (closeDb || db != null) {
				db.close();
			}
		}

	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * WK_グループ売変除外品ASNを作成するSQLを取得する
	 * 
	 * @return WK_GROUPBAIHEN_EXCLUDE_ASN登録SQL
	 */
	private String getWkGroupBaihenExcludeASNInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_GROUPBAIHEN_EXCLUDE_ASN ");
		sql.append("	( ");
		sql.append("	 SYOHIN_CD ");
		sql.append("	,TENPO_CD ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 DGEA.SYOHIN_CD ");
		sql.append("	,DHTT.TENPO_CD ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	DT_GROUPBAIHEN_EXCLUDE_ASN DGEA ");
		sql.append("	INNER JOIN ");
		sql.append("		DT_THEME DT ");
		sql.append("	ON ");
		sql.append("		DGEA.THEME_CD = DT.THEME_CD ");
		sql.append("	LEFT JOIN ");
		sql.append("		DT_HANSOKU_TAISYO_TENPO DHTT ");
		sql.append("	ON ");
		sql.append("		DGEA.THEME_CD = DHTT.THEME_CD ");
// 2017.04.19 T.Han #4705対応 (S)
		sql.append("	INNER JOIN ");
		sql.append("		WK_TEC_PLU_SYOHIN WTPS ");
		sql.append("	ON ");
		sql.append("		WTPS.SYOHIN_CD = DGEA.SYOHIN_CD AND ");
		sql.append("		WTPS.BUNRUI1_CD = DT.BUNRUI1_CD ");
		sql.append("	INNER JOIN ");
		sql.append("		WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
		sql.append("	ON ");
		sql.append("		WTPSR.SYOHIN_CD = DGEA.SYOHIN_CD AND ");
		sql.append("		WTPSR.BUNRUI1_CD = DT.BUNRUI1_CD AND ");
		sql.append("		WTPSR.TENPO_CD = DHTT.TENPO_CD ");
// 2017.04.19 T.Han #4705対応 (E)
		sql.append("WHERE  ");
		// #4064 MSTB101 2017.02.24 T.Yajima Add (S)
		sql.append("    DT.DELETE_FG  = '0'  AND ");
		// #4064 MSTB101 2017.02.24 T.Yajima Add (E)
		sql.append("	DT.HANBAI_START_DT	<= '" + yokuBatchDt + "'  AND ");
		sql.append("	DT.HANBAI_END_DT	>= '" + yokuBatchDt + "'  ");
		sql.append("GROUP BY ");
		sql.append("	DGEA.SYOHIN_CD  ");
		sql.append("   ,DHTT.TENPO_CD ");

		return sql.toString();
	}

	/**
	 * WK_TEC_PLUマージするSQLを取得する
	 *
	 * @return WK_TEC_PLUマージSQL
	 */
	private String getWkTecPluMargeSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("MERGE INTO ");
		sql.append("	WK_TEC_PLU WTP ");
		sql.append("	USING ");
		sql.append("		( ");
		// #3686 MSTB101 2017.02.14 T.Yajima Del (S)
//		sql.append("		SELECT ");
//		sql.append("			 WTPS.BUNRUI1_CD ");
//		sql.append("			,WGEA.SYOHIN_CD ");
//		sql.append("			,WTPS.OLD_SYOHIN_CD ");
//		sql.append("			,WGEA.TENPO_CD ");
//		sql.append("			,WTPS.GENTANKA_VL ");
//		sql.append("			,WTPS.BAITANKA_VL ");
//		sql.append("			,WTPS.SIIRESAKI_CD ");
//		sql.append("			,WTPS.PLU_SEND_DT ");
//		sql.append("			,WTPS.BAIKA_HAISHIN_FG ");
//		sql.append("			,WTPS.BUNRUI5_CD ");
//		sql.append("			,WTPS.REC_HINMEI_KANJI_NA ");
//		sql.append("			,WTPS.REC_HINMEI_KANA_NA ");
//		sql.append("			,WTPS.KIKAKU_KANJI_NA ");
//		sql.append("			,WTPS.MAKER_KIBO_KAKAKU_VL ");
//		sql.append("			,WTPS.ZEI_KB ");
//		sql.append("			,'00' AS ERR_KB ");
//		sql.append("			,WTPS.BUNRUI2_CD ");
//		sql.append("			,WTPS.TEIKAN_FG ");
//		sql.append("			,WTPS.ZEI_RT ");
//		sql.append("			,WTPS.BUNRUI5_CD AS SEASON_ID ");
//		sql.append("			,WTPS.SYOHI_KIGEN_DT ");
//		sql.append("			,WTPS.CARD_FG ");
//		sql.append("			,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
//		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
//		sql.append("			,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
//		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
//		sql.append("			,WTPS.HANBAI_TANI ");
//		sql.append("			,WTPS.KEIRYOKI_NM ");
//		// 2016/09/09 VINX t.han #1921対応（S)
//		sql.append("			,WTPS.PLU_HANEI_TIME ");
//		sql.append("			,WTPS.SYOHI_KIGEN_HYOJI_PATTER ");
//		sql.append("			,WTPS.LABEL_SEIBUN ");
//		sql.append("			,WTPS.LABEL_HOKAN_HOHO ");
//		sql.append("			,WTPS.LABEL_TUKAIKATA ");
//		// 2016/09/09 VINX t.han #1921対応（E)
//		// #2839 MSTB101 2016.11.24 S.Takayama (S)
//		sql.append("			,TRT.GYOTAI_KB ");
//		// #2839 MSTB101 2016.11.24 S.Takayama (E)
//	    // 2016/12/09 VINX t.han #3049対応（S)
//		sql.append("			,WTPS.LABEL_COUNTRY_NA ");
//	    // 2016/12/09 VINX t.han #3049対応（E)
//	    // 2017/02/09 T.Han #3765対応（S)
//		sql.append("			,WTPS.HANBAI_TANI_EN ");
//	    // 2017/02/09 T.Han #3765対応（E)
//		sql.append("		FROM ");
//		sql.append("			WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
//		sql.append("			INNER JOIN ");
//		sql.append("				WK_TEC_PLU_SYOHIN WTPS ");
//		sql.append("			ON ");
//		sql.append("				WGEA.SYOHIN_CD = WTPS.SYOHIN_CD ");
//		// #2839 MSTB101 2016.11.24 S.Takayama (S)
//		sql.append("			INNER JOIN ");
//		sql.append("				TMP_R_TENPO TRT ");
//		sql.append("			ON ");
//		sql.append("				WGEA.TENPO_CD = TRT.TENPO_CD ");
//		// #2839 MSTB101 2016.11.24 S.Takayama (E)
//		sql.append("		WHERE NOT EXISTS ");
//		sql.append("			( SELECT * ");
//		sql.append("			FROM ");
//		sql.append("				WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
//		sql.append("			WHERE  ");
//		sql.append("				WGEA.SYOHIN_CD = WTPSR.SYOHIN_CD ");
//		sql.append("				AND WGEA.TENPO_CD = WTPSR.TENPO_CD ) ");
//		sql.append("		AND NOT EXISTS ");
//		sql.append("			( SELECT * ");
//		sql.append("			FROM ");
//		sql.append("				BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
//		sql.append("			WHERE  ");
//		sql.append("				WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
//		sql.append("				AND WGEA.TENPO_CD = BGEA.TENPO_CD ) ");
//		sql.append("		UNION ALL ");
		// #3686 MSTB101 2017.02.14 T.Yajima Del (E)
		sql.append("		SELECT ");
		sql.append("			 WTPSR.BUNRUI1_CD ");
		sql.append("			,WGEA.SYOHIN_CD ");
		sql.append("			,WTPS.OLD_SYOHIN_CD ");
		sql.append("			,WGEA.TENPO_CD ");
		// #3686対応 2017/02/13 T.Yajima Add(S)
		//sql.append("			,WTPSR.GENTANKA_VL ");
		//sql.append("			,WTPSR.BAITANKA_VL ");
		//sql.append("			,WTPSR.SIIRESAKI_CD ");
		//sql.append("			,WTPSR.PLU_SEND_DT ");
        sql.append("      		, NVL(WTPSR.GENTANKA_VL, WTPS.GENTANKA_VL) AS GENTANKA_VL ");
        sql.append("      		, NVL(WTPSR.BAITANKA_VL, WTPS.BAITANKA_VL) AS BAITANKA_VL ");
        sql.append("      		, NVL(WTPSR.SIIRESAKI_CD, WTPS.SIIRESAKI_CD) AS SIIRESAKI_CD ");
        sql.append("      		, NVL(WTPSR.PLU_SEND_DT, WTPS.PLU_SEND_DT) AS PLU_SEND_DT ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("			,WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("			,WTPS.BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (S)
		sql.append("			,WTPS.HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("			,WTPS.MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("			,WTPS.REC_HINMEI_KANJI_NA ");
		sql.append("			,WTPS.REC_HINMEI_KANA_NA ");
		sql.append("			,WTPS.KIKAKU_KANJI_NA ");
		sql.append("			,WTPS.MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,WTPS.ZEI_KB ");
		sql.append("			,'00' AS ERR_KB ");
		sql.append("			,WTPS.BUNRUI2_CD ");
		sql.append("			,WTPS.TEIKAN_FG ");
		sql.append("			,WTPS.ZEI_RT ");
		sql.append("			,WTPS.BUNRUI5_CD AS SEASON_ID ");
		sql.append("			,WTPS.SYOHI_KIGEN_DT ");
		sql.append("			,WTPS.CARD_FG ");
		sql.append("			,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("			,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("			,WTPS.HANBAI_TANI ");
		sql.append("			,WTPS.KEIRYOKI_NM ");
		// 2016/09/09 VINX t.han #1921対応（S)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		//sql.append("			,WTPSR.PLU_HANEI_TIME ");
		sql.append("      		, NVL(WTPSR.PLU_HANEI_TIME, WTPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("			,WTPS.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("			,WTPS.LABEL_SEIBUN ");
		sql.append("			,WTPS.LABEL_HOKAN_HOHO ");
		sql.append("			,WTPS.LABEL_TUKAIKATA ");
		// 2016/09/09 VINX t.han #1921対応（E)
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("			,WTPS.LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,WTPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("            ,GREATEST（WTPS.DELETE_FG ，WTPSR.DELETE_FG）AS DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E) 
		sql.append("		FROM ");
		sql.append("			WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
		sql.append("			INNER JOIN ");
		sql.append("				WK_TEC_PLU_SYOHIN WTPS ");
		sql.append("			ON ");
		sql.append("				WGEA.SYOHIN_CD = WTPS.SYOHIN_CD ");
		sql.append("			INNER JOIN ");
		sql.append("				WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
		sql.append("			ON ");
		sql.append("				WGEA.SYOHIN_CD = WTPSR.SYOHIN_CD ");
		sql.append("				AND WGEA.TENPO_CD = WTPSR.TENPO_CD ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("			INNER JOIN ");
		sql.append("				TMP_R_TENPO TRT ");
		sql.append("			ON ");
		sql.append("				WGEA.TENPO_CD = TRT.TENPO_CD ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
		sql.append("		WHERE NOT EXISTS ");
		sql.append("			( SELECT * ");
		sql.append("			FROM ");
		sql.append("				BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
		sql.append("			WHERE  ");
		sql.append("				WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
		sql.append("				AND WGEA.TENPO_CD = BGEA.TENPO_CD ) ");
		// #4417 Add 2017.03.29 M.Son (S)
		sql.append("			OR EXISTS ");
		sql.append("			( SELECT * ");
		sql.append("			FROM ");
		sql.append("				WK_TEC_PLU WTP1 ");
		sql.append("			WHERE  ");
		sql.append("				WGEA.SYOHIN_CD = WTP1.SYOHIN_CD ");
		sql.append("				AND WGEA.TENPO_CD = WTP1.TENPO_CD ) ");
		// #4417 Add 2017.03.29 M.Son (E)
//		sql.append("		UNION ALL ");
//		sql.append("		SELECT ");
//		sql.append("			 WTPS.BUNRUI1_CD ");
//		sql.append("			,BGEA.SYOHIN_CD ");
//		sql.append("			,WTPS.OLD_SYOHIN_CD ");
//		sql.append("			,BGEA.TENPO_CD ");
//		sql.append("			,WTPS.GENTANKA_VL ");
//		sql.append("			,WTPS.BAITANKA_VL ");
//		sql.append("			,WTPS.SIIRESAKI_CD ");
//		sql.append("			,WTPS.PLU_SEND_DT ");
//		sql.append("			,WTPS.BAIKA_HAISHIN_FG ");
//		sql.append("			,WTPS.BUNRUI5_CD ");
//		sql.append("			,WTPS.REC_HINMEI_KANJI_NA ");
//		sql.append("			,WTPS.REC_HINMEI_KANA_NA ");
//		sql.append("			,WTPS.KIKAKU_KANJI_NA ");
//		sql.append("			,WTPS.MAKER_KIBO_KAKAKU_VL ");
//		sql.append("			,WTPS.ZEI_KB ");
//		sql.append("			,'00' AS ERR_KB ");
//		sql.append("			,WTPS.BUNRUI2_CD ");
//		sql.append("			,WTPS.TEIKAN_FG ");
//		sql.append("			,WTPS.ZEI_RT ");
//		sql.append("			,NULL AS SEASON_ID ");
//		sql.append("			,WTPS.SYOHI_KIGEN_DT ");
//		sql.append("			,WTPS.CARD_FG ");
//		sql.append("			,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
//		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
//		sql.append("			,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
//		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
//		sql.append("			,WTPS.HANBAI_TANI ");
//		sql.append("			,WTPS.KEIRYOKI_NM ");
//		// 2016/09/09 VINX t.han #1921対応（S)
//		sql.append("			,WTPS.PLU_HANEI_TIME ");
//		sql.append("			,WTPS.SYOHI_KIGEN_HYOJI_PATTER ");
//		sql.append("			,WTPS.LABEL_SEIBUN ");
//		sql.append("			,WTPS.LABEL_HOKAN_HOHO ");
//		sql.append("			,WTPS.LABEL_TUKAIKATA ");
//		// 2016/09/09 VINX t.han #1921対応（E)
//		// #2839 MSTB101 2016.11.24 S.Takayama (S)
//		sql.append("			,TRT.GYOTAI_KB ");
//		// #2839 MSTB101 2016.11.24 S.Takayama (E)
//	    // 2016/12/09 VINX t.han #3049対応（S)
//		sql.append("			,WTPS.LABEL_COUNTRY_NA ");
//	    // 2016/12/09 VINX t.han #3049対応（E)
//	    // 2017/02/09 T.Han #3765対応（S)
//		sql.append("			,WTPS.HANBAI_TANI_EN ");
//	    // 2017/02/09 T.Han #3765対応（E)
//		sql.append("		FROM ");
//		sql.append("			BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
//		sql.append("			INNER JOIN ");
//		sql.append("				WK_TEC_PLU_SYOHIN WTPS ");
//		sql.append("			ON ");
//		sql.append("				BGEA.SYOHIN_CD = WTPS.SYOHIN_CD ");
//		// #2839 MSTB101 2016.11.24 S.Takayama (S)
//		sql.append("			INNER JOIN ");
//		sql.append("				TMP_R_TENPO TRT ");
//		sql.append("			ON ");
//		sql.append("				BGEA.TENPO_CD = TRT.TENPO_CD ");
//		// #2839 MSTB101 2016.11.24 S.Takayama (E)
//		sql.append("		WHERE NOT EXISTS ");
//		sql.append("			( SELECT * ");
//		sql.append("			FROM ");
//		sql.append("				WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
//		sql.append("			WHERE  ");
//		sql.append("				BGEA.SYOHIN_CD = WTPSR.SYOHIN_CD ");
//		sql.append("				AND BGEA.TENPO_CD = WTPSR.TENPO_CD ) ");
//		sql.append("		AND NOT EXISTS ");
//		sql.append("			( SELECT * ");
//		sql.append("			FROM ");
//		sql.append("				WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
//		sql.append("			WHERE  ");
//		sql.append("				WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
//		sql.append("				AND WGEA.TENPO_CD = BGEA.TENPO_CD ) ");
		// #3686 MSTB101 2017.02.14 T.Yajima Del (E)
		sql.append("		UNION ALL ");
		sql.append("		SELECT ");
		sql.append("			 WTPSR.BUNRUI1_CD ");
		sql.append("			,BGEA.SYOHIN_CD ");
		sql.append("			,WTPS.OLD_SYOHIN_CD ");
		sql.append("			,BGEA.TENPO_CD ");
		// #3686対応 2017/02/13 T.Yajima Add(S)
		//sql.append("			,WTPSR.GENTANKA_VL ");
		//sql.append("			,WTPSR.BAITANKA_VL ");
		//sql.append("			,WTPSR.SIIRESAKI_CD ");
		//sql.append("			,WTPSR.PLU_SEND_DT ");
        sql.append("      		, NVL(WTPSR.GENTANKA_VL, WTPS.GENTANKA_VL) AS GENTANKA_VL ");
        sql.append("      		, NVL(WTPSR.BAITANKA_VL, WTPS.BAITANKA_VL) AS BAITANKA_VL ");
        sql.append("      		, NVL(WTPSR.SIIRESAKI_CD, WTPS.SIIRESAKI_CD) AS SIIRESAKI_CD ");
        sql.append("      		, NVL(WTPSR.PLU_SEND_DT, WTPS.PLU_SEND_DT) AS PLU_SEND_DT ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("			,WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("			,WTPS.BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (S)
		sql.append("			,WTPS.HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("			,WTPS.MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("			,WTPS.REC_HINMEI_KANJI_NA ");
		sql.append("			,WTPS.REC_HINMEI_KANA_NA ");
		sql.append("			,WTPS.KIKAKU_KANJI_NA ");
		sql.append("			,WTPS.MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,WTPS.ZEI_KB ");
		sql.append("			,'00' AS ERR_KB ");
		sql.append("			,WTPS.BUNRUI2_CD ");
		sql.append("			,WTPS.TEIKAN_FG ");
		sql.append("			,WTPS.ZEI_RT ");
		sql.append("			,NULL AS SEASON_ID ");
		sql.append("			,WTPS.SYOHI_KIGEN_DT ");
		sql.append("			,WTPS.CARD_FG ");
		sql.append("			,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("			,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("			,WTPS.HANBAI_TANI ");
		sql.append("			,WTPS.KEIRYOKI_NM ");
		// 2016/09/09 VINX t.han #1921対応（S)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		//sql.append("			,WTPSR.PLU_HANEI_TIME ");
        sql.append("      		, NVL(WTPSR.PLU_HANEI_TIME, WTPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("			,WTPS.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("			,WTPS.LABEL_SEIBUN ");
		sql.append("			,WTPS.LABEL_HOKAN_HOHO ");
		sql.append("			,WTPS.LABEL_TUKAIKATA ");
		// 2016/09/09 VINX t.han #1921対応（E)
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("			,WTPS.LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,WTPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("            ,GREATEST（WTPS.DELETE_FG ，WTPSR.DELETE_FG）AS DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("		FROM ");
		sql.append("			BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
		sql.append("			INNER JOIN ");
		sql.append("				WK_TEC_PLU_SYOHIN WTPS ");
		sql.append("			ON ");
		sql.append("				BGEA.SYOHIN_CD = WTPS.SYOHIN_CD ");
		sql.append("			INNER JOIN ");
		sql.append("				WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
		sql.append("			ON ");
		sql.append("				BGEA.SYOHIN_CD = WTPSR.SYOHIN_CD ");
		sql.append("				AND BGEA.TENPO_CD = WTPSR.TENPO_CD ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("			INNER JOIN ");
		sql.append("				TMP_R_TENPO TRT ");
		sql.append("			ON ");
		sql.append("				BGEA.TENPO_CD = TRT.TENPO_CD ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
		sql.append("		WHERE NOT EXISTS ");
		sql.append("			( SELECT * ");
		sql.append("			FROM ");
		sql.append("				WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
		sql.append("			WHERE  ");
		sql.append("				WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
		sql.append("				AND WGEA.TENPO_CD = BGEA.TENPO_CD ) ");
		sql.append("		) ABCD ");
		sql.append("		ON ");
		sql.append("			( ");
		sql.append("			ABCD.BUNRUI1_CD	= WTP.BUNRUI1_CD	AND ");
		sql.append("			ABCD.SYOHIN_CD	= WTP.SYOHIN_CD	AND ");
		sql.append("			ABCD.TENPO_CD	= WTP.TENPO_CD ");
		sql.append("			) ");
		sql.append("	WHEN MATCHED THEN ");
		sql.append("		UPDATE ");
		sql.append("			SET ");
		sql.append("				 WTP.OLD_SYOHIN_CD			= ABCD.OLD_SYOHIN_CD ");
		sql.append("				,WTP.GENTANKA_VL			= ABCD.GENTANKA_VL ");
		sql.append("				,WTP.BAITANKA_VL			= ABCD.BAITANKA_VL ");
		sql.append("				,WTP.SIIRESAKI_CD			= ABCD.SIIRESAKI_CD ");
		sql.append("				,WTP.PLU_SEND_DT			= ABCD.PLU_SEND_DT ");
		sql.append("				,WTP.BAIKA_HAISHIN_FG		= ABCD.BAIKA_HAISHIN_FG ");
		sql.append("				,WTP.BUNRUI5_CD				= ABCD.BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (S)
		sql.append("				,WTP.HINMEI_KANJI_NA		= ABCD.HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("				,WTP.MAX_BUY_QT				= ABCD.MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("				,WTP.REC_HINMEI_KANJI_NA	= ABCD.REC_HINMEI_KANJI_NA ");
		sql.append("				,WTP.REC_HINMEI_KANA_NA		= ABCD.REC_HINMEI_KANA_NA ");
		sql.append("				,WTP.KIKAKU_KANJI_NA		= ABCD.KIKAKU_KANJI_NA ");
		sql.append("				,WTP.MAKER_KIBO_KAKAKU_VL	= ABCD.MAKER_KIBO_KAKAKU_VL ");
		sql.append("				,WTP.ZEI_KB					= ABCD.ZEI_KB ");
		sql.append("				,WTP.ERR_KB					= ABCD.ERR_KB ");
		sql.append("				,WTP.BUNRUI2_CD				= ABCD.BUNRUI2_CD ");
		sql.append("				,WTP.TEIKAN_FG				= ABCD.TEIKAN_FG ");
		sql.append("				,WTP.ZEI_RT					= ABCD.ZEI_RT ");
		sql.append("				,WTP.SEASON_ID				= ABCD.SEASON_ID ");
		sql.append("				,WTP.SYOHI_KIGEN_DT			= ABCD.SYOHI_KIGEN_DT ");
		sql.append("				,WTP.CARD_FG				= ABCD.CARD_FG ");
		sql.append("				,WTP.UPDATE_USER_ID			= '" + userLog.getJobId() + "' ");
		sql.append("				,WTP.UPDATE_TS				= '" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("				,WTP.HANBAI_TANI			= ABCD.HANBAI_TANI ");
		sql.append("				,WTP.KEIRYOKI_NM			= ABCD.KEIRYOKI_NM ");
		// 2016/09/09 VINX t.han #1921対応（S)
		sql.append("				,WTP.PLU_HANEI_TIME				= ABCD.PLU_HANEI_TIME ");
		sql.append("				,WTP.SYOHI_KIGEN_HYOJI_PATTER	= ABCD.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("				,WTP.LABEL_SEIBUN				= ABCD.LABEL_SEIBUN ");
		sql.append("				,WTP.LABEL_HOKAN_HOHO			= ABCD.LABEL_HOKAN_HOHO ");
		sql.append("				,WTP.LABEL_TUKAIKATA			= ABCD.LABEL_TUKAIKATA ");
		// 2016/09/09 VINX t.han #1921対応（E)
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("				,WTP.GYOTAI_KB			= ABCD.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("				,WTP.LABEL_COUNTRY_NA 	= ABCD.LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("				,WTP.HANBAI_TANI_EN 	= ABCD.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("            	,WTP.DELETE_FG	        = ABCD.DELETE_FG  ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("	WHEN NOT MATCHED THEN ");
		sql.append("				INSERT ");
		sql.append("					( ");
		sql.append("					 BUNRUI1_CD ");
		sql.append("					,SYOHIN_CD ");
		sql.append("					,OLD_SYOHIN_CD ");
		sql.append("					,TENPO_CD ");
		sql.append("					,GENTANKA_VL ");
		sql.append("					,BAITANKA_VL ");
		sql.append("					,SIIRESAKI_CD ");
		sql.append("					,PLU_SEND_DT ");
		sql.append("					,BAIKA_HAISHIN_FG ");
		sql.append("					,BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (S)
		sql.append("					,HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("					,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("					,REC_HINMEI_KANJI_NA ");
		sql.append("					,REC_HINMEI_KANA_NA ");
		sql.append("					,KIKAKU_KANJI_NA ");
		sql.append("					,MAKER_KIBO_KAKAKU_VL ");
		sql.append("					,ZEI_KB ");
		sql.append("					,ERR_KB ");
		sql.append("					,BUNRUI2_CD ");
		sql.append("					,TEIKAN_FG ");
		sql.append("					,ZEI_RT ");
		sql.append("					,SEASON_ID ");
		sql.append("					,SYOHI_KIGEN_DT ");
		sql.append("					,CARD_FG ");
		sql.append("					,INSERT_USER_ID ");
		sql.append("					,INSERT_TS ");
		sql.append("					,UPDATE_USER_ID ");
		sql.append("					,UPDATE_TS ");
		sql.append("					,HANBAI_TANI ");
		sql.append("					,KEIRYOKI_NM ");
		// 2016/09/09 VINX t.han #1921対応（S)
		sql.append("					,PLU_HANEI_TIME ");
		sql.append("					,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("					,LABEL_SEIBUN ");
		sql.append("					,LABEL_HOKAN_HOHO ");
		sql.append("					,LABEL_TUKAIKATA ");
		// 2016/09/09 VINX t.han #1921対応（E)
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("					,GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("					,LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("					,HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("            		, DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("					) ");
		sql.append("				VALUES ");
		sql.append("					( ");
		sql.append("					 ABCD.BUNRUI1_CD ");
		sql.append("					,ABCD.SYOHIN_CD ");
		sql.append("					,ABCD.OLD_SYOHIN_CD ");
		sql.append("					,ABCD.TENPO_CD ");
		sql.append("					,ABCD.GENTANKA_VL ");
		sql.append("					,ABCD.BAITANKA_VL ");
		sql.append("					,ABCD.SIIRESAKI_CD ");
		sql.append("					,ABCD.PLU_SEND_DT ");
		sql.append("					,ABCD.BAIKA_HAISHIN_FG ");
		sql.append("					,ABCD.BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (S)
		sql.append("					,ABCD.HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.13 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("					,ABCD.MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("					,ABCD.REC_HINMEI_KANJI_NA ");
		sql.append("					,ABCD.REC_HINMEI_KANA_NA ");
		sql.append("					,ABCD.KIKAKU_KANJI_NA ");
		sql.append("					,ABCD.MAKER_KIBO_KAKAKU_VL ");
		sql.append("					,ABCD.ZEI_KB ");
		sql.append("					,ABCD.ERR_KB ");
		sql.append("					,ABCD.BUNRUI2_CD ");
		sql.append("					,ABCD.TEIKAN_FG ");
		sql.append("					,ABCD.ZEI_RT ");
		sql.append("					,ABCD.SEASON_ID ");
		sql.append("					,ABCD.SYOHI_KIGEN_DT ");
		sql.append("					,ABCD.CARD_FG ");
		sql.append("					,'" + userLog.getJobId() + "' ");
		sql.append("					,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("					,'" + userLog.getJobId() + "' ");
		sql.append("					,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("					,ABCD.HANBAI_TANI ");
		sql.append("					,ABCD.KEIRYOKI_NM ");
		// 2016/09/09 VINX t.han #1921対応（S)
		sql.append("					,ABCD.PLU_HANEI_TIME ");
		sql.append("					,ABCD.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("					,ABCD.LABEL_SEIBUN ");
		sql.append("					,ABCD.LABEL_HOKAN_HOHO ");
		sql.append("					,ABCD.LABEL_TUKAIKATA ");
		// 2016/09/09 VINX t.han #1921対応（E)
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("					,ABCD.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("					,ABCD.LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("					,ABCD.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("             		 ,ABCD.DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("					) ");

		return sql.toString();
	}

/********** 共通処理 **********/

	/**
	 * ユーザーログとバッチログにログを出力します。
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
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}
