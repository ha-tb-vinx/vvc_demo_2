package mdware.master.batch.process.MSTB998;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: WK_商品スキャンマスタ削除処理</p>
 * <p>説明　　: WK_商品スキャンマスタ削除をします</p>
 * <p>著作権　: Copyright (c) 2014</p>
 * <p>会社名　: VVC</p>
 * @author HUNG.NT
 */
public class MSTB998999_WkSyohinScanDelete {

    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;
    
    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    // テーブル
    private static final String TABLE_WK = "WK_SYOHIN_SCAN"; // WK_IF_計量器マスタ

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

    // バッチ日付
    private String batchDt = null;

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB998999_WkSyohinScanDelete(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB998999_WkSyohinScanDelete() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }

    /**
     * データアクセス処理を実行します。
     */
    public void execute() throws Exception {

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
            
            int day = Integer.parseInt(ResorceUtil.getInstance().getPropertie("WK_SCAN_SYOHIN_DELETE_DAY_LENGTH"));

            // WK_SYOHIN_SCANを削除
            writeLog(Level.INFO_INT, "WK_SYOHIN_SCANを削除処理を開始します。");
            iRec = db.executeUpdate(getWkSyohinScanDeleteSql(day));
            writeLog(Level.INFO_INT, "WK_商品スキャンマスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "WK_SYOHIN_SCANを削除処理を終了します。");

            db.commit();

            /** バッチ終了ログ出力 */
            writeLog(Level.INFO_INT, "処理を終了します。");

        }catch (Exception e) {
            this.writeError(e);
            throw e;
        }

    }

    /**
     * WK_SYOHIN_SCANを削除するSQLを取得する
     * 
     * @return WK_SYOHIN_SCANを削除SQL
     */
    private String getWkSyohinScanDeleteSql(int day) {

        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE " + TABLE_WK + " ");
        sql.append(" WHERE ");
        sql.append("    SUBSTR(INSERT_TS, 1, 8) <=  TO_CHAR(TO_DATE('" + batchDt + "', 'YYYYMMDD') - " + day + ",'YYYYMMDD') ");
    
        return sql.toString();
        
    }

    public Object getOutputObject() {
        return null;
    }

    public void setInputObject(Object obj) {
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
