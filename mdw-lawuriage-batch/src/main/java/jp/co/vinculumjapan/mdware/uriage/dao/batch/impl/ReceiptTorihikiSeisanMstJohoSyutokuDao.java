package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.ErrorKbDictionary;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
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

/**
 * <p>タイトル: ReceiptTorihikiSeisanMstJohoSyutokuDao クラス</p>
 * <p>説明　: マスタ情報取得処理（レシート別取引精算）</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.05.16) VINX S.Kashihara FIVI対応
 * @Version 1.01 (2016.12.14) VINX T.Kamei FIVI対応(#3306)
 * @Version 1.02 (2017.01.19) VINX J.Endo FIVI対応 Cレコードが複数レコード発生する対応 #3468
 * @Version 1.03 (2017.03.09) N.Katou #3760
 * @Version 1.04 (2017.04.10) X.Liu #4553
 * @Version 1.05 (2017.06.21) X.Liu #5421
 * @Version 1.06 (2017.07.10) J.Endo #5040
 * @Version 1.07 (2017.08.31) N.Katou #5840
 */
public class ReceiptTorihikiSeisanMstJohoSyutokuDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 金額MAX値 */
    //#5040 MOD J.Endo 2017.07.10 (S)
    //private static final long KINGAKU_MAXNUM = 9999999999999L;
    private static final String KINGAKU_MAXNUM = "999999999999999.99";
    //#5040 MOD J.Endo 2017.07.10 (E)

    /** バッチ処理名 */
    private static final String DAO_NAME = "マスタ情報取得処理（レシート別取引精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "有効レシート別取引精算ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_RECEIPT_TORI_SEISAN";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        //
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        // 当月度会計年月取得DAO
        TouKaikeiNengetsuSelectDaoInputBean inputBean = new TouKaikeiNengetsuSelectDaoInputBean();
        // 法人コード
        inputBean.setCompCd(COMP_CD);

        // 当月度会計年月取得DAOの実行
        TouKaikeiNengetsuSelectDao touKaikeiNengetsu = new TouKaikeiNengetsuSelectDao();
        TouKaikeiNengetsuSelectDaoOutputBean outputBean = new TouKaikeiNengetsuSelectDaoOutputBean();

        // 入力ビーンをセット
        touKaikeiNengetsu.setInputObject(inputBean);

        invoker.InvokeDao(touKaikeiNengetsu);

        // 出力ビーンをセット
        outputBean = (TouKaikeiNengetsuSelectDaoOutputBean) touKaikeiNengetsu.getOutputObject();

        // 会計年月日
        String kaikeiDt = outputBean.getUserKaikeiYr() + outputBean.getUserKaikeiMn() + UriageCommonConstants.FIRST_DAY;

        int insertCount = 0;
        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

           String dbServerTime = FiResorceUtility.getDBServerTime();
            // TMP責任者精算データから有効責任者取引精算データワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTmpSekininsyaSeisanInsertSql());

            int intI = 1;

            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_01.getCode());  // 01：店舗エラー
            preparedStatementExIns.setString(intI++, kaikeiDt);
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_04.getCode());  // 04：過去日エラー
            //#5040 MOD J.Endo 2017.07.10 (S)
            //preparedStatementExIns.setDouble(intI++, KINGAKU_MAXNUM);
            preparedStatementExIns.setString(intI++, KINGAKU_MAXNUM);
            //#5040 MOD J.Endo 2017.07.10 (E)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_05.getCode()); // 05：桁数エラー
            // 2017/08/31 VINX N.Katou #5840対応 (S)
