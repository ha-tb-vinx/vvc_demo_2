package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: CheckerBetsuUriageSyukeiDao クラス</p>
 *  <P>説明: チェッカー別売上集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0
 *  @Version 3.00 (2016.05.10) monden    S03対応
 *  @Version 3.01 (2016.09.13) Y.Itaki   #2072対応
 *  @Version 3.02 (2016.11.21) J.Endo    #2818対応
 *  @Version 3.03 (2016.12.13) J.Endo    #3305対応
 *  @Version 3.04 (2017.01.24) J.Endo    #3184対応
 *  @Version 3.05 (2017.02.08) J.Endo    #3931対応
 *  @Version 3.06 (2017.04.07) X.Liu     #4525対応
 *  @Version 3.07 (2017.06.14) X.Liu     #5399対応
 *  @Version 3.08 (2017.07.03) J.Endo    #5040対応
 *  @Version 3.09 (2017.08.31) N.Katou   #5840対応
 *  @Version 3.10 (2017.09.13) X.Liu     #5901対応
 *  @Version 3.11 (2022.10.25) SIEU.D #6682対応
 */
public class CheckerBetsuUriageSyukeiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "チェッカー別売上集計処理";
    // バッチID
    private static final String BATCH_ID = "URIB051020";
    // チェッカー精算売上ワーク.店舗コード
    private static final String WCSU_TENPO_CD = "WCSU_TENPO_CD";
    // 店舗マスタ.店舗コード
    private static final String RT_TENPO_CD = "RT_TENPO_CD";
    // 計上日
    private static final String KEIJO_DT = "KEIJO_DT";
    // チェッカーコード
    private static final String CHECKER_CD = "CHECKER_CD";
    // 日計ネット
    private static final String DAY_NET_VL = "DAY_NET_VL";
    // 2016/11/21 VINX J.Endo #2818対応 ADD(S)
    // 支払金額
    private static final String SHIHARAI_VL = "SHIHARAI_VL";
    // 2016/11/21 VINX J.Endo #2818対応 ADD(E)
    // 店別：POS金額
    private static final String DTDU_POS_VL = "DTDU_POS_VL";
    // 違算：POS金額
    private static final String DTIS_POS_VL = "DTIS_POS_VL";
    // 店舗コード
    private static final String TENPO_CD = "TENPO_CD";

    // 2016/05/11 VINX #S03対応（S)
    // 支払種別コード
    private static final String SHIHARAI_SYUBETSU_CD = "SHIHARAI_SYUBETSU_CD";
    // サブ支払種別コード
    private static final String SHIHARAI_SYUBETSU_SUB_CD = "SHIHARAI_SYUBETSU_SUB_CD";
    // 2016/05/11 VINX #S03対応（E)

    // 2017/08/31 VINX N.Katou #5840対応 (S)
    // レシート枚数
    private static final String RECEIPT_QT = "RECEIPT_QT";
    // 集金額
    private static final String SYUUKIN_VL = "SYUUKIN_VL";
    // 超過額
    private static final String CYOUKA_VL = "CYOUKA_VL";
    // 訂正後超過額
    private static final String TEISEIGO_CYOUKA_VL = "TEISEIGO_CYOUKA_VL";
    // 2017/08/31 VINX N.Katou #5840対応 (E)
    
    
    // 検索チェッカー精算売上ワークSQL文用定数
    private static final String SEL_CHECKER_SEISAN = "selectCheckerSeisan";
    // 登録店別違算精算データSQL文用定数
    private static final String INS_TENISAN = "createTenIsan";
    // 検索店別DPT売上SQL文用定数
    private static final String SEL_TEIBETSU_DPT_URI = "selectTeibetsuDPTUriage";
    // 更新店精算状況SQL文用定数
    private static final String UPD_TEI_SEISAN_STATE = "updateTenSeisanState";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /**
     * チェッカー別売上集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 店別違算精算データ追加した件数
        int intCreateCount = 0;
        // 店別違算精算データ更新した件数
        int intUpdateCount = 0;
        // 判断データ件数
        int intLine = 0;
        // エラーフラグ
        boolean blnErrFlg = false;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // チェッカー精算売上ワーク.店舗コード
        String strWCSUTenpoCD = null;
        // 店舗マスタ.店舗コード
        String strRTTenpoCD = null;
        // 判断用店舗コード
        String strTempTenpoCD = null;

        // DPT精算状況フラグ設定用変数
        //String strBunrui1SeisanFlg = null;
        /** 090902 yasuda update (DPT精算状況フラグ⇒精算状況フラグ) */
        /** 091117 shibuya update (精算状況フラグ⇒違算精算状況フラグ) */
        String strSeisanFlg = null;
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;
        PreparedStatementEx psCreateTenIsan = null;
        PreparedStatementEx psUpdateTenSeisanState = null;
        ResultSet resultSet = null;
        ResultSet rs = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　チェッカー別売上集計処理を開始します。");
        invoker.infoLog(strUserID + "　：　店別違算精算データ追加処理を開始します。");
        //#5901 Add X.Liu 2017.09.13 (S)
        invoker.infoLog(strUserID + "　：　店別精算状況データ更新を開始します。");
        //#5901 Add X.Liu 2017.09.13 (E)
        // 2016/05/11 VINX #S03対応（S)
        // SQLを取得し、パラメータを条件にバインドする
