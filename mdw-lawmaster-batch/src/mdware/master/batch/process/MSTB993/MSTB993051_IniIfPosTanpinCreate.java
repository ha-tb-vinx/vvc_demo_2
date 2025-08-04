package mdware.master.batch.process.MSTB993;

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
/**
 *
 * <p>タイトル: MSTB993051_IniIfPosTanpinCreate.java クラス</p>
 * <p>説明　: IF_INI_PLU単品メンテに取込む<br>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2016.10.03) nv.cuong
 * @version 1.01 (2016.11.24) nv.cuong
 * @version 1.02 (2016.12.12) #3049 nv.cuong
 * @version 1.03 (2016.12.12) #3234 nv.cuong
 * @version 1.04 (2017.01.30) #3571 M.Akagi
 * @version 1.05 (2017.02.09) #3765 S.Takayama
 * @version 1.06 (2020.07.10) #6167 KHAI.NN
 * @version 1.07 (2020.09.22) #6227 KHAI.NN
 * @version 1.08 (2020.09.30) #6238 KHAI.NN
 * @version 1.09 (2022.12.08) #6708 SIEU.D
 * @Version 1.10 (2024.01.16) #15277 DUY.HM
 *
 */
public class MSTB993051_IniIfPosTanpinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF_INI_PLU_TANPIN = "IF_INI_PLU_TANPIN"; // IF_INI_PLU単品メンテ

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** パディング文字 */
	private static final String PADDING_STR_BLANK = " ";

	//"000000000000000000"固定
	private static final String SYOHIN_BARCODE = "000000000000000000";

	//"00000000000000000"固定
	private static final String KAIIN_TANKA = "00000000000000000";

	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB993051_IniIfPosTanpinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB993051_IniIfPosTanpinCreate() {
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
			//batchDt = RMSTDATEUtil.getBatDateDt();

			//バッチ日付取得
			String batchDatetmp = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

			//バッチ日付＋１日
			String batchDate = DateChanger.addDate(batchDatetmp, SPAN_DAYS);

			//商品分類体系作成日取得
			String createDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_SAKUSEI_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);

			/*writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if (!batchDate.equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。(バッチ処理日≠商品分類体系作成日)");

				return;
			}
			writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");*/

			// IF_INI_PLU単品のTRUNCATE
			writeLog(Level.INFO_INT, "IF_INI_PLU単品削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_INI_PLU_TANPIN);
			writeLog(Level.INFO_INT, "IF_INI_PLU単品を削除処理を終了します。");

			// IF_INI_PLU単品メンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_INI_PLU単品メンテ(新規・訂正)登録処理を開始します。");
			iRec = db.executeUpdate(getPosSyohinInsertSql(batchDate));
			writeLog(Level.INFO_INT, "IF_INI_PLU単品メンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_INI_PLU単品メンテ(新規・訂正)登録処理を終了します。");

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
	 * IF_POS_LINE(新規・訂正)を登録するSQLを取得する
	 *
	 * @return IF_POS_LINE(新規・訂正)登録SQL
	 */
	private String getPosSyohinInsertSql(String batchDate) throws SQLException {
		StringBuilder sql = new StringBuilder();

		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_INI_PLU_TANPIN ");
		sql.append("	( ");
		sql.append(" EIGYO_DT ");
		sql.append(" ,TENPO_CD ");
		sql.append(" ,SEND_QT ");
		sql.append(" ,TOROKU_ID ");
		sql.append(" ,SYOHIN_CD ");
		sql.append(" ,SYOHIN_RN ");
		sql.append(" ,SYOHIN_NA ");
		sql.append(" ,SYOHIN_RN_CHN ");
		sql.append(" ,SYOHIN_NA_CHN ");
		sql.append(" ,SYOHIN_BAR_CD ");
		sql.append(" ,BAITANKA_VL ");
		sql.append(" ,KAIIN_BAITANKA_VL ");
		sql.append(" ,HANBAI_TN ");
		sql.append(" ,DIVISION_CD ");
		sql.append(" ,DEPARTMENT_CD ");
		sql.append(" ,CLASS_CD ");
		sql.append(" ,SUBCLASS_CD ");
		sql.append(" ,TEIKAN_FG ");
		sql.append(" ,PLU_FG ");
		sql.append(" ,CREATE_TS ");
		sql.append(" ,ZEI_KB ");
		sql.append(" ,ZEI_RT ");
		sql.append(" ,SEASON_ID ");
		sql.append(" ,HANBAI_ZEI_RT ");
		sql.append(" ,STUDENT_WARIBIKI_CARD_FG ");
		sql.append(" ,SYOHI_KIGEN_DT ");
		sql.append(" ,CARD_FG ");
		sql.append(" ,INJI_HANBAI_TN ");
		sql.append(" ,INJI_SEIZOU_DT ");
		sql.append(" ,ZEI_CD ");
		sql.append(" ,INSERT_TS ");
		sql.append(" ,INSERT_USER_ID ");
		sql.append(" ,UPDATE_TS ");
		sql.append(" ,UPDATE_USER_ID ");
		sql.append(" ,SIIRESAKI_CD ");
		sql.append(" ,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append(" ,LABEL_SEIBUN ");
		sql.append(" ,LABEL_HOKAN_HOHO ");
		sql.append(" ,LABEL_TUKAIKATA ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append(" ,GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)

		// add 2016.12.12 #3049 nv.cuong(S)
		sql.append(" ,LABEL_COUNTRY_NA ");
		// add 2016.12.12 #3049 nv.cuong(E)
		// 2017.01.30 M.Akagi #3751 (S)
		sql.append(" ,OLD_SYOHIN_CD ");
		// 2017.01.30 M.Akagi #3751 (E)
		// #3765 MSTB993 2017.02.09 S.Takayama (S)
		sql.append(" ,INJI_HANBAI_TN_EN ");
		// #3765 MSTB993 2017.02.09 S.Takayama (E)

		// #6238 MSTB993 Add 2020.09.30 KHAI.NN (S)
		sql.append(" ,ITEM_OFFICIAL_NA ");
		// #6238 MSTB993 Add 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append(" ,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append(" '" + batchDate + "' ");
		sql.append(" ,TENPO_CD ");
		sql.append(" ,'01' ");
		sql.append(" ,'A' ");
		sql.append(" ,SYOHIN_CD ");
		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		// #6167 MSTB993 Mod 2020.07.10 KHAI.NN (S)
//		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		// #6227 MSTB993 Mod 2020.09.22 KHAI.NN (S)
		//sql.append(" ,HINMEI_KANJI_NA ");
		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		// #6227 MSTB993 Mod 2020.09.22 KHAI.NN (E)
		// #6167 MSTB993 Mod 2020.07.10 KHAI.NN (E)
		sql.append(" ,KEIRYOKI_NM ");
		sql.append(" ,REC_HINMEI_KANJI_NA ");
		sql.append(" ,'" + SYOHIN_BARCODE + "' ");
		sql.append(" ,BAITANKA_VL ");
		sql.append(" ,'" + KAIIN_TANKA + "' ");
		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		// #6708 MOD 2022.12.07 SIEU.D (S)
		// sql.append(" ,BUNRUI2_CD ");
		// sql.append(" ,BUNRUI5_CD ");
		sql.append(" ,LTRIM(BUNRUI2_CD, '0') ");
		sql.append(" ,LTRIM(BUNRUI5_CD, '0')  ");
		// #6708 MOD 2022.12.07 SIEU.D (E)
		sql.append(" ,TEIKAN_FG ");
		sql.append(" ,'2' ");
		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		// add 2016.12.12 #3234 nv.cuong(S)
		//sql.append(" ,'" + PADDING_STR + "'  ");
		sql.append(" ,ZEI_KB  ");
		// add 2016.12.12 #3234 nv.cuong(E)
		sql.append(" ,ZEI_RT ");
		sql.append(" ,SEASON_ID ");
		sql.append(" ,'000' ");
		sql.append(" ,'" + PADDING_STR + "'  ");
		sql.append(" ,SYOHI_KIGEN_DT ");
		sql.append(" ,CARD_FG ");
		sql.append(" ,HANBAI_TANI ");
		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		sql.append(" ,'" + PADDING_STR_BLANK + "' ");
		sql.append(" ,'" + batchTs + "' ");
		sql.append(" ,'" + userLog.getJobId() + "' ");
		sql.append(" ,'" + batchTs + "' ");
		sql.append(" ,'" + userLog.getJobId() + "' ");
		sql.append(" ,(CASE WHEN SIIRESAKI_CD IS NULL ");
		sql.append(" THEN '000000000' ");
		sql.append(" ELSE SIIRESAKI_CD ");
		sql.append(" END)");
		sql.append(" ,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append(" ,LABEL_SEIBUN ");
		sql.append(" ,LABEL_HOKAN_HOHO ");
		sql.append(" ,LABEL_TUKAIKATA ");
		// add 2016.11.24 #2839 nv.cuong(S)
		sql.append(" ,GYOTAI_KB ");
		// add 2016.11.24 #2839 nv.cuong(E)

		// add 2016.12.12 #3049 nv.cuong(S)
		sql.append(" ,LABEL_COUNTRY_NA ");
		// add 2016.12.12 #3049 nv.cuong(E)
		// 2017.01.30 M.Akagi #3751 (S)
		sql.append(" ,OLD_SYOHIN_CD ");
		// 2017.01.30 M.Akagi #3751 (E)
		// #3765 MSTB993 2017.02.09 S.Takayama (S)
		sql.append(" ,HANBAI_TANI_EN ");
		// #3765 MSTB993 2017.02.09 S.Takayama (E)
		// #6238 MSTB993 Add 2020.09.30 KHAI.NN (S)
		sql.append(" ,HINMEI_KANJI_NA AS ITEM_OFFICIAL_NA ");
		// #6238 MSTB993 Add 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append(" ,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append(" FROM WK_TEC_INI_PLU ");
		sql.append(" WHERE ERR_KB = '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");

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

