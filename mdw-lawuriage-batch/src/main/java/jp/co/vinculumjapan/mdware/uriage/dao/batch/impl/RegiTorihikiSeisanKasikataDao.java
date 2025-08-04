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
 * <p>タイトル: RegiTorihikiSeisanKasikataDao クラス</p>
 * <p>説明　: レジ別取引精算貸方レコード作成処理(レジ取引精算)</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016/05/13) S_MDware-G_FIVIマート様開発 VINX k.Hyo
 * @Version 1.01 (2016/06/15) S_MDware-G_FIVIマート様開発 VINX Y.Itaki #1628
 * @Version 1.02 (2016/07/27) S_MDware-G_FIVIマート様開発 VINX T.Kamei #1860
 * @Version 1.03 (2016/09/27) S_MDware-G_FIVIマート様開発 VINX Y.Itaki #2009
 * @Version 1.04 (2017/03/14) S_MDware-G_FIVIマート様開発 VINX J.Endo  #4288
 *
 */
public class RegiTorihikiSeisanKasikataDao implements DaoIf /*extends TorikomiDaoSuper*/ {


    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** バッチ処理名 */
    private static final String DAO_NAME = "レジ別取引精算貸方レコード作成処理（レジ別取引精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "店舗DPT売上データ";
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
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

        int insertCount = 0;
        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // TMPレジ別取引精算データから有効レジ別取引精算ワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTmpRegiTorihikiSeisanSql());

            //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(S)
            int i;
            // 売上区分=1（POS）の場合
            //   精算画面用売上金額（税抜）レコード
            i = 0;
            preparedStatementExIns.setString(i+1, COMP_CD);
            preparedStatementExIns.setString(i+2, userId);
            preparedStatementExIns.setString(i+3, dbServerTime);
            preparedStatementExIns.setString(i+4, userId);
            preparedStatementExIns.setString(i+5, dbServerTime);
            preparedStatementExIns.setString(i+6, COMP_CD);
            preparedStatementExIns.setString(i+7, BATCH_DT);

            //   精算画面用消費税額レコード
            i += 7;
            preparedStatementExIns.setString(i+1, COMP_CD);
            preparedStatementExIns.setString(i+2, userId);
            preparedStatementExIns.setString(i+3, dbServerTime);
            preparedStatementExIns.setString(i+4, userId);
            preparedStatementExIns.setString(i+5, dbServerTime);
            preparedStatementExIns.setString(i+6, COMP_CD);
            preparedStatementExIns.setString(i+7, BATCH_DT);

            //   精算画面用返品金額税抜レコード
            i += 7;
            preparedStatementExIns.setString(i+1, COMP_CD);
            preparedStatementExIns.setString(i+2, userId);
            preparedStatementExIns.setString(i+3, dbServerTime);
            preparedStatementExIns.setString(i+4, userId);
            preparedStatementExIns.setString(i+5, dbServerTime);
            preparedStatementExIns.setString(i+6, COMP_CD);
            preparedStatementExIns.setString(i+7, BATCH_DT);

            //   精算画面用返品税額レコード
            i += 7;
            preparedStatementExIns.setString(i+1, COMP_CD);
            preparedStatementExIns.setString(i+2, userId);
            preparedStatementExIns.setString(i+3, dbServerTime);
            preparedStatementExIns.setString(i+4, userId);
            preparedStatementExIns.setString(i+5, dbServerTime);
            preparedStatementExIns.setString(i+6, COMP_CD);
            preparedStatementExIns.setString(i+7, BATCH_DT);

            // 売上区分=2（自家消費）の場合
            //   精算画面用売上金額（税抜）レコード
            i = 28;
            preparedStatementExIns.setString(i+1, COMP_CD);
            preparedStatementExIns.setString(i+2, userId);
            preparedStatementExIns.setString(i+3, dbServerTime);
            preparedStatementExIns.setString(i+4, userId);
            preparedStatementExIns.setString(i+5, dbServerTime);
            preparedStatementExIns.setString(i+6, COMP_CD);
            preparedStatementExIns.setString(i+7, BATCH_DT);

            //   精算画面用消費税額レコード
            i += 7;
            preparedStatementExIns.setString(i+1, COMP_CD);
            preparedStatementExIns.setString(i+2, userId);
            preparedStatementExIns.setString(i+3, dbServerTime);
            preparedStatementExIns.setString(i+4, userId);
            preparedStatementExIns.setString(i+5, dbServerTime);
            preparedStatementExIns.setString(i+6, COMP_CD);
            preparedStatementExIns.setString(i+7, BATCH_DT);

            //   精算画面用返品金額税抜レコード
            i += 7;
            preparedStatementExIns.setString(i+1, COMP_CD);
            preparedStatementExIns.setString(i+2, userId);
            preparedStatementExIns.setString(i+3, dbServerTime);
            preparedStatementExIns.setString(i+4, userId);
            preparedStatementExIns.setString(i+5, dbServerTime);
            preparedStatementExIns.setString(i+6, COMP_CD);
            preparedStatementExIns.setString(i+7, BATCH_DT);

