package mdware.master.batch.process.mbA2;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:MSTB111070_IF店舗マスタ作成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author T.Yokoyama
 * @version 1.00 初版作成
 * @version 1.10 結合Ｔ障害対応 2038 okada
 * @version 1.11 2021/10/05 SIEU.D #6348 MKH対応
 */
 
public class MBA20701_CreateIfTenpo{

	private MasterDataBase db = null;

	// ログ用フィールド
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA20701_CreateIfTenpo( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA20701_CreateIfTenpo() {

		this( new MasterDataBase( "rbsite_ora" ) );
	}

	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		String jobId = userLog.getJobId();
		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			
			// タイムスタンプ取得
			String timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );

			// IFスキーマ名
			String ifSchema = null;
			try{
				ifSchema = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.IF_SCHEMA).trim();
			}catch(Exception ex){
				writeLog(Level.ERROR_INT, "内部IFスキーマ名が設定されていないため、TMP店舗マスタはIFされません。");
				return;
			}

			// テーブルクリア
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを開始します。");
			DBUtil.truncateTable(db, ifSchema, "IF_R_TENPO");
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを終了します。");

			// IFテーブルへの登録処理
			writeLog(Level.INFO_INT, "IFテーブルのデータ登録を開始します。");
			int insNum = db.executeUpdate(getInsertSql(timeStamp));
			writeLog(Level.INFO_INT, "IFテーブルに " + insNum + "件登録しました。");

			// コミット
			db.commit();

		// 例外処理
		} catch ( Exception e ) {

			// ロールバック
			db.rollback();

			// ログ出力
			writeError(e);

			throw e;
		}finally{
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/*********************************************/
	
	/**
	 * IFテーブルInsert用SQL
	 * @param timeStamp 登録年月日
	 */
	public String getInsertSql(String timeStamp) throws SQLException, Exception {
		StringBuffer sb = new StringBuffer();

		// SQL作成
		sb.append("INSERT /*+ APPEND */ INTO IF_R_TENPO NOLOGGING (");
		sb.append("	TENPO_CD, ");
		sb.append("	HOJIN_CD, ");
		sb.append("	TENPOKAISO_TIIKI_CD, ");
		sb.append("	TENPOKAISO_AREA_CD, ");
		sb.append("	TENPOKAISO_BLOCK_CD, ");
		sb.append("	KANJI_NA, ");
		sb.append("	KANA_NA, ");
		sb.append("	KANJI_RN, ");
		sb.append("	KANA_RN, ");
		sb.append("	TENPO_TYPE_KB, ");
		sb.append("	TENPO_KB, ");
		sb.append("	KAITEN_DT, ");
		sb.append("	HEITEN_DT, ");
		sb.append("	ZAIMU_END_DT, ");
		sb.append("	OPEN_TM, ");
		sb.append("	CLOSE_TM, ");
		sb.append("	ADDRESS_KANJI_NA, ");
		sb.append("	ADDRESS_KANA_NA, ");
		sb.append("	ADDRESS_3_NA, ");
		sb.append("	YUBIN_CD, ");
		sb.append("	TEL_CD, ");
		sb.append("	TRANS_TEL_CD, ");
		sb.append("	FAX_CD, ");
		sb.append("	EMAIL_ADDRESS, ");
		sb.append("	HACHU_ST_DT, ");
		sb.append("	HACHU_ED_DT, ");
		sb.append("	FLOOR_AREA_QT, ");
		sb.append("	URIBA_AREA_QT, ");
		sb.append("	BK_AREA_QT, ");
		sb.append("	TENANT_AREA_QT, ");
		sb.append("	REGISTER_COUNT_QT, ");
		sb.append("	PARKING_AREA_QT, ");
		sb.append("	PARKING_COUNT_QT, ");
		sb.append("	TENPO_HYOJIJUN_NO, ");
		sb.append("	BUTYURYUHI_CENTER, ");
		sb.append("	SNDST_NEHUDA_DT, ");
		sb.append("	SNDST_PRICE_DT, ");
		sb.append("	SNDST_TAG_DT, ");
		sb.append("	SNDST_PLU_DT, ");
		sb.append("	SNDST_POP_DT, ");
		sb.append("	SNDST_KEIRYOKI_DT, ");
		sb.append("	SAI_SEND_PLU_DT, ");
		sb.append("	HANKU1_KEIRYOKI_TYPE, ");
		sb.append("	HANKU2_KEIRYOKI_TYPE, ");
		sb.append("	HANKU3_KEIRYOKI_TYPE, ");
		sb.append("	HANKU4_KEIRYOKI_TYPE, ");
		sb.append("	POS_KB, ");
		sb.append("	BOTEN_CD, ");
		sb.append("	GYOTAI_KB, ");
		sb.append("	ZENTEN_KB,");
		// #6348 Add 2021/10/05 SIEU.D (S)
		sb.append("	GLOBAL_LOCATION_NO,");
		// #6348 Add 2021/10/05 SIEU.D (E)
		sb.append("	IF_INSERT_TS");
		sb.append(") ");

		sb.append("SELECT ");
		// #6348 Add MOD/10/05 SIEU.D (S)
//		sb.append("	TRIM(TENPO_CD), ");
//		sb.append("	TRIM(HOJIN_CD), ");
//		sb.append("	TRIM(TENPOKAISO_TIIKI_CD), ");
//		sb.append("	TRIM(TENPOKAISO_AREA_CD), ");
//		sb.append("	TRIM(TENPOKAISO_BLOCK_CD), ");
//		sb.append("	TRIM(KANJI_NA), ");
//		sb.append("	TRIM(KANA_NA), ");
//		sb.append("	TRIM(KANJI_RN), ");
//		sb.append("	TRIM(KANA_RN), ");
//		sb.append("	TRIM(TENPO_TYPE_KB), ");
//		sb.append("	TRIM(TENPO_KB), ");
//		sb.append("	TRIM(KAITEN_DT), ");
//		sb.append("	TRIM(HEITEN_DT), ");
//		sb.append("	TRIM(ZAIMU_END_DT), ");
//		sb.append("	TRIM(OPEN_TM), ");
//		sb.append("	TRIM(CLOSE_TM), ");
//		sb.append("	TRIM(ADDRESS_KANJI_NA), ");
//		sb.append("	TRIM(ADDRESS_KANA_NA), ");
//		sb.append("	TRIM(ADDRESS_3_NA), ");
//		sb.append("	TRIM(YUBIN_CD), ");
//		sb.append("	TRIM(TEL_CD), ");
//		sb.append("	TRIM(TRANS_TEL_CD), ");
//		sb.append("	TRIM(FAX_CD), ");
//		sb.append("	TRIM(EMAIL_ADDRESS), ");
//		sb.append("	TRIM(HACHU_ST_DT), ");
//		sb.append("	TRIM(HACHU_ED_DT), ");
//		sb.append("	FLOOR_AREA_QT, ");
//		sb.append("	URIBA_AREA_QT, ");
//		sb.append("	BK_AREA_QT, ");
//		sb.append("	TENANT_AREA_QT, ");
//		sb.append("	REGISTER_COUNT_QT, ");
//		sb.append("	PARKING_AREA_QT, ");
//		sb.append("	PARKING_COUNT_QT, ");
//		sb.append("	TRIM(TENPO_HYOJIJUN_NO), ");
//		sb.append("	TRIM(BUTYURYUHI_CENTER), ");
//		sb.append("	TRIM(SNDST_NEHUDA_DT), ");
//		sb.append("	TRIM(SNDST_PRICE_DT), ");
//		sb.append("	TRIM(SNDST_TAG_DT), ");
//		sb.append("	TRIM(SNDST_PLU_DT), ");
//		sb.append("	TRIM(SNDST_POP_DT), ");
//		sb.append("	TRIM(SNDST_KEIRYOKI_DT), ");
//		sb.append("	TRIM(SAI_SEND_PLU_DT), ");
//		sb.append("	TRIM(HANKU1_KEIRYOKI_TYPE), ");
//		sb.append("	TRIM(HANKU2_KEIRYOKI_TYPE), ");
//		sb.append("	TRIM(HANKU3_KEIRYOKI_TYPE), ");
//		sb.append("	TRIM(HANKU4_KEIRYOKI_TYPE), ");
//		sb.append("	TRIM(POS_KB), ");
//		sb.append("	TRIM(BOTEN_CD), ");
//		sb.append("	TRIM(GYOTAI_KB), ");
//		sb.append("	TRIM(ZENTEN_KB), ");
//		sb.append("	'").append(timeStamp).append("' ");
//		sb.append("FROM TMP_R_TENPO ");
//		sb.append("WHERE DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() +"'");

		sb.append("	TRIM(TRT.TENPO_CD), ");
		sb.append("	TRIM(TRT.HOJIN_CD), ");
		sb.append("	TRIM(TRT.TENPOKAISO_TIIKI_CD), ");
		sb.append("	TRIM(TRT.TENPOKAISO_AREA_CD), ");
		sb.append("	TRIM(TRT.TENPOKAISO_BLOCK_CD), ");
		sb.append("	TRIM(TRT.KANJI_NA), ");
		sb.append("	TRIM(TRT.KANA_NA), ");
		sb.append("	TRIM(TRT.KANJI_RN), ");
		sb.append("	TRIM(TRT.KANA_RN), ");
		sb.append("	TRIM(TRT.TENPO_TYPE_KB), ");
		sb.append("	TRIM(TRT.TENPO_KB), ");
		sb.append("	TRIM(TRT.KAITEN_DT), ");
		sb.append("	TRIM(TRT.HEITEN_DT), ");
		sb.append("	TRIM(TRT.ZAIMU_END_DT), ");
		sb.append("	TRIM(TRT.OPEN_TM), ");
		sb.append("	TRIM(TRT.CLOSE_TM), ");
		sb.append("	TRIM(TRT.ADDRESS_KANJI_NA), ");
		sb.append("	TRIM(TRT.ADDRESS_KANA_NA), ");
		sb.append("	TRIM(TRT.ADDRESS_3_NA), ");
		sb.append("	TRIM(TRT.YUBIN_CD), ");
		sb.append("	TRIM(TRT.TEL_CD), ");
		sb.append("	TRIM(TRT.TRANS_TEL_CD), ");
		sb.append("	TRIM(TRT.FAX_CD), ");
		sb.append("	TRIM(TRT.EMAIL_ADDRESS), ");
		sb.append("	TRIM(TRT.HACHU_ST_DT), ");
		sb.append("	TRIM(TRT.HACHU_ED_DT), ");
		sb.append("	TRT.FLOOR_AREA_QT, ");
		sb.append("	TRT.URIBA_AREA_QT, ");
		sb.append("	TRT.BK_AREA_QT, ");
		sb.append("	TRT.TENANT_AREA_QT, ");
		sb.append("	TRT.REGISTER_COUNT_QT, ");
		sb.append("	TRT.PARKING_AREA_QT, ");
		sb.append("	TRT.PARKING_COUNT_QT, ");
		sb.append("	TRIM(TRT.TENPO_HYOJIJUN_NO), ");
		sb.append("	TRIM(TRT.BUTYURYUHI_CENTER), ");
		sb.append("	TRIM(TRT.SNDST_NEHUDA_DT), ");
		sb.append("	TRIM(TRT.SNDST_PRICE_DT), ");
		sb.append("	TRIM(TRT.SNDST_TAG_DT), ");
		sb.append("	TRIM(TRT.SNDST_PLU_DT), ");
		sb.append("	TRIM(TRT.SNDST_POP_DT), ");
		sb.append("	TRIM(TRT.SNDST_KEIRYOKI_DT), ");
		sb.append("	TRIM(TRT.SAI_SEND_PLU_DT), ");
		sb.append("	TRIM(TRT.HANKU1_KEIRYOKI_TYPE), ");
		sb.append("	TRIM(TRT.HANKU2_KEIRYOKI_TYPE), ");
		sb.append("	TRIM(TRT.HANKU3_KEIRYOKI_TYPE), ");
		sb.append("	TRIM(TRT.HANKU4_KEIRYOKI_TYPE), ");
		sb.append("	TRIM(TRT.POS_KB), ");
		sb.append("	TRIM(TRT.BOTEN_CD), ");
		sb.append("	TRIM(TRT.GYOTAI_KB), ");
		sb.append("	TRIM(TRT.ZENTEN_KB), ");
		sb.append("	TRIM(RTA.GLOBAL_LOCATION_NO), ");
		sb.append("	'").append(timeStamp).append("' ");
		sb.append(" FROM TMP_R_TENPO TRT ");
		sb.append(" LEFT JOIN R_TENPO_ASN RTA ");
		sb.append("      ON TRT.TENPO_CD = RTA.TENPO_CD ");
		sb.append("WHERE TRT.DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() +"'");
		// #6348 MOD 2021/10/05 SIEU.D (E)

		return sb.toString();
	}

	/********* 以下、ログ出力用メソッド *********/
	
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
