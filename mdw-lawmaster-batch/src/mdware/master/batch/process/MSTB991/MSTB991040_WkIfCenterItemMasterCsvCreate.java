package mdware.master.batch.process.MSTB991;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import mdware.common.dictionary.IfCenterSendFgDictionary;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * 
 * <p>タイトル: MSTB991040_WkIfCenterItemMasterCsvCreate クラス</p>
 * <p>説明　: センターシステム向けIFファイル作成（商品情報マスタ）を作成する。</p>
 * <p>著作権: Copyright (c) 2021</p>
 * <p>会社名: VVC</p>
 * @author VINX
 * @version 1.00 (2021.10.05) HOAI.TTT #6361 対応
 * @version 1.01 (2021.11.18) KHAI.NN #6361 MKHK対応
 * @version 1.02 (2021.11.22) KHAI.NN #6361 MKHK対応
 * @version 1.03 (2021.11.25) KHAI.NN #6361 MKHK対応
 * @version 1.04 (2021.12.22) KHOI.ND #6361 MKHK対応
 * @version 1.05 (2021.12.27) KHAI.NN #6361 MKHK対応
 * @version 1.06 (2021.12.29) KHAI.NN #6361 MKHK対応
 * @version 1.07 (2022.01.06) KHAI.NN #6361 MKHK対応
 *
 */

public class MSTB991040_WkIfCenterItemMasterCsvCreate {
	
