package mdware.master.batch.process.MSTB908;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;
/**
*
* <p>タイトル: MSTB908040_CreateBlkSyohinMasterRuikeiData.java クラス</p>
* <p>説明　: Blynkに連携した商品マスタの累計データを作成する</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 (2015.08.07) TAM.NM FIVImart様対応
* @Version 1.06 (2016.05.18) to #1325,1328対応
*
*/
public class MSTB908040_CreateBlkSyohinMasterRuikeiData {

    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();
    private static final String SYORI_KBCR = "A";
    private static final String SYORI_KBDEL = "D";

    // バッチ日付
    private String batchDt = null;
    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
    private String jobId =userLog.getJobId();
    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB908040_CreateBlkSyohinMasterRuikeiData(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB908040_CreateBlkSyohinMasterRuikeiData() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }
    public void execute() throws Exception{
        try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
            //バッチ処理件数をカウント（ログ出力用）
            int iRec = 0;
            db.setDisableTransaction(false); // false : ログ有り  true : ログ無し
            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");
            // バッチ日付
            batchDt = RMSTDATEUtil.getBatDateDt();
            writeLog(Level.INFO_INT, "バッチ日付： " + batchDt);

            // DT_BLK商品マスタ削除
            writeLog(Level.INFO_INT, "DT_BLK商品マスタ削除処理を開始します。");
            iRec = db.executeUpdate(getDtBlkSyohinDeleteSql());
            writeLog(Level.INFO_INT, "DT_BLK商品マスタを" + iRec + "件作成しました。");
            db.commit();
            writeLog(Level.INFO_INT, "DT_BLK商品マスタ削除処理を終了します。");

            // DT_BLK商品マスタ更新
            writeLog(Level.INFO_INT, "DT_BLK商品マスタ更新処理を開始します。");
            iRec = db.executeUpdate(getDtBlkSyohinUpdateSql());
            writeLog(Level.INFO_INT, "DT_BLK商品マスタを" + iRec + "件作成しました。");
            db.commit();
            writeLog(Level.INFO_INT, "DT_BLK商品マスタ更新処理を終了します。");

            // DT_BLK商品マスタ登録
            writeLog(Level.INFO_INT, "DT_BLK商品マスタ登録処理を開始します。");
            iRec = db.executeUpdate(getDtBlkSyohinInsertSql());
            writeLog(Level.INFO_INT, "DT_BLK商品マスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "DT_BLK商品マスタ登録処理を終了します。");
            db.commit();
            writeLog(Level.INFO_INT, "処理を終了します。");

        //SQLエラーが発生した場合の処理
        }catch (SQLException se) {
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
    /********** ＳＱＬ生成処理 **********/

    /**
     * DT_BLK_SYOHINを登録するSQLを取得する
     *
     * @return DT_BLK_SYOHIN登録SQL
     */
    private String getDtBlkSyohinDeleteSql() throws SQLException{

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE  ");
        sql.append(" FROM ");
        sql.append("	DT_BLK_SYOHIN DBS");
        sql.append(" WHERE EXISTS ");
        sql.append(" ( ");
        sql.append("  SELECT * ");
        sql.append("  FROM ");
        sql.append(" 	IF_BLK_SYOHIN IBS");
        sql.append("  WHERE ");
        sql.append(" 	IBS.SYORI_KB = '" + SYORI_KBDEL + "' AND ");
        sql.append(" 	IBS.SYOHIN_CD = DBS.SYOHIN_CD");
        sql.append(" )	 ");

        return sql.toString();
    }
    /**
     * DT_BLK_SYOHINを登録するSQLを取得する
     *
     * @return DT_BLK_SYOHIN登録SQL
     */
    private String getDtBlkSyohinUpdateSql() throws SQLException{

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE DT_BLK_SYOHIN DBS");
        sql.append("	SET ");
        sql.append("	( ");
        sql.append("	 SYOHIN_CD");
        sql.append("	 ,SYOHIN_NA");
        sql.append("	 ,DPT_CD");
        sql.append("	 ,LINE_CD");
        sql.append("	 ,CLASS_CD");
        sql.append("	 ,SUPPLIER_CD");
        sql.append("	 ,SUPPLIER_NA");
        sql.append("	 ,INSERT_TS");
        sql.append("	 ,INSERT_USER_ID");
        sql.append("	 ,UPDATE_TS");
        sql.append("	 ,UPDATE_USER_ID");
     // #1325,1328対応 2016.05.19 to Mod (S)
        sql.append("	 ,TEIKAN_FG");
     // #1325,1328対応 2016.05.19 to Mod (E)
        sql.append("	) ");
        sql.append("	= ");
        sql.append("	( ");
        sql.append(" SELECT ");
        sql.append("	 IBS.SYOHIN_CD");
        sql.append("	 ,IBS.SYOHIN_NA");
        sql.append("	 ,IBS.DPT_CD");
        sql.append("	 ,IBS.LINE_CD");
        sql.append("	 ,IBS.CLASS_CD");
        sql.append("	 ,IBS.SUPPLIER_CD");
        sql.append("	 ,IBS.SUPPLIER_NA");
        sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
        sql.append("	 ,'" + jobId + "' ");
        sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
        sql.append("	 ,'" + jobId + "' ");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	 ,IBS.TEIKAN_FG ");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append(" FROM ");
        sql.append("	IF_BLK_SYOHIN IBS");
        sql.append(" WHERE ");
        sql.append("	IBS.SYOHIN_CD = DBS.SYOHIN_CD ");
        sql.append("	) ");
        sql.append("	WHERE EXISTS  ");
        sql.append("	( ");
        sql.append("	SELECT * FROM");
        sql.append("		IF_BLK_SYOHIN IBS");
        sql.append("	WHERE ");
        sql.append("		IBS.SYORI_KB = '" + SYORI_KBCR + "' AND ");
        sql.append("		IBS.SYOHIN_CD= DBS.SYOHIN_CD ");
        sql.append("	) ");

        return sql.toString();
    }
    /**
     * DT_BLK_SYOHINを登録するSQLを取得する
     *
     * @return DT_BLK_SYOHIN登録SQL
     */
    private String getDtBlkSyohinInsertSql() throws SQLException{

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO DT_BLK_SYOHIN");
        sql.append("	( ");
        sql.append("	 SYOHIN_CD");
        sql.append("	 ,SYOHIN_NA");
        sql.append("	 ,DPT_CD");
        sql.append("	 ,LINE_CD");
        sql.append("	 ,CLASS_CD");
        sql.append("	 ,SUPPLIER_CD");
        sql.append("	 ,SUPPLIER_NA");
        sql.append("	 ,INSERT_TS");
        sql.append("	 ,INSERT_USER_ID");
        sql.append("	 ,UPDATE_TS");
        sql.append("	 ,UPDATE_USER_ID");
     // #1325,1328対応 2016.05.19 to Mod (S)
        sql.append("	 ,TEIKAN_FG");
     // #1325,1328対応 2016.05.19 to Mod (E)
        sql.append("	 ) ");
        sql.append(" SELECT ");
        sql.append("	 IBS.SYOHIN_CD");
        sql.append("	 ,IBS.SYOHIN_NA");
        sql.append("	 ,IBS.DPT_CD");
        sql.append("	 ,IBS.LINE_CD");
        sql.append("	 ,IBS.CLASS_CD");
        sql.append("	 ,IBS.SUPPLIER_CD");
        sql.append("	 ,IBS.SUPPLIER_NA");
        sql.append("	 ,IBS.INSERT_TS");
        sql.append("	 ,IBS.INSERT_USER_ID");
        sql.append("	 ,IBS.UPDATE_TS");
        sql.append("	 ,IBS.UPDATE_USER_ID");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	 ,IBS.TEIKAN_FG ");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append(" FROM ");
        sql.append("	IF_BLK_SYOHIN IBS ");
        sql.append(" LEFT JOIN ");
        sql.append("	DT_BLK_SYOHIN DBS ");
        sql.append(" ON ");
        sql.append(" IBS.SYOHIN_CD = DBS.SYOHIN_CD ");
        sql.append("			WHERE ");
        sql.append("	IBS.SYORI_KB = '" + SYORI_KBCR + "' AND DBS.SYOHIN_CD is NULL ");

        return sql.toString();
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
