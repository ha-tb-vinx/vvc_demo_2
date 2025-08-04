package jp.co.vinculumjapan.mdware.uriage.util;

import jp.co.vinculumjapan.mdware.common.dao.impl.DataImportDao;
import jp.co.vinculumjapan.mdware.common.dao.input.DataImportDaoInputBean;
import jp.co.vinculumjapan.mdware.common.dao.output.DataImportDaoOutputBean;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;

/**
 * <p>タイトル: FiDataImportDaoUtility クラス</p>
 * <p>説明: データ取込ユーティリティ[BAT用]</p>
 * <p>当クラス処理を利用する場合は、</p>
 * <p>
 * <ul>
 * <li>mdw-mm[SUBSYSTEM_ID]-bat/src/main/java/jp/co/vinculumjapan/mdware/[SUBSYSTEM_ID]/util<br>
 *     に配置して利用して下さい</li>
 * </ul>
 * </p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: VJC</p>
 * @author VJC
 * @version 1.00
 */
public class FiDataImportDaoUtility {

    /**
     * リソースキー、ログファイルディレクトリ
     */
    private static final String PARAM_ID_LOADER_LOG_DIR_PATH = "LOADER_LOG_DIR_PATH";

    /**
     * リソースキー、フォーマットファイルディレクトリ
     */
    private static final String PARAM_ID_LOADER_FORMAT_DIR_PATH = "LOADER_FORMAT_DIR_PATH";

    /**
     * リソースキー、データファイルディレクトリ
     */
    private static String dataDirPath = "DATA_DIR_PATH";
    
    /**
     * リソースキー、バックアップファイルディレクトリ
     */
    private static String backupDirPtah = "BACKUP_DIR_PATH";

    /**
     * SQL*Loaderを実行し、結果を取得する
     * @param invoker DaoInvokerIf
     * @param dataFileName データファイル名
     * @param formatFileName フォーマットファイル名
     * @param backupFileName バックアップファイル名
     * @param logFileName ログファイル名
     * @param tableName テーブル名
     * @return 結果のDataImportDaoOutputBean
     * @throws Exception
     */
    public static DataImportDaoOutputBean executeDataAccess(DaoInvokerIf invoker, String dataFileName, String formatFileName, String backupFileName, String logFileName, String tableName) throws Exception {

        //入力用Beanにデータを設定する
        DataImportDaoInputBean ibean = new DataImportDaoInputBean();
        ibean.setDataFileDirPath(FiResorceUtility.getPropertie(FiDataImportDaoUtility.getDataDirPath()));
        ibean.setDataFileName(dataFileName);
        ibean.setFormatFileDirPath(FiResorceUtility.getPropertie(FiDataImportDaoUtility.PARAM_ID_LOADER_FORMAT_DIR_PATH));
        ibean.setFormatFileName(formatFileName);
        ibean.setBackupFileDirPath(FiResorceUtility.getPropertie(FiDataImportDaoUtility.getBackupDirPtah()));
        ibean.setBackupFileName(backupFileName);
        ibean.setLogFileDirPath(FiResorceUtility.getPropertie(FiDataImportDaoUtility.PARAM_ID_LOADER_LOG_DIR_PATH));
        ibean.setLogFileName(logFileName);
        ibean.setTableName(tableName);

        //SQL*Loaderを実行し、結果を返す
        DataImportDao dao = new DataImportDao();
        dao.setInputObject(ibean);
        invoker.InvokeDao(dao);
        return (DataImportDaoOutputBean) dao.getOutputObject();
    }

    /**
     * バックアップファイルディレクトリのパラメータID を返します
     * @return backupDirPtah
     */
    public static String getBackupDirPtah() {
        return backupDirPtah;
    }

    /**
     * バックアップファイルディレクトリのパラメータID を設定します
     * @param backupDirPtah 設定する backupDirPtah
     */
    public static void setBackupDirPtah(String backupDirPtah) {
        FiDataImportDaoUtility.backupDirPtah = backupDirPtah;
    }

    /**
     * データファイルディレクトリのパラメータID を返します
     * @return dataDirPath
     */
    public static String getDataDirPath() {
        return dataDirPath;
    }

    /**
     * データファイルディレクトリのパラメータID を設定します
     * @param dataDirPath 設定する dataDirPath
     */
    public static void setDataDirPath(String dataDirPath) {
        FiDataImportDaoUtility.dataDirPath = dataDirPath;
    }
}
