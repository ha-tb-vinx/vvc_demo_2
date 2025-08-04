/**
 * 
 */
package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.StringUtility;
import jp.co.vinculumjapan.mdware.common.util.Zip;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;


/**
 * <p>タイトル: TorikomiDaoSuper クラス</p>
 * <p>説明　: POS実績取込処理スーパークラス</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.13) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * @Version 3.02 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №005
 * @Version 3.03 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @version 3.04 (2014.02.24) S.Arakawa [結合0152] ファイルがない場合に、エラーにしない対応
 * @version 3.05 (2014.02.27) S.Arakawa [結合0152] ファイルがない場合に、エラーにしない対応 不要なコメントを削除
 * @version 3.06 (2014.02.28) Y.Tominaga[結合0152] ファイルがない場合に、エラーにしない対応 バックアップがうまく取れていなかったので修正
 * @version 3.07 (2015.08.28) NGUYEN.NTM FIVImart様対応
 * @Version 3.08 (2016.05.10) VINX S.Kashihara FIVI対応
 * @Version 3.09 (2016.05.12) VINX H.Yaguma FIVI対応
 * @Version 3.10 (2016.05.20) Hirata FIVImart様対応
 */
abstract class TorikomiDaoSuper implements DaoIf {

    // プロパティファイルより法人コード取得
    protected static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    protected static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** 単品精算取込ファイルヘッダー項目数 */
    protected static final int FILE_HEADER_LIST_LENGTH = 10;

    // システム日付を先に取得しておく
    private String sysTime = ResorceUtil.getInstance().getServerTime();

    /** バッチ処理名 */
    protected String daoName;
    /** 登録先テーブル名称 */
    protected String insTableName;
    /** 取込ファイル */
    protected String fileName;

    /** 追加SQL文用 */
    protected String insSql;
    /** 削除SQL文用 */
    protected String delSql;

    /** ユーザID */
    protected String userId;

    /** バックアップフォルダPID */
    protected String backUpDirPID;

    // Add 2015.08.28 NGUYEN.NTM (S)
    protected Map<String, String> syohinMapping = null;
    // Add 2015.08.28 NGUYEN.NTM (E)
    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 機能ID取得
        String jobId = getJobId();
        // バッチ処理名取得
        daoName = getDaoName();
        // 登録先テーブル名称取得
        insTableName = getInsTableName();
        // 取込ファイル名
        fileName = getFileName();
        // 追加SQL文取得
        insSql = getInsSql();
        // 削除SQL文用取得
        delSql = getDelSql();
        // バックアップフォルダ取得
        backUpDirPID = getBackUpDirPID();

        // ユーザID取得
        userId = invoker.getUserId();
        // Add 2015.08.28 NGUYEN.NTM (S)
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
        /*
        if("URIB012010".equals(jobId)){
            TanpinSeisanTorikomiDao tstDao = new TanpinSeisanTorikomiDao();
            syohinMapping = tstDao.setSyohinMapping(invoker);
        */
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016.05.12 VINX H.Yaguma FIVI対応 (S)
        // 部門精算
//        } else if("URIB012020".equals(jobId)){
//            BumonSeisanTorikomiDao bstDao = new BumonSeisanTorikomiDao();
//            syohinMapping = bstDao.setSyohinMapping(invoker);
//        }
        // 2016.05.12 VINX H.Yaguma FIVI対応 (E)
        // Add 2015.08.28 NGUYEN.NTM (E)
        // ディレクトリパス取得
        String dirPath = FiResorceUtility.getPropertie(UriResorceKeyConstant.DATA_DIR_PATH);

        seitoPosTorikomi(invoker, jobId, dirPath, fileName);

