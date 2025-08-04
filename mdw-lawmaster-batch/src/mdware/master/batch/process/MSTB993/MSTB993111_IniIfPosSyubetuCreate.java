package mdware.master.batch.process.MSTB993;

import java.nio.CharBuffer;
import java.sql.SQLException;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB993111_IfIniPosSyubetuCreate.java クラス</p>
 * <p>説明　: イニシャルPLU用IFカテゴリマスタ作成
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @version 1.00  (2016.10.03) VINX H.Sakamoto #1979対応
 * @version 1.01  (2016.10.17) M.Akagi #1748対応
 * @version 1.02  (2016.10.18) nv.cuong #2238 FIVIMART対応
 * @version 1.03  (2016.11.24) nv.cuong #2839 FIVIMART対応
 * @version 1.04  (2016.12.19) nv.cuong #3233 FIVIMART対応
 * @version 1.05 (2016.12.22) S.Takayama #3382 FIVIMART対応
 * @version 1.06  (2017.01.12) T.Han #3583 FIVIMART対応
 * @version 1.07  (2017.02.02) S.Takayama #3853 FIVIMART対応
 * @version 1.08  (2017.04.27) #4824対応 S.Nakazato FIVImart対応
 * @version 1.09  (2017.05.19) M.Son #5044 FIVImart対応
 * @version 1.10 (2021.12.14) KHOI.ND #6406
 * @version 1.11 (2022.05.05) SIEU.D #6553
 */
