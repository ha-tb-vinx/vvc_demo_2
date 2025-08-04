package mdware.master.batch.process.mbA2;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.DateDiff;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:MSTB111210_IF商品分類体系マスタ作成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author T.Yokoyama
 * @version 1.00 初版作成
 * @version 1.10 結合Ｔ障害対応 2038,2041 okada
 */
 
public class MBA22101_CreateIfSyohinTaikei{

	private MasterDataBase db = null;

	// ログ用フィールド
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA22101_CreateIfSyohinTaikei( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA22101_CreateIfSyohinTaikei() {

		this( new MasterDataBase( "rbsite_ora" ) );
	}

	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		String jobId = userLog.getJobId();
		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			
			// タイムスタンプ取得
			String timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );
	    	// バッチ日付
			String batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

			// 商品分類体系切替マスタIF基準日
			String ifKijunDt = DateChanger.addDate(batchDt, 1);
			
			// 商品切替日(未設定でもエラーにはしない)
			String kirikaeDt = null;
			boolean execFlag = true;
			try{
				kirikaeDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_KIRIKAE_DT).trim();
			}catch(Exception ex){
				writeLog(Level.INFO_INT, "商品切替日が設定されていないため、TMP商品分類体系切替マスタはIFされません。");
				execFlag = false;
			}
			
			if(execFlag && kirikaeDt.equals("")){
				writeLog(Level.INFO_INT, "商品切替日が設定されていないため、TMP商品分類体系切替マスタはIFされません。");
				execFlag = false;
			}

			if(execFlag && DateDiff.getDiffDays( ifKijunDt, kirikaeDt ) <= 0){
				writeLog(Level.INFO_INT, "商品切替日に過去日が設定されているため、TMP商品分類体系切替マスタはIFされません。");
				execFlag = false;
			}

			// IFスキーマ名
			String ifSchema = null;
			try{
				ifSchema = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.IF_SCHEMA).trim();
			}catch(Exception ex){
				writeLog(Level.ERROR_INT, "内部IFスキーマ名が設定されていないため、TMP商品分類体系マスタはIFされません。");
				return;
			}

			// テーブルクリア
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを開始します。");
			DBUtil.truncateTable(db, ifSchema, "IF_R_SYOHIN_TAIKEI");
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを終了します。");

			// IFテーブルへの登録処理（TMP商品分類体系マスタ）
			writeLog(Level.INFO_INT, "TMP商品分類体系マスタからIFテーブルのデータ登録を開始します。");
			int insNum = db.executeUpdate(getInsertSql(timeStamp, ifKijunDt));
			writeLog(Level.INFO_INT, "IFテーブルに " + insNum + "件登録しました。");

			if(execFlag){
				// IFテーブルへの登録処理（TMP商品分類体系切替マスタ）
				writeLog(Level.INFO_INT, "TMP商品分類体系切替マスタからIFテーブルのデータ登録を開始します。");
				insNum = db.executeUpdate(getInsertSql2(timeStamp, kirikaeDt));
				writeLog(Level.INFO_INT, "IFテーブルに " + insNum + "件登録しました。");
	
			}
			// コミット
			db.commit();
		// 例外処理
		} catch ( Exception e ) {

			// ロールバック
			db.rollback();

			// ログ出力
			writeError(e);

			throw e;
		}finally{
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/*********************************************/
	
	/**
	 * IFテーブルInsert用SQL
	 * @param timeStamp 登録年月日
	 */
	public String getInsertSql(String timeStamp, String yukoDt) throws SQLException, Exception {
		StringBuffer sb = new StringBuffer();
		
		// SQL作成
		sb.append("INSERT INTO IF_R_SYOHIN_TAIKEI (");

		sb.append("SYSTEM_KB, ");
		sb.append("YUKO_DT, ");
		sb.append("BUNRUI5_CD, ");
		sb.append("BUNRUI4_CD, ");
		sb.append("BUNRUI3_CD, ");
		sb.append("BUNRUI2_CD, ");
		sb.append("BUNRUI1_CD, ");
		sb.append("DAIBUNRUI2_CD, ");
		sb.append("DAIBUNRUI1_CD, ");
		sb.append("IF_INSERT_TS ");
		sb.append(") ");
		
		sb.append("SELECT ");
		sb.append("trim(SYSTEM_KB), ");
		sb.append("TO_CHAR(TO_DATE(UPDATE_TS, 'YYYYMMDDHH24MISS') + 1, 'YYYYMMDD'), ");
		sb.append("trim(BUNRUI5_CD), ");
		sb.append("trim(BUNRUI4_CD), ");
		sb.append("trim(BUNRUI3_CD), ");
		sb.append("trim(BUNRUI2_CD), ");
		sb.append("trim(BUNRUI1_CD), ");
		sb.append("trim(DAIBUNRUI2_CD), ");
		sb.append("trim(DAIBUNRUI1_CD), ");
		sb.append("	'").append(timeStamp).append("' ");

		sb.append("FROM TMP_R_SYOHIN_TAIKEI ");

		return sb.toString();
	}

	/**
	 * IFテーブルInsert用SQL
	 * @param timeStamp 登録年月日
	 */
	public String getInsertSql2(String timeStamp, String yukoDt) throws SQLException, Exception {
		StringBuffer sb = new StringBuffer();
		
		// SQL作成
		sb.append("INSERT INTO IF_R_SYOHIN_TAIKEI (");

		sb.append("SYSTEM_KB, ");
		sb.append("YUKO_DT, ");
		sb.append("BUNRUI5_CD, ");
		sb.append("BUNRUI4_CD, ");
		sb.append("BUNRUI3_CD, ");
		sb.append("BUNRUI2_CD, ");
		sb.append("BUNRUI1_CD, ");
		sb.append("DAIBUNRUI2_CD, ");
		sb.append("DAIBUNRUI1_CD, ");
		sb.append("IF_INSERT_TS ");
		sb.append(")");
		
		sb.append("SELECT ");
		sb.append("trim(SYSTEM_KB), ");
		sb.append("'").append(yukoDt).append("', ");
		sb.append("trim(BUNRUI5_CD), ");
		sb.append("trim(BUNRUI4_CD), ");
		sb.append("trim(BUNRUI3_CD), ");
		sb.append("trim(BUNRUI2_CD), ");
		sb.append("trim(BUNRUI1_CD), ");
		sb.append("trim(DAIBUNRUI2_CD), ");
		sb.append("trim(DAIBUNRUI1_CD), ");
		sb.append("	'").append(timeStamp).append("' ");

		sb.append("FROM TMP_R_SYOHIN_TAIKEI_KIRIKAE ");

		return sb.toString();
	}
	/********* 以下、ログ出力用メソッド *********/
	
	/**
	 * ユーザーログとバッチログにログを出力します。
	 * 
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
