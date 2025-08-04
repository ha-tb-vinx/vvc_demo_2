package mdware.master.batch.process.MSTB908;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;
/**
*
* <p>タイトル: MSTB908050_CreateBlkFile.java クラス</p>
* <p>説明　: Blynk向け連携ファイルを、IF_BLKグループマスタ、IF_BLK部門マスタ、IF_BLKデプトマスタ、IF_BLKラインマスタ、IF_BLKクラスマスタ、IF_BLK店舗マスタ、
* <br>IF_BLK商品マスタ、IF_BLK支払種別マスタ、IF_BLK特売種別マスタ、IF_BLK仕入先マスタのデータを元に作成する。</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 (2015.08.07) TAM.NM FIVImart様対応
* @version 1.01 (2015.10.14) DAI.BQ FIVImart様対応
* @version 1.02 (2016.01.08) DUNG.NQ FIVImart様対応
* @version 1.03 (2016.04.29) M.Kanno FIVImart様対応 出力先フォルダ変更
* @Version 1.04 (2016.05.18) to #1325,1328対応
* @Version 1.05 (2016.05.23) to #1325対応
* @version 1.06 (2016.09.13) VINX H.sakamoto #1748対応
* @version 1.07 (2016.10.13) M.Akagi #2277対応
* @version 1.08 (2016.10.18) Li.Sheng #2238
* @version 1.09 (2016.10.18) Li.Sheng #3233
* @version 1.10 (2016.12.22) T.Han #3212 FIVIMART対応
* @version 1.11 (2017.01.12) T.Han #3583 FIVIMART対応
* @version 1.12 (2017.02.17) T.Han #4004 FIVIMART対応
* @version 1.13 (2017.04.24) S.Nakazato #4824
* @version 1.14 (2017.05.17) M.Son #5044
* @version 1.15 (2023.12.01) TUNG.LT #20077 MKH対応
*/
public class MSTB908050_CreateBlkFile {

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
    /** デプトマスタデータファイル名 */
    private String csvFileDpt = null;
    /** グループマスタデータファイル名 */
    private String csvFileGroup = null;
    /** 部門マスタデータファイル名 */
    private String csvFileBumon = null;
    /** ラインマスタデータファイル名 */
    private String csvFileLine  = null;
    /** クラスマスタデータファイル名 */
    private String csvFileClass  = null;
    /** 店舗マスタデータファイル名 */
    private String csvFileTenpo  = null;
    /** 商品マスタデータファイル名 */
    private String csvFileSyohin = null;
    //2016/9/13 VINX h.sakamoto #1748対応 (S)
    /** 支払種別マスタデータファイル名 */
    private String csvFilePayment = null;
    /** 特売種別マスタデータファイル名 */
    private String csvFileDiscount = null;
    //2016/9/13 VINX h.sakamoto #1748対応 (E)
    //2016.10.13 M.Akagi #2277 (S)
    /** 仕入先マスタデータファイル名 */
    private String csvFileShiiresaki = null;
    //2016.10.13 M.Akagi #2277 (E)
    /** システム時刻 */
    private String timeStamp = "";
    /** 作成日 */
    private String sakuseiDt = "";
    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
    private String jobId =userLog.getJobId();

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB908050_CreateBlkFile(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB908050_CreateBlkFile() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }

