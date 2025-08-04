package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.FileOutputStream;

import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FtpCheckDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * <p>タイトル: FTP受信処理</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2007</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author R.Tei
 * @version 1.00 (2009/06/29) 初版作成
 */
public class FtpCheckDao implements DaoIf {

    FtpCheckDaoInputBean input_ = null;

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

        String remoteDirName = input_.getRemoteDirName();
        if (remoteDirName == null || remoteDirName.equals("")) {
            remoteDirName = FiResorceUtility.getPropertie("FTP_REMOTE_DIR_NAME");
        }

        String localDirName = input_.getLocalDirName();
        if (localDirName == null || localDirName.equals("")) {
            localDirName = FiResorceUtility.getPropertie("FTP_LOCAL_DIR_NAME");
        }

        FTPClient fp = new FTPClient();
        FileOutputStream os = null;
        try {
            // 接続処理
            fp.connect(host, port);
            if (!FTPReply.isPositiveCompletion(fp.getReplyCode())) {
                throw new DaoException(invokerIf.getUserId() + "：FTP接続に失敗しました。");
            }

            // ログイン処理
            if (fp.login(loginName, password) == false) {
                throw new DaoException(invokerIf.getUserId() + "：ログインに失敗しました。");
            }

            // ファイル受信
            os = new FileOutputStream(localDirName + "/" + input_.getLocalFileName());
            fp.retrieveFile(remoteDirName + "/" + input_.getRemoteFileName(), os);
            os.close();

            if (fp.getReplyCode() != FTPReply.CODE_226) {
                throw new DaoException(invokerIf.getUserId() + "：ファイル受信に失敗しました。");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            fp.disconnect();
            os.close();
        }

        invokerIf.infoLog(invokerIf.getUserId() + "：FTP受信処理が完了しました。");
    }

    public Object getOutputObject() {
        return null;
    }

    public void setInputObject(Object obj) {
        input_ = (FtpCheckDaoInputBean) obj;
    }
}