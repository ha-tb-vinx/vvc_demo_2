package mdware.master.batch.process.MSTB101;

import java.sql.SQLException;
import java.util.Map;
import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB101060_WkHamperKoseiCreate.java クラス</p>
 * <p>説明　: ハンパー構成商品として登録されている商品をPLU送信対象として、<br>
 *            WK_ハンパー構成を作成し、WK_PLU店別商品にマージする</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2016.12.15) #3232対応 T.Han 新規作成
 * @version 1.01 (2017.02.09) #3765対応 T.Han FIVImart対応
 * @version 1.02 (2017.02.13) #3686対応 T.Yajima FIVImart対応
 * @version 1.03 (2017.03.28) #4418対応 Li.Sheng FIVImart対応
 * @version 1.04 (2017.03.30) #4433対応 Li.Sheng FIVImart対応
 * @version 1.05 (2017.04.12) #4603対応 M.Akagi
 * @version 1.06 (2017.04.24) #4775対応 S.Takayama
 * @version 1.07 (2017.04.28) #4845対応 M.Son
 * @version 1.08 (2021.12.14) #6406 KHOI.ND 
 */
public class MSTB101060_WkHamperKoseiCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK_HAMPER_KOSEI = "WK_HAMPER_KOSEI"; // WK_ハンパー構成

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;

	/** ゼロ (定数) */
	private static final String CONST_ZERO = "0";

	// #3686 MSTB101 2017.02.14 T.Yajima Add (S)
	/** 店舗区分 */
	private String MASTER_IF_PLU_TENPO_KB = null; 
	// #3686 MSTB101 2017.02.14 T.Yajima Add (E)
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB101060_WkHamperKoseiCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB101060_WkHamperKoseiCreate() {
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
			
			// #3686 MSTB101 2017.02.14 T.Yajima Add (S)
			// 店舗区分セット
			Map tenpoMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_IF_TENPO_KB);  
			MASTER_IF_PLU_TENPO_KB = SqlSupportUtil.createInString(tenpoMap.keySet().toArray()); 
			// #3686 MSTB101 2017.02.14 T.Yajima Add (E)
			
			// WK_ハンパー構成 のTRUNCATE
			writeLog(Level.INFO_INT, "WK_ハンパー構成 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_HAMPER_KOSEI);
			writeLog(Level.INFO_INT, "WK_ハンパー構成 を削除処理を終了します。");

			// WK_ハンパー構成 の登録
			writeLog(Level.INFO_INT, "WK_ハンパー構成 登録処理を開始します。");
			iRec = db.executeUpdate(getWkHamperKoseiInsertSql());
			writeLog(Level.INFO_INT, "WK_ハンパー構成 を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_ハンパー構成 登録処理を終了します。");

			db.commit();

            // WK_PLU店別商品マージ
			writeLog(Level.INFO_INT, "WK_PLU店別商品マージ処理を開始します。");
			iRec = db.executeUpdate(getWkTecPluMergeSql());
			writeLog(Level.INFO_INT, "WK_PLU店別商品を" + iRec + "件マージしました。");
			writeLog(Level.INFO_INT, "WK_PLU店別商品マージ処理を終了します。");

			db.commit();

// #4775 Del 2017.04.24 S.Takayama (S)
// #4433 2017.03.30 Add Li.Sheng 対応（S)
//			// ハンパー構成商品ではなくなった商品をWK_PLU店別商品にマージする
//			writeLog(Level.INFO_INT, "ハンパー構成商品ではなくなった商品のWK_PLU店別商品マージ処理を開始します。");
//			iRec = db.executeUpdate(getWkTecPluMergeSql1());
//			writeLog(Level.INFO_INT, "WK_PLU店別商品を" + iRec + "件マージしました。");
//			writeLog(Level.INFO_INT, "ハンパー構成商品ではなくなった商品のWK_PLU店別商品マージ処理を終了します。");
//
//			db.commit();		
// #4433 2017.03.30 Add Li.Sheng 対応（E)
// #4775 Del 2017.04.24 S.Takayama (E)

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
// #4433 2017.03.30 Add Li.Sheng 対応（S)	
	 /**
     * ハンパー構成商品ではなくなった商品WK_PLU店別商品にマージするSQLを取得する
     *
     * @return WK_PLU店別商品にマージするSQL
     */
    private String getWkTecPluMergeSql1() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" MERGE INTO");
        sql.append(" 	WK_TEC_PLU WTP");
        sql.append(" 	USING");
        sql.append(" 		(");
        sql.append(" 			SELECT	NVL(WTPSR.BUNRUI1_CD, WTPS.BUNRUI1_CD) AS BUNRUI1_CD,");
        sql.append(" 					WHK.KOSEI_SYOHIN_CD AS SYOHIN_CD,");
        sql.append(" 					WTPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD,");
        sql.append(" 					WHK.TENPO_CD AS TENPO_CD,");
        sql.append(" 					NVL(WTPSR.GENTANKA_VL, WTPS.GENTANKA_VL) AS GENTANKA_VL,");
        sql.append(" 					NVL(WTPSR.BAITANKA_VL, WTPS.BAITANKA_VL) AS BAITANKA_VL,");
        sql.append(" 					NVL(WTPSR.SIIRESAKI_CD, WTPS.SIIRESAKI_CD) AS SIIRESAKI_CD,");
        sql.append(" 					NVL(WTPSR.PLU_SEND_DT, WTPS.PLU_SEND_DT) AS PLU_SEND_DT,");
		sql.append("					CASE ");
		sql.append("						WHEN WHK.BAIKA_HAISHIN_FG = '0' THEN '0' ");
		sql.append("						ELSE NVL(WTPSR.BAIKA_HAISHIN_FG, WTPS.BAIKA_HAISHIN_FG) ");
		sql.append("	 				END AS BAIKA_HAISHIN_FG, ");
		sql.append(" 					WTPS.BUNRUI5_CD AS BUNRUI5_CD,");
        sql.append(" 					WTPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA,");
        sql.append(" 					WTPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA,");
        sql.append(" 					WTPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA,");
        sql.append(" 					WTPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 					WTPS.ZEI_KB AS ZEI_KB,");
        sql.append("					'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB, ");
        sql.append(" 					WTPS.BUNRUI2_CD AS BUNRUI2_CD,");
        sql.append(" 					WTPS.TEIKAN_FG AS TEIKAN_FG,");
        sql.append(" 					WTPS.ZEI_RT AS ZEI_RT,");
		sql.append(" 					DECODE(WGEA.SYOHIN_CD,NULL,NULL,WTPS.BUNRUI5_CD) AS SEASON_ID,");
		sql.append(" 					WTPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT,");
        sql.append(" 					WTPS.CARD_FG AS CARD_FG,");
		sql.append("					'" + userLog.getJobId() + "' AS INSERT_USER_ID, ");
		sql.append("					'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS, ");
		sql.append("					'" + userLog.getJobId() + "' AS UPDATE_USER_ID, ");
		sql.append("					'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS, ");
        sql.append(" 					WTPS.HANBAI_TANI AS HANBAI_TANI,");
        sql.append(" 					WTPS.KEIRYOKI_NM AS KEIRYOKI_NM,");
        sql.append(" 					NVL(WTPSR.PLU_HANEI_TIME, WTPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME,");
        sql.append(" 					WTPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 					WTPS.LABEL_SEIBUN AS LABEL_SEIBUN,");
        sql.append(" 					WTPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO,");
        sql.append(" 					WTPS.LABEL_TUKAIKATA AS LABEL_TUKAIKATA,");
        sql.append(" 					TRT.GYOTAI_KB AS GYOTAI_KB,");
        sql.append(" 					WTPS.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");
		sql.append("					,WTPS.HANBAI_TANI_EN AS HANBAI_TANI_EN ");
		// 2017.04.12 M.Akagi #4603 (S)
		//sql.append("	 			 , CASE WHEN WHK.BAIKA_HAISHIN_FG = '0' THEN '0' ");
		//sql.append("	 			   ELSE GREATEST（WTPS.DELETE_FG，WTPSR.DELETE_FG）END AS DELETE_FG ");
		sql.append("	 				,GREATEST（WTPS.DELETE_FG，WTPSR.DELETE_FG） AS DELETE_FG ");
		// 2017.04.12 M.Akagi #4603 (E)
        sql.append(" 			FROM	(SELECT WHK1.KOSEI_SYOHIN_CD AS KOSEI_SYOHIN_CD, ");
        sql.append(" 				 WHK1.TENPO_CD AS TENPO_CD, ");
        sql.append(" 				 MIN(WHK1.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
        sql.append(" 			FROM	BK_HAMPER_KOSEI WHK1 ");
        sql.append(" 			GROUP BY WHK1.KOSEI_SYOHIN_CD,WHK1.TENPO_CD) WHK  "); 
        sql.append("					INNER JOIN ");
        sql.append("						WK_TEC_PLU_SYOHIN WTPS ");
        sql.append("					ON ");
        sql.append("						WHK.KOSEI_SYOHIN_CD = WTPS.SYOHIN_CD ");   
        sql.append("                    INNER JOIN ");
        sql.append("						WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
        sql.append("					ON ");
        sql.append("						WHK.KOSEI_SYOHIN_CD = WTPSR.SYOHIN_CD	AND ");
        sql.append("						WHK.TENPO_CD = WTPSR.TENPO_CD ");
        sql.append("					INNER JOIN ");
        sql.append("						TMP_R_TENPO TRT ");
        sql.append("					ON ");
        sql.append("						WHK.TENPO_CD = TRT.TENPO_CD ");
        sql.append("					LEFT JOIN WK_GROUPBAIHEN_EXCLUDE_ASN WGEA  ");
        sql.append("					ON WHK.KOSEI_SYOHIN_CD = WGEA.SYOHIN_CD ");
        sql.append("					AND WHK.TENPO_CD = WGEA.TENPO_CD ");     
        sql.append(" 			WHERE	 ");
		sql.append("				NOT EXISTS ");
		sql.append("						( ");
		sql.append("							SELECT ");
		sql.append("			 					* ");
		sql.append("							FROM ");
		sql.append("								( ");	
		sql.append("							SELECT BHK.KOSEI_SYOHIN_CD, ");
		sql.append("							BHK.TENPO_CD, ");
		sql.append("							MIN(BHK.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
		sql.append("							FROM WK_HAMPER_KOSEI BHK ");
		sql.append("							GROUP BY BHK.KOSEI_SYOHIN_CD,BHK.TENPO_CD ");
		sql.append("							) SUB1 ");
		sql.append("							WHERE ");
		sql.append("								WHK.KOSEI_SYOHIN_CD	= SUB1.KOSEI_SYOHIN_CD	AND ");
		sql.append("								WHK.TENPO_CD		= SUB1.TENPO_CD	 AND ");
		sql.append("								WHK.BAIKA_HAISHIN_FG		= SUB1.BAIKA_HAISHIN_FG	 ");
		sql.append("						) ");
        sql.append(" 		) MAINDATA ");
        sql.append(" 	ON");
        sql.append(" 	(");
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append(" 		MAINDATA.BUNRUI1_CD = WTP.BUNRUI1_CD AND");
        // #6620 DEL 2022.11.18 VU.TD (E)
        sql.append(" 		MAINDATA.SYOHIN_CD  = WTP.SYOHIN_CD AND");
        sql.append(" 		MAINDATA.TENPO_CD   = WTP.TENPO_CD");
        sql.append(" 	)");
        sql.append(" WHEN MATCHED THEN");
        sql.append(" 	UPDATE");
        sql.append(" 		SET");
        sql.append(" 			WTP.BAIKA_HAISHIN_FG = MAINDATA.BAIKA_HAISHIN_FG,");
        sql.append(" 			WTP.UPDATE_USER_ID = MAINDATA.UPDATE_USER_ID, ");
        sql.append(" 			WTP.UPDATE_TS = MAINDATA.UPDATE_TS ");
        sql.append(" WHEN NOT MATCHED THEN");
        sql.append(" 	INSERT");
        sql.append(" 		(");
        sql.append(" 			BUNRUI1_CD,");
        sql.append(" 			SYOHIN_CD,");
        sql.append(" 			OLD_SYOHIN_CD,");
        sql.append(" 			TENPO_CD,");
        sql.append(" 			GENTANKA_VL,");
        sql.append(" 			BAITANKA_VL,");
        sql.append(" 			SIIRESAKI_CD,");
        sql.append(" 			PLU_SEND_DT,");
        sql.append(" 			BAIKA_HAISHIN_FG,");
        sql.append(" 			BUNRUI5_CD,");
        sql.append(" 			REC_HINMEI_KANJI_NA,");
        sql.append(" 			REC_HINMEI_KANA_NA,");
        sql.append(" 			KIKAKU_KANJI_NA,");
        sql.append(" 			MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 			ZEI_KB,");
        sql.append(" 			ERR_KB,");
        sql.append(" 			BUNRUI2_CD,");
        sql.append(" 			TEIKAN_FG,");
        sql.append(" 			ZEI_RT,");
        sql.append(" 			SEASON_ID,");
        sql.append(" 			SYOHI_KIGEN_DT,");
        sql.append(" 			CARD_FG,");
        sql.append(" 			INSERT_USER_ID,");
        sql.append(" 			INSERT_TS,");
        sql.append(" 			UPDATE_USER_ID,");
        sql.append(" 			UPDATE_TS,");
        sql.append(" 			HANBAI_TANI,");
        sql.append(" 			KEIRYOKI_NM,");
        sql.append(" 			PLU_HANEI_TIME,");
        sql.append(" 			SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 			LABEL_SEIBUN,");
        sql.append(" 			LABEL_HOKAN_HOHO,");
        sql.append(" 			LABEL_TUKAIKATA,");
        sql.append(" 			GYOTAI_KB,");
        sql.append(" 			LABEL_COUNTRY_NA");
		sql.append("			,HANBAI_TANI_EN ");
		sql.append("	 		,  DELETE_FG ");
        sql.append(" 		)");
        sql.append(" 	VALUES");
        sql.append(" 		(");
        sql.append(" 			MAINDATA.BUNRUI1_CD,");
        sql.append(" 			MAINDATA.SYOHIN_CD,");
        sql.append(" 			MAINDATA.OLD_SYOHIN_CD,");
        sql.append(" 			MAINDATA.TENPO_CD,");
        sql.append(" 			MAINDATA.GENTANKA_VL,");
        sql.append(" 			MAINDATA.BAITANKA_VL,");
        sql.append(" 			MAINDATA.SIIRESAKI_CD,");
        sql.append(" 			MAINDATA.PLU_SEND_DT,");
        sql.append(" 			MAINDATA.BAIKA_HAISHIN_FG,");
        sql.append(" 			MAINDATA.BUNRUI5_CD,");
        sql.append(" 			MAINDATA.REC_HINMEI_KANJI_NA,");
        sql.append(" 			MAINDATA.REC_HINMEI_KANA_NA,");
        sql.append(" 			MAINDATA.KIKAKU_KANJI_NA,");
        sql.append(" 			MAINDATA.MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 			MAINDATA.ZEI_KB,");
        sql.append(" 			MAINDATA.ERR_KB,");
        sql.append(" 			MAINDATA.BUNRUI2_CD,");
        sql.append(" 			MAINDATA.TEIKAN_FG,");
        sql.append(" 			MAINDATA.ZEI_RT,");
        sql.append(" 			MAINDATA.SEASON_ID,");
        sql.append(" 			MAINDATA.SYOHI_KIGEN_DT,");
        sql.append(" 			MAINDATA.CARD_FG,");
        sql.append(" 			MAINDATA.INSERT_USER_ID,");
        sql.append(" 			MAINDATA.INSERT_TS,");
        sql.append(" 			MAINDATA.UPDATE_USER_ID,");
        sql.append(" 			MAINDATA.UPDATE_TS,");
        sql.append(" 			MAINDATA.HANBAI_TANI,");
        sql.append(" 			MAINDATA.KEIRYOKI_NM,");
        sql.append(" 			MAINDATA.PLU_HANEI_TIME,");
        sql.append(" 			MAINDATA.SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 			MAINDATA.LABEL_SEIBUN,");
        sql.append(" 			MAINDATA.LABEL_HOKAN_HOHO,");
        sql.append(" 			MAINDATA.LABEL_TUKAIKATA,");
        sql.append(" 			MAINDATA.GYOTAI_KB,");
        sql.append(" 			MAINDATA.LABEL_COUNTRY_NA");
		sql.append("			,MAINDATA.HANBAI_TANI_EN ");
		sql.append("	 		,MAINDATA.DELETE_FG ");
        sql.append(" 		)");

        return sql.toString();
    }
    
	/**
	 * WK_HAMPER_KOSEIを登録するSQLを取得する
	 * 
	 * @return WK_HAMPER_KOSEI登録SQL
	 */
	private String getWkHamperKoseiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_HAMPER_KOSEI ");
		sql.append("	( ");
		sql.append("	 HAMPER_SYOHIN_CD ");
		sql.append("	,KOSEI_SYOHIN_CD ");
		sql.append("	,HANBAI_START_DT ");
		sql.append("	,HANBAI_END_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,BAIKA_HAISHIN_FG ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 RHK.HAMPER_SYOHIN_CD ");
		sql.append("	,RHK.KOSEI_SYOHIN_CD ");
		sql.append("	,RHK.HANBAI_START_DT ");
		sql.append("	,RHK.HANBAI_END_DT ");
		// #3686 MSTB101 2017.02.14 T.Yajima Mod (S)
		//sql.append("	,TRTT.TENPO_CD ");
		sql.append("    ,TRT.TENPO_CD ");
		// #3686 MSTB101 2017.02.14 T.Yajima Mod (E)
		// #4775 Add 2017.04.24 S.Takayama (S)
		sql.append("    ,CASE ");
		// 2017.05.09 M.Akagi #4845 (S)
		sql.append("    WHEN WTPSR.SYOHIN_CD IS NULL THEN '1' ");
		// 2017.05.09 M.Akagi #4845 (E)
		sql.append("    WHEN WTPSR.DELETE_FG=1 OR WTPS.DELETE_FG=1 THEN '1' ");
		sql.append("    ELSE NVL(WTPSR.BAIKA_HAISHIN_FG, WTPS.BAIKA_HAISHIN_FG) ");
		sql.append("    END AS BAIKA_HAISHIN_FG ");
		//sql.append("    ,NVL(WTPSR.BAIKA_HAISHIN_FG, WTPS.BAIKA_HAISHIN_FG) ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	R_HAMPER_KOSEI RHK ");
		// #3686 MSTB101 2017.02.14 T.Yajima Del (S)
//		sql.append("	INNER JOIN ");
//        sql.append("		TMP_R_TANPINTEN_TORIATUKAI TRTT ");
//        sql.append("	ON ");
//        sql.append("		RHK.HAMPER_SYOHIN_CD = TRTT.SYOHIN_CD ");
		// #3686 MSTB101 2017.02.14 T.Yajima Del (E)
		// 2017.05.09 M.Akagi #4845 (S)
		sql.append("	CROSS JOIN ");
		sql.append("      (  ");
		sql.append("        SELECT ");
		sql.append("          TRT.TENPO_CD ");
		sql.append("        FROM ");
		sql.append("          TMP_R_TENPO TRT ");
		sql.append("            WHERE ");
		sql.append("                TRT.ZAIMU_END_DT    > '" + batchDt + "' AND ");
        // #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("                TRT.DELETE_FG       = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		// sql.append("                TRT.TENPO_KB        IN (" + MASTER_IF_PLU_TENPO_KB + ") ");
		sql.append("                TRT.DELETE_FG       = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        // #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("      ) TRT  ");
		// 2017.05.09 M.Akagi #4845 (E) 
		sql.append("	INNER JOIN ");
		sql.append("		WK_TEC_PLU_SYOHIN WTPS ");
		sql.append("	ON ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("		RHK.HAMPER_SYOHIN_CD = WTPS.SYOHIN_CD ");
		sql.append("		RHK.KOSEI_SYOHIN_CD = WTPS.SYOHIN_CD ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		// 2017.05.09 M.Akagi #4845 (S)
        // #3686 MSTB101 2017.02.14 T.Yajima Mod (S)
		//sql.append("	LEFT JOIN ");
		//sql.append("    INNER JOIN ");
        // #3686 MSTB101 2017.02.14 T.Yajima Mod (E)
		sql.append("	LEFT JOIN ");
		// 2017.05.09 M.Akagi #4845 (E)
        sql.append("		WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
        sql.append("	ON ");
        // #3686 MSTB101 2017.02.14 T.Yajima Mod (S)
        //sql.append("		RHK.HAMPER_SYOHIN_CD = WTPSR.SYOHIN_CD	AND ");
        //sql.append("		TRTT.TENPO_CD = WTPSR.TENPO_CD ");
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append("        RHK.HAMPER_SYOHIN_CD = WTPSR.SYOHIN_CD   ");
        sql.append("        RHK.KOSEI_SYOHIN_CD = WTPSR.SYOHIN_CD   ");
        // 2017.05.09 M.Akagi #4845 (S)
        sql.append("        AND TRT.TENPO_CD = WTPSR.TENPO_CD   ");
        // 2017.05.09 M.Akagi #4845 (E)
        // 2017.05.09 M.Akagi #4845 Del (S)
        // #4775 Add 2017.04.24 S.Takayama (S)
        // #3686 MSTB101 2017.02.14 T.Yajima Mod (E)
        // #3686 MSTB101 2017.02.14 T.Yajima Add (S)
//        sql.append("    INNER JOIN ");
//        sql.append("      (  ");
//        sql.append("        SELECT ");
//        sql.append("          TRT.TENPO_CD ");
//        sql.append("        FROM ");
//        sql.append("          TMP_R_TENPO TRT ");
//        sql.append("            WHERE ");
//        sql.append("                TRT.ZAIMU_END_DT    > '" + batchDt + "' AND ");
//        sql.append("                TRT.DELETE_FG       = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
//        sql.append("                TRT.TENPO_KB        IN (" + MASTER_IF_PLU_TENPO_KB + ") ");
//        sql.append("      ) TRT  ");
//        sql.append("  ON ");
//        sql.append("    TRIM(TRT.TENPO_CD)  = WTPSR.TENPO_CD ");
        // 2017.05.09 M.Akagi #4845 Del (E)
        // #3686 MSTB101 2017.02.14 T.Yajima Add (E)
        // #4845 Del 2017/04/28 M.Son（S)
		//sql.append("	WHERE ");
		//sql.append("		RHK.HANBAI_START_DT	<= '" + yokuBatchDt + "'	AND ");
// #4433 2017.03.30 Mod Li.Sheng 対応（S)
//		sql.append("		RHK.HANBAI_END_DT	>= '" + batchDt + "'		AND ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("		RHK.HANBAI_END_DT	> '" + batchDt + "'		AND ");
		//sql.append("		RHK.HANBAI_END_DT	> '" + batchDt + "'	 ");
// #4433 2017.03.30 Mod Li.Sheng 対応（E)
// #4433 2017.03.30 Del Li.Sheng 対応（S)
//		sql.append("		RHK.DELETE_KB	= '" + CONST_ZERO + "' ");
		// #3686 MSTB101 2017.02.14 T.Yajima Add (S)
//		sql.append("        AND ");
// #4433 2017.03.30 Del Li.Sheng 対応（E)
		//sql.append("        WTPS.DELETE_FG   = '" + CONST_ZERO + "' AND ");
		//sql.append("        WTPSR.DELETE_FG   = '" + CONST_ZERO + "' ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		// #4845 Del 2017/04/28 M.Son（E)
		// #3686 MSTB101 2017.02.14 T.Yajima Add (E)
		return sql.toString();
	}

    /**
     * WK_ハンパー構成をWK_PLU店別商品にマージするSQLを取得する
     *
     * @return WK_PLU店別商品にマージするSQL
     */
    private String getWkTecPluMergeSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" MERGE INTO");
        sql.append(" 	WK_TEC_PLU WTP");
        sql.append(" 	USING");
        sql.append(" 		(");
        sql.append(" 			SELECT	NVL(WTPSR.BUNRUI1_CD, WTPS.BUNRUI1_CD) AS BUNRUI1_CD,");
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append(" 					WHK.KOSEI_SYOHIN_CD AS SYOHIN_CD,");
        // #4845 Mod 2017/05/09 M.Son（S)
        //sql.append(" 					WHK.HAMPER_SYOHIN_CD AS SYOHIN_CD,");
        sql.append(" 					WTPS.SYOHIN_CD AS SYOHIN_CD,");
        // #4845 Mod 2017/05/09 M.Son（E)
        // #4775 Add 2017.04.24 S.Takayama (E)
        sql.append(" 					WTPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD,");
        // #4845 Mod 2017/05/09 M.Son（S)
        //sql.append(" 					WHK.TENPO_CD AS TENPO_CD,");
        sql.append(" 					WTPSR.TENPO_CD AS TENPO_CD,");
        // #4845 Mod 2017/05/09 M.Son（E)
        sql.append(" 					NVL(WTPSR.GENTANKA_VL, WTPS.GENTANKA_VL) AS GENTANKA_VL,");
        sql.append(" 					NVL(WTPSR.BAITANKA_VL, WTPS.BAITANKA_VL) AS BAITANKA_VL,");
        sql.append(" 					NVL(WTPSR.SIIRESAKI_CD, WTPS.SIIRESAKI_CD) AS SIIRESAKI_CD,");
        sql.append(" 					NVL(WTPSR.PLU_SEND_DT, WTPS.PLU_SEND_DT) AS PLU_SEND_DT,");
		sql.append("					CASE ");
// #4433 2017.03.30 Mod Li.Sheng 対応（S)
//		sql.append("						WHEN WHK.HANBAI_END_DT <> '" + batchDt + "' AND WHK.BAIKA_HAISHIN_FG = '0' THEN '0' ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("						WHEN WHK.BAIKA_HAISHIN_FG = '0' THEN '0' ");
		// #4845 Mod 2017/04/28 M.Son（S)
		//sql.append("						WHEN WHK.BAIKA_HAISHIN_FG = '1' THEN '1' ");
		sql.append("						WHEN WHK.HAMPER_SYOHIN_CD IS NULL THEN '1' ");
		sql.append("						WHEN WHK.BAIKA_HAISHIN_FG = '1' THEN '1' ");
		// #4845 Mod 2017/04/28 M.Son（E)
		// #4775 Add 2017.04.24 S.Takayama (E)
// #4433 2017.03.30 Mod Li.Sheng 対応（E)
		sql.append("						ELSE NVL(WTPSR.BAIKA_HAISHIN_FG, WTPS.BAIKA_HAISHIN_FG) ");
		sql.append("	 				END AS BAIKA_HAISHIN_FG, ");
        sql.append(" 					WTPS.BUNRUI5_CD AS BUNRUI5_CD,");
        sql.append(" 					WTPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA,");
        sql.append(" 					WTPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA,");
        sql.append(" 					WTPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA,");
        sql.append(" 					WTPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 					WTPS.ZEI_KB AS ZEI_KB,");
        sql.append("					'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB, ");
        sql.append(" 					WTPS.BUNRUI2_CD AS BUNRUI2_CD,");
        sql.append(" 					WTPS.TEIKAN_FG AS TEIKAN_FG,");
        sql.append(" 					WTPS.ZEI_RT AS ZEI_RT,");
// #4433 2017.03.30 Mod Li.Sheng 対応（S)
//        sql.append(" 					WTPS.SEASON_ID AS SEASON_ID,");
        sql.append(" 					DECODE(WGEA.SYOHIN_CD,NULL,NULL,WTPS.BUNRUI5_CD) AS SEASON_ID,");
// #4433 2017.03.30 Mod Li.Sheng 対応（E)
        sql.append(" 					WTPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT,");
        sql.append(" 					WTPS.CARD_FG AS CARD_FG,");
		sql.append("					'" + userLog.getJobId() + "' AS INSERT_USER_ID, ");
		sql.append("					'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS, ");
		sql.append("					'" + userLog.getJobId() + "' AS UPDATE_USER_ID, ");
		sql.append("					'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS, ");
        sql.append(" 					WTPS.HANBAI_TANI AS HANBAI_TANI,");
        sql.append(" 					WTPS.KEIRYOKI_NM AS KEIRYOKI_NM,");
        sql.append(" 					NVL(WTPSR.PLU_HANEI_TIME, WTPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME,");
        sql.append(" 					WTPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 					WTPS.LABEL_SEIBUN AS LABEL_SEIBUN,");
        sql.append(" 					WTPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO,");
        sql.append(" 					WTPS.LABEL_TUKAIKATA AS LABEL_TUKAIKATA,");
        sql.append(" 					TRT.GYOTAI_KB AS GYOTAI_KB,");
        sql.append(" 					WTPS.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("					,WTPS.HANBAI_TANI_EN AS HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)
		// #3686 MSTB101 2017.02.14 T.Yajima Mod (S)
//		sql.append("	 			 , GREATEST（WTPS.DELETE_FG，WTPSR.DELETE_FG）AS DELETE_FG ");
// #4433 2017.03.30 Mod Li.Sheng 対応（S)
//		sql.append("	 			 , CASE WHEN WHK.HANBAI_END_DT <> '" +  batchDt + "' AND WHK.BAIKA_HAISHIN_FG = '0' THEN '0' ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("	 			 , CASE WHEN WHK.BAIKA_HAISHIN_FG = '0' THEN '0' ");
// #4433 2017.03.30 Mod Li.Sheng 対応（E)	
		//sql.append("	 			   ELSE GREATEST（WTPS.DELETE_FG，WTPSR.DELETE_FG）END AS DELETE_FG ");
		sql.append("	 			   , GREATEST(WTPS.DELETE_FG，WTPSR.DELETE_FG) AS DELETE_FG ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		// #3686 MSTB101 2017.02.14 T.Yajima Mod (E)
// #4418 2017.03.28 Mod Li.Sheng 対応（E)
// #4433 2017.03.30 Add Li.Sheng 対応（S)
//        sql.append(" 			FROM	WK_HAMPER_KOSEI WHK ");
		// #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append(" 			FROM	(SELECT WHK1.KOSEI_SYOHIN_CD AS KOSEI_SYOHIN_CD, ");
// #4845 Del 2017/04/28 M.Son（S)
//		sql.append(" 			FROM	(SELECT WHK1.HAMPER_SYOHIN_CD AS HAMPER_SYOHIN_CD, ");
//        // #4775 Add 2017.04.24 S.Takayama (E)
//        sql.append(" 				 WHK1.TENPO_CD AS TENPO_CD, ");
//        // #4775 Add 2017.04.24 S.Takayama (S)
//        //sql.append(" 				 MIN(WHK1.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
//        sql.append(" 				 MAX(WHK1.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
//        // #4775 Add 2017.04.24 S.Takayama (E)
//        sql.append(" 			FROM	WK_HAMPER_KOSEI WHK1 ");
//        // #4775 Add 2017.04.24 S.Takayama (S)
//        //sql.append(" 			GROUP BY WHK1.KOSEI_SYOHIN_CD,WHK1.TENPO_CD) WHK  "); 
//        sql.append(" 			GROUP BY WHK1.HAMPER_SYOHIN_CD,WHK1.TENPO_CD) WHK  "); 
//        // #4775 Add 2017.04.24 S.Takayama (E)
//// #4433 2017.03.30 Add Li.Sheng 対応（E)       
//        sql.append("					INNER JOIN ");
//        sql.append("						WK_TEC_PLU_SYOHIN WTPS ");
//        sql.append("					ON ");
//        // #4775 Add 2017.04.24 S.Takayama (S)
//        //sql.append("						WHK.KOSEI_SYOHIN_CD = WTPS.SYOHIN_CD ");   
//        sql.append("						WHK.HAMPER_SYOHIN_CD = WTPS.SYOHIN_CD ");   
//        // #4775 Add 2017.04.24 S.Takayama (E)
//        // #3686 MSTB101 2017.02.14 T.Yajima Mod (S)
//        //sql.append("					LEFT JOIN ");
//        sql.append("                    INNER JOIN ");
//        // #3686 MSTB101 2017.02.14 T.Yajima Mod (E)
//        sql.append("						WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
//        sql.append("					ON ");
//        // #4775 Add 2017.04.24 S.Takayama (S)
//        //sql.append("						WHK.KOSEI_SYOHIN_CD = WTPSR.SYOHIN_CD	AND ");
//        sql.append("						WHK.HAMPER_SYOHIN_CD = WTPSR.SYOHIN_CD	AND ");
//        // #4775 Add 2017.04.24 S.Takayama (E)
//        sql.append("						WHK.TENPO_CD = WTPSR.TENPO_CD ");
// #4845 Del 2017/04/28 M.Son（E)
        // #4845 Add 2017/04/28 M.Son（S)
        sql.append("			FROM	WK_TEC_PLU_SYOHIN WTPS ");
        sql.append("				INNER JOIN ");
        sql.append("						WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
        sql.append("					ON ");
        sql.append("						WTPS.SYOHIN_CD = WTPSR.SYOHIN_CD ");
        sql.append("				LEFT JOIN ");
        sql.append("					(SELECT WHK1.HAMPER_SYOHIN_CD, ");
        sql.append("							WHK1.TENPO_CD, ");
        sql.append("							MAX(WHK1.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
        sql.append("					FROM	WK_HAMPER_KOSEI WHK1 ");
        sql.append("					GROUP BY WHK1.HAMPER_SYOHIN_CD,WHK1.TENPO_CD) WHK  "); 
        sql.append("					ON ");
        sql.append("						WTPSR.SYOHIN_CD = WHK.HAMPER_SYOHIN_CD AND");
        sql.append("						WTPSR.TENPO_CD = WHK.TENPO_CD ");
        // #4845 Add 2017/04/28 M.Son（E)
        // #4845 Add 2017/05/09 M.Son（S)
        //sql.append("					INNER JOIN ");
        sql.append("					LEFT JOIN ");
        // #4845 Add 2017/05/09 M.Son（E)
        sql.append("						TMP_R_TENPO TRT ");
        sql.append("					ON ");
        sql.append("						WHK.TENPO_CD = TRT.TENPO_CD ");
// #4433 2017.03.30 Add Li.Sheng 対応（S)   
        sql.append("					LEFT JOIN WK_GROUPBAIHEN_EXCLUDE_ASN WGEA  ");
        // 2017.05.09 M.Akagi #4845 (S)
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append("					ON WHK.KOSEI_SYOHIN_CD = WGEA.SYOHIN_CD ");
        //sql.append("					ON WHK.HAMPER_SYOHIN_CD = WGEA.SYOHIN_CD ");
        // #4775 Add 2017.04.24 S.Takayama (E)
        //sql.append("					AND WHK.TENPO_CD = WGEA.TENPO_CD ");
        sql.append("					ON WTPS.SYOHIN_CD = WGEA.SYOHIN_CD ");
        sql.append("					AND WTPSR.TENPO_CD = WGEA.TENPO_CD ");
        // 2017.05.09 M.Akagi #4845 (E)
// #4433 2017.03.30 Add Li.Sheng 対応（E)     
// #4433 2017.03.30 Mod Li.Sheng 対応（S) 
//        sql.append(" 			WHERE	WHK.HANBAI_START_DT = '" + yokuBatchDt + "'	OR ");
//        sql.append(" 					WHK.HANBAI_END_DT = '" + batchDt + "'	OR ");
        sql.append(" 			WHERE	");
// #4433 2017.03.30 Mod Li.Sheng 対応（E) 
        // #4845 Add 2017/04/28 M.Son（S)
        sql.append("					WTPS.HAMPER_SYOHIN_FG = '1' AND");
        sql.append("					(");
        // #4845 Add 2017/04/28 M.Son（E)
		sql.append("					EXISTS ");
		sql.append("						( ");
		sql.append("							SELECT ");
		sql.append("			 					1 ");
		sql.append("							FROM ");
		sql.append("								WK_TEC_PLU WTP ");
		sql.append("							WHERE ");
		// 2017.05.09 M.Akagi #4845 (S)
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("								WHK.KOSEI_SYOHIN_CD	= WTP.SYOHIN_CD	AND ");
		//sql.append("								WHK.HAMPER_SYOHIN_CD	= WTP.SYOHIN_CD	AND ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		//sql.append("								WHK.TENPO_CD		= WTP.TENPO_CD	 ");
		sql.append("								WTPS.SYOHIN_CD	= WTP.SYOHIN_CD	AND ");
		sql.append("								WTPSR.TENPO_CD		= WTP.TENPO_CD	 ");
		// 2017.05.09 M.Akagi #4845 (E)
		sql.append("						) ");
// #4433 2017.03.30 Add Li.Sheng 対応（S)
		sql.append("				OR NOT EXISTS ");
		sql.append("						( ");
		sql.append("							SELECT ");
		sql.append("			 					* ");
		sql.append("							FROM ");
		sql.append("								( ");	
		// #4775 Add 2017.04.24 S.Takayama (S)
		sql.append("							SELECT BHK.HAMPER_SYOHIN_CD, ");
		//sql.append("							SELECT BHK.KOSEI_SYOHIN_CD, ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		sql.append("							BHK.TENPO_CD, ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		sql.append("							MAX(BHK.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
		//sql.append("							MIN(BHK.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		sql.append("							FROM BK_HAMPER_KOSEI BHK ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("							GROUP BY BHK.KOSEI_SYOHIN_CD,BHK.TENPO_CD ");
		sql.append("							GROUP BY BHK.HAMPER_SYOHIN_CD,BHK.TENPO_CD ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		sql.append("							) SUB1 ");
		sql.append("							WHERE ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("								WHK.KOSEI_SYOHIN_CD	= SUB1.KOSEI_SYOHIN_CD	AND ");
		sql.append("								WHK.HAMPER_SYOHIN_CD	= SUB1.HAMPER_SYOHIN_CD	AND ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		sql.append("								WHK.TENPO_CD		= SUB1.TENPO_CD	 AND ");
		sql.append("								WHK.BAIKA_HAISHIN_FG		= SUB1.BAIKA_HAISHIN_FG	 ");
		sql.append("						) ");
		// #4845 Add 2017/04/28 M.Son（S)
		sql.append("					) ");
		// #4845 Add 2017/04/28 M.Son（E)
// #4433 2017.03.30 Add Li.Sheng 対応（E)
        sql.append(" 		) MAINDATA ");
        sql.append(" 	ON");
        sql.append(" 	(");
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append(" 		MAINDATA.BUNRUI1_CD = WTP.BUNRUI1_CD AND");
        // #6620 DEL 2022.11.18 VU.TD (E)
        sql.append(" 		MAINDATA.SYOHIN_CD  = WTP.SYOHIN_CD AND");
        sql.append(" 		MAINDATA.TENPO_CD   = WTP.TENPO_CD");
        sql.append(" 	)");
        sql.append(" WHEN MATCHED THEN");
        sql.append(" 	UPDATE");
        sql.append(" 		SET");
        sql.append(" 			WTP.BAIKA_HAISHIN_FG = MAINDATA.BAIKA_HAISHIN_FG,");
        sql.append(" 			WTP.UPDATE_USER_ID = MAINDATA.UPDATE_USER_ID, ");
        sql.append(" 			WTP.UPDATE_TS = MAINDATA.UPDATE_TS ");
        sql.append(" WHEN NOT MATCHED THEN");
        sql.append(" 	INSERT");
        sql.append(" 		(");
        sql.append(" 			BUNRUI1_CD,");
        sql.append(" 			SYOHIN_CD,");
        sql.append(" 			OLD_SYOHIN_CD,");
        sql.append(" 			TENPO_CD,");
        sql.append(" 			GENTANKA_VL,");
        sql.append(" 			BAITANKA_VL,");
        sql.append(" 			SIIRESAKI_CD,");
        sql.append(" 			PLU_SEND_DT,");
        sql.append(" 			BAIKA_HAISHIN_FG,");
        sql.append(" 			BUNRUI5_CD,");
        sql.append(" 			REC_HINMEI_KANJI_NA,");
        sql.append(" 			REC_HINMEI_KANA_NA,");
        sql.append(" 			KIKAKU_KANJI_NA,");
        sql.append(" 			MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 			ZEI_KB,");
        sql.append(" 			ERR_KB,");
        sql.append(" 			BUNRUI2_CD,");
        sql.append(" 			TEIKAN_FG,");
        sql.append(" 			ZEI_RT,");
        sql.append(" 			SEASON_ID,");
        sql.append(" 			SYOHI_KIGEN_DT,");
        sql.append(" 			CARD_FG,");
        sql.append(" 			INSERT_USER_ID,");
        sql.append(" 			INSERT_TS,");
        sql.append(" 			UPDATE_USER_ID,");
        sql.append(" 			UPDATE_TS,");
        sql.append(" 			HANBAI_TANI,");
        sql.append(" 			KEIRYOKI_NM,");
        sql.append(" 			PLU_HANEI_TIME,");
        sql.append(" 			SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 			LABEL_SEIBUN,");
        sql.append(" 			LABEL_HOKAN_HOHO,");
        sql.append(" 			LABEL_TUKAIKATA,");
        sql.append(" 			GYOTAI_KB,");
        sql.append(" 			LABEL_COUNTRY_NA");
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686 MSTB101 2017.02.14 T.Yajima Mod (S)
		sql.append("	 		,  DELETE_FG ");
		// #3686 MSTB101 2017.02.14 T.Yajima Mod (E)
        sql.append(" 		)");
        sql.append(" 	VALUES");
        sql.append(" 		(");
        sql.append(" 			MAINDATA.BUNRUI1_CD,");
        sql.append(" 			MAINDATA.SYOHIN_CD,");
        sql.append(" 			MAINDATA.OLD_SYOHIN_CD,");
        sql.append(" 			MAINDATA.TENPO_CD,");
        sql.append(" 			MAINDATA.GENTANKA_VL,");
        sql.append(" 			MAINDATA.BAITANKA_VL,");
        sql.append(" 			MAINDATA.SIIRESAKI_CD,");
        sql.append(" 			MAINDATA.PLU_SEND_DT,");
        sql.append(" 			MAINDATA.BAIKA_HAISHIN_FG,");
        sql.append(" 			MAINDATA.BUNRUI5_CD,");
        sql.append(" 			MAINDATA.REC_HINMEI_KANJI_NA,");
        sql.append(" 			MAINDATA.REC_HINMEI_KANA_NA,");
        sql.append(" 			MAINDATA.KIKAKU_KANJI_NA,");
        sql.append(" 			MAINDATA.MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 			MAINDATA.ZEI_KB,");
        sql.append(" 			MAINDATA.ERR_KB,");
        sql.append(" 			MAINDATA.BUNRUI2_CD,");
        sql.append(" 			MAINDATA.TEIKAN_FG,");
        sql.append(" 			MAINDATA.ZEI_RT,");
        sql.append(" 			MAINDATA.SEASON_ID,");
        sql.append(" 			MAINDATA.SYOHI_KIGEN_DT,");
        sql.append(" 			MAINDATA.CARD_FG,");
        sql.append(" 			MAINDATA.INSERT_USER_ID,");
        sql.append(" 			MAINDATA.INSERT_TS,");
        sql.append(" 			MAINDATA.UPDATE_USER_ID,");
        sql.append(" 			MAINDATA.UPDATE_TS,");
        sql.append(" 			MAINDATA.HANBAI_TANI,");
        sql.append(" 			MAINDATA.KEIRYOKI_NM,");
        sql.append(" 			MAINDATA.PLU_HANEI_TIME,");
        sql.append(" 			MAINDATA.SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 			MAINDATA.LABEL_SEIBUN,");
        sql.append(" 			MAINDATA.LABEL_HOKAN_HOHO,");
        sql.append(" 			MAINDATA.LABEL_TUKAIKATA,");
        sql.append(" 			MAINDATA.GYOTAI_KB,");
        sql.append(" 			MAINDATA.LABEL_COUNTRY_NA");
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,MAINDATA.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686 MSTB101 2017.02.14 T.Yajima Mod (S)
		sql.append("	 		,MAINDATA.DELETE_FG ");
		// #3686 MSTB101 2017.02.14 T.Yajima Mod (E)
        sql.append(" 		)");

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