    /**
     * 本処理
     * @throws Exception
     */
    public void execute() throws Exception {
        userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
        try {

            //バッチ処理件数をカウント（ログ出力用）
            int iRec = 0;

            // トランザクションログ有無（AutoCommitモード）
            // （trueを指定すると、トランザクションログ出力をしない分の速度向上
            // 　が見込めますが、コミット・ロールバックが無効となります。）
            db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");
            // 作成日取得
            // #2238 Add 2016.10.18 Li.Sheng (S)
            //timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
            //sakuseiDt = timeStamp.substring(0, 4) + "" + timeStamp.substring(4, 6) + "" + timeStamp.substring(6, 8);
            sakuseiDt = RMSTDATEUtil.getBatDateDt();
            sakuseiDt = DateChanger.addDate(sakuseiDt, 1);
    		// #2238 Add 2016.10.18 Li.Sheng (E)
            // システムコントロール項目取得
            getSystemControl();

            writeLog(Level.INFO_INT, "出力先ディレクトリ：" + csvFilePath);

            // グループマスタデータファイル作成
            writeLog(Level.INFO_INT, "グループマスタデータファイル（" + csvFileGroup + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileGroup, getIfBlkGroupSelectSql());
            writeLog(Level.INFO_INT, "グループマスタ（DWH）データファイルを" + iRec + "件作成しました。");

            // 部門マスタデータファイル作成
            writeLog(Level.INFO_INT, "部門マスタデータファイル（" + csvFileBumon + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileBumon, getIfBlkBumonSelectSql());
            writeLog(Level.INFO_INT, "部門マスタデータファイルを" + iRec + "件作成しました。");

            // デプトマスタデータファイル作成
            writeLog(Level.INFO_INT, "デプトマスタデータファイル（" + csvFileDpt + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileDpt, getIfBlkDptSelectSql());
            writeLog(Level.INFO_INT, "デプトマスタデータファイルを" + iRec + "件作成しました。");

            // ラインマスタデータファイル作成
            writeLog(Level.INFO_INT, "ラインマスタデータファイル（" + csvFileLine + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileLine, getIfBlkLineSelectSql());
            writeLog(Level.INFO_INT, "ラインマスタデータファイルを" + iRec + "件作成しました。");

            // クラスマスタデータファイル作成
            writeLog(Level.INFO_INT, "クラスマスタデータファイル（" + csvFileClass + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileClass, getIfBlkClassSelectSql());
            writeLog(Level.INFO_INT, "クラスマスタデータファイルを" + iRec + "件作成しました。");

            // 店舗マスタデータファイル作成
            writeLog(Level.INFO_INT, "店舗マスタデータファイル（" + csvFileTenpo + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileTenpo, getIfBlkTenpoSelectSql());
            writeLog(Level.INFO_INT, "店舗マスタデータファイルを" + iRec + "件作成しました。");

            // 商品マスタデータファイル作成
            writeLog(Level.INFO_INT, "商品マスタデータファイル（" + csvFileSyohin + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileSyohin, getIfBlkSyohinSelectSql());
            writeLog(Level.INFO_INT, "商品マスタデータファイルを" + iRec + "件作成しました。");

            //2016/9/13 VINX h.sakamoto #1748対応 (S)
            // 支払種別マスタデータファイル作成
            writeLog(Level.INFO_INT, "支払種別マスタデータファイル（" + csvFilePayment + "）作成処理を開始します。");
            iRec = createCSVFile(csvFilePayment, getIfBlkPaymentSelectSql());
            writeLog(Level.INFO_INT, "支払種別マスタデータファイルを" + iRec + "件作成しました。");

            // 特売種別マスタデータファイル作成
            writeLog(Level.INFO_INT, "支払種別マスタデータファイル（" + csvFileDiscount + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileDiscount, getIfBlkDiscountSelectSql());
            writeLog(Level.INFO_INT, "支払種別マスタデータファイルを" + iRec + "件作成しました。");
            //2016/9/13 VINX h.sakamoto #1748対応 (E)

            //2016.10.13 M.Akagi #2277 (S)
            writeLog(Level.INFO_INT, "仕入先マスタデータファイル（" + csvFileShiiresaki + "）作成処理を開始します。");
            iRec = createCSVFile(csvFileShiiresaki, getIfBlkShiiresakiSelectSql());
            writeLog(Level.INFO_INT, "仕入先マスタデータファイルを" + iRec + "件作成しました。");
            //2016.10.13 M.Akagi #2277 (E)

            writeLog(Level.INFO_INT, "稼働日判定処理を開始します。");

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
     * システムコントロール情報取得
     * @param  なし
     * @throws Exception 例外
     */
    private void getSystemControl() throws Exception {

        // CSVファイルパス取得
        //Mod 2015.10.14 DAI.BQ (S)
        //出力先フォルダ変更対応 2016.04.29 M.Kanno (S)
        //csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_BLK);
    	csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.FIVI_IFDIR_CRM);
        //出力先フォルダ変更対応 2016.04.29 M.Kanno (E)
        //Mod 2015.10.14 DAI.BQ (E)
        if(csvFilePath == null || csvFilePath.trim().length() == 0){
            this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
            throw new Exception();
        }

        //2016/9/13 VINX h.sakamoto #1748対応 (S)
        String createYear = sakuseiDt.substring(3,4);
        String createMonth = null;
        if(sakuseiDt.substring(4, 5).equals("0")){
            createMonth = sakuseiDt.substring(5, 6);
        }else{
            if(sakuseiDt.substring(5, 6).equals("0")){
                createMonth = "A";
            }else if(sakuseiDt.substring(5, 6).equals("1")){
                createMonth = "B";
            }else if(sakuseiDt.substring(5, 6).equals("2")){
                createMonth = "C";
            }
        }
        String createDay = sakuseiDt.substring(6,8);
        //2016/9/13 VINX h.sakamoto #1748対応 (E)

        // グループマスタデータファイル名取得
        csvFileGroup = "MBGRP" + sakuseiDt + "01";
        // 部門マスタデータファイル名取得
        csvFileBumon = "MBDIV" + sakuseiDt + "01";
        // デプトマスタデータファイル名取得
        csvFileDpt = "MBDPT" + sakuseiDt + "01";
        // ラインマスタデータファイル名取得
        csvFileLine = "MBLIN" + sakuseiDt + "01";
        // クラスマスタデータファイル名取得
        csvFileClass = "MBCLS" + sakuseiDt + "01";
        // 店舗マスタデータファイル名取得
        csvFileTenpo = "MBSTR" + sakuseiDt + "01";
        // 商品マスタデータファイル名取得
        csvFileSyohin = "MBSKU" + sakuseiDt + "01";
        //2016/9/13 VINX h.sakamoto #1748対応 (S)
        // 支払種別マスタデータファイル名取得
        csvFilePayment = "L" + createYear + createMonth + createDay + "01";
        // 特売種別マスタデータファイル名取得
        csvFileDiscount = "K" + createYear + createMonth + createDay + "01";
        //2016/9/13 VINX h.sakamoto #1748対応 (E)
        //2016.10.13 M.Akagi #2277 (S)
        csvFileShiiresaki = "MBSUP" + sakuseiDt.substring(0,4) + sakuseiDt.substring(4,6) + createDay + "01";
        //2016.10.13 M.Akagi #2277 (E)
    }

    /**
     * CSVファイルを作成します。
     * @param fileName		ファイル名
     * @param sqlStatement	検索SQL
     * @return 出力件数
     * @throws IOException
     * @throws SQLException
     * @throws Exception
     */
    private int createCSVFile(String fileName, String sqlStatement) throws IOException, SQLException, Exception {

        ResultSet		rs				= null;
        String			fileFullName	= null;
        File			file 			= null;
        /* No.224 MSTB908050 Mod 2016.01.08 DUNG.NQ UTF-8(S)*/
        //FileWriter		fw 				= null;
        FileOutputStream	fw 				= null;
        /* No.224 MSTB908050 Mod 2016.01.08 DUNG.NQ UTF-8(E)*/
        BufferedWriter	bw 				= null;
        StringBuffer	sb				= new StringBuffer();
        int				dataCnt			= 0;


        try{
            // CSVファイル格納パス、ファイル名
            file 	 = new File(csvFilePath);

            if( file.exists() == false ){
                // ディレクトリが見つからなければ
                this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
                throw new Exception();
            }

            fileFullName = file + "/" + fileName;


            // データ取得
            rs = db.executeQuery(sqlStatement);

            while (rs.next()) {

                if (fw == null) {
                    /* No.224 MSTB908050 Mod 2016.01.08 DUNG.NQ UTF-8(S)*/
                    // ファイルオープン
                    // 検索結果が０件でない場合のみ、ファイルを作成する
                    //fw = new FileWriter(fileFullName, false);
                    //bw = new BufferedWriter(fw);
                    //ファイル出力時の文字コードをUTF-8とする旨を追記
                    fw = new FileOutputStream(fileFullName);
                    bw = new BufferedWriter(new OutputStreamWriter(fw, "UTF8"));
                    /* No.224 MSTB908050 Mod 2016.01.08 DUNG.NQ UTF-8(E)*/
                }
                // 行データ作成
                sb.append(createCsvRowData(rs));

                // 行データ出力
                bw.write(sb.toString());
                sb.setLength(0);

                dataCnt++;

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

        return dataCnt;

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
     * IF_BLK_GROUPを取得するSQLを取得する
     *
     * @return IF_BLK_GROUP取得SQL
     */
    private String getIfBlkGroupSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	GROUP_CD ");
     // #1325,1328対応 2016.05.18 to Mod (S)
//        sql.append("	,GROUP_NA ");
        sql.append("	,RPAD(GROUP_NA,240,' ') ");
     // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append(" FROM ");
        sql.append("	IF_BLK_GROUP");
        sql.append(" ORDER BY ");
        sql.append("	GROUP_CD");

        return sql.toString();
    }

    /**
     * IF_BLK_BUMONを取得するSQLを取得する
     *
     * @return IF_BLK_BUMON取得SQL
     */
    private String getIfBlkBumonSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	BUMON_CD ");
     // #1325,1328対応 2016.05.18 to Mod (S)
//        sql.append("	,BUMON_NA ");
        sql.append("	,RPAD(BUMON_NA,240,' ') ");
     // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	,GROUP_CD ");
        sql.append(" FROM ");
        sql.append("	IF_BLK_BUMON");
        sql.append(" ORDER BY ");
        sql.append("	BUMON_CD");

        return sql.toString();
    }

    /**
     * IF_BLK_DPTを取得するSQLを取得する
     *
     * @return IF_BLK_DPT取得SQL
     */
    private String getIfBlkDptSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	DPT_CD ");
     // #1325,1328対応 2016.05.18 to Mod (S)
//        sql.append("	,DPT_NA ");
        sql.append("	,RPAD(DPT_NA,240,' ') ");
     // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	,BUMON_CD ");
        sql.append(" FROM ");
        sql.append("	IF_BLK_DPT");
        sql.append(" ORDER BY ");
        sql.append("	DPT_CD");

        return sql.toString();
    }

    /**
     * IF_BLK_LINEを取得するSQLを取得する
     *
     * @return IF_BLK_LINE取得SQL
     */
    private String getIfBlkLineSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	LINE_CD ");
     // #1325,1328対応 2016.05.18 to Mod (S)
//        sql.append("	,LINE_NA ");
        sql.append("	,RPAD(LINE_NA,240,' ') ");
     // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	,DPT_CD ");
        sql.append(" FROM ");
        sql.append("	IF_BLK_LINE");
        sql.append(" ORDER BY ");
        sql.append("	DPT_CD,");
        sql.append("	LINE_CD");

        return sql.toString();
    }

