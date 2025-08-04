package mdware.master.batch.process.mb38;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Level;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;
import mdware.master.util.RMSTDATEUtil;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.dictionary.EosKbDictionary;
import mdware.common.dictionary.ToriatsukaiKbDictionary;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;

/**
 * <p>タイトル:店別例外マスタ自動生成</p>
 * <p>説明:店別例外マスタ自動生成を行います。  </p>
 * <p>著作権: Copyright (c) 2010</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2010/08/24<BR>
 * @version 1.01 2010/09/15 S.Hashiguchi 店舗マスタの閉店・店舗区分の考慮を追加<BR>
 * @version 1.02 2021/12/20 KHOI.ND #6406
 * @author Y.IMAI
 */
 
public class MB380C08_CreateReigaiAuto {
	
	private boolean closeDb 		= false;

	// DataBaseクラス
	private MasterDataBase db	= null;
	
	// バッチ日付
	String MasterDT = RMSTDATEUtil.getMstDateDt();

	// ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private static final String BATCH_ID = "MB38-0C-08";
	private static final String BATCH_NA = "店別例外自動作成";
	private String batchID = "";
	
	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		batchID = batchId;
		execute();
	}
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB380C08_CreateReigaiAuto(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB380C08_CreateReigaiAuto() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		String jobId = userLog.getJobId();
		int cnt = 0; 
		
		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");

            // 店別例外マスタ作成
			cnt = db.executeUpdate(insReigaiAutoSql());

			writeLog(Level.INFO_INT, cnt + "件の店別例外マスタを登録しました");
			
			db.commit();

		// SQLエラーが発生した場合の処理
		} catch(SQLException se) {
			db.rollback();
			writeError(se);
			throw se;

		// その他のエラーが発生した場合の処理
		} catch(Exception e) {
			db.rollback();
			writeError(e);
			throw e;

		// SQL終了処理
		} finally {
			db.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 * 本日登録された新規商品を取得するSQL文を生成<br>
	 * 
	 * @param なし
	 * @return 検索用SQL文
	 */
	private String insReigaiAutoSql() {

		StringBuffer sb = new StringBuffer();
		
		// 2010.09.15 S.Hashiguchi Add 店舗マスタ考慮追加 Start
		Map tenpoKbMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.TANPINTEN_CREATE_TENPO_KB);	
		Map tenpoCdMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.TANPINTEN_CREATE_TENPO_CD);	
		// 2010.09.15 S.Hashiguchi Add End

		sb.append("INSERT INTO ");
		sb.append("	R_TENSYOHIN_REIGAI");
		sb.append("	( ");
		sb.append("		 BUNRUI1_CD ");
		sb.append("		,SYOHIN_CD ");
		sb.append("		,TENPO_CD ");
		sb.append("		,YUKO_DT ");
		sb.append("		,TEN_HACHU_ST_DT ");
		sb.append("		,TEN_HACHU_ED_DT ");
		sb.append("		,GENTANKA_VL ");
		sb.append("		,BAITANKA_VL ");
		sb.append("		,MAX_HACHUTANI_QT ");
		sb.append("		,EOS_KB ");
		sb.append("		,SIIRESAKI_CD ");
		sb.append("		,TENBETU_HAISO_CD ");
		sb.append("		,BIN_1_KB ");
		sb.append("		,HACHU_PATTERN_1_KB ");
		sb.append("		,SIME_TIME_1_QT ");
		sb.append("		,C_NOHIN_RTIME_1_KB ");
		sb.append("		,TEN_NOHIN_RTIME_1_KB ");
		sb.append("		,TEN_NOHIN_TIME_1_KB ");
		sb.append("		,BIN_2_KB ");
		sb.append("		,HACHU_PATTERN_2_KB ");
		sb.append("		,SIME_TIME_2_QT ");
		sb.append("		,C_NOHIN_RTIME_2_KB ");
		sb.append("		,TEN_NOHIN_RTIME_2_KB ");
		sb.append("		,TEN_NOHIN_TIME_2_KB ");
		sb.append("		,BIN_3_KB ");
		sb.append("		,HACHU_PATTERN_3_KB ");
		sb.append("		,SIME_TIME_3_QT ");
		sb.append("		,C_NOHIN_RTIME_3_KB ");
		sb.append("		,TEN_NOHIN_RTIME_3_KB ");
		sb.append("		,TEN_NOHIN_TIME_3_KB ");
		sb.append("		,C_NOHIN_RTIME_KB ");
		sb.append("		,SYOHIN_KB ");
		sb.append("		,BUTURYU_KB ");
		sb.append("		,TEN_ZAIKO_KB ");
		sb.append("		,YUSEN_BIN_KB ");
		sb.append("		,PLU_SEND_DT ");
		sb.append("		,HENKO_DT ");
		sb.append("		,INSERT_TS ");
		sb.append("		,INSERT_USER_ID ");
		sb.append("		,UPDATE_TS ");
		sb.append("		,UPDATE_USER_ID ");
		sb.append("		,BATCH_UPDATE_TS ");
		sb.append("		,BATCH_UPDATE_ID ");
		sb.append("		,DELETE_FG ");
		sb.append("		,SINKI_TOROKU_DT ");
		sb.append("	) ");
		sb.append("SELECT  ");
		sb.append("	 SYO.BUNRUI1_CD ");
		sb.append("	,SYO.SYOHIN_CD ");
		sb.append("	,DPT.TENPO_CD ");
		sb.append("	,SYO.YUKO_DT ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,'" + EosKbDictionary.TAISYOGAI.getCode() + "' ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,NULL ");
		sb.append("	,'99999999' ");
		sb.append("	,'" + MasterDT + "' ");
		sb.append("	,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ");
		sb.append("	,'" + BATCH_ID + "' ");
		sb.append("	,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ");
		sb.append("	,'" + BATCH_ID + "' ");
		sb.append("	,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ");
		sb.append("	,'" + BATCH_ID + "' ");
		sb.append("	,'" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("	,'" + MasterDT + "' ");
		sb.append("FROM ");
		sb.append("	( ");
		sb.append("		SELECT ");
		sb.append("			 BUNRUI1_CD ");
		sb.append(" 		,YUKO_DT ");
		sb.append("			,SYOHIN_CD ");
		sb.append("		FROM  ");
		sb.append("			R_SYOHIN ");
		sb.append("		WHERE ");
		sb.append("			SINKI_TOROKU_DT =  '" + MasterDT + "'");
		sb.append("		AND ");
		sb.append("			DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  ");
		sb.append("	) SYO ");
		sb.append("	INNER JOIN  ");
		sb.append("		( ");
		sb.append("			SELECT ");
		sb.append("				 RTB1.TENPO_CD ");
		sb.append("				,RTB1.BUNRUI1_CD ");
		sb.append("				,RTB1.YUKO_DT ");
		sb.append("			FROM ");
		sb.append("			( ");
		sb.append("				SELECT ");
		sb.append("					 TENPO_CD");
		sb.append("					,BUNRUI1_CD");
		sb.append("					,YUKO_DT ");
		sb.append("				FROM ");
		sb.append("					R_TENPO_BUNRUI1 RTB ");
		sb.append("				WHERE ");
		sb.append("					YUKO_DT = ( ");
		sb.append("									SELECT ");
		sb.append("										MAX(SUB.YUKO_DT) ");
		sb.append("									FROM ");
		sb.append("										R_TENPO_BUNRUI1 SUB ");
		sb.append("									WHERE ");
		sb.append("										RTB.TENPO_CD = SUB.TENPO_CD ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sb.append("									AND ");
//		sb.append("										RTB.BUNRUI1_CD = SUB.BUNRUI1_CD ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sb.append("									AND ");
		// 2010.09.15 S.Hashiguchi Mod Start
		//sb.append("										SUB.YUKO_DT <= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sb.append("										SUB.YUKO_DT <= '" + MasterDT + "' ");
		// 2010.09.15 S.Hashiguchi Mod End
		sb.append("									) ");
		sb.append("				AND ");
		sb.append("					DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("				AND ");
		sb.append("					TORIATSUKAI_KB = '" + ToriatsukaiKbDictionary.TORIATSUKAI_ARI.getCode() + "' ");
		sb.append("			) RTB1 ");
		sb.append("			INNER JOIN  ");
		sb.append("				R_TENANT_DPT_MATCHING RTDM ");
		sb.append("			ON  ");
		sb.append("				RTB1.BUNRUI1_CD = RTDM.BUNRUI1_CD ");
		sb.append("			INNER JOIN ");
		sb.append("			( ");
		sb.append("				SELECT ");
		sb.append("					 TENPO_CD");
		sb.append("					,BUNRUI1_CD");
		sb.append("					,YUKO_DT ");
		sb.append("				FROM ");
		sb.append("					R_TENPO_BUNRUI1 RTB ");
		sb.append("				WHERE ");
		sb.append("					YUKO_DT = ( ");
		sb.append("									SELECT ");
		sb.append("										MAX(SUB.YUKO_DT) ");
		sb.append("									FROM ");
		sb.append("										R_TENPO_BUNRUI1 SUB ");
		sb.append("									WHERE ");
		sb.append("										RTB.TENPO_CD = SUB.TENPO_CD ");
		// #6620 DEL 2022.11.18 VU.TD (S)
		sb.append("									AND ");
		sb.append("										RTB.BUNRUI1_CD = SUB.BUNRUI1_CD ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sb.append("									AND ");
		// 2010.09.15 S.Hashiguchi Mod Start
		//sb.append("										SUB.YUKO_DT <= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sb.append("										SUB.YUKO_DT <= '" + MasterDT + "' ");
		// 2010.09.15 S.Hashiguchi Mod End
		sb.append("									) ");
		sb.append("				AND ");
		sb.append("					DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("				AND ");
		sb.append("					TORIATSUKAI_KB = '" + ToriatsukaiKbDictionary.TORIATSUKAI_ARI.getCode() + "' ");
		sb.append("			) RTB2 ");
		sb.append("			ON ");
		sb.append("				RTDM.BUNRUI1_CD_TENANT = RTB2.BUNRUI1_CD ");
		sb.append("			AND ");
		sb.append("				RTB1.TENPO_CD = RTB2.TENPO_CD ");
		sb.append("		) DPT ");
		sb.append("	ON ");
		sb.append("		SYO.BUNRUI1_CD = DPT.BUNRUI1_CD ");
		// 2010.09.15 S.Hashiguchi Add 店舗マスタ考慮追加 Start
		sb.append(" INNER JOIN ");
		sb.append("		( ");
		sb.append("			SELECT ");
		sb.append("				TENPO_CD ");
		sb.append("			FROM ");
		sb.append("				R_TENPO ");
		sb.append("			WHERE ");
		sb.append("				( ");
		// #6406 Del 2021.12.20 KHOI.ND (S)
		// sb.append("					TENPO_KB IN (" + SqlSupportUtil.createInString(tenpoKbMap.keySet().toArray()) + ") ");
		// sb.append("					OR ");
		// #6406 Del 2021.12.20 KHOI.ND (E)
		sb.append("					TENPO_CD IN (" + SqlSupportUtil.createInString(tenpoCdMap.keySet().toArray()) + ") ");
		sb.append("				) ");
		sb.append("			AND COALESCE(HEITEN_DT, '99999999') >= '" + MasterDT + "' ");
		sb.append("			AND DELETE_FG =  '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("		) RT ");
		sb.append("	ON ");
		sb.append("		DPT.TENPO_CD = RT.TENPO_CD ");
		// 2010.09.15 S.Hashiguchi Add End
		sb.append("WHERE ");
		sb.append("	NOT EXISTS");
		sb.append("		( ");
		sb.append("			SELECT ");
		sb.append("				* ");
		sb.append("			FROM ");
		sb.append("				R_TENSYOHIN_REIGAI RTR");
		sb.append("			WHERE ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sb.append("				RTR.BUNRUI1_CD = SYO.BUNRUI1_CD ");
//		sb.append("			AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sb.append("				RTR.SYOHIN_CD = SYO.SYOHIN_CD ");
		sb.append("			AND ");
		sb.append("				RTR.TENPO_CD = DPT.TENPO_CD ");
		sb.append("			AND ");
		sb.append("				RTR.YUKO_DT = SYO.YUKO_DT ");
		sb.append("		) ");

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
}
