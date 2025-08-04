package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

import org.apache.commons.lang.StringUtils;


/**
 * <p>タイトル: TanpinSeisanUriagegaiNyukinJogaiDao クラス</p>
 * <p>説明　: 売上外入金除外処理(単品精算)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.19) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.22) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №003, №004
 * @Version 3.02 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 * @Version 3.03 (2016.05.10) VINX S.Kashihara FIVI対応
 * @Version 3.04 (2016.08.09) VINX k.Hyo #1879対応
 * 
 */
public class TanpinSeisanUriagegaiNyukinJogaiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "売上外入金除外処理（単品精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "単品精算ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_TANPIN_SEISAN";
    /** 売上外単品コード */
    private static final Map URIAGEGAI_TANPIN_CD_MAP = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.URIAGEGAI_TANPIN_CD);

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        int insertCount = 0;

        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkTanpinSeisanInsertSql());

            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(5, COMP_CD);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }

            } catch (Exception e2) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

    }

    /**
     * 単品精算ワーク登録用SQLを取得する
     * 
     * @return 単品精算ワーク登録用SQL
     */
    private String getWkTanpinSeisanInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_TANPIN_SEISAN( ");
        sql.append("    COMP_CD, ");
        sql.append("    EIGYO_DT, ");
        sql.append("    TENPO_CD, ");
        sql.append("    TANPIN_SHIKIBETSU_CD, ");
        sql.append("    TANPIN_CD, ");
        sql.append("    URIAGE_SOURI_QT, ");
        sql.append("    URIAGE_SOURI_VL, ");
        sql.append("    URIAGE_HITEIBAN_QT, ");
        sql.append("    URIAGE_HITEIBAN_VL, ");
        sql.append("    MM_NEBIKI_QT, ");
        sql.append("    MM_NEBIKI_VL, ");
        sql.append("    LOS_QT, ");
        sql.append("    LOS_VL, ");
        sql.append("    NEBIKI_REGI_QT, ");
        sql.append("    NEBIKI_REGI_VL, ");
        sql.append("    NEBIKI_SC_QT, ");
        sql.append("    NEBIKI_SC_VL, ");
        sql.append("    HAIKI_QT, ");
        sql.append("    HAIKI_VL, ");
        sql.append("    GAISAN_ARARI_SOURI_VL, ");
        sql.append("    GAISAN_ARARI_HITEIBAN_VL, ");
        sql.append("    TEBAN_TANKA_VL, ");
        sql.append("    TOKUBAI_KIKAKU_NO, ");
        sql.append("    SAISYU_URIAGE_TM, ");
        sql.append("    INSERT_USER_ID, ");
        sql.append("    INSERT_TS, ");
        sql.append("    UPDATE_USER_ID, ");
        sql.append("    UPDATE_TS, ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
        //sql.append("    HANBAI_WEIGHT_QT) ");
        sql.append("    HANBAI_WEIGHT_QT, ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016/08/23 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    URIAGE_NUKI_SOURI_VL, ");
        sql.append("    URIAGE_ZEI_VL, ");
        sql.append("    HENPIN_QT, ");
        sql.append("    HENPIN_WEIGHT, ");
        sql.append("    HENPIN_KOMI_VL, ");
        sql.append("    HENPIN_NUKI_VL, ");
        sql.append("    HENPIN_ZEI_VL, ");
        sql.append("    URIAGE_KB) ");
        sql.append("SELECT ");
        sql.append("    COMP_CD, ");
        sql.append("    EIGYO_DT, ");
        sql.append("    TENPO_CD, ");
        sql.append("    TANPIN_SHIKIBETSU_CD, ");
        sql.append("    TANPIN_CD, ");
        sql.append("    SUM(URIAGE_SOURI_QT), ");
        sql.append("    SUM(URIAGE_SOURI_VL), ");
        sql.append("    URIAGE_HITEIBAN_QT, ");
        sql.append("    URIAGE_HITEIBAN_VL, ");
        sql.append("    MM_NEBIKI_QT, ");
        sql.append("    MM_NEBIKI_VL, ");
        sql.append("    LOS_QT, ");
        sql.append("    LOS_VL, ");
        sql.append("    NEBIKI_REGI_QT, ");
        sql.append("    NEBIKI_REGI_VL, ");
        sql.append("    NEBIKI_SC_QT, ");
        sql.append("    NEBIKI_SC_VL, ");
        sql.append("    HAIKI_QT, ");
        sql.append("    HAIKI_VL, ");
        sql.append("    GAISAN_ARARI_SOURI_VL, ");
        sql.append("    GAISAN_ARARI_HITEIBAN_VL, ");
        sql.append("    TEBAN_TANKA_VL, ");
        sql.append("    TOKUBAI_KIKAKU_NO, ");
        sql.append("    SAISYU_URIAGE_TM, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    ?, ");
        sql.append("    SUM(HANBAI_WEIGHT_QT), ");
        sql.append("    SUM(URIAGE_NUKI_SOURI_VL), ");
        sql.append("    SUM(URIAGE_ZEI_VL), ");
        sql.append("    SUM(HENPIN_QT), ");
        sql.append("    SUM(HENPIN_WEIGHT), ");
        sql.append("    SUM(HENPIN_KOMI_VL), ");
        sql.append("    SUM(HENPIN_NUKI_VL), ");
        sql.append("    SUM(HENPIN_ZEI_VL), ");
        sql.append("    URIAGE_KB ");
        sql.append("    FROM ");
        sql.append("    (SELECT ");
        sql.append("      TTS1.COMP_CD AS COMP_CD, ");
        sql.append("      TTS1.EIGYO_DT AS EIGYO_DT, ");
        sql.append("         TTS1.TENPO_CD AS TENPO_CD, ");
        sql.append("      TTS1.TANPIN_SHIKIBETSU_CD AS TANPIN_SHIKIBETSU_CD, ");
        sql.append("      TTS1.TANPIN_CD AS TANPIN_CD, ");
        //sql.append("    TTS1.URIAGE_SOURI_QT, ");
        //sql.append("    TTS1.URIAGE_SOURI_VL, ");
        sql.append("      CASE WHEN URIAGE_HENPIN_KB = '1' THEN URIAGE_SOURI_QT END AS URIAGE_SOURI_QT, ");
        sql.append("      CASE WHEN URIAGE_HENPIN_KB = '1' THEN URIAGE_SOURI_VL END AS URIAGE_SOURI_VL, ");
        // 2016/08/23 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("     TTS1.URIAGE_HITEIBAN_QT AS URIAGE_HITEIBAN_QT, ");
        sql.append("     TTS1.URIAGE_HITEIBAN_VL AS URIAGE_HITEIBAN_VL, ");
        sql.append("     TTS1.MM_NEBIKI_QT AS MM_NEBIKI_QT, ");
        sql.append("     TTS1.MM_NEBIKI_VL AS MM_NEBIKI_VL, ");
        sql.append("     TTS1.LOS_QT AS LOS_QT, ");
        sql.append("     TTS1.LOS_VL AS LOS_VL, ");
        sql.append("     TTS1.NEBIKI_REGI_QT AS NEBIKI_REGI_QT, ");
        sql.append("     TTS1.NEBIKI_REGI_VL AS NEBIKI_REGI_VL, ");
        sql.append("     TTS1.NEBIKI_SC_QT AS NEBIKI_SC_QT, ");
        sql.append("     TTS1.NEBIKI_SC_VL AS NEBIKI_SC_VL, ");
        sql.append("     TTS1.HAIKI_QT AS HAIKI_QT, ");
        sql.append("     TTS1.HAIKI_VL AS HAIKI_VL, ");
        sql.append("     TTS1.GAISAN_ARARI_SOURI_VL AS GAISAN_ARARI_SOURI_VL, ");
        sql.append("     TTS1.GAISAN_ARARI_HITEIBAN_VL AS GAISAN_ARARI_HITEIBAN_VL, ");
        sql.append("     TTS1.TEBAN_TANKA_VL AS TEBAN_TANKA_VL, ");
        sql.append("     TTS1.TOKUBAI_KIKAKU_NO AS TOKUBAI_KIKAKU_NO, ");
        sql.append("     TTS1.SAISYU_URIAGE_TM AS SAISYU_URIAGE_TM, ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (S)
        sql.append("     CASE WHEN URIAGE_HENPIN_KB = '1' THEN HANBAI_WEIGHT_QT END AS HANBAI_WEIGHT_QT, ");
        // 2016.05.10 VINX S.Kashihara FIVI対応 (E)
        // 2016/08/09 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("     CASE WHEN URIAGE_HENPIN_KB = '1' THEN URIAGE_NUKI_SOURI_VL END AS URIAGE_NUKI_SOURI_VL, ");
        sql.append("     CASE WHEN URIAGE_HENPIN_KB = '1' THEN URIAGE_ZEI_VL END AS URIAGE_ZEI_VL, ");
        sql.append("     CASE WHEN URIAGE_HENPIN_KB = '2' THEN URIAGE_SOURI_QT END AS HENPIN_QT, ");
        sql.append("     CASE WHEN URIAGE_HENPIN_KB = '2' THEN HANBAI_WEIGHT_QT END AS HENPIN_WEIGHT, ");
        sql.append("     CASE WHEN URIAGE_HENPIN_KB = '2' THEN URIAGE_SOURI_VL END AS HENPIN_KOMI_VL, ");
        sql.append("     CASE WHEN URIAGE_HENPIN_KB = '2' THEN URIAGE_NUKI_SOURI_VL END AS HENPIN_NUKI_VL, ");
        sql.append("     CASE WHEN URIAGE_HENPIN_KB = '2' THEN URIAGE_ZEI_VL END AS HENPIN_ZEI_VL, ");
        sql.append("     URIAGE_KB AS URIAGE_KB ");
        // 2016/08/09 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("  FROM ");
        sql.append("    TMP_TANPIN_SEISAN TTS1 ");
        sql.append("  WHERE ");
        sql.append("    TTS1.COMP_CD = ? ");

        if (!URIAGEGAI_TANPIN_CD_MAP.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            // 2013.10.22 T.Ooshiro [CUS00057] 結合テスト課題対応 №003, №004（S)
            for (Iterator it = URIAGEGAI_TANPIN_CD_MAP.entrySet().iterator(); it.hasNext();) {
                Map.Entry uriageTanpinCd = (Map.Entry) it.next();
                if (!StringUtils.isEmpty(((String) uriageTanpinCd.getKey()).trim())) {
                    buff.append(",'" + uriageTanpinCd.getValue() + "'");
                }
            }

            if (buff.length() > 0) {
                sql.append("    AND TTS1.TANPIN_CD NOT IN (");
                sql.append(buff.substring(1));
                sql.append(")");
            }
            // 2013.10.22 T.Ooshiro [CUS00057] 結合テスト課題対応 №003, №004（E)
        }
        
        // 2016/08/23 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    ) ");
        sql.append("GROUP BY ");
        sql.append("    COMP_CD, ");
        sql.append("    EIGYO_DT, ");
        sql.append("    TENPO_CD, ");
        sql.append("    TANPIN_SHIKIBETSU_CD, ");
        sql.append("    TANPIN_CD, ");
        sql.append("    URIAGE_HITEIBAN_QT, ");
        sql.append("    URIAGE_HITEIBAN_VL, ");
        sql.append("    MM_NEBIKI_QT, ");
        sql.append("    MM_NEBIKI_VL, ");
        sql.append("    LOS_QT, ");
        sql.append("    LOS_VL, ");
        sql.append("    NEBIKI_REGI_QT, ");
        sql.append("    NEBIKI_REGI_VL, ");
        sql.append("    NEBIKI_SC_QT, ");
        sql.append("    NEBIKI_SC_VL, ");
        sql.append("    HAIKI_QT, ");
        sql.append("    HAIKI_VL, ");
        sql.append("    GAISAN_ARARI_SOURI_VL, ");
        sql.append("    GAISAN_ARARI_HITEIBAN_VL, ");
        sql.append("    TEBAN_TANKA_VL, ");
        sql.append("    TOKUBAI_KIKAKU_NO, ");
        sql.append("    SAISYU_URIAGE_TM, ");
        sql.append("    URIAGE_KB ");
        // 2016/08/23 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

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
            DaoIf dao = new TanpinSeisanUriagegaiNyukinJogaiDao();
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
