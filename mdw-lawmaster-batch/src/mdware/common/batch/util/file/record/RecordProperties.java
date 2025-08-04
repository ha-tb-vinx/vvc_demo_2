package mdware.common.batch.util.file.record;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class RecordProperties {
	public static String LINE_CR = "\r";
	public static String LINE_LF = "\n";
	public static String LINE_CRLF = "\r\n";
	public static String LINE_NO = "";

	public static String CODE_CR = "CR";
	public static String CODE_LF = "LF";
	public static String CODE_CRLF = "CRLF";
	public static String CODE_NO = "";

	private String lfCode = null;
	private String lineField = null;
	private int readByte = 0;

	public RecordProperties(String lfCode, int readByte) {
		this.lfCode = lfCode;
		this.readByte = readByte;
		if (lfCode.equalsIgnoreCase(CODE_CR)) {
			lineField = LINE_CR;
		} else if (lfCode.equalsIgnoreCase(CODE_LF)) {
			lineField = LINE_LF;
		} else if (lfCode.equalsIgnoreCase(CODE_CRLF)) {
			lineField = LINE_CRLF;
		} else {
			lineField = CODE_NO;
		}
	}

    public String getLineField() {
        return lineField;
    }
    public int getReadByte() {
        return readByte;
    }
    public String getLfCode() {
        return lfCode;
    }
    public void setLfCode(String lfCode) {
        this.lfCode = lfCode;
    }
}