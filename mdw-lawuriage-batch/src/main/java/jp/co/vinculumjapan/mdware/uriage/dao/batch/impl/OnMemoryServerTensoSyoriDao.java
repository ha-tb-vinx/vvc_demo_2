package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpPutDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.OnMemoryServerTensoSyoriDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: OnMemoryServerTensoSyoriDao クラス</p>
 *  <P>説明: オンメモリサーバー転送処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author CH.He
 *  @version 1.0 
 *  @Version 3.00 (2013.10.20) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応 ファイル送信処理対応
 */
public class OnMemoryServerTensoSyoriDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "オンメモリサーバー転送処理";
    // サーバアドレス
    private static final String FTP_ONMEMO_HOST = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_HOST);
    // FTPユーザ
    private static final String FTP_ONMEMO_LOGIN_NAME = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_LOGIN_NAME);
    // FTPパスワード
    private static final String FTP_ONMEMO_PASSWORD = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_PASSWORD);
    // FTPポート番号
    private static final String FTP_ONMEMO_PORT = FiResorceUtility.getPropertie(UriResorceKeyConstant.FTP_ONMEMO_PORT);
    // 送信元ディレクトリ
    private static final String PATH_SEND = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND);

    // インプットビーン
    private OnMemoryServerTensoSyoriDaoInputBean inputBean = null;

    /**
     * オンメモリサーバー転送処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        // リモートディレクトリ
        final String PATH_SET_POS_NCR = FiResorceUtility.getPropertie(inputBean.getRemoteFileDirPID());
        // ファイル名群取得
        final Map FILE_NAMES = FiResorceUtility.getPropertieMap(inputBean.getFileNamesPID());
        // 日付置換文字列取得
        final String REPLACE_DATE = FiResorceUtility.getPropertie(inputBean.getDateChgStrPID());

        // 送信ファイル名
        String strFileName = "";

        // ログ出力
        invoker.infoLog(strUserID + "　：　オンメモリサーバー転送処理を開始します。");

        Iterator itNames = FILE_NAMES.values().iterator();
        // 処理対象ファイル件数分（「退避：ファイル名群」の件数）繰り返し、FTP受信を行う
        while (itNames.hasNext()) {
            // 「退避：ファイル名群」の値を「退避：ファイル名」に一時退避する
            strFileName = (String) itNames.next();
            // 「退避：ファイル名」の【yyyymmdd】（日付文字列）を「退避：日付置換文字列」に文字列置換する
            strFileName = strFileName.replace(UriageCommonConstants.REPLACE_DATE, REPLACE_DATE);
            // 「退避：ファイル名」の【hhmmss】（時刻文字列）を≪InputBean≫.時刻置換文字列に文字列置換する
            strFileName = strFileName.replace(UriageCommonConstants.REPLACE_TIME, inputBean.getTimeChgStr());

            FtpPutDao FtpputDao = new FtpPutDao();

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
            inputBean.setDestDirName(PATH_SET_POS_NCR);
            // 送信元ディレクトリ
            inputBean.setSourceDirName(PATH_SEND);
            // 送信先ファイル名
            inputBean.setDestFileName(strFileName);
            // 送信元ファイル名
            inputBean.setSourceFileName(strFileName);

            // 入力ビーンビーンをセット
            FtpputDao.setInputObject(inputBean);

            // データアクセスオブジェクトのビジネスロジック実行 
            invoker.InvokeDao(FtpputDao);
            
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + strFileName + "の転送処理が成功しました。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　オンメモリサーバー転送処理を終了します。");

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
        inputBean = (OnMemoryServerTensoSyoriDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new OnMemoryServerTensoSyoriDao();
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
