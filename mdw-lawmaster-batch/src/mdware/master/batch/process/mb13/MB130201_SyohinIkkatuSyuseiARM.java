package mdware.master.batch.process.mb13;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.master.common.dictionary.mst006801_MstMainteFgDictionary;
import mdware.master.common.dictionary.mst006901_AlarmMakeFgDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.portal.bean.DtAlarmBean;
import mdware.portal.dictionary.AlarmProcessKbDic;
import mdware.portal.dictionary.PortalAlarmKbDic;
import mdware.portal.process.PortalAlarmInterface;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.util.date.AbstractMDWareDateGetter;

/**
 * <p>タイトル:商品マスタ一括修正処理</p>
 * <p>説明:マスタ管理</p>
 * <p>【商品マスタ一括修正】(R_SYOHIN_IKKATU_MENTE)より当日のデータを抽出し</p>
 * <p> その抽出データを元に編集したデータで【商品マスタ】(R_SYOHIN)を更新する</p>
 * <p> 更新内容を業種区分別に【アラームテーブル】に登録する</p>
 * <p>【マスタ日付テーブル】(SYSTEM_CONTROL)のバッチ日付を当日とする</p>
 * <p>当日までで直近のもの</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/06/24<BR>
 * @version 1.1 (2006/04/18) Takayama 【アラーム対応】<br>
 * @version 2.0 (2008/05/29) VJC       MM様対応<br>
 * @author sawabe
 */
 
public class MB130201_SyohinIkkatuSyuseiARM {

	//DataBaseクラス
	private MasterDataBase db	= null;
	private boolean closeDb 	= false;


	//batchID
	String jobId = null;
	
	// ログ用フィールド
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB130201_SyohinIkkatuSyuseiARM(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB130201_SyohinIkkatuSyuseiARM() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}


	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
//		batchID = batchId;
		execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		
		jobId = userLog.getJobId();

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			
			//SQL文生成用
			StringBuffer strSql = new StringBuffer();
			
			//バッチ登録件数をカウント
			int iRec = 0;
			
			//バッチ日付を取得する
			String batchDt = RMSTDATEUtil.getBatDateDt();
			
			/***************************************************
			 * 商品一括修正TR の今回追加分を Excel種別ごとに読込む
			 ***************************************************/
			strSql.delete(0,strSql.length());
			//▼SELECT句
			strSql.append("SELECT BUNRUI1_CD,");
			strSql.append("       EXCEL_FILE_SYUBETSU, ");
			strSql.append("       MAX(MST_MAINTE_FG) MST_MAINTE_FG");
			//▼FROM句
			strSql.append("  FROM TR_SYOHIN_IKKATU_MENTE ");
			//▼WHERE句
			strSql.append(" WHERE SYORI_DT      = '" + batchDt + "'");			
			strSql.append("   AND ALARM_MAKE_FG = '" + mst006901_AlarmMakeFgDictionary.MI.getCode() + "' "); 
			//▼GROUP BY句
			strSql.append(" GROUP BY ");
			strSql.append("       EXCEL_FILE_SYUBETSU, ");
			strSql.append("       BUNRUI1_CD");
			strSql.append(" ORDER BY ");
			strSql.append("       EXCEL_FILE_SYUBETSU, ");
			strSql.append("       BUNRUI1_CD");
			
			ResultSet rs = db.executeQuery(strSql.toString());

