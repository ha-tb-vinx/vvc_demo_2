package mdware.master.batch.process.MSTB992;

import java.sql.SQLException;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB992020_IfPosCategoryCreate.java クラス</p>
 * <p>説明　: WK_POSラインメンテの内容を、IF_POSラインメンテに取込む<br>
 *            WK_POSクラスメンテの内容を、IF_POSクラスメンテに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.26) T.Ooshiro [CUS00063] ランドローム様対応 POSインターフェイス仕様変更対応
 * @version 3.01 (2013.12.17) T.Ooshiro [CUS00063] POSインターフェイス仕様変更対応  結合テスト課題対応 №017,№018
 * @version 3.02 (2014.02.10) S.Arakawa [シス0018] 項目セット内容変更
 * @version 3.03 (2022.05.05) SIEU.D #6553
 *
 */
public class MSTB992020_IfPosCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDt = null;

	// テーブル
	private static final String TABLE_IF_LINE = "IF_POS_LINE"; // IF_POSラインメンテ
	private static final String TABLE_IF_CLASS = "IF_POS_CLASS"; // IF_POSクラスメンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 値入率(定数) */
	private static final String NEIRER_CONST = "+000.0";
	private static final String NEIRER_CONST_PLUS_ONE = "+001.0";
	/** 実行種別(定数) */
	private static final String JIKKO_KB = "00";
	/** 税ステータス(定数) */
	private static final String STATUS_1_CD_CONST = "00000010";
	/** ステータス(定数) */
	private static final String STATUS_2_CD_CONST = "00000010";
	/** 定番単価変更禁止フラグ(定数) */
	private static final String KAKAKU_HENKO_KINSHI_FG_CONST = "0";
	/** 特定クラスフラグ(定数) */
	private static final String TOKUTEI_CLASS_FG_CONST = "0";

	/** リンクコード 桁数 */
	private static final String LINK_CD_LENGTH = "6";
	/** クラスコード 桁数 */
	private static final String CLASS_CD_LENGTH = "6";
	/** 部門コード 桁数 */
	private static final String LINK_BUMON_CD_LENGTH = "4";
	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** パディング文字 */
	private static final String PADDING_STR_BLANK = " ";
	/** ライン編集(1) 開始カラム */
	private static final String LINE_EDIT_FIRST_ST_COLUMN = "1";
	/** ライン編集(1) 桁数 */
	private static final String LINE_EDIT_FIRST_LENGTH = "2";
	/** ライン編集(2) 開始カラム */
	private static final String LINE_EDIT_SECOND_ST_COLUMN = "4";
	/** ライン編集(2) 桁数 */
	private static final String LINE_EDIT_SECOND_LENGTH = "2";
	/** 店舗コード 桁数 */
	//private static final String TENPO_CD_LENGTH = "5";
	private static final String TENPO_CD_LENGTH = "6";

	/** Oracle正規表現 日付パターン */
	private static final String REGEXP_PATTERN_YYYYMMDD = "([[:digit:]]{4})([[:digit:]]{2})([[:digit:]]{2})";
	/** Oracle正規表現 日付変換後 */
	private static final String REGEXP_REPLACED_YYYYMMDD =  "\\1/\\2/\\3";
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992020_IfPosCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992020_IfPosCategoryCreate() {
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

			//バッチ日付取得
			String batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

			//商品分類体系作成日取得
			String createDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_SAKUSEI_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。(バッチ処理日≠商品分類体系作成日)");

				return;
			}
			writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");

			// IF_POSラインメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_POSラインメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_LINE);
			writeLog(Level.INFO_INT, "IF_POSラインメンテを削除処理を終了します。");

			// IF_POSクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_CLASS);
			writeLog(Level.INFO_INT, "IF_POSクラスメンテを削除処理を終了します。");

			// IF_POSラインメンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_POSラインメンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosLineInsertSql());
			writeLog(Level.INFO_INT, "IF_POSラインメンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSラインメンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POSラインメンテ(削除)の登録
			writeLog(Level.INFO_INT, "IF_POSラインメンテ(削除)登録処理（ZEN→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosLineDelInsertSql());
			writeLog(Level.INFO_INT, "IF_POSラインメンテ(削除)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSラインメンテ(削除)登録処理（ZEN→IF）を終了します。");

			db.commit();

			// IF_POSクラスメンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosClassInsertSql());
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POSクラスメンテ(削除)の登録
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(削除)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosClassDelInsertSql());
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(削除)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(削除)登録処理（WK→IF）を終了します。");

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
	 * IF_POS_LINE(新規・訂正)を登録するSQLを取得する
	 *
	 * @return IF_POS_LINE(新規・訂正)登録SQL
	 */
	private String getIfPosLineInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_LINE ");
		sql.append("	( ");
		sql.append("	 EIGYO_DT_IF ");
		sql.append("	,TENPO_CD ");
		sql.append("	,DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,LINE_CD ");
		sql.append("	,LINK_BUMON_CD ");
		sql.append("	,LINE_KANJI_NA ");
		sql.append("	,LINE_ANK_NA ");
		sql.append("	,NEIRER ");
		sql.append("	,JIKKO_DT ");
		sql.append("	,JIKKO_TM ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 REGEXP_REPLACE(" + batchDt + ", '" + REGEXP_PATTERN_YYYYMMDD + "', '" + REGEXP_REPLACED_YYYYMMDD + "') ");
		sql.append("	                                                                          AS EIGYO_DT_IF ");
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + mst000102_IfConstDictionary.DATA_KB_SHINKI_TEISEI + "'              AS DATA_KB ");
		sql.append("	,'" + JIKKO_KB + "'                                                       AS JIKKO_KB ");
		sql.append("	,LPAD(SUBSTR(WPL.BUNRUI2_CD, " + LINE_EDIT_FIRST_ST_COLUMN + ", " + LINE_EDIT_FIRST_LENGTH + ") || SUBSTR(WPL.BUNRUI2_CD, " + LINE_EDIT_SECOND_ST_COLUMN + ", " + LINE_EDIT_SECOND_LENGTH + "), " + LINK_CD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                          AS LINE_CD ");
		sql.append("	,LPAD(TRIM(WPL.BUNRUI1_CD), " + LINK_BUMON_CD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                          AS LINK_BUMON_CD ");
		sql.append("	,WPL.KANJI_NA                                                             AS LINE_KANJI_NA ");
		sql.append("	,WPL.KANA_NA                                                              AS LINE_ANK_NA ");
//		sql.append("	,'" + NEIRER_CONST + "'                                                   AS NEIRER ");
		sql.append("	,'" + NEIRER_CONST_PLUS_ONE + "'                                          AS NEIRER ");
		sql.append("	,'" + PADDING_STR_BLANK + "'                                              AS JIKKO_DT ");
		sql.append("	,'" + PADDING_STR_BLANK + "'                                              AS JIKKO_TM ");
		sql.append("	,'" + userLog.getJobId() + "'                                             AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                        AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                             AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                        AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	 WK_POS_LINE WPL ");
		sql.append("	 LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 ZPL.BUNRUI2_CD ");
		sql.append("			,ZPL.BUNRUI1_CD ");
		sql.append("			,ZPL.KANJI_NA ");
		sql.append("			,ZPL.KANA_NA ");
		sql.append("		FROM ");
		sql.append("			ZEN_POS_LINE ZPL ");
		sql.append("		) ZPL ");
		sql.append("		ON ");
		sql.append("			ZPL.BUNRUI2_CD	= WPL.BUNRUI2_CD ");
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + batchDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + batchDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRT ");
		sql.append("WHERE ");
		sql.append("	NVL(WPL.BUNRUI1_CD, ' ')	<> NVL(ZPL.BUNRUI1_CD, ' ')	OR ");
		sql.append("	NVL(WPL.KANJI_NA, ' ')		<> NVL(ZPL.KANJI_NA, ' ')	OR ");
		sql.append("	NVL(WPL.KANA_NA, ' ')		<> NVL(ZPL.KANA_NA, ' ') ");

		return sql.toString();
	}

	/**
	 * IF_POS_LINE(削除)を登録するSQLを取得する
	 *
	 * @return IF_POS_LINE(削除)登録SQL
	 */
	private String getIfPosLineDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_LINE ");
		sql.append("	( ");
		sql.append("	 EIGYO_DT_IF ");
		sql.append("	,TENPO_CD ");
		sql.append("	,DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,LINE_CD ");
		sql.append("	,LINK_BUMON_CD ");
		sql.append("	,LINE_KANJI_NA ");
		sql.append("	,LINE_ANK_NA ");
		sql.append("	,NEIRER ");
		sql.append("	,JIKKO_DT ");
		sql.append("	,JIKKO_TM ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 REGEXP_REPLACE(" + batchDt + ", '" + REGEXP_PATTERN_YYYYMMDD + "', '" + REGEXP_REPLACED_YYYYMMDD + "') ");
		sql.append("	                                                                          AS EIGYO_DT_IF ");
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + mst000102_IfConstDictionary.DATA_KB_SAKUJO + "'                     AS DATA_KB ");
		sql.append("	,'" + JIKKO_KB + "'                                                       AS JIKKO_KB ");
		sql.append("	,LPAD(SUBSTR(ZPL.BUNRUI2_CD, " + LINE_EDIT_FIRST_ST_COLUMN + ", " + LINE_EDIT_FIRST_LENGTH + ") || SUBSTR(ZPL.BUNRUI2_CD, " + LINE_EDIT_SECOND_ST_COLUMN + ", " + LINE_EDIT_SECOND_LENGTH + "), " + LINK_CD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                          AS LINE_CD ");
		sql.append("	,LPAD(TRIM(ZPL.BUNRUI1_CD), " + LINK_BUMON_CD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                          AS LINK_BUMON_CD ");
		sql.append("	,ZPL.KANJI_NA                                                             AS LINE_KANJI_NA ");
		sql.append("	,ZPL.KANA_NA                                                              AS LINE_ANK_NA ");
//		sql.append("	,'" + NEIRER_CONST + "'                                                   AS NEIRER ");
		sql.append("	,'" + NEIRER_CONST_PLUS_ONE + "'                                          AS NEIRER ");
		sql.append("	,'" + PADDING_STR_BLANK + "'                                              AS JIKKO_DT ");
		sql.append("	,'" + PADDING_STR_BLANK + "'                                              AS JIKKO_TM ");
		sql.append("	,'" + userLog.getJobId() + "'                                             AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                        AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                             AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                        AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	 ZEN_POS_LINE ZPL ");
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + batchDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + batchDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRT ");
		sql.append("WHERE ");
		sql.append("	NOT EXISTS ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			WPL.BUNRUI2_CD ");
		sql.append("		FROM ");
		sql.append("			WK_POS_LINE WPL ");
		sql.append("		WHERE ");
		sql.append("			WPL.BUNRUI2_CD	= ZPL.BUNRUI2_CD ");
		sql.append("		) ");

		return sql.toString();
	}

	/**
	 * IF_POS_CLASSを登録(新規・訂正)するSQLを取得する
	 *
	 * @return IF_POS_CLASS(新規・訂正)登録SQL
	 */
	private String getIfPosClassInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_CLASS ");
		sql.append("	( ");
		sql.append("	 EIGYO_DT_IF ");
		sql.append("	,TENPO_CD ");
		sql.append("	,DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,CLASS_CD ");
		sql.append("	,LINK_LINE_CD ");
		sql.append("	,CLASS_KANJI_NA ");
		sql.append("	,CLASS_ANK_NA ");
		sql.append("	,STATUS_1_CD ");
		sql.append("	,STATUS_2_CD ");
		sql.append("	,KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,TOKUTEI_CLASS_FG ");
		sql.append("	,NEIRER ");
		sql.append("	,JIKKO_DT ");
		sql.append("	,JIKKO_TM ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 REGEXP_REPLACE(" + batchDt + ", '" + REGEXP_PATTERN_YYYYMMDD + "', '" + REGEXP_REPLACED_YYYYMMDD + "') ");
		sql.append("	                                                                          AS EIGYO_DT_IF ");
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + mst000102_IfConstDictionary.DATA_KB_SHINKI_TEISEI + "'              AS DATA_KB ");
		sql.append("	,'" + JIKKO_KB + "'                                                       AS JIKKO_KB ");
		sql.append("	,LPAD(TRIM(WPC.BUNRUI5_CD), " + CLASS_CD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                          AS CLASS_CD ");
		sql.append("	,LPAD(SUBSTR(WPC.BUNRUI2_CD, " + LINE_EDIT_FIRST_ST_COLUMN + ", " + LINE_EDIT_FIRST_LENGTH + ") || SUBSTR(WPC.BUNRUI2_CD, " + LINE_EDIT_SECOND_ST_COLUMN + ", " + LINE_EDIT_SECOND_LENGTH + "), " + LINK_CD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                          AS LINK_LINE_CD ");
		sql.append("	,WPC.KANJI_NA                                                             AS CLASS_KANJI_NA ");
		sql.append("	,WPC.KANA_NA                                                              AS CLASS_ANK_NA ");
		sql.append("	,'" + STATUS_1_CD_CONST + "'                                              AS STATUS_1_CD ");
		sql.append("	,'" + STATUS_2_CD_CONST + "'                                              AS STATUS_2_CD ");
		sql.append("	,'" + KAKAKU_HENKO_KINSHI_FG_CONST + "'                                   AS KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,'" + TOKUTEI_CLASS_FG_CONST + "'                                         AS TOKUTEI_CLASS_FG ");
		sql.append("	,'" + NEIRER_CONST + "'                                                   AS NEIRER ");
		sql.append("	,'" + PADDING_STR_BLANK + "'                                              AS JIKKO_DT ");
		sql.append("	,'" + PADDING_STR_BLANK + "'                                              AS JIKKO_TM ");
		sql.append("	,'" + userLog.getJobId() + "'                                             AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                        AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                             AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                        AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	 WK_POS_CLASS WPC ");
		sql.append("	 LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 ZPC.BUNRUI2_CD ");
		sql.append("			,ZPC.BUNRUI5_CD ");
		sql.append("			,ZPC.KANJI_NA ");
		sql.append("			,ZPC.KANA_NA ");
		sql.append("		FROM ");
		sql.append("			ZEN_POS_CLASS ZPC ");
		sql.append("		) ZPC ");
		sql.append("		ON ");
		sql.append("			ZPC.BUNRUI5_CD	= WPC.BUNRUI5_CD ");
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + batchDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + batchDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRT ");
		sql.append("WHERE ");
		sql.append("	NVL(WPC.BUNRUI2_CD, ' ')	<> NVL(ZPC.BUNRUI2_CD, ' ')	OR ");
		sql.append("	NVL(WPC.KANJI_NA, ' ')		<> NVL(ZPC.KANJI_NA, ' ')	OR ");
		sql.append("	NVL(WPC.KANA_NA, ' ')		<> NVL(ZPC.KANA_NA, ' ') ");

		return sql.toString();
	}

	/**
	 * IF_POS_CLASSを登録(削除)するSQLを取得する
	 *
	 * @return IF_POS_CLASS(削除)登録SQL
	 */
	private String getIfPosClassDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_CLASS ");
		sql.append("	( ");
		sql.append("	 EIGYO_DT_IF ");
		sql.append("	,TENPO_CD ");
		sql.append("	,DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,CLASS_CD ");
		sql.append("	,LINK_LINE_CD ");
		sql.append("	,CLASS_KANJI_NA ");
		sql.append("	,CLASS_ANK_NA ");
		sql.append("	,STATUS_1_CD ");
		sql.append("	,STATUS_2_CD ");
		sql.append("	,KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,TOKUTEI_CLASS_FG ");
		sql.append("	,NEIRER ");
		sql.append("	,JIKKO_DT ");
		sql.append("	,JIKKO_TM ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 REGEXP_REPLACE(" + batchDt + ", '" + REGEXP_PATTERN_YYYYMMDD + "', '" + REGEXP_REPLACED_YYYYMMDD + "') ");
		sql.append("	                                                                          AS EIGYO_DT_IF ");
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + mst000102_IfConstDictionary.DATA_KB_SAKUJO + "'                     AS DATA_KB ");
		sql.append("	,'" + JIKKO_KB + "'                                                       AS JIKKO_KB ");
		sql.append("	,LPAD(TRIM(ZPC.BUNRUI5_CD), " + CLASS_CD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                          AS CLASS_CD ");
		sql.append("	,LPAD(SUBSTR(ZPC.BUNRUI2_CD, " + LINE_EDIT_FIRST_ST_COLUMN + ", " + LINE_EDIT_FIRST_LENGTH + ") || SUBSTR(ZPC.BUNRUI2_CD, " + LINE_EDIT_SECOND_ST_COLUMN + ", " + LINE_EDIT_SECOND_LENGTH + "), " + LINK_CD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                          AS LINK_LINE_CD ");
		sql.append("	,ZPC.KANJI_NA                                                             AS CLASS_KANJI_NA ");
		sql.append("	,ZPC.KANA_NA                                                              AS CLASS_ANK_NA ");
		sql.append("	,'" + STATUS_1_CD_CONST + "'                                              AS STATUS_1_CD ");
		sql.append("	,'" + STATUS_2_CD_CONST + "'                                              AS STATUS_2_CD ");
		sql.append("	,'" + KAKAKU_HENKO_KINSHI_FG_CONST + "'                                   AS KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,'" + TOKUTEI_CLASS_FG_CONST + "'                                         AS TOKUTEI_CLASS_FG ");
		sql.append("	,'" + NEIRER_CONST + "'                                                   AS NEIRER ");
		sql.append("	,'" + PADDING_STR_BLANK + "'                                              AS JIKKO_DT ");
		sql.append("	,'" + PADDING_STR_BLANK + "'                                              AS JIKKO_TM ");
		sql.append("	,'" + userLog.getJobId() + "'                                             AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                        AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                             AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                        AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	 ZEN_POS_CLASS ZPC ");
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + batchDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + batchDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRT ");
		sql.append("WHERE ");
		sql.append("	NOT EXISTS ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			WPC.BUNRUI2_CD ");
		sql.append("		FROM ");
		sql.append("			WK_POS_CLASS WPC ");
		sql.append("		WHERE ");
		sql.append("			WPC.BUNRUI5_CD	= ZPC.BUNRUI5_CD ");
		sql.append("		) ");

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

