package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpPutDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: OnMemoryIfPosNejogeDataSendDao クラス</p>
 *  <P>説明: URIB061070 POS値上下データFTP送信処理</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author A.Shinsaka
 *  @version 1.0 
 */
public class OnMemoryIfPosNejogeDataSendDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "オンメモリ処理済データFTP送信処理";
    // サーバアドレス
    private static final String FTP_KIKAN_HOST = FiResorceUtility.getPropertie("FTP_KIKAN_HOST");
    // FTPユーザ
    private static final String FTP_KIKAN_LOGIN_NAME = FiResorceUtility.getPropertie("FTP_KIKAN_LOGIN_NAME");
    // FTPパスワード
    private static final String FTP_KIKAN_PASSWORD = FiResorceUtility.getPropertie("FTP_KIKAN_PASSWORD");
    // FTPポート番号
    private static final String FTP_KIKAN_PORT = FiResorceUtility.getPropertie("FTP_KIKAN_PORT");
    // 送信元ディレクトリ
    private static final String PATH_ONMEMO_SEND_KIKAN = FiResorceUtility.getPropertie("PATH_ONMEMO_SEND_KIKAN");
    // 仕入用値上下データ格納ディレクトリパス
    private static final String PATH_SEND_NEJYOGE_SIIRE = FiResorceUtility.getPropertie("PATH_SEND_NEJYOGE_SIIRE");
    // POS値上下データ
    private static final String FILE_DT_POS_NEJOGEE = "IF_DT_POS_NEJOGE.csv";

    /** 起動パラメタ */
    protected String strParameter = null;

    /**
     * オンメモリ処理済データFTP送信処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　POS値上下データFTP送信処理を開始します。");
        
        // POS値上下データをFTP送信
        sendFileToFtp(invoker, FILE_DT_POS_NEJOGEE, PATH_SEND_NEJYOGE_SIIRE);

        // ログ出力
        invoker.infoLog(strUserID + "　：　POS値上下データFTP送信処理を終了します。");
    }

    /**
     * 指定フォルダにFTP送信
     * @param DaoInvokerIf invoker
     * @param String strCSVFileName
     * @param String strDestDirName
     */
    private static void sendFileToFtp(DaoInvokerIf invoker, String strCSVFileName, String strDestDirName) throws Exception {

        FtpPutDao ftpPutDao = new FtpPutDao();

        // 入力ビーンインスタンス生成
        FtpPutDaoInputBean inputBean = new FtpPutDaoInputBean();

        // 項目転送内容を設定する
        // サーバアドレス
        inputBean.setHost(FTP_KIKAN_HOST);
        // FTPユーザ
        inputBean.setLoginName(FTP_KIKAN_LOGIN_NAME);
        // FTPパスワード
        inputBean.setPassword(FTP_KIKAN_PASSWORD);
        // FTPポート番号
        inputBean.setPort(Integer.parseInt(FTP_KIKAN_PORT));
        // 送信先ディレクトリ
        inputBean.setDestDirName(strDestDirName);
        // 送信先ファイル名
        inputBean.setDestFileName(strCSVFileName);
        // 送信元ディレクトリ
        inputBean.setSourceDirName(PATH_ONMEMO_SEND_KIKAN);
        // 送信元ファイル名
        inputBean.setSourceFileName(strCSVFileName);

        // 入力ビーンをセット
        ftpPutDao.setInputObject(inputBean);

        // データアクセスオブジェクトのビジネスロジック実行 
        invoker.InvokeDao(ftpPutDao);
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
        if (input != null && !input.toString().equals("")) {
            strParameter = (String) input;
        }
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new OnMemoryIfPosNejogeDataSendDao();
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