    /**
     * IF_BLK_CLASSを取得するSQLを取得する
     *
     * @return IF_BLK_CLASS取得SQL
     */
    private String getIfBlkClassSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	CLASS_CD ");
     // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	,RPAD(CLASS_NA,240,' ') ");
//        sql.append("	,CLASS_NA ");
     // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	,DPT_CD ");
        sql.append("	,LINE_CD ");
        sql.append(" FROM ");
        sql.append("	IF_BLK_CLASS");
        sql.append(" ORDER BY ");
        sql.append("	DPT_CD");
        sql.append("	,LINE_CD ");
        sql.append("	,CLASS_CD ");


        return sql.toString();
    }

    /**
     * IF_BLK_TENPOを取得するSQLを取得する
     *
     * @return IF_BLK_TENPO取得SQL
     */
    private String getIfBlkTenpoSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	TENPO_CD ");
     // #1325,1328対応 2016.05.18 to Mod (S)
//        sql.append("	,TENPO_NA ");
        sql.append("	,RPAD(TENPO_NA,240,' ') ");
     // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append("	,TENPO_KB ");
        sql.append("	,KAITEN_DT ");
        sql.append("	,HEITEN_DT ");
        sql.append(" FROM ");
        sql.append("	IF_BLK_TENPO");
        sql.append(" ORDER BY ");
        sql.append("	TENPO_CD");

        return sql.toString();
    }

    /**
     * IF_BLK_SYOHINを取得するSQLを取得する
     *
     * @return IF_BLK_SYOHIN取得SQL
     */
    private String getIfBlkSyohinSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	SYORI_KB ");
        sql.append("	,SYOHIN_CD ");
     // #1325,1328対応 2016.05.19 to Mod (S)
        sql.append("	,RPAD(SYOHIN_NA,240,' ' ) ");
     // #1325,1328対応 2016.05.19 to Mod (E)
        sql.append("	,DPT_CD ");
        sql.append("	,LINE_CD ");
        sql.append("	,CLASS_CD ");
        sql.append("	,SUPPLIER_CD ");
        // #1325,1328対応 2016.05.18 to Mod (S)
