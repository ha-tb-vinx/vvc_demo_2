package mdware.master.batch.process.MSTB908;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;
/**
*
* <p>タイトル: MSTB908080_BackupBlkTable.java クラス</p>
* <p>説明　: IF_BLKグループマスタ、IF_BLK部門マスタ、IF_BLKデプトマスタ、IF_BLKラインマスタ、IF_BLKクラスマスタ、IF_BLK店舗マスタ、IF_BLK商品マスタ、IF_BLK支払種別マスタ、IF_BLK特売種別マスタ、IF_BLK仕入先マスタのバックアップを作成する。</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 (2014.08.17) TAM.NM FIVImart様対応
* @version 1.01 (2016.05.19) to #1401 FIVImart様対応
* @version 1.02 (2016.09.13) VINX H.sakamoto #1748対応
* @version 1.03 (2016.10.13) M.Akagi #2277対応
* @version 1.04 (2017.01.06) M.Akagi #3553対応
*/
public class MSTB908080_BackupBlkTable {
	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	// バッチ日付
	private String batchDt = null;
	/** システム時刻 */
	private String timeStamp = "";
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	private String jobId =userLog.getJobId();

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB908080_BackupBlkTable(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB908080_BackupBlkTable() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}
	public void execute() throws Exception{

		try {
			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し
			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");
			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDt);
			// 作成日取得
			timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

			// BK_IF_BLKグループマスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLKグループマスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkGroupInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLKグループマスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLKグループマスタ登録処理を終了します。");
			// BK_IF_BLK部門マスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLK部門マスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkBumonInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLK部門マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLK部門マスタ登録処理を終了します。");
			// BK_IF_BLKデプトマスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLKデプトマスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkDptInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLKデプトマスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLKデプトマスタ登録処理を終了します。");
			// BK_IF_BLKラインマスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLKラインマスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkLineInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLKラインマスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLKラインマスタ登録処理を終了します。");
			// BK_IF_BLKクラスマスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLKクラスマスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkClassInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLKクラスマスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLKクラスマスタ登録処理を終了します。");
			// BK_IF_BLK店舗マスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLK店舗マスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkTenpoInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLK店舗マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLK店舗マスタ登録処理を終了します。");
			// BK_IF_BLK商品マスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLK商品マスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkSyohinInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLK商品マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLK商品マスタ登録処理を終了します。");
			//2016/9/13 VINX h.sakamoto #1748対応 (S)
			// BK_IF_BLK支払種別マスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLK支払種別マスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkPaymentInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLK支払種別マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLK支払種別マスタ登録処理を終了します。");
			// BK_IF_BLK特売種別マスタの登録
			writeLog(Level.INFO_INT, "BK_IF_BLK特売種別マスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkDiscountInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLK特売種別マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLK特売種別マスタ登録処理を終了します。");
			//2016/9/13 VINX h.sakamoto #1748対応 (E)
			//2016.10.13 M.Akagi #2277 (S)
			writeLog(Level.INFO_INT, "BK_IF_BLK仕入先マスタ登録処理を開始します。");
			iRec = db.executeUpdate(getBkIfBlkShiiresakiInsertSql());
			writeLog(Level.INFO_INT, "BK_IF_BLK仕入先マスタを" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "BK_IF_BLK仕入先マスタ登録処理を終了します。");
			//2016.10.13 M.Akagi #2277 (E)

			db.commit();
			writeLog(Level.INFO_INT, "処理を終了します。");

		//SQLエラーが発生した場合の処理
		}catch (SQLException se) {
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
	 * BK_IF_BLK_GROUPを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_GROUP登録SQL
	 */
	private String getBkIfBlkGroupInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_GROUP ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 ,DATA_MAKE_DT ");
		//sql.append("	 ,GROUP_CD ");
		//sql.append("	 ,GROUP_NA ");
		//sql.append("	 ,INSERT_TS ");
		//sql.append("	 ,INSERT_USER_ID ");
		//sql.append("	 ,UPDATE_TS ");
		//sql.append("	 ,UPDATE_USER_ID ");
		sql.append("	 ,IF_BLK_GROUP.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("     IF_BLK_GROUP ");

		return sql.toString();
	}

	/**
	 * BK_IF_BLK_BUMONを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_BUMON登録SQL
	 */
	private String getBkIfBlkBumonInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_BUMON ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 ,DATA_MAKE_DT ");
		//sql.append("	 ,BUMON_CD ");
		//sql.append("	 ,BUMON_NA ");
		//sql.append("	 ,GROUP_CD ");
		//sql.append("	 ,INSERT_TS ");
		//sql.append("	 ,INSERT_USER_ID ");
		//sql.append("	 ,UPDATE_TS ");
		//sql.append("	 ,UPDATE_USER_ID ");
		sql.append("	 ,IF_BLK_BUMON.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("     IF_BLK_BUMON ");

		return sql.toString();
	}

	/**
	 * BK_IF_BLK_DPTを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_DPT登録SQL
	 */
	private String getBkIfBlkDptInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_DPT ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 ,DATA_MAKE_DT ");
		//sql.append("	 ,DPT_CD ");
		//sql.append("	 ,DPT_NA ");
		//sql.append("	 ,BUMON_CD ");
		//sql.append("	 ,INSERT_TS ");
		//sql.append("	 ,INSERT_USER_ID ");
		//sql.append("	 ,UPDATE_TS ");
		//sql.append("	 ,UPDATE_USER_ID ");
		sql.append("	 ,IF_BLK_DPT.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("     IF_BLK_DPT ");

		return sql.toString();
	}

	/**
	 * BK_IF_BLK_LINEを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_LINE登録SQL
	 */
	private String getBkIfBlkLineInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_LINE ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 ,DATA_MAKE_DT ");
		//sql.append("	 ,LINE_CD ");
		//sql.append("	 ,LINE_NA ");
		//sql.append("	 ,DPT_CD ");
		//sql.append("	 ,INSERT_TS ");
		//sql.append("	 ,INSERT_USER_ID ");
		//sql.append("	 ,UPDATE_TS ");
		//sql.append("	 ,UPDATE_USER_ID ");
		sql.append("	 ,IF_BLK_LINE.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("     IF_BLK_LINE ");

		return sql.toString();
	}

	/**
	 * BK_IF_BLK_CLASSを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_CLASS登録SQL
	 */
	private String getBkIfBlkClassInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_CLASS ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 ,DATA_MAKE_DT ");
		//sql.append("	 ,CLASS_CD ");
		//sql.append("	 ,CLASS_NA ");
		//sql.append("	 ,DPT_CD ");
		//sql.append("	 ,LINE_CD ");
		//sql.append("	 ,INSERT_TS ");
		//sql.append("	 ,INSERT_USER_ID ");
		//sql.append("	 ,UPDATE_TS ");
		//sql.append("	 ,UPDATE_USER_ID ");
		sql.append("	 ,IF_BLK_CLASS.* ");
		// 20167.01.06 M.Akagi #3553 (S)
		sql.append(" FROM ");
		sql.append("     IF_BLK_CLASS ");

		return sql.toString();
	}

