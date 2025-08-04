package mdware.master.batch.process.MSTB902;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:日別店別POS商品マスタ作成</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author o.uemura
 * @version 1.00 Sou ORACLE11対応
 */
public class MSTB902020_CreateHibetsuTenbetsuPosSyohinMaster {

	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	private MasterDataBase db = null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = null;
	// 有効日
	private String yukoDate = null;
	// 予約フラグ
	private String saiyoYoyakuFg = null;

	// 状態フラグ
	private static final String JOTAI_FG_DEL  = "02"; // 02:削除

	/*
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB902020_CreateHibetsuTenbetsuPosSyohinMaster(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB902020_CreateHibetsuTenbetsuPosSyohinMaster() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}


	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try{

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();
			writeLog(Level.INFO_INT, "バッチ日付：" + batchDate);
			writeLog(Level.INFO_INT, "有効日：" + yukoDate);
			writeLog(Level.INFO_INT, "採用予約フラグ：" + saiyoYoyakuFg);

			// バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			//*-------------------------------------
			//  WK_日別店別POS商品マスタ生成
			//-------------------------------------*
			// TRUNCATE
			writeLog(Level.INFO_INT, "WK_日別店別POS商品マスタ(WK_R_DAY_TEN_POS_SYOHIN)のデータクリア処理を開始します。");
			DBUtil.truncateTable(db, "WK_R_DAY_TEN_POS_SYOHIN");
			writeLog(Level.INFO_INT, "WK_日別店別POS商品マスタ(WK_R_DAY_TEN_POS_SYOHIN)のデータクリア処理を終了します。");

			// INSERT
			writeLog(Level.INFO_INT, "WK_日別店別POS商品マスタ(WK_R_DAY_TEN_POS_SYOHIN)への登録処理を開始します。");
			iRec = db.executeUpdate(getInsTmpHibetsuTenbetsuPosSyohinMasterSQL(saiyoYoyakuFg));
			writeLog(Level.INFO_INT, "WK_日別店別POS商品マスタ(WK_R_DAY_TEN_POS_SYOHIN)への登録処理を完了しました (" + iRec + "件)");

			db.commit();

			//*-------------------------------------
			//  WK_日別店別POS商品マスタ 優先判定
			//-------------------------------------*
			writeLog(Level.INFO_INT, "WK_日別店別POS商品マスタ 優先判定処理を開始します。");
			// 未来日レコード削除
			iRec = 0;
			iRec = db.executeUpdate(deleteHenkoDtFutureSQL(batchDate));
			writeLog(Level.INFO_INT, "未来レコード削除処理を完了しました (" + iRec + "件)");

			// 変更時刻 最新以外削除
			iRec = 0;
			iRec = db.executeUpdate(deleteHenkoTmOlderSQL());
			writeLog(Level.INFO_INT, "重複レコード(変更日/店舗/単品)「変更時刻 最新  」以外を削除しました (" + iRec + "件)");

			// POS単価 最安価以外削除
			iRec = 0;
			iRec = db.executeUpdate(deletePosTankaHigherSQL());
			writeLog(Level.INFO_INT, "重複レコード(変更日/店舗/単品)「POS単価  最安価」以外を削除しました (" + iRec + "件)");

			// 行番号 最新以外削除
			iRec = 0;
			iRec = db.executeUpdate(deleteSeqLowerSQL());
			writeLog(Level.INFO_INT, "重複レコード(変更日/店舗/単品)「行番号   最新  」以外を削除しました (" + iRec + "件)");
			writeLog(Level.INFO_INT, "WK_日別店別POS商品マスタ 優先判定処理を終了します。");

			db.commit();

			//*-------------------------------------
			//  日別店別POS商品マスタ生成
			//-------------------------------------*
			// UPDATE
			writeLog(Level.INFO_INT, "日別店別POS商品マスタ(R_DAY_TEN_POS_SYOHIN)への更新処理を開始します。");
			iRec = 0;
			iRec = db.executeUpdate(getUpdateHibetsuTenbetsuPosSyohinMasterSQL());
			writeLog(Level.INFO_INT, "日別店別POS商品マスタ(R_DAY_TEN_POS_SYOHIN)への更新処理を完了しました (" + iRec + "件)");

			// INSERT
			writeLog(Level.INFO_INT, "日別店別POS商品マスタ(R_DAY_TEN_POS_SYOHIN)への登録処理を開始します。");
			iRec = 0;
			iRec = db.executeUpdate(getInsHibetsuTenbetsuPosSyohinMasterSQL());
			writeLog(Level.INFO_INT, "日別店別POS商品マスタ(R_DAY_TEN_POS_SYOHIN)への登録処理を完了しました (" + iRec + "件)");

			db.commit();

			writeLog(Level.INFO_INT, "処理を終了します。");


		//SQLエラーが発生した場合の処理
		}catch(SQLException se){
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

		//その他のエラーが発生した場合の処理
		}catch(Exception e){
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

		//SQL終了処理
		}finally{
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

    /**
     * <p>一時テーブルへの取込処理</p>
     *
     * WK_POS単価変更ログより、全レコードをWK_日別店別POS商品マスタ候補へ登録する。<br>
     * その際、変更日、変更時刻、店舗コードを整形する。
     *
     * @param なし
     */
	private String getInsTmpHibetsuTenbetsuPosSyohinMasterSQL(String saiyoYoyakuFg)  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT /*+ APPEND */ INTO                   ");
		sql.append("    WK_R_DAY_TEN_POS_SYOHIN                 ");
		sql.append("( ");
		sql.append("     SEQ                                    ");
		sql.append("    ,POS_HENKO_DT                           ");
		sql.append("    ,POS_HENKO_TM                           ");
		sql.append("    ,TENPO_CD                               ");
		sql.append("    ,SYOHIN_NA                              ");
		sql.append("    ,JOTAI_FG                               ");
		sql.append("    ,KOSHIN_FG                              ");
		sql.append("    ,YOYAKU_FG                              ");
		sql.append("    ,TANPIN_CD                              ");
		sql.append("    ,POS_TANKA_VL                           ");
		sql.append("    ,INSERT_TS                              ");
		sql.append("    ,INSERT_USER_ID                         ");
		sql.append("    ,UPDATE_TS                              ");
		sql.append("    ,UPDATE_USER_ID                         ");
		sql.append(") ");
		sql.append("SELECT                                      ");
		sql.append("     SEQ                                    ");
		sql.append("    ,SUBSTR(HENKO_DT, 1, 4) || SUBSTR(HENKO_DT, 6, 2) || SUBSTR(HENKO_DT, 9, 2) ");
		sql.append("    ,SUBSTR(HENKO_TM, 1, 2) || SUBSTR(HENKO_TM, 4, 2) ");
		sql.append("    ,SUBSTR(TENPO_CD, 2, 4)                 ");
		sql.append("    ,TANPIN_NA                              ");
		sql.append("    ,JOTAI_FG                               ");
		sql.append("    ,HENKO_FG                               ");
		sql.append("    ,YOYAKU_FG                              ");
		sql.append("    ,TANPIN_CD                              ");
		sql.append("    ,CASE WHEN JOTAI_FG = '"+ JOTAI_FG_DEL+ "' THEN JITSUBAI_VL ELSE SHIN_TANKA_VL END ");
		sql.append("    ,'"+systemDt+"'                         ");
		sql.append("    ,'"+userLog.getJobId()+"'               ");
		sql.append("    ,'"+systemDt+"'                         ");
		sql.append("    ,'"+userLog.getJobId()+"'               ");
		sql.append("FROM ");
		sql.append("    WK_POS_TANKA_HENKO_LOG                  ");
		sql.append("WHERE ");
		sql.append("    YOYAKU_FG IN ("+ saiyoYoyakuFg +")      ");