//        sql.append("	,SUPPLIER_NA ");
        sql.append("	,RPAD(SUPPLIER_NA,240,' ') ");
        sql.append("	,TEIKAN_FG ");
        // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append(" FROM ");
        sql.append("	IF_BLK_SYOHIN");
        sql.append(" ORDER BY ");
        sql.append("	SYOHIN_CD");
        return sql.toString();
    }

    //2016/9/13 VINX h.sakamoto #1748対応 (S)
    /**
     * IF_BLK_PAYMENTを取得するSQLを取得する
     *
     * @return IF_BLK_PAYMENT取得SQL
     */
    private String getIfBlkPaymentSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("	  RPAD(TOROKU_ID, 1, ' ')");
        sql.append("	  , RPAD(NVL(SHIHARAI_SYUBETSU_SEQ,' '), 4, ' ')");
        // 2016.12.22 T.Han #3212対応（S)
        //sql.append("	  , RPAD(NVL(SHIHARAI_SYUBETSU_EN_NA,' '), 20, ' ')");
        sql.append("	  , SUBSTR(RPAD(NVL(SHIHARAI_SYUBETSU_EN_NA, ' '), 40), 0, 20) ");
        //sql.append("	  , RPAD(NVL(SHIHARAI_SYUBETSU_VN_NA,' '), 20, ' ')");
        sql.append("      , SUBSTR(RPAD(NVL(SHIHARAI_SYUBETSU_VN_NA, ' '), 40), 0, 20) ");
        // 2016.12.22 T.Han #3212対応（E)
        sql.append("	  , RPAD(NVL(POS_SEQ,' '), 2, ' ')");
        sql.append("	  , RPAD(NVL(OVER_TYPE,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(NEED_AUTHORITY,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(NEED_EXPIRY,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(CARD_NUMBER,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(PROCESS_TYPE,' '), 30, ' ')");
        sql.append("	  , RPAD(NVL(SHIHARAI_SYUBETSU_GROUP_SEQ,' '), 4, ' ')");
        sql.append("	  , RPAD(NVL(CARD_LENGTH,' '), 2, ' ')");
        // 2017.02.17 T.Han #4004対応（S)
        //sql.append("	  , RPAD(NVL(SHIHARAI_SYUBETSU_SUB_CD,' '), 5, ' ')");
        sql.append("	  , RPAD(NVL(SHIHARAI_SYUBETSU_SUB_CD,' '), 7, ' ')");
        // 2017.02.17 T.Han #4004対応（E)
        sql.append("	  , RPAD(NVL(DISPLAY_FG,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(VOID_FG,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(RETURN_FG,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(OPEN_DRAWER_FG,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(EXTRA_RECEIPT,' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(MAXIMUM_RECEIPT,' '), 1, ' ')");
        sql.append("	  , YUKO_START_DT");
        sql.append("	  , YUKO_END_DT ");
    	// 2017.01.12 T.Han #3583対応（S)
        sql.append("	  , RPAD(NVL(JIKASYOHI_KB, ' '), 1) JIKASYOHI_KB ");
        sql.append("	  , SUBSTR(RPAD(NVL(JIKASYOHI_RECEIPT_VN_NA, ' '), 120), 0, 40) JIKASYOHI_RECEIPT_VN_NA ");
    	// 2017.01.12 T.Han #3583対応（E)
        sql.append(" FROM ");
        sql.append("     IF_BLK_PAYMENT ");
        sql.append(" ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	TOROKU_ID");
        sql.append("	TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	, SHIHARAI_SYUBETSU_SEQ");
        return sql.toString();
    }

    /**
     * IF_BLK_DISCOUNTを取得するSQLを取得する
     *
     * @return IF_BLK_DISCOUNT取得SQL
     */
    private String getIfBlkDiscountSelectSql() throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("	  TOROKU_ID");
        sql.append("	  , RPAD(DISCOUNT_CD, 3, ' ')");
        sql.append("	  , RPAD(NVL(SUB_DISCOUNT_CD, ' '), 5, ' ')");
        // 2016.12.22 T.Han #3212対応（S)
        //sql.append("	  , RPAD(NVL(DISCOUNT_NA, ' '), 20, ' ')");
        sql.append("	  , SUBSTR(RPAD(NVL(DISCOUNT_NA, ' '), 40), 0, 20) ");
        //sql.append("	  , RPAD(NVL(SUB_DISCOUNT_NA, ' '), 20, ' ')");
        sql.append("	  , SUBSTR(RPAD(NVL(SUB_DISCOUNT_NA, ' '), 40), 0, 20) ");
        // 2016.12.22 T.Han #3212対応（E)
        sql.append("	  , RPAD(NVL(RECEIPT_QT, ' '), 1, ' ')");
        sql.append("	  , RPAD(NVL(MAX_RECEIPT_QT, ' '), 1, ' ')");
        sql.append("	  , LPAD(NVL(TRIM(NEBIKI_RITU_VL), '0'), 3, '0')");
        sql.append("	  , YUKO_START_DT");
        sql.append("	  , YUKO_END_DT");
        sql.append("	  , LPAD( TRIM( TO_CHAR( NVL(MAX_NEBIKI_GAKU_VL, '0'), '99999999999999.99')) , 17 , '0') ");
