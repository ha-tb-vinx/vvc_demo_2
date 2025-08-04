package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.common.util.dictionary.FractionDigitDic;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.DPTSeisanUriageDataTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 *  <P>タイトル: DPTSeisanUriageDataTorikomiDao クラス</p>
 *  <P>説明: DPT精算売上データ取込処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.00 (2014/12/04) chou グローバル化対応 通貨対応
 *  @version 1.01 (2016/05/12) H.Yaguma FIVI対応
 *  @version 1.02 (2016/06/07) Y.Itaki FIVI対応
 *  @version 1.02 (2016/08/23) Y.Itaki FIVI(#1879対応)
 *
 *  */
public class DPTSeisanUriageDataTorikomiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システムコントロールより店別客数取引コード取得
    private static final String TEN_KYAKU_TORIHIKI_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_KYAKU_TORIHIKI_CD);
    // システムコントロールより店別客数設定項目取得
    private static final String TEN_KYAKU_KOMOKU = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_KYAKU_KOMOKU);
    // システムコントロールより店集計DPTコード取得
    private static final String TEN_SYUKEI_BUNRUI1_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_SYUKEI_BUNRUI1_CD);
    // システムコントロールより内掛税の取引コード取得
    private static final String SYOHIZEI_TORIHIKI_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.SYOHIZEI_TORIHIKI_CD);
    // システムコントロールより内掛税の設定項目取得
    private static final String SYOHIZEI_KOMOKU = FiResorceUtility.getPropertie(UriResorceKeyConstant.SYOHIZEI_KOMOKU);

    // バッチ処理名
    private static final String BATCH_NAME = "DPT精算売上データ取込処理";
    // 更新対象テーブル名
    private static final String TABLE_NAME = "WK_DPT_SEISAN_URI";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE " + TABLE_NAME;

    // 入力ビーン
    private DPTSeisanUriageDataTorikomiDaoInputBean inputBean = null;

    private ResorceUtil ru = ResorceUtil.getInstance();

    /**
     * DPT精算売上データ取込処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　DPT精算売上データ取込処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDelete = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // ｢DPT精算売上ワーク」のデータを全件削除する。
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // 部門精算データとレジ別取引データからDPT精算売上データへ登録
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkDptSeisanUriInsertSql());

            // 20160823 Y.Itaki FIVI(#1879対応) MOD (S)
//            preparedStatementExIns.setString(1, TEN_SYUKEI_BUNRUI1_CD);
//            preparedStatementExIns.setString(2, TEN_SYUKEI_BUNRUI1_CD);
//            preparedStatementExIns.setString(3, TEN_SYUKEI_BUNRUI1_CD);
//            preparedStatementExIns.setString(4, userId);
//            preparedStatementExIns.setString(5, dbServerTime);
//            preparedStatementExIns.setString(6, userId);
//            preparedStatementExIns.setString(7, dbServerTime);
//
//            preparedStatementExIns.setString(8, COMP_CD);
//            preparedStatementExIns.setString(9, BATCH_DT);
//            preparedStatementExIns.setString(10, TEN_KYAKU_TORIHIKI_CD);
//
//            preparedStatementExIns.setString(11, COMP_CD);
//            preparedStatementExIns.setString(12, BATCH_DT);

            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);

            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, BATCH_DT);
            // 20160823 Y.Itaki FIVI(#1879対応) MOD (S)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件のDPT精算売上ワークを追加しました。");

            // 20160823 Y.Itaki FIVI(#1879対応) DEL (S)
//            // APPEND INSERTした内容確定する必要があるのでcommitを行う
//            invoker.getDataBase().commit();
//
//            //// 店集計レコードの税額設定
//            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkDptSeisanUriUpdateSql());
//
//            preparedStatementExIns.setString(1, userId);
//            preparedStatementExIns.setString(2, dbServerTime);
//            preparedStatementExIns.setString(3, SYOHIZEI_TORIHIKI_CD);
//            preparedStatementExIns.setString(4, SYOHIZEI_TORIHIKI_CD);
//
//            preparedStatementExIns.setString(5, COMP_CD);
//            preparedStatementExIns.setString(6, BATCH_DT);
//            preparedStatementExIns.setString(7, TEN_SYUKEI_BUNRUI1_CD);
//
//            insertCount = preparedStatementExIns.executeUpdate();

//            // ログ出力
//            invoker.infoLog(strUserID + "　：　" + insertCount + "件のDPT精算売上ワーク(店集計データ)を更新しました。");
            // 20160823 Y.Itaki FIVI(#1879対応) DEL (E)

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

        // ログ出力
        invoker.infoLog(strUserID + "　：　DPT精算売上データ取込処理を終了します。");
    }

    /**
     * 部門精算データとレジ別取引データからDPT精算売上データへ登録するSQLを取得する
     *
     * @return DPT精算売上データ登録クエリー
     */
    private String getWkDptSeisanUriInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_DPT_SEISAN_URI( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,URIAGE_NUKI_VL ");
        sql.append("    ,URIAGE_KOMI_VL ");
        sql.append("    ,SYOHIZEI_VL ");
        sql.append("    ,URIAGE_GENKA_VL ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,NEBIKI_QT ");
        sql.append("    ,KAKO_VL ");
        sql.append("    ,KAKO_QT ");
        sql.append("    ,HAIKI_VL ");
        sql.append("    ,HAIKI_QT ");
        sql.append("    ,HENPIN_VL ");
        sql.append("    ,HENPIN_QT ");
        sql.append("    ,BUNRUI1_KYAKU_QT ");
        sql.append("    ,URIAGE_ZEI_KB ");
        sql.append("    ,URIAGE_TAX_RATE_KB ");
        sql.append("    ,URIAGE_TAX_RT ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
//        sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
//        sql.append("    ,DATA_SAKUSEI_DT) ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,URIAGE_WEIGHT ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        sql.append(") ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        sql.append("SELECT ");
        sql.append("     DBS.COMP_CD ");
        sql.append("    ,DBS.KEIJO_DT ");
        sql.append("    ,DBS.TENPO_CD ");
        sql.append("    ,DBS.BUNRUI1_CD ");
        // 20160823 Y.Itaki FIVI(#1879対応) MOD (S)
//        sql.append("    ,DBS.URIAGE_SOURI_VL AS URIAGE_NUKI_VL ");
        sql.append("    ,DBS.URIAGE_NUKI_SOURI_VL AS URIAGE_NUKI_VL ");
//        sql.append("    ,CASE ");
//        sql.append("        WHEN DBS.BUNRUI1_CD = ? THEN 0 ");
//        sql.append("        ELSE ");
//        //2014/12/03 chou グローバル化対応 通貨対応 MOD START
//        //sql.append("            GET_ZEIKOMI ( ");
//        //sql.append("                 DBS.URIAGE_SOURI_VL ");
//        //sql.append("                ,DBS.URIAGE_ZEI_KB ");
//        //sql.append("                ,DBS.URIAGE_TAX_RT ");
//        sql.append("            GET_ZEIKOMI_MARUME ( ");
//        sql.append("                 DBS.URIAGE_SOURI_VL ");
//        sql.append("                ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
//        sql.append("                ,DBS.URIAGE_ZEI_KB ");
//        sql.append("                ,DBS.URIAGE_TAX_RT ");
//        sql.append("                ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
//        //2014/12/03 chou グローバル化対応 通貨対応 MOD END
//        sql.append("             ) ");
//        sql.append("     END AS URIAGE_KOMI_VL ");
        sql.append("    ,DBS.URIAGE_SOURI_VL AS URIAGE_NUKI_VL ");
//        sql.append("    ,CASE ");
//        sql.append("        WHEN DBS.BUNRUI1_CD = ? THEN 0 ");
//        sql.append("        ELSE ");
//        2014/12/03 chou グローバル化対応 通貨対応 MOD START
//        //sql.append("            GET_ZEIGAKU ( ");
//        //sql.append("                 DBS.URIAGE_SOURI_VL ");
//        //sql.append("                ,DBS.URIAGE_ZEI_KB ");
//        //sql.append("                ,DBS.URIAGE_TAX_RT ");
//        sql.append("            GET_ZEIGAKU_MARUME ( ");
//        sql.append("                 DBS.URIAGE_SOURI_VL ");
//        sql.append("                ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
//        sql.append("                ,DBS.URIAGE_ZEI_KB ");
//        sql.append("                ,DBS.URIAGE_TAX_RT ");
//        sql.append("                ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
//        //2014/12/03 chou グローバル化対応 通貨対応 MOD END
//        sql.append("             ) ");
//        sql.append("     END AS SYOHIZEI_VL ");
        sql.append("    ,DBS.URIAGE_ZEI_VL AS SYOHIZEI_VL ");
        // 20160823 Y.Itaki FIVI(#1879対応) MOD (E)
        sql.append("    ,0 AS URIAGE_GENKA_VL ");
        sql.append("    ,DBS.URIAGE_SOURI_QT ");
        sql.append("    ,DBS.NEBIKI_REGI_VL ");
        sql.append("    ,DBS.NEBIKI_REGI_QT ");
        sql.append("    ,0 AS KAKO_VL ");
        sql.append("    ,0 AS KAKO_QT ");
        sql.append("    ,DBS.HAIKI_VL ");
        sql.append("    ,DBS.HAIKI_QT ");
        sql.append("    ,DBS.HENPIN_VL ");
        sql.append("    ,DBS.HENPIN_QT ");
        // 20160823 Y.Itaki FIVI(#1879対応) MOD (S)
//        sql.append("    ,CASE ");
//        sql.append("        WHEN DBS.BUNRUI1_CD = ? THEN DRTS.DRTS_KYAKU_QT ");
//        sql.append("        ELSE DBS.KYAKU_QT ");
//        sql.append("     END AS BUNRUI1_KYAKU_QT ");
        sql.append("    ,DBS.KYAKU_QT AS BUNRUI1_KYAKU_QT");
        // 20160823 Y.Itaki FIVI(#1879対応) MOD (E)

        sql.append("    ,DBS.URIAGE_ZEI_KB ");
        sql.append("    ,DBS.URIAGE_TAX_RATE_KB ");
        sql.append("    ,DBS.URIAGE_TAX_RT ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,DBS.DATA_SAKUSEI_DT ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        sql.append("FROM ");
        sql.append("    DT_BUMON_SEISAN DBS ");
        // 20160823 Y.Itaki FIVI(#1879対応) DEL (S)
//        sql.append("    LEFT JOIN ");
//        // レジ別取引精算データ
//        sql.append("        ( ");
//        sql.append("            SELECT ");
//        sql.append("                 DRTS.COMP_CD   AS DRTS_COMP_CD ");
//        sql.append("                ,DRTS.KEIJO_DT  AS DRTS_KEIJO_DT ");
//        sql.append("                ,DRTS.TENPO_CD  AS DRTS_TENPO_CD ");
//        sql.append("                ,DRTS." + TEN_KYAKU_KOMOKU + "  AS DRTS_KYAKU_QT ");
//        sql.append("            FROM ");
//        sql.append("                DT_REGI_TORIHIKI_SEISAN DRTS ");
//        sql.append("            WHERE ");
//        sql.append("                DRTS.COMP_CD        = ?     AND ");
//        sql.append("                DRTS.KEIJO_DT       = ?     AND ");
//        sql.append("                DRTS.TORIHIKI_CD    = ? ");
//        sql.append("        ) DRTS ");
//        sql.append("    ON ");
//        sql.append("        DBS.COMP_CD     = DRTS.DRTS_COMP_CD     AND ");
//        sql.append("        DBS.KEIJO_DT    = DRTS.DRTS_KEIJO_DT    AND ");
//        sql.append("        DBS.TENPO_CD    = DRTS.DRTS_TENPO_CD ");
        // 20160823 Y.Itaki FIVI(#1879対応) DEL (E)
        sql.append("WHERE ");
        sql.append("    DBS.COMP_CD     = ? AND ");
        //2016/06/07 Y.Itaki FIVI対応 MOD START
        //sql.append("    DBS.KEIJO_DT    = ? ");
        sql.append("    DBS.DATA_SAKUSEI_DT    = ? ");
        //2016/06/07 Y.Itaki FIVI対応 MOD END

        return sql.toString();
    }

    /**
     * DPT精算売上データ 店集計レコードの税額を更新するSQLを取得する
     *
     * @return DPT精算売上データ 店集計レコード税額更新クエリー
     */
    // 20160823 Y.Itaki FIVI(#1879対応) DEL (S)
//    private String getWkDptSeisanUriUpdateSql() {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("UPDATE WK_DPT_SEISAN_URI WDSU ");
//        sql.append("   SET ( ");
//        sql.append("         WDSU.SYOHIZEI_VL ");
//        sql.append("        ,WDSU.URIAGE_KOMI_VL ");
//        sql.append("        ,WDSU.UPDATE_USER_ID ");
//        sql.append("        ,WDSU.UPDATE_TS ");
//        sql.append("    ) = ( ");
//        sql.append("        SELECT ");
//        sql.append("             " + SYOHIZEI_KOMOKU + " ");
//        sql.append("            ,WDSU.URIAGE_NUKI_VL + " + SYOHIZEI_KOMOKU + " ");
//        sql.append("            ,? ");
//        sql.append("            ,? ");
//        sql.append("        FROM ");
//        sql.append("            DT_REGI_TORIHIKI_SEISAN DRTS ");
//        sql.append("        WHERE ");
//        sql.append("            DRTS.COMP_CD        = WDSU.COMP_CD  AND ");
//        sql.append("            DRTS.KEIJO_DT       = WDSU.KEIJO_DT AND ");
//        sql.append("            DRTS.TENPO_CD       = WDSU.TENPO_CD AND ");
//        sql.append("            DRTS.TORIHIKI_CD    = ? ");
//        sql.append("        ) ");
//        sql.append("WHERE EXISTS ");
//        sql.append("    ( ");
//        sql.append("        SELECT ");
//        sql.append("            '' ");
//        sql.append("        FROM ");
//        sql.append("            DT_REGI_TORIHIKI_SEISAN DRTS ");
//        sql.append("        WHERE ");
//        sql.append("            DRTS.COMP_CD        = WDSU.COMP_CD  AND ");
//        sql.append("            DRTS.KEIJO_DT       = WDSU.KEIJO_DT AND ");
//        sql.append("            DRTS.TENPO_CD       = WDSU.TENPO_CD AND ");
//        sql.append("            DRTS.TORIHIKI_CD    = ? ");
//        sql.append("    ) AND ");
//        sql.append("    WDSU.COMP_CD    = ? AND ");
//        sql.append("    WDSU.KEIJO_DT   = ? AND ");
//        sql.append("    WDSU.BUNRUI1_CD = ? ");
//
//        return sql.toString();
//    }
    // 20160823 Y.Itaki FIVI(#1879対応) DEL (E)



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
        inputBean = (DPTSeisanUriageDataTorikomiDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new DPTSeisanUriageDataTorikomiDao();
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
