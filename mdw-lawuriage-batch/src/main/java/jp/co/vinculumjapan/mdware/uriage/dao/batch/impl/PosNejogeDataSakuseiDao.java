package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.PosNejogeDataSakuseiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.impl.CsvExportPreparedDao;
import jp.co.vinculumjapan.mdware.uriage.dao.input.CsvExportPreparedInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * 
 * <p>タイトル: PosNejogeDataSakuseiDao.java クラス</p>
 * <p>説明　: POS値上下データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.09) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.02 (2013.10.20) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 ファイル送信処理対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 *
 */
public class PosNejogeDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    // 「/」
    private static final String SLASH = "/";
    // 送信元ディレクトリ
    private static final String PATH_SEND = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND_NEJYOGE_SIIRE);

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS値上下データ作成処理";
    /** インプットビーン */
    private PosNejogeDataSakuseiDaoInputBean input = null;

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementSelect = null;

        // CSV出力用
        CsvExportPreparedDao csvExportDao = new CsvExportPreparedDao();
        CsvExportPreparedInputBean csvInputBean = new CsvExportPreparedInputBean();

        int intCreateCount = 0;
        try {

            preparedStatementSelect = invoker.getDataBase().prepareStatement(getWkNejogePosSelectSql(true));
            preparedStatementSelect.setString(1, COMP_CD);
            preparedStatementSelect.setString(2, BATCH_DT);


            // CSV出力用SQL文をセット
            csvInputBean.setSqlStatement(preparedStatementSelect);
            // CSV出力パスをセット
            csvInputBean.setExportFilePath(PATH_SEND + SLASH + input.getFileName());

            // 入力ビーンをセット
            csvExportDao.setInputObject(csvInputBean);

            invoker.InvokeDao(csvExportDao);


            // データ生成件数をセットする
            intCreateCount = Integer.parseInt(csvExportDao.getOutputObject().toString());

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + intCreateCount + "件のPOS値上下データを出力しました。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementSelect != null) {
                    preparedStatementSelect.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * POS値上下ワークを抽出するSQLを抽出するSQLを取得する
     * 
     * @return POS値上下ワーク抽出SQL取得
     */
    private String getWkNejogePosSelectSql(boolean isRegiNebiki) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("     WNP.DENPYO_KB ");
        sql.append("    ,WNP.KANRI_DT ");
        sql.append("    ,WNP.TENPO_CD ");
        sql.append("    ,WNP.BUNRUI1_CD ");
        sql.append("    ,WNP.SYOHIN_CD ");
        sql.append("    ,WNP.BUNRUI2_CD ");
        sql.append("    ,WNP.BUNRUI5_CD ");
        sql.append("    ,WNP.SYOHIN_HINMEI_KANJI_NA ");
        sql.append("    ,WNP.KIKAKU_KANJI_NA ");
        sql.append("    ,WNP.KIKAKU_KANJI_2_NA ");
        sql.append("    ,WNP.HINMEI_KANA_NA ");
        sql.append("    ,WNP.KIKAKU_KANA_NA ");
        sql.append("    ,WNP.KIKAKU_KANA_2_NA ");
        sql.append("    ,WNP.RIYU_KB ");
        sql.append("    ,WNP.RIYU_SYOSAI_KB ");
        sql.append("    ,WNP.ZEI_KB ");
        sql.append("    ,WNP.TAX_RT ");
        sql.append("    ,WNP.SURYO_QT ");
        sql.append("    ,WNP.SHIIRE_SURYO_QT ");
        sql.append("    ,WNP.BAITANKA_OLD_VL ");
        sql.append("    ,WNP.BAITANKA_NEW_VL ");
        sql.append("    ,WNP.M_BAITANKA_VL ");
        sql.append("    ,WNP.BAIKA_ZEINUKI_VL ");
        sql.append("    ,WNP.BAIKA_ZEIKOMI_VL ");
        sql.append("    ,WNP.BAIKA_ZEIGAKU_VL ");
        sql.append("FROM ");
        sql.append("    WK_NEJOGE_POS WNP ");
        sql.append("WHERE ");
        sql.append("    WNP.COMP_CD     = ? AND ");
        sql.append("    WNP.KANRI_DT    = ? ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object input PosNejogeDataSakuseiDaoInputBean
     */
    public void setInputObject(Object input) throws Exception {
        this.input = (PosNejogeDataSakuseiDaoInputBean) input;
    }

    /**
     * アウトプットBeanを取得する
     * 
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new PosNejogeDataSakuseiDao();
            new StandAloneDaoInvoker("mm").InvokeDao(dao);
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
