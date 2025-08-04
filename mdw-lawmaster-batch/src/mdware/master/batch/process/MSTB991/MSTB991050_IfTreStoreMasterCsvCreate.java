package mdware.master.batch.process.MSTB991;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.mdware.common.util.CSVLine;
import jp.co.vinculumjapan.mdware.common.util.StringUtility;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 *  <p>タイトル: MSTB991050_IfTreStoreMasterCsvCreate クラス</p>
 *  <p>説明: TRE向けIFファイル（店舗マスタ）を作成する。</p>
 *  <p>著作権: Copyright (c) 2022</p>
 *  <p>会社名: VVC</p>
 *  @author VINX
 *  @Version 1.00 (2022.01.26) KHAI.NN #6497 MKH対応
 */
public class MSTB991050_IfTreStoreMasterCsvCreate {
	/** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    // システム日時
    private String systemDt = "";
    // 出力データ格納先
    private String fileDir = "";

    // ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    // テーブル
    private static final String TABLE_WK = "WK_IF_TRE_STORE_MASTER"; // WK_店舗マスタ

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
    
    private static final String OUTPUT_CHAR_SET = "UTF-8";

    /**
     * コンストラクタ
     *
     * @param dataBase
     */
    public MSTB991050_IfTreStoreMasterCsvCreate(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB991050_IfTreStoreMasterCsvCreate() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }

    /**
     * データアクセス処理を実行します。
     */
    public void execute() throws Exception {

        try {

            // バッチ処理件数をカウント（ログ出力用）
            int iRec = 0;
            
            // トランザクションログ有無（AutoCommitモード）
            // （trueを指定すると、トランザクションログ出力をしない分の速度向上
            // 　が見込めますが、コミット・ロールバックが無効となります。）
            db.setDisableTransaction(false); // false : ログ有り true : ログ無し

            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");

            // システム日付
            systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);
            fileDir = ResorceUtil.getInstance().getPropertie("IF_TRE_PATH");
            Date systemDate = new SimpleDateFormat("yyyyMMddhhmmss").parse(systemDt);
            String csvFileName = ResorceUtil.getInstance().getPropertie("IF_TRE_STORE_MASTER_FILENAME");
            // ファイル名編集
            csvFileName = csvFileName.replaceAll("yymmddhhmmss", new SimpleDateFormat("yyMMddhhmmss").format(systemDate));

            // WK_店舗マスタのTRUNCATE
            writeLog(Level.INFO_INT, TABLE_WK + "削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_WK);
            writeLog(Level.INFO_INT, TABLE_WK + "を削除処理を終了します。");

            // WK_店舗マスタ
            writeLog(Level.INFO_INT, TABLE_WK + "登録処理を開始します。");
            iRec = db.executeUpdate(getStoreMasterInsertSql());
            writeLog(Level.INFO_INT, iRec + "件の" + TABLE_WK + "を登録しました。");
            writeLog(Level.INFO_INT, TABLE_WK + "登録処理を終了します。");

            db.commit();

            // IF店舗ワーク抽出
            writeLog(Level.INFO_INT, "TRE向けIFファイル（店舗マスタ）作成処理を開始します。");
            selectDataToCSV(csvFileName);
            writeLog(Level.INFO_INT, "TRE向けIFファイル（店舗マスタ）作成処理を終了します。");

            /** バッチ終了ログ出力 */
            writeLog(Level.INFO_INT, "処理を終了します。");

            // SQLエラーが発生した場合の処理
        } catch (SQLException se) {
            db.rollback();
            writeLog(Level.ERROR_INT, "ロールバックしました。");
            this.writeError(se);
            throw se;
            // その他のエラーが発生した場合の処理
        } catch (Exception e) {
            db.rollback();
            writeLog(Level.ERROR_INT, "ロールバックしました。");
            this.writeError(e);
            throw e;
            // SQL終了処理
        } finally {
            if (closeDb || db != null) {
                db.close();
            }
        }
    }

    /**
    *
    * @return sql
    */
    public String getStoreMasterSelectSql() {

        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");
        sb.append("    WITSM.TENPO_CD ");
        sb.append("    ,WITSM.TENPO_NA ");
        sb.append("    ,WITSM.ADDRESS_TX ");
        sb.append("    ,WITSM.TEL_NB ");
        sb.append("    ,WITSM.EMAIL_ADDRESS ");
        sb.append("    ,WITSM.UPDATE_TS ");
        sb.append(" FROM ");
        sb.append(TABLE_WK + " WITSM ");
        sb.append(" ORDER BY");
        sb.append("       WITSM.TENPO_CD ");

        return sb.toString();
    }

