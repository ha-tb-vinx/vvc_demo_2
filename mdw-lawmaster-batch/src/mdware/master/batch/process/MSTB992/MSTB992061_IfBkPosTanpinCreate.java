package mdware.master.batch.process.MSTB992;

import java.sql.SQLException;
import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;

/**
 *
 * <p>タイトル: MSTB992061_IfBkPosTanpinCreate.java クラス</p>
 * <p>説明　: IF_PLU送信単品メンテとWK_PLU店別商品から、送信累積であるDT_PLU送信単品メンテを作成する。</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2015.08.07) DAI.BQ FIVImart様対応
 * @version 1.01 (2016.12.02) #2960 Li.Sheng FIVImart様対応
 * @Version 1.02 (2016.12.08) #3049 Li.Sheng FIVImart様対応
 * @version 1.03 (2017.02.09) #3765対応 T.Han FIVImart対応
 */
public class MSTB992061_IfBkPosTanpinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_BK_SEND = "BK_DT_TEC_PLU_SEND"; // BK_DT_PLU送信単品メンテ
	private static final String TABLE_SEND = "DT_TEC_PLU_SEND"; // DT_PLU送信単品メンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 店舗コード 開始位置 */
	private static final String TENPO_CD_ST_COLUMN = "2";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992061_IfBkPosTanpinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992061_IfBkPosTanpinCreate() {
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

			//バッチ日付取得
			String batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDate);

			// BK_DT_PLU送信単品メンテの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "BK_DT_PLU送信単品メンテの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_BK_SEND));
			writeLog(Level.INFO_INT, "BK_DT_PLU送信単品メンテを" + iRec + "件削除しました。");
			writeLog(Level.INFO_INT, "BK_DT_PLU送信単品メンテの削除処理（全件）を終了します。");

			// DT_PLU送信単品メンテのバックアップ（SEND→BK_SEND）
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテのバックアップ処理（SEND→BK_SEND）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_SEND, TABLE_BK_SEND, batchDate));
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテへ" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテのバックアップ処理（SEND→BK_SEND）を終了します。");

			// DT_PLU送信単品メンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテ(新規・訂正)登録処理（IF→SEND）を開始します。");
			iRec = db.executeUpdate(getDtTecPluSendInsertSql());
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテ(新規・訂正)登録処理（IF→SEND）を終了します。");

			// DT_PLU送信単品メンテの削除
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテ削除処理を開始します。");
			iRec = db.executeUpdate(getDtTecPluSendDeleteSql());
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテ削除を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "DT_PLU送信単品メンテ削除処理を終了します。");

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
	 * 全件削除用SQLを生成する。
	 * @param tableId	削除テーブル
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllDeleteSQL(String tableId) throws Exception {
		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("DELETE FROM " + tableId + " ");

		return strSql.toString();
	}


	/**
	 * 全件追加用SQLを生成する。
	 * @param tableIdFrom	追加元テーブル
	 * @param tableIdTo		追加先テーブル
	 * @param batchDt		バッチ日付
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllInsertSQL(String tableIdFrom, String tableIdTo, String batchDt) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + tableIdTo + " ");
		strSql.append("SELECT ");
		strSql.append("	'" + batchDt + "' ");
		strSql.append("	,'" + systemDt + "' ");
		strSql.append("	," + tableIdFrom + ".* ");
		strSql.append("FROM " + tableIdFrom + " ");

		return strSql.toString();
	}

	/**
	 * DT_TEC_PLU_SEND(新規・訂正)を登録するSQLを取得する
	 *
	 * @return DT_TEC_PLU_SEND(新規・訂正)登録SQL
	 */
	private String getDtTecPluSendInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("MERGE INTO ");
		sql.append("	DT_TEC_PLU_SEND DTPS ");
		sql.append("	USING ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 WTP.BUNRUI1_CD ");
		sql.append("			,WTP.SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (S)
		sql.append("			,WTP.OLD_SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (E)
		sql.append("			,WTP.TENPO_CD ");
		sql.append("			,WTP.GENTANKA_VL ");
		sql.append("			,WTP.BAITANKA_VL ");
		sql.append("			,WTP.SIIRESAKI_CD ");
		sql.append("			,WTP.PLU_SEND_DT ");
		sql.append("			,WTP.BAIKA_HAISHIN_FG ");
		sql.append("			,WTP.BUNRUI5_CD ");
		sql.append("			,WTP.REC_HINMEI_KANJI_NA ");
		sql.append("			,WTP.REC_HINMEI_KANA_NA ");
		sql.append("			,WTP.KIKAKU_KANJI_NA ");
		sql.append("			,WTP.MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,WTP.ZEI_KB ");
		sql.append("			,WTP.ERR_KB ");
		sql.append("			,WTP.BUNRUI2_CD ");
		sql.append("			,WTP.TEIKAN_FG ");
		sql.append("			,WTP.ZEI_RT ");
		sql.append("			,WTP.SEASON_ID ");
		sql.append("			,WTP.SYOHI_KIGEN_DT ");
		sql.append("			,WTP.CARD_FG ");
// Add 2016.12.02 #2960 Li.Sheng (S)
		sql.append("			,WTP.HANBAI_TANI ");
		sql.append("			,WTP.KEIRYOKI_NM ");
		sql.append("			,WTP.PLU_HANEI_TIME ");
		sql.append("			,WTP.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("			,WTP.LABEL_SEIBUN ");
		sql.append("			,WTP.LABEL_HOKAN_HOHO ");
		sql.append("			,WTP.LABEL_TUKAIKATA ");
		sql.append("			,WTP.GYOTAI_KB ");
// Add 2016.12.02 #2960 Li.Sheng (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
		sql.append("			,WTP.LABEL_COUNTRY_NA ");
// #3049 Add 2016.12.09 Li.Sheng (E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,WTP.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		sql.append("		FROM ");
		sql.append("			WK_TEC_PLU WTP ");
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("					SELECT ");
		sql.append("						 IPT.TENPO_CD");
		//add 2016.08.31 nv.cuong(S)
		//sql.append("						,ITP.TANPIN_CD ");
		sql.append("						,IPT.SYOHIN_CD ");
		//add 2016.08.31 nv.cuong(E)
		sql.append("					FROM ");
		//add 2016.08.31 nv.cuong(S)
		//sql.append("						IF_TEC_PLU ITP ");
		sql.append("						IF_PLU_TANPIN IPT ");
		//add 2016.08.31 nv.cuong(E)
		sql.append("					WHERE ");
		//add 2016.08.31 nv.cuong(S)
		//sql.append("						ITP.DATA_KB	= '" + mst000102_IfConstDictionary.DATA_KB_SHINKI_TEISEI + "' ");
		sql.append("						IPT.TOROKU_ID	= '" + mst000102_IfConstDictionary.TOROKU_ID + "' ");
		//add 2016.08.31 nv.cuong(E)
		sql.append("				) IPT ");
		sql.append("				ON ");
		//add 2016.08.31 nv.cuong(S)
		//sql.append("					WTP.SYOHIN_CD		= ITP.TANPIN_CD			AND ");
		sql.append("					WTP.SYOHIN_CD		= IPT.SYOHIN_CD			AND ");
		//add 2016.08.31 nv.cuong(E)
		sql.append("					TRIM(WTP.TENPO_CD)	= TRIM(IPT.TENPO_CD) ");
		sql.append("		) WTP ");
		sql.append("		ON ");
		sql.append("			( ");
		sql.append("			DTPS.SYOHIN_CD	= WTP.SYOHIN_CD	AND ");
		sql.append("			DTPS.TENPO_CD	= WTP.TENPO_CD ");
		sql.append("			) ");
		sql.append("	WHEN MATCHED THEN ");
		sql.append("		UPDATE ");
		sql.append("			SET ");
		sql.append("				 DTPS.BUNRUI1_CD			= WTP.BUNRUI1_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (S)
		sql.append("				,DTPS.OLD_SYOHIN_CD 		= WTP.OLD_SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (E)
		sql.append("				,DTPS.GENTANKA_VL			= WTP.GENTANKA_VL ");
		sql.append("				,DTPS.BAITANKA_VL			= WTP.BAITANKA_VL ");
		sql.append("				,DTPS.SIIRESAKI_CD			= WTP.SIIRESAKI_CD ");
		sql.append("				,DTPS.PLU_SEND_DT			= WTP.PLU_SEND_DT ");
		sql.append("				,DTPS.BAIKA_HAISHIN_FG		= WTP.BAIKA_HAISHIN_FG ");
		sql.append("				,DTPS.BUNRUI5_CD			= WTP.BUNRUI5_CD ");
		sql.append("				,DTPS.REC_HINMEI_KANJI_NA	= WTP.REC_HINMEI_KANJI_NA ");
		sql.append("				,DTPS.REC_HINMEI_KANA_NA	= WTP.REC_HINMEI_KANA_NA ");
		sql.append("				,DTPS.KIKAKU_KANJI_NA		= WTP.KIKAKU_KANJI_NA ");
		sql.append("				,DTPS.MAKER_KIBO_KAKAKU_VL	= WTP.MAKER_KIBO_KAKAKU_VL ");
		sql.append("				,DTPS.ZEI_KB				= WTP.ZEI_KB ");
		sql.append("				,DTPS.ERR_KB				= WTP.ERR_KB ");
		sql.append("				,DTPS.BUNRUI2_CD 			= WTP.BUNRUI2_CD ");
		sql.append("				,DTPS.TEIKAN_FG 			= WTP.TEIKAN_FG ");
		sql.append("				,DTPS.ZEI_RT 				= WTP.ZEI_RT ");
		sql.append("				,DTPS.SEASON_ID 			= WTP.SEASON_ID ");
		sql.append("				,DTPS.SYOHI_KIGEN_DT 		= WTP.SYOHI_KIGEN_DT ");
		sql.append("				,DTPS.CARD_FG 				= WTP.CARD_FG ");
		sql.append("				,DTPS.UPDATE_USER_ID		= '" + userLog.getJobId() + "' ");
		sql.append("				,DTPS.UPDATE_TS				= '" + batchTs + "' ");
// Add 2016.12.02 #2960 Li.Sheng (S)
		sql.append("				,DTPS.HANBAI_TANI 			= WTP.HANBAI_TANI ");
		sql.append("				,DTPS.KEIRYOKI_NM 			= WTP.KEIRYOKI_NM ");
		sql.append("				,DTPS.PLU_HANEI_TIME 			= WTP.PLU_HANEI_TIME ");
		sql.append("				,DTPS.SYOHI_KIGEN_HYOJI_PATTER 			= WTP.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("				,DTPS.LABEL_SEIBUN 			= WTP.LABEL_SEIBUN ");
		sql.append("				,DTPS.LABEL_HOKAN_HOHO 			= WTP.LABEL_HOKAN_HOHO ");
		sql.append("				,DTPS.LABEL_TUKAIKATA 			= WTP.LABEL_TUKAIKATA ");
		sql.append("				,DTPS.GYOTAI_KB 			= WTP.GYOTAI_KB ");
// Add 2016.12.02 #2960 Li.Sheng (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
		sql.append("				,DTPS.LABEL_COUNTRY_NA 			= WTP.LABEL_COUNTRY_NA ");
// #3049 Add 2016.12.09 Li.Sheng (E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("				,DTPS.HANBAI_TANI_EN = WTP.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		sql.append("	WHEN NOT MATCHED THEN ");
		sql.append("		INSERT ");
		sql.append("			( ");
		sql.append("			 BUNRUI1_CD ");
		sql.append("			,SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (S)
		sql.append("			,OLD_SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (E)
		sql.append("			,TENPO_CD ");
		sql.append("			,GENTANKA_VL ");
		sql.append("			,BAITANKA_VL ");
		sql.append("			,SIIRESAKI_CD ");
		sql.append("			,PLU_SEND_DT ");
		sql.append("			,BAIKA_HAISHIN_FG ");
		sql.append("			,BUNRUI5_CD ");
		sql.append("			,REC_HINMEI_KANJI_NA ");
		sql.append("			,REC_HINMEI_KANA_NA ");
		sql.append("			,KIKAKU_KANJI_NA ");
		sql.append("			,MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,ZEI_KB ");
		sql.append("			,ERR_KB ");
		sql.append("			,BUNRUI2_CD ");
		sql.append("			,TEIKAN_FG ");
		sql.append("			,ZEI_RT ");
		sql.append("			,SEASON_ID ");
		sql.append("			,SYOHI_KIGEN_DT ");
		sql.append("			,CARD_FG ");
		sql.append("			,INSERT_USER_ID ");
		sql.append("			,INSERT_TS ");
		sql.append("			,UPDATE_USER_ID ");
		sql.append("			,UPDATE_TS ");
// Add 2016.12.02 #2960 Li.Sheng (S)
		sql.append("			,HANBAI_TANI ");
		sql.append("			,KEIRYOKI_NM ");
		sql.append("			,PLU_HANEI_TIME ");
		sql.append("			,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("			,LABEL_SEIBUN ");
		sql.append("			,LABEL_HOKAN_HOHO ");
		sql.append("			,LABEL_TUKAIKATA ");
		sql.append("			,GYOTAI_KB ");
// Add 2016.12.02 #2960 Li.Sheng (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
		sql.append("			,LABEL_COUNTRY_NA ");
// #3049 Add 2016.12.09 Li.Sheng (E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		sql.append("			) ");
		sql.append("		VALUES ");
		sql.append("			( ");
		sql.append("			 WTP.BUNRUI1_CD ");
		sql.append("			,WTP.SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (S)
		sql.append("			,WTP.OLD_SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (E)
		sql.append("			,WTP.TENPO_CD ");
		sql.append("			,WTP.GENTANKA_VL ");
		sql.append("			,WTP.BAITANKA_VL ");
		sql.append("			,WTP.SIIRESAKI_CD ");
		sql.append("			,WTP.PLU_SEND_DT ");
		sql.append("			,WTP.BAIKA_HAISHIN_FG ");
		sql.append("			,WTP.BUNRUI5_CD ");
		sql.append("			,WTP.REC_HINMEI_KANJI_NA ");
		sql.append("			,WTP.REC_HINMEI_KANA_NA ");
		sql.append("			,WTP.KIKAKU_KANJI_NA ");
		sql.append("			,WTP.MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,WTP.ZEI_KB ");
		sql.append("			,WTP.ERR_KB ");
		sql.append("			,WTP.BUNRUI2_CD ");
		sql.append("			,WTP.TEIKAN_FG ");
		sql.append("			,WTP.ZEI_RT ");
		sql.append("			,WTP.SEASON_ID ");
		sql.append("			,WTP.SYOHI_KIGEN_DT ");
		sql.append("			,WTP.CARD_FG ");
		sql.append("			,'" + userLog.getJobId() + "' ");
		sql.append("			,'" + batchTs + "' ");
		sql.append("			,'" + userLog.getJobId() + "' ");
		sql.append("			,'" + batchTs + "' ");
// Add 2016.12.02 #2960 Li.Sheng (S)	
		sql.append("			,WTP.HANBAI_TANI ");
		sql.append("			,WTP.KEIRYOKI_NM ");
		sql.append("			,WTP.PLU_HANEI_TIME ");
		sql.append("			,WTP.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("			,WTP.LABEL_SEIBUN ");
		sql.append("			,WTP.LABEL_HOKAN_HOHO ");
		sql.append("			,WTP.LABEL_TUKAIKATA ");
		sql.append("			,WTP.GYOTAI_KB ");
// Add 2016.12.02 #2960 Li.Sheng (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
		sql.append("			,WTP.LABEL_COUNTRY_NA ");
// #3049 Add 2016.12.09 Li.Sheng (E)	
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,WTP.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		sql.append("			) ");

		return sql.toString();
	}

	/**
	 * DT_TEC_PLU_SEND(削除)するSQLを取得する
	 *
	 * @return DT_TEC_PLU_SEND(削除)SQL
	 */
	private String getDtTecPluSendDeleteSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	DT_TEC_PLU_SEND DTPS ");
		sql.append("WHERE ");
		sql.append("	EXISTS ");
		sql.append("		( ");
		sql.append("		SELECT ");
		// add 2016.08.31 nv.cuong(S)
		//sql.append("			 ITP.TANPIN_CD ");
		sql.append("			 IPT.SYOHIN_CD ");
		// add 2016.08.31 nv.cuong(E)
		sql.append("		FROM ");
		// add 2016.08.31 nv.cuong(S)
		//sql.append("			IF_TEC_PLU ITP ");
		sql.append("			IF_PLU_TANPIN IPT ");
		// add 2016.08.31 nv.cuong(E)
		sql.append("		WHERE ");
		// add 2016.08.31 nv.cuong(S)
		//sql.append("			ITP.DATA_KB			= '" + mst000102_IfConstDictionary.DATA_KB_SAKUJO + "'					AND ");
		sql.append("			IPT.TOROKU_ID			= '" + mst000102_IfConstDictionary.TOROKU_ID_DEL + "'					AND ");
		//sql.append("			DTPS.SYOHIN_CD		= ITP.TANPIN_CD			AND ");
		sql.append("			DTPS.SYOHIN_CD		= IPT.SYOHIN_CD			AND ");
		//sql.append("			TRIM(DTPS.TENPO_CD)	= TRIM(ITP.TENPO_CD) ");
		sql.append("			TRIM(DTPS.TENPO_CD)	= TRIM(IPT.TENPO_CD) ");
		// add 2016.08.31 nv.cuong(E)
		sql.append("		) ");

		return sql.toString();
	}

/********** 共通処理 **********/

	/**
	 * ユーザーログとバッチログにログを出力します。
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message) {
		String jobId = userLog.getJobId();

		switch (level) {
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
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
		batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}
