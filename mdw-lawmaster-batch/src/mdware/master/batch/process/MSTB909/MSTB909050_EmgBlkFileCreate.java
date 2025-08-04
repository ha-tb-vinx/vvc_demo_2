package mdware.master.batch.process.MSTB909;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB909050_EmgBlkFileCreate クラス</p>
 * <p>説明　 : Blynk向け連携ファイルを、IF_緊急BLK支払種別マスタ、IF_緊急BLK特売種別マスタを元に作成する。</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: VINX</p>
 * @Version 1.00  (2017.04.06) T.Han #2463 FIVImart様対応
 * @Version 1.01  (2017.04.12) M.Akagi #4610
 * @version 1.02 (2017.04.25) S.Nakazato #4824 FIVIMART対応
 * @version 1.03 (2017.05.18) M.Son #5044 FIVIMART対応
 * @version 1.04 (2023.12.01) TUNG.LT #20077 MKH対応
 */
public class MSTB909050_EmgBlkFileCreate {

	// 改行文字
	private static final String RETURN_CODE	= System.getProperty("line.separator");

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** 支払種別メンテナンス */
	public String blkPaymentMainte = null;
	/** 特売種別メンテナンス */
	public String blkDiscountMainte = null;

	/** 伝送ヘッダーレコードリスト */
	private List densoRecordList = null;

	/** CSVファイルパス */
	private String csvFilePath = null;