		return sql.toString();
	}

    /**
     * <p>未来日レコードの除外</p>
     *
     * WK_日別店別POS商品マスタ候補に対し、変更日が未来日のレコードを削除する。<br>
     *
     * @param batchDate
     */
    private String deleteHenkoDtFutureSQL(String batchDate) throws SQLException {

        StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM                     ");
		sql.append("    WK_R_DAY_TEN_POS_SYOHIN     ");
		sql.append("WHERE                           ");
		sql.append("    POS_HENKO_DT > " +batchDate  );

		return sql.toString();
    }

    /**
     * <p>変更時刻による選別</p>
     *
     * WK_日別店別POS商品マスタ候補より変更日、店舗コード、商品コードで集約し、<br>
     * 変更時刻が優先のデータ以外を削除する。
     *
     * @param なし
     */
    private String deleteHenkoTmOlderSQL() throws SQLException {

        StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM                             ");
		sql.append("    WK_R_DAY_TEN_POS_SYOHIN             ");
		sql.append("WHERE                                   ");
		sql.append("    SEQ                                 ");
		sql.append("    IN                                  ");
		sql.append("    (                                   ");
		sql.append("        SELECT                          ");
		sql.append("            SEQ                         ");
		sql.append("        FROM                            ");
		sql.append("        (                               ");
		sql.append("            SELECT                      ");
		sql.append("                 SEQ                    ");
		sql.append("                ,RANK() OVER(PARTITION BY POS_HENKO_DT, TENPO_CD, TANPIN_CD ORDER BY POS_HENKO_TM DESC) AS RANKING ");
		sql.append("            FROM                        ");
		sql.append("                WK_R_DAY_TEN_POS_SYOHIN ");
		sql.append("        )                               ");
		sql.append("        WHERE                           ");
		sql.append("            RANKING > 1                 ");
		sql.append("    )                                   ");

		return sql.toString();
    }

    /**
     * <p>POS単価による選別</p>
     *
     * WK_日別店別POS商品マスタ候補より変更日、店舗コード、商品コードで集約し、<br>
     * POS単価が優先のデータ以外を削除する。
     *
     * @param なし
     */
    private String deletePosTankaHigherSQL() throws SQLException {

        StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM                             ");
		sql.append("    WK_R_DAY_TEN_POS_SYOHIN             ");
		sql.append("WHERE                                   ");
		sql.append("    SEQ                                 ");
		sql.append("    IN                                  ");
		sql.append("    (                                   ");
		sql.append("        SELECT                          ");
		sql.append("            SEQ                         ");
		sql.append("        FROM                            ");
		sql.append("        (                               ");
		sql.append("            SELECT                      ");
		sql.append("                 SEQ                    ");
		sql.append("                ,RANK() OVER(PARTITION BY POS_HENKO_DT, TENPO_CD, TANPIN_CD ORDER BY POS_TANKA_VL ASC) AS RANKING ");
		sql.append("            FROM                        ");
		sql.append("                WK_R_DAY_TEN_POS_SYOHIN ");
		sql.append("        )                               ");
		sql.append("        WHERE                           ");
		sql.append("            RANKING > 1                 ");
		sql.append("    )                                   ");

		return sql.toString();
    }

    /**
     * <p>行番号による選別</p>
     *
     * WK_日別店別POS商品マスタ候補より変更日、店舗コード、商品コードで集約し、<br>
     * 行番号が優先のデータ以外を削除する。
     *
     * @param なし
     */
    private String deleteSeqLowerSQL() throws SQLException {

        StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM                              ");
		sql.append("    WK_R_DAY_TEN_POS_SYOHIN              ");
		sql.append("WHERE                                    ");
		sql.append("    SEQ                                  ");
		sql.append("    IN                                   ");
		sql.append("    (                                    ");
		sql.append("        SELECT                           ");
		sql.append("            SEQ                          ");
		sql.append("        FROM                             ");
		sql.append("        (                                ");
		sql.append("            SELECT                       ");
		sql.append("                 SEQ                     ");
		sql.append("                ,RANK() OVER(PARTITION BY POS_HENKO_DT, TENPO_CD, TANPIN_CD ORDER BY SEQ DESC) AS RANKING ");
		sql.append("            FROM                         ");
		sql.append("            WK_R_DAY_TEN_POS_SYOHIN      ");
		sql.append("        )                                ");
		sql.append("        WHERE                            ");
		sql.append("            RANKING > 1                  ");
		sql.append("    )                                    ");

		return sql.toString();
    }

    /**
     * <p>日別店別POS商品マスタへの更新処理</p>
     *
     * 日別店別POS商品マスタに対し、「POS変更日」「店舗コード」「単品コード」が<br>
     * 一致するレコードの更新処理を行う
     *
     * @param なし
     */
	private String getUpdateHibetsuTenbetsuPosSyohinMasterSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();
		// 2015/07/21 Sou ORACLE11対応 Start
