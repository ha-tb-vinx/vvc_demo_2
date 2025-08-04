package mdware.master.batch.process.MSTB995;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst001101_TeikanKbDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB995030_IfKeiryokiSyohinCreate.java クラス</p>
 * <p>説明　: WK_IF_計量器の内容を、IF_計量器マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.25) T.Ooshiro [CUS00065] ランドローム様対応 計量器インターフェイス仕様変更対応
 * @version 3.01 (2013.12.27) T.Ooshiro [CUS00065] 計量器インターフェイス仕様変更対応 結合テスト課題対応 No036
 *
 */
public class MSTB995030_IfKeiryokiSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF = "IF_KEIRYOKI"; // IF_計量器マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** 添加物名桁数(バイト数) */
	private static final String TENKABUTU_NA_LENGTH = "48";
	/** 分類１コード 桁数 */
	private static final String BUNRUI1_CD_LENGTH = "3";
	/** 存在フラグ：１（固定値） */
	private static final String CONST_SONZAI_FG = "0";
	/** バー区分：０４（固定値） */
	private static final String CONST_BAR_KB = "04";
	/** 品名文字フラグ：０１（固定値） */
	private static final String CONST_HINMEI_FG= "01";
	/** 計量区分：０ 定額（固定値） */
	private static final String CONST_TEIKAN_KB_TEIGAKU = "1";
	/** 計量区分：１ 計量（固定値） */
	private static final String CONST_TEIKAN_KB_KEIRYO = "0";
	/** 添加物フラグ：０２（固定値） */
	private static final String CONST_TENKABUTU_FG = "02";
	/** 商品コード 開始位置 */
	private static final String SYOHIN_CD_ST_COLUMN = "1";
	/** 商品コード 桁数 */
	private static final String SYOHIN_CD_LENGTH = "8";
	/** 商品コード パディング後 桁数 */
	private static final String SYOHIN_CD_PAD_LENGTH = "13";
	/** 値付器名 開始位置 */
	private static final String NETSUKEKI_NA_ST_COLUMN = "1";
	/** 値付器名 桁数 */
	private static final String NETSUKEKI_NA_LENGTH = "48";
	/** レシート品名（漢字） 開始位置 */
	private static final String REC_HINMEI_KANJI_NA_ST_COLUMN = "1";
	/** レシート品名（漢字） 桁数 */
	private static final String REC_HINMEI_KANJI_NA_LENGTH = "24";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB995030_IfKeiryokiSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB995030_IfKeiryokiSyohinCreate() {
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

			// IF_計量器マスタのTRUNCATE
			writeLog(Level.INFO_INT, "IF_計量器マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_計量器マスタを削除処理を終了します。");

			// IF_計量器マスタの登録(新規分・修正分)
			writeLog(Level.INFO_INT, "IF_計量器マスタ(新規分・修正分)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfKeiryokiInsertSql());
			writeLog(Level.INFO_INT, "IF_計量器マスタ(新規分・修正分)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_計量器マスタ(新規分・修正分)登録処理（WK→IF）を終了します。");

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
	 * IF_KEIRYOKIを登録するSQLを取得する(新規分・修正分)
	 * 
	 * @return IF_KEIRYOKI登録SQL(新規分・修正分)
	 */
	private String getIfKeiryokiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_KEIRYOKI ");
		sql.append("	( ");
		sql.append("	 BUNRUI1_CD ");
		sql.append("	,SYOHIN_YOBIDASI ");
		sql.append("	,SONZAI_FG ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,BAR_KB ");
		sql.append("	,HINMEI_FG ");
		sql.append("	,HINMEI_NA ");
		sql.append("	,TEIKAN_KB ");
		sql.append("	,TEIKAN_TANI_KB ");
		sql.append("	,TEIKAN_WEIGHT_QT ");
		sql.append("	,FUTAI_WEIGHT_QT ");
		sql.append("	,KAKOBI_PRINT_KB ");
		sql.append("	,SYOMIKIKAN_KB ");
		sql.append("	,SYOMIKIKAN_VL ");
		sql.append("	,KAKOJIKOKU_PRINT_KB ");
		sql.append("	,SENTAKU_COMMENT1_CD ");
		sql.append("	,SENTAKU_COMMENT2_CD ");
		sql.append("	,TENKABUTU_YOBIDASI ");
		sql.append("	,REC_HINMEI_KANJI_NA ");
		sql.append("	,TRACEABILITY_FG ");
		sql.append("	,BUNRUI_CD ");
		sql.append("	,CATEGORY_CD ");
		sql.append("	,TENKABUTU_01_FG ");
		sql.append("	,TENKABUTU_01_NA ");
		sql.append("	,TENKABUTU_02_FG ");
		sql.append("	,TENKABUTU_02_NA ");
		sql.append("	,TENKABUTU_03_FG ");
		sql.append("	,TENKABUTU_03_NA ");
		sql.append("	,TENKABUTU_04_FG ");
		sql.append("	,TENKABUTU_04_NA ");
		sql.append("	,TENKABUTU_05_FG ");
		sql.append("	,TENKABUTU_05_NA ");
		sql.append("	,TENKABUTU_06_FG ");
		sql.append("	,TENKABUTU_06_NA ");
		sql.append("	,TENKABUTU_07_FG ");
		sql.append("	,TENKABUTU_07_NA ");
		sql.append("	,TENKABUTU_08_FG ");
		sql.append("	,TENKABUTU_08_NA ");
		sql.append("	,TENKABUTU_09_FG ");
		sql.append("	,TENKABUTU_09_NA ");
		sql.append("	,TENKABUTU_10_FG ");
		sql.append("	,TENKABUTU_10_NA ");
		sql.append("	,SYOHIN_UPDATE_TS ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 LPAD(TRIM(WIK.BUNRUI1_CD), " + BUNRUI1_CD_LENGTH + ", '" + PADDING_STR + "')                  AS BUNRUI1_CD ");
		sql.append("	,WIK.SYOHIN_YOBIDASI                                                                           AS SYOHIN_YOBIDASI ");
		sql.append("	,'" + CONST_SONZAI_FG + "'                                                                     AS SONZAI_FG ");
		sql.append("	,RPAD(SUBSTR(WIK.SYOHIN_CD, " + SYOHIN_CD_ST_COLUMN + ", " + SYOHIN_CD_LENGTH + "), " + SYOHIN_CD_PAD_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("                                                                                                   AS SYOHIN_CD ");
		sql.append("	,'" + CONST_BAR_KB + "'                                                                        AS BAR_KB ");
		sql.append("	,'" + CONST_HINMEI_FG + "'                                                                     AS HINMEI_FG ");
		sql.append("	,SUBSTRB(WIK.NETSUKEKI_NA, " + NETSUKEKI_NA_ST_COLUMN + ", " + NETSUKEKI_NA_LENGTH + ")        AS HINMEI_NA ");
		sql.append("	,CASE WIK.TEIKAN_KB ");
		sql.append("		WHEN '" + mst001101_TeikanKbDictionary.TEIKAN.getCode() + "' ");
		sql.append("			THEN '" + CONST_TEIKAN_KB_TEIGAKU + "' ");
		sql.append("		WHEN '" + mst001101_TeikanKbDictionary.TEIKAN.getCode() + "' ");
		sql.append("			THEN '" + CONST_TEIKAN_KB_KEIRYO + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TEIKAN_KB ");
		sql.append("	,WIK.TEIKAN_TANI_KB                                                                            AS TEIKAN_TANI_KB ");
		sql.append("	,WIK.TEIKAN_WEIGHT_QT                                                                          AS TEIKAN_WEIGHT_QT ");
		sql.append("	,WIK.FUTAI_WEIGHT_QT                                                                           AS FUTAI_WEIGHT_QT ");
		sql.append("	,WIK.KAKOBI_PRINT_KB                                                                           AS KAKOBI_PRINT_KB ");
		sql.append("	,WIK.SYOMIKIKAN_KB                                                                             AS SYOMIKIKAN_KB ");
		sql.append("	,WIK.SYOMIKIKAN_VL                                                                             AS SYOMIKIKAN_VL ");
		sql.append("	,WIK.KAKOJIKOKU_PRINT_KB                                                                       AS KAKOJIKOKU_PRINT_KB ");
		sql.append("	,WIK.SENTAKU_COMMENT_CD                                                                        AS SENTAKU_COMMENT1_CD ");
		sql.append("	,WIK.HOZON_ONDOTAI_KB                                                                          AS SENTAKU_COMMENT2_CD ");
		sql.append("	,WIK.SYOHIN_YOBIDASI                                                                           AS TENKABUTU_YOBIDASI ");
		sql.append("	,SUBSTRB(WIK.REC_HINMEI_KANJI_NA, " + REC_HINMEI_KANJI_NA_ST_COLUMN + ", " + REC_HINMEI_KANJI_NA_LENGTH + ") ");
		sql.append("                                                                                                   AS REC_HINMEI_KANJI_NA  ");
		sql.append("	,WIK.TRACEABILITY_FG                                                                           AS TRACEABILITY_FG ");
		sql.append("	,''                                                                                            AS BUNRUI_CD ");
		sql.append("	,''                                                                                            AS CATEGORY_CD ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  0 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_01_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  0 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_01_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  1 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_02_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  1 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_02_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  2 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_03_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  2 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_03_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  3 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_04_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  3 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_04_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  4 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_05_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  4 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_05_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  5 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_06_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  5 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_06_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  6 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_07_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  6 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_07_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  7 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_08_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  7 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_08_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  8 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_09_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  8 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_09_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  9 + 1, " + TENKABUTU_NA_LENGTH + ") IS NOT NULL ");
		sql.append("			THEN '" + CONST_TENKABUTU_FG + "' ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                                                           AS TENKABUTU_10_FG ");
		sql.append("	,SUBSTRB(WIK.TENKABUTU_NA, " + TENKABUTU_NA_LENGTH + " *  9 + 1, " + TENKABUTU_NA_LENGTH + ")  AS TENKABUTU_10_NA ");
		sql.append("	,WIK.SYOHIN_UPDATE_TS                                                                          AS SYOHIN_UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                  AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                                             AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                  AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                                             AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_IF_KEIRYOKI WIK ");
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 ZIK.BUNRUI1_CD ");
		sql.append("			,ZIK.SYOHIN_CD ");
		sql.append("			,ZIK.SYOHIN_YOBIDASI ");
		sql.append("			,ZIK.BUNRUI2_CD ");
		sql.append("			,ZIK.BUNRUI5_CD ");
		sql.append("			,ZIK.NETSUKEKI_NA ");
		sql.append("			,ZIK.TEIKAN_KB ");
		sql.append("			,ZIK.TEIKAN_TANI_KB ");
		sql.append("			,ZIK.TEIKAN_WEIGHT_QT ");
		sql.append("			,ZIK.FUTAI_WEIGHT_QT ");
		sql.append("			,ZIK.KAKOBI_PRINT_KB ");
		sql.append("			,ZIK.SYOMIKIKAN_KB ");
		sql.append("			,ZIK.SYOMIKIKAN_VL ");
		sql.append("			,ZIK.KAKOJIKOKU_PRINT_KB ");
		sql.append("			,ZIK.SENTAKU_COMMENT_CD ");
		sql.append("			,ZIK.HOZON_ONDOTAI_KB ");
		sql.append("			,ZIK.REC_HINMEI_KANJI_NA ");
		sql.append("			,ZIK.TRACEABILITY_FG ");
		sql.append("			,ZIK.TENKABUTU_NA ");
		sql.append("			,ZIK.SYOHIN_UPDATE_TS ");
		sql.append("			,ZIK.ERR_KB ");
		sql.append("		FROM ");
		sql.append("			ZEN_IF_KEIRYOKI ZIK ");
		sql.append("		WHERE ");
		sql.append("			ZIK.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");
		sql.append("		) ZIK ");
		sql.append("		ON ");
		sql.append("			WIK.BUNRUI1_CD	= ZIK.BUNRUI1_CD	AND ");
		sql.append("			WIK.SYOHIN_CD	= ZIK.SYOHIN_CD ");
		sql.append("WHERE ");
		sql.append("	WIK.ERR_KB			 = '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'	AND ");
		sql.append("	( ");
		sql.append("		NVL(WIK.BUNRUI1_CD, ' ')			<>	NVL(ZIK.BUNRUI1_CD, ' ')			OR ");
		sql.append("		NVL(WIK.SYOHIN_CD, ' ')				<>	NVL(ZIK.SYOHIN_CD, ' ')				OR ");
		sql.append("		NVL(WIK.SYOHIN_YOBIDASI, ' ')		<>	NVL(ZIK.SYOHIN_YOBIDASI, ' ')		OR ");
		sql.append("		NVL(WIK.BUNRUI2_CD, ' ')			<>	NVL(ZIK.BUNRUI2_CD, ' ')			OR ");
		sql.append("		NVL(WIK.BUNRUI5_CD, ' ')			<>	NVL(ZIK.BUNRUI5_CD, ' ')			OR ");
		sql.append("		NVL(WIK.NETSUKEKI_NA, ' ')			<>	NVL(ZIK.NETSUKEKI_NA, ' ')			OR ");
		sql.append("		NVL(WIK.TEIKAN_KB, ' ')				<>	NVL(ZIK.TEIKAN_KB, ' ')				OR ");
		sql.append("		NVL(WIK.TEIKAN_TANI_KB, ' ')		<>	NVL(ZIK.TEIKAN_TANI_KB, ' ')		OR ");
		sql.append("		NVL(WIK.TEIKAN_WEIGHT_QT, 0)		<>	NVL(ZIK.TEIKAN_WEIGHT_QT, 0)		OR ");
		sql.append("		NVL(WIK.FUTAI_WEIGHT_QT, 0)			<>	NVL(ZIK.FUTAI_WEIGHT_QT, 0)			OR ");
		sql.append("		NVL(WIK.KAKOBI_PRINT_KB, ' ')		<>	NVL(ZIK.KAKOBI_PRINT_KB, ' ')		OR ");
		sql.append("		NVL(WIK.SYOMIKIKAN_KB, ' ')			<>	NVL(ZIK.SYOMIKIKAN_KB, ' ')			OR ");
		sql.append("		NVL(WIK.SYOMIKIKAN_VL, 0)			<>	NVL(ZIK.SYOMIKIKAN_VL, 0)			OR ");
		sql.append("		NVL(WIK.KAKOJIKOKU_PRINT_KB, ' ')	<>	NVL(ZIK.KAKOJIKOKU_PRINT_KB, ' ')	OR ");
		sql.append("		NVL(WIK.SENTAKU_COMMENT_CD, ' ')	<>	NVL(ZIK.SENTAKU_COMMENT_CD, ' ')	OR ");
		sql.append("		NVL(WIK.HOZON_ONDOTAI_KB, ' ')		<>	NVL(ZIK.HOZON_ONDOTAI_KB, ' ')		OR ");
		sql.append("		NVL(WIK.REC_HINMEI_KANJI_NA, ' ')	<>	NVL(ZIK.REC_HINMEI_KANJI_NA, ' ')	OR ");
		sql.append("		NVL(WIK.TRACEABILITY_FG, ' ')		<>	NVL(ZIK.TRACEABILITY_FG, ' ')		OR ");
		sql.append("		NVL(WIK.TENKABUTU_NA, ' ')			<>	NVL(ZIK.TENKABUTU_NA, ' ')			OR ");
		sql.append("		NVL(WIK.SYOHIN_UPDATE_TS, ' ')		<>	NVL(ZIK.SYOHIN_UPDATE_TS, ' ') ");
		sql.append("	) ");

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

