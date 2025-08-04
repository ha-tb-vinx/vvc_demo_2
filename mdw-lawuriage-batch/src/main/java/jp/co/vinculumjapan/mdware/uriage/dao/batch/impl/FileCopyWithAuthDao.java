package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import jcifs.smb.SmbFile;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FileCopyDaoInputBean;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

import org.apache.commons.io.IOUtils;

/**
 *  <P>タイトル: FileCopyWithAuthDao クラス</p>
 *  <P>説明:別サーバの共有フォルダに対してファイルコピーを行う処理です。</p>
 *  <p>システムコントロールのコピー先に以下の形式でパスを指定してください。<br>
 *  smb://[ユーザID]:[パスワード]@[サーバIP]/[共有ディレクトリのパス]<br>
 *  ※パスはスラッシュ区切りで指定</p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @JUnit jp.co.vinculumjapan.mdware.autoorder.dao.batch.impl.FileCopyDaoInvoker
 *  @author S.Arakawa
 *  @version 3.00 (2013.10.24) S.Arakawa [CUS00057] ランドローム様 POSインターフェイス仕様変更対応　コピーファイル処理　新規作成
 *  @version 3.01 (2013.10.25) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №037
 *  @Version 3.02 (2014.04.22) Y.Tominaga[シス0201] ファイル名日時分秒追加
 */
public class FileCopyWithAuthDao implements DaoIf {
    
    // インプットビーン
    private FileCopyDaoInputBean input = null;

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
        
        // ユーザーID
        String strUserID = invoker.getUserId();
        
        // ファイル設定情報取得
        ResorceUtil ru = ResorceUtil.getInstance();

// 2014.04.22 Y.Tominaga[シス0201] ファイル名日時分秒追加 (S)
//        // コピー元ファイル
//        String fromFilePath = ru.getPropertie(input.getFromDirId())  + "/" + input.getFromFileId();
//        // コピー先ファイル
//        String toFileName = ru.getPropertie(input.getToDirId()) + "/" + input.getToFileId();

        // コピー元ファイルパス
        String fromFilePath = ru.getPropertie(input.getFromDirId())  + "/";
        // コピー先ファイルパス
        String toFileName = ru.getPropertie(input.getToDirId()) + "/";
        
        // コピー元ファイル存在フラグ
        boolean isFromFile = false;
        
        // コピー元ファイル存在チェック 日付つきのファイル名なので、部分一致を行う
        File fromFile = new File(ru.getPropertie(input.getFromDirId()));
        // ディレクトリ内のファイル名を取得
        String[] fromFileName = fromFile.list();
        
        {
            invoker.infoLog(strUserID + "　：　ファイル存在チェックを開始します（コピー元）：" + fromFilePath);

            // ディレクトリ内のファイルがインプットファイル名から始まるかチェック
            for (String fName : fromFileName) {
                if (fName.startsWith(input.getFromFileId())) {
                    // 一致すれば真
                    isFromFile = true;
                    
                    // ファイル名をセットする
                    fromFilePath = fromFilePath + fName;
                    toFileName = toFileName + fName;
                    break;
                }
            }
            
            if (!isFromFile) {
                invoker.errorLog(strUserID + "　：　ファイルが存在しません（コピー元）：" + fromFilePath);
                throw new DaoException(strUserID + "　：　ファイルが存在しません（コピー元）：" + fromFilePath);
            }
            invoker.infoLog(strUserID + "　：　ファイル存在チェックを終了します（コピー元）");
        }
        
        // ファイルが存在するので、コピー元ファイル定義
        File fromFileFullPath = new File(fromFilePath);
// 2014.04.22 Y.Tominaga[シス0201] ファイル名日時分秒追加 (E)

        // コピー先ディレクトリ存在チェック
        {
            String toDirPath = ru.getPropertie(input.getToDirId());
            invoker.infoLog(strUserID + "　：　コピー先ディレクトリ存在チェックを開始します（コピー先）：" + toDirPath);

            SmbFile toDir = new SmbFile(toDirPath);

            if (!toDir.exists()) {
                invoker.errorLog(strUserID + "　：　ディレクトリが存在しません（コピー先）：" + toDirPath);
                throw new DaoException(strUserID + "　：　ディレクトリが存在しません（コピー先）：" + toDirPath);
            }

            if (!toDir.isDirectory()) {
                invoker.errorLog(strUserID + "　：　ディレクトリを指定して下さい（コピー先）：" + toDirPath);
                throw new DaoException(strUserID + "　：　ディレクトリを指定して下さい（コピー先）：" + toDirPath);
            }
            invoker.infoLog(strUserID + "　：　コピー先ディレクトリ存在チェックを終了します（コピー先）");
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
                String renameFile = ru.getPropertie(input.getToDirId()) + "\\" + input.getToFileId() + ResorceUtil.getInstance().getServerTime().substring(2);
                invoker.infoLog(strUserID + "　：　既存ファイルリネームを開始します：" + renameFile);
                SmbFile backUpFile = new SmbFile(renameFile);
                toFile.copyTo(backUpFile);

                toFile.delete();
                invoker.infoLog(strUserID + "　：　既存ファイルリネームを終了します");
            }

            invoker.infoLog(strUserID + "　：　ファイルコピーを開始します：" + toFileName);
            // ファイルコピー
            FileInputStream input = new FileInputStream(fromFileFullPath);
            try {
                OutputStream output = toFileTmp.getOutputStream();
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
            invoker.infoLog(strUserID + "　：　ファイルコピーを終了します");
        }
    }
    
    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {
        
        this.input = (FileCopyDaoInputBean) input;
        
    }
    
    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {
        
        return null;
    
    }
}
