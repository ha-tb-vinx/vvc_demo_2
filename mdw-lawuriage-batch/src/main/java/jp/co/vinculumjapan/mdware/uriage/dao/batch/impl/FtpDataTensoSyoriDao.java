package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import org.apache.commons.lang.StringUtils;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpDataTensoSyoriDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpPutDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * 
 * <p>タイトル: FtpDataTensoSyoriDao.java クラス</p>
 * <p>説明　: FTP転送処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.20) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 ファイル送信処理対応
 *
 */
public class FtpDataTensoSyoriDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "FTPデータ転送処理";
    
    private static final String ROOT = "/";

    /** インプットビーン */
    private FtpDataTensoSyoriDaoInputBean input = null;

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {


        // ユーザーID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + BATCH_NAME + "を開始します。");

        // サーバアドレス
        String ftpHost = FiResorceUtility.getPropertie(input.getFtpHost());
        // FTPユーザ
        String ftpLoginName = FiResorceUtility.getPropertie(input.getFtpLoginName());
        // FTPパスワード
        String ftpPassword = FiResorceUtility.getPropertie(input.getFtpPassword());
        // FTPポート番号
        String ftpPort = FiResorceUtility.getPropertie(input.getFtpPort());
        // 送信先ディレクトリ
        String pathSendToDir = "";
        if (StringUtils.isEmpty(input.getPathSendToDir())) {
            pathSendToDir = ROOT;
        } else {
            pathSendToDir = FiResorceUtility.getPropertie(input.getPathSendToDir());
        }
        // 送信元ディレクトリ
        String pathSendFromDir = FiResorceUtility.getPropertie(input.getPathSendFromDir());
        // 送信ファイル名
        String fileName = input.getFileName();

        FtpPutDao ftpPutDao = new FtpPutDao();

        // 入力ビーンインスタンス生成
        FtpPutDaoInputBean inputBean = new FtpPutDaoInputBean();

        // 項目転送内容を設定する
        // サーバアドレス
        inputBean.setHost(ftpHost);
        // FTPユーザ
        inputBean.setLoginName(ftpLoginName);
        // FTPパスワード
        inputBean.setPassword(ftpPassword);
        // FTPポート番号
        inputBean.setPort(Integer.parseInt(ftpPort));
        // 送信先ディレクトリ
        inputBean.setDestDirName(pathSendToDir);
        // 送信先ファイル名
        inputBean.setDestFileName(fileName);
        // 送信元ディレクトリ
        inputBean.setSourceDirName(pathSendFromDir);
        // 送信元ファイル名
        inputBean.setSourceFileName(fileName);

        // 入力ビーンをセット
        ftpPutDao.setInputObject(inputBean);

        // データアクセスオブジェクトのビジネスロジック実行 
        invoker.InvokeDao(ftpPutDao);

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + BATCH_NAME + "を終了します。");
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object input FtpDataTensoSyoriDaoInputBean型オブジェクト
     */
    public void setInputObject(Object input) throws Exception {
        this.input = (FtpDataTensoSyoriDaoInputBean) input;

    }


    /**
     * アウトプットBeanを取得する
     * 
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }


    public static void main(String[] arg) {
        try {
            DaoIf dao = new FtpDataTensoSyoriDao();
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
