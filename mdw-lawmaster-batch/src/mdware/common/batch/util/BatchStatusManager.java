package mdware.common.batch.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.bean.BatchJobDateBean;
import mdware.common.batch.bean.BatchJobIDBean;
import mdware.common.batch.bean.BatchJobStatusBean;
import mdware.common.batch.log.BatchLog;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル: BATCHステータスを管理するクラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright  (C) 2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * <pre>
 * 入力
 * 出力
 * 戻り値
 * </pre>
 *
 * @author k_tanigawa
 * @version 1.0 (2006/08/17) 初版作成
 * @version 1.1 (2006/08/22) 追加モード時に、JobIDがあればUPDATE、なければINSERT→JobIDとマスタバッチ日付を見て、あればUPDATE、なければINSERTする様に変更(バグ対応)
 */
public class BatchStatusManager {

	//定数一覧
	private static final int START_STATUS        = 2;	//開始ステータス
	private static final String STATUS_REGISTER_0   = "0";	//上書き
	private static final String STATUS_REGISTER_1   = "1";	//追加
	public static final int END_STATUS_NORMAL   = 0;	//正常終了
	public static final int END_STATUS_WARN     = 1;	//警告終了
	public static final int END_STATUS_ABNORMAL = 9;	//異常終了
	
	private static final String CURRENT_TIMESTAMP   = "CURRENT_TIMESTAMP"; //カレントタイムスタンプでDBを更新する際に使用する定数
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();

	//マスタバッチ日付、作成年月日保持用
	private BatchJobDateBean batchJobDateBean = null;

	//JobID保持用
	private String JobID = null;
	
	/**
	 * コンストラクタ
	 */
	public BatchStatusManager(){
		this.batchJobDateBean = new BatchJobDateBean();
		this.JobID = "";
	}
	
