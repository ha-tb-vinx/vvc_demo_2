package mdware.master.batch.process.mbD0;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

import org.apache.log4j.Level;

/**
 * <p>タイトル:翌日有効商品マスタワーク作成</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author S.Matsushita
 * @version 3.00 (2013/05/20) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 * @version 3.01 (2013/07/25) M.Ayukawa    [MSTC00028] 商品変更リスト作成対応
 */
public class MBD00101_CreateSyohinInitAS400 {

	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	private DataBase db	= null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = null;
	// 有効日
	private String yukoDate = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBD00101_CreateSyohinInitAS400(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase( mst000101_ConstDictionary.CONNECTION_STR );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBD00101_CreateSyohinInitAS400() {
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
			writeLog(Level.INFO_INT, "有効日：" + yukoDate);

			// WK_MBD0商品ワークのTRUNCATE
			writeLog(Level.INFO_INT, "WK_MBD0商品ワークデータクリア処理を開始します。");
			DBUtil.truncateTable(db, "WK_MBD0_SYOHIN");
			writeLog(Level.INFO_INT, "WK_MBD0商品ワークデータをクリアしました。");

			// WK_MBD0商品ワークの作成（TMP→WK）
			writeLog(Level.INFO_INT, "WK_MBD0商品ワークデータ作成処理（TMP→WK）を開始します。");
			iRec = db.executeUpdate(getWkMbd0SyohinInsertSQL(yukoDate));
			writeLog(Level.INFO_INT, "WK_MBD0商品ワークデータを" + iRec + "件作成しました。");

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

		// 有効日
		yukoDate = DateChanger.addDate(batchDate, SPAN_DAYS);

	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * WK_MBD0商品ワーク作成用SQLを生成する。
	 * @param yukoDt
	 * @return SQL
	 * @throws Exception
	 */
	private String getWkMbd0SyohinInsertSQL(String yukoDt) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

		//SQL文生成用
		StringBuffer strSql = new StringBuffer();
		StringBuffer strSubSql = new StringBuffer();	// 有効日絞込み用SELECT文
		StringBuffer strInsSql = new StringBuffer();	// INSERT句の項目
		StringBuffer strSelSql = new StringBuffer();	// SELECT句の項目

		// INSERT句・SELECT句の項目設定
		strInsSql.append("	SYOHIN_CD ");
		strSelSql.append("	TRS.SYOHIN_CD ");

		strInsSql.append("	, YUKO_DT ");
		strSelSql.append("	, TRS.YUKO_DT ");

		strInsSql.append("	, BUNRUI1_CD ");
		strSelSql.append("	, TRS.BUNRUI1_CD ");

		strInsSql.append("	, SYOHIN_KB ");
		strSelSql.append("	, TRS.SYOHIN_KB ");

		strInsSql.append("	, BUNRUI2_CD ");
		strSelSql.append("	, TRS.BUNRUI2_CD ");

		strInsSql.append("	, BUNRUI5_CD ");
		strSelSql.append("	, TRS.BUNRUI5_CD ");

		strInsSql.append("	, MAKER_CD ");
		strSelSql.append("	, TRS.MAKER_CD ");

		strInsSql.append("	, HINMEI_KANJI_NA ");
		strSelSql.append("	, TRS.HINMEI_KANJI_NA ");

		strInsSql.append("	, KIKAKU_KANJI_NA ");
		strSelSql.append("	, TRS.KIKAKU_KANJI_NA ");

		strInsSql.append("	, KIKAKU_KANJI_2_NA ");
		strSelSql.append("	, TRS.KIKAKU_KANJI_2_NA ");

		strInsSql.append("	, REC_HINMEI_KANJI_NA ");
		strSelSql.append("	, TRS.REC_HINMEI_KANJI_NA ");

		strInsSql.append("	, HINMEI_KANA_NA ");
		strSelSql.append("	, TRS.HINMEI_KANA_NA ");

		strInsSql.append("	, KIKAKU_KANA_NA ");
		strSelSql.append("	, TRS.KIKAKU_KANA_NA ");

		strInsSql.append("	, KIKAKU_KANA_2_NA ");
		strSelSql.append("	, TRS.KIKAKU_KANA_2_NA ");

		strInsSql.append("	, REC_HINMEI_KANA_NA ");
		strSelSql.append("	, TRS.REC_HINMEI_KANA_NA ");

		strInsSql.append("	, GENTANKA_VL ");
		strSelSql.append("	, TRS.GENTANKA_VL ");

		strInsSql.append("	, BAITANKA_VL ");
		strSelSql.append("	, TRS.BAITANKA_VL ");

		strInsSql.append("	, SIIRESAKI_CD ");
		strSelSql.append("	, TRS.SIIRESAKI_CD ");

		strInsSql.append("	, EOS_KB ");
		strSelSql.append("	, TRS.EOS_KB ");

		strInsSql.append("	, HACHUTANI_IRISU_QT ");
		strSelSql.append("	, TRS.HACHUTANI_IRISU_QT ");

		strInsSql.append("	, BIN_1_KB ");
		strSelSql.append("	, TRS.BIN_1_KB ");

		strInsSql.append("	, BIN_2_KB ");
		strSelSql.append("	, TRS.BIN_2_KB ");

		strInsSql.append("	, CGC_HENPIN_KB ");
		strSelSql.append("	, TRS.CGC_HENPIN_KB ");

		strInsSql.append("	, PC_KB ");
		strSelSql.append("	, TRS.PC_KB ");

		strInsSql.append("	, SINKI_TOROKU_DT ");
		strSelSql.append("	, TRS.SINKI_TOROKU_DT ");

		strInsSql.append("	, HENKO_DT ");
		strSelSql.append("	, TRS.HENKO_DT ");

		strInsSql.append("	, SYOKAI_TOROKU_TS ");
		strSelSql.append("	, TRS.SYOKAI_TOROKU_TS ");

		strInsSql.append("	, SYOKAI_USER_ID ");
		strSelSql.append("	, TRS.SYOKAI_USER_ID ");

		strInsSql.append("	, INSERT_TS ");
		strSelSql.append("	, TRS.INSERT_TS ");

		strInsSql.append("	, INSERT_USER_ID ");
		strSelSql.append("	, TRS.INSERT_USER_ID ");

		strInsSql.append("	, UPDATE_TS ");
		strSelSql.append("	, TRS.UPDATE_TS ");

		strInsSql.append("	, UPDATE_USER_ID ");
		strSelSql.append("	, TRS.UPDATE_USER_ID ");

		strInsSql.append("	, BATCH_UPDATE_TS ");
		strSelSql.append("	, '" + systemDt + "' ");

		strInsSql.append("	, BATCH_UPDATE_ID ");
		strSelSql.append("	, '" + userLog.getJobId() + "' ");

		strInsSql.append("	, DELETE_FG ");
		strSelSql.append("	, TRS.DELETE_FG ");

		// 有効日絞り込み用SELECT文生成
		// ※本来はBUNRUI1_CDもキーとして指定しなくてはいけないが、商品コードを完全に一意にする為、商品コードのみとする
		// 　（何らかの問題でBURUI1_CDとSYOHIN_CDをキーにし、有効なレコードを取得した場合、複数の商品コードが返る恐れがある為）
		strSubSql.append("SELECT ");
		strSubSql.append("	MAX(TRSW.YUKO_DT) ");
		strSubSql.append("FROM TMP_R_SYOHIN TRSW ");
		strSubSql.append("WHERE ");
		strSubSql.append("	TRSW.YUKO_DT <= '" + yukoDt + "' ");
		strSubSql.append("	AND TRSW.SYOHIN_CD = TRS.SYOHIN_CD ");

		// INSERT文生成
		strSql.append("INSERT INTO WK_MBD0_SYOHIN ( ");
		strSql.append(strInsSql);
		strSql.append(") ");
		strSql.append("SELECT ");
		strSql.append(strSelSql);
		strSql.append("FROM TMP_R_SYOHIN TRS ");
		strSql.append("WHERE ");
		strSql.append("	TRS.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		strSql.append("	AND YUKO_DT = ( ");
		strSql.append(strSubSql);
		strSql.append("	) ");

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