	/** システム時刻 */
	private String timeStamp = "";
	/** 作成日 */
	private String data_make_dt = "";

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB909050_EmgBlkFileCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB909050_EmgBlkFileCreate() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		String jobId = userLog.getJobId();
		try {
			userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;
			ResultSet rsData = null;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();

			// 支払種別マスタデータファイル作成(S)
			String getIfEmgBlkPaymentSakuseiDTSelectSql = getIfEmgBlkPaymentSakuseiDtSelectSql();
			rsData = db.executeQuery(getIfEmgBlkPaymentSakuseiDTSelectSql);
			densoRecordList = new ArrayList();
			while (rsData.next()){
				// 引数情報 作成日取得
				data_make_dt = rsData.getString("DATA_MAKE_DT");
				MSTB909050_BlkFileCreateRow rowData = new MSTB909050_BlkFileCreateRow(data_make_dt);
				densoRecordList.add(rowData);
			}
			int fileNo = 2;
			rsData = db.executeQuery(getFileNoSelectSql());
			try {
				while (rsData.next()){
					fileNo = rsData.getInt("FILENO");
				}
			} catch (Exception e) {
				throw e;
			}
			for (Object densoRecord : densoRecordList) {
				MSTB909050_BlkFileCreateRow rowData = (MSTB909050_BlkFileCreateRow) densoRecord;
				data_make_dt = rowData.getDataMakeDt();
				blkPaymentMainte = "L" + getName(data_make_dt, String.format("%02d", fileNo));
				writeLog(Level.INFO_INT, "支払種別マスタデータファイル（" + blkPaymentMainte + "）作成処理を開始します。");
				iRec = createCSVFile(blkPaymentMainte, getIfEmgBlkPaymentMainteSelectSql(),csvFilePath);
				writeLog(Level.INFO_INT, "支払種別マスタデータファイルを" + iRec + "件作成しました。");
			}
			// 支払種別マスタデータファイル作成(E)

			// 特売種別マスタデータファイル作成(S)
			String getIfEmgBlkDiscountSakuseiDtSelectSql = getIfEmgBlkDiscountSakuseiDtSelectSql();
			rsData = db.executeQuery(getIfEmgBlkDiscountSakuseiDtSelectSql);
			densoRecordList = new ArrayList();
			while (rsData.next()){
				// 引数情報 作成日取得
				data_make_dt = rsData.getString("DATA_MAKE_DT");
				MSTB909050_BlkFileCreateRow rowData = new MSTB909050_BlkFileCreateRow(data_make_dt);
				densoRecordList.add(rowData);
			}
			fileNo = 2;
			rsData = db.executeQuery(getFileNoSelectSql());
			try {
				while (rsData.next()){
					fileNo = rsData.getInt("FILENO");
				}
			} catch (Exception e) {
				throw e;
			}
			for (Object densoRecord : densoRecordList) {
				MSTB909050_BlkFileCreateRow rowData = (MSTB909050_BlkFileCreateRow) densoRecord;
				data_make_dt = rowData.getDataMakeDt();
				blkDiscountMainte = "K" + getName(data_make_dt, String.format("%02d", fileNo));
				writeLog(Level.INFO_INT, "特売種別マスタデータファイル（" + blkDiscountMainte + "）作成処理を開始します。");
				iRec = createCSVFile(blkDiscountMainte, getIfEmgBlkDiscountMainteSelectSql(),csvFilePath);
				writeLog(Level.INFO_INT, "特売種別マスタデータファイルを" + iRec + "件作成しました。");
			}
			// 特売種別マスタデータファイル作成(E)

			db.executeUpdate(getPosFileSeqUpdateSql());
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
	 * CSVNameを作成します。
	 *
	 * @param sakuseiDt 作成日
	 * @param rr シーケンスNo
	 * @return name
	 */
	public String getName(String sakuseiDt, String rr){
		String name = null;
		String M = sakuseiDt.substring(4, 6);
		if("10".equals(M)){
			M = "A";
		}else if("11".equals(M)){
			M = "B";
		}else if("12".equals(M)){
			M = "C";
		}else {
			M = M.substring(1, 2);
		}
        name = sakuseiDt.substring(3, 4) + M + sakuseiDt.substring(6, 8) + rr;
		return name;
	}

	/**
	 * CSVファイルを作成します。
	 *
	 * @param fileNa データファイル名
	 * @param selSql メンテナンスレコード取得SQL
	 * @throws Exception
	 */
	public int createCSVFile(String fileNa, String selSql, String csvFilePath) throws Exception {

		ResultSet rs = null;
		String fileName = null;
		File file = null;
		BufferedWriter bw = null;
		StringBuffer sb = new StringBuffer();
		int iRec = 0;

		try {

			// 情報分析用CSVファイル格納パス、ファイル名
			file = new File(csvFilePath);
			fileName = new File(csvFilePath) + "/" + fileNa;

			if (file.exists() == false) {
				// ディレクトリが見つからなければ
				writeLog(Level.INFO_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			// データ取得
			rs = db.executeQuery(selSql);

			while (rs.next()) {
				if(bw == null){
					// ファイルオープン
					bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
				}

				 if("I".equals(fileNa.substring(0,1))){
					sb.append(this.createCsvRowData(rs, false));
					String oldSyohinCd = rs.getString("OLD_SYOHIN_CD");
					if (!StringUtils.isEmpty(oldSyohinCd)) {
						sb.append(this.createCsvRowData(rs, true));
					}
					bw.write(sb.toString());
				} else {
					// 行データ作成
					sb.append(this.createCsvRowData(rs));
					// 行データ出力
					bw.write(sb.toString());
				}

				sb.setLength(0);
				iRec++;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//リザルトセットクローズ
			if (rs != null) {
				rs.close();
			}
			// ファイルクローズ
			if (bw != null) {
				bw.close();
			}
		}
		return iRec;
	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

        // CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie("FIVI_IFDIR_CRM");
        if(csvFilePath == null || csvFilePath.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}

		timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
	}

	/**
	 * CSVファイルへ出力する明細データを作成する
	 * @param		ResultSet			取得データ
	 * @return		StringBuffer	１行分の文字列
	 * @throws		SQLException
	 * @throws		Exception
	 */
	private StringBuffer createCsvRowData(ResultSet rs) throws SQLException, Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		StringBuffer sb = new StringBuffer();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if (i < rsmd.getColumnCount()) {
				// 最終項目以外はカンマ編集
				sb.append(createCsvString(rs.getString(i)));
			} else {
				// 最終項目は改行編集
				sb.append(createCsvEndString(rs.getString(i)));
			}
		}

		return sb;
	}

	/**
	 * CSVファイルへ出力する明細データを作成する
	 * @param		ResultSet			取得データ
	 * @return		StringBuffer	１行分の文字列
	 * @throws		SQLException
	 * @throws		Exception
	 */
	private StringBuffer createCsvRowData(ResultSet rs, boolean isOld) throws SQLException, Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		StringBuffer sb = new StringBuffer();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if (i < rsmd.getColumnCount()) {
				// 最終項目以外はカンマ編集
				if(isOld){
					if(i != 2){
						sb.append(createCsvString(rs.getString(i)));
					}
				}else if(i != 3){
					sb.append(createCsvString(rs.getString(i)));
				}
			} else {
				// 最終項目は改行編集
				sb.append(createCsvEndString(rs.getString(i)));
			}
		}

		return sb;
	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_EMG_BLK_PAYMENTからDATA_MAKE_DTを取得するSQLを取得する
	 *
	 * @return IF_EMG_BLK_PAYMENTからDATA_MAKE_DTを取得SQL
	 */
	private String getIfEmgBlkPaymentSakuseiDtSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT");
		sql.append("	DATA_MAKE_DT ");
		sql.append("FROM ");
		sql.append("	IF_EMG_BLK_PAYMENT ");
		sql.append("GROUP BY ");
		sql.append("	DATA_MAKE_DT ");
		sql.append("ORDER BY ");
		sql.append("	DATA_MAKE_DT ");

		return sql.toString();
	}

