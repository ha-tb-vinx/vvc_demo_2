package mdware.master.batch.process.mbA0;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:新店用店グルーピングマスタ作成処理</p>
 * <p>説明:新店取扱登録条件に設定されている内容に基づき、店グルーピングマスタに対して新店用のデータ作成を行う。</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2009/02/26 マミーマート様向け初版作成 T.Mori
 * @author T.Mori
 */
 
public class MBA00401_CreateTenGroupMst {

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA00401_CreateTenGroupMst(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA00401_CreateTenGroupMst() {
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
			int iRec = 0;
			int insNum = 0; 
				
			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			writeLog(Level.INFO_INT, "処理を開始します。");

			writeLog(Level.INFO_INT, "新店取扱登録条件ワーク取得処理を開始します。");
			ResultSet rsSet = db.executeQuery( this.getSelDataSQL() );

			while (rsSet.next()){
				
				recSelCnt++;

				writeLog(Level.INFO_INT, "店グルーピングマスタデータ確認処理を開始します。");
				
				//店グルーピングマスタの登録対象データ件数を取得する。
				strSql.delete(0,strSql.length());
				strSql.append("SELECT ");
				strSql.append("  COUNT(*) AS COUNT ");
				strSql.append("FROM ");
				strSql.append("  R_TENGROUP ");
				strSql.append("WHERE ");
				strSql.append(" BUNRUI1_CD = '" ).append( rsSet.getString("BUNRUI1_CD") ).append( "' " );
				strSql.append(" AND TENPO_CD = '" ).append( rsSet.getString("CREATE_TENPO_CD") ).append( "' " );

				ResultSet rs = db.executeQuery(strSql.toString());

				if(rs.next()){
					iRec = rs.getInt("COUNT");
				}
				db.closeResultSet(rs);
				
				//登録データが存在する場合
				if ( iRec > 0 ){
					
					writeLog(Level.INFO_INT, "店グルーピングマスタの物理削除処理を開始します。");
					//登録データを物理削除する
					strSql.delete(0,strSql.length());
					strSql.append(" DELETE FROM ");
					strSql.append("   R_TENGROUP ");
					strSql.append(" WHERE ");
					strSql.append("   BUNRUI1_CD = '" ).append( rsSet.getString("BUNRUI1_CD") ).append( "' " );
					strSql.append("   AND TENPO_CD = '" ).append( rsSet.getString("CREATE_TENPO_CD") ).append( "' " );

					int delNum = db.executeUpdate(strSql.toString());
					
					writeLog(Level.INFO_INT, "店グルーピングマスタを " + delNum + "件削除しました。");
					writeLog(Level.INFO_INT, "店グルーピングマスタの物理削除処理を終了します。");

				}

				writeLog(Level.INFO_INT, "店グルーピングマスタの登録処理を開始します。");
				
				//登録する
				strSql.delete(0,strSql.length());
				strSql.append("INSERT INTO R_TENGROUP(");
				strSql.append("  BUNRUI1_CD,");
				strSql.append("  YOTO_KB,");
				strSql.append("  GROUPNO_CD,");
				strSql.append("  TENPO_CD,");
				strSql.append("  RANK_NB,");
				strSql.append("  ANBUN_RT,");
				strSql.append("  INSERT_TS,");
				strSql.append("  INSERT_USER_ID,");
				strSql.append("  UPDATE_TS,");
				strSql.append("  UPDATE_USER_ID,");
				strSql.append("  DELETE_FG) ");
				strSql.append("  SELECT ");
				strSql.append("    '"+ rsSet.getString("BUNRUI1_CD") +"', ");
				strSql.append("	   YOTO_KB, ");
				strSql.append("	   GROUPNO_CD, ");
				strSql.append("    '"+ rsSet.getString("CREATE_TENPO_CD") +"', ");

				strSql.append("	( ");
		        strSql.append("		SELECT");
		        strSql.append("			NVL(MAX(RMAX.RANK_NB), 0) + 1 ");
		        strSql.append("		FROM");
		        strSql.append("			R_TENGROUP RMAX");
		        strSql.append("		WHERE");
		        strSql.append("			RMAX.BUNRUI1_CD = '" ).append( rsSet.getString("BUNRUI1_CD") ).append( "' AND");
		        strSql.append("			RMAX.YOTO_KB = RTG.YOTO_KB AND ");
		        strSql.append("			RMAX.GROUPNO_CD = RTG.GROUPNO_CD ");
		        strSql.append("	) , ");

				strSql.append("    ANBUN_RT,");
				strSql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
				strSql.append("    '" + userLog.getJobId() + "', ");
				strSql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
				strSql.append("    '" + userLog.getJobId() + "', ");
				strSql.append("    '" + mst000101_ConstDictionary.DELETE_FG_NOR ).append( "' " );
				strSql.append("  FROM ");
				strSql.append("    R_TENGROUP RTG");
				strSql.append("  WHERE ");
				strSql.append("    BUNRUI1_CD = '" ).append( rsSet.getString("BUNRUI1_CD") ).append( "' " );
				strSql.append("    AND TENPO_CD = '" ).append( rsSet.getString("COPY_TENPO_CD") ).append( "' " );
				strSql.append("    AND DELETE_FG = '" ).append( mst000101_ConstDictionary.DELETE_FG_NOR ).append( "' " );	

				insNum += db.executeUpdate(strSql.toString());
				
				writeLog(Level.INFO_INT, "店グルーピングマスタの登録処理を終了します。");

				writeLog(Level.INFO_INT, "登録件数確認処理を開始します。");
				
				iRec = 0;
				
				//店グルーピングマスタに登録した件数を取得する。
				strSql.delete(0,strSql.length());
				strSql.append("SELECT ");
				strSql.append("  BUNRUI1_CD, ");
				strSql.append("  TENPO_CD, ");
				strSql.append("  COUNT(*) AS COUNT ");
				strSql.append("FROM ");
				strSql.append("  R_TENGROUP ");
				strSql.append("WHERE ");
				strSql.append("  BUNRUI1_CD = '" ).append( rsSet.getString("BUNRUI1_CD") ).append( "' " );
				strSql.append("  AND TENPO_CD = '" ).append( rsSet.getString("CREATE_TENPO_CD") ).append( "' " );
				strSql.append("  AND SUBSTR(INSERT_TS,0,8) = SUBSTR('" ).append( AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") ).append( "',0,8) " );
				strSql.append("GROUP BY ");
				strSql.append("  BUNRUI1_CD,TENPO_CD ");
				
				rs = db.executeQuery(strSql.toString());

				if(rs.next()){
					iRec = rs.getInt("COUNT");
				}
				db.closeResultSet(rs);
				
				writeLog(Level.INFO_INT, iRec + "件登録しました。" + "（分類１コード：" + rsSet.getString("BUNRUI1_CD") + " 店舗コード：" + rsSet.getString("CREATE_TENPO_CD") + "）");
				
				writeLog(Level.INFO_INT, "登録件数確認処理を終了します。");

			}

			db.closeResultSet(rsSet);
			
			//対象データが存在しなかった場合
			if (recSelCnt == 0) {
				writeLog(Level.INFO_INT, "新店取扱登録条件ワークにデータは存在しませんでした。");
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
	 * 新店取扱登録条件ワーク取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer strSql = new StringBuffer("");

		strSql.append( "SELECT " );
		strSql.append( "  TRIM(CREATE_TENPO_DT) AS CREATE_TENPO_DT, " );
		strSql.append( "  TRIM(CREATE_TENPO_CD) AS CREATE_TENPO_CD, " );
		strSql.append( "  TRIM(BUNRUI1_CD) AS BUNRUI1_CD, " );
		strSql.append( "  TRIM(COPY_TENPO_CD) AS COPY_TENPO_CD " );
		strSql.append( "FROM " );
		strSql.append( "  WK_R_SINTEN_TOROKU " );
		
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