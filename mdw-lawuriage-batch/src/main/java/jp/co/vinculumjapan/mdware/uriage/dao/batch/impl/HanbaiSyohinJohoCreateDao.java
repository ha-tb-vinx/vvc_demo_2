package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.common.util.DateChanger;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: HanbaiSyohinJohoCreateDaoクラス</p>
 * <p>説明　: 販売商品情報データ保管処理</p>
 * <p>著作権: Copyright (c) 2020</p>
 * <p>会社名: VVC</p>
 * @Version 1.00 #6288 (2020.12.25) THONG.VQ MKV対応
 * @Version 1.01 #6513 (2022.02.11) KHAI.NN MKV対応
 * @Version 1.02 #18254 (2024.03.08) TUNG.LT MK対応
 */
public class HanbaiSyohinJohoCreateDao implements DaoIf {

    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // #6513 Del 2022.02.11 KHAI.NN (S)
    //private static final String PREVIOUS_DT = DateChanger.addDate(BATCH_DT, -1);
    // #6513 Del 2022.02.11 KHAI.NN (E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "販売商品情報データ保管";
    /** 登録先テーブル名称(販売商品情報データ) */
    private static final String DT_HANBAI_SYOHIN_JOHO_TABLE_NAME = "販売商品情報データ";

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

