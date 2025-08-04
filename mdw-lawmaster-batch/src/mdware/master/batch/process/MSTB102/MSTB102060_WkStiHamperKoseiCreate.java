package mdware.master.batch.process.MSTB102;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.command.MstbGetPluMaster;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst011701_BaikaHaishinFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: 指定日ハンパー構成反映処理(MSTB102060)</p>
 * <p>説明　: ハンパー構成商品として登録されている商品をPLU送信対象にする。</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.01.17) #1749対応 S.Takayama 新規作成
 * @version 1.01 (2017.02.09) #3765対応 S.Takayama
 * @version 1.02 (2017.02.14) #3686対応 S.Takayama
 * @version 1.03 (2017.03.15) #4336対応 T.Han FIVIMART対応
 * @version 1.04 (2017.03.28) Li.Sheng #4418対応
 * @version 1.04 (2017.03.29) Li.Sheng #4433対応
 * @version 1.06 (2017.04.19) T.Han #4705対応
 * @version 1.07 (2017.04.25) M.Son #4775対応
 * @version 1.08 (2017.04.28) T.Han #4845対応
 * @version 1.09 (2017.05.11) M.Son #4985対応
 * @version 1.10 (2017.05.16) M.Akagi #5041対応
 * @version 1.11 (2021.12.20) KHOI.ND #6406
 */
public class MSTB102060_WkStiHamperKoseiCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
    /** 伝送ヘッダーレコードリスト */
    private List densoRecordList = null;
    /** 受付No */
    private String uketsukeNo = "";
    /** 店舗コード */
    private String tenpoCd = "";
    /** 対象日 */
    private String taisyoDt = "";
    
    // #3686 MSTB102 2017.02.14 S.Takayama (S)
	/** 店舗区分 */
	private String MASTER_IF_PLU_TENPO_KB = null;
	// #3686 MSTB102 2017.02.14 S.Takayama (E)

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// 2017.03.15 T.Han #4336対応（S)
	/** 特殊日付("99999999") */
	private static final String SPECIAL_DATE = "99999999";
	// 2017.03.15 T.Han #4336対応（E)

	// テーブル
	private static final String TABLE_WK_STI_HAMPER_KOSEI = "WK_STI_HAMPER_KOSEI"; // WK_指定日ハンパー構成

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;

	/** ゼロ (定数) */
	private static final String CONST_ZERO = "0";


	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB102060_WkStiHamperKoseiCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB102060_WkStiHamperKoseiCreate() {
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
			
			// #3686 MSTB102 2017.02.14 S.Takayama (S)
			// 店舗区分セット
			Map tenpoMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_IF_TENPO_KB);
			MASTER_IF_PLU_TENPO_KB = SqlSupportUtil.createInString(tenpoMap.keySet().toArray());
			// #3686 MSTB102 2017.02.14 S.Takayama (E)

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();

			// WK_指定日ハンパー構成 のTRUNCATE
			writeLog(Level.INFO_INT, "WK_指定日ハンパー構成 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_STI_HAMPER_KOSEI);
			writeLog(Level.INFO_INT, "WK_指定日ハンパー構成 を削除処理を終了します。");

			//PLU指定日マスタを取得する。
			ResultSet rsData = new MstbGetPluMaster().process(db);

			densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 受付No、店舗コード、対象日取得
            	uketsukeNo = rsData.getString("UKETSUKE_NO");
                tenpoCd = rsData.getString("TENPO_CD");
                taisyoDt = rsData.getString("TAISYO_DT");
                MSTB102060_WkStiHamperKoseiCreateRow rowData = new MSTB102060_WkStiHamperKoseiCreateRow(uketsukeNo, tenpoCd, taisyoDt);
                densoRecordList.add(rowData);
            }

            for (Object densoRecord : densoRecordList) {
            	MSTB102060_WkStiHamperKoseiCreateRow rowData = (MSTB102060_WkStiHamperKoseiCreateRow) densoRecord;
            	uketsukeNo = rowData.getUketsukeNo();
                tenpoCd = rowData.getTenpoCd();
                taisyoDt = rowData.getTaisyoDt();

                // WK_指定日ハンパー構成の登録
				writeLog(Level.INFO_INT, "WK_指定日ハンパー構成 登録処理を開始します。");
				// 2017.03.15 T.Han #4336対応（S)
				//iRec = db.executeUpdate(getWkHamperKoseiInsertSql(uketsukeNo,tenpoCd,taisyoDt));
				iRec = db.executeUpdate(getInsertWkHamperKoseiSql(uketsukeNo,tenpoCd,taisyoDt));
				// 2017.03.15 T.Han #4336対応（E)
				writeLog(Level.INFO_INT, "WK_指定日ハンパー構成 を" + iRec + "件作成しました。");
				writeLog(Level.INFO_INT, "WK_指定日ハンパー構成 登録処理を終了します。");

				db.commit();

				// #4775 Mod 2017.04.25 M.Son (S)
//				// WK_指定日PLU店別商品マージ
//				writeLog(Level.INFO_INT, "WK_指定日PLU店別商品マージ処理を開始します。");
//				// 2017.03.15 T.Han #4336対応（S)
//				//iRec = db.executeUpdate(getWkTecPluMergeSql(taisyoDt));
//				iRec = db.executeUpdate(getMergeWkTecPluSql(taisyoDt));
//				// 2017.03.15 T.Han #4336対応（E)
//				writeLog(Level.INFO_INT, "WK_指定日PLU店別商品を" + iRec + "件マージしました。");
//				writeLog(Level.INFO_INT, "WK_指定日PLU店別商品マージ処理を終了します。");

				// WK_指定日PLU店別商品削除
				writeLog(Level.INFO_INT, "WK_指定日PLU店別商品 削除処理を開始します。");
				// 2017.05.16 M.Akagi #5041 (S)
// 2017.04.28 T.Han #4845対応（S)
//				iRec = db.executeUpdate(getDelWkTecPluSql(taisyoDt));
				//iRec = db.executeUpdate(getDelWkTecPluSql(uketsukeNo,taisyoDt));
// 2017.04.28 T.Han #4845対応（E)
				iRec = db.executeUpdate(getDelWkTecPluSql(uketsukeNo,taisyoDt,tenpoCd));
				// 2017.05.16 M.Akagi #5041 (E)
				writeLog(Level.INFO_INT, "WK_指定日PLU店別商品 を" + iRec + "件削除しました。");
				writeLog(Level.INFO_INT, "WK_指定日PLU店別商品 削除処理を終了します。");
				// #4775 Mod 2017.04.25 M.Son (E)

				db.commit();
			}

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
	 * WK_HAMPER_KOSEIを登録するSQLを取得する
	 * @param taisyo_Dt
	 * @param tenpo_Cd
	 *
	 * @return WK_HAMPER_KOSEI登録SQL
	 */
	private String getWkHamperKoseiInsertSql(String uketsuke_No,String tenpo_Cd, String taisyo_Dt) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_STI_HAMPER_KOSEI ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,TAISYO_DT ");
		sql.append("	,HAMPER_SYOHIN_CD ");
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
		sql.append("	 '" + uketsuke_No + "' AS UKETSUKE_NO ");
		sql.append("	,'" + taisyo_Dt + "' AS TAISYO_DT ");
		sql.append("	,RHK.HAMPER_SYOHIN_CD ");
		sql.append("	,RHK.KOSEI_SYOHIN_CD ");
		sql.append("	,RHK.HANBAI_START_DT ");
		sql.append("	,RHK.HANBAI_END_DT ");
		sql.append("	,'" + tenpo_Cd + "' AS TENPO_CD ");
		sql.append("	,NVL(WTSPSR.BAIKA_HAISHIN_FG, WTSPS.BAIKA_HAISHIN_FG) ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	R_HAMPER_KOSEI RHK ");