//            preparedStatementExIns.setString(intI++, ErrorKbDictionary.ERROR_09.getCode()); // 09：取込済エラー
            // 2017/08/31 VINX N.Katou #5840対応 (E)
            preparedStatementExIns.setString(intI++, ErrorKbDictionary.NORMAL_00.getCode());
            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, dbServerTime);
            preparedStatementExIns.setString(intI++, userId);
            preparedStatementExIns.setString(intI++, dbServerTime);

            preparedStatementExIns.setString(intI++, COMP_CD);
            preparedStatementExIns.setString(intI++, BATCH_DT);
            preparedStatementExIns.setString(intI++, BATCH_DT);

            // #3306 2016.12.14 T.Kamei DEL (S)
            //preparedStatementExIns.setString(intI++, COMP_CD);
            //preparedStatementExIns.setString(intI++, BATCH_DT);//計上日？
            // #3306 2016.12.14 T.Kamei DEL (E)
            preparedStatementExIns.setString(intI++, COMP_CD);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

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
            try {
                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

    }

    /**
     * TMP責任者精算データから有効責任者精算ワークへ登録するSQLを取得する
     *
     * @return 有効レジ別取引精算データ登録クエリー
     */
    private String getTmpSekininsyaSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        // C: 有効レシート別取引精算ワーク
        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_RECEIPT_TORI_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TRAN_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,CREDIT_NO ");
        sql.append("    ,SYONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,MERCHANT_CODE ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // #3760 URIB012560 2017/3/09 N.katou(S)
        // #3468 2017.01.19 J.Endo Add (S)
//        sql.append("    ,KINSYU_VL) ");
        sql.append("    ,KINSYU_VL ");
        // #3468 2017.01.19 J.Endo Add (E)
        // 2017/08/31 VINX N.Katou #5840対応 (S)
//        sql.append("    ,POINT_ISSUDE_DT) ");
        sql.append("    ,POINT_ISSUDE_DT ");
        // #3760 URIB012560 2017/3/09 N.katou(E)
        sql.append("    ,SYUUKIN_VL ");
        sql.append("    ,CYOUKA_VL ");
        sql.append("    ) ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("SELECT  ");
        sql.append("     COMP_CD  ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TRAN_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,CHECKER_CD ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,CREDIT_NO ");
        sql.append("    ,SYONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("    ,TERMINAL_ID ");
        sql.append("    ,TRACE_NO ");
        sql.append("    ,MERCHANT_CODE ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("    ,CASE ");
        sql.append("        WHEN RT_TENPO_CD IS NULL THEN ? "); // 01：店舗エラー
        sql.append("        WHEN EIGYO_DT IS NULL OR EIGYO_DT < ? THEN ? "); // 04：計上日エラー
        sql.append("        WHEN KINGAKU_VL > ? THEN ? "); // 05：桁数エラー
        // #3306 2016.12.14 T.Kamei Mod (S)
        //sql.append("        WHEN DTSS_SEISAN_STATE_FG >= '1' THEN ? "); // 09：取込済エラー
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        WHEN DTSS_SEISAN_STATE_FG <> '0' THEN ? "); // 09：取込済エラー
        // 2017/08/31 VINX N.Katou #5840対応 (S)
//        sql.append("        WHEN DTSS_ISAN_SEISAN_STATE_FG <> '0' THEN ? "); // 09：取込済エラー
        // 2017/08/31 VINX N.Katou #5840対応 (W)
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3306 2016.12.14 T.Kamei Mod (E)
        sql.append("        ELSE ? ");
        sql.append("     END AS ERR_KB ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        // #3468 2017.01.19 J.Endo Add (S)
//        sql.append("    ,KINSYU_VL) ");
        sql.append("    ,KINSYU_VL ");
        // #3468 2017.01.19 J.Endo Add (E)
        // #3760 URIB012560 2017/3/09 N.katou(S)
        sql.append("    ,POINT_ISSUDE_DT ");
        // #3760 URIB012560 2017/3/09 N.katou(E)
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("    ,SYUUKIN_VL ");
        sql.append("    ,CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("FROM ");
        sql.append("    ( ");

        // R メイン: TMPレシート別取引精算データ
        sql.append("    SELECT   ");
        sql.append("         COMP_CD ");
        sql.append("        ,TRTS.EIGYO_DT ");
        sql.append("        ,TRTS.TENPO_CD ");
        sql.append("        ,TRTS.REGI_NO ");
        sql.append("        ,TRTS.TRAN_CD ");
        sql.append("        ,TRTS.SHIHARAI_SYUBETSU_CD ");
        sql.append("        ,TRTS.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("        ,TRTS.CHECKER_CD ");
        sql.append("        ,TRTS.KINGAKU_VL ");
        sql.append("        ,TRTS.CREDIT_NO ");
        sql.append("        ,TRTS.SYONIN_NO ");
        //#5421 Add X.Liu 2017.06.21 (S)
        sql.append("        ,TRTS.TERMINAL_ID ");
        sql.append("        ,TRTS.TRACE_NO ");
        sql.append("        ,TRTS.MERCHANT_CODE ");
        //#5421 Add X.Liu 2017.06.21 (E)
        sql.append("        ,RT.TENPO_CD AS RT_TENPO_CD ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("        ,DTSS.DTSS_SEISAN_STATE_FG ");
        sql.append("        ,DTSS.DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        // #3468 2017.01.19 J.Endo Add (S)
        sql.append("        ,TRTS.KINSYU_VL ");
        // #3468 2017.01.19 J.Endo Add (E)
        // #3760 URIB012560 2017/3/09 N.katou(S)
        sql.append("        ,TRTS.POINT_ISSUDE_DT ");
        // #3760 URIB012560 2017/3/09 N.katou(E)
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("        ,TRTS.SYUUKIN_VL ");
        sql.append("        ,TRTS.CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("    FROM   ");
        sql.append("        TMP_RECEIPT_TORIHIKI_SEISAN TRTS   ");

        // R 外部結合: 店舗マスタ
        sql.append("        LEFT JOIN   ");
        sql.append("            R_TENPO RT  ");
        sql.append("        ON   ");
        sql.append("            RT.HOJIN_CD      = ?      AND  ");
        sql.append("            RT.TENPO_KB      = '1'    AND  ");
        sql.append("            RT.KAITEN_DT    <= ?      AND  ");
        sql.append("            RT.ZAIMU_END_DT >= ?      AND  ");
        sql.append("            RT.DELETE_FG     = '0'    AND  ");
        sql.append("            RT.TENPO_CD = TRTS.TENPO_CD   ");

        // R 外部結合: 店別精算状況データ
        sql.append("        LEFT JOIN ");
        sql.append("            ( ");
        sql.append("                SELECT ");
        // #3306 2016.12.14 T.Kamei Mod (S)
        sql.append("                    COMP_CD               AS DTSS_COMP_CD  ");
        sql.append("                    ,KEIJO_DT               AS DTSS_KEIJO_DT  ");
        sql.append("                    ,TENPO_CD AS DTSS_TENPO_CD, ");
        //#4553 Mod X.Liu 2017.04.10 (S)
//        sql.append("                    SEISAN_STATE_FG AS DTSS_SEISAN_STATE_FG ");
        sql.append("                    ISAN_SEISAN_STATE_FG AS DTSS_ISAN_SEISAN_STATE_FG ");
        //#4553 Mod X.Liu 2017.04.10 (E)
        sql.append("                FROM ");
        sql.append("                    DT_TEN_SEISAN_STATE ");
        //sql.append("                WHERE ");
        //sql.append("                    COMP_CD = ? AND ");
        //sql.append("                    KEIJO_DT = ? ");
        sql.append("            ) DTSS ");
        sql.append("        ON ");
        sql.append("            DTSS.DTSS_COMP_CD = TRTS.COMP_CD AND ");
        sql.append("            DTSS.DTSS_KEIJO_DT = TRTS.EIGYO_DT AND ");
        sql.append("            DTSS.DTSS_TENPO_CD = TRTS.TENPO_CD   ");
        //sql.append("            TRTS.TENPO_CD = DTSS.DTSS_TENPO_CD ");
        // #3306 2016.12.14 T.Kamei Mod (E)

        sql.append("    ) ");
        sql.append("WHERE ");
        sql.append("    COMP_CD     = ? ");

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
            DaoIf dao = new ReceiptTorihikiSeisanMstJohoSyutokuDao();
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
