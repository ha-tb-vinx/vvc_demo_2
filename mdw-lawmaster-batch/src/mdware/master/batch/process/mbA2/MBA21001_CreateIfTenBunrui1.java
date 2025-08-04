package mdware.master.batch.process.mbA2;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:MSTB111100_IF店分類１マスタ作成</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author T.Yokoyama
 * @version 1.00 初版作成
 * @version 1.10 結合Ｔ障害対応 2038,2041 okada
 */
 
public class MBA21001_CreateIfTenBunrui1{

	private MasterDataBase db = null;

	// ログ用フィールド
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	
	// 処理当日から基準日までの間隔
	private static final int SYORI_SPAN = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA21001_CreateIfTenBunrui1( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA21001_CreateIfTenBunrui1() {

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

			// IFスキーマ名
			String ifSchema = null;
			try{
				ifSchema = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.IF_SCHEMA).trim();
			}catch(Exception ex){
				writeLog(Level.ERROR_INT, "内部IFスキーマ名が設定されていないため、TMP店分類１マスタはIFされません。");
				return;
			}

			// テーブルクリア
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを開始します。");
			DBUtil.truncateTable(db, ifSchema, "IF_R_TENPO_BUNRUI1");
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを終了します。");

			// IFテーブルへの登録処理
			writeLog(Level.INFO_INT, "IFテーブルのデータ登録を開始します。");
			int insNum = db.executeUpdate(getInsertSql(timeStamp, batchDt));
			writeLog(Level.INFO_INT, "IFテーブルに " + insNum + "件登録しました。");

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
	 * @param batchDate バッチ日付
	 */
	public String getInsertSql(String timeStamp,  String batchDate) throws SQLException, Exception {
		StringBuffer sb = new StringBuffer();

		// 転送項目作成

		// SQL作成
		sb.append("INSERT /*+ APPEND */ INTO IF_R_TENPO_BUNRUI1 NOLOGGING (");
		sb.append("	TENPO_CD, ");
		sb.append("	BUNRUI1_CD, ");
		sb.append("	YUKO_DT, ");
		sb.append("	TORIATSUKAI_KB, ");
		sb.append("	URIBA_AREA_QT, ");
		sb.append("	SAGYOUBA_AREA_QT, ");
		sb.append("	TANAOROSHI_GENKA_KB, ");
		sb.append("	TANAOROSHI_SYUKI_KB, ");
		sb.append("	DENSHI_TANAFUDA_KB, ");
		sb.append("	HIKIATE_LOSS_RATE, ");
		sb.append("	MAX_TANAOROSHI_QT, ");
		sb.append("	KIJUN_SAI_RT, ");
		sb.append("	KIJUN_SAI_QT, ");
		sb.append("	TANAWARI_USE_KB, ");
		sb.append("	KAISO_DT, ");
		sb.append("	IF_INSERT_TS");
		sb.append(")");

		sb.append("SELECT ");
		sb.append("	TRIM(TENPO_CD), ");
		sb.append("	TRIM(BUNRUI1_CD), ");
		sb.append("	TRIM(YUKO_DT), ");
		sb.append("	TRIM(TORIATSUKAI_KB), ");
		sb.append("	TRIM(URIBA_AREA_QT), ");
		sb.append("	TRIM(SAGYOUBA_AREA_QT), ");
		sb.append("	TRIM(TANAOROSHI_GENKA_KB), ");
		sb.append("	TRIM(TANAOROSHI_SYUKI_KB), ");
		sb.append("	TRIM(DENSHI_TANAFUDA_KB), ");
		sb.append("	HIKIATE_LOSS_RATE, ");
		sb.append("	MAX_TANAOROSHI_QT, ");
		sb.append("	KIJUN_SAI_RT, ");
		sb.append("	KIJUN_SAI_QT, ");
		sb.append("	TRIM(TANAWARI_USE_KB), ");
		sb.append("	TRIM(KAISO_DT), ");
		sb.append("	'").append(timeStamp).append("' ");
		sb.append("FROM TMP_R_TENPO_BUNRUI1 TB1 ");

		sb.append("	WHERE ( ");
		sb.append(" TB1.YUKO_DT = ");
		sb.append("	( SELECT ");
		sb.append("		MAX( YUKO_DT ) ");
		sb.append("	FROM ");
		sb.append("		TMP_R_TENPO_BUNRUI1 TBSUB ");
		sb.append("	WHERE ");
		sb.append("		TBSUB.YUKO_DT <= '").append( DateChanger.addDate(batchDate, SYORI_SPAN) ).append("' ");
		sb.append("		AND TBSUB.TENPO_CD = TB1.TENPO_CD ");
		sb.append("		AND TBSUB.BUNRUI1_CD = TB1.BUNRUI1_CD ");
		sb.append("	) OR ( ");
		sb.append("		TB1.YUKO_DT > '").append( DateChanger.addDate(batchDate, SYORI_SPAN) ).append("' ");
		sb.append("	) ");
		sb.append(") ");
		sb.append("AND TB1.DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() +"' ");

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
