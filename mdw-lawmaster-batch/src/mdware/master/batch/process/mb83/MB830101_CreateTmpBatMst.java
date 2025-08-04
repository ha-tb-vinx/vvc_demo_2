package mdware.master.batch.process.mb83;

import java.sql.SQLException;
import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.master.batch.common.util.MBBatch_CommonUtility;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst010501_NaibuIFFgDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;

/**
 * <p>タイトル: MB83-01-01 店別商品データ作成 - TMP BATマスタ作成処理</p>
 * <p>説明: 店別商品データを作成する前処理として、TMP R 系マスタより
 * 			店別商品データ作成に必要なデータのみを抽出したTMP BAT系マスタを
 * 			作成する処理です。
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 *  @author S.JINNO
 *  @version 1.0
 *  @version 1.1 2007/09/21 物流区分追加対応 JINNO
 */

public class MB830101_CreateTmpBatMst {

	//各種宣言
	private DataBase db = null;													//DBアクセス用 オブジェクト(DataBaseクラスを継承）
	private BatchLog batchLog = BatchLog.getInstance();							//バッチログ出力用 オブジェクト
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private boolean closeDb = false;												//DBステータス管理用
	private MBBatch_CommonUtility commonUtil = new MBBatch_CommonUtility();		//共通処理用オブジェクト


	//バッチID & バッチ名
	private String BATCH_UID = "MB83-01-01";
	private static final String BATCH_NA  = "店別商品データ作成 - TMP BATマスタ作成処理";

	// 当日日付
	private String today = null;

	// 使用 TMP BAT テーブル名一覧
	private String[][] tmpBatTableNames = 	{
												{"TMP_BAT_SYOHIN",					"TMP BAT 商品マスタ"},
//												{"TMP_BAT_TANPINTEN_TORIATUKAI",	"TMP BAT 単品店取扱いマスタ"},
//												{"TMP_BAT_TENPO",					"TMP BAT 店舗マスタ"},
//												{"TMP_BAT_TENBETU_SIIRESAKI",		"TMP BAT 店別仕入先マスタ"},
												{"TMP_BAT_TENSYOHIN_REIGAI",		"TMP BAT 店別商品例外マスタ"}
											};

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB830101_CreateTmpBatMst(DataBase db) {
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
	public MB830101_CreateTmpBatMst() {
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

			//STEP1：本日日付＆システム日時　取得処理
			getDates();

			//STEP2：TMP BAT系マスタ一括削除処理
			truncateTmpBatMst(tmpBatTableNames);

			//STEP3：TMP BAT マスタ作成処理
			createTmpBatMst();

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
			//共通クラス内のDBアクセスクラスも閉じておく。
			if (commonUtil != null) {
				this.commonUtil.close();
			}
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 *  STEP1：本日日付取得処理
	 *  @param
	 *  @return
	 *  @exception Exception
	 *  共通日付管理マスタより、本日日付とシステム日付を取得する処理
　	*/
	private void getDates() throws Exception
	{

		try {
			//開始ログ出力
			String LogInfo = "本日日付　取得処理";
			writeLog(Level.INFO_INT, LogInfo + "を開始します");

			//本日日付取得
			today = RMSTDATEUtil.getBatDateDt();

			//本日日付ログ出力
			writeLog(Level.INFO_INT,  "本日日付は" + today + "です");

			//終了ログ出力
			writeLog(Level.INFO_INT,  LogInfo + "を終了します");

		} catch (Exception e) {
			//エラー時は、ROLLBACKしてログ出力
			e.printStackTrace();
			db.rollback();
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());
			throw e;
		}

	}



	/**
	 *  STEP2：TMP BAT系マスタ一括削除処理
	 *  @param
	 *  @return
	 *  @exception Exception
	 *  TMP BAT系マスタ全てを全件削除する。
　	*/
	private void truncateTmpBatMst(String[][] tmpBatTableNames) throws Exception
	{

		try {

            //開始ログ出力
			String LogInfo = "TMP BAT系マスタ一括削除処理";
			writeLog(Level.INFO_INT,  LogInfo + "を開始します");


			//TMP BATテーブル数分繰り返す。
			for(int i = 0; i < tmpBatTableNames.length; i++){

				//TRUNCATE処理
				DBUtil.truncateTable(db, tmpBatTableNames[i][0]);

				//削除ログ出力
				writeLog(Level.INFO_INT,  tmpBatTableNames[i][1] + "を削除しました");
			}


			//終了ログ出力
			writeLog(Level.INFO_INT,  LogInfo + "を終了します");

		} catch (Exception e) {
			//エラー時は、ROLLBACKしてログ出力
			db.rollback();
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());
			throw e;
		}
	}

