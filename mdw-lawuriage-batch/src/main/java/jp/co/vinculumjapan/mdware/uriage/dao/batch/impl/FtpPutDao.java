package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.FileInputStream;

import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpPutDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * <p>タイトル: FTP送信処理</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2007</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author A.Yoshida
 * @version 1.00 (2008/02/14) 初版作成
 */
public class FtpPutDao implements DaoIf {

    FtpPutDaoInputBean input_ = null;

    /**
     * データアクセス処理を実行します。
     */
    public void executeDataAccess(DaoInvokerIf invokerIf) throws Exception {

        String host = input_.getHost();
        if (host == null || host.equals("")) {
            host = FiResorceUtility.getPropertie("FTP_HOST");
        }

        int port = input_.getPort();
        if (port == 0) {
            port = Integer.parseInt(FiResorceUtility.getPropertie("FTP_PORT"));
        }

        String loginName = input_.getLoginName();
        if (loginName == null || loginName.equals("")) {
            loginName = FiResorceUtility.getPropertie("FTP_LOGIN_NAME");
        }

        String password = input_.getPassword();
        if (password == null || password.equals("")) {
            password = FiResorceUtility.getPropertie("FTP_PASSWORD");
        }

        String destDirName = input_.getDestDirName();
        if (destDirName == null || destDirName.equals("")) {
            destDirName = FiResorceUtility.getPropertie("FTP_DEST_DIR_NAME");
        }

        String sourceDirName = input_.getSourceDirName();
        if (sourceDirName == null || sourceDirName.equals("")) {
            sourceDirName = FiResorceUtility.getPropertie("FTP_SOURCE_DIR_NAME");
        }

        FTPClient fp = new FTPClient();
        FileInputStream is = null;
        try {
            // 接続処理
            fp.connect(host, port);
            if (!FTPReply.isPositiveCompletion(fp.getReplyCode())) {
                invokerIf.infoLog(invokerIf.getUserId() + "：FTP接続に失敗しました。");
                throw new DaoException(invokerIf.getUserId() + "：FTP接続に失敗しました。");
            }

            // ログイン処理
            if (fp.login(loginName, password) == false) {
                invokerIf.infoLog(invokerIf.getUserId() + "：ログインに失敗しました。");
                throw new DaoException(invokerIf.getUserId() + "：ログインに失敗しました。");
            }

            // ファイル送信前チェック
            String[] fileList = fp.listNames(destDirName);
            if (!FTPReply.isPositiveCompletion(fp.getReplyCode())) {
                invokerIf.infoLog(invokerIf.getUserId() + "：FTP送信先ディレクトリが存在しません。");
                throw new DaoException(invokerIf.getUserId() + "：FTP送信先ディレクトリが存在しません。");
            }
            if (fileList == null) {
                invokerIf.infoLog(invokerIf.getUserId() + "：FTP送信先ディレクトリが存在しません。");
                throw new DaoException(invokerIf.getUserId() + "：FTP送信先ディレクトリが存在しません。");
            }
            
            // ファイル送信
            is = new FileInputStream(sourceDirName + "/" + input_.getSourceFileName());
            if (!fp.storeFile(destDirName + "/" + input_.getDestFileName(), is)) {
                invokerIf.infoLog(invokerIf.getUserId() + "：FTPファイル送信処理に失敗しました。");
                throw new DaoException(invokerIf.getUserId() + "：FTPファイル送信処理に失敗しました。");
            }
            is.close();
        } catch (Exception e) {
            throw e;
        } finally {
            fp.disconnect();
            is.close();
        }

        invokerIf.infoLog(invokerIf.getUserId() + "：FTP送信処理が完了しました。");
    }

    public Object getOutputObject() {
        return null;
    }

    public void setInputObject(Object obj) {
        input_ = (FtpPutDaoInputBean) obj;
    }
}