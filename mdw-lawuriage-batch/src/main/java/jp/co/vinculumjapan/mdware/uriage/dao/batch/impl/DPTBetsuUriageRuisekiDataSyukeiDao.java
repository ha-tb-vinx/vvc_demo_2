package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.DPTBetsuUriageRuisekiDataSyukeiDaoInputBean;
//import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpPutDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.impl.CsvExportPreparedDao;
import jp.co.vinculumjapan.mdware.uriage.dao.input.CsvExportPreparedInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: DPTBetsuUriageRuisekiDataSyukeiDao クラス</p>
 *  <P>説明: DPT別売上累積データ集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 
 */
public class DPTBetsuUriageRuisekiDataSyukeiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "DPT別売上累積データ集計処理";
    // 「/」
    private static final String SOLIDUS = "/";
    // 検索DPT売上累積SQL文用定数
    private static final String SEL_DPT_URI_RUISEKI = "selectDPTUriRuiseki";
//    // サーバアドレス
//    private static final String FTP_ONMEMO_HOST = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_HOST);
//    // FTPユーザ
//    private static final String FTP_ONMEMO_LOGIN_NAME = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_LOGIN_NAME);
//    // FTPパスワード
//    private static final String FTP_ONMEMO_PASSWORD = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_PASSWORD);
//    // FTPポート番号
//    private static final String FTP_ONMEMO_PORT = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_PORT);
//    // 送信先ディレクトリ
//    private static final String PATH_SEND_URIAGE_EIGYOU = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND_URIAGE_EIGYOU);
    // 送信元ディレクトリ
    private static final String PATH_SEND = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND);

    // 入力ビーン
    private DPTBetsuUriageRuisekiDataSyukeiDaoInputBean inputBean = null;

    /**
     * DPT別売上累積データ集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // DPT別売上累積データ追加件数
        int intCreateCount = 0;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // CSVファイル名を取得する
        String strCSVFileName = inputBean.getcsvFileName();

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // CSV出力用
        CsvExportPreparedDao csvExportDao = new CsvExportPreparedDao();
        CsvExportPreparedInputBean csvInputBean = new CsvExportPreparedInputBean();

        // ログ出力
        invoker.infoLog(strUserID + "　：　DPT別売上累積データ集計処理を開始します。");
        invoker.infoLog(strUserID + "　：　DPT別売上累積データ:ファイル作成処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_DPT_URI_RUISEKI);

        // CSV出力用SQL文をセット
        csvInputBean.setSqlStatement(preparedStatementEx);
        // CSV出力パスをセット
        csvInputBean.setExportFilePath(PATH_SEND + SOLIDUS + strCSVFileName);

        // 入力ビーンをセット
        csvExportDao.setInputObject(csvInputBean);

        invoker.InvokeDao(csvExportDao);

        // データ生成件数をセットする
        intCreateCount = Integer.parseInt(csvExportDao.getOutputObject().toString());

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件のDPT別売上累積データを追加しました。");
        invoker.infoLog(strUserID + "　：　DPT別売上累積データ:ファイル作成処理を終了します。");

//        // 商品情報指定フォルダにFTP送信
//        sendFileToFtp(invoker, strCSVFileName);

        // ログ出力
        invoker.infoLog(strUserID + "　：　DPT別売上累積データ集計処理を終了します。");

    }

//    /**
//     * 商品情報指定フォルダにFTP送信
//     * @param DaoInvokerIf invoker
//     * @param String strCSVFileName
//     */
//    private static void sendFileToFtp(DaoInvokerIf invoker, String strCSVFileName) throws Exception {
//
//        FtpPutDao ftpPutDao = new FtpPutDao();
//
//        // 入力ビーンインスタンス生成
//        FtpPutDaoInputBean inputBean = new FtpPutDaoInputBean();
//
//        // 項目転送内容を設定する
//        // サーバアドレス
//        inputBean.setHost(FTP_ONMEMO_HOST);
//        // FTPユーザ
//        inputBean.setLoginName(FTP_ONMEMO_LOGIN_NAME);
//        // FTPパスワード
//        inputBean.setPassword(FTP_ONMEMO_PASSWORD);
//        // FTPポート番号
//        inputBean.setPort(Integer.parseInt(FTP_ONMEMO_PORT));
//        // 送信先ディレクトリ
//        inputBean.setDestDirName(PATH_SEND_URIAGE_EIGYOU);
//        // 送信先ファイル名
//        inputBean.setDestFileName(strCSVFileName);
//        // 送信元ディレクトリ
//        inputBean.setSourceDirName(PATH_SEND);
//        // 送信元ファイル名
//        inputBean.setSourceFileName(strCSVFileName);
//
//        // 入力ビーンをセット
//        ftpPutDao.setInputObject(inputBean);
//
//        // データアクセスオブジェクトのビジネスロジック実行 
//        invoker.InvokeDao(ftpPutDao);
//
//    }

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
        inputBean = (DPTBetsuUriageRuisekiDataSyukeiDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new DPTBetsuUriageRuisekiDataSyukeiDao();
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
