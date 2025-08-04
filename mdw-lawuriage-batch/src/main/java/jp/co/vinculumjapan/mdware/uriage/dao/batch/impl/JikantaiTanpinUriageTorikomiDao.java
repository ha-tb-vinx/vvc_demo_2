package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.JikantaiTanpinUriageTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * 
 * <p>タイトル: JikantaiTanpinUriageTorikomiDao.java クラス</p>
 * <p>説明　: 時間帯単品売上取込処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.16) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2013.10.28) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №058
 * @Version 3.03 (2014.01.31) Y.Tominaga 結合ﾃｽﾄNo.0112 POS時間帯取込処理改善 対応
 *
 */
public class JikantaiTanpinUriageTorikomiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "時間帯単品売上取込処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "IF時間帯単品売上ワーク";
    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_IF_JIKANTAI_TANPIN_URIAGE";

    /** 時間帯単品売上取込処理InputBean*/
    private JikantaiTanpinUriageTorikomiDaoInputBean inputBean = null;

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // IF時間帯単品売上ワークを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 単品点検データからIF時間帯単品売上ワークを登録する
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkIfJikantaiTanpinUriageInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, BATCH_DT);
//            preparedStatementExIns.setString(8, inputBean.getTimeChgStr());

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementDel != null) {
                    preparedStatementDel.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 単品精算データからIF時間帯単品売上ワークを登録するSQLを取得する
     * 
     * @return IF時間帯単品売上ワーク登録SQL
     */
    private String getWkIfJikantaiTanpinUriageInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_IF_JIKANTAI_TANPIN_URIAGE( ");
        sql.append("     COMP_CD ");
        sql.append("    ,YR ");
        sql.append("    ,MN ");
        sql.append("    ,DD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,JAN_CD ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,TIME_NO ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,NEBIKI_QT ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("    DTT.COMP_CD ");
        sql.append("    ,SUBSTR(DTT.KEIJO_DT, 1, 4) ");
        sql.append("    ,SUBSTR(DTT.KEIJO_DT, 5, 2) ");
        sql.append("    ,SUBSTR(DTT.KEIJO_DT, 7, 2) ");
        sql.append("    ,DTT.TENPO_CD ");
        sql.append("    ,DTT.TANPIN_CD ");
        sql.append("    ,DTT.TANPIN_CD ");
        sql.append("    ,DTT.TIME_NO ");
        sql.append("    ,DTT.URIAGE_VL - COALESCE(ZJTU.URIAGE_VL, 0) ");
        sql.append("    ,DTT.URIAGE_QT - COALESCE(ZJTU.URIAGE_QT, 0) ");
        sql.append("    ,DTT.NEBIKI_REGI_VL - COALESCE(ZJTU.NEBIKI_VL, 0) ");
        sql.append("    ,DTT.NEBIKI_REGI_QT - COALESCE(ZJTU.NEBIKI_QT, 0) ");
        sql.append("    ,'0' ");
        sql.append("    ,DTT.BUNRUI1_CD ");
        sql.append("    ,DTT.BUNRUI2_CD ");
        sql.append("    ,DTT.BUNRUI5_CD ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    DT_TANPIN_TENKEN DTT ");
        sql.append("    LEFT JOIN ");
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("             ZJTU.COMP_CD ");
        sql.append("            ,ZJTU.YR ");
        sql.append("            ,ZJTU.MN ");
        sql.append("            ,ZJTU.DD ");
        sql.append("            ,ZJTU.TENPO_CD ");
        sql.append("            ,ZJTU.SYOHIN_CD ");
        sql.append("            ,ZJTU.URIAGE_VL ");
        sql.append("            ,ZJTU.URIAGE_QT ");
        sql.append("            ,ZJTU.NEBIKI_VL ");
        sql.append("            ,ZJTU.NEBIKI_QT ");
        sql.append("        FROM ");
        sql.append("            ZEN_JIKANTAI_TANPIN_URIAGE ZJTU ");
        sql.append("        WHERE ");
        sql.append("            ZJTU.COMP_CD = ? ");
        sql.append("        ) ZJTU ");
        sql.append("    ON ");
        sql.append("        DTT.COMP_CD                 = ZJTU.COMP_CD  AND ");
        sql.append("        SUBSTR(DTT.KEIJO_DT, 1, 4)  = ZJTU.YR       AND ");
        sql.append("        SUBSTR(DTT.KEIJO_DT, 5, 2)  = ZJTU.MN       AND ");
        sql.append("        SUBSTR(DTT.KEIJO_DT, 7, 2)  = ZJTU.DD       AND ");
        sql.append("        DTT.TENPO_CD                = ZJTU.TENPO_CD AND ");
        sql.append("        DTT.TANPIN_CD               = ZJTU.SYOHIN_CD ");
        sql.append("WHERE ");
        sql.append("    DTT.COMP_CD     = ? AND ");
        sql.append("    DTT.KEIJO_DT    = ? ");
//        sql.append("    DTT.TIME_NO     = ? ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object input JikantaiTanpinUriageTorikomiDaoInputBean
     */
    public void setInputObject(Object input) throws Exception {
        this.inputBean = null;
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
            DaoIf dao = new JikantaiTanpinUriageTorikomiDao();
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
