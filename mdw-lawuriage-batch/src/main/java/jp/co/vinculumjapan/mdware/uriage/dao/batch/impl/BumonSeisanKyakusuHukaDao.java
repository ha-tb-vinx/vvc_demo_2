package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: BumonSeisanKyakusuHuka クラス</p>
 * <p>説明　: 部門精算客数付加</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.06.24) Y.Itaki FIVI対応
 * @Version 1.01 (2016.08.23) Y.Itaki FIVI(#1879対応)
 * @Version 1.02 (2016.12.21) J.Endo FIVI(#3297対応)
 * @Version 1.03 (2017.06.23) J.Endo FIVI(#5040対応)
 *
 */
public class BumonSeisanKyakusuHukaDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    // システムコントロールより店集計DPTコード取得
    private static final String TEN_SUMMARY_DPT_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.TEN_SYUKEI_BUNRUI1_CD);

    // 削除SQL文用定数
    private static final String DEL_SQL = "TRUNCATE TABLE WK_YUKO_BUMON_SEISAN_KYAKUSU";

    // 処理名
    private static final String DAO_NAME = "部門精算客数付加";

    // テーブル名
    private static final String TABLE_NAME = "有効部門精算客数ワーク";

    // 処理実行
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDel = null;

        int insertCount = 0;
        try {
            // WK_YUKO_BUMON_SEISAN_テーブルを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME + "の追加を開始します。");

            // 有効部門精算客数ワークに登録
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkYukoBumonSeisanKyakusuInsertSql());
            preparedStatementExIns.setString(1, COMP_CD);
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + TABLE_NAME + "を追加しました。");
            invoker.infoLog(strUserId + "　：　" + TABLE_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }
                if (preparedStatementDel != null) {
                    preparedStatementDel.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

    }

    /**
     * 有効部門精算客数ワーク登録用SQLを取得する
     *
     * @return 有効部門精算客数ワーク登録用SQL
     */
    private String getWkYukoBumonSeisanKyakusuInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_YUKO_BUMON_SEISAN_KYAKUSU(  ");
        sql.append("    COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,KYAKU_QT ");
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
        sql.append("    ,DAIHYO_SYOHIN_CD ");
        sql.append("    ,URIAGE_ZEI_KB ");
        sql.append("    ,URIAGE_TAX_RATE_KB ");
        sql.append("    ,URIAGE_TAX_RT ");
        sql.append("    ,ERR_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        //sql.append("    ,UPDATE_TS) ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        sql.append(") ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        sql.append("SELECT ");
        sql.append("    COMP_CD ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("    ,KEIJO_DT ");
        sql.append("    ,SUBSTR(EIGYO_DT,1, 4) || SUBSTR(EIGYO_DT,6, 2) || SUBSTR(EIGYO_DT,9, 2)  AS KEIJO_DT ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("    ,TENPO_CD ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,BUMON_CD ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        sql.append("    ,CASE  ");
        sql.append("       WHEN WYBS.URIAGE_KB = '1' THEN ( ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        sql.append("         CASE  ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("           WHEN WYBS.BUNRUI1_CD = '" + TEN_SUMMARY_DPT_CD + "' THEN ( ");
        sql.append("           WHEN WYBS.BUMON_CD = '" + TEN_SUMMARY_DPT_CD + "' THEN ( ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("               NVL( ");
        sql.append("                   ( ");
        sql.append("                       SELECT ");
        sql.append("                           DJKS.KYAKU_QT ");
        sql.append("                       FROM ");
        // 20161221 J.Endo FIVI(#3297対応) MOD (S)
        //sql.append("                           DT_JIKANTAI_KYAKUSU_SYUKEI DJKS ");
        sql.append("                           DT_HIBETU_KYAKUSU_A DJKS ");
        // 20161221 J.Endo FIVI(#3297対応) MOD (E)
        sql.append("                       WHERE ");
        // 20161221 J.Endo FIVI(#3297対応) MOD (S)
        //sql.append("                           SUBSTR(DJKS.TENPO_CD,0,4) = SUBSTR(WYBS.TENPO_CD,-4) AND ");
        sql.append("                           SUBSTR(DJKS.TENPO_CD,-4) = SUBSTR(WYBS.TENPO_CD,-4) AND ");
        // 20161221 J.Endo FIVI(#3297対応) MOD (E)
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("                           DJKS.KEIJYO_DT = WYBS.KEIJO_DT AND ");
        sql.append("                           DJKS.KEIJYO_DT = SUBSTR(WYBS.EIGYO_DT,1, 4) || SUBSTR(WYBS.EIGYO_DT,6, 2) || SUBSTR(WYBS.EIGYO_DT,9, 2) AND ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("                           DJKS.KYAKU_SYUKEI_TANI = 'STR' AND ");
        sql.append("                           SUBSTR(DJKS.KYAKU_SYUKEI_CD,-6) = WYBS.TENPO_CD ");
        sql.append("                   ) ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("                   ,WYBS.KYAKU_QT ");
        sql.append("                   ,WYBS.URIAGE_KYAKU_QT ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("               ) ");
        sql.append("           ) ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("           WHEN WYBS.BUNRUI1_CD <> '" + TEN_SUMMARY_DPT_CD + "' THEN ( ");
        sql.append("           WHEN WYBS.BUMON_CD <> '" + TEN_SUMMARY_DPT_CD + "' THEN ( ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        sql.append("               NVL( ");
        sql.append("                   ( ");
        sql.append("                       SELECT ");
        sql.append("                           DJKS.KYAKU_QT ");
        sql.append("                       FROM ");
        // 20161221 J.Endo FIVI(#3297対応) MOD (S)
        //sql.append("                           DT_JIKANTAI_KYAKUSU_SYUKEI DJKS ");
        sql.append("                           DT_HIBETU_KYAKUSU_A DJKS ");
        // 20161221 J.Endo FIVI(#3297対応) MOD (E)
        sql.append("                       WHERE ");
        // 20161221 J.Endo FIVI(#3297対応) MOD (S)
        //sql.append("                           SUBSTR(DJKS.TENPO_CD,0,4) = SUBSTR(WYBS.TENPO_CD,-4) AND ");
        sql.append("                           SUBSTR(DJKS.TENPO_CD,-4) = SUBSTR(WYBS.TENPO_CD,-4) AND ");
        // 20161221 J.Endo FIVI(#3297対応) MOD (E)
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("                           DJKS.KEIJYO_DT = WYBS.KEIJO_DT AND ");
        sql.append("                           DJKS.KEIJYO_DT = SUBSTR(WYBS.EIGYO_DT,1, 4) || SUBSTR(WYBS.EIGYO_DT,6, 2) || SUBSTR(WYBS.EIGYO_DT,9, 2) AND ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("                           DJKS.KYAKU_SYUKEI_TANI = 'TYP' AND ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("                           SUBSTR(DJKS.KYAKU_SYUKEI_CD,-2) = SUBSTR(WYBS.BUNRUI1_CD,0,2) ");
        sql.append("                           SUBSTR(DJKS.KYAKU_SYUKEI_CD,-2) = SUBSTR(WYBS.BUMON_CD,0,2) ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("                   ) ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("                   ,WYBS.KYAKU_QT ");
        sql.append("                   ,WYBS.URIAGE_KYAKU_QT ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("               ) ");
        sql.append("           ) ");
        sql.append("           END ");
        sql.append("       ) ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        sql.append("       WHEN WYBS.URIAGE_KB <> '1' THEN 0 ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        sql.append("       END AS KYAKU_QT ");
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
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("    ,DAIHYO_SYOHIN_CD ");
        //sql.append("    ,URIAGE_ZEI_KB ");
        //sql.append("    ,URIAGE_TAX_RATE_KB ");
        //sql.append("    ,URIAGE_TAX_RT ");
        //sql.append("    ,ERR_KB ");
        sql.append("    ,null DAIHYO_SYOHIN_CD ");
        sql.append("    ,null URIAGE_ZEI_KB ");
        sql.append("    ,null URIAGE_TAX_RATE_KB ");
        sql.append("    ,null URIAGE_TAX_RT ");
        sql.append("    ,null ERR_KB ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (S)
        sql.append("    ,HANBAI_WEIGHT_QT ");
        sql.append("    ,URIAGE_NUKI_SOURI_VL ");
        sql.append("    ,URIAGE_ZEI_VL ");
        sql.append("    ,HENPIN_WEIGHT ");
        sql.append("    ,HENPIN_NUKI_VL ");
        sql.append("    ,HENPIN_ZEI_VL ");
        sql.append("    ,URIAGE_KB ");
        // 20160823 Y.Itaki FIVI(#1879対応) ADD (E)
        sql.append("FROM ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (S)
        //sql.append("    WK_YUKO_BUMON_SEISAN WYBS ");
        sql.append("    WK_BUMON_SEISAN WYBS ");
        // 20170623 J.Endo FIVI(#5040対応) MOD (E)
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");//法人コード

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
