package mdware.common.batch.util.file.record;

/**
 * <p>タイトル: RecordContentsProperties</p>
 * <p>説明: レコード内データ項目情報を保持します。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 * @version 1.1 2004/04/26 update takata 障害報告書No189への対応
 * @version 1.2 2004/12/26 shimatani ポスフール独自仕様+符号数値対応
 * @version 1.3 2006/11/09 shimatani フジ様対応 スペースカット CAHR型を追加 対応
 */

public class RecordContentsProperties {
	public static final String TYPE_CHAR = "X";
	public static final String TYPE_NUMBER = "9";

	//20031104 @ADD 符号数値対応
	public static final String TYPE_SIGN_NUMBER = "S9";
	//20041226 @ADD 符号数値対応
	public static final String TYPE_PLUS_SIGN_NUMBER = "+S9";
	//20061109 @ADD スペース付与無しCHAR型
	public static final String TYPE_SPC_CHAR = "SPCX";

	public static final String ALIGN_RIGHT = "right";
	public static final String ALIGN_LEFT = "left";

	private String fileKbn = null;
	private String recordKind = null;
	private String itemCode = null;
	private String itemValue = null;
	private int length = 0;
	private String parameterDM = null;
	private String type = null;
	private boolean left = true;
	private int scale = 0;

	private String preValue = null; // 2004/04/26 add takata

	/**
	 * コンストラクタ
	 * @param fileKbn
	 * @param recordKind
	 * @param itemCode
	 * @param length
	 * @param scale
	 * @param parameterDM
	 * @param type
	 * @param align
	 * @param preValue
	 */
	public RecordContentsProperties(String fileKbn, String recordKind, String itemCode, int length, int scale, String parameterDM,
									String type, String align, String preValue) {
		this.fileKbn = fileKbn;
		this.recordKind = recordKind;
		this.itemCode = itemCode;
		this.length = length;
		this.scale = scale;
		this.parameterDM = parameterDM;
		this.type = type;
		if (align.equalsIgnoreCase(ALIGN_RIGHT)) {
			this.left = false;
		}
		//this.left = left;

// 2004/04/26 update takata
//		this.itemValue = preValue;
		this.preValue = preValue;
	}

	/**
	 * コンストラクタ
	 * @param fileKbn
	 * @param recordKind
	 * @param itemCode
	 * @param length
	 * @param scale
	 * @param parameterDM
	 * @param type
	 * @param left
	 * @param preValue
	 */
	public RecordContentsProperties(String fileKbn, String recordKind, String itemCode, int length, int scale, String parameterDM,
									String type, boolean left, String preValue) {
		this.fileKbn = fileKbn;
		this.recordKind = recordKind;
		this.itemCode = itemCode;
		this.length = length;
		this.scale = scale;
		this.parameterDM = parameterDM;
		this.type = type;
		this.left = left;
		this.left = left;

// 2004/04/26 update takata
//		this.itemValue = preValue;
		this.preValue = preValue;
	}

	/**
	 * 現在のRecordContentsPropertiesオブジェクトの各メンバ値を保持した新しいRecordContentsPropertiesオブジェクトインスタンスを返します。
	 * @return
	 */
	public RecordContentsProperties createClone() {
		return new RecordContentsProperties(fileKbn, recordKind, itemCode, length, scale, parameterDM, type, left, itemValue);
	}

	public String getItemCode() {
		return itemCode;
	}
	public String getItemValue() {
// 2004/04/26 add takata start
		if (this.itemValue == null) {
			return this.preValue;
		}
// 2004/04/26 add takata end
		return itemValue;
	}
	public int getLength() {
		return length;
	}
	public int getScale() {
		return scale;
	}
	public String getParameterDM() {
		return parameterDM;
	}
	public String getRecordKind() {
		return recordKind;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public String getFileKbn() {
		return fileKbn;
	}
	public String getType() {
		return type;
	}
	public boolean isLeft() {
		return left;
	}
}