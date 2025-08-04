package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;
import java.util.Map;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoUtils;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <P>タイトル: OutsideDayTenDPTSeisanDataCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author S.Hamaguchi
 *  @version 1.0 2014/12/11 Sou グローバル対応 通貨対応
 */
public class OutsideDayTenDPTSeisanDataCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "勤怠IF用累計データ作成処理";
    /** バッチID */
    private static final String BATCH_ID = "URIB131040";
    /** DB日付取得 */
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** バッチ日付 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 当日差分 */
    private static final String TODAY_SABUN = "0";
    /** 更新差分 */
    private static final String UPDATE_SABUN = "1";

    /** 当日差分用時間帯NO */
    private static final String TIME_23 = "23";

    /**
     *
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        int intDeleteCount = 0;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExTimeNo = null;
        PreparedStatementEx preparedStatementIns      = null;
        PreparedStatementEx preparedStatementDel      = null;
        PreparedStatementEx preparedStatementTodayEx  = null;
        PreparedStatementEx preparedStatementUpdateEx = null;

        preparedStatementTodayEx  = invoker.getDataBase().prepareStatement(getSelKintaiIFRuikeiTodaySql());
        preparedStatementUpdateEx = invoker.getDataBase().prepareStatement(getSelKintaiIFRuikeiKousinSql());
        preparedStatementExTimeNo = invoker.getDataBase().prepareStatement(getSelKintaiIFRuikeiTimeNoSql());

        preparedStatementIns  = invoker.getDataBase().prepareStatement(insSelKintaiIFRuisekiSql());

        // ログ出力
        invoker.infoLog(strUserID + "　：　勤怠Ｓ：勤怠IF用累計データ作成処理を開始します。");
        invoker.infoLog(strUserID + "　：　勤怠IF用累積データ:勤怠IF用累積データ削除処理を開始します。");

        // 勤怠IF用累積データ削除処理
        preparedStatementDel = invoker.getDataBase().prepareStatement("deleteDtKintaiIfRuiseki");
        preparedStatementDel.setString(1, FiResorceUtility.getSubSystemId());
        preparedStatementDel.setString(2, UriResorceKeyConstant.KINTAI_IF_MONTH);
        preparedStatementDel.setString(3, UriageCommonConstants.HONJIME_KB_MISYORI);
        preparedStatementDel.setString(4, COMP_CD);
        // 実行
        intDeleteCount = preparedStatementDel.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intDeleteCount + "件の勤怠IF用累積データを削除しました。");
        invoker.infoLog(strUserID + "　：　勤怠IF用累積データ:勤怠IF用累積データ削除処理を終了します。");

        // 当日差分
        insKintaiIFRuiseki(invoker, TODAY_SABUN, preparedStatementTodayEx, preparedStatementExTimeNo, preparedStatementIns);

        // 更新差分
        insKintaiIFRuiseki(invoker, UPDATE_SABUN, preparedStatementUpdateEx, preparedStatementExTimeNo, preparedStatementIns);

        if (preparedStatementTodayEx != null) {
            preparedStatementTodayEx.close();
        }
        if (preparedStatementUpdateEx != null) {
            preparedStatementUpdateEx.close();
        }
        if (preparedStatementExTimeNo != null) {
            preparedStatementExTimeNo.close();
        }
        if (preparedStatementIns != null) {
            preparedStatementIns.close();
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　勤怠Ｓ：勤怠IF用累計データ作成処理を終了します。");

    }

    /**
     *
     * @param DaoInvokerIf invoker
     * @param String sabun
     */
    public void insKintaiIFRuiseki(DaoInvokerIf invoker, String sabun, PreparedStatementEx preparedStatementEx,
            PreparedStatementEx preparedStatementExTimeNo, PreparedStatementEx preparedStatementIns) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        int intInsKintaiIFRuisekiCount  = 0;
        int intInsKintaiIFRuisekiCount2  = 0;
        int IDX = 1;

        // 2014/12/11 Sou グローバル対応 通貨対応 Start
        //int intDiffUriageNukiVl = 0;
        double doubleDiffUriageNukiVl = 0;
        // 2014/12/11 Sou グローバル対応 通貨対応 End
        int intDiffUriageQt     = 0;
        int intDiffKyakuQt      = 0;

        // 2014/12/11 Sou グローバル対応 通貨対応 Start
        //int intInsDiffUriageNukiVl = 0;
        double doubleInsDiffUriageNukiVl = 0;
        // 2014/12/11 Sou グローバル対応 通貨対応 End
        int intInsDiffUriageQt     = 0;
        int intInsDiffKyakuQt      = 0;

        String strInsDiffUriageNukiVl = "";
        String strInsDiffUriageQt     = "";
        String strInsDiffKyakuQt      = "";

        ResultSet rsSelectFileOutData  = null;
        ResultSet rsSelectFileOutDataTimeNo = null;

        if(TODAY_SABUN.equals(sabun)){

            // 当日差分
            invoker.infoLog(strUserID + "　：　勤怠IF用累積データ:勤怠IF用累積データ挿入処理(当日差分)を開始します。");

            // SQLを取得し、パラメータを条件にバインドする
            IDX = 1;
            preparedStatementEx.setString(IDX++, COMP_CD);
            preparedStatementEx.setString(IDX++, BATCH_DT);
            preparedStatementEx.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_MI);
            preparedStatementEx.setString(IDX++, COMP_CD);
            preparedStatementEx.setString(IDX++, BATCH_DT);

        }else{
            // 更新差分
            invoker.infoLog(strUserID + "　：　勤怠IF用累積データ:勤怠IF用累積データ挿入処理(更新差分)を開始します。");

            // SQLを取得し、パラメータを条件にバインドする
            IDX = 1;
            preparedStatementEx.setString(IDX++, COMP_CD);
            preparedStatementEx.setString(IDX++, BATCH_DT);
            preparedStatementEx.setString(IDX++, COMP_CD);
            preparedStatementEx.setString(IDX++, UriageCommonConstants.HONJIME_KB_SYORI);
            preparedStatementEx.setString(IDX++, COMP_CD);
            preparedStatementEx.setString(IDX++, BATCH_DT);
            preparedStatementEx.setString(IDX++, COMP_CD);
            preparedStatementEx.setString(IDX++, UriageCommonConstants.HONJIME_KB_SYORI);
            preparedStatementEx.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_ZUMI);
            preparedStatementEx.setString(IDX++, COMP_CD);
        }

        // SQLを実行し、データ取得
        rsSelectFileOutData = preparedStatementEx.executeQuery();

        while (rsSelectFileOutData.next()) {

            Map mapFileOutData = DaoUtils.getMapFromRs(rsSelectFileOutData);

// 2014.04.22 Y.Tominaga [シス0200] 当日差分データ作成不備 (S)
            // 勤怠IF登録ﾌﾗｸﾞ
            boolean kintaiFg = false;
// 2014.04.22 Y.Tominaga [シス0200] 当日差分データ作成不備 (E)

            // 売上金額、売上数、客数のいずれかが不一致の場合
            // 2014/12/11 Sou グローバル対応 通貨対応 Start
            //if(Integer.parseInt(mapFileOutData.get("DIFF_URIAGE_NUKI_VL").toString()) != 0  ||
            if(Double.parseDouble(mapFileOutData.get("DIFF_URIAGE_NUKI_VL").toString()) != 0  ||
            // 2014/12/11 Sou グローバル対応 通貨対応 End
               Integer.parseInt(mapFileOutData.get("DIFF_URIAGE_QT").toString())      != 0  ||
               Integer.parseInt(mapFileOutData.get("DIFF_KYAKU_QT").toString())       != 0      ){

                // 各項目の差分を取得
                // 2014/12/11 Sou グローバル対応 通貨対応 Start
                //intDiffUriageNukiVl = Integer.parseInt(mapFileOutData.get("DIFF_URIAGE_NUKI_VL").toString());
                doubleDiffUriageNukiVl = Double.parseDouble(mapFileOutData.get("DIFF_URIAGE_NUKI_VL").toString());
                // 2014/12/11 Sou グローバル対応 通貨対応 End
                intDiffUriageQt     = Integer.parseInt(mapFileOutData.get("DIFF_URIAGE_QT").toString());
                intDiffKyakuQt      = Integer.parseInt(mapFileOutData.get("DIFF_KYAKU_QT").toString());

                // SQLを取得し、パラメータを条件にバインドする
                IDX = 1;

                preparedStatementExTimeNo.setString(IDX++, mapFileOutData.get("COMP_CD").toString());
                preparedStatementExTimeNo.setString(IDX++, mapFileOutData.get("KEIJO_DT").toString());
                preparedStatementExTimeNo.setString(IDX++, mapFileOutData.get("TENPO_CD").toString());
                preparedStatementExTimeNo.setString(IDX++, mapFileOutData.get("BUNRUI1_CD").toString());

                // SQLを実行し、データ取得
                rsSelectFileOutDataTimeNo = preparedStatementExTimeNo.executeQuery();

                while (rsSelectFileOutDataTimeNo.next()) {

// 2014.04.22 Y.Tominaga [シス0200] 当日差分データ作成不備 (S)
                    // 勤怠IF登録ﾌﾗｸﾞを真にする
                    kintaiFg = true;
// 2014.04.22 Y.Tominaga [シス0200] 当日差分データ作成不備 (E)

                    Map mapFileOutDataTimeNo = DaoUtils.getMapFromRs(rsSelectFileOutDataTimeNo);

                    strInsDiffUriageNukiVl = "";
                    strInsDiffUriageQt     = "";
                    strInsDiffKyakuQt      = "";

                    // 2014/12/11 Sou グローバル対応 通貨対応 Start
                    //intInsDiffUriageNukiVl = intDiffUriageNukiVl + Integer.parseInt(mapFileOutDataTimeNo.get("URIAGE_NUKI_VL").toString());
                    doubleInsDiffUriageNukiVl = doubleDiffUriageNukiVl + Double.parseDouble(mapFileOutDataTimeNo.get("URIAGE_NUKI_VL").toString());
                    // 2014/12/11 Sou グローバル対応 通貨対応 End
                    intInsDiffUriageQt     = intDiffUriageQt     + Integer.parseInt(mapFileOutDataTimeNo.get("URIAGE_QT").toString());
                    intInsDiffKyakuQt      = intDiffKyakuQt      + Integer.parseInt(mapFileOutDataTimeNo.get("KYAKU_QT").toString());

                    // 2014/12/11 Sou グローバル対応 通貨対応 Start
                    // 時間帯ごとの売上金額と残差額の比較
//                    if(intDiffUriageNukiVl != 0){
//                        if(intInsDiffUriageNukiVl < 0){
//                            intInsDiffUriageNukiVl = Integer.parseInt(mapFileOutDataTimeNo.get("URIAGE_NUKI_VL").toString()) * -1;
//                            intDiffUriageNukiVl    = intDiffUriageNukiVl + Integer.parseInt(mapFileOutDataTimeNo.get("URIAGE_NUKI_VL").toString());
//                        }else{
//                            intInsDiffUriageNukiVl = intDiffUriageNukiVl;
//                            intDiffUriageNukiVl    = 0;
//                        }
//                    }else{
//                        intInsDiffUriageNukiVl = 0;
//                    }
                    if(doubleDiffUriageNukiVl != 0){
                        if(doubleInsDiffUriageNukiVl < 0){
                            doubleInsDiffUriageNukiVl = Double.parseDouble(mapFileOutDataTimeNo.get("URIAGE_NUKI_VL").toString()) * -1;
                            doubleDiffUriageNukiVl    = doubleDiffUriageNukiVl + Double.parseDouble(mapFileOutDataTimeNo.get("URIAGE_NUKI_VL").toString());
                        }else{
                            doubleInsDiffUriageNukiVl = doubleDiffUriageNukiVl;
                            doubleDiffUriageNukiVl    = 0;
                        }
                    }else{
                        doubleInsDiffUriageNukiVl = 0;
                    }
                    // 2014/12/11 Sou グローバル対応 通貨対応 End
                    
                    // 時間帯ごとの売上数と残差額の比較
                    if(intDiffUriageQt != 0){
                        if(intInsDiffUriageQt < 0){
                            intInsDiffUriageQt = Integer.parseInt(mapFileOutDataTimeNo.get("URIAGE_QT").toString()) * -1;
                            intDiffUriageQt    = intDiffUriageQt + Integer.parseInt(mapFileOutDataTimeNo.get("URIAGE_QT").toString());
                        }else{
                            intInsDiffUriageQt = intDiffUriageQt;
                            intDiffUriageQt    = 0;
                        }
                    }else{
                        intInsDiffUriageQt = 0;
                    }

                    // 時間帯ごとの客数と残差額の比較
                    if(intDiffKyakuQt != 0){
                        if(intInsDiffKyakuQt < 0){
                            intInsDiffKyakuQt = Integer.parseInt(mapFileOutDataTimeNo.get("KYAKU_QT").toString()) * -1;
                            intDiffKyakuQt    = intDiffKyakuQt + Integer.parseInt(mapFileOutDataTimeNo.get("KYAKU_QT").toString());
                        }else{
                            intInsDiffKyakuQt = intDiffKyakuQt;
                            intDiffKyakuQt    = 0;
                        }
                    }else{
                        intInsDiffKyakuQt = 0;
                    }

                    // 2014/12/11 Sou グローバル対応 通貨対応 Start
                    //strInsDiffUriageNukiVl = String.valueOf(intInsDiffUriageNukiVl);
                    strInsDiffUriageNukiVl = String.valueOf(doubleInsDiffUriageNukiVl);
                    // 2014/12/11 Sou グローバル対応 通貨対応 End
                    strInsDiffUriageQt     = String.valueOf(intInsDiffUriageQt);
                    strInsDiffKyakuQt      = String.valueOf(intInsDiffKyakuQt);

                    // 勤怠IF用累積データ(更新差分)挿入処理
                    // SQLを取得し、パラメータを条件にバインドする
                    IDX = 1;
                    preparedStatementIns.setString(IDX++, mapFileOutDataTimeNo.get("COMP_CD").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutDataTimeNo.get("KEIJO_DT").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutDataTimeNo.get("TENPO_CD").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutDataTimeNo.get("BUNRUI1_CD").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutDataTimeNo.get("TIME_NO").toString());
                    preparedStatementIns.setString(IDX++, strInsDiffUriageNukiVl);
                    preparedStatementIns.setString(IDX++, strInsDiffUriageQt);
                    preparedStatementIns.setString(IDX++, strInsDiffKyakuQt);
                    preparedStatementIns.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_MI);
                    preparedStatementIns.setString(IDX++, BATCH_ID);
                    preparedStatementIns.setString(IDX++, DBSERVER_DT);
                    preparedStatementIns.setString(IDX++, BATCH_ID);
                    preparedStatementIns.setString(IDX++, DBSERVER_DT);

                    // 実行
                    intInsKintaiIFRuisekiCount2 = preparedStatementIns.executeUpdate();

                    // 出力件数加算
                    intInsKintaiIFRuisekiCount = intInsKintaiIFRuisekiCount + intInsKintaiIFRuisekiCount2;

                    // 差分がなくなった時点で終了
                    // 2014/12/11 Sou グローバル対応 通貨対応 Start
                    //if(intDiffUriageNukiVl == 0  &&  intDiffUriageQt == 0  &&  intDiffKyakuQt == 0){
                    if(doubleDiffUriageNukiVl == 0  &&  intDiffUriageQt == 0  &&  intDiffKyakuQt == 0){
                    // 2014/12/11 Sou グローバル対応 通貨対応 End
                    	break;
                    }
                }
                if (rsSelectFileOutDataTimeNo != null) {
                    rsSelectFileOutDataTimeNo.close();
                }

// 2014.04.22 Y.Tominaga [シス0200] 当日差分データ作成不備 (S)
                // 当日差分の時かつ、勤怠IFﾌﾗｸﾞが偽の時は、勤怠IF累積に登録する。
                if(TODAY_SABUN.equals(sabun) && !kintaiFg){

                    IDX = 1;
                    preparedStatementIns.setString(IDX++, mapFileOutData.get("COMP_CD").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutData.get("KEIJO_DT").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutData.get("TENPO_CD").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutData.get("BUNRUI1_CD").toString());
                    preparedStatementIns.setString(IDX++, TIME_23);
                    preparedStatementIns.setString(IDX++, mapFileOutData.get("DIFF_URIAGE_NUKI_VL").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutData.get("DIFF_URIAGE_QT").toString());
                    preparedStatementIns.setString(IDX++, mapFileOutData.get("DIFF_KYAKU_QT").toString());
                    preparedStatementIns.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_MI);
                    preparedStatementIns.setString(IDX++, BATCH_ID);
                    preparedStatementIns.setString(IDX++, DBSERVER_DT);
                    preparedStatementIns.setString(IDX++, BATCH_ID);
                    preparedStatementIns.setString(IDX++, DBSERVER_DT);

                    // 実行
                    intInsKintaiIFRuisekiCount2 = preparedStatementIns.executeUpdate();

                    // 出力件数加算
                    intInsKintaiIFRuisekiCount = intInsKintaiIFRuisekiCount + intInsKintaiIFRuisekiCount2;
                }
