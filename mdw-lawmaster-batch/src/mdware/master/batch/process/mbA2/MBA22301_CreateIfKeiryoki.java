package mdware.master.batch.process.mbA2;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:IF計量器マスタ作成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2009/05/07<BR>
 * @author A.Okada
 * @version 1.00 初版作成
 * @version 1.10 結合Ｔ障害対応 2038,2041 okada
 */
 
public class MBA22301_CreateIfKeiryoki{

	private MasterDataBase db = null;

	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	
	// 処理当日から基準日までの間隔
	private static final int SYORI_SPAN = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA22301_CreateIfKeiryoki( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA22301_CreateIfKeiryoki() {

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

			// バッチ日付
			String batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

			// IFスキーマ名
			String ifSchema = null;
			try{
				ifSchema = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.IF_SCHEMA).trim();
			}catch(Exception ex){
				writeLog(Level.ERROR_INT, "内部IFスキーマ名が設定されていないため、TMP計量器マスタはIFされません。");
				return;
			}

			// テーブルクリア
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを開始します。");
			DBUtil.truncateTable(db, ifSchema, "IF_R_KEIRYOKI");
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを終了します。");

			// IFテーブルへの登録処理
			writeLog(Level.INFO_INT, "IFテーブルのデータ登録を開始します。");
			int insNum = db.executeUpdate(getInsertSql(timeStamp, batchDt));
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

	/**
	 * IFテーブルInsert用SQL
	 * @param timeStamp 登録年月日
	 * @param batchDate バッチ日付
	 */
	protected String getInsertSql(String timeStamp,  String batchDate){
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT /*+ APPEND */ INTO IF_R_KEIRYOKI NOLOGGING ( ");
		sb.append("    BUNRUI1_CD           , ");
		sb.append("    SYOHIN_CD            , ");
		sb.append("    YUKO_DT              , ");
		sb.append("    SYOHIN_YOBIDASI      , ");
		sb.append("    S_GYOSYU_CD          , ");
		sb.append("    THEME_CD             , ");
		sb.append("    KEIRYO_HANKU_CD      , ");
		sb.append("    HANEI_DT             , ");
		sb.append("    SYOHIN_KBN           , ");
		sb.append("    KEIRYOKI_NA          , ");
		sb.append("    KEIRYOKI2_NA         , ");
		sb.append("    KEIRYOKI3_NA         , ");
		sb.append("    RECEIPT_HINMEI_NA    , ");
		sb.append("    TEIGAKU_UP_KB        , ");
		sb.append("    TANKA_VL             , ");
		sb.append("    TEIGAKU_VL           , ");
		sb.append("    TEIGAKUJI_TANI_KB    , ");
		sb.append("    SYOMIKIKAN_KB        , ");
		sb.append("    SYOMIKIKAN_VL        , ");
		sb.append("    SANTI_KB             , ");
		sb.append("    KAKOJIKOKU_PRINT_KB  , ");
		sb.append("    CHORIYO_KOKOKUBUN_KB , ");
		sb.append("    HOZON_ONDOTAI_KB     , ");
		sb.append("    START_KB             , ");
		sb.append("    BACK_LABEL_KB        , ");
		sb.append("    EIYO_SEIBUN_NA       , ");
		sb.append("    COMMENT_KB           , ");
		sb.append("    BIKO_TX              , ");
		sb.append("    GENZAIRYO_NA         , ");
		sb.append("    TENKABUTU_NA         , ");
		sb.append("    MIN_WEIGHT_QT        , ");
		sb.append("    MAX_WEIGHT_QT        , ");
		sb.append("    TEIKAN_WEIGHT_QT     , ");
		sb.append("    FUTAI_WEIGHT_QT      , ");
		sb.append("    EYE_CATCH_NO         , ");
		sb.append("    EYE_CATCH_MODE       , ");
		sb.append("    TEIGAKU_KB           , ");
		sb.append("    TEIGAKU_BAIKA_VL     , ");
		sb.append("    HOZON_ONDO_QT        , ");
		sb.append("    CALORIE              , ");
		sb.append("    TRAY_NA              , ");
		sb.append("    NETSUKEKI_NA         , ");
		sb.append("    NETSUKEKI_NA_2       , ");
		sb.append("    INSERT_TS            , ");
		sb.append("    INSERT_USER_ID       , ");
		sb.append("    UPDATE_TS            , ");
		sb.append("    UPDATE_USER_ID       , ");
		sb.append("    BATCH_UPDATE_TS      , ");
		sb.append("    BATCH_UPDATE_ID      , ");
		sb.append("    DELETE_FG            , ");
		sb.append("    IF_INSERT_TS           ");
		sb.append(") ");
		sb.append("SELECT ");
		sb.append("    TRIM(BUNRUI1_CD)           , ");
		sb.append("    TRIM(SYOHIN_CD)            , ");
		sb.append("    TRIM(YUKO_DT)              , ");
		sb.append("    TRIM(SYOHIN_YOBIDASI)      , ");
		sb.append("    TRIM(S_GYOSYU_CD)          , ");
		sb.append("    TRIM(THEME_CD)             , ");
		sb.append("    TRIM(KEIRYO_HANKU_CD)      , ");
		sb.append("    TRIM(HANEI_DT)             , ");
		sb.append("    TRIM(SYOHIN_KBN)           , ");
		sb.append("    TRIM(KEIRYOKI_NA)          , ");
		sb.append("    TRIM(KEIRYOKI2_NA)         , ");
		sb.append("    TRIM(KEIRYOKI3_NA)         , ");
		sb.append("    TRIM(RECEIPT_HINMEI_NA)    , ");
		sb.append("    TRIM(TEIGAKU_UP_KB)        , ");
		sb.append("    TANKA_VL                   , ");
		sb.append("    TEIGAKU_VL                 , ");
		sb.append("    TRIM(TEIGAKUJI_TANI_KB)    , ");
		sb.append("    TRIM(SYOMIKIKAN_KB)        , ");
		sb.append("    SYOMIKIKAN_VL              , ");
		sb.append("    TRIM(SANTI_KB)             , ");
		sb.append("    TRIM(KAKOJIKOKU_PRINT_KB)  , ");
		sb.append("    TRIM(CHORIYO_KOKOKUBUN_KB) , ");
		sb.append("    TRIM(HOZON_ONDOTAI_KB)     , ");
		sb.append("    TRIM(START_KB)             , ");
		sb.append("    TRIM(BACK_LABEL_KB)        , ");
		sb.append("    TRIM(EIYO_SEIBUN_NA)       , ");
		sb.append("    TRIM(COMMENT_KB)           , ");
		sb.append("    TRIM(BIKO_TX)              , ");
		sb.append("    TRIM(GENZAIRYO_NA)         , ");
		sb.append("    TRIM(TENKABUTU_NA)         , ");
		sb.append("    TRIM(MIN_WEIGHT_QT)        , ");
		sb.append("    TRIM(MAX_WEIGHT_QT)        , ");
		sb.append("    TRIM(TEIKAN_WEIGHT_QT)     , ");
		sb.append("    TRIM(FUTAI_WEIGHT_QT)      , ");
		sb.append("    TRIM(EYE_CATCH_NO)         , ");
		sb.append("    TRIM(EYE_CATCH_MODE)       , ");
		sb.append("    TRIM(TEIGAKU_KB)           , ");
		sb.append("    TEIGAKU_BAIKA_VL           , ");
		sb.append("    HOZON_ONDO_QT              , ");
		sb.append("    TRIM(CALORIE)              , ");
		sb.append("    TRIM(TRAY_NA)              , ");
		sb.append("    TRIM(NETSUKEKI_NA)         , ");
		sb.append("    TRIM(NETSUKEKI_NA_2)       , ");
		sb.append("    TRIM(INSERT_TS)            , ");
		sb.append("    TRIM(INSERT_USER_ID)       , ");
		sb.append("    TRIM(UPDATE_TS)            , ");
		sb.append("    TRIM(UPDATE_USER_ID)       , ");
		sb.append("    TRIM(BATCH_UPDATE_TS)      , ");
		sb.append("    TRIM(BATCH_UPDATE_ID)      , ");
		sb.append("    TRIM(DELETE_FG)            , ");
		sb.append("    '").append(timeStamp).append("' ");
		sb.append("FROM ");
		sb.append("    TMP_R_KEIRYOKI TB1 ");
		sb.append("WHERE ");
		sb.append("    TB1.YUKO_DT = ");
		sb.append("                  ( ");
		sb.append("                      SELECT ");
		sb.append("                          MAX( YUKO_DT ) ");
		sb.append("                      FROM ");
		sb.append("                          TMP_R_KEIRYOKI ");
		sb.append("                      WHERE ");
		sb.append("                          YUKO_DT <= '").append( DateChanger.addDate(batchDate, SYORI_SPAN) ).append("' ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sb.append("                      AND BUNRUI1_CD = TB1.BUNRUI1_CD ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sb.append("                      AND SYOHIN_CD = TB1.SYOHIN_CD ");
		sb.append("                  ) ");
		sb.append("AND TB1.DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("ORDER BY ");
		sb.append("    BUNRUI1_CD, ");
		sb.append("    SYOHIN_CD, ");
		sb.append("    YUKO_DT ");

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
