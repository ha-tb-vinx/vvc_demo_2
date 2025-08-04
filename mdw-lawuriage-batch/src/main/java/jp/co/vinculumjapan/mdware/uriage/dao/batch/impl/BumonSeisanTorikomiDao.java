/**
 *
 */
package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.BumonSeisanTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;


/**
 * <p>タイトル: BumonSeisanTorikomiDao クラス</p>
 * <p>説明　: POS実績取込処理(部門精算)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.17) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * @Version 3.02 (2016.05.12) VINX H.Yaguma FIVI対応
 * @Version 3.03 (2016.06.1) VINX to FIVI対応
 * @Version 3.04 (2016.06.02) VINX K.Hirano FIVI(#1505対応)
 * @Version 3.05 (2016.06.03) VINX K.Hirano FIVI(#1505対応)
 * @Version 3.06 (2016.08.26) VINX K.Hirano FIVI(#1879対応)
 */
public class BumonSeisanTorikomiDao implements DaoIf {


    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    // 2016.06.02 VINX K.Hirano FIVI(#1505対応) ADD(S)
    // 削除SQL文用定数
    private static final String DEL_SQL = "deleteTmpBumonSeisan";
    // 2016.06.02 VINX K.Hirano FIVI(#1505対応) ADD(E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理(部門精算)";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMP部門精算データ";

    @Override
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        // 2016.06.03 VINX K.Hirano FIVI(#1505対応) ADD(S)
        PreparedStatementEx preparedStatementDelete = null;
        // 2016.06.03 VINX K.Hirano FIVI(#1505対応) ADD(E)

        int insertCount = 0;
        String dbServerTime = "";
        try{
            // 2016.06.03 VINX K.Hirano FIVI(#1505対応) ADD(S)
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();
            // 2016.06.03 VINX K.Hirano FIVI(#1505対応) ADD(E)

            // TMP部門精算データの追加

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を開始します。");

            dbServerTime = FiResorceUtility.getDBServerTime();
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTmpBumonSeisanInsertSql());
            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, userId);
            preparedStatementExIns.setString(3, dbServerTime);
            preparedStatementExIns.setString(4, userId);
            preparedStatementExIns.setString(5, dbServerTime);
            preparedStatementExIns.setString(6, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を終了します。");
    
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
     * TMP部門精算データ登録用SQLを取得する
     *
     * @return TMP部門精算データ登録用SQL
     */
    private String getTmpBumonSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO TMP_BUMON_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUMON_CD ");
        sql.append("    ,URIAGE_KYAKU_QT ");
        sql.append("    ,URIAGE_SOURI_QT ");
        sql.append("    ,URIAGE_SOURI_VL ");
        sql.append("    ,URIAGE_HITEIBAN_QT ");
        sql.append("    ,URIAGE_HITEIBAN_VL ");
        sql.append("    ,LOS_QT ");
        sql.append("    ,LOS_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,NEBIKI_SC_QT ");
        sql.append("    ,NEBIKI_SC_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,HENPIN_VL ");
        sql.append("    ,GAISAN_ARARI_SOURI_VL ");
        sql.append("    ,GAISAN_ARARI_HITEIBAN_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        // 2016/08/26 k.Hyo #1879対応 (S)
        //sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB) ");
        // 2016/08/26 k.Hyo #1879対応 (E)      
        sql.append("SELECT ");
        sql.append("     ? ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        // vinx to #1480対応 20160601 (S)
//        sql.append("    ,KEIJO_DT ");
        sql.append("    ,SUBSTR(KEIJO_DT,1,4)||'/'||SUBSTR(KEIJO_DT,5,2)||'/'||SUBSTR(KEIJO_DT,7,2) ");
        // vinx to #1480対応 20160601 (E)
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,0 ");
        sql.append("    ,sum(URIAGE_SOURI_QT) ");
        sql.append("    ,sum(URIAGE_SOURI_VL) ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        // 2016/08/26 k.Hyo #1879対応 (S)
        //sql.append("    ,0 ");
        //sql.append("    ,0 ");
        sql.append("    ,sum(HENPIN_QT) ");
        sql.append("    ,sum(HENPIN_KOMI_VL) ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // 2016/08/26 k.Hyo #1879対応 (S)
        sql.append("    ,sum(HANBAI_WEIGHT_QT) ");
        sql.append("    ,sum(URIAGE_NUKI_SOURI_VL) ");
        sql.append("    ,sum(URIAGE_ZEI_VL) ");
        sql.append("    ,sum(HENPIN_WEIGHT) ");
        sql.append("    ,sum(HENPIN_NUKI_VL) ");
        sql.append("    ,sum(HENPIN_ZEI_VL) ");
        sql.append("    ,URIAGE_KB ");
        // 2016/08/26 k.Hyo #1879対応 (E)
        sql.append("FROM ");
        sql.append("    DT_TANPIN_SEISAN_HMPR_BR_CK ");
        sql.append("WHERE ");
        sql.append("    DATA_SAKUSEI_DT = ? ");
        sql.append("GROUP BY ");
        // 2016/08/26 k.Hyo #1879対応 (S)
        //sql.append("    KEIJO_DT,TENPO_CD,BUNRUI1_CD ");
        sql.append("    KEIJO_DT,TENPO_CD,BUNRUI1_CD,URIAGE_KB ");
        // 2016/08/26 k.Hyo #1879対応 (E)

        return sql.toString();
    }


    /** インプットBean */
    private BumonSeisanTorikomiDaoInputBean inputBean = null;

    /**
     * アウトプットBeanを取得する
     *
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input BumonSeisanTorikomiDaoInputBean型オブジェクト
     */
    public void setInputObject(Object input) throws Exception {
        inputBean = (BumonSeisanTorikomiDaoInputBean) input;
        
    }
}
