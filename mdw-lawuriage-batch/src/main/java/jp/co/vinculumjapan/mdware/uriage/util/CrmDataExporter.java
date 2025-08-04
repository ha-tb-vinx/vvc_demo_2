package jp.co.vinculumjapan.mdware.uriage.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

import jp.co.vinculumjapan.mdware.common.util.Zip;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 * <p>タイトル: CSV データの処理、フォーマット、エクスポート (海外CRM用IF)</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VVC</p>
 * @author THONG.LT
 * @Version 1.00 (2025.07.10) THONG.LT #35147 対応
 */

public class CrmDataExporter {
    
    private static DaoInvokerIf invoker;
    /** ログ出力用変数 */
    private static String batchId;
    /** IFファイル格納ディレクトリ */
    private static final String IF_CRM_PATH = FiResorceUtility.getPropertie("IF_CRM_PATH");
    /** 暗号化キー */
    private static final String IF_CRM_KYAKU_CD_ENCRYPT_KEY = FiResorceUtility.getPropertie("IF_CRM_KYAKU_CD_ENCRYPT_KEY");
    /** 出力ファイル文字コード */
    private static final String OUTPUT_CHAR_SET = "UTF-8";
    /** CSVファイル名 */
    public String fileName;
    /** 検索条件 */
    public String queryString;
    
    public CrmDataExporter(DaoInvokerIf invoker, String batchName) {
        CrmDataExporter.invoker = invoker;
        CrmDataExporter.batchId = invoker.getUserId() + " " + batchName;
    }
    
    public static class ColumnSpec {
        public String columnName;
        public int maxBytes;
        public boolean leftToRight;
        public boolean isEncrypt;
        
        public ColumnSpec(String columnName, int maxBytes) {
            this.columnName = columnName;
            this.maxBytes = maxBytes;
            this.isEncrypt = false;
        }

        public ColumnSpec(String columnName, int maxBytes, boolean isEncrypt) {
            this.columnName = columnName;
            this.maxBytes = maxBytes;
            this.isEncrypt = isEncrypt;
        }
    }
    
    public List<ColumnSpec> columnSpecs = new ArrayList<ColumnSpec>();
    
    /**
     * 本処理 - ファイル作成
     * 
     * @throws Exception 例外
     */
    public void export() throws Exception {
        invoker.infoLog(batchId + " ： 処理を開始します");

        //基本設定をチェックする
        if (fileName == null 
                || fileName.isEmpty() 
                || queryString == null 
                || queryString.isEmpty() 
                || columnSpecs.isEmpty()) {
            invoker.errorLog(batchId + " ： 設定エラー");
            throw new Exception("設定エラー");
        }

        //ＣＳＶ出力先のパスの設定をチェックする
        if (IF_CRM_PATH == null || IF_CRM_PATH.trim().length() == 0 )
        {
            invoker.errorLog(batchId + " ： システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
            throw new Exception("出力パスが設定されていません");
        }

        //ディレクトリの存在をチェックする
        File folderPath = new File(IF_CRM_PATH);
        if (!folderPath.exists()) {
            invoker.errorLog(batchId + " ： ＣＳＶ出力先のパスが存在しません");
            throw new Exception(folderPath.getPath().toString() + " が存在しません。");
        }

        //古いCSVファイルの存在をチェックする
        String csvFilePath = folderPath + File.separator + fileName + ".csv";
        deleteFileIfExists(csvFilePath);

        //CSVファイル作成
        invoker.infoLog(batchId + " ： " + fileName + ".csvファイル作成を開始します");
        PreparedStatementEx ps = invoker.getDataBase().prepareStatement(queryString);
        ResultSet rs = ps.executeQuery();
        int iRec = 0;

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(csvFilePath), OUTPUT_CHAR_SET);
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        // UTF_8_BOM
        writer.write("\uFEFF");

        while (rs.next()) {
            if (iRec != 0) {
                writer.newLine();
            }
            iRec++;
            for (int i = 0; i < columnSpecs.size(); i++) {
                ColumnSpec col = columnSpecs.get(i);
                String rawValue = rs.getString(col.columnName);
                String value = cutStringByByte(rawValue, col.maxBytes);
                if (col.isEncrypt && value != null && !value.isEmpty()) {
                    // 顧客コード
                    long  memberCd = Long.parseLong(value);
                    value = String.format("%0" + col.maxBytes + "d", memberCd);
                    value = encrypt(value);
                }
                value = "\"" + value.replace("\"", "\"\"") + "\"";
                writer.write(value);
                if (i < columnSpecs.size() - 1) {
                    writer.write(",");
                }
            }
        }
        writer.close();
        rs.close();
        ps.close();
        invoker.infoLog(batchId + " ： " + iRec + "件のデータを取得しました");
        invoker.infoLog(batchId + " ： " + fileName + ".csvファイル作成を終了しました");

        //古いZIPファイルの存在をチェックする
        String zipFilePath =  csvFilePath.replace(".csv", ".zip");
        deleteFileIfExists(zipFilePath);

        //ZIPファイル作成
        invoker.infoLog(batchId + " ： " + fileName + ".zipファイル作成を開始します");
        Zip.encodeZip(csvFilePath, zipFilePath);
        invoker.infoLog(batchId + " ： " + fileName + ".zipファイル作成を終了しました");

        //CSVファイル削除
        deleteFileIfExists(csvFilePath);
        
        invoker.infoLog(batchId + " ：処理を終了しました");
    }
    
    private static String cutStringByByte(String value, int maxBytes) throws UnsupportedEncodingException { 
        if (value == null || value.isEmpty() || maxBytes <= 0) 
            return "";
        
        value = value.trim();
        
        byte[] bytes = value.getBytes(OUTPUT_CHAR_SET);
        
        if (bytes.length <= maxBytes) 
            return value;
        
        int byteCount = 0;
        int i = 0;
        for (; i < value.length(); i++) {
            String ch = String.valueOf(value.charAt(i));
            int charBytes = ch.getBytes(OUTPUT_CHAR_SET).length;
    
            if (byteCount + charBytes > maxBytes)
                break;
    
            byteCount += charBytes;
        }
        
        return value.substring(0, i);
    }
    
    private static void deleteFileIfExists(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                invoker.infoLog(batchId + " ： " + filePath + "の削除処理を実施しました");
            } else {
                invoker.infoLog(batchId + " ： " + filePath + "の削除に失敗しました");
                throw new IOException("ファイル削除失敗: " + filePath);
            }
        }
    }
    
    private static String encrypt(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = new SecretKeySpec(IF_CRM_KYAKU_CD_ENCRYPT_KEY.getBytes(OUTPUT_CHAR_SET), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes(OUTPUT_CHAR_SET));
        byte[] encodedBytes = Base64.encodeBase64(encryptedBytes);
        
        return new String(encodedBytes, OUTPUT_CHAR_SET);
    }
}