package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.util.HashMap;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: TenbetsuDPTUriageSyukeiDao クラス</p>
 *  <P>説明: 店別DPT売上集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 3.00 (2013.10.04) T.ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 *  @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 *  @Version 3.02 (2016.05.12) H.Yaguma FIVI対応
 *  @Version 3.03 (2016.09.07) k.Hyo FIVI対応
 *  @Version 3.04 (2016.09.30) k.Hyo FIVI対応
 *  @Version 3.05 (2017.06.14) X.Liu FIVI対応
 */
public class TenbetsuDPTUriageSyukeiDao implements DaoIf {

    // ※ カスタマイズ前はJavaで個別登録していましたが、カスタマイズ対応でSQLでSELECT-INSERT化したため、全面改定

    // バッチ処理名
    private static final String BATCH_NAME = "店別DPT売上集計処理";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // エラー表示用DPT取得
    @SuppressWarnings("rawtypes")
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラー表示用DPT
    private static final String ERR_HYOJI_DPT = ERROR_DPT.keySet().toString().replace("[", "").replace("]", "");    

    /**
     * 店別DPT売上集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 店別DPT売上データ追加件数
        int intCountDPTUriage = 0;
        // 店別精算状況データ更新件数
        int intCountSeisanJokyo = 0;

        // ユーザーID(DB登録・更新用)
        String userId = invoker.getUserId();
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　店別DPT売上集計処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;

        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 「DPT精算売上ワーク」からデータ抽出し、「店別DPT売上データ」に挿入する。
            // ログ出力
            invoker.infoLog(strUserID + "　：　店別DPT売上データ追加処理を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getDtTenDptUriInsertSql());

            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, BATCH_DT);

            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, BATCH_DT);

            // SQLを実行する
            intCountDPTUriage = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + intCountDPTUriage + "件の店別DPT売上データを追加しました。");
            invoker.infoLog(strUserID + "　：　店別DPT売上データ追加処理を終了します。");

            // 「店別DPT売上データ」に挿入を行った店舗について、「店別精算状況データ」に処理結果を反映する。
            invoker.infoLog(strUserID + "　：　店別精算状況データ更新処理を開始します。");
        
            // 店別精算状況データ　正常更新処理
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getDtTenSeisanStateUpdateSql(false));

            preparedStatementExIns.setString(1, UriageCommonConstants.TORIKOMI_TSUMI_FLAG);
            preparedStatementExIns.setString(2, userId);
            preparedStatementExIns.setString(3, dbServerTime);

            preparedStatementExIns.setString(4, COMP_CD);
            preparedStatementExIns.setString(5, BATCH_DT);

            intCountSeisanJokyo = preparedStatementExIns.executeUpdate();

            // 店別精算状況データ　エラー更新処理
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getDtTenSeisanStateUpdateSql(true));

            preparedStatementExIns.setString(1, UriageCommonConstants.ERROR_FLAG);
            preparedStatementExIns.setString(2, userId);
            preparedStatementExIns.setString(3, dbServerTime);

            preparedStatementExIns.setString(4, ERR_HYOJI_DPT);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, BATCH_DT);

            preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + intCountSeisanJokyo + "件の店別精算状況データを更新しました。");
            invoker.infoLog(strUserID + "　：　店別精算状況データ更新処理を終了します。");
        } catch (Exception e) {
            invoker.errorLog(e);
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
        invoker.infoLog(strUserID + "　：　店別DPT売上集計処理を終了します。");
    }

    /**
     * DPT精算売上ワークから店別DPT売上データへ登録するSQLを取得する
     * 
     * @return 店別DPT売上データ登録クエリー
     */
    private String getDtTenDptUriInsertSql() {
        StringBuilder sql = new StringBuilder();

        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_TEN_DPT_URI DTDU ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      COMP_CD");
        sql.append("      , KEIJO_DT");
        sql.append("      , TENPO_CD");
        sql.append("      , BUNRUI1_CD");
        sql.append("      , (NVL(URIAGE_KOMI_VL, 0) + NVL(HENPIN_VL, 0)) AS POS_VL");
        sql.append("      , (NVL(URIAGE_KOMI_VL, 0) + NVL(HENPIN_VL, 0)) AS URIAGE_KOMI_VL");
        sql.append("      , (NVL(URIAGE_NUKI_VL, 0) + NVL(HENPIN_NUKI_VL, 0)) AS URIAGE_NUKI_VL");
        sql.append("      , (NVL(SYOHIZEI_VL, 0) + NVL(HENPIN_ZEI_VL, 0)) AS SYOHIZEI_VL");
        sql.append("      , (NVL(URIAGE_QT, 0) + NVL(HENPIN_QT, 0)) AS POS_QT");
        sql.append("      , (NVL(URIAGE_QT, 0) + NVL(HENPIN_QT, 0)) AS URIAGE_QT");
        sql.append("      , (NVL(URIAGE_WEIGHT, 0) + NVL(HENPIN_WEIGHT, 0)) AS URIAGE_WEIGHT");
        sql.append("      , BUNRUI1_KYAKU_QT AS POS_KYAKU_QT");
        sql.append("      , BUNRUI1_KYAKU_QT AS KYAKU_QT");
        sql.append("      , '0' AS ERRER_FG");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS");
        sql.append("      , ? AS DATA_SAKUSEI_DT");
        sql.append("      , URIAGE_NUKI_VL AS SE_URIAGE_NUKI_VL");
        sql.append("      , SYOHIZEI_VL AS SE_SYOHIZEI_VL");
        sql.append("      , HENPIN_NUKI_VL AS SE_HENPIN_NUKI_VL");
        sql.append("      , HENPIN_ZEI_VL AS SE_HENPIN_ZEI_VL");
        sql.append("      , URIAGE_KB ");
        sql.append("    FROM");
        sql.append("      WK_DPT_SEISAN_URI ");
        sql.append("    WHERE");
        sql.append("      COMP_CD = ? ");
        sql.append("      AND DATA_SAKUSEI_DT = ?");
        sql.append("  ) WDSU ");
        sql.append("    ON ( ");
        sql.append("      DTDU.COMP_CD = WDSU.COMP_CD ");
        sql.append("      AND DTDU.KEIJO_DT = WDSU.KEIJO_DT ");
        sql.append("      AND DTDU.TENPO_CD = WDSU.TENPO_CD ");
        sql.append("      AND DTDU.BUNRUI1_CD = WDSU.BUNRUI1_CD ");
        sql.append("      AND DTDU.URIAGE_KB = WDSU.URIAGE_KB");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  POS_VL = WDSU.POS_VL                         ");//POS金額
        sql.append("  , URIAGE_KOMI_VL = WDSU.URIAGE_KOMI_VL       ");//売上金額（税込）
        sql.append("  , URIAGE_NUKI_VL = WDSU.URIAGE_NUKI_VL       ");//売上金額（税抜）
        sql.append("  , SYOHIZEI_VL = WDSU.SYOHIZEI_VL             ");//消費税額
        sql.append("  , POS_QT = WDSU.POS_QT                       ");//POS数量
        sql.append("  , URIAGE_QT = WDSU.URIAGE_QT                 ");//売上数量
        sql.append("  , URIAGE_WEIGHT = WDSU.URIAGE_WEIGHT         ");//売上重量
        sql.append("  , POS_KYAKU_QT = WDSU.POS_KYAKU_QT           ");//POS客数
        sql.append("  , KYAKU_QT = WDSU.KYAKU_QT                   ");//客数
        sql.append("  , SE_URIAGE_NUKI_VL = WDSU.SE_URIAGE_NUKI_VL ");//精算画面用売上金額（税抜）
        sql.append("  , SE_SYOHIZEI_VL = WDSU.SE_SYOHIZEI_VL       ");//精算画面用消費税額
        sql.append("  , SE_HENPIN_NUKI_VL = WDSU.SE_HENPIN_NUKI_VL ");//精算画面用返品金額税抜
        sql.append("  , SE_HENPIN_ZEI_VL = WDSU.SE_HENPIN_ZEI_VL   ");//精算画面用返品税額
        sql.append("  , DATA_SAKUSEI_DT = WDSU.DATA_SAKUSEI_DT     ");//データ作成日
        sql.append("  , UPDATE_USER_ID = WDSU.UPDATE_USER_ID       ");//更新者ID
        sql.append("  , UPDATE_TS = WDSU.UPDATE_TS                 ");//更新タイム
        sql.append("  WHEN NOT MATCHED THEN                        ");
//        sql.append("INSERT /*+ APPEND */ INTO DT_TEN_DPT_URI( ");
        sql.append("INSERT ( ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,POS_VL ");
        sql.append("    ,URIAGE_KOMI_VL ");
        sql.append("    ,URIAGE_NUKI_VL ");
        sql.append("    ,SYOHIZEI_VL ");
        sql.append("    ,POS_QT ");
        sql.append("    ,URIAGE_QT ");
        // 2016.09.07 k.Hyo FIVI対応 (S)
        sql.append("    ,URIAGE_WEIGHT ");
        // 2016.09.07 k.Hyo FIVI対応 (E)
        sql.append("    ,POS_KYAKU_QT ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,ERRER_FG ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
//      sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        // 2016.09.30 k.Hyo FIVI対応 (S)
        //sql.append("    ,DATA_SAKUSEI_DT) ");
        sql.append("    ,DATA_SAKUSEI_DT ");
        sql.append("    ,SE_URIAGE_NUKI_VL ");
        sql.append("    ,SE_SYOHIZEI_VL ");
        sql.append("    ,SE_HENPIN_NUKI_VL ");
        sql.append("    ,SE_HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB) ");
        // 2016.09.30 k.Hyo FIVI対応 (E)
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("SELECT ");
//        sql.append("     COMP_CD ");
//        sql.append("    ,KEIJO_DT ");
//        sql.append("    ,TENPO_CD ");
//        sql.append("    ,BUNRUI1_CD ");
//        // 2016.09.07 k.Hyo FIVI対応 (S)
//        //sql.append("    ,URIAGE_NUKI_VL ");
//        //sql.append("    ,URIAGE_KOMI_VL ");
//        //sql.append("    ,URIAGE_NUKI_VL ");
//        //sql.append("    ,SYOHIZEI_VL ");
//        //sql.append("    ,URIAGE_QT ");
//        //sql.append("    ,URIAGE_QT ");
//        sql.append("    ,(URIAGE_KOMI_VL + HENPIN_VL) AS URIAGE_KOMI_VL ");
//        sql.append("    ,(URIAGE_KOMI_VL + HENPIN_VL) AS URIAGE_KOMI_VL ");
//        sql.append("    ,(URIAGE_NUKI_VL + HENPIN_NUKI_VL) AS URIAGE_NUKI_VL ");
//        sql.append("    ,(SYOHIZEI_VL + HENPIN_ZEI_VL) AS SYOHIZEI_VL ");
//        sql.append("    ,(URIAGE_QT + HENPIN_QT) AS URIAGE_QT ");
//        sql.append("    ,(URIAGE_QT + HENPIN_QT) AS URIAGE_QT ");
//        sql.append("    ,(URIAGE_WEIGHT + HENPIN_WEIGHT) AS URIAGE_WEIGHT ");
//        // 2016.09.07 k.Hyo FIVI対応 (E)
//        sql.append("    ,BUNRUI1_KYAKU_QT ");
//        sql.append("    ,BUNRUI1_KYAKU_QT ");
//        sql.append("    ,'0' ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        sql.append("    ,? ");
//        // 2016.09.30 k.Hyo FIVI対応 (S)
//        sql.append("    ,URIAGE_NUKI_VL ");
//        sql.append("    ,SYOHIZEI_VL ");
//        sql.append("    ,HENPIN_NUKI_VL ");
//        sql.append("    ,HENPIN_ZEI_VL ");
//        sql.append("    ,URIAGE_KB ");
//        // 2016.09.30 k.Hyo FIVI対応 (E)
//        sql.append("FROM ");
//        sql.append("    WK_DPT_SEISAN_URI ");
//        sql.append("WHERE ");
//        sql.append("    COMP_CD     = ? AND ");
////        sql.append("    KEIJO_DT    = ? ");
//        // 2016.09.30 k.Hyo FIVI対応 (S)
//        sql.append("    DATA_SAKUSEI_DT    = ? ");
//        //sql.append("    DATA_SAKUSEI_DT    = ? AND ");
//        //sql.append("    URIAGE_KB    = '1' ");
//        // 2016.09.30 k.Hyo FIVI対応 (E)
        sql.append(" VALUES ( ");
        sql.append(" WDSU.COMP_CD");
        sql.append("  , WDSU.KEIJO_DT");
        sql.append("  , WDSU.TENPO_CD");
        sql.append("  , WDSU.BUNRUI1_CD");
        sql.append("  , WDSU.POS_VL");
        sql.append("  , WDSU.URIAGE_KOMI_VL");
        sql.append("  , WDSU.URIAGE_NUKI_VL");
        sql.append("  , WDSU.SYOHIZEI_VL");
        sql.append("  , WDSU.POS_QT");
        sql.append("  , WDSU.URIAGE_QT");
        sql.append("  , WDSU.URIAGE_WEIGHT");
        sql.append("  , WDSU.POS_KYAKU_QT");
        sql.append("  , WDSU.KYAKU_QT");
        sql.append("  , WDSU.ERRER_FG");
        sql.append("  , WDSU.INSERT_USER_ID");
        sql.append("  , WDSU.INSERT_TS");
        sql.append("  , WDSU.UPDATE_USER_ID");
        sql.append("  , WDSU.UPDATE_TS");
        sql.append("  , WDSU.DATA_SAKUSEI_DT");
        sql.append("  , WDSU.SE_URIAGE_NUKI_VL");
        sql.append("  , WDSU.SE_SYOHIZEI_VL");
        sql.append("  , WDSU.SE_HENPIN_NUKI_VL");
        sql.append("  , WDSU.SE_HENPIN_ZEI_VL");
        sql.append("  , WDSU.URIAGE_KB");
        sql.append(" ) ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        return sql.toString();
    }
    

    /**
     * 店別精算状況データを更新するSQLを取得する
     * 
     * @param エラー区分<br>
     * true：エラー状況を登録する際のクエリーを戻す <br>
     * false：取込済を登録する際のクエリーを戻す <br>
     * @return 店別精算状況データ更新クエリー
     */
    private String getDtTenSeisanStateUpdateSql(boolean isError) {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE ");
        sql.append("    DT_TEN_SEISAN_STATE DTSS ");
        sql.append("SET ");
        sql.append("     DTSS.BUNRUI1_SEISAN_STATE_FG = ? ");
        sql.append("    ,UPDATE_USER_ID = ? ");
        sql.append("    ,UPDATE_TS = ? ");
        sql.append("WHERE ");
        sql.append("    EXISTS ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            '' ");
        sql.append("        FROM ");
        sql.append("            WK_DPT_SEISAN_URI WDSU ");
        sql.append("        WHERE ");
        sql.append("            WDSU.COMP_CD    = DTSS.COMP_CD  AND ");
        sql.append("            WDSU.KEIJO_DT   = DTSS.KEIJO_DT AND ");
        sql.append("            WDSU.TENPO_CD   = DTSS.TENPO_CD ");
        if (isError) {
            sql.append(" AND ");
            sql.append("            WDSU.BUNRUI1_CD = ? ");
        }
        sql.append("    ) AND ");
        sql.append("    DTSS.COMP_CD    = ? AND ");
        sql.append("    DTSS.KEIJO_DT   = ? ");

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
            DaoIf dao = new TenbetsuDPTUriageSyukeiDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
