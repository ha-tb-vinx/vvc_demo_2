package mdware.common.batch.log;
import mdware.common.batch.log.CategoryForBatchLog;
import mdware.batch.mail.control.BatchMailController;

import org.apache.log4j.PropertyConfigurator;

/**
 * <P>タイトル:  バッチ処理用ログシステム（STCライブラリから流用）</P>
 * <P>説明: RB Siteバッチ処理共通のＬＯＧ出力を行います。</P>
 * <P>著作権:	 Copyright (c) 2001 Vinculum Japan Corporation</P>
 * <P>会社名:	 Vinculum Japan Corporation</P>
 * <PRE>
 *
 * </PRE>
 * @author Nob
 * @version 1.00
 * @version 2.00 (2004.05.25) 内部改修書No.004対応 yunba
 */

public class BatchLog {
	private static boolean initialized = false;
	private static final BatchLog INSTANCE = new BatchLog();

	// Log4jのCategotyクラスの参照を宣言する。

//	private static Category appLog;
	private static CategoryForBatchLog appLog; //20030130 @UPD yamanaka

	/**
	 * コンストラクタ。
	 * privateなので表に出ない形になる。
	 * シングルトンを実現する。
	 */
	private BatchLog() {
	}

	/**
	 * シングルトンであるBatchLogインスタンスの取得。
	 * 必ずクラスのメンバとして使用する。
	 * public static BatchLog BatchLog = BatchLog.getInstance();
	 * @return BatchLog このクラスをインスタンス化して返す。
	 */
	public static BatchLog getInstance() {
		return INSTANCE;
	}

	/**
	 * アプリケーションログに対して書き込みを行うCategoryを返す。
	 * アプリケーション情報を残したいときに使用する。
	 * 通常アプリケーションでログを残したいときは、このメソッドを使用しgetSysLogは使用しない。
	 * 使用例：BatchLog.getLog().info( "？？？を処理中です。");
	 * @return Category
	 */
//	public Category getLog() {
	public CategoryForBatchLog getLog() { //20030130 @UPD yamanaka

		// とりあえずcategoryが初期化されていなければ、nullを返す。
		//　ただ、ライブラリとしてデフォルトの初期化はBatchLogのインスタンス化時に行うべき。
		// そのあとに、ユーザにユーザ独自のCategoryインスタンスを生成させるべき。
		// とりあえず未実装。NullPointerExceptionの危険性あり。要改善！！！
		if (!initialized) {
			System.err.println("BatchLogインスタンスに含まれるorg.apache.log4j.Categoryが初期化されていません。");
			return null;
		}

		return appLog;
	}

	/**
	 * BatchLogの初期化を行う。
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
			System.out.println("batchはログを" + log4jInitFile + "で初期化を行います。");
			PropertyConfigurator.configure(log4jInitFile);

			// batchのカテゴリ群を生成する。（エラーの場合はreturn false）
			//if((appLog = Category.getInstance("batch.app")) != null ) {
			//if((appLog = CategoryForBatchLog.getInstance("batch.app")) != null ) { //20030130 @UPD yamanaka
			if((appLog = (CategoryForBatchLog)CategoryForBatchLog.getInstance("batch.app")) != null ) { //20031017 @UPD takata
///// 20040423 yunba add start ///// 内部改修書No.004対応
				// ロードされたlogファイルがbatchAppの場合のみメール送信オブジェクトを作成。
				//System.out.println("ここでappLog.setMailController(MailController.getInstance();を呼んでXMLデータを取得。");	// コメントアウト対象
				try {
					appLog.setMailController(BatchMailController.getInstance());
				} catch(Exception e) {
					System.err.println("エラーメールのデータ取得でエラーが発生しました。");	// コメントアウト対象
				}
///// 20040423 yunba add end   /////
				initialized = true; // 初期化済みにセット
			}
			else {
				System.err.println("batchのロギング・システムは初期化ファイル\"" + log4jInitFile + "\"による初期化に失敗しました。");
				return false;
			}
		}
		else {
			// 初期化失敗
			System.err.println("batchのロギング・システムは初期化ファイルがnullの為、初期化に失敗しました。");

			return false;
		}

		appLog.debug("batchLogは" + log4jInitFile + "で初期化に成功しました。");

		return true;
	}
}
