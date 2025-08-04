package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.JikantaiDataTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;


/**
 * <p>タイトル: JikantaiDataTorikomiDao クラス</p>
 * <p>説明　: 時間帯データ取込</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.05.20) Hirata FIVImart様対応
 * @Version 1.01 (2020.01.28) THONG.VQ MKV対応 #6293
 */
public class JikantaiDataTorikomiDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "時間帯データ取込";
    /** 登録先テーブル1名称 */
    private static final String INS_TABLE_NAME1 = "時間帯データ（FORMAT1）データ";
    /** 登録先テーブル2名称 */
    private static final String INS_TABLE_NAME2 = "時間帯データ（FORMAT2）データ";
    /** テーブル1レコード長 */
    private static final int TABLE1_RECORD_LENGTH = 92;
    /** テーブル2レコード長 */
    private static final int TABLE2_RECORD_LENGTH = 36;

    // 追加SQL文用定数
    private static final String INS_SQL1 = "insertTmpJikantaiFormat1";
    private static final String INS_SQL2 = "insertTmpJikantaiFormat2";
    // 削除SQL文用定数
    private static final String DEL_SQL1 = "deleteTmpJikantaiFormat1";
    private static final String DEL_SQL2 = "deleteTmpJikantaiFormat2";

    /** インプットBean */
    private JikantaiDataTorikomiDaoInputBean inputBean = null;

    /**
     * 取込ファイルを取得します。
     * @return 取込ファイル
     */
    private String getFileName() {
        return inputBean.getFileId();
    }

    /**
     * ファイルフォルダPIDを取得します。
     * @return ファイルフォルダPID
     */
    private String getFileDirPID() {
        return inputBean.getFileDirPID();
    }

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 取込ファイル名
        String fileName = getFileName();
        // ファイルフォルダ取得
        String fileDirPath = FiResorceUtility.getPropertie(getFileDirPID());

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        // 
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // ディレクトリを取得する
        File directory = new File(fileDirPath);
        if (!directory.isDirectory()) {
            invoker.errorLog(strUserId + "　：　" + "ディレクトリを正しく指定して下さい。");
            throw new IllegalStateException(strUserId + "　：　" + "ディレクトリを正しく指定して下さい。");
        }

        // ファイルをリスト化
        File readFile = new File(directory + "/" + fileName);
        if (!readFile.exists()) {
            invoker.errorLog(strUserId + "　：　" + fileDirPath + "にファイルが存在しません。");
        } else {

            BufferedReader file = null;

            int insertCount1 = 0;
            int insertCount2 = 0;

            // オブジェクトを初期化する
            PreparedStatementEx preparedStatementExIns1 = null;
            PreparedStatementEx preparedStatementExIns2 = null;
            PreparedStatementEx preparedStatementDelete1 = null;
            PreparedStatementEx preparedStatementDelete2 = null;

            try {

                // SQLを取得し、TMPテーブルを削除する
                preparedStatementDelete1 = invoker.getDataBase().prepareStatement(DEL_SQL1);
                preparedStatementDelete1.execute();
                preparedStatementDelete2 = invoker.getDataBase().prepareStatement(DEL_SQL2);
                preparedStatementDelete2.execute();

                // SQLを取得し、パラメータを条件にバインドする
                preparedStatementExIns1 = invoker.getDataBase().prepareStatement(INS_SQL1);
                preparedStatementExIns2 = invoker.getDataBase().prepareStatement(INS_SQL2);

                // ファイル読み込み
                file = new BufferedReader(new FileReader(readFile));

                // 全行読込
                String record;
                // #6293 URIB013410 Upd 2020.01.28 THONG.VQ (S)
                //while ((record = file.readLine()) != null) {
                HashMap<String, String> recordDatas= new HashMap();
                while ((record = file.readLine()) != null) {
                    String key = record.substring(0, 21);
                    String value = record;
                    recordDatas.put(key, value);
                }
                file.close();
                for (Map.Entry<String, String> recordData : recordDatas.entrySet()) {

                    record = recordData.getValue();
                // #6293 URIB013410 Upd 2020.01.28 THONG.VQ (E)
                    switch (record.length()) {
                    case TABLE1_RECORD_LENGTH:
                        try {
                            String tenpoCd = record.substring(0, 4);
                            String syohinCd = record.substring(4, 17);
                            String hhMm = record.substring(17, 21);
                            String uriageQt = getZeroSuppressedNumber(record.substring(21, 32));
                            String uriageVl = getZeroSuppressedNumber(record.substring(32, 55));
                            String kyakuQt = getZeroSuppressedNumber(record.substring(55, 61));
                            String uriageNukiVl = getZeroSuppressedNumber(record.substring(61, 84));
                            String keijyoDt = record.substring(84, 92);
                            
                            int index = 1;
                            preparedStatementExIns1.setString(index++, tenpoCd);
                            preparedStatementExIns1.setString(index++, syohinCd);
                            preparedStatementExIns1.setString(index++, hhMm);
                            preparedStatementExIns1.setString(index++, uriageQt);
                            preparedStatementExIns1.setString(index++, uriageVl);
                            preparedStatementExIns1.setString(index++, kyakuQt);
                            preparedStatementExIns1.setString(index++, uriageNukiVl);
                            preparedStatementExIns1.setString(index++, keijyoDt);
                            
                            insertCount1 += preparedStatementExIns1.executeUpdate();
                        } catch (Exception e) {
                            throw e;
                        }
                        break;
                    case TABLE2_RECORD_LENGTH:
                        try {
                            String tenpoCd = record.substring(0, 4);
                            String kyakuSyukeiTani = record.substring(4, 7);
                            String kyakuSyukeiCd = record.substring(7, 17);
                            String hhMm = record.substring(17, 21);
                            String kyakuQt = getZeroSuppressedNumber(record.substring(21, 28));
                            String keijyoDt = record.substring(28, 36);
                            
                            int index = 1;
                            preparedStatementExIns2.setString(index++, tenpoCd);
                            preparedStatementExIns2.setString(index++, kyakuSyukeiTani);
                            preparedStatementExIns2.setString(index++, kyakuSyukeiCd);
                            preparedStatementExIns2.setString(index++, hhMm);
                            preparedStatementExIns2.setString(index++, kyakuQt);
                            preparedStatementExIns2.setString(index++, keijyoDt);
                            
                            insertCount2 += preparedStatementExIns2.executeUpdate();
                        } catch (Exception e) {
                            throw e;
                        }
                        break;
                    }
                }

                // #6293 URIB013410 Del 2020.01.28 THONG.VQ (S)
                //file.close();
                // #6293 URIB013410 Del 2020.01.28 THONG.VQ (E)
                
                invoker.getDataBase().commit();

                // ログ出力
                invoker.infoLog(strUserId + "　：　" + "取込件数は" + INS_TABLE_NAME1 + "：" + insertCount1 + "件です。");
                invoker.infoLog(strUserId + "　：　" + "取込件数は" + INS_TABLE_NAME2 + "：" + insertCount2 + "件です。");

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
                    if (preparedStatementExIns1 != null) {
                        preparedStatementExIns1.close();
                    }
                    
                    if (preparedStatementExIns2 != null) {
                        preparedStatementExIns2.close();
                    }

                    if (preparedStatementDelete1 != null) {
                        preparedStatementDelete1.close();
                    }
                    
                    if (preparedStatementDelete2 != null) {
                        preparedStatementDelete2.close();
                    }

                } catch (Exception e) {
                    invoker.infoLog(strUserId + "　：　" + "preparedStatement Closeエラー");
                }
            }

        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

    }

    /**
     * 0を削除した数字文字列を取得する
     *
     * @param leftZeroFilledNumber 左0埋めされた数字文字列
     * @return String 0を削除した数字文字列
     */
    private String getZeroSuppressedNumber(String leftZeroFilledNumber) {
        final Pattern pttrn = Pattern.compile("^(-?)0*(\\d+)$");
        Matcher mtchr = pttrn.matcher(leftZeroFilledNumber);
        if (mtchr.find()) {
            return mtchr.group(1) + mtchr.group(2);
        } else {
            return leftZeroFilledNumber;
        }
    }

    /**
     * アウトプットBeanを取得する
     *
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input JikantaiDataTorikomiDaoInputBean型オブジェクト
     */
    public void setInputObject(Object input) throws Exception {
        inputBean = (JikantaiDataTorikomiDaoInputBean) input;

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new JikantaiDataTorikomiDao();
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
