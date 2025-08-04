package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.common.dao.output.DataImportDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.OfflineDPTSeisanUriageDataTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiDataImportDaoUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiFileUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: OfflineDPTSeisanUriageDataTorikomiDao クラス</p>
 * <p>説明　　: オフラインDPT精算売上データ取込処理</p>
 * <p>著作権　: Copyright (c) 2009</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author   J.Lu
 * @version 1.00 (2009.05.20) 初版作成
 */
public class OfflineDPTSeisanUriageDataTorikomiDao implements DaoIf {

    // 更新対象テーブル名 
//    private static final String TABLE_NAME = "WK_OFFLINE_DPT_SEISAN_URI";
    private static final String TABLE_NAME = "WK_OFF_DPT_SEISAN_URI";
    // バッチ名称
    private static final String BATCH_NAME = "オフラインDPT精算売上データ取込処理";

    // 入力ビーン
    private OfflineDPTSeisanUriageDataTorikomiDaoInputBean inputBean = null;

    /**
     * オフラインDPT精算売上データ取込処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserId = invoker.getUserId() + " " + BATCH_NAME;

        // 開始ログを出力する
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上データ取込処理を開始します。");

        // FTPファイルの存在をチェックする
        if (!FiFileUtility.pathFileExists(FiResorceUtility.getPropertie(UriResorceKeyConstant.DATA_DIR_PATH), inputBean.getDataFileName())) {
            // FTPファイルが無い場合、未処理通知ログを出力する
            invoker.warnLog(strUserId + "　：　オフラインDPT精算売上ファイル(" + inputBean.getDataFileName() + ")が存在しません。オフラインDPT精算売上データ取込処理は行われませんでした。");
            throw new DaoException("データ取込処理にて例外エラー発生。");
        }

        // オフラインDPT精算売上ワーク追加ログ開始を出力する
        invoker.infoLog(strUserId + "　：　SQL*Loaderで、オフラインDPT精算売上ワークへのデータ取込処理を開始します。");

        // オフラインDPT精算売上ワークテーブルデータの取込を行う
        DataImportDaoOutputBean outBean = FiDataImportDaoUtility.executeDataAccess(invoker, inputBean.getDataFileName(), inputBean.getFormatFileName(), inputBean.getBackupFileName(), inputBean
                .getLogFileName(), TABLE_NAME);

        // outputBeanの終了ステータスより処理結果判定
        if (outBean.getResultCd() != 0) {
            // 正常終了以外は異常終了
            invoker.warnLog(strUserId + "　：　SQL*Loaderで、オフラインDPT精算売上ワークへのデータ取込処理にて例外エラーが発生しました。");
            throw new DaoException("データ取込処理にて例外エラー発生。");
        }

        invoker.infoLog(strUserId + "　：　SQL*Loaderで、オフラインDPT精算売上ワークへのデータ取込処理を終了します。取込件数は" + outBean.getResultCount() + "件です。");

        // 終了ログを出力
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上データ取込処理を終了します。");

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
        this.inputBean = (OfflineDPTSeisanUriageDataTorikomiDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new OfflineDPTSeisanUriageDataTorikomiDao();
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
