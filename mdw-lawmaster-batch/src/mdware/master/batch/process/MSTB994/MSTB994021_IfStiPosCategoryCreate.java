package mdware.master.batch.process.MSTB994;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.common.command.MstbGetPluMaster;

/**
 *
 * <p>タイトル: MSTB994021_IfStiPosCategoryCreate.java クラス</p>
 * <p>説明　: WK_指定日POS部門メンテ、WK_指定日POSデプトメンテ、WK_指定日POSクラスメンテ、WK_指定日POSサブクラスメンテの内容を、
 * <br>IF_指定日POS部門メンテ、IF_指定日POSデプトメンテ、IF_指定日POSクラスメンテ、IF_指定日POSサブクラスメンテに取込む</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.01.16) 新規作成 #1749対応 T.Han
 * @version 1.01 (2021.12.20) #6406 KHOI.ND
 * @version 1.02 (2022.05.05) SIEU.D #6553
 *
 */
public class MSTB994021_IfStiPosCategoryCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF_STI_POS_BUMON = "IF_STI_POS_BUMON"; 			// IF_指定日_POS部門メンテ
	private static final String TABLE_IF_STI_POS_DPT = "IF_STI_POS_DPT"; 				// IF_指定日_POSデプトメンテ
	private static final String TABLE_IF_STI_POS_CLASS_FIVI = "IF_STI_POS_CLASS_FIVI"; 	// IF_指定日_POSクラスメンテ
	private static final String TABLE_IF_STI_POS_SUBCLASS = "IF_STI_POS_SUBCLASS"; 		// IF_指定日_POSサブクラスメンテ
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** 店舗コード 桁数 */
	private static final String TENPO_CD_LENGTH = "6";
	/** 登録ID 新規・訂正 */
	private static final String TOROKU_ID_A = "A";
	/** 送信回数 */
	private static final String SEND_QT = "01";
    /** 伝送ヘッダーレコードリスト */
    private List densoRecordList = null;
    /** 受付No */
    private String uketsukeNo = "";
    /** 店舗コード */
    private String tenpoCd = "";
    /** 対象日 */
    private String taisyoDt = "";

    /**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB994021_IfStiPosCategoryCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB994021_IfStiPosCategoryCreate() {
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
			int iRec1 = 0;
			int iRec2 = 0;
			int iRec3 = 0;
			int iRec4 = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			//バッチ日付取得
			String batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDate);

			// IF_指定日_POS部門メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_指定日_POS部門メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_STI_POS_BUMON);
			writeLog(Level.INFO_INT, "IF_指定日_POS部門メンテ削除処理を終了します。");

			// IF_指定日_POSデプトメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_指定日_POSデプトメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_STI_POS_DPT);
			writeLog(Level.INFO_INT, "IF_指定日_POSデプトメンテ削除処理を終了します。");

			// IF_指定日_POSクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_指定日_POSクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_STI_POS_CLASS_FIVI);
			writeLog(Level.INFO_INT, "IF_指定日_POSクラスメンテ削除処理を終了します。");

			// IF_指定日_POSサブクラスメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_指定日_POSサブクラスメンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_STI_POS_SUBCLASS);
			writeLog(Level.INFO_INT, "IF_指定日_POSサブクラスメンテ削除処理を終了します。");

            // PLU指定日マスタ取得
			ResultSet rsData = new MstbGetPluMaster().process(db);

            densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 受付No、店舗コード、対象日取得
            	uketsukeNo = rsData.getString("UKETSUKE_NO");
                tenpoCd = rsData.getString("TENPO_CD");
                taisyoDt = rsData.getString("TAISYO_DT");
                MSTB994021_IfStiPosCategoryCreateRow rowData = new MSTB994021_IfStiPosCategoryCreateRow(uketsukeNo, tenpoCd, taisyoDt);
                densoRecordList.add(rowData);
            }

			writeLog(Level.INFO_INT, "IF_指定日_POS部門メンテ登録処理（WK→IF）を開始します。");
			writeLog(Level.INFO_INT, "IF_指定日_POSデプトメンテ登録処理（WK→IF）を開始します。");
			writeLog(Level.INFO_INT, "IF_指定日_POSクラスメンテ登録処理（WK→IF）を開始します。");
			writeLog(Level.INFO_INT, "IF_指定日_POSサブクラスメンテ登録処理（WK→IF）を開始します。");

			for (Object densoRecord : densoRecordList) {
            	MSTB994021_IfStiPosCategoryCreateRow rowData = (MSTB994021_IfStiPosCategoryCreateRow) densoRecord;
            	uketsukeNo = rowData.getUketsukeNo();
                tenpoCd = rowData.getTenpoCd();
                taisyoDt = rowData.getTaisyoDt();
    			// IF_指定日_POS部門メンテの登録
    			iRec1 += db.executeUpdate(getIfStiPosBumonInsertSql(uketsukeNo, tenpoCd, taisyoDt));
    			// IF_指定日_POSデプトメンテの登録
    			iRec2 += db.executeUpdate(getIfStiPosDPTInsertSql(uketsukeNo, tenpoCd, taisyoDt));
    			// IF_指定日_POSクラスメンテの登録
    			iRec3 += db.executeUpdate(getIfStiPosClassFiviInsertSql(uketsukeNo, tenpoCd, taisyoDt));
    			// IF_指定日_POSサブクラスメンテの登録
    			iRec4 += db.executeUpdate(getIfStiPosSubClassInsertSql(uketsukeNo, tenpoCd, taisyoDt));

    			db.commit();
			}

			writeLog(Level.INFO_INT, "IF_指定日_POS部門メンテを" + iRec1 + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POSデプトメンテを" + iRec2 + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POSクラスメンテを" + iRec3 + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POSサブクラスメンテを" + iRec4 + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POS部門メンテ登録処理（WK→IF）を終了します。");
			writeLog(Level.INFO_INT, "IF_指定日_POSデプトメンテ登録処理（WK→IF）を終了します。");
			writeLog(Level.INFO_INT, "IF_指定日_POSクラスメンテ登録処理（WK→IF）を終了します。");
			writeLog(Level.INFO_INT, "IF_指定日_POSサブクラスメンテ登録処理（WK→IF）を終了します。");

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
	 * IF_STI_POS_BUMONを登録するSQLを取得する
	 *
	 * @return IF_STI_POS_BUMON登録SQL
	 */
	private String getIfStiPosBumonInsertSql(String uketsukeNo, String tenpoCd, String taisyoDt) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_STI_POS_BUMON ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,EIGYO_DT ");
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
		sql.append("	,GYOTAI_KB ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + uketsukeNo + "' AS UKETSUKE_NO");
		sql.append("	,'" + taisyoDt + "' AS TAISYO_DT");
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "' AS TOROKU_ID ");
		sql.append("	,WSPB.DIVISION_CD AS DIVISION_CD");
		sql.append("	,WSPB.DIVISION_NA AS DIVISION_NA");
		sql.append("	,WSPB.PRIME_GROUP AS PRIME_GROUP");
		sql.append("	,WSPB.PRIME_GROUP_NA AS PRIME_GROUP_NA");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		sql.append(" FROM ");
		sql.append("	 WK_STI_POS_BUMON WSPB ");
