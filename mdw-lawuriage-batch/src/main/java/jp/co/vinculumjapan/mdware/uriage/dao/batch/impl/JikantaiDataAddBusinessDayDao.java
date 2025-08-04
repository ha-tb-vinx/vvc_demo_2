package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.JikantaiDataAddBusinessDayDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * 
 * <p>タイトル: JikantaiDataAddBusinessDayDao クラス</p>
 * <p>説明　: 時間帯データ営業日付加処理</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.05.20) T.Kamei 新規作成
 *
 */
public class JikantaiDataAddBusinessDayDao implements DaoIf {
    
    /* 時間帯データ営業日付与処理入力Bean */
    private JikantaiDataAddBusinessDayDaoInputBean input = null;

    /* (非 Javadoc)
     * @see jp.co.vinculumjapan.swc.commons.dao.DaoIf#executeDataAccess(jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf)
     */
    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
        
        // ユーザIDを取得
        String userId = invoker.getUserId();
        
        // バッチ日付を取得
        String batchDt = FiResorceUtility.getBatchDt();
        
        // データ取得元フォルダ名を取得
        String inputFilePathName = ResorceUtil.getInstance().getPropertie(input.getInputFilePath());
        
        // データ出力先フォルダ名を取得
        String outputFilePathName = ResorceUtil.getInstance().getPropertie(input.getOutputFilePath());
        
        try {
        
            // 取得元、出力先のパスを取得
            File inputFilePath = new File(inputFilePathName);
            File outputFilePath = new File(outputFilePathName);
            
            
            // 取得元フォルダの存在チェック
            if (!inputFilePath.exists()) {
                invoker.errorLog(userId + "　：　取得元フォルダが存在しません：" + inputFilePathName);
                throw new DaoException(userId + "　：　取得元フォルダが存在しません：" + inputFilePathName);
            }
            
            // 出力先フォルダの存在チェック
            if (!outputFilePath.exists()) {
                invoker.errorLog(userId + "　：　出力先フォルダが存在しません：" + outputFilePathName);
                throw new DaoException(userId + "　：　出力先フォルダが存在しません：" + outputFilePathName);
            }
            
            // 取得元フォルダ内のファイルを取得
            File inFiles[] = inputFilePath.listFiles();
            
            // 取得元フォルダ内のファイル存在チェック
            if (inFiles == null) {
                invoker.infoLog(userId + "　：　時間帯データのファイルが存在しませんでした");
                return;
            }
            
            // 作成ファイルカウンター
            int createCnt = 0;
            
            // 取得元直下にあるファイルを全て処理する
            for(File readFile : inFiles) {
                
                // 取得したオブジェクトがフォルダの場合は次のファイルへ
                if (readFile.isDirectory()) {
                    continue;
                }
                
                // ファイル名から営業日を取得
                String eigyoDt = getEigyoDt(readFile.getName(), batchDt, invoker, userId);
                
                // ファイルをUTF-8で読み込み
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile), "UTF-8"));
                
                // 出力ファイルを用意 インプットと同じファイル名で出力用フォルダにUTF-8で出力
                File outputFile = new File(new StringBuilder(outputFilePath.getPath()).append("/").append(readFile.getName()).toString());
                PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8")));
                
                // ファイル内の最後のレコードまで1行ずつ読み込み、営業日を付与して出力する。
                String record = null;
                while ((record = br.readLine()) != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(record).append(eigyoDt);
                    pw.println(sb.toString());
                }
                
                // クローズ処理
                br.close();
                pw.close();
                
                // 作成ファイルカウンターをインクリメント
                createCnt++;
                
            }
            
            if (createCnt == 0) {
                // 作成したファイルが0件の場合（取得元フォルダ内にフォルダしかない場合）
                invoker.infoLog(userId + "　：　時間帯データのファイルが存在しませんでした");
            } else {
                invoker.infoLog(userId + "　：　" + createCnt + "ファイルの処理を行いました");
            }
        } catch(Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        }
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

    /* (非 Javadoc)
     * @see jp.co.vinculumjapan.swc.commons.dao.DaoIf#setInputObject(java.lang.Object)
     */
    @Override
    public void setInputObject(Object input) throws Exception {
        this.input = (JikantaiDataAddBusinessDayDaoInputBean) input;
    }

    /* (非 Javadoc)
     * @see jp.co.vinculumjapan.swc.commons.dao.DaoIf#getOutputObject()
     */
    @Override
    public Object getOutputObject() throws Exception {
        return null;
    }

}
