package mdware.master.batch.process.MSTB920;

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
 * <p>タイトル: MSTB920030_PosFileCreate クラス</p>
 * <p>説明　 : POS連携ファイルをIF_緊急PLU単品メンテからデータを取得して作成する。</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: VINX</p>
 * @Version 1.00  (2015.10.13) THO.VT FIVImart様対応
 * @Version 1.01  (2015.11.11) DAI.BQ FIVImart対応
 * @Version 1.02  (2016.04.26) to  FIVIMART対応
 * @Version 1.03  (2016.05.06) M.Kanno  #1306 FIVIMART対応
 * @Version 1.04  (2016.05.12) M.Kanno  #1320 #1321 FIVIMART対応
 * @Version 1.05  (2016.05.19) DAI.BQ 設計書 #1334 FIVImart対応
 * @Version 1.06  (2016.06.02) M.Kanno  #1417 FIVImart対応
 * @Version 1.07  (2016.06.03) DAI.BQ #1474 FIVImart対応
 * @Version 1.08  (2016.09.06) S.Li #1871 FIVImart対応
 * @Version 1.09  (2016.09.29) M.Akagi #2186 FIVImart対応
 * @Version 1.10  (2016.11.28) S.Takayama #2839 FIVImart対応
 * @Version 1.01  (2016.12.09) Li.Sheng #3049 FIVImart対応 
 * @version 1.12  (2016.12.13) T.Han #3234 FIVImart対応
 * @version 1.13  (2017.02.09) M.Son #3765 FIVImart対応
 * @version 1.14  (2017.04.04) T.Han #2463 FIVIMART対応
 * @version 1.15  (2017.04.12) M.Kanno #4610 FIVIMART対応
 * @version 1.16  (2017.04.27) S.Nakazato #4824対応
 * @version 1.17  (2017.05.18) M.Son #5044対応
 * @version 1.18  (2020.07.13) KHAI.NN #6167 MKV対応
 * @version 1.19  (2020.09.22) KHAI.NN #6227 MKV対応
 * @version 1.20  (2020.09.30) KHAI.NN #6238 MKV対応
 * @version 1.21  (2023.12.01) TUNG.LT #20077 MKH対応
 * @Version 1.22  (2024.01.16) DUY.HM #15277 MKH対応
 */
public class MSTB920030_PosFileCreate {

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
	// #2839 MSTB920 2016.11.25 S.Takayama (S)
	private String csvFilePath2 = null;
	// #2839 MSTB920 2016.11.25 S.Takayama (E)
	// 2017.04.04 T.Han #2463対応（S)
	private String csvFilePath3 = null;
	private String csvFilePath4 = null;
	private String csvFilePath5 = null;
	private String csvFilePath6 = null;
	// 2017.04.04 T.Han #2463対応（E)
	/** 単品メンテナンス */
	public String posTanpinMainte = null;
	// 2017.04.04 T.Han #2463対応（S)
	/** 支払種別メンテナンス */
	public String posPaymentMainte = null;
	/** 特売種別メンテナンス */
	public String posDiscountMainte = null;
	// 2017.04.04 T.Han #2463対応（E)
	/** 部門メンテナンス */
	public String posBumonMainte = null;
	/** クラスメンテナンス */
	public String posDeptMainte = null;
	/** クラスメンテナンス */
	public String posClassMainte = null;
	/** サブクラスメンテナンス */
	public String posSubClassMainte = null;

