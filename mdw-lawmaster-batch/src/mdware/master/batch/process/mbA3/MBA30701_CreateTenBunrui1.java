package mdware.master.batch.process.mbA3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:情報分析用店分類１マスタ生成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author SIC A.Okada
 * @version 1.00 2015/10/16 Sou OHPA用システムコントロールデータ修正
 */

public class MBA30701_CreateTenBunrui1 {

	// CSVファイル名（店分類１マスタ）
	private static final String FILE_NAME		= "IF_R_TENPO_BUNRUI1.csv";
	// 区切り文字（カンマ区切り）
	private static final String SPLIT_CODE	= ",";
	// 改行文字
	private static final String RETURN_CODE	= System.getProperty("line.separator");

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// バッチ日付
	private String 		batchDt 	= null;
	// CSVファイルパス
	private String 		csvFilePath = null;
	// システム日付
	private String 		systemDt 	= null;

	// 処理当日から基準日までの間隔
	private static final int SYORI_SPAN = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA30701_CreateTenBunrui1( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA30701_CreateTenBunrui1() {

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
		long lngOPCnt = 0;

		try {

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			this.writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();

			// CSVファイル削除
			this.writeLog(Level.INFO_INT, "既存ファイルの削除処理を開始します。");
			this.deleteCSVFile();
			this.writeLog(Level.INFO_INT, "既存ファイルの削除処理を終了します。");


			// CSVファイル作成
			this.writeLog(Level.INFO_INT, FILE_NAME + "の出力処理を開始します。");
			lngOPCnt = this.createCSVFile( this.getSelDataSQL() );
			if( lngOPCnt == 0 ){
				this.writeLog(Level.INFO_INT, FILE_NAME + "への出力対象データがありませんでした。");
			}else{
				this.writeLog(Level.INFO_INT, FILE_NAME + "へ" + lngOPCnt + "件のデータを出力しました。");
			}

			this.writeLog(Level.INFO_INT, "処理を終了します。");

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
		csvFilePath = null;

    	// バッチ日付
		batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

    	if(batchDt == null || batchDt.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
    		throw new Exception();
		}

    	// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.CSVPATH_JOHO);

    	if(csvFilePath == null || csvFilePath.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、情報分析用のCSVファイルパスが取得できませんでした");
    		throw new Exception();
		}
	}

	/**
	 * CSVファイル削除処理
	 * @return int
	 * @throws IOException
	 */
	private void deleteCSVFile() throws IOException, Exception{

		String	fileName	= null;
		File 	file 		= null;

		// 情報分析用CSVファイル格納パス、ファイル名
		file 	 = new File(csvFilePath);
		fileName = new File(csvFilePath) + "/" + FILE_NAME;

		if( file.exists() == false ){
			//　削除ディレクトリが見つからなければ
    		this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
			throw new Exception();
		} else {
			// 削除
			new File( fileName ).delete();
    		this.writeLog(Level.INFO_INT, FILE_NAME + " の削除処理を実施しました。");
		}
	}

	/**
	 * 店分類１マスタ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		sb.append("SELECT ");
		sb.append("    TRIM(TB1.TENPO_CD)            AS TENPO_CD, ");
		sb.append("    TRIM(TB1.BUNRUI1_CD)          AS BUNRUI1_CD, ");
		sb.append("    TRIM(TB1.YUKO_DT)             AS YUKO_DT, ");
		sb.append("    TRIM(TB1.TORIATSUKAI_KB)      AS TORIATUKAI_KB, ");
		sb.append("    TRIM(TB1.URIBA_AREA_QT)       AS URIBA_AREA_QT, ");
		sb.append("    TRIM(TB1.SAGYOUBA_AREA_QT)    AS SAGYOUBA_AREA_QT, ");
		sb.append("    TRIM(TB1.TANAOROSHI_GENKA_KB) AS TANAOROSHI_GENKA_KB, ");
		sb.append("    TRIM(TB1.TANAOROSHI_SYUKI_KB) AS TANAOROSHI_SYUKI_KB, ");
		sb.append("    TRIM(TB1.DENSHI_TANAFUDA_KB)  AS DENSHI_TANAFUDA_KB, ");
		sb.append("    TRIM(TB1.HIKIATE_LOSS_RATE)   AS HIKIATE_LOSS_RATE, ");
		sb.append("    TRIM(TB1.MAX_TANAOROSHI_QT)   AS MAX_TANAOROSHI_QT, ");
		sb.append("    TRIM(TB1.KIJUN_SAI_RT)        AS KIJUN_SAI_RT, ");
		sb.append("    TRIM(TB1.KIJUN_SAI_QT)        AS KIJUN_SAI_QT, ");
		sb.append("    TRIM(TB1.TANAWARI_USE_KB)     AS TANAWARI_USE_KB, ");
		sb.append("    TRIM(TB1.KAISO_DT)            AS KAISO_DT ");
		sb.append("FROM ");
		sb.append("    TMP_R_TENPO_BUNRUI1 TB1 ");
		sb.append("INNER JOIN (");
		sb.append("               SELECT ");
		sb.append("                   TENPO_CD, ");
		sb.append("                   BUNRUI1_CD, ");
		sb.append("                   MAX( YUKO_DT ) AS YUKO_DT ");
		sb.append("               FROM ");
		sb.append("                   TMP_R_TENPO_BUNRUI1 ");
		sb.append("               WHERE ");
		sb.append("                   YUKO_DT <= '").append( DateChanger.addDate(batchDt, SYORI_SPAN) ).append("' ");
		sb.append("               GROUP BY ");
		sb.append("                   TENPO_CD, ");
		sb.append("                   BUNRUI1_CD ");
		sb.append("            ) TB2 ");
		sb.append("    ON  TB1.TENPO_CD = TB2.TENPO_CD ");
		sb.append("    AND TB1.BUNRUI1_CD = TB2.BUNRUI1_CD ");
		sb.append("    AND TB1.YUKO_DT = TB2.YUKO_DT ");
		sb.append("    AND TB1.DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );

		sb.append("UNION ALL ");

		sb.append("SELECT ");
		sb.append("    TRIM(TB3.TENPO_CD)            AS TENPO_CD, ");
		sb.append("    TRIM(TB3.BUNRUI1_CD)          AS BUNRUI1_CD, ");
		sb.append("    TRIM(TB3.YUKO_DT)             AS YUKO_DT, ");
		sb.append("    TRIM(TB3.TORIATSUKAI_KB)      AS TORIATUKAI_KB, ");
		sb.append("    TRIM(TB3.URIBA_AREA_QT)       AS URIBA_AREA_QT, ");
		sb.append("    TRIM(TB3.SAGYOUBA_AREA_QT)    AS SAGYOUBA_AREA_QT, ");
		sb.append("    TRIM(TB3.TANAOROSHI_GENKA_KB) AS TANAOROSHI_GENKA_KB, ");
		sb.append("    TRIM(TB3.TANAOROSHI_SYUKI_KB) AS TANAOROSHI_SYUKI_KB, ");
		sb.append("    TRIM(TB3.DENSHI_TANAFUDA_KB)  AS DENSHI_TANAFUDA_KB, ");
		sb.append("    TRIM(TB3.HIKIATE_LOSS_RATE)   AS HIKIATE_LOSS_RATE, ");
		sb.append("    TRIM(TB3.MAX_TANAOROSHI_QT)   AS MAX_TANAOROSHI_QT, ");
		sb.append("    TRIM(TB3.KIJUN_SAI_RT)        AS KIJUN_SAI_RT, ");
		sb.append("    TRIM(TB3.KIJUN_SAI_QT)        AS KIJUN_SAI_QT, ");
		sb.append("    TRIM(TB3.TANAWARI_USE_KB)     AS TANAWARI_USE_KB, ");
		sb.append("    TRIM(TB3.KAISO_DT)            AS KAISO_DT ");
		sb.append("FROM ");
		sb.append("    TMP_R_TENPO_BUNRUI1 TB3 ");
		sb.append("WHERE ");
		sb.append("    TB3.YUKO_DT > '").append( DateChanger.addDate(batchDt, SYORI_SPAN) ).append("' ");
		sb.append("AND TB3.DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("ORDER BY ");
		sb.append("    TENPO_CD, ");
		sb.append("    BUNRUI1_CD, ");
		sb.append("    YUKO_DT ");

		return( sb.toString() );

	}

	/**
	 * CSVファイルを作成します。
	 *
	 * @param	String			SQL文字列
	 * @throws	IOException
	 * @throws	SQLException
	 * @throws	Exception
	 */
	private long createCSVFile(String selSql) throws IOException, SQLException, Exception{

		ResultSet		rs			= null;
		String			fileName	= null;
		File			file 		= null;
		// 2015/10/16 Sou エンコーディング指定 Start
		//FileWriter		fw 			= null;
		//BufferedWriter	bw 			= null;
		OutputStreamWriter osw = null;
		// 2015/10/16 Sou エンコーディング指定 End
		StringBuffer	sb			= new StringBuffer();
		long			lngOPCnt	= 0;

		try{
			// 情報分析用CSVファイル格納パス、ファイル名
			file 	 = new File(csvFilePath);
			fileName = new File(csvFilePath) + "/" + FILE_NAME;

			if( file.exists() == false ){
				// ディレクトリが見つからなければ
	    		this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			// システム日付取得
			systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );

			// データ取得
			rs = db.executeQuery(selSql);

			while( rs.next() ){

				// 2015/10/16 Sou エンコーディング指定 Start
				/*if( (fw == null) && (bw == null) ){
					// ファイルオープン
					fw = new FileWriter( fileName, true );
					bw = new BufferedWriter( fw );
				}*/

				if(osw == null){
					 osw = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");
				}
				// 2015/10/16 Sou エンコーディング指定 End

				// 行データ作成
				sb.append( this.createRowData(rs) );

				// 行データ出力
				// 2015/10/16 Sou エンコーディング指定 Start
				//bw.write(sb.toString());
				osw.write(sb.toString());
				// 2015/10/16 Sou エンコーディング指定 End
				sb.setLength(0);

				lngOPCnt++;
			}

			// 最終ファイル
			if( !rs.next() ){
				// 2015/10/16 Sou エンコーディング指定 Start
				// ファイルクローズ
				/*if( bw != null ){
					bw.close();
				}
				if( fw != null ){
					fw.close();
				} */
				if( osw != null ){
					osw.close();
				}
				// 2015/10/16 Sou エンコーディング指定 End
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
			// 2015/10/16 Sou エンコーディング指定 Start
			/*if( bw != null ){
				bw.close();
			}
			if( fw != null ){
				fw.close();
			}*/
			if( osw != null ){
				osw.close();
			}
			// 2015/10/16 Sou エンコーディング指定 End
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
	private StringBuffer createRowData(ResultSet rs) throws SQLException, Exception{

		StringBuffer sb = new StringBuffer();

		// 店舗コード
		if( rs.getString("TENPO_CD") != null ){
			sb.append( rs.getString("TENPO_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 分類１コード
		if( rs.getString("BUNRUI1_CD") != null ){
			sb.append( rs.getString("BUNRUI1_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 有効日
		if( rs.getString("YUKO_DT") != null ){
			sb.append( rs.getString("YUKO_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 取扱区分
		if( rs.getString("TORIATUKAI_KB") != null ){
			sb.append( rs.getString("TORIATUKAI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 売場面積（坪）
		if( rs.getString("URIBA_AREA_QT") != null  ){
			sb.append( rs.getString("URIBA_AREA_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 作業場面積（坪）
		if( rs.getString("SAGYOUBA_AREA_QT") != null ){
			sb.append( rs.getString("SAGYOUBA_AREA_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 棚卸原価計算区分
		if( rs.getString("TANAOROSHI_GENKA_KB") != null ){
			sb.append( rs.getString("TANAOROSHI_GENKA_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 棚卸周期区分
		if( rs.getString("TANAOROSHI_SYUKI_KB") != null ){
			sb.append( rs.getString("TANAOROSHI_SYUKI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 電子棚札区分
		if( rs.getString("DENSHI_TANAFUDA_KB") != null ){
			sb.append( rs.getString("DENSHI_TANAFUDA_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 引当ロス率
		if( rs.getString("HIKIATE_LOSS_RATE") != null ){
			sb.append( rs.getString("HIKIATE_LOSS_RATE").trim() );
		}
		sb.append( SPLIT_CODE );

		// 最大棚卸数
		if( rs.getString("MAX_TANAOROSHI_QT") != null ){
			sb.append( rs.getString("MAX_TANAOROSHI_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 基準差異率
		if( rs.getString("KIJUN_SAI_RT") != null ){
			sb.append( rs.getString("KIJUN_SAI_RT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 基準差異数
		if( rs.getString("KIJUN_SAI_QT") != null ){
			sb.append( rs.getString("KIJUN_SAI_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 棚割使用区分
		if( rs.getString("TANAWARI_USE_KB") != null ){
			sb.append( rs.getString("TANAWARI_USE_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 改装年月日
		if( rs.getString("KAISO_DT") != null ){
			sb.append( rs.getString("KAISO_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// IF作成日時
		sb.append( systemDt );
		sb.append( RETURN_CODE );

		return sb;
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
