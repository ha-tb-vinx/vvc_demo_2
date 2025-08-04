package mdware.master.batch.process.MSTB992;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import mdware.common.util.DateChanger;
import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB992070_PosFileCreate.java クラス</p>
 * <p>説明　: 単品メンテナンス、単品メンテナンス(ヘッダーファイル)、<br>
 *            クラスメンテナンス、クラスメンテナンス(ヘッダーファイル)、<br>
 *            ラインメンテナンス、ラインメンテナンス(ヘッダーファイル)を作成する。</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00  (2014.08.30) M.Ayukawa 海外LAWSON様対応 POS連携対応
 *
 */
public class MSTB992070_PosFileCreate {

	// 区切り文字（カンマ区切り）
	private static final String SPLIT_CODE	= ",";
	// 改行文字
	private static final String RETURN_CODE	= System.getProperty("line.separator");

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** CSVファイルパス */
	private String csvFilePath = null;
	/** 単品メンテナンス */
	public String posFtpTanpinMainte = null;
	/** 単品メンテナンス(ヘッダーファイル) */
	public String posFtpTanpinMainteHds = null;
	/** クラスメンテナンス */
	public String posFtpClassMainte = null;
	/** クラスメンテナンス(ヘッダーファイル) */
	public String posFtpClassMainteHds = null;
	/** ラインメンテナンス */
	public String posFtpLineMainte = null;
	/** ラインメンテナンス(ヘッダーファイル) */
	public String posFtpLineMainteHds = null;

	/** システム時刻 */
	private String timeStamp = "";
	/** 作成日 */
	private String sakuseiDt = "";
	/** 作成時刻 */
	private String sakuseiTs = "";
	/** 伝送ヘッダーレコードリスト */
	private List densoRecordList = null;

	/** ファイルサイズ桁数(固定値) */
	private static final String FILE_SIZE_LENGTH = "12";
	/** 伝送ヘッダーレコード桁数 */
	private static final String DENSO_HEADER_RECORD_LENGTH = "128";
	/** 単品メンテナンス レコード桁数 */
	private static final String TANPIN_RECORD_LENGTH = "298";
	/** クラスメンテナンス レコード桁数 */
	private static final String CLASS_RECORD_LENGTH = "115";
	/** ラインメンテナンス レコード桁数 */
	private static final String LINE_RECORD_LENGTH = "91";
	/** ファイル名桁数 */
	private static final String FILE_NA_LENGTH = "32";
	/** ファイル名桁数(ヘッダーファイル 明細用) */
	private static final int HEADER_DETAIL_FILE_NA_LENGTH = 16;
	/** 店舗コード桁数 */
	private static final String TENPO_CD_LENGTH = "6"; // 2014.05.30 M.Ayukawa 海外LAWSON様対応 POS連携対応
	/** レコード数 桁数 */
	private static final String RECORD_CNT_QT_LENGTH = "7";
	/** 予備桁数 */
	private static final String YOBI_LENGTH = "34";
	/** 単品名称（漢字） 桁数 */
	private static final String HINMEI_KANJI_NA_LENGTH = "28";
	/** 単品名称（カナ） 桁数 */
	private static final String HINMEI_KANA_NA_LENGTH = "14";
	/** 規格名称（漢字） 桁数 */
	private static final String KIKAKU_KANJI_NA_LENGTH = "16";
	/** クラス名称（漢字） 桁数 */
	private static final String CLASS_KANJI_NA_LENGTH = "32";
	/** クラス名称（ANK） 桁数 */
	private static final String CLASS_ANK_NA_LENGTH = "14";
	/** ライン名称（漢字） 桁数 */
	private static final String LINE_KANJI_NA_LENGTH = "32";
	/** ライン名称（ANK） 桁数 */
	private static final String LINE_ANK_NA_LENGTH = "14";

	/** ヘッダー桁数 */
	private static final int HEADER_HEADER_LENGTH = 72;
	/** ヘッダー明細レコード桁数 */
	private static final int HEADER_RECORD_LENGTH = 72;
	/** オンライン区分 (定数) */
	private static final String ONLINE_KB = "1";
	/** HOST/店舗/ﾍﾞﾝﾀﾞ数 (定数) */
	private static final String HOST_TENPO_BENDER_QT = "001";
	/** 配信パターン (定数) */
	private static final String HAISIN_PATTERN = "0";

	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** パディング文字(空白) */
	private static final String PADDING_STR_BLANK = " ";

