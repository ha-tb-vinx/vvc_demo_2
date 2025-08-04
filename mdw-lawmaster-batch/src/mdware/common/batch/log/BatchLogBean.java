package mdware.common.batch.log;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002 Vinculum Japan Corporation</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author takata
 * @version 1.0
 */

public class BatchLogBean {

    private String path;
	private String logDate;
	private String logStatus;
	private String logClassName;
	private String logMethod;
	private String logMessage;
    private String num;         // 20030116 @ADD for v2 A.Tashiro

	/**
	 * コンストラクタ
	*/
	public BatchLogBean() {
	}

	public String getDate() {
		return logDate;
	}

	public String getStatus() {
		return logStatus;
	}

	public String getClassName() {
		return logClassName;
	}

	public String getMethod() {
		return logMethod;
	}

	public String getMessage() {
		return logMessage;
	}
// 20030116 @ADD start A.Tasihro
 	public String getNum() {
		return num;
	}

    public void setNum(String num) {
        this.num = num;
	}
// 20030116 @ADD end
	public void setRecord(String record) {
		int fromIndex = 0;
		int index = 0;
		String date = "";
		// 日時取得
		index = record.indexOf(",");
		if (index < 0) {
			return;
		}
		logDate = record.substring(fromIndex, index);

		// ステータス取得
		fromIndex = ++index;
		index = record.indexOf(",", fromIndex);
		if (index < 0) {
			return;
		}
		logStatus = record.substring(fromIndex, index);

		// クラス名取得
		fromIndex = ++index;
		index = record.indexOf(",", fromIndex);
		if (index < 0) {
			return;
		}
		logClassName = record.substring(fromIndex, index);

		// メソッド名取得
		fromIndex = ++index;
		index = record.indexOf(",", fromIndex);
		if (index < 0) {
			return;
		}
		logMethod = record.substring(fromIndex, index);

		// メッセージ取得
		fromIndex = ++index;
		logMessage = record.substring(fromIndex);

	}
}
