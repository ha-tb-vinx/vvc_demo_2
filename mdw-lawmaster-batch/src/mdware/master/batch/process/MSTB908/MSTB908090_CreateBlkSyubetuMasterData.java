package mdware.master.batch.process.MSTB908;

import java.nio.CharBuffer;
import java.sql.SQLException;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;
/**
*
* <p>タイトル: MSTB908090_CreateBlkSyubetuMasterData.java クラス</p>
* <p>説明　: Blynkに連携する支払種別マスタ、特売種別マスタデータを作成する</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 (2016.09.15) VINX H.Sakamoto #1748対応
* @version 1.01 (2016.10.17) M.Akagi #1748対応
* @version 1.02 (2016.10.18) VINX S.Takayama #2238 対応
* @version 1.03 (2016.12.19) Li.Sheng #3233
* @version 1.04 (2016.12.22) S.Takayama #3382 FIVIMART対応
* @version 1.05 (2016.12.22) T.Han #3212 FIVIMART??
* @version 1.06 (2017.01.12) T.Han #3583 FIVIMART対応
* @version 1.07 (2017.02.02) S.Takayama #3853 FIVIMART対応
* @version 1.08 (2017.04.06) M.Son #2463 FIVIMART対応
* @version 1.09 (2017.04.12) M.Akagi #4611
* @version 1.10 (2017.04.24) S.Nakazato #4824
* @version 1.11 (2017.05.17) M.Son #5044
*/
public class MSTB908090_CreateBlkSyubetuMasterData {

    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();
    // バッチ日付
    private String batchDt = null;
    
    // #2238 MSTB908 2016.10.24 S.Takayama (S)
    // 翌日のバッチ日付
    private String yokuBatchDt = null;
    // #2238 MSTB908 2016.10.24 S.Takayama (E)
    
	// #3382 MSTB908 2016.12.22 S.Takayama (S)
	/** 最大レシート発行数 */
	private String maxImumReceipt = "";
	private String MAXIMUM_RECEIPT = "";
	// #3382 MSTB908 2016.12.22 S.Takayama (E)
    
	// 2017.01.12 T.Han #3583対応（S)
	/** 自家消費レシート印字文言（VN） */
	private static final int LENGTH_SHIHARAI_SYUBETSU_SUB_NA = 40;
	// 2017.01.12 T.Han #3583対応（E)

    // テーブル
    private static final String TABLE_IF_BLK_PAYMENT = "IF_BLK_PAYMENT"; // IF_BLK支払種別マスタ
    private static final String TABLE_IF_BLK_DISCOUNT = "IF_BLK_DISCOUNT"; // IF_BLK特売種別マスタ
    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
    private String jobId =userLog.getJobId();

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB908090_CreateBlkSyubetuMasterData(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB908090_CreateBlkSyubetuMasterData() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }

