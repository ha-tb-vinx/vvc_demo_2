package mdware.master.batch.process.mbB3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:不稼働商品出力処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2012/06/20<BR>
 * @author Y.Imai
 */

public class MBB30101_FukadoSyohinOutput {
	
	// CSVファイル名（商品マスタ）
	private static final String FILE_NAME		= "FUKADO_SYOHIN_DPT_";
	// 区切り文字
	private static final String SPLIT_CODE	= ",";
	// 改行文字
	private static final String RETURN_CODE	= System.getProperty("line.separator");

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private static final String BATCH_ID = "MBB3-01-01";
	private static final String BATCH_NA = "不稼働商品出力処理";
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();
	
	// バッチ日付
	private String batchDt = null;
	// システム日付
	private String systemDt = null;
	// 現在日付
	private String nowDt = null;
	// CSVファイルパス
	private String csvFilePath = null;
	// jobId
	private String jobId = null;
	
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBB30101_FukadoSyohinOutput( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBB30101_FukadoSyohinOutput() {

		this( new MasterDataBase( "rbsite_ora" ) );
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
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		
		// 対象店舗件数
		int selTenpoCnt = 0;
		// TMPテーブル件数
		int insertCnt = 0;
		// 出力件数
		long outPutCsvCnt = 0;
		
		// 画面パラメータ
		String parameter = "";
		// 指定DPT
		String shiteiBunrui1Cd = "";
		// 対象店舗コード
		String tenpoCd = "";
		// 対象店舗名
		String tenpoNa = "";
		// 結合テーブル名
		String joinTableNa = "";
		
		ResultSet rsParameter = null;
		ResultSet rsDpt = null;
		ResultSet rsTenpo = null;
		ResultSet rsCsvMeisai = null;
		
		String jobId = userLog.getJobId();
		
		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
    		
			this.writeLog(Level.INFO_INT, "処理を開始します。");
			
			// システムコントロール情報取得
			this.getSystemControl();
			
			// システム日付取得
			systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );
						
			// 画面パラメータ取得処理実行
			rsParameter = db.executeQuery(getParameter());
			
			// 出力対象店舗(全営業店)取得処理実行
			rsTenpo = db.executeQuery(getTenpoData());
			
			if (rsParameter.next()){
				
				// 画面パラメータ取得
				parameter = StringUtility.null2string(rsParameter,"LAST_PARAMETER1_TX".trim());
				
				// DPT存在確認処理実行
				rsDpt = db.executeQuery(getShiteiBunrui1Cd(parameter));
				
				// DPT存在確認
				if (rsDpt.next()){
					shiteiBunrui1Cd = rsDpt.getString("BUNRUI1_CD").trim();
				
					// 単品店取扱マスタの指定DPTのデータのみを一時テーブルとして作成
					insertCnt += db.executeUpdate(getInsTmpTanpinten(shiteiBunrui1Cd));
					
					if( insertCnt != 0 ){
						this.writeLog(Level.INFO_INT, "一時テーブルTMP_TANPINTEN_DPTへ" + insertCnt + "件のデータを登録しました。");
					
						if(rsTenpo != null && insertCnt != 0){
							
							// CSV出力データ取得
							StringBuffer sql = new StringBuffer("");
							StringBuffer sql1 = new StringBuffer("");
							StringBuffer sql2 = new StringBuffer("");
							StringBuffer strHead = new StringBuffer("");
							StringBuffer strHead2 = new StringBuffer("");
							
							// 店舗数分繰り返し
							while(rsTenpo.next()){
							
								// 対象店舗コード
								tenpoCd = rsTenpo.getString("TENPO_CD").trim();
								// 対象店舗名称
								tenpoNa = rsTenpo.getString("KANJI_NA").trim();
								// 結合テーブル名
								joinTableNa = "TDD" + tenpoCd;
								// 店舗件数カウント
								selTenpoCnt++;
		
								// CSVヘッダ行取得
								// 店舗情報（店舗コード：店舗名）
								strHead2.append( tenpoCd + ":" + tenpoNa ).append(SPLIT_CODE);
		
								// CSV明細行取得SQL
								sql1.append( "	,CASE WHEN " + joinTableNa + ".NON_ACT_KB = '9' THEN '×' " );
								sql1.append( "	 ELSE NULL " );
								sql1.append( "	 END AS TENPO_DATA"+ selTenpoCnt +" " );
								
								sql2.append( "	LEFT OUTER JOIN " );
								sql2.append( "		( " );
								sql2.append( "			SELECT * FROM " );
								sql2.append( "				TMP_TANPINTEN_DPT " );
								sql2.append( "			WHERE " );
								sql2.append( "				TENPO_CD = '" + tenpoCd + "' " );
								sql2.append( "		) " + joinTableNa + " " );
								sql2.append( "	ON " );
								sql2.append( "		WFS.BUNRUI1_CD = " + joinTableNa + ".BUNRUI1_CD " );
								sql2.append( "	AND WFS.SYOHIN_CD = " + joinTableNa + ".SYOHIN_CD " );
							}
							
							// CSVヘッダ行取得
							strHead.append("DPT").append(SPLIT_CODE);
							strHead.append("ライン").append(SPLIT_CODE);
							strHead.append("クラス").append(SPLIT_CODE);
							strHead.append("取引先コード").append(SPLIT_CODE);
							strHead.append("取引先名称").append(SPLIT_CODE);
							strHead.append("商品コード").append(SPLIT_CODE);
							strHead.append("商品名称").append(SPLIT_CODE);
							strHead.append(strHead2.toString());
		
							// CSV明細行取得SQL
							sql.append( "SELECT " );
							sql.append( "	 WFS.BUNRUI1_CD				AS BUNRUI1_CD " );
							sql.append( "	,WFS.BUNRUI2_CD				AS BUNRUI2_CD " );
							sql.append( "	,WFS.BUNRUI5_CD				AS BUNRUI5_CD " );
							sql.append( "	,WFS.SIIRESAKI_CD			AS SIIRESAKI_CD " );
							sql.append( "	,WFS.TORIHIKISAKI_KANJI_NA	AS TORIHIKISAKI_NA " );
							sql.append( "	,WFS.SYOHIN_CD				AS SYOHIN_CD " );
							sql.append( "	,WFS.HINMEI_KANJI_NA		AS HIMEI_NA " );
							sql.append(sql1.toString());
							sql.append( "FROM " );
							sql.append( "	WK_FUKADO_SYOHIN WFS " );
							sql.append(sql2.toString());
							sql.append( "WHERE " );
							sql.append( "	WFS.BUNRUI1_CD = '" + shiteiBunrui1Cd + "' " );
							sql.append( "ORDER BY " );
							sql.append( "	 WFS.BUNRUI1_CD " );
							sql.append( "	,WFS.BUNRUI2_CD " );
							sql.append( "	,WFS.BUNRUI5_CD " );
							sql.append( "	,WFS.SIIRESAKI_CD " );
							sql.append( "	,WFS.TORIHIKISAKI_KANJI_NA " );
							sql.append( "	,WFS.SYOHIN_CD " );
							sql.append( "	,WFS.HINMEI_KANJI_NA " );
							
							rsCsvMeisai = db.executeQuery(sql.toString());
							
							// CSVファイル作成
							this.writeLog(Level.INFO_INT, FILE_NAME + shiteiBunrui1Cd + ".csvファイルへの出力処理を開始します。");
							outPutCsvCnt = this.createCSVFile(rsCsvMeisai, strHead, shiteiBunrui1Cd, selTenpoCnt);
							
							if( outPutCsvCnt == 0 ){
								this.writeLog(Level.INFO_INT, FILE_NAME + shiteiBunrui1Cd + ".csvファイルへの出力対象データがありませんでした。");
								
								// バッチステータステーブルへ処理を更新(異常)
								updateTable_DtBatchStatusAbnormal();
								
							}else{
								this.writeLog(Level.INFO_INT, FILE_NAME + shiteiBunrui1Cd + ".csvファイルへ" + outPutCsvCnt + "件のデータを出力しました。");
							}
							
							// 現在日付取得
							nowDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );
		
							// バッチステータステーブルへ処理を更新(正常)
							updateTable_DtBatchStatusNormal();
							
							this.writeLog(Level.INFO_INT, "処理を終了します。");
						
						} else {
							this.writeLog(Level.INFO_INT, "営業店が存在しません。");
							
							// バッチステータステーブルへ処理を更新(異常)
							updateTable_DtBatchStatusAbnormal();
							
							this.writeLog(Level.INFO_INT, "処理を終了します。");
						}
					}else{
						this.writeLog(Level.INFO_INT, "一時テーブルへの登録データがありませんでした。");
						
						// バッチステータステーブルへ処理を更新(異常)
						updateTable_DtBatchStatusAbnormal();
					}
				} else {
					this.writeLog(Level.INFO_INT, "指定のDPTは存在しません。");
					
					// バッチステータステーブルへ処理を更新(異常)
					updateTable_DtBatchStatusAbnormal();

					this.writeLog(Level.INFO_INT, "処理を終了します。");
				}
			} else {
				this.writeLog(Level.INFO_INT, "DPTが指定されていません。");
				
				// バッチステータステーブルへ処理を更新(異常)
				updateTable_DtBatchStatusAbnormal();

				this.writeLog(Level.INFO_INT, "処理を終了します。");
			}
			
		}catch( Exception e ){
			db.rollback();
			
			// バッチステータステーブルへ処理を更新(異常)
			updateTable_DtBatchStatusAbnormal();
			
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
		csvFilePath = null;
		
		// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_FUKADO_SYOHIN);

    	// バッチ日付
    	batchDt = RMSTDATEUtil.getBatDateDt();
    		
    	// JobId取得
    	jobId = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.FUKADO_SYOHIN_PG_ID);

    	if(csvFilePath == null || csvFilePath.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、商品情報出力用のCSVファイルパスが取得できませんでした");
    		throw new Exception();
		}
	}

    /**
     * 画面パラメーター取得
     */
    private String getParameter() throws SQLException{
    	
    	StringBuffer sb = new StringBuffer("");
    	
		sb.append( "SELECT " );
		sb.append( "	TRIM(LAST_PARAMETER1_TX) AS LAST_PARAMETER1_TX " );
		sb.append( "FROM " );
		sb.append( "	DT_BAT_STATUS " );
		sb.append( "WHERE " );
		sb.append( "    TRIM(JOB_ID) = '" + jobId + "' " );
    	
		return sb.toString();
    }
    
    /**
     * DPT存在確認
     */
    private String getShiteiBunrui1Cd(String parameter) throws SQLException{
    	
    	StringBuffer sb = new StringBuffer("");
    	
    	sb.append( "SELECT " );
    	sb.append( "	BUNRUI1_CD " );
    	sb.append( "FROM R_SYOHIN " );
    	sb.append( "WHERE BUNRUI1_CD = '" + parameter + "' " );
    	sb.append( "GROUP BY BUNRUI1_CD " );
    	sb.append( "HAVING COUNT(*) > 0 " );
    	
		return sb.toString();
    }

    /**
     * 単品店取扱一時テーブル取得
     */
    private String getInsTmpTanpinten(String shiteiBunrui1Cd) throws SQLException {
    	
    	StringBuffer sb = new StringBuffer("");
    	
	    sb.append( "INSERT INTO " );
	    sb.append( "	TMP_TANPINTEN_DPT " );
	    sb.append( "SELECT * " );
	    sb.append( "FROM R_TANPINTEN_TORIATUKAI " );
	    sb.append( "WHERE BUNRUI1_CD = '" + shiteiBunrui1Cd + "' " );
	    
    	return sb.toString();
    }
    
    /**
     * 全営業店取得
     */
    private String getTenpoData() throws SQLException {
    	
    	StringBuffer sb = new StringBuffer("");
    	
    	sb.append( "SELECT " );
    	sb.append( "	 TENPO_CD " );
    	sb.append( "	,KANJI_NA " );
    	sb.append( "FROM R_TENPO " );
    	sb.append( "WHERE TENPO_KB = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' " );
    	sb.append( "AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() +  "' " );
    	sb.append( "AND KAITEN_DT <= '" + batchDt + "' " );
    	sb.append( "AND HEITEN_DT >= '" + batchDt + "' " );
    	sb.append( "ORDER BY " );
    	sb.append( "	TENPO_CD " );

    	return sb.toString();
    }
    
	/**
	 * CSVファイルを作成します。
	 * 
	 * @param	String			SQL文字列
	 * @throws	IOException
	 * @throws	SQLException
	 * @throws	Exception
	 */
	private long createCSVFile(ResultSet rs, StringBuffer str, String shiteiBunrui1Cd, int selTenpoCnt) throws IOException, SQLException, Exception{

		String			fileName	= null;
		File			file 		= null;
		FileWriter		fw 			= null;
		BufferedWriter	bw 			= null;
		StringBuffer	sb			= new StringBuffer();
		long			lngOPCnt	= 0;
		
		try{
					
			// 情報分析用CSVファイル格納パス、ファイル名
			file 	 = new File(csvFilePath);
			fileName = new File(csvFilePath) + "/" + FILE_NAME + shiteiBunrui1Cd + "_" + systemDt + ".csv";
	
			if( file.exists() == false ){
				// ディレクトリが見つからなければ
	    		this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}
	
			// ファイルオープン
			fw = new FileWriter( fileName, true );
			bw = new BufferedWriter( fw );
			
			// ヘッダ行データ作成
			sb.append( str );
			
			// ヘッダ行データ出力
			bw.write(sb.toString());	
			sb.setLength(0);
			
			while( rs.next() ){
	
				// 明細行データ作成
				sb.append( this.createRowData(rs, selTenpoCnt) );
				
				// 行データ出力
				bw.write(sb.toString());	
				sb.setLength(0);
	
				lngOPCnt++;
			}
	
			// 最終ファイル
			if( !rs.next() ){
				// ファイルクローズ
				if( bw != null ){
					bw.close();
				}
				if( fw != null ){
					fw.close();
				} 			           
			}
	
			if( closeDb ){
				if( rs != null ){
					db.closeResultSet(rs);
				}
				if( db != null ){
					this.db.close();
				}
			}

		} catch( Exception e ) {
			// ファイルクローズ
			if( bw != null ){
				bw.close();
			}
			if( fw != null ){
				fw.close();
			}
			
			// バッチステータステーブルへ処理を更新(異常)
			updateTable_DtBatchStatusAbnormal();
			
			throw e;
		}
			
		return lngOPCnt;
	}
	
	/**
	 * CSVファイルへ出力する行データを作成する
	 * 
	 * @param		ResultSet       取得データ
	 * @return		StringBuffer	１行分の文字列
	 * @throws		SQLException
	 * @throws		Exception
	 */
	private StringBuffer createRowData(ResultSet rs, int selTenpoCnt) throws SQLException, Exception{
		
		StringBuffer sb = new StringBuffer();
		
		sb.append( RETURN_CODE );
		
		// DPTコード
		if( rs.getString("BUNRUI1_CD") != null ){
			sb.append( rs.getString("BUNRUI1_CD").trim() );
		}
		
		// ラインコード
		sb.append( SPLIT_CODE );
		if( rs.getString("BUNRUI2_CD") != null ){
			sb.append( rs.getString("BUNRUI2_CD").trim() );
		}

		// クラスコード
		sb.append( SPLIT_CODE );
		if( rs.getString("BUNRUI5_CD") != null  ){
			sb.append( rs.getString("BUNRUI5_CD").trim() );
		}

		// 取引先コード
		sb.append( SPLIT_CODE );
		if( rs.getString("SIIRESAKI_CD") != null  ){
			sb.append( rs.getString("SIIRESAKI_CD").trim() );
		}

		// 取引先名称
		sb.append( SPLIT_CODE );
		if( rs.getString("TORIHIKISAKI_NA") != null  ){
			sb.append( rs.getString("TORIHIKISAKI_NA").trim() );
		}

		// 商品コード
		sb.append( SPLIT_CODE );
		if( rs.getString("SYOHIN_CD") != null  ){
			sb.append( rs.getString("SYOHIN_CD").trim() );
		}

		// 商品名称
		sb.append( SPLIT_CODE );
		if( rs.getString("HIMEI_NA") != null  ){
			sb.append( rs.getString("HIMEI_NA").trim() );
		}
	
		// 店舗数分繰り返し
		for(int i = 0; i < selTenpoCnt; i++){
			int tenCnt = i + 1;
			
			// 店舗情報（店舗コードと名称）
			sb.append( SPLIT_CODE );
			if( rs.getString("TENPO_DATA"+ tenCnt) != null  ){
				sb.append( rs.getString("TENPO_DATA"+ tenCnt).trim() );
			} else {
				sb.append( " " );
			}
		}

		return sb;
	}

    /**
     * バッチステータス更新異常終了
     */
    private void updateTable_DtBatchStatusAbnormal() throws Exception {

        try {
        	
        	StringBuffer sb = new StringBuffer("");
        	
        	sb.append( "UPDATE " );
        	sb.append( "    DT_BAT_STATUS " );
        	sb.append( "SET " );
        	sb.append( "     STATUS_KB = '" + mst000101_ConstDictionary.BAT_STATUS_KB_ERR + "' " );
        	sb.append( "    ,UPDATE_USER_ID = '" + BATCH_ID + "' " );
        	sb.append( "    ,UPDATE_TS = '" + systemDt + "' " );
        	sb.append( "WHERE " );
        	sb.append( "    TRIM(JOB_ID) = '" + jobId + "' " );
        	
            db.executeUpdate(sb.toString());

        } finally {
            if (db != null) {
            	db.close();
            }
        }
    }

    /**
     * バッチステータス更新（正常終了）
     */
    private void updateTable_DtBatchStatusNormal() throws Exception {

        try {
        	
        	StringBuffer sb = new StringBuffer("");
        	
        	sb.append( "UPDATE " );
        	sb.append( "    DT_BAT_STATUS " );
        	sb.append( "SET " );
        	sb.append( "     STATUS_KB = '" + mst000101_ConstDictionary.BAT_STATUS_KB_KANRYO + "' " );
        	sb.append( "    ,LAST_FINISH_TS = '" + nowDt + "' " );
        	sb.append( "    ,UPDATE_USER_ID = '" + BATCH_ID + "' " );
        	sb.append( "    ,UPDATE_TS = '" + nowDt + "' " );
        	sb.append( "WHERE " );
        	sb.append( "    TRIM(JOB_ID) = '" + jobId + "' " );

            db.executeUpdate(sb.toString());

        } finally {
            if (db != null) {
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
