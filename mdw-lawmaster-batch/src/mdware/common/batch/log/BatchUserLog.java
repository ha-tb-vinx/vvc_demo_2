package mdware.common.batch.log;
import mdware.common.batch.util.control.jobProperties.JobProperties;
import mdware.common.batch.util.control.jobProperties.Jobs;

import org.apache.log4j.PropertyConfigurator;

/**
 * <P>タイトル:  ユーザー用バッチ処理ログクラス</P>
 * <P>説明: ユーザーログを出力します。
 * <P>著作権:	 Copyright (c) 2009 Vinculum Japan Corporation</P>
 * <P>会社名:	 Vinculum Japan Corporation</P>
 * <PRE>
 * 
 * Usage:
 * １．ジョブID、ジョブ名、バッチクラス名を付加したログを出力する
 *    BatchUserLog userLog = BatchUserLog.getInstance();
 *    userLog.debug("TEST");
 *  
 *    ※ 注意：BatchLogと使用方法が違うので注意。
 *    (BatchLogは batchLog.getLog.debug("TEST"); で出力)
 *  
 *  
 *  ２．ジョブID等を付与せず、直接ログファイルへ出力する
 *    BatchUserLog userLog = BatchUserLog.getInstance();
 *    userLog.getLog().debug("TEST");
 *  
 *    ※ 注意：こちらがBatchLogと同じ使用方法。
 *
 * </PRE>
 * @author T.Yokoyama
 * @version 1.00
 */
public class BatchUserLog {
	private static boolean initialized = false;
	private static final BatchUserLog INSTANCE = new BatchUserLog();

	private String jobId = null;

	// Log4jのCategotyクラスの参照を宣言する。
	// カテゴリ毎に出力クラスを設定できないので、propertieファイルの
	// log4j.loggerFactory で設定されている CategoryForBatchLog
	private static CategoryForBatchLog appLog; 

	/**
	 * コンストラクタ。
	 * privateなので表に出ない形になる。
	 * シングルトンを実現する。
	 */
	private BatchUserLog() {
	}

	/**
	 * インスタンスの取得。
	 * @return UserLog インスタンスを返す。
	 */
	public static BatchUserLog getInstance() {
		return INSTANCE;
	}

	/**
	 * ログファイルへ直接出力するため、カテゴリを返す。
	 */
	public CategoryForBatchLog getLog() { //20030130 @UPD yamanaka
		if (!initialized) {
			System.err.println("BatchLogインスタンスに含まれるorg.apache.log4j.Categoryが初期化されていません。");
			return null;
		}
		return appLog;
	}

	/**
	 * UserLogの初期化を行う。
	 * シングルトンを使用しているので内部では一度のみ初期化される。
	 * batch内で利用するので、アプリケーションでは呼ばない！
	 * @param log4jInitFile String 初期化用ファイルのパス。
	 * @return boolean 初期化に成功したかを返す。
	 */
	public static boolean init(String log4jInitFile) {
		// 既に初期化されていた場合は初期化しない。
		if (initialized)
			return true;

		// もしlog4j-init-fileが正しく設定されていない場合は無視する。
		if( log4jInitFile != null ) {
			// log4j設定ファイル読み込む。
			System.out.println("UserLogはログを" + log4jInitFile + "で初期化を行います。");
			PropertyConfigurator.configure(log4jInitFile);

			// batchのカテゴリを生成する。（エラーの場合はreturn false）
			if((appLog = (CategoryForBatchLog)CategoryForBatchLog.getInstance("batch.user")) != null ) {
				initialized = true; // 初期化済みにセット
			}
			else {
				System.err.println("UserLogのロギング・システムは初期化ファイル\"" + log4jInitFile + "\"による初期化に失敗しました。");
				return false;
			}
		}
		else {
			// 初期化失敗
			System.err.println("UserLogのロギング・システムは初期化ファイルがnullの為、初期化に失敗しました。");

			return false;
		}

		appLog.debug(",UserLogは" + log4jInitFile + "で初期化に成功しました。");

		return true;
	}

	/* ************************* 出力メソッドのラッパ ************************* */

	public void fatal(Object a){
		appLog.fatal(_format(a));
	}

	public void error(Object a){
		appLog.error(_format(a));
	}

	public void warn(Object a){
		appLog.warn(_format(a));
	}

	public void info(Object a){
		appLog.info(_format(a));
	}

	public void debug(Object a){
		appLog.debug(_format(a));
	}

	/* ************************* Setter and Getter ************************* */

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/* ******************************************************** */
	
	/**
	 * ファイルに出力するため、メッセージのフォーマットを行う
	 * @param a 出力するメッセージ
	 * @return "クラス名,ジョブID ジョブ名: 引数のメッセージ"
	 */
	private String _format(Object a){
		StringBuffer sb = new StringBuffer();
		Jobs jobs = Jobs.getInstance();
		JobProperties jProp = jobs.get(jobId);

		sb.append(jProp.getClassName());
		sb.append(",");
		sb.append(jobId);
		sb.append(" ");
		sb.append(jProp.getJobName());
		sb.append(":");
		sb.append(a.toString());

		return sb.toString();
	}
	
}