			PortalAlarmInterface alarm = new PortalAlarmInterface();
			//一括修正されたデータがある場合
			while (rs.next()){
				String DelKeyStr = null;
				String alarmId = null;
				
				//メンテフラグ="1"の場合
				if ("1".equals(rs.getString("mst_mainte_fg"))){
					alarmId = "mst0011";
					DelKeyStr = "mst0011" + rs.getString("excel_file_syubetsu") + batchDt + "1";
					//登録済アラームを削除する
//					alarm.removeAlarmMessage(DelKeyStr, jobId, AlarmProcessKbDic.BATCH.getCode());
				}
				//メンテフラグ="2"の場合
				if ("2".equals(rs.getString("mst_mainte_fg"))){
					alarmId = "mst0012";
					DelKeyStr = "mst0012" + rs.getString("excel_file_syubetsu") + batchDt + "2";
					//登録済アラームを削除する
//					alarm.removeAlarmMessage(DelKeyStr, jobId, AlarmProcessKbDic.BATCH.getCode());
				}
				
				setAlarmMessage(alarm, alarmId, rs.getString("BUNRUI1_CD"), DelKeyStr);
				iRec = iRec + 1;
			}

			writeLog(Level.INFO_INT, iRec +"件の商品一括修正のアラーム情報を登録しました。");

			//アラーム作成フラグを更新
			if (iRec > 0) {
				iRec = db.executeUpdate(setUpdateTR());
				writeLog(Level.INFO_INT, iRec +"件をアラーム作成済に更新しました。");
			}
				
			db.commit();
			
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
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}
	
	/**
	 * アラーム情報を登録する。 
	 * @param  alarm 
	 * @param  strAlarmId アラームID
	 * @param  strDelKeyStr String 
	 * @return なし
	 */			
	private void setAlarmMessage(PortalAlarmInterface alarm, String strAlarmId, String strBumonCd, String DelKeyStr) {
		
		// アラーム情報登録クラス
		DtAlarmBean alarmBean = new DtAlarmBean();
		String strProcess = null ;
		
		// アラーム区分
		alarmBean.setAlarmKb(PortalAlarmKbDic.BUYERMUKE.getCode());
		// 送信先区分
		alarmBean.setDestinationBumonCd(strBumonCd);
		// 送信先ユザID
		alarmBean.setDestinationUserId(null);
		// url_tx
//		alarmBean.setUrlTx("app?JobID=mst160101_SyohinIkkatuMeneteInit");
		alarmBean.setUrlTx(null);
		// del_key_tx
		alarmBean.setDelKeyTx(DelKeyStr);
		// アラームID
		alarmBean.setAlarmId(strAlarmId);
		// 作成者ID
		alarmBean.setInsertUserId(jobId);
		// 処理区分
		strProcess = AlarmProcessKbDic.BATCH.getCode();
		
		alarm.setAlarmMessage(alarmBean, strProcess);
	}

	
//	↓↓移植（2006.05.11）↓↓	
	/**
	 * アラーム作成フラグ「１」更新用SQL
	 * @param	String mstNa			テーブル名
	 * @throws SQLException
	 */		
	private String setUpdateTR() throws SQLException {
//	↑↑移植（2006.05.11）↑↑	
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" UPDATE TR_SYOHIN_IKKATU_MENTE ");
		sb.append(" SET ");
		
//		↓↓2006.07.25 lixy カスタマイズ修正↓↓		
		//アラーム作成フラグ
		//sb.append("   ALARM_MAKE_FG = '1', ");
		sb.append("   ALARM_MAKE_FG = '" + mst006901_AlarmMakeFgDictionary.ZUMI.getCode() + "', ");
//		↑↑2006.07.25 lixy カスタマイズ修正↑↑	
		
		//更新者社員ID
//		↓↓移植（2006.05.11）↓↓
		sb.append("   UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
//		↑↑移植（2006.05.11）↑↑
		//更新年月日
		sb.append("   UPDATE_USER_ID = '"+ jobId +"'");
		sb.append(" WHERE ");							//アラーム作成フラグが全て0
		
//		↓↓2006.07.25 lixy カスタマイズ修正↓↓				
		//sb.append("   ALARM_MAKE_FG = '0' ");			//マスタメンテフラグが1or2or3
		sb.append("   ALARM_MAKE_FG = '" + mst006901_AlarmMakeFgDictionary.MI.getCode() + "' ");
//		↑↑2006.07.25 lixy カスタマイズ修正↑↑
	
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
