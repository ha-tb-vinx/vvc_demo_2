package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: AddEigyoDtJournalEDao クラス</p>
 * <p>説明　: Eレコード営業日付加処理</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2017.03.10) VINX J.Endo FIVI対応 #4322
 * @Version 1.01 (2017.03.16) VINX J.Endo FIVI対応 #4382
 * @Version 1.02 (2017.04.17) VINX J.Endo FIVI対応 #4701
 * @Version 1.03 (2017.06.20) VINX J.Endo FIVI対応 #5224
 */
public class AddEigyoDtJournalEDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "Eレコード営業日付加処理";
    /** 入力ファイルパス */
    private static final String FILE_PATH = "PATH_RECV_POS";
    /** 出力ファイルパス */
    private static final String OUTPUT_FOLDER = "/wk/";
    /** 対象ファイル特定文字 */
    private static final String FILENAME_MATCH_CHAR = "S";
    // 2017.03.16 VINX E.Endo FIVI対応(#4382) ADD(S)
    /** ファイル未到着の場合のファイル名 */
    private static final String FILE_NOT_EXIST = "S_ZERO__.xxx";
    // 2017.03.16 VINX E.Endo FIVI対応(#4382) ADD(E)
    /** 改行文字(CRLF) */
    private static final String LINE_FEED_CHAR = UriageCommonConstants.CRLF;
    /** 文字コード */
    private static final String CHAR_SET = "UTF-8";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        // ファイルフォルダ取得
        String fileDirPath = FiResorceUtility.getPropertie(FILE_PATH);
        // 入力の処理対象ファイル名
        String targetFileName = "";
        // Bレコード営業日
        String strEigyoDate;
        // 処理ファイル数
        int fileCount = 0;
        // 2017.06.20 VINX E.Endo FIVI対応(#5224) ADD(S)
        // ユーザIDを取得
        String userId = invoker.getUserId();
        // バッチ日付を取得
        String batchDt = FiResorceUtility.getBatchDt();
        // 2017.06.20 VINX E.Endo FIVI対応(#5224) ADD(E)

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // ディレクトリを取得する
        File directory = new File(fileDirPath);
        if (!directory.isDirectory()) {
            invoker.errorLog(strUserId + "　：　" + "ディレクトリを正しく指定して下さい。");
            throw new IllegalStateException(strUserId + "　：　" + "ディレクトリを正しく指定して下さい。");
        }

        // 複数の入力対象ファイルを取得
        String[] fileList= directory.list();
        for(String fileName:fileList){
            if (fileName.substring(0,1).equals(FILENAME_MATCH_CHAR)) {
                targetFileName = fileName.toString();

                // 2017.03.16 VINX E.Endo FIVI対応(#4382) ADD(S)
                if (targetFileName.equals(FILE_NOT_EXIST)) {
                    // 空ファイル作成
                    File writeFile = new File(fileDirPath + OUTPUT_FOLDER + targetFileName);
                    try{
                        writeFile.createNewFile();
                    }catch(IOException e){
                    }
                } else {
                // 2017.03.16 VINX E.Endo FIVI対応(#4382) ADD(E)
                    // ファイルをリスト化
                    File readFile = new File(directory + "/" + targetFileName);
                    if (!readFile.exists()) {
                        invoker.infoLog(strUserId + "　：　" + fileDirPath + "に対象ファイルが存在しません。");
                    } else {
                        // 入力ファイル
                        BufferedReader br = null;
                        // 出力ファイル
                        BufferedWriter bw = null;
                        // Bレコード営業日を初期化
                        strEigyoDate = null;
                        // レコードワーク
                        String record;
    
                        try {
                            // ファイル読み込み（Bレコードの営業日取得）
                            br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile), CHAR_SET));
                            while ((record = br.readLine()) != null) {
                                if (record.substring(26, 27).equals("B")) {
                                    strEigyoDate = record.substring(115,123);
                                    break;
                                }
                            }

                            br.close();

                            // 2017.06.20 VINX E.Endo FIVI対応(#5224) MOD(S)
                            //// Bレコードの営業日が取得出来た場合はEレコードに付加
                            //if (strEigyoDate != null) {
                            // Bレコードの営業日が取得出来なかった場合はファイル名から営業日を取得
                            if (strEigyoDate == null) {
                                strEigyoDate = getEigyoDt(targetFileName, batchDt, invoker, userId);
                            }
                            // 2017.06.20 VINX E.Endo FIVI対応(#5224) MOD(E)

                            // 出力ファイル用フォルダ作成
                            File newfile = new File(fileDirPath + OUTPUT_FOLDER);
                            newfile.mkdir();

                            // 出力ファイル作成
                            File writeFile = new File(fileDirPath + OUTPUT_FOLDER + targetFileName);
                            // 上書モードで出力ファイルをオープン(FileOutputStream:false)
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(writeFile, false), CHAR_SET);
                            bw = new BufferedWriter(outputStreamWriter);

                            // ファイル読み込み（Eレコードに営業日を付加して出力）
                            br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile), CHAR_SET));
                            while ((record = br.readLine()) != null) {
                                // 2017.04.17 VINX E.Endo FIVI対応(#4701) MOD(S)
                                //if (record.substring(26, 27).equals("E")) {
                                // 2017.06.20 VINX E.Endo FIVI対応(#5224) MOD(S)
                                //if (record.substring(26, 27).equals("E") || 
                                //    record.substring(26, 27).equals("T")) {
                                if (record.substring(26, 27).equals("E") || 
                                    record.substring(26, 27).equals("F") || 
                                    record.substring(26, 27).equals("T")) {
                                // 2017.06.20 VINX E.Endo FIVI対応(#5224) MOD(E)
                                // 2017.04.17 VINX E.Endo FIVI対応(#4701) MOD(E)
                                    bw.write(record + strEigyoDate + LINE_FEED_CHAR);
                                } else {
                                    bw.write(record + LINE_FEED_CHAR);
                                }
                            }

                            fileCount++; // POSジャーナルファイルをカウント

                            bw.close();
                            br.close();
                            // 2017.06.20 VINX E.Endo FIVI対応(#5224) DEL(S)
                            //}
                            // 2017.06.20 VINX E.Endo FIVI対応(#5224) DEL(E)
                        } catch (Exception e) {
                            invoker.errorLog(e.toString());
                            throw e;
                        } finally {
                            try {
                                if (br != null) {
                                    br.close();
                                }
                                if (bw != null) {
                                    bw.close();
                                }
                            } catch (Exception e) {
                                invoker.infoLog(strUserId + "　：　" + "FILE Closeエラー");
                            }
                        }
                    // 2017.03.16 VINX E.Endo FIVI対応(#4382) ADD(S)
                    }
                    // 2017.03.16 VINX E.Endo FIVI対応(#4382) ADD(E)
                }
            }
        }
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + fileCount + "ファイルのPOSジャーナルを更新しました。");

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * ファイル名とバッチ日付から営業日を取得する
     * 
     * @param String fileName
     * @param String batchDt
     * @param DaoInvokerIf invoker
     * @param String userId
     * @return String
     * @throws Exception
     */
    private String getEigyoDt(String fileName, String batchDt, DaoInvokerIf invoker, String userId) throws Exception {
        
        // ファイル名の長さが規定と異なる場合、エラー
        if (fileName.length() != 12) {
            invoker.errorLog(userId + "　：　ファイル名の長さが規定の長さ(12桁)と異なります：" + fileName);
            throw new DaoException(userId + "　：　ファイル名の長さが規定の長さ(12桁)と異なります：" + fileName);
        }
        
        StringBuilder sb = new StringBuilder();
        
        // バッチ日付の年の頭3桁を取得
        int batchYear = Integer.parseInt(batchDt.substring(0, 3));
        // バッチ日付の年の下1桁を取得
        String batchYearUnder1Digit = batchDt.substring(3, 4);
        // バッチ日付の月を取得
        String batchMonth = batchDt.substring(4, 6);
        // ファイル名から月を取得
        String fileMonth = fileName.substring(2, 3);
        
        
        // 【営業日の年を設定】
        // バッチ日付の下1桁が"0"かつ、バッチ日付の月が1月、かつファイル名の月が12月の場合
        // (10年単位の年跨ぎ)
        if ("0".equals(batchYearUnder1Digit) && "01".equals(batchMonth) && "C".equals(fileMonth)) {
            // バッチ日付の頭3桁から1を引いたものにファイル名の2桁目を結合して設定
            sb.append(String.valueOf(batchYear - 1)).append(fileName.substring(1, 2));
        } else {
            // 上記以外はバッチ日付の頭3桁にファイル名の2桁目を結合して設定
            sb.append(String.valueOf(batchYear)).append(fileName.substring(1, 2));
        }
        
        // 【営業日の月を設定】
        // ファイ名の月がA,B,Cの場合、それぞれ10,11,12を設定、それ以外の場合はファイル名の月
        // に前0をつけて設定
        if ("A".equals(fileMonth)) {
            sb.append("10");
        } else if ("B".equals(fileMonth)) {
            sb.append("11");
        } else if ("C".equals(fileMonth)) {
            sb.append("12");
        } else {
            sb.append("0").append(fileMonth);
        }
        
        // 【営業日の日を設定】
        sb.append(fileName.substring(3, 5));
        
        // 日付の妥当性チェック
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);
        
        try {
            df.parse(sb.toString());
        } catch (ParseException e) {
            invoker.errorLog(userId + "　：　ファイル名とバッチ日付から営業日として妥当性のある日付が取得できませんでした：" + sb.toString());
            throw new DaoException(userId + "　：　ファイル名とバッチ日付から営業日として妥当性のある日付が取得できませんでした：" + sb.toString());
        }
        
        return sb.toString();
    }

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
            DaoIf dao = new AddEigyoDtJournalEDao();
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