//		sql.append("	 LEFT JOIN ");
//		sql.append("			ZEN_POS_BUMON ZPB ");
//		sql.append("		ON ");
//		sql.append("			ZPB.DIVISION_CD	= WSPB.DIVISION_CD ");
		sql.append("	, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("			,TRT.GYOTAI_KB");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'	AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		sql.append("			TRT.TENPO_CD		 = '" + tenpoCd + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + taisyoDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + taisyoDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("	) TRT ");

		return sql.toString();
	}

	/**
	 * IF_STI_POS_DPT登録するSQLを取得する
	 *
	 * @return IF_STI_POS_DPT登録SQL
	 */
	private String getIfStiPosDPTInsertSql(String uketsukeNo, String tenpoCd, String taisyoDt) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_STI_POS_DPT ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,EIGYO_DT ");
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
		sql.append("	,GYOTAI_KB");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + uketsukeNo + "' AS UKETSUKE_NO");
		sql.append("	,'" + taisyoDt + "' AS TAISYO_DT");
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "' AS TOROKU_ID ");
		sql.append("	,WSPD.DIVISION_CD  AS DIVISION_CD ");
		sql.append("	,WSPD.DEPARTMENT_CD  AS DEPARTMENT_CD ");
		sql.append("	,WSPD.DEPARTMENT_NA  AS DEPARTMENT_NA ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		sql.append(" FROM ");
		sql.append("	 WK_STI_POS_DPT WSPD ");
//		sql.append("	 LEFT JOIN ");
//		sql.append("			ZEN_POS_DPT ZPD ");
//		sql.append("		ON ");
//		sql.append("			ZPD.DEPARTMENT_CD	= WSPD.DEPARTMENT_CD ");
		sql.append("	, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("			,TRT.GYOTAI_KB");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'	AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		sql.append("			TRT.TENPO_CD		 = '" + tenpoCd + "'	AND ");
        // #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + taisyoDt + "'	AND ");
        // #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + taisyoDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("	) TRT ");

		return sql.toString();
	}

	/**
	 * IF_STI_POS_CLASS_FIVI登録するSQLを取得する
	 *
	 * @return IF_STI_POS_CLASS_FIVI登録SQL
	 */
	private String getIfStiPosClassFiviInsertSql(String uketsukeNo, String tenpoCd, String taisyoDt) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_STI_POS_CLASS_FIVI ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,EIGYO_DT ");
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
		sql.append("	,GYOTAI_KB");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + uketsukeNo + "' AS UKETSUKE_NO");
		sql.append("	,'" + taisyoDt + "' AS TAISYO_DT");
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "' AS TOROKU_ID ");
		sql.append("	,WSPCF.DEPARTMENT_CD  AS DEPARTMENT_CD ");
		sql.append("	,WSPCF.CLASS_CD  AS CLASS_CD ");
		sql.append("	,WSPCF.CLASS_NA  AS CLASS_NA ");
		sql.append("	,WSPCF.DEPARTMENT_TYPE  AS DEPARTMENT_TYPE ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		sql.append(" FROM ");
		sql.append("	 WK_STI_POS_CLASS_FIVI WSPCF ");
		sql.append("	, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("			,TRT.GYOTAI_KB");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'	AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		sql.append("			TRT.TENPO_CD		 = '" + tenpoCd + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + taisyoDt + "'	AND ");
		// #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + taisyoDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("	) TRT ");

		return sql.toString();
	}

	/**
	 * IF_STI_POS_SUBCLASS登録するSQLを取得する
	 *
	 * @return IF_STI_POS_SUBCLASS登録SQL
	 */
	private String getIfStiPosSubClassInsertSql(String uketsukeNo, String tenpoCd, String taisyoDt) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_STI_POS_SUBCLASS ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,EIGYO_DT ");
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
		sql.append("	,GYOTAI_KB");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + uketsukeNo + "' AS UKETSUKE_NO");
		sql.append("	,'" + taisyoDt + "' AS TAISYO_DT");
		sql.append("	,LPAD(TRIM(TRT.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT + "' AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "' AS TOROKU_ID ");
		sql.append("	,WSPS.CLASS_CD  AS CLASS_CD ");
		sql.append("	,WSPS.SUBCLASS_CD  AS SUBCLASS_CD ");
		sql.append("	,WSPS.SUBCLASS_NA  AS SUBCLASS_NA ");
		sql.append("	,WSPS.AEON_CARD_NEBIKI_FG  AS AEON_CARD_NEBIKI_FG ");
		sql.append("	,WSPS.CHG_VL_NEBIKI_BTN_FG  AS CHG_VL_NEBIKI_BTN_FG ");
		sql.append("	,WSPS.KAIIN_CARD_NEBIKI_RT  AS KAIIN_CARD_NEBIKI_RT ");
		sql.append("	,WSPS.MATERNITY_CARD_NEBIKI_RT  AS MATERNITY_CARD_NEBIKI_RT ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		sql.append(" FROM ");
		sql.append("	 WK_STI_POS_SUBCLASS WSPS ");
//		sql.append("	 LEFT JOIN ");
//		sql.append("			ZEN_POS_SUBCLASS ZPS ");
//		sql.append("		ON ");
//		sql.append("			ZPS.SUBCLASS_CD	= WSPS.SUBCLASS_CD ");
		sql.append("	, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("			,TRT.GYOTAI_KB");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'	AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		sql.append("			TRT.TENPO_CD		 = '" + tenpoCd + "'	AND ");
        // #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + taisyoDt + "'	AND ");
        // #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + taisyoDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("	) TRT ");

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

    /**
     * <p>タイトル: MSTB992071_PosFileCreateRow クラス</p>
     * <p>説明　: 伝送ヘッダーデータを保持する</p>
     *
     */
    class MSTB994021_IfStiPosCategoryCreateRow {

        /** 受付No */
        private String uketsukeNo;
        /** 店舗コード */
        private String tenpoCd;
        /** 対象日 */
        private String taisyoDt;

        /**
         * MSTB992071_PosFileCreateRow を生成
         *
         * @param uketsukeNo 受付No
         * @param tenpoCd 店舗コード
         * @param taisyoDt 対象日
         */
        public MSTB994021_IfStiPosCategoryCreateRow(String uketsukeNo, String tenpoCd, String taisyoDt) {
            this.uketsukeNo = uketsukeNo;
            this.tenpoCd = tenpoCd;
            this.taisyoDt = taisyoDt;
        }

        /**
         * 受付Noを取得します。
         * @return 受付No
         */
        public String getUketsukeNo() {
            return uketsukeNo;
        }

        /**
         * 店舗コードを取得します。
         * @return 店舗コード
         */
        public String getTenpoCd() {
            return tenpoCd;
        }
        
        /**
         * 対象日を取得します。
         * @return 対象日
         */
        public String getTaisyoDt() {
            return taisyoDt;
        }

    }

}

