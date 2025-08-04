package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.util.HashMap;

import jp.co.vinculumjapan.mdware.common.util.dictionary.FractionDigitDic;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;


/**
 *
 * <p>タイトル: TenDPTuchiUriWkSakuseiDao.java クラス</p>
 * <p>説明　: 店別DPT打ち売上ワーク作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2014.12.04) chou グローバル化対応 通貨対応
 * @Version 1.01 (2016.12.05) J.Endo FIVI対応 部門清算および単品清算へ売上区分項目の追加対応 #3089
 *
 */
public class TenDPTUchiuriWkSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // エラーコードとエラーDPT名
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラーコード
    private static final String ERROR_DPT_CODE = ERROR_DPT.keySet().toString().replace("[", "").replace("]", "");
    // システムコントロールより店集計DPTコード取得
    private static final String TEN_SUMMARY_DPT_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_SYUKEI_BUNRUI1_CD);

    /** バッチ処理名 */
    private static final String DAO_NAME = "店別DPT打ち売上ワーク作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "店別DPT打ち売上ワーク";
    /** 削除SQL */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_TEN_DPTUCHI_URI";

    private ResorceUtil ru = ResorceUtil.getInstance();

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

            // 店DPT打ち売上ワークを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 部門精算データと単品精算データから店DPT打ち売上ワークを登録する
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkTenDptuchiUriInsertSql());
            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            //2016/12/05 J.Endo FIVI対応 MOD START
            //preparedStatementExIns.setString(5, COMP_CD);
            //preparedStatementExIns.setString(6, BATCH_DT);
            //preparedStatementExIns.setString(7, COMP_CD);
            //preparedStatementExIns.setString(8, BATCH_DT);
            //preparedStatementExIns.setString(9, ERROR_DPT_CODE);
            //preparedStatementExIns.setString(10, TEN_SUMMARY_DPT_CD);
            preparedStatementExIns.setString(5, COMP_CD);
            preparedStatementExIns.setString(6, BATCH_DT);
            preparedStatementExIns.setString(7, ERROR_DPT_CODE);
            preparedStatementExIns.setString(8, TEN_SUMMARY_DPT_CD);
            preparedStatementExIns.setString(9, COMP_CD);
            preparedStatementExIns.setString(10, BATCH_DT);
            //2016/12/05 J.Endo FIVI対応 MOD END

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
     * 部門精算データと単品精算データから店DPT打ち売上ワークを登録するSQLを取得する
     *
     * @return 店DPT打ち売上ワーク登録SQL
     */
    private String getWkTenDptuchiUriInsertSql() {
        StringBuilder sql = new StringBuilder();

        //2016/12/05 J.Endo FIVI対応 MOD START
        //sql.append("INSERT /*+ APPEND */ INTO WK_TEN_DPTUCHI_URI( ");
        //sql.append("     COMP_CD ");
        //sql.append("    ,KEIJO_DT ");
        //sql.append("    ,TENPO_CD ");
        //sql.append("    ,BUNRUI1_CD ");
        //sql.append("    ,URIAGE_VL ");
        //sql.append("    ,TAISYO_URIAGE_VL ");
        //sql.append("    ,TAISYO_URIAGE_QT ");
        //sql.append("    ,INSERT_USER_ID ");
        //sql.append("    ,INSERT_TS ");
        //sql.append("    ,UPDATE_USER_ID ");
        //sql.append("    ,UPDATE_TS) ");
        //sql.append("SELECT ");
        //sql.append("     DBS.COMP_CD ");
        //sql.append("    ,DBS.KEIJO_DT ");
        //sql.append("    ,DBS.TENPO_CD ");
        //sql.append("    ,DBS.BUNRUI1_CD ");
        //sql.append("    ,DBS.URIAGE_SOURI_VL ");
        //sql.append("    ,DBS.URIAGE_SOURI_VL - COALESCE(DTS.ZEINUKI_SOURI_SUM_VL, 0) ");
        //sql.append("    ,DBS.URIAGE_SOURI_QT - COALESCE(DTS.URIAGE_SOURI_SUM_QT, 0) ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("    ,? ");
        //sql.append("FROM ");
        //sql.append("    DT_BUMON_SEISAN DBS ");
        //sql.append("    LEFT JOIN ");
        //sql.append("    ( ");
        //sql.append("    SELECT ");
        //sql.append("         DTS.COMP_CD ");
        //sql.append("        ,DTS.KEIJO_DT ");
        //sql.append("        ,DTS.TENPO_CD ");
        //sql.append("        ,DTS.BUNRUI1_CD ");
        //sql.append("        ,SUM(DTS.ZEINUKI_SOURI_SUM_VL) AS ZEINUKI_SOURI_SUM_VL ");
        //sql.append("        ,SUM(DTS.URIAGE_SOURI_SUM_QT) AS URIAGE_SOURI_SUM_QT ");
        //sql.append("    FROM ");
        //sql.append("        ( ");
        //sql.append("        SELECT ");
        //sql.append("             DTS.COMP_CD ");
        //sql.append("            ,DTS.KEIJO_DT ");
        //sql.append("            ,DTS.TENPO_CD ");
        //sql.append("            ,DTS.BUNRUI1_CD ");
        //sql.append("            ,DTS.TANPIN_CD ");
        ////2014/12/04 chou グローバル化対応 通貨対応 MOD START
        ////sql.append("            ,GET_ZEINUKI(SUM(DTS.URIAGE_SOURI_VL), DTS.ZEI_KB, DTS.TAX_RT) AS ZEINUKI_SOURI_SUM_VL ");
        //
        //sql.append("            ,GET_ZEINUKI_MARUME(SUM(DTS.URIAGE_SOURI_VL) ");
        //sql.append("            ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
        //sql.append("            ,DTS.ZEI_KB ");
        //sql.append("            ,DTS.TAX_RT ");
        //sql.append("            ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
        //sql.append("            ) AS ZEINUKI_SOURI_SUM_VL ");
        ////2014/12/04 chou グローバル化対応 通貨対応 MOD END
        //sql.append("            ,SUM(DTS.URIAGE_SOURI_QT) AS URIAGE_SOURI_SUM_QT ");
        //sql.append("        FROM ");
        //sql.append("            DT_TANPIN_SEISAN DTS ");
        //sql.append("        WHERE ");
        //sql.append("            DTS.COMP_CD     = ? AND ");
        //sql.append("            DTS.KEIJO_DT    = ? ");
        //sql.append("        GROUP BY ");
        //sql.append("             DTS.COMP_CD ");
        //sql.append("            ,DTS.KEIJO_DT ");
        //sql.append("            ,DTS.TENPO_CD ");
        //sql.append("            ,DTS.BUNRUI1_CD ");
        //sql.append("            ,DTS.TANPIN_CD ");
        //sql.append("            ,DTS.ZEI_KB ");
        //sql.append("            ,DTS.TAX_RT ");
        //sql.append("        ) DTS ");
        //sql.append("    GROUP BY ");
        //sql.append("         DTS.COMP_CD ");
        //sql.append("        ,DTS.KEIJO_DT ");
        //sql.append("        ,DTS.TENPO_CD ");
        //sql.append("        ,DTS.BUNRUI1_CD ");
        //sql.append("    ) DTS ");
        //sql.append("    ON ");
        //sql.append("        DBS.COMP_CD     = DTS.COMP_CD   AND ");
        //sql.append("        DBS.KEIJO_DT    = DTS.KEIJO_DT  AND ");
        //sql.append("        DBS.TENPO_CD    = DTS.TENPO_CD  AND ");
        //sql.append("        DBS.BUNRUI1_CD  = DTS.BUNRUI1_CD ");
        //sql.append("    WHERE ");
        //sql.append("        DBS.COMP_CD      = ?    AND ");
        //sql.append("        DBS.KEIJO_DT     = ?    AND ");
        //sql.append("        DBS.BUNRUI1_CD  <> ?    AND ");
        //sql.append("        DBS.BUNRUI1_CD  <> ?    AND ");
        //sql.append("        DBS.URIAGE_SOURI_VL <> COALESCE(DTS.ZEINUKI_SOURI_SUM_VL, 0) ");
        sql.append("INSERT /*+ APPEND */ INTO WK_TEN_DPTUCHI_URI( ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,BUNRUI1_CD ");
        sql.append("   ,URIAGE_VL ");
        sql.append("   ,TAISYO_URIAGE_VL ");
        sql.append("   ,TAISYO_URIAGE_QT ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("    DBS.COMP_CD ");
        sql.append("   ,DBS.KEIJO_DT ");
        sql.append("   ,DBS.TENPO_CD ");
        sql.append("   ,DBS.BUNRUI1_CD ");
        sql.append("   ,DBS.URIAGE_SOURI_SUM_VL ");
        sql.append("   ,DBS.URIAGE_SOURI_SUM_VL - COALESCE(DTS.ZEINUKI_SOURI_SUM_VL, 0) ");
        sql.append("   ,DBS.URIAGE_SOURI_SUM_QT - COALESCE(DTS.URIAGE_SOURI_SUM_QT, 0) ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ( ");
        sql.append("    SELECT ");
        sql.append("        COMP_CD ");
        sql.append("       ,KEIJO_DT ");
        sql.append("       ,TENPO_CD ");
        sql.append("       ,BUNRUI1_CD ");
        sql.append("       ,SUM(URIAGE_SOURI_VL) AS URIAGE_SOURI_SUM_VL ");
        sql.append("       ,SUM(URIAGE_SOURI_QT) AS URIAGE_SOURI_SUM_QT ");
        sql.append("    FROM DT_BUMON_SEISAN ");
        sql.append("    WHERE ");
        sql.append("        COMP_CD      = ? ");
        sql.append("    AND KEIJO_DT     = ? ");
        sql.append("    AND BUNRUI1_CD  <> ? ");
        sql.append("    AND BUNRUI1_CD  <> ? ");
        sql.append("    GROUP BY ");
        sql.append("        COMP_CD ");
        sql.append("       ,KEIJO_DT ");
        sql.append("       ,TENPO_CD ");
        sql.append("       ,BUNRUI1_CD ");
        sql.append("    ) DBS ");
        sql.append("    LEFT JOIN ( ");
        sql.append("    SELECT ");
        sql.append("        DTS.COMP_CD ");
        sql.append("       ,DTS.KEIJO_DT ");
        sql.append("       ,DTS.TENPO_CD ");
        sql.append("       ,DTS.BUNRUI1_CD ");
        sql.append("       ,SUM(DTS.ZEINUKI_SOURI_SUM_VL) AS ZEINUKI_SOURI_SUM_VL ");
        sql.append("       ,SUM(DTS.URIAGE_SOURI_SUM_QT) AS URIAGE_SOURI_SUM_QT ");
        sql.append("    FROM ( ");
        sql.append("        SELECT ");
        sql.append("            DTS.COMP_CD ");
        sql.append("           ,DTS.KEIJO_DT ");
        sql.append("           ,DTS.TENPO_CD ");
        sql.append("           ,DTS.BUNRUI1_CD ");
        sql.append("           ,DTS.TANPIN_CD ");
        sql.append("           ,GET_ZEINUKI_MARUME(SUM(DTS.URIAGE_SOURI_VL) ");
        sql.append("              ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
        sql.append("              ,DTS.ZEI_KB ");
        sql.append("              ,DTS.TAX_RT ");
        sql.append("              ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
        sql.append("               ) AS ZEINUKI_SOURI_SUM_VL ");
        sql.append("           ,SUM(DTS.URIAGE_SOURI_QT) AS URIAGE_SOURI_SUM_QT ");
        sql.append("        FROM DT_TANPIN_SEISAN DTS ");
        sql.append("        WHERE ");
        sql.append("            DTS.COMP_CD  = ? ");
        sql.append("        AND DTS.KEIJO_DT = ? ");
        sql.append("        GROUP BY ");
        sql.append("            DTS.COMP_CD ");
        sql.append("           ,DTS.KEIJO_DT ");
        sql.append("           ,DTS.TENPO_CD ");
        sql.append("           ,DTS.BUNRUI1_CD ");
        sql.append("           ,DTS.TANPIN_CD ");
        sql.append("           ,DTS.ZEI_KB ");
        sql.append("           ,DTS.TAX_RT ");
        sql.append("        ) DTS ");
        sql.append("    GROUP BY ");
        sql.append("        DTS.COMP_CD ");
        sql.append("       ,DTS.KEIJO_DT ");
        sql.append("       ,DTS.TENPO_CD ");
        sql.append("       ,DTS.BUNRUI1_CD ");
        sql.append("    ) DTS ");
        sql.append("    ON ");
        sql.append("        DBS.COMP_CD    = DTS.COMP_CD ");
        sql.append("    AND DBS.KEIJO_DT   = DTS.KEIJO_DT ");
        sql.append("    AND DBS.TENPO_CD   = DTS.TENPO_CD ");
        sql.append("    AND DBS.BUNRUI1_CD = DTS.BUNRUI1_CD ");
        sql.append("WHERE ");
        sql.append("    DBS.URIAGE_SOURI_SUM_VL <> COALESCE(DTS.ZEINUKI_SOURI_SUM_VL, 0) ");
        //2016/12/05 J.Endo FIVI対応 MOD END

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
            DaoIf dao = new TenDPTUchiuriWkSakuseiDao();
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
