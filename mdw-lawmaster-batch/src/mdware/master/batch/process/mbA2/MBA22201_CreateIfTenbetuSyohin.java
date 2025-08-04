package mdware.master.batch.process.mbA2;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst010501_NaibuIFFgDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:MSTB111010_IF分類１マスタ作成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author T.Yokoyama
 * @version 1.00 初版作成
 * @version 1.10 結合Ｔ障害対応 2038 okada
 */
 
public class MBA22201_CreateIfTenbetuSyohin{

	private MasterDataBase db = null;

	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA22201_CreateIfTenbetuSyohin( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA22201_CreateIfTenbetuSyohin() {

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

			// IFスキーマ名
			String ifSchema = null;
			try{
				ifSchema = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.IF_SCHEMA).trim();
			}catch(Exception ex){
				writeLog(Level.ERROR_INT, "内部IFスキーマ名が設定されていないため、TMP店別商品マスタはIFされません。");
				return;
			}

			// テーブルクリア
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを開始します。");
			DBUtil.truncateTable(db, ifSchema, "IF_DT_TENBETU_SYOHIN");
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを終了します。");

			// IFテーブルへの登録処理
			writeLog(Level.INFO_INT, "IFテーブルのデータ登録を開始します。");
			int insNum = db.executeUpdate(getInsertSql(timeStamp));
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

	/**
	 * IFテーブルInsert用SQL
	 * @param timeStamp 登録年月日
	 */
	protected String getInsertSql(String timeStamp) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT /*+ APPEND */ INTO IF_DT_TENBETU_SYOHIN NOLOGGING ( ");
		sb.append("TENPO_CD, ");
		sb.append("BUNRUI1_CD, ");
		sb.append("SYOHIN_CD, ");
		sb.append("YUKO_DT, ");
		sb.append("SIIRESAKI_CD, ");
		sb.append("GENTANKA_VL, ");
		sb.append("BAIKATANKA_VL, ");
		sb.append("BUTURYU_KB, ");
		sb.append("EOS_KB, ");
		sb.append("NON_ACT_KB, ");
		sb.append("IF_INSERT_TS ");
		sb.append(") ");

		sb.append("SELECT ");
		sb.append("trim(TENPO_CD), ");
		sb.append("trim(BUNRUI1_CD), ");
		sb.append("trim(SYOHIN_CD), ");
		sb.append("trim(YUKO_DT), ");
		sb.append("trim(SIIRESAKI_CD), ");
		sb.append("trim(GENTANKA_VL), ");
		sb.append("trim(BAITANKA_VL), ");
		sb.append("trim(BUTURYU_KB), ");
		sb.append("trim(EOS_KB), ");
		sb.append("trim(NON_ACT_KB), ");
		sb.append(" '").append(timeStamp).append("' ");

		sb.append("FROM DT_TENBETU_SYOHIN ");

		sb.append("WHERE ");
		sb.append("    NAIBU_IF_FG = '" ).append( mst010501_NaibuIFFgDictionary.TAISYO.getCode() ).append( "' " );
		sb.append("AND SYOHIN_DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		
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
