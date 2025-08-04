package mdware.master.batch.process.MSTB991;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.CSVLine;
import jp.co.vinculumjapan.mdware.common.util.DateChanger;
import jp.co.vinculumjapan.mdware.common.util.StringUtility;
import jp.co.vinculumjapan.stc.util.db.DataBase;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst007501_ZeiKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *  <p>タイトル: MSTB991010_IfFastSyohinCsvCreate クラス</p>
 *  <p>説明: 説明: FAST向けIFファイル（アイテム一覧）を作成する。</p>
 *  <p>著作権: Copyright (c) 2020</p>
 *  <p>会社名: VVC</p>
 *  @author VINX
 *  @Version 1.00 (2020.08.26) KHAI.NN MKV対応
 *  @Version 1.01 (2020.10.05) THONG.VQ  #6244 MKV対応
 */
public class MSTB991010_IfFastSyohinCsvCreate {

    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    // システム日時
    private String systemDt = "";
    // システム日時
    private String fileDir = "";


    // ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    // テーブル
    private static final String TABLE_WK = "WK_IF_SYOHIN"; // WK_IF_商品マスタ

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
    public MSTB991010_IfFastSyohinCsvCreate(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB991010_IfFastSyohinCsvCreate() {
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
            String csvFileName = ResorceUtil.getInstance().getPropertie("FAST_SEND_FILE_ITEM_LIST");
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
            csvFileName = csvFileName + "_" + previousDt + ".csv";

            // WK_IF_SYOHINのTRUNCATE
            writeLog(Level.INFO_INT, "WK_IF_SYOHIN削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_WK);
            writeLog(Level.INFO_INT, "WK_IF_SYOHINを削除処理を終了します。");

            // WK_IF_SYOHIN
            writeLog(Level.INFO_INT, "WK_IF_SYOHIN登録処理を開始します。");
            iRec = db.executeUpdate(getItemListInsertSql());
            writeLog(Level.INFO_INT, iRec + "件のWK_IF_SYOHINを登録しました。");
            writeLog(Level.INFO_INT, "WK_IF_SYOHIN登録処理を終了します。");

            db.commit();

            // WK_IF_SYOHINファイル作成
            writeLog(Level.INFO_INT, "WK_IF_SYOHINファイル作成処理を開始します。");
            selectDataToCSV(csvFileName);
            writeLog(Level.INFO_INT, "WK_IF_SYOHINファイル作成処理を終了します。");
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

    public String getItemListSelectSql() {

        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");
        sb.append("    WIS.ZAIKO_SYOHIN_CD ");
        sb.append("    ,WIS.REC_HINMEI_KANJI_NA ");
        sb.append("    ,WIS.HACHU_TANI_NA ");
        sb.append("    ,WIS.BARA_IRISU_QT ");
        sb.append("    ,WIS.SYOHIN_CD ");
        sb.append("    ,WIS.DAIBUNRUI1_CD ");
        sb.append("    ,WIS.DAIBUNRUI2_CD ");
        sb.append("    ,WIS.BUNRUI5_CD ");
        sb.append("    ,WIS.SIIRESAKI_CD ");
        sb.append("    ,WIS.KIKAKU_KANJI_NA ");
        sb.append("    ,WIS.TAX_RT ");
        sb.append("    ,WIS.IMPORT_TAX_RT ");
        sb.append("    ,WIS.DELETE_FG ");
        sb.append(" FROM ");
        sb.append("     WK_IF_SYOHIN  WIS ");
        sb.append(" ORDER BY");
        sb.append("       WIS.ZAIKO_SYOHIN_CD ");
        sb.append("       ,WIS.SYOHIN_CD ");

        return sb.toString();
    }

    public String getItemListInsertSql() {

        StringBuffer sb = new StringBuffer();

        sb.append("INSERT /*+ APPEND */ INTO WK_IF_SYOHIN ");
        sb.append("    ( ");
        sb.append("    ZAIKO_SYOHIN_CD ");
        sb.append("    ,REC_HINMEI_KANJI_NA ");
        sb.append("    ,HACHU_TANI_NA ");
        sb.append("    ,BARA_IRISU_QT ");
        sb.append("    ,SYOHIN_CD ");
        sb.append("    ,DAIBUNRUI1_CD ");
        sb.append("    ,DAIBUNRUI2_CD ");
        sb.append("    ,BUNRUI5_CD ");
        sb.append("    ,SIIRESAKI_CD ");
        sb.append("    ,KIKAKU_KANJI_NA ");
        sb.append("    ,TAX_RT ");
        sb.append("    ,IMPORT_TAX_RT ");
        sb.append("    ,DELETE_FG ");
        sb.append("    ,INSERT_USER_ID ");
        sb.append("    ,INSERT_TS ");
        sb.append("    ,UPDATE_USER_ID ");
        sb.append("    ,UPDATE_TS ");
        sb.append("    ) ");
        sb.append("SELECT ");
        sb.append("    RS.ZAIKO_SYOHIN_CD ");
        sb.append("    ,RS.REC_HINMEI_KANJI_NA ");
        sb.append("    ,RN.KANJI_NA AS HACHU_TANI_NA ");
        sb.append("    ,RS.BARA_IRISU_QT ");
        sb.append("    ,RS.SYOHIN_CD ");
        sb.append("    ,TRIM(RS.DAIBUNRUI1_CD) AS DAIBUNRUI1_CD ");
        sb.append("    ,TRIM(RS.DAIBUNRUI2_CD) AS DAIBUNRUI2_CD ");
        sb.append("    ,RS.BUNRUI5_CD ");
        sb.append("    ,TRIM(RS.SIIRESAKI_CD) AS SIIRESAKI_CD ");
        sb.append("    ,RS.KIKAKU_KANJI_NA ");
        sb.append("    ,CASE ");
        sb.append("        WHEN RS.ZEI_KB = '"+ mst007501_ZeiKbDictionary.HIKAZEI.getCode() +"' ");
        sb.append("        THEN 'KT' ");
        sb.append("        ELSE TRIM(TO_CHAR(RTR.TAX_RT, '00')) ");
        sb.append("    END AS TAX_RT ");
        sb.append("    ,SUBSTR(RS.COMMENT_1_TX, 1, 2) AS IMPORT_TAX_RT ");
        sb.append("    ,RS.DELETE_FG ");
        sb.append("    ,'" + userLog.getJobId() + "' ");
        sb.append("    ,'" + systemDt + "' ");
        sb.append("    ,'" + userLog.getJobId() + "' ");
        sb.append("    ,'" + systemDt + "' ");
        sb.append("FROM ");
        sb.append("    (");
        sb.append("        SELECT ");
        sb.append("            RS1.* ");
        sb.append("            ,RST.DAIBUNRUI1_CD ");
        sb.append("            ,RST.DAIBUNRUI2_CD ");
        sb.append("            ,(  SELECT ");
        sb.append("                    MAX(YUKO_DT)");
        sb.append("                FROM R_TAX_RATE TAX_RATE_NM");
        sb.append("                WHERE ");
        sb.append("                        TAX_RATE_NM.TAX_RATE_KB = RS1.TAX_RATE_KB");
        sb.append("                    AND TAX_RATE_NM.DELETE_FG    = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
        sb.append("                    AND TAX_RATE_NM.YUKO_DT    <= '").append(previousDt).append("' ");
        sb.append("             ) AS TAX_RATE_DT ");
        sb.append("            ,(  SELECT ");
        sb.append("                    MAX(YUKO_DT)");
        sb.append("                FROM R_SYOHIN RS2");
        sb.append("                WHERE ");
        sb.append("                        RS2.YUKO_DT    <= '").append(previousDt).append("' ");
        sb.append("                    AND RS2.SYOHIN_CD    = RS1.SYOHIN_CD");
        // #6620 DEL 2022.07.01 SIEU.D (S)
        // sb.append("                    AND RS2.BUNRUI1_CD    = RS1.BUNRUI1_CD");
        // #6620 DEL 2022.07.01 SIEU.D (E)
        sb.append("             ) AS MAX_YUKO_DT ");
        sb.append("        FROM R_SYOHIN RS1 ");
        sb.append("        INNER JOIN R_SYOHIN_TAIKEI RST ");
        sb.append("        ON  RST.SYSTEM_KB = RS1.SYSTEM_KB ");
        sb.append("        AND RST.BUNRUI5_CD = RS1.BUNRUI5_CD ");
        sb.append("    ) RS");
        sb.append("    INNER JOIN R_TAX_RATE RTR ");
        sb.append("    ON ");
        sb.append("            RTR.TAX_RATE_KB = RS.TAX_RATE_KB ");
        sb.append("        AND RTR.YUKO_DT     = RS.TAX_RATE_DT");
        sb.append("    LEFT JOIN R_NAMECTF RN ");
        sb.append("    ON ");
        sb.append("            RN.CODE_CD       = RS.HACHU_TANI_NA ");
        sb.append("        AND RN.SYUBETU_NO_CD = '3030_vi_VN' ");
        sb.append("        AND RN.DELETE_FG     = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
        sb.append("WHERE ");
        sb.append("    RS.YUKO_DT = RS.MAX_YUKO_DT");
        // #6244 MSTB991010 ADD 2020.10.05 THONG.VQ (S)
        sb.append("    AND RS.DELETE_FG     = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
        // #6244 MSTB991010 ADD 2020.10.05 THONG.VQ (E)
        return sb.toString();
    }

    /**
     * IF支払ワークCSV出力処理
     */
    private void selectDataToCSV(String csvFileName) throws Exception {
        ResultSet rs = null;
        try {
            rs = db.executeQuery(getItemListSelectSql());
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
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("ZAIKO_SYOHIN_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("REC_HINMEI_KANJI_NA"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("HACHU_TANI_NA"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("BARA_IRISU_QT"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("SYOHIN_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("DAIBUNRUI1_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("DAIBUNRUI2_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("BUNRUI5_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("SIIRESAKI_CD"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("KIKAKU_KANJI_NA"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("TAX_RT"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("IMPORT_TAX_RT"))));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("DELETE_FG"))));
                writer.write(dtlLine.getLine() + strCrlf);
                count++;
            }

            if (count > 0) {
                // 処理件数ログ出力
                writeLog(Level.INFO_INT, count + "件の受領データを出力しました。");
            } else {

                // 空行出力
                CSVLine dtlLine = new CSVLine();
                writer.write(dtlLine.getLine());

                // 処理処理対象無しの場合
                writeLog(Level.INFO_INT, "受領データは存在しませんでした。");
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
