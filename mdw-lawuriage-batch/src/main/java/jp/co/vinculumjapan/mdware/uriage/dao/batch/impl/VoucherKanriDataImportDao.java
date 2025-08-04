package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: VoucherKanriDataImportDao クラス</p>
 * <p>説明　: Voucher管理データ取込処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.10.18) VINX J.Endo FIVI対応
 * @Version 1.01 (2016.12.07) VINX J.Endo FIVI対応 Voucher管理データのファイル名変更 #3206
 * @Version 1.02 (2017.09.13) VINX X.Liu  FIVI対応 #5902
 */
public class VoucherKanriDataImportDao implements DaoIf {

    // バッチ処理名
    private static final String DAO_NAME = "Voucher管理データ取込処理";
    // ファイル保存ディレクトリパス
    private static final String FILE_PATH = "PATH_RECV_BLYNK";
    // Voucher管理データのレコードID("A":Add/Change, "D":Delete)
    private static final String REC_ID_DELETE = "D";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ファイルフォルダ取得
        String fileDirPath = FiResorceUtility.getPropertie(FILE_PATH);

        //#5902 Add X.Liu 2017.09.13 (S)
        //全レコードのリスト
        List<String> list = new ArrayList<String>();
        //#5902 Add X.Liu 2017.09.13 (E)
        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // ディレクトリを取得する
        File directory = new File(fileDirPath);
        if (!directory.isDirectory()) {
            invoker.errorLog(strUserId + "　：　" + "ディレクトリを正しく指定して下さい。");
            throw new IllegalStateException(strUserId + "　：　" + "ディレクトリを正しく指定して下さい。");
        }

        //2016.12.07 J.Endo FIVI対応(#3206) MOD(S)
        //// 取込ファイル名(当日部分(VYMDD))
        //String strFileName = getVoucherKanriFileName();
        // 取込ファイル名("V"で始まる１ファイル)
        //#5902 Mod X.Liu 2017.09.13 (S)
//        String strFileName = "V";
        String strFileName = "V_ALL_FILE.txt";
        //#5902 Mod X.Liu 2017.09.13 (E)
        //2016.12.07 J.Endo FIVI対応(#3206) MOD(E)
        // 取込ファイル名(連番部分(RR)※指定ディレクトリに存在する当日ファイルの連番を取得)
        String[] fileList= directory.list();
        for(String fileName:fileList){
            //2016.12.07 J.Endo FIVI対応(#3206) MOD(S)
            //if (strFileName.equals(fileName.substring(0,5))) {
            //    strFileName = strFileName + fileName.substring(5,7);
            //    break;
            //}
        	//#5902 Mod X.Liu 2017.09.13 (S)
//        	if (strFileName.equals(fileName.substring(0,1))) {
            if (strFileName.equals(fileName)) {
            //#5902 Mod X.Liu 2017.09.13 (E)
                strFileName = fileName.toString();
                invoker.infoLog(strUserId + "　：　対象のVoucher管理データは「" + strFileName + "」です。");
                break;
            }
            //2016.12.07 J.Endo FIVI対応(#3206) MOD(E)
        }

