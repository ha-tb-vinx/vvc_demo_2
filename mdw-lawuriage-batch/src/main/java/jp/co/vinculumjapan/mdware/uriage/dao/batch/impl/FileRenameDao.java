package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.FileRenameDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 *  <P>タイトル: 売上管理 ファイルリネーム処理</p>
 *  <P>説明:売上管理のファイルリネームを行う処理です。</p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author Y.Tominaga
 *  @version 3.00 2013.10.18 ランドローム様対応　ファイルリネーム処理　初版作成
 */
public class FileRenameDao implements DaoIf {
    
    // InputBean
    FileRenameDaoInputBean input_ = null;

    // システム日付を先に取得しておく
    private String sysTime = ResorceUtil.getInstance().getServerTime();

    public void executeDataAccess(DaoInvokerIf daoInvoker) throws Exception {
        
        // リネームファイル名群を取得する。
        final Map fileNameMap = FiResorceUtility.getPropertieMap(input_.getRenameFile()); 
        
        Iterator it = fileNameMap.values().iterator();
        
        while(it.hasNext()) {
            
            String fileName = it.next().toString();
            
            // リネーム前ファイルのフルパス
            String fromFileName = FiResorceUtility.getPropertie(input_.getRenameDirPath()) + "/" + fileName;
            
            // リネーム後ファイルのフルパス
            String toFileName = FiResorceUtility.getPropertie(input_.getRenameDirPath()) + "/" + fileRenameName(fileName);
            
            // 開始ログ出力
            daoInvoker.infoLog(daoInvoker.getUserId() + "： ファイルリネーム処理を開始します。：" + fromFileName);
            
            // リネーム前ファイル存在チェック
            File fromFile = new File(fromFileName);
            {
                daoInvoker.infoLog(daoInvoker.getUserId() + "：ファイル存在チェックを開始します（リネーム前）：" + fromFileName);
    
                if (!fromFile.exists()) {
                    daoInvoker.errorLog(daoInvoker.getUserId() + "：ファイルが存在しません（リネーム前）：" + fromFileName);
                    throw new DaoException(daoInvoker.getUserId() + "：ファイルが存在しません（リネーム前）：" + fromFileName);
                }
    
                if (!fromFile.isFile()) {
                    daoInvoker.errorLog(daoInvoker.getUserId() + "：ファイル指定して下さい（リネーム前）：" + fromFileName);
                    throw new DaoException(daoInvoker.getUserId() + "：ファイル指定して下さい（リネーム前）：" + fromFileName);
                }
                daoInvoker.infoLog(daoInvoker.getUserId() + "：ファイル存在チェックを終了します（リネーム前）");
            }
    
            // ファイルリネーム実行
            {           
    
                File toFile = new File(toFileName);
    
                // リネーム処理を行う
                if (!fromFile.renameTo(toFile)) {
                    daoInvoker.errorLog(daoInvoker.getUserId() + "：ファイルのリネームに失敗しました");
                    throw new DaoException(daoInvoker.getUserId() + "：ファイルのリネームに失敗しました");
                }
            }
            
            // 終了ログ出力
            daoInvoker.infoLog(daoInvoker.getUserId() + "： ファイルリネーム処理を終了します。：" + fromFileName);
            
        }
        
        /** ファイルバックアップ処理
         *  バックアップを行うファイル名をzip圧縮で保存する。zip名にはシステム日付を付ける。
         *  ※ 今回の処理では使用しないので、コメント化対応*/
//        // リネーム後のファイルのバックアップファイルを取得する。
//        {
//            
//            // 開始ログ出力
//            daoInvoker.infoLog(daoInvoker.getUserId() + "： ファイルバックアップ処理を開始します。");
//            
//            // バックアップファイル名を指定　リネーム後のファイル名
//            String backupFileName = fileBackUpName(input_.getRenameFile()) + ".zip";
//            
//            // バックアップディレクトリパス
//            String backupFileDirPath = FiResorceUtility.getPropertie(input_.getBackupFileDirPath());
//            
//            // バックアップファイルのフルパス
//            String backupFilePath = new File(backupFileDirPath + "/" + backupFileName).getAbsolutePath();
//            
//            Zip zip = null;
//            
//            if (!new File(backupFileDirPath).exists()) {
//                throw new DaoException("バックアップ保管用ディレクトリが存在しません。");
//            }
//            
//            zip = new Zip();
//            zip.setSrcFilename(toFileName);
//            zip.encodeZip(backupFilePath);
//            // 終了ログ出力
//            daoInvoker.infoLog(daoInvoker.getUserId() + "：ファイルバックアップ処理を終了します。");
//        }
    }
    

//    /**
//     * ファイル名にシステム日付をセットする。
//     * 例　test.txt → testYYYYMMDDHHMMSS.txt
//     * @param toFileId
//     * @return
//     */
//    private String fileBackUpName(String fileName) {
//        
//        String[] name = fileName.split("\\.");
//        
//        return name[0].trim() + sysTime + name[1].trim();
//    }

    /**
     * ファイル名にinput_から取得した値をセットする
     * 例　test.txt → test_20131018_09.txt
     * @param toFileId
     * @return
     */
    private String fileRenameName(String fileName) {
        
        String[] name = fileName.split("\\.");
        
        return name[0].trim() + "_" + FiResorceUtility.getPropertie(input_.getRenameDt()) + "_" + input_.getRenameTime() + "." + name[1].trim();
    }
    
    
    public void setInputObject(Object input) throws Exception {
        input_ = (FileRenameDaoInputBean) input;
    }

    public Object getOutputObject() throws Exception {
        return null;
    }

}