        try {
            // 単品精算エラーデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_HANBAI_SYOHIN_JOHO_TABLE_NAME + "の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getHanbaiSyohinJohoInsertSql());
            int i = 1;
            // #6513 Del 2022.02.11 KHAI.NN (S)
            //preparedStatementExIns.setString(i++, PREVIOUS_DT);
            //preparedStatementExIns.setString(i++, PREVIOUS_DT);
            // #6513 Del 2022.02.11 KHAI.NN (E)
            // #18254 DEL 2024/03/08 TUNG.LT (S)
            // preparedStatementExIns.setString(i++, UriageCommonConstants.FG_OFF);
            // #18254 DEL 2024/03/08 TUNG.LT (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + DT_HANBAI_SYOHIN_JOHO_TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_HANBAI_SYOHIN_JOHO_TABLE_NAME + "の追加を終了します。");

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
       * 販売商品情報データ登録用SQLを取得する
     *
     * @return 販売商品情報データ登録用SQL
     */
    private String getHanbaiSyohinJohoInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_HANBAI_SYOHIN_JOHO ( ");
        sql.append("     EIGYO_DT ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,SKU ");
        sql.append("    ,ITEM_NAME_RECEIPT ");
        sql.append("    ,SIIRESAKI_CD ) ");
        sql.append("SELECT ");
        sql.append("  RST.*  ");
        sql.append("FROM ");
        sql.append("  (  ");
        sql.append("    SELECT DISTINCT ");
        sql.append("      DPAI.EIGYO_DT ");
        sql.append("      , DPAI.STORE ");
        sql.append("      , DPAI.POS ");
        sql.append("      , DPAI.TRANS_NO ");
        sql.append("      , DPAI.CASHIER_ID ");
        sql.append("      , RS.BUNRUI1_CD ");
        sql.append("      , RS.BUNRUI2_CD ");
        sql.append("      , RS.BUNRUI5_CD ");
        sql.append("      , DPAI.SKU ");
        sql.append("      , DPAI.ITEM_NAME_RECEIPT ");
        // #18254 MOD 2024/06/05 TUNG.LT (S)
        // sql.append("      , RS.SIIRESAKI_CD ");
        sql.append("      , NVL(RTR.SIIRESAKI_CD, RS.SIIRESAKI_CD) SIIRESAKI_CD ");
        // #18254 MOD 2024/06/05 TUNG.LT (E)
        sql.append("    FROM ");
        sql.append("      (  ");
        sql.append("        SELECT ");
        sql.append("          EIGYO_DT ");
        sql.append("          , STORE ");
        sql.append("          , POS ");
        sql.append("          , TRANS_NO ");
        sql.append("          , CASHIER_ID ");
        sql.append("          , SKU ");
        sql.append("          , QTY ");
        sql.append("          , ITEM_NAME_RECEIPT ");
        sql.append("        FROM ");
        sql.append("          DT_POS_A_ITEM DPAI  ");
        sql.append("        WHERE ");
        // #6513 Mod 2022.02.11 KHAI.NN (S)
        //sql.append("          TRIM(EIGYO_DT) = ? ");
        sql.append("          NOT EXISTS ( ");
        sql.append("                SELECT ");
        sql.append("                    'x' ");
        sql.append("                FROM ");
        sql.append("                    DT_HANBAI_SYOHIN_JOHO DHSJ ");
        sql.append("                WHERE ");
        sql.append("                    DHSJ.STORE = DPAI.STORE ");
        sql.append("                    AND DHSJ.POS = DPAI.POS ");
        sql.append("                    AND DHSJ.TRANS_NO = DPAI.TRANS_NO ");
        sql.append("                    AND DHSJ.CASHIER_ID = DPAI.CASHIER_ID ");
        sql.append("                    AND DHSJ.EIGYO_DT = DPAI.EIGYO_DT ");
        sql.append("                    AND DHSJ.SKU = DPAI.SKU ");
        sql.append("          ) ");
        // #6513 Mod 2022.02.11 KHAI.NN (E)
        sql.append("      ) DPAI ");
        sql.append("      INNER JOIN (  ");
        sql.append("        SELECT ");
        sql.append("          TORI_TIME ");
        sql.append("          , STORE ");
        sql.append("          , POS ");
        sql.append("          , TRANS_NO ");
        sql.append("          , CASHIER_ID ");
        sql.append("          , EIGYO_DT  ");
        sql.append("        FROM ");
        // #6513 Mod 2022.02.11 KHAI.NN (S)
        //sql.append("          DT_POS_B_TOTAL  ");
        sql.append("          DT_POS_B_TOTAL DPBT ");
        // #6513 Mod 2022.02.11 KHAI.NN (E)
        sql.append("        WHERE ");
        // #6513 Mod 2022.02.11 KHAI.NN (S)
        //sql.append("          TRIM(EIGYO_DT) = ? ");
        sql.append("          NOT EXISTS ( ");
        sql.append("                SELECT ");
        sql.append("                    'x' ");
        sql.append("                FROM ");
        sql.append("                    DT_HANBAI_SYOHIN_JOHO DHSJ ");
        sql.append("                WHERE ");
        sql.append("                    DHSJ.STORE = DPBT.STORE ");
        sql.append("                    AND DHSJ.POS = DPBT.POS ");
        sql.append("                    AND DHSJ.TRANS_NO = DPBT.TRANS_NO ");
        sql.append("                    AND DHSJ.CASHIER_ID = DPBT.CASHIER_ID ");
        sql.append("                    AND DHSJ.EIGYO_DT = DPBT.EIGYO_DT ");
        sql.append("          ) ");
        // #6513 Mod 2022.02.11 KHAI.NN (E)
        sql.append("      ) DPBT  ");
        sql.append("        ON DPAI.STORE = DPBT.STORE  ");
        sql.append("        AND DPAI.POS = DPBT.POS  ");
        sql.append("        AND DPAI.TRANS_NO = DPBT.TRANS_NO  ");
        sql.append("        AND DPAI.CASHIER_ID = DPBT.CASHIER_ID  ");
        sql.append("        AND DPAI.EIGYO_DT = DPBT.EIGYO_DT  ");
        sql.append("      INNER JOIN (  ");
        sql.append("        SELECT DISTINCT ");
        sql.append("          SYOHIN_CD ");
        sql.append("          , BUNRUI1_CD ");
        sql.append("          , BUNRUI5_CD  ");
        sql.append("          , BUNRUI2_CD  ");
        sql.append("          , SIIRESAKI_CD  ");
        sql.append("          , YUKO_DT ");
        sql.append("          , DELETE_FG ");
        sql.append("        FROM ");
        sql.append("          R_SYOHIN ");
        sql.append("      ) RS  ");
        sql.append("        ON DPAI.SKU = RS.SYOHIN_CD  ");
        // #18254 DEL 2024/03/08 TUNG.LT (S)
        // sql.append("        AND RS.DELETE_FG = ? ");
        // #18254 DEL 2024/03/08 TUNG.LT (E)
        sql.append("        AND RS.YUKO_DT = ( ");
        sql.append("             SELECT ");
        sql.append("                 MAX(RMS1.YUKO_DT) ");
        sql.append("             FROM ");
        sql.append("                 R_SYOHIN RMS1 ");
        sql.append("             WHERE ");
        sql.append("                 RMS1.SYOHIN_CD = DPAI.SKU ");
        sql.append("                 AND RMS1.YUKO_DT <= DPAI.EIGYO_DT ");
        sql.append("         ) ");
        // #18254 ADD 2024/06/05 TUNG.LT (S)
        sql.append("      INNER JOIN R_TENSYOHIN_REIGAI RTR  ");
        sql.append("        ON DPAI.SKU          = RTR.SYOHIN_CD  ");
        sql.append("        AND '00'||DPAI.STORE = RTR.TENPO_CD  ");
        sql.append("        AND RTR.YUKO_DT = ( ");
        sql.append("             SELECT ");
        sql.append("                 MAX(RTR2.YUKO_DT) ");
        sql.append("             FROM ");
        sql.append("                 R_TENSYOHIN_REIGAI RTR2 ");
        sql.append("             WHERE ");
        sql.append("                 RTR2.SYOHIN_CD    = RTR.SYOHIN_CD ");
        sql.append("                 AND RTR2.TENPO_CD = RTR.TENPO_CD ");
        sql.append("                 AND RTR2.YUKO_DT <= DPAI.EIGYO_DT ");
        sql.append("         ) ");
        // #18254 ADD 2024/06/05 TUNG.LT (E)
        sql.append("     ) RST ");

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
}
