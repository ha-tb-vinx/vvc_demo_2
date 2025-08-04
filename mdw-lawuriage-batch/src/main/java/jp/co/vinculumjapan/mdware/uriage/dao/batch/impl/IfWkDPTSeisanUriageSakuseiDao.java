package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * 
 * <p>タイトル: IfWkDPTSeisanUriageSakuseiDao.java クラス</p>
 * <p>説明　: IF DPT精算売上（仕入用）ワーク作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.07) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.02 (2014.02.06) S.Arakawa （全体）結合課題No.0117対応
 * @Version 3.03 (2016.11.10) k.Hyo #2674対応
 *
 */
public class IfWkDPTSeisanUriageSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システムコントロールより店集計DPTコード取得
    private static final String TEN_SUMMARY_DPT_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_SYUKEI_BUNRUI1_CD);
    // システムコントロールより内掛税の取引コード取得
    private static final String SYOHIZEI_TORIHIKI_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.SYOHIZEI_TORIHIKI_CD);
    // システムコントロールより内掛税の設定項目取得
    private static final String SYOHIZEI_KOMOKU = FiResorceUtility.getPropertie(UriResorceKeyConstant.SYOHIZEI_KOMOKU);

    /** バッチ処理名 */
    private static final String DAO_NAME = "IF DPT精算売上（仕入用）ワーク作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "IF DPT精算売上（仕入用）ワーク";
    /** 店舗コード */
    private static final String TENPO_CD = "TENPO_CD";
    /** 消費税差額 */
    private static final String SYOHIZEI_DIF_VL = "SYOHIZEI_DIF_VL";
    /** DPTコード */
    private static final String BUNRUI1_CD = "BUNRUI1_CD";
    /** 売上金額(税込) */
    private static final String URIAGE_KOMI_VL = "URIAGE_KOMI_VL";

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementSelect = null;
        PreparedStatementEx preparedStatementSelUpdTarget = null;
        PreparedStatementEx preparedStatementSelUpdTargetCount = null;
        PreparedStatementEx preparedStatementUpd = null;

        int insertCount = 0;

        int updateCount = 0;

        try {

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "追加処理を開始します。");

            // IF DPT精算売上（仕入用）ワーク作成処理
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getIfWkDPTSeisanUriShiireInsertSql());
            preparedStatementExIns.setString(1, COMP_CD);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "レコードを追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "追加処理を終了します。");


