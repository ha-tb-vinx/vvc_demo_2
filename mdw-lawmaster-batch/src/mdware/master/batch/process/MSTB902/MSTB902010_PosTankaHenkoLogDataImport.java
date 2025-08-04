package mdware.master.batch.process.MSTB902;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * <p>タイトル:WK_POS単価変更ログ取込</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author o.uemura
 * @version 3.00 (2013/11/05) O.Uemura [CUS00050] ランドローム様対応 POS売価不一致勧告対応
 * @version 3.01 (2014/03/20) K.TO [シス0138] POS売価チェック全体仕様見直し
 */
public class MSTB902010_PosTankaHenkoLogDataImport {

	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	// ヘッダ行、明細行のカラム数を宣言する
	private static final int HEADER_ROW_NUMBER = 10;
	private static final int MEISAI_ROW_NUMBER = 21;

	private MasterDataBase db = null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = null;
	// 有効日
	private String yukoDate = null;
	// 取込対象ファイルパス
	private String csvHolder = null;
	private String csvName = null;

	/*
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB902010_PosTankaHenkoLogDataImport(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB902010_PosTankaHenkoLogDataImport() {
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

			// 取込先テーブル(WK_UNIT_PRICE_CHANGE_LOG)のTRUNCATE
			writeLog(Level.INFO_INT, "WK_POS単価変更ログ(WK_POS_TANKA_HENKO_LOG)のデータクリア処理を開始します。");
			DBUtil.truncateTable(db, "WK_POS_TANKA_HENKO_LOG");
			writeLog(Level.INFO_INT, "WK_POS単価変更ログ(WK_POS_TANKA_HENKO_LOG)のデータをクリアしました。");


			BufferedReader reader = null;
	        String csvFileRec;
	        String[]  csvFileRecSplit = new String[25];
            //ファイルの取り込み
            File path = new File(csvHolder + "\\" + csvName);
            String csvFilePath = path.getCanonicalPath();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), "UTF-8"));

            PreparedStatement pstmt = db.getPrepareStatement(getInsWkPosTankaHenkoLogSQL());
            // INSERT処理可否判定
            Boolean instFg = true;

            // 行数カウント用
            int countRecord = 0;
            int countHeader = 0;
            int countDetails = 0;
            int countExecute = 0;

            while ((csvFileRec = reader.readLine()) != null ) {
                countRecord++;
                String[] st = csvFileRec.split(",");

                //カラム数でヘッダ行・明細行を判別する
                if (st.length == HEADER_ROW_NUMBER && st[1].trim().equals(csvName)) { // ヘッダ行の2列目に表記されるファイル名称が対象IFファイル名称と一致することを確認する
        			// ヘッダ行に対する処理
        			csvFileRecSplit[1] = st[5]; //送信日付
        			csvFileRecSplit[2] = st[6]; //送信時刻
        			csvFileRecSplit[3] = st[4]; //店舗コード
        			countHeader++;
                } else if (st.length == MEISAI_ROW_NUMBER){
        			// 明細行に対する処理
    				csvFileRecSplit[0] = Integer.toString(countDetails+1); // SEQ
        			for(int i=0;i<st.length;i++){
        				csvFileRecSplit[i+4] = st[i]; //明細行の各列の値を挿入
        			}

        			// 取込先テーブル(WK_UNIT_PRICE_CHANGE_LOG)への取込準備
        			pstmt.setLong(1, Long.parseLong(csvFileRecSplit[0]) );
        			pstmt.setString(2, csvFileRecSplit[1] );
        			pstmt.setString(3, csvFileRecSplit[2] );
        			pstmt.setString(4, csvFileRecSplit[3] );
        			pstmt.setString(5, csvFileRecSplit[4] );
        			pstmt.setString(6, csvFileRecSplit[5] );
        			pstmt.setString(7, csvFileRecSplit[6] );
        			pstmt.setString(8, csvFileRecSplit[7] );
        			pstmt.setString(9, csvFileRecSplit[8] );
        			pstmt.setString(10, csvFileRecSplit[9] );
        			pstmt.setString(11, csvFileRecSplit[10] );
        			pstmt.setString(12, csvFileRecSplit[11] );
        			//通貨対応 start
//        			pstmt.setLong(13, Long.parseLong(csvFileRecSplit[12]) );
//        			pstmt.setLong(14, Long.parseLong(csvFileRecSplit[13]) );
//        			pstmt.setLong(15, Long.parseLong(csvFileRecSplit[14]) );
        			pstmt.setDouble(13, Double.parseDouble(csvFileRecSplit[12]) );
        			pstmt.setDouble(14, Double.parseDouble(csvFileRecSplit[13]) );
        			pstmt.setDouble(15, Double.parseDouble(csvFileRecSplit[14]) );
        			pstmt.setString(16, csvFileRecSplit[15] );
        			pstmt.setString(17, csvFileRecSplit[16] );
        			pstmt.setString(18, csvFileRecSplit[17] );
        			pstmt.setLong(19, Long.parseLong(csvFileRecSplit[18]) );
//        			pstmt.setLong(20, Long.parseLong(csvFileRecSplit[19]) );
//        			pstmt.setLong(21, Long.parseLong(csvFileRecSplit[20]) );
        			pstmt.setDouble(20, Double.parseDouble(csvFileRecSplit[19]) );
        			pstmt.setDouble(21, Double.parseDouble(csvFileRecSplit[20]) );
        			//通貨対応 end
        			pstmt.setLong(22, Long.parseLong(csvFileRecSplit[21]) );
        			pstmt.setString(23, csvFileRecSplit[22] );
        			pstmt.setString(24, csvFileRecSplit[23] );
        			pstmt.setString(25, csvFileRecSplit[24] );
        			pstmt.addBatch();
        			countDetails++;
        			countExecute++;

        			if (countExecute == 100000){
        	            // 10万件毎にINSERT処理を実行する
        	            pstmt.executeBatch();
        				countExecute = 0;
        			}

                } else {
        			writeLog(Level.INFO_INT, countRecord + ":行目: データが不正です。");
        			instFg = false;
                	break;
                }
            }

            if (instFg){ // 全件正常である場合に限り、INSERT処理およびコミットを行う。
	            // INSERT処理の実行
	            pstmt.executeBatch();
				db.commit();
				writeLog(Level.INFO_INT, countRecord+"件中、ヘッダ行"+countHeader+"件、明細行"+countDetails+"件を処理しました。");
            }

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

	private String getInsWkPosTankaHenkoLogSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ");
		sql.append("    WK_POS_TANKA_HENKO_LOG ");
		sql.append("( ");
		sql.append("     SEQ ");
		sql.append("    ,HEADER_HENKO_DT ");
		sql.append("    ,HEADER_HENKO_TM ");
		sql.append("    ,TENPO_CD ");
		sql.append("    ,HENKO_DT ");
		sql.append("    ,HENKO_TM ");
		sql.append("    ,TANPIN_CD_ID ");
		sql.append("    ,TANPIN_CD ");
		sql.append("    ,JISYA_CD ");
		sql.append("    ,TANPIN_NA ");
		sql.append("    ,KIKAKU_NA ");
		sql.append("    ,MAKER_CD ");
		sql.append("    ,MAKER_VL ");
		sql.append("    ,TEIBAN_VL ");
		sql.append("    ,JITSUBAI_VL ");
		sql.append("    ,JOTAI_FG ");
		sql.append("    ,TOKUBAI_KIKAKU_NB ");
		sql.append("    ,MM_NB ");
		sql.append("    ,MM_QT ");
		sql.append("    ,MM_VL ");
		sql.append("    ,SHIN_TANKA_VL ");
		sql.append("    ,SET_HANBAI_QT ");
		sql.append("    ,HENKO_FG ");
		sql.append("    ,YOYAKU_FG ");
		sql.append("    ,JIKO_FG ");
		sql.append("    ,INSERT_TS ");
		sql.append("    ,INSERT_USER_ID ");
		sql.append("    ,UPDATE_TS ");
		sql.append("    ,UPDATE_USER_ID ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("( ");
		sql.append("     SUBSTRB(?,0,10) ");
		sql.append("    ,SUBSTRB(?,0,10) ");
		sql.append("    ,SUBSTRB(?,0,5)  ");
		sql.append("    ,SUBSTRB(?,0,5)  ");
		sql.append("    ,SUBSTRB(?,0,10) ");
		sql.append("    ,SUBSTRB(?,0,5)  ");
		sql.append("    ,SUBSTRB(?,0,1)  ");
		sql.append("    ,SUBSTRB(?,0,13) ");
		sql.append("    ,SUBSTRB(?,0,13) ");
		sql.append("    ,SUBSTRB(?,0,28) ");
		sql.append("    ,SUBSTRB(?,0,16) ");
		sql.append("    ,SUBSTRB(?,0,7)  ");
		sql.append("    ,SUBSTRB(?,0,7)  ");
		sql.append("    ,SUBSTRB(?,0,6)  ");
		sql.append("    ,SUBSTRB(?,0,6)  ");
		sql.append("    ,SUBSTRB(?,0,2)  ");
		sql.append("    ,SUBSTRB(?,0,9)  ");
		sql.append("    ,SUBSTRB(?,0,4)  ");
		sql.append("    ,SUBSTRB(?,0,4)  ");
		sql.append("    ,SUBSTRB(?,0,6)  ");
		sql.append("    ,SUBSTRB(?,0,6)  ");
		sql.append("    ,SUBSTRB(?,0,3)  ");
		sql.append("    ,SUBSTRB(?,0,2)  ");
		sql.append("    ,SUBSTRB(?,0,2)  ");
		sql.append("    ,SUBSTRB(?,0,4)  ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append(") ");

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

		// 取込対象ファイルパス
		csvHolder = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_TANKA_HENKO_LOG_SOURCE_DIR_NAME);
		if(csvHolder == null || csvHolder.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、取込対象ファイルパスが取得できませんでした");
			throw new Exception();
		}
		csvName = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_TANKA_HENKO_LOG_SOURCE_FILE_NAME);
		if(csvName == null || csvName.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、取込対象ファイル名が取得できませんでした");
			throw new Exception();
		}

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
