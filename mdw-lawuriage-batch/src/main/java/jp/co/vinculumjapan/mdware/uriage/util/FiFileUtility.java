package jp.co.vinculumjapan.mdware.uriage.util;

import java.io.File;

/**
 * <p>タイトル: FiFileUtility クラス</p>
 * <p>説明: 予算共通</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: VJC</p>
 * @author  ZF.XU
 * @version 1.00 (Create time: 2009/03/06)
 */
public class FiFileUtility {

    /**
     * IFファイルの存在をチェックする
     * 
     * @param String strDataFileDirPath   データファイルディレクトリのパス
     * @param String strDataFileName      データファイル名
     * 
     * @return IFファイルの存在チェック正しければtrue, 誤っていればfalse
     */
    public static boolean pathFileExists(String strDataFileDirPath, String strDataFileName) {

        // データファイル
        String dataFilePath = new File(strDataFileDirPath + "/" + strDataFileName).getAbsolutePath();

        // データファイル存在チェック
        if (!new File(dataFilePath).exists()) {
            return false;
        }
        return true;
    }
}