public class MSTB993111_IniIfPosSyubetuCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** バッチ日付 */
	private String batchDt = null;
	/** バッチ日付翌日 */
	private String yokuBatchDt = null;
	/** 登録ID 固定文字 */
	private String TOROKU_ID = "A";

	// #3382 MSTB993 2016.12.22 S.Takayama (S)
	/** 最大レシート発行数 */
	private String maxImumReceipt = "";
	private String MAXIMUM_RECEIPT = "";
	// #3382 MSTB993 2016.12.22 S.Takayama (E)

	// 2017.01.12 T.Han #3583対応（S)
	/** 自家消費レシート印字文言（VN） */
	private static final int LENGTH_SHIHARAI_SYUBETSU_SUB_NA = 40;
	// 2017.01.12 T.Han #3583対応（E)

	// テーブル
	private static final String TABLE_CLASS = "IF_INI_POS_PAYMENT"; // IF_INI_POS支払種別メンテ
	private static final String TABLE_SUBCLASS = "IF_INI_POS_DISCOUNT"; // IF_INI_POS特売種別メンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB993111_IniIfPosSyubetuCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB993111_IniIfPosSyubetuCreate() {
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

			// #3382 MSTB993 2016.12.22 S.Takayama (S)
			// システムコントロール情報取得
			this.getSystemControl();
			MAXIMUM_RECEIPT = maxImumReceipt;
			// #3382 MSTB993 2016.12.22 S.Takayama (E)
			//バッチ日付取得
			batchDt = RMSTDATEUtil.getBatDateDt();
			//バッチ日付の翌日取得
			yokuBatchDt = DateChanger.addDate(batchDt, 1);

			// IF_INI_POS支払種別メンテメンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_INI_POS支払種別メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_CLASS);
			writeLog(Level.INFO_INT, "IF_INI_POS支払種別メンテ削除処理を終了します。");

			// IF_INI_POS特売種別メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_INI_POS特売種別メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_SUBCLASS);
			writeLog(Level.INFO_INT, "IF_INI_POS特売種別メンテ削除処理を終了します。");

			// IF_INI_POS支払種別メンテ登録
			writeLog(Level.INFO_INT, "IF_INI_POS支払種別メンテ登録処理を開始します。");
			iRec = db.executeUpdate(getIfIniPosClassInsertSql());
			writeLog(Level.INFO_INT, "IF_INI_POS支払種別メンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_INI_POS支払種別メンテ登録処理を終了します。");

			db.commit();

			// IF_INI_POS特売種別メンテ登録
			writeLog(Level.INFO_INT, "IF_INI_POS特売種別メンテ登録処理を開始します。");
			iRec = db.executeUpdate(getIfIniPosSubClassInsertSql());
			writeLog(Level.INFO_INT, "IF_INI_POS特売種別メンテを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_INI_POS特売種別メンテ登録処理を終了します。");

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
	 * IF_INI_POS支払種別メンテを登録するSQLを取得する
	 *
	 * @return IF_INI_POS_PAYMENT登録SQL
	 */
	private String getIfIniPosClassInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT  ");
		sql.append("INTO IF_INI_POS_PAYMENT(  ");
		sql.append("  EIGYO_DT ");
		sql.append("  , TENPO_CD ");
		// #2839 add 2016.11.24 nv.cuong(S)
		sql.append("  , GYOTAI_KB ");
		// #2839 add 2016.11.24 nv.cuong(E)
		sql.append("  , TOROKU_ID ");
		sql.append("  , SHIHARAI_SYUBETSU_SEQ ");
		sql.append("  , SHIHARAI_SYUBETSU_EN_NA ");
		sql.append("  , SHIHARAI_SYUBETSU_VN_NA ");
		sql.append("  , POS_SEQ ");
		sql.append("  , OVER_TYPE ");
		sql.append("  , NEED_AUTHORITY ");
		sql.append("  , NEED_EXPIRY ");
		sql.append("  , CARD_NUMBER ");
		sql.append("  , PROCESS_TYPE ");
		sql.append("  , SHIHARAI_SYUBETSU_GROUP_SEQ ");
		sql.append("  , CARD_LENGTH ");
		sql.append("  , SHIHARAI_SYUBETSU_SUB_CD ");
		sql.append("  , DISPLAY_FG ");
		sql.append("  , VOID_FG ");
		sql.append("  , RETURN_FG ");
		sql.append("  , OPEN_DRAWER_FG ");
		sql.append("  , EXTRA_RECEIPT ");
		sql.append("  , MAXIMUM_RECEIPT ");
		sql.append("  , YUKO_START_DT ");
		sql.append("  , YUKO_END_DT ");
		// 2017.01.12 T.Han #3583対応（S)
		sql.append("  , JIKASYOHI_KB ");
		sql.append("  , JIKASYOHI_RECEIPT_VN_NA ");
		// 2017.01.12 T.Han #3583対応（E)
		sql.append(")  ");
		sql.append("SELECT ");
		// #2238 add 2016.10.19 nv.cuong(S) 
		//sql.append("  '" + batchDt + "' ");
		sql.append("  '" + yokuBatchDt + "' ");
		// #2238 add 2016.10.19 nv.cuong(E) 
		sql.append("  , TRT.TENPO_CD ");
		// #2839 add 2016.11.24 nv.cuong(S)
		sql.append("  , TRT.GYOTAI_KB ");
		// #2839 add 2016.11.24 nv.cuong(E)
		sql.append("  , '" + TOROKU_ID + "' ");
		sql.append("  , SHIHARAI_SYUBETSU_SEQ ");
		sql.append("  , SHIHARAI_SYUBETSU_EN_NA ");
		sql.append("  , SHIHARAI_SYUBETSU_VN_NA ");
		sql.append("  , POS_SEQ ");
		sql.append("  , OVER_TYPE ");
		sql.append("  , ' ' ");
		sql.append("  , ' ' ");
		sql.append("  , ' ' ");
		sql.append("  , PROCESS_TYPE ");
		sql.append("  , SHIHARAI_SYUBETSU_GROUP_SEQ ");
		sql.append("  , ' ' ");
		// #3853 MSTB992 2017.02.02 S.Takayama (S)
		// 2016.10.17 M.Akagi #1748 (S)
		//sql.append("  , SHIHARAI_SYUBETSU_SUB_CD ");
		//sql.append("	,CASE WHEN RDC.PARAMETER_TX = '1' AND RP.SHIHARAI_SYUBETSU_GROUP_SEQ IS NULL THEN '     ' ");
		//sql.append("	ELSE RP.SHIHARAI_SYUBETSU_SUB_CD END SHIHARAI_SYUBETSU_SUB_CD ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_SUB_CD ");
		// 2016.10.17 M.Akagi #1748 (E)
		// #3853 MSTB992 2017.02.02 S.Takayama (E)
		sql.append("  , DISPLAY_FG ");
		sql.append("  , VOID_FG ");
		sql.append("  , RETURN_FG ");
		sql.append("  , OPEN_DRAWER_FG ");
		sql.append("  , EXTRA_RECEIPT ");
		// #3382 MSTB993 2016.12.22 S.Takayama (S)
		// sql.append("  , MAXIMUM_RECEIPT ");
		sql.append("  ,'" + MAXIMUM_RECEIPT + "' AS MAXIMUM_RECEIPT");
		// #3382 MSTB993 2016.12.22 S.Takayama (E)
		sql.append("  , YUKO_START_DT ");
		sql.append("  , YUKO_END_DT  ");
		// 2017.01.12 T.Han #3583対応（S)
		sql.append("  , CASE WHEN  RP.SHIHARAI_SYUBETSU_CD = 'MRS' THEN 'Y' ELSE 'N' END JIKASYOHI_KB ");
		sql.append("  , CASE WHEN  RP.SHIHARAI_SYUBETSU_CD = 'MRS' THEN RP.SHIHARAI_SYUBETSU_SUB_NA ELSE '"+spaces(LENGTH_SHIHARAI_SYUBETSU_SUB_NA)+"' END JIKASYOHI_RECEIPT_VN_NA ");
		// 2017.01.12 T.Han #3583対応（E)
		sql.append("FROM ");
		sql.append("  R_PAYMENT RP  ");
		sql.append("  INNER JOIN TMP_R_TENPO TRT  ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("    ON TRT.TENPO_KB = '"+ mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() +"' AND ");
		// sql.append("    TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		// #6553 MOD 2022.05.05 SIEU.D (S)
		// sql.append("    ON TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		sql.append("    ON ");
		// #6553 MOD 2022.05.05 SIEU.D (E)
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("    TRT.ZAIMU_END_DT >= '"+ batchDt +"' AND ");
		sql.append("    TRT.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		sql.append("    TRT.SAI_SEND_PLU_DT = '"+ yokuBatchDt +"' ");
		// #3853 MSTB992 2017.02.02 S.Takayama (S)
		//2016.10.17 M.Akagi #1748 (S)
		//sql.append("  LEFT JOIN ");
		//sql.append("		R_DICTIONARY_CONTROL RDC ");
		//sql.append("	ON ");
		//sql.append("		RP.SHIHARAI_SYUBETSU_GROUP_CD = RDC.DICTIONARY_ID ");
		//sql.append("		AND RDC.PARAMETER_ID = 'SHIHARAI_SYUBETSU_GROUP_CD' ");
		//2016.10.17 M.Akagi #1748 (E)
		// #3853 MSTB992 2017.02.02 S.Takayama (E)

		return sql.toString();
	}

	/**
	 * IF_INI_POS特売種別メンテを登録するSQLを取得する
	 *
	 * @return IF_INI_POS_DISCOUNT登録SQL
	 */
	private String getIfIniPosSubClassInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT  ");
		sql.append("INTO IF_INI_POS_DISCOUNT(  ");
		sql.append("  EIGYO_DT ");
		sql.append("  , TENPO_CD ");
		// #2839 add 2016.11.24 nv.cuong(S)
		sql.append("  , GYOTAI_KB ");
		// #2839 add 2016.11.24 nv.cuong(E)
		sql.append("  , TOROKU_ID ");
		sql.append("  , DISCOUNT_CD ");
		sql.append("  , SUB_DISCOUNT_CD ");
		sql.append("  , DISCOUNT_NA ");
		sql.append("  , SUB_DISCOUNT_NA ");
		sql.append("  , RECEIPT_QT ");
		sql.append("  , MAX_RECEIPT_QT ");
		sql.append("  , NEBIKI_RITU_VL ");
		sql.append("  , YUKO_START_DT ");
		sql.append("  , YUKO_END_DT ");
		sql.append("  , MAX_NEBIKI_GAKU_VL "); 
		// add 2016.12.19 #3233 nv.cuong(S)
		sql.append("  , CARD_KB ");
		// add 2016.12.19 #3233 nv.cuong(E)
		// 2017.04.26 S.Nakazato #4824対応（S)
		sql.append("  , SHIHARAI_JOKEN_CD ");
		// 2017.04.26 S.Nakazato #4824対応（E)
		sql.append(")  ");
		sql.append("SELECT ");
		// #2238 add 2016.10.18 nv.cuong(S)
		//sql.append("  '20160720' ");
		sql.append("  '" + yokuBatchDt + "' ");
		// #2238 add 2016.10.18 nv.cuong(E)
		sql.append("  , TRT.TENPO_CD ");
		// #2839 add 2016.11.24 nv.cuong(S)
		sql.append("  , TRT.GYOTAI_KB ");
		// #2839 add 2016.11.24 nv.cuong(E)
		sql.append("  , '" + TOROKU_ID + "' ");
		sql.append("  , RD.DISCOUNT_CD ");
		sql.append("  , RD.SUB_DISCOUNT_CD ");
		sql.append("  , RD.DISCOUNT_NA ");
		sql.append("  , RD.SUB_DISCOUNT_NA ");
		sql.append("  , RD.RECEIPT_QT ");
		// #3382 MSTB993 2016.12.22 S.Takayama (S)
		//sql.append("  , RD.MAX_RECEIPT_QT ");
		sql.append("  ,'" + MAXIMUM_RECEIPT + "' AS MAX_RECEIPT_QT");
		// #3382 MSTB993 2016.12.22 S.Takayama (E)
		sql.append("  , RD.NEBIKI_RITU_VL ");
		sql.append("  , RD.YUKO_START_DT ");
		sql.append("  , RD.YUKO_END_DT ");
		sql.append("  , RD.MAX_NEBIKI_GAKU_VL  ");
		// add 2016.12.19 #3233 nv.cuong(S)
		sql.append("  , RD.CARD_KB  ");
		// add 2016.12.19 #3233 nv.cuong(E)
		// 2017.04.26 S.Nakazato #4824対応（S)
		// 2017.05.19 M.Son #5044対応（S)
		//sql.append("  , RD.SHIHARAI_JOKEN_CD ");
		sql.append("  , RP.SHIHARAI_SYUBETSU_SEQ ");
		// 2017.05.19 M.Son #5044対応（E)
		// 2017.04.26 S.Nakazato #4824対応（E)
		sql.append("FROM ");
		sql.append("  R_DISCOUNT RD  ");
		sql.append("  INNER JOIN TMP_R_TENPO TRT  ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("    ON TRT.TENPO_KB = '"+ mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() +"' AND ");
		// sql.append("    TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		// #6553 MOD 2022.05.05 SIEU.D (S)
		// sql.append("    ON TRT.KAITEN_DT <= '"+ batchDt +"' AND ");
		sql.append("    ON ");
		// #6553 MOD 2022.05.05 SIEU.D (E)
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("    TRT.ZAIMU_END_DT >= '"+ batchDt +"' AND ");
		sql.append("    TRT.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		sql.append("    TRT.SAI_SEND_PLU_DT = '"+ yokuBatchDt +"' ");
		// 2017.05.19 M.Son #5044対応（S)
		sql.append("LEFT JOIN ");
		sql.append("    R_PAYMENT RP ");
		sql.append("ON ");
		sql.append("    RD.SHIHARAI_JOKEN_CD = RP.SHIHARAI_SYUBETSU_SUB_CD ");
		// 2017.05.19 M.Son #5044対応（E)

		return sql.toString();
	}

	// 2017.01.12 T.Han #3583対応（S)
	/**
	 * @param spaces
	 * @return String
	 */
	private String spaces(int spaces){
		return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
	}
	// 2017.01.12 T.Han #3583対応（E)

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

	// #3382 MSTB993 2016.12.22 S.Takayama (S)
	/**
	 * システムコントロール情報取得
	 * 
	 * @param なし
	 * @throws Exception
	 *             例外
	 */
	private void getSystemControl() throws Exception {
		maxImumReceipt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.MAX_RECEIPT_QT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
	}
	// #3382 MSTB993 2016.12.22 S.Takayama (E)
}
