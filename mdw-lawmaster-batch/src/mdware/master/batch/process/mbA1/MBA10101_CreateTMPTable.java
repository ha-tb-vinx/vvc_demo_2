package mdware.master.batch.process.mbA1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.common.util.db.MDWareDBUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst010401_CreateTableKbDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;
/**
 * <p>タイトル:バッチ処理用マスタ生成</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author N.Nomura
 * @version 1.00 2009/02/24<BR>
 * @Version 3.00  (2013.05.05) M.Ayukawa [MSTM00005] ランドローム様対応  同一日複数回起動対応

 * 
 */
 
public class MBA10101_CreateTMPTable {

	private MasterDataBase db	= null;
	
	//batchID
	private String batchID;

	// ログ出力用変数
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA10101_CreateTMPTable(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA10101_CreateTMPTable() {
		this(new MasterDataBase("rbsite_ora"));
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		batchID = batchId;
		execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		// jobId
		String jobId = userLog.getJobId();
		
		// テーブル元
		String table_fm ;
		
		// テーブル先
		String table_to ;
		
		// 対象テーブル群
		Map tableMap = new HashMap();
		
		// バッチ登録件数をカウント
		int iRec = 0;
		
		try {

            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			
			// バッチ日付
			String batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			
			//スキーマの取得
			String SKMA = MDWareDBUtility.getUser().toUpperCase();
			
			writeLog(Level.INFO_INT, "テーブル作成条件テーブルの抽出を開始します。");
			// 処理対象テーブルを抽出
			ResultSet rs = db.executeQuery(selectCreateTableSql(batchDt));
			
			// 抽出結果を保持
			while (rs.next()) {
				tableMap.put(rs.getString("CREATE_MOTO_TABLE"), 
							 rs.getString("CREATE_SAKI_TABLE"));
			}
			db.closeResultSet(rs);
			writeLog(Level.INFO_INT, "テーブル作成条件テーブルの抽出を終了します。");
			
			// バックアップ作成処理
			for (Iterator ite = tableMap.entrySet().iterator(); ite.hasNext();) {
				Map.Entry bktb = (Map.Entry) ite.next();
				
				table_fm = (String) bktb.getKey();
				table_to = (String) bktb.getValue();
				
				if (table_fm.equals("R_TORIHIKISAKI")) {
					writeLog(Level.INFO_INT, table_fm + "から" + table_to + "への複製を開始します。");
					dropTable(table_to);
					db.executeUpdate("CREATE TABLE TMP_R_TORIHIKISAKI AS SELECT * FROM R_TORIHIKISAKI ");
					db.executeUpdate("ALTER TABLE TMP_R_TORIHIKISAKI ADD CONSTRAINT PK_TMP_R_TORIHIKISAKI PRIMARY KEY (COMP_CD, CHOAI_KB, TORIHIKISAKI_CD, RIREKI_RB, TEKIYO_START_DT) ");
					writeLog(Level.INFO_INT, table_fm + "から" + table_to + "への複製を終了します。");
				} else {
					
					// 作成元テーブルを元に、作成先テーブルを作成
					writeLog(Level.INFO_INT, table_fm + "を元に、" + table_to + "の作成を開始します。");
					DBUtil.createCopyTable(db, SKMA, table_fm, table_to);
					writeLog(Level.INFO_INT, table_fm + "を元に、" + table_to + "の作成を終了します。");

					// NOLOGGINに変更
					db.executeUpdate("ALTER TABLE " + table_to + " NOLOGGING ");

					ResultSet irs = db.executeQuery("SELECT INDEX_NAME FROM USER_INDEXES WHERE TABLE_NAME = '" + table_to + "'" );
					while (irs.next()) {
						db.executeUpdate("ALTER INDEX " + irs.getString("INDEX_NAME") + " NOLOGGING ");
					}
					db.closeResultSet(irs);
					
					// 作成元テーブルから作成先テーブルにデータ全件コピー
					writeLog(Level.INFO_INT, table_fm + "から" + table_to + "へのデータコピーを開始します。");
					iRec = db.executeUpdate(insertCopySql(table_fm, table_to));
					writeLog(Level.INFO_INT, table_to + "に" + iRec + "件登録しました。");
				}
					
				// 作成条件テーブルの作成日付を更新
				writeLog(Level.INFO_INT, "テーブル作成条件テーブルのデータ更新を開始します。");
				iRec = db.executeUpdate(updateCreateTableSql(batchDt,table_fm));
				writeLog(Level.INFO_INT, "テーブル作成条件テーブルを" + iRec + "件更新しました。");

				db.commit();
			}

		// SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			db.rollback();
			// ログ出力
			writeError(se);
			throw se;

		// その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			// ログ出力
			writeError(e);
			throw e;

		// SQL終了処理
		} finally {
			db.close();
			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
		
		
	}

	
	/**
	 * テーブル削除
	 * @param tableName テーブル名
	 * @return なし
	 */
	private void dropTable(String tableName) throws Exception {

		try {
			db.executeUpdate("DROP TABLE " + tableName);
		} catch (SQLException se) {
			if (se.getErrorCode() == 942) {
				// 「Drop対象のテーブルが存在しないエラー」は無視
			} else {
				// それ以外のエラーはスローする
				throw se;
			}
		}
	}
	
	/**
	 * テーブル作成条件から作成対象のテーブルを抽出する
	 * @param Table_fm テーブル
	 * @param Table_to テーブル
	 * @return SQL文
	 */
	private String selectCreateTableSql(String batchDt) {
		
		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" SELECT ");
		strSql.append("        CREATE_MOTO_TABLE ");
		strSql.append("       ,CREATE_SAKI_TABLE ");
		strSql.append("   FROM ");
		strSql.append("        R_CREATE_TABLE ");
		strSql.append("  WHERE ");
		strSql.append("        CREATE_TABLE_KB = '" + mst010401_CreateTableKbDictionary.TMP.getCode() + "' ");
// 2013.05.17 [MSTC00013]  同一日複数回起動対応
//		strSql.append("    AND CREATE_TABLE_DT < '" + batchDt + "' ");
		strSql.append("    AND DELETE_FG       = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		strSql.append("  ORDER BY ");
		strSql.append("        CREATE_MOTO_TABLE ");
		
		return strSql.toString();
	}
	
	/**
	 * データの全件コピーを行う
	 * @param tableFrom コピー元テーブル
	 * @param tableTo   コピー先テーブル
	 * @return SQL文
	 */
	private String insertCopySql(String tableFrom, String tableTo) {
		
		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" INSERT /*+ APPEND */ INTO " + tableTo + " NOLOGGING ");
		strSql.append("        SELECT * FROM " + tableFrom + " ");
		
		return strSql.toString();
	}
	
	/**
	 * テーブル作成条件のテーブル作成日付を更新する
	 * @param batchDt   バッチ日付
	 * @param tableFrom テーブル
	 * @return SQL文
	 */
	private String updateCreateTableSql(String batchDt, String tableFrom) throws SQLException {
		
		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" UPDATE ");
		strSql.append("        R_CREATE_TABLE");
		strSql.append("    SET ");
		strSql.append("        CREATE_TABLE_DT = '" + batchDt + "' ");
		strSql.append("       ,UPDATE_TS       = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		strSql.append("       ,UPDATE_USER_ID  = '" + batchID + "' ");
		strSql.append("  WHERE ");
		strSql.append("        CREATE_TABLE_KB   = '" + mst010401_CreateTableKbDictionary.TMP.getCode() + "' ");
		strSql.append("    AND CREATE_MOTO_TABLE = '" + tableFrom + "' ");
		
		return strSql.toString();
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