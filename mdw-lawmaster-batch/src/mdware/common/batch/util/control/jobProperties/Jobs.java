package mdware.common.batch.util.control.jobProperties;

import java.io.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.csv.*;
import jp.co.vinculumjapan.stc.util.properties.StcLibProperty;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <P>タイトル:  STCライブラリ</P>
 * <P>説明:</P>
 * <P>著作権:	Copyright (c) 2001</P>
 * <P>会社名:	株式会社マイカルシステムズ</P>
 * <PRE>
 *
 * </PRE>
 * @author Nob
 * @version 1.0
 */

public class Jobs {
	private static final Jobs INSTANCE = new Jobs();
	private static Map jobMap;
	private boolean initialize = false;
	private static final StcLog stcLog = StcLog.getInstance();

	/**
	 * シングルトンで使用するコンストラクタ
	 */
	private Jobs() {
	}

	/**
	 * このクラスのインスタンスを返す。
	 * シングルトンを実現するためこのメソッドからインスタンス化されたクラスを取得する。
	 * @return Jobs
	 */
	public static Jobs getInstance() {
		return INSTANCE;
	}

	/**
	 * JobIDに対応したＪＯＢ名やクラスを保持したクラスをマップに登録する。
	 * @param jobProperties JobProperties
	 * @return Object 同じキーで登録したクラスが存在していたときは以前のクラスを返します。同じキーのクラスが存在しない時はnullを返します。
	 */
	private Object put(JobProperties jobProperties) {
		return jobMap.put(jobProperties.getCode(), jobProperties);
	}

	/**
	 * JobPropertiesを返します。
	 * @param code String JobPropertiesの検索キー(ＨＴＭＬで指定されるJobID)
	 * @return JobProperties
	 */
	public JobProperties get(String code) {
		return (JobProperties)jobMap.get( code );
	}

	/**
	 * JobPropertiesを保持しているＭａｐからＣｏｌｌｅｃｔｉｏｎを返す。
	 * @return Collection
	 */
	public Collection values() {
		return jobMap.values();
	}

	/**
	 * JobPropertiesをMapから全削除する。
	 */
	public void clear() {
		jobMap.clear();
	}

	/**
	 * Map内のJobPropertiesの数を返す。
	 * @return int
	 */
	public int size() {
		return jobMap.size();
	}

	/**
	 * Map内にJobPropertiesが空かを返す。
	 * @return boolean
	 */
	public boolean isEmpty() {
		return jobMap.isEmpty();
	}

	/**
	 * 与えられたキーがＭａｐに存在するかを返す。
	 * @param code String
	 * @return boolean
	 */
	public boolean constainsKey(String code) {
		return jobMap.containsKey(code);
	}

	/**
	 * 初期化済みかを返す。
	 * @return boolean
	 */
	public boolean isInitialized() {
		return initialize;
	}

	/**
	 * 初期化を行います。
	 * 与えられたファイルの内容を読みJobPropertiesを生成する。
	 * @param fileName String
	 * @return boolean
	 */
	public boolean initFromFile(String fileName) {
		if (initialize) return true;

		stcLog.getSysLog().debug("Jobs#initFromFile(\"" + fileName + "\")が呼ばれました。");

		jobMap = new HashMap();

		try {
			// 2015/08/21 Sou BatchJob.properties読む時エンコード文字指定 Start
			//FileReader inputFileReader = new FileReader(fileName);
			InputStreamReader inputFileReader = new InputStreamReader(new FileInputStream(fileName), StcLibProperty.propertyFilesEncoding);
			// 2015/08/21 Sou BatchJob.properties読む時エンコード文字指定 End
			BufferedReader inputBufferedReader = new BufferedReader(inputFileReader);

			String tempString;

			while ((tempString = inputBufferedReader.readLine()) != null) {
				// 空行なら読み飛ばす。
				if (tempString.trim().length() == 0)
					continue;
				System.out.println(tempString);
				stcLog.getSysLog().debug(tempString); // 読込行ログ出力

				// 最初の半角スペースを除き、最初の文字が'#'ならばコメントとみなし読み飛ばす。
				if (tempString.trim().indexOf("#") == 0)
					continue;

				CSVTokenizer data = new CSVTokenizer(tempString);

				if ( data.countTokens() < 4 ) {
					stcLog.getSysLog().warn("この行は読み飛ばします。（読込行: " + tempString + "要素数: " + data.countTokens() + "）");
					continue;
				}

				String code = data.nextToken().trim();
				String jobName = data.nextToken().trim();
				String className = data.nextToken().trim();
				String methodName = data.nextToken().trim();
				ArrayList argList = new ArrayList();
				while (data.hasMoreTokens()) {
					argList.add(data.nextToken().trim());
				}
				Object[] args = argList.toArray();
				String logString = "code: " + code + " jobName: " + jobName + " className: " + className + " methodName: " + methodName;
				for (int i = 1 ; i <= args.length ; i++) {
					logString += " args[" + i + "]: " + args[i - 1].toString();
				}
				stcLog.getSysLog().debug(logString);

				JobProperties jobProperties = new JobProperties(code, jobName, className, methodName, args);
				stcLog.getSysLog().debug("jobProperties: " + jobProperties.toString());

				put(jobProperties);
			}

			inputFileReader.close();
		} catch(Exception e) {
			stcLog.getSysLog().fatal("Jobs#initFromFile:" + e.toString());
			return false;
		}

		initialize = true;

		return true;
	}
}