	/**
	 *  STEP3：TMP BAT マスタ作成処理
	 *  @param
	 *  @return
	 *  @exception Exception
	 *  TMP R 系マスタから、 TMP BAT 系マスタを作成する。
　	*/
	private void createTmpBatMst() throws Exception
	{

		try {

			//変数宣言
			String LogInfo = null;

			//開始ログ出力
			LogInfo = "TMP BAT 系マスタ　作成処理を開始します";
			writeLog(Level.INFO_INT,  LogInfo);

			// TMP BAT 商品マスタ作成
			createTmpBatSyohinMst();

			//	TMP BAT 店別例外マスタ作成
			createTmpBatTenbetuReigaiMst();

			//終了ログ出力
			LogInfo = "TMP BAT 系マスタ　作成処理を終了します";
			writeLog(Level.INFO_INT,  LogInfo);

		} catch (Exception e) {
			//エラー時は、呼び出し元にエラースロー
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());
			throw e;
		}

	}

	/**
	 *  TMP BAT商品マスタ作成処理
	 *  @param
	 *  @return String
	 *  @exception Exception
	 *  TMP R 商品マスタから、TMP BAT 商品マスタを作成する。
　	*/
	private void createTmpBatSyohinMst() throws Exception
	{

		try {
				//変数宣言
				int sqlCount = 0;
				String LogInfo = null;

				//SQL組み立て
				StringBuffer sb = null;


				sb = new StringBuffer("");

				sb.append("INSERT /*+ APPEND */ INTO TMP_BAT_SYOHIN NOLOGGING ");
				sb.append("( ");
				sb.append("       BUNRUI1_CD, ");
				sb.append("       SYOHIN_CD, ");
				sb.append("       YUKO_DT, ");
				sb.append("       GENTANKA_VL, ");
				sb.append("       BAITANKA_VL, ");
				sb.append("       SIIRESAKI_CD, ");
				sb.append("       EOS_KB, ");
				sb.append("       BUTURYU_KB, ");
				sb.append("       HENKO_DT, ");
				sb.append("       DELETE_FG, ");
				sb.append("       KEIRYOKI_DELETE_FG, ");
				sb.append("       NAIBU_IF_FG ");
				sb.append(") ");
				sb.append("SELECT S.BUNRUI1_CD, ");
				sb.append("       S.SYOHIN_CD, ");
				sb.append("       S.YUKO_DT, ");
				sb.append("       S.GENTANKA_VL, ");
				sb.append("       S.BAITANKA_VL, ");
				sb.append("       S.SIIRESAKI_CD, ");
				sb.append("       S.EOS_KB, ");
				sb.append("       S.BUTURYU_KB, ");
				sb.append("       S.HENKO_DT, ");
				sb.append("       S.DELETE_FG, ");
				sb.append("       K.DELETE_FG, ");
				sb.append("       CASE WHEN S.YUKO_DT > '" + today + "' THEN '" + mst010501_NaibuIFFgDictionary.TAISYO.getCode() + "' ");
				sb.append("            WHEN S.YUKO_DT = S.CHK_YUKO_DT THEN '" + mst010501_NaibuIFFgDictionary.TAISYO.getCode() + "' ");
				sb.append("            ELSE '" + mst010501_NaibuIFFgDictionary.TAISYOGAI.getCode() + "' ");
				sb.append("       END AS NAIBU_IF_FG ");
				sb.append("  FROM (SELECT /*+ NO_MERGE */ ");
				sb.append("               BUNRUI1_CD, ");
				sb.append("               SYOHIN_CD, ");
				sb.append("               YUKO_DT, ");
				sb.append("               GENTANKA_VL, ");
				sb.append("               BAITANKA_VL, ");
				sb.append("               SIIRESAKI_CD, ");
				sb.append("               EOS_KB, ");
				sb.append("               BUTURYU_KB, ");
				sb.append("               HENKO_DT, ");
				sb.append("               DELETE_FG, ");
				sb.append("               (SELECT MAX(YUKO_DT) ");
				sb.append("                  FROM TMP_R_SYOHIN ");
				// #6620 DEL 2022.06.30 SIEU.D(S)
				// sb.append("                 WHERE BUNRUI1_CD = W.BUNRUI1_CD ");
				// sb.append("                   AND SYOHIN_CD  = W.SYOHIN_CD ");
				sb.append("                 WHERE SYOHIN_CD  = W.SYOHIN_CD ");
				// #6620 DEL 2022.06.30 SIEU.D(E)
				sb.append("                   AND YUKO_DT   <= '" + DateChanger.addDate(today, 1) + "'");
				sb.append("               ) AS CHK_YUKO_DT ");
				sb.append("          FROM TMP_R_SYOHIN W ");
				sb.append("         WHERE YUKO_DT >= '" + DateChanger.addDate(today, -3) + "'");
				sb.append("            OR ( ");
				sb.append("                   DELETE_FG = '" +  mst000801_DelFlagDictionary.INAI.getCode() + "' ");
				sb.append("                   AND ");
				sb.append("                   YUKO_DT = ");
				sb.append("                       (SELECT MAX(YUKO_DT) ");
				sb.append("                          FROM TMP_R_SYOHIN ");
				// #6620 DEL 2022.06.30 SIEU.D(S)
				// sb.append("                         WHERE BUNRUI1_CD = W.BUNRUI1_CD ");
				// sb.append("                           AND SYOHIN_CD  = W.SYOHIN_CD ");
				sb.append("                         WHERE SYOHIN_CD  = W.SYOHIN_CD ");
				// #6620 DEL 2022.06.30 SIEU.D(E)
				sb.append("                           AND YUKO_DT   <= '" + DateChanger.addDate(today, -3) + "'");
				sb.append("                       )");
				sb.append("               )");
				sb.append("        ) S ");
				sb.append("  LEFT JOIN ");
				sb.append("       TMP_R_KEIRYOKI K ");
				sb.append("    ON K.BUNRUI1_CD = S.BUNRUI1_CD ");
				sb.append("   AND K.SYOHIN_CD  = S.SYOHIN_CD ");
				sb.append("   AND K.YUKO_DT    = S.YUKO_DT ");

				//SQL実行
				sqlCount = db.executeUpdate(sb.toString());
				db.commit();

				//終了ログ出力
				LogInfo = "TMP BAT 商品マスタを" + sqlCount + "件、作成しました。";
				writeLog(Level.INFO_INT,  LogInfo);


		} catch (Exception e) {
			//エラー時は、呼び出し元にエラースロー
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());
			throw e;
		}

	}


	/**
	 *  TMP BAT店別例外マスタ作成処理
	 *  @param
	 *  @return String
	 *  @exception Exception
	 *  TMP R 店別例外マスタから、TMP BAT 店別例外マスタを作成する。
　	*/
	private void createTmpBatTenbetuReigaiMst() throws Exception
	{

		try {

				//変数宣言
				int sqlCount = 0;
				String LogInfo = null;
				String DelFgInai = mst000801_DelFlagDictionary.INAI.getCode();

				//SQL組み立て
				StringBuffer sb = null;

				sb = new StringBuffer("");

				sb.append("INSERT /*+ APPEND */ INTO TMP_BAT_TENSYOHIN_REIGAI NOLOGGING ");
				sb.append("( ");
				sb.append("       BUNRUI1_CD, ");
				sb.append("       SYOHIN_CD, ");
				sb.append("       TENPO_CD, ");
				sb.append("       YUKO_DT, ");
				sb.append("       GENTANKA_VL, ");
				sb.append("       BAITANKA_VL, ");
				sb.append("       SIIRESAKI_CD, ");
				sb.append("       BUTURYU_KB, ");
				sb.append("       EOS_KB, ");
				sb.append("       HENKO_DT, ");
				sb.append("       DELETE_FG, ");
				sb.append("       SYOHIN_YUKO_DT ");
				sb.append(") ");
				sb.append("SELECT BUNRUI1_CD, ");
				sb.append("       SYOHIN_CD, ");
				sb.append("       TENPO_CD, ");
				sb.append("       YUKO_DT, ");
				sb.append("       CASE WHEN DELETE_FG = '" + DelFgInai + "' THEN GENTANKA_VL  ELSE NULL END AS GENTANKA_VL, ");
				sb.append("       CASE WHEN DELETE_FG = '" + DelFgInai + "' THEN BAITANKA_VL  ELSE NULL END AS BAITANKA_VL, ");
				sb.append("       CASE WHEN DELETE_FG = '" + DelFgInai + "' THEN SIIRESAKI_CD ELSE NULL END AS SIIRESAKI_CD, ");
				sb.append("       CASE WHEN DELETE_FG = '" + DelFgInai + "' THEN BUTURYU_KB   ELSE NULL END AS BUTURYU_KB, ");
				sb.append("       CASE WHEN DELETE_FG = '" + DelFgInai + "' THEN EOS_KB       ELSE NULL END AS EOS_KB, ");
				sb.append("       HENKO_DT, ");
				sb.append("       DELETE_FG, ");
				sb.append("       (SELECT MAX(YUKO_DT) ");
				sb.append("          FROM TMP_R_SYOHIN ");
				sb.append("         WHERE BUNRUI1_CD = R.BUNRUI1_CD ");
				sb.append("           AND SYOHIN_CD  = R.SYOHIN_CD ");
				sb.append("           AND YUKO_DT   <= R.YUKO_DT ");
				sb.append("       ) AS SYOHIN_YUKO_DT ");
				sb.append("  FROM TMP_R_TENSYOHIN_REIGAI R ");
				sb.append(" WHERE YUKO_DT > '" + DateChanger.addDate(today, -3) + "' ");
				sb.append("    OR YUKO_DT = ");
				sb.append("           (SELECT MAX(YUKO_DT) ");
				sb.append("              FROM TMP_R_TENSYOHIN_REIGAI ");
				sb.append("             WHERE BUNRUI1_CD  = R.BUNRUI1_CD ");
				sb.append("               AND SYOHIN_CD   = R.SYOHIN_CD ");
				sb.append("               AND TENPO_CD    = R.TENPO_CD ");
				sb.append("               AND YUKO_DT    <= '" + DateChanger.addDate(today, -3) + "' ");
				sb.append("           )");

				//SQL実行
				sqlCount = db.executeUpdate(sb.toString());
				db.commit();

				//終了ログ出力
				LogInfo = "TMP BAT 店別商品例外マスタを" + sqlCount + "件、作成しました。";
				writeLog(Level.INFO_INT,  LogInfo);


		} catch (Exception e) {
			//エラー時は、呼び出し元にエラースロー
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());
			throw e;
		}

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
		MB830101_CreateTmpBatMst batch = new MB830101_CreateTmpBatMst();
		try {
			batch.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
