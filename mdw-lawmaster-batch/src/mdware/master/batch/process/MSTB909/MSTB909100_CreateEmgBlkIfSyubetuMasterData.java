package mdware.master.batch.process.MSTB909;

import java.sql.SQLException;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;
/**
*
* <p>タイトル: MSTB909100_CreateEmgBlkIfSyubetuMasterData.java クラス</p>
* <p>説明　: IF_緊急BLｋ支払種別マスタ、IF_緊急BLK特売種別マスタを作成する。</p>
* <p>著作権: Copyright (c) 2017</p>
* <p>会社名: VINX</p>
* @version 1.00 (2017.04.07) M.Son #2463 FIVIMART対応
* @version 1.01 (2017.04.25) S.Nakazato #4824 FIVIMART対応
*/
public class MSTB909100_CreateEmgBlkIfSyubetuMasterData {

    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    // テーブル
    private static final String TABLE_IF_EMG_BLK_PAYMENT = "IF_EMG_BLK_PAYMENT"; // IF_緊急BLK支払種別マスタ
    private static final String TABLE_IF_EMG_BLK_DISCOUNT = "IF_EMG_BLK_DISCOUNT"; // IF_緊急BLK特売種別マスタ
    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
    private String jobId =userLog.getJobId();

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB909100_CreateEmgBlkIfSyubetuMasterData(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB909100_CreateEmgBlkIfSyubetuMasterData() {
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

            // IF_緊急BLK支払種別マスタTRUNCATE
            writeLog(Level.INFO_INT, "IF_緊急BLK支払種別マスタ削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_IF_EMG_BLK_PAYMENT);
            writeLog(Level.INFO_INT, "IF_緊急BLK支払種別マスタ削除処理を終了します。");

            // IF_緊急BLK特売種別マスタTRUNCATE
            writeLog(Level.INFO_INT, "IF_緊急BLK特売種別マスタ削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_IF_EMG_BLK_DISCOUNT);
            writeLog(Level.INFO_INT, "IF_緊急BLK特売種別マスタ削除処理を終了します。");

            // IF_緊急BLK支払種別マスタの登録
            writeLog(Level.INFO_INT, "IF_緊急BLK支払種別マスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkPaymentInsertSql());
            writeLog(Level.INFO_INT, "IF_緊急BLK支払種別マスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_緊急BLK支払種別マスタ登録処理を終了します。");

            db.commit();

            // IF_緊急BLK支払種別マスタの登録
            writeLog(Level.INFO_INT, "IF_緊急BLK支払種別マスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkDiscountInsertSql());
            writeLog(Level.INFO_INT, "IF_緊急BLK支払種別マスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_緊急BLK支払種別マスタ登録処理を終了します。");

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
     * IF_緊急BLK支払種別マスタを登録するSQLを取得
     * 
     * @return
     * @throws SQLException
     */
    private String getIfBlkPaymentInsertSql() throws SQLException{

    	String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO IF_EMG_BLK_PAYMENT ");
        sql.append("    SELECT  ");
        sql.append("        WEBP.DATA_MAKE_DT ");
        sql.append("        ,WEBP.TOROKU_ID ");
        sql.append("        ,WEBP.SHIHARAI_SYUBETSU_SEQ ");
        sql.append("        ,WEBP.SHIHARAI_SYUBETSU_EN_NA ");
        sql.append("        ,WEBP.SHIHARAI_SYUBETSU_VN_NA ");
        sql.append("        ,WEBP.POS_SEQ ");
        sql.append("        ,WEBP.OVER_TYPE ");
        sql.append("        ,WEBP.NEED_AUTHORITY ");
        sql.append("        ,WEBP.NEED_EXPIRY ");
        sql.append("        ,WEBP.CARD_NUMBER ");
        sql.append("        ,WEBP.PROCESS_TYPE ");
        sql.append("        ,WEBP.SHIHARAI_SYUBETSU_GROUP_SEQ ");
        sql.append("        ,WEBP.CARD_LENGTH ");
        sql.append("        ,WEBP.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("        ,WEBP.DISPLAY_FG ");
        sql.append("        ,WEBP.VOID_FG ");
        sql.append("        ,WEBP.RETURN_FG ");
        sql.append("        ,WEBP.OPEN_DRAWER_FG ");
        sql.append("        ,WEBP.EXTRA_RECEIPT ");
        sql.append("        ,WEBP.MAXIMUM_RECEIPT ");
        sql.append("        ,NVL(WEBP.YUKO_START_DT, '        ') ");
        sql.append("        ,NVL(WEBP.YUKO_END_DT, '        ') ");
        sql.append("        ,WEBP.JIKASYOHI_KB ");
        sql.append("        ,SUBSTR(RPAD(NVL(WEBP.JIKASYOHI_RECEIPT_VN_NA, ' '), 40), 0, 40) ");
        sql.append("        ,'"+systemDt+"' ");
        sql.append("        ,'"+userLog.getJobId()+"' ");
        sql.append("        ,'"+systemDt+"' ");
        sql.append("        ,'"+userLog.getJobId()+"' ");
        sql.append("    FROM  ");
        sql.append("        WK_EMG_BLK_PAYMENT WEBP ");
        sql.append("    LEFT OUTER JOIN  ");
        sql.append("        ZEN_BLK_PAYMENT ZBP ");
        sql.append("    ON  ");
        sql.append("        WEBP.SHIHARAI_SYUBETSU_SEQ = ZBP.SHIHARAI_SYUBETSU_SEQ ");
        sql.append("    WHERE  ");
        sql.append("        ZBP.SHIHARAI_SYUBETSU_SEQ IS NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.SHIHARAI_SYUBETSU_EN_NA,ZBP.SHIHARAI_SYUBETSU_EN_NA),NULLIF(ZBP.SHIHARAI_SYUBETSU_EN_NA,WEBP.SHIHARAI_SYUBETSU_EN_NA)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.SHIHARAI_SYUBETSU_VN_NA,ZBP.SHIHARAI_SYUBETSU_VN_NA),NULLIF(ZBP.SHIHARAI_SYUBETSU_VN_NA,WEBP.SHIHARAI_SYUBETSU_VN_NA)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.POS_SEQ,ZBP.POS_SEQ),NULLIF(ZBP.POS_SEQ,WEBP.POS_SEQ)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.OVER_TYPE,ZBP.OVER_TYPE),NULLIF(ZBP.OVER_TYPE,WEBP.OVER_TYPE)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.NEED_AUTHORITY,ZBP.NEED_AUTHORITY),NULLIF(ZBP.NEED_AUTHORITY,WEBP.NEED_AUTHORITY)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.NEED_EXPIRY,ZBP.NEED_EXPIRY),NULLIF(ZBP.NEED_EXPIRY,WEBP.NEED_EXPIRY)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.CARD_NUMBER,ZBP.CARD_NUMBER),NULLIF(ZBP.CARD_NUMBER,WEBP.CARD_NUMBER)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.PROCESS_TYPE,ZBP.PROCESS_TYPE),NULLIF(ZBP.PROCESS_TYPE,WEBP.PROCESS_TYPE)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.SHIHARAI_SYUBETSU_GROUP_SEQ,ZBP.SHIHARAI_SYUBETSU_GROUP_SEQ),NULLIF(ZBP.SHIHARAI_SYUBETSU_GROUP_SEQ,WEBP.SHIHARAI_SYUBETSU_GROUP_SEQ)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.CARD_LENGTH,ZBP.CARD_LENGTH),NULLIF(ZBP.CARD_LENGTH,WEBP.CARD_LENGTH)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.SHIHARAI_SYUBETSU_SUB_CD,ZBP.SHIHARAI_SYUBETSU_SUB_CD),NULLIF(ZBP.SHIHARAI_SYUBETSU_SUB_CD,WEBP.SHIHARAI_SYUBETSU_SUB_CD)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.DISPLAY_FG,ZBP.DISPLAY_FG),NULLIF(ZBP.DISPLAY_FG,WEBP.DISPLAY_FG)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.VOID_FG,ZBP.VOID_FG),NULLIF(ZBP.VOID_FG,WEBP.VOID_FG)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.RETURN_FG,ZBP.RETURN_FG),NULLIF(ZBP.RETURN_FG,WEBP.RETURN_FG)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.OPEN_DRAWER_FG,ZBP.OPEN_DRAWER_FG),NULLIF(ZBP.OPEN_DRAWER_FG,WEBP.OPEN_DRAWER_FG)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.EXTRA_RECEIPT,ZBP.EXTRA_RECEIPT),NULLIF(ZBP.EXTRA_RECEIPT,WEBP.EXTRA_RECEIPT)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.MAXIMUM_RECEIPT,ZBP.MAXIMUM_RECEIPT),NULLIF(ZBP.MAXIMUM_RECEIPT,WEBP.MAXIMUM_RECEIPT)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.YUKO_START_DT,ZBP.YUKO_START_DT),NULLIF(ZBP.YUKO_START_DT,WEBP.YUKO_START_DT)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.YUKO_END_DT,ZBP.YUKO_END_DT),NULLIF(ZBP.YUKO_END_DT,WEBP.YUKO_END_DT)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.JIKASYOHI_KB,ZBP.JIKASYOHI_KB),NULLIF(ZBP.JIKASYOHI_KB,WEBP.JIKASYOHI_KB)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBP.JIKASYOHI_RECEIPT_VN_NA,ZBP.JIKASYOHI_RECEIPT_VN_NA),NULLIF(ZBP.JIKASYOHI_RECEIPT_VN_NA,WEBP.JIKASYOHI_RECEIPT_VN_NA)) IS NOT NULL ");

        return sql.toString();
    }

    /**
     * IF_緊急BLK特売種別マスタを登録するSQLを取得
     * 
     * @return
     * @throws SQLException
     */
    private String getIfBlkDiscountInsertSql() throws SQLException{

        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO IF_EMG_BLK_DISCOUNT ");
        sql.append("    SELECT ");
        sql.append("        WEBD.DATA_MAKE_DT ");
        sql.append("        ,WEBD.TOROKU_ID ");
        sql.append("        ,WEBD.DISCOUNT_CD ");
        sql.append("        ,WEBD.SUB_DISCOUNT_CD ");
        sql.append("        ,WEBD.DISCOUNT_NA ");
        sql.append("        ,WEBD.SUB_DISCOUNT_NA ");
        sql.append("        ,WEBD.RECEIPT_QT ");
        sql.append("        ,WEBD.MAX_RECEIPT_QT ");
        sql.append("        ,NVL(WEBD.NEBIKI_RITU_VL,'  ') ");
        sql.append("        ,NVL(WEBD.YUKO_START_DT,'        ') ");
        sql.append("        ,NVL(WEBD.YUKO_END_DT,'        ') ");
        sql.append("        ,NVL(WEBD.MAX_NEBIKI_GAKU_VL,'00000000000000.00') ");
        sql.append("        ,WEBD.CARD_KB ");
        sql.append("        ,'"+systemDt+"' ");
        sql.append("        ,'"+userLog.getJobId()+"' ");
        sql.append("        ,'"+systemDt+"' ");
        sql.append("        ,'"+userLog.getJobId()+"' ");
        // 2017.04.25 S.Nakazato #4824対応（S)
        sql.append("        ,WEBD.SHIHARAI_JOKEN_CD ");
        // 2017.04.25 S.Nakazato #4824対応（E)
        sql.append("    FROM ");
        sql.append("        WK_EMG_BLK_DISCOUNT WEBD ");
        sql.append("    LEFT OUTER JOIN ");
        sql.append("        ZEN_BLK_DISCOUNT ZBD ");
        sql.append("    ON  ");
        sql.append("        WEBD.SUB_DISCOUNT_CD = ZBD.SUB_DISCOUNT_CD ");
        sql.append("    WHERE ");
        // 2017.04.25 S.Nakazato #4824対応（S)
        //sql.append("        WEBD.SUB_DISCOUNT_CD IS NULL OR ");
        sql.append("        ZBD.SUB_DISCOUNT_CD IS NULL OR ");
        // 2017.04.25 S.Nakazato #4824対応（E)
        sql.append("        NVL(NULLIF(WEBD.DISCOUNT_CD,ZBD.DISCOUNT_CD),NULLIF(ZBD.DISCOUNT_CD,WEBD.DISCOUNT_CD)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.DISCOUNT_NA,ZBD.DISCOUNT_NA),NULLIF(ZBD.DISCOUNT_NA,WEBD.DISCOUNT_NA)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.SUB_DISCOUNT_NA,ZBD.SUB_DISCOUNT_NA),NULLIF(ZBD.SUB_DISCOUNT_NA,WEBD.SUB_DISCOUNT_NA)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.RECEIPT_QT,ZBD.RECEIPT_QT),NULLIF(ZBD.RECEIPT_QT,WEBD.RECEIPT_QT)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.MAX_RECEIPT_QT,ZBD.MAX_RECEIPT_QT),NULLIF(ZBD.MAX_RECEIPT_QT,WEBD.MAX_RECEIPT_QT)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.NEBIKI_RITU_VL,ZBD.NEBIKI_RITU_VL),NULLIF(ZBD.NEBIKI_RITU_VL,WEBD.NEBIKI_RITU_VL)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.YUKO_START_DT,ZBD.YUKO_START_DT),NULLIF(ZBD.YUKO_START_DT,WEBD.YUKO_START_DT)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.YUKO_END_DT,ZBD.YUKO_END_DT),NULLIF(ZBD.YUKO_END_DT,WEBD.YUKO_END_DT)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.MAX_NEBIKI_GAKU_VL,ZBD.MAX_NEBIKI_GAKU_VL),NULLIF(ZBD.MAX_NEBIKI_GAKU_VL,WEBD.MAX_NEBIKI_GAKU_VL)) IS NOT NULL OR ");
        // 2017.04.25 S.Nakazato #4824対応（S)
        //sql.append("        NVL(NULLIF(WEBD.CARD_KB,ZBD.CARD_KB),NULLIF(ZBD.CARD_KB,WEBD.CARD_KB)) IS NOT NULL ");
        sql.append("        NVL(NULLIF(WEBD.CARD_KB,ZBD.CARD_KB),NULLIF(ZBD.CARD_KB,WEBD.CARD_KB)) IS NOT NULL OR ");
        sql.append("        NVL(NULLIF(WEBD.SHIHARAI_JOKEN_CD,ZBD.SHIHARAI_JOKEN_CD),NULLIF(ZBD.SHIHARAI_JOKEN_CD,WEBD.SHIHARAI_JOKEN_CD)) IS NOT NULL ");
        // 2017.04.25 S.Nakazato #4824対応（E )

        return sql.toString();
    }

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
