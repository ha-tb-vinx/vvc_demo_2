package mdware.master.batch.process.mbD0;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst001201_EosKbDictionary;
import mdware.master.common.dictionary.mst005801_BinKbDictionary;
import mdware.master.common.dictionary.mst010101_SyohinKbDictionary;
import mdware.master.common.dictionary.mst011201_CgcHenpinKbDictionary;

import org.apache.log4j.Level;

/**
 * <p>タイトル:IF_AS400商品マスタ作成</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author S.Matsushita
 * @version 3.00 (2013/05/20) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 */
public class MBD00201_CreateSyohinIfAS400 {

	// テーブル
	private static final String TABLE_IF		= "IF_AS400_SYOHIN";	// IF_AS400商品マスタ
	private static final String TABLE_WK		= "WK_MBD0_SYOHIN";		// WK_MBD0商品マスタ
	private static final String TABLE_ZEN		= "ZEN_MBD0_SYOHIN";	// ZEN_MBD0商品マスタ
	// データ区分（新規）
	private static final String DATA_KBN_NEW = mst000101_ConstDictionary.AS400_DATA_KB_NEW;
	// データ区分（修正）
	private static final String DATA_KBN_ADD = mst000101_ConstDictionary.AS400_DATA_KB_ADD;

	private DataBase db	= null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBD00201_CreateSyohinIfAS400(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase( mst000101_ConstDictionary.CONNECTION_STR );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBD00201_CreateSyohinIfAS400() {
		this(new DataBase( mst000101_ConstDictionary.CONNECTION_STR ));
		closeDb = true;
	}


	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try{

			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();
			writeLog(Level.INFO_INT, "バッチ日付：" + batchDate);

			// WK_MBD0商品ワークのTRUNCATE
			writeLog(Level.INFO_INT, "IF_AS400商品マスタデータクリア処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF);
			writeLog(Level.INFO_INT, "IF_AS400商品マスタデータをクリアしました。");

			// IF_AS400商品マスタ（新規）の作成（WK→IF）
			writeLog(Level.INFO_INT, "IF_AS400商品マスタ（新規）データ作成処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getWkMbd0SyohinInsertSQL(DATA_KBN_NEW));
			writeLog(Level.INFO_INT, "IF_AS400商品マスタ（新規）データを" + iRec + "件作成しました。");

			// IF_AS400商品マスタ（修正）の作成（WK→IF）
			writeLog(Level.INFO_INT, "IF_AS400商品マスタ（修正）データ作成処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getWkMbd0SyohinInsertSQL(DATA_KBN_ADD));
			writeLog(Level.INFO_INT, "IF_AS400商品マスタ（修正）データを" + iRec + "件作成しました。");

			db.commit();

			writeLog(Level.INFO_INT, "処理を終了します。");

		//SQLエラーが発生した場合の処理
		}catch(SQLException se){
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

		//その他のエラーが発生した場合の処理
		}catch(Exception e){
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

		//SQL終了処理
		}finally{
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// バッチ日付取得
		batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
		if(batchDate == null || batchDate.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
			throw new Exception();
		}

	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * WK_MBD0商品ワーク作成用SQLを生成する。
	 * @param dataKb
	 * @return SQL
	 * @throws Exception
	 */
	private String getWkMbd0SyohinInsertSQL(String dataKb) throws Exception {
		//SQL文生成用
		StringBuffer strSql = new StringBuffer();
		StringBuffer strInsSql = new StringBuffer();	// INSERT句の項目
		StringBuffer strSelSql = new StringBuffer();	// SELECT句の項目

		// INSERT句・SELECT句の項目設定
		strInsSql.append("	DATA_KB ");
		strSelSql.append("	'" + dataKb + "' ");

		strInsSql.append("	, SEQNO ");
		strSelSql.append("	, ROW_NUMBER() OVER(ORDER BY WMS.UPDATE_TS) AS SEQNO ");

		strInsSql.append("	, SYOHIN_CD ");
		strSelSql.append("	, SUBSTR(WMS.SYOHIN_CD,1,13) ");

		strInsSql.append("	, SYOHIN_NA ");
		strSelSql.append("	, SUBSTR(WMS.REC_HINMEI_KANJI_NA,1,15) ");

		strInsSql.append("	, KIKAKU_NA ");
		strSelSql.append("	, SUBSTR(WMS.KIKAKU_KANJI_NA,1,8) ");

		strInsSql.append("	, KANA_NA ");
		strSelSql.append("	, SUBSTR(WMS.HINMEI_KANA_NA,1,25) ");

		strInsSql.append("	, POS_NA ");
		strSelSql.append("	, SUBSTR(WMS.REC_HINMEI_KANA_NA,1,14) ");

		strInsSql.append("	, DPT_CD ");
		strSelSql.append("	, SUBSTR(WMS.BUNRUI1_CD,1,3) ");

		strInsSql.append("	, LINE_CD ");
		strSelSql.append("	, SUBSTR(WMS.BUNRUI2_CD,3,3) ");

		strInsSql.append("	, CLASS_CD ");
		strSelSql.append("	, SUBSTR(WMS.BUNRUI5_CD,3,3) ");

		strInsSql.append("	, HACHUSAKI_CD ");
		strSelSql.append("	, SUBSTR(WMS.SIIRESAKI_CD,1,6) ");

		strInsSql.append("	, IRISU_QT ");
		strSelSql.append("	, CASE WHEN WMS.HACHUTANI_IRISU_QT > " + mst000101_ConstDictionary.AS400_MAX_IRISU_QT + " ");
		strSelSql.append("		THEN " + mst000101_ConstDictionary.AS400_MAX_IRISU_QT + " ");
		strSelSql.append("		ELSE WMS.HACHUTANI_IRISU_QT ");
		strSelSql.append("		END AS IRISU_QT ");

		strInsSql.append("	, GENTANKA_VL ");
		strSelSql.append("	, WMS.GENTANKA_VL ");

		strInsSql.append("	, BAITANKA_VL ");
		strSelSql.append("	, WMS.BAITANKA_VL ");

		strInsSql.append("	, OB_KB ");
		strSelSql.append("	, CASE WHEN WMS.CGC_HENPIN_KB = '" + mst011201_CgcHenpinKbDictionary.REITO_SYOKUHIN_IGAI.getCode() + "' ");
		strSelSql.append("		THEN '" + mst000101_ConstDictionary.AS400_OB_KB + "' ");
		strSelSql.append("		END AS OB_KB ");

		strInsSql.append("	, SYOHIN_KB ");
		strSelSql.append("	, CASE WHEN WMS.SYOHIN_KB IN ('" + mst010101_SyohinKbDictionary.SIIREHANBAI.getCode() + "','" + mst010101_SyohinKbDictionary.HANBAI.getCode() + "') ");
		strSelSql.append("		THEN '" + mst000101_ConstDictionary.AS400_SYOHIN_KB_SYOHIN + "' ");
		strSelSql.append("		WHEN WMS.SYOHIN_KB = '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "' ");
		strSelSql.append("		THEN '" + mst000101_ConstDictionary.AS400_SYOHIN_KB_GENRYO + "' ");
		strSelSql.append("		END AS SYOHIN_KB ");

		strInsSql.append("	, PC_KB ");
		strSelSql.append("	, WMS.PC_KB ");

		strInsSql.append("	, SOSHIN_KB ");
		strSelSql.append("	, CASE WHEN WMS.SYOHIN_KB IN ('" + mst010101_SyohinKbDictionary.SIIREHANBAI.getCode() + "','" + mst010101_SyohinKbDictionary.HANBAI.getCode() + "') ");
		strSelSql.append("		THEN '" + mst000101_ConstDictionary.AS400_SEND_KB + "' ");
		strSelSql.append("		END AS SOSHIN_KB ");

		strInsSql.append("	, BIN_1_KB ");
		strSelSql.append("	, CASE WHEN WMS.BIN_1_KB = '" + mst005801_BinKbDictionary.BIN1.getCode() + "' ");
		strSelSql.append("		THEN '" + mst000101_ConstDictionary.AS400_BIN_KB + "' ");
		strSelSql.append("		END AS BIN_1_KB ");

		strInsSql.append("	, BIN_2_KB ");
		strSelSql.append("	, CASE WHEN WMS.BIN_2_KB = '" + mst005801_BinKbDictionary.BIN2.getCode() + "' ");
		strSelSql.append("		THEN '" + mst000101_ConstDictionary.AS400_BIN_KB + "' ");
		strSelSql.append("		END AS BIN_2_KB ");

		strInsSql.append("	, TEKIYO_DT ");
		strSelSql.append("	, '' ");

		strInsSql.append("	, TOKUSEI_KB ");
		strSelSql.append("	, '' ");

		strInsSql.append("	, TORIATUKAI_KB ");
		strSelSql.append("	, '' ");

		strInsSql.append("	, TEISHI_KB ");
		strSelSql.append("	, CASE WHEN WMS.EOS_KB IN ('" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "') ");
		strSelSql.append("		THEN '" + mst000101_ConstDictionary.AS400_TEISI_KB + "' ");
		strSelSql.append("		END AS TEISHI_KB ");

		strInsSql.append("	, END_MARK_TX ");
		strSelSql.append("	, '*' ");

		strInsSql.append("	, INSERT_TS ");
		strSelSql.append("	, WMS.INSERT_TS ");

		strInsSql.append("	, INSERT_USER_ID ");
		strSelSql.append("	, WMS.INSERT_USER_ID ");

		strInsSql.append("	, UPDATE_TS ");
		strSelSql.append("	, WMS.UPDATE_TS ");

		strInsSql.append("	, UPDATE_USER_ID ");
		strSelSql.append("	, WMS.UPDATE_USER_ID ");

		// INSERT文生成
		strSql.append("INSERT INTO " + TABLE_IF + "( ");
		strSql.append(strInsSql);
		strSql.append(") ");
		strSql.append("SELECT ");
		strSql.append(strSelSql);
		strSql.append("FROM " + TABLE_WK + " WMS ");

		if (DATA_KBN_NEW.equals(dataKb)) {
			// 新規の場合
			strSql.append("LEFT JOIN " + TABLE_ZEN + " ZMS ");
			strSql.append("	ON WMS.SYOHIN_CD = ZMS.SYOHIN_CD ");
			strSql.append("WHERE ");
			strSql.append("	ZMS.SYOHIN_CD IS NULL ");
		} else {
			// 修正の場合
			strSql.append("INNER JOIN " + TABLE_ZEN + " ZMS ");
			strSql.append("	ON WMS.SYOHIN_CD = ZMS.SYOHIN_CD ");
			strSql.append("	AND WMS.UPDATE_TS > ZMS.UPDATE_TS ");
		}

		return strSql.toString();
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
