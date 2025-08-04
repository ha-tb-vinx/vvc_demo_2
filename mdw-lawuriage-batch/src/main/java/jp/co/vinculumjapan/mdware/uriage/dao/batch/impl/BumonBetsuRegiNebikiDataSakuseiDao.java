package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.util.HashMap;

import jp.co.vinculumjapan.mdware.common.util.dictionary.FractionDigitDic;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
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
 * <p>タイトル: BumonBetsuRegiNebikiDataSakuseiDao.java クラス</p>
 * <p>説明　: 部門別レジ値引データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2014.12.04) chou グローバル化対応 通貨対応
 *
 */
public class BumonBetsuRegiNebikiDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システムコントロールよりダミーDPTコード取得
    private static final String DUMMY_BUNRUI1_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI1_CD);
    // エラーコードとエラーDPT名
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラーコード
    private static final String ERROR_DPT_CODE = ERROR_DPT.keySet().toString().replace("[", "").replace("]", "");
    // システムコントロールより店集計DPTコード取得
    private static final String TEN_SUMMARY_DPT_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_SYUKEI_BUNRUI1_CD);

    /** バッチ処理名 */
    private static final String DAO_NAME = "部門別レジ値引データ作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "POS値上下ワーク";

    private ResorceUtil ru = ResorceUtil.getInstance();

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 部門精算データからPOS値上下ワークを登録する(レジ値引)
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "(レジ値引)の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkNejogePosInsertSql(true));
            preparedStatementExIns.setString(1, UriageCommonConstants.DENPYO_SYUBETSU_BAIHEN);
            preparedStatementExIns.setString(2, UriageCommonConstants.RIYU_KB_NEAGE);
            preparedStatementExIns.setString(3, UriageCommonConstants.RIYU_KB_NESAGE);
            preparedStatementExIns.setString(4, UriageCommonConstants.RIYU_SYOSAI_KB_SONOTA_NEAGE);
            preparedStatementExIns.setString(5, UriageCommonConstants.RIYU_SYOSAI_KB_TENPO_NESAGE);
            preparedStatementExIns.setString(6, userId);
            preparedStatementExIns.setString(7, dbServerTime);
            preparedStatementExIns.setString(8, userId);
            preparedStatementExIns.setString(9, dbServerTime);

            preparedStatementExIns.setString(10, COMP_CD);
            preparedStatementExIns.setString(11, BATCH_DT);
            preparedStatementExIns.setString(12, DUMMY_BUNRUI1_CD);
            preparedStatementExIns.setString(13, COMP_CD);
            preparedStatementExIns.setString(14, BATCH_DT);
            preparedStatementExIns.setString(15, COMP_CD);
            preparedStatementExIns.setString(16, BATCH_DT);
            preparedStatementExIns.setString(17, ERROR_DPT_CODE);
            preparedStatementExIns.setString(18, TEN_SUMMARY_DPT_CD);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "(レジ値引)を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "(レジ値引)の追加を終了します。");

            // コミット
            invoker.getDataBase().commit();

            // 部門精算データからPOS値上下ワークを登録する(SC値引)
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "(SC値引)の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkNejogePosInsertSql(false));
            preparedStatementExIns.setString(1, UriageCommonConstants.DENPYO_SYUBETSU_BAIHEN);
            preparedStatementExIns.setString(2, UriageCommonConstants.RIYU_KB_NEAGE);
            preparedStatementExIns.setString(3, UriageCommonConstants.RIYU_KB_NESAGE);
            preparedStatementExIns.setString(4, UriageCommonConstants.RIYU_SYOSAI_KB_SONOTA_NEAGE);
            preparedStatementExIns.setString(5, UriageCommonConstants.RIYU_SYOSAI_KB_GROUP_BAIHEN);
            preparedStatementExIns.setString(6, userId);
            preparedStatementExIns.setString(7, dbServerTime);
            preparedStatementExIns.setString(8, userId);
            preparedStatementExIns.setString(9, dbServerTime);

            preparedStatementExIns.setString(10, COMP_CD);
            preparedStatementExIns.setString(11, BATCH_DT);
            preparedStatementExIns.setString(12, DUMMY_BUNRUI1_CD);
            preparedStatementExIns.setString(13, COMP_CD);
            preparedStatementExIns.setString(14, BATCH_DT);
            preparedStatementExIns.setString(15, COMP_CD);
            preparedStatementExIns.setString(16, BATCH_DT);
            preparedStatementExIns.setString(17, ERROR_DPT_CODE);
            preparedStatementExIns.setString(18, TEN_SUMMARY_DPT_CD);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "(SC値引)を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "(SC値引)の追加を終了します。");

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
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 部門精算データからPOS値上下ワークを登録するSQLを取得する
     *
     * @param isRegiNebiki true:レジ値引きの場合、false:SC値引きの場合
     * @return POS値上下ワーク登録SQL
     */
    private String getWkNejogePosInsertSql(boolean isRegiNebiki) {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND*/ INTO WK_NEJOGE_POS( ");
        sql.append("     COMP_CD ");
        sql.append("    ,DENPYO_KB ");
        sql.append("    ,KANRI_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,SYOHIN_HINMEI_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_2_NA ");
        sql.append("    ,HINMEI_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_2_NA ");
        sql.append("    ,RIYU_KB ");
        sql.append("    ,RIYU_SYOSAI_KB ");
        sql.append("    ,ZEI_KB ");
        sql.append("    ,TAX_RT ");
        sql.append("    ,SURYO_QT ");
        sql.append("    ,SHIIRE_SURYO_QT ");
        sql.append("    ,BAITANKA_OLD_VL ");
        sql.append("    ,BAITANKA_NEW_VL ");
        sql.append("    ,M_BAITANKA_VL ");
        sql.append("    ,BAIKA_ZEINUKI_VL ");
        sql.append("    ,BAIKA_ZEIKOMI_VL ");
        sql.append("    ,BAIKA_ZEIGAKU_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     DBS.COMP_CD ");
        sql.append("    ,? ");
        sql.append("    ,DBS.KEIJO_DT ");
        sql.append("    ,DBS.TENPO_CD ");
        sql.append("    ,DBS.BUNRUI1_CD ");
        sql.append("    ,DBS.SYOHIN_CD ");
        sql.append("    ,DBS.BUNRUI2_CD ");
        sql.append("    ,DBS.BUNRUI5_CD ");
        sql.append("    ,DBS.HINMEI_KANJI_NA ");
        sql.append("    ,DBS.KIKAKU_KANJI_NA ");
        sql.append("    ,DBS.KIKAKU_KANJI_2_NA ");
        sql.append("    ,DBS.HINMEI_KANA_NA ");
        sql.append("    ,DBS.KIKAKU_KANA_NA ");
        sql.append("    ,DBS.KIKAKU_KANA_2_NA ");
        sql.append("    ,CASE ");
        sql.append("        WHEN DBS.BAIHEN_VL < 0 THEN ? ");
        sql.append("        ELSE ? ");
        sql.append("     END AS RIYU_KB ");
        sql.append("    ,CASE ");
        sql.append("        WHEN DBS.BAIHEN_VL < 0 THEN ? ");
        sql.append("        ELSE ? ");
        sql.append("     END AS RIYU_SYOSAI_KB ");
        sql.append("    ,DBS.ZEI_KB ");
        sql.append("    ,DBS.TAX_RT ");
        sql.append("    ,1 AS SURYO_QT ");
        sql.append("    ,1 AS SHIIRE_SURYO_QT ");
        sql.append("    ,ABS(DBS.BAIHEN_VL) AS BAITANKA_OLD_VL ");
        sql.append("    ,0 AS BAITANKA_NEW_VL ");
        sql.append("    ,ABS(DBS.BAIHEN_VL) AS M_BAITANKA_VL ");

        //2014/12/03 chou グローバル化対応 通貨対応 MOD START
        //sql.append("    ,ABS(GET_ZEINUKI( ");
        //sql.append("        DBS.BAIHEN_VL, DBS.ZEI_KB, DBS.TAX_RT ");
        //sql.append("     )) AS BAIKA_ZEINUKI_VL ");
        //sql.append("    ,ABS(GET_ZEIKOMI( ");
        //sql.append("        DBS.BAIHEN_VL, DBS.ZEI_KB, DBS.TAX_RT ");
        //sql.append("     )) AS BAIKA_ZEIKOMI_VL ");
        //sql.append("    ,ABS(GET_ZEIGAKU( ");
        //sql.append("        DBS.BAIHEN_VL, DBS.ZEI_KB, DBS.TAX_RT ");
        //sql.append("     )) AS BAIKA_ZEIGAKU_VL ");

        sql.append("    ,ABS(GET_ZEINUKI_MARUME( ");
        sql.append("        DBS.BAIHEN_VL ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
        sql.append("        ,DBS.ZEI_KB ");
        sql.append("        ,DBS.TAX_RT ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
        sql.append("     )) AS BAIKA_ZEINUKI_VL ");

        sql.append("    ,ABS(GET_ZEIKOMI_MARUME( ");
        sql.append("        DBS.BAIHEN_VL ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
        sql.append("        ,DBS.ZEI_KB ");
        sql.append("        ,DBS.TAX_RT ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
        sql.append("     )) AS BAIKA_ZEIKOMI_VL ");

        sql.append("    ,ABS(GET_ZEIGAKU_MARUME( ");
        sql.append("        DBS.BAIHEN_VL ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
        sql.append("        ,DBS.ZEI_KB ");
        sql.append("        ,DBS.TAX_RT ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
        sql.append("     )) AS BAIKA_ZEIGAKU_VL ");
        //2014/12/03 chou グローバル化対応 通貨対応 MOD END
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("         DBS.COMP_CD ");
        sql.append("        ,DBS.KEIJO_DT ");
        sql.append("        ,DBS.TENPO_CD ");
        sql.append("        ,DBS.BUNRUI1_CD ");
        sql.append("        ,RDS.RDS_SYOHIN_CD AS SYOHIN_CD ");
        sql.append("        ,RDS.RDS_BUNRUI2_CD AS BUNRUI2_CD ");
        sql.append("        ,RDS.RDS_BUNRUI5_CD AS BUNRUI5_CD ");
        sql.append("        ,RDS.RDS_HINMEI_KANJI_NA AS HINMEI_KANJI_NA ");
        sql.append("        ,RDS.RDS_KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA ");
        sql.append("        ,RDS.RDS_KIKAKU_KANJI_2_NA AS KIKAKU_KANJI_2_NA ");
        sql.append("        ,RDS.RDS_HINMEI_KANA_NA AS HINMEI_KANA_NA ");
        sql.append("        ,RDS.RDS_KIKAKU_KANA_NA AS KIKAKU_KANA_NA ");
        sql.append("        ,RDS.RDS_KIKAKU_KANA_2_NA AS KIKAKU_KANA_2_NA ");
        sql.append("        ,RDS.RDS_ZEI_KB AS ZEI_KB ");
        sql.append("        ,RDS.RDS_TAX_RT AS TAX_RT ");
        if (isRegiNebiki) {
            sql.append("        ,DBS.NEBIKI_REGI_VL - COALESCE(DTS.NEBIKI_REGI_SUM_VL, 0) AS BAIHEN_VL ");
        } else {
            sql.append("        ,DBS.NEBIKI_SC_VL - COALESCE(DTS.NEBIKI_SC_SUM_VL, 0) AS BAIHEN_VL ");
        }
        sql.append("    FROM ");
        sql.append("        DT_BUMON_SEISAN DBS ");
        sql.append("        LEFT JOIN ");
        // ＜SUB単品精算データ＞
        sql.append("            ( ");
        sql.append("            SELECT ");
        sql.append("                 DTS.COMP_CD ");
        sql.append("                ,DTS.KEIJO_DT ");
        sql.append("                ,DTS.TENPO_CD ");
        sql.append("                ,DTS.BUNRUI1_CD ");
        sql.append("                ,SUM(DTS.NEBIKI_REGI_VL)    AS NEBIKI_REGI_SUM_VL ");
        sql.append("                ,SUM(DTS.NEBIKI_SC_VL)      AS NEBIKI_SC_SUM_VL ");
        sql.append("                ,SUM(DTS.NEBIKI_MM_VL)      AS NEBIKI_MM_SUM_VL ");
        sql.append("            FROM ");
        sql.append("                DT_TANPIN_SEISAN DTS ");
        sql.append("            WHERE ");
        sql.append("                DTS.COMP_CD                                  = ?    AND ");
        sql.append("                DTS.KEIJO_DT                                 = ?    AND ");
        sql.append("                TRIM(DTS.BUNRUI1_CD)                        <> ?        ");
        sql.append("            GROUP BY ");
        sql.append("                 DTS.COMP_CD ");
        sql.append("                ,DTS.KEIJO_DT ");
        sql.append("                ,DTS.TENPO_CD ");
        sql.append("                ,DTS.BUNRUI1_CD ");
        sql.append("            ) DTS ");
        sql.append("        ON ");
        sql.append("            DBS.COMP_CD     = DTS.COMP_CD   AND ");
        sql.append("            DBS.KEIJO_DT    = DTS.KEIJO_DT  AND ");
        sql.append("            DBS.TENPO_CD    = DTS.TENPO_CD  AND ");
        sql.append("            DBS.BUNRUI1_CD  = DTS.BUNRUI1_CD ");
        sql.append("        LEFT JOIN   ");
        // SUB代表商品マスタ＞
        sql.append("            (  ");
        sql.append("            SELECT   ");
        sql.append("                 RDS1.COMP_CD           AS RDS_COMP_CD ");
        sql.append("                ,RDS1.BUNRUI1_CD        AS RDS_BUNRUI1_CD ");
        sql.append("                ,RDS1.SYOHIN_CD         AS RDS_SYOHIN_CD ");
        sql.append("                ,RDS1.BUNRUI2_CD        AS RDS_BUNRUI2_CD ");
        sql.append("                ,RDS1.BUNRUI5_CD        AS RDS_BUNRUI5_CD ");
        sql.append("                ,RDS1.HINMEI_KANJI_NA   AS RDS_HINMEI_KANJI_NA ");
        sql.append("                ,RDS1.KIKAKU_KANJI_NA   AS RDS_KIKAKU_KANJI_NA ");
        sql.append("                ,RDS1.KIKAKU_KANJI_2_NA AS RDS_KIKAKU_KANJI_2_NA ");
        sql.append("                ,RDS1.HINMEI_KANA_NA    AS RDS_HINMEI_KANA_NA ");
        sql.append("                ,RDS1.KIKAKU_KANA_NA    AS RDS_KIKAKU_KANA_NA ");
        sql.append("                ,RDS1.KIKAKU_KANA_2_NA  AS RDS_KIKAKU_KANA_2_NA ");
        sql.append("                ,RDS1.ZEI_KB            AS RDS_ZEI_KB ");
        sql.append("                ,RDS1.TAX_RT            AS RDS_TAX_RT ");
        sql.append("            FROM   ");
        sql.append("                R_DAIHYO_SYOHIN RDS1 ");
        sql.append("                INNER JOIN ");
        sql.append("                    (  ");
        sql.append("                    SELECT ");
        sql.append("                         RDS.COMP_CD ");
        sql.append("                        ,RDS.BUNRUI1_CD ");
        sql.append("                        ,MAX(RDS.YUKO_DT) AS YUKO_DT ");
        sql.append("                    FROM  ");
        sql.append("                        R_DAIHYO_SYOHIN RDS ");
        sql.append("                    WHERE  ");
        sql.append("                        RDS.COMP_CD  = ?    AND ");
        sql.append("                        RDS.YUKO_DT <= ? ");
        sql.append("                    GROUP BY ");
        sql.append("                         RDS.COMP_CD  ");
        sql.append("                        ,RDS.BUNRUI1_CD ");
        sql.append("                    ) RDS2 ");
        sql.append("                ON  ");
        sql.append("                    RDS1.COMP_CD    = RDS2.COMP_CD      AND ");
        sql.append("                    RDS1.BUNRUI1_CD = RDS2.BUNRUI1_CD   AND ");
        sql.append("                    RDS1.YUKO_DT    = RDS2.YUKO_DT ");
        sql.append("            ) RDS ");
        sql.append("        ON   ");
        sql.append("            DBS.COMP_CD     = RDS.RDS_COMP_CD       AND ");
        sql.append("            DBS.BUNRUI1_CD  = RDS.RDS_BUNRUI1_CD ");
        sql.append("    WHERE ");
        sql.append("        DBS.COMP_CD                                  = ?    AND ");
        sql.append("        DBS.KEIJO_DT                                 = ?    AND ");
        sql.append("        DBS.BUNRUI1_CD                              <> ?    AND ");
        sql.append("        TRIM(DBS.BUNRUI1_CD)                        <> ?    AND ");
        if (isRegiNebiki) {
            sql.append("        DBS.NEBIKI_REGI_VL - COALESCE(DTS.NEBIKI_REGI_SUM_VL, 0) <> 0 ");
        } else {
            sql.append("        DBS.NEBIKI_SC_VL - COALESCE(DTS.NEBIKI_SC_SUM_VL, 0)     <> 0 ");
        }
        sql.append("    ) DBS ");

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
            DaoIf dao = new BumonBetsuRegiNebikiDataSakuseiDao();
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
