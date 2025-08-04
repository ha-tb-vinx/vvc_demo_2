package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <P>タイトル: DWHIFRuisekiDataCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author T.Morihiro
 *  @version 3.00 (2013.10.09) T.Morihiro [CUS00057] ランドローム様対応 売上管理―URIB131_日別売上集計処理
 *  @version 3.01 (2013.10.22) T.Ooshiro  [CUS00057] 課題№191 対応
 *  @version 3.02 (2013.10.25) T.Ooshiro  [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №038
 *  @version 3.03 (2013.10.27) T.Ooshiro  [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №039,№040
 *  @version 3.04 (2013.11.01) T.Ooshiro  [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №076
 *  @version 3.05 (2014.01.15) S.Arakawa  [CUS00057] POSインターフェイス仕様変更対応（全体）結合テスト課題対応 №0039
 */
public class DWHIFRuisekiDataCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "ＤＷＨIF用累積データ作成処理";
    /** バッチID */
    private static final String BATCH_ID = "URIB131080";
    /** DB日付取得 */
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    /** バッチ日付 */
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    private static final String DWH_IF_MONTH = FiResorceUtility.getPropertie(UriResorceKeyConstant.DWH_IF_MONTH);
    
    /**
     * 
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        
        int intDeleteCount = 0;
        int intInsertCount = 0;
        
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementIns = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　ＤＷＨＳ：ＤＷＨIF用累積データ作成処理を開始します。");
        invoker.infoLog(strUserID + "　：　ＤＷＨIF用累積データ:ＤＷＨIF用累積データ削除処理を開始します。");

        // ＤＷＨIF用累積データ削除処理
        int IDX = 1;
        preparedStatementDel = invoker.getDataBase().prepareStatement(delDWHIFRuisekiDataSql());
        preparedStatementDel.setString(IDX++, DWH_IF_MONTH);
        preparedStatementDel.setString(IDX++, UriageCommonConstants.HONJIME_KB_MISYORI);
        preparedStatementDel.setString(IDX++, COMP_CD);
        // 実行
        intDeleteCount = preparedStatementDel.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intDeleteCount + "件のＤＷＨIF用累積データを削除しました。");
        invoker.infoLog(strUserID + "　：　ＤＷＨIF用累積データ:ＤＷＨIF用累積データ削除処理を終了します。");

// D3システムでは、POSシステムとAS400からの連携データを別々に取扱いしているため、当日分データの連携は不要
//        invoker.infoLog(strUserID + "　：　ＤＷＨIF用累積データ:ＤＷＨIF用累積データ(当日分)挿入処理を開始します。");
//        
//        // ＤＷＨIF用累積データ(当日分)挿入処理
//        // SQLを取得し、パラメータを条件にバインドする
//        IDX = 1;
//        preparedStatementIns = invoker.getDataBase().prepareStatement(getSelInsTodayDWHIFRuisekiDataSql());
//        preparedStatementIns.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_ZUMI);
//        preparedStatementIns.setString(IDX++, BATCH_ID);
//        preparedStatementIns.setString(IDX++, DBSERVER_DT);
//        preparedStatementIns.setString(IDX++, BATCH_ID);
//        preparedStatementIns.setString(IDX++, DBSERVER_DT);
//        preparedStatementIns.setString(IDX++, COMP_CD);
//        preparedStatementIns.setString(IDX++, BATCH_DT);
//
//        // 実行
//        intInsertCount = preparedStatementIns.executeUpdate();
//        // ログ出力
//        invoker.infoLog(strUserID + "　：　" + intInsertCount + "件のＤＷＨIF用累積データ(当日分)を挿入しました。");

        invoker.infoLog(strUserID + "　：　ＤＷＨIF用累積データ:ＤＷＨIF用累積データ挿入処理を開始します。");
        
        // ＤＷＨIF用累積データ挿入処理
        // SQLを取得し、パラメータを条件にバインドする
        IDX = 1;
        preparedStatementIns = invoker.getDataBase().prepareStatement(getSelInsDWHIFRuisekiDataSql());
//        preparedStatementIns.setString(IDX++, BATCH_DT);
//        preparedStatementIns.setString(IDX++, BATCH_DT);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_MI);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, BATCH_DT);
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.HONJIME_KB_SYORI);
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, BATCH_DT);
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.HONJIME_KB_SYORI);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_ZUMI);
        preparedStatementIns.setString(IDX++, COMP_CD);

        // 実行
        intInsertCount = preparedStatementIns.executeUpdate();
        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intInsertCount + "件のＤＷＨIF用累積データを挿入しました。");
        invoker.infoLog(strUserID + "　：　ＤＷＨＳ：ＤＷＨIF：ＤＷＨIF用累積データ作成処理を終了します。");

    }

    private String delDWHIFRuisekiDataSql() {
        StringBuffer sb = new StringBuffer("");
        
        // ＤＷＨIF用データ削除用DeleteSQL構築
        sb.append(" DELETE ");
        sb.append(" FROM ");
        sb.append("    DT_DWH_IF_RUISEKI DDIR ");
        sb.append(" WHERE ");
        sb.append("    EXISTS ");
        sb.append("    ( ");
        sb.append("        SELECT ");
        sb.append("              RC.COMP_CD ");
        sb.append("            , RC.END_DT ");
        sb.append("        FROM ");
        sb.append("            ( ");
        sb.append("                SELECT ");
        sb.append("                      R.COMP_CD ");
        sb.append("                    , TO_CHAR ");
        sb.append("                      ( ");
        sb.append("                          ADD_MONTHS(MIN(R.END_DT), - ?) ");
        sb.append("                        , 'YYYYMMDD' ");
        sb.append("                      ) END_DT ");
        sb.append("                FROM ");
        sb.append("                    R_CALENDAR R ");
        sb.append("                WHERE ");
        sb.append("                    R.KARIZIMESYORI_KB = ? ");
        sb.append("                GROUP BY ");
        sb.append("                      R.COMP_CD ");
        sb.append("            ) RC ");
        sb.append("        WHERE ");
        sb.append("            DDIR.COMP_CD  =  RC.COMP_CD AND ");
        sb.append("            DDIR.KEIJO_DT <= RC.END_DT AND ");
        sb.append("            DDIR.COMP_CD  =  ? ");
        sb.append("    ) ");
        
        return sb.toString();
    }

// D3システムでは、POSシステムとAS400からの連携データを別々に取扱いしているため、当日分データの連携は不要
//    private String getSelInsTodayDWHIFRuisekiDataSql() {
//        StringBuffer sb = new StringBuffer("");
//        
//        // ＤＷＨIF用累積データ(当日分)挿入用InsertSQL構築
//        sb.append(" INSERT INTO ");
//        sb.append("    DT_DWH_IF_RUISEKI ");
//        sb.append("    ( ");
//        sb.append("          COMP_CD ");
//        sb.append("        , SEQ_RB ");
//        sb.append("        , KEIJO_DT ");
//        sb.append("        , TENPO_CD ");
//        sb.append("        , BUNRUI1_CD ");
//        sb.append("        , POS_VL ");
//        sb.append("        , POS_QT ");
//        sb.append("        , URIAGE_NUKI_VL ");
//        sb.append("        , URIAGE_QT ");
//        sb.append("        , KYAKU_QT ");
//        sb.append("        , DATA_RENKEI_FG ");
//        sb.append("        , INSERT_USER_ID ");
//        sb.append("        , INSERT_TS ");
//        sb.append("        , UPDATE_USER_ID ");
//        sb.append("        , UPDATE_TS ");
//        sb.append("    ) ");
//        sb.append("    ( ");
//        sb.append("        SELECT ");
//        sb.append("              COMP_CD ");
//        sb.append("            , SEQ_DT_DWH_IF_RUISEKI.NEXTVAL ");
//        sb.append("            , KEIJO_DT ");
//        sb.append("            , TENPO_CD ");
//        sb.append("            , BUNRUI1_CD ");
//        sb.append("            , POS_VL ");
//        sb.append("            , POS_QT ");
//        sb.append("            , URIAGE_NUKI_VL ");
//        sb.append("            , URIAGE_QT ");
//        sb.append("            , KYAKU_QT ");
//        sb.append("            , ? ");
//        sb.append("            , ? ");
//        sb.append("            , ? ");
//        sb.append("            , ? ");
//        sb.append("            , ? ");
//        sb.append("        FROM ");
//        sb.append("            DT_TEN_DPT_URI ");
//        sb.append("        WHERE ");
//        sb.append("            COMP_CD  = ? AND ");
//        sb.append("            KEIJO_DT = ? ");
//        sb.append("    ) ");
//        
//        return sb.toString();
//    }

    private String getSelInsDWHIFRuisekiDataSql() {
        StringBuffer sb = new StringBuffer("");
        
        // ＤＷＨIF用データ挿入用InsertSQL構築
        sb.append(" INSERT INTO ");
        sb.append("    DT_DWH_IF_RUISEKI ");
        sb.append("    ( ");
        sb.append("          COMP_CD ");
        sb.append("        , SEQ_RB ");
        sb.append("        , KEIJO_DT ");
        sb.append("        , TENPO_CD ");
        sb.append("        , BUNRUI1_CD ");
        sb.append("        , POS_VL ");
        sb.append("        , POS_QT ");
        sb.append("        , URIAGE_NUKI_VL ");
        sb.append("        , URIAGE_QT ");
        sb.append("        , KYAKU_QT ");
        sb.append("        , DATA_RENKEI_FG ");
        sb.append("        , INSERT_USER_ID ");
        sb.append("        , INSERT_TS ");
        sb.append("        , UPDATE_USER_ID ");
        sb.append("        , UPDATE_TS ");
        sb.append("    ) ");
        sb.append("    ( ");
        sb.append("        SELECT ");
        sb.append("              SUB_DTDU.COMP_CD ");
        sb.append("            , SEQ_DT_DWH_IF_RUISEKI.NEXTVAL ");
        sb.append("            , SUB_DTDU.KEIJO_DT ");
        sb.append("            , SUB_DTDU.TENPO_CD ");
        sb.append("            , SUB_DTDU.BUNRUI1_CD ");
//        sb.append("            , CASE ");
//        sb.append("                  WHEN SUB_DTDU.KEIJO_DT = ? THEN SUB_DTDU.POS_VL ");
//        sb.append("                  ELSE 0");
//        sb.append("              END ");
//        sb.append("            , CASE ");
//        sb.append("                  WHEN SUB_DTDU.KEIJO_DT = ? THEN SUB_DTDU.POS_QT ");
//        sb.append("                  ELSE 0");
//        sb.append("              END ");
        sb.append("            , NVL(SUB_DTDU.POS_VL, 0) - NVL(SUB_DDIR.POS_VL, 0) DIFF_POS_VL ");
        sb.append("            , NVL(SUB_DTDU.POS_QT, 0) - NVL(SUB_DDIR.POS_QT, 0) DIFF_POS_QT ");
        sb.append("            , NVL(SUB_DTDU.URIAGE_NUKI_VL, 0) - NVL(SUB_DDIR.URIAGE_NUKI_VL, 0) DIFF_URIAGE_NUKI_VL ");
        sb.append("            , NVL(SUB_DTDU.URIAGE_QT, 0)      - NVL(SUB_DDIR.URIAGE_QT, 0)      DIFF_URIAGE_QT ");
        sb.append("            , NVL(SUB_DTDU.KYAKU_QT, 0)       - NVL(SUB_DDIR.KYAKU_QT, 0)       DIFF_KYAKU_QT ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("        FROM ");
        sb.append("            ( ");
        sb.append("                SELECT ");
        sb.append("                      COMP_CD ");
        sb.append("                    , KEIJO_DT ");
        sb.append("                    , TENPO_CD ");
        sb.append("                    , BUNRUI1_CD ");
        sb.append("                    , POS_VL ");
        sb.append("                    , POS_QT ");
        sb.append("                    , URIAGE_NUKI_VL ");
        sb.append("                    , URIAGE_QT ");
        sb.append("                    , KYAKU_QT ");
        sb.append("                FROM ");
        sb.append("                    DT_TEN_DPT_URI ");
        sb.append("                WHERE ");
        sb.append("                    COMP_CD      =  ? ");
        sb.append("                    AND KEIJO_DT <=  ? ");
        sb.append("                    AND KEIJO_DT >= ");
        sb.append("                    ( ");
        sb.append("                        SELECT ");
        sb.append("                            MIN(START_DT) ");
        sb.append("                        FROM ");
        sb.append("                            R_CALENDAR ");
        sb.append("                        WHERE ");
        sb.append("                            COMP_CD          =  ? AND ");
        sb.append("                            KARIZIMESYORI_KB <= ? ");
        sb.append("                    ) ");
        sb.append("            ) SUB_DTDU ");
        sb.append("            LEFT JOIN ");
        sb.append("            ( ");
        sb.append("                SELECT ");
        sb.append("                      COMP_CD ");
        sb.append("                    , KEIJO_DT ");
        sb.append("                    , TENPO_CD ");
        sb.append("                    , BUNRUI1_CD ");
        sb.append("                    , SUM(POS_VL)         POS_VL ");
        sb.append("                    , SUM(POS_QT)         POS_QT ");
        sb.append("                    , SUM(URIAGE_NUKI_VL) URIAGE_NUKI_VL ");
        sb.append("                    , SUM(URIAGE_QT)      URIAGE_QT ");
        sb.append("                    , SUM(KYAKU_QT)       KYAKU_QT ");
        sb.append("                FROM ");
        sb.append("                    DT_DWH_IF_RUISEKI ");
        sb.append("                WHERE ");
        sb.append("                    COMP_CD  =  ? AND ");
        sb.append("                    KEIJO_DT <=  ? AND ");
        sb.append("                    KEIJO_DT >= ");
        sb.append("                    ( ");
        sb.append("                        SELECT ");
        sb.append("                            MIN(START_DT) ");
        sb.append("                        FROM ");
        sb.append("                            R_CALENDAR ");
        sb.append("                        WHERE ");
        sb.append("                            COMP_CD          =  ? AND ");
        sb.append("                            KARIZIMESYORI_KB <= ? ");
        sb.append("                    ) ");
        sb.append("                    AND DATA_RENKEI_FG = ? ");
        sb.append("                GROUP BY ");
        sb.append("                      COMP_CD ");
        sb.append("                    , KEIJO_DT ");
        sb.append("                    , TENPO_CD ");
        sb.append("                    , BUNRUI1_CD ");
        sb.append("            ) SUB_DDIR ");
        sb.append("            ON ");
        sb.append("                SUB_DTDU.COMP_CD    = SUB_DDIR.COMP_CD    AND ");
        sb.append("                SUB_DTDU.KEIJO_DT   = SUB_DDIR.KEIJO_DT   AND ");
        sb.append("                SUB_DTDU.TENPO_CD   = SUB_DDIR.TENPO_CD   AND ");
        sb.append("                SUB_DTDU.BUNRUI1_CD = SUB_DDIR.BUNRUI1_CD ");
        sb.append("        WHERE ");
        sb.append("            SUB_DTDU.COMP_CD = ? AND ");
        sb.append("            ( ");
        sb.append("                NVL(SUB_DTDU.URIAGE_NUKI_VL, 0) - NVL(SUB_DDIR.URIAGE_NUKI_VL, 0) <> 0 OR ");
        sb.append("                NVL(SUB_DTDU.URIAGE_QT, 0)      - NVL(SUB_DDIR.URIAGE_QT, 0)      <> 0 OR ");
        sb.append("                NVL(SUB_DTDU.KYAKU_QT, 0)       - NVL(SUB_DDIR.KYAKU_QT, 0)       <> 0 OR ");
        sb.append("                NVL(SUB_DTDU.POS_VL, 0)         - NVL(SUB_DDIR.POS_VL, 0)         <> 0 OR ");
        sb.append("                NVL(SUB_DTDU.POS_QT, 0)         - NVL(SUB_DDIR.POS_QT, 0)         <> 0 ");
        sb.append("            ) ");
        sb.append("    ) ");
        
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