// 2014.04.22 Y.Tominaga [シス0200] 当日差分データ作成不備 (E)
            }
        }

        if(TODAY_SABUN.equals(sabun)){

            // 当日差分
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + intInsKintaiIFRuisekiCount + "件の勤怠IF用累積データ(当日差分)を挿入しました。");
            invoker.infoLog(strUserID + "　：　勤怠IF用累積データ:勤怠IF用累積データ挿入処理(当日差分)を終了します。");

        }else{
            // 更新差分
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + intInsKintaiIFRuisekiCount + "件の勤怠IF用累積データ(更新差分)を挿入しました。");
            invoker.infoLog(strUserID + "　：　勤怠IF用累積データ:勤怠IF用累積データ挿入処理(更新差分)を終了します。");

        }

    }

    private String getSelKintaiIFRuikeiTodaySql() {
        StringBuffer sb = new StringBuffer("");

        // 勤怠IF用累積データ（当日差分）抽出用SelectSQL構築
        sb.append(" SELECT ");
        sb.append("       DBS.COMP_CD ");
        sb.append("     , DBS.KEIJO_DT ");
        sb.append("     , DBS.TENPO_CD ");
        sb.append("     , DBS.BUNRUI1_CD ");
        sb.append("     , NVL(DBS.URIAGE_SOURI_VL, 0) - NVL(SUB_DKIR.URIAGE_NUKI_VL, 0)  DIFF_URIAGE_NUKI_VL ");
        sb.append("     , NVL(DBS.URIAGE_SOURI_QT, 0) - NVL(SUB_DKIR.URIAGE_QT, 0)       DIFF_URIAGE_QT ");
        sb.append("     , NVL(DBS.KYAKU_QT, 0)        - NVL(SUB_DKIR.KYAKU_QT, 0)        DIFF_KYAKU_QT ");
        sb.append(" FROM ");
        sb.append("     DT_BUMON_SEISAN  DBS ");
        sb.append(" LEFT JOIN ");
        sb.append(" ( ");
        sb.append("     SELECT ");
        sb.append("           COMP_CD ");
        sb.append("         , KEIJO_DT ");
        sb.append("         , TENPO_CD ");
        sb.append("         , BUNRUI1_CD ");
        sb.append("         , SUM(URIAGE_NUKI_VL) URIAGE_NUKI_VL ");
        sb.append("         , SUM(URIAGE_QT)      URIAGE_QT ");
        sb.append("         , SUM(KYAKU_QT)       KYAKU_QT  ");
        sb.append("     FROM ");
        sb.append("         DT_KINTAI_IF_RUISEKI ");
        sb.append("     WHERE ");
        sb.append("         COMP_CD        = ? AND ");
        sb.append("         KEIJO_DT       = ? AND ");
        sb.append("         DATA_RENKEI_FG = ? ");
        sb.append("     GROUP BY ");
        sb.append("           COMP_CD ");
        sb.append("         , KEIJO_DT ");
        sb.append("         , TENPO_CD ");
        sb.append("         , BUNRUI1_CD ");
        sb.append(" )  SUB_DKIR ");
        sb.append(" ON ");
        sb.append("     SUB_DKIR.COMP_CD    = DBS.COMP_CD    AND ");
        sb.append("     SUB_DKIR.KEIJO_DT   = DBS.KEIJO_DT   AND ");
        sb.append("     SUB_DKIR.TENPO_CD   = DBS.TENPO_CD   AND ");
        sb.append("     SUB_DKIR.BUNRUI1_CD = DBS.BUNRUI1_CD ");
        sb.append(" WHERE ");
        sb.append("     DBS.COMP_CD     = ? AND ");
        sb.append("     DBS.KEIJO_DT    = ? AND ");
        sb.append("     ( ");
        sb.append("         NVL(DBS.URIAGE_SOURI_VL, 0) - NVL(SUB_DKIR.URIAGE_NUKI_VL, 0) <> 0  OR ");
        sb.append("         NVL(DBS.URIAGE_SOURI_QT, 0) - NVL(SUB_DKIR.URIAGE_QT, 0)      <> 0  OR ");
        sb.append("         NVL(DBS.KYAKU_QT, 0)        - NVL(SUB_DKIR.KYAKU_QT, 0)       <> 0 ");
        sb.append("     ) ");
        sb.append(" ORDER BY ");
        sb.append("       DBS.COMP_CD ");
        sb.append("     , DBS.KEIJO_DT ");
        sb.append("     , DBS.TENPO_CD ");
        sb.append("     , DBS.BUNRUI1_CD ");

        return sb.toString();
    }

    private String getSelKintaiIFRuikeiKousinSql() {
        StringBuffer sb = new StringBuffer("");

        // 勤怠IF用累積データ（更新差分）抽出用SelectSQL構築
        sb.append(" SELECT ");
        sb.append("       SUB_DTDU.COMP_CD  ");
        sb.append("     , SUB_DTDU.KEIJO_DT ");
        sb.append("     , SUB_DTDU.TENPO_CD ");
        sb.append("     , SUB_DTDU.BUNRUI1_CD ");
        sb.append("     , NVL(SUB_DTDU.URIAGE_NUKI_VL, 0) - NVL(SUB_DKIR.URIAGE_NUKI_VL, 0)  DIFF_URIAGE_NUKI_VL ");
        sb.append("     , NVL(SUB_DTDU.URIAGE_QT, 0)      - NVL(SUB_DKIR.URIAGE_QT, 0)       DIFF_URIAGE_QT ");
        sb.append("     , NVL(SUB_DTDU.KYAKU_QT, 0)       - NVL(SUB_DKIR.KYAKU_QT, 0)        DIFF_KYAKU_QT ");
        sb.append(" FROM ");
        sb.append(" ( ");
        sb.append("     SELECT ");
        sb.append("           COMP_CD ");
        sb.append("         , KEIJO_DT ");
        sb.append("         , TENPO_CD ");
        sb.append("         , BUNRUI1_CD ");
        sb.append("         , URIAGE_NUKI_VL ");
        sb.append("         , URIAGE_QT ");
        sb.append("         , KYAKU_QT ");
        sb.append("     FROM ");
        sb.append("         DT_TEN_DPT_URI ");
        sb.append("     WHERE ");
        sb.append("         COMP_CD  =  ? AND ");
        sb.append("         KEIJO_DT <  ? AND ");
        sb.append("         KEIJO_DT >= ");
        sb.append("          ( ");
        sb.append("              SELECT ");
        sb.append("                  MIN(START_DT) ");
        sb.append("              FROM ");
        sb.append("                  R_CALENDAR ");
        sb.append("              WHERE ");
        sb.append("                  COMP_CD          =  ? AND ");
        sb.append("                  KARIZIMESYORI_KB <= ? ");
        sb.append("          ) ");
        sb.append(" )  SUB_DTDU ");
        sb.append(" LEFT JOIN ");
        sb.append(" ( ");
        sb.append("     SELECT ");
        sb.append("           COMP_CD ");
        sb.append("         , KEIJO_DT ");
        sb.append("         , TENPO_CD ");
        sb.append("         , BUNRUI1_CD ");
        sb.append("         , SUM(URIAGE_NUKI_VL) URIAGE_NUKI_VL ");
        sb.append("         , SUM(URIAGE_QT)      URIAGE_QT ");
        sb.append("         , SUM(KYAKU_QT)       KYAKU_QT ");
        sb.append("     FROM ");
        sb.append("         DT_KINTAI_IF_RUISEKI ");
        sb.append("     WHERE ");
        sb.append("         COMP_CD        =  ? AND ");
        sb.append("         KEIJO_DT       <  ? AND ");
        sb.append("         KEIJO_DT       >= ");
        sb.append("         ( ");
        sb.append("             SELECT ");
        sb.append("                 MIN(START_DT) ");
        sb.append("             FROM ");
        sb.append("                 R_CALENDAR ");
        sb.append("             WHERE ");
        sb.append("                 COMP_CD          =  ? AND ");
        sb.append("                 KARIZIMESYORI_KB <= ? ");
        sb.append("         ) AND ");
        sb.append("         DATA_RENKEI_FG =  ? ");
        sb.append("     GROUP BY ");
        sb.append("           COMP_CD ");
        sb.append("         , KEIJO_DT ");
        sb.append("         , TENPO_CD ");
        sb.append("         , BUNRUI1_CD ");
        sb.append(" )  SUB_DKIR ");
        sb.append(" ON ");
        sb.append("     SUB_DTDU.COMP_CD    = SUB_DKIR.COMP_CD AND ");
        sb.append("     SUB_DTDU.KEIJO_DT   = SUB_DKIR.KEIJO_DT AND ");
        sb.append("     SUB_DTDU.TENPO_CD   = SUB_DKIR.TENPO_CD AND ");
        sb.append("     SUB_DTDU.BUNRUI1_CD = SUB_DKIR.BUNRUI1_CD ");
        sb.append(" WHERE ");
        sb.append("     SUB_DTDU.COMP_CD = ? AND ");
        sb.append("     ( ");
        sb.append("         NVL(SUB_DTDU.URIAGE_NUKI_VL, 0) - NVL(SUB_DKIR.URIAGE_NUKI_VL, 0) <> 0  OR ");
        sb.append("         NVL(SUB_DTDU.URIAGE_QT, 0)      - NVL(SUB_DKIR.URIAGE_QT, 0)      <> 0  OR ");
        sb.append("         NVL(SUB_DTDU.KYAKU_QT, 0)       - NVL(SUB_DKIR.KYAKU_QT, 0)       <> 0 ");
        sb.append("     ) ");
        sb.append(" ORDER BY ");
        sb.append("       SUB_DTDU.COMP_CD  ");
        sb.append("     , SUB_DTDU.KEIJO_DT ");
        sb.append("     , SUB_DTDU.TENPO_CD ");
        sb.append("     , SUB_DTDU.BUNRUI1_CD ");

        return sb.toString();
    }

    private String getSelKintaiIFRuikeiTimeNoSql() {
        StringBuffer sb = new StringBuffer("");

        // 勤怠IF用累積データ（当日更新差分）抽出用SelectSQL構築
        sb.append(" SELECT ");
        sb.append("       COMP_CD ");
        sb.append("     , KEIJO_DT ");
        sb.append("     , TENPO_CD ");
        sb.append("     , BUNRUI1_CD ");
        sb.append("     , TIME_NO ");
        sb.append("     , SUM(URIAGE_NUKI_VL) URIAGE_NUKI_VL ");
        sb.append("     , SUM(URIAGE_QT)      URIAGE_QT ");
        sb.append("     , SUM(KYAKU_QT)       KYAKU_QT ");
        sb.append(" FROM ");
        sb.append("     DT_KINTAI_IF_RUISEKI ");
        sb.append(" WHERE ");
        sb.append("     COMP_CD    = ? AND ");
        sb.append("     KEIJO_DT   = ? AND ");
        sb.append("     TENPO_CD   = ? AND ");
        sb.append("     BUNRUI1_CD = ? ");
        sb.append(" GROUP BY ");
        sb.append("       COMP_CD ");
        sb.append("     , KEIJO_DT ");
        sb.append("     , TENPO_CD ");
        sb.append("     , BUNRUI1_CD ");
        sb.append("     , TIME_NO ");
        sb.append(" ORDER BY ");
        sb.append("     TIME_NO DESC ");

        return sb.toString();
    }

    private String insSelKintaiIFRuisekiSql() {
        StringBuffer sb = new StringBuffer("");

        // 勤怠IF用累積データ（当日更新差分）作成用InsertSQL構築
        sb.append(" INSERT INTO ");
        sb.append("     DT_KINTAI_IF_RUISEKI ");
        sb.append("     ( ");
        sb.append("           COMP_CD ");
        sb.append("         , SEQ_RB ");
        sb.append("         , KEIJO_DT ");
        sb.append("         , TENPO_CD ");
        sb.append("         , BUMON_CD ");
        sb.append("         , BUNRUI1_CD ");
        sb.append("         , TIME_NO ");
        sb.append("         , URIAGE_NUKI_VL ");
        sb.append("         , URIAGE_QT ");
        sb.append("         , KYAKU_QT ");
        sb.append("         , DATA_RENKEI_FG ");
        sb.append("         , INSERT_USER_ID ");
        sb.append("         , INSERT_TS ");
        sb.append("         , UPDATE_USER_ID ");
        sb.append("         , UPDATE_TS ");
        sb.append("     ) ");
        sb.append(" VALUES ");
        sb.append("     ( ");
        sb.append("           ? ");
        sb.append("         , SEQ_DT_KINTAI_IF_RUISEKI.NEXTVAL ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , NULL ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("         , ? ");
        sb.append("     ) ");

        return sb.toString();
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
}