// #3233 Add 2016.10.18 Li.Sheng (S)
        sql.append("	  , RPAD(NVL(CARD_KB, ' '), 1) AS CARD_KB ");
// #3233 Add 2016.10.18 Li.Sheng (E)
        // 2017.04.24 S.Nakazato #4824対応（S)
        // #5044 2017.05.17 M.Son (S)
        //sql.append("	  , RPAD(NVL(SHIHARAI_JOKEN_CD, ' '), 7, ' ')");
        sql.append("	  , RPAD(NVL(SHIHARAI_JOKEN_CD, ' '), 4, ' ')");
        // #5044 2017.05.17 M.Son (E)
        // 2017.04.24 S.Nakazato #4824対応（E)
        sql.append(" FROM ");
        sql.append("     IF_BLK_DISCOUNT ");
        sql.append(" ORDER BY ");
        // #20077 MOD 2023.12.01 TUNG.LT (S)
        // sql.append("	TOROKU_ID");
        sql.append("	TOROKU_ID DESC ");
        // #20077 MOD 2023.12.01 TUNG.LT (E)
        sql.append("	, DISCOUNT_CD");
        return sql.toString();
    }
    //2016/9/13 VINX h.sakamoto #1748対応 (E)

    //2016/9/13 M.Akgai #2277 (S)
    /**
     * IF_BLK_SHIIRESAKIを取得するSQLを取得する
     *
     * @return IF_BLK_SHIIRESAKI取得SQL
     */
    private String getIfBlkShiiresakiSelectSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("	 SHIIRESAKI_CD ");
        sql.append("	 , RPAD(SHIIRESAKI_KANJI_NA, 40, ' ') ");
        sql.append(" FROM ");
        sql.append("	 IF_BLK_SHIIRESAKI ");
        sql.append(" ORDER BY ");
        sql.append("	 SHIIRESAKI_CD ");

        return  sql.toString();
    }
    //2016/9/13 M.Akgai #2277 (E)

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


     // #1325 2016.05.23 to Mod (S)
//    	String val = "";
//        if( str != null ){
//            val = str.trim();
//        }

     // #1325 2016.05.23 to Mod (E)

        // セパレータの判定。最終項目の場合は改行する。
        if (endFg) {
            // #1325 2016.05.23 to Mod (S)
//            val += RETURN_CODE;
            // #1325 2016.05.23 to Mod (E)
            str += RETURN_CODE;
        }
        // #1325,1328対応 2016.05.18 to Mod (S)
//        else {
//            val += SPLIT_CODE;
//        }
        // #1325,1328対応 2016.05.18 to Mod (E)
     // #1325 2016.05.23 to Mod (S)
//        return val;
        return str;
     // #1325 2016.05.23 to Mod (E)
    }

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
