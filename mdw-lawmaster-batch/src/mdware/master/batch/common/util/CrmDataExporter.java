package mdware.master.batch.common.util;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.batch.util.convert.DataExchanger;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;
import java.io.*;
import java.sql.ResultSet;
import java.util.*;

/**
 * <p>タイトル: CSV データの処理、フォーマット、エクスポート (海外CRM用IF)</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VVC</p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */

public class CrmDataExporter {
	/** ログ出力用変数 */
	private static final BatchLog BATCH_LOG = BatchLog.getInstance();
	/** ログ出力用変数 */
	private static final BatchUserLog USER_LOG = BatchUserLog.getInstance();
	/** バッチID */
	private static final String JOB_ID = USER_LOG.getJobId();
	/** バッチ名 */
	private static final String JOB_NAME = Jobs.getInstance().get(JOB_ID).getJobName();
	/** IFファイル格納ディレクトリ */
	private static final String IF_CRM_PATH = ResorceUtil.getInstance().getPropertie("IF_CRM_PATH");
	/** 出力ファイル文字コード */
	private static final String OUTPUT_CHAR_SET = "UTF-8";
	/** DBインスタンス */
	private static MasterDataBase db = null;
	/** CSVファイル名 */
	public String fileName;
	/** 検索条件 */
	public String queryString;
    
	public static class ColumnSpec {
		public String columnName;
		public int maxBytes;
		public boolean leftToRight;
        
		public ColumnSpec(String columnName, int maxBytes) {
			this.columnName = columnName;
			this.maxBytes = maxBytes;
		}
	}
    
	public List<ColumnSpec> columnSpecs = new ArrayList<ColumnSpec>();
    
	/**
	 * 本処理 - ファイル作成
	 * 
	 * @throws Exception 例外
	 */
	public void export() throws Exception {
		writeLog(Level.INFO_INT, "処理を開始します。");

		//基本設定をチェックする
		if (fileName == null 
				|| fileName.isEmpty() 
				|| queryString == null 
				|| queryString.isEmpty() 
				|| columnSpecs.isEmpty()) {
			writeLog(Level.ERROR_INT, "設定エラー");
			throw new Exception("設定エラー");
		}

		//ＣＳＶ出力先のパスの設定をチェックする
		if (IF_CRM_PATH == null || IF_CRM_PATH.trim().length() == 0 )
		{
			writeLog(Level.ERROR_INT, "ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception("出力パスが設定されていません");
		}

		//ディレクトリの存在をチェックする
		File folderPath = new File(IF_CRM_PATH);
		if (!folderPath.exists()) {
			writeLog(Level.ERROR_INT, "ＣＳＶ出力先のパスが存在しません");
			throw new Exception(folderPath.getPath().toString() + " が存在しません。");
		}

		//古いCSVファイルの存在をチェックする
		String csvFilePath = folderPath + File.separator + fileName + ".csv";
		deleteFileIfExists(csvFilePath);

		//CSVファイル作成
		writeLog(Level.INFO_INT, fileName + ".csvファイル作成を開始します。");
		db = new MasterDataBase("rbsite_ora");
		ResultSet rs = db.executeQuery(queryString);
		int iRec = 0;

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(csvFilePath), OUTPUT_CHAR_SET);
		BufferedWriter writer = new BufferedWriter(outputStreamWriter);

		// UTF_8_BOM
		writer.write("\uFEFF");

		while (rs.next()) {
			if (iRec != 0) {
				writer.newLine();
			}
			iRec++;
			for (int i = 0; i < columnSpecs.size(); i++) {
				ColumnSpec col = columnSpecs.get(i);
				String rawValue = rs.getString(col.columnName);
				String value = cutStringByByte(rawValue, col.maxBytes);
				value = "\"" + value.replace("\"", "\"\"") + "\"";
				writer.write(value);
				if (i < columnSpecs.size() - 1) {
					writer.write(",");
				}
		    }
		}
		writer.close();
		rs.close();
		db.close();
		writeLog(Level.INFO_INT, iRec + "件のデータを取得しました");
		writeLog(Level.INFO_INT, fileName + ".csvファイル作成を終了しました。");

		//古いZIPファイルの存在をチェックする
		String zipFilePath =  csvFilePath.replace(".csv", ".zip");
		deleteFileIfExists(zipFilePath);

		//ZIPファイル作成
		writeLog(Level.INFO_INT, fileName + ".zipファイル作成を開始します。");
		DataExchanger.exchange(DataExchanger.ZIP, csvFilePath, zipFilePath);
		writeLog(Level.INFO_INT, fileName + ".zipファイル作成を終了しました。");

		//CSVファイル削除
		deleteFileIfExists(csvFilePath);

		writeLog(Level.INFO_INT, "処理を終了しました。");
	}
    
	public static String cutStringByByte(String value, int maxBytes) throws UnsupportedEncodingException {	
		if (value == null || value.isEmpty() || maxBytes <= 0) 
			return "";
		
		value = value.trim();
		
		byte[] bytes = value.getBytes(OUTPUT_CHAR_SET);
		
		if (bytes.length <= maxBytes) 
			return value;
		
		int byteCount = 0;
		int i = 0;
		for (; i < value.length(); i++) {
			String ch = String.valueOf(value.charAt(i));
			int charBytes = ch.getBytes(OUTPUT_CHAR_SET).length;
			
			if (byteCount + charBytes > maxBytes)
				break;
			
			byteCount += charBytes;
		}
	    
	    return value.substring(0, i);
	}

	public static void writeLog(int level, String message) {
		switch (level) {
			case Level.DEBUG_INT:
				USER_LOG.debug(message);
				BATCH_LOG.getLog().debug(JOB_ID, JOB_NAME, message);
				break;

			case Level.INFO_INT:
				USER_LOG.info(message);
				BATCH_LOG.getLog().info(JOB_ID, JOB_NAME, message);
				break;

			case Level.ERROR_INT:
				USER_LOG.error(message);
				BATCH_LOG.getLog().error(JOB_ID, JOB_NAME, message);
				break;

			case Level.FATAL_INT:
				USER_LOG.fatal(message);
				BATCH_LOG.getLog().fatal(JOB_ID, JOB_NAME, message);
				break;
		}
	}
	
	public static void deleteFileIfExists(String filePath) throws Exception {
		File file = new File(filePath);
		if (file.exists()) {
			if (file.delete()) {
				writeLog(Level.INFO_INT, filePath + " の削除処理を実施しました。");
			} else {
				writeLog(Level.ERROR_INT, filePath + " の削除に失敗しました。");
				throw new IOException("ファイル削除失敗: " + filePath);
			}
		}
	}
}