	/**
	 * BK_IF_BLK_TENPOを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_TENPO登録SQL
	 */
	private String getBkIfBlkTenpoInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_TENPO ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 ,DATA_MAKE_DT ");
		//sql.append("	 ,TENPO_CD ");
		//sql.append("	 ,TENPO_NA ");
		//sql.append("	 ,TENPO_KB ");
		//sql.append("	 ,KAITEN_DT ");
		//sql.append("	 ,HEITEN_DT ");
		//sql.append("	 ,INSERT_TS ");
		//sql.append("	 ,INSERT_USER_ID ");
		//sql.append("	 ,UPDATE_TS ");
		//sql.append("	 ,UPDATE_USER_ID ");
		sql.append("	 ,IF_BLK_TENPO.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("     IF_BLK_TENPO ");

		return sql.toString();
	}

	/**
	 * BK_IF_BLK_SYOHINを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_SYOHIN登録SQL
	 */
	private String getBkIfBlkSyohinInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_SYOHIN ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 ,DATA_MAKE_DT ");
		//sql.append("	 ,SYORI_KB ");
		//sql.append("	 ,SYOHIN_CD ");
		//sql.append("	 ,SYOHIN_NA ");
		//sql.append("	 ,DPT_CD ");
		//sql.append("	 ,LINE_CD ");
		//sql.append("	 ,CLASS_CD ");
		//sql.append("	 ,SUPPLIER_CD ");
		//sql.append("	 ,SUPPLIER_NA ");
		//sql.append("	 ,INSERT_TS ");
		//sql.append("	 ,INSERT_USER_ID ");
		//sql.append("	 ,UPDATE_TS ");
		//sql.append("	 ,UPDATE_USER_ID ");
		// to 2016.05.19 #1401対応 (S)
		//sql.append("	 ,TEIKAN_FG ");
		// to 2016.05.19 #1401対応 (E)
		sql.append("	 ,IF_BLK_SYOHIN.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("     IF_BLK_SYOHIN ");

		return sql.toString();
	}