	/**
	 * ステータス登録・更新処理(バッチ開始時使用)
	 * JOBステータステーブルにINSERTかUPDATEします(開始を表すステータスをINSERTかUPDATEします)。
	 *
	 * 存在しないJobIDを指定した場合は、STC-LIBのバッチログに出力します
	 * 
	 * @param String JobID 実行中のバッチJobID
	 * @return なし
	 **/
	public void statusRegisterProcStart(final String _jobId){

		MasterDataBase dataBase = new MasterDataBase("rbsite_ora");

		if( _jobId == null || _jobId.trim().length() <= 0 ){
			batchLog.getLog().error("JobIDを入力してください");
			return;
		}

		//BATCHJOBID検索処理
		BatchJobIDBean bjIDBean = null; 
		try{		
			bjIDBean = this.getBatchJobIdInfo(dataBase, _jobId);
		} catch (SQLException e) {
			batchLog.getLog().error("DB検索処理でエラーが発生しました。BATCHJOBSTテーブルへの開始ステータスデータの登録・更新は行いません。エラー：");
			batchLog.getLog().error(e.getStackTrace());
			dataBase.rollback();
			return;
		}
	
		//JOBID存在チェック
		if( bjIDBean == null || bjIDBean.getJobId().trim().length() <= 0){	//エラー
			batchLog.getLog().error("JobIDが存在しません。BATCHJOBSTテーブルへの開始ステータスデータの登録・更新は行いません。");
			return;
		}

		this.JobID = _jobId;	//クラス内でJobIDを共有
		
		//処理日付取得処理
		try{
			this.batchJobDateBean = this.getBatchDateBean(dataBase);
		} catch (SQLException e) {
			batchLog.getLog().error("DB検索処理でエラーが発生しました。BATCHJOBSTテーブルへの開始ステータスデータの登録・更新は行いません。");
			batchLog.getLog().error(e.getStackTrace());
			dataBase.rollback();
			return;
		}

		//BATCHJOBST検索処理
		BatchJobStatusBean bjStsBean = null;
		try{
			bjStsBean = this.getBatchJobStatusInfo(dataBase, _jobId, this.batchJobDateBean.getBatchDate());
		} catch (SQLException e) {
			batchLog.getLog().error("DB検索処理でエラーが発生しました。BATCHJOBSTテーブルへの開始ステータスデータの登録・更新は行いません。");
			batchLog.getLog().error(e.getStackTrace());
			dataBase.rollback();
			return;
		}

		//BATCHJOBST登録処理
		String execSQL = null;
		if( !(bjIDBean.getTorokuKb().trim().equals(STATUS_REGISTER_1)) ){					//上書きモード(STATUS_REGISTER_1以外)

			if ((bjStsBean == null) || (bjStsBean.getJobID().trim().length() <= 0)) {	// テーブルにデータが存在しない場合
				execSQL = this.getBatchJobStatusInsertSQL(bjIDBean,	this.batchJobDateBean);	// INSERT
			} else { // テーブルにデータが存在する場合
				bjStsBean.setInsertTsForUpdate(this.batchJobDateBean.getCreateDate());
				bjStsBean.setSts(START_STATUS);
				bjStsBean.setStartTs(CURRENT_TIMESTAMP);
				bjStsBean.setEndTs(null);
				bjStsBean.setClassName(bjIDBean.getClassName());
				bjStsBean.setClassNameJa(bjIDBean.getClassNameJa());
				bjStsBean.setSubSysNa(bjIDBean.getSubSysNa());
				bjStsBean.setInpCnt1(0);
				bjStsBean.setInpCnt2(0);
				bjStsBean.setInpCnt3(0);
				bjStsBean.setInpCnt4(0);
				bjStsBean.setInpCnt5(0);
				bjStsBean.setOutCnt1(0);
				bjStsBean.setOutCnt2(0);
				bjStsBean.setOutCnt3(0);
				bjStsBean.setOutCnt4(0);
				bjStsBean.setOutCnt5(0);

				execSQL = this.getBatchJobStatusUpdateSQL(bjStsBean);					// UPDATE
			}
		} else {																		// 追加モード(STATUS_REGISTER_1)
			execSQL = this.getBatchJobStatusInsertSQL(bjIDBean, this.batchJobDateBean);// INSERT
		} 
		
		/*******************************
		 *     UPDATE/INSERT処理実行   *
		/*******************************/
		try {
			dataBase.executeUpdate(execSQL);
			dataBase.commit();
		} catch (SQLException e) {
			batchLog.getLog().error("DB登録・更新処理でエラーが発生しました。BATCHJOBSTテーブルへの開始ステータスデータの登録・更新は行いません。エラー：");
			batchLog.getLog().error(e.getStackTrace());
			dataBase.rollback();
			return;
		}
	}

	
	/**
	 * ステータス更新処理(バッチ終了時使用)
	 * JOBステータステーブルをUPDATEします。
	 * このメソッドは、バッチ処理終了時に使用して下さい。
	 * 入力件数1～5、出力件数1～5には、後続の処理にパラメータとして渡したい値を
	 * setして下さい。
	 * 
	 * 存在しないJobIDを指定した場合は、STC-LIBのバッチログに出力します
	 * 
	 * @param String JobID 実行中のバッチJobID
	 * @param int iSts バッチ処理のステータス
	 * @param int input1 入力件数1
	 * @param int input2 入力件数2
	 * @param int input3 入力件数3
	 * @param int input4 入力件数4
	 * @param int input5 入力件数5
	 * @param int output1 出力件数1
	 * @param int output2 出力件数2
	 * @param int output3 出力件数3
	 * @param int output4 出力件数4
	 * @param int output5 出力件数5
	 * @return なし
	 **/
	public void statusRegisterProcEnd(String _jobId, int iSts, int input1, int input2, int input3, int input4, int input5, int output1, int output2, int output3, int output4, int output5){

		BatchJobStatusBean bjsb = new BatchJobStatusBean();
		bjsb.setJobID(_jobId);
		bjsb.setSts(iSts);
		bjsb.setInpCnt1(input1);
		bjsb.setInpCnt2(input2);
		bjsb.setInpCnt3(input3);
		bjsb.setInpCnt4(input4);
		bjsb.setInpCnt5(input5);
		bjsb.setOutCnt1(output1);
		bjsb.setOutCnt2(output2);
		bjsb.setOutCnt3(output3);
		bjsb.setOutCnt4(output4);
		bjsb.setOutCnt5(output5);

		statusRegisterProcEnd(bjsb);
	}
	
	
	private void statusRegisterProcEnd(final BatchJobStatusBean bjsb){

		MasterDataBase dataBase = new MasterDataBase("rbsite_ora");

		if( bjsb.getJobID() == null || bjsb.getJobID().trim().length() <= 0 ){
			batchLog.getLog().error("JobIDがありません");
			return;
		}
		
		if( !this.JobID.trim().equalsIgnoreCase(bjsb.getJobID().trim()) ){
			batchLog.getLog().error("ステータス登録開始時にセットしたJobIDとは異なるJobIDがセットされています。");
			batchLog.getLog().error("ステータス登録開始時にセットしたJobID："+this.JobID);
			batchLog.getLog().error("ステータス登録終了時にセットしたJobID："+bjsb.getJobID());
			return;
		}
		
		//BATCHJOBID検索処理
		BatchJobIDBean bjIDBean = null; 
		try{		
			bjIDBean = this.getBatchJobIdInfo(dataBase, bjsb.getJobID());
		} catch (SQLException e) {
			batchLog.getLog().error("DB検索処理でエラーが発生しました。BATCHJOBSTテーブルへの開始ステータスデータの登録・更新は行いません。");
			dataBase.rollback();
			return;
		}
	
		//JOBID存在チェック
		if( bjIDBean == null || bjIDBean.getJobId().trim().length() <= 0){	//エラー
			batchLog.getLog().error("JobIDが存在しません。BATCHJOBSTテーブルへの開始ステータスデータの登録・更新は行いません。");
			return;
		}

		//UPDATE前処理
		BatchJobStatusBean bjStsBean = new BatchJobStatusBean();
		bjStsBean.setJobID( bjsb.getJobID() );
		bjStsBean.setSyoriDt( this.batchJobDateBean.getBatchDate() );
		bjStsBean.setInsertTsForWhere( this.batchJobDateBean.getCreateDate() );
		bjStsBean.setInsertTsForUpdate(null);
		bjStsBean.setSts( Integer.parseInt(bjsb.getSts()) );
		bjStsBean.setStartTs(null);
		bjStsBean.setEndTs(CURRENT_TIMESTAMP);
		bjStsBean.setClassName(bjIDBean.getClassName());
		bjStsBean.setClassNameJa(bjIDBean.getClassNameJa());
		bjStsBean.setSubSysNa(bjIDBean.getSubSysNa());
		bjStsBean.setInpCnt1(bjsb.getInpCnt1());
		bjStsBean.setInpCnt2(bjsb.getInpCnt2());
		bjStsBean.setInpCnt3(bjsb.getInpCnt3());
		bjStsBean.setInpCnt4(bjsb.getInpCnt4());
		bjStsBean.setInpCnt5(bjsb.getInpCnt5());
		bjStsBean.setOutCnt1(bjsb.getOutCnt1());
		bjStsBean.setOutCnt2(bjsb.getOutCnt2());
		bjStsBean.setOutCnt3(bjsb.getOutCnt3());
		bjStsBean.setOutCnt4(bjsb.getOutCnt4());
		bjStsBean.setOutCnt5(bjsb.getOutCnt5());
	
		/*******************************
		 *        UPDATE処理実行       *
		/*******************************/
		String updateSQL = this.getBatchJobStatusUpdateSQL(bjStsBean);
		try {
			dataBase.executeUpdate(updateSQL);
			dataBase.commit();
		} catch (SQLException e) {
			batchLog.getLog().error("DB登録・更新処理でエラーが発生しました。BATCHJOBSTテーブルへの開始ステータスデータの登録・更新は行いません。エラー：");
			batchLog.getLog().error(e.getStackTrace());
			dataBase.rollback();
			return;
		}
	}
	
