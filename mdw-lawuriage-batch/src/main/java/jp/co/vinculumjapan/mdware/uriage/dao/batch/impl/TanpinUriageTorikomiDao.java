package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.TanpinUriageTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: TanpinUriageTorikomiDao クラス</p>
 *  <P>説明: 単品売上取込処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0
 *  @version 1.1 (2010.06.08) S.Hashiguchi 商品コードオールゼロをPOSコード変換処理追加
 *  @Version 3.0  (2013.10.16) S.Arakawa [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 *  @Version 3.1 (2016.05.20) VINX S.Kashihara FIVI対応
 *  @Version 3.2 (2016.06.01) VINX K.Hirano    FIVI(#1475対応)
 *  @Version 3.3 (2016.09.06) VINX Y.Itaki     FIVI(#1879対応)
 *  @Version 3.4 (2016.09.15) VINX Y.Itaki     FIVI(#2009対応)
 *  @Version 3.5 (2016.10.19) VINX k.Hyo       FIVI(#2408対応)
 *  @Version 3.6 (2016.11.29) VINX Y.Itaki     FIVI(#3010対応)
 *  @Version 3.7 (2016.12.20) VINX T.Kamei     FIVI(#3387対応)
 *  @Version 3.8 (2017.01.31) VINX J.Endo      FIVI(#3826対応)
 *
 */
public class TanpinUriageTorikomiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "単品売上取込処理";
    // 更新対象テーブル名
    private static final String TABLE_NAME = "WK_NEW_TANPIN_SUMMARY";
    // 2010.06.08 S.Hashiguchi Add Start
    // 商品コード更新用SQL文変数
    private static final String SYOHIN_CD_UPDATE = "syohinCdUpdate";
    // 2010.06.08 S.Hashiguchi Add End

    // 2013.10.16 S.Arakawa ランドローム様対応 POSインターフェイス仕様変更対応 (S)
    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールマスタ．バッチ日付
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // バインド用パラメータ
    private static final String KOTEICHI1 = "0";
    private static final String KOTEICHI2 = "00";
    private static final String KOTEICHI3 = "0000";
    private static final String KOTEICHI4 = "0000000000";
    // 2013.10.16 S.Arakawa ランドローム様対応 POSインターフェイス仕様変更対応 (E)

    // 入力ビーン
    private TanpinUriageTorikomiDaoInputBean inputBean = null;

    /**
     * 単品売上取込処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // 2010.06.08 S.Hashiguchi Add Start
        // オブジェクトを初期化
        PreparedStatementEx preparedStatementEx = null;
        // 商品コード変換件数
        int intCountTanpinUpdate = 0;
        // 2010.06.08 S.Hashiguchi Add End

        // 2013.10.16 S.Arakawa ランドローム様対応 POSインターフェイス仕様変更対応 (S)
        //オブジェクトを初期化
        PreparedStatementEx psDelNewTanpinSummaryWork = null;
        // バインドパラメータインデックス
        int index = 1;
        // 取込件数
        int intTorikomiCount = 0;
        // 2013.10.16 S.Arakawa ランドローム様対応 POSインターフェイス仕様変更対応 (E)

        // ログ出力
        invoker.infoLog(strUserID + "　：　単品売上取込処理を開始します。");

        // 2013.10.16 S.Arakawa ランドローム様対応 POSインターフェイス仕様変更対応 (S)
//
//        // FTPファイルの存在をチェックする
//        if (!FiFileUtility.pathFileExists(FiResorceUtility.getPropertie(UriResorceKeyConstant.DATA_DIR_PATH), inputBean.getDataFileName())) {
//            // FTPファイルが無い場合、ログ出力
//            invoker.warnLog(strUserID + "　：　新単品サマリファイル(" + inputBean.getDataFileName() + ")が存在しません。単品売上取込処理は行われませんでした。");
//            invoker.infoLog(strUserID + "　：　単品売上取込処理を終了します。");
//            throw new DaoException("データ取込処理にて例外エラー発生。");
//        }
//
//        // ログ出力
//        invoker.infoLog(strUserID + "　：　SQL*Loaderで、新単品サマリワークへのデータ取込処理を開始します。");
//
//        // 新単品サマリワークテーブルデータの取込を行う
//        DataImportDaoOutputBean outBean = FiDataImportDaoUtility.executeDataAccess(invoker, inputBean.getDataFileName(), inputBean.getFormatFileName(), inputBean.getBackupFileName(), inputBean
//                .getLogFileName(), TABLE_NAME);
//
//        // outputBeanの終了ステータスより処理結果判定
//        if (outBean.getResultCd() != 0) {
//            // 正常終了以外は異常終了
//            invoker.warnLog(strUserID + "　：　SQL*Loaderで、新単品サマリワークへのデータ取込処理にて例外エラーが発生しました。");
//            throw new DaoException("データ取込処理にて例外エラー発生。");
//        }
//
//        // ログ出力
//        invoker.infoLog(strUserID + "　：　SQL*Loaderで、新単品サマリワークへのデータ取込処理を終了します。取込件数は" + outBean.getResultCount() + "件です。");
//
//        // 2010.06.08 S.Hashiguchi Add Start
//        // 処理開始ログ出力
//        //invoker.infoLog(strUserID + "　：　新単品明細ログの商品コード変換処理を開始します。");
//        //preparedStatementEx = invoker.getDataBase().prepareStatement(SYOHIN_CD_UPDATE);
//        //intCountTanpinUpdate = preparedStatementEx.executeUpdate();
//
//        // 処理終了ログ出力
//        //invoker.infoLog(strUserID + "　：　新単品明細ログの商品コード変換処理を終了します。" + intCountTanpinUpdate + "件の商品コードを変換しました。");
//        // 2010.06.08 S.Hashiguchi Add End


        // 新単品サマリワークのデータを全件削除するSQL取得
        psDelNewTanpinSummaryWork = invoker.getDataBase().prepareStatement(getDeleteNewTanpinSummarySQL());

        // SQL文実行
        psDelNewTanpinSummaryWork.executeUpdate();

        // 処理開始ログ出力
        invoker.infoLog(strUserID + "　：　新単品サマリワークへのデータ取込処理を開始します。");

        // 新単品サマリワーク取込SQL取得
        preparedStatementEx = invoker.getDataBase().prepareStatement(getInsertNewTanpinSummarySQL());

        // バインドパラメータ設定
        preparedStatementEx.setString(index++, KOTEICHI3);
        preparedStatementEx.setString(index++, "00");
        preparedStatementEx.setString(index++, "02");
        preparedStatementEx.setString(index++, KOTEICHI3);
        preparedStatementEx.setString(index++, KOTEICHI2);
        preparedStatementEx.setString(index++, KOTEICHI2);
        preparedStatementEx.setString(index++, KOTEICHI4);
        preparedStatementEx.setString(index++, KOTEICHI2);
        preparedStatementEx.setString(index++, KOTEICHI4);
        preparedStatementEx.setString(index++, KOTEICHI1);
        preparedStatementEx.setString(index++, KOTEICHI1);
        preparedStatementEx.setString(index++, KOTEICHI1);
        //preparedStatementEx.setString(index++, KOTEICHI1);
        //preparedStatementEx.setString(index++, KOTEICHI1);
        preparedStatementEx.setString(index++, KOTEICHI1);
        preparedStatementEx.setString(index++, KOTEICHI1);
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(S)
        preparedStatementEx.setString(index++, KOTEICHI1);
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(E)
        preparedStatementEx.setString(index++, COMP_CD);
        preparedStatementEx.setString(index++, BATCH_DT);

        // SQL文を実行し、取込件数を取得
        intTorikomiCount = preparedStatementEx.executeUpdate();

        // 処理終了ログ出力
        invoker.infoLog(strUserID + "　：　新単品サマリワークへのデータ取込処理を終了します。");
        invoker.infoLog("取込件数は" + intTorikomiCount + "件です。");

        // 2013.10.16 S.Arakawa ランドローム様対応 POSインターフェイス仕様変更対応 (E)

        invoker.infoLog(strUserID + "　：　単品売上取込処理を終了します。");
    }

    /*
     * 新単品サマリワークのデータを全件削除するSQL
     * return String sql
     */
    private String getDeleteNewTanpinSummarySQL(){

        StringBuffer sql = new StringBuffer("");

        sql.append("TRUNCATE TABLE              ");
        sql.append("    WK_NEW_TANPIN_SUMMARY   ");

        return sql.toString();

    }

    /*
     * 単品精算データを新単品サマリワークへ取り込むSQL
     * return String sql
     */
    private String getInsertNewTanpinSummarySQL(){

        StringBuffer sql = new StringBuffer("");

        sql.append("INSERT /*+ APPEND */ INTO                   ");
        sql.append("    WK_NEW_TANPIN_SUMMARY                   ");
        sql.append("    (                                       ");
        sql.append("     COMP_CD                                ");
        sql.append("    ,KEIJO_DT                               ");
        sql.append("    ,TENPO_CD                               ");
        sql.append("    ,POS_CD                                 ");
        sql.append("    ,SYOHIN_CD                              ");
        sql.append("    ,BAITANKA_VL                            ");
        sql.append("    ,DAIBUNRUI2_CD                          ");
        sql.append("    ,RECEIPT_NA                             ");
        sql.append("    ,DAIBUNRUI1_CD                          ");
        sql.append("    ,KIKAKU_CD                              ");
        sql.append("    ,SYOHIN_KB                              ");
        sql.append("    ,MIX_MATCH_CD                           ");
        sql.append("    ,MIX_MATCH_TYPE_TX                      ");
        sql.append("    ,BUNDLE_1_QT                            ");
        sql.append("    ,BUNDLE_1_VL                            ");
        sql.append("    ,BUNDLE_2_QT                            ");
        sql.append("    ,BUNDLE_2_VL                            ");
        sql.append("    ,MIX_MATCH_KANA_NA                      ");
        sql.append("    ,URIAGE_VL                              ");
        sql.append("    ,URIAGE_GENKA_VL                        ");
        sql.append("    ,URIAGE_QT                              ");
        sql.append("    ,NEBIKI_VL                              ");
        sql.append("    ,NEBIKI_QT                              ");
        sql.append("    ,KAKO_VL                                ");
        sql.append("    ,KAKO_QT                                ");
        sql.append("    ,HAIKI_VL                               ");
        sql.append("    ,HAIKI_QT                               ");
        sql.append("    ,HENPIN_VL                              ");
        sql.append("    ,HENPIN_QT                              ");
        sql.append("    ,KYAKU_QT                               ");
        sql.append("    ,BUNRUI1_CD                             ");
        sql.append("    ,BUNRUI2_CD                             ");
        sql.append("    ,BUNRUI5_CD                             ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(S)
        sql.append("    ,END_HANBAI_TS                          ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(E)
        sql.append("    ,MM_POINT_TAISYO_KB                     ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(S)
        sql.append("    ,PI_URIAGE_QT                           ");
        sql.append("    ,PI_HENPIN_QT                           ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(E)
        // 2016.09.15 VINX Y.Itaki FIVI(#2009対応) ADD(S)
        sql.append("    ,URIAGE_KB                              ");
        // 2016.09.15 VINX Y.Itaki FIVI(#2009対応) ADD(E)
        sql.append("    )                                       ");
        sql.append("SELECT                                      ");
        sql.append("     DTS.COMP_CD                            ");
        sql.append("    ,DTS.KEIJO_DT                           ");
        sql.append("    ,DTS.TENPO_CD                           ");
        sql.append("    ,DTS.TANPIN_CD                          ");
        sql.append("    ,DTS.TANPIN_CD                          ");
        sql.append("    ,DTS.TEIBAN_TANKA_VL                    ");
        sql.append("    ,DTS.DAIBUNRUI2_CD                      ");
        sql.append("    ,SUBSTRB(DTS.REC_HINMEI_KANA_NA, 1, 12) ");
        sql.append("    ,?                                      ");
        sql.append("    ,DTS.TOKUBAI_KIKAKU_NO                  ");
        sql.append("    ,(                                      ");
        sql.append("     CASE                                   ");
        sql.append("        WHEN TOKUBAI_KIKAKU_NO              ");
        sql.append("            = '000000000' THEN ?            ");
        sql.append("     ELSE                                   ");
        sql.append("        ?                                   ");
        sql.append("     END                                    ");
        sql.append("    ) AS TOKUBAI_KIKAKU_NO                  ");
        sql.append("    ,?                                      ");
        sql.append("    ,?                                      ");
        sql.append("    ,?                                      ");
        sql.append("    ,?                                      ");
        sql.append("    ,?                                      ");
        sql.append("    ,?                                      ");
        sql.append("    ,NULL                                   ");
        // 2016.09.06 VINX Y.Itaki FIVI(#1879対応) MOD(S)
        //sql.append("    ,DTS.URIAGE_SOURI_VL                    ");
        // 2016.10.20 VINX k.Hyo FIVI(#2408対応) (S)
        //sql.append("    ,DTS.URIAGE_SOURI_VL + DTS.HENPIN_KOMI_VL ");
        // 2016.11.29 VINX Y.Itaki FIVI(#3010対応) (S)
        //sql.append("    ,DTS.URIAGE_SOURI_VL ");
        sql.append("    ,DTS.URIAGE_NUKI_SOURI_VL ");
        // 2016.11.29 VINX Y.Itaki FIVI(#3010対応) (E)
        // 2016.10.20 VINX k.Hyo FIVI(#2408対応) (E)
        // 2016.09.06 VINX Y.Itaki FIVI(#1879対応) MOD(E)
        sql.append("    ,?                                      ");
        // 2016.09.06 VINX Y.Itaki FIVI(#1879対応) MOD(S)
        // 2016.05.20 VINX S.Kashihara FIVI対応 MOD(S)
        //sql.append("    ,DTS.URIAGE_SOURI_QT                    ");
        //sql.append("    ,CASE ");
        //sql.append("        WHEN TEIKAN_KB = '1' THEN DTS.URIAGE_SOURI_QT ");
        //sql.append("        WHEN TEIKAN_KB = '2' THEN DTS.HANBAI_WEIGHT_QT ");
        //sql.append("        ELSE null ");
        //sql.append("     END ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 MOD(E)
        sql.append("    ,CASE ");
        // 2016.10.20 VINX k.Hyo FIVI(#2408対応) (S)
        //sql.append("        WHEN TEIKAN_KB = '1' THEN DTS.URIAGE_SOURI_QT + DTS.HENPIN_QT ");
        //sql.append("        WHEN TEIKAN_KB = '2' THEN DTS.HANBAI_WEIGHT_QT + DTS.HENPIN_WEIGHT ");
        sql.append("        WHEN TEIKAN_KB = '1' THEN DTS.URIAGE_SOURI_QT ");
        sql.append("        WHEN TEIKAN_KB = '2' THEN DTS.HANBAI_WEIGHT_QT ");
        // 2016.10.20 VINX k.Hyo FIVI(#2408対応) (E)
        sql.append("        ELSE 0 ");
        sql.append("     END ");
        // 2016.09.06 VINX Y.Itaki FIVI(#1879対応) MOD(E)
        sql.append("    ,DTS.NEBIKI_REGI_VL                     ");
        sql.append("    ,DTS.NEBIKI_REGI_QT                     ");
        sql.append("    ,?                                      ");
        sql.append("    ,?                                      ");
        sql.append("    ,DTS.HAIKI_VL                           ");
        sql.append("    ,DTS.HAIKI_QT                           ");
        // 2016.10.20 VINX k.Hyo FIVI(#2408対応) (S)
        //sql.append("    ,?                                      ");
        //sql.append("    ,?                                      ");
        // 2016.11.29 VINX Y.Itaki FIVI(#3010対応) (S)
        //sql.append("    ,HENPIN_KOMI_VL                         ");
        // 2016.12.20 VINX T.Kamei FIVI(#3387対応) (S)
        // 2017.01.31 VINX J.Endo FIVI(#3826対応) (S)
        //sql.append("    ,HENPIN_NUKI_VL * -1                    ");
        sql.append("    ,CASE                                   ");
        sql.append("         WHEN URIAGE_KB = '1' THEN HENPIN_NUKI_VL * -1 ");
        sql.append("         WHEN URIAGE_KB = '2' THEN HENPIN_NUKI_VL * -1 ");
        sql.append("         ELSE                      HENPIN_NUKI_VL      ");
        sql.append("     END ");
        // 2017.01.31 VINX J.Endo FIVI(#3826対応) (E)
        //sql.append("    ,HENPIN_NUKI_VL                         ");
        // 2016.12.20 VINX T.Kamei FIVI(#3387対応) (E)
        // 2016.11.29 VINX Y.Itaki FIVI(#3010対応) (E)
        sql.append("    ,CASE ");
        // 2016.12.20 VINX T.Kamei FIVI(#3387対応) (S)
        //sql.append("        WHEN TEIKAN_KB = '1' THEN DTS.HENPIN_QT ");
        //sql.append("        WHEN TEIKAN_KB = '2' THEN DTS.HENPIN_WEIGHT ");
        // 2017.01.31 VINX J.Endo FIVI(#3826対応) (S)
        //sql.append("        WHEN TEIKAN_KB = '1' THEN DTS.HENPIN_QT * -1");
        //sql.append("        WHEN TEIKAN_KB = '2' THEN DTS.HENPIN_WEIGHT * -1");
        sql.append("        WHEN TEIKAN_KB = '1' THEN ");
        sql.append("            CASE ");
        sql.append("                WHEN URIAGE_KB = '1' THEN DTS.HENPIN_QT * -1 ");
        sql.append("                WHEN URIAGE_KB = '2' THEN DTS.HENPIN_QT * -1 ");
        sql.append("                ELSE                      DTS.HENPIN_QT      ");
        sql.append("            END ");
        sql.append("        WHEN TEIKAN_KB = '2' THEN ");
        sql.append("            CASE ");
        sql.append("                WHEN URIAGE_KB = '1' THEN DTS.HENPIN_WEIGHT * -1 ");
        sql.append("                WHEN URIAGE_KB = '2' THEN DTS.HENPIN_WEIGHT * -1 ");
        sql.append("                ELSE                      DTS.HENPIN_WEIGHT      ");
        sql.append("            END ");
        // 2017.01.31 VINX J.Endo FIVI(#3826対応) (E)
        // 2016.12.20 VINX T.Kamei FIVI(#3387対応) (E)
        sql.append("        ELSE 0 ");
        sql.append("     END ");
        // 2016.10.20 VINX k.Hyo FIVI(#2408対応) (E)
        sql.append("    ,?                                      ");
        sql.append("    ,DTS.BUNRUI1_CD                         ");
        sql.append("    ,DTS.BUNRUI2_CD                         ");
        sql.append("    ,DTS.BUNRUI5_CD                         ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(S)
        sql.append("    ,DTS.END_HANBAI_TS                      ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(E)
        sql.append("    ,?                                      ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(S)
        // 2016.09.06 VINX Y.Itaki FIVI(#1879対応) MOD(S)
        //sql.append("    ,DTS.URIAGE_SOURI_QT                    ");
        sql.append("    ,DTS.URIAGE_SOURI_QT + DTS.HENPIN_QT    ");
        // 2016.09.06 VINX Y.Itaki FIVI(#1879対応) MOD(E)
        sql.append("    ,?                                      ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 ADD(E)
        // 2016.09.15 VINX Y.Itaki FIVI(#2009対応) ADD(S)
        sql.append("    ,DTS.URIAGE_KB                          ");
        // 2016.09.15 VINX Y.Itaki FIVI(#2009対応) ADD(E)
        sql.append("FROM                                        ");
        // 2016.06.01 VINX K.Hirano FIVI(#1475対応) MOD(S)
        // 2016.05.20 VINX S.Kashihara FIVI対応 MOD(S)
        //sql.append("    DT_TANPIN_SEISAN DTS                    ");
        // sql.append("    DT_TANPIN_SEISAN_HMPR_BR DTS            ");
        // 2016.05.20 VINX S.Kashihara FIVI対応 MOD(E)
        sql.append("    DT_TANPIN_SEISAN_HMPR_BR_CK DTS            ");
        // 2016.06.01 VINX K.Hirano FIVI(#1475対応) MOD(E)
        sql.append("WHERE                                       ");
        sql.append("    DTS.COMP_CD     =   ?     AND           ");
        // 2016.06.01 VINX K.Hirano FIVI(#1475対応) MOD(S)
        //sql.append("    DTS.KEIJO_DT    =   ?                   ");
        sql.append("    DTS.DATA_SAKUSEI_DT    =   ?                   ");
        // 2016.06.01 VINX K.Hirano FIVI(#1475対応) MOD(E)

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
        inputBean = (TanpinUriageTorikomiDaoInputBean) input;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new TanpinUriageTorikomiDao();
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
