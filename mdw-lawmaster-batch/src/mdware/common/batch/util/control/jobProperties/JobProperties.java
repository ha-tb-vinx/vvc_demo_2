package mdware.common.batch.util.control.jobProperties;

/**
 * <P>タイトル:  STCライブラリ</P>
 * <P>説明: HTMLのJobIDに対応したＪＯＢ名とＣＬＡＳＳを保持する</P>
 * <P>著作権:	Copyright (c) 2001</P>
 * <P>会社名:	株式会社マイカルシステムズ</P>
 * <PRE>
 *
 * </PRE>
 * @author Nob
 * @version 1.0
 */

public class JobProperties {
	private String code;
	private String jobName;
	private String className;
	private String methodName;
	private Object[] args;

	/**
	 * コンストラクタ。
	 * ＪＯＢ名やクラスを保持する。
	 * @param code String
	 * @param jobName String
	 * @param className String
	 */
	public JobProperties(String code, String jobName, String className, String methodName, Object[] args) {
		this.code = code;
		this.jobName = jobName;
		this.className = className;
		this.methodName = methodName;
		this.args = args;
	}

	/**
	 * HTMLから渡されるJobIDを取得する。
	 * @return String
	 */
	public String getCode() { return code; }
	/**
	 * JobIDに対応したＪＯＢ名を取得する。
	 * @return String
	 */
	public String getJobName() { return jobName; }
	/**
	 * JobIDに対応したＣＬＡＳＳを取得する。
	 * @return String
	 */
	public String getClassName() { return className; }

	/**
	 * JobIDに対応したＭＥＴＨＯＤを取得する。
	 * @return
	 */
	public String getMethodName() { return methodName; }

	/**
	 * JobIDに対応したメソッド引数を取得する。
	 * @return
	 */
	public Object[] getArgs() { return args; }

	/**
	 * ObjectのtoString()をオーバーライドする。
	 * @return String
	 */
	public String toString() {
		String retString = "code: " + code + " jobName: " + jobName + " className: " + className + " methodName: " + methodName;
		for (int i = 1 ; i <= args.length ; i++) {
			retString += " args[" + i + "]: " + args[i - 1].toString();
		}
		return retString;
	}
}
