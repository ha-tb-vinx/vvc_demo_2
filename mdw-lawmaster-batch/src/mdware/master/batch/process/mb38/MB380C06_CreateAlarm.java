package mdware.master.batch.process.mb38;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Level;

//↓↓2006.07.25 lixy カスタマイズ修正↓↓
//import mdware.batch.common.log.BatchLog;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
//↑↑2006.07.25 lixy カスタマイズ修正↑↑
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.common.dictionary.mst006901_AlarmMakeFgDictionary;
import mdware.master.common.dictionary.mst008701_AlarmSheetSyubetsuDictionary;
import mdware.master.common.dictionary.mst006801_MstMainteFgDictionary;
import mdware.master.util.db.MasterDataBase;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.util.RMSTDATEUtil;
import mdware.portal.bean.DtAlarmBean;
import mdware.portal.dictionary.AlarmProcessKbDic;
import mdware.portal.dictionary.PortalAlarmKbDic;
import mdware.portal.process.PortalAlarmInterface;
import mdware.common.batch.util.BatchStatusManager;
import mdware.common.batch.util.control.jobProperties.Jobs;
/**
 * <p>タイトル:アラーム編集処理</p>
 * <p>説明:バッチ登録後の更新結果を編集し、画面出力用のアラーム行データを作成する。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/05/31<BR>
 * @author hara
 * @version 1.1 (2006/04/03) Takayama 【アラーム対応】
 * @version     (2006/04/07) Takayama 【処理テーブル追加】
 */

public class MB380C06_CreateAlarm {
	
	private boolean closeDb 		= false;

	//DataBaseクラス
	private MasterDataBase db	= null;
	
	//バッチ日付
	String batchDt = RMSTDATEUtil.getBatDateDt();

	//各トランマスタテーブル名
	ArrayList mstTrNa = null;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private static final String BATCH_ID = "MB38-0C-06";
	private static final String BATCH_NA = "アラーム編集処理";
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
	public MB380C06_CreateAlarm(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB380C06_CreateAlarm() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}


	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
					
		//各トランバッチ登録件数をカウント
		int iRec = 0;
		int iCount = 0;
		
		String jobId = userLog.getJobId();

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
		
			StringBuffer strSql = new StringBuffer();

			// 2006.04.03 takayama Mod ↓
			this.mstTrNa = new ArrayList();
			
			//商品マスタTR
			strSql.append(getSelectTrSql("TR_SYOHIN", mst000601_GyoshuKbDictionary.GRO.toString()));
			strSql.append(" UNION ");
			//ギフト商品マスタTR
			strSql.append(getSelectTrSql("TR_GIFT_SYOHIN", mst000601_GyoshuKbDictionary.GRO.toString()));
			strSql.append(" UNION ");
			//計量器マスタTR
			strSql.append(getSelectTrSql("TR_KEIRYOKI", mst000601_GyoshuKbDictionary.GRO.toString()));
			strSql.append(" UNION ");
			//店別商品例外マスタメンテT/R
			strSql.append(getSelectTrSql("TR_TENSYOHIN_REIGAI", ""));
			
			//ARMの当日データ登録処理実行
			ResultSet rset = db.executeQuery(this.getSelectARMSql(strSql.toString()));
			String strMstMainteFg = null;
			while (rset.next()) {
				strMstMainteFg = rset.getString("MST_MAINTE_FG_TORIKOMI");	
				
				if (strMstMainteFg.equals(mst006801_MstMainteFgDictionary.ERROR.getCode())) {
					
					//アラーム情報を登録する
					if (rset.getString("SIIRESAKI_CD") != null && !rset.getString("SIIRESAKI_CD").trim().equals("")) {
						// 仕入先アップの場合
						setAlarmMessage("mst0005", rset.getString("INSERT_USER_ID"), rset.getString("BUMON_CD"), rset.getString("BY_NO"), null, rset.getString("UKETSUKE_NO"));
						setAlarmMessage("mst0006", rset.getString("INSERT_USER_ID"), rset.getString("BUMON_CD"), rset.getString("BY_NO"), rset.getString("SIIRESAKI_CD"), rset.getString("UKETSUKE_NO"));
					} else {
						// バイヤーアップの場合
						setAlarmMessage("mst0005", rset.getString("INSERT_USER_ID"), rset.getString("BUMON_CD"), rset.getString("BY_NO"), null, rset.getString("UKETSUKE_NO"));
					}
					iCount++;
				}
			}
			if (iCount > 0) {
				writeLog(Level.INFO_INT, iCount + "件のアラームを登録しました。");
			}
			
