package mdware.master.batch.process.mb86;

import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.util.db.MasterDataBase;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst007601_NonActKbDictionary;

/**
 * <p>タイトル:不稼動判定処理</p>
 * <p>説明:単品売上情報、単品仕入情報をもとに、不稼動商品の設定を行う。</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2009/03/12 マミーマート様向け初版作成 T.Mori
 */
 
public class MB860101_FukadoHantei {
	
	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();
	
	// バッチ日付
	private String batchDate = "";
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB860101_FukadoHantei(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB860101_FukadoHantei() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}
	
	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		execute();
	}
	
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		
		try {
			
			writeLog(Level.INFO_INT, "処理を開始します。");
			
			//バッチ日付取得
			batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			
			//SQL文生成用
			StringBuffer strSql = new StringBuffer();
			
			writeLog(Level.INFO_INT, "不稼動更新処理（NON-ACT⇒ACT）を開始します。");

			strSql.delete(0,strSql.length());
			strSql.append("UPDATE R_TANPINTEN_TORIATUKAI T ");
			strSql.append("   SET NON_ACT_KB        = '" + mst007601_NonActKbDictionary.ACT.getCode() + "', ");
			strSql.append("       NON_ACT_SOSHIN_DT = '" + batchDate + "', ");
			strSql.append("       HENKO_DT          = '" + batchDate + "', ");
			strSql.append("       BATCH_UPDATE_TS   = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
			strSql.append("       BATCH_UPDATE_ID   = '" + userLog.getJobId() + "' ");
			strSql.append(" WHERE NON_ACT_KB = '" + mst007601_NonActKbDictionary.NON_ACT.getCode() + "' ");
			strSql.append("   AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("   AND EXISTS ");
			strSql.append("       (SELECT /*+ HASH_SJ */ ");
			strSql.append("               * ");
			strSql.append("          FROM R_SYOHIN ");
			// #6620 MOD 2022.11.18 VU.TD (S)
//			strSql.append("         WHERE BUNRUI1_CD = T.BUNRUI1_CD ");
//			strSql.append("           AND SYOHIN_CD  = T.SYOHIN_CD ");
			strSql.append("         WHERE  ");
			strSql.append("           	 SYOHIN_CD  = T.SYOHIN_CD ");
			// #6620 MOD 2022.11.18 VU.TD (E)
			strSql.append("           AND HENKO_DT   = '" + batchDate + "'");
			strSql.append("           AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("           AND TEN_HACHU_ED_DT >= '" + batchDate + "' ");
			strSql.append("       ) ");
			int iRec = db.executeUpdate(strSql.toString());			
			db.commit();
			
			writeLog(Level.INFO_INT, iRec + "件の単品店取扱マスタレコードを稼動に更新しました。");	
			writeLog(Level.INFO_INT, "処理を終了します。");

		// SQL例外処理
		}catch( SQLException se ){
			db.rollback();
			this.writeError(se);
			throw se;	
			
		}catch( Exception e ){
			db.rollback();
			this.writeError(e);
			throw e;

		}finally{
			// クローズ
			if (closeDb || db != null) {
				db.close();
			}
		}
	}
	
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