	/************************************************
	 * BATCHJOBIDテーブルよりデータを取得する       *
	 *                                              *
	 * @param String batchJobId バッチJOBID        *
	 * @return BatchJobIDBean 検索結果が入ったBean *
	 ************************************************/
	private BatchJobIDBean getBatchJobIdInfo(DataBase db, String batchJobId) throws SQLException{
		
		String sql = getBatchJobIdInfoSQL(batchJobId);

		//検索処理
		ResultSet rest;
		BatchJobIDBean bjidBean = new BatchJobIDBean();
		rest = db.executeQuery( sql );
		while( (rest != null) && (rest.next()) ) {
			bjidBean.setJobId(rest.getString("jobid"));
			bjidBean.setTorokuKb(rest.getString("torokukb"));
			bjidBean.setClassName(rest.getString("class"));
			bjidBean.setClassNameJa(rest.getString("classna"));
			bjidBean.setSubSysNa(rest.getString("subsysna"));
		}

		return bjidBean;
	}
	
	/************************************************
	 * BATCHJOBIDﾃｰﾌﾞﾙよりﾃﾞｰﾀを取得するSQLを返す   *
	 *                                              *
	 * @param String batchJobId バッチJOBID        *
	 * @return String テーブル検索用SQL            *
	 ************************************************/
	private String getBatchJobIdInfoSQL(String batchJobId){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT            ");
		sb.append("     T1.JOBID      ");
		sb.append("    ,T1.TOROKUKB   ");
		sb.append("    ,T1.CLASS      ");
		sb.append("    ,T1.CLASSNA    ");
		sb.append("    ,T1.SUBSYSNA   ");
		sb.append(" FROM              ");
		sb.append("     BATCHJOBID T1 ");
		sb.append(" WHERE             ");
		sb.append("     T1.JOBID = '"+batchJobId+"' ");
		
		return sb.toString();
	}
	