			// 各マスタメンテトランテーブル毎に、アラーム作成フラグを「1：作成済」に更新
			Iterator it = this.mstTrNa.iterator();
			while (it.hasNext()) {
				String tblNa = it.next().toString();
				//アラーム作成フラグ更新SQLの設定
				iRec = db.executeUpdate(setUpdateTR(tblNa));
				
				if (iRec == 0) {
					writeLog(Level.INFO_INT, "アラームテーブル更新対象データが存在しませんでした。");
					
				} else {
					writeLog(Level.INFO_INT, tblNa + "テーブルのアラーム作成フラグ["+ iRec +"]件の更新しました。");
				}
			}
			// 2006.04.03 takayama Mod ↑

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
//	↓↓移植（2006.05.11）↓↓	
	/**
	 * アラームテーブル登録(住生活、衣料8桁、衣料12桁)用SQL
	 * @param	String subTable			テーブル名
	 * @return String sql
	 * @throws SQLException
	 */	
	private String getSelectARMSql(String subTable) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		sb.append("   tbl.TORIKOMI_DT, ");
		sb.append("   tbl.EXCEL_FILE_SYUBETSU, ");
		sb.append("   tbl.UKETSUKE_NO, ");
		sb.append("   tbl.BUMON_CD, ");
		sb.append("   tbl.SYUSEI_KB, ");
		sb.append("   ts.SIIRESAKI_CD, ");
		sb.append("   ts.BY_NO, ");
		sb.append("   ts.INSERT_USER_ID, ");
		sb.append("   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
		sb.append("   tbl.INSERT_TS, ");
		sb.append("   tbl.MST_MAINTE_FG_TORIKOMI, ");
		sb.append("   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
		sb.append("   '"+ BATCH_ID +"'");
		sb.append(" FROM ");
		sb.append("   tr_toroku_syonin ts inner join ");
		sb.append("( SELECT ");
		sb.append("   tr.TORIKOMI_DT, ");
		sb.append("   tr.EXCEL_FILE_SYUBETSU, ");
		sb.append("   tr.UKETSUKE_NO, ");
		sb.append("   max(tr.INSERT_TS) INSERT_TS, ");
		sb.append("   max(tr.SYUSEI_KB) SYUSEI_KB, ");
		sb.append("   min(tr.BUMON_CD) BUMON_CD, ");
		sb.append("   max(tr.MST_MAINTE_FG) MST_MAINTE_FG_TORIKOMI, ");
		sb.append("   '" + mst008701_AlarmSheetSyubetsuDictionary.EXCEL.toString() + "' AS EXCEL_TOROKU, ");
		sb.append("   max(tr.GYOSHU_NA)     GYOSHU_NA ");
		sb.append(" FROM ");		
		sb.append("   ("+ subTable +") tr ");
		sb.append(" WHERE ");
		sb.append("   tr.ALARM_MAKE_FG = '" + mst006901_AlarmMakeFgDictionary.MI.getCode() + "' ");
		sb.append("  AND tr.MST_MAINTE_FG IN ('" + mst006801_MstMainteFgDictionary.SYORIZUMI.getCode() + "'");
		sb.append("                         , '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "'");
		sb.append("                         , '" + mst006801_MstMainteFgDictionary.ERROR.getCode() + "')");
		sb.append(" GROUP BY ");
		sb.append("   tr.TORIKOMI_DT,");					
		sb.append("   tr.EXCEL_FILE_SYUBETSU,");
		sb.append("   tr.UKETSUKE_NO ");
		sb.append(") tbl");
		sb.append(" on ");
		sb.append("       ts.TORIKOMI_DT = tbl.TORIKOMI_DT ");
		sb.append("   and ts.EXCEL_FILE_SYUBETSU = tbl.EXCEL_FILE_SYUBETSU ");
		sb.append("   and ts.UKETSUKE_NO = tbl.UKETSUKE_NO ");
	
		return sb.toString();
	}
	
	
//	↓↓移植（2006.05.11）↓↓	
	/**
	 * アラーム作成フラグ「１」更新用SQL
	 * @param	String mstNa			テーブル名
	 * @throws SQLException
	 */		
	private String setUpdateTR(String mstNa) throws SQLException {
//	↑↑移植（2006.05.11）↑↑	
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" UPDATE ");
		sb.append("  "+ mstNa +" ");
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
		sb.append("   UPDATE_USER_ID = '"+ BATCH_ID +"'");
		sb.append(" WHERE ");							//アラーム作成フラグが全て0
		
//		↓↓2006.07.25 lixy カスタマイズ修正↓↓				
		//sb.append("   ALARM_MAKE_FG = '0' ");			//マスタメンテフラグが1or2or3
		sb.append("   ALARM_MAKE_FG = '" + mst006901_AlarmMakeFgDictionary.MI.getCode() + "' ");
//		↑↑2006.07.25 lixy カスタマイズ修正↑↑
		
		// 2006.04.03 takayama MOD 警告フラグを追加 ↓
		sb.append("   AND MST_MAINTE_FG IN ('" + mst006801_MstMainteFgDictionary.SYORIZUMI.getCode() + "'");
		sb.append("                       , '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "'");
		sb.append("                       , '" + mst006801_MstMainteFgDictionary.ERROR.getCode() + "')");
		// 2006.04.03 takayama MOD ↑
	
		return sb.toString();
	}
	
	/**
	 * 各マスタメンテトランの検索用SQL文を生成<br>
	 * 同時にアラーム作成フラグの更新用にテーブル名を退避<br>
	 * 
	 * @param tblNa テーブル名
	 * @param gyosyuNa 業種名
	 * @return 検索用SQL文
	 */
	private String getSelectTrSql(String tblNa, String gyosyuNa) {
		this.mstTrNa.add(tblNa);	//各マスタメンテトランテーブル名の退避
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TORIKOMI_DT");
		sb.append("     , EXCEL_FILE_SYUBETSU");
		sb.append("     , UKETSUKE_NO");
		sb.append("     , TR_BUNRUI1_CD AS BUMON_CD");
		sb.append("     , SYUSEI_KB");
		sb.append("     , ALARM_MAKE_FG");
		sb.append("     , MST_MAINTE_FG");
		sb.append("     , INSERT_TS");
		sb.append("     , '"+ gyosyuNa +"' AS GYOSHU_NA");
		sb.append("  FROM " + tblNa);
		sb.append(" WHERE NVL(ALARM_MAKE_FG, ' ') <> '" + mst006901_AlarmMakeFgDictionary.ZUMI.getCode() + "'");
		return sb.toString();
	}

	/**
	 * アラーム情報を登録する。
	 * 
	 * @param  strAlarmId String アラームID
	 * @param  strInsertUserId String 登録者ID
	 * @param  strBumonCd String 代表部門コード
	 * @param  strByNo String バイヤーNo.
	 * @param  strSiiresakiCd String 登録票承認TRの仕入先コード
	 * @param  strUketsukeNo String 受付№
	 * @return なし
	 * 
	 * @throws なし
	 */			
	private void setAlarmMessage(String strAlarmId, String strInsertUserId, String strBumonCd, String strByNo, String strSiiresakiCd, String strUketsukeNo) {

//		↓↓2006.12.14 H.Yamamoto パンチデータ対策修正↓↓
		// パンチデータのアラームは不要
		if (strInsertUserId != null && strInsertUserId.trim().equals("CNV")) {
			return;
		}
//		↑↑2006.12.14 H.Yamamoto パンチデータ対策修正↑↑

		// アラーム情報登録クラス
		PortalAlarmInterface alarm = new PortalAlarmInterface();
		DtAlarmBean alarmBean = new DtAlarmBean();

		alarmBean.setAlarmId(strAlarmId);
		if (strSiiresakiCd != null && !strSiiresakiCd.trim().equals("")) {
			alarmBean.setAlarmKb(PortalAlarmKbDic.SIIREMUKE.getCode());
		} else {
			alarmBean.setAlarmKb(PortalAlarmKbDic.BUYERMUKE.getCode());
		}
		// destomatopm_user_idの設定
		alarmBean.setDestinationUserId(strInsertUserId);
		// insert_user_idの設定
		alarmBean.setInsertUserId(strByNo);
		// destomatopm_bumon_cdの設定
		alarmBean.setDestinationBumonCd(strBumonCd);
		// url_txの設定
//		↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
//		alarmBean.setUrlTx("app?JobID=mst380101_ExcelUploadInit&buyer_no=" + strByNo.substring(4));
//		if (strAlarmId.equals("mst0005")) {
//			alarmBean.setUrlTx("app?JobID=mstA70101_TorokuhyoBYSyoninInit&tx_byNo=" + strByNo.substring(4));
//		} else {
//			alarmBean.setUrlTx("app?JobID=mst380101_ExcelUploadInit&buyer_no=" + strByNo.substring(4));
//		}
//		↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
		alarmBean.setUrlTx(null);
		// del_key_txの設定
		alarmBean.setDelKeyTx(strAlarmId + strUketsukeNo);
		// アラーム情報を登録する
		alarm.setAlarmMessage(alarmBean, AlarmProcessKbDic.BATCH.getCode());
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