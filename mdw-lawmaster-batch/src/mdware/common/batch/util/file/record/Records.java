package mdware.common.batch.util.file.record;

import java.io.*;
import java.util.*;

import mdware.common.util.properties.PropertyUtil;
import jp.co.vinculumjapan.stc.util.csv.*;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class Records {
	//20061109 Shimatani mod Start 
//	private static final String RECORD_PROPETIES_FILE = "rbsite/record/record.properties";
	private static final String RECORD_PROPETIES_FILE = "mdware/record/record.properties";
	//20061109 Shimatani mod End 

	private static RecordProperties recordProperties = null;
	private static boolean initilized = false;
	private static Map map = null;
	private static final StcLog stcLog = StcLog.getInstance();

    public Records() {
    	//20061110 Shimatani mod Start
		initFromFile(PropertyUtil.getPropertiesDir() + "/" + RECORD_PROPETIES_FILE);
//		initFromFile(mdware.common.batch.util.properties.BatchProperty.propertiesDir + "/" + RECORD_PROPETIES_FILE);
    	//20061110 Shimatani mod End
    }

	public RecordProperties getRecordProperties(String key) {
		return (RecordProperties)map.get(key);
	}

	private void initFromFile(String fileName) {
		if (initilized) {
			return;
		}

		map = new HashMap();

		try {
			FileReader inputFileReader = new FileReader(fileName);
			BufferedReader inputBufferedReader = new BufferedReader(inputFileReader);

			String tempString;

			while ((tempString = inputBufferedReader.readLine()) != null) {
				// 空行なら読み飛ばす。
				if (tempString.trim().length() == 0)
					continue;

				stcLog.getSysLog().debug(tempString); // 読込行ログ出力

				// 最初の半角スペースを除き、最初の文字が'#'ならばコメントとみなし読み飛ばす。
				if (tempString.trim().indexOf("#") == 0)
					continue;

				CSVTokenizer data = new CSVTokenizer(tempString);

				if ( data.countTokens() < 2 ) {
					continue;
				}

				String key = data.nextToken().trim();
				String lfCode = data.nextToken().trim();
				int readByte = 0;
				try {
					readByte = Integer.parseInt(data.nextToken().trim());
				} catch (Exception e) {
				}

				this.map.put(key, new RecordProperties(lfCode, readByte));
			}

			inputFileReader.close();
		} catch(Exception e) {
			stcLog.getSysLog().fatal("RecordProperties#initFromFile:" + e.toString());
			return;
		}

		initilized = true;
	}
}