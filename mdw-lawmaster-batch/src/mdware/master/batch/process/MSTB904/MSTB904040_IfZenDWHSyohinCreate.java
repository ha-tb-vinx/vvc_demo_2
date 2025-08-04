package mdware.master.batch.process.MSTB904;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
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
import mdware.master.common.dictionary.mst007501_ZeiKbDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB904040_IfZenDWHSyohinCreate.java クラス</p>
 * <p>説明　: WK_DWH_商品マスタから正常データの内容を、ZEN_IF_DWH_商品マスタに取込む（新規分・修正分）<br>
 *            ZEN_ZEN_IF_DWH_商品マスタの内容からWK_DWH_商品マスタに含まれないデータをを、ZEN_IF_DWH_商品マスタに取込む（削除分）</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 1.0 2014/06/18 Ha.ntt 海外LAWSON様通貨対応
 * @version 1.1 2015/07/21 Sou ORACLE11対応
 *
 */
public class MSTB904040_IfZenDWHSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_ZEN_IF = "ZEN_IF_DWH_SYOHIN"; // ZEN_IF_DWH_商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** カテゴリ文字列長 */
	private static final String CATEGORY_LENGTH = "6";
	/** 定数ZERO */
	private static final String CONST_ZERO = "0";
	/** 税区分 内税 */
	private static final String UTIZEI = mst007501_ZeiKbDictionary.UTIZEI.getCode();
	/** 丸め区分 切捨 */
	private static final String MARUME_KIRISUTE = "0";
	/** 丸め区分 四捨五入 */
	private static final String MARUME_SISYAGONYU = "1";
	/** DWH商品Ｍ税処理日 */
	private static final String DWH_TAX_SYORI_DT = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.DWH_TAX_SYORI_DT);
	/** 値入率：切上げ用補正値 */
	private static final String NEIRE_RT_ROUNDUP_CORRECTION_VALUE = "10000";

	// バッチ日付
	private String batchDt = null;
	// バッチ日付
	private String batchAddDt = null;
	// システム日付
	private String systemDt = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB904040_IfZenDWHSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB904040_IfZenDWHSyohinCreate() {
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
			// バッチ日付
			batchAddDt = DateChanger.addDate(batchDt, 1);
			// システム日付取得
			systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );

			// ZEN_IF_DWH_商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_ZEN_IF);
			writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタを削除処理を終了します。");

			// ZEN_IF_DWH_商品マスタの登録(新規分・修正分)
			writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタ(新規分・修正分)登録処理（WK→ZEN_IF）を開始します。");
			iRec = db.executeUpdate(getZenIfDwhSyohinInsertSql());
			writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタ(新規分・修正分)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタ(新規分・修正分)登録処理（WK→ZEN_IF）を終了します。");

			db.commit();

			// ZEN_IF_DWH_商品マスタの登録(削除分)
			writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタ(削除分)登録処理（BK→ZEN_IF）を開始します。");
			iRec = db.executeUpdate(getZenIfDwhSyohinDelInsertSql());
			writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタ(削除分)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタ(削除分)登録処理（BK→ZEN_IF）を終了します。");

			db.commit();

			writeLog(Level.INFO_INT, "DWH商品M税処理日は [" + DWH_TAX_SYORI_DT + "] です。");

			if (DWH_TAX_SYORI_DT.compareTo(batchDt) <= 0) {

				writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタの消費税対応更新処理を開始します。");

				// ZEN_IF_DWH_商品マスタの更新(消費税剥がし対応)
				iRec = db.executeUpdate(getZenIfDwhSyohinUpdSql());
				writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタを" + iRec + "件更新しました。");
				writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタ消費税対応更新処理を終了します。");

				db.commit();

			} else {

				writeLog(Level.INFO_INT, "バッチ日付が、DWH商品M税処理日より過去です。");
				writeLog(Level.INFO_INT, "ZEN_IF_DWH_商品マスタの消費税対応更新処理は行いません。");

			}

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
	 * ZEN_IF_DWH_SYOHINを登録するSQLを取得する(新規分・修正分)
	 *
	 * @return ZEN_IF_DWH_SYOHIN登録SQL(新規分・修正分)
	 */
	private String getZenIfDwhSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO ZEN_IF_DWH_SYOHIN ");
		sql.append("	( ");
		sql.append("	 SYORI_DT ");
		sql.append("	,SYORI_TM ");
		sql.append("	,KIGYO_1_CD ");
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
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	,'" + systemDt + "' ");
		sql.append("	,WDS.KIGYO_1_CD ");
		sql.append("	,TO_CHAR(TO_NUMBER(WDS.SYOHIN_CD)) ");
		sql.append("	,LPAD(TRIM(WDS.BUNRUI1_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	,LPAD(TRIM(WDS.BUNRUI2_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	,LPAD(TRIM(WDS.BUNRUI5_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	,WDS.CATEGORY_4_CD ");
		sql.append("	,WDS.CATEGORY_5_CD ");
		sql.append("	,WDS.HINMEI_KANJI_NA ");
		sql.append("	,WDS.HINMEI_KANA_NA ");
		sql.append("	,WDS.GENTANKA_VL ");
		sql.append("	,WDS.BAITANKA_VL ");
		sql.append("	,WDS.NEIRE_RT ");
		sql.append("	,TO_CHAR(TO_NUMBER(WDS.MAKER_CD)) ");
		sql.append("	,TO_CHAR(TO_NUMBER(WDS.SIIRESAKI_CD)) ");
		sql.append("	,WDS.ATSUKAI_ST_DT ");
		sql.append("	,WDS.ATSUKAI_ED_DT ");
		sql.append("	,WDS.ZOKUSEI_1_KB ");
		sql.append("	,WDS.ZOKUSEI_2_KB ");
		sql.append("	,WDS.ZOKUSEI_3_KB ");
		sql.append("	,WDS.ZOKUSEI_4_KB ");
		sql.append("	,WDS.ZOKUSEI_5_KB ");
		sql.append("	,WDS.COMMENT_1_TX ");
		sql.append("	,WDS.COMMENT_2_TX ");
		sql.append("	,WDS.COMMENT_3_TX ");
		sql.append("	,WDS.COMMENT_4_TX ");
		sql.append("	,WDS.COMMENT_5_TX ");
		sql.append("	," + mst000102_IfConstDictionary.DWH_DELETE_KB_ACTIVE + " ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_DWH_SYOHIN WDS ");
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("			SELECT ");
		sql.append("				 ZDS.KIGYO_1_CD ");
		sql.append("				,ZDS.SYOHIN_CD ");
		sql.append("				,ZDS.BUNRUI1_CD ");
		sql.append("				,ZDS.BUNRUI2_CD ");
		sql.append("				,ZDS.BUNRUI5_CD ");
		sql.append("				,ZDS.CATEGORY_4_CD ");
		sql.append("				,ZDS.CATEGORY_5_CD ");
		sql.append("				,ZDS.HINMEI_KANJI_NA ");
		sql.append("				,ZDS.HINMEI_KANA_NA ");
		sql.append("				,ZDS.GENTANKA_VL ");
		sql.append("				,ZDS.BAITANKA_VL ");
		sql.append("				,ZDS.NEIRE_RT ");
		sql.append("				,ZDS.MAKER_CD ");
		sql.append("				,ZDS.SIIRESAKI_CD ");
		sql.append("				,ZDS.ATSUKAI_ST_DT ");
		sql.append("				,ZDS.ATSUKAI_ED_DT ");
		sql.append("				,ZDS.ZOKUSEI_1_KB ");
		sql.append("				,ZDS.ZOKUSEI_2_KB ");
		sql.append("				,ZDS.ZOKUSEI_3_KB ");
		sql.append("				,ZDS.ZOKUSEI_4_KB ");
		sql.append("				,ZDS.ZOKUSEI_5_KB ");
		sql.append("				,ZDS.COMMENT_1_TX ");
		sql.append("				,ZDS.COMMENT_2_TX ");
		sql.append("				,ZDS.COMMENT_3_TX ");
		sql.append("				,ZDS.COMMENT_4_TX ");
		sql.append("				,ZDS.COMMENT_5_TX ");
		sql.append("				,ZDS.DELETE_FG ");
		sql.append("				,ZDS.ERR_KB ");
		sql.append("			FROM ");
		sql.append("				ZEN_DWH_SYOHIN ZDS ");
		sql.append("			WHERE ");
		sql.append("				ZDS.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");
		sql.append("		) ZDS ");
		sql.append("		ON ");
		sql.append("			ZDS.KIGYO_1_CD	= WDS.KIGYO_1_CD	AND ");
		sql.append("			ZDS.SYOHIN_CD	= WDS.SYOHIN_CD ");
		sql.append("WHERE ");
		sql.append("	WDS.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'	AND ");
		sql.append("	( ");
		sql.append("		NVL(WDS.KIGYO_1_CD, ' ')		<> NVL(ZDS.KIGYO_1_CD, ' ')			OR ");
		sql.append("		NVL(WDS.SYOHIN_CD, ' ')			<> NVL(ZDS.SYOHIN_CD, ' ')			OR ");
		sql.append("		NVL(WDS.BUNRUI1_CD, ' ')		<> NVL(ZDS.BUNRUI1_CD, ' ')			OR ");
		sql.append("		NVL(WDS.BUNRUI2_CD, ' ')		<> NVL(ZDS.BUNRUI2_CD, ' ')			OR ");
		sql.append("		NVL(WDS.BUNRUI5_CD, ' ')		<> NVL(ZDS.BUNRUI5_CD, ' ')			OR ");
		sql.append("		NVL(WDS.CATEGORY_4_CD, ' ')		<> NVL(ZDS.CATEGORY_4_CD, ' ')		OR ");
		sql.append("		NVL(WDS.CATEGORY_5_CD, ' ')		<> NVL(ZDS.CATEGORY_5_CD, ' ')		OR ");
		sql.append("		NVL(WDS.HINMEI_KANJI_NA, ' ')	<> NVL(ZDS.HINMEI_KANJI_NA, ' ')	OR ");
		sql.append("		NVL(WDS.HINMEI_KANA_NA, ' ')	<> NVL(ZDS.HINMEI_KANA_NA, ' ')		OR ");
		sql.append("		NVL(WDS.GENTANKA_VL, 0)			<> NVL(ZDS.GENTANKA_VL, 0)			OR ");
		sql.append("		NVL(WDS.BAITANKA_VL, 0)			<> NVL(ZDS.BAITANKA_VL, 0)			OR ");
		sql.append("		NVL(WDS.NEIRE_RT, 0)			<> NVL(ZDS.NEIRE_RT, 0)				OR ");
		sql.append("		NVL(WDS.MAKER_CD, ' ')			<> NVL(ZDS.MAKER_CD, ' ')			OR ");
		sql.append("		NVL(WDS.SIIRESAKI_CD, ' ')		<> NVL(ZDS.SIIRESAKI_CD, ' ')		OR ");
		sql.append("		NVL(WDS.ATSUKAI_ST_DT, ' ')		<> NVL(ZDS.ATSUKAI_ST_DT, ' ')		OR ");
		sql.append("		NVL(WDS.ATSUKAI_ED_DT, ' ')		<> NVL(ZDS.ATSUKAI_ED_DT, ' ')		OR ");
		sql.append("		NVL(WDS.ZOKUSEI_1_KB, ' ')		<> NVL(ZDS.ZOKUSEI_1_KB, ' ')		OR ");
		sql.append("		NVL(WDS.ZOKUSEI_2_KB, ' ')		<> NVL(ZDS.ZOKUSEI_2_KB, ' ')		OR ");
		sql.append("		NVL(WDS.ZOKUSEI_3_KB, ' ')		<> NVL(ZDS.ZOKUSEI_3_KB, ' ')		OR ");
		sql.append("		NVL(WDS.ZOKUSEI_4_KB, ' ')		<> NVL(ZDS.ZOKUSEI_4_KB, ' ')		OR ");
		sql.append("		NVL(WDS.ZOKUSEI_5_KB, ' ')		<> NVL(ZDS.ZOKUSEI_5_KB, ' ')		OR ");
		sql.append("		NVL(WDS.COMMENT_1_TX, ' ')		<> NVL(ZDS.COMMENT_1_TX, ' ')		OR ");
		sql.append("		NVL(WDS.COMMENT_2_TX, ' ')		<> NVL(ZDS.COMMENT_2_TX, ' ')		OR ");
		sql.append("		NVL(WDS.COMMENT_3_TX, ' ')		<> NVL(ZDS.COMMENT_3_TX, ' ')		OR ");
		sql.append("		NVL(WDS.COMMENT_4_TX, ' ')		<> NVL(ZDS.COMMENT_4_TX, ' ')		OR ");
		sql.append("		NVL(WDS.COMMENT_5_TX, ' ')		<> NVL(ZDS.COMMENT_5_TX, ' ')		OR ");
		sql.append("		NVL(WDS.DELETE_FG, ' ')			<> NVL(ZDS.DELETE_FG, ' ')			OR ");
		sql.append("		NVL(WDS.ERR_KB, ' ')			<> NVL(ZDS.ERR_KB, ' ') ");
		sql.append("	) ");

		return sql.toString();
	}

	/**
	 * ZEN_IF_DWH_SYOHINを登録するSQLを取得する(削除分)
	 *
	 * @return ZEN_IF_DWH_SYOHIN登録SQL(削除分)
	 */
	private String getZenIfDwhSyohinDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO ZEN_IF_DWH_SYOHIN ");
		sql.append("	( ");
		sql.append("	 SYORI_DT ");
		sql.append("	,SYORI_TM ");
		sql.append("	,KIGYO_1_CD ");
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
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	,'" + systemDt + "' ");
		sql.append("	,ZDS.KIGYO_1_CD ");
		sql.append("	,TO_CHAR(TO_NUMBER(ZDS.SYOHIN_CD)) ");
		sql.append("	,LPAD(TRIM(ZDS.BUNRUI1_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	,LPAD(TRIM(ZDS.BUNRUI2_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	,LPAD(TRIM(ZDS.BUNRUI5_CD), " + CATEGORY_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	,ZDS.CATEGORY_4_CD ");
		sql.append("	,ZDS.CATEGORY_5_CD ");
		sql.append("	,ZDS.HINMEI_KANJI_NA ");
		sql.append("	,ZDS.HINMEI_KANA_NA ");
		sql.append("	,ZDS.GENTANKA_VL ");
		sql.append("	,ZDS.BAITANKA_VL ");
		sql.append("	,ZDS.NEIRE_RT ");
		sql.append("	,TO_CHAR(TO_NUMBER(ZDS.MAKER_CD)) ");
		sql.append("	,ZDS.SIIRESAKI_CD ");
		sql.append("	,ZDS.ATSUKAI_ST_DT ");
		sql.append("	,ZDS.ATSUKAI_ED_DT ");
		sql.append("	,ZDS.ZOKUSEI_1_KB ");
		sql.append("	,ZDS.ZOKUSEI_2_KB ");
		sql.append("	,ZDS.ZOKUSEI_3_KB ");
		sql.append("	,ZDS.ZOKUSEI_4_KB ");
		sql.append("	,ZDS.ZOKUSEI_5_KB ");
		sql.append("	,ZDS.COMMENT_1_TX ");
		sql.append("	,ZDS.COMMENT_2_TX ");
		sql.append("	,ZDS.COMMENT_3_TX ");
		sql.append("	,ZDS.COMMENT_4_TX ");
		sql.append("	,ZDS.COMMENT_5_TX ");
		sql.append("	," + mst000102_IfConstDictionary.DWH_DELETE_KB_HAISI + " ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	ZEN_DWH_SYOHIN ZDS ");
		sql.append("WHERE ");
		sql.append("	ZDS.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'	AND ");
		sql.append("	NOT EXISTS ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			 WDS.KIGYO_1_CD ");
		sql.append("			,WDS.SYOHIN_CD ");
		sql.append("		FROM ");
		sql.append("			WK_DWH_SYOHIN WDS ");
		sql.append("		WHERE ");
		sql.append("			WDS.KIGYO_1_CD	= ZDS.KIGYO_1_CD	AND ");
		sql.append("			WDS.SYOHIN_CD	= ZDS.SYOHIN_CD ");
		sql.append("	) ");

		return sql.toString();
	}

	/**
	 * 消費税対応
	 * システムコントロールマスタ.DWH商品Ｍ税有効日から連携する商品マスタは税抜とする。
	 * @return
	 * @throws SQLException
	 */
	private String getZenIfDwhSyohinUpdSql() throws SQLException {

		StringBuilder sql = new StringBuilder();
		// 2015/07/21 Sou ORACLE11対応 Start
//		sql.append(" UPDATE                                                                                    ");
//		sql.append("     (                                                                                     ");
//		sql.append("         SELECT /*+ BYPASS_UJVC */                                                         ");
//		sql.append("             ZIDS.GENTANKA_VL          AS GENTANKA_VL                                      ");
//		sql.append("             , ZIDS.BAITANKA_VL        AS BAITANKA_VL                                      ");
//		sql.append("             , ZIDS.NEIRE_RT           AS NEIRE_RT                                         ");
//		sql.append("             , ZIDS.UPDATE_USER_ID     AS UPDATE_USER_ID                                   ");
//		sql.append("             , ZIDS.UPDATE_TS          AS UPDATE_TS                                        ");
//		sql.append("             , GET_ZEINUKI_MARUME(NVL(ZIDS.GENTANKA_VL,0) * 100, " + MoneyUtil.getFractionCostUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
//		sql.append(MoneyUtil.getFractionCostUnitMode() + ") / 100  AS UPD_GENTANKA_VL ");
//		sql.append("             , GET_ZEINUKI_MARUME(NVL(ZIDS.BAITANKA_VL,0), " + MoneyUtil.getFractionSellUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
//		sql.append(MoneyUtil.getFractionSellUnitMode() + ")        AS UPD_BAITANKA_VL ");
//		sql.append("         FROM                                                                              ");
//		sql.append("             ZEN_IF_DWH_SYOHIN ZIDS                                                        ");
//		sql.append("         INNER JOIN                                                                        ");
//		sql.append("             R_TAX_RATE RTR                                                                ");
//		sql.append("         ON                                                                                ");
//		sql.append("             RTR.TAX_RATE_KB = ZIDS.ZOKUSEI_1_KB          AND                              ");
//		sql.append("             RTR.YUKO_DT     = (                                                           ");
//		sql.append("                                   SELECT                                                  ");
//		sql.append("                                       MAX(SUB.YUKO_DT)                                    ");
//		sql.append("                                   FROM                                                    ");
//		sql.append("                                       R_TAX_RATE SUB                                      ");
//		sql.append("                                   WHERE                                                   ");
//		sql.append("                                       SUB.TAX_RATE_KB = RTR.TAX_RATE_KB        AND        ");
//		sql.append("                                       SUB.YUKO_DT    <= '" + batchAddDt + "'              ");  // ※3月31日時点で4月1日(税率が変わる日)有効の税率マスタを取得したいので、ここはバッチ日付+1日とする
//		sql.append("                                )                                                          ");
//		sql.append("         WHERE                                                                             ");
//		sql.append("             ZIDS.ZOKUSEI_1_KB           = '" + UTIZEI + "'                                ");
//		sql.append("     )                                                                                     ");
//		sql.append(" SET                                                                                       ");
//		sql.append("      GENTANKA_VL = UPD_GENTANKA_VL                                                        ");
//		sql.append("     ,BAITANKA_VL = UPD_BAITANKA_VL                                                        ");
//		sql.append("     ,UPDATE_USER_ID = '" + userLog.getJobId() + "UPD" + " '                               ");
//		sql.append("     ,UPDATE_TS   = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
//		sql.append("     ,NEIRE_RT    = (CASE                                                                  ");
//		sql.append("                         WHEN                                                              ");
//		sql.append("                             UPD_GENTANKA_VL IS NOT NULL              AND                  ");
//		sql.append("                             UPD_BAITANKA_VL IS NOT NULL              AND                  ");
//		sql.append("                             UPD_BAITANKA_VL <> '" + CONST_ZERO + "'  AND                  ");
//		sql.append("                             UPD_BAITANKA_VL > UPD_GENTANKA_VL                             ");
//		sql.append("                         THEN                                                              ");
//		sql.append("                             CEIL(((UPD_BAITANKA_VL - UPD_GENTANKA_VL) / UPD_BAITANKA_VL) * " + NEIRE_RT_ROUNDUP_CORRECTION_VALUE + ") / " + NEIRE_RT_ROUNDUP_CORRECTION_VALUE + " ");
//		sql.append("                         ELSE                                                              ");
//		sql.append("                             " + CONST_ZERO + "                                            ");
//		sql.append("                         END                                                               ");
//		sql.append("                     )                                                                     ");

		sql.append(" MERGE INTO ");
		sql.append("     ZEN_IF_DWH_SYOHIN ZIDS ");
		sql.append(" USING ");
		sql.append("    R_TAX_RATE RTR ");
		sql.append(" ON ( RTR.TAX_RATE_KB = ZIDS.ZOKUSEI_1_KB ");
		sql.append("     AND RTR.YUKO_DT     = ( ");
		sql.append("                        SELECT ");
		sql.append("                            MAX(SUB.YUKO_DT) ");
		sql.append("                        FROM ");
		sql.append("                            R_TAX_RATE SUB ");
		sql.append("                        WHERE ");
		sql.append("                            SUB.TAX_RATE_KB = RTR.TAX_RATE_KB        AND ");
		sql.append("                            SUB.YUKO_DT    <= '" + batchAddDt + "' ");
		sql.append("                       ) ");
		sql.append("     AND ZIDS.ZOKUSEI_1_KB           = '" + UTIZEI + "' ) ");
		sql.append(" WHEN MATCHED THEN ");
		sql.append("  UPDATE SET ");
		sql.append("       ZIDS.GENTANKA_VL = GET_ZEINUKI_MARUME(NVL(ZIDS.GENTANKA_VL,0) * 100, " + MoneyUtil.getFractionCostUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                              " + MoneyUtil.getFractionCostUnitMode() + ") / 100 ");
		sql.append("      ,ZIDS.BAITANKA_VL = GET_ZEINUKI_MARUME(NVL(ZIDS.BAITANKA_VL,0), " + MoneyUtil.getFractionSellUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                              " + MoneyUtil.getFractionSellUnitMode() + ") ");
		sql.append("      ,ZIDS.UPDATE_USER_ID = '" + userLog.getJobId() + "UPD" + " ' ");
		sql.append("      ,ZIDS.UPDATE_TS   = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("      ,ZIDS.NEIRE_RT    = (CASE ");
		sql.append("                          WHEN ");
		sql.append("                              GET_ZEINUKI_MARUME(NVL(ZIDS.GENTANKA_VL,0) * 100, " + MoneyUtil.getFractionCostUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                                      " + MoneyUtil.getFractionCostUnitMode() + ") / 100 IS NOT NULL AND ");
		sql.append("                              GET_ZEINUKI_MARUME(NVL(ZIDS.BAITANKA_VL,0), " + MoneyUtil.getFractionSellUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                                      " + MoneyUtil.getFractionSellUnitMode() + ")  IS NOT NULL AND ");
		sql.append("                              GET_ZEINUKI_MARUME(NVL(ZIDS.BAITANKA_VL,0), " + MoneyUtil.getFractionSellUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                                      " + MoneyUtil.getFractionSellUnitMode() + ")  <> '" + CONST_ZERO + "'  AND ");
		sql.append("                              GET_ZEINUKI_MARUME(NVL(ZIDS.BAITANKA_VL,0), " + MoneyUtil.getFractionSellUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                                      " + MoneyUtil.getFractionSellUnitMode() + ")  > ");
		sql.append("                              GET_ZEINUKI_MARUME(NVL(ZIDS.GENTANKA_VL,0) * 100, " + MoneyUtil.getFractionCostUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                                      " + MoneyUtil.getFractionCostUnitMode() + ") / 100 ");
		sql.append("                          THEN ");
		sql.append("                              CEIL(((GET_ZEINUKI_MARUME(NVL(ZIDS.BAITANKA_VL,0), " + MoneyUtil.getFractionSellUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                                             " + MoneyUtil.getFractionSellUnitMode() + ")  - ");
		sql.append("                                     GET_ZEINUKI_MARUME(NVL(ZIDS.GENTANKA_VL,0) * 100, " + MoneyUtil.getFractionCostUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                                             " + MoneyUtil.getFractionCostUnitMode() + ") / 100) / ");
		sql.append("                                     GET_ZEINUKI_MARUME(NVL(ZIDS.BAITANKA_VL,0), " + MoneyUtil.getFractionSellUnitLen() + ", RTR.TAX_RATE_KB, RTR.TAX_RT, ");
		sql.append("                                             " + MoneyUtil.getFractionSellUnitMode() + ") ) * " + NEIRE_RT_ROUNDUP_CORRECTION_VALUE + ") / " + NEIRE_RT_ROUNDUP_CORRECTION_VALUE + " ");
		sql.append("                          ELSE ");
		sql.append("                              " + CONST_ZERO + " ");
		sql.append("                          END ");
		sql.append("                        ) ");
		// 2015/07/21 Sou ORACLE11対応 End
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
