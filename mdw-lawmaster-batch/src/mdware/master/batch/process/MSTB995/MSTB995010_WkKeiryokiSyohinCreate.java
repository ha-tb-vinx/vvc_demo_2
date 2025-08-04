package mdware.master.batch.process.MSTB995;

import java.sql.SQLException;

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

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB995010_WkKeiryokiSyohinCreate.java クラス</p>
 * <p>説明　: TMP計量器マスタの内容を、WK_IF_計量器マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.24) T.Ooshiro [CUS00065] ランドローム様対応 計量器インターフェイス仕様変更対応
 *
 */
public class MSTB995010_WkKeiryokiSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK = "WK_IF_KEIRYOKI"; // WK_IF_計量器マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;

	/** 重複エラー判定用コンスト値 */
	private static final String CONST_TWO = "2";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB995010_WkKeiryokiSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB995010_WkKeiryokiSyohinCreate() {
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

			// WK_IF_計量器マスタのTRUNCATE
			writeLog(Level.INFO_INT, "WK_IF_計量器マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK);
			writeLog(Level.INFO_INT, "WK_IF_計量器マスタを削除処理を終了します。");

			// WK_IF_計量器マスタの登録
			writeLog(Level.INFO_INT, "WK_IF_計量器マスタ登録処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkIfKeiryokiInsertSql());
			writeLog(Level.INFO_INT, "WK_IF_計量器マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_IF_計量器マスタ登録処理（TMP→WK）を終了します。");

			db.commit();

			// WK_IF_計量器マスタの更新
			writeLog(Level.INFO_INT, "WK_IF_計量器マスタ更新処理（エラー判定）を開始します。");
			iRec = db.executeUpdate(getWkIfKeiryokiUpdateSql());
			writeLog(Level.INFO_INT, "WK_IF_計量器マスタを" + iRec + "件更新しました。");
			writeLog(Level.INFO_INT, "WK_IF_計量器マスタ登録処理（エラー判定）を終了します。");

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
	 * WK_IF_KEIRYOKIを登録するSQLを取得する
	 * 
	 * @return WK_IF_KEIRYOKI登録SQL
	 */
	private String getWkIfKeiryokiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_IF_KEIRYOKI ");
		sql.append("	( ");
		sql.append("	 BUNRUI1_CD ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,SYOHIN_YOBIDASI ");
		sql.append("	,BUNRUI2_CD ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,NETSUKEKI_NA ");
		sql.append("	,TEIKAN_KB ");
		sql.append("	,TEIKAN_TANI_KB ");
		sql.append("	,TEIKAN_WEIGHT_QT ");
		sql.append("	,FUTAI_WEIGHT_QT ");
		sql.append("	,KAKOBI_PRINT_KB ");
		sql.append("	,SYOMIKIKAN_KB ");
		sql.append("	,SYOMIKIKAN_VL ");
		sql.append("	,KAKOJIKOKU_PRINT_KB ");
		sql.append("	,SENTAKU_COMMENT_CD ");
		sql.append("	,HOZON_ONDOTAI_KB ");
		sql.append("	,REC_HINMEI_KANJI_NA ");
		sql.append("	,TRACEABILITY_FG ");
		sql.append("	,TENKABUTU_NA ");
		sql.append("	,SYOHIN_UPDATE_TS ");
		sql.append("	,ERR_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 TRK.BUNRUI1_CD ");
		sql.append("	,TRK.SYOHIN_CD ");
		sql.append("	,TRK.SYOHIN_YOBIDASI ");
		sql.append("	,TRS.BUNRUI2_CD ");
		sql.append("	,TRS.BUNRUI5_CD ");
		sql.append("	,TRK.NETSUKEKI_NA ");
		sql.append("	,TRS.TEIKAN_KB ");
		sql.append("	,TRK.TEIKAN_TANI_KB ");
		sql.append("	,TRK.TEIKAN_WEIGHT_QT ");
		sql.append("	,TRK.FUTAI_WEIGHT_QT ");
		sql.append("	,TRK.KAKOBI_PRINT_KB ");
		sql.append("	,TRK.SYOMIKIKAN_KB ");
		sql.append("	,TRK.SYOMIKIKAN_VL ");
		sql.append("	,TRK.KAKOJIKOKU_PRINT_KB ");
		sql.append("	,TRK.SENTAKU_COMMENT_CD ");
		sql.append("	,TRK.HOZON_ONDOTAI_KB ");
		sql.append("	,TRS.REC_HINMEI_KANJI_NA ");
		sql.append("	,TRK.TRACEABILITY_FG ");
		sql.append("	,TRK.TENKABUTU_NA ");
		sql.append("	,TRS.UPDATE_TS ");
		sql.append("	,'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");
		sql.append("	,'" + userLog.getJobId() + "' ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	,'" + userLog.getJobId() + "' ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("FROM ");
		sql.append("	TMP_R_KEIRYOKI TRK ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRS.BUNRUI1_CD ");
		sql.append("			,TRS.SYOHIN_CD ");
		sql.append("			,TRS.BUNRUI2_CD ");
		sql.append("			,TRS.BUNRUI5_CD ");
		sql.append("			,TRS.TEIKAN_KB ");
		sql.append("			,TRS.REC_HINMEI_KANJI_NA ");
		sql.append("			,TRS.UPDATE_TS ");
		sql.append("		FROM ");
		sql.append("			TMP_R_SYOHIN TRS ");
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("				SELECT ");
		sql.append("					 TRS.SYOHIN_CD ");
		sql.append("					,MAX(YUKO_DT) AS YUKO_DT ");
		sql.append("				FROM ");
		sql.append("					TMP_R_SYOHIN TRS ");
		sql.append("				WHERE ");
		sql.append("					YUKO_DT	<= '" + yokuBatchDt + "' ");
		sql.append("				GROUP BY ");
		sql.append("					 TRS.SYOHIN_CD ");
		sql.append("				) TRS1 ");
		sql.append("				ON ");
		sql.append("					TRS.SYOHIN_CD	= TRS1.SYOHIN_CD	AND ");
		sql.append("					TRS.YUKO_DT		= TRS1.YUKO_DT ");
		sql.append("		WHERE ");
		sql.append("			TRS.DELETE_FG	<> '" + mst000101_ConstDictionary.DELETE_FG_DEL + "' ");
		sql.append("		) TRS ");
		sql.append("		ON ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("			TRK.SYOHIN_CD	= TRS.SYOHIN_CD		AND ");
//		sql.append("			TRK.BUNRUI1_CD	= TRS.BUNRUI1_CD ");
		sql.append("			TRK.SYOHIN_CD	= TRS.SYOHIN_CD		 ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("WHERE ");
		sql.append("	TRK.YUKO_DT						 =	( ");
		sql.append("										SELECT ");
		sql.append("											MAX(TRK1.YUKO_DT) ");
		sql.append("										FROM ");
		sql.append("											TMP_R_KEIRYOKI TRK1 ");
		sql.append("										WHERE ");
		sql.append("											TRK1.YUKO_DT	<= '" + yokuBatchDt + "'	AND ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("											TRK1.BUNRUI1_CD	 = TRK.BUNRUI1_CD			AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("											TRK1.SYOHIN_CD	 = TRK.SYOHIN_CD ");
		sql.append("										)	AND ");
		sql.append("	TRK.DELETE_FG					<> '" + mst000101_ConstDictionary.DELETE_FG_DEL + "' 	AND ");
		sql.append("	GET_JAN_SYUBETSU(TRK.SYOHIN_CD)	 = '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_02 + "' ");

		return sql.toString();
	}

	/**
	 * WK_IF_KEIRYOKIを更新するSQLを取得する<br>
	 * 分類１コード、商品コード単位で２件以上存在する場合、エラー区分に04を設定する
	 * 
	 * @return WK_IF_KEIRYOKI更新SQL
	 */
	private String getWkIfKeiryokiUpdateSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE ");
		sql.append("	WK_IF_KEIRYOKI WIK ");
		sql.append("SET ");
		sql.append("	WIK.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_04 + "' ");
		sql.append("WHERE ");
		sql.append("	EXISTS ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 WIK1.BUNRUI1_CD ");
		sql.append("			,WIK1.SYOHIN_YOBIDASI ");
		sql.append("		FROM ");
		sql.append("			WK_IF_KEIRYOKI WIK1 ");
		sql.append("		WHERE ");
		sql.append("			WIK1.BUNRUI1_CD			= WIK.BUNRUI1_CD		AND ");
		sql.append("			WIK1.SYOHIN_YOBIDASI	= WIK.SYOHIN_YOBIDASI ");
		sql.append("		GROUP BY ");
		sql.append("			 WIK1.BUNRUI1_CD ");
		sql.append("			,WIK1.SYOHIN_YOBIDASI ");
		sql.append("		HAVING ");
		sql.append("			COUNT(WIK1.SYOHIN_YOBIDASI) >= " + CONST_TWO + " ");
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

