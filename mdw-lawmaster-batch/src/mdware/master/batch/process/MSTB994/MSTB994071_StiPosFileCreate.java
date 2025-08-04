package mdware.master.batch.process.MSTB994;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
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
import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB994071_StiPosFileCreate.java クラス</p>
 * <p>説明　 : POS連携ファイルを、IF_指定日POS部門メンテ、IF_指定日POSデプトメンテ、IF_指定日POSクラスメンテ、IF_指定日POSサブクラスメンテ、
 * <br>IF_指定日PLU単品メンテ、IF_指定日POS支払種別メンテ、IF_指定日POS特売種別メンテ　からデータを取得して作成する。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.01.16) 新規作成 #1749対応 T.Han
 * @version 1.01 (2017.01.25) M.Akagi #3739
 * @version 1.02 (2017.01.31) X.Liu #3732対応
 * @version 1.03 (2017.02.09) #3765 S.Takayama
 * @version 1.04 (2017.02.16) #4004 T.Han FIVImart対応
 * @version 1.05 (2017.04.26) #4824 T.Han FIVImart対応
 * @version 1.06 (2017.05.19) #5044 M.Son FIVImart対応
 * @version 1.07 (2020.07.13) KHAI.NN #6167 MKV対応
 * @version 1.08 (2020.09.22) KHAI.NN #6227 MKV対応
 * @version 1.09 (2020.09.30) KHAI.NN #6238 MKV対応
 * @version 1.10 (2023.12.01) TUNG.LT #20077 MKH対応
 * @Version 1.11 (2024.01.16) DUY.HM #15277 MKH対応
 */
public class MSTB994071_StiPosFileCreate {

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
    private String csvFilePath1 = null;
    /** 単品メンテナンス */
    public String posTanpinMainte = null;
    /** 支払種別メンテナンス */
    public String posPaymentMainte = null;
    /** 特売種別メンテナンス */
    public String posDiscountMainte = null;
    /** 部門メンテナンス */
    public String posBumonMainte = null;
    /** デプトメンテナンス */
    public String posDeptMainte = null;
    /** クラスメンテナンス */
    public String posClassMainte = null;
    /** サブクラスメンテナンス */
    public String posSubClassMainte = null;

    /** システム時刻 */
    private String timeStamp = "";
    /** 営業日 */
    private String eigyoDt = "";
    /** 店舗コード */
    private String tenpoCd = "";
    /** 伝送ヘッダーレコードリスト */
    private List densoRecordList = null;

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB994071_StiPosFileCreate(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB994071_StiPosFileCreate() {
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

			//バッチ日付取得
			String batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDate);

			// トランザクションログ有無（AutoCommitモード）
            // （trueを指定すると、トランザクションログ出力をしない分の速度向上
            // 　が見込めますが、コミット・ロールバックが無効となります。）
            db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");

            // システムコントロール情報取得
            this.getSystemControl();
            String tmpCsvFilePath = csvFilePath1;

            // 単品メンテナンス ファイル作成(S)
            String ifPluTanpinEigodtAndTenpocdSel = getIfPluTanpinEigodtAndTenpocdSelectSql();
            rsData = db.executeQuery(ifPluTanpinEigodtAndTenpocdSel);
            densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 営業日・店舗コード取得
                eigyoDt = rsData.getString("EIGYO_DT");
                tenpoCd = rsData.getString("TENPO_CD");
                MSTB994071_StiPosFileCreateRow rowData = new MSTB994071_StiPosFileCreateRow(eigyoDt, tenpoCd);
                densoRecordList.add(rowData);
            }
            for (Object densoRecord : densoRecordList) {
            	MSTB994071_StiPosFileCreateRow rowData = (MSTB994071_StiPosFileCreateRow) densoRecord;
                eigyoDt = rowData.getEigyoDt();
                tenpoCd = rowData.getTenpoCd();
                csvFilePath1 = tmpCsvFilePath + "\\" + batchDate + "\\" + tenpoCd;
                posTanpinMainte = "I" + getName(eigyoDt, tenpoCd);
                writeLog(Level.INFO_INT, "単品メンテナンス データファイル（" + posTanpinMainte + "）作成処理を開始します。");
				iRec = createCSVFile5(posTanpinMainte, getIfPluTanpinMainteSelectSql(),csvFilePath1);
                writeLog(Level.INFO_INT, "単品メンテナンス データファイルを" + iRec + "件作成しました。");
            }
            // 単品メンテナンス ファイル作成(E)

