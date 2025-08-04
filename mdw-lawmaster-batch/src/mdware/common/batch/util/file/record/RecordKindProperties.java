package mdware.common.batch.util.file.record;


/**
 * <p>タイトル: RecordKindProperties</p>
 * <p>説明: レコード情報保持クラス。このクラスでは主にレコード種類や長さまたレコード文字列そのものなどを保持できます。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class RecordKindProperties {
	private String fileKbn = null;
	private String recordKind = null;
	private String recordName = null;
	private String recordKbn = null;
	private int recordLength = 0;
	private String iteration = null;
	private String recordMode = null;
	private String recordString = null;
	private String useBeanName = null;
	private String useDMName = null;
//2003.12.29 @ADD bcpコードを追加
	private String bcpCode = null;

	/**
	 * コンストラクタ
	 * @param fileKbn
	 * @param recordKind
	 * @param recordName
	 * @param recordKbn
	 * @param recordLength
	 * @param iteration
	 * @param recordMode
	 * @param useBeanName
	 * @param useDMName
	 */
    public RecordKindProperties(String fileKbn, String recordKind, String recordName,
	                            String recordKbn, int recordLength, String iteration, String recordMode,
								String useBeanName, String useDMName, String bcpCode) {
		this.fileKbn = fileKbn;
		this.recordKind = recordKind;
		this.recordName = recordName;
		this.recordKbn = recordKbn;
		this.recordLength = recordLength;
		this.iteration = iteration;
		this.recordMode = recordMode;
		this.useBeanName = useBeanName;
		this.useDMName = useDMName;
//2003.12.29 @ADD bcpコードを追加
		this.bcpCode = bcpCode;
    }

	/**
	 * 現在のRecordKindPropertiesオブジェクトの各メンバ値を保持した新しいRecordKindPropertiesオブジェクトインスタンスを返します。
	 * @param recordString
	 * @return
	 */
	public RecordKindProperties createCloneRecord(String recordString) {
		RecordKindProperties cloneRecord = new RecordKindProperties(fileKbn, recordKind, recordName, recordKbn, recordLength, iteration, recordMode, useBeanName, useDMName, bcpCode);
		cloneRecord.setRecordString(recordString);
		return cloneRecord;
	}

    public String getFileKbn() {
        return fileKbn;
    }
    public String getIteration() {
        return iteration;
    }
    public String getRecordKbn() {
        return recordKbn;
    }
    public String getRecordKind() {
        return recordKind;
    }
    public int getRecordLength() {
        return recordLength;
    }
    public String getRecordName() {
        return recordName;
    }
    public String getRecordString() {
        return recordString;
    }
	public String getRecordMode() {
		return recordMode;
	}

    public void setRecordString(String recordString) {
        this.recordString = recordString;
    }
    public String getUseBeanName() {
        return useBeanName;
    }
    public String getUseDMName() {
        return useDMName;
    }
    
//2003.12.29 @ADD bcpコードを追加
    public String getBcpCode() {
        return bcpCode;
    }
}