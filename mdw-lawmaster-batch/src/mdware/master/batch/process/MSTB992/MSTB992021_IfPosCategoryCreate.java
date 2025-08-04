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
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB992021_IfPosCategoryCreate.java クラス</p>
 * <p>説明　: WK_POS部門メンテ、WK_POSデプトメンテ、WK_POSクラスメンテ、
 *          WK_POSサブクラスメンテの内容を、IF_POS部門メンテ、IF_POSデプトメンテ、
 *          IF_POSクラスメンテ、IF_POSサブクラスメンテに取込む</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2015.08.06) DAI.BQ FIVImart様対応
 * @version 1.01 (2016.05.16) M.Kanno #1327 #1340 FIVImart様対応
 * @version 1.02 (2016.10.18) Li.Sheng #2238
 * @version 1.03 (2016.11.25) S.Talayama #2839
 * @version 1.04 (2021.12.14) KHOI.ND #6406
 * @version 1.05 (2022.05.05) SIEU.D #6553
 *
 */
public class MSTB992021_IfPosCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDt = null;
	
	// #2238 Add 2016.10.18 Li.Sheng (S)
	/** 翌日バッチ日付 */
	private String yokuBatchDt = null;
	// #2238 Add 2016.10.18 Li.Sheng (E)

	// テーブル
	private static final String TABLE_IF_POS_BUMON = "IF_POS_BUMON"; // IF_POS部門メンテ
	private static final String TABLE_IF_POS_DPT = "IF_POS_DPT"; // IF_POSデプトメンテ
	private static final String TABLE_IF_POS_CLASS_FIVI = "IF_POS_CLASS_FIVI"; // IF_POSクラスメンテ
	private static final String TABLE_IF_POS_SUBCLASS = "IF_POS_SUBCLASS"; // IF_POSサブクラスメンテ
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** 店舗コード 桁数 */
	private static final String TENPO_CD_LENGTH = "6";
	/** 処理日間隔*/
	private static final int SPAN_DAYS = 1;
	/** 登録ID 新規・訂正 */
	private static final String TOROKU_ID_A = "A";
	/** 登録ID 削除 */
	private static final String TOROKU_ID_D = "D";
	/** 送信回数 */
	private static final String SEND_QT = "01";
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992021_IfPosCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992021_IfPosCategoryCreate() {
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
			// #2238 Add 2016.10.18 Li.Sheng (S)
			yokuBatchDt = DateChanger.addDate(batchDt, 1);
			// #2238 Add 2016.10.18 Li.Sheng (E)
			
			//バッチ日付取得
			String batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDate);

			//商品分類体系作成日取得
			String createDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_SAKUSEI_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
			writeLog(Level.INFO_INT, "商品分類体系作成日： " + createDate);

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			createDate = DateChanger.addDate(batchDt, 1);
			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。(バッチ処理日≠商品分類体系作成日)");

				return;
			}
			writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");

			// IF_POS部門メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_POS部門メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_POS_BUMON);
			writeLog(Level.INFO_INT, "IF_POS部門メンテを削除処理を終了します。");

			// IF_POSデプトメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_POSデプトメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_POS_DPT);
			writeLog(Level.INFO_INT, "IF_POSデプトメンテを削除処理を終了します。");

			// IF_POSクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_POS_CLASS_FIVI);
			writeLog(Level.INFO_INT, "IF_POSクラスメンテを削除処理を終了します。");

			// IF_POSサブクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_POSサブクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_POS_SUBCLASS);
			writeLog(Level.INFO_INT, "IF_POSサブクラスメンテを削除処理を終了します。");

			// IF_POS部門メンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_POS部門メンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosBumonInsertSql());
			writeLog(Level.INFO_INT, "IF_POS部門メンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POS部門メンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POS部門メンテ(削除)の登録
			writeLog(Level.INFO_INT, "IF_POS部門メンテ(削除)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosBumonDelInsertSql());
			writeLog(Level.INFO_INT, "IF_POS部門メンテ(削除)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POS部門メンテ(削除)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POSデプトメンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_POSデプトメンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosDPTInsertSql());
			writeLog(Level.INFO_INT, "IF_POSデプトメンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSデプトメンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POSデプトメンテ(削除)の登録
			writeLog(Level.INFO_INT, "IF_POSデプトメンテ(削除)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosDPTDelInsertSql());
			writeLog(Level.INFO_INT, "IF_POSデプトメンテ(削除)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSデプトメンテ(削除)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POSクラスメンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosClassFiviInsertSql());
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POSクラスメンテ(削除)の登録
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(削除)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosClassFiviDelInsertSql());
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(削除)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSクラスメンテ(削除)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POSサブクラスメンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_POSサブクラスメンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosSubClassInsertSql());
			writeLog(Level.INFO_INT, "IF_POSサブクラスメンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSサブクラスメンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();

			// IF_POSサブクラスメンテ(削除)の登録
			writeLog(Level.INFO_INT, "IF_POSサブクラスメンテ(削除)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPosSubClassDelInsertSql());
			writeLog(Level.INFO_INT, "IF_POSサブクラスメンテ(削除)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POSサブクラスメンテ(削除)登録処理（WK→IF）を終了します。");

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
	 * IF_POS_BUMON(新規・訂正)を登録するSQLを取得する
	 *
	 * @return IF_POS_BUMON(新規・訂正)登録SQL
	 */
	private String getIfPosBumonInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_BUMON ");
		sql.append("	( ");
		sql.append("	 BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,DIVISION_CD ");
		sql.append("	,DIVISION_NA ");
		sql.append("	,PRIME_GROUP ");
		sql.append("	,PRIME_GROUP_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB ");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("	 '" + batchDt + "' AS BATCH_DT");
		sql.append("	 '" + yokuBatchDt + "' AS BATCH_DT");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "' AS TOROKU_ID ");
		sql.append("	,WPB.DIVISION_CD AS DIVISION_CD");
		sql.append("	,WPB.DIVISION_NA AS DIVISION_NA");
		sql.append("	,WPB.PRIME_GROUP AS PRIME_GROUP");
		sql.append("	,WPB.PRIME_GROUP_NA AS PRIME_GROUP_NA");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append(" FROM ");
		sql.append("	 WK_POS_BUMON WPB ");
		sql.append("	 LEFT JOIN ");
		sql.append("			ZEN_POS_BUMON ZPB ");
		sql.append("		ON ");
		sql.append("			ZPB.DIVISION_CD	= WPB.DIVISION_CD ");
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + batchDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + batchDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRT ");
		sql.append("WHERE ");
		sql.append("	NVL(WPB.DIVISION_NA, ' ') <> NVL(ZPB.DIVISION_NA, ' ')");

		return sql.toString();
	}

	/**
	 * IF_POS_BUMON(削除)を登録するSQLを取得する
	 *
	 * @return IF_POS_BUMON(削除)登録SQL
	 */
	private String getIfPosBumonDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_BUMON ");
		sql.append("	( ");
		sql.append("	 BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,DIVISION_CD ");
		sql.append("	,DIVISION_NA ");
		sql.append("	,PRIME_GROUP ");
		sql.append("	,PRIME_GROUP_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB ");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("	 '" + batchDt + "' AS BATCH_DT");
		sql.append("	 '" + yokuBatchDt + "' AS BATCH_DT");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_D + "' AS TOROKU_ID ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("	,WPB.DIVISION_CD AS DIVISION_CD");
		//sql.append("	,WPB.DIVISION_NA AS DIVISION_NA");
		//sql.append("	,WPB.PRIME_GROUP AS PRIME_GROUP");
		//sql.append("	,WPB.PRIME_GROUP_NA AS PRIME_GROUP_NA");
		sql.append("	,ZPB.DIVISION_CD AS DIVISION_CD");
		sql.append("	,ZPB.DIVISION_NA AS DIVISION_NA");
		sql.append("	,ZPB.PRIME_GROUP AS PRIME_GROUP");
		sql.append("	,ZPB.PRIME_GROUP_NA AS PRIME_GROUP_NA");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append(" FROM ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("	 WK_POS_BUMON WPB ");
		sql.append("	 ZEN_POS_BUMON ZPB ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB ");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
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
		sql.append("			ZPB.DIVISION_CD ");
		sql.append("		FROM ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("			ZEN_POS_BUMON ZPB ");
		sql.append("			WK_POS_BUMON WPB ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("		WHERE ");
		sql.append("			ZPB.DIVISION_CD	= WPB.DIVISION_CD ");
		sql.append("		) ");

		return sql.toString();
	}

	/**
	 * IF_POS_DPTを登録(新規・訂正)するSQLを取得する
	 *
	 * @return IF_POS_DPT(新規・訂正)登録SQL
	 */
	private String getIfPosDPTInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_DPT ");
		sql.append("	( ");
		sql.append("	 BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,DIVISION_CD ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,DEPARTMENT_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("	 '" + batchDt + "' AS BATCH_DT");
		sql.append("	 '" + yokuBatchDt + "' AS BATCH_DT");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "' AS TOROKU_ID ");
		sql.append("	,WPD.DIVISION_CD  AS DIVISION_CD ");
		sql.append("	,WPD.DEPARTMENT_CD  AS DEPARTMENT_CD ");
		sql.append("	,WPD.DEPARTMENT_NA  AS DEPARTMENT_NA ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append(" FROM ");
		sql.append("	 WK_POS_DPT WPD ");
		sql.append("	 LEFT JOIN ");
		sql.append("			ZEN_POS_DPT ZPD ");
		sql.append("		ON ");
		sql.append("			ZPD.DEPARTMENT_CD	= WPD.DEPARTMENT_CD ");
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + batchDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + batchDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRT ");
		sql.append("WHERE ");
		sql.append("	NVL(WPD.DIVISION_CD, ' ')	<> NVL(ZPD.DIVISION_CD, ' ')	OR ");
		sql.append("	NVL(WPD.DEPARTMENT_NA, ' ')		<> NVL(ZPD.DEPARTMENT_NA, ' ') ");

		return sql.toString();
	}

	/**
	 * IF_POS_DPTを登録(削除)するSQLを取得する
	 *
	 * @return IF_POS_DPT(削除)登録SQL
	 */
	private String getIfPosDPTDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_DPT ");
		sql.append("	( ");
		sql.append("	 BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,DIVISION_CD ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,DEPARTMENT_NA ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("	 '" + batchDt + "' AS BATCH_DT");
		sql.append("	 '" + yokuBatchDt + "' AS BATCH_DT");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_D + "' AS TOROKU_ID ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("	,WPD.DIVISION_CD  AS DIVISION_CD ");
		//sql.append("	,WPD.DEPARTMENT_CD  AS DEPARTMENT_CD ");
		//sql.append("	,WPD.DEPARTMENT_NA  AS DEPARTMENT_NA ");
		sql.append("	,ZPD.DIVISION_CD  AS DIVISION_CD ");
		sql.append("	,ZPD.DEPARTMENT_CD  AS DEPARTMENT_CD ");
		sql.append("	,ZPD.DEPARTMENT_NA  AS DEPARTMENT_NA ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append(" FROM ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("	 WK_POS_DPT WPD ");
		sql.append("	 ZEN_POS_DPT ZPD ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
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
		sql.append("			ZPD.DEPARTMENT_CD ");
		sql.append("		FROM ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("			ZEN_POS_DPT ZPD ");
		sql.append("			WK_POS_DPT WPD ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("		WHERE ");
		sql.append("			WPD.DEPARTMENT_CD	= ZPD.DEPARTMENT_CD ");
		sql.append("		) ");

		return sql.toString();
	}

	/**
	 * IF_POS_CLASS_FIVIを登録(新規・訂正)するSQLを取得する
	 *
	 * @return IF_POS_CLASS_FIVI(新規・訂正)登録SQL
	 */
	private String getIfPosClassFiviInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_CLASS_FIVI ");
		sql.append("	( ");
		sql.append("	 BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,CLASS_NA ");
		sql.append("	,DEPARTMENT_TYPE ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("	 '" + batchDt + "' AS BATCH_DT");
		sql.append("	 '" + yokuBatchDt + "' AS BATCH_DT");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "' AS TOROKU_ID ");
		sql.append("	,WPCF.DEPARTMENT_CD  AS DEPARTMENT_CD ");
		sql.append("	,WPCF.CLASS_CD  AS CLASS_CD ");
		sql.append("	,WPCF.CLASS_NA  AS CLASS_NA ");
		sql.append("	,WPCF.DEPARTMENT_TYPE  AS DEPARTMENT_TYPE ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append(" FROM ");
		sql.append("	 WK_POS_CLASS_FIVI WPCF ");
		sql.append("	 LEFT JOIN ");
		sql.append("			ZEN_POS_CLASS_FIVI ZPCF ");
		sql.append("		ON ");
		sql.append("			ZPCF.CLASS_CD	= WPCF.CLASS_CD ");
		sql.append("	 , ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + batchDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + batchDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRT ");
		sql.append("WHERE ");
		sql.append("	NVL(WPCF.DEPARTMENT_CD, ' ')	<> NVL(ZPCF.DEPARTMENT_CD, ' ')	OR ");
		sql.append("	NVL(WPCF.CLASS_NA, ' ')		<> NVL(ZPCF.CLASS_NA, ' ') ");

		return sql.toString();
	}

	/**
	 * IF_POS_CLASS_FIVIを登録(削除)するSQLを取得する
	 *
	 * @return IF_POS_CLASS_FIVI(削除)登録SQL
	 */
	private String getIfPosClassFiviDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_CLASS_FIVI ");
		sql.append("	( ");
		sql.append("	 BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,CLASS_NA ");
		sql.append("	,DEPARTMENT_TYPE ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("	 '" + batchDt + "' AS BATCH_DT");
		sql.append("	 '" + yokuBatchDt + "' AS BATCH_DT");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_D + "' AS TOROKU_ID ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("	,WPCF.DEPARTMENT_CD  AS DEPARTMENT_CD ");
		//sql.append("	,WPCF.CLASS_CD  AS CLASS_CD ");
		//sql.append("	,WPCF.CLASS_NA  AS CLASS_NA ");
		//sql.append("	,WPCF.DEPARTMENT_TYPE  AS DEPARTMENT_TYPE ");
		sql.append("	,ZPCF.DEPARTMENT_CD  AS DEPARTMENT_CD ");
		sql.append("	,ZPCF.CLASS_CD  AS CLASS_CD ");
		sql.append("	,ZPCF.CLASS_NA  AS CLASS_NA ");
		sql.append("	,ZPCF.DEPARTMENT_TYPE  AS DEPARTMENT_TYPE ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append(" FROM ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("	 WK_POS_CLASS_FIVI WPCF ");
		sql.append("	 ZEN_POS_CLASS_FIVI ZPCF ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
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
		sql.append("			ZPCF.CLASS_CD ");
		sql.append("		FROM ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("			ZEN_POS_CLASS_FIVI ZPCF ");
		sql.append("			WK_POS_CLASS_FIVI WPCF ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("		WHERE ");
		sql.append("			ZPCF.CLASS_CD	= WPCF.CLASS_CD ");
		sql.append("		) ");
		return sql.toString();
	}

	/**
	 * IF_POS_SUBCLASSを登録(新規・訂正)するSQLを取得する
	 *
	 * @return IF_POS_SUBCLASS(新規・訂正)登録SQL
	 */
	private String getIfPosSubClassInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_SUBCLASS ");
		sql.append("	( ");
		sql.append("	 BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,CLASS_CD ");
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
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("	 '" + batchDt + "' AS BATCH_DT");
		sql.append("	 '" + yokuBatchDt + "' AS BATCH_DT");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "' AS TOROKU_ID ");
		sql.append("	,WPSC.CLASS_CD  AS CLASS_CD ");
		sql.append("	,WPSC.SUBCLASS_CD  AS SUBCLASS_CD ");
		sql.append("	,WPSC.SUBCLASS_NA  AS SUBCLASS_NA ");
		sql.append("	,WPSC.AEON_CARD_NEBIKI_FG  AS AEON_CARD_NEBIKI_FG ");
		sql.append("	,WPSC.CHG_VL_NEBIKI_BTN_FG  AS CHG_VL_NEBIKI_BTN_FG ");
		sql.append("	,WPSC.KAIIN_CARD_NEBIKI_RT  AS KAIIN_CARD_NEBIKI_RT ");
		sql.append("	,WPSC.MATERNITY_CARD_NEBIKI_RT  AS MATERNITY_CARD_NEBIKI_RT ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append(" FROM ");
		sql.append("	 WK_POS_SUBCLASS WPSC ");
		sql.append("	 LEFT JOIN ");
		sql.append("			ZEN_POS_SUBCLASS ZPSC ");
		sql.append("		ON ");
		sql.append("			ZPSC.SUBCLASS_CD	= WPSC.SUBCLASS_CD ");
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + batchDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + batchDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRT ");
		sql.append("WHERE ");
		sql.append("	NVL(WPSC.CLASS_CD, ' ')	<> NVL(ZPSC.CLASS_CD, ' ')	OR ");
		sql.append("	NVL(WPSC.SUBCLASS_NA, ' ')		<> NVL(ZPSC.SUBCLASS_NA, ' ') OR ");
		sql.append("	NVL(WPSC.CHG_VL_NEBIKI_BTN_FG, ' ')		<> NVL(ZPSC.CHG_VL_NEBIKI_BTN_FG, ' ') OR ");
		sql.append("	NVL(WPSC.KAIIN_CARD_NEBIKI_RT, ' ')		<> NVL(ZPSC.KAIIN_CARD_NEBIKI_RT, ' ') ");
		return sql.toString();
	}

	/**
	 * IF_POS_SUBCLASSを登録(削除)するSQLを取得する
	 *
	 * @return IF_POS_SUBCLASS(削除)登録SQL
	 */
	private String getIfPosSubClassDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_POS_SUBCLASS ");
		sql.append("	( ");
		sql.append("	 BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,CLASS_CD ");
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
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("	) ");
		sql.append("SELECT ");
		// #2238 Mod 2016.10.18 Li.Sheng (S)
		//sql.append("	 '" + batchDt + "' AS BATCH_DT");
		sql.append("	 '" + yokuBatchDt + "' AS BATCH_DT");
		// #2238 Mod 2016.10.18 Li.Sheng (E)
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_D + "' AS TOROKU_ID ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("	,WPSC.CLASS_CD  AS CLASS_CD ");
		//sql.append("	,WPSC.SUBCLASS_CD  AS SUBCLASS_CD ");
		//sql.append("	,WPSC.SUBCLASS_NA  AS SUBCLASS_NA ");
		//sql.append("	,WPSC.AEON_CARD_NEBIKI_FG  AS AEON_CARD_NEBIKI_FG ");
		//sql.append("	,WPSC.CHG_VL_NEBIKI_BTN_FG  AS CHG_VL_NEBIKI_BTN_FG ");
		//sql.append("	,WPSC.KAIIN_CARD_NEBIKI_RT  AS KAIIN_CARD_NEBIKI_RT ");
		//sql.append("	,WPSC.MATERNITY_CARD_NEBIKI_RT  AS MATERNITY_CARD_NEBIKI_RT ");
		sql.append("	,ZPSC.CLASS_CD  AS CLASS_CD ");
		sql.append("	,ZPSC.SUBCLASS_CD  AS SUBCLASS_CD ");
		sql.append("	,ZPSC.SUBCLASS_NA  AS SUBCLASS_NA ");
		sql.append("	,ZPSC.AEON_CARD_NEBIKI_FG  AS AEON_CARD_NEBIKI_FG ");
		sql.append("	,ZPSC.CHG_VL_NEBIKI_BTN_FG  AS CHG_VL_NEBIKI_BTN_FG ");
		sql.append("	,ZPSC.KAIIN_CARD_NEBIKI_RT  AS KAIIN_CARD_NEBIKI_RT ");
		sql.append("	,ZPSC.MATERNITY_CARD_NEBIKI_RT  AS MATERNITY_CARD_NEBIKI_RT ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append(" FROM ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("	 WK_POS_SUBCLASS WPSC ");
		sql.append("	 ZEN_POS_SUBCLASS ZPSC ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("	, ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'			AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
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
		sql.append("			ZPSC.SUBCLASS_CD ");
		sql.append("		FROM ");
		// #1327,#1340対応 2016.05.16 M.Kanno (S)
		//sql.append("			ZEN_POS_SUBCLASS ZPSC ");
		sql.append("			WK_POS_SUBCLASS WPSC ");
		// #1327,#1340対応 2016.05.16 M.Kanno (E)
		sql.append("		WHERE ");
		sql.append("			ZPSC.SUBCLASS_CD	= WPSC.SUBCLASS_CD ");
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