	/** ファイル名識別(固定値) */
	private static final String FILE_NA_SIKIBETSU_CONST = "0";
	/** 緊急通知フラグ(固定値) */
	private static final String KINKYU_TUCHI_FG_CONST = "0";

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992070_PosFileCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992070_PosFileCreate() {
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

			// システムコントロール情報取得
			this.getSystemControl();

			sakuseiDt = timeStamp.substring(0, 4) + "/" + timeStamp.substring(4, 6) + "/" + timeStamp.substring(6, 8);
			sakuseiTs = timeStamp.substring(8, 10) + ":" + timeStamp.substring(10, 12);

			// 単品メンテナンス ファイル作成(S)
			writeLog(Level.INFO_INT, "単品メンテナンス データファイル（" + posFtpTanpinMainte + "）作成処理を開始します。");
			iRec = createCSVFile(posFtpTanpinMainte, getIfTecPluDensoHeaderSelectSql(), getIfTecPluSelectSql());
			writeLog(Level.INFO_INT, "単品メンテナンス データファイルを" + iRec + "件作成しました。");
			// 単品メンテナンス ファイル作成(E)
			if (iRec > 0) {
				// 単品メンテナンス(ヘッダーファイル) ファイル作成(S)
				writeLog(Level.INFO_INT, "単品メンテナンス (ヘッダーファイル)ファイル（" + posFtpTanpinMainteHds + "）作成処理を開始します。");
				iRec = createHdsCSVFile(posFtpTanpinMainteHds, posFtpTanpinMainte, densoRecordList);
				writeLog(Level.INFO_INT, "単品メンテナンス (ヘッダーファイル)ファイルを" + iRec + "件作成しました。");
				// 単品メンテナンス(ヘッダーファイル) ファイル作成(E)
			}

			//バッチ日付取得
			String batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			
			//商品分類体系作成日取得
			String createDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_SAKUSEI_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);

			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
			//稼働日判定処理
			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
				// 処理を終了する
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。(バッチ処理日≠商品分類体系作成日)");
				writeLog(Level.INFO_INT, "処理を終了します。");

			} else {
				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
				
				// クラスメンテナンス ファイル作成(S)
				writeLog(Level.INFO_INT, "クラスメンテナンス データファイル（" + posFtpClassMainte + "）作成処理を開始します。");
				iRec = createCSVFile(posFtpClassMainte, getIfPosClassDensoHeaderSelectSql(), getIfPosClassSelectSql());
				writeLog(Level.INFO_INT, "クラスメンテナンス データファイルを" + iRec + "件作成しました。");
				// クラスメンテナンス ファイル作成(E)

				if (iRec > 0) {
					// クラスメンテナンス(ヘッダーファイル) ファイル作成(S)
					writeLog(Level.INFO_INT, "クラスメンテナンス (ヘッダーファイル)ファイル（" + posFtpClassMainteHds + "）作成処理を開始します。");
					iRec = createHdsCSVFile(posFtpClassMainteHds, posFtpClassMainte, densoRecordList);
					writeLog(Level.INFO_INT, "クラスメンテナンス (ヘッダーファイル)ファイルを" + iRec + "件作成しました。");
					// クラスメンテナンス(ヘッダーファイル) ファイル作成(E)
					
				}

				// ラインメンテナンス ファイル作成(S)
				writeLog(Level.INFO_INT, "ラインメンテナンス データファイル（" + posFtpLineMainte + "）作成処理を開始します。");
				iRec = createCSVFile(posFtpLineMainte, getIfPosLineDensoHeaderSelectSql(), getIfPosLineSelectSql());
				writeLog(Level.INFO_INT, "ラインメンテナンス データファイルを" + iRec + "件作成しました。");
				// ラインメンテナンス ファイル作成(E)
				if (iRec > 0) {
					// ラインメンテナンス(ヘッダーファイル) ファイル作成(S)
					writeLog(Level.INFO_INT, "ラインメンテナンス (ヘッダーファイル)ファイル（" + posFtpLineMainteHds + "）作成処理を開始します。");
					iRec = createHdsCSVFile(posFtpLineMainteHds, posFtpLineMainte, densoRecordList);
					writeLog(Level.INFO_INT, "ラインメンテナンス (ヘッダーファイル)ファイルを" + iRec + "件作成しました。");
					// ラインメンテナンス(ヘッダーファイル) ファイル作成(E)
				}
			}
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
	 * CSVファイルを作成します。
	 * 
	 * @param fileNa データファイル名
	 * @param selHeaderSql 伝送ヘッダー取得SQL
	 * @param selSql メンテナンスレコード取得SQL
	 * @throws Exception
	 */
	public int createCSVFile(String fileNa, String selHeaderSql, String selSql) throws Exception {

		ResultSet rs = null;
		String fileName = null;
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
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
			rs = db.executeQuery(selHeaderSql);

			densoRecordList = new ArrayList();

			// 伝送ヘッダーデータ取得
			while (rs.next()) {
				// 引数情報 営業日・店舗コード取得
				String eigyoDt = rs.getString("EIGYO_DT");
				String tenpoCd = rs.getString("TENPO_CD");
				String fileSize = rs.getString("FILE_SIZE");
				String densoRowData = createCsvRowData(rs).toString();
				MSTB992070_PosFileCreateRow rowData = new MSTB992070_PosFileCreateRow(eigyoDt, tenpoCd, fileSize, densoRowData);
				densoRecordList.add(rowData);
			}
			// ファイルオープン
			if (densoRecordList.size() > 0) {
				// ファイルオープン
				fw = new FileWriter(fileName, false);
				bw = new BufferedWriter(fw);
			}

			for (Object densoRecord : densoRecordList) {
				MSTB992070_PosFileCreateRow rowData = (MSTB992070_PosFileCreateRow) densoRecord;
				// 伝送ヘッダー出力
				bw.write(rowData.getDensoRowData());

				iRec++;
				// レコード作成
				// 引数情報 営業日・店舗コード取得
				String eigyoDt = rowData.getEigyoDt();
				String tenpoCd = rowData.getTenpoCd();

				// メンテナンスデータ取得
				rs = db.executeQuery(String.format(selSql, eigyoDt, tenpoCd));
				while (rs.next()) {
					// メンテナンス レコード出力
					bw.write(createCsvRowData(rs).toString());
					iRec++;
				}

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
			if (fw != null) {
				fw.close();
			}
		}
		return iRec;
	}

	/**
	 * CSVヘッダーファイルを作成します。
	 * 
	 * @param fileNa ヘッダーファイル名
	 * @param txtFileNa データファイル名
	 * @param densoRecordList 伝送ヘッダー情報格納リスト
	 * @return ヘッダーファイル出力
	 * @throws Exception 
	 */
	public int createHdsCSVFile(String fileNa, String txtFileNa, List densoRecordList) throws Exception {

		String fileName = null;
		File file = null;
		FileWriter fw = null;
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

			// ファイルオープン
			fw = new FileWriter(fileName, false);
			bw = new BufferedWriter(fw);

			// ヘッダーレコード出力
			// ファイルサイズ取得
			int fileSize = 0;
			for (Object densoData : densoRecordList) {
				MSTB992070_PosFileCreateRow rowData = (MSTB992070_PosFileCreateRow) densoData;
				fileSize = fileSize + Integer.parseInt(rowData.getFileSize());
			}
			sb.append(createCsvString(String.format("%05d", HEADER_HEADER_LENGTH + HEADER_RECORD_LENGTH * densoRecordList.size())));
			sb.append(createCsvString(ONLINE_KB));
			sb.append(createCsvString(StringUtility.spaceFormat(txtFileNa, Integer.parseInt(FILE_NA_LENGTH))));
			sb.append(createCsvString(String.format("%012d", fileSize)));
			sb.append(createCsvString(sakuseiDt));
			sb.append(createCsvEndString(sakuseiTs));
			bw.write(sb.toString());
			sb.setLength(0);
			iRec++;

			// ヘッダーファイルレコード出力
			for (Object densoData : densoRecordList) {
				MSTB992070_PosFileCreateRow rowData = (MSTB992070_PosFileCreateRow) densoData;

				sb.append(createCsvString(StringUtility.spaceFormat(txtFileNa, HEADER_DETAIL_FILE_NA_LENGTH)));
				sb.append(createCsvString(HOST_TENPO_BENDER_QT));
				sb.append(createCsvString(PADDING_STR + rowData.getTenpoCd()));
				sb.append(createCsvString(rowData.getEigyoDt()));
				sb.append(createCsvString(sakuseiDt));
				sb.append(createCsvString(sakuseiTs));
				sb.append(createCsvString(rowData.getFileSize()));
				sb.append(createCsvEndString(HAISIN_PATTERN));
				bw.write(sb.toString());
				sb.setLength(0);
				iRec++;

			}

		} catch (Exception e) {
			throw e;
		} finally {
			// ファイルクローズ
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
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
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_PLU);
		if(csvFilePath == null || csvFilePath.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}

		// 単品メンテナンス データファイル名取得
		posFtpTanpinMainte = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_FTP_TANPIN_MAINTE);
		if(posFtpTanpinMainte == null || posFtpTanpinMainte.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、単品メンテナンス データファイル名が取得できませんでした");
			throw new Exception();
		}

		// 単品メンテナンス(ヘッダーファイル) データファイル名取得
		posFtpTanpinMainteHds = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_FTP_TANPIN_MAINTE_HDS);
		if(posFtpTanpinMainteHds == null || posFtpTanpinMainteHds.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、単品メンテナンス(ヘッダーファイル)ファイル名が取得できませんでした");
			throw new Exception();
		}

		// クラスメンテナンス データファイル名取得
		posFtpClassMainte = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_FTP_CLASS_MAINTE);
		if(posFtpClassMainte == null || posFtpClassMainte.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、クラスメンテナンス データファイル名が取得できませんでした");
			throw new Exception();
		}

		// クラスメンテナンス(ヘッダーファイル) データファイル名取得
		posFtpClassMainteHds = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_FTP_CLASS_MAINTE_HDS);
		if(posFtpClassMainteHds == null || posFtpClassMainteHds.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、クラスメンテナンス(ヘッダーファイル)ファイル名が取得できませんでした");
			throw new Exception();
		}

		// ラインメンテナンス データファイル名取得
		posFtpLineMainte = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_FTP_LINE_MAINTE);
		if(posFtpLineMainte == null || posFtpLineMainte.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ラインメンテナンス データファイル名が取得できませんでした");
			throw new Exception();
		}

		// ラインメンテナンス(ヘッダーファイル) データファイル名取得
		posFtpLineMainteHds = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POS_FTP_LINE_MAINTE_HDS);
		if(posFtpLineMainteHds == null || posFtpLineMainteHds.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ラインメンテナンス(ヘッダーファイル)ファイル名が取得できませんでした");
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


/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_TEC_PLU伝送ヘッダーを取得するSQLを取得する
	 * 
	 * @return IF_TEC_PLU伝送ヘッダー取得SQL
	 */
	private String getIfTecPluDensoHeaderSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 '" + FILE_NA_SIKIBETSU_CONST + "'                                            AS FILE_NA_SIKIBETSU ");
		sql.append("	,RPAD('" + posFtpTanpinMainte + "', " + FILE_NA_LENGTH + ", ' ')              AS FILE_NA ");
		sql.append("	,LPAD(" + DENSO_HEADER_RECORD_LENGTH + " + " + TANPIN_RECORD_LENGTH + " * RECORD_CNT_QT , " + FILE_SIZE_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                              AS FILE_SIZE ");
		sql.append("	,ITP.EIGYO_DT_IF                                                              AS EIGYO_DT ");
		sql.append("	,LPAD(TRIM(ITP.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')           AS TENPO_CD ");
		sql.append("	,'" + sakuseiDt + "'                                                          AS FILE_SAKUSEI_DT ");
		sql.append("	,'" + sakuseiTs + "'                                                          AS FILE_SAKUSEI_TS ");
		sql.append("	,LPAD(ITP.RECORD_CNT_QT, " + RECORD_CNT_QT_LENGTH + ", '" + PADDING_STR + "') AS RECORD_CNT_QT ");
		sql.append("	,'" + KINKYU_TUCHI_FG_CONST + "'                                              AS KINKYU_TUCHI_FG ");
		sql.append("	,LPAD(' ', " + YOBI_LENGTH + ", '" + PADDING_STR_BLANK + "')                  AS YOBI ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("	SELECT  ");
		sql.append("		 ITP.EIGYO_DT_IF ");
		sql.append("		,ITP.TENPO_CD ");
		sql.append("		,COUNT(ITP.TANPIN_CD) AS RECORD_CNT_QT ");
		sql.append("	FROM ");
		sql.append("		IF_TEC_PLU ITP ");
		sql.append("	GROUP BY ");
		sql.append("		 ITP.EIGYO_DT_IF ");
		sql.append("		,ITP.TENPO_CD ");
		sql.append("	) ITP ");
		sql.append("ORDER BY ");
		sql.append("	 ITP.TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_TEC_PLUを取得するSQLを取得する
	 * 
	 * @return IF_TEC_PLU取得SQL
	 */
	private String getIfTecPluSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,JIKKO_DT ");
		sql.append("	,TANPIN_CD_SHIKIBETSU ");
		sql.append("	,TANPIN_CD ");
		sql.append("	,JISYA_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,CATEGORY_1_CD ");
		sql.append("	,CATEGORY_2_CD ");
		sql.append("	,CATEGORY_3_CD ");
		sql.append("	,RPAD(NVL(TRIM(HINMEI_KANJI_NA), '" + PADDING_STR_BLANK + "'), " + HINMEI_KANJI_NA_LENGTH + ", '" + PADDING_STR_BLANK + "' ) ");
		sql.append("	,RPAD(NVL(TRIM(HINMEI_KANA_NA) , '" + PADDING_STR_BLANK + "'), " + HINMEI_KANA_NA_LENGTH  + ", '" + PADDING_STR_BLANK + "' ) ");
		sql.append("	,RPAD(NVL(TRIM(KIKAKU_KANJI_NA), '" + PADDING_STR_BLANK + "'), " + KIKAKU_KANJI_NA_LENGTH + ", '" + PADDING_STR_BLANK + "' ) ");
		sql.append("	,TANPIN_KB ");
		sql.append("	,JAN_LABEL_CD ");
		sql.append("	,JAN_LABEL_CD_SU ");
		sql.append("	,MAKER_CD ");
		sql.append("	,MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,TEIBAN_TANKA ");
		sql.append("	,JITSUBAI_TANKA ");
		sql.append("	,GENKA_KB ");
		sql.append("	,GENKA_VL ");
		sql.append("	,NEIRER ");
		sql.append("	,TOKUTEI_TANPIN_FG ");
		sql.append("	,JIDO_WARI_KINSHI_FG ");
		sql.append("	,JIDO_SAKU_KINSHI_FG ");
		sql.append("	,KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,MIN_BAIKA_CHK_FG ");
		sql.append("	,MIN_BAIKA_VL ");
		sql.append("	,STATUS_1_CD ");
		sql.append("	,STATUS_2_CD ");
		sql.append("	,STATUS_3_CD ");
		sql.append("	,STATUS_4_CD ");
		sql.append("	,STATUS_5_CD ");
		sql.append("	,JIDO_NEBIKI_NO ");
		sql.append("	,SENTAKU_SEISAN_NO ");
		sql.append("	,START_DT ");
		sql.append("	,DATA_SYUKEI_PTN ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,IRYO_FG ");
		sql.append("	,STOCK_QT ");
		sql.append("FROM ");
		sql.append("	IF_TEC_PLU ITP ");
		sql.append("WHERE ");
		sql.append("	ITP.EIGYO_DT_IF	= '%s'	AND ");
		sql.append("	ITP.TENPO_CD	= '%s' ");
		sql.append("ORDER BY ");
		sql.append("	 DATA_KB ");
		sql.append("	,CLASS_CD ");
		sql.append("	,TANPIN_CD ");

		return sql.toString();
	}

	/**
	 * IF_POS_CLASS伝送ヘッダーを取得するSQLを取得する
	 * 
	 * @return IF_POS_CLASS伝送ヘッダー取得SQL
	 */
	private String getIfPosClassDensoHeaderSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 '" + FILE_NA_SIKIBETSU_CONST + "'                                            AS FILE_NA_SIKIBETSU ");
		sql.append("	,RPAD('" + posFtpClassMainte + "', " + FILE_NA_LENGTH + ", ' ')               AS FILE_NA ");
		sql.append("	,LPAD(" + DENSO_HEADER_RECORD_LENGTH + " + " + CLASS_RECORD_LENGTH + " * RECORD_CNT_QT , " + FILE_SIZE_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                              AS FILE_SIZE ");
		sql.append("	,IPC.EIGYO_DT_IF                                                              AS EIGYO_DT ");
		sql.append("	,LPAD(IPC.TENPO_CD, " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')           AS TENPO_CD ");
		sql.append("	,'" + sakuseiDt + "'                                                          AS FILE_SAKUSEI_DT ");
		sql.append("	,'" + sakuseiTs + "'                                                          AS FILE_SAKUSEI_TS ");
		sql.append("	,LPAD(IPC.RECORD_CNT_QT, " + RECORD_CNT_QT_LENGTH + ", '" + PADDING_STR + "') AS RECORD_CNT_QT ");
		sql.append("	,'" + KINKYU_TUCHI_FG_CONST + "'                                              AS KINKYU_TUCHI_FG ");
		sql.append("	,LPAD(' ', " + YOBI_LENGTH + ", '" + PADDING_STR_BLANK + "')                  AS YOBI ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("	SELECT  ");
		sql.append("		 IPC.EIGYO_DT_IF ");
		sql.append("		,IPC.TENPO_CD ");
		sql.append("		,COUNT(IPC.CLASS_CD) AS RECORD_CNT_QT ");
		sql.append("	FROM ");
		sql.append("		IF_POS_CLASS IPC ");
		sql.append("	GROUP BY ");
		sql.append("		 IPC.EIGYO_DT_IF ");
		sql.append("		,IPC.TENPO_CD ");
		sql.append("	) IPC ");
		sql.append("ORDER BY ");
		sql.append("	 IPC.TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_POS_CLASSを取得するSQLを取得する
	 * 
	 * @return IF_POS_CLASS取得SQL
	 */
	private String getIfPosClassSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,CLASS_CD ");
		sql.append("	,LINK_LINE_CD ");
		sql.append("	,RPAD(NVL(TRIM(CLASS_KANJI_NA), '" + PADDING_STR_BLANK + "'), " + CLASS_KANJI_NA_LENGTH + ", '" + PADDING_STR_BLANK + "' ) ");
		sql.append("	,RPAD(NVL(TRIM(CLASS_ANK_NA)  , '" + PADDING_STR_BLANK + "'), " + CLASS_ANK_NA_LENGTH  + " , '" + PADDING_STR_BLANK + "' ) ");
		sql.append("	,STATUS_1_CD ");
		sql.append("	,STATUS_2_CD ");
		sql.append("	,KAKAKU_HENKO_KINSHI_FG ");
		sql.append("	,TOKUTEI_CLASS_FG ");
		sql.append("	,NEIRER ");
		sql.append("	,'" + sakuseiDt + "' ");
		sql.append("	,'" + sakuseiTs + "' ");
		sql.append("FROM ");
		sql.append("	IF_POS_CLASS IPC ");
		sql.append("WHERE ");
		sql.append("	IPC.EIGYO_DT_IF	= '%s'	AND ");
		sql.append("	IPC.TENPO_CD	= '%s' ");
		sql.append("ORDER BY ");
		sql.append("	 DATA_KB ");
		sql.append("	,CLASS_CD ");

		return sql.toString();
	}

	/**
	 * IF_POS_LINE伝送ヘッダーを取得するSQLを取得する
	 * 
	 * @return IF_POS_LINE伝送ヘッダー取得SQL
	 */
	private String getIfPosLineDensoHeaderSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 '" + FILE_NA_SIKIBETSU_CONST + "'                                            AS FILE_NA_SIKIBETSU ");
		sql.append("	,RPAD('" + posFtpLineMainte + "', " + FILE_NA_LENGTH + ", ' ')               AS FILE_NA ");
		sql.append("	,LPAD(" + DENSO_HEADER_RECORD_LENGTH + " + " + LINE_RECORD_LENGTH + " * RECORD_CNT_QT , " + FILE_SIZE_LENGTH + ", '" + PADDING_STR + "') ");
		sql.append("	                                                                              AS FILE_SIZE ");
		sql.append("	,IPL.EIGYO_DT_IF                                                              AS EIGYO_DT ");
		sql.append("	,LPAD(IPL.TENPO_CD, " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')           AS TENPO_CD ");
		sql.append("	,'" + sakuseiDt + "'                                                          AS FILE_SAKUSEI_DT ");
		sql.append("	,'" + sakuseiTs + "'                                                          AS FILE_SAKUSEI_TS ");
		sql.append("	,LPAD(IPL.RECORD_CNT_QT, " + RECORD_CNT_QT_LENGTH + ", '" + PADDING_STR + "') AS RECORD_CNT_QT ");
		sql.append("	,'" + KINKYU_TUCHI_FG_CONST + "'                                              AS KINKYU_TUCHI_FG ");
		sql.append("	,LPAD(' ', " + YOBI_LENGTH + ", '" + PADDING_STR_BLANK + "')                  AS YOBI ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("	SELECT  ");
		sql.append("		 IPL.EIGYO_DT_IF ");
		sql.append("		,IPL.TENPO_CD ");
		sql.append("		,COUNT(IPL.LINE_CD) AS RECORD_CNT_QT ");
		sql.append("	FROM ");
		sql.append("		IF_POS_LINE IPL ");
		sql.append("	GROUP BY ");
		sql.append("		 IPL.EIGYO_DT_IF ");
		sql.append("		,IPL.TENPO_CD ");
		sql.append("	) IPL ");
		sql.append("ORDER BY ");
		sql.append("	 IPL.TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_POS_LINEを取得するSQLを取得する
	 * 
	 * @return IF_POS_LINE取得SQL
	 */
	private String getIfPosLineSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 DATA_KB ");
		sql.append("	,JIKKO_KB ");
		sql.append("	,LINE_CD ");
		sql.append("	,LINK_BUMON_CD ");
		sql.append("	,RPAD(NVL(TRIM(LINE_KANJI_NA), '" + PADDING_STR_BLANK + "'), " + LINE_KANJI_NA_LENGTH + ", '" + PADDING_STR_BLANK + "' ) ");
		sql.append("	,RPAD(NVL(TRIM(LINE_ANK_NA)  , '" + PADDING_STR_BLANK + "'), " + LINE_ANK_NA_LENGTH  + " , '" + PADDING_STR_BLANK + "' ) ");
		sql.append("	,NEIRER ");
		sql.append("	,'" + sakuseiDt + "' ");
		sql.append("	,'" + sakuseiTs + "' ");
		sql.append("FROM ");
		sql.append("	IF_POS_LINE IPL ");
		sql.append("WHERE ");
		sql.append("	IPL.EIGYO_DT_IF	= '%s'	AND ");
		sql.append("	IPL.TENPO_CD	= '%s' ");
		sql.append("ORDER BY ");
		sql.append("	 DATA_KB ");
		sql.append("	,LINE_CD ");

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
			val += SPLIT_CODE;
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
	 * <p>タイトル: MSTB992070_PosFileCreateRow クラス</p>
	 * <p>説明　: 伝送ヘッダーデータを保持する</p>
	 *
	 */
	class MSTB992070_PosFileCreateRow {

		/** 営業日 */
		private String eigyoDt;
		/** 店舗コード */
		private String tenpoCd;
		/** ファイルサイズ */
		private String fileSize;
		/** 伝送ヘッダーデータ */
		private String densoRowData;

		// デフォルトコンストラクタは使用禁止
		private MSTB992070_PosFileCreateRow() {
		};

		/**
		 * HSKB041040_PosFileCreateRow を生成
		 * 
		 * @param eigyoDt 営業日
		 * @param tenpoCd 店舗コード
		 * @param fileSize ファイルサイズ
		 * @param densoRowData 伝送ヘッダーデータ
		 */
		public MSTB992070_PosFileCreateRow(String eigyoDt, String tenpoCd, String fileSize, String densoRowData) {
			this.eigyoDt = eigyoDt;
			this.tenpoCd = tenpoCd;
			this.fileSize = fileSize;
			this.densoRowData = densoRowData;
		}

		/**
		 * 営業日を取得します。
		 * @return 営業日
		 */
		public String getEigyoDt() {
			return eigyoDt;
		}

		/**
		 * 店舗コードを取得します。
		 * @return 店舗コード
		 */
		public String getTenpoCd() {
			return tenpoCd;
		}

		/**
		 * ファイルサイズを取得します。
		 * @return ファイルサイズ
		 */
		public String getFileSize() {
			return fileSize;
		}

		/**
		 * 伝送ヘッダーデータを取得します。
		 * @return 伝送ヘッダーデータ
		 */
		public String getDensoRowData() {
			return densoRowData;
		}

	}

}
