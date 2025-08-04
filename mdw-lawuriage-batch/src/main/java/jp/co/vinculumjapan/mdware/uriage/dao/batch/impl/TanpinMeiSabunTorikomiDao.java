package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.common.dao.output.DataImportDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.TanpinMeiSabunTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiDataImportDaoUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiFileUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: TanpinMeiSabunTorikomiDao クラス</p>
 *  <P>説明: 単品明細差分取込処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author XP.Chen
 *  @version 1.0 
 */
public class TanpinMeiSabunTorikomiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "単品明細差分取込処理";
    // 更新対象テーブル名 
    private static final String TABLE_NAME = "WK_TANPIN_MEI_SABUN";

    // 入力ビーン
    private TanpinMeiSabunTorikomiDaoInputBean inputBean = null;

    /**
     * 単品明細差分取込処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　単品明細差分取込処理を開始します。");

        // FTPファイルの存在をチェックする
        if (!FiFileUtility.pathFileExists(FiResorceUtility.getPropertie(UriResorceKeyConstant.DATA_DIR_PATH), inputBean.getDataFileName())) {
            // FTPファイルが無い場合、ログ出力
            invoker.warnLog(strUserID + "　：　リカバリ用新単品明細ログファイル(" + inputBean.getDataFileName() + ")が存在しません。単品明細差分取込処理は行われませんでした。");
            invoker.infoLog(strUserID + "　：　単品明細差分取込処理を終了します。");
            throw new DaoException("データ取込処理にて例外エラー発生。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　SQL*Loaderで、新単品明細ログ差分ワークへのデータ取込処理を開始します。");

        // 新単品明細ログ差分ワークテーブルデータの取込を行う
        DataImportDaoOutputBean outBean = FiDataImportDaoUtility.executeDataAccess(invoker, inputBean.getDataFileName(), inputBean.getFormatFileName(), inputBean.getBackupFileName(), inputBean
                .getLogFileName(), TABLE_NAME);

        // outputBeanの終了ステータスより処理結果判定
        if (outBean.getResultCd() != 0) {
            // 正常終了以外は異常終了
            invoker.warnLog(strUserID + "　：　SQL*Loaderで、新単品明細ログ差分ワークへのデータ取込処理にて例外エラーが発生しました。");
            throw new DaoException("データ取込処理にて例外エラー発生。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　SQL*Loaderで、新単品明細ログ差分ワークへのデータ取込処理を終了します。取込件数は" + outBean.getResultCount() + "件です。");
        invoker.infoLog(strUserID + "　：　単品明細差分取込処理を終了します。");
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
        inputBean = (TanpinMeiSabunTorikomiDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new TanpinMeiSabunTorikomiDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