            //   精算画面用返品税額レコード
            i += 7;
            preparedStatementExIns.setString(i+1, COMP_CD);
            preparedStatementExIns.setString(i+2, userId);
            preparedStatementExIns.setString(i+3, dbServerTime);
            preparedStatementExIns.setString(i+4, userId);
            preparedStatementExIns.setString(i+5, dbServerTime);
            preparedStatementExIns.setString(i+6, COMP_CD);
            preparedStatementExIns.setString(i+7, BATCH_DT);

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
     * 店別DPT売上データ、POSジャーナル（C_Payment）一時データからTMPレジ別取引精算データへ登録するSQLを取得する
     *
     * @return TMPレジ別取引精算データ登録クエリー
     */
    private String getTmpRegiTorihikiSeisanSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO TMP_REGI_TORIHIKI_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,FLOAR_NO ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,TYPE_KB ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ) ");

        // 売上区分=1（POS）の場合
        //   精算画面用売上金額（税抜）レコード
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0051' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(S)
        //sql.append("    ,URIAGE_NUKI_VL ");
        sql.append("    ,TP.SE_URIAGE_NUKI_VL ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(E)
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,'999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(S)
        //sql.append("    ,'99999' ");
        sql.append("    ,'9999999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(S)
        //sql.append("            URIAGE_NUKI_VL ");
        sql.append("            SE_URIAGE_NUKI_VL ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(E)
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        //2016/09/27 Y.Itaki FIVI対応 #2009 ADD(S)
        sql.append("        AND URIAGE_KB = '1' ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 ADD(E)
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");

        //   精算画面用消費税額レコード
        sql.append("UNION ALL ");
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0052' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(S)
        //sql.append("    ,TP.SYOHIZEI_VL ");
        sql.append("    ,TP.SE_SYOHIZEI_VL ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(E)
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,'999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(S)
        //sql.append("    ,'99999' ");
        sql.append("    ,'9999999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(S)
        //sql.append("            SYOHIZEI_VL ");
        sql.append("            SE_SYOHIZEI_VL ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 MOD(E)
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        //2016/09/27 Y.Itaki FIVI対応 #2009 ADD(S)
        sql.append("        AND URIAGE_KB = '1' ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 ADD(E)
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");

        //2016/09/27 Y.Itaki FIVI対応 #2009 ADD(S)
        //   精算画面用返品金額税抜レコード
        sql.append("UNION ALL ");
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0053' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.SE_HENPIN_NUKI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,'999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(S)
        //sql.append("    ,'99999' ");
        sql.append("    ,'9999999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            SE_HENPIN_NUKI_VL ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("        AND URIAGE_KB = '1' ");
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");

        //   精算画面用返品税額レコード
        sql.append("UNION ALL ");
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0054' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.SE_HENPIN_ZEI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,'999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(S)
        //sql.append("    ,'99999' ");
        sql.append("    ,'9999999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            SE_HENPIN_ZEI_VL ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("        AND URIAGE_KB = '1' ");
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");

        // 売上区分=2（自家消費）の場合
        //   精算画面用売上金額（税抜）レコード
        sql.append("UNION ALL ");
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0055' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.SE_URIAGE_NUKI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,'999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(S)
        //sql.append("    ,'99999' ");
        sql.append("    ,'9999999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            SE_URIAGE_NUKI_VL ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("        AND URIAGE_KB = '2' ");
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");

        //   精算画面用消費税額レコード
        sql.append("UNION ALL ");
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0056' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.SE_SYOHIZEI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,'999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(S)
        //sql.append("    ,'99999' ");
        sql.append("    ,'9999999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            SE_SYOHIZEI_VL ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("        AND URIAGE_KB = '2' ");
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");

        //   精算画面用返品金額税抜レコード
        sql.append("UNION ALL ");
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0057' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.SE_HENPIN_NUKI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,'999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(S)
        //sql.append("    ,'99999' ");
        sql.append("    ,'9999999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            SE_HENPIN_NUKI_VL ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("        AND URIAGE_KB = '2' ");
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");

        //   精算画面用返品税額レコード
        sql.append("UNION ALL ");
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0058' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.SE_HENPIN_ZEI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,'999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(S)
        //sql.append("    ,'99999' ");
        sql.append("    ,'9999999' ");
        //2017/03/14 J.Endo FIVI対応 #4288 MOD(E)
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            SE_HENPIN_ZEI_VL ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("        AND URIAGE_KB = '2' ");
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");
        //2016/09/27 Y.Itaki FIVI対応 #2009 ADD(E)



        //2016/09/27 Y.Itaki FIVI対応 #2009 DEL(S)
        // #2009対応後のSQLを見易くするため、以下のSQLは全てコメントアウトしてます

        //sql.append("INSERT /*+ APPEND */ INTO TMP_REGI_TORIHIKI_SEISAN( ");
        /*
        sql.append("     COMP_CD ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,FLOAR_NO ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,TYPE_KB ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ) ");
        //2016/06/16 Y.Itaki FIVI対応 #1628 MOD(S)
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        //sql.append("    ,TO_CHAR(TO_DATE(SUBSTR(EIGYO_DT,1,8)),'YYYY/MM/DD') ");
        //sql.append("    ,STORE ");
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0051' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,URIAGE_NUKI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        //2016/07/27 T.Kamei FIVI対応 #1860 MOD(S)
        sql.append("    ,'999' ");
        sql.append("    ,'99999' ");
        //2016/07/27 T.Kamei FIVI対応 #1860 MOD(E)
        //sql.append("FROM ");
        //sql.append("    TMP_POS_C_PAYMENT ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            URIAGE_NUKI_VL ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");

        sql.append("UNION ALL ");
        sql.append("SELECT ");
        sql.append("    ? ");//法人コード
        //sql.append("    ,TO_CHAR(TO_DATE(SUBSTR(EIGYO_DT,1,8)),'YYYY/MM/DD') ");
        //sql.append("    ,STORE ");
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        sql.append("    ,LPAD(TPCP.STORE,6,'0') ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0052' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.SYOHIZEI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        //2016/07/27 T.Kamei FIVI対応 #1860 MOD(S)
        sql.append("    ,'999' ");
        sql.append("    ,'99999' ");
        //2016/07/27 T.Kamei FIVI対応 #1860 MOD(E)
        //sql.append("FROM ");
        //sql.append("    TMP_POS_C_PAYMENT ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("        FROM ");
        sql.append("            TMP_POS_C_PAYMENT ");
        sql.append("        group by ");
        sql.append("            STORE ");
        sql.append("            ,EIGYO_DT ");
        sql.append("    ) TPCP ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            SYOHIZEI_VL ");
        sql.append("            ,TENPO_CD ");
        sql.append("            ,KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? ");//法人コード
        sql.append("        AND BUNRUI1_CD = '9999' ");
        sql.append("        AND DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("    ) TP ");
        sql.append("ON  LPAD(TPCP.STORE,6,'0') = TP.TENPO_CD ");
        sql.append("AND SUBSTR(TPCP.EIGYO_DT,1,8) = TP.KEIJO_DT ");
        */
        //2016/09/27 Y.Itaki FIVI対応 #2009 DEL(E)
        /*
        sql.append("SELECT  ");
        sql.append("     ? ");//法人コード
        sql.append("    ,TO_CHAR(TO_DATE(SUBSTR(EIGYO_DT,1,8)),'YYYY/MM/DD') ");
        sql.append("    ,STORE ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0051' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.URIAGE_NUKI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("FROM ");
        sql.append("    TMP_POS_C_PAYMENT ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            TP.URIAGE_NUKI_VL AS URIAGE_NUKI_VL ");
        sql.append("            ,TP.TENPO_CD ");
        sql.append("            ,TP.KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI TP ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? AND ");//法人コード
        sql.append("            BUNRUI1_CD = '9999' AND ");
        sql.append("            DATA_SAKUSEI_DT = ? ");//システムコントロール.バッチ日付
        sql.append("    ) TP ");
        sql.append("ON ");
        sql.append("    LPAD(TMP_POS_C_PAYMENT.STORE,6,'0') = TP.TENPO_CD AND ");
        sql.append("    SUBSTR(TMP_POS_C_PAYMENT.EIGYO_DT,1,8) = TP.KEIJO_DT ");
        sql.append("UNION ALL ");
        sql.append("SELECT  ");
        sql.append("     ? ");
        sql.append("    ,TO_CHAR(TO_DATE(SUBSTR(EIGYO_DT,1,8)),'YYYY/MM/DD') ");
        sql.append("    ,STORE ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,'0052' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,TP.SYOHIZEI_VL ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("FROM ");
        sql.append("    TMP_POS_C_PAYMENT ");
        sql.append("INNER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            TP.SYOHIZEI_VL AS SYOHIZEI_VL ");
        sql.append("            ,TP.TENPO_CD ");
        sql.append("            ,TP.KEIJO_DT ");
        sql.append("        FROM ");
        sql.append("            DT_TEN_DPT_URI TP ");
        sql.append("        WHERE ");
        sql.append("            COMP_CD = ? AND ");
        sql.append("            BUNRUI1_CD = '9999' AND ");
        sql.append("            DATA_SAKUSEI_DT = ? ");
        sql.append("    ) TP ");
        sql.append("ON ");
        sql.append("    LPAD(TMP_POS_C_PAYMENT.STORE,6,'0') = TP.TENPO_CD AND ");
        sql.append("    SUBSTR(TMP_POS_C_PAYMENT.EIGYO_DT,1,8) = TP.KEIJO_DT ");
        */
        //2016/06/16 Y.Itaki FIVI対応 #1628 MOD(E)

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
            DaoIf dao = new RegiTorihikiSeisanMstJohoSyutokuDao();
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
