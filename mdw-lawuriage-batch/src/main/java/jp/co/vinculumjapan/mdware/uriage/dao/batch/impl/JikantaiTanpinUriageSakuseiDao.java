package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.JikantaiTanpinUriageSakuseiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * 
 * <p>タイトル: JikantaiTanpinUriageSakuseiDao.java クラス</p>
 * <p>説明　: 時間帯単品売上作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.16) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.20) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応(ファイル送信処理)
 * @Version 3.02 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @version 3.03 (2014.01.27) M.Ayukawa [結合0101] 時間帯内部IFデータパス不備
 *
 */
public class JikantaiTanpinUriageSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "時間帯単品売上作成処理";
    /** テーブル名称 */
    private static final String TABLE_NAME = "IF時間帯単品売上ワーク";
    /** カラム名 明細 */
    private static final String MEISAI_RC = "MEISAI_RC";
    /** 改行コード */
    private static final String NEW_LINE_CD = "\r\n";

    /** 時間帯単品売上作成処理InputBean*/
    private JikantaiTanpinUriageSakuseiDaoInputBean inputBean = null;

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // システムコントロールよりディレクトリパス取得
        final String PATH_SEND_JIKANTAI_SYOHIN = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_SEND_JIKANTAI_SYOHIN);
        // 日付置換文字列取得
        final String REPLACE_DATE = FiResorceUtility.getPropertie(inputBean.getDateChgStrPID());

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementSelect = null;
        BufferedWriter writer = null;

        int outputCount = 0;

        try {
            // ファイル名取得
            String strFileName = (String) inputBean.getFileName();
            // 「退避：ファイル名」の【yyyymmdd】（日付文字列）を「退避：日付置換文字列」に文字列置換する
            strFileName = strFileName.replace(UriageCommonConstants.REPLACE_DATE, REPLACE_DATE);
            // 「退避：ファイル名」の【hhmmss】（時刻文字列）を≪InputBean≫.時刻置換文字列に文字列置換する
            strFileName = strFileName.replace(UriageCommonConstants.REPLACE_TIME, inputBean.getTimeChgStr());

            // 出力ファイル初期化
            File tmpFile = new File(PATH_SEND_JIKANTAI_SYOHIN + "\\" + strFileName);
            writer = new BufferedWriter(new FileWriter(tmpFile));

            // IF時間帯単品売上ワークから時間帯単品売上ファイルを出力する

            preparedStatementSelect = invoker.getDataBase().prepareStatement(getWkIfJikantaiTanpinUriageSelectSql());
            preparedStatementSelect.setString(1, COMP_CD);
            preparedStatementSelect.setString(2, BATCH_DT);

            ResultSet rs = preparedStatementSelect.executeQuery();

            // ファイル出力処理
            while (rs.next()) {
                writer.write(rs.getString(MEISAI_RC) + NEW_LINE_CD);
                outputCount = outputCount + 1;
            }

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + outputCount + "件の" + TABLE_NAME + "を出力しました。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("BufferedWriter Closeエラー");
            }
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
     * 部門精算データから時間帯DPT客数ワークを登録するSQLを取得する
     * 
     * @return 時間帯DPT客数ワーク登録SQL
     */
    private String getWkIfJikantaiTanpinUriageSelectSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("       WIJTU.YR ");
        sql.append("    || WIJTU.MN ");
        sql.append("    || WIJTU.DD ");
        sql.append("    || TRIM(WIJTU.TENPO_CD) ");
        sql.append("    || WIJTU.JAN_CD ");
        sql.append("    || WIJTU.SYOHIN_CD ");
        sql.append("    || WIJTU.TIME_NO ");
        sql.append("    || TO_CHAR(WIJTU.URIAGE_VL, 'S000000000000') ");
        sql.append("    || TO_CHAR(WIJTU.URIAGE_QT, 'S00000') ");
        sql.append("    || TO_CHAR(WIJTU.NEBIKI_VL, 'S000000000000') ");
        sql.append("    || TO_CHAR(WIJTU.NEBIKI_QT, 'S00000') ");
        sql.append("    || TO_CHAR(WIJTU.KYAKU_QT, 'S00000') ");
        sql.append("    || WIJTU.BUNRUI1_CD ");
        sql.append("    || WIJTU.BUNRUI2_CD ");
        sql.append("    || WIJTU.BUNRUI5_CD AS MEISAI_RC ");
        sql.append("FROM ");
        sql.append("    WK_IF_JIKANTAI_TANPIN_URIAGE WIJTU ");
        sql.append("WHERE  ");
        sql.append("    WIJTU.COMP_CD                       = ? AND ");
        sql.append("    WIJTU.YR || WIJTU.MN || WIJTU.DD    = ? ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object input JikantaiTanpinUriageSakuseiDaoInputBean
     */
    public void setInputObject(Object input) throws Exception {
        this.inputBean = (JikantaiTanpinUriageSakuseiDaoInputBean)input;
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
            DaoIf dao = new JikantaiTanpinUriageSakuseiDao();
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