	/************************************************
	 * SYSTEM_CONTROLﾃｰﾌﾞﾙよりﾏｽﾀﾊﾞｯﾁ日付を取得する *
	 *                                              *
	 * @return String ﾏｽﾀﾊﾞｯﾁ日付                  *
	 ************************************************/
	private BatchJobDateBean getBatchDateBean(DataBase db) throws SQLException{
		
		String sql = getMasterBatchDateInfoSQL();

		//検索処理
		ResultSet rest;
		rest = db.executeQuery( sql );
		BatchJobDateBean batchJobDateBean = new BatchJobDateBean();
		while( (rest != null) && (rest.next()) ) {

			
			batchJobDateBean.setBatchDate(rest.getString("bat_date_dt"));
			batchJobDateBean.setCreateDate(rest.getString("create_dt"));
		}
		
		return batchJobDateBean;
	}
	
	/*********************************************************
	 * SYSTEM_CONTROLﾃｰﾌﾞﾙよりﾏｽﾀﾊﾞｯﾁ日付を取得するSQLを返す *
	 *                                                       *
	 * @return String テーブル検索用SQL                     *
	 *********************************************************/
	private String getMasterBatchDateInfoSQL(){

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT                ");
		sb.append("     T1.BAT_DATE_DT    ");
		sb.append("    ,TO_CHAR(CURRENT TIMESTAMP, 'YYYYMMDDHH24MISS') AS CREATE_DT ");
		sb.append(" FROM                  ");
		sb.append("     SYSTEM_CONTROL T1 ");
		
		return sb.toString();
	}
	
