package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.common.util.DateChanger;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
//import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpPutDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.KyakusuJisekiDataCreateDaoInputBean;
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
 *  <P>タイトル: KyakusuJisekiDataCreateDao クラス</p>
 *  <P>説明: 客数実績データ作成処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 3.00 (2013.10.03) T.ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 *  @Version 3.01 (2013.10.20) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 ファイル送信処理対応
 */
public class KyakusuJisekiDataCreateDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "客数実績データ作成処理";
    // バッチID
    private static final String BATCH_ID = "URIB031030";
    // 「/」
    private static final String SOLIDUS = "/";
    // 削除IF客数実績ワークSQL文用定数
    private static final String DEL_IF_KYAKUSU = "deleteIFKyakusu";
    // 登録IF客数実績ワークSQL文用定数(DPT精算売上ワーク)
    private static final String INS_IF_KYAKUSU = "createIFKyakusu";
    // 登録IF客数実績ワークSQL文用定数(店別DPT売上データ)
    private static final String INS_IF_KYAKUSU_JISEKI = "createIFKyakuJiseki";
    // 検索IF客数実績ワークSQL文用定数
    private static final String SEL_IF_KYAKUSU = "selectIFKyakusu";
    // 検索会計年月SQL文用定数
    private static final String SEL_START_DT = "selectStartDt";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
//    // サーバアドレス
//    private static final String FTP_ONMEMO_HOST = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_HOST);
//    // FTPユーザ
//    private static final String FTP_ONMEMO_LOGIN_NAME = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_LOGIN_NAME);
//    // FTPパスワード
//    private static final String FTP_ONMEMO_PASSWORD = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_PASSWORD);
//    // FTPポート番号
//    private static final String FTP_ONMEMO_PORT = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_PORT);
//    // 送信先ディレクトリ
//    private static final String PATH_SEND_KYAKUSU_SYOHIN = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND_KYAKUSU_SYOHIN);
    // 送信元ディレクトリ
    private static final String PATH_SEND = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND);
    // 開始年月日
    private static final String START_DT = "START_DT";

    // 入力ビーン
    private KyakusuJisekiDataCreateDaoInputBean inputBean = null;

    /**
     * 客数実績データ作成処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // IF客数実績ワーク削除した件数
        int intDeleteWKCount = 0;
        // IF客数実績ワーク追加した件数
        int intCreateWKCount = 0;
        // 客数実績データ追加件数
        int intCreateCount = 0;
        // 開始年月日
        String strStartDt = "";
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // CSV出力用
        CsvExportPreparedDao csvExportDao = new CsvExportPreparedDao();
        CsvExportPreparedInputBean csvInputBean = new CsvExportPreparedInputBean();

        // ログ出力
        invoker.infoLog(strUserID + "　：　客数実績データ作成処理を開始します。");

        // 該当会計年月抽出
        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_START_DT);
        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // SQL実行
        ResultSet resultSet = preparedStatementEx.executeQuery();

        if (resultSet.next()) {
            strStartDt = resultSet.getString(START_DT);
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　IF客数実績ワーク削除処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(DEL_IF_KYAKUSU);
        preparedStatementEx.setString(1, COMP_CD);

        // SQLを実行して、｢IF客数実績ワーク」のデータを全件削除する
        intDeleteWKCount = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intDeleteWKCount + "件のIF客数実績ワークを削除しました。");
        invoker.infoLog(strUserID + "　：　IF客数実績ワーク削除処理を終了します。");
        invoker.infoLog(strUserID + "　：　IF客数実績ワーク追加処理を開始します。");

        // 店別DPT売上データから当月01日以降、ﾊﾞｯﾁ日付-1までの「IF客数実績ワーク」データ作成
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_IF_KYAKUSU_JISEKI);
        preparedStatementEx.setString(1, DBSERVER_DT);
        preparedStatementEx.setString(2, BATCH_ID);
        preparedStatementEx.setString(3, DBSERVER_DT);
        preparedStatementEx.setString(4, BATCH_ID);
        preparedStatementEx.setString(5, DBSERVER_DT);
        preparedStatementEx.setString(6, COMP_CD);
        preparedStatementEx.setString(7, strStartDt);
        preparedStatementEx.setString(8, DateChanger.addDate(BATCH_DT, -1));

        // SQLを実行する
        intCreateWKCount = intCreateWKCount + preparedStatementEx.executeUpdate();

        // 「IF客数実績ワーク」本日処理分データ作成
        preparedStatementEx = invoker.getDataBase().prepareStatement(INS_IF_KYAKUSU);
        preparedStatementEx.setString(1, DBSERVER_DT);
        preparedStatementEx.setString(2, BATCH_ID);
        preparedStatementEx.setString(3, DBSERVER_DT);
        preparedStatementEx.setString(4, BATCH_ID);
        preparedStatementEx.setString(5, DBSERVER_DT);
        preparedStatementEx.setString(6, COMP_CD);
        preparedStatementEx.setString(7, BATCH_DT);

        // SQLを実行する
        intCreateWKCount = intCreateWKCount + preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateWKCount + "件のIF客数実績ワークを追加しました。");
        invoker.infoLog(strUserID + "　：　IF客数実績ワーク追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　客数実績データ:ファイル作成処理を開始します。");

        // CSVファイル名を取得する
        String strCSVFileName = inputBean.getcsvFileName();

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_IF_KYAKUSU);
        preparedStatementEx.setString(1, COMP_CD);

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
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件の客数実績データを追加しました。");
        invoker.infoLog(strUserID + "　：　客数実績データ:ファイル作成処理を終了します。");

//        // 商品情報指定フォルダにFTP送信
//        sendFileToFtp(invoker, strCSVFileName);

        // ログ出力
        invoker.infoLog(strUserID + "　：　客数実績データ作成処理を終了します。");

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
//        inputBean.setDestDirName(PATH_SEND_KYAKUSU_SYOHIN);
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
        inputBean = (KyakusuJisekiDataCreateDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new KyakusuJisekiDataCreateDao();
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
