package mdware.master.batch.process.MSTB902;

import java.sql.SQLException;
import java.util.Calendar;

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
 * <p>タイトル:店別POS商品マスタ作成</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author o.uemura
 * @version 3.00 (2013/11/13) O.Uemura  [CUS00050] ランドローム様対応 POS売価不一致勧告対応
 * @version 3.01 (2014/03/20) M.Ayukawa [シス0138] 処理見直し 
 */
public class MSTB902030_CreateTenbetsuPosSyohinMaster {

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
	
	// 単価差異フラグ
	private static final String TANKA_SAI_FG_ON  = "1";
	private static final String TANKA_SAI_FG_OFF = "0";

	/*
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB902030_CreateTenbetsuPosSyohinMaster(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB902030_CreateTenbetsuPosSyohinMaster() {
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

			// バッチ処理件数をカウント（ログ出力用）
			int iRec = 0; 

			//*-------------------------------------
			//  WK_店別POS商品マスタ生成
			//-------------------------------------*
			// TRUNCATE
			writeLog(Level.INFO_INT, "WK_店別POS商品マスタ(WK_R_TEN_POS_SYOHIN)のデータクリア処理を開始します。");
			DBUtil.truncateTable(db, "WK_R_TEN_POS_SYOHIN");
			writeLog(Level.INFO_INT, "WK_店別POS商品マスタ(WK_R_TEN_POS_SYOHIN)のデータクリア処理を終了します。");
			
			// INSERT
			writeLog(Level.INFO_INT, "WK_店別POS商品マスタ(WK_R_TEN_POS_SYOHIN)への登録処理を開始します。");
			iRec = db.executeUpdate(getInsWkTenbetsuPosSyohinMasterSQL());
			db.commit();
			writeLog(Level.INFO_INT, "WK_店別POS商品マスタ(WK_R_TEN_POS_SYOHIN)への登録処理を完了しました (" + iRec + "件)");
			

			//*-------------------------------------
			//  WK_店別POS商品販売マスタ生成
			//-------------------------------------*		
			// TRUNCATE			
			writeLog(Level.INFO_INT, "WK_店別POS商品販売マスタ(WK_R_TEN_POS_SYOHIN_HANBAI)のデータクリア処理を開始します。");
			DBUtil.truncateTable(db, "WK_R_TEN_POS_SYOHIN_HANBAI");
			writeLog(Level.INFO_INT, "WK_店別POS商品販売マスタ(WK_R_TEN_POS_SYOHIN_HANBAI)のデータクリア処理を終了します。");
			
			// INSERT
			writeLog(Level.INFO_INT, "WK_店別POS商品販売マスタ(WK_R_TEN_POS_SYOHIN_HANBAI)への登録処理を開始します。");
			iRec = 0; 
			iRec = db.executeUpdate(getInsWkRTenPosSyohinHanbaiSQL());
			writeLog(Level.INFO_INT, "WK_店別POS商品販売マスタ(WK_R_TEN_POS_SYOHIN_HANBAI)への登録処理を完了しました (" + iRec + "件)");
			db.commit();
			
			//*-------------------------------------
			//  店別POS商品マスタ生成
			//-------------------------------------*
			// TRUNCATE
			writeLog(Level.INFO_INT, "店別POS商品マスタ(R_TEN_POS_SYOHIN)のデータクリア処理を開始します。");
			DBUtil.truncateTable(db, "R_TEN_POS_SYOHIN");
			writeLog(Level.INFO_INT, "店別POS商品マスタ(R_TEN_POS_SYOHIN)のデータをクリアしました。");
			
			// 登録処理
			writeLog(Level.INFO_INT, "店別POS商品マスタ(R_TEN_POS_SYOHIN)への登録処理を開始します。");
			iRec = 0; // 値のリセット
			iRec = db.executeUpdate(getInsTenbetsuPosSyohinMasterSQL());
			writeLog(Level.INFO_INT, "店別POS商品マスタ(R_TEN_POS_SYOHIN)への登録処理を完了しました (" + iRec + "件)");
			
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
     * 日別店別POS商品マスタより、全レコードをWK_店別POS商品マスタへ登録する。<br>
     * 
     * @param なし
     */
	private String getInsWkTenbetsuPosSyohinMasterSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT /*+ APPEND */ INTO                               ");
		sql.append("    WK_R_TEN_POS_SYOHIN                                 ");
		sql.append("( ");
		sql.append("     POS_HENKO_DT                                       ");
		sql.append("    ,TENPO_CD                                           ");
		sql.append("    ,TANPIN_CD                                          ");
		sql.append("    ,SYOHIN_NA                                          ");
		sql.append("    ,POS_HENKO_TM                                       ");
		sql.append("    ,JOTAI_FG                                           ");
		sql.append("    ,KOSHIN_FG                                          ");
		sql.append("    ,YOYAKU_FG                                          ");
		sql.append("    ,POS_TANKA_VL                                       ");
		sql.append("    ,INSERT_TS                                          ");
		sql.append("    ,INSERT_USER_ID                                     ");
		sql.append("    ,UPDATE_TS                                          ");
		sql.append("    ,UPDATE_USER_ID                                     ");
		sql.append(") ");
		sql.append("SELECT                                                  ");
		sql.append("     POS.POS_HENKO_DT                                   ");
		sql.append("    ,POS.TENPO_CD                                       ");
		sql.append("    ,POS.TANPIN_CD                                      ");
		sql.append("    ,POS.SYOHIN_NA                                      ");
		sql.append("    ,POS.POS_HENKO_TM                                   ");
		sql.append("    ,POS.JOTAI_FG                                       ");
		sql.append("    ,POS.KOSHIN_FG                                      ");
		sql.append("    ,POS.YOYAKU_FG                                      ");
		sql.append("    ,POS.POS_TANKA_VL                                   ");
		sql.append("    ,'"+systemDt+"'                                     ");
		sql.append("    ,'"+userLog.getJobId()+"'                           ");
		sql.append("    ,'"+systemDt+"'                                     ");
		sql.append("    ,'"+userLog.getJobId()+"'                           ");
		sql.append("FROM                                                    ");
		sql.append("    R_DAY_TEN_POS_SYOHIN POS                            ");
		sql.append("    INNER JOIN                                          ");
		sql.append("        (                                               ");
		sql.append("        SELECT                                          ");
		sql.append("             MAX(POS_HENKO_DT) POS_HENKO_DT             ");
		sql.append("            ,TENPO_CD                                   ");
		sql.append("            ,TANPIN_CD                                  ");
		sql.append("        FROM                                            ");
		sql.append("            R_DAY_TEN_POS_SYOHIN                        ");
		sql.append("        GROUP BY                                        ");
		sql.append("            TENPO_CD                                    ");
		sql.append("           ,TANPIN_CD                                   ");
		sql.append("        ) POS_MAX                                       ");
		sql.append("    ON                                                  ");
		sql.append("        POS.POS_HENKO_DT = POS_MAX.POS_HENKO_DT AND     ");
		sql.append("        POS.TENPO_CD     = POS_MAX.TENPO_CD     AND     ");
		sql.append("        POS.TANPIN_CD    = POS_MAX.TANPIN_CD            ");
		