            // 支払種別メンテナンスデータファイル作成（S)
            String ifPosPaymentEigodtAndTenpocdSel = getIfPosPaymentEigodtAndTenpocdSelectSql();
            rsData = db.executeQuery(ifPosPaymentEigodtAndTenpocdSel);
            densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 営業日・店舗コード取得
                eigyoDt = rsData.getString("EIGYO_DT");
                tenpoCd = rsData.getString("TENPO_CD");
                MSTB994071_StiPosFileCreateRow rowData = new MSTB994071_StiPosFileCreateRow(eigyoDt, tenpoCd);
                densoRecordList.add(rowData);
            }
            for (Object densoRecord : densoRecordList) {
            	MSTB994071_StiPosFileCreateRow rowData = (MSTB994071_StiPosFileCreateRow) densoRecord;
                eigyoDt = rowData.getEigyoDt();
                tenpoCd = rowData.getTenpoCd();
                csvFilePath1 = tmpCsvFilePath + "\\" + batchDate + "\\" + tenpoCd;
                posPaymentMainte = "L" + getName(eigyoDt, tenpoCd);
                writeLog(Level.INFO_INT, "支払種別メンテナンス データファイル（" + posPaymentMainte + "）作成処理を開始します。");
				iRec = createCSVFile6(posPaymentMainte, getIfPosPaymentMainteSelectSql(),csvFilePath1);
                writeLog(Level.INFO_INT, "支払種別メンテナンス データファイルを" + iRec + "件作成しました。");
            }
            // 支払種別メンテナンスデータファイル作成（E)

            // 特売種別メンテナンスデータファイル作成（S)
            String ifPosDiscountEigodtAndTenpocdSel = getIfPosDiscountEigodtAndTenpocdSelectSql();
            rsData = db.executeQuery(ifPosDiscountEigodtAndTenpocdSel);
            densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 営業日・店舗コード取得
                eigyoDt = rsData.getString("EIGYO_DT");
                tenpoCd = rsData.getString("TENPO_CD");
                MSTB994071_StiPosFileCreateRow rowData = new MSTB994071_StiPosFileCreateRow(eigyoDt, tenpoCd);
                densoRecordList.add(rowData);
            }
            for (Object densoRecord : densoRecordList) {
            	MSTB994071_StiPosFileCreateRow rowData = (MSTB994071_StiPosFileCreateRow) densoRecord;
                eigyoDt = rowData.getEigyoDt();
                tenpoCd = rowData.getTenpoCd();
                csvFilePath1 = tmpCsvFilePath + "\\" + batchDate + "\\" + tenpoCd;
                posDiscountMainte = "K" + getName(eigyoDt, tenpoCd);
                writeLog(Level.INFO_INT, "特売種別メンテナンス データファイル（" + posDiscountMainte + "）作成処理を開始します。");
				iRec = createCSVFile7(posDiscountMainte, getIfPosDiscountMainteSelectSql(),csvFilePath1);
                writeLog(Level.INFO_INT, "特売種別メンテナンス データファイルを" + iRec + "件作成しました。");
            }
            // 特売種別メンテナンスデータファイル作成（E)

            // 部門メンテナンス ファイル作成(S)
            String  ifPosBumonEigodtAndTenpocdSel = getIfPosBumonEigodtAndTenpocdSelectSql();
            rsData = db.executeQuery(ifPosBumonEigodtAndTenpocdSel);
            densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 営業日・店舗コード取得
                eigyoDt = rsData.getString("EIGYO_DT");
                tenpoCd = rsData.getString("TENPO_CD");
                MSTB994071_StiPosFileCreateRow rowData = new MSTB994071_StiPosFileCreateRow(eigyoDt, tenpoCd);
                densoRecordList.add(rowData);
            }
            for (Object densoRecord : densoRecordList) {
            	MSTB994071_StiPosFileCreateRow rowData = (MSTB994071_StiPosFileCreateRow) densoRecord;
                eigyoDt = rowData.getEigyoDt();
                tenpoCd = rowData.getTenpoCd();
                csvFilePath1 = tmpCsvFilePath + "\\" + batchDate + "\\" + tenpoCd;
                posBumonMainte = "D" + getName(eigyoDt, tenpoCd);
                writeLog(Level.INFO_INT, "部門メンテナンス データファイル（" + posBumonMainte + "）作成処理を開始します。");
				iRec = createCSVFile1(posBumonMainte, getIfPosBumonMainteSelectSql(),csvFilePath1);
                writeLog(Level.INFO_INT, "部門メンテナンス データファイルを" + iRec + "件作成しました。");
            }
            // 部門メンテナンス ファイル作成(E)

            // デプトメンテナンス ファイル作成(S)
            String  ifPosDeptEigodtAndTenpocdSel = getIfPosDeptEigodtAndTenpocdSelectSql();
            rsData = db.executeQuery(ifPosDeptEigodtAndTenpocdSel);
            densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 営業日・店舗コード取得
                eigyoDt = rsData.getString("EIGYO_DT");
                tenpoCd = rsData.getString("TENPO_CD");
                MSTB994071_StiPosFileCreateRow rowData = new MSTB994071_StiPosFileCreateRow(eigyoDt, tenpoCd);
                densoRecordList.add(rowData);
            }
            for (Object densoRecord : densoRecordList) {
            	MSTB994071_StiPosFileCreateRow rowData = (MSTB994071_StiPosFileCreateRow) densoRecord;
                eigyoDt = rowData.getEigyoDt();
                tenpoCd = rowData.getTenpoCd();
                csvFilePath1 = tmpCsvFilePath + "\\" + batchDate + "\\" + tenpoCd;
                posDeptMainte = "T" + getName(eigyoDt, tenpoCd);
                writeLog(Level.INFO_INT, "デプトメンテナンス データファイル（" + posDeptMainte + "）作成処理を開始します。");
				iRec = createCSVFile2(posDeptMainte, getIfPosDeptMainteSelectSql(),csvFilePath1);
                writeLog(Level.INFO_INT, "デプトメンテナンスデータファイルを" + iRec + "件作成しました。");
            }
            // デプトメンテナンスファイル作成(E)

            // クラスメンテナンス ファイル作成(S)
            String  ifPosClassEigodtAndTenpocdSel = getIfPosClassEigodtAndTenpocdSelectSql();
            rsData = db.executeQuery(ifPosClassEigodtAndTenpocdSel);
            densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 営業日・店舗コード取得
                eigyoDt = rsData.getString("EIGYO_DT");
                tenpoCd = rsData.getString("TENPO_CD");
                MSTB994071_StiPosFileCreateRow rowData = new MSTB994071_StiPosFileCreateRow(eigyoDt, tenpoCd);
                densoRecordList.add(rowData);
            }
            for (Object densoRecord : densoRecordList) {
            	MSTB994071_StiPosFileCreateRow rowData = (MSTB994071_StiPosFileCreateRow) densoRecord;
                eigyoDt = rowData.getEigyoDt();
                tenpoCd = rowData.getTenpoCd();
                csvFilePath1 = tmpCsvFilePath + "\\" + batchDate + "\\" + tenpoCd;
                posClassMainte = "A" + getName(eigyoDt, tenpoCd);
                writeLog(Level.INFO_INT, "クラスメンテナンス データファイル（" + posClassMainte + "）作成処理を開始します。");
				iRec = createCSVFile4(posClassMainte, getIfPosClassMainteSelectSql(),csvFilePath1);
                writeLog(Level.INFO_INT, "クラスメンテナンス データファイルを" + iRec + "件作成しました。");
            }
            // クラスメンテナンス ファイル作成(E)

            // サブクラスメンテナンス ファイル作成(S)
            String  ifPosSubClassEigodtAndTenpocdSel = getIfPosSubClassEigodtAndTenpocdSelectSql();
            rsData = db.executeQuery(ifPosSubClassEigodtAndTenpocdSel);
            densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 営業日・店舗コード取得
                eigyoDt = rsData.getString("EIGYO_DT");
                tenpoCd = rsData.getString("TENPO_CD");
                MSTB994071_StiPosFileCreateRow rowData = new MSTB994071_StiPosFileCreateRow(eigyoDt, tenpoCd);
                densoRecordList.add(rowData);
            }
            for (Object densoRecord : densoRecordList) {
            	MSTB994071_StiPosFileCreateRow rowData = (MSTB994071_StiPosFileCreateRow) densoRecord;
                eigyoDt = rowData.getEigyoDt();
                tenpoCd = rowData.getTenpoCd();
                csvFilePath1 = tmpCsvFilePath + "\\" + batchDate + "\\" + tenpoCd;
                posSubClassMainte = "C" + getName(eigyoDt, tenpoCd);
                writeLog(Level.INFO_INT, "サブクラスメンテナンスデータファイル（" + posSubClassMainte + "）作成処理を開始します。");
				iRec = createCSVFile3(posSubClassMainte, getIfPosSubClassMainteSelectSql(),csvFilePath1);
                writeLog(Level.INFO_INT, "サブクラスメンテナンス データファイルを" + iRec + "件作成しました。");

            }
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
     * @return name
     */
    public String getName(String eigyoDt, String tenpoCd){
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

        // #3732 Mod X.Liu 2017.01.31 (S)
//        name = eigyoDt.substring(3, 4) + M + eigyoDt.substring(6, 8) + "01" + tenpoCd.substring(2, 3) + "." +  tenpoCd.substring(3, 6) + "_noncompleted";
        name = eigyoDt.substring(3, 4) + M + eigyoDt.substring(6, 8) + "01" + tenpoCd.substring(2, 3) + "." +  tenpoCd.substring(3, 6) ;
        // #3732 Mod X.Liu 2017.01.31 (E)
        return name;
    }

    /**
     * CSV1ファイルを作成します。
     *
     * @param fileNa データファイル名
     * @param selSql メンテナンスレコード取得SQL
     * @throws Exception
     */
    public int createCSVFile1(String fileNa, String selSql, String csvFilePath) throws Exception {

        ResultSet rs = null;
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
                // 2017.01.25 M.Akagi #3739 (S)
                // ディレクトリが見つからなければ
                //writeLog(Level.INFO_INT, csvFilePath + " が存在しません。");
                //throw new Exception();
                file.mkdirs();
                // 2017.01.25 M.Akagi #3739 (E)
            }

            // データ取得
            rs = db.executeQuery(selSql);
            while (rs.next()) {
                if( (fw == null) && (bw == null) ){
                    // ファイルオープン
                    bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
                }
                if("I".equals(fileNa.substring(0,1))){
                    sb.append(this.createCsvRowData(rs, false));
                    String oldSyohinCd = rs.getString("OLD_SYOHIN_CD");
                    if(oldSyohinCd != null && !"".equals(oldSyohinCd) && !spaces(13).equals(oldSyohinCd)){
                        sb.append(this.createCsvRowData(rs, true));
                    }
                    bw.write(sb.toString());
                }else{
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
            if (fw != null) {
                fw.close();
            }
        }
        return iRec;
    }

    /**
     * CSV2ファイルを作成します。
     *
     * @param fileNa データファイル名
     * @param selSql メンテナンスレコード取得SQL
     * @throws Exception
     */
    public int createCSVFile2(String fileNa, String selSql, String csvFilePath) throws Exception {

        ResultSet rs = null;
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
                // 2017.01.25 M.Akagi #3739 (S)
                // ディレクトリが見つからなければ
                //writeLog(Level.INFO_INT, csvFilePath + " が存在しません。");
                //throw new Exception();
                file.mkdirs();
                // 2017.01.25 M.Akagi #3739 (E)
            }

            // データ取得
            rs = db.executeQuery(selSql);
            while (rs.next()) {
                if( (fw == null) && (bw == null) ){
                    // ファイルオープン
                    bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
                }
                if("I".equals(fileNa.substring(0,1))){
                    sb.append(this.createCsvRowData(rs, false));
                    String oldSyohinCd = rs.getString("OLD_SYOHIN_CD");
                    if(oldSyohinCd != null && !"".equals(oldSyohinCd) && !spaces(13).equals(oldSyohinCd)){
                        sb.append(this.createCsvRowData(rs, true));
                    }
                    bw.write(sb.toString());
                }else{
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
            if (fw != null) {
                fw.close();
            }
        }
        return iRec;
    }

    /**
     * CSV3ファイルを作成します。
     *
     * @param fileNa データファイル名
     * @param selSql メンテナンスレコード取得SQL
     * @throws Exception
     */
    public int createCSVFile3(String fileNa, String selSql, String csvFilePath) throws Exception {

        ResultSet rs = null;
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
                // 2017.01.25 M.Akagi #3739 (S)
                // ディレクトリが見つからなければ
                //writeLog(Level.INFO_INT, csvFilePath + " が存在しません。");
                //throw new Exception();
                file.mkdirs();
                // 2017.01.25 M.Akagi #3739 (E)
            }

            // データ取得
            rs = db.executeQuery(selSql);
            while (rs.next()) {
                if( (fw == null) && (bw == null) ){
                    // ファイルオープン
                    bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
                }
                if("I".equals(fileNa.substring(0,1))){
                    sb.append(this.createCsvRowData(rs, false));
                    String oldSyohinCd = rs.getString("OLD_SYOHIN_CD");
                    if(oldSyohinCd != null && !"".equals(oldSyohinCd) && !spaces(13).equals(oldSyohinCd)){
                        sb.append(this.createCsvRowData(rs, true));
                    }
                    bw.write(sb.toString());
                }else{
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
            if (fw != null) {
                fw.close();
            }
        }
        return iRec;
    }

    /**
     * CSV4ファイルを作成します。
     *
     * @param fileNa データファイル名
     * @param selSql メンテナンスレコード取得SQL
     * @throws Exception
     */
    public int createCSVFile4(String fileNa, String selSql, String csvFilePath) throws Exception {
        ResultSet rs = null;
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
                // 2017.01.25 M.Akagi #3739 (S)
                // ディレクトリが見つからなければ
                //writeLog(Level.INFO_INT, csvFilePath + " が存在しません。");
                //throw new Exception();
                file.mkdirs();
                // 2017.01.25 M.Akagi #3739 (E)
            }

            // データ取得
            rs = db.executeQuery(selSql);
            while (rs.next()) {
                if( (fw == null) && (bw == null) ){
                    // ファイルオープン
                    bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
                }
                if("I".equals(fileNa.substring(0,1))){
                    sb.append(this.createCsvRowData(rs, false));
                    String oldSyohinCd = rs.getString("OLD_SYOHIN_CD");
                    if(oldSyohinCd != null && !"".equals(oldSyohinCd) && !spaces(13).equals(oldSyohinCd)){
                        sb.append(this.createCsvRowData(rs, true));
                    }
                    bw.write(sb.toString());
                }else{
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
            if (fw != null) {
                fw.close();
            }
        }
        return iRec;
    }

    /**
     * CSV5ファイルを作成します。
     *
     * @param fileNa データファイル名
     * @param selSql メンテナンスレコード取得SQL
     * @throws Exception
     */
    public int createCSVFile5(String fileNa, String selSql,String csvFilePath) throws Exception {

        ResultSet rs = null;
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
                // 2017.01.25 M.Akagi #3739 (S)
                // ディレクトリが見つからなければ
                //writeLog(Level.INFO_INT, csvFilePath + " が存在しません。");
                //throw new Exception();
                file.mkdirs();
                // 2017.01.25 M.Akagi #3739 (E)
            }

            // データ取得
            rs = db.executeQuery(selSql);
            while (rs.next()) {
                if( (fw == null) && (bw == null) ){
                    // ファイルオープン
                    bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
                }
                if("I".equals(fileNa.substring(0,1))){
                    sb.append(this.createCsvRowData(rs, false));
                    String oldSyohinCd = rs.getString("OLD_SYOHIN_CD");
                    if(oldSyohinCd != null && !"".equals(oldSyohinCd) && !spaces(13).equals(oldSyohinCd)){
                        sb.append(this.createCsvRowData(rs, true));
                    }
                    bw.write(sb.toString());
                }else{
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
            if (fw != null) {
                fw.close();
            }
        }
        return iRec;
    }

    /**
     * CSV6ファイルを作成します。
     *
     * @param fileNa データファイル名
     * @param selSql メンテナンスレコード取得SQL
     * @throws Exception
     */
    public int createCSVFile6(String fileNa, String selSql, String csvFilePath) throws Exception {

        ResultSet rs = null;
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
                // 2017.01.25 M.Akagi #3739 (S)
                // ディレクトリが見つからなければ
                //writeLog(Level.INFO_INT, csvFilePath + " が存在しません。");
                //throw new Exception();
                file.mkdirs();
                // 2017.01.25 M.Akagi #3739 (E)
            }

            // データ取得
            rs = db.executeQuery(selSql);
            while (rs.next()) {
                if( (fw == null) && (bw == null) ){
                    // ファイルオープン
                    bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
                }
                if("I".equals(fileNa.substring(0,1))){
                    sb.append(this.createCsvRowData(rs, false));
                    String oldSyohinCd = rs.getString("OLD_SYOHIN_CD");
                    if(oldSyohinCd != null && !"".equals(oldSyohinCd) && !spaces(13).equals(oldSyohinCd)){
                        sb.append(this.createCsvRowData(rs, true));
                    }
                    bw.write(sb.toString());
                }else{
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
            if (fw != null) {
                fw.close();
            }
        }
        return iRec;
    }

    /**
     * CSV7ファイルを作成します。
     *
     * @param fileNa データファイル名
     * @param selSql メンテナンスレコード取得SQL
     * @throws Exception
     */
    public int createCSVFile7(String fileNa, String selSql, String csvFilePath) throws Exception {

        ResultSet rs = null;
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
                // 2017.01.25 M.Akagi #3739 (S)
                // ディレクトリが見つからなければ
                //writeLog(Level.INFO_INT, csvFilePath + " が存在しません。");
                //throw new Exception();
                file.mkdirs();
                // 2017.01.25 M.Akagi #3739 (E)
            }

            // データ取得
            rs = db.executeQuery(selSql);
            while (rs.next()) {
                if( (fw == null) && (bw == null) ){
                    // ファイルオープン
                    bw = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(fileName), "UTF8"));
                }
                if("I".equals(fileNa.substring(0,1))){
                    sb.append(this.createCsvRowData(rs, false));
                    String oldSyohinCd = rs.getString("OLD_SYOHIN_CD");
                    if(oldSyohinCd != null && !"".equals(oldSyohinCd) && !spaces(13).equals(oldSyohinCd)){
                        sb.append(this.createCsvRowData(rs, true));
                    }
                    bw.write(sb.toString());
                }else{
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
            if (fw != null) {
                fw.close();
            }
        }
        return iRec;
    }

    /**
     * @param spaces
     * @return String
     * @author Dai.BQ
     */
    private String spaces(int spaces){
        return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
    }


    /**
     * システムコントロール情報取得
     * @param  なし
     * @throws Exception 例外
     */
    private void getSystemControl() throws Exception {

        // CSVファイルパス取得
    	csvFilePath1 = ResorceUtil.getInstance().getPropertie("FIVI_IFDIR_SITEI_POS");

        if(csvFilePath1 == null || csvFilePath1.trim().length() == 0){
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
     * IF_PLU_TANPINからEIGYO_DT、TENPO_CDを取得するSQLを取得する
     *
     * @return IF_PLU_TANPINからEIGYO_DT、TENPO_CDを取得SQL
     */
    private String getIfPluTanpinEigodtAndTenpocdSelectSql() throws SQLException {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append(" FROM ");
        sql.append("	IF_STI_PLU_TANPIN ");
        sql.append("GROUP BY ");
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
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
        sql.append("SELECT ");
        sql.append("	 IPT.TOROKU_ID ");
        sql.append("	,LPAD(NVL(TRIM(IPT.SYOHIN_CD), '0'), 13, '0') SYOHIN_CD ");
        sql.append("	,LPAD(TRIM(IPT.OLD_SYOHIN_CD), 13, '0') OLD_SYOHIN_CD ");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_RN, ' '), 30), 0, 15) SYOHIN_RN ");
        // #6167 MSTB994 Mod 2020.07.13 KHAI.NN (S)
