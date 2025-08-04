package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *
 * <p>タイトル: HamperUriageSyokaiDataSakuseiDao.java クラス</p>
 * <p>説明　: ハンパー売上照会用データ作成</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016/05/13) S_MDware-G_FIVIマート様開発 VINX k.Hyo
 *
 */
public class HamperUriageSyokaiDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String DAO_NAME = "ハンパー売上照会データ作成";
    /** 登録先テーブル名称(レジ別取引精算テーブル) */
    private static final String DT_HAMPER_URIAGE_JISEKI_NAME = "ハンパー売上実績データ";
    /** システム日付取得 */
    String sysDt = FiResorceUtility.getDBServerTime();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        String dbServerTime = "";

        try {

            // レジ別取引精算データの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_HAMPER_URIAGE_JISEKI_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getRegiTorihikiSeisanInsertSql());

            preparedStatementExIns.setString(1, BATCH_DT);
            preparedStatementExIns.setString(2, BATCH_DT);
            preparedStatementExIns.setString(3, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_HAMPER_URIAGE_JISEKI_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_HAMPER_URIAGE_JISEKI_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * レジ別取引精算データ登録用SQLを取得する
     *
     * @return レジ別取引精算データ登録用SQL
     */
    private String getRegiTorihikiSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_HAMPER_URIAGE_JISEKI( ");
        sql.append("     URIAGE_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,HAMPER_SYOHIN_CD ");
        sql.append("    ,TENPO_NA ");
        sql.append("    ,SYOHIN_NA ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,DELETE_FG) ");
        sql.append("SELECT ");
        sql.append("     DTSH.KEIJO_DT ");
        sql.append("    ,DTSH.TENPO_CD ");
        sql.append("    ,DTSH.TANPIN_CD ");
        sql.append("    ,RT.KANJI_RN ");
        sql.append("    ,DTSH.HINMEI_KANJI_NA ");
        sql.append("    ,DTSH.URIAGE_SOURI_QT ");
        sql.append("    ,DTSH.URIAGE_SOURI_VL ");
        sql.append("    ,'URIB012650' ");
        sql.append("    ,? ");
        sql.append("    ,'URIB012650' ");
        sql.append("    ,? ");
        sql.append("    ,'0' ");
        sql.append("FROM ");
        sql.append("    DT_TANPIN_SEISAN_HMPR DTSH ");
        sql.append("LEFT JOIN R_TENPO RT ");
        sql.append("    ON ");
        sql.append("    DTSH.TENPO_CD = RT.TENPO_CD ");
        //sql.append("LEFT JOIN ( ");
        //sql.append("    SELECT ");
        //sql.append("        RFS_SUB.SYOHIN_CD ");
        //sql.append("        ,SUBMAX.KEIJO_DT ");
        //sql.append("        ,SUBMAX.MAX_YUKO_DT ");
        //sql.append("        ,RFS_SUB.HINMEI_KANJI_NA ");
        //sql.append("    FROM ");
        //sql.append("        R_FI_SYOHIN RFS_SUB ");
        //sql.append("    INNER JOIN ( ");
        //sql.append("        SELECT ");
        //sql.append("            DTSH_SUB.TANPIN_CD ");
        //sql.append("            ,DTSH_SUB.KEIJO_DT ");
        //sql.append("            ,MAX(RFS_SUBMAX.YUKO_DT) AS MAX_YUKO_DT ");
        //sql.append("        FROM ");
        //sql.append("            DT_TANPIN_SEISAN_HMPR DTSH_SUB ");
        //sql.append("        INNER JOIN R_FI_SYOHIN RFS_SUBMAX ");
        //sql.append("        ON ");
        //sql.append("        RFS_SUBMAX.SYOHIN_CD = DTSH_SUB.TANPIN_CD AND ");
        //sql.append("        RFS_SUBMAX.YUKO_DT <= DTSH_SUB.KEIJO_DT ");
        //sql.append("        GROUP BY ");
        //sql.append("           DTSH_SUB.TANPIN_CD ");
        //sql.append("           ,DTSH_SUB.KEIJO_DT) SUBMAX ");
        //sql.append("    ON ");
        //sql.append("        RFS_SUB.SYOHIN_CD = SUBMAX.TANPIN_CD ");
        //sql.append("        AND RFS_SUB.YUKO_DT = SUBMAX.MAX_YUKO_DT) RFS ");
        //sql.append("ON ");
        //sql.append("DTSH.TANPIN_CD = RFS.SYOHIN_CD ");
        //sql.append("AND DTSH.KEIJO_DT = RFS.KEIJO_DT ");

        sql.append("WHERE ");
        sql.append("    DTSH.DATA_SAKUSEI_DT = ? ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
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
            DaoIf dao = new SeisanDataSakuseiDao();
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