// 結合課題.0117対応の為 コメントアウト
//            // ログ出力
//            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "更新(消費税誤差調整)処理を開始します。");
//
//            // 消費税誤差　調整処理
//
//            // 消費税に誤差が発生している店舗を抽出する
//            preparedStatementSelect = invoker.getDataBase().prepareStatement(getDifSyohizeiTenpoQuerrySql());
//            preparedStatementSelect.setString(1, COMP_CD);
//            preparedStatementSelect.setString(2, BATCH_DT);
//            preparedStatementSelect.setString(3, TEN_SUMMARY_DPT_CD);
//            preparedStatementSelect.setString(4, COMP_CD);
//            preparedStatementSelect.setString(5, BATCH_DT);
//            preparedStatementSelect.setString(6, SYOHIZEI_TORIHIKI_CD);
//
//            ResultSet resultSet = preparedStatementSelect.executeQuery();
//
//            // 更新対象店舗データ数取得クエリ
//            preparedStatementSelUpdTargetCount = invoker.getDataBase().prepareStatement(getIfWkDPTSeisanUpdateDataCountSql());
//            // 更新対象店舗データ取得クエリ
//            preparedStatementSelUpdTarget = invoker.getDataBase().prepareStatement(getIfWkDPTSeisanUpdateDataSelectSql());
//            // 更新クエリ
//            preparedStatementUpd = invoker.getDataBase().prepareStatement(getIfWkDPTSeisanUpdateSql());
//
//            while (resultSet.next()) {
//
//                // 誤差発生店舗・誤差消費税額取得
//                String tenpoCd = resultSet.getString(TENPO_CD);
//                int syohizeiDifVl = resultSet.getInt(SYOHIZEI_DIF_VL);
//
//                preparedStatementSelUpdTargetCount.setString(1, COMP_CD);
//                preparedStatementSelUpdTargetCount.setString(2, BATCH_DT);
//                preparedStatementSelUpdTargetCount.setString(3, tenpoCd);
//                preparedStatementSelUpdTargetCount.setString(4, TEN_SUMMARY_DPT_CD);
//
//                ResultSet updateDataCountResultSet = preparedStatementSelUpdTargetCount.executeQuery();
//
//                preparedStatementSelUpdTarget.setString(1, COMP_CD);
//                preparedStatementSelUpdTarget.setString(2, BATCH_DT);
//                preparedStatementSelUpdTarget.setString(3, tenpoCd);
//                preparedStatementSelUpdTarget.setString(4, TEN_SUMMARY_DPT_CD);
//
//                ResultSet updateDataResultSet = preparedStatementSelUpdTarget.executeQuery();
//
//                // 誤差補正値(全データ)
//                int adjustValAllData = 0;
//                // 誤差補正値(個別)
//                int difVal = 0;
//
//                // 店舗別データ数取得
//                updateDataCountResultSet.next();
//                int updateDataSize = updateDataCountResultSet.getInt(1);
//
//                // 誤差補正値計算
//                if (Math.abs(syohizeiDifVl) >= updateDataSize) {
//                    adjustValAllData = syohizeiDifVl / updateDataSize;
//                    difVal = syohizeiDifVl % updateDataSize;
//                } else {
//                    adjustValAllData = 0;
//                    difVal = syohizeiDifVl;
//                }
//
//                while (updateDataResultSet.next()) {
//
//                    String dptCd = updateDataResultSet.getString(BUNRUI1_CD);
//                    long uriageKomiVl = updateDataResultSet.getLong(URIAGE_KOMI_VL);
//
//                    // 誤差補正
//                    uriageKomiVl = uriageKomiVl + adjustValAllData;
//                    if (difVal > 0) {
//                        uriageKomiVl = uriageKomiVl + 1;
//                        difVal = difVal - 1;
//                    } else if (difVal < 0) {
//                        uriageKomiVl = uriageKomiVl - 1;
//                        difVal = difVal + 1;
//                    }
//
//                    // 補正を行った売上金額(税込)でIF DPT精算売上(仕入用)ワークを更新する
//                    preparedStatementUpd.setLong(1, uriageKomiVl);
//                    preparedStatementUpd.setString(2, COMP_CD);
//                    preparedStatementUpd.setString(3, BATCH_DT);
//                    preparedStatementUpd.setString(4, tenpoCd);
//                    preparedStatementUpd.setString(5, dptCd);
//
//                    preparedStatementUpd.executeUpdate();
//                    updateCount = updateCount + 1;
//
//                    if (adjustValAllData == 0 && difVal == 0) {
//                        // 誤差補正値(全データ)がなく、個別の誤差補正値を振り分け終わった場合
//                        break;
//                    }
//                }
//
//            }
//
//            // ログ出力
//            invoker.infoLog(strUserId + "　：　" + updateCount + "件の" + INS_TABLE_NAME + "レコードを更新しました。");
//
//            // ログ出力
//            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "更新(消費税誤差調整)処理を終了します。");

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
                if (preparedStatementSelect != null) {
                    preparedStatementSelect.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementSelUpdTarget != null) {
                    preparedStatementSelUpdTarget.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementUpd != null) {
                    preparedStatementUpd.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

    }

    /**
     * DPT精算売上ワークからIF DPT精算売上（仕入用）ワークを登録するSQLを取得する
     * 
     * @return IF DPT精算売上（仕入用）ワーク登録クエリー
     */
    private String getIfWkDPTSeisanUriShiireInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO IF_WK_DPT_SEISAN_URI_SHIIRE( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,URIAGE_KOMI_VL) ");
        sql.append("SELECT ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        // 2016.11.10 k.Hyo add (S)
        //sql.append("    ,URIAGE_KOMI_VL ");
        sql.append("    ,SUM(URIAGE_KOMI_VL) ");
        // 2016.11.10 k.Hyo add (E)
        sql.append("FROM ");
        sql.append("    WK_DPT_SEISAN_URI ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");
        // 2016.11.10 k.Hyo add (S)
        sql.append("GROUP BY ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        // 2016.11.10 k.Hyo add (E)
        return sql.toString();
    }

    /**
     * 消費税に誤差が発生している店舗を抽出するSQLを取得する
     * 
     * @return 消費税誤差発生店舗抽出SQL取得クエリー
     */
    private String getDifSyohizeiTenpoQuerrySql() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("     WDSU.TENPO_CD ");
        sql.append("    ,DRTS.DRTS_SYOHIZEI_SUM_VL - WDSU.WDSU_SYOHIZEI_SUM_VL AS SYOHIZEI_DIF_VL ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("         WDSU.KEIJO_DT ");
        sql.append("        ,WDSU.TENPO_CD ");
        sql.append("        ,SUM(WDSU.SYOHIZEI_VL)  AS WDSU_SYOHIZEI_SUM_VL ");
        sql.append("    FROM ");
        sql.append("        WK_DPT_SEISAN_URI WDSU ");
        sql.append("    WHERE ");
        sql.append("        WDSU.COMP_CD     = ?    AND ");
        sql.append("        WDSU.KEIJO_DT    = ?    AND ");
        sql.append("        WDSU.BUNRUI1_CD <> ? ");
        sql.append("    GROUP BY ");
        sql.append("         KEIJO_DT ");
        sql.append("        ,TENPO_CD ");
        sql.append("    ) WDSU ");
        sql.append("LEFT JOIN ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("         DRTS.KEIJO_DT          AS DRTS_KEIJO_DT ");
        sql.append("        ,DRTS.TENPO_CD          AS DRTS_TENPO_CD ");
        sql.append("        ,SUM(DRTS." + SYOHIZEI_KOMOKU + ")   AS DRTS_SYOHIZEI_SUM_VL ");
        sql.append("    FROM ");
        sql.append("        DT_REGI_TORIHIKI_SEISAN DRTS ");
        sql.append("    WHERE ");
        sql.append("        DRTS.COMP_CD        = ?     AND ");
        sql.append("        DRTS.KEIJO_DT       = ?     AND ");
        sql.append("        DRTS.TORIHIKI_CD    = ? ");
        sql.append("    GROUP BY ");
        sql.append("         DRTS.KEIJO_DT ");
        sql.append("        ,DRTS.TENPO_CD ");
        sql.append("    ) DRTS ");
        sql.append("ON ");
        sql.append("    WDSU.KEIJO_DT   = DRTS.DRTS_KEIJO_DT    AND ");
        sql.append("    WDSU.TENPO_CD   = DRTS.DRTS_TENPO_CD ");
        sql.append("WHERE ");
        sql.append("    WDSU.WDSU_SYOHIZEI_SUM_VL <> DRTS.DRTS_SYOHIZEI_SUM_VL ");

        return sql.toString();
    }

    /**
     * IF DPT精算売上(仕入用)ワークから更新対象を抽出するSQLを取得する
     * 
     * @return IF DPT精算売上(仕入用)ワーク更新対象抽出SQL取得クエリー
     */
    private String getIfWkDPTSeisanUpdateDataSelectSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("     BUNRUI1_CD ");
        sql.append("    ,URIAGE_KOMI_VL ");
        sql.append("FROM ");
        sql.append("    IF_WK_DPT_SEISAN_URI_SHIIRE ");
        sql.append("WHERE ");
        sql.append("    COMP_CD      = ? AND ");
        sql.append("    KEIJO_DT     = ? AND ");
        sql.append("    TENPO_CD     = ? AND ");
        sql.append("    BUNRUI1_CD  <> ? ");
        sql.append("ORDER BY ");
        sql.append("    URIAGE_KOMI_VL DESC");

        return sql.toString();
    }

    /**
     * IF DPT精算売上(仕入用)ワークから更新対象件数を取得するSQLを取得する
     * 
     * @return IF DPT精算売上(仕入用)ワーク更新対象件数取得SQL取得クエリー
     */
    private String getIfWkDPTSeisanUpdateDataCountSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("     COUNT(1) ");
        sql.append("FROM ");
        sql.append("    IF_WK_DPT_SEISAN_URI_SHIIRE ");
        sql.append("WHERE ");
        sql.append("    COMP_CD      = ? AND ");
        sql.append("    KEIJO_DT     = ? AND ");
        sql.append("    TENPO_CD     = ? AND ");
        sql.append("    BUNRUI1_CD  <> ? ");
        sql.append("GROUP BY ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");

        return sql.toString();
    }

    /**
     * IF DPT精算売上(仕入用)ワークを更新するSQLを取得する
     * 
     * @return IF DPT精算売上(仕入用)ワーク更新SQL取得クエリー
     */
    private String getIfWkDPTSeisanUpdateSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE ");
        sql.append("    IF_WK_DPT_SEISAN_URI_SHIIRE ");
        sql.append("SET ");
        sql.append("    URIAGE_KOMI_VL = ? ");
        sql.append("WHERE ");
        sql.append("    COMP_CD      = ? AND ");
        sql.append("    KEIJO_DT     = ? AND ");
        sql.append("    TENPO_CD     = ? AND ");
        sql.append("    BUNRUI1_CD   = ? ");

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
            DaoIf dao = new IfWkDPTSeisanUriageSakuseiDao();
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
