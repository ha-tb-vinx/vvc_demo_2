package mdware.master.batch.process.mbB3;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:不稼働商品出力用の不稼働商品ワーク作成処理</p>
 * <p>説明:不稼働店舗がある商品の商品マスタを作成。
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2012/06/20<BR>
 * @author Y.Imai
 */

public class MBB30201_SyohinDataCreate {
	private boolean closeDb 		= false;

	//DataBaseクラス
	private MasterDataBase db	= null;
	
	// バッチ日付
	private String batchDt = RMSTDATEUtil.getMstDateDt();
	// システム日付
	private String systemDt = "";

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private static final String BATCH_ID = "MBB3-02-01";
	private static final String BATCH_NA = "不稼働商品ワーク作成処理";
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
	public MBB30201_SyohinDataCreate(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBB30201_SyohinDataCreate() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}


	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		
		String jobId = userLog.getJobId();
		
		// システム日付取得
		systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );

		int tanpinCnt = 0;
		int syohinCnt = 0;
		
		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "を開始します。");
            
        	PreparedStatement psDelWkFukadoTanpin = db.getPrepareStatement(getDelWkFukadoTanpinSQL());		
        	PreparedStatement psInsWkFukadoTanpin = db.getPrepareStatement(getInsWkFukadoTanpinSQL());
        	PreparedStatement psDelWkFukadoSyohin = db.getPrepareStatement(getDelWkFukadoSyohinSQL());		
        	PreparedStatement psInsWkFukadoSyohin = db.getPrepareStatement(getInsWkFukadoSyohinSQL());	
        	
        	// 不稼働単品ワーク削除
			psDelWkFukadoTanpin.executeUpdate();
			
			// 不稼働単品ワーク作成
			tanpinCnt += psInsWkFukadoTanpin.executeUpdate();
			db.commit();
			
			psDelWkFukadoTanpin.close();
			psInsWkFukadoTanpin.close();
			writeLog(Level.INFO_INT, tanpinCnt + "件の不稼働単品ワークを登録しました。");
			
			// 不稼働商品ワーク削除
			psDelWkFukadoSyohin.executeUpdate();
			
			// 不稼働商品ワーク作成
			syohinCnt += psInsWkFukadoSyohin.executeUpdate();
			db.commit();
			
			psDelWkFukadoSyohin.close();
			psInsWkFukadoSyohin.close();

			writeLog(Level.INFO_INT, syohinCnt + "件の不稼働商品ワークを登録しました。");

			
		//SQLエラーが発生した場合の処理
		}catch(SQLException se){
			db.rollback();
			writeError(se);
			throw se;

		//その他のエラーが発生した場合の処理
		}catch(Exception e){
			db.rollback();
			writeError(e);
			throw e;

		//SQL終了処理
		}finally{
			db.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "が終了しました。");
		}
	}

	
	/**
	 * 不稼働単品ワーク削除用のSQLを返す<br>
	 * 
	 * @param なし
	 * @return 検索用SQL文
	 */
	private String getDelWkFukadoTanpinSQL() throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("TRUNCATE TABLE ");
		sb.append("	WK_FUKADO_TANPIN");
		
		return sb.toString();
	}
	
	
	/**
	 * 不稼働単品ワーク登録用PreparedStatement
	 * @throws Exception
	 */
	private String getInsWkFukadoTanpinSQL() throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT /*+ APPEND */ INTO WK_FUKADO_TANPIN ");
		sb.append("SELECT ");
		sb.append("	 RTT.BUNRUI1_CD ");
		sb.append("	,RTT.SYOHIN_CD ");
		sb.append("FROM R_TANPINTEN_TORIATUKAI RTT ");
		sb.append("WHERE RTT.NON_ACT_KB = '9' ");
		sb.append("GROUP BY  ");
		sb.append("	 RTT.BUNRUI1_CD ");
		sb.append("	,RTT.SYOHIN_CD ");
		
		return sb.toString();
	}
	
	/**
	 * 不稼働商品ワーク削除用のSQLを返す<br>
	 * 
	 * @param なし
	 * @return 検索用SQL文
	 */
	private String getDelWkFukadoSyohinSQL() throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("TRUNCATE TABLE ");
		sb.append("	WK_FUKADO_SYOHIN");
		
		return sb.toString();
	}
	
	/**
	 * 不稼働商品ワーク登録用PreparedStatement
	 * @throws Exception
	 */
	private String getInsWkFukadoSyohinSQL() throws SQLException {
		
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT /*+ APPEND */ INTO WK_FUKADO_SYOHIN ");
		sb.append("SELECT ");
		sb.append("	 RS.BUNRUI1_CD ");
		sb.append("	,RS.BUNRUI2_CD ");
		sb.append("	,RS.BUNRUI5_CD ");
		sb.append("	,RS.SIIRESAKI_CD ");
		sb.append("	,RT.TORIHIKISAKI_KANJI_NA ");
		sb.append("	,RS.SYOHIN_CD ");
		sb.append("	,RS.HINMEI_KANJI_NA ");
		sb.append("	,'" + BATCH_ID + "'");
		sb.append("	,'" + systemDt + "'");
		sb.append("	,'" + BATCH_ID + "'");
		sb.append("	,'" + systemDt + "'");
		sb.append("FROM  ");
		sb.append("	( ");
		sb.append("		SELECT * ");
		sb.append("		FROM R_SYOHIN RS1 ");
		sb.append("		WHERE RS1.YUKO_DT =  ");
		sb.append("			( ");
		sb.append("				SELECT MAX(RS2.YUKO_DT) ");
		sb.append("				FROM R_SYOHIN RS2 ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sb.append("				WHERE RS1.BUNRUI1_CD = RS2.BUNRUI1_CD ");
//		sb.append("				AND RS1.SYOHIN_CD = RS2.SYOHIN_CD ");
		sb.append("				WHERE  ");
		sb.append("					 RS1.SYOHIN_CD = RS2.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sb.append("				AND RS2.YUKO_DT <= '" + DateChanger.addDate(batchDt, +1) + "' ");
		sb.append("			) ");
		sb.append("		AND RS1.DELETE_FG = '0' ");
		sb.append("	)RS ");
		sb.append("INNER JOIN ");
		sb.append("	( ");
		sb.append("		SELECT ");
		sb.append("			 RT1.TORIHIKISAKI_CD ");
		sb.append("			,RT1.TORIHIKISAKI_KANJI_NA ");
		sb.append("		FROM R_TORIHIKISAKI RT1 ");
		sb.append("		WHERE RT1.TEKIYO_START_DT =  ");
		sb.append("			( ");
		sb.append("				SELECT MAX(RT2.TEKIYO_START_DT) ");
		sb.append("				FROM R_TORIHIKISAKI RT2 ");
		sb.append("				WHERE RT1.COMP_CD = RT2.COMP_CD ");
		sb.append("				AND RT1.CHOAI_KB = RT2.CHOAI_KB ");
		sb.append("				AND RT1.TORIHIKISAKI_CD = RT2.TORIHIKISAKI_CD ");
		sb.append("				AND RT2.TEKIYO_START_DT <= '" + DateChanger.addDate(batchDt, +1) + "' ");
		sb.append("			) ");
		sb.append("		AND RT1.CHOAI_KB = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' ");
		sb.append("		AND RT1.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("		AND RT1.TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
		sb.append("	)RT ");
		sb.append("ON RS.SIIRESAKI_CD = RT.TORIHIKISAKI_CD ");
		sb.append("INNER JOIN ");
		sb.append("	WK_FUKADO_TANPIN WFT ");
		sb.append("ON RS.BUNRUI1_CD = WFT.BUNRUI1_CD ");
		sb.append("AND RS.SYOHIN_CD = WFT.SYOHIN_CD ");
		sb.append("ORDER BY ");
		sb.append("	 RS.BUNRUI1_CD ");
		sb.append("	,RS.BUNRUI2_CD ");
		sb.append("	,RS.BUNRUI5_CD ");
		sb.append("	,RS.SIIRESAKI_CD ");
		sb.append("	,RT.TORIHIKISAKI_KANJI_NA ");
		sb.append("	,RS.SYOHIN_CD ");
		
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
