package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.common.dao.output.DataImportDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.OfflineKaikeiSeisanUriageDataTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiDataImportDaoUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiFileUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: OfflineKaikeiSeisanUriageDataTorikomiDao クラス</p>
 *  <P>説明: オフライン会計精算売上データ取込処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 
 */
public class OfflineKaikeiSeisanUriageDataTorikomiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "オフライン会計精算売上データ取込処理";
    // 更新対象テーブル名 
//    private static final String TABLE_NAME = "WK_OFFLINE_KAIKEI_SEISAN";
    private static final String TABLE_NAME = "WK_OFF_KAIKEI_SEISAN";

    // 入力ビーン
    private OfflineKaikeiSeisanUriageDataTorikomiDaoInputBean inputBean = null;

    /**
     * オフライン会計精算売上データ取込処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　オフライン会計精算売上データ取込処理を開始します。");

        // FTPファイルの存在をチェックする
        if (!FiFileUtility.pathFileExists(FiResorceUtility.getPropertie(UriResorceKeyConstant.DATA_DIR_PATH), inputBean.getDataFileName())) {
            // FTPファイルが無い場合、ログ出力
            invoker.warnLog(strUserID + "　：　オフライン会計精算売上ファイル(" + inputBean.getDataFileName() + ")が存在しません。オフライン会計精算売上データ取込処理は行われませんでした。");
            throw new DaoException("データ取込処理にて例外エラー発生。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　SQL*Loaderで、オフライン会計精算売上ワークへのデータ取込処理を開始します。");

        // オフライン会計精算売上ワークテーブルデータの取込を行う
        DataImportDaoOutputBean outBean = FiDataImportDaoUtility.executeDataAccess(invoker, inputBean.getDataFileName(), inputBean.getFormatFileName(), inputBean.getBackupFileName(), inputBean
                .getLogFileName(), TABLE_NAME);

        // outputBeanの終了ステータスより処理結果判定
        if (outBean.getResultCd() != 0) {
            // 正常終了以外は異常終了
            invoker.warnLog(strUserID + "　：　SQL*Loaderで、オフライン会計精算売上ワークへのデータ取込処理にて例外エラーが発生しました。");
            throw new DaoException("データ取込処理にて例外エラー発生。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　SQL*Loaderで、オフライン会計精算売上ワークへのデータ取込処理を終了します。取込件数は" + outBean.getResultCount() + "件です。");
        /** 090904 yasuda update start (日計ネット金額＝内税対象金額＋外税対象金額＋非課税対象金額となるようUPDATEする) */
        invoker.infoLog(strUserID + "　：　日計ネット金額算出処理を開始します。");
        // 日計ネットの算出
        PreparedStatementEx ps = invoker.getDataBase().prepareStatement("calcNikkeiNet");
        ps.executeUpdate();
        invoker.infoLog(strUserID + "　：　日計ネット金額算出処理を終了します。");
        
        invoker.infoLog(strUserID + "　：　オフライン会計精算売上データ取込処理を終了します。");
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
        inputBean = (OfflineKaikeiSeisanUriageDataTorikomiDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new OfflineKaikeiSeisanUriageDataTorikomiDao();
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
