package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.common.util.CalcUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル： OfflineKaikeiSeisanUriageSaiDataCreateDao クラス</p>
 * <p>説明　　： 「オフライン会計精算売上ワーク」からデータを抽出し「オフライン会計精算売上差異ワーク」を作成する。
 *                その際、「オフライン会計精算売上累積ワーク」がなければINS、あればUPDする。             
 * </p>
 * <p>著作権　： Copyright (c) 2009</p>
 * <p>会社名　： Vinculum Japan Corp.</p>
 * @author   M.TADA
 * @version 1.00 (2009.12.25) 初版作成
 */
public class OfflineKaikeiSeisanUriageSaiDataCreateDao implements DaoIf {

    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ名称
    private static final String BATCH_NAME = "ｵﾌﾗｲﾝ会計精算売上差異ﾃﾞｰﾀ作成処理";

    /**
     * メイン処理
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserId = invoker.getUserId() + " " + BATCH_NAME;

        // 日計ネット
        String strDayNetVl = "0";
        // 計1金額
        String strKei1Vl = "0";
        // 計1回数
        String strKei1CntQt = "0";
        // 計2金額
        String strKei2Vl = "0";
        // 計2回数
        String strKei2CntQt = "0";
        // 計3金額
        String strKei3Vl = "0";
        // 計3回数
        String strKei3CntQt = "0";
        // 計4金額
        String strKei4Vl = "0";
        // 計4回数
        String strKei4CntQt = "0";
        // 計5金額
        String strKei5Vl = "0";
        // 計5回数
        String strKei5CntQt = "0";
        // 計6金額
        String strKei6Vl = "0";
        // 計6回数
        String strKei6CntQt = "0";
        // 計7金額
        String strKei7Vl = "0";
        // 計7回数
        String strKei7CntQt = "0";
        // 計8金額
        String strKei8Vl = "0";
        // 計8回数
        String strKei8CntQt = "0";
        // 計9金額
        String strKei9Vl = "0";
        // 計9回数
        String strKei9CntQt = "0";
        // 計10金額
        String strKei10Vl = "0";
        // 計10回数
        String strKei10CntQt = "0";
        // 外税対象金額
        String strSotoZeiTaiVl = "0";
        // 内税対象金額
        String strUchiZeiTaiVl = "0";
        // 非課税対象金額
        String strHikaZeiTaiVl = "0";
        // 外税金額
        String strSotoZeiVl = "0";
        // 内税金額
        String strUchiZeiVl = "0";
        // 点数
        String strTensuQt ="0";
        // 客数
        String strKyakuQt ="0";

        PreparedStatementEx ps = null;
        PreparedStatementEx psRuiSel = null;
        PreparedStatementEx psRuiIns = null;
        PreparedStatementEx psRuiUpd = null;
        PreparedStatementEx psSaiIns = null;
        ResultSet rs = null;
        ResultSet rsRui = null;
        int i = 0;
        int iRS = 0;
        int iR = 0;
        int iS = 0;
        int cntRuiUpd = 0;
        int cntRuiUpdSum = 0;
        int cntRuiIns = 0;
        int cntRuiInsSum = 0;
        int cntSaiIns = 0;
        int cntSaiInsSum = 0;
        
        // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ抽出処理
        psRuiSel = invoker.getDataBase().prepareStatement("selectWkOffKaiSeiUriRui");
        // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ追加処理
        psRuiIns = invoker.getDataBase().prepareStatement("insertWkOffKaiSeiUriRui");
        // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ更新処理
        psRuiUpd = invoker.getDataBase().prepareStatement("updateWkOffKaiSeiUriRui");
        // ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ追加処理
        psSaiIns = invoker.getDataBase().prepareStatement("insertWkOffKaiSeiUriSai");
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上差異データ作成処理を開始します。");
        
        // ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ削除処理
        deleteKaiSeiUriSai(invoker, strUserId);
        
        // ｵﾌﾗｲﾝ会計精算売上ﾜｰｸ抽出処理
        ps = invoker.getDataBase().prepareStatement("selectWkOffKaiSeiUri");
        
        ps.setString(++i, COMP_CD);

        // ｵﾌﾗｲﾝ会計精算売上ﾜｰｸ抽出処理 実行
        rs = ps.executeQuery();
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上累積更新処理を開始します。");
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上差異追加処理を開始します。");

        while (rs.next()) {
            
            // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ抽出処理
            iRS = 0;
            psRuiSel.setString(++iRS, rs.getString("COMP_CD"));
            psRuiSel.setString(++iRS, rs.getString("TENPO_CD"));
            psRuiSel.setString(++iRS, rs.getString("KEIJO_DT"));
            psRuiSel.setString(++iRS, rs.getString("POS_NB"));
            
            // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ抽出処理 実行
            rsRui = psRuiSel.executeQuery();
                
            if (rsRui.next()) {

                // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸがある場合
                // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ更新処理
                iR = 0;
                psRuiUpd.setString(++iR, rs.getString("ENTRY_DT"));
                psRuiUpd.setString(++iR, rs.getString("DAY_NET_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_1_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_1_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_2_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_2_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_3_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_3_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_4_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_4_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_5_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_5_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_6_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_6_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_7_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_7_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_8_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_8_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_9_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_9_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("KEI_10_VL"));
                psRuiUpd.setString(++iR, rs.getString("KEI_10_COUNT_QT"));
                psRuiUpd.setString(++iR, rs.getString("SOTOZEI_TAISYO_VL"));
                psRuiUpd.setString(++iR, rs.getString("UCHIZEI_TAISYO_VL"));
                psRuiUpd.setString(++iR, rs.getString("HIKAZEI_TAISYO_VL"));
                psRuiUpd.setString(++iR, rs.getString("SOTOZEI_VL"));
                psRuiUpd.setString(++iR, rs.getString("UCHIZEI_VL"));
                psRuiUpd.setString(++iR, rs.getString("TENSU_QT"));
                psRuiUpd.setString(++iR, rs.getString("KYAKU_QT"));
                psRuiUpd.setString(++iR, rs.getString("COMP_CD"));
                psRuiUpd.setString(++iR, rs.getString("TENPO_CD"));
                psRuiUpd.setString(++iR, rs.getString("KEIJO_DT"));
                psRuiUpd.setString(++iR, rs.getString("POS_NB"));
                
                // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ更新処理 実行
                cntRuiUpd = psRuiUpd.executeUpdate();
                cntRuiUpdSum = cntRuiUpdSum + cntRuiUpd; 
                
                // ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ追加のために変数セット
                // ｵﾌﾗｲﾝ会計精算売上ﾜｰｸ.金額-ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ.金額
                strDayNetVl = CalcUtility.minus(rs.getString("DAY_NET_VL"), rsRui.getString("DAY_NET_VL"));
                strKei1Vl = CalcUtility.minus(rs.getString("KEI_1_VL"), rsRui.getString("KEI_1_VL"));
                strKei1CntQt = CalcUtility.minus(rs.getString("KEI_1_COUNT_QT"), rsRui.getString("KEI_1_COUNT_QT"));
                strKei2Vl = CalcUtility.minus(rs.getString("KEI_2_VL"), rsRui.getString("KEI_2_VL"));
                strKei2CntQt = CalcUtility.minus(rs.getString("KEI_2_COUNT_QT"), rsRui.getString("KEI_2_COUNT_QT"));
                strKei3Vl = CalcUtility.minus(rs.getString("KEI_3_VL"), rsRui.getString("KEI_3_VL"));
                strKei3CntQt = CalcUtility.minus(rs.getString("KEI_3_COUNT_QT"), rsRui.getString("KEI_3_COUNT_QT"));
                strKei4Vl = CalcUtility.minus(rs.getString("KEI_4_VL"), rsRui.getString("KEI_4_VL"));
                strKei4CntQt = CalcUtility.minus(rs.getString("KEI_4_COUNT_QT"), rsRui.getString("KEI_4_COUNT_QT"));
                strKei5Vl = CalcUtility.minus(rs.getString("KEI_5_VL"), rsRui.getString("KEI_5_VL"));
                strKei5CntQt = CalcUtility.minus(rs.getString("KEI_5_COUNT_QT"), rsRui.getString("KEI_5_COUNT_QT"));
                strKei6Vl = CalcUtility.minus(rs.getString("KEI_6_VL"), rsRui.getString("KEI_6_VL"));
                strKei6CntQt = CalcUtility.minus(rs.getString("KEI_6_COUNT_QT"), rsRui.getString("KEI_6_COUNT_QT"));
                strKei7Vl = CalcUtility.minus(rs.getString("KEI_7_VL"), rsRui.getString("KEI_7_VL"));
                strKei7CntQt = CalcUtility.minus(rs.getString("KEI_7_COUNT_QT"), rsRui.getString("KEI_7_COUNT_QT"));
                strKei8Vl = CalcUtility.minus(rs.getString("KEI_8_VL"), rsRui.getString("KEI_8_VL"));
                strKei8CntQt = CalcUtility.minus(rs.getString("KEI_8_COUNT_QT"), rsRui.getString("KEI_8_COUNT_QT"));
                strKei9Vl = CalcUtility.minus(rs.getString("KEI_9_VL"), rsRui.getString("KEI_9_VL"));
                strKei9CntQt = CalcUtility.minus(rs.getString("KEI_9_COUNT_QT"), rsRui.getString("KEI_9_COUNT_QT"));
                strKei10Vl = CalcUtility.minus(rs.getString("KEI_10_VL"), rsRui.getString("KEI_10_VL"));
                strKei10CntQt = CalcUtility.minus(rs.getString("KEI_10_COUNT_QT"), rsRui.getString("KEI_10_COUNT_QT"));
                strSotoZeiTaiVl = CalcUtility.minus(rs.getString("SOTOZEI_TAISYO_VL"), rsRui.getString("SOTOZEI_TAISYO_VL"));
                strUchiZeiTaiVl = CalcUtility.minus(rs.getString("UCHIZEI_TAISYO_VL"), rsRui.getString("UCHIZEI_TAISYO_VL"));
                strHikaZeiTaiVl = CalcUtility.minus(rs.getString("HIKAZEI_TAISYO_VL"), rsRui.getString("HIKAZEI_TAISYO_VL"));
                strSotoZeiVl = CalcUtility.minus(rs.getString("SOTOZEI_VL"), rsRui.getString("SOTOZEI_VL"));
                strUchiZeiVl = CalcUtility.minus(rs.getString("UCHIZEI_VL"), rsRui.getString("UCHIZEI_VL"));
                strTensuQt = CalcUtility.minus(rs.getString("TENSU_QT"), rsRui.getString("TENSU_QT"));
                strKyakuQt = CalcUtility.minus(rs.getString("KYAKU_QT"), rsRui.getString("KYAKU_QT"));
                
            } else {

                // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸがない場合
                // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ追加処理
                iR = 0;
                psRuiIns.setString(++iR, rs.getString("COMP_CD"));
                psRuiIns.setString(++iR, rs.getString("TENPO_CD"));
                psRuiIns.setString(++iR, rs.getString("ENTRY_DT"));
                psRuiIns.setString(++iR, rs.getString("KEIJO_DT"));
                psRuiIns.setString(++iR, rs.getString("POS_NB"));
                psRuiIns.setString(++iR, rs.getString("DAY_NET_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_1_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_1_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_2_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_2_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_3_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_3_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_4_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_4_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_5_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_5_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_6_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_6_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_7_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_7_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_8_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_8_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_9_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_9_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("KEI_10_VL"));
                psRuiIns.setString(++iR, rs.getString("KEI_10_COUNT_QT"));
                psRuiIns.setString(++iR, rs.getString("SOTOZEI_TAISYO_VL"));
                psRuiIns.setString(++iR, rs.getString("UCHIZEI_TAISYO_VL"));
                psRuiIns.setString(++iR, rs.getString("HIKAZEI_TAISYO_VL"));
                psRuiIns.setString(++iR, rs.getString("SOTOZEI_VL"));
                psRuiIns.setString(++iR, rs.getString("UCHIZEI_VL"));
                psRuiIns.setString(++iR, rs.getString("TENSU_QT"));
                psRuiIns.setString(++iR, rs.getString("KYAKU_QT"));
                
                // ｵﾌﾗｲﾝ会計精算売上累積ﾜｰｸ追加処理 実行
                cntRuiIns = psRuiIns.executeUpdate();
                cntRuiInsSum = cntRuiInsSum + cntRuiIns; 
                
                // ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ追加のために変数セット
                // ｵﾌﾗｲﾝ会計精算売上ﾜｰｸ.金額
                strDayNetVl = rs.getString("DAY_NET_VL");
                strKei1Vl = rs.getString("KEI_1_VL");
                strKei1CntQt = rs.getString("KEI_1_COUNT_QT");
                strKei2Vl = rs.getString("KEI_2_VL");
                strKei2CntQt = rs.getString("KEI_2_COUNT_QT");
                strKei3Vl = rs.getString("KEI_3_VL");
                strKei3CntQt = rs.getString("KEI_3_COUNT_QT");
                strKei4Vl = rs.getString("KEI_4_VL");
                strKei4CntQt = rs.getString("KEI_4_COUNT_QT");
                strKei5Vl = rs.getString("KEI_5_VL");
                strKei5CntQt = rs.getString("KEI_5_COUNT_QT");
                strKei6Vl = rs.getString("KEI_6_VL");
                strKei6CntQt = rs.getString("KEI_6_COUNT_QT");
                strKei7Vl = rs.getString("KEI_7_VL");
                strKei7CntQt = rs.getString("KEI_7_COUNT_QT");
                strKei8Vl = rs.getString("KEI_8_VL");
                strKei8CntQt = rs.getString("KEI_8_COUNT_QT");
                strKei9Vl = rs.getString("KEI_9_VL");
                strKei9CntQt = rs.getString("KEI_9_COUNT_QT");
                strKei10Vl = rs.getString("KEI_10_VL");
                strKei10CntQt = rs.getString("KEI_10_COUNT_QT");
                strSotoZeiTaiVl = rs.getString("SOTOZEI_TAISYO_VL");
                strUchiZeiTaiVl = rs.getString("UCHIZEI_TAISYO_VL");
                strHikaZeiTaiVl = rs.getString("HIKAZEI_TAISYO_VL");
                strSotoZeiVl = rs.getString("SOTOZEI_VL");
                strUchiZeiVl = rs.getString("UCHIZEI_VL");
                strTensuQt = rs.getString("TENSU_QT");
                strKyakuQt = rs.getString("KYAKU_QT");
                
            }
            
            // ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ追加処理
            iS = 0;
            psSaiIns.setString(++iS, rs.getString("COMP_CD"));
            psSaiIns.setString(++iS, rs.getString("TENPO_CD"));
            psSaiIns.setString(++iS, rs.getString("ENTRY_DT"));
            psSaiIns.setString(++iS, rs.getString("KEIJO_DT"));
            psSaiIns.setString(++iS, rs.getString("POS_NB"));
            psSaiIns.setString(++iS, strDayNetVl);
            psSaiIns.setString(++iS, strKei1Vl);
            psSaiIns.setString(++iS, strKei1CntQt);
            psSaiIns.setString(++iS, strKei2Vl);
            psSaiIns.setString(++iS, strKei2CntQt);
            psSaiIns.setString(++iS, strKei3Vl);
            psSaiIns.setString(++iS, strKei3CntQt);
            psSaiIns.setString(++iS, strKei4Vl);
            psSaiIns.setString(++iS, strKei4CntQt);
            psSaiIns.setString(++iS, strKei5Vl);
            psSaiIns.setString(++iS, strKei5CntQt);
            psSaiIns.setString(++iS, strKei6Vl);
            psSaiIns.setString(++iS, strKei6CntQt);
            psSaiIns.setString(++iS, strKei7Vl);
            psSaiIns.setString(++iS, strKei7CntQt);
            psSaiIns.setString(++iS, strKei8Vl);
            psSaiIns.setString(++iS, strKei8CntQt);
            psSaiIns.setString(++iS, strKei9Vl);
            psSaiIns.setString(++iS, strKei9CntQt);
            psSaiIns.setString(++iS, strKei10Vl);
            psSaiIns.setString(++iS, strKei10CntQt);
            psSaiIns.setString(++iS, strSotoZeiTaiVl);
            psSaiIns.setString(++iS, strUchiZeiTaiVl);
            psSaiIns.setString(++iS, strHikaZeiTaiVl);
            psSaiIns.setString(++iS, strSotoZeiVl);
            psSaiIns.setString(++iS, strUchiZeiVl);
            psSaiIns.setString(++iS, strTensuQt);
            psSaiIns.setString(++iS, strKyakuQt);
            
            // ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ追加処理 実行
            cntSaiIns = psSaiIns.executeUpdate();
            cntSaiInsSum = cntSaiInsSum + cntSaiIns;
            
        }
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + cntRuiInsSum + "件のオフライン会計精算売上累積データを追加しました。");
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上累積データ追加処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + cntRuiUpdSum + "件のオフライン会計精算売上累積データを更新しました。");
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上累積データ更新処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + cntSaiInsSum + "件のオフライン会計精算売上差異データを追加しました。");
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上差異データ追加処理を終了します。");
        // 終了ログを出力する。
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上差異データ作成処理を終了します。");
        
    }

    /**
     * ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ削除処理
     */
    private void deleteKaiSeiUriSai(DaoInvokerIf invoker , String strUserId) throws Exception {

        int count = 0;
        int i = 0;
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ削除処理を開始します。");

        // ｵﾌﾗｲﾝ会計精算売上差異ﾃﾞｰﾀ削除
        PreparedStatementEx psDelSai = invoker.getDataBase().prepareStatement("deleteWkOffKaiSeiUriSai");

        // パラメタ設定
        psDelSai.setString(++i, COMP_CD);
        
        // 実行
        count = psDelSai.executeUpdate();
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + count + "件のｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸを削除しました。");
        invoker.infoLog(strUserId + "　：　ｵﾌﾗｲﾝ会計精算売上差異ﾜｰｸ削除処理を終了します。");

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
            DaoIf dao = new OfflineKaikeiSeisanUriageSaiDataCreateDao();
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
