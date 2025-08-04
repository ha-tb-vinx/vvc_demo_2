package mdware.master.batch.process.MB02;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Level;

import mdware.master.util.db.MasterDataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.batch.process.mb38.MB380001_CommonMessage;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst003401_KaisoPatternDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <p>タイトル:商品分類体系マスタ作成処理</p>
 * <p>説明:商品階層マスタに登録されている内容に基づき、商品分類体系マスタ作成を行う。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/05/24<BR>
 * @author hara
 * @version 2.00 2009/03/04 マミーマート様向け初版作成 T.Mori
 * @version 2.01 2009/05/15 結合障害1767 対応 SIC okada
 * @version 2.02 2009/05/25 結合障害1836 対応 SIC okada
 * @version 3.00(2013.05.24) M.Ayukawa [MSTM00005] ランドローム様  クラスライン５桁対応
 */
 
public class MB020101_CreateSyohinTaikei {

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();
	
	// バッチ日付
	private String batchDate = "";
	
	// 作成日付
	private String createDate = "";

	private final int DEAD_LOCK_ERROR  = 60;
	private int waitTime = 0;
	private int retryCnt = 0;
	
	private Map msgMap = MB380001_CommonMessage.getMsg();

	// 処理日間隔
	private static final int SPAN_DAYS = 1;
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB020101_CreateSyohinTaikei(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB020101_CreateSyohinTaikei() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @param waitTime 待機時間
	 * @param retryCnt リトライ回数 
	 * @throws Exception 例外
	 */
	public void execute(String batchId,String waitTime,String retryCnt) throws Exception {
		this.waitTime = Integer.parseInt(waitTime);
		this.retryCnt = Integer.parseInt(retryCnt);
		execute();
	}
	
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		
		try{
			
			writeLog(Level.INFO_INT, "処理を開始します。");
			
			//バッチ日付取得
			batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			
			//商品分類体系作成日取得
			createDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_SAKUSEI_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if(!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)){
				writeLog(Level.INFO_INT, msgMap.get("0531").toString());

				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				writeLog(Level.INFO_INT, "処理を終了します。");
				return;
			}
			writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");

			writeLog(Level.INFO_INT, "商品分類階層マスタデータ確認処理を開始します。");

	    	//SQL文生成用
			StringBuffer strSql = new StringBuffer();
			
			//バッチ登録件数をカウント
			int iRec = 0;
			int count = 0;
			
			//商品分類階層マスタのレコード件数を取得する。	
			strSql.append("SELECT ");
			strSql.append("  COUNT(*) as COUNT ");
			strSql.append("FROM ");
			strSql.append("  R_SYOHINKAISO ");

			ResultSet rs = db.executeQuery(strSql.toString());

			if(rs.next()){
				iRec = rs.getInt("COUNT");
			}
			db.closeResultSet(rs);
			
			//レコード件数が0件の場合は以降の処理を行わない。
			if(iRec == 0){
				writeLog(Level.INFO_INT, "商品分類階層マスタにデータは存在しませんでした。");

				// 処理を終了する
				writeLog(Level.INFO_INT, "商品分類階層マスタデータ確認処理を終了します。");	
				writeLog(Level.INFO_INT, "処理を終了します。");
				return;
			}else{
				writeLog(Level.INFO_INT, iRec + "件の商品分類階層データが存在します。");
			}
			
			writeLog(Level.INFO_INT, "商品分類階層マスタデータ確認処理を終了します。");	

			writeLog(Level.INFO_INT, "商品分類体系マスタの物理削除処理を開始します。");
			//物理削除する
			strSql.delete(0,strSql.length());
			strSql.append(" DELETE FROM ");
			strSql.append("   R_SYOHIN_TAIKEI ");

			int delNum = db.executeUpdate(strSql.toString());
			
			writeLog(Level.INFO_INT, "商品分類体系マスタを " + delNum + "件削除しました。");
			writeLog(Level.INFO_INT, "商品分類体系マスタの物理削除処理を終了します。");

			writeLog(Level.INFO_INT, "商品分類体系マスタの登録処理を開始します。");
			
			//商品分類階層マスタから商品分類体系マスタを生成する。
			//StringBufferの内容をクリアする
			strSql.delete(0,strSql.length());
			
			strSql.append("INSERT INTO R_SYOHIN_TAIKEI(");
			strSql.append("  SYSTEM_KB,");
			strSql.append("  BUNRUI5_CD,");
			strSql.append("  BUNRUI4_CD,");
			strSql.append("  BUNRUI3_CD,");
			strSql.append("  BUNRUI2_CD,");
			strSql.append("  BUNRUI1_CD,");
			strSql.append("  DAIBUNRUI2_CD,");
			strSql.append("  DAIBUNRUI1_CD,");
			strSql.append("  INSERT_TS,");
			strSql.append("  INSERT_USER_ID,");
			strSql.append("  UPDATE_TS,");	
			strSql.append("  UPDATE_USER_ID)");
					
			strSql.append("SELECT ");
//  2013.05.24 [MSTM00005] ランドローム様  クラスライン５桁対応(S)
			strSql.append("  A.SYSTEM_KB AS SYSTEM_KB,");
			strSql.append("  TRIM(A.CODE1_CD)  AS BUNRUI5_CD,");
			strSql.append("  '0'         AS BUNRUI4_CD,");
			strSql.append("  '0'         AS BUNRUI3_CD,");
			strSql.append("  TRIM(B.CODE1_CD)  AS BUNRUI2_CD,");
			strSql.append("  TRIM(C.CODE1_CD)  AS BUNRUI1_CD,");
			strSql.append("  TRIM(D.CODE1_CD)  AS DAIBUNRUI2_CD,");
			strSql.append("  TRIM(D.CODE2_CD)  AS DAIBUNRUI1_CD,");
			strSql.append(" '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
			strSql.append("	   '" + userLog.getJobId() + "', ");
			strSql.append(" '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
			strSql.append("	   '" + userLog.getJobId() + "' ");
//  2013.05.24 [MSTM00005] ランドローム様  クラスライン５桁対応(E)			
			strSql.append("FROM ");
			//階層パターン1を抽出するテーブル(A)を作成
			strSql.append("  (SELECT ");
			strSql.append("		RS1.SYSTEM_KB, ");
			strSql.append("		RS1.KAISOU_PATTERN_KB, ");
			strSql.append("		RS1.CODE1_CD, ");
			strSql.append("		RS1.CODE2_CD ");						
			strSql.append("  FROM ");
			strSql.append("		R_SYOHINKAISO RS1 ");
			strSql.append("  WHERE ");
			strSql.append("		RS1.SYSTEM_KB ='"+ mst000101_ConstDictionary.SYSTEM_DIVISION +"' ");
			strSql.append("  AND ");
			strSql.append("		RS1.KAISOU_PATTERN_KB ='"+ mst003401_KaisoPatternDictionary.CLASS_LINE.getCode() +"' ");
			strSql.append("  AND ");
			strSql.append("		RS1.YUKO_DT = ");
			strSql.append("		  (SELECT ");
			strSql.append("          MAX(YUKO_DT) ");
			strSql.append("        FROM");
			strSql.append("          R_SYOHINKAISO ");
			strSql.append("        WHERE ");
			strSql.append("          SYSTEM_KB ='"+ mst000101_ConstDictionary.SYSTEM_DIVISION +"' ");
			strSql.append("        AND ");	
			strSql.append("          KAISOU_PATTERN_KB ='"+ mst003401_KaisoPatternDictionary.CLASS_LINE.getCode() +"' ");
			strSql.append("        AND ");
			strSql.append("          YUKO_DT <= '" + DateChanger.addDate(batchDate, SPAN_DAYS ) + "' ");
			strSql.append("        AND ");
			strSql.append("          SYSTEM_KB = RS1.SYSTEM_KB");
			strSql.append("        AND ");
			strSql.append("          KAISOU_PATTERN_KB = RS1.KAISOU_PATTERN_KB");
			strSql.append("        AND ");
			strSql.append("          CODE1_CD = RS1.CODE1_CD");
			strSql.append("        ) ");
			strSql.append("  AND ");
			strSql.append("     RS1.DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() +"' ) A, ");

			//階層パターン2を抽出するテーブル(B)を作成
			strSql.append("  (SELECT ");
			strSql.append("		RS2.SYSTEM_KB, ");
			strSql.append("		RS2.KAISOU_PATTERN_KB, ");
			strSql.append("		RS2.CODE1_CD, ");
			strSql.append("		RS2.CODE2_CD ");						
			strSql.append("  FROM ");
			strSql.append("		R_SYOHINKAISO RS2 ");
			strSql.append("  WHERE ");
			strSql.append("		RS2.SYSTEM_KB ='"+ mst000101_ConstDictionary.SYSTEM_DIVISION +"' ");
			strSql.append("  AND ");
			strSql.append("		RS2.KAISOU_PATTERN_KB ='"+ mst003401_KaisoPatternDictionary.LINE_DPT.getCode() +"' ");
			strSql.append("  AND ");
			strSql.append("		RS2.YUKO_DT = ");
			strSql.append("		  (SELECT ");
			strSql.append("          MAX(YUKO_DT) ");
			strSql.append("        FROM ");
			strSql.append("          R_SYOHINKAISO ");
			strSql.append("        WHERE ");
			strSql.append("          SYSTEM_KB ='"+ mst000101_ConstDictionary.SYSTEM_DIVISION +"' ");
			strSql.append("        AND ");	
			strSql.append("          KAISOU_PATTERN_KB ='"+ mst003401_KaisoPatternDictionary.LINE_DPT.getCode() +"' ");
			strSql.append("        AND ");
			strSql.append("          YUKO_DT <= '" + DateChanger.addDate(batchDate, SPAN_DAYS ) + "' ");
			strSql.append("        AND ");
			strSql.append("          SYSTEM_KB = RS2.SYSTEM_KB");
			strSql.append("        AND ");
			strSql.append("          KAISOU_PATTERN_KB = RS2.KAISOU_PATTERN_KB");
			strSql.append("        AND ");
			strSql.append("          CODE1_CD = RS2.CODE1_CD");
			strSql.append("        ) ");
			strSql.append("  AND ");
			strSql.append("     RS2.DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() +"' ) B,");
			
			//階層パターン3を抽出するテーブル(C)を作成
			strSql.append("  (SELECT ");
			strSql.append("		RS3.SYSTEM_KB, ");
			strSql.append("		RS3.KAISOU_PATTERN_KB, ");
			strSql.append("		RS3.CODE1_CD, ");
			strSql.append("		RS3.CODE2_CD ");						
			strSql.append("  FROM ");
			strSql.append("		R_SYOHINKAISO RS3 ");
			strSql.append("  WHERE ");
			strSql.append("		RS3.SYSTEM_KB ='"+ mst000101_ConstDictionary.SYSTEM_DIVISION +"' ");
			strSql.append("  AND ");
			strSql.append("		RS3.KAISOU_PATTERN_KB ='"+ mst003401_KaisoPatternDictionary.DPT_BUMON.getCode() +"' ");
			strSql.append("  AND ");
			strSql.append("		RS3.YUKO_DT = ");
			strSql.append("		  (SELECT ");
			strSql.append("          MAX( YUKO_DT ) ");
			strSql.append("        FROM ");
			strSql.append("          R_SYOHINKAISO ");
			strSql.append("        WHERE ");
			strSql.append("          SYSTEM_KB ='"+ mst000101_ConstDictionary.SYSTEM_DIVISION +"' ");
			strSql.append("        AND ");	
			strSql.append("          KAISOU_PATTERN_KB ='"+ mst003401_KaisoPatternDictionary.DPT_BUMON.getCode() +"' ");
			strSql.append("        AND ");
			strSql.append("          YUKO_DT <= '" + DateChanger.addDate(batchDate, SPAN_DAYS ) + "' ");
			strSql.append("        AND ");
			strSql.append("          SYSTEM_KB = RS3.SYSTEM_KB");
			strSql.append("        AND ");
			strSql.append("          KAISOU_PATTERN_KB = RS3.KAISOU_PATTERN_KB");
			strSql.append("        AND ");
			strSql.append("          CODE1_CD = RS3.CODE1_CD");
			strSql.append("        ) ");
			strSql.append("  AND ");
			strSql.append("     RS3.DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() +"' ) C, ");
			
			//階層パターン4を抽出するテーブル(D)を作成
			strSql.append("  (SELECT ");
			strSql.append("		RS4.SYSTEM_KB, ");
			strSql.append("		RS4.KAISOU_PATTERN_KB, ");
			strSql.append("		RS4.CODE1_CD, ");
			strSql.append("		RS4.CODE2_CD ");						
			strSql.append("  FROM ");
			strSql.append("		R_SYOHINKAISO RS4 ");
			strSql.append("  WHERE ");
			strSql.append("		RS4.SYSTEM_KB ='"+ mst000101_ConstDictionary.SYSTEM_DIVISION +"' ");
			strSql.append("  AND ");
			strSql.append("		RS4.KAISOU_PATTERN_KB ='"+ mst003401_KaisoPatternDictionary.BUMON_GRP.getCode() +"' ");
			strSql.append("  AND ");
			strSql.append("     RS4.YUKO_DT = ");
			strSql.append("		  (SELECT ");
			strSql.append("          MAX( YUKO_DT ) ");
			strSql.append("        FROM");
			strSql.append("          R_SYOHINKAISO ");
			strSql.append("        WHERE ");
			strSql.append("          SYSTEM_KB ='"+ mst000101_ConstDictionary.SYSTEM_DIVISION +"' ");
			strSql.append("        AND ");	
			strSql.append("          KAISOU_PATTERN_KB ='"+ mst003401_KaisoPatternDictionary.BUMON_GRP.getCode() +"' ");
			strSql.append("        AND ");
			strSql.append("          YUKO_DT <= '" + DateChanger.addDate(batchDate, SPAN_DAYS ) + "' ");
			strSql.append("        AND ");
			strSql.append("          SYSTEM_KB = RS4.SYSTEM_KB");
			strSql.append("        AND ");
			strSql.append("          KAISOU_PATTERN_KB = RS4.KAISOU_PATTERN_KB");
			strSql.append("        AND ");
			strSql.append("          CODE1_CD = RS4.CODE1_CD");
			strSql.append("        ) ");
			strSql.append("  AND ");
			strSql.append("     RS4.DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() +"' ) D ");
			
			strSql.append("WHERE  ");
			strSql.append("  A.CODE2_CD = B.CODE1_CD  ");			
			strSql.append("AND ");
			strSql.append("  A.SYSTEM_KB = B.SYSTEM_KB  ");
			strSql.append("AND ");
			strSql.append("  B.CODE2_CD = C.CODE1_CD  ");						
			strSql.append("AND ");		
			strSql.append("  B.SYSTEM_KB = C.SYSTEM_KB  ");
			strSql.append("AND ");
			strSql.append("  C.CODE2_CD = D.CODE1_CD  ");			
			strSql.append("AND ");	
			strSql.append("  C.SYSTEM_KB = D.SYSTEM_KB ");
			
			for (int i = 0;i < this.retryCnt;i++) {
				try {
					count = db.executeUpdate(strSql.toString());
					break;
				} catch (SQLException sqle) {
					if (sqle.getErrorCode() == this.DEAD_LOCK_ERROR) {
						
						if (i + 1 >= this.retryCnt) {
							writeLog(Level.FATAL_INT, "登録処理が最大リトライ回数に達したため停止します。");
							throw sqle;
						}
						
						writeLog(Level.FATAL_INT, "登録処理に失敗したため" + this.waitTime / 1000 + "秒待機後にリトライします。" + (i + 1) + "回目");
						try{
							Thread.sleep(this.waitTime);
						} catch (Exception e){}
					} else {
						throw sqle;
					}
				}
			}
			
			writeLog(Level.INFO_INT, count + "件の商品体系レコードを登録しました。");	
	
			writeLog(Level.INFO_INT, "商品分類体系マスタの登録処理を終了します。");
			
			db.commit();	
			
			writeLog(Level.INFO_INT, "処理を終了します。");
			
		// SQL例外処理
		}catch( SQLException se ){
			db.rollback();

			this.writeError(se);

			throw se;	
			
		}catch( Exception e ){
			db.rollback();

			this.writeError(e);

			throw e;

		}finally{
			// クローズ
			if (closeDb || db != null) {
				db.close();
			}
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
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}