// #3686 MSTB102 2017.02.14 S.Takayama (S)
//		sql.append("	INNER JOIN ");
//        sql.append("		TMP_R_STI_TORIATUKAI TSRTT ");
//        sql.append("	ON ");
//        sql.append("		RHK.HAMPER_SYOHIN_CD = TSRTT.SYOHIN_CD ");
// #3686 MSTB102 2017.02.14 S.Takayama (E)
		sql.append("	INNER JOIN ");
        sql.append("		WK_TEC_STI_PLU_SYOHIN WTSPS ");
        sql.append("	ON ");
        sql.append("		RHK.HAMPER_SYOHIN_CD = WTSPS.SYOHIN_CD ");
// #3686 MSTB102 2017.02.14 S.Takayama (S)
//		sql.append("	LEFT JOIN ");
//        sql.append("		WK_TEC_STI_PLU_SYOHIN_REIGAI WTSPSR ");
//        sql.append("	ON ");
//        sql.append("		RHK.HAMPER_SYOHIN_CD = WTSPSR.SYOHIN_CD	AND ");
//        sql.append("		TSRTT.TENPO_CD = WTSPSR.TENPO_CD ");
        sql.append("	INNER JOIN ");
        sql.append("		WK_TEC_STI_PLU_SYOHIN_REIGAI WTSPSR ");
        sql.append("		ON RHK.HAMPER_SYOHIN_CD = WTSPSR.SYOHIN_CD ");
        sql.append("	INNER JOIN ");
        sql.append("		( SELECT TENPO_CD ");
        sql.append("		  FROM TMP_R_STI_TENPO ");
        sql.append("		  WHERE ");
        sql.append("		  DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        // #6406 Mod 2021.10.20 KHOI.ND (S)
        // sql.append("		  AND TENPO_KB IN (" + MASTER_IF_PLU_TENPO_KB + ") ");
        // #6406 Mod 2021.10.20 KHOI.ND (E)
        sql.append("		  AND ZAIMU_END_DT > '" +batchDt +"')TRET ");
        sql.append("	ON	TRIM(TRET.TENPO_CD) = TRIM(WTSPSR.TENPO_CD) ");
// #3686 MSTB102 2017.02.14 S.Takayama (E)
		sql.append("	WHERE ");
		sql.append("		RHK.HANBAI_START_DT	<= '" + taisyo_Dt + "'	AND ");
		sql.append("		RHK.HANBAI_END_DT	>= '" + taisyo_Dt + "'		AND ");
// #3686 MSTB102 2017.02.14 S.Takayama (S)
//		sql.append("		TSRTT.TENPO_CD = '" + tenpo_Cd + "'		AND ");
		sql.append("		TRET.TENPO_CD = '" + tenpo_Cd + "'		AND ");
// #3686 MSTB102 2017.02.14 S.Takayama (E)
		sql.append("		RHK.DELETE_KB	= '" + CONST_ZERO + "' ");
		// #3686 MSTB102 2017.02.14 S.Takayama (S)
        sql.append("		AND WTSPS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        sql.append("		AND WTSPSR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #3686 MSTB102 2017.02.14 S.Takayama (E)

		return sql.toString();
	}

    /**
     * WK_ハンパー構成をWK_PLU店別商品にマージするSQLを取得する
     *
     * @return WK_PLU店別商品にマージするSQL
     */
    private String getWkTecPluMergeSql(String taisyoDt) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" MERGE INTO");
        sql.append(" 	WK_TEC_STI_PLU WTSP ");
        sql.append(" 	USING");
        sql.append(" 		(");
        sql.append(" 			SELECT	");
        sql.append(" 					WSHK.UKETSUKE_NO,");
        sql.append(" 					WSHK.TAISYO_DT,");
        sql.append(" 					NVL(WTSPSR.BUNRUI1_CD, WTSPS.BUNRUI1_CD) AS BUNRUI1_CD,");
        sql.append(" 					WSHK.KOSEI_SYOHIN_CD AS SYOHIN_CD,");
        sql.append(" 					WTSPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD,");
        sql.append(" 					WSHK.TENPO_CD AS TENPO_CD,");
        sql.append(" 					NVL(WTSPSR.GENTANKA_VL, WTSPS.GENTANKA_VL) AS GENTANKA_VL,");
        sql.append(" 					NVL(WTSPSR.BAITANKA_VL, WTSPS.BAITANKA_VL) AS BAITANKA_VL,");
        sql.append(" 					NVL(WTSPSR.SIIRESAKI_CD, WTSPS.SIIRESAKI_CD) AS SIIRESAKI_CD,");
        sql.append(" 					NVL(WTSPSR.PLU_SEND_DT, WTSPS.PLU_SEND_DT) AS PLU_SEND_DT,");
// #3686 MSTB102 2017.02.14 S.Takayama (S)
//		sql.append("					CASE ");
//		sql.append("						WHEN WSHK.HANBAI_END_DT <> '" + taisyoDt + "' AND WSHK.BAIKA_HAISHIN_FG = '0' THEN '0' ");
//		sql.append("						ELSE NVL(WTSPSR.BAIKA_HAISHIN_FG, WTSPS.BAIKA_HAISHIN_FG) ");
//		sql.append("	 				END AS BAIKA_HAISHIN_FG, ");
        sql.append("	 				WSHK.BAIKA_HAISHIN_FG AS BAIKA_HAISHIN_FG, ");
