package mdware.master.batch.process.MSTB905;

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
import mdware.master.common.dictionary.mst001201_EosKbDictionary;
import mdware.master.common.dictionary.mst010101_SyohinKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * 
 * <p>タイトル: MSTB905010_WkVanSyohinCreate.java クラス</p>
 * <p>説明　: TMP商品マスタの内容を、WK_VAN商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.20) T.Ooshiro [CUS00102] レクサスEDIインターフェイス仕様変更対応（マスタ）対応
 *
 */
public class MSTB905010_WkVanSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK = "WK_VAN_SYOHIN"; // WK_VAN商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;
	// チェック対象日（最小）
	private String minCheckDt = null;
	
	/** 金額項目桁数エラー判定値 */
	private static final String KINGAKU_ERROR_CHECK_VL = "999999";
	/** 仕入先コード EOS対象外 */
	private static final String SIIRESAKI_CD_EOS_TAISYOGAI = "900000";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB905010_WkVanSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB905010_WkVanSyohinCreate() {
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
			String vanEosCheckTerm = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.VAN_EOS_CHECK_TERM);
			minCheckDt = DateChanger.addDate(yokuBatchDt, -1 * Integer.parseInt(vanEosCheckTerm));

			// WK_VAN_商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "WK_VAN_商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK);
			writeLog(Level.INFO_INT, "WK_VAN_商品マスタを削除処理を終了します。");

			// WK_VAN_商品マスタの登録
			writeLog(Level.INFO_INT, "WK_VAN_商品マスタ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkVanSyohinInsertSql());
			writeLog(Level.INFO_INT, "WK_VAN_商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_VAN_商品マスタ登録処理（TMP→WK）を終了します。");

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
	 * WK_VAN_SYOHINを登録するSQLを取得する
	 * 
	 * @return WK_VAN_SYOHIN登録SQL
	 */
	private String getWkVanSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_VAN_SYOHIN ");
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
		sql.append("	,ERR_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		// EOS区分が２以外で登録されている商品
		sql.append("SELECT ");
		sql.append("	 TRS.SIIRESAKI_CD ");
		sql.append("	,TRS.SYOHIN_CD ");
		sql.append("	,TRS.REC_HINMEI_KANJI_NA ");
		sql.append("	,TRS.KIKAKU_KANJI_NA ");
		sql.append("	,TRS.HINMEI_KANA_NA ");
		sql.append("	,TRS.BUNRUI1_CD ");
		sql.append("	,TRS.HACHUTANI_IRISU_QT ");
		sql.append("	,TRS.GENTANKA_VL ");
		sql.append("	,TRS.BAITANKA_VL ");
		sql.append("	,TRS.BUNRUI5_CD ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRS.GENTANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " ");
		sql.append("			THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		sql.append("		WHEN TRS.BAITANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " ");
		sql.append("			THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		sql.append("		ELSE '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");
		sql.append("	 END ");
		sql.append("	,'" + userLog.getJobId() + "' ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	,'" + userLog.getJobId() + "' ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("FROM ");
		sql.append("	TMP_R_SYOHIN TRS ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRS.SYOHIN_CD ");
		sql.append("			,MAX(TRS.YUKO_DT) AS YUKO_DT ");
		sql.append("		FROM ");
		sql.append("			TMP_R_SYOHIN TRS ");
		sql.append("		WHERE ");
		sql.append("			TRS.YUKO_DT <= '" + yokuBatchDt + "' ");
		sql.append("		GROUP BY ");
		sql.append("			TRS.SYOHIN_CD ");
		sql.append("		)  TRS1 ");
		sql.append("		ON ");
		sql.append("			TRS.SYOHIN_CD	= TRS1.SYOHIN_CD	AND ");
		sql.append("			TRS.YUKO_DT		= TRS1.YUKO_DT ");
		sql.append("WHERE ");
		sql.append("	TRS.DELETE_FG			<> '" + mst000101_ConstDictionary.DELETE_FG_DEL + "'			AND ");
		sql.append("	TRS.EOS_KB				<> '" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "'	AND ");
		sql.append("	TRS.SYOHIN_KB			<> '" + mst010101_SyohinKbDictionary.HANBAI.getCode() + "'		AND ");
		sql.append("	TRIM(TRS.SIIRESAKI_CD)	<> '" + SIIRESAKI_CD_EOS_TAISYOGAI + "' ");
		sql.append("UNION ");
		// ７日以内に２に変更されている商品
		sql.append("SELECT ");
		sql.append("	 TRS.SIIRESAKI_CD ");
		sql.append("	,TRS.SYOHIN_CD ");
		sql.append("	,TRS.REC_HINMEI_KANJI_NA ");
		sql.append("	,TRS.KIKAKU_KANJI_NA ");
		sql.append("	,TRS.HINMEI_KANA_NA ");
		sql.append("	,TRS.BUNRUI1_CD ");
		sql.append("	,TRS.HACHUTANI_IRISU_QT ");
		sql.append("	,TRS.GENTANKA_VL ");
		sql.append("	,TRS.BAITANKA_VL ");
		sql.append("	,TRS.BUNRUI5_CD ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRS.GENTANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " ");
		sql.append("			THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		sql.append("		WHEN TRS.BAITANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " ");
		sql.append("			THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		sql.append("		ELSE '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");
		sql.append("	 END ");
		sql.append("	,'" + userLog.getJobId() + "' ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	,'" + userLog.getJobId() + "' ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("FROM ");
		sql.append("	TMP_R_SYOHIN TRS ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRS.SYOHIN_CD ");
		sql.append("			,MAX(TRS.YUKO_DT) AS YUKO_DT ");
		sql.append("		FROM ");
		sql.append("			TMP_R_SYOHIN TRS ");
		sql.append("		WHERE ");
		sql.append("			TRS.YUKO_DT <= '" + yokuBatchDt + "' ");
		sql.append("		GROUP BY ");
		sql.append("			TRS.SYOHIN_CD ");
		sql.append("		)  TRS1 ");
		sql.append("		ON ");
		sql.append("			TRS.SYOHIN_CD	= TRS1.SYOHIN_CD	AND ");
		sql.append("			TRS.YUKO_DT		= TRS1.YUKO_DT ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRS.SYOHIN_CD ");
		sql.append("			,MAX(TRS.YUKO_DT) AS YUKO_DT ");
		sql.append("		FROM ");
		sql.append("			( ");
		// 直近過去に２でないデータが存在する ");
		sql.append("			SELECT ");
		sql.append("				 TRS.SYOHIN_CD ");
		sql.append("				,MAX(YUKO_DT) AS YUKO_DT ");
		sql.append("			FROM ");
		sql.append("				TMP_R_SYOHIN TRS ");
		sql.append("			WHERE ");
		sql.append("				TRS.DELETE_FG			<> '" + mst000101_ConstDictionary.DELETE_FG_DEL + "'			AND ");
		sql.append("				TRS.EOS_KB				<> '" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "'	AND ");
		sql.append("				TRS.SYOHIN_KB			<> '" + mst010101_SyohinKbDictionary.HANBAI.getCode() + "'		AND ");
		sql.append("				TRIM(TRS.SIIRESAKI_CD)	<> '" + SIIRESAKI_CD_EOS_TAISYOGAI + "'							AND ");
		sql.append("				TRS.YUKO_DT 			<= '" + yokuBatchDt + "'										AND ");
		sql.append("				TRS.YUKO_DT				>= '" + minCheckDt + "' ");
		sql.append("			GROUP BY ");
		sql.append("				TRS.SYOHIN_CD ");
		sql.append("			UNION ");
		// 7日より前の直近のレコードを取得、そのEOS区分が２でない
		sql.append("			SELECT ");
		sql.append("				 TRS.SYOHIN_CD ");
		sql.append("				,TRS.YUKO_DT ");
		sql.append("			FROM ");
		sql.append("				TMP_R_SYOHIN TRS ");
		sql.append("				INNER JOIN ");
		sql.append("					( ");
		sql.append("					SELECT ");
		sql.append("						 TRS.SYOHIN_CD ");
		sql.append("						,MAX(YUKO_DT) AS YUKO_DT ");
		sql.append("					FROM ");
		sql.append("						TMP_R_SYOHIN TRS ");
		sql.append("					WHERE ");
		sql.append("						TRS.DELETE_FG			<> '" + mst000101_ConstDictionary.DELETE_FG_DEL + "'			AND ");
		sql.append("						TRS.SYOHIN_KB			<> '" + mst010101_SyohinKbDictionary.HANBAI.getCode() + "'		AND ");
		sql.append("						TRIM(TRS.SIIRESAKI_CD)	<> '" + SIIRESAKI_CD_EOS_TAISYOGAI + "'							AND ");
		sql.append("						TRS.YUKO_DT				 < '" + minCheckDt + "' ");
		sql.append("					GROUP BY ");
		sql.append("						TRS.SYOHIN_CD ");
		sql.append("					)  TRS1 ");
		sql.append("					ON ");
		sql.append("						TRS.SYOHIN_CD	= TRS1.SYOHIN_CD	AND ");
		sql.append("						TRS.YUKO_DT		= TRS1.YUKO_DT ");
		sql.append("			WHERE ");
		sql.append("				TRS.EOS_KB	<> '" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "' ");
		sql.append("			) TRS ");
		sql.append("		GROUP BY ");
		sql.append("			TRS.SYOHIN_CD ");
		sql.append("		) TRS2 ");
		sql.append("		ON ");
		sql.append("			TRS.SYOHIN_CD	= TRS2.SYOHIN_CD ");
		sql.append("WHERE ");
		sql.append("	TRS.DELETE_FG			<> '" + mst000101_ConstDictionary.DELETE_FG_DEL + "'			AND ");
		sql.append("	TRS.EOS_KB				 = '" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "'	AND ");
		sql.append("	TRS.SYOHIN_KB			<> '" + mst010101_SyohinKbDictionary.HANBAI.getCode() + "'		AND ");
		sql.append("	TRIM(TRS.SIIRESAKI_CD)	<> '" + SIIRESAKI_CD_EOS_TAISYOGAI + "'							AND ");
		sql.append("	TRS.YUKO_DT				>= '" + minCheckDt + "'											AND");
		sql.append("	TRS2.YUKO_DT			IS NOT NULL ");
		sql.append("	AND ");
		sql.append("	NOT EXISTS ");
		sql.append("		( ");
		sql.append("			SELECT ");
		sql.append("				TRS3.SYOHIN_CD ");
		sql.append("			FROM ");
		sql.append("				TMP_R_SYOHIN TRS3 ");
		sql.append("			WHERE ");
		sql.append("				TRS3.SYOHIN_CD	= TRS.SYOHIN_CD	AND ");
		sql.append("				TRS3.YUKO_DT	< TRS.YUKO_DT	AND ");
		sql.append("				TRS3.YUKO_DT	> TRS2.YUKO_DT	AND ");
		sql.append("				TRS3.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_DEL + "' ");
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