	/**
	 * IF_EMG_BLK_PAYMENTからデータを取得するSQLを取得する
	 *
	 * @return IF_EMG_BLK_PAYMENTからデータを取得SQL
	 */
	private String getIfEmgBlkPaymentMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("	 IEBP.TOROKU_ID ");
		sql.append("	,RPAD(NVL(IEBP.SHIHARAI_SYUBETSU_SEQ, ' '), 4, ' ') ");
        sql.append("	,SUBSTR(RPAD(NVL(IEBP.SHIHARAI_SYUBETSU_EN_NA, ' '), 40), 0, 20) SHIHARAI_SYUBETSU_EN_NA ");
        sql.append("	,SUBSTR(RPAD(NVL(IEBP.SHIHARAI_SYUBETSU_VN_NA, ' '), 40), 0, 20) SHIHARAI_SYUBETSU_VN_NA ");
        sql.append("	,RPAD(NVL(IEBP.POS_SEQ, ' '), 2, ' ') ");
		sql.append("	,NVL(IEBP.OVER_TYPE, ' ') ");
		sql.append("	,NVL(IEBP.NEED_AUTHORITY, ' ') ");
		sql.append("	,NVL(IEBP.NEED_EXPIRY, ' ') ");
		sql.append("	,NVL(IEBP.CARD_NUMBER, ' ') ");
        sql.append("	,SUBSTR(RPAD(NVL(IEBP.PROCESS_TYPE, ' '), 60), 0, 30) PROCESS_TYPE ");
        sql.append("	,RPAD(NVL(IEBP.SHIHARAI_SYUBETSU_GROUP_SEQ, ' '), 4, ' ') ");
        sql.append("	,RPAD(NVL(IEBP.CARD_LENGTH, ' '), 2, ' ') ");
        sql.append("	,RPAD(NVL(IEBP.SHIHARAI_SYUBETSU_SUB_CD, ' '), 7, ' ') ");
		sql.append("	,NVL(IEBP.DISPLAY_FG, ' ') ");
		sql.append("	,NVL(IEBP.VOID_FG, ' ') ");
		sql.append("	,NVL(IEBP.RETURN_FG, ' ') ");
		sql.append("	,NVL(IEBP.OPEN_DRAWER_FG, ' ') ");
		sql.append("	,NVL(IEBP.EXTRA_RECEIPT, ' ') ");
		sql.append("	,NVL(IEBP.MAXIMUM_RECEIPT, ' ') ");
        sql.append("	,RPAD(NVL(IEBP.YUKO_START_DT, ' '), 8, ' ') ");
        sql.append("	,RPAD(NVL(IEBP.YUKO_END_DT, ' '), 8, ' ') ");
		sql.append("	,NVL(IEBP.JIKASYOHI_KB, ' ') ");
        sql.append("	,SUBSTR(RPAD(NVL(IEBP.JIKASYOHI_RECEIPT_VN_NA, ' '), 80), 0, 40) JIKASYOHI_RECEIPT_VN_NA ");
        sql.append(" FROM ");
        sql.append("	IF_EMG_BLK_PAYMENT IEBP ");
        sql.append("WHERE ");
        sql.append("	IEBP.DATA_MAKE_DT = '" + data_make_dt + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	 IEBP.TOROKU_ID ");
        sql.append("	 IEBP.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IEBP.SHIHARAI_SYUBETSU_SEQ ");
		return sql.toString();
	}

	/**
	 * IF_EMG_BLK_DISCOUNTからDATA_MAKE_DTを取得するSQLを取得する
	 *
	 * @return IF_EMG_BLK_DISCOUNTからDATA_MAKE_DTを取得SQL
	 */
	private String getIfEmgBlkDiscountSakuseiDtSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT");
		sql.append("	DATA_MAKE_DT ");
		sql.append("FROM ");
		sql.append("	IF_EMG_BLK_DISCOUNT ");
		sql.append("GROUP BY ");
		sql.append("	DATA_MAKE_DT ");
		sql.append("ORDER BY ");
		sql.append("	DATA_MAKE_DT ");

