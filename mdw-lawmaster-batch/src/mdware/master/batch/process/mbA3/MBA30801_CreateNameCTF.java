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
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:情報分析用名称マスタ生成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author SIC A.Okada
 * @version 1.00 2015/10/16 Sou OHPA用システムコントロールデータ修正
 * @version 1.01 2015/10/23 kou カンマCSV出力対応
 */

public class MBA30801_CreateNameCTF {

	// CSVファイル名（名称マスタ）
	private static final String FILE_NAME		= "IF_R_NAMECTF.csv";
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

	// CSVファイルパス
	private String 		csvFilePath = null;
	// システム日付
	private String 		systemDt 	= null;


	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA30801_CreateNameCTF( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA30801_CreateNameCTF() {

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
		csvFilePath = null;

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
	 * 名称マスタ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		sb.append("SELECT ");
		sb.append("    TRIM(SYUBETU_NO_CD) AS SYUBETU_NO_CD, ");
		sb.append("    TRIM(CODE_CD)       AS CODE_CD, ");
		// 2015/10/23 kou OHPA用名称マスタデータ修正 Start
		//sb.append("    TRIM(KANJI_NA)      AS KANJI_NA, ");
		sb.append(" '\"' ||    TRIM(KANJI_NA) || '\"'      AS KANJI_NA, ");
		// 2015/10/23 kou OHPA用名称マスタデータ修正 End
		// 2015/10/23 kou OHPA用名称マスタデータ修正 Start
		//sb.append("    TRIM(KANA_NA)       AS KANA_NA, ");
		sb.append(" '\"' ||   TRIM(KANA_NA)  || '\"'     AS KANA_NA, ");
		// 2015/10/23 kou OHPA用名称マスタデータ修正 End
		// 2015/10/23 kou OHPA用名称マスタデータ修正 Start
		//sb.append("    TRIM(KANJI_RN)      AS KANJI_RN, ");
		sb.append(" '\"' ||   TRIM(KANJI_RN)  || '\"'    AS KANJI_RN, ");
		// 2015/10/23 kou OHPA用名称マスタデータ修正 End
		// 2015/10/23 kou OHPA用名称マスタデータ修正 Start
		//sb.append("    TRIM(KANA_RN)       AS KANA_RN, ");
		sb.append(" '\"' ||  TRIM(KANA_RN)    || '\"'     AS KANA_RN, ");
		// 2015/10/23 kou OHPA用名称マスタデータ修正 End
		sb.append("    TRIM(ZOKUSEI_CD)    AS ZOKUSEI_CD ");
		sb.append("FROM ");
		sb.append("    TMP_R_NAMECTF ");
		sb.append("WHERE ");
		sb.append("    DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("ORDER BY " );
		sb.append("    SYUBETU_NO_CD, " );
		sb.append("    CODE_CD " );

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

		// 種別NO
		if( rs.getString("SYUBETU_NO_CD") != null ){
			sb.append( rs.getString("SYUBETU_NO_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// コード
		if( rs.getString("CODE_CD") != null  ){
			sb.append( rs.getString("CODE_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 正式名称（漢字）
		if( rs.getString("KANJI_NA") != null ){
			sb.append( rs.getString("KANJI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 正式名称（カナ）
		if( rs.getString("KANA_NA") != null ){
			sb.append( rs.getString("KANA_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 略式名称（漢字）
		if( rs.getString("KANJI_RN") != null ){
			sb.append( rs.getString("KANJI_RN").trim() );
		}
		sb.append( SPLIT_CODE );

		// 略式名称（カナ）
		if( rs.getString("KANA_RN") != null ){
			sb.append( rs.getString("KANA_RN").trim() );
		}
		sb.append( SPLIT_CODE );

		// 属性コード
		if( rs.getString("ZOKUSEI_CD") != null ){
			sb.append( rs.getString("ZOKUSEI_CD").trim() );
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
