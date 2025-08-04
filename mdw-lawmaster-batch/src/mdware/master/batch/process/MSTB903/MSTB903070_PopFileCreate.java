package mdware.master.batch.process.MSTB903;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB903070_PopFileCreate.java クラス</p>
 * <p>説明　: 商品マスタデータファイル、商品マスタエンドファイル、店別商品マスタデータファイル、店別商品マスタエンドファイルを作成する。</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * Version 1.00  (2015.05.05) DAI.BQ 海外LAWSON様対応 英文化対応
 */
public class MSTB903070_PopFileCreate {

	// 区切り文字（カンマ区切り）
	private static final String SPLIT_CODE = ",";
	// 改行文字
	private static final String RETURN_CODE = System.getProperty("line.separator");

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	/** CSVファイルパス */
	private String csvFilePath = null;
	/** 商品マスタデータファイル名 */
	private String popFtpSyohin = null;
	/** 商品マスタエンドファイル名 */
	private String popFtpSyohinEnd = null;
	/** 店別商品マスタデータファイル名 */
	private String popFtpTenbetuSyohin = null;
	/** 店別商品マスタエンドファイル名 */
	private String popFtpTenbetuSyohinEnd = null;

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB903070_PopFileCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB903070_PopFileCreate() {
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

			// 商品マスタデータファイル作成
			writeLog(Level.INFO_INT, "商品マスタデータファイル（" + popFtpSyohin + "）作成処理を開始します。");
			iRec = createCSVFile(popFtpSyohin, getIfPopSyohinHeader(), getIfPopSyohinSelectSql());
			writeLog(Level.INFO_INT, "商品マスタデータファイルを" + iRec + "件作成しました。");

			// 商品マスタエンドファイル作成
			writeLog(Level.INFO_INT, "商品マスタエンドファイル（" + popFtpSyohin + "）作成処理を開始します。");
			iRec = createENDFile(popFtpSyohinEnd);
			writeLog(Level.INFO_INT, "商品マスタエンドファイルを作成しました。");

			// 店別商品マスタデータファイル作成
			writeLog(Level.INFO_INT, "店別商品マスタデータファイル（" + popFtpTenbetuSyohin + "）作成処理を開始します。");
			iRec = createCSVFile(popFtpTenbetuSyohin, getIfPopTenbetuSyohinHeader(), getIfPopTenbetuSyohinSelectSql());
			writeLog(Level.INFO_INT, "店別商品マスタデータファイルを" + iRec + "件作成しました。");

			// 店別商品マスタエンドファイル作成
			writeLog(Level.INFO_INT, "店別商品マスタエンドファイル（" + popFtpTenbetuSyohinEnd + "）作成処理を開始します。");
			iRec = createENDFile(popFtpTenbetuSyohinEnd);
			writeLog(Level.INFO_INT, "店別商品マスタエンドファイルを作成しました。");

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
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_POP);
		if (csvFilePath == null || csvFilePath.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}