	/** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    // システム日時
    private String csvFilePath = "";
    private String csvFileName = "";
    
    // バッチ日付
    private String systemDt = "";
    
    // ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();
    // テーブル
    private static final String TABLE_WK = "WK_IF_CENTER_ITEM_MASTER"; // WK_センター商品情報マスタ
    // バッチ日付
    private String batchDt = null;
    // #6361 Add 2021.11.18 KHAI.NN (S)
    private String previousDt = null;
    // #6361 Add 2021.11.18 KHAI.NN (E)
 	
    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
    
    // #6361 Add 2021.12.27 KHAI.NN (S)
    private static final String IF_CENTER_SEND_ALL_FG = ResorceUtil.getInstance().getPropertie("IF_CENTER_SEND_ALL_FG");;
    // #6361 Add 2021.12.27 KHAI.NN (E)
   

    /**
     * コンストラクタ
     *
     * @param dataBase
     */
    public MSTB991040_WkIfCenterItemMasterCsvCreate(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB991040_WkIfCenterItemMasterCsvCreate() {
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
            csvFileName = ResorceUtil.getInstance().getPropertie("IF_CENTER_ITEM_MASTER_FILE_NAME");
            // トランザクションログ有無（AutoCommitモード）
            // （trueを指定すると、トランザクションログ出力をしない分の速度向上
            // 　が見込めますが、コミット・ロールバックが無効となります。）
            db.setDisableTransaction(false); // false : ログ有り true : ログ無し

            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");

            // バッチ日付
            batchDt = RMSTDATEUtil.getBatDateDt();
            // #6361 Add 2021.11.18 KHAI.NN (S)
            previousDt = DateChanger.addDate(batchDt, -1);
            // #6361 Add 2021.11.18 KHAI.NN (E)
            systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);
            
            // WK_センター製品マスター削除処理
            writeLog(Level.INFO_INT, "WK_センター製品マスター削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_WK);
            writeLog(Level.INFO_INT, "WK_センター製品マスターを削除処理を終了します。");

            // WK_センター製品マスター登録処理
            writeLog(Level.INFO_INT, "WK_センター製品マスター登録処理を開始します。");
            iRec = db.executeUpdate(insertWkIfCenterItemMaster());
            writeLog(Level.INFO_INT, iRec + "件のWK_センター製品マスターを登録しました。");
            writeLog(Level.INFO_INT, "WK_センター製品マスター登録処理を終了します。");
            
            db.commit();
            
 			// システムコントロール情報取得
 			this.getSystemControl();

			// CSVファイル削除
			this.writeLog(Level.INFO_INT, "既存ファイルの削除処理を開始します。");
			this.deleteCSVFile();
			this.writeLog(Level.INFO_INT, "既存ファイルの削除処理を終了します。");

			// CSVファイル作成
			this.writeLog(Level.INFO_INT, csvFileName + "の出力処理を開始します。");
			selectDataToCSV(csvFileName);
			this.writeLog(Level.INFO_INT, csvFileName + "の出力処理を終了します。");
			
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
    
    
    public String insertWkIfCenterItemMaster() {

        StringBuffer sb = new StringBuffer();

        sb.append("INSERT /*+ APPEND */ INTO WK_IF_CENTER_ITEM_MASTER ");
        sb.append(" ( ");
        sb.append("     SYOHIN_CD ");
        sb.append("     ,HINMEI_KANJI_NA ");
        sb.append("     ,BARA_IRISU_QT ");
        sb.append("     ,PACK_WEIGHT_QT ");
        sb.append("     ,PACK_SIZE_QT ");
        sb.append("     ,SYOHIN_WEIGHT_QT ");
        sb.append("     ,SYOHIN_SIZE_QT ");
        sb.append("     ,SYOMI_KIGEN_NISU	 ");
        sb.append("     ,SHUKKA_KIGEN_NISU ");
        sb.append("     ,NYUKA_KIGEN_NISU ");
        sb.append("     ,DAIBUNRUI1_CD ");
        sb.append("    ) ");
        // #6361 Add 2021.11.25 KHAI.NN (S)
        sb.append(" SELECT ");
        sb.append("     RS.SYOHIN_CD ");
        sb.append("     ,RS.HINMEI_KANJI_NA ");
        sb.append("     ,RS.BARA_IRISU_QT ");
        sb.append("     ,RS.PACK_WEIGHT_QT ");
        sb.append("     ,CASE ");
        sb.append("         WHEN TO_CHAR(RS.PACK_SIZE_QT,'FM999990D999999') LIKE '#%' THEN ");
        sb.append("             SUBSTR(RS.PACK_SIZE_QT, 1, 6) || '.' || SUBSTR(RS.PACK_SIZE_QT, INSTR(RS.PACK_SIZE_QT, '.') + 1) ");
        sb.append("         ELSE ");
        sb.append("             TO_CHAR(RS.PACK_SIZE_QT) ");
        sb.append("         END PACK_SIZE_QT ");
        sb.append("     ,RS.SYOHIN_WEIGHT_QT ");
        sb.append("     ,CASE ");
        sb.append("         WHEN TO_CHAR(RS.SYOHIN_SIZE_QT,'FM999990D999999') LIKE '#%' THEN ");
        sb.append("             SUBSTR(RS.SYOHIN_SIZE_QT, 1, 6) || '.' || SUBSTR(RS.SYOHIN_SIZE_QT, INSTR(RS.SYOHIN_SIZE_QT, '.') + 1) ");
        sb.append("         ELSE ");
        sb.append("             TO_CHAR(RS.SYOHIN_SIZE_QT) ");
        sb.append("         END SYOHIN_SIZE_QT ");
        sb.append("     ,RS.SYOMI_KIGEN_NISU ");
        sb.append("     ,RS.SHUKKA_KIGEN_NISU ");
        sb.append("     ,RS.NYUKA_KIGEN_NISU ");
        sb.append("     ,RS.DAIBUNRUI1_CD ");
        sb.append(" FROM  ");
        sb.append(" ( ");
        // #6361 Add 2021.11.25 KHAI.NN (E)
        sb.append(" SELECT ");
        sb.append("     RS.SYOHIN_CD AS SYOHIN_CD"); 
        // #6361 Mod 2021.12.21 KHOI.ND (S)
        // sb.append("     ,RS.HINMEI_KANJI_NA AS HINMEI_KANJI_NA");
        sb.append("     ,RS.REC_HINMEI_KANJI_NA AS HINMEI_KANJI_NA");
        // #6361 Mod 2021.12.21 KHOI.ND (E)
        sb.append("     ,RS.BARA_IRISU_QT AS BARA_IRISU_QT");
        // #6361 Mod 2021.11.25 KHAI.NN (S)
        //sb.append("     ,COALESCE((RS.PACK_WEIGHT_QT * RS.BARA_IRISU_QT ),0) AS PACK_WEIGHT_QT ");
        //sb.append("     ,COALESCE(((RS.SYOHIN_WIDTH_QT* RS.SYOHIN_HEIGHT_QT* RS.SYOHIN_DEPTH_QT ) * RS.BARA_IRISU_QT ),0) AS PACK_SIZE_QT ");
        //sb.append("     ,COALESCE(RS.PACK_WEIGHT_QT,0) AS  SYOHIN_WEIGHT_QT");
        //sb.append("     ,COALESCE((RS.SYOHIN_WIDTH_QT* RS.SYOHIN_HEIGHT_QT* RS.SYOHIN_DEPTH_QT ),0) AS SYOHIN_SIZE_QT ");
        sb.append("     ,COALESCE((RS.PACK_WEIGHT_QT * RS.BARA_IRISU_QT ),0) / 1000 AS PACK_WEIGHT_QT "); //MD：g → 日通IF：Kg
        sb.append("     ,ROUNDUP(COALESCE(((RS.SYOHIN_WIDTH_QT* RS.SYOHIN_HEIGHT_QT* RS.SYOHIN_DEPTH_QT ) * RS.BARA_IRISU_QT ),0) / POWER(1000,3), 6) AS PACK_SIZE_QT "); //MD：㎣ → 日通：㎥
        sb.append("     ,COALESCE(RS.PACK_WEIGHT_QT,0) / 1000 AS  SYOHIN_WEIGHT_QT"); //MD：g → 日通IF：Kg
        sb.append("     ,ROUNDUP(COALESCE((RS.SYOHIN_WIDTH_QT* RS.SYOHIN_HEIGHT_QT* RS.SYOHIN_DEPTH_QT ),0) / POWER(1000,3), 6) AS SYOHIN_SIZE_QT "); //MD：㎣ → 日通：㎥
        // #6361 Mod 2021.11.25 KHAI.NN (E)
        sb.append("     ,RS.SYOMI_KIGEN_NISU AS SYOMI_KIGEN_NISU");
        sb.append("     ,RS.SHUKKA_KIGEN_NISU AS SHUKKA_KIGEN_NISU");
        sb.append("     ,RS.NYUKA_KIGEN_NISU AS NYUKA_KIGEN_NISU");
        sb.append("     ,TRIM(RST.DAIBUNRUI1_CD) AS DAIBUNRUI1_CD ");
        sb.append(" FROM  ");
        sb.append("    R_SYOHIN RS ");
        sb.append(" INNER JOIN R_SYOHIN_TAIKEI RST ");
        sb.append("    ON  RS.SYSTEM_KB 			= RST.SYSTEM_KB  ");
        sb.append("          AND RS.BUNRUI5_CD 	= RST.BUNRUI5_CD  ");
        sb.append(" WHERE  ");
        sb.append("   		RS.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("   AND ( ");
		// #6361 MOD 2021.12.21 KHOI.ND (S)
		// sb.append("          RS.YUKO_DT = (SELECT MAX(RS1.YUKO_DT) ");
		// sb.append("                           FROM R_SYOHIN RS1 ");
		// sb.append("                          WHERE RS1.BUNRUI1_CD = RS.BUNRUI1_CD ");
		// sb.append("                            AND RS1.SYOHIN_CD  	= RS.SYOHIN_CD ");
		//#6361 Mod 2021.11.18 KHAI.NN (S)
		//sb.append("	                           AND RS1.YUKO_DT    <= '" + batchDt + "' ");
		//sb.append("	                           AND RS1.YUKO_DT    <= '" + previousDt + "' ");
		//#6361 Mod 2021.11.18 KHAI.NN (E)
		// sb.append("	                       ) ");
		//#6361 Mod 2021.12.27 KHAI.NN (S)
		//sb.append("          RS.YUKO_DT = '" + batchDt + "' ");
		if (IfCenterSendFgDictionary.ALL.getCode().equals(IF_CENTER_SEND_ALL_FG)) {
			sb.append("          RS.YUKO_DT = (SELECT MAX(RS1.YUKO_DT) ");
			sb.append("                           FROM R_SYOHIN RS1 ");
			// #6620 MOD 2022.11.18 VU.TD (S)
//			sb.append("                          WHERE RS1.BUNRUI1_CD = RS.BUNRUI1_CD ");
//			sb.append("                            AND RS1.SYOHIN_CD  	= RS.SYOHIN_CD ");
			sb.append("                          WHERE  ");
			sb.append("                            	 RS1.SYOHIN_CD  	= RS.SYOHIN_CD ");
			// #6620 MOD 2022.11.18 VU.TD (E)
			sb.append("	                           AND RS1.YUKO_DT    <= '" + batchDt + "' ");
			sb.append("	                       ) ");
		} else {
			sb.append("          RS.YUKO_DT = '" + batchDt + "' ");
			//#6361 Mod 2021.12.29 KHAI.NN (S)
			sb.append("       OR ( ");
			sb.append("            RS.YUKO_DT = '"+ previousDt +"' ");
			//#6361 Mod 2022.01.06 KHAI.NN (S)
			//sb.append("            AND SUBSTR(RS.UPDATE_TS, 1, 8) = '"+ previousDt +"' ");
			sb.append("       AND (       SUBSTR(RS.UPDATE_TS, 1, 8) = '"+ previousDt +"' ");
			sb.append("                  OR SUBSTR(RS.UPDATE_TS, 1, 8) = '"+ batchDt +"' ");
			sb.append("                ) ");
			//#6361 Mod 2022.01.06 KHAI.NN (E)
			sb.append("          ) ");
			//#6361 Mod 2021.12.29 KHAI.NN (E)
		}
		//#6361 Mod 2021.12.27 KHAI.NN (E)
        // #6361 Mod 2021.12.21 KHOI.ND (E)
		sb.append("	      ) ");
        // #6361 Add 2021.11.25 KHAI.NN (S)
		sb.append(" ) RS ");
        // #6361 Add 2021.11.25 KHAI.NN (E)
        
        return sb.toString();
    }
    
    /**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {
		
		// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie("IF_CENTER_SEND_PATH");

    	if(csvFilePath == null || csvFilePath.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、情報分析用のCSVファイルパスが取得できませんでした");
    		throw new Exception();
		}
	}

	/**
	 * CSVファイル削除処理
	 * @return int
	 * @throws IOException
	 */
	private void deleteCSVFile() throws IOException, Exception{

		String	fileName	= null;
		File 	fileDir 		= null;

		// 情報分析用CSVファイル格納パス、ファイル名
		fileDir 	 = new File(csvFilePath);
		fileName = ResorceUtil.getInstance().getPropertie("IF_CENTER_ITEM_MASTER_FILE_NAME");
		
		// ファイル名編集
		csvFileName = fileName.replace("YYYYMMDDHHMMSS", systemDt);
		// #6361 Mod 2021.11.22 KHAI.NN (S)
		//fileName = fileDir +""+ csvFileName;
		fileName = csvFilePath +""+ csvFileName;
		// #6361 Mod 2021.11.22 KHAI.NN (E)

		if( fileDir.exists() == false ){
			//　削除ディレクトリが見つからなければ
    		this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
			throw new Exception();
		} else {
			// 削除
			new File( fileName ).delete();
    		this.writeLog(Level.INFO_INT, csvFileName + " の削除処理を実施しました。");
		}
	}
	
    public String selectWkIfCenterItemMaster() {

        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT * ");
        sb.append(" FROM ");
        sb.append("     WK_IF_CENTER_ITEM_MASTER  ");
        sb.append(" ORDER BY");
        sb.append("      SYOHIN_CD ");
        
        return sb.toString();
    }

    /**
     * IF支払ワークCSV出力処理
     */
    private void selectDataToCSV(String csvFileName) throws Exception {
        ResultSet rs = null;
        try {
            rs = db.executeQuery(selectWkIfCenterItemMaster());
            // CSV出力
            // #6363 Mod 2020.12.23 KHOI.ND (S)
            // outputCSV(rs, csvFileName);
            if (rs.next()) {
                outputCSV(rs, csvFileName);
            }
            // #6363 Mod 2020.12.23 KHOI.ND (E)

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
            File csvFile = new File(csvFilePath + "" + csvFileName);
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(csvFile), "UTF-8"));
            int count = 0;
            writer.write("\ufeff");

            // #6361 Mod 2021.12.23 KHOI.ND (S)
            // while (rs.next()) {
            do {
            // #6361 Mod 2021.12.23 KHOI.ND (E)
                // CSV出力処理
                CSVLine dtlLine = new CSVLine();
                dtlLine.addItem(addQuarter("MKHK"));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("SYOHIN_CD")).trim()));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("HINMEI_KANJI_NA")).trim()));
                // #6361 Add 2021.11.25 KHAI.NN (S)
                //dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("BARA_IRISU_QT")).trim()));
                //dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("PACK_WEIGHT_QT")).trim()));
                //dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("PACK_SIZE_QT")).trim()));
                //dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("SYOHIN_WEIGHT_QT")).trim()));
                //dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("SYOHIN_SIZE_QT")).trim()));
                dtlLine.addItem(addQuarter(rs.getBigDecimal("BARA_IRISU_QT").toString()));
                dtlLine.addItem(addQuarter(rs.getBigDecimal("PACK_WEIGHT_QT").toString()));
                dtlLine.addItem(addQuarter(rs.getBigDecimal("PACK_SIZE_QT").toString()));
                dtlLine.addItem(addQuarter(rs.getBigDecimal("SYOHIN_WEIGHT_QT").toString()));
                dtlLine.addItem(addQuarter(rs.getBigDecimal("SYOHIN_SIZE_QT").toString()));
                // #6361 Add 2021.11.25 KHAI.NN (E)
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("SYOMI_KIGEN_NISU")).trim()));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("SHUKKA_KIGEN_NISU")).trim()));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("NYUKA_KIGEN_NISU")).trim()));
                dtlLine.addItem(addQuarter(StringUtility.cnvNullToBlank(rs.getString("DAIBUNRUI1_CD")).trim()));
                writer.write(dtlLine.getLine() + strCrlf);
                count++;
            }
            // #6361 Add 2021.12.23 KHOI.ND (S)
            while(rs.next());
            // #6361 Add 2021.12.23 KHOI.ND (E)

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
    
	/********** 共通処理 **********/

	/**
	 * ユーザーログとバッチログにログを出力します。
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message) {
		String jobId = userLog.getJobId();

		switch (level) {
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
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
		batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}
	
}
