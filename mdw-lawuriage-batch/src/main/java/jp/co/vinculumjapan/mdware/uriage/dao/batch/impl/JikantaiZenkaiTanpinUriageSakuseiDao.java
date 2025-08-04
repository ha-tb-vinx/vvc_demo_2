package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.JikantaiZenkaiTanpinUriageSakuseiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * 
 * <p>タイトル: JikantaiZenkaiTanpinUriageSakuseiDao.java クラス</p>
 * <p>説明　: 前回時間帯単品売上データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.16) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2013.10.28) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №058
 * @Version 3.03 (2014.01.31) Y.Tominaga 結合ﾃｽﾄNo.0112 POS時間帯取込処理改善 対応
 *
 */
public class JikantaiZenkaiTanpinUriageSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "前回時間帯単品売上データ作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "前回時間帯単品売上データ";

    /** 前回時間帯単品売上データ作成処理InputBean*/
    private JikantaiZenkaiTanpinUriageSakuseiDaoInputBean inputBean = null;

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

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 単品精算データから前回時間帯単品売上データを登録する
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加・更新を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getZenJikantaiTanpinUriageMergeSql());
            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, BATCH_DT);
//            preparedStatementExIns.setString(3, inputBean.getTimeChgStr());
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, userId);
            preparedStatementExIns.setString(6, dbServerTime);
            preparedStatementExIns.setString(7, userId);
            preparedStatementExIns.setString(8, dbServerTime);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加・更新しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加・更新を終了します。");

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
     * 単品精算データから前回時間帯単品売上データを登録・更新するSQLを取得する
     * 
     * @return 前回時間帯単品売上データ登録・更新SQL
     */
    private String getZenJikantaiTanpinUriageMergeSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("MERGE INTO ZEN_JIKANTAI_TANPIN_URIAGE ZJTU ");
        sql.append("    USING ");
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("             DTT.COMP_CD ");
        sql.append("            ,SUBSTR(DTT.KEIJO_DT, 1, 4) AS YR ");
        sql.append("            ,SUBSTR(DTT.KEIJO_DT, 5, 2) AS MN ");
        sql.append("            ,SUBSTR(DTT.KEIJO_DT, 7, 2) AS DD ");
        sql.append("            ,DTT.TENPO_CD ");
        sql.append("            ,DTT.TANPIN_CD ");
        sql.append("            ,DTT.TIME_NO ");
        sql.append("            ,DTT.URIAGE_VL ");
        sql.append("            ,DTT.URIAGE_QT ");
        sql.append("            ,DTT.NEBIKI_REGI_VL ");
        sql.append("            ,DTT.NEBIKI_REGI_QT ");
        sql.append("            ,DTT.BUNRUI1_CD ");
        sql.append("            ,DTT.BUNRUI2_CD ");
        sql.append("            ,DTT.BUNRUI5_CD ");
        sql.append("        FROM ");
        sql.append("            DT_TANPIN_TENKEN DTT ");
        sql.append("        WHERE ");
        sql.append("            DTT.COMP_CD     = ? AND ");
        sql.append("            DTT.KEIJO_DT    = ? ");
//        sql.append("            DTT.TIME_NO     = ? ");
        sql.append("        ) DTT ");
        sql.append("    ON ");
        sql.append("        ( ");
        sql.append("            ZJTU.COMP_CD    = DTT.COMP_CD   AND ");
        sql.append("            ZJTU.YR         = DTT.YR        AND ");
        sql.append("            ZJTU.MN         = DTT.MN        AND ");
        sql.append("            ZJTU.DD         = DTT.DD        AND ");
        sql.append("            ZJTU.TENPO_CD   = DTT.TENPO_CD  AND ");
        sql.append("            ZJTU.SYOHIN_CD  = DTT.TANPIN_CD ");
        sql.append("        ) ");
        sql.append("    WHEN MATCHED THEN ");
        sql.append("        UPDATE ");
        sql.append("            SET ");
        sql.append("                 ZJTU.JAN_CD            = DTT.TANPIN_CD ");
        sql.append("                ,ZJTU.TIME_NO           = DTT.TIME_NO ");
        sql.append("                ,ZJTU.URIAGE_VL         = DTT.URIAGE_VL ");
        sql.append("                ,ZJTU.URIAGE_QT         = DTT.URIAGE_QT ");
        sql.append("                ,ZJTU.NEBIKI_VL         = DTT.NEBIKI_REGI_VL ");
        sql.append("                ,ZJTU.NEBIKI_QT         = DTT.NEBIKI_REGI_QT ");
        sql.append("                ,ZJTU.KYAKU_QT          = 0 ");
        sql.append("                ,ZJTU.BUNRUI1_CD        = DTT.BUNRUI1_CD ");
        sql.append("                ,ZJTU.BUNRUI2_CD        = DTT.BUNRUI2_CD ");
        sql.append("                ,ZJTU.BUNRUI5_CD        = DTT.BUNRUI5_CD ");
        sql.append("                ,ZJTU.UPDATE_USER_ID    = ? ");
        sql.append("                ,ZJTU.UPDATE_TS         = ? ");
        sql.append("    WHEN NOT MATCHED THEN ");
        sql.append("        INSERT ( ");
        sql.append("             COMP_CD ");
        sql.append("            ,YR ");
        sql.append("            ,MN ");
        sql.append("            ,DD ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,JAN_CD ");
        sql.append("            ,SYOHIN_CD ");
        sql.append("            ,TIME_NO ");
        sql.append("            ,URIAGE_VL ");
        sql.append("            ,URIAGE_QT ");
        sql.append("            ,NEBIKI_VL ");
        sql.append("            ,NEBIKI_QT ");
        sql.append("            ,KYAKU_QT ");
        sql.append("            ,BUNRUI1_CD ");
        sql.append("            ,BUNRUI2_CD ");
        sql.append("            ,BUNRUI5_CD ");
        sql.append("            ,INSERT_USER_ID ");
        sql.append("            ,INSERT_TS ");
        sql.append("            ,UPDATE_USER_ID ");
        sql.append("            ,UPDATE_TS) ");
        sql.append("        VALUES ( ");
        sql.append("             DTT.COMP_CD ");
        sql.append("            ,DTT.YR ");
        sql.append("            ,DTT.MN ");
        sql.append("            ,DTT.DD ");
        sql.append("            ,DTT.TENPO_CD ");
        sql.append("            ,DTT.TANPIN_CD ");
        sql.append("            ,DTT.TANPIN_CD ");
        sql.append("            ,DTT.TIME_NO ");
        sql.append("            ,DTT.URIAGE_VL ");
        sql.append("            ,DTT.URIAGE_QT ");
        sql.append("            ,DTT.NEBIKI_REGI_VL ");
        sql.append("            ,DTT.NEBIKI_REGI_QT ");
        sql.append("            ,'0' ");
        sql.append("            ,DTT.BUNRUI1_CD ");
        sql.append("            ,DTT.BUNRUI2_CD ");
        sql.append("            ,DTT.BUNRUI5_CD ");
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("            ,? ");
        sql.append("        ) ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object input JikantaiZenkaiTanpinUriageSakuseiDaoInputBean
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
            DaoIf dao = new JikantaiZenkaiTanpinUriageSakuseiDao();
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