		// 商品マスタデータファイル名取得
		popFtpSyohin = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POP_FTP_SYOHIN);
		if (popFtpSyohin == null || popFtpSyohin.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタデータファイル名が取得できませんでした");
			throw new Exception();
		}

		// 商品マスタエンドファイル名取得
		popFtpSyohinEnd = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POP_FTP_SYOHIN_END);
		if (popFtpSyohinEnd == null || popFtpSyohinEnd.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタエンドファイル名が取得できませんでした");
			throw new Exception();
		}

		// 店別商品マスタデータファイル名取得
		popFtpTenbetuSyohin = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POP_FTP_TENBETU_SYOHIN);
		if (popFtpTenbetuSyohin == null || popFtpTenbetuSyohin.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、店別商品マスタデータファイル名が取得できませんでした");
			throw new Exception();
		}

		// 店別商品マスタエンドファイル名取得
		popFtpTenbetuSyohinEnd = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POP_FTP_TENBETU_SYOHIN_END);
		if (popFtpTenbetuSyohinEnd == null || popFtpTenbetuSyohinEnd.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、店別商品マスタエンドファイル名が取得できませんでした");
			throw new Exception();
		}

	}

	/**
	 * CSVファイルを作成します。
	 * @param fileName		ファイル名
	 * @param headerStr		ヘッダー文字列
	 * @param sqlStatement	検索SQL
	 * @return 出力件数
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	private int createCSVFile(String fileName, String headerStr, String sqlStatement) throws IOException, SQLException, Exception {

		ResultSet rs = null;
		String fileFullName = null;
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		StringBuffer sb = new StringBuffer();
		int dataCnt = 0;

		try {
			// CSVファイル格納パス、ファイル名
			file = new File(csvFilePath);

			if (file.exists() == false) {
				// ディレクトリが見つからなければ
				this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			fileFullName = file + "/" + fileName;

			// ファイルオープン
			fw = new FileWriter(fileFullName, false);
			bw = new BufferedWriter(fw);

			// ヘッダー書き出し
			bw.write(headerStr);

			// データ取得
			rs = db.executeQuery(sqlStatement);

			while (rs.next()) {

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

	/**
	 * ENDファイルを作成します。(０バイトファイル)
	 * @param fileName		ファイル名
	 * @return 出力件数
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	private int createENDFile(String fileName) throws IOException, SQLException, Exception {

		String fileFullName = null;
		File file = null;
		FileWriter fw = null;
		int dataCnt = 0;

		try {
			// CSVファイル格納パス、ファイル名
			file = new File(csvFilePath);

			if (file.exists() == false) {
				// ディレクトリが見つからなければ
				this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			fileFullName = file + "/" + fileName;

			// ファイルオープン
			fw = new FileWriter(fileFullName, false);

		} catch (Exception e) {
			throw e;
		} finally {
			// ファイルクローズ
			if (fw != null) {
				fw.close();
			}
		}

		return dataCnt;
	}


	/**
	 * 商品マスタヘッダを取得する
	 * 
	 * @return 商品マスタヘッダ文字列
	 */
	private String getIfPopSyohinHeader() throws SQLException {
		StringBuilder sb = new StringBuilder();
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		sb.append(MessageUtil.getMessage("MSTB903_TXT_00001", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00002", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00003", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00004", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00005", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00006", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00007", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00008", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00009", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00010", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00011", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00012", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00013", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00014", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00015", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00016", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00017", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00018", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00019", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00020", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00021", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00022", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00023", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00024", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00025", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00026", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00027", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00028", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00029", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00030", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00031", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00032", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00033", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00034", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00035", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00036", userLocal));
		sb.append(RETURN_CODE);

		return sb.toString();
	}


	/**
	 * 店別商品マスタヘッダを取得する
	 * 
	 * @return 店別商品マスタヘッダ文字列
	 */
	private String getIfPopTenbetuSyohinHeader() throws SQLException {
		StringBuilder sb = new StringBuilder();
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		sb.append(MessageUtil.getMessage("MSTB903_TXT_00001", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00002", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00037", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00009", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00021", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00022", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00026", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00027", userLocal));
		sb.append(SPLIT_CODE).append(MessageUtil.getMessage("MSTB903_TXT_00036", userLocal));
		sb.append(RETURN_CODE);

		return sb.toString();
	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_POP_SYOHINを取得するSQLを取得する
	 * 
	 * @return IF_POP_SYOHIN取得SQL
	 */
	private String getIfPopSyohinSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 IPS.MAINTE_KB ");
		sql.append("	,IPS.SYOHIN_CD ");
		sql.append("	,IPS.BUNRUI1_CD ");
		sql.append("	,IPS.BUNRUI5_CD ");
		sql.append("	,IPS.MAKER_KANJI_NA ");
		sql.append("	,IPS.HINMEI_KANJI_NA ");
		sql.append("	,IPS.KIKAKU_KANJI_NA ");
		sql.append("	,IPS.KIKAKU_KANJI_2_NA ");
		sql.append("	,IPS.BAITANKA_VL ");
		sql.append("	,IPS.MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,IPS.BUMON_DAI_CD ");
		sql.append("	,IPS.BUMON_CHU_CD ");
		sql.append("	,IPS.JAN_CD ");
		sql.append("	,IPS.JAN_UPCA_CD ");
		sql.append("	,IPS.JAN_UPCE_CD ");
		sql.append("	,IPS.YOBI_1 ");
		sql.append("	,IPS.JAN_4_CD ");
		sql.append("	,IPS.JAN_5_CD ");
		sql.append("	,IPS.JAN_6_CD ");
		sql.append("	,IPS.ZEI_KB ");
		sql.append("	,IPS.TAX_RT ");
		sql.append("	,IPS.BAITANKA_NUKI_VL ");
		sql.append("	,IPS.SYOHIN_WIDTH_QT ");
		sql.append("	,IPS.SYOHIN_HEIGHT_QT ");
		sql.append("	,IPS.SYOHIN_DEPTH_QT ");
		sql.append("	,IPS.SIIRESAKI_CD ");
		sql.append("	,IPS.HACHU_TANI ");
		sql.append("	,IPS.BIN_1_KB ");
		sql.append("	,IPS.BIN_2_KB ");
		sql.append("	,IPS.SAKE_NAIYORYO_QT ");
		sql.append("	,IPS.SYOHI_KIGEN_DT ");
		sql.append("	,IPS.YOBI_2 ");
		sql.append("	,IPS.YOBI_3 ");
		sql.append("	,IPS.YOBI_4 ");
		sql.append("	,IPS.YOBI_5 ");
		sql.append("	,IPS.AUTO_HAT_KB ");
		sql.append("FROM ");
		sql.append("	IF_POP_SYOHIN IPS ");
		sql.append("ORDER BY ");
		sql.append("	 IPS.MAINTE_KB ");
		sql.append("	,IPS.BUNRUI1_CD ");
		sql.append("	,IPS.SYOHIN_CD ");

		return sql.toString();
	}

	/**
	 * IF_POP_TENBETU_SYOHINを取得するSQLを取得する
	 * 
	 * @return IF_POP_TENBETU_SYOHIN取得SQL
	 */
	private String getIfPopTenbetuSyohinSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 IPTS.MAINTE_KB ");
		sql.append("	,IPTS.SYOHIN_CD ");
		sql.append("	,IPTS.TENPO_CD ");
		sql.append("	,IPTS.BAITANKA_VL ");
		sql.append("	,IPTS.TAX_RT ");
		sql.append("	,IPTS.BAITANKA_NUKI_VL ");
		sql.append("	,IPTS.SIIRESAKI_CD ");
		sql.append("	,IPTS.HACHU_TANI ");
		sql.append("	,IPTS.AUTO_HAT_KB ");
		sql.append("FROM ");
		sql.append("	IF_POP_TENBETU_SYOHIN IPTS ");
		sql.append("ORDER BY ");
		sql.append("	 IPTS.MAINTE_KB ");
		sql.append("	,IPTS.TENPO_CD ");
		sql.append("	,IPTS.SYOHIN_CD ");

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
		if (str != null) {
			val = str.trim();
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
	private void writeLog(int level, String message) {
		String jobId = userLog.getJobId();

		switch (level) {
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
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
		batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}
