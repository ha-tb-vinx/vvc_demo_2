package mdware.master.batch.process.mb83;

import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: MB83-02-01 店別商品データ作成 - 店別商品展開処理</p>
 * <p>説明: TMP BAT商品マスタ、TMP BAT 単品店取扱いマスタを中心に、
 * 			店別商品情報に展開した、TMP 店別商品データを作成する。
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 *  @author S.JINNO
 *  @version 1.0 
 *  @version 1.1 2007/09/21 物流区分追加対応 JINNO
 */

public class MB830201_TenbetuSyohinTenkai {
	
	//各種宣言
	private DataBase db = null;											//DBアクセス用 オブジェクト(DataBaseクラスを継承）
	private BatchLog batchLog = BatchLog.getInstance();					//バッチログ出力用 オブジェクト
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private boolean closeDb = false;										//DBステータス管理用
	
	
	//バッチID & バッチ名
	private String BATCH_UID = "MB83-02-01";
	private static final String BATCH_NA  = "店別商品データ作成 - 店別商品展開処理";

	// システム日付
	private String sysDate = null;

	// 当日日付
	private String today = null;
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB830201_TenbetuSyohinTenkai(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase("rbsite_ora",true); // ジャーナル無し対応版
//			this.db = new DataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB830201_TenbetuSyohinTenkai() {
		this(new DataBase("rbsite_ora",true)); // ジャーナル無し対応版
//		this(new DataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		BATCH_UID = batchId;
		execute();
	}
	
	/**
	 *  主処理
	 *  @param 
	 *  @return 
	 *  @exception Exception
　	*/	
	
	public void execute() throws Exception {
		
		String jobId = userLog.getJobId();

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			
			//STEP1：システム日時　取得処理
			getDates();
		 
			//STEP2：TMP 店別商品データ一括削除処理
			truncateTmpM83TenbetuSyohin();
		 
			//STEP3：TMP 店別商品データ作成処理
			createTmpM83TenbetuSyohin();
			
		} catch (Exception e) {
			//エラー時は、ROLLBACKしてログ出力
			e.printStackTrace();	
			db.rollback();
			writeError(e);
			throw e;
			
		} finally {
			//DBアクセスクラス開放
			if (closeDb || db != null) {
				this.db.close();
			}
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}
	
