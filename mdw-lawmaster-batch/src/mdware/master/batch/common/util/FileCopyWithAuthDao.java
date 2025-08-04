package mdware.master.batch.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import jcifs.smb.SmbFile;
import org.apache.commons.io.IOUtils;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.batch.common.util.FileCopyDaoInputBean;

/**
 *  <P>タイトル: 実績管理 共有フォルダへのファイルコピー処理</p>
 *  <P>説明:別サーバの共有フォルダに対してファイルコピーを行う処理です。</p>
 *  <p>システムコントロールのコピー先に以下の形式でパスを指定してください。<br>
 *  smb://[ユーザID]:[パスワード]@[サーバIP]/[共有ディレクトリのパス]<br>
 *  ※パスはスラッシュ区切りで指定</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @JUnit jp.co.vinculumjapan.mdware.autoorder.dao.batch.impl.FileCopyDaoInvoker
 *  @author A.Yoshida
 *  @version 1.0 2009/09/29 初版作成
 */
public class FileCopyWithAuthDao {

	/** ログ出力オブジェト */
	protected BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	String batchId = userLog.getJobId();
	String batchName = Jobs.getInstance().get(batchId).getJobName();
	
	// InputBean
    FileCopyDaoInputBean input_ = null;

    public void setInputObject(Object input) throws Exception {
        input_ = (FileCopyDaoInputBean) input;
    }

    public Object getOutputObject() throws Exception {
        return null;
    }

    public void copy() throws Exception {

        // コピー元ファイル
        String fromFilePath = input_.getFromDirId() + "/" + input_.getFromFileId();
        // コピー先ファイル
        String toFileName = input_.getToDirId() + "/" + input_.getToFileId();

        // コピー元ファイル存在チェック
        File fromFile = new File(fromFilePath);
        {
			batchLog.getLog().info(batchId, batchName, "ファイル存在チェックを開始します（コピー元）：" + fromFilePath);

            if (!fromFile.exists()) {
            	batchLog.getLog().warn(batchId, batchName, "ファイルが存在しません（コピー元）：" + fromFilePath);
                return;
            }

            if (!fromFile.isFile()) {
            	batchLog.getLog().error(batchId, batchName, "ファイル指定して下さい（コピー元）：" + fromFilePath);
                throw new Exception("ファイル指定して下さい（コピー元）：" + fromFilePath);
            }
            batchLog.getLog().info(batchId, batchName, "ファイル存在チェックを終了します（コピー元）");
        }

        // コピー先ディレクトリ存在チェック
        {
            String toDirPath = input_.getToDirId();
            batchLog.getLog().info(batchId, batchName, "コピー先ディレクトリ存在チェックを開始します（コピー先）：" + toDirPath);

            SmbFile toDir = new SmbFile(toDirPath);

            if (!toDir.exists()) {
                batchLog.getLog().error(batchId, batchName, "ディレクトリが存在しません（コピー先）：" + toDirPath);
                throw new Exception("ディレクトリが存在しません（コピー先）：" + toDirPath);
            }

            if (!toDir.isDirectory()) {
                batchLog.getLog().error(batchId, batchName, "ディレクトリを指定して下さい（コピー先）：" + toDirPath);
                throw new Exception("ディレクトリを指定して下さい（コピー先）：" + toDirPath);
            }
            batchLog.getLog().info(batchId, batchName, "コピー先ディレクトリ存在チェックを終了します（コピー先）");
        }

        // ファイルコピー実行(一度.tmpでファイル作成し、転送完了後.tmpを外す）
        {           
            SmbFile toFile = new SmbFile(toFileName);
            SmbFile toFileTmp = new SmbFile(toFileName + ".tmp");

            // .tmpファイル削除
            if (toFileTmp.exists()) {
                toFileTmp.delete();
            }
            
            // ファイルが存在していたらりネーム後、削除
            if (toFile.exists()) {
            	SmbFile renameFromFile = new SmbFile(toFileName);
            	SmbFile renameToFile = new SmbFile(renameFromFile + input_.getAddString());
            	renameFromFile.renameTo(renameToFile);
                batchLog.getLog().info(batchId, batchName, "既存ファイルリネームを終了します");
            }

            batchLog.getLog().info(batchId, batchName, "ファイルコピーを開始します：" + toFileName);
            // ファイルコピー
            FileInputStream input = new FileInputStream(fromFile);
            try {
                OutputStream output = (OutputStream) toFileTmp.getOutputStream();
                try {
                    IOUtils.copy(input, output);
                } finally {
                    IOUtils.closeQuietly(output);
                }
            } finally {
                IOUtils.closeQuietly(input);
            }

            // ファイルコピー後リネーム
        	toFileTmp.renameTo(toFile);
            batchLog.getLog().info(batchId, batchName, "ファイルコピーを終了します");
        }
    }
}
