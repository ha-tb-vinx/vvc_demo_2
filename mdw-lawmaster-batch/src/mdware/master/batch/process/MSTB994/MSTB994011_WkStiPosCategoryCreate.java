package mdware.master.batch.process.MSTB994;

import java.nio.CharBuffer;
import java.sql.SQLException;
import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * <p>タイトル: MSTB994011_WkStiPosCategoryCreate.java クラス</p>
 * <p>説明　: TMP商品分類体系マスタの内容を、指定日商品分類体系マスタの内容を、WK_指定日POS部門メンテ、WK_指定日POSデプトメンテ、
 * <br>WK_指定日POSクラスメンテ、WK_指定日POSサブクラスメンテに取込む</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.01.16) 新規作成 #1749対応 T.Han
 * @version 1.01 (2017.01.23) N.katou #3870
 */
public class MSTB994011_WkStiPosCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK_STI_POS_BUMON = "WK_STI_POS_BUMON";			// WK指定日POS部門メンテ
	private static final String TABLE_WK_STI_POS_DPT = "WK_STI_POS_DPT";				// WK指定日POSデプトメンテ
	private static final String TABLE_WK_STI_POS_CLASS_FIVI = "WK_STI_POS_CLASS_FIVI";	// WK指定日POSクラスメンテ
	private static final String TABLE_WK_STI_POS_SUBCLASS = "WK_STI_POS_SUBCLASS";		// WK指定日POSサブクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** Prime Group桁数 ライン */
	/** Prime Group 名桁数 ライン */
	private static final int PRIME_GROUP_NA_LENGTH = 30;
	/** 正式名称（漢字）開始カラム */
	private static final String KANJI_NA_START_COLUMN = "1";
	/** 正式名称（漢字）桁数 ライン */
	private static final String KANJI_NA_LENGTH = "30";
	/** Class名 ライン */
	private static final String CLASS_NA_LENGTH = "40";
	/** （POS）金額変更／値引ボタンフラグ*/
	private static final String CHG_VL_NEBIKI_BTN_FG = "3";
	/** 会員カード値引率*/
	private static final String KAIIN_CARD_NEBIKI_RT = "000";
	/** マタニティカード値引率*/
	private static final String MATERNITY_CARD_NEBIKI_RT = "000";
	/** 新規・訂正 登録ID */
	private static final String DEPARTMENT_TYPE = "D";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB994011_WkStiPosCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB994011_WkStiPosCategoryCreate() {
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

			// WK指定日POS部門メンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK指定日POS部門メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_STI_POS_BUMON);
			writeLog(Level.INFO_INT, "WK指定日POS部門メンテ削除処理を終了します。");

			// WK指定日POSデプトメンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK指定日POSデプトメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_STI_POS_DPT);
			writeLog(Level.INFO_INT, "WK指定日POSデプトメンテ削除処理を終了します。");

			// WK指定日POSクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK指定日POSクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_STI_POS_CLASS_FIVI);
			writeLog(Level.INFO_INT, "WK指定日POSクラスメンテ削除処理を終了します。");

			// WK指定日POSサブクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "WK指定日POSサブクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_STI_POS_SUBCLASS);
			writeLog(Level.INFO_INT, "WK指定日POSサブクラスメンテ削除処理を終了します。");

			// WK指定日POS部門メンテの登録
			writeLog(Level.INFO_INT, "WK指定日POS部門メンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkStiPosBumonInsertSql());
			writeLog(Level.INFO_INT, "WK指定日POS部門メンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK指定日POS部門メンテ登録処理（TMP→WK）を終了します。");

			db.commit();

			// WK指定日POSデプトメンテの登録
			writeLog(Level.INFO_INT, "WK指定日POSデプトメンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkStiPosDPTInsertSql());
			writeLog(Level.INFO_INT, "WK指定日POSデプトメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK指定日POSデプトメンテ登録処理（TMP→WK）を終了します。");

			db.commit();

			// WK指定日POSクラスメンテの登録
			writeLog(Level.INFO_INT, "WK指定日POSクラスメンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkStiPosClassFiviInsertSql());
			writeLog(Level.INFO_INT, "WK指定日POSクラスメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK指定日POSクラスメンテ登録処理（TMP→WK）を終了します。");

			db.commit();

			// WK指定日POSサブクラスメンテの登録
			writeLog(Level.INFO_INT, "WK指定日POSサブクラスメンテ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkStiPosSubClassInsertSql());
			writeLog(Level.INFO_INT, "WK指定日POSサブクラスメンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK指定日POSサブクラスメンテ登録処理（TMP→WK）を終了します。");

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
	 *WK指定日POS部門メンテを登録するSQLを取得する
	 *
	 * @return WK指定日POS部門メンテ登録SQL
	 */
	private String getWkStiPosBumonInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_STI_POS_BUMON ");
		sql.append("	( ");
		sql.append("	 DIVISION_CD ");
		sql.append("	,DIVISION_NA ");
		sql.append("	,PRIME_GROUP ");
		sql.append("	,PRIME_GROUP_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		// #3870 2017/2/3 N.katou(S)
//		  sql.append("	 SUBSTRB(LTRIM(TRST.DAIBUNRUI2_CD, '0'), 1, 3) AS DIVISION_CD");
		sql.append("   SUBSTRB(LTRIM(RSST.DAIBUNRUI2_CD, '0'), 1, 3) AS DIVISION_CD");
		// #3870 2017/2/3 N.katou(E)
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") AS DIVISION_NA ");
		// #3870 2017/2/3 N.katou(S)
//		  sql.append("	,SUBSTRB(LTRIM(TRST.DAIBUNRUI1_CD, '0'), 1, 3) AS PRIME_GROUP");
		sql.append("  ,SUBSTRB(LTRIM(RSST.DAIBUNRUI1_CD, '0'), 1, 3) AS PRIME_GROUP");
		// #3870 2017/2/3 N.katou(E)
		sql.append("	,'" + spaces(PRIME_GROUP_NA_LENGTH) + "' AS PRIME_GROUP_NA ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		// #3870 2017/2/3 N.katou(S)
//		  sql.append("	TMP_R_SYOHIN_TAIKEI TRST ");
        sql.append("    R_STI_SYOHIN_TAIKEI RSST ");
		// #3870 2017/2/3 N.katou(E)
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.DAIBUNRUI2, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		// #3870 2017/2/3 N.katou(S)
//		  sql.append("			TRIM(TRST.DAIBUNRUI2_CD) = TRN.CODE_CD ");
	    sql.append("            TRIM(RSST.DAIBUNRUI2_CD) = TRN.CODE_CD ");
		// #3870 2017/2/3 N.katou(E)
		sql.append("GROUP BY ");
		// #3870 2017/2/3 N.katou(S)
//		  sql.append("	 TRST.DAIBUNRUI2_CD ");
        sql.append("     RSST.DAIBUNRUI2_CD ");
//        sql.append("    ,TRST.DAIBUNRUI1_CD ");
		sql.append("	,RSST.DAIBUNRUI1_CD ");
		// #3870 2017/2/3 N.katou(E)
		sql.append("	,TRN.KANJI_NA ");

		return sql.toString();
	}

	/**
	 * WK_STI_POS_DPTを登録するSQLを取得する
	 *
	 * @return WK_STI_POS_DPT登録SQL
	 */
	private String getWkStiPosDPTInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_STI_POS_DPT ");
		sql.append("	( ");
		sql.append("	 DIVISION_CD ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,DEPARTMENT_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		// #3870 2017/2/3 N.katou(S)
//		  sql.append("	 SUBSTRB(LTRIM(TRST.DAIBUNRUI2_CD, '0'), 1, 3) AS DIVISION_CD");
        sql.append("     SUBSTRB(LTRIM(RSST.DAIBUNRUI2_CD, '0'), 1, 3) AS DIVISION_CD");
//        sql.append("    ,SUBSTRB(LTRIM(TRST.BUNRUI1_CD, '0'), 1, 3) AS DEPARTMENT_CD ");
		sql.append("	,SUBSTRB(LTRIM(RSST.BUNRUI1_CD, '0'), 1, 3) AS DEPARTMENT_CD ");
		// #3870 2017/2/3 N.katou(S)
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") AS DEPARTMENT_NA ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
        // #3870 2017/2/3 N.katou(S)
//        sql.append("    TMP_R_SYOHIN_TAIKEI TRST ");
        sql.append("    R_STI_SYOHIN_TAIKEI RSST ");
        // #3870 2017/2/3 N.katou(E)
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		// #3870 2017/2/3 N.katou(S)
//		sql.append("			TRIM(TRST.BUNRUI1_CD)	= TRN.CODE_CD ");
		sql.append("          TRIM(RSST.BUNRUI1_CD)   = TRN.CODE_CD ");
		// #3870 2017/2/3 N.katou(E)
		sql.append("GROUP BY ");
        // #3870 2017/2/3 N.katou(S)
//		  sql.append("	 TRST.BUNRUI1_CD	 ");
        sql.append("     RSST.BUNRUI1_CD     ");
//        sql.append("    ,TRST.DAIBUNRUI2_CD ");
		sql.append("	,RSST.DAIBUNRUI2_CD ");
	      // #3870 2017/2/3 N.katou(E)
		sql.append("	,TRN.KANJI_NA ");

		return sql.toString();
	}

	/**
	 * WK_STI_POS_CLASS_FIVIを登録するSQLを取得する
	 *
	 * @return WK_STI_POS_CLASS_FIVI登録SQL
	 */
	private String getWkStiPosClassFiviInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_STI_POS_CLASS_FIVI ");
		sql.append("	( ");
		sql.append("	 DEPARTMENT_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,CLASS_NA ");
		sql.append("	,DEPARTMENT_TYPE ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		// #3870 2017/2/3 N.katou(S)
//  	  sql.append("	 SUBSTRB(LTRIM(TRST.BUNRUI1_CD, '0'), 1, 3 ) AS DEPARTMENT_CD");
        sql.append("     SUBSTRB(LTRIM(RSST.BUNRUI1_CD, '0'), 1, 3 ) AS DEPARTMENT_CD");
//        sql.append("    ,LTRIM(TRST.BUNRUI2_CD, '0') AS CLASS_CD ");
		sql.append("	,LTRIM(RSST.BUNRUI2_CD, '0') AS CLASS_CD ");
		// #3870 2017/2/3 N.katou(E)
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + CLASS_NA_LENGTH + ") AS CLASS_NA ");
		sql.append("	,'" + DEPARTMENT_TYPE + "' AS DEPARTMENT_TYPE ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
        // #3870 2017/2/3 N.katou(S)
//      sql.append("    TMP_R_SYOHIN_TAIKEI TRST ");
        sql.append("    R_STI_SYOHIN_TAIKEI RSST ");
        // #3870 2017/2/3 N.katou(E)
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		// #3870 2017/2/3 N.katou(S)
//		sql.append("			TRIM(TRST.BUNRUI2_CD)	= TRN.CODE_CD ");
		sql.append("          TRIM(RSST.BUNRUI2_CD)   = TRN.CODE_CD ");
		// #3870 2017/2/3 N.katou(E)
		sql.append("GROUP BY ");
		// #3870 2017/2/3 N.katou(S)
//	 	  sql.append("	 TRST.BUNRUI2_CD	 ");
        sql.append("     RSST.BUNRUI2_CD     ");
//        sql.append("    ,TRST.BUNRUI1_CD ");
		sql.append("	,RSST.BUNRUI1_CD ");
		// #3870 2017/2/3 N.katou(E)
		sql.append("	,TRN.KANJI_NA ");

		return sql.toString();
	}

	/**
	 * WK_STI_POS_SUBCLASSを登録するSQLを取得する
	 *
	 * @return WK_STI_POS_SUBCLASS登録SQL
	 */
	private String getWkStiPosSubClassInsertSql() throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_STI_POS_SUBCLASS ");
		sql.append("	( ");
		sql.append("	 CLASS_CD ");
		sql.append("	,SUBCLASS_CD ");
		sql.append("	,SUBCLASS_NA ");
		sql.append("	,AEON_CARD_NEBIKI_FG ");
		sql.append("	,CHG_VL_NEBIKI_BTN_FG ");
		sql.append("	,KAIIN_CARD_NEBIKI_RT ");
		sql.append("	,MATERNITY_CARD_NEBIKI_RT ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		// #3870 2017/2/3 N.katou(S)
//    	  sql.append("	 LTRIM(TRST.BUNRUI2_CD,'0') AS CLASS_CD ");
        sql.append("     LTRIM(RSST.BUNRUI2_CD,'0') AS CLASS_CD ");
//        sql.append("    ,LTRIM(TRST.BUNRUI5_CD,'0') AS SUBCLASS_CD ");
		sql.append("	,LTRIM(RSST.BUNRUI5_CD,'0') AS SUBCLASS_CD ");
		// #3870 2017/2/3 N.katou(E)
		sql.append("	,SUBSTRB(TRN.KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") AS SUBCLASS_NA ");
		sql.append("	,' ' AS AEON_CARD_NEBIKI_FG ");
		sql.append("	,'" + CHG_VL_NEBIKI_BTN_FG + "' AS CHG_VL_NEBIKI_BTN_FG ");
		sql.append("	,'" + KAIIN_CARD_NEBIKI_RT + "' AS KAIIN_CARD_NEBIKI_RT ");
		sql.append("	,'" + MATERNITY_CARD_NEBIKI_RT + "' AS MATERNITY_CARD_NEBIKI_RT ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
        // #3870 2017/2/3 N.katou(S)
//      sql.append("    TMP_R_SYOHIN_TAIKEI TRST ");
        sql.append("    R_STI_SYOHIN_TAIKEI RSST ");
        // #3870 2017/2/3 N.katou(E)
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
		sql.append("		) TRN ");
		sql.append("		ON ");
		// #3870 2017/2/3 N.katou(S)
//		  sql.append("			TRIM(CONCAT(TRST.SYSTEM_KB, TRST.BUNRUI5_CD))	= TRN.CODE_CD ");
		sql.append("          TRIM(CONCAT(RSST.SYSTEM_KB, RSST.BUNRUI5_CD))   = TRN.CODE_CD ");
		// #3870 2017/2/3 N.katou(E)
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

	/**
	 * @param spaces
	 * @return String
	 */
	private String spaces(int spaces){
		return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
	}

}