	/**
	 *  STEP1：システム日時取得処理
	 *  @param 
	 *  @return 
	 *  @exception Exception
	 *  共通日付管理マスタより、システム日付を取得する処理
　	*/	
	private void getDates() throws Exception
	{	
		
		try {
			//開始ログ出力
			String LogInfo = "システム日時　取得処理";
			writeLog(Level.INFO_INT, LogInfo + "を開始します");
			
			// システム日付取得
			sysDate = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
			
			//開始ログ出力
			LogInfo = "本日日付　取得処理";
			writeLog(Level.INFO_INT, LogInfo + "を開始します");
			
			//本日日付取得
			today = RMSTDATEUtil.getBatDateDt();
			
			//本日日付ログ出力
			writeLog(Level.INFO_INT, "本日日付は" + today + "です");
			
			//終了ログ出力
			writeLog(Level.INFO_INT, LogInfo + "を終了します");

		} catch (Exception e) {
			//エラー時は、ROLLBACKしてログ出力
			e.printStackTrace();
			db.rollback();
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());	
			throw e;
		}
		
	}
	
	

	/**
	 *  STEP2：TMP 店別商品データ一括削除処理
	 *  @param 
	 *  @return 
	 *  @exception Exception
	 *  TMP 店別商品データを全件削除する。
　	*/
	private void truncateTmpM83TenbetuSyohin() throws Exception
	{	
		
		try {
			
			//開始ログ出力
			String LogInfo = "店別商品データ 一括削除処理";
			writeLog(Level.INFO_INT, LogInfo + "を開始します");
	
			//TRUNCATE処理
			DBUtil.truncateTable(db, "WK_MB83_TENBETU_SYOHIN");

			//削除ログ出力
			writeLog(Level.INFO_INT, "TMP 店別商品データを削除しました");						
			
			//終了ログ出力
			writeLog(Level.INFO_INT, LogInfo + "を終了します");		
			
		} catch (Exception e) {
			//エラー時は、呼び出し元にスロー
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());	
			throw e;
		}
	}
	
	/**
	 *  STEP3：TMP 店別商品データ作成処理
	 *  @param 
	 *  @return 
	 *  @exception Exception
	 *  TMP BAT 系各マスタから、 TMP 店別商品データを作成する。
　	*/	
	private void createTmpM83TenbetuSyohin() throws Exception
	{	
		
		try {
			
			//変数宣言
			int sqlCount = 0;
			String sql = null;
			
			//開始ログ出力
			writeLog(Level.INFO_INT, "TMP 店別商品データ　作成処理を開始します");
			
			// TMP 店別商品データ作成SQLセット
			sql = getInsertTmpM83TenbetuSyohin();
			
			//SQL実行
			sqlCount = db.executeUpdate(sql);
			db.commit();
	
			//結果ログ出力
			writeLog(Level.INFO_INT, "TMP 店別商品データを" + sqlCount + "件、作成しました。");
			
			//終了ログ出力
			writeLog(Level.INFO_INT, "TMP 店別商品データ　作成処理を終了します");	
			
		} catch (Exception e) {
			//エラー時は、呼び出し元にエラースロー
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());				
			throw e;
		}
	
	}
	
	/**
	 *  TMP 店別商品データ作成SQL
	 *  @param 
	 *  @return String 
	 *  @exception Exception
	 *  TMP 店別商品データ INSERT 用 SQLを出力する。
　	*/	
	private String getInsertTmpM83TenbetuSyohin() throws Exception
	{	
		
		//SQL組み立て
		StringBuffer sb = null;
		
		sb = new StringBuffer("");

		sb.append(" INSERT /*+ APPEND */ ");
		sb.append("   INTO WK_MB83_TENBETU_SYOHIN NOLOGGING ( ");											
		sb.append("        TENPO_CD,");										// 店舗
		sb.append("        BUNRUI1_CD, ");									// 分類１コード
		sb.append("        SYOHIN_CD, "); 									// 商品コード
		sb.append("        YUKO_DT, ");										// 有効日
		sb.append("        SIIRESAKI_CD, ");								// 仕入先コード
		sb.append("        GENTANKA_VL, ");									// 原価単価
		sb.append("        BAITANKA_VL, ");									// 売価単価
		sb.append("        BUTURYU_KB, ");									// 納品区分
		sb.append("        EOS_KB, ");										// EOS区分
		sb.append("        NON_ACT_KB,"); 									// NON-ACT区分
		sb.append("        HENKO_DT, ");									// 変更日
		sb.append("        TENPO_KB, ");									// 店舗区分
		sb.append("        SYOHIN_DELETE_FG, ");							// 削除フラグ
		sb.append("        KEIRYOKI_DELETE_FG, ");							// 計量器フラグ
		sb.append("        NAIBU_IF_FG ");									// 内部IF対象フラグ
		sb.append(" ) ");
		sb.append(" SELECT /*+ ORDERED USE_HASH(S T TEN) */ ");
		sb.append("        T.TENPO_CD,");									// 店舗
		sb.append("        S.BUNRUI1_CD, ");								// 分類１コード
		sb.append("        S.SYOHIN_CD, "); 								// 商品コード
		sb.append("        S.YUKO_DT, ");									// 有効日
		sb.append("        S.SIIRESAKI_CD, ");								// 仕入先コード
		sb.append("        S.GENTANKA_VL, ");								// 原価単価
		sb.append("        S.BAITANKA_VL, ");								// 売価単価
		sb.append("        S.BUTURYU_KB, ");								// 納品区分
		sb.append("        S.EOS_KB, ");									// EOS区分
		sb.append("        T.NON_ACT_KB,"); 								// NON-ACT区分
		sb.append("        S.HENKO_DT, ");									// 変更日
		sb.append("        TEN.TENPO_KB, ");								// 店舗区分
		sb.append("        S.DELETE_FG, ");									// 商品削除フラグ
		sb.append("        S.KEIRYOKI_DELETE_FG, ");						// 計量器削除フラグ
		sb.append("        S.NAIBU_IF_FG ");								// 内部IF対象フラグ
		sb.append("   FROM TMP_BAT_SYOHIN S ");
		sb.append("  INNER JOIN");
		sb.append("        TMP_R_TANPINTEN_TORIATUKAI T ");
		sb.append("     ON T.BUNRUI1_CD =  S.BUNRUI1_CD ");
		sb.append("    AND T.SYOHIN_CD  =  S.SYOHIN_CD");
		sb.append("    AND T.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("  INNER JOIN");
		sb.append("        TMP_R_TENPO TEN ");
		sb.append("     ON TEN.TENPO_CD     = T.TENPO_CD ");
		sb.append("    AND TEN.DELETE_FG    = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("    AND TEN.ZAIMU_END_DT > '" + today + "' ");
				
		return sb.toString();
	}


	/**
	 * ユーザーログとバッチログにログを出力します。
	 * 
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message){
		String jobId = userLog.getJobId();

		switch(level){
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
			
		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}
	
	/**
	 * エラーをログファイルに出力します。
	 * ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 * 
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました");
		} else {
			userLog.error("エラーが発生しました");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

	/**
	 * test実行用メイン関数
	 * @param args
	 */
	public static void main(String[] args) {
		String propertyDir = "defaultroot/WEB-INF/properties";
		String executeMode = "release";
		String databaseUse = "use";
		mdware.common.batch.util.control.BatchController controller =
			new mdware.common.batch.util.control.BatchController();
		controller.init(propertyDir, executeMode, databaseUse);
		MB830201_TenbetuSyohinTenkai batch = new MB830201_TenbetuSyohinTenkai();
		try {
			batch.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