	/****************************************************
	 * BATCHJOBSTテーブルよりデータを取得する           *
	 *                                                  *
	 * @param String batchJobId バッチJOBID            *
	 * @return BatchJobStatusBean 検索結果が入ったBean *
	 ****************************************************/
	private BatchJobStatusBean getBatchJobStatusInfo(DataBase db, String batchJobId, String batchDate) throws SQLException{
		
		String sql = getBatchJobStatusSQL(batchJobId, batchDate);

		//検索処理
		ResultSet rest;
		rest = db.executeQuery( sql );
		BatchJobStatusBean bjsBean = new BatchJobStatusBean();
		while( (rest != null) && (rest.next()) ) {

			bjsBean.setJobID( rest.getString("jobid") );
			bjsBean.setSyoriDt( rest.getString("syoridt") );
			bjsBean.setInsertTsForWhere( rest.getString("insertts") );
			bjsBean.setInsertTsForUpdate( bjsBean.getInsertTsForWhere().trim() );
//			bjsBean.setClassName(  );
//			bjsBean.setClassNameJa();
//			bjsBean.setEndTs();
//			bjsBean.setInpCnt1();
//			bjsBean.setInpCnt2();
//			bjsBean.setInpCnt3();
//			bjsBean.setInpCnt4();
//			bjsBean.setInpCnt5();
//			bjsBean.setInsertTs();
//			bjsBean.setOutCnt1();
//			bjsBean.setOutCnt2();
//			bjsBean.setOutCnt3();
//			bjsBean.setOutCnt4();
//			bjsBean.setOutCnt5();
//			bjsBean.setStartTs();
//			bjsBean.setSts();
//			bjsBean.setSubSysNa();
		}
		
		
		//Beanセット処理
		
		return bjsBean;
	}
	
	/************************************************
	 * BATCHJOBSTﾃｰﾌﾞﾙよりﾃﾞｰﾀを取得するSQLを返す   *
	 *                                              *
	 * @param String batchJobId バッチJOBID        *
	 * @return String テーブル検索用SQL            *
	 ************************************************/
	private String getBatchJobStatusSQL(String batchJobId, String batchDate){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT                            ");
		sb.append("      max(T1.JOBID) as JOBID       ");
		sb.append("     ,max(T1.SYORIDT) as SYORIDT   ");
		sb.append("     ,max(T1.INSERTTS) as INSERTTS ");
//		sb.append("     ,T1.STS                         ");
//		sb.append("     ,T1.STARTTS                     ");
//		sb.append("     ,T1.ENDTS                       ");
//		sb.append("     ,T1.CLASS                       ");
//		sb.append("     ,T1.CLASSNA                     ");
//		sb.append("     ,T1.SUBSYSNA                    ");
//		sb.append("     ,T1.INPCNT1                     ");
//		sb.append("     ,T1.INPCNT2                     ");
//		sb.append("     ,T1.INPCNT3                     ");
//		sb.append("     ,T1.INPCNT4                     ");
//		sb.append("     ,T1.INPCNT5                     ");
//		sb.append("     ,T1.OUTCNT1                     ");
//		sb.append("     ,T1.OUTCNT2                     ");
//		sb.append("     ,T1.OUTCNT3                     ");
//		sb.append("     ,T1.OUTCNT4                     ");
//		sb.append("     ,T1.OUTCNT5                     ");
		sb.append(" FROM                               ");
		sb.append("      BATCHJOBST T1                 ");
		sb.append(" WHERE                              ");
		sb.append("      T1.JOBID   = '"+batchJobId+"' ");
		sb.append("  AND T1.SYORIDT = '"+batchDate+"'  ");

		return sb.toString();
	}
	
