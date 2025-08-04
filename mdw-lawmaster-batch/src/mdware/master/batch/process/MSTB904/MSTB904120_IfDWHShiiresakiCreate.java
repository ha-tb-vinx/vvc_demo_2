package mdware.master.batch.process.MSTB904;

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
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * 
 * <p>タイトル: MSTB904120_IfDWHShiiresakiCreate.java クラス</p>
 * <p>説明　: TMP取引先マスタの内容を、IF_DWH_仕入先マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.05) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 * @Version 3.01 (2013.12.12) T.Ooshiro [CUS00059] D3(DWH)システムインターフェイス仕様変更対応 結合テスト課題対応 №008
 *
 */
public class MSTB904120_IfDWHShiiresakiCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF = "IF_DWH_SHIIRESAKI"; // IF_DWH_仕入先マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 取引先名(漢字)開始カラム */
	private static final String KANJI_NA_START_COLUMN = "1";
	/** 取引先名(漢字)桁数 */
	private static final String KANJI_NA_LENGTH = "30";
	/** 取引先名(カナ)開始カラム */
	private static final String KANA_NA_START_COLUMN = "1";
	/** 取引先名(カナ)桁数 */
	private static final String KANA_NA_LENGTH = "15";

	/** 取引先区分：仕入先 */
	private static final String TORIHIKISAKI_KB_SHIIRESAKI = "1";
	/** 取引先区分：テナント(売上仕入) */
	private static final String TORIHIKISAKI_KB_TENANT_URIAGESHIIRE = "4";
	/** 取引先区分：テナント(賃科精算) */
	private static final String TORIHIKISAKI_KB_TENANT_CHINKASEISAN = "5";

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904120_IfDWHShiiresakiCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904120_IfDWHShiiresakiCreate() {
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

			// IF_DWH_取引先マスタのTRUNCATE
			writeLog(Level.INFO_INT, "IF_DWH_取引先マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_DWH_取引先マスタを削除処理を終了します。");

			// IF_DWH_取引先マスタの登録
			writeLog(Level.INFO_INT, "IF_DWH_取引先マスタ登録処理（TMP→IF）を開始します。");
			iRec = db.executeUpdate(getIfDwhShiiresakiInsertSql());
			writeLog(Level.INFO_INT, "IF_DWH_取引先マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_DWH_取引先マスタ登録処理（TMP→IF）を終了します。");

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
	 * IF_DWH_SHIIRESAKIを登録するSQLを取得する
	 * 
	 * @return IF_DWH_SHIIRESAKI登録SQL
	 */
	private String getIfDwhShiiresakiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_DWH_SHIIRESAKI ");
		sql.append("	( ");
		sql.append("	 KIGYO_1_CD ");
		sql.append("	,SHIIRESAKI_CD ");
		sql.append("	,SHIIRESAKI_KANJI_NA ");
		sql.append("	,SHIIRESAKI_KANA_NA ");
		sql.append("	,CATEGORY_1_CD ");
		sql.append("	,COMMENT_1_TX ");
		sql.append("	,COMMENT_2_TX ");
		sql.append("	,COMMENT_3_TX ");
		sql.append("	,COMMENT_4_TX ");
		sql.append("	,COMMENT_5_TX ");
		sql.append("	,DELETE_FG ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + mst000102_IfConstDictionary.DWH_COMP_CD  + "' AS KIGYO_1_CD ");
		sql.append("	,TO_CHAR(TO_NUMBER(TRT.TORIHIKISAKI_CD)) AS SHIIRESAKI_CD ");
		sql.append("	,SUBSTRB(TRT.TORIHIKISAKI_KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ") AS SHIIRESAKI_KANJI_NA ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRIM(TRT.TORIHIKISAKI_KANA_NA) IS NULL THEN '' ");
		sql.append("		ELSE SUBSTRB(TRT.TORIHIKISAKI_KANA_NA, " + KANA_NA_START_COLUMN + ", " + KANA_NA_LENGTH + ") ");
		sql.append("	 END AS SHIIRESAKI_KANA_NA ");
		sql.append("	,'' AS CATEGORY_1_CD ");
		sql.append("	,'' AS COMMENT_1_TX ");
		sql.append("	,'' AS COMMENT_2_TX ");
		sql.append("	,'' AS COMMENT_3_TX ");
		sql.append("	,'' AS COMMENT_4_TX ");
		sql.append("	,'' AS COMMENT_5_TX ");
		sql.append("	,'" + mst000102_IfConstDictionary.DWH_DELETE_KB_ACTIVE  + "' AS DELETE_FG ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	TMP_R_TORIHIKISAKI TRT ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRT.COMP_CD ");
		sql.append("			,TRT.CHOAI_KB ");
		sql.append("			,TRT.TORIHIKISAKI_CD ");
		sql.append("			,MAX(TRT.TEKIYO_START_DT) TEKIYO_START_DT ");
		sql.append("		FROM ");
		sql.append("			TMP_R_TORIHIKISAKI TRT ");
		sql.append("		WHERE  ");
		sql.append("			TRT.TORIKESHI_FG	 = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' AND ");
		sql.append("			TRT.CHOAI_KB		 = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' AND ");
		sql.append("			TRT.TEKIYO_START_DT	<= '" + yokuBatchDt + "' ");
		sql.append("		GROUP BY ");
		sql.append("			 TRT.COMP_CD ");
		sql.append("			,TRT.CHOAI_KB ");
		sql.append("			,TRT.TORIHIKISAKI_CD ");
		sql.append("		) TRT2 ");
		sql.append("		ON ");
		sql.append("			TRT.COMP_CD			= TRT2.COMP_CD			AND ");
		sql.append("			TRT.CHOAI_KB		= TRT2.CHOAI_KB			AND ");
		sql.append("			TRT.TORIHIKISAKI_CD	= TRT2.TORIHIKISAKI_CD	AND ");
		sql.append("			TRT.TEKIYO_START_DT	= TRT2.TEKIYO_START_DT ");
		sql.append("WHERE ");
		sql.append("	TRT.COMP_CD			= '" + mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD ).append("'				AND ");
		sql.append("	TRT.CHOAI_KB		= '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "'				AND ");
		sql.append("	TRT.DELETE_FG		= '" + mst000801_DelFlagDictionary.INAI.getCode() + "'				AND ");
		sql.append("	TRT.TORIKESHI_FG	= '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "'				AND ");
		sql.append("	TRT.TORIHIKISAKI_KB	IN	('" + TORIHIKISAKI_KB_SHIIRESAKI + "', '" + TORIHIKISAKI_KB_TENANT_URIAGESHIIRE + "', '" + TORIHIKISAKI_KB_TENANT_CHINKASEISAN + "') ");

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
