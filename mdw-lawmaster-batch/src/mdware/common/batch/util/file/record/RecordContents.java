package mdware.common.batch.util.file.record;

import java.io.*;
import java.util.*;

import mdware.common.util.properties.PropertyUtil;
import jp.co.vinculumjapan.stc.util.csv.*;
import jp.co.vinculumjapan.stc.log.StcLog;

/**
 * <p>タイトル: RecordContents</p>
 * <p>説明: プロパティファイルを読込み、レコード内項目情報を保持します。プロパティファイル名はrecordContents.properties（固定）です。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yamanaka
 * @version 1.00 2004.01.05 毎回初期化されるので、初期Call時のみに変更
 */

public class RecordContents {
	//20061109 Shimatani mod Start 
//	private static final String RECORD_CONTENTS_PROPETIES_FILE = "rbsite/record/recordContents.properties";
	private static final String RECORD_CONTENTS_PROPETIES_FILE = "mdware/record/recordContents.properties";
	//20061109 Shimatani mod End
	private RecordKindProperties recordKindProperties = null;
	//private List recordContentsList = null;
	private static final StcLog stcLog = StcLog.getInstance();

	private Map recordContentsMap = new HashMap();

	/**
	 * コンストラクタ
	 * @param propertiesDir プロパティファイル格納用ディレクトリパス
	 * @param recordKindProperties RecordKindPropertiesクラスオブジェクト
	 */
    public RecordContents(String fileKbn) {
    	//20061110 add mod Shimatani Start
		initFromFile(fileKbn, PropertyUtil.getPropertiesDir() + "/" + RECORD_CONTENTS_PROPETIES_FILE);
//		initFromFile(fileKbn, mdware.common.batch.util.properties.BatchProperty.propertiesDir + "/" + RECORD_CONTENTS_PROPETIES_FILE);
		//20061110 add mod Shimatani End
    }
    //public RecordContents(RecordKindProperties recordKindProperties) {
	//	this.recordKindProperties = recordKindProperties;
	//	initFromFile(mdware.common.batch.util.properties.BatchProperty.propertiesDir + "/" + RECORD_CONTENTS_PROPETIES_FILE);
    //}

	public void setRecordKind(RecordKindProperties recordKindProperties) {
		this.recordKindProperties = recordKindProperties;
	}

	/**
	 * レコード内項目情報リストを返します。
	 * @return RecordContentsPropertiesクラスオブジェクトのリスト
	 */
	public List getRecordContentsPropertiesList() {
		return (List)recordContentsMap.get(recordKindProperties.getRecordKind());
	}

	/**
	 * RecordContentsPropertiesを返します。
	 * @param code String RecordContentsPropertiesの検索キー(itemCode)
	 * @return RecordContentsPropertiesクラスオブジェクト
	 */
	public RecordContentsProperties get(String itemCode) {
		Iterator ite = getRecordContentsPropertiesList().iterator();
		while (ite.hasNext()) {
			RecordContentsProperties recCont = (RecordContentsProperties)ite.next();
			if (recCont.getItemCode().equalsIgnoreCase(itemCode)) {
				return recCont;
			}
		}
		return null;
	}

	/**
	 * レコード内項目情報を返します。
	 * @param seqNo プロパティファイル内のレコード種別内での順番
	 * @return RecordContentsPropertiesクラスオブジェクト
	 */
	public RecordContentsProperties get(int seqNo) {
		return (RecordContentsProperties)getRecordContentsPropertiesList().get( seqNo );
	}

	/**
	 * レコード内項目情報数を返します。
	 * @return
	 */
	public int size() {
		return getRecordContentsPropertiesList().size();
	}

	/**
	 * プロパティファイルを読込み、レコード内項目情報を格納します。
	 * @param fileName
	 * @return
	 */
	private boolean initFromFile(String fileKbn, String fileName) {
		stcLog.getSysLog().debug("RecordContents#initFromFile(\"" + fileName + "\")が呼ばれました。");

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

				if ( data.countTokens() < 8 ) {
					continue;
				}

				String fileKbn_w = data.nextToken().trim();
				String recordKind = data.nextToken().trim();

				if ( !fileKbn_w.equals(fileKbn) ) {
					continue;
				}

				String itemCode = data.nextToken().trim();
				String lenScaleStr = data.nextToken().trim();
				StringTokenizer st = new StringTokenizer(lenScaleStr, ".");
				int length = Integer.parseInt(st.nextToken());
				int scale = 0;
				if (st.hasMoreTokens()) {
					scale = Integer.parseInt(st.nextToken());
				}
				String parameterDM = data.nextToken().trim();
				String type = data.nextToken().trim();
				String align = data.nextToken().trim();
				String preValue = data.nextToken().trim();

				RecordContentsProperties recordContentsProperties
				  = new RecordContentsProperties(fileKbn, recordKind, itemCode, length, scale, parameterDM, type, align, preValue);

				List list = (List)recordContentsMap.get(recordKind);
				if (list == null) {
					list = new ArrayList();
				}
				list.add(recordContentsProperties);

				recordContentsMap.put(recordKind, list);
			}

			inputFileReader.close();
		} catch(Exception e) {
			stcLog.getSysLog().fatal("RecordKinds#initFromFile:" + e.toString());
			return false;
		}

		return true;
	}
}