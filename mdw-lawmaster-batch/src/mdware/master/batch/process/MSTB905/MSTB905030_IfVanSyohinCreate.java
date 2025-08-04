package mdware.master.batch.process.MSTB905;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB905030_IfVanSyohinCreate.java クラス</p>
 * <p>説明　: WK_VAN商品マスタの内容を、IF_VAN商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.20) T.Ooshiro [CUS00102] レクサスEDIインターフェイス仕様変更対応（マスタ）対応
 *
 */
public class MSTB905030_IfVanSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF = "IF_VAN_SYOHIN"; // IF_VAN_商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** 仕入先コード：開始カラム */
	private static final String SIIRESAKI_CD_ST_COLUMN = "1";
	/** 仕入先コード：桁数 */
	private static final String SIIRESAKI_CD_LENGTH = "6";
	/** 正式名称：開始カラム */
	private static final String REC_HINMEI_KANJI_NA_ST_COLUMN = "1";
	/** 正式名称：桁数 */
	private static final String REC_HINMEI_KANJI_NA_LENGTH = "32";
	/** 規格：開始カラム */
	private static final String KIKAKU_KANJI_NA_ST_COLUMN = "1";
	/** 規格：桁数 */
	private static final String KIKAKU_KANJI_NA_LENGTH = "18";
	/** 名称カナ：開始カラム */
	private static final String HINMEI_KANA_NA_ST_COLUMN = "1";
	/** 名称カナ：桁数 */
	private static final String HINMEI_KANA_NA_LENGTH = "25";
	/** ＤＰＴコード：桁数 */
	private static final String BUNRUI1_CD_LENGTH = "3";
	/** クラスコード：開始カラム */
	private static final String BUNRUI5_CD_ST_COLUMN = "3";
	/** クラスコード：桁数 */
	private static final String BUNRUI5_CD_LENGTH = "3";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB905030_IfVanSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB905030_IfVanSyohinCreate() {
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

			// IF_VAN_商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "IF_VAN_商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_VAN_商品マスタを削除処理を終了します。");

			// IF_VAN_商品マスタの登録
			writeLog(Level.INFO_INT, "IF_VAN_商品マスタ登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfVanSyohinInsertSql());
			writeLog(Level.INFO_INT, "IF_VAN_商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_VAN_商品マスタ登録処理（WK→IF）を終了します。");

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
	 * IF_VAN_SYOHINを登録するSQLを取得する
	 * 
	 * @return IF_VAN_SYOHIN登録SQL
	 */
	private String getIfVanSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_VAN_SYOHIN ");
		sql.append("	( ");
		sql.append("	 SIIRESAKI_CD ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,REC_HINMEI_KANJI_NA ");
		sql.append("	,KIKAKU_KANJI_NA ");
		sql.append("	,HINMEI_KANA_NA ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,HACHUTANI_IRISU_QT ");
		sql.append("	,GENTANKA_VL ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 SUBSTRB(WVS.SIIRESAKI_CD, " + SIIRESAKI_CD_ST_COLUMN + ", " + SIIRESAKI_CD_LENGTH + ") AS SIIRESAKI_CD ");
		sql.append("	,WVS.SYOHIN_CD                                                                          AS SYOHIN_CD ");
		sql.append("	,SUBSTRB(WVS.REC_HINMEI_KANJI_NA, " + REC_HINMEI_KANJI_NA_ST_COLUMN + ", " +REC_HINMEI_KANJI_NA_LENGTH  + ") ");
		sql.append("	                                                                                        AS REC_HINMEI_KANJI_NA ");
		sql.append("	,SUBSTRB(NVL(WVS.KIKAKU_KANJI_NA, ' '), " + KIKAKU_KANJI_NA_ST_COLUMN + ", " + KIKAKU_KANJI_NA_LENGTH + ") ");
		sql.append("	                                                                                        AS KIKAKU_KANJI_NA ");
		sql.append("	,SUBSTRB(WVS.HINMEI_KANA_NA, " + HINMEI_KANA_NA_ST_COLUMN + ", " + HINMEI_KANA_NA_LENGTH + ") ");
		sql.append("	                                                                                        AS HINMEI_KANA_NA ");
		sql.append("	,LPAD(TRIM(WVS.BUNRUI1_CD), " + BUNRUI1_CD_LENGTH + ", '" + PADDING_STR + "')           AS BUNRUI1_CD ");
		sql.append("	,TRIM(TO_CHAR(TRUNC(WVS.HACHUTANI_IRISU_QT), '0000'))                                   AS HACHUTANI_IRISU_QT ");
		sql.append("	,TRIM(TO_CHAR(WVS.GENTANKA_VL * 100, '00000000'))                                       AS GENTANKA_VL ");
		sql.append("	,TRIM(TO_CHAR(WVS.BAITANKA_VL, '000000'))                                               AS BAITANKA_VL ");
		sql.append("	,SUBSTRB(WVS.BUNRUI5_CD, " + BUNRUI5_CD_ST_COLUMN + ", " + BUNRUI5_CD_LENGTH + ")       AS BUNRUI5_CD ");
		sql.append("	,'" + userLog.getJobId() + "'                                                           AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "'          AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                           AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "'          AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_VAN_SYOHIN WVS ");
		sql.append("WHERE ");
		sql.append("	WVS.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");

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
