package mdware.batch.master;

import java.util.*;
import java.io.*;

import jp.co.vinculumjapan.stc.util.csv.*;
import jp.co.vinculumjapan.stc.log.StcLog;

import mdware.common.batch.log.BatchLog;
import mdware.batch.master.bean.BcpBean;
import mdware.common.batch.util.properties.BatchProperty;

/**
 * <p>タイトル: MasterImport</p>
 * <p>説明: マスタ取込処理を行います。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * <p>日付: 2002.12.18</p>
 * @author yamanaka
 * @version 1.0
 * @version 1.1 kaneda Oracle、SQLServerに対応するために改変
 * @version 1.2 kaneda OracleのSQL*Serverにて停止する場合があるのを修正
 * @version 1.3 2004.09.24 sakai 同時に実行された際にバッチファイルの生成に失敗が発生するのを修正
 */

public class ExecBcp {
	private static final String IMPORT_PROPETIES_FILE =
		"rbsite/master/import.properties";
	private static final String FORMAT_DIR = "rbsite/master/format";
	private String CMD_FILE = "rbsite/master/format/bcpExec.bat";
	private String CMD_PATH = "rbsite/master/format/";
	private String CMD_FILENAME = "bcpExec.bat";
	private Map bcpMap = new HashMap();

	private BatchLog batchLog = BatchLog.getInstance();
	private static boolean initilized = false;

	/**
	 * コンストラクタ。プロパティファイルを読込み、初期化します。
	 */
	public ExecBcp() {
		initilized =
			initBcp(BatchProperty.propertiesDir + "/" + IMPORT_PROPETIES_FILE);
	}

	/**
	 * インポート用のプロパティ情報格納マップを返します。
	 * @return Map
	 */
	public Map getBcpPropertiesMap() {
		return bcpMap;
	}

	/**
	 * プロパティファイルを読込み、レコード情報を格納します。
	 * @param fileName
	 * @return
	 */
	private boolean initBcp(String fileName) {
		batchLog.getLog().debug(
			"MasterImport#initBcp(\"" + fileName + "\")が呼ばれました。");

		try {
			FileReader inputFileReader = new FileReader(fileName);
			BufferedReader inputBufferedReader =
				new BufferedReader(inputFileReader);

			String tempString;

			while ((tempString = inputBufferedReader.readLine()) != null) {
				// 空行なら読み飛ばす。
				if (tempString.trim().length() == 0)
					continue;

				//stcLog.getSysLog().debug(tempString); // 読込行ログ出力

				// 最初の半角スペースを除き、最初の文字が'#'ならばコメントとみなし読み飛ばす。
				if (tempString.trim().indexOf("#") == 0)
					continue;

				CSVTokenizer data = new CSVTokenizer(tempString);

				if (data.countTokens() < 8) {
					continue;
				}

				String bcpCode = data.nextToken().trim();
				String dataSyubetuCode = data.nextToken().trim();
				String masterName = data.nextToken().trim();
				String tableName = data.nextToken().trim();
				String userId = data.nextToken().trim();
				String password = data.nextToken().trim();
				String serverName = data.nextToken().trim();
				String formatFile = data.nextToken().trim();
				// 2004/07/16 新規追加 kaneda
				String dbKind = data.nextToken().trim();
				List pkList = new ArrayList();
				while (data.hasMoreTokens()) {
					pkList.add(data.nextToken().trim());
				}
				String[] primaryKey = new String[pkList.size()];
				for (int i = 0; i < pkList.size(); i++) {
					primaryKey[i] = (String) pkList.get(i);
				}

				BcpBean bcpBean =
					new BcpBean(
						bcpCode,
						dataSyubetuCode,
						masterName,
						tableName,
						userId,
						password,
						serverName,
						formatFile,
						dbKind,
						primaryKey);

				bcpMap.put(bcpCode, bcpBean);
			}
			inputFileReader.close();
		} catch (Exception e) {
			batchLog.getLog().fatal("MasterImport#initBcp:" + e.toString());
			return false;
		}
		return true;
	}

