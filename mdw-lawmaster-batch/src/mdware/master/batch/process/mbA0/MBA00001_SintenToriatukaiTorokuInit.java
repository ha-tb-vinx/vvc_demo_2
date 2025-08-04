package mdware.master.batch.process.mbA0;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:新店取扱登録条件初期処理</p>
 * <p>説明:新店取扱登録条件に設定されている情報を抽出し、ワークテーブルを作成する。</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2009/02/23 マミーマート様向け初版作成 T.Mori
 * @author T.Mori
 */
 
public class MBA00001_SintenToriatukaiTorokuInit {

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();
	
	// バッチ日付
	private String batchDate = "";
	
	public static final int SYORI_DAYS = 1;
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA00001_SintenToriatukaiTorokuInit(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA00001_SintenToriatukaiTorokuInit() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		
		try{
			
			//SQL文生成用
			StringBuffer strSql = new StringBuffer();
			
			int recSelCnt = 0;
			String strRec = null;
			int insNum = 0; 
				
			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			writeLog(Level.INFO_INT, "処理を開始します。");

			//バッチ日付取得
			batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			
			// テーブルクリア
			writeLog(Level.INFO_INT, "新店取扱登録条件ワークのデータクリアを開始します。");
			DBUtil.truncateTable(db, "WK_R_SINTEN_TOROKU");
			writeLog(Level.INFO_INT, "新店取扱登録条件ワークのデータクリアを終了します。");
			
			writeLog(Level.INFO_INT, "新店取扱登録条件取得処理を開始します。");
			ResultSet rsSet = db.executeQuery( this.getSelDataSQL() );
			
			while (rsSet.next()){
			
				recSelCnt++;
			
				writeLog(Level.INFO_INT, "店舗マスタ登録状況チェック処理を開始します。");
				//店舗マスタ登録状況チェック処理
				strSql.delete(0,strSql.length());
				strSql.append("SELECT ");
				strSql.append("  TRIM(TENPO_CD) AS TENPO_CD ");
				strSql.append("FROM ");
				strSql.append("  R_TENPO ");
				strSql.append("WHERE ");
				strSql.append("  (TENPO_CD = '" ).append( rsSet.getString("CREATE_TENPO_CD") ).append( "' " );
				strSql.append("  OR TENPO_CD = '" ).append( rsSet.getString("COPY_TENPO_CD") ).append( "') " );
				strSql.append("  AND HEITEN_DT > '" ).append( DateChanger.addDate(batchDate,SYORI_DAYS) ).append( "' " );
				strSql.append("  AND DELETE_FG = '" ).append( mst000101_ConstDictionary.DELETE_FG_NOR ).append( "' " );
				
				int selNum = db.executeUpdate(strSql.toString());
				
				//取得件数が2件の場合
				if (selNum == 2){
					writeLog(Level.INFO_INT, "新店取扱登録条件ワーク登録処理を開始します。");
					//新店取扱登録条件ワーク登録処理
					
					//登録する
					strSql.delete(0,strSql.length());
					strSql.append("INSERT INTO WK_R_SINTEN_TOROKU(");
					strSql.append("  CREATE_TENPO_DT,");
					strSql.append("  CREATE_TENPO_CD,");
					strSql.append("  BUNRUI1_CD,");
					strSql.append("  COPY_TENPO_CD,");
					strSql.append("  INSERT_TS) ");
					strSql.append("  SELECT ");
					strSql.append("	   CREATE_TENPO_DT, ");
					strSql.append("    CREATE_TENPO_CD, ");
					strSql.append("    BUNRUI1_CD, ");
					strSql.append("	   COPY_TENPO_CD,");
					strSql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
					strSql.append("  FROM ");
					strSql.append("    R_SINTEN_TOROKU ");
					strSql.append("  WHERE ");
					strSql.append("    CREATE_TENPO_DT = '" ).append( DateChanger.addDate(batchDate,SYORI_DAYS) ).append( "' " );
					strSql.append("    AND CREATE_TENPO_CD = '" ).append( rsSet.getString("CREATE_TENPO_CD") ).append( "' " );
					strSql.append("    AND COPY_TENPO_CD = '" ).append( rsSet.getString("COPY_TENPO_CD") ).append( "' " );
					strSql.append("    AND DELETE_FG = '" ).append( mst000101_ConstDictionary.DELETE_FG_NOR ).append( "' " );	
					
					insNum = db.executeUpdate(strSql.toString());
					
					//登録件数が0件の場合
					if(insNum == 0){
						writeLog(Level.INFO_INT, "登録対象データは存在しませんでした。");
					}

					writeLog(Level.INFO_INT, "新店取扱登録条件ワーク登録処理を終了します。");
				//取得件数が1件の場合
				}else if (selNum == 1){
					ResultSet rs = db.executeQuery(strSql.toString());
					if(rs.next()){
						strRec = rs.getString("TENPO_CD");
					}
					rs.close();
					db.closeResultSet(rs);
					
					if(strRec.equals(rsSet.getString("CREATE_TENPO_CD"))){
						writeLog(Level.INFO_INT, "未取得店舗コード：" + rsSet.getString("COPY_TENPO_CD"));
					}else{
						writeLog(Level.INFO_INT, "未取得店舗コード：" + rsSet.getString("CREATE_TENPO_CD"));
					}
				//取得件数が0件の場合
				}else{
					writeLog(Level.INFO_INT, "未取得店舗コード：" + rsSet.getString("CREATE_TENPO_CD") + "," + rsSet.getString("COPY_TENPO_CD"));
				}
			}

			db.closeResultSet(rsSet);
			
			//対象データが存在しなかった場合
			if (recSelCnt == 0) {
				writeLog(Level.INFO_INT, "新店取扱登録条件にデータは存在しませんでした。");
			}
			
			db.commit();

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
	 * 新店取扱登録条件取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer strSql = new StringBuffer("");

		strSql.append( "SELECT " );
		strSql.append( "  TRIM(CREATE_TENPO_DT) AS CREATE_TENPO_DT, " );
		strSql.append( "  TRIM(CREATE_TENPO_CD) AS CREATE_TENPO_CD, " );
		strSql.append( "  TRIM(COPY_TENPO_CD) AS COPY_TENPO_CD " );
		strSql.append( "FROM " );
		strSql.append( "  R_SINTEN_TOROKU " );
		strSql.append( "WHERE ");
		strSql.append( "  CREATE_TENPO_DT = '" ).append( DateChanger.addDate(batchDate,SYORI_DAYS) ).append( "' " );
		strSql.append( "  AND DELETE_FG = '" ).append( mst000101_ConstDictionary.DELETE_FG_NOR ).append( "' " );	
		strSql.append( "GROUP BY ");
		strSql.append( "  CREATE_TENPO_DT,CREATE_TENPO_CD,COPY_TENPO_CD ");
		
		return( strSql.toString() );
		
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