        // 取込処理が正常終了している場合、バックアップを作成し取込ファイルを削除する
        backUpAndDeleteFile(invoker, dirPath);

    }

    /**
     * CSV取込処理を行う
     * @param invokerIf データベースアクセスオブジェクトの為のinvoker
     * @param jobId ジョブID
     * @param dirPath ディレクトリパス
     * @param mainFileId ファイル名
     * @throws DaoException Dao例外
     * @throws Exception 例外
     */
    private void csvTorikomi(DaoInvokerIf invoker, String jobId, String dirPath, String mainFileId) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + daoName;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + daoName + "を開始します。");

        // ディレクトリを取得する
        File directory = new File(dirPath);
        if (!directory.isDirectory()) {
            invoker.errorLog("ディレクトリを正しく指定して下さい。");
            throw new IllegalStateException("ディレクトリを正しく指定して下さい。");
        }

        // ファイルをリスト化
        File readFile = new File(directory + "/" + fileName);
        if (!readFile.exists()) {
            invoker.errorLog(dirPath + "にファイルが存在しません");
        } else {

            BufferedReader file = null;

            int insertCount = 0;

            // オブジェクトを初期化する
            PreparedStatementEx preparedStatementExIns = null;
            PreparedStatementEx preparedStatementDelete = null;

            try {

                // SQLを取得し、TMPテーブルを削除する
                preparedStatementDelete = invoker.getDataBase().prepareStatement(delSql);
                preparedStatementDelete.execute();

                // SQLを取得し、パラメータを条件にバインドする
                preparedStatementExIns = invoker.getDataBase().prepareStatement(insSql);

                // ファイル読み込み
                file = new BufferedReader(new FileReader(readFile));
                // CSVの取得用
                String str = null;
                String[] csvlist = null;
                String fileId = null;
                String tenpoCd = null;
                String eigyoDt = null;

                String batchDt = FiResorceUtility.getDBServerTime();

                // CSVファイルを1行ずつ読み込む
                while ((str = file.readLine()) != null) {
                    // カンマ区切りで読み込む
                    csvlist = str.split(UriageCommonConstants.COMMA);
                    // 空白行の場合は次のレコードへスキップ
                    if (csvlist.length == 1) {
                        continue;
                    }

                    // ヘッダー行かどうか
                    if (csvlist.length == FILE_HEADER_LIST_LENGTH && StringUtility.cutString(mainFileId, 0, 8).equals(StringUtility.cutString(csvlist[1].trim(), 0, 8))) {
                        // 行の２項目目の値を取得
                        fileId = StringUtility.cutString(csvlist[1].trim(), 0, 8);

                        if (StringUtility.cutString(mainFileId, 0, 8).equals(fileId)) {

                            // ヘッダーから営業日、店舗コードを取得
                            eigyoDt = csvlist[3].trim();
                            tenpoCd = csvlist[4].trim();

                        } else {
                            // 次のファイルへスキップ
                            break;
                        }
                    }

                    // 明細行の場合
                    else if (isDetailRecord(csvlist.length)) {

                        int count = insertData(preparedStatementExIns, csvlist, tenpoCd, eigyoDt, batchDt);
                        insertCount = insertCount + count;
                    }

                    // ヘッダー行でも明細行でもないものはエラー
                    else {
                        throw new DaoException("ファイル内容がフォーマットに則っていません。");
                    }
                }
                // 初期化
                csvlist = null;
                file.close();

                // ログ出力
                invoker.infoLog(strUserId + "　：　" + insTableName + "へのデータ取込処理を終了します。取込件数は" + insertCount + "件です。");

            } catch (Exception e) {
                invoker.errorLog(e.toString());
                throw e;
            } finally {
                try {
                    if (file != null) {
                        file.close();
                    }
                } catch (Exception e) {
                    invoker.infoLog("FILE Closeエラー");
                }
                try {
                    if (preparedStatementExIns != null) {
                        preparedStatementExIns.close();
                    }

                    if (preparedStatementDelete != null) {
                        preparedStatementDelete.close();
                    }

                } catch (Exception e) {
                    invoker.infoLog("preparedStatement Closeエラー");
                }
            }

        }

        invoker.infoLog(strUserId + "　：　" + daoName + "を終了します。");
    }

    /**
     * ファイルバックアップ処理・ファイル削除処理
     * バックアップを行うファイル名をzip圧縮で保存する。zip名にはシステム日付を付ける。
     * 取込ファイルの削除を行う
     * 
     * @param invoker
     * @param dirPath
     * @throws DaoException
     */
    private void backUpAndDeleteFile(DaoInvokerIf invoker, String dirPath) throws DaoException {
        // 開始ログ出力
        invoker.infoLog(invoker.getUserId() + "： ファイルバックアップ処理を開始します。");

        // バックアップファイル名を指定　リネーム後のファイル名
        String backupFileName = fileBackUpName(fileName);

        // バックアップディレクトリパス
        String backupFileDirPath = FiResorceUtility.getPropertie(backUpDirPID);

        // バックアップファイルのフルパス
        String backupFilePath = new File(backupFileDirPath + "/" + backupFileName).getAbsolutePath();

        Zip zip = null;

        if (!new File(backupFileDirPath).exists()) {
            throw new DaoException("バックアップ保管用ディレクトリが存在しません。");
        }

        // バックアップ対象ファイルの存在確認
        File readFile = new File(dirPath + "/" + fileName);
        if (!readFile.exists()) {
            invoker.errorLog(dirPath + "にファイルが存在しません");
            // 終了ログ出力
            invoker.infoLog(invoker.getUserId() + "：ファイルバックアップ処理を終了します。");
        } else {

            zip = new Zip();
            zip.setSrcFilename(dirPath + "/" + fileName);
            zip.encodeZip(backupFilePath);
    
            // ログ出力
            invoker.infoLog(invoker.getUserId() + "：バックアップを行いました。[" + backupFilePath + "]");
    
            // 終了ログ出力
            invoker.infoLog(invoker.getUserId() + "：ファイルバックアップ処理を終了します。");
    
            // 2013.10.22 T.Ooshiro [CUS00057] 結合テスト課題対応 №005（S)
            // 開始ログ出力
            invoker.infoLog(invoker.getUserId() + "： ファイル削除処理を開始します。");
    
            // ファイル削除
            File srcFile = new File(dirPath + "\\" + fileName);
            if (srcFile.exists()) {
                //srcFile.delete();//TODO 石川 テスト用コメントアウト
            }
            // ログ出力
            invoker.infoLog(invoker.getUserId() + "：ファイル削除を行いました。[" + dirPath + "/" + fileName + "]");
    
            // 終了ログ出力
            invoker.infoLog(invoker.getUserId() + "：ファイル削除処理を終了します。");
            // 2013.10.22 T.Ooshiro [CUS00057] 結合テスト課題対応 №005（E)
        }
    }

    /**
    * ファイル名にシステム日付をセットする。
    * 例 : XXXX0000.txt → XXXX0000_YYYYMMDDHHMMSS.zip
    * @param fileName
    * @return バックアップファイル名
    */
    private String fileBackUpName(String fileName) {

        String[] name = fileName.split("\\.");

        return name[0].trim() + UriageCommonConstants.UNDERBAR + sysTime + ".zip";
    }

    /**
     * 
     * SEITO社POSDataの取り込み処理
     * 
     * @param invokerIf データベースアクセスオブジェクトの為のinvoker
     * @param jobId ジョブID
     * @param dirPath ディレクトリパス
     * @param mainFileId ファイル名
     * @throws Exception 例外
     */
    abstract protected void seitoPosTorikomi(DaoInvokerIf invoker, String jobId, String dirPath, String mainFileId) throws Exception;

    /**
     * 明細レコードからデータを登録する
     * 
     * @param preparedStatementExIns インサート用preparedStatement
     * @param csvlist 明細レコード(コンマ分割後配列)
     * @param tenpoCd 店コード
     * @param eigyoDt 営業日
     * @param dbServerTime DBサーバ時刻
     * @return 登録データ件数
     * @throws SQLException
     */
    abstract protected int insertData(PreparedStatementEx preparedStatementExIns, String[] csvlist, String tenpoCd, String eigyoDt, String dbServerTime) throws SQLException;

    /**
     * 明細行判定
     * 
     * @param recordColumnCount レコードカラム桁数
     * @return true：明細行である、false：明細行ではない
     */
    abstract protected boolean isDetailRecord(int recordColumnCount);
    
    
    /**
     * JOBIDを取得します。
     * @return バッチ処理名
     */
    abstract String getJobId();

    /**
     * バッチ処理名を取得します。
     * @return バッチ処理名
     */
    abstract String getDaoName();

    /**
     * 登録先テーブル名称を取得します。
     * @return 登録先テーブル名称
     */
    abstract String getInsTableName();

    /**
     * 取込ファイルを取得します。
     * @return 取込ファイル
     */
    abstract String getFileName();

    /**
     * 追加SQL文を取得します。
     * @return 追加SQL文
     */
    abstract String getInsSql();

    /**
     * 削除SQL文を取得します。
     * @return 削除SQL文
     */
    abstract String getDelSql();

    /**
     * バックアップフォルダPIDを取得します。
     * @return バックアップフォルダPID
     */
    abstract String getBackUpDirPID();
}
