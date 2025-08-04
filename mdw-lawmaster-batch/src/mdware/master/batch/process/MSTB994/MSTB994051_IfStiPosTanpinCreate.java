package mdware.master.batch.process.MSTB994;

import java.nio.CharBuffer;
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
import mdware.master.util.RMSTDATEUtil;
/**
*
* <p>タイトル: MSTB994051_IfStiPosTanpinCreate.java クラス</p>
* <p>説明　: WK_指定日PLU店別商品の内容を、IF_PLU単品メンテに取込む</p>
* <p>著作権: Copyright (c) 2017</p>
* <p>会社名: VINX</p>
* @version 1.00 (2017.01.16) 新規作成 #1749対応 T.Han
* @version 1.01 (2017.02.09) #3765 S.Takayama
* @version 1.02 (2020.07.13) KHAI.NN #6167 MKV対応
* @version 1.03 (2020.09.22) KHAI.NN #6227 MKV対応
* @version 1.04 (2020.09.30) KHAI.NN #6238 MKV対応
* @Version 1.05 (2024.01.16) DUY.HM #15277 MKH対応
*/

public class MSTB994051_IfStiPosTanpinCreate {
	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	// バッチ日付
	private String batchDt = null;
	/** システム時刻 */
	private String timeStamp = "";
	/** 作成日 */
	private String sakuseiDt = "";

	private static final String TABLE_IF_STI_PLU_TANPIN = "IF_STI_PLU_TANPIN"; // IF_指定日_PLU単品メンテ
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 送信回数 */
	private static final String SEND_QT = "01";
	/** 新規・訂正 登録ID */
	private static final String TOROKU_ID_A = "A";

	/** 商品バーコード*/
	private static final String SYOHIN_BAR_CD = "000000000000000000";
	/** 会員売価単価*/
	private static final String KAIIN_BAITANKA_VL = "00000000000000000";
	/** PLUフラグ*/
	private static final String PLU_FG = "2";
	/** 販売税率*/
	private static final String HANBAI_ZEI_RT = "000";
	/** 学生割引カード（KADS1M）フラグ*/
	private static final String STUDENT_WARIBIKI_CARD_FG = "0";
	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** 店舗コード 桁数 */
	private static final String TENPO_CD_LENGTH = "6";
	/** 正式名称（漢字）開始カラム */
	/** 正式名称（漢字）桁数 ライン */
	private static final int LENGTH_SYOHIN_RN  = 15;
	private static final int LENGTH_SYOHIN_NA = 30;
	private static final int LENGTH_HANBAI_TN = 5;
	private static final int LENGTH_DIVISION_CD = 3;
	private static final int LENGTH_DEPARTMENT_CD = 3;
	private static final int LENGTH_CREATE_TS = 8;
	private static final int LENGTH_INJI_SEIZOU_DT = 3;
	private static final int LENGTH_ZEI_CD = 5;
	/** 仕入先コードがNULLの場合に設定する値 */
	private static final String SIIRESAKI_CD_NULL = "000000000";
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB994051_IfStiPosTanpinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB994051_IfStiPosTanpinCreate() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception{
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
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDt);
			DateChanger.addDate(batchDt, 1);

			// 作成日取得
			timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
			sakuseiDt = timeStamp.substring(0, 4) + "/" + timeStamp.substring(4, 6) + "/" + timeStamp.substring(6, 8);
			writeLog(Level.INFO_INT, "作成日： " + sakuseiDt);