	//2016/9/13 VINX h.sakamoto #1748対応 (S)
	/**
	 * BK_IF_BLK_PAYMENTを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_PAYMENT登録SQL
	 */
	private String getBkIfBlkPaymentInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_PAYMENT ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	  , DATA_MAKE_DT");
		//sql.append("	  , TOROKU_ID");
		//sql.append("	  , SHIHARAI_SYUBETSU_SEQ");
		//sql.append("	  , SHIHARAI_SYUBETSU_EN_NA");
		//sql.append("	  , SHIHARAI_SYUBETSU_VN_NA");
		//sql.append("	  , POS_SEQ");
		//sql.append("	  , OVER_TYPE");
		//sql.append("	  , NEED_AUTHORITY");
		//sql.append("	  , NEED_EXPIRY");
		//sql.append("	  , CARD_NUMBER");
		//sql.append("	  , PROCESS_TYPE");
		//sql.append("	  , SHIHARAI_SYUBETSU_GROUP_SEQ");
		//sql.append("	  , CARD_LENGTH");
		//sql.append("	  , SHIHARAI_SYUBETSU_SUB_CD");
		//sql.append("	  , DISPLAY_FG");
		//sql.append("	  , VOID_FG");
		//sql.append("	  , RETURN_FG");
		//sql.append("	  , OPEN_DRAWER_FG");
		//sql.append("	  , EXTRA_RECEIPT");
		//sql.append("	  , MAXIMUM_RECEIPT");
		//sql.append("	  , YUKO_START_DT");
		//sql.append("	  , YUKO_END_DT");
		sql.append("	  , IF_BLK_PAYMENT.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("	 IF_BLK_PAYMENT ");
		return sql.toString();
	}

	/**
	 * BK_IF_BLK_DISCOUNTを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_DISCOUNT登録SQL
	 */
	private String getBkIfBlkDiscountInsertSql() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_DISCOUNT ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 , DATA_MAKE_DT");
		//sql.append("	 , TOROKU_ID");
		//sql.append("	 , DISCOUNT_CD");
		//sql.append("	 , SUB_DISCOUNT_CD");
		//sql.append("	 , DISCOUNT_NA");
		//sql.append("	 , SUB_DISCOUNT_NA");
		//sql.append("	 , RECEIPT_QT");
		//sql.append("	 , MAX_RECEIPT_QT");
		//sql.append("	 , NEBIKI_RITU_VL");
		//sql.append("	 , YUKO_START_DT");
		//sql.append("	 , YUKO_END_DT");
		//sql.append("	 , MAX_NEBIKI_GAKU_VL");
		sql.append("	 , IF_BLK_DISCOUNT.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("	 IF_BLK_DISCOUNT ");
		return sql.toString();
	}
	//2016/9/13 VINX h.sakamoto #1748対応 (E)

	//2016.10.13 M.Akagi #2277 (S)
	/**
	 * BK_IF_BLK_SHIIRESAKIを登録するSQLを取得する
	 *
	 * @return BK_IF_BLK_SHIIRESAKI登録SQL
	 */
	private String getBkIfBlkShiiresakiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT /*+ APPEND */ INTO BK_IF_BLK_SHIIRESAKI ");
		sql.append(" SELECT ");
		sql.append("	 '" + batchDt + "' ");
		sql.append("	 ,'" + timeStamp + "' ");
		// 20167.01.06 M.Akagi #3553 (S)
		//sql.append("	 , DATA_MAKE_DT");
		//sql.append("	 , SHIIRESAKI_CD");
		//sql.append("	 , SHIIRESAKI_KANJI_NA");
		//sql.append("	 , INSERT_TS");
		//sql.append("	 , INSERT_USER_ID");
		//sql.append("	 , UPDATE_TS");
		//sql.append("	 , UPDATE_USER_ID");
		sql.append("	 , IF_BLK_SHIIRESAKI.* ");
		// 20167.01.06 M.Akagi #3553 (E)
		sql.append(" FROM ");
		sql.append("	 IF_BLK_SHIIRESAKI ");

		return sql.toString();
	}
	//2016.10.13 M.Akagi #2277 (E)

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
