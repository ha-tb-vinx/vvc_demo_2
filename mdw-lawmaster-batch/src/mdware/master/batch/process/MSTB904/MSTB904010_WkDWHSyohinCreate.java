package mdware.master.batch.process.MSTB904;

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
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * 
 * <p>タイトル: MSTB904010_WkDWHSyohinCreate.java クラス</p>
 * <p>説明　: TMP商品マスタの内容を、WK_DWH_商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.08) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 * @version 3.01 (2013.12.12) T.Ooshiro [CUS00059] D3(DWH)システムインターフェイス仕様変更対応 結合テスト課題対応 №005, №006, №007
 * @version 3.02 (2013.12.24) T.Ooshiro [CUS00059] D3(DWH)システムインターフェイス仕様変更対応 チェックディジット対応
 * @version 3.03 (2014.04.14) M.Ayukawa [シス0186] 品名セット内容変更
 *
 */
public class MSTB904010_WkDWHSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK = "WK_DWH_SYOHIN"; // WK_DWH_商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;

	/** 金額項目エラー判定値 */
	private static final String KINGAKU_ERROR_CHECK_VL = "999999";
	/** 定数ZERO */
	private static final String CONST_ZERO = "0";
	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** カテゴリ：ダミー値 */
	private static final String CATEGORY_DUMMY = "      ";
	//2014.04.14 M.Ayukawa [シス0186] 品名セット内容変更 (S)
	/** 商品名称(漢字)：漢字名開始カラム */
	private static final String KANJI_NA_START_COLUMN = "1";
	/** 商品名称(漢字)：漢字名桁数 */
	private static final String KANJI_NA_LENGTH = "40";
	/** 商品名称(漢字)：規格名開始カラム */
	private static final String KANJI_NA_KIKAKU_START_COLUMN = "1";
	/** 商品名称(漢字)：規格名桁数 */
	private static final String KANJI_NA_KIKAKU_LENGTH = "18";
	//2014.04.14 M.Ayukawa [シス0186] 品名セット内容変更 (E)	
	/** 商品名称(漢字)：区切文字 */
	private static final String SPLIT_STRING = "　";
	/** 商品名称(カナ)：カナ名開始カラム */
	private static final String KANA_NA_START_COLUMN = "1";
	/** 商品名称(カナ)：カナ名桁数 */
	private static final String KANA_NA_LENGTH = "30";
	/** 値入率：切上げ用補正値 */
	private static final String NEIRE_RT_ROUNDUP_CORRECTION_VALUE = "10000";
	/** メーカーコード開始カラム：JAN13 */
	private static final String MAKER_CD_START_COLUMN_JAN13 = "3";
	/** メーカーコード桁数：JAN13 */
	private static final String MAKER_CD_LENGTH_JAN13 = "5";
	/** メーカーコード開始カラム：EAN13 */
	private static final String MAKER_CD_START_COLUMN_EAN13 = "3";
	/** メーカーコード桁数：EAN13 */
	private static final String MAKER_CD_LENGTH_EAN13 = "5";
	/** メーカーコード開始カラム：JAN8 */
	private static final String MAKER_CD_START_COLUMN_JAN8 = "8";
	/** メーカーコード桁数：JAN8 */
	private static final String MAKER_CD_LENGTH_JAN8 = "4";
	/** メーカーコード開始カラム：EAN8 */
	private static final String MAKER_CD_START_COLUMN_EAN8 = "8";
	/** メーカーコード桁数：EAN8 */
	private static final String MAKER_CD_LENGTH_EAN8 = "4";
	/** メーカーコード開始カラム：UPC-A */
	private static final String MAKER_CD_START_COLUMN_UPC_A = "3";
	/** メーカーコード桁数：UPC-A */
	private static final String MAKER_CD_LENGTH_UPC_A = "4";
	/** 入数桁数 */
	private static final String IRISU_LENGTH = "3";
	/** 規格名開始カラム */
	private static final String KIKAKU_NA_START_COLUMN = "1";
	/** 規格名桁数 */
	private static final String KIKAKU_NA_LENGTH = "15";
	/** 規格名２開始カラム */
	private static final String KIKAKU_NA_2_START_COLUMN = "1";
	/** 規格名２桁数 */
	private static final String KIKAKU_NA_2_LENGTH = "15";
	/** 任意コード桁数 */
	private static final String NINI_LENGTH = "5";
	/** 特殊日付("99999999") */
	private static final String SPECIAL_DATE = "99999999";

	/** DWHC/Dフラグ */
	private static final String DWH_CD_FG = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.DWH_CD_FG);
	/** 商品コード開始位置 */
	private static final String SYOHIN_CD_ST_COLUMN = "1";
	/** 商品コード桁数 */
	private static final String SYOHIN_CD_LENGTH = "12";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904010_WkDWHSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904010_WkDWHSyohinCreate() {
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

			// WK_DWH_商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "WK_DWH_商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK);
			writeLog(Level.INFO_INT, "WK_DWH_商品マスタを削除処理を終了します。");

			// WK_DWH_商品マスタの登録
			writeLog(Level.INFO_INT, "WK_DWH_商品マスタ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkDwhSyohinInsertSql());
			writeLog(Level.INFO_INT, "WK_DWH_商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_DWH_商品マスタ登録処理（TMP→WK）を終了します。");

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
	 * WK_DWH_SYOHINを登録するSQLを取得する
	 * 
	 * @return WK_DWH_SYOHIN登録SQL
	 */
	private String getWkDwhSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_DWH_SYOHIN ");
		sql.append("	( ");
		sql.append("	 KIGYO_1_CD ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,BUNRUI2_CD ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,CATEGORY_4_CD ");
		sql.append("	,CATEGORY_5_CD ");
		sql.append("	,HINMEI_KANJI_NA ");
		sql.append("	,HINMEI_KANA_NA ");
		sql.append("	,GENTANKA_VL ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,NEIRE_RT ");
		sql.append("	,MAKER_CD ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,ATSUKAI_ST_DT ");
		sql.append("	,ATSUKAI_ED_DT ");
		sql.append("	,ZOKUSEI_1_KB ");
		sql.append("	,ZOKUSEI_2_KB ");
		sql.append("	,ZOKUSEI_3_KB ");
		sql.append("	,ZOKUSEI_4_KB ");
		sql.append("	,ZOKUSEI_5_KB ");
		sql.append("	,COMMENT_1_TX ");
		sql.append("	,COMMENT_2_TX ");
		sql.append("	,COMMENT_3_TX ");
		sql.append("	,COMMENT_4_TX ");
		sql.append("	,COMMENT_5_TX ");
		sql.append("	,DELETE_FG ");
		sql.append("	,ERR_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + mst000102_IfConstDictionary.DWH_COMP_CD + "' AS KIGYO_1_CD ");
		if (DWH_CD_FG.equals(mst000101_ConstDictionary.DWH_CD_ON)) {

			sql.append("	,CASE GET_JAN_SYUBETSU(TRS.SYOHIN_CD) ");
			sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_02 + "' ");
			sql.append("			THEN ");
			sql.append("				SUBSTR(TRS.SYOHIN_CD, " + SYOHIN_CD_ST_COLUMN + ", " + SYOHIN_CD_LENGTH + ") ||  CALC_CHECK_DIGIT(TRS.SYOHIN_CD) ");
			sql.append("		ELSE ");
			sql.append("			TRS.SYOHIN_CD ");
			sql.append("	 END ");
		
		} else {
			sql.append("	,TRS.SYOHIN_CD ");
		}
		sql.append("	,TRS.BUNRUI1_CD ");
		sql.append("	,TRS.BUNRUI2_CD ");
		sql.append("	,TRS.BUNRUI5_CD ");
		sql.append("	,'" + CATEGORY_DUMMY + "' AS CATEGORY_4_CD ");
		sql.append("	,'" + CATEGORY_DUMMY + "' AS CATEGORY_5_CD ");
		sql.append("	,SUBSTRB(TRS.HINMEI_KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") || '" + SPLIT_STRING + "' || SUBSTRB(TRS.KIKAKU_KANJI_NA, " + KANJI_NA_KIKAKU_START_COLUMN + ", " + KANJI_NA_KIKAKU_LENGTH + ") AS HINMEI_KANJI_NA ");
		sql.append("	,SUBSTRB(TRS.HINMEI_KANA_NA, " + KANA_NA_START_COLUMN + ", " + KANA_NA_LENGTH + ") AS HINMEI_KANA_NA ");
		sql.append("	,TRS.GENTANKA_VL ");
		sql.append("	,TRS.BAITANKA_VL ");
		sql.append("	,CASE ");
		sql.append("		WHEN ");
		sql.append("			TRS.GENTANKA_VL IS NOT NULL AND ");
		sql.append("			TRS.BAITANKA_VL IS NOT NULL AND ");
		sql.append("			TRS.BAITANKA_VL <> " + CONST_ZERO + " AND ");
		sql.append("			TRS.BAITANKA_VL > TRS.GENTANKA_VL ");
		sql.append("				THEN CEIL(((TRS.BAITANKA_VL - TRS.GENTANKA_VL) / TRS.BAITANKA_VL) * " + NEIRE_RT_ROUNDUP_CORRECTION_VALUE + ") / " + NEIRE_RT_ROUNDUP_CORRECTION_VALUE + " ");
		sql.append("		ELSE " + CONST_ZERO + " ");
		sql.append("	 END NEIRE_RT ");
		sql.append("	,CASE GET_JAN_SYUBETSU(TRS.SYOHIN_CD) ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_JAN_13 + "' THEN SUBSTR(TRS.SYOHIN_CD, " + MAKER_CD_START_COLUMN_JAN13 + ", " + MAKER_CD_LENGTH_JAN13 + ") ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EAN_13 + "' THEN SUBSTR(TRS.SYOHIN_CD, " + MAKER_CD_START_COLUMN_EAN13 + ", " + MAKER_CD_LENGTH_EAN13 + ") ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_JAN_8 + "' THEN SUBSTR(TRS.SYOHIN_CD, " + MAKER_CD_START_COLUMN_JAN8 + ", " + MAKER_CD_LENGTH_JAN8 + ") ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EAN_8 + "' THEN SUBSTR(TRS.SYOHIN_CD, " + MAKER_CD_START_COLUMN_EAN8 + ", " + MAKER_CD_LENGTH_EAN8 + ") ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_UPC_A + "' THEN SUBSTR(TRS.SYOHIN_CD, " + MAKER_CD_START_COLUMN_UPC_A + ", " + MAKER_CD_LENGTH_UPC_A + ") ");
		sql.append("		ELSE '' ");
		sql.append("	 END AS MAKER_CD ");
		sql.append("	,TRS.SIIRESAKI_CD ");
		sql.append("	,TRS.HANBAI_ST_DT AS ATSUKAI_ST_DT ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRS.HANBAI_ED_DT <> '" + SPECIAL_DATE + "' ");
		sql.append("			THEN TRS.HANBAI_ED_DT  ");
		sql.append("		ELSE ");
		sql.append("			''  ");
		sql.append("	 END AS ATSUKAI_ED_DT ");
		sql.append("	,TRS.ZEI_KB AS  ZOKUSEI_1_KB ");
		sql.append("	,TRS.FREE_1_KB AS ZOKUSEI_2_KB ");
		sql.append("	,TRS.FREE_2_KB AS ZOKUSEI_3_KB ");
		sql.append("	,TRS.FREE_3_KB AS ZOKUSEI_4_KB ");
		sql.append("	,TRS.FREE_4_KB AS ZOKUSEI_5_KB ");
		sql.append("	,LPAD(TRS.HACHUTANI_IRISU_QT, " + IRISU_LENGTH + ", '" + PADDING_STR + "') AS COMMENT_1_TX ");
		sql.append("	,SUBSTRB(TRS.KIKAKU_KANJI_NA, " + KIKAKU_NA_START_COLUMN + ", " + KIKAKU_NA_LENGTH + ") AS COMMENT_2_TX ");
		sql.append("	,'' AS COMMENT_3_TX ");
		sql.append("	,SUBSTRB(TRS.KIKAKU_KANJI_2_NA, " + KIKAKU_NA_2_START_COLUMN + ", " + KIKAKU_NA_2_LENGTH + ") AS COMMENT_4_TX ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRIM(TRS.FREE_CD) IS NOT NULL THEN LPAD(TRIM(TO_CHAR(TRS.FREE_CD, '99999')), " + NINI_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("		ELSE '' ");
		sql.append("	 END AS COMMENT_5_TX ");
		sql.append("	,'" + mst000102_IfConstDictionary.DWH_DELETE_KB_ACTIVE + "' AS DELETE_FG ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRS.GENTANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		sql.append("		WHEN TRS.BAITANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		sql.append("		ELSE '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");
		sql.append("	 END AS ERR_KB ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	TMP_R_SYOHIN TRS ");
		sql.append("	INNER JOIN ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			 TRS.SYOHIN_CD ");
		sql.append("			,MAX(TRS.YUKO_DT) AS YUKO_DT ");
		sql.append("		FROM ");
		sql.append("			TMP_R_SYOHIN TRS ");
		sql.append("		WHERE ");
		sql.append("			TRS.YUKO_DT <= '" + yokuBatchDt + "' ");
		sql.append("		GROUP BY ");
		sql.append("			 TRS.SYOHIN_CD ");
		sql.append("	) TRS2 ");
		sql.append("	ON ");
		sql.append("		TRS.SYOHIN_CD	= TRS2.SYOHIN_CD	AND ");
		sql.append("		TRS.YUKO_DT		= TRS2.YUKO_DT ");
		sql.append("WHERE ");
		sql.append("	TRS.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");

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
