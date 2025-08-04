package mdware.master.batch.process.MSTB991;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.mdware.common.util.CSVLine;
import jp.co.vinculumjapan.mdware.common.util.DateChanger;
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
import mdware.master.util.RMSTDATEUtil;

/**
 *  <p>タイトル: MSTB991030_IfFastMerchandiseHierarchyCsvCreate クラス</p>
 *  <p>説明: 説明: FAST向けIFファイル（商品分類）を作成する。</p>
 *  <p>著作権: Copyright (c) 2020</p>
 *  <p>会社名: VVC</p>
 *  @author VINX
 *  @Version 1.00 (2020.08.26) KHAI.NN MKV対応
 *  @Version 1.01 (2020.09.02) KHAI.NN #6242 MKV対応
 */
public class MSTB991030_IfFastMerchandiseHierarchyCsvCreate {
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
    private static final String TABLE_WK = "WK_MERCHANDISE_HIERARCHY"; // WK_商品分類

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

    // バッチ日付
    private String batchDt = null;
    private String previousDt = null;

    /**
     * コンストラクタ
     *
     * @param dataBase
     */
    public MSTB991030_IfFastMerchandiseHierarchyCsvCreate(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB991030_IfFastMerchandiseHierarchyCsvCreate() {
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
            String csvFileName = ResorceUtil.getInstance().getPropertie("FAST_SEND_FILE_MERCHANDISE_HIERARCHY");
            // トランザクションログ有無（AutoCommitモード）
            // （trueを指定すると、トランザクションログ出力をしない分の速度向上
            // 　が見込めますが、コミット・ロールバックが無効となります。）
            db.setDisableTransaction(false); // false : ログ有り true : ログ無し

            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");

            // バッチ日付
            batchDt = RMSTDATEUtil.getBatDateDt();
            previousDt = DateChanger.addDate(batchDt, -1);
            systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);
            fileDir = ResorceUtil.getInstance().getPropertie("FAST_IF_PATH");
            // ファイル名編集
            // #6242 MSTB991 Mod 2020.10.02 KHAI.NN (S)
            //csvFileName = csvFileName + "_ " + previousDt + ".csv";
            csvFileName = csvFileName + "_" + previousDt + ".csv";
            // #6242 MSTB991 Mod 2020.10.02 KHAI.NN (S)

            // WK_MERCHANDISE_HIERARCHYのTRUNCATE
            writeLog(Level.INFO_INT, "WK_MERCHANDISE_HIERARCHY削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_WK);
            writeLog(Level.INFO_INT, "WK_MERCHANDISE_HIERARCHYを削除処理を終了します。");

            // WK_MERCHANDISE_HIERARCHY
            writeLog(Level.INFO_INT, "WK_MERCHANDISE_HIERARCHY登録処理を開始します。");
            iRec = db.executeUpdate(getMerchandiseHierarchyInsertSql());
            writeLog(Level.INFO_INT, iRec + "件のWK_MERCHANDISE_HIERARCHYを登録しました。");
            writeLog(Level.INFO_INT, "WK_MERCHANDISE_HIERARCHY登録処理を終了します。");

            db.commit();

            // IF支払ワーク抽出
            writeLog(Level.INFO_INT, "WK_MERCHANDISE_HIERARCHYファイル作成処理を開始します。");
            selectDataToCSV(csvFileName);
            writeLog(Level.INFO_INT, "WK_MERCHANDISE_HIERARCHYファイル作成処理を終了します。");

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
    public String getMerchandiseHierarchySelectSql() {

        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");
        sb.append("    WMH.DAIBUNRUI1_CD ");
        sb.append("    ,WMH.DAIBUNRUI1_NA ");
        sb.append("    ,WMH.DAIBUNRUI2_CD ");
        sb.append("    ,WMH.DAIBUNRUI2_NA ");
        sb.append("    ,WMH.BUNRUI5_CD ");
        sb.append("    ,WMH.BUNRUI5_NA ");
        sb.append(" FROM ");
        sb.append("     WK_MERCHANDISE_HIERARCHY  WMH ");
        sb.append(" ORDER BY");
        sb.append("       WMH.DAIBUNRUI1_CD, ");
        sb.append("       WMH.DAIBUNRUI2_CD, ");
        sb.append("       WMH.BUNRUI5_CD ");

        return sb.toString();
    }

    /**
     * 	登録用ＳＱＬの生成を行う。
     * @return String 生成されたＳＱＬ
     */
    public String getMerchandiseHierarchyInsertSql() {

        StringBuffer sb = new StringBuffer();

        sb.append("INSERT /*+ APPEND */ INTO WK_MERCHANDISE_HIERARCHY ");
        sb.append("    ( ");
        sb.append("    DAIBUNRUI1_CD ");
        sb.append("    ,DAIBUNRUI1_NA ");
        sb.append("    ,DAIBUNRUI2_CD ");
        sb.append("    ,DAIBUNRUI2_NA ");
        sb.append("    ,BUNRUI5_CD ");
        sb.append("    ,BUNRUI5_NA ");
        sb.append("    ,INSERT_USER_ID ");
        sb.append("    ,INSERT_TS ");
        sb.append("    ,UPDATE_USER_ID ");
        sb.append("    ,UPDATE_TS ");
        sb.append("    ) ");
        sb.append("SELECT ");
        sb.append("     DISTINCT TRIM(ST.DAIBUNRUI1_CD) AS DAIBUNRUI1_CD ");
        sb.append("    ,CTF1.KANJI_NA    AS DAIBUNRUI1_NA ");
        sb.append("    ,TRIM(ST.DAIBUNRUI2_CD) AS DAIBUNRUI2_CD ");
        sb.append("    ,CTF2.KANJI_NA    AS DAIBUNRUI2_NA ");
        sb.append("    ,TRIM(ST.BUNRUI5_CD)    AS BUNRUI5_CD ");
        sb.append("    ,CTF3.KANJI_NA    AS BUNRUI5_NA ");
        sb.append("    ,'" + userLog.getJobId() + "' ");
        sb.append("    ,'" + systemDt + "' ");
        sb.append("    ,'" + userLog.getJobId() + "' ");
        sb.append("    ,'" + systemDt + "' ");
        sb.append("FROM ");
        sb.append("     R_SYOHIN_TAIKEI ST ");
		sb.append("      LEFT OUTER JOIN R_NAMECTF CTF1 ON ");
		sb.append("          (TRIM(ST.DAIBUNRUI1_CD) = CTF1.CODE_CD ");
		sb.append("       AND CTF1.SYUBETU_NO_CD = '0070_vi_VN' ");
		sb.append("       AND CTF1.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "') ");
		sb.append("      LEFT OUTER JOIN R_NAMECTF CTF2 ON " );
		sb.append("          (TRIM(ST.DAIBUNRUI2_CD) = CTF2.CODE_CD " );
		sb.append("       AND CTF2.SYUBETU_NO_CD = '0080_vi_VN' " );
		sb.append("       AND CTF2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "') ");
		sb.append("      LEFT OUTER JOIN R_NAMECTF CTF3 ON " );
		sb.append("          (CONCAT( TRIM(ST.SYSTEM_KB) ,TRIM(ST.BUNRUI5_CD)) = CTF3.CODE_CD " );
		sb.append("       AND CTF3.SYUBETU_NO_CD = '0060_vi_VN' " );
		sb.append("       AND CTF3.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "') ");
		sb.append("       ORDER BY DAIBUNRUI1_CD ");
        return sb.toString();
    }

    /**
     * IF支払ワークCSV出力処理
     */
    private void selectDataToCSV(String csvFileName) throws Exception {
        ResultSet rs = null;
        try {
            rs = db.executeQuery(getMerchandiseHierarchySelectSql());
            // CSV出力
            outputCSV(rs, csvFileName);

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
                    new FileOutputStream(csvFile), "UTF-8"));
            int count = 0;
            writer.write("\ufeff");

            while (rs.next()) {
                // CSV出力処理
                CSVLine dtlLine = new CSVLine();
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("DAIBUNRUI1_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("DAIBUNRUI1_NA"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("DAIBUNRUI2_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("DAIBUNRUI2_NA"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("BUNRUI5_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("BUNRUI5_NA"))));
                writer.write(dtlLine.getLine() + strCrlf);
                count++;
            }

            if (count > 0) {
                // 処理件数ログ出力
                writeLog(Level.INFO_INT, count + "件の商品分類データを作成しました。");
            } else {

                // 空行出力
                CSVLine dtlLine = new CSVLine();
                writer.write(dtlLine.getLine());

                // 処理処理対象無しの場合
                writeLog(Level.INFO_INT, "商品分類データは存在しませんでした。");
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