        // ファイルをリスト化
        File readFile = new File(directory + "/" + strFileName);
        if (!readFile.exists()) {
            invoker.infoLog(strUserId + "　：　" + fileDirPath + "に対象ファイルが存在しません。");
        } else {

            BufferedReader file = null;

            int insertCount = 0;
            int deleteCount = 0;

            // オブジェクトを初期化する
            PreparedStatementEx preparedStatementExInsert = null;
            PreparedStatementEx preparedStatementExDelete = null;

            try {
                // SQLを取得し、パラメータを条件にバインドする
                preparedStatementExInsert = invoker.getDataBase().prepareStatement(getInsertSql());
                preparedStatementExDelete = invoker.getDataBase().prepareStatement(getDeleteSql());

                // ファイル読み込み
                file = new BufferedReader(new FileReader(readFile));

                // 全行読込
                String record;
                while ((record = file.readLine()) != null) {
                    list.add(record);
                    //#5902 Del X.Liu 2017.09.13 (S)
//                    try {
//                        String strRecordFlag = record.substring(0, 1);
//                        String strVoucherStatus = record.substring(1, 2);
//                        String strBarcodeNumber = record.substring(2, 32);
//                        String strDateOfexpiry = record.substring(32, 40);
//                        String strVoucherNumber = record.substring(40, 50);
//                        String strVoucherPaymentType = record.substring(50, 53);
//                        String strVoucherAmount = record.substring(53, 70);
//                        int index = 1;
//                        if (strRecordFlag.equals(REC_ID_DELETE)) {
//                            preparedStatementExDelete.setString(index++, strVoucherNumber);
//
//                            deleteCount += preparedStatementExDelete.executeUpdate();
//                        } else {
//                            preparedStatementExInsert.setString(index++, strVoucherStatus);
//                            preparedStatementExInsert.setString(index++, strBarcodeNumber);
//                            preparedStatementExInsert.setString(index++, strDateOfexpiry);
//                            preparedStatementExInsert.setString(index++, strVoucherNumber);
//                            preparedStatementExInsert.setString(index++, strVoucherPaymentType);
//                            preparedStatementExInsert.setString(index++, strVoucherAmount);
//                            preparedStatementExInsert.setString(index++, userId);
//                            preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
//                            preparedStatementExInsert.setString(index++, userId);
//                            preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
//                            
//                            insertCount += preparedStatementExInsert.executeUpdate();
//                        }
//                    } catch (Exception e) {
//                        throw e;
//                    }
                    //#5902 Del X.Liu 2017.09.13 (E)
                }
                //#5902 Add X.Liu 2017.09.13 (S)
                if(list.size()>0){
                    
                    String[] array = list.toArray(new String[0]);
                    sortArray(array);
                    
                    for (int i = 0 ;i<array.length;i++){
                        record = array[i];
                        try {
                            String strRecordFlag = record.substring(0, 1);
                            String strVoucherStatus = record.substring(1, 2);
                            String strBarcodeNumber = record.substring(2, 32);
                            String strDateOfexpiry = record.substring(32, 40);
                            String strVoucherNumber = record.substring(40, 50);
                            String strVoucherPaymentType = record.substring(50, 53);
                            String strVoucherAmount = record.substring(53, 70);
                            int index = 1;
                            if (strRecordFlag.equals(REC_ID_DELETE)) {
                                preparedStatementExDelete.setString(index++, strVoucherNumber);

                                deleteCount += preparedStatementExDelete.executeUpdate();
                            } else {
                                preparedStatementExInsert.setString(index++, strVoucherStatus);
                                preparedStatementExInsert.setString(index++, strBarcodeNumber);
                                preparedStatementExInsert.setString(index++, strDateOfexpiry);
                                preparedStatementExInsert.setString(index++, strVoucherNumber);
                                preparedStatementExInsert.setString(index++, strVoucherPaymentType);
                                preparedStatementExInsert.setString(index++, strVoucherAmount);
                                preparedStatementExInsert.setString(index++, userId);
                                preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
                                preparedStatementExInsert.setString(index++, userId);
                                preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
                                
                                insertCount += preparedStatementExInsert.executeUpdate();
                            }
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
                //#5902 Add X.Liu 2017.09.13 (E)

                file.close();

                invoker.getDataBase().commit();

                // ログ出力
                invoker.infoLog(strUserId + "　：　" + insertCount + "件のVoucher管理データを追加しました。");
                invoker.infoLog(strUserId + "　：　" + deleteCount + "件のVoucher管理データを削除しました。");

            } catch (Exception e) {
                invoker.errorLog(e.toString());
                throw e;
            } finally {
                try {
                    if (file != null) {
                        file.close();
                    }
                } catch (Exception e) {
                    invoker.infoLog(strUserId + "　：　" + "FILE Closeエラー");
                }
                try {
                    if (preparedStatementExInsert != null) {
                        preparedStatementExInsert.close();
                    }
                    if (preparedStatementExDelete != null) {
                        preparedStatementExDelete.close();
                    }
                } catch (Exception e) {
                    invoker.infoLog(strUserId + "　：　" + "preparedStatement Closeエラー");
                }
            }

        }

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 出力データ登録用SQLを取得する
     * @return 出力データ登録用SQL
     */
    private String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("MERGE INTO DT_VOUCHER_KANRI DVK ");
        sql.append("    USING ( ");
        sql.append("        SELECT ");
        sql.append("            ? RECORD_FLAG, ");
        sql.append("            ? V_NUMBER, ");
        sql.append("            ? EXPIRY_DATE, ");
        sql.append("            ? V_VERF_NUMBER, ");
        sql.append("            ? V_PYMT_TYPE, ");
        sql.append("            ? V_AMT, ");
        sql.append("            ? INSERT_USER_ID, ");
        sql.append("            ? INSERT_TS, ");
        sql.append("            ? UPDATE_USER_ID, ");
        sql.append("            ? UPDATE_TS ");
        sql.append("        FROM DUAL ) WDVK ");
        sql.append("        ON (DVK.V_VERF_NUMBER = WDVK.V_VERF_NUMBER) ");
        sql.append("WHEN MATCHED THEN ");
        sql.append("    UPDATE SET ");
        sql.append("        DVK.RECORD_FLAG    = WDVK.RECORD_FLAG, ");
        sql.append("        DVK.V_NUMBER       = WDVK.V_NUMBER, ");
        sql.append("        DVK.EXPIRY_DATE    = WDVK.EXPIRY_DATE, ");
        sql.append("        DVK.V_PYMT_TYPE    = WDVK.V_PYMT_TYPE, ");
        sql.append("        DVK.V_AMT          = WDVK.V_AMT, ");
        sql.append("        DVK.UPDATE_USER_ID = WDVK.UPDATE_USER_ID, ");
        sql.append("        DVK.UPDATE_TS      = WDVK.UPDATE_TS ");
        sql.append("WHEN NOT MATCHED THEN ");
        sql.append("    INSERT ( ");
        sql.append("        DVK.RECORD_FLAG, ");
        sql.append("        DVK.V_NUMBER, ");
        sql.append("        DVK.EXPIRY_DATE, ");
        sql.append("        DVK.V_VERF_NUMBER, ");
        sql.append("        DVK.V_PYMT_TYPE, ");
        sql.append("        DVK.V_AMT, ");
        sql.append("        DVK.INSERT_USER_ID, ");
        sql.append("        DVK.INSERT_TS, ");
        sql.append("        DVK.UPDATE_USER_ID, ");
        sql.append("        DVK.UPDATE_TS) ");
        sql.append("    VALUES ( ");
        sql.append("        WDVK.RECORD_FLAG, ");
        sql.append("        WDVK.V_NUMBER, ");
        sql.append("        WDVK.EXPIRY_DATE, ");
        sql.append("        WDVK.V_VERF_NUMBER, ");
        sql.append("        WDVK.V_PYMT_TYPE, ");
        sql.append("        WDVK.V_AMT, ");
        sql.append("        WDVK.INSERT_USER_ID, ");
        sql.append("        WDVK.INSERT_TS, ");
        sql.append("        WDVK.UPDATE_USER_ID, ");
        sql.append("        WDVK.UPDATE_TS) ");

        return sql.toString();
    }

    /**
     * レコード削除用SQLを取得する
     * @return レコード削除用SQL
     */
    private String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM DT_VOUCHER_KANRI ");
        sql.append("WHERE V_VERF_NUMBER = ? ");

        return sql.toString();
    }

    //2016.12.07 J.Endo FIVI対応(#3206) DEL(S)
    ///**
    // * Voucher管理データのファイル名を生成(NYMDDRR→V6A19)※RR位置の連番は"01"固定ではないため後処理の存在確認で特定
    // * @return Voucher管理データのファイル名
    // */
    //private String getVoucherKanriFileName() {
    //    String strDate = FiResorceUtility.getDBServerTime();
    //    String strFileName = "V";                            // N位置の文字に"V"を設定
    //    strFileName = strFileName + strDate.substring(3,4);  // Y位置の文字に年を設定
    //    if (strDate.substring(4,6).equals("10")){            // M位置の文字に月を設定
    //        strFileName = strFileName + "A";
    //    } else if (strDate.substring(4,6).equals("11")){
    //        strFileName = strFileName + "B";
    //    } else if (strDate.substring(4,6).equals("12")){
    //        strFileName = strFileName + "C";
    //    } else {
    //        strFileName = strFileName + strDate.substring(5,6);
    //    }
    //    strFileName = strFileName + strDate.substring(6,8);  // DD位置の文字に日を設定
    //
    //    return strFileName;
    //}
    //2016.12.07 J.Endo FIVI対応(#3206) DEL(E)

    //#5902 Add X.Liu 2017.09.13 (S)
    /**
     * ソート処理
     * @param array
     * @throws ParseException
     */
    public static void sortArray(String[] array) throws ParseException {

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        if (array != null && array.length > 0) {
            for (int j = 0; j < array.length - 1; j++) {
                boolean flag = true;
                for (int i = 0; i < array.length - 1; i++) {
                    String tmp1 = array[i];
                    String tmp2 = array[i + 1];
                    String dt1 = tmp1.substring(tmp1.length() - 8);
                    String dt2 = tmp2.substring(tmp2.length() - 8);
                    String tp = "";
                    
                    if (sf.parse(dt1).after(sf.parse(dt2))) {
                        tp = tmp2;
                        array[i + 1] = tmp1;
                        array[i] = tp;
                        flag = false;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }
    //#5902 Add X.Liu 2017.09.13 (E)
    /**
     * インプットBeanを設定する
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    /**
     * アウトプットBeanを取得する
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new VoucherKanriDataImportDao();
            new StandAloneDaoInvoker("mm").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