//        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA, ' '), 60), 0, 30) SYOHIN_NA ");
        // #6227 MSTB994 Mod 2020.09.22 KHAI.NN (S)
        //sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA, ' '), 240), 0, 120) SYOHIN_NA ");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA, ' '), 60), 0, 30) SYOHIN_NA ");
        // #6227 MSTB994 Mod 2020.09.22 KHAI.NN (E)
        // #6167 MSTB994 Mod 2020.07.13 KHAI.NN (E)
        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_RN_CHN,' '), 80), 0, 25) SYOHIN_RN_CHN");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.SYOHIN_NA_CHN, ' '), 120), 0, 40) SYOHIN_NA_CHN ");
        sql.append("	,LPAD(TRIM(TO_CHAR(NVL(TRIM(IPT.BAITANKA_VL), '0'), '99999999999999.99')), 17, '0') BAITANKA_VL ");
        sql.append("	,RPAD(NVL(IPT.CLASS_CD, ' '), 6) CLASS_CD ");
        sql.append("	,RPAD(NVL(IPT.SUBCLASS_CD, ' '), 9) SUBCLASS_CD ");
        sql.append("	,IPT.TEIKAN_FG ");
        sql.append("	,IPT.PLU_FG ");
        sql.append(" ,CASE IPT.ZEI_KB ");
        sql.append(" WHEN '3' THEN '   ' ");
        sql.append(" ELSE LPAD(NVL(TRIM(IPT.ZEI_RT), '0'), 3, '0') ");
        sql.append(" END AS ZEI_RT  ");
        sql.append("	,RPAD(NVL(IPT.SEASON_ID, ' '), 6) SEASON_ID ");
        sql.append("	,LPAD(NVL(TRIM(TO_CHAR(IPT.SYOHI_KIGEN_DT)), '   '), 3, '0') SYOHI_KIGEN_DT ");
        sql.append("	,SUBSTR(IPT.INJI_HANBAI_TN, 0, 8)");
        sql.append("	,IPT.SIIRESAKI_CD ");
        sql.append("	,NVL(IPT.SYOHI_KIGEN_HYOJI_PATTER, ' ') ");
        sql.append("	,RPAD(NVL(IPT.LABEL_SEIBUN, ' '), 20) ");
        sql.append("	,SUBSTR(RPAD(NVL(IPT.LABEL_HOKAN_HOHO, ' '), 30), 0, 15) ");
        sql.append("	,RPAD(NVL(IPT.LABEL_TUKAIKATA, ' '), 15) ");
        sql.append("	,RPAD(NVL(IPT.LABEL_COUNTRY_NA, ' '), 25) ");
        // #3765 MSTB994 2017.02.09 S.Takayama (S)
        sql.append("	,RPAD(NVL(IPT.INJI_HANBAI_TN_EN, ' '), 8) ");
        // #3765 MSTB994 2017.02.09 S.Takayama (E)
        // #6238 MSTB994 Add 2020.09.30 KHAI.NN (S)
        sql.append("	,SUBSTR(RPAD(NVL(IPT.ITEM_OFFICIAL_NA, ' '), 240), 0, 120) ITEM_OFFICIAL_NA ");
        // #6238 MSTB994 Add 2020.09.30 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("	,LPAD(NVL(TRIM(IPT.MAX_BUY_QT), '   '), 3, '0') MAX_BUY_QT ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("FROM ");
        sql.append("	IF_STI_PLU_TANPIN IPT ");
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
        return sql.toString();
    }

    /**
     * IF_POS_PAYMENTからEIGYO_DT、TENPO_CDを取得するSQLを取得する
     *
     * @return IF_POS_PAYMENTからEIGYO_DT、TENPO_CDを取得SQL
     */
    private String getIfPosPaymentEigodtAndTenpocdSelectSql() throws SQLException {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append(" FROM ");
        sql.append("	IF_STI_POS_PAYMENT ");
        sql.append("GROUP BY ");
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append("ORDER BY ");
        sql.append("	TENPO_CD ");

        return sql.toString();
    }

    /**
     * IF_POS_PAYMENTからデータを取得するSQLを取得する
     *
     * @return IF_POS_PAYMENTからデータを取得SQL
     */
    private String getIfPosPaymentMainteSelectSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	 IPP.TOROKU_ID ");
        sql.append("	,RPAD(NVL(IPP.SHIHARAI_SYUBETSU_SEQ, ' '), 4) SHIHARAI_SYUBETSU_SEQ ");
        sql.append("	,SUBSTR(RPAD(NVL(IPP.SHIHARAI_SYUBETSU_EN_NA, ' '), 60), 0, 20) SHIHARAI_SYUBETSU_EN_NA ");
        sql.append("	,SUBSTR(RPAD(NVL(IPP.SHIHARAI_SYUBETSU_VN_NA, ' '), 60), 0, 20) SHIHARAI_SYUBETSU_VN_NA ");
        sql.append("	,RPAD(NVL(IPP.POS_SEQ, ' '), 2) POS_SEQ ");
        sql.append("	,RPAD(NVL(IPP.OVER_TYPE, ' '), 1) OVER_TYPE ");
        sql.append("	,RPAD(NVL(IPP.NEED_AUTHORITY, ' '), 1) NEED_AUTHORITY ");
        sql.append("	,RPAD(NVL(IPP.NEED_EXPIRY, ' '), 1) NEED_EXPIRY ");
        sql.append("	,RPAD(NVL(IPP.CARD_NUMBER, ' '), 1) CARD_NUMBER ");
        sql.append("	,SUBSTR(RPAD(NVL(IPP.PROCESS_TYPE, ' '), 90), 0, 30) PROCESS_TYPE ");
        sql.append("	,RPAD(NVL(IPP.SHIHARAI_SYUBETSU_GROUP_SEQ, ' '), 4) SHIHARAI_SYUBETSU_GROUP_SEQ ");
        sql.append("	,RPAD(NVL(IPP.CARD_LENGTH, ' '), 2) CARD_LENGTH ");
		// 2017.02.16 T.Han #4004対応（S)
        //sql.append("	,RPAD(NVL(IPP.SHIHARAI_SYUBETSU_SUB_CD, ' '), 5) SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("	,RPAD(NVL(IPP.SHIHARAI_SYUBETSU_SUB_CD, ' '), 7) SHIHARAI_SYUBETSU_SUB_CD ");
		// 2017.02.16 T.Han #4004対応（E)
        sql.append("	,RPAD(NVL(IPP.DISPLAY_FG, ' '), 1) DISPLAY_FG ");
        sql.append("	,RPAD(NVL(IPP.VOID_FG, ' '), 1) VOID_FG ");
        sql.append("	,RPAD(NVL(IPP.RETURN_FG, ' '), 1) RETURN_FG ");
        sql.append("	,RPAD(NVL(IPP.OPEN_DRAWER_FG, ' '), 1) OPEN_DRAWER_FG ");
        sql.append("	,RPAD(NVL(IPP.EXTRA_RECEIPT, ' '), 1) EXTRA_RECEIPT ");
        sql.append("	,RPAD(NVL(IPP.MAXIMUM_RECEIPT, ' '), 1) MAXIMUM_RECEIPT ");
        sql.append("	,RPAD(NVL(IPP.YUKO_START_DT, ' '), 8) YUKO_START_DT ");
        sql.append("	,RPAD(NVL(IPP.YUKO_END_DT, ' '), 8) YUKO_END_DT ");
        sql.append("	,RPAD(NVL(IPP.JIKASYOHI_KB, ' '), 1) JIKASYOHI_KB ");
        sql.append("	,SUBSTR(RPAD(NVL(IPP.JIKASYOHI_RECEIPT_VN_NA, ' '), 120), 0, 40) JIKASYOHI_RECEIPT_VN_NA ");
        sql.append("FROM ");
        sql.append("	IF_STI_POS_PAYMENT IPP ");
        sql.append("WHERE ");
        sql.append("	IPP.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IPP.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	IPP.TOROKU_ID ");
        sql.append("	IPP.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IPP.SHIHARAI_SYUBETSU_SEQ ");
        return sql.toString();
    }

    /**
     * IF_POS_DISCOUNTからEIGYO_DT、TENPO_CDを取得するSQLを取得する
     *
     * @return IF_POS_DISCOUNTからEIGYO_DT、TENPO_CDを取得SQL
     */
    private String getIfPosDiscountEigodtAndTenpocdSelectSql() throws SQLException {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append(" FROM ");
        sql.append("	IF_STI_POS_DISCOUNT ");
        sql.append("GROUP BY ");
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append("ORDER BY ");
        sql.append("	TENPO_CD ");

        return sql.toString();
    }

    /**
     * IF_POS_DISCOUNTからデータを取得するSQLを取得する
     *
     * @return IF_POS_DISCOUNTからデータを取得SQL
     */
    private String getIfPosDiscountMainteSelectSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	 TRIM(IPD.TOROKU_ID) ");
        sql.append("	,RPAD(NVL(IPD.DISCOUNT_CD, ' '), 3) DISCOUNT_CD ");
        sql.append("	,RPAD(NVL(IPD.SUB_DISCOUNT_CD, ' '), 5) SUB_DISCOUNT_CD ");
        sql.append("	,SUBSTR(RPAD(NVL(IPD.DISCOUNT_NA, ' '), 60), 0, 20) DISCOUNT_NA ");
        sql.append("	,SUBSTR(RPAD(NVL(IPD.SUB_DISCOUNT_NA, ' '), 60), 0, 20) SUB_DISCOUNT_NA ");
        sql.append("	,RPAD(NVL(IPD.RECEIPT_QT, ' '), 1) RECEIPT_QT ");
        sql.append("	,RPAD(NVL(IPD.MAX_RECEIPT_QT, ' '), 1) MAX_RECEIPT_QT ");
        sql.append("	,LPAD(NVL(TRIM(IPD.NEBIKI_RITU_VL), '0'), 3, '0') NEBIKI_RITU_VL ");
        sql.append("	,RPAD(NVL(IPD.YUKO_START_DT, ' '), 8) YUKO_START_DT ");
        sql.append("	,RPAD(NVL(IPD.YUKO_END_DT, ' '), 8) YUKO_END_DT ");
        sql.append("	,LPAD(TRIM(TO_CHAR(NVL(TRIM(IPD.MAX_NEBIKI_GAKU_VL), '0'), '99999999999999.99')), 17, '0') MAX_NEBIKI_GAKU_VL ");
        sql.append("	,RPAD(NVL(IPD.CARD_KB, ' '), 1) CARD_KB ");
        // 2017.04.26 T.Han #4824対応（S)
        // 2017.05.19 M.Son #5044対応（S)
        //sql.append("	,RPAD(NVL(IPD.SHIHARAI_JOKEN_CD, ' '), 7) SHIHARAI_JOKEN_CD ");
        sql.append("	,RPAD(NVL(IPD.SHIHARAI_JOKEN_CD, ' '), 4) SHIHARAI_JOKEN_CD ");
        // 2017.05.19 M.Son #5044対応（E)
        // 2017.04.26 T.Han #4824対応（E)
        sql.append("FROM ");
        sql.append("	IF_STI_POS_DISCOUNT IPD ");
        sql.append("WHERE ");
        sql.append("	IPD.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IPD.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	IPD.TOROKU_ID ");
        sql.append("	IPD.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IPD.DISCOUNT_CD ");
        return sql.toString();
    }

    /**
     * IF_POS_BUMONからEIGYO_DT、TENPO_CDを取得するSQLを取得する
     *
     * @return IF_POS_BUMONからEIGYO_DT、TENPO_CDを取得SQL
     */
    private String getIfPosBumonEigodtAndTenpocdSelectSql() throws SQLException {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append(" FROM ");
        sql.append("	IF_STI_POS_BUMON ");
        sql.append("GROUP BY ");
        sql.append("	EIGYO_DT ");
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
        sql.append("SELECT ");
        sql.append("	IPB.TOROKU_ID ");
        sql.append("	,RPAD(IPB.DIVISION_CD, 3) DIVISION_CD ");
        sql.append("	,SUBSTR(RPAD(NVL(IPB.DIVISION_NA, ' '), 60), 0, 30) DIVISION_NA ");
        sql.append("	,RPAD(NVL(IPB.PRIME_GROUP, ' '), 3) PRIME_GROUP ");
        sql.append("FROM ");
        sql.append("	IF_STI_POS_BUMON IPB ");
        sql.append("WHERE ");
        sql.append("	IPB.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IPB.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	IPB.TOROKU_ID ");
        sql.append("	IPB.TOROKU_ID DESC");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IPB.DIVISION_CD ");
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
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append(" FROM ");
        sql.append("	IF_STI_POS_DPT ");
        sql.append("GROUP BY ");
        sql.append("	EIGYO_DT ");
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
        sql.append("SELECT ");
        sql.append("	IPD.TOROKU_ID  ");
        sql.append("	,RPAD(IPD.DIVISION_CD, 3) DIVISION_CD ");
        sql.append("	,RPAD(IPD.DEPARTMENT_CD, 3) DEPARTMENT_CD ");
        sql.append("	,SUBSTR(RPAD(NVL(IPD.DEPARTMENT_NA, ' '), 60), 0, 30) DEPARTMENT_NA ");
        sql.append("FROM ");
        sql.append("	IF_STI_POS_DPT IPD ");
        sql.append("WHERE");
        sql.append("	IPD.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IPD.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	IPD.TOROKU_ID  ");
        sql.append("	IPD.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IPD.DIVISION_CD ");
        sql.append("	,IPD.DEPARTMENT_CD ");
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
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append(" FROM ");
        sql.append("	IF_STI_POS_CLASS_FIVI ");
        sql.append("GROUP BY ");
        sql.append("	EIGYO_DT ");
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
        sql.append("SELECT ");
        sql.append("	IPCV.TOROKU_ID ");
        sql.append("	,RPAD(IPCV.DEPARTMENT_CD, 3) DEPARTMENT_CD ");
        sql.append("	,RPAD(IPCV.CLASS_CD, 6) CLASS_CD ");
        sql.append("	,SUBSTR(RPAD(NVL(IPCV.CLASS_NA, ' '), 80), 0, 40) CLASS_NA ");
        sql.append("	,IPCV.DEPARTMENT_TYPE ");
        sql.append("FROM ");
        sql.append("	IF_STI_POS_CLASS_FIVI IPCV ");
        sql.append("WHERE");
        sql.append("	IPCV.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IPCV.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	IPCV.TOROKU_ID ");
        sql.append("	IPCV.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IPCV.DEPARTMENT_CD ");
        sql.append("	,IPCV.CLASS_CD ");
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
        sql.append("	EIGYO_DT ");
        sql.append("	,TENPO_CD ");
        sql.append(" FROM ");
        sql.append("	IF_STI_POS_SUBCLASS ");
        sql.append("GROUP BY ");
        sql.append("	EIGYO_DT ");
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
        sql.append("SELECT ");
        sql.append("	IPSC.TOROKU_ID ");
        sql.append("	,RPAD(IPSC.CLASS_CD, 6) ");
        sql.append("	,RPAD(IPSC.SUBCLASS_CD, 9) ");
        sql.append("	,SUBSTR(RPAD(NVL(IPSC.SUBCLASS_NA, ' '), 60), 0, 30) SUBCLASS_NA ");
        sql.append("	,RPAD(NVL(IPSC.AEON_CARD_NEBIKI_FG, ' '), 1) AEON_CARD_NEBIKI_FG ");
        sql.append("	,IPSC.CHG_VL_NEBIKI_BTN_FG ");
        sql.append("	,IPSC.KAIIN_CARD_NEBIKI_RT ");
        sql.append("	,IPSC.MATERNITY_CARD_NEBIKI_RT ");
        sql.append("FROM ");
        sql.append("	IF_STI_POS_SUBCLASS IPSC ");
        sql.append("WHERE");
        sql.append("	IPSC.EIGYO_DT = '" + eigyoDt + "' ");
        sql.append("	AND ");
        sql.append("	IPSC.TENPO_CD = '" + tenpoCd + "' ");
        sql.append("ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	IPSC.TOROKU_ID ");
        sql.append("	IPSC.TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	,IPSC.CLASS_CD ");
        sql.append("	,IPSC.SUBCLASS_CD ");
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
     * <p>タイトル: MSTB992071_PosFileCreateRow クラス</p>
     * <p>説明　: 伝送ヘッダーデータを保持する</p>
     *
     */
    class MSTB994071_StiPosFileCreateRow {

        /** 営業日 */
        private String eigyoDt;
        /** 店舗コード */
        private String tenpoCd;

        /**
         * MSTB992071_PosFileCreateRow を生成
         *
         * @param eigyoDt 営業日
         * @param tenpoCd 店舗コード
         */
        public MSTB994071_StiPosFileCreateRow(String eigyoDt, String tenpoCd) {
            this.eigyoDt = eigyoDt;
            this.tenpoCd = tenpoCd;
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


    }

}
