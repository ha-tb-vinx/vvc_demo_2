package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: OfflineVoucherSiyouJisekiCreateDao クラス</p>
 * <p>説明　: オフラインVoucher使用実績作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.10.18) VINX J.Endo FIVI対応
 * @Version 1.01 (2016.12.09) VINX J.Endo FIVI対応 Voucher使用実績データのファイル名(#3205)およびレイアウトを変更 #3012
 */
public class OfflineVoucherSiyouJisekiCreateDao implements DaoIf {

    // バッチ処理名
    private static final String DAO_NAME = "オフラインVoucher使用実績作成処理";
    // ファイル保存ディレクトリパス
    private static final String FILE_PATH = "PATH_SEND_BLYNK";
    // 改行文字(CRLF)
    private static final String LINE_FEED_CHAR = UriageCommonConstants.CRLF;
    // 出力ファイル文字コード
    private static final String OUTPUT_CHAR_SET = UriageCommonConstants.CHARSET;

    /**
     * オフラインVoucher使用実績作成処理
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザーID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExSelect = null;
        PreparedStatementEx preparedStatementExUpdate = null;
        ResultSet resultSet = null;

        // オフラインVoucher使用実績の出力済み分の格納用リスト
        List<String> voucherList = new ArrayList<String>();

        // オフラインVoucher使用実績の出力件数
        int createCount = 0;

        try {
            // SQL文を取得する
            preparedStatementExSelect = invoker.getDataBase().prepareStatement(getSelectSql());
            preparedStatementExUpdate = invoker.getDataBase().prepareStatement(getUpdateSql());

            // SQL文を実行する(Voucher使用実績を取得)
            resultSet = preparedStatementExSelect.executeQuery();

            // データ生成件数をセットする
            createCount = csvExport(resultSet, getVoucherFileName(), voucherList);

            // Voucher使用実績の「BLYNK連携区分」項目をBLYNK連携済みに更新
            for (int i = 0; i < voucherList.size(); i++) {
                // SQL文を実行する
                preparedStatementExUpdate.setString(1, userId);
                preparedStatementExUpdate.setString(2, FiResorceUtility.getDBServerTime());
                preparedStatementExUpdate.setString(3, voucherList.get(i));
                preparedStatementExUpdate.executeUpdate();
            }

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + createCount + "件のオフラインVoucher使用実績を作成しました。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExSelect != null) {
                    preparedStatementExSelect.close();
                }
                if (preparedStatementExUpdate != null) {
                    preparedStatementExUpdate.close();
                }
            } catch (Exception e2) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserID + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * Voucher使用実績データからオフラインVoucher使用実績の抽出を行うSQL
     * @return
     */
    private String getSelectSql() {
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT ");
        sql.append("    CREDIT_NO ");
        sql.append("   ,REGISTER_NO ");
        sql.append("   ,TRANSACTION_NO ");
        //2016.12.09 J.Endo FIVI対応(#3012) ADD(S)
        sql.append("   ,NVL(CASHIER_ID, ' ') CASHIER_ID ");
        sql.append("   ,TENPO_CD ");
        //2016.12.09 J.Endo FIVI対応(#3012) ADD(E)
        sql.append("FROM ");
        sql.append("    DT_VOUCHER_JISEKI ");
        sql.append("WHERE ");
        sql.append("    ONLINE_V_KB = '1' "); // 1:オフラインVoucherのみ抽出
        sql.append("AND BLYNK_KB = '0' ");    // 0:BLYNK未連携のみ抽出
        sql.append("ORDER BY ");
        sql.append("    CREDIT_NO ");

        return sql.toString();
    }

    /**
     * Voucher使用実績データのBLYNK連携済みフラグを連携済みに更新するSQL
     * @return
     */
    private String getUpdateSql() {
        StringBuffer sql = new StringBuffer();

        sql.append("UPDATE DT_VOUCHER_JISEKI ");
        sql.append("SET ");
        sql.append("    BLYNK_KB = '1' ");      // 1:BLYNK連携済み
        sql.append("   ,UPDATE_USER_ID = ? ");
        sql.append("   ,UPDATE_TS = ? ");
        sql.append("WHERE ");
        sql.append("    CREDIT_NO = ? ");
        
        return sql.toString();
    }

    /**
     * オフラインVoucher使用実績のファイル名を生成
     * @return オフラインVoucher使用実績のファイル名
     */
    private String getVoucherFileName() {
        //2016.12.09 J.Endo FIVI対応((#3205)#3012) MOD(S)
        //String strDate = FiResorceUtility.getDBServerTime();
        //String strFileName = "M";                            // N位置の文字に"M"を設定
        //strFileName = strFileName + strDate.substring(3,4);  // Y位置の文字に年を設定
        //if (strDate.substring(4,6).equals("10")){            // M位置の文字に月を設定
        //    strFileName = strFileName + "A";
        //} else if (strDate.substring(4,6).equals("11")){
        //    strFileName = strFileName + "B";
        //} else if (strDate.substring(4,6).equals("12")){
        //    strFileName = strFileName + "C";
        //} else {
        //    strFileName = strFileName + strDate.substring(5,6);
        //}
        //strFileName = strFileName + strDate.substring(6,8);  // DD位置の文字に日を設定
        //strFileName = strFileName + "01";                    // RR位置の文字に連番を設定
        String strFileName = "MBVOC";                        // 先頭に固定値"MBVOC"を設定
        strFileName = strFileName + FiResorceUtility.getDBServerTime().substring(0,8); // YYYYMMDD位置の文字にシステム日付を設定
        strFileName = strFileName + "01";                    // 連番に"01"を設定
        //2016.12.09 J.Endo FIVI対応((#3205)#3012) MOD(E)

        return strFileName;
    }

    /**
     * ファイル出力処理
     * @param rsSelectData
     * @param strFileName
     * @param retVoucherList
     * @return 出力件数
     * @throws Exception
     */
    private int csvExport(ResultSet rsSelectData, String strFileName, List<String> retVoucherList) throws Exception {
        // 出力データ数
        int outputCount = 0;
        // ファイル出力データ
        ResultSetMetaData rsdCSVData = null;
        // ファイル出力
        BufferedWriter writer = null;
        // ファイルフォルダ取得
        String fileDirPath = FiResorceUtility.getPropertie(FILE_PATH);

        File dataFile = new File(fileDirPath + "/" + strFileName);

        // 出力データはResultSetMetaDataへ変換する
        rsdCSVData = rsSelectData.getMetaData();

        try {
            // 追記モードで出力ファイルをオープン(FileOutputStream:true)
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile, true), OUTPUT_CHAR_SET);
            writer = new BufferedWriter(outputStreamWriter);

            while (rsSelectData.next()) {
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 1; i <= rsdCSVData.getColumnCount(); i++) {
                    if (rsSelectData.getString(i) != null) {
                        String data = new String(rsSelectData.getString(i).getBytes(OUTPUT_CHAR_SET), OUTPUT_CHAR_SET);
                        switch (i) {
                        case 1:  // Voucher Number A(30) Left justified,right fill blank
                            retVoucherList.add(data); // Voucher使用実績の「BLYNK連携区分」項目更新のため保存
                            data = String.format("%-30s", data);
                            break;
                        case 2:  // POS Register Number 9(6) Left justified,right fill blank
                            data = String.format("%-6s", data);
                            break;
                        case 3:  // POS Transaction Number 9(8) Left justified,right fill blank
                            data = String.format("%-8s", data);
                            break;
                        //2016.12.09 J.Endo FIVI対応(#3012) ADD(S)
                        case 4:  // Cashier ID 9(20) Left justified,right fill blank
                            data = String.format("%-20s", data);
                            break;
                        case 5:  // Store Code 9(6) Left justified,right fill blank
                            data = String.format("%-6s", data);
                            break;
                        //2016.12.09 J.Endo FIVI対応(#3012) ADD(E)
                        }
                        stringBuffer.append(data);
                    }
                }
                stringBuffer.append(LINE_FEED_CHAR);
                writer.write(stringBuffer.toString());

                outputCount++;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();

                    // オフラインVoucher使用実績は当日分が追記のため、処理終了時にゼロバイトの時のみファイル削除
                    if (dataFile.length() == 0) {
                        dataFile.delete();
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return outputCount;
    }

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    /**
     * インプットBeanを設定する
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new OfflineVoucherSiyouJisekiCreateDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