		return sql.toString();
	}

    /**
     * <p>WK_店別POS商品販売マスタ生成</p>
     * 
     * @param なし
     */
    private String getInsWkRTenPosSyohinHanbaiSQL() throws SQLException {
    	String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
        StringBuffer sql = new StringBuffer();

		sql.append("INSERT /*+ APPEND */ INTO                                   ");
		sql.append("    WK_R_TEN_POS_SYOHIN_HANBAI                              ");
		sql.append("(                                                           ");
		sql.append("     TENPO_CD                                               ");
		sql.append("    ,TANPIN_CD                                              ");
		sql.append("    ,TEKIYO_START_DT                                        ");
		sql.append("    ,BUNRUI1_CD                                             ");
		sql.append("    ,BUNRUI2_CD                                             ");
		sql.append("    ,BUNRUI5_CD                                             ");
		sql.append("    ,HINMEI_KANJI_NA                                        ");
		sql.append("    ,BAITANKA_DAY_VL                                        ");
		sql.append("    ,BAIHEN_TYPE                                            ");
		sql.append("    ,INSERT_TS                                              ");
		sql.append("    ,INSERT_USER_ID                                         ");
		sql.append("    ,UPDATE_TS                                              ");
		sql.append("    ,UPDATE_USER_ID                                         ");
		sql.append(")                                                           ");
		sql.append("SELECT                                                      ");
		sql.append("     TRIM(RFH.TENPO_CD)                                     ");
		sql.append("    ,RFH.SYOHIN_CD                                          ");
		sql.append("    ,RFH.TEKIYO_START_DT                                    ");
		sql.append("    ,RFH.BUNRUI1_CD                                         ");
		sql.append("    ,RFH.BUNRUI2_CD                                         ");
		sql.append("    ,RFH.BUNRUI5_CD                                         ");
		sql.append("    ,RFH.HINMEI_KANJI_NA                                    ");
		sql.append("    ,RFH.BAITANKA_DAY_VL                                    ");
		sql.append("    ,RFH.BAIHEN_TYPE                                        ");
		sql.append("    ,'"+systemDt+"'                                         ");
		sql.append("    ,'"+userLog.getJobId()+"'                               ");
		sql.append("    ,'"+systemDt+"'                                         ");
		sql.append("    ,'"+userLog.getJobId()+"'                               ");
		sql.append("FROM                                                        ");
		sql.append("	(                                                       ");
		sql.append("	SELECT                                                  ");
		sql.append("	     RFH.TENPO_CD                                       ");
		sql.append("	    ,RFH.SYOHIN_CD                                      ");
		sql.append("	    ,MAX(RFH.TEKIYO_START_DT) TEKIYO_START_DT           ");
		sql.append("	FROM                                                    ");
		sql.append("	    R_FI_HANBAI RFH                                     ");
		sql.append("	    LEFT OUTER JOIN                                     ");
		sql.append("	        WK_R_TEN_POS_SYOHIN WPOS                        ");
		sql.append("	    ON                                                  ");
		sql.append("	        RFH.TENPO_CD  = WPOS.TENPO_CD AND               ");
		sql.append("	        RFH.SYOHIN_CD = WPOS.TANPIN_CD                  ");
		sql.append("	WHERE                                                   ");
		sql.append("	    RFH.TEKIYO_START_DT <= WPOS.POS_HENKO_DT            ");
		sql.append("	GROUP BY                                                ");
		sql.append("	     RFH.TENPO_CD                                       ");
		sql.append("	    ,RFH.SYOHIN_CD                                      ");
		sql.append("	) RFH_MAX                                               ");
		sql.append("	INNER JOIN                                              ");
		sql.append("		R_FI_HANBAI RFH                                     ");
		sql.append("	ON                                                      ");
		sql.append("		RFH_MAX.TENPO_CD        = RFH.TENPO_CD        AND   ");
		sql.append("		RFH_MAX.SYOHIN_CD       = RFH.SYOHIN_CD       AND   ");
		sql.append("		RFH_MAX.TEKIYO_START_DT = RFH.TEKIYO_START_DT       ");


		return sql.toString();
    }

    /**
     * <p>店別POS商品マスタへの登録処理</p>
     * 
     * 店別POS商品マスタに対し、「店舗コード」「単品コード」が<br>
     * 一致しない（：新規）レコードの登録処理を行う
     * 
     * @param なし
     */
	private String getInsTenbetsuPosSyohinMasterSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();
		
		// 実績取得期間
		String zengetu_start_dt;
		String zengetu_end_dt;
		String togetu_start_dt;
		String togetu_end_dt;
		
		// 前月
		zengetu_start_dt = getLastMonthDay(batchDate); 
		zengetu_end_dt   = getLastDay(zengetu_start_dt);
		// 当月
		togetu_start_dt  = getFirstDay(batchDate); 
		togetu_end_dt    = getLastDay(batchDate);
		
		writeLog(Level.INFO_INT, "実績取得期間　前月：" + zengetu_start_dt + "－" + zengetu_end_dt );
		writeLog(Level.INFO_INT, "実績取得期間　当月：" + togetu_start_dt  + "－" + togetu_end_dt  );

		
        sql.append("INSERT /*+ APPEND */ INTO                                                             ");
        sql.append("    R_TEN_POS_SYOHIN                                                                  ");
        sql.append("(                                                                                     ");
        sql.append("     TENPO_CD                                                                         ");
        sql.append("    ,TANPIN_CD                                                                        ");
        sql.append("    ,BUNRUI1_CD                                                                       ");
        sql.append("    ,BUNRUI2_CD                                                                       ");
        sql.append("    ,BUNRUI5_CD                                                                       ");
        sql.append("    ,SYOHIN_NA                                                                        ");
        sql.append("    ,POS_HENKO_DT                                                                     ");
        sql.append("    ,POS_HENKO_TM                                                                     ");
        sql.append("    ,JOTAI_FG                                                                         ");
        sql.append("    ,KOSHIN_FG                                                                        ");
        sql.append("    ,YOYAKU_FG                                                                        ");
        sql.append("    ,TANKA_SAI_FG                                                                     ");
        sql.append("    ,POS_TANKA_VL                                                                     ");
        sql.append("    ,BAITANKA_DAY_VL                                                                  ");
        sql.append("    ,BAIHEN_TYPE                                                                      ");
        sql.append("    ,ZENGETU_HANBAI_QT                                                                ");
        sql.append("    ,ZENGETU_HANBAI_VL                                                                ");
        sql.append("    ,ZENGETU_BAIHEN_VL                                                                ");
        sql.append("    ,ZENGETU_NEBIKI_VL                                                                ");
        sql.append("    ,TOGETU_HANBAI_QT                                                                 ");
        sql.append("    ,TOGETU_HANBAI_VL                                                                 ");
        sql.append("    ,TOGETU_BAIHEN_VL                                                                 ");
        sql.append("    ,TOGETU_NEBIKI_VL                                                                 ");
        sql.append("    ,INSERT_TS                                                                        ");
        sql.append("    ,INSERT_USER_ID                                                                   ");
        sql.append("    ,UPDATE_TS                                                                        ");
        sql.append("    ,UPDATE_USER_ID                                                                   ");
        sql.append(")                                                                                     ");
        sql.append("SELECT                                                                                ");
        sql.append("     WPOS.TENPO_CD                                                                    ");
        sql.append("    ,WPOS.TANPIN_CD                                                                   ");
        sql.append("    ,WHAN.BUNRUI1_CD                                                                  "); // 分類1 販売マスタ未存在時はNULL
        sql.append("    ,WHAN.BUNRUI2_CD                                                                  "); // 分類2 販売マスタ未存在時はNULL
        sql.append("    ,WHAN.BUNRUI5_CD                                                                  "); // 分類5 販売マスタ未存在時はNULL
        sql.append("    ,COALESCE(WHAN.HINMEI_KANJI_NA,SYOHIN_NA) SYOHIN_NA                               "); // 商品名 販売マスタ未存在時は　IF単価変更ログの商品名
        sql.append("    ,WPOS.POS_HENKO_DT                                                                ");
        sql.append("    ,WPOS.POS_HENKO_TM                                                                ");
        sql.append("    ,WPOS.JOTAI_FG                                                                    ");
        sql.append("    ,WPOS.KOSHIN_FG                                                                   ");
        sql.append("    ,WPOS.YOYAKU_FG                                                                   ");
        sql.append("    ,CASE                                                                             ");
        sql.append("       WHEN WHAN.TANPIN_CD IS NULL                                                    ");
        sql.append("        THEN '"+ TANKA_SAI_FG_OFF +"'                                                 ");
        sql.append("       WHEN COALESCE(WHAN.BAITANKA_DAY_VL,0) - COALESCE(WPOS.POS_TANKA_VL,0) = 0      ");
        sql.append("        THEN '"+ TANKA_SAI_FG_OFF +"'                                                 ");
        sql.append("        ELSE '"+ TANKA_SAI_FG_ON  +"'                                                 ");
        sql.append("     END                                          AS TANKA_SAI_FG                     "); // 単価差異フラグ 販売マスタ未存在時、又は単価差がない場合、「0:OFF」
        sql.append("    ,WPOS.POS_TANKA_VL                                                                ");
        sql.append("    ,WHAN.BAITANKA_DAY_VL                                                             "); // 売単価 販売マスタ未存在時はNULL
        sql.append("    ,WHAN.BAIHEN_TYPE                                                                 "); // 売変タイプ 販売マスタ未存在時はNULL
        sql.append("    ,COALESCE(ZENGETU_HANBAI_QT, 0)                                                   ");
        sql.append("    ,COALESCE(ZENGETU_HANBAI_VL, 0)                                                   ");
        sql.append("    ,COALESCE(ZENGETU_BAIHEN_VL, 0)                                                   ");
        sql.append("    ,COALESCE(ZENGETU_NEBIKI_VL, 0)                                                   ");
        sql.append("    ,COALESCE(TOGETU_HANBAI_QT,  0)                                                   ");
        sql.append("    ,COALESCE(TOGETU_HANBAI_VL,  0)                                                   ");
        sql.append("    ,COALESCE(TOGETU_BAIHEN_VL,  0)                                                   ");
        sql.append("    ,COALESCE(TOGETU_NEBIKI_VL,  0)                                                   ");
        sql.append("    ,'"+systemDt+"'                                                                   ");
        sql.append("    ,'"+userLog.getJobId()+"'                                                         ");
        sql.append("    ,'"+systemDt+"'                                                                   ");
        sql.append("    ,'"+userLog.getJobId()+"'                                                         ");
        sql.append("FROM                                                                                  ");
        sql.append("    WK_R_TEN_POS_SYOHIN WPOS                                                          ");
        sql.append("    LEFT OUTER JOIN                                                                   ");
        sql.append("        WK_R_TEN_POS_SYOHIN_HANBAI WHAN                                               ");
        sql.append("    ON                                                                                ");
        sql.append("        WPOS.TENPO_CD  = WHAN.TENPO_CD AND                                            ");
        sql.append("        WPOS.TANPIN_CD = WHAN.TANPIN_CD                                               ");
        // 販売実績取得：単品売上データ
        sql.append("    LEFT OUTER JOIN                                                                   ");
        sql.append("        (                                                                             ");
        sql.append("        SELECT                                                                        ");
        sql.append("             TRIM(TENPO_CD) TENPO_CD                                                  ");
        sql.append("            ,SYOHIN_CD                                                                ");
        sql.append("            ,SUM( CASE WHEN KEIJYO_DT BETWEEN '"+ zengetu_start_dt +"' AND '"+ zengetu_end_dt +"' THEN HANBAI_QT END ) ZENGETU_HANBAI_QT  ");
        sql.append("            ,SUM( CASE WHEN KEIJYO_DT BETWEEN '"+ zengetu_start_dt +"' AND '"+ zengetu_end_dt +"' THEN HANBAI_VL END ) ZENGETU_HANBAI_VL  ");
        sql.append("            ,SUM( CASE WHEN KEIJYO_DT BETWEEN '"+ togetu_start_dt  +"' AND '"+ togetu_end_dt  +"' THEN HANBAI_QT END ) TOGETU_HANBAI_QT   ");
        sql.append("            ,SUM( CASE WHEN KEIJYO_DT BETWEEN '"+ togetu_start_dt  +"' AND '"+ togetu_end_dt  +"' THEN HANBAI_VL END ) TOGETU_HANBAI_VL   ");
        sql.append("        FROM                                                                          ");
        sql.append("            DT_TANPIN_URIAGE                                                          ");
        sql.append("        GROUP BY                                                                      ");
        sql.append("             TENPO_CD                                                                 ");
        sql.append("            ,SYOHIN_CD                                                                ");
        sql.append("        ) URI                                                                         ");
        sql.append("    ON                                                                                ");
        sql.append("        WPOS.TENPO_CD  = URI.TENPO_CD AND                                             ");
        sql.append("        WPOS.TANPIN_CD = URI.SYOHIN_CD                                                ");
        // 売変実績取得：値引売変データ
        sql.append("    LEFT OUTER JOIN                                                                   ");
        sql.append("        (                                                                             ");
        sql.append("        SELECT                                                                        ");
        sql.append("             TENPO_CD                                                                 ");
        sql.append("            ,TANPIN_CD                                                                ");
        sql.append("            ,SUM( CASE WHEN HENKO_DT BETWEEN '"+ zengetu_start_dt +"' AND '"+ zengetu_end_dt +"' THEN BAIHENBAIKA_ZEIKOMI_VL END ) ZENGETU_BAIHEN_VL ");
        sql.append("            ,SUM( CASE WHEN HENKO_DT BETWEEN '"+ zengetu_start_dt +"' AND '"+ zengetu_end_dt +"' THEN NEBIKIBAIKA_ZEIKOMI_VL END ) ZENGETU_NEBIKI_VL ");
        sql.append("            ,SUM( CASE WHEN HENKO_DT BETWEEN '"+ togetu_start_dt  +"' AND '"+ togetu_end_dt  +"' THEN BAIHENBAIKA_ZEIKOMI_VL END ) TOGETU_BAIHEN_VL  ");
        sql.append("            ,SUM( CASE WHEN HENKO_DT BETWEEN '"+ togetu_start_dt  +"' AND '"+ togetu_end_dt  +"' THEN NEBIKIBAIKA_ZEIKOMI_VL END ) TOGETU_NEBIKI_VL  ");
        sql.append("        FROM                                                                          ");
        sql.append("            DT_NEBIKI_BAIHEN                                                          ");
        sql.append("        GROUP BY                                                                      ");
        sql.append("             TENPO_CD                                                                 ");
        sql.append("            ,TANPIN_CD                                                                ");
        sql.append("        ) BHN                                                                         ");
        sql.append("    ON                                                                                ");
        sql.append("        WPOS.TENPO_CD  = BHN.TENPO_CD AND                                             ");
        sql.append("        WPOS.TANPIN_CD = BHN.TANPIN_CD                                                ");


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
		
	}

	/**
	 * 指定した日付文字列（yyyy/MM/dd or yyyy-MM-dd）
	 * における先月初日を返します。
	 * 
	 * @param strDate 対象の日付文字列
	 * @return 先月初日
	 */
	public static String getLastMonthDay(String strDate) {
		int yyyy = Integer.parseInt(strDate.substring(0,4));
		int MM = Integer.parseInt(strDate.substring(4,6));
		int dd = Integer.parseInt(strDate.substring(6,8));
		String lastMonth = "";
		Calendar cal = Calendar.getInstance();
		cal.set(yyyy,MM-1,dd);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		if(month < 10) {
			lastMonth = year + "0" + Integer.toString(month) + "01";
		} else {
			lastMonth = year +  Integer.toString(month) + "01";
		}
		return lastMonth;
	}
	/**
	 * 指定した日付文字列（yyyy/MM/dd or yyyy-MM-dd）
	 * における月初日付を返します。
	 * 
	 * @param strDate 対象の日付文字列
	 * @return 月初日付
	 */
	public static String getFirstDay(String strDate) {
		String firstDay = strDate.substring(0,6) +  "01";
		return firstDay;
	}
	/**
	 * 指定した日付文字列（yyyy/MM/dd or yyyy-MM-dd）
	 * における月末日付を返します。
	 * 
	 * @param strDate 対象の日付文字列
	 * @return 月末日付
	 */
	public static String getLastDay(String strDate) {
		int yyyy = Integer.parseInt(strDate.substring(0,4));
		int MM = Integer.parseInt(strDate.substring(4,6));
		int dd = Integer.parseInt(strDate.substring(6,8));
		Calendar cal = Calendar.getInstance();
		cal.set(yyyy,MM-1,dd);
		int last = cal.getActualMaximum(Calendar.DATE);
		String lastDay = strDate.substring(0,6) + Integer.toString(last);
		return lastDay;
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