//        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_CHECKER_SEISAN);
        preparedStatementEx = invoker.getDataBase().prepareStatement(getSelectCheckerSeisanUriageWorkSQL());
        // 2016/05/11 VINX #S03対応（E)

        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(2, BATCH_DT);
        // バッチ日付
        preparedStatementEx.setString(3, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(4, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(5, BATCH_DT);
        // 2016/11/21 VINX J.Endo #2818対応 ADD(S)
        // 法人コード
        preparedStatementEx.setString(6, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(7, BATCH_DT);
        // バッチ日付
        preparedStatementEx.setString(8, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(9, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(10, BATCH_DT);
        // 2016/11/21 VINX J.Endo #2818対応 ADD(E)

        // SQLを実行して、「チェッカー精算売上ワーク」データ抽出
        resultSet = preparedStatementEx.executeQuery();

        // 登録店別違算精算データSQLを取得し、パラメータを条件にバインドする
        // 2016/05/11 VINX #S03対応（S)
//        psCreateTenIsan = invoker.getDataBase().prepareStatement(INS_TENISAN);
        psCreateTenIsan = invoker.getDataBase().prepareStatement(getInsertTenIsanSeisanSQL());
        // 2016/05/11 VINX #S03対応（E)
        
        // #6682 ADD 2022.10.25 SIEU.D (S)
        Map<String, Map> mpUpdateTenSeisan = new HashMap<String, Map>();
        // #6682 ADD 2022.10.25 SIEU.D (E)

        while (resultSet.next()) {

            //#4525 Add X.Liu 2017.04.07 (S)
            if("0".equals(resultSet.getString(SHIHARAI_VL) ) && "0".equals(resultSet.getString(DAY_NET_VL) ) )
                continue;
            //#4525 Add X.Liu 2017.04.07 (E)
            // 取得したチェッカー精算売上ワーク.店舗コードは変数にセットする
            strWCSUTenpoCD = resultSet.getString(WCSU_TENPO_CD);
            // 取得した店舗マスタ.店舗コードは変数にセットする
            strRTTenpoCD = resultSet.getString(RT_TENPO_CD);

            // データが一件目の場合
            if (intLine == 0) {
                // エラーフラグにFALSEをセット
                blnErrFlg = false;

                if (strRTTenpoCD == null) {
                    // ログを出力する
                    invoker.warnLog(strUserID + "　：　店舗コード：" + strWCSUTenpoCD + "が店舗マスタに存在しません。");
                    // 判断用店舗コードは変数にセットする
                    strTempTenpoCD = resultSet.getString(WCSU_TENPO_CD);
                    // データ件数を加算する
                    intLine++;
                    // エラーフラグにTRUEをセット
                    blnErrFlg = true;
                    // バイパス、次レコード処理を続行
                    continue;
                }
            } else {
                // 該当レコードの「退避：店舗コード」と前レコードの「退避：店舗コード」同じ場合
                if (strTempTenpoCD.equals(strWCSUTenpoCD)) {
                    // エラーフラグがTRUEの場合
                    if (blnErrFlg == true) {
                        // 判断用店舗コードは変数にセットする
                        strTempTenpoCD = resultSet.getString(WCSU_TENPO_CD);
                        // データ件数を加算する
                        intLine++;
                        continue;
                    }
                } else {
                    // エラーフラグにFALSEをセット
                    blnErrFlg = false;

                    if (strRTTenpoCD == null) {
                        // ログを出力する
                        invoker.warnLog(strUserID + "　：　店舗コード：" + strWCSUTenpoCD + "が店舗マスタに存在しません。");
                        // エラーフラグにTRUEをセット
                        blnErrFlg = true;
                        // 判断用店舗コードは変数にセットする
                        strTempTenpoCD = resultSet.getString(WCSU_TENPO_CD);
                        // データ件数を加算する
                        intLine++;
                        // バイパス、次レコード処理を続行
                        continue;
                    }
                }
            }

            // 法人コード
            psCreateTenIsan.setString(1, COMP_CD);
            // 計上日
            psCreateTenIsan.setString(2, resultSet.getString(KEIJO_DT));
            // 店舗コード
            psCreateTenIsan.setString(3, strWCSUTenpoCD);
            // チェッカーコード
            psCreateTenIsan.setString(4, resultSet.getString(CHECKER_CD));
            // 2016/05/11 VINX #S03対応（S)
            // 支払種別コード
            psCreateTenIsan.setString(5, resultSet.getString(SHIHARAI_SYUBETSU_CD));
            // サブ支払種別コード
            psCreateTenIsan.setString(6, resultSet.getString(SHIHARAI_SYUBETSU_SUB_CD));
            // 2016/05/11 VINX #S03対応（E)
            // 2016/11/21 VINX J.Endo #2818対応 MOD(S)
            //// 日計ネット
            //psCreateTenIsan.setString(7, resultSet.getString(DAY_NET_VL));
            // 支払金額
            psCreateTenIsan.setString(7, resultSet.getString(SHIHARAI_VL));
            // 2016/11/21 VINX J.Endo #2818対応 MOD(E)
            // 2016/09/13 VINX Y.Itaki #2072対応（S)
//            // 作成者ID
//            psCreateTenIsan.setString(8, BATCH_ID);
//            // 作成年月日
//            psCreateTenIsan.setString(9, DBSERVER_DT);
//            // 更新者ID
//            psCreateTenIsan.setString(10, BATCH_ID);
//            // 更新年月日
//            psCreateTenIsan.setString(11, DBSERVER_DT);
            // 日計ネット
            psCreateTenIsan.setString(8, resultSet.getString(DAY_NET_VL));
            // 作成者ID
            psCreateTenIsan.setString(9, BATCH_ID);
            // 作成年月日
            psCreateTenIsan.setString(10, DBSERVER_DT);
            // 更新者ID
            psCreateTenIsan.setString(11, BATCH_ID);
            // 更新年月日
            psCreateTenIsan.setString(12, DBSERVER_DT);
            // 2016/09/13 VINX Y.Itaki #2072対応（E)
            // 2016/12/13 VINX J.Endo #3305対応 ADD(S)
            // 訂正後売上金額
            psCreateTenIsan.setString(13, resultSet.getString(SHIHARAI_VL));
            // 2016/12/13 VINX J.Endo #3305対応 ADD(E)

            // 2017/08/31 VINX N.Katou #5840対応 (S)
            psCreateTenIsan.setString(14, resultSet.getString(RECEIPT_QT));
            psCreateTenIsan.setString(15, resultSet.getString(SYUUKIN_VL));
            psCreateTenIsan.setString(16, resultSet.getString(CYOUKA_VL));
            psCreateTenIsan.setString(17, resultSet.getString(TEISEIGO_CYOUKA_VL));
            // 2017/08/31 VINX N.Katou #5840対応 (E)
            
            // 店別違算精算データ追加件数を設定する
            intCreateCount = intCreateCount + psCreateTenIsan.executeUpdate();

            // 判断用店舗コードは変数にセットする
            strTempTenpoCD = resultSet.getString(WCSU_TENPO_CD);
            // データ件数を加算する
            intLine++;
            //#5901 Add X.Liu 2017.09.13 (S)
            // 更新店別精算状況SQLを取得し、パラメータを条件にバインドする
            
            // #6682 MOD 2022.10.25 SIEU.D (S)
//            psUpdateTenSeisanState = invoker.getDataBase().prepareStatement(getUpdateTenSeisanStateSql());
//            
//            strSeisanFlg = UriageCommonConstants.TORIKOMI_TSUMI_FLAG;
//            //DPT精算状況フラグ
//            psUpdateTenSeisanState.setString(1, strSeisanFlg);
//            // 更新者ID
//            psUpdateTenSeisanState.setString(2, BATCH_ID);
//            // 更新年月日
//            psUpdateTenSeisanState.setString(3, DBSERVER_DT);
//            // 法人コード
//            psUpdateTenSeisanState.setString(4, COMP_CD);
//            // 計上日
//            psUpdateTenSeisanState.setString(5, resultSet.getString(KEIJO_DT));
//            // 店舗コード
//            psUpdateTenSeisanState.setString(6, resultSet.getString(WCSU_TENPO_CD));
//            // 店別精算状況データ更新件数を設定する
//            intUpdateCount = intUpdateCount + psUpdateTenSeisanState.executeUpdate();
//            //#5901 Add X.Liu 2017.09.13 (E)
            
            String updateKey = resultSet.getString(KEIJO_DT) + resultSet.getString(WCSU_TENPO_CD);
            if(!mpUpdateTenSeisan.containsKey(updateKey)) {
                
                Map<String, String> updateValue = new HashMap<String, String>();
                
                // Update
                updateValue.put("ISAN_SEISAN_STATE_FG", UriageCommonConstants.TORIKOMI_TSUMI_FLAG);
                updateValue.put("UPDATE_USER_ID", BATCH_ID);
                updateValue.put("UPDATE_TS", DBSERVER_DT);

                // Where
                updateValue.put("COMP_CD", COMP_CD);
                updateValue.put("KEIJO_DT", resultSet.getString(KEIJO_DT));
                updateValue.put("TENPO_CD", resultSet.getString(WCSU_TENPO_CD));
                
                mpUpdateTenSeisan.put(updateKey, updateValue);

            }
            
            // #6682 MOD 2022.10.25 SIEU.D (E)
        }
        // #6682 ADD 2022.10.25 SIEU.D (S)
        if (!mpUpdateTenSeisan.isEmpty()) {
            psUpdateTenSeisanState = invoker.getDataBase().prepareStatement(getUpdateTenSeisanStateSql());

            for (Iterator iter = mpUpdateTenSeisan.values().iterator(); iter.hasNext();) {
                Map<String, String> updateValue = (Map<String, String>) iter.next();
                int updateIdx = 1;

                //DPT精算状況フラグ
                psUpdateTenSeisanState.setString(updateIdx++, updateValue.get("ISAN_SEISAN_STATE_FG"));
                // 更新者ID
                psUpdateTenSeisanState.setString(updateIdx++, updateValue.get("UPDATE_USER_ID"));
                // 更新年月日
                psUpdateTenSeisanState.setString(updateIdx++, updateValue.get("UPDATE_TS"));
                // 法人コード
                psUpdateTenSeisanState.setString(updateIdx++, updateValue.get("COMP_CD"));
                // 計上日
                psUpdateTenSeisanState.setString(updateIdx++, updateValue.get("KEIJO_DT"));
                // 店舗コード
                psUpdateTenSeisanState.setString(updateIdx++, updateValue.get("TENPO_CD"));

                intUpdateCount = intUpdateCount + psUpdateTenSeisanState.executeUpdate();
            }

        }
        // #6682 MOD 2022.10.25 SIEU.D (E)

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件の店別違算精算データを追加しました。");
        invoker.infoLog(strUserID + "　：　店別違算精算データ追加処理を終了します。");
        //#5901 Del X.Liu 2017.09.13 (S)
//        invoker.infoLog(strUserID + "　：　店別精算状況データ更新を開始します。");
        //#5901 Del X.Liu 2017.09.13 (E)
        //#5901 Add X.Liu 2017.09.13 (S)
        invoker.infoLog(strUserID + "　：　" + intUpdateCount + "件の店別精算状況データを更新しました。");
        invoker.infoLog(strUserID + "　：　店別精算状況データ更新処理を終了します。");
        //#5901 Add X.Liu 2017.09.13 (E)
        // SQLを取得し、パラメータを条件にバインドする
        // 2016/05/11 VINX #S03対応（S)
//        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_TEIBETSU_DPT_URI);
        //#5901 Del X.Liu 2017.09.13 (S)
//        preparedStatementEx = invoker.getDataBase().prepareStatement(getSelectTenDptUriSql());
//        // 2016/05/11 VINX #S03対応（E)
//
//        // 法人コード
//        preparedStatementEx.setString(1, COMP_CD);
//        // 2016/09/13 VINX Y.Itaki #2072対応（S)
////        // バッチ日付
////        preparedStatementEx.setString(2, BATCH_DT);
////        // 法人コード
////        preparedStatementEx.setString(3, COMP_CD);
////        // バッチ日付
////        preparedStatementEx.setString(4, BATCH_DT);
//        // 2017/07/03 VINX J.Endo #5040対応 MOD(S)
//        //// 法人コード
//        //preparedStatementEx.setString(2, COMP_CD);
//        //// バッチ日付
//        //preparedStatementEx.setString(3, BATCH_DT);
//        // バッチ日付
//        preparedStatementEx.setString(2, BATCH_DT);
//        // 法人コード
//        preparedStatementEx.setString(3, COMP_CD);
//        // 2017/07/03 VINX J.Endo #5040対応 MOD(E)
//        // 2016/09/13 VINX Y.Itaki #2072対応（E)
//
//        // SQLを実行して、「チェッカー精算売上ワーク」データ抽出
//        rs = preparedStatementEx.executeQuery();
//
//        // 更新店別精算状況SQLを取得し、パラメータを条件にバインドする
//        psUpdateTenSeisanState = invoker.getDataBase().prepareStatement(getUpdateTenSeisanStateSql());
//
//        while (rs.next()) {
//            // DPT精算状況フラグは１を設定する
//            //strBunrui1SeisanFlg = UriageCommonConstants.TORIKOMI_TSUMI_FLAG;
//            /** 090902 yasuda update (DPT精算状況フラグ⇒精算状況フラグ) */
//            /** 091117 shibuya update (精算状況フラグ⇒違算精算状況フラグ) */
//            strSeisanFlg = UriageCommonConstants.TORIKOMI_TSUMI_FLAG;

//            if (rs.getString(DTIS_POS_VL) == null) {
//                // DPT精算状況フラグは９を設定する
//                //strBunrui1SeisanFlg = UriageCommonConstants.ERROR_FLAG;
//                /** 090902 yasuda update (DPT精算状況フラグ⇒精算状況フラグ) */
//                /** 091117 shibuya update (精算状況フラグ⇒違算精算状況フラグ) */
//                strSeisanFlg = UriageCommonConstants.ERROR_FLAG;
//
//                // ログを出力する
//                invoker.warnLog(strUserID + "　：　DPT売上と違算精算売上が一致しません。計上日：" + rs.getString(KEIJO_DT) + " " + "店舗コード：" + rs.getString(TENPO_CD) + " " + "DPT売上金額：" + rs.getString(DTDU_POS_VL) + " "
//                        + "違算精算売上金額：計上なし");
//
//            }
//            // 「店別：POS金額」≠「違算：POS金額」の場合
//            else if (!rs.getString(DTDU_POS_VL).equals(rs.getString(DTIS_POS_VL))) {
//                // DPT精算状況フラグは９を設定する
//                //strBunrui1SeisanFlg = UriageCommonConstants.ERROR_FLAG;
//                /** 090902 yasuda update (DPT精算状況フラグ⇒精算状況フラグ) */
//                /** 091117 shibuya update (精算状況フラグ⇒違算精算状況フラグ) */
//                strSeisanFlg = UriageCommonConstants.ERROR_FLAG;
//                // ログを出力する
//                invoker.warnLog(strUserID + "　：　DPT売上と違算精算売上が一致しません。計上日：" + rs.getString(KEIJO_DT) + " " + "店舗コード：" + rs.getString(TENPO_CD) + " " + "DPT売上金額：" + rs.getString(DTDU_POS_VL) + " "
//                        + "違算精算売上金額：" + rs.getString(DTIS_POS_VL));
//            }
//
//            // DPT精算状況フラグ
//            //psUpdateTenSeisanState.setString(1, strBunrui1SeisanFlg);
//            /** 090902 yasuda update (DPT精算状況フラグ⇒精算状況フラグ) */
//            /** 091117 shibuya update (精算状況フラグ⇒違算精算状況フラグ) */
//            psUpdateTenSeisanState.setString(1, strSeisanFlg);
//            // 更新者ID
//            psUpdateTenSeisanState.setString(2, BATCH_ID);
//            // 更新年月日
//            psUpdateTenSeisanState.setString(3, DBSERVER_DT);
//            // 法人コード
//            psUpdateTenSeisanState.setString(4, COMP_CD);
//            // 2016/09/13 VINX Y.Itaki #2072対応（S)
////            // バッチ日付
////            psUpdateTenSeisanState.setString(5, BATCH_DT);
//            psUpdateTenSeisanState.setString(5, rs.getString(KEIJO_DT));
//            // 2016/09/13 VINX Y.Itaki #2072対応（E)
//            // 店舗コード
//            psUpdateTenSeisanState.setString(6, rs.getString(TENPO_CD));
//
//            // 店別精算状況データ更新件数を設定する
//            intUpdateCount = intUpdateCount + psUpdateTenSeisanState.executeUpdate();
//        }
//
//        // ログ出力
//        invoker.infoLog(strUserID + "　：　" + intUpdateCount + "件の店別精算状況データを更新しました。");
//        invoker.infoLog(strUserID + "　：　店別精算状況データ更新処理を終了します。");
        //#5901 Del X.Liu 2017.09.13 (E)
        // 2017/01/24 VINX J.Endo #3184対応 ADD(S)
        int insertCount = 0;
        invoker.infoLog(strUserID + "　：　全店精算状況データ作成処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(getInsertZentenSeisanStateSQL());

        // 2017/02/08 VINX J.Endo #0000対応 MOD(S)
        //// 法人コード
        //preparedStatementEx.setString(1, COMP_CD);
        //// 作成者ID
        //preparedStatementEx.setString(2, BATCH_ID);
        //// 作成年月日
        //preparedStatementEx.setString(3, DBSERVER_DT);
        //// 更新者ID
        //preparedStatementEx.setString(4, BATCH_ID);
        //// 更新年月日
        //preparedStatementEx.setString(5, DBSERVER_DT);
        //// 法人コード
        //preparedStatementEx.setString(6, COMP_CD);
        //// バッチ日付
        //preparedStatementEx.setString(7, BATCH_DT);
        //// バッチ日付
        //preparedStatementEx.setString(8, BATCH_DT);
        //// 法人コード
        //preparedStatementEx.setString(9, COMP_CD);
        //// バッチ日付
        //preparedStatementEx.setString(10, BATCH_DT);
        //// 法人コード
        //preparedStatementEx.setString(11, COMP_CD);
        //// バッチ日付
        //preparedStatementEx.setString(12, BATCH_DT);
        //// バッチ日付
        //preparedStatementEx.setString(13, BATCH_DT);
        //// 法人コード
        //preparedStatementEx.setString(14, COMP_CD);
        //// バッチ日付
        //preparedStatementEx.setString(15, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(2, BATCH_DT);
        // バッチ日付
        preparedStatementEx.setString(3, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(4, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(5, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(6, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(7, BATCH_DT);
        // バッチ日付
        preparedStatementEx.setString(8, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(9, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(10, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(11, COMP_CD);
        // 作成者ID
        preparedStatementEx.setString(12, BATCH_ID);
        // 作成年月日
        preparedStatementEx.setString(13, DBSERVER_DT);
        // 更新者ID
        preparedStatementEx.setString(14, BATCH_ID);
        // 更新年月日
        preparedStatementEx.setString(15, DBSERVER_DT);
        // 2017/02/08 VINX J.Endo #0000対応 MOD(E)

        // SQLを実行して、全店精算状況データを追加
        insertCount = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + insertCount + "件の全店精算状況データを追加しました。");
        invoker.infoLog(strUserID + "　：　全店精算状況データ作成処理を終了します。");

        invoker.infoLog(strUserID + "　：　店別チェッカー別精算状況データ作成処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(getInsertTenCheckerSeisanStateSQL());

        // 2017/02/08 VINX J.Endo #0000対応 MOD(S)
        //// 法人コード
        //preparedStatementEx.setString(1, COMP_CD);
        //// 作成者ID
        //preparedStatementEx.setString(2, BATCH_ID);
        //// 作成年月日
        //preparedStatementEx.setString(3, DBSERVER_DT);
        //// 更新者ID
        //preparedStatementEx.setString(4, BATCH_ID);
        //// 更新年月日
        //preparedStatementEx.setString(5, DBSERVER_DT);
        //// 法人コード
        //preparedStatementEx.setString(6, COMP_CD);
        //// バッチ日付
        //preparedStatementEx.setString(7, BATCH_DT);
        //// バッチ日付
        //preparedStatementEx.setString(8, BATCH_DT);
        //// 法人コード
        //preparedStatementEx.setString(9, COMP_CD);
        //// バッチ日付
        //preparedStatementEx.setString(10, BATCH_DT);
        //// 法人コード
        //preparedStatementEx.setString(11, COMP_CD);
        //// バッチ日付
        //preparedStatementEx.setString(12, BATCH_DT);
        //// バッチ日付
        //preparedStatementEx.setString(13, BATCH_DT);
        //// 法人コード
        //preparedStatementEx.setString(14, COMP_CD);
        //// バッチ日付
        //preparedStatementEx.setString(15, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(2, BATCH_DT);
        // バッチ日付
        preparedStatementEx.setString(3, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(4, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(5, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(6, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(7, BATCH_DT);
        // バッチ日付
        preparedStatementEx.setString(8, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(9, COMP_CD);
        // バッチ日付
        preparedStatementEx.setString(10, BATCH_DT);
        // 法人コード
        preparedStatementEx.setString(11, COMP_CD);
        // 作成者ID
        preparedStatementEx.setString(12, BATCH_ID);
        // 作成年月日
        preparedStatementEx.setString(13, DBSERVER_DT);
        // 更新者ID
        preparedStatementEx.setString(14, BATCH_ID);
        // 更新年月日
        preparedStatementEx.setString(15, DBSERVER_DT);
        // 2017/02/08 VINX J.Endo #0000対応 MOD(E)

        // SQLを実行して、店別チェッカー別精算状況データを追加
        insertCount = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + insertCount + "件の店別チェッカー別精算状況データを追加しました。");
        invoker.infoLog(strUserID + "　：　店別チェッカー別精算状況データ作成処理を終了します。");
        // 2017/01/24 VINX J.Endo #3184対応 ADD(E)

        invoker.infoLog(strUserID + "　：　チェッカー別売上集計処理を終了します。");

    }



    // 2016/05/11 VINX #S03対応（S)
    /**
     * 責任者精算データ取得SQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getSelectCheckerSeisanUriageWorkSQL(){

        StringBuffer sql = new StringBuffer("");

        // 2016/11/21 VINX J.Endo #2818対応 MOD(S)
        //sql.append("SELECT                               ");
        //sql.append("  WCSU.KEIJO_DT,                     ");
        //sql.append("  WCSU.TENPO_CD WCSU_TENPO_CD,       ");
        //sql.append("  WCSU.CHECKER_CD,                   ");
        //sql.append("  WCSU.DAY_NET_VL,                   ");
        //sql.append("  RT.TENPO_CD RT_TENPO_CD,           ");
        //sql.append("  WCSU.SHIHARAI_SYUBETSU_CD,         ");
        //sql.append("  WCSU.SHIHARAI_SYUBETSU_SUB_CD      ");
        //sql.append("FROM                                 ");
        //sql.append("  WK_CHECKER_SEISAN_URI WCSU         ");
        //sql.append("INNER JOIN                           ");
        //sql.append("  DT_TEN_SEISAN_STATE DTSS           ");
        //sql.append("ON DTSS.COMP_CD = WCSU.COMP_CD       ");
        //sql.append("AND DTSS.KEIJO_DT = WCSU.KEIJO_DT    ");
        //sql.append("AND DTSS.TENPO_CD = WCSU.TENPO_CD    ");
        //sql.append("AND DTSS.ISAN_SEISAN_STATE_FG <> '2' ");
        //sql.append("LEFT JOIN                            ");
        //sql.append("  R_TENPO RT                         ");
        //sql.append("ON RT.HOJIN_CD = ?                   ");
        //sql.append("AND WCSU.TENPO_CD = RT.TENPO_CD      ");
        //sql.append("AND RT.TENPO_KB = '1'                ");
        //sql.append("AND RT.KAITEN_DT <= ?                ");
        //sql.append("AND RT.ZAIMU_END_DT >= ?             ");
        //sql.append("AND RT.DELETE_FG = '0'               ");
        //sql.append("WHERE                                ");
        //sql.append("  WCSU.COMP_CD = ?                   ");
        //sql.append("AND WCSU.DATA_SAKUSEI_DT = ?         ");
        //sql.append("ORDER BY                             ");
        //sql.append("  WCSU.TENPO_CD                      ");
        sql.append("SELECT ");
        sql.append("    KEIJO_DT ");
        sql.append("   ,WCSU_TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SUM(SHIHARAI_VL) SHIHARAI_VL ");
        sql.append("   ,SUM(DAY_NET_VL) DAY_NET_VL ");
        sql.append("   ,RT_TENPO_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("   ,SUM(RECEIPT_QT) RECEIPT_QT ");
        sql.append("   ,SUM(SYUUKIN_VL) SYUUKIN_VL ");
        sql.append("   ,SUM(CYOUKA_VL) CYOUKA_VL ");
        sql.append("   ,SUM(CYOUKA_VL) TEISEIGO_CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("FROM ( ");
        sql.append("    SELECT ");
        sql.append("        WCSU.KEIJO_DT ");
        sql.append("       ,WCSU.TENPO_CD WCSU_TENPO_CD ");
        sql.append("       ,WCSU.CHECKER_CD ");
        sql.append("       ,0 SHIHARAI_VL ");
        sql.append("       ,WCSU.DAY_NET_VL DAY_NET_VL ");
        sql.append("       ,RT.TENPO_CD RT_TENPO_CD ");
        sql.append("       ,WCSU.SHIHARAI_SYUBETSU_CD ");
        sql.append("       ,WCSU.SHIHARAI_SYUBETSU_SUB_CD ");
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("       ,0 RECEIPT_QT ");
        sql.append("       ,0 SYUUKIN_VL ");
        sql.append("       ,0 CYOUKA_VL ");
        sql.append("       ,0 TEISEIGO_CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("    FROM ");
        sql.append("        WK_CHECKER_SEISAN_URI WCSU ");
        sql.append("    INNER JOIN ");
        sql.append("        DT_TEN_SEISAN_STATE DTSS ON ");
        sql.append("            DTSS.COMP_CD = WCSU.COMP_CD ");
        sql.append("        AND DTSS.KEIJO_DT = WCSU.KEIJO_DT ");
        sql.append("        AND DTSS.TENPO_CD = WCSU.TENPO_CD ");
        sql.append("        AND DTSS.ISAN_SEISAN_STATE_FG <> '2' ");
        sql.append("        LEFT JOIN ");
        sql.append("            R_TENPO RT ON ");
        sql.append("                RT.HOJIN_CD = ? ");
        sql.append("            AND WCSU.TENPO_CD = RT.TENPO_CD ");
        sql.append("            AND RT.TENPO_KB = '1' ");
        sql.append("            AND RT.KAITEN_DT <= ? ");
        sql.append("            AND RT.ZAIMU_END_DT >= ? ");
        sql.append("            AND RT.DELETE_FG = '0' ");
        sql.append("        WHERE ");
        sql.append("            WCSU.COMP_CD = ? ");
        sql.append("        AND WCSU.DATA_SAKUSEI_DT = ? ");
        sql.append("    UNION ALL ");
        sql.append("    SELECT ");
        sql.append("        WCSUC.KEIJO_DT ");
        sql.append("       ,WCSUC.TENPO_CD WCSUC_TENPO_CD ");
        sql.append("       ,WCSUC.CHECKER_CD ");
        sql.append("       ,WCSUC.SHIHARAI_VL SHIHARAI_VL ");
        sql.append("       ,0 DAY_NET_VL ");
        sql.append("       ,RT2.TENPO_CD RT2_TENPO_CD ");
        sql.append("       ,WCSUC.SHIHARAI_SYUBETSU_CD ");
        sql.append("       ,WCSUC.SHIHARAI_SYUBETSU_SUB_CD ");
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("       ,WCSUC.RECEIPT_QT ");
        sql.append("       ,WCSUC.SYUUKIN_VL ");
        sql.append("       ,WCSUC.CYOUKA_VL ");
        sql.append("       ,WCSUC.CYOUKA_VL TEISEIGO_CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("    FROM ");
        sql.append("        WK_CHECKER_SEISAN_URI_C WCSUC ");
        sql.append("    INNER JOIN ");
        sql.append("        DT_TEN_SEISAN_STATE DTSS2 ON ");
        sql.append("            DTSS2.COMP_CD = WCSUC.COMP_CD ");
        sql.append("        AND DTSS2.KEIJO_DT = WCSUC.KEIJO_DT ");
        sql.append("        AND DTSS2.TENPO_CD = WCSUC.TENPO_CD ");
        sql.append("        AND DTSS2.ISAN_SEISAN_STATE_FG <> '2' ");
        sql.append("        LEFT JOIN ");
        sql.append("            R_TENPO RT2 ON ");
        sql.append("                RT2.HOJIN_CD = ? ");
        sql.append("            AND WCSUC.TENPO_CD = RT2.TENPO_CD ");
        sql.append("            AND RT2.TENPO_KB = '1' ");
        sql.append("            AND RT2.KAITEN_DT <= ? ");
        sql.append("            AND RT2.ZAIMU_END_DT >= ? ");
        sql.append("            AND RT2.DELETE_FG = '0' ");
        sql.append("        WHERE ");
        sql.append("            WCSUC.COMP_CD = ? ");
        sql.append("        AND WCSUC.DATA_SAKUSEI_DT = ? ");
        sql.append("    ) ");
        sql.append("GROUP BY ");
        sql.append("    KEIJO_DT ");
        sql.append("   ,WCSU_TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,RT_TENPO_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("ORDER BY ");
        sql.append("    WCSU_TENPO_CD");
        // 2016/11/21 VINX J.Endo #2818対応 MOD(E)

        return sql.toString();
    }

    /**
     * 店別違算精算データ追加SQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getInsertTenIsanSeisanSQL(){

        StringBuffer sql = new StringBuffer("");

        //#5399 Mod X.Liu 2017.06.14 (S)
        sql.append("MERGE ");
        sql.append("INTO DT_TEN_ISAN_SEISAN DTIS ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      ? AS COMP_CD");
        sql.append("      , ? AS KEIJO_DT");
        sql.append("      , ? AS TENPO_CD");
        sql.append("      , ? AS CHECKER_CD");
        sql.append("      , ? AS SHIHARAI_SYUBETSU_CD");
        sql.append("      , ? AS SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("      , ? AS URIAGE_VL");
        sql.append("      , ? AS ARIDAKA_VL");
        sql.append("      , ? AS INSERT_USER_ID");
        sql.append("      , ? AS INSERT_TS");
        sql.append("      , ? AS UPDATE_USER_ID");
        sql.append("      , ? AS UPDATE_TS");
        sql.append("      , ? AS TEISEIGO_URIAGE_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("      , ? AS RECEIPT_QT ");
        sql.append("      , ? AS SYUUKIN_VL ");
        sql.append("      , ? AS CYOUKA_VL ");
        sql.append("      , ? AS TEISEIGO_CYOUKA_VL ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("    FROM");
        sql.append("      DUAL");
        sql.append("  ) DUAL ");
        sql.append("    ON ( ");
        sql.append("      DTIS.COMP_CD = DUAL.COMP_CD ");
        sql.append("      AND DTIS.KEIJO_DT = DUAL.KEIJO_DT ");
        sql.append("      AND DTIS.TENPO_CD = DUAL.TENPO_CD ");
        sql.append("      AND DTIS.CHECKER_CD = DUAL.CHECKER_CD ");
        sql.append("      AND DTIS.SHIHARAI_SYUBETSU_CD = DUAL.SHIHARAI_SYUBETSU_CD ");
        sql.append("      AND DTIS.SHIHARAI_SYUBETSU_SUB_CD = DUAL.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  URIAGE_VL = DUAL.URIAGE_VL");
        sql.append("  , ARIDAKA_VL = DUAL.ARIDAKA_VL");
        sql.append("  , TEISEIGO_URIAGE_VL = DUAL.TEISEIGO_URIAGE_VL");
        sql.append("  , UPDATE_USER_ID = DUAL.UPDATE_USER_ID");
        // 2017/08/31 VINX N.Katou #5840対応 (S)
//        sql.append("  , UPDATE_TS = DUAL.UPDATE_TS WHEN NOT MATCHED THEN ");
        sql.append("  , UPDATE_TS = DUAL.UPDATE_TS ");
        sql.append("  , RECEIPT_QT = DTIS.RECEIPT_QT + DUAL.RECEIPT_QT ");
        sql.append("  , SYUUKIN_VL = DTIS.SYUUKIN_VL + DUAL.SYUUKIN_VL ");
        sql.append("  , CYOUKA_VL = DTIS.CYOUKA_VL + DUAL.CYOUKA_VL ");
        sql.append("  , TEISEIGO_CYOUKA_VL = DTIS.TEISEIGO_CYOUKA_VL + DUAL.TEISEIGO_CYOUKA_VL ");
        sql.append("  WHEN NOT MATCHED THEN ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
//        sql.append("INSERT INTO               ");
//        sql.append("DT_TEN_ISAN_SEISAN        ");
        sql.append(" INSERT        ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        sql.append("(                         ");
        sql.append("COMP_CD,                  ");
        sql.append("KEIJO_DT,                 ");
        sql.append("TENPO_CD,                 ");
        sql.append("CHECKER_CD,               ");
        sql.append("SHIHARAI_SYUBETSU_CD,     ");
        sql.append("SHIHARAI_SYUBETSU_SUB_CD, ");
        sql.append("URIAGE_VL,                ");
        sql.append("ARIDAKA_VL,               ");
        sql.append("INSERT_USER_ID,           ");
        sql.append("INSERT_TS,                ");
        sql.append("UPDATE_USER_ID,           ");
        // 2016/12/13 VINX J.Endo #3305対応 MOD(S)
        //sql.append("UPDATE_TS)                ");
        sql.append("UPDATE_TS,                ");
        // 2017/08/31 VINX N.Katou #5840対応 (S)
//        sql.append("TEISEIGO_URIAGE_VL)       ");
        sql.append("TEISEIGO_URIAGE_VL,       ");
        // 2016/12/13 VINX J.Endo #3305対応 MOD(E)
        sql.append("RECEIPT_QT,               ");
        sql.append("SYUUKIN_VL,               ");
        sql.append("CYOUKA_VL,                ");
        sql.append("TEISEIGO_CYOUKA_VL)       ");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("VALUES                    ");
        // 2016/12/13 VINX J.Endo #3305対応 MOD(S)
        //// 2016/09/13 VINX Y.Itaki #2072対応（S)
        ////sql.append("(?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, ?)   ");
        //sql.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)   ");
        //// 2016/09/13 VINX Y.Itaki #2072対応（E)
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sql.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)   ");
        // 2016/12/13 VINX J.Endo #3305対応 MOD(E)
        sql.append(" (DUAL.COMP_CD");
        sql.append("    , DUAL.KEIJO_DT");
        sql.append("    , DUAL.TENPO_CD");
        sql.append("    , DUAL.CHECKER_CD");
        sql.append("    , DUAL.SHIHARAI_SYUBETSU_CD");
        sql.append("    , DUAL.SHIHARAI_SYUBETSU_SUB_CD");
        sql.append("    , DUAL.URIAGE_VL");
        sql.append("    , DUAL.ARIDAKA_VL");
        sql.append("    , DUAL.INSERT_USER_ID");
        sql.append("    , DUAL.INSERT_TS");
        sql.append("    , DUAL.UPDATE_USER_ID");
        sql.append("    , DUAL.UPDATE_TS");
        sql.append("    , DUAL.TEISEIGO_URIAGE_VL");
        // 2017/08/31 VINX N.Katou #5840対応 (S)
        sql.append("    , DUAL.RECEIPT_QT");
        sql.append("    , DUAL.SYUUKIN_VL");
        sql.append("    , DUAL.CYOUKA_VL");
        sql.append("    , DUAL.TEISEIGO_CYOUKA_VL");
        // 2017/08/31 VINX N.Katou #5840対応 (E)
        sql.append("  )    ");
        //#5399 Mod X.Liu 2017.06.14 (E)

        return sql.toString();
    }

    /**
     * 店別DPT売上データ取得SQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getSelectTenDptUriSql(){

        StringBuffer sql = new StringBuffer("");

        sql.append("SELECT                               ");
        sql.append("  DTDU.KEIJO_DT,                     ");
        sql.append("  DTDU.POS_VL DTDU_POS_VL,           ");
        sql.append("  DTIS.POS_VL DTIS_POS_VL,           ");
        sql.append("  DTDU.TENPO_CD                      ");
        // 2017/07/03 VINX J.Endo #5040対応 MOD(S)
        //sql.append("FROM                                 ");
        //sql.append("  DT_TEN_DPT_URI DTDU                ");
        sql.append("  FROM ( ");
        sql.append("    SELECT ");
        sql.append("      COMP_CD ");
        sql.append("     ,TENPO_CD ");
        sql.append("     ,KEIJO_DT ");
        sql.append("     ,SUM(POS_VL) AS POS_VL ");
        sql.append("    FROM DT_TEN_DPT_URI ");
        sql.append("    WHERE ");
        sql.append("      COMP_CD = ? AND ");
        sql.append("      DATA_SAKUSEI_DT = ? AND ");
        sql.append("      BUNRUI1_CD = '9999' AND ");
        sql.append("      URIAGE_KB <> '3' ");
        sql.append("    GROUP BY ");
        sql.append("      COMP_CD ");
        sql.append("     ,TENPO_CD ");
        sql.append("     ,KEIJO_DT ");
        sql.append("  ) DTDU ");
        // 2017/07/03 VINX J.Endo #5040対応 MOD(E)
        sql.append("INNER JOIN                           ");
        sql.append("  DT_TEN_SEISAN_STATE DTSS           ");
        sql.append("ON DTSS.COMP_CD = DTDU.COMP_CD       ");
        sql.append("AND DTSS.KEIJO_DT = DTDU.KEIJO_DT    ");
        sql.append("AND DTSS.TENPO_CD = DTDU.TENPO_CD    ");
        sql.append("AND DTSS.ISAN_SEISAN_STATE_FG <> '2' ");
        sql.append("LEFT JOIN                            ");
        sql.append("  (SELECT                            ");
        sql.append("     SUM(URIAGE_VL) POS_VL,          ");
        sql.append("     COMP_CD,                        ");
        sql.append("     TENPO_CD,                       ");
        sql.append("     KEIJO_DT                        ");
        sql.append("  FROM                               ");
        sql.append("     DT_TEN_ISAN_SEISAN              ");
        sql.append("  WHERE                              ");
        sql.append("     COMP_CD = ?                     ");
        // 2016/09/13 VINX Y.Itaki #2072対応（S)
        //sql.append("  AND KEIJO_DT = ?                   ");
        // 2016/09/13 VINX Y.Itaki #2072対応（E)
        sql.append("  GROUP BY                           ");
        sql.append("     COMP_CD, TENPO_CD, KEIJO_DT) DTIS ");
        sql.append("ON DTDU.COMP_CD = DTIS.COMP_CD         ");
        sql.append("AND DTDU.TENPO_CD = DTIS.TENPO_CD      ");
        sql.append("AND DTDU.KEIJO_DT = DTIS.KEIJO_DT      ");
        // 2017/07/03 VINX J.Endo #5040対応 DEL(S)
        //sql.append("WHERE                                  ");
        //sql.append("  DTDU.COMP_CD = ?                     ");
        //sql.append("AND DTDU.DATA_SAKUSEI_DT = ?           ");
        //sql.append("AND DTDU.BUNRUI1_CD = '9999'           ");
        // 2017/07/03 VINX J.Endo #5040対応 DEL(E)
        sql.append("ORDER BY                               ");
        sql.append("  DTDU.TENPO_CD                        ");

        return sql.toString();
    }
    // 2016/05/11 VINX #S03対応（E)


    // 2016/09/13 VINX Y.Itaki #2072対応（S)
    /**
     * 店別精算状況データ更新SQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getUpdateTenSeisanStateSql(){

        StringBuffer sql = new StringBuffer("");

        sql.append("UPDATE ");
        sql.append("  DT_TEN_SEISAN_STATE ");
        sql.append("SET ");
        sql.append("  ISAN_SEISAN_STATE_FG = ?, ");
        sql.append("  UPDATE_USER_ID = ?, ");
        sql.append("  UPDATE_TS = ? ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? ");
        sql.append("AND KEIJO_DT = ? ");
        sql.append("AND TENPO_CD = ? ");

        return sql.toString();
    }
    // 2016/09/13 VINX Y.Itaki #2072対応（E)

    // 2017/01/24 VINX J.Endo #3184対応 ADD(S)
    /**
     * 全店精算状況データ追加SQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getInsertZentenSeisanStateSQL(){

        StringBuffer sql = new StringBuffer("");

        // 2017/02/08 VINX J.Endo #3931対応 MOD(S)
        //sql.append("INSERT INTO DT_ZENTEN_SEISAN_STATE ( ");
        //sql.append("    COMP_CD ");
        //sql.append("   ,KEIJO_DT ");
        //sql.append("   ,SEISAN_STATE_FG ");
        //sql.append("   ,INSERT_USER_ID ");
        //sql.append("   ,INSERT_TS ");
        //sql.append("   ,UPDATE_USER_ID ");
        //sql.append("   ,UPDATE_TS ");
        //sql.append("    ) ");
        //sql.append("SELECT ");
        //sql.append("    ? ");
        //sql.append("   ,KEIJO_DT ");
        //sql.append("   ,'1' ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("FROM ( ");
        //sql.append("    SELECT ");
        //sql.append("        WCSU.KEIJO_DT ");
        //sql.append("       ,RT.TENPO_CD RT_TENPO_CD ");
        //sql.append("    FROM WK_CHECKER_SEISAN_URI WCSU ");
        //sql.append("    INNER JOIN DT_TEN_SEISAN_STATE DTSS ");
        //sql.append("    ON  DTSS.COMP_CD         = WCSU.COMP_CD AND ");
        //sql.append("        DTSS.KEIJO_DT        = WCSU.KEIJO_DT AND ");
        //sql.append("        DTSS.TENPO_CD        = WCSU.TENPO_CD AND ");
        //sql.append("        DTSS.ISAN_SEISAN_STATE_FG <> '2' ");
        //sql.append("    LEFT JOIN R_TENPO RT ");
        //sql.append("    ON  RT.HOJIN_CD          = ? AND ");
        //sql.append("        WCSU.TENPO_CD        = RT.TENPO_CD AND ");
        //sql.append("        RT.TENPO_KB          = '1' AND ");
        //sql.append("        RT.KAITEN_DT        <= ? AND ");
        //sql.append("        RT.ZAIMU_END_DT     >= ? AND ");
        //sql.append("        RT.DELETE_FG         = '0' ");
        //sql.append("    WHERE ");
        //sql.append("        WCSU.COMP_CD         = ? AND ");
        //sql.append("        WCSU.DATA_SAKUSEI_DT = ? ");
        //sql.append("    UNION ALL ");
        //sql.append("    SELECT ");
        //sql.append("        WCSUC.KEIJO_DT ");
        //sql.append("       ,RT2.TENPO_CD RT2_TENPO_CD ");
        //sql.append("    FROM WK_CHECKER_SEISAN_URI_C WCSUC ");
        //sql.append("    INNER JOIN DT_TEN_SEISAN_STATE DTSS2 ");
        //sql.append("    ON  DTSS2.COMP_CD         = WCSUC.COMP_CD AND ");
        //sql.append("        DTSS2.KEIJO_DT        = WCSUC.KEIJO_DT AND ");
        //sql.append("        DTSS2.TENPO_CD        = WCSUC.TENPO_CD AND ");
        //sql.append("        DTSS2.ISAN_SEISAN_STATE_FG <> '2' ");
        //sql.append("    LEFT JOIN R_TENPO RT2 ");
        //sql.append("    ON  RT2.HOJIN_CD          = ? AND ");
        //sql.append("        WCSUC.TENPO_CD        = RT2.TENPO_CD AND ");
        //sql.append("        RT2.TENPO_KB          = '1' AND ");
        //sql.append("        RT2.KAITEN_DT        <= ? AND ");
        //sql.append("        RT2.ZAIMU_END_DT     >= ? AND ");
        //sql.append("        RT2.DELETE_FG         = '0' ");
        //sql.append("    WHERE ");
        //sql.append("        WCSUC.COMP_CD         = ? AND ");
        //sql.append("        WCSUC.DATA_SAKUSEI_DT = ? ) ");
        //sql.append("WHERE ");
        //sql.append("    RT_TENPO_CD IS NOT NULL ");
        //sql.append("GROUP BY ");
        //sql.append("    KEIJO_DT ");
        sql.append("MERGE INTO DT_ZENTEN_SEISAN_STATE DZS ");
        sql.append("USING ( ");
        sql.append("    SELECT ");
        sql.append("        KEIJO_DT ");
        sql.append("    FROM ( ");
        sql.append("        SELECT ");
        sql.append("            WCSU.KEIJO_DT ");
        sql.append("           ,RT.TENPO_CD RT_TENPO_CD ");
        sql.append("        FROM WK_CHECKER_SEISAN_URI WCSU ");
        sql.append("        INNER JOIN DT_TEN_SEISAN_STATE DTSS ");
        sql.append("        ON  DTSS.COMP_CD         = WCSU.COMP_CD AND ");
        sql.append("            DTSS.KEIJO_DT        = WCSU.KEIJO_DT AND ");
        sql.append("            DTSS.TENPO_CD        = WCSU.TENPO_CD AND ");
        sql.append("            DTSS.ISAN_SEISAN_STATE_FG <> '2' ");
        sql.append("        LEFT JOIN R_TENPO RT ");
        sql.append("        ON  RT.HOJIN_CD          = ? AND ");
        sql.append("            WCSU.TENPO_CD        = RT.TENPO_CD AND ");
        sql.append("            RT.TENPO_KB          = '1' AND ");
        sql.append("            RT.KAITEN_DT        <= ? AND ");
        sql.append("            RT.ZAIMU_END_DT     >= ? AND ");
        sql.append("            RT.DELETE_FG         = '0' ");
        sql.append("        WHERE ");
        sql.append("            WCSU.COMP_CD         = ? AND ");
        sql.append("            WCSU.DATA_SAKUSEI_DT = ? ");
        sql.append("        UNION ALL ");
        sql.append("        SELECT ");
        sql.append("            WCSUC.KEIJO_DT ");
        sql.append("           ,RT2.TENPO_CD RT2_TENPO_CD ");
        sql.append("        FROM WK_CHECKER_SEISAN_URI_C WCSUC ");
        sql.append("        INNER JOIN DT_TEN_SEISAN_STATE DTSS2 ");
        sql.append("        ON  DTSS2.COMP_CD         = WCSUC.COMP_CD AND ");
        sql.append("            DTSS2.KEIJO_DT        = WCSUC.KEIJO_DT AND ");
        sql.append("            DTSS2.TENPO_CD        = WCSUC.TENPO_CD AND ");
        sql.append("            DTSS2.ISAN_SEISAN_STATE_FG <> '2' ");
        sql.append("        LEFT JOIN R_TENPO RT2 ");
        sql.append("        ON  RT2.HOJIN_CD          = ? AND ");
        sql.append("            WCSUC.TENPO_CD        = RT2.TENPO_CD AND ");
        sql.append("            RT2.TENPO_KB          = '1' AND ");
        sql.append("            RT2.KAITEN_DT        <= ? AND ");
        sql.append("            RT2.ZAIMU_END_DT     >= ? AND ");
        sql.append("            RT2.DELETE_FG         = '0' ");
        sql.append("        WHERE ");
        sql.append("            WCSUC.COMP_CD         = ? AND ");
        sql.append("            WCSUC.DATA_SAKUSEI_DT = ? ) ");
        sql.append("    WHERE ");
        sql.append("        RT_TENPO_CD IS NOT NULL ");
        sql.append("    GROUP BY ");
        sql.append("        KEIJO_DT ");
        sql.append("    ) WCS ");
        sql.append("ON (DZS.KEIJO_DT = WCS.KEIJO_DT) ");
        sql.append("WHEN NOT MATCHED THEN ");
        sql.append("    INSERT ( ");
        sql.append("        COMP_CD ");
        sql.append("       ,KEIJO_DT ");
        sql.append("       ,SEISAN_STATE_FG ");
        sql.append("       ,INSERT_USER_ID ");
        sql.append("       ,INSERT_TS ");
        sql.append("       ,UPDATE_USER_ID ");
        sql.append("       ,UPDATE_TS ");
        sql.append("        ) ");
        sql.append("    VALUES ( ");
        sql.append("        ? ");
        sql.append("       ,WCS.KEIJO_DT ");
        sql.append("       ,'1' ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("        ) ");
        // 2017/02/08 VINX J.Endo #3931対応 ADD(E)

        return sql.toString();
    }

    /**
     * 店別チェッカー別精算状況データ追加SQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getInsertTenCheckerSeisanStateSQL(){

        StringBuffer sql = new StringBuffer("");

        // 2017/02/08 VINX J.Endo #3931対応 MOD(S)
        //sql.append("INSERT INTO DT_TEN_CHECKER_SEISAN_STATE ( ");
        //sql.append("    COMP_CD ");
        //sql.append("   ,KEIJO_DT ");
        //sql.append("   ,TENPO_CD ");
        //sql.append("   ,CHECKER_CD ");
        //sql.append("   ,SEISAN_STATE_FG ");
        //sql.append("   ,INSERT_USER_ID ");
        //sql.append("   ,INSERT_TS ");
        //sql.append("   ,UPDATE_USER_ID ");
        //sql.append("   ,UPDATE_TS ");
        //sql.append("    ) ");
        //sql.append("SELECT ");
        //sql.append("    ? ");
        //sql.append("   ,KEIJO_DT ");
        //sql.append("   ,WCSU_TENPO_CD ");
        //sql.append("   ,CHECKER_CD ");
        //sql.append("   ,'1' ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("FROM ( ");
        //sql.append("    SELECT ");
        //sql.append("        WCSU.KEIJO_DT ");
        //sql.append("       ,WCSU.TENPO_CD WCSU_TENPO_CD ");
        //sql.append("       ,WCSU.CHECKER_CD ");
        //sql.append("       ,RT.TENPO_CD RT_TENPO_CD ");
        //sql.append("    FROM WK_CHECKER_SEISAN_URI WCSU ");
        //sql.append("    INNER JOIN DT_TEN_SEISAN_STATE DTSS ");
        //sql.append("    ON  DTSS.COMP_CD         = WCSU.COMP_CD AND ");
        //sql.append("        DTSS.KEIJO_DT        = WCSU.KEIJO_DT AND ");
        //sql.append("        DTSS.TENPO_CD        = WCSU.TENPO_CD AND ");
        //sql.append("        DTSS.ISAN_SEISAN_STATE_FG <> '2' ");
        //sql.append("    LEFT JOIN R_TENPO RT ");
        //sql.append("    ON  RT.HOJIN_CD          = ? AND ");
        //sql.append("        WCSU.TENPO_CD        = RT.TENPO_CD AND ");
        //sql.append("        RT.TENPO_KB          = '1' AND ");
        //sql.append("        RT.KAITEN_DT        <= ? AND ");
        //sql.append("        RT.ZAIMU_END_DT     >= ? AND ");
        //sql.append("        RT.DELETE_FG         = '0' ");
        //sql.append("    WHERE ");
        //sql.append("        WCSU.COMP_CD         = ? AND ");
        //sql.append("        WCSU.DATA_SAKUSEI_DT = ? ");
        //sql.append("    UNION ALL ");
        //sql.append("    SELECT ");
        //sql.append("        WCSUC.KEIJO_DT ");
        //sql.append("       ,WCSUC.TENPO_CD WCSUC_TENPO_CD ");
        //sql.append("       ,WCSUC.CHECKER_CD ");
        //sql.append("       ,RT2.TENPO_CD RT2_TENPO_CD ");
        //sql.append("    FROM WK_CHECKER_SEISAN_URI_C WCSUC ");
        //sql.append("    INNER JOIN DT_TEN_SEISAN_STATE DTSS2 ");
        //sql.append("    ON  DTSS2.COMP_CD         = WCSUC.COMP_CD AND ");
        //sql.append("        DTSS2.KEIJO_DT        = WCSUC.KEIJO_DT AND ");
        //sql.append("        DTSS2.TENPO_CD        = WCSUC.TENPO_CD AND ");
        //sql.append("        DTSS2.ISAN_SEISAN_STATE_FG <> '2' ");
        //sql.append("    LEFT JOIN R_TENPO RT2 ");
        //sql.append("    ON  RT2.HOJIN_CD          = ? AND ");
        //sql.append("        WCSUC.TENPO_CD        = RT2.TENPO_CD AND ");
        //sql.append("        RT2.TENPO_KB          = '1' AND ");
        //sql.append("        RT2.KAITEN_DT        <= ? AND ");
        //sql.append("        RT2.ZAIMU_END_DT     >= ? AND ");
        //sql.append("        RT2.DELETE_FG         = '0' ");
        //sql.append("    WHERE ");
        //sql.append("        WCSUC.COMP_CD         = ? AND ");
        //sql.append("        WCSUC.DATA_SAKUSEI_DT = ? ) ");
        //sql.append("WHERE ");
        //sql.append("    RT_TENPO_CD IS NOT NULL ");
        //sql.append("GROUP BY ");
        //sql.append("    KEIJO_DT ");
        //sql.append("   ,WCSU_TENPO_CD ");
        //sql.append("   ,CHECKER_CD ");
        sql.append("MERGE INTO DT_TEN_CHECKER_SEISAN_STATE DTC ");
        sql.append("USING ( ");
        sql.append("    SELECT ");
        sql.append("        KEIJO_DT ");
        sql.append("       ,WCSU_TENPO_CD ");
        sql.append("       ,CHECKER_CD ");
        sql.append("    FROM ( ");
        sql.append("        SELECT ");
        sql.append("            WCSU.KEIJO_DT ");
        sql.append("           ,WCSU.TENPO_CD WCSU_TENPO_CD ");
        sql.append("           ,WCSU.CHECKER_CD ");
        sql.append("           ,RT.TENPO_CD RT_TENPO_CD ");
        sql.append("        FROM WK_CHECKER_SEISAN_URI WCSU ");
        sql.append("        INNER JOIN DT_TEN_SEISAN_STATE DTSS ");
        sql.append("        ON  DTSS.COMP_CD         = WCSU.COMP_CD AND ");
        sql.append("            DTSS.KEIJO_DT        = WCSU.KEIJO_DT AND ");
        sql.append("            DTSS.TENPO_CD        = WCSU.TENPO_CD AND ");
        sql.append("            DTSS.ISAN_SEISAN_STATE_FG <> '2' ");
        sql.append("        LEFT JOIN R_TENPO RT ");
        sql.append("        ON  RT.HOJIN_CD          = ? AND ");
        sql.append("            WCSU.TENPO_CD        = RT.TENPO_CD AND ");
        sql.append("            RT.TENPO_KB          = '1' AND ");
        sql.append("            RT.KAITEN_DT        <= ? AND ");
        sql.append("            RT.ZAIMU_END_DT     >= ? AND ");
        sql.append("            RT.DELETE_FG         = '0' ");
        sql.append("        WHERE ");
        sql.append("            WCSU.COMP_CD         = ? AND ");
        sql.append("            WCSU.DATA_SAKUSEI_DT = ? ");
        sql.append("        UNION ALL ");
        sql.append("        SELECT ");
        sql.append("            WCSUC.KEIJO_DT ");
        sql.append("           ,WCSUC.TENPO_CD WCSUC_TENPO_CD ");
        sql.append("           ,WCSUC.CHECKER_CD ");
        sql.append("           ,RT2.TENPO_CD RT2_TENPO_CD ");
        sql.append("        FROM WK_CHECKER_SEISAN_URI_C WCSUC ");
        sql.append("        INNER JOIN DT_TEN_SEISAN_STATE DTSS2 ");
        sql.append("        ON  DTSS2.COMP_CD         = WCSUC.COMP_CD AND ");
        sql.append("            DTSS2.KEIJO_DT        = WCSUC.KEIJO_DT AND ");
        sql.append("            DTSS2.TENPO_CD        = WCSUC.TENPO_CD AND ");
        sql.append("            DTSS2.ISAN_SEISAN_STATE_FG <> '2' ");
        sql.append("        LEFT JOIN R_TENPO RT2 ");
        sql.append("        ON  RT2.HOJIN_CD          = ? AND ");
        sql.append("            WCSUC.TENPO_CD        = RT2.TENPO_CD AND ");
        sql.append("            RT2.TENPO_KB          = '1' AND ");
        sql.append("            RT2.KAITEN_DT        <= ? AND ");
        sql.append("            RT2.ZAIMU_END_DT     >= ? AND ");
        sql.append("            RT2.DELETE_FG         = '0' ");
        sql.append("        WHERE ");
        sql.append("            WCSUC.COMP_CD         = ? AND ");
        sql.append("            WCSUC.DATA_SAKUSEI_DT = ? ");
        sql.append("        ) ");
        sql.append("    WHERE ");
        sql.append("        RT_TENPO_CD IS NOT NULL ");
        sql.append("    GROUP BY ");
        sql.append("        KEIJO_DT ");
        sql.append("       ,WCSU_TENPO_CD ");
        sql.append("       ,CHECKER_CD ");
        sql.append("    ) WCS ");
        sql.append("ON (DTC.KEIJO_DT   = WCS.KEIJO_DT AND ");
        sql.append("    DTC.TENPO_CD   = WCS.WCSU_TENPO_CD AND ");
        sql.append("    DTC.CHECKER_CD = WCS.CHECKER_CD) ");
        sql.append("WHEN NOT MATCHED THEN ");
        sql.append("    INSERT ( ");
        sql.append("        COMP_CD ");
        sql.append("       ,KEIJO_DT ");
        sql.append("       ,TENPO_CD ");
        sql.append("       ,CHECKER_CD ");
        sql.append("       ,SEISAN_STATE_FG ");
        sql.append("       ,INSERT_USER_ID ");
        sql.append("       ,INSERT_TS ");
        sql.append("       ,UPDATE_USER_ID ");
        sql.append("       ,UPDATE_TS ");
        sql.append("        ) ");
        sql.append("    VALUES ( ");
        sql.append("        ? ");
        sql.append("       ,WCS.KEIJO_DT ");
        sql.append("       ,WCS.WCSU_TENPO_CD ");
        sql.append("       ,WCS.CHECKER_CD ");
        sql.append("       ,'1' ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("       ,? ");
        sql.append("        ) ");
        // 2017/02/08 VINX J.Endo #3931対応 MOD(E)

        return sql.toString();
    }
    // 2017/01/24 VINX J.Endo #3184対応 ADD(E)


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
            DaoIf dao = new CheckerBetsuUriageSyukeiDao();
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