// #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append(" 					WTSPS.BUNRUI5_CD AS BUNRUI5_CD,");
        sql.append(" 					WTSPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA,");
        sql.append(" 					WTSPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA,");
        sql.append(" 					WTSPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA,");
        sql.append(" 					WTSPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 					WTSPS.ZEI_KB AS ZEI_KB,");
        sql.append("					'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB, ");
        sql.append(" 					WTSPS.BUNRUI2_CD AS BUNRUI2_CD,");
        sql.append(" 					WTSPS.TEIKAN_FG AS TEIKAN_FG,");
        sql.append(" 					WTSPS.ZEI_RT AS ZEI_RT,");
        sql.append(" 					WTSPS.SEASON_ID AS SEASON_ID,");
        sql.append(" 					WTSPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT,");
        sql.append(" 					WTSPS.CARD_FG AS CARD_FG,");
		sql.append("					'" + userLog.getJobId() + "' AS INSERT_USER_ID, ");
		sql.append("					'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS, ");
		sql.append("					'" + userLog.getJobId() + "' AS UPDATE_USER_ID, ");
		sql.append("					'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS, ");
        sql.append(" 					WTSPS.HANBAI_TANI AS HANBAI_TANI,");
        sql.append(" 					WTSPS.KEIRYOKI_NM AS KEIRYOKI_NM,");
        sql.append(" 					NVL(WTSPSR.PLU_HANEI_TIME, WTSPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME,");
        sql.append(" 					WTSPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 					WTSPS.LABEL_SEIBUN AS LABEL_SEIBUN,");
        sql.append(" 					WTSPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO,");
        sql.append(" 					WTSPS.LABEL_TUKAIKATA AS LABEL_TUKAIKATA,");
        sql.append(" 					TRST.GYOTAI_KB AS GYOTAI_KB,");
        sql.append(" 					WTSPS.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");
        // #3765 MSTB102 2017.02.09 S.Takayama (S)
        sql.append(" 					, WTSPS.HANBAI_TANI_EN AS HANBAI_TANI_EN");
        // #3765 MSTB102 2017.02.09 S.Takayama (E)
        // #3686 MSTB102 2017.02.17 S.Takayama (S)
        sql.append(" 					, GREATEST(WTSPS.DELETE_FG ,WTSPSR.DELETE_FG ) AS DELETE_FG ");
        // #3686 MSTB102 2017.02.17 S.Takayama (E)
        sql.append(" 			FROM	( ");
        sql.append(" 						SELECT KOSEI_SYOHIN_CD ");
        sql.append(" 							, TENPO_CD ");
        sql.append(" 							, TAISYO_DT ");
        sql.append(" 							, UKETSUKE_NO ");
        sql.append(" 							, BAIKA_HAISHIN_FG ");
        sql.append(" 							, HANBAI_END_DT ");
        sql.append(" 						FROM WK_STI_HAMPER_KOSEI ");
        sql.append(" 						GROUP BY KOSEI_SYOHIN_CD ");
        sql.append(" 								, TENPO_CD ");
        sql.append(" 								, TAISYO_DT ");
        sql.append(" 								, UKETSUKE_NO ");
        sql.append(" 								, BAIKA_HAISHIN_FG ");
        sql.append(" 								, HANBAI_END_DT ");
        sql.append(" 					)WSHK ");
        sql.append("					INNER JOIN ");
        sql.append("						WK_TEC_STI_PLU_SYOHIN WTSPS ");
        sql.append("					ON ");
        sql.append("						WSHK.KOSEI_SYOHIN_CD = WTSPS.SYOHIN_CD ");
        // #3686 MSTB102 2017.02.14 S.Takayama (S)
        //sql.append("					LEFT JOIN ");
        sql.append("					INNER JOIN ");
        // #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append("						WK_TEC_STI_PLU_SYOHIN_REIGAI WTSPSR ");
        sql.append("					ON ");
        sql.append("						WSHK.KOSEI_SYOHIN_CD = WTSPSR.SYOHIN_CD	AND ");
        sql.append("						WSHK.TENPO_CD = WTSPSR.TENPO_CD ");
        sql.append("					INNER JOIN ");
        sql.append("						TMP_R_STI_TENPO TRST ");
        sql.append("					ON ");
        sql.append("						WSHK.TENPO_CD = TRST.TENPO_CD ");
        // #3686 MSTB102 2017.02.14 S.Takayama (S)
        sql.append(" 			WHERE WSHK.BAIKA_HAISHIN_FG ='"+ mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() +"' ");
        sql.append(" 					AND  WTSPS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        sql.append(" 					AND  WTSPSR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        // #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append(" 		) MAINDATA ");
        sql.append(" 	ON");
        sql.append(" 	(");
        sql.append(" 		MAINDATA.BUNRUI1_CD = WTSP.BUNRUI1_CD AND");
        sql.append(" 		MAINDATA.SYOHIN_CD  = WTSP.SYOHIN_CD AND");
        sql.append(" 		MAINDATA.UKETSUKE_NO   = WTSP.UKETSUKE_NO");
        sql.append(" 	)");
        sql.append(" WHEN MATCHED THEN");
        sql.append(" 	UPDATE");
        sql.append(" 		SET");
        sql.append(" 			WTSP.BAIKA_HAISHIN_FG = MAINDATA.BAIKA_HAISHIN_FG,");
        sql.append(" 			WTSP.UPDATE_USER_ID = MAINDATA.UPDATE_USER_ID, ");
        sql.append(" 			WTSP.UPDATE_TS = MAINDATA.UPDATE_TS ");
        sql.append(" WHEN NOT MATCHED THEN");
        sql.append(" 	INSERT");
        sql.append(" 		(");
        sql.append(" 			UKETSUKE_NO,");
        sql.append(" 			TAISYO_DT,");
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
	     // #3765 MSTB102 2017.02.09 S.Takayama (S)
        sql.append(" 			, HANBAI_TANI_EN ");
	     // #3765 MSTB102 2017.02.09 S.Takayama (E)
        // #3686 MSTB102 2017.02.17 S.Takayama (S)
        sql.append(" 			, DELETE_FG  ");
        // #3686 MSTB102 2017.02.17 S.Takayama (E)
        sql.append(" 		)");
        sql.append(" 	VALUES");
        sql.append(" 		(");
        sql.append(" 			MAINDATA.UKETSUKE_NO,");
        sql.append(" 			MAINDATA.TAISYO_DT,");
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
        // #3765 MSTB102 2017.02.09 S.Takayama (S)
        sql.append(" 			, MAINDATA.HANBAI_TANI_EN ");
        // #3765 MSTB102 2017.02.09 S.Takayama (E)
        // #3686 MSTB102 2017.02.17 S.Takayama (S)
        sql.append(" 			, MAINDATA.DELETE_FG  ");
        // #3686 MSTB102 2017.02.17 S.Takayama (E)
        sql.append(" 		)");

        return sql.toString();
    }

	// 2017.03.15 T.Han #4336対応（S)
	/**
	 * WK_HAMPER_KOSEIを登録するSQLを取得する
	 * @param uketsuke_No
	 * @param taisyo_Dt
	 * @param tenpo_Cd
	 *
	 * @return WK_HAMPER_KOSEI登録SQL
	 */
	private String getInsertWkHamperKoseiSql(String uketsuke_No,String tenpo_Cd, String taisyo_Dt) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_STI_HAMPER_KOSEI ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,TAISYO_DT ");
		sql.append("	,HAMPER_SYOHIN_CD ");
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
		sql.append("	 '" + uketsuke_No + "' AS UKETSUKE_NO ");
		sql.append("	,'" + taisyo_Dt + "' AS TAISYO_DT ");
		sql.append("	,RHK.HAMPER_SYOHIN_CD ");
		sql.append("	,RHK.KOSEI_SYOHIN_CD ");
		sql.append("	,RHK.HANBAI_START_DT ");
		sql.append("	,RHK.HANBAI_END_DT ");
		sql.append("	,'" + tenpo_Cd + "' AS TENPO_CD ");
		// #4775 Mod 2017.04.25 M.Son (S)
		//sql.append("	,NVL(TRSTR.BAIKA_HAISHIN_FG, TRSS.BAIKA_HAISHIN_FG) ");
		// #4985 Mod 2017.05.11 M.Son (S)
		//sql.append("	,CASE WHEN TRSTR.DELETE_FG = '1' OR TRSS.DELETE_FG = '1'");
		sql.append("	,CASE ");
		sql.append("		WHEN TRSTR.SYOHIN_CD IS NULL ");
		sql.append("			THEN '1'");
		sql.append("		WHEN TRSTR.DELETE_FG = '1' OR TRSS.DELETE_FG = '1'");
		// #4985 Mod 2017.05.11 M.Son (E)
		sql.append("			THEN '1'");
		sql.append("		ELSE NVL(TRSTR.BAIKA_HAISHIN_FG, TRSS.BAIKA_HAISHIN_FG) ");
		sql.append("	 END AS BAIKA_HAISHIN_FG");
		// #4775 Mod 2017.04.25 M.Son (E)
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	R_HAMPER_KOSEI RHK ");
		// #4985 Mod 2017.05.11 M.Son (S)
		sql.append("	CROSS JOIN ");
		sql.append("		( ");
		sql.append("			SELECT TENPO_CD ");
		sql.append("			FROM TMP_R_STI_TENPO ");
		sql.append("			WHERE ");
		sql.append("				ZAIMU_END_DT > '" +batchDt +"' AND ");
		// #6406 Mod 2021.12.20 KHOI.ND (S)
		// sql.append("				DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		// sql.append("				TENPO_KB IN (" + MASTER_IF_PLU_TENPO_KB + ") ");
		sql.append("				DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #6406 Mod 2021.12.20 KHOI.ND (E)
		sql.append("		) TRST ");
		// #4985 Mod 2017.05.11 M.Son (E)
        sql.append("	INNER JOIN ");
        sql.append("		( ");
        sql.append("			SELECT  ");
        sql.append("				TRSS.*  ");
        sql.append("			FROM ");
        sql.append("				TMP_R_STI_SYOHIN TRSS  ");
        sql.append("				INNER JOIN  ");
        sql.append("					(  ");
        sql.append("						SELECT  ");
        sql.append("							 SYOHIN_CD  ");
        sql.append("							,MAX(YUKO_DT) AS YUKO_DT  ");
        sql.append("						FROM  ");
        sql.append("							TMP_R_STI_SYOHIN ");
        sql.append("						WHERE  ");
        sql.append("							YUKO_DT <= '" + taisyo_Dt + "' AND ");
        sql.append("							PLU_SEND_DT <= '" + taisyo_Dt + "' ");
        sql.append("						GROUP BY  ");
        sql.append("							 SYOHIN_CD ");
        sql.append("					) SUB2  ");
        sql.append("					ON ");
        sql.append("						TRSS.YUKO_DT = SUB2.YUKO_DT AND ");
        sql.append("						TRSS.SYOHIN_CD = SUB2.SYOHIN_CD ");
        sql.append("		) TRSS ");
        sql.append("		ON ");
        // #4775 Mod 2017.04.25 M.Son (S)
        //sql.append("			RHK.HAMPER_SYOHIN_CD = TRSS.SYOHIN_CD ");
        sql.append("			RHK.KOSEI_SYOHIN_CD = TRSS.SYOHIN_CD ");
        // #4775 Mod 2017.04.25 M.Son (E)
        // #4985 Mod 2017.05.11 M.Son (S)
        //sql.append("	INNER JOIN ");
        sql.append("	LEFT JOIN ");
        // #4985 Mod 2017.05.11 M.Son (E)
        sql.append("		( ");
        sql.append("			SELECT ");
        sql.append("				TRSTR.* ");
        sql.append("			FROM ");
        sql.append("				TMP_R_STI_TENSYOHIN_REIGAI TRSTR ");
        sql.append("				INNER JOIN  ");
        sql.append("					(  ");
        sql.append("						SELECT  ");
        sql.append("							 SYOHIN_CD ");
        sql.append("							,TENPO_CD ");
        sql.append("							,MAX(YUKO_DT) AS YUKO_DT ");
        sql.append("						FROM  ");
        sql.append("							TMP_R_STI_TENSYOHIN_REIGAI ");
        sql.append("						WHERE  ");
        sql.append("							YUKO_DT <= '" + taisyo_Dt + "' AND ");
        sql.append("							( PLU_SEND_DT <= '" + taisyo_Dt + "' OR PLU_SEND_DT = '" + SPECIAL_DATE + "' ) ");
        sql.append("						GROUP BY  ");
        sql.append("							 SYOHIN_CD, TENPO_CD  ");
        sql.append("					) SUB3  ");
        sql.append("					ON ");
        sql.append("						TRSTR.YUKO_DT = SUB3.YUKO_DT AND ");
        sql.append("						TRSTR.SYOHIN_CD = SUB3.SYOHIN_CD AND ");
        sql.append("						TRSTR.TENPO_CD = SUB3.TENPO_CD ");
        sql.append("		) TRSTR ");
        sql.append("		ON ");
        // #4775 Mod 2017.04.25 M.Son (S)
        //sql.append("			RHK.HAMPER_SYOHIN_CD = TRSTR.SYOHIN_CD ");
        sql.append("			RHK.KOSEI_SYOHIN_CD = TRSTR.SYOHIN_CD ");
        // #4775 Mod 2017.04.25 M.Son (E)
        // #4985 Add 2017.05.11 M.Son (S)
        sql.append("		AND TRST.TENPO_CD = TRSTR.TENPO_CD ");
        // #4985 Add 2017.05.11 M.Son (E)
// #4985 Del 2017.05.11 M.Son (S)
//        sql.append("	INNER JOIN ");
//        sql.append("		(  ");
//        sql.append("			SELECT TENPO_CD ");
//        sql.append("			FROM TMP_R_STI_TENPO ");
//        sql.append("			WHERE ");
//        sql.append("				ZAIMU_END_DT > '" +batchDt +"' AND ");
//        sql.append("				DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
//        sql.append("				TENPO_KB IN (" + MASTER_IF_PLU_TENPO_KB + ") ");
//        sql.append("		) TRST  ");
//        sql.append("		ON ");
//        sql.append("			TRIM(TRST.TENPO_CD) = TRIM(TRSTR.TENPO_CD) ");
// #4985 Del 2017.05.11 M.Son (E)
		sql.append("	WHERE ");
	    // 2017.04.28 T.Han #4845対応（S)
		//sql.append("		RHK.HANBAI_START_DT	<= '" + taisyo_Dt + "'	AND ");
		//sql.append("		RHK.HANBAI_END_DT	>= '" + taisyo_Dt + "'		AND ");
	    // 2017.04.28 T.Han #4845対応（E)
        // #4775 Mod 2017.04.25 M.Son (S)
		//sql.append("		TRST.TENPO_CD = '" + tenpo_Cd + "'		AND ");
		//sql.append("		RHK.DELETE_KB	= '" + CONST_ZERO + "' AND ");
        //sql.append("		TRSS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
        //sql.append("		TRSTR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        sql.append("		TRST.TENPO_CD = '" + tenpo_Cd + "' ");
        // #4775 Mod 2017.04.25 M.Son (E)

		return sql.toString();
	}

    /**
     * WK_ハンパー構成をWK_PLU店別商品にマージするSQLを取得する
     *
     * @return WK_PLU店別商品にマージするSQL
     */
	// 2017.05.16 M.Akagi #5041 (S)
    // #4775 Mod 2017.04.25 M.Son (S)
    //private String getMergeWkTecPluSql(String taisyoDt) throws SQLException {
