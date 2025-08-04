package mdware.master.batch.process.mb44;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;
/**
 * <p>タイトル:店グルーピングマスタ洗替処理</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author sawabe
 * @version 1.00 2005/05/24<BR>
 * @version 2.0 (2009/01/13) マミーマート様向け初版作成 A.Okada
 */
 
public class MB440101_TenGroupMstReplacement {

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// バッチ日付
	private String 		batchDt 	= null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB440101_TenGroupMstReplacement(MasterDataBase db) {

		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB440101_TenGroupMstReplacement() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute( String batchId ) throws Exception {

		execute();

	}


	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		
		try{

			ResultSet	rs       = null;
			ResultSet	rs2      = null;
			long		insCnt   = 0;

			
			this.writeLog(Level.INFO_INT, "処理を開始します。");

			
			// システムコントロール情報取得
			this.getSystemControl();
			

			// 既存データ削除
			this.writeLog(Level.INFO_INT, "既存の全店データ削除処理を開始します。");
			this.deleteExistData();
			this.writeLog(Level.INFO_INT, "既存の全店データ削除処理を終了します。");

			// 店舗データ取得
			this.writeLog(Level.INFO_INT, "店舗データ取得処理を開始します。");
			rs = this.selectTargetData();
			this.writeLog(Level.INFO_INT, "店舗データ取得処理を終了します。");

			// 店グルーピングデータ登録
			this.writeLog(Level.INFO_INT, "店グルーピングデータ登録処理を開始します。");

			insCnt = this.insertTargetData( rs );
			if( insCnt == 0 ){
				this.writeLog(Level.INFO_INT, "店グルーピングマスタへの登録対象データがありませんでした。");
				// 店舗データが存在しない場合は、削除データを元に戻す
				db.rollback();
			} else {
				this.writeLog(Level.INFO_INT, insCnt + "件、店グルーピングマスタへデータを登録しました。");
				db.commit();
			}
			
			this.writeLog(Level.INFO_INT, "店グルーピングデータ登録処理を終了します。");

			db.closeResultSet( rs );


			// 閉店店舗データ取得
			this.writeLog(Level.INFO_INT, "閉店店舗データ取得処理を開始します。");
			rs2 = this.selectCloseTenpoData();
			this.writeLog(Level.INFO_INT, "閉店店舗データ取得処理を終了します。");

			// 店グルーピングデータ更新
			this.writeLog(Level.INFO_INT, "店グルーピングデータ更新処理を開始します。");

			insCnt = this.updateTargetData( rs2 );
			if( insCnt == 0 ){
				this.writeLog(Level.INFO_INT, "店グルーピングマスタへの更新対象データがありませんでした。");
			} else {
				this.writeLog(Level.INFO_INT, insCnt + "件、店グルーピングマスタのデータを更新しました。");
				db.commit();
			}
			
			this.writeLog(Level.INFO_INT, "店グルーピングデータ更新処理を終了します。");

			db.closeResultSet( rs2 );
			
			this.writeLog(Level.INFO_INT, "処理を終了します。");

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
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// 初期化
		batchDt 	= null;
		
    	// バッチ日付
		batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

    	if(batchDt == null || batchDt.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
    		throw new Exception();
		}
	}

	/**
	 * 既存の全店データ削除
	 * @param  なし
	 * @return なし
	 * @throws 例外
	 */
	private void deleteExistData() throws SQLException, Exception{

		long delCnt = 0;
		//店グルーピングマスタから、全店コードを削除する
		delCnt =  db.executeUpdate( this.getDelDataSQL() );

		this.writeLog(Level.INFO_INT, delCnt + "件、店グルーピングの全店グループデータを削除しました。");
	}

	/**
	 * 店グルーピングマスタ削除用SQLを取得します。
	 * @return SQL
	 */
	private String getDelDataSQL() {

		StringBuffer sb = new StringBuffer("");

		sb.append( "DELETE FROM " );
		sb.append( "    R_TENGROUP " );
		sb.append( "WHERE " );
		sb.append( "    GROUPNO_CD = '" ).append( mst000101_ConstDictionary.ALL_TEN_GROUP ).append( "' " );
		
		return( sb.toString() );
	}

	/**
	 * 有効店舗データ取得
	 * @param  なし
	 * @return ResultSet
	 * @throws 例外
	 */
	private ResultSet selectTargetData() throws SQLException, Exception{

		ResultSet rs = null;
		
		rs = db.executeQuery( this.getSelDataSQL() );

		return( rs );
	}

	/**
	 * 店舗マスタ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		Map map = ResorceUtil.getInstance().getPropertieMap( mst000101_ConstDictionary.MASTER_USE_TENPO_KB ); 
		
		sb.append("SELECT ");
		sb.append("    TRIM(TENPO_CD)      AS TENPO_CD, ");
		sb.append("    TRIM(TENPO_TYPE_KB) AS TENPO_TYPE_KB, ");
		sb.append("    TRIM(TENPO_KB)      AS TENPO_KB, ");
		sb.append("    TRIM(HEITEN_DT)     AS HEITEN_DT ");
		sb.append("FROM ");
		sb.append("    R_TENPO ");
		sb.append("WHERE ");
		sb.append("    TENPO_KB in ( " ).append( SqlSupportUtil.createInString( map.keySet().toArray() ).toString() ).append( " ) " ); 
		sb.append("AND " );
		sb.append("    NVL(HEITEN_DT,'99991231') >= '" ).append( batchDt ).append( "' " );
		sb.append("AND " );
		sb.append("    DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("ORDER BY " );
		sb.append("    TENPO_CD " );

		return( sb.toString() );
	}
	
	/**
	 * 店グルーピングマスタ登録
	 * @param  なし
	 * @return ResultSet
	 * @throws 例外
	 */
	private long insertTargetData( ResultSet rs ) throws SQLException, Exception{

		long rankNb = 0;
		long insCnt = 0;

		while( rs.next() ){

			//順位カウント
			rankNb++;

			// 登録件数カウント
			insCnt += db.executeUpdate( this.getInsDataSQL(rs, rankNb) );

		}

		return( insCnt );
	}
	
	/**
	 * 店グルーピングマスタ登録SQLを取得します。
	 * @param  ResultSet, int
	 * @return SQL
	 * @throws 例外
	 */
	private String getInsDataSQL( ResultSet rs, long rankNb ) throws SQLException, Exception{

		StringBuffer sb = new StringBuffer("");

		sb.append("INSERT INTO R_TENGROUP (");
		sb.append("    BUNRUI1_CD,");
		sb.append("    YOTO_KB,");
		sb.append("    GROUPNO_CD,");
		sb.append("    TENPO_CD,");
		sb.append("    RANK_NB,");
		sb.append("    ANBUN_RT,");
		sb.append("    INSERT_TS,");
		sb.append("    INSERT_USER_ID,");
		sb.append("    UPDATE_TS,");
		sb.append("    UPDATE_USER_ID,");
		sb.append("    DELETE_FG");
		sb.append(") ");
		sb.append("    SELECT ");
		sb.append("        BUNRUI1_CD,");
		sb.append("        '1',");
		sb.append("        GROUPNO_CD, ");
		sb.append("        '" + rs.getString( "TENPO_CD" ) +"', ");
		sb.append("        " + rankNb + ", ");
		sb.append("        0, ");
		sb.append("        '" + AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" ).toString() + "',");
		sb.append("        '" + userLog.getJobId() +"',");
		sb.append("        '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sb.append("        '" + userLog.getJobId() +"',");
		sb.append("        '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("    FROM ");
		sb.append("        R_TENGROUPNO ");
		sb.append("    WHERE ");
		sb.append("        GROUPNO_CD = '" ).append( mst000101_ConstDictionary.ALL_TEN_GROUP ).append( "' " );
		sb.append("    AND ");
		sb.append("        DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("    GROUP BY ");
		sb.append("        BUNRUI1_CD, ");
		sb.append("        GROUPNO_CD ");

		return( sb.toString() );
	}
		
	/**
	 * 閉店店舗データ取得
	 * @param  なし
	 * @return ResultSet
	 * @throws 例外
	 */
	private ResultSet selectCloseTenpoData() throws SQLException, Exception{

		ResultSet rs = null;
		
		rs = db.executeQuery( this.getSelCloseDataSQL() );

		return( rs );
	}

	/**
	 * 店舗マスタ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelCloseDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		Map map = ResorceUtil.getInstance().getPropertieMap( mst000101_ConstDictionary.MASTER_USE_TENPO_KB ); 
		
		sb.append("SELECT ");
		sb.append("    TRIM(TENPO_CD)      AS TENPO_CD, ");
		sb.append("    TRIM(TENPO_TYPE_KB) AS TENPO_TYPE_KB, ");
		sb.append("    TRIM(TENPO_KB)      AS TENPO_KB, ");
		sb.append("    TRIM(HEITEN_DT)     AS HEITEN_DT ");
		sb.append("FROM ");
		sb.append("    R_TENPO ");
		sb.append("WHERE ");
		sb.append("    TENPO_KB in ( " ).append( SqlSupportUtil.createInString( map.keySet().toArray() ).toString() ).append( " ) " ); 
		sb.append("AND " );
		sb.append("    HEITEN_DT < '" ).append( batchDt ).append( "' " );
		sb.append("AND " );
		sb.append("    DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("ORDER BY " );
		sb.append("    TENPO_CD " );

		return( sb.toString() );
	}
	
	/**
	 * 店グルーピングマスタ更新
	 * @param  なし
	 * @return ResultSet
	 * @throws 例外
	 */
	private long updateTargetData( ResultSet rs2 ) throws SQLException, Exception{

		long updCnt = 0;

		while( rs2.next() ){

			// 更新件数カウント
			updCnt += db.executeUpdate( this.getUpdDataSQL( rs2 ) );

		}

		return( updCnt );
	}
	
	/**
	 * 店グルーピングマスタ登録SQLを取得します。
	 * @param  ResultSet, int
	 * @return SQL
	 * @throws 例外
	 */
	private String getUpdDataSQL( ResultSet rs2 ) throws SQLException, Exception{

		StringBuffer sb = new StringBuffer("");

		sb.append(" UPDATE R_TENGROUP SET " );
		sb.append("     DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "', " );
		sb.append("     UPDATE_TS = '" ).append( AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" ).toString() ).append( "', " );
		sb.append("     UPDATE_USER_ID = '" ).append( userLog.getJobId() ).append( "' " );
		sb.append(" WHERE ");
		sb.append("     TENPO_CD = '" ).append( rs2.getString("TENPO_CD").trim() ).append( "' " );
		sb.append(" AND ");
		sb.append("     DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );

		return( sb.toString() );
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