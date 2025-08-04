package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <P>タイトル: UriageRuisekiTorikomiDao クラス</p>
 *  <P>説明: 売上累積取込処理です。</p>
 *  <P>著作権: Copyright (c) 2011</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author T.Kuzuhara
 *  @version 1.0 
 *  @Version 3.00 (2013.10.16) Y.Tominaga [CUS00057] ランドローム様対応　SQLLoaderを使用した取込処理から、「単品精算データ」より「売上累積ワーク」へ全件挿入する。
 *                                                                       不足した店舗のデータがある場合は、その不足店舗分のデータを「単品点検データ」より「売上累積ワーク」へ挿入する。
 *  @Version 3.01 (2013.10.24) T.Ooshiro  [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №028
 */
public class UriageRuisekiTorikomiDao implements DaoIf {
    
    // バッチ処理名
    private static final String BATCH_NAME = "売上累積取込処理";

// 2013.10.16 Y.Tominaga [CUS00057] SQLLoaderを使用した取込処理から、「単品精算データ」、「単品点検データ」より「売上累積ワーク」へ全件挿入する。 (S)
    
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // プロパティファイルよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // ダミーDPTコード取得
    private static final String DUMMY_BUNRUI1_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI1_CD);
    
//    // 更新対象テーブル名 
//    private static final String TABLE_NAME = "WK_URIAGE_RUISEKI";
//    
//    // 商品コード更新用SQL文変数
//    private static final String SYOHIN_CD_UPDATE = "syohinCdUpdate";
//
//    // 入力ビーン
//    private UriageRuisekiTorikomiDaoInputBean inputBean = null;
    
    
    
    /**
     * 売上累積取込処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
       
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        
        // オブジェクトを初期化する
        PreparedStatementEx psWkUriRuisekiTrun = null;
        PreparedStatementEx psWkUriRuisekiTorikomiSeisan = null;
        PreparedStatementEx psWkUriRuisekiTorikomiTenken = null;
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　売上累積取込処理を開始します。");
        
        // 売上累積ワーク全件削除(トランケート)
        psWkUriRuisekiTrun = invoker.getDataBase().prepareStatement(wkUriRuisekiTrun());
        psWkUriRuisekiTrun.executeUpdate();
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　単品精算データより、売上累積ワークへの取込処理を開始します。");

        /** 単品精算データより、売上累積ワークへデータのINSERT*/
        
        int kensu = 0;
        int index = 1;
        
        psWkUriRuisekiTorikomiSeisan = invoker.getDataBase().prepareStatement(wkUriRuisekiTorikomiSeisan());
        
        psWkUriRuisekiTorikomiSeisan.setString(index++, COMP_CD);
        psWkUriRuisekiTorikomiSeisan.setString(index++, BATCH_DT);
        
        kensu = psWkUriRuisekiTorikomiSeisan.executeUpdate();
        
        // 明示的にコミット
        invoker.getDataBase().commit();
        
        // 取込結果ログ出力
        invoker.infoLog(strUserID + "　取込件数は" + kensu + "件です。");
        
        // ログ出力
        invoker.infoLog(strUserID + "　：　単品点検データより、売上累積ワークへの不足店舗分のデータ取込処理を開始します。");
        
        /** 単品点検データより、売上累積ワークへ不足店舗分のデータのINSERT*/
        
        kensu = 0;
        index = 1;

        final String YR = BATCH_DT.substring(UriageCommonConstants.SUBSTR_YEAR_START, UriageCommonConstants.SUBSTR_YEAR_END);
        final String MN = BATCH_DT.substring(UriageCommonConstants.SUBSTR_MONTH_START, UriageCommonConstants.SUBSTR_MONTH_END);
        final String DD = BATCH_DT.substring(UriageCommonConstants.SUBSTR_DAY_START, UriageCommonConstants.SUBSTR_DAY_END);

        psWkUriRuisekiTorikomiTenken = invoker.getDataBase().prepareStatement(wkUriRuisekiTorikomiSeisanTenken());

        psWkUriRuisekiTorikomiTenken.setString(index++, COMP_CD);
        psWkUriRuisekiTorikomiTenken.setString(index++, YR);
        psWkUriRuisekiTorikomiTenken.setString(index++, MN);
        psWkUriRuisekiTorikomiTenken.setString(index++, DD);
        psWkUriRuisekiTorikomiTenken.setString(index++, COMP_CD);
        psWkUriRuisekiTorikomiTenken.setString(index++, BATCH_DT);
        
        kensu = psWkUriRuisekiTorikomiTenken.executeUpdate();
        
        // 取込結果ログ出力
        invoker.infoLog(strUserID + "　取込件数は" + kensu + "件です。");
        
        // 終了結果ログ出力
        invoker.infoLog(strUserID + "　：　売上累積取込処理を終了します。");
        
    }

    /**
     * 売上累積ワークへ取込処理を行うSQL
     * @return
     */
    private String wkUriRuisekiTorikomiSeisan() {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append(" INSERT /*+append */ INTO            ");
        sb.append("     WK_URIAGE_RUISEKI               ");
        sb.append("     (                               ");
        sb.append("     COMP_CD                         ");
        sb.append("     ,KEIJO_DT                       ");
        sb.append("     ,TENPO_CD                       ");
        sb.append("     ,POS_CD                         ");
        sb.append("     ,SYOHIN_CD                      ");
        sb.append("     ,BAITANKA_VL                    ");
        sb.append("     ,DAIBUNRUI2_CD                  ");
        sb.append("     ,RECEIPT_NA                     ");
        sb.append("     ,DAIBUNRUI1_CD                  ");
        sb.append("     ,KIKAKU_CD                      ");
        sb.append("     ,SYOHIN_KB                      ");
        sb.append("     ,MIX_MATCH_CD                   ");
        sb.append("     ,MIX_MATCH_TYPE_TX              ");
        sb.append("     ,BUNDLE_1_QT                    ");
        sb.append("     ,BUNDLE_1_VL                    ");
        sb.append("     ,BUNDLE_2_QT                    ");
        sb.append("     ,BUNDLE_2_VL                    ");
        sb.append("     ,MIX_MATCH_KANA_NA              ");
        sb.append("     ,URIAGE_VL                      ");
        sb.append("     ,URIAGE_GENKA_VL                ");
        sb.append("     ,URIAGE_QT                      ");
        sb.append("     ,NEBIKI_VL                      ");
        sb.append("     ,NEBIKI_QT                      ");
        sb.append("     ,KAKO_VL                        ");
        sb.append("     ,KAKO_QT                        ");
        sb.append("     ,HAIKI_VL                       ");
        sb.append("     ,HAIKI_QT                       ");
        sb.append("     ,HENPIN_VL                      ");
        sb.append("     ,HENPIN_QT                      ");
        sb.append("     ,KYAKU_QT                       ");
        sb.append("     ,BUNRUI1_CD                     ");
        sb.append("     ,BUNRUI2_CD                     ");
        sb.append("     ,BUNRUI5_CD                     ");
        sb.append("     ,END_HANBAI_TS                  ");
        sb.append("     ,MM_POINT_TAISYO_KB             ");
        sb.append("     )                               ");
        sb.append("     SELECT                          ");
        sb.append("         COMP_CD                     ");
        sb.append("         ,KEIJO_DT                   ");
        sb.append("         ,TENPO_CD                   ");
        sb.append("         ,TANPIN_CD                  ");
        sb.append("         ,TANPIN_CD                  ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,URIAGE_SOURI_VL            ");
        sb.append("         ,null                       ");
        sb.append("         ,URIAGE_SOURI_QT            ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("     FROM                            ");
        sb.append("         DT_TANPIN_SEISAN            ");
        sb.append("     WHERE                           ");
        sb.append("         COMP_CD = ?        AND      ");
        sb.append("         KEIJO_DT = ?       AND      ");
        sb.append("         BUNRUI1_CD <> '" + DUMMY_BUNRUI1_CD + "'    ");
        
        return sb.toString();
    }

    /**
     * 不足店舗分のデータを売上累積ワークへ取込処理を行うSQL
     * @return
     */
    private String wkUriRuisekiTorikomiSeisanTenken() {

        StringBuffer sb = new StringBuffer();
        
        sb.append(" INSERT /*+append */ INTO            ");
        sb.append("     WK_URIAGE_RUISEKI               ");
        sb.append("     (                               ");
        sb.append("     COMP_CD                         ");
        sb.append("     ,KEIJO_DT                       ");
        sb.append("     ,TENPO_CD                       ");
        sb.append("     ,POS_CD                         ");
        sb.append("     ,SYOHIN_CD                      ");
        sb.append("     ,BAITANKA_VL                    ");
        sb.append("     ,DAIBUNRUI2_CD                  ");
        sb.append("     ,RECEIPT_NA                     ");
        sb.append("     ,DAIBUNRUI1_CD                  ");
        sb.append("     ,KIKAKU_CD                      ");
        sb.append("     ,SYOHIN_KB                      ");
        sb.append("     ,MIX_MATCH_CD                   ");
        sb.append("     ,MIX_MATCH_TYPE_TX              ");
        sb.append("     ,BUNDLE_1_QT                    ");
        sb.append("     ,BUNDLE_1_VL                    ");
        sb.append("     ,BUNDLE_2_QT                    ");
        sb.append("     ,BUNDLE_2_VL                    ");
        sb.append("     ,MIX_MATCH_KANA_NA              ");
        sb.append("     ,URIAGE_VL                      ");
        sb.append("     ,URIAGE_GENKA_VL                ");
        sb.append("     ,URIAGE_QT                      ");
        sb.append("     ,NEBIKI_VL                      ");
        sb.append("     ,NEBIKI_QT                      ");
        sb.append("     ,KAKO_VL                        ");
        sb.append("     ,KAKO_QT                        ");
        sb.append("     ,HAIKI_VL                       ");
        sb.append("     ,HAIKI_QT                       ");
        sb.append("     ,HENPIN_VL                      ");
        sb.append("     ,HENPIN_QT                      ");
        sb.append("     ,KYAKU_QT                       ");
        sb.append("     ,BUNRUI1_CD                     ");
        sb.append("     ,BUNRUI2_CD                     ");
        sb.append("     ,BUNRUI5_CD                     ");
        sb.append("     ,END_HANBAI_TS                  ");
        sb.append("     ,MM_POINT_TAISYO_KB             ");
        sb.append("     )                               ");
        sb.append("     SELECT                          ");
        sb.append("         ZJTU.COMP_CD                ");
        sb.append("         ,ZJTU.KEIJO_DT              ");
        sb.append("         ,ZJTU.TENPO_CD              ");
        sb.append("         ,ZJTU.TANPIN_CD             ");
        sb.append("         ,ZJTU.TANPIN_CD             ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,ZJTU.URIAGE_VL             ");
        sb.append("         ,null                       ");
        sb.append("         ,ZJTU.URIAGE_QT             ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("         ,null                       ");
        sb.append("     FROM                            ");
        sb.append("         (                           ");
        sb.append("         SELECT                      ");
        sb.append("             COMP_CD                 ");
        sb.append("             ,YR || MN || DD AS KEIJO_DT ");
        sb.append("             ,TENPO_CD               ");
        sb.append("             ,SYOHIN_CD AS TANPIN_CD ");
        sb.append("             ,SUM(URIAGE_VL) AS URIAGE_VL");
        sb.append("             ,SUM(URIAGE_QT) AS URIAGE_QT");
        sb.append("         FROM                        ");
        sb.append("             ZEN_JIKANTAI_TANPIN_URIAGE ");
        sb.append("         WHERE                       ");
        sb.append("             COMP_CD = ?        AND  ");
        sb.append("             YR      = ?        AND  ");
        sb.append("             MN      = ?        AND  ");
        sb.append("             DD      = ?        AND  ");
        sb.append("             BUNRUI1_CD <> '" + DUMMY_BUNRUI1_CD + "'      ");
        sb.append("         GROUP BY                    ");
        sb.append("             COMP_CD                 ");
        sb.append("             ,YR || MN || DD         ");
        sb.append("             ,TENPO_CD               ");
        sb.append("             ,SYOHIN_CD              ");
        sb.append("         ) ZJTU                      ");
        sb.append("     WHERE                           ");
        sb.append("         NOT EXISTS                  ");
        sb.append("         (                           ");
        sb.append("         SELECT                      ");
        sb.append("             *                       ");
        sb.append("         FROM                        ");
        sb.append("             (                       ");
        sb.append("             SELECT                  ");
        sb.append("                 COMP_CD             ");
        sb.append("                 ,KEIJO_DT           ");
        sb.append("                 ,TENPO_CD           ");
        sb.append("             FROM                    ");
        sb.append("                 DT_TANPIN_SEISAN    ");
        sb.append("             WHERE                   ");
        sb.append("                 COMP_CD = ?    AND  ");
        sb.append("                 KEIJO_DT = ?   AND  ");
        sb.append("                 BUNRUI1_CD <> '" + DUMMY_BUNRUI1_CD + "' ");
        sb.append("             GROUP BY                ");
        sb.append("                 COMP_CD             ");
        sb.append("                 ,KEIJO_DT           ");
        sb.append("                 ,TENPO_CD           ");
        sb.append("             ) DTS                   ");
        sb.append("         WHERE                       ");
        sb.append("             ZJTU.COMP_CD = DTS.COMP_CD    AND");
        sb.append("             ZJTU.KEIJO_DT = DTS.KEIJO_DT  AND");
        sb.append("             ZJTU.TENPO_CD = DTS.TENPO_CD");
        sb.append("         )                           ");

        return sb.toString();
    }
    
    /**
     * 売上累積ワークのトランケートを行うSQL
     * @return String
     */
    private String wkUriRuisekiTrun() {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append(" TRUNCATE TABLE");
        sb.append("     WK_URIAGE_RUISEKI");
        
        return sb.toString();
    }

    
//    /**
//    * 売上累積取込処理を行う
//    * @param DaoInvokerIf invoker
//    */
//   public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
//      
//       // ユーザーID
//       String strUserID = invoker.getUserId() + " " + BATCH_NAME;
//       
//       // ログ出力
//       invoker.infoLog(strUserID + "　：　売上累積取込処理を開始します。");
//
//       // FTPファイルの存在をチェックする
//       if (!FiFileUtility.pathFileExists(FiResorceUtility.getPropertie(UriResorceKeyConstant.DATA_DIR_PATH), inputBean.getDataFileName())) {
//           // FTPファイルが無い場合、ログ出力
//           invoker.warnLog(strUserID + "　：　売上累積ファイル(" + inputBean.getDataFileName() + ")が存在しません。売上累積取込処理は行われませんでした。");
//           throw new DaoException("データ取込処理にて例外エラー発生。");
//       }
//       
//       // ファイルが存在しても空の場合はエラーにする
//       File file = new File(FiResorceUtility.getPropertie(UriResorceKeyConstant.DATA_DIR_PATH) + "/" + inputBean.getDataFileName());
//       
//       if (file.length() == 0) {
//           invoker.warnLog(strUserID + "　：　新単品サマリファイル(" + inputBean.getDataFileName() + ")が空です。単品売上取込処理は行われませんでした。");
//           invoker.infoLog(strUserID + "　：　単品売上取込処理を終了します。");
//           throw new DaoException("データ取込処理にて例外エラー発生。");
//       }
//       
//       // ログ出力
//       invoker.infoLog(strUserID + "　：　SQL*Loaderで、売上累積ワークへのデータ取込処理を開始します。");
//
//       // 新単品サマリ差分ワークテーブルデータの取込を行う
//       DataImportDaoOutputBean outBean = FiDataImportDaoUtility.executeDataAccess(invoker, inputBean.getDataFileName(), inputBean.getFormatFileName(), inputBean.getBackupFileName(), inputBean
//               .getLogFileName(), TABLE_NAME);
//
//       // outputBeanの終了ステータスより処理結果判定
//       if (outBean.getResultCd() != 0) {
//           // 正常終了以外は異常終了
//           invoker.warnLog(strUserID + "　：　SQL*Loaderで、売上累積ワークへのデータ取込処理にて例外エラーが発生しました。");
//           throw new DaoException("データ取込処理にて例外エラー発生。");
//       }
//
//       // ログ出力
//       invoker.infoLog(strUserID + "　：　SQL*Loaderで、売上累積ワークへのデータ取込処理を終了します。取込件数は" + outBean.getResultCount() + "件です。");
//       
//       // 商品コード編集
//       
//       // 処理開始ログ出力
//       invoker.infoLog(strUserID + "　：　売上累積の商品コード変換処理を開始します。");
//       PreparedStatementEx preparedStatementEx = invoker.getDataBase().prepareStatement(SYOHIN_CD_UPDATE);
//       int updateCnt = preparedStatementEx.executeUpdate();
//       
//       // 処理終了ログ出力
//       invoker.infoLog(strUserID + "　：　売上累積ログの商品コード変換処理を終了します。　" + updateCnt + "件の商品コードを変換しました。");
//       
//       invoker.infoLog(strUserID + "　：　売上累積取込処理を終了します。");
//       
//   }

// 2013.10.16 Y.Tominaga [CUS00057] SQLLoaderを使用した取込処理から、「単品精算データ」、「単品点検データ」より「売上累積ワーク」へ全件挿入する。 (E)
    
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

}