	/** システム時刻 */
	private String timeStamp = "";
	/** 作成日 */
	private String sakuseiDt = "";
	/** 作成時刻 */
	private String sakuseiTs = "";
	/** 営業日 */
	private String eigyoDt = "";
	/** 店舗コード */
	private String tenpoCd = "";
	// #2839 MSTB920 2016.11.25 S.Takayama (S)
	/** 業態区分 */
	private String gyotaiKb = "";
	// #2839 MSTB920 2016.11.25 S.Takayama (E)
	/** 伝送ヘッダーレコードリスト */
	private List densoRecordList = null;

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	// 処理日間隔
	private static final int SPAN_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB920030_PosFileCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB920030_PosFileCreate() {
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

			sakuseiDt = timeStamp.substring(0, 4) + "/" + timeStamp.substring(4, 6) + "/" + timeStamp.substring(6, 8);
			sakuseiTs = timeStamp.substring(8, 10) + ":" + timeStamp.substring(10, 12);

			// 単品メンテナンス ファイル作成(S)
			String ifPluTanpinEigodtAndTenpocdSel = getIfPluEmgTanpinEigodtAndTenpocdSelectSql();
			rsData = db.executeQuery(ifPluTanpinEigodtAndTenpocdSel);
			densoRecordList = new ArrayList();
			while (rsData.next()){
				// 引数情報 営業日・店舗コード取得
				eigyoDt = rsData.getString("EIGYO_DT");
				tenpoCd = rsData.getString("TENPO_CD");
				// #2839 MSTB920 2016.11.25 S.Takayama (S)
				gyotaiKb = rsData.getString("GYOTAI_KB");
				// #2839 MSTB920 2016.11.25 S.Takayama (E)
				MSTB920030_PosFileCreateRow rowData = new MSTB920030_PosFileCreateRow(eigyoDt, tenpoCd, gyotaiKb);
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
				MSTB920030_PosFileCreateRow rowData = (MSTB920030_PosFileCreateRow) densoRecord;
				eigyoDt = rowData.getEigyoDt();
				tenpoCd = rowData.getTenpoCd();
				// #2839 MSTB920 2016.11.25 S.Takayama (S)
				gyotaiKb = rowData.getGyotaiKb();
				// #2839 MSTB920 2016.11.25 S.Takayama (E)
				posTanpinMainte = "I" + getName(eigyoDt, tenpoCd, String.format("%02d", fileNo));
				writeLog(Level.INFO_INT, "単品メンテナンス データファイル（" + posTanpinMainte + "）作成処理を開始します。");
				// #2839 MSTB920 2016.11.25 S.Takayama (S)
				// #6401 Mod 2021.12.10 KHOI.ND (S)
				// if("3".equals(gyotaiKb)){
				// 	iRec = createCSVFile(posTanpinMainte, getIfPluTanpinMainteSelectSql(),csvFilePath2);
				// }else{
				// 	iRec = createCSVFile(posTanpinMainte, getIfPluTanpinMainteSelectSql(),csvFilePath);
				// }
				iRec = createCSVFile(posTanpinMainte, getIfPluTanpinMainteSelectSql(),csvFilePath);
				// #6401 Mod 2021.12.10 KHOI.ND (E)
				// #2839 MSTB920 2016.11.25 S.Takayama (E)
				writeLog(Level.INFO_INT, "単品メンテナンス データファイルを" + iRec + "件作成しました。");
			}
			// 単品メンテナンス ファイル作成(E)

			// 2017.04.04 T.Han #2463対応（S)
			// 支払種別メンテナンスデータファイル作成(S)
			String ifEmgPosPaymentEigodtAndTenpocdSel = getIfEmgPosPaymentEigodtAndTenpocdSelectSql();
			rsData = db.executeQuery(ifEmgPosPaymentEigodtAndTenpocdSel);
			densoRecordList = new ArrayList();
			while (rsData.next()){
				// 引数情報 営業日・店舗コード取得
				eigyoDt = rsData.getString("EIGYO_DT");
				tenpoCd = rsData.getString("TENPO_CD");
				gyotaiKb = rsData.getString("GYOTAI_KB");
				MSTB920030_PosFileCreateRow rowData = new MSTB920030_PosFileCreateRow(eigyoDt, tenpoCd, gyotaiKb);
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
				MSTB920030_PosFileCreateRow rowData = (MSTB920030_PosFileCreateRow) densoRecord;
				eigyoDt = rowData.getEigyoDt();
				tenpoCd = rowData.getTenpoCd();
				gyotaiKb = rowData.getGyotaiKb();
				posPaymentMainte = "L" + getName(eigyoDt, tenpoCd, String.format("%02d", fileNo));
				writeLog(Level.INFO_INT, "支払種別メンテナンスデータファイル（" + posPaymentMainte + "）作成処理を開始します。");
				// #6401 Mod 2021.12.10 KHOI.ND (S)
				// if("3".equals(gyotaiKb)){
				// 	iRec = createCSVFile(posPaymentMainte, getIfEmgPosPaymentMainteSelectSql(),csvFilePath4);
				// }else{
				// 	iRec = createCSVFile(posPaymentMainte, getIfEmgPosPaymentMainteSelectSql(),csvFilePath3);
				// }
				iRec = createCSVFile(posPaymentMainte, getIfEmgPosPaymentMainteSelectSql(),csvFilePath3);
				// #6401 Mod 2021.12.10 KHOI.ND (E)
				writeLog(Level.INFO_INT, "支払種別メンテナンスデータファイルを" + iRec + "件作成しました。");
			}
			// 支払種別メンテナンスデータファイル作成(E)

			// 特売種別メンテナンスデータファイル作成(S)
			String ifEmgPosDiscountEigodtAndTenpocdSel = getIfEmgPosDiscountEigodtAndTenpocdSelectSql();
			rsData = db.executeQuery(ifEmgPosDiscountEigodtAndTenpocdSel);
			densoRecordList = new ArrayList();
			while (rsData.next()){
				// 引数情報 営業日・店舗コード取得
				eigyoDt = rsData.getString("EIGYO_DT");
				tenpoCd = rsData.getString("TENPO_CD");
				gyotaiKb = rsData.getString("GYOTAI_KB");
				MSTB920030_PosFileCreateRow rowData = new MSTB920030_PosFileCreateRow(eigyoDt, tenpoCd, gyotaiKb);
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
				MSTB920030_PosFileCreateRow rowData = (MSTB920030_PosFileCreateRow) densoRecord;
				eigyoDt = rowData.getEigyoDt();
				tenpoCd = rowData.getTenpoCd();
				gyotaiKb = rowData.getGyotaiKb();
				posDiscountMainte = "K" + getName(eigyoDt, tenpoCd, String.format("%02d", fileNo));
				writeLog(Level.INFO_INT, "特売種別メンテナンスデータファイル（" + posDiscountMainte + "）作成処理を開始します。");
				// #6401 Mod 2021.12.10 KHOI.ND (S)
				// if("3".equals(gyotaiKb)){
				// 	iRec = createCSVFile(posDiscountMainte, getIfEmgPosDiscountMainteSelectSql(),csvFilePath6);
				// }else{
				// 	iRec = createCSVFile(posDiscountMainte, getIfEmgPosDiscountMainteSelectSql(),csvFilePath5);
				// }
				iRec = createCSVFile(posDiscountMainte, getIfEmgPosDiscountMainteSelectSql(),csvFilePath5);
				// #6401 Mod 2021.12.10 KHOI.ND (E)
				writeLog(Level.INFO_INT, "特売種別メンテナンスデータファイルを" + iRec + "件作成しました。");
			}
			// 特売種別メンテナンスデータファイル作成(E)

			// 2017.04.04 T.Han #2463対応（E)

// 2016.09.29 M.Akgai #2186 (S)
//			//バッチ日付取得
//			String batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
//			writeLog(Level.INFO_INT, "バッチ日付： " + batchDate);
//
//			//商品分類体系作成日取得
//			String createDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_SAKUSEI_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
//			writeLog(Level.INFO_INT, "商品分類体系作成日： " + createDate);
//
//			writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");
//
//			//稼働日判定処理
//			if (!DateChanger.addDate(batchDate, SPAN_DAYS).equals(createDate)) {
//				// 処理を終了する
//				writeLog(Level.INFO_INT, "稼働日判定処理を終了します。(バッチ処理日≠商品分類体系作成日)");
//				writeLog(Level.INFO_INT, "処理を終了します。");
//				return;
//			}
//			writeLog(Level.INFO_INT, "稼働日判定処理を終了します。");
//
//			// 部門メンテナンス ファイル作成(S)
//			String  ifPosBumonEigodtAndTenpocdSel = getIfPosBumonEigodtAndTenpocdSelectSql();
//			rsData = db.executeQuery(ifPosBumonEigodtAndTenpocdSel);
//			densoRecordList = new ArrayList();
//			while (rsData.next()){
//				// 引数情報 営業日・店舗コード取得
//				eigyoDt = rsData.getString("EIGYO_DT");
//				tenpoCd = rsData.getString("TENPO_CD");
//				MSTB920030_PosFileCreateRow rowData = new MSTB920030_PosFileCreateRow(eigyoDt, tenpoCd);
//				densoRecordList.add(rowData);
//			}
//			for (Object densoRecord : densoRecordList) {
//				MSTB920030_PosFileCreateRow rowData = (MSTB920030_PosFileCreateRow) densoRecord;
//				eigyoDt = rowData.getEigyoDt();
//				tenpoCd = rowData.getTenpoCd();
//				posBumonMainte = "D" + getName(eigyoDt, tenpoCd, "01");
//				writeLog(Level.INFO_INT, "部門メンテナンス データファイル（" + posBumonMainte + "）作成処理を開始します。");
//				iRec = createCSVFile(posBumonMainte, getIfPosBumonMainteSelectSql());
//				writeLog(Level.INFO_INT, "部門メンテナンス データファイルを" + iRec + "件作成しました。");
//			}
//			// 部門メンテナンス ファイル作成(E)
//
//			// デプトメンテナンス ファイル作成(S)
//			String  ifPosDeptEigodtAndTenpocdSel = getIfPosDeptEigodtAndTenpocdSelectSql();
//			rsData = db.executeQuery(ifPosDeptEigodtAndTenpocdSel);
//			densoRecordList = new ArrayList();
//			while (rsData.next()){
//				// 引数情報 営業日・店舗コード取得
//				eigyoDt = rsData.getString("EIGYO_DT");
//				tenpoCd = rsData.getString("TENPO_CD");
//				MSTB920030_PosFileCreateRow rowData = new MSTB920030_PosFileCreateRow(eigyoDt, tenpoCd);
//				densoRecordList.add(rowData);
//			}
//			for (Object densoRecord : densoRecordList) {
//				MSTB920030_PosFileCreateRow rowData = (MSTB920030_PosFileCreateRow) densoRecord;
//				eigyoDt = rowData.getEigyoDt();
//				tenpoCd = rowData.getTenpoCd();
//				posDeptMainte = "T" + getName(eigyoDt, tenpoCd, "01");
//				writeLog(Level.INFO_INT, "デプトメンテナンス データファイル（" + posDeptMainte + "）作成処理を開始します。");
//				iRec = createCSVFile(posDeptMainte, getIfPosDeptMainteSelectSql());
//				writeLog(Level.INFO_INT, "デプトメンテナンスデータファイルを" + iRec + "件作成しました。");
//			}
//			// デプトメンテナンスファイル作成(E)
//
//			// クラスメンテナンス ファイル作成(S)
//			String  ifPosClassEigodtAndTenpocdSel = getIfPosClassEigodtAndTenpocdSelectSql();
//			rsData = db.executeQuery(ifPosClassEigodtAndTenpocdSel);
//			densoRecordList = new ArrayList();
//			while (rsData.next()){
//				// 引数情報 営業日・店舗コード取得
//				eigyoDt = rsData.getString("EIGYO_DT");
//				tenpoCd = rsData.getString("TENPO_CD");
//				MSTB920030_PosFileCreateRow rowData = new MSTB920030_PosFileCreateRow(eigyoDt, tenpoCd);
//				densoRecordList.add(rowData);
//			}
//			for (Object densoRecord : densoRecordList) {
//				MSTB920030_PosFileCreateRow rowData = (MSTB920030_PosFileCreateRow) densoRecord;
//				eigyoDt = rowData.getEigyoDt();
//				tenpoCd = rowData.getTenpoCd();
//				posClassMainte = "A" + getName(eigyoDt, tenpoCd, "01");
//				writeLog(Level.INFO_INT, "クラスメンテナンス データファイル（" + posClassMainte + "）作成処理を開始します。");
//				iRec = createCSVFile(posClassMainte, getIfPosClassMainteSelectSql());
//				writeLog(Level.INFO_INT, "クラスメンテナンス データファイルを" + iRec + "件作成しました。");
//			}
//			// クラスメンテナンス ファイル作成(E)
//
//			// サブクラスメンテナンス ファイル作成(S)
//			String  ifPosSubClassEigodtAndTenpocdSel = getIfPosSubClassEigodtAndTenpocdSelectSql();
//			rsData = db.executeQuery(ifPosSubClassEigodtAndTenpocdSel);
//			densoRecordList = new ArrayList();
//			while (rsData.next()){
//				// 引数情報 営業日・店舗コード取得
//				eigyoDt = rsData.getString("EIGYO_DT");
//				tenpoCd = rsData.getString("TENPO_CD");
//				MSTB920030_PosFileCreateRow rowData = new MSTB920030_PosFileCreateRow(eigyoDt, tenpoCd);
//				densoRecordList.add(rowData);
//			}
//			for (Object densoRecord : densoRecordList) {
//				MSTB920030_PosFileCreateRow rowData = (MSTB920030_PosFileCreateRow) densoRecord;
//				eigyoDt = rowData.getEigyoDt();
//				tenpoCd = rowData.getTenpoCd();
//				posSubClassMainte = "C" + getName(eigyoDt, tenpoCd, "01");
//				writeLog(Level.INFO_INT, "サブクラスメンテナンスデータファイル（" + posSubClassMainte + "）作成処理を開始します。");
//				iRec = createCSVFile(posSubClassMainte, getIfPosSubClassMainteSelectSql());
//				writeLog(Level.INFO_INT, "サブクラスメンテナンス データファイルを" + iRec + "件作成しました。");
//
//			}
// 2016.09.29 M.Akgai #2186 (E)
			db.executeUpdate(getPosFileSeqUpdateSql());
			db.commit();
			// サブクラスメンテナンスファイル作成(E)
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
	 * @param eigyoDt 営業日
	 * @param tenpoCd 店舗コード
	 * @param rr シーケンスNo
	 * @return name
	 */
	public String getName(String eigyoDt, String tenpoCd, String rr){
		String name = null;
		String M = eigyoDt.substring(4, 6);
		if("10".equals(M)){
			M = "A";
		}else if("11".equals(M)){
			M = "B";
		}else if("12".equals(M)){
			M = "C";
		}else {
			M = M.substring(1, 2);
		}
        // to 2016.04.27 S62対応　ファイル出力修正 (S)
        //        name = eigyoDt.substring(3, 4) + M + eigyoDt.substring(6, 8) + rr + tenpoCd.substring(2, 3) + "." +  tenpoCd.substring(3, 6);
        name = eigyoDt.substring(3, 4) + M + eigyoDt.substring(6, 8) + rr + tenpoCd.substring(2, 3) + "." +  tenpoCd.substring(3, 6) + "_noncompleted";
        // to 2016.04.27 S62対応　ファイル出力修正 (E)
		return name;
	}

	/**
	 * CSVファイルを作成します。
	 *
	 * @param fileNa データファイル名
	 * @param selSql メンテナンスレコード取得SQL
	 * @throws Exception
	 */
	// #2839 MSTB920 2016.11.30 S.Takayama add (String csvFilePath)
	public int createCSVFile(String fileNa, String selSql, String csvFilePath) throws Exception {

		ResultSet rs = null;
		String fileName = null;
		File file = null;
		// #1474 MSTB920030 Del 2016.06.03 DAI.BQ (S)
		//FileWriter fw = null;
		// #1474 MSTB920030 Del 2016.06.03 DAI.BQ (E)
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
				// #1474 MSTB920030 Mod 2016.06.03 DAI.BQ (S)
				//if( (fw == null) && (bw == null) ){
				if(bw == null){
					// ファイルオープン
					//fw = new FileWriter( fileName, true );
					//bw = new BufferedWriter( fw );
					bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
					// #1474 MSTB920030 Mod 2016.06.03 DAI.BQ (E)
				}

				// #1334 MSTB920030 Mod 2016.05.19 DAI.BQ オペレーションコード (S)
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

				// // 行データ作成
				// sb.append(this.createCsvRowData(rs));
				// // 行データ出力
				// bw.write(sb.toString());
				// #1334 MSTB920030 Mod 2016.05.19 DAI.BQ オペレーションコード (E)
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
			// #1474 MSTB920030 Del 2016.06.03 DAI.BQ (S)
//			if (fw != null) {
//				fw.close();
//			}
			// #1474 MSTB920030 Del 2016.06.03 DAI.BQ (E)
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
        // to 2016.04.28 v0r6　S62対応　桁数変更 (S)
//    	csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_PLU);
		csvFilePath = ResorceUtil.getInstance().getPropertie("FIVI_IFDIR_POS_ITEM");
		// #2839 MSTB920 2016.11.24 S.Takayama (S)
		csvFilePath2 = ResorceUtil.getInstance().getPropertie("FIVI_IFDIR_INI_POS_ITEM");
        // #2839 MSTB920 2016.11.24 S.Takayama (S)
		// 2017.04.04 T.Han #2463対応（S)
		csvFilePath3 = ResorceUtil.getInstance().getPropertie("FIVI_IFDIR_POS_PAYMENT");
		csvFilePath4 = ResorceUtil.getInstance().getPropertie("FIVI_IFDIR_INI_POS_PAYMENT");
		csvFilePath5 = ResorceUtil.getInstance().getPropertie("FIVI_IFDIR_POS_DISCOUNT");
		csvFilePath6 = ResorceUtil.getInstance().getPropertie("FIVI_IFDIR_INI_POS_DISCOUNT");
		// 2017.04.04 T.Han #2463対応（E)
        // to 2016.04.28 v0r6　S62対応　桁数変更 (E)
        if(csvFilePath == null || csvFilePath.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}
        // #2839 MSTB920 2016.11.25 S.Takayama (S)
        if(csvFilePath2 == null || csvFilePath2.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}
        // #2839 MSTB920 2016.11.25 S.Takayama (E)
		// 2017.04.04 T.Han #2463対応（S)
        if(csvFilePath3 == null || csvFilePath3.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}
        if(csvFilePath4 == null || csvFilePath4.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}
        if(csvFilePath5 == null || csvFilePath5.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}
        if(csvFilePath6 == null || csvFilePath6.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}
		// 2017.04.04 T.Han #2463対応（E)

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


	// #1334 MSTB920030 Add 2016.05.19 DAI.BQ オペレーションコード (S)
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
	// #1334 MSTB920030 Add 2016.05.19 DAI.BQ オペレーションコード (E)

/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_PLU_EMG_TANPINからEIGYO_DT、TENPO_CDを取得するSQLを取得する
	 *
	 * @return IF_PLU_EMG_TANPINからEIGYO_DT、TENPO_CDを取得SQL
	 */
	private String getIfPluEmgTanpinEigodtAndTenpocdSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT");
		sql.append("	EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		// #2839 MSTB920 2016.11.25 S.Takayama (S)
		sql.append("	,TRIM(GYOTAI_KB) AS GYOTAI_KB ");
		// #2839 MSTB920 2016.11.25 S.Takayama (E)
		sql.append("FROM ");
		sql.append("	IF_PLU_EMG_TANPIN ");
		sql.append("GROUP BY ");
		sql.append("	EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		// #2839 MSTB920 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB ");
		// #2839 MSTB920 2016.11.25 S.Takayama (E)
		sql.append("ORDER BY ");
		sql.append("	TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_PLU_TANPINからデータを取得するSQLを取得する
	 *
	 * @return IF_PLU_TANPINからデータを取得SQL
	 */
	private String getIfPluTanpinMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		// #939対応 2015.11.11 DAI.BQ Mod (S)
		sql.append("SELECT ");
		sql.append("	 IPT.TOROKU_ID ");
        // #1321対応 2016.05.12 M.Kanno (S)
        //sql.append("	,RPAD(NVL(IPT.SYOHIN_CD, ' '), 13, ' ') ");
        sql.append("	,LPAD(NVL(TRIM(IPT.SYOHIN_CD), '0'), 13, '0') ");
        // #1321対応 2016.05.12 M.Kanno (E)
        // #1334 MSTB920030 Add 2016.05.19 DAI.BQ オペレーションコード (S)
        sql.append("	,LPAD(TRIM(IPT.OLD_SYOHIN_CD), 13, '0') OLD_SYOHIN_CD ");
        // #1334 MSTB920030 Add 2016.05.19 DAI.BQ オペレーションコード (E)
        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_RN, ' '), 30), 0, 15) SYOHIN_RN ");
        // #6167 MSTB920 Mod 2020.07.13 KHAI.NN (S)
//        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA, ' '), 60), 0, 30) SYOHIN_NA ");
        // #6227 MSTB920 Mod 2020.09.22 KHAI.NN (S)
        //sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA, ' '), 240), 0, 120) SYOHIN_NA ");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA, ' '), 60), 0, 30) SYOHIN_NA ");
        // #6227 MSTB920 Mod 2020.09.22 KHAI.NN (E)
        // #6167 MSTB920 Mod 2020.07.13 KHAI.NN (E)
        // to 2016.04.25 v0r6　S62対応　桁数変更 (S)
        // sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_RN_CHN,' '), 40), 0, 20) SYOHIN_RN_CHN");
        // sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA_CHN, ' '), 80), 0, 40) SYOHIN_NA_CHN ");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_RN_CHN,' '), 40), 0, 25) SYOHIN_RN_CHN ");
        /* #1921 Mod 2016.09.06 Li.Sheng (S) */
        //sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA_CHN, ' '), 80), 0, 31) SYOHIN_NA_CHN ");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA_CHN, ' '), 80), 0, 40) SYOHIN_NA_CHN ");
        /* #1921 Mod 2016.09.06 Li.Sheng (E) */
        // sql.append("	,RPAD(NVL(IPT.SYOHIN_BAR_CD, ' '), 18, ' ') ");
        sql.append("	,LPAD(TRIM(TO_CHAR(NVL(TRIM(IPT.BAITANKA_VL), '0'), '999999999.99')), 17, '0') ");
        // sql.append("	,RPAD(NVL(TO_CHAR(IPT.KAIIN_BAITANKA_VL), ' '), 17, ' ') ");
        // #1306対応 2016.05.06 M.Kanno (S)
        //sql.append("	,RPAD(NVL(IPT.HANBAI_TN, ' '), 5, ' ') ");
        // #1306対応 2016.05.06 M.Kanno (E)
        // sql.append("	,RPAD(NVL(IPT.DIVISION_CD, ' '), 3, ' ') ");
        // sql.append("	,RPAD(NVL(IPT.DEPARTMENT_CD, ' '), 3, ' ') ");
		sql.append("	,RPAD(NVL(IPT.CLASS_CD, ' '), 6, ' ') ");
		sql.append("	,RPAD(NVL(IPT.SUBCLASS_CD, ' '), 9, ' ') ");
		sql.append("	,IPT.TEIKAN_FG ");
		sql.append("	,IPT.PLU_FG ");
        // sql.append("	,RPAD(NVL(IPT.CREATE_TS, ' '), 8) ");
        // sql.append("	,RPAD(NVL(TO_CHAR(IPT.ZEI_KB), ' '), 1, ' ') ");
	    // 2016/12/13 VINX t.han #3234対応（S)
        //sql.append("	,LPAD(NVL(TRIM(IPT.ZEI_RT), '0'), 3, '0') ");
        sql.append("	,DECODE(IPT.ZEI_KB, '3', '   ', LPAD(NVL(TRIM(IPT.ZEI_RT), '0'), 3, '0')) ");
	    // 2016/12/13 VINX t.han #3234対応（E)
        sql.append("	,RPAD(NVL(IPT.SEASON_ID, ' '), 6, ' ') ");
        // sql.append("	,LPAD(NVL(TRIM(IPT.HANBAI_ZEI_RT), '0'), 3, '0') ");
        // sql.append("	,IPT.STUDENT_WARIBIKI_CARD_FG ");
        // #1320対応 2016.05.12 M.Kanno (S)
        //sql.append("	,RPAD(NVL(TO_CHAR(IPT.SYOHI_KIGEN_DT), ' '), 3, ' ') ");
        // #1417対応 2016.06.02 M.Kanno (S)
        //sql.append("	,LPAD(NVL(TRIM(TO_CHAR(IPT.SYOHI_KIGEN_DT)), '0'), 3, '0') ");
        sql.append("	,LPAD(NVL(TRIM(TO_CHAR(IPT.SYOHI_KIGEN_DT)), '   '), 3, '0') ");
        // #1417対応 2016.06.02 M.Kanno (E)
        // #1320対応 2016.05.12 M.Kanno (E)
        // sql.append("	,IPT.CARD_FG ");
        // #1306対応 2016.05.06 M.Kanno (S)
        //sql.append("	,IPT.INJI_HANBAI_TN ");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.INJI_HANBAI_TN, ' '), 16), 0, 8) ");
        // #1306対応 2016.05.06 M.Kanno (E)
        // sql.append("	,IPT.INJI_SEIZOU_DT ");
        // sql.append("	,IPT.ZEI_CD ");
        sql.append("	,SIIRESAKI_CD ");
        /* #1921 Add 2016.09.06 Li.Sheng (S) */
        sql.append("	,NVL(IPT.SYOHI_KIGEN_HYOJI_PATTER, ' ') ");
        sql.append("	,RPAD(NVL(IPT.LABEL_SEIBUN, ' '), 20) ");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.LABEL_HOKAN_HOHO, ' '), 30), 0, 15) ");
        sql.append("	,RPAD(NVL(IPT.LABEL_TUKAIKATA, ' '), 15) ");
        /* #1921 Add 2016.09.06 Li.Sheng (E) */