			// IF_指定日_PLU単品メンテTRUNCATE
			writeLog(Level.INFO_INT, "IF_指定日_PLU単品メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_STI_PLU_TANPIN);
			writeLog(Level.INFO_INT, "IF_指定日_PLU単品メンテを削除処理を終了します。");

			// IF_指定日_PLU単品メンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_指定日_PLU単品メンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfStiPluTanpinInsertSql());
			writeLog(Level.INFO_INT, "IF_指定日_PLU単品メンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_指定日_PLU単品メンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();

			writeLog(Level.INFO_INT, "処理を終了します。");

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
	 * IF_STI_PLU_TANPIN(新規・訂正)を登録するSQLを取得する
	 *
	 * @return IF_STI_PLU_TANPIN(新規・訂正)登録SQL
	 */
	public String getIfStiPluTanpinInsertSql() throws Exception{
		StringBuilder sql= new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_STI_PLU_TANPIN");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO");
		sql.append("	,EIGYO_DT");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,SYOHIN_RN ");
		sql.append("	,SYOHIN_NA ");
		sql.append("	,SYOHIN_RN_CHN ");
		sql.append("	,SYOHIN_NA_CHN ");
		sql.append("	,SYOHIN_BAR_CD ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,KAIIN_BAITANKA_VL ");
		sql.append("	,HANBAI_TN ");
		sql.append("	,DIVISION_CD ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,SUBCLASS_CD ");
		sql.append("	,TEIKAN_FG ");
		sql.append("	,PLU_FG ");
		sql.append("	,CREATE_TS ");
		sql.append("	,ZEI_KB ");
		sql.append("	,ZEI_RT ");
		sql.append("	,SEASON_ID ");
		sql.append("	,HANBAI_ZEI_RT ");
		sql.append("	,STUDENT_WARIBIKI_CARD_FG ");
		sql.append("	,SYOHI_KIGEN_DT ");
		sql.append("	,CARD_FG ");
		sql.append("	,INJI_HANBAI_TN ");
		sql.append("	,INJI_SEIZOU_DT ");
		sql.append("	,ZEI_CD ");
		sql.append("	,INSERT_TS ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,LABEL_SEIBUN ");
		sql.append("	,LABEL_HOKAN_HOHO ");
		sql.append("	,LABEL_TUKAIKATA ");
		sql.append("	,GYOTAI_KB");
		sql.append("	,LABEL_COUNTRY_NA");
		// 2017.01.25 M.Akagi #3571 (S)
		sql.append("	,OLD_SYOHIN_CD");
		// 2017.01.25 M.Akagi #3571 (E)
		// #3765 MSTB994 2017.02.09 S.Takayama (S)
		sql.append("	,INJI_HANBAI_TN_EN ");
		// #3765 MSTB994 2017.02.09 S.Takayama (E)
		// #6238 MSTB994 Add 2020.09.30 KHAI.NN (S)
		sql.append("	,ITEM_OFFICIAL_NA ");
		// #6238 MSTB994 Add 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 WTSP.UKETSUKE_NO                                                               AS UKETSUKE_NO ");
		sql.append("	,WTSP.TAISYO_DT                                                                 AS TAISYO_DT");
		sql.append("	,LPAD(TRIM(WTSP.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')      AS TENPO_CD ");
		sql.append("	,'" + SEND_QT +"'                                                               AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "'                                                          AS TOROKU_ID ");
		sql.append("	,WTSP.SYOHIN_CD                                                                 AS SYOHIN_CD ");
		sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                              AS SYOHIN_RN");
		// #6167 MSTB994 Mod 2020.07.13 KHAI.NN (S)
//		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                              AS SYOHIN_NA");
		// #6227 MSTB994 Mod 2020.09.22 KHAI.NN (S)
		//sql.append("	,WTSP.HINMEI_KANJI_NA                                                           AS SYOHIN_NA");
		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                              AS SYOHIN_NA");
		// #6227 MSTB994 Mod 2020.09.22 KHAI.NN (E)
		// #6167 MSTB994 Mod 2020.07.13 KHAI.NN (E)
		sql.append("	,WTSP.KEIRYOKI_NM                                                               AS SYOHIN_RN_CHN ");
		sql.append("	,WTSP.REC_HINMEI_KANJI_NA                                                       AS SYOHIN_NA_CHN");
		sql.append("	,'" + SYOHIN_BAR_CD + "'                                                        AS SYOHIN_BAR_CD ");
		sql.append("	,WTSP.BAITANKA_VL                                                               AS BAITANKA_VL");
		sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                                    AS KAIIN_BAITANKA_VL ");
		sql.append("	,'" + spaces(LENGTH_HANBAI_TN) + "'                                             AS HANBAI_TN ");
		sql.append("	,'" + spaces(LENGTH_DIVISION_CD) + "'                                           AS DIVISION_CD ");
		sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD) + "'                                         AS DEPARTMENT_CD ");
		sql.append("	,LTRIM(WTSP.BUNRUI2_CD, '0')                                                    AS CLASS_CD ");
		sql.append("	,LTRIM(WTSP.BUNRUI5_CD, '0')                                                    AS SUBCLASS_CD ");
		sql.append("	,WTSP.TEIKAN_FG                                                                 AS TEIKAN_FG ");
		sql.append("	,'" + PLU_FG + "'                                                               AS PLU_FG ");
		sql.append("	,'" + spaces(LENGTH_CREATE_TS) + "'                                             AS CREATE_TS ");
		sql.append("	,WTSP.ZEI_KB                                                                    AS ZEI_KB ");
		sql.append("	,WTSP.ZEI_RT                                                                    AS ZEI_RT ");
		sql.append("	,WTSP.SEASON_ID                                                                 AS SEASON_ID ");
		sql.append("	,'" + HANBAI_ZEI_RT + "'                                                        AS HANBAI_ZEI_RT ");
		sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                             AS STUDENT_WARIBIKI_CARD_FG ");
		sql.append("	,WTSP.SYOHI_KIGEN_DT                                                            AS SYOHI_KIGEN_DT ");
		sql.append("	,WTSP.CARD_FG                                                                   AS CARD_FG ");
		sql.append("	,WTSP.HANBAI_TANI                                                               AS INJI_HANBAI_TN ");
		sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                        AS INJI_SEIZOU_DT ");
		sql.append("	,'" + spaces(LENGTH_ZEI_CD) + "'                                                AS ZEI_CD ");
		sql.append("	,'" + batchTs + "'                                                              AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                   AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                              AS UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                   AS UPDATE_USER_ID ");
		sql.append("	,COALESCE(WTSP.SIIRESAKI_CD, '" + SIIRESAKI_CD_NULL + "')                       AS SIIRESAKI_CD ");
		sql.append("	,WTSP.SYOHI_KIGEN_HYOJI_PATTER                                                  AS SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,WTSP.LABEL_SEIBUN                                                              AS LABEL_SEIBUN ");
		sql.append("	,WTSP.LABEL_HOKAN_HOHO                                                          AS LABEL_HOKAN_HOHO ");
		sql.append("	,WTSP.LABEL_TUKAIKATA                                                           AS LABEL_TUKAIKATA ");
		sql.append("	,WTSP.GYOTAI_KB                                                                 AS GYOTAI_KB ");
		sql.append("	,WTSP.LABEL_COUNTRY_NA                                                          AS LABEL_COUNTRY_NA ");
		// 2017.01.25 M.Akagi #3571 (S)
		sql.append("	,WTSP.OLD_SYOHIN_CD                                                          AS OLD_SYOHIN_CD ");
		// 2017.01.25 M.Akagi #3571 (E)
		// #3765 MSTB994 2017.02.09 S.Takayama (S)
		sql.append("	,WTSP.HANBAI_TANI_EN                                                            AS INJI_HANBAI_TN_EN ");
		// #3765 MSTB994 2017.02.09 S.Takayama (E)
		// #6238 MSTB994 Add 2020.09.30 KHAI.NN (S)
		sql.append("	,WTSP.HINMEI_KANJI_NA                                                           AS ITEM_OFFICIAL_NA");
		// #6238 MSTB994 Add 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,WTSP.MAX_BUY_QT                                                                AS MAX_BUY_QT");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append(" FROM ");
		sql.append("	WK_TEC_STI_PLU WTSP ");
		sql.append("WHERE ");
		sql.append("	WTSP.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");

		return sql.toString();
	}

	/**
	 * @param spaces
	 * @return String
	 */
	private String spaces(int spaces){
		return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
	}

	/******* 共通処理 **********/

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