		return sql.toString();
	}

	/**
	 * IF_EMG_BLK_DISCOUNTからデータを取得するSQLを取得する
	 *
	 * @return IF_EMG_BLK_DISCOUNTからデータを取得SQL
	 */
	private String getIfEmgBlkDiscountMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("	 IEBD.TOROKU_ID ");
        sql.append("	,RPAD(NVL(IEBD.DISCOUNT_CD, ' '), 3, ' ') ");
        sql.append("	,RPAD(NVL(IEBD.SUB_DISCOUNT_CD, ' '), 5, ' ') ");
        sql.append("	,SUBSTR(RPAD(NVL(IEBD.DISCOUNT_NA, ' '), 40), 0, 20) DISCOUNT_NA ");
        sql.append("	,SUBSTR(RPAD(NVL(IEBD.SUB_DISCOUNT_NA, ' '), 40), 0, 20) SUB_DISCOUNT_NA ");
		sql.append("	,NVL(IEBD.RECEIPT_QT, ' ') ");
		sql.append("	,NVL(IEBD.MAX_RECEIPT_QT, ' ') ");
        // 2017.04.12 M.Akagi #4610 (S)
        //sql.append("	,RPAD(NVL(IEBD.NEBIKI_RITU_VL, ' '), 3, ' ') ");
        sql.append("	,LPAD(NVL(TRIM(IEBD.NEBIKI_RITU_VL),'0'), 3, '0') ");
        // 2017.04.12 M.Akagi #4610 (E)
        sql.append("	,RPAD(NVL(IEBD.YUKO_START_DT, ' '), 8, ' ') ");
        sql.append("	,RPAD(NVL(IEBD.YUKO_END_DT, ' '), 8, ' ') ");
        sql.append("	,LPAD(TRIM(TO_CHAR(NVL(TRIM(IEBD.MAX_NEBIKI_GAKU_VL), '0'), '99999999999999.99')), 17, '0') ");
		sql.append("	,NVL(IEBD.CARD_KB, ' ') ");
        // 2017.04.25 S.Nakazato #4824対応（S)
        // #5044 2017.05.18 M.Son (S)
        //sql.append("	,RPAD(NVL(IEBD.SHIHARAI_JOKEN_CD, ' '), 7, ' ') ");
        sql.append("	,RPAD(NVL(IEBD.SHIHARAI_JOKEN_CD, ' '), 4, ' ') ");
        // #5044 2017.05.18 M.Son (E)
        // 2017.04.25 S.Nakazato #4824対応（E)
        sql.append(" FROM ");
        sql.append("	IF_EMG_BLK_DISCOUNT IEBD ");
        sql.append("WHERE ");
        sql.append("	IEBD.DATA_MAKE_DT = '" + data_make_dt + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	 IEBD.TOROKU_ID ");
        sql.append("	 IEBD.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IEBD.DISCOUNT_CD ");
		return sql.toString();
	}

	/**
	 * @return fileNo (シーケンスNo)
	 * @throws Exception
	 */
	private String getFileNoSelectSql() throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT FILENO ");
		sql.append(" FROM BLK_FILE_SEQ ");

		return sql.toString();
	}

	/**
	 * Count up No. File sequence update sql
	 *
	 * @return String sql
	 */
	private String getPosFileSeqUpdateSql(){
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE BLK_FILE_SEQ SET FILENO = FILENO + 1");
		return sql.toString();
	}
/********** 共通処理 **********/

	/**
	 * CSV出力データ編集共通処理
	 * @param str
	 * @return CSV出力データ
	 */
	private String createCsvString(String str) {
		return createCsvStringCommon(str, false);
	}
	private String createCsvEndString(String str) {
		return createCsvStringCommon(str, true);
	}
	/**
	 * CSV出力データ編集共通処理
	 * @param str
	 * @param endFg true:最終項目、false:最終項目以外
	 * @return CSV出力データ
	 */
	private String createCsvStringCommon(String str, boolean endFg) {
		String val = "";
		if( str != null ){
			val = str;
		}

		// セパレータの判定。最終項目の場合は改行する。
		if (endFg) {
			val += RETURN_CODE;
		} else {
		}

		return val;
	}

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

	/**
	 * <p>タイトル: MSTB909050_BlkFileCreateRow クラス</p>
	 * <p>説明　: 伝送ヘッダーデータを保持する</p>
	 *
	 */
	class MSTB909050_BlkFileCreateRow {

		/** 作成日 */
		private String dataMakeDt;

		// デフォルトコンストラクタは使用禁止
		private MSTB909050_BlkFileCreateRow() {
		};

		/**
		 * MSTB909050_BlkFileCreateRow を生成
		 *
		 * @param dataMakeDt 作成日
		 */
		public MSTB909050_BlkFileCreateRow(String dataMakeDt) {
			this.dataMakeDt = dataMakeDt;
		}

		/**
		 * 作成日を取得します。
		 * @return 作成日
		 */
		public String getDataMakeDt() {
			return dataMakeDt;
		}
	}

}