// #3049 Add 2016.12.09 Li.Sheng (S)
        sql.append("	,RPAD(NVL(IPT.LABEL_COUNTRY_NA, ' '), 25) ");
// #3049 Add 2016.12.09 Li.Sheng (E)
        // #3765 Add 2017.02.09 M.Son (S)
        sql.append("	,RPAD(NVL(IPT.INJI_HANBAI_TN_EN, ' '), 8) ");
        // #3765 Add 2017.02.09 M.Son (E)
        // #6238 MSTB920 Add 2020.09.22 KHAI.NN (S)
        sql.append("	,SUBSTR(RPAD(NVL(IPT.ITEM_OFFICIAL_NA, ' '), 240), 0, 120) ITEM_OFFICIAL_NA ");
        // #6238 MSTB920 Add 2020.09.22 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("	,LPAD(NVL(TRIM(IPT.MAX_BUY_QT), '   '), 3, '0') MAX_BUY_QT ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append(" FROM ");
        // #1001対応 2015.12.01 DAI.BQ Mod (S)
        sql.append("	IF_PLU_EMG_TANPIN IPT ");
        // #1001対応 2015.12.01 DAI.BQ Mod (E)
        sql.append("WHERE ");
        sql.append("	IPT.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IPT.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	IPT.TOROKU_ID ");
        sql.append("	IPT.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IPT.SYOHIN_CD ");
        // #939対応 2015.11.11 DAI.BQ Mod (E)
        // to 2016.04.25 v0r6　S62対応　桁数変更 (E)
		return sql.toString();
	}


	// 2017.04.04 T.Han #2463対応（S)
	/**
	 * IF_EMG_POS_PAYMENTからEIGYO_DT、TENPO_CDを取得するSQLを取得する
	 *
	 * @return IF_EMG_POS_PAYMENTからEIGYO_DT、TENPO_CDを取得SQL
	 */
	private String getIfEmgPosPaymentEigodtAndTenpocdSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT");
		sql.append("	EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,TRIM(GYOTAI_KB) AS GYOTAI_KB ");
		sql.append("FROM ");
		sql.append("	IF_EMG_POS_PAYMENT ");
		sql.append("GROUP BY ");
		sql.append("	EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,GYOTAI_KB ");
		sql.append("ORDER BY ");
		sql.append("	TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_EMG_POS_PAYMENTからデータを取得するSQLを取得する
	 *
	 * @return IF_EMG_POS_PAYMENTからデータを取得SQL
	 */
	private String getIfEmgPosPaymentMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("	 IEPP.TOROKU_ID ");
		sql.append("	,RPAD(NVL(IEPP.SHIHARAI_SYUBETSU_SEQ, ' '), 4, ' ') ");
        sql.append("	,SUBSTR(RPAD(NVL(IEPP.SHIHARAI_SYUBETSU_EN_NA, ' '), 40), 0, 20) SHIHARAI_SYUBETSU_EN_NA ");
        sql.append("	,SUBSTR(RPAD(NVL(IEPP.SHIHARAI_SYUBETSU_VN_NA, ' '), 40), 0, 20) SHIHARAI_SYUBETSU_VN_NA ");
        sql.append("	,RPAD(NVL(IEPP.POS_SEQ, ' '), 2, ' ') ");
		sql.append("	,NVL(IEPP.OVER_TYPE, ' ') ");
		sql.append("	,NVL(IEPP.NEED_AUTHORITY, ' ') ");
		sql.append("	,NVL(IEPP.NEED_EXPIRY, ' ') ");
		sql.append("	,NVL(IEPP.CARD_NUMBER, ' ') ");
        sql.append("	,SUBSTR(RPAD(NVL(IEPP.PROCESS_TYPE, ' '), 60), 0, 30) PROCESS_TYPE ");
        sql.append("	,RPAD(NVL(IEPP.SHIHARAI_SYUBETSU_GROUP_SEQ, ' '), 4, ' ') ");
        sql.append("	,RPAD(NVL(IEPP.CARD_LENGTH, ' '), 2, ' ') ");
        sql.append("	,RPAD(NVL(IEPP.SHIHARAI_SYUBETSU_SUB_CD, ' '), 7, ' ') ");
		sql.append("	,NVL(IEPP.DISPLAY_FG, ' ') ");
		sql.append("	,NVL(IEPP.VOID_FG, ' ') ");
		sql.append("	,NVL(IEPP.RETURN_FG, ' ') ");
		sql.append("	,NVL(IEPP.OPEN_DRAWER_FG, ' ') ");
		sql.append("	,NVL(IEPP.EXTRA_RECEIPT, ' ') ");
		sql.append("	,NVL(IEPP.MAXIMUM_RECEIPT, ' ') ");
        sql.append("	,RPAD(NVL(IEPP.YUKO_START_DT, ' '), 8, ' ') ");
        sql.append("	,RPAD(NVL(IEPP.YUKO_END_DT, ' '), 8, ' ') ");
		sql.append("	,NVL(IEPP.JIKASYOHI_KB, ' ') ");
        sql.append("	,SUBSTR(RPAD(NVL(IEPP.JIKASYOHI_RECEIPT_VN_NA, ' '), 80), 0, 40) JIKASYOHI_RECEIPT_VN_NA ");
        sql.append(" FROM ");
        sql.append("	IF_EMG_POS_PAYMENT IEPP ");
        sql.append("WHERE ");
        sql.append("	IEPP.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IEPP.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	IEPP.TOROKU_ID ");
        sql.append("	IEPP.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IEPP.SHIHARAI_SYUBETSU_SEQ ");
		return sql.toString();
	}

	/**
	 * IF_EMG_POS_DISCOUNTからEIGYO_DT、TENPO_CDを取得するSQLを取得する
	 *
	 * @return IF_EMG_POS_DISCOUNTからEIGYO_DT、TENPO_CDを取得SQL
	 */
	private String getIfEmgPosDiscountEigodtAndTenpocdSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT");
		sql.append("	EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,TRIM(GYOTAI_KB) AS GYOTAI_KB ");
		sql.append("FROM ");
		sql.append("	IF_EMG_POS_DISCOUNT ");
		sql.append("GROUP BY ");
		sql.append("	EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,GYOTAI_KB ");
		sql.append("ORDER BY ");
		sql.append("	TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_EMG_POS_DISCOUNTからデータを取得するSQLを取得する
	 *
	 * @return IF_EMG_POS_DISCOUNTからデータを取得SQL
	 */
	private String getIfEmgPosDiscountMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("	 IEPD.TOROKU_ID ");
        sql.append("	,RPAD(NVL(IEPD.DISCOUNT_CD, ' '), 3, ' ') ");
        sql.append("	,RPAD(NVL(IEPD.SUB_DISCOUNT_CD, ' '), 5, ' ') ");
        sql.append("	,SUBSTR(RPAD(NVL(IEPD.DISCOUNT_NA, ' '), 40), 0, 20) DISCOUNT_NA ");
        sql.append("	,SUBSTR(RPAD(NVL(IEPD.SUB_DISCOUNT_NA, ' '), 40), 0, 20) SUB_DISCOUNT_NA ");
		sql.append("	,NVL(IEPD.RECEIPT_QT, ' ') ");
		sql.append("	,NVL(IEPD.MAX_RECEIPT_QT, ' ') ");
		// 2017.04.12 M.Kanno #4610 (S)
		//sql.append("	,RPAD(NVL(IEPD.NEBIKI_RITU_VL, ' '), 3, ' ') ");
		sql.append("	,LPAD(NVL(TRIM(IEPD.NEBIKI_RITU_VL),'0'), 3, '0') ");
		// 2017.04.12 M.Kanno #4610 (E)
		sql.append("	,NVL(IEPD.YUKO_START_DT, '        ') ");
		sql.append("	,NVL(IEPD.YUKO_END_DT, '        ') ");
        sql.append("	,LPAD(TRIM(TO_CHAR(NVL(TRIM(IEPD.MAX_NEBIKI_GAKU_VL), '0'), '99999999999999.99')), 17, '0') ");
		sql.append("	,NVL(IEPD.CARD_KB, ' ') ");
        // 2017.04.27 S.Nakazato #4824 (S)
        // 2017.05.18 M.Son #5044対応（S)
        //sql.append("	,RPAD(NVL(TRIM(IEPD.SHIHARAI_JOKEN_CD),' '), 7, ' ') ");
        sql.append("	,RPAD(NVL(TRIM(IEPD.SHIHARAI_JOKEN_CD),' '), 4, ' ') ");
        // 2017.05.18 M.Son #5044対応（E)
        // 2017.04.27 S.Nakazato #4824 (E)
        sql.append(" FROM ");
        sql.append("	IF_EMG_POS_DISCOUNT IEPD ");
        sql.append("WHERE ");
        sql.append("	IEPD.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IEPD.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)        
        // sql.append("	IEPD.TOROKU_ID ");
        sql.append("	IEPD.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IEPD.DISCOUNT_CD ");
		return sql.toString();
	}

	// 2017.04.04 T.Han #2463対応（E)


	/**
	 * IF_POS_BUMONからEIGYO_DT、TENPO_CDを取得するSQLを取得する
	 *
	 * @return IF_POS_BUMONからEIGYO_DT、TENPO_CDを取得SQL
	 */
	private String getIfPosBumonEigodtAndTenpocdSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	BATCH_DT AS EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("FROM ");
		sql.append("	IF_POS_BUMON ");
		sql.append("GROUP BY ");
		sql.append("	BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("ORDER BY ");
		sql.append("	TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_POS_BUMONからデータを取得するSQLを取得する
	 *
	 * @return IF_POS_BUMONからデータを取得SQL
	 */
	private String getIfPosBumonMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		// #939対応 2015.11.11 DAI.BQ Mod (S)
		sql.append("SELECT ");
		sql.append("	IPB.TOROKU_ID ");
		sql.append("	,RPAD(IPB.DIVISION_CD, 3, ' ') ");
		sql.append("	,SUBSTR(RPAD(NVL(IPB.DIVISION_NA, ' '), 60), 0, 30) DIVISION_NA ");
		sql.append("	,RPAD(NVL(IPB.PRIME_GROUP, ' '), 3, ' ') ");
		sql.append("	,SUBSTR(RPAD(NVL(IPB.PRIME_GROUP_NA, ' '), 60), 0, 30) PRIME_GROUP_NA ");
		sql.append("FROM ");
		sql.append("	IF_POS_BUMON IPB ");
		sql.append("WHERE ");
		sql.append("	IPB.BATCH_DT = '" + eigyoDt + "' ");
		sql.append("	AND ");
		sql.append("	IPB.TENPO_CD = '" + tenpoCd + "' ");
		sql.append("ORDER BY ");
		sql.append("	IPB.TOROKU_ID ");
		sql.append("	,IPB.DIVISION_CD ");
		// #939対応 2015.11.11 DAI.BQ Mod (E)
		return sql.toString();
	}

	/**
	 * IF_POS_DPTからEIGYO_DT、TENPO_CDを取得するSQLを取得する
	 *
	 * @return IF_POS_DPTからEIGYO_DT、TENPO_CDを取得SQL
	 */
	private String getIfPosDeptEigodtAndTenpocdSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	BATCH_DT AS EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("FROM ");
		sql.append("	IF_POS_DPT ");
		sql.append("GROUP BY ");
		sql.append("	BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("ORDER BY ");
		sql.append("	TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_POS_DPTからデータを取得するSQLを取得する
	 *
	 * @return IF_POS_DPTからデータを取得SQL
	 */
	private String getIfPosDeptMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		// #939対応 2015.11.11 DAI.BQ Mod (S)
		sql.append("SELECT ");
		sql.append("	IPD.TOROKU_ID  ");
		sql.append("	,RPAD(IPD.DIVISION_CD, 3, ' ') ");
		sql.append("	,RPAD(IPD.DEPARTMENT_CD, 3, ' ') ");
		sql.append("	,SUBSTR(RPAD(NVL(IPD.DEPARTMENT_NA, ' '), 60), 0, 30) DEPARTMENT_NA ");
		sql.append("FROM ");
		sql.append("	IF_POS_DPT IPD ");
		sql.append("WHERE");
		sql.append("	IPD.BATCH_DT = '" + eigyoDt + "' ");
		sql.append("	AND ");
		sql.append("	IPD.TENPO_CD = '" + tenpoCd + "' ");
		sql.append("ORDER BY ");
		sql.append("	IPD.TOROKU_ID  ");
		sql.append("	,IPD.DIVISION_CD ");
		sql.append("	,IPD.DEPARTMENT_CD ");
		// #939対応 2015.11.11 DAI.BQ Mod (E)
		return sql.toString();
	}

	/**
	 * IF_POS_CLASS_FIVIからEIGYO_DT、TENPO_CDを取得するSQLを取得する
	 *
	 * @return IF_POS_CLASS_FIVIからEIGYO_DT、TENPO_CDを取得するSQL
	 */
	private String getIfPosClassEigodtAndTenpocdSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	BATCH_DT AS EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("FROM ");
		sql.append("	IF_POS_CLASS_FIVI ");
		sql.append("GROUP BY ");
		sql.append("	BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("ORDER BY ");
		sql.append("	TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_POS_CLASS_FIVIからデータを取得するSQLを取得する
	 *
	 * @return IF_POS_CLASS_FIVIからデータを取得するSQL
	 */
	private String getIfPosClassMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		// #939対応 2015.11.11 DAI.BQ Mod (S)
		sql.append("SELECT ");
		sql.append("	IPCV.TOROKU_ID ");
		sql.append("	,RPAD(IPCV.DEPARTMENT_CD, 3, ' ') ");
		sql.append("	,RPAD(IPCV.CLASS_CD, 6, ' ') ");
		sql.append("	,SUBSTR(RPAD(NVL(IPCV.CLASS_NA, ' '), 80), 0, 40) CLASS_NA ");
		sql.append("	,IPCV.DEPARTMENT_TYPE ");
		sql.append("FROM ");
		sql.append("	IF_POS_CLASS_FIVI IPCV ");
		sql.append("WHERE");
		sql.append("	IPCV.BATCH_DT = '" + eigyoDt + "' ");
		sql.append("	AND ");
		sql.append("	IPCV.TENPO_CD = '" + tenpoCd + "' ");
		sql.append("ORDER BY ");
		sql.append("	IPCV.TOROKU_ID ");
		sql.append("	,IPCV.DEPARTMENT_CD ");
		sql.append("	,IPCV.CLASS_CD ");
		// #939対応 2015.11.11 DAI.BQ Mod (E)
		return sql.toString();
	}

	/**
	 * IF_POS_SUBCLASSからEIGYO_DT、TENPO_CDを取得するSQLを取得する
	 *
	 * @return IF_POS_SUBCLASSからEIGYO_DT、TENPO_CDを取得するSQL
	 */
	private String getIfPosSubClassEigodtAndTenpocdSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	BATCH_DT AS EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("FROM ");
		sql.append("	IF_POS_SUBCLASS ");
		sql.append("GROUP BY ");
		sql.append("	BATCH_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("ORDER BY ");
		sql.append("	TENPO_CD ");

		return sql.toString();
	}

	/**
	 * IF_POS_SUBCLASS伝送ヘッダーを取得するSQLを取得する
	 *
	 * @return IF_POS_SUBCLASS伝送ヘッダー取得SQL
	 */
	private String getIfPosSubClassMainteSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		// #939対応 2015.11.11 DAI.BQ Mod (S)
		sql.append("SELECT ");
		sql.append("	IPSC.TOROKU_ID ");
		sql.append("	,RPAD(IPSC.CLASS_CD,6,' ') ");
		sql.append("	,RPAD(IPSC.SUBCLASS_CD,9,' ') ");
		sql.append("	,SUBSTR(RPAD(NVL(IPSC.SUBCLASS_NA, ' '), 60), 0, 30) SUBCLASS_NA ");
		sql.append("	,RPAD(NVL(IPSC.AEON_CARD_NEBIKI_FG,' '),1,' ') ");
		sql.append("	,IPSC.CHG_VL_NEBIKI_BTN_FG ");
		sql.append("	,IPSC.KAIIN_CARD_NEBIKI_RT ");
		sql.append("	,IPSC.MATERNITY_CARD_NEBIKI_RT ");
		sql.append("FROM ");
		sql.append("	IF_POS_SUBCLASS IPSC ");
		sql.append("WHERE");
		sql.append("	IPSC.BATCH_DT = '" + eigyoDt + "' ");
		sql.append("	AND ");
		sql.append("	IPSC.TENPO_CD = '" + tenpoCd + "' ");
		sql.append("ORDER BY ");
		sql.append("	IPSC.TOROKU_ID ");
		sql.append("	,IPSC.CLASS_CD ");
		sql.append("	,IPSC.SUBCLASS_CD ");
		// #939対応 2015.11.11 DAI.BQ Mod (E)
		return sql.toString();
	}

	/**
	 * @return fileNo (シーケンスNo)
	 * @throws Exception
	 */
	private String getFileNoSelectSql() throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT FILENO ");
		sql.append(" FROM POS_FILE_SEQ ");

		return sql.toString();
	}

	/**
	 * Count up No. File sequence update sql
	 *
	 * @return String sql
	 */
	private String getPosFileSeqUpdateSql(){
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE POS_FILE_SEQ SET FILENO = FILENO + 1");
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
			// #939対応 2015.11.11 DAI.BQ Del (S)
			//val += SPLIT_CODE;
			// #939対応 2015.11.11 DAI.BQ Del (S)
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
	 * <p>タイトル: MSTB920030_PosFileCreateRow クラス</p>
	 * <p>説明　: 伝送ヘッダーデータを保持する</p>
	 *
	 */
	class MSTB920030_PosFileCreateRow {

		/** 営業日 */
		private String eigyoDt;
		/** 店舗コード */
		private String tenpoCd;
		// #2839 MSTB920 2016.11.25 S.Takayama (S)
		/** 業態区分 */
		private String gyotaiKb;
		// #2839 MSTB920 2016.11.25 S.Takayama (E)

		// デフォルトコンストラクタは使用禁止
		private MSTB920030_PosFileCreateRow() {
		};

		/**
		 * MSTB920030_PosFileCreateRow を生成
		 *
		 * @param eigyoDt 営業日
		 * @param tenpoCd 店舗コード
		 * @param gyotaiKb 業態区分
		 */
		// #2839 MSTB920 2016.11.30 S.Takayama add (String gyotaiKb)
		public MSTB920030_PosFileCreateRow(String eigyoDt, String tenpoCd, String gyotaiKb) {
			this.eigyoDt = eigyoDt;
			this.tenpoCd = tenpoCd;
			// #2839 MSTB920 2016.11.25 S.Takayama (S)
			this.gyotaiKb = gyotaiKb;
			// #2839 MSTB920 2016.11.25 S.Takayama (E)
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
		// #2839 MSTB920 2016.11.25 S.Takayama (S)
		/**
		 * 業態区分を取得します。
		 * @return 業態区分
		 */
		public String getGyotaiKb() {
			return gyotaiKb;
		}
		// #2839 MSTB920 2016.11.25 S.Takayama (E)

	}

}
