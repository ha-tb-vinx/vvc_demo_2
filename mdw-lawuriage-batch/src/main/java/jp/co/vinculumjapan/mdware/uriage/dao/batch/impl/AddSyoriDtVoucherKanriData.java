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
 * <p>タイトル: AddSyoriDtVoucherKanriData クラス</p>
 * <p>説明　: Voucher管理データ処理日付付加</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2017.09.13) VINX X.Liu FIVI対応 #5902
 * @Version 1.01 (2017.10.02) VINX X.Liu FIVI対応 #5991
 */
public class AddSyoriDtVoucherKanriData implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "Voucher管理データ処理日付付加";
    /** 入力ファイルパス */
    private static final String FILE_PATH = "PATH_RECV_BLYNK";
    /** 出力ファイルパス */
    private static final String OUTPUT_FOLDER = "/wk/";
    /** 対象ファイル特定文字 */
    private static final String FILENAME_MATCH_CHAR = "V";
    /** ファイル未到着の場合のファイル名 */
    private static final String FILE_NOT_EXIST = "V_ZERO__.xxx";
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
        // 処理日付
        String strSyoriDate;
        // 処理ファイル数
        int fileCount = 0;
        // ユーザIDを取得
        String userId = invoker.getUserId();
        // バッチ日付を取得
        String batchDt = FiResorceUtility.getBatchDt();

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

                if (targetFileName.equals(FILE_NOT_EXIST)) {
                    // 空ファイル作成
                    File writeFile = new File(fileDirPath + OUTPUT_FOLDER + targetFileName);
                    try{
                        writeFile.createNewFile();
                    }catch(IOException e){
                    }
                } else {
                    // ファイルをリスト化
                    File readFile = new File(directory + "/" + targetFileName);
                    if (!readFile.exists()) {
                        invoker.infoLog(strUserId + "　：　" + fileDirPath + "に対象ファイルが存在しません。");
                    } else {
                        // 入力ファイル
                        BufferedReader br = null;
                        // 出力ファイル
                        BufferedWriter bw = null;
                        // 処理日付を初期化
                        strSyoriDate = null;
                        // レコードワーク
                        String record;
    
                        try {
                            // ファイル名から処理日付を取得
                            strSyoriDate = getEigyoDt(targetFileName, batchDt, invoker, userId);

                            // 出力ファイル用フォルダ作成
                            File newfile = new File(fileDirPath + OUTPUT_FOLDER);
                            newfile.mkdir();

                            // 出力ファイル作成
                            File writeFile = new File(fileDirPath + OUTPUT_FOLDER + targetFileName);
                            // 上書モードで出力ファイルをオープン(FileOutputStream:false)
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(writeFile, false), CHAR_SET);
                            bw = new BufferedWriter(outputStreamWriter);

                            // ファイル読み込み（レコードに処理日付を付加して出力）
                            br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile), CHAR_SET));
                            while ((record = br.readLine()) != null) {
                                bw.write(record + strSyoriDate + LINE_FEED_CHAR);
                            }

                            fileCount++; // ファイルをカウント

                            bw.close();
                            br.close();
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
                    }
                }
            }
        }
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + fileCount + "ファイルを更新しました。");
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * ファイル名とバッチ日付から処理日付を取得する
     * 
     * @param String fileName
     * @param String batchDt
     * @param DaoInvokerIf invoker
     * @param String userId
     * @return String
     * @throws Exception
     */
    private String getEigyoDt(String fileName, String batchDt, DaoInvokerIf invoker, String userId) throws Exception {
        
        //#5991 Del X.Liu 2017.10.02 (S)
//        // ファイル名の長さが規定と異なる場合、エラー
//        if (fileName.length() != 7) {
//            invoker.errorLog(userId + "　：　ファイル名の長さが規定の長さ(7桁)と異なります：" + fileName);
//            throw new DaoException(userId + "　：　ファイル名の長さが規定の長さ(7桁)と異なります：" + fileName);
//        }
        //#5991 Del X.Liu 2017.10.02 (E)
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
        
        // 【処理日付の日を設定】
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
            DaoIf dao = new AddSyoriDtVoucherKanriData();
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
