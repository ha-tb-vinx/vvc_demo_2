package mdware.common.batch.util.file.record;

import java.io.*;
import java.util.*;

import mdware.common.util.properties.PropertyUtil;
import jp.co.vinculumjapan.stc.util.csv.*;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: RecordKinds</p>
 * <p>説明: プロパティファイルを読込み、レコードに関する情報を保持します。プロパティファイル名はrecordKind.properties（固定）です。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yamanaka
 * @version 1.0
 */

public class RecordKinds {
	//20061109 Shimatani mod Start 
//	private static final String RECORD_KIND_PROPETIES_FILE = "rbsite/record/recordKind.properties";
	private static final String RECORD_KIND_PROPETIES_FILE = "mdware/record/recordKind.properties";
	//20061109 Shimatani mod End
	private String fileKbn = null;
	private List recordKindList = null;
	private static final StcLog stcLog = StcLog.getInstance();

	/**
	 * コンストラクタ
	 * @param propertiesDir プロパティファイル格納用ディレクトリパス
	 * @param fileKbn ファイル種類（プロパティファイルに記述したコード）
	 */
    public RecordKinds(String fileKbn) {
		this.fileKbn = fileKbn;
		//20061110 S.Shimatani mod Start
//		initFromFile(mdware.common.batch.util.properties.BatchProperty.propertiesDir + "/" + RECORD_KIND_PROPETIES_FILE);
		initFromFile(PropertyUtil.getPropertiesDir() + "/" + RECORD_KIND_PROPETIES_FILE);
		//20061110 S.Shimatani mod End
    }

	/**
	 * レコード種類情報を返します。
	 * @return RecordKindPropertiesクラスオブジェクトのリスト
	 */
	public List getRecordKindPropertiesList() {
		return recordKindList;
	}

	/**
	 * RecordKindPropertiesを返します。
	 * @param code String RecordKindPropertiesの検索キー(recordKind)
	 * @return recordKind
	 */
	public RecordKindProperties get(String recordKind) {
		Iterator ite = recordKindList.iterator();
		while (ite.hasNext()) {
			RecordKindProperties recKind = (RecordKindProperties)ite.next();
			if (recKind.getRecordKind().equalsIgnoreCase(recordKind)) {
				return recKind;
			}
		}
		return null;
	}

	/**
	 * RecordKindPropertiesを取得します。
	 * @param seqNo
	 * @return
	 */
	public RecordKindProperties get(int seqNo) {
		return (RecordKindProperties)recordKindList.get( seqNo );
	}

	/**
	 * レコード情報数を返します。
	 * @return
	 */
	public int size() {
		return recordKindList.size();
	}

	/**
	 * プロパティファイルを読込み、レコード情報を格納します。
	 * @param fileName
	 * @return
	 */
	private boolean initFromFile(String fileName) {
		stcLog.getSysLog().debug("RecordKinds#initFromFile(\"" + fileName + "\")が呼ばれました。");

		recordKindList = new ArrayList();

		try {
			FileReader inputFileReader = new FileReader(fileName);
			BufferedReader inputBufferedReader = new BufferedReader(inputFileReader);

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

				if ( data.countTokens() < 9 ) {
					continue;
				}

				String wkFileKbn = data.nextToken().trim();

				if ( !fileKbn.equals(wkFileKbn) ) {
					continue;
				}

				String recordKind = data.nextToken().trim();
				String recordName = data.nextToken().trim();
				String recordKbn = data.nextToken().trim();
				int recordLength = Integer.parseInt(data.nextToken().trim());
				String iteration = data.nextToken().trim();
				String recordMode = data.nextToken().trim();
				String useBeanName = data.nextToken().trim();
				String useDMName = data.nextToken().trim();

//2003.12.29 @ADD bcpコードを追加
				String bcpCd = null;
				if ( data.countTokens() > 9 ) {
					bcpCd = data.nextToken().trim();
				}

				RecordKindProperties recordKindProperties
//				  = new RecordKindProperties(fileKbn, recordKind, recordName, recordKbn, recordLength, iteration, recordMode, useBeanName, useDMName);
				  = new RecordKindProperties(fileKbn, recordKind, recordName, recordKbn, recordLength, iteration, recordMode, useBeanName, useDMName, bcpCd);

				recordKindList.add(recordKindProperties);
			}

			inputFileReader.close();
		} catch(Exception e) {
			stcLog.getSysLog().fatal("RecordKinds#initFromFile:" + e.toString());
			return false;
		}

		return true;
	}
}