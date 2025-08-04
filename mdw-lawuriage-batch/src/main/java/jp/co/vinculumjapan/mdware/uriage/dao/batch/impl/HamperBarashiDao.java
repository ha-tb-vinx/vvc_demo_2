package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 * <p>
 * タイトル: HamperBarashiDao クラス
 * </p>
 * <p>
 * 説明　: ハンパー商品ばらし
 * </p>
 * <p>
 * 著作権: Copyright (c) 2013
 * </p>
 * <p>
 * 会社名: VINX
 * </p>
 *
 * @Version 1.00 (2016.05.17) VINX S.Kashihara FIVI対応
 * @Version 1.01 (2016.06.02) VINX K.Hirano    FIVI(#1512対応)
 * @Version 1.02 (2016.08.05) VINX Y.Itaki     FIVI(#1879対応)
 * @Version 1.03 (2016.10.31) VINX Y.Itaki     FIVI(#2014対応)
 *
 */
public class HamperBarashiDao implements DaoIf {

    // バッチ処理名
    private static final String DAO_NAME = "ハンパー商品ばらし";
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();


    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        // 更新時刻
        String dbServerTime = FiResorceUtility.getDBServerTime();

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        PreparedStatementEx psInsertSelectNonHamper = null;
        PreparedStatementEx psSelectLoopHamper = null;
        PreparedStatementEx psInsertSelectHamper = null;

        try {
            // 2016/10/31 VINX Y.Itaki FIVI(#2014) MOD(S)
            //  ※ ハンパーばらし機能を全て削除

            // 単品精算データテーブルから単品精算データ_ハンパーばらしテーブルへ移送する。
            psInsertSelectNonHamper = invoker.getDataBase().prepareStatement(getInsertSelectNonHamperSql());

            psInsertSelectNonHamper.setString(1, userId);
            psInsertSelectNonHamper.setString(2, dbServerTime);
            psInsertSelectNonHamper.setString(3, userId);
            psInsertSelectNonHamper.setString(4, dbServerTime);
            psInsertSelectNonHamper.setString(5, BATCH_DT);

            int insCount1 = psInsertSelectNonHamper.executeUpdate();
            invoker.getDataBase().commit(); // APPEND ヒント使用後に参照可能にするためにコミット
            invoker.infoLog(strUserId + "　：　" + insCount1 + "件の単品精算データ_ハンパーばらしを追加しました。");

            // 2016/10/31 VINX Y.Itaki FIVI(#2014) MOD(E)
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;

        } finally {
            try {
                if (psInsertSelectNonHamper != null) {
                    psInsertSelectNonHamper.close();
                }
                if (psSelectLoopHamper != null) {
                    psSelectLoopHamper.close();
                }
                if (psInsertSelectHamper != null) {
                    psInsertSelectHamper.close();
                }
            } catch (Exception e2) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }



    /**
     * @return ハンパー商品以外のデータ登録SQL
     */
    private String getInsertSelectNonHamperSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_TANPIN_SEISAN_HMPR_BR ");
        sql.append("SELECT ");
        sql.append("    DTS.COMP_CD, ");
        sql.append("    DTS.KEIJO_DT, ");
        sql.append("    DTS.TENPO_CD, ");
        sql.append("    DTS.TANPIN_CD, ");
        sql.append("    DTS.URIAGE_SOURI_QT, ");
        sql.append("    DTS.URIAGE_SOURI_VL, ");
        sql.append("    DTS.URIAGE_HITEIBAN_QT, ");
        sql.append("    DTS.URIAGE_HITEIBAN_VL, ");
        sql.append("    DTS.NEBIKI_MM_QT, ");
        sql.append("    DTS.NEBIKI_MM_VL, ");
        sql.append("    DTS.LOS_QT, ");
        sql.append("    DTS.LOS_VL, ");
        sql.append("    DTS.NEBIKI_REGI_QT, ");
        sql.append("    DTS.NEBIKI_REGI_VL, ");
        sql.append("    DTS.NEBIKI_SC_QT, ");
        sql.append("    DTS.NEBIKI_SC_VL, ");
        sql.append("    DTS.HAIKI_QT, ");
        sql.append("    DTS.HAIKI_VL, ");
        sql.append("    DTS.TEIBAN_TANKA_VL, ");
        sql.append("    DTS.TOKUBAI_KIKAKU_NO, ");
        sql.append("    DTS.END_HANBAI_TS, ");
        sql.append("    DTS.DAIBUNRUI2_CD, ");
        sql.append("    DTS.BUNRUI1_CD, ");
        sql.append("    DTS.BUNRUI2_CD, ");
        sql.append("    DTS.BUNRUI5_CD, ");
        sql.append("    DTS.HINMEI_KANJI_NA, ");
        sql.append("    DTS.KIKAKU_KANJI_NA, ");
        sql.append("    DTS.KIKAKU_KANJI_2_NA, ");
        sql.append("    DTS.HINMEI_KANA_NA, ");
        sql.append("    DTS.KIKAKU_KANA_NA, ");
        sql.append("    DTS.KIKAKU_KANA_2_NA, ");
        sql.append("    DTS.REC_HINMEI_KANA_NA, ");
        sql.append("    DTS.TEIKAN_KB, ");
        sql.append("    DTS.ZEI_KB, ");
        sql.append("    DTS.TAX_RATE_KB, ");
        sql.append("    DTS.TAX_RT, ");
        sql.append("    DTS.MST_BAITANKA_VL, ");
        sql.append("    DTS.SHIIRE_HANBAI_KB, ");
        sql.append("    DTS.TANAOROSHI_GENKA_KB, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    DTS.HANBAI_WEIGHT_QT, ");
        sql.append("    DTS.DATA_SAKUSEI_DT, ");
        sql.append("    DTS.URIAGE_NUKI_SOURI_VL, ");
        sql.append("    DTS.URIAGE_ZEI_VL, ");
        sql.append("    DTS.HENPIN_QT, ");
        sql.append("    DTS.HENPIN_WEIGHT, ");
        sql.append("    DTS.HENPIN_KOMI_VL, ");
        sql.append("    DTS.HENPIN_NUKI_VL, ");
        sql.append("    DTS.HENPIN_ZEI_VL, ");
        sql.append("    DTS.URIAGE_KB ");
        sql.append("FROM ");
        sql.append("    DT_TANPIN_SEISAN DTS ");
        sql.append("WHERE ");
        sql.append("    DTS.DATA_SAKUSEI_DT = ? "); //バッチ日付


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