    /**
     * 本処理
     * @throws Exception
     */
    public void execute() throws Exception {
        userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
        try {

            //バッチ処理件数をカウント（ログ出力用）
            int iRec = 0;

            // トランザクションログ有無（AutoCommitモード）
            // （trueを指定すると、トランザクションログ出力をしない分の速度向上
            // 　が見込めますが、コミット・ロールバックが無効となります。）
            db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");
            // バッチ日付
            batchDt = RMSTDATEUtil.getBatDateDt();
            
            // #2238 MSTB908 2016.10.24 S.Takayama (S)
            yokuBatchDt = DateChanger.addDate(batchDt, 1);
            // #2238 MSTB908 2016.10.24 S.Takayama (E)
            
			// #3382 MSTB908 2016.12.22 S.Takayama (S)
			// システムコントロール情報取得
			this.getSystemControl();
			MAXIMUM_RECEIPT = maxImumReceipt;
			// #3382 MSTB908 2016.12.22 S.Takayama (E)
            
            // IF_BLK支払種別マスタTRUNCATE
            writeLog(Level.INFO_INT, "IF_BLK支払種別マスタ削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_IF_BLK_PAYMENT);
            writeLog(Level.INFO_INT, "IF_BLK支払種別マスタを削除処理を終了します。");

            // IF_BLK特売種別マスタTRUNCATE
            writeLog(Level.INFO_INT, "IF_BLK特売種別マスタ削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_IF_BLK_DISCOUNT);
            writeLog(Level.INFO_INT, "IF_BLK特売種別マスタを削除処理を終了します。");

            // IF_BLK支払種別マスタの登録
            writeLog(Level.INFO_INT, "IF_BLK支払種別マスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkPaymentInsertSql());
            writeLog(Level.INFO_INT, "IF_BLK支払種別マスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLK支払種別マスタ登録処理を終了します。");

            db.commit();

            // IF_BLK特売種別マスタの登録
            writeLog(Level.INFO_INT, "IF_BLK特売種別マスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkDiscountInsertSql());
            writeLog(Level.INFO_INT, "IF_BLK特売種別マスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLK特売種別マスタ登録処理を終了します。");

            db.commit();

            writeLog(Level.INFO_INT, "処理を終了します。");

            //SQLエラーが発生した場合の処理
        } catch (SQLException se) {
            db.rollback();
            writeLog(Level.ERROR_INT, "ロールバックしました。");
            this.writeError(se);
            throw se;

            //その他のエラーが発生した場合の処理
        } catch (Exception e) {
            db.rollback();
            writeLog(Level.ERROR_INT, "ロールバックしました。");
            this.writeError(e);
            throw e;

            //SQL終了処理
        } finally {
            if (closeDb || db != null) {
                db.close();
            }
        }

    }
    
    /**
     * IF_BLK支払種別マスタを登録するSQLを取得
     * 
     * @return
     * @throws SQLException
     */
    private String getIfBlkPaymentInsertSql() throws SQLException{
        // 2017.04.06 M.Son #2463対応（S)
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
        // 2017.04.06 M.Son #2463対応（E)

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT ");
        sql.append("INTO IF_BLK_PAYMENT( ");
        sql.append("  DATA_MAKE_DT");
        sql.append("  , TOROKU_ID");
        sql.append("  , SHIHARAI_SYUBETSU_SEQ");
        sql.append("  , SHIHARAI_SYUBETSU_EN_NA");
        sql.append("  , SHIHARAI_SYUBETSU_VN_NA");
        sql.append("  , POS_SEQ");
        sql.append("  , OVER_TYPE");
        sql.append("  , NEED_AUTHORITY");
        sql.append("  , NEED_EXPIRY");
        sql.append("  , CARD_NUMBER");
        sql.append("  , PROCESS_TYPE");
        sql.append("  , SHIHARAI_SYUBETSU_GROUP_SEQ");
        sql.append("  , CARD_LENGTH");
        sql.append("  , SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("  , DISPLAY_FG");
        sql.append("  , VOID_FG");
        sql.append("  , RETURN_FG");
        sql.append("  , OPEN_DRAWER_FG");
        sql.append("  , EXTRA_RECEIPT");
        sql.append("  , MAXIMUM_RECEIPT");
        sql.append("  , YUKO_START_DT");
        sql.append("  , YUKO_END_DT");
		// 2017.01.12 T.Han #3583対応（S)
		sql.append("  , JIKASYOHI_KB ");
		sql.append("  , JIKASYOHI_RECEIPT_VN_NA ");
		// 2017.01.12 T.Han #3583対応（E)
        // 2017.04.06 M.Son #2463対応（S)
        sql.append("  , INSERT_TS ");
        sql.append("  , INSERT_USER_ID ");
        sql.append("  , UPDATE_TS ");
        sql.append("  , UPDATE_USER_ID ");
        // 2017.04.06 M.Son #2463対応（E)
        sql.append(") ");
        sql.append("SELECT");
        // #2238 MSTB908090 2016.10.18 S.Takayama (S)
        //sql.append("  '"+batchDt+"'");
        sql.append("  '"+yokuBatchDt+"'");
        // #2238 MSTB908090 2016.10.18 S.Takayama (E)
        sql.append("  , 'A'");
        sql.append("  , SHIHARAI_SYUBETSU_SEQ");
        // 2017.04.12 M.Akagi #4611 (S)
        // 2016.12.22 T.Han #3212対応（S)
        //sql.append("  , SHIHARAI_SYUBETSU_EN_NA");
        //sql.append("  , SUBSTR(RPAD(NVL(SHIHARAI_SYUBETSU_EN_NA, ' '), 40), 0, 20) ");
        sql.append("  , SHIHARAI_SYUBETSU_EN_NA");
        //sql.append("  , SHIHARAI_SYUBETSU_VN_NA");
        //sql.append("  , SUBSTR(RPAD(NVL(SHIHARAI_SYUBETSU_VN_NA, ' '), 40), 0, 20) ");
        sql.append("  , SHIHARAI_SYUBETSU_VN_NA");
        // 2016.12.22 T.Han #3212対応（E)
        // 2017.04.12 M.Akagi #4611 (E)
        sql.append("  , POS_SEQ");
        sql.append("  , OVER_TYPE");
        sql.append("  , ' '");
        sql.append("  , ' '");
        sql.append("  , ' '");
        sql.append("  , PROCESS_TYPE");
        sql.append("  , SHIHARAI_SYUBETSU_GROUP_SEQ");
        sql.append("  , ' '");
        // #3853 MSTB908 2017.02.02 S.Takayama (S)
        //2016.10.17 M.Akagi #1748 (S)
        //sql.append("  , SHIHARAI_SYUBETSU_SUB_CD");
        //sql.append("	,CASE WHEN RDC.PARAMETER_TX = '1' AND SHIHARAI_SYUBETSU_GROUP_SEQ IS NULL THEN '     ' ");
        //sql.append("	ELSE SHIHARAI_SYUBETSU_SUB_CD END SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("  , SHIHARAI_SYUBETSU_SUB_CD");
        // #3853 MSTB908 2017.02.02 S.Takayama (E)
        //2016.10.17 M.Akagi #1748 (S)
        sql.append("  , DISPLAY_FG");
        sql.append("  , VOID_FG");
        sql.append("  , RETURN_FG");
        sql.append("  , OPEN_DRAWER_FG");
        sql.append("  , EXTRA_RECEIPT");
		// #3382 MSTB908 2016.12.22 S.Takayama (S)
		//sql.append("  , MAXIMUM_RECEIPT");
		sql.append("  ,'" + MAXIMUM_RECEIPT + "' AS MAXIMUM_RECEIPT");
		// #3382 MSTB908 2016.12.22 S.Takayama (E)
        sql.append("  , NVL(YUKO_START_DT,'        ')");
        sql.append("  , NVL(YUKO_END_DT,'        ') ");
		// 2017.01.12 T.Han #3583対応（S)
		sql.append("  , CASE WHEN  SHIHARAI_SYUBETSU_CD = 'MRS' THEN 'Y' ELSE 'N' END JIKASYOHI_KB ");
		sql.append("  , CASE WHEN  SHIHARAI_SYUBETSU_CD = 'MRS' THEN SHIHARAI_SYUBETSU_SUB_NA ELSE '"+spaces(LENGTH_SHIHARAI_SYUBETSU_SUB_NA)+"' END JIKASYOHI_RECEIPT_VN_NA ");
		// 2017.01.12 T.Han #3583対応（E)
        // 2017.04.06 M.Son #2463対応（S)
        sql.append("  ,'"+systemDt+"' ");
        sql.append("  ,'"+userLog.getJobId()+"' ");
        sql.append("  ,'"+systemDt+"' ");
        sql.append("  ,'"+userLog.getJobId()+"' ");
        // 2017.04.06 M.Son #2463対応（E)
        sql.append("FROM");
        sql.append("  R_PAYMENT ");
        //2016.10.17 M.Akagi #1748 (S)
        // #3853 MSTB908 2017.02.02 S.Takayama (S)
        //sql.append("  LEFT JOIN ");
		//sql.append("		R_DICTIONARY_CONTROL RDC ");
		//sql.append("	ON ");
		//sql.append("		SHIHARAI_SYUBETSU_GROUP_CD = RDC.DICTIONARY_ID ");
		//sql.append("		AND RDC.PARAMETER_ID = 'SHIHARAI_SYUBETSU_GROUP_CD' ");
        //2016.10.17 M.Akagi #1748 (E)
        // #3853 MSTB908 2017.02.02 S.Takayama (E)
        return sql.toString();
    }

    /**
     * IF_BLK特売種別マスタを登録するSQLを取得
     * 
     * @return
     * @throws SQLException
     */
    private String getIfBlkDiscountInsertSql() throws SQLException{
        // 2017.04.06 M.Son #2463対応（S)
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
        // 2017.04.06 M.Son #2463対応（E)

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT ");
        sql.append("INTO IF_BLK_DISCOUNT( ");
        sql.append("  DATA_MAKE_DT");
        sql.append("  , TOROKU_ID");
        sql.append("  , DISCOUNT_CD");
        sql.append("  , SUB_DISCOUNT_CD");
        sql.append("  , DISCOUNT_NA");
        sql.append("  , SUB_DISCOUNT_NA");
        sql.append("  , RECEIPT_QT");
        sql.append("  , MAX_RECEIPT_QT");
        sql.append("  , NEBIKI_RITU_VL");
        sql.append("  , YUKO_START_DT");
        sql.append("  , YUKO_END_DT");
        sql.append("  , MAX_NEBIKI_GAKU_VL");
// #3233 Add 2016.10.18 Li.Sheng (S)
        sql.append("  , CARD_KB");
// #3233 Add 2016.10.18 Li.Sheng (E)
        // 2017.04.06 M.Son #2463対応（S)
        sql.append("  , INSERT_TS ");
        sql.append("  , INSERT_USER_ID ");
        sql.append("  , UPDATE_TS ");
        sql.append("  , UPDATE_USER_ID ");
        // 2017.04.06 M.Son #2463対応（E)
        // 2017.04.24 S.Nakazato #4824対応 (S)
        sql.append("  , SHIHARAI_JOKEN_CD ");
        // 2017.04.24 S.Nakazato #4824対応 (E)
        sql.append(") ");
// 2017.05.17 M.Son #5044対応 (S)
//        sql.append("SELECT");
//        // #2238 MSTB908090 2016.10.18 S.Takayama (S)
//        //sql.append("  '"+batchDt+"'");
//        sql.append("  '"+yokuBatchDt+"'");
//        // #2238 MSTB908090 2016.10.18 S.Takayama (E)
//        sql.append("  , 'A'");
//        sql.append("  , DISCOUNT_CD");
//        sql.append("  , SUB_DISCOUNT_CD");
//        // 2017.04.12 M.Akagi #4611 (S)
//        // 2016.12.22 T.Han #3212対応（S)
//        //sql.append("  , DISCOUNT_NA");
//        //sql.append("  , SUBSTR(RPAD(NVL(DISCOUNT_NA, ' '), 40), 0, 20) ");
//        sql.append("  , DISCOUNT_NA");
//        //sql.append("  , SUB_DISCOUNT_NA");
//        //sql.append("  , SUBSTR(RPAD(NVL(SUB_DISCOUNT_NA, ' '), 40), 0, 20) ");
//        sql.append("  , SUB_DISCOUNT_NA");
//        // 2016.12.22 T.Han #3212対応（E)
//        // 2017.04.12 M.Akagi #4611 (E)
//        sql.append("  , RECEIPT_QT");
//		// #3382 MSTB908 2016.12.22 S.Takayama (S)
//		//sql.append("  , MAX_RECEIPT_QT");
//		sql.append("  ,'" + MAXIMUM_RECEIPT + "' AS MAX_RECEIPT_QT");
//		// #3382 MSTB908 2016.12.22 S.Takayama (E)
//        sql.append("  , NVL(NEBIKI_RITU_VL, '  ')");
//        sql.append("  , NVL(YUKO_START_DT, '        ')");
//        sql.append("  , NVL(YUKO_END_DT, '        ')");
//        sql.append("  , NVL(MAX_NEBIKI_GAKU_VL, '00000000000000.00') ");
//// #3233 Add 2016.10.18 Li.Sheng (S)
//        sql.append("  , CARD_KB ");
//// #3233 Add 2016.10.18 Li.Sheng (E)  
//        // 2017.04.06 M.Son #2463対応（S)
//        sql.append("  ,'"+systemDt+"' ");
//        sql.append("  ,'"+userLog.getJobId()+"' ");
//        sql.append("  ,'"+systemDt+"' ");
//        sql.append("  ,'"+userLog.getJobId()+"' ");
//        // 2017.04.06 M.Son #2463対応（E)
//        // 2017.04.24 S.Nakazato #4824対応 (S)
//        sql.append("  , SHIHARAI_JOKEN_CD ");
//        // 2017.04.24 S.Nakazato #4824対応 (E)
//        sql.append("FROM");
//        sql.append("  R_DISCOUNT");
        sql.append("SELECT");
        sql.append("  '"+yokuBatchDt+"'");
        sql.append("  , 'A'");
        sql.append("  , RD.DISCOUNT_CD");
        sql.append("  , RD.SUB_DISCOUNT_CD");
        sql.append("  , RD.DISCOUNT_NA");
        sql.append("  , RD.SUB_DISCOUNT_NA");
        sql.append("  , RD.RECEIPT_QT");
        sql.append("  ,'" + MAXIMUM_RECEIPT + "' AS MAX_RECEIPT_QT");
        sql.append("  , NVL(RD.NEBIKI_RITU_VL, '  ')");
        sql.append("  , NVL(RD.YUKO_START_DT, '        ')");
        sql.append("  , NVL(RD.YUKO_END_DT, '        ')");
        sql.append("  , NVL(RD.MAX_NEBIKI_GAKU_VL, '00000000000000.00') ");
        sql.append("  , RD.CARD_KB ");
        sql.append("  ,'"+systemDt+"' ");
        sql.append("  ,'"+userLog.getJobId()+"' ");
        sql.append("  ,'"+systemDt+"' ");
        sql.append("  ,'"+userLog.getJobId()+"' ");
        sql.append("  , RP.SHIHARAI_SYUBETSU_SEQ ");
        sql.append("FROM ");
        sql.append("  R_DISCOUNT RD ");
        sql.append("LEFT JOIN ");
        sql.append("  R_PAYMENT RP ");
        sql.append("ON ");
        sql.append("  RD.SHIHARAI_JOKEN_CD = RP.SHIHARAI_SYUBETSU_SUB_CD ");
// 2017.05.17 M.Son #5044対応 (E)
        return sql.toString();
    }

	// 2017.01.12 T.Han #3583対応（S)
	/**
	 * @param spaces
	 * @return String
	 */
	private String spaces(int spaces){
		return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
	}
	// 2017.01.12 T.Han #3583対応（E)

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

	// #3382 MSTB908 2016.12.22 S.Takayama (S)
	/**
	 * システムコントロール情報取得
	 * 
	 * @param なし
	 * @throws Exception
	 *             例外
	 */
	private void getSystemControl() throws Exception {
		maxImumReceipt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.MAX_RECEIPT_QT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
	}
	// #3382 MSTB908 2016.12.22 S.Takayama (E)

}