// 2017.04.28 T.Han #4845対応（S)
//    private String getDelWkTecPluSql(String taisyoDt) throws SQLException {
      //private String getDelWkTecPluSql(String uketsukeNo, String taisyoDt) throws SQLException {
// 2017.04.28 T.Han #4845対応（E)
    // #4775 Mod 2017.04.25 M.Son (E)
	private String getDelWkTecPluSql(String uketsukeNo, String taisyoDt, String tenpoCd) throws SQLException {
    // 2017.05.16 M.Akagi #5041 (E)
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

        StringBuilder sql = new StringBuilder();
// #4775 Mod 2017.04.25 M.Son (S)
//        sql.append(" MERGE INTO");
//        sql.append(" 	WK_TEC_STI_PLU WTSP ");
//        sql.append(" 	USING");
//        sql.append(" 		(");
//        sql.append(" 			SELECT	");
//        sql.append(" 					WSHK.UKETSUKE_NO,");
//        sql.append(" 					WSHK.TAISYO_DT,");
//        sql.append(" 					NVL(TRSTR.BUNRUI1_CD, TRSS.BUNRUI1_CD) AS BUNRUI1_CD,");
//        sql.append(" 					WSHK.KOSEI_SYOHIN_CD AS SYOHIN_CD,");
//        sql.append(" 					TRSS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD,");
//        sql.append(" 					WSHK.TENPO_CD AS TENPO_CD,");
//        sql.append(" 					NVL(TRSTR.GENTANKA_VL, TRSS.GENTANKA_VL) AS GENTANKA_VL,");
//        sql.append(" 					NVL(TRSTR.BAITANKA_VL, TRSS.BAITANKA_VL) AS BAITANKA_VL,");
//        sql.append(" 					NVL(TRSTR.SIIRESAKI_CD, TRSS.SIIRESAKI_CD) AS SIIRESAKI_CD,");
//        sql.append(" 					NVL(TRSTR.PLU_SEND_DT, TRSS.PLU_SEND_DT) AS PLU_SEND_DT,");
//        sql.append("	 				WSHK.BAIKA_HAISHIN_FG AS BAIKA_HAISHIN_FG, ");
//        sql.append(" 					TRSS.BUNRUI5_CD AS BUNRUI5_CD,");
//        sql.append("					SUBSTR(TRSS.HINMEI_KANJI_NA, 1, 40) AS REC_HINMEI_KANJI_NA,");
//        sql.append("					SUBSTR(TRSS.HINMEI_KANA_NA, 1, 20) AS REC_HINMEI_KANA_NA,");
//        sql.append(" 					TRSS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA,");
//        sql.append(" 					TRSS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL,");
//        sql.append(" 					TRSS.ZEI_KB AS ZEI_KB,");
//        sql.append("					'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB, ");
//        sql.append(" 					TRSS.BUNRUI2_CD AS BUNRUI2_CD,");
//        sql.append("					NVL(TRESA.HANBAI_HOHO_KB, '0') AS TEIKAN_FG, ");
//        sql.append("					TRSTR.TAX_RT AS ZEI_RT, ");
//// #4433 2017.03.29 Mod Li.Sheng 対応（S)
//        //sql.append(" 					'' AS SEASON_ID,");
//        sql.append(" 					DECODE(SUB6.SYOHIN_CD,NULL,NULL,TRSS.BUNRUI5_CD) AS SEASON_ID,");
//// #4433 2017.03.29 Mod Li.Sheng 対応（E)     
//        sql.append(" 					TRSS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT,");
//        sql.append("					CASE ");
//        sql.append("						WHEN TRESA.MEMBER_DISCOUNT_FG = 0 THEN 2 ");
//        sql.append("						WHEN TRESA.MEMBER_DISCOUNT_FG = 1 THEN 0 ");
//        sql.append("		 			END AS CARD_FG, ");
//        sql.append("					'" + userLog.getJobId() + "' AS INSERT_USER_ID, ");
//        sql.append("					'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS, ");
//        sql.append("					'" + userLog.getJobId() + "' AS UPDATE_USER_ID, ");
//        sql.append("					'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS, ");
//        sql.append("					RN2.KANJI_NA AS HANBAI_TANI, ");
//        sql.append("					TRESA.SYOHIN_EIJI_NA AS KEIRYOKI_NM, ");
//        sql.append("					NVL(TRSTR.PLU_HANEI_TIME, TRSS.PLU_HANEI_TIME) AS PLU_HANEI_TIME, ");
//        sql.append(" 					TRSS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER,");
//        sql.append(" 					TRESA.LABEL_SEIBUN AS LABEL_SEIBUN,");
//        sql.append(" 					TRESA.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO,");
//        sql.append(" 					TRESA.LABEL_TUKAIKATA AS LABEL_TUKAIKATA,");
//        sql.append(" 					TRST.GYOTAI_KB AS GYOTAI_KB,");
//        sql.append("					RN1.KANJI_RN AS LABEL_COUNTRY_NA, ");
//        sql.append("					RN2.KANJI_RN AS HANBAI_TANI_EN, ");
//// #4418 2017.03.28 Mod Li.Sheng 対応（S) 
////        sql.append("	  				GREATEST(TRSS.DELETE_FG,TRSTR.DELETE_FG) AS DELETE_FG ");
//        sql.append("	  				DECODE(WSHK.BAIKA_HAISHIN_FG,'0','0',GREATEST(TRSS.DELETE_FG,TRSTR.DELETE_FG)) AS DELETE_FG ");
//// #4418 2017.03.28 Mod Li.Sheng 対応（E) 
        sql.append(" DELETE FROM");
        sql.append(" 	WK_TEC_STI_PLU WTSP");
        sql.append(" WHERE EXISTS");
        sql.append(" 	(");
        sql.append(" 		SELECT 1 FROM (	");
        sql.append(" 			SELECT	");
        sql.append(" 					WSHK.UKETSUKE_NO,");
// 2017.04.28 T.Han #4845対応（S)
//        sql.append(" 					NVL(TRSTR.BUNRUI1_CD, TRSS.BUNRUI1_CD) AS BUNRUI1_CD,");
        sql.append(" 					TRSS.BUNRUI1_CD AS BUNRUI1_CD,");
//        sql.append(" 					WSHK.HAMPER_SYOHIN_CD AS SYOHIN_CD");
        sql.append(" 					TRSS.SYOHIN_CD AS SYOHIN_CD");
// 2017.04.28 T.Han #4845対応（E)
        // #4775 Mod 2017.04.25 M.Son (E)
        // 2017.05.16 M.Akagi #5041 (S)
        sql.append(" 					,TRSTR.TENPO_CD AS TENPO_CD");
        // 2017.05.16 M.Akagi #5041 (E)
        sql.append(" 			FROM ");
// 2017.04.28 T.Han #4845対応（S)
//        sql.append(" 				( ");
//        // #4775 Mod 2017.04.25 M.Son (S)
//        //sql.append(" 					SELECT KOSEI_SYOHIN_CD ");
//        sql.append(" 					SELECT HAMPER_SYOHIN_CD ");
//        // #4775 Mod 2017.04.25 M.Son (E)
//        sql.append(" 						, TENPO_CD ");
//        sql.append(" 						, TAISYO_DT ");
//        sql.append(" 						, UKETSUKE_NO ");
//        sql.append(" 						, BAIKA_HAISHIN_FG ");
//        sql.append(" 						, HANBAI_END_DT ");
//        sql.append(" 					FROM WK_STI_HAMPER_KOSEI ");
//        // #4775 Mod 2017.04.25 M.Son (S)
//        //sql.append(" 					GROUP BY KOSEI_SYOHIN_CD ");
//        sql.append(" 					GROUP BY HAMPER_SYOHIN_CD ");
//        // #4775 Mod 2017.04.25 M.Son (E)
//        sql.append(" 							, TENPO_CD ");
//        sql.append(" 							, TAISYO_DT ");
//        sql.append(" 							, UKETSUKE_NO ");
//        sql.append(" 							, BAIKA_HAISHIN_FG ");
//        sql.append(" 							, HANBAI_END_DT ");
//        sql.append(" 				) WSHK ");
//        sql.append("			INNER JOIN ");
// 2017.04.28 T.Han #4845対応（E)
        sql.append("				( ");
        sql.append("					SELECT  ");
        sql.append("						TRSS.*  ");
        sql.append("					FROM ");
        sql.append("						TMP_R_STI_SYOHIN TRSS  ");
        sql.append("						INNER JOIN  ");
        sql.append("							(  ");
        sql.append("								SELECT  ");
        sql.append("									 SYOHIN_CD  ");
        sql.append("									,MAX(YUKO_DT) AS YUKO_DT  ");
        sql.append("								FROM  ");
        sql.append("									TMP_R_STI_SYOHIN ");
        sql.append("								WHERE  ");
        sql.append("									YUKO_DT <= '" + taisyoDt + "' AND ");
        sql.append("									PLU_SEND_DT <= '" + taisyoDt + "' ");
        sql.append("								GROUP BY  ");
        sql.append("									 SYOHIN_CD ");
        sql.append("							) SUB2  ");
        sql.append("							ON ");
        sql.append("								TRSS.YUKO_DT = SUB2.YUKO_DT AND ");
        sql.append("								TRSS.SYOHIN_CD = SUB2.SYOHIN_CD ");
        sql.append("				) TRSS ");
// 2017.04.28 T.Han #4845対応（S)
//        sql.append("				ON ");
// 2017.04.28 T.Han #4845対応（E)
        // #4775 Mod 2017.04.25 M.Son (S)
        //sql.append("					WSHK.KOSEI_SYOHIN_CD = TRSS.SYOHIN_CD ");
// 2017.04.28 T.Han #4845対応（S)
//        sql.append("					WSHK.HAMPER_SYOHIN_CD = TRSS.SYOHIN_CD ");
// 2017.04.28 T.Han #4845対応（E)
        // #4775 Mod 2017.04.25 M.Son (E)
        sql.append("			INNER JOIN ");
        sql.append("				( ");
        sql.append("					SELECT ");
        sql.append("						TRSTR.* ");
        sql.append("					FROM ");
        sql.append("						TMP_R_STI_TENSYOHIN_REIGAI TRSTR ");
        sql.append("						INNER JOIN  ");
        sql.append("							(  ");
        sql.append("								SELECT  ");
        sql.append("									 SYOHIN_CD ");
        sql.append("									,TENPO_CD ");
        sql.append("									,MAX(YUKO_DT) AS YUKO_DT ");
        sql.append("								FROM  ");
        sql.append("									TMP_R_STI_TENSYOHIN_REIGAI ");
        sql.append("								WHERE  ");
        sql.append("									YUKO_DT <= '" + taisyoDt + "' AND ");
        sql.append("									( PLU_SEND_DT <= '" + taisyoDt + "' OR PLU_SEND_DT = '" + SPECIAL_DATE + "' ) ");
        sql.append("								GROUP BY  ");
        sql.append("									 SYOHIN_CD, TENPO_CD  ");
        sql.append("							) SUB3  ");
        sql.append("							ON ");
        sql.append("								TRSTR.YUKO_DT = SUB3.YUKO_DT AND ");
        sql.append("								TRSTR.SYOHIN_CD = SUB3.SYOHIN_CD AND ");
        // 2017.05.16 M.Akagi #5041 (S)
        //sql.append("								TRSTR.TENPO_CD = SUB3.TENPO_CD ");
        sql.append("								TRSTR.TENPO_CD = SUB3.TENPO_CD AND ");
        sql.append("								TRSTR.TENPO_CD = '" + tenpoCd + "'  ");
        // 2017.05.16 M.Akagi #5041 (E)
        sql.append("				) TRSTR ");
        sql.append("				ON ");
        // #4775 Mod 2017.04.25 M.Son (S)
        //sql.append("					WSHK.KOSEI_SYOHIN_CD = TRSTR.SYOHIN_CD AND ");
// 2017.04.28 T.Han #4845対応（S)
//        sql.append("					WSHK.HAMPER_SYOHIN_CD = TRSTR.SYOHIN_CD AND ");
        // #4775 Mod 2017.04.25 M.Son (E)
//        sql.append("					WSHK.TENPO_CD = TRSTR.TENPO_CD ");
      sql.append("					TRSS.SYOHIN_CD = TRSTR.SYOHIN_CD ");
// 2017.04.28 T.Han #4845対応（E)
// 2017.04.28 T.Han #4845対応（S)
//        sql.append("			INNER JOIN ");
//        sql.append("				TMP_R_STI_TENPO TRST ");
//        sql.append("			ON ");
//        sql.append("				WSHK.TENPO_CD = TRST.TENPO_CD ");
// 2017.04.28 T.Han #4845対応（E)
        sql.append("			INNER JOIN  ");
        sql.append("				( ");
        sql.append("					SELECT  ");
        sql.append("						 TRETR.TAX_RATE_KB ");
        sql.append("						,TRETR.TAX_RT  ");
        sql.append("					FROM ");
        sql.append("						TMP_R_STI_TAX_RATE TRETR  ");
        sql.append("						INNER JOIN ");
        sql.append("							(  ");
        sql.append("								SELECT  ");
        sql.append("									 TRETR.TAX_RATE_KB  ");
        sql.append("									,MAX(TRETR.YUKO_DT) AS YUKO_DT  ");
        sql.append("								FROM  ");
        sql.append("									TMP_R_STI_TAX_RATE TRETR  ");
        sql.append("								WHERE  ");
        sql.append("									TRETR.YUKO_DT <= '" + taisyoDt + "'  ");
        sql.append("									AND TRETR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        sql.append("								GROUP BY  ");
        sql.append("									 TRETR.TAX_RATE_KB  ");
        sql.append("							) SUB5  ");
        sql.append("						ON ");
        sql.append("							TRETR.TAX_RATE_KB = SUB5.TAX_RATE_KB ");
        sql.append("							AND TRETR.YUKO_DT = SUB5.YUKO_DT ");
        sql.append("				) TRSTR ");
        sql.append("				ON ");
        sql.append("					TRSS.TAX_RATE_KB	= TRSTR.TAX_RATE_KB ");
        sql.append("			INNER JOIN ");
        sql.append("				TMP_R_STI_SYOHIN_ASN TRESA ");
        sql.append("			ON ");
        sql.append("				TRESA.SYOHIN_CD = TRSS.SYOHIN_CD ");
        sql.append("				AND TRESA.YUKO_DT = TRSS.YUKO_DT ");
// 2017.04.28 T.Han #4845対応（S)
        sql.append("				AND TRESA.HAMPER_SYOHIN_FG = '1' ");
// 2017.04.28 T.Han #4845対応（E)
// 2017.04.28 T.Han #4845対応（S)
        sql.append("			LEFT JOIN ");
        sql.append(" 				WK_STI_HAMPER_KOSEI WSHK ");
        sql.append("			ON ");
        sql.append("				TRSTR.SYOHIN_CD = WSHK.HAMPER_SYOHIN_CD AND ");
        sql.append("				TRSTR.TENPO_CD = WSHK.TENPO_CD AND ");
        sql.append("				WSHK.UKETSUKE_NO = '" + uketsukeNo + "' ");
        sql.append("			LEFT JOIN ");
        sql.append("				TMP_R_STI_TENPO TRST ");
        sql.append("			ON ");
        sql.append("				WSHK.TENPO_CD = TRST.TENPO_CD ");
// 2017.04.28 T.Han #4845対応（E)
        sql.append("			LEFT JOIN ");
        sql.append("				R_NAMECTF RN1 ");
        sql.append("			ON ");
        sql.append("				RN1.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COUNTRY_DIVISION, userLocal) + "' AND ");
        sql.append("				TRIM(TRESA.COUNTRY_CD) = TRIM(RN1.CODE_CD) ");
        sql.append("			LEFT JOIN ");
        sql.append("				R_NAMECTF RN2 ");
        sql.append("			ON ");
        sql.append("				RN2.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "' AND ");
        sql.append("				TRIM(TRSS.HANBAI_TANI) = TRIM(RN2.CODE_CD) ");
// #4433 2017.03.29 Add Li.Sheng 対応（S)
        // 結合条件（外部：　サブクエリ６）
        sql.append("			LEFT JOIN ( ");
        sql.append("			SELECT DGEA.SYOHIN_CD, ");
        sql.append("			DHTT.TENPO_CD ");
// 2017.04.19 T.Han #4705対応 (S)
        sql.append("		   ,DT.BUNRUI1_CD ");
// 2017.04.19 T.Han #4705対応 (E)
        sql.append("			FROM DT_GROUPBAIHEN_EXCLUDE_ASN DGEA "); // グループ売変除外品ASN
        sql.append("			INNER JOIN DT_HANSOKU_TAISYO_TENPO DHTT "); // 販促対象店舗
        sql.append("			ON DGEA.THEME_CD = DHTT.THEME_CD  ");
        sql.append("			INNER JOIN DT_GROUPBAIHEN_ASN DGA "); // グループ売変ASN
        sql.append("			ON DGEA.THEME_CD = DGA.THEME_CD  ");
        sql.append("			INNER JOIN DT_THEME DT "); // 特売テーマデータ
        sql.append("			ON DT.THEME_CD = DGA.THEME_CD  ");
        sql.append("			AND DT.DELETE_FG = '0'  ");
        sql.append("			WHERE  ");
        sql.append("			(DT.DATE_SITEI_KB = '0' ");
        sql.append("			AND DGA.HANBAI_START_DT <= '" + taisyoDt + "' ");
        sql.append("			AND DGA.HANBAI_END_DT >= '" + taisyoDt + "') ");
        sql.append("			OR  (DT.DATE_SITEI_KB = '1' AND ");
        
		String day = taisyoDt.substring(6, 8);
		if (day.equals("01")) {
			sql.append("			DT.FLG_1ST = '1' ");
		} else if (day.equals("02")) {
			sql.append("			DT.FLG_2ND = '1' ");
		} else if (day.equals("03")) {
			sql.append("			DT.FLG_3RD = '1' ");
		} else if (day.equals("04")) {
			sql.append("			DT.FLG_4TH = '1' ");
		} else if (day.equals("05")) {
			sql.append("			DT.FLG_5TH = '1' ");
		} else if (day.equals("06")) {
			sql.append("			DT.FLG_6TH = '1' ");
		} else if (day.equals("07")) {
			sql.append("			DT.FLG_7TH = '1' ");
		} else if (day.equals("08")) {
			sql.append("			DT.FLG_8TH = '1' ");
		} else if (day.equals("09")) {
			sql.append("			DT.FLG_9TH = '1' ");
		} else if (day.equals("10")) {
			sql.append("			DT.FLG_10TH = '1' ");
		} else if (day.equals("11")) {
			sql.append("			DT.FLG_11TH = '1' ");
		} else if (day.equals("12")) {
			sql.append("			DT.FLG_12TH = '1' ");
		} else if (day.equals("13")) {
			sql.append("			DT.FLG_13TH = '1' ");
		} else if (day.equals("14")) {
			sql.append("			DT.FLG_14TH = '1' ");
		} else if (day.equals("15")) {
			sql.append("			DT.FLG_15TH = '1' ");
		} else if (day.equals("16")) {
			sql.append("			DT.FLG_16TH = '1' ");
		} else if (day.equals("17")) {
			sql.append("			DT.FLG_17TH = '1' ");
		} else if (day.equals("18")) {
			sql.append("			DT.FLG_18TH = '1' ");
		} else if (day.equals("19")) {
			sql.append("			DT.FLG_19TH = '1' ");
		} else if (day.equals("20")) {
			sql.append("			DT.FLG_20TH = '1' ");
		} else if (day.equals("21")) {
			sql.append("			DT.FLG_21ST = '1' ");
		} else if (day.equals("22")) {
			sql.append("			DT.FLG_22ND = '1' ");
		} else if (day.equals("23")) {
			sql.append("			DT.FLG_23RD = '1' ");
		} else if (day.equals("24")) {
			sql.append("			DT.FLG_24TH = '1' ");
		} else if (day.equals("25")) {
			sql.append("			DT.FLG_25TH = '1' ");
		} else if (day.equals("26")) {
			sql.append("			DT.FLG_26TH = '1' ");
		} else if (day.equals("27")) {
			sql.append("			DT.FLG_27TH = '1' ");
		} else if (day.equals("28")) {
			sql.append("			DT.FLG_28TH = '1' ");
		} else if (day.equals("29")) {
			sql.append("			DT.FLG_29TH = '1' ");
		} else if (day.equals("30")) {
			sql.append("			DT.FLG_30TH = '1' ");
		} else if (day.equals("31")) {
			sql.append("			DT.FLG_31ST = '1' ");
		}
		
        sql.append("			AND DGA.HANBAI_START_DT <= '" + taisyoDt + "' ");
        sql.append("			AND DGA.HANBAI_END_DT >= '" + taisyoDt + "') ");
        sql.append("			GROUP BY DGEA.SYOHIN_CD, ");
        sql.append("			DHTT.TENPO_CD ");     
// 2017.04.19 T.Han #4705対応 (S)
        sql.append("		   ,DT.BUNRUI1_CD ");
// 2017.04.19 T.Han #4705対応 (E)
        sql.append("			) SUB6 ");
        sql.append(" 	ON" );
        sql.append(" TRSS.SYOHIN_CD = SUB6.SYOHIN_CD ");
        sql.append(" AND TRST.TENPO_CD = SUB6.TENPO_CD ");
// 2017.04.19 T.Han #4705対応 (S)
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append(" AND TRSS.BUNRUI1_CD = SUB6.BUNRUI1_CD ");
//        sql.append(" AND TRSTR.BUNRUI1_CD = SUB6.BUNRUI1_CD ");
        // #6620 DEL 2022.11.18 VU.TD (E)
// 2017.04.19 T.Han #4705対応 (E)
// #4433 2017.03.29 Add Li.Sheng 対応（E)
        sql.append(" 			WHERE ");
// #4418 2017.03.28 Mod Li.Sheng 対応（S) 
//        sql.append(" 				WSHK.BAIKA_HAISHIN_FG ='"+ mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() +"' AND ");
//        sql.append(" 				TRSS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
//        sql.append(" 				TRSTR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
// #4775 Mod 2017.04.25 M.Son (S)
//        sql.append(" 				WSHK.BAIKA_HAISHIN_FG ='"+ mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() +"'  ");
        sql.append(" 				WSHK.BAIKA_HAISHIN_FG ='1'");
	    // 2017.04.28 T.Han #4845対応（S)
        sql.append(" 				OR WSHK.HAMPER_SYOHIN_CD IS NULL ");
	    // 2017.04.28 T.Han #4845対応（E)
        sql.append(" 		) MAINDATA ");
        sql.append(" 	WHERE ");
// 2017.04.28 T.Han #4845対応（S)
//        sql.append(" 		MAINDATA.UKETSUKE_NO = WTSP.UKETSUKE_NO AND");
// 2017.04.28 T.Han #4845対応（E)
        sql.append(" 		MAINDATA.SYOHIN_CD = WTSP.SYOHIN_CD AND");
        // 2017.05.16 M.Akagi #5041 (S)
        //sql.append(" 		MAINDATA.BUNRUI1_CD = WTSP.BUNRUI1_CD");
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append(" 		MAINDATA.BUNRUI1_CD = WTSP.BUNRUI1_CD AND");
        // #6620 DEL 2022.11.18 VU.TD (E)
        sql.append(" 		MAINDATA.TENPO_CD = WTSP.TENPO_CD");
        // 2017.05.16 M.Akagi #5041 (E)
        sql.append(" )");
// #4775 Mod 2017.04.25 M.Son (E)
// #4418 2017.03.28 Mod Li.Sheng 対応（E)     
// #4775 Del 2017.04.25 M.Son (S)
//        sql.append(" 		) MAINDATA ");
//        sql.append(" 	ON");
//        sql.append(" 	(");
//        sql.append(" 		MAINDATA.BUNRUI1_CD = WTSP.BUNRUI1_CD AND");
//        sql.append(" 		MAINDATA.SYOHIN_CD  = WTSP.SYOHIN_CD AND");
//        sql.append(" 		MAINDATA.UKETSUKE_NO   = WTSP.UKETSUKE_NO");
//        sql.append(" 	)");
//        sql.append(" WHEN MATCHED THEN");
//        sql.append(" 	UPDATE");
//        sql.append(" 		SET");
//        sql.append(" 			WTSP.BAIKA_HAISHIN_FG = MAINDATA.BAIKA_HAISHIN_FG,");
//        sql.append(" 			WTSP.UPDATE_USER_ID = MAINDATA.UPDATE_USER_ID, ");
//        sql.append(" 			WTSP.UPDATE_TS = MAINDATA.UPDATE_TS ");
//        sql.append(" WHEN NOT MATCHED THEN");
//        sql.append(" 	INSERT");
//        sql.append(" 		(");
//        sql.append(" 			UKETSUKE_NO,");
//        sql.append(" 			TAISYO_DT,");
//        sql.append(" 			BUNRUI1_CD,");
//        sql.append(" 			SYOHIN_CD,");
//        sql.append(" 			OLD_SYOHIN_CD,");
//        sql.append(" 			TENPO_CD,");
//        sql.append(" 			GENTANKA_VL,");
//        sql.append(" 			BAITANKA_VL,");
//        sql.append(" 			SIIRESAKI_CD,");
//        sql.append(" 			PLU_SEND_DT,");
//        sql.append(" 			BAIKA_HAISHIN_FG,");
//        sql.append(" 			BUNRUI5_CD,");
//        sql.append(" 			REC_HINMEI_KANJI_NA,");
//        sql.append(" 			REC_HINMEI_KANA_NA,");
//        sql.append(" 			KIKAKU_KANJI_NA,");
//        sql.append(" 			MAKER_KIBO_KAKAKU_VL,");
//        sql.append(" 			ZEI_KB,");
//        sql.append(" 			ERR_KB,");
//        sql.append(" 			BUNRUI2_CD,");
//        sql.append(" 			TEIKAN_FG,");
//        sql.append(" 			ZEI_RT,");
//        sql.append(" 			SEASON_ID,");
//        sql.append(" 			SYOHI_KIGEN_DT,");
//        sql.append(" 			CARD_FG,");
//        sql.append(" 			INSERT_USER_ID,");
//        sql.append(" 			INSERT_TS,");
//        sql.append(" 			UPDATE_USER_ID,");
//        sql.append(" 			UPDATE_TS,");
//        sql.append(" 			HANBAI_TANI,");
//        sql.append(" 			KEIRYOKI_NM,");
//        sql.append(" 			PLU_HANEI_TIME,");
//        sql.append(" 			SYOHI_KIGEN_HYOJI_PATTER,");
//        sql.append(" 			LABEL_SEIBUN,");
//        sql.append(" 			LABEL_HOKAN_HOHO,");
//        sql.append(" 			LABEL_TUKAIKATA,");
//        sql.append(" 			GYOTAI_KB,");
//        sql.append(" 			LABEL_COUNTRY_NA");
//        sql.append(" 			, HANBAI_TANI_EN ");
//        sql.append(" 			, DELETE_FG  ");
//        sql.append(" 		)");
//        sql.append(" 	VALUES");
//        sql.append(" 		(");
//        sql.append(" 			MAINDATA.UKETSUKE_NO,");
//        sql.append(" 			MAINDATA.TAISYO_DT,");
//        sql.append(" 			MAINDATA.BUNRUI1_CD,");
//        sql.append(" 			MAINDATA.SYOHIN_CD,");
//        sql.append(" 			MAINDATA.OLD_SYOHIN_CD,");
//        sql.append(" 			MAINDATA.TENPO_CD,");
//        sql.append(" 			MAINDATA.GENTANKA_VL,");
//        sql.append(" 			MAINDATA.BAITANKA_VL,");
//        sql.append(" 			MAINDATA.SIIRESAKI_CD,");
//        sql.append(" 			MAINDATA.PLU_SEND_DT,");
//        sql.append(" 			MAINDATA.BAIKA_HAISHIN_FG,");
//        sql.append(" 			MAINDATA.BUNRUI5_CD,");
//        sql.append(" 			MAINDATA.REC_HINMEI_KANJI_NA,");
//        sql.append(" 			MAINDATA.REC_HINMEI_KANA_NA,");
//        sql.append(" 			MAINDATA.KIKAKU_KANJI_NA,");
//        sql.append(" 			MAINDATA.MAKER_KIBO_KAKAKU_VL,");
//        sql.append(" 			MAINDATA.ZEI_KB,");
//        sql.append(" 			MAINDATA.ERR_KB,");
//        sql.append(" 			MAINDATA.BUNRUI2_CD,");
//        sql.append(" 			MAINDATA.TEIKAN_FG,");
//        sql.append(" 			MAINDATA.ZEI_RT,");
//        sql.append(" 			MAINDATA.SEASON_ID,");
//        sql.append(" 			MAINDATA.SYOHI_KIGEN_DT,");
//        sql.append(" 			MAINDATA.CARD_FG,");
//        sql.append(" 			MAINDATA.INSERT_USER_ID,");
//        sql.append(" 			MAINDATA.INSERT_TS,");
//        sql.append(" 			MAINDATA.UPDATE_USER_ID,");
//        sql.append(" 			MAINDATA.UPDATE_TS,");
//        sql.append(" 			MAINDATA.HANBAI_TANI,");
//        sql.append(" 			MAINDATA.KEIRYOKI_NM,");
//        sql.append(" 			MAINDATA.PLU_HANEI_TIME,");
//        sql.append(" 			MAINDATA.SYOHI_KIGEN_HYOJI_PATTER,");
//        sql.append(" 			MAINDATA.LABEL_SEIBUN,");
//        sql.append(" 			MAINDATA.LABEL_HOKAN_HOHO,");
//        sql.append(" 			MAINDATA.LABEL_TUKAIKATA,");
//        sql.append(" 			MAINDATA.GYOTAI_KB,");
//        sql.append(" 			MAINDATA.LABEL_COUNTRY_NA,");
//        sql.append(" 			MAINDATA.HANBAI_TANI_EN, ");
//        sql.append(" 			MAINDATA.DELETE_FG  ");
//        sql.append(" 		)");
// #4775 Mod 2017.04.25 M.Son (E)

        return sql.toString();
    }
    // 2017.03.15 T.Han #4336対応（E)

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
    /**
     * <p>タイトル: MSTB102060_WkStiHamperKoseiCreateRow クラス</p>
     * <p>説明　: 伝送ヘッダーデータを保持する</p>
     *
     */
    class MSTB102060_WkStiHamperKoseiCreateRow {

        /** 受付No */
        private String uketsukeNo;
        /** 店舗コード */
        private String tenpoCd;
        /** 対象日 */
        private String taisyoDt;

        /**
         * MSTB992071_PosFileCreateRow を生成
         *
         * @param uketsukeNo 受付No
         * @param tenpoCd 店舗コード
         * @param taisyoDt 対象日
         */
        public MSTB102060_WkStiHamperKoseiCreateRow(String uketsukeNo, String tenpoCd, String taisyoDt) {
            this.uketsukeNo = uketsukeNo;
            this.tenpoCd = tenpoCd;
            this.taisyoDt = taisyoDt;
        }

        /**
         * 受付Noを取得します。
         * @return 受付No
         */
        public String getUketsukeNo() {
            return uketsukeNo;
        }

        /**
         * 店舗コードを取得します。
         * @return 店舗コード
         */
        public String getTenpoCd() {
            return tenpoCd;
        }

        /**
         * 対象日を取得します。
         * @return 対象日
         */
        public String getTaisyoDt() {
            return taisyoDt;
        }

    }

}


