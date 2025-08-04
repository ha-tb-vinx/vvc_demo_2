package mdware.master.batch.process.MSTB992;

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
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst007501_ZeiKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * 
 * <p>タイトル: MSTB992050_IfPosTanpinCreate.java クラス</p>
 * <p>説明　: WK_PLU店別商品の内容を、IF_PLU単品メンテに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00  (2014.08.30) M.Ayukawa 海外LAWSON様対応 POS連携対応
 *
 */
public class MSTB992050_IfPosTanpinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDt = null;
	private String yokuBatchDt = null;
	/** システム時刻 */
	private String timeStamp = "";
	/** 作成日 */
	private String sakuseiDt = "";

	// テーブル
	private static final String TABLE_IF = "IF_TEC_PLU"; // IF_PLU単品メンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** 実行種別(定数) */
	private static final String JIKKO_KB = "00";
	/** 値入率(定数) */
	private static final String NEIRER_CONST = "+000.0";
	/** 単品識別コード(定数) */
	private static final String TANPIN_CD_SHIKIBETSU_CONST = "0";
	/** カテゴリコード(定数) */
	private static final String CATEGORY_CD_CONST = "000000";
	/** 単品区分 NON-PLU(定数) */
	private static final String TANPIN_KB_NON_PLU_CONST = "1";
	/** 単品区分 PLU(定数) */
	private static final String TANPIN_KB_PLU_CONST = "0";
	/** ＪＡＮラベルコード(定数) */
	private static final String JAN_LABEL_CD_CONST = "0000000000000";
	/** ＪＡＮラベルコード桁数(定数) */
	private static final String JAN_LABEL_CD_SU_CONST = "00";
	/** メーカーコード(定数) */
	private static final String MAKER_CD_CONST = "0000000";
	/** 売価配信フラグ オン (定数) */
	private static final String BAIKA_HAISHIN_FG_ON_CONST = "1";
	/** 売価配信フラグ オフ (定数) */
	private static final String BAIKA_HAISHIN_FG_OFF_CONST = "0";

	/** 実売単価(定数) */
	private static final String JITSUBAI_TANKA_CONST = "000000";
	/** 原価値入率区分 "1" (定数) */
	private static final String GENKA_KB_ON_CONST = "1";
	/** 原価値入率区分 "0" (定数) */
	private static final String GENKA_KB_OFF_CONST = "0";
	/** 特定単品フラグ (定数) */
	private static final String TOKUTEI_TANPIN_FG_CONST = "0";
	/** 自動割引禁止フラグ (定数) */
	private static final String JIDO_WARI_KINSHI_FG_CONST = "0";
	/** 自動削除禁止フラグ (定数) */
	private static final String JIDO_SAKU_KINSHI_FG_CONST = "0";
	/** 定番単価変更禁止フラグ (定数) */
	private static final String KAKAKU_HENKO_KINSHI_FG_CONST = "0";
	/** 下限売価チェックフラグ (定数) */
	private static final String MIN_BAIKA_CHK_FG_CONST = "0";
	/** 下限売価 (定数) */
	private static final String MIN_BAIKA_VL_CONST = "000000";
	/** ステータス１ 内税 (定数) */
	private static final String STATUS_1_CD_UTIZEI_CONST = "00000010";
	/** ステータス１ 外税 (定数) */
	private static final String STATUS_1_CD_SOTOZEI_CONST = "00000001";
	/** ステータス１ 非課税 (定数) */
	private static final String STATUS_1_CD_HIKAZEI_CONST = "00000000";
	/** ステータス (定数) */
	private static final String STATUS_CD_CONST = "00000000";
	/** 自動値引テーブルNO (定数) */
	private static final String JIDO_NEBIKI_NO_CONST = "00";
	/** 選択精算NO (定数) */
	private static final String SENTAKU_SEISAN_NO_CONST = "01";
	/** 選択精算NO ゼロ (定数) */
	private static final String SENTAKU_SEISAN_NO_ZERO_CONST = "00";
	/** データ集計パターン (定数) */
	private static final String DATA_SYUKEI_PTN_CONST = "3";
	/** データ集計パターン ゼロ (定数) */
	private static final String DATA_SYUKEI_PTN_ZERO_CONST = "0";
	/** 衣料フラグ (定数) */
	private static final String IRYO_FG_CONST = "0";
	/** ストック数量 (定数) */
	private static final String STOCK_QT_CONST = "0";
	/** 自社コード (定数) */
	private static final String JISYA_CD_CONST = "0000000000000";

	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** パディング文字 */
	private static final String PADDING_STR_BLANK = " ";
	/** パディング文字 */
	private static final String PADDING_STR_F = "F";
	/** ゼロ (定数) */
	private static final String CONST_ZERO = "0";

	/** 店舗コード 桁数 */
	private static final String TENPO_CD_LENGTH = "6";  // 2014.05.30 M.Ayukawa 海外LAWSON様対応 POS連携対応
	/** 分類５コード 桁数 */
	private static final String BUNRUI5_CD_LENGTH = "6";
	/** 単品名称（漢字） 開始位置 */
	private static final String HINMEI_KANJI_NA_ST_COLUMN = "1";
	/** 単品名称（漢字） 桁数 */
	private static final String HINMEI_KANJI_NA_LENGTH = "28";
	/** 単品名称（カナ） 開始位置 */
	private static final String HINMEI_KANA_NA_ST_COLUMN = "1";
	/** 単品名称（カナ） 桁数 */
	private static final String HINMEI_KANA_NA_LENGTH = "14";
	/** 規格名称（漢字） 開始位置 */
	private static final String KIKAKU_KANJI_NA_ST_COLUMN = "1";
	/** 規格名称（漢字） 桁数 */
	private static final String KIKAKU_KANJI_NA_LENGTH = "16";
	/** メーカー希望価格 桁数 */
	private static final String MAKER_KIBO_KAKAKU_VL_LENGTH = "7";
	/** 定番単価 桁数 */
	private static final String TEIBAN_TANKA_LENGTH = "7"; // 2014.05.30 M.Ayukawa 海外LAWSON様対応 POS連携対応
	/** 仕入先コード 桁数 */
	private static final String SIIRESAKI_CD_LENGTH = "7";

	/** Oracle正規表現 日付パターン */
	private static final String REGEXP_PATTERN_YYYYMMDD = "([[:digit:]]{4})([[:digit:]]{2})([[:digit:]]{2})";
	/** Oracle正規表現 日付変換後 */
	private static final String REGEXP_REPLACED_YYYYMMDD =  "\\1/\\2/\\3";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992050_IfPosTanpinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992050_IfPosTanpinCreate() {
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

			// 作成日取得
			timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
			sakuseiDt = timeStamp.substring(0, 4) + "/" + timeStamp.substring(4, 6) + "/" + timeStamp.substring(6, 8);

			// IF_PLU単品メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_PLU単品メンテを削除処理を終了します。");

			// IF_PLU単品メンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfTecPluInsertSql());
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_PLU単品メンテ(削除)の登録
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)登録処理（SEND→IF）を開始します。");
			iRec = db.executeUpdate(getIfTecPluDelInsertSql());
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)登録処理（SEND→IF）を終了します。");

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
	 * IF_TEC_PLU(新規・訂正)を登録するSQLを取得する
	 * 
	 * @return IF_TEC_PLU(新規・訂正)登録SQL
	 */
	private String getIfTecPluInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);
		
		sql.append("INSERT /*+ APPEND */ INTO IF_TEC_PLU ");
		sql.append("	( ");
		sql.append("	 EIGYO_DT_IF ");
		sql.append("	,TENPO_CD ");
		sql.append("	,DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,JIKKO_DT ");
		sql.append("	,TANPIN_CD_SHIKIBETSU ");
		sql.append("	,TANPIN_CD ");
		sql.append("	,JISYA_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,CATEGORY_1_CD ");
		sql.append("	,CATEGORY_2_CD ");
		sql.append("	,CATEGORY_3_CD ");
		sql.append("	,HINMEI_KANJI_NA ");
		sql.append("	,HINMEI_KANA_NA ");
		sql.append("	,KIKAKU_KANJI_NA ");
		sql.append("	,TANPIN_KB ");
		sql.append("	,JAN_LABEL_CD ");
		sql.append("	,JAN_LABEL_CD_SU ");
		sql.append("	,MAKER_CD ");
		sql.append("	,MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,TEIBAN_TANKA ");
		sql.append("	,JITSUBAI_TANKA ");
		sql.append("	,GENKA_KB ");
		sql.append("	,GENKA_VL ");
		sql.append("	,NEIRER ");
		sql.append("	,TOKUTEI_TANPIN_FG ");
		sql.append("	,JIDO_WARI_KINSHI_FG ");
		sql.append("	,JIDO_SAKU_KINSHI_FG ");
		sql.append("	,KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,MIN_BAIKA_CHK_FG ");
		sql.append("	,MIN_BAIKA_VL ");
		sql.append("	,STATUS_1_CD ");
		sql.append("	,STATUS_2_CD ");
		sql.append("	,STATUS_3_CD ");
		sql.append("	,STATUS_4_CD ");
		sql.append("	,STATUS_5_CD ");
		sql.append("	,JIDO_NEBIKI_NO ");
		sql.append("	,SENTAKU_SEISAN_NO ");
		sql.append("	,START_DT ");
		sql.append("	,DATA_SYUKEI_PTN ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,IRYO_FG ");
		sql.append("	,STOCK_QT ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 REGEXP_REPLACE(" + batchDt + ", '" + REGEXP_PATTERN_YYYYMMDD + "', '" + REGEXP_REPLACED_YYYYMMDD + "')              AS EIGYO_DT_IF ");
		sql.append("	,LPAD(TRIM(WTP.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                                            AS TENPO_CD ");
		sql.append("	,'" + mst000102_IfConstDictionary.DATA_KB_SHINKI_TEISEI + "'                                                         AS DATA_KB ");
		sql.append("	,'" + JIKKO_KB + "'                                                                                                  AS JIKKO_KB ");
		sql.append("	,'" + sakuseiDt + "'                                                                                                 AS JIKKO_DT ");
		sql.append("	,'" + TANPIN_CD_SHIKIBETSU_CONST + "'                                                                                AS TANPIN_CD_SHIKIBETSU ");
		sql.append("	,WTP.SYOHIN_CD                                                                                                       AS TANPIN_CD ");
		sql.append("	,WTP.SYOHIN_CD                                                                                                       AS JISYA_CD ");
		sql.append("	,LPAD(WTP.BUNRUI5_CD, " + BUNRUI5_CD_LENGTH + ", '" + PADDING_STR + "')                                              AS CLASS_CD ");
		sql.append("	,'" + CATEGORY_CD_CONST + "'                                                                                         AS CATEGORY_1_CD ");
		sql.append("	,'" + CATEGORY_CD_CONST + "'                                                                                         AS CATEGORY_2_CD ");
		sql.append("	,'" + CATEGORY_CD_CONST + "'                                                                                         AS CATEGORY_3_CD ");
		sql.append("	,SUBSTRB(WTP.REC_HINMEI_KANJI_NA, " + HINMEI_KANJI_NA_ST_COLUMN + " , " + HINMEI_KANJI_NA_LENGTH + ")                AS HINMEI_KANJI_NA ");
		sql.append("	,SUBSTRB(WTP.REC_HINMEI_KANA_NA , " + HINMEI_KANA_NA_ST_COLUMN + "  , " + HINMEI_KANA_NA_LENGTH + ")                 AS HINMEI_KANA_NA ");
		sql.append("	,SUBSTRB(WTP.KIKAKU_KANJI_NA    , " + KIKAKU_KANJI_NA_ST_COLUMN + " , " + KIKAKU_KANJI_NA_LENGTH + ")                AS KIKAKU_KANJI_NA ");
		sql.append("	,CASE GET_JAN_SYUBETSU(WTP.SYOHIN_CD)  ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_02 + "' THEN '" + TANPIN_KB_NON_PLU_CONST + "' ");
		sql.append("		ELSE '" + TANPIN_KB_PLU_CONST + "' ");
		sql.append("	 END                                                                                                                 AS TANPIN_KB ");
		sql.append("	,'" + JAN_LABEL_CD_CONST + "'                                                                                        AS JAN_LABEL_CD ");
		sql.append("	,'" + JAN_LABEL_CD_SU_CONST + "'                                                                                     AS JAN_LABEL_CD_SU ");
		sql.append("	,'" + MAKER_CD_CONST + "'                                                                                            AS MAKER_CD ");
		sql.append("	,LPAD(NVL(WTP.MAKER_KIBO_KAKAKU_VL, " + CONST_ZERO + "), " + MAKER_KIBO_KAKAKU_VL_LENGTH + ", '" + PADDING_STR + "') AS MAKER_KIBO_KAKAKU_VL ");
		// 2014.05.30 M.Ayukawa 海外LAWSON様対応 POS連携対応 (S)
		sql.append("	,CASE ");
		sql.append("		WHEN DTPS.SYOHIN_CD	IS NULL ");
		sql.append("			 THEN TRIM(TO_CHAR(WTP.BAITANKA_VL, '0000000.00')) ");
		sql.append("		WHEN DTPS.SYOHIN_CD	IS NOT NULL AND ");
		sql.append("				WTP.PLU_SEND_DT	<> '" + yokuBatchDt + "' ");
		sql.append("			THEN LPAD('" + PADDING_STR_F + "', " + TEIBAN_TANKA_LENGTH + ", '" + PADDING_STR_F + "') ");
		sql.append("		WHEN DTPS.SYOHIN_CD	IS NOT NULL AND ");
		sql.append("				WTP.BAIKA_HAISHIN_FG = '" + BAIKA_HAISHIN_FG_ON_CONST + "' ");
		sql.append("			THEN TRIM(TO_CHAR(WTP.BAITANKA_VL, '0000000.00')) ");
		sql.append("		WHEN DTPS.SYOHIN_CD	IS NOT NULL AND ");
		sql.append("				WTP.BAIKA_HAISHIN_FG = '" + BAIKA_HAISHIN_FG_OFF_CONST + "' ");
		sql.append("			THEN LPAD('" + PADDING_STR_F + "', " + TEIBAN_TANKA_LENGTH + ", '" + PADDING_STR_F + "') ");
		sql.append("	 END                                                                                                                 AS TEIBAN_TANKA ");
		// 2014.05.30 M.Ayukawa 海外LAWSON様対応 POS連携対応 (E)		
		sql.append("	,'" + JITSUBAI_TANKA_CONST + "'                                                                                      AS JITSUBAI_TANKA ");
		sql.append("	,'" + GENKA_KB_ON_CONST + "'                                                                                         AS GENKA_KB ");
		sql.append("	,TRIM(TO_CHAR(WTP.GENTANKA_VL, '000000.00'))                                                                         AS GENKA_VL ");
		sql.append("	,'" + NEIRER_CONST + "'                                                                                              AS NEIRER ");
		sql.append("	,'" + TOKUTEI_TANPIN_FG_CONST + "'                                                                                   AS TOKUTEI_TANPIN_FG ");
		sql.append("	,'" + JIDO_WARI_KINSHI_FG_CONST + "'                                                                                 AS JIDO_WARI_KINSHI_FG ");
		sql.append("	,'" + JIDO_SAKU_KINSHI_FG_CONST + "'                                                                                 AS JIDO_SAKU_KINSHI_FG ");
		sql.append("	,'" + KAKAKU_HENKO_KINSHI_FG_CONST + "'                                                                              AS KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,'" + MIN_BAIKA_CHK_FG_CONST + "'                                                                                    AS MIN_BAIKA_CHK_FG ");
		sql.append("	,'" + MIN_BAIKA_VL_CONST + "'                                                                                        AS MIN_BAIKA_VL ");
		sql.append("	,CASE WTP.ZEI_KB ");
		sql.append("		WHEN '" + mst007501_ZeiKbDictionary.UTIZEI.getCode()  + "' THEN '" + STATUS_1_CD_UTIZEI_CONST + "' ");
		sql.append("		WHEN '" + mst007501_ZeiKbDictionary.SOTOZEI.getCode() + "' THEN '" + STATUS_1_CD_SOTOZEI_CONST + "' ");
		sql.append("		WHEN '" + mst007501_ZeiKbDictionary.HIKAZEI.getCode() + "' THEN '" + STATUS_1_CD_HIKAZEI_CONST + "' ");
		sql.append("	 END                                                                                                                 AS STATUS_1_CD ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                                           AS STATUS_2_CD ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                                           AS STATUS_3_CD ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                                           AS STATUS_4_CD ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                                           AS STATUS_5_CD ");
		sql.append("	,'" + JIDO_NEBIKI_NO_CONST + "'                                                                                      AS JIDO_NEBIKI_NO ");
		sql.append("	,'" + SENTAKU_SEISAN_NO_CONST + "'                                                                                   AS SENTAKU_SEISAN_NO ");
		sql.append("	,REGEXP_REPLACE(" + batchDt + ", '" + REGEXP_PATTERN_YYYYMMDD + "', '" + REGEXP_REPLACED_YYYYMMDD + "')              AS START_DT ");
		sql.append("	,'" + DATA_SYUKEI_PTN_CONST + "'                                                                                     AS DATA_SYUKEI_PTN ");
		sql.append("	,LPAD(TRIM(WTP.SIIRESAKI_CD), " + SIIRESAKI_CD_LENGTH + ", '" + PADDING_STR + "')                                    AS SIIRESAKI_CD ");
		sql.append("	,'" + IRYO_FG_CONST + "'                                                                                             AS IRYO_FG ");
		sql.append("	,'" + STOCK_QT_CONST + "'                                                                                            AS STOCK_QT ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                                                                   AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                                                                   AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_TEC_PLU WTP ");
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 DTPS.BUNRUI1_CD ");
		sql.append("			,DTPS.SYOHIN_CD ");
		sql.append("			,DTPS.TENPO_CD ");
		sql.append("		FROM ");
		sql.append("			DT_TEC_PLU_SEND DTPS ");
		sql.append("		) DTPS ");
		sql.append("		ON ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("			DTPS.BUNRUI1_CD	= WTP.BUNRUI1_CD	AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("			DTPS.SYOHIN_CD	= WTP.SYOHIN_CD		AND ");
		sql.append("			DTPS.TENPO_CD	= WTP.TENPO_CD ");
		sql.append("WHERE ");
		sql.append("	WTP.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'	AND ");
		sql.append("	( ");
		sql.append("		(DTPS.SYOHIN_CD		IS NULL		AND ");
		sql.append("		 WTP.PLU_SEND_DT	= '" + yokuBatchDt + "')	OR ");
		sql.append("		DTPS.SYOHIN_CD		IS NOT NULL ");
		sql.append("	) ");

		return sql.toString();
	}

	/**
	 * IF_TEC_PLU(削除)を登録するSQLを取得する
	 * 
	 * @return IF_TEC_PLU(削除)登録SQL
	 */
	private String getIfTecPluDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_TEC_PLU ");
		sql.append("	( ");
		sql.append("	 EIGYO_DT_IF ");
		sql.append("	,TENPO_CD ");
		sql.append("	,DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,JIKKO_DT ");
		sql.append("	,TANPIN_CD_SHIKIBETSU ");
		sql.append("	,TANPIN_CD ");
		sql.append("	,JISYA_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,CATEGORY_1_CD ");
		sql.append("	,CATEGORY_2_CD ");
		sql.append("	,CATEGORY_3_CD ");
		sql.append("	,HINMEI_KANJI_NA ");
		sql.append("	,HINMEI_KANA_NA ");
		sql.append("	,KIKAKU_KANJI_NA ");
		sql.append("	,TANPIN_KB ");
		sql.append("	,JAN_LABEL_CD ");
		sql.append("	,JAN_LABEL_CD_SU ");
		sql.append("	,MAKER_CD ");
		sql.append("	,MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,TEIBAN_TANKA ");
		sql.append("	,JITSUBAI_TANKA ");
		sql.append("	,GENKA_KB ");
		sql.append("	,GENKA_VL ");
		sql.append("	,NEIRER ");
		sql.append("	,TOKUTEI_TANPIN_FG ");
		sql.append("	,JIDO_WARI_KINSHI_FG ");
		sql.append("	,JIDO_SAKU_KINSHI_FG ");
		sql.append("	,KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,MIN_BAIKA_CHK_FG ");
		sql.append("	,MIN_BAIKA_VL ");
		sql.append("	,STATUS_1_CD ");
		sql.append("	,STATUS_2_CD ");
		sql.append("	,STATUS_3_CD ");
		sql.append("	,STATUS_4_CD ");
		sql.append("	,STATUS_5_CD ");
		sql.append("	,JIDO_NEBIKI_NO ");
		sql.append("	,SENTAKU_SEISAN_NO ");
		sql.append("	,START_DT ");
		sql.append("	,DATA_SYUKEI_PTN ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,IRYO_FG ");
		sql.append("	,STOCK_QT ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 REGEXP_REPLACE(" + batchDt + ", '" + REGEXP_PATTERN_YYYYMMDD + "', '" + REGEXP_REPLACED_YYYYMMDD + "') AS EIGYO_DT_IF ");
		sql.append("	,LPAD(TRIM(DTPS.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                              AS TENPO_CD ");
		sql.append("	,'" + mst000102_IfConstDictionary.DATA_KB_SAKUJO + "'                                                   AS DATA_KB ");
		sql.append("	,'" + JIKKO_KB + "'                                                                                     AS JIKKO_KB ");
		sql.append("	,'" + sakuseiDt + "'                                                                                    AS JIKKO_DT ");
		sql.append("	,'" + TANPIN_CD_SHIKIBETSU_CONST + "'                                                                   AS TANPIN_CD_SHIKIBETSU ");
		sql.append("	,DTPS.SYOHIN_CD                                                                                         AS TANPIN_CD ");
		sql.append("	,'" + JISYA_CD_CONST + "'                                                                               AS JISYA_CD ");
		sql.append("	,LPAD('" + PADDING_STR + "', " + BUNRUI5_CD_LENGTH + ", '" + PADDING_STR + "')                          AS CLASS_CD ");
		sql.append("	,'" + CATEGORY_CD_CONST + "'                                                                            AS CATEGORY_1_CD ");
		sql.append("	,'" + CATEGORY_CD_CONST + "'                                                                            AS CATEGORY_2_CD ");
		sql.append("	,'" + CATEGORY_CD_CONST + "'                                                                            AS CATEGORY_3_CD ");
		sql.append("	,RPAD('" + PADDING_STR_BLANK + "', " + HINMEI_KANJI_NA_LENGTH + ", '" + PADDING_STR_BLANK + "')         AS HINMEI_KANJI_NA ");
		sql.append("	,RPAD('" + PADDING_STR_BLANK + "', " + HINMEI_KANA_NA_LENGTH +  ", '" + PADDING_STR_BLANK + "')         AS HINMEI_KANA_NA ");
		sql.append("	,RPAD('" + PADDING_STR_BLANK + "', " + KIKAKU_KANJI_NA_LENGTH + ", '" + PADDING_STR_BLANK + "')         AS KIKAKU_KANJI_NA ");
		sql.append("	,'" + TANPIN_KB_PLU_CONST + "'                                                                          AS TANPIN_KB ");
		sql.append("	,'" + JAN_LABEL_CD_CONST + "'                                                                           AS JAN_LABEL_CD ");
		sql.append("	,'" + JAN_LABEL_CD_SU_CONST + "'                                                                        AS JAN_LABEL_CD_SU ");
		sql.append("	,'" + MAKER_CD_CONST + "'                                                                               AS MAKER_CD ");
		sql.append("	,LPAD('" + PADDING_STR + "', " + MAKER_KIBO_KAKAKU_VL_LENGTH + ", '" + PADDING_STR + "')                AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,LPAD('" + PADDING_STR + "', " + TEIBAN_TANKA_LENGTH +         ", '" + PADDING_STR + "')                AS TEIBAN_TANKA ");
		sql.append("	,'" + JITSUBAI_TANKA_CONST + "'                                                                         AS JITSUBAI_TANKA ");
		sql.append("	,'" + GENKA_KB_OFF_CONST + "'                                                                           AS GENKA_KB ");
		sql.append("	,TRIM(TO_CHAR(" + PADDING_STR + ", '000000.00'))                                                        AS GENKA_VL ");
		sql.append("	,'" + NEIRER_CONST + "'                                                                                 AS NEIRER ");
		sql.append("	,'" + TOKUTEI_TANPIN_FG_CONST + "'                                                                      AS TOKUTEI_TANPIN_FG ");
		sql.append("	,'" + JIDO_WARI_KINSHI_FG_CONST + "'                                                                    AS JIDO_WARI_KINSHI_FG ");
		sql.append("	,'" + JIDO_SAKU_KINSHI_FG_CONST + "'                                                                    AS JIDO_SAKU_KINSHI_FG ");
		sql.append("	,'" + KAKAKU_HENKO_KINSHI_FG_CONST + "'                                                                 AS KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,'" + MIN_BAIKA_CHK_FG_CONST + "'                                                                       AS MIN_BAIKA_CHK_FG ");
		sql.append("	,'" + MIN_BAIKA_VL_CONST + "'                                                                           AS MIN_BAIKA_VL ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                              AS STATUS_1_CD ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                              AS STATUS_2_CD ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                              AS STATUS_3_CD ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                              AS STATUS_4_CD ");
		sql.append("	,'" + STATUS_CD_CONST + "'                                                                              AS STATUS_5_CD ");
		sql.append("	,'" + JIDO_NEBIKI_NO_CONST + "'                                                                         AS JIDO_NEBIKI_NO ");
		sql.append("	,'" + SENTAKU_SEISAN_NO_ZERO_CONST + "'                                                                 AS SENTAKU_SEISAN_NO ");
		sql.append("	,REGEXP_REPLACE(" + batchDt + ", '" + REGEXP_PATTERN_YYYYMMDD + "', '" + REGEXP_REPLACED_YYYYMMDD + "') AS START_DT ");
		sql.append("	,'" + DATA_SYUKEI_PTN_ZERO_CONST + "'                                                                   AS DATA_SYUKEI_PTN ");
		sql.append("	,LPAD('" + PADDING_STR + "', " + SIIRESAKI_CD_LENGTH + ", '" + PADDING_STR + "')                        AS SIIRESAKI_CD ");
		sql.append("	,'" + IRYO_FG_CONST + "'                                                                                AS IRYO_FG ");
		sql.append("	,'" + STOCK_QT_CONST + "'                                                                               AS STOCK_QT ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                           AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                                                      AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                           AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                                                      AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	DT_TEC_PLU_SEND DTPS ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 WTPS.BUNRUI1_CD ");
		sql.append("			,WTPS.SYOHIN_CD ");
		sql.append("		FROM ");
		sql.append("			WK_TEC_PLU_SYOHIN WTPS ");
		sql.append("		WHERE ");
		sql.append("			WTPS.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_DEL + "' ");
		sql.append("		) WTPS ");
		sql.append("		ON ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("			DTPS.BUNRUI1_CD	= WTPS.BUNRUI1_CD	AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("			DTPS.SYOHIN_CD	= WTPS.SYOHIN_CD ");

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

