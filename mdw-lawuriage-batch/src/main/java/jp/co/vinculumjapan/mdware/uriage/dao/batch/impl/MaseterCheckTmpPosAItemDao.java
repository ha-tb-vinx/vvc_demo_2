package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.dao.impl.TouKaikeiNengetsuSelectDao;
import jp.co.vinculumjapan.mdware.uriage.dao.input.TouKaikeiNengetsuSelectDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.output.TouKaikeiNengetsuSelectDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル: MaseterCheckTmpPosAItemDao クラス</p>
 * <p>説明:マスタチェック（Aレコード）</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.07.13) k.Hyo FIVI対応
 * @Version 1.01 (2016.11.10) Y.Itaki FIVI対応(#2725)
 * @Version 1.02 (2016.12.15) T.Kamei FIVI対応(#3344)
 * @Version 1.03 (2017.01.13) J.Endo  FIVI対応(#3502)
 * @Version 1.04 (2017.04.20) J.Endo  FIVI対応(#4704)
 * @Version 1.05 (2017/04/27) X.Liu   FIVI対応(#4553)
 * @Version 1.06 (2017/05/22) X.Liu   FIVI対応(#3770)
 * @Version 1.07 (2017/06/14) X.Liu   FIVI対応(#5399)
 * @Version 1.08 (2022/05/09) SIEU.D MKH対応(#6552)
 * @Version 1.09 (2023/02/02) SIEU.D MKH対応(#6716)
 * @Version 1.10 (2023/07/03) TUNG.LT #16388 MKH対応
 * @see なし
 */
public class MaseterCheckTmpPosAItemDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "マスタチェック（Aレコード）";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // バッチID
    private static final String BATCH_ID = "URIB012670";

    // #3344 2016.12.15 T.Kamei Add (S)
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // #3344 2016.12.15 T.Kamei Add (E)

    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_POS_A_ITEM";
    //private static final String DEL_SQL = "TRUNCATE TABLE TMP_POS_A_ITEM_CHECK_DATA";

    /**
     * マスタチェック（Aレコード）
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // Aレコードチェックデータを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            // #3344 2016.12.15 T.Kamei Add (S)
            // 当月度会計年月取得DAOInputBean
            TouKaikeiNengetsuSelectDaoInputBean inputKaikeiBean = new TouKaikeiNengetsuSelectDaoInputBean();
            // 当月度会計年月取得DAOOutputBean
            TouKaikeiNengetsuSelectDaoOutputBean outputKaikeiBean = new TouKaikeiNengetsuSelectDaoOutputBean();
            // 当月度会計年月取得DAO
            TouKaikeiNengetsuSelectDao daoSelect = new TouKaikeiNengetsuSelectDao();

            // 法人コード
            inputKaikeiBean.setCompCd(COMP_CD);

            // 入力ビーンをセット
            daoSelect.setInputObject(inputKaikeiBean);

            invoker.InvokeDao(daoSelect);

            // 出力ビーンをセット
            outputKaikeiBean = (TouKaikeiNengetsuSelectDaoOutputBean) daoSelect.getOutputObject();

            // 会計年月日
            String kaikeiDt = outputKaikeiBean.getUserKaikeiYr() + outputKaikeiBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;
            // #3344 2016.12.15 T.Kamei Add (E)

            String dbServerTime = FiResorceUtility.getDBServerTime();
            //マスタチェック（Aレコード）

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getMaseterCheckTmpPosAItemDaoSql(dbServerTime));

            int i=1;
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_ID);
            preparedStatementExIns.setString(i++, dbServerTime);
            preparedStatementExIns.setString(i++, BATCH_ID);
            preparedStatementExIns.setString(i++, dbServerTime);
            // #3344 2016.12.15 T.Kamei Add (S)
            preparedStatementExIns.setString(i++, kaikeiDt);
            preparedStatementExIns.setString(i++, kaikeiDt);
            preparedStatementExIns.setString(i++, kaikeiDt);
            preparedStatementExIns.setString(i++, kaikeiDt);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, BATCH_DT);
            preparedStatementExIns.setString(i++, COMP_CD);
            // #3344 2016.12.15 T.Kamei Add (E)
            //#5399 Add X.Liu 2017.06.14 (S)
            preparedStatementExIns.setString(i++, COMP_CD);
            //#5399 Add X.Liu 2017.06.14 (E)
            insertCount = preparedStatementExIns.executeUpdate();

            // #6552 ADD 2022.05.09 SIEU.D (S)
//            invoker.getDataBase().commit();
//            int delCount = invoker.getDataBase().prepareStatement(getDeleteItemHasQtyEqual0()).executeUpdate();
//            insertCount = insertCount - delCount;
            // #6552 ADD 2022.05.09 SIEU.D (E)

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件のAレコードチェックデータを追加しました。");
            

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

        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    private String getDeleteItemHasQtyEqual0() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE WK_POS_A_ITEM ");
        sql.append(" WHERE ");
        sql.append("   ( COMMAND || STORE || POS || TRANS_NO || CASHIER_ID || SKU ) ");
        sql.append("     IN ( ");
        sql.append("       SELECT ( COMMAND || STORE || POS || TRANS_NO || CASHIER_ID || SKU )   ");
        sql.append("       FROM WK_POS_A_ITEM ");
        sql.append("       WHERE ERR_KB = '0' ");
        sql.append("       GROUP BY COMMAND, STORE, POS, TRANS_NO, CASHIER_ID, SKU ");
        sql.append("       HAVING SUM(QTY) = 0 ");
        sql.append("     ) ");

        return sql.toString();
    }

    /**
     * マスタチェック（Aレコード）SQLを取得する
     *
     * @return マスタチェック（Aレコード）
     */
    private String getMaseterCheckTmpPosAItemDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        // 2016/11/10 VINX Y.Itaki FIVI(#2725) MOD(S)

        sql.append("INSERT /*+ APPEND */ INTO WK_POS_A_ITEM ( ");
        sql.append("     COMMAND ");
        sql.append("    ,STORE ");
        sql.append("    ,POS ");
        sql.append("    ,TRANS_NO ");
        sql.append("    ,CASHIER_ID ");
        sql.append("    ,FORMAT ");
        sql.append("    ,ATYPE ");
        sql.append("    ,ODR_LINE_IDX ");
        sql.append("    ,SKU ");
        sql.append("    ,QTY ");
        sql.append("    ,WEIGHT_SOLD ");
        sql.append("    ,REG_SELL ");
        sql.append("    ,ACTUAL_SELL2 ");
        sql.append("    ,ACTUAL_SELL ");
        sql.append("    ,REG_SELL_WOT ");
        sql.append("    ,ACTUAL_SELL_WOT2 ");
        sql.append("    ,ACTUAL_SELL_WOT ");
        sql.append("    ,EMP_PURCH ");
        sql.append("    ,ITEM_WEIGH ");
        sql.append("    ,REGULAR_UNIT_SELL ");
        sql.append("    ,GST_TAX ");
        sql.append("    ,DISC4_AMT ");
        sql.append("    ,ITEM_NAME_RECEIPT ");
        sql.append("    ,ITEM_UOM_RECEIPT ");
        sql.append("    ,PRCCHG_NO ");
        sql.append("    ,PRCCHG1_NO ");
        sql.append("    ,PRCCHG2_NO ");
        sql.append("    ,PRCCHG3_NO ");
        sql.append("    ,PRIVILEGE_MEM ");
        sql.append("    ,OVER_WRITE_FLAG ");
        sql.append("    ,HAMPER_ITEM_CD ");
        sql.append("    ,SUPERVISOR_ID ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");

        sql.append("SELECT ");
        sql.append("     TPAI.COMMAND ");
        sql.append("    ,TPAI.STORE ");
        sql.append("    ,TPAI.POS ");
        sql.append("    ,TPAI.TRANS_NO ");
        sql.append("    ,TPAI.CASHIER_ID ");
        sql.append("    ,TPAI.FORMAT ");
        sql.append("    ,TPAI.ATYPE ");
        // #6552 MOD 2022.06.01 SIEU.D (S)
         sql.append("    ,TPAI.ODR_LINE_IDX ");
//        sql.append("    , TRIM(TO_CHAR(DENSE_RANK() OVER ( ");
//        sql.append("          PARTITION BY SUM_DATA.COMMAND, SUM_DATA.STORE ,SUM_DATA.POS, SUM_DATA.TRANS_NO ,SUM_DATA.CASHIER_ID ,SUM_DATA.FORMAT ");
//        sql.append("          ORDER BY TPAI.ODR_LINE_IDX, SUM_DATA.SKU ");
//        sql.append("          ) , '000' ");
//        sql.append("       )) ODR_LINE_IDX");
        // #6552 MOD 2022.06.01 SIEU.D (E)
        sql.append("    ,TPAI.SKU ");
        // #6552 MOD 2022.06.01 SIEU.D (S)
        sql.append("    ,TPAI.QTY ");
        //2017.01.13 J.Endo FIVI対応(#3502) MOD(S)
        //sql.append("    ,TPAI.WEIGHT_SOLD ");
        sql.append("    ,LPAD(TO_CHAR(NVL(TPAI.WEIGHT_SOLD,0) * 1000,'FM00000000'),9,'0') ");
        //2017.01.13 J.Endo FIVI対応(#3502) MOD(E)
        sql.append("    ,TPAI.REG_SELL ");
        sql.append("    ,TPAI.ACTUAL_SELL2 ");
        sql.append("    ,TPAI.ACTUAL_SELL ");
        sql.append("    ,TPAI.REG_SELL_WOT ");
        sql.append("    ,TPAI.ACTUAL_SELL_WOT2 ");
        sql.append("    ,TPAI.ACTUAL_SELL_WOT ");
//        sql.append("    , REPLACE(TO_CHAR(SUM_DATA.QTY, 'S00000.00'),'+','0') QTY ");
//        sql.append("    ,LPAD(TO_CHAR(NVL(SUM_DATA.WEIGHT_SOLD,0) * 1000,'FM00000000'),9,'0') ");
//        sql.append("    ,REPLACE(TO_CHAR(SUM_DATA.REG_SELL , 'S0000000000000000000.00'),'+','0') REG_SELL ");
//        sql.append("    ,REPLACE(TO_CHAR(SUM_DATA.ACTUAL_SELL2 , 'S0000000000000000000.00'),'+','0') ACTUAL_SELL2 ");
//        sql.append("    ,REPLACE(TO_CHAR(SUM_DATA.ACTUAL_SELL , 'S0000000000000000000.00'),'+','0') ACTUAL_SELL ");
//        sql.append("    ,REPLACE(TO_CHAR(SUM_DATA.REG_SELL_WOT , 'S0000000000000000000.00'),'+','0') REG_SELL_WOT ");
//        sql.append("    ,REPLACE(TO_CHAR(SUM_DATA.ACTUAL_SELL_WOT2 , 'S0000000000000000000.00'),'+','0') ACTUAL_SELL_WOT2 ");
//        sql.append("    ,REPLACE(TO_CHAR(SUM_DATA.ACTUAL_SELL_WOT , 'S0000000000000000000.00'),'+','0') ACTUAL_SELL_WOT ");
        // #6552 MOD 2022.06.01 SIEU.D (E)
        sql.append("    ,TPAI.EMP_PURCH ");
        sql.append("    ,TPAI.ITEM_WEIGH ");
        sql.append("    ,TPAI.REGULAR_UNIT_SELL ");
        sql.append("    ,TPAI.GST_TAX ");
        sql.append("    ,TPAI.DISC4_AMT ");
        sql.append("    ,TPAI.ITEM_NAME_RECEIPT ");
        sql.append("    ,TPAI.ITEM_UOM_RECEIPT ");
        sql.append("    ,TPAI.PRCCHG_NO ");
        sql.append("    ,TPAI.PRCCHG1_NO ");
        sql.append("    ,TPAI.PRCCHG2_NO ");
        sql.append("    ,TPAI.PRCCHG3_NO ");
        sql.append("    ,TPAI.PRIVILEGE_MEM ");
        sql.append("    ,TPAI.OVER_WRITE_FLAG ");
        sql.append("    ,TPAI.HAMPER_ITEM_CD ");
        sql.append("    ,TPAI.SUPERVISOR_ID ");
        sql.append("    ,TPAI.EIGYO_DT ");
        sql.append("    ,? ");
        sql.append("    ,CASE ");
        // #3344 2016.12.15 T.Kamei Mod (S)
        //sql.append("         WHEN DPAI.STORE IS NULL THEN '0' ");
        //sql.append("         ELSE '1' ");
        //#3770 Mod X.Liu 2017.05.22 (S)
//        sql.append("         WHEN DPAI.STORE IS NOT NULL THEN '1' ");
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("         WHEN DPAI.STORE IS NOT NULL OR DPARC.STORE IS NOT NULL THEN '1' ");
        sql.append("         WHEN DTRH.TENPO_CD IS NOT NULL THEN '1' ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        //#3770 Mod X.Liu 2017.05.22 (E)
        sql.append("         WHEN (SEISAN_ST_DT IS NULL OR SEISAN_ED_DT IS NULL) OR (SUBSTR(TPAI.EIGYO_DT, 1, 8) < SEISAN_ST_DT OR SEISAN_ED_DT < SUBSTR(TPAI.EIGYO_DT, 1, 8)) THEN '4' ");
        //#4553 Mod X.Liu 2017.04.27 (S)
//        sql.append("         WHEN SEISAN_STATE_FG <> '0' THEN '9' ");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("         WHEN ISAN_SEISAN_STATE_FG <> '0' THEN '9' ");
        sql.append("  WHEN DTCSS.SEISAN_STATE_FG = '2' THEN '8'");
        sql.append("  WHEN RC.KARIZIMESYORI_KB <> '0' THEN '9'");
        //#5399 Mod X.Liu 2017.06.14 (E)
        //#4553 Mod X.Liu 2017.04.27 (E)
        //#3770 Add X.Liu 2017.05.22 (S)
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("WHEN (  ");
        // sql.append("        SELECT  ");
        // // #6716 MOD 2023.02.02 SIEU.D (S)
        // // sql.append("          SUM(ACTUAL_SELL)  ");
        // sql.append("          SUM(QTY)  ");
        // // #6716 MOD 2023.02.02 SIEU.D (E)
        // sql.append("        FROM    ");
        // sql.append("          WK_POS_A_ITEM_YOMIKAE TPAI_SUM    ");
        // sql.append("        WHERE   ");
        // sql.append("          TPAI.COMMAND = TPAI_SUM.COMMAND   ");
        // sql.append("          AND TPAI.EIGYO_DT = TPAI_SUM.EIGYO_DT     ");
        // sql.append("          AND TPAI.STORE = TPAI_SUM.STORE   ");
        // sql.append("          AND TPAI.POS = TPAI_SUM.POS   ");
        // sql.append("          AND TPAI.TRANS_NO = TPAI_SUM.TRANS_NO     ");
        // sql.append("          AND TPAI.CASHIER_ID = TPAI_SUM.CASHIER_ID     ");
        // sql.append("        GROUP BY    ");
        // sql.append("          COMMAND   ");
        // sql.append("          , EIGYO_DT    ");
        // sql.append("          , STORE   ");
        // sql.append("          , POS ");
        // sql.append("          , TRANS_NO    ");
        // sql.append("          , CASHIER_ID  ");
        // sql.append("      ) = 0     ");
        sql.append("  WHEN TPAI_SUM.SUM_QTY = 0 ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        sql.append("      THEN '7'  ");
        //#3770 Add X.Liu 2017.05.22 (E)
        sql.append("         ELSE '0' ");
        // #3344 2016.12.15 T.Kamei Mod (E)
        sql.append("     END ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        //2017.04.20 J.Endo FIVI対応(#4704) MOD(S)
        //sql.append("    TMP_POS_A_ITEM TPAI ");
        sql.append("    WK_POS_A_ITEM_YOMIKAE TPAI ");
        // #16388 ADD 2023.07.03 TUNG.LT (S)
        sql.append(" LEFT JOIN (  ");
        sql.append("        SELECT  ");
        sql.append("          COMMAND   ");
        sql.append("          , EIGYO_DT    ");
        sql.append("          , STORE   ");
        sql.append("          , POS ");
        sql.append("          , TRANS_NO    ");
        sql.append("          , CASHIER_ID  ");
        sql.append("          , SUM(QTY) AS SUM_QTY ");
        sql.append("        FROM    ");
        sql.append("          WK_POS_A_ITEM_YOMIKAE TPAI_SUM    ");
        sql.append("        GROUP BY    ");
        sql.append("          COMMAND   ");
        sql.append("          , EIGYO_DT    ");
        sql.append("          , STORE   ");
        sql.append("          , POS ");
        sql.append("          , TRANS_NO    ");
        sql.append("          , CASHIER_ID  ");
        sql.append("      ) TPAI_SUM    ");
        sql.append(" ON   ");
        sql.append("    TPAI.COMMAND = TPAI_SUM.COMMAND   ");
        sql.append("    AND TPAI.EIGYO_DT = TPAI_SUM.EIGYO_DT     ");
        sql.append("    AND TPAI.STORE = TPAI_SUM.STORE   ");
        sql.append("    AND TPAI.POS = TPAI_SUM.POS   ");
        sql.append("    AND TPAI.TRANS_NO = TPAI_SUM.TRANS_NO     ");
        sql.append("    AND TPAI.CASHIER_ID = TPAI_SUM.CASHIER_ID     ");
        // #16388 ADD 2023.07.03 TUNG.LT (E)
        //2017.04.20 J.Endo FIVI対応(#4704) MOD(E)
        // #6552 ADD 2022.06.01 SIEU.D (S)
//        sql.append(" INNER JOIN ( ");
//        sql.append("     SELECT ");
//        sql.append("         COMMAND ");
//        sql.append("         , STORE  ");
//        sql.append("         , POS ");
//        sql.append("         , TRANS_NO  ");
//        sql.append("         , CASHIER_ID  ");
//        sql.append("         , FORMAT ");
//        sql.append("         , SKU  ");
//        sql.append("         , SUM(QTY)QTY ");
//        sql.append("         , SUM(WEIGHT_SOLD) WEIGHT_SOLD ");
//        sql.append("         , SUM(REG_SELL) REG_SELL ");
//        sql.append("         , SUM(ACTUAL_SELL2) ACTUAL_SELL2 ");
//        sql.append("         , SUM(ACTUAL_SELL) ACTUAL_SELL ");
//        sql.append("         , SUM(REG_SELL_WOT) REG_SELL_WOT ");
//        sql.append("         , SUM(ACTUAL_SELL_WOT2) ACTUAL_SELL_WOT2 ");
//        sql.append("         , SUM(ACTUAL_SELL_WOT) ACTUAL_SELL_WOT ");
//        sql.append("     FROM WK_POS_A_ITEM_YOMIKAE ");
//        sql.append("     GROUP BY COMMAND ");
//        sql.append("         , STORE  ");
//        sql.append("         , POS ");
//        sql.append("         , TRANS_NO  ");
//        sql.append("         , CASHIER_ID  ");
//        sql.append("         , FORMAT ");
//        sql.append("         , SKU ");
//        sql.append(" ) SUM_DATA ON ");
//        sql.append("     TPAI.COMMAND             = SUM_DATA.COMMAND ");
//        sql.append("     AND TPAI.STORE           = SUM_DATA.STORE ");
//        sql.append("     AND TPAI.POS               = SUM_DATA.POS ");
//        sql.append("     AND TPAI.TRANS_NO   = SUM_DATA.TRANS_NO ");
//        sql.append("     AND TPAI.CASHIER_ID = SUM_DATA.CASHIER_ID ");
//        sql.append("     AND TPAI.FORMAT        = SUM_DATA.FORMAT ");
//        sql.append("     AND TPAI.SKU               = SUM_DATA.SKU ");
//        sql.append("     AND SUBSTR(TPAI.ATYPE, 2 ,1) = ( ");
//        sql.append("           CASE WHEN SUM_DATA.QTY < 0 THEN '3' ");
//        sql.append("           ELSE '0' END ");
//        sql.append("     ) ");
        // #6552 ADD 2022.06.01 SIEU.D (E)
        // #16388 MOD 2023.07.03 TUNG.LT (S)
        // sql.append("LEFT OUTER JOIN ");
        // sql.append("    DT_POS_A_ITEM DPAI ");
        // sql.append("ON ");
        // sql.append("    TPAI.STORE = DPAI.STORE AND ");
        // sql.append("    TPAI.POS = DPAI.POS AND ");
        // sql.append("    TPAI.TRANS_NO = DPAI.TRANS_NO AND ");
        // sql.append("    TPAI.EIGYO_DT = DPAI.EIGYO_DT ");
        // //#3770 Add X.Liu 2017.05.22 (S)
        // sql.append(" LEFT OUTER JOIN DT_POS_A_RECEIPT_CANCEL DPARC  ");
        // sql.append("  ON TPAI.STORE = DPARC.STORE   ");
        // sql.append("  AND TPAI.POS = DPARC.POS  ");
        // sql.append("  AND TPAI.TRANS_NO = DPARC.TRANS_NO    ");
        // sql.append("  AND TPAI.EIGYO_DT = DPARC.EIGYO_DT    ");
        sql.append(" LEFT OUTER JOIN DT_TEN_RECEIPT_H DTRH  ");
        sql.append("  ON TPAI.STORE = SUBSTR(DTRH.TENPO_CD, 3, 6)   ");
        sql.append("  AND TPAI.POS = DTRH.REGI_RB  ");
        sql.append("  AND TPAI.TRANS_NO = DTRH.TERMINAL_RB    ");
        sql.append("  AND TPAI.EIGYO_DT = DTRH.EIGYO_DT    ");
        // #16388 MOD 2023.07.03 TUNG.LT (E)
        //#3770 Add X.Liu 2017.05.22 (E)
        // #3344 2016.12.15 T.Kamei Add (S)
        // ＜SUB店舗マスタ＞
        sql.append("LEFT JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            RT.TENPO_CD ");
        sql.append("           ,CASE ");
        sql.append("                WHEN RT.SEISAN_ST_DT IS NULL THEN ? ");
        sql.append("                ELSE ");
        sql.append("                    CASE ");
        sql.append("                        WHEN RT.SEISAN_ST_DT >= ? THEN RT.SEISAN_ST_DT ");
        sql.append("                        ELSE ");
        sql.append("                            CASE ");
        sql.append("                                WHEN RT.SEISAN_ED_DT IS NULL OR RT.SEISAN_ED_DT >= ? THEN ? ");
                                                    // 上記以外の場合はSEISAN_ST_DT、SEISAN_ED_DT共に会計年月より過去となるためnullを設定する。
        sql.append("                                ELSE NULL ");
        sql.append("                            END ");
        sql.append("                    END ");
        sql.append("            END AS SEISAN_ST_DT ");
        sql.append("           ,CASE ");
        sql.append("                WHEN RT.SEISAN_ED_DT IS NULL THEN ? ");
        sql.append("                ELSE ");
        sql.append("                    CASE ");
        sql.append("                        WHEN RT.SEISAN_ED_DT <= ? THEN RT.SEISAN_ED_DT ");
        sql.append("                        ELSE ");
        sql.append("                            CASE ");
        sql.append("                                WHEN RT.SEISAN_ST_DT IS NULL OR RT.SEISAN_ST_DT <= ? THEN ? ");
                                                    // 上記以外の場合は精算可能開始日、精算可能終了日共に会計年月より未来となるためnullを設定する。
        sql.append("                                ELSE NULL ");
        sql.append("                            END ");
        sql.append("                    END ");
        sql.append("            END AS SEISAN_ED_DT ");
        sql.append("        FROM ");
        sql.append("            R_TENPO RT ");
        sql.append("                WHERE ");
        sql.append("                    RT.HOJIN_CD      = ?    AND ");
        sql.append("                    RT.TENPO_KB      = '1'  AND ");
        sql.append("                    RT.KAITEN_DT    <= ?    AND ");
        sql.append("                    RT.ZAIMU_END_DT >= ?    AND ");
        sql.append("                    RT.DELETE_FG     = '0' ");
        sql.append("    ) RT ");
        sql.append("ON ");
        sql.append("    RT.TENPO_CD = '00' || TPAI.STORE ");
        // ＜SUB店別精算状況データ＞
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("LEFT JOIN ");
//        sql.append("    ( ");
//        sql.append("        SELECT ");
//        sql.append("            KEIJO_DT ");
//        sql.append("           ,TENPO_CD ");
//        //#4553 Mod X.Liu 2017.04.27 (S)
////        sql.append("           ,SEISAN_STATE_FG ");
//        sql.append("           ,ISAN_SEISAN_STATE_FG ");
//        //#4553 Mod X.Liu 2017.04.27 (E)
//        sql.append("        FROM ");
//        sql.append("            DT_TEN_SEISAN_STATE ");
//        sql.append("        WHERE ");
//        sql.append("            COMP_CD = ? ");
//        sql.append("    ) DTSS ");
//        sql.append("ON ");
//        sql.append("    DTSS.KEIJO_DT = SUBSTR(TPAI.EIGYO_DT, 1, 8) AND ");
//        sql.append("    DTSS.TENPO_CD = '00' || TPAI.STORE ");
        // #3344 2016.12.15 T.Kamei Add (E)

        // 2016/11/10 VINX Y.Itaki FIVI(#2725) MOD(E)
        sql.append("  LEFT JOIN ( ");
        sql.append("    SELECT");
        sql.append("      KEIJO_DT");
        sql.append("      , TENPO_CD");
        sql.append("      , CHECKER_CD");
        sql.append("      , SEISAN_STATE_FG ");
        sql.append("    FROM");
        sql.append("      DT_TEN_CHECKER_SEISAN_STATE ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ?");
        sql.append("  ) DTCSS ");
        sql.append("    ON DTCSS.KEIJO_DT = SUBSTR(TPAI.EIGYO_DT, 1, 8) ");
        sql.append("    AND DTCSS.TENPO_CD = '00' || TPAI.STORE ");
        sql.append("    AND DTCSS.CHECKER_CD = TPAI.CASHIER_ID ");
        sql.append("  LEFT JOIN ( ");
        sql.append("    SELECT");
        sql.append("      KARIZIMESYORI_KB");
        sql.append("      , START_DT");
        sql.append("      , END_DT ");
        sql.append("    FROM");
        sql.append("      R_CALENDAR ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ?");
        sql.append("  ) RC ");
        sql.append("    ON RC.START_DT <= SUBSTR(TPAI.EIGYO_DT, 1, 8) ");
        sql.append("    AND RC.END_DT >= SUBSTR(TPAI.EIGYO_DT, 1, 8)");
        //#5399 Mod X.Liu 2017.06.14 (E)

        return sql.toString();
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

    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new VatInvoiceRegistDao();
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