	/************************************************
	 * BATCHJOBSTﾃｰﾌﾞﾙにINSERTするSQLを返す         *
	 *                                              *
	 * @param String batchJobId バッチJOBID        *
	 * @return String テーブル検索用SQL            *
	 ************************************************/
	private String getBatchJobStatusInsertSQL(final BatchJobIDBean bJIDBean, final BatchJobDateBean batchJobDateBean){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT INTO BATCHJOBST(                          ");
		sb.append("  JOBID                                          ");
		sb.append(" ,SYORIDT                                        ");
		sb.append(" ,INSERTTS                                       ");
		sb.append(" ,STS                                            ");
		sb.append(" ,STARTTS                                        ");
		sb.append(" ,ENDTS                                          ");
		sb.append(" ,CLASS                                          ");
		sb.append(" ,CLASSNA                                        ");
		sb.append(" ,SUBSYSNA                                       ");
		sb.append(" ,INPCNT1                                        ");
		sb.append(" ,INPCNT2                                        ");
		sb.append(" ,INPCNT3                                        ");
		sb.append(" ,INPCNT4                                        ");
		sb.append(" ,INPCNT5                                        ");
		sb.append(" ,OUTCNT1                                        ");
		sb.append(" ,OUTCNT2                                        ");
		sb.append(" ,OUTCNT3                                        ");
		sb.append(" ,OUTCNT4                                        ");
		sb.append(" ,OUTCNT5                                        ");
		sb.append(" ) VALUES (                                      ");
		sb.append(" '"+bJIDBean.getJobId()+"'                       ");
		sb.append(" ,'"+batchJobDateBean.getBatchDate()+"'          ");
		sb.append(" ,'"+batchJobDateBean.getCreateDate()+"'         ");
		sb.append(" ,'"+START_STATUS+"'                             ");
		sb.append(" ,TO_CHAR(CURRENT TIMESTAMP, 'YYYYMMDDHH24MISS') ");
		sb.append(" ,0                                              ");
		sb.append(" ,'"+bJIDBean.getClassName()+"'                  ");
		sb.append(" ,'"+bJIDBean.getClassNameJa()+"'                ");
		sb.append(" ,'"+bJIDBean.getSubSysNa()+"'                   ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" ,0                                              ");
		sb.append(" )                                               ");

		return sb.toString();
	}
	
	/************************************************
	 * BATCHJOBSTﾃｰﾌﾞﾙをUPDATEするSQLを返す         *
	 *                                              *
	 * @param String batchJobId バッチJOBID        *
	 * @return String テーブル検索用SQL            *
	 ************************************************/
	private String getBatchJobStatusUpdateSQL(final BatchJobStatusBean bJSTSBean){

		StringBuffer sb = new StringBuffer();
		char comma = ' ';
		
		sb.append(" UPDATE                                           ");
		sb.append("     BATCHJOBST                                   ");
		sb.append(" SET                                              ");
	if( bJSTSBean.getInsertTsForUpdate() != null && bJSTSBean.getInsertTsForUpdate().trim().length() > 0 ){
		sb.append("      INSERTTS = '"+bJSTSBean.getInsertTsForUpdate()+"'    ");
		comma = ',';
	}
		sb.append(comma);
		sb.append("      STS      = '"+bJSTSBean.getSts()+"'         ");
	if( bJSTSBean.getStartTs().trim().equals(CURRENT_TIMESTAMP) ){
		sb.append("     ,STARTTS = TO_CHAR(CURRENT TIMESTAMP, 'YYYYMMDDHH24MISS')    ");
	} 
	if( bJSTSBean.getEndTs().trim().equals(CURRENT_TIMESTAMP) ){
		sb.append("     ,ENDTS = TO_CHAR(CURRENT TIMESTAMP, 'YYYYMMDDHH24MISS')    ");
	} else {
		sb.append("     ,ENDTS = '0'                                 ");
	}
		sb.append("     ,CLASS    = '"+bJSTSBean.getClassName()+"'   ");
		sb.append("     ,CLASSNA  = '"+bJSTSBean.getClassNameJa()+"' ");
		sb.append("     ,SUBSYSNA = '"+bJSTSBean.getSubSysNa()+"'    ");
		sb.append("     ,INPCNT1  = "+bJSTSBean.getInpCnt1()+"       ");
		sb.append("     ,INPCNT2  = "+bJSTSBean.getInpCnt2()+"       ");
		sb.append("     ,INPCNT3  = "+bJSTSBean.getInpCnt3()+"       ");
		sb.append("     ,INPCNT4  = "+bJSTSBean.getInpCnt4()+"       ");
		sb.append("     ,INPCNT5  = "+bJSTSBean.getInpCnt5()+"       ");
		sb.append("     ,OUTCNT1  = "+bJSTSBean.getOutCnt1()+"       ");
		sb.append("     ,OUTCNT2  = "+bJSTSBean.getOutCnt2()+"       ");
		sb.append("     ,OUTCNT3  = "+bJSTSBean.getOutCnt3()+"       ");
		sb.append("     ,OUTCNT4  = "+bJSTSBean.getOutCnt4()+"       ");
		sb.append("     ,OUTCNT5  = "+bJSTSBean.getOutCnt5()+"       ");
		sb.append(" WHERE                                            ");
		sb.append("      JOBID    = '"+bJSTSBean.getJobID()+"'       ");
		sb.append("  AND SYORIDT  = '"+bJSTSBean.getSyoriDt()+"'     ");
		sb.append("  AND INSERTTS = '"+bJSTSBean.getInsertTsForWhere()+"'     ");

		return sb.toString();
	}
	
	
	
	
	/**
	* TEST FUNCTION 
	* A USEFUL SAMPLE ON HOW TO USE THIS CLASS.
	* 
	* PURPOSE OF USING THIS CLASS.
	* 1. TO GIVE JAVA PROGRAMS RESULT AS PARAMETERS TO THE COBOL PROGRAMS
	*** THIS CLASS WILL NOT AFFECT THE EXISTING DATABASE TRANSACTION OF BATCH PROGRAMS(THE CLASS WILL CREATE ITS OWN DATABASE TRANSACTION TO INSURE THE STATUS WILL BE INSERTED TO THE DB2)
	* 
	* THE PARAMETERS WILL BE REGISTERED IN A TABLE CALLED BATCHJOBST
	* FOR MORE INFORMATION ON HOW TO USE THIS CLASS, PLEASE SEE THE SAMPLE PROGRAM BELOW.
	* FOR ANY QUESTIONS OR COMMENTS PLEASE CALL 4153 FOR TANIGAWA OR TUTUMI-SAN.
	* 
	* @param args 
	*/
	public static void main(String[] args) {
        String propertyDir = "C:/eclipse_3.1.2/workspace/Test/WEB-INF/properties";	//where your properties is located
		String executeMode = "release";												//release mode? debug mode?
		String databaseUse = "use";													//use database pool?
		mdware.common.batch.util.control.BatchController controller =
			new mdware.common.batch.util.control.BatchController();	//create your own instance for the batch controller
		controller.init(propertyDir, executeMode, databaseUse);			
		BatchStatusManager batch = new BatchStatusManager();		//create your own instance for the Batch Status controller.
																	//*It's not a singleton.*
		String JobID = "TESTJOBID1";								//set your JobID
		batch.statusRegisterProcStart(JobID);						//By calling this method, 
																	//it makes a record in the job status table with the given argument( or parameter).
																	//depending on the data on JobID table,  
																	//the record is either updated or created.
																	//if the TOROKUKB field in the JobID table is 0,
																	//the data will be created if it does not exist on the BATCHJOBST table
																	//						   if it exists, the data having the most recent(latest) INSERTTS will be updated to the "BEGINNING" status.
																	//if the TOROKUKB field in the JobID table is 1,
																	//the data will always be created.
																	//The first status is the "BEGINNING" status.

		System.out.println("中間点");								//output your own message to the console window. THIS HAS NOTHING TO DO WITH THE PROGRAM.

		batch.statusRegisterProcEnd(JobID,BatchStatusManager.END_STATUS_NORMAL,1,2,3,4,5,6,7,8,9,10);	
																	//By calling this method, 
																	//it will update a record in the job status table( data which you've created/updated by calling batch.statusRegisterProcStart(JobID);). 
																	//The status is the "ENDING" status.
	}
}
