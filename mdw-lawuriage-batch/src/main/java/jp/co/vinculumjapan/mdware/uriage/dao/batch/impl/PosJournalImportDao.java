package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.common.dao.output.DataImportDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.PosJournalImportDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiDataImportDaoUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiFileUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;

/**
 * <p>タイトル: PosJournalImportDao クラス</p>
 * <p>説明: POSジャーナル取込</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @Version 1.00 (2015.09.07) TU.TD FIVIMART対応
 * @Version 1.01 (2016.05.25) VINX S.Kashihara FIVI対応
 * @Version 1.02 (2016.06.14) VINX Y.Itaki FIVI対応
 * @Version 1.03 (2016.11.01) VINX k.Hyo FIVI対応
 * @Version 1.04 (2016.11.25) VINX Y.Itaki FIVI対応
 * @Version 1.05 (2017.03.15) VINX J.Endo FIVI対応 #4380
 * @Version 1.06 (2017.04.17) VINX J.Endo FIVI対応 #4701
 * @Version 1.07 (2017.04.17) VINX J.Endo FIVI対応 #4703
 *
 */

public class PosJournalImportDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "POSジャーナル取込";
    // ファイル名
    private static final String FILE_NAME = "FILE_RECV_POS";
    // ファイルパス
    private static final String FILE_PATH = "PATH_RECV_POS";
    // バックアップパス
    private static final String FILE_BACKUP_PATH = "PATH_RECV_POS_RECOVERY";

    // 入力ビーン
    private PosJournalImportDaoInputBean inputBean = null;

    /**
     *
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
        // ユーザーID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + BATCH_NAME;

        //inputBeanにファイル名を設定する
        inputBean.setDataFileName(FiResorceUtility.getPropertie(PosJournalImportDao.FILE_NAME));

        // ログ出力
        invoker.infoLog(strUserID + "　：　POSジャーナル取込を開始します。");

        // FTPファイルの存在をチェックする
        if (!FiFileUtility.pathFileExists(FiResorceUtility.getPropertie(PosJournalImportDao.FILE_PATH), inputBean.getDataFileName())) {
            // CSVファイルが無い場合、ログ出力
            invoker.warnLog(strUserID + "　：　POSジャーナル(" + inputBean.getDataFileName() + ")が存在しません。POSジャーナル取込は行われませんでした。");
            invoker.infoLog(strUserID + "　：　POSジャーナル取込を終了します。");
            throw new DaoException("データ取込処理にて例外エラー発生。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　SQL*Loaderで、POSジャーナル取込を開始します。");

        // データディレクトリパスの設定
        FiDataImportDaoUtility.setDataDirPath(PosJournalImportDao.FILE_PATH);
        // バックアップディレクトリパスの設定
        FiDataImportDaoUtility.setBackupDirPtah(PosJournalImportDao.FILE_BACKUP_PATH);

        // 店別DPT打ち売上ワークテーブルデータの取込を行う
        DataImportDaoOutputBean outBean = FiDataImportDaoUtility.executeDataAccess(invoker, inputBean.getDataFileName(), inputBean.getFormatFileName(), inputBean.getBackupFileName(), inputBean
                .getLogFileName(), getSelectCountTable());

        // outputBeanの終了ステータスより処理結果判定
        if (outBean.getResultCd() != 0) {
            // 正常終了以外は異常終了
            invoker.warnLog(strUserID + "　：　SQL*Loaderで、POSジャーナル取込処理にて例外エラーが発生しました。");
            throw new DaoException("データ取込処理にて例外エラー発生。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　SQL*Loaderで、POSジャーナル取込処理を終了します。取込件数は" + outBean.getResultCount() + "件です。");
        invoker.infoLog(strUserID + "　：　POSジャーナル込処理を終了します。");
    }

    /**
     * 各テーブルの行数の合計
     * @return
     */
    private String getSelectCountTable(){
        StringBuilder sb = new StringBuilder();
        // 2016.06.14 VINX Y.Itaki FIVI対応 MOD(S)
        sb.append("(SELECT COMMAND FROM TMP_POS_A_ITEM_WK_BEFORE ");
        sb.append("UNION ALL ");
        sb.append("SELECT COMMAND FROM TMP_POS_B_TOTAL ");
        sb.append("UNION ALL ");
        sb.append("SELECT COMMAND FROM TMP_POS_C_PAYMENT_WK ");
        sb.append("UNION ALL ");
        sb.append("SELECT COMMAND FROM TMP_POS_D_CASH_WK ");
        sb.append("UNION ALL ");
        // 2017.03.15 VINX E.Endo FIVI対応(#4380) MOD(S)
        //sb.append("SELECT COMMAND FROM TMP_POS_E_TENDER_WK ");
        sb.append("SELECT COMMAND FROM TMP_POS_E_TENDER ");
        // 2017.03.15 VINX E.Endo FIVI対応(#4380) MOD(E)
        sb.append("UNION ALL ");
        sb.append("SELECT COMMAND FROM TMP_POS_F_INVOICE_WK_BEFORE ");
        // 2016.05.25 VINX S.Kashihara FIVI対応 ADD(S)
        sb.append("UNION ALL ");
        sb.append("SELECT COMMAND FROM TMP_POS_K_DISCOUNT_WK ");
        // 2016.05.25 VINX S.Kashihara FIVI対応 ADD(E)
        sb.append("UNION ALL ");
        // 2017.04.17 VINX E.Endo FIVI対応(#4701) MOD(S)
        //sb.append("SELECT COMMAND FROM TMP_POS_T_TRAILER_WK ");
        sb.append("SELECT COMMAND FROM TMP_POS_T_TRAILER ");
        // 2017.04.17 VINX E.Endo FIVI対応(#4701) MOD(E)
        // 2016.06.14 VINX Y.Itaki FIVI対応 MOD(E)
        // 2017.04.17 VINX E.Endo FIVI対応(#4703) ADD(S)
        sb.append("UNION ALL ");
        sb.append("SELECT COMMAND FROM TMP_POS_P_PROMO_WK ");
        // 2017.04.17 VINX E.Endo FIVI対応(#4703) ADD(E)
        // 2016.11.01 VINX k.Hyo FIVI対応 ADD(S)
        sb.append("UNION ALL ");
        // 2016.11.25 VINX Y.Itaki FIVI対応 MOD(S)
        //sb.append("SELECT COMMAND FROM TMP_POS_H_HAMPER_WK) TMP_POS_TOTAL ");
        sb.append("SELECT COMMAND FROM TMP_POS_H_HAMPER_WK_BEFORE) TMP_POS_TOTAL ");
        // 2016.11.25 VINX Y.Itaki FIVI対応 MOD(E)
        // 2016.11.01 VINX k.Hyo FIVI対応 ADD(E)
        return sb.toString();
    }

    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {
        inputBean = (PosJournalImportDaoInputBean) input;
    }

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

}
