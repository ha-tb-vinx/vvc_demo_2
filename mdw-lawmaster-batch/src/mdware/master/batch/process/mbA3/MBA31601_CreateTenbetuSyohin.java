package mdware.master.batch.process.mbA3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.common.util.MoneyUtil;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst010501_NaibuIFFgDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:情報分析用店別商品マスタ生成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author  VINX
 * @version 1.0 2014/09/11 Minh-NV 海外LAWSON様通貨対応
 * @version 1.01 2015/10/16 Sou OHPA用システムコントロールデータ修正
 */

public class MBA31601_CreateTenbetuSyohin {

	// CSVファイル名（店別商品データ）
	private static final String FILE_NAME		= "IF_DT_TENBETU_SYOHIN.csv";
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

	// 送信対象の店舗区分
	private String sendIfTenpoKb = null;


	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA31601_CreateTenbetuSyohin( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA31601_CreateTenbetuSyohin() {

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
    	// 各種初期値セット
		Map tenpoMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_IF_JOHO_TENPO_KB);
		sendIfTenpoKb = SqlSupportUtil.createInString(tenpoMap.keySet().toArray());

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
	 * 店別商品データ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		sb.append("SELECT ");
		sb.append("    TRIM(TENPO_CD)      AS TENPO_CD, ");
		sb.append("    TRIM(BUNRUI1_CD)    AS BUNRUI1_CD, ");
		sb.append("    TRIM(SYOHIN_CD)     AS SYOHIN_CD, ");
		sb.append("    TRIM(YUKO_DT)       AS YUKO_DT, ");
		sb.append("    TRIM(SIIRESAKI_CD)  AS SIIRESAKI_CD, ");
		sb.append("    TRIM(GENTANKA_VL)   AS GENTANKA_VL, ");
		sb.append("    TRIM(BAITANKA_VL)   AS BAITANKA_VL, ");
		sb.append("    TRIM(BUTURYU_KB)    AS BUTURYU_KB, ");
		sb.append("    TRIM(EOS_KB)        AS EOS_KB, ");
		sb.append("    TRIM(NON_ACT_KB)    AS NON_ACT_KB ");
		sb.append("FROM ");
		sb.append("    DT_TENBETU_SYOHIN ");
		sb.append("WHERE ");
		sb.append("    NAIBU_IF_FG = '" ).append( mst010501_NaibuIFFgDictionary.TAISYO.getCode() ).append( "' " );
		sb.append("AND SYOHIN_DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("AND TENPO_KB  IN (" + sendIfTenpoKb + ") ");
		sb.append("ORDER BY " );
		sb.append("    TENPO_CD, " );
		sb.append("    BUNRUI1_CD, " );
		sb.append("    SYOHIN_CD, " );
		sb.append("    YUKO_DT " );

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
		if( rs.getString("BUNRUI1_CD") != null  ){
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

		// 仕入先コード
		if( rs.getString("SIIRESAKI_CD") != null  ){
			sb.append( rs.getString("SIIRESAKI_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 原単価
		if( rs.getString("GENTANKA_VL") != null  ){
			sb.append(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(rs.getString("GENTANKA_VL").trim())));
		}
		sb.append( SPLIT_CODE );

		// 売単価
		if( rs.getString("BAITANKA_VL") != null  ){
			sb.append( MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(rs.getString("BAITANKA_VL").trim())));
		}
		sb.append( SPLIT_CODE );

		// 物流区分
		if( rs.getString("BUTURYU_KB") != null ){
			sb.append( rs.getString("BUTURYU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// EOS区分
		if( rs.getString("EOS_KB") != null ){
			sb.append( rs.getString("EOS_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// NON-ACT区分
		if( rs.getString("NON_ACT_KB") != null ){
			sb.append( rs.getString("NON_ACT_KB").trim() );
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
