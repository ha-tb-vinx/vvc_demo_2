package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: ShinTanpinMeiSabunFileSakuseiDao クラス</p>
 *  <P>説明: 新単品明細ログ差分ファイル作成処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author XP.Chen
 *  @version 1.01 2015/06/18 Sou 英語化対応
 */
public class ShinTanpinMeiSabunFileSakuseiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "新単品明細ログ差分ファイル作成処理";
    // 「/」
    private static final String SOLIDUS = "/";
    // 囲み文字(ダブルクォーテーション)
    private static final String ENCLOSE_CHAR = "\"";
    // 改行文字(CRLF)
    private static final String LINE_FEED_CHAR = "\r\n";
    // DB文字コード
    private static final String DB_CHAR_SET = "Cp943C";
    // 出力ファイル文字コード
    private static final String OUTPUT_CHAR_SET = "UTF-8";
    // 時分秒
    private static final String NINE = "9";
    // 検索新単品明細ログ差分SQL文用定数
    private static final String SEL_TANPIN_MEI_SABUN = "selectTanpinMeiSabun";
    // 検索新単品明細ログ差分SQL文用定数
    private static final String DEL_TANPIN_MEI_SABUN = "deleteTanpinMeiSabun";

    // ファイル保存ディレクトリ
    private static final String PATH_SEND = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND);

    /**
     * 新単品明細ログ差分ファイル作成処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 新単品明細ログ差分追加件数
        int intCreateCount = 0;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;
        ResultSet resultSet = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　新単品明細ログ差分ファイル作成を開始します。");
        invoker.infoLog(strUserID + "　：　新単品明細ログ差分ファイル作成処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_TANPIN_MEI_SABUN);

        // SQL文を実行する
        resultSet = preparedStatementEx.executeQuery();

        // データ生成件数をセットする
        intCreateCount = csvExport(resultSet, FiResorceUtility.getPropertie(UriResorceKeyConstant.FILE_TANPINMEI_SABUN));

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件の新単品明細ログ差分を追加しました。");
        invoker.infoLog(strUserID + "　：　新単品明細ログ差分ファイル作成処理を終了します。");


        invoker.infoLog(strUserID + "　：　新単品明細ログ差分ワーク削除処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(DEL_TANPIN_MEI_SABUN);

        preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　新単品明細ログ差分ワークを全件削除しました。");
        invoker.infoLog(strUserID + "　：　新単品明細ログ差分ワーク削除処理を終了します。");
        invoker.infoLog(strUserID + "　：　新単品明細ログ差分ファイル作成を終了します。");

    }

    /**
     * ファイル出力処理
     * @param rsSelectData
     * @param strFileName
     * @return 出力件数
     * @throws Exception
     */
    private int csvExport(ResultSet rsSelectData, String strFileName) throws Exception {

        // 出力データ数
        int intCount = 0;
        // CSV出力データ
        ResultSetMetaData rsdCSVData = null;
        // CSV出力
        BufferedWriter writer = null;
        File dataFile = new File(PATH_SEND + SOLIDUS + strFileName);

        // 出力データはResultSetMetaDataへ変換する
        rsdCSVData = rsSelectData.getMetaData();

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile), OUTPUT_CHAR_SET);
            writer = new BufferedWriter(outputStreamWriter);

            while (rsSelectData.next()) {
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 1; i <= rsdCSVData.getColumnCount(); i++) {
                    if (rsSelectData.getString(i) != null) {
                        // 2013.10.15 [CUS00057] POSインターフェイス仕様変更対応（S)
                        String data = new String(rsSelectData.getString(i).getBytes(OUTPUT_CHAR_SET), OUTPUT_CHAR_SET);
                        // 2013.10.15 [CUS00057] POSインターフェイス仕様変更対応（E)
                        //囲み文字がデータに含まれる場合は削除
                        data = data.replaceAll(ENCLOSE_CHAR, "");
                        stringBuffer.append(data);
                    }
                }
                stringBuffer.append(LINE_FEED_CHAR);
                writer.write(stringBuffer.toString());

                intCount++;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return intCount;
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
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {

    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new ShinTanpinMeiSabunFileSakuseiDao();
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