//		sql.append("UPDATE 												");
//		sql.append("    (                                               ");
//		sql.append("    SELECT /*+ BYPASS_UJVC */                       ");
//		sql.append("         DAY.POS_HENKO_TM    AS SET_POS_HENKO_TM    ");
//		sql.append("        ,DAY.SYOHIN_NA       AS SET_SYOHIN_NA       ");
//		sql.append("        ,DAY.JOTAI_FG        AS SET_JOTAI_FG        ");
//		sql.append("        ,DAY.KOSHIN_FG       AS SET_KOSHIN_FG       ");
//		sql.append("        ,DAY.YOYAKU_FG       AS SET_YOYAKU_FG       ");
//		sql.append("        ,DAY.POS_TANKA_VL    AS SET_POS_TANKA_VL    ");
//		sql.append("        ,DAY.UPDATE_TS       AS SET_UPDATE_TS       ");
//		sql.append("        ,DAY.UPDATE_USER_ID  AS SET_UPDATE_USER_ID  ");
//		sql.append("        ,WDAY.POS_HENKO_TM   AS UPD_POS_HENKO_TM    ");
//		sql.append("        ,WDAY.SYOHIN_NA      AS UPD_SYOHIN_NA       ");
//		sql.append("        ,WDAY.JOTAI_FG       AS UPD_JOTAI_FG        ");
//		sql.append("        ,WDAY.KOSHIN_FG      AS UPD_KOSHIN_FG       ");
//		sql.append("        ,WDAY.YOYAKU_FG      AS UPD_YOYAKU_FG       ");
//		sql.append("        ,WDAY.POS_TANKA_VL   AS UPD_POS_TANKA_VL    ");
//		sql.append("    FROM                                            ");
//		sql.append("        R_DAY_TEN_POS_SYOHIN DAY                    ");
//		sql.append("        INNER JOIN                                  ");
//		sql.append("            WK_R_DAY_TEN_POS_SYOHIN WDAY            ");
//		sql.append("        ON                                          ");
//		sql.append("            DAY.POS_HENKO_DT = WDAY.POS_HENKO_DT AND");
//		sql.append("            DAY.TENPO_CD     = WDAY.TENPO_CD     AND");
//		sql.append("            DAY.TANPIN_CD    = WDAY.TANPIN_CD       ");
//		sql.append("    )                                               ");
//		sql.append("SET                                                 ");
//		sql.append("     SET_POS_HENKO_TM   = UPD_POS_HENKO_TM          ");
//		sql.append("    ,SET_SYOHIN_NA      = UPD_SYOHIN_NA             ");
//		sql.append("    ,SET_JOTAI_FG       = UPD_JOTAI_FG              ");
//		sql.append("    ,SET_KOSHIN_FG      = UPD_KOSHIN_FG             ");
//		sql.append("    ,SET_YOYAKU_FG      = UPD_YOYAKU_FG             ");
//		sql.append("    ,SET_POS_TANKA_VL   = UPD_POS_TANKA_VL          ");
//		sql.append("    ,SET_UPDATE_TS      = '"+systemDt+"'            ");
//		sql.append("    ,SET_UPDATE_USER_ID = '"+userLog.getJobId()+"'  ");

		sql.append(" MERGE INTO ");
		sql.append("     R_DAY_TEN_POS_SYOHIN DAY ");
		sql.append(" USING ");
		sql.append("     WK_R_DAY_TEN_POS_SYOHIN WDAY ");
		sql.append(" ON (DAY.POS_HENKO_DT = WDAY.POS_HENKO_DT ");
		sql.append("     AND DAY.TENPO_CD     = WDAY.TENPO_CD ");
		sql.append("     AND DAY.TANPIN_CD    = WDAY.TANPIN_CD ) ");
		sql.append(" WHEN MATCHED THEN ");
		sql.append("  UPDATE SET ");
		sql.append("      DAY.POS_HENKO_TM   = WDAY.POS_HENKO_TM ");
		sql.append("     ,DAY.SYOHIN_NA      = WDAY.SYOHIN_NA ");
		sql.append("     ,DAY.JOTAI_FG       = WDAY.JOTAI_FG ");
		sql.append("     ,DAY.KOSHIN_FG      = WDAY.KOSHIN_FG ");
		sql.append("     ,DAY.YOYAKU_FG      = WDAY.YOYAKU_FG ");
		sql.append("     ,DAY.POS_TANKA_VL   = WDAY.POS_TANKA_VL ");
		sql.append("     ,DAY.UPDATE_TS      = '"+systemDt+"' ");
		sql.append("     ,DAY.UPDATE_USER_ID = '"+userLog.getJobId()+"' ");
		// 2015/07/21 Sou ORACLE11対応 End
		return sql.toString();
	}

    /**
     * <p>日別店別POS商品マスタへの登録処理</p>
     *
     * 日別店別POS商品マスタに対し、「POS変更日」「店舗コード」「単品コード」が<br>
     * 一致しない（：新規）レコードの登録処理を行う
     *
     * @param なし
     */
	private String getInsHibetsuTenbetsuPosSyohinMasterSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ");
		sql.append("    R_DAY_TEN_POS_SYOHIN RDTPS                   ");
		sql.append("( ");
		sql.append("     POS_HENKO_DT                                ");
		sql.append("    ,TENPO_CD                                    ");
		sql.append("    ,TANPIN_CD                                   ");
		sql.append("    ,SYOHIN_NA                                   ");
		sql.append("    ,POS_HENKO_TM                                ");
		sql.append("    ,JOTAI_FG                                    ");
		sql.append("    ,KOSHIN_FG                                   ");
		sql.append("    ,YOYAKU_FG                                   ");
		sql.append("    ,POS_TANKA_VL                                ");
		sql.append("    ,INSERT_TS                                   ");
		sql.append("    ,INSERT_USER_ID                              ");
		sql.append("    ,UPDATE_TS                                   ");
		sql.append("    ,UPDATE_USER_ID                              ");
		sql.append(") ");
		sql.append("SELECT ");
		sql.append("     POS_HENKO_DT                                ");
		sql.append("    ,TENPO_CD                                    ");
		sql.append("    ,TANPIN_CD                                   ");
		sql.append("    ,SYOHIN_NA                                   ");
		sql.append("    ,POS_HENKO_TM                                ");
		sql.append("    ,JOTAI_FG                                    ");
		sql.append("    ,KOSHIN_FG                                   ");
		sql.append("    ,YOYAKU_FG                                   ");
		sql.append("    ,POS_TANKA_VL                                ");
		sql.append("    ,'"+systemDt+"'                              ");
		sql.append("    ,'"+userLog.getJobId()+"'                    ");
		sql.append("    ,'"+systemDt+"'                              ");
		sql.append("    ,'"+userLog.getJobId()+"'                    ");
		sql.append("FROM ");
		sql.append("    WK_R_DAY_TEN_POS_SYOHIN WDAY                 ");
		sql.append("WHERE NOT EXISTS                                 ");
		sql.append("( ");
		sql.append("    SELECT                                       ");
		sql.append("        ''                                       ");
		sql.append("    FROM                                         ");
		sql.append("        R_DAY_TEN_POS_SYOHIN DAY ");
		sql.append("    WHERE ");
		sql.append("        DAY.POS_HENKO_DT = WDAY.POS_HENKO_DT AND ");
		sql.append("        DAY.TENPO_CD     = WDAY.TENPO_CD     AND ");
		sql.append("        DAY.TANPIN_CD    = WDAY.TANPIN_CD        ");
		sql.append(") ");

		return sql.toString();
	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// バッチ日付取得
		batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
		if(batchDate == null || batchDate.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
			throw new Exception();
		}

		// 有効日
		yukoDate = DateChanger.addDate(batchDate, SPAN_DAYS);

		// 採用予約フラグ取得
		saiyoYoyakuFg = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_TANKA_HENKO_LOG_SAIYO_YOYAKU_FG);
		if(saiyoYoyakuFg == null || saiyoYoyakuFg.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、採用予約フラグが取得できませんでした");
			throw new Exception();
		}
	}



/********** 共通処理 **********/

	/**
	 * ユーザーログとバッチログにログを出力します。
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
