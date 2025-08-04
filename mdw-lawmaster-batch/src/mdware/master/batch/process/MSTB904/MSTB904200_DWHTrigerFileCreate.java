package mdware.master.batch.process.MSTB904;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <p>タイトル: DWH用マスタトリガーファイル作成処理</p>
 * <p>説明　　: DWH用マスタトリガーファイル作成をします</p>
 * <p>著作権　: Copyright (c) 2014</p>
 * <p>会社名　: VINX</p>
 * @author S.Arakawa
 * @version 3.00 (2014.02.21) S.Arakawa [シス0064] ランドローム様対応 DWHマスタトリガーファイル作成
 * @version 3.01 (2014.02.27) M.Ayukawa [シス0064] DWHマスタトリガーファイル作成(SYSTEM_CONTROL化)
 */
public class MSTB904200_DWHTrigerFileCreate {

    /** バッチ名称 */
    private static final String BAT_NAME = "DWH用マスタトリガーファイル作成";

    /** ファイル作成先フォルダパス */
    private static final String PATH_SEND_DWH = ResorceUtil.getInstance().getPropertie("PATH_SEND_DWH");

    /** 作成ファイル名 */
    private static final String FILE_NAME = ResorceUtil.getInstance().getPropertie("DWH_FTP_FILE_MST_TRG");

	/** ログ出力用変数 */
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

    /**
     * データアクセス処理を実行します。
     */
    public void execute() throws Exception {

        /** ユーザーIDの取得 */
        String userId = userLog.getJobId();

        /** バッチ開始ログ出力 */
        batchLog.getLog().debug(userId + " " + BAT_NAME + "：" + "DWH用マスタトリガーファイル作成処理を開始します。");

        /** ファイルパス作成 */
        File trigerFilePath = new File(PATH_SEND_DWH + "\\" + FILE_NAME);

        /** ファイル作成（0バイト） */
        BufferedWriter writer = new BufferedWriter(new FileWriter(trigerFilePath));

        /** バッチ終了ログ出力 */
        batchLog.getLog().debug(userId + " " + BAT_NAME + "：" + "DWH用マスタトリガーファイル作成処理を終了します。");

    }

    public Object getOutputObject() {
        return null;
    }

    public void setInputObject(Object obj) {
    }

}