    /**
     * 	登録用ＳＱＬの生成を行う。
     * @return String 生成されたＳＱＬ
     */
    public String getStoreMasterInsertSql() {

        StringBuffer sb = new StringBuffer();

        sb.append("INSERT /*+ APPEND */ INTO ");
        sb.append(TABLE_WK);
        sb.append("    ( ");
        sb.append("    TENPO_CD ");
        sb.append("    ,TENPO_NA ");
        sb.append("    ,ADDRESS_TX ");
        sb.append("    ,TEL_NB ");
        sb.append("    ,EMAIL_ADDRESS ");
        sb.append("    ,UPDATE_TS ");
        sb.append("    ) ");
        sb.append("SELECT ");
        sb.append("     DISTINCT RT.TENPO_CD ");
        sb.append("    ,TRIM(SUBSTR(RT.KANJI_NA, 1, 50)) AS TENPO_NA ");
        sb.append("    ,TRIM(SUBSTR(RT.ADDRESS_KANJI_NA || ' ' || RT.ADDRESS_3_NA, 1, 100)) AS ADDRESS_TX ");
        sb.append("    ,TRIM(RT.TEL_CD) AS TEL_NB ");
        sb.append("    ,TRIM(SUBSTR(RT.EMAIL_ADDRESS, 1, 20)) AS EMAIL_ADDRESS ");
        sb.append("    ,TO_CHAR( TO_DATE(RT.UPDATE_TS, 'yyyymmddhh24miss'), 'yyyy/MM/dd hh24:mi') AS UPDATE_TS ");
        sb.append("FROM ");
        sb.append("     R_TENPO RT ");
		sb.append(" WHERE ");
		sb.append("     RT.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("     AND RT.TENPO_KB <> '7' ");
		sb.append(" ORDER BY RT.TENPO_CD ");
        return sb.toString();
    }

    /**
     * IF支払ワークCSV出力処理
     */
    private void selectDataToCSV(String csvFileName) throws Exception {
        ResultSet rs = null;
        try {
            rs = db.executeQuery(getStoreMasterSelectSql());
            // CSV出力
            if (rs.next()) {
                outputCSV(rs, csvFileName);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * CSV出力
     */
    private void outputCSV(ResultSet rs, String csvFileName) throws Exception {
        // 改行
        String strCrlf = "\r\n";
        BufferedWriter writer = null;

        try {
            File csvFile = new File(this.fileDir + "\\" + csvFileName);
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(csvFile), OUTPUT_CHAR_SET));
            int count = 0;
            writer.write("\ufeff");

            do {
                // CSV出力処理
                CSVLine dtlLine = new CSVLine();
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("TENPO_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("TENPO_NA"))));
                dtlLine.addItem(addQuarter("HKD"));
                dtlLine.addItem(addQuarter("COD"));
                dtlLine.addItem(addQuarter("AR"));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("ADDRESS_TX"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("TEL_NB"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(null)));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("EMAIL_ADDRESS"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("UPDATE_TS"))));
                writer.write(dtlLine.getLine() + strCrlf);
                count++;
            } while (rs.next());

            if (count > 0) {
                // 処理件数ログ出力
                writeLog(Level.INFO_INT, count + "件の店舗マスタを作成しました。");
            } else {

                // 空行出力
                CSVLine dtlLine = new CSVLine();
                writer.write(dtlLine.getLine());

                // 処理処理対象無しの場合
                writeLog(Level.INFO_INT, "店舗マスタは存在しませんでした。");
            }

        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    private String addQuarter(String value){
        return '"' + value + '"';
    }

    public Object getOutputObject() {
        return null;
    }

    public void setInputObject(Object obj) {
    }

    /********** 共通処理 **********/

    /**
     * ユーザーログとバッチログにログを出力します。
     *
     * @param level
     *            出力レベル。 Levelクラスの定数を指定。
     * @param message
     *            出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
     */
    private void writeLog(int level, String message) {
        String jobId = userLog.getJobId();

        switch (level) {
        case Level.DEBUG_INT:
            userLog.debug(message);
            batchLog.getLog().debug(jobId,
                    Jobs.getInstance().get(jobId).getJobName(), message);
            break;

        case Level.INFO_INT:
            userLog.info(message);
            batchLog.getLog().info(jobId,
                    Jobs.getInstance().get(jobId).getJobName(), message);
            break;

        case Level.ERROR_INT:
            userLog.error(message);
            batchLog.getLog().error(jobId,
                    Jobs.getInstance().get(jobId).getJobName(), message);
            break;

        case Level.FATAL_INT:
            userLog.fatal(message);
            batchLog.getLog().fatal(jobId,
                    Jobs.getInstance().get(jobId).getJobName(), message);
            break;
        }
    }

    /**
     * エラーをログファイルに出力します。 ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
     *
     * @param e
     *            発生したException
     */
    private void writeError(Exception e) {
        if (e instanceof SQLException) {
            userLog.error("ＳＱＬエラーが発生しました。");
        } else {
            userLog.error("エラーが発生しました。");
        }

        String jobId = userLog.getJobId();
        batchLog.getLog().error(jobId,
                Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
        batchLog.getLog().error(e.toString());

        StackTraceElement[] elements = e.getStackTrace();
        for (int tmp = 0; tmp < elements.length; tmp++) {
            batchLog.getLog().error(
                    elements[tmp].getClassName() + " : line "
                            + elements[tmp].getLineNumber());
        }
    }
}
