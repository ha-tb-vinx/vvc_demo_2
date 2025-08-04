package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FTPDataCheckSyoriDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpCheckDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpGetDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: FTPDataCheckSyoriDao クラス</p>
 *  <P>説明: FTPデータチェック処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author R.Tei
 *  @version 1.0 
 */
public class FTPDataCheckSyoriDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "FTPデータチェック処理";
    /** サーバアドレス */
    private static final String FTP_POS_HOST = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_POS_HOST);
    /** FTPユーザ */
    private static final String FTP_POS_LOGIN_NAME = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_POS_LOGIN_NAME);
    /** FTPパスワード */
    private static final String FTP_POS_PASSWORD = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_POS_PASSWORD);
    /** FTPポート番号 */
    private static final String FTP_POS_PORT = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_POS_PORT);
    /** ローカルディレクトリ */
    private static final String PATH_RECV_POS_NCR = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_RECV_POS_NCR);
    /** インプットビーン */
    private FTPDataCheckSyoriDaoInputBean inputBean = null;

    /**
     * FTPデータチェック処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // リモートディレクトリ
        final String PATH_GET_POS_NCR = FiResorceUtility.getPropertie(inputBean.getRemoteFileDirPID());
        // ファイル名群取得
        final Map FILE_NAMES = FiResorceUtility.getPropertieMap(inputBean.getFileNamesPID());
        // 日付置換文字列取得
        final String REPLACE_DATE = FiResorceUtility.getPropertie(inputBean.getDateChgStrPID());

        // ユーザーID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;
        // ファイル名
        String strFileName = "";

        // ログ出力
        invoker.infoLog(strUserID + "　：　FTPデータチェック処理を開始します。");

        // ローカルディレクトリ の作成
        java.io.File folder = new java.io.File(PATH_RECV_POS_NCR);
        // フォルダーが存在しないの場合、新規作成
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Iterator itNames = FILE_NAMES.values().iterator();
        // 処理対象ファイル件数分（「退避：ファイル名群」の件数）繰り返し、FTP受信を行う
        while (itNames.hasNext()) {
            // 「退避：ファイル名群」の値を「退避：ファイル名」に一時退避する
            strFileName = (String) itNames.next();
            // 「退避：ファイル名」の【yyyymmdd】（日付文字列）を「退避：日付置換文字列」に文字列置換する
            strFileName = strFileName.replace(UriageCommonConstants.REPLACE_DATE, REPLACE_DATE);
            // 「退避：ファイル名」の【hhmmss】（時刻文字列）を≪InputBean≫.時刻置換文字列に文字列置換する
            strFileName = strFileName.replace(UriageCommonConstants.REPLACE_TIME, inputBean.getTimeChgStr());

            // FTPチェック処理DAOを実行する
            FtpCheckDao ftpCheckDao = new FtpCheckDao();

            // 入力ビーンインスタンス生成
            FtpCheckDaoInputBean inputBean = new FtpCheckDaoInputBean();
            // 項目転送内容を設定する
            // サーバアドレス
            inputBean.setHost(FTP_POS_HOST);
            // FTPユーザ
            inputBean.setLoginName(FTP_POS_LOGIN_NAME);
            // FTPパスワード
            inputBean.setPassword(FTP_POS_PASSWORD);
            // FTPポート番号
            inputBean.setPort(Integer.parseInt(FTP_POS_PORT));
            // リモートディレクトリ
            inputBean.setRemoteDirName(PATH_GET_POS_NCR);
            // ローカルディレクトリ
            inputBean.setLocalDirName(PATH_RECV_POS_NCR);
            // リモートファイル名
            inputBean.setRemoteFileName(strFileName);
            // ローカルファイル名
            inputBean.setLocalFileName(strFileName);

            // 入力ビーンビーンをセット
            ftpCheckDao.setInputObject(inputBean);

            // データアクセスオブジェクトのビジネスロジック実行 
            invoker.InvokeDao(ftpCheckDao);

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + strFileName + "の受信処理が成功しました。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　FTPデータチェック処理を終了します。");

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
        inputBean = (FTPDataCheckSyoriDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new FTPDataSyutokuSyoriDao();
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
