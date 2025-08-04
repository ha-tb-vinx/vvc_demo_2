package mdware.master.batch.process.MSTB903;

import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;

import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB903020_IfPopSyohinCreate.java クラス</p>
 * <p>説明　: WK_POP商品マスタの内容を、IF_POP商品マスタに取込む<br>
 *            ZEN_POP商品マスタに登録されていてWK_POP商品マスタに登録されていない内容を、IF_POP商品マスタに取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 1.0 2014/09/15 Ha.ntt 海外LAWSON様通貨対応
 * @Version 1.01  (2015.05.06) DAI.BQ 海外LAWSON様対応 英文化対応
 *
 */
public class MSTB903020_IfPopSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF = "IF_POP_SYOHIN"; // IF_POP_商品マスタ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** メンテ区分(新規・訂正) */
	private static final String MAINTE_KB_SHINKI_TEISEI = "1";
	/** メンテ区分(削除) */
	private static final String MAINTE_KB_SAKUJO = "3";
	/** ゼロ（固定値） */
	private static final String CONST_ZERO = "0";

	/** 【あたり】文言 */
	private static final String ATARI_STR = "MSTB903_TXT_00038";
	/** 【円】文言 */
	private static final String YEN_STR = "MSTB903_TXT_00039";
	/** 基準価格：切上げ用補正値 */
	private static final String KIJUN_VL_ROUNDUP_CORRECTION_VALUE = "10";

	/** コメント１ 開始位置 */
	private static final String COMMENT_1_TX_ST_COLUMN = "1";
	/** コメント１ 桁数 */
	private static final String COMMENT_1_TX_LENGTH = "60";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB903020_IfPopSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB903020_IfPopSyohinCreate() {
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

			// IF_POP商品マスタのTRUNCATE
			writeLog(Level.INFO_INT, "IF_POP商品マスタ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_POP商品マスタを削除処理を終了します。");

			// IF_POP商品マスタの登録(新規分・修正分)
			writeLog(Level.INFO_INT, "IF_POP商品マスタ(新規分・修正分)登録処理（WK→ZEN_IF）を開始します。");
			iRec = db.executeUpdate(getIfPopSyohinInsertSql());
			writeLog(Level.INFO_INT, "IF_POP商品マスタ(新規分・修正分)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POP商品マスタ(新規分・修正分)登録処理（WK→ZEN_IF）を終了します。");

			db.commit();

		// IF_POP商品マスタの登録(削除分)
			writeLog(Level.INFO_INT, "IF_POP商品マスタ(削除分)登録処理（BK→ZEN_IF）を開始します。");
			iRec = db.executeUpdate(getIfPopSyohinDelInsertSql());
			writeLog(Level.INFO_INT, "IF_POP商品マスタ(削除分)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_POP商品マスタ(削除分)登録処理（BK→ZEN_IF）を終了します。");

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
	 * IF_POP_SYOHINを登録するSQLを取得する(新規分・修正分)
	 *
	 * @return IF_POP_SYOHIN登録SQL(新規分・修正分)
	 */
	private String getIfPopSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		sql.append("INSERT /*+ APPEND */ INTO IF_POP_SYOHIN ");
		sql.append("	( ");
		sql.append("	 MAINTE_KB ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,MAKER_KANJI_NA ");
		sql.append("	,HINMEI_KANJI_NA ");
		sql.append("	,KIKAKU_KANJI_NA ");
		sql.append("	,KIKAKU_KANJI_2_NA ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,BUMON_DAI_CD ");
		sql.append("	,BUMON_CHU_CD ");
		sql.append("	,JAN_CD ");
		sql.append("	,JAN_UPCA_CD ");
		sql.append("	,JAN_UPCE_CD ");
		sql.append("	,YOBI_1 ");
		sql.append("	,JAN_4_CD ");
		sql.append("	,JAN_5_CD ");
		sql.append("	,JAN_6_CD ");
		sql.append("	,ZEI_KB ");
		sql.append("	,TAX_RT ");
		sql.append("	,BAITANKA_NUKI_VL ");
		sql.append("	,SYOHIN_WIDTH_QT ");
		sql.append("	,SYOHIN_HEIGHT_QT ");
		sql.append("	,SYOHIN_DEPTH_QT ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,HACHU_TANI ");
		sql.append("	,BIN_1_KB ");
		sql.append("	,BIN_2_KB ");
		sql.append("	,SAKE_NAIYORYO_QT ");
		sql.append("	,SYOHI_KIGEN_DT ");
		sql.append("	,YOBI_2 ");
		sql.append("	,YOBI_3 ");
		sql.append("	,YOBI_4 ");
		sql.append("	,YOBI_5 ");
		sql.append("	,AUTO_HAT_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + MAINTE_KB_SHINKI_TEISEI + "'                     AS MAINTE_KB ");
		sql.append("	,WPS.SYOHIN_CD                                         AS SYOHIN_CD ");
		sql.append("	,WPS.BUNRUI1_CD                                        AS BUNRUI1_CD ");
		sql.append("	,WPS.BUNRUI5_CD                                        AS BUNRUI5_CD ");
		sql.append("	,WPS.KIKAKU_KANJI_2_NA                                 AS MAKER_KANJI_NA ");
		sql.append("	,WPS.HINMEI_KANJI_NA                                   AS HINMEI_KANJI_NA ");
		sql.append("	,WPS.KIKAKU_KANJI_NA                                   AS KIKAKU_KANJI_NA ");
		sql.append("	,TRN1.KANJI_NA                                         AS KIKAKU_KANJI_2_NA ");
		sql.append("	,WPS.BAITANKA_VL                                       AS BAITANKA_VL ");
		sql.append("	,WPS.MAKER_KIBO_KAKAKU_VL                              AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,WPS.BUNRUI1_CD                                        AS BUMON_DAI_CD ");
		sql.append("	,WPS.BUNRUI5_CD                                        AS BUMON_CHU_CD ");
		sql.append("	,CASE GET_JAN_SYUBETSU(WPS.SYOHIN_CD) ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_JAN_13 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_JAN_8 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EAN_13 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EAN_8 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_02 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_KEY_PLU_4 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_KEY_PLU_5 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_DAIHYO + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                   AS JAN_CD ");
		sql.append("	,CASE GET_JAN_SYUBETSU(WPS.SYOHIN_CD) ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_UPC_A + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                   AS JAN_UPCA_CD ");
		sql.append("	,CASE GET_JAN_SYUBETSU(WPS.SYOHIN_CD) ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_UPC_E + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                   AS JAN_UPCE_CD ");
		sql.append("	,''                                                    AS YOBI_1 ");
		sql.append("	,CASE GET_JAN_SYUBETSU(WPS.SYOHIN_CD) ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_JAN_13 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_JAN_8 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EAN_13 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EAN_8 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_02 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_KEY_PLU_4 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_KEY_PLU_5 + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_DAIHYO + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                   AS JAN_4_CD ");
		sql.append("	,CASE GET_JAN_SYUBETSU(WPS.SYOHIN_CD) ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_UPC_A + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                   AS JAN_5_CD ");
		sql.append("	,CASE GET_JAN_SYUBETSU(WPS.SYOHIN_CD) ");
		sql.append("		WHEN '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_UPC_E + "' ");
		sql.append("			THEN WPS.SYOHIN_CD ");
		sql.append("		ELSE '' ");
		sql.append("	 END                                                   AS JAN_6_CD ");
		sql.append("	,WPS.ZEI_KB                                            AS ZEI_KB ");
		sql.append("	,WPS.TAX_RT                                            AS TAX_RT ");
		sql.append("	,GET_ZEINUKI_MARUME(WPS.BAITANKA_VL, " + MoneyUtil.getFractionSellUnitLen() + ", WPS.ZEI_KB, WPS.TAX_RT, ");
		sql.append(		MoneyUtil.getFractionSellUnitMode() + ") AS BAITANKA_NUKI_VL ");
		sql.append("	,WPS.SYOHIN_WIDTH_QT                                   AS SYOHIN_WIDTH_QT ");
		sql.append("	,WPS.SYOHIN_HEIGHT_QT                                  AS SYOHIN_HEIGHT_QT ");
		sql.append("	,WPS.SYOHIN_DEPTH_QT                                   AS YOHIN_DEPTH_QT ");
		sql.append("	,WPS.SIIRESAKI_CD                                      AS SIIRESAKI_CD ");
		sql.append("	,WPS.HACHUTANI_IRISU_QT                                AS HACHU_TANI ");
		sql.append("	,WPS.BIN_1_KB                                          AS BIN_1_KB ");
		sql.append("	,WPS.BIN_2_KB                                          AS BIN_2_KB ");
		sql.append("	,WPS.SAKE_NAIYORYO_QT                                  AS SAKE_NAIYORYO_QT ");
		sql.append("	,WPS.HANBAI_LIMIT_QT || TRN2.KANJI_NA                  AS SYOHI_KIGEN_DT ");
		sql.append("	,WPS.REC_HINMEI_KANJI_NA                               AS YOBI_2 ");
		sql.append("	,SUBSTRB(WPS.COMMENT_1_TX, " + COMMENT_1_TX_ST_COLUMN + ", " + COMMENT_1_TX_LENGTH + ") ");
		sql.append("	                                                       AS YOBI_3 ");
		sql.append("	,CASE ");
		sql.append("		WHEN ");
		sql.append("			WPS.UNIT_PRICE_TANI_KB									<> '" + CONST_ZERO + "'	AND ");
		sql.append("			NVL(WPS.UNIT_PRICE_KIJUN_TANI_QT, " + CONST_ZERO + ")	 > " + CONST_ZERO + "	AND ");
		sql.append("			NVL(WPS.UNIT_PRICE_KIJUN_TANI_QT, " + CONST_ZERO + ")	 > " + CONST_ZERO + "	AND ");
		sql.append("			NVL(WPS.BAITANKA_VL, " + CONST_ZERO + ")				 > " + CONST_ZERO + " ");
		sql.append("			THEN ");
		sql.append("				WPS.UNIT_PRICE_KIJUN_TANI_QT || TRN3.KANJI_NA || '" + MessageUtil.getMessage(ATARI_STR, userLocal) + "' ||  ");
		sql.append("				CEIL(WPS.UNIT_PRICE_KIJUN_TANI_QT / WPS.UNIT_PRICE_NAIYORYO_QT * WPS.BAITANKA_VL * " + KIJUN_VL_ROUNDUP_CORRECTION_VALUE + ") / " + KIJUN_VL_ROUNDUP_CORRECTION_VALUE + " || '" + MessageUtil.getMessage(YEN_STR, userLocal) + "' ");
		sql.append("		ELSE ");
		sql.append("			'' ");
		sql.append("	 END                                                   AS YOBI_4 ");
		sql.append("	,CASE ");
		sql.append("		WHEN ");
		sql.append("			WPS.UNIT_PRICE_TANI_KB									<> '" + CONST_ZERO + "'	AND ");
		sql.append("			NVL(WPS.UNIT_PRICE_KIJUN_TANI_QT, " + CONST_ZERO + ")	 > " + CONST_ZERO + "	AND ");
		sql.append("			NVL(WPS.UNIT_PRICE_KIJUN_TANI_QT, " + CONST_ZERO + ")	 > " + CONST_ZERO + "	AND ");
		sql.append("			NVL(WPS.BAITANKA_VL, " + CONST_ZERO + ")				 > " + CONST_ZERO + " ");
		sql.append("			THEN ");
		sql.append("				WPS.UNIT_PRICE_KIJUN_TANI_QT || TRN3.KANJI_NA || '" + MessageUtil.getMessage(ATARI_STR, userLocal) + "' ||  ");
		sql.append("				CEIL(WPS.UNIT_PRICE_KIJUN_TANI_QT / WPS.UNIT_PRICE_NAIYORYO_QT * ");
		sql.append("					GET_ZEINUKI_MARUME(WPS.BAITANKA_VL, " + MoneyUtil.getFractionSellUnitLen() + ", WPS.ZEI_KB, WPS.TAX_RT, ");
		sql.append(MoneyUtil.getFractionSellUnitMode() + ") * " + KIJUN_VL_ROUNDUP_CORRECTION_VALUE + ") / " + KIJUN_VL_ROUNDUP_CORRECTION_VALUE + " || '" + MessageUtil.getMessage(YEN_STR, userLocal) + "' ");
		sql.append("		ELSE ");
		sql.append("			'' ");
		sql.append("	 END                                                   AS YOBI_5 ");
		sql.append("	,WPS.EOS_KB                                            AS AUTO_HAT_KB ");
		sql.append("	,'" + userLog.getJobId() + "'                          AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                     AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                          AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'                                     AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	WK_POP_SYOHIN WPS ");
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 SYOHIN_CD ");
		sql.append("			,BUNRUI1_CD ");
		sql.append("			,BUNRUI5_CD ");
		sql.append("			,KIKAKU_KANJI_2_NA ");
		sql.append("			,HINMEI_KANJI_NA ");
		sql.append("			,KIKAKU_KANJI_NA ");
		sql.append("			,HANBAI_TANI ");
		sql.append("			,BAITANKA_VL ");
		sql.append("			,MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,ZEI_KB ");
		sql.append("			,TAX_RATE_KB ");
		sql.append("			,TAX_RT ");
		sql.append("			,SYOHIN_DEPTH_QT ");
		sql.append("			,SYOHIN_HEIGHT_QT ");
		sql.append("			,SYOHIN_WIDTH_QT ");
		sql.append("			,SIIRESAKI_CD ");
		sql.append("			,HACHUTANI_IRISU_QT ");
		sql.append("			,BIN_1_KB ");
		sql.append("			,BIN_2_KB ");
		sql.append("			,SAKE_NAIYORYO_QT ");
		sql.append("			,HANBAI_LIMIT_KB ");
		sql.append("			,HANBAI_LIMIT_QT ");
		sql.append("			,REC_HINMEI_KANJI_NA ");
		sql.append("			,COMMENT_1_TX ");
		sql.append("			,UNIT_PRICE_KIJUN_TANI_QT ");
		sql.append("			,UNIT_PRICE_TANI_KB ");
		sql.append("			,UNIT_PRICE_NAIYORYO_QT ");
		sql.append("			,EOS_KB ");
		sql.append("		FROM ");
		sql.append("			ZEN_POP_SYOHIN ");
		sql.append("		) ZPS ");
		sql.append("		ON ");
		sql.append("			WPS.SYOHIN_CD	= ZPS.SYOHIN_CD ");

		// 販売単位区分
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "'	AND ");
		sql.append("			TRN.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRN1 ");
		sql.append("		ON ");
		sql.append("			WPS.HANBAI_TANI	= TRN1.CODE_CD ");
		// 販売基準区分
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_KIJUN_TANI, userLocal) + "'	AND ");
		sql.append("			TRN.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRN2 ");
		sql.append("		ON ");
		sql.append("			WPS.HANBAI_LIMIT_KB	= TRN2.CODE_CD ");
		// ユニットプライス単位区分
		sql.append("	LEFT JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 TRN.CODE_CD ");
		sql.append("			,TRN.KANJI_NA ");
		sql.append("		FROM ");
		sql.append("			TMP_R_NAMECTF TRN ");
		sql.append("		WHERE ");
		sql.append("			TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.UNIT_PRICE_UNIT_AMOUNT, userLocal) + "'	AND ");
		sql.append("			TRN.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("		) TRN3 ");
		sql.append("		ON ");
		sql.append("			WPS.UNIT_PRICE_TANI_KB	= TRN3.CODE_CD ");
		sql.append("WHERE ");
		sql.append("	NVL(WPS.BUNRUI1_CD, ' ')				<> NVL(ZPS.BUNRUI1_CD, ' ')				OR ");
		sql.append("	NVL(WPS.BUNRUI5_CD, ' ')				<> NVL(ZPS.BUNRUI5_CD, ' ')				OR ");
		sql.append("	NVL(WPS.KIKAKU_KANJI_2_NA, ' ')			<> NVL(ZPS.KIKAKU_KANJI_2_NA, ' ')		OR ");
		sql.append("	NVL(WPS.HINMEI_KANJI_NA, ' ')			<> NVL(ZPS.HINMEI_KANJI_NA, ' ')		OR ");
		sql.append("	NVL(WPS.KIKAKU_KANJI_NA, ' ')			<> NVL(ZPS.KIKAKU_KANJI_NA, ' ')		OR ");
		sql.append("	NVL(WPS.HANBAI_TANI, ' ')				<> NVL(ZPS.HANBAI_TANI, ' ')			OR ");
		sql.append("	NVL(WPS.BAITANKA_VL, 0)					<> NVL(ZPS.BAITANKA_VL, 0)				OR ");
		sql.append("	NVL(WPS.MAKER_KIBO_KAKAKU_VL, 0)		<> NVL(ZPS.MAKER_KIBO_KAKAKU_VL, 0)		OR ");
		sql.append("	NVL(WPS.ZEI_KB, ' ')					<> NVL(ZPS.ZEI_KB, ' ')					OR ");
		sql.append("	NVL(WPS.TAX_RATE_KB, ' ')				<> NVL(ZPS.TAX_RATE_KB, ' ')			OR ");
		sql.append("	NVL(WPS.TAX_RT, 0)						<> NVL(ZPS.TAX_RT, 0)					OR ");
		sql.append("	NVL(WPS.SYOHIN_DEPTH_QT, 0)				<> NVL(ZPS.SYOHIN_DEPTH_QT, 0)			OR ");
		sql.append("	NVL(WPS.SYOHIN_HEIGHT_QT, 0)			<> NVL(ZPS.SYOHIN_HEIGHT_QT, 0)			OR ");
		sql.append("	NVL(WPS.SYOHIN_WIDTH_QT, 0)				<> NVL(ZPS.SYOHIN_WIDTH_QT, 0)			OR ");
		sql.append("	NVL(WPS.SIIRESAKI_CD, ' ')				<> NVL(ZPS.SIIRESAKI_CD, ' ')			OR ");
		sql.append("	NVL(WPS.HACHUTANI_IRISU_QT, 0)			<> NVL(ZPS.HACHUTANI_IRISU_QT, 0)		OR ");
		sql.append("	NVL(WPS.BIN_1_KB, ' ')					<> NVL(ZPS.BIN_1_KB, ' ')				OR ");
		sql.append("	NVL(WPS.BIN_2_KB, ' ')					<> NVL(ZPS.BIN_2_KB, ' ')				OR ");
		sql.append("	NVL(WPS.SAKE_NAIYORYO_QT, 0)			<> NVL(ZPS.SAKE_NAIYORYO_QT, 0)			OR ");
		sql.append("	NVL(WPS.HANBAI_LIMIT_KB, ' ')			<> NVL(ZPS.HANBAI_LIMIT_KB, ' ')		OR ");
		sql.append("	NVL(WPS.HANBAI_LIMIT_QT, 0)				<> NVL(ZPS.HANBAI_LIMIT_QT, 0)			OR ");
		sql.append("	NVL(WPS.REC_HINMEI_KANJI_NA, ' ')		<> NVL(ZPS.REC_HINMEI_KANJI_NA, ' ')	OR ");
		sql.append("	NVL(WPS.COMMENT_1_TX, ' ')				<> NVL(ZPS.COMMENT_1_TX, ' ')			OR ");
		sql.append("	NVL(WPS.UNIT_PRICE_KIJUN_TANI_QT, 0)	<> NVL(ZPS.UNIT_PRICE_KIJUN_TANI_QT, 0)	OR ");
		sql.append("	NVL(WPS.UNIT_PRICE_TANI_KB, ' ')		<> NVL(ZPS.UNIT_PRICE_TANI_KB, ' ')		OR ");
		sql.append("	NVL(WPS.UNIT_PRICE_NAIYORYO_QT, 0)		<> NVL(ZPS.UNIT_PRICE_NAIYORYO_QT, 0)	OR ");
		sql.append("	NVL(WPS.EOS_KB, ' ')					<> NVL(ZPS.EOS_KB, ' ') ");

		return sql.toString();
	}

	/**
	 * IF_POP_SYOHINを登録するSQLを取得する(削除分)
	 *
	 * @return IF_POP_SYOHIN登録SQL(削除分)
	 */
	private String getIfPopSyohinDelInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_POP_SYOHIN ");
		sql.append("	( ");
		sql.append("	 MAINTE_KB ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,BUNRUI1_CD ");
		sql.append("	,BUNRUI5_CD ");
		sql.append("	,MAKER_KANJI_NA ");
		sql.append("	,HINMEI_KANJI_NA ");
		sql.append("	,KIKAKU_KANJI_NA ");
		sql.append("	,KIKAKU_KANJI_2_NA ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,BUMON_DAI_CD ");
		sql.append("	,BUMON_CHU_CD ");
		sql.append("	,JAN_CD ");
		sql.append("	,JAN_UPCA_CD ");
		sql.append("	,JAN_UPCE_CD ");
		sql.append("	,YOBI_1 ");
		sql.append("	,JAN_4_CD ");
		sql.append("	,JAN_5_CD ");
		sql.append("	,JAN_6_CD ");
		sql.append("	,ZEI_KB ");
		sql.append("	,TAX_RT ");
		sql.append("	,BAITANKA_NUKI_VL ");
		sql.append("	,SYOHIN_WIDTH_QT ");
		sql.append("	,SYOHIN_HEIGHT_QT ");
		sql.append("	,SYOHIN_DEPTH_QT ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,HACHU_TANI ");
		sql.append("	,BIN_1_KB ");
		sql.append("	,BIN_2_KB ");
		sql.append("	,SAKE_NAIYORYO_QT ");
		sql.append("	,SYOHI_KIGEN_DT ");
		sql.append("	,YOBI_2 ");
		sql.append("	,YOBI_3 ");
		sql.append("	,YOBI_4 ");
		sql.append("	,YOBI_5 ");
		sql.append("	,AUTO_HAT_KB ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + MAINTE_KB_SAKUJO + "'   AS MAINTE_KB ");
		sql.append("	,ZPS.SYOHIN_CD                AS SYOHIN_CD ");
		sql.append("	," + CONST_ZERO + "           AS BUNRUI1_CD ");
		sql.append("	," + CONST_ZERO + "           AS BUNRUI5_CD ");
		sql.append("	,''                           AS MAKER_KANJI_NA ");
		sql.append("	,''                           AS HINMEI_KANJI_NA ");
		sql.append("	,''                           AS KIKAKU_KANJI_NA ");
		sql.append("	,''                           AS KIKAKU_KANJI_2_NA ");
		sql.append("	," + CONST_ZERO + "           AS BAITANKA_VL ");
		sql.append("	," + CONST_ZERO + "           AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("	," + CONST_ZERO + "           AS BUMON_DAI_CD ");
		sql.append("	," + CONST_ZERO + "           AS BUMON_CHU_CD ");
		sql.append("	," + CONST_ZERO + "           AS JAN_CD ");
		sql.append("	," + CONST_ZERO + "           AS JAN_UPCA_CD ");
		sql.append("	," + CONST_ZERO + "           AS JAN_UPCE_CD ");
		sql.append("	,''                           AS YOBI_1 ");
		sql.append("	," + CONST_ZERO + "           AS JAN_4_CD ");
		sql.append("	," + CONST_ZERO + "           AS JAN_5_CD ");
		sql.append("	," + CONST_ZERO + "           AS JAN_6_CD ");
		sql.append("	,''                           AS ZEI_KB ");
		sql.append("	," + CONST_ZERO + "           AS TAX_RT ");
		sql.append("	," + CONST_ZERO + "           AS BAITANKA_NUKI_VL ");
		sql.append("	," + CONST_ZERO + "           AS SYOHIN_WIDTH_QT ");
		sql.append("	," + CONST_ZERO + "           AS SYOHIN_HEIGHT_QT ");
		sql.append("	," + CONST_ZERO + "           AS SYOHIN_DEPTH_QT ");
		sql.append("	,''                           AS SIIRESAKI_CD ");
		sql.append("	," + CONST_ZERO + "           AS HACHU_TANI ");
		sql.append("	,''                           AS BIN_1_KB ");
		sql.append("	,''                           AS BIN_2_KB ");
		sql.append("	," + CONST_ZERO + "           AS SAKE_NAIYORYO_QT ");
		sql.append("	,''                           AS SYOHI_KIGEN_DT ");
		sql.append("	,''                           AS YOBI_2 ");
		sql.append("	,''                           AS YOBI_3 ");
		sql.append("	,''                           AS YOBI_4 ");
		sql.append("	,''                           AS YOBI_5 ");
		sql.append("	,''                           AS AUTO_HAT_KB ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'            AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + batchTs + "'            AS UPDATE_TS ");
		sql.append("FROM ");
		sql.append("	ZEN_POP_SYOHIN ZPS ");
		sql.append("WHERE ");
		sql.append("	NOT EXISTS ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			WPS.SYOHIN_CD ");
		sql.append("		FROM ");
		sql.append("			WK_POP_SYOHIN WPS ");
		sql.append("		WHERE ");
		sql.append("			WPS.SYOHIN_CD	= ZPS.SYOHIN_CD ");
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

