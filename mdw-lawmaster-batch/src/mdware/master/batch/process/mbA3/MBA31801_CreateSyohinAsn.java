package mdware.master.batch.process.mbA3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:MBA31801_CreateSyohinAsn.java</p>
 * <p>説明:著作権: 情報分析用商品マスタASN作成処理</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @Version 1.00  (2015.08.31) THE.VV FIVIMART対応
 * @Version 1.01  (2016.01.27) M.KANNO カンマデータ対応
 * @Version 1.02  (2023.09.06) DUY.HM #18109 MKH対応
 */

public class MBA31801_CreateSyohinAsn  {

	/** バッチ日付 */
	protected String batchDt = null;

	// CSVファイル名（店別商品データ）
	private static final String FILE_NAME		= "IF_R_SYOHIN_ASN.csv";
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

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA31801_CreateSyohinAsn( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA31801_CreateSyohinAsn() {

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

			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();
			this.writeLog(Level.INFO_INT, "バッチ日付" + batchDt );

			// システムコントロール情報取得
			this.getSystemControl();
			this.writeLog(Level.INFO_INT, "CSVファイルパス取得処理" + csvFilePath);

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
	 * メッセージマスタ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		sb.append("SELECT ");
		sb.append("    TRSA1.BUNRUI1_CD  			AS BUNRUI1_CD ");
		sb.append("    ,TRSA1.SYOHIN_CD  			AS SYOHIN_CD ");
		sb.append("    ,TRSA1.YUKO_DT				AS YUKO_DT ");
		//sb.append("    ,TRSA1.SYOHIN_EIJI_NA    	AS SYOHIN_EIJI_NA ");
		sb.append("    ,'\"' || TRSA1.SYOHIN_EIJI_NA  || '\"'  	AS SYOHIN_EIJI_NA ");
		sb.append("    ,TRSA1.COUNTRY_CD    		AS COUNTRY_CD ");
		sb.append("    ,TRSA1.MAKER_CD    			AS MAKER_CD ");
		sb.append("    ,TRSA1.MIN_ZAIKO_QT    		AS MIN_ZAIKO_QT ");
		sb.append("    ,TRSA1.SECURITY_TAG_FG    	AS SECURITY_TAG_FG ");
		sb.append("    ,TRSA1.MEMBER_DISCOUNT_FG    AS MEMBER_DISCOUNT_FG ");
		sb.append("    ,TRSA1.HAMPER_SYOHIN_FG		AS HAMPER_SYOHIN_FG ");
		sb.append("    ,TRSA1.INSERT_TS    			AS INSERT_TS ");
		sb.append("    ,TRSA1.INSERT_USER_ID    	AS INSERT_USER_ID ");
		sb.append("    ,TRSA1.UPDATE_TS    			AS UPDATE_TS ");
		sb.append("    ,TRSA1.UPDATE_USER_ID    	AS UPDATE_USER_ID ");
		sb.append("    ,TRSA1.DELETE_FG    			AS DELETE_FG ");
		sb.append("    ,'" + batchDt + "' 			AS BATCH_DT ");
		sb.append("FROM ");
		sb.append("    TMP_R_SYOHIN_ASN TRSA1 ");
		// #18109 DEL 2023.09.06 DUY.HM (S)
		// sb.append("WHERE " );
		// sb.append("    TRSA1.YUKO_DT = (  " );
		// sb.append("    SELECT " );
		// sb.append("     	MAX(TRSA2.YUKO_DT) " );
		// sb.append("    FROM  " );
		// sb.append("    	TMP_R_SYOHIN_ASN TRSA2 " );
		// sb.append("    WHERE ( " );
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sb.append("    TRSA2.BUNRUI1_CD = TRSA1.BUNRUI1_CD " );
//		sb.append("    AND TRSA2.SYOHIN_CD = TRSA1.SYOHIN_CD " );
		// sb.append("     TRSA2.SYOHIN_CD = TRSA1.SYOHIN_CD " );
		// #6620 MOD 2022.11.18 VU.TD (E)
		// sb.append("    AND TRSA2.YUKO_DT <= '" + batchDt + "' " );
		// sb.append("    )) " );
		// sb.append("OR " );
		// sb.append("    TRSA1.YUKO_DT > '" + batchDt + "' " );
		// #18109 DEL 2023.09.06 DUY.HM (E)
		// #18109 ADD 2023.09.06 DUY.HM (S)
		sb.append(" LEFT JOIN ");
		sb.append(" 	(SELECT ");
		sb.append(" 		TRSA2.SYOHIN_CD, ");
		sb.append(" 		MAX(TRSA2.YUKO_DT) AS YUKO_DT ");
		sb.append(" 	FROM ");
		sb.append(" 		TMP_R_SYOHIN_ASN TRSA2 ");
		sb.append(" 	WHERE ");
		sb.append(" 		TRSA2.YUKO_DT <= '" + batchDt + "' " );
		sb.append(" 		GROUP BY TRSA2.SYOHIN_CD) SUB ");
		sb.append(" ON SUB.SYOHIN_CD = TRSA1.SYOHIN_CD ");
		sb.append(" WHERE ");
		sb.append(" 	TRSA1.YUKO_DT > '" + batchDt + "' " );
		sb.append(" 	OR TRSA1.YUKO_DT = SUB.YUKO_DT ");
		// #18109 ADD 2023.09.06 DUY.HM (E)

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
		FileWriter		fw 			= null;
		BufferedWriter	bw 			= null;
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

			// データ取得
			rs = db.executeQuery(selSql);

			while( rs.next() ){

				if( (fw == null) && (bw == null) ){
					// ファイルオープン
					fw = new FileWriter( fileName, true );
					bw = new BufferedWriter( fw );
				}

				// 行データ作成
				sb.append( this.createRowData(rs) );

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

		// 分類１コード
		if( rs.getString("BUNRUI1_CD") != null ){
			sb.append( rs.getString("BUNRUI1_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品コード
		if( rs.getString("SYOHIN_CD") != null  ){
			sb.append( rs.getString("SYOHIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 有効日
		if( rs.getString("YUKO_DT") != null  ){
			sb.append( rs.getString("YUKO_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品名（英字）
		if( rs.getString("SYOHIN_EIJI_NA") != null  ){
			sb.append( rs.getString("SYOHIN_EIJI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 国コード
		if( rs.getString("COUNTRY_CD") != null  ){
			sb.append( rs.getString("COUNTRY_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// メーカーコード
		if( rs.getString("MAKER_CD") != null  ){
			sb.append( rs.getString("MAKER_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 最低在庫数量
		if( rs.getString("MIN_ZAIKO_QT") != null  ){
			sb.append( rs.getString("MIN_ZAIKO_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// セキュリティタグフラグ
		if( rs.getString("SECURITY_TAG_FG") != null  ){
			sb.append( rs.getString("SECURITY_TAG_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// メンバーディ割引対象外商品フラグ
		if( rs.getString("MEMBER_DISCOUNT_FG") != null  ){
			sb.append( rs.getString("MEMBER_DISCOUNT_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// ハンパー商品フラグ
		if( rs.getString("HAMPER_SYOHIN_FG") != null  ){
			sb.append( rs.getString("HAMPER_SYOHIN_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// 作成年月日
		if( rs.getString("INSERT_TS") != null  ){
			sb.append( rs.getString("INSERT_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// 作成者ID
		if( rs.getString("INSERT_USER_ID") != null  ){
			sb.append( rs.getString("INSERT_USER_ID").trim() );
		}
		sb.append( SPLIT_CODE );

		// 更新年月日
		if( rs.getString("UPDATE_TS") != null  ){
			sb.append( rs.getString("UPDATE_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// 更新者ID
		if( rs.getString("UPDATE_USER_ID") != null  ){
			sb.append( rs.getString("UPDATE_USER_ID").trim() );
		}
		sb.append( SPLIT_CODE );

		// 削除フラグ
		if( rs.getString("DELETE_FG") != null  ){
			sb.append( rs.getString("DELETE_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// システム日時
		if( rs.getString("BATCH_DT") != null  ){
			sb.append( rs.getString("BATCH_DT").trim() );
		}

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
