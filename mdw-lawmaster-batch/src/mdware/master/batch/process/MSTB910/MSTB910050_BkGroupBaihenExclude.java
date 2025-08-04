package mdware.master.batch.process.MSTB910;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * <p>タイトル: MSTB910040_GroupbaihenExcludeReflect.java クラス</p>
 * <p>説明: グループ売変除外品バックアップ処理処理</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @Version 1.00  (2016.09.05) VINX h.sakamoto #1566対応
 */
public class MSTB910050_BkGroupBaihenExclude {
    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB910050_BkGroupBaihenExclude(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB910050_BkGroupBaihenExclude() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }

    /**
     * 本処理
     * @throws Exception
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

            // BK_グループ売変除外品ASNの登録処理
            writeLog(Level.INFO_INT, "BK_グループ売変除外品ASN登録処理を開始します。");
            iRec = db.executeUpdate(getBkGroupBaihenExcludeASNInsertSql());
            writeLog(Level.INFO_INT, "BK_グループ売変除外品ASNを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "BK_グループ売変除外品ASN登録処理を終了します。");

            db.commit();

            // BK_グループ売変除外品ASNの削除処理
            writeLog(Level.INFO_INT, "BK_グループ売変除外品ASN削除処理を開始します。");
            iRec = db.executeUpdate(getBkGroupBaihenExcludeASNDeleteSql());
            writeLog(Level.INFO_INT, "BK_グループ売変除外品ASNを" + iRec + "件削除しました。");
            writeLog(Level.INFO_INT, "BK_グループ売変除外品ASN削除処理を終了します。");

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
     * BK_グループ売変除外品ASNを作成するSQLを取得する
     *
     * @return WK_GROUPBAIHEN_EXCLUDE_ASN登録SQL
     */
    private String getBkGroupBaihenExcludeASNInsertSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT ");
        sql.append("INTO BK_GROUPBAIHEN_EXCLUDE_ASN( ");
        sql.append("  SYOHIN_CD");
        sql.append("  , TENPO_CD");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS");
        sql.append(") ");
        sql.append("SELECT");
        sql.append("  SYOHIN_CD");
        sql.append("  , TENPO_CD");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS ");
        sql.append("FROM");
        sql.append("  WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
        sql.append("WHERE");
        sql.append("  NOT EXISTS ( ");
        sql.append("    SELECT");
        sql.append("      * ");
        sql.append("    FROM");
        sql.append("      BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
        sql.append("    WHERE");
        sql.append("      WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
        sql.append("      AND WGEA.TENPO_CD = BGEA.TENPO_CD");
        sql.append("  ) ");
        return sql.toString();
    }

    /**
     * BK_グループ売変除外品ASNを削除するSQLを取得する
     *
     * @return WK_GROUPBAIHEN_EXCLUDE_ASN削除SQL
     */
    private String getBkGroupBaihenExcludeASNDeleteSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
        sql.append("WHERE");
        sql.append("  NOT EXISTS ( ");
        sql.append("    SELECT");
        sql.append("      * ");
        sql.append("    FROM");
        sql.append("      WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
        sql.append("    WHERE");
        sql.append("      BGEA.SYOHIN_CD = WGEA.SYOHIN_CD ");
        sql.append("      AND BGEA.TENPO_CD = WGEA.TENPO_CD");
        sql.append("  )");
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
