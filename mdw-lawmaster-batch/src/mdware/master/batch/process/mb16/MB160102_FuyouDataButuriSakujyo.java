package mdware.master.batch.process.mb16;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:不要過去レコード物理削除処理</p>
 * <p>説明:論理削除されたデータについて、指定期間を経過したレコードは物理削除をする</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author A.Okada<BR>
 * @version 1.00 (2009/02/10) マミーマート様向け初版作成<BR>
 */

public class MB160102_FuyouDataButuriSakujyo {
	
	//DataBaseクラス
	private MasterDataBase db			= null;
	private boolean       closeDb		= false;
	
	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// バッチ日付
	private String 		batchDt 	= null;

	// 削除データ保持期間
	private int	 		purgeInterval = 0;

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute( String batchId ) throws Exception {
		execute();
	}
		
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB160102_FuyouDataButuriSakujyo(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB160102_FuyouDataButuriSakujyo() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}
	
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		
		String tables[][] = {
			{ "R_GIFT_HASSOMOTO",       "ギフト発送元マスタ"         },
			{ "R_SIIRE_HACHUNOHIN_NG",  "取引先発注納品不可日マスタ" },
			{ "R_TANPINTEN_TORIATUKAI", "単品店取扱マスタ"           },
			{ "R_TENGROUP",             "店グルーピングマスタ"       },
			{ "R_TENGROUPNO",           "店グルーピングNoマスタ"     },
			{ "R_TENPO",                "店舗マスタ"                 },
			{ "R_HACHUNOHIN_NG",        "店舗発注納品不可日マスタ"   },
			{ "R_NAMECTF",              "名称マスタ"                 }
		};

		try{
			
			this.writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();

			this.writeLog(Level.INFO_INT, "論理削除レコードの物理削除処理を開始します");
			
			for (int i = 0; i < tables.length; i++) {
				this.deleteData( tables[i][0], tables[i][1] );
			}

			this.writeLog(Level.INFO_INT, "論理削除レコードの物理削除処理を終了します");

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
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		String wkData	= null;

		// 初期化
		batchDt 	  = null;
		purgeInterval = 0;
		
    	// バッチ日付
		batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

    	if(batchDt == null || batchDt.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
    		throw new Exception();
		}

    	// 論理削除データ保持期間
    	wkData = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.RONRI_SAKUJO_SPAN);

    	if(wkData == null || wkData.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、論理削除データ保持期間が取得できませんでした");
    		throw new Exception();
		} else {
			purgeInterval= Integer.parseInt( wkData );
		}
	}

	/**
	 * 論理削除確定データの不要過去レコード物理削除処理
	 * @param String
	 * @param int
	 * @return
	 */	
	private void deleteData( String buturiTblName, String ronriTblName ) throws SQLException, Exception {
		int delCnt = 0;

		delCnt = db.executeUpdate( getDeleteSql( buturiTblName ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件の不要過去レコードを物理削除しました。("+ ronriTblName +")");
	
	}
	
	/**
	 * 削除データを物理削除するSQL
	 * @param String tblname
	 * @return
	 */	
	private String getDeleteSql( String tblname ) {
		
		StringBuffer sb = new StringBuffer();

		// 更新日付をチェック対象とするため、バッチ日付＋保持期間で
		// 算出した日付に、時間（23時59分59秒）を付加する
		sb.append("DELETE FROM " + tblname + " ");
		sb.append("  WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("    AND UPDATE_TS <= '" + DateChanger.addDate(batchDt, 0 - purgeInterval) + "235959" + "' ");

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
