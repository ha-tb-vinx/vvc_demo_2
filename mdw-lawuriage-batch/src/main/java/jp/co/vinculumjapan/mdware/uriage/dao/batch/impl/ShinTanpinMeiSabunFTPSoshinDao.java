package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpPutDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiFileUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: ShinTanpinMeiSabunFTPSoshinDao クラス</p>
 *  <P>説明: 新単品明細ログ（差分）FTP送信処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author XP.Chen
 *  @version 1.0 
 */
public class ShinTanpinMeiSabunFTPSoshinDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "新単品明細ログ（差分）FTP送信処理";

    // 新単品明細ログ差分ファイル名
    private static final String strFileName = FiResorceUtility.getPropertie(UriResorceKeyConstant.FILE_TANPINMEI_SABUN);

    // サーバアドレス
    private static final String FTP_ONMEMO_HOST = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_HOST);
    // FTPユーザ
    private static final String FTP_ONMEMO_LOGIN_NAME = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_LOGIN_NAME);
    // FTPパスワード
    private static final String FTP_ONMEMO_PASSWORD = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_PASSWORD);
    // FTPポート番号
    private static final String FTP_ONMEMO_PORT = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_PORT);
    // 送信先ディレクトリ
    private static final String PATH_SEND_TANPINMEI_SABUN_ONMEMO = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND_TANPINMEI_SABUN_ONMEMO);
    // 送信元ディレクトリ
    private static final String PATH_SEND = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND);

    /**
     * 新単品明細ログ（差分）FTP送信処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　新単品明細ログ（差分）FTP送信処理を開始します。");

        // FTPファイルの存在をチェックする
        if (!FiFileUtility.pathFileExists(PATH_SEND, strFileName)) {
            // CSVファイルが無い場合、ログ出力
            invoker.warnLog(strUserID + "　：　新単品明細ログ（差分）ファイル(" + strFileName + ")が存在しません。");
            throw new DaoException("FTP送信処理にて例外エラー発生。");
        }

        // 商品情報指定フォルダにFTP送信       
        FtpPutDao ftpPutDao = new FtpPutDao();

        // 入力ビーンインスタンス生成
        FtpPutDaoInputBean inputBean = new FtpPutDaoInputBean();

        // 項目転送内容を設定する
        // サーバアドレス
        inputBean.setHost(FTP_ONMEMO_HOST);
        // FTPユーザ
        inputBean.setLoginName(FTP_ONMEMO_LOGIN_NAME);
        // FTPパスワード
        inputBean.setPassword(FTP_ONMEMO_PASSWORD);
        // FTPポート番号
        inputBean.setPort(Integer.parseInt(FTP_ONMEMO_PORT));
        // 送信先ディレクトリ
        inputBean.setDestDirName(PATH_SEND_TANPINMEI_SABUN_ONMEMO);
        // 送信先ファイル名
        inputBean.setDestFileName(strFileName);
        // 送信元ディレクトリ
        inputBean.setSourceDirName(PATH_SEND);
        // 送信元ファイル名
        inputBean.setSourceFileName(strFileName);

        // 入力ビーンをセット
        ftpPutDao.setInputObject(inputBean);

        // データアクセスオブジェクトのビジネスロジック実行 
        invoker.InvokeDao(ftpPutDao);

        // ログ出力
        invoker.infoLog(strUserID + "　：　新単品明細ログ（差分）FTP送信処理を終了します。");

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
            DaoIf dao = new ShinTanpinMeiSabunFTPSoshinDao();
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
