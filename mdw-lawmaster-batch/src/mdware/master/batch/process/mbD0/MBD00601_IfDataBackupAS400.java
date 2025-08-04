package mdware.master.batch.process.mbD0;

import java.sql.SQLException;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル:AS400用テーブルバックアップ</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author S.Matsushita
 * @version 3.00 (2013/05/20) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 */
public class MBD00601_IfDataBackupAS400 {

	private DataBase db	= null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF		= "IF_AS400_SYOHIN";	// IF_AS400商品マスタ
	private static final String TABLE_IF_BK		= "BK_IF_AS400_SYOHIN";	// BK_IF_AS400商品マスタ
	private static final String TABLE_WK		= "WK_MBD0_SYOHIN";		// WK_MBD0商品マスタ
	private static final String TABLE_ZEN		= "ZEN_MBD0_SYOHIN";	// ZEN_MBD0商品マスタ
	private static final String TABLE_ZEN_BK	= "BK_ZEN_MBD0_SYOHIN";	// BK_ZEN_MBD0商品マスタ

	// バッチ日付
	private String batchDate = null;
	// IF_AS400商品マスタ保持日数
	private String ifAs400BackupSpan = null;
	// 退役日付
	private String deleteDate = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBD00601_IfDataBackupAS400(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase( mst000101_ConstDictionary.CONNECTION_STR );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBD00601_IfDataBackupAS400() {
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

			// IF_AS400商品マスタのバックアップ（IF→BK_IF）
			writeLog(Level.INFO_INT, "IF_AS400商品マスタのバックアップ処理（IF→BK_IF）を開始します。");
			iRec = db.executeUpdate(getIfAs400BackupSQL(batchDate));
			writeLog(Level.INFO_INT, "BK_IF_AS400商品マスタへ" + iRec + "件登録しました。");

			// BK_IF_AS400商品マスタの退役処理
			writeLog(Level.INFO_INT, "BK_IF_AS400商品マスタの退役処理を開始します。");
			writeLog(Level.INFO_INT, deleteDate + "より過去の処理日データを退役します。");
			iRec = db.executeUpdate(getBkIfAs400DeleteSQL(deleteDate));
			writeLog(Level.INFO_INT, "BK_IF_AS400商品マスタを" + iRec + "件削除しました。");

			// BK_ZEN_MBD0商品マスタの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "BK_ZEN_MBD0商品マスタの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN_BK));
			writeLog(Level.INFO_INT, "BK_ZEN_MBD0商品マスタを" + iRec + "件削除しました。");

			// ZEN_MBD0商品マスタのバックアップ（ZEN→BK_ZEN）
			writeLog(Level.INFO_INT, "ZEN_MBD0商品マスタのバックアップ処理（ZEN→BK_ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_ZEN, TABLE_ZEN_BK));
			writeLog(Level.INFO_INT, "BK_ZEN_MBD0商品マスタへ" + iRec + "件登録しました。");

			// ZEN_MBD0商品マスタの全件削除（トランザクションの為TRUNCATEしない）
			writeLog(Level.INFO_INT, "ZEN_MBD0商品マスタの削除処理（全件）を開始します。");
			iRec = db.executeUpdate(getAllDeleteSQL(TABLE_ZEN));
			writeLog(Level.INFO_INT, "ZEN_MBD0商品マスタを" + iRec + "件削除しました。");

			// ZEN_MBD0商品マスタの作成（WK→ZEN）
			writeLog(Level.INFO_INT, "ZEN_MBD0商品マスタの作成処理（WK→ZEN）を開始します。");
			iRec = db.executeUpdate(getAllInsertSQL(TABLE_WK, TABLE_ZEN));
			writeLog(Level.INFO_INT, "ZEN_MBD0商品マスタへ" + iRec + "件登録しました。");

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

		// IF_AS400商品マスタの保持日数を取得
		ifAs400BackupSpan = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_IF_BACKUP_SPAN);
		if(ifAs400BackupSpan == null || ifAs400BackupSpan.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、IFAS400保持日数が取得できませんでした");
			throw new Exception();
		}

		// 退役処理対象の処理日を算出
		deleteDate = DateChanger.addDate(batchDate, Integer.parseInt("-" + ifAs400BackupSpan));

	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_AS400商品マスタのバックアップ用SQLを生成する。
	 * @param batchDt
	 * @return SQL
	 * @throws Exception
	 */
	private String getIfAs400BackupSQL(String batchDt) throws Exception {
		// システム日付取得
		String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + TABLE_IF_BK + " ");
		strSql.append("SELECT ");
		strSql.append("	'" + batchDt + "' ");
		strSql.append("	,'" + systemDt + "' ");
		strSql.append("	," + TABLE_IF + ".* ");
		strSql.append("FROM " + TABLE_IF + " ");

		return strSql.toString();
	}

	/**
	 * BK_IF_AS400商品マスタの退役処理用SQLを生成する。
	 * @param deleteDt
	 * @return SQL
	 * @throws Exception
	 */
	private String getBkIfAs400DeleteSQL(String deleteDt) throws Exception {
		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("DELETE FROM " + TABLE_IF_BK + " ");
		strSql.append("WHERE ");
		strSql.append("	SYORI_DT < '" + deleteDt + "' ");

		return strSql.toString();
	}

	/**
	 * 全件削除用SQLを生成する。
	 * @param tableId	削除テーブル
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllDeleteSQL(String tableId) throws Exception {
		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("DELETE FROM " + tableId + " ");

		return strSql.toString();
	}

	/**
	 * 全件追加用SQLを生成する。（前提としてテーブルレイアウトが同じであること）
	 * @param tableIdF	追加元テーブル
	 * @param tableIdT	追加先テーブル
	 * @return SQL
	 * @throws Exception
	 */
	private String getAllInsertSQL(String tableIdF, String tableIdT) throws Exception {
		//SQL文生成用
		StringBuffer strSql = new StringBuffer();

		strSql.append("INSERT INTO " + tableIdT + " ");	// テーブルレイアウトが同じ前提である為、カラムは指定しない。
		strSql.append("SELECT * ");						// テーブルレイアウトが同じ前提である為「*」を使う。
		strSql.append("FROM " + tableIdF + " ");

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
