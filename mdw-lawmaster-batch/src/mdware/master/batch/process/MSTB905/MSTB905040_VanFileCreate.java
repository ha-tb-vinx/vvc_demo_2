package mdware.master.batch.process.MSTB905;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB905040_VanFileCreate.java クラス</p>
 * <p>説明　: VAN商品マスタを作成する。</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.20) T.Ooshiro [CUS00102] レクサスEDIインターフェイス仕様変更対応（マスタ）対応
 * @version 3.01 (2015.06.15) Thao.NTL 海外LAWSON様対応 英文化対応
 */
public class MSTB905040_VanFileCreate {

	// 区切り文字（区切りなし）
	private static final String SPLIT_CODE	= "";
	// 改行文字
	private static final String RETURN_CODE	= System.getProperty("line.separator");

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	/** CSVファイルパス */
	private String csvFilePath = null;
	/** 商品マスタ（VAN）データファイル名 */
	private String vanTcpIpSyohin = null;

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** パディング文字 スペース */
	private static final String PADDING_STR_BLANK = " ";

	/** 正式名称：桁数 */
	private static final String REC_HINMEI_KANJI_NA_LENGTH = "32";
	/** 規格：桁数 */
	private static final String KIKAKU_KANJI_NA_LENGTH = "18";
	/** 名称カナ：桁数 */
	private static final String HINMEI_KANA_NA_LENGTH = "25";
	/** FILLER：桁数 */
	private static final String FILLER_LENGTH = "10";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB905040_VanFileCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB905040_VanFileCreate() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try {

			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール項目取得
			getSystemControl();

			writeLog(Level.INFO_INT, "出力先ディレクトリ：" + csvFilePath);

			// 商品マスタ（VAN）データファイル作成
			writeLog(Level.INFO_INT, "商品マスタ（VAN）データファイル（" + vanTcpIpSyohin + "）作成処理を開始します。");
			iRec = createCSVFile(vanTcpIpSyohin, getIfVanSyohinSelectSql());
			writeLog(Level.INFO_INT, "商品マスタ（VAN）データファイルを" + iRec + "件作成しました。");

			writeLog(Level.INFO_INT, "処理を終了します。");

			//SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

			//その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

			//SQL終了処理
		} finally {
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

		// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_VAN);
		if(csvFilePath == null || csvFilePath.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}

		// 商品マスタ（VAN）データファイル名取得
		vanTcpIpSyohin = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.VAN_TCP_IP_SYOHIN);
		if(vanTcpIpSyohin == null || vanTcpIpSyohin.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタ（VAN）データファイル名が取得できませんでした");
			throw new Exception();
		}

	}

	/**
	 * CSVファイルを作成します。
	 * @param fileName		ファイル名
	 * @param sqlStatement	検索SQL
	 * @return 出力件数
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	private int createCSVFile(String fileName, String sqlStatement) throws IOException, SQLException, Exception {

		ResultSet		rs				= null;
		String			fileFullName	= null;
		File			file 			= null;
		FileWriter		fw 				= null;
		BufferedWriter	bw 				= null;
		StringBuffer	sb				= new StringBuffer();
		int				dataCnt			= 0;


		try{
			// CSVファイル格納パス、ファイル名
			file 	 = new File(csvFilePath);

			if( file.exists() == false ){
				// ディレクトリが見つからなければ
				this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			fileFullName = file + "/" + fileName;


			// データ取得
			rs = db.executeQuery(sqlStatement);

			while (rs.next()) {

				if (fw == null) {
					// ファイルオープン
					// 検索結果が０件でない場合のみ、ファイルを作成する
					fw = new FileWriter(fileFullName, false);
					bw = new BufferedWriter(fw);
				}
				// 行データ作成
				sb.append(createCsvRowData(rs));

				// 行データ出力
				bw.write(sb.toString());
				sb.setLength(0);

				dataCnt++;

			}
		} catch (Exception e) {
			throw e;
		} finally {
			// ファイルクローズ
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}
		}

		return dataCnt;

	}

	/**
	 * CSVファイルへ出力する明細データを作成する
	 * @param		ResultSet			取得データ
	 * @return		StringBuffer	１行分の文字列
	 * @throws		SQLException
	 * @throws		Exception
	 */
	private StringBuffer createCsvRowData(ResultSet rs) throws SQLException, Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		StringBuffer sb = new StringBuffer();
		
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if (i < rsmd.getColumnCount()) {
				// 最終項目以外はカンマ編集
				sb.append(createCsvString(rs.getString(i)));
			} else {
				// 最終項目は改行編集
				sb.append(createCsvEndString(rs.getString(i)));
			}
		}

		return sb;
	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_VAN_SYOHINを取得するSQLを取得する
	 * 
	 * @return IF_VAN_SYOHIN取得SQL
	 */
	private String getIfVanSyohinSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 IVS.SIIRESAKI_CD ");
		sql.append("	,IVS.SYOHIN_CD ");
		sql.append("	,RPAD(NVL(IVS.REC_HINMEI_KANJI_NA , '" + PADDING_STR_BLANK + "'), " + REC_HINMEI_KANJI_NA_LENGTH + ", '" + PADDING_STR_BLANK + "')");
		sql.append("	,RPAD(NVL(IVS.KIKAKU_KANJI_NA     , '" + PADDING_STR_BLANK + "'), " + KIKAKU_KANJI_NA_LENGTH     + ", '" + PADDING_STR_BLANK + "')");
		//sql.append("	,RPAD(NVL(IVS.HINMEI_KANA_NA      , '" + PADDING_STR_BLANK + "'), " + HINMEI_KANA_NA_LENGTH      + ", '" + PADDING_STR_BLANK + "')");
		sql.append("	,IVS.BUNRUI1_CD ");
		sql.append("	,IVS.HACHUTANI_IRISU_QT ");
		sql.append("	,IVS.GENTANKA_VL ");
		sql.append("	,IVS.BAITANKA_VL ");
		sql.append("	,IVS.BUNRUI5_CD ");
		sql.append("	,RPAD('" + PADDING_STR_BLANK + "', " + FILLER_LENGTH + ", '" + PADDING_STR_BLANK + "') ");
		sql.append("FROM ");
		sql.append("	IF_VAN_SYOHIN IVS ");
		sql.append("ORDER BY ");
		sql.append("	 IVS.SIIRESAKI_CD ");
		sql.append("	,IVS.SYOHIN_CD ");

		return sql.toString();
	}

/********** 共通処理 **********/

	/**
	 * CSV出力データ編集共通処理
	 * @param str
	 * @return CSV出力データ
	 */
	private String createCsvString(String str) {
		return createCsvStringCommon(str, false);
	}
	private String createCsvEndString(String str) {
		return createCsvStringCommon(str, true);
	}
	/**
	 * CSV出力データ編集共通処理
	 * @param str
	 * @param endFg true:最終項目、false:最終項目以外
	 * @return CSV出力データ
	 */
	private String createCsvStringCommon(String str, boolean endFg) {
		String val = "";
		if( str != null ){
			val = str;
		}

		// セパレータの判定。最終項目の場合は改行する。
		if (endFg) {
			val += RETURN_CODE;
		} else {
			val += SPLIT_CODE;
		}

		return val;
	}

	/**
	 * ユーザーログとバッチログにログを出力します。
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