	/**
	 * マスタインポート処理を行います（MS-SQLServer用）
	 * @param bcpCode BCPコード
	 * @param masterFilePath マスタファイルパス
	 * @throws Exception
	 */
	public void importMasterMSSQL(String bcpCode, String masterFilePath)
		throws Exception {
		BcpBean bcpBean = (BcpBean) this.bcpMap.get(bcpCode);
		if (bcpBean == null) {
			throw new IllegalArgumentException("与えられたパラメータが不正です。");
		}
		batchLog.getLog().debug("マスタインポートを開始します。");

		String formatFile =
			new File(
				BatchProperty.propertiesDir
					+ "/"
					+ FORMAT_DIR
					+ "/"
					+ bcpBean.getFormatFile())
				.getAbsolutePath();

		StringBuffer bcpCmd = new StringBuffer();

		bcpCmd.append("bcp ");
		bcpCmd.append(bcpBean.getTableName());
		bcpCmd.append(" in ");
		bcpCmd.append(masterFilePath);
		bcpCmd.append(" -U");
		bcpCmd.append(bcpBean.getUserId());
		bcpCmd.append(" -P");
		bcpCmd.append(bcpBean.getPassword());
		bcpCmd.append(" -S");
		bcpCmd.append(bcpBean.getServerName());
		bcpCmd.append(" -f");
		bcpCmd.append(formatFile);
		bcpCmd.append(" -obcpExecLog.log");

		//bcpCmd.append("notepad.exe");

		batchLog.getLog().debug(bcpCmd.toString());

		int exitNum = 0;

		String cmdFile = BatchProperty.propertiesDir + "/" + CMD_PATH + bcpCode + "_" + CMD_FILENAME;
		try {
			//バッチファイルを強引に作成
			FileWriter fileWriter = new FileWriter(cmdFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(bcpCmd.toString());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			fileWriter.close();

			String cmdLine = new File(cmdFile).getAbsolutePath();
			batchLog.getLog().debug("バッチファイルを実行します。：コマンド = " + cmdLine);

			String[] cmdArray = { cmdLine }; //コマンド＆パラメータ格納

			batchLog.getLog().debug(
				"マスタインポートが終了しました。終了コードは[" + getExitValue(cmdArray) + "]です。");
		} catch (IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * マスタインポート処理を行います（Oracle用）
	 * @param bcpCode BCPコード
	 * @param masterFilePath マスタファイルパス
	 * @throws Exception
	 */
	public void importMasterOracle(String bcpCode, String masterFilePath)
		throws Exception {
		// SQL*loader
		BcpBean bcpBean = (BcpBean) this.bcpMap.get(bcpCode);
		if (bcpBean == null) {
			throw new IllegalArgumentException("与えられたパラメータが不正です。");
		}

		batchLog.getLog().debug("マスタインポートを開始します。");

		String formatFile =
			new File(
				BatchProperty.propertiesDir
					+ "/"
					+ FORMAT_DIR
					+ "/"
					+ bcpBean.getFormatFile())
				.getAbsolutePath();

		StringBuffer bcpCmd = new StringBuffer();

		bcpCmd.append("sqlldr ");
		// bcpCmd.append(bcpBean.getTableName());
		// bcpCmd.append(" in ");
		// bcpCmd.append(masterFilePath);
		bcpCmd.append(" userid=");
		bcpCmd.append(bcpBean.getUserId());
		bcpCmd.append("/");
		bcpCmd.append(bcpBean.getPassword());
		bcpCmd.append("@");
		bcpCmd.append(bcpBean.getServerName());
		bcpCmd.append(" control=");
		bcpCmd.append(formatFile);
		bcpCmd.append(" log=sqlloadExecLog.log");

		//bcpCmd.append("notepad.exe");

		batchLog.getLog().debug(bcpCmd.toString());

		int exitNum = 0;

		String cmdFile = BatchProperty.propertiesDir + "/" + CMD_PATH + bcpCode + "_" + CMD_FILENAME;
		try {
			//バッチファイルを強引に作成
			FileWriter fileWriter = new FileWriter(cmdFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(bcpCmd.toString());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			fileWriter.close();

			String cmdLine = new File(cmdFile).getAbsolutePath();
			batchLog.getLog().debug("バッチファイルを実行します。：コマンド = " + cmdLine);

			String[] cmdArray = { cmdLine }; //コマンド＆パラメータ格納

			batchLog.getLog().debug(
				"マスタインポートが終了しました。終了コードは[" + getExitValue(cmdArray) + "]です。");
		} catch (IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * 別プロセスを起動する。
	 * @param args
	 * @return
	 * @throws IOException
	 */
	private static int getExitValue(String[] args) throws IOException {
		Process pr = null;
		try {
			pr = Runtime.getRuntime().exec(args);
			/*
			InputStream es = pr.getErrorStream();
			byte[] b = new byte[1024];
			int len_es = -1;
			while((len_es = es.read(b)) > -1){
				System.out.println(new String(b,0,len_es));
			}
			*/
			//SQL*LOADERが停止する場合があるのを修正 2004.07.30 kaneda
			InputStream is = pr.getInputStream();
			byte[] b = new byte[1024];
			int len_is = -1;
			while ((len_is = is.read(b)) > -1) {
				System.out.println(new String(b, 0, len_is));
			}

		} catch (java.io.IOException ie) {
			throw ie;
		}
		int retValue = 0;

		while (true) {
			try {
				retValue = pr.exitValue();
				break;
			} catch (IllegalThreadStateException itse) {
				//まだ実行中なので0.2秒だけ待ちます
				try {
					Thread.sleep(200);
				} catch (InterruptedException ie) {}
			}
		}
		return retValue;